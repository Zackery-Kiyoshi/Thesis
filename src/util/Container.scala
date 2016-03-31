package util

/**
 * @author zkurimab
  
 */
// Wrapper so nodes can have any kind of Container
abstract class Container {
  var inputData:Vector[DataElement]
  var outputData:Vector[DataElement]
  def apply();
}