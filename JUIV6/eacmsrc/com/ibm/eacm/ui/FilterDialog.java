//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.ui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.*;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.nav.Navigate;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.table.BookmarkTable;
import com.ibm.eacm.table.FilterTable;

/******************************************************************************
 * This is used to display the filter dialog
 * @author Wendy Stimpson
 */
//$Log: FilterDialog.java,v $
//Revision 1.3  2013/07/29 18:31:46  wendy
//enable filter actions when a filter is typed
//
//Revision 1.2  2013/05/01 18:35:13  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:11  wendy
//Initial code
//

public class FilterDialog extends EACMDialog implements EACMGlobals, ActionListener,PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.3 $";

	public static final String ISFILTERED_KEY="_isFiltered";
	private static final String FILTER_CONSTANT = "MFILTER_";

	private Filterable filterable=null;

	private JPanel filterPanel = new JPanel(null);
	private JCheckBox m_chkCase = new JCheckBox(Utils.getResource("case"));

	private JButton m_btnAdd = null;
	private JButton m_btnR = null;
	private JButton m_btnRA = null;

	private JButton m_btnReset = null;
	private JButton m_btnRun = null;
	private JButton m_btnCancel = null;

	private FilterTable filterTable = null;
	private JScrollPane jsp = null;
	private Navigate nav;

	/**
	 * @param owner
	 * @param filt
	 */
	public FilterDialog(Window owner, Filterable filt)  {
		super(owner,"filter.panel",JDialog.ModalityType.DOCUMENT_MODAL);//allow eacm frame to come to front

		closeAction = new CloseAction(this);
		filterable = filt;
		setIconImage(Utils.getImage("fltr.gif"));
		filterTable = new FilterTable(EACM.getEACM().getCurrentTab().getProfile());
		jsp = new JScrollPane(filterTable);
		init();
		jsp.setFocusable(false);

		setFilterGroup();
		
		getAction(ADD_ACTION).setEnabled(allRowsValid());
		
		filterTable.resizeCells();
		
		filterTable.addPropertyChangeListener(DATACHANGE_PROPERTY, this);
		
		finishSetup(owner);
		setSize(new Dimension(500,300));
		m_btnAdd.requestFocusInWindow();
		
		setResizable(true);
	}
	/**
	 * init dialog components
	 */
	private void init() {
		createActions();

		filterTable.getSelectionModel().addListSelectionListener(
				(ListSelectionListener) getAction(REMOVESEL_ACTION));

		filterTable.addTextEditorListener((CellEditorListener)getAction(RUNFILTER_ACTION));
		filterTable.addTextEditorListener((CellEditorListener)getAction(ADD_ACTION));

		filterTable.setFillsViewportHeight(true);
		filterTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		m_chkCase.setMnemonic(Utils.getMnemonic("case"));
		m_chkCase.addActionListener(this);

		JPanel m_pBtn = new JPanel(new GridLayout(1,3,5,5));

		m_btnReset = new JButton(getAction(UNDOFILTER_ACTION));
		m_btnReset.setMnemonic((char)((Integer)m_btnReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(m_btnReset);

		m_btnRun = new JButton(getAction(RUNFILTER_ACTION));
		m_btnRun.setMnemonic((char)((Integer)m_btnRun.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(m_btnRun);

		m_btnCancel = new JButton(getAction(CLOSE_ACTION));
		m_btnCancel.setMnemonic((char)((Integer)m_btnCancel.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(m_btnCancel);

		JPanel m_pBtnTop = new JPanel(new GridLayout(1,3,5,5));
		m_btnAdd = new JButton(getAction(ADD_ACTION));
		m_btnAdd.setMnemonic((char)((Integer)m_btnAdd.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtnTop.add(m_btnAdd);
		m_btnR = new JButton(getAction(REMOVESEL_ACTION));
		m_btnR.setMnemonic((char)((Integer)m_btnR.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtnTop.add(m_btnR);
		m_btnRA= new JButton(getAction(REMOVEALL_ACTION));
		m_btnRA.setMnemonic((char)((Integer)m_btnRA.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtnTop.add(m_btnRA);

		
		GroupLayout layout = new GroupLayout(filterPanel);
		filterPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(m_chkCase);
		leftToRight.addComponent(m_pBtnTop);
		leftToRight.addComponent(jsp);
		leftToRight.addComponent(m_pBtn);
		

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(m_chkCase);
		topToBottom.addComponent(m_pBtnTop);
		topToBottom.addComponent(jsp);
		topToBottom.addComponent(m_pBtn);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);
		
		getContentPane().add(filterPanel);
	}
	/**
	 * load the filter table model - using any saved one first
	 */
	private void setFilterGroup() {
		boolean useCase = false;
		FilterGroup fGroup = getSavedFilterGroup();
		if (fGroup != null) {
			Object filtertbl = filterable.getFilterableTable();
			if (filterable instanceof BookmarkTable) {
				fGroup.refresh((SimpleTableModel)filtertbl);
			} else {
				fGroup.refresh((RowSelectableTable)filtertbl);
			}

			if(filterable.isPivoted()){
				filterable.setColFilterGroup(fGroup);
			}else{
				filterable.setFilterGroup(fGroup);
			}
			filterTable.loadTableModel(fGroup);
		} else {
			if(filterable.isPivoted()){
				fGroup = filterable.getColFilterGroup();
			}else{
				fGroup = filterable.getFilterGroup();
			}

			filterTable.loadTableModel(fGroup);
		}
		if (fGroup!=null){
			useCase = fGroup.isCaseSensitive();
		}
		m_chkCase.setSelected(useCase);

		filterTable.getModel().addTableModelListener((TableModelListener)getAction(RUNFILTER_ACTION));
		filterTable.getModel().addTableModelListener((TableModelListener)getAction(ADD_ACTION));

		getAction(REMOVEALL_ACTION).setEnabled(filterTable.getRowCount()>0);
		getAction(RUNFILTER_ACTION).setEnabled(anyRowsValid());
	}

	/**
	 * get any saved filtergroup
	 * @return
	 */
	private FilterGroup getSavedFilterGroup() {
		FilterGroup out = null;
		String key = getConstant() + filterable.getUIPrefKey();
		Object o = SerialPref.getPref(key);
		if (o instanceof FilterGroup) {
			out = (FilterGroup) o;
		}

		return out;
	}

	/**
	 * create all of the actions
	 */
	private void createActions() {
		EACMAction act = new RunFilterAction();
		addAction( act);
		act = new UndoFilterAction();
		addAction( act);
		act = new CancelAction();
		addAction( act);
		act = new AddFilterAction();
		addAction( act);
		act = new RemoveSelAction();
		addAction( act);
		act = new RemoveAllAction();
		addAction( act);
	}

	/**
	 * used for back nav reload of filters
	 * @param n
	 */
	public void setNavigate(Navigate n){
		nav = n;
	}
	/**
	 * called when case sensitive cb is changed
	 */
	public void actionPerformed(ActionEvent e) {
		FilterGroup fg = null;
		if(filterable.isPivoted()){
			fg = filterable.getColFilterGroup();
		}else{
			fg = filterable.getFilterGroup();
		}
		if (fg != null) {
			fg.setCaseSensitive(m_chkCase.isSelected());
		}
	}
	/******
	 * release memory
	 */
	public void dereference(){
		filterTable.removePropertyChangeListener(DATACHANGE_PROPERTY, this);
		
		filterTable.getSelectionModel().removeListSelectionListener(
				(ListSelectionListener) getAction(REMOVESEL_ACTION));

		filterTable.removeTextEditorListener((CellEditorListener)getAction(RUNFILTER_ACTION));
		filterTable.removeTextEditorListener((CellEditorListener)getAction(ADD_ACTION));
		filterTable.getModel().removeTableModelListener((TableModelListener)getAction(RUNFILTER_ACTION));
		filterTable.getModel().removeTableModelListener((TableModelListener)getAction(ADD_ACTION));

		super.dereference(); // this derefs the actiontbl

		filterable = null;

		jsp.removeAll();
		jsp.setUI(null);
		jsp = null;

		nav  = null;
		
		filterTable.dereference();
		filterTable = null;

		m_chkCase.removeActionListener(this);
		m_chkCase.removeAll();
		m_chkCase.setUI(null);
		m_chkCase = null;

		m_btnRun.setAction(null);
		m_btnRun.setUI(null);
		m_btnRun = null;
		m_btnCancel.setAction(null);
		m_btnCancel.setUI(null);
		m_btnCancel = null;
		m_btnReset.setAction(null);
		m_btnReset.setUI(null);
		m_btnReset = null;
		m_btnAdd.setAction(null);
		m_btnAdd.setUI(null);
		m_btnAdd = null;
		m_btnR.setAction(null);
		m_btnR.setUI(null);
		m_btnR = null;
		m_btnRA.setAction(null);
		m_btnRA.setUI(null);
		m_btnRA=null;

		removeAll();
		filterPanel.setUI(null);
		filterPanel.setLayout(null);
		filterPanel = null;
	}

	private class RemoveAllAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		RemoveAllAction() {
			super(REMOVEALL_ACTION);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			filterTable.cancelCurrentEdit();

			filterTable.removeAllItems();
			getAction(REMOVEALL_ACTION).setEnabled(filterTable.getRowCount()>0);
			String uipref = filterable.getUIPrefKey();
			if (uipref != null){
				String key = getConstant() + uipref;
				SerialPref.removePref(key);
				SerialPref.removePref(key+ISFILTERED_KEY);
			}
		}
	}

	private class RemoveSelAction extends EACMAction implements ListSelectionListener  {
		private static final long serialVersionUID = 1L;
		RemoveSelAction() {
			super(REMOVESEL_ACTION);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			filterTable.cancelCurrentEdit();

			filterTable.removeSelected();
			getAction(REMOVEALL_ACTION).setEnabled(filterTable.getRowCount()>0);
			String uipref = filterable.getUIPrefKey();
			if (uipref != null && filterTable.getRowCount()==0){
				String key = getConstant() + uipref;
				SerialPref.removePref(key);
				SerialPref.removePref(key+ISFILTERED_KEY);
			}
		}

		/**
		 * only enable this if something is selected
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				setEnabled(!lsm.isSelectionEmpty());
			}
		}
	}

	private class AddFilterAction extends EACMAction implements CellEditorListener,TableModelListener {
		private static final long serialVersionUID = 1L;
		AddFilterAction() {
			super(ADD_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			filterTable.saveCurrentEdit();

			filterTable.addRow();
			getAction(REMOVEALL_ACTION).setEnabled(filterTable.getRowCount()>0);
		}
		public void editingCanceled(ChangeEvent e) {
			setEnabled(allRowsValid());
		}

		public void editingStopped(ChangeEvent e) {
			setEnabled(allRowsValid());
			m_btnAdd.requestFocusInWindow();
		}

		public void tableChanged(TableModelEvent e) {
			setEnabled(allRowsValid());
		}
	}
	private class RunFilterAction extends EACMAction implements CellEditorListener,TableModelListener   {
		private static final long serialVersionUID = 1L;
		RunFilterAction() {
			super(RUNFILTER_ACTION);
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			filterTable.saveCurrentEdit();
			// this will run all filters in the filtergroup
			filterable.filter();
	
			if(nav != null){
				nav.saveFilterGroup();
			}
			getAction(UNDOFILTER_ACTION).setEnabled(filterable.isFiltered());
		}

		public void editingCanceled(ChangeEvent e) {
			setEnabled(anyRowsValid());
		}

		public void editingStopped(ChangeEvent e) {
			setEnabled(anyRowsValid());
		}

		public void tableChanged(TableModelEvent e) {
			setEnabled(anyRowsValid());
		}
	}

	private boolean allRowsValid() {
		boolean isok = true;
		FilterGroup fGroup = null;
		if(filterable.isPivoted()){
			fGroup = filterable.getColFilterGroup();
		}else{
			fGroup = filterable.getFilterGroup();
		}
		if (fGroup!=null){
			for (int i = 0; i < fGroup.getFilterItemCount(); i++) {
				if (!fGroup.getFilterItem(i).isComplete()) {
					isok = false;
					break;
				}
			}
		}else{
			isok = false;
		}
		return isok;
	}

	private boolean anyRowsValid() {
		boolean isok = false;
		FilterGroup fGroup = null;
		if(filterable.isPivoted()){
			fGroup = filterable.getColFilterGroup();
		}else{
			fGroup = filterable.getFilterGroup();
		}

		if (fGroup!=null && fGroup.getFilterItemCount()>0){
			for (int i = 0; i < fGroup.getFilterItemCount(); i++) {
				if (fGroup.getFilterItem(i).isComplete()) {
					isok = true;
					break;
				}
			}
		}
		return isok;
	}
	private void saveFilterGroup() {
		FilterGroup fg = null;
		if(filterable.isPivoted()){
			fg = filterable.getColFilterGroup();
		}else{
			fg = filterable.getFilterGroup();
		}
		String uipref = filterable.getUIPrefKey();
		if (fg != null && uipref != null) {
			String key = getConstant() + uipref;
			try {
				// must copy because original will be dereferenced
				SerialPref.putPref(key, fg.copy());
				SerialPref.putPref(key+ISFILTERED_KEY, filterable.isFiltered());
			} catch (MiddlewareException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * used to find key for saved filters
	 * @return
	 */
	public static String getConstant() {
		Profile prof = EACM.getEACM().getCurrentTab().getProfile();
		if (prof != null) {
			return prof.getEnterprise() + "_" + prof.getOPWGID() + "_"+FILTER_CONSTANT;
		}
		return FILTER_CONSTANT;
	}

	private class UndoFilterAction extends EACMAction  {
		private static final long serialVersionUID = 1L;
		UndoFilterAction() {
			super(UNDOFILTER_ACTION);
			setEnabled(filterable.isFiltered());
		}

		public void actionPerformed(ActionEvent e) {
			filterable.resetFilter();
			setEnabled(false);

			if(nav != null){
				nav.removeFilterGroup();
			}
			// save updated filter state
			String uipref = filterable.getUIPrefKey(); 
			if (uipref != null) {
				String key = getConstant() + uipref;
				SerialPref.putPref(key+ISFILTERED_KEY, filterable.isFiltered());
			}
		}
	}

	private class CloseAction extends CloseDialogAction {
		private static final long serialVersionUID = 1L;
		CloseAction(EACMDialog f) {
			super(f);
		}
		public void actionPerformed(ActionEvent e) {
			if (filterTable.getRowCount()>0){
				filterTable.cancelCurrentEdit(); // if an editor is open, force it to close
				saveFilterGroup();
			}
			Component comp = (Component)filterable; // this will be dereferenced when dialog closes
			super.actionPerformed(e);
			comp.requestFocusInWindow(); // crosstable in matrixtab may not have focus
		}
	}
	private class CancelAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		CancelAction() {
			super(CLOSE_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			filterTable.cancelCurrentEdit();
			closeAction.actionPerformed(e);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(DATACHANGE_PROPERTY)) {
			boolean enable = false;
			if(event.getNewValue() instanceof Boolean){
				enable = ((Boolean)event.getNewValue()).booleanValue();
			}
			getAction(ADD_ACTION).setEnabled(enable);
			getAction(RUNFILTER_ACTION).setEnabled(enable);
		}
	}
}