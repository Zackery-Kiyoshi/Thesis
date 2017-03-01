
import org.scalameter.api._
import org.scalameter.picklers.Implicits._

import sequential.SequentialGraph
import util._

class MultTest50 extends Bench.OfflineRegressionReport {

  override lazy val executor = SeparateJvmsExecutor(
    new Executor.Warmer.Default,
    Aggregator.average,
    new Measurer.Default)
  override lazy val measurer = new Measurer.Default
  override lazy val reporter = new LoggingReporter[Double]
  override lazy val persistor = Persistor.None

  var g = SequentialGraph()
  val n = 50
  val end = 100000
  performance of "Sequential Graph" in {
    measure method "construction" in {
      var i: Int = 0
      while (i < n) {
        g = g.addFilter(new ListSource(0, end, 1 + i), "ls" + i)
        i += 1
      }
      i = 0
      var j = 0
      var k = 2
      while (n / k > 1) {
        while (j < n / k && i < n) {
          g = g.addFilter(new FunctionFilter("x[0][0][0]*x[1][0][0]"), "fs" + j)
          g = g.connectNodes("ls" + i, "fs" + j)
          g = g.connectNodes("ls" + (i + 1), "fs" + j)
          i += k
          j += 1
        }
        j=0
        k *=2
      }
      println(k/2)
    }

    measure method "run" in {
      g.run()
    }

  }

}