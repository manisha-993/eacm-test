/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: BooleanTable.java,v $
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:11  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:03  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:06  tony
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
 * Revision 1.5  2003/12/01 17:43:27  tony
 * accessibility
 *
 * Revision 1.4  2003/10/16 15:13:44  tony
 * 52560
 *
 * Revision 1.3  2003/08/27 19:08:48  tony
 * updated generic search to improve functionality.
 *
 * Revision 1.2  2003/08/22 16:39:18  tony
 * general search
 *
 * Revision 1.1.1.1  2003/03/03 18:03:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:33  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import com.ibm.eannounce.erend.BooRend;
import COM.ibm.eannounce.objects.RowSelectableTable;
import java.awt.event.*;
import java.util.EventObject;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class BooleanTable extends NavTable {
	private static final long serialVersionUID = 1L;
	/**
     * booleanTable
     * @author Anthony C. Liberto
     */
    public BooleanTable() {
		super(TABLE_BOOLEAN);
		setDefaultRenderer(Boolean.class,new BooRend());
		return;
	}

	/**
     * updateModel
     *
     * @author Anthony C. Liberto
     * @param _rst
     */
    public void updateModel(RowSelectableTable _rst) {
		int colCount = -1;
        clearSelection();
		cgtm.updateModel(_rst);
		createDefaultColumnsFromModel();
		resizeAndRepaint();
		resizeCells();
		if (getRowCount() > 0) {
			setRowSelectionInterval(0,0);
		}
		colCount = getColumnCount();
		if (colCount >= 2) {
			setColumnSelectionInterval(1,1);
		} else if (colCount > 0) {
			setColumnSelectionInterval(0,0);
		}
		return;
	}

	/**
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean editCellAt(int _r, int _c, EventObject _e) {
		if (_e instanceof MouseEvent) {
			if (_c == 0) {
				toggleValue(_r,_c);
			} else if (((MouseEvent)_e).getClickCount() == 2) {
				toggleValue(_r,0);
			}
		} else if (_e instanceof KeyEvent) {
			int code = ((KeyEvent)_e).getKeyCode();
			if (code == KeyEvent.VK_SPACE) {
				toggleValue(_r,0);
			}
		}
		return false;
	}

	private void toggleValue(int _r, int _c) {
		Object o = getValueAt(_r,_c);
		if (o instanceof Boolean) {
//52560			setValueAt(new Boolean(!((Boolean)o).booleanValue()),_r,_c);
			putValueAt(new Boolean(!((Boolean)o).booleanValue()),_r,_c);		//52560
			valueChanged();
			repaint();
		}
		return;
	}

	/**
     * hasSelection
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasSelection() {
		int rr = getRowCount();
		for (int r=0;r<rr;++r) {
			Object o = getValueAt(r,0);
			if (o instanceof Boolean) {
				if (((Boolean)o).booleanValue()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
     * valueChanged
     * @author Anthony C. Liberto
     */
    public void valueChanged() {}
 /*
  accessibility
  */
	/**
     * @see com.ibm.eannounce.eForms.table.GTable#getContext()
     * @author Anthony C. Liberto
     */
    protected String getContext() {
		return ".bool";
	}

	/**
     * @see com.ibm.eannounce.eForms.table.GTable#getAccessibleAttributeType(int, int)
     * @author Anthony C. Liberto
     */
    protected String getAccessibleAttributeType(int _row, int _col) {
		if (_col == 1) {
			Object o = getValueAt(_row,0);
			if (o != null && o instanceof Boolean) {
				if (((Boolean)o).booleanValue()) {
					return getString("accessible.booleantable.selected");
				} else {
					return getString("accessible.booleantable.unselected");
				}
			}
		}
		return "";
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
		return getValueAt(_row,1);
	}

    /**
     * getAccessibleColumnNameAt
     *
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleColumnNameAt(int _col) {
		return "";
	}

    /**
     * getAccessibleRowNameAt
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleRowNameAt(int _row) {
		return "";
	}

	/**
     * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
     * @author Anthony C. Liberto
     */
    public void changeSelection(int _row, int _col, boolean _toggle, boolean _extend) {
		if (getColumnCount() >= 2) {
			super.changeSelection(_row,1,_toggle,_extend);
		} else {
			super.changeSelection(_row,_col,_toggle,_extend);
		}
		return;
	}

}
