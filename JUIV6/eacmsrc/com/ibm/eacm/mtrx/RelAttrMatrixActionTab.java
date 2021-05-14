//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.mtrx;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import COM.ibm.eannounce.objects.*;


import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.FindNextAction;
import com.ibm.eacm.actions.FindRepAction;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.*;
import com.ibm.eacm.toolbar.ComboItem;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;


/**
 * tab for MatrixAction
 * when there are RELATTR defined for one of the MatrixGroups
 * matrix action for relators with attributes specified using RELATTR for matrix action
 * SG	Action/Attribute	MTRFEATURE	RELATTR	FEATURECABLE	QTY
 * SG	Action/Attribute	MTRFEATURE	RELATTR	PRODSTRUCT	ORDERCODE
 *
 * each matrixgroup table is in a separate tab
 * @author Wendy Stimpson
 */
//$Log: RelAttrMatrixActionTab.java,v $
//Revision 1.3  2013/09/19 21:59:22  wendy
//control sort when a row is updated
//
//Revision 1.2  2013/05/01 18:35:13  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class RelAttrMatrixActionTab extends MatrixActionBase
implements ChangeListener
{
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane = null;
	private JPanel cPanel = new JPanel(new BorderLayout());

	/**
	 * matrixAction with relator attributes on at least one matrix group
	 * @param _mList
	 * @param key
	 */
	public RelAttrMatrixActionTab(MatrixList _mList, String key) {
		super(_mList, key);
		init();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.DataActionPanel#getDefaultToolbarLayout()
	 */
	public ComboItem getDefaultToolbarLayout() {
		return DefaultToolbarLayout.RELATTRCROSSTAB_BAR;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.mtrx.MatrixActionTab#initMatrix()
	 */
	protected void initMatrix(){
		tabbedPane = new JTabbedPane() {
			private static final long serialVersionUID = 1L;
			/*
			 * make selected tab lighter than the rest
			 * @see javax.swing.JTabbedPane#getBackgroundAt(int)
			 */
			public Color getBackgroundAt(int _index) {
				Color out = super.getBackgroundAt(_index);
				if (out != null) {
					if (getSelectedIndex()!=_index) {
						out = out.darker();
					}
				}
				return out;
			}
			public void removeAll() {
				// dereference all tables
				for (int i=0; i<getTabCount(); i++){
					Component c = getComponentAt(i);
					if (c instanceof JScrollPane) {
						CrossTable ct = (CrossTable)(((JScrollPane)c).getViewport().getView());
						ct.getSelectionModel().removeListSelectionListener(RelAttrMatrixActionTab.this);
					    ct.getColumnModel().removeColumnModelListener(RelAttrMatrixActionTab.this); // base class has listener methods

					    ct.getColumnModel().getSelectionModel().removeListSelectionListener(RelAttrMatrixActionTab.this);
						ct.removePropertyChangeListener((CancelAction)getAction(MTRX_CANCEL));
						ct.removePropertyChangeListener((SaveAction)getAction(MTRX_SAVE));

						ct.unregisterEACMAction(getAction(PASTE_ACTION),KeyEvent.VK_V, Event.CTRL_MASK);
						ct.unregisterEACMAction(getAction(COPY_ACTION),KeyEvent.VK_C, Event.CTRL_MASK);

						ct.dereference();
					}
				}
				super.removeAll(); // remove all tabs
			}
		};

		tabbedPane.addChangeListener(this);

		EACMAction cpyact = getAction(COPY_ACTION);
		EACMAction pstact = getAction(PASTE_ACTION);
		for (int i = getMatrixList().getMatrixGroupCount() - 1; i >= 0; --i) {
			MatrixGroup mg = getMatrixList().getMatrixGroup(i);
			if (mg != null) {
				CrossTable crssTable = null;
				if(mg.showRelAttr()){
					crssTable = new RelAttrCrossTable(mg,this);
				}else{
					crssTable = new CrossTable(mg,this);
				}

				JScrollPane scroll = new JScrollPane(crssTable);
				//crssTable.setLongDescription(false);
				if (crssTable.getRowCount() > 0) {
					crssTable.setRowSelectionInterval(0, 0);
					if (crssTable.getColumnCount() > 1) {
						crssTable.setColumnSelectionInterval(1, 1);
					}
				}
				//need this for row selection changes, if selectall, then invertselection is done, the colmodel doesnt notify
				crssTable.getSelectionModel().addListSelectionListener(RelAttrMatrixActionTab.this);
				crssTable.getColumnModel().addColumnModelListener(RelAttrMatrixActionTab.this); // base class has listener methods

				crssTable.getColumnModel().getSelectionModel().addListSelectionListener(RelAttrMatrixActionTab.this);
				crssTable.addPropertyChangeListener((CancelAction)getAction(MTRX_CANCEL));
				crssTable.addPropertyChangeListener((SaveAction)getAction(MTRX_SAVE));
				crssTable.addMouseListener(new MouseAdapter() { // base class deref will remove this
		            public void mouseReleased(MouseEvent evt) {
		                if (evt.isPopupTrigger()) {
		                    popup.show(evt.getComponent(), evt.getX(), evt.getY());
		                }
		            }
				});
				if (cpyact!=null){
					crssTable.registerEACMAction(cpyact, KeyEvent.VK_C, Event.CTRL_MASK);
				}
				if (pstact!=null){
					crssTable.registerEACMAction(pstact, KeyEvent.VK_V, Event.CTRL_MASK);
				}

				scroll.setRowHeaderView(crssTable.getRowHeader(0));
				scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, crssTable.getHeader());

				tabbedPane.addTab(crssTable.getTableTitle(), scroll);
			}
		}

		cPanel.add(getCrossToolbar().getAlignment(), getCrossToolbar());
		cPanel.add(tabbedPane,BorderLayout.CENTER);

		getSplitPane().setRightComponent(cPanel);
		getSplitPane().setLeftComponent(getActionTree());


		if(tabbedPane.getTabCount()>0){
			addComponentListener(new ComponentAdapter(){
				//Ensure the tabbed pane gets focus
				public void componentShown(ComponentEvent ce) {
					tabbedPane.getComponentAt(0).requestFocusInWindow();
					Component c = tabbedPane.getComponentAt(0);
					if (c instanceof JScrollPane) {
						(((JScrollPane)c).getViewport().getView()).requestFocusInWindow();
					}
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#getTableTitle()
	 */
	public String getTableTitle() {
		return getMatrixList().getTable().getTableTitle();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.mtrx.MatrixActionBase#createMatrixActions()
	 */
	protected void createMatrixActions() {
		super.createActions();
		createTableActions((RSTTable)null);
		createEditActions((RSTTable)null);
		addAction(new FindRepAction());
		addAction(new FindNextAction());
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.mtrx.MatrixActionBase#createMenus()
	 */
	protected void createMenus() {
		createFileMenu();
		createEditMenu();
		createActionMenu();
		createTableMenu();
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
		addLocalActionMenuItem(tblMenu, ENTITYDATA_ACTION);

		getMenubar().add(tblMenu);
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

		addLocalActionMenuItem(actMenu,MTRX_ADDCOL);

		actMenu.addSeparator();
		addLocalActionMenuItem(actMenu,MTRX_DELETECOL);
		addLocalActionMenuItem(actMenu,MTRX_DELETEROW);
		addLocalActionMenuItem(actMenu,MTRX_RESETSEL);

		actMenu.addSeparator();
		addLocalActionMenuItem(actMenu,MTRX_ADJUSTCOL);
		addLocalActionMenuItem(actMenu,MTRX_ADJUSTROW);
		getMenubar().add(actMenu);
	}
	private void createEditMenu() {
		JMenu editMenu = new JMenu(Utils.getResource(EDIT_MENU));
		editMenu.setMnemonic(Utils.getMnemonic(EDIT_MENU));

		addLocalActionMenuItem(editMenu,COPY_ACTION);
		addLocalActionMenuItem(editMenu,PASTE_ACTION);

		getMenubar().add(editMenu);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.mtrx.MatrixActionBase#canClose()
	 */
	public boolean canClose() {
		boolean ok = true;
		CrossTable crssTable = getCrossTable();
		if (crssTable.isEditing()) {
			crssTable.cancelCurrentEdit();
		}
		if (getMatrixList().hasChanges()) {
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

    /* (non-Javadoc)
     * @see com.ibm.eacm.tabs.ActionTreeTabPanel#disableActionsAndWait()
     */
    public void disableActionsAndWait(){
    	super.disableActionsAndWait();
    	// disable the tabs and table too
    	tabbedPane.setEnabled(false);
    	getCrossTable().setEnabled(false);
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.tabs.ActionTreeTabPanel#enableActionsAndRestore()
     */
    public void enableActionsAndRestore(){
    	super.enableActionsAndRestore();
    	if(isWaiting()){ // all workers have not completed
    		return;
    	}
       	// enable the tabs and table too
    	tabbedPane.setEnabled(true);
    	getCrossTable().setEnabled(true);
    }

	/**
	 * update
	 *
	 * @return
	 */
	private boolean update() {
		boolean bCommit = true;
		try {
			getCrossTable().commit();
		} catch (Exception e) {
			bCommit=false;
			if (e instanceof EANBusinessRuleException){
				getCrossTable().moveToError((EANBusinessRuleException) e);
			}

			com.ibm.eacm.ui.UI.showException(getCrossTable(),e);
		}

		return bCommit;
	}

	/**
	 * dereference
	 */
	public void dereference() {
		// release the locks before matrixlist is dereferenced
		tabbedPane.removeChangeListener(this);
		// release memory for each crosstable
		tabbedPane.removeAll();
		tabbedPane.setUI(null);
		tabbedPane = null;

		super.dereference(); // this dereferences the matrixlist

		if (cPanel != null) {
			cPanel.removeAll();
			cPanel.setUI(null);
			cPanel = null;
		}
	}

	/**
	 * get CrossTable at selected index
	 * @return
	 */
	private CrossTable getCrossTable() {
		JScrollPane scroll = (JScrollPane)tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		if (scroll != null) {
			return (CrossTable) scroll.getViewport().getView();
		}
		return null;
	}

	public Findable getFindable() {
		if(tabbedPane==null){ // deref was done
			return null;
		}
		return getCrossTable();
	}
	/* (non-Javadoc)
	 * get crosstable based on tab selection
	 * @see com.ibm.eacm.mtrx.MatrixActionTab#getJTable()
	 */
	protected BaseTable getJTable() {
		if(tabbedPane==null){ // deref was done
			return null;
		}
		return getCrossTable();
	}

    /**
     * called when user has selected a different action tab and this one is no longer selected
     * clear any editors
     */
    public void deselect() {
		// end any current edit on a previous tab
		for (int i=0; i<tabbedPane.getTabCount(); i++){
			Component c = tabbedPane.getComponentAt(i);
			if (c instanceof JScrollPane) {
				CrossTable ct = (CrossTable)(((JScrollPane)c).getViewport().getView());
				ct.cancelCurrentEdit();
			}
		}
    }
	/**
	 * edit is now complete, items were updated.. refresh the table
	 */
	public void editComplete(){
		for (int i=0; i<tabbedPane.getTabCount(); i++){
			Component c = tabbedPane.getComponentAt(i);
			if (c instanceof JScrollPane) {
				CrossTable ct = (CrossTable)(((JScrollPane)c).getViewport().getView());
				boolean wasFiltered = ct.isFiltered();
				ct.updateTableWithSelectedRows();
				if(wasFiltered){
					ct.filter();
				}
			}
		}
		this.enableActionsAndRestore();
	}
    /**
     * called when user has selected this tab
     *
     */
    public void select() {
    	super.select();
    	CrossTable focusTable = getCrossTable();
		if (focusTable != null) {
	       	// only different values will actually fire an event - make sure actions listening
			// reflect the correct state
			focusTable.firePropertyChange(DATACHANGE_PROPERTY, true, false);
		}
    }

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.DataActionPanel#getHelpText()
	 */
	public String getHelpText() {
		CrossTable focusTable = getCrossTable();
		if (focusTable != null) {
			return focusTable.getHelpText();
		}
		return null;
	}
	/**
	 * called when user selects a crosstable tab
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
   	public void stateChanged(ChangeEvent e) {
		// end any current edit on a previous tab
		for (int i=0; i<tabbedPane.getTabCount(); i++){
			Component c = tabbedPane.getComponentAt(i);
			if (c instanceof JScrollPane) {
				CrossTable ct = (CrossTable)(((JScrollPane)c).getViewport().getView());
				ct.cancelCurrentEdit();
			}
		}

		CrossTable focusTable = getCrossTable();
		updateCrossTableActions(focusTable);
		updateTableActions(focusTable);// set the table to use in the generic table actions

		//FILTER_ACTION used in relattrmatrixactiontab
		if (focusTable!=null){
			EACMAction act = getAction(FILTER_ACTION);
			act.setEnabled(focusTable.getModel().getColumnCount()>1);
		}
	}

   	/**
   	 * set current crosstable in its actions
   	 * called when user selects a tab
   	 * @param table
   	 */
   	protected void updateCrossTableActions(CrossTable table) {
   		super.updateCrossTableActions(table);
   		//MTRX_PIVOT
   		((PivotAction)getAction(MTRX_PIVOT)).setTable(table);
   		//MTRX_CANCEL
   		((CancelAction)getAction(MTRX_CANCEL)).setTable(table);
   		//MTRX_SAVE
   		((SaveAction)getAction(MTRX_SAVE)).setTable(table);
		if (table != null) {
			table.setFilterIcon(); // update the icon
		   	EACM.getEACM().setHiddenStatus(table.hasHiddenCols()); // update the icon

	       	// only different values will actually fire an event - make sure actions listening
			// reflect the correct state
		   	table.firePropertyChange(DATACHANGE_PROPERTY, true, false);
		}

		updateEditActions(table); // copy and paste
   	}

	/**
	 * enable actions based on current selection
	 * called after pivot, after user changes row or column selection and after enableActionsAndRestore()
	 */
	public void refreshActions() {
		CrossTable crssTable = getCrossTable();
		if (crssTable != null) {
			//FILTER_ACTION used in relattrmatrixactiontab
			EACMAction act = getAction(FILTER_ACTION);
			if (act!=null){
				//column may have been added
				act.setEnabled(crssTable.getModel().getColumnCount()>1);
			}
		}

		//enable actions based on selection
		enableCrossTableActions();

		//enable actions based on table state - these can not be in base class because mtrxtable controls them in mtrxactiontab
   		//MTRX_PIVOT
   		((PivotAction)getAction(MTRX_PIVOT)).setEnabled(true);//.setTable(crssTable);
   		//MTRX_CANCEL
   		((CancelAction)getAction(MTRX_CANCEL)).setEnabled(true);//setTable(crssTable);
   		//MTRX_SAVE
   		((SaveAction)getAction(MTRX_SAVE)).setEnabled(true);//setTable(crssTable);
	}

}
