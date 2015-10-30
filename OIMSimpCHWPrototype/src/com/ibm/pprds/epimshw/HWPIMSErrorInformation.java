package com.ibm.pprds.epimshw;


/**
 * Has the task of reading and updating the DB2 table that contains error information.
 * This class will be used in two separate situations.
 *	1)  RFC Error recording and recovery - Any function that must call a series
 *		of RFCs should use the class to keep track of how many RFCs have been called
 *		and recovery information.
 *
 *		It will check DB2 to see if the previous task (promote) had any RFC failures.  The
 *		recoveryMode function will then indicate if an RFC should run (either because an error has never
 *		occurred or it was the failing RFC last time).
 *
 *	2)	A non-RFC error has occurred and needs to be written to DB2
 * Creation date: (3/27/00 8:53:38 AM)
 * @author: Greg Johnson
 */


import org.apache.log4j.Logger;

import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.pprds.epimshw.util.LogManager;


public class HWPIMSErrorInformation
{
	private static Logger _logger = LogManager.getLogManager().getPromoteLogger();
	
	/** Will indicate that an error has occurred on a previous execution.
	* So, if errorFlag is false, the RFC should always run.  If it is true, then all RFCs before
	* the errorTransaction should not run (they already have), then once the errorTransaction is
	* executed, the errorFlag will be set to false and hopefully the rest of the RFCs will work.
	* If they do not, then normal error handling will kick in.*/
	protected boolean errorFlag;
	
	/** Used to keep track of the number of RFC transactions that have run.  This is updated
	* with each call to recoveryModeCheck().  If an RFC error occurs, this value is posted as the
	* failing transaction.
	*/
	protected int transactionCounter;
	
	/** In cases where an RFC error occurred on a previous transaction, this is the failing
	* transaction
	*/
	protected int errorTransaction;
	
	/** Represents the overall action being done.  This will be "A" for a Promote Announcement
	* and a "P" for Promote CTO (Product)
	*/
	protected java.lang.String action;

	/** Identifier of an action.  This will be the Announcement Document Number for an
	* Announcement and the Part Number for a CTO/product.
	*/
	protected java.lang.String actionNo;
	
	/** Version of either the announcement or product */
	protected int version;

	/** Indicates if an attempt should be made to recover from this error.  A value of "R"
	* indicates that an attempt should be made and a value of "U" indicates it should not.
	*/
	protected java.lang.String status;

	/** Will contain relevant information about the error.
	*/
	protected java.lang.String message;

	/** The userID of the user */
	protected java.lang.String user;

	/** Indicates in what type of operation the error occurred.  Valid choices are:
	*	RFC - RFC error
	*	OBJ - Object construction error
	*   STP - Stopped by operator request
	*	JAV - Undefined Java error
	*/
	protected java.lang.String event;

	/** Instantiated from a property.  This indicates if error checking should be done
	*/
	protected boolean rfcErrorMode;

