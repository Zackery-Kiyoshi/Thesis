package sequential

import util._
import scala.ref.WeakReference
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class SequentialGraph (
  override val filtKeys: Map[FKey, Filter],
  override val fKeys: List[FKey],
  override val dataKeys: Map[DKey, DataStore],
  override val dKeys: List[DKey],
  override val funcToData: Map[FKey, Vector[DKey]],
  override val dataToFunc: Map[DKey, Vector[FKey]],
  override val funcToInputs: Map[FKey, Vector[DKey]],
  override val nextfkey: Int,
  override val nextdkey: Int,
  override val runOnModify:Boolean,
  override val parent:WeakReference[SequentialGraph],
  val print:Boolean
  ) extends Graph(filtKeys,fKeys,dataKeys,dKeys,funcToData,dataToFunc,funcToInputs,nextfkey,nextdkey,runOnModify,parent){
  
//  private val runOnModify = true
  private var running = true
  def setPrints(b:Boolean){
    new SequentialGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc,funcToInputs, nextfkey, nextdkey,runOnModify,WeakReference(this),b)
  }
  
  override def setInput(f:FKey,newInputs:Vector[DKey]):SequentialGraph={
    new SequentialGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc,funcToInputs+(f->newInputs), nextfkey, nextdkey,runOnModify,WeakReference(this),print)
  }
  override def setInput(f:String,newInputs:Vector[DKey]):SequentialGraph={
    var ret = setInputHelper(getFKey(f),newInputs)
    return new SequentialGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, ret, nextfkey, nextdkey, runOnModify, WeakReference(this),print)
  }
  
  override def replace(fstr: String, f2: Filter): SequentialGraph = {
    var ret = replaceHelper(fstr,f2)
    return new SequentialGraph(ret._1, fKeys, ret._2, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, WeakReference(this),print)
  }
  
  override def modify(fstr: String)(func: Filter => Filter):SequentialGraph = {
    var ret = modifyHelper(fstr)(func)
    return new SequentialGraph(ret._1, fKeys, ret._2, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, WeakReference(this),print)
  }
  
  override def addFilter(filter:Filter, fName: String = ""): SequentialGraph = {
    var ret = addFilterHelper(filter, fName)
    
    return new SequentialGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, ret._8, ret._9, runOnModify, WeakReference(this),print)
  }

  override def addFilter(filter:Filter): SequentialGraph = {
    addFilter(filter, "")
  }
 
  override def connectNodes(d: DKey, f: FKey): SequentialGraph = {
    var ret = connectNodesHelper(d,f)
    return new SequentialGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, ret._1, ret._2, nextfkey, nextdkey, runOnModify, WeakReference(this),print)
  }

  override def connectNodes(d:String, f:String,idx:Int=0): SequentialGraph = {
    connectNodes(getDKey(d,idx),getFKey(f)) 
    //ret.run( List.empty:+ super.getFKey(f))
  }
  
  override def disconnectNodes(d: DKey, f: FKey): SequentialGraph = {
    var ret = disconnectNodesHelper(d,f)
    return new SequentialGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, ret._1, ret._2, nextfkey, nextdkey, runOnModify, WeakReference(this),print)
  }

  override def disconnectNodes(d:String, f:String,idx:Int=0): SequentialGraph = {
    disconnectNodes(getDKey(d,idx),getFKey(f))
  }
  
  override def removeNode(f: FKey): SequentialGraph = {
    var ret = removeNodeHelper(f)
    return new SequentialGraph(ret._1, ret._2, ret._3, ret._4, ret._5, ret._6, ret._7, nextfkey, nextdkey, runOnModify, WeakReference(this),print)
  }
  override def removeNode(f: String): SequentialGraph = {
    removeNode(getFKey(f))
  }
  
  // does this need to be overwritten or could it happen when connecting a node/modifying a node???
  // also does it really need to be parallized???
  override def analyze():Boolean={
    var ret = true
    
    return ret
  }
  
  
  
