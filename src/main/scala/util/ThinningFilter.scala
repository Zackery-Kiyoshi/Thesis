package util

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class ThinningFilter(var thinFactor: Int = 10) extends Filter() {
  val t: String = "ThinningFilter"

  var useGroups: Boolean = false
  private var groupFormula = new DoubleFormula("v[0]");
  private var inputTmp: Vector[DataStore] = null

  override def apply(input: Vector[DataStore]): Vector[DataStore] = {
    var ret = Vector[DataStore]()
    inputTmp = input
    for (i <- input) {
      var tmpDE: Vector[DataElement] = Vector.empty[DataElement]
      for (j <- 0 until i.length by thinFactor) {
        //var tmp:DataElement = i(j)
        tmpDE = tmpDE :+ i(j)
      }
      var t = new DataStore()
      t.set(tmpDE)
      ret = ret :+ t
    }

    for (s <- 0 until input.length) {
      val ss: Int = 0
      if (useGroups) {
        // grouped by a function return using the groups with the same numbers rather than element count
        /*
    			ThreadHandler.instance().loadWaitTask(this,new Runnable() {
    				@Override
                    public void run() {
    					int groupCount=0;
    					int[] range=groupFormula.getSafeElementRange(ThinningFilter.this,ss);
    		            DataFormula.checkRangeSafety(range,ThinningFilter.this);
    					int i=range[0];
    					while(i<range[1]) {
    						int groupEnd=doGroupSelection(i,range[1],groupFormula,ss);
    						if(groupCount%thinFactor.getValue()==0) {
    							while(i<groupEnd) {
    								dataVect.get(ss).add(input.getElement(i,ss));
    								++i;
    							}
    						}
    						i=groupEnd;
    						++groupCount;
    					}
    				}
    			});
    		} else {
    			//  parallel
    			var vects:ArrayBuffer[ArrayBuffer[DataElement]]=new ArrayBuffer()
          for (i <-0 until ThreadHandler.instance().getNumThreads()) {
           	vects = vects :+ new ArrayBuffer[DataElement]()
          }
    //    create ReduceLoopBody array
          for (i <- 0 until vects.length) {
          	val index=i
            //      public void execute(int start, int end) {
          			var data:ArrayBuffer[DataElement]=vects(index)
           			for (j <- 0 until input(ss).length) {
           				if(j%thinFactor==0) data += (input(j)(ss));
          			}
          	//      }
          }
          
          
          ThreadHandler.instance().chunkedForLoop(this,0,input.getNumElements(ss),loops);
          
          
          // merge lists
          var size = 0
          for (i <- 0 until vects.size) {
           	size+=vects(i).size
          }
          //dataVect(s).ensureCapacity(size);
          for (i <- 0 until vects.size) {
          	//dataVect(s).addAll(vects(i))
          	ret(s).addAll(vects(i))
          }
//          */
      }

    }

    return ret
  }

  private def doGroupSelection(startIndex: Int, maxIndex: Int, sortFormula: DoubleFormula, stream: Int): Int = {
    //		/*
    //var v:Double = sortFormula(this,stream,startIndex)
    var v: Double = sortFormula(startIndex, inputTmp, null)
    var i: Int = 0
    breakable {
      for (i <- startIndex + 1 until maxIndex) {
        if (!(sortFormula(i, inputTmp, null) == v)) break
      }
    }
    return i;
    //	*/
    return 0
  }

}

