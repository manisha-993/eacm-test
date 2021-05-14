//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.tabs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.JTableHeader;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.mw.Timer;
import com.ibm.eacm.objects.*;

import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.toolbar.ComboItem;
import com.ibm.eacm.toolbar.EACMToolbar;
import com.ibm.eacm.toolbar.ToolbarLayout;
import com.ibm.eacm.ui.EACMFrame;

/**
 * this is the base class for tabs and navigate, it has the toolbar, menubar and popup
 * it maintains the actiontbl
 * @author Wendy Stimpson
 */
//$Log: DataActionPanel.java,v $
//Revision 1.7  2015/01/05 19:15:34  stimpsow
//use Theme for background colors
//
//Revision 1.6  2014/01/24 12:47:13  wendy
//getro() throw remoteexception to allow reconnect
//
//Revision 1.5  2013/07/31 00:47:12  wendy
//correct UAT refresh error
//
//Revision 1.4  2013/07/18 18:45:25  wendy
//fix compiler warnings
//
//Revision 1.3  2013/02/05 18:23:22  wendy
//throw/handle exception if ro is null
//
//Revision 1.2  2012/10/26 21:45:38  wendy
//make sure waitingCnt is never less than 0
//
//Revision 1.1  2012/09/27 19:39:23  wendy
//Initial code
//
public abstract class DataActionPanel extends JPanel implements EACMGlobals,
PropertyChangeListener,TableColumnModelListener, ListSelectionListener,FindableComp
{
	private static final long serialVersionUID = 1L;
	private static int uidcnt=0; // give this panel a unique id, needed when instantiating and tracking a frame

	protected JPopupMenu popup = null;
	private String myuid=null;
	private boolean bRefresh = false;
	private JMenuBar menubar = null;
	private EACMToolbar toolBar = null;

	private int waitingCnt = 0;
	private java.awt.Container tabContainer = null;

	private Hashtable<String, EACMAction> actionTbl = null;

	/**
	 * is the profile for this data been dialed back?
	 * @return
	 */
	public boolean isDateDialedBack(){
		boolean isDialedBack = false;
		if(getProfile()!=null){
			isDialedBack = !getProfile().getValOn().equals(DateRoutines.getEOD());
		}
		return isDialedBack;
	}
	/**
	 * set the time for this profile
	 * called when the role is first loaded and when the resetdate action is used
	 * @param time
	 */
	public void setProcessTime(String time) {
		Profile prof = getProfile();
		if (prof != null) {
			prof.setValOnEffOn(time, time);
			EACM.getEACM().setPastStatus();
		}
	}
	/**
	 * get the unique id for this
	 * @return
	 */
	public String getUID() { return myuid;}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if(o instanceof DataActionPanel && getUID()!=null){
			return getUID().equals(((DataActionPanel)o).getUID());
		}

		return super.equals(o);
	}
	/**
	 * constructor
	 */
	public DataActionPanel(){
		super(new BorderLayout());
		uidcnt++;
		myuid = ""+uidcnt;
		//look for timer notifications
		RMIMgr.getRmiMgr().getTimer().addPropertyChangeListener(Timer.PROF_VALON_UPDATE,this);
	}

	/**
	 * EOD has passed, update profiles valon
	 * @param newdate
	 */
	protected void updateValOn(String newdate) {
		getProfile().setValOnEffOn(newdate, newdate);
	}
	/* (non-Javadoc)
	 * called by the timer when valon needs to be updated
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(Timer.PROF_VALON_UPDATE)){
			String newdate = event.getNewValue().toString();
			String olddate = event.getOldValue().toString();
			Profile prof = getProfile();
			if (prof != null){
				if(!isDateDialedBack() || prof.getValOn().equals(olddate)) {
					updateValOn(newdate);
				}
			}
		}
	}
	/**
	 * create the toolbar
	 */
	protected void createToolbar(){
		toolBar = new EACMToolbar(ToolbarLayout.getToolbarLayout(getDefaultToolbarLayout()), getActionTbl());
	}

	/**
	 * access the remote database interface
	 * @return
	 * @throws RemoteException 
	 */
	protected RemoteDatabaseInterface ro() throws RemoteException  {
		return RMIMgr.getRmiMgr().getRemoteDatabaseInterface();
	}

	/**
	 * get the toolbar
	 * @return
	 */
	public EACMToolbar getToolbar(){ return toolBar;}

	/**
	 * get the hashtable with all actions
	 * @return
	 */
	public Hashtable<String, EACMAction> getActionTbl() {
		if (actionTbl==null){
			actionTbl = new Hashtable<String, EACMAction>();
		}
		return actionTbl;
	}

	/**
	 * get eanaction for use in the action tree
	 * @return
	 */
	public EANAction getEANAction(String actionKey) {
		EANAction act = null;

		for (Enumeration<?> e = getActionTbl().elements(); e.hasMoreElements();){
			EACMAction action = (EACMAction)e.nextElement();
			if(action instanceof EANAction){
				if (actionKey.equals(action.getActionKey())){
					act = (EANAction)action;
					break;
				}
			}
			if(action instanceof EANActionSet){
				EANAction act2 = ((EANActionSet)action).getEANAction(actionKey);
				if (act2!=null){
					act = act2;
					break;
				}
			}
		}

		return act;
	}

	/**
	 * disable/enable actions that rely on table state
	 * each action knows what to look for
	 * TODO attribute info under help is enabled ALL the time!!
	 */
	protected void enableTableActions() {
		boolean b = true;

		getAction(MOVECOL_LEFT_ACTION).setEnabled(b);
		getAction(MOVECOL_RIGHT_ACTION).setEnabled(b);
		getAction(HIDECOL_ACTION).setEnabled(b);
		getAction(UNHIDECOL_ACTION).setEnabled(b);
		getAction(SHOWHIST_ACTION).setEnabled(b);
		getAction(ENTITYDATA_ACTION).setEnabled(b);

		//COPY_ACTION
		if (getAction(COPY_ACTION)!=null){
			getAction(COPY_ACTION).setEnabled(b);
		}
		//PASTE_ACTION
		if (getAction(PASTE_ACTION)!=null){
			getAction(PASTE_ACTION).setEnabled(b);
		}
	}

	/**
	 * get the action for this key
	 * @param key
	 * @return
	 */
	public EACMAction getAction(String key){
		return getActionTbl().get(key);
	}
	/**
	 * add action to the table
	 * @param act
	 */
	protected void addAction(EACMAction act){
		getActionTbl().put(act.getActionKey(), act);
	}

	/**
	 * add menuitem using global action
	 * @param menu
	 * @param actionName
	 */
	protected void addGlobalActionMenuItem(JMenu menu, String actionName){
		JMenuItem mi = menu.add(EACM.getEACM().getGlobalAction(actionName));
		mi.setVerifyInputWhenFocusTarget(false);
	}
	/**
	 * add menu using local action
	 * @param menu
	 * @param actionName
	 */
	protected void addLocalActionMenuItem(JMenu menu, String actionName){
		JMenuItem mi = menu.add(getAction(actionName));
		mi.setVerifyInputWhenFocusTarget(false);
	}
	/**
	 * create all of the actions, they are shared between toolbar and menu
	 */
	protected void createTableActions(RSTTable table) {
		//FILTER_ACTION
		addAction(new com.ibm.eacm.actions.FilterAction(EACM.getEACM(),table));
		//SORT_ACTION
		addAction(new com.ibm.eacm.actions.SortAction(EACM.getEACM(),table));
		//SELECTALL_ACTION
		addAction(new SelectAllAction(table));
		//SELECTINV_ACTION
		addAction(new SelectInvertAction(table));
		//MOVECOL_LEFT_ACTION
		addAction(new MoveColLeftAction(table));
		//MOVECOL_RIGHT_ACTION
		addAction(new MoveColRightAction(table));
		//UNHIDECOL_ACTION
		UnhideColAction act = new UnhideColAction(table);
		addAction(act);
		//HIDECOL_ACTION
		addAction(new HideColAction(table,act));
		//SHOWHIST_ACTION
		addAction(new ShowEntityHistoryAction(this,table));

		//ENTITYDATA_ACTION
		addAction(new EntityDataAction(table));
	}

	/**
	 * create the edit actions
	 * @param table
	 */
	protected void createEditActions(RSTTable table) {
		//COPY_ACTION
		addAction(new CopyAction(table));
		//PASTE_ACTION
		addAction(new PasteAction(this,table));
	}
	/**
	 * set the table to use in the table actions
	 * @param table
	 */
	public void updateTableActions(RSTTable table) {
		//FILTER_ACTION
		EACMAction  act = getAction(FILTER_ACTION);
		if (act!=null){
			((FilterAction)act).setFilterable(table);
		}
		//SORT_ACTION
		act = getAction(SORT_ACTION);
		if (act!=null){
			((SortAction)act).setSortable(table);
		}

		//SELECTALL_ACTION
		act = getAction(SELECTALL_ACTION);
		if (act!=null){
			((SelectAllAction)act).setTable(table);
		}

		//SELECTINV_ACTION
		act = getAction(SELECTINV_ACTION);
		if (act!=null){
			((SelectInvertAction)act).setTable(table);
		}

		//
		//MOVECOL_LEFT_ACTION
		act = getAction(MOVECOL_LEFT_ACTION);
		if (act!=null){
			((MoveColLeftAction)act).setTable(table);
		}

		//MOVECOL_RIGHT_ACTION
		act = getAction(MOVECOL_RIGHT_ACTION);
		if (act!=null){
			((MoveColRightAction)act).setTable(table);
		}

		//HIDECOL_ACTION
		act = getAction(HIDECOL_ACTION);
		if (act!=null){
			((HideColAction)act).setTable(table);
		}

		//UNHIDECOL_ACTION
		act = getAction(UNHIDECOL_ACTION);
		if (act!=null){
			((UnhideColAction)act).setTable(table);
		}
		//SHOWHIST_ACTION
		act = getAction(SHOWHIST_ACTION);
		if (act!=null){
			((ShowEntityHistoryAction)act).setHistoryInterface(table);
		}

		//ENTITYDATA_ACTION
		act = getAction(ENTITYDATA_ACTION);
		if (act!=null){
			((EntityDataAction)act).setTable(table);
		}
	}
	/**
	 * set the table to use in the edit actions
	 * @param table
	 */
	protected void updateEditActions(RSTTable table) {
		//COPY_ACTION
		EACMAction act = getAction(COPY_ACTION);
		if (act!=null){
			((CopyAction)act).setTable(table);
		}
		//PASTE_ACTION
		act = getAction(PASTE_ACTION);
		if (act!=null){
			((PasteAction)act).setTable(table);
		}
		//CUT_ACTION
		act =  getAction(CUT_ACTION);
		if (act!=null){
			((CutAction)act).setTable(table);
		}
	}

	/**
	 * is this waiting for an action to complete
	 * @return
	 */
	public boolean isWaiting() { return waitingCnt>0;}

	/**
	 * called by actions when using workers
	 */
	public void disableActionsAndWait(){
		waitingCnt++;
		
		Logger.getLogger(APP_PKG_NAME).log(Level.FINE, "UID"+this.myuid+" waitingcnt "+waitingCnt);
		
		disableActions();
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if(toolBar !=null){
			toolBar.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}

		if(getTabContainer() != null){
			getTabContainer().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}

		fixTableHeaderCursorBug(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	/**
	 * tableheaders do not consistently display correct cursor
	 * @param cursor
	 */
	protected void fixTableHeaderCursorBug(Cursor cursor){
		JTable table = this.getJTable();
		if(table !=null){
			JTableHeader header = table.getTableHeader();
			if (header != null) {
				header.setCursor(cursor);
			}
		}
	}

	private java.awt.Container getTabContainer(){
		if (tabContainer == null){
			java.awt.Container comp = this;
			java.awt.Container parent = this.getParent();
			while(parent!=null){
				if(parent instanceof JTabbedPane){
					tabContainer = comp;
					break;
				}
				comp = parent;
				parent = comp.getParent();
			}
		}
		return tabContainer;
	}
	/**
	 * worker has finished, so enable actions and restore the cursor
	 */
	public void enableActionsAndRestore(){
		// this may be called after tab is closed, so check if deref'd
		if(myuid ==null){
			return;
		}

		if(waitingCnt>0){
			waitingCnt--;
		}else{
			return;
		}

		Logger.getLogger(APP_PKG_NAME).log(Level.FINE, "UID"+this.myuid+" waitingcnt "+waitingCnt);

		if(isWaiting()){
			Logger.getLogger(APP_PKG_NAME).log(Level.FINE,"UID"+this.myuid+" Waiting for workers to finish, do not enable yet waitingCnt: "+waitingCnt);	
			return;
		}

		enableActions();

		setCursor(Cursor.getDefaultCursor());
		if(toolBar !=null){
			toolBar.setCursor(Cursor.getDefaultCursor());
		}
		if(getTabContainer() != null){
			getTabContainer().setCursor(Cursor.getDefaultCursor());
		}

		fixTableHeaderCursorBug(Cursor.getDefaultCursor());

		refreshActions(); // enable based on current selection - this must be done last
	}
	/**
	 * disable all actions
	 */
	private void disableActions(){
		for (Enumeration<?> e = getActionTbl().elements(); e.hasMoreElements();){
			EACMAction action = (EACMAction)e.nextElement();
			if(action.getActionKey().equals(CLOSETAB_ACTION)){
				continue;
			}
			action.setEnabled(false);
		}
	}
	/**
	 * enable all actions
	 */
	private void enableActions(){
		for (Enumeration<?> e = getActionTbl().elements(); e.hasMoreElements();){
			EACMAction action = (EACMAction)e.nextElement();
			//enable all
			action.setEnabled(true);
		}
	}

	/**
	 * enable actions based on current selection
	 */
	public void refreshActions() {}

	/**
	 * getMenubar
	 */
	public JMenuBar getMenubar() {
		if(menubar==null){
			menubar = new JMenuBar();
		}
		return menubar;
	}

	/**
	 * user changed background color, update all components
	 */
	public void updateComponentsUI(){
		SwingUtilities.updateComponentTreeUI(this);

		if(menubar !=null){
			SwingUtilities.updateComponentTreeUI(menubar);
			
			for(int i2=0;i2<menubar.getMenuCount();i2++){
				// menubar isnt updated on tabs that are not selected
				EACM.updateMenuUI(menubar.getMenu(i2));
			}
		}
		
		if(popup !=null){
			SwingUtilities.updateComponentTreeUI(popup);
		}
		if(toolBar !=null){
			SwingUtilities.updateComponentTreeUI(toolBar);
		}
	}
	/**
	 * createFileMenu
	 */
	public void createFileMenu() {
		JMenu fileMenu = new JMenu(Utils.getResource(FILE_MENU));
		fileMenu.setMnemonic(Utils.getMnemonic(FILE_MENU));

		addGlobalActionMenuItem(fileMenu, CLOSETAB_ACTION);

		addGlobalActionMenuItem(fileMenu,CLOSEALL_ACTION);

		fileMenu.addSeparator();

		//this is only used in matrix
		if (getAction(MTRX_SAVE)!=null){
			addLocalActionMenuItem(fileMenu, MTRX_SAVE);
			fileMenu.addSeparator();
		}

		addGlobalActionMenuItem(fileMenu,LOGOFF_ACTION);
		addGlobalActionMenuItem(fileMenu,EXIT_ACTION);

		getMenubar().add(fileMenu);
	}

	/**
	 * release memory
	 */
	public void dereference(){
		RMIMgr.getRmiMgr().getTimer().removePropertyChangeListener(Timer.PROF_VALON_UPDATE,this);

		// only remove menus for this tab
		if(menubar!=null){
			EACM.getEACM().closeTabMenus(menubar);
			menubar = null;
		}

		if (toolBar!=null){
			toolBar.dereference();
			toolBar = null;
		}

		if(canDerefWorker()){
			derefWorkerData();
		}

		if(popup!=null){
			removePopup();
			popup = null;
		}
		myuid=null;
		tabContainer = null;

		removeAll();
		setUI(null);
	}
	/**
	 * dereference data needed by workers, if worker is running, it must call this when done
	 */
	public void derefWorkerData(){
		if(actionTbl!=null){
			for (Enumeration<EACMAction> e = actionTbl.elements(); e.hasMoreElements();){
				EACMAction action = (EACMAction)e.nextElement();
				action.dereference();
			}
			actionTbl.clear();
			actionTbl = null;
		}
	}

	/**
	 * if workers are running, parts of deref must be delayed until workers finish or many null ptrs can happen
	 * @return
	 */
	protected boolean canDerefWorker(){
		boolean canderef = true;
		if(actionTbl!=null){
			for (Enumeration<EACMAction> e = actionTbl.elements(); e.hasMoreElements();){
				EACMAction action = (EACMAction)e.nextElement();
				if(!action.canDerefWorker()){
					canderef = false;
					break;
				}
			}
		}
		return canderef;
	}

	/**
	 * release memory for the popup
	 */
	private void removePopup(){
		for (int ii=0; ii<popup.getComponentCount(); ii++) {
			Component comp = popup.getComponent(ii);
			if (comp instanceof JMenuItem) {// separators are null
				EACM.closeMenuItem((JMenuItem)comp);
			}
		}
		popup.setUI(null);
		popup.removeAll();
	}

	/**
	 * get the default toolbar definition - used in toolbar preferences
	 * @return
	 */
	public abstract ComboItem getDefaultToolbarLayout();

	/**
	 * shouldRefresh
	 * @return
	 */
	public void setShouldRefresh(boolean _b) {
		bRefresh = _b;
	}
	/**
	 * @return
	 */
	public boolean shouldRefresh() {
		return bRefresh;
	}

	/**
	 * refresh called when nlslanguage is changed or when tab is selected and shouldrefresh is true
	 */
	public void refresh() {}

	/**
	 * getEntityType - used when column order preference is opened
	 * @param _i
	 * @return
	 */
	public String getEntityType(int _i){ return null;}

	/**
	 * getHelpText
	 * @return
	 */
	public String getHelpText() {
		return Utils.getResource("nia");
	}

	/**
	 * close tab - tabs with workers need to cancel them
	 * @return
	 */
	public boolean canClose() {
		boolean ok = true;
		for (Enumeration<?> e = getActionTbl().elements(); e.hasMoreElements();){
			EACMAction action = (EACMAction)e.nextElement();
			if(!action.canClose()){
				ok = false;
				break;
			}
		}

		if(ok){
			//find any open search or picklist windows
			Vector<EACMFrame> frameVct = EACM.getEACM().getMyEACMFrames(this.getUID()+":");
			for (int i=0; i<frameVct.size(); i++){
				EACMFrame frame = frameVct.elementAt(i);
				frame.setVisible(false);
				frame.dispose();
				frame.dereference();
			}
			frameVct.clear();
		}
		return ok;
	}

	/**
	 * getProfile
	 * @return
	 */
	public abstract Profile getProfile();


	/**
	 * get the table if any for this
	 * @return
	 */
	protected BaseTable getJTable() { return null;}

	/* (non-Javadoc)
	 * @see javax.swing.event.TableColumnModelListener#columnAdded(javax.swing.event.TableColumnModelEvent)
	 */
	public void columnAdded(TableColumnModelEvent e) {
		EACMAction act = getAction(UNHIDECOL_ACTION);
		if (act!=null){
			act.setEnabled(false);
		}
	}
	public void columnMarginChanged(ChangeEvent e) {}
	public void columnMoved(TableColumnModelEvent e) {}
	public void columnRemoved(TableColumnModelEvent e) {
		EACMAction act = getAction(UNHIDECOL_ACTION);
		if (act!=null){
			act.setEnabled(true);
		}
	}

	/* (non-Javadoc)
	 * enable move col left and right actions based on current col selection
	 * navigate does not support these actions..
	 * @see javax.swing.event.TableColumnModelListener#columnSelectionChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void columnSelectionChanged(ListSelectionEvent e) {
		// enable move left or right based on col selection
		if (!e.getValueIsAdjusting()){
			EACMAction act = getAction(MOVECOL_LEFT_ACTION);
			if (act!=null){
				act.setEnabled(true); // it will check values
			}

			act = getAction(MOVECOL_RIGHT_ACTION);
			if (act!=null){
				act.setEnabled(true); // it will check values
			}

			act = getAction(UNHIDECOL_ACTION);
			if (act!=null){
				act.setEnabled(true); // this will check for conditions
			}

			act = getAction(HIDECOL_ACTION);
			if (act!=null){
				act.setEnabled(true); // this will check for conditions
			}
		}
	}

	/**
	 * enable remove selected action based on current row selection
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent _lse) {
		if (!_lse.getValueIsAdjusting()) {
			refreshActions();
		 }
	 }
}
