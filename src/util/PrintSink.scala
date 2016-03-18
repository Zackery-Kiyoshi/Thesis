package util

/**
 * @author zkurimab
 */
class PrintSink (val input:Vector[DataElement]) extends Container{
  val inputs:Int = 1;
  val outputs:Int = 0;
  
  //def this(){  }
  
  def apply(){
    for(i <- 0 until input.length){
      val tmp = input(i).get()
      println( "V[" + i + "]")
      for(x <- tmp) println(" " + x)
    }
  }
  
}