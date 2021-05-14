/*
 * Created on Jun 22, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package COM.ibm.eannounce.catalog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
import COM.ibm.opicmpdh.middleware.ReturnID;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Stopwatch;

/**
 * @author Jicky
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DatabaseExt {
    private Database m_db = null;

    private CallableStatement m_cstmtHandle = null;

    private boolean c_bUsageLogging = false;

    public DatabaseExt(Database _db) {
        m_db = _db;
        c_bUsageLogging = MiddlewareServerProperties.getUsageLogging();
    }
    
    private void debug(int debugLvl,String str){
    	m_db.debug(debugLvl,str);
    }
    
    private void test(boolean flag,String str) throws Exception{
    	m_db.test(flag,str);
    }
    
    private final boolean LogUsage() {
        return c_bUsageLogging;
      }    

    /**
     * Call GBL8180 (no description)
     * 
     * @author generated code
     * @return the stored procedure output from GBL8180 as a JDBC
     *         <code>ResultSet</code>
     * @exception MiddlewareException
     */

    final public void callGBL8180(ReturnStatus returnStatus, int sessionid,
            String enterprise, String actiontype, String entitytype,
            int startentityid, int endentityid) throws MiddlewareException {

        // ResultSet
        ResultSet rs = null;

        // Return value (for insert/update)
        ReturnID returnID = new ReturnID(0);
        MiddlewareException mx = null;
        long m_lStart = System.currentTimeMillis();
        long m_lFinish = 0;

        Connection conn = null;

        try {
            m_db.debug(D.EBUG_DETAIL, "executing callGBL8180");

            String strNeedDB = "opicmpdh";

            m_db.debug(D.EBUG_DETAIL, "database needed for GBL8180 is "
                    + strNeedDB);

            if (strNeedDB.equals("opicmods")) {
                if (!m_db.hasODS()) {
                    throw new MiddlewareException(
                            "ODS database is not available for GBL8180");
                }
                if (m_db.getODSConnection() == null) {
                    m_db.connect();
                }
                conn = m_db.getODSConnection();
            } else {
                if (!m_db.hasPDH()) {
                    throw new MiddlewareException(
                            "PDH database is not available for GBL8180");
                }
                if (m_db.getPDHConnection() == null) {
                    m_db.connect();
                }
                conn = m_db.getPDHConnection();
            }

            try {
                m_db.test(enterprise != null, "GBL8180:enterprise is null");
                m_db.debug(D.EBUG_SPEW, "GBL8180:enterprise:length = "
                        + (enterprise.getBytes("UTF8")).length);
                m_db.test((enterprise.getBytes("UTF8")).length <= 16,
                        "GBL8180:enterprise length not <= 16 [passed length="
                                + (enterprise.getBytes("UTF8")).length + "]");
                m_db.test(actiontype != null, "GBL8180:actiontype is null");
                m_db.debug(D.EBUG_SPEW, "GBL8180:actiontype:length = "
                        + (actiontype.getBytes("UTF8")).length);
                m_db.test((actiontype.getBytes("UTF8")).length <= 32,
                        "GBL8180:actiontype length not <= 32 [passed length="
                                + (actiontype.getBytes("UTF8")).length + "]");
                m_db.test(entitytype != null, "GBL8180:entitytype is null");
                m_db.debug(D.EBUG_SPEW, "GBL8180:entitytype:length = "
                        + (entitytype.getBytes("UTF8")).length);
                m_db.test((entitytype.getBytes("UTF8")).length <= 32,
                        "GBL8180:entitytype length not <= 32 [passed length="
                                + (entitytype.getBytes("UTF8")).length + "]");
                // Build a line with combined parameter values
                StringBuffer strbParms = new StringBuffer();
                strbParms.append("" + returnStatus);
                strbParms.append(":" + sessionid);
                strbParms.append(":" + enterprise);
                strbParms.append(":" + actiontype);
                strbParms.append(":" + entitytype);
                strbParms.append(":" + startentityid);
                strbParms.append(":" + endentityid);
                String strParms = new String(strbParms);
                // Output the line
                m_db.debug(D.EBUG_DETAIL, "GBL8180 SPPARMS " + strParms);
                // This will never happen, used just to keep java compiler quiet
                // in case there are no parms to SP
                if (1 == 2) {
                    throw new java.io.UnsupportedEncodingException();
                }
            } catch (java.io.UnsupportedEncodingException uex) {
                m_db.debug(D.EBUG_ERR,
                        "Threw an exception checking byte length " + uex);
            }
            if (this.c_bUsageLogging) {
                String strTransaction = "GBL8180";
                // increment the use count for this transaction
                if (!strTransaction.equals("GBL9981")) {
                    m_db.record_transaction("GBL8180");
                }
            }
            // Build a string containing the SQL CALL statement with parameter
            // markers
            String strSQL = "CALL opicm.GBL8180(?,?,?,?,?,?,?)";
            // If not connected to database, now is the time!
            // After making a connection, we should have a connection handle
            m_db.test(conn != null, "callGBL8180:Connection handle is null");
            // If there is a prior statement something is wrong
            m_db.isPending("callGBL8180");
            // Prepare the SQL
            m_cstmtHandle = conn.prepareCall(strSQL);
            // After preparing a statement, there should be a handle
            m_db.test(m_cstmtHandle != null,
                    "callGBL8180:Statement handle is null");
            m_cstmtHandle.registerOutParameter(1, Types.INTEGER);
            // Bind the method parameters to the SQL statement markers
            m_cstmtHandle.setInt(1, returnStatus.intValue());
            m_cstmtHandle.setInt(2, sessionid);
            m_cstmtHandle.setString(3, enterprise);
            m_cstmtHandle.setString(4, actiontype);
            m_cstmtHandle.setString(5, entitytype);
            m_cstmtHandle.setInt(6, startentityid);
            m_cstmtHandle.setInt(7, endentityid);
            // Execute the SQL statement
            m_cstmtHandle.executeUpdate();
            // Retrieve the return status
            returnStatus.setValue(m_cstmtHandle.getInt(1));
        } catch (RuntimeException rx) {
            mx = new MiddlewareException("(callGBL8180) RuntimeException: "
                    + rx);
            m_db.debug(D.EBUG_ERR, "RuntimeException trapped at callGBL8180: "
                    + rx);
            StringWriter writer = new StringWriter();
            rx.printStackTrace(new PrintWriter(writer));
            String x = writer.toString();
            m_db.debug(D.EBUG_ERR, "" + x);
        } catch (Exception x) {
            mx = new MiddlewareException("(callGBL8180) Exception: " + x);
            m_db.debug(D.EBUG_ERR, "Exception trapped at callGBL8180: " + x);
        } finally {
            m_lFinish = System.currentTimeMillis();
            long longDuration = m_lFinish - m_lStart;
            m_db.debug(D.EBUG_INFO, "timing GBL8180 "
                    + Stopwatch.format(longDuration));
            if (mx != null) {
                m_db.debug(D.EBUG_DETAIL, "exiting callGBL8180 - error");
                throw mx;
            } else {
                m_db.debug(D.EBUG_DETAIL, "exiting callGBL8180");
            }
        }
    }

    /**
     * Call GBL8181 (no description)
     * 
     * @author generated code
     * @return the stored procedure output from GBL8181 as a JDBC
     *         <code>ResultSet</code>
     * @exception MiddlewareException
     */

    final public void callGBL8181(ReturnStatus returnStatus,
            int sessionid_prior, int sessionid_curr, String enterprise,
            String actiontype, String roottype, int startentityid,
            int endentityid, String suffix) throws MiddlewareException {
        
        // ResultSet
        ResultSet rs = null;

        // Return value (for insert/update)
        ReturnID returnID = new ReturnID(0);
        MiddlewareException mx = null;
        long m_lStart = System.currentTimeMillis();
        long m_lFinish = 0;
        
        Connection conn = null;

        try {
            m_db.debug(D.EBUG_DETAIL, "executing callGBL8181" + suffix);

            String strNeedDB = "opicmpdh";

            m_db.debug(D.EBUG_DETAIL, "database needed for GBL8181" + suffix
                    + " is " + strNeedDB);

            if (strNeedDB.equals("opicmods")) {
                if (!m_db.hasODS()) {
                    throw new MiddlewareException(
                            "ODS database is not available for GBL8181"
                                    + suffix);
                }
                if (m_db.getODSConnection() == null) {
                    m_db.connect();
                }
                conn = m_db.getODSConnection();
            } else {
                if (!m_db.hasPDH()) {
                    throw new MiddlewareException(
                            "PDH database is not available for GBL8181"
                                    + suffix);
                }
                if (m_db.getPDHConnection() == null) {
                    m_db.connect();
                }
                conn = m_db.getPDHConnection();
            }

            try {
                m_db.test(enterprise != null, "GBL8181" + suffix
                        + ":enterprise is null");
                m_db.debug(D.EBUG_SPEW, "GBL8181" + suffix
                        + ":enterprise:length = "
                        + (enterprise.getBytes("UTF8")).length);
                m_db.test((enterprise.getBytes("UTF8")).length <= 16, "GBL8181"
                        + suffix
                        + ":enterprise length not <= 16 [passed length="
                        + (enterprise.getBytes("UTF8")).length + "]");
                m_db.test(actiontype != null, "GBL8181" + suffix
                        + ":actiontype is null");
                m_db.debug(D.EBUG_SPEW, "GBL8181" + suffix
                        + ":actiontype:length = "
                        + (actiontype.getBytes("UTF8")).length);
                m_db.test((actiontype.getBytes("UTF8")).length <= 32, "GBL8181"
                        + suffix
                        + ":actiontype length not <= 32 [passed length="
                        + (actiontype.getBytes("UTF8")).length + "]");
                m_db.test(roottype != null, "GBL8181" + suffix
                        + ":roottype is null");
                m_db.debug(D.EBUG_SPEW, "GBL8181" + suffix
                        + ":roottype:length = "
                        + (roottype.getBytes("UTF8")).length);
                m_db.test((roottype.getBytes("UTF8")).length <= 32, "GBL8181"
                        + suffix + ":roottype length not <= 32 [passed length="
                        + (roottype.getBytes("UTF8")).length + "]");
                // Build a line with combined parameter values
                StringBuffer strbParms = new StringBuffer();
                strbParms.append("" + returnStatus);
                strbParms.append(":" + sessionid_prior);
                strbParms.append(":" + sessionid_curr);
                strbParms.append(":" + enterprise);
                strbParms.append(":" + actiontype);
                strbParms.append(":" + roottype);
                strbParms.append(":" + startentityid);
                strbParms.append(":" + endentityid);
                String strParms = new String(strbParms);
                // Output the line
                m_db.debug(D.EBUG_DETAIL, "GBL8181" + suffix + " SPPARMS "
                        + strParms);
                // This will never happen, used just to keep java compiler quiet
                // in case there are no parms to SP
                if (1 == 2) {
                    throw new java.io.UnsupportedEncodingException();
                }
            } catch (java.io.UnsupportedEncodingException uex) {
                m_db.debug(D.EBUG_ERR,
                        "Threw an exception checking byte length " + uex);
            }
            if (this.c_bUsageLogging) {
                String strTransaction = "GBL8181" + suffix;
                // increment the use count for this transaction
                if (!strTransaction.equals("GBL9981")) {
                    m_db.record_transaction("GBL8181" + suffix);
                }
            }
            // Build a string containing the SQL CALL statement with parameter
            // markers
            String strSQL = "CALL opicm.GBL8181" + suffix + "(?,?,?,?,?,?,?,?)";
            // If not connected to database, now is the time!
            // After making a connection, we should have a connection handle
            m_db.test(conn != null, "callGBL8181" + suffix
                    + ":Connection handle is null");
            // If there is a prior statement something is wrong
            m_db.isPending("callGBL8181" + suffix);
            // Prepare the SQL
            m_cstmtHandle = conn.prepareCall(strSQL);
            // After preparing a statement, there should be a handle
            m_db.test(m_cstmtHandle != null, "callGBL8181" + suffix
                    + ":Statement handle is null");
            m_cstmtHandle.registerOutParameter(1, Types.INTEGER);
            // Bind the method parameters to the SQL statement markers
            m_cstmtHandle.setInt(1, returnStatus.intValue());
            m_cstmtHandle.setInt(2, sessionid_prior);
            m_cstmtHandle.setInt(3, sessionid_curr);
            m_cstmtHandle.setString(4, enterprise);
            m_cstmtHandle.setString(5, actiontype);
            m_cstmtHandle.setString(6, roottype);
            m_cstmtHandle.setInt(7, startentityid);
            m_cstmtHandle.setInt(8, endentityid);
            // Execute the SQL statement
            m_cstmtHandle.executeUpdate();
            // Retrieve the return status
            returnStatus.setValue(m_cstmtHandle.getInt(1));
        } catch (RuntimeException rx) {
            mx = new MiddlewareException("(callGBL8181" + suffix
                    + ") RuntimeException: " + rx);
            m_db.debug(D.EBUG_ERR, "RuntimeException trapped at callGBL8181"
                    + suffix + ": " + rx);
            StringWriter writer = new StringWriter();
            rx.printStackTrace(new PrintWriter(writer));
            String x = writer.toString();
            m_db.debug(D.EBUG_ERR, "" + x);
        } catch (Exception x) {
            mx = new MiddlewareException("(callGBL8181" + suffix
                    + ") Exception: " + x);
            m_db.debug(D.EBUG_ERR, "Exception trapped at callGBL8181" + suffix
                    + ": " + x);
        } finally {
            m_lFinish = System.currentTimeMillis();
            long longDuration = m_lFinish - m_lStart;
            m_db.debug(D.EBUG_INFO, "timing GBL8181" + suffix + " "
                    + Stopwatch.format(longDuration));
            if (mx != null) {
                m_db.debug(D.EBUG_DETAIL, "exiting callGBL8181" + suffix
                        + " - error");
                throw mx;
            } else {
                m_db.debug(D.EBUG_DETAIL, "exiting callGBL8181" + suffix + "");
            }
        }
    }

    /**
     * Call GBL8184 (no description)
     * 
     * @author generated code
     * @return the stored procedure output from GBL8184 as a JDBC
     *         <code>ResultSet</code>
     * @exception MiddlewareException
     */

    final public ResultSet callGBL8184(ReturnStatus returnStatus,
            int sessionID, String enterprise, String targetEntityType,
            String actionType, String roleCode, String startDate,
            String endDate, int iPass, String changedEntityType,
            int changedEntityID, int iFilter, String suffix)
            throws MiddlewareException {

        // ResultSet
        ResultSet rs = null;

        // Return value (for insert/update)
        ReturnID returnID = new ReturnID(0);
        MiddlewareException mx = null;
        long m_lStart = System.currentTimeMillis();
        long m_lFinish = 0;

        Connection conn = null;

        try {
            m_db.debug(D.EBUG_DETAIL, "executing callGBL8184" + suffix);

            String strNeedDB = "opicmpdh";

            m_db.debug(D.EBUG_DETAIL, "database needed for GBL8184" + suffix
                    + " is " + strNeedDB);

            if (strNeedDB.equals("opicmods")) {
                if (!m_db.hasODS()) {
                    throw new MiddlewareException(
                            "ODS database is not available for GBL8184"
                                    + suffix);
                }
                if (m_db.getODSConnection() == null) {
                    m_db.connect();
                }
                conn = m_db.getODSConnection();
            } else {
                if (!m_db.hasPDH()) {
                    throw new MiddlewareException(
                            "PDH database is not available for GBL8184"
                                    + suffix);
                }
                if (m_db.getPDHConnection() == null) {
                    m_db.connect();
                }
                conn = m_db.getPDHConnection();
            }

            try {
                m_db.test(enterprise != null, "GBL8184" + suffix
                        + ":Enterprise is null");
                m_db.debug(D.EBUG_SPEW, "GBL8184" + suffix
                        + ":enterprise:length = "
                        + (enterprise.getBytes("UTF8")).length);
                m_db.test((enterprise.getBytes("UTF8")).length <= 16, "GBL8184"
                        + suffix
                        + ":enterprise length not <= 16 [passed length="
                        + (enterprise.getBytes("UTF8")).length + "]");
                m_db.test(targetEntityType != null, "GBL8184" + suffix
                        + ":TargetEntityType is null");
                m_db.debug(D.EBUG_SPEW, "GBL8184" + suffix
                        + ":targetEntityType:length = "
                        + (targetEntityType.getBytes("UTF8")).length);
                m_db
                        .test(
                                (targetEntityType.getBytes("UTF8")).length <= 32,
                                "GBL8184"
                                        + suffix
                                        + ":targetEntityType length not <= 32 [passed length="
                                        + (targetEntityType.getBytes("UTF8")).length
                                        + "]");
                m_db.test(actionType != null, "GBL8184" + suffix
                        + ":ActionType is null");
                m_db.debug(D.EBUG_SPEW, "GBL8184" + suffix
                        + ":actionType:length = "
                        + (actionType.getBytes("UTF8")).length);
                m_db.test((actionType.getBytes("UTF8")).length <= 32, "GBL8184"
                        + suffix
                        + ":actionType length not <= 32 [passed length="
                        + (actionType.getBytes("UTF8")).length + "]");
                m_db.test(roleCode != null, "GBL8184" + suffix
                        + ":RoleCode is null");
                m_db.debug(D.EBUG_SPEW, "GBL8184" + suffix
                        + ":roleCode:length = "
                        + (roleCode.getBytes("UTF8")).length);
                m_db.test((roleCode.getBytes("UTF8")).length <= 32, "GBL8184"
                        + suffix + ":roleCode length not <= 32 [passed length="
                        + (roleCode.getBytes("UTF8")).length + "]");
                m_db.test(startDate != null, "GBL8184" + suffix
                        + ":StartDate is null");
                m_db.debug(D.EBUG_SPEW, "GBL8184" + suffix
                        + ":startDate:length = "
                        + (startDate.getBytes("UTF8")).length);
                m_db.test((startDate.getBytes("UTF8")).length <= 26, "GBL8184"
                        + suffix
                        + ":startDate length not <= 26 [passed length="
                        + (startDate.getBytes("UTF8")).length + "]");
                //                m_db.test(Validate.isoDate(startDate) == true,
                //                        "GBL8184" + suffix + ":startDate is not a valid ISO date ["
                //                                + startDate
                //                                + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
                m_db.test(endDate != null, "GBL8184" + suffix
                        + ":EndDate is null");
                m_db.debug(D.EBUG_SPEW, "GBL8184" + suffix
                        + ":endDate:length = "
                        + (endDate.getBytes("UTF8")).length);
                m_db.test((endDate.getBytes("UTF8")).length <= 26, "GBL8184"
                        + suffix + ":endDate length not <= 26 [passed length="
                        + (endDate.getBytes("UTF8")).length + "]");
                //                m_db.test(Validate.isoDate(endDate) == true,
                //                        "GBL8184" + suffix + ":endDate is not a valid ISO date [" +
                // endDate
                //                                + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
                m_db.test(changedEntityType != null, "GBL8184" + suffix
                        + ":ChangedEntityType is null");
                m_db.debug(D.EBUG_SPEW, "GBL8184" + suffix
                        + ":changedEntityType:length = "
                        + (changedEntityType.getBytes("UTF8")).length);
                m_db
                        .test(
                                (changedEntityType.getBytes("UTF8")).length <= 32,
                                "GBL8184"
                                        + suffix
                                        + ":changedEntityType length not <= 32 [passed length="
                                        + (changedEntityType.getBytes("UTF8")).length
                                        + "]");
                // Build a line with combined parameter values
                StringBuffer strbParms = new StringBuffer();
                strbParms.append("" + returnStatus);
                strbParms.append(":" + sessionID);
                strbParms.append(":" + enterprise);
                strbParms.append(":" + targetEntityType);
                strbParms.append(":" + actionType);
                strbParms.append(":" + roleCode);
                strbParms.append(":" + startDate);
                strbParms.append(":" + endDate);
                strbParms.append(":" + iPass);
                strbParms.append(":" + changedEntityType);
                strbParms.append(":" + changedEntityID);
                strbParms.append(":" + iFilter);
                String strParms = new String(strbParms);
                // Output the line
                m_db.debug(D.EBUG_DETAIL, "GBL8184" + suffix + " SPPARMS "
                        + strParms);
                // This will never happen, used just to keep java compiler quiet
                // in case there are no parms to SP
                if (1 == 2) {
                    throw new java.io.UnsupportedEncodingException();
                }
            } catch (java.io.UnsupportedEncodingException uex) {
                m_db.debug(D.EBUG_ERR,
                        "Threw an exception checking byte length " + uex);
            }
            if (this.c_bUsageLogging) {
                String strTransaction = "GBL8184" + suffix;
                // increment the use count for this transaction
                if (!strTransaction.equals("GBL9981")) {
                    m_db.record_transaction("GBL8184" + suffix);
                }
            }
            // Build a string containing the SQL CALL statement with parameter
            // markers
            String strSQL = "CALL opicm.GBL8184" + suffix
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)";
            // If not connected to database, now is the time!
            // After making a connection, we should have a connection handle
            m_db.test(conn != null, "callGBL8184" + suffix
                    + ":Connection handle is null");
            // If there is a prior statement something is wrong
            m_db.isPending("callGBL8184" + suffix);
            // Prepare the SQL
            m_cstmtHandle = conn.prepareCall(strSQL);
            // After preparing a statement, there should be a handle
            m_db.test(m_cstmtHandle != null, "callGBL8184" + suffix
                    + ":Statement handle is null");
            m_cstmtHandle.registerOutParameter(1, Types.INTEGER);
            // Bind the method parameters to the SQL statement markers
            m_cstmtHandle.setInt(1, returnStatus.intValue());
            m_cstmtHandle.setInt(2, sessionID);
            m_cstmtHandle.setString(3, enterprise);
            m_cstmtHandle.setString(4, targetEntityType);
            m_cstmtHandle.setString(5, actionType);
            m_cstmtHandle.setString(6, roleCode);
            m_cstmtHandle.setString(7, startDate);
            m_cstmtHandle.setString(8, endDate);
            m_cstmtHandle.setInt(9, iPass);
            m_cstmtHandle.setString(10, changedEntityType);
            m_cstmtHandle.setInt(11, changedEntityID);
            m_cstmtHandle.setInt(12, iFilter);
            // Execute the SQL statement
            rs = m_cstmtHandle.executeQuery();
            // Retrieve the return status
            returnStatus.setValue(m_cstmtHandle.getInt(1));
        } catch (RuntimeException rx) {
            mx = new MiddlewareException("(callGBL8184" + suffix
                    + ") RuntimeException: " + rx);
            m_db.debug(D.EBUG_ERR, "RuntimeException trapped at callGBL8184"
                    + suffix + ": " + rx);
            StringWriter writer = new StringWriter();
            rx.printStackTrace(new PrintWriter(writer));
            String x = writer.toString();
            m_db.debug(D.EBUG_ERR, "" + x);
            return null;
        } catch (Exception x) {
            mx = new MiddlewareException("(callGBL8184" + suffix
                    + ") Exception: " + x);
            m_db.debug(D.EBUG_ERR, "Exception trapped at callGBL8184" + suffix
                    + ": " + x);
            return null;
        } finally {
            m_lFinish = System.currentTimeMillis();
            long longDuration = m_lFinish - m_lStart;
            m_db.debug(D.EBUG_INFO, "timing GBL8184" + suffix + " "
                    + Stopwatch.format(longDuration));
            if (mx != null) {
                m_db.debug(D.EBUG_DETAIL, "exiting callGBL8184" + suffix
                        + " - error");
                throw mx;
            } else {
                m_db.debug(D.EBUG_DETAIL, "exiting callGBL8184" + suffix);
            }
        }
