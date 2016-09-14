package util

import sequential.SeqNode



class MyGraph() {
  // need to figure out how to abstract for different node types
  
  var nodeTemplate:Node = new SeqNode( new DataStore(new DKey("")) )
  
  var FuncKeys = scala.collection.mutable.Map[FKey,Node]()
  var DataKeys = scala.collection.mutable.Map[DKey,Node]()
  
  var FuncToData = scala.collection.mutable.Map[FKey,DKey]()
  var DatatoFunc = scala.collection.mutable.Map[DKey,Vector[FKey]]()
  
  var nodes:Vector[Node] = Vector[Node]()
  var key = 0;
  
  def addListSouceNode(s:Double, e:Double, di:Double):Key={
    var n:Node = nodeTemplate.copy()
    var fKey = new FKey(key+"")
    n.c = new Fcon(new ListSource(s,e,di), new FKey(""))
    FuncKeys+(fKey -> n)
    key+=1
    
    var dKey = new DKey(key+"")
    var d:Node = nodeTemplate.copy()
    d.c = new DataStore(dKey)
    d.addInput(n)
    n.addOutput(d)
    DataKeys+(dKey -> d)
    key+=1
    
    FuncToData + (fKey -> dKey)
    nodes = nodes :+ n
    return fKey
  }
  
  def addFunctionNode():Key={
    var n:Node = nodeTemplate.copy()
    var fKey = new FKey(key+"")
    //n.c = new Fnode(, fKey)
    FuncKeys+(fKey -> n)
    key+=1
    
    var dKey = new DKey(key+"")
    var d:Node = nodeTemplate.copy()
    d.c = new DataStore(dKey)
    d.addInput(n)
    n.addOutput(d)
    DataKeys+(dKey -> d)
    key+=1
    
    FuncToData + (fKey -> dKey)
    nodes = nodes :+ n
    return fKey
  }
  
  def addSingleMapNode():Key={
    var n:Node = nodeTemplate.copy()
    var fKey = new FKey(key+"")
    //n.c = new SingleMap(, new FKey(""))
    FuncKeys+(fKey -> n)
    key+=1
    
    var dKey = new DKey(key+"")
    var d:Node = nodeTemplate.copy()
    d.c = new DataStore(dKey)
    d.addInput(n)
    n.addOutput(d)
    DataKeys+(dKey -> d)
    key+=1
    
    FuncToData + (fKey -> dKey)
    nodes = nodes :+ n
    return fKey
  }
  
  def addPrintSkink():Key={
    var n:Node = nodeTemplate.copy()
    var fKey = new FKey(key+"")
    //n.c = new Fnode(, fKey)
    FuncKeys+(fKey -> n)
    key+=1
    
    nodes = nodes :+ n
    return fKey
  }
  
  def connectNodes(input:Key, output:Key){
    var in:Node = FuncKeys(input.asInstanceOf[FKey])
    var out:Node = FuncKeys(output.asInstanceOf[FKey])
    var inData = FuncToData(input.asInstanceOf[FKey])
    
    DataKeys(inData).addOutput(out)
    out.addInput(DataKeys(inData))
    DatatoFunc + (inData->output)
  }
  
}