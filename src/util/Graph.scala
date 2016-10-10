package util

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap


class FKey(var key:String){
  def equals(s:FKey):Boolean={
    return key == s.key
  }
  def equals(s:String):Boolean={
    return key == s
  }
}

class DKey(var key:String){
  def equals(s:DKey):Boolean={
    return key == s.key
  }
  def equals(s:String):Boolean={
    return key == s
  }
}


class Graph {
  var funcKeys = HashMap.empty[FKey,Function]
  var fKeys:List[FKey] = List()
  var dataKeys = HashMap.empty[DKey,DataStore]
  var dKeys:List[DKey] = List()
  var funcToData = HashMap.empty[FKey,Vector[DKey]]
  var dataToFunc = HashMap.empty[DKey,Vector[FKey]]
  var fkey = 0
  var dkey = 0
  
  override def clone():Graph={
    var tmp = new Graph()
    tmp.funcKeys = funcKeys
    tmp.fKeys = fKeys
    tmp.dataKeys = dataKeys
    tmp.dKeys = dKeys
    tmp.funcToData = funcToData
    tmp.dataToFunc = dataToFunc
    tmp.fkey = fkey
    tmp.dkey = dkey
    return tmp
  }
  
  //method(Graph): Graph
  def help(){
    println("analyze() ")
    println("  Where the code is updated")
    println("  possible modifications:")
    println("     To identify types for filters and data ")
    println("     Show errors in graph")
    println("     ")
    println("process(Graph,Command):Graph ")
    println("  Where the code is updated")
    println("  possible modifications:")
    println("     command to store")
    println("     ")
    println("run() ")
    println("  Where implementation of parallelesm or lack there of")
    println("  possible modifications:")
    println("     ")
  }
  
  
  def analyze(): Boolean={ /* (Checks for warnings/errors) */
    var ret = false
    println("analyze not inplimented")
    return ret
  }
  
  def process(g:Graph,c:NodeChange):Graph={
    var ret = g.clone()
    
    println("process not inplimented")
    
    return ret
  }
  
  def run(){
    println("run not inplimented")
  }
  
  
  
  def connectNode(f:FKey, d:DKey) : Boolean = {
    var ret = false
    
    return ret
  }
  def connectNode(d:DKey, f:FKey) : Boolean = {
    var ret = false
    
    return ret
  }
  def disconnectNodes(f:FKey, d:DKey) : Boolean = {
    var ret = false
    
    return ret
  }
  def disconnectNodes(d:DKey, f:FKey) : Boolean = {
    var ret = false
    
    return ret
  }

  def printNodes(): String={ /* {Key, NodeType } */
    var ret = ""
    
    for(i <- 0 until fKeys.length){
      ret += fKeys(i).key + ":" + funcKeys(fKeys(i)).t + "/n"
    }
    for(i <- 0 until dKeys.length){
      ret += dKeys(i).key + ":" + "DataNode" + "/n"
    }
    
    return ret
  }

  def printConnections(): String={ /* fKey : connectedNodes, */
    var ret = ""
    
    
    for(i <- 0 until fKeys.length){
      fKeys(i).key + ":"
    }
    for(i <- 0 until dKeys.length){
      dKeys(i).key + ":"
    }
    
    return ret
  }
  

  def removeNode(f:FKey): Boolean = {
    var ret = false
    // must delete associated DataNodes
    
    
    // delete the actual node
    
    
    return ret
  }
  def removeNode(f:String): Boolean = {
    var ret:FKey = null
    // find the fkey
    for(i <- 0 until fKeys.length){
      if(fKeys(i).key == f) ret = fKeys(i)
    }
    return removeNode(ret)
  }
  
  def addListSouceNode(s:Double, e:Double, di:Double):Pair[FKey,DKey]={
    var fKey = new FKey("f"+fkey)
    var n:Function = new ListSource(s,e,di,fKey)
    funcKeys += (fKey -> n)
    fkey+=1
    
    var dKey = new DKey("d"+dkey)
    var d:DataStore = new DataStore(dKey)
    
    dataKeys += (dKey -> d)
    dkey+=1
    var out:Vector[DKey] = Vector.empty
    out :+ dKey
    funcToData += (fKey -> out )
    return new Pair(fKey,dKey)
  }
  def addListSouceNode(s:Double, e:Double, di:Double,fk:String,dk:String):Pair[FKey,DKey]={
    var fKey = new FKey(fk)
    var n:Function = new ListSource(s,e,di,fKey)
    funcKeys += (fKey -> n)
    
    var dKey = new DKey(dk)
    var d:DataStore = new DataStore(dKey)
    
    dataKeys += (dKey -> d)
    var out:Vector[DKey] = Vector.empty
    out :+ dKey
    funcToData += (fKey -> out )
    return new Pair(fKey,dKey)
  }
  
  def addFunctionFilter(s:String):Pair[FKey,DKey]={
    var fKey = new FKey("f"+fkey)
    var n:Function = new FunctionFilter(s,fKey)
    //n.c = new SingleMap(, new FKey(""))
    funcKeys += (fKey -> n)
    fkey+=1
    
    var dKey = new DKey("d"+dkey)
    var d:DataStore = new DataStore(dKey)
    dataKeys += (dKey -> d)
    dkey+=1
    var out:Vector[DKey] = Vector.empty
    out :+ dKey
    funcToData += (fKey -> out)
    return new Pair(fKey,dKey)
  }
  def addFunctionFilter(s:String,fk:String,dk:String):Pair[FKey,DKey]={
    var fKey = new FKey(fk)
    var n:Function = new FunctionFilter(s,fKey)
    //n.c = new SingleMap(, new FKey(""))
    funcKeys += (fKey -> n)
    
    var dKey = new DKey(dk)
    var d:DataStore = new DataStore(dKey)
    dataKeys += (dKey -> d)
    var out:Vector[DKey] = Vector.empty
    out :+ dKey
    funcToData += (fKey -> out)
    return new Pair(fKey,dKey)
  }
  
  
  def addPrintSkink():FKey={
    var fKey = new FKey("f"+fkey)
    var n:Function = new PrintSink(fKey)
    
    //n.c = new Fnode(, fKey)
    funcKeys += (fKey -> n)
    fkey+=1
    
    return fKey
  }
  def addPrintSkink(fk:String):FKey={
    var fKey = new FKey(fk)
    var n:Function = new PrintSink(fKey)
    
    //n.c = new Fnode(, fKey)
    funcKeys += (fKey -> n)
    
    return fKey
  }
  
  
  def connectNodes(input:DKey, output:FKey){
    var tmp = dataToFunc.get(input)
    var t = tmp.getOrElse(null)
    if(t != null){
      t :+ output
      dataToFunc += (input -> t)
    }else{
      t = Vector.empty
      t :+ output
      dataToFunc += (input -> t)
    }
  }
  def connectNodes(input:String, output:String){
    var i = getDKey(input);
    var tmp = dataToFunc.get(i)
    var t = tmp.getOrElse(null)
    if(t != null){
      t :+ output
      dataToFunc += (i -> t)
    }else{
      t = Vector.empty
      t :+ output
      dataToFunc += (i -> t)
    }
  }
  
  
  private def getDKey(s:String):DKey={
    var ret:DKey = null
    for(i <- 0 until dKeys.length){
      if(dKeys(i).key == s) ret = dKeys(i)
    }
    return ret
  }
  
  private def getFKey(s:String):FKey={
    var ret:FKey = null
    for(i <- 0 until fKeys.length){
      if(fKeys(i).key == s) ret = fKeys(i)
    }
    return ret
  }
  
}