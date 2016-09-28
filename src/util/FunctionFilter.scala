package util

class FunctionFilter extends Function {

  
  
  override def apply(input: Vector[DataStore]): Vector[DataStore] = {
    var ret = Vector[DataStore]()

    for (i <- input) {
      //var tmp:DataStore = i
      var tmpDE:Vector[DataElement] = Vector.empty[DataElement]
      for (j <- 0 until i.length) {
        //var tmp:DataElement = i(j)
        var tmp = new Array[Double](i(j).length)
        for (k <- 0 until i(j).length) {
          // do it for i(j)(k)
          tmp(k) = i(j)(k)
        }
        var De = new DataElement(tmp.toVector)
        tmpDE = tmpDE :+ De 
      }
      var t = new DataStore(new DKey(""))
      t.set(tmpDE)
      ret = ret :+ t
    }

    return ret;
  }

}


/*

protected void redoAllElements() {
        sizeDataVectToInputStreams();
        for(int s=0; s<inputVector.get(0).getNumStreams(); ++s) {
            final int ss=s;
            final int[] indexBounds=new int[]{0,inputVector.get(0).getNumElements(s)};
            for(FunctionEntry fe:paramEntry) {
                DataFormula.mergeSafeElementRanges(indexBounds,fe.formula.getSafeElementRange(this,s));
            }
            for(FunctionEntry fe:valueEntry) {
                DataFormula.mergeSafeElementRanges(indexBounds,fe.formula.getSafeElementRange(this,s));
            }
            if(indexBounds==null) return;
            DataFormula.checkRangeSafety(indexBounds,this);
            dataVect.get(s).ensureCapacity(indexBounds[1]-indexBounds[0]);
            for (int i=0; i<indexBounds[1]-indexBounds[0]; i++) {
                dataVect.get(s).add(null);
            }
            ReduceLoopBody[] threadBody=new ReduceLoopBody[ThreadHandler.instance().getNumThreads()];
            for(int i=0; i<ThreadHandler.instance().getNumThreads(); ++i) {
                threadBody[i]=new ReduceLoopBody() {
                    @Override
                    public void execute(int start, int end) {
                        for(int i=start; i<end; ++i) {
                            for(int j=0; j<paramArray.length; j++) {
                                paramArray[j]=(int)(pForms[j].valueOf(OldFunctionFilter.this,ss,i));
                            }
                            for(int j=0; j<valueArray.length; j++) {
                                valueArray[j]=(float)(vForms[j].valueOf(OldFunctionFilter.this,ss,i));
                            }
                            dataVect.get(ss).set(i - indexBounds[0],new DataElement(paramArray,valueArray));
                        }
                    }
                    final DataFormula[] pForms=buildParamFormulas();
                    final DataFormula[] vForms=buildValueFormulas();
                    final int[] paramArray=new int[paramEntry.size()];
                    final float[] valueArray=new float[valueEntry.size()];
                };
            }
            ThreadHandler.instance().chunkedForLoop(this,indexBounds[0], indexBounds[1], threadBody);
        }
    }

*/

/*


/* Generated by Together */

package util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.swri.swiftvis.DataElement;
import edu.swri.swiftvis.DataFormula;
import edu.swri.swiftvis.DataSource;
import edu.swri.swiftvis.GraphElement;
import edu.swri.swiftvis.util.EditableString;
import edu.swri.swiftvis.util.ReduceLoopBody;
import edu.swri.swiftvis.util.ThreadHandler;

public class OldFunctionFilter extends AbstractMultipleSourceFilter {
    public OldFunctionFilter() {
        valueEntry=new ArrayList<FunctionEntry>();
        paramEntry=new ArrayList<FunctionEntry>();
    }

    private OldFunctionFilter(OldFunctionFilter c,List<GraphElement> l) {
        super(c,l);
        valueEntry=new ArrayList<FunctionEntry>();
        for(FunctionEntry fe:c.valueEntry) {
            valueEntry.add(new FunctionEntry(fe));
        }
        paramEntry=new ArrayList<FunctionEntry>();
        for(FunctionEntry fe:c.paramEntry) {
            paramEntry.add(new FunctionEntry(fe));
        }
    }

    @Override
    public String getDescription(){ return "Function Filter"; }

    public static String getTypeDescription(){ return "Function Filter"; }

