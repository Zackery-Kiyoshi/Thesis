package util

// optional

class GroupNumberingFilter() extends Filter() {
  
  val t = "GroupNumberingFilter"
  
  def apply(){
    
    
  }
  
}

/*

protected void redoAllElements() {
        if(input==null) return;
        sizeDataVectToInputStreams();
        for(int s=0; s<getSource(0).getNumStreams(); ++s) {
            int[] newPars=new int[2];
            newPars[0]=0;
            int[] range=groupFormula.getSafeElementRange(this,s);
            DataFormula.checkRangeSafety(range,this);
            for(int i=range[0]; i<range[1]; ++i) {
                newPars[1]=0;
                double groupValue=groupFormula.valueOf(this,s,i);
                double curValue=groupValue;
                int j=i;
                while(curValue==groupValue && j<range[1]) {
                    dataVect.get(s).add(new DataElement(input.getElement(j,s),newPars));
                    newPars[1]++;
                    ++j;
                    if(j<range[1]) curValue=groupFormula.valueOf(this,s,j);
                }
                i=j-1;
                newPars[0]++;
            }
        }
    }

*/

object GroupNumberingFilter {
    def main(args:Array[String]) {
        
        
    }
}