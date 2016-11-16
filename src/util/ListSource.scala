package util

import sequential._
import Array._

/**
 * @author zkurimab
 */
class ListSource (var start:Double, var end:Double, var dx:Double) extends Filter(){
  
  val t = "ListSource" 
  
  var arr:Array[Double] = Array.tabulate( ((end-start)/dx).toInt)(_*dx + start)
  //var inputData:Vector[DataElement] = Vector(new DataElement( Vector.empty ))
  //var outputData:Vector[DataElement] = Vector(new DataElement( arr.to[Vector]))
  //var outputs:Vector[Node] = Vector.empty
  //var output:DataStore = new DataStore( outputData, outputs)
  
  override def apply(inputData:Vector[DataStore]):Vector[DataStore]={
    var tmp:Vector[DataElement] = Vector.empty
    var retp:DataStore = new DataStore()
    for(i <- arr){
      tmp = tmp :+ new DataElement(Vector.empty :+ i)
    }
    retp.set(tmp)
    return Vector.empty :+ retp
  }
  
  

} 