    @Override
    protected void setupSpecificPanelProperties(){
        JPanel outerPanel=new JPanel(new BorderLayout());
        JPanel panel=new JPanel(new BorderLayout());
        JButton button=new JButton("Mirror Values");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { mirrorValues(); }
        } );
        panel.add(button,BorderLayout.NORTH);
        valueList=new JList();
        valueList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) { valueListSelection(); }
        } );
        valueList.setListData(valueEntry.toArray());
        valueList.setMinimumSize(new Dimension(10,100));
        panel.add(new JScrollPane(valueList),BorderLayout.CENTER);
        JPanel tmpPanel=new JPanel(new GridLayout(1,2));
        JButton newButton=new JButton("New");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { newValue(); }
        } );
        tmpPanel.add(newButton);
        newButton=new JButton("Remove");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { removeValue(); }
        } );
        tmpPanel.add(newButton);
        panel.add(tmpPanel,BorderLayout.SOUTH);
        outerPanel.add(panel,BorderLayout.NORTH);
        valuePanel=new JPanel(new GridLayout(1,1));
        outerPanel.add(valuePanel,BorderLayout.CENTER);
        button=new JButton("Propagate Changes");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { abstractRedoAllElements(); }
        } );
        outerPanel.add(button,BorderLayout.SOUTH);
        propPanel.add("Values",outerPanel);

        outerPanel=new JPanel(new BorderLayout());
        panel=new JPanel(new BorderLayout());
        button=new JButton("Mirror Parameters");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { mirrorParams(); }
        } );
        panel.add(button,BorderLayout.NORTH);
        paramList=new JList();
        paramList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) { paramListSelection(); }
        } );
        paramList.setListData(paramEntry.toArray());
        paramList.setMinimumSize(new Dimension(10,100));
        panel.add(new JScrollPane(paramList),BorderLayout.CENTER);
        tmpPanel=new JPanel(new GridLayout(1,2));
        newButton=new JButton("New");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { newParam(); }
        } );
        tmpPanel.add(newButton);
        newButton=new JButton("Remove");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { removeParam(); }
        } );
        tmpPanel.add(newButton);
        panel.add(tmpPanel,BorderLayout.SOUTH);
        outerPanel.add(panel,BorderLayout.NORTH);
        paramPanel=new JPanel(new GridLayout(1,1));
        outerPanel.add(paramPanel,BorderLayout.CENTER);
        button=new JButton("Propagate Changes");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { abstractRedoAllElements(); }
        } );
        outerPanel.add(button,BorderLayout.SOUTH);
        propPanel.add("Parameters",outerPanel);
    }

    /**
     * Tells you what a particular parameter is used for.
     */
    @Override
    public String getParameterDescription(int stream, int which){
        return paramEntry.get(which).description.getValue();
    }

    /**
     * Tells you what a particular value is used for.
     */
    @Override
    public String getValueDescription(int stream, int which){
        return valueEntry.get(which).description.getValue();
    }

    @Override
    public int getNumParameters(int stream){
        return paramEntry.size();
    }

    @Override
    public int getNumValues(int stream){
        return valueEntry.size();
    }

    @Override
    public OldFunctionFilter copy(List<GraphElement> l) {
        return new OldFunctionFilter(this,l);
    }

    @Override
    protected boolean doingInThreads() {
        return true;
    }

    @Override
    protected void redoAllElements() {
        sizeDataVectToInputStreams();
        for(int s=0; s<inputVector.get(0).getNumStreams(); ++s) {
            final int ss=s;
            final int[] indexBounds=new int[]{0,inputVector.get(0).getNumElements(s)};
            for(FunctionEntry fe:paramEntry) {
                DataFormula.mergeSafeElementRanges(indexBounds,fe.formula.getSafeElementRange(this,s));
            }
            for(FunctionEntry fe:valueEntry) {
                DataFormula.mergeSafeElementRanges(indexBounds,fe.formula.getSafeElementRange(this,s));
            }
            if(indexBounds==null) return;
            DataFormula.checkRangeSafety(indexBounds,this);
            dataVect.get(s).ensureCapacity(indexBounds[1]-indexBounds[0]);
            for (int i=0; i<indexBounds[1]-indexBounds[0]; i++) {
                dataVect.get(s).add(null);
            }
            ReduceLoopBody[] threadBody=new ReduceLoopBody[ThreadHandler.instance().getNumThreads()];
            for(int i=0; i<ThreadHandler.instance().getNumThreads(); ++i) {
                threadBody[i]=new ReduceLoopBody() {
                    @Override
                    public void execute(int start, int end) {
                        for(int i=start; i<end; ++i) {
                            for(int j=0; j<paramArray.length; j++) {
                                paramArray[j]=(int)(pForms[j].valueOf(OldFunctionFilter.this,ss,i));
                            }
                            for(int j=0; j<valueArray.length; j++) {
                                valueArray[j]=(float)(vForms[j].valueOf(OldFunctionFilter.this,ss,i));
                            }
                            dataVect.get(ss).set(i - indexBounds[0],new DataElement(paramArray,valueArray));
                        }
                    }
                    final DataFormula[] pForms=buildParamFormulas();
                    final DataFormula[] vForms=buildValueFormulas();
                    final int[] paramArray=new int[paramEntry.size()];
                    final float[] valueArray=new float[valueEntry.size()];
                };
            }
            ThreadHandler.instance().chunkedForLoop(this,indexBounds[0], indexBounds[1], threadBody);
        }
    }

    private DataFormula[] buildParamFormulas() {
        DataFormula[] ret=new DataFormula[paramEntry.size()];
        for(int i=0; i<ret.length; ++i) {
            ret[i]=paramEntry.get(i).formula.getParallelCopy();
        }
        return ret;
    }

    private DataFormula[] buildValueFormulas() {
        DataFormula[] ret=new DataFormula[valueEntry.size()];
        for(int i=0; i<ret.length; ++i) {
            ret[i]=valueEntry.get(i).formula.getParallelCopy();
        }
        return ret;        
    }

    private void mirrorValues() {
        if(getNumSources()<1) return;
        DataSource source=getSource(0);
        valueEntry.clear();
        for(int i=0; i<source.getNumValues(0); i++) {
            FunctionEntry fe=new FunctionEntry(source.getValueDescription(0, i),"d[0].v["+i+"]");
            valueEntry.add(fe);
        }
        valueList.setListData(valueEntry.toArray());
    }

    private void valueListSelection() {
        if(valueList.getSelectedIndex()<0) return;
        valuePanel.removeAll();
        valuePanel.add(valueEntry.get(valueList.getSelectedIndex()).getSettingsPanel());
        valuePanel.validate();
        valuePanel.repaint();
    }

    private void newValue() {
        valueEntry.add(new FunctionEntry("New Value","1"));
        valueList.setListData(valueEntry.toArray());
    }

    private void removeValue() {
        if(valueList.getSelectedIndex()<0) return;
        valueEntry.remove(valueList.getSelectedIndex());
        valueList.setListData(valueEntry.toArray());
    }

    private void mirrorParams() {
        if(getNumSources()<1) return;
        DataSource source=getSource(0);
        paramEntry.clear();
        for(int i=0; i<source.getNumParameters(0); i++) {
            FunctionEntry fe=new FunctionEntry(source.getParameterDescription(0, i),"d[0].p["+i+"]");
            paramEntry.add(fe);
        }
        paramList.setListData(paramEntry.toArray());
    }

    private void paramListSelection() {
        if(paramList.getSelectedIndex()<0) return;
        paramPanel.removeAll();
        paramPanel.add(paramEntry.get(paramList.getSelectedIndex()).getSettingsPanel());
        paramPanel.validate();
        paramPanel.repaint();
    }

    private void newParam() {
        paramEntry.add(new FunctionEntry("New Parameter","1"));
        paramList.setListData(paramEntry.toArray());
    }

    private void removeParam() {
        if(paramList.getSelectedIndex()<0) return;
        paramEntry.remove(paramList.getSelectedIndex());
        paramList.setListData(paramEntry.toArray());
    }

    private ArrayList<FunctionEntry> valueEntry;

    private ArrayList<FunctionEntry> paramEntry;

    private transient JList valueList;

    private transient JList paramList;

    private transient JPanel valuePanel;

    private transient JPanel paramPanel;

    private static final long serialVersionUID=2567098734l;

    private class FunctionEntry implements Serializable {
        public FunctionEntry(String desc,String form) {
            formula=new DataFormula(form);
            description=new EditableString(desc);
        }

        public FunctionEntry(FunctionEntry c) {
            formula=new DataFormula(c.formula);
            description=c.description;
        }

        @Override
        public String toString() { return description.getValue(); }

        public JPanel getSettingsPanel() {
            if (settingsPanel == null) {
                settingsPanel = new JPanel(new BorderLayout());
                JPanel outerPanel = new JPanel(new BorderLayout());
                JPanel tmpPanel = new JPanel(new GridLayout(2, 1));
                tmpPanel.add(new JLabel("Description"));
                tmpPanel.add(new JLabel("Expression"));
                outerPanel.add(tmpPanel, BorderLayout.WEST);
                tmpPanel = new JPanel(new GridLayout(2, 1));
                tmpPanel.add(description.getTextField(new EditableString.Listener() {
                    @Override
                    public void valueChanged() {
                        valueList.setListData(valueEntry.toArray());
                        paramList.setListData(paramEntry.toArray());                        
                    }
                }));
                tmpPanel.add(formula.getTextField(null));
                outerPanel.add(tmpPanel, BorderLayout.CENTER);
                settingsPanel.add(outerPanel, BorderLayout.NORTH);
            }
            return settingsPanel;
        }

        public DataFormula formula;
        public EditableString description;
        private transient JPanel settingsPanel;
        private static final long serialVersionUID=3250987326l;
    }
}



*/