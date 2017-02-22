package futures

import scala.ref.WeakReference
import scala.concurrent.{ Await, Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.collection.mutable.ListBuffer

import util._

class FutureRunGraph(
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
    override val parent: WeakReference[FutureGraph],
    override val futs: scala.collection.mutable.Map[FKey, Future[Vector[DataStore]]]
    ) extends FutureGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, parent,futs) {

  /*
  // : Future[FutureGraph] =
  def makeFuts() {
    //val futs = scala.collection.mutable.Map[FKey, Future[Vector[DataStore]]]()

    // for each FKey call make Fut
    for (i <- 0 until fKeys.length) {
			println("Making future on "+i)
      makeFut(fKeys(i))
    }

    def makeFut(f: FKey): Future[Vector[DataStore]] = {
      if (futs.contains(f)) return futs(f)
      else {
        println("inMF:" + f + " " + funcToInputs(f).length)
        funcToInputs(f).foreach(x => makeFut(x.key))
        println("fs built")
//        val output: Future[Vector[DataStore]] = Future.sequence(fs).map(filtKeys(f).apply(_))
				val output: Future[Vector[DataStore]] = Future.sequence(funcToInputs(f).map { case DKey(fkey, i) => futs(fkey).map(_(i)) }).map(filtKeys(f)(_))
        futs(f) = output
        println("end:" + f)
        // run on next???
        
        return output
      }
    }

  }
//  */

  override def run():FutureRunGraph= {
    return this
  }

  def getData(f:FKey):Vector[DataStore]={
    // how to get data out
    return Await.result(futs(f), Duration.Inf)
  }
  
  // don't need topoSort (parallelism deals with dependencies)

  override def union(g: FutureRunGraph): FutureRunGraph = {
    val newfiltKeys: Map[FKey, Filter] = filtKeys ++ g.filtKeys.filter(p => !fKeys.contains(p._1))
    val newfKeys: List[FKey] = fKeys ++ g.fKeys.filter { x => !fKeys.contains(x) }
    val newdataKeys: Map[DKey, Future[DataStore]] = dataKeys ++ g.dataKeys.filter(p => !dKeys.contains(p._1))
    val newdKeys: List[DKey] = dKeys ++ g.dKeys.filter { x => !dKeys.contains(x) }
    val newfuncToData: Map[FKey, Vector[DKey]] = funcToData ++ g.funcToData.filter(p => !fKeys.contains(p._1))
    val newdataToFunc: Map[DKey, Vector[FKey]] = dataToFunc ++ g.dataToFunc.filter(p => !dKeys.contains(p._1))
    val newfuncToInputs: Map[FKey, Vector[DKey]] = funcToInputs ++ g.funcToInputs.filter(p => !fKeys.contains(p._1))
    val newnextfkey: Int = if (nextfkey > g.nextfkey) nextfkey else g.nextfkey;
    val newnextdkey: Int = if (nextdkey > g.nextdkey) nextdkey else g.nextdkey;
    val newFuts = futs ++ g.futs.filter(p => !fKeys.contains(p._1))
    return new FutureRunGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc, newfuncToInputs, newnextfkey, newnextdkey, runOnModify, WeakReference(this),newFuts)
  }


  def clearDownstreamFuts(f:FKey):scala.collection.mutable.Map[FKey, Future[Vector[DataStore]]]={
    var tmp = futs.clone()
    var cur:List[FKey] = (f) :: List() 
    while(cur.length != 0){
      for( i <- funcToData(cur(0))){
        cur = cur ::: dataToFunc(i).toList
      }
      tmp.remove(cur(0))
      cur = cur.tail
    }
    return tmp
  }

  override def copy(): FutureRunGraph = {
    return new FutureRunGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, parent,futs)
  }

}

object FutureRunGraph {
  def apply(b: Boolean = false): FutureGraph = {
    new FutureRunGraph(Map[FKey, Filter](), List[FKey](), Map[DKey, Future[DataStore]](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), Map[FKey, Vector[DKey]](), 0, 0, b, null,scala.collection.mutable.Map[FKey, Future[Vector[DataStore]]]())
  }
  
}