	/** Indicates that the db2 query to find an error row on the database
	*/
	public boolean db2QueryExecutedFlag = false;
/**
 * Construct an HWPIMSErrorInformation object
 * Creation date: (4/5/00 11:34:02 AM)
 */
public HWPIMSErrorInformation() {
	
//	initRfcErrorMode();
	transactionCounter=0;
	}
/**
 * Constructs an HWPIMSErrorInformation object and instantiates many of the attributes.
 * @param action String
 * @param actionNo String
 * @param version int
 * @param status String
 * @param message String
 * @param user String
 * @param event String
 */
public HWPIMSErrorInformation(String action, String actionNo, int version, String status, String message, String user, String event) {
	super();
	this.action = action;
	this.actionNo = actionNo;
	this.version = version;
	this.status = status;
	this.message = message;
	this.user = user;
	this.event = event;

	this.errorTransaction = 0;

//	initRfcErrorMode();
}
/**
 * action Getter
 * @return java.lang.String
 */
public java.lang.String getAction() {
	return action;
}
/**
 * actionNo Getter
 * @return java.lang.String
 */
public java.lang.String getActionNo() {
	return actionNo;
}
/**
 * errorTransaction Getter
 * @return int
 */
public int getErrorTransaction() {
	return errorTransaction;
}
/**
 * event Getter
 * @return java.lang.String
 */
public java.lang.String getEvent() {
	return event;
}
/**
 * message Getter
 * @return java.lang.String
 */
public java.lang.String getMessage() {
	return message;
}
/**
 * status Getter
 * @return java.lang.String
 */
public java.lang.String getStatus() {
	return status;
}
/**
 * transactionCounter Getter
 * @return int
 */
public int getTransactionCounter() {
	return transactionCounter;
}
/**
 * user Getter
 * @return java.lang.String
 */
public java.lang.String getUser() {
	return user;
}
/**
 * version Getter
 * @return int
 */
public int getVersion() {
	return version;
}
/**
 * Since single quotes are used in SQL statements as delimiters, change
 * single quotes within the message to '' so it is preserved
 */
protected void handleSingleQuotesInMessage() {

	if (message.indexOf("'") != -1) {

		StringBuffer m2 = new StringBuffer(message.length()+10);
	
		for (int i=0; i<message.length(); i++) {
			if (message.charAt(i) == '\'') {
				m2.append("'");
			}
			m2.append(message.charAt(i));
		
		}
		message = m2.toString();
	}
}
/** 
* Increment the transactionCounter
*/
public void incrTransactionCounter() {
	++transactionCounter;
	}
/**
 * Not currently used
 */
public void initRfcErrorMode() throws Exception {
	
	if (ConfigManager.getConfigManager().getString(PropertyKeys.KEY_RFC_ERROR_MODE, true).equals("N")) {
		rfcErrorMode = false;
	} else {
		rfcErrorMode = true;
//		if (db2QueryExecutedFlag == false && action != null && actionNo != null) {
//			boolean aFlag = retrieveErrorInfo();
//		}
	}

}
/**
 * db2QueryExecutedFlag getter
 * @return boolean
 */
public boolean isDb2QueryExecutedFlag() {
	return db2QueryExecutedFlag;
}
/**
 * errorFlag getter
 * @return boolean
 */
public boolean isErrorFlag() {
	return errorFlag;
}
/**
 * rfcErrorMode getter
 * @return boolean
 */
public boolean isRfcErrorMode() {
	return rfcErrorMode;
}
/**
 * This method is used to determine if an RFC transaction should be executed.  A return
 * value of true indicates that the RFC should be run.  A false means that it should not.
 * The errorFlag attribute will indicate that an error has occurred on a previous execution.
 * So, if errorFlag is false, the RFC should always run.  If it is true, then all RFCs before
 * the errorTransaction should not run (they already have), then once the errorTransaction is
 * executed, the errorFlag will be set to false and hopefully the rest of the RFCs will work.
 * If they do not, then normal error handling will kick in.
 *
 * Note that this will also handle the incrementing of the transactionCounter in case an
 * error occurs.  The transactionCounter will be (at invocation of the method), the count of
 * successful transactions.
 * @return boolean - indicates if next transaction should be run
 */
public boolean recoveryModeCheck() {

	return this.recoveryModeCheck(true);
	
}
/**
 * This method is used to determine if an RFC transaction should be executed.  A return
 * value of true indicates that the RFC should be run.  A false means that it should not.
 * The errorFlag attribute will indicate that an error has occurred on a previous execution.
 * So, if errorFlag is false, the RFC should always run.  If it is true, then all RFCs before
 * the errorTransaction should not run (they already have), then once the errorTransaction is
 * executed, the errorFlag will be set to false and hopefully the rest of the RFCs will work.
 * If they do not, then normal error handling will kick in.
 *
 * Note that this will also handle the incrementing of the transactionCounter if the
 * bumpCounter is true.
 *
 * The transactionCounter will be (at invocation of the method), the count of
 * successful transactions.
 *
 * @param boolean - indicates if the counter should be incremented
 * @return boolean - indicates if next transaction should be run
 */
public boolean recoveryModeCheck(boolean bumpCounter) {

	if (bumpCounter) {
			transactionCounter++;
		}
	if (rfcErrorMode == false) {
		
		return true;
	} else {

		boolean rtnFlag;
		
		if (errorFlag == true) {
			if (transactionCounter >= errorTransaction) {
				errorFlag = false;
				rtnFlag = true;
			} else {
				rtnFlag = false;
			}
		} else {
			rtnFlag = true;
		}
		return rtnFlag;
	}
}

	
/**
 * Retrieve the appropriate error row from the error table using the
 * connection to DB2 that is passed.
 *
 */
//public boolean retrieveErrorInfo() throws HWPIMSAbnormalException {
//	int transNum;
//	boolean pFlag = false;
//	String schema = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
//	Connection db2Connection = null;	
//	Statement stmt = null;
//	ResultSet rs = null;
//	try {
//		db2Connection = DBConnectionManager.getInstance().getConnection();
//		stmt = db2Connection.createStatement();
//		String query=null;
//		
//		if(action.equals("C") ) query = "Select PROMOTESTATUS from " + schema + ".CHW_ANNOUNCEMENT WHERE ANNDOCNO='"+actionNo+"'";
//		else if(action.equals("A") )  query = "Select PROMOTESTATUS from " + schema + ".ANNOUNCEMENT WHERE ANNDOCNO='"+actionNo+"' AND ANNVERSION="+Integer.toString(version);
//		else if(action.equals("P") )  query = "Select PROMOTESTATUS from " + schema + ".PRODUCT WHERE PARTNUMBER='"+actionNo+"' AND PRODVERSION="+Integer.toString(version);
//
//		rs = stmt.executeQuery(query);
//		if (rs.next()) {
//			String pStatus = rs.getString("PROMOTESTATUS");
//			if (pStatus.equals("4"))
//				errorFlag = true;
//			else
//				errorFlag = false;
//		}
//		rs.close();
//		stmt.close();
//		if (errorFlag == true) {
//			stmt = db2Connection.createStatement();
//			query = "Select ACTION, ACTIONNO, VERSION, EVENT, STATUS, TRANSNO, USERID, ERRORNO from " + schema + ".HWPIMSERROR ";
//			query += " where ACTION='" + action + "' and ACTIONNO='" + actionNo + "' and VERSION=" + Integer.toString(version) + " and STATUS='R'";
//			query += " order by ERRORNO DESC";
//			rs = stmt.executeQuery(query);
//			if (rs.next()) {
//				//Error row does exist.  We only care about the first one.
//				action = rs.getString(1);
//				actionNo = rs.getString(2);
//				version = rs.getInt(3);
//				event = rs.getString(4);
//				status = rs.getString(5);
//				errorTransaction = rs.getInt(6);
//				user = rs.getString(7);
//				//errorFlag = true;
//			} else {
//				// an Error entry is not present
//				errorTransaction = 0;
//				//errorFlag = false;
//				String msg = "No entry in HWPIMSERROR table for action: " + action 
//				+ ", action number: " + actionNo
//				+ ", version: " + version;
//				HWPIMSLog.Write("Error - " + msg + " while Announcement in 'Promote in Error' status.\n", "E");
//				throw new HWPIMSAbnormalException(msg);
//			}
//			//rs.close();
//			//stmt.close();
//		} 
//		db2QueryExecutedFlag = true;
//	} catch (SQLException e) {
//		String msg = "SQL exception while reading from HWPIMSERROR table.";
//		HWPIMSLog.Write("Error - " + msg + "\n" + ExceptionUtility.getStackTrace(e), "E");
//		throw new HWPIMSAbnormalException(msg, e);
//	}
//	finally
//	{
//		DBConnectionManager.closeRSAndStatement(rs, stmt);
//		DBConnectionManager.closeConnection(db2Connection);
//	}
//	return errorFlag;
//}
/**
 * Retrieve the appropriate error row from the error table using the
 * connection to DB2 that is passed.
 *
 */
//public void retrieveErrorInfo(boolean recoveryFlag) throws HWPIMSAbnormalException {
//	int transNum;
//	boolean pFlag = false;
//	String schema = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
//	Connection db2Connection = null;
//	Statement stmt = null;
//	ResultSet rs = null;
//		
//	try {
//		db2Connection = DBConnectionManager.getInstance().getConnection();
//		stmt = db2Connection.createStatement();
//		String query=null;
///*		
//		if(action.equals("C") ) query = "Select PROMOTESTATUS from " + schema + ".CHW_ANNOUNCEMENT WHERE ANNDOCNO='"+actionNo+"'";
//		else if(action.equals("A") )  query = "Select PROMOTESTATUS from " + schema + ".ANNOUNCEMENT WHERE ANNDOCNO='"+actionNo+"' AND ANNVERSION="+Integer.toString(version);
//		else if(action.equals("P") )  query = "Select PROMOTESTATUS from " + schema + ".PRODUCT WHERE PARTNUMBER='"+actionNo+"' AND PRODVERSION="+Integer.toString(version);
//
//		rs = stmt.executeQuery(query);
//		if (rs.next()) {
//			String pStatus = rs.getString("PROMOTESTATUS");
//			if (pStatus.equals("4"))
//				errorFlag = true;
//			else
//				errorFlag = false;
//		}
//		rs.close();
//		stmt.close();
//*/
//		errorFlag = recoveryFlag;
//	   
//		if (errorFlag == true) {
//			//stmt = db2Connection.createStatement();
//			query = "Select ACTION, ACTIONNO, VERSION, EVENT, STATUS, TRANSNO, USERID, ERRORNO from " + schema + ".HWPIMSERROR ";
//			query += " where ACTION='" + action + "' and ACTIONNO='" + actionNo + "' and VERSION=" + Integer.toString(version) + " and STATUS='R'";
//			query += " order by ERRORNO DESC";
//			rs = stmt.executeQuery(query);
//			if (rs.next()) {
//				//Error row does exist.  We only care about the first one.
//				action = rs.getString(1);
//				actionNo = rs.getString(2);
//				version = rs.getInt(3);
//				event = rs.getString(4);
//				status = rs.getString(5);
//				errorTransaction = rs.getInt(6);
//				user = rs.getString(7);
//				//errorFlag = true;
//			} else {
//				// an Error entry is not present
//				errorTransaction = 0;
//				//errorFlag = false;
//				String msg = "No entry in HWPIMSERROR table for action: " + action 
//					+ ", action number: " + actionNo
//					+ ", version: " + version;
//				HWPIMSLog.Write("Error - " + msg + " while Announcement in 'Promote in Error' status.\n", "E");
//				throw new HWPIMSAbnormalException(msg);
//			}
//		} 
//		db2QueryExecutedFlag = true;
//	} catch (SQLException e) {
//		String msg = "SQLException while reading from HWPIMSERROR table.";
//		HWPIMSLog.Write("Error - " + msg + "\n" + ExceptionUtility.getStackTrace(e), "E");
//		throw new HWPIMSAbnormalException(msg, e);
//	}
//	finally
//	{
//		DBConnectionManager.closeRSAndStatement(rs, stmt);
//		DBConnectionManager.closeConnection(db2Connection);
//	}
//}
/**
 * action setter
 * @param newAction java.lang.String
 */
public void setAction(java.lang.String newAction) {
	action = newAction;
}
/**
 * actionNo setter
 * @param newActionNo java.lang.String
 */
public void setActionNo(java.lang.String newActionNo) {
	actionNo = newActionNo;
}
/**
 * db2QueryExecutedFlag setter
 * @param newDb2QueryExecutedFlag boolean
 */
public void setDb2QueryExecutedFlag(boolean newDb2QueryExecutedFlag) {
	db2QueryExecutedFlag = newDb2QueryExecutedFlag;
}
/**
 * errorFlag setter
 * @param newErrorFlag boolean
 */
public void setErrorFlag(boolean newErrorFlag) {
	errorFlag = newErrorFlag;
}
/**
 * errorTransaction setter
 * @param newErrorTransaction int
 */
public void setErrorTransaction(int newErrorTransaction) {
	errorTransaction = newErrorTransaction;
}
/**
 * event setter
 * @param newEvent java.lang.String
 */
public void setEvent(java.lang.String newEvent) {
	event = newEvent;
}
/**
 * message setter
 * @param newMessage java.lang.String
 */
public void setMessage(java.lang.String newMessage) {
	// Since database field is a varChar(1000), we will truncated so that it will fit.

	if (newMessage.length() > 1000) {
		message = newMessage.substring(0,990)+">>TRUNC";
	} else {
		message = newMessage;
	}
}
/**
 * rfcErrorMode setter
 * @param newRfcErrorMode boolean
 */
public void setRfcErrorMode(boolean newRfcErrorMode) {
	rfcErrorMode = newRfcErrorMode;
}
/**
 * status setter
 * @param newStatus java.lang.String
 */
public void setStatus(java.lang.String newStatus) {
	status = newStatus;
}
/**
 * transactionCounter setter
 * @param newTransactionCounter int
 */
public void setTransactionCounter(int newTransactionCounter) {
	transactionCounter = newTransactionCounter;
}
/**
 * user setter
 * @param newUser java.lang.String
 */
public void setUser(java.lang.String newUser) {
	user = newUser;
}
/**
 * version setter
 * @param newVersion int
 */
public void setVersion(int newVersion) {
	version = newVersion;
}
/**
 * Static method to write an error method.
 * @param aConnection Connection - db2 Connection
 * @param action String
 * @param actionNo String
 * @param version int
 * @param status String
 * @param message String
 * @param user String
 * @param event String
 */
public static void WriteError(String action, String actionNo, int version, String status, String message, String user, String event) throws HWPIMSAbnormalException {
	
	HWPIMSErrorInformation rfce = new HWPIMSErrorInformation(action, actionNo, version, status, message, user, event);
	//rfce.writeErrorInfo();
	
	}
/**
 * write the error information to the database.
 * 
 * @param action java.lang.String - action column
 * @param key java.lang.String - actionno column
 * @param version java.lang.String -  version column
 * @param status java.lang.String -  status column
 * @param msg java.lang.String -  message column
 * @param user java.lang.String - user column
 */
//public void writeErrorInfo() throws HWPIMSAbnormalException {
//
//	getLogger().debug ("Making entry in HWPIMSERROR table - "
//		+ "ACTION: " + action
//		+ ", ACTIONNO: " + actionNo
//		+ ", VERSION: " + version
//		+ ", EVENT: " + event
//		+ ", STATUS: " + status
//		+ ", TRANSNO: " + transactionCounter
//		+ ", USERID: " + user
//		+ ", MESSAGE: " + message);
//	
//	int errno =0;
//
//	if (HWPIMSUpdateChecker.CheckUpdateMode()) {
//
//		// Must specify with package since java.sql also has a Date class
//		//java.util.Date curDate = new java.util.Date();
//		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//	
//		handleSingleQuotesInMessage();
//		
//		//DBConnectionManager dbMgr = DBConnectionManager.getInstance();
//		//String schema = dbMgr.getSchema();
//		
//		
//		String schema = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SCHEMA);
//		
//		Connection db2Connection = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		Statement stmt1 = null;
//		ResultSet rs1 = null;
//		Statement stmt2 = null;
//		try {
//			db2Connection = DBConnectionManager.getInstance().getConnection();
//			stmt = db2Connection.createStatement();
//
//			//First get the next errorNo value to use
//
//			String query = "SELECT MAX(ERRORNO) FROM "+schema+".HWPIMSERROR WHERE ACTIONNO = '"+actionNo+"' AND VERSION = "+version;
//			rs = stmt.executeQuery(query);
//			
//			stmt1 = db2Connection.createStatement();
//			String query1 = "SELECT ERRORNO FROM "+schema+".HWPIMSERROR WHERE ACTIONNO = '"+actionNo+"' AND VERSION = "+version;
//			rs1 = stmt1.executeQuery(query1);
//			
//			/*	if (rs.next()) {
//				errno = rs.getInt("ERRORNO") + 1;
//			} else {   // Nothing in the result set so start with 1
//				errno = 1;
//			}*/
//
//			if (rs1.next()) {
//				if(rs.next()){
//				errno = rs.getInt(1) + 1;
//				}
//			} else {   // Nothing in the result set so start with 1
//				errno = 1;
//			}
//
//			stmt2 = db2Connection.createStatement();
//			
//			StringBuffer query2 = new StringBuffer();	
//			query2.append("INSERT into " + schema);
//			query2.append(".HWPIMSERROR (ACTION, ACTIONNO, VERSION, EVENT, STATUS, TRANSNO, USERID, AUDITTIMESTAMP, MESSAGE, ERRORNO) ");
//			query2.append(" VALUES('" + action + "', '" + actionNo + "', " + version);
//			query2.append(", '" + event + "', '" + status + "', " + transactionCounter);
//			query2.append(", '" + user + "', CURRENT TIMESTAMP, '" + message + "', " + errno+")");
//	
//			int recordsProcessed = stmt2.executeUpdate(query2.toString());
//			
////			rs1.close();
////			rs.close();
////			stmt2.close();
////			stmt1.close();
////			stmt.close();
//	
//		}
//		catch (SQLException e)
//		{
//			String msg = "Unable to insert into HWPIMSERROR table.\n"  
//				+ "Reason: " + e.getMessage() + "\n"
//				+ ExceptionUtility.getStackTrace(e) + "\n"
//				+ "ACTION: " + action
//				+ ", ACTIONNO: " + actionNo
//				+ ", VERSION: " + version
//				+ ", EVENT: " + event
//				+ ", STATUS: " + status
//				+ ", TRANSNO: " + transactionCounter
//				+ ", USERID: " + user
//				+ ", MESSAGE: " + message;
//			getLogger().error(msg, e);
//			throw new HWPIMSAbnormalException(msg, e);
//		}
//		finally
//		{
//			DBConnectionManager.closeRSAndStatement(rs, stmt);
//			DBConnectionManager.closeRSAndStatement(rs1, stmt1);
//			DBConnectionManager.closeRSAndStatement(null, stmt2);
//			DBConnectionManager.closeConnection(db2Connection);
//		}
//	}
//	
//}

	private Logger getLogger()
	{
		return _logger;
	}
}
