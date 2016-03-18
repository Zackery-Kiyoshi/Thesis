package util

/**
 * @author zkurimab
  
 */
// Wrapper so nodes can have any kind of Container
abstract class Container {
  val inputs:Int;
  val outputs:Int;
  def apply();
}