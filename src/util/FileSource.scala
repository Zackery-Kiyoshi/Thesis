package util

import scala.io.Source

class FileSource(val filename:String) extends Filter{
  val t = "FileSource"
  
  override def apply(input:Vector[DataStore]):Vector[DataStore] = {
    var ret = Vector[DataStore](new DataStore())
    var tmp = Vector[DataElement]()
    for (line <- Source.fromFile(filename).getLines()) {
      tmp = tmp :+ new DataElement( line.split(Array(' ','\t')).map(_.toDouble).toVector )
    }
    ret(0).set(tmp)
    return ret;
  }
  
}