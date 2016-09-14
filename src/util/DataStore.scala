package util
import scala.collection.mutable.ListBuffer

class DataStore(var id:DKey) extends Container {
  //var numOutputs;
  
  var in:Vector[Vector[DataElement]] = Vector[Vector[DataElement]]()
  var out:Vector[Vector[DataElement]] = Vector[Vector[DataElement]]()
  
  def apply(){
    out = in
  }
}