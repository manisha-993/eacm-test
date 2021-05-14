//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.*;


import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.eannounce.objects.SearchActionItem;
import COM.ibm.eannounce.objects.SearchRequest;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.mtrx.MatrixActionBase;

import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.mw.RMIMgr;

import com.ibm.eacm.nav.Navigate;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.table.CrossTable;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.table.BooleanTable;
import com.ibm.eacm.tabs.NavController;
import com.ibm.eacm.wused.WUsedActionTab;

import com.ibm.eacm.edit.SearchEditor;

/******************************************************************************
* This is used to display the search frame.
* @author Wendy Stimpson
*/
// $Log: SearchFrame.java,v $
// Revision 1.3  2013/10/22 18:34:14  wendy
// make sure valid data exists before displaying picklist frame
//
// Revision 1.2  2012/11/09 20:47:59  wendy
// check for null profile from getNewProfileInstance()
//
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//

public class SearchFrame extends EACMFrame  implements PropertyChangeListener,ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.3 $";

    private JPanel pnlMain = new JPanel(null);
    private JButton btnFind = null;
	private JButton btnCancel = null;
	private JButton btnResetAll = null;

    private SearchActionItem sai = null;
    private boolean picklistOpen = false;

    private Navigate navSrc = null;

    private WUsedActionTab wusedTabSrc = null;
    private MatrixActionBase mtrxSrc = null;
    private CrossTable crssTable = null;
    private Profile profile = null;

    // dynamic search
    private SearchEditor searchEditor = null;

    // plain search
    private JScrollPane scroll = null;
    private BooleanTable booleanSrchTbl = null;
    private JLabel lblSearch = null;
    private PropertyTextField txtSearch = null;

    /**
     * get unique id for this frame
     * getUID()
     * @return
     */
    public String getUID() {
    	String actionkey = sai.getActionItemKey();
		if (mtrxSrc != null) {
			actionkey = mtrxSrc.getUID()+":"+actionkey;
		}
		if (wusedTabSrc != null) {
			actionkey = wusedTabSrc.getUID()+":"+actionkey;
		}
		if (navSrc != null) {
			actionkey = navSrc.getUID()+":"+actionkey;
		}

    	return actionkey;
    }

    /**
     * called from navigate search action
     * @param sai
     * @param rst
     * @param source
     */
    public SearchFrame(SearchActionItem sai, RowSelectableTable rst, Navigate source)  {
    	this(sai);
    	navSrc = source;
        profile = navSrc.getProfile();

		if (navSrc.isPin()) {
			profile = LoginMgr.getNewProfileInstance(profile);
		}

    	init(rst);
        finishSetup(EACM.getEACM());
        setResizable(true);
    }
    /**
     * called by whereused link action
     * @param sai
     * @param rst
     * @param source
     */
    public SearchFrame(SearchActionItem sai, RowSelectableTable rst, WUsedActionTab source)  {
        this(sai);
        wusedTabSrc = source;
        profile = wusedTabSrc.getProfile();
        init(rst);
        finishSetup(EACM.getEACM());
        setResizable(true);
    }

    /**
     * called by matrix add column action
     * @param sai
     * @param source
     * @param rst
     * @param tbl
     */
    public SearchFrame(SearchActionItem sai, MatrixActionBase source, RowSelectableTable rst, CrossTable tbl)  {
        this(sai);
        mtrxSrc = source;
        crssTable = tbl;
        profile = mtrxSrc.getProfile();
        init(rst);
        finishSetup(EACM.getEACM());
        setResizable(true);
    }

    private SearchFrame(SearchActionItem sai2)  {
        super("search.panel", sai2.toString());
        this.sai = sai2;
        closeAction = new CloseAction(this);
    	setIconImage(Utils.getImage("search.gif"));
    }

    /* (non-Javadoc)
     * @see java.awt.Window#setVisible(boolean)
     */
    public void setVisible(boolean b) {
    	if(!b){
    		// enable caller actions
			// dont enable tab until picklist is done
			if(!picklistOpen){
				if(wusedTabSrc!=null){
					wusedTabSrc.enableActionsAndRestore();
				}
				if(mtrxSrc!=null){
					mtrxSrc.enableActionsAndRestore();
				}
				if (navSrc != null) {
					navSrc.enableActionsAndRestore();
				}
			}
    	}
        super.setVisible(b);
    }
    /**
     * called when row selection changes in searcheditor.searchtable
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
        	if(getAction(ENTITYDATA_ACTION)!=null){
        		getAction(ENTITYDATA_ACTION).setEnabled(true);
        	}

        	if(getAction(COPY_ACTION)!=null){
        		getAction(COPY_ACTION).setEnabled(true);
        	}
        	if(getAction(PASTE_ACTION)!=null){
        		getAction(PASTE_ACTION).setEnabled(true);
        	}
        	if(getAction(FIND_ACTION)!=null){
        		getAction(FIND_ACTION).setEnabled(true);
        	}
        }
    }
    /**
     * release memory
     */
    public void dereference() {
		if (searchEditor!=null){
			searchEditor.getTable().unregisterEACMAction(getAction(COPY_ACTION),KeyEvent.VK_V, Event.CTRL_MASK);
			searchEditor.getTable().unregisterEACMAction(getAction(PASTE_ACTION),KeyEvent.VK_C, Event.CTRL_MASK);

	    	searchEditor.getTable().getSelectionModel().removeListSelectionListener(this);
	    	searchEditor.getTable().removePropertyChangeListener((ResetAllAttrAction)getAction(RESETALLATTR_ACTION));
			searchEditor.dereference();
			searchEditor = null;
		}

    	super.dereference(); // this clears all actions

    	sai = null;
    	navSrc = null;
    	wusedTabSrc=null;
        mtrxSrc = null;

        btnFind.setAction(null);
        btnFind.setUI(null);
        btnFind = null;

        profile = null;

        btnCancel.setAction(null);
		btnCancel.setUI(null);
		btnCancel = null;

		if (btnResetAll!=null){
			btnResetAll.setAction(null);
			btnResetAll.setUI(null);
			btnResetAll = null;
		}

		if (scroll!=null){
			scroll.removeAll();
			scroll.setUI(null);
			scroll = null;
		}
		if (lblSearch!=null){
			lblSearch.setLabelFor(null);
			lblSearch.removeAll();
			lblSearch.setUI(null);
			lblSearch = null;
		}
		if (booleanSrchTbl !=null){
		    booleanSrchTbl.removePropertyChangeListener(DATACHANGE_PROPERTY,this);
			booleanSrchTbl.getSelectionModel().removeListSelectionListener(this);
			booleanSrchTbl.dereference();
			booleanSrchTbl = null;
		}
		if (txtSearch !=null){
			txtSearch.removePropertyChangeListener(this);
			txtSearch.dereference();
			txtSearch = null;
		}

    	pnlMain.removeAll();
    	pnlMain.setUI(null);
    	pnlMain = null;
    	crssTable = null;
    }
    /**
     * init frame components
     */
    private void init(RowSelectableTable rst) {
    	createActions();

    	createMenuBar();

    	btnFind = new JButton(getAction(FIND_ACTION));
    	btnFind.setMnemonic((char)((Integer)btnFind.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

    	btnCancel = new JButton(getAction(CANCEL_ACTION));
    	btnCancel.setMnemonic((char)((Integer)btnCancel.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

    	 if (sai.isDynaSearchEnabled()) {
    		btnResetAll = new JButton(getAction(RESETALLATTR_ACTION));
    		btnResetAll.setMnemonic((char)((Integer)btnResetAll.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

    		initDynaSearch(rst);
    		searchEditor.getTable().addPropertyChangeListener((ResetAllAttrAction)getAction(RESETALLATTR_ACTION));

    		// must override JComponent bindings or EACM copy and paste action are not invoked
    		searchEditor.getTable().registerEACMAction(getAction(COPY_ACTION), KeyEvent.VK_C, Event.CTRL_MASK);
    		searchEditor.getTable().registerEACMAction(getAction(PASTE_ACTION), KeyEvent.VK_V, Event.CTRL_MASK);
    	 } else {
    		initPlainSearch(rst);
    	}

        getContentPane().add(pnlMain);
    }

    private void initDynaSearch(RowSelectableTable rst) {
    	// get unique key for this
    	String srchkey = sai.getActionItemKey();
    	searchEditor = new SearchEditor(profile,rst, srchkey);

    	String title = this.getTitle();
    	if (title.trim().toLowerCase().endsWith("work with")){
    		// add more info
    		this.setTitle(title+" "+sai.getTargetEntityGroup());
    	}

    	getAction(FIND_ACTION).setEnabled(true);

    	((EntityDataAction)getAction(ENTITYDATA_ACTION)).setTable(searchEditor.getTable());
        //COPY_ACTION
      	((CopyAction)getAction(COPY_ACTION)).setTable(searchEditor.getTable());
        //PASTE_ACTION
      	((PasteAction)getAction(PASTE_ACTION)).setTable(searchEditor.getTable());

    	searchEditor.getTable().getSelectionModel().addListSelectionListener(this);

		JPanel m_pBtn = new JPanel(new GridLayout(1, 3, 10, 10));
		m_pBtn.add(btnFind);
		m_pBtn.add(btnResetAll);
		m_pBtn.add(btnCancel);

		GroupLayout layout = new GroupLayout(pnlMain);
		pnlMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(searchEditor);
		leftToRight.addComponent(m_pBtn);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(searchEditor);
		topToBottom.addComponent(m_pBtn,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE); // prevent vertical growth

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);

//		getRootPane().setDefaultButton(btnFind); this interferes with ctrl-enter to open editor
    }

    private void initPlainSearch(RowSelectableTable rst) {
        SearchRequest sr = sai.getSearchRequest();
        if(sr!=null){
        	rst = sr.getTable();
        }
    	lblSearch = new JLabel(Utils.getResource("fnd-l"));
        booleanSrchTbl = new BooleanTable(rst,profile); //gen_search

        booleanSrchTbl.getSelectionModel().addListSelectionListener(this);
        booleanSrchTbl.addPropertyChangeListener(DATACHANGE_PROPERTY,this);

        scroll = new JScrollPane(booleanSrchTbl);

        txtSearch = new PropertyTextField(25, PropertyTextField.MINIMUM_EQUAL_TYPE, sai.getSearchStringMin());
        txtSearch.addPropertyChangeListener(this);
        lblSearch.setLabelFor(txtSearch);

		JPanel m_pBtn = new JPanel(new GridLayout(1, 2, 10, 10));
		m_pBtn.add(btnFind);
		m_pBtn.add(btnCancel);

	    JPanel dtPanel = new JPanel(new BorderLayout(5,5));
	    dtPanel.add(lblSearch,BorderLayout.WEST);
	    dtPanel.add(txtSearch,BorderLayout.CENTER);

		GroupLayout layout = new GroupLayout(pnlMain);
		pnlMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(dtPanel);
		leftToRight.addComponent(scroll);
		leftToRight.addComponent(m_pBtn);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(dtPanel);
		topToBottom.addComponent(scroll);
		topToBottom.addComponent(m_pBtn);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);

		getRootPane().setDefaultButton(btnFind);

    }
    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent pce) {
        String property = pce.getPropertyName();

        if (property.equalsIgnoreCase(PropertyTextField.ACTIVATE) || property.equalsIgnoreCase(DATACHANGE_PROPERTY) ) {
        	getAction(FIND_ACTION).setEnabled(true);
        } else if (property.equalsIgnoreCase(PropertyTextField.DEACTIVATE)) {
        	getAction(FIND_ACTION).setEnabled(false);
        }
    }
    /**
     * create all of the actions, they are shared between toolbar and menu
     */
    private void createActions() {
        //FIND_ACTION
        EACMAction act = new FindAction();
        addAction(act);
        //CANCEL_ACTION
    	act = new CancelAction();
    	addAction(act);

		if(sai.isDynaSearchEnabled()){
			//RESETALLATTR_ACTION
		 	act = new ResetAllAttrAction();
		 	addAction(act);
			 //ENTITYDATA_ACTION
	        act = new EntityDataAction((RSTTable)null);
	        addAction(act);

	        //COPY_ACTION
	        addAction(new CopyAction(null));
	        //PASTE_ACTION
	        addAction(new PasteAction(null));
		}
    }
    /**
     * createMenuBar
     */
    private void createMenuBar() {
        menubar = new JMenuBar();
        JMenu mnuFile = new JMenu(Utils.getResource(FILE_MENU));
        mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));

    	JMenuItem mi = mnuFile.add(closeAction);
    	mi.setVerifyInputWhenFocusTarget(false);

    	addGlobalActionMenuItem(mnuFile, BREAK_ACTION);

		menubar.add(mnuFile);

		if(sai.isDynaSearchEnabled()){
			// create table.
			JMenu mnuTbl = new JMenu(Utils.getResource(TABLE_MENU));
			mnuTbl.setMnemonic(Utils.getMnemonic(TABLE_MENU));

			addLocalActionMenuItem(mnuTbl, ENTITYDATA_ACTION);
			menubar.add(mnuTbl);

			mnuTbl = new JMenu(Utils.getResource(EDIT_MENU));
			mnuTbl.setMnemonic(Utils.getMnemonic(EDIT_MENU));

			addLocalActionMenuItem(mnuTbl, COPY_ACTION);
			addLocalActionMenuItem(mnuTbl, PASTE_ACTION);

			menubar.add(mnuTbl);
		}
    	setJMenuBar(menubar);
    }

    //================================================================
    // execute search
    private FindWorker findWorker=null;
    private class FindAction extends EACMAction
    {
    	private static final long serialVersionUID = 1L;
    	FindAction() {
    		super(FIND_ACTION);
    		setEnabled(false);
    	}

    	/**
    	 * derived classes should override for conditional checks needed before enabling
    	 * @return
    	 */
    	protected boolean canEnable(){
    		boolean ok = true;
    	   	if (booleanSrchTbl!=null){
        		ok = booleanSrchTbl.hasSelection();
        	}

    	   	if(ok && txtSearch !=null){
    	   		ok = txtSearch.isActive();
    	   	}

    		return ok;
    	}

    	public void actionPerformed(ActionEvent e) {
    		if (!sai.isDynaSearchEnabled()) {
    			try {
    				//this cant happen because txtSearch prevents it
    				// T.est(_str.length() > 2, "Search String is too small .. needs to be at least 3 characters.");
    				sai.setSearchString(txtSearch.getText());
    			} catch (MiddlewareRequestException mre) {
    				UI.showException(SearchFrame.this,mre);
    				return;
    			}
    		} else if (!searchEditor.canStopEditing()) { //close any edits
    			UI.showFYI(SearchFrame.this, "Can not close editor");
    			return;
    		}
    		disableActionsAndWait();
    		findWorker = new FindWorker();
    		RMIMgr.getRmiMgr().execute(findWorker);
    	}
    }

	public void disableActionsAndWait(){
		super.disableActionsAndWait();
		if (searchEditor !=null){
			searchEditor.setEnabled(false);
		}
	}
	public void enableActionsAndRestore(){
		super.enableActionsAndRestore();
		if (searchEditor !=null){
			searchEditor.setEnabled(true);
		}
	}

    private class FindWorker extends DBSwingWorker<EntityList, Void> {
    	@Override
    	public EntityList doInBackground() {
    		EntityList list=null;
    		try{
    			list = DBUtils.doSearch(sai,profile, SearchFrame.this);
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(this);
            	findWorker = null;
    		}
			return list;
    	}
    	
        @Override
        public void done() {
        	boolean success = false;
        	//this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			EntityList list = get();
        			if (list !=null){
        				if (navSrc != null) {
        					if (hasData(list)) {
        					//if (list.getTable().getRowCount() > 0) { only display picklist if it has valid data
        						if (list.isPicklist()){
        							picklistOpen = true;
        							navSrc.showPicklist(list);
        						}else if(navSrc.getNavController().isPin()){
        							Profile prof = LoginMgr.getNewProfileInstance(list.getProfile());
        							if(prof!=null){
        								NavController navCtrl = new NavController(prof);
        								navCtrl.setParentProfile(navSrc.getProfile());
        								navCtrl.init(list,navSrc.getHistory());

        								Utils.getTabTitle("navTab", navCtrl.getProfile());

        								EACM.getEACM().addTab(navSrc.getNavController(), navCtrl);
        							}
        						}else{
        							list.getProfile().setReadLanguage(navSrc.getProfile().getReadLanguage());
        							navSrc.load(list, Navigate.NAVIGATE_INIT_LOAD);
        						}
        						success = true;
        					}else{
        						//msg24005 = Search found no matching items.
        	        			com.ibm.eacm.ui.UI.showErrorMessage(SearchFrame.this,Utils.getResource("msg24005"));
        	        			list.dereference();
        					}
        				}else{
        					if (hasData(list)) {
        						success = true;
        						if (mtrxSrc != null) {
        							picklistOpen = true;
        							mtrxSrc.showPicklist(crssTable,list);
        						}
        						if (wusedTabSrc != null) {
        							picklistOpen = true;
        							wusedTabSrc.showPicklist(list);
        						}
        					}else{
        						//msg24005 = Search found no matching items.
        	        			com.ibm.eacm.ui.UI.showErrorMessage(SearchFrame.this,Utils.getResource("msg24005"));

        						list.dereference();
        					}
        				}
        			}
        		}
        	} catch (InterruptedException ignore) {}
        	catch (java.util.concurrent.ExecutionException e) {
        		listErr(e,"end search");
        	}finally{
        		if (success){
        			// close the search dialog
        			closeAction.actionPerformed(null);
        		}else{
        			toFront();
        			enableActionsAndRestore();
        		}
        	}
        }

        private boolean hasData(EntityList eList) {
        	if (eList != null) {
        		for (int i = 0; i < eList.getEntityGroupCount(); ++i) {
        			EntityGroup eg = eList.getEntityGroup(i);
        			if (eg.isDisplayable() && eg.getEntityItemCount() > 0) {
        				return true;
        			}
        		}
            }
            return false;
        }
     	public void notExecuted(){
     		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
     		enableActionsAndRestore();
        	findWorker = null;
    	}
    }

    protected void enableActions(){
    	if (booleanSrchTbl!=null){
    		getAction(FIND_ACTION).setEnabled(true);
    	}else{
    		searchEditor.setEnabled(true);
        	getAction(FIND_ACTION).setEnabled(true);
    	}

    	if(getAction(RESETALLATTR_ACTION)!=null){
    		getAction(RESETALLATTR_ACTION).setEnabled(true);
    	}
    	if(getAction(ENTITYDATA_ACTION)!=null){
    		getAction(ENTITYDATA_ACTION).setEnabled(true);
    	}
    	if(getAction(COPY_ACTION)!=null){
    		getAction(COPY_ACTION).setEnabled(true);
    	}
       	if(getAction(PASTE_ACTION)!=null){
    		getAction(PASTE_ACTION).setEnabled(true);
    	}
    }

	protected void disableActions(){
    	getAction(FIND_ACTION).setEnabled(false);
    	if(getAction(RESETALLATTR_ACTION)!=null){
    		getAction(RESETALLATTR_ACTION).setEnabled(false);
    	}
    	if(getAction(ENTITYDATA_ACTION)!=null){
    		getAction(ENTITYDATA_ACTION).setEnabled(false);
    	}
    	if(getAction(COPY_ACTION)!=null){
    		getAction(COPY_ACTION).setEnabled(false);
    	}
       	if(getAction(PASTE_ACTION)!=null){
    		getAction(PASTE_ACTION).setEnabled(false);
    	}
    	if (searchEditor!=null){
    		searchEditor.setEnabled(false);
    	}
	}

    //================================================================
    private class ResetAllAttrAction extends EACMAction implements PropertyChangeListener {
    	private static final long serialVersionUID = 1L;
    	ResetAllAttrAction() {
    		super(RESETALLATTR_ACTION);
    		setEnabled(true);
    	}
    	/* (non-Javadoc)
    	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
    	 */
    	public void propertyChange(PropertyChangeEvent event) {
    		if(event.getPropertyName().equals(DATACHANGE_PROPERTY)) {
    			setEnabled(true);
    		}
    	}
    	public void setEnabled(boolean newValue) {
    		// haschanges will always be true because entityid<0
    		boolean attrchgs= false;
    		EntityGroup eg = sai.getEntityGroup();
    		if(eg!=null){
    			EntityItem ei = eg.getEntityItem(0);
    			if (ei!=null){
    				attrchgs = ei.hasAttributeChanges();
    			}
    		}
    		if(!attrchgs){
    			//look if eg1 or eg2 exist..
    			eg = sai.getEntityGroup1();
        		if(eg!=null){
        			EntityItem ei = eg.getEntityItem(0);
        			if (ei!=null){
        				attrchgs = ei.hasAttributeChanges();
        			}
        		}
    		}
    		if(!attrchgs){
    			//look if eg1 or eg2 exist..
    			eg = sai.getEntityGroup2();
        		if(eg!=null){
        			EntityItem ei = eg.getEntityItem(0);
        			if (ei!=null){
        				attrchgs = ei.hasAttributeChanges();
        			}
        		}
    		}

    		super.setEnabled(newValue && attrchgs);
    	}
    	public void actionPerformed(ActionEvent e) {
    		searchEditor.rollback();
		}
	}
    //================================================================
	private class CancelAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		CancelAction() {
			super(CANCEL_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			closeAction.actionPerformed(e);
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
			if (findWorker!=null){
				//findWorker.cancel(true);
				RMIMgr.getRmiMgr().cancelWorker(findWorker,true);
			}

			super.actionPerformed(e);
		}
    }
}