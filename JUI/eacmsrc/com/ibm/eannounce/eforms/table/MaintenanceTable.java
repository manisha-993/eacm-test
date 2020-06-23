/**
 * Copyright (c) 2004-2005 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * for cr_FlagUpdate
 *
 * @version 3.0  2005/03/02
 * @author Anthony C. Liberto
 *
 * $Log: MaintenanceTable.java,v $
 * Revision 1.3  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/06/04 18:42:53  wendy
 * RQ110306297 support max length rule on long description for flags
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:05  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/03/24 20:51:04  tony
 * jt_20050324
 * added ability to unretire flags
 *
 * Revision 1.4  2005/03/08 20:33:53  tony
 * added updateFlagCodes logic
 *
 * Revision 1.3  2005/03/04 18:35:55  tony
 * updated selection criteria
 *
 * Revision 1.2  2005/03/03 22:19:05  tony
 * cr_FlagUpdate
 * improved functionality
 *
 * Revision 1.1  2005/03/03 21:48:11  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
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
public class MaintenanceTable extends HorzTable {
	private static final long serialVersionUID = 1L;
	private SimpleTextEditor text = new SimpleTextEditor();
	private SimpleMaxTextEditor maxText = new SimpleMaxTextEditor(); // RQ110306297

    /**
     * MaintenanceTable
     *
     * @author Anthony C. Liberto
     */
    public MaintenanceTable() {
		this(null,null);
		return;
	}

	private MaintenanceTable(FilterGroup _group, RowSelectableTable _table) {
		super(_group,_table);
		init();
		cgtm.setType(TABLE_MAINTENANCE);
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
		setColumnSelectionAllowed(false);
		getTableHeader().setReorderingAllowed(false);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setOpaque(false);
		setDefaultRenderer(Object.class, new SingleRenderer());
		setSearchable(true);
		setReplaceable(false);
		return;
	}

    /**
     * refreshTable
     *
     * @author Anthony C. Liberto
     * @param _list
     */
    public void refreshTable(MetaFlagMaintList _list) {
		if (_list != null) {
			cgtm.setObject(_list);
			updateModel(_list.getTable());
			createDefaultColumnsFromModel();
			refreshTable(true);
			if (isFocusable()) {
				setRowSelectionInterval(0,0);
				setColumnSelectionInterval(0,0);
				scrollToRow(0);
			}
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
			} else if (_e instanceof KeyEvent) {
				KeyEvent ke = (KeyEvent)_e;
				if (ke.isControlDown() || ke.isMetaDown() || ke.isAltDown() || ke.isActionKey()) {
					return false;
				}
			}
		}

		if (cgtm.isCellEditable(_r,_c)) {
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
					((SimpleEditor)editorComp).prepareToEdit();
					return true;
				}
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
	    if (o instanceof SimpleTextAttribute) {
			if (((SimpleTextAttribute)o).getKey().equals(MetaFlagMaintItem.LONGDESCRIPTION)){ // RQ110306297
				MetaFlagMaintList mlist = (MetaFlagMaintList)cgtm.getObject();
				MetaMaintActionItem mmai = mlist.getParentActionItem();
				maxText.setFoundation((EANFoundation)o);
				maxText.setMaxLen(mmai.getMaxLength(mlist.getMetaFlag()));
				maxText.setMetaAttr(mlist.getMetaFlag());
				tce = maxText;
			}else{
				text.setFoundation((EANFoundation)o);
				tce = text;
			}
		} else {
			System.out.println("MaintenanceTable.getCellEditor r:"+_r+" c "+_c+" class1: " + o.getClass().getName());
		}
		return tce;
	}

    /**
     * getUIPrefKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() {
        return "HORZ_MAINT";
    }

    /**
     * getFoundation
     *
     * @author Anthony C. Liberto
     * @param _r
     * @param _c
     * @return
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
					toggleCommit(true);
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
     *
     * @author Anthony C. Liberto
     */
    public void addRow() {
		int iLastRow = getRowCount();
		cgtm.addRow(true,false);
		if (getColumnCount() == 0) {
			createDefaultColumnsFromModel();
		}
		refreshTable(true);
		if (iLastRow < getRowCount()) {
			scrollToRow(iLastRow);
			setRowSelectionInterval(iLastRow,iLastRow);
			setColumnSelectionInterval(0,0);
		}
		return;
	}

    /**
     * remove
     *
     * @author Anthony C. Liberto
     */
    public void remove() {
		int[] rows = getSelectedRows();
		int[] mRows = null;
		int ii = 0;
		if (rows != null) {
			ii = rows.length;
			mRows = new int[ii];
			for (int i=0;i<ii;++i) {
				mRows[i] = cgtm.getRowIndex(cgtm.getModelKey(rows[i]));
			}
		}
		if (mRows != null) {
			RowSelectableTable tmpRST = cgtm.getTable();
			dBase().retireFlags(tmpRST,mRows,this);
			dBase().updateFlagCodes(tmpRST,getEntityList(),this);
		}
		repaint();
		return;
	}

    /**
     * unexpireFlags
     *
     * @author Anthony C. Liberto
     */
    public void unexpireFlags() {
		int[] rows = getSelectedRows();
		int[] mRows = null;
		int ii = 0;
		if (rows != null) {
			ii = rows.length;
			mRows = new int[ii];
			for (int i=0;i<ii;++i) {
				mRows[i] = cgtm.getRowIndex(cgtm.getModelKey(rows[i]));
			}
		}
		if (mRows != null) {
			RowSelectableTable tmpRST = cgtm.getTable();
			dBase().unexpireFlags(tmpRST,mRows,this);
			dBase().updateFlagCodes(tmpRST,getEntityList(),this);
		}
		repaint();
		return;
	}

    /**
     * @see java.awt.Container#removeAll()
     * @author Anthony C. Liberto
     */
    public void removeAll(){
		cgtm.removeAll();
		return;
	}

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
		Object o = cgtm.getRow(_row);
		if (o != null && o instanceof EANFoundation) {
			return ((EANFoundation)o).getKey();
		}
		return super.getAccessibleRowNameAt(_row);
	}

    /**
     * commit
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean commit() {
        boolean out = false;
        Exception e = null;
        RowSelectableTable tmpRST = null;
        if (isEditing()) {
            if (!canStopEditing()) {
                return out;
            }
        }
        tmpRST = cgtm.getTable();
        e = dBase().commit(tmpRST,this);
        dBase().updateFlagCodes(tmpRST,getEntityList(),this);
        if (e != null) {
            showException(e,ERROR_MESSAGE,OK);
        } else {
            refresh();
            out = true;
        }
        return out;
	}

	/**
     * toggleCommit
     *
     * @author Anthony C. Liberto
     * @param _b
     */
    public void toggleCommit(boolean _b) {
		return;
	}

	/**
	 * getEntityList
	 *
	 * @author Anthony C. Liberto
	 * @return EntityList
	 */
	public EntityList getEntityList() {
		return null;
	}

    /**
     * rollback
     *
     * @author Anthony C. Liberto
     */
    public void rollback() {
//        System.out.println("rollback()");
        cgtm.rollback();
        resizeCells();
        return;
    }


    /**
     * rollbackSingle
     *
     * @author Anthony C. Liberto
     */
    public void rollbackSingle() {
        int r = getSelectedRow();
        int c = convertColumnIndexToModel(getSelectedColumn());
//        System.out.println("restting: " + r + ", " + c);
        if (isValidCell(r, c)) {
            cgtm.rollback(cgtm.getRowKey(r), cgtm.getColumnKey(c));
            resizeRow(r);
            resizeColumn(c);
        }
        return;
    }
}
