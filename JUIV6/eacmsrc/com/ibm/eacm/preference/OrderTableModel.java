//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;

import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.EACMGlobals;

import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

import java.awt.Component;
import java.rmi.RemoteException;

import javax.swing.event.*;
import javax.swing.table.*;

/**
 * this implements the table model needed by OrderTable.  It is a wrapper for the meta table.
 * @author Wendy Stimpson
 */
// $Log: OrderTableModel.java,v $
// Revision 1.3  2014/01/24 12:47:13  wendy
// getro() throw remoteexception to allow reconnect
//
// Revision 1.2  2013/02/05 18:23:22  wendy
// throw/handle exception if ro is null
//
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class OrderTableModel extends DefaultTableModel implements EACMGlobals {
	private static final long serialVersionUID = 1L;
	private MetaColumnOrderTable ctm = null;
    private boolean chgsMade = false;

	/**
     * dereference
     */
	protected void dereference() {
	 	if (ctm!=null){
    		ctm.dereference();
    		ctm = null;
		}
	}

	/**
     * updateModel
     * @param ctm2
     */
    protected void updateModel(MetaColumnOrderTable ctm2) {
    	if (ctm!=null){
    		ctm.dereference();
    	}
		ctm = ctm2;
	    // All row data in the table has changed, listeners should discard any state 
        //TableModelEvent(this, 0, Intege.MAX_VALUE, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)
        fireTableChanged(new TableModelEvent(this));
	}

	/**
     * setUpdateDefaults
     * @param b
     */
    protected void setUpdateDefaults(boolean b) {
        try {
        	ctm.setUpdateDefaults(b);
        } catch (MiddlewareRequestException mre) { // only thrown if profile cant do this
        	com.ibm.eacm.ui.UI.showException(null,mre, "mw.err-title");
        } 
    }
	/**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
		if (ctm != null) {
			return ctm.getRowCount();
		}
		return 0;
	}

	/**
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
		if (ctm != null) {
			return ctm.getColumnCount();
		}
		return 0;
	}

	/**
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int i) {
		if (ctm != null) {
			return ctm.getColumnHeader(i);
		}
		return null;
	}

	/**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class<?> getColumnClass(int i) {
		if (ctm != null) {
			return ctm.getColumnClass(i);
		}
		return String.class;
	}

	/**
     * moveRow
     * @param old
     * @param new
     */
    protected void moveRow(int old, int bnew) {
		if (ctm != null) {
			ctm.moveRow(old,bnew);
			chgsMade = true;
		}
	}

    protected boolean hasChanges(){ 
    	if (ctm != null) {
    		//this doesnt work.. meta has diff numbers and ui renumbers them (still same order though)
			//chgs = ctm.hasChanges();
		}
    	return chgsMade;
    }

	/**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int r,int c) {
		return ctm.isCellEditable(r,c);
	}

	/**
     * commit - called on worker thread
     * @param c
     */
    protected void commit(Component c) {
    	try {
    		ctm.commit(null, ro());
    		chgsMade = false;//reset chgs flag
    	} catch(Exception exc){
    		if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
    			if (RMIMgr.getRmiMgr().reconnectMain()) {
    				try{
    					ctm.commit(null, ro());
    					chgsMade = false;//reset chgs flag
    				}catch(Exception e){
    					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
    						if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, e) == RETRY) {
    							commit(c);
    						}
    					}else{ 
    						com.ibm.eacm.ui.UI.showException(c,e, "mw.err-title");
    					}
    				}
    			}else{	// reconnect failed
    				com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
    			}
    		}else{ // show user msg and ask what to do
    			if(RMIMgr.shouldPromptUser(exc)){
    				if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, exc) == RETRY) {
    					commit(c);
    				}// else user decide to ignore or exit	
    			}else{
    				com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
    			}
    		}
    	}
    }
	/**
     * rollbackDefault - called on worker thread
     */
    protected boolean rollbackDefault(Component c) {
    	boolean isok = false;
        try {
        	ctm.resetToDefaults(null, ro());
        	isok = true;
        } catch(Exception exc){
    		if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
    			if (RMIMgr.getRmiMgr().reconnectMain()) {
    				try{
    					ctm.resetToDefaults(null, ro());
    					isok = true;
    				}catch(Exception e){
    					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
    						if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, e) == RETRY) {
    							isok = rollbackDefault(c);
    						}
    					}else{ 
    						com.ibm.eacm.ui.UI.showException(c,e, "mw.err-title");
    					}
    				}
    			}else{	// reconnect failed
    				com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
    			}
    		}else{ // show user msg and ask what to do
    			if(RMIMgr.shouldPromptUser(exc)){
    				if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, exc) == RETRY) {
    					isok=rollbackDefault(c);
    				}// else user decide to ignore or exit	
    			}else{
    				com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
    			}
    		}
    	}
        return isok;
    }
    
    private RemoteDatabaseInterface ro() throws RemoteException  {
    	return RMIMgr.getRmiMgr().getRemoteDatabaseInterface();
    }
    /**
     * called on dispatch thread
     */
    protected void modelChangedUpdate()    {
	    // All row data in the table has changed, listeners should discard any state 
        //TableModelEvent(this, 0, Integer.MAX_VALUE, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)
        fireTableChanged(new TableModelEvent(this));
        
       	chgsMade = false; //reset chgs flag
    }
	/**
     * rollback - called on worker thread
     */
    protected boolean rollback(Component c) {
    	boolean isok = false;
        try {
            ctm.rollback(null, ro());
            isok = true;
        } catch(Exception exc){
    		if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
    			if (RMIMgr.getRmiMgr().reconnectMain()) {
    				try{
    					ctm.rollback(null, ro());
    					isok = true;
    				}catch(Exception e){
    					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
    						if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, e) == RETRY) {
    							isok=rollback(c);
    						}
    					}else{ 
    						com.ibm.eacm.ui.UI.showException(c,e, "mw.err-title");
    					}
    				}
    			}else{	// reconnect failed, show orig error
    				com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
    			}
    		}else{ // show user msg and ask what to do
    			if(RMIMgr.shouldPromptUser(exc)){
    				if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, exc) == RETRY) {
    					isok=rollback(c);
    				}// else user decide to ignore or exit	
    			}else{
    				com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
    			}
    		}
    	}
        return isok;
    }

	/**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int r, int c) {
		if (ctm != null) {
			return ctm.getValueAt(r,c);
		}
		return null;
	}

	/**
     * get
     * @param r
     * @param c
     * @return
     */
    protected Object get(int r, int c) {
		if (ctm != null) {
			return ctm.get(r,c);
		}
		return null;
	}

	/**
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(Object o, int r, int c) {
		try {
			ctm.put(r,c,o);
			chgsMade=true;
		} catch (EANBusinessRuleException bre) {
			bre.printStackTrace();
			bre.dereference();
		}
	}
}
