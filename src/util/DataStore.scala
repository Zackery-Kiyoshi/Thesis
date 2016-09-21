package util
import scala.collection.mutable.ListBuffer

class DataStore(val id:DKey) {
  //var numOutputs;
  
  private var out:Vector[DataElement] = Vector[DataElement]()
  
  def apply(i: Int): DataElement = out(i)
  def length = out.length
}