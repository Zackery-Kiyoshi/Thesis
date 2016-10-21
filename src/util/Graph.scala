package util

case class FKey(val key: String) {
  def equals(s: FKey): Boolean = {
    return key == s.key
  }
  def equals(s: String): Boolean = {
    return key == s
  }
}

case class DKey(val key: String) {
  def equals(s: DKey): Boolean = {
    return key == s.key
  }
  def equals(s: String): Boolean = {
    return key == s
  }
}

class Graph (
  val filtKeys: Map[FKey, Filter],
  val fKeys: List[FKey],
  val dataKeys: Map[DKey, DataStore],
  val dKeys: List[DKey],
  val funcToData: Map[FKey, Vector[DKey]],
  val dataToFunc: Map[DKey, Vector[FKey]],
  val nextfkey: Int,
  val nextdkey: Int) {
  
  def apply(fstr: String): Filter = filtKeys(FKey(fstr))
  
  
  
  def replace(fstr: String, f2: Filter): Graph = {
    var tmp = ClearDownstream(FKey(fstr))
    new Graph(filtKeys.map { case (k, f) => if(k.key == fstr) k -> f2 else k -> f },
        fKeys, dataKeys, dKeys, funcToData, dataToFunc, nextfkey, nextdkey)
  }
  
  def modify(fstr: String)(func: Filter => Filter):Graph = {
    var tmp = ClearDownstream(FKey(fstr))
    new Graph(filtKeys.map { case (k, f) => if(k.key == fstr) k -> func(f) else k -> f },
        fKeys, dataKeys, dKeys, funcToData, dataToFunc, nextfkey, nextdkey)
  }
  
  def addFilter(filter:Filter, fName: String = "", dName: String = ""): Graph = {
    val (fkey, nextf) = if (fName=="") (FKey("filt"+nextfkey), nextfkey+1) else (FKey(fName),nextfkey)
    val (dkey, nextd) = if (dName=="") (DKey("data"+nextdkey), nextdkey+1) else (DKey(dName),nextdkey)
    //TODO - This doesn't add a datastore for the filter
    var tmp = filtKeys + (fkey -> filter)
    new Graph(filtKeys + (fkey -> filter), fkey::fKeys, dataKeys+(dkey -> new DataStore(dkey) ), dkey::dKeys, funcToData + (fkey -> Vector.empty.+:(dkey)), dataToFunc, nextf, nextd)
  }

 
  def connectNode(d: DKey, f: FKey): Graph = {
    // need to actually add f to dataToFunc(d)
    var tmp:Vector[FKey] = dataToFunc(d)
    tmp = tmp :+ f
    new Graph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc + (d -> tmp ), nextfkey, nextdkey)
  }
  def disconnectNodes(d: DKey, f: FKey): Graph = {
    // need to actually remove (f) from dataToFunc(d)
    var tmp:Vector[FKey] = Vector.empty
    for(i <- dataToFunc(d)) if(i.key != f.key) tmp = tmp :+ i
    new Graph(filtKeys , fKeys, dataKeys, dKeys, funcToData, dataToFunc + (d -> tmp ), nextfkey, nextdkey)
  }

  def removeNode(f: FKey): Graph = {
    // must delete associated DataNodes
    var d = funcToData(f)
    var tmpDataToFunc = dataToFunc
    var tmpdKeys = dKeys
    var tmpDataKeys = dataKeys
    for(i<-d){
      tmpDataToFunc = tmpDataToFunc - i
      tmpDataKeys = tmpDataKeys - i
      tmpdKeys = tmpdKeys.filter(_!=i)
    }
    
    // delete the actual node
    new Graph(filtKeys-f, fKeys.filter(_!=f), tmpDataKeys, tmpdKeys, funcToData-f, tmpDataToFunc, nextfkey, nextdkey)
  }
  def removeNode(f: String): Graph = {
    var ret: FKey = null
    // find the fkey
    for (i <- 0 until fKeys.length) {
      if (fKeys(i).key == f) ret = fKeys(i)
    }
    return removeNode(ret)
  }
  
  
  
  def analyze(): Boolean = { 
    var ret = false
    println("analyze not inplimented")
    return ret
  }

  /*  */
  def run():Unit={
    println("run not inplimented")
  }
  
  
  def printNodes(): String = { /* {Key, NodeType } */
    var ret = ""

    for (i <- 0 until fKeys.length) {
      ret += fKeys(i).key + ":" + filtKeys(fKeys(i)).t + ":" + fKeys(i) + "\n"
    }
    for (i <- 0 until dKeys.length) {
      ret += dKeys(i).key + ":" + "DataNode :" + dKeys(i) + "\n"
    }
    println(ret)
    return ret
  }

  def printConnections(): String = { /* fKey : connectedNodes, */
    var ret = ""

    println(fKeys.length)
    for (i <- 0 until fKeys.length) {
      println(i + ":" + fKeys(i))
      ret += fKeys(i).key + ": (" + funcToData(fKeys(i)).length + ") ["
      if (funcToData(fKeys(i)) != null) {
        for (j <- 0 until funcToData(fKeys(i)).length) {
          ret += funcToData(fKeys(i))(j).key
          if (j < funcToData(fKeys(i)).length - 1) ret += ","
        }
      }
      ret += "] \n"
    }
    for (i <- 0 until dKeys.length) {
      dKeys(i).key + ":"
    }
    println(ret)
    return ret
  }

  
  private def ClearDownstream(f:FKey):Map[DKey, DataStore]={
    var tmp:Map[DKey, DataStore] = dataKeys
    
    var cur:List[FKey] = (f) :: List() 
    
    while(cur.length != 0){
      for( i <- funcToData(cur(0))){
        dataKeys+(i-> new DataStore(i))
        cur = cur ::: dataToFunc(i).toList
      }
      cur = cur.tail
    }
    return tmp
  }
  
  private def getDKey(s: String): DKey = {
    var ret: DKey = null
    for (i <- 0 until dKeys.length) {
      if (dKeys(i).key == s) ret = dKeys(i)
    }
    return ret
  }

  private def getFKey(s: String): FKey = {
    var ret: FKey = null
    for (i <- 0 until fKeys.length) {
      if (fKeys(i).key == s) ret = fKeys(i)
    }
    return ret
  }

}

object Graph {
  def apply(): Graph = {
    new Graph( Map[FKey, Filter](), List[FKey](), Map[DKey, DataStore](), List[DKey](), Map[FKey, Vector[DKey]](), Map[DKey, Vector[FKey]](), 0, 0)
  }
  
  
  
}