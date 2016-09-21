package util

/**
 * @author zkurimab
  
 */
// Wrapper so nodes can have any kind of Container
abstract class Function {
  
  //def apply(inputData:Vector[Vector[DataElement]]):Vector[Vector[DataElement]]={
  def apply(){
    var ret = Vector[Vector[DataElement]]()
    
    return ret;
  }
  
  def sizeDataVectToInputStreams() {
    if(dataVect.size()>input.getNumStreams()) dataVect.clear();
    while(dataVect.size()<input.getNumStreams()) dataVect.add(new ArrayList<DataElement>());
  }
}