/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: HeaderTable.java,v $
 * Revision 1.2  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:04  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:45  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2002/11/07 16:58:33  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import com.ibm.eannounce.erend.ERowRend;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class HeaderTable extends JTable {
	private static final long serialVersionUID = 1L;
	private ELabel lbl = new ELabel();
	private ERowRend rend = new ERowRend();
	private GTable table = null;
	private Object header = null;
	private Dimension size = new Dimension();
	private int width = 0;
	private Rectangle rect = new Rectangle();

	/**
     * headerTable
     * @author Anthony C. Liberto
     */
    public HeaderTable() {
		super();
	}

	/**
     * setTable
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setTable(GTable _table) {
		table = _table;
	}

	/**
     * @see javax.swing.JTable#getColumnCount()
     * @author Anthony C. Liberto
     */
    public int getColumnCount() {
		return 1;
	}

	/**
     * @see javax.swing.JTable#rowAtPoint(java.awt.Point)
     * @author Anthony C. Liberto
     */
    public int rowAtPoint(Point _pt) {
		return table.rowAtPoint(_pt);
	}

	/**
     * @see javax.swing.JTable#columnAtPoint(java.awt.Point)
     * @author Anthony C. Liberto
     */
    public int columnAtPoint(Point _pt) {
		TableColumnModel tcm = null;
        int index = -1;
        if (table == null || header == null) {
            return -1;
		}
		tcm = table.getColumnModel();
		index = tcm.getColumnIndex(header);
		return index;
	}

	/**
     * resizeAndPaint
     * @author Anthony C. Liberto
     */
    public void resizeAndPaint() {
        Component c = getParent();
        sizeTable();
		if (c instanceof JViewport) {
			JViewport jv = (JViewport)c;
			jv.setViewSize(size);
			jv.setSize(size);
			jv.setPreferredSize(size);
			jv.revalidate();
		}
		resizeAndRepaint();
		return;
	}

	/**
     * @see javax.swing.JTable#getRowCount()
     * @author Anthony C. Liberto
     */
    public int getRowCount() {
		if (table == null) {
            return 0;
		}
		return table.getRowCount();
	}

	/**
     * @see javax.swing.JTable#getColumnModel()
     * @author Anthony C. Liberto
     */
    public TableColumnModel getColumnModel() {
		if (table == null) {
            return super.getColumnModel();
		}
		return table.getColumnModel();
	}

	/**
     * setHeader
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setHeader(Object _o) {
		header = _o;
		if (_o != null) {
			TableColumnModel tcm = table.getColumnModel();
			int index = tcm.getColumnIndex(_o);
			lbl.setText(table.getColumnName(index));
			width = tcm.getColumn(index).getWidth();
		} else {
			lbl.setText("");
			width = 0;
		}
		sizeTable();
		return;
	}

	private void sizeTable() {
		getTableSize();
		setSize(size);
		setPreferredSize(size);
		return;
	}

	/**
     * getTableSize
     * @return
     * @author Anthony C. Liberto
     */
    public Dimension getTableSize() {
		int h = table.getPreferredSize().height;
		size.setSize(width,h);
		return size;
	}

	private int getHeaderColumn() {
		TableColumnModel tcm = null;
        if (table == null) {
            return -1;
		}
		tcm = table.getColumnModel();
		return tcm.getColumnIndex(header);
	}

	/**
     * @see javax.swing.JTable#getCellRect(int, int, boolean)
     * @author Anthony C. Liberto
     */
    public Rectangle getCellRect(int _row, int _col, boolean _space) {
//		int columnMargin = table.getColumnModel().getColumnMargin();
		Dimension spacing = null;
        int rowSpacing = -1;
        int y = 0;
        rect.height = getRowHeight(_row) + table.getRowMargin();
		spacing = table.getIntercellSpacing();
		rowSpacing = spacing.height;
		for (int i=0;i<_row;++i) {
			y += getRowHeight(i) + rowSpacing;
		}
		rect.y = y;
		rect.x = 0;
		rect.width = width;
		if (!_space) {
			rect.setBounds(rect.x + spacing.width/2, rect.y + spacing.height/2, rect.width - spacing.width, rect.height - spacing.height);
		}
		return rect;
	}

	/**
     * @see javax.swing.JTable#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c) {
//		if (header == null) return "";
		return table.getHeaderValueAt(_r,getHeaderColumn());
//		return table.getValueAt(_r, getHeaderColumn());
	}

	/**
     * @see javax.swing.JTable#getCellRenderer(int, int)
     * @author Anthony C. Liberto
     */
    public TableCellRenderer getCellRenderer(int _r, int _c) {
		return rend;
	}

	/**
     * @see javax.swing.JTable#getRowHeight(int)
     * @author Anthony C. Liberto
     */
    public int getRowHeight(int _row) {
		return table.getRowHeight(_row);
	}

	/**
     * getHeaderLabel
     * @return
     * @author Anthony C. Liberto
     */
    public ELabel getHeaderLabel() {
		return lbl;
	}

	/**
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean editCellAt(int _r, int _c, EventObject _e) {
		return false;
	}
	/**
     * @see javax.swing.JTable#setValueAt(java.lang.Object, int, int)
     * @author Anthony C. Liberto
     */
    public void setValueAt(Object _o, int _r, int _c) {
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {				//acl_Mem_20020131
		if (lbl != null) {				//acl_Mem_20020131
			lbl.removeAll();			//acl_Mem_20020131
			lbl = null;					//acl_Mem_20020131
		}								//acl_Mem_20020131
		rend = null;					//acl_Mem_20020131
		table = null;					//acl_Mem_20020131
		header = null;					//acl_Mem_20020131
		size = null;					//acl_Mem_20020131
		width = 0;						//acl_Mem_20020131
		rect = null;					//acl_Mem_20020131
		removeAll();					//acl_Mem_20020131
		return;							//acl_Mem_20020131
	}
}
