//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2013  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.tabs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.FindNextAction;
import com.ibm.eacm.actions.FindRepAction;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.Utils;

import com.ibm.eacm.table.ABRQSTable;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.toolbar.ComboItem;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;

/**
* tab for ABRQueueStatus
* @author Wendy Stimpson
*/
//$Log: ABRQSActionTab.java,v $
//Revision 1.1  2013/09/19 16:31:36  wendy
//add abr queue status
//
public class ABRQSActionTab extends ActionTabPanel {
	private static final long serialVersionUID = 1L;
	private ABRQSTable table = null;
	private JScrollPane jsp = null;

	private ABRQueueStatusList abrqslist = null;

	/* (non-Javadoc)
	 * used by base class for enabling column listener
	 * @see com.ibm.eacm.tabs.TabPanel#getJTable()
	 */
	protected BaseTable getJTable() { return table;}

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		return "ABRQueueStatusList: "+getProfile();
	}
	
	public Findable getFindable() {
		return table;
	}

	public ComboItem getDefaultToolbarLayout() {
		return DefaultToolbarLayout.ABRQS_BAR;
	}

	public ABRQSActionTab(Profile profile) throws MiddlewareRequestException, MiddlewareException
	{
		setSessionTagText("N/A");

		abrqslist = new ABRQueueStatusList(profile);  // get an empty table

		table = new ABRQSTable(abrqslist.getTable(),getProfile());
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
	}

	private void createActionMenu() {
		JMenu actMenu = new JMenu(Utils.getResource(ACTIONS_MENU));
		actMenu.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));

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
	 * dereference
	 */
	public void dereference() {
		abrqslist.dereference();
		abrqslist = null;

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
		return "ABR Status";
	}

	/**
	 * getProfile
	 * @return Profile
	 */
	public Profile getProfile() {
		return abrqslist.getProfile();
	}

	protected String getTabMenuTitleKey() {
		return "abrqs.title";
	}
	protected String getTabIconKey() {
		return "abrview.icon";
	}

	 /**
	  * enable actions based on current selection
	  */
	 public void refreshActions() {
		 if(table==null){ // deref may have run before worker was done
			 return;
		 }
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
	 private class RefreshWorker extends DBSwingWorker<ABRQueueStatusList, Void> {
		 @Override
		 public ABRQueueStatusList doInBackground() {
			 ABRQueueStatusList ll=null;
			 try{
				 ll = getABRList();
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
					 ABRQueueStatusList ll = get();
					 if (ll !=null){
						 ABRQueueStatusList orig = abrqslist;
						 abrqslist=ll;
						 table.updateModel(ll.getTable(), ABRQSActionTab.this.getProfile());
						 if(orig != null){
							 orig.dereference();
							 orig = null;
						 }
					 }
				 }
			 } catch (InterruptedException ignore) {}
			 catch (java.util.concurrent.ExecutionException e) {
				 listErr(e,"getting abrqueuestatuslist");
			 }finally{
				 enableActionsAndRestore();
				 ABRQSActionTab.this.repaint();
			 }
		 }
		 public void notExecuted(){
			 Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
			 enableActionsAndRestore();
			 refreshWorker = null;
		 }
	 }
	 private ABRQueueStatusList getABRList() {
		 ABRQueueStatusList abqs = null;
		 try {
			 abqs = ro().getABRQueueStatus(getProfile());
		 } catch(Exception exc){
			 if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				 if (RMIMgr.getRmiMgr().reconnectMain()) {
					 try{
						 abqs = ro().getABRQueueStatus(getProfile());
					 }catch(Exception e){
						 if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							 if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, e) == RETRY) {
								 abqs = getABRList();
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
						 abqs = getABRList();
					 }// else user decide to ignore or exit
				 }else{
					 com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
				 }
			 }
		 }

		 return abqs;
	 }
}
