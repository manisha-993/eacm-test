//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.mtrx;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.FindNextAction;
import com.ibm.eacm.actions.FindRepAction;

import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.CrossTable;

import com.ibm.eacm.table.MtrxTable;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.toolbar.ComboItem;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;
import com.ibm.eacm.toolbar.EACMToolbar;
import com.ibm.eacm.toolbar.ToolbarLayout;

/**
 * tab for MatrixAction
 * column for each matrixgroup in MatrixTable
 * cross table for selected column where number of relators can be specified in the cell (no RELATTR)
 * SG	Action/Attribute	MTRMODEL	TYPE	EntityType	MODEL	
 * SG	Action/Attribute	MTRMODEL	ENTITYTYPE	Link	MODEL	
 * SG	Action/Attribute	MTRMODEL	TYPE	NavAction	NAVMTRMODEL	
 * 
 * @author Wendy Stimpson
 */
//$Log: MatrixActionTab.java,v $
//Revision 1.4  2014/10/03 11:08:08  wendy
//IN5515352 remove F8 keyboard mapping
//
//Revision 1.3  2013/09/19 21:59:09  wendy
//control sort when a row is updated
//
//Revision 1.2  2013/05/01 18:35:13  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class MatrixActionTab extends MatrixActionBase 
implements FocusListener
{
	private static final long serialVersionUID = 1L;
	private MtrxTable mTable = null;

	private JScrollPane mScroll = null;
	private JScrollPane cScroll = new JScrollPane();
	private JSplitPane split = null;
	
	private RSTTable focusTable = null;
	private JPanel mPanel = null;
	private JPanel cPanel = new JPanel(new BorderLayout());

	private EACMToolbar mTool = null;

	private EntityItem[] eiSrcArray = null;

	private JButton mtrxFilterButton = null;
	private JButton crssFilterButton = null;
	private JButton crssCopyButton = null;
	private JButton crssFindButton = null;
	private JButton mtrxFindButton = null;

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.FindableComp#getFindable()
	 */
	public Findable getFindable() {
		if(focusTable==null){
			return mTable;
		}
		return focusTable;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#getTableTitle()
	 */
	public String getTableTitle() {
		return mTable.getTableTitle();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.DataActionPanel#getDefaultToolbarLayout()
	 */
	public ComboItem getDefaultToolbarLayout() {
		return DefaultToolbarLayout.CROSSTAB_BAR;
	}

	/**
	 * edit is now complete, items were updated.. refresh the table
	 */
	public void editComplete(){
		boolean wasFiltered = mTable.isFiltered();
		mTable.updateTableWithSelectedRows();
		if(wasFiltered){
			mTable.filter();
		}
		
		CrossTable crssTable = mTable.getCrossTable();
		if (crssTable != null) {
			wasFiltered = crssTable.isFiltered();
			crssTable.updateTableWithSelectedRows();
			if(wasFiltered){
				crssTable.filter();
			}
		}
		this.enableActionsAndRestore();
	}
	/**
	 * @param _mList
	 * @param key
	 * @param ei
	 */
	public MatrixActionTab(MatrixList _mList, String key, EntityItem[] ei) {
		super(_mList,key);
		eiSrcArray = ei;		
		init();

		// get filter buttons, only the button will be disabled, not the action
		crssFilterButton = getButton(getCrossToolbar(),FILTER_ACTION);
		mtrxFilterButton = getButton(mTool,FILTER_ACTION);
		crssCopyButton = getButton(getCrossToolbar(),COPY_ACTION);
		crssFindButton = getButton(getCrossToolbar(),FINDREP_ACTION);
		mtrxFindButton= getButton(mTool,FINDREP_ACTION);

		EACMAction act = getAction(COPY_ACTION);
		if (act!=null){
			mTable.registerEACMAction(act, KeyEvent.VK_C, Event.CTRL_MASK);
			mTable.getCrossTable().registerEACMAction(act, KeyEvent.VK_C, Event.CTRL_MASK);
		}
		act = getAction(PASTE_ACTION);
		if (act!=null){
			mTable.registerEACMAction(act, KeyEvent.VK_V, Event.CTRL_MASK);
			mTable.getCrossTable().registerEACMAction(act, KeyEvent.VK_V, Event.CTRL_MASK);
		}
		mTable.addMouseListener(new MouseAdapter() { // base class deref will remove this
			public void mouseReleased(MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					if(focusTable instanceof CrossTable){
						// this is for the matrix, but crosstbl had last focus
						mTable.requestFocusInWindow();
					}
			
					popup.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		});
		mTable.getCrossTable().addMouseListener(new MouseAdapter() { // base class deref will remove this
			public void mouseReleased(MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					if(focusTable instanceof MtrxTable){
						// this is for the crosstable, but matrix had last focus
						mTable.getCrossTable().requestFocusInWindow();
					}
				
					popup.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		});
	}
	private JButton getButton(EACMToolbar toolbar, String actionkey){
		JButton theButton = null;
		for(int i=0; i<toolbar.getComponentCount(); i++){
			Component comp = toolbar.getComponent(i);
			if (comp instanceof JButton){
				JButton button = ((JButton)comp);
				EACMAction act = (EACMAction)button.getAction();
				if(act!=null && actionkey.equals(act.getActionKey())){
					theButton = button;
					break;
				}
			}
		}
		return theButton;
	}

	protected void initMatrix(){
		mTool = new EACMToolbar(ToolbarLayout.getToolbarLayout(DefaultToolbarLayout.MATRIX_BAR), getActionTbl());
		mTable.addFocusListener(this);

		addComponentListener(new ComponentAdapter(){
			//Ensure the tabbed pane gets focus
			public void componentShown(ComponentEvent ce) {
				mTable.requestFocusInWindow();
			}
		});

		mScroll = new JScrollPane(mTable);

		mScroll.setRowHeaderView(mTable.getRowHeader(0));
		mScroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, mTable.getHeader());

		refreshCrossTab(); // set initial crosstab in the viewport

		mPanel = new JPanel(new BorderLayout());
		mPanel.add(mTool.getAlignment(), mTool);

		cPanel.add(getCrossToolbar().getAlignment(), getCrossToolbar());

		buildMatrixSplit();

		if (mTable.getRowCount() > 0) {
			mTable.setRowSelectionInterval(0, 0);
			if (mTable.getColumnCount() > 1) {
				mTable.setColumnSelectionInterval(1, 1);
			}
		}
	}

	private void buildMatrixSplit() {
		split = new JSplitPane(0);
		split.setOneTouchExpandable(true);

		mPanel.add(mScroll,BorderLayout.CENTER);
		cPanel.add(cScroll,BorderLayout.CENTER);

		split.setTopComponent(mPanel);
		split.setBottomComponent(cPanel);

		Dimension min = UIManager.getDimension("eannounce.minimum");
		cPanel.setMinimumSize(min);
		mPanel.setMinimumSize(min);
		split.setMinimumSize(min);

		getSplitPane().setRightComponent(split);
		getSplitPane().setLeftComponent(getActionTree()); 

		split.setResizeWeight(.5D);
		split.setDividerLocation(EACM.getEACM().getHeight() / 2);
		
		//IN5515352 remove F8 keyboard mapping
		KeyStroke keyToRemove = KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0);
		Utils.removeKeyBoardMapping(split, keyToRemove);
		
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.ActionTreeTabPanel#disableActionsAndWait()
	 */
	public void disableActionsAndWait(){
		super.disableActionsAndWait();
		mTable.setEnabled(false);
		mTable.getCrossTable().setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.ActionTreeTabPanel#enableActionsAndRestore()
	 */
	public void enableActionsAndRestore(){
    	super.enableActionsAndRestore();
    	if(isWaiting()){ // all workers have not completed
    		return;
    	}
		mTable.setEnabled(true);
		mTable.getCrossTable().setEnabled(true);
		
		enableFocusTable();
	}

	/**
	 * createMenus
	 *
	 */
	protected void createMenus() {
		createFileMenu();

		createEditMenu();
		createViewMenu(); 
		createActionMenu();

		createTableMenu();
	}

	private void createEditMenu() {
		JMenu editMenu = new JMenu(Utils.getResource(EDIT_MENU));
		editMenu.setMnemonic(Utils.getMnemonic(EDIT_MENU));
		addLocalActionMenuItem(editMenu,COPY_ACTION);

		addLocalActionMenuItem(editMenu,PASTE_ACTION);

		getMenubar().add(editMenu);
	}

	/**
	 * create all of the actions, they are shared between toolbars and menu
	 */
	protected void createMatrixActions(){
		mTable = new MtrxTable(getMatrixList(),this);

		mTable.getSelectionModel().addListSelectionListener(this); // get notified when rows chg
		mTable.getColumnModel().addColumnModelListener(this); // base class has listener methods

		mTable.getColumnModel().getSelectionModel().addListSelectionListener(this); // get notified when cols chg

		focusTable = mTable;

		super.createActions();

		//MTRXEDIT_ACTIONSET
		addAction(new EANEditActionSet(this));
		//MTRXWU_ACTIONSET
		addAction(new EANWuActionSet(this));
		//MTRX_TOGLACT
		addAction(new ToggleSplitAction());
		//MTRX_TOGLCRS
		addAction(new ToggleCrossAction());

		//MTRX_SAVEORDER
		addAction(new SaveMgOrderAction(mTable, this)); 

		//MTRX_RESTOREORDER
		addAction(new RestoreMgOrderAction(mTable, this)); 

		//MTRX_PIVOT was instantiated in init()
		((PivotAction)getAction(MTRX_PIVOT)).setTable(mTable);	
		//MTRX_CANCEL
		((CancelAction)getAction(MTRX_CANCEL)).setTable(mTable);	
		//MTRX_SAVE
		((SaveAction)getAction(MTRX_SAVE)).setTable(mTable);
		
		addAction(new FindRepAction());
		addAction(new FindNextAction());

		mTable.addPropertyChangeListener((CancelAction)getAction(MTRX_CANCEL));
		mTable.addPropertyChangeListener((SaveAction)getAction(MTRX_SAVE));

		createTableActions(mTable);

		createEditActions(mTable.getCrossTable());
	}

	private void createActionMenu() {
		JMenu actMenu = new JMenu(Utils.getResource(ACTIONS_MENU));
		actMenu.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));
	
		addLocalActionMenuItem(actMenu, MTRX_CANCEL);

		actMenu.addSeparator();
		if (getActionTree().actionExists(ACTION_PURPOSE_EDIT)) {
			addLocalActionMenuItem(actMenu, MTRXEDIT_ACTIONSET);
		}
		if (getActionTree().actionExists(ACTION_PURPOSE_WHERE_USED)) {
			addLocalActionMenuItem(actMenu, MTRXWU_ACTIONSET);
		}
		addLocalActionMenuItem(actMenu, MTRX_ADDCOL);
		actMenu.addSeparator();

		addLocalActionMenuItem(actMenu,MTRX_DELETECOL);
		addLocalActionMenuItem(actMenu,MTRX_DELETEROW);

		addLocalActionMenuItem(actMenu,MTRX_RESETSEL);
		actMenu.addSeparator();
		addLocalActionMenuItem(actMenu,MTRX_ADJUSTCOL);
		addLocalActionMenuItem(actMenu,MTRX_ADJUSTROW);

		getMenubar().add(actMenu); 
	}

	/**
	 * createTableMenu
	 */
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
		addLocalActionMenuItem(tblMenu, MTRX_PIVOT);
		tblMenu.addSeparator();
		addLocalActionMenuItem(tblMenu, SELECTALL_ACTION);
		addLocalActionMenuItem(tblMenu, SELECTINV_ACTION);

		tblMenu.addSeparator();
		addLocalActionMenuItem(tblMenu, FINDREP_ACTION);
		addLocalActionMenuItem(tblMenu, FINDNEXT_ACTION);

		addLocalActionMenuItem(tblMenu, FILTER_ACTION); 
	
		tblMenu.addSeparator();

		addLocalActionMenuItem(tblMenu, MTRX_SAVEORDER);

		//MTRX_RESTOREORDER
		addLocalActionMenuItem(tblMenu, MTRX_RESTOREORDER);

		tblMenu.addSeparator();
		addLocalActionMenuItem(tblMenu, ENTITYDATA_ACTION);

		getMenubar().add(tblMenu);
	} 

	private void createViewMenu() {
		JMenu viewMenu = new JMenu(Utils.getResource(VIEW_MENU));
		viewMenu.setMnemonic(Utils.getMnemonic(VIEW_MENU));
		
		addLocalActionMenuItem(viewMenu, MTRX_TOGLACT);
		addLocalActionMenuItem(viewMenu, MTRX_TOGLCRS);

		getMenubar().add(viewMenu);
	}

	/**
	 * called by edit and wu actions
	 * @return
	 */
	public boolean hasData(){
		return mTable.getRowCount()>0 && mTable.getColumnCount()>1;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.mtrx.MatrixActionBase#canClose()
	 */
	public boolean canClose() {
		boolean ok = true;
		CrossTable crssTable = mTable.getCrossTable();
		if (crssTable.isEditing()) {
			crssTable.cancelCurrentEdit();
		}
		if (mTable.hasChanges()) {
			//updtMsg2=Table values have changed. Would you like to save the change(s)?
			int action =  com.ibm.eacm.ui.UI.showConfirmYesNoCancel(this, Utils.getResource("updtMsg2"));
			if (action == YES_BUTTON){
				disableActionsAndWait();
				ok = update();
				enableActionsAndRestore();
			}else if (action==NO_BUTTON|| action==CLOSED){                
			}else if (action==CANCEL_BUTTON){
				return false;
			}
		}
		if(ok){
			ok=super.canClose();
		}
		return ok;
	}
	
	/**
	 * used by matrix edit and wu action, not tested
	 * @return
	 * @throws OutOfRangeException 
	 */
	protected EntityItem[] getRelatedEntityItems() throws OutOfRangeException
	{
		EntityItem[] out = null;

		EntityItem[] _eiRelatives = (EntityItem[]) mTable.getSelectedEntityItems(false, true);

		if (eiSrcArray == null) {
			return _eiRelatives;
		}

		if (_eiRelatives.length == eiSrcArray.length) {
			return eiSrcArray;
		}
		
		Vector<EntityItem> v = new Vector<EntityItem>();
		for (int i = 0; i < _eiRelatives.length; ++i) {
			String key = _eiRelatives[i].getKey();
			for (int x = 0; x < eiSrcArray.length; ++x) {
				if (eiSrcArray[x].getDownLink(key) != null) {
					v.add(eiSrcArray[x]);
					break;
				}
			}
		}
		if (!v.isEmpty()) {
			out = new EntityItem[v.size()];
			v.copyInto(out);
			v.clear();
		}


		return out;
	}

	/**
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent _fe) {
		Object o = _fe.getSource();

		focusTable = (RSTTable) o;
		if(isWaiting()){
			return; // dont enable anything now
		}
		enableFocusTable();
	}

	private void enableFocusTable(){
		// set matrix or crosstable in generic table actions
		updateTableActions(focusTable);
		updateEditActions(focusTable); // copy and paste

		if (focusTable instanceof CrossTable) {
			getAction(MTRXEDIT_ACTIONSET).setEnabled(false);
			getAction(MTRXWU_ACTIONSET).setEnabled(false);
			if(crssFilterButton!=null){
				crssFilterButton.setEnabled(true);
			}
			if(mtrxFilterButton!=null){
				mtrxFilterButton.setEnabled(false);
			}
			
			if(crssFindButton!=null){
				crssFindButton.setEnabled(true);
			}
			if(mtrxFindButton!=null){
				mtrxFindButton.setEnabled(false);
			}
			if(crssCopyButton!=null){
				crssCopyButton.setEnabled(mTable.getCrossTable().getSelectedRowCount() > 0);
			}
			getActionTree().setEnabled(false);

		} else if (focusTable instanceof MtrxTable) {
			getAction(MTRXEDIT_ACTIONSET).setEnabled(getActionTree().actionExists(ACTION_PURPOSE_EDIT));

			getAction(MTRXWU_ACTIONSET).setEnabled(getActionTree().actionExists(ACTION_PURPOSE_WHERE_USED));

			if(crssFilterButton!=null){
				crssFilterButton.setEnabled(false);
			}
			if(crssFindButton!=null){
				crssFindButton.setEnabled(false);
			}
			if(mtrxFindButton!=null){
				mtrxFindButton.setEnabled(true);
			}
			if(mtrxFilterButton!=null){
				mtrxFilterButton.setEnabled(true);
			}
			if(crssCopyButton!=null){
				crssCopyButton.setEnabled(false);
			}
			getActionTree().setEnabled(true);

		}

		focusTable.setFilterIcon(); // update the icon
		EACM.getEACM().setHiddenStatus(focusTable.hasHiddenCols()); // update the icon

		// only different values will actually fire an event - make sure actions listening
		// reflect the correct state
		focusTable.firePropertyChange(DATACHANGE_PROPERTY, true, false);
		//FILTER_ACTION 
		EACMAction act = getAction(FILTER_ACTION);
		act.setEnabled(focusTable.getModel().getColumnCount()>1);

	}

	/**
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent _fe) {}

	/**
	 * update
	 */
	private boolean update() {
		boolean bCommit = true;
		try {
			mTable.commit();
		} catch (Exception e) {
			bCommit=false;
			if (e instanceof EANBusinessRuleException){
				mTable.moveToError((EANBusinessRuleException) e);
			}
	    	
			com.ibm.eacm.ui.UI.showException(mTable,e);
		}

		return bCommit;
	}

	/**
	 * dereference
	 */
	public void dereference() {

		eiSrcArray = null;

		ComponentListener[] l = getComponentListeners();
		for (int i = 0; i < l.length; ++i) {
			removeComponentListener(l[i]);
		}
		crssFilterButton = null;
		mtrxFilterButton = null;
		crssFindButton = null;
		mtrxFindButton = null;
		crssCopyButton = null;

		focusTable = null;
		if (mTable != null) {
			mTable.unregisterEACMAction(getAction(PASTE_ACTION),KeyEvent.VK_V, Event.CTRL_MASK);
			mTable.unregisterEACMAction(getAction(COPY_ACTION),KeyEvent.VK_C, Event.CTRL_MASK);
			
			mTable.getSelectionModel().removeListSelectionListener(this);  
			mTable.getColumnModel().getSelectionModel().removeListSelectionListener(this); 
			mTable.getColumnModel().removeColumnModelListener(this); // base class has listener methods

			CrossTable crssTable = mTable.getCrossTable();
			if (crssTable!=null){
				crssTable.removePropertyChangeListener((CancelAction)getAction(MTRX_CANCEL));
				crssTable.removePropertyChangeListener((SaveAction)getAction(MTRX_SAVE));
				crssTable.unregisterEACMAction(getAction(PASTE_ACTION),KeyEvent.VK_V, Event.CTRL_MASK);
				crssTable.unregisterEACMAction(getAction(COPY_ACTION),KeyEvent.VK_C, Event.CTRL_MASK);
			}

			mTable.removePropertyChangeListener((CancelAction)getAction(MTRX_CANCEL));
			mTable.removePropertyChangeListener((SaveAction)getAction(MTRX_SAVE));

			mTable.dereference();
			mTable = null;
		}

		if (mScroll != null) {
			mScroll.removeAll();
			mScroll.setUI(null);
			mScroll = null;
		}

		if (cScroll != null) {
			cScroll.removeAll();
			cScroll.setUI(null);
			cScroll = null;
		}

		if (mPanel != null) {
			mPanel.removeAll();
			mPanel.setUI(null);
			mPanel = null;
		}

		if (cPanel != null) {
			cPanel.removeAll();
			cPanel.setUI(null);
			cPanel = null;
		}

		if (mTool != null) {
			mTool.dereference();
			mTool = null;
		}

		split.removeAll();
		split.setUI(null);
		split = null;
		
		super.dereference();
	}

	/* (non-Javadoc)
	 * does crosstable or matrix table have any hidden columns?
	 * @see com.ibm.eacm.mtrx.MatrixActionBase#hasHiddenColumns()
	 */
	protected boolean hasHiddenColumns() {
		boolean mtrxcols = false;
		boolean crsscols = false;
		if (mTable!=null){
			mtrxcols = mTable.hasHiddenCols();
			crsscols = mTable.getCrossTable().hasHiddenCols();
		}
		return mtrxcols && crsscols;
	}

	// set crosstable based on column selection in matrixtable
	protected BaseTable getJTable() { return focusTable;}

	/* (non-Javadoc)
	 * when mtable column selection changes this is notified
	 * also called when tablemodel is changed
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent _lse) {
		if (!_lse.getValueIsAdjusting()) {
			if(focusTable == mTable){    // user may have selected a different mg, update crosstable	
				if(mTable.getSelectedColumnCount()==1){
					refreshCrossTab();
				}
				mTable.revalidate();
				mTable.repaint(); // selecting other cells sometimes doesnt clear previous selection
				if(crssFilterButton!=null){
					crssFilterButton.setEnabled(false);
				}
				if(mtrxFilterButton!=null){
					mtrxFilterButton.setEnabled(true);
				}
				if(crssFindButton!=null){
					crssFindButton.setEnabled(false);
				}
				if(mtrxFindButton!=null){
					mtrxFindButton.setEnabled(true);
				}
				if(crssCopyButton!=null){
					crssCopyButton.setEnabled(false);
				}
			}else{
				refreshActions();
				if(mTable.getCrossTable()!=null){
					if(crssFilterButton!=null){
						crssFilterButton.setEnabled(true);
					}
					if(crssFindButton!=null){
						crssFindButton.setEnabled(true);
					}

					if(crssCopyButton!=null){
						crssCopyButton.setEnabled(mTable.getCrossTable().getSelectedRowCount() > 0);
					}
				}
				if(mtrxFilterButton!=null){
					mtrxFilterButton.setEnabled(false);
				}
				if(mtrxFindButton!=null){
					mtrxFindButton.setEnabled(false);
				}
			}
		}
	}
	/**
	 * enable actions based on current selection
	 * called after pivot, after user changes row or column selection and after enableActionsAndRestore()
	 */
	public void refreshActions() {
		//enable actions based on selection
		enableCrossTableActions();

		//enable actions based on mtable state
		//MTRX_PIVOT
		((PivotAction)getAction(MTRX_PIVOT)).setEnabled(true);//setTable(mTable);	
		//MTRX_CANCEL
		((CancelAction)getAction(MTRX_CANCEL)).setEnabled(true);//setTable(mTable);	
		//MTRX_SAVE
		((SaveAction)getAction(MTRX_SAVE)).setEnabled(true);//setTable(mTable);	
	}

	/**
	 * set the crosstable to use
	 * called when user selects a row or column in mtable and when matrixactiontab is instantiated
	 */
	private void refreshCrossTab() {
		mTable.setCrossTable(getProfile());

		CrossTable crssTable = mTable.getCrossTable();
		if (crssTable != null) {
			JViewport vp = cScroll.getViewport();
			if(vp==null || vp.getView()!= crssTable){ // this is the first time thru here
				cScroll.setViewportView(crssTable);
				crssTable.addPropertyChangeListener((CancelAction)getAction(MTRX_CANCEL));
				crssTable.addPropertyChangeListener((SaveAction)getAction(MTRX_SAVE));
			}

			vp = cScroll.getRowHeader();
			if(vp==null || vp.getView()!=crssTable.getRowHeader(0)){
				cScroll.setRowHeaderView(crssTable.getRowHeader(0));
				cScroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, crssTable.getHeader());
			}
		}
		// set current crosstable in the actions, data will also be checked to enable the action
		updateCrossTableActions(crssTable);

		// mtable still has focus, make sure the status icons still reflect it
		mTable.setFilterIcon(); // update the icon
		EACM.getEACM().setHiddenStatus(mTable.hasHiddenCols()); // update the icon
	}

	private class ToggleSplitAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		ToggleSplitAction() {
			super(MTRX_TOGLACT, KeyEvent.VK_T, Event.CTRL_MASK);
		}
		public void actionPerformed(ActionEvent e) {
			int divLoc = getSplitPane().getDividerLocation(); 
			if (divLoc < 10) { 
				getSplitPane().setDividerLocation(getActionTree().getPreferredWidth()); 
			} else { 
				getSplitPane().setDividerLocation(0); 
			}
			repaint(); 
		}
	}
	private class ToggleCrossAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		ToggleCrossAction() {
			super(MTRX_TOGLCRS,KeyEvent.VK_T, Event.CTRL_MASK + Event.SHIFT_MASK);
		}
		public void actionPerformed(ActionEvent e) {			
			if (split.getDividerLocation() == split.getMinimumDividerLocation()) {
				split.setDividerLocation(split.getMaximumDividerLocation());
			} else {
				split.setDividerLocation(split.getMinimumDividerLocation());
			}
			repaint();
		}
	}
}
