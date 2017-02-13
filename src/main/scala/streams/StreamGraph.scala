package streams

import scala.ref.WeakReference
import scala.concurrent.{ Await, Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

import util._

class StreamGraph (
    override val filtKeys: Map[FKey, Filter],
    override val fKeys: List[FKey],
    override val dataKeys: Map[DKey, Future[DataStore]],
    override val dKeys: List[DKey],
    override val funcToData: Map[FKey, Vector[DKey]],
    override val dataToFunc: Map[DKey, Vector[FKey]],
    override val funcToInputs: Map[FKey, Vector[DKey]],
    override val nextfkey: Int,
    override val nextdkey: Int,
    override val runOnModify: Boolean,
    override val parent: WeakReference[StreamGraph]) extends ParallelGraph(filtKeys, fKeys, dataKeys, dKeys, funcToData, dataToFunc, funcToInputs, nextfkey, nextdkey, runOnModify, parent) {
  
  
  
  
  
}