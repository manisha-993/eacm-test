//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.ui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;

import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.*;
import com.ibm.eacm.table.MaintTable;

import com.ibm.eacm.mw.*;

/******************************************************************************
 * This is used to display the flag maintenance frame.
 * @author Wendy Stimpson
 */
//$Log: MaintenanceFrame.java,v $
//Revision 1.7  2015/03/13 18:30:55  stimpsow
//allow alt+f4 to get to the frame, toolbar was grabbing it
//
//Revision 1.6  2014/02/11 19:24:46  wendy
//don't enable actions if currently waiting
//
//Revision 1.5  2014/02/10 21:35:16  wendy
//close if aborted due to a mw exception
//
//Revision 1.4  2014/02/10 20:11:52  wendy
//prevent null ptr if deref has run before done()
//
//Revision 1.3  2013/09/23 21:12:00  wendy
//control sort when a row is updated
//
//Revision 1.2  2013/09/17 20:47:40  wendy
//make sure editor has focus
//
//Revision 1.1  2012/09/27 19:39:11  wendy
//Initial code
//
public class MaintenanceFrame extends EACMFrame implements ListSelectionListener, FindableComp
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.7 $";
	private static final String ADD = "addMF";
	private static final String RETIRE = "rtrSF";
	private static final String UNRETIRE = "unRetire";
	private static final String COMMIT = "cmtF";

	private JPanel pnlMain = new JPanel(null);
	private JLabel lblFilter = new JLabel(Utils.getImageIcon("fltr.gif"));
	private MaintTable table = null;
	private JPanel maintPanel = new JPanel(new BorderLayout());
	private JScrollPane jsp = null;
	private int expStatusColId=1;
	private int wasAborted = -1;  //if user selects abort after a MiddlewareException, just close
	private EntityList parentList = null;

	/**
	 * @param mflist
	 * @param list
	 */
	public MaintenanceFrame(MetaFlagMaintList mflist,EntityList list)  {
		//maint.panel = EACM {0} Flag Maintenance
		super("maint.panel", mflist.getLongDescription());

		parentList = list;

		closeAction = new CloseAction(this);

		setIconImage(Utils.getImage("maint.gif"));

		init(mflist);

		finishSetup(EACM.getEACM());

		setResizable(true);
		enableActions();
	}
	/**
	 * enable label
	 * @param _filter
	 */
	public void setFilter(boolean _filter) {
		lblFilter.setEnabled(_filter);
	}
	/**
	 * get the meta flag key used for this maintlist
	 * @return
	 */
	public String getMetaFlagKey(){
		return table.getMetaFlagKey();
	}

	public void updateLists(MetaFlagMaintList mflist,EntityList list) {
		int origcolcnt = table.getColumnCount();
		setTitle(Utils.getResource("maint.panel", mflist.getLongDescription()));
		parentList = list;
		table.updateList(mflist);
//		expired status starts in the last column
		expStatusColId = table.getColumnCount()-1;
		enableActions();
		if(origcolcnt != table.getColumnCount()){
			setSize(this.getPreferredSize());
		}
	}
	/**
	 * init frame components
	 */
	private void init(MetaFlagMaintList mflist) {
		table = new MaintTable(mflist);
		table.getSelectionModel().addListSelectionListener(this);

		createActions();

		createMenuBar();
		createToolbar();

		// expired status starts in the last column
		expStatusColId = table.getColumnCount()-1;

		jsp = new JScrollPane(table);
		jsp.setFocusable(false);
		table.resizeCells();

		lblFilter.setEnabled(false);
		lblFilter.setToolTipText(Utils.getToolTip("maintFilterLbl"));
		lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilter.setVerticalAlignment(SwingConstants.CENTER);

		GroupLayout layout = new GroupLayout(pnlMain);
		pnlMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(lblFilter, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);// this centers it
		leftToRight.addComponent(jsp);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(lblFilter);
		topToBottom.addComponent(jsp);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);

		maintPanel.add(tBar,BorderLayout.NORTH);
		maintPanel.add(pnlMain,BorderLayout.CENTER);

		getContentPane().add(maintPanel);
	}


	public Findable getFindable() {
		return table;
	}

	/**
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent _lse) {
		if (!_lse.getValueIsAdjusting()) {
			if(!isWaiting()){ // don't re-enable the actions if currently waiting
				enableActions();
			}
		}
	}
    /**
     * createToolbar using same actions as menus
     */
    private void createToolbar() {
    	tBar = new JToolBar(){
    		private static final long serialVersionUID = 1L;
    	    /* (non-Javadoc)
    	     * need to copy this method to override the button keybinding
    	     * @see javax.swing.JToolBar#createActionComponent(javax.swing.Action)
    	     */
    	    protected JButton createActionComponent(Action a) {
    	        JButton b = new JButton() {
    				private static final long serialVersionUID = 1L;
    				protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
    	                PropertyChangeListener pcl = createActionChangeListener(this);
    	                if (pcl==null) {
    	                    pcl = super.createActionPropertyChangeListener(a);
    	                }
    	                return pcl;
    	            }
    	    		/* (non-Javadoc)
    	    		 * keystrokes are going to the toolbar buttons, ALT-F4 was not going to the frame
    	    		 * @see javax.swing.JComponent#processKeyBinding(javax.swing.KeyStroke, java.awt.event.KeyEvent, int, boolean)
    	    		 */
    	    		protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
    	              	if(e.getKeyCode()==KeyEvent.VK_ALT || e.getKeyCode()==KeyEvent.VK_F4){
    	                	return false;
    	            	}
    	    			return super.processKeyBinding(ks, e, condition, pressed); 
    	    		}
    	        };
    	        if (a != null && (a.getValue(Action.SMALL_ICON) != null ||
    	                          a.getValue(Action.LARGE_ICON_KEY) != null)) {
    	            b.setHideActionText(true);
    	        }
    	        b.setHorizontalTextPosition(JButton.CENTER);
    	        b.setVerticalTextPosition(JButton.BOTTOM);
    	        return b;
    	    }
    	};
    	tBar.setFloatable(false);

       	tBar.add(getAction(FINDREP_ACTION));
      	tBar.add(getAction(FILTER_ACTION));
    }
	/**
	 * create all of the actions
	 */
	private void createActions() {
		addAction(new ResetSingleAction());

		addAction(new ResetAllAction());
		addAction(new AddAction());

		addAction(new FindRepAction());
		addAction(new FindNextAction());

		addAction(new RetireAction());
		addAction(new UnRetireAction());
		addAction(new CommitAction());

		addAction(new FilterAction(this,table));

		//SELECTALL_ACTION
		addAction(new SelectAllAction(table));
		//SELECTINV_ACTION
		addAction(new SelectInvertAction(table));
	}
	/**
	 * createMenuBar
	 */
	private void createMenuBar() {
		menubar = new JMenuBar();
		createFileMenu();
		createTableMenu();

		setJMenuBar(menubar);
	}

	private void createTableMenu(){
		JMenu mnuTbl = new JMenu(Utils.getResource(TABLE_MENU));
		mnuTbl.setMnemonic(Utils.getMnemonic(TABLE_MENU));

		addLocalActionMenuItem(mnuTbl, FINDREP_ACTION);
		addLocalActionMenuItem(mnuTbl, FINDNEXT_ACTION);
		addLocalActionMenuItem(mnuTbl, FILTER_ACTION);
		mnuTbl.addSeparator();
		addLocalActionMenuItem(mnuTbl, SELECTALL_ACTION);
		addLocalActionMenuItem(mnuTbl, SELECTINV_ACTION);
		mnuTbl.addSeparator();
		addLocalActionMenuItem(mnuTbl, RESETALLATTR_ACTION);
		addLocalActionMenuItem(mnuTbl, RESETONEATTR_ACTION);
		mnuTbl.addSeparator();

		addLocalActionMenuItem(mnuTbl, ADD);
		addLocalActionMenuItem(mnuTbl, RETIRE);
		addLocalActionMenuItem(mnuTbl, UNRETIRE);
		addLocalActionMenuItem(mnuTbl, COMMIT);

		menubar.add(mnuTbl);
	}
	private void createFileMenu() {
		JMenu mnuFile = new JMenu(Utils.getResource(FILE_MENU));
		mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));

		JMenuItem mi = mnuFile.add(closeAction);
		mi.setVerifyInputWhenFocusTarget(false);

		menubar.add(mnuFile);
	}

	/**
	 * release memory
	 */
	public void dereference() {
		super.dereference();

		table.getSelectionModel().removeListSelectionListener(this);
		table.dereference();
		table = null;

		maintPanel.removeAll();
	    maintPanel.setUI(null);
	    maintPanel = null;

		pnlMain.removeAll();
		pnlMain.setUI(null);
		pnlMain = null;

		jsp.removeAll();
		jsp.setUI(null);
		jsp = null;

		lblFilter.removeAll();
		lblFilter.setUI(null);
		lblFilter = null;
		parentList = null;
	}

	//=============================================================================
	//actions and workers
	//=============================================================================

	private class ResetAllAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		ResetAllAction() {
			super(RESETALLATTR_ACTION,KeyEvent.VK_Z, Event.CTRL_MASK + Event.SHIFT_MASK);
			super.setEnabled(false);
		}
		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			boolean ok = table.getRowCount()>0;
			return ok;
		}
		public void actionPerformed(ActionEvent e) {
			table.rollback();
		}
	}
	private class ResetSingleAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		ResetSingleAction() {
			super(RESETONEATTR_ACTION,KeyEvent.VK_Z, Event.CTRL_MASK);
			super.setEnabled(false);
		}
		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			boolean ok = false;
			int[] rows = table.getSelectedRows();
			if (rows.length>0) {
				ok = true;
			}
			return ok;
		}
		public void actionPerformed(ActionEvent e) {
			table.rollbackSingle();
		}
	}

	private class AddAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		AddAction() {
			super(ADD,KeyEvent.VK_N, Event.CTRL_MASK);
		}

		public void actionPerformed(ActionEvent e) {
			table.stopCellEditing();
			table.addRow();
			table.requestFocusInWindow(); // send keystrokes to the new row
		}
	}

	private int[] getValidRows(String condition){
		int[] modelRows = null;
		int[] rows = table.getSelectedRows();
		Vector<Integer> tmp = new Vector<Integer>();
		int viewcolid = table.convertColumnIndexToView(expStatusColId);
		if (rows.length>0) {
			for (int i=0;i<rows.length;++i) {
				Object obj = table.getValueAt(rows[i], viewcolid);
				if(obj != null && condition.equalsIgnoreCase(obj.toString()) && !table.isNew(rows[i])){
					tmp.add(new Integer(table.convertRowIndexToModel(rows[i])));
				}
			}
		}

		if(tmp.size()>0){
			modelRows = new int[tmp.size()];
			for (int i=0;i<tmp.size(); i++){
				modelRows[i]=tmp.elementAt(i).intValue();
			}
		}else{
			modelRows = new int[0];
		}
		return modelRows;
	}

	private class RetireAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		RetireAction() {
			super(RETIRE,KeyEvent.VK_R, Event.CTRL_MASK);
			super.setEnabled(false);
		}
		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			return getValidRows("N").length>0;
		}

		public void actionPerformed(ActionEvent e) {
			table.cancelCurrentEdit();
			disableActionsAndWait();
			worker = new ExpireWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}

		class ExpireWorker extends HeavyWorker<Exception, Void> {
			@Override
			public Exception doInBackground() {
				try{
					int[] mRows = getValidRows("N");
					RowSelectableTable tmpRST = table.getRSTable();
					expireFlags(tmpRST,mRows);
					if(wasAborted != ABORT_BUTTON){
						updateFlagCodes(tmpRST);
					}
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
							com.ibm.eacm.ui.UI.showException(table,e);
						}else{
							if (wasAborted != ABORT_BUTTON) {
								table.refresh();
							}
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"saving to PDH");
				}finally{
					enableActionsAndRestore();
				}
			}
			public void notExecuted(){
				Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
				enableActionsAndRestore();
				worker = null;
			}
		}
	}

	private void expireFlags(RowSelectableTable rst, int[] rows) {
		try {
			rst.retireFlags(null,ro(),parentList.getProfile(),rows);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						rst.retireFlags(null,ro(),parentList.getProfile(),rows);
					}catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							wasAborted = com.ibm.eacm.ui.UI.showMWExcPrompt(this, e);
							if (wasAborted == RETRY) {
								expireFlags(rst,rows);
							}else if(wasAborted == ABORT_BUTTON){
								closeAction.actionPerformed(null);
							}
						}else{
							com.ibm.eacm.ui.UI.showException(this,e, "mw.err-title");
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					wasAborted = com.ibm.eacm.ui.UI.showMWExcPrompt(this, exc);
					if (wasAborted == RETRY) {
						expireFlags(rst,rows);
					}else if(wasAborted == ABORT_BUTTON){
						closeAction.actionPerformed(null);
					}// else user decide to ignore or exit
				}else{
					com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
				}
			}
		}
	}
	private class UnRetireAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		UnRetireAction() {
			super(UNRETIRE,KeyEvent.VK_U, Event.CTRL_MASK);
			super.setEnabled(false);
		}
		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			return getValidRows("Y").length>0;
		}
		public void actionPerformed(ActionEvent e) {
			table.cancelCurrentEdit();
			disableActionsAndWait();
			worker = new UnexpireWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}
		class UnexpireWorker extends HeavyWorker<Exception, Void> {
			@Override
			public Exception doInBackground() {
				try{
					int[] mRows = getValidRows("Y");
					RowSelectableTable tmpRST = table.getRSTable();
					unexpireFlags(tmpRST,mRows);
					if(wasAborted != ABORT_BUTTON){
						updateFlagCodes(tmpRST);
					}
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
							com.ibm.eacm.ui.UI.showException(table,e);
						}else{
							if (wasAborted != ABORT_BUTTON) {
								table.refresh();
							}
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"saving to PDH");
				}finally{
					enableActionsAndRestore();
				}
			}
			public void notExecuted(){
				Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
				enableActionsAndRestore();
				worker = null;
			}
		}
	}
	private void unexpireFlags(RowSelectableTable rst, int[] rows) {
		try {
			rst.unexpireFlags(null,ro(),parentList.getProfile(),rows);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						rst.unexpireFlags(null,ro(),parentList.getProfile(),rows);
					}catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							wasAborted = com.ibm.eacm.ui.UI.showMWExcPrompt(this, e);
							if (wasAborted == RETRY) {
								unexpireFlags(rst,rows);
							} else if(wasAborted == ABORT_BUTTON){
								closeAction.actionPerformed(null);
							}
						}else{
							com.ibm.eacm.ui.UI.showException(this,e, "mw.err-title");
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					wasAborted = com.ibm.eacm.ui.UI.showMWExcPrompt(this, exc);
					if (wasAborted == RETRY) {
						unexpireFlags(rst,rows);
					}else if(wasAborted == ABORT_BUTTON){
						closeAction.actionPerformed(null);
					}// else user decide to ignore or exit
				}else{
					com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
				}
			}
		}
	}

	private class CommitAction extends EACMAction implements PropertyChangeListener,DocumentListener
	{
		private static final long serialVersionUID = 1L;
		CommitAction() {
			super(COMMIT,KeyEvent.VK_S, Event.CTRL_MASK);
			super.setEnabled(false);
			table.addPropertyChangeListener(DATACHANGE_PROPERTY,this);
			table.addDocumentListener(this); // will be removed when table is derefed
		}
		public void dereference(){
			table.removePropertyChangeListener(DATACHANGE_PROPERTY,this);
			super.dereference();
		}
		/* (non-Javadoc)
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent event) {
			if(event.getPropertyName().equals(DATACHANGE_PROPERTY)) {
				setEnabled(true);
			}
		}

		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			boolean ok = table.hasChanges();
			if(ok){
				for(int i=0; i<table.getRowCount(); i++){
					Object obj = table.getValueAt(i, table.convertColumnIndexToView(0));
					// must have value to commit
					if(obj ==null || obj.toString().trim().length()==0){
						TableCellEditor editor = table.getCellEditor();
						if(editor !=null){
							obj = editor.getCellEditorValue(); // get value currently under edit
							ok = !(obj ==null || obj.toString().trim().length()==0);
						}else{
							ok = false;
						}
						break;
					}
				}
			}
			return ok;
		}
		public void actionPerformed(ActionEvent e) {
			table.stopCellEditing();
			disableActionsAndWait();
			worker = new CommitWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}

		class CommitWorker extends HeavyWorker<Exception, Void> {
			@Override
			public Exception doInBackground() {
				try{
					table.commit();
					updateFlagCodes(table.getRSTable());
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
							com.ibm.eacm.ui.UI.showException(table,e);
						}else{
							if(wasAborted != ABORT_BUTTON && table !=null){ // deref may have run
								table.updateTableWithSelectedRows();//refresh();
							}
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"saving to PDH");
				}finally{
					enableActionsAndRestore();
				}
			}
			public void notExecuted(){
				Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
				enableActionsAndRestore();
				worker = null;
			}
		}

		public void changedUpdate(DocumentEvent e) {
			setEnabled(true);
		}
		public void insertUpdate(DocumentEvent e) {
			setEnabled(true);
		}
		public void removeUpdate(DocumentEvent e) {
			setEnabled(true);
		}
	}
	private void updateFlagCodes(RowSelectableTable rst) {
		try {
			rst.updateFlagCodes(null,ro(),parentList.getProfile(),parentList);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						rst.updateFlagCodes(null,ro(),parentList.getProfile(),parentList);
					}catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							wasAborted = com.ibm.eacm.ui.UI.showMWExcPrompt(this, e);
							if (wasAborted == RETRY) {
								updateFlagCodes(rst);
							}else if(wasAborted == ABORT_BUTTON){
								closeAction.actionPerformed(null);
							}
						}else{
							com.ibm.eacm.ui.UI.showException(this,e, "mw.err-title");
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					wasAborted = com.ibm.eacm.ui.UI.showMWExcPrompt(this, exc);
					if (wasAborted == RETRY) {
						updateFlagCodes(rst);
					}else if(wasAborted == ABORT_BUTTON){
						closeAction.actionPerformed(null);
					}// else user decide to ignore or exit
				}else{
					com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
				}
			}
		}
	}

	//================================================================
	private class CloseAction extends CloseFrameAction
	{
		private static final long serialVersionUID = 1L;
		CloseAction(EACMFrame f) {
			super(f);
		}

		public void actionPerformed(ActionEvent e) {
			if(okToClose()){
				super.actionPerformed(e);
			}
		}
	}
	private boolean okToClose() {
		table.stopCellEditing();

		if (table.hasChanges()) {
			int action=  com.ibm.eacm.ui.UI.showConfirmYesNoCancel(this, Utils.getResource("updtMsg2"));
			if (action == YES_BUTTON){
				try{
					table.commit();
					updateFlagCodes(table.getRSTable());
					return true;
				}catch(Exception x){
					com.ibm.eacm.ui.UI.showException(table,x);
					return false;
				}
			} else if (action == NO_BUTTON){
				return true;
			} else {
				return false;
			}
		}

		return true;
	}

}