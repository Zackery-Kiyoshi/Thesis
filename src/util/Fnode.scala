package util

/**
 * @author zkurimab
 */
class Fnode (var func:Function){
  var next:Vector[DataStore] = Vector.empty;
  var prev:Vector[DataStore]= Vector.empty;
  
  
  def update(){
    //get data from input
    var input:Vector[Vector[DataElement]] = Vector.empty
    for(x <- prev){
      input = input :+ x.getData()
    }
    
    // 
    var outputData = func(input)
    
    for(i <- 0 until outputData.length){
      var tmpNext = next(i).next
      
      var newDataStore = new DataStore(outputData(i),this)
      newDataStore.next = tmpNext
      // updates the output
      for(j <- 0 until newDataStore.next.length){
        newDataStore.next(j).addInput(newDataStore)
      }
      // removes links from old datastore to Fnodes
      for(j <- 0 until newDataStore.next.length){
        next(i).removeOutput( newDataStore.next(j))
      }
      for(j <- 0 until newDataStore.next.length){
        newDataStore.next(j).removeInput(next(i))
      }
      
    }
    
  }
  
  def addOutput(n:DataStore){
    next = next :+ n
  }
  /*
  def removeOutput(i:Int){
    var tmpOut:Vector[DataStore] = Vector.empty
    for(c <- 0 until next.length){
      if(c != i) tmpOut = tmpOut :+ next(c)
    }
    next = tmpOut
  }
  
  def removeOutput(c:DataStore){
    // think about/ is it necessary?
    var tmpOut:Vector[DataStore] = Vector.empty
    for(n <- 0 until next.length){
      if(c != next(n)) tmpOut = tmpOut :+ next(n)
    }
    next = tmpOut
  }
  */
  def addInput(n:DataStore){
    prev = prev :+ n
  }
  
  def removeInput(i:Int){
    var tmpOut:Vector[DataStore] = Vector.empty
    for(c <- 0 until prev.length){
      if(c != i) tmpOut = tmpOut :+ prev(c)
    }
    prev = tmpOut
  }
  
  def removeInput(c:DataStore){
    // think about/ is it necessary?
    var tmpOut:Vector[DataStore] = Vector.empty
    for(n <- 0 until prev.length){
      if(c != prev(n)) tmpOut = tmpOut :+ prev(n)
    }
    prev = tmpOut
  }
}