//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mtrx;


import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import COM.ibm.eannounce.objects.EANAttribute;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.CrossTable;


/*********************************************************************
 * If RELATTR is defined for a CrossTable, then the relator attribute populates the cell
 * else this is used to adjust number of relators in a row in CrossTable 
 * update selected rows and all columns with the specified value
 * @author Wendy Stimpson
 */
//$Log: AdjustRowAction.java,v $
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class AdjustRowAction extends EACMAction implements FocusListener
{
	private static final long serialVersionUID = 1L;
	
	private CrossTable crssTable = null;
	private MatrixActionBase actionTab = null;
	private boolean hasFocus = true;

	public AdjustRowAction(CrossTable _tab,MatrixActionBase mab) { 
		super(MTRX_ADJUSTROW);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("nrow.gif"));
		setTable(_tab);
		actionTab = mab;
	}
	public void setTable(CrossTable nt){
		crssTable = nt;
	    setEnabled(true);
	}
	public void setEnabled(boolean newValue) {
		if (actionTab!=null && actionTab.isWaiting()){
			newValue=false; // dont enable if some action is running
		}
		super.setEnabled(hasFocus && newValue && crssTable!=null
				&& crssTable.hasSelectedDataColumns() && !Utils.isPast(crssTable.getProfile()));
	}
	
	public void dereference(){
		super.dereference();
		crssTable = null;
		actionTab = null;
	}
	public void actionPerformed(ActionEvent e) {
		crssTable.cancelCurrentEdit();
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		 		if (crssTable.showRelAttr()) { // crsstable is really a RelAttrCrossTable
					int row = crssTable.getSelectedRow();
					int col = crssTable.getSelectedColumn();
					Object cell = crssTable.getValueAt(row, col);
					//chooseVal= Please Choose a Value:
					String usermsg = Utils.getResource("chooseVal");
					if (cell instanceof EANAttribute){
						EANAttribute ean = (EANAttribute)cell;
						//setAttrVal = Please specify {0}:
						String desc = ean.getLongDescription();
						if (desc.startsWith("*")){
							desc = desc.substring(1);
						}
						usermsg = Utils.getResource("setAttrVal",desc);
					}
					Object obj = com.ibm.eacm.ui.UI.showTableCellEditor(crssTable,
							usermsg, crssTable.getFillComponent(row, col));
					crssTable.repaint(); // get rid of dialog box
					
		 			if (obj != JOptionPane.UNINITIALIZED_VALUE) {
		 				// do it like this because getting the locks can take a while
		 				actionTab.disableActionsAndWait();
		 				worker = new UpdateWorker(obj);
		 				RMIMgr.getRmiMgr().execute(worker);
		 			}
		  		} else {
		  			//msg24003 = Please enter the number to fill. limit it from 1-99
		  			// locks are not obtained for this, so there is no delay
		  			int numRelators = com.ibm.eacm.ui.UI.showIntSpinner(null,Utils.getResource("msg24003"),1, 1, 99, 1);
		  			if (numRelators != CLOSED) { 
		  				crssTable.verticalAdjust(Integer.toString(numRelators));
		  			}
		  		}
			}
		});
	}

	/**
	 * this class is used to allow the dispatch thread to repaint and then run this, wait cursor is displayed this way
	 *
	 */
	private class UpdateWorker extends DBSwingWorker<Object, Void> { 
		private Object obj = null;
		UpdateWorker(Object o){
			obj =o;
		}
		@Override
		public Object doInBackground() {
			RMIMgr.getRmiMgr().complete(this);
			worker = null;
			return null;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
	 				crssTable.verticalAdjust(obj); // obj=null is a reset
				}
			} finally{    
				actionTab.enableActionsAndRestore();	
				obj = null;
			}
		}
		public void notExecuted(){
			Logger.getLogger(MTRX_PKG_NAME).log(Level.WARNING,"not executed");
			actionTab.enableActionsAndRestore();
			worker = null; 
		}
	}
	
	/* (non-Javadoc)
	 * this is needed when crosstable is used with a matrixtable, disable when matrixtable has focus
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
		hasFocus = e.getSource() instanceof CrossTable;
		setEnabled(hasFocus);
	}
	public void focusLost(FocusEvent e) {}	
}