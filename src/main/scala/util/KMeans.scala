package util

import scala.util.Random

class KMeans(val k: Int) extends Filter {
  val t = "KMeans"

  override def apply(input: Vector[DataStore]): Vector[DataStore] = {

    // need to find size of dataElements to create
    var d = 0
    for (i <- input) {
      for (j <- 0 until i.length) {
        if (d < (i(j)).length) d = (i(j)).length
      }
    }
    d -=1
    // create k random points
    val r = scala.util.Random
    val ctrs: Array[DataElement] = new Array(k)
    for (i <- 0 until k) {
      ctrs(i) = new DataElement(Vector.tabulate(d)( x => r.nextDouble))
    }
    if(input(0).length == 0) return Vector[DataStore](new DataStore())
    // actual algorithm
    var keepGoing = true
    do {
      //println(">>>>>>>>>START")
      val preCtrs = ctrs.filter(e => true)
      for (i <- 0 until k) {
        var closest: List[DataElement] = List.empty
        
        for (a <- input) {
          for( z <- 0 until a.length ){
            val t = List.tabulate(d) { x:  Int => if (x < a(z).length) Math.abs(ctrs(i)(x) - a(z)(x)) else 0.0 }
            var ret = true
            for(j <- 0 until i){
              for(l <- 0 until d){
                if(t(l) > Math.abs(ctrs(j)(l) - a(z)(l) )) ret = false
              }
            }
            for(j<-i+1 until k){
              for(l <- 0 until d){
                if(t(l) > Math.abs(ctrs(j)(l) - a(z)(l) )) ret = false
              }
            }
            if(ret) closest = a(z) :: closest
            
          }
        }
        ctrs(i) = new DataElement(Vector.tabulate(d) { x: Int =>
          {
            var tmp = 0.0
            for (q <- closest) tmp += q(x)
            tmp/closest.length
          }
        })
      }
      /*
      for(i <- 0 until ctrs.length){
        println(i +":")
        for(j <- 0 until ctrs(i).length) print(preCtrs(i)(j) +",")
        println()
        for(j <- 0 until ctrs(i).length) print(ctrs(i)(j) +",")
        println()
        
        for(j <- 0 until ctrs(i).length)
          println( Math.abs(ctrs(i)(j) - preCtrs(i)(j)) < 0.005)
      }
			*/

      for(i <- 0 until ctrs.length){
        for(j <- 0 until ctrs(i).length){
          if( Math.abs(ctrs(i)(j) - preCtrs(i)(j)) < 0.005){
            keepGoing = false
//            println(">>" + i)
          }
        }
      }

    } while (keepGoing)
    var ret = new DataStore()
    ret.set(ctrs.toVector)
    return Vector[DataStore](ret)
  }

}
