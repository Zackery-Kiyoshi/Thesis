package sequential

import util._

class SequentialGraph extends Graph{
  
  override def clone():SequentialGraph={
    var tmp = new SequentialGraph()
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
  
  override def analyze():Boolean={
    var ret = false
    
    return ret
  }
  
  
  def process(g:SequentialGraph,c:NodeChange):SequentialGraph={
    var ret = g.clone()
    
    //println("process not inplimented")
    
    
    
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
      var curNode:Function = null
      if(curRoot>=0){
        if(curRoot < roots.length) curNode = funcKeys(roots(curRoot))
        else running = false
      }
    }
    
    
  }
//  */
  
}