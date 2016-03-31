package util

/**
 * @author zkurimab
 */
class PrintSink (var inputData:Vector[DataElement]) extends Container{
  var outputData:Vector[DataElement] = Vector(new DataElement( Vector.empty))
  //def this(){  }
  
  def apply(){
    for(i <- 0 until inputData.length){
      val tmp = inputData(i).get()
      println( "V[" + i + "]")
      for(x <- tmp) println(" " + x)
    }
  }
  
}