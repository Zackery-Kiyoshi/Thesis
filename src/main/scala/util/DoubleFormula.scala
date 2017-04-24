package util

//import ScalaVis._
import scala.util.parsing.combinator._

class DoubleFormula(val str:String) {
  import DoubleFormula.IOList
    private val tree=DoubleFormula.parseAll(DoubleFormula.Expression,str).get
    def apply(i:Int,x:IOList,vars:Map[String,Double]):Double = tree.eval(i,x,vars)
    def safeRange(x:IOList,df:DoubleFormula*):Range = {
      return tree.safeRange(x) 
    }
}

object DoubleFormula extends JavaTokenParsers {
  type IOList = IndexedSeq[DataStore]
    private val allRange = -1000000000 to 1000000000
    
    def apply(str:String) = new DoubleFormula(str)
    def main(args:Array[String]) {
        println(new DoubleFormula("4+5")(0,null,null))
        println(new DoubleFormula("4+5*3")(0,null,null))
        println(new DoubleFormula("(4+5)*3")(0,null,null))
        println(new DoubleFormula("4+5-3")(0,null,null))
        println(new DoubleFormula("4*5/3")(0,null,null))
        println(new DoubleFormula("4+5*i")(0,null,Map(("i"->3.0))))
        var tmp1 = new DataStore()
        tmp1.set( Vector.empty :+ new DataElement(Vector(3.0,4.0)) )
        println(new BooleanFormula("5>3")(0,null,null))
        println(new DoubleFormula("if(5>3) 4+5*3 else 4+5")(0,null,null))
        // Original Test
        //println( new DoubleFormula("y[1]+5*x[0]")(0,IndexedSeq(tmp1),null) )
        println( new DoubleFormula("x[1]+5*x[0]")(0,IndexedSeq(tmp1),null) )
        //println( new DoubleFormula("x[i]+5*x[0]")(0,IndexedSeq(tmp1),Map(("i"->1))) )
        tmp1 = new DataStore()
        tmp1.set( Vector.empty :+ new DataElement(Vector(3.0,4.0)) )
        // Original Test
        //println(new DoubleFormula("y[0][1]+5*x[0][0]")(0,IndexedSeq( tmp1 ),null))
        println(new DoubleFormula("x[0][1]+5*x[0][0]")(0,IndexedSeq( tmp1 ),null))
        tmp1 = new DataStore()
        tmp1.set( Vector.empty :+ new DataElement(Vector(3.0,4.0)) )
        // Original Test
        //println(new DoubleFormula("y[0][0][1]+5*x[0][0][0]")(0,IndexedSeq(tmp1),null))
        println(new DoubleFormula("x[0][0][1]+5*x[0][0][0]")(0,IndexedSeq(tmp1),null))
        println("  multi-tests:")
        tmp1 = new DataStore()
        tmp1.set( Vector.empty :+ new DataElement(Vector(3.0)) :+ new DataElement(Vector(4.0)) )
        var tmp2 = new DataStore()
        tmp2.set( Vector.empty :+ new DataElement(Vector(5.0)) :+ new DataElement(Vector(6.0)) )
        var tmp3 = new DataStore()
        tmp3.set( Vector.empty :+ new DataElement(Vector(7.0)) :+ new DataElement(Vector(8.0)))
        var data = Vector.empty :+tmp1:+tmp2:+tmp3
        //println("HERE : " + data(2)(0)(0))
        var df = new DoubleFormula("x[0][0]+x[1][0]+x[2][0]")
        var r = safeRange(data ,df)
        println(r.start +":"+r.end)
        for(i <- r){
          println(df(i,data,null))
        }
        
        data = Vector.empty :+tmp1:+tmp2
        //println("HERE : " + data(2)(0)(0))
        df = new DoubleFormula("x[0][0]+x[1][0]")
        r = safeRange(data ,df)
        println(r.start +":"+r.end)
        for(i <- r){
          println(df(i,data,null))
        }
    }
    
    def safeRange(x:IOList,df:DoubleFormula*):Range = {
//        println("allRange = "+allRange)
        val ret=df.foldLeft(allRange)((r,dblf) => {
            val nr=dblf.tree.safeRange(x)
//            println(r+" :: "+nr)
            (r.head max nr.head) to (r.last min nr.last)
        } )
        if(ret==allRange) 0 until x.map(_.length).max 
        else ret
    }
    
