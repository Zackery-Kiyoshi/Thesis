package futures

import scala.ref.WeakReference
import scala.concurrent.{ Await, Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.collection.mutable.ListBuffer

import util._

class FutureGraph(
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
    val futs: scala.collection.mutable.Map[FKey, Future[Vector[DataStore]]]
    ) extends ParallelGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, parent) {

  //new FutureGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, WeakReference(this),futs)
  override def setInput(f: FKey, newInputs: Vector[DKey]): FutureGraph = {
    new FutureGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs + (f -> newInputs), nextfkey, nextdkey, runOnModify, WeakReference(this),futs)
  }
  override def setInput(f: String, newInputs: Vector[DKey]): FutureGraph = {
    setInput(getFKey(f), newInputs)
  }

  override def replace(fstr: String, f2: Filter): FutureGraph = {
    var ret = replaceHelper(fstr, f2)
    new FutureGraph(ret._1, fKeys, ret._2, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, WeakReference(this),futs)
  }

  override def modify(fstr: String)(func: Filter => Filter): FutureGraph = {
    var ret = modifyHelper(fstr)(func)
    new FutureGraph(ret._1, fKeys, ret._2, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, WeakReference(this),futs)
  }

  override def addFilter(filter: Filter, fName: String): FutureGraph = {
    var ret = addFilterHelper(filter, fName)
    new FutureGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, ret._8, ret._9, runOnModify, WeakReference(this),futs)
  }
  override def addFilter(filter: Filter): FutureGraph = {
    var ret = addFilterHelper(filter, "")
    new FutureGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, ret._8, ret._9, runOnModify, WeakReference(this),futs)
  }

  override def connectNodes(d: DKey, f: FKey): FutureGraph = {
    var ret = connectNodesHelper(d, f)
    new FutureGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, ret._1, ret._2, nextfkey, nextdkey, runOnModify, WeakReference(this),futs)
  }
  override def connectNodes(d: String, f: String): FutureGraph = {
    connectNodes(getDKey(d),getFKey(f))
  }

  override def disconnectNodes(d: DKey, f: FKey): FutureGraph = {
    var ret = disconnectNodesHelper(d,f)
    new FutureGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, ret._1, ret._2, nextfkey, nextdkey, runOnModify, WeakReference(this),futs)
  }
  override def disconnectNodes(d: String, f: String): FutureGraph = {
    disconnectNodes(getDKey(d),getFKey(f))
  }

  override def removeNode(f: FKey): FutureGraph = {
    var ret = removeNodeHelper(f)
    new FutureGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, nextfkey, nextdkey, runOnModify, WeakReference(this),futs)
  }
  override def removeNode(f: String): FutureGraph = {
    removeNode(getFKey(f))
  }

  // : Future[FutureGraph] =
  def makeFuts():scala.collection.mutable.Map[FKey, Future[Vector[DataStore]]]= {
    
    def mFut(f: FKey): Future[Vector[DataStore]] = {
      if (futs.contains(f)) return futs(f)
      else {
        //println("inMF:" + f + " " + funcToInputs(f).length)
        funcToInputs(f).foreach(x => mFut(x.key))
        //println("fs built")
//        val input = for (d <- funcToInputs(f)) yield {
//          println(f + "  "+ futs(d.key))
//          while( futs(d.key) == null) { println("T") }
          
          //while(dataKeys(d) == Future{DataStore()}){ println("TEST") }
//          dataKeys(d)
//        }
        
//        val output: Future[Vector[DataStore]] = Future.sequence(fs).map(filtKeys(f).apply(_))
				val output: Future[Vector[DataStore]] = Future.sequence(funcToInputs(f).map { case DKey(fkey, i) => futs(fkey).map(_(i)) }).map(filtKeys(f)(_))
        futs(f) = output
        //println("end:" + f)
        // run on next???
        
        
        
        return output
      }
    }
    
    //val futs = scala.collection.mutable.Map[FKey, Future[Vector[DataStore]]]()
    // for each FKey call make Fut
    for (i <- 0 until fKeys.length) {
			//println("Making future on "+i)
      mFut(fKeys(i))
    }
    return futs;
  }

  def run():FutureRunGraph= {
    println("Run (fut)")
    return new FutureRunGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, WeakReference(this),makeFuts())
  }

  // don't need topoSort (parallelism deals with dependencies)

  
  override def union(g:ParallelGraph):FutureGraph={
    val newfiltKeys: Map[FKey, Filter] = filtKeys ++ g.filtKeys.filter(p => !fKeys.contains(p._1))
    val newfKeys: List[FKey] = fKeys ++ g.fKeys.filter { x => !fKeys.contains(x) }
    val newdataKeys: Map[DKey, Future[DataStore]] = dataKeys ++ g.dataKeys.filter(p => !dKeys.contains(p._1))
    val newdKeys: List[DKey] = dKeys ++ g.dKeys.filter { x => !dKeys.contains(x) }
    val newfuncToData: Map[FKey, Vector[DKey]] = funcToData ++ g.funcToData.filter(p => !fKeys.contains(p._1))
    val newdataToFunc: Map[DKey, Vector[FKey]] = dataToFunc ++ g.dataToFunc.filter(p => !dKeys.contains(p._1))
    val newfuncToInputs: Map[FKey, Vector[DKey]] = funcToInputs ++ g.funcToInputs.filter(p => !fKeys.contains(p._1))
    val newnextfkey: Int = if (nextfkey > g.nextfkey) nextfkey else g.nextfkey;
    val newnextdkey: Int = if (nextdkey > g.nextdkey) nextdkey else g.nextdkey;
    return new FutureGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc, newfuncToInputs, newnextfkey, newnextdkey, runOnModify, WeakReference(this),futs)
  }
  
  def union(g: FutureGraph): FutureGraph = {
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
    return new FutureGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc, newfuncToInputs, newnextfkey, newnextdkey, runOnModify, WeakReference(this),newFuts)
  }
  def union(g: FutureRunGraph): FutureGraph = {
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
    return new FutureGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc, newfuncToInputs, newnextfkey, newnextdkey, runOnModify, WeakReference(this),newFuts)
  }

  def copy(): FutureGraph = {
    new FutureGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, parent,futs)
  }
  def setRunOnModify(b: Boolean):FutureGraph={
    new FutureGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, b, parent,futs)
  }

}

object FutureGraph {
  def apply(b: Boolean = false): FutureGraph = {
    new FutureGraph(Map[FKey, Filter](), List[FKey](), Map[DKey, Future[DataStore]](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), Map[FKey, Vector[DKey]](), 0, 0, b, null,scala.collection.mutable.Map[FKey, Future[Vector[DataStore]]]())
  }
  
  def apply(filtKeys: Map[FKey, Filter], fKeys: List[FKey], dataKeys: Map[DKey, Future[DataStore]], dKeys: List[DKey], funcToData: Map[FKey, Vector[DKey]], dataToFunc: Map[DKey, Vector[FKey]], funcToInputs: Map[FKey, Vector[DKey]], nextfkey: Int, nextdkey: Int, runOnModify: Boolean, parent: WeakReference[FutureGraph], futs: scala.collection.mutable.Map[FKey, Future[Vector[DataStore]]] ):FutureGraph={
    new FutureGraph( filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, parent,futs)
  }
  
}


