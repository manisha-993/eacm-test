/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: OrderTableModel.java,v $
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:06  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.2  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2003/05/30 21:09:24  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.11  2003/04/11 20:02:31  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import java.awt.Component;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class OrderTableModel extends DefaultTableModel implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private MetaColumnOrderTable ctm = null;
	private boolean bOrder = true;

	/**
     * orderTableModel
     * @author Anthony C. Liberto
     */
    public OrderTableModel() {
		super();
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		ctm = null;
		return;
	}

	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public EAccess eaccess() {
		return EAccess.eaccess();
	}

	/**
     * setOrderable
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setOrderable(boolean _b) {
		bOrder = _b;
		return;
	}

	/**
     * isOrderable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isOrderable() {
		return bOrder;
	}

	/**
     * updateModel
     * @param _ctm
     * @param _type
     * @author Anthony C. Liberto
     */
    public void updateModel(MetaColumnOrderTable _ctm, int _type) {
		ctm = _ctm;
		modelChanged(_type);
		return;
	}

	/**
     * setUpdateDefaults
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUpdateDefaults(boolean _b) {
		eaccess().dBase().setUpdateDefaults(ctm,_b);
		return;
	}

	/**
     * @see javax.swing.table.TableModel#getRowCount()
     * @author Anthony C. Liberto
     */
    public int getRowCount() {
		if (ctm != null) {
			return ctm.getRowCount();
		}
		return 0;
	}

	/**
     * @see javax.swing.table.TableModel#getColumnCount()
     * @author Anthony C. Liberto
     */
    public int getColumnCount() {
		if (ctm != null) {
			return ctm.getColumnCount();
		}
		return 0;
	}

	/**
     * @see javax.swing.table.TableModel#getColumnName(int)
     * @author Anthony C. Liberto
     */
    public String getColumnName(int _i) {
		if (ctm != null) {
			return ctm.getColumnHeader(_i);
		}
		return null;
	}

	/**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     * @author Anthony C. Liberto
     */
    public Class getColumnClass(int _i) {
		if (ctm != null) {
			return ctm.getColumnClass(_i);
		}
		return String.class;
	}

	/**
     * moveRow
     * @param _old
     * @param _new
     * @author Anthony C. Liberto
     */
    public void moveRow(int _old, int _new) {
		if (ctm != null) {
			ctm.moveRow(_old,_new);
		}
		return;
	}

	/**
     * moveRows
     * @param _old
     * @param _new
     * @author Anthony C. Liberto
     */
    public void moveRows(int[] _old, int _new) {}

	/**
     * isMoveableColumn
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMoveableColumn(int _c) {
		return true;
	}

	/**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(int _r,int _c) {
		return ctm.isCellEditable(_r,_c);
	}

	/**
     * commit
     * @param _c
     * @author Anthony C. Liberto
     */
    public void commit(Component _c) {
		eaccess().dBase().commit(ctm, _c);
		return;
	}

	/**
     * rollbackDefault
     * @author Anthony C. Liberto
     */
    public void rollbackDefault() {
		if (eaccess().dBase().resetToDefaults(ctm)) {
			ctm.refresh();
			modelUpdate();
		}
		return;
	}

	/**
     * rollback
     * @author Anthony C. Liberto
     */
    public void rollback() {
		if (eaccess().dBase().rollback(ctm)) {
			modelUpdate();
		}
		return;
	}

	/**
     * hasChanges
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChanges() {
		return ctm.hasChanges();
	}

	/**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c) {
		if (ctm != null) {
			return ctm.getValueAt(_r,_c);
		}
		return null;
	}

	/**
     * get
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Object get(int _r, int _c) {
		if (ctm != null) {
			return ctm.get(_r,_c);
		}
		return null;
	}

	/**
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     * @author Anthony C. Liberto
     */
    public void setValueAt(Object _o, int _r, int _c) {
		try {
			ctm.put(_r,_c,_o);
		} catch (EANBusinessRuleException _bre) {
			_bre.printStackTrace();
		}
		return;
	}

	/**
     * modelUpdate
     * @author Anthony C. Liberto
     */
    public void modelUpdate() {
		modelChanged(TableModelEvent.UPDATE);
		return;
	}

    /**
     * modelChanged
     * @param _type
     * @author Anthony C. Liberto
     */
    public void modelChanged(int _type) {
		tableModelChanged(0,getRowCount()-1,TableModelEvent.ALL_COLUMNS, _type);
		return;
	}

    /**
     * tableModelChanged
     * @param startRow
     * @param endRow
     * @param col
     * @param type
     * @author Anthony C. Liberto
     */
    public void tableModelChanged(int startRow, int endRow, int col, int type) {
		TableModelEvent event = null;
        if (startRow < 0) {
			startRow = 0;
		}
		if (endRow < 0) {
			endRow = Math.max(0,getRowCount()-1);
		}
		event = new TableModelEvent(this, startRow, endRow, col, type);
		fireTableChanged(event);
		return;
    }

    /**
     * dump
     * @param _sb
     * @param _brief
     * @return
     * @author Anthony C. Liberto
     */
    public StringBuffer dump(StringBuffer _sb, boolean _brief) {
		_sb.append("TableModel.getRowCount(): " + getRowCount() + RETURN);
		_sb.append("TableModel.getColumnCount(): " + getColumnCount() + RETURN);
		if (!_brief) {
			int rr = getRowCount();
			int cc = getColumnCount();
			int lastCol = (cc - 1);
			for (int r=0;r<rr;++r) {
				_sb.append("Row(" + r + "): ");
				for (int c=0;c<cc;++c) {
					Object o = getValueAt(r,c);
					if (o != null) {
						_sb.append(o.toString());
					}
					if (c != lastCol) {
						_sb.append(", ");
					}
				}
				_sb.append(RETURN);
			}
		}
		return _sb;
	}
}
