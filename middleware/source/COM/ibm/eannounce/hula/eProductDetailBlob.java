//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eProductDetailBlob.java,v $
// Revision 1.22  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.21  2005/02/09 22:13:43  dave
// more JTest Cleanup
//
// Revision 1.20  2005/01/04 23:21:27  gregg
// some more for prod detail rel
//
// Revision 1.19  2004/12/01 22:02:08  gregg
// fixes
//
// Revision 1.18  2004/10/15 21:02:10  gregg
// focus on loops: settin temp variables to save access times
//
// Revision 1.17  2004/10/15 17:58:55  gregg
// more ticky tack tweaks
//
// Revision 1.16  2004/10/15 17:25:25  gregg
// dropping in some finals
//
// Revision 1.15  2004/10/07 05:21:00  dave
// fixed the close all statement
//
// Revision 1.14  2004/10/07 04:51:21  dave
// trace on Blob Delete
//
// Revision 1.13  2004/09/30 00:22:40  dave
// syntax
//
// Revision 1.12  2004/09/24 22:00:19  gregg
// de-singletonize PreparedStatementCollection.
// This is now accessed through eProductUpdater.
//
// Revision 1.11  2004/09/23 15:58:25  gregg
// remove debugs
//
// Revision 1.10  2004/09/21 17:09:29  gregg
// some PreparedStatement reuse
//
// Revision 1.9  2004/09/21 00:56:02  gregg
// fix
//
// Revision 1.8  2004/09/10 19:24:16  gregg
// hasBlobValue
//
// Revision 1.7  2004/09/10 19:21:23  gregg
// getBlobValue
//
// Revision 1.6  2004/09/10 18:20:59  gregg
// delete for blob
//
// Revision 1.5  2004/09/09 21:14:56  gregg
// update for blobs
//
// Revision 1.4  2004/09/03 19:18:11  gregg
// more blob stuff
//
// Revision 1.3  2004/09/03 18:44:13  gregg
// attributefilename
//
// Revision 1.2  2004/09/03 18:22:25  gregg
// blobs
//
// Revision 1.1  2004/09/03 17:40:58  gregg
// initial load
//
//
//

package COM.ibm.eannounce.hula;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is really a helper for eProductDetail.  It cannot exist without an eProductDetail Parent.
 */
public final class eProductDetailBlob extends eTableRecord {

    static final long serialVersionUID = 20011106L;

    /**
     * FIELD
     */
    public static final String TABLE_NAME = "PRODUCTDETAILBLOB";

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
    public static final String ATTRIBUTEFILENAME = "ATTRIBUTEFILENAME";
    /**
     * FIELD
     */
    public static final String ATTRIBUTEVALUE = "ATTRIBUTEVALUE";

    static {

        // PRODUCTDETAILBLOB TABLE
        eTable.getETable(TABLE_NAME).makeStringColumn(ENTERPRISE, 8);
        eTable.getETable(TABLE_NAME).makeIntColumn(NLSID);
        eTable.getETable(TABLE_NAME).makeStringColumn(CHILDTYPE, 32);
        eTable.getETable(TABLE_NAME).makeIntColumn(CHILDID);
        eTable.getETable(TABLE_NAME).makeStringColumn(ATTRIBUTECODE, 32);
        eTable.getETable(TABLE_NAME).makeStringColumn(ATTRIBUTEFILENAME, 128);
        eTable.getETable(TABLE_NAME).makeBlobColumn(ATTRIBUTEVALUE);

    }

    /**
     * FIELD
     */
    public static final String NO_COLUMN_VAL = eProductDetail.NO_COLUMN_VAL;

    private static final String ODS_SCHEMA = eProductProperties.getDatabaseSchema();

    //
    private boolean m_bWasValueChanged = false;
    //

