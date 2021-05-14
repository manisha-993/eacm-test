/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: SimpleTextEditor.java,v $
 * Revision 1.2  2008/01/30 16:27:07  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:55:54  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:13  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:59  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/01 00:27:54  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 23:42:28  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/04/11 20:02:29  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.editor.simple;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.event.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SimpleTextEditor extends ETextField implements SimpleEditor {
	private static final long serialVersionUID = 1L;
	private SimpleTextAttribute sta = null;
	/**
     * changeEvent
     */
    transient protected ChangeEvent changeEvent = null;

	/**
     * simpleTextEditor
     * @author Anthony C. Liberto
     */
    public SimpleTextEditor() {
		super();
		return;
	}
/*
 simpleEdit
*/
	/**
     * setFoundation
     *
     * @author Anthony C. Liberto
     * @param _ean
     */
    public void setFoundation(EANFoundation _ean) {
		sta = (SimpleTextAttribute)_ean;
	}

    /**
     * getFoundation
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANFoundation getFoundation() {
		return sta;
	}

    /**
     * prepareToEdit
     *
     * @author Anthony C. Liberto
     */
    public void prepareToEdit() {
		requestFocus();
		return;
	}

	/**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
		return sta.toString();
	}
/*
 tableCellEditor
*/
	/**
     * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void addCellEditorListener(CellEditorListener _cel) {
		listenerList.add(CellEditorListener.class, _cel);
		return;
	}

	/**
     * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void removeCellEditorListener(CellEditorListener _cel) {
		listenerList.remove(CellEditorListener.class, _cel);
		return;
	}

	/**
     * @see javax.swing.CellEditor#cancelCellEditing()
     * @author Anthony C. Liberto
     */
    public void cancelCellEditing() {
		fireCancel();
	}

	/**
     * fireCancel
     * @author Anthony C. Liberto
     */
    public void fireCancel() {
        Object[] listen = listenerList.getListenerList();
        for (int i = listen.length-2; i>=0; i-=2) {
            if (listen[i]==CellEditorListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener)listen[i+1]).editingCanceled(changeEvent);
            }
        }
        return;
	}

	/**
     * @see javax.swing.CellEditor#stopCellEditing()
     * @author Anthony C. Liberto
     */
    public boolean stopCellEditing() {
		fireStop();
		return true;
	}

	/**
     * fireStop
     * @author Anthony C. Liberto
     */
    public void fireStop() {
		Object[] listen = listenerList.getListenerList();
		for (int i = listen.length-2; i>=0; i-=2) {
			if (listen[i]==CellEditorListener.class) {
				if (changeEvent == null) {
					changeEvent = new ChangeEvent(this);
				}
				((CellEditorListener)listen[i+1]).editingStopped(changeEvent);
			}
		}
	}

	/**
     * @see javax.swing.CellEditor#getCellEditorValue()
     * @author Anthony C. Liberto
     */
    public Object getCellEditorValue() {
		return getText();
	}

	/**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     * @author Anthony C. Liberto
     */
    public Component getTableCellEditorComponent(JTable _tbl, Object _o, boolean _selected, int _row, int _col) {
		setText(_o.toString());
		return this;
	}

	/**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(EventObject _eo) {
		return true;
	}

	/**
     * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean shouldSelectCell(EventObject _eo) {
		return true;
	}
}
