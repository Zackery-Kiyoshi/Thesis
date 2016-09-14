package util

import scala.collection.mutable.Map._


class FKey(val key:String){
  def equals(s:FKey):Boolean={
    return key == s.key
  }
  def equals(s:String):Boolean={
    return key == s
  }
}

class DKey(val key:String){
  def equals(s:DKey):Boolean={
    return key == s.key
  }
  def equals(s:String):Boolean={
    return key == s
  }
}


class Graph {
  var funcKeys:Map:FKey,Function)
  var dataKeys:Map[DKey,DataStore]
  var funcToData:Map[FKey,Vector[DKey]]
  var datatoFunc:Map[DKey,Vector[FKey]]

  def Graph(){
    funcKeys = new Map(new FKey("")=>new Function())
    dataKeys = Map(new DKey(" ") => new DataStore(new DKey("1")))
    funcToData = Map(new FKey(" ") => new Vector[new DKey(" " )])
    datatoFunc = Map(new DKey(" ") => new Vector[new FKey(" " )])
  }
  
  //method(Graph): Graph
  def process(){
    
  }
  def analyze(): Boolean={ /* (Checks for warnings/errors) */
    var ret = false
    
    return ret
  }
  
  def connectNode(FKey, DKey) : Boolean = {
    var ret = false
    
    return ret
  }
  def connectNode(DKey, FKey) : Boolean = {
    var ret = false
    
    return ret
  }
  def disconnectNodes(FKey, DKey) : Boolean = {
    var ret = false
    
    return ret
  }
  def disconnectNodes(DKey, FKey) : Boolean = {
    var ret = false
    
    return ret
  }

  def printNodes(): String={ /* {Key, NodeType } */
    var ret = ""
    
    return ret
  }

  def createFileInput( String, Params) : Boolean

+ removeNode(FKey): Boolean
+ removeNode(DKey): Boolean
}