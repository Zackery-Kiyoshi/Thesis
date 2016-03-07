package util

/**
 * @author zkurimab
  Wrapper for Nodes in the graph
 */
abstract class Container {
  val inputs:Int;
  val outputs:Int;
  def apply();
}