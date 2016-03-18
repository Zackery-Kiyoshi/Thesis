package sequential

import util._

/**
 * @author zkurimab
 */
class SeqNode(var c:Container) extends Node{
  
  def run(){
    c.apply()
  }
  
}