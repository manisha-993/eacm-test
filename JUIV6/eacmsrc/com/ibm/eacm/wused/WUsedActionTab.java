//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.wused;

import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.event.*;

import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.actions.*;

import com.ibm.eacm.cart.*;

import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.preference.PrefMgr;

import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.WUsedTable;
import com.ibm.eacm.table.WUsedTableModel;
import com.ibm.eacm.tabs.ActionTreeTabPanel;
import com.ibm.eacm.toolbar.*;


/**
 * where used action tab
 * @author Wendy Stimpson
 *
 *
 */
//$Log: WUsedActionTab.java,v $
//Revision 1.8  2013/09/19 22:04:25  wendy
//control sort when a row is updated
//
//Revision 1.7  2013/09/19 15:09:14  wendy
//IN4311970:  Avails linked to incorrect product structures
//
//Revision 1.6  2013/08/29 17:59:23  wendy
//make sure relator exists for removelink
//
//Revision 1.5  2013/05/01 18:35:14  wendy
//perf updates for large amt of data
//
//Revision 1.4  2013/02/07 13:37:38  wendy
//log close tab
//
//Revision 1.3  2012/12/03 19:40:56  wendy
//fix typo
//
//Revision 1.2  2012/11/09 20:47:58  wendy
//check for null profile from getNewProfileInstance()
//
//Revision 1.1  2012/09/27 19:39:25  wendy
//Initial code

