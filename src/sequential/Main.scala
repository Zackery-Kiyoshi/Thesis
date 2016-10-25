package sequential

import util._

object Main {
  
  
  def simpleListTest():Graph={
    
    var func1 = "(2+3)*3"
    
    var graph1:SequentialGraph = SequentialGraph()
    
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    graph1 = graph1.addFilter(new PrintSink(), "ps1")
    
    graph1 = graph1.connectNodes("data0", "ps1")
    
    //graph1.printConnections()
    
    //graph1.analyze()
    graph1.run()
    
    // making a change
//    /*
    graph1 = graph1.replace("ls1",new ListSource(0,20,2))
    println("Modified:")
    graph1.run()
//    */
    return graph1
  }
  
  def simpleFuncTest():Graph={
    
    var func1 = "(2+3)*3"
    
    var graph1:SequentialGraph = SequentialGraph()
    
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    graph1 = graph1.addFilter(new FunctionFilter(func1), "fn1")
    graph1 = graph1.addFilter(new PrintSink(), "ps1")
    
    graph1 = graph1.connectNodes("data0", "fn1")
    graph1 = graph1.connectNodes("data1", "ps1")
    
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
    simpleListTest()
    //simpleFuncTest()
    
  }
      
}