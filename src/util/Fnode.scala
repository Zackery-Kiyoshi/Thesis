package util

/**
 * @author zkurimab
 */
class Fnode (var func:Function){
  
  
  
  def update(input:Vector[Vector[DataElement]]):Vector[Vector[DataElement]]={
    var outputData = func(input)
    
    return outputData
    
  }
  

  
}