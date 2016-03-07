package sequential

import util._

/**
 * @author zkurimab
 */
class SeqNode(var c:Container) {
  
  def run(){
    c.apply()
  }
  
}