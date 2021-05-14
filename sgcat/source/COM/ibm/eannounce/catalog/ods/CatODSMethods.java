package COM.ibm.eannounce.catalog.ods;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.lang.reflect.Array;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.catalog.CatalogProperties;
import COM.ibm.eannounce.catalog.GeneralAreaMap;
import COM.ibm.eannounce.catalog.Catalog;
import COM.ibm.eannounce.catalog.GeneralAreaMapGroup;
import COM.ibm.eannounce.catalog.GeneralAreaMapItem;
import COM.ibm.eannounce.catalog.*;
import COM.ibm.eannounce.catalog.ods.CatODSServerProperties;

/**
 *  This contains all the methods used to manipulate the ODS
 *
 *
 *@author     Administrator
 *@created    February 11, 2003
 */
public abstract class CatODSMethods {
    public CatODSMethods() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * number of blob trsnasctions to let go prior to a commit
     */
    public static final int BLOB_COMMIT_COUNT = 100;

    /**
     * INDEX_NAME_LENGTH
     */
    public static final int INDEX_NAME_LENGTH = 14;

    /**
     * NEW LINE
     */
    public static final String NEW_LINE = "\n";
    /**
     * TAB
     *
     */
    public static final String TAB = "\t";

    /**
     * FIELD
     */
    protected static Hashtable c_hshGAMap = new Hashtable();

    protected Catalog m_cat = null;

    /**
     * FIELD
     */
    protected Database m_dbPDH = null;

    /**
     * FIELD
     */
    protected Connection m_conODS = null;
    /**
     * FIELD
     */
    protected Connection m_conPDH = null;
    /**
     * FIELD
     */
    protected Profile m_prof = null;
    /**
     * FIELD
     */
    protected String m_strNow = null;
    /**
     * FIELD
     */
    protected String m_strLastRun = null;
    /**
     * FIELD
     */
    protected String m_strForever = null;
    /**
     * FIELD
     */
    protected String m_strEpoch = null;

