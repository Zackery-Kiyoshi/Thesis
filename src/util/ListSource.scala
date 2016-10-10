package util

import sequential._
import Array._

/**
 * @author zkurimab
 */
class ListSource (s:Double, e:Double, d:Double, var id1:FKey) extends Function(id1){
  
  val t = "ListSource" 
  
  var start:Double = s
  var end:Double = e
  var dx:Double = d
  var arr:Array[Double] = Array.tabulate((end-start/dx).toInt)(_*dx + start)
  //var inputData:Vector[DataElement] = Vector(new DataElement( Vector.empty ))
  //var outputData:Vector[DataElement] = Vector(new DataElement( arr.to[Vector]))
  //var outputs:Vector[Node] = Vector.empty
  //var output:DataStore = new DataStore( outputData, outputs)
  
  override def apply(inputData:Vector[DataStore]):Vector[DataStore]={
    var tmp:Vector[DataElement] = Vector.empty
    tmp :+ new DataElement(arr.to[Vector])
    var retp:DataStore = new DataStore(new DKey("t"))
    retp.set(tmp)
    var ret:Vector[DataStore] = Vector.empty
    ret :+ retp
    return ret
  }
  
  def apply(){
    arr =  Array.tabulate((end-start/dx).toInt)(_*dx + start)
    //outputData = Vector(new DataElement( arr.to[Vector]))
    //output.update();
  }
  

} 