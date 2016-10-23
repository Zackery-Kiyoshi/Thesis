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

 
  override def connectNodes(d: DKey, f: FKey): SequentialGraph = {
    var ret = super.connectNodes(d,f)
    toSequentialGraph(ret)
  }
  override def connectNodes(d:String, f:String): SequentialGraph = {
    var ret = super.connectNodes(d,f)
    toSequentialGraph(ret)
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
    new SequentialGraph(g.filtKeys,g.fKeys,g.dataKeys,g.dKeys,g.funcToData,g.dataToFunc,g.nextfkey,g.nextdkey)
  }
  
  
  // does this need to be overwritten or could it happen when connecting a node/modifying a node???
  // also does it really need to be parallized???
  override def analyze():Boolean={
    var ret = true
    
    return ret
  }
  
  
  
//  /*
  override def run(){
    
    // analyze first to make sure there are no mistakes???
    if(!analyze() ){
      println("There is an error in the graph please fix before running again")
      return
    }
    
    
    var roots:List[FKey] = List()
    // need to find roots
    for(i <- 0 until fKeys.length){
      //println(i + ":" + fKeys(i).key)
      roots = roots :+ fKeys(i)
    }
    for(d <- 0 until dKeys.length){
      // remove the
      for(f <- fKeys){
        if(dataToFunc(dKeys(d)).contains(f)){
          var tmpRoots:List[FKey] = List()
          for(k <- roots){
            if(k != f) tmpRoots = tmpRoots :+ k
          }
          roots = tmpRoots
        }
      }
    }
    /*
    println("Roots:")
    for(i <- roots) println(" "+i.key)
//    */
    var curRoot = roots.length - 1
    var todo:List[FKey] = List()
    var needNewPath = false
    var data:Vector[DataStore] = Vector.empty
    var rezData:Vector[DataStore] = Vector.empty
    var running = true
    // need to run each loop
    while(running){
      var curNode:Filter = null
      if(needNewPath){
        curRoot-=1
        if(curRoot >=0){
          curNode = filtKeys(roots(curRoot))
          needNewPath = false
        }
        else running = false
      }
      else {
        curNode = filtKeys(todo(0))
      }
      // need to get the correct input data
      rezData = curNode.apply(data)
      for(d<-funcToData(todo(0))){
        // update dataStores
        dataKeys + (d->rezData)
        for(f <- dataToFunc(d)){
          todo = todo :+ (f)
        }
      }
      
      
      
      if(!needNewPath) todo = todo.tail
      
      if(todo.isEmpty)needNewPath=true
    }
    
    
  }
//  */
  
}


object SequentialGraph {
  def apply(): SequentialGraph = {
    new SequentialGraph( Map[FKey, Filter](), List[FKey](), Map[DKey, DataStore](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), 0, 0)
  }
  
}
