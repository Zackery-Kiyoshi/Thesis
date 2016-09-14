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
  var dataKeys = HashMap.empty[DKey,DataStore]
  var funcToData = HashMap.empty[FKey,Vector[DKey]]
  var dataToFunc = HashMap.empty[DKey,Vector[FKey]]
  var key = 0;
  
  //method(Graph): Graph
  def process(){
    
  }
  
  def analyze(): Boolean={ /* (Checks for warnings/errors) */
    var ret = false
    
    return ret
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
    
    return ret
  }

  

  def removeNode(f:FKey): Boolean = {
    var ret = false
    
    return ret
  }
  def removeNode(d:DKey): Boolean = {
    var ret = false
    
    return ret
  }
  
  
  def addListSouceNode(s:Double, e:Double, di:Double):FKey={
    var fKey = new FKey(key+"")
    var n:Function = new ListSource(s,e,di,fKey)
    funcKeys += (fKey -> n)
    key+=1
    
    var dKey = new DKey(key+"")
    var d:DataStore = new DataStore(dKey)
    
    dataKeys += (dKey -> d)
    key+=1
    var out:Vector[DKey] = Vector.empty
    out :+ dKey
    funcToData += (fKey -> out )
    return fKey
  }
  
  def addSingleMapNode(f: Double ⇒ Double):FKey={
    var fKey = new FKey(key+"")
    var n:Function = new SingleMap(f,fKey)
    //n.c = new SingleMap(, new FKey(""))
    funcKeys += (fKey -> n)
    key+=1
    
    var dKey = new DKey(key+"")
    var d:DataStore = new DataStore(dKey)
    dataKeys += (dKey -> d)
    key+=1
    var out:Vector[DKey] = Vector.empty
    out :+ dKey
    funcToData += (fKey -> out)
    return fKey
  }
  
  def addPrintSkink():FKey={
    var fKey = new FKey(key+"")
    var n:Function = new PrintSink(fKey)
    
    //n.c = new Fnode(, fKey)
    funcKeys += (fKey -> n)
    key+=1
    
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
  
}