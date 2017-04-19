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
  override def equals(d:Any):Boolean = {
    d match {
      case d: DataStore =>{
        if(out.length != d.length) return false
        for(i <- 0 until d.length){
          if(out(i) != d(i)) return false
        }
        true
      }
    case _ => false
    }
  }
}

object DataStore {
  def apply(): DataStore = {
    new DataStore( )
  }
  
}