    private def Expression:Parser[DoubleNode] = IfExpression | 
        AdditiveExpression
    private def IfExpression:Parser[DoubleNode] = 
        "if(" ~ BooleanExpression ~ ")" ~ Expression ~ "else" ~ Expression ^^ { case _ ~ cond ~ _ ~ texp ~ _ ~ fexp => new IfNode(cond,texp,fexp) }
    private[util] def BooleanExpression:Parser[BooleanNode] = 
        AndExpression ~ rep(OrOperator ~ AndExpression)  ^^ { case (start)~(rest) => if(rest.isEmpty) start else new BooleanTreeNode(start, rest) }
    private def AndExpression:Parser[BooleanNode] = 
        RelationalExpression ~ rep(AndOperator ~ RelationalExpression) ^^ { case (start)~(rest) => if(rest.isEmpty) start else new BooleanTreeNode(start, rest) }
    private def RelationalExpression:Parser[BooleanNode] = 
        AdditiveExpression ~ RelationalOperator ~ AdditiveExpression  ^^ { case (start)~(op)~(rest) => new RelationalTreeNode(start,op,rest) } |
        BooleanPrimaryExpression
    private def AdditiveExpression:Parser[DoubleNode] = 
        MultiplicativeExpression ~ rep(AdditiveOperator ~ MultiplicativeExpression)  ^^ { case (start)~(rest) => if(rest.isEmpty) start else new TreeNode(start, rest) }
    private def MultiplicativeExpression:Parser[DoubleNode] = 
        UnaryExpression ~ rep(MultiplicativeOperator ~ UnaryExpression) ^^ { case (start)~(rest) => if(rest.isEmpty) start else new TreeNode(start, rest) }
    private def UnaryExpression:Parser[DoubleNode] = PrimaryExpression | 
        UnaryOperator ~ UnaryExpression ^^ { case op ~ e => new UnaryNode(op,e) } 
    private def Constructor:Parser[String] = ident ~ repsep(".",ident) ^^ { case s1 ~ s2 => s1+s2.mkString }
    private def PrimaryExpression:Parser[DoubleNode] = "(" ~ Expression ~ ")" ^^ { case _ ~ e ~ _ => e}| 
        floatingPointNumber ^^ (s => new LiteralNode(s.toDouble) ) |
        "x[" ~ wholeNumber ~ "][" ~ wholeNumber ~ "][" ~ wholeNumber ~ "]" ^^ { case _ ~ input ~ _ ~ offset ~ _ ~ index ~ _=> new XNode(input.toInt,offset.toInt,index.toInt) } |
        "x[" ~ wholeNumber ~ "][" ~ wholeNumber ~ "]" ^^ { case _ ~ input ~ _ ~ index ~ _=> new XNode(input.toInt,0,index.toInt) } |
        "x[" ~ wholeNumber ~ "]" ^^ { case _ ~ index ~ _ => new XNode(0,0,index.toInt) } |
//        "y[" ~ wholeNumber ~ "][" ~ wholeNumber ~ "][" ~ wholeNumber ~ "]" ^^ { case _ ~ input ~ _ ~ offset ~ _ ~ index ~ _=> new YNode(input.toInt,offset.toInt,index.toInt) } |
//        "y[" ~ wholeNumber ~ "][" ~ wholeNumber ~ "]" ^^ { case _ ~ input ~ _ ~ index ~ _=> new YNode(input.toInt,0,index.toInt) } |
//        "y[" ~ wholeNumber ~ "]" ^^ { case _ ~ index ~ _ => new YNode(0,0,index.toInt) } |
//        "cy[" ~ wholeNumber ~ "][" ~ wholeNumber ~ "][" ~ wholeNumber ~ "]" ^^ { case _ ~ input ~ _ ~ offset ~ _ ~ index ~ _=> new CYNode(input.toInt,offset.toInt,index.toInt) } |
//        "cy[" ~ wholeNumber ~ "][" ~ wholeNumber ~ "]" ^^ { case _ ~ input ~ _ ~ index ~ _=> new CYNode(input.toInt,0,index.toInt) } |
//        "cy[" ~ wholeNumber ~ "]" ^^ { case _ ~ index ~ _ => new CYNode(0,0,index.toInt) } |
        ident ^^ (s => new VarNode(s))
    private def BooleanPrimaryExpression:Parser[BooleanNode] = "(" ~ BooleanExpression ~ ")" ^^ { case _ ~ e ~ _ => e} | 
        "false" ^^ (_ => new BooleanLiteralNode(false)) | 
        "true" ^^ (_ => new BooleanLiteralNode(true)) 
    
    private def RelationalOperator:Parser[(Double,Double)=>Boolean] = "==" ^^ (_ => (a:Any,b:Any) => a==b ) | 
        "!=" ^^ (_ => (a:Double,b:Double) => a!=b ) | 
        "<" ^^ (_ => (a:Double,b:Double) => a<b) | 
        ">" ^^ (_ => (a:Double,b:Double) => a>b) | 
        "<=" ^^ (_ => (a:Double,b:Double) => a<=b) |
        ">=" ^^ (_ => (a:Double,b:Double) => a>=b)            
    private def MultiplicativeOperator:Parser[(Double,Double)=>Double] = 
        "*" ^^ (_ => (a:Double,b:Double) => a*b ) | 
        "/" ^^ (_ => (a:Double,b:Double) => a/b ) | 
        "%" ^^ (_ => (a:Double,b:Double) => a%b )
    private def AdditiveOperator:Parser[(Double,Double)=>Double] = 
        "+" ^^ (_ => (a:Double,b:Double) => a+b ) | 
        "-" ^^ (_ => (a:Double,b:Double) => a-b )
    private def UnaryOperator:Parser[(Double)=>Double] = 
        "-" ^^ (_ => (a:Double) => -a ) | 
        "+" ^^ (_ => (a:Double) => a ) 
    private def BooleanUnaryOperator:Parser[(Boolean)=>Boolean] = 
        "!" ^^ (_ => (a:Boolean) => !a)
    private def AndOperator:Parser[(Boolean,Boolean)=>Boolean] = 
        "&&" ^^ (_ => (a:Boolean,b:Boolean) => a && b ) 
    private def OrOperator:Parser[(Boolean,Boolean)=>Boolean] = 
        "||" ^^ (_ => (a:Boolean,b:Boolean) => a || b) 

