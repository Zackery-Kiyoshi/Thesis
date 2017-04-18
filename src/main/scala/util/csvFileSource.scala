package util

import scala.io.Source

class csvFileSource(val filename:String) extends Filter{
  val t = "csvFileSource"
  
  override def apply(input:Vector[DataStore]):Vector[DataStore] = {
    var ret = Vector[DataStore](new DataStore())
    var tmp = Vector[DataElement]()
    var i = 0
    for (line <- Source.fromFile(filename).getLines()) {
      if(i != 0 ) tmp = tmp :+ new DataElement( line.split(Array(',')).map( readCSV(_) ).toVector )
      i+=1
    }
    ret(0).set(tmp)
    return ret;
  }
  
  def readCSV(s:String):Double={
    var ret:Double = 0
    try{ 
      ret = s.toDouble
    } catch{ 
      case e:NumberFormatException => ret = -1.0
    }
    ret
  }
  
}