    /**
     * eProductDetailBlob
     *
     * @param _prodDetail
     * @param _blob
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    protected eProductDetailBlob(eProductDetail _prodDetail, COM.ibm.opicmpdh.objects.Blob _blob) throws MiddlewareRequestException, MiddlewareException, SQLException {

        super(_prodDetail, _prodDetail.getProfile(), (buildKey(_prodDetail)), TABLE_NAME);

        try {
            String strAttFileName = _blob.getBlobExtension();

            putStringVal(ENTERPRISE, getProfile().getEnterprise());
            putIntVal(NLSID, _prodDetail.getIntVal(eProductDetail.NLSID));
            putStringVal(CHILDTYPE, _prodDetail.getStringVal(eProductDetail.ENTITYTYPE));
            putIntVal(CHILDID, _prodDetail.getIntVal(eProductDetail.ENTITYID));
            putStringVal(ATTRIBUTECODE, _prodDetail.getStringVal(ATTRIBUTECODE));
            putStringVal(ATTRIBUTEFILENAME, strAttFileName);
            putBlobVal(ATTRIBUTEVALUE, _blob);
        } finally {
            //
        }
    }
    /**
     * buildKey
     *
     * @param _prodDetail
     * @return
     *  @author David Bigelow
     */
    protected static final String buildKey(eProductDetail _prodDetail) {
        String s0 = "PDBLOB:";
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
     * @param _prodDetailBlob 
     */

    public final void ingest(eProductDetailBlob _prodDetailBlob) {
        for (int i = 0; i < getColumnCount(); i++) {
            int iColType = getColumnType(i);
            if (iColType == INT) {
                putIntVal(getColumnKey(i), _prodDetailBlob.getIntVal(i));
            } else if (iColType == STRING) {
                String strColKey = getColumnKey(i);
                String strValThat = _prodDetailBlob.getStringVal(i);
                if (strColKey.equals(ATTRIBUTEVALUE)) {
                    if (!getStringVal(i).equals(strValThat)) {
                        m_bWasValueChanged = true;
                    }
                }
                putStringVal(strColKey, strValThat);
            } else if (iColType == BLOB) {
                putBlobVal(getColumnKey(i), _prodDetailBlob.getBlobVal(i));
            }
        }
    }

    /**
     * wasBlobValueChanged
     *
     * @return
     *  @author David Bigelow
     */
    protected final boolean wasBlobValueChanged() {
        return m_bWasValueChanged;
    }

    /**
     * Check column values for equality -- if the relevant columns are equals, we are not putting this ProductDetail.
     * We want to skip valfrom check. this is obvious.
     * We want to skip Sequences ... these are calculated after we do the equals() check
     *
     * @param _eProdDetailBlob
     * @return boolean
     */

    public final boolean isEquivalent(eProductDetailBlob _eProdDetailBlob) {
        //COLUMN_LOOP : 
        for (int i = 0; i < getColumnCount(); i++) {
            int iColType = getColumnType(i);
            if (iColType == INT) {
                if (getIntVal(i) != _eProdDetailBlob.getIntVal(i)) {
                    //eProductUpdater.debug(getIntVal(i) + " != " + _eProdDetail.getIntVal(i));
                    return false;
                }
            } else if (iColType == STRING) {
                if (!getStringVal(i).equals(_eProdDetailBlob.getStringVal(i))) {
                    //eProductUpdater.debug("\"" + getStringVal(i) + "\"" + " !equals \"" + _eProdDetail.getStringVal(i) + "\"");
                    return false;
                }
            } else if (iColType == BLOB) {
                // skip blobs of byte[] size zero.  this means we dont need to update.
                // if the byte[] exists, that means we need to update.
                if (getBlobVal(i).size() > 0 || _eProdDetailBlob.getBlobVal(i).size() > 0) {
                    return false;
                }
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
        //sb.append("\n");
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

        String strDeleteSQL = "DELETE FROM " + ODS_SCHEMA + ".PRODUCTDETAILBLOB " + " WHERE " + " ENTERPRISE = ? AND " + " CHILDTYPE = ? AND " + " CHILDID = ? AND " + " ATTRIBUTECODE = ? AND " + " NLSID = ?";
        PreparedStatement psDelete = null;
        
        try {
            psDelete = _conODS.prepareStatement(strDeleteSQL);

            D.ebug(D.EBUG_SPEW, "TIMING productDetailBlob.delete Parms " + getStringVal(ENTERPRISE) + ":" + getStringVal(CHILDTYPE) + ":" + getIntVal(CHILDID) + ":" + getStringVal(ATTRIBUTECODE) + ":" + getIntVal(NLSID) + ":");
            D.ebug(D.EBUG_SPEW, "TIMING productDetailBlob.delete START");

            psDelete.setString(1, getStringVal(ENTERPRISE));
            psDelete.setString(2, getStringVal(CHILDTYPE));
            psDelete.setInt(3, getIntVal(CHILDID));
            psDelete.setString(4, getStringVal(ATTRIBUTECODE));
            psDelete.setInt(5, getIntVal(NLSID));

            psDelete.executeUpdate();
            psDelete.close();
            psDelete = null;
            D.ebug(D.EBUG_SPEW, "TIMING productDetailBlob.delete END");

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("cannot delete blob");
        } finally {
            if (psDelete != null) {
                psDelete.close();
                psDelete = null;
            }
        }
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

        eProductUpdater.logUpdate("     PDBLOB: " + dump(false));
        delete(conODS);

        try {
            psInsert = getPreparedStatementCollection().getPS(PreparedStatementCollection.PRODUCTDETAILBLOB_INSERT);
            if (psInsert == null) {
                String strInsertSQL = null;
                // 2) insert record if new
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
                strInsertSQL = "INSERT INTO " + ODS_SCHEMA + ".PRODUCTDETAILBLOB " + "( " + sbCols.toString() + ") " + "VALUES (" + sbQuestions.toString() + ")";
                psInsert = conODS.prepareStatement(strInsertSQL);
                getPreparedStatementCollection().putPS(PreparedStatementCollection.PRODUCTDETAILBLOB_INSERT, psInsert);
            }
            psInsert.clearParameters();
    
            for (int i = 0; i < getColumnCount(); i++) {
                if (getColumnType(i) == INT) {
                    psInsert.setInt(i + 1, getIntVal(i));
                } else if (getColumnType(i) == STRING) {
                    psInsert.setString(i + 1, getStringVal(i));
                } else if (getColumnType(i) == BLOB) {
                    COM.ibm.opicmpdh.objects.Blob blob = getBlobVal(i);
                    if (blob != null) {
                        psInsert.setBinaryStream(i + 1, blob.openBinaryStream(), blob.size());
                    }
                }
            }
            
            psInsert.executeUpdate();
            setActive(false);

        } finally {
            psInsert.close();
            psInsert = null;
        }
        
    }

    /**
     *  getBlobValue
     * 
     * @param _conODS
     * @throws java.lang.Exception
     * @return COM.ibm.opicmpdh.objects.Blob
     */
    public final COM.ibm.opicmpdh.objects.Blob getBlobValue(Connection _conODS) throws Exception {

        String strProdDetailBlobSQL = "SELECT ATTRIBUTEVALUE FROM " + ODS_SCHEMA + ".PRODUCTDETAILBLOB " + " WHERE " + eProductDetail.ENTERPRISE + " = ? AND " + eProductDetail.ENTITYTYPE + " = ? AND " + eProductDetail.ENTITYID + " = ? AND " + eProductDetail.ATTRIBUTECODE + " = ? AND " + eProductDetail.NLSID + " = ? ";
        byte[] baAttValBlob = new byte[0];
        COM.ibm.opicmpdh.objects.Blob blob = null;
        
        PreparedStatement psProdDetailBlob = null;
        ResultSet rsProdDetailBlob = null;
        
        try {
            _conODS.prepareStatement(strProdDetailBlobSQL);
            psProdDetailBlob.clearParameters();
            psProdDetailBlob.setString(1, getStringVal(ENTERPRISE));
            psProdDetailBlob.setString(2, getStringVal(CHILDTYPE));
            psProdDetailBlob.setInt(3, getIntVal(CHILDID));
            psProdDetailBlob.setString(4, getStringVal(ATTRIBUTECODE));
            psProdDetailBlob.setInt(5, getIntVal(NLSID));
            rsProdDetailBlob = psProdDetailBlob.executeQuery();

            while (rsProdDetailBlob.next()) {
                baAttValBlob = rsProdDetailBlob.getBytes(1);
            }
        } finally {
            rsProdDetailBlob.close();
            psProdDetailBlob.close();
            rsProdDetailBlob = null;
            psProdDetailBlob = null;
        }
        
        blob = new COM.ibm.opicmpdh.objects.Blob(getStringVal(ENTERPRISE), getStringVal(CHILDTYPE), getIntVal(CHILDID), getStringVal(ATTRIBUTECODE), baAttValBlob, getStringVal(ATTRIBUTEFILENAME), getIntVal(NLSID));
        putBlobVal(ATTRIBUTEVALUE, blob);
        return blob;
    }

    /**
     * hasBlobValue
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean hasBlobValue() {
        if (getBlobVal(ATTRIBUTEVALUE) == null) {
            return false;
        }
        if (getBlobVal(ATTRIBUTEVALUE).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: eProductDetailBlob.java,v 1.22 2008/01/31 21:05:17 wendy Exp $";
    }

}
