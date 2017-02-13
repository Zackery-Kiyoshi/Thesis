package util


class DataElement(in:Vector[Double]) {
  
  private var element:Array[Double] = in.toArray 
  // scala.collection.immutable.IndexedSeq[Double]
  
  def apply(i: Int) = element(i)
  def length = element.length
  
  /*
  // possibly unnecessary
  def set(t:Vector[Double]){
    element = t.to[Array]
  }
  */
  
}