package sequential

import util._

object Main {
  
  
  def simpleFuncTest():Graph={
    
    var func1 = "(2+3)*3"
    
    var graph1:SequentialGraph = new SequentialGraph()
    
    var n1 = graph1.addListSouceNode(0,10,1)
    //var n2 = graph1.addFunctionFilter(func1)
    var n3 = graph1.addPrintSkink()
    var n4 = graph1.addPrintSkink()
    
    //graph1.printNodes()
    
    // connect listSource to FunctionFilter
   // graph1.connectNodes(n1._2,n2._1)
    // connect FunctionFilter to PrintSink1
   // graph1.connectNodes(n2._2,n3)
    // connect listSource to printSink2
    graph1.connectNodes(n1._2,n4)
    
    
    graph1.printConnections()
    
    //graph1.analyze()
    println("start")
    graph1.run()
    println("end")
    
    
    // making a change
    
    var change1 = new NodeChange(null)
    
    graph1.process(graph1,change1)
    
    return graph1
  }
  
  
  def testHelp():Graph={
    var g:Graph = new Graph();
    
    g.help()
    
    return g
  }
  
  
  def main(args: Array[String]): Unit = {
    //testHelp()
    simpleFuncTest()
    
    
  }
      
}