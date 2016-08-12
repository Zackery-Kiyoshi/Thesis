package util
import scala.collection.mutable.ListBuffer

/**
 * @author zkurimab
 */
class Fcon (var func:Function,var id:Key) extends Container{
  
  var in:Vector[Vector[DataElement]] = Vector[Vector[DataElement]]()
  var out:Vector[Vector[DataElement]] = Vector[Vector[DataElement]]()
  
  def apply(){
    out = func(in)
  }
  
}