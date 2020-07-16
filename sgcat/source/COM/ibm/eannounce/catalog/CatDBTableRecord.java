//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CatDBTableRecord.java,v $
// Revision 1.7  2011/05/05 11:21:31  wendy
// src from IBMCHINA
//
// Revision 1.1.1.1  2007/06/05 02:09:08  jingb
// no message
//
// Revision 1.5  2006/06/07 22:21:33  gregg
// fixing intersectFlagVals logic
//
// Revision 1.4  2006/05/15 17:51:54  gregg
// null ptr fix
//
// Revision 1.3  2006/05/15 17:45:10  gregg
// :[ENGLISH_ONLY] directive
//
// Revision 1.2  2006/04/10 17:26:00  gregg
// "fix" intersectFlagVals
//
// Revision 1.1.1.1  2006/03/30 17:36:27  gregg
// Moving catalog module from middleware to
// its own module.
//
// Revision 1.58  2006/03/23 21:13:55  gregg
// intersectFlagVals algorithm
//
// Revision 1.57  2006/02/09 18:50:34  gregg
// "|" delimiter for multi-flags
//
// Revision 1.56  2006/01/31 19:05:22  dave
// 9200 cleanup
//
// Revision 1.55  2006/01/23 18:29:57  gregg
// debugs
//
// Revision 1.54  2006/01/23 16:12:12  dave
// Better no PDH Value message for LANGUAGES
//
// Revision 1.53  2006/01/23 16:06:27  dave
// abit of formatting
//
// Revision 1.52  2006/01/19 21:12:46  gregg
// some debugs for LanguageMapper
//
// Revision 1.51  2006/01/17 18:51:53  gregg
// getMappings() fallback
//
// Revision 1.50  2006/01/17 18:40:43  gregg
// language mapper parsing fix
//
// Revision 1.49  2006/01/16 21:54:55  gregg
// introducing language mapper
//
// Revision 1.48  2006/01/13 23:32:20  gregg
// OSLEVEL processing per 6/12 spec
//
// Revision 1.47  2006/01/04 19:00:39  gregg
// debug for type "X" text added
//
// Revision 1.46  2005/12/22 18:40:20  gregg
// add in "X" attributetype in getAttributeValue
//
// Revision 1.45  2005/12/22 18:06:00  gregg
// removing debugs
//
// Revision 1.44  2005/12/21 19:24:35  gregg
// trimming any *'s off flag vals in getAttribute
//
// Revision 1.43  2005/11/30 19:42:31  gregg
// some debugs
//
// Revision 1.42  2005/11/28 20:28:59  gregg
// no blank flag vals
//
// Revision 1.41  2005/11/28 15:58:09  bala
// add debug statements
//
// Revision 1.40  2005/11/22 21:59:44  gregg
// static-ized getAttributeValue method. Let's hope not to break anything.
//
// Revision 1.39  2005/11/02 22:42:07  gregg
// fix
//
// Revision 1.38  2005/10/31 18:43:12  gregg
// replcaing [NO VAlue] in some fields
//
// Revision 1.37  2005/10/28 17:45:00  gregg
// "[No Value]" for nulls on concats
//
// Revision 1.36  2005/10/28 17:24:55  gregg
// *** empty log message ***
//
// Revision 1.35  2005/10/28 17:09:44  gregg
// fix
//
// Revision 1.34  2005/10/28 17:03:04  gregg
// columns object allowNull property to return null or ""
//
// Revision 1.33  2005/10/27 23:57:37  gregg
// default in props + some fixes
//
// Revision 1.32  2005/10/27 21:14:29  gregg
// *** empty log message ***
//
// Revision 1.31  2005/10/27 20:17:08  gregg
// default values in props
//
// Revision 1.30  2005/10/26 00:57:14  dave
// <No Comment Entered>
//
// Revision 1.29  2005/10/25 18:01:18  gregg
// adjusting debug lvl
//
// Revision 1.28  2005/10/25 17:41:52  dave
// more trace
//
// Revision 1.27  2005/10/24 23:39:08  gregg
// some debugs
//
// Revision 1.26  2005/10/14 17:31:28  gregg
// getValueCombo stuff
//
// Revision 1.25  2005/10/13 00:12:43  gregg
// more getValueCombo
//
// Revision 1.24  2005/10/13 00:02:26  gregg
// getValue combinatoric madness
//
// Revision 1.23  2005/10/12 21:35:27  gregg
// fix
//
// Revision 1.22  2005/10/12 21:29:18  gregg
// more class name change
//
// Revision 1.21  2005/10/12 21:17:06  gregg
// CatNavGroup/Item --> MultiRowAttrGroup/Item
//
// Revision 1.20  2005/10/12 21:00:42  gregg
// committing changes
//
// Revision 1.19  2005/10/12 19:44:04  gregg
// cleaning up CatNav funtionality
//
// Revision 1.18  2005/10/12 18:26:37  gregg
// some more CatNav work
//
// Revision 1.17  2005/10/12 16:34:48  gregg
// more MultiRowAttrGroup up into CatDBTableRecord.
//
// Revision 1.16  2005/10/10 21:19:35  gregg
// some multi flag stuff
//
// Revision 1.15  2005/10/10 20:21:36  gregg
// working on puts and such for multi-flags.
//
// Revision 1.14  2005/10/06 18:28:44  gregg
// add LongText type
//
// Revision 1.13  2005/10/06 18:23:12  gregg
// more props functionality (literal "val")
//
// Revision 1.12  2005/10/06 16:48:49  gregg
// some finals
//
// Revision 1.11  2005/10/05 22:54:15  gregg
// NO_TIMESTAMP_VAL
//
// Revision 1.10  2005/09/30 22:26:04  gregg
// fix my really flawed setAttribute from props logic. whoops.
//
// Revision 1.9  2005/09/30 21:59:30  gregg
// debug
//
// Revision 1.8  2005/09/23 19:46:27  gregg
// derive class name for property file interaction
//
// Revision 1.7  2005/09/23 18:10:21  gregg
// making setAttributesFromProps work again
//
// Revision 1.6  2005/09/23 17:35:03  gregg
// fix
//
// Revision 1.5  2005/09/23 17:26:02  gregg
// some set/getAttributes work
//
// Revision 1.4  2005/09/23 17:00:11  gregg
// CatDBTableRecord now extends CatItem
//
// Revision 1.3  2005/09/21 20:47:39  gregg
// oops. correct package name.
//
// Revision 1.2  2005/09/21 20:41:18  gregg
// comp[ile fix
//
// Revision 1.1  2005/09/21 19:35:54  gregg
// moving over from hula package
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
// add table name in CatDBTableRecord constructor
//
// Revision 1.5  2004/08/31 19:52:12  gregg
// blob store/get methods
//
// Revision 1.4  2004/08/31 19:12:55  gregg
// putBlobVal method stub
//
// Revision 1.3  2004/08/24 22:35:49  gregg
// implements EANComparable in CatDBTableRecord for nifty column sorting
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
import java.io.*;
import COM.ibm.opicmpdh.objects.*;
import java.lang.reflect.Field;
import COM.ibm.opicmpdh.transactions.NLSItem;

