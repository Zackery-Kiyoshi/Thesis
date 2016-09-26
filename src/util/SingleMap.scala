package util
import scala.collection.mutable.ListBuffer
/**
 * @author zkurimab
 */
class SingleMap(var f:(Double) => (Double),var id:FKey) extends Function {
  
  
  def apply(input: Vector[DataStore]): Vector[DataStore] = {
    var ret = Vector[DataStore]()

    for (i <- input) {
      var tmpDE:Vector[DataElement] = Vector.empty[DataElement]
      for (j <- 0 until i.length) {
        var tmp = new Array[Double](i(j).length)
        //println( "V[" +  + "]")
        for (k <- 0 until i(j).length) {
          // do it for i(j)(k)
          tmp(k) = i(j)(k);
        }
        tmp.map(x => f(x))
        var De = new DataElement(tmp.toVector)
        tmpDE = tmpDE :+ De 
      }
      var t = new DataStore(new DKey(""))
      t.set(tmpDE)
      ret = ret :+ t
    }

    return ret;
  }
  
  
}