public class WUsedActionTab extends ActionTreeTabPanel implements Cartable,
ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	protected static final Logger logger;
	static {
		logger = Logger.getLogger(WU_PKG_NAME);
		logger.setLevel(PrefMgr.getLoggerLevel(WU_PKG_NAME, Level.INFO));
	}
	private WUsedTable wTable = null;
	private JScrollPane tScroll = null;
	private WhereUsedList wList = null;
	private CartList cart = null;
	private NavCartFrame cartFrame = null;

	private WUPickFrame pickFrame = null;
	private WindowAdapter pickFrameListener = null;
	private LinkAction linkAction = null;
	private LinkChainAction linkChainAction = null;

	private String prevRelatorKey = null;
	private boolean prevRelatorExisted = false;
	private EANActionItem actions[] = null; // set of actions for row currently selected
	private Hashtable<String, EANActionItem[]> wuiActionTbl = new Hashtable<String, EANActionItem[]>();// key is WhereUsedItem.relator key, value is EANActionItem[]

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		if(wList!=null){
			return "WhereUsed: "+wList.getParentActionItem().getActionItemKey();
		}else{
			return super.toString();
		}
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.FindableComp#getFindable()
	 */
	public Findable getFindable() {
		return wTable;
	}
	/**
	 * edit is now complete, items were updated.. refresh the table
	 */
	public void editComplete(){
		boolean wasFiltered = wTable.isFiltered();
		wTable.updateTableWithSelectedRows(); 
		if(wasFiltered){
			wTable.filter();
		}
	
		this.enableActionsAndRestore();
	}
	/**
	 * constructor
	 * @param wl
	 * @param key
	 */
	public WUsedActionTab(WhereUsedList wl,String key){
		super(key);
		wList = wl;
		cart = CartList.getCartList(this,getProfile());

		init();
	}

	private void init() {
		wTable = new WUsedTable(wList);

		wTable.addMouseListener(new MouseAdapter() { // base class deref will remove this
			public void mouseReleased(MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					popup.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		});

		tScroll = new JScrollPane(wTable);

		createActions();
		createMenus();
		createPopupMenu();

		createToolbar();

		JPanel tPnl = new JPanel(new BorderLayout());
		tPnl.add(tScroll,BorderLayout.CENTER);
		tPnl.add(getToolbar().getAlignment(), getToolbar());

		if (wTable.getRowCount() > 0) {
			wTable.setColumnSelectionInterval(0,0);
			wTable.setRowSelectionInterval(0,0);

			loadActions();
		} else{
			// disable all EANxx actions, they will be restored when an entity is selected
			disableEANActions();
		}

		//enable actions based on selection
		enableTableActions();

		getSplitPane().setRightComponent(tPnl);
		getSplitPane().setLeftComponent(getActionTree());

		wTable.getSelectionModel().addListSelectionListener(this);

		wTable.getColumnModel().addColumnModelListener(this); // base class has listener methods
	}

	/**
	 * get the valid types for link to control linkaction -avail linking prodstruct will have a feature tab
	 * too, must disable invalid feature linking
	 * @return
	 */
	protected Vector<String> getValidTypes(){
		Vector<String> validTypesVct = new Vector<String>(2);

		String selectedWUItemkeys[] = getSelectedKeys();
		if(selectedWUItemkeys !=null){
			for (int i = 0; i < selectedWUItemkeys.length; ++i) {
				int mdlrowid = getRSTable().getRowIndex(selectedWUItemkeys[i]);
				WhereUsedItem wui = ((WUsedTableModel)wTable.getModel()).getWhereUsedItemForRow(mdlrowid);
				WhereUsedGroup wug = wui.getWhereUsedGroup();
				//use metalink from first selected whereusedgroup
				if (wug != null && wug.isPickListable()) {
					// assumption is all keys will either be parents or children, not a mix
					int direction = MetaLinkGroup.DOWN;
					if (wug.isParent()) { 
						direction = MetaLinkGroup.UP;
					}
					MetaLink ml = wList.getParentEntityGroup().getMetaLinkGroup().getMetaLink(direction, wug.getEntityType());   
					if(ml!=null){
						validTypesVct.add(ml.getEntity1Type());
						validTypesVct.add(ml.getEntity2Type());
						break;
					}
				}
			}
		}
		return validTypesVct;
	}
	/* (non-Javadoc)
	 * called when tab is selected
	 * @see com.ibm.eacm.tabs.ActionTabPanel#select()
	 */
	public void select() {
		super.select();
		cart.setCartable(this); // tell cart to use this for data selection
		if(pickFrame == null){ // pickframe is not open
			cart.updateActions();
		}else{
			getAction(ADD2CART_ACTION).setEnabled(false); // will still be enabled from last tab
		}
	}

	/**
	 * does this tab contain this data
	 * @param prof
	 * @param ei
	 * @param eai
	 * @return
	 */
	public boolean tabExistsWithAll(Profile prof, EntityItem[] ei, EANActionItem eai){
		if(Utils.isPast(getProfile())){
			return false;
		}
		if(prof.getEnterprise().equals(getProfile().getEnterprise()) &&
				prof.getOPWGID()== getProfile().getOPWGID()){
			return wList.equivalent(ei, eai);
		}
		return false;
	}
	/**
	 * does this tab contain some of this data
	 * @param prof
	 * @param ei
	 * @param eai
	 * @return
	 */
	public boolean tabExistsWithSome(Profile prof, EntityItem[] ei, EANActionItem eai){
		if(Utils.isPast(getProfile())){
			return false;
		}
		if(prof.getEnterprise().equals(getProfile().getEnterprise()) &&
				prof.getOPWGID()== getProfile().getOPWGID()){
			return wList.subset(ei, eai);
		}
		return false;
	}

	/* (non-Javadoc)
	 * remove pickframe listener
	 * @see com.ibm.eacm.tabs.DataActionPanel#close()
	 */
	public boolean canClose() {
		//this is not interruptable.. tell user data may be corrupted if cancelled, confirm it
		boolean ok = linkAction.canClose();
		if (ok){
			ok = linkChainAction.canClose();
			if (ok){
				ok = super.canClose();
			}
		}
		if(ok){
			if(pickFrame !=null){
				pickFrame.removeWindowListener(pickFrameListener);
				pickFrameListener = null;
				pickFrame = null;
			}
		}

		return ok;
	}

	/* (non-Javadoc)
	 * used by base class for enabling column listener
	 * @see com.ibm.eacm.tabs.TabPanel#getJTable()
	 */
	protected BaseTable getJTable() { return wTable;}

	/**
	 * get items used as source for whereused action
	 * @return
	 */
	public EntityItem[] getOriginalEntityItemAsArray() {
		WhereUsedItem[] items = wTable.getWhereUsedItems();
		EntityItem[] ei = new EntityItem[items.length];
		for (int i = 0; i < items.length; ++i) {
			ei[i] = items[i].getOriginalEntityItem();
		}
		return ei;
	}

	/* (non-Javadoc)
	 * release memory
	 * @see com.ibm.eacm.tabs.ActionTabPanel#dereference()
	 */
	public void dereference(){
		super.dereference();

		pickFrame = null;
		pickFrameListener = null;

		prevRelatorKey = null;
		actions = null;

		tScroll.removeAll();
		tScroll.setUI(null);
		tScroll = null;

		if (cartFrame != null) {
			cartFrame.dereference();
			cartFrame = null;
		}

		wuiActionTbl.clear();
		wuiActionTbl = null;

		cart = null;
	}
	/**
	 * dereference data needed by workers, if worker is running, it must call this when done
	 */
	public void derefWorkerData(){
		super.derefWorkerData();

		linkAction.dereference();
		linkAction = null;
		linkChainAction.dereference();
		linkChainAction = null;

		wList.dereference();
		wList = null;

		wTable.getColumnModel().removeColumnModelListener(this);
		wTable.getSelectionModel().removeListSelectionListener(this);
		wTable.dereference();
		wTable = null;
	}

	/* (non-Javadoc)
	 * tab was closed, if a non-interruptable worker is running, cant deref worker data now
	 * @see com.ibm.eacm.tabs.TabPanel#canDerefWorker()
	 */
	protected boolean canDerefWorker(){
		boolean deref = linkAction.canDerefWorker();
		if (deref){
			deref = linkChainAction.canDerefWorker();
			if (deref){
				deref = super.canDerefWorker();
			}
		}

		return deref;
	}
	/* (non-Javadoc)
	 * when row selection changes this is notified, not column selection chgs
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent lse) {
		if (!lse.getValueIsAdjusting()) {
			if (isWaiting()){ // a link may have been added, so valuechanged would call this but
				// linkdialog is still open
				return;
			}
			boolean rowSelected = wTable.getSelectedRow()!=-1;
			if (rowSelected) {
				loadActions();
				getActionTree().setEnabled(true);
			}else{
				// disable all EANxx actions, they will be restored when an entity is selected
				disableEANActions();
			}
			//enable actions based on selection
			enableTableActions();
		}
	}
	/* (non-Javadoc)
	 * called when nothing is selected
	 * @see com.ibm.eacm.tabs.DataActionPanel#disableEANActions()
	 */
	protected void disableEANActions() {
		super.disableEANActions();
		getActionTree().setEnabled(false);
	}

	/* (non-Javadoc)
	 * enable actions based on selection
	 * @see com.ibm.eacm.tabs.DataActionPanel#enableTableActions()
	 */
	protected void enableTableActions(){
		boolean b = wTable.getSelectedRowCount()==1;
		super.enableTableActions();
		getAction(SHOWRELHIST_ACTION).setEnabled(b);

		cart.updateActions();// this will enable/disable the ADD2CART_ACTIONs
	}

	/**
	 * get all actions for this relator type
	 * cache them locally to prevent lookup, if first time, it goes to pdh, after that it is cached in objectpool
	 * local cache prevents that lookup
	 * @param wui
	 * @return
	 */
	private EANActionItem[] getActionsForRelatorType(WhereUsedItem wui){
		EANActionItem[] allActions = null;
		if(wui != null){
			String curKey = null;
			EntityItem relator = wui.getRelatorEntityItem();
			if (relator != null) {
				curKey = relator.getEntityType() + wui.getDirection();
			}
			String key = wui.getKey();

			String actionkey = curKey;
			if(actionkey==null){
				actionkey = key;
			}
			allActions = (EANActionItem[])wuiActionTbl.get(actionkey);//use hashtbl as local cache, dont get them over and over
			if(allActions==null){
				allActions=getAllActions(getActionItemsAsArray(key,wui));
				if(allActions!=null){
					wuiActionTbl.put(actionkey,allActions);
				}
			}
		}

		return allActions;
	}
	/**
	 * loadActions based on first selected row
	 */
	private void loadActions() {
		WhereUsedItem wui = wTable.getSelectedWhereUsedItem();

		String curKey = null;
		EntityItem relator = null;
		if(wui != null){
			relator = wui.getRelatorEntityItem();
		}
		if (relator != null) {
			curKey = relator.getEntityType() + wui.getDirection();
		}

		getActionTree().clear(); // clear all actions from previous selection

		// did the relatortype+direction change, or existance of data for same type, then get new actions
		boolean relatorTypeChg = (curKey == null || !curKey.equals(prevRelatorKey));
		boolean curRelExist = wTable.selectionHasActionEntities();//wTable.selectionHasEntities()
		boolean existanceChg = (!relatorTypeChg && (curRelExist!=prevRelatorExisted));
		if(relatorTypeChg ||  // diff relatortype+direction
				existanceChg){ // same relatortype+direction but existance of linked entities has changed
			actions = getActionsForRelatorType(wui);
			prevRelatorKey = curKey;
			prevRelatorExisted = curRelExist;
		}

		if (actions != null && !curRelExist) {
			Vector<EANActionItem> v = new Vector<EANActionItem>();
			// limit the set of possible actions if the selected rows do not have entities
			for (int i=0;i<actions.length;++i) {
				if (actions[i] instanceof CreateActionItem) {
					v.add(actions[i]);
				} else if (actions[i] instanceof LinkActionItem) {
					v.add(actions[i]);
				} else if (actions[i] instanceof SearchActionItem) {
					v.add(actions[i]);
				} else if (actions[i] instanceof NavActionItem) {
					if (((NavActionItem)actions[i]).isPicklist()) {
						v.add(actions[i]);
					}
				}
			}
			if (v.isEmpty()) {
				actions = null;
			} else {
				actions = (EANActionItem[])v.toArray(new EANActionItem[v.size()]);
				v.clear();
			}
		}

		getActionTree().load(actions,getTableTitle());

		refreshActions();
	}
	/**
	 * used by the WU actions for enabling
	 * @return
	 */
	public boolean hasData(){
		return wTable.getRowCount()>0 && wTable.getColumnCount()>0;
	}
	/**
	 * is there a related entity in the first selected row
	 * @return
	 */
	public boolean hasEntity(){
		return wTable.hasEntity();
	}
	/**
	 * create all of the actions, they are shared between toolbar and menu
	 */
	public void createActions() {
		super.createActions();
		addAction(new FindRepAction());
		addAction(new FindNextAction());

		addAction(new AddSelected2CartAction(cart));
		addAction(new EANCreateActionSet(this));
		addAction(new EANEditActionSet(this));
		addAction(new EANWuActionSet(this));

		//WULINK_ACTIONSET
		addAction(new EANLinkActionSet(this));

		addAction(new EANRemoveLinkActionSet(this));
		addAction(new EANMtrxActionSet(this));
		addAction(new ShowCartAction(this));

		createTableActions();

		linkAction = new LinkAction(this); // do not add to actiontable, this is used by the pickframe
		linkChainAction = new LinkChainAction(this);
	}

	private void createTableActions() {
		super.createTableActions(wTable);
		addAction(new ShowRelHistoryAction(this,wTable)); //	SHOWRELHIST_ACTION
	}
	private void createMenus() {
		createFileMenu();
		createEditMenu();
		createActionMenu();
		createTableMenu();
	}
	private void createTableMenu() {
		JMenu tblMenu = new JMenu(Utils.getResource(TABLE_MENU));
		tblMenu.setMnemonic(Utils.getMnemonic(TABLE_MENU));

		addLocalActionMenuItem(tblMenu, MOVECOL_LEFT_ACTION);
		addLocalActionMenuItem(tblMenu, MOVECOL_RIGHT_ACTION);
		tblMenu.addSeparator();

		addLocalActionMenuItem(tblMenu, SORT_ACTION);
		tblMenu.addSeparator();

		addLocalActionMenuItem(tblMenu, HIDECOL_ACTION);
		addLocalActionMenuItem(tblMenu, UNHIDECOL_ACTION);

		tblMenu.addSeparator();

		addLocalActionMenuItem(tblMenu, FINDREP_ACTION);
		addLocalActionMenuItem(tblMenu, FINDNEXT_ACTION);

		addLocalActionMenuItem(tblMenu, FILTER_ACTION);
		tblMenu.addSeparator();
		addLocalActionMenuItem(tblMenu, SHOWHIST_ACTION);

		addLocalActionMenuItem(tblMenu, SHOWRELHIST_ACTION);
		addLocalActionMenuItem(tblMenu, ENTITYDATA_ACTION);

		getMenubar().add(tblMenu);

		tblMenu.setEnabled(wTable.getRowCount()>0);
	}
	private void createEditMenu() {
		JMenu editMenu = new JMenu(Utils.getResource(EDIT_MENU));
		editMenu.setMnemonic(Utils.getMnemonic(EDIT_MENU));

		addLocalActionMenuItem(editMenu, SELECTALL_ACTION);

		addLocalActionMenuItem(editMenu, SELECTINV_ACTION);

		getMenubar().add(editMenu);
	}
	private void createActionMenu() {
		JMenu actMenu = new JMenu(Utils.getResource(ACTIONS_MENU));
		actMenu.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));

		addLocalActionMenuItem(actMenu, WUCRT_ACTIONSET);

		addLocalActionMenuItem(actMenu, WUEDIT_ACTIONSET);
		addLocalActionMenuItem(actMenu, WU_ACTIONSET);

		actMenu.addSeparator();
		addLocalActionMenuItem(actMenu, WULINK_ACTIONSET);

		addLocalActionMenuItem(actMenu, WURLINK_ACTIONSET);

		if (getActionTree().actionExists(ACTION_PURPOSE_MATRIX)) {
			addLocalActionMenuItem(actMenu, WUMTRX_ACTIONSET);
		}

		actMenu.addSeparator();

		addLocalActionMenuItem(actMenu, ADD2CART_ACTION);

		addLocalActionMenuItem(actMenu, SHOWCART_ACTION);

		getMenubar().add(actMenu);
	}

	private EANActionItem[] getAllActions(EANActionItem[] _child) {
		EANActionItem[] allActions = null;
		EANActionItem[] _parent = getParentActionItems();
		if (_parent != null && _child != null) {
			int pp = _parent.length;
			int cc = _child.length;
			int aa = pp + cc;
			EANActionItem[] out = new EANActionItem[aa];
			System.arraycopy(_parent, 0, out, 0, pp);
			System.arraycopy(_child, 0, out, pp, cc);
			allActions = out;
		} else if (_parent != null) {
			allActions= _parent;
		} else if (_child != null) {
			allActions= _child;
		}

		return allActions;
	}

	private EANActionItem[] getParentActionItems() {
		EANActionItem[] out = null;

		EntityGroup peg = wList.getParentEntityGroup();
		if (peg != null) {
			ActionGroup pag = peg.getActionGroup();
			if (pag != null) {
				out = new EANActionItem[pag.getActionItemCount()];
				for (int i = 0; i < pag.getActionItemCount(); ++i) {
					out[i] = pag.getActionItem(i);
				}
			}
		}

		return out;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.ActionTreeTabPanel#disableActionsAndWait()
	 */
	 public void disableActionsAndWait(){
		super.disableActionsAndWait();
		wTable.setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.ActionTreeTabPanel#enableActionsAndRestore()
	 */
	 public void enableActionsAndRestore(){
		 super.enableActionsAndRestore();
		 if(isWaiting()){ // all workers have not completed
			 return;
		 }
		 wTable.setEnabled(true);
	 }

	 /* (non-Javadoc)
	  * @see com.ibm.eacm.tabs.TabPanel#popupCart()
	  */
	 public void popupCart() {
		 if (cartFrame==null){
			 cartFrame = NavCartFrame.getNavCart(cart);
		 }
		 cart.updateActions();
		 cartFrame.setVisible(true);
	 }
	 /**
	  * called to disable/enable action based on selected row
	  * actions in the tree were set based on the first whereuseditem selected
	  */
	 public void refreshActions() {
		 if(wTable==null){ // deref may have run before worker was done
			 return;
		 }
		 if (isWaiting()){ // a link may have been added, so valuechanged would call this but
			 // linkdialog is still open
			 return;
		 }

		 EANActionItem[] eai = getActionTree().getActionItemArray(ACTION_PURPOSE_CREATE);
		 String[] actRA = { ACTION_PURPOSE_NAVIGATE, ACTION_PURPOSE_SEARCH };
		 EANActionSet actionSet = ((EANActionSet)getAction(WUCRT_ACTIONSET));
		 if(actionSet!=null){
			 actionSet.updateEANActions(eai);
		 }

		 eai = getActionTree().getActionItemArray(ACTION_PURPOSE_EDIT);

		 actionSet = ((EANActionSet)getAction(WUEDIT_ACTIONSET));
		 if(actionSet!=null){
			 actionSet.updateEANActions(eai);
		 }

		 eai = getActionTree().getActionItemArray(ACTION_PURPOSE_WHERE_USED);

		 actionSet = ((EANActionSet)getAction(WU_ACTIONSET));
		 if(actionSet!=null){
			 actionSet.updateEANActions(eai);
		 }

		 eai = getActionTree().getActionItemArray(actRA);
		 actionSet = ((EANActionSet)getAction(WULINK_ACTIONSET));
		 if(actionSet!=null){
			 actionSet.updateEANActions(eai);
		 }

		 eai = getActionTree().getActionItemArray(ACTION_PURPOSE_DELETE);
		 actionSet = ((EANActionSet)getAction(WURLINK_ACTIONSET));
		 if(actionSet!=null){
			 actionSet.updateEANActions(eai);
		 }

		 eai = getActionTree().getActionItemArray(ACTION_PURPOSE_MATRIX);

		 actionSet = ((EANActionSet)getAction(WUMTRX_ACTIONSET));
		 if(actionSet!=null){
			 actionSet.updateEANActions(eai);
		 }

		 // 	getAction(ADD2CART_ACTION).setEnabled(wTable.selectionHasEntities()); action checks this now when setenabled(true)

		 getAction(FILTER_ACTION).setEnabled(wTable.getRowCount()>0);

		 getAction(SHOWHIST_ACTION).setEnabled(wTable.getRowCount()>0);
	 }

	 public ComboItem getDefaultToolbarLayout() {
		 return DefaultToolbarLayout.USED_BAR_TABLE;
	 }
	 public String getHelpText() {
		 return wTable.getHelpText();
	 }
	 public Profile getProfile() {
		 return wList.getProfile();
	 }
	 public String getTableTitle() {
		 return wTable.getTableTitle();
	 }
	 public String getTabMenuTitleKey() { return "whereused.title";}
	 protected String getTabIconKey() {		return "whereused.icon";}
	 // Cartable interface
	 public EntityGroup getSelectedEntityGroup(){
		 return null;
	 }
	 public EntityItem[] getAllEntityItems() {
		 return null;
	 }
	 public boolean supportsGetAll() { return false;} // disable add all action without having to get all
	 public boolean hasCartableData() {
		 return !isPast() && wTable.hasAnyEntities();
	 }
	 /* (non-Javadoc)
	  * @see com.ibm.eacm.cart.Cartable#getSelectedEntityItems()
	  */
	 public EntityItem[] getSelectedEntityItems(boolean showException) {
		 EntityItem[] eai = null;
		 int[] rows = wTable.getSelectedRows(); // indexes are from table view
		 Vector<EntityItem> vct = new Vector<EntityItem>();
		 // only get the rows with a related entity
		 for (int i=0;i<rows.length;++i) {
			 EntityItem rel = wTable.getRelatedEntityItem(rows[i]);
			 if (rel !=null){
				 vct.add(rel);
			 }
		 }

		 if (!vct.isEmpty()){
			 eai = new EntityItem[vct.size()];
			 vct.copyInto(eai);
			 vct.clear();
		 }

		 return eai;
	 }

	 /**
	  * has this profile been dialed back?  check needed to enable some actions
	  * @return
	  */
	 public boolean isPast(){
		 return Utils.isPast(getProfile());
	 }
	 //=====================================================================
	 // Actions
	 public void refresh() {// never tested

		 WhereUsedActionItem wuai = wList.getParentActionItem();
		 if (wuai != null) {
			 EntityGroup peg = wList.getParentEntityGroup();
			 if (peg != null) {
				 EntityItem[] ei = peg.getEntityItemsAsArray();
				 Profile prof = LoginMgr.getNewProfileInstance(getProfile());
				 if(prof!=null){
					 wuai.setEntityItems(ei);
					 WhereUsedList tmpList = DBUtils.getWUList(wuai, prof, this);
					 if (tmpList != null) {
						 int row = wTable.getSelectedRow();
						 RowSelectableTable rst = tmpList.getTable();
						 wTable.updateModel(rst, this.getProfile());
						 //wTable.sort();
						 wTable.refreshTable(false);
						 //wTable.reselectActions(row);


						 prevRelatorKey = null; // reset current key to force reload of actions, some lost after a create


						 wTable.setRowSelectionInterval(row,row);
						 loadActions();

						 wList.dereference();
						 wList = tmpList;
					 }
				 }
			 }
		 }

	 }

	 /**
	  * this might go to the db if not cached, it will be on the event thread
	  * get all the EANActionItems for this WhereUsedItem
	  * @param key
	  * @return
	  */
	 private EANActionItem[] getActionItemsAsArray(String key,WhereUsedItem wui) {
		 EANActionItem[] out = wTable.getActionItemsAsArray(key);
		 if (out == null || out.length==0) {
			 //msg23007.2 = No actions available for {0}.
			 //WhereUsedItem wui = wTable.getSelectedWhereUsedItem();
			 //com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg23007.2",
			 //	wui.get(WhereUsedItem.RELATEDENTITYTYPE, true)));
			 Logger.getLogger(WU_PKG_NAME).log(Level.SEVERE,"No actions available for "+wui.get(WhereUsedItem.RELATEDENTITYTYPE, true));
			 out = null; // make sure
		 }
		 return out;
	 }

	 /**
	  * called from link button or menu item
	  * this is on the bg thread from the EANLinkNavAction worker
	  * @param nav
	  */
	 public EntityList getPicklist() {
		 String key = wTable.getEANKey(wTable.getSelectedRow());
		 Long t11 =System.currentTimeMillis();
		 Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+key);

		 int row = getRSTable().getRowIndex(key);
		 int col = getRSTable().getColumnIndex(key);

		 EntityList eList=null;
		 if (row >= 0) {
			 eList= generatePicklist(row);
		 }else if (col >= 0) {
			 eList= generatePicklist(col);
		 }


		 Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"picklistnav ended "+Utils.getDuration(t11));
		 return eList;
	 }

	 /**
	  * get the entitylist to use as a picklist
	  * @param row
	  * @return
	  */
	 private EntityList generatePicklist(int row) {
		 EntityList list = null;
		 try {
			 list = getRSTable().generatePickList(row, null, ro(), getProfile(), false);
		 } catch (Exception ex) {
			 if(RMIMgr.shouldTryReconnect(ex) && // try to reconnect
					 RMIMgr.getRmiMgr().reconnectMain()){
				 try {
					 list = getRSTable().generatePickList(row, null, ro(), getProfile(), false);
				 } catch (Exception x) {
					 com.ibm.eacm.ui.UI.showException(this,x, "mw.err-title");
				 }
			 } else {
				 com.ibm.eacm.ui.UI.showException(this,ex, "mw.err-title");
			 }
		 }

		 return list;
	 }

	 /**
	  * called from searchframe, and whereused EANNavLinkAction or EANLinkSrchAction
	  * @param list
	  */
	 public void showPicklist(EntityList eList) {
		 pickFrame = new WUPickFrame(this, eList);
		 // this is needed because user may close it while worker is running

		 pickFrameListener = new WindowAdapter(){
			 public void windowClosed(WindowEvent e) {
				 pickFrame.removeWindowListener(this);
				 pickFrame = null;
				 linkAction.setPickFrame(pickFrame);
			 }
			 public void windowClosing(WindowEvent e) {
				 pickFrame.removeWindowListener(this);
				 pickFrame = null;
				 linkAction.setPickFrame(pickFrame);
			 }
		 };
		 pickFrame.addWindowListener(pickFrameListener);

		 pickFrame.setVisible(true);
	 }

	 //====================================================
	 // used for whereused
	 protected int[] getSelectedModelRowIndexes(){
		 return wTable.getSelectedModelRowIndexes();
	 }
	 protected String[] getSelectedKeys(){
		 return wTable.getSelectedKeys();
	 }
	 protected EntityItem getRelatedItemForMdlIndex(int mdlid){
		 return wTable.getRelatedItemForMdlIndex(mdlid);
	 }
	 protected EntityItem[] getRelatedItemForMdlIndexes(int mdlid[]){
		 return wTable.getRelatedItemForMdlIndexes(mdlid);
	 }
	 //====================================================
	 // used for edit action
	 protected RowSelectableTable getRSTable() {
		 return wTable.getRSTable();
	 }
	 /**
	  * find all selected rows that can be edited
	  * @return
	  */
	 protected int[] getEditRowIndexes(boolean editable){
		 int[] rows = null;
		 Vector<String> vct = new Vector<String>();
		 boolean allSame = wTable.getMatchingSelectedKeys(vct);

		 if (!allSame) {
			 //msg5023.0 = Can not {0} different entity types with one Action.
			 com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg5023.0",
					 editable?"Edit":"View"));
		 }else{
			 if (!vct.isEmpty()) {
				 String[] keys = new String[vct.size()];
				 vct.copyInto(keys);
				 vct.clear();
				 rows = wTable.getRowIndexes(keys);
			 }else{
				 //msg5022.0 = Some Selected Items, were not valid for action selected.
				 com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg5022.0"));
			 }
		 }

		 return rows;
	 }

	 //====================================================
	 // used for removelink - done on bg thread
	 protected boolean removeLinks(String[] validKeys){
		 return wTable.removeLink(getProfile(),validKeys);
	 }

	 /**
	  * done on dispatch thread
	  * called by removelink action, look at all selected rows and find valid 'deletes'
	  * @param purpose
	  * @return
	  */
	 protected String[] getKeysToRemove(String purpose){
		 // get keys that can be removed, they must be the same type as the first one selected or must have a delete action
		 WhereUsedItem wui = wTable.getSelectedWhereUsedItem();//get the WhereUsedItem for the first selected row
		 String curKey = "";
		 EntityItem relator = wui.getRelatorEntityItem();
		 if (relator != null) {
			 curKey = relator.getEntityType() + wui.getDirection();
		 }

		 int[] rows = wTable.getSelectedRows(); // view indexes
		 Vector<String> vct = new Vector<String>();
		 for (int i = 0; i < rows.length; ++i) {
			 int viewRowIndex = rows[i];
			 WhereUsedItem otherwui = wTable.getWhereUsedItem(viewRowIndex);
			 relator = otherwui.getRelatorEntityItem();
			 String otherkey=null;
			 if (relator != null) {
				 otherkey = relator.getEntityType() + otherwui.getDirection();
			 }

			 if(curKey.equals(otherkey)){ // same type, so use it
				 if(wTable.hasEntity(viewRowIndex)){
					 vct.add(wTable.getEANKey(viewRowIndex));
				 }
			 }else{
				 // does it have an entity?
				 if(wTable.hasEntity(viewRowIndex)){
					 // does it have the same action available?
					 EANActionItem[] otherActions = getActionsForRelatorType(otherwui);
					 if(otherActions!=null){
						 for (int x=0; x<otherActions.length; x++){
							 if(EANActionMap.getPurpose(otherActions[x]).equals(purpose)){
								 vct.add(wTable.getEANKey(viewRowIndex));
							 }
						 }
					 }
				 }
			 }
		 }
		 String[] keys = new String[vct.size()];
		 vct.copyInto(keys);

		 return wTable.validateKeys(keys);
	 }

	 /**
	  * done on evt dispatch thread
	  */
	 protected void updateTable(){
		 wTable.updateTable();
		 loadActions();
		 getActionTree().setEnabled(true);
	 }
	 //=========================================================================
	 /**
	  * called by wupickframe.linkaction on the evt dispatch thread
	  * @param dataItems
	  */
	 protected void link(EntityItem[] dataItems){
		 linkAction.setPickFrame(pickFrame);
		 linkAction.link(dataItems);
	 }

	 /**
	  * called on the bg thread from linkaction
	 * @param eia
	 * @param comp
	 * @return
	 */
	protected EANFoundation[] dolink(EntityItem[] eia,Component comp){
		 return wTable.link(eia, this.getProfile(), comp);
	 }
	 /**
	  * called on the evt thread from linkaction
	  * @param ean
	  */
	 protected void updateTable(EANFoundation ean){
		 wTable.updateTable(ean);
		 // update actions also
		 loadActions();
		 getActionTree().setEnabled(true);
	 }


	 //====================================================================
	 // completely untested!!!!
	 protected void linkChain(EntityItem[] selItems,LinkActionItem lai){
		 linkChainAction.linkChain(selItems, lai);
	 }
	 protected Object linkChain(LinkActionItem lai, EntityItem[] parent, EntityItem[] child) {
		 return wTable.linkChain(lai, parent, child, this.getProfile(), this);
	 }

}
