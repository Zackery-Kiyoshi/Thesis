package util

class DataStore(var data:Vector[DataElement], var outputs:Vector[Node]) {
  var numOutputs = outputs.length
  
  def addOutput(n:Node){
    outputs = outputs :+ n
  }
  def removeOutput(c:Container){
    // think about/ is it necessary?
  }
  def removeOutput(i:Int){
    var tmpOut:Vector[Node] = Vector.empty
    for(c <- 0 until outputs.length){
      if(c != i) tmpOut = tmpOut :+ outputs(c)
    }
    outputs = tmpOut
  }
}