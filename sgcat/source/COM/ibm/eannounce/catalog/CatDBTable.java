//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CatDBTable.java,v $
// Revision 1.3  2011/05/05 11:21:34  wendy
// src from IBMCHINA
//
// Revision 1.1.1.1  2007/06/05 02:09:06  jingb
// no message
//
// Revision 1.1.1.1  2006/03/30 17:36:27  gregg
// Moving catalog module from middleware to
// its own module.
//
// Revision 1.4  2005/10/28 17:03:04  gregg
// columns object allowNull property to return null or ""
//
// Revision 1.3  2005/10/27 23:57:37  gregg
// default in props + some fixes
//
// Revision 1.2  2005/09/21 20:47:39  gregg
// oops. correct package name.
//
// Revision 1.1  2005/09/21 19:35:54  gregg
// moving over from hula package
//
// Revision 1.5  2004/10/15 20:44:49  gregg
// some behind the scenes Array use for faster access on indexed gets
//
// Revision 1.4  2004/08/31 19:11:24  gregg
// BLOB type
//
// Revision 1.3  2004/08/23 23:48:38  gregg
// minor rearranging
//
// Revision 1.2  2004/08/23 23:39:29  gregg
// getVersion() method
//
// Revision 1.1  2004/08/23 16:42:20  gregg
// load to middleware module
//
//

package COM.ibm.eannounce.catalog;

import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.sql.Connection;

/**
 * Models a Table in the ODS.
 */
public class CatDBTable {

    //static final long serialVersionUID = 20011106L;

    private static Hashtable c_hashTables = new Hashtable();
    private Hashtable m_hashColumns = null;
    private String[] m_asColumnKeys = null;
    private int m_iColNum = 0;
    public static final int STRING = 0;
    public static final int INT    = 1;
    public static final int BLOB   = 2;

    private CatDBTable(String _strTableName) {
        m_hashColumns = new Hashtable();
        m_asColumnKeys = null;
        c_hashTables.put(_strTableName,this);
    }

    public static CatDBTable getCatDBTable(String _strTableName) {
        CatDBTable table = (CatDBTable)c_hashTables.get(_strTableName);
        if(table == null) {
            synchronized(CatDBTable.class) {
                table = new CatDBTable(_strTableName);
            }
        }
        return table;
    }

    public final int getColumnNum(String _strKey) {
        Column col = getColumn(_strKey);
        if(col == null) {
            return -1;
        }
        return col.getColNum();
    }

    public final int getColumnType(String _strKey) {
        Column col = getColumn(_strKey);
        if(col == null) {
            return -4;
        }
        return col.getType();
    }

    public final int getColumnType(int _i) {
        Column col = getColumn(_i);
        if(col == null) {
            return -4;
        }
        return col.getType();
    }

    public final void makeStringColumn(String _strKey, int _iColLen, boolean _bAllowNulls) {
        Column c = new Column(_strKey,m_iColNum++,STRING, _bAllowNulls);
        c.setColLen(_iColLen);
        putColumn(c);
    }

    public final void makeIntColumn(String _strKey, boolean _bAllowNulls) {
        putColumn(new Column(_strKey,m_iColNum++,INT, _bAllowNulls));
    }

    public final void makeBlobColumn(String _strKey, boolean _bAllowNulls) {
        putColumn(new Column(_strKey,m_iColNum++,BLOB, _bAllowNulls));
    }

    public final int getColumnCount() {
        return getColumns().size();
    }

    public final String getColumnKey(int _i) {
        if(_i < 0 || _i >= m_asColumnKeys.length) {
            return null;
        }
        return m_asColumnKeys[_i];
    }

    private final Column getColumn(String _strKey) {
        return (Column)m_hashColumns.get(_strKey);
    }

    private final Column getColumn(int _i) {
        String strKey = getColumnKey(_i);
        if(strKey == null) {
            return null;
        }
        return (Column)m_hashColumns.get(strKey);
    }

    private final void putColumn(Column _col) {
        getColumns().put(_col.getKey(),_col);
        m_asColumnKeys = addArrayElement(m_asColumnKeys,_col.getKey());
    }

    public final int getColumnLen(int _i) {
        return getColumn(_i).getColLen();
    }

    public final int getColumnLen(String _strKey) {
        return getColumn(_strKey).getColLen();
    }

    public final boolean allowColumnNulls(int _i) {
        return getColumn(_i).allowNulls();
    }

    public final boolean allowColumnNulls(String _strKey) {
        return getColumn(_strKey).allowNulls();
    }

    private final Hashtable getColumns() {
        return m_hashColumns;
    }

    public final boolean wasColumnValSet(String _strKey) {
        Column col = getColumn(_strKey);
        if(col == null) {
            return false;
        }
        return col.wasValSet();
    }

    public final boolean wasColumnValSet(int _i) {
        Column col = getColumn(_i);
        if(col == null) {
            return false;
        }
        return col.wasValSet();
    }

    public final class Column {
        private String m_strKey = null;
        private Object m_objVal = null;
        private int m_iColNum = -1;
        private int m_iType = -1;
        private int m_iColLen = -1;
        private boolean m_bValSet = false;
        private boolean m_bAllowNulls = false;
        private Column(String _strKey, int _iColNum, int _iType, boolean _bAllowNulls) {
            m_strKey = _strKey;
            m_iColNum = _iColNum;
            m_iType = _iType;
            m_bAllowNulls = _bAllowNulls;
        }
        public final String getKey(){return m_strKey;}
        public final int getColNum(){return m_iColNum;}
        public final int getType(){return m_iType;}
        private final void setColLen(int _i) {m_iColLen = _i;}
        private final int getColLen() {return m_iColLen;}
        public final boolean wasValSet() {return m_bValSet;}
        public final boolean allowNulls() {return m_bAllowNulls;}
    }

/**
 * bump the array length by one and return the new array, w/ last index having passed String
 * ...lets sacrifice cost of put for cheapness of get.
 * !!! its nota reference thing we must set new Array !!!
 */
    private String[] addArrayElement(String[] _sa, String _s) {
        if(_sa == null) {
           _sa = new String[0];
        }
        String[] temp = new String[_sa.length+1];
        for(int i = 0; i < _sa.length; i++) {
            temp[i] = _sa[i];
        }
        temp[temp.length-1] = _s;
        _sa = temp;
        temp = null;
        return _sa;
    }

/*
 * Version info
 */
    public String getVersion() {
        return new String("$Id: CatDBTable.java,v 1.3 2011/05/05 11:21:34 wendy Exp $");
    }

}
