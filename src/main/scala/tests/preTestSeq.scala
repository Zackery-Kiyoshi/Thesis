package tests

import org.scalameter._

import sequential.SequentialGraph
import futures.FutureGraph
import util._
import java.io._

object preTest1Seq {
  def main(args: Array[String]): Unit = {

    println("Heap size:" + Runtime.getRuntime().maxMemory())

    var g = SequentialGraph(true)
    g.setPrints(true)
    var timeInitial = System.nanoTime()  
    g = g.addFilter(new LewisBinReader("CartAndRad.100.bin"), "ls1")
    /*
    g = g.addFilter(new LewisBinReader("CartAndRad.100.bin",5), "source")
    g = g.addFilter(new ThinningFilter(60), "ls1").connectNodes("source", "ls1")
//    */
    var x = -1.5 * math.pow(10, -5) + 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) < x }), "f1").connectNodes("ls1", "f1")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f2").connectNodes("ls1", "f2")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f3").connectNodes("ls1", "f3")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f4").connectNodes("ls1", "f4")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f5").connectNodes("ls1", "f5")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f6").connectNodes("ls1", "f6")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f7").connectNodes("ls1", "f7")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f8").connectNodes("ls1", "f8")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f9").connectNodes("ls1", "f9")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f10").connectNodes("ls1", "f10")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f11").connectNodes("ls1", "f11")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f12").connectNodes("ls1", "f12")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f13").connectNodes("ls1", "f13")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f14").connectNodes("ls1", "f14")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f15").connectNodes("ls1", "f15")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f16").connectNodes("ls1", "f16")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f17").connectNodes("ls1", "f17")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f18").connectNodes("ls1", "f18")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f19").connectNodes("ls1", "f19")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f20").connectNodes("ls1", "f20")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f21").connectNodes("ls1", "f21")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f22").connectNodes("ls1", "f22")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f23").connectNodes("ls1", "f23")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f24").connectNodes("ls1", "f24")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.093750 }), "f25").connectNodes("ls1", "f25")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f26").connectNodes("ls1", "f26")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f27").connectNodes("ls1", "f27")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f28").connectNodes("ls1", "f28")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f29").connectNodes("ls1", "f29")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f30").connectNodes("ls1", "f30")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x && d(0) < x + 0.09375 }), "f31").connectNodes("ls1", "f31")
    x += 0.09375
    g = g.addFilter(new FilterBy((d: DataElement) => { d(0) >= x }), "f32").connectNodes("ls1", "f32")

    //g = g.addFilter(new LinearFitFilter("x[0][0][0]"), "lf").connectNodes("ls1", "lf")
    
    //g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a1").connectNodes("f1","a1").connectNodes("f2","a1")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a2").connectNodes("f3","a2").connectNodes("f4","a2")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a3").connectNodes("f5","a3").connectNodes("f6","a3")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a4").connectNodes("f7","a4").connectNodes("f8","a4")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a5").connectNodes("f9","a5").connectNodes("f10","a5")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a6").connectNodes("f11","a6").connectNodes("f12","a6")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a7").connectNodes("f13","a7").connectNodes("f14","a7")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a8").connectNodes("f15","a8").connectNodes("f16","a8")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a9").connectNodes("f17","a9").connectNodes("f18","a9")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a10").connectNodes("f19","a10").connectNodes("f20","a10")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a11").connectNodes("f21","a11").connectNodes("f22","a11")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a12").connectNodes("f23","a12").connectNodes("f24","a12")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a13").connectNodes("f25","a13").connectNodes("f26","a13")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a14").connectNodes("f27","a14").connectNodes("f28","a14")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a15").connectNodes("f29","a15").connectNodes("f30","a15")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "a16").connectNodes("f31","a16").connectNodes("f32","a16")

    //g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "b1").connectNodes("a1","b1").connectNodes("a2","b1")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "b2").connectNodes("a3","b2").connectNodes("a4","b2")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "b3").connectNodes("a5","b3").connectNodes("a6","b3")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "b4").connectNodes("a7","b4").connectNodes("a8","b4")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "b5").connectNodes("a9","b5").connectNodes("a10","b5")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "b6").connectNodes("a11","b6").connectNodes("a12","b6")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "b7").connectNodes("a13","b7").connectNodes("a14","b7")
    g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "b8").connectNodes("a15","b8").connectNodes("a16","b8")
    
    g = g.addFilter(new LinearFitFilter("x[0][0][0]"), "lf").connectNodes("b1", "lf")
    g = g.connectNodes("b2", "lf")
    g = g.connectNodes("b3", "lf")
    g = g.connectNodes("b4", "lf")
    g = g.connectNodes("b5", "lf")
    g = g.connectNodes("b6", "lf")
    g = g.connectNodes("b7", "lf")
    g = g.connectNodes("b8", "lf")
    
    g = g.addFilter(new PrintSink(), "ps1")
    var time1 = System.nanoTime()
    println("construction time:" + (time1 - timeInitial))
    timeInitial = System.nanoTime()
    g.run()
    time1 = System.nanoTime()
    println("run time:" + (time1 - timeInitial))

  }
}