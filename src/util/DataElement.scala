package util


class DataElement(in:Vector[Double]) extends Container() {
  val inputs: Int = 0
  val outputs: Int = 0
  private var element:Array[Double] = in.to[Array] 
  // scala.collection.immutable.IndexedSeq[Double]
  
  def apply(){
    
  };
  
  def get():Array[Double] ={
    val tmp = element.to[Array]
    tmp
  }
  // possibly unnecessary
  def set(t:Vector[Double]){
    element = t.to[Array]
  }
  
  
}