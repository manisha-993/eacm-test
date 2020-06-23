//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.table.RSTTable;

import COM.ibm.eannounce.objects.RowSelectableTable;

import java.awt.Dimension;
import java.beans.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * these are the arrows with number of entities under edit inbetween
 * @author Wendy Stimpson
 */
//$Log: RecordToggle.java,v $
//Revision 1.2  2014/01/24 18:46:36  wendy
//show x of y in record toggle label
//
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class RecordToggle extends JToolBar implements PropertyChangeListener, 
EACMGlobals {
	private static final long serialVersionUID = 1L;
    private static final Border NORMALBORDER = new EmptyBorder(2, 2, 2, 2);
	private JButton btnLeft = null;
	private JButton btnRight = null;
	private JLabel lblCount = new JLabel("1");
	private int currentIndex = 0;
	private RowSelectableTable table = null;
	private Action prevAction = null;
	private Action nextAction = null;
    private String currentKey = null;
    private RSTTable gridTable = null;

	/**
     * recordToggle
     */
    protected RecordToggle(RowSelectableTable t,Action p, Action n) {
		super("RecordToggle");
		prevAction = p;
		nextAction = n;
		
		table = t; // this is the RST from the entitygroup under edit
		init();
	}
    
	/**
	 * initialize components
	 */
	private void init () {
		btnLeft = new JButton(prevAction);
		btnRight = new JButton(nextAction);
		btnLeft.setHideActionText(true);
		btnRight.setHideActionText(true);
		
		lblCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCount.setVerticalAlignment(SwingConstants.BOTTOM);
		
		setFloatable(false);
		
		add(btnLeft);
		add(lblCount);
		add(btnRight);

		btnLeft.setBorder(UIManager.getBorder(NORMALBORDER));
		btnRight.setBorder(UIManager.getBorder(NORMALBORDER));
	  	
		Dimension forcedDim = UIManager.getDimension(TOOLBAR_BUTTON_DIM);
		btnLeft.setRequestFocusEnabled(false);
		btnLeft.setPreferredSize(forcedDim);
		btnLeft.setMaximumSize(forcedDim);	
		btnRight.setRequestFocusEnabled(false);
		btnRight.setPreferredSize(forcedDim);
		btnRight.setMaximumSize(forcedDim);	

		forcedDim = null;
		
		setBorder(UIManager.getBorder(RAISED_BORDER_KEY));
	}
	
	/**
	 * set the table used by grid editor, filtering is handled in the view, not the RST now
	 * @param gt
	 */
	protected void setGridTable(JTable gt){
		gridTable = (RSTTable)gt;
	}
	
	/**
	 * called when rows are removed
	 * @return
	 */
	protected boolean hasNext(){
		return currentIndex < getViewRowCount()-1;
	}
	/**
	 * called when rows are removed
	 * @return
	 */
	protected boolean hasPrev(){
		return currentIndex > 0;
	}
    /**
     * called when entity under edit is changed either by pressing an arrow button, menu item or
     * saveall moves to an error in a different entity
     * @param key
     */
    protected void setCurrentKey(String key){
    	if(key==null){
    		currentKey = key;
    		currentIndex = -1;
    		prevAction.setEnabled(false);
    		nextAction.setEnabled(false);
    		lblCount.setToolTipText(null);
    		return;
    	}
    	currentKey = key;
    	setViewRowIndex();

   		prevAction.setEnabled(currentIndex > 0);
    	nextAction.setEnabled(currentIndex < getViewRowCount()-1);
		
		lblCount.setText(Integer.toString(currentIndex+1));
		
		setCountOf();
    }
    
    /**
     * set the count x of y in the label tooltip
     */
    protected void setCountOf(){
    	if(currentIndex!= -1){
    		lblCount.setToolTipText(lblCount.getText()+" of "+this.getViewRowCount());
    	}
    }
    /**
     * hang onto index of the current item
     */
    private void setViewRowIndex(){
    	if(gridTable!=null){
    		int modelRowIndex = table.getRowIndex(currentKey);
    		currentIndex = gridTable.convertRowIndexToView(modelRowIndex);
    	}else{
    		currentIndex = table.getRowIndex(currentKey);
    	}
    }
    /**
     * called by actions to determine size
     * @return
     */
    public int getViewRowCount(){
    	if(gridTable!=null){
    		return gridTable.getRowCount();
    	}else{
    		return table.getRowCount();
    	}
    }

    /**
     * get the key for this index from the table controlling the recordtoggle
     * @param index
     * @return
     */
    private String getRowKey(int index){
    	if(gridTable!=null){
    		return gridTable.getEANKey(index);
    	}else{
    		return table.getRowKey(index);
    	}
    }
    /**
     * get the key for the next index
     * @return
     */
    protected String getNextRowKey(){
    	return getRowKey(getCurrentIndex()+1);
    }
    /**
     * get the key for the prev index
     * @return
     */
    protected String getPrevRowKey(){
    	return getRowKey(getCurrentIndex()-1);
    }
	/**
	 * change orientation of this when parent toolbar changes
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName().equals("orientation")) {
			setOrientation(((Integer)pce.getNewValue()).intValue());
		}
	}

	/**
	 * called from prevedit and nextedit actions for enablement and called from editctrl to move to next/prev item
     * getCurrentIndex
     * @return
     */
    protected int getCurrentIndex() {
		return currentIndex;
	}

	/**
     * release memory - called when editctrller is derefed
     */
    protected void dereference() {
		table = null;
		gridTable = null;
		
		btnLeft.setAction(null);
		btnLeft.removeAll();
		btnLeft.setUI(null);
		btnLeft = null;
		
		btnRight.setAction(null);
		btnRight.removeAll();
		btnRight.setUI(null);
		btnRight = null;
		
		prevAction = null;
		nextAction = null;
		
		removeAll();
		setUI(null);
		

		lblCount.removeAll();
		lblCount.setUI(null);
		lblCount = null;

		prevAction = null;
		nextAction = null;
	    currentKey = null;
	}
}
