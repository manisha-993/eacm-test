/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: SimpleEditor.java,v $
 * Revision 1.1  2007/04/18 19:55:54  wendy
 * Reorganized JUI module
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
 * Revision 1.2  2005/01/26 23:42:27  tony
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
import COM.ibm.eannounce.objects.*;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface SimpleEditor extends TableCellEditor {
	/**
     * setFoundation
     * @param _ean
     * @author Anthony C. Liberto
     */
    void setFoundation(EANFoundation _ean);
	/**
     * getFoundation
     * @return
     * @author Anthony C. Liberto
     */
    EANFoundation getFoundation();
	/**
     * toString
     * @return
     * @author Anthony C. Liberto
     */
    String toString();
	/**
     * prepareToEdit
     * @author Anthony C. Liberto
     */
    void prepareToEdit();
/*
 tableCellEditor
*/
	/**
     * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    void addCellEditorListener(CellEditorListener _cel);
	/**
     * @see javax.swing.CellEditor#cancelCellEditing()
     * @author Anthony C. Liberto
     */
    void cancelCellEditing();
	/**
     * @see javax.swing.CellEditor#getCellEditorValue()
     * @author Anthony C. Liberto
     */
    Object getCellEditorValue();
	/**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     * @author Anthony C. Liberto
     */
    Component getTableCellEditorComponent(JTable _tbl, Object _o, boolean _selected, int _row, int _col);
	/**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    boolean isCellEditable(EventObject _eo);
	/**
     * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    void removeCellEditorListener(CellEditorListener _cel);
	/**
     * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    boolean shouldSelectCell(EventObject _eo);
	/**
     * @see javax.swing.CellEditor#stopCellEditing()
     * @author Anthony C. Liberto
     */
    boolean stopCellEditing();

}
