package streams

import scala.ref.WeakReference
import scala.concurrent.{ Await, Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

import akka.stream._
import akka.stream.scaladsl._

import util._

class StreamGraph(
    override val filtKeys: Map[FKey, Filter],
    override val fKeys: List[FKey],
    override val dataKeys: Map[DKey, Future[DataStore]],
    override val dKeys: List[DKey],
    override val funcToData: Map[FKey, Vector[DKey]],
    override val dataToFunc: Map[DKey, Vector[FKey]],
    override val funcToInputs: Map[FKey, Vector[DKey]],
    override val nextfkey: Int,
    override val nextdkey: Int,
    override val runOnModify: Boolean,
    override val parent: WeakReference[StreamGraph],
    val futs: scala.collection.mutable.Map[FKey, Vector[DataStore]]) extends ParallelGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, parent) {

  override def setInput(f: FKey, newInputs: Vector[DKey]): StreamGraph = {
    new StreamGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs + (f -> newInputs), nextfkey, nextdkey, runOnModify, WeakReference(this), futs)
  }
  override def setInput(f: String, newInputs: Vector[DKey]): StreamGraph = {
    setInput(getFKey(f), newInputs)
  }

  override def replace(fstr: String, f2: Filter): StreamGraph = {
    var ret = replaceHelper(fstr, f2)
    new StreamGraph(ret._1, fKeys, ret._2, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, WeakReference(this), futs)
  }

  override def modify(fstr: String)(func: Filter => Filter): StreamGraph = {
    var ret = modifyHelper(fstr)(func)
    new StreamGraph(ret._1, fKeys, ret._2, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, WeakReference(this), futs)
  }

  override def addFilter(filter: Filter, fName: String): StreamGraph = {
    var ret = addFilterHelper(filter, fName)
    new StreamGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, ret._8, ret._9, runOnModify, WeakReference(this), futs)
  }
  override def addFilter(filter: Filter): StreamGraph = {
    var ret = addFilterHelper(filter, "")
    new StreamGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, ret._8, ret._9, runOnModify, WeakReference(this), futs)
  }

  override def connectNodes(d: DKey, f: FKey): StreamGraph = {
    var ret = connectNodesHelper(d, f)
    new StreamGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, ret._1, ret._2, nextfkey, nextdkey, runOnModify, WeakReference(this), futs)
  }
  override def connectNodes(d: String, f: String, i: Int = 0): StreamGraph = {
    connectNodes(getDKey(d, i), getFKey(f))
  }

  override def disconnectNodes(d: DKey, f: FKey): StreamGraph = {
    var ret = disconnectNodesHelper(d, f)
    new StreamGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, ret._1, ret._2, nextfkey, nextdkey, runOnModify, WeakReference(this), futs)
  }
  override def disconnectNodes(d: String, f: String, i: Int = 0): StreamGraph = {
    disconnectNodes(getDKey(d, i), getFKey(f))
  }

  override def removeNode(f: FKey): StreamGraph = {
    var ret = removeNodeHelper(f)

    new StreamGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, nextfkey, nextdkey, runOnModify, WeakReference(this), futs)
  }
  override def removeNode(f: String): StreamGraph = {
    removeNode(getFKey(f))
  }

  override def run(): StreamRunGraph = {
    var t = 5
    val source: Source[Int, akka.NotUsed] = Source(1 to 100)
    val test: Sink[Unit, akka.NotUsed] = Sink.onComplete(a => t = 6)

    //val s: Source[Future[DataStore], akka.NotUsed] = Source(Vector(dataKeys(getDKey("", 0))))
    //val f: Flow[Vector[DataStore], Vector[DataStore], akka.NotUsed] = Flow.fromFunction(filtKeys(getFKey("")).apply)
    // need to decide how to construct graph

    // if created before here then disconnecting will be dificult/impossible
    // if created here then every new run would need to create a new graph but could then make efficencies to run
    //    only the unrun nodes based on their datastores
    /* example Graph creation
    val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[akka.NotUsed] =>
      import GraphDSL.Implicits._
      val in = Source(1 to 10)
      val out = Sink.ignore
      val bcast = builder.add(Broadcast[Int](2))
      val merge = builder.add(Merge[Int](2))
      val f1, f2, f3, f4 = Flow[Int].map(_ + 10)
      in ~> f1 ~> bcast ~> f2 ~> merge ~> f3 ~> out
      bcast ~> f4 ~> merge
      ClosedShape
    })
//		*/

    val gs: scala.collection.mutable.Map[FKey, akka.stream.scaladsl.RunnableGraph[akka.NotUsed]] = scala.collection.mutable.Map[FKey, akka.stream.scaladsl.RunnableGraph[akka.NotUsed]]()

    val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[akka.NotUsed]  =>
      import GraphDSL.Implicits._
      val in = Source(Vector[Vector[DataStore]]())
      val out = Sink.ignore
      // for each filter create a flow with the datasotore as an output

      val filts: scala.collection.mutable.Map[FKey, Flow[Vector[DataStore], Vector[DataStore], akka.NotUsed]] = scala.collection.mutable.Map[FKey, Flow[Vector[DataStore], Vector[DataStore], akka.NotUsed]]()
      // correct output: changing value for datastore
      val stores: scala.collection.mutable.Map[FKey, SinkShape[Vector[DataStore]]] = scala.collection.mutable.Map[FKey,SinkShape[Vector[DataStore]]] ()
      var outputs:scala.collection.mutable.Map[FKey, UniformFanOutShape[Vector[DataStore], Vector[DataStore]]] = scala.collection.mutable.Map[FKey, UniformFanOutShape[Vector[DataStore], Vector[DataStore]]]()
      var outbools:scala.collection.mutable.Map[FKey, Int] = scala.collection.mutable.Map[FKey, Int]() 
      for (i <- fKeys) {
        filts += (i -> Flow.fromFunction(filtKeys(i).apply))
        stores += (i -> builder.add(Sink.seq[Vector[DataStore]]) )
        //stores += (i -> builder.add(Sink.foreach { x:Vector[DataStore] => futs(i) = x }))
        println( i + ":" + funcToData(i).length)
        outputs += (i -> builder.add(Broadcast[Vector[DataStore]](funcToData(i).length + 1)))
        outbools += (i -> (funcToData(i).length + 1))
      }
       
      for (i <- getTopoSort()) {
        // if fKey(i) is source connect to in
        var tmpin: UniformFanInShape[Vector[DataStore], Vector[DataStore]] = builder.add(Merge[Vector[DataStore]](  if( funcToInputs(i).length != 0) funcToInputs(i).length else 1))
        if (funcToInputs(i).isEmpty) {
          // out should be the datastore
          in ~> tmpin
        } else {
          // collect input datastores and connect to filts(i)
          for (j <- funcToInputs(i)) {
//            println(i)
//            print(j + ":")
//            println(outputs(j.key).outlets.length)
            outputs(j.key) ~> tmpin
            outbools(j.key) -= 1 
          }

        }
        if (funcToData(i).isEmpty) {
          println("HERE:" + i)
          outputs(i) ~> out
          outbools(i) -= 1
        }
        // connect the store
        outputs(i) ~> stores(i)
        outbools(i) -= 1
        tmpin ~> filts(i) ~> outputs(i)

      }
      for( i <- outbools){
        println( i )
      }
      ClosedShape
    })
    
    implicit val system = akka.actor.ActorSystem("Graph")
    implicit val materializer = akka.stream.ActorMaterializer()
    g.run()(materializer)
    return new StreamRunGraph(Map[FKey, Filter](), List[FKey](), Map[DKey, Future[DataStore]](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), Map[FKey, Vector[DKey]](), 0, 0, false, null, futs)
  }

  override def union(g: ParallelGraph): StreamGraph = {
    val newfiltKeys: Map[FKey, Filter] = filtKeys ++ g.filtKeys.filter(p => !fKeys.contains(p._1))
    val newfKeys: List[FKey] = fKeys ++ g.fKeys.filter { x => !fKeys.contains(x) }
    val newdataKeys: Map[DKey, Future[DataStore]] = dataKeys ++ g.dataKeys.filter(p => !dKeys.contains(p._1))
    val newdKeys: List[DKey] = dKeys ++ g.dKeys.filter { x => !dKeys.contains(x) }
    val newfuncToData: Map[FKey, Vector[DKey]] = funcToData ++ g.funcToData.filter(p => !fKeys.contains(p._1))
    val newdataToFunc: Map[DKey, Vector[FKey]] = dataToFunc ++ g.dataToFunc.filter(p => !dKeys.contains(p._1))
    val newfuncToInputs: Map[FKey, Vector[DKey]] = funcToInputs ++ g.funcToInputs.filter(p => !fKeys.contains(p._1))
    val newnextfkey: Int = if (nextfkey > g.nextfkey) nextfkey else g.nextfkey;
    val newnextdkey: Int = if (nextdkey > g.nextdkey) nextdkey else g.nextdkey;
    return new StreamGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc, newfuncToInputs, newnextfkey, newnextdkey, runOnModify, WeakReference(this), futs)
  }

  def union(g: StreamGraph): StreamGraph = {
    val newfiltKeys: Map[FKey, Filter] = filtKeys ++ g.filtKeys.filter(p => !fKeys.contains(p._1))
    val newfKeys: List[FKey] = fKeys ++ g.fKeys.filter { x => !fKeys.contains(x) }
    val newdataKeys: Map[DKey, Future[DataStore]] = dataKeys ++ g.dataKeys.filter(p => !dKeys.contains(p._1))
    val newdKeys: List[DKey] = dKeys ++ g.dKeys.filter { x => !dKeys.contains(x) }
    val newfuncToData: Map[FKey, Vector[DKey]] = funcToData ++ g.funcToData.filter(p => !fKeys.contains(p._1))
    val newdataToFunc: Map[DKey, Vector[FKey]] = dataToFunc ++ g.dataToFunc.filter(p => !dKeys.contains(p._1))
    val newfuncToInputs: Map[FKey, Vector[DKey]] = funcToInputs ++ g.funcToInputs.filter(p => !fKeys.contains(p._1))
    val newnextfkey: Int = if (nextfkey > g.nextfkey) nextfkey else g.nextfkey;
    val newnextdkey: Int = if (nextdkey > g.nextdkey) nextdkey else g.nextdkey;
    return new StreamGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc, newfuncToInputs, newnextfkey, newnextdkey, runOnModify, WeakReference(this), futs)
  }

}

object StreamGraph {
  def apply(b: Boolean = false): StreamGraph = {
    new StreamGraph(Map[FKey, Filter](), List[FKey](), Map[DKey, Future[DataStore]](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), Map[FKey, Vector[DKey]](), 0, 0, b, null, scala.collection.mutable.Map[FKey, Vector[DataStore]]())
  }
}