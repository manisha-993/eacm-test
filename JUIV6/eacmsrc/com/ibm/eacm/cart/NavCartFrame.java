//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.cart;


import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.transactions.OPICMList;
import java.awt.event.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

import javax.swing.event.*;
import javax.swing.table.JTableHeader;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.*;

import COM.ibm.opicmpdh.middleware.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.*;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.preference.PrefMgr;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.EntityGroupTable;
import com.ibm.eacm.tabs.CloseTabComponent;
import com.ibm.eacm.ui.EACMFrame;
import com.ibm.eacm.ui.EntityListTabbedPane;
import com.ibm.eacm.ui.UI;


/**
 * This is the main cart window.
 * one of these per profile, it can be shared across multiple tabs
 *
 * @author Wendy Stimpson
 */
//$Log: NavCartFrame.java,v $
//Revision 1.5  2015/03/13 18:30:55  stimpsow
//allow alt+f4 to get to the frame, toolbar was grabbing it
//
//Revision 1.4  2014/10/20 19:56:08  wendy
//Add worker id to timing log output
//
//Revision 1.3  2013/10/25 14:58:50  wendy
//Swap yes and no in close dialog - so default is 'NO' IN4456532 CR TBD
//
//Revision 1.2  2013/09/09 20:31:46  wendy
//only enable linkall if data exists and !readonly
//
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class NavCartFrame extends EACMFrame implements ChangeListener,
ListSelectionListener,FindableComp {
	private static final long serialVersionUID = 1L;

	// use a single NavCartFrame per profile
	private static HashMap<String, NavCartFrame> cartFrameMap = new HashMap<String, NavCartFrame>();
	// keep count of navigates with the same profile, when all are closed, the frame will be disposed
	private static HashMap<String, Integer> cartFrameCntMap = new HashMap<String, Integer>();
	protected static final Logger logger;

	static {
		String loggername = com.ibm.eacm.cart.NavCartFrame.class.getPackage().getName();
		logger = Logger.getLogger(loggername);
		logger.setLevel(PrefMgr.getLoggerLevel(loggername, Level.INFO));
	}

	private JPanel mainpnl = new JPanel(new BorderLayout());
	private JPanel pnlMain = new JPanel(null);

	private EntityListTabbedPane tabbedPane = null;
	private CartList cList = null;
	private JButton removeCartButton=null;
	private JButton add2CartButton=null;
	private JLabel lblFilter = new JLabel(Utils.getImageIcon("fltr.gif"));

	/**
	 * a navigate has closed, decrease the count and dispose of the frame if cnt==0
	 * @param cart
	 */
	private static void closeCart(NavCartFrame cartFrame){
		synchronized(cartFrameMap){
			Integer cnt = cartFrameCntMap.get(cartFrame.cList.getCartKey());
			cnt--;
			if (cnt==0){
				cartFrameMap.remove(cartFrame.cList.getCartKey());
				cartFrameCntMap.remove(cartFrame.cList.getCartKey());
				cartFrame.dereferenceMe();
			}else{
				cartFrameCntMap.put(cartFrame.cList.getCartKey(), new Integer(cnt));
			}
		}
	}

	/**
	 * reuse same NavCartFrame for a particular profile and cartlist
	 * assumption is activeprofile is updated before a cart is requested!!!
	 * this must be changed to keeping the profile on the tab or multi-threading will not work
	 *
	 * @param cart
	 * @return
	 */
	public static NavCartFrame getNavCart(CartList cart){
		NavCartFrame cartFrame = null;
		synchronized(cartFrameMap){
			String key = cart.getCartKey();
			cartFrame = cartFrameMap.get(key);
			if (cartFrame==null){
				cartFrame = new NavCartFrame(cart);
				cartFrameMap.put(cartFrame.cList.getCartKey(),cartFrame);
				cartFrameCntMap.put(cartFrame.cList.getCartKey(), new Integer(1));
			}else{
				Integer cnt = cartFrameCntMap.get(key);
				cartFrameCntMap.put(key, new Integer(++cnt));
			}
		}
		return cartFrame;
	}

	/**
	 * constructor
	 */
	private NavCartFrame(CartList cart){
		super("cart.panel",cart.getEntityList().getProfile().toString());
		closeAction = new CloseAction(this);
		setIconImage(Utils.getImage("histLoad.gif"));
		cList = cart;
		cList.setCartFrame(this);
		init();
		finishSetup(EACM.getEACM());
		setName("Navigate Cart");

		addComponentListener(new ComponentAdapter(){
			//Ensure the tabbed pane gets focus
			public void componentShown(ComponentEvent ce) {
				tabbedPane.requestFocusInWindow();
			}
		});
		// enable/disable actions based on current content
		stateChanged(null);
		setResizable(true);
	}

	/**
	 * initialize
	 */
	private void init() {
		createActions();
		createMenuBar();
		createToolbar();

		createPopupMenu();

		Dimension d = new Dimension(500,600);
		setPreferredSize(d);
		setSize(d);
		setMinimumSize(d);

		//display the cart entitylist in a tabbedpane
		tabbedPane = new EntityListTabbedPane(new MouseAdapter() {
			public void mouseReleased(MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					popup.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		},this,getAction(REMOVETAB_ACTION));
		tabbedPane.load(cList.getEntityList());
		if(tabbedPane.getTabCount()>0){
			tabbedPane.setSelectedIndex(0);
		}
		tabbedPane.addChangeListener(this);

		lblFilter.setEnabled(false);
		lblFilter.setToolTipText(Utils.getToolTip("cartFilterLbl"));
		lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilter.setVerticalAlignment(SwingConstants.CENTER);

		GroupLayout layout = new GroupLayout(pnlMain);
		pnlMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();

		leftToRight.addComponent(lblFilter, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);// this centers it
		leftToRight.addComponent(tabbedPane);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(lblFilter);
		topToBottom.addComponent(tabbedPane);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);

		mainpnl.add(tBar,BorderLayout.NORTH);
		mainpnl.add(pnlMain,BorderLayout.CENTER);

		getContentPane().add(mainpnl);
	}
	/**
	 * enable label
	 * @param filter
	 */
	public void setFilter(boolean filter) {
		lblFilter.setEnabled(filter);
	}

	public void setVisible(boolean b) {
		if(b){
			// select something when frame opens if it has any data
			if(tabbedPane.getTabCount()>0){
				EntityGroupTable egt = tabbedPane.getTable(0);
				if (egt.getRowCount()>0 && egt.getSelectedRow()==-1){
					egt.setColumnSelectionInterval(0, 0);
					egt.setRowSelectionInterval(0, 0);
				}
			}
		}
		super.setVisible(b);
	}
	protected void fixTableHeaderCursorBug(Cursor cursor){
		//tableheaders do not consistently display correct cursor
		for (int i=0; i< tabbedPane.getTabCount(); i++){
			if(tabbedPane.getTable(i)!=null){ //do all tables
				JTableHeader header = tabbedPane.getTable(i).getTableHeader();
				if (header != null) {
					header.setCursor(cursor);
				}
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.FindableComp#getFindable()
	 */
	public Findable getFindable() {
		return tabbedPane.getTable();
	}
	/**
	 * enable remove selected action based on current selection
	 * listener is added to each tab in the tabbedpane when the tab is created
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent lse) {
		if (lse==null || !lse.getValueIsAdjusting()) {
			getAction(REMOVESELCART_ACTION).setEnabled(tabbedPane.hasSelection());

			boolean hasTabs = tabbedPane.getTabCount()!=0;
			getAction(FILTER_ACTION).setEnabled(hasTabs);
			getAction(SORT_ACTION).setEnabled(hasTabs);   // really care about data, but this may be enough
		}
	}
	/**
	 * create all of the actions, they are shared between toolbar and menu
	 */
	private void createActions() {
		//ADD2CART_ACTION
		addAction(new AddSelected2CartAction(cList));
		//ADDALL2CART_ACTION
		addAction(new AddAll2CartAction(cList));
		//REMOVESELCART_ACTION
		addAction(new RemoveSelCartAction());
		//RESETCART_ACTION
		addAction(new ResetCartAction(cList,this));
		//REMOVETAB_ACTION
		addAction(new RemoveTabAction());

		//ADD2CART_BUTTON
		addAction(new AddAction());
		//REMOVECART_BUTTON
		addAction(new RemoveAction());

		addAction(new LinkAllAction());
		addAction(new PasteAction());

		addAction(new com.ibm.eacm.actions.SortAction(this,null));

		//SELECTALL_ACTION
		addAction(new SelectAllAction(null));

		//SELECTINV_ACTION
		addAction(new SelectInvertAction((BaseTable)null));

		//FILTER_ACTION
		addAction(new com.ibm.eacm.actions.FilterAction(this,null));
		addAction(new FindRepAction());
		addAction(new FindNextAction());
	}
	/**
	 * createMenuBar
	 */
	private void createMenuBar() {
		menubar = new JMenuBar();

		createFileMenu();
		createTableMenu();
		createActionMenu();

		setJMenuBar(menubar);
	}

	/**
	 * createToolbar
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

		tBar.add(getAction(CUTPASTE_ACTION));
		add2CartButton = tBar.add(getAction(ADD2CART_BUTTON));
		removeCartButton = tBar.add(getAction(REMOVECART_BUTTON));

		tBar.addSeparator();
		tBar.add(EACM.getEACM().getGlobalAction(BREAK_ACTION));

		adjustToolBarButtons();
	}

	private void createFileMenu() {
		JMenu mnuFile = new JMenu(Utils.getResource(FILE_MENU));
		mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));
		mnuFile.add(closeAction);
		menubar.add(mnuFile);
	}
	private void createActionMenu(){
		JMenu mnuAction = new JMenu(Utils.getResource(ACTIONS_MENU));
		mnuAction.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));

		addLocalActionMenuItem(mnuAction, ADD2CART_ACTION);
		addLocalActionMenuItem(mnuAction, ADDALL2CART_ACTION);
		mnuAction.addSeparator();
		addLocalActionMenuItem(mnuAction, REMOVESELCART_ACTION);
		addLocalActionMenuItem(mnuAction, REMOVETAB_ACTION);
		addLocalActionMenuItem(mnuAction, RESETCART_ACTION);
		mnuAction.addSeparator();
		addLocalActionMenuItem(mnuAction, LINKALL_ACTION);
		mnuAction.addSeparator();
		addGlobalActionMenuItem(mnuAction, BREAK_ACTION);

		menubar.add(mnuAction);
	}

	private void createTableMenu(){
		JMenu mnuTbl = new JMenu(Utils.getResource(TABLE_MENU));
		mnuTbl.setMnemonic(Utils.getMnemonic(TABLE_MENU));

		addLocalActionMenuItem(mnuTbl, FINDREP_ACTION);
		addLocalActionMenuItem(mnuTbl, FINDNEXT_ACTION);

		addLocalActionMenuItem(mnuTbl, FILTER_ACTION);

		addLocalActionMenuItem(mnuTbl, SORT_ACTION);

		mnuTbl.addSeparator();
		addLocalActionMenuItem(mnuTbl, SELECTALL_ACTION);
		addLocalActionMenuItem(mnuTbl, SELECTINV_ACTION);

		menubar.add(mnuTbl);
	}


	/**
	 * the tab using the cart is closing or this frame is closing, remove its usage of the cart
	 */
	public void dereference() {
		closeCart(this);
	}

	/**
	 * only call this when all navigates with the same profile are closed
	 */
	private void dereferenceMe() {
		cList.setCartFrame(null);

		dispose();

		add2CartButton.setAction(null);
		add2CartButton=null;
		removeCartButton.setAction(null);
		removeCartButton=null;

		mainpnl.removeAll();
		mainpnl.setUI(null);
		mainpnl = null;

		pnlMain.removeAll();
		pnlMain.setUI(null);
		pnlMain = null;

		ComponentListener[] l = getComponentListeners();
		for (int i = 0; i < l.length; ++i) {
			removeComponentListener(l[i]);
		}

		tabbedPane.removeChangeListener(this);
		tabbedPane.dereference();
		tabbedPane = null;

		cList = null;

		lblFilter.removeAll();
		lblFilter.setUI(null);
		lblFilter = null;

		super.dereference();
	}

	private void removePopup(JPopupMenu jpm){
		for (int ii=0; ii<jpm.getComponentCount(); ii++) {
			Component comp = jpm.getComponent(ii);
			if (comp instanceof JMenuItem) {// separators are null
				EACM.closeMenuItem((JMenuItem)comp);
			}
		}
		jpm.setUI(null);
		jpm.removeAll();
	}

	/**
	 * called when user selects a tab or tab is added or removed
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce) {
		EntityGroup selGrp = tabbedPane.getEntityGroup(); // gets selected tab
		if (selGrp != null) {
			getAction(CUTPASTE_ACTION).setEnabled(selGrp == cList.getCutGroup());
		} else {
			getAction(CUTPASTE_ACTION).setEnabled(false);
		}

		boolean hasTabs = tabbedPane.getTabCount()!=0;

		getAction(REMOVESELCART_ACTION).setEnabled(tabbedPane.hasSelection());
		getAction(REMOVETAB_ACTION).setEnabled(hasTabs);

		// these depend on current tab
		setFilter(hasTabs && tabbedPane.getTable().isFiltered());

		if (hasTabs){
			//SELECTALL_ACTION
			EACMAction act = getAction(SELECTALL_ACTION);
			BaseTable tbl = tabbedPane.getTable();
			if (act!=null){
				((SelectAllAction)act).setTable(tbl);
			}
			//SELECTINV_ACTION
			act = getAction(SELECTINV_ACTION);
			if (act!=null){
				((SelectInvertAction)act).setTable(tbl);
			}
			//FILTER_ACTION
			act = getAction(FILTER_ACTION);
			if (act!=null){
				((FilterAction)act).setFilterable(tbl);
				act.setEnabled(hasTabs);
			}
			//SORT_ACTION
			act = getAction(SORT_ACTION);
			if (act!=null){
				((SortAction)act).setSortable(tbl);
				act.setEnabled(hasTabs);
			}
			//LINKALL_ACTION
			act = getAction(LINKALL_ACTION);
			if (act!=null){
				act.setEnabled(hasTabs);
			}
		}else{
			//SELECTALL_ACTION
			getAction(SELECTALL_ACTION).setEnabled(false);
			getAction(SELECTINV_ACTION).setEnabled(false);
			getAction(FILTER_ACTION).setEnabled(false);
			getAction(SORT_ACTION).setEnabled(false);
			getAction(LINKALL_ACTION).setEnabled(false);
		}

		cList.firePropertyChange(RESETCART_ACTION,!hasTabs,hasTabs); // update any resetcart action
	}

	/**
	 * remove items from the cart that user has deleted in the ui
	 * @param eg
	 * @param ei
	 */
	protected void removeDeletedItems(EntityGroup eg, EntityItem[] ei) {
		//find the table for this group, select the items, then remove them
		int cnt = tabbedPane.removeItems(eg, ei);
		if (cnt== 0) { // if all items were removed, remove the tab too
			finishRemove();
		}
	}
	private void finishRemove(){
		if (tabbedPane.getEntityGroup() == cList.getCutGroup()) {
			//tab will be removed by this method
			cList.clearCutGroup();
		} else {
			//msg11003 = Would you like to remove empty tab?
			int reply =  YES_BUTTON;
			if (this.isShowing()){ // removeselecteditems may have been called by cartlist and may not be showing
				reply = com.ibm.eacm.ui.UI.showConfirmYesNo(this, Utils.getResource("msg11003"));
			}
			if (reply == YES_BUTTON) {
				EntityGroup eg = tabbedPane.getEntityGroup();
				tabbedPane.removeTab(eg.getKey());
				cList.getEntityList().removeEntityGroup(eg);
			}
		}
	}

	protected void disableActions(){
		super.disableActions();

		cList.firePropertyChange(ADDALL2CART_ACTION,true, false); // disable any addall action
		cList.firePropertyChange(ADD2CART_ACTION,true, false); // disable any add selected action

		popup.setEnabled(false);
	}
	protected void refreshActions(){
		cList.firePropertyChange(ADDALL2CART_ACTION,false, true); // enable any addall action
		cList.firePropertyChange(ADD2CART_ACTION,false, true); // enable any add selected action

		popup.setEnabled(true);

		valueChanged(null);
		stateChanged(null);
	}

	protected void removeTab(String key){
		tabbedPane.removeTab(key);
	}
	protected void refreshTab(EntityGroup eg){
		tabbedPane.refreshTab(eg);
	}

	/**
	 * remove the tab for this entitygroup
	 * @param eg
	 */
	private void removeTab(EntityGroup eg) {
		if (eg != null) {
			if (eg == cList.getCutGroup()) {
				cList.clearCutGroup();
			}else{
				tabbedPane.removeTab(eg.getKey());
				cList.getEntityList().removeEntityGroup(eg);
			}
		}
	}

	//=======================================================================
	// Actions
	private class AddAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		private JPopupMenu popup = new JPopupMenu();
		AddAction() {
			super(ADD2CART_BUTTON);
			putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("plus.gif"));

			popup.add(getAction(ADD2CART_ACTION));
			popup.add(getAction(ADDALL2CART_ACTION));
		}

		public void actionPerformed(ActionEvent e) {
			//popup add2cart menu
			if (popup.isShowing()) {
				popup.setVisible(false);
			} else {
				popup.show(add2CartButton, 0, add2CartButton.getHeight());
			}
		}
		public void dereference(){
			super.dereference();
			removePopup(popup);
			popup = null;
		}
	}

	private class RemoveAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		private JPopupMenu popup = new JPopupMenu();
		RemoveAction() {
			super(REMOVECART_BUTTON);
			putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("minus.gif"));

			popup.add(getAction(REMOVESELCART_ACTION));
			popup.add(getAction(RESETCART_ACTION));
			popup.add(getAction(REMOVETAB_ACTION));
		}

		public void actionPerformed(ActionEvent e) {
			//popup add2cart menu
			if (popup.isShowing()) {
				popup.setVisible(false);
			} else {
				popup.show(removeCartButton, 0, removeCartButton.getHeight());
			}
		}
		public void dereference(){
			super.dereference();

			removePopup(popup);
			popup = null;
		}
	}

	private class RemoveSelCartAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		RemoveSelCartAction() {
			super(REMOVESELCART_ACTION);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();

			//popup or menu call this to remove selected items
			int cnt = tabbedPane.removeSelectedItems();
			if (cnt== 0) { // if all items were removed, remove the tab too
				finishRemove();
			}

			enableActionsAndRestore();
		}
	}

	private class RemoveTabAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		RemoveTabAction() {
			super(REMOVETAB_ACTION);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();

			EntityGroup egrp = tabbedPane.getEntityGroup();
			if(e.getSource() instanceof CloseTabComponent.TabButton){
				// user may have clicked close on a tab that was not selected
				Component tab = ((CloseTabComponent.TabButton)e.getSource()).getTabComponent();
				int i = tabbedPane.indexOfTabComponent(tab);
				int selected = tabbedPane.getSelectedIndex();
				if(i!=selected){
					egrp = tabbedPane.getEntityGroup(i);
				}
			}

			//popup or menu call this to remove tab from cart
			removeTab(egrp);

			enableActionsAndRestore();
		}
	}

	private class CloseAction extends CloseFrameAction
	{
		private static final long serialVersionUID = 1L;
		CloseAction(EACMFrame f) {
			super(f);
		}

		public void actionPerformed(ActionEvent e) {
			cList.cancelWorkers();

			if (cList.getEntityList().getEntityGroupCount() != 0) {
				if (always()) {
					cList.clearAll();
				} else if (prompt()) {
					if (showConfirm() == YES_BUTTON) {
						cList.clearAll();
					}
				}
			}

			//super.actionPerformed(e); dont dereference this here
			NavCartFrame.this.setVisible(false);
		}
	}
	private boolean always() {
		int iTest = Routines.getIntProperty("clrWFType.always");
		return BehaviorPref.getClearWFType() == iTest;
	}
	private boolean prompt() {
		int iTest = Routines.getIntProperty("clrWFType.prompt");
		return BehaviorPref.getClearWFType() == iTest;
	}

	private int showConfirm() {
		//String[] options = {"yes","no"};
		//String accdesc[] = { "yes-acc", "no-acc"};
		String[] options = {"no","yes"}; //Swap yes and no so default is NO IN4456532 CR TBD
		String accdesc[] = { "no-acc", "yes-acc"};

		JCheckBox chkDoNotDisplay = new JCheckBox(Utils.getResource("DNSAgain"));
		//DNSAgain = Do Not Show Again
		chkDoNotDisplay.setMnemonic(Utils.getMnemonic("DNSAgain"));
		chkDoNotDisplay.setSelected(false);

		//msg24016 = Reset Work Folder?
		Object obj[] = new Object[] {Utils.getResource("msg24016"),chkDoNotDisplay};
		int r= com.ibm.eacm.ui.UI.showAccessibleDialog(this,//Component parentComponent
				"msg-title", //title
				JOptionPane.QUESTION_MESSAGE, //messageType
				JOptionPane.YES_NO_OPTION, // optiontype
				"msg-acc", //accDialogDesc
				obj, //msgs
				options,  //button labels
				accdesc); //accButtonDescs
		
		// swapped yes and no IN4456532
		if (r==JOptionPane.YES_OPTION){
			r = JOptionPane.NO_OPTION;
		}else if (r==JOptionPane.NO_OPTION){
			r = JOptionPane.YES_OPTION;
		}

		if (chkDoNotDisplay.isSelected()) {
			if (r==JOptionPane.YES_OPTION){
				Preferences.userNodeForPackage(PrefMgr.class).putInt(BehaviorPref.PREF_WF_CLEAR_TYPE, Routines.getIntProperty("clrWFType.always"));
			}else if (r==JOptionPane.NO_OPTION){
				Preferences.userNodeForPackage(PrefMgr.class).putInt(BehaviorPref.PREF_WF_CLEAR_TYPE, Routines.getIntProperty("clrWFType.never"));
			}
		}

		options = null;
		accdesc = null;

		chkDoNotDisplay.removeAll();
		chkDoNotDisplay.setUI(null);
		chkDoNotDisplay = null;

		return r;
	}

	private EntityItem[] getAllChildren() {
		EntityItem[] childArray = null;
		EntityList eList = cList.getEntityList();
		Vector<EntityItem> v = new Vector<EntityItem>();
		for (int i=0;i<eList.getEntityGroupCount();++i) {
			EntityGroup eg = eList.getEntityGroup(i);
			for (int x=0;x<eg.getEntityItemCount();++x) {
				v.add(eg.getEntityItem(x));
			}
		}
		if (!v.isEmpty()) {
			childArray = new EntityItem[v.size()];
			v.copyInto(childArray);
		}
		return childArray;
	}
	/**
	 * linkAll
	 *
	 * @param eiParent
	 * @param eiChild
	 * @return
	 */
	private OPICMList linkAll(EntityItem[] eiParent, EntityItem[] eiChild) {
		long t1 =System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
		OPICMList oList = null;
		try {
			oList = EANUtility.linkAllEntityItems(ro(), null, cList.getEntityList().getProfile(), BehaviorPref.getLinkTypeKey(), eiParent, eiChild, EANUtility.LINK_DEFAULT,1, true);
		} catch (Exception ex) {
			if(RMIMgr.shouldTryReconnect(ex) && // try to reconnect
					RMIMgr.getRmiMgr().reconnectMain()){
				try {
					oList = EANUtility.linkAllEntityItems(ro(), null, cList.getEntityList().getProfile(), BehaviorPref.getLinkTypeKey(), eiParent, eiChild, EANUtility.LINK_DEFAULT,1, true);
				}catch (Exception x) {
					com.ibm.eacm.ui.UI.showException(this,x, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(this,ex, "mw.err-title");
			}
		}

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"linkall ended "+Utils.getDuration(t1));
		return oList;
	}
	/**
	 * RCQ00213801 - turn off link all capability on the JUI workfolder
	 * check to see if a link is allowed between these parents and childre
	 * @param eiParent
	 * @param eiChild
	 * @return
	 */
	private boolean canLinkAll(EntityItem[] eiParent, EntityItem[] eiChild){
		Hashtable<String,EntityGroup> parentTypeTbl = new Hashtable<String,EntityGroup>();
		Hashtable<String,EntityGroup> childTypeTbl = new Hashtable<String,EntityGroup>();
		for (int ii = 0; ii < eiParent.length; ii++) {
			EntityItem eiP = eiParent[ii];
			EntityGroup egP = eiP.getEntityGroup();
			logger.log(Level.FINE," parent["+ii+"] "+eiP.getKey());
			if (egP == null) {
				logger.log(Level.FINE,"EntityGroup is null for parent "+eiP.getKey());
				continue;
			}

			if (egP.isRelator() || egP.isAssoc()) {
				eiP = (EntityItem) eiP.getDownLink(0);
				egP = eiP.getEntityGroup();
			}
			if(!parentTypeTbl.containsKey(eiP.getEntityType())){
				parentTypeTbl.put(eiP.getEntityType(),eiP.getEntityGroup());
			}
		}
		for (int ii = 0; ii < eiChild.length; ii++) {
			EntityItem eiC = eiChild[ii];
			EntityGroup egC = eiC.getEntityGroup();
			if (egC == null) {
				logger.log(Level.FINE,"Entitygroup is null for eic "+eiC.getKey());
				continue;
			}

			if(!childTypeTbl.containsKey(eiC.getEntityType())){
				childTypeTbl.put(eiC.getEntityType(),eiC.getEntityGroup());
			}
		}
		boolean bLinkChild = false;
		for (Enumeration<EntityGroup> e = parentTypeTbl.elements(); e.hasMoreElements();){
			EntityGroup egP = e.nextElement();
			MetaLinkGroup mlgP = egP.getMetaLinkGroup();

			//link to children
			for (int p = 0; p < mlgP.getMetaLinkCount(MetaLinkGroup.DOWN); p++) {
				MetaLink ml = mlgP.getMetaLink(MetaLinkGroup.DOWN, p);
				String strEntityType = ml.getEntityType();
				String strEntity1Type = ml.getEntity1Type();
				String strEntity2Type = ml.getEntity2Type();

				if (!egP.getEntityType().equals(strEntity1Type)) {
					logger.log(Level.FINE,"Parent entity type " + egP.getEntityType() + " not equal metalink " + strEntity1Type);
					logger.log(Level.FINE,"metalink: " + ml.dump(false));
					continue;
				}

				int cnt=0;
				for (Enumeration<EntityGroup> ec = childTypeTbl.elements(); ec.hasMoreElements();){
					EntityGroup egC = ec.nextElement();

					logger.log(Level.FINE," child["+cnt+"] "+egC.getKey());
					cnt++;
					if (egC.getEntityType().equals(strEntity2Type)) {
						// check to see if this is a valid linkall
						if(!ml.isLinkAllAble()){
							logger.log(Level.INFO," "+strEntityType+" can not linkall "+ml.dump(false));
							UIManager.getLookAndFeel().provideErrorFeedback(null);
							//msg3011.2 = No {0} Link(s) can be created.
							com.ibm.eacm.ui.UI.showFYI(NavCartFrame.this, Utils.getResource("msg3011.2",strEntityType));
							childTypeTbl.clear();
							parentTypeTbl.clear();
							return false;
						}
						bLinkChild = true;
					} else { // look down the child entity to get the right match
						EntityItem eiC = egC.getEntityItem(0);
						for (int iz = 0; iz < eiC.getDownLinkCount(); iz++) {
							EntityItem eiCDown = (EntityItem) eiC.getDownLink(iz);
							if (eiCDown.getEntityType().equals(strEntity2Type)) {
								// check to see if this is a valid linkall
								if(!ml.isLinkAllAble()){ 
									logger.log(Level.INFO," "+strEntityType+" can not linkall "+ml.dump(false));
									UIManager.getLookAndFeel().provideErrorFeedback(null);
									//msg3011.2 = No {0} Link(s) can be created.
									com.ibm.eacm.ui.UI.showFYI(NavCartFrame.this, Utils.getResource("msg3011.2",strEntityType));
									childTypeTbl.clear();
									parentTypeTbl.clear();
									return false;
								}
								bLinkChild = true;
							}
						}
					}
				}
			}
		}

		if (!bLinkChild) {
			Hashtable<String,EntityGroup> childTypeTbl2 = new Hashtable<String,EntityGroup>();
			for (Enumeration<EntityGroup> ec = childTypeTbl.elements(); ec.hasMoreElements();){
				EntityGroup egC = ec.nextElement();
				if (egC.isRelator() || egC.isAssoc()) {
					EntityItem eic = egC.getEntityItem(0);
					eic = (EntityItem) eic.getDownLink(0);
					egC = eic.getEntityGroup();
				}
				if(!childTypeTbl2.containsKey(egC.getEntityType())){
					childTypeTbl2.put(egC.getEntityType(),egC);
				}
			}
			childTypeTbl.clear();
			for (Enumeration<EntityGroup> ec = childTypeTbl2.elements(); ec.hasMoreElements();){
				EntityGroup egC = ec.nextElement();
				MetaLinkGroup mlgP = egC.getMetaLinkGroup();

				for (int p = 0; p < mlgP.getMetaLinkCount(MetaLinkGroup.DOWN); p++) {
					MetaLink ml = mlgP.getMetaLink(MetaLinkGroup.DOWN, p);
					String strEntityType = ml.getEntityType();
					String strEntity1Type = ml.getEntity1Type();
					String strEntity2Type = ml.getEntity2Type();

					if (!egC.getEntityType().equals(strEntity1Type)) {
						logger.log(Level.FINE,"child entity type " + egC.getEntityType() + " not equal " + strEntity1Type);
						logger.log(Level.FINE,"ml " + ml.dump(false));
						continue;
					}

					for (Enumeration<EntityGroup> e = parentTypeTbl.elements(); e.hasMoreElements();){
						EntityGroup egP = e.nextElement();
						if (egP.getEntityType().equals(strEntity2Type)) {
							// check to see if this is a valid linkall
							if(!ml.isLinkAllAble()){
								logger.log(Level.INFO," "+strEntityType+" can not linkall "+ml.dump(false));
								UIManager.getLookAndFeel().provideErrorFeedback(null);
								//msg3011.2 = No {0} Link(s) can be created.
								com.ibm.eacm.ui.UI.showFYI(NavCartFrame.this, Utils.getResource("msg3011.2",strEntityType));
								childTypeTbl2.clear();
								parentTypeTbl.clear();
								return false;
							}
						} else { // look down the child entity to get the right match
							EntityItem eiC = egP.getEntityItem(0);;
							for (int iz = 0; iz < eiC.getDownLinkCount(); iz++) {
								EntityItem eiCDown = (EntityItem) eiC.getDownLink(iz);
								if (eiCDown.getEntityType().equals(strEntity2Type)) {
									// check to see if this is a valid linkall
									if(!ml.isLinkAllAble()){ 
										logger.log(Level.INFO," "+strEntityType+" can not linkall "+ml.dump(false));
										UIManager.getLookAndFeel().provideErrorFeedback(null);
										//msg3011.2 = No {0} Link(s) can be created.
										com.ibm.eacm.ui.UI.showFYI(NavCartFrame.this, Utils.getResource("msg3011.2",strEntityType));
										childTypeTbl2.clear();
										parentTypeTbl.clear();
										return false;
									}
								}
							}
						}
					}
				}
			}
			childTypeTbl2.clear();
		}
		childTypeTbl.clear();
		parentTypeTbl.clear();
		return true;
	}

	private void reportLinkAll(OPICMList oList, EntityItem[] eiParent, EntityItem[] eiChild) {
		if (oList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			Hashtable<String,EntityItem> entityTbl = new Hashtable<String,EntityItem>();
			for(int i=0;i<eiParent.length;i++){
				entityTbl.put(eiParent[i].getKey(), eiParent[i]);
			}
			for(int i=0;i<eiChild.length;i++){
				entityTbl.put(eiChild[i].getKey(), eiChild[i]);
			}
			for (int i=0;i<oList.size();++i) {
				Object o = oList.getAt(i);
				if (o instanceof ReturnRelatorKey) {
					getRelatorInfo(sb, (ReturnRelatorKey)o,entityTbl);
					sb.append(NEWLINE);
				}
			}
			//msg3011.0 = The link(s) have been created.
			UI.showLinkResults(this,sb.toString());
			entityTbl.clear();
		}
	}

	private void getRelatorInfo(StringBuffer sb, ReturnRelatorKey rrk, Hashtable<String,EntityItem> entityTbl) {
		EntityItem par = entityTbl.get(rrk.getEntity1Type() + rrk.getEntity1ID());
		EntityItem kid = entityTbl.get(rrk.getEntity2Type() + rrk.getEntity2ID());
		if (par != null && kid != null) {
			sb.append(par.getEntityGroup().getLongDescription()+": "+par.toString());
			//linked2 = linked to:
			sb.append(" " + Utils.getResource("linked2") + NEWLINE+" ");
			sb.append(kid.getEntityGroup().getLongDescription()+": "+kid.toString());
			logger.log(Level.INFO,"linked parent "+par.getKey()+" to child "+kid.getKey());
		}
	}

	private class LinkAllAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		LinkAllAction() {
			super(LINKALL_ACTION);
		}
		
		/* (non-Javadoc)
		 * @see com.ibm.eacm.actions.EACMAction#canEnable()
		 */
		protected boolean canEnable(){ 
			boolean ok = false;
			if(cList !=null && cList.getEntityList() !=null && !cList.getEntityList().getProfile().isReadOnly()){
				ok = true;
			}
			return ok; 
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			EntityItem[] eiParent = cList.getCartable().getSelectedEntityItems(true);
			if (eiParent == null) {
				//msg3011.1a = No Link(s) were created because nothing was selected in the Navigation.
				com.ibm.eacm.ui.UI.showFYI(NavCartFrame.this, Utils.getResource("msg3011.1a"));
				logger.log(Level.WARNING,"no possible links found.  no navigate parents selected");
			}
			EntityItem[] eiChild = getAllChildren();
			if (eiChild == null) {
				//msg3011.1 = No Link(s) were created.
				com.ibm.eacm.ui.UI.showFYI(NavCartFrame.this, Utils.getResource("msg3011.1"));
				logger.log(Level.WARNING,"no possible links found.   children null");
				return;
			}

			if(!canLinkAll(eiParent, eiChild)){
				return;
			}
			disableActionsAndWait(); //disable all other actions and also set wait cursor
			worker = new LinkAllWorker(eiParent,eiChild);
			RMIMgr.getRmiMgr().execute(worker);
		}
		private class LinkAllWorker extends DBSwingWorker<OPICMList, Void> {
			private EntityItem[] eiParent;
			private EntityItem[] eiChild;

			private long t11 = 0L;
			LinkAllWorker(EntityItem[] eip, EntityItem[] eic){
				eiParent=eip;
				eiChild = eic;
			}
			@Override
			public OPICMList doInBackground() {
				OPICMList list = null;
				try{
					t11 = System.currentTimeMillis();
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting linkall "+getIdStr());
					list = linkAll(eiParent,eiChild);
				}catch(Exception ex){ // prevent hang
					logger.log(Level.SEVERE, "",ex);
				}finally{
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" linkall bg ended "+Utils.getDuration(t11));
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
				}

				return list;
			}

			@Override
			public void done() {
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						OPICMList list = get();
						if (list != null) {
							reportLinkAll(list,eiParent,eiChild);
						} else {
							//msg3011.1 = No Link(s) were created.
							com.ibm.eacm.ui.UI.showFYI(NavCartFrame.this, Utils.getResource("msg3011.1"));
							StringBuilder sb = new StringBuilder("no possible links found");

							for (int p=0;p<eiParent.length;++p) {
								sb.append(RETURN+"    parent: " + p + " of " + eiParent.length + " " +  eiParent[p].getEntityType());
							}

							for (int i=0;i<eiChild.length;++i) {
								sb.append(RETURN+"        child " + i + " of " + eiChild.length + " " + eiChild[i].getEntityType());
							}

							logger.log(Level.WARNING, sb.toString());
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"linking all");
				}finally{
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" linkall dispatch ended "+Utils.getDuration(t11));
					enableActionsAndRestore();
					eiParent= null;
					eiChild = null;
				}
			}
			/* (non-Javadoc)
			 * @see com.ibm.eacm.mw.DBSwingWorker#notExecuted()
			 */
			public void notExecuted(){
				logger.log(Level.FINER,"LinkAllAction.LinkAllWorker"+getIdStr()+" not executed");
				enableActionsAndRestore();
				worker = null;
			}
		}
	}

	//====================================================================
	// not really sure what this is really supposed to do, just migrated it as is
	private class PasteAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		PasteAction() {
			super(CUTPASTE_ACTION);
			putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("paste.gif"));
		}

		public void actionPerformed(ActionEvent e) {
			EntityItem[] ndItems = null;
			EntityItem[] dataItems = null;

			MetaLink relator = null;
			try {
				ndItems = cList.getCartable().getSelectedEntityItems(true);
				dataItems = tabbedPane.getSelectedEntityItems(false,true);
			} catch (OutOfRangeException range) {
				com.ibm.eacm.ui.UI.showFYI(NavCartFrame.this,range);
				return;
			}
			relator = getCutRelator();

			if (relator == null || ndItems == null || dataItems == null) {
				return;
			}
			disableActionsAndWait(); //disable all other actions and also set wait cursor
			worker = new PasteCutWorker(ndItems,dataItems, relator);
			RMIMgr.getRmiMgr().execute(worker);
		}

		private class PasteCutWorker extends DBSwingWorker<String, Void> {
			private EntityItem[] eiParent;
			private EntityItem[] eiChild;
			private MetaLink relator;

			private long t11 = 0L;
			PasteCutWorker(EntityItem[] eip, EntityItem[] eic,MetaLink rel){
				eiParent=eip;
				eiChild = eic;
				relator = rel;
			}
			@Override
			public String doInBackground() {
				String out = null;
				try{
					t11 = System.currentTimeMillis();
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting pastecut "+getIdStr());

					if (DBUtils.link(BehaviorPref.getLinkTypeKey(), cList.getCartable().getProfile(),eiParent, eiChild, 
							relator, EANUtility.LINK_MOVE,1)) {
						out = reportLink(eiParent, eiChild, relator);
					}
				}catch(Exception ex){ // prevent hang
					logger.log(Level.SEVERE, "",ex);
				}finally{
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" pastecut bg ended "+Utils.getDuration(t11));
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
				}

				return out;
			}

			@Override
			public void done() {
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						String out = get();
						if (out != null) {
							//msg3011.0 = The link(s) have been created.
							UI.showLinkResults(NavCartFrame.this,out);
							removeSelectedItems(cList.getCutGroup(), eiChild);
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"pastecut");
				}finally{
					Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" pastecut dispatch ended "+Utils.getDuration(t11));
					enableActionsAndRestore();
					eiParent= null;
					eiChild = null;
					relator = null;
				}
			}
			/* (non-Javadoc)
			 * @see com.ibm.eacm.mw.DBSwingWorker#notExecuted()
			 */
			public void notExecuted(){
				logger.log(Level.FINER,"PasteCutWorker"+getIdStr()+" not executed");
				enableActionsAndRestore();
				worker = null;
				eiParent= null;
				eiChild = null;
				relator = null;
			}
		}
	}
	private void removeSelectedItems(EntityGroup eg, EntityItem[] ei) {
		// remove the selected item(s) from the group
		for (int i=0;i<ei.length;++i) {
			eg.removeEntityItem(ei[i]);
		}

		if (eg.getEntityItemCount() == 0) { // if all items were removed, remove the tab too
			if (eg == cList.getCutGroup()) {
				//tab will be removed by this method
				cList.clearCutGroup();
			} else {
				//msg11003 = Would you like to remove empty tab?
				int reply =  YES_BUTTON;
				if (this.isShowing()){ // removeselecteditems may have been called by cartlist and may not be showing
					reply = com.ibm.eacm.ui.UI.showConfirmYesNo(this, Utils.getResource("msg11003"));
				}
				if (reply == YES_BUTTON) {
					tabbedPane.removeTab(eg.getKey());
					cList.getEntityList().removeEntityGroup(eg);
				}else{
					tabbedPane.refreshTab(eg); // this will add it!!!! but removes it first.. messy
				}
			}
		}else{
			tabbedPane.refreshTab(eg); // this will add it!!!! and messes up filters and sorts
		}
	}
	private MetaLink getCutRelator() {
		if (cList.getCutGroup() != null) {
			EntityGroup egParent = cList.getCartable().getSelectedEntityGroup();
			if (egParent != null) {
				MetaLink[] links = egParent.getMetaLinkList(cList.getCutGroup());
				if (links == null || links.length == 0) {
					//msg23043 = No relationship exists between {0} and Cut group
					com.ibm.eacm.ui.UI.showErrorMessage(this,Utils.getResource("msg23043",egParent.getLongDescription()));
					return null;
				}
				return getRelatorType(links);
			}
		}
		return null;
	}
	private MetaLink getRelatorType(MetaLink[] link) {
		MetaLink ml = null;
		if (link.length == 1) {
			ml= link[0];
		}else{
			//msg24009 = Please Select a relator.
			Object values[] = com.ibm.eacm.ui.UI.showList(this,
					Utils.getResource("msg24009"),link);
			if(values !=null && values.length>0){
				ml = (MetaLink)values[0];
			}
		}

		return ml;
	}
	/**
	 * reportLink
	 * @param eiParent
	 * @param eiChild
	 * @param rel
	 * @return
	 */
	private String reportLink(EntityItem[] eiParent, EntityItem[] eiChild, MetaLink rel) {
		StringBuffer sb = new StringBuffer();
		//linked2 = linked to:
		String msg = " " + Utils.getResource("linked2") + " ";
		for (int p=0;p< eiParent.length;++p) {
			for (int c=0;c<eiChild.length;++c) {
				sb.append(getParentString(eiParent[p], rel) + msg + eiChild[c].toString() + NEWLINE);
			}
		}
		return sb.toString();
	}

	/**
	 * getParentString
	 * @param ei
	 * @param rel
	 * @return
	 */
	private String getParentString(EntityItem ei, MetaLink rel) {
		if (rel != null) {
			String e1Type = rel.getEntity1Type();
			if (!e1Type.equals(ei.getEntityType()) && ei.hasDownLinks()) {
				EANEntity ent = ei.getDownLink(0);
				if (e1Type.equals(ent.getEntityType())) {
					return ent.toString();
				}
			}
		}
		return ei.toString();
	}
}
