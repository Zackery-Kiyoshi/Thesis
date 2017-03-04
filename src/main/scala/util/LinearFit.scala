package util

class LinearFit(val n:Int = 0) extends Filter {
  val t = "LinearFit"

  override def apply(input: Vector[DataStore]): Vector[DataStore] = {
    var a = 0.0
    var b = 0.0
    var x = 0.0
    var y = 0.0
    for (i <- input) {
      for (j <- 0 until i.length) {
        i(j).length
        a += i(j)(0)*i(j)(1)
        b += i(j)(0)*i(j)(0)
        x += i(j)(0)
        y += i(j)(1)
      }
    }
    
    var ds = new DataStore()
    ds.set(Vector(new DataElement(Vector(a,b))))
    
    return Vector(ds)
  }

}