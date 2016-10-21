package util
import scala.collection.mutable.ListBuffer

/**
 * @author zkurimab
 */
class PrintSink () extends Filter() {
  
  val t = "PrintSink"
  
  override def apply(input: Vector[DataStore]): Vector[DataStore] = {
    var ret = Vector[DataStore]()

    for (i <- input) {
      //var tmpDE:Vector[DataElement] = Vector.empty[DataElement]
      for (j <- 0 until i.length) {
        //var tmp = new Array[Double](i(j).length)
        //println( "V[" +  + "]")
        for (k <- 0 until i(j).length) {
          // do it for i(j)(k)
          println( i(j)(k) )
        }
        //var De = new DataElement(tmp.toVector)
        //tmpDE = tmpDE :+ De 
      }
      //var t = new DataStore(new DKey(""))
      //t.set(tmpDE)
      //ret = ret :+ t
    }

    return ret;
  }
  
}