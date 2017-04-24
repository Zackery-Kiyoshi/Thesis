package tests

import org.scalameter._

import sequential.SequentialGraph
import futures.FutureGraph
import util._
import java.io._

object TestFutLin {
  def main(args: Array[String]): Unit = {

    val standardConfig = config(
      Key.exec.minWarmupRuns -> 5,
      Key.exec.maxWarmupRuns -> 10,
      Key.exec.benchRuns -> 50,
      Key.verbose -> false
      ) withWarmer (new org.scalameter.Warmer.Default)
    
    println("Heap size:" + Runtime.getRuntime().maxMemory())
    
    var g = FutureGraph(1,false)
    var timeInitial = System.nanoTime()
    g = g.addFilter(new csvFileSource("US_births_2000-2014_SSA.csv"), "ls1")
    
    var idx = 4
    var dx = 16081.0-5728.0/32
    var start = 5728.0
    g = g.addFilter(new MinFilter( (d1:DataElement, d2:DataElement)=>d1(idx)>d2(idx)  ),"min").connectNodes("ls1","min")
    //g = g.addFilter(new PrintSink(), "pMin").connectNodes("min", "pMin")
    g = g.addFilter(new MaxFilter( (d1:DataElement, d2:DataElement)=>d1(idx)>d2(idx)  ),"max").connectNodes("ls1","max")
    //g = g.addFilter(new PrintSink(), "pMax").connectNodes("max", "pMax")
//    /*
    
    g = g.addFilter(new FilterBy((d: DataElement) => { d(3) == 1}), "f1.3").connectNodes("ls1", "f1.3")
    //g= g.addFilter(new PrintSink(),"p3").connectNodes("f1.3","p3")
    
    var x = start + dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) < start + dx }), "f1").connectNodes("ls1", "f1")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f2").connectNodes("ls1", "f2")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f3").connectNodes("ls1", "f3")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f4").connectNodes("ls1", "f4")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f5").connectNodes("ls1", "f5")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f6").connectNodes("ls1", "f6")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f7").connectNodes("ls1", "f7")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f8").connectNodes("ls1", "f8")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f9").connectNodes("ls1", "f9")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f10").connectNodes("ls1", "f10")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f11").connectNodes("ls1", "f11")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f12").connectNodes("ls1", "f12")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f13").connectNodes("ls1", "f13")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f14").connectNodes("ls1", "f14")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f15").connectNodes("ls1", "f15")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f16").connectNodes("ls1", "f16")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f17").connectNodes("ls1", "f17")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f18").connectNodes("ls1", "f18")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f19").connectNodes("ls1", "f19")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f20").connectNodes("ls1", "f20")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f21").connectNodes("ls1", "f21")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f22").connectNodes("ls1", "f22")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f23").connectNodes("ls1", "f23")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f24").connectNodes("ls1", "f24")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f25").connectNodes("ls1", "f25")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f26").connectNodes("ls1", "f26")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f27").connectNodes("ls1", "f27")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f28").connectNodes("ls1", "f28")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f29").connectNodes("ls1", "f29")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f30").connectNodes("ls1", "f30")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x && d(idx) < x + dx }), "f31").connectNodes("ls1", "f31")
    x += dx
    g = g.addFilter(new FilterBy((d: DataElement) => { d(idx) >= x }), "f32").connectNodes("ls1", "f32")

    //g = g.addFilter(new LinearFitFilter("x[0][0][0]"), "lf").connectNodes("ls1", "lf")
    
    //g = g.addFilter(new FunctionFilter("x[0][0]"), "a1").connectNodes("f1","a1").connectNodes("f2","a1")
    
    ///*
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a1").connectNodes("f1","a1").connectNodes("f2","a1")
    
    /*
    //g = g.addFilter(new LinearFitFilter("x[0][0][0]"), "lf").connectNodes("b1", "lf")
    g = g.connectNodes("b2", "lf")
    g = g.connectNodes("b3", "lf")
    g = g.connectNodes("b4", "lf")
    g = g.connectNodes("b5", "lf")
    g = g.connectNodes("b6", "lf")
    g = g.connectNodes("b7", "lf")
    g = g.connectNodes("b8", "lf")
    //*/
//     */
//     */
//     */
    
    //g = g.addFilter(new PrintSink(), "ps1")
    var time1 = System.nanoTime()
    println("construction time:" + (time1 - timeInitial))
    timeInitial = System.nanoTime()
    var time = standardConfig measure {
      g.run()
    }
    time1 = System.nanoTime()
    println("test run time:" + (time1 - timeInitial))
    println("run time:" + time)
  }
}
