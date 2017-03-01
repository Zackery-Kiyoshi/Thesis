package util

class KMeans(val k: Int) extends Filter {
  val t = "FileSource"

  override def apply(input: Vector[DataStore]): Vector[DataStore] = {

    // need to find size of dataElements to create
    var d = 0
    for (i <- input) {
      for (j <- 0 until i.length) {
        if (d < (i(j)).length) d = (i(j)).length
      }
    }

    // create k random points

    var ctrs: Array[DataElement] = new Array(k)
    var preCtrs: Array[DataElement] = new Array(k)
    for (i <- 0 until k) {
      ctrs(i) = new DataElement(Vector.fill(d)(0.0))
    }

    // actual algorithm
    do {
      preCtrs = ctrs.filter(e => true)
      for (i <- 0 until k) {
        var closest: List[DataElement] = List.empty
        for (a <- input) {
          closest = a.getVect().filter(e => {
            var t: List[Double] = List.tabulate(d) { x:  Int => if (x < e.length) ctrs(i)(x) - e(x) else 0.0 }
            var ret = true
            for(j <- 0 until i){
              for(l <- 0 until d){
                if(t(l) > ctrs(j)(l) - e(l)) ret = ret && false
              }
            }
            for(j<-i+1 until k){
              for(l <- 0 until d){
                if(t(l) > ctrs(j)(l) - e(l)) ret = ret && false
              }
            }
            ret
          }).toList ::: closest
        }
        ctrs(i) = new DataElement(Vector.tabulate(d) { x: Int =>
          {
            var tmp = 0.0
            for (q <- closest) tmp += q(x)
            tmp/closest.length
          }
        })
      }
    } while (preCtrs != ctrs)
    var ret = new DataStore()
    ret.set(ctrs.toVector)
    return Vector[DataStore](ret)
  }

}