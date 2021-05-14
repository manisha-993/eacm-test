//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.tabs;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;


import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.FindNextAction;
import com.ibm.eacm.actions.FindRepAction;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.Utils;

import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.toolbar.ComboItem;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;

import com.ibm.eacm.table.LockTable;



/**
 * tab for LockAction
 * @author Wendy Stimpson
 */
//$Log: LockActionTab.java,v $
//Revision 1.3  2013/09/24 17:33:26  wendy
//prevent null ptr if attr no longer has a parent
//
//Revision 1.2  2013/02/07 13:37:37  wendy
//log close tab
//
//Revision 1.1  2012/09/27 19:39:23  wendy
//Initial code
//
public class LockActionTab extends ActionTabPanel {
	private static final long serialVersionUID = 1L;
	private LockTable table = null;
	private JScrollPane jsp = null;

	private boolean bAllWGLock = false;
	private LockList locklist = null;

	/* (non-Javadoc)
	 * used by base class for enabling column listener
	 * @see com.ibm.eacm.tabs.TabPanel#getJTable()
	 */
	protected BaseTable getJTable() { return table;}

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		return "Locklist: "+getProfile();
	}
	
	public Findable getFindable() {
		return table;
	}

	public ComboItem getDefaultToolbarLayout() {
		return DefaultToolbarLayout.LOCK_BAR;
	}

	public LockActionTab(boolean lockall, Profile profile) throws MiddlewareRequestException, MiddlewareException
	{
		setSessionTagText("N/A");

		locklist = new LockList(
				profile
				//ELogin.getEACM().getActiveProfile()
				);  // get an empty table
		bAllWGLock = lockall;

		table = new LockTable(locklist.getTable(),getProfile());
		table.addMouseListener(new MouseAdapter() { // base class deref will remove this
            public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    popup.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
		});
		table.getSelectionModel().addListSelectionListener(this);

		init();

		getAction(REFRESH_ACTION).actionPerformed(null); // populate the table
	}

	private void init() {
		createActions();
		createPopupMenu();
		createMenus();
		createToolbar();

		jsp = new JScrollPane(table);
		getAction(UNLOCK_ACTION).setEnabled(table.getRowCount() > 0);

		JPanel tPnl = new JPanel(new BorderLayout());
		tPnl.add(jsp,BorderLayout.CENTER);

		tPnl.add(getToolbar().getAlignment(), getToolbar());

		add(tPnl,BorderLayout.CENTER);

	    table.getColumnModel().addColumnModelListener(this); // base class has listener methods

		if (table.getRowCount() > 0) {
			table.setColumnSelectionInterval(0,0);
			table.setRowSelectionInterval(0,0);
		}
		//enable actions based on selection
		enableTableActions();
	}
	private void createMenus() {
		createFileMenu();
		createEditMenu();
		createActionMenu();
		createTableMenu();
	}
	private void createEditMenu() {
		JMenu editMenu = new JMenu(Utils.getResource(EDIT_MENU));
		editMenu.setMnemonic(Utils.getMnemonic(EDIT_MENU));

		addLocalActionMenuItem(editMenu, FINDREP_ACTION);
		addLocalActionMenuItem(editMenu, FINDNEXT_ACTION);

		getMenubar().add(editMenu);
	}

	/**
	 * create all of the actions, they are shared between toolbar and menu
	 */
	public void createActions() {
		super.createActions();
	  	addAction(new FindRepAction());
		addAction(new FindNextAction());

		createTableActions(table);
		EACMAction act = new RefreshAction();
		addAction(act);

		addAction(new UnlockAction());
	}

	private void createActionMenu() {
		JMenu actMenu = new JMenu(Utils.getResource(ACTIONS_MENU));
		actMenu.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));

		addLocalActionMenuItem(actMenu, UNLOCK_ACTION);

		actMenu.addSeparator();
		addLocalActionMenuItem(actMenu, REFRESH_ACTION);

		actMenu.addSeparator();
		addLocalActionMenuItem(actMenu, SELECTALL_ACTION);
		addLocalActionMenuItem(actMenu, SELECTINV_ACTION);

		getMenubar().add(actMenu);
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

		addLocalActionMenuItem(tblMenu, FILTER_ACTION);

		getMenubar().add(tblMenu);
	}

	/**
	 * check to see if a lockaction tab is already open for this profile
	 * @param _b
	 * @return
	 */
	public boolean viewLockExist(boolean _b) {
		if (bAllWGLock == _b) {
			Profile myProf = getProfile();
			Profile actProf = EACM.getEACM().getCurrentTab().getProfile();
			if (myProf != null && actProf != null) {
				if (myProf.getEnterprise().equals(actProf.getEnterprise())) {
					if (myProf.getOPWGID() == actProf.getOPWGID()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * dereference
	 */
	public void dereference() {
		locklist.dereference();
		locklist = null;

		table.getSelectionModel().removeListSelectionListener(this);
	    table.getColumnModel().addColumnModelListener(this); // base class has listener methods

		table.dereference();
		table = null;

		jsp.removeAll();
		jsp.setUI(null);
		jsp = null;

		super.dereference();
	}
	public String getTableTitle() {
		return "lockActionTable";
	}

	/**
	 * getProfile
	 * @return Profile
	 */
	public Profile getProfile() {
		return locklist.getProfile();
	}

	protected String getTabMenuTitleKey() {
		String key = null;
		if (bAllWGLock) {
			key = "lock.wg.title";
		} else {
			key = "lock.title";
		}
		return key;
	}
	protected String getTabIconKey() {
		String icon = null;
		if (bAllWGLock) {
			icon = "lock.wg.icon";
		}else{
			icon = "lock.icon";
		}
		return icon;
	}

	 /**
	  * enable actions based on current selection
	  */
	 public void refreshActions() {
		 if(table==null){ // deref may have run before worker was done
			 return;
		 }
		 boolean rowSelected = table.getSelectedRow()!= -1;
		 getAction(UNLOCK_ACTION).setEnabled(rowSelected);
		 //enable actions based on table state
		 enableTableActions();
	 }

	 //================================================================
	 // refresh table
	 private RefreshWorker refreshWorker=null;
	 private class RefreshAction extends EACMAction
	 {
		 private static final long serialVersionUID = 1L;
		 RefreshAction() {
			 super(REFRESH_ACTION,KeyEvent.VK_F5, 0);
			 putValue(javax.swing.Action.LARGE_ICON_KEY, Utils.getImageIcon("refresh.gif"));
		 }

		 public void actionPerformed(ActionEvent e) {
			 table.clearSelection();
			 // need to do this in the tab
			 disableActionsAndWait();
			 refreshWorker = new RefreshWorker();
			 RMIMgr.getRmiMgr().execute(refreshWorker);
		 }
	 }
	 private class RefreshWorker extends DBSwingWorker<LockList, Void> {
		 @Override
		 public LockList doInBackground() {
			 LockList ll=null;
			 try{
				 if (bAllWGLock) {
					 ll = getAllSoftLocksForWGID();
				 } else {
					 ll = getLockList();
				 }
			 }catch(Exception ex){ // prevent hang
	    		Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
			 }finally{
				 RMIMgr.getRmiMgr().complete(this);
				 refreshWorker = null;
			 }
			 return ll;
		 }

		 @Override
		 public void done() {
			 //this will be on the dispatch thread
			 try {
				 if(!isCancelled()){
					 LockList ll = get();
					 if (ll !=null){
						 locklist=ll;
						 table.updateModel(ll.getTable(), LockActionTab.this.getProfile());
						 getAction(UNLOCK_ACTION).setEnabled(table.getRowCount() > 0);
					 }
				 }
			 } catch (InterruptedException ignore) {}
			 catch (java.util.concurrent.ExecutionException e) {
				 listErr(e,"getting locklist");
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
	 private LockList getLockList() {
		 LockList ll = null;
		 try {
			 ll = ro().getLockList(
					 getProfile(),
					 //ELogin.getEACM().getActiveProfile(),
					 true);
		 } catch(Exception exc){
			 if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				 if (RMIMgr.getRmiMgr().reconnectMain()) {
					 try{
						 ll = ro().getLockList(
								 LockActionTab.this.getProfile(),
								 //ELogin.getEACM().getActiveProfile(),
								 true);
					 }catch(Exception e){
						 if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							 if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, e) == RETRY) {
								 ll = getLockList();
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
						 ll = getLockList();
					 }// else user decide to ignore or exit
				 }else{
					 com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
				 }
			 }
		 }

		 return ll;
	 }

	 /**
	  * getAllSoftLocksForWGID
	  */
	 private LockList getAllSoftLocksForWGID() {
		 LockList ll = null;
		 try {
			 ll = ro().getAllSoftLocksForWGID(
					 getProfile()
					 //ELogin.getEACM().getActiveProfile()
					 );
		 } catch(Exception exc){
			 if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				 if (RMIMgr.getRmiMgr().reconnectMain()) {
					 try{
						 ll = ro().getAllSoftLocksForWGID(LockActionTab.this.getProfile());//
								 //ELogin.getEACM().getActiveProfile());
					 }catch(Exception e){
						 if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							 if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, e) == RETRY) {
								 ll = getAllSoftLocksForWGID();
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
						 ll = getAllSoftLocksForWGID();
					 }// else user decide to ignore or exit
				 }else{
					 com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
				 }
			 }
		 }
		 return ll;
	 }
	 //========================================================================
	 private UnlockWorker unlockWorker=null;
	 private class UnlockAction extends EACMAction
	 {
		 private static final long serialVersionUID = 1L;
		 UnlockAction() {
			 super(UNLOCK_ACTION,KeyEvent.VK_U, Event.CTRL_MASK);
			 putValue(javax.swing.Action.LARGE_ICON_KEY, Utils.getImageIcon("ulck.gif"));
		 }

		 public void actionPerformed(ActionEvent e) {
			 // need to do this in the tab
			 disableActionsAndWait();
			 unlockWorker = new UnlockWorker();
			 RMIMgr.getRmiMgr().execute(unlockWorker);
		 }
	 }
	 private class UnlockWorker extends DBSwingWorker<Boolean, Void> {
		 @Override
		 public Boolean doInBackground() {
			 Boolean out=false;
			 try{
				 LockItem[] li = table.getLockItems();
				 out = unlock(li);
				 for(int i=0; i<li.length; i++){
					 String lockGrpKey = li[i].getLockGroup().getKey();
					 // rollback any changes so UI reflects proper lock state
					 Vector<EANFoundation> lockedAttVct = EACM.getEACM().getLockMgr().getLockGrpKeyVct(lockGrpKey);
					 if(lockedAttVct!=null){
						 while(lockedAttVct.size()>0){
							 EANFoundation ean = lockedAttVct.remove(0);

							 if (ean instanceof MatrixItem){
								 ((MatrixItem)ean).rollback();
							 }else if (ean instanceof EANAttribute){
								 EANAttribute att = (EANAttribute)ean;
								 if(att.getParent()!=null){
									 ((EntityItem)att.getParent()).rollback(att);
								 }
							 }
						 }
						 EACM.getEACM().getLockMgr().clearLockGrpKey(lockGrpKey);
					 }
				 }
			 }catch(Exception ex){ // prevent hang
	    		Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
			 }finally{
				 RMIMgr.getRmiMgr().complete(this);
				 unlockWorker = null;
			 }
			 return out;
		 }

		 @Override
		 public void done() {
			 //this will be on the dispatch thread
			 try {
				 if(!isCancelled()){
					 Boolean out = get();
					 if (out){
						 table.updateModel(locklist.getTable(), LockActionTab.this.getProfile());
					 }
				 }
			 } catch (InterruptedException ignore) {}
			 catch (java.util.concurrent.ExecutionException e) {
				 listErr(e,"UnlockWorker ");
			 }finally{
				 enableActionsAndRestore();
			 }
		 }
		 public void notExecuted(){
			 Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
			 enableActionsAndRestore();
			 unlockWorker = null;
		 }
	 }
	 private boolean unlock(LockItem[] _li) {
		 boolean ok = false;
		 try {
			 locklist.removeLockItem(null, ro(),
					 getProfile()
					 //ELogin.getEACM().getActiveProfile()
					 , _li);
			 // remove the lockitem from any currently held lockgroup
			 // the lockgroup is from an entityitem and is queried to determine the cellrenderer
			 LockList ll =EACM.getEACM().getLockMgr().getLockList(getProfile(),true);
			 if (ll != null) {
				 for(int i=0; i<_li.length; i++){
					 LockItem lockitem = _li[i];
					 String lgkey = lockitem.getLockGroup().getKey();
					 LockGroup lg = ll.getLockGroup(lgkey);
					 if (lg!= null){
						 String likey = lockitem.getKey();
						 lg.removeLockItem(likey);
					 }
				 }
			 }

			 ok = true;
			 Utils.monitor("unlock",locklist);
		 } catch (Exception exc) {
			 if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				 if (RMIMgr.getRmiMgr().reconnectMain()) {
					 try{
						 locklist.removeLockItem(null, ro(),
								 getProfile(),
								 //ELogin.getEACM().getActiveProfile(),
								 _li);
						 ok = true;
						 Utils.monitor("unlock",locklist);
					 }catch (Exception ex) {
						 com.ibm.eacm.ui.UI.showException(this,ex, "mw.err-title");
					 }
				 }else{	// reconnect failed
					 com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
				 }
			 }else{
				 com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
			 }
		 }

		 return ok;
	 }
}
