package util

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class InputCollectionFilter() extends Filter() {
  
  val t:String = "InputCollectionFilter"
  
  var inputCount:Int = 0
  var numToKeep = 0
  
  override def apply(input:Vector[DataStore]):Vector[DataStore] = {
    var ret:Vector[DataStore] = Vector.empty
    var lastToRemove:Int = -1
    //sizeDataVectToInputStreams();
//    /*
    for(s <- 0 until input(0).length) {
      breakable { 
      for(lastToRemove <- 0 until input(s).length)
        //if(!(dataVect.get(s).get(lastToRemove).getParam(input.getNumParameters(s))<inputCount-numToKeep.getValue()+1)) break
        if(!(ret(s)(lastToRemove)(ret(s).length)<inputCount-numToKeep+1)) break
      }
      if(lastToRemove>0) {
        var toRemove:ArrayBuffer[DataElement] = new ArrayBuffer();
        //toRemove.ensureCapacity(lastToRemove);
        for(i <- 0 until lastToRemove) {
          toRemove = toRemove :+ input(s)(i)
        }
        // removing from datastore
        ret = ret :+ new DataStore()
        var newS:Vector[DataElement] = Vector.empty
        // loop through input(s) and keep wanted data
        
        ret(s).set(newS)
      //  ret(s).removeAll(toRemove)
      }
      for(i <- 0 until input(s).length) { 
      //  ret(s).add(new DataElement(input(i)(s),inputCount));
        
      }
    }
    inputCount+=1
//    */
    return ret;
  }
  
   
}

/*

protected void redoAllElements() {
        int lastToRemove;
        sizeDataVectToInputStreams();
        for(int s=0; s<getSource(0).getNumStreams(); ++s) {
            for(lastToRemove=0; lastToRemove<dataVect.get(s).size() && dataVect.get(s).get(lastToRemove).getParam(input.getNumParameters(s))<inputCount-numToKeep.getValue()+1; ++lastToRemove);
            if(lastToRemove>0) {
                ArrayList<DataElement> toRemove=new ArrayList<DataElement>();
                toRemove.ensureCapacity(lastToRemove);
                for(int i=0; i<lastToRemove; ++i) {
                    toRemove.add(dataVect.get(s).get(i));
                }
                dataVect.get(s).removeAll(toRemove);
            }
            for(int i=0; i<input.getNumElements(s); ++i) {
                dataVect.get(s).add(new DataElement(input.getElement(i,s),inputCount));
            }
        }
        inputCount++;
    }

*/

object InputCollectionFilter {
    def main(args:Array[String]) {
        
      
        
    }
}

