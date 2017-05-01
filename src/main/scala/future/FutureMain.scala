package future

import util._

object FutureMain {
  
  def simpleListTest():ParallelGraph={
    var func1 = "(2+3)*3"
    var graph1:FutureGraph = FutureGraph(2,false)
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    graph1 = graph1.addFilter(new PrintSink(), "ps1")
    graph1 = graph1.connectNodes("ls1", "ps1")
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
  
  def simpleFuncTest():ParallelGraph={
    
    var func1 = "(2+3)*3"
    
    var graph1:FutureGraph = FutureGraph()
    
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    graph1 = graph1.addFilter(new FunctionFilter(func1), "fn1")
    graph1 = graph1.addFilter(new PrintSink(), "ps1")
    
    graph1 = graph1.connectNodes("ls1", "fn1")
    graph1 = graph1.connectNodes("fn1", "ps1")
    
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
    var graph1:FutureGraph = FutureGraph(2,false)
    
    graph1 = graph1.addFilter(new ListSource(1,2,1), "ls1")
    graph1 = graph1.addFilter(new ListSource(10,11,1), "ls10")
    graph1 = graph1.addFilter(new ListSource(10,11,1), "ls10.2")
    
    graph1 = graph1.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]+i",Map(("i"->1.0))), "f100" )
    graph1 = graph1.addFilter(new PrintSink(), "ps1")
    graph1 = graph1.connectNodes("f10", "f100")
    graph1 = graph1.connectNodes("f10.2", "f100")
    graph1 = graph1.connectNodes("f100", "ps1")
    graph1.run()
    
    println("Modify to have multiple inputs")
    graph1 = graph1.addFilter(new FunctionFilter("x[0][0][0]+x[1][0][0]+x[2][0][0]"), "f111")
    graph1 = graph1.connectNodes("f1", "f111").connectNodes("f10", "f111").connectNodes("f100", "f111")
    //graph1 = graph1.disconnectNodes("d100", "ps1").connectNodes("d111", "ps1")
    graph1.run()
    
  }
  
  
  def linearFiltTest(){
    var graph1:FutureGraph = FutureGraph(2,false)
    
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
    var graph1:FutureGraph = FutureGraph()
    
    graph1 = graph1.addFilter(new ListSource(0,10,1), "ls1")
    
    graph1 = graph1.addFilter(new InputCollectionFilter())
    
    
    
  }
  
  def fib(i:Int):FutureGraph={
    var graph1:FutureGraph = FutureGraph(2,false)
    for(j <- 0 to i){
      if(j==1){
        graph1 = graph1.addFilter(new ListSource(1,2,1), "l1")
      }else if(j==0){
        graph1 = graph1.addFilter(new ListSource(0,1,1), "l0")
      }else{
        graph1 = graph1.addFilter(new FunctionFilter("x[0][0][0]+x[1][0][0]"),"l"+j)
        val t1 = j-1
        val t2 = j-2
        graph1 = graph1.connectNodes("l"+t1,"l"+j)
        graph1 = graph1.connectNodes("l"+t2,"l"+j)
      }
    }
    graph1 = graph1.addFilter(new PrintSink(), "ps")
    graph1 = graph1.connectNodes("l"+i,"ps")
    return graph1
  }
  
