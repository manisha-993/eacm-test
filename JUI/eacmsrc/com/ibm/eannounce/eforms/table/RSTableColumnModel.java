/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: RSTableColumnModel.java,v $
 * Revision 1.3  2009/04/09 21:13:04  wendy
 * Sort needs more than column name to find the attribute
 *
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:06  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/04/05 17:29:50  tony
 * MN_23318121
 *
 * Revision 1.3  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/12/10 22:04:18  tony
 * cleaned-up the code.
 *
 * Revision 1.2  2003/09/12 17:49:32  tony
 * 52196
 *
 * Revision 1.1.1.1  2003/03/03 18:03:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/12/02 18:40:11  tony
 * 23420
 *
 * Revision 1.3  2002/11/07 16:58:34  tony
 * added/adjusted copyright statement
 *
 */
/*
 * added as a result of 21694
 */
package com.ibm.eannounce.eforms.table;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;					//23420
import java.util.*;
import javax.swing.event.TableColumnModelEvent;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class RSTableColumnModel extends DefaultTableColumnModel {
	private static final long serialVersionUID = 1L;
	private Vector vHide = new Vector();
	private Comparator comp = null;

	/**
     * rsTableColumnModel
     * @author Anthony C. Liberto
     */
    public RSTableColumnModel() {
		super();
		comp = createComparator();
	}

    /**
     * hideColumn
     * @param _key
     * @author Anthony C. Liberto
     */
    public void hideColumn(String _key) {
		int ii = getColumnCount();
		for (int i=0;i<ii;++i) {
			RSTableColumn col = (RSTableColumn)getColumn(i);
			if (_key.equals(col.key())) {
				hideColumn(col,i);
				return;
			}
		}
    }

	/**
     * hideColumn
     * @param _col
     * @param _indx
     * @author Anthony C. Liberto
     */
    public void hideColumn(RSTableColumn _col, int _indx) {
		removeColumn(_col);
		_col.setViewIndex(_indx);
		vHide.add(_col);
	}

	/**
     * showColumn
     * @param _key
     * @author Anthony C. Liberto
     */
    public void showColumn(String _key) {
		int ii = vHide.size();
		for (int i=0;i<ii;++i) {
			RSTableColumn col = (RSTableColumn)vHide.get(i);
			if (_key.equals(col.key())) {
				showColumn(col,i);
				return;
			}
		}
	}

	/**
     * showColumn
     * @param _col
     * @param _i
     * @author Anthony C. Liberto
     */
    public void showColumn(RSTableColumn _col, int _i) {
		vHide.remove(_col);
		addColumn(_col,_i);
	}

    /**
     * addColumn
     * @param _col
     * @param _i
     * @author Anthony C. Liberto
     */
    public void addColumn(RSTableColumn _col, int _i) {
        if (_i < 0) {
        	addColumn(_col);
        	return;
		}
        if (_col == null) {
            throw new IllegalArgumentException("Object is null");
		}
        tableColumns.add(_i,_col);
        _col.addPropertyChangeListener(this);
        totalColumnWidth = -1;
        fireColumnAdded(new TableColumnModelEvent(this, 0, getColumnCount() - 1));
    }

	/**
     * setHidden
     * @param _key
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setHidden(String _key, boolean _b) {
		if (_b) {
			hideColumn(_key);
		} else {
			showColumn(_key);
		}
	}

	/**
     * resetHidden
     * @author Anthony C. Liberto
     */
    public void resetHidden() {
		int ii = vHide.size();
		for (int i=0;i<ii;++i) {
			showColumn((RSTableColumn)vHide.get(i),i);
		}
	}

	/**
     * isHidden
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isHidden(String _key) {
		int ii = vHide.size();
		for (int i=0;i<ii;++i) {
			RSTableColumn col = (RSTableColumn)vHide.get(i);
			if (_key.equals(col.key())) {
				return true;
			}
		}
		return false;
	}

	/**
     * getHidden
     * @return
     * @author Anthony C. Liberto
     */
    public RSTableColumn[] getHidden() {
		int ii = -1;
        RSTableColumn[] out = null;
        if (vHide.isEmpty()) {
			return null;
		}
		ii = vHide.size();
		out = new RSTableColumn[ii];
		for (int i=0;i<ii;++i) {
			out[i] = (RSTableColumn)vHide.get(i);
		}
		return out;
	}


	/**
     * @see javax.swing.table.TableColumnModel#getColumn(int)
     * @author Anthony C. Liberto
     */
    public TableColumn getColumn(int _col) {		//23420
		if (_col < getColumnCount() && _col >= 0) {	//23420
			return super.getColumn(_col);			//23420
		}
		return null;								//23420
	}												//23420

/*
 52196
 */
    /**
     * sort
     * @author Anthony C. Liberto
     */
    public void sort() {}

	/**
     * sort
     * @param _o
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] sort(Object[] _o) {
		if (comp != null) {
			Arrays.sort(_o,comp);
		}
		return _o;
	}

	/**
     * createComparator
     * @return
     * @author Anthony C. Liberto
     */
    public Comparator createComparator() {
		return null;
	}

	/**
     * moveColumn
     * @param _tc
     * @param _loc
     * @author Anthony C. Liberto
     */
    public void moveColumn(TableColumn _tc, int _loc) {
		moveColumn(getColumnIndex(_tc.getIdentifier()),_loc);
	}

    /**
     * getIndexFromHeader
     *
     * @param _s
     * @return
     * @author Anthony C. Liberto
     * /
    public int getIndexFromHeader(String _s) {
		for (int i=0;i<getColumnCount();++i) {
			TableColumn tc = getColumn(i);
			if (tc != null) {
				if (tc.toString().equals(_s)) {
					return i;
				}
			}
		}
		return 0;
	}*/
}
