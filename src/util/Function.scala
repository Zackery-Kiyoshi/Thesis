package util

/**
 * @author zkurimab
  
 */
// Wrapper so nodes can have any kind of Container
abstract class Function {
  
  def apply(inputData:Vector[Vector[DataElement]]):Vector[Vector[DataElement]];
}