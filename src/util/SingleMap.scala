package util

/**
 * @author zkurimab
 */
class SingleMap(var inputData:Vector[DataElement], var f:(Double) => (Double)) extends Container {
  
  var outputs:Vector[Node] = Vector.empty
  var outputData:Vector[DataElement] = Vector.empty
  //var output:DataStore = new DataStore( outputData, outputs)
  
  def apply(){
    outputData = Vector.empty
    for(i <- inputData){
      var tmp = i.get()
      tmp.map( x => f(x))
      outputData = outputData :+ new DataElement(tmp.to[Vector])
    }
    //output.update();
  }
  
  def addOutput(n:Node){
    //outputs = outputs :+ n
    //output.addOutput(n)
  }
  
}