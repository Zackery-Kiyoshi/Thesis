package util
import scala.collection.mutable.ListBuffer
/**
 * @author zkurimab
 */
class SingleMap(var f:(Double) => (Double),var id:FKey) extends Function {
  
  var in:Vector[Vector[DataElement]] = Vector[Vector[DataElement]]()
  var out:Vector[Vector[DataElement]] = Vector[Vector[DataElement]]()
  
  //var output:DataStore = new DataStore( outputData, outputs)
  
  def apply(){
    out = Vector[Vector[DataElement]]()
    var outputData = Vector[DataElement]()
    var index:Int = 0
    for(t <- in){
      for(i <- t){
        var tmp = i.get()
        tmp.map( x => f(x))
        outputData = outputData :+ new DataElement(tmp.to[Vector])
      }
      out = out :+ Vector[DataElement]()
      //  outputData
      outputData = Vector[DataElement]()
      index += 1
    }
    //output.update();
  }
  
}