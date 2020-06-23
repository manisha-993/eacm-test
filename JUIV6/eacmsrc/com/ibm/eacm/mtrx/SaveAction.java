//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mtrx;


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;

import COM.ibm.eannounce.objects.EANBusinessRuleException;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.HeavyWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.*;



/*********************************************************************
 * Save any updates made in crosstable
 * @author Wendy Stimpson
 */
//$Log: SaveAction.java,v $
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class SaveAction extends EACMAction implements PropertyChangeListener
{
	private RSTTable table = null;
	private MatrixActionBase actionTab = null;
	private static final long serialVersionUID = 1L;
	public SaveAction(MatrixActionBase mab) { 
		super(MTRX_SAVE,KeyEvent.VK_S, Event.CTRL_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("save.gif"));
		actionTab = mab;
		setTable(null);
	}
	
	public void setTable(RSTTable nt){
		table = nt;
	    setEnabled(true);
	}
	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue && table!=null && table.hasChanges());
	}
	public void dereference(){
		super.dereference();
		table = null;
		actionTab = null;
	    /*dont interrupt a save if(saveWorker != null){
	    	saveWorker.cancel(true);
	    }*/
	}

	public void actionPerformed(ActionEvent e) {
		table.stopCellEditing();
		actionTab.disableActionsAndWait();
		worker = new SaveWorker();
		RMIMgr.getRmiMgr().execute(worker);
	}
	
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(DATACHANGE_PROPERTY)) {
			setEnabled(table.hasChanges());
		}
	}

    private class SaveWorker extends HeavyWorker<Exception, Void> { 
    	@Override
    	public Exception doInBackground() {
    		try{
    			table.commit();
    		}catch(Exception x){
    			return x;
    		}finally{
            	RMIMgr.getRmiMgr().complete(this);
            	worker = null; 
    		}
			return null;
    	}

        @Override
        public void done() {
            //this will be on the event dispatch thread
        	try {
        		if(!isCancelled()){
        			Exception e = get();
        			if (e != null){      	
        				if (e instanceof EANBusinessRuleException){
        					table.moveToError((EANBusinessRuleException) e);
        				}
        		    	
        				com.ibm.eacm.ui.UI.showException(table,e);
        			}else{
        				table.refresh();
        				if(table instanceof MtrxTable){
        					((MtrxTable)table).resizeCellsAndHeader();
        				}
        			}
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"saving to PDH");
            }finally{
            	actionTab.enableActionsAndRestore();
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(MTRX_PKG_NAME).log(Level.WARNING,"not executed");
     		actionTab.enableActionsAndRestore();
     		worker = null; 
    	}
    }
}