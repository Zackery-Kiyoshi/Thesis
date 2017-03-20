

//import org.scalameter.api._
import scala.collection._
import scala.util.Random
import org.scalameter.api._
import org.scalameter.picklers.Implicits._

import sequential.SequentialGraph
import util._
import java.io._

class LargerTest extends Bench.OfflineRegressionReport {
  
  override lazy val executor = SeparateJvmsExecutor(
    new Executor.Warmer.Default,
    Aggregator.average,
    new Measurer.Default
  )
  override lazy val measurer = new Measurer.Default
  override lazy val reporter = new LoggingReporter[Double]
  override lazy val persistor = Persistor.None

  
  performance of "Sequential Graph" in {
    var g = SequentialGraph() 
    g = g.addFilter(new LewisBinReader("CartAndRad.100.bin"),"ls1")
    g = g.addFilter(new PrintSink(), "ps1")
    //g=g.connectNodes("ls1","ps1")
    
    
    measure method "run" in{
      g.run()
    }
    
  }

  
  
  
  
  /*
  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 25,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  override def main(args: Array[String]) {
    
    
    var g = SequentialGraph() 
    g = g.addFilter(new LewisBinReader("CartAndRad.100.bin"),"Ls1")
    g = g.addFilter(new PrintSink(), "psl").connectNodes("Ls1","ps1")
    
    
    val runtime = standardConfig measure {
      g.run()
    }
    println(s"run time: $runtime ms")
    
  }
   */

}