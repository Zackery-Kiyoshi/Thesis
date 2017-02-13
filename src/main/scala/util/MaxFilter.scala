package util

class MaxFilter(val com:(DataElement,DataElement)=>Boolean,val n:Int = 0) extends Filter {
  val t = "MaxFilter"
  
  override def apply(input:Vector[DataStore]):Vector[DataStore] = {
    var ret = Vector[DataStore]()
    
    if(n==0){
      for(in <- input){
        var max:DataElement = in(0)
        for(i <- 1 until in.length){
          if(com(in(i),max))max = in(i)
        }
        var d:DataStore = new DataStore()
        d.set(Vector.empty :+ max)
        ret = ret :+ d 
      }
    }else{
      for(in <- input){
        var v:Vector[DataElement] = Vector.empty
        for( i <- 0 until in.length by n){
          var max:DataElement = in(i)
          for( j <- i+1 to i+n){
            if(in.length < j)
              if(com(in(j),max))max = in(j)
          }
          v = v :+ max
        }
        var d:DataStore = new DataStore()
        d.set(v)
        ret = ret :+ d
      }
    }
    return ret;
  }
  
}