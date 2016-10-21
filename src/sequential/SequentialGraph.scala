package sequential

import util._
import scala.language.implicitConversions





class SequentialGraph private(
  override val filtKeys: Map[FKey, Filter],
  override val fKeys: List[FKey],
  override val dataKeys: Map[DKey, DataStore],
  override val dKeys: List[DKey],
  override val funcToData: Map[FKey, Vector[DKey]],
  override val dataToFunc: Map[DKey, Vector[FKey]],
  override val nextfkey: Int,
  override val nextdkey: Int
  ) extends Graph(filtKeys,fKeys,dataKeys,dKeys,funcToData,dataToFunc,nextfkey,nextdkey){
  
  
  override def replace(fstr: String, f2: Filter): SequentialGraph = {
    var ret = super.replace(fstr,f2)
    toSequentialGraph(ret)
  }
  
  override def modify(fstr: String)(func: Filter => Filter):SequentialGraph = {
    var ret = super.modify(fstr)(func)
    toSequentialGraph(ret)
  }
  
  override def addFilter(filter:Filter, fName: String = "", dName: String = ""): SequentialGraph = {
    var ret = super.addFilter(filter, fName, dName)
    toSequentialGraph(ret)
  }

 
  override def connectNode(d: DKey, f: FKey): SequentialGraph = {
    var ret = super.connectNode(d,f)
    toSequentialGraph(ret)
  }
  override def disconnectNodes(d: DKey, f: FKey): SequentialGraph = {
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
    new SequentialGraph(g.filtKeys,g.fKeys,g.dataKeys,g.dKeys,g.funcToData,g.dataToFunc,g.nextfkey,g.nextdkey)
  }
  
  override def analyze():Boolean={
    var ret = false
    
    return ret
  }
  
  
  
//  /*
  override def run(){
    
    var roots:List[FKey] = List()
    // need to find roots
    for(i <- 0 until fKeys.length){
      //println(i + ":" + fKeys(i).key)
      roots = roots :+ fKeys(i)
    }
    for(i <- 0 until dKeys.length){
      // remove the
      for(j <- dataToFunc(dKeys(i))){
          if(roots.contains(j)){
            var tmpRoots:List[FKey] = List()
            for(k <- roots){
              if(k != j) tmpRoots = tmpRoots :+ k
            }
          }
      }
    }
    
    println("Roots:")
    for(i <- roots) println(" "+i.key)
    
    var curRoot = 0
    
    var running = true
    // need to run each loop
    while(running){
      var curNode:Filter = null
      if(curRoot>=0){
        if(curRoot < roots.length) curNode = filtKeys(roots(curRoot))
        else running = false
      }
    }
    
    
  }
//  */
  
}


object SequentialGraph {
  def apply(): SequentialGraph = {
    new SequentialGraph( Map[FKey, Filter](), List[FKey](), Map[DKey, DataStore](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), 0, 0)
  }
  
}
