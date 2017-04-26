package tests

import org.scalameter._

import sequential.SequentialGraph
import futures.FutureGraph
import util._
import java.io._

object TestNBAFut {
  def main(args: Array[String]): Unit = {

    val standardConfig = config(
      Key.exec.minWarmupRuns -> 50,
      Key.exec.maxWarmupRuns -> 100,
      Key.exec.benchRuns -> 500,
      Key.verbose -> false
      ) withWarmer (new org.scalameter.Warmer.Default)
    
    println("Heap size:" + Runtime.getRuntime().maxMemory())

    var g = FutureGraph(1,true)
    var timeInitial = System.nanoTime()
    g = g.addFilter(new csvFileSource("nba2015historical_projections.csv"), "ls1")

    var idx = 6
    var dx =  0.655715297-0.030613137/32
    var start = 0.030613137
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
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a2").connectNodes("f3","a2").connectNodes("f4","a2")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a3").connectNodes("f5","a3").connectNodes("f6","a3")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a4").connectNodes("f7","a4").connectNodes("f8","a4")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a5").connectNodes("f9","a5").connectNodes("f10","a5")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a6").connectNodes("f11","a6").connectNodes("f12","a6")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a7").connectNodes("f13","a7").connectNodes("f14","a7")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a8").connectNodes("f15","a8").connectNodes("f16","a8")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a9").connectNodes("f17","a9").connectNodes("f18","a9")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a10").connectNodes("f19","a10").connectNodes("f20","a10")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a11").connectNodes("f21","a11").connectNodes("f22","a11")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a12").connectNodes("f23","a12").connectNodes("f24","a12")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a13").connectNodes("f25","a13").connectNodes("f26","a13")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a14").connectNodes("f27","a14").connectNodes("f28","a14")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a15").connectNodes("f29","a15").connectNodes("f30","a15")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "a16").connectNodes("f31","a16").connectNodes("f32","a16")

    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "b1").connectNodes("a1","b1").connectNodes("a2","b1")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "b2").connectNodes("a3","b2").connectNodes("a4","b2")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "b3").connectNodes("a5","b3").connectNodes("a6","b3")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "b4").connectNodes("a7","b4").connectNodes("a8","b4")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "b5").connectNodes("a9","b5").connectNodes("a10","b5")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "b6").connectNodes("a11","b6").connectNodes("a12","b6")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "b7").connectNodes("a13","b7").connectNodes("a14","b7")
    g = g.addFilter(new FunctionFilter("x[0][0]["+idx+"]*x[1][0]["+idx+"]"), "b8").connectNodes("a15","b8").connectNodes("a16","b8")
    
    //g = g.addFilter(new PrintSink(), "pf1").connectNodes("f1","pf1")
    g = g.addFilter(new KMeans(2),"k1").connectNodes("f1","k1").connectNodes("ls1","k1")
    g = g.addFilter(new KMeans(3),"k2").connectNodes("f2","k2").connectNodes("ls1","k2")
    g = g.addFilter(new KMeans(4),"k3").connectNodes("f3","k3").connectNodes("ls1","k3")
    g = g.addFilter(new KMeans(5),"k4").connectNodes("f4","k4").connectNodes("ls1","k4")
    g = g.addFilter(new KMeans(5),"k5").connectNodes("ls1","k5")
    
    
    g = g.addFilter(new KMeans(6),"km1").connectNodes("ls1","km1").connectNodes("b1","km1")
    g = g.addFilter(new KMeans(6),"km2").connectNodes("ls1","km2").connectNodes("b1","km2")
    g = g.addFilter(new KMeans(6),"km3").connectNodes("ls1","km3").connectNodes("b1","km3")
    g = g.addFilter(new KMeans(6),"km4").connectNodes("ls1","km4").connectNodes("b1","km4")
    g = g.addFilter(new KMeans(6),"km5").connectNodes("ls1","km5").connectNodes("b1","km5")
    g = g.addFilter(new KMeans(6),"km6").connectNodes("ls1","km6").connectNodes("b1","km6")
    g = g.addFilter(new KMeans(6),"km7").connectNodes("ls1","km7").connectNodes("b1","km7")
    g = g.addFilter(new KMeans(6),"km8").connectNodes("ls1","km8").connectNodes("b1","km8")
    
    
    /*
    //g = g.addFilter(new LinearFitFilter("x[0][0][0]"), "lf").connectNodes("b1", "lf")
    g = g.connectNodes("b2", "lf")
    g = g.connectNodes("b3", "lf")
    g = g.connectNodes("b4", "lf")
    g = g.connectNodes("b5", "lf")
    g = g.connectNodes("b6", "lf")
    g = g.connectNodes("b7", "lf")
    g = g.connectNodes("b8", "lf")
//    */
    
    //g = g.addFilter(new PrintSink(), "ps1")
    var time1 = System.nanoTime()
    println("construction time:" + (time1 - timeInitial))
    timeInitial = System.nanoTime()
    var time = standardConfig measure {
      var tg = g.run()
      println(tg.getData("f1"))
    }
    time1 = System.nanoTime()
    println("test run time:" + (time1 - timeInitial))
    println("run time:" + time)
  }
}