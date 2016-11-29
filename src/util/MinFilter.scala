package util

// com returns true if the first is larger than the second
class MinFilter(val com:(DataElement,DataElement)=>Boolean, val n:Int = 0) extends Filter {
  val t = "MinFilter"
  
  override def apply(input:Vector[DataStore]):Vector[DataStore] = {
    var ret = Vector[DataStore]()
    
    if(n==0){
      for(in <- input){
        var min:DataElement = in(0)
        for(i <- 1 until in.length){
          if(com(min,in(i)))min = in(i)
        }
        var d:DataStore = new DataStore()
        d.set(Vector.empty :+ min)
        ret = ret :+ d 
      }
    }else{
      for(in <- input){
        var v:Vector[DataElement] = Vector.empty
        for( i <- 0 until in.length by n){
          var min:DataElement = in(i)
          for( j <- i+1 to i+n){
            if(in.length < j)
              if(com(min,in(j)))min = in(j)
          }
          v = v :+ min
        }
        var d:DataStore = new DataStore()
        d.set(v)
        ret = ret :+ d
      }
    }
    return ret;
  }
  
  
  
}