package util


import scala.util.parsing.combinator._

class BooleanFormula(val str:String) {
  import BooleanFormula.IOList
  
    val tree=DoubleFormula.parseAll(DoubleFormula.BooleanExpression,str).get
    def apply(i:Int,s:Int,x:IOList,vars:Map[String,Double]):Boolean = tree.eval(i,x,vars)
}

object BooleanFormula {
  type IOList = IndexedSeq[DataStore]
    def safeRange(s:Int,x:IOList,y:IOList,df:BooleanFormula*):Range = {
        // TODO
        1 to 100
    }

}