    /**
     * FIELD
     */
    protected DateFormat m_df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.JAPAN);
    /**
     * FIELD
     */
    protected String m_strurlODS = CatODSServerProperties.getDatabaseURL();
    /**
     * FIELD
     */
    protected String m_struidODS = CatODSServerProperties.getDatabaseUser();
    /**
     * FIELD
     */
    protected String m_strpwdODS = CatODSServerProperties.getDatabasePassword();
    /**
     * FIELD
     */
    protected DatabaseMetaData m_dbMetaODS = null;
    /**
     * FIELD
     */
    protected String m_strODSSchema = CatODSServerProperties.getDatabaseSchema();
    /**
     * FIELD
     */
    protected String m_strTableSpace = CatODSServerProperties.getDefaultTableSpace(m_strODSSchema);
    /**
     * FIELD
     */
    protected String m_strIndexSpace = CatODSServerProperties.getDefaultIndexSpace(m_strODSSchema);
    /**
     * FIELD
     */
    protected int m_iChuckSize = CatODSServerProperties.getChunkSize();
    /**
     * FIELD
     */
    protected boolean m_bRebuild = false;
    /**
     * FIELD
     */
    protected boolean m_bInit = false;
    /**
     * FIELD
     */
    protected boolean m_bInitAll = false;
    /**
     * FIELD
     */
    protected boolean m_bInitOne = false;
    /**
     * FIELD
     */
    protected boolean m_bNetChange = false;
    /**
     * FIELD
     */
    protected boolean m_bDropAll = false;
    /**
     * FIELD
     */
    protected boolean m_bMultiFlag = false;
    /**
     * FIELD
     */
    protected boolean m_bSoftwareFlag = false;
    /**
     * FIELD
     */
    protected boolean m_bMetaDesc = false;
    /**
     * FIELD
     */
    protected boolean m_bRestart = false;
    /**
     * FIELD
     */
    protected boolean m_bRebuildBlob = false;
    /**
     * FIELD
     */
    protected boolean m_bResetMetaAttributes = false;

    /**
     * FIELD
     */
    protected boolean m_bRebuildDeleteLog = false;
    /**
     * FIELD
     */
    protected String m_strTableName = "";

    /**
     * FIELD!
     */
    protected Vector m_vctRollupAttrs = new Vector();
    /**
     * FIELD!
     */
    protected Vector m_vctRootRollupAttrs = new Vector();
    /**
     * FIELD!
     */
    protected Hashtable m_HashMultiAttEntityGroups = new Hashtable();

    /**
     *  Sets the oDSConnection attribute of the CatODSMethods object
     */

    protected int iLevel = 1;
    protected void setODSConnection() {
        try {
            D.ebug(D.EBUG_DETAIL, "Connecting to ODS");
            m_conODS = m_dbPDH.getODSConnection();
            m_conODS.setAutoCommit(false);
        }
        catch (Exception ex) {
            D.ebug(D.EBUG_ERR, "odsConnectionError" + ex.getMessage());
            System.exit( -1);
        }

    }

    protected void setConnection() {
        try {
            m_dbPDH.connect();
        }
        catch (MiddlewareException ex) {
            D.ebug(D.EBUG_ERR, "setConnectionError" + ex.getMessage());
            System.exit( -1);
        }
        catch (SQLException ex) {
            D.ebug(D.EBUG_ERR, "setConnectionError" + ex.getMessage());
            System.exit( -1);
        }
        setPDHConnection();
        setODSConnection();
    }

    /**
     *  Sets the oDSConnection attribute of the CatODSMethods object
     */
    protected void setPDHConnection() {
        try {
            D.ebug(D.EBUG_DETAIL, "Connecting to PDH");
            m_conPDH = m_dbPDH.getPDHConnection();
            m_conPDH.setAutoCommit(true);
        }
        catch (Exception ex) {
            D.ebug(D.EBUG_ERR, "pdhConnectionError" + ex.getMessage());
            System.exit( -1);
        }
    }

    /**
     *  Gets the Connection attribute of the CatODSMethods object
     *
     *@return    The Connection object
     */
    protected Connection getODSConnection() {
        return m_conODS;
    }

    protected void setCatalog(Catalog _cat) {
        m_cat = _cat;
    }

    protected Catalog getCatalog() {
        return m_cat;
    }

    /**
     *  Gets the databaseObject attribute of the CatODSMethods object
     *
     *@return    The databaseObject value
     */
    protected Database getDatabaseObject() {
        return m_dbPDH;
    }

    /**
     *  Constructor for the setDatabaseObject object
     *
     * @param _dbPDH
     */
    protected void setDatabaseObject(Database _dbPDH) {
        m_dbPDH = _dbPDH;
    }

    /**
     *  Sets the profile attribute of the CatODSMethods object
     *
     *@exception  MiddlewareException
     *@exception  SQLException
     */
    protected void setProfile() throws MiddlewareException, SQLException {
        m_prof = new Profile(m_dbPDH, CatODSServerProperties.getProfileEnterprise(),
                             CatODSServerProperties.getCatDBODSProfileOPWGID());
    }

    /**
     *  Gets the profile attribute of the CatODSMethods object
     *
     *@return    The profile value
     */
    protected Profile getProfile() {
        return m_prof;
    }

    /**
     *  Sets the profile,now and forever  attributes of the CatODSMethods object
     */
    protected void setDateTimeVars() {
        DatePackage dpNow = new DatePackage(m_dbPDH);
        m_prof.setValOn(dpNow.getEndOfDay());
        m_prof.setEffOn(dpNow.getEndOfDay());
        m_strNow = dpNow.getNow();
        m_strForever = dpNow.getForever();
    }

    /**
     * Rebuilds the Blob Table
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected void rebuildBlobTable() throws SQLException, MiddlewareException {

        int count = 0;
        int errcount = 0;
        int reploop = 0;

        String strTableName = m_strODSSchema + ".BLOB";

        String strDropSQL = "DROP TABLE " + strTableName;
        String strTableSQL = "CREATE TABLE " + strTableName + " (" + "VALFROM TIMESTAMP NOT NULL, " +
            "ENTITYTYPE CHAR(32) NOT NULL, " + "ENTITYID INTEGER NOT NULL, " + "ATTRIBUTECODE CHAR(32) NOT NULL, " +
            "NLSID INT NOT NULL, " + "BLOBEXTENSION CHAR(32) NOT NULL," + "ATTRIBUTEVALUE BLOB(50M) NOT NULL " + ")";

        String strIndexSQL = " CREATE UNIQUE INDEX " + strTableName + "_PK ON " + strTableName +
            "(ENTITYID, ENTITYTYPE,ATTRIBUTECODE, NLSID)";
        String strInsertSQL = "INSERT INTO " + strTableName + "( " + "VALFROM, " + "ENTITYTYPE, " + "ENTITYID, " +
            "ATTRIBUTECODE, " + "NLSID, " + "BLOBEXTENSION, " + "ATTRIBUTEVALUE " + ") " + "VALUES (?,?,?,?,?,?,?)";

        String strOuterSelectSQL = "SELECT DISTINCT " + "B.ENTITYTYPE, " + "B.ENTITYID, " + "B.ATTRIBUTECODE, " + "B.NLSID " +
            "FROM OPICM.BLOB B" + " WHERE " + "B.ENTERPRISE = '" + m_prof.getEnterprise() + "' AND " + "B.ValFrom <= '" + m_strNow +
            "' AND '" + m_strNow + "' < B.ValTo AND " + "B.EffFrom <= '" + m_strNow + "' AND '" + m_strNow + "' < B.EffTo  " + " AND ENTITYTYPE NOT IN ('OPWG','DGENTITY', 'XLATEGRP','METAXLATEGRP') AND ENTITYID > 0 AND ATTRIBUTECODE NOT IN ('ETSOBJECT','SERIALHISTORY','PRUSERPREFERENCES','CHQETSMOBJECT') FOR READ ONLY";

        String strInnerSelectSQL = "SELECT " + "B.BLOBEXTENSION, " + "B.ATTRIBUTEVALUE " + "FROM OPICM.BLOB B " + "WHERE " +
            "B.ENTERPRISE = '" + m_prof.getEnterprise() + "' AND " +
            "B.EntityType = ? AND B.EntityID = ? AND B.ATTRIBUTECODE = ? AND B.NLSID = ? AND " + "B.ValFrom <= '" + m_strNow +
            "' AND '" + m_strNow + "' < B.ValTo AND " + "B.EffFrom <= '" + m_strNow + "' AND '" + m_strNow + "' < B.EffTo";

        String strTableSpace = CatODSServerProperties.getTableSpace(m_strODSSchema, "BLOB");
        String strIndexSpace = CatODSServerProperties.getIndexSpace(m_strODSSchema, "BLOB");

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        Statement ddlstmt = null;
        PreparedStatement insertrecord = null;
        PreparedStatement getBlob1 = null;
        PreparedStatement getBlob2 = null;

        try {

            strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
            strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
            strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
                (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

            ddlstmt = m_conODS.createStatement();

            // Lets try to drop the table...

            try {
                D.ebug(D.EBUG_INFO, "rebuildBlob:dropping Table:" + strTableName);
                ddlstmt.executeUpdate(strDropSQL);
                m_conODS.commit();
            }
            catch (SQLException ex) {
                D.ebug(D.EBUG_INFO, "rebuildBlob:Skipping Table Drop:" + strTableName + ":" + ex.getMessage());
            }

            D.ebug(D.EBUG_INFO, "rebuildBlob:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);
            m_conODS.commit();

            // Now the index
            D.ebug(D.EBUG_INFO, "rebuildBlob:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);
            m_conODS.commit();

            D.ebug(D.EBUG_INFO, "rebuildBlob:Start.");

        }
        finally {
            ddlstmt.close();
        }

        try {
            D.ebug(D.EBUG_INFO, "rebuildBlob:OPICM Blob OuterQuery Begins.");
            getBlob1 = m_conPDH.prepareStatement(strOuterSelectSQL);
            rs = getBlob1.executeQuery();
            D.ebug(D.EBUG_INFO, "rebuildBlob:OPICM Blob OuterQuery Ends");
            rdrs = new ReturnDataResultSet(rs);

        }
        finally {

            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (getBlob1 != null) {
                getBlob1.close();
                getBlob1 = null;
            }
            m_dbPDH.commit();
            m_dbPDH.freeStatement();
        }

        insertrecord = m_conODS.prepareStatement(strInsertSQL);
        getBlob2 = m_conPDH.prepareStatement(strInnerSelectSQL);

        try {

            for (int i = 0; i < rdrs.size(); i++) {

                String strEntityType = "empty";
                int iEntityID = 0;
                String strAttributeCode = "empty";
                int iNLSID = 0;
                String strBlobExtension = "empty";
                int iblobsize = 0;

                try {

                    strEntityType = rdrs.getColumn(i, 0);
                    iEntityID = rdrs.getColumnInt(i, 1);
                    strAttributeCode = rdrs.getColumn(i, 2);
                    iNLSID = rdrs.getColumnInt(i, 3);

                    getBlob2.setString(1, strEntityType);
                    getBlob2.setInt(2, iEntityID);
                    getBlob2.setString(3, strAttributeCode);
                    getBlob2.setInt(4, iNLSID);

                    rs = getBlob2.executeQuery();
                    if (rs.next()) {
                        byte[] mybytes = null;
                        strBlobExtension = rs.getString(1);
                        mybytes = rs.getBytes(2);
                        iblobsize = mybytes.length;

                        D.ebug(D.EBUG_INFO,
                               "rebuildBlob:about to insert:  " + NEW_LINE + TAB + ":ET:" + strEntityType + NEW_LINE + TAB +
                               ":EID:" + iEntityID + NEW_LINE + TAB + ":AC:" + strAttributeCode + NEW_LINE + TAB + ":NLS:" + iNLSID +
                               NEW_LINE + TAB + ":BEX:" + strBlobExtension + NEW_LINE + TAB + ":BSZ:" + iblobsize + NEW_LINE + TAB +
                               ":COUNT:" + count);

                        insertrecord.clearParameters();
                        insertrecord.setString(1, m_strNow); // Valfrom
                        insertrecord.setString(2, strEntityType); // EntityType
                        insertrecord.setInt(3, iEntityID); // Entityid
                        insertrecord.setString(4, strAttributeCode); // AttributeCode
                        insertrecord.setInt(5, iNLSID); // NLSID
                        insertrecord.setString(6, strBlobExtension); // NLSID
                        insertrecord.setBytes(7, mybytes); //
                        insertrecord.executeUpdate();
                        count++;
                        reploop++;
                        if (reploop == BLOB_COMMIT_COUNT) {
                            D.ebug(D.EBUG_INFO, "rebuildBlob:commit on:" + count);
                            m_conODS.commit();
                            reploop = 0;
                        }
                    }

                }
                catch (SQLException ex) {
                    errcount++;
                    D.ebug(D.EBUG_ERR,
                           "rebuildBlob:SQL Panic, Skipping insert.  " + NEW_LINE + TAB + ":ET:" + strEntityType + NEW_LINE + TAB +
                           ":EID:" + iEntityID + NEW_LINE + TAB + ":AC:" + strAttributeCode + NEW_LINE + TAB + ":NLS:" + iNLSID +
                           NEW_LINE + TAB + ":BEX:" + strBlobExtension + NEW_LINE + TAB + ":BSZ:" + iblobsize + NEW_LINE + TAB +
                           ":COUNT:" + count + NEW_LINE + TAB + ":MESS" + ex.getMessage());
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    m_dbPDH.commit();
                    m_dbPDH.freeStatement();
                }
            }

        }
        finally {
            getBlob2.close();
            insertrecord.close();
            m_conODS.commit();
            m_conPDH.commit();
        }

        D.ebug(D.EBUG_INFO, "rebuildBlob:Finished.  # of Recs Processed:" + count + "  # number of errors:" + errcount);

    }

    /**
     * Rebuilds the Blob Table
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected void updateBlobTable() throws SQLException, MiddlewareException {

        String strTableName = m_strODSSchema + ".BLOB";

        String strInsertSQL = "INSERT INTO " + strTableName + "( " + "VALFROM, " + "ENTITYTYPE, " + "ENTITYID, " +
            "ATTRIBUTECODE, " + "NLSID, " + "BLOBEXTENSION, " + "ATTRIBUTEVALUE " + ") " + "VALUES (?,?,?,?,?,?,?)";

        String strSelectSQL = "Select EntityID FROM " + strTableName + " WHERE " + "ENTITYTYPE = ? AND " + "ENTITYID = ? AND " +
            "ATTRIBUTECODE = ? AND " + "NLSID = ? ";
        String strUpdateSQL = "UPDATE " + strTableName + " SET VALFROM =  ?, BLOBEXTENSION = ?, ATTRIBUTEVALUE = ? " + " WHERE " +
            "ENTITYTYPE = ? AND " + "ENTITYID = ? AND " + "ATTRIBUTECODE = ? AND " + "NLSID = ? ";
        String strDeleteSQL = "Delete FROM  " + strTableName + " WHERE " + "ENTITYTYPE = ? AND " + "ENTITYID = ? AND " +
            "ATTRIBUTECODE = ? AND " + "NLSID = ? ";

        String strSelectPDHSQL = "SELECT  " + "B.ENTITYTYPE, " + "B.ENTITYID, " + "B.ATTRIBUTECODE, " + "B.NLSID, " + "B.EffTo, " +
            "B.BlobExtension, " + "B.AttributeValue " + "FROM OPICM.BLOBX B " + " WHERE " + "B.ENTERPRISE = '" +
            m_prof.getEnterprise() + "' AND " + "B.ValFrom >= '" + m_strLastRun + "' AND " +
            "B.ValTo = '9999-12-31-00.00.00.000000' AND " + "B.ENTITYTYPE <> 'OPWG' AND B.ENTITYID > 0 AND B.ATTRIBUTECODE NOT IN ('ETSOBJECT','SERIALHISTORY','PRUSERPREFERENCES','CHQETSMOBJECT') FOR READ ONLY";

        int count = 0;
        int errcount = 0;
        int reploop = 0;

        String strEntityType = "empty";
        int iEntityID = 0;
        String strAttributeCode = "empty";
        int iNLSID = 0;
        String strBlobExtension = "empty";
        int iblobsize = 0;
        byte[] mybytes = null;
        boolean bActive = false;

        PreparedStatement insertrecord = null;
        PreparedStatement selectrecord = null;
        PreparedStatement updaterecord = null;
        PreparedStatement deleterecord = null;
        PreparedStatement getBlob1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;

        try {
            D.ebug(D.EBUG_INFO, "rebuildBlob:Start.");

            getBlob1 = m_conPDH.prepareStatement(strSelectPDHSQL);
            deleterecord = m_conODS.prepareStatement(strDeleteSQL);
            insertrecord = m_conODS.prepareStatement(strInsertSQL);
            selectrecord = m_conODS.prepareStatement(strSelectSQL);
            updaterecord = m_conODS.prepareStatement(strUpdateSQL);

            D.ebug(D.EBUG_INFO, "rebuildBlob:OPICM Blob Query Begins.");
            rs = getBlob1.executeQuery();
            D.ebug(D.EBUG_INFO, "rebuildBlob:OPICM Blob Query Ends");

            while (rs.next()) {
                strEntityType = rs.getString(1);
                iEntityID = rs.getInt(2);
                strAttributeCode = rs.getString(3);
                iNLSID = rs.getInt(4);
                bActive = rs.getString(5).startsWith("9999-12-31");
                strBlobExtension = rs.getString(6);
                mybytes = rs.getBytes(7);
                iblobsize = mybytes.length;
                D.ebug(D.EBUG_INFO,
                       "updateBlob:about to insert:  " + NEW_LINE + TAB + ":ET:" + strEntityType + NEW_LINE + TAB + ":EID:" +
                       iEntityID + NEW_LINE + TAB + ":AC:" + strAttributeCode + NEW_LINE + TAB + ":NLS:" + iNLSID + NEW_LINE + TAB +
                       ":BEX:" + strBlobExtension + NEW_LINE + TAB + ":BSZ:" + iblobsize + NEW_LINE + TAB + ":Active:" + bActive +
                       NEW_LINE + TAB + ":COUNT:" + count);

                if (!bActive) {
                    deleterecord.clearParameters();
                    deleterecord.setString(1, strEntityType);
                    deleterecord.setInt(2, iEntityID);
                    deleterecord.setString(3, strAttributeCode);
                    deleterecord.setInt(4, iNLSID);
                    deleterecord.executeUpdate();

                }
                else {

                    try {

                        selectrecord.clearParameters();
                        selectrecord.setString(1, strEntityType);
                        selectrecord.setInt(2, iEntityID);
                        selectrecord.setString(3, strAttributeCode);
                        selectrecord.setInt(4, iNLSID);
                        rs1 = selectrecord.executeQuery();
                        if (rs1.next()) {
                            updaterecord.clearParameters();
                            updaterecord.setString(1, m_strNow);
                            updaterecord.setString(2, strBlobExtension);
                            updaterecord.setBytes(3, mybytes);
                            updaterecord.setString(4, strEntityType);
                            updaterecord.setInt(5, iEntityID);
                            updaterecord.setString(6, strAttributeCode);
                            updaterecord.setInt(7, iNLSID);
                            updaterecord.executeUpdate();
                        }
                        else {
                            insertrecord.clearParameters();
                            insertrecord.setString(1, m_strNow);
                            insertrecord.setString(2, strEntityType);
                            insertrecord.setInt(3, iEntityID);
                            insertrecord.setString(4, strAttributeCode);
                            insertrecord.setInt(5, iNLSID);
                            insertrecord.setString(6, strBlobExtension);
                            insertrecord.setBytes(7, mybytes);
                            insertrecord.executeUpdate();
                        }
                    }
                    finally {
                        if (rs1 != null) {
                            rs1.close();
                            rs1 = null;
                        }
                    }
                }

                count++;
                reploop++;
                if (reploop == BLOB_COMMIT_COUNT) {
                    D.ebug(D.EBUG_INFO, "updateBlob:commit on:" + count);
                    m_conODS.commit();
                    reploop = 0;
                }
            }
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }

            getBlob1.close();
            insertrecord.close();
            updaterecord.close();
            deleterecord.close();
            selectrecord.close();
            m_conODS.commit();
            m_dbPDH.commit();
            m_dbPDH.freeStatement();
        }

        D.ebug(D.EBUG_INFO, "updateBlob:Finished.  # of Recs Processed:" + count + "  # number of errors:" + errcount);

    }

    /**
     *  This rebuiilds the Metadescription Table
     *
     *@exception  SQLException
     *@exception  MiddlewareException
     */
    protected void rebuildMetaDescription() throws SQLException, MiddlewareException {

        String strTableName = m_strODSSchema + ".METAFLAGTABLE";
        NLSItem nlsi = m_prof.getReadLanguage();

        String strDropSQL = "DROP TABLE " + strTableName;
        String strTableSQL = "CREATE TABLE " + strTableName + " (" + "VALFROM TIMESTAMP NOT NULL, " +
            "ATTRIBUTECODE CHAR(32) NOT NULL, " + "NLSID INT NOT NULL, " + "ATTRIBUTEVALUE CHAR(32) NOT NULL, " +
            "SHORTDESCRIPTION CHAR(64), " + "LONGDESCRIPTION CHAR(128) " + ")";

        String strIndexSQL = " CREATE UNIQUE INDEX " + strTableName + "_PK ON " + strTableName +
            "(ATTRIBUTECODE, ATTRIBUTEVALUE, NLSID)";
        String strInsertSQL = " INSERT INTO " + strTableName + " ( " + "VALFROM, " + "ATTRIBUTECODE, " + "NLSID, " +
            "ATTRIBUTEVALUE, " + "SHORTDESCRIPTION, " + "LONGDESCRIPTION " + "  ) " + "VALUES (?,?,?,?,?,?)";

        String strTableSpace = CatODSServerProperties.getTableSpace(m_strODSSchema, "FLAG");
        String strIndexSpace = CatODSServerProperties.getIndexSpace(m_strODSSchema, "FLAG");

        String strAttributeCode = null;
        String strAttributeType = null;

        Statement ddlstmt = null;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        PreparedStatement psFlag = null;

        try {

            ddlstmt = m_conODS.createStatement();

            strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
            strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
            strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
                (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

            // Let us drop the table first
            try {
                D.ebug(D.EBUG_INFO, "rebuildMetaDesc:dropping Table:" + strTableName);
                ddlstmt.executeUpdate(strDropSQL);
                m_conODS.commit();
            }
            catch (SQLException ex) {
                D.ebug(D.EBUG_INFO, "rebuildMetaDesc:Skipping Table Drop:" + strTableName);
                ex.printStackTrace();
            }

            D.ebug(D.EBUG_INFO, "rebuildMetaDesc:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);

            // Now the index
            D.ebug(D.EBUG_INFO, "rebuildMetaDesc:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);

        }
        finally {

            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
            }
            m_conODS.commit();
        }

        //
        // Lets get some meta stuff
        //
        try {
            rs = m_dbPDH.callGBL5714(new ReturnStatus( -1), m_prof.getEnterprise(), m_prof.getRoleCode());
            rdrs = new ReturnDataResultSet(rs);

        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }

            m_dbPDH.freeStatement();
            m_dbPDH.isPending();
            m_dbPDH.commit();
        }

        try {

            EANMetaFlagAttribute mfa = null;
            psFlag = m_conODS.prepareStatement(strInsertSQL);

            for (int ii = 0; ii < rdrs.size(); ii++) {
                strAttributeCode = rdrs.getColumn(ii, 0);
                strAttributeType = rdrs.getColumn(ii, 1);
                mfa = new MetaMultiFlagAttribute(null, m_dbPDH, m_prof, strAttributeCode, true);

                D.ebug(D.EBUG_INFO, "rebuildMetaDesc:Creating Rows for :" + strAttributeCode + ":" + strAttributeType);

                for (int ix = 0; ix < m_prof.getReadLanguages().size(); ix++) {
                    m_prof.setReadLanguage(ix);
                    for (int iy = 0; iy < mfa.getMetaFlagCount(); iy++) {
                        MetaFlag mf = mfa.getMetaFlag(iy);
                        String strFlagCode = mf.getFlagCode();
                        String strShortDescription = mf.getShortDescription();
                        String strLongDescription = mf.getLongDescription();
                        int iNLSID = mf.getNLSID();
                        psFlag.setString(1, m_strNow);
                        psFlag.setString(2, strAttributeCode);
                        psFlag.setInt(3, iNLSID);
                        psFlag.setString(4, strFlagCode);
                        psFlag.setString(5, strShortDescription);
                        psFlag.setString(6, strLongDescription);
                        psFlag.execute();
                        D.ebug(D.EBUG_INFO,
                               "rebuildMetaDesc:inserting record:" + strAttributeCode + ":" + iNLSID + ":" + strFlagCode + ":" +
                               strShortDescription + ":" + strLongDescription);
                    }
                }
                m_conODS.commit();
            }
        }
        catch (NullPointerException ne) {
            D.ebug(D.EBUG_INFO, "rebuildMetaDesc:Error while creating metadescription rows for Attribute:" + strAttributeCode);
            throw ne;
        }
        finally {
            if (psFlag != null) {
                psFlag.close();
                psFlag = null;
            }
        }

        //
        // Set it back
        //
        m_prof.setReadLanguage(nlsi);

    }

    /**
     *  Rebuilds the Flag Table
     *
     *@exception  SQLException
     */
    protected void resetMultiFlagTable() throws SQLException {

        String strTableName = m_strODSSchema + ".FLAG";
        String strDropSQL = "DROP TABLE " + strTableName;
        String strTableSQL = "CREATE TABLE " + strTableName + " (" + "VALFROM TIMESTAMP NOT NULL, " +
            "ENTITYTYPE CHAR(16) NOT NULL, " + "ENTITYID INTEGER NOT NULL, " + "NLSID INTEGER NOT NULL, " +
            "ATTRIBUTECODE CHAR(32) NOT NULL, " + "FLAGCODE CHAR(16) NOT NULL, " + "SFVALUE INT NOT NULL, " +
            "FLAGDESCRIPTION VARCHAR(128) NOT NULL " + ")";

        String strTableSpace = CatODSServerProperties.getTableSpace(m_strODSSchema, "FLAG");
        String strIndexSpace = CatODSServerProperties.getIndexSpace(m_strODSSchema, "FLAG");
        String strIndexSQL = " CREATE UNIQUE INDEX " + strTableName + "_PK ON " + strTableName +
            "(ENTITYID, ENTITYTYPE, NLSID, ATTRIBUTECODE, FLAGCODE)";

        Statement ddlstmt = null;

        try {

            strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
            strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
            strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
                (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

            ddlstmt = m_conODS.createStatement();
            try {
                D.ebug(D.EBUG_INFO, "resetMultiFlagTable:dropping Table:" + strTableName);
                ddlstmt.executeUpdate(strDropSQL);
                m_conODS.commit();
            }
            catch (SQLException ex) {
                D.ebug(D.EBUG_INFO, "resetMultiFlagTable:Skipping Table Drop:" + strTableName + ":" + ex.getMessage());
            }

            D.ebug(D.EBUG_INFO, "resetMultiFlagTable:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);

            // Now the index
            D.ebug(D.EBUG_INFO, "resetMultiFlagTable:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);
        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
                m_conODS.commit();
            }
        }
    }

    /**
     *  Drops all the tables in the database schema specified
     *
     *@exception  SQLException
     */
    protected void dropAllTables() throws SQLException {

        //
        // Here is a hashtable of protected tables that should not be dropped
        //
        Hashtable hsh1 = new Hashtable();

        String strTableListSQL = "Select tabname from syscat.tables where tabschema = '" + m_strODSSchema + "' AND TYPE = 'T'";

        String strTableFilterSQL = "Select tablename from "+m_strODSSchema+".catdb_tables where tableschema='" + m_strODSSchema +
            "' and ods_purge='N'";
        Statement stat = null;
        ResultSet rs = null;
        Statement ddlstmt = null;
        ReturnDataResultSet rdrs = null;

        //
        //Catalog Base tables that do not need to be dropped
        // we get the list from gbli.catdb_tables
        //

        D.ebug(D.EBUG_INFO, "dropAllTables:Generating Table filter:" + strTableFilterSQL);
        try {
            stat = m_conPDH.createStatement();
            rs = stat.executeQuery(strTableFilterSQL);
            rdrs = new ReturnDataResultSet(rs);
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strTableName = rdrs.getColumn(ii, 0);
                if (!hsh1.containsKey(strTableName)) {
                    hsh1.put(strTableName, "Y");
                }
            }

        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stat != null) {
                stat.close();
                stat = null;
            }
            m_conPDH.commit();
        }

        D.ebug(D.EBUG_INFO, "dropAllTables:Generating List:" + strTableListSQL);

        try {
            stat = m_conODS.createStatement();
            rs = stat.executeQuery(strTableListSQL);
            rdrs = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stat != null) {
                stat.close();
                stat = null;
            }
            m_conODS.commit();
        }

        // O.K.  Lets get all the known tables for a schema and lets remove them!

        try {
            ddlstmt = m_conODS.createStatement();
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strTableName = rdrs.getColumn(ii, 0);
                String strDropSQL = "DROP TABLE " + m_strODSSchema + "." + strTableName;
                // Let us drop the table first
                try {
                    D.ebug(D.EBUG_INFO, "dropAllTables:dropping Table:" + strDropSQL);
                    if (hsh1.containsKey(strTableName)) {
                        D.ebug(D.EBUG_INFO, "dropAllTables:Skipping Table Drop:" + strTableName);
                    }
                    else {
                        ddlstmt.executeUpdate(strDropSQL);
                    }
                    m_conODS.commit();
                }
                catch (SQLException ex) {
                    D.ebug(D.EBUG_INFO, "dropAllTables:Skipping Table Drop:" + strDropSQL + ":" + ex.getMessage());
                }
            }
        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
            }
        }
    }

    /**
     *  This will drop and recreate the ods table
     *
     *@param  _eg               Entity Group of the entity to reset
     *@exception  SQLException
     */
    public void resetODSTable(EntityGroup _eg) throws SQLException {

        Statement ddlstmt = null;
        String strEntityType = _eg.getEntityType();
        String strTableName = m_strODSSchema + "." + strEntityType;

        String strDropSQL = "DROP TABLE " + strTableName;
        String strIndexSQL = " CREATE UNIQUE INDEX " + m_strODSSchema + "." +
            (strEntityType.length() > INDEX_NAME_LENGTH ? strEntityType.substring(0, INDEX_NAME_LENGTH) : strEntityType) +
            "_PK ON " + strTableName + "(ENTITYID, NLSID, VALFROM";
        String strIndexR1SQL = " CREATE UNIQUE INDEX " + m_strODSSchema + "." +
            (strEntityType.length() > INDEX_NAME_LENGTH ? strEntityType.substring(0, INDEX_NAME_LENGTH) : strEntityType) +
            "_IX1 ON " + strTableName + "(ID1,ID2,EntityID,NLSID)";
        String strIndexR2SQL = " CREATE UNIQUE INDEX " + m_strODSSchema + "." +
            (strEntityType.length() > INDEX_NAME_LENGTH ? strEntityType.substring(0, INDEX_NAME_LENGTH) : strEntityType) +
            "_IX2 ON " + strTableName + "(ID2,ID1,EntityID,NLSID)";
        String strAlterSQL = "";

        String strTableSQL = (_eg.isRelator() ? "CREATE TABLE " + strTableName + "(ENTITYID INT NOT NULL, NLSID INT NOT NULL, ID1 INT, ID2 INT, VALFROM TIMESTAMP NOT NULL, VALTO TIMESTAMP NOT NULL, ISACTIVE INT NOT NULL, COUNTRYCODE CHAR(02), LANGUAGECODE CHAR(02), COUNTRYLIST CHAR(08) " :
                              "CREATE TABLE " + strTableName + "(ENTITYID INT NOT NULL, NLSID INT NOT NULL, VALFROM TIMESTAMP NOT NULL, VALTO TIMESTAMP NOT NULL, ISACTIVE INT NOT NULL, COUNTRYCODE CHAR(02), LANGUAGECODE CHAR(02), COUNTRYLIST CHAR(08) ");

        String strTableSpace = CatODSServerProperties.getTableSpace(m_strODSSchema, strEntityType);
        String strIndexSpace = CatODSServerProperties.getIndexSpace(m_strODSSchema, strEntityType);

        String strFKey = CatODSServerProperties.getFKeyMap(m_strODSSchema, strEntityType);
        boolean bFKEY = (!strFKey.equals("n"));
        boolean bPubFlag = CatODSServerProperties.hasPublishFlag(m_strODSSchema, strEntityType);

        // Let us drop the table first
        try {
            ddlstmt = m_conODS.createStatement();
            D.ebug(D.EBUG_INFO, "resetODSTable:dropping Table:" + strTableName);
            ddlstmt.executeUpdate(strDropSQL);
        }
        catch (SQLException ex) {
            D.ebug(D.EBUG_INFO, "resetODSTable:Skipping Table Drop:" + strTableName + ":" + ex.getMessage());
        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
            }
            m_conODS.commit();
        }

        if (bFKEY) {
            D.ebug(D.EBUG_INFO, "resetODSTable:Adding FKey to table:" + strTableName + ", " + strFKey);
            strTableSQL = strTableSQL + ", " + strFKey + " INT ";
        }

        //Get the CATNAV attributes..they have to be a column
        MultiRowAttrGroup cng = new MultiRowAttrGroup(getCatalog(), strEntityType);

        //
        // ALL Type 'A' and 'B' attributes are skipped
        // A = ODS never needs
        // B = will be gotten from its own table.
        //
        // First go after all non lob attributes
        for (int ii = 0; ii < _eg.getMetaAttributeCount(); ii++) {
            EANMetaAttribute ma = _eg.getMetaAttribute(ii);
            MultiRowAttrItem cni = cng.getItem(ma.getAttributeCode());
            D.ebug(D.EBUG_INFO, "resetODSTable:Attribute:" + ma.getAttributeCode() + ":" + ma.getAttributeType());

            if (includeColumn(ma) && cni == null) {
                if (! (ma.getAttributeType().equals("F") || ma.getAttributeType().equals("X") || ma.getAttributeType().equals("L"))) {
                    strAlterSQL = strAlterSQL + ", " + ma.getAttributeCode() + " " + getDBFieldType(ma);
                    if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                        strAlterSQL = strAlterSQL + ", " + ma.getAttributeCode() + "_FC" + " CHAR(16)";
                    }
                }
            }
        }

        for (int i = 0; i < cng.getItemCount(); i++) {
            MultiRowAttrItem cni = cng.getItem(i);
            EANMetaAttribute ma = _eg.getMetaAttribute(cni.getColumnKey());
            if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S") || ma.getAttributeType().equals("F")) {
                strAlterSQL = strAlterSQL + ", " + cni.getColumnKey() + " CHAR(040)" + ", " + ma.getAttributeCode() + "_FC" + " CHAR(16)";
                //strAlterSQL = strAlterSQL + ", " + ma.getAttributeCode() + " " + getDBFieldType(ma) + ", " + ma.getAttributeCode() + "_FC" + " CHAR(16)";
                strIndexSQL = strIndexSQL + "," + cni.getColumnKey() + " ," + ma.getAttributeCode() + "_FC";

            }
            else {
                strAlterSQL = strAlterSQL + ", " + ma.getAttributeCode() + " " + getDBFieldType(ma);
                strIndexSQL = strIndexSQL + "," + cni.getColumnKey();
            }
        }
        strIndexSQL = strIndexSQL + ")";

        // now go after lob attributes (X,F,L)
        // First go after all non lob attributes
        for (int ii = 0; ii < _eg.getMetaAttributeCount(); ii++) {
            EANMetaAttribute ma = _eg.getMetaAttribute(ii);
            if (includeColumn(ma)) {
                if (ma.getAttributeType().equals("F") || ma.getAttributeType().equals("X") || ma.getAttributeType().equals("L")) {
                    strAlterSQL = strAlterSQL + ", " + ma.getAttributeCode() + " " + getDBFieldType(ma);
                }
            }
        }

        // O.K. Put it all together and go for the create base
        strTableSQL = strTableSQL + strAlterSQL + ")";

        // Now .. Lets make the tableSpace, and Ispace Thing
        strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
        strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
        strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
            (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

        try {
            ddlstmt = m_conODS.createStatement();
            D.ebug(D.EBUG_INFO, "resetODSTable:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);

            // Now the index
            D.ebug(D.EBUG_INFO, "resetODSTable:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);

            if (_eg.isRelator()) {
                ddlstmt.executeUpdate(strIndexR1SQL);
                ddlstmt.executeUpdate(strIndexR2SQL);
            }

            if (bFKEY) {
                String strIndexFKSQL = " CREATE UNIQUE INDEX " + m_strODSSchema + "." +
                    (strEntityType.length() > INDEX_NAME_LENGTH ? strEntityType.substring(0, INDEX_NAME_LENGTH) : strEntityType) +
                    "_FK ON " + strTableName + "(" + strFKey + ", ENTITYID, NLSID, VALFROM)";

                ddlstmt.executeUpdate(strIndexFKSQL);
            }
        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
            }
            m_conODS.commit();
        }
    }

    /**
     *  Gets the ODS Column substitute of the Attribute given a EANMetaAttribute
     *  object
     *
     *@param  _ma  The EANMetaAttribute
     *@return      The ODS Column Type
     */
    protected String getDBFieldType(EANMetaAttribute _ma) {

        //
        //  Here we are dealing with a text variant
        //

        m_dbPDH.debug(D.EBUG_SPEW,"getDBFieldType:Processing Attribute :"+_ma.getAttributeCode()+" of TYPE:"+_ma.getAttributeType()+":LENGTH:"+_ma.getOdsLength());

        String strODSLen = (_ma.getOdsLength() > 0 ? "0" + _ma.getOdsLength() : "0" + CatODSServerProperties.getVarCharColumnLength());
        int iODSLength = _ma.getOdsLength() > 0 ? _ma.getOdsLength() : CatODSServerProperties.getAttrColLength(_ma.getAttributeCode());
        if (_ma.getAttributeType().equals("T") || _ma.getAttributeType().equals("I")) {
            if (_ma.isDate()) {
                return "DATE";
            }
            else if (_ma.isTime()) {
                return "TIME";
            }
            else if (_ma.isInteger() && !_ma.isAlpha()) {
                return "INT";
            }
            else if (_ma.isDecimal() || _ma.isNumeric()) {
                if (_ma.getOdsLength() > 10) {
                    m_dbPDH.debug(D.EBUG_DETAIL,
                                  "getDBFieldType: *** Curiously large odslength for a number," + _ma.getAttributeCode() + ":" +
                                  strODSLen + ".  Setting to " + strODSLen + " instead of 10");
                    return "VARCHAR(" + strODSLen + ")";
                }
                else {
                    return "CHAR(10)";
                }
            }
            else {
                return "VARCHAR(" + strODSLen + ")";
            }
        }
        else if (_ma.getAttributeType().equals("U")) {
            return "VARCHAR(" + strODSLen + ")";
        }
        else if (_ma.getAttributeType().equals("S")) {
            return "VARCHAR(" + strODSLen + ")";
        }
        else if (_ma.getAttributeType().equals("F")) {
            return "LONG VARCHAR";
        }
        else if (_ma.getAttributeType().equals("L")) {
          if (iODSLength > 0) {              //Use the length defined instead of defaulting to long varchar
            m_dbPDH.debug(D.EBUG_DETAIL,"getDBFieldType: *** OVERRIDING DEFAULT LONG VARCHAR for attribute " + _ma.getAttributeCode() + ":"
                          +"to VARCHAR(" + iODSLength + ")");
            return "VARCHAR(" + iODSLength + ")";
          } else {
            return "LONG VARCHAR";
          }
        }
        else if (_ma.getAttributeType().equals("X")) {
          return "LONG VARCHAR";
        }
        else if (_ma.getAttributeType().equals("B")) {
            return "BLOB (10M)";
        }
        else {
            return "VARCHAR(25)";
        }
    }

    /**
     *  This will populate the ODS table with data from the corresponding PDH
     *  entity
     *
     *@param  _eg                      EntityGroup object for the ODS Table
     *@exception  SQLException
     *@exception  MiddlewareException
     */
    protected void populateODSTable(EntityGroup _eg) throws SQLException, MiddlewareException {

        int iSessionID = m_dbPDH.getNewSessionID();
        int iStartID = 0;
        int iEndID = 0;
        int iMaxID = 0;
        int iEntityCount = 0;
        boolean bLoop = false;
        EANList elMulti = new EANList();
        EANList elTrans = new EANList();
        String strEntityType = _eg.getEntityType();
        String strEntity1Type = null;
        String strEntity2Type = null;

        String strEnterprise = m_prof.getEnterprise();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        ReturnDataResultSet rdrs1 = null;
        ReturnStatus returnStatus = new ReturnStatus( -1);

        EntityGroup eg1 = null;
        EntityGroup eg2 = null;

        boolean bFKEY = false;

        String strInsertHEADSQL = null;
        String strInsertFIELDSSQL = null;
        String strInsertMARKERSQL = null;

        String strInsertCATNAVFIELDSQL = null;
        String strInsertCATNAVMARKERSQL = null;
        String strInsertCATNAVVALUESSQL = null;

        String strDeleteFLAGSQL = null;
        String strInsertFLAGSQL = null;
        String strUpdateFKey = null;

        PreparedStatement psFlag = null;
        PreparedStatement psFlagDel = null;
        PreparedStatement psFKey = null;
        int[] iAllNLS = new int[m_prof.getReadLanguages().size()];
        String[] strCountryCode = new String[m_prof.getReadLanguages().size()];
        String[] strLanguageCode = new String[m_prof.getReadLanguages().size()];
        String[] strCountryList = new String[m_prof.getReadLanguages().size()];


        String strCountryFilterAttribute = CatODSServerProperties.getCountryFilterAttribute(m_strODSSchema,_eg.getEntityType());
        boolean bhasCountryFilter = ((strCountryFilterAttribute.length() > 0 ) ? true :false);

        Hashtable hCatNavs = new Hashtable();
        NLSItem nlsCurrent = null;
        System.gc();

        // Set up the possibility for relator management ...

        // O.K.  lets strip this information from some international flags..
        // Some flags can only be us. english only

//    setEnglishOnlyFlags(_eg);             Check if this has to be removed

        //Set up the General area map


        try {
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(this.getClass());
            GeneralAreaMap gam = getCatalog().getGam();

            GeneralAreaMapGroup gamp = gam.lookupGeneralArea(strGeneralArea);
            Enumeration en = gamp.elements();

            if (!en.hasMoreElements()) {
                System.out.println("no gami to find!!!");
            }

            //Allocate all the working NLS for this catalog Entity
            while (en.hasMoreElements()) {

                Iterator it = null;

                GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();

                Vector vct = m_prof.getReadLanguages();
                for (int i = 0; i < vct.size(); i++) {
                    NLSItem nlsi = (NLSItem) vct.elementAt(i);
                    if (nlsi.getNLSID() == gami.getNLSID()) {
                        strCountryCode[i] = gami.getCountry();
                        strLanguageCode[i] = gami.getLanguage();
                        strCountryList[i] = gami.getCountryList();
                        iAllNLS[i] = nlsi.getNLSID();
                        m_dbPDH.debug(D.EBUG_SPEW, "Add Available NLS for this entity :" + nlsi.getNLSID());
                    } else {
                      m_dbPDH.debug(D.EBUG_SPEW, "Skipping NLS for this entity :" + nlsi.getNLSID());

                    }
                }

            }

            for (int y=0;y<iAllNLS.length;y++) {
              m_dbPDH.debug(D.EBUG_SPEW, "Retrieving stored NLS for this entity :" + iAllNLS[y]);

            }

        }
        finally {
            m_dbPDH.commit();
            m_dbPDH.freeStatement();
            m_dbPDH.isPending();
        }

        //Get the chunking going by estimating the chunks we have to process
        int iFloor = 0;
        int iCeiling = 0;

        try {
            rs = m_dbPDH.callGBL9011(new ReturnStatus( -1), strEnterprise, strEntityType, m_strODSSchema, m_iChuckSize);
            rdrs1 = new ReturnDataResultSet(rs);
            if (rdrs1.size() == 0) {
                D.ebug(D.EBUG_DETAIL, "GBL9011:No Entities found to process for " + strEntityType);
            }
            else {
                D.ebug(D.EBUG_DETAIL, "GBL9011:" + strEntityType + " will be processed in: " + rdrs1.size() + " :chunks");
            }
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            m_dbPDH.commit();
            m_dbPDH.freeStatement();
            m_dbPDH.isPending();
        }

        if (_eg.isRelator()) {
            strEntity1Type = _eg.getEntity1Type();
            strEntity2Type = _eg.getEntity2Type();
        }

        bFKEY = CatODSServerProperties.isFKeyMapRelator(m_strODSSchema, strEntityType) &&
            !CatODSServerProperties.getFKeyMap(m_strODSSchema, strEntity2Type).equals("n");

        strDeleteFLAGSQL = "DELETE FROM " + m_strODSSchema + ".FLAG " + " WHERE EntityType = ? AND EntityID = ?";
        strInsertFLAGSQL = "INSERT  INTO " + m_strODSSchema + ".FLAG " +
            " (VALFROM,ENTITYTYPE,ENTITYID, NLSID, ATTRIBUTECODE, FLAGCODE,SFVALUE,FLAGDESCRIPTION) " + " VALUES (?,?,?,?,?,?,?,?)";
        strUpdateFKey = "UPDATE  " + m_strODSSchema + "." + strEntity2Type + " SET " + strEntity1Type +
            "ID = ?, valfrom = ? WHERE ENTITYID = ?";

        try {
            // Do some prepared statements here..
            psFlag = m_conODS.prepareStatement(strInsertFLAGSQL);
            psFlagDel = m_conODS.prepareStatement(strDeleteFLAGSQL);
            psFKey = m_conODS.prepareStatement(strUpdateFKey);

            //
            // Lets see if we want to do that forgien key trick.
            //


            //For each nls in the Generalareamapgroup, get the attributes and write
            //for each item in the
            for (int x = 0; x < rdrs1.size(); x++) {
                iFloor = rdrs1.getColumnInt(x, 0);
                iCeiling = rdrs1.getColumnInt(x, 1);
                iStartID = rdrs1.getColumnInt(x, 2);
                iEndID = rdrs1.getColumnInt(x, 3);
                D.ebug(D.EBUG_DETAIL,
                       "GBL9011:Result:et" + strEntityType + ":floor:" + iFloor + ":ceiling:" + iCeiling + ":start:" + iStartID +
                       ":finish:" + iEndID);

                System.gc();

                if (_eg.isRelator()) {
                    eg1 = new EntityGroup(null, m_dbPDH, m_prof, strEntity1Type, "No Atts");
                    eg2 = new EntityGroup(null, m_dbPDH, m_prof, strEntity2Type, "No Atts");
                }

                try {
                    //Get the entities which will fulfill the filter criteria set in the meta for this entity
                    m_dbPDH.debug(D.EBUG_DETAIL,
                                  "gbl9000:params:" + iSessionID + ":" + strEnterprise + ":" + strEntityType + ":" + m_strODSSchema +
                                  ":" + iStartID + ":" + iEndID);
                    rs = m_dbPDH.callGBL9000(returnStatus, iSessionID, strEnterprise, strEntityType, m_strODSSchema, iStartID,
                                             iEndID);
                    rdrs = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs == null) {
                        rs.close();
                        rs = null;
                    }
                    m_dbPDH.commit();
                    m_dbPDH.freeStatement();
                    m_dbPDH.isPending();
                }

                // O.K.  Lets tuck all the entity items into the group
                for (int ii = 0; ii < rdrs.size(); ii++) {
                    int iEntity1ID = rdrs.getColumnInt(ii, 0);
                    int iEntityID = rdrs.getColumnInt(ii, 1);
                    int iEntity2ID = rdrs.getColumnInt(ii, 2);
                    String strValFrom = rdrs.getColumnDate(ii, 3);
                    String strValTo = rdrs.getColumnDate(ii, 4);
                    EntityItem ei1 = null;
                    EntityItem ei2 = null;
                    EntityItem ei = null;
                    m_dbPDH.debug(D.EBUG_SPEW,
                                  "gbl9000:answers:" + iEntity1ID + ":" + iEntityID + ":" + iEntity2ID + ":" + strValFrom + ":" +
                                  strValTo);
                    if (!_eg.containsEntityItem(strEntityType, iEntityID) &&
                        ( (_eg.isRelator() && iEntity1ID > 0 && iEntity2ID > 0) || !_eg.isRelator())) {
                        ei = new EntityItem(_eg, null, strEntityType, iEntityID);
                        _eg.putEntityItem(ei);
                    }
                    if (_eg.isRelator() && iEntity1ID > 0 && iEntity2ID > 0) {
                        ei1 = new EntityItem(eg1, null, strEntity1Type, iEntity1ID);
                        eg1.putEntityItem(ei1);
                        ei2 = new EntityItem(eg2, null, strEntity2Type, iEntity2ID);
                        eg2.putEntityItem(ei2);
                        ei.putUpLink(ei1);
                        ei.putDownLink(ei2);
                    }
                }

                // Now ... we fill out all the attributes if anything was populated
                // from above

                if (rdrs.size() > 0) {
                    popAllAttributeValues(_eg, iSessionID, false);
                }

                // Now remove all the records to clean up after yourself
                // We need a simpler way to do this

                m_dbPDH.callGBL8105(returnStatus, iSessionID);
                m_dbPDH.commit();
                m_dbPDH.freeStatement();
                m_dbPDH.isPending();

                // Now.. lets build the SQL table insert statement

                strInsertHEADSQL = "INSERT INTO " + m_strODSSchema + "." + strEntityType;
                strInsertFIELDSSQL = (_eg.isRelator() ?
                                      " (EntityID, NLSID, ID1, ID2, VALFROM, VALTO,ISACTIVE,COUNTRYCODE,LANGUAGECODE,COUNTRYLIST" :
                                      " (EntityID, NLSID, VALFROM, VALTO,ISACTIVE,COUNTRYCODE,LANGUAGECODE,COUNTRYLIST");
                strInsertMARKERSQL = (_eg.isRelator() ? " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?" : " VALUES (?, ?, ?, ?, ?, ?, ?, ?");

                MultiRowAttrGroup cng = new MultiRowAttrGroup(getCatalog(), strEntityType);
                for (int i = 0; i < cng.getItemCount(); i++) {
                    MultiRowAttrItem cni = cng.getItem(i);
                    String strAttrCode = cni.getColumnKey().trim(); //Get the attributecodes
                    //Store in hashtable
                    if (!hCatNavs.containsKey(strAttrCode)) {
                        hCatNavs.put(strAttrCode, "We processed this attribute");
                        D.ebug(D.EBUG_SPEW,
                               "CatODSMethods.populateODSTable:ET:" + strEntityType + ": Adding to hashtable Attribute: " +
                               strAttrCode);

                    }

                }

                // Here  we build the rest of the SQL Statement

                for (int iEcount = 0; iEcount < _eg.getEntityItemCount(); iEcount++) {
                    //We need to set up once for the catnav attributes
                    //whose flag values are mandatory in all the active nls's
                    if (iEcount == 0) {
                        //Now get all the special attributes with its values all rows  'Must' have in combination with the NLS's
                        D.ebug(D.EBUG_DETAIL,
                               "CatODSMethods.populateODSTable:ET:" + strEntityType + ": Processing CatNavAttributes ");


                    }

                    String strInsertFIELDS = "";
                    String strInsertMARKER = "";
                    EntityItem ei = _eg.getEntityItem(iEcount);

                    PreparedStatement psInsert = null;
                    try {

                        D.ebug(D.EBUG_DETAIL, "CatODSMethods.populateODSTable:ET:" + ei.getEntityType() + ":EID:" + ei.getEntityID());

                        // O.K.  if we are not in an INIT ALL State.. or we are in a restatewe have to remove
                        // multi flags from the flag table first .. for each entity or were we caught in the middle of a restart?
                        if (! (m_bInitAll || m_bMultiFlag) || m_bRestart) {
                            try {
                                psFlagDel.setString(1, strEntityType);
                                psFlagDel.setInt(2, ei.getEntityID());
                                D.ebug(D.EBUG_DETAIL,
                                       "CatODSMethods.populateODSTable:Deleting MultiFlagValues Entity info for (" +
                                       ei.getEntityType() + ":" + ei.getEntityID() + ") " + ei.toString());
                                psFlagDel.execute();

                            }
                            catch (SQLException ex) {
                                D.ebug(D.EBUG_ERR, "**Deleting Multi Flag Entity:" + ex.getMessage());
                            }
                        }

                        elMulti = new EANList();
                        elTrans = new EANList();
                        boolean bCatNavFound = false;
                        int iInsertOffset = 0;
                        //Repeat for all languages (NLS) enabled
                        for (int iNLSCount = 0; iNLSCount < iAllNLS.length; iNLSCount++) {
                            Vector vCatNavAttrValues = new Vector();
                            D.ebug(D.EBUG_SPEW,"Readlanguage in vector is "+iAllNLS[iNLSCount]);
                            if (iAllNLS[iNLSCount] == 0) { //We have reached the end of allocated NLS's
                                D.ebug(D.EBUG_SPEW,"Skipping read language in position "+iNLSCount);
                                continue;
                            }


                            if (bhasCountryFilter) {
                              if (!isCountryFilterMatch(strCountryFilterAttribute,ei,strCountryList[iNLSCount])) {
                                D.ebug(this,D.EBUG_DETAIL,"Discarding "+ei.getEntityType()+":"+ei.getEntityID()+":"+iAllNLS[iNLSCount]+" as value of "+strCountryFilterAttribute+ " DOES NOT match "+strCountryList[iNLSCount]);
                                       continue;
                              }
                            }



                            String strInsertSQL = null;

                            int iInsertCount = 0;

                            //Set the NLSid to be read in the profile so that we retrieve the correct translations
                            nlsCurrent = new NLSItem(iAllNLS[iNLSCount], "Current translation");
                            m_prof.setReadLanguage(nlsCurrent);
                            D.ebug(D.EBUG_INFO,
                                   "Setting readLanguage for Entity :" + ei.getEntityType() + ":" + ei.getEntityID() + ": to :" +
                                   iAllNLS[iNLSCount]);

                            strInsertFIELDS = "";
                            strInsertMARKER = "";
                            strInsertCATNAVFIELDSQL = "";
                            strInsertCATNAVMARKERSQL = "";
                            bCatNavFound = false;
                            for (int iAttrCount = 0; iAttrCount < ei.getAttributeCount(); iAttrCount++) {

                                EANAttribute att = ei.getAttribute(iAttrCount);
                                EANMetaAttribute ma = att.getMetaAttribute();
                                if (isDerivedEntityID(ma)) {
                                    continue;
                                }
                                if (att.toString().length() == 0) {
                                    D.ebug(D.EBUG_INFO,
                                           att.getAttributeCode() + "!!! ZERO LENGTH ATT FOUND AND SKIPPED. Section #1 !!!");
                                    continue;
                                }

                                //Special processing for CATNAV attrs..ignoring whether its a Multiflag attr..we got
                                // to include all values for it when its selected
                                if (hCatNavs.containsKey(att.getAttributeCode().trim())) {
                                    bCatNavFound = true;
                                    D.ebug(D.EBUG_SPEW, "AC#COUNT0#:" + iAttrCount + ":" + att.getAttributeCode());
                                    strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode();
                                    strInsertMARKER = strInsertMARKER + ", ?";

                                    if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S") ||
                                        ma.getAttributeType().equals("F")) {
                                        strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode() + "_FC";
                                        strInsertMARKER = strInsertMARKER + ", ?";
                                    }

                                }
                                else if (includeColumn(ma) && att.toString().length() > 0) {
                                    D.ebug(D.EBUG_SPEW, "AC#COUNT1#:" + iAttrCount + ":" + att.getAttributeCode());
                                    strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode();
                                    strInsertMARKER = strInsertMARKER + ", ?";
                                    if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                                        strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode() + "_FC";
                                        strInsertMARKER = strInsertMARKER + ", ?";
                                    }
                                }
                            }

                            strInsertSQL = strInsertHEADSQL + strInsertFIELDSSQL + strInsertFIELDS + ")" + strInsertMARKERSQL +
                                strInsertMARKER + ")";

                            D.ebug(D.EBUG_SPEW, strInsertSQL);

                            psInsert = m_conODS.prepareStatement(strInsertSQL);
                            psInsert.setInt(1, ei.getEntityID());
                            psInsert.setInt(2, iAllNLS[iNLSCount]); // Setting the ODS NLS here

                            iInsertCount = 0;
                            if (_eg.isRelator()) {
                                EntityItem eip = (EntityItem) ei.getUpLink(0);
                                EntityItem eic = (EntityItem) ei.getDownLink(0);

                                psInsert.setInt(3, (eip == null ? -1 : eip.getEntityID()));
                                psInsert.setInt(4, (eic == null ? -1 : eic.getEntityID()));

                                psInsert.setString(5, m_strNow);
                                psInsert.setString(6, m_strForever);
                                psInsert.setInt(7, 1);
                                psInsert.setString(8, strCountryCode[iNLSCount]);
                                psInsert.setString(9, strLanguageCode[iNLSCount]);
                                psInsert.setString(10, strCountryList[iNLSCount]);
                                iInsertCount = 11;
                            }
                            else {
                                psInsert.setString(3, m_strNow);
                                psInsert.setString(4, m_strForever);
                                psInsert.setInt(5, 1);
                                psInsert.setString(6, strCountryCode[iNLSCount]);
                                psInsert.setString(7, strLanguageCode[iNLSCount]);
                                psInsert.setString(8, strCountryList[iNLSCount]);
                                iInsertCount = 9;
                            }
                            iInsertOffset = iInsertCount;
                            for (int iAttrCount = 0; iAttrCount < ei.getAttributeCount(); iAttrCount++) {
                                EANAttribute att = ei.getAttribute(iAttrCount);
                                EANMetaAttribute ma = att.getMetaAttribute();
                                if (att.toString().length() == 0) {
                                    D.ebug(D.EBUG_INFO,
                                           att.getAttributeCode() + "!!! ZERO LENGTH ATT FOUND AND SKIPPED Section #2 !!!");
                                    continue;
                                }
                                if (isDerivedEntityID(ma)) {
                                    continue;
                                }

                                // If its a column we need..
                                // and its a multi value flag that is needed and is not a SF Flag
                                // lets collect some flag info right now!
                                // and some s and feed attributes
                                if (iNLSCount == 0) { //We are on NLS 1
                                    if (ma.getAttributeType().equals("F")) {
                                        elMulti.put(att);
                                    }
                                }

                                // Now lets build the table columns for the table row update
                                D.ebug(D.EBUG_SPEW, "Checking attribute " + att.getAttributeCode());
                                if (includeColumn(ma) && att.toString().length() > 0 &&
                                    !hCatNavs.containsKey(att.getAttributeCode().trim())) {

                                    D.ebug(D.EBUG_SPEW,
                                           "AC#COUNT2#:" + iAttrCount + ":" + iInsertCount + ":" + att.getAttributeCode() + ":" +
                                           ma.getAttributeType() + ":IN:" + ma.isInteger() + ":DT:" + ma.isDate() + ":AL:" +
                                           ma.isAlpha() + ":" + att.toString());

                                    // Temp use string to set the date and let it be converted  by DB2 to the proper date
                                    if (ma.isDate() && ma.getAttributeType().equals("T")) {
                                        try {
                                            if (att.toString().trim().toUpperCase().equals("OPEN") ||
                                                att.toString().trim().equals("Open")) {
                                                D.ebug(D.EBUG_ERR,
                                                    "**DATE IN " + ma.getAttributeCode() + " -- Set to OPEN in PDH, setting to null in ODS");
                                                psInsert.setString(iInsertCount, null);
                                            }
                                            else {
                                                psInsert.setString(iInsertCount, getODSString(att));
                                                D.ebug(D.EBUG_SPEW,
                                                    "TEXT Position: " + iInsertCount + " :Value:" + getODSString(att));
                                            }
                                        }
                                        catch (Exception dx) {
                                            D.ebug(D.EBUG_ERR,
                                                "**#0 PARAMETER SET ERROR:  Date format Exception for (" + ei.getEntityType() + ":" +
                                                ei.getEntityID() + ":" + att.getAttributeCode() + ") - Value (" + att.toString() +
                                                ").  Using null." + ":" + dx.getMessage());
                                            psInsert.setString(iInsertCount, null);
                                        }
                                    }
                                    else if (ma.isInteger() && !ma.isAlpha() && ma.getAttributeType().equals("T")) {
                                        try {
                                            psInsert.setInt(iInsertCount, Integer.valueOf(att.toString()).intValue());
                                            D.ebug(D.EBUG_SPEW,
                                                "INTEGER Position: " + iInsertCount + " :Value:" +
                                                Integer.valueOf(att.toString()).intValue());
                                        }
                                        catch (NumberFormatException nx) {
                                            D.ebug(D.EBUG_ERR,
                                                "**#1 PARAMETER SET ERROR:  Number format Exception for (" + ei.getEntityType() +
                                                ":" + ei.getEntityID() + ":" + att.getAttributeCode() + ") - Value (" +
                                                att.toString() + ").  Using zero(0) instead." + ":" + nx.getMessage());
                                            psInsert.setInt(iInsertCount, 0);
                                        }
                                    }
                                    else {
                                        try {
                                            psInsert.setString(iInsertCount, getODSString(att));
                                            D.ebug(D.EBUG_SPEW,
                                                "OTHER TEXT Position: " + iInsertCount + " :Value:" + getODSString(att));
                                        }
                                        catch (SQLException nx) {
                                            D.ebug(D.EBUG_ERR,
                                                "**#2 BASIC TEXT PARAMETER SET ERROR: SQLError for (" + ei.getEntityType() + ":" +
                                                ei.getEntityID() + ":" + att.getAttributeCode() + ") - Value (" + att.toString() +
                                                ").  Using null instead." + ":" + nx.getMessage());
                                            psInsert.setString(iInsertCount, null);
                                        }
                                    }
                                    iInsertCount++;

                                    // Lets look for the flag code option
                                    //

                                    if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                                        EANFlagAttribute fa = (EANFlagAttribute) att;
                                        try {
                                            psInsert.setString(iInsertCount, fa.getFirstActiveFlagCode());
                                            D.ebug(D.EBUG_SPEW,
                                                "FLAG Position: " + iInsertCount + " :Value:" + fa.getFirstActiveFlagCode());
                                        }
                                        catch (SQLException nx) {
                                            D.ebug(D.EBUG_ERR,
                                                "**#3 PARAMETER SET ERROR: SQLError for (" + ei.getEntityType() + ":" +
                                                ei.getEntityID() + ":" + att.getAttributeCode() + ") - Value (" + att.toString() +
                                                ").  Using null instead." + ":" + nx.getMessage());
                                            psInsert.setString(iInsertCount, null);
                                        }
                                        iInsertCount++;
                                    }

                                }
                                else if (hCatNavs.containsKey(att.getAttributeCode().trim())) {
                                    D.ebug(D.EBUG_SPEW,
                                           "CATNAVATTR:Storing position for :" + att.getAttributeCode() + ":" + iInsertCount);
                                    hCatNavs.remove(att.getAttributeCode().trim()); //Store value of position in hashtable
                                    hCatNavs.put(att.getAttributeCode().trim(), iInsertCount + "");
                                    iInsertCount++;
                                    iInsertCount++; //twice for including _FC

                                }

                            }

                            Enumeration eCatNavs = hCatNavs.keys();
                            ArrayList alAllValues = new ArrayList();
                            ArrayList alFlagValues = new ArrayList();
                            ArrayList alAllFlagCodes = new ArrayList();
                            ArrayList alFlagCodes = new ArrayList();
                            String[] strCatNavColumns = new String[hCatNavs.size()];

                            //Way too complex....gotta find a way to simplify this
                            if (bCatNavFound) {
                                int i = 0;
                                //Get all the FlagValues and store it
                                while (eCatNavs.hasMoreElements()) {
                                    String strCatNavAttr = (String) eCatNavs.nextElement();
                                    strCatNavColumns[i] = strCatNavAttr;
                                    i++;
                                    int iPosition = Integer.valueOf( (String) hCatNavs.get(strCatNavAttr)).intValue();
                                    D.ebug(D.EBUG_SPEW, "CATNAVATTR:Retrieving position for :" + strCatNavAttr);
                                    EANAttribute att = ei.getAttribute(strCatNavAttr);
                                    EANMetaAttribute ma = att.getMetaAttribute();
                                    D.ebug(D.EBUG_SPEW, "CATNAVATTR:" + iPosition + ":" + att.getAttributeCode().trim());
                                    String strFlagValues = "";
                                    String strFlagCode = "";
                                    switch (ma.getAttributeType().charAt(0)) {
                                        case 'F':
                                        case 'U':
                                        case 'S':
                                            MetaFlag[] mfAttr = (MetaFlag[]) att.get();
                                            for (int k = 0; k < mfAttr.length; k++) {
                                                if (mfAttr[k].isSelected()) { //Store this
                                                    strFlagValues = mfAttr[k].getLongDescription();
                                                    alFlagValues.add(strFlagValues);
                                                    strFlagCode = mfAttr[k].getFlagCode();
                                                    alFlagCodes.add(strFlagCode);
                                                    D.ebug(D.EBUG_SPEW,
                                                        "CATNAVATTR:Storing flag value for :" + att.getAttributeCode().trim() +
                                                        ":CODE:" + strFlagCode + ":VALUE:" + strFlagValues);
                                                }
                                            }

                                            break;
                                        default:

                                    }
                                    D.ebug(D.EBUG_SPEW, "CATNAVATTR:Store to ArrayList");
                                    alAllValues.add(alFlagValues);
                                    alAllFlagCodes.add(alFlagCodes);

                                }

                                //Create the 2d array we want with initial size
                                String[][] strAllFlagValues = new String[alAllValues.size()][];
                                convertToArray(strAllFlagValues, alAllValues);
                                D.ebug(D.EBUG_SPEW, "CATNAVATTR:Convert to Array#1");

                                String[][] strAllFlagCodes = new String[alAllValues.size()][];
                                convertToArray(strAllFlagCodes, alAllFlagCodes);
                                D.ebug(D.EBUG_SPEW, "CATNAVATTR:Convert to Array#2");

                                //Set up the answer array which holds the combination of values from the flags
                                int[] iResultDim = getCombinationSize(strAllFlagValues);
                                String[] strFlagValueResult = new String[iResultDim[0]];
                                String[] strFlagCodeResult = new String[iResultDim[0]];

                                //Process the array here and insert the rows

                                iLevel = 1;
                                computePermutations(strFlagValueResult, strAllFlagValues, 0, "");

                                iLevel = 1;
                                computePermutations(strFlagCodeResult, strAllFlagCodes, 0, "");

                                //Here we go!
                                for (int k = 0; k < strFlagValueResult.length; k++) {
                                    String strValues = strFlagValueResult[k];
                                    String strFlagCodes = strFlagCodeResult[k];
                                    StringTokenizer st = new StringTokenizer(strValues, ",");
                                    StringTokenizer st1 = new StringTokenizer(strFlagCodes, ",");
                                    int iCol = 0;
                                    String strCatNavAttr = null;
                                    String strFlagValue = null;
                                    String strFlagCode = null;
                                    while (st.hasMoreTokens()) {
                                        strCatNavAttr = strCatNavColumns[iCol];
                                        EANAttribute att = ei.getAttribute(strCatNavAttr);
                                        EANMetaAttribute ma = att.getMetaAttribute();
                                        strFlagValue = st.nextToken().trim();

                                        if (st1.hasMoreTokens()) {
                                            strFlagCode = st1.nextToken().trim();
                                        }
                                        iCol++;

                                        int iPosition = Integer.valueOf( (String) hCatNavs.get(strCatNavAttr)).intValue();
                                        D.ebug(D.EBUG_DETAIL,
                                               "CatODSMethods.populateODSTable:ET:" + strEntityType + ": CatNavAttributes : Found values for " +
                                               strCatNavAttr + ":TYPE:" + ma.getAttributeType() + ":NLS:" + iAllNLS[iNLSCount] +
                                               ":POSITION:" + iPosition + ":VALUES:" + strFlagValue + ":" + strFlagCode);

                                        try {
                                            psInsert.setString(iPosition, strFlagValue);

                                        }
                                        catch (SQLException se) {
                                            D.ebug(D.EBUG_ERR,
                                                "**#0 PARAMETER SET ERROR: SQLError for (" + ei.getEntityType() + ":LOCATION:" +
                                                iPosition + ":" + ei.getEntityID() + ":" + strCatNavAttr + ") - Value (" +
                                                strFlagValue + ").  Using null instead." + ":" + se.getMessage());
                                            psInsert.setString(iPosition, null);

                                        }

                                        if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S") ||
                                            ma.getAttributeType().equals("F")) {
                                            EANFlagAttribute fa = (EANFlagAttribute) att;
                                            D.ebug(D.EBUG_SPEW, "About to set the " + strCatNavAttr + "_FC to :" + strFlagCode);
                                            try {
                                                psInsert.setString(iPosition + 1, strFlagCode);
                                            }
                                            catch (SQLException nx) {
                                                D.ebug(D.EBUG_ERR,
                                                    "**#3 PARAMETER SET ERROR: SQLError for (" + ei.getEntityType() + ":" +
                                                    ei.getEntityID() + ":" + att.getAttributeCode() + ") - Value (" + strFlagValue +
                                                    ").  Using null instead." + ":" + nx.getMessage());
                                                psInsert.setString(iPosition + 1, null);
                                            }
                                        }

                                    }
                                    D.ebug(D.EBUG_INFO,
                                           "Inserting Entity(" + ei.getEntityType() + ":" + ei.getEntityID() + ") " + ei.toString());

                                    psInsert.execute();

                                }

                            }
                            else { //This is the Vanilla insert when no CatNav values are found

                                D.ebug(D.EBUG_INFO,
                                       "Inserting Entity(" + ei.getEntityType() + ":" + ei.getEntityID() + ") " + ei.toString());

                                psInsert.execute();

                            }
                        } //End NLS Processing

                        //
                        // set it back to the first language in list for basic processing
                        //

                        //nlsCurrent = new NLSItem(iAllNLS[0], "Current translation");
                        //m_prof.setReadLanguage(nlsCurrent);

                        //
                        // This is normal flag processing that is not considered one of the S&F attributes
                        // this nlsid = 0
                        if (elMulti.size() > 0) {
                            psFlag.setString(1, m_strNow);
                            psFlag.setString(2, strEntityType);
                            psFlag.setInt(3, ei.getEntityID());
                            psFlag.setInt(4, iAllNLS[0]);
                            D.ebug(D.EBUG_INFO, "->\tMultiFlags:Starting:" + ei.getEntityType() + ":" + ei.getEntityID());
                            for (int iy = 0; iy < elMulti.size(); iy++) {
                                EANFlagAttribute fa = (EANFlagAttribute) elMulti.getAt(iy);
                                EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) fa.getMetaAttribute();
                                Iterator it = fa.getPDHHashtable().keySet().iterator();
                                String strAttributeCode = fa.getAttributeCode();

                                psFlag.setString(5, strAttributeCode);

                                while (it.hasNext()) {
                                    String strFlagCode = (String) it.next();
                                    MetaFlag mf = mfa.getMetaFlag(strFlagCode);
                                    psFlag.setString(6, strFlagCode);
                                    psFlag.setInt(7, 0);
                                    psFlag.setString(8, mf.getLongDescription());
                                    psFlag.execute();
                                }
                            }
                            D.ebug(D.EBUG_INFO, "->\tMultiFlags:Finishing:" + ei.getEntityType() + ":" + ei.getEntityID());

                        }

                        // Lets take care of the fkey here
                        if (bFKEY) {

                            int iID1 = ( (EntityItem) ei.getUpLink(0)).getEntityID();
                            int iID2 = ( (EntityItem) ei.getDownLink(0)).getEntityID();

                            psFKey.setInt(1, iID1);
                            psFKey.setString(2, m_strNow);
                            psFKey.setInt(3, iID2);
                            D.ebug(D.EBUG_INFO,
                                   "->\tFKEY:Starting:" + ei.getEntityType() + ":" + ei.getEntityID() + ":" + iID1 + " --> " + iID2);
                            psFKey.execute();
                            D.ebug(D.EBUG_INFO,
                                   "->\tFKEY:Finishing:" + ei.getEntityType() + ":" + ei.getEntityID() + ":" + iID1 + " --> " +
                                   iID2);
                        }

                    }
                    catch (SQLException ex) {
                        String strErr = "";
                        D.ebug(D.EBUG_ERR,
                               "** INSERTION ERROR *** Skipping Entity (" + ei.getEntityType() + ":" + ei.getEntityID() + ") Error is: " +
                               ex.getMessage());
                        for (int ierr = 0; ierr < ei.getAttributeCount(); ierr++) {
                            EANAttribute eanAtt = ei.getAttribute(ierr);
                            strErr = strErr + eanAtt.getAttributeCode();
                            strErr = strErr + ":" + eanAtt.toString() + NEW_LINE;
                        }
                        D.ebug(D.EBUG_ERR, "Values are ******:" + NEW_LINE + strErr);
                    }
                    finally{
                    	if(psInsert != null){
	                    	psInsert.close();
	                        psInsert = null;
                    	}
                    }
                }

                m_conODS.commit();
                _eg.resetEntityItem();

                if (eg1 != null) {
                    eg1.resetEntityItem();
                }
                if (eg2 != null) {
                    eg2.resetEntityItem();
                }
                System.gc();

            }
        }
        finally {
            // Close the stuff out
            psFlag.close();
            psFlag = null;
            psFlagDel.close();
            psFlagDel = null;
            psFKey.close();
            psFKey = null;
        }
    }

    int[] getCombinationSize(String[][] _strArray) {
        int iRows = 0;
        int iColumns = 0;
        int[] iReturn = new int[2];
        for (int i = 0; i < _strArray.length; i++) {
            iColumns = (iColumns < _strArray.length) ? _strArray.length : iColumns;
            if (i == 0) {
                iRows = _strArray[i].length;
            }
            else {
                iRows = iRows * _strArray[i].length;
            }
        }
        D.ebug(D.EBUG_SPEW, "Combinations possible are " + iRows + "," + iColumns);
        iReturn[0] = iRows;
        iReturn[1] = iColumns;
        return iReturn;
    }

    void convertToArray(String[][] _strAr, ArrayList _al) {
        for (int i = 0; i < _al.size(); i++) {
            ArrayList alTemp = (ArrayList) _al.get(i);
            _strAr[i] = new String[alTemp.size()];
            for (int y = 0; y < alTemp.size(); y++) {
                _strAr[i][y] = (String) alTemp.get(y);
            }
        }
    }

    void computePermutations(String[] _strResult, String[][] _strFrom, int _iIndex, String _strPrefix) {

        for (int i = 0; i < _strFrom[_iIndex].length; ++i) {
            if (_iIndex < (_strFrom.length - 1)) {
                computePermutations(_strResult, _strFrom, _iIndex + 1, _strPrefix + _strFrom[_iIndex][i] + ",");
            }
            else {
                _strResult[iLevel - 1] = _strPrefix + _strFrom[_iIndex][i];
                iLevel++;
            }
        }
    }

    /**
     * growFlagArray
     *
     * @param strFValCombination String[][]
     * @param vFlagValues Vector
     */
    private void growFlagArray(String[][] _strFValCombination, Vector _vFlagValues) {
        String[][] strTempArray = (String[][]) Array.newInstance(String.class, new int[] {
            _strFValCombination.length + 1, _vFlagValues.size()
        });
        if (_strFValCombination != null) {
            System.arraycopy(_strFValCombination, 0, strTempArray, 0, _strFValCombination.length);
        }
        _strFValCombination = strTempArray;
    }

    /**
     *  Repopulates all the Entities and its attributes from the rows retrieved from the database for the entity group
     *
     *@param  _eg                      EntityGroup object
     *@param  _iSessionID              Session Id
     *@param  _bPullExpiredAtts        SP switch (Do we only want only VALID attributes, or do we pull all - i.e. true means pick up 'turned-off' attributes)
     *@exception  SQLException
     *@exception  MiddlewareException
     */
    private void popAllAttributeValues(EntityGroup _eg, int _iSessionID, boolean _bPullExpiredAtts) throws SQLException,
        MiddlewareException {

        ReturnStatus returnStatus = new ReturnStatus();
        ResultSet rs = null;

        String strEnterprise = m_prof.getEnterprise();
        String strValOn = m_prof.getValOn();
        String strEffOn = m_prof.getEffOn();

        // Lets set the rules to lax here
        m_df.setLenient(true);

        try {
            if (_bPullExpiredAtts) {
                rs = m_dbPDH.callGBL9006(returnStatus, strEnterprise, _iSessionID, m_strLastRun, m_strNow);
            }
            else {
                rs = m_dbPDH.callGBL9002(returnStatus, strEnterprise, _iSessionID, strValOn, strEffOn);
            }

            while (rs.next()) {

                EANAttribute att = null;
                TextAttribute ta = null;
                SingleFlagAttribute sfa = null;
                StatusAttribute sa = null;
                MultiFlagAttribute mfa = null;
                TaskAttribute tska = null;
                LongTextAttribute lta = null;
                XMLAttribute xa = null;
                BlobAttribute ba = null;

                String str1 = rs.getString(1).trim();
                int i1 = rs.getInt(2);
                String str2 = rs.getString(3).trim();
                int i2 = rs.getInt(4);
                String str3 = rs.getString(5).trim();
                String str4 = rs.getString(6).trim();
                String str5 = rs.getString(7).trim();

                EntityItem ei = _eg.getEntityItem(str1, i1);
                EANMetaAttribute ma = _eg.getMetaAttribute(str2);

                m_dbPDH.debug(D.EBUG_SPEW,
                              (_bPullExpiredAtts ? "gbl9006" : "gbl9002") + ":answers:" + str1 + ":" + i1 + ":" + str2 + ":" + i2 +
                              ":" + str3 + ":" + str4 + ":" + str5);

                if (ei == null) {
                    m_dbPDH.debug(D.EBUG_ERR, "** Attribute Information for a non Existiant Entity Item:" + str1 + ":" + i1 + ":");
                    continue;
                }
                if (ma == null) {
                    continue;
                }
                if (isDerivedEntityID(ma)) {
                    continue;
                }

                att = ei.getAttribute(str2);
                if (att == null) {
                    if (ma instanceof MetaTextAttribute) {
                        ta = new TextAttribute(ei, null, (MetaTextAttribute) ma);
                        ei.putAttribute(ta);
                    }
                    else if (ma instanceof MetaSingleFlagAttribute) {
                        sfa = new SingleFlagAttribute(ei, null, (MetaSingleFlagAttribute) ma);
                        ei.putAttribute(sfa);
                    }
                    else if (ma instanceof MetaMultiFlagAttribute) {
                        mfa = new MultiFlagAttribute(ei, null, (MetaMultiFlagAttribute) ma);
                        ei.putAttribute(mfa);
                    }
                    else if (ma instanceof MetaStatusAttribute) {
                        sa = new StatusAttribute(ei, null, (MetaStatusAttribute) ma);
                        ei.putAttribute(sa);
                    }
                    else if (ma instanceof MetaTaskAttribute) {
                        tska = new TaskAttribute(ei, null, (MetaTaskAttribute) ma);
                        ei.putAttribute(tska);
                    }
                    else if (ma instanceof MetaLongTextAttribute) {
                        lta = new LongTextAttribute(ei, null, (MetaLongTextAttribute) ma);
                        ei.putAttribute(lta);
                    }
                    else if (ma instanceof MetaXMLAttribute) {
                        xa = new XMLAttribute(ei, null, (MetaXMLAttribute) ma);
                        ei.putAttribute(xa);
                    }
                    else if (ma instanceof MetaBlobAttribute) {
                        ba = new BlobAttribute(ei, null, (MetaBlobAttribute) ma);
                        ei.putAttribute(ba);
                    }
                }
                else {
                    if (ma instanceof MetaTextAttribute) {
                        ta = (TextAttribute) att;
                        ei.putAttribute(ta);
                    }
                    else if (ma instanceof MetaSingleFlagAttribute) {
                        sfa = (SingleFlagAttribute) att;
                        ei.putAttribute(sfa);
                    }
                    else if (ma instanceof MetaMultiFlagAttribute) {
                        mfa = (MultiFlagAttribute) att;
                        ei.putAttribute(mfa);
                    }
                    else if (ma instanceof MetaStatusAttribute) {
                        sa = (StatusAttribute) att;
                        ei.putAttribute(sa);
                    }
                    else if (ma instanceof MetaTaskAttribute) {
                        tska = (TaskAttribute) att;
                        ei.putAttribute(tska);
                    }
                    else if (ma instanceof MetaLongTextAttribute) {
                        lta = (LongTextAttribute) att;
                        ei.putAttribute(lta);
                    }
                    else if (ma instanceof MetaXMLAttribute) {
                        xa = (XMLAttribute) att;
                        ei.putAttribute(xa);
                    }
                    else if (ma instanceof MetaBlobAttribute) {
                        ba = (BlobAttribute) att;
                        ei.putAttribute(ba);
                    }
                }

                // OK.. drop the value into the structure

                if (ma instanceof MetaTextAttribute) {
                    ta.putPDHData(i2, str3);
                    if (_bPullExpiredAtts) {
                        ta.setValFrom(str4);
                    }
                }
                else if (ma instanceof MetaSingleFlagAttribute) {
                    sfa.putPDHFlag(str3);
                    if (_bPullExpiredAtts) {
                        sfa.setValFrom(str4);
                    }
                }
                else if (ma instanceof MetaMultiFlagAttribute) {
                    mfa.putPDHFlag(str3);
                    if (_bPullExpiredAtts) {
                        mfa.setValFrom(str4);
                    }
                }
                else if (ma instanceof MetaStatusAttribute) {
                    sa.putPDHFlag(str3);
                    if (_bPullExpiredAtts) {
                        sa.setValFrom(str4);
                    }
                }
                else if (ma instanceof MetaTaskAttribute) {
                    tska.putPDHFlag(str3);
                    if (_bPullExpiredAtts) {
                        tska.setValFrom(str4);
                    }
                }
                else if (ma instanceof MetaLongTextAttribute) {
                    lta.putPDHData(i2, str3);
                    if (_bPullExpiredAtts) {
                        lta.setValFrom(str4);
                    }
                }
                else if (ma instanceof MetaXMLAttribute) {
                    xa.putPDHData(i2, str3);
                    if (_bPullExpiredAtts) {
                        xa.setValFrom(str4);
                    }
                }
                else if (ma instanceof MetaBlobAttribute) {
                    ba.putPDHData(i2, new OPICMBlobValue(i2, str3, null));
                    if (_bPullExpiredAtts) {
                        ba.setValFrom(str4);
                    }
                }
                else {
                    m_dbPDH.debug(D.EBUG_ERR, "**Unknown Meta Type for" + str2 + ":");
                }
            }

        }
        finally {

            if (rs != null) {
                rs.close();
                rs = null;
            }
            m_dbPDH.freeStatement();
            m_dbPDH.isPending();
            m_dbPDH.commit();
        }

    }

    /**
     * This guy will take long text verbetum (as with XML)
     * it will turn MultiValueFlags into words seperated by returns.
     * it will ensure maximum length values are forced.. so they fit within the boundries of
     * the ods table.
     * making sure we trim characters.. on character boundries
     * until it fits..
     *  Gets the oDSString attribute of the CatODSMethods object
     *
     *@param  _att  Description of the Parameter
     *@return       The oDSString value
     */
    private String getODSString(EANAttribute _att) {

        EANMetaAttribute ma = _att.getMetaAttribute();
        EntityItem ei = _att.getEntityItem();

        // are we the special MKT_IMG_FILENAME field that needs to be derived?
        if (ma.getAttributeCode().equals("MKT_IMG_FILENAME")) {
            EANBlobAttribute ba = (BlobAttribute) ei.getAttribute("MKTING_IMAGE");
            String strExtension = ".unk";
            String strBlobExt = (ba == null ? _att.toString() : ba.getBlobExtension());
            int i = strBlobExt.lastIndexOf('.');
            if (i > -1) {
                strExtension = strBlobExt.substring(i);
            }
            return ei.getEntityType() + ei.getEntityID() + "_MKT_IMG_FILENAME" + "_" + ei.getNLSItem().getNLSID() + strExtension;
        }

        // If there is no meta attribute.. then return the String Value.. untouched.
        if (ma == null) {
            return _att.toString();
        }

        // If Long Text, or XML.. we return as is
        if (ma.getAttributeType().equals("L")) {
            return _att.toString();
        }

        if (ma.getAttributeType().equals("X")) {
            return _att.toString();
        }

        // O.K.  If multi valued.. we remove the "* " 's so we have flag description followed by /n
        if (ma.getAttributeType().equals("F")) {
            StringTokenizer stMain = new StringTokenizer(_att.toString().replace('\n', ';'), "* ");
            String strAnswer = "";
            while (stMain.hasMoreTokens()) {
                strAnswer = strAnswer + stMain.nextToken().trim();
            }
            return strAnswer;
        }

        // O.K.  All other AttributeTypes .. here we go ..

        try {
            // Get the default max length.. or if one is available in the Meta Attribute.. use it...
            int iMaxLength = (ma.getOdsLength() > -1 ? ma.getOdsLength() : CatODSServerProperties.getVarCharColumnLength());

            String strAnswer = _att.toString();
            int iLength = strAnswer.getBytes("UTF8").length;

            if (iLength > iMaxLength) {
                D.ebug(D.EBUG_WARN,
                       "**getODSString: Length Exceeds max length for (" + ei.getEntityType() + ":" + ei.getEntityID() + ":" +
                       _att.getAttributeCode() + ") - Value (" + _att.toString() + ") - len:" + iLength + ":max:" + iMaxLength +
                       ":offby:" + (iLength - iMaxLength));
                while (iLength > iMaxLength) {
                    strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
                    iLength = strAnswer.getBytes("UTF8").length;
                }
            }
            return strAnswer;
        }
        catch (UnsupportedEncodingException ex) {
            D.ebug(D.EBUG_ERR,
                   "**getODSString: UnsupportedEncodingException (" + ei.getEntityType() + ":" + ei.getEntityID() + ":" +
                   _att.getAttributeCode() + ") - Value (" + _att.toString() + ").  Passing entire string back." + ex.getMessage());
        }

        return _att.toString();
    }

    /**
     * Gets the first 254 characters
     *
     * @param _strAttributeCode
     * @param _str
     * @return
     * @author Dave
     */
    protected String getFirst254Char(String _strAttributeCode, String _str) {
        try {

            int iMaxLength = 254;
            int iLength = _str.getBytes("UTF8").length;
            String strAnswer = _str;

            if (iLength > iMaxLength) {
                D.ebug(D.EBUG_WARN,
                       "**getFirst254Char: Length Exceeds max length for prodattriute table for " + _strAttributeCode + " (which is 254 chars) for :" +
                       _str);
                while (iLength > iMaxLength) {
                    strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
                    iLength = strAnswer.getBytes("UTF8").length;
                }
            }
            _str = strAnswer;
        }
        catch (UnsupportedEncodingException ex) {
            D.ebug(D.EBUG_ERR, "**getFirst254Char: UnsupportedEncodingException.  Passing entire string back." + ex.getMessage());
        }

        return _str;

    }

    /**
     *  This method initializes the deletelog table
     */
    protected void rebuildDeleteLog() {

        ResultSet rs = null;
        Statement stmtDroptable = null;
        Statement stmtCreatetable = null;

        String ODS_ET_WIDTH = "32";
        String strEnterprise = m_prof.getEnterprise();

        String strDropStmt = "DROP TABLE " + m_strODSSchema + ".DELETELOG";
        String strCreateStmt = "CREATE TABLE " + m_strODSSchema + ".DELETELOG " + "(TYPE CHAR(32) NOT NULL," +
            "   ENTITYTYPE CHAR(" + ODS_ET_WIDTH + " ) NOT NULL, " + " ENTITYID INTEGER NOT NULL, " + " ENTITY1TYPE CHAR(" +
            ODS_ET_WIDTH + " ), " + " ENTITY1ID INTEGER ," + " ENTITY2TYPE CHAR(" + ODS_ET_WIDTH + " ), " + " ENTITY2ID INTEGER ," +
            " ATTRIBUTECODE CHAR(32) , " + " ATTRIBUTEVALUE CHAR(24) , " +
            " VALFROM TIMESTAMP NOT NULL WITH DEFAULT CURRENT TIMESTAMP, " + " PRIMARY KEY (TYPE,ENTITYTYPE,ENTITYID,VALFROM)) ";

        try {

            DatabaseMetaData dbMeta = m_conODS.getMetaData();

            String[] strMetaDB2Token = {
                "TABLE"}; // Look only for tables
            boolean bTableThere = false; // flag to indicate if the table is currently present

            try {
                rs = dbMeta.getTables(null, m_strODSSchema, "DELETELOG", strMetaDB2Token); //  Check for the table ...
                if (rs.next()) {
                    bTableThere = true; // We have found it...
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }

            if (bTableThere) {
                //Drop the table since we found it
                m_dbPDH.debug(D.EBUG_DETAIL, "rebuildDeleteLog" + strEnterprise + ": DROPPING DELETELOG");
                try {
                    stmtDroptable = m_conODS.createStatement();
                    stmtDroptable.execute(strDropStmt);
                }
                finally {
                    stmtDroptable.close();
                }

            }
            else {
                m_dbPDH.debug(D.EBUG_DETAIL, "rebuildDeleteLog" + strEnterprise + ":DELETELOG DROP BYPASSED");
            }
            m_dbPDH.debug(D.EBUG_DETAIL, "rebuildDeleteLog" + strEnterprise + ":CREATING NEW DELETELOG");
            try {
                stmtCreatetable = m_conODS.createStatement();
                stmtCreatetable.execute(strCreateStmt);
            }
            finally {
                stmtCreatetable.close();
            }

        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "rebuildDeleteLog" + strEnterprise + ":ERROR:" + e.getMessage());
            System.exit(1);
        }
        finally {
            try {
                m_conODS.commit();
            }
            catch (SQLException ex) {
                m_dbPDH.debug(D.EBUG_ERR, "rebuildDeleteLog" + strEnterprise + ":COMMIT ERROR:" + ex.getMessage());
            }
        }
    } //End method rebuildDeleteLog

    /**
     *  Intializes the timetable
     */
    protected void initializeTimeTable() {

        String strDropTimeTableStmt = "DROP TABLE " + m_strODSSchema + ".TIMETABLE";
        String strCreateNewTimeTableStmt = "CREATE TABLE " + m_strODSSchema +
            ".TIMETABLE (RUNTIME TIMESTAMP NOT NULL, RUNTYPE VARCHAR(1), " + "PRIMARY KEY (RUNTIME)) ";

        ResultSet rs = null;
        Statement stmtDropTimetable = null;
        Statement stmtCreateTimetable = null;

        try {
            DatabaseMetaData dbMeta = m_conODS.getMetaData();

            String[] strMetaDB2Token = {
                "TABLE"}; // Look only for tables
            boolean bTableThere = false; // flag to indicate if the table is currently present
            try {
                rs = dbMeta.getTables(null, m_strODSSchema, "TIMETABLE", strMetaDB2Token); //  Check for the table ...
                if (rs.next()) {
                    bTableThere = true; // We have found it...
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }

            if (bTableThere) {
                //Drop the table since we found it
                try {
                    stmtDropTimetable = m_conODS.createStatement();
                    m_dbPDH.debug(D.EBUG_INFO, "initializeTimeTable:DROPPING TIMETABLE");
                    stmtDropTimetable.execute(strDropTimeTableStmt);
                }
                finally {
                    stmtDropTimetable.close();
                }
            }
            else {
                m_dbPDH.debug(D.EBUG_INFO, "initializeTimeTable:TIMETABLE DROP BYPASSED");
            }

            try {
                m_dbPDH.debug(D.EBUG_INFO, "initializeTimeTable:CREATING NEW TIMETABLE");
                stmtCreateTimetable = m_conODS.createStatement();
                stmtCreateTimetable.execute(strCreateNewTimeTableStmt);
            }
            finally {
                stmtCreateTimetable.close();
            }
            m_conODS.commit();

        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "initializeTimeTable:ERROR:" + e.getMessage());
            System.exit(1);
        }

    } //End method initializeTimeTable

    /**
     *  This table is used to track if an ODS Table has been completed
     *  It can only be deleted if there contains no data.
     *  otherwise we are in restart mode and
     *  the table names in this table are a list of tables
     *  that need to be skipped
     */
    protected void initializeRestartTable() {

        String strCreateTableStmt = "CREATE TABLE " + m_strODSSchema +
            ".RESTARTABLE (RUNTIME TIMESTAMP NOT NULL, TABLENAME VARCHAR(32)) ";
        String strCountTableStmt = "SELECT COUNT(*) FROM " + m_strODSSchema + ".RESTARTABLE ";

        ResultSet rs = null;
        Statement stmt = null;
        Statement stmtCreatetable = null;

        try {
            DatabaseMetaData dbMeta = m_conODS.getMetaData();

            String[] strMetaDB2Token = {
                "TABLE"}; // Look only for tables
            boolean bTableThere = false; // flag to indicate if the table is currently present
            try {
                rs = dbMeta.getTables(null, m_strODSSchema, "RESTARTABLE", strMetaDB2Token); //  Check for the table ...
                if (rs.next()) {
                    bTableThere = true; // We have found it...
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
            }

            if (bTableThere) {
                int iCount = 0;
                try {
                    stmt = m_conODS.createStatement();
                    rs = stmt.executeQuery(strCountTableStmt);
                    if (rs.next()) {
                        iCount = rs.getInt(1);
                    }
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    stmt.close();
                }
                if (iCount > 0) {
                    m_bRestart = true;
                    m_dbPDH.debug(D.EBUG_INFO, "initializeRestartTable:RESTARTABLE  CLEARING BYPASSED -- It is not empty");
                }
            }
            else {
                m_dbPDH.debug(D.EBUG_INFO, "initializeRestartTable:CREATING NEW RESTARTABLE");
                try {
                    stmtCreatetable = m_conODS.createStatement();
                    stmtCreatetable.execute(strCreateTableStmt);
                }
                finally {
                    if (stmtCreatetable != null) {
                        stmtCreatetable.close();
                        stmtCreatetable = null;
                    }
                }
                m_conODS.commit();
            }
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "initializeRestartTable:ERROR:" + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * addToRestartTable
     *
     * @param _strTableName
     *  @author David Bigelow
     */
    protected void addToRestartTable(String _strTableName) {
        String strInsertTableStmt = "INSERT INTO " + m_strODSSchema + ".RESTARTABLE  VALUES(CURRENT TIMESTAMP, '" + _strTableName +
            "')";

        Statement stmtInserttable = null;

        try {
            m_dbPDH.debug(D.EBUG_INFO, "addToRestartTable:Inserting " + _strTableName + " INTO RESTARTABLE");
            try {
                stmtInserttable = m_conODS.createStatement();
                stmtInserttable.execute(strInsertTableStmt);
            }
            finally {
                if (stmtInserttable != null) {
                    stmtInserttable.close();
                    stmtInserttable = null;
                }
            }
            m_bRestart = false; // Done w/ restart
            m_conODS.commit();
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "addToRestartTable:ERROR:" + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * isInRestartTable
     *
     * @param _strTableName
     * @return
     *  @author David Bigelow
     */
    protected boolean isInRestartTable(String _strTableName) {
        String strCountTableStmt = "SELECT COUNT(*) FROM " + m_strODSSchema + ".RESTARTABLE  WHERE TABLENAME = '" + _strTableName +
            "'";

        Statement stmt = null;
        ResultSet rs = null;
        int iCount = 0;

        try {
            m_dbPDH.debug(D.EBUG_INFO, "isInRestartTable:checking " + _strTableName + " IN RESTARTABLE");
            try {
                stmt = m_conODS.createStatement();
                rs = stmt.executeQuery(strCountTableStmt);
                iCount = 0;
                if (rs.next()) {
                    iCount = rs.getInt(1);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
            return (iCount > 0);
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "isInRestartTable:ERROR:" + e.getMessage());
            System.exit(1);
        }
        return false;
    }

    /**
     * clearRestartTable
     *
     *  @author David Bigelow
     */
    protected void clearRestartTable() {

        String strDeleteTableStmt = "DELETE FROM " + m_strODSSchema + ".RESTARTABLE ";

        try {
            Statement stmtDeletetable = m_conODS.createStatement();
            m_dbPDH.debug(D.EBUG_INFO, "clearRestartTable:CLEARING OUT RESTARTABLE");
            try {
                stmtDeletetable.execute(strDeleteTableStmt);
            }
            finally {
                stmtDeletetable.close();
                stmtDeletetable = null;
            }
            m_conODS.commit();
            m_bRestart = false;
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "clearRestartTable:ERROR:" + e.getMessage());
            System.exit(1);
        }

    }

    /**
     * setTimestampInTimetable
     *
     *  @author David Bigelow
     */
    protected void setTimestampInTimetable() {
        String strInsertTimestampStmt = "INSERT INTO " + m_strODSSchema + ".TIMETABLE " + "(RUNTIME,RUNTYPE) VALUES('" + m_strNow +
            "','I')";
        try {
            Statement stmtInsertTimeStamp = m_conODS.createStatement();
            try {
                stmtInsertTimeStamp.execute(strInsertTimestampStmt);
            }
            finally {
                stmtInsertTimeStamp.close();
                stmtInsertTimeStamp = null;
            }
            m_conODS.commit();
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "setTimestampInTimetable:ERROR:Insert into timetable:" + e.getMessage());
            System.exit( -1);
        }

    }

    /**
     * getLastRuntime
     *
     * @return
     *  @author David Bigelow
     */
    protected String getLastRuntime() {

        String strStatement = "SELECT RUNTIME FROM " + m_strODSSchema + ".TIMETABLE ORDER BY RUNTIME DESC";
        String strAnswer = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            try {
                stmt = m_conODS.createStatement();
                rs = stmt.executeQuery(strStatement);
                if (rs.next()) {
                    strAnswer = rs.getString(1).trim();
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }

        }
        catch (SQLException se) {
            m_dbPDH.debug(D.EBUG_ERR, "getLastRuntime:ERROR:" + se.getMessage());
            System.exit( -1);
        }
        return strAnswer;
    }

    /**
     *  This will update the ODS table with data from the corresponding PDH
     *  entities which have changed
     *
     *@param  _eg                      EntityGroup object for the ODS Table
     *@exception  SQLException
     *@exception  MiddlewareException
     */
    //protected void updateODSTable(EntityGroup _eg) throws SQLException,
    //  MiddlewareException {
    protected void updateODSTable(EntityGroup _eg) {

        int iStartID = 0;
        int iEndID = 0;
        int iMaxID = 0;
        int iEntityCount = 0;

        boolean bLoop = false;
        boolean bAttSubSet = true;

        String strDeleteFLAGSQL = null;
        String strDeleteEntitySQL = null;
        String strInsertFLAGSQL = null;
        String strInsertDLOGSQL = null;
        String strGetExistingRel = null;
        String strGetExistingNLS = null;
        String strDeleteEntityNLSSQL = null;

        // Do some prepared statements here..
        PreparedStatement psFlag = null;
        PreparedStatement psFlagDel = null;
        PreparedStatement psDEL = null;
        PreparedStatement psDLOG = null;
        PreparedStatement psGetNLS = null;
        PreparedStatement psDELNLS = null;
        PreparedStatement psExistRel = null;

        String strUpdateHeadSQL = null;
        String strUpdateFootSQL = null;
        String strUpdateFIELDS = null;

        String strInsertSQL = null;
        String strUpdateSQL = null;

        String strInsertHEADSQL = null;
        String strInsertFIELDSSQL = null;
        String strInsertMARKERSQL = null;

        String strInsertFIELDS = "";
        String strInsertMARKER = "";

        EANList elMulti = new EANList();
        EANList elTrans = new EANList();

        EntityGroup eg1 = null;
        EntityGroup eg2 = null;

        String strEntityType = _eg.getEntityType();
        String strEntity1Type = null;
        String strEntity2Type = null;

        String strEnterprise = m_prof.getEnterprise();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        ReturnStatus returnStatus = new ReturnStatus( -1);

        int[] iAllNLS = new int[10];
        String[] strCountryCode = new String[10];
        String[] strLanguageCode = new String[10];
        String[] strCountryList = new String[10];

        Hashtable hCatNavs = new Hashtable();
        NLSItem nlsCurrent = null;

        boolean bCatNavFound = false;
        String strCountryFilterAttribute = CatODSServerProperties.getCountryFilterAttribute(m_strODSSchema,_eg.getEntityType());
        boolean bhasCountryFilter = ((strCountryFilterAttribute.length() > 0 ) ? true :false);

        // Lets set the rules to lax here
        m_df.setLenient(true);

        // O.K.  lets strip this information from some international flags..
        // Some flags can only be us. english only
        setEnglishOnlyFlags(_eg);

        System.gc();

        try {
            String strGeneralArea = CatalogProperties.getGeneralAreaKey(this.getClass());
            Database db = getCatalog().getCatalogDatabase();
            GeneralAreaMap gam = getCatalog().getGam();

            GeneralAreaMapGroup gamp = gam.lookupGeneralArea(strGeneralArea);
            Enumeration en = gamp.elements();

            if (!en.hasMoreElements()) {
                System.out.println("no gami to find!!!");
            }

            //Allocate all the working NLS for this catalog Entity
            while (en.hasMoreElements()) {

                Iterator it = null;

                GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
                Vector vct = m_prof.getReadLanguages();
                if (vct.size() == 0) {
                    m_dbPDH.debug(D.EBUG_DETAIL, "WARNING!, No NLS's returned from gami!!!");

                }
                for (int i = 00; i < vct.size(); i++) {
                    NLSItem nlsi = (NLSItem) vct.elementAt(i);
                    if (nlsi.getNLSID() == gami.getNLSID()) {
                        strCountryCode[i] = gami.getCountry();
                        strLanguageCode[i] = gami.getLanguage();
                        strCountryList[i] = gami.getCountryList();
                        iAllNLS[i] = nlsi.getNLSID();
                        m_dbPDH.debug(D.EBUG_SPEW, "Add Available NLS for this entity :" + nlsi.getNLSID());
                    }
                }
            }

            int iSessionID = m_dbPDH.getNewSessionID();
            //Now get the max rows we have to retrieve for this entity
            m_dbPDH.debug(D.EBUG_DETAIL,
                          "gbl9003:params:" + strEnterprise + ":" + strEntityType + ":" + m_strODSSchema + ":" + m_strLastRun + ":" +
                          m_strNow);
            try {
                rs = m_dbPDH.callGBL9003(returnStatus, strEnterprise, strEntityType, m_strODSSchema, m_strLastRun, m_strNow);
                if (rs.next()) {
                    iMaxID = rs.getInt(1);
                    iStartID = rs.getInt(2);
                    iEntityCount = rs.getInt(3);
                    m_dbPDH.debug(D.EBUG_DETAIL, "gbl9003:answer: Max:" + iMaxID + ":Start:" + iStartID + ":Rows:" + iEntityCount);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                m_dbPDH.commit();
                m_dbPDH.freeStatement();
                m_dbPDH.isPending();
            }

            // New iEndID calculations
            iEndID = ( (iStartID + m_iChuckSize) > iMaxID ? iMaxID : iStartID + m_iChuckSize);
            bLoop = iStartID <= iMaxID;

            if (_eg.isRelator()) {
                strEntity1Type = _eg.getEntity1Type();
                strEntity2Type = _eg.getEntity2Type();
            }

            strDeleteFLAGSQL = "DELETE FROM " + m_strODSSchema + ".FLAG " + " WHERE EntityType = ? AND EntityID = ?";
            strDeleteEntitySQL = "UPDATE  " + m_strODSSchema + "." + strEntityType +
                " SET VALFROM = ?, ISACTIVE = 0 WHERE  EntityID = ?";

            strInsertFLAGSQL = "INSERT  INTO " + m_strODSSchema + ".FLAG " +
                " (VALFROM,ENTITYTYPE,ENTITYID, NLSID, ATTRIBUTECODE, FLAGCODE,SFVALUE,FLAGDESCRIPTION) " +
                " VALUES (?,?,?,?,?,?,?,?)";

            strInsertDLOGSQL = "INSERT  INTO " + m_strODSSchema + ".DELETELOG " +
                " (TYPE, ENTITYTYPE, ENTITYID, ENTITY1TYPE, ENTITY1ID, ENTITY2TYPE, ENTITY2ID) " + " VALUES (?,?,?,?,?,?,?)";

            strGetExistingRel = "SELECT COUNT(*) FROM " + m_strODSSchema + "." + strEntityType +
                " WHERE ENTITYID <> ? AND ID1 = ? and ID2 = ?";
            strGetExistingNLS = "SELECT DISTINCT NLSID FROM " + m_strODSSchema + "." + strEntityType + " WHERE ENTITYID = ?";
            strDeleteEntityNLSSQL = "DELETE FROM " + m_strODSSchema + "." + strEntityType + " WHERE  EntityID = ? and NLSID = ?";

            // Do some prepared statements here..
            psFlag = m_conODS.prepareStatement(strInsertFLAGSQL);
            psFlagDel = m_conODS.prepareStatement(strDeleteFLAGSQL);
            psDLOG = m_conODS.prepareStatement(strInsertDLOGSQL);
            psDEL = m_conODS.prepareStatement(strDeleteEntitySQL);
            psGetNLS = m_conODS.prepareStatement(strGetExistingNLS);
            psDELNLS = m_conODS.prepareStatement(strDeleteEntityNLSSQL);
            psExistRel = m_conODS.prepareStatement(strGetExistingRel);

            MultiRowAttrGroup cng = new MultiRowAttrGroup(getCatalog(), strEntityType);
            for (int i = 0; i < cng.getItemCount(); i++) {
                MultiRowAttrItem cni = cng.getItem(i);
                String strAttrCode = cni.getColumnKey().trim(); //Get the attributecodes
                //Store in hashtable
                if (!hCatNavs.containsKey(strAttrCode)) {
                    hCatNavs.put(strAttrCode, "We processed this attribute");
                    D.ebug(D.EBUG_SPEW,
                           "CatODSMethods.updateODSTable:ET:" + strEntityType + ": Adding to hashtable Attribute: " + strAttrCode);

                }

            }

            System.gc();
            if (_eg.isRelator()) {
                eg1 = new EntityGroup(null, m_dbPDH, m_prof, strEntity1Type, "No Atts");
                eg2 = new EntityGroup(null, m_dbPDH, m_prof, strEntity2Type, "No Atts");
            }

            while (bLoop) {

                //Get the entities which will fulfill the filter criteria set in the meta for this entity
                m_dbPDH.debug(D.EBUG_DETAIL,
                              "gbl9005:params:" + iSessionID + ":" + strEnterprise + ":" + strEntityType + ":" + m_strODSSchema +
                              ":" + iStartID + ":" + iEndID + ":" + m_strLastRun + ":" + m_strNow);
                rs = m_dbPDH.callGBL9005(returnStatus, iSessionID, strEnterprise, strEntityType, m_strODSSchema, iStartID, iEndID,
                                         m_strLastRun, m_strNow);
                rdrs = new ReturnDataResultSet(rs);
                rs.close();
                m_dbPDH.commit();
                rs = null;
                m_dbPDH.freeStatement();
                m_dbPDH.isPending();

                // O.K.  Lets tuck all the entity items into the group
                for (int iEcount = 0; iEcount < rdrs.size(); iEcount++) {

                    EntityItem ei1 = null;
                    EntityItem ei2 = null;
                    EntityItem ei = null;

                    int iEntity1ID = rdrs.getColumnInt(iEcount, 0);
                    int iEntityID = rdrs.getColumnInt(iEcount, 1);
                    int iEntity2ID = rdrs.getColumnInt(iEcount, 2);
                    String strValFrom = rdrs.getColumnDate(iEcount, 3);
                    String strEffTo = rdrs.getColumnDate(iEcount, 4);
                    m_dbPDH.debug(D.EBUG_DETAIL,
                                  "gbl9005:answers:" + iEntity1ID + ":" + iEntityID + ":" + iEntity2ID + ":" + strValFrom + ":" +
                                  strEffTo);
                    if (!_eg.containsEntityItem(strEntityType, iEntityID) &&
                        ( (_eg.isRelator() && iEntity1ID > 0 && iEntity2ID > 0) || !_eg.isRelator())) {
                        ei = new EntityItem(_eg, null, strEntityType, iEntityID);
                        // If this date is not forever set active for this guy to false..
                        ei.setActive(strEffTo.equals(m_strForever));
                        _eg.putEntityItem(ei);
                    }
                    if (_eg.isRelator() && iEntity1ID > 0 && iEntity2ID > 0) {
                        ei1 = new EntityItem(eg1, null, strEntity1Type, iEntity1ID);
                        eg1.putEntityItem(ei1);
                        ei2 = new EntityItem(eg2, null, strEntity2Type, iEntity2ID);
                        eg2.putEntityItem(ei2);
                        ei.putUpLink(ei1);
                        ei.putDownLink(ei2);
                    }
                }

                // Now ... we fill out all the attributes if anything was populated
                // from above
                //
                // only look for subsets if we need to

                if (rdrs.size() > 0) {
                    popAllAttributeValues(_eg, iSessionID, bAttSubSet);
                }

                // Now remove all the records to clean up after yourself
                // We need a simpler way to do this

                m_dbPDH.callGBL8105(returnStatus, iSessionID);
                m_dbPDH.commit();
                m_dbPDH.freeStatement();
                m_dbPDH.isPending();

                strUpdateHeadSQL = "UPDATE " + m_strODSSchema + "." + strEntityType + " SET ISACTIVE=1,";
                strUpdateFootSQL = "  VALFROM = ? WHERE EntityID = ? AND NLSID = ?";

                //If Multirowattributes are present for this entity, they will be part of the key,
                // so have to modify the WHERE clause

                //Get the multirow  attributes..they have to be part of the key
//        D.ebug(D.EBUG_SPEW,"Before Changing footer for  multirow Attribute");
                for (int i = 0; i < cng.getItemCount(); i++) {
                    MultiRowAttrItem cni = cng.getItem(i);
                    String strAttrCode = cni.getColumnKey().trim(); //Get the attributecodes
                    EANMetaAttribute ma = _eg.getMetaAttribute(cni.getColumnKey());
                    D.ebug(D.EBUG_SPEW, "Getting multirow Attribute" + strAttrCode + ":" + ma.getAttributeCode());
                    if (ma.getAttributeType().equals("X") || ma.getAttributeType().equals("L")) {
                        //do nothing for Longtext attr
                        D.ebug(D.EBUG_SPEW, "THIS ATTRIBUTE IS NOT THE RIGHT TYPE!!");
                    }
                    else {
                        strUpdateFootSQL += " AND " + strAttrCode + " = ?";
                        ///            D.ebug(D.EBUG_SPEW,"Changing footer #1 for  multirow Attribute"+strAttrCode );
                        if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S") ||
                            ma.getAttributeType().equals("F")) {
                            strUpdateFootSQL += " AND " + strAttrCode + "_FC = ?";
//                D.ebug(D.EBUG_SPEW,"Changing footer #2 for  multirow Attribute"+strAttrCode );
                        }
                    }

                }

                strUpdateFIELDS = "";

                strInsertSQL = "";
                strUpdateSQL = "";
                strInsertHEADSQL = "INSERT INTO " + m_strODSSchema + "." + strEntityType;
                strInsertFIELDSSQL = (_eg.isRelator() ?
                                      " (EntityID, NLSID, ID1, ID2, VALFROM, VALTO,ISACTIVE,COUNTRYCODE,LANGUAGECODE,COUNTRYLIST" :
                                      " (EntityID, NLSID, VALFROM, VALTO,ISACTIVE,COUNTRYCODE,LANGUAGECODE,COUNTRYLIST");
                strInsertMARKERSQL = (_eg.isRelator() ? " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?" : " VALUES (?, ?, ?, ?, ?, ?, ?, ?");

                strInsertFIELDS = "";
                strInsertMARKER = "";

                // Here we build the rest of the SQL Statement
                for (int iEcount = 0; iEcount < _eg.getEntityItemCount(); iEcount++) {

                    EntityItem ei = _eg.getEntityItem(iEcount);

                    D.ebug(D.EBUG_SPEW,
                           "Processing EntityItem:" + ei.getEntityType() + ":" + ei.getEntityID() + " is active?" + ei.isActive());

                    if (CatODSServerProperties.excludeEntityFromUpdate(m_strODSSchema, ei)) {
                        D.ebug(D.EBUG_DETAIL,
                               "CatODSMethods.updateODSTable:excludeEntityFromUpdate:ET:" + ei.getEntityType() + ":EID:" +
                               ei.getEntityID());
                        D.ebug(D.EBUG_DETAIL,
                               "CatODSMethods.updateODSTable:excludeEntityFromUpdate:Deleting Entity:" + ei.getEntityType() +
                               ":EID:" + ei.getEntityID());
                        psDEL.setString(1, m_strNow);
                        psDEL.setInt(2, ei.getEntityID());
                        psDEL.execute();
                        continue;
                    }



                    for (int inls = 0; inls < iAllNLS.length; inls++) { //Start processing the special attributes using nls
                        if (iAllNLS[inls] == 0) {
                            continue;
                        }
                        //Now get all the flag descriptions corresponding to its nls

                        nlsCurrent = new NLSItem(iAllNLS[inls], "Current translation");
                        m_prof.setReadLanguage(nlsCurrent);

                    }

                    //
                    try {
                        // O.K.  Lets try to delete it and all multi flags and everything else we are can remove
                        // Right here..
                        // If it is active.. we will put it back
                        D.ebug(D.EBUG_DETAIL, "CatODSMethods.updateODSTable:ET:" + ei.getEntityType() + ":EID:" + ei.getEntityID());
                        try {
                            psFlagDel.setString(1, strEntityType);
                            psFlagDel.setInt(2, ei.getEntityID());
                            D.ebug(D.EBUG_DETAIL,
                                   "CatODSMethods.updateODSTable:Deleting MultiFlagValues Entity info for (" + ei.getEntityType() +
                                   ":" + ei.getEntityID() + ") " + ei.toString());
                            psFlagDel.execute();
                        }
                        catch (SQLException ex) {
                            D.ebug(D.EBUG_ERR, "CatODSMethods.updateODSTable.Warning on Flags:" + ex.getMessage());
                        }

                        //We reset the entity (there may be multiple rows for them)
                        //when we find it and then reactivate them when we update
                        //that way we dont need to know which nls is deleted etc.

                        try {
                            if (!ei.isActive()) {
                                D.ebug(D.EBUG_DETAIL,
                                       "CatODSMethods.updateODSTable:Deleting Deactivated Entity:" + ei.getEntityType() + ":EID:" +
                                       ei.getEntityID());
                            }
                            else {
                                D.ebug(D.EBUG_DETAIL,
                                       "CatODSMethods.updateODSTable:Resetting  Entity:" + ei.getEntityType() + ":EID:" +
                                       ei.getEntityID());

                            }
                            psDEL.setString(1, m_strNow);
                            psDEL.setInt(2, ei.getEntityID());
                            psDEL.execute();
                        }
                        catch (SQLException ex) {
                            D.ebug(D.EBUG_ERR,
                                   "CatODSMethods.updateODSTable.Warning on Delete/Unpublish/Reset Entity:" + ex.getMessage());
                        }

                        //
                        // O.K.  are we a prod Attr Relator?
                        // Here .. we only remove if its the last one in the prodattr relator table...
                        //


                        if (!ei.isActive()) {
                            // Do we delete, hell yeah



                            // Finally, tagg the delete log for anyone who needs to know..
                            D.ebug(D.EBUG_DETAIL,
                                   "CatODSMethods.updateODSTable:Logging Delete for :" + ei.getEntityType() + ":EID:" +
                                   ei.getEntityID());

                            try {
                                psDLOG.setString(1, (_eg.isRelator() ? "RELATOR" : "ENTITY"));
                                psDLOG.setString(2, ei.getEntityType());
                                psDLOG.setInt(3, ei.getEntityID());
                                if (_eg.isRelator()) {
                                    int iEntity1ID = ( (EntityItem) ei.getUpLink(0)).getEntityID();
                                    int iEntity2ID = ( (EntityItem) ei.getDownLink(0)).getEntityID();
                                    strEntity1Type = _eg.getEntity1Type();
                                    strEntity2Type = _eg.getEntity2Type();
                                    psDLOG.setString(4, strEntity1Type);
                                    psDLOG.setInt(5, iEntity1ID);
                                    psDLOG.setString(6, strEntity2Type);
                                    psDLOG.setInt(7, iEntity2ID);
                                }
                                else {
                                    psDLOG.setString(4, null);
                                    psDLOG.setInt(5, 0);
                                    psDLOG.setString(6, null);
                                    psDLOG.setInt(7, 0);
                                }
                                psDLOG.execute();
                            }
                            catch (SQLException ex) {
                                D.ebug(D.EBUG_ERR,
                                       "CatODSMethods.updateODSTable.Warning on Inserting Delete Image:" + ex.getMessage());
                            }

                        }
                        else {
                            //Not a delete? so that means update/insert

                            nlsCurrent = null;

                            elMulti = new EANList();
                            elTrans = new EANList();

                            //This will have to be repeated for all the languages enabled for the entity TBD
                            for (int il = 0; il < iAllNLS.length; il++) {

                                if (iAllNLS[il] == 0) {
                                    continue;
                                }

                                if (bhasCountryFilter) {
                                  if (!isCountryFilterMatch(strCountryFilterAttribute,ei,strCountryList[il])) {
                                    D.ebug(this,D.EBUG_DETAIL,"Discarding "+ei.getEntityType()+":"+ei.getEntityID()+":"+iAllNLS[il]+" as value of "+strCountryFilterAttribute+ " DOES NOT match "+strCountryList[il]);
                                           continue;
                                  }
                                }


                                boolean bEntityExists = false;
                                int iz = 1;

                                PreparedStatement ps = null;

                                if (entityExistsInODS(ei.getEntityType(), ei.getEntityID(), iAllNLS[il])) {
                                    bEntityExists = true;
                                    strUpdateFIELDS = "";
                                }
                                else {
                                    bEntityExists = false;
                                    strInsertFIELDS = "";
                                    strInsertMARKER = "";
                                }

                                D.ebug(D.EBUG_DETAIL,
                                       "CatODSMethods.updateODSTable:" + (bEntityExists ? "Updating " : "Inserting ") + "Entity " +
                                       ei.getEntityType() + ":EID:" + ei.getEntityID() + ":" + iAllNLS[il]);

                                bCatNavFound = false;
                                // This loop needs to be by metaattribute..updating things to null that do not have an attribute
                                for (int iy = 0; iy < _eg.getMetaAttributeCount(); iy++) {
                                    try {
                                        EANMetaAttribute ma = _eg.getMetaAttribute(iy);
                                        EANAttribute att = null;
                                        if (isDerivedEntityID(ma)) {
                                            continue;
                                        }

                                        att = ei.getAttribute(ma.getAttributeCode());
                                        // a null value here means an expired attribute value!
                                        if (att != null && (att.get() == null || att.get().equals(""))) {
                                            D.ebug(D.EBUG_DETAIL,
                                                "CatODSMethods.updateODSTable:removing attribute w/ null value:EI" +
                                                ei.getEntityType() + ":" + ei.getEntityID() + ":" + att.getAttributeCode() + ":" +
                                                att.getValFrom());
                                            if (!ei.getEntityType().equals("CATLGPUB") || !att.getAttributeCode().equals("PUBTO")) {
                                            ei.removeAttribute(att);
                                            }
                                        }

                                        if (att != null) {

                                            D.ebug(D.EBUG_SPEW,
                                                "Processing attribute:" + att.getAttributeCode() + ": for NLS :" + iAllNLS[il]);

                                            //Special processing for CATNAV attrs..ignoring whether its a Multiflag attr..we got
                                            // to include all values for it when its selected
                                            att.getAttributeCode().trim();

                                            if (hCatNavs.containsKey(att.getAttributeCode().trim())) {
                                                bCatNavFound = true;
                                                D.ebug(D.EBUG_SPEW,
                                                    "AC#COUNT0#:" + iy + ":" + (att == null ? "no att" : att.getAttributeCode()));
                                                if (bEntityExists) {
                                                    //multirow attributes are table keys, so no need to update
                                                }
                                                else {
                                                    strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode();
                                                    strInsertMARKER = strInsertMARKER + ", ?";
                                                    if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")||
                                                        ma.getAttributeType().equals("F")) {
                                                        strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode() + "_FC";
                                                        strInsertMARKER = strInsertMARKER + ", ?";
                                                    }
                                                }

                                            }
                                            else if (includeColumn(ma)) {
                                                D.ebug(D.EBUG_SPEW,
                                                    "AC#COUNT1#:" + iy + ":" + (att == null ? "no att" : att.getAttributeCode()));
                                                if (bEntityExists) {
                                                    strUpdateFIELDS = (strUpdateFIELDS.length() > 0) ?
                                                        strUpdateFIELDS + ", " + ma.getAttributeCode() + " = ? " :
                                                        " " + ma.getAttributeCode() + " = ?";
                                                    if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                                                        strUpdateFIELDS = (strUpdateFIELDS.length() > 0) ?
                                                            strUpdateFIELDS + ", " + ma.getAttributeCode() + "_FC = ? " :
                                                            " " + ma.getAttributeCode() + "_FC = ?";
                                                    }
                                                }
                                                else {
                                                    strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode();
                                                    strInsertMARKER = strInsertMARKER + ", ?";
                                                    if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                                                        strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode() + "_FC";
                                                        strInsertMARKER = strInsertMARKER + ", ?";
                                                    }
                                                }
                                            }
                                        }

                                    }
                                    catch (NullPointerException ex2) {
                                        D.ebug(D.EBUG_SPEW, "aaaaaaaaaaaaaaaaaaaaaa HHHHHH");
                                        ex2.printStackTrace();
                                    }
                                }

                                if (bEntityExists) {
                                    strUpdateSQL = strUpdateHeadSQL + (strUpdateFIELDS.length() > 0 ? strUpdateFIELDS + "," : " ") +
                                        strUpdateFootSQL;
                                    D.ebug(D.EBUG_SPEW, "CatODSMethods.updateODSTable:" + strUpdateSQL);
                                    ps = m_conODS.prepareStatement(strUpdateSQL);
                                }
                                else {
                                    strInsertSQL = strInsertHEADSQL + strInsertFIELDSSQL + strInsertFIELDS + ")" +
                                        strInsertMARKERSQL + strInsertMARKER + ")";
                                    D.ebug(D.EBUG_SPEW, "CatODSMethods.updateODSTable:" + strInsertSQL);
                                    ps = m_conODS.prepareStatement(strInsertSQL);
                                }

                                //Set the NLSid to be read in the profile so that we retrieve the correct translations
                                nlsCurrent = new NLSItem(iAllNLS[il], "Current translation");
                                m_prof.setReadLanguage(nlsCurrent);
                                D.ebug(D.EBUG_INFO,
                                       "Setting readLanguage for Entity :" + ei.getEntityType() + ":" + ei.getEntityID() + ": to :" +
                                       iAllNLS[il]);

                                iz = 1;

                                if (!bEntityExists) {

                                    ps.setInt(1, ei.getEntityID());
                                    ps.setInt(2, iAllNLS[il]); // Setting the ODS NLS here
                                    if (_eg.isRelator()) {
                                        EntityItem eip = (EntityItem) ei.getUpLink(0);
                                        EntityItem eic = (EntityItem) ei.getDownLink(0);

                                        ps.setInt(3, (eip == null ? -1 : eip.getEntityID()));
                                        ps.setInt(4, (eic == null ? -1 : eic.getEntityID()));

                                        ps.setString(5, m_strNow);
                                        ps.setString(6, m_strForever);
                                        ps.setInt(7, 1);
                                        iz = 8;
                                    }
                                    else {
                                        ps.setString(3, m_strNow);
                                        ps.setString(4, m_strForever);
                                        ps.setInt(5, 1);
                                        iz = 6;
                                    }
                                    ps.setString(iz, strCountryCode[il]);
                                    iz++;
                                    ps.setString(iz, strLanguageCode[il]);
                                    iz++;
                                    ps.setString(iz, strCountryList[il]);
                                    iz++;

                                }

                                // Lets get the attributes here

                                for (int iy = 0; iy < _eg.getMetaAttributeCount(); iy++) {
                                    EANMetaAttribute ma = _eg.getMetaAttribute(iy);
                                    EANAttribute att = ei.getAttribute(ma.getAttributeCode());
                                    if (att == null) {
                                        D.ebug(D.EBUG_SPEW, "Yikes! att is NULL");
                                        continue;
                                    }
                                    D.ebug(D.EBUG_SPEW, "Checking attribute " + att.getAttributeCode());

                                    if (il == 0) {
                                        if (ma.getAttributeType().equals("F") && att != null) {
                                            elMulti.put(att);
                                        }
                                    }

                                    // Now lets build the table columns for the table row update

                                    String atttostring = att.toString();
                                    boolean CatlgpubNullPubto = false;
                                    if (ei.getEntityType().equals("CATLGPUB") && att.getAttributeCode().equals("PUBTO") && 
                                    		atttostring.length() == 0) {
                                    	atttostring = "2999-12-31";
                                    	CatlgpubNullPubto = true;
                                    }
                                    
                                    
                                    if (includeColumn(ma) && atttostring.length() > 0 &&
                                        !hCatNavs.containsKey(att.getAttributeCode().trim())) {

                                        D.ebug(D.EBUG_SPEW,
                                               "AC#COUNT2#:" + iy + ":" + ma.getAttributeCode() + ":" + ma.getAttributeType() +
                                               ":IN:" + ma.isInteger() + ":DT:" + ma.isDate() + ":AL:" + ma.isAlpha() + ":" +
                                               (att == null ? "null" : att.toString()));

                                        if (ma.isDate() && ma.getAttributeType().equals("T")) {
                                            try {
                                                if (att != null) {
                                                    if (att.toString().trim().toUpperCase().equals("OPEN") ||
                                                        att.toString().trim().equals("Open")) {
                                                        D.ebug(D.EBUG_ERR,
                                                            "**DATE IN " + ma.getAttributeCode() + " -- Set to OPEN in PDH, setting to null in ODS");
                                                        ps.setString(iz, null);
                                                    }
                                                    else {
                                                    	if (CatlgpubNullPubto) {
                                                    		ps.setString(iz,atttostring);
                                                    	}
                                                    	else {
                                                        ps.setString(iz, getODSString(att));
                                                    	} 
                                                    }
                                                }
                                                else {
                                                    ps.setString(iz, null);
                                                }
                                                D.ebug(D.EBUG_SPEW,
                                                    "Setting Date/String attribute " + att.getAttributeCode() + ":" + iz);
                                            }
                                            catch (Exception dx) {
                                                D.ebug(D.EBUG_ERR,
                                                    "**PARAMETER SET ERROR:  Date format Exception for (" + ei.getEntityType() +
                                                    ":" + ei.getEntityID() + ":" + ma.getAttributeCode() + ") - Value (" +
                                                    (att == null ? "null" : att.toString()) + ").  Using null." + ":" +
                                                    dx.getMessage());
                                                ps.setString(iz, null);
                                            }
                                        }
                                        else if (ma.isInteger() && !ma.isAlpha() && ma.getAttributeType().equals("T")) {
                                            try {
                                                if (att != null) {
                                                    ps.setInt(iz, Integer.valueOf(att.toString()).intValue());
                                                }
                                                else {
                                                    ps.setInt(iz, 0);
                                                }
                                            }
                                            catch (NumberFormatException nx) {
                                                D.ebug(D.EBUG_ERR,
                                                    "**PARAMETER SET ERROR:  Number format Exception for (" + ei.getEntityType() +
                                                    ":" + ei.getEntityID() + ":" + ma.getAttributeCode() + ") - Value (" +
                                                    (att == null ? "null" : att.toString()) + ").  Using zero(0) instead." + ":" +
                                                    nx.getMessage());
                                                ps.setInt(iz, 0);
                                            }
                                            D.ebug(D.EBUG_SPEW, "Setting Int attribute " + att.getAttributeCode() + ":" + iz);
                                        }
                                        else {
                                            try {
                                                if (att != null) {
                                                    ps.setString(iz, getODSString(att));
                                                }
                                                else {
                                                    ps.setString(iz, null);
                                                }
                                                D.ebug(D.EBUG_SPEW, "Setting other attribute " + att.getAttributeCode() + ":" + iz);
                                            }
                                            catch (SQLException nx) {
                                                D.ebug(D.EBUG_ERR,
                                                    "**PARAMETER SET ERROR: String SQLError for (" + ei.getEntityType() + ":" +
                                                    ei.getEntityID() + ":" + ma.getAttributeCode() + ") - Value (" +
                                                    (att == null ? "null" : att.toString()) + ").  Using null instead." + ":" +
                                                    nx.getMessage());
                                                ps.setString(iz, null);
                                            }
                                        }

                                        iz++;

                                        // Lets look for the flag code option
                                        //
                                        if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                                            if (att != null) {
                                                EANFlagAttribute fa = (EANFlagAttribute) att;
                                                try {
                                                    ps.setString(iz, fa.getFirstActiveFlagCode());
                                                }
                                                catch (SQLException nx) {
                                                    D.ebug(D.EBUG_ERR,
                                                        "**PARAMETER SET ERROR: FC SQLError for (" + ei.getEntityType() + ":" +
                                                        ei.getEntityID() + ":" + ma.getAttributeCode() + ") - Value (" +
                                                        (att == null ? "null" : att.toString()) + ").  Using null instead." + ":" +
                                                        nx.getMessage());
                                                    ps.setString(iz, null);
                                                }
                                            }
                                            else {
                                                try {
                                                    ps.setString(iz, null);
                                                }
                                                catch (SQLException nx) {
                                                    D.ebug(D.EBUG_ERR,
                                                        "**PARAMETER SET ERROR: FC SQLError for (" + ei.getEntityType() + ":" +
                                                        ei.getEntityID() + ":" + ma.getAttributeCode() + ") - Value (" +
                                                        (att == null ? "null" : att.toString()) + ").  Using null instead." + ":" +
                                                        nx.getMessage());
                                                    ps.setString(iz, null);
                                                }
                                            }
                                            D.ebug(D.EBUG_SPEW,
                                                "Setting flag attribute code " + att.getAttributeCode() + "_FC" + ":" + iz);
                                            iz++;
                                        }

                                    }
                                    else if (hCatNavs.containsKey(att.getAttributeCode().trim())) {
                                        D.ebug(D.EBUG_SPEW, "CATNAVATTR:Storing position for :" + att.getAttributeCode() + ":" + iz);
                                        hCatNavs.remove(att.getAttributeCode().trim()); //Store value of position in hashtable
                                        hCatNavs.put(att.getAttributeCode().trim(), iz + "");
                                        if (!bEntityExists) {
                                          iz++; //Increment only if insert, skip otherwise
                                          iz++; //Increment again to account for _FC
                                        }
                                    }

                                }

                                Enumeration eCatNavs = hCatNavs.keys();
                                ArrayList alAllValues = new ArrayList();
                                ArrayList alFlagValues = new ArrayList();
                                ArrayList alAllFlagCodes = new ArrayList();
                                ArrayList alFlagCodes = new ArrayList();
                                String[] strCatNavColumns = new String[hCatNavs.size()];

                                //Way too complex....gotta find a way to simplify this
                                if (bCatNavFound) {
                                    int i = 0;
                                    //Get all the FlagValues and store it
                                    while (eCatNavs.hasMoreElements()) {
                                        String strCatNavAttr = (String) eCatNavs.nextElement();
                                        strCatNavColumns[i] = strCatNavAttr;
                                        i++;
                                        int iPosition = Integer.valueOf( (String) hCatNavs.get(strCatNavAttr)).intValue();
                                        D.ebug(D.EBUG_SPEW, "CATNAVATTR:Retrieving position for :" + strCatNavAttr);
                                        EANAttribute att = ei.getAttribute(strCatNavAttr);
                                        EANMetaAttribute ma = att.getMetaAttribute();
                                        D.ebug(D.EBUG_SPEW, "CATNAVATTR:" + iPosition + ":" + att.getAttributeCode().trim());
                                        String strFlagValues = "";
                                        String strFlagCode = "";
                                        switch (ma.getAttributeType().charAt(0)) {
                                            case 'F':
                                            case 'U':
                                            case 'S':
                                                MetaFlag[] mfAttr = (MetaFlag[]) att.get();
                                                for (int k = 0; k < mfAttr.length; k++) {
                                                    if (mfAttr[k].isSelected()) { //Store this
                                                        strFlagValues = mfAttr[k].getLongDescription().trim();
                                                        D.ebug(D.EBUG_SPEW, "CATNAVATTR:Storing Flagvalue:"+strFlagValues);
                                                        alFlagValues.add(strFlagValues);
                                                        strFlagCode = mfAttr[k].getFlagCode();
                                                        D.ebug(D.EBUG_SPEW, "CATNAVATTR:Storing Flagcode:"+strFlagCode);
                                                        alFlagCodes.add(strFlagCode);
                                                    }
                                                }

                                                break;
                                            default:

                                        }
                                        D.ebug(D.EBUG_SPEW, "CATNAVATTR:Store to ArrayList");
                                        alAllValues.add(alFlagValues);
                                        alAllFlagCodes.add(alFlagCodes);

                                    }

                                    //Create the 2d array we want with initial size
                                    String[][] strAllFlagValues = new String[alAllValues.size()][];
                                    convertToArray(strAllFlagValues, alAllValues);

                                    String[][] strAllFlagCodes = new String[alAllValues.size()][];
                                    convertToArray(strAllFlagCodes, alAllFlagCodes);

                                    //Set up the answer array which holds the combination of values from the flags
                                    int[] iResultDim = getCombinationSize(strAllFlagValues);
                                    String[] strFlagValueResult = new String[iResultDim[0]];
                                    String[] strFlagCodeResult = new String[iResultDim[0]];

                                    //Process the array here and insert the rows

                                    iLevel = 1;
                                    computePermutations(strFlagValueResult, strAllFlagValues, 0, "");

                                    iLevel = 1;
                                    computePermutations(strFlagCodeResult, strAllFlagCodes, 0, "");

                                    //Here we go!
                                    for (int k = 0; k < strFlagValueResult.length; k++) {
                                        String strValues = strFlagValueResult[k];
                                        String strFlagCodes = strFlagCodeResult[k];
                                        StringTokenizer st = new StringTokenizer(strValues, ",");
                                        StringTokenizer st1 = new StringTokenizer(strFlagCodes, ",");
                                        int iCol = 0;
                                        String strCatNavAttr = null;
                                        String strFlagValue = null;
                                        String strFlagCode = null;
                                        while (st.hasMoreTokens()) {
                                            strCatNavAttr = strCatNavColumns[iCol];
                                            EANAttribute att = ei.getAttribute(strCatNavAttr);
                                            EANMetaAttribute ma = att.getMetaAttribute();
                                            strFlagValue = st.nextToken().trim();

                                            if (st1.hasMoreTokens()) {
                                                strFlagCode = st1.nextToken().trim();
                                            }

                                            //add 3 to the update position to account for valfrom,eid and nlsid

                                            int iPosition = (!bEntityExists) ?
                                                Integer.valueOf( (String) hCatNavs.get(strCatNavAttr)).intValue() : iz + 3 + iCol;

                                            iCol++;

                                            D.ebug(D.EBUG_DETAIL,
                                                "CatODSMethods.populateODSTable:ET:" + strEntityType + ": CatNavAttributes : Found values for " +
                                                strCatNavAttr + ":NLS:" + iAllNLS[il] + ":POSITION:" + iPosition + ":iz:" + iz +
                                                ":VALUES:" + strFlagValue);

                                            try {
                                                ps.setString(iPosition, strFlagValue);

                                            }
                                            catch (SQLException se) {
                                                D.ebug(D.EBUG_ERR,
                                                    "**#0 PARAMETER SET ERROR: SQLError for (" + ei.getEntityType() + ":LOCATION:" +
                                                    iPosition + ":" + ei.getEntityID() + ":" + strCatNavAttr + ") - Value (" +
                                                    strFlagValue + ").  Using null instead." + ":" + se.getMessage());
                                                ps.setString(iPosition, null);

                                            }

                                            if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S") ||
                                                ma.getAttributeType().equals("F")) {
                                                EANFlagAttribute fa = (EANFlagAttribute) att;
                                                iPosition++;
                                                D.ebug(D.EBUG_DETAIL,
                                                    "CatODSMethods.populateODSTable:ET:" + strEntityType + ": CatNavAttributes : Setting value for " +
                                                    strCatNavAttr + "_FC:VALUE:" + strFlagCode + ":position:" + iPosition);
                                                try {
                                                    ps.setString(iPosition, strFlagCode);
                                                }
                                                catch (SQLException nx) {
                                                    D.ebug(D.EBUG_ERR,
                                                        "**#3 PARAMETER SET ERROR: SQLError for (" + ei.getEntityType() + ":" +
                                                        ei.getEntityID() + ":" + att.getAttributeCode() + ") - Value (" +
                                                        strFlagValue + ").  Using null instead." + ":" + nx.getMessage());
                                                    ps.setString(iPosition, null);
                                                }
                                            }
                                            if (!bEntityExists) {
                                                D.ebug(D.EBUG_INFO,
                                                    "Inserting Entity(" + ei.getEntityType() + ":" + ei.getEntityID() + ") " +
                                                    ei.toString());
                                            }
                                            else { //this is the update to existing Entity

                                                ps.setString(iz, m_strNow);
                                                ps.setInt(iz + 1, ei.getEntityID());
                                                ps.setInt(iz + 2, iAllNLS[il]);

                                                D.ebug(D.EBUG_INFO,
                                                    "Updating Entity(" + ei.getEntityType() + ":" + ei.getEntityID() + ") " +
                                                    ei.toString());
                                            }

                                            ps.execute();
                                        }

                                    }
                                    ps.close();
                                    ps = null;

                                }
                                else {
                                    if (bEntityExists) { //this is the Vanilla update to existing Entity

                                        ps.setString(iz, m_strNow);
                                        iz++;
                                        ps.setInt(iz, ei.getEntityID());
                                        iz++;
                                        ps.setInt(iz, iAllNLS[il]);
                                        D.ebug(D.EBUG_INFO,
                                               "Updating Entity(" + ei.getEntityType() + ":" + ei.getEntityID() + ") " +
                                               ei.toString());
                                    }
                                    else {
                                        D.ebug(D.EBUG_INFO,
                                               "Inserting Entity(" + ei.getEntityType() + ":" + ei.getEntityID() + ") " +
                                               ei.toString());
                                    }

                                    try {
                                        ps.execute();
                                    }
                                    finally {
                                        ps.close();
                                        ps = null;
                                    }

                                }
                            } // End of NLS Loop

                            //
                            // This is normal flag processing that is not considered one of the S&F attributes
                            // this nlsid = 0
                            if (elMulti.size() > 0) {
                                psFlag.setString(1, m_strNow);
                                psFlag.setString(2, strEntityType);
                                psFlag.setInt(3, ei.getEntityID());
                                psFlag.setInt(4, 1);
                                D.ebug(D.EBUG_INFO, "->\tMultiFlags:Starting:" + ei.getEntityType() + ":" + ei.getEntityID());
                                for (int iy = 0; iy < elMulti.size(); iy++) {
                                    EANFlagAttribute fa = (EANFlagAttribute) elMulti.getAt(iy);
                                    EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) fa.getMetaAttribute();

                                    Iterator it = fa.getPDHHashtable().keySet().iterator();

                                    String strAttributeCode = fa.getAttributeCode();
                                    psFlag.setString(5, strAttributeCode);

                                    // Loop through and collect all turned on values.
                                    while (it.hasNext()) {
                                        String strFlagCode = (String) it.next();
                                        MetaFlag mf = mfa.getMetaFlag(strFlagCode);
                                        psFlag.setString(6, strFlagCode);
                                        psFlag.setInt(7, 0);
                                        psFlag.setString(8, mf.getLongDescription());
                                        psFlag.execute();
                                    }
                                }
                                D.ebug(D.EBUG_INFO, "->\tMultiFlags:Finishing:" + ei.getEntityType() + ":" + ei.getEntityID());
                            }

                        }

                    }
                    catch (SQLException ex) {
                        String strErr = "";
                        D.ebug(D.EBUG_ERR,
                               "** INSERTION ERROR *** Skipping Entity (" + ei.getEntityType() + ":" + ei.getEntityID() + ") Error is: " +
                               ex.getMessage());
                        for (int ierr = 0; ierr < ei.getAttributeCount(); ierr++) {
                            EANAttribute eanAtt = ei.getAttribute(ierr);
                            strErr = strErr + eanAtt.getAttributeCode();
                            strErr = strErr + ":" + eanAtt.toString() + NEW_LINE;
                        }
                        D.ebug(D.EBUG_ERR, "Values are ******" + NEW_LINE + strErr);
                    }

                }

                m_conODS.commit();
                _eg.resetEntityItem();

                if (eg1 != null) {
                    eg1.resetEntityItem();
                }
                if (eg2 != null) {
                    eg2.resetEntityItem();
                }
                System.gc();

                // O.k. Lets bump up the intervals
                iStartID = iEndID + 1;
                iEndID = (iEndID + m_iChuckSize > iMaxID ? iMaxID : iEndID + m_iChuckSize);
                bLoop = iStartID <= iMaxID;

            }
        }
        catch (SQLException ex) {
            D.ebug(D.EBUG_ERR, ex.getMessage());
            ex.printStackTrace();

        }
        catch (MiddlewareException ex) {
            D.ebug(D.EBUG_ERR, ex.getMessage());
            ex.printStackTrace();

        }
        finally {
            // Close the stuff out

            try {
                psFlag.close();
                psFlag = null;
                psFlagDel.close();
                psFlagDel = null;
                psDEL.close();
                psDEL = null;
                psDLOG.close();
                psDLOG = null;
                psGetNLS.close();
                psGetNLS = null;
                psDELNLS.close();
                psDELNLS = null;
                psExistRel.close();
                psExistRel = null;
            }
            catch (SQLException ex1) {
                D.ebug(D.EBUG_ERR, ex1.getMessage());
                ex1.printStackTrace();

            }
        }

        return;
    }

    /**
     * Convert from yyyy-MM-dd hh:mm:ss.ffffff to yyyy-MM-dd-hh:mm:ss.ffffff
     */
    private String getISO(String _s1) {
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(_s1, " ");
        sb.append(st.nextToken());
        sb.append("-");
        sb.append(st.nextToken().replace(':', '.'));
        return sb.toString();
    }

    /**
     * Checks for and updates any flag records which have had their MetaDescriptions changed within the given interval.
     * Hits tables:
     * 1) PRODATTR
     * 2) Relevant Entity's Base table (if NON-MULTI-FLAG only)
     * 3) FLAG table (if MULTI-FLAG only)
     * 4) METAFLAGTABLE
     *
     * @param _mel
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected void updateDescriptionChanges(MetaEntityList _mel) throws SQLException, MiddlewareException {

        DescriptionChangeList dcList = m_dbPDH.getDescriptionChangeList(m_prof, DescriptionChangeList.FLAG_CHANGES,
            getISO(m_strLastRun), m_strNow);
        D.ebug(D.EBUG_INFO, "updateDescriptionChanges: updating " + dcList.getDescriptionChangeItemCount() + " descriptions...");

        for (int iDC = 0; iDC < dcList.getDescriptionChangeItemCount(); iDC++) {
            DescriptionChangeItem dci = dcList.getDescriptionChangeItem(iDC);
            String strEntityType = dci.getEntityType();
            String strAttributeCode = dci.getAttributeCode();
            int iNLSID = dci.getNLSID();
            String strFlagCode = dci.getFlagCode();

            // dont worry, this should be a 'complete' EG w/ atts at this point
            EntityGroup eg = _mel.getEntityGroup(strEntityType);
            EANMetaAttribute ema = null;

            if (eg == null || !CatODSServerProperties.includeTable(m_strODSSchema, strEntityType)) {
                D.ebug(D.EBUG_INFO,
                       "updateDescriptionChanges: skipping Entity " + strEntityType + ". Not found in List. (AttributeCode=" +
                       strAttributeCode + ", FlagCode=" + strFlagCode + ", NLSID=" + iNLSID + ")");
                continue;
            }
            ema = eg.getMetaAttribute(strAttributeCode);
            if (ema == null || !CatODSServerProperties.isAttributeSubset(m_strODSSchema, strEntityType, strAttributeCode)) {
                D.ebug(D.EBUG_INFO,
                       "updateDescriptionChanges: skipping Attribute " + strEntityType + "." + strAttributeCode + ". Not an include Attribute. (FlagCode=" +
                       strFlagCode + ", NLSID=" + iNLSID + ")");
                continue;
            }
            try {
                updateDescriptionChangeForItem(dci, (EANMetaFlagAttribute) ema);
            }
            catch (SQLException sqlExc) {
                D.ebug(D.EBUG_ERR,
                       "updateDescriptionChanges: ERROR at updateDescriptionChangeForItem:" + strEntityType + ":" + strAttributeCode +
                       ":" + strFlagCode + ":MSG:" + sqlExc.getMessage());
            }
        }
    }

    private void updateDescriptionChangeForItem(DescriptionChangeItem _dcItem, EANMetaFlagAttribute _ema) throws SQLException,
        MiddlewareException {

        int iLength = 128;
        boolean bNewRecord = false;

        String strTruncatedLD = null;
        String strNewShortDescription = null;
        String strUpdateFlagSQL = null;
        String strUpdateMFTSQL = null;
        String strUpdateEntitySQL = null;
        String strInsertMFTSQL = null;
        String strQueryMFTSQL = null;
        String strMFTSQL = null;

        Statement stQueryMFT = null;
        Statement stUpdateMFT = null;
        Statement stUpdateFlag = null;
        ResultSet rsMFT = null;
        ReturnDataResultSet rdrsMFT = null;
        Statement stUpdateEntity = null;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        boolean bMultiFlag = (!CatODSServerProperties.isMultiFlag(m_strODSSchema) && _ema.getAttributeType().equals("F"));
        String strEntityType = _dcItem.getEntityType();
        String strAttributeCode = _dcItem.getAttributeCode();
        String strFlagCode = _dcItem.getFlagCode();
        String strNewLongDescription = _dcItem.getNewLDescription();

        int iNLSID = _dcItem.getNLSID();

        if (!bMultiFlag) {
            iLength = CatODSServerProperties.getVarCharColumnLength();
            if (_ema.getOdsLength() > 0) {
                iLength = _ema.getOdsLength();
            }
        }

        strTruncatedLD = (strNewLongDescription.length() > iLength ? strNewLongDescription.substring(0, iLength) :
                          strNewLongDescription);
        strNewShortDescription = _dcItem.getNewSDescription();

        strUpdateFlagSQL = "UPDATE " + m_strODSSchema + ".FLAG " + " SET " + "   ValFrom = '" + m_strNow + "' " +
            " , FlagDescription = '" + strNewLongDescription + "' " + " WHERE " + "     EntityType = '" + strEntityType + "' " +
            " AND AttributeCode = '" + strAttributeCode + "' " + " AND FlagCode = '" + strFlagCode + "' " + " AND NLSID = " +
            iNLSID;

        strUpdateEntitySQL = "UPDATE " + m_strODSSchema + "." + strEntityType + " SET " + "   ValFrom = '" + m_strNow + "' " +
            " , " + strAttributeCode + " = '" + strTruncatedLD + "' " + " WHERE " + strAttributeCode + "_FC = '" + strFlagCode +
            "' " + " AND NLSID = " + iNLSID;

        strUpdateMFTSQL = "UPDATE " + m_strODSSchema + ".METAFLAGTABLE " + " SET " + "   ValFrom = '" + m_strNow + "' " +
            " , SHORTDESCRIPTION = '" + strNewShortDescription + "' " + " , LONGDESCRIPTION = '" + strNewLongDescription + "' " +
            " WHERE " + " ATTRIBUTECODE = '" + strAttributeCode + "' " + " AND ATTRIBUTEVALUE = '" + strFlagCode + "' " +
            " AND NLSID = " + iNLSID;

        strInsertMFTSQL = "INSERT INTO " + m_strODSSchema + ".METAFLAGTABLE " + " VALUES( " + " '" + m_strNow + "' " + ",'" +
            strAttributeCode + "' " + ", " + iNLSID + " " + ",'" + strFlagCode + "' " + ",'" + strNewShortDescription + "' " + ",'" +
            strNewLongDescription + "') ";

        strQueryMFTSQL = " SELECT COUNT(*) FROM " + m_strODSSchema + ".METAFLAGTABLE WHERE " + " ATTRIBUTECODE = '" +
            strAttributeCode + "'  AND " + " ATTRIBUTEVALUE = '" + strFlagCode + "'";

        if (bMultiFlag) {
            D.ebug(D.EBUG_INFO, "updateDescriptionChanges:MULTI-FLAG:strUpdateFlagSQL:" + strUpdateFlagSQL);
            try {
                stUpdateFlag = m_conODS.createStatement();
                stUpdateFlag.executeUpdate(strUpdateFlagSQL);
            }
            finally {
                if (stUpdateFlag != null) {
                    stUpdateFlag.close();
                    stUpdateFlag = null;
                }
            }
        }
        else {
            D.ebug(D.EBUG_INFO, "updateDescriptionChanges:NON-MULTI-FLAG:strUpdateEntitySQL:" + strUpdateEntitySQL);
            try {
                stUpdateEntity = m_conODS.createStatement();
                stUpdateEntity.executeUpdate(strUpdateEntitySQL);
            }
            finally {
                if (stUpdateEntity != null) {
                    stUpdateEntity.close();
                    stUpdateEntity = null;
                }
            }
        }

        D.ebug(D.EBUG_INFO, "updateDescriptionChanges:strQueryMFTSQL:" + strQueryMFTSQL);

        try {
            stQueryMFT = m_conODS.createStatement();
            rsMFT = stQueryMFT.executeQuery(strQueryMFTSQL);
            rdrsMFT = new ReturnDataResultSet(rsMFT);
        }
        finally {
            if (rsMFT != null) {
                rsMFT.close();
                rsMFT = null;
            }
            if (stQueryMFT != null) {
                stQueryMFT.close();
                stQueryMFT = null;
            }
        }

        bNewRecord = (rdrsMFT.getColumnInt(0, 0) == 0);
        strMFTSQL = (bNewRecord ? strInsertMFTSQL : strUpdateMFTSQL);

        D.ebug(D.EBUG_INFO, "updateDescriptionChanges:strMFTSQL:" + strMFTSQL);

        try {
            stUpdateMFT = m_conODS.createStatement();
            stUpdateMFT.executeUpdate(strMFTSQL);
        }
        finally {
            if (stUpdateMFT != null) {
                stUpdateMFT.close();
                stUpdateMFT = null;
            }
        }

        // if multi --> we need to check this from flag table.


        return;
    }

    private final void mergeAndSort(ReturnDataResultSet _rdrs1, ReturnDataResultSet _rdrs2, int[] _ai) {
        _rdrs1.addAll(_rdrs2);
        D.ebug(D.EBUG_SPEW, "merge and sort " + _rdrs1.size() + "," + _rdrs2.size());
        new SortUtil().sort(_rdrs1, new int[] {0, 1});
    }

    protected void resetMultiAttributeTable() throws SQLException {

        String strBaseTabName = CatODSServerProperties.getMultiAttributeTableName(m_strODSSchema);
        String strTableName = m_strODSSchema + "." + strBaseTabName;
        String strDropSQL = "DROP TABLE " + strTableName;
        String strTableSQL = "CREATE TABLE " + strTableName + " (" + "VALFROM TIMESTAMP NOT NULL, " +
            "ENTITYTYPE VARCHAR(16) NOT NULL, " + "ENTITYID INTEGER NOT NULL, " + "NLSID INTEGER NOT NULL, " +
            "ATTRIBUTECODE VARCHAR(32) NOT NULL, " + "ATTRIBUTEVALUE VARCHAR(1536) NOT NULL " + ")";

        String strTableSpace = CatODSServerProperties.getTableSpace(m_strODSSchema, strBaseTabName);
        String strIndexSpace = CatODSServerProperties.getIndexSpace(m_strODSSchema, strBaseTabName);
        String strIndexSQL = " CREATE UNIQUE INDEX " + strTableName + "_PK ON " + strTableName +
            "(ENTITYID, ENTITYTYPE, NLSID, ATTRIBUTECODE)";

        Statement ddlstmt = null;

        try {

            strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
            strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
            strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
                (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

            ddlstmt = m_conODS.createStatement();
            try {
                D.ebug(D.EBUG_INFO, "resetMultiAttributeTable:dropping Table:" + strTableName);
                ddlstmt.executeUpdate(strDropSQL);
                m_conODS.commit();
            }
            catch (SQLException ex) {
                D.ebug(D.EBUG_INFO, "resetMultiAttributeTable:Skipping Table Drop:" + strTableName + ":" + ex.getMessage());
            }

            D.ebug(D.EBUG_INFO, "resetMultiAttributeTable:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);

            // Now the index
            D.ebug(D.EBUG_INFO, "resetMultiAttributeTable:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);
        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
                m_conODS.commit();
            }
        }
    }

    /**
     * updateValFrom
     *
     * @param _strEntityType
     * @param _iEntityID
     *  @author David Bigelow
     */
    protected void updateValFrom(String _strEntityType, int _iEntityID) {

        String strSQL2 = "UPDATE " + m_strODSSchema + "." + _strEntityType + " SET VALFROM = '" + m_strNow + "' WHERE ENTITYID = " +
            _iEntityID;

        Statement s2 = null;

        try {
            D.ebug(D.EBUG_DETAIL, "updateValFrom.called for Entity (" + _strEntityType + ":" + _iEntityID + ")");
            try {
                s2 = m_conODS.createStatement();
                s2.executeUpdate(strSQL2);
            }
            finally {
                if (s2 != null) {
                    s2.close();
                    s2 = null;
                }
            }
        }
        catch (SQLException ex) {
            D.ebug(D.EBUG_ERR,
                   "updateValFrom ** ERROR *** Skipping Entity (" + _strEntityType + ":" + _iEntityID + ") Error is: " +
                   ex.getMessage());
        }
    }

    /**
     * entityExistsInODS
     *
     * @param _strEtype
     * @param _iEid
     * @param _iNls
     * @return
     *  @author David Bigelow
     */
    protected boolean entityExistsInODS(String _strEtype, int _iEid, int _iNls) {
        String strCheckExistSQL = "SELECT entityid from " + m_strODSSchema + "." + _strEtype + " WHERE  EntityID = ? AND NLSID = ?";
        boolean bAnswer = false;

        PreparedStatement psCheck = null;
        ResultSet rs = null;

        try {
            try {
                psCheck = m_conODS.prepareStatement(strCheckExistSQL);
                psCheck.setInt(1, _iEid);
                psCheck.setInt(2, _iNls);
                rs = psCheck.executeQuery();
                if (rs.next()) {
                    bAnswer = true;
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (psCheck != null) {
                    psCheck.close();
                    psCheck = null;
                }
            }
        }
        catch (SQLException se) {
            m_dbPDH.debug(D.EBUG_ERR, "entityExistsinODS:ERROR:" + se.getMessage());
            System.exit( -1);
        }
        return bAnswer;
    }

    /**
     * calculateLastRuntime
     *
     *  @author David Bigelow
     */
    protected void calculateLastRuntime() {
        m_strLastRun = getLastRuntime();
    }

    /**
     * getFlagAttributes
     *
     * @param _eg
     * @return
     *  @author David Bigelow
     */
    protected Vector getFlagAttributes(EntityGroup _eg) {
        EANMetaAttribute eanMetaAttr = null;
        Vector vReturn = new Vector();
        for (int i = 0; i < _eg.getMetaAttributeCount(); i++) {
            eanMetaAttr = _eg.getMetaAttribute(i);
            if (eanMetaAttr.getAttributeType().equals("F") || eanMetaAttr.getAttributeType().equals("U") ||
                eanMetaAttr.getAttributeType().equals("S")) {
                vReturn.add(eanMetaAttr.getAttributeCode());
            }
        }
        return vReturn;
    }

    /**
     * deleteExpiredRows
     *
     * @param _strEType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    protected void deleteExpiredRows(String _strEType) throws SQLException, MiddlewareException {

        String strDeleteSQL = "DELETE FROM " + m_strODSSchema + "." + _strEType + " WHERE EntityID IN (?)";
        String strEntityList = null;
        String strEnterprise = m_prof.getEnterprise();
        int iRowsUpdated = 0;

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        ReturnStatus returnStatus = new ReturnStatus( -1);
        PreparedStatement ps = null;

        m_dbPDH.debug(D.EBUG_DETAIL, "gbl9004:params:" + strEnterprise + ":" + _strEType + ":" + m_strLastRun + ":" + m_strNow);
        try {
            rs = m_dbPDH.callGBL9004(returnStatus, strEnterprise, _strEType, m_strLastRun, m_strNow);
            rdrs = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            m_dbPDH.commit();
            m_dbPDH.freeStatement();
            m_dbPDH.isPending();
        }

        /*
         * We have the list of all the expired entities from the PDH.
         *   Now create an update statement to delete all of them from the ODS table
         */

        if (rdrs.size() > 0) { //Get the first one
            strEntityList = rdrs.getColumn(0, 0);
        }
        else {
            m_dbPDH.debug(D.EBUG_INFO, "deleteExpiredRows: no Entities found to Delete");
            return;
        }

        for (int i = 1; i < rdrs.size(); i++) {
            strEntityList = strEntityList + "," + rdrs.getColumn(i, 0); //and then the rest
        }

        try {
            ps = m_conODS.prepareStatement(strDeleteSQL);
            ps.setString(1, strEntityList);
            iRowsUpdated = ps.executeUpdate();
            m_dbPDH.debug(D.EBUG_INFO,
                          "deleteExpiredRows: " + iRowsUpdated + "Deleted Entities " + strEntityList + " from ODS table " +
                          _strEType);
        }
        catch (SQLException ex) {
            m_dbPDH.debug(D.EBUG_INFO,
                          "deleteExpiredRows: Problem in deleting from ODS table " + _strEType + ":" + strEntityList + ":" +
                          ex.getMessage());
        }
        finally {
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }

    }

    /**
     * checkODSTable
     *
     * @param _eg
     *  @author David Bigelow
     */
    protected void checkODSTable(EntityGroup _eg) {
        Hashtable hTableColumns = new Hashtable();
        String strAlterSQL = "";
        String strEntityType = _eg.getEntityType();
        String strAlterTableSQL = "ALTER TABLE " + m_strODSSchema + "." + strEntityType;
        String strColName = null;
        String[] strAFilter = {
            "TABLE"};

        boolean bAlterTable = false;
        boolean blnfound = false;

        Statement ddlstmt = null;
        ResultSet rs = null;
        Statement stmtAlterTable = null;

        try {
            DatabaseMetaData dbMetaODS = getODSDbMetaData();

            try {
                ddlstmt = m_conODS.createStatement();
                m_dbPDH.debug(D.EBUG_INFO, "checkODSTable:ET:" + strEntityType);
                rs = dbMetaODS.getTables(null, m_strODSSchema, strEntityType, strAFilter);

                if (rs.next()) {
                    blnfound = true;
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ddlstmt != null) {
                    ddlstmt.close();
                    ddlstmt = null;
                }
            }

            if (!blnfound) { //Table not found...so create a new one
                resetODSTable(_eg);
                return;
            }

            /*
             * Table is found...so now store its columns to compare with the PDH meta
             */
            try {
                rs = dbMetaODS.getColumns(null, "%", strEntityType, "%");
                while (rs.next()) {
                    strColName = rs.getString(4).trim();
                    hTableColumns.put(strColName, " ");
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }
            // First go after all non lob attributes
            for (int ii = 0; ii < _eg.getMetaAttributeCount(); ii++) {
                EANMetaAttribute ma = _eg.getMetaAttribute(ii);

                if (includeColumn(ma)) {

                    if (hTableColumns.get(ma.getAttributeCode()) != null) { //Column is found..so dont do anything
                        continue;
                    }
                    if (! (ma.getAttributeType().equals("F") || ma.getAttributeType().equals("X") ||
                           ma.getAttributeType().equals("L"))) {
                        bAlterTable = true;
                        m_dbPDH.debug(D.EBUG_INFO, "checkODSTable: New Column Found: " + ma.getAttributeCode());
                        strAlterSQL = strAlterSQL + " ADD COLUMN " + ma.getAttributeCode() + " " + getDBFieldType(ma);
                        if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                            strAlterSQL = strAlterSQL + " ADD COLUMN " + ma.getAttributeCode() + "_FC CHAR(16)";
                        }
                    }
                }
            }

            // now go after lob attributes (X,FL)
            for (int ii = 0; ii < _eg.getMetaAttributeCount(); ii++) {
                EANMetaAttribute ma = _eg.getMetaAttribute(ii);

                if (includeColumn(ma)) {
                    if (hTableColumns.get(ma.getAttributeCode()) != null) { //Column is found..so dont do anything
                        continue;
                    }
                    if (ma.getAttributeType().equals("F") || ma.getAttributeType().equals("X") || ma.getAttributeType().equals("L")) {
                        bAlterTable = true;
                        m_dbPDH.debug(D.EBUG_INFO, "checkODSTable: New Column Found: " + ma.getAttributeCode());
                        strAlterSQL = strAlterSQL + " ADD COLUMN " + ma.getAttributeCode() + " " + getDBFieldType(ma);
                    }
                }
            }

            //Alter only if any new columns found

            if (!bAlterTable) {
                m_dbPDH.debug(D.EBUG_INFO, "checkODSTable: No Alterations for  strAlterTableSQL");
                return;
            }

            m_dbPDH.debug(D.EBUG_INFO, "checkODSTable: Altering Table " + strAlterTableSQL + " with DDL " + strAlterSQL);

            try {
                stmtAlterTable = m_conODS.createStatement();
                stmtAlterTable.executeUpdate(strAlterTableSQL + " " + strAlterSQL);
            }
            finally {
                if (stmtAlterTable != null) {
                    stmtAlterTable.close();
                    stmtAlterTable = null;
                }
            }

        }
        catch (SQLException ex) {
            m_dbPDH.debug(D.EBUG_ERR,
                          "checkODSTableDef:*error* with AlterStatement:" + ex.getMessage() + NEW_LINE + strAlterTableSQL + " " +
                          strAlterSQL);
            System.exit(0);
        }
    }

    /**
     * getODSDbMetaData
     *
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    protected DatabaseMetaData getODSDbMetaData() throws SQLException {
        if (m_dbMetaODS == null) {
            m_dbMetaODS = m_conODS.getMetaData();
        }
        return m_dbMetaODS;
    }

    /**
     * setEnglishOnlyFlags
     *
     * @param _eg
     *  @author David Bigelow
     */
    protected void setEnglishOnlyFlags(EntityGroup _eg) {
        m_dbPDH.debug(D.EBUG_DETAIL, "setEnglishOnlyFlags Checking : " + _eg.getEntityType());
        for (int x = 0; x < _eg.getMetaAttributeCount(); x++) {
            EANMetaAttribute ma = _eg.getMetaAttribute(x);
            if (CatODSServerProperties.isEnglishOnly(m_strODSSchema, ma.getAttributeCode()) || ma.getAttributeType().equals("S")) {
                D.ebug("CatODSMethods.setEnglishOnlyFlags.setting: " + ma + " To US English Only");
                ma.setUSEnglishOnly(true);
            }
        }
    }

    /**
     * includeColumn
     *
     * @param _ma
     * @return
     *  @author David Bigelow
     */
    protected boolean includeColumn(EANMetaAttribute _ma) {
        return (!_ma.getAttributeType().equals("A") && !_ma.getAttributeType().equals("B") &&
                ! (!CatODSServerProperties.isMultiFlag(m_strODSSchema) && _ma.getAttributeType().equals("F")) &&
                !isDerivedEntityID(_ma));
    }

    private boolean isDerivedEntityID(EANMetaAttribute _ma) {
        return _ma.getAttributeCode().equals("DERIVED_EID");
    }

    /**
     * generateGeoMap
     *
     *  @author David Bigelow
     */
    protected void generateGeoMap() {

        String strSQL = "SELECT general_area, nlsid from gbli.gamap";
        Statement stmt = null;
        ResultSet rs = null;

        c_hshGAMap = new Hashtable();

        try {

            D.ebug("generateGeoMap.Starting...");
            try {
                stmt = m_conODS.createStatement();
                rs = stmt.executeQuery(strSQL);
                while (rs.next()) {
                    String strGA = rs.getString(1).trim();
                    String strNLS = rs.getInt(2) + "";
                    if (c_hshGAMap.containsKey(strGA)) {
                        String strNLSs = (String) c_hshGAMap.get(strGA);
                        strNLS = strNLSs + ":" + strNLS;
                    }
                    // Now.. lets place this back to see what we get
                    D.ebug("generateGeoMap.putting values:" + strGA + " (" + strNLS + ")");
                    c_hshGAMap.put(strGA, strNLS);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }

        }
        catch (SQLException se) {
            m_dbPDH.debug(D.EBUG_ERR, "generateGeoMap:ERROR:" + se.getMessage());
            System.exit( -1);
        }
        D.ebug("generateGeoMap.Finished...");
    }

    /**
     * getGeoNLSArray
     *
     * @param ei
     * @return
     *  @author David Bigelow
     */
    protected int[] getGeoNLSArray(EntityItem ei) {
        String strFlagCode = null;
        String strNLS = null;
        StringTokenizer st = null;

        int[] iAllNLS = {
            1};
        int i = 0;

        EANFlagAttribute fa = (EANFlagAttribute) ei.getAttribute("GENAREANAME");
        if (fa == null) {
            D.ebug("getGeoNLSArray.General Area Attribute does not exist for this entity " + ei.toString());
            return iAllNLS;
        }
        strFlagCode = fa.getFirstActiveFlagCode();

        if (!c_hshGAMap.containsKey(strFlagCode)) {
            D.ebug("getGeoNLSArray.Flagcode not found for general area: " + strFlagCode);
            return iAllNLS;
        }

        strNLS = (String) c_hshGAMap.get(strFlagCode);
        st = new StringTokenizer(strNLS, ":");
        iAllNLS = new int[st.countTokens()];
        while (st.hasMoreTokens()) {
            iAllNLS[i++] = Integer.valueOf(st.nextToken()).intValue();
            D.ebug("getGeoNLSArray.adding: " + iAllNLS[i - 1] + " to the geo calc for " + strFlagCode + " for " + ei);
        }
        return iAllNLS;
    }

    private void jbInit() throws Exception {
    }


    protected static final boolean canCatalogRun(Catalog _cat) throws MiddlewareException {

         Statement stmt = null;
         ResultSet rs = null;
         Database db = null;
         Connection con = null;

         //
         // 1 = CatalogRunner
         // 4 = EACM PRice
         // 10 = Cat Generator
         // 20 = this
         String strStatement = "SELECT COUNT(*) from eacm.IFMLOCK where STATUS = 1 AND PROCESS_ID IN (1,4,10,20)";
         int iAnswer = 0;

         try {

             try {
                 db = _cat.getCatalogDatabase();
                                 db.connect();
                 con = db.getPDHConnection();
                 stmt = con.createStatement();
                 rs = stmt.executeQuery(strStatement);
                 if (rs.next()) {
                     iAnswer = rs.getInt(1);
                 }
             }
             finally {
                 if (rs != null) {
                     rs.close();
                     rs = null;
                 }
                 if (stmt != null) {
                     stmt.close();
                     stmt = null;
                 }
                 if (con != null) {
                     con.close();
                 }
                 if (db != null) {
                     db.close();
                 }
             }
         }
         catch (SQLException se) {
              D.ebug("Cannot run had an SQL error... ");
             se.printStackTrace();
             System.exit( -1);
         }
         return iAnswer == 0;

     }
     protected static void setIFMLock(Catalog _cat) throws MiddlewareException {
//
         Database db = null;
         Connection con = null;

         String strSelect = "SELECT * FROM EACM.IFMLOCK WHERE PROCESS_ID = 20";
         String strUpdate = "UPDATE EACM.IFMLOCK SET STATUS = 1, START_TIME = CURRENT TIMESTAMP WHERE PROCESS_ID = 20";
         String strInsert = "INSERT INTO EACM.IFMLOCK (PROCESS_ID,PROCESS_NAME,STATUS,START_TIME, END_TIME) VALUES " +
             "(20,'CATODS',0,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";

         Statement stmt = null;
         ResultSet rs = null;
         Statement stmtInsertTable = null;
         Statement stmtUpdateTable = null;

         boolean bExists = false;

         try {

             db = _cat.getCatalogDatabase();
             db.connect();
             con = db.getPDHConnection();

             try {
                 stmt = con.createStatement();
                 rs = stmt.executeQuery(strSelect);
                 bExists = rs.next();
             }
             finally {
                 if (rs != null) {
                     rs.close();
                     rs = null;
                 }
                 if (stmt != null) {
                     stmt.close();
                     stmt = null;
                 }
                 con.commit();
             }

             if (!bExists) {
                 D.ebug("setIFMLock:Inserting PROCESS_ID = 20 INTO EACM.IFMLOCK");
                 try {
                     stmtInsertTable = con.createStatement();
                     stmtInsertTable.execute(strInsert);
                 }
                 finally {
                     if (stmtInsertTable != null) {
                         stmtInsertTable.close();
                         stmtInsertTable = null;
                     }
                     con.commit();
                 }
             }

             D.ebug("setIFMLock:Update PROCESS_ID = 20 INTO EACM.IFMLOCK");
             try {
                 stmtUpdateTable = con.createStatement();
                 stmtUpdateTable.execute(strUpdate);
             }
             finally {
                 if (stmtUpdateTable != null) {
                     stmtUpdateTable.close();
                     stmtUpdateTable = null;
                 }
                 con.commit();
             }

             if (con != null) {
                 con.commit();
                 con.close();
             }
             if (db != null) {
                 db.close();
             }

         }
         catch (SQLException e) {
             D.ebug("setIFMLock:ERROR: on EACM.IFMLOCK:" + e.getMessage());
             System.exit( -1);
         }

     }

     /**
      * clearIFMLock
      *
      *  @author David Bigelow
      */
     protected static void clearIFMLock(Catalog _cat) throws MiddlewareException {

         String strUpdate = "UPDATE EACM.IFMLOCK SET STATUS = 0, END_TIME = CURRENT TIMESTAMP WHERE PROCESS_ID = 20";
         Statement stmtUpdateTable = null;

         Database db = null;
         Connection con = null;

         try {

             db = _cat.getCatalogDatabase();
             db.connect();
             con = db.getPDHConnection();

             D.ebug("setIFMLock:Update PROCESS_ID = 20 INTO EACM.IFMLOCK");

             // force a change
             try {
                 stmtUpdateTable = con.createStatement();
                 stmtUpdateTable.execute(strUpdate);
             }
             finally {
                 if (stmtUpdateTable != null) {
                     stmtUpdateTable.close();
                     stmtUpdateTable = null;
                 }
                 if (con != null) {
                     con.commit();
                     con.close();
                 }
                 if (db != null) {
                     db.close();
                 }
             }

         }
         catch (SQLException e) {
             D.ebug("setIFMLock:ERROR: on EACM.IFMLOCK:" + e.getMessage());
             System.exit( -1);
         }

     }
     protected  boolean isCountryFilterMatch(String _strCountryFilterAttribute,EntityItem _ei,String _strCountryList) {
       EANAttribute att = _ei.getAttribute(_strCountryFilterAttribute);
       EANFlagAttribute fa = (EANFlagAttribute) att;
       String strFlagCode = null;
       boolean bRet = false;
       try {
           strFlagCode = fa.getFirstActiveFlagCode();
           if (strFlagCode.equals(_strCountryList)) {
             bRet =true;
           }
       }
       catch (Exception nx) {
           D.ebug(D.EBUG_ERR,
                  "isCountryFilterMatch: Cannot get flag value for CountryFilterAttribute:"+_strCountryFilterAttribute+": INCLUDING THIS IN LOAD");
       }

       return bRet;
     }
     
     /**
      * Move final data to price.'_F' tables.
      * @author Bing Liu
      */
     /**
      * Move final data to price.'_F' tables.
      * @author Bing Liu
      */
     protected void populateFinalData(){
    	long beginTimeStamp = System.currentTimeMillis();
    	String[] tables = CatODSServerProperties.getFinalTableNames();
    	String status =  CatODSServerProperties.getFinalTablesStatus();
 		if(tables==null){
 			D.ebug(D.EBUG_WARN,CatODSServerProperties.getDatabaseSchema()
 					+"_final_tables' does not exits in the prop file.");
 			return;
 		}
 		if(tables.length == 0){
 			D.ebug(D.EBUG_WARN,"There is no final data table.");
 			return;
 		}
 		if(status == null){
 			D.ebug(D.EBUG_WARN,"APPROVEDTABLES_STATUS does not exits in the prop file.");
 			return;
 		}
 		String strSQL = "{CALL opicm.popfinal(?,?,?,?,?,?)}";
 		CallableStatement cstmt = null;  		 		
 		String schemaName = CatODSServerProperties.getDatabaseSchema();
 		for(int i =0 ; i<tables.length;i++){
 			try{
 				cstmt = m_conODS.prepareCall(strSQL);
 				cstmt.setInt(1,0);
 				cstmt.setString(2,schemaName);
 				cstmt.setString(3,tables[i]);
 				cstmt.setString(4,status);
 				cstmt.setString(5,CatODSServerProperties.getTableSpace(schemaName,tables[i]));
 				String idxSpaceName = CatODSServerProperties.getIndexSpace(schemaName, tables[i]);
 				if(idxSpaceName == null){
 					
 				}
 				cstmt.setString(6,idxSpaceName);
 				cstmt.execute();
 				D.ebug(D.EBUG_INFO,"Populate final data to table "+tables[i]+" successfully!");
 			}
 			catch(SQLException sqle){
 		 		D.ebug(D.EBUG_ERR,"Call procedure for table "+tables[i]+
 		 				" error : "+sqle.getMessage());
 		 	} 				
 		 	finally{
 		 		if(cstmt!=null){
 		 			try{cstmt.close();}catch(Exception e){}
 		 		}
 		 	}
 		}
 		D.ebug(D.EBUG_INFO,"All final data is populated!");
// 		D.ebug(D.EBUG_INFO,"DB procedure return string:"+outStr);
 		long endTimeStamp = System.currentTimeMillis();
 		D.ebug("Total time used in populateFinalData(): "+ Long.toString(endTimeStamp - beginTimeStamp));
// 			aggregateFlagTableData();
 		
     } 

     
     /**
      * Aggregate flag table data.
      * @author Bing Liu
      */
     /*
     protected void aggregateFlagTableData(){
    	long beginTimeStamp = System.currentTimeMillis();
    	String tablesStr = CatODSServerProperties.getAggrTableNames();
 		if(tablesStr==null){
 			D.ebug(D.EBUG_WARN,CatODSServerProperties.getDatabaseSchema()
 					+"_aggr_tables' does not exits in the prop file.");
 			return;
 		}
 		if("".equals(tablesStr)){
 			D.ebug(D.EBUG_WARN,CatODSServerProperties.getDatabaseSchema()
 					+"No value is set for the property PRICE_aggr_tables.");
 			return;
 		}
 		String strSQL = "{CALL PRICE.proc_flag_aggr(?)}";
 		CallableStatement cstmt = null; 
 		try{
 			D.ebug(D.EBUG_INFO,"Begin aggregating tables.................");
 			cstmt = m_conODS.prepareCall(strSQL);
 			cstmt.setString(1,tablesStr);
 			boolean result = cstmt.execute();
 			D.ebug(D.EBUG_INFO,"Aggregate table - "+tablesStr+" successfully, execute result is "+result);
 			long endTimeStamp = System.currentTimeMillis();
 			D.ebug(D.EBUG_INFO,"Total time used in aggregateFlagTableData(): "+ Long.toString(endTimeStamp - beginTimeStamp));
 		}
 		catch(Exception sqle){
 			D.ebug(D.EBUG_ERR,"Call procedure error : "+sqle);
 		}
 		finally{
 			if(cstmt!=null){
 				try{cstmt.close();}catch(Exception e){}
 			}
 		}
     }     
     */
}
/*
 $Log: CatODSMethods.java,v $
 Revision 1.19  2015/06/18 15:25:52  jilichao
 move PreparedStatement close function to the finally clause to close it properly.

 Revision 1.18  2011/05/05 11:22:00  wendy
 src from IBMCHINA

 Revision 1.2  2007/10/09 08:11:49  sulin
 no message

 Revision 1.1.1.1  2007/06/05 02:09:44  jingb
 no message

 Revision 1.14  2007/05/15 15:47:02  rick
 changed to pull deactivated attributes and not filter
 out CATLGPUB:PUBTO when null.
 Then sets CATDB column CATLGPUB:PUBTO
 to 12-31-2999
 as a default when null

 Revision 1.13  2006/06/03 16:58:50  bala
 get Attributewidth from prop file using getAttrColLength overriding default

 Revision 1.12  2006/06/02 16:12:26  bala
 use odslength property to check for Longtext overrride

 Revision 1.11  2006/06/02 16:06:01  bala
 add odslength override for L type attribute

 Revision 1.10  2006/05/31 22:51:00  bala
 fix for nls read count more than 10

 Revision 1.9  2006/05/24 21:30:36  gregg
 harcoding multrow attr col at 40

 Revision 1.8  2006/05/23 21:50:10  gregg
 pull multirowattribute's column width from metalinkattr table's odslength record.

 Revision 1.7  2006/05/23 17:54:42  bala
 fix for insert in updateODSTable

 Revision 1.6  2006/05/11 23:19:14  bala
 make sure that catdb_tables is in the same schema as the rest of the ods tables

 Revision 1.5  2006/04/19 22:51:59  bala
 fix for flagdesc retrieval during multirow attribute processing

 Revision 1.4  2006/04/07 19:36:36  bala
 check for multiflag when multirow attribute

 Revision 1.3  2006/04/06 23:20:33  bala
 add CountryFilter property and associated methods

 fix for isactive set to 1 during updates

 Revision 1.2  2006/04/06 17:05:12  bala
 fix wrong parm error in updateOds

 Revision 1.1.1.1  2006/03/30 17:36:30  gregg
 Moving catalog module from middleware to
 its own module.

 Revision 1.52  2006/02/23 23:46:02  bala
 trim flagcodes/flagvalues returned from meta to avoid string truncation during sql insert

 Revision 1.51  2006/02/23 21:34:52  bala
 some more corrections

 Revision 1.50  2006/02/23 21:29:07  bala
 complete fix

 Revision 1.49  2006/02/23 19:32:35  bala
 fix for skipping nls's

 Revision 1.48  2006/02/15 22:47:56  bala
 change gamap column genareaname_fc to general_area

 Revision 1.47  2006/01/25 18:08:50  bala
 correct process id during insert

 Revision 1.46  2006/01/17 22:51:20  bala
 add pid 20 to check

 Revision 1.45  2006/01/17 22:49:43  bala
 correcting debug statement

 Revision 1.44  2006/01/17 22:46:10  bala
 Added/using canCatalogRun, setIFMLock,clearIFMlock to warn other apps so that they dont fall over each other

 Revision 1.43  2006/01/17 16:47:53  dave
 <No Comment Entered>

 Revision 1.42  2005/12/23 22:38:26  bala
 fix nullpointer

 Revision 1.41  2005/12/23 22:13:02  bala
 new chunking for init (populateODSTable)

 Revision 1.40  2005/11/28 21:48:44  bala
 more cleanup, comment/remove debug statements

 Revision 1.39  2005/11/17 08:29:02  bala
 change resetTable to get the list of tables (not to be deleted) from a table
 change the multirow attr to always have the _fc column so that the metadescription change can
 work accurately

 Revision 1.38  2005/10/28 02:18:21  bala
 some more cleanup..will have to add FC for the multirow flag attr for metaflagchanges to work

 Revision 1.37  2005/10/28 02:05:24  bala
 change updateODSTable

 Revision 1.36  2005/10/20 22:33:10  bala
 fix bug

 Revision 1.35  2005/10/20 22:20:20  bala
 change to skip duplicate attributes when flags are selected as table column attributes in the ods properties

 Revision 1.34  2005/10/20 17:49:44  bala
 changes to get new custom profile for cat ods datamover

 Revision 1.33  2005/10/19 17:34:15  bala
 change insert ddl statement

 Revision 1.32  2005/10/19 16:26:06  bala
 add countrycode,languagecode and countrylist as mandatory columns to cat table

 Revision 1.31  2005/10/18 21:40:58  bala
 add to list of tables that dont need to be dropped

 Revision 1.30  2005/10/14 19:40:57  bala
 fix again

 Revision 1.29  2005/10/14 17:43:37  bala
 fix for class cast exception

 Revision 1.28  2005/10/14 14:01:40  bala
 <No Comment Entered>

 Revision 1.27  2005/10/12 21:11:35  bala
 Change class CatNavGroup/Item to MultiRowAttrGroup/Item

 Revision 1.26  2005/10/12 20:13:59  gregg
 change method name...
  CatNavItem.getAttributeCode --> getColumnKey

 Revision 1.25  2005/10/12 07:55:24  bala
 add catnav columns to the unique index to enable multiple rows

 Revision 1.24  2005/10/12 07:41:53  bala
 fix

 Revision 1.23  2005/10/12 07:27:45  bala
 fixes

 Revision 1.22  2005/10/12 07:17:29  bala
 more fix

 Revision 1.21  2005/10/12 07:06:21  bala
 more changes

 Revision 1.20  2005/10/12 06:59:08  bala
 more debug

 Revision 1.19  2005/10/12 05:31:42  bala
 more debugging

 Revision 1.18  2005/10/12 04:35:39  bala
 correct wrong number of parm error

 Revision 1.17  2005/10/12 01:55:23  bala
 find out the numberformatexception problem

 Revision 1.16  2005/10/12 01:33:46  bala
 insert mutiple  when multivalues found

 Revision 1.15  2005/10/11 19:57:07  bala
 change hashtable check

 Revision 1.14  2005/10/11 19:34:08  bala
 add debug comments

 Revision 1.13  2005/10/11 18:04:09  bala
 adding catnav columns when table is created

 Revision 1.12  2005/10/10 20:10:13  bala
 More changes for CatNav use

 Revision 1.11  2005/10/08 22:54:14  bala
 First Iteration CatNavGroup

 Revision 1.10  2005/10/07 16:23:31  bala
 Add logging to show NLS's available

 Revision 1.9  2005/10/07 16:10:31  bala
 added setConnection method

 Revision 1.8  2005/10/07 15:46:20  bala
 change PDHConnection logging

 Revision 1.7  2005/10/07 00:41:56  bala
 plug in catalog classes

 Revision 1.6  2005/09/28 21:06:26  bala
 add exception trace to updateODS method

 Revision 1.5  2005/09/28 18:16:20  bala
 changed index name length to 14

 Revision 1.4  2005/09/27 23:07:03  bala
 somemore fixes

 Revision 1.3  2005/09/26 23:24:45  bala
 remove prodattribute stuff

 Revision 1.2  2005/09/26 22:38:52  bala
 Add debug comments

 Revision 1.1  2005/09/26 17:30:31  bala
 <No Comment Entered>

 */
