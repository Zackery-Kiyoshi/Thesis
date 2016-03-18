package util

/**
 * @author zkurimab
 */
class SingleMap(val input:Vector[DataElement], val f:(Double) => (Double), var output:Vector[Container]) extends Container {
  val inputs:Int = 1;
  val outputs:Int = 1;
  var outputData:Vector[DataElement] = Vector.empty
  
  
  def apply(){
    outputData = Vector.empty
    for(i <- input){
      var tmp = i.get()
      tmp.map( x => f(x))
      outputData = outputData :+ new DataElement(tmp.to[Vector])
    }
  }
  
}