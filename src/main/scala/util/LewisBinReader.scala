package util

import java.io._

class LewisBinReader(val filename:String, val m:Int = 1) extends Filter {
  val t = "LinearFit"

  override def apply(input: Vector[DataStore]): Vector[DataStore] = {
    var dis = new DataInputStream( new BufferedInputStream( new FileInputStream(new File(filename)) ))
    var n = java.lang.Integer.reverseBytes( dis.readInt() ) 
    n /= m
    var a:Array[Double] = Array.fill(n*7)(0.0)
    for(i <- 0 until n*7-1){
      a(i) = java.lang.Double.longBitsToDouble(java.lang.Long.reverseBytes(dis.readLong()))
    }
    var ret:Vector[DataElement] = Vector.empty
    for(i <- 0 until n){
      var tmp:Vector[Double] = Vector( a(i*7), a(i*7+1), a(i*7+2), a(i*7+3), a(i*7+4), a(i*7+5), a(n*6+i) )
      ret = new DataElement(tmp) +: ret
    }
    var ds = DataStore()
    ds.set(ret)
    return Vector(ds)
  }
}