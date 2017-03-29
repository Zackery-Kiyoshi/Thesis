package util

class FilterBy(val com:(DataElement)=>Boolean) extends Filter {
  val t = "FilterBy"
  
  override def apply(input:Vector[DataStore]):Vector[DataStore] = {
    var v:Vector[DataElement] = Vector.empty
    for(in <- input){
      for( j <- 0 until in.length){
        if(in.length < j)
          if(com(in(j))) v = v :+ in(j)
      }
    }
    var d:DataStore = new DataStore()
    d.set(v)
    return Vector[DataStore](d);
  }
  
}