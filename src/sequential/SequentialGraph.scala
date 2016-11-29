package sequential

import util._
import scala.ref.WeakReference
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class SequentialGraph private(
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
  override val parent:WeakReference[SequentialGraph]
  ) extends Graph(filtKeys,fKeys,dataKeys,dKeys,funcToData,dataToFunc,funcToInputs,nextfkey,nextdkey,runOnModify,parent){
  
//  private val runOnModify = true
  private var running = true
  
  
  override def setInput(f:FKey,newInputs:Vector[DKey]):SequentialGraph={
    new SequentialGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc,funcToInputs+(f->newInputs), nextfkey, nextdkey,runOnModify,WeakReference(this))
  }
  override def setInput(f:String,newInputs:Vector[DKey]):SequentialGraph={
    var ret = toSequentialGraph(super.setInput(f,newInputs))
    if(runOnModify)ret.run( )
    return ret
  }
  
  override def replace(fstr: String, f2: Filter): SequentialGraph = {
    var ret = toSequentialGraph(super.replace(fstr,f2))
    if(runOnModify)ret.run( )
    return ret
  }
  
  override def modify(fstr: String)(func: Filter => Filter):SequentialGraph = {
    var ret = toSequentialGraph(super.modify(fstr)(func))
    if(runOnModify)ret.run( )
    return ret
  }
  
  override def addFilter(filter:Filter, fName: String = "", dName: String = ""): SequentialGraph = {
    var ret = toSequentialGraph(super.addFilter(filter, fName, dName))
    if(runOnModify)ret.run( )
    return ret
  }

 
  override def connectNodes(d: DKey, f: FKey): SequentialGraph = {
    var ret = toSequentialGraph(super.connectNodes(d,f))
    if(runOnModify)ret.run()
    return ret
  }
  override def connectNodes(d:String, f:String): SequentialGraph = {
    var ret = toSequentialGraph(super.connectNodes(d,f)) 
    //ret.run( List.empty:+ super.getFKey(f))
    return ret
  }
  
  override def disconnectNodes(d: DKey, f: FKey): SequentialGraph = {
    var ret = super.disconnectNodes(d,f)
    toSequentialGraph(ret)
  }
  override def disconnectNodes(d:String, f:String): SequentialGraph = {
    var ret = super.disconnectNodes(d,f)
    toSequentialGraph(ret)
  }
  
  override def removeNode(f: FKey): SequentialGraph = {
    var ret = super.removeNode(f)
    toSequentialGraph(ret)
  }
  override def removeNode(f: String): SequentialGraph = {
    var ret = super.removeNode(f)
    toSequentialGraph(ret)
  }
  
  private def toSequentialGraph(g:Graph):SequentialGraph={
    new SequentialGraph(g.filtKeys,g.fKeys,g.dataKeys,g.dKeys,g.funcToData,g.dataToFunc,g.funcToInputs,g.nextfkey,g.nextdkey,runOnModify,WeakReference(this))
  }
  
  
  // does this need to be overwritten or could it happen when connecting a node/modifying a node???
  // also does it really need to be parallized???
  override def analyze():Boolean={
    var ret = true
    
    return ret
  }
  
  
  
//  /*
  override def run(){
    //println("run")
    // analyze first to make sure there are no mistakes???
    if(!analyze() ){
      println("There is an error in the graph please fix before running again")
      return
    }
    running = true
    
    run(super.getTopoSort())
    // need to run each loop
    
  }
  
  def run(todo:List[FKey]):SequentialGraph={
    if(running==false){
      running = runOnModify
      return this
    }
    var newTodo:List[FKey] = List.empty 
    var curNode:Filter = null
    if(todo.isEmpty){
      return this
    }else {
      newTodo = todo.tail
      //println(todo(0).key)
      curNode = filtKeys(todo(0))
    }
    val tmpDataKeys:collection.mutable.Map[DKey, DataStore] = collection.mutable.Map(dataKeys.toSeq: _*)
    // need to get the correct input data
    val data:Vector[DataStore] = (for(d <- dKeys; if(dataToFunc(d).contains(todo(0)))) yield {
        dataKeys(d)
    }).toVector
    //if(data.length >0)
      //println( data.length + ":" + data(0) )
    //val d:Future[Vector[DataStore]] = Future.sequence(data)
    val rezData:Vector[DataStore] = curNode.apply(data)  
    // in creation each need filter needs to know how many of each
    var i=0
    //println(funcToData(todo(0)).length)
    //println("  " + rezData.length)
    for(d<- funcToData(todo(0))){
      // update dataStores
      //for sinks
      if(i < rezData.length){
        //Future{rezData(i)}
//        println("HERE " + Future{rezData(i)} + ";")
        tmpDataKeys(d) = rezData(i)
        i+=1
      }
    }
    var n = new SequentialGraph(filtKeys,fKeys,Map(tmpDataKeys.toSeq: _*),dKeys,funcToData,dataToFunc,funcToInputs,nextfkey,nextdkey,runOnModify,WeakReference(this))
    n.run(newTodo)
  }
  
//  */
  
  // will stop this graph from running safely
  def terminate():Unit={
    //
    running=false
    
  }
  
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
    return new SequentialGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc,newfuncToInputs, newnextfkey, newnextdkey,runOnModify,WeakReference(this))
  }
  
  override def setRunOnModify(b:Boolean):SequentialGraph={
    toSequentialGraph(super.setRunOnModify(b))
  }
  
}

// modifications will start running depending on settings
  
// datastores should really be future[DataStore]
// functions give promises for the future[DataStore]


object SequentialGraph {
  def apply(b: Boolean = false): SequentialGraph = {
    new SequentialGraph( Map[FKey, Filter](), List[FKey](), Map[DKey, DataStore](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), Map[FKey, Vector[DKey]](), 0, 0,b,null)
  }
  
}
