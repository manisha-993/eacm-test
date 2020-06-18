//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eTableRecord.java,v $
// Revision 1.14  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.13  2004/10/15 17:58:55  gregg
// more ticky tack tweaks
//
// Revision 1.12  2004/09/30 00:02:20  dave
// new SP's for performance run testing
//
// Revision 1.11  2004/09/20 17:56:00  gregg
// null ptr fix
//
// Revision 1.10  2004/09/20 17:47:27  gregg
// trimUnicodeString
//
// Revision 1.9  2004/09/20 17:33:16  gregg
// getCharacterEncoding() to trim column vals to actual length
//
// Revision 1.8  2004/09/08 16:21:58  gregg
// NO_INT_VAL
//
// Revision 1.7  2004/08/31 22:23:13  gregg
// null ptr fix
//
// Revision 1.6  2004/08/31 22:16:31  gregg
// add table name in eTableRecord constructor
//
// Revision 1.5  2004/08/31 19:52:12  gregg
// blob store/get methods
//
// Revision 1.4  2004/08/31 19:12:55  gregg
// putBlobVal method stub
//
// Revision 1.3  2004/08/24 22:35:49  gregg
// implements EANComparable in eTableRecord for nifty column sorting
//
// Revision 1.2  2004/08/23 23:39:29  gregg
// getVersion() method
//
// Revision 1.1  2004/08/23 16:42:20  gregg
// load to middleware module
//
//

package COM.ibm.eannounce.hula;

import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.io.*;
import COM.ibm.opicmpdh.objects.*;

/**
 * Models one record in an ODS table.
 */
public abstract class eTableRecord extends EANMetaEntity implements EANComparable {

    static final long serialVersionUID = 20011106L;

    public static final int STRING = eTable.STRING;
    public static final int INT    = eTable.INT;
    public static final int BLOB   = eTable.BLOB;

    protected static final String m_strODSSchema = eProductProperties.getDatabaseSchema();

    public static final int NO_INT_VAL = -3;

    private Hashtable m_hashColVals = null;
    private String m_strCompareField = null;
    private String m_strTableName = null;

    public eTableRecord(EANMetaFoundation _emf, Profile _prof, String _strKey, String _strTableName) throws MiddlewareRequestException {
        super(_emf,_prof,_strKey);
        m_strTableName = _strTableName;
        m_hashColVals = new Hashtable(getColumnCount());
    }

    public final void putStringVal(String _strColKey, String _strVal) {
        _strVal = trimUnicodeString(_strVal,_strColKey,getColumnLen(_strColKey));
        putVal(_strColKey,_strVal);
    }

    public final void putIntVal(String _strColKey, int _iVal) {
        putVal(_strColKey,new Integer(_iVal));
    }

    public final void putBlobVal(String _strColKey, Blob _blob) {
        putVal(_strColKey,_blob);
    }

    public final String getStringVal(String _strKey) {
        Object o = getVal(_strKey);
        if(o == null) {
            return null;
        }
        return o.toString();
    }

    public final String getStringVal(int _i) {
        String strColKey = getColumnKey(_i);
        Object o = getVal(strColKey);
        if(o == null) {
            return null;
        }
        return o.toString();
    }

    public final int getIntVal(String _strKey) {
        Integer objInt = (Integer)getVal(_strKey);
        if(objInt == null) {
            return NO_INT_VAL;
        }
        return objInt.intValue();
    }

    public final int getIntVal(int _i) {
        String strColKey = getColumnKey(_i);
        Integer objInt = (Integer)getVal(strColKey);
        if(objInt == null) {
            return NO_INT_VAL;
        }
        return objInt.intValue();
    }

    public final Blob getBlobVal(String _strKey) {
        Blob blob = (Blob)getVal(_strKey);
        if(blob == null) {
            return null;
        }
        return blob;
    }

    public final Blob getBlobVal(int _i) {
        String strColKey = getColumnKey(_i);
        Blob blob = (Blob)getVal(strColKey);
        if(blob == null) {
            return null;
        }
        return blob;
    }

    public final int getColumnNum(String _strKey) {
        return getTable().getColumnNum(_strKey);
    }

    public final int getColumnType(String _strKey) {
        return getTable().getColumnType(_strKey);
    }

    public final int getColumnType(int _i) {
        return getTable().getColumnType(_i);
    }

    public final int getColumnCount() {
        return getTable().getColumnCount();
    }

    public final String getColumnKey(int _i) {
        return getTable().getColumnKey(_i);
    }

    /*private final int getColumnLen(int _i) {
        return getTable().getColumnLen(_i);
    }*/

    private final int getColumnLen(String _strKey) {
        return getTable().getColumnLen(_strKey);
    }

    private void putVal(String _strKey, Object _obj) {
        if(_obj == null) {
            return;
        }
        m_hashColVals.put(_strKey,_obj);
    }

    private Object getVal(String _strKey) {
        if(_strKey == null) {
            return null;
        }
        return m_hashColVals.get(_strKey);
    }

    private Object getVal(int _i) {
        String strKey = getColumnKey(_i);
        if(strKey == null) {
            return null;
        }
        return m_hashColVals.get(strKey);
    }

    private final eTable getTable() {
        return eTable.getETable(getTableName());
    }

    public final boolean wasColumnValSet(String _strKey) {
        //return getTable().wasColumnValSet(_strKey);
        return (getVal(_strKey) != null);
    }

    public final boolean wasColumnValSet(int _i) {
        //return getTable().wasColumnValSet(_i);
        return (getVal(_i) != null);
    }

    public abstract void update(Database _db) throws Exception;

    public String getTableName() {
        return m_strTableName;
    }

/*
 * Version info
 */
    public String getVersion() {
        return new String("$Id: eTableRecord.java,v 1.14 2008/01/31 21:05:17 wendy Exp $");
    }

    //
    // EANComparable
    //
//enforce a separate key for compare - this way objects can have different sort vals!
    public String toCompareString() {
        String s = getStringVal(getCompareField());
        if(s != null) {
            return s;
        }
        return toString();
    }

//set the String representing the field to compare by
    public void setCompareField(String _strColKey) {
        m_strCompareField = _strColKey;
    }

//get the String representing the field to compare by
    public String getCompareField() {
        return (m_strCompareField!=null?m_strCompareField:"");
    }

  private String trimUnicodeString(String _strVal, String _strColName, int _iColLen) {
      if(_strVal == null) {
          return _strVal;
      }
      try {
          int iLength = _strVal.getBytes(eProductProperties.getCharacterEncoding()).length;
          String strAnswer = new String(_strVal);
          if (iLength > _iColLen) {
              D.ebug(D.EBUG_WARN, "**trimUnicodeString: Length Exceeds max length for " + _strColName + ":" + _strVal);
              while (iLength > _iColLen) {
                  strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
                  iLength = strAnswer.getBytes(eProductProperties.getCharacterEncoding()).length;
              }
          }
          _strVal = strAnswer;
      } catch (UnsupportedEncodingException ex) {
          D.ebug(D.EBUG_ERR, "**trimUnicodeString: UnsupportedEncodingException.  Passing entire string back." + ex.getMessage());
      }
      return _strVal;
  }

}
