package util
import scala.collection.mutable.ListBuffer

/**
 * @author zkurimab
 */
class PrintSink (var id:FKey) extends Function {
  
  var in:Vector[Vector[DataElement]] = Vector[Vector[DataElement]]()
  var out:Vector[Vector[DataElement]] = Vector[Vector[DataElement]]()
  
  //def this(){  }
  
  def apply(){
    for(i <- 0 until in.length){
      for(j <- 0 until in(i).length){
        val tmp = in(i)(j).get()
        println( "V[" + i + "]")
        for(x <- tmp) println(" " + x)
      }
    }
  }
  
}