/**
 * Models one record in an ODS table.
 */
public abstract class CatDBTableRecord
    extends CatItem {

    //static final long serialVersionUID = 20011106L;

    public static final String NO_DATE_VAL = "01/01/1980";
    public static final String NO_TIMESTAMP_VAL = "1980-01-01-00.00.00.000000";

    public static final int STRING = CatDBTable.STRING;
    public static final int INT = CatDBTable.INT;
    public static final int BLOB = CatDBTable.BLOB;

    protected static final String m_strCatDBScema = "GBLI";

    public static final int NO_INT_VAL = 0;
    public static final String NO_STRING_VAL = "[No Value]";
    public static final String ENGLISH_ONLY_TOK = ":[ENGLISH_ONLY]";

    private Hashtable m_hashColVals = null;
    private String m_strCompareField = null;
    private String m_strTableName = null;
    private HashMap m_attCollection = null;
    private MultiRowAttrGroup m_mrag = null;

    /*
        public CatDBTableRecord(EANMetaFoundation _emf, Profile _prof, String _strKey, String _strTableName) throws MiddlewareRequestException {
            super(_emf,_prof,_strKey);
            m_strTableName = _strTableName;
            m_hashColVals = new Hashtable(getColumnCount());
        }
     */

    public CatDBTableRecord(String _strTableName, CatId _cid) {
        super(_cid);
        m_strTableName = _strTableName;
        m_hashColVals = new Hashtable(getColumnCount());
        m_attCollection = new HashMap();
    }

    public void putStringVal(String _strColKey, String _strVal) {
        _strVal = trimUnicodeString(_strVal, _strColKey, getColumnLen(_strColKey));
        putVal(_strColKey, _strVal);
        //D.ebug(D.EBUG_SPEW,"putStringVal(" + _strColKey + "," + _strVal);
        if (hasMultiRowAttrGroup() && getMultiRowAttrGroup().containsColumnKey(_strColKey)) {
            getMultiRowAttrGroup().getItem(_strColKey).putValue(_strVal);
        } else if (hasMultiRowAttrGroup() && getMultiRowAttrGroup().containsColumnKey_fc(_strColKey)) {
            //D.ebug(D.EBUG_SPEW,"containsColumnKey_fc b is true!!");
            getMultiRowAttrGroup().getItem(_strColKey.substring(0, _strColKey.length() - 3)).putValue_fc(_strVal);
        }
        setAttribute(_strColKey, _strVal);
    }

    public void putIntVal(String _strColKey, int _iVal) {
        putVal(_strColKey, new Integer(_iVal));
        setAttribute(_strColKey, new Integer(_iVal));
    }

    public void putBlobVal(String _strColKey, Blob _blob) {
        putVal(_strColKey, _blob);
        setAttribute(_strColKey, _blob);
    }

    public String getStringVal(String _strKey) {
        Object o = getVal(_strKey);
        if (o == null) {
            return null;
        }
        if (o.toString().equalsIgnoreCase("null") || o.toString().trim().equals("")) {
            return (getTable().allowColumnNulls(_strKey) ? null : "");
        }
        return o.toString();
    }

    public String getStringVal(int _i) {
        String strColKey = getColumnKey(_i);
        Object o = getVal(strColKey);
        if (o == null) {
            return null;
        }
        if (o.toString().equalsIgnoreCase("null") || o.toString().trim().equals("")) {
            return (getTable().allowColumnNulls(_i) ? null : "");
        }
        return o.toString();
    }

    public int getIntVal(String _strKey) {
        Integer objInt = (Integer) getVal(_strKey);
        if (objInt == null) {
            return NO_INT_VAL;
        }
        return objInt.intValue();
    }

    public int getIntVal(int _i) {
        String strColKey = getColumnKey(_i);
        Integer objInt = (Integer) getVal(strColKey);
        if (objInt == null) {
            return NO_INT_VAL;
        }
        return objInt.intValue();
    }

    public Blob getBlobVal(String _strKey) {
        Blob blob = (Blob) getVal(_strKey);
        if (blob == null) {
            return null;
        }
        return blob;
    }

    public Blob getBlobVal(int _i) {
        String strColKey = getColumnKey(_i);
        Blob blob = (Blob) getVal(strColKey);
        if (blob == null) {
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

    private final int getColumnLen(int _i) {
        return getTable().getColumnLen(_i);
    }

    private final int getColumnLen(String _strKey) {
        return getTable().getColumnLen(_strKey);
    }

    private void putVal(String _strKey, Object _obj) {
        if (_obj == null) {
            return;
        }
        m_hashColVals.put(_strKey, _obj);
    }

    private Object getVal(String _strKey) {
        if (_strKey == null) {
            return null;
        }
        return m_hashColVals.get(_strKey);
    }

    private Object getVal(int _i) {
        String strKey = getColumnKey(_i);
        if (strKey == null) {
            return null;
        }
        return m_hashColVals.get(strKey);
    }

    private final CatDBTable getTable() {
        return CatDBTable.getCatDBTable(getTableName());
    }

    public final boolean wasColumnValSet(String _strKey) {
        //return getTable().wasColumnValSet(_strKey);
        return (getVal(_strKey) != null);
    }

    public final boolean wasColumnValSet(int _i) {
        //return getTable().wasColumnValSet(_i);
        return (getVal(_i) != null);
    }

    //public abstract void update(Database _db) throws Exception;

    public String getTableName() {
        return m_strTableName;
    }

    /*
     * Version info
     */
    public String getVersion() {
        return new String("$Id: CatDBTableRecord.java,v 1.7 2011/05/05 11:21:31 wendy Exp $");
    }

    //
    // EANComparable
    //
//enforce a separate key for compare - this way objects can have different sort vals!
    public String toCompareString() {
        String s = getStringVal(getCompareField());
        if (s != null) {
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
        return (m_strCompareField != null ? m_strCompareField : "");
    }

    private String trimUnicodeString(String _strVal, String _strColName, int _iColLen) {
        if (_strVal == null) {
            return _strVal;
        }
        try {
            int iLength = _strVal.getBytes("UTF-8").length;
            String strAnswer = new String(_strVal);
            if (iLength > _iColLen) {
                D.ebug(D.EBUG_WARN, "**trimUnicodeString: Length Exceeds max length for " + _strColName + ":" + _strVal);
                while (iLength > _iColLen) {
                    strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
                    iLength = strAnswer.getBytes("UTF-8").length;
                }
            }
            _strVal = strAnswer;
        }
        catch (UnsupportedEncodingException ex) {
            D.ebug(D.EBUG_ERR, "**trimUnicodeString: UnsupportedEncodingException.  Passing entire string back." + ex.getMessage());
        }
        return _strVal;
    }

    //////////////////
    //////////////////
    //////////////////
    public void setAttribute(String _strCol, Object _oAtt) {
        m_attCollection.put(_strCol, _oAtt);
        return;
    }

    public Object getAttribute(String _strTag) {
        if (m_attCollection.containsKey(_strTag)) {
            return m_attCollection.get(_strTag);
        } else {
            System.out.println("attribute not found for " + _strTag);
        }
        return null;
    }

    /**
     * get attribute keys
     * 20050808
     * @return keys[]
     * @author tony
     */
    public String[] getAttributeKeys() {
        if (m_attCollection != null) {
            Set keys = m_attCollection.keySet();
            if (keys != null) {
                return (String[]) keys.toArray(new String[m_attCollection.size()]);
            }
        }
        return null;
    }

    //////////////////
    //////////////////
    //////////////////
    public void writeXMLString(XMLWriter _xml, String _strColName) throws Exception {
        _xml.writeEntity(_strColName);
        _xml.write(getStringVal(_strColName));
        _xml.endEntity();
    }

    public void writeXMLInt(XMLWriter _xml, String _strColName) throws Exception {
        _xml.writeEntity(_strColName);
        _xml.write(String.valueOf(getIntVal(_strColName)));
        _xml.endEntity();
    }

///
/// Some attribute-catdb column populating methods
///

    /**
     * given an EntityItem, go through and pull out values and place them into corresponding buckets
     */
    protected final void setAttributesFromProps(EntityItem _ei, String _strVE) {

        Class c = this.getClass();
        Field[] aFields = c.getDeclaredFields();
        String strClassName = getClass().getName();
        StringTokenizer st0 = new StringTokenizer(strClassName, ".");
        st0.nextToken();
        st0.nextToken();
        st0.nextToken();
        st0.nextToken();
        strClassName = st0.nextToken().toLowerCase();
        FIELD_LOOP:for (int i = 0; i < aFields.length; i++) {
            String strName = aFields[i].getName();
            String strPropVal = CatalogProperties.getColAttMapping(strClassName, _strVE, strName);
            if (strPropVal == null || strPropVal.equals("")) {
                continue FIELD_LOOP;
            }

            // this is the part where we want to skip if we are not on the right entity...
            // blah...we wanna be smart and scan ahead..
            if (strPropVal.indexOf(":") > -1) {
                boolean bOnTheRightEntityType = false;
                StringTokenizer stCheckPlus = new StringTokenizer(strPropVal, "+");
                PLUS_LOOP:while (stCheckPlus.hasMoreTokens()) {
                    StringTokenizer stCheck = new StringTokenizer(stCheckPlus.nextToken(), ":");
                    String strETCheck = stCheck.nextToken();
                    if (_ei.getEntityType().equals(strETCheck)) {
                        bOnTheRightEntityType = true;
                        continue PLUS_LOOP;
                    }
                }
                if (!bOnTheRightEntityType) {
                    continue FIELD_LOOP;
                }
            }
            if (strPropVal.indexOf("+") > -1) {
                StringTokenizer st = new StringTokenizer(strPropVal, "+");
                StringBuffer sbFinalVal = new StringBuffer();
                while (st.hasMoreTokens()) {
                    String strPropBlock = st.nextToken();
                    String strDefBlock = null;
                    //
                    if (strPropBlock != null && strPropBlock.indexOf("{") > -1 && strPropBlock.indexOf("}") > -1 &&
                        (strPropBlock.indexOf("}") > strPropBlock.indexOf("{"))) {
                        int idx1 = strPropBlock.indexOf("{");
                        int idx2 = strPropBlock.indexOf("}");
                        String strOG = strPropBlock;
                        strPropBlock = strOG.substring(0, idx1);
                        strDefBlock = strOG.substring(idx1 + 1, idx2);
                    }
                    //
                    String[] sa = getValFromPropBlock(_ei, strPropBlock);
                    if (sa[0] == null) {
                        // check default
                        if (strDefBlock != null) {
                            sa = getValFromPropBlock(_ei, strDefBlock);
                        }
                        // if STILL null...
                        if (sa[0] == null) {
                            sa[0] = NO_STRING_VAL;
                        }
                    }

                    sbFinalVal.append(sa[0]);
                }
                putStringVal(strName, sbFinalVal.toString());
            } else {
                //
                String strDefBlock = null;
                if (strPropVal != null && strPropVal.indexOf("{") > -1 && strPropVal.indexOf("}") > -1 &&
                    (strPropVal.indexOf("}") > strPropVal.indexOf("{"))) {
                    int idx1 = strPropVal.indexOf("{");
                    int idx2 = strPropVal.indexOf("}");
                    String strOG = strPropVal;
                    strPropVal = strOG.substring(0, idx1);
                    strDefBlock = strOG.substring(idx1 + 1, idx2);
                }
                //
                String[] sa = getValFromPropBlock(_ei, strPropVal);
                if (sa[0] == null || sa[0].equals("")) {
                    sa = getValFromPropBlock(_ei, strDefBlock);
                    // if STILL null...
                    if (sa[0] == null) {
                        continue; // FIELD_LOOP;
                    }
                }
                if (sa[1].equalsIgnoreCase("int")) {
                    putIntVal(strName, new Integer(sa[0]).intValue());
                } else if (sa[1].equalsIgnoreCase("String")) { // single flag, text, longtext cases
                    if (sa[0].indexOf("::") > -1) {
                        //D.ebug(D.EBUG_SPEW,"sa[0] is:" + sa[0]);
                        StringTokenizer st = new StringTokenizer(sa[0], "::");
                        String strVal = st.nextToken();
                        String strFC = st.nextToken();
                        putStringVal(strName, strVal);
                        putStringVal(strName + "_FC", strFC);
                    } else {
                        putStringVal(strName, sa[0]);
                    }
                } else if (sa[1].equalsIgnoreCase("String-Multi")) { // multi-flag case
                    StringTokenizer st1 = new StringTokenizer(sa[0], "::");
                    String strVal = st1.nextToken();
                    String strFC = st1.nextToken();
                    StringTokenizer st2 = new StringTokenizer(strVal, "|");
                    StringTokenizer st3 = new StringTokenizer(strFC, "|");
                    // catnav style - we want to explode these into rows...
                    if (hasMultiRowAttrGroup() && getMultiRowAttrGroup().containsColumnKey(strName)) {
                        while (st2.hasMoreTokens()) {
                            // these are to allow are multiflag put code to fire on each value!! hacky, but worky...
                            putStringVal(strName, st2.nextToken());
                        }
                        while (st3.hasMoreTokens()) {
                            putStringVal(strName + "_FC", st3.nextToken());
                        }
                    } else {
                        putStringVal(strName, strVal);
                        putStringVal(strName + "_FC", strFC);
                    }
                }
            }
        }
    }

    /**
     * Pull out one value from a block of properties values for collattmap
     * Because we can do additions of fields and things...
     *    [0] is val (FC seperated by ":")
     *    [1] is data type
     */
    private final String[] getValFromPropBlock(EntityItem _ei, String _strPropBlock) {

        String[] sa = new String[2];
        if (_strPropBlock == null) {
            return sa;
        }
		// Allow us to force english only on any part of a column's value block (meaning, anything seperated by "+", etc...
        Profile prof = _ei.getProfile();
        NLSItem nlsItemCurr = prof.getReadLanguage();
		if(_strPropBlock.indexOf(ENGLISH_ONLY_TOK) > -1) {
            D.ebug(D.EBUG_SPEW, "CatDBTableRecord [ENGLISH_ONLY] found for:" + _ei.getKey() + " (Curr nls is " + nlsItemCurr.getNLSID() + ")");
            Vector vct = prof.getReadLanguages();
            for (int i = 0; i < vct.size(); i++) {
                NLSItem nlsi = (NLSItem) vct.elementAt(i);
                if (nlsi.getNLSID() == 1) {
                    D.ebug(D.EBUG_SPEW,"CatDBTableRecord [ENGLISH_ONLY]: setting read language to:" + nlsi.getNLSID() + " for block:\"" + _strPropBlock + "\"");
                    prof.setReadLanguage(nlsi);
                    break;
                }
            }
            // remove it.
            StringUtil.replaceFirst(_strPropBlock,ENGLISH_ONLY_TOK,"");
		}
		//
        if (_strPropBlock.indexOf("\"") > -1) {
            // This part need some help I think..

            // ok, we have two cases here:
            //  1) its a standalone val, so we need to verify entitytype...\
            //  2) its just a "dumb" value, and we take it literally...
            //
            // 1:
            if (_strPropBlock.indexOf(":") > -1) {
                StringTokenizer st = new StringTokenizer(_strPropBlock, ":");
                String strEntType = st.nextToken();
                if (!_ei.getEntityType().equals(strEntType)) {
                    return sa;
                }
                String strVal = st.nextToken();
                strVal = strVal.substring(1, strVal.length() - 1);
                String strDataType = st.nextToken();
                sa[0] = strVal;
                sa[1] = (strDataType.equalsIgnoreCase("int") ? "int" : "String");
            } else { //2
                String strVal = _strPropBlock.substring(1, _strPropBlock.length() - 1);
                sa[0] = strVal;
                sa[1] = "String";
            }
        } else if (_strPropBlock.indexOf(":") > -1) {
            StringTokenizer st = new StringTokenizer(_strPropBlock, ":");
            String strEntType = st.nextToken();
            // let's just stop right here if we're off...
            if (!_ei.getEntityType().equals(strEntType)) {
                return sa;
            }
            String strAttCode = st.nextToken();
            String strAttType = st.nextToken();
            String strDataType = st.nextToken();
            if (strAttType.equals("D")) {
                if (strAttCode.equals("ENTITYID")) {
                    sa[0] = String.valueOf(_ei.getEntityID());
                    sa[1] = "int";
                } else if (strDataType.equals("LanguageMapper")) { // LanguageMapper implies unique flags.
                    D.ebug(D.EBUG_SPEW, "CatDBTableRecord:LanguageMapper att found...");
                    String strVal = getAttributeValue(_ei, strAttCode)[0];
                    String strFC = getAttributeValue(_ei, strAttCode)[1];
                    if (strFC != null && strFC.length() > 0) {
                        String[] saMappings = Catalog.getLanguageMapper().getMapping(strAttCode, strVal, strFC);
                        if(saMappings[0] == null || saMappings[0].equals("")) {
							D.ebug(D.EBUG_WARN,"NO MAPPING FOR LANGUAGE" + _ei.getKey());
						}
                        sa[0] = saMappings[0] + "::" + saMappings[1]; //strVal + "::" + strFC;
                        sa[1] = "String";
                    } else {
                        D.ebug(D.EBUG_WARN,"NO VALUE IN THE PDH HAS BEEN SET YET FOR LANGUAGE" + _ei.getKey() + " SO NOTHING TO MAP");
                    }
                }
            } else if (strAttType.equals("U")) { // unique flag
                if (_ei.getEntityType().equals(strEntType)) {
                    String strVal = getAttributeValue(_ei, strAttCode)[0];
                    String strFC = getAttributeValue(_ei, strAttCode)[1];
                    if (strFC != null && strFC.length() > 0) {
                        sa[0] = strVal + "::" + strFC;
                        sa[1] = "String";
                    }
                }
            } else if (strAttType.equals("F")) { // multi flag
                if (_ei.getEntityType().equals(strEntType)) {
                    String strVal = getAttributeValue(_ei, strAttCode)[0];
                    String strFC = getAttributeValue(_ei, strAttCode)[1];
                    if (strFC != null && strFC.length() > 0) {
                        sa[0] = strVal + "::" + strFC;
                        sa[1] = "String";
                    }
                    sa[0] = strVal + "::" + strFC;
                    sa[1] = "String-Multi";
                }
            } else if (strAttType.equals("T")) {
                if (_ei.getEntityType().equals(strEntType)) {
                    sa[0] = getAttributeValue(_ei, strAttCode)[0];
                    sa[1] = strDataType;
                }
            }
        }
        // we want to set this back in the case we have locked it to English Only..
        prof.setReadLanguage(nlsItemCurr);
        //
        return sa;
    }

    /**
     * This pulls out an attribute value from an entity
     */
    protected static final String[] getAttributeValue(EntityItem _ei, String _strAttCode) {
        EANMetaAttribute ema = _ei.getEntityGroup().getMetaAttribute(_strAttCode);
        D.ebug(D.EBUG_SPEW, "getAttributeValue, looking at:" + _ei.getKey() + "." + _strAttCode);
        String[] saVals = new String[2];
        if (ema != null) {
            String strAttType = ema.getAttributeType();

            if (strAttType.equals("T") || strAttType.equals("L") || strAttType.equals("X")) {
                EANTextAttribute ta = (EANTextAttribute) _ei.getAttribute(_strAttCode);
                D.ebug(D.EBUG_SPEW, "getAttributeValue, strAttType for Text is:" + strAttType);
                if (ta != null) {
                    D.ebug(D.EBUG_SPEW, "getAttributeValue text val is:" + ta.toString().trim());
                    saVals[0] = ta.toString().trim();
                }
            } else if (strAttType.equals("U") || strAttType.equals("S")) {
                saVals = new String[2];
                EANFlagAttribute fa = (EANFlagAttribute) _ei.getAttribute(_strAttCode);
                if (fa != null) {
                    String strFlagVal = fa.toString().trim();
                    if (strFlagVal != null && strFlagVal.length() > 1 && strFlagVal.startsWith("*")) {
                        strFlagVal = strFlagVal.substring(2);
                    }
                    saVals[0] = strFlagVal;
                    saVals[1] = fa.getFirstActiveFlagCode().trim();
                    D.ebug(D.EBUG_SPEW, "getAttributeValue flag vals:\"" + saVals[0] + "\", \"" + saVals[1] + "\"");
                }
            } else if (strAttType.equals("F")) { // multi!!
                saVals = new String[2];
                EANFlagAttribute fa = (EANFlagAttribute) _ei.getAttribute(_strAttCode);
                if (fa == null) {
                    D.ebug(D.EBUG_SPEW, "fa is null for:" + _strAttCode);
                }
                if (fa != null) {
                    String strDesc = null;
                    String strFC = null;
                    MetaFlag[] amf = (MetaFlag[]) fa.get();
                    for (int i = 0; i < amf.length; i++) {
                        MetaFlag mf = amf[i];
                        //D.ebug(D.EBUG_SPEW, "mf:" + mf.getLongDescription() + ":isSelected:" + mf.isSelected());
                        if (!mf.isSelected()) {
                            continue;
                        }
                        if (strDesc != null) {
                            strDesc += "|";
                            strFC += "|";
                        } else {
                            strDesc = "";
                            strFC = "";
                        }
                        String strFlagVal = mf.getLongDescription().trim();
                        if (strFlagVal != null && strFlagVal.length() > 1 && strFlagVal.startsWith("*")) {
                            strFlagVal = strFlagVal.substring(2);
                        }
                        //
                        strDesc += strFlagVal;
                        strFC += mf.getFlagCode();
                    }
                    saVals[0] = strDesc;
                    saVals[1] = strFC;
                    D.ebug(D.EBUG_SPEW, "getAttributeValue flag vals:\"" + saVals[0] + "\", \"" + saVals[1] + "\"");
                }
            }
        } else {
            D.ebug(D.EBUG_WARN, "WARNING: getAttributeValue for " + _ei.getEntityType() + "." + _strAttCode + " ema is NULL");
        }
        return saVals;
    }

//
// CatNav stuff
//
    protected void setMultiRowAttrGroup(MultiRowAttrGroup _mrag) {
        m_mrag = _mrag;
    }

    protected MultiRowAttrGroup getMultiRowAttrGroup() {
        return m_mrag;
    }

    protected boolean hasMultiRowAttrGroup() {
        return (getMultiRowAttrGroup() != null);
    }

    protected String getStringVal(String _strColKey, int _i) {
        if (!hasMultiRowAttrGroup()) {
            return getStringVal(_strColKey);
        }
        //D.ebug(D.EBUG_SPEW,"getStringVal.. objectKey = " + getMultiRowAttrGroup().getObjectKey() + " for MultiRowAttrGroup, getValueCombinationCount()=" + getMultiRowAttrGroup().getValueCombinationCount() + ", getPopulatedItemCount()=" + getMultiRowAttrGroup().getPopulatedItemCount() + ", trying for _strColKey=\"" + _strColKey + "\", _i=" + _i);
        String s = getMultiRowAttrGroup().getValueCombo(_strColKey, _i);
        if (s == null) {
            return getStringVal(_strColKey);
        }
        return s;
    }

    protected static String[] mergeFlagVals(String[] _sa1, String[] _sa2) {
        if (_sa1 == null && _sa2 == null) {
            return null;
        }
        if (_sa1 != null && _sa2 == null) {
            return _sa1;
        }
        if (_sa1 == null && _sa2 != null) {
            return _sa2;
        }
        // ok, both not null so we can continue...
        String[] saNew = new String[2];
        // Here's where we actually put in some logic later which rids dups!!!
        saNew[0] = _sa1[0] + "|" + _sa2[0];
        saNew[1] = _sa1[1] + "|" + _sa2[1];
        return saNew;
    }

    protected static String[] intersectFlagVals(String[] _sa1, String[] _sa2) {
        // Since its an intersection, any empty or null value means return null.
        if (_sa1 == null || _sa2 == null || _sa1[1] == null || _sa1[1].equals("") || _sa2[1] == null || _sa2[1].equals("")) {
            return null;
        }
        // ok, both not null so we can continue...
        String[] saNew = new String[2];
        Hashtable hsh = new Hashtable();
        // use FC's
        // !!! Re: We need to really look at if these are going to be "|" delimited or not
        StringTokenizer st1 = new StringTokenizer(_sa1[0],"|");
        StringTokenizer st1_FC = new StringTokenizer(_sa1[1],"|");
        StringTokenizer st2_FC = new StringTokenizer(_sa2[1],"|");
        while(st1_FC.hasMoreTokens()) {
			hsh.put(st1_FC.nextToken(),st1.nextToken());
		}
        while(st2_FC.hasMoreTokens()) {
			String strVal_FC = st2_FC.nextToken();
			String strVal = (String)hsh.get(strVal_FC);
			D.ebug(D.EBUG_SPEW,"intersectFlagVals-->one fc value is:" + strVal_FC + "...");
			if(strVal != null && !strVal.equals("")) { // i.e. found an intersection
				if(saNew[0] == null || saNew[0].equals("")) { // prime the proverbial pump
					saNew[0] = strVal;
					saNew[1] = strVal_FC;
					D.ebug(D.EBUG_SPEW,"intersectFlagVals-->starting w/ " + saNew[1]);
				} else {
					saNew[0] = saNew[0] + "|" + strVal;
					saNew[1] = saNew[1] + "|" + strVal_FC;
					D.ebug(D.EBUG_SPEW,"intersectFlagVals-->adding to it we have:" + saNew[1]);
				}
			}
		}
		D.ebug(D.EBUG_SPEW,"intersectFlagVals-->And finally we have:" + saNew[1]);
        return saNew;
    }
}
