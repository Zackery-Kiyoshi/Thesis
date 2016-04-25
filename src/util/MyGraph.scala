package util
 

case class FKey(val key:String)
case class DKey(val key:String)

class MyGraph() {
  
  var FuncKeys = scala.collection.mutable.Map[FKey,Fnode]()
  var DataKeys = scala.collection.mutable.Map[DKey,Fnode]()
  
  var FuncToData = scala.collection.mutable.Map[FKey,DKey]()
  var DatatoFunc = scala.collection.mutable.Map[DKey,Vector[FKey]]()
  
}