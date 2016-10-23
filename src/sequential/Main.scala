package sequential

import util._

object Main {
  
  
  def simpleFuncTest():Graph={
    
    var func1 = "(2+3)*3"
    
    var graph1:SequentialGraph = SequentialGraph()
    
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    graph1 = graph1.addFilter(new PrintSink(), "ps1")
    
    graph1 = graph1.connectNodes("data0", "ps1")
    
    //graph1.printConnections()
    
    //graph1.analyze()
    println("start")
    graph1.run()
    println("end")
    
    
    // making a change
    
    
    return graph1
  }
  
  
  def main(args: Array[String]): Unit = {
    //testHelp()
    simpleFuncTest()
    
    
  }
      
}