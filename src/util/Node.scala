package util

// Wrapper so DataStores can be implementation dependent
abstract class Node {
  // anything needed?
  var c:Container
  def run()
  def updateInput(data:Vector[DataElement])
  def updateOutput()
}