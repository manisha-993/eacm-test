/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: TableComparator.java,v $
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:09  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.2  2005/01/14 20:32:12  tony
 * pivot
 *
 * Revision 1.1.1.1  2004/02/10 16:59:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2003/12/23 21:42:41  tony
 * updated number processing logic.
 *
 * Revision 1.6  2003/07/25 15:41:22  tony
 * updated sort logic so that numbers would
 * be properly sorted.
 *
 * Revision 1.5  2003/06/03 19:51:53  tony
 * 51052
 *
 * Revision 1.4  2003/06/03 17:15:58  tony
 * 51052
 *
 * Revision 1.3  2003/05/21 15:38:11  tony
 * updated table logic to allow for the table and model
 * to always know the specific type of table.  Based on a
 * table constant.
 *
 * Revision 1.2  2003/05/13 22:45:06  tony
 * 50616
 * Switched keys from a string to a pointer to the
 * EANFoundation.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2002/11/07 16:58:38  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.RowSelectableTable;

import java.util.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class TableComparator implements Comparator, EAccessConstants {
    /**
     * m_bDirection
     */
    private boolean m_bDirection = true;
    /**
     * m_iIndex
     */
    private int m_iIndex = 0;
    /**
     * m_table
     */
    private RowSelectableTable m_table = null;
    /**
     * m_iType
     */
    private int m_iType = -1;
    /**
     * bPivot
     */
    private boolean bPivot = false;

    /**
     * tableComparator
     * @param _index
     * @param _asc
     * @author Anthony C. Liberto
     */
    public TableComparator(int _index, boolean _asc) {
        setDirection(_asc);
        setIndex(_index);
        return;
    }

    /*
     51052
     */
    /**
     * tableComparator
     * @param _comp
     * @author Anthony C. Liberto
     */
    public TableComparator(TableComparator _comp) {
        m_iIndex = _comp.m_iIndex;
        m_bDirection = _comp.m_bDirection;
        m_iType = _comp.m_iType;
        return;
    }

    /**
     * setType
     * @param _type
     * @author Anthony C. Liberto
     */
    public void setType(int _type) {
        m_iType = _type;
        return;
    }

    /**
     * isType
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isType(int _i) {
        return m_iType == _i;
    }

    /**
     * setIndex
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setIndex(int _i) {
        m_iIndex = _i;
        return;
    }

    /**
     * setTable
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setTable(RowSelectableTable _table) {
        m_table = _table;
        return;
    }

    /**
     * setDirection
     * @param _asc
     * @author Anthony C. Liberto
     */
    public void setDirection(boolean _asc) {
        m_bDirection = _asc;
        return;
    }

    /**
     * setPivot
     * @param _piv
     * @author Anthony C. Liberto
     */
    public void setPivot(boolean _piv) {
        bPivot = _piv;
        return;
    }

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @author Anthony C. Liberto
     */
    public int compare(Object _o1, Object _o2) {
        String k1 = null;
        String k2 = null;
        Object o1 = null;
        Object o2 = null;

        if (m_table == null) {
            return 0;
        }
        k1 = ((EANFoundation) _o1).getKey();
        k2 = ((EANFoundation) _o2).getKey();

        if (isType(TABLE_MATRIX)) {
            if (m_iIndex < 0) {
                //				if (bPivot) {
                //					o1 = m_table.getColumnHeader(m_table.getColumnIndex(k1));
                //					o2 = m_table.getColumnHeader(m_table.getColumnIndex(k1));
                //				} else {
                o1 = m_table.getRowHeaderMatrix(m_table.getRowIndex(k1));
                o2 = m_table.getRowHeaderMatrix(m_table.getRowIndex(k2));
                //				}
            } else {
                o1 = m_table.getMatrixValue(m_table.getRowIndex(k1), m_iIndex);
                o2 = m_table.getMatrixValue(m_table.getRowIndex(k2), m_iIndex);
            }
        } else {
            if (m_iIndex < 0) {
                //				if (bPivot) {
                //					o1 = m_table.getColumnHeader(m_table.getColumnIndex(k1));
                //					o2 = m_table.getColumnHeader(m_table.getColumnIndex(k1));
                //				} else {
                o1 = m_table.getRowHeader(m_table.getRowIndex(k1));
                o2 = m_table.getRowHeader(m_table.getRowIndex(k2));
                //				}
            } else {
                o1 = m_table.get(m_table.getRowIndex(k1), m_iIndex);
                o2 = m_table.get(m_table.getRowIndex(k2), m_iIndex);
            }
        }
        return compareObject(o1, o2);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @author Anthony C. Liberto
     */
    public boolean equals(Object o3) {
        return m_bDirection;
    }

    private int compareObject(Object _o1, Object _o2) {
        if (_o1 == null && _o2 == null) {
            return 0;
        }
        if (_o1 == null) {
            return -1;
        }
        if (_o2 == null) {
            return 1;
        }
        return compareString(_o1.toString(), _o2.toString());
    }

    private int compareString(String s1, String s2) {
        Integer i1 = null;
        if (s1 == null || s2 == null) {
            return -1;
        }
        i1 = Routines.toInteger(null, s1); //acl_20030725
        if (i1 != null) { //acl_20030725
            Integer i2 = Routines.toInteger(null, s2); //acl_20030725
            if (i2 != null) { //acl_20030725
                return compareInteger(i1, i2); //acl_20030725
            } //acl_20030725
        } //acl_20030725
        return getResult(s1.compareToIgnoreCase(s2));
    }

    private int compareInteger(Integer i1, Integer i2) {
        return getResult(i1.compareTo(i2));
    }

    private int getResult(int result) {
        if (!m_bDirection) {
            return -result;
        }
        return result;
    }
}
