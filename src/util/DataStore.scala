package util
import scala.collection.mutable.ListBuffer

class DataStore(val id:DKey) {
  //var numOutputs;
  
  private var out:Vector[DataElement] = null
  
  def apply(i: Int): DataElement = out(i)
  def length = out.length
  def set(t:Vector[DataElement]){ if(out == null) out = t }
  
}