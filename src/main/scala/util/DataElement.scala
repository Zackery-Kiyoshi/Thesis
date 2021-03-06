package util


class DataElement(in:Vector[Double]) {
  
  private val element:Array[Double] = in.toArray 
  // scala.collection.immutable.IndexedSeq[Double]
  
  def apply(i: Int) = element(i)
  def length = element.length
  
  override def equals(d:Any):Boolean = {
    d match {
      case d:DataElement => 
//        length == d.length && (element, d.element).zipped.forall(_ == _)
      {
        if(length != d.length) return false
        for(i <- 0 until length){
          if(element(i) != d(i)) return false
        }
        true
      }
    case _ => false
    }
  }
  
  /*
  // possibly unnecessary
  def set(t:Vector[Double]){
    element = t.to[Array]
  }
  */
  
}
