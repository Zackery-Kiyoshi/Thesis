package util

class CombineDataElems extends Filter {
  val t = "CombineDataElems"

  override def apply(input: Vector[DataStore]): Vector[DataStore] = {
    var ret = DataStore()
    var tmp:Vector[Vector[Double]] = Vector.fill(1)(Vector.empty)
    for(i <- 0 until input.length){
      for(j <- 0 until input(i).length){
         for(k <- 0 until input(i)(j).length){
           input(i)(j)(k)
           
         }
      }
    }
    var tmp2:Vector[DataElement] = Vector.empty
    
    ret.set(tmp2)
    return Vector(ret)
  }
  
}