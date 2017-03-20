
import org.scalameter.api._
import org.scalameter.picklers.Implicits._

import futures.FutureGraph
import util._

trait SmallTestFut extends Bench.OfflineRegressionReport {
  
  override lazy val executor = SeparateJvmsExecutor(
    new Executor.Warmer.Default,
    Aggregator.average,
    new Measurer.Default
  )
  override lazy val measurer = new Measurer.Default
  override lazy val reporter = new LoggingReporter[Double]
  override lazy val persistor = Persistor.None
  
  var g = FutureGraph() 
  
  
  performance of "Future Graph" in {
    measure method "construction" in {
        g = g.addFilter(new ListSource(0,100000,2), "ls2")
        g = g.addFilter(new ListSource(0,100000,3), "ls3")
        g = g.addFilter(new ListSource(0,100000,4), "ls4")
        
        g = g.addFilter(new ListSource(0,100000,1), "ls1").addFilter(new FunctionFilter("x[0][0][0]*x[0][0][0]"), "fs1").connectNodes("ls1", "fs1")
        
        g = g.addFilter(new FunctionFilter("x[0][0][0]*x[0][0][0]"), "fs2").connectNodes("ls2", "fs2")
        g = g.addFilter(new ListSource(0,100000,1), "ls1")
    }
    
    measure method "run" in {
      g.run()
    }
    
  }
  
  
  
}