//      Return the ResultSet
        return rs;
    }

    /**
     * Call GBL9012 (This returns a list of blocks that have entityid's for a
     * given type (based on GBL9010))
     * 
     * @author generated code
     * @return the stored procedure output from GBL9012 as a JDBC
     *         <code>ResultSet</code>
     * @exception MiddlewareException
     */
 
    final public ResultSet callGBL9012(ReturnStatus returnStatus,
            String enterprise, String rootType, int sessionID, int chunk,
            String suffix) throws MiddlewareException {
        
        // ResultSet
        ResultSet rs = null;

        // Return value (for insert/update)
        ReturnID returnID = new ReturnID(0);
        MiddlewareException mx = null;
        long m_lStart = System.currentTimeMillis();
        long m_lFinish = 0;

        Connection conn = null;

        try {
            m_db.debug(D.EBUG_DETAIL, "executing callGBL9012" + suffix);

            String strNeedDB = "opicmpdh";

            m_db.debug(D.EBUG_DETAIL, "database needed for GBL9012" + suffix
                    + " is " + strNeedDB);

            if (strNeedDB.equals("opicmods")) {
                if (!m_db.hasODS()) {
                    throw new MiddlewareException(
                            "ODS database is not available for GBL9012"
                                    + suffix);
                }
                if (m_db.getODSConnection() == null) {
                    m_db.connect();
                }
                conn = m_db.getODSConnection();
            } else {
                if (!m_db.hasPDH()) {
                    throw new MiddlewareException(
                            "PDH database is not available for GBL9012"
                                    + suffix);
                }
                if (m_db.getPDHConnection() == null) {
                    m_db.connect();
                }
                conn = m_db.getPDHConnection();
            }

            try {
                m_db.test(enterprise != null, "GBL9012" + suffix
                        + ":Enterprise is null");
                m_db.debug(D.EBUG_SPEW, "GBL9012" + suffix
                        + ":enterprise:length = "
                        + (enterprise.getBytes("UTF8")).length);
                m_db.test((enterprise.getBytes("UTF8")).length <= 16, "GBL9012"
                        + suffix
                        + ":enterprise length not <= 16 [passed length="
                        + (enterprise.getBytes("UTF8")).length + "]");
                m_db.test(rootType != null, "GBL9012" + suffix
                        + ":RootType is null");
                m_db.debug(D.EBUG_SPEW, "GBL9012" + suffix
                        + ":rootType:length = "
                        + (rootType.getBytes("UTF8")).length);
                m_db.test((rootType.getBytes("UTF8")).length <= 32, "GBL9012"
                        + suffix + ":rootType length not <= 32 [passed length="
                        + (rootType.getBytes("UTF8")).length + "]");
                // Build a line with combined parameter values
                StringBuffer strbParms = new StringBuffer();
                strbParms.append("" + returnStatus);
                strbParms.append(":" + enterprise);
                strbParms.append(":" + rootType);
                strbParms.append(":" + sessionID);
                strbParms.append(":" + chunk);
                String strParms = new String(strbParms);
                // Output the line
                m_db.debug(D.EBUG_DETAIL, "GBL9012" + suffix + " SPPARMS "
                        + strParms);
                // This will never happen, used just to keep java compiler quiet
                // in case there are no parms to SP
                if (1 == 2) {
                    throw new java.io.UnsupportedEncodingException();
                }
            } catch (java.io.UnsupportedEncodingException uex) {
                m_db.debug(D.EBUG_ERR,
                        "Threw an exception checking byte length " + uex);
            }
            if (this.c_bUsageLogging) {
                String strTransaction = "GBL9012" + suffix;
                // increment the use count for this transaction
                if (!strTransaction.equals("GBL9981")) {
                    m_db.record_transaction("GBL9012" + suffix);
                }
            }
            // Build a string containing the SQL CALL statement with parameter
            // markers
            String strSQL = "CALL opicm.GBL9012" + suffix + "(?,?,?,?,?)";
            // If not connected to database, now is the time!
            // After making a connection, we should have a connection handle
            m_db.test(conn != null, "callGBL9012" + suffix
                    + ":Connection handle is null");
            // If there is a prior statement something is wrong
            m_db.isPending("callGBL9012" + suffix);
            // Prepare the SQL
            m_cstmtHandle = conn.prepareCall(strSQL);
            // After preparing a statement, there should be a handle
            m_db.test(m_cstmtHandle != null, "callGBL9012" + suffix
                    + ":Statement handle is null");
            m_cstmtHandle.registerOutParameter(1, Types.INTEGER);
            // Bind the method parameters to the SQL statement markers
            m_cstmtHandle.setInt(1, returnStatus.intValue());
            m_cstmtHandle.setString(2, enterprise);
            m_cstmtHandle.setString(3, rootType);
            m_cstmtHandle.setInt(4, sessionID);
            m_cstmtHandle.setInt(5, chunk);
            // Execute the SQL statement
            rs = m_cstmtHandle.executeQuery();
            // Retrieve the return status
            returnStatus.setValue(m_cstmtHandle.getInt(1));
        } catch (RuntimeException rx) {
            mx = new MiddlewareException("(callGBL9012" + suffix
                    + ") RuntimeException: " + rx);
            m_db.debug(D.EBUG_ERR, "RuntimeException trapped at callGBL9012"
                    + suffix + ": " + rx);
            StringWriter writer = new StringWriter();
            rx.printStackTrace(new PrintWriter(writer));
            String x = writer.toString();
            m_db.debug(D.EBUG_ERR, "" + x);
            return null;
        } catch (Exception x) {
            mx = new MiddlewareException("(callGBL9012" + suffix
                    + ") Exception: " + x);
            m_db.debug(D.EBUG_ERR, "Exception trapped at callGBL9012" + suffix
                    + ": " + x);
            return null;
        } finally {
            m_lFinish = System.currentTimeMillis();
            long longDuration = m_lFinish - m_lStart;
            m_db.debug(D.EBUG_INFO, "timing GBL9012" + suffix + " "
                    + Stopwatch.format(longDuration));
            if (mx != null) {
                m_db.debug(D.EBUG_DETAIL, "exiting callGBL9012" + suffix
                        + " - error");
                throw mx;
            } else {
                m_db.debug(D.EBUG_DETAIL, "exiting callGBL9012" + suffix + "");
            }
        }
		//Return the ResultSet
        return rs;
    }
    /**
    * Call GBL4025 ( Deactivates the  the Product Structure)
    * @author generated code
    * @return the stored procedure output from GBL4025 as a JDBC <code>ResultSet</code>
    * @exception MiddlewareException
    */

    final public void callGBL4025
    (
        ReturnStatus returnStatus
      , String enterprise
      , String entityType
      , int entityID
      , String countryCode
      , String languageCode
      , int nlsID
    ) throws MiddlewareException {

        // ResultSet
        ResultSet rs = null;

        // Return value (for insert/update)
        ReturnID returnID = new ReturnID(0);
        MiddlewareException mx = null;
        long m_lStart = System.currentTimeMillis();
        long m_lFinish = 0;

        Connection conn = null;

        try {
        	m_db.debug(D.EBUG_DETAIL, "executing callGBL4025A");

            String strNeedDB = "opicmpdh";
        
            m_db.debug(D.EBUG_DETAIL, "database needed for GBL4025A is " + strNeedDB);


            if (strNeedDB.equals("opicmods")) {
                if (!m_db.hasODS()) {
                    throw new MiddlewareException(
                            "ODS database is not available for GBL4025A"
                                    );
                }
                if (m_db.getODSConnection() == null) {
                    m_db.connect();
                }
                conn = m_db.getODSConnection();
            } else {
                if (!m_db.hasPDH()) {
                    throw new MiddlewareException(
                            "PDH database is not available for GBL4025A"
                                    );
                }
                if (m_db.getPDHConnection() == null) {
                    m_db.connect();
                }
                conn = m_db.getPDHConnection();
            }
            try {
            // Build a line with combined parameter values
            StringBuffer strbParms = new StringBuffer();
            strbParms.append("" + returnStatus);
            strbParms.append(":" + enterprise);
            strbParms.append(":" + entityType);
            strbParms.append(":" + entityID);
            strbParms.append(":" + countryCode);
            strbParms.append(":" + languageCode);
            strbParms.append(":" + nlsID);
            String strParms = new String(strbParms);
            // Output the line
            m_db.debug(D.EBUG_DETAIL, "GBL4025A SPPARMS " + strParms);
                // This will never happen, used just to keep java compiler quiet in case there are no parms to SP
                if (1 == 2) {
                    throw new java.io.UnsupportedEncodingException();
                }
            }
            catch (java.io.UnsupportedEncodingException uex) {
            	m_db.debug(D.EBUG_ERR, "Threw an exception checking byte length " + uex);
            }
             if (this.c_bUsageLogging) {
                String strTransaction = "GBL4025A";
                // increment the use count for this transaction
                if (!strTransaction.equals("GBL9981")) {
                	m_db.record_transaction("GBL4025A");
                }
            }
            // Build a string containing the SQL CALL statement with parameter markers
            String strSQL = "CALL opicm.GBL4025A(?,?,?,?,?,?,?)";
            // If not connected to database, now is the time!
            // After making a connection, we should have a connection handle
            m_db.test(conn != null, "callGBL4025A:Connection handle is null");
            // If there is a prior statement something is wrong
            m_db.isPending("callGBL4025A");
            // Prepare the SQL
            m_cstmtHandle = conn.prepareCall(strSQL);
            // After preparing a statement, there should be a handle
            m_db.test(m_cstmtHandle != null, "callGBL4025A:Statement handle is null");
            m_cstmtHandle.registerOutParameter(1, Types.INTEGER);
            // Bind the method parameters to the SQL statement markers
          m_cstmtHandle.setInt(1, returnStatus.intValue());
          m_cstmtHandle.setString(2, enterprise);
          m_cstmtHandle.setString(3, entityType);
          m_cstmtHandle.setInt(4, entityID);
          m_cstmtHandle.setString(5, countryCode);
          m_cstmtHandle.setString(6, languageCode);
          m_cstmtHandle.setInt(7, nlsID);
            // Execute the SQL statement
            m_cstmtHandle.executeUpdate();
            // Retrieve the return status
            returnStatus.setValue(m_cstmtHandle.getInt(1));
            } catch (RuntimeException rx) {
                mx = new MiddlewareException("(callGBL4025A) RuntimeException: " + rx);
                m_db.debug(D.EBUG_ERR, "RuntimeException trapped at callGBL4025A: " + rx);
                StringWriter writer = new StringWriter();
                rx.printStackTrace(new PrintWriter(writer));
                String x = writer.toString();
                m_db.debug(D.EBUG_ERR, "" + x);
            } catch (Exception x) {
                mx = new MiddlewareException("(callGBL4025A) Exception: " + x);
                m_db.debug(D.EBUG_ERR, "Exception trapped at callGBL4025A: " + x);
            } finally {
                m_lFinish = System.currentTimeMillis();
                long longDuration = m_lFinish - m_lStart;
                m_db.debug(D.EBUG_INFO, "timing GBL4025A " + Stopwatch.format(longDuration));
                if (mx != null) {
                	m_db.debug(D.EBUG_DETAIL, "exiting callGBL4025A - error");
                    throw mx;
                } else {
                	m_db.debug(D.EBUG_DETAIL, "exiting callGBL4025A");
                    // Return the ResultSet
                    return;
                }
            }
    }
    
    /**
     * Call GBL8109A ( Deactivates the  the Product Structure)
     * @param returnStatus
     * @param sessionID
     * @param enterprise
     * @return
     * @throws MiddlewareException
     */
    final public void callGBL8109A(ReturnStatus returnStatus, int sessionID,
			String enterprise) throws MiddlewareException {

		// ResultSet
		ResultSet rs = null;

		// Return value (for insert/update)
		ReturnID returnID = new ReturnID(0);
		MiddlewareException mx = null;
		long m_lStart = System.currentTimeMillis();
		long m_lFinish = 0;

		Connection conn = null;

		try {
			m_db.debug(D.EBUG_DETAIL, "executing callGBL8109A");

			String strNeedDB = "opicmpdh";

			m_db.debug(D.EBUG_DETAIL, "database needed for callGBL8109A is "
					+ strNeedDB);

			if (strNeedDB.equals("opicmods")) {
				if (!m_db.hasODS()) {
					throw new MiddlewareException(
							"ODS database is not available for GBL8109A");
				}
				if (m_db.getODSConnection() == null) {
					m_db.connect();
				}
				conn = m_db.getODSConnection();
			} else {
				if (!m_db.hasPDH()) {
					throw new MiddlewareException(
							"PDH database is not available for GBL8109A");
				}
				if (m_db.getPDHConnection() == null) {
					m_db.connect();
				}
				conn = m_db.getPDHConnection();
			}

			try {
				m_db.test(enterprise != null, "GBL8109A:enterprise is null");
				m_db.debug(D.EBUG_SPEW, "GBL8109A:enterprise:length = "
						+ (enterprise.getBytes("UTF8")).length);
				m_db.test((enterprise.getBytes("UTF8")).length <= 16,
						"GBL8109A:enterprise length not <= 16 [passed length="
								+ (enterprise.getBytes("UTF8")).length + "]");
				// Build a line with combined parameter values
				StringBuffer strbParms = new StringBuffer();
				strbParms.append("" + returnStatus);
				strbParms.append(":" + sessionID);
				strbParms.append(":" + enterprise);
				String strParms = new String(strbParms);
				// Output the line
				m_db.debug(D.EBUG_DETAIL, "GBL8109A SPPARMS " + strParms);
				// This will never happen, used just to keep java compiler quiet
				// in case there are no parms to SP
				if (1 == 2) {
					throw new java.io.UnsupportedEncodingException();
				}
			} catch (java.io.UnsupportedEncodingException uex) {
				m_db.debug(D.EBUG_ERR,
						"Threw an exception checking byte length " + uex);
			}
			if (this.c_bUsageLogging) {
				String strTransaction = "GBL8109A";
				// increment the use count for this transaction
				if (!strTransaction.equals("GBL9981")) {
					m_db.record_transaction("GBL8109A");
				}
			}
			// Build a string containing the SQL CALL statement with parameter
			// markers
			String strSQL = "CALL opicm.GBL8109A(?,?,?)";
			// If not connected to database, now is the time!
			// After making a connection, we should have a connection handle
			m_db.test(conn != null, "callGBL8109A:Connection handle is null");
			// If there is a prior statement something is wrong
			m_db.isPending("callGBL8109A");
			// Prepare the SQL
			m_cstmtHandle = conn.prepareCall(strSQL);
			// After preparing a statement, there should be a handle
			m_db.test(m_cstmtHandle != null,
					"callGBL8109A:Statement handle is null");
			m_cstmtHandle.registerOutParameter(1, Types.INTEGER);
			// Bind the method parameters to the SQL statement markers
			m_cstmtHandle.setInt(1, returnStatus.intValue());
			m_cstmtHandle.setInt(2, sessionID);
			m_cstmtHandle.setString(3, enterprise);
			// Execute the SQL statement
			m_cstmtHandle.executeUpdate();
			// Retrieve the return status
			returnStatus.setValue(m_cstmtHandle.getInt(1));
		} catch (RuntimeException rx) {
			mx = new MiddlewareException("(callGBL8109A) RuntimeException: "
					+ rx);
			m_db.debug(D.EBUG_ERR, "RuntimeException trapped at callGBL8109A: "
					+ rx);
			StringWriter writer = new StringWriter();
			rx.printStackTrace(new PrintWriter(writer));
			String x = writer.toString();
			m_db.debug(D.EBUG_ERR, "" + x);
		} catch (Exception x) {
			mx = new MiddlewareException("(callGBL8109A) Exception: " + x);
			m_db.debug(D.EBUG_ERR, "Exception trapped at callGBL8109A: " + x);
		} finally {
			m_lFinish = System.currentTimeMillis();
			long longDuration = m_lFinish - m_lStart;
			m_db.debug(D.EBUG_INFO, "timing GBL8109A "
					+ Stopwatch.format(longDuration));
			if (mx != null) {
				m_db.debug(D.EBUG_DETAIL, "exiting callGBL8109A - error");
				throw mx;
			} else {
				m_db.debug(D.EBUG_DETAIL, "exiting callGBL8109A");
			}
		}
	}
    /**
    * Call GBL8981 ( Manages the update of an ProdStruct Record)
    * @author generated code
    * @return the stored procedure output from GBL8981 as a JDBC <code>ResultSet</code>
    * @exception MiddlewareException
    */

    final public void callGBL8981A
    (
        ReturnStatus returnStatus
      , String enterprise
      , String countrycode
      , String languagecode
      , int nlsID
      , String prodentitytype
      , int prodentityid
      , String structentitytype
      , int structentityid
      , String featentitytype
      , int featentityid
      , String ordercode
      , String ordercode_fc
      , String oslevel
      , String oslevel_fc
      , String anndayte
      , String withdrawdayte
      , String pubfrom
      , String pubto
      , int systemmax
      , int systemmin
      , int confqty
      , String status
      , String status_fc
      , String configuratorflag_fc
      , String CFGFLAG
      , String CFGFLAG_FC
      , int isactive
    ) throws MiddlewareException {

        // ResultSet
        ResultSet rs = null;

        // Return value (for insert/update)
        ReturnID returnID = new ReturnID(0);
        MiddlewareException mx = null;
        long m_lStart = System.currentTimeMillis();
        long m_lFinish = 0;

        Connection conn = null;

        try {
            m_db.debug(D.EBUG_DETAIL, "executing callGBL8981A");

            String strNeedDB = "opicmpdh";
        
            m_db.debug(D.EBUG_DETAIL, "database needed for GBL8981A is " + strNeedDB);

            if (strNeedDB.equals("opicmods")) {
                if (!m_db.hasODS()) {
                    throw new MiddlewareException("ODS database is not available for GBL8981A");
                }
                if (m_db.getODSConnection() == null) {
                	m_db.connect();
                }
                conn = m_db.getODSConnection();
            } else {
                if (!m_db.hasPDH()) {
                    throw new MiddlewareException("PDH database is not available for GBL8981A");
                }
                if (m_db.getPDHConnection() == null) {
                	m_db.connect();
                }
                conn = m_db.getPDHConnection();
            }

            try {
            m_db.test(enterprise != null, "GBL8981A:enterprise is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
            m_db.test((enterprise.getBytes("UTF8")).length <= 8, "GBL8981A:enterprise length not <= 8 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
            m_db.test(countrycode != null, "GBL8981A:countrycode is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:countrycode:length = " + (countrycode.getBytes("UTF8")).length);
            m_db.test((countrycode.getBytes("UTF8")).length <= 2, "GBL8981A:countrycode length not <= 2 [passed length=" + (countrycode.getBytes("UTF8")).length + "]");
            m_db.test(languagecode != null, "GBL8981A:languagecode is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:languagecode:length = " + (languagecode.getBytes("UTF8")).length);
            m_db.test((languagecode.getBytes("UTF8")).length <= 2, "GBL8981A:languagecode length not <= 2 [passed length=" + (languagecode.getBytes("UTF8")).length + "]");
            m_db.test(prodentitytype != null, "GBL8981A:prodentitytype is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:prodentitytype:length = " + (prodentitytype.getBytes("UTF8")).length);
            m_db.test((prodentitytype.getBytes("UTF8")).length <= 32, "GBL8981A:prodentitytype length not <= 32 [passed length=" + (prodentitytype.getBytes("UTF8")).length + "]");
            m_db.test(structentitytype != null, "GBL8981A:structentitytype is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:structentitytype:length = " + (structentitytype.getBytes("UTF8")).length);
            m_db.test((structentitytype.getBytes("UTF8")).length <= 32, "GBL8981A:structentitytype length not <= 32 [passed length=" + (structentitytype.getBytes("UTF8")).length + "]");
            m_db.test(featentitytype != null, "GBL8981A:featentitytype is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:featentitytype:length = " + (featentitytype.getBytes("UTF8")).length);
            m_db.test((featentitytype.getBytes("UTF8")).length <= 32, "GBL8981A:featentitytype length not <= 32 [passed length=" + (featentitytype.getBytes("UTF8")).length + "]");
            m_db.test(ordercode != null, "GBL8981A:ordercode is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:ordercode:length = " + (ordercode.getBytes("UTF8")).length);
            m_db.test((ordercode.getBytes("UTF8")).length <= 25, "GBL8981A:ordercode length not <= 25 [passed length=" + (ordercode.getBytes("UTF8")).length + "]");
            m_db.test(ordercode_fc != null, "GBL8981A:ordercode_fc is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:ordercode_fc:length = " + (ordercode_fc.getBytes("UTF8")).length);
            m_db.test((ordercode_fc.getBytes("UTF8")).length <= 8, "GBL8981A:ordercode_fc length not <= 8 [passed length=" + (ordercode_fc.getBytes("UTF8")).length + "]");
            m_db.test(oslevel != null, "GBL8981A:oslevel is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:oslevel:length = " + (oslevel.getBytes("UTF8")).length);
            m_db.test((oslevel.getBytes("UTF8")).length <= 4096, "GBL8981A:oslevel length not <= 4096 [passed length=" + (oslevel.getBytes("UTF8")).length + "]");
            m_db.test(oslevel_fc != null, "GBL8981A:oslevel_fc is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:oslevel_fc:length = " + (oslevel_fc.getBytes("UTF8")).length);
            m_db.test((oslevel_fc.getBytes("UTF8")).length <= 1024, "GBL8981A:oslevel_fc length not <= 1024 [passed length=" + (oslevel_fc.getBytes("UTF8")).length + "]");
            m_db.test(anndayte != null, "GBL8981A:anndayte is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:anndayte:length = " + (anndayte.getBytes("UTF8")).length);
            m_db.test((anndayte.getBytes("UTF8")).length <= 12, "GBL8981A:anndayte length not <= 12 [passed length=" + (anndayte.getBytes("UTF8")).length + "]");
            m_db.test(withdrawdayte != null, "GBL8981A:withdrawdayte is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:withdrawdayte:length = " + (withdrawdayte.getBytes("UTF8")).length);
            m_db.test((withdrawdayte.getBytes("UTF8")).length <= 12, "GBL8981A:withdrawdayte length not <= 12 [passed length=" + (withdrawdayte.getBytes("UTF8")).length + "]");
            m_db.test(pubfrom != null, "GBL8981A:pubfrom is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:pubfrom:length = " + (pubfrom.getBytes("UTF8")).length);
            m_db.test((pubfrom.getBytes("UTF8")).length <= 12, "GBL8981A:pubfrom length not <= 12 [passed length=" + (pubfrom.getBytes("UTF8")).length + "]");
            m_db.test(pubto != null, "GBL8981A:pubto is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:pubto:length = " + (pubto.getBytes("UTF8")).length);
            m_db.test((pubto.getBytes("UTF8")).length <= 12, "GBL8981A:pubto length not <= 12 [passed length=" + (pubto.getBytes("UTF8")).length + "]");
            m_db.test(status != null, "GBL8981A:status is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:status:length = " + (status.getBytes("UTF8")).length);
            m_db.test((status.getBytes("UTF8")).length <= 15, "GBL8981A:status length not <= 15 [passed length=" + (status.getBytes("UTF8")).length + "]");
            m_db.test(status_fc != null, "GBL8981A:status_fc is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:status_fc:length = " + (status_fc.getBytes("UTF8")).length);
            m_db.test((status_fc.getBytes("UTF8")).length <= 8, "GBL8981A:status_fc length not <= 8 [passed length=" + (status_fc.getBytes("UTF8")).length + "]");
            m_db.test(configuratorflag_fc != null, "GBL8981A:configuratorflag_fc is null");
            m_db.debug(D.EBUG_SPEW, "GBL8981A:configuratorflag_fc:length = " + (configuratorflag_fc.getBytes("UTF8")).length);
            m_db.test((configuratorflag_fc.getBytes("UTF8")).length <= 8, "GBL8981A:configuratorflag_fc length not <= 8 [passed length=" + (configuratorflag_fc.getBytes("UTF8")).length + "]");
            // Build a line with combined parameter values
            StringBuffer strbParms = new StringBuffer();
            strbParms.append("" + returnStatus);
            strbParms.append(":" + enterprise);
            strbParms.append(":" + countrycode);
            strbParms.append(":" + languagecode);
            strbParms.append(":" + nlsID);
            strbParms.append(":" + prodentitytype);
            strbParms.append(":" + prodentityid);
            strbParms.append(":" + structentitytype);
            strbParms.append(":" + structentityid);
            strbParms.append(":" + featentitytype);
            strbParms.append(":" + featentityid);
            strbParms.append(":" + ordercode);
            strbParms.append(":" + ordercode_fc);
            strbParms.append(":" + oslevel);
            strbParms.append(":" + oslevel_fc);
            strbParms.append(":" + anndayte);
            strbParms.append(":" + withdrawdayte);
            strbParms.append(":" + pubfrom);
            strbParms.append(":" + pubto);
            strbParms.append(":" + systemmax);
            strbParms.append(":" + systemmin);
            strbParms.append(":" + confqty);
            strbParms.append(":" + status);
            strbParms.append(":" + status_fc);
            strbParms.append(":" + configuratorflag_fc);
            strbParms.append(":" + CFGFLAG);
            strbParms.append(":" + CFGFLAG_FC); 
            strbParms.append(":" + isactive);
            String strParms = new String(strbParms);
            // Output the line
            m_db.debug(D.EBUG_DETAIL, "GBL8981A SPPARMS " + strParms);
                // This will never happen, used just to keep java compiler quiet in case there are no parms to SP
                if (1 == 2) {
                    throw new java.io.UnsupportedEncodingException();
                }
            }
            catch (java.io.UnsupportedEncodingException uex) {
                m_db.debug(D.EBUG_ERR, "Threw an exception checking byte length " + uex);
            }
            if (this.c_bUsageLogging) {
                String strTransaction = "GBL8981A";
                // increment the use count for this transaction
                if (!strTransaction.equals("GBL9981")) {
                	m_db.record_transaction("GBL8981A");
                }
            }
            // Build a string containing the SQL CALL statement with parameter markers
            String strSQL = "CALL opicm.GBL8981A(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            // If not connected to database, now is the time!
            // After making a connection, we should have a connection handle
            m_db.test(conn != null, "callGBL8981A:Connection handle is null");
            // If there is a prior statement something is wrong
            m_db.isPending("callGBL8981A");
            // Prepare the SQL
            m_cstmtHandle = conn.prepareCall(strSQL);
            // After preparing a statement, there should be a handle
            m_db.test(m_cstmtHandle != null, "callGBL8981A:Statement handle is null");
            m_cstmtHandle.registerOutParameter(1, Types.INTEGER);
            // Bind the method parameters to the SQL statement markers
          m_cstmtHandle.setInt(1, returnStatus.intValue());
          m_cstmtHandle.setString(2, enterprise);
          m_cstmtHandle.setString(3, countrycode);
          m_cstmtHandle.setString(4, languagecode);
          m_cstmtHandle.setInt(5, nlsID);
          m_cstmtHandle.setString(6, prodentitytype);
          m_cstmtHandle.setInt(7, prodentityid);
          m_cstmtHandle.setString(8, structentitytype);
          m_cstmtHandle.setInt(9, structentityid);
          m_cstmtHandle.setString(10, featentitytype);
          m_cstmtHandle.setInt(11, featentityid);
          m_cstmtHandle.setString(12, ordercode);
          m_cstmtHandle.setString(13, ordercode_fc);
          m_cstmtHandle.setString(14, oslevel);
          m_cstmtHandle.setString(15, oslevel_fc);
          m_cstmtHandle.setString(16, anndayte);
          m_cstmtHandle.setString(17, withdrawdayte);
          m_cstmtHandle.setString(18, pubfrom);
          m_cstmtHandle.setString(19, pubto);
          m_cstmtHandle.setInt(20, systemmax);
          m_cstmtHandle.setInt(21, systemmin);
          m_cstmtHandle.setInt(22, confqty);
          m_cstmtHandle.setString(23, status);
          m_cstmtHandle.setString(24, status_fc);
          m_cstmtHandle.setString(25, configuratorflag_fc);
          m_cstmtHandle.setString(26, CFGFLAG);
          m_cstmtHandle.setString(27, CFGFLAG_FC);
          m_cstmtHandle.setInt(28, isactive);
            // Execute the SQL statement
            m_cstmtHandle.executeUpdate();
            // Retrieve the return status
            returnStatus.setValue(m_cstmtHandle.getInt(1));
            } catch (RuntimeException rx) {
                mx = new MiddlewareException("(callGBL8981A) RuntimeException: " + rx);
                m_db.debug(D.EBUG_ERR, "RuntimeException trapped at callGBL8981A: " + rx);
                StringWriter writer = new StringWriter();
                rx.printStackTrace(new PrintWriter(writer));
                String x = writer.toString();
                m_db.debug(D.EBUG_ERR, "" + x);
            } catch (Exception x) {
                mx = new MiddlewareException("(callGBL8981A) Exception: " + x);
                m_db.debug(D.EBUG_ERR, "Exception trapped at callGBL8981A: " + x);
            } finally {
                m_lFinish = System.currentTimeMillis();
                long longDuration = m_lFinish - m_lStart;
                m_db.debug(D.EBUG_INFO, "timing GBL8981A " + Stopwatch.format(longDuration));
                if (mx != null) {
                    m_db.debug(D.EBUG_DETAIL, "exiting callGBL8981A - error");
                    throw mx;
                } else {
                    m_db.debug(D.EBUG_DETAIL, "exiting callGBL8981A");
                    // Return the ResultSet
                    return;
                }
            }
    }
    
    /**
     * Call GBL8980A ( Manages the update of an WWProduct Reocrd)
     * @author generated code
     * @return the stored procedure output from GBL8980A as a JDBC <code>ResultSet</code>
     * @exception MiddlewareException
     */

     final public void callGBL8980A
     (
         ReturnStatus returnStatus
       , String enterprise
       , String countrycode
       , String languagecode
       , int nlsID
       , String countrylist
       , String featentitytype
       , int featentityid
       , String featurecode
       , String fcmktgname
       , String fctype
       , String fctype_fc
       , String anndate
       , String withdrawdate
       , String pricedfeature
       , String pricedfeature_fc
       , String tandc
       , String category
       , String category_fc
       , String subcategory
       , String subcategory_fc
       , String group
       , String group_fc
       , String cgtype
       , String cgtype_fc
       , String oslevel
       , String oslevel_fc
       , String status
       , String status_fc
       , String configuratorflag
       , String configuratorflag_fc
       , int isactive
     ) throws MiddlewareException {

         // ResultSet
         ResultSet rs = null;

         // Return value (for insert/update)
         ReturnID returnID = new ReturnID(0);
         MiddlewareException mx = null;
         long m_lStart = System.currentTimeMillis();
         long m_lFinish = 0;

         Connection conn = null;

         try {
             debug(D.EBUG_DETAIL, "executing callGBL8980A");

             String strNeedDB = "opicmpdh";
         
             debug(D.EBUG_DETAIL, "database needed for GBL8980A is " + strNeedDB);

             if (strNeedDB.equals("opicmods")) {
                 if (!m_db.hasODS()) {
                     throw new MiddlewareException("ODS database is not available for GBL8981A");
                 }
                 if (m_db.getODSConnection() == null) {
                 	m_db.connect();
                 }
                 conn = m_db.getODSConnection();
             } else {
                 if (!m_db.hasPDH()) {
                     throw new MiddlewareException("PDH database is not available for GBL8981A");
                 }
                 if (m_db.getPDHConnection() == null) {
                 	m_db.connect();
                 }
                 conn = m_db.getPDHConnection();
             }

             try {
             test(enterprise != null, "GBL8980A:enterprise is null");
             debug(D.EBUG_SPEW, "GBL8980A:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
             test((enterprise.getBytes("UTF8")).length <= 8, "GBL8980A:enterprise length not <= 8 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
             test(countrycode != null, "GBL8980A:countrycode is null");
             debug(D.EBUG_SPEW, "GBL8980A:countrycode:length = " + (countrycode.getBytes("UTF8")).length);
             test((countrycode.getBytes("UTF8")).length <= 2, "GBL8980A:countrycode length not <= 2 [passed length=" + (countrycode.getBytes("UTF8")).length + "]");
             test(languagecode != null, "GBL8980A:languagecode is null");
             debug(D.EBUG_SPEW, "GBL8980A:languagecode:length = " + (languagecode.getBytes("UTF8")).length);
             test((languagecode.getBytes("UTF8")).length <= 2, "GBL8980A:languagecode length not <= 2 [passed length=" + (languagecode.getBytes("UTF8")).length + "]");
             test(countrylist != null, "GBL8980A:countrylist is null");
             debug(D.EBUG_SPEW, "GBL8980A:countrylist:length = " + (countrylist.getBytes("UTF8")).length);
             test((countrylist.getBytes("UTF8")).length <= 8, "GBL8980A:countrylist length not <= 8 [passed length=" + (countrylist.getBytes("UTF8")).length + "]");
             test(featentitytype != null, "GBL8980A:featentitytype is null");
             debug(D.EBUG_SPEW, "GBL8980A:featentitytype:length = " + (featentitytype.getBytes("UTF8")).length);
             test((featentitytype.getBytes("UTF8")).length <= 32, "GBL8980A:featentitytype length not <= 32 [passed length=" + (featentitytype.getBytes("UTF8")).length + "]");
             test(featurecode != null, "GBL8980A:featurecode is null");
             debug(D.EBUG_SPEW, "GBL8980A:featurecode:length = " + (featurecode.getBytes("UTF8")).length);
             test((featurecode.getBytes("UTF8")).length <= 8, "GBL8980A:featurecode length not <= 8 [passed length=" + (featurecode.getBytes("UTF8")).length + "]");
             test(fcmktgname != null, "GBL8980A:fcmktgname is null");
             debug(D.EBUG_SPEW, "GBL8980A:fcmktgname:length = " + (fcmktgname.getBytes("UTF8")).length);
             test((fcmktgname.getBytes("UTF8")).length <= 254, "GBL8980A:fcmktgname length not <= 254 [passed length=" + (fcmktgname.getBytes("UTF8")).length + "]");
             test(fctype != null, "GBL8980A:fctype is null");
             debug(D.EBUG_SPEW, "GBL8980A:fctype:length = " + (fctype.getBytes("UTF8")).length);
             test((fctype.getBytes("UTF8")).length <= 32, "GBL8980A:fctype length not <= 32 [passed length=" + (fctype.getBytes("UTF8")).length + "]");
             test(fctype_fc != null, "GBL8980A:fctype_fc is null");
             debug(D.EBUG_SPEW, "GBL8980A:fctype_fc:length = " + (fctype_fc.getBytes("UTF8")).length);
             test((fctype_fc.getBytes("UTF8")).length <= 8, "GBL8980A:fctype_fc length not <= 8 [passed length=" + (fctype_fc.getBytes("UTF8")).length + "]");
             test(anndate != null, "GBL8980A:anndate is null");
             debug(D.EBUG_SPEW, "GBL8980A:anndate:length = " + (anndate.getBytes("UTF8")).length);
             test((anndate.getBytes("UTF8")).length <= 10, "GBL8980A:anndate length not <= 10 [passed length=" + (anndate.getBytes("UTF8")).length + "]");
             test(withdrawdate != null, "GBL8980A:withdrawdate is null");
             debug(D.EBUG_SPEW, "GBL8980A:withdrawdate:length = " + (withdrawdate.getBytes("UTF8")).length);
             test((withdrawdate.getBytes("UTF8")).length <= 10, "GBL8980A:withdrawdate length not <= 10 [passed length=" + (withdrawdate.getBytes("UTF8")).length + "]");
             test(pricedfeature != null, "GBL8980A:pricedfeature is null");
             debug(D.EBUG_SPEW, "GBL8980A:pricedfeature:length = " + (pricedfeature.getBytes("UTF8")).length);
             test((pricedfeature.getBytes("UTF8")).length <= 32, "GBL8980A:pricedfeature length not <= 32 [passed length=" + (pricedfeature.getBytes("UTF8")).length + "]");
             test(pricedfeature_fc != null, "GBL8980A:pricedfeature_fc is null");
             debug(D.EBUG_SPEW, "GBL8980A:pricedfeature_fc:length = " + (pricedfeature_fc.getBytes("UTF8")).length);
             test((pricedfeature_fc.getBytes("UTF8")).length <= 8, "GBL8980A:pricedfeature_fc length not <= 8 [passed length=" + (pricedfeature_fc.getBytes("UTF8")).length + "]");
             test(tandc != null, "GBL8980A:tandc is null");
             debug(D.EBUG_SPEW, "GBL8980A:tandc:length = " + (tandc.getBytes("UTF8")).length);
             test((tandc.getBytes("UTF8")).length <= 128, "GBL8980A:tandc length not <= 128 [passed length=" + (tandc.getBytes("UTF8")).length + "]");
             test(category != null, "GBL8980A:category is null");
             debug(D.EBUG_SPEW, "GBL8980A:category:length = " + (category.getBytes("UTF8")).length);
             test((category.getBytes("UTF8")).length <= 32, "GBL8980A:category length not <= 32 [passed length=" + (category.getBytes("UTF8")).length + "]");
             test(category_fc != null, "GBL8980A:category_fc is null");
             debug(D.EBUG_SPEW, "GBL8980A:category_fc:length = " + (category_fc.getBytes("UTF8")).length);
             test((category_fc.getBytes("UTF8")).length <= 8, "GBL8980A:category_fc length not <= 8 [passed length=" + (category_fc.getBytes("UTF8")).length + "]");
             test(subcategory != null, "GBL8980A:subcategory is null");
             debug(D.EBUG_SPEW, "GBL8980A:subcategory:length = " + (subcategory.getBytes("UTF8")).length);
             test((subcategory.getBytes("UTF8")).length <= 64, "GBL8980A:subcategory length not <= 64 [passed length=" + (subcategory.getBytes("UTF8")).length + "]");
             test(subcategory_fc != null, "GBL8980A:subcategory_fc is null");
             debug(D.EBUG_SPEW, "GBL8980A:subcategory_fc:length = " + (subcategory_fc.getBytes("UTF8")).length);
             test((subcategory_fc.getBytes("UTF8")).length <= 8, "GBL8980A:subcategory_fc length not <= 8 [passed length=" + (subcategory_fc.getBytes("UTF8")).length + "]");
             test(group != null, "GBL8980A:group is null");
             debug(D.EBUG_SPEW, "GBL8980A:group:length = " + (group.getBytes("UTF8")).length);
             test((group.getBytes("UTF8")).length <= 32, "GBL8980A:group length not <= 32 [passed length=" + (group.getBytes("UTF8")).length + "]");
             test(group_fc != null, "GBL8980A:group_fc is null");
             debug(D.EBUG_SPEW, "GBL8980A:group_fc:length = " + (group_fc.getBytes("UTF8")).length);
             test((group_fc.getBytes("UTF8")).length <= 8, "GBL8980A:group_fc length not <= 8 [passed length=" + (group_fc.getBytes("UTF8")).length + "]");
             test(cgtype != null, "GBL8980A:cgtype is null");
             debug(D.EBUG_SPEW, "GBL8980A:cgtype:length = " + (cgtype.getBytes("UTF8")).length);
             test((cgtype.getBytes("UTF8")).length <= 50, "GBL8980A:cgtype length not <= 50 [passed length=" + (cgtype.getBytes("UTF8")).length + "]");
             test(cgtype_fc != null, "GBL8980A:cgtype_fc is null");
             debug(D.EBUG_SPEW, "GBL8980A:cgtype_fc:length = " + (cgtype_fc.getBytes("UTF8")).length);
             test((cgtype_fc.getBytes("UTF8")).length <= 8, "GBL8980A:cgtype_fc length not <= 8 [passed length=" + (cgtype_fc.getBytes("UTF8")).length + "]");
             test(oslevel != null, "GBL8980A:oslevel is null");
             debug(D.EBUG_SPEW, "GBL8980A:oslevel:length = " + (oslevel.getBytes("UTF8")).length);
             test((oslevel.getBytes("UTF8")).length <= 2056, "GBL8980A:oslevel length not <= 2056 [passed length=" + (oslevel.getBytes("UTF8")).length + "]");
             test(oslevel_fc != null, "GBL8980A:oslevel_fc is null");
             debug(D.EBUG_SPEW, "GBL8980A:oslevel_fc:length = " + (oslevel_fc.getBytes("UTF8")).length);
             test((oslevel_fc.getBytes("UTF8")).length <= 512, "GBL8980A:oslevel_fc length not <= 512 [passed length=" + (oslevel_fc.getBytes("UTF8")).length + "]");
             test(status != null, "GBL8980A:status is null");
             debug(D.EBUG_SPEW, "GBL8980A:status:length = " + (status.getBytes("UTF8")).length);
             test((status.getBytes("UTF8")).length <= 32, "GBL8980A:status length not <= 32 [passed length=" + (status.getBytes("UTF8")).length + "]");
             test(status_fc != null, "GBL8980A:status_fc is null");
             debug(D.EBUG_SPEW, "GBL8980A:status_fc:length = " + (status_fc.getBytes("UTF8")).length);
             test((status_fc.getBytes("UTF8")).length <= 8, "GBL8980A:status_fc length not <= 8 [passed length=" + (status_fc.getBytes("UTF8")).length + "]");
             // Build a line with combined parameter values
             StringBuffer strbParms = new StringBuffer();
             strbParms.append("" + returnStatus);
             strbParms.append(":" + enterprise);
             strbParms.append(":" + countrycode);
             strbParms.append(":" + languagecode);
             strbParms.append(":" + nlsID);
             strbParms.append(":" + countrylist);
             strbParms.append(":" + featentitytype);
             strbParms.append(":" + featentityid);
             strbParms.append(":" + featurecode);
             strbParms.append(":" + fcmktgname);
             strbParms.append(":" + fctype);
             strbParms.append(":" + fctype_fc);
             strbParms.append(":" + anndate);
             strbParms.append(":" + withdrawdate);
             strbParms.append(":" + pricedfeature);
             strbParms.append(":" + pricedfeature_fc);
             strbParms.append(":" + tandc);
             strbParms.append(":" + category);
             strbParms.append(":" + category_fc);
             strbParms.append(":" + subcategory);
             strbParms.append(":" + subcategory_fc);
             strbParms.append(":" + group);
             strbParms.append(":" + group_fc);
             strbParms.append(":" + cgtype);
             strbParms.append(":" + cgtype_fc);
             strbParms.append(":" + oslevel);
             strbParms.append(":" + oslevel_fc);
             strbParms.append(":" + status);
             strbParms.append(":" + status_fc);
             strbParms.append(":" + configuratorflag);
             strbParms.append(":" + configuratorflag_fc);             
             strbParms.append(":" + isactive);
             String strParms = new String(strbParms);
             // Output the line
             debug(D.EBUG_DETAIL, "GBL8980A SPPARMS " + strParms);
                 // This will never happen, used just to keep java compiler quiet in case there are no parms to SP
                 if (1 == 2) {
                     throw new java.io.UnsupportedEncodingException();
                 }
             }
             catch (java.io.UnsupportedEncodingException uex) {
                 debug(D.EBUG_ERR, "Threw an exception checking byte length " + uex);
             }
             if (this.LogUsage()) {
                 String strTransaction = "GBL8980A";
                 // increment the use count for this transaction
                 if (!strTransaction.equals("GBL9980")) {
                 	m_db.record_transaction("GBL8980A");
                 }
             }
             // Build a string containing the SQL CALL statement with parameter markers
             String strSQL = "CALL opicm.GBL8980A(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             // If not connected to database, now is the time!
             // After making a connection, we should have a connection handle
             test(conn != null, "callGBL8980A:Connection handle is null");
             // If there is a prior statement something is wrong
             m_db.isPending("callGBL8980A");
             // Prepare the SQL
             m_cstmtHandle = conn.prepareCall(strSQL);
             // After preparing a statement, there should be a handle
             test(m_cstmtHandle != null, "callGBL8980A:Statement handle is null");
             m_cstmtHandle.registerOutParameter(1, Types.INTEGER);
             // Bind the method parameters to the SQL statement markers
           m_cstmtHandle.setInt(1, returnStatus.intValue());
           m_cstmtHandle.setString(2, enterprise);
           m_cstmtHandle.setString(3, countrycode);
           m_cstmtHandle.setString(4, languagecode);
           m_cstmtHandle.setInt(5, nlsID);
           m_cstmtHandle.setString(6, countrylist);
           m_cstmtHandle.setString(7, featentitytype);
           m_cstmtHandle.setInt(8, featentityid);
           m_cstmtHandle.setString(9, featurecode);
           m_cstmtHandle.setString(10, fcmktgname);
           m_cstmtHandle.setString(11, fctype);
           m_cstmtHandle.setString(12, fctype_fc);
           m_cstmtHandle.setString(13, anndate);
           m_cstmtHandle.setString(14, withdrawdate);
           m_cstmtHandle.setString(15, pricedfeature);
           m_cstmtHandle.setString(16, pricedfeature_fc);
           m_cstmtHandle.setString(17, tandc);
           m_cstmtHandle.setString(18, category);
           m_cstmtHandle.setString(19, category_fc);
           m_cstmtHandle.setString(20, subcategory);
           m_cstmtHandle.setString(21, subcategory_fc);
           m_cstmtHandle.setString(22, group);
           m_cstmtHandle.setString(23, group_fc);
           m_cstmtHandle.setString(24, cgtype);
           m_cstmtHandle.setString(25, cgtype_fc);
           m_cstmtHandle.setString(26, oslevel);
           m_cstmtHandle.setString(27, oslevel_fc);
           m_cstmtHandle.setString(28, status);
           m_cstmtHandle.setString(29, status_fc);
           m_cstmtHandle.setString(30, configuratorflag);
           m_cstmtHandle.setString(31, configuratorflag_fc);
           m_cstmtHandle.setInt(32, isactive);
             // Execute the SQL statement
             m_cstmtHandle.executeUpdate();
             // Retrieve the return status
             returnStatus.setValue(m_cstmtHandle.getInt(1));
             } catch (RuntimeException rx) {
                 mx = new MiddlewareException("(callGBL8980A) RuntimeException: " + rx);
                 debug(D.EBUG_ERR, "RuntimeException trapped at callGBL8980A: " + rx);
                 StringWriter writer = new StringWriter();
                 rx.printStackTrace(new PrintWriter(writer));
                 String x = writer.toString();
                 debug(D.EBUG_ERR, "" + x);
             } catch (Exception x) {
                 mx = new MiddlewareException("(callGBL8980A) Exception: " + x);
                 debug(D.EBUG_ERR, "Exception trapped at callGBL8980A: " + x);
             } finally {
                 m_lFinish = System.currentTimeMillis();
                 long longDuration = m_lFinish - m_lStart;
                 debug(D.EBUG_INFO, "timing GBL8980A " + Stopwatch.format(longDuration));
                 if (mx != null) {
                     debug(D.EBUG_DETAIL, "exiting callGBL8980A - error");
                     throw mx;
                 } else {
                     debug(D.EBUG_DETAIL, "exiting callGBL8980A");
                     // Return the ResultSet
                     return;
                 }
             }
     }
    
}