
import scala.collection._
import scala.util.Random
import org.scalameter.api._
import org.scalameter.picklers.Implicits._

import futures._
import util._
import java.io._


class Test1Fut extends Bench.OfflineRegressionReport {
  
  override lazy val executor = SeparateJvmsExecutor(
    new Executor.Warmer.Default,
    Aggregator.average,
    new Measurer.Default
  )
  override lazy val measurer = new Measurer.Default
  override lazy val reporter = new LoggingReporter[Double]
  override lazy val persistor = Persistor.None

  
  performance of "Future Graph" in {
    var g = FutureGraph() 
    g = g.addFilter(new LewisBinReader("CartAndRad.100.bin"),"ls1")
    var x = -1.5 * math.pow(10,-5) + 0.09375
    
//    /*
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0)<x}),"f1" ).connectNodes("ls1","f1")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f2" ).connectNodes("ls1","f2")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f3" ).connectNodes("ls1","f3")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f4" ).connectNodes("ls1","f4")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f5" ).connectNodes("ls1","f5")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f6" ).connectNodes("ls1","f6")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f7" ).connectNodes("ls1","f7")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f8" ).connectNodes("ls1","f8")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f9" ).connectNodes("ls1","f9")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f10" ).connectNodes("ls1","f10")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f11" ).connectNodes("ls1","f11")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f12" ).connectNodes("ls1","f12")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f13" ).connectNodes("ls1","f13")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f14" ).connectNodes("ls1","f14")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f15" ).connectNodes("ls1","f15")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f16" ).connectNodes("ls1","f16")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f17" ).connectNodes("ls1","f17")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f18" ).connectNodes("ls1","f18")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f19" ).connectNodes("ls1","f19")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f20" ).connectNodes("ls1","f20")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f21" ).connectNodes("ls1","f21")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f22" ).connectNodes("ls1","f22")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f23" ).connectNodes("ls1","f23")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f24" ).connectNodes("ls1","f24")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.093750 }),"f25" ).connectNodes("ls1","f25")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f26" ).connectNodes("ls1","f26")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f27" ).connectNodes("ls1","f27")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f28" ).connectNodes("ls1","f28")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f29" ).connectNodes("ls1","f29")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f30" ).connectNodes("ls1","f30")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x && d(0)< x+0.09375 }),"f31" ).connectNodes("ls1","f31")
    x += 0.09375
    g = g.addFilter(new FilterBy( (d:DataElement)=>{ d(0) >= x }),"f32" ).connectNodes("ls1","f32")
    
    g = g.addFilter(new LinearFitFilter("x[0][0][0]"),"lf").connectNodes("ls1","lf")
    //+x[0][0][1]+x[0][0][0]+x[0][0][2]
    /*
    g = g.addFilter(new LinearFitFilter("x[0][0][0]+x[0][0][1]+x[0][0][0]+x[0][0][2]"),"lf1")
    g = g.connectNodes("f1","lf1").connectNodes("f2","lf1").connectNodes("f3","lf1").connectNodes("f4","lf1")
    
    g = g.addFilter(new LinearFitFilter("x[0][0][0]+x[0][0][1]+x[0][0][0]+x[0][0][2]"),"lf2")
    g = g.connectNodes("f1","lf2").connectNodes("f2","lf2").connectNodes("f3","lf2").connectNodes("f4","lf2")
    
    g = g.addFilter(new LinearFitFilter("x[0][0][0]+x[0][0][1]+x[0][0][0]+x[0][0][2]"),"lf3")
    g = g.connectNodes("f1","lf3").connectNodes("f2","lf3").connectNodes("f3","lf3").connectNodes("f4","lf3")
    
    g = g.addFilter(new LinearFitFilter("x[0][0][0]+x[0][0][1]+x[0][0][0]+x[0][0][2]"),"lf4")
    g = g.connectNodes("f1","lf4").connectNodes("f2","lf4").connectNodes("f3","lf4").connectNodes("f4","lf4")
    
    g = g.addFilter(new LinearFitFilter("x[0][0][0]+x[0][0][1]+x[0][0][0]+x[0][0][2]"),"lf5")
    g = g.connectNodes("f1","lf5").connectNodes("f2","lf5").connectNodes("f3","lf5").connectNodes("f4","lf5")
    
    g = g.addFilter(new LinearFitFilter("x[0][0][0]+x[0][0][1]+x[0][0][0]+x[0][0][2]"),"lf6")
    g = g.connectNodes("f1","lf6").connectNodes("f2","lf6").connectNodes("f3","lf6").connectNodes("f4","lf6")
    
    g = g.addFilter(new LinearFitFilter("x[0][0][0]+x[0][0][1]+x[0][0][0]+x[0][0][2]"),"lf7")
    g = g.connectNodes("f1","lf7").connectNodes("f2","lf7").connectNodes("f3","lf7").connectNodes("f4","lf7")
    
    g = g.addFilter(new LinearFitFilter("x[0][0][0]+x[0][0][1]+x[0][0][0]+x[0][0][2]"),"lf8")
    g = g.connectNodes("f1","lf8").connectNodes("f2","lf8").connectNodes("f3","lf8").connectNodes("f4","lf8")
    
//    */
    /*
    g = g.addFilter(new MinFilter( (d:DataElement,d1:DataElement)=>{ d(0) > d1(0)} ),"min").connectNodes("ls1","min")
    g = g.addFilter(new MaxFilter((d:DataElement,d1:DataElement)=>{ d(0) > d1(0)}),"max").connectNodes("ls1","max")
    g = g.addFilter(new PrintSink(), "ps1").connectNodes("min","ps1")
    g = g.addFilter(new PrintSink(), "ps2").connectNodes("max","ps2")
//    */
    //g=g.connectNodes("ls1","ps1")
    
    var gRun:futures.FutureRunGraph = null
    measure method "run" in{
      gRun = g.run()
    }
    
  }
}