//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package com.ibm.eacm.nav;

import com.ibm.eacm.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.mw.RMIMgr;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.opicmpdh.middleware.*;

import java.awt.event.*;
import java.awt.*;
import java.io.File;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import javax.swing.event.*;

import javax.swing.border.Border;

import com.ibm.eacm.actions.*;
import com.ibm.eacm.cart.*;
import com.ibm.eacm.edit.EditController;

import com.ibm.eacm.objects.EANActionMenu;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.HistoryInterface;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.NavLayoutPref;
import com.ibm.eacm.preference.PrefMgr;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.EntityGroupTable;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.tabs.NavController;

import com.ibm.eacm.tabs.DataActionPanel;

import com.ibm.eacm.toolbar.ComboItem;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;
import com.ibm.eacm.tree.ActionTreeScroll;
import com.ibm.eacm.ui.UI;


/**
 * this is the navigate panel
 * @author Wendy Stimpson
 */
//$Log: Navigate.java,v $
//Revision 1.18  2015/03/03 21:48:48  stimpsow
//only enable goto if there are multiple tabs
//
//Revision 1.17  2014/10/27 19:33:19  wendy
//Set actions as 'none available' if no tab is added (entitygroup is null)
//
//Revision 1.16  2014/10/20 19:56:07  wendy
//Add worker id to timing log output
//
//Revision 1.15  2014/10/03 11:08:08  wendy
//IN5515352 remove F8 keyboard mapping
//
//Revision 1.14  2014/04/25 21:09:05  wendy
//get tab tool tip from selected history item
//
//Revision 1.13  2014/02/24 15:10:48  wendy
//RCQ285768 - view cached XML in JUI
//
//Revision 1.12  2014/01/14 15:38:04  wendy
//add more info to trace msgs
//
//Revision 1.11  2013/09/23 21:15:14  wendy
//notify any edits if nav is refreshed
//
//Revision 1.10  2013/09/19 22:01:07  wendy
//control sort when a row is updated
//
//Revision 1.9  2013/09/19 16:34:10  wendy
//add abr queue status
//
//Revision 1.8  2013/07/31 18:06:25  wendy
//correct gotoprev with pinned nav
//
//Revision 1.7  2013/07/25 19:40:25  wendy
//prevent nullptr if data.gettable is null
//
//Revision 1.6  2013/07/22 13:34:23  wendy
//highlight filterstatus in statusbar after backnav
//
//Revision 1.5  2013/05/01 18:35:13  wendy
//perf updates for large amt of data
//
//Revision 1.4  2013/04/04 15:33:28  wendy
//restore selection after search and link
//
//Revision 1.3  2013/02/07 13:37:38  wendy
//log close tab
//
//Revision 1.2  2012/11/09 20:47:59  wendy
//check for null profile from getNewProfileInstance()
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class Navigate extends DataActionPanel // put in a panel, so can set cursor for it and its children
implements ChangeListener,HistoryInterface
{
	private static final long serialVersionUID = 1L;

	protected static final Logger logger;
	static {
		logger = Logger.getLogger(NAV_PKG_NAME);
		logger.setLevel(PrefMgr.getLoggerLevel(NAV_PKG_NAME, Level.INFO));
	}
	// actions that rely on row or column selections
	private static final String[] ROWCOL_SELECTION_ACTIONS = {
		NAVCUT_ACTION,
		ADDALL2CART_ACTION,ADD2CART_ACTION,
		ENTITYDATA_ACTION,NAVWU_ACTIONSET,NAVSRCH_ACTIONSET,NAVEDIT_ACTIONSET,
		NAVWF_ACTIONSET,NAV_ACTIONSET,NAVPICK_ACTIONSET
	};

	/**
	 * DATA_DOUBLE - '1' is needed in form xml for dual nav
	 */
	private static final int DATA_DOUBLE = 1;
	private static final int SINGLE_CLICK = 1;
	private static final int DEFAULT = -1;

	/**
	 * nav init load
	 */
	public static final int NAVIGATE_INIT_LOAD = 0; //always load
	/**
	 * nav load
	 */
	public static final int NAVIGATE_LOAD = 1; //always load
	/**
	 * nav reset
	 */
	public static final int NAVIGATE_RESET = 2; //always load
	/**
	 * load from history
	 */
	private static final int NAVIGATE_RENAVIGATE = 3; //never load
	/**
	 * refresh
	 */
	public static final int NAVIGATE_REFRESH = 4; //never load

	private PLLinkAction pllinkAction = null;
	private NavHistBox history = null;
	private NavData data = null;
	private ActionTreeScroll actionTree = null;
	private EntityList seed = null;
	private NavPickFrame pickFrame = null;
	private WindowAdapter pickFrameListener = null;
	private ReNavListWorker renavListWorker = null;

	private EntityList navList = null;
	private SerialObjectController nsoController = null;

	private NavController parent = null;
	private int opwgID = -1;
	private int iSide = -1;
	private boolean bRefresh = false;
	private ButtonGroup m_languageBG;
	private HashSet<EditController> openEdits = new HashSet<EditController>();


	private MouseListener mouseListener = new MouseAdapter() {
		public void mouseReleased(MouseEvent evt) {
			if (evt.isPopupTrigger()) {
				popup.show(evt.getComponent(), evt.getX(), evt.getY());
			}
		}
	};

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		if(navList!=null && navList.getParentActionItem() !=null){
			return "Navigate: "+navList.getParentActionItem().getActionItemKey();
		}else{
			return super.toString();
		}
	}
	/**
	 * called by actions when using workers
	 */
	public void disableActionsAndWait(){
		history.setEnabled(false);
		//	getAction().setEnabled(false); // this makes focus jump from nav[0] to nav[1]
		if(data.getTable()!=null){
			data.getTable().setEnabled(false);
		}
		super.disableActionsAndWait();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.DataActionPanel#enableActionsAndRestore()
	 */
	public void enableActionsAndRestore(){
		super.enableActionsAndRestore();

		if(isWaiting()){ // all workers have not completed
			return;
		}
		getAction(BOOKMARK_ACTION).setEnabled(true);
		getAction(PIN_ACTION).setEnabled(true);

		history.setEnabled(true);
		getActionTree().setEnabled(true);
		if(data.getTable()!=null){
			data.getTable().setEnabled(true);
		}

		if(((JTabbedPane)data).getSelectedComponent()!=null){
			((JTabbedPane)data).getSelectedComponent().requestFocusInWindow(); // put focus on tab
		}
	}

	/* (non-Javadoc)
	 * when column selection changes this is notified
	 * also called when tablemodel is changed
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent lse) {
		if (!lse.getValueIsAdjusting()) {
			updateRowColActions();
		}
	}

	/**
	 * update the actions that rely on attribute selection
	 */
	private void updateRowColActions(){
		// these actions require something to be selected
		for (int i=0; i<ROWCOL_SELECTION_ACTIONS.length; i++){
			EACMAction act = getAction(ROWCOL_SELECTION_ACTIONS[i]);
			if (act!=null){
				act.setEnabled(true);
			}
		}
		getCart().updateActions();
	}
	/**
	 * enable actions based on current selection
	 */
	public void refreshActions() {
		updateMenuActions();
		EntityGroupTable tbl = data.getTable();
		if (tbl != null){
			EntityGroup eg = tbl.getEntityGroup();
			adjustMenus(eg);
		}
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.DataActionPanel#getJTable()
	 */
	public BaseTable getJTable() {
		return data.getTable();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.DataActionPanel#getDefaultToolbarLayout()
	 */
	public ComboItem getDefaultToolbarLayout() {
		if (NavLayoutPref.isDualNavLayout()) {
			return DefaultToolbarLayout.NAV_BAR_DUAL;
		} else {
			return DefaultToolbarLayout.NAV_BAR;
		}
	}

	/**
	 * constructor - called from NavController.generateForm()
	 * @param parent2
	 * @param iSide2
	 */
	public Navigate(NavController parent2, int iSide2) {
		this.parent = parent2;
		this.iSide = iSide2;
	}

	/**
	 * updateTabPlacement
	 */
	public void updateTabPlacement() {
		data.updateTabPlacement();
	}

	/**
	 * getTagDisplay
	 * @return
	 */
	public String getTagDisplay() {
		if (navList != null) {
			return navList.getTagDisplay();
		}
		return null;
	}

	/**
	 * get Navigate controller
	 * @return
	 */
	public NavController getNavController() {
		return parent;
	}

	/**
	 * getCurrentEntityList
	 * @return
	 */
	public EntityList getCurrentEntityList() {
		return navList;
	}

	/**
	 * getParentEntityGroup
	 * @return
	 */
	public EntityGroup getParentEntityGroup() {
		if (navList != null) {
			return navList.getParentEntityGroup();
		}
		return null;
	}

	/**
	 * getNavSerialObjectController
	 * @return
	 */
	public SerialObjectController getNavSerialObjectController() {
		return nsoController;
	}

	/**
	 * getProfile
	 * @return
	 */
	public Profile getProfile() {
		if (navList == null) {
			return null;
		}
		return navList.getProfile();
	}
    /**
     * change the read language to this nlsitem
     * @param nls
     */
    public void setReadLanguage(NLSItem nls){
    	Profile prof = getProfile();
    	if (prof != null) {
    		if(prof.getReadLanguage().getNLSID()==nls.getNLSID()){
    			return;
    		}

    		prof.setReadLanguage(nls);

    		String nlsStr = ""+nls.getNLSID();
    		// make sure the menu button reflects the correct language, can only happen in dualnav
    		for (Enumeration<AbstractButton> btnEnum = m_languageBG.getElements(); btnEnum.hasMoreElements();){
    			JMenuItem jmi = (JMenuItem)btnEnum.nextElement();
    			EACMAction act = (EACMAction)jmi.getAction();
    			if(act.getActionKey().equals(nlsStr)){
    				if(!jmi.isSelected()){
    					jmi.setSelected(true);
    				}
    				break;
    			}
    		}

    		EACM.getEACM().setStatus(prof);

       		refresh();
    	}
    }
	/**
	 * requestFocus
	 */
	public boolean requestFocusInWindow() {
		boolean rfw = false;
		if (data instanceof NavDataDouble) {
			DataTab dt = ((NavDataDouble)data).getDataTab();
			if (dt != null) {
				rfw = dt.requestFocusInWindow();
			}
		} else {
			EntityGroupTable nt = data.getTable();
			if (nt != null) {
				rfw = nt.requestFocusInWindow();
			}
		}
		return rfw;
	}

	/**
	 * setSeed - this is needed because the Navigate is actually built from xml
	 * @param s
	 */
	public void setSeed(EntityList s) {
		seed = s;
	}

	/**
	 * build - used for single navigate, called from xml form generation
	 */
	public void build() {
		build(DEFAULT, SINGLE_CLICK);
	}

	/**
	 * build - used for navigate, called from xml form generation
	 * @param dataType
	 * @param actionClicks
	 */
	public void build(int dataType, int actionClicks) {
		long starttime = System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"buildnav load");

		navList = seed; // profile is needed when actions are created

		Profile prof = getProfile();
		nsoController = new SerialObjectController();
		opwgID = prof.getOPWGID();

		history = new NavHistBox(this);
		history.setName("Navigate History");

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"buildnav history ended "+Utils.getDuration(starttime));
		data = createData(dataType, actionClicks);

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"buildnav data ended "+Utils.getDuration(starttime));
		if (dataType != DATA_DOUBLE) {
			actionTree = new ActionTreeScroll(this,  actionClicks);
			actionTree.setName("Navigate Action");
		}

		createActions();  // each navigate must have its own actions for enabling/disabling

		history.addActionListener(getAction(PREV_ACTION));

		if (iSide == 0) {
			parent.setToolbar(getToolbar());
		}

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"buildnav init ended "+Utils.getDuration(starttime));
		finishAction(seed, NAVIGATE_INIT_LOAD);
		seed=null;

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"buildnav load ended "+Utils.getDuration(starttime));
	}

	private NavData createData(int type, int clicks) {
		if(type ==DATA_DOUBLE){
			return new NavDataDouble(this, clicks);
		}else{
			return new NavDataSingle(this);
		}
	}
	private void createActions() {
		addAction(new GoToAction());
		addAction(new ResetAction(this));
		addAction(new RefreshAction());
		addAction(new ExportAction());
		addAction(new FindRepAction());
		addAction(new FindNextAction());
		addAction(new ShowRelHistoryAction(this,this));

		addAction(new ShowEntityHistoryAction(this,this));

		addAction(new NavRestoreAction(this));
		addAction(new AddCutAction(this));
		addAction(new PrevAction(this,history));
		addAction(new BookmarkAction(this));

		addAction(new EANNavActionSet(this));
		addAction(new EANNavPickActionSet(this));
		addAction(new EANSearchActionSet(this));
		addAction(new EANEditActionSet(this));
		addAction(new EANMtrxActionSet(this));
		addAction(new EANWUActionSet(this));
		addAction(new EANCreateActionSet(this));
		addAction(new EANRptActionSet(this));
		addAction(new EANWFlowActionSet(this));
		addAction(new EANPDGActionSet(this));
		addAction(new EANXtractActionSet(this));
		addAction(new EANCopyActionSet(this));
		//addAction(new EANABRViewActionSet(this));
		addAction(new EANSetCGActionSet(this));
		addAction(new EANLinkActionSet(this));

		if (NavLayoutPref.isDualNavLayout()) {
			addAction(new EANLinkDualActionSet(this));
		}

		addAction(new EANLockPActionSet(this));
		addAction(new EANDeActActionSet(this));
		addAction(new EANQueryActionSet(this));

		addAction(new AddSelected2CartAction(getCart()));
		addAction(new AddAll2CartAction(getCart()));
		addAction(new ResetCartAction(getCart(), EACM.getEACM()));
		addAction(new ShowCartAction(parent));
		addAction(new PinAction(parent));
		addAction(new PrintAction(parent));
		addAction(new ClearChgGrpAction(parent));
		addAction(new ViewPersistentLocksAction(parent));
		addAction(new ViewPersistentWGLocksAction(parent));
		addAction(new ABRQSAction(parent));

		//ENTITYDATA_ACTION
		addAction(new EntityDataAction((RSTTable)null));

		//VIEWXML_ACTION
		addAction(new com.ibm.eacm.actions.ViewXMLAction(EACM.getEACM(),this));
		
		//SORT_ACTION
		addAction(new com.ibm.eacm.actions.SortAction(EACM.getEACM(),null));

		//FILTER_ACTION
		com.ibm.eacm.actions.FilterAction fa = new com.ibm.eacm.actions.FilterAction(EACM.getEACM(),null);
		fa.setNavigate(this);
		addAction(fa);

		//SELECTALL_ACTION
		addAction(new SelectAllAction(null));

		//SELECTINV_ACTION
		addAction(new SelectInvertAction((BaseTable)null));

		pllinkAction = new PLLinkAction(this); // do not add to actiontable, this is used by the pickframe
		//linkChainAction = new LinkChainAction(this);

		createPopupMenu();
		createMenu();
		createToolbar();
	}

	/**
	 * createPopupMenu
	 */
	private void createPopupMenu() {
		popup = new JPopupMenu("popup");
		popup.add(getAction(SORT_ACTION));
		popup.addSeparator();
		popup.add(getAction(FINDREP_ACTION));
		popup.add(getAction(FILTER_ACTION));
		popup.addSeparator();
		popup.add(getAction(SELECTALL_ACTION));
		popup.add(getAction(SELECTINV_ACTION));
		popup.addSeparator();
		popup.add(getAction(NAVCUT_ACTION));
	}
	/**
	 * createMenu
	 *
	 */
	private void createMenu() {
		createFileMenu();
		createEditMenu();
		createViewMenu();
		createHistoryMenu();
		createActionsMenu();
		createTableMenu();
	}

	public void createFileMenu() {
		JMenu fileMenu = new JMenu(Utils.getResource(FILE_MENU));
		fileMenu.setMnemonic(Utils.getMnemonic(FILE_MENU));

		addGlobalActionMenuItem(fileMenu, CLOSETAB_ACTION);

		addGlobalActionMenuItem(fileMenu,CLOSEALL_ACTION);

		fileMenu.addSeparator();

		addLocalActionMenuItem(fileMenu,EXPORT_ACTION);

		addLocalActionMenuItem(fileMenu, PRINT_ACTION);

		addGlobalActionMenuItem(fileMenu,PAGESETUP_ACTION);

		fileMenu.addSeparator();


		addGlobalActionMenuItem(fileMenu,LOGOFF_ACTION);
		addGlobalActionMenuItem(fileMenu,EXIT_ACTION);

		getMenubar().add(fileMenu);
	}
	/**
	 * createEditMenu
	 *
	 */
	private void createEditMenu() {
		JMenu editMenu = new JMenu(Utils.getResource(EDIT_MENU));
		editMenu.setMnemonic(Utils.getMnemonic(EDIT_MENU));

		addLocalActionMenuItem(editMenu,NAVCUT_ACTION);

		editMenu.addSeparator();

		addLocalActionMenuItem(editMenu,SELECTALL_ACTION);
		addLocalActionMenuItem(editMenu,SELECTINV_ACTION);

		editMenu.addSeparator();

		addLocalActionMenuItem(editMenu,FINDREP_ACTION);
		addLocalActionMenuItem(editMenu,FINDNEXT_ACTION);

		editMenu.addSeparator();
		addLocalActionMenuItem(editMenu, ADD2CART_ACTION);

		getMenubar().add(editMenu);
	}

	/**
	 * createTableMenu
	 */
	private void createTableMenu() {
		JMenu tableMenu = new JMenu(Utils.getResource(TABLE_MENU));
		tableMenu.setMnemonic(Utils.getMnemonic(TABLE_MENU));

		addLocalActionMenuItem(tableMenu, SORT_ACTION);

		tableMenu.addSeparator();
		addLocalActionMenuItem(tableMenu, FILTER_ACTION);

		tableMenu.addSeparator();
		addLocalActionMenuItem(tableMenu, PIN_ACTION);

		addLocalActionMenuItem(tableMenu, BOOKMARK_ACTION);

		tableMenu.addSeparator();

		addLocalActionMenuItem(tableMenu, SHOWHIST_ACTION);
		addLocalActionMenuItem(tableMenu, SHOWRELHIST_ACTION);

		addLocalActionMenuItem(tableMenu, ENTITYDATA_ACTION);

		tableMenu.addSeparator();

		addLocalActionMenuItem(tableMenu, REFRESH_ACTION);

		getMenubar().add(tableMenu);
	}

	/**
	 * createHistoryMenu
	 *
	 */
	private void createHistoryMenu() {
		JMenu histMenu = new JMenu(Utils.getResource(HISTORY_MENU));
		histMenu.setMnemonic(Utils.getMnemonic(HISTORY_MENU));

		addLocalActionMenuItem(histMenu,PREV_ACTION);

		histMenu.addSeparator();

		addLocalActionMenuItem(histMenu,RESET_ACTION);

		addLocalActionMenuItem(histMenu, RESETCART_ACTION);
		getMenubar().add(histMenu);
	}
	/**
	 * createActionsMenu
	 */
	private void createActionsMenu() {
		JMenu actionsMenu = new JMenu(Utils.getResource(ACTIONS_MENU));
		actionsMenu.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));

		Profile prof = getProfile();

		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAV_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVPICK_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVSRCH_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVEDIT_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVMTRX_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVWU_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVCRT_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVRPT_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVWF_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVPDG_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVXTRACT_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVCOPY_ACTIONSET)));
		//actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVABRVIEW_ACTIONSET)));
		addLocalActionMenuItem(actionsMenu, ABRQS_ACTION);
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVSETCGGRP_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVLINK_ACTIONSET)));

		if (NavLayoutPref.isDualNavLayout()) {
			actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVLINKD_ACTIONSET)));
		}
		addLocalActionMenuItem(actionsMenu, CGRP_ACTION);

		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVLOCKP_ACTIONSET)));
		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVDEACT_ACTIONSET)));

		if (prof != null) {
			if (!prof.hasRoleFunction(Profile.ROLE_FUNCTION_READONLY)) {
				addLocalActionMenuItem(actionsMenu, VIEWP_ACTION);
			}
			if (prof.hasRoleFunction(Profile.ROLE_FUNCTION_WGLOCKS)) {
				addLocalActionMenuItem(actionsMenu, VIEWPW_ACTION);
			}
		}

		addLocalActionMenuItem(actionsMenu, RESTORE_ACTION);

		actionsMenu.add(new EANActionMenu((EANActionSet)getAction(NAVQUERY_ACTIONSET)));
		
		addLocalActionMenuItem(actionsMenu, VIEWXML_ACTION);

		getMenubar().add(actionsMenu);
	}
	/**
	 * createViewMenu
	 */
	@SuppressWarnings("unchecked")
	private void createViewMenu() {
		JMenu viewMenu = new JMenu(Utils.getResource(VIEW_MENU));
		viewMenu.setMnemonic(Utils.getMnemonic(VIEW_MENU));

		JMenu langMenu = new JMenu(Utils.getResource(NAVLANG_ACTIONSET));
		langMenu.setMnemonic(Utils.getMnemonic(NAVLANG_ACTIONSET));
		viewMenu.add(langMenu);

		Profile prof = getProfile();
		if (prof != null) {
			NLSItem nls =prof.getReadLanguage();
			// must clone or the 'active' read language is tampered with
			Vector<Object> NLSVector = (Vector<Object>)prof.getReadLanguages().clone();
			Collections.sort(NLSVector,new Comparator<Object>(){
				public int compare(Object o1, Object o2) {
					return o1.toString().compareToIgnoreCase(o2.toString());
				}
			});

			NLSItem[] array = new NLSItem[NLSVector.size()];
			NLSVector.copyInto(array);

			m_languageBG = new ButtonGroup();
			for (int i=0; i<array.length; i++){
				EACMAction act = new NLSLangAction(array[i]);
				addAction(act); // add here so it will be dereferenced
				JMenuItem jmi = new JRadioButtonMenuItem(act) {
					private static final long serialVersionUID = 1L;
					// this is the only way to get selected to show up.. setting icon didnt do it
					public Icon getIcon() {
						if (isSelected()) {
							return UIManager.getIcon("eannounce.selectedIcon");
						}
						return UIManager.getIcon("eannounce.unselectedIcon");
					}
				};

				langMenu.add(jmi);
				if (array[i]==nls){
					jmi.setSelected(true);
				}
				m_languageBG.add(jmi);
			}
		}
		viewMenu.addSeparator();

		addLocalActionMenuItem(viewMenu, SHOWCART_ACTION);

		addLocalActionMenuItem(viewMenu, GOTO_ACTION);

		getMenubar().add(viewMenu);
	}
	/**
	 * isPin
	 * @return
	 */
	public boolean isPin() {
		return parent.isPin();
	}

	/**
	 * called by PrevAction
	 */
	protected void gotoPrev(){
		history.backup();
	}

	/**
	 * child tables need this to invoke popup
	 * @return
	 */
	public MouseListener getMouseListener(){
		return mouseListener;
	}

	/**
	 * dereference
	 *
	 */
	public void dereference() {
		for (int i = 0; i < getComponentCount(); ++i) {
			Component c = getComponent(i);
			if (c instanceof JSplitPane) {
				c.removePropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, parent);
				break;
			}
		}
		history.removeActionListener(getAction(PREV_ACTION));
		super.dereference();

		for (Enumeration<AbstractButton> btnEnum = m_languageBG.getElements(); btnEnum.hasMoreElements();){
			m_languageBG.remove(btnEnum.nextElement());
		}
		m_languageBG = null;

		pickFrame = null;
		pickFrameListener = null;

		data.dereference();
		data = null;

		history.dereference();
		history = null;

		if(actionTree !=null){  // not created for dualnav
			actionTree.dereference();
			actionTree = null;
		}

		navList = null;
		mouseListener = null;
		openEdits.clear();
		openEdits = null;

		parent = null;

		seed = null;

		renavListWorker = null;

		nsoController.dereference();
		nsoController= null;
	}
	/**
	 * dereference data needed by workers, if worker is running, it must call this when done
	 */
	public void derefWorkerData(){
		super.derefWorkerData();

		pllinkAction.dereference();
		pllinkAction = null;
	}

	/**
	 * resetHistory
	 */
	public void resetHistory() {
		if (history != null) {
			history.reset();
		}
	}

	private boolean loadHist(int navType) {
		return (navType < NAVIGATE_RENAVIGATE);
	}

	/**
	 * loadHistory
	 * @param in
	 */
	public void loadHistory(NavHistBox in) {
		if (in !=null){
			history.load(in);
		}
	}

	/**
	 * load - called after loading a bookmark or when finishing a navigate action (search too)
	 * @param eList
	 * @param navType
	 */
	public void load(EntityList eList, int navType) {
		EANActionItem eai = null;
		logger.log(Level.FINER,"entered eList: "+eList);

		bRefresh = false;

		if (isPin()) {
			NavController.loadFromNav(parent, history, eList, getProfile());
			return;
		}
		parent.setSessionTagText(eList.getTagDisplay());
		eai = eList.getParentActionItem();

		logger.log(Level.FINER," parentaction: "+eai!=null?eai.getActionItemKey():"null");
		if (eai instanceof NavActionItem ||
				eai instanceof SearchActionItem ||
				eai instanceof ABRStatusActionItem ||
				eai instanceof ExtractActionItem)
		{
			//search and link was not returning to selected items, save it here
			try{
				if (eai instanceof SearchActionItem){
					EntityItem[] ei = getSelectedEntityItems(false, true);
					// set the history in the current action
					NavSerialObject nso = this.nsoController.getCurrentNso();
					if(nso!=null){
						nso.resetKeys(data.getEntityGroupKey(), ei);
					}
				}
			}catch(OutOfRangeException e){}
			
			navList = eList;
			EACM.getEACM().updateBookmarkAction(eai,getNavigationHistory());

			logger.log(Level.FINER," navList "+navList);
			if (!loadHist(navType)) {
				history.removeHistory(history.getSelectedIndex(), true);
			}

			long t1 = System.currentTimeMillis();
			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting subload "+navList.getParentActionItem().getActionItemKey());

			EntityGroup eg = data.loadNav(navList);
			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"subload1 ended "+Utils.getDuration(t1));
			if (loadHist(navType)) {
				NavSerialObject nso = nsoController.generateHist(navList, iSide);
				history.load(nso, navList, true);
			}

			if (eg == null) {
				ActionTreeScroll nAction = getActionTree();
				if (nAction != null) { // can be null if DataTab does not have data or actions
					nAction.clear();
					nAction.noneAvail();
				}
				updateMenuActions(null);
				return;
			}

			data.setSelectedIndex(0);
			eList.setActiveEntityGroup(eg); // what does this do?

			EACM.getEACM().setStatus(navList.getProfile());
		}

		getAction(GOTO_ACTION).setEnabled(data.getTabCount() > 1);
		EACM.getEACM().setFilterStatus(false);

		logger.log(Level.FINER," Navigate"+getUID()+".load exiting");
	}

	/* (non-Javadoc)
	 * make sure any pickframe is closed
	 * @see com.ibm.eacm.tabs.DataActionPanel#close()
	 */
	public boolean canClose() {
		boolean ok = super.canClose();
		if(ok){
			if(pickFrame !=null){
				pickFrame.removeWindowListener(pickFrameListener);
				pickFrameListener = null;
				pickFrame = null;
			}
		}
		return ok;
	}
	/**
	 *
	 * call from searchframe or picklist action
	 * @param list
	 */
	public void showPicklist(EntityList eList) {
		if(pickFrame !=null){
			pickFrame.removeWindowListener(pickFrameListener);
			pickFrame.setVisible(false);
			pickFrame.dispose();
			pickFrame.dereference();
			pickFrame = null;
			pllinkAction.setPickFrame(pickFrame);
		}

		pickFrame = new NavPickFrame(this, eList);

		// this is needed because user may close it while worker is running
		pickFrameListener = new WindowAdapter(){
			public void windowClosed(WindowEvent e) {
				pickFrame.removeWindowListener(this);
				pickFrame = null;
				pllinkAction.setPickFrame(pickFrame);
			}
			public void windowClosing(WindowEvent e) {
				pickFrame.removeWindowListener(this);
				pickFrame = null;
				pllinkAction.setPickFrame(pickFrame);
			}
		};
		pickFrame.addWindowListener(pickFrameListener);

		pickFrame.setVisible(true);
	}
	//=========================================================================
	/**
	 * called by navpickframe.linkaction on the evt dispatch thread
	 * @param dataItems
	 */
	protected void link(LinkActionItem lai, EntityItem[] dataItems){
		pllinkAction.setPickFrame(pickFrame);
		try {
			EntityItem[] navItems = getSelectedEntityItems(false, true);
			pllinkAction.link(lai,navItems,dataItems);
		} catch (OutOfRangeException range) {} // already checked this in navpickframe
	}

	/**
	 * reload
	 * @param navList2
	 * @param nso
	 * @param navType
	 */
	protected void reload(EntityList navList2, NavSerialObject nso, int navType) {
		EntityGroup eg = null;
		ActionTreeScroll nAction = null;
		bRefresh = false;

		this.navList = navList2;

		parent.setSessionTagText(navList.getTagDisplay());

		EACM.getEACM().updateBookmarkAction(navList.getParentActionItem(),getNavigationHistory());

		history.removeHistory(history.getSelectedIndex(), true);

		FilterGroup fg = null;
		if (navType == NAVIGATE_REFRESH) {
			if(data.getTable()!=null){
				fg = data.getTable().getFilterGroup();
			}
		}else if(navType ==NAVIGATE_RENAVIGATE){
			// is there a saved fg?
			fg = (FilterGroup)Utils.read(nso.getFileName()+FG_EXT);
		}
		
		eg = data.loadNav(navList);
		logger.log(Level.FINER,"eg "+eg);

		data.setSelected(nso.getKey(), nso.getKeys());

		if (fg != null && data.getTable()!=null) {
			data.getTable().setFilterGroup(fg);
			data.getTable().filter();
		}

		nAction = getActionTree();

		if (eg != null) {
			navList.setActiveEntityGroup(eg);
		} else {
			if (nAction != null) {
				nAction.load(null,  null);
			}
			updateMenuActions(nAction);
		}

		EACM.getEACM().setStatus(navList.getProfile());

		getAction(GOTO_ACTION).setEnabled(data.getTabCount() > 1);
		EACM.getEACM().setFilterStatus(this.isFiltered());
	}

	/**
	 * has any data at all in the selected table
	 * @return
	 */
	public boolean hasData() {
		EntityGroupTable nt = data.getTable();
		if (nt != null) {
			return nt.getRowCount()>0;
		}
		return false;
	}
	/**
	 * table has one or more rows selected
	 * @return
	 */
	public boolean hasSelectedData() {
		EntityGroupTable nt = data.getTable();
		if (nt != null) {
			return nt.getSelectedRowCount()>0;
		}
		return false;
	}

	/**
	 * updateMenuActions
	 */
	public void updateMenuActions() {
		if (parent.getNavigate()==this){
			// must update actions in toolbar and menus, nav has changed
			updateMenuActions(getActionTree());
		}
	}

	/**
	 * getSelectedEntityItems
	 *
	 * @param bnew
	 * @param bEx
	 * @throws com.ibm.eacm.objects.OutOfRangeException
	 * @return
	 */
	public EntityItem[] getSelectedEntityItems(boolean bnew, boolean bEx) throws OutOfRangeException {
		return data.getSelectedEntityItems(bnew, bEx);
	}

	/**
	 * getEntityGroup
	 * @param key
	 * @return
	 */
	protected EntityGroup getEntityGroup(String key) {
		return navList.getEntityGroup(key);
	}

	/**
	 * getHistory - called by build process
	 * @return
	 */
	public NavHistBox getHistory() {
		return history;
	}

	/**
	 * getData - called by build process
	 * @return
	 */
	public NavData getData() {
		return data;
	}

	/**
	 * getActionTree, single nav and dual nav have it in different places
	 * used in build process too so must be public
	 * @return
	 */
	public ActionTreeScroll getActionTree() {
		if (data instanceof NavDataDouble) {
			return ((NavDataDouble) data).getNavActionTree(); // tree may be null if no data
		}
		return actionTree;
	}

	/**
	 * getCart
	 * @return
	 */
	public CartList getCart() {
		return parent.getCart();
	}

	/**
	 * called when user selects a tab or tab is added or removed
	 * this is added in NavDataSingle and NavDataDouble constructors
	 * it is also added to the tabbedPane in the DataTab
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		if ((e.getSource() instanceof NavDataSingle)||
				(e.getSource() instanceof NavDataDouble)){ // NavDataSingle is a JTabbedPane too
			stateChanged();
		}//else
		{ // multiple tabs on navdatadbl dont set current nav
			if (e.getSource() instanceof JTabbedPane){ // must be from the DataTab tabbedpane
				Component comp = (Component)e.getSource();
				while (comp != null){
					if (comp instanceof NavDataDouble){
						if(parent.getNavigate()!=this){
							parent.setCurrentNavigate(this);
						}
						break;
					}
					comp = comp.getParent();
				}

				updateRowColActions();
			}
		}
	}

	private void stateChanged() {
		EntityGroupTable tbl = data.getTable();
		if (tbl != null){
			EntityGroup eg = tbl.getEntityGroup();
			ActionTreeScroll nAction = getActionTree();
			RowSelectableTable rst =  eg.getActionGroupTable();

			nAction.load(Utils.getExecutableActionItems(eg, rst), Utils.getActionTitle(eg, rst),
					navList.getParentActionItem().getActionItemKey()+eg.getEntityType());
			updateMenuActions(nAction);

			adjustMenus(eg);
		}
	}


	/**
	 * has this profile been dialed back?  check needed to enable some actions
	 * @return
	 */
	public boolean isPast(){
		return Utils.isPast(getProfile());
	}

	private void adjustMenus(EntityGroup eg) {
		boolean hasRows = eg.getEntityItemCount() > 0;
		boolean isPast = Utils.isPast(getProfile());

		getAction(SORT_ACTION).setEnabled(hasRows);

		getAction(FINDREP_ACTION).setEnabled(hasRows);
		getAction(FINDNEXT_ACTION).setEnabled(hasRows);

		getAction(FILTER_ACTION).setEnabled(hasRows);
		getAction(SELECTALL_ACTION).setEnabled(hasRows && !isSingleSelect());
		getAction(SELECTINV_ACTION).setEnabled(hasRows && !isSingleSelect());
		//never added setEnabled("rLink", hasRows);
		getAction(EXPORT_ACTION).setEnabled(hasRows);
		getAction(SHOWHIST_ACTION).setEnabled(hasRows);
		getAction(SHOWRELHIST_ACTION).setEnabled(hasRows);
		//action checks now getAction(ENTITYDATA_ACTION).setEnabled(hasRows);
		if (isPast) {
			getAction(NAVCRT_ACTIONSET).setEnabled(false);
		}

		getAction(NAVCUT_ACTION).setEnabled(!isPast);
	}

	/**
	 * isSingleSelect
	 * @return
	 */
	private boolean isSingleSelect() {
		EANActionItem ean = navList.getParentActionItem();
		if (ean instanceof NavActionItem) {
			return ((NavActionItem) ean).isSingleSelect();
		}

		return false;
	}

	/**
	 * export
	 */
	public void export() {
		String strDir = null;
		String file = null;
		FileDialog fd = null;
		String export = data.export();
		if (export == null) {
			// msg11001=Nothing to export.
			com.ibm.eacm.ui.UI.showErrorMessage(this,Utils.getResource("msg11001"));
			return;
		}
		fd = new FileDialog(EACM.getEACM(), Utils.getResource("saveTo"), FileDialog.SAVE);
		fd.setModal(true);
		fd.setResizable(false);
		fd.setVisible(true);
		strDir = fd.getDirectory();
		file = fd.getFile();
		if (strDir != null && file != null) {
			if (!Routines.endsWithIgnoreCase(file, ".csv")) {
				file = file + ".csv";
			}
			Utils.writeString(strDir + file, export,EACM_FILE_ENCODE);
		}
	}

	/**
	 * getKey
	 * @return
	 */
	public String getKey() {
		if (navList != null) {
			return navList.getParentActionItem().getKey();
		}
		return null;
	}

	/**
	 * getOPWGID
	 * @return
	 */
	public int getOPWGID() {
		return opwgID;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.DataActionPanel#updateValOn(java.lang.String)
	 */
	protected void updateValOn(String time) {
		getProfile().setValOnEffOn(time, time);
		refresh();
	}
	/**
	 * this will run a swingworker and enable when done
	 */
	public void forceRefresh() {
		if(this.isMultipleNavigate()){
			((NavDataDouble)data).requestFocusInWindow(); // must do this or focus jumps to nav[1] on F5 for nav[0]
		}

		try {
			EntityItem[] ei = getSelectedEntityItems(false, true);
			Profile prof = getProfile();
			if (isPin()) {
				prof = LoginMgr.getNewProfileInstance(prof);
			}
			if(prof !=null){
				NavSerialObject nso = this.nsoController.getCurrentNso();
				nso.resetKeys(data.getEntityGroupKey(), ei);

				disableActionsAndWait(); //disable all other actions and also set wait cursor
				
				refreshListWorker = new RefreshListWorker(prof, nso,true);	
				RMIMgr.getRmiMgr().execute(refreshListWorker);
			}
		} catch (OutOfRangeException range) {
			UI.showException(this,range);
		}	
	}
	/**
	 * this will run a swingworker and enable when done
	 */
	public void refresh() {
		try {
			EntityItem[] ei = getSelectedEntityItems(false, true);
			Profile prof = getProfile();
			if (isPin()) {
				prof = LoginMgr.getNewProfileInstance(prof);
			}
			if(prof !=null){
				NavSerialObject nso = this.nsoController.getCurrentNso();
				nso.resetKeys(data.getEntityGroupKey(), ei);

				disableActionsAndWait(); //disable all other actions and also set wait cursor
				
				refreshListWorker = new RefreshListWorker(prof, nso,false);	
				RMIMgr.getRmiMgr().execute(refreshListWorker);
			}
		} catch (OutOfRangeException range) {
			UI.showException(this,range);
		}	
	}
	
	/**
	 * cut
	 *  this is enabled when it cant cut.. like the wg, but if you do it after adding wg, it chgs
	 * the tab to 'move'.. ugh.. more crappy code
	 */
	public void addToCutGroup() {
		EntityItem[] ei = null;
		try {
			ei = data.getSelectedEntityItems(false, true);
			// not so sure how this really works.. so for now, stop user if a group already exists
			if(getCart().canAddCutItems(ei)){
				getCart().addCutItems(ei);
			}else{
				//msg6001.0 = Entity Group already exists on WorkFolder.  Please remove it before executing Cut action.
				com.ibm.eacm.ui.UI.showWarning(this, Utils.getResource("msg6001.0"));
			}
		} catch (OutOfRangeException range) {
			com.ibm.eacm.ui.UI.showFYI(this,range);
		}
	}

	/**
	 * isFiltered
	 * @return
	 */
	public boolean isFiltered() {
		if (data != null) {
			EntityGroupTable nt = data.getTable();
			if (nt != null) {
				return nt.isFiltered();
			}
		}
		return false;
	}
	private static final String FG_EXT = ".fg";

	/**
	 * save the filter group for any back nav
	 */
	public void saveFilterGroup(){
		// put in own thread to improve performance
		Runnable writelater = new Runnable() {
			public void run() {
				NavSerialObject nso = nsoController.getCurrentNso();
				String fn = nso.getFileName()+FG_EXT;
				Utils.write(fn, getJTable().getFilterGroup());
				// all will be deleted on exit, so might as well mark it now
				Utils.deleteOnExit(fn); 	
			}
		};

		Thread t = new Thread(writelater);
		t.start();
	}
	
	/**
	 * remove the filter group, no longer filtered
	 */
	public void removeFilterGroup(){
		NavSerialObject nso = this.nsoController.getCurrentNso();
		File f = new File(nso.getFileName()+FG_EXT);
		if(f.exists()){
			f.delete();
		}
	}
	
	/**
	 * called from history combo box
	 * @param fn
	 */
	public void loadFromHistory(final String fn) {
		if(nsoController.isRefresh(fn)){  // why reload when same history is selected?
		} else {
			Profile prof = getProfile();
			if (isPin()) {
				prof = LoginMgr.getNewProfileInstance(getProfile());
			}
			if(prof!=null){
				NavSerialObject nso = nsoController.readNavSerialObject(fn); // this may end up in a new navigate
				if (nso!=null){
					renavListWorker = new ReNavListWorker(prof, nso);
					disableActionsAndWait(); //disable all other actions and also set wait cursor
					RMIMgr.getRmiMgr().execute(renavListWorker);
				}else{
					history.reselectIndex(history.getHistoryCount()-1);
				}
			}
		}
	}


	/**
	 * called by EANNavAction
	 * @return
	 */
	protected EntityList navigate(NavActionItem nai, EntityItem [] eai) {
		return nsoController.navigate(getProfile(),nai, eai, data.getEntityGroupKey());
	}

	/*
	 * should always becalled to complete processing of entityList actions
	 */
	public void finishAction(EntityList eList, int navType) {
		logger.log(Level.FINER," eList");
		// loggers need this put into separate lines
		String listArray[] = Routines.splitString(Utils.outputList(eList), NEWLINE);
		for(int i=0;i<listArray.length; i++){
			logger.log(Level.FINER, listArray[i]);
		}

		long starttime = System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting finishaction");
		if (eList != null) {

			EANActionItem parentAction = eList.getParentActionItem();
			// this is needed when pin is done, to set navlist etc..
			if (parentAction instanceof NavActionItem || parentAction instanceof SearchActionItem) {
				load(eList, navType);
			}

			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"finishAction process ended "+Utils.getDuration(starttime));
			EntityGroup eg = eList.getParentEntityGroup();
			if (eg != null) {
				parent.clearChgGrpAction(parentAction,eg.getEntityItemsAsArray());
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"finishAction clearaction ended "+Utils.getDuration(starttime));
			}

			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"finishAction ended "+Utils.getDuration(starttime));
		}
	}


	/**
	 * getEntityType
	 * @param i
	 * @return
	 */
	public String getEntityType(int i) {
		EntityGroup eg = getEntityGroup();
		if (eg != null) {
			if (i == 0) {
				return eg.getEntityType();
			} else if (i == 1) {
				return eg.getEntity1Type();
			} else if (i == 2) {
				return eg.getEntity2Type();
			}
		}
		return null;
	}


	public Findable getFindable() {
		return data.getTable();
	}
	/**
	 * getHelpText
	 * @return
	 */
	public String getHelpText() {
		EntityGroup eg = data.getEntityGroup();
		if (eg != null) {
			String s = eg.getHelp(eg.getEntityType());
			if (Routines.have(s)) {
				return s;
			}
		}

		return Utils.getResource("nia");
	}

	/**
	 * setShouldRefresh
	 * @param eType
	 * @param opwg
	 */
	public void setShouldRefresh(String eType, int opwg) {
		if (parent != null) {
			parent.setShouldRefresh(eType, opwg, KEY_TYPE);
		}
	}
	
	public void removeEditCtrl(EditController editctrl){
		if(openEdits != null){
			// remove reference to editctrl
			synchronized(this) {
				openEdits.remove(editctrl);
			}
		}
	}
	public void addEditCtrl(EditController editctrl){
		if(openEdits != null){
			// add reference to editctrl
			synchronized(this) {
				openEdits.add(editctrl);
			}
		}
	}
	
	/**
	 * notify any open edit controllers that navigate was refreshed and any source entityitems have now changed
	 */
	private void notifyEdits(){
		Iterator<EditController> itr = openEdits.iterator();
		while(itr.hasNext()){
			EditController editctrl = itr.next();
			// force a refresh when edit is done
			editctrl.setSourceEntityItems(null);
		}
	}
	/**
	 * edit is now complete, items were updated.. refresh the table
	 */
	public void editComplete(){
		// update the ui
		data.updateTableWithSelectedRows();
		//update the lastexecutedts 
		NavSerialObject nso = this.nsoController.getCurrentNso();
		nso.updateLastExecDTS();
		this.enableActionsAndRestore();
	}

	/**
	 * dualnav shares one toolbar, one menu and one set of actions
	 *
	 * @param action
	 */
	private void updateMenuActions(ActionTreeScroll action) {
		boolean bHasData = hasData();
		Profile prof = null;
		EANActionItem[] acts = null;

		Vector<EANActionItem> v = new Vector<EANActionItem>();

		// table changes, so set it now
		EACMAction act = getAction(ENTITYDATA_ACTION);
		if(act!=null){
			((EntityDataAction)act).setTable(data.getTable());
		}
		act = getAction(SORT_ACTION);
		if(act!=null){
			((SortAction)act).setSortable(data.getTable());
		}
		act = getAction(FILTER_ACTION);
		if(act!=null){
			((FilterAction)act).setFilterable(data.getTable());
		}
		act = getAction(SELECTALL_ACTION);
		if(act!=null){
			((SelectAllAction)act).setTable(data.getTable());
		}
		act = getAction(SELECTINV_ACTION);
		if(act!=null){
			((SelectInvertAction)act).setTable(data.getTable());
		}

		EANActionItem[] eai = getActionItemArray(action,ACTION_PURPOSE_NAVIGATE);

		EANActionSet actionSet = ((EANActionSet)getAction(NAV_ACTIONSET));
		Vector<EANActionItem>[] vctArray = splitNavActionPicklists(eai);
		if(actionSet!=null){
			//just get the navigate actions, no picklists
			Vector<EANActionItem> vct = vctArray[0];
			if(vct.size()>0){
				eai = new EANActionItem[vct.size()];
				vct.copyInto(eai);
				vct.clear();
			}else{
				eai = null;
			}

			actionSet.updateEANActions(eai);
		}

		actionSet = ((EANActionSet)getAction(NAVPICK_ACTIONSET));
		if(actionSet!=null){
			//just get the navigate picklist actions
			Vector<EANActionItem> vct = vctArray[1];
			if(vct.size()>0){
				eai = new EANActionItem[vct.size()];
				vct.copyInto(eai);
				vct.clear();
			}else{
				eai = null;
			}
			actionSet.updateEANActions(eai);
		}
		vctArray= null;

		eai = getActionItemArray(action,ACTION_PURPOSE_SEARCH);

		actionSet = ((EANActionSet)getAction(NAVSRCH_ACTIONSET));
		if(actionSet!=null){
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_CREATE);
		actionSet = ((EANActionSet)getAction(NAVCRT_ACTIONSET));
		if(actionSet!=null){
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_EDIT_EXTRACT);
		if (eai != null) {
			v.clear();
			for (int i=0;i<eai.length;++i) {
				if (eai[i] instanceof ExtractActionItem) {
					if (eai[i].isVEEdit()) {
						v.add(eai[i]);
					}
				} else {
					v.add(eai[i]);
				}
			}
			if (v.isEmpty()) {
				eai = null;
			} else {
				eai = (EANActionItem[])v.toArray(new EANActionItem[v.size()]);
				v.clear();
			}
		}

		actionSet = ((EANActionSet)getAction(NAVEDIT_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_MATRIX);
		actionSet = ((EANActionSet)getAction(NAVMTRX_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_WHERE_USED);
		actionSet = ((EANActionSet)	getAction(NAVWU_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_QUERY);
		actionSet = ((EANActionSet)	getAction(NAVQUERY_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_REPORT);
		actionSet = ((EANActionSet)	getAction(NAVRPT_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		if (bHasData) { //3.0a
			eai = getActionItemArray(action,ACTION_PURPOSE_LINK);
			actionSet = ((EANActionSet)	getAction(NAVLINK_ACTIONSET));
			if(actionSet!=null){
				//actionSet.setEnabled(bHasData);
				actionSet.updateEANActions(eai);
			}

			if (data instanceof NavDataDouble) {
			updateLinkDual(eai);
			}
		} else {
			eai = getActionItemArray(action,ACTION_PURPOSE_LINK);
			acts = getOppSelectActions(eai);
			actionSet = ((EANActionSet)	getAction(NAVLINK_ACTIONSET));
			if(actionSet!=null){
				//actionSet.setEnabled(bHasData);
				actionSet.updateEANActions(acts);
			}

			if (data instanceof NavDataDouble) { //3.0a
				updateLinkDual(acts); //3.0a
			} //3.0a
		} //3.0a
		eai = getActionItemArray(action,ACTION_PURPOSE_WORK_FLOW);

		actionSet = ((EANActionSet)	getAction(NAVWF_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_PDG);
		actionSet = ((EANActionSet)	getAction(NAVPDG_ACTIONSET));
		if(actionSet!=null){
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_EXTRACT);
		if (eai != null) {
			v.clear();
			for (int i=0;i<eai.length;++i) {
				if (!eai[i].isVEEdit()) {
					v.add(eai[i]);
				}
			}
			if (v.isEmpty()) {
				eai = null;
			} else {
				eai = (EANActionItem[])v.toArray(new EANActionItem[v.size()]);
				v.clear();
			}
		}

		actionSet = ((EANActionSet)	getAction(NAVXTRACT_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_DELETE);
		actionSet = ((EANActionSet)	getAction(NAVDEACT_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_CHANGE_GROUP); //chgroup

		actionSet = ((EANActionSet)	getAction(NAVSETCGGRP_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_LOCK);
		actionSet = ((EANActionSet)	getAction(NAVLOCKP_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_COPY);

		actionSet = ((EANActionSet)	getAction(NAVCOPY_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		eai = getActionItemArray(action,ACTION_PURPOSE_ABRSTATUS);
		actionSet = ((EANActionSet)	getAction(NAVABRVIEW_ACTIONSET));
		if(actionSet!=null){
			//actionSet.setEnabled(bHasData);
			actionSet.updateEANActions(eai);
		}

		prof = getProfile();

		if (prof != null) {
			//buttons are always added based on toolbarlayout, disable and hide if necessary
			getAction(VIEWP_ACTION).setEnabled(!prof.hasRoleFunction(Profile.ROLE_FUNCTION_READONLY));
			getToolbar().setVisible("viewP", !prof.hasRoleFunction(Profile.ROLE_FUNCTION_READONLY));

			getAction(VIEWPW_ACTION).setEnabled(prof.hasRoleFunction(Profile.ROLE_FUNCTION_WGLOCKS));
			getToolbar().setVisible("viewPW", prof.hasRoleFunction(Profile.ROLE_FUNCTION_WGLOCKS));
			getAction(CGRP_ACTION).setEnabled(prof.getTranID() != 0);
		}else{
			getAction(CGRP_ACTION).setEnabled(false);
		}

		getAction(SHOWCART_ACTION).setEnabled(!Utils.isPast(getProfile()));

		getAction(NAVCUT_ACTION).setEnabled(!Utils.isPast(getProfile()));


	}

	/**
	 * split navigate actions into plain nav and picklist nav
	 * @param eai
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Vector<EANActionItem>[] splitNavActionPicklists(EANActionItem[] eai){
		Vector<EANActionItem>[] vctArray = new Vector[2];
		Vector<EANActionItem> navVct = new Vector<EANActionItem>();
		Vector<EANActionItem> pickVct = new Vector<EANActionItem>();
		vctArray[0] = navVct;
		vctArray[1] = pickVct;
		if(eai != null){
			for (int i=0; i<eai.length; i++){
				EANActionItem eaiItem = eai[i];
				if (eaiItem instanceof NavActionItem) {
					if (!((NavActionItem) eaiItem).isPicklist()) {
						navVct.add(eaiItem);
					}else{
						pickVct.add(eaiItem);
					}
				}
			}
		}
		return vctArray;
	}
	/*
 	 added to properly clear out the action menuBar
	 */
	private EANActionItem [] getActionItemArray(ActionTreeScroll action,String key) {
		if (action != null) {
			return action.getActionItemArray(key);
		}
		return null;
	}

	/*
 	 TIR USRO-R-PKUR-6GEJEY
 	 added to properly clear out the action menuBar
	 */
	private EANActionItem [] getActionItemArray(ActionTreeScroll action,String[] key) {
		if (action != null) {
			return action.getActionItemArray(key);
		}
		return null;
	}

	/**
	 * isMultipleNavigate
	 * @return
	 */
	public boolean isMultipleNavigate() {
		return parent.isMultipleNavigate();
	}

	/**
	 * getOpposingNavigate
	 * @return
	 */
	public Navigate getOpposingNavigate() {
		if (parent != null) {
			return parent.getOpposingNavigate(this);
		}
		return null;
	}

	/**
	 * getSelectedBorder
	 * called by NavDataDouble to highlight selected Navigate
	 * @return
	 */
	public Border getSelectedBorder() {
		return parent.getSelectedBorder(this);
	}

	/**
	 * isType
	 * @param str
	 * @param code
	 * @return
	 */
	public boolean isType(String str, int code) {
		EntityGroup eg = null;
		String str2 = null;
		if (bRefresh) {
			return true;
		}
		eg = getEntityGroup();
		switch (code) {
		case KEY_TYPE :
			str2 = getKey();
			break;
		case RELATOR_TYPE :
			if (eg != null) {
				str2 = eg.getEntityType();
			}
			break;
		case PARENT_TYPE :
			if (eg != null) {
				str2 = eg.getEntity1Type();
			}
			break;
		case CHILD_TYPE :
			if (eg != null) {
				str2 = eg.getEntity2Type();
			}
			break;
		default:
			break;
		}
		if (str2 != null) {
			bRefresh = str2.equals(str);
		}
		return bRefresh;
	}

	/**
	 * shouldRefresh
	 * @return
	 */
	public boolean shouldRefresh() {
		return bRefresh;
	}

	/**
	 * setShouldRefresh
	 * @param b
	 */
	public void setShouldRefresh(boolean b) {
		bRefresh = b;
	}

	/**
	 * getTabToolTipText
	 * @return
	 */
	public String getTabToolTipText() {
		if (history != null && history.getSelectedItem()!=null) {
			return history.getSelectedItem().toString();
		}
		return null;
	}

	/**
	 * @return
	 */
	public String getTabIconKey(){
		if (parent.isBookmark()) {
			return "bookmark.icon";
		}
		return "navigate.icon";
	}

	/**
	 * getTabMenuTitle
	 * @return
	 */
	public String getTabMenuTitle() {
		return getTabTitle();
	}

	/**
	 * getTabTitle
	 * @return
	 */
	public String getTabTitle() {
		Object obj[] = new Object[2];
		if (parent.isBookmark()) {
			//bookmark.title=Bookmark
			obj[0]= Utils.getResource("bookmark.title");
		} else {
			//navigate.title=Navigate
			obj[0]= Utils.getResource("navigate.title");
		}

		obj[1] = getProfile().toString();
		return Utils.getResource("tab.title",obj);
	}

	/**
	 * getNavigationHistory
	 * @return
	 */
	public EANActionItem[] getNavigationHistory() {
		if (history != null) {
			return history.getNavigationHistory();
		}
		return null;
	}
	//bookmark action needs this
	public EANActionItem getNavAction()
	{
		EANActionItem ean = null;
		if (data!=null){
			EntityGroupTable nt = data.getTable(); // how could this be different than the navlist?
			if (nt != null) {
				ean = nt.getParentAction();
			}
		}
		if (ean == null) {
			if (navList!=null){
				ean = navList.getParentActionItem();
			}
		}
		return ean;
	}

	/**
	 * load
	 * @param eList
	 * @param book
	 * @param navType
	 */
	public void load(EntityList eList, BookmarkItem book, int navType) {
		load(eList, navType);
		if (history != null) {
			history.loadBookmarkHistory(book);
		}
	}

	/**
	 * loadBookmarkHistory
	 * @param book
	 */
	public void loadBookmarkHistory(BookmarkItem book) {
		history.loadBookmarkHistory( book);
	}

	/**
	 * processLink
	 * @param ean
	 */
	private void updateLinkDual(EANActionItem[] ean) {
		Navigate opNav = getOpposingNavigate();
		LinkActionItem[] links = null;
		//boolean bHasData = hasData();
		if (opNav != null) {
			Vector<LinkActionItem> v = new Vector<LinkActionItem>();
			String e2Type = opNav.getEntityType(2);
			if (ean != null && e2Type != null) {
				for (int i = 0; i < ean.length; ++i) {
					if (ean[i] instanceof LinkActionItem) {
						LinkActionItem link = (LinkActionItem) ean[i];
						MetaLink ml = link.getMetaLink();
						if (ml != null) {
							if (e2Type.equals(ml.getEntity2Type())) {
								v.add(link);
							}
						}
					}
				}
			}
			if (!v.isEmpty()) {
				links = (LinkActionItem[]) v.toArray(new LinkActionItem[v.size()]);
				v.clear();
			}
		}
		EANActionSet actionSet = ((EANActionSet)//parent.
				getAction(NAVLINKD_ACTIONSET));
		if(actionSet!=null){
			///actionSet.setEnabled(hasData());
			actionSet.updateEANActions(links);
		}

		links = null;
	}


	/*
	 * look for OppSelect link actions
	 */
	private EANActionItem[] getOppSelectActions(EANActionItem[] eai) {
		EANActionItem[] ea = eai;
		if (eai != null) {
			Vector<EANActionItem> v = new Vector<EANActionItem>();
			for (int i = 0; i < eai.length; ++i) {
				EANActionItem eaitem = eai[i];
				if (eaitem instanceof LinkActionItem &&((LinkActionItem) eaitem).isOppSelect()) {
					v.add(eaitem);
				}
			}
			if (v.isEmpty()) {
				ea= null;
			} else {
				ea= (EANActionItem[]) v.toArray(new EANActionItem[v.size()]);
				v.clear();
			}
		}
		return ea;
	}

	/**
	 * @param i
	 * @return
	 */
	public Object[] getParentInformationAtLevel(int i) {
		Object[] out = null;
		if (history != null) {
			int histCount = history.getHistoryCount();
			int iLevel = histCount - i;
			if (iLevel >= 0 && iLevel < histCount) {
				String fn = history.getHistoryFilename(iLevel);
				EntityItem[] ei = nsoController.getEntityItems(fn);
				if (ei != null) {
					out = getParentLevelInformation(ei);
				}
			}
		}
		return out;
	}

	/**
	 * getParentLevelInformation
	 *
	 * @param ei
	 * @return
	 */
	private Object[] getParentLevelInformation(EntityItem[] ei) {
		String strEntityType = null;
		int[] iEntityID = null;
		Object[] out = new Object[2];
		if (ei != null) {
			int ii = ei.length;
			iEntityID = new int[ii];
			for (int i=0;i<ii;++i) {
				if (i == 0) {
					strEntityType = ei[i].getEntityType();
				}
				iEntityID[i] = ei[i].getEntityID();
			}
			out[0] = strEntityType;
			out[1] = iEntityID;
		}
		return out;
	}

	private EntityGroup getEntityGroup() {
		if (data != null) {
			return data.getEntityGroup();
		}
		return null;
	}

	//=======================================================================
	// Actions
	private class GoToAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		GoToAction() {
			super(GOTO_ACTION, KeyEvent.VK_G, Event.CTRL_MASK);
		}
		
		protected boolean canEnable(){
			return (data.getTabCount() > 1); 
		}

		public void actionPerformed(ActionEvent e) {
			String s = com.ibm.eacm.ui.UI.showInput(null,Utils.getResource("msg11002"), "",20);
			if (Routines.have(s)) {
				data.gotoTab(s);
			}
		}
	}

	private class RefreshAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		public RefreshAction() {
			super(REFRESH_ACTION,KeyEvent.VK_F5, 0);
		}
		public void actionPerformed(ActionEvent e) {
			forceRefresh();
		}
	}
	private class ExportAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		public ExportAction() {
			super(EXPORT_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			export();
		}
	}
	//HistoryInterface
	public boolean enableHistory() {
		EntityGroupTable nt = data.getTable();
		if (nt != null) {
			return nt.getSelectedRowCount()>0;
		}
		return false;
	}
	// this will always go down a relator to the child entity
	public EntityItem getHistoryEntityItem() {
		return  data.getCurrentEntityItem(false);
	}

	public EntityItem getHistoryRelatorItem(){
		EntityItem curitem = data.getCurrentEntityItem(); // based on row and column
		if (curitem.getEntityGroup().isRelator() || curitem.getEntityGroup().isAssoc()){
		}else{
			if (curitem.hasUpLinks()){
				// get parent relator to show history
				curitem = (EntityItem)curitem.getUpLink(0);
			}else{
				//msg6000 = No Parent Relator found for selected entity, {0}, in this navigation.
				com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg6000",curitem.getKey()));
			}
		}
		return curitem;
	}
	
	private RefreshListWorker refreshListWorker;
	private class RefreshListWorker extends DBSwingWorker<EntityList, Void> {
		private Profile prof = null;
		private NavSerialObject nso = null;
		private boolean forceRefresh;
		private long t11 = 0L;
		RefreshListWorker(Profile p, NavSerialObject ns, boolean mustRefresh){
			prof = p;
			nso = ns;
			forceRefresh = mustRefresh;
		}
		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{
				t11 = System.currentTimeMillis();
				notifyEdits();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				// using this allows the history to display the correct language
				list = nso.replay(prof,history,forceRefresh,false);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" refresh bg ended "+Utils.getDuration(t11));

				RMIMgr.getRmiMgr().complete(this);
				refreshListWorker = null;
			}

			return list;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					EntityList list = get();
					if (list !=null){
						long t1 = System.currentTimeMillis();
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting reload "+getIdStr());
						reload(list, nso, NAVIGATE_REFRESH);
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" refresh.redisplay ended "+Utils.getDuration(t1));
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting entity list");
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" refresh dispatch ended "+Utils.getDuration(t11));

				prof = null;
				nso = null;
				enableActionsAndRestore();
			}
		}
		public void notExecuted(){
			logger.log(Level.WARNING,getIdStr()+" not executed");
			enableActionsAndRestore();
			refreshListWorker = null;
			prof = null;
			nso = null;
		}
	}

	/**
	 * used when loading from history
	 *
	 */
	private class ReNavListWorker extends DBSwingWorker<EntityList, Void> {
		private Profile prof = null;
		private NavSerialObject nso = null;
		private long t11 = 0L;
		ReNavListWorker(Profile p, NavSerialObject ns){
			prof = p;
			nso = ns;
		}
		@Override
		public EntityList doInBackground() {
			EntityList list = null;
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting renav "+getIdStr());
				list = nso.replay(prof, history,false,isPin());
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" renav ended "+Utils.getDuration(t11));

				RMIMgr.getRmiMgr().complete(this);
				renavListWorker = null;
			}

			return list;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					EntityList list = get();
					if (list !=null){
						long t1 = System.currentTimeMillis();
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting redisplay "+getIdStr());

						if (isPin()) {
							NavController navCtrl = NavController.loadFromNav(parent, history, list, getProfile());
							Navigate nav = navCtrl.getNavigate();
							nav.getData().setSelected(nso.getKey(), nso.getKeys());
							nav.getAction(PREV_ACTION).setEnabled(true); // force this to check
						}else{
							nsoController.setCurrentNso(nso);
							reload(list, nso, NAVIGATE_RENAVIGATE);
						}
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" renavigate.redisplay ended "+Utils.getDuration(t1));
					}else {
						history.reselectIndex(history.getHistoryCount()-1);
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting entity list");
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" renav dispatch ended "+Utils.getDuration(t11));

				prof = null;
				nso = null;
				enableActionsAndRestore();
			}
		}
		public void notExecuted(){
			logger.log(Level.WARNING,"not executed");
			enableActionsAndRestore();
			renavListWorker = null;
			prof = null;
			nso = null;
		}
	}
}
