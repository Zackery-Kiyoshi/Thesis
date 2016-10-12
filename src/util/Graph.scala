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

class Graph private(
  val funcKeys: Map[FKey, Function],
  val fKeys: List[FKey],
  val dataKeys: Map[DKey, DataStore],
  val dKeys: List[DKey],
  val funcToData: Map[FKey, Vector[DKey]],
  val dataToFunc: Map[DKey, Vector[FKey]],
  val nextfkey: Int,
  val nextdkey: Int) {
  
  def apply(fstr: String): Function = funcKeys(FKey(fstr))
  
  def replace(fstr: String, f2: Function): Graph = {
    // TODO - This doesn't clear the downstream data stores
    new Graph(funcKeys.map { case (k, f) => if(k.key == fstr) k -> f2 else k -> f },
        fKeys, dataKeys, dKeys, funcToData, dataToFunc, nextfkey, nextdkey)
  }
  
  def modify(fstr: String)(func: Function => Function):Graph = {
    // TODO - This doesn't clear the downstream data stores
    new Graph(funcKeys.map { case (k, f) => if(k.key == fstr) k -> func(f) else k -> f },
        fKeys, dataKeys, dKeys, funcToData, dataToFunc, nextfkey, nextdkey)
  }
  
  def addFilter(filter:Function, name: String = ""): Graph = {
    val (fkey, next) = if (name=="") (FKey("filt"+nextfkey), nextfkey+1) else (FKey(name),nextfkey)
    new Graph(funcKeys + (fkey -> filter), fkey::fKeys, dataKeys, dKeys, funcToData, dataToFunc, next, nextdkey)
    //TODO - This doesn't add a datastore for the filter
  }

  //method(Graph): Graph
  def help() {
    println("analyze() ")
    println("  Where the code is updated")
    println("  possible modifications:")
    println("     To identify types for filters and data ")
    println("     Show errors in graph")
    println("     ")
    println("process(Graph,Command):Graph ")
    println("  Where the code is updated")
    println("  possible modifications:")
    println("     command to store")
    println("     ")
    println("run() ")
    println("  Where implementation of parallelesm or lack there of")
    println("  possible modifications:")
    println("     ")
  }

  def analyze(): Boolean = { /* (Checks for warnings/errors) */
    var ret = false
    println("analyze not inplimented")
    return ret
  }

  def process(c: NodeChange): Graph = {
    println("process not inplimented")

    return this
  }

  def run() {
    println("run not inplimented")
  }

  def connectNode(f: FKey, d: DKey): Boolean = {
    var ret = false

    return ret
  }
  def connectNode(d: DKey, f: FKey): Boolean = {
    var ret = false

    return ret
  }
  def disconnectNodes(f: FKey, d: DKey): Boolean = {
    var ret = false

    return ret
  }
  def disconnectNodes(d: DKey, f: FKey): Boolean = {
    var ret = false

    return ret
  }

  def printNodes(): String = { /* {Key, NodeType } */
    var ret = ""

    for (i <- 0 until fKeys.length) {
      ret += fKeys(i).key + ":" + funcKeys(fKeys(i)).t + ":" + fKeys(i) + "\n"
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

  def removeNode(f: FKey): Boolean = {
    var ret = false
    // must delete associated DataNodes

    // delete the actual node

    return ret
  }
  def removeNode(f: String): Boolean = {
    var ret: FKey = null
    // find the fkey
    for (i <- 0 until fKeys.length) {
      if (fKeys(i).key == f) ret = fKeys(i)
    }
    return removeNode(ret)
  }

  /**
   * 
   */
  /*
  def addListSouceNode(s: Double, e: Double, di: Double): Graph = {
    val fKey = new FKey("f" + fkey)
    val n: Function = new ListSource(s, e, di, fKey)
    funcKeys += (fKey -> n)
    fKeys = fKeys :+ fKey
    fkey += 1

    val dKey = new DKey("d" + dkey)
    val d: DataStore = new DataStore(dKey)
    dKeys = dKeys :+ dKey
    dataKeys += (dKey -> d)
    dkey += 1
    val out: Vector[DKey] = Vector.empty
    out :+ dKey
    funcToData += (fKey -> out)
    val tmp:Vector[FKey] = Vector.empty
    dataToFunc += (dKey -> tmp)
    return new Pair(fKey, dKey)
  }
  def addListSouceNode(s: Double, e: Double, di: Double, fk: String, dk: String): Pair[FKey, DKey] = {
    var fKey = new FKey(fk)
    var n: Function = new ListSource(s, e, di, fKey)
    funcKeys += (fKey -> n)
    fKeys = fKeys :+ fKey

    var dKey = new DKey(dk)
    var d: DataStore = new DataStore(dKey)
    dKeys = dKeys :+ dKey
    dataKeys += (dKey -> d)
    var out: Vector[DKey] = Vector.empty
    out :+ dKey
    funcToData += (fKey -> out)
    var tmp:Vector[FKey] = Vector.empty
    dataToFunc += (dKey -> tmp)
    return new Pair(fKey, dKey)
  }

  def addFunctionFilter(s: String): Pair[FKey, DKey] = {
    var fKey = new FKey("f" + fkey)
    var n: Function = new FunctionFilter(s, fKey)
    //n.c = new SingleMap(, new FKey(""))
    funcKeys += (fKey -> n)
    fKeys = fKeys :+ fKey
    fkey += 1

    var dKey = new DKey("d" + dkey)
    var d: DataStore = new DataStore(dKey)
    dKeys = dKeys :+ dKey
    dataKeys += (dKey -> d)
    dkey += 1
    var out: Vector[DKey] = Vector.empty
    out :+ dKey
    funcToData += (fKey -> out)
    var tmp:Vector[FKey] = Vector.empty
    dataToFunc += (dKey -> tmp)
    return new Pair(fKey, dKey)
  }
  def addFunctionFilter(s: String, fk: String, dk: String): Pair[FKey, DKey] = {
    var fKey = new FKey(fk)
    var n: Function = new FunctionFilter(s, fKey)
    //n.c = new SingleMap(, new FKey(""))
    funcKeys += (fKey -> n)
    fKeys = fKeys :+ fKey

    var dKey = new DKey(dk)
    var d: DataStore = new DataStore(dKey)
    dKeys = dKeys :+ dKey
    dataKeys += (dKey -> d)
    var out: Vector[DKey] = Vector.empty
    out :+ dKey
    funcToData += (fKey -> out)
    var tmp:Vector[FKey] = Vector.empty
    dataToFunc += (dKey -> tmp)
    return new Pair(fKey, dKey)
  }

  def addPrintSkink(): FKey = {
    var fKey = new FKey("f" + fkey)
    var n: Function = new PrintSink(fKey)

    //n.c = new Fnode(, fKey)
    funcKeys += (fKey -> n)
    var tmp:Vector[DKey] = Vector.empty
    funcToData += (fKey -> tmp)
    fKeys = fKeys :+ fKey
    fkey += 1

    return fKey
  }
  def addPrintSkink(fk: String): FKey = {
    var fKey = new FKey(fk)
    var n: Function = new PrintSink(fKey)

    //n.c = new Fnode(, fKey)
    funcKeys += (fKey -> n)
    var tmp:Vector[DKey] = Vector.empty
    funcToData += (fKey -> tmp)
    fKeys = fKeys :+ fKey
    return fKey
  }

  def connectNodes(input: DKey, output: FKey): Unit = {
    var tmp = dataToFunc(input)
    tmp = tmp :+ output
    dataToFunc(input) = (tmp)
  }
  
  def connectNodes(input: String, output: String) {
    var in = getDKey(input);
    var tmp = dataToFunc(in);
    var f = getFKey(output)
    tmp = tmp :+ f
    dataToFunc(in) = (tmp)
    
  }
  Guture(Graph)
  */

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
//    new Graph(???)
    ???
  }
}