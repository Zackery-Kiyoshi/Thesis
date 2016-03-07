package sequential

import scala.collection.immutable.Queue._
import scala.collection.mutable.Queue

class Scheduler(var nodes:Array[SeqNode], var graphIn:Array[Array[Boolean]])  {
  
  var runQueue = new Queue[SeqNode]
  
  def makeSchedule() {
    // find start nodes
    runQueue = new Queue[SeqNode]  
    var startNodes = new Queue[SeqNode]
    var startNodesI = new Queue[Int]
    var Nodes:Array[Boolean] = new Array(nodes.length);
    
    for( i <- 0 until nodes.length ){
      var start = true;
      for( j <- 0 until graphIn(i).length ){
        if( j != i){
          if( graphIn(i)(j) ) start = false;
        }
      }
      if(start) {
        startNodes.enqueue(nodes(i))
        startNodesI.enqueue(i)
        Nodes(i) = true
      }
    }
    
    var nodesLeft = startNodes
    runQueue = startNodes
    
    while( !nodesLeft.isEmpty ){
      var newNodes = new Queue[SeqNode]
      while( !nodesLeft.isEmpty ){
        nodesLeft.dequeue()
        val t = startNodesI.dequeue()
        for( tmp <- 0 until graphIn(t).length){
          if(graphIn(t)(tmp) && !Nodes(tmp) ){
            // need to check that it could be processed
            var canAdd = true;
            for(i <- 0 until graphIn.length){
              if( !Nodes(i) && graphIn(i)(t) ) canAdd = false
            }
            if(canAdd){
              newNodes.enqueue(nodes(tmp))
              startNodesI.enqueue(tmp)
1           }
          }
        }
      }
      
      while( !newNodes.isEmpty) {
        var tmp = newNodes.dequeue()
        runQueue.enqueue(tmp)
        nodesLeft.enqueue(tmp)
      }
    }
    
  }
  
  def run(){
    var tmpRun = runQueue;
    
    while( !tmpRun.isEmpty){
      var cur = tmpRun.dequeue()
      cur.run();
    }
    
  }
}



