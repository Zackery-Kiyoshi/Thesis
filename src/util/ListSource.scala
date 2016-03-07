package util

import Array._

/**
 * @author zkurimab
 */
class ListSource (start:Double, end:Double, dx:Double ) extends Container{
  val inputs:Int = 0;
  val outputs:Int = 1;
  val arr = for(x <- start to end by dx) yield x;
  val output:DataElement = new DataElement( arr.to[Vector]);
  
  def apply(){}
  
}