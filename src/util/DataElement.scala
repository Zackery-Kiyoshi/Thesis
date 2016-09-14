package util


class DataElement(in:Vector[Double]) {
  
  private var element:Array[Double] = in.to[Array] 
  // scala.collection.immutable.IndexedSeq[Double]
  
  def apply(){
    
  }
  
  def get():Array[Double] ={
    val tmp = element;
    tmp
  }
  
  /*
  // possibly unnecessary
  def set(t:Vector[Double]){
    element = t.to[Array]
  }
  */
  
}