package util

/**
 * @author zkurimab
 */
class PrintSink (val input:DataElement) extends Container{
  val inputs:Int = 1;
  val outputs:Int = 0;
  def apply(){
    val tmp = input.get()
    for(x <- tmp) println(x)
  }
}