/*

protected void redoAllElements() {
		if(input==null) return;
		sizeDataVectToInputStreams();
		for(int s=0; s<getSource(0).getNumStreams(); ++s) {
		    final int ss=0;
    		if(useGroups.getValue()) {
    			ThreadHandler.instance().loadWaitTask(this,new Runnable() {
    				@Override
                    public void run() {
    					int groupCount=0;
    					int[] range=groupFormula.getSafeElementRange(OldThinningFilter.this,ss);
    		            DataFormula.checkRangeSafety(range,OldThinningFilter.this);
    					int i=range[0];
    					while(i<range[1]) {
    						int groupEnd=doGroupSelection(i,range[1],groupFormula,ss);
    						if(groupCount%thinFactor.getValue()==0) {
    							while(i<groupEnd) {
    								dataVect.get(ss).add(input.getElement(i,ss));
    								++i;
    							}
    						}
    						i=groupEnd;
    						++groupCount;
    					}
    				}
    			});
    		} else {
    			//  parallel
    			final ArrayList<ArrayList<DataElement>> vects=new ArrayList<ArrayList<DataElement>>();
                for (int i=0; i<ThreadHandler.instance().getNumThreads(); i++) {
                	vects.add(new ArrayList<DataElement>());
                }
    //          create ReduceLoopBody array
                ReduceLoopBody[] loops=new ReduceLoopBody[vects.size()];
                for (int i=0; i<loops.length; i++) {
                	final int index=i;
                	loops[i]=new ReduceLoopBody() {
                		@Override
                        public void execute(int start, int end) {
                			ArrayList<DataElement> data=vects.get(index);
                			for (int j=start; j<end; j++) {
                				if(j%thinFactor.getValue()==0) data.add(input.getElement(j,ss));
                			}
                		}
                	};
                }
                ThreadHandler.instance().chunkedForLoop(this,0,input.getNumElements(ss),loops);
                // merge lists
                int size=0;
                for (int i=0; i<vects.size(); i++) {
                	size+=vects.get(i).size();
                }
                dataVect.get(s).ensureCapacity(size);
                for (int i=0; i<vects.size(); i++) {
                	dataVect.get(s).addAll(vects.get(i));
                }
    		}
		}
	}
	/**
	 * Returns the elements after the group that begins at startIndex.
	 * @param startIndex The index to start the group at.
	 * @return The index of the first element not in the group.
	 */
	private int doGroupSelection(int startIndex,int maxIndex,DataFormula sortFormula,int stream) {
		double val=sortFormula.valueOf(this,stream,startIndex);
		int i;
		for(i=startIndex+1; i<maxIndex && sortFormula.valueOf(this,stream,i)==val; ++i);
		return i;
	}

*/

/*

/*
 * Created on Sep 1, 2003
 *
 */
package util;

import java.awt.*;
import javax.swing.*;

import edu.swri.swiftvis.DataElement;
import edu.swri.swiftvis.DataFormula;
import edu.swri.swiftvis.GraphElement;
import edu.swri.swiftvis.util.EditableBoolean;
import edu.swri.swiftvis.util.EditableInt;
import edu.swri.swiftvis.util.ReduceLoopBody;
import edu.swri.swiftvis.util.ThreadHandler;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mark Lewis
 *
 * This is a simple thinning filter that is provided in case you want to cut out a large
 * number of elements and don't want to do it in any type of manner provided by other
 * filters.  You provide an integer, n, and only one out of every n inputs will be selected.
 * Instead of individual elements you can also do the thinning by whole groups.  This can
 * be more useful for SWIFT so that you can keep all the particles in a timestep, but only
 * take every 10th timestep or something along those lines.  A group will always be defined
 * as adjacent elements having the same value for the top sort formula of the input
 * data source. 
 */
public class OldThinningFilter extends AbstractSingleSourceFilter {
	public OldThinningFilter() {
		input=null;
	}
    
    private OldThinningFilter(OldThinningFilter c,List<GraphElement> l) {
        super(c,l);
        thinFactor=new EditableInt(c.thinFactor.getValue());
        useGroups=new EditableBoolean(c.useGroups.getValue());
        groupFormula=new DataFormula(groupFormula);
    }

	@Override
    public String getDescription(){ return "Thinning Filter"; }

	public static String getTypeDescription(){ return "Thinning Filter"; }

