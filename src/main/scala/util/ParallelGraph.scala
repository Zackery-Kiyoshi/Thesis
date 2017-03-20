package util

import scala.ref.WeakReference
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global


abstract class ParallelGraph (
  val filtKeys: Map[FKey, Filter],
  val fKeys: List[FKey],
  val dataKeys: Map[DKey, Future[DataStore]],
  val dKeys: List[DKey],
  val funcToData: Map[FKey, Vector[DKey]],
  val dataToFunc: Map[DKey, Vector[FKey]],
  val funcToInputs: Map[FKey, Vector[DKey]],
  val nextfkey: Int,
  val nextdkey: Int,
  val runOnModify:Boolean,
  val parent:WeakReference[ParallelGraph]) {
  
  def apply(fstr: String): Filter = filtKeys(FKey(fstr))
  
  
  def viewInput(f:FKey):Vector[DKey]={
    funcToInputs(f)
  }
  def viewInput(f:String):Vector[DKey]={
    viewInput(getFKey(f))
  }
  
  def setInput(f:FKey,newInputs:Vector[DKey]):ParallelGraph
  def setInputHelper(f:FKey,newInputs:Vector[DKey]):Map[FKey, Vector[DKey]]={
    return funcToInputs+(f->newInputs)
  }
  def setInput(f:String,newInputs:Vector[DKey]):ParallelGraph={
    setInput(getFKey(f),newInputs)
  }
  
  def replace(fstr: String, f2: Filter): ParallelGraph
  def replaceHelper(fstr: String, f2: Filter): (Map[FKey, Filter],Map[DKey, Future[DataStore]]) = {
    var tmp = ClearDownstream(FKey(fstr))
    
    //needs to change datastores if more are necessary
    
    return (filtKeys.map { case (k, f) => if(k.key == fstr) k -> f2 else k -> f },tmp)
  }
  
  def modify(fstr: String)(func: Filter => Filter):ParallelGraph
  def modifyHelper(fstr: String)(func: Filter => Filter):(Map[FKey, Filter],Map[DKey, Future[DataStore]]) = {
    var tmp = ClearDownstream(FKey(fstr))
    return (filtKeys.map { case (k, f) => if(k.key == fstr) k -> func(f) else k -> f }, tmp)
  }
  
  def addFilter(filter:Filter, fName: String):ParallelGraph
  def addFilter(filter:Filter):ParallelGraph
  def addFilterHelper(filter:Filter, fName: String = ""): ( Map[FKey, Filter], List[FKey],Map[DKey, Future[DataStore]],List[DKey],Map[FKey, Vector[DKey]],Map[DKey, Vector[FKey]],Map[FKey, Vector[DKey]],Int,Int ) = {
    val (fkey, nextf) = if (fName=="") (FKey("filt"+nextfkey), nextfkey+1) else (FKey(fName),nextfkey)
    val (dkey, nextd) = (DKey(fkey,0), nextdkey)

    //TODO - This doesn't add a multiple datastore for the filters
    
    val tmp = filtKeys + (fkey -> filter)
    return (filtKeys + (fkey -> filter), fkey::fKeys, dataKeys+(dkey -> Future{ DataStore() } ), dkey::dKeys, funcToData + (fkey -> Vector.empty.+:(dkey)),
        dataToFunc+(dkey -> Vector.empty ),funcToInputs+ (fkey -> Vector.empty),nextf,nextd)
  }
 
  
  def connectNodes(d: DKey, f: FKey): ParallelGraph
  def connectNodesHelper(d: DKey, f: FKey): (Map[DKey, Vector[FKey]],Map[FKey, Vector[DKey]]) = {
    // need to actually add f to dataToFunc(d)
    var tmp:Vector[FKey] = dataToFunc(d)
    tmp = tmp :+ f
    var tmpInputs = funcToInputs(f) :+ d
    //for(i <- tmp) println(i.key)
    return (dataToFunc + (d -> tmp ),funcToInputs+(f->tmpInputs))
  }
  def connectNodes(d:String, f:String,idx:Int=0): ParallelGraph = {
    return connectNodes(getDKey(d,idx),getFKey(f))
  }
  
  def disconnectNodes(d: DKey, f: FKey):ParallelGraph
  def disconnectNodesHelper(d: DKey, f: FKey): ( Map[DKey, Vector[FKey]],Map[FKey, Vector[DKey]] ) = {
    // need to actually remove (f) from dataToFunc(d)
    var tmp:Vector[FKey] = Vector.empty
    for(i <- dataToFunc(d)) if(i.key != f.key) tmp = tmp :+ i
    
    var tmpInputs = funcToInputs(f).filter(_ != d)
    
    return (dataToFunc + (d -> tmp ),funcToInputs+(f->tmpInputs))
  }
  def disconnectNodes(d:String, f:String,idx:Int=0): ParallelGraph = {
    return disconnectNodes(getDKey(d,idx),getFKey(f))
  }
   
  def removeNode(f: FKey):ParallelGraph
  def removeNodeHelper(f: FKey): ( Map[FKey, Filter], List[FKey],Map[DKey, Future[DataStore]],List[DKey],Map[FKey, Vector[DKey]],Map[DKey, Vector[FKey]],Map[FKey, Vector[DKey]] ) = {
    // must delete associated DataNodes
    var d = funcToData(f)
    var tmpDataToFunc = dataToFunc
    var tmpdKeys = dKeys
    var tmpDataKeys = dataKeys
    for(i<-d){
      tmpDataToFunc = tmpDataToFunc - i
      tmpDataKeys = tmpDataKeys - i
      tmpdKeys = tmpdKeys.filter(_!=i)
    }
    
    // delete the actual node
    return (filtKeys-f, fKeys.filter(_!=f), tmpDataKeys, tmpdKeys, funcToData-f, tmpDataToFunc,funcToInputs-f)
  }
  def removeNode(f: String): ParallelGraph = {
    var ret: FKey = null
    // find the fkey
    for (i <- 0 until fKeys.length) {
      if (fKeys(i).key == f) ret = fKeys(i)
    }
    return removeNode(ret)
  }
  
  def resetDataStores():Map[DKey, Future[DataStore]]={
    var tmp =  Map[DKey, Future[DataStore]]()
    for(d <- dKeys) tmp = tmp +(d -> Future{DataStore()})
    return tmp
  }
  
  def getListToDo(fs:List[FKey]):List[FKey]={
    var sorted:List[FKey] = List.empty
    var f:List[FKey] = List.empty
    for(i <- fs) f = f:+ i
    var ftd:Map[FKey,Vector[DKey]] = Map.empty
    for(i <- funcToData.keys) ftd = ftd + (i->funcToData(i))
    var dtf:Map[DKey,Vector[FKey]] = Map.empty
    for(i <- dataToFunc.keys) dtf = dtf + (i->dataToFunc(i))
    while(!f.isEmpty){
      for( d <- ftd(f(0)) ){
        for( m <- dtf(d) ){
          // remove edge
          dtf = dtf + (d -> dtf(d).filter(_!=m))
          // if there are no other incoming edges then insert into sorted
          var t = true
          
          for(i <- dKeys){
            if(dtf(i).contains(m)) t = false
          }
          
          if(t){
            sorted = sorted :+ m
          }
        }
      }
      f = f.tail
    }
    // if graph has edges then error cycle
    
    return sorted
  }
  
  
  def analyze(): Boolean = { 
    var ret = true
    println("analyze not inplimented")

    return ret
  }

  def run():ParallelGraph
  
  def printNodes(): String = { /* {Key, NodeType } */
    var ret = ""

    for (i <- 0 until fKeys.length) {
      ret += fKeys(i).key + ":" + filtKeys(fKeys(i)).t + ":" + fKeys(i) + "\n"
    }
    for (i <- 0 until dKeys.length) {
      ret += dKeys(i).key + ":" + "DataNode :" + dKeys(i) + "\n"
    }
    println(ret)
    return ret
  }

  def printConnections(): String = { /* fKey : connectedNodes, */
    var ret = ""
    //println("#FKeys:"+fKeys.length)
    ret += "  Filters \n"
    for (i <- 0 until fKeys.length) {
      //println(i + ":" + fKeys(i))
      ret += fKeys(i).key + ": (" + funcToData(fKeys(i)).length + ") ["
      if (funcToData(fKeys(i)) != null) {
        for (j <- 0 until funcToData(fKeys(i)).length) {
          ret += funcToData(fKeys(i))(j).key
          if (j < funcToData(fKeys(i)).length - 1) ret += ","
        }
      }
      ret += "] \n"
    }
    //println("#DKeys:"+dKeys.length)
    ret += "  DataStores \n"
    for (i <- 0 until dKeys.length) {
      ret += dKeys(i).key + ": (" + dataToFunc(dKeys(i)).length + ") ["
      if (dataToFunc(dKeys(i)) != null) {
        for (j <- 0 until dataToFunc(dKeys(i)).length) {
          ret += dataToFunc(dKeys(i))(j).key
          if (j < dataToFunc(dKeys(i)).length - 1) ret += ","
        }
      }
      ret += "] \n"
    }
    println(ret)
    return ret
  }
  
  def union(g:ParallelGraph):ParallelGraph /*={
    val newfiltKeys: Map[FKey, Filter] = filtKeys ++ g.filtKeys.filter( p => !fKeys.contains(p._1) )
    val newfKeys: List[FKey] = fKeys ++ g.fKeys.filter { x => !fKeys.contains(x) }
    val newdataKeys: Map[DKey, Future[DataStore]] = dataKeys ++ g.dataKeys.filter( p => !dKeys.contains(p._1) )
    val newdKeys: List[DKey] = dKeys ++ g.dKeys.filter { x => !dKeys.contains(x) }
    val newfuncToData: Map[FKey, Vector[DKey]] = funcToData ++ g.funcToData.filter( p => !fKeys.contains(p._1) )
    val newdataToFunc: Map[DKey, Vector[FKey]] = dataToFunc ++ g.dataToFunc.filter( p => !dKeys.contains(p._1) )
    val newfuncToInputs: Map[FKey, Vector[DKey]] = funcToInputs ++ g.funcToInputs.filter( p => !fKeys.contains(p._1) )
    val newnextfkey: Int = if(nextfkey > g.nextfkey) nextfkey else g.nextfkey;
    val newnextdkey: Int = if(nextdkey > g.nextdkey) nextdkey else g.nextdkey;
    return new ParallelGraph(newfiltKeys, newfKeys, newdataKeys, newdKeys, newfuncToData, newdataToFunc,newfuncToInputs, newnextfkey, newnextdkey,runOnModify,WeakReference(this))
  } */

  def setRunOnModify(b:Boolean):ParallelGraph
  
  private def ClearDownstream(f:FKey):Map[DKey, Future[DataStore]]={
    var tmp:Map[DKey, Future[DataStore]] = dataKeys
    
    var cur:List[FKey] = (f) :: List() 
    
    while(cur.length != 0){
      for( i <- funcToData(cur(0))){
        dataKeys+(i-> new DataStore())
        cur = cur ::: dataToFunc(i).toList
      }
      cur = cur.tail
    }
    return tmp
  }
  
  /*
  possible implementations
  Future(Graph)
  Future(DataStore)
//  */

  def getTopoSort(): List[FKey]={
    var ret:List[FKey] = List.empty
    
    // get roots
    var todo:List[FKey] = List.empty
    // need to find roots
    for(f <- fKeys){
      var isRoot = true
      for(d <- dKeys){
        if(dataToFunc(d).contains(f)) isRoot = false
      }
      if(isRoot) todo = todo :+ f
    }
    
    // List of edges
    var edges:List[(FKey,FKey)] = List.empty
    for(f <- fKeys){
      for(d <- funcToData(f)){
        for(i <- dataToFunc(d)){
          edges = edges :+ (f,i)
        }
      }
    }
    
    while(!todo.isEmpty){
      ret = ret :+ todo.head
      // for each node with an edge from  roots.head
      var curEdges = edges.filter(x => x._1 == todo.head)
      for(i <- 0 until curEdges.length){
        // remove the edge
        edges = edges.filter(x => x != curEdges(i))
        if(edges.filter(x=> x._2 == curEdges(i)._2).isEmpty){
          todo = todo :+ curEdges(i)._2
        }
      }
      todo = todo.tail
    }
    return ret
  }
  
  
  protected def getDKey(s: String,idx:Int): DKey = {
    var ret: DKey = null
    for (i <- 0 until dKeys.length) {
      //println(dKeys(i))
      if (dKeys(i).key.key == s && dKeys(i).idx ==idx) ret = dKeys(i)
    }
    return ret
  }

  protected def getFKey(s: String): FKey = {
    var ret: FKey = null
    for (i <- 0 until fKeys.length) {
      if (fKeys(i).key == s) ret = fKeys(i)
    }
    return ret
  }

}

object ParallelGraph {
  /* def apply(b: Boolean = true): ParallelGraph = {
    new ParallelGraph( Map[FKey, Filter](), List[FKey](), Map[DKey, Future[DataStore]](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](),Map[FKey, Vector[DKey]](), 0, 0,b,null)
  }
//  */
}
  
  
