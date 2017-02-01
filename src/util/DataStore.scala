package util
import scala.collection.mutable.ListBuffer

class DataStore() {
  //var numOutputs;
  
  private var out:Vector[DataElement] = null
  
  def apply(i: Int): DataElement = out(i)
  def length = out.length
  def set(t:Vector[DataElement]){ if(out == null) out = t }
  def add(d:DataElement) { out = out :+ d }
  def getVect():Vector[DataElement] = out
  def isEmpty():Boolean = out == null
}

object DataStore {
  def apply(): DataStore = {
    new DataStore( )
  }
  
}