	@Override
    protected void setupSpecificPanelProperties(){
		JPanel formPanel=new JPanel(new BorderLayout());
		JPanel northPanel=new JPanel(new GridLayout(3,1));
		JPanel innerPanel=new JPanel(new BorderLayout());
		innerPanel.add(new JLabel("Keep 1 in "),BorderLayout.WEST);
		innerPanel.add(thinFactor.getTextField(null),BorderLayout.CENTER);
		northPanel.add(innerPanel);
		northPanel.add(useGroups.getCheckBox("Use Groups?",null));
		innerPanel=new JPanel(new BorderLayout());
		innerPanel.add(new JLabel("Group Formula"),BorderLayout.WEST);
		innerPanel.add(groupFormula.getTextField(null));
		northPanel.add(innerPanel);
		formPanel.add(northPanel,BorderLayout.NORTH);

		JButton button=new JButton("Propagate Changes");
		button.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) { abstractRedoAllElements(); }
		} );
		formPanel.add(button,BorderLayout.SOUTH);
		propPanel.add("Thinning",formPanel);
	}

	/**
	 * Tells you what a particular parameter is used for.
	 */
	@Override
    public String getParameterDescription(int stream, int which){
		if(input==null) return "None";
		return input.getParameterDescription(stream,which);
	}

	/**
	 * Tells you what a particular value is used for.
	 */
	@Override
    public String getValueDescription(int stream, int which){
		if(input==null) return "None";
		return input.getValueDescription(stream,which);
	}

	@Override
    public int getNumParameters(int stream){
		if(input==null) return 0;
		return input.getNumParameters(stream);
	}

	@Override
    public int getNumValues(int stream){
		if(input==null) return 0;
		return input.getNumValues(stream);
	}

    @Override
    public OldThinningFilter copy(List<GraphElement> l) {
        return new OldThinningFilter(this,l);
    }

    @Override
    protected boolean doingInThreads() {
		return true;
	}
    
	@Override
    protected void redoAllElements() {
		if(input==null) return;
		sizeDataVectToInputStreams();
		for(int s=0; s<getSource(0).getNumStreams(); ++s) {
		    final int ss=0;
    		if(useGroups.getValue()) {
    			ThreadHandler.instance().loadWaitTask(this,new Runnable() {
    				@Override
                    public void run() {
    					int groupCount=0;
    					int[] range=groupFormula.getSafeElementRange(OldThinningFilter.this,ss);
    		            DataFormula.checkRangeSafety(range,OldThinningFilter.this);
    					int i=range[0];
    					while(i<range[1]) {
    						int groupEnd=doGroupSelection(i,range[1],groupFormula,ss);
    						if(groupCount%thinFactor.getValue()==0) {
    							while(i<groupEnd) {
    								dataVect.get(ss).add(input.getElement(i,ss));
    								++i;
    							}
    						}
    						i=groupEnd;
    						++groupCount;
    					}
    				}
    			});
    		} else {
    			//  parallel
    			final ArrayList<ArrayList<DataElement>> vects=new ArrayList<ArrayList<DataElement>>();
                for (int i=0; i<ThreadHandler.instance().getNumThreads(); i++) {
                	vects.add(new ArrayList<DataElement>());
                }
    //          create ReduceLoopBody array
                ReduceLoopBody[] loops=new ReduceLoopBody[vects.size()];
                for (int i=0; i<loops.length; i++) {
                	final int index=i;
                	loops[i]=new ReduceLoopBody() {
                		@Override
                        public void execute(int start, int end) {
                			ArrayList<DataElement> data=vects.get(index);
                			for (int j=start; j<end; j++) {
                				if(j%thinFactor.getValue()==0) data.add(input.getElement(j,ss));
                			}
                		}
                	};
                }
                ThreadHandler.instance().chunkedForLoop(this,0,input.getNumElements(ss),loops);
                // merge lists
                int size=0;
                for (int i=0; i<vects.size(); i++) {
                	size+=vects.get(i).size();
                }
                dataVect.get(s).ensureCapacity(size);
                for (int i=0; i<vects.size(); i++) {
                	dataVect.get(s).addAll(vects.get(i));
                }
    		}
		}
	}
    
	/**
	 * Returns the elements after the group that begins at startIndex.
	 * @param startIndex The index to start the group at.
	 * @return The index of the first element not in the group.
	 */
	private int doGroupSelection(int startIndex,int maxIndex,DataFormula sortFormula,int stream) {
		double val=sortFormula.valueOf(this,stream,startIndex);
		int i;
		for(i=startIndex+1; i<maxIndex && sortFormula.valueOf(this,stream,i)==val; ++i);
		return i;
	}

	private EditableInt thinFactor=new EditableInt(10);
	private EditableBoolean useGroups=new EditableBoolean(false);

    private DataFormula groupFormula = new DataFormula("v[0]");

    private static final long serialVersionUID=346987198146l;
}


*/