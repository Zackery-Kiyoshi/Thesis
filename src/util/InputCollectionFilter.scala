package util

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class InputCollectionFilter extends Function {
  
  var inputCount:Int = 0;
  
  override def apply(input:Vector[DataStore]):Vector[DataStore] = {
    var ret = Vector[DataStore]()
    var lastToRemove:Int = -1
    //sizeDataVectToInputStreams();
//    /*
    for(s <- 0 until getSource(0).getNumStreams()) {
      breakable { 
      for(lastToRemove <- 0 until dataVect.get(s).size())
        if(!(dataVect.get(s).get(lastToRemove).getParam(input.getNumParameters(s))<inputCount-numToKeep.getValue()+1)) break
      }
      if(lastToRemove>0) {
        var toRemove:ArrayBuffer[DataElement] = new ArrayBuffer();
        //toRemove.ensureCapacity(lastToRemove);
        for(i <- 0 until lastToRemove) {
          toRemove = toRemove :+ input(s)(i)
        }
        dataVect.get(s).removeAll(toRemove);
      }
      for(i <- 0 until input(s).length) {
        dataVect.get(s).add(new DataElement(input.getElement(i,s),inputCount));
      }
    }
    inputCount+=1
//    */
    return ret;
  }
  
   
}

/*

protected void redoAllElements() {
        int lastToRemove;
        sizeDataVectToInputStreams();
        for(int s=0; s<getSource(0).getNumStreams(); ++s) {
            for(lastToRemove=0; lastToRemove<dataVect.get(s).size() && dataVect.get(s).get(lastToRemove).getParam(input.getNumParameters(s))<inputCount-numToKeep.getValue()+1; ++lastToRemove);
            if(lastToRemove>0) {
                ArrayList<DataElement> toRemove=new ArrayList<DataElement>();
                toRemove.ensureCapacity(lastToRemove);
                for(int i=0; i<lastToRemove; ++i) {
                    toRemove.add(dataVect.get(s).get(i));
                }
                dataVect.get(s).removeAll(toRemove);
            }
            for(int i=0; i<input.getNumElements(s); ++i) {
                dataVect.get(s).add(new DataElement(input.getElement(i,s),inputCount));
            }
        }
        inputCount++;
    }

*/

/*

/*
 * Created on Dec 25, 2007
 */
package util;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.swri.swiftvis.DataElement;
import edu.swri.swiftvis.GraphElement;
import edu.swri.swiftvis.util.EditableInt;

public class OldInputCollectionFilter extends AbstractSingleSourceFilter {
    public OldInputCollectionFilter() {

    }

    public OldInputCollectionFilter(OldInputCollectionFilter c,List<GraphElement> l) {
        super(c,l);
        numToKeep=new EditableInt(c.numToKeep.getValue());
        inputCount=c.inputCount;
    }

    @Override
    protected boolean doingInThreads() {
        return false;
    }

    @Override
    protected void redoAllElements() {
        int lastToRemove;
        sizeDataVectToInputStreams();
        for(int s=0; s<getSource(0).getNumStreams(); ++s) {
            for(lastToRemove=0; lastToRemove<dataVect.get(s).size() && dataVect.get(s).get(lastToRemove).getParam(input.getNumParameters(s))<inputCount-numToKeep.getValue()+1; ++lastToRemove);
            if(lastToRemove>0) {
                ArrayList<DataElement> toRemove=new ArrayList<DataElement>();
                toRemove.ensureCapacity(lastToRemove);
                for(int i=0; i<lastToRemove; ++i) {
                    toRemove.add(dataVect.get(s).get(i));
                }
                dataVect.get(s).removeAll(toRemove);
            }
            for(int i=0; i<input.getNumElements(s); ++i) {
                dataVect.get(s).add(new DataElement(input.getElement(i,s),inputCount));
            }
        }
        inputCount++;
    }

    @Override
    protected void setupSpecificPanelProperties() {
        JPanel outerPanel=new JPanel(new BorderLayout());
        JPanel panel=new JPanel(new GridLayout(2,1));
        panel.add(numToKeep.getLabeledTextField("Number of inputs to keep:",null));
        JButton button=new JButton("Reset");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(ArrayList<DataElement> al:dataVect) {
                    al.clear();
                }
                inputCount=0;
                abstractRedoAllElements();
            }
        });
        panel.add(button);
        outerPanel.add(panel,BorderLayout.NORTH);
        propPanel.add("Settings",outerPanel);
    }

    @Override
    public int getNumParameters(int stream) {
        if(input==null) return 0;
        return input.getNumParameters(stream)+1;
    }

    @Override
    public int getNumValues(int stream) {
        if(input==null) return 0;
        return input.getNumValues(stream);
    }

    @Override
    public String getParameterDescription(int stream, int which) {
        if(input==null) return "";
        if(which>=input.getNumParameters(stream)) return "Input Count";
        return input.getParameterDescription(stream,which);
    }

    @Override
    public String getValueDescription(int stream, int which) {
        if(input==null) return "";
        return input.getValueDescription(stream,which);
    }

    @Override
    public GraphElement copy(List<GraphElement> l) {
        return new OldInputCollectionFilter(this,l);
    }

    @Override
    public String getDescription() {
        return "Input Collection Filter";
    }

    public static String getTypeDescription() {
        return "Input Collection Filter";
    }

    @Override
    protected boolean clearOnRedo() {
        return false;
    }

    private EditableInt numToKeep=new EditableInt(10000);
    private int inputCount=0;

    private static final long serialVersionUID = -4026987942190303075L;
}


*/