//  /*
  override def run():SequentialRunGraph={
    if(print)println("Run (seq)")
    // analyze first to make sure there are no mistakes???
    if(!analyze() ){
      println("There is an error in the graph please fix before running again")
      return null
    }
    running = true
    
    val topo = getTopoSort()
    assert(fKeys.length == topo.length, "The topological sort lost something!")
    val ret = run(topo)
    // need to run each loop
    return new SequentialRunGraph(ret.filtKeys, ret.fKeys, ret.dataKeys, ret.dKeys, ret.funcToData, ret.dataToFunc, ret.funcToInputs, ret.nextfkey, ret.nextdkey, false, WeakReference(this),print)
  }
  
  def run(todo:List[FKey]):SequentialGraph={
    if(running==false){
      running = runOnModify
      return this
    }
    if(todo.isEmpty) return this
    val newTodo:List[FKey] = todo.tail 
    val curNode:Filter = filtKeys(todo.head)
    val tmpDataKeys:collection.mutable.Map[DKey, DataStore] = collection.mutable.Map(dataKeys.toSeq: _*)
    // need to get the correct input data
    val data:Vector[DataStore] = (for(d <- dKeys; if(dataToFunc(d).contains(todo(0)))) yield {
        dataKeys(d)
    }).toVector
    //if(data.length >0)
      //println( data.length + ":" + data(0) )
    //val d:Future[Vector[DataStore]] = Future.sequence(data)
    if(print) println("Start:"+todo(0))
    val rezData:Vector[DataStore] = curNode.apply(data)  
    // in creation each need filter needs to know how many of each
    if(print) println("end:"+todo(0))
    var i=0
    //println(funcToData(todo(0)).length)
    //println("  " + rezData.len gth)
    for(d<- funcToData(todo.head)){
      // update dataStores
      //for sinks
      if(i < rezData.length){
        //Future{rezData(i)}
//        println("HERE " + Future{rezData(i)} + ";")
        tmpDataKeys(d) = rezData(i)
        i+=1
      }
    }
    val n = new SequentialGraph(filtKeys,fKeys,Map(tmpDataKeys.toSeq: _*),dKeys,funcToData,dataToFunc,funcToInputs,nextfkey,nextdkey,runOnModify,WeakReference(this),print)
    n.run(newTodo)
  }
  
//  */
  
  def union(g:SequentialGraph):SequentialGraph={
    val newfiltKeys: Map[FKey, Filter] = filtKeys ++ g.filtKeys.filter( p => !fKeys.contains(p._1) )
    val newfKeys: List[FKey] = fKeys ++ g.fKeys.filter { x => !fKeys.contains(x) }
    val newdataKeys: Map[DKey, DataStore] = dataKeys ++ g.dataKeys.filter( p => !dKeys.contains(p._1) )
    val newdKeys: List[DKey] = dKeys ++ g.dKeys.filter { x => !dKeys.contains(x) }
    val newfuncToData: Map[FKey, Vector[DKey]] = funcToData ++ g.funcToData.filter( p => !fKeys.contains(p._1) )
    val newdataToFunc: Map[DKey, Vector[FKey]] = dataToFunc ++ g.dataToFunc.filter( p => !dKeys.contains(p._1) )
    val newfuncToInputs: Map[FKey, Vector[DKey]] = funcToInputs ++ g.funcToInputs.filter( p => !fKeys.contains(p._1) )
    val newnextfkey: Int = if(nextfkey > g.nextfkey) nextfkey else g.nextfkey;
    val newnextdkey: Int = if(nextdkey > g.nextdkey) nextdkey else g.nextdkey;
    return new SequentialGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc,newfuncToInputs, newnextfkey, newnextdkey,runOnModify,WeakReference(this),print)
  }
  
  def setRunOnModify(b:Boolean):SequentialGraph={
    new SequentialGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, b, parent,print)
  }
  
}

// modifications will start running depending on settings
  
// datastores should really be future[DataStore]
// functions give promises for the future[DataStore]


object SequentialGraph {
  def apply(b: Boolean = false): SequentialGraph = {
    new SequentialGraph( Map[FKey, Filter](), List[FKey](), Map[DKey, DataStore](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), Map[FKey, Vector[DKey]](), 0, 0,false,null,b)
  }
  
}
