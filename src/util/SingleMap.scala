package util

/**
 * @author zkurimab
 */
class SingleMap(val input:DataElement, val f:(Double) => (Double) ) extends Container {
  val inputs:Int = 1;
  val outputs:Int = 1;
  var output:DataElement = new DataElement(Vector.empty)
  
  
  def apply(){
    var tmp = input.get()
    tmp.map( x => f(x))
    output = new DataElement(tmp)
  }
  
}