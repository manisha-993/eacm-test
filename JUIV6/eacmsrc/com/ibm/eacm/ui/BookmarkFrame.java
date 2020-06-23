//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.*;

import com.ibm.eacm.table.BookmarkTable;
import com.ibm.eacm.tabs.NavController;

import com.ibm.eacm.mw.*;

/******************************************************************************
* This is used to display the bookmark frame.  done as a frame to allow min and max buttons
* @author Wendy Stimpson
*/
// $Log: BookmarkFrame.java,v $
// Revision 1.3  2015/03/13 18:30:55  stimpsow
// allow alt+f4 to get to the frame, toolbar was grabbing it
//
// Revision 1.2  2013/07/18 18:39:29  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//

public class BookmarkFrame extends EACMFrame implements ListSelectionListener,
PropertyChangeListener,FindableComp
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2015  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.3 $";

    private JPanel bookMarkPanel = new JPanel(new BorderLayout());
    private JPanel pnlMain = new JPanel(null);
    private JLabel lblText = new JLabel(Utils.getResource("bookmark.name"));
    private JLabel lblFilter = new JLabel(Utils.getImageIcon("fltr.gif"));
    private PropertyTextField eText = null;
    private BookmarkTable table = null;

    private JScrollPane jsp = null;

    private Hashtable<String, Boolean> stateTbl = new Hashtable<String, Boolean>();

    private BookmarkGroup bGroup = null;
    private EANActionItem curNavAction = null;
    private EANActionItem[] navHistoryActions = null;
    private EANActionItem[] newHistoryActions = null;
    private EANActionItem newActionItem = null;

    /**
     * constructor
     */
    public BookmarkFrame()  {
        super("bookmark.panel");

    	closeAction = new CloseAction(this);

    	setIconImage(Utils.getImage("bookmark.gif"));

    	init();

        finishSetup(EACM.getEACM());

        setResizable(true);
        
    }

    /**
     * enable label
     * @param _filter
     */
    public void setFilter(boolean _filter) {
    	lblFilter.setEnabled(_filter);
    }
    /**
     * init frame components
     */
    private void init() {
        EACMAction act = new LoadAction();
        addAction(act);

    	table = new BookmarkTable(act);
    	table.getSelectionModel().addListSelectionListener(this);
    	table.addMouseListener(new MouseAdapter() { // base class deref will remove this
            public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    popup.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
		});

    	//When this property is true the table uses the entire height of the container, even if the
    	//table doesn't have enough rows to use the whole vertical space. This makes it easier to use
    	//the table as a drag-and-drop target.
    	//table.setFillsViewportHeight(true);

    	createActions();

    	createMenuBar();
     	createPopupMenu();
        createToolbar();

    	jsp = new JScrollPane(table);
        jsp.setFocusable(false);
        Dimension d = new Dimension(700, 200);
        jsp.setPreferredSize(d);
        jsp.setSize(d);

        eText = new PropertyTextField(40, PropertyTextField.MINIMUM_TYPE, 0);
        eText.setMaximumLength(254);
        eText.addPropertyChangeListener(this);
        lblText.setLabelFor(eText);
        lblText.setDisplayedMnemonic(Utils.getMnemonic("bookmark.name"));

        lblFilter.setEnabled(false);
        lblFilter.setToolTipText(Utils.getToolTip("bookFilterLbl"));
        lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFilter.setVerticalAlignment(SwingConstants.CENTER);

        GroupLayout layout = new GroupLayout(pnlMain);
        pnlMain.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
        GroupLayout.SequentialGroup bknm= layout.createSequentialGroup();
        bknm.addComponent(lblText);
        bknm.addComponent(eText);
        leftToRight.addGroup(bknm);
        leftToRight.addComponent(lblFilter, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);// this centers it
        leftToRight.addComponent(jsp);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
        GroupLayout.ParallelGroup bknmRow = layout.createParallelGroup();
        bknmRow.addComponent(lblText);
        bknmRow.addComponent(eText,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                GroupLayout.PREFERRED_SIZE); // prevent vertical growth
        topToBottom.addGroup(bknmRow);
        topToBottom.addComponent(lblFilter);
        topToBottom.addComponent(jsp);

        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);

        bookMarkPanel.add(tBar,BorderLayout.NORTH);
        bookMarkPanel.add(pnlMain,BorderLayout.CENTER);

        getContentPane().add(bookMarkPanel);
    }

    /**
     * look for any saved filtergroup in preferences
     */
    private void useSavedFilterGroup() {
     	String key = FilterDialog.getConstant() + table.getUIPrefKey();
    	Object o = SerialPref.getPref(key);
    	if (o instanceof FilterGroup) {
    		FilterGroup fGroup = (FilterGroup) o;
    		table.setFilterGroup(fGroup);
     		boolean tblWasFiltered = SerialPref.getPref(key+FilterDialog.ISFILTERED_KEY, false);
    		if (tblWasFiltered){
    			table.filter();
    		}else{
    			// removing rows will do a refresh and use any filter if not set to false
    			table.getBookmarkTable().setUseFilter(false); // default is true
    		}
    	}
    }

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.FindableComp#getFindable()
	 */
	public Findable getFindable() {
		return table;
	}

    /* (non-Javadoc)
     * @see java.awt.Window#setVisible(boolean)
     */
    public void setVisible(boolean b) {
        if (b){
        	eText.requestFocusInWindow();
        }
        super.setVisible(b);
    }
    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * called when textfield changes
     */
    public void propertyChange(PropertyChangeEvent _pce) {
    	// only react to activate chgs
    	if(PropertyTextField.DEACTIVATE.equals(_pce.getNewValue()) ||
    			PropertyTextField.ACTIVATE.equals(_pce.getNewValue())){
    		toggleSave();
    	}
    }
    private boolean isBookmarkable() {
        return (curNavAction instanceof NavActionItem);
    }
    private boolean canAdd() {
        return (isBookmarkable() &&(table.getRowCount() < BookmarkGroup.MAX_BOOKMARKITEMS));
    }

    private void toggleSave() {
        if (Routines.have(eText.getText()) && canAdd()) {
            boolean selected = table.getSelectedRow() != -1;
            getAction(SAVEBM_ACTION).setEnabled(true);
            getAction(RENAMEBM_ACTION).setEnabled(eText.isEnabled() && selected);
        } else {
        	getAction(SAVEBM_ACTION).setEnabled(false);
        	getAction(RENAMEBM_ACTION).setEnabled(false);
        }
    }
    private void toggleLoad() {
        boolean selected = table.getSelectedRow() != -1;
        boolean bMultSelect = table.isMultipleSelection();
        boolean bHasText = eText.isEnabled() && Routines.have(eText.getText());
        getAction(LOADBM_ACTION).setEnabled(selected && !bMultSelect);
        getAction(REMOVE_ACTION).setEnabled(selected);
        getAction(SEND_ACTION).setEnabled(selected && !bMultSelect);
        getAction(RENAMEBM_ACTION).setEnabled(selected && bHasText && !bMultSelect);

		getAction(SELECTALL_ACTION).setEnabled (table.getRowCount() > 0);
		getAction(SELECTINV_ACTION).setEnabled (table.getRowCount() > 0);
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.ui.EACMFrame#disableActions()
     */
    protected void disableActions(){
    	for (Enumeration<?> e = getActionTbl().elements(); e.hasMoreElements();){
    		EACMAction action = (EACMAction)e.nextElement();
    		//disable all while worker is running
    		//if (BREAK_ACTION.equals(action.getActionKey())){
    			//continue; // dont disable break
    		//}
    		if(action.getActionKey().equals(CLOSETAB_ACTION)){
    			continue;
    		}
    		stateTbl.put(action.getActionKey(), new Boolean(action.isEnabled()));
    		action.setEnabled(false);
    	}

    	eText.setEnabled(false);
    	table.setEnabled(false);
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.ui.EACMFrame#enableActions()
     */
    protected void enableActions(){
    	if (bGroup==null){ // something happened, stop
    		getAction(REFRESH_ACTION).setEnabled(true);
    	}else{
    		for (Enumeration<String> e = stateTbl.keys(); e.hasMoreElements();){
    			String key = (String)e.nextElement();
    			EACMAction action = getAction(key);
    			if (action !=null){
    				action.setEnabled((Boolean)stateTbl.get(key));
    			}
    		}
    		table.setEnabled(true);
    		eText.setEnabled(true);
    		getAction(REFRESH_ACTION).setEnabled(true);
    		toggleSave();
    		toggleLoad();

    		if (newActionItem!=null){ // request came in while working on something else
    			setActionItem(newActionItem,newHistoryActions);
    			newActionItem = null;
    			newHistoryActions = null;
    		}
    	}
    }
    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    public void valueChanged(ListSelectionEvent _lse) {
        if (!_lse.getValueIsAdjusting()) {
        	toggleLoad();
        }
    }

    /**
     * setActionItem
     * @param _eanAction
     */
    public void setActionItem(EANActionItem _eanAction, EANActionItem[] history) {
    	if (eText.isEnabled()){
    		if(bGroup==null){
    			newActionItem=_eanAction; // table not loaded yet
    			newHistoryActions = history;
    			if (!(newActionItem instanceof NavActionItem)){ // tell user not bookmarkable before bookmarks load
    				//msg12004.0 = Unable to bookmark current location.
    				eText.setText(Utils.getResource("msg12004.0"));
    			}
    			getAction(REFRESH_ACTION).actionPerformed(null);
    		}else{
    			curNavAction = _eanAction;
    	    	navHistoryActions = history;
    			setAction();
    		}
    	}else{ // worker is busy
    		newActionItem=_eanAction;
    		newHistoryActions = history;
    	}
    }
    private void setAction(){
		if (curNavAction != null) {
			if (isBookmarkable()) {
				String str = curNavAction.getLongDescription();
				eText.setText(str);
				eText.setEnabled(true);
			} else {
				//msg12004.0 = Unable to bookmark current location.
				eText.setText(Utils.getResource("msg12004.0"));
				eText.setEnabled(false);
			}
			toggleSave();
		}
    }

    /**
     * create all of the actions, they are shared between toolbar and menu
     */
    private void createActions() {
    	addAction(new SaveAction());

    	addAction(new SendAction());

        addAction(new RemoveAction());
        addAction(new FindRepAction());
        addAction(new FindNextAction());

        addAction(new RenameAction());

        addAction(new RefreshAction());

        addAction(new com.ibm.eacm.actions.SortAction(this,table));

        addAction(new com.ibm.eacm.actions.FilterAction(this,table));

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
    	createEditMenu();
    	createTableMenu();
    	createActionMenu();

    	setJMenuBar(menubar);
    }

    private void createActionMenu(){
        JMenu mnuAction = new JMenu(Utils.getResource(ACTIONS_MENU));
        mnuAction.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));

        addLocalActionMenuItem(mnuAction, SAVEBM_ACTION);
        addLocalActionMenuItem(mnuAction, LOADBM_ACTION);
        addLocalActionMenuItem(mnuAction, SEND_ACTION);
        addLocalActionMenuItem(mnuAction, RENAMEBM_ACTION);
        addLocalActionMenuItem(mnuAction, REMOVE_ACTION);
        mnuAction.addSeparator();
        addLocalActionMenuItem(mnuAction, REFRESH_ACTION);
        addGlobalActionMenuItem(mnuAction, BREAK_ACTION);

    	menubar.add(mnuAction);
    }

    private void createTableMenu(){
        JMenu mnuTbl = new JMenu(Utils.getResource(TABLE_MENU));
        mnuTbl.setMnemonic(Utils.getMnemonic(TABLE_MENU));

        addLocalActionMenuItem(mnuTbl, SORT_ACTION);

       // menubar.addMenu("Table", "fltr", this, KeyEvent.VK_F8, 0, true);
        addLocalActionMenuItem(mnuTbl, FILTER_ACTION);

		menubar.add(mnuTbl);
    }
    private void createFileMenu() {
        JMenu mnuFile = new JMenu(Utils.getResource(FILE_MENU));
        mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));

    	JMenuItem mi = mnuFile.add(closeAction);
    	mi.setVerifyInputWhenFocusTarget(false);

		menubar.add(mnuFile);
    }
    private void createEditMenu() {
        JMenu mnuEdit = new JMenu(Utils.getResource(EDIT_MENU));
        mnuEdit.setMnemonic(Utils.getMnemonic(EDIT_MENU));

    	addLocalActionMenuItem(mnuEdit, FINDREP_ACTION);
    	addLocalActionMenuItem(mnuEdit, FINDNEXT_ACTION);

    	mnuEdit.addSeparator();
     	addLocalActionMenuItem(mnuEdit, SELECTALL_ACTION);
       	addLocalActionMenuItem(mnuEdit, SELECTINV_ACTION);

		menubar.add(mnuEdit);
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
    	tBar.add(getAction(SAVEBM_ACTION));
    	tBar.add(getAction(LOADBM_ACTION));
    	tBar.add(getAction(SEND_ACTION));
    	tBar.add(getAction(RENAMEBM_ACTION));
    	tBar.add(getAction(REFRESH_ACTION));
    	tBar.add(getAction(REMOVE_ACTION));
    	tBar.add(getAction(FILTER_ACTION));
    	tBar.addSeparator();

    	tBar.add(EACM.getEACM().getGlobalAction(BREAK_ACTION));

    	adjustToolBarButtons();
    }

    /**
     * release memory
     */
    public void dereference() {
    	super.dereference();

    	if (bGroup !=null){
    		bGroup.dereference();
    		bGroup = null;
    	}

    	table.getSelectionModel().removeListSelectionListener(this);
    	table.dereference();
    	table = null;

    	pnlMain.removeAll();
    	pnlMain.setUI(null);
    	pnlMain = null;

    	jsp.removeAll();
    	jsp.setUI(null);
    	jsp = null;

    	eText.removePropertyChangeListener(this);
        eText.dereference();
        eText = null;

        curNavAction = null;
        navHistoryActions = null;
        newActionItem = null;
        newHistoryActions = null;

        stateTbl.clear();
        stateTbl = null;

        bookMarkPanel.removeAll();
        bookMarkPanel.setUI(null);
        bookMarkPanel = null;

        lblText.setLabelFor(null);
        lblText.removeAll();
        lblText.setUI(null);
        lblText = null;

        lblFilter.removeAll();
        lblFilter.setUI(null);
        lblFilter = null;
    }

    //=============================================================================
    //actions and workers
    //=============================================================================

    // send bookmark
    private ProfSetWorker psWorker = null;
    private SendWorker sendWorker = null;

    private class SendAction extends EACMAction
    {
		private static final long serialVersionUID = 1L;
		SendAction() {
            super(SEND_ACTION,KeyEvent.VK_S, Event.CTRL_MASK + Event.SHIFT_MASK);
            putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("send.gif"));
		}

		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();

			psWorker = new ProfSetWorker();
			RMIMgr.getRmiMgr().execute(psWorker);
		}
    }

    private class ProfSetWorker extends DBSwingWorker<ProfileSet, Void> {
    	@Override
    	public ProfileSet doInBackground() {
    		ProfileSet pSet = null;
    		try{
    			// get profiles that match this profile
                pSet = getBuddies();
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
    			RMIMgr.getRmiMgr().complete(psWorker); // done accessing pdh, allow another to run
             	psWorker = null;  // nothing to cancel now
    		}
			return pSet;
    	}

        @Override
        public void done() {
        	//this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			ProfileSet pSet = get();
        			if (pSet != null) {
        				Object values[] = com.ibm.eacm.ui.UI.showList(BookmarkFrame.this,
        						Utils.getResource("bookmark.send.to"), convertProfSet(pSet));
        				if(!isCancelled() && values!=null && values.length>0){
        					sendWorker = new SendWorker(values);
        					RMIMgr.getRmiMgr().execute(sendWorker);
        				}
        			} else {
        				com.ibm.eacm.ui.UI.showErrorMessage(BookmarkFrame.this,Utils.getResource("msg13000.0"));
        			}
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"getting profileset");
            }finally{
            	if(sendWorker==null){
            		enableActionsAndRestore();
            	}
            }
        }
    	public void notExecuted(){
    		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
    		enableActionsAndRestore();
    		psWorker = null;
    	}
    }
    private ProfileSet getBuddies() {
    	BookmarkItem book = table.getBookmarkItem();
        try {
            return book.getBuddies(ro());
        } catch (Exception _ex) {
        	if(RMIMgr.shouldTryReconnect(_ex) && // try to reconnect
        			RMIMgr.getRmiMgr().reconnectMain()){
        		try{
        			return book.getBuddies(ro());
        		}catch(Exception e){
        			com.ibm.eacm.ui.UI.showException(this,e, "mw.err-title");
        		}
        	}else{
        		com.ibm.eacm.ui.UI.showException(this,_ex, "mw.err-title");
        	}
        }
        return null;
    }
    private class SendWorker extends DBSwingWorker<Void, Void> {
    	private Profile[] prof = null;
    	SendWorker(Object obj[]){
			prof = new Profile[obj.length];
			for (int i=0;i<obj.length;++i) {
				prof[i] = ((ProfileDisplay)obj[i]).getProfile();
			}
    	}
    	@Override
    	public Void doInBackground() {
    		try{
    			// send the bookmarks
    			sendBookmark(prof);
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
    			RMIMgr.getRmiMgr().complete(sendWorker); // done accessing pdh, allow another to run
    			sendWorker = null;  // nothing to cancel now
    			enableActionsAndRestore();
            	prof = null;
    		}
			return null;
    	}

    	public void notExecuted(){
    		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
    		enableActionsAndRestore();
    		sendWorker = null;
        	prof = null;
    	}
    }

    /**
     * sendBookmark
     *
     * @param _prof
     */
    private void sendBookmark(Profile[] _prof) {
    	BookmarkItem book = table.getBookmarkItem();
        try {
            if (book != null) {
               book.send(ro(), _prof);
            }
        } catch (BookmarkSendException _be) {
        	showBSEMessage( _be);
        } catch (Exception _ex) {
        	if(RMIMgr.shouldTryReconnect(_ex) &&	// try to reconnect
        			RMIMgr.getRmiMgr().reconnectMain()) {
        		try{
        			book.send(ro(), _prof);
        		} catch (BookmarkSendException _bme) {
        			showBSEMessage(_bme);
        		}catch(Exception e){
        			com.ibm.eacm.ui.UI.showException(this,e, "mw.err-title");
        		}
        	}else{
        		com.ibm.eacm.ui.UI.showException(this,_ex, "mw.err-title");
        	}
        }
    }

    private void showBSEMessage(BookmarkSendException _bse) {
        if (_bse != null) {
        	StringBuffer sb = new StringBuffer();
            //bse_err = {0} for {1}
            for (int i = 0; i < _bse.getFailedProfileCount(); ++i) {
            	Exception ex = _bse.getProfileException(i);
            	Profile prof = _bse.getFailedProfile(i);
            	if (sb.length() > 0) {
            		sb.append("\n");
            	}
            	sb.append(Utils.getResource("bse_err",ex.toString(),prof.getOPName()));
            }
            com.ibm.eacm.ui.UI.showErrorMessage(this,sb.toString());
        }
    }
    /**
     * convert profileset into ProfileDisplay items for send choice list
     * @param pset
     * @return
     */
    private Object[] convertProfSet(ProfileSet pset){
    	Profile[] prof = pset.toArray();
    	Arrays.sort(prof,new ProfComparator(true));
    	java.util.Vector<ProfileDisplay> profVct = new java.util.Vector<ProfileDisplay>();
    	for (int i=0; i<prof.length; i++){
    		String opname = prof[i].getOPName();
    		if (!profVct.contains(opname) &&
    				opname.indexOf("@")!=-1){ // dont use application ids
    			profVct.add(new ProfileDisplay(prof[i]));
    		}
    	}
    	Object[] data = new Object[profVct.size()];
    	profVct.copyInto(data);
    	profVct.clear();
    	return data;
    }

    //================================================================
    //load bookmark
    private LoadWorker loadWorker = null;
    private class LoadAction extends EACMAction
    {
		private static final long serialVersionUID = 1L;
		LoadAction() {
            super(LOADBM_ACTION,KeyEvent.VK_L, Event.CTRL_MASK);
            putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("load.gif"));
		}

		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();
		    loadWorker = new LoadWorker();
			RMIMgr.getRmiMgr().execute(loadWorker);
		}
    }

    private class LoadWorker extends DBSwingWorker<EntityList, Void> {
    	@Override
    	public EntityList doInBackground() {
    		EntityList el = null;
    		try{
    			EACM.getEACM().disableActionsAndWait();
    			// get list to load
                el = replay();
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(loadWorker);
             	loadWorker = null;
            }
			return el;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
            try {
            	if(!isCancelled()){
            		EntityList el = get();
            		if (el != null) {
            			BookmarkItem item = table.getBookmarkItem();
            			NavController.loadBookmark(el, item);
            			closeAction.actionPerformed(null);
            		}
            	}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"getting entitylist");
            }finally{
            	EACM.getEACM().enableActionsAndRestore();
            	enableActionsAndRestore();
            }
        }
       	public void notExecuted(){
       		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
       		enableActionsAndRestore();
        	loadWorker = null;
    	}
    }
    private EntityList replay() {
        EntityList el = null;

    	BookmarkItem item = table.getBookmarkItem();
        if (item != null) {
            try {
                el = item.replay(ro(), EACM.getEACM().getActiveProfile());
            } catch (Exception exc) {
            	if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
        			if (RMIMgr.getRmiMgr().reconnectMain()) {
        				try{
        					el = item.replay(ro(), EACM.getEACM().getActiveProfile());
        				}catch(Exception e){
        					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
        						if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, e) == RETRY) {
        							el = replay();
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
        				if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, exc) == RETRY) {
        					el = replay();
        				}// else user decide to ignore or exit
        			}else{
        				com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
        			}
        		}
            }
        }
        return el;
    }
    //================================================================
    // refresh action
    private RefreshWorker refreshWorker = null;
    private class RefreshAction extends EACMAction
    {
		private static final long serialVersionUID = 1L;
		RefreshAction() {
            super(REFRESH_ACTION,KeyEvent.VK_F5,0);
            putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("refresh.gif"));
		}

		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();
			refreshWorker = new RefreshWorker();
			RMIMgr.getRmiMgr().execute(refreshWorker);
		}
    }

    private class RefreshWorker extends DBSwingWorker<BookmarkGroup, Void> {
    	@Override
    	public BookmarkGroup doInBackground() {
    		BookmarkGroup bg = null;
    		try{
    			// get BookmarkGroup to load
    			bg = getBookmarkGroup();
    			if (bg != null) {
    				bg.setDupMode(BookmarkGroup.DUP_GEN_NEW_MODE);
    			}
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(refreshWorker);
            	refreshWorker = null;
    		}
			return bg;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			BookmarkGroup oldGrp = bGroup;
        			bGroup = get();

        			table.updateModel(new BookmarkGroupTable(bGroup, "test"),bGroup.getProfile());
        			if(oldGrp==null){
        				// this is the first time
        				useSavedFilterGroup();
        			}
        			if (oldGrp!=null && bGroup!=oldGrp){
        				// must deref after model is updated
        				oldGrp.dereference();
        				oldGrp = null;
        			}
        			if (table.getRowCount()>0){
    					table.setRowSelectionInterval(0, 0);
    					table.scrollToRow(0);
    				}
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"getting BookmarkGroup");
            }catch(Exception ex){ 
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	enableActionsAndRestore();
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
     		enableActionsAndRestore();
        	refreshWorker = null;
    	}
    }

    private BookmarkGroup getBookmarkGroup() {
    	BookmarkGroup bg = null;
    	try {
    		bg = ro().getBookmarkGroup(EACM.getEACM().getActiveProfile());
    	} catch (Exception exc) {
    		if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
    			if (RMIMgr.getRmiMgr().reconnectMain()) {
    				try{
    					bg = ro().getBookmarkGroup(EACM.getEACM().getActiveProfile());
    				}catch(Exception e){
    					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
    						if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, e) == RETRY) {
    							bg = getBookmarkGroup();
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
    				if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, exc) == RETRY) {
    					bg = getBookmarkGroup();
    				}// else user decide to ignore or exit
    			}else{
    				com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
    			}
    		}
    	}
    	return bg;
    }
    //================================================================
    // save new bookmark
    private SaveWorker saveWorker=null;
    private class SaveAction extends EACMAction
    {
		private static final long serialVersionUID = 1L;
		SaveAction() {
            super(SAVEBM_ACTION,KeyEvent.VK_S, Event.CTRL_MASK);
            putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("save.gif"));
   		}

		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();
			saveWorker = new SaveWorker();
			RMIMgr.getRmiMgr().execute(saveWorker);
		}
    }
    private class SaveWorker extends HeavyWorker<String, Void> {
    	@Override
    	public String doInBackground() {
    		String newitemkey=null;
    		try{
    			// profile is shared with entitylist, entitylist will be dereferenced and profile will be nulled out
     			curNavAction = new NavActionItem((NavActionItem)curNavAction); // prevent null ptr in action profile after list is deref

    			// get a new item, it was appended to the local table
    			BookmarkItem item = BookmarkTable.addBookmarkItem(BookmarkFrame.this,table.getBookmarkTable(),
    					curNavAction, navHistoryActions, eText.getText());
    			if (item!=null){
    				//if text doesnt match, this is a duplicate and is not persistant until user renames it
    				if (item.getUserDescription().equals(eText.getText())){
    					// put it into the pdh
    					boolean ok = saveBookmark(table.getBookmarkTable(),item);
    					if (ok){
        					newitemkey = item.getKey();
        				}
    				}else {
    					newitemkey = item.getKey();
    				}
    			}
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(saveWorker);
            	saveWorker = null;
    		}
			return newitemkey;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			String key = get();
        			if (key !=null){
         				// notify the listeners that the table has been modified
        				table.updatedTbl();
        				eText.setText("");
        				int r = table.getBookmarkTable().getRowIndex(key);
        				table.setRowSelectionInterval(r, r);
        				table.scrollToRow(r);
        			}
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"adding Bookmark");
            }finally{
            	enableActionsAndRestore();
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
     		enableActionsAndRestore();
        	saveWorker = null;
    	}
    }
    /**
     * put the new bookmarkitem into the pdh
     * @param _bgt
     * @param _item
     * @return
     */
    private boolean saveBookmark(BookmarkGroupTable _bgt, BookmarkItem _item) {
    	boolean results = false;
        try {
            _bgt.storeRow(ro(), _item.getKey());
            results = true;
        } catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
    			if (RMIMgr.getRmiMgr().reconnectMain()) {
    				try{
    					_bgt.storeRow(ro(), _item.getKey());
    				    results = true;
    				}catch(Exception e){
    					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
    						if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, e) == RETRY) {
    							results = saveBookmark(_bgt, _item);
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
    				if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, exc) == RETRY) {
    					results = saveBookmark(_bgt, _item);
    				}// else user decide to ignore or exit
    			}else{
    				com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
    			}
    		}
        }
        return results;
    }

    //================================================================
    //rename bookmark
    private RenameWorker renameWorker = null;
    private class RenameAction extends EACMAction   {
		private static final long serialVersionUID = 1L;
		RenameAction() {
            super(RENAMEBM_ACTION,KeyEvent.VK_R, Event.CTRL_MASK);
            putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("renameMark.gif"));
		}

		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();
			renameWorker = new RenameWorker();
			RMIMgr.getRmiMgr().execute(renameWorker);
		}
    }

    private class RenameWorker extends DBSwingWorker<String, Void> {
    	@Override
    	public String doInBackground() {
    		String renamedkey=null;
    		try{
    			// get the action for the old bookmark
    			BookmarkItem olditem =table.getBookmarkItem();

    			EANActionItem oldaction=olditem.getActionItem();
    			if(olditem.isPersistent()){
    				oldaction = ro().getBookmarkedActionItem(olditem.getProfile(),
    						olditem.getActionItemKey(),olditem.getUserDescription());
    			}

    			// get a new item - it has been added to the local object only
    			BookmarkItem newitem = BookmarkTable.addBookmarkItem(BookmarkFrame.this,table.getBookmarkTable(),
    					oldaction, null, eText.getText());

    			if (newitem!=null){
    				// remove this row from the table in the pdh (and local copy too)
    				if (deleteBookmark(table.getBookmarkTable(),new String[]{olditem.getKey()})) {
    					// put new one into the pdh
    					boolean ok = saveBookmark(table.getBookmarkTable(),newitem);
    					if (!ok){
    						ok = saveBookmark(table.getBookmarkTable(),olditem); // put old one back
    						if (ok){
    							renamedkey = olditem.getKey();
    						}
    					}else{
    						renamedkey = newitem.getKey();
    					}
    				}
    			}
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(renameWorker);
            	renameWorker = null;
    		}

			return renamedkey;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
            try {
           		if(!isCancelled()){
           			String key = get();
        			if (key!=null){
        				// notify listeners that the table has been modified
        				table.updatedTbl();
        				eText.setText("");
        				int r = table.getBookmarkTable().getRowIndex(key);
        				table.setRowSelectionInterval(r, r);
        				table.scrollToRow(r);
        			}
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"renaming Bookmark");
            }finally{
            	enableActionsAndRestore();
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
     		enableActionsAndRestore();
        	renameWorker = null;
    	}
    }
    /**
     * delete this bookmarkitem(s) (key) from the bookmarktable in the pdh
     * @param _bgt
     * @param _keys
     * @return
     */
    private boolean deleteBookmark(BookmarkGroupTable _bgt, String[] _keys) {
    	boolean ok = false;
        try {
            _bgt.deleteRows(ro(), _keys);
            ok = true;
        } catch (Exception _ex) {
        	if(RMIMgr.shouldTryReconnect(_ex) &&	// try to reconnect
        			RMIMgr.getRmiMgr().reconnectMain()) {
        		try{
        			_bgt.deleteRows(ro(), _keys);
        			ok = true;
        		} catch(Exception e){
        			com.ibm.eacm.ui.UI.showException(this,e, "mw.err-title");
        		}
        	}else{	// reconnect failed
        		com.ibm.eacm.ui.UI.showException(this,_ex, "mw.err-title");
        	}
        }
        return ok;
    }

    //================================================================
    // remove bookmarks
    private RemoveWorker removeWorker = null;
    private class RemoveAction extends EACMAction
    {
		private static final long serialVersionUID = 1L;
		RemoveAction() {
            super(REMOVE_ACTION,KeyEvent.VK_DELETE, Event.CTRL_MASK);
            putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("waste.gif"));
		}

		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();
			// must allow paint to run before doing the actual delete
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					removeWorker = new RemoveWorker();
					RMIMgr.getRmiMgr().execute(removeWorker);
				}
			});
		}
    }

    private class RemoveWorker extends DBSwingWorker<int[], Void> {
    	@Override
    	public int[] doInBackground() {
    		int[] delrows = null;
    		try{
    			BookmarkItem[] items = table.getBookmarkItems();
    			String[] keys = new String[items.length];
    			for (int i = 0; i < items.length; ++i) {
    				keys[i] = items[i].getKey();
    			}

    			delrows = new int[items.length];
    			for (int i=0;i<items.length;++i) {
    				delrows[i] = table.getBookmarkTable().getRowIndex(items[i].getKey());
    			}

    			FilterGroup fg = table.getFilterGroup();
    			table.setFilterGroup(null); // prevent tablemodel from using the filter
    			// remove these rows from the table in the pdh (and local copy too)
    			if (!deleteBookmark(table.getBookmarkTable(),keys)) {
    				delrows = null;
    			}

    			table.setFilterGroup(fg);
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(removeWorker);
            	removeWorker = null;
    		}

			return delrows;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
            try {
           		if(!isCancelled()){
        			int delrows[] = get();
        			if (delrows!=null){
        				table.fireRowDeleted(delrows);

        				// redraw the tbl
        				table.refreshTable();
        				if (table.getRowCount()>0){
        					table.setRowSelectionInterval(0, 0);
        					table.scrollToRow(0);
        				}
        			}
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"removing Bookmark");
            }finally{
            	enableActionsAndRestore();
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
     		enableActionsAndRestore();
        	removeWorker = null;
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
			if (psWorker!=null){
				RMIMgr.getRmiMgr().cancelWorker(psWorker,true);
			}
			if (sendWorker!=null){
				RMIMgr.getRmiMgr().cancelWorker(sendWorker,true);
			}
			if (loadWorker!=null){
				RMIMgr.getRmiMgr().cancelWorker(loadWorker,true);
			}
			if (refreshWorker!=null){
				RMIMgr.getRmiMgr().cancelWorker(refreshWorker,true);
			}
			if (saveWorker!=null){
				RMIMgr.getRmiMgr().cancelWorker(saveWorker,true);
			}
			if (renameWorker!=null){
				RMIMgr.getRmiMgr().cancelWorker(renameWorker,true);
			}
			if (removeWorker!=null){
				RMIMgr.getRmiMgr().cancelWorker(removeWorker,true);
			}
 
			super.actionPerformed(e);
		}
    }

}