//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.event.KeyEvent;

import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.BaseTable;

import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;


/******************************************************************************
* This is used to display EACM Frames 
* @author Wendy Stimpson
*/
// $Log: EACMFrame.java,v $
// Revision 1.4  2014/01/24 12:47:13  wendy
// getro() throw remoteexception to allow reconnect
//
// Revision 1.3  2013/07/18 18:39:29  wendy
// fix compiler warnings
//
// Revision 1.2  2013/02/05 18:23:21  wendy
// throw/handle exception if ro is null
//
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//

public abstract class EACMFrame extends JFrame implements EACMGlobals
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.4 $";
    protected CloseFrameAction closeAction = null;
    private Hashtable<String, EACMAction> actionTbl = new Hashtable<String, EACMAction>();
    protected JMenuBar menubar = null;
    protected JToolBar tBar = null;
    protected JPopupMenu popup = null;
    
    protected Hashtable<String, EACMAction> getActionTbl() { return actionTbl;}
	
    /**
     * @param title
     * @param args
     */
    protected EACMFrame(String title, Object ... args)  {
        super(Utils.getResource(title,args));
    	setIconImage(Utils.getImage(DEFAULT_ICON));
        EACM.getEACM().addUserWindow(this);
    }
  
    /**
     * hide or show buttons
     * @param _key
     * @param _b
     */
    public void setVisible(String _key, boolean _b){
		for (int i=0; i<tBar.getComponentCount(); i++){
			Component comp = tBar.getComponent(i);
			if (comp instanceof JButton){
				EACMAction act = (EACMAction)((JButton)comp).getAction();
				if (act==null){
					continue;
				}
				if(act.getActionKey().equals(_key)){
					comp.setVisible(_b);
					break;
				}
			}
		}
	}
    /**
     * get the unique id for this frame
     * @return
     */
    public String getUID(){return "";}
    
	protected EACMAction getAction(String key){ 
		return actionTbl.get(key);
	}
	protected void addAction(EACMAction act){
		actionTbl.put(act.getActionKey(), act);
	}
	private boolean waiting = false;
	public boolean isWaiting() { return waiting;}
	/**
	 * called by actions when using workers
     */
	public void disableActionsAndWait(){
		waiting = true;
    	disableActions();
    	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      	fixTableHeaderCursorBug(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    /* (non-Javadoc)
     * used by base class for enabling column listener
     * @see com.ibm.eacm.tabs.TabPanel#getJTable()
     */
    protected BaseTable getJTable() { return null;}

    protected void fixTableHeaderCursorBug(Cursor cursor){
    	//tableheaders do not consistently display correct cursor
    	JTable table = getJTable();
    	if (table != null){
    		JTableHeader header = table.getTableHeader();
    		if (header != null) {
    			header.setCursor(cursor);
    		}
    	}
	}
    /**
     * enable label 
     * @param _filter
     */
    public void setFilter(boolean _filter) {}
    
	public void enableActionsAndRestore(){
		waiting = false;
		if(actionTbl!=null){ // can happen if frame is closed but worker was still running
			enableActions();
			refreshActions(); // enable based on current selection
		}
		setCursor(Cursor.getDefaultCursor());
		fixTableHeaderCursorBug(Cursor.getDefaultCursor());
	}
	protected void disableActions(){
       	for (Enumeration<EACMAction> e = actionTbl.elements(); e.hasMoreElements();){
    		EACMAction action = (EACMAction)e.nextElement(); 
    		if(action.getActionKey().equals(CLOSETAB_ACTION)){
    			continue;
    		}
    		action.setEnabled(false);
    	}
    }
    protected void refreshActions() {}
    protected void enableActions(){
    	for (Enumeration<EACMAction> e = actionTbl.elements(); e.hasMoreElements();){
    		EACMAction action = (EACMAction)e.nextElement(); 
    		//enable all until i figure out which ones
    		action.setEnabled(true);
    	}
    }
    
    protected void addGlobalActionMenuItem(JMenu menu, String actionName){
    	JMenuItem mi = menu.add(EACM.getEACM().getGlobalAction(actionName));
    	mi.setVerifyInputWhenFocusTarget(false);
    }

    protected void addLocalActionMenuItem(JMenu menu, String actionName){
    	JMenuItem mi = menu.add(actionTbl.get(actionName));
    	mi.setVerifyInputWhenFocusTarget(false);
    }
   
    protected RemoteDatabaseInterface ro() throws RemoteException  {
    	return RMIMgr.getRmiMgr().getRemoteDatabaseInterface();
    }
    
    protected void finishSetup(Frame owner){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	 
        if(closeAction==null){
        	closeAction = new CloseFrameAction(this);
        }
    	addWindowListener((WindowListener)closeAction);
    	
    	if(tBar !=null){
    		tBar.setRollover(true);
    	}
    	
        // allow escape to close dialog
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), closeAction.getActionKey());
        getRootPane().getActionMap().put(closeAction.getActionKey(), closeAction);

        pack();
        if (owner!=null){
        	setLocationRelativeTo(owner);
        }
        setSize(getPreferredSize());
        setResizable(false);
    }
    
    /**
     * make all toolbar buttons the same size
     */
    protected void adjustToolBarButtons(){
      	Dimension forcedDim = UIManager.getDimension(TOOLBAR_BUTTON_DIM);
    	for (int i=0; i<tBar.getComponentCount(); i++)	{
    		Component comp = tBar.getComponent(i);
    		// everytime a button is pressed, focus will be returned to the dialog
    		if (comp instanceof JButton) {
    			((JButton)comp).setRequestFocusEnabled(false);
    			// preferred sizes and max sizes are different if this control is
    			// part of a dialog.. force them to be the same
    			((JButton)comp).setPreferredSize(forcedDim);
    			((JButton)comp).setMaximumSize(forcedDim);
    		}
    	}

    	forcedDim = null;
    }
    
	/**
     * createPopupMenu
     */
    protected void createPopupMenu() {
    	popup = new JPopupMenu();
    	
		popup.add(getAction(FINDREP_ACTION));
		popup.add(getAction(FINDNEXT_ACTION));
		
		popup.add(getAction(SORT_ACTION));
		popup.add(getAction(FILTER_ACTION));
		popup.addSeparator();
		popup.add(getAction(SELECTALL_ACTION));
		popup.add(getAction(SELECTINV_ACTION));
	}

    /**
     * release memory
     */
    public void dereference() {
     	closeMenuBar();
     	
    	closeToolBar();
    	
    	removePopup();
    	
    	if (closeAction!=null){
    		removeWindowListener((WindowListener)closeAction);
    		closeAction.dereference();
    		closeAction = null;
    	}

    	// deref actions here actionTbl
    	for (Enumeration<EACMAction> e = actionTbl.elements(); e.hasMoreElements();){
    		EACMAction action = (EACMAction)e.nextElement(); 
    		action.dereference();
    	}
    	actionTbl.clear();
    	actionTbl = null;

    	removeAll();
        EACM.getEACM().removeUserWindow(this);
    }
    private void closeToolBar() {
    	if(tBar!=null){
    		for (int i=0; i<tBar.getComponentCount(); i++){
    			Component comp = tBar.getComponent(i);
    			if (comp instanceof JButton){
    				((JButton)comp).setAction(null);
    			}
    		}

    		tBar.removeAll();
    		tBar.setUI(null);
    		tBar = null;
    	}
    }
    
    private void removePopup(){
    	if (popup!=null){
    		for (int ii=0; ii<popup.getComponentCount(); ii++) {
    			Component comp = popup.getComponent(ii);
    			if (comp instanceof JMenuItem) {// separators are null
    				EACM.closeMenuItem((JMenuItem)comp);
    			}  
    		}
    		popup.setUI(null);
    		popup.removeAll();
    		popup = null;
    	}
    }

    private void closeMenuBar() {
    	if (menubar!=null){
    		for (int i=0; i<menubar.getMenuCount(); i++) {
    			JMenu amenu = menubar.getMenu(i);
    			EACM.closeMenu(amenu);
    		}
    		menubar.removeAll();
    		menubar.setUI(null);
    		menubar = null;
    	}
    }
}
