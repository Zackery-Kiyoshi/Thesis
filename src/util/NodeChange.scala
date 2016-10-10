package util

class ChangeField(var from:Any, var to:Any)

class NodeChange( var node:Function ) {
  
  var changes:List[Pair[String,ChangeField]] = List.empty
  
  def add[F](s:String,f:F,t:F) { 
    var tmp:Pair[String,ChangeField] = new Pair(s,new ChangeField(f,t))
    changes = changes :+ tmp
  }
  
}