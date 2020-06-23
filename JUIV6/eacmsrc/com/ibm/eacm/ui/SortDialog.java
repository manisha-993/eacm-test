//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.RowSorter.SortKey;

import com.ibm.eacm.actions.*;
import com.ibm.eacm.objects.*;

/******************************************************************************
* This is used to display the sort dialog
* @author Wendy Stimpson
*/
// $Log: SortDialog.java,v $
// Revision 1.3  2015/01/05 19:15:34  stimpsow
// use Theme for background colors
//
// Revision 1.2  2013/07/29 18:32:38  wendy
// allow sort dialog to show current keys
//
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//

public class SortDialog extends EACMDialog implements EACMGlobals, ActionListener
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.3 $";
    
	private static final int MAXSORT = 4;		
    private Sortable sortable = null;

	private JButton	m_btnRun = null;
	private JButton	m_btnCancel = null;
	private JButton m_btnReset = null;

	private JPanel sortPanel = new JPanel(null);
	private SortCriteria scArray[] = new SortCriteria[MAXSORT];
    
    private SortColumn sortColumnArray[] = null;
	
	/***
	 * 
	 * @param owner
	 * @param sort
	 */
    public SortDialog(Window owner, Sortable sort)  {
        super(owner,"sort.panel",JDialog.ModalityType.DOCUMENT_MODAL);//allow eacm frame to come to front
     
    	closeAction = new CloseDialogAction(this);
    	
     	sortable = sort;
     	
     	this.setTitle(this.getTitle()+" "+sortable.getTableTitle());
    	
    	setIconImage(Utils.getImage(DEFAULT_ICON));
    	
    	init();
    	       	
        finishSetup(owner);
      
        setSize(getPreferredSize());
    }
    
    /**
     * init dialog components
     */
    private void init() {
    	createActions();
    	
    	createMenuBar();
   
     	sortColumnArray = sortable.getVisibleColumnNames();
    	Arrays.sort(sortColumnArray);
    	
    	SortCriteria sc = new SortCriteria("first",0);
    	sc.setSortEnabled(true);
    	sc.getCombo().addActionListener(this);
    	scArray[0]=sc;
    	sc = new SortCriteria("second",1);
    	sc.setSortEnabled(false);
    	sc.getCombo().addActionListener(this);
    	sc.addPredecessor(scArray[0].getCombo());
    	scArray[1]=sc;
    	sc = new SortCriteria("third",2);
    	sc.setSortEnabled(false);
    	sc.getCombo().addActionListener(this);
    	sc.addPredecessor(scArray[0].getCombo());
    	sc.addPredecessor(scArray[1].getCombo());
    	scArray[2]=sc;
    	sc = new SortCriteria("fourth",3);
    	sc.setSortEnabled(false);
    	sc.getCombo().addActionListener(this);
    	sc.addPredecessor(scArray[0].getCombo());
    	sc.addPredecessor(scArray[1].getCombo());
    	sc.addPredecessor(scArray[2].getCombo());
    	scArray[3]=sc;
    	
    	List<? extends SortKey>curSortKeys = sortable.getSortKeys();
    	if(curSortKeys!=null){
    		SortColumn origArray[] = sortable.getVisibleColumnNames();
    		for (int i=0; i<curSortKeys.size(); i++){
				RowSorter.SortKey skey = curSortKeys.get(i);
				// find matching column
				int sortkeycol =skey.getColumn();
				String colname = origArray[sortkeycol].toString();
				for(int ii=0;ii<sortColumnArray.length;ii++){
					if(colname.equals(sortColumnArray[ii].toString())){
						scArray[i].getCombo().removeActionListener(this);
						scArray[i].setSort(ii,skey.getSortOrder());
						scArray[i].getCombo().addActionListener(this);
						break;
					}
				}
				
				if(i == scArray.length){
					break;
				}
			}
    		getAction(SRTOK_ACTION).setEnabled(true);
    	}
       	
    	JPanel m_pBtn = new JPanel(new GridLayout(1,3,5,5));
    	
		m_btnRun = new JButton(getAction(SRTOK_ACTION));	
		m_btnRun.setMnemonic((char)((Integer)m_btnRun.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(m_btnRun);
		
		m_btnReset = new JButton(getAction(SRTCLEAR_ACTION));	
		m_btnReset.setMnemonic((char)((Integer)m_btnReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(m_btnReset);		
			
		m_btnCancel = new JButton(getAction(CANCEL_ACTION));	
		m_btnCancel.setMnemonic((char)((Integer)m_btnCancel.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(m_btnCancel);
		   	
        GroupLayout layout = new GroupLayout(sortPanel);
        sortPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
        for (int i=0; i<MAXSORT; i++){
        	leftToRight.addComponent(scArray[i]);
        }
        leftToRight.addComponent(m_pBtn);
            
        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
        for (int i=0; i<MAXSORT; i++){
        	topToBottom.addComponent(scArray[i]);
        }
        topToBottom.addComponent(m_pBtn);
        
        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);
        
        if (sortColumnArray.length<MAXSORT){
        	for (int i=sortColumnArray.length; i<MAXSORT; i++){
        		scArray[i].setVisible(false);
        	}
        }
        getContentPane().add(sortPanel);
    }
    
    /**
     * actionPerformed
     * 
     * @param _action
     */
    private boolean isupdating = false;
	public void actionPerformed(ActionEvent e) {
		if (isupdating){
			return;
		}
		int index = Integer.parseInt(e.getActionCommand());
		isupdating = true;
		if (e.getActionCommand().equals("0")){
			getAction(SRTOK_ACTION).setEnabled(true);
		}
		scArray[index].enableButtons(true);
		index++;
		if (index<MAXSORT){
			scArray[index].setSortEnabled(true);
		}
		for (int i=index+1; i<MAXSORT; i++){
			scArray[i].reset(false);
		}
		isupdating = false;
    }
    /**
     * createMenuBar
     */
    private void createMenuBar() {
        menubar = new JMenuBar();
        
    	createFileMenu();

    	setJMenuBar(menubar);
    }
    private void createFileMenu() {
        JMenu mnuFile = new JMenu(Utils.getResource(FILE_MENU));
        mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));

    	JMenuItem mi = mnuFile.add(closeAction);
    	mi.setVerifyInputWhenFocusTarget(false);

		menubar.add(mnuFile);
    }
    /**
     * create all of the actions
     */
    private void createActions() {
        EACMAction act = new OkAction();
        addAction( act);
        act = new ClearAction();
        addAction( act);
        act = new CancelAction();
        addAction( act);
    }
    
    public void dereference(){
    	super.dereference();
 
    	sortable = null;
    	sortColumnArray = null;
    	
        for (int i=0; i<MAXSORT; i++){
        	scArray[i].getCombo().removeActionListener(this);
        	scArray[i].dereference();
        }
        scArray = null;
        
    	m_btnRun.setAction(null);
    	m_btnRun.setUI(null);
    	m_btnRun = null;
    	m_btnCancel.setAction(null);
    	m_btnCancel.setUI(null);
    	m_btnCancel = null;
    	m_btnReset.setAction(null);
    	m_btnReset.setUI(null);
    	m_btnReset = null;
     	  	
    	removeAll();
    	sortPanel.setLayout(null); 
    	sortPanel.setUI(null);
    }
    
    private class OkAction extends EACMAction 
    {
		private static final long serialVersionUID = 1L;
		OkAction() {
            super(SRTOK_ACTION);
            setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			SortDialog.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			sort(); 
	      	SortDialog.this.setCursor(Cursor.getDefaultCursor());
		} 	
    } 
	private void sort() {
	      //The precedence of the columns in the sort is indicated by the order of the sort keys in the sort key list.
        List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        
    	for (int i=0;i<MAXSORT;++i) {
    		SortCriteria sc = scArray[i];
    		SortColumn o = sc.getSelectedItem();
    		if (o != null) {
    	        sortKeys.add(new RowSorter.SortKey(o.getColumnIndex(), 
    	        		 sc.getDirection()?SortOrder.ASCENDING:SortOrder.DESCENDING)); 
    		}
    	}
	
    	sortable.sort(sortKeys);	
		// close the dialog
		closeAction.actionPerformed(null);
	}

    private class ClearAction extends EACMAction 
    {
		private static final long serialVersionUID = 1L;
		ClearAction() {
            super(SRTCLEAR_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			reset(); 
		} 	
    } 
	private void reset() {

		for (int i=0;i<MAXSORT;++i) {
			scArray[i].reset(i==0);
		}
		getAction(SRTOK_ACTION).setEnabled(false);
	}

	private class CancelAction extends EACMAction 
	{
		private static final long serialVersionUID = 1L;
		CancelAction() {
			super(CANCEL_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			closeAction.actionPerformed(e); 
		} 	
	} 
	
	private class SortCriteria extends JPanel {
		private static final long serialVersionUID = 1L;
		private JComboBox cmboSort = new JComboBox(){
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * needed because metal lnf and using themes change the color - make it look like it did
			 * @see java.awt.Component#getBackground()
			 */
			public Color getBackground() {
				return Color.white;
			}
		};
		private JLabel headerLbl = null;
		private ButtonGroup btnGroup = null;
		private Vector<JComboBox> comboVct = null;
		private JRadioButton rbAsc, rbDesc;
		private SortCriteria(String labelkey, int index){
			super(new BorderLayout());
			
			headerLbl = new JLabel(Utils.getResource(labelkey));
			headerLbl.setLabelFor(cmboSort);
		    headerLbl.setDisplayedMnemonic(Utils.getMnemonic(labelkey));
			
			for (int i=0; i<sortColumnArray.length; i++){
				cmboSort.addItem(sortColumnArray[i]);
			}
			Dimension curdim = cmboSort.getPreferredSize();
			Dimension adjdim = new Dimension(curdim.width+5,curdim.height); // allow for arrow
			cmboSort.setSize(adjdim);
			cmboSort.setPreferredSize(adjdim);
		
			cmboSort.setSelectedItem(null);
			cmboSort.setActionCommand(""+index);

			btnGroup = new ButtonGroup();
			rbAsc = new JRadioButton(Utils.getResource("srtA"),true);
			rbDesc = new JRadioButton(Utils.getResource("srtD"),false);	
			
			btnGroup.add(rbAsc);
			btnGroup.add(rbDesc);
			
			add(headerLbl,"North");
			add(cmboSort,"West");
			JPanel btnPanel = new JPanel(new BorderLayout());
			btnPanel.add(rbAsc,"West");
			btnPanel.add(rbDesc,"East");
			add(btnPanel,"East");
		}
		
		void setSort(int col, SortOrder order){
			if(col<cmboSort.getItemCount()){
				cmboSort.setSelectedIndex(col);
				rbAsc.setSelected(order ==SortOrder.ASCENDING);
				rbDesc.setSelected(order ==SortOrder.DESCENDING);
				enableButtons(true);
				cmboSort.setEnabled(true);
			}
		}
		
		JComboBox getCombo(){ return cmboSort;}
		SortColumn getSelectedItem() {return (SortColumn)cmboSort.getSelectedItem();}
		boolean getDirection() { return rbAsc.isSelected();}
		
		/**
		 * when a predecessor has a selected value, remove it from this one
		 * @param combo
		 */
		void addPredecessor(JComboBox combo){
			if (comboVct==null){
				comboVct = new Vector<JComboBox>();
			}
			comboVct.add(combo);
		}
		/**
		 * reset all selections
		 * only the first SortCriteria will be set to true
		 * @param b
		 */
		void reset(boolean b){
			if (!b){
				if (cmboSort.getSelectedItem()!=null){ // something was changed, reset all
					cmboSort.removeAllItems();
					for (int i=0; i<sortColumnArray.length; i++){
						cmboSort.addItem(sortColumnArray[i]);
					}
				}
			}
			cmboSort.setSelectedItem(null);
			setSortEnabled(b);
		}
		void setSortEnabled(boolean b){
			if(b){
				if (cmboSort.getSelectedItem()!=null){
					cmboSort.removeAllItems();
					for (int i=0; i<sortColumnArray.length; i++){
						cmboSort.addItem(sortColumnArray[i]);
					}
					cmboSort.setSelectedItem(null);
				}
				// look at predecessors and remove any selected values
				if (comboVct!=null){
					for (int i=0; i<comboVct.size(); i++){
						cmboSort.removeItem(comboVct.elementAt(i).getSelectedItem());
					}
				}
			}
			cmboSort.setEnabled(b);
			
			rbAsc.setSelected(true);
			enableButtons(false);
		}
		void enableButtons(boolean b){
			rbAsc.setEnabled(b);
			rbDesc.setEnabled(b);	
		}
		void dereference(){
			cmboSort.removeAllItems();
			cmboSort.removeAll();
			cmboSort.removeNotify();
			cmboSort.setUI(null);
			
			headerLbl.setLabelFor(null);
			headerLbl.removeAll();
			headerLbl.removeNotify();
			headerLbl.setUI(null);
			
			btnGroup.remove(rbAsc);
			btnGroup.remove(rbDesc);
			
			if (comboVct!=null){
				comboVct.clear();
				comboVct = null;
			}
			
			removeAll();
			removeNotify();
			setUI(null);
		}
	}
}