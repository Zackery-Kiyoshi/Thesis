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
  
  def singFuncTest(){
    var graph1:SequentialGraph = SequentialGraph(false)
    
    graph1 = graph1.addFilter(new ListSource(1,2,1), "ls1", "d1")
    graph1 = graph1.addFilter(new ListSource(10,11,1), "ls10", "d10")
    graph1 = graph1.addFilter(new ListSource(10,11,1), "ls10.2", "d10.2")
    
    graph1 = graph1.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]+i",Map(("i"->1.0))), "f100", "d100")
    graph1 = graph1.addFilter(new PrintSink(), "ps1", "data7")
    graph1 = graph1.connectNodes("d10", "f100")
    graph1 = graph1.connectNodes("d10.2", "f100")
    graph1 = graph1.connectNodes("d100", "ps1")
    graph1.run()
    
    println("Modify to have multiple inputs")
    graph1 = graph1.addFilter(new FunctionFilter("x[0][0][0]+x[1][0][0]+x[2][0][0]"), "f111", "d111")
    graph1 = graph1.connectNodes("d1", "f111").connectNodes("d10", "f111").connectNodes("d100", "f111")
    //graph1 = graph1.disconnectNodes("d100", "ps1").connectNodes("d111", "ps1")
    graph1.run()
    
    
    
  }
  
  
  def linearFiltTest(){
    var graph1:SequentialGraph = SequentialGraph(false)
    
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls2")
    graph1 = graph1.addFilter(new LinearFitFilter(), "lf1")
    graph1 = graph1.addFilter(new PrintSink(), "ps1")
    
    graph1 = graph1.connectNodes("ls1", "lf1")
    graph1 = graph1.connectNodes("ls2", "lf1")
    graph1 = graph1.connectNodes("lf1","ps1")
    
    graph1.run()
  }
  
  def inputCollecitonFiltTest(){
    var graph1:SequentialGraph = SequentialGraph()
    
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    
    graph1 = graph1.addFilter(new InputCollectionFilter())
    
    
    
  }
  
  def fib(i:Int):SequentialGraph={
    var graph1:SequentialGraph = SequentialGraph(false)
    for(j <- 0 to i){
      if(j==1){
        graph1 = graph1.addFilter(new ListSource(1,2,1), "l1", "d1")
      }else if(j==0){
        graph1 = graph1.addFilter(new ListSource(0,1,1), "l0", "d0")
      }else{
        graph1 = graph1.addFilter(new FunctionFilter("x[0][0][0]+x[1][0][0]"),"l"+j,"d"+j)
        val t1 = j-1
        val t2 = j-2
        graph1 = graph1.connectNodes("d"+t1,"l"+j)
        graph1 = graph1.connectNodes("d"+t2,"l"+j)
      }
    }
    graph1 = graph1.addFilter(new PrintSink(), "ps")
    graph1 = graph1.connectNodes("d"+i,"ps")
    return graph1
  }
  
  def fibRec(i:Int, g:SequentialGraph = SequentialGraph(false)):SequentialGraph={
    // being able to combine graphs
    // no actually recursive yet
    var graph1 = g
    for(j <- 0 to i){
      if(j==1){
        graph1 = graph1.addFilter(new ListSource(1,2,1), "l1", "d1")
      }else if(j==0){
        graph1 = graph1.addFilter(new ListSource(0,1,1), "l0", "d0")
      }else{
        graph1 = graph1.addFilter(new FunctionFilter("x[0][0][0]+x[1][0][0]"),"l"+j,"d"+j)
        val t1 = j-1
        val t2 = j-2
        graph1 = graph1.connectNodes("d"+t1,"l"+j)
        graph1 = graph1.connectNodes("d"+t2,"l"+j)
      }
    }
    graph1 = graph1.addFilter(new PrintSink(), "ps")
    graph1 = graph1.connectNodes("d"+i,"ps")
    return graph1
  }
  
  def main(args: Array[String]): Unit = {
    //testHelp()
    //simpleListTest()
    //simpleFuncTest()
    
    //linearFiltTest()
     
    singFuncTest()
    
    fib(6).run()
    
    
    // min/max (i) forevery i elements give the min/max
    // sort filters
    
    // fix functionfilter
    // union for graphs
    
    // read from file
  }
      
}