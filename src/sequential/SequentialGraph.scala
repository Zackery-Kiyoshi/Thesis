package sequential

import util._

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
