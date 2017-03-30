

//import org.scalameter.api._
import scala.collection._
import scala.util.Random
//import org.scalameter.api._
import org.scalameter.api._
import org.scalameter.picklers.Implicits._
import org.scalameter._

import sequential.SequentialGraph
import util._
import java.io._

class LargerTest extends org.scalameter.api.Bench.OfflineRegressionReport {
  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 25,
    Key.verbose -> false
  ) withWarmer(new org.scalameter.Warmer.Default)
  override lazy val executor = SeparateJvmsExecutor(
    new org.scalameter.api.Executor.Warmer.Default,
    org.scalameter.api.Aggregator.average,
    new org.scalameter.api.Measurer.Default
  )
  override lazy val measurer = new org.scalameter.api.Measurer.Default
  override lazy val reporter = new LoggingReporter[Double]
  override lazy val persistor = org.scalameter.api.Persistor.None

  var g = SequentialGraph()
  var time = standardConfig measure {
    g = g.addFilter(new LewisBinReader("CartAndRad.100.bin"),"ls1")
    g = g.addFilter(new PrintSink(), "ps1")  
  }
  println("construction time:" + time)
  time = standardConfig measure {
    g.run()
  }
  println("run time:" + time)
  /*
  performance of "Sequential Graph" in {
    g = g.addFilter(new LewisBinReader("CartAndRad.100.bin"),"ls1")
    g = g.addFilter(new PrintSink(), "ps1")
    //g=g.connectNodes("ls1","ps1")
    measure method "run" in{
      g.run()
    }
  }
	*/
  
}