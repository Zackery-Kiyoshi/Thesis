package sequential

import util._
import scala.ref.WeakReference

class SequentialRunGraph (
  override val filtKeys: Map[FKey, Filter],
  override val fKeys: List[FKey],
  override val dataKeys: Map[DKey, DataStore],
  override val dKeys: List[DKey],
  override val funcToData: Map[FKey, Vector[DKey]],
  override val dataToFunc: Map[DKey, Vector[FKey]],
  override val funcToInputs: Map[FKey, Vector[DKey]],
  override val nextfkey: Int,
  override val nextdkey: Int,
  override val runOnModify:Boolean,
  override val parent:WeakReference[SequentialGraph],
  override val print:Boolean
  ) extends SequentialGraph(filtKeys,fKeys,dataKeys,dKeys,funcToData,dataToFunc,funcToInputs,nextfkey,nextdkey,runOnModify,parent,print)
  //extends SequentialGraph(filtKeys,fKeys,dataKeys,dKeys,funcToData,dataToFunc,funcToInputs,nextfkey,nextdkey,runOnModify,parent)
  {
//  private val runOnModify = true
  private var running = true
  
  
  
  // does this need to be overwritten or could it happen when connecting a node/modifying a node???
  // also does it really need to be parallized???
  override def analyze():Boolean={
    var ret = true
    
    return ret
  }
  
  
  
//  /*
  override def run():SequentialRunGraph={
    return this
  }
  
  
  def getData(f:String):Vector[DataStore]= getData( getFKey(f) )
  
  def getData(f:FKey):Vector[DataStore]={
    // how to get data out
    var ret:Vector[DataStore] = Vector.empty
    for(i <- dKeys){
      if( i.key == f) ret =  ret :+ dataKeys(i)
    }
    return ret
  }
  
//  */
  
  
  
}

// modifications will start running depending on settings
  
// datastores should really be future[DataStore]
// functions give promises for the future[DataStore]

