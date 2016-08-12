package util
import scala.collection.mutable.ListBuffer

// Wrapper so DataStores can be implementation dependent
abstract class Node {
  // anything needed?
  var c:Container
  var in:Vector[Node]
  var out:Vector[Node]
  
  def addInput(n:Node)
  def removeInput(n:Node)
  def addOutput(n:Node)
  def removeOutput(n:Node)
  def getOutput():Vector[Vector[DataElement]]
  def run()
  def copy():Node
}