    private trait DoubleNode { 
        def eval(i:Int,x:IOList,vars:Map[String,Double]):Double
        def safeRange(x:IOList):Range
    }
    private class LiteralNode(v:Double) extends DoubleNode {
        override def eval(i:Int,x:IOList,vars:Map[String,Double]):Double = v
        def safeRange(x:IOList):Range = allRange
        override def toString:String = "Lit="+v
    }
    private class VarNode(val name:String) extends DoubleNode {
        override def eval(i:Int,x:IOList,vars:Map[String,Double]):Double = vars(name)
        def safeRange(x:IOList):Range = allRange
    }
    private case class XNode(input:Int,offset:Int,index:Int) extends DoubleNode {
        override def eval(i:Int,x:IOList,vars:Map[String,Double]):Double = x(input)(i+offset)(index)
        def safeRange(x:IOList):Range = -offset until x(input).length-offset
    }
    private class UnaryNode(op:(Double)=>Double,node:DoubleNode) extends DoubleNode {
        override def eval(i:Int,x:IOList,vars:Map[String,Double]):Double = op(node.eval(i,x,vars))
        def safeRange(x:IOList):Range = node.safeRange(x)
    }
    private class TreeNode(start: DoubleNode, ops: List[~[(Double,Double) => Double,DoubleNode]]) extends DoubleNode {
        override def eval(i:Int,x:IOList,vars: Map[String, Double]): Double = (ops foldLeft start.eval(i,x,vars)){(res, e) => e._1(res, e._2.eval(i,x,vars))}
        def safeRange(x:IOList):Range = ops.foldLeft(start.safeRange(x))((r,t) => {
            val nr=t._2.safeRange(x)
            if(nr.isEmpty) {
              val xn = t._2.asInstanceOf[XNode]
              println("nr empty from "+xn)
            }
            (r.head max nr.head) to (r.last min nr.last)
        } )
        override def toString:String = "TreeNode "+start+" "+ops
    }
    private class IfNode(cond:BooleanNode,texp:DoubleNode,fexp:DoubleNode) extends DoubleNode {
        override def eval(i:Int,x:IOList,vars:Map[String,Double]):Double = if(cond.eval(i,x,vars)) texp.eval(i,x,vars) else fexp.eval(i,x,vars)
        def safeRange(x:IOList):Range = {
            val cr = cond.safeRange(x)
            val tr = texp.safeRange(x)
            val fr = fexp.safeRange(x)
            (cr.head max tr.head max fr.head) to (cr.last min tr.last min fr.last)
        }
    }
    
    private[util] trait BooleanNode { 
        def eval(i:Int,x:IOList,vars:Map[String,Double]):Boolean
        def safeRange(x:IOList):Range
    }
    private class BooleanLiteralNode(v:Boolean) extends BooleanNode {
        override def eval(i:Int,x:IOList,vars:Map[String,Double]):Boolean = v
        def safeRange(x:IOList):Range = allRange
        override def toString:String = "Lit="+v
    }
    private class BooleanUnaryNode(op:(Boolean)=>Boolean,node:BooleanNode) extends BooleanNode {
        override def eval(i:Int,x:IOList,vars:Map[String,Double]):Boolean = op(node.eval(i,x,vars))
        def safeRange(x:IOList):Range = node.safeRange(x)
    }
    private class BooleanTreeNode(start: BooleanNode, ops: List[~[(Boolean,Boolean) => Boolean,BooleanNode]]) extends BooleanNode {
        override def eval(i:Int,x:IOList,vars: Map[String,Double]): Boolean = (ops foldLeft start.eval(i,x,vars)){(res, e) => e._1(res, e._2.eval(i,x,vars))}
        def safeRange(x:IOList):Range = ops.foldLeft(start.safeRange(x))((r,t) => {
            val nr=t._2.safeRange(x)
            (r.head max nr.head) to (r.last min nr.last)
        } )
        override def toString:String = "TreeNode "+start+" "+ops
    }

    private class RelationalTreeNode(start: DoubleNode, op: (Double,Double) => Boolean, rest:DoubleNode) extends BooleanNode {
        override def eval(i:Int,x:IOList,vars: Map[String,Double]): Boolean = op(start.eval(i,x,vars),rest.eval(i,x,vars))
        def safeRange(x:IOList):Range = {
            val sr = start.safeRange(x)
            val rr = rest.safeRange(x)
            (sr.head max rr.head) to (sr.last min rr.last)
        }
    }
}
