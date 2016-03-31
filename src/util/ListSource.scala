package util

import sequential._
import Array._

/**
 * @author zkurimab
 */
class ListSource (s:Double, e:Double, d:Double) extends Container{
  var start:Double = s
  var end:Double = e
  var dx:Double = d
  var arr:Array[Double] = Array.tabulate((end-start/dx).toInt)(_*dx + start)
  var inputData:Vector[DataElement] = Vector(new DataElement( Vector.empty ))
  var outputData:Vector[DataElement] = Vector(new DataElement( arr.to[Vector]))
  var outputs:Vector[Node] = Vector.empty
  var output:DataStore = new DataStore( outputData, outputs)
  
  def apply(){
    arr =  Array.tabulate((end-start/dx).toInt)(_*dx + start)
    outputData = Vector(new DataElement( arr.to[Vector]))
    output.update();
  }
  
  def addOutput(n:Node){
    outputs = outputs :+ n
    output.addOutput(n)
  }
  
} 