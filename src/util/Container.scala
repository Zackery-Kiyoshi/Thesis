package util
import scala.collection.mutable.ListBuffer
/**
 * @author zkurimab
  
 */
// Wrapper so nodes can have any kind of Container
abstract class Container {
  
  var id:Key
  var in:Vector[Vector[DataElement]]
  var out:Vector[Vector[DataElement]]
  
  def apply();
}