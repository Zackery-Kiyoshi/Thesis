import scala.collection._
import org.scalameter._

object Streamtest {
   val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 25,
    Key.verbose -> false
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]) {

    val seqtime = standardConfig measure {
      
    }
    println(s"sequential time: $seqtime ms")

    val partime = standardConfig measure {
    }

    println(s"parallel time: $partime ms")
    println(s"speedup: ${}")
  }
}