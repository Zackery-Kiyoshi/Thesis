package util

/**
 * @author zkurimab
 */
class MultiMap ( f:(Double)=>(Double) ) extends Function{
  
  override def apply(inputData:Vector[Vector[DataElement]]):Vector[Vector[DataElement]]={
    var ret:Vector[Vector[DataElement]] = Vector.empty
    for( x <- inputData){
      var tmpRet:Vector[DataElement] = Vector.empty
      for( y <- x){
        var tmpData = y.get()
        for(i <- 0 until tmpData.length){
          tmpData(i) = f(tmpData(i))
        }
         tmpRet = tmpRet :+ new DataElement(tmpData.to[Vector])
      }
      ret = ret :+ tmpRet
    }
    ret
  }
  
}