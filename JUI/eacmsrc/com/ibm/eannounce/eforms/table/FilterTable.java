/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @version 1.2
 * @author Anthony C. Liberto
 *
 */

package com.ibm.eannounce.eforms.table;
import com.ibm.eannounce.eforms.editor.simple.*;
import com.ibm.eannounce.erend.SingleRenderer;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FilterTable extends HorzTable {
	private static final long serialVersionUID = 1L;
	/**
     * textCol
     */
 //   private final int textCol = 1;

	private SimpleListEditor list = new SimpleListEditor();
	private SimpleTextEditor text = new SimpleTextEditor();

    public boolean saveCurrentEdit() { 
    	boolean ok = super.saveCurrentEdit();
		System.err.println("saveCurrentEdit "+ok+" getRowCount() "+getRowCount());
    	// all rows must have a value or get error on Add
    	if(getRowCount()>0){
    		int lastRow = this.getRowCount()-1;
    		for(int c=0; c<this.getColumnCount(); c++){
    			Object obj = this.getValueAt(lastRow, c);
    			if(obj==null || obj.toString().trim().length()==0){
    				ok = false;
    				this.setRowSelectionInterval(lastRow, lastRow);
    				this.setColumnSelectionInterval(c, c);
    				break;
    			}
    		}
    	}

        return ok; 
    }
    
	/**
     * FilterTable
     * @author Anthony C. Liberto
     */
    public FilterTable() {
		this(null,null);
		return;
	}

	private FilterTable(FilterGroup _group, RowSelectableTable _table) {
		super(_group,_table);
		init();
		return;
	}

    /**
     * generateEditor
     *
     * @author Anthony C. Liberto
     */
    protected void generateEditor() {}

    /**
     * init
     *
     * @author Anthony C. Liberto
     */
    public void init() {
		initAccessibility("accessible.fltrTable");
		setRowMargin(0);
		setShowGrid(true);
		setColumnSelectionAllowed(true);
		getTableHeader().setReorderingAllowed(false);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setOpaque(false);
		setDefaultRenderer(Object.class, new SingleRenderer());						//access
		return;
	}

	/**
     * refreshTable
     * @param _group
     * @author Anthony C. Liberto
     */
    public void refreshTable(FilterGroup _group) {
		if (_group != null) {
			cgtm.setObject(_group);
			updateModel(_group.getFilterGroupTable());
			createDefaultColumnsFromModel();					//50873
			refreshTable(true);
		} else {
			cgtm.setObject(null);
			cgtm.setTable(null);
		}
		return;
	}

	/**
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean editCellAt(int _r, int _c, EventObject _e) {
        TableCellEditor tce = null;
        stopEditing();
        if (_e != null) {
			if (_e instanceof MouseEvent) {
				if (((MouseEvent)_e).getClickCount() != 2) {
					return false;
				}
			} else if (_e instanceof KeyEvent) {														//52328
				KeyEvent ke = (KeyEvent)_e;																//52328
				if (ke.isControlDown() || ke.isMetaDown() || ke.isAltDown() || ke.isActionKey()) {		//52328
					return false;																		//52328
				}																						//52328
			}
		}
		tce = getCellEditor(_r, _c);
		if (tce != null && tce.isCellEditable(_e)) {
			editorComp = prepareEditor(tce, _r, _c);
			if (editorComp == null) {
				removeEditor();
				return false;
			} else if (editorComp instanceof SimpleEditor) {
				editorComp.setBounds(getCellRect(_r,_c,false));
				add(editorComp);
				editorComp.validate();
				setCellEditor(tce);
				setEditingRowKey(_r);
				setEditingRow(_r);
				setEditingColumnKey(_c);
				setEditingColumn(_c);
				tce.addCellEditorListener(this);
				repaint();
				((SimpleEditor)editorComp).prepareToEdit();		//51433
				return true;
			}
		}
		return false;
	}

    /**
     * getCellEditor
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public TableCellEditor getCellEditor(int _r, int _c) {
		TableCellEditor tce = null;
		Object o = getFoundation(_r,_c);
		if (o instanceof SimplePicklistAttribute) {
			SimplePicklistAttribute spa = (SimplePicklistAttribute)o;
			if (spa.isMultiple()) {
				appendLog("Multiple SimplePicklist Attributes currently not supported");
			} else {
				list.setFoundation((EANFoundation)o);
				tce = list;
			}
		} else if (o instanceof SimpleTextAttribute) {
			text.setFoundation((EANFoundation)o);
			tce = text;
		}
		return tce;
	}

	/**
     * getFoundation
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public EANFoundation getFoundation(int _r, int _c) {
		Object o = getEANObject(_r,_c);
		if (o instanceof EANFoundation) {
			return (EANFoundation)o;
		}
		return null;
	}

    /**
     * @see javax.swing.event.CellEditorListener#editingStopped(javax.swing.event.ChangeEvent)
     * @author Anthony C. Liberto
     */
    public void editingStopped(ChangeEvent _ce) {
		TableCellEditor editor = getCellEditor();
		if (editor != null) {
			Object value = editor.getCellEditorValue();
			if (editor instanceof SimpleEditor) {
				if (putValueAt(value,getEditingRowKey(),getEditingColumnKey())) {
					removeEditor();
				}
			}
		}
		requestFocus();
		return;
    }

	/**
     * @see javax.swing.JTable#removeEditor()
     * @author Anthony C. Liberto
     */
    public void removeEditor() {
		TableCellEditor edit = getCellEditor();
		Rectangle cellRect = null;
        if(edit != null) {
			edit.removeCellEditorListener(this);
			requestFocus();
			if (editorComp != null) {
				remove(editorComp);
			}
			cellRect = getCellRect(editingRow, editingColumn, false);
			setCellEditor(null);
			setEditingColumn(-1);
			setEditingRow(-1);
			editorComp = null;
			repaint(cellRect);
		}
		return;
	}

	/**
     * addRow
     * @author Anthony C. Liberto
     */
    public void addRow() {
		if (getColumnCount() == 0) {
//acl_20030911			cgtm.addRow(false);
			cgtm.addRow(false,false);			//acl_20030911
			createDefaultColumnsFromModel();
		} else {
//acl_20030911			cgtm.addRow(false);
			cgtm.addRow(false,true);			//acl_20030911
		}
		refreshTable(true);
		return;
	}

	/**
     * remove
     * @author Anthony C. Liberto
     */
    public void remove() {
		cgtm.removeRows(getSelectedRows());
		return;
	}

	/**
     * @see java.awt.Container#removeAll()
     * @author Anthony C. Liberto
     */
    public void removeAll(){
//52345		selectAll();
//52345		remove();
		cgtm.removeAll();			//52345
		return;
	}

    /**
     * sort
     *
     * @author Anthony C. Liberto
     */
    public void sort(){}

    /**
     * isColumnVisible
     *
     * @param col
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isColumnVisible(int col) {
		return true;
	}

	/**
     * @see com.ibm.eannounce.eForms.table.GTable#getString(int, int, boolean)
     * @author Anthony C. Liberto
     */
    public String getString(int r, int c, boolean _b) {return "Default String";}
	/**
     * @see com.ibm.eannounce.eForms.table.GTable#setFound(int, int)
     * @author Anthony C. Liberto
     */
    public void setFound(int row, int c){}
	/**
     * @see com.ibm.eannounce.eForms.table.GTable#isFound(int, int)
     * @author Anthony C. Liberto
     */
    public boolean isFound(int row, int col){return false;}
	/**
     * isFound
     * @author Anthony C. Liberto
     * @return boolean
     * @param caseSensitive
     * @param find
     * @param str
     */
    public boolean isFound(String str, String find, boolean caseSensitive) {return false;}

    /**
     * isRowHidden
     * @param _r
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRowHidden(int _r) {
        return false;
	}

    /**
     * isRowFiltered
     *
     * @param _r
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRowFiltered(int _r) {
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
	/**
     * @see com.ibm.eannounce.eForms.table.GTable#getContext()
     * @author Anthony C. Liberto
     */
    protected String getContext() {
		return ".horz";
	}

    /**
     * getAccessibleValueAt
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public Object getAccessibleValueAt(int _row, int _col) {
		return getValueAt(_row,_col);
	}

    /**
     * getAccessibleColumnNameAt
     *
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleColumnNameAt(int _col) {
		return getColumnName(_col);
	}

    /**
     * getAccessibleRowNameAt
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleRowNameAt(int _row) {
		Object o = cgtm.getRow(_row);							//52521
		if (o != null && o instanceof EANFoundation) {			//52521
			return ((EANFoundation)o).getKey();					//52521
		}
		return super.getAccessibleRowNameAt(_row);
	}
}