  def fibRec(i:Int, g:FutureGraph = FutureGraph(2,false), cur:Int = 0):FutureGraph={
    // being able to combine graphs
    // no actually recursive yet
    var graph1 = g
    for(j <- 0 to i){
      if(j==1){
        graph1 = graph1.addFilter(new ListSource(1,2,1), "l1")
      }else if(j==0){
        graph1 = graph1.addFilter(new ListSource(0,1,1), "l0")
      }else{
        graph1 = graph1.addFilter(new FunctionFilter("x[0][0][0]+x[1][0][0]"),"l"+j)
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
  
  def minmaxTest(){
    var graph1 = FutureGraph(2,false)
    graph1 = graph1.addFilter(new ListSource(0,21,2), "ls1")
    //graph1 = graph1.addFilter(new PrintSink(), "ps1").connectNodes("d1","ps1")
    
    graph1 = graph1.addFilter(new MinFilter( (x:DataElement,y:DataElement) => x(0) > y(0) ),"noImin").connectNodes("ls1", "noImin")
    //graph1 = graph1.addFilter(new PrintSink(), "ps1").connectNodes("noIminD","ps1")
    
    graph1 = graph1.addFilter(new MinFilter( (x:DataElement,y:DataElement) => x(0) > y(0), 5 ),"5min").connectNodes("ls1", "5min")
    //graph1 = graph1.addFilter(new PrintSink(), "ps1").connectNodes("5minD","ps1")
    
    graph1 = graph1.addFilter(new MaxFilter( (x:DataElement,y:DataElement) => x(0) > y(0) ),"noImax").connectNodes("ls1", "noImax")
    graph1 = graph1.addFilter(new PrintSink(), "ps1").connectNodes("noImax","ps1")
    
    graph1 = graph1.addFilter(new MaxFilter( (x:DataElement,y:DataElement) => x(0) > y(0), 5 ),"5max").connectNodes("ls1", "5max")
    graph1 = graph1.addFilter(new PrintSink(), "ps1").connectNodes("5max","ps1")
    
    graph1.run()
    
  }
  
  def sortTest(){
    var graph1 = FutureGraph(2,false)
    graph1 = graph1.addFilter(new ListSource(0,21,2), "ls1")
    //graph1 = graph1.addFilter(new PrintSink(), "ps1").connectNodes("d1","ps1")
    graph1 = graph1.addFilter(new SortFilter( (x:DataElement,y:DataElement) => x(0) > y(0)), "sf1").connectNodes("ls1","sf1")
    graph1 = graph1.addFilter(new PrintSink(), "ps2").connectNodes("sf1","ps2")
   
    graph1.run()
  }
  
  def fileInputTest(){
    var graph1 = FutureGraph(2,false)
    graph1 = graph1.addFilter(new FileSource("test.txt"), "fs1")
    graph1 = graph1.addFilter(new PrintSink(), "ps1").connectNodes("fs1","ps1")
    
    graph1.run()
  }
  
  def funcTest(){
    var g1 = FutureGraph()
    g1 = g1.addFilter(new ListSource(0,11,1), "s1")
    g1 = g1.addFilter(new ListSource(10,21,1), "s2")
    g1 = g1.addFilter(new FunctionFilter("x[0][0]+x[1][0]"),"l").connectNodes("s1","l").connectNodes("s2","l")
    g1 = g1.addFilter(new PrintSink(), "ps3").connectNodes("l","ps3")
    g1.run()
  }
  
  def simpleTest(){
    var g1 = FutureGraph()
    g1 = g1.addFilter(new ListSource(0,11,1), "s1")
    g1 = g1.addFilter(new SortFilter( (x:DataElement,y:DataElement) => x(0) > y(0)), "sfg").connectNodes("s1","sfg")
    g1 = g1.addFilter(new SortFilter( (x:DataElement,y:DataElement) => x(0) < y(0)), "sfl").connectNodes("s1","sfl")
    g1 = g1.addFilter(new FunctionFilter("x[0][0]*x[1][0]"),"times").connectNodes("sfg","times").connectNodes("sfl","times")
    g1 = g1.addFilter(new PrintSink(), "ps1").connectNodes("times","ps1")
    g1.run()
  }
  
  def simpleTestChain(){
    FutureGraph()
    .addFilter(new ListSource(0,11,1), "s1")
    .addFilter(new SortFilter( (x:DataElement,y:DataElement) => x(0) > y(0)), "sfg").connectNodes("s1","sfg")
    .addFilter(new SortFilter( (x:DataElement,y:DataElement) => x(0) < y(0)), "sfl").connectNodes("s1","sfl")
    .addFilter(new FunctionFilter("x[0][0]*x[1][0]"),"times").connectNodes("sfg","times").connectNodes("sfl","times")
    .addFilter(new PrintSink(), "ps1").connectNodes("times","ps1")
    .run()
  }
  
  
  def main(args: Array[String]): Unit = {
    //testHelp()
    simpleListTest()
    simpleFuncTest()
    //linearFiltTest()
    //singFuncTest()
    fib(6).run()
    minmaxTest()
    sortTest()
    //fileInputTest()
    funcTest()
    
    simpleTest()
    simpleTestChain
		//println("Waiting for an input.")
		//io.StdIn.readLine
  }
      
}
