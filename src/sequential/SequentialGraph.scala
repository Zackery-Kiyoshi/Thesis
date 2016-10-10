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
  
  
  def process(g:SequentialGraph):SequentialGraph={
    var ret = g.clone()
    
    println("process not inplimented")
    
    return ret
  }
  
  /*
  override def run(){
    
  }
  */
  
}