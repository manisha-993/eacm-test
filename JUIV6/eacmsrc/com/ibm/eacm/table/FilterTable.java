//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;


import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.ibm.eacm.EACM;
import com.ibm.eacm.editor.*;


/**
 * This the table used for the Filter action
 * @author Wendy Stimpson
 */
// $Log: FilterTable.java,v $
// Revision 1.2  2013/07/29 18:37:22  wendy
// enable filter actions when a filter is typed
//
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class FilterTable extends HorzTable implements DocumentListener
{
	private static final long serialVersionUID = 1L;

    private PickListCellEditor pickListEd =null;
	private SimpleTextCellEditor textEd = null;

	protected RSTTableModel createTableModel(){ 
		return new FilterTableModel(this);
	}
	/**
     * FilterTable
     */
    public FilterTable(Profile p) {
    	super(null, p);
    	setSurrendersFocusOnKeystroke(true); // for accessibility, let user edit using keystrokes
		init();
	    pickListEd = new PickListCellEditor(); 
	    textEd = new SimpleTextCellEditor(this);
	}

    public void addTextEditorListener(CellEditorListener l){
    	textEd.addCellEditorListener(l);
    }
    public void removeTextEditorListener(CellEditorListener l){
    	textEd.removeCellEditorListener(l);
    }

    /**
     * init
     */
    protected void init() {
    	super.init();

		setColumnSelectionAllowed(true);
		getTableHeader().setReorderingAllowed(false);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setOpaque(false);
		
        if (getRowCount() > 0) {
            setRowSelectionInterval(0, 0);
        }
        if (getColumnCount() > 0) { 
            setColumnSelectionInterval(0, 0);
        } 
	}
    /**
     * get the accessibility resource key
     * @return
     */
    protected String getAccessibilityKey() {
    	return "accessible.fltrTable";
    }
    /**
     * release memory
     * @see com.ibm.eacm.table.BaseTable#dereference()
     */
    public void dereference(){
        pickListEd.dereference();
        pickListEd = null;

        textEd.dereference();
        textEd = null;
        
      	super.dereference();
    }

	/**
     * load the table model using the FilterGroup
     *     
     * @param group
     */
    public void loadTableModel(FilterGroup group) {
    	if (group != null) {
    		Profile prof = EACM.getEACM().getCurrentTab().getProfile();
    		updateModel(group.getFilterGroupTable(),prof);
    	}
	}

    /**
     * return an editor based on type of object in that cell
     *
     * @param r
     * @param c
     * @return
     */
    public TableCellEditor getCellEditor(int r, int c) {
		TableCellEditor tce = null;
		Object o = getEANObject(r,c);
		if (o instanceof SimplePicklistAttribute) {
			SimplePicklistAttribute spa = (SimplePicklistAttribute)o;
			// assumption is that only single value will be selectable
			pickListEd.setPickListAttribute(spa);
			tce =pickListEd;
		} else if (o instanceof SimpleTextAttribute) {
			textEd.setTextAttribute((SimpleTextAttribute)o);
			tce = textEd;
		}
		if (tce==null){
			tce = super.getCellEditor(r,c);
		}
		return tce;
	}

    public Object getValueAt(int row, int column) {
    	Object obj = super.getValueAt(row, column);
    	if(obj instanceof String){
    		String str = obj.toString();
    		if (str.startsWith("imp:")){ //Implicators start with this, not sure why i have to trim it here 
    			obj = str.substring(4);
    		}
    	}
        return obj;
    }
    
    /***
     * called when editing has stopped
     * @see javax.swing.JTable#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(Object aValue, int row, int column) {
        ((FilterTableModel)getModel()).putValueAt(aValue, convertRowIndexToModel(row), convertColumnIndexToModel(column));
        resizeCells();
    }
	
	/**
	 * make sure current edit ends and is put into the table model
	 */
	public void saveCurrentEdit(){
	    TableCellEditor tce = getCellEditor();
		if (tce != null) {
			tce.stopCellEditing();
		}
	}

	/**
	 * add new row to the table model
	 */
	public void addRow() {
		((FilterTableModel)getModel()).addRow(this);		
		
		// select new row and first col
		int lastrow = getRowCount()-1;
		setColumnSelectionInterval(0, 0);
		setRowSelectionInterval(lastrow, lastrow);

		resizeCells();
	}
    
	/**
     * remove selected item
     */
    public void removeSelected() {
    	int viewrows[] = getSelectedRows();
    	int mdlrows[] = new int[viewrows.length];
    	for (int i = (viewrows.length - 1); i >= 0; i--) {
    		mdlrows[i]=this.convertRowIndexToModel(viewrows[i]);
    	}

    	 ((FilterTableModel)getModel()).removeRows(mdlrows);
    }

	/**
     * remove all items
     */
    public void removeAllItems(){
    	 ((FilterTableModel)getModel()).removeAllItems();		
	}

    /**
     * isRowFiltered
     *
     * @param r
     * @return
     */
    public boolean isRowFiltered(int r) {
		return false;
	}
/*
 accessibility
 */
//	 ""		 --> default
//	 ".ctab" --> cross table
//	 ".horz" --> horizontal
//	 ".mtrx" --> matrix
//	 ".vert" --> vertical

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getContext()
     */
    protected String getContext() {
		return ".horz";
	}
    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#isAccessibleCellEditable(int, int)
     */
    protected boolean isAccessibleCellEditable(int row, int col) {
		return true;
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getAccessibleRowNameAt(int)
     */
    protected String getAccessibleRowNameAt(int row) {
    	EANFoundation o =  ((RSTTableModel)getModel()).getRowEAN(row);				
		if (o instanceof EANFoundation) {			
			return ((EANFoundation)o).getKey();					
		}
		return super.getAccessibleRowNameAt(row);
	}
    
	/**
	 * used when text document changes to enable actions
	 * @param e
	 */
	public void changedUpdate(DocumentEvent e) {
		final boolean hasdata = e.getDocument().getLength()>0;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// only different values will actually fire an event - save and cancel actions listen for this
				firePropertyChange(DATACHANGE_PROPERTY, !hasdata, hasdata);
			}
		});
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent e) {
		final boolean hasdata = e.getDocument().getLength()>0;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// only different values will actually fire an event - save and cancel actions listen for this
				firePropertyChange(DATACHANGE_PROPERTY, !hasdata, hasdata);
			}
		});
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	public void removeUpdate(DocumentEvent e) {
		final boolean hasdata = e.getDocument().getLength()>0;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// only different values will actually fire an event - save and cancel actions listen for this
				firePropertyChange(DATACHANGE_PROPERTY, !hasdata, hasdata);
			}
		});
	}
}
