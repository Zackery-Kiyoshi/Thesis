package util

/**
 * @author zkurimab
  
 */
// Wrapper so nodes can have any kind of Container


abstract class Filter() {
  
  val t:String
  
  //def apply(inputData:Vector[Vector[DataElement]]):Vector[Vector[DataElement]]={
  def apply(input:Vector[DataStore]):Vector[DataStore] = {
    var ret = Vector[DataStore]()
    
    return ret;
  }
  
  def update(changes:NodeChange){
    
  }
  
  
}