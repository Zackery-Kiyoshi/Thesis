package sequential

import util._

/**
 * @author zkurimab
 */
class SeqNode(var c:Container) extends Node{
  
  def run(){
    c.apply()
  }
  
  def updateInput(data:Vector[DataElement]){
    //c.inputData = data
  }
  
  def updateOutput(){
    c.apply()
  }
  
}