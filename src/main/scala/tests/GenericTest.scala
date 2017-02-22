package tests

import org.scalameter.api._

/*
object GenericTest extends Bench.LocalTime {
  
  performance of "Test" in {
    measure method "println" in {
      println("test")
      wait(5)
    }
  }
  
}
// */

object RangeBenchmark extends Bench.LocalTime {
  val sizes = Gen.range("size")(300000, 1500000, 300000)

  val ranges = for {
    size <- sizes
  } yield 0 until size

  performance of "Range" in {
    measure method "map" in {
      using(ranges) in {
        r => r.map(_ + 1)
      }
    }
  }
}

