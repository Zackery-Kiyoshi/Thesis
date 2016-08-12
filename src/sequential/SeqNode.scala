package sequential

import util._
import scala.collection.mutable.ListBuffer

/**
 * @author zkurimab
 */
class SeqNode(var c:Container) extends Node{
  var in:Vector[Node] = Vector[Node]()
  var out:Vector[Node] = Vector[Node]()
  
  def id():String={
    c.id.key
  }
  
  def addInput(n:Node){
    in = in:+n
  }
  
  def removeInput(n:Node){
    var tmpIn = Vector[Node]()
    for(i <- in){
      if(i!=n) tmpIn = tmpIn:+i
    }
    in = tmpIn
  }
  
  def addOutput(n:Node){
    out = out:+n
  }
  
  def removeOutput(n:Node){
    var tmpOut = Vector[Node]()
    for( i <- out){
      if(i != n) tmpOut=tmpOut:+i
    }
    out = tmpOut
  }
  
  def getOutput():Vector[Vector[DataElement]]={
    c.out
  }
  
  def run(){
    // update inputs
    var tmpInputs = Vector[Vector[DataElement]]()
    for(i<-in){
      var tmp = i.getOutput()
      for(j<-tmp){
        tmpInputs = tmpInputs :+ j
      }
    }
    c.in = tmpInputs
    c.apply()
    
    //update outputs
    for(i<-out){
      i.run()
    }
    
  }
  
  def copy():Node={
    var newNode = new SeqNode(this.c)
    newNode.in = this.in
    newNode.out = this.out
    
    newNode
  }
  
}