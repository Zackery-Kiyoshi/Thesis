package sequential

object Main {
  
  
  
  def main(args: Array[String]): Unit = {
    
    
    var nodeSet1:Array[SeqNode] = new Array(0)
    var graph1:Array[Array[Boolean]] = new Array(1)(1)
  
    var scheduler1 = new Scheduler(nodeSet1,graph1)
    scheduler1.makeSchedule()
    scheduler1.run()
    
    
  }
      
}