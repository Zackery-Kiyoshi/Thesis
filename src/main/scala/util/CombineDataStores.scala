package util

class CombineDataStores extends Filter {
  val t = "CombineDataStores"

  override def apply(input: Vector[DataStore]): Vector[DataStore] = {
    var ret = DataStore()
    var tmp:Vector[DataElement] = Vector.empty
    for(i <- input){
      tmp ++= i.getVect() 
    }
    ret.set(tmp)
    return Vector(ret)
  }
}