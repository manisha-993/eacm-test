//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eProductDetailLong.java,v $
// Revision 1.23  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.22  2005/02/09 22:13:43  dave
// more JTest Cleanup
//
// Revision 1.21  2005/01/04 23:21:27  gregg
// some more for prod detail rel
//
// Revision 1.20  2004/12/01 22:00:38  gregg
// fixes
//
// Revision 1.19  2004/10/15 21:02:10  gregg
// focus on loops: settin temp variables to save access times
//
// Revision 1.18  2004/10/15 17:58:55  gregg
// more ticky tack tweaks
//
// Revision 1.17  2004/10/15 17:25:25  gregg
// dropping in some finals
//
// Revision 1.16  2004/09/30 00:22:40  dave
// syntax
//
// Revision 1.15  2004/09/24 22:08:22  gregg
// sum fixez
//
// Revision 1.14  2004/09/24 22:00:18  gregg
// de-singletonize PreparedStatementCollection.
// This is now accessed through eProductUpdater.
//
// Revision 1.13  2004/09/23 15:58:25  gregg
// remove debugs
//
// Revision 1.12  2004/09/21 17:09:29  gregg
// some PreparedStatement reuse
//
// Revision 1.11  2004/09/10 18:21:41  gregg
// delete()
//
// Revision 1.10  2004/09/02 23:44:03  gregg
// wasLongValueChanged
//
// Revision 1.9  2004/09/02 23:31:03  gregg
// trim down update logic
//
// Revision 1.8  2004/09/02 23:04:25  gregg
// some long detail updating trickery
//
// Revision 1.7  2004/09/02 21:03:29  gregg
// use eRecordCollection to manage longtext records
//
// Revision 1.6  2004/09/01 22:38:08  gregg
// prodDetailLong ingestion
//
// Revision 1.5  2004/09/01 21:35:10  gregg
// fix long insert
//
// Revision 1.4  2004/09/01 19:09:16  gregg
// fix
//
// Revision 1.3  2004/09/01 17:40:09  gregg
// isEquivalent()
//
// Revision 1.2  2004/09/01 17:37:40  gregg
// SQL fix
//
// Revision 1.1  2004/09/01 17:37:15  gregg
// initial load
//
//

package COM.ibm.eannounce.hula;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is really a helper for eProductDetail.  It cannot exist without an eProductDetail Parent.
 */
public final class eProductDetailLong extends eTableRecord {

    static final long serialVersionUID = 20011106L;

    /**
     * FIELD
     */
    public static final String TABLE_NAME = "PRODUCTDETAILLONG";

    /**
     * FIELD
     */
    public static final String ENTERPRISE = "ENTERPRISE";
    /**
     * FIELD
     */
    public static final String NLSID = "NLSID";
    /**
     * FIELD
     */
    public static final String CHILDTYPE = "CHILDTYPE";
    /**
     * FIELD
     */
    public static final String CHILDID = "CHILDID";
    /**
     * FIELD
     */
    public static final String ATTRIBUTECODE = "ATTRIBUTECODE";
    /**
     * FIELD
     */
    public static final String ATTRIBUTEVALUE = "ATTRIBUTEVALUE";

    static {

        // PRODUCTDETAILLONG TABLE
        eTable.getETable(TABLE_NAME).makeStringColumn(ENTERPRISE, 8);
        eTable.getETable(TABLE_NAME).makeIntColumn(NLSID);
        eTable.getETable(TABLE_NAME).makeStringColumn(CHILDTYPE, 32);
        eTable.getETable(TABLE_NAME).makeIntColumn(CHILDID);
        eTable.getETable(TABLE_NAME).makeStringColumn(ATTRIBUTECODE, 32);
        eTable.getETable(TABLE_NAME).makeStringColumn(ATTRIBUTEVALUE, 32700);

    }

    /**
     * FIELD
     */
    public static final String NO_COLUMN_VAL = eProductDetail.NO_COLUMN_VAL;

    private static final String ODS_SCHEMA = eProductProperties.getDatabaseSchema();
    private boolean m_bWasValueChanged = false;

    /**
     * eProductDetailLong
     *
     * @param _prodDetail
     * @param _strAttVal
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    protected eProductDetailLong(eProductDetail _prodDetail, String _strAttVal) throws MiddlewareRequestException, MiddlewareException, SQLException {

        super(_prodDetail, _prodDetail.getProfile(), (buildKey(_prodDetail)), TABLE_NAME);

        putStringVal(ENTERPRISE, getProfile().getEnterprise());
        putIntVal(NLSID, _prodDetail.getIntVal(eProductDetail.NLSID));
        putStringVal(CHILDTYPE, _prodDetail.getStringVal(eProductDetail.ENTITYTYPE));
        putIntVal(CHILDID, _prodDetail.getIntVal(eProductDetail.ENTITYID));
        putStringVal(ATTRIBUTECODE, _prodDetail.getStringVal(ATTRIBUTECODE));
        putStringVal(ATTRIBUTEVALUE, _strAttVal);
    }

    /**
     * buildKey
     *
     * @param _prodDetail
     * @return
     *  @author David Bigelow
     */
    protected static final String buildKey(eProductDetail _prodDetail) {
        String s0 = "PDLONG:";
        String s1 = _prodDetail.getStringVal(eProductDetail.ENTERPRISE);
        int i1 = _prodDetail.getIntVal(eProductDetail.NLSID);
        String s2 = _prodDetail.getStringVal(eProductDetail.ENTITYTYPE);
        int i2 = _prodDetail.getIntVal(eProductDetail.ENTITYID);
        String s3 = _prodDetail.getStringVal(eProductDetail.ATTRIBUTECODE);
        return s0 + s1 + i1 + s2 + i2 + s3;
    }

    /**
     * getProductHeader
     *
     * @return
     *  @author David Bigelow
     */
    public final eProduct getProductHeader() {
        return getProductDetail().getProductHeader();
    }

    /**
     * getProductDetail
     *
     * @return
     *  @author David Bigelow
     */
    public final eProductDetail getProductDetail() {
        return (eProductDetail) getParent();
    }

    /**
     * getProductUpdater
     *
     * @return
     *  @author David Bigelow
     */
    protected final eProductUpdater getProductUpdater() {
        return getProductDetail().getProductUpdater();
    }
    /**
     * getPreparedStatementCollection
     *
     * @return
     *  @author David Bigelow
     */
    protected final PreparedStatementCollection getPreparedStatementCollection() {
        return getProductUpdater().getPreparedStatementCollection();
    }

    /**
     * Make the current Object look like the passed Object.
     *
     * @param _prodDetailLong 
     */

    public final void ingest(eProductDetailLong _prodDetailLong) {
        for (int i = 0; i < getColumnCount(); i++) {
            int iColType = getColumnType(i);
            if (iColType == INT) {
                putIntVal(getColumnKey(i), _prodDetailLong.getIntVal(i));
            } else if (iColType == STRING) {
                String strColumnKey = getColumnKey(i);
                String strValThat = _prodDetailLong.getStringVal(i);
                if (strColumnKey.equals(ATTRIBUTEVALUE)) {
                    if (!getStringVal(i).equals(strValThat)) {
                        m_bWasValueChanged = true;
                    }
                }
                putStringVal(strColumnKey, strValThat);
            } // else if(getColumnType(i) == BLOB) {
            //  putBlobVal(getColumnKey(i),_prodDetail.getBlobVal(i));
            //}
        }
    }

    /**
     * wasLongValueChanged
     *
     * @return
     *  @author David Bigelow
     */
    protected final boolean wasLongValueChanged() {
        return m_bWasValueChanged;
    }

    /**
     * Check column values for equality -- if the relevant columns are equals, we are not putting this ProductDetail.
     * We want to skip valfrom check. this is obvious.
     * We want to skip Sequences ... these are calculated after we do the equals() check
     *
     * @return boolean
     * @param _eProdDetailLong 
     */

    public final boolean isEquivalent(eProductDetailLong _eProdDetailLong) {
        //COLUMN_LOOP : 
        for (int i = 0; i < getColumnCount(); i++) {
            int iColType = getColumnType(i);
            if (iColType == INT) {
                if (getIntVal(i) != _eProdDetailLong.getIntVal(i)) {
                    //eProductUpdater.debug(getIntVal(i) + " != " + _eProdDetail.getIntVal(i));
                    return false;
                }
            } else if (iColType == STRING) {
                if (!getStringVal(i).equals(_eProdDetailLong.getStringVal(i))) {
                    //eProductUpdater.debug("\"" + getStringVal(i) + "\"" + " !equals \"" + _eProdDetail.getStringVal(i) + "\"");
                    return false;
                }
            } else if (iColType == BLOB) {
                return true;
                //if(!getStringVal(i).equals(_eProdDetail.getStringVal(i))) {
                //  //eProductUpdater.debug("\"" + getStringVal(i) + "\"" + " !equals \"" + _eProdDetail.getStringVal(i) + "\"");
                //  return false;
                //}
            }
        }
        return true;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public final String dump(boolean _b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < getColumnCount(); i++) {
            sb.append(getStringVal(i) + ":");
        }
        return sb.toString();
    }

    /**
     * delete
     *
     * @param _conODS
     * @throws java.lang.Exception
     *  @author David Bigelow
     */
    public final void delete(Connection _conODS) throws Exception {

        PreparedStatement psDelete = getPreparedStatementCollection().getPS(PreparedStatementCollection.PRODUCTDETAILLONG_DELETE);

        if (psDelete == null) {

            String strDeleteSQL = "DELETE FROM " + ODS_SCHEMA + ".PRODUCTDETAILLONG " + " WHERE " + " ENTERPRISE = ? AND " + " CHILDTYPE = ? AND " + " CHILDID = ? AND " + " ATTRIBUTECODE = ? AND " + " NLSID = ?";
            psDelete = _conODS.prepareStatement(strDeleteSQL);
            getPreparedStatementCollection().putPS(PreparedStatementCollection.PRODUCTDETAILLONG_DELETE, psDelete);
        }

        psDelete.clearParameters();

        psDelete.setString(1, getStringVal(ENTERPRISE));
        psDelete.setString(2, getStringVal(CHILDTYPE));
        psDelete.setInt(3, getIntVal(CHILDID));
        psDelete.setString(4, getStringVal(ATTRIBUTECODE));
        psDelete.setInt(5, getIntVal(NLSID));

        psDelete.executeUpdate();

    }

    /**
     * (non-Javadoc)
     * update
     *
     * @see COM.ibm.eannounce.hula.eTableRecord#update(COM.ibm.opicmpdh.middleware.Database)
     */
    public final void update(Database _db) throws Exception {

        Connection conODS = _db.getODSConnection();
        PreparedStatement psInsert = null;
        
        if (!isActive()) {
            return;
        }
        delete(conODS);

        try {
            
            psInsert = getPreparedStatementCollection().getPS(PreparedStatementCollection.PRODUCTDETAILLONG_INSERT);
    
            if (psInsert == null) {
    
                String strInsertSQL = null;
                StringBuffer sbCols = new StringBuffer();
                StringBuffer sbQuestions = new StringBuffer();
            
                for (int i = 0; i < getColumnCount(); i++) {
                    if (i > 0) {
                        sbCols.append(",");
                        sbQuestions.append(",");
                    }
                    sbCols.append(getColumnKey(i));
                    sbQuestions.append("?");
                }
                strInsertSQL = "INSERT INTO " + ODS_SCHEMA + ".PRODUCTDETAILLONG " + "( " + sbCols.toString() + ") " + "VALUES (" + sbQuestions.toString() + ")";
                psInsert = conODS.prepareStatement(strInsertSQL);
                getPreparedStatementCollection().putPS(PreparedStatementCollection.PRODUCTDETAILLONG_INSERT, psInsert);
            }
    
            psInsert.clearParameters();
            for (int i = 0; i < getColumnCount(); i++) {
                if (getColumnType(i) == INT) {
                    psInsert.setInt(i + 1, getIntVal(i));
                } else if (getColumnType(i) == STRING) {
                    psInsert.setString(i + 1, getStringVal(i));
                }
            }
            psInsert.executeUpdate();
            setActive(false);

        } finally {
                      
        }
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: eProductDetailLong.java,v 1.23 2008/01/31 21:05:17 wendy Exp $";
    }
}
