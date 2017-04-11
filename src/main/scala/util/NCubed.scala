package util

class NCubed extends Filter() {
  val t: String = "NCubed"
  
  override def apply(input: Vector[DataStore]): Vector[DataStore] = {
    
    var tmp:List[DataElement] = List.empty
    
    for(i <- 0 until input.length){
      for(j <- 0 until input(i).length){
          
        for(ip <- 0 until input.length){
          for(jp <- 0 until input(ip).length){
            
            for(ipp <- 0 until input.length){
              for(jpp <- 0 until input(ipp).length){
                
                var t = for(k <- 0 until input(i)(j).length) yield input(i)(j)(k) + input(ip)(jp)(k) - input(ipp)(jpp)(k) 
                
                tmp = tmp :+ new DataElement(t.toVector)
                
              }
            }
            
          }
        }
        
      }
    }
    
    
    return Vector[DataStore]()
  }
  
}