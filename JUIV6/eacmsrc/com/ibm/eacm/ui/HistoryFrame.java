//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.TableRowSorter;

import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.RSTTable;


/******************************************************************************
 * This is used to display the change history frame.
 * @author Wendy Stimpson
 */
//$Log: HistoryFrame.java,v $
//Revision 1.2  2013/07/29 18:40:46  wendy
//Turn off autosort, update classifications after edit and resize cells
//
//Revision 1.1  2012/09/27 19:39:11  wendy
//Initial code
//
public class HistoryFrame extends EACMFrame
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.2 $";


	private JPanel pnlMain = new JPanel(null);
	private JPanel pnlBtn = new JPanel(new GridLayout(1,1));
	private JButton btnOK;
	private HistoryTable table = null;
	private JScrollPane jsp = null;
	private String historyKey;

	/**
	 */
	public HistoryFrame(ChangeHistoryGroup chgGroup)  {
		//search.panel=EACM {0}
		super("search.panel", chgGroup.getChangeHistoryGroupTable().getTableTitle());

		historyKey = chgGroup.getChangeHistoryGroupTable().getTableTitle();
		closeAction = new CloseAction(this);

		init(chgGroup);

		finishSetup(EACM.getEACM());

		setResizable(true);
		enableActions();
	}


	/**
	 * init frame components
	 */
	private void init(ChangeHistoryGroup chgGroup) {
		Dimension d = new Dimension(600,400);
		table = new HistoryTable(chgGroup);

		btnOK = new JButton(closeAction);
		btnOK.setMnemonic((char)((Integer)closeAction.getValue(Action.MNEMONIC_KEY)).intValue());

		jsp = new JScrollPane(table);
		jsp.setFocusable(false);
		jsp.setPreferredSize(d);
		jsp.setSize(d);

		table.resizeCells();

		pnlBtn.add(btnOK);

		GroupLayout layout = new GroupLayout(pnlMain);
		pnlMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(jsp);
		leftToRight.addComponent(pnlBtn, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);// this centers it

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(jsp);
		topToBottom.addComponent(pnlBtn);


		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);
		getContentPane().add(pnlMain);
	}

	public String getHistoryKey(){
		return historyKey;
	}

	public void setHistoryKey(String key){
		historyKey=key;
	}

	public void updateHistory(ChangeHistoryGroup chgGroup)  {
		//search.panel=EACM {0}
		setTitle(Utils.getResource("search.panel", chgGroup.getChangeHistoryGroupTable().getTableTitle()));
		table.updateModel(chgGroup.getChangeHistoryGroupTable(), chgGroup.getProfile());
		historyKey = chgGroup.getChangeHistoryGroupTable().getTableTitle();
	}
	/**
	 * release memory
	 */
	public void dereference() {
		super.dereference();

		btnOK.setAction(null);
		btnOK.removeAll();
		btnOK.setUI(null);
		btnOK = null;

		table.dereference();
		table = null;

		pnlMain.removeAll();
		pnlMain.setUI(null);
		pnlMain = null;

		pnlBtn.removeAll();
		pnlBtn.setUI(null);
		pnlBtn = null;

		jsp.removeAll();
		jsp.setUI(null);
		jsp = null;

		historyKey=null;
	}

	private class CloseAction extends CloseFrameAction
	{
		private static final long serialVersionUID = 1L;
		CloseAction(EACMFrame f) {
			super(f,OK_ACTION);
		}
	}

	private class HistoryTable extends RSTTable {
		private static final long serialVersionUID = 1L;

		HistoryTable(ChangeHistoryGroup chgGroup) {
			super(chgGroup.getChangeHistoryGroupTable(),chgGroup.getProfile());

			init();
		}

		/**
		 * init
		 *
		 */
		protected void init() {
			super.init();
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		/**
		 * get the accessibility resource key
		 * @return
		 */
		protected String getAccessibilityKey() {
			return "accessible.histTable";
		}
	    /* (non-Javadoc)
	     * @see com.ibm.eacm.table.RSTTable#setSortKeys()
	     */
	    protected void setSortKeys(){
			List<? extends SortKey> origsortKeys = null;
			if(getRowSorter() != null){
				origsortKeys = ((TableRowSorter<?>)getRowSorter()).getSortKeys();
			}
	    	if (origsortKeys !=null && origsortKeys.size()>0){
	    		return; // keep the sort already specified
	    	}
	    	// must be done after the columns are setup in the model
	        //The precedence of the columns in the sort is indicated by the order of the sort keys in the sort key list.
	        List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
	        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));

	        setSortKeys(sortKeys);
	    }
	}
}