package streams

import scala.ref.WeakReference
import scala.concurrent.{ Await, Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

import akka.stream._
import akka.stream.scaladsl._

import util._

class StreamGraph (
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
    override val parent: WeakReference[StreamGraph]
    ) extends ParallelGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, parent) {
  
  override def setInput(f: FKey, newInputs: Vector[DKey]): StreamGraph = {
    new StreamGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs + (f -> newInputs), nextfkey, nextdkey, runOnModify, WeakReference(this))
  }
  override def setInput(f: String, newInputs: Vector[DKey]): StreamGraph = {
    setInput(getFKey(f), newInputs)
  }

  override def replace(fstr: String, f2: Filter): StreamGraph = {
    var ret = replaceHelper(fstr, f2)
    new StreamGraph(ret._1, fKeys, ret._2, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, WeakReference(this))
  }

  override def modify(fstr: String)(func: Filter => Filter): StreamGraph = {
    var ret = modifyHelper(fstr)(func)
    new StreamGraph(ret._1, fKeys, ret._2, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, WeakReference(this))
  }

  override def addFilter(filter: Filter, fName: String): StreamGraph = {
    var ret = addFilterHelper(filter, fName)
    new StreamGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, ret._8, ret._9, runOnModify, WeakReference(this))
  }
  override def addFilter(filter: Filter): StreamGraph = {
    var ret = addFilterHelper(filter, "")
    new StreamGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, ret._8, ret._9, runOnModify, WeakReference(this))
  }

  override def connectNodes(d: DKey, f: FKey): StreamGraph = {
    var ret = connectNodesHelper(d, f)
    new StreamGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, ret._1, ret._2, nextfkey, nextdkey, runOnModify, WeakReference(this))
  }
  override def connectNodes(d: String, f: String): StreamGraph = {
    connectNodes(getDKey(d),getFKey(f))
  }

  override def disconnectNodes(d: DKey, f: FKey): StreamGraph = {
    var ret = disconnectNodesHelper(d,f)
    new StreamGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, ret._1, ret._2, nextfkey, nextdkey, runOnModify, WeakReference(this))
  }
  override def disconnectNodes(d: String, f: String): StreamGraph = {
    disconnectNodes(getDKey(d),getFKey(f))
  }

  override def removeNode(f: FKey): StreamGraph = {
    var ret = removeNodeHelper(f)
    
    
    
    new StreamGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, nextfkey, nextdkey, runOnModify, WeakReference(this))
  }
  override def removeNode(f: String): StreamGraph = {
    removeNode(getFKey(f))
  }
  
  
  override def run():StreamRunGraph={
    var t = 5
    val source: Source[Int, akka.NotUsed] = Source(1 to 100)
    val test:Sink[Unit, akka.NotUsed] = Sink.onComplete(a => t=6)
    
    val s:Source[Future[DataStore],akka.NotUsed] = Source(Vector(dataKeys(getDKey(""))))
    val f:Flow[Vector[DataStore],Vector[DataStore],akka.NotUsed] = Flow.fromFunction(filtKeys(getFKey("")).apply)
    // need to decide how to construct graph
    
    // if created before here then disconnecting will be dificult/impossible
    // if created here then every new run would need to create a new graph but could then make efficencies to run
    //    only the unrun nodes based on their datastores
    
    return new StreamRunGraph(Map[FKey, Filter](), List[FKey](), Map[DKey, Future[DataStore]](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), Map[FKey, Vector[DKey]](), 0, 0, false, null)
  }
  
  override def union(g:ParallelGraph):StreamGraph={
    val newfiltKeys: Map[FKey, Filter] = filtKeys ++ g.filtKeys.filter(p => !fKeys.contains(p._1))
    val newfKeys: List[FKey] = fKeys ++ g.fKeys.filter { x => !fKeys.contains(x) }
    val newdataKeys: Map[DKey, Future[DataStore]] = dataKeys ++ g.dataKeys.filter(p => !dKeys.contains(p._1))
    val newdKeys: List[DKey] = dKeys ++ g.dKeys.filter { x => !dKeys.contains(x) }
    val newfuncToData: Map[FKey, Vector[DKey]] = funcToData ++ g.funcToData.filter(p => !fKeys.contains(p._1))
    val newdataToFunc: Map[DKey, Vector[FKey]] = dataToFunc ++ g.dataToFunc.filter(p => !dKeys.contains(p._1))
    val newfuncToInputs: Map[FKey, Vector[DKey]] = funcToInputs ++ g.funcToInputs.filter(p => !fKeys.contains(p._1))
    val newnextfkey: Int = if (nextfkey > g.nextfkey) nextfkey else g.nextfkey;
    val newnextdkey: Int = if (nextdkey > g.nextdkey) nextdkey else g.nextdkey;
    return new StreamGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc, newfuncToInputs, newnextfkey, newnextdkey, runOnModify, WeakReference(this))
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
    return new StreamGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc, newfuncToInputs, newnextfkey, newnextdkey, runOnModify, WeakReference(this))
  }
  
}

object StreamGraph{
  def apply(b: Boolean = false): StreamGraph = {
    new StreamGraph(Map[FKey, Filter](), List[FKey](), Map[DKey, Future[DataStore]](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), Map[FKey, Vector[DKey]](), 0, 0, b, null)
  }
}