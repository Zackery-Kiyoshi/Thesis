package streams

import util._
import scala.concurrent.{ Await, Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.ref.WeakReference

class StreamRunGraph (
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
    override val futs: scala.collection.mutable.Map[FKey, Vector[DataStore]]
    ) extends StreamGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, parent,futs) {
  
  
  override def run():StreamRunGraph={
    return this
  }
  
  def union(g: StreamRunGraph): StreamGraph = {
    val newfiltKeys: Map[FKey, Filter] = filtKeys ++ g.filtKeys.filter(p => !fKeys.contains(p._1))
    val newfKeys: List[FKey] = fKeys ++ g.fKeys.filter { x => !fKeys.contains(x) }
    val newdataKeys: Map[DKey, Future[DataStore]] = dataKeys ++ g.dataKeys.filter(p => !dKeys.contains(p._1))
    val newdKeys: List[DKey] = dKeys ++ g.dKeys.filter { x => !dKeys.contains(x) }
    val newfuncToData: Map[FKey, Vector[DKey]] = funcToData ++ g.funcToData.filter(p => !fKeys.contains(p._1))
    val newdataToFunc: Map[DKey, Vector[FKey]] = dataToFunc ++ g.dataToFunc.filter(p => !dKeys.contains(p._1))
    val newfuncToInputs: Map[FKey, Vector[DKey]] = funcToInputs ++ g.funcToInputs.filter(p => !fKeys.contains(p._1))
    val newnextfkey: Int = if (nextfkey > g.nextfkey) nextfkey else g.nextfkey;
    val newnextdkey: Int = if (nextdkey > g.nextdkey) nextdkey else g.nextdkey;
    return new StreamGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc, newfuncToInputs, newnextfkey, newnextdkey, runOnModify, WeakReference(this),futs)
  }
  
  def getData(f:FKey):Vector[DataStore]={
    // how to get data out
    var ret:Vector[DataStore] = Vector.empty
    
    for(i <- dKeys){
      var t = dataKeys(i)
      if( i.key == f) ret = ret.+:( Await.result(dataKeys(i), Duration.Inf) )
    }
    return ret
  }
  
}