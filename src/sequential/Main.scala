package sequential

import util._

object Main {
  
  
  def simpleListTest():Graph={
    
    var func1 = "(2+3)*3"
    
    var graph1:SequentialGraph = SequentialGraph(false)
    
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    graph1 = graph1.addFilter(new PrintSink(), "ps1")
    
    graph1 = graph1.connectNodes("data0", "ps1")
    //println("HERE")
    //graph1.printConnections()
    
    //graph1.analyze()
    graph1.run()
    
    // making a change
//    /*
    graph1 = graph1.replace("ls1",new ListSource(0,20,2))
    println("Modified:")
    graph1.run()
    println("test")
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
    var graph2 = graph1
    
    graph2 = graph2.replace("fn1", new FunctionFilter("3"))
    graph2.run()
    
    return graph1
  }
  
  def linearFiltTest(){
    var graph1:SequentialGraph = SequentialGraph()
    
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    graph1 = graph1.addFilter(new LinearFitFilter())
    
    
    
  }
  
  def inputCollecitonFiltTest(){
    var graph1:SequentialGraph = SequentialGraph()
    
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    graph1 = graph1.addFilter(new InputCollectionFilter())
    
    
    
  }
  
  def groupNumberingFiltTest(){
    
  }
  
  def main(args: Array[String]): Unit = {
    //testHelp()
    simpleListTest()
    //simpleFuncTest()
    
  }
      
}