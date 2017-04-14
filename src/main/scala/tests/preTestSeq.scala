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
    g = g.addFilter(new LewisBinReader("CartAndRad.100.bin"), "source")
    g = g.addFilter(new ThinningFilter(20), "ls1").connectNodes("source", "ls1")
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

    g = g.addFilter(new LinearFitFilter("x[0][0][0]"), "lf").connectNodes("ls1", "lf")

    g = g.addFilter(new PrintSink(), "ps1")
    var time1 = System.nanoTime()
    println("construction time:" + (time1 - timeInitial))
    timeInitial = System.nanoTime()
    g.run()
    time1 = System.nanoTime()
    println("run time:" + (time1 - timeInitial))

  }
}