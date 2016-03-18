package util

class DataElement(in:Vector[Double]) {
  private var element:Vector[Double] = in 
  // scala.collection.immutable.IndexedSeq[Double]
  def get():Array[Double] ={
    val tmp = element.to[Array]
    tmp
  }
  // possibly unnecessary
  def set(t:Vector[Double]){
    element = t
  }
  
}