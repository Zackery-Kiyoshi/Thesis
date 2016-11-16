package util

import scala.collection.mutable.ArrayBuffer

class LinearFitFilter(val s:String = "-1") extends Filter() {
  
  val t:String = "LinearFitFilter"
  
  private var fitFormula:DoubleFormula = new DoubleFormula(s);
  private var terms:ArrayBuffer[DoubleFormula] = new ArrayBuffer();
  private var coefs:Array[Float]=new Array(0)
  
  override def apply(input: Vector[DataStore]): Vector[DataStore] = {
    var ret = Vector[DataStore]()
    for(s <-0 until input.length ) {
//       /*
            var a:Array[Array[Double]] =new Array(terms.size)(terms.size)
            var b:Array[Double] = new Array(terms.size)
            var range:Array[Int]=fitFormula.safeRange(input).toArray
            for( t <- 0 until terms.length) {
                var tmp:Range =terms(t).safeRange(input)
                if(tmp(0)>range(0)) range(0)=tmp(0)
                if(tmp(1)<range(1)) range(1)=tmp(1) 
            }
         //   DataFormula.checkRangeSafety(range,this);
            for(i <- range(0) until range(1)  ) {
                var ti:Array[Double]=new Array(terms.size)
                for(j <- 0 until ti.length) ti(j)=terms(j)(i,input,null );
                var fit=fitFormula(i,input,null)
                for(j <- 0 until a.length) {
                    for(k <- 0 until a(j).length) {
                        a(j)(k)+=ti(j)*ti(k)
                    }
                    b(j)+=ti(j)*fit
                }
            }

            var p:Array[Int]=LUPDecompose(a);
            var x:Array[Double]=LUPSolve(a,p,b);
            for(i <- 0 until x.length) {
                coefs(i)= x(i).toFloat
            }
            // new DataElement(new Array[Int](0),coefs)
            var tmp = new DataElement(Vector.empty)
         //   dataVect.get(2*s).add(tmp)
//           input.get(2*s).add(tmp)
            var params:Array[Int] =new Array(input(s).length)
            var values:Array[Float]=new Array(input(s).length+2)
            for(i <- range(0) until range(1)) {
                var de:DataElement=input(i)(s);
                for(j <- 0 until params.length) params(j)=de(j).toInt
                for(j <- 0 until values.length-2) values(j)=de(j).toFloat
                values(values.length-2)=fitFormula(i,input, null).toFloat
                values(values.length-1)=0;
                for(j <- 0 until terms.size) {
                    values(values.length-1)+=coefs(j)*terms(j)(i,input,null).toFloat
                }
        //        dataVect.get(2*s+1).add(new DataElement(params,values));
                var r:Vector[Double] = Vector.empty
                for(i <- 0 until values.length) r = r :+ values(i).toDouble
                
                // ret?
                //  dataVect.get(2*s+1).add(new DataElement(r))
                
                var tmp = new DataStore()
                tmp.set(Vector.empty :+(new DataElement(r)))
                ret = ret :+ tmp
            }
//            */
        }
    return ret
  }
  
  def LUPDecompose(a:Array[Array[Double]]):Array[Int]= {
        var n:Int = a.length
        var pi:Array[Int] = new Array(n)
        for(i <- 0 until n) {
            pi(i)=i
        }
        for(k <- 0 until n) {
            var p:Double =0
            var kp:Int = -1
            for(i <- k until n) {
                if(Math.abs(a(i)(k))>p) {
                    p=Math.abs(a(i)(k))
                    kp=i
                }
            }
            if(p==0) throw new IllegalArgumentException("Singular matrix for linear fit.")
            var tmp:Int =pi(kp)
            pi(kp)=pi(k)
            pi(k) = tmp
            var tmp2:Array[Double]=a(kp)
            a(kp)=a(k)
            a(k)=tmp2
            for(i <- k+1 until n) {
                a(i)(k)/=a(k)(k)
                for(j <- k+1 until n) {
                    a(i)(j) -= a(i)(k)*a(k)(j)
                }
            }
        }
        return pi;
    }
  
  def LUPSolve(lu:Array[Array[Double]], p:Array[Int],b:Array[Double]):Array[Double]= {
        var n:Int=lu.length
        var x:Array[Double] = new Array(n)
        var y:Array[Double] = new Array(n)
        for(i <- 0 until n) {
            y(i)=b(p(i))
            for(j <- 0 until i) {
                y(i)-=lu(i)(j)*y(j)
            }
        }
        for(i <- n-1 to 0 by -1) {
            x(i)=y(i)
            for(j <- i+1 until n) x(i)-=lu(i)(j)*x(j)
            x(i)/=lu(i)(i);
        }
        return x;
    }
  
}

/*

protected void redoAllElements() {
        sizeDataVectToInputStreams();
        for(int s=0; s<input.getNumStreams(); ++s) {
            double[][] a=new double[terms.size()][terms.size()];
            double[] b=new double[terms.size()];
            int[] range=fitFormula.getSafeElementRange(this,s);
            for(DataFormula t:terms) {
                int[] tmp=t.getSafeElementRange(this,s);
                if(tmp[0]>range[0]) range[0]=tmp[0];
                if(tmp[1]<range[1]) range[1]=tmp[1]; 
            }
            DataFormula.checkRangeSafety(range,this);
            for(int i=range[0]; i<range[1]; ++i) {
                double[] ti=new double[terms.size()];
                for(int j=0; j<ti.length; ++j) ti[j]=terms.get(j).valueOf(this,s,i);
                double fit=fitFormula.valueOf(this,s,i);
                for(int j=0; j<a.length; ++j) {
                    for(int k=0; k<a[j].length; ++k) {
                        a[j][k]+=ti[j]*ti[k];
                    }
                    b[j]+=ti[j]*fit;
                }
            }

            int[] p=LUPDecompose(a);
            double[] x=LUPSolve(a,p,b);
            for(int i=0; i<x.length; ++i) {
                coefs[i]=(float)x[i];
            }

            dataVect.get(2*s).add(new DataElement(new int[0],coefs));
            int[] params=new int[input.getNumParameters(s)];
            float[] values=new float[input.getNumValues(s)+2];
            for(int i=range[0]; i<range[1]; ++i) {
                DataElement de=input.getElement(i,s);
                for(int j=0; j<params.length; ++j) params[j]=de.getParam(j);
                for(int j=0; j<values.length-2; ++j) values[j]=de.getValue(j);
                values[values.length-2]=(float)fitFormula.valueOf(this,s,i);
                values[values.length-1]=0;
                for(int j=0; j<terms.size(); ++j) {
                    values[values.length-1]+=coefs[j]*terms.get(j).valueOf(this,s,i);
                }
                dataVect.get(2*s+1).add(new DataElement(params,values));
            }
        }
    }

    public static int[] LUPDecompose(double[][] a) {
        int n=a.length;
        int[] pi=new int[n];
        for(int i=0; i<n; ++i) {
            pi[i]=i;
        }
        for(int k=0; k<n; ++k) {
            double p=0;
            int kp=-1;
            for(int i=k; i<n; ++i) {
                if(Math.abs(a[i][k])>p) {
                    p=Math.abs(a[i][k]);
                    kp=i;
                }
            }
            if(p==0) throw new IllegalArgumentException("Singular matrix for linear fit.");
            int tmp=pi[kp];
            pi[kp]=pi[k];
            pi[k]=tmp;
            double[] tmp2=a[kp];
            a[kp]=a[k];
            a[k]=tmp2;
            for(int i=k+1; i<n; ++i) {
                a[i][k]/=a[k][k];
                for(int j=k+1; j<n; ++j) {
                    a[i][j]-=a[i][k]*a[k][j];
                }
            }
        }
        return pi;
    }

    public static double[] LUPSolve(double[][] lu,int[] p,double[] b) {
        int n=lu.length;
        double[] x=new double[n];
        double[] y=new double[n];
        for(int i=0; i<n; ++i) {
            y[i]=b[p[i]];
            for(int j=0; j<i; ++j) {
                y[i]-=lu[i][j]*y[j];
            }
        }
        for(int i=n-1; i>=0; --i) {
            x[i]=y[i];
            for(int j=i+1; j<n; ++j) x[i]-=lu[i][j]*x[j];
            x[i]/=lu[i][i];
        }
        return x;
    }

*/

object LinearFitFilter {
    def main(args:Array[String]) {
        
        
    }
}