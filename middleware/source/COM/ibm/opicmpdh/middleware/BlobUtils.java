package COM.ibm.opicmpdh.middleware;
//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: BlobUtils.java,v $
// Revision 1.2  2012/11/08 22:07:37  wendy
// add log version
//
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
  
public class BlobUtils {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/* A utility class only contains static methods and static variables. Since an
    implicit default constructor is "public" and a utility class is not designed to
    be instantiated, the "public" default constructor of a utility class should be
    declared "private".  */
	private BlobUtils() {}

	private static final String INSERT_BLOB = "INSERT INTO opicm.Blob"+
	" (Enterprise, EntityType, EntityID, AttributeCode, BlobExtension, NLSID, ValFrom, ValTo, EffFrom, EffTo, OPENID, TRANID,AttributeValue)"+
	" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String INSERT_BLOBX = 	"INSERT INTO opicm.BlobX"+
	" (Enterprise, EntityType, EntityID, AttributeCode, BlobExtension, NLSID, ValFrom, ValTo, EffFrom, EffTo, OPENID, TRANID,AttributeValue)"+
	" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	
	/**
	 * get blob
	 * @param db
	 * @param conn
	 * @param _prof
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @param _iNLS
	 * @return
	 * @throws SQLException
	 */
	public static final COM.ibm.opicmpdh.objects.Blob getBlobNow(Database db, Connection conn, 
			Profile _prof, String entityType, int entityID, String attributeCode, int _iNLS) throws SQLException {

		String strEnterprise = _prof.getEnterprise();
		int iNLSID = ((_iNLS > 0) ? _iNLS : _prof.getReadLanguage().getNLSID());

		StringBuffer strbSQL = new StringBuffer();

		strbSQL.append("SELECT LENGTH(AttributeValue), BlobExtension, NLSID, ValFrom, ValTo, EffFrom, EffTo, OPENID, AttributeValue FROM opicm.Blob");
		strbSQL.append(" WHERE Enterprise = '" + strEnterprise + "'");
		strbSQL.append(" AND EntityType = '" + entityType + "'");
		strbSQL.append(" AND EntityID = " + entityID);
		strbSQL.append(" AND AttributeCode = '" + attributeCode + "'");
		strbSQL.append(" AND NLSID = " + iNLSID);
		strbSQL.append(" AND ValFrom <= CURRENT TIMESTAMP AND CURRENT TIMESTAMP < ValTo");
		strbSQL.append(" AND EffFrom <= CURRENT TIMESTAMP AND CURRENT TIMESTAMP < EffTo with ur");

		return executeGetBlob(strbSQL.toString(),  db, conn,strEnterprise, entityType, entityID, attributeCode);
	}
	
	/**
	 * go get the blob
	 * @param sql
	 * @param db
	 * @param conn
	 * @param strEnterprise
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @return
	 * @throws SQLException
	 */
	private static COM.ibm.opicmpdh.objects.Blob executeGetBlob(String sql, Database db, Connection conn,
			String strEnterprise, String entityType, int entityID, String attributeCode) throws SQLException{
		COM.ibm.opicmpdh.objects.Blob blobNew = null;
		String trace = " for entityType: "+entityType+" entityID: "+entityID+" attributeCode: "+attributeCode;
		boolean success = false;

		db.debug("executeGetBlob: "+trace+" requested");
		ResultSet rs = null;
		Statement statement = null;
		try {
			statement = conn.createStatement();

			rs = statement.executeQuery(sql);
			if (rs.next()) {
				int iBlobSize = rs.getInt(1);
				byte[] baBlob = null;
				db.debug("executeGetBlob: "+trace+" read a blob of size " + iBlobSize + " bytes");
				if (iBlobSize > 0) {
					//just wasting memory baBlob = new byte[iBlobSize];
					baBlob = rs.getBytes(9);		// column nine!
				} else {
					baBlob = new byte[0];
				}
				
				blobNew = new COM.ibm.opicmpdh.objects.Blob(strEnterprise, entityType, entityID, attributeCode, 
						baBlob, Unicode.rtrim(rs.getString(2)), rs.getInt(3), Unicode.rtrim(rs.getString(4)), 
						Unicode.rtrim(rs.getString(5)), Unicode.rtrim(rs.getString(6)), 
						Unicode.rtrim(rs.getString(7)), rs.getInt(8));
				baBlob = null;
				//SELECT LENGTH(AttributeValue), BlobExtension, NLSID, ValFrom, ValTo, EffFrom, EffTo, OPENID,
				while(rs.next()){
					db.debug("executeGetBlob: WARNING "+trace+" FOUND more than one blob size:"+rs.getInt(1)+
							" NLSID:"+rs.getInt(3)+" valfrom:"+rs.getString(4)+" valto:"+rs.getString(5)+
							" openid:"+rs.getInt(8));
				}
			} else {
				db.debug("executeGetBlob: "+trace+" no blob found");
				blobNew = new COM.ibm.opicmpdh.objects.Blob();
			}
			success = true;
		}catch (SQLException sx) {
			db.debug(D.EBUG_ERR, "executeGetBlob: "+trace+" failed " + sx);
			sx.printStackTrace();
			throw sx;
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
			} catch (SQLException x) {
				db.debug(D.EBUG_DETAIL, "executeGetBlob: "+trace+" ERROR failure closing ResultSet"+x);
			} 
			rs = null;
			try {
				if(statement!=null){
					statement.close();
				}
			} catch (SQLException x) {
				db.debug(D.EBUG_DETAIL, "executeGetBlob: "+trace+" ERROR failure closing get blob Statement "+x);
			} 
			statement=null;

			if(success){
				db.commit();
			}else{
				try {
					db.rollback();
				} catch (SQLException sx) {
					db.debug(D.EBUG_ERR, "executeGetBlob: "+trace+" rollback failed " + sx);
				}
			}
		}

		return blobNew;	
	}
	/**
	 * get blob
	 * @param db
	 * @param conn
	 * @param _prof
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @param _nlsID
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	public static final COM.ibm.opicmpdh.objects.Blob getBlob(Database db, Connection conn, 
			Profile _prof, String entityType, int entityID, String attributeCode, int _nlsID) throws Exception, SQLException, MiddlewareException {
		String strEnterprise = _prof.getEnterprise();
		int iNLSID = _nlsID;
		String strValOn = _prof.getValOn();
		String strEffOn = _prof.getEffOn();

		StringBuffer strbSQL = new StringBuffer();

		strbSQL.append("SELECT LENGTH(AttributeValue), BlobExtension, NLSID, ValFrom, ValTo, EffFrom, EffTo, OPENID, AttributeValue FROM opicm.Blob");
		strbSQL.append(" WHERE Enterprise = '" + strEnterprise + "'");
		strbSQL.append(" AND EntityType = '" + entityType + "'");
		strbSQL.append(" AND EntityID = " + entityID);
		strbSQL.append(" AND AttributeCode = '" + attributeCode + "'");
		strbSQL.append(" AND NLSID = " + iNLSID);
		strbSQL.append(" AND ValFrom <= '" + strValOn + "'");
		strbSQL.append(" AND '" + strValOn + "'");
		strbSQL.append(" < ValTo");
		strbSQL.append(" AND EffFrom <= '" + strEffOn + "'");
		strbSQL.append(" AND ");
		strbSQL.append(" '" + strEffOn + "'");
		strbSQL.append(" < EffTo with ur");

		return executeGetBlob(strbSQL.toString(),  db, conn,strEnterprise, entityType, entityID, attributeCode);	
	}
	/**
	 * get blob 
	 * @param db
	 * @param conn
	 * @param _prof
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @return
	 * @throws SQLException
	 */
	public static final COM.ibm.opicmpdh.objects.Blob getBlob(Database db, Connection conn, Profile _prof, 
			String entityType, int entityID, String attributeCode) throws SQLException {
        String strEnterprise = _prof.getEnterprise();
        int iNLSID = _prof.getReadLanguage().getNLSID();
        String strValOn = _prof.getValOn();
        String strEffOn = _prof.getEffOn();

        StringBuffer strbSQL = new StringBuffer();

        strbSQL.append("SELECT LENGTH(AttributeValue), BlobExtension, NLSID, ValFrom, ValTo, EffFrom, EffTo, OPENID, AttributeValue FROM opicm.Blob");
        strbSQL.append(" WHERE Enterprise = '" + strEnterprise + "'");
        strbSQL.append(" AND EntityType = '" + entityType + "'");
        strbSQL.append(" AND EntityID = " + entityID);
        strbSQL.append(" AND AttributeCode = '" + attributeCode + "'");
        strbSQL.append(" AND NLSID = " + iNLSID);
        strbSQL.append(" AND ValFrom <= '" + strValOn + "'");
        strbSQL.append(" AND '" + strValOn + "'");
        strbSQL.append(" < ValTo");
        strbSQL.append(" AND EffFrom <= '" + strEffOn + "'");
        strbSQL.append(" AND ");
        strbSQL.append(" '" + strEffOn + "'");
        strbSQL.append(" < EffTo with ur");

        return executeGetBlob(strbSQL.toString(),  db, conn,strEnterprise, entityType, entityID, attributeCode);
    }
	/**
	 * Used to update CACHE and other blob attributes
	 * @param db
	 * @param conn
	 * @param _prof
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @param blobExtension
	 * @param effFrom
	 * @param effTo
	 * @param attributeValue
	 * @param _nlsID
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	public final static void putBlobCache(Database db, Connection conn, Profile _prof, String entityType, int entityID, 
			String attributeCode, String blobExtension, String effFrom, String effTo, byte[] attributeValue, 
			int _nlsID) throws SQLException, MiddlewareException 
	{
		String trace = "for entitytype "+entityType+" attributecode "+attributeCode;

		// Need to grab now and forever
		db.getNow();
		String strNow = Database.c_strNow;
		boolean success = false;
		try{
			// Check if the record exists
			boolean exist = checkBlobRecordExist(db, conn, _prof, entityType, entityID, 
				attributeCode, _nlsID);

			if (exist) {
				// Update the existing BLOB row
				updateBlobRecord("opicm.Blob",db, conn, _prof, entityType, entityID, 
						attributeCode, blobExtension, attributeValue, _nlsID);


				// Update the existing BLOBX row
				updateBlobRecord("opicm.BlobX",db, conn, _prof, entityType, entityID, 
						attributeCode, blobExtension, attributeValue, _nlsID);
			} else {
				// Insert the new BLOB row
				insertBlob(INSERT_BLOB,db, conn,strNow,_prof, entityType, entityID, 
						attributeCode, blobExtension, _nlsID, effFrom, effTo, attributeValue);

				// Now the X record
				insertBlob(INSERT_BLOBX,db, conn,strNow,_prof, entityType, entityID, 
						attributeCode, blobExtension, _nlsID, effFrom, effTo, attributeValue);
			}
			success = true;
		}catch (SQLException sx) {
			db.debug(D.EBUG_ERR, "putBlobCache: "+trace+" failed " + sx);
			sx.printStackTrace();
			throw sx;
		}finally{  // MN32174982
			if(success){
				db.commit();
			}else{
				try {
					db.rollback();
				} catch (SQLException sx) {
					db.debug(D.EBUG_ERR, "putBlobCache: "+trace+" rollback failed " + sx);
				}
			}
		}
	}

	/**
	 * used to update Blob attributes, not CACHE
	 * @param db
	 * @param conn
	 * @param _prof
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @param blobExtension
	 * @param _iNLSID
	 * @param effFrom
	 * @param effTo
	 * @param attributeValue
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	public static final void putBlob(Database db, Connection conn, Profile _prof, String entityType, int entityID, 
			String attributeCode, String blobExtension, int _iNLSID, String effFrom, String effTo, 
			byte[] attributeValue) throws SQLException, MiddlewareException 
	{
		String trace = "for entitytype "+entityType+" attributecode "+attributeCode;

		// Need to grab now and forever
		db.getNow();
		String strNow = Database.c_strNow;
		boolean success = false;
		try{ // MN32174982
		
			// Deactivate the existing BLOB row
			deactivateBlob("opicm.Blob", db, conn, _prof, entityType,  entityID, attributeCode,  _iNLSID, strNow);

			// Deactivate the existing BLOBX row
			deactivateBlob("opicm.BlobX", db, conn, _prof, entityType,  entityID, attributeCode,  _iNLSID, strNow);

			// Insert the new BLOB row
			insertBlob(INSERT_BLOB,db, conn,strNow,_prof, entityType, entityID, 
					attributeCode, blobExtension, _iNLSID, effFrom, effTo, attributeValue);

			// Now the X record
			insertBlob(INSERT_BLOBX,db, conn,strNow,_prof, entityType, entityID, 
					attributeCode, blobExtension, _iNLSID, effFrom, effTo, attributeValue);
		
			success = true;
		} catch (SQLException sx) {
			db.debug(D.EBUG_ERR, "putBlob: "+trace+" failed " + sx);
			sx.printStackTrace();
			throw sx;
		}finally { // MN32174982
			if(success){
				db.commit();
			}else{
				try {
					db.rollback();
				} catch (SQLException sx) {
					db.debug(D.EBUG_ERR, "putBlob: "+trace+" rollback failed " + sx);
				}
			}
		}
	}

	/**
	 * this doesn't seem to be used now, the nlsid comes from the profile here
	 * @param db
	 * @param conn
	 * @param _prof
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @param blobExtension
	 * @param effFrom
	 * @param effTo
	 * @param attributeValue
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	public static final void putBlob(Database db, Connection conn,Profile _prof, String entityType, 
			int entityID, String attributeCode, String blobExtension, String effFrom, String effTo, 
			byte[] attributeValue) throws SQLException, MiddlewareException {

		String trace = " for entitytype "+entityType+" attributecode "+attributeCode;

		int iNLSID = _prof.getReadLanguage().getNLSID();

		// Need to grab now and forever
		db.getNow();
		String strNow = Database.c_strNow; 
		boolean success = false;
		try{ // MN32174982
	
			// Deactivate the existing BLOB row
			deactivateBlob("opicm.Blob", db, conn, _prof, entityType,  entityID, attributeCode,  iNLSID, strNow);

			// Deactivate the existing BLOBx row
			deactivateBlob("opicm.BlobX", db, conn, _prof, entityType,  entityID, attributeCode,  iNLSID, strNow);

			// Insert the new BLOB row
			insertBlob(INSERT_BLOB,db, conn,strNow,_prof, entityType, entityID, 
					attributeCode, blobExtension, iNLSID, effFrom, effTo, attributeValue);

			// Insert the new BLOBx row
			insertBlob(INSERT_BLOBX,db, conn,strNow,_prof, entityType, entityID, 
					attributeCode, blobExtension, iNLSID, effFrom, effTo, attributeValue);

			success = true;
		} catch (SQLException sx) {
			db.debug(D.EBUG_ERR, "putBlob: "+trace+" failed " + sx);
			sx.printStackTrace();
			throw sx;
		}finally { // MN32174982
			if(success){
				db.commit();
			}else{
				try {
					db.rollback();
				} catch (SQLException sx) {
					db.debug(D.EBUG_ERR, "putBlob: "+trace+" rollback failed " + sx);
				}
			}
		}
	}
	/**
	 * deactivate row in blobx or blob table
	 * @param table
	 * @param db
	 * @param conn
	 * @param _prof
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @param _iNLSID
	 * @param strNow
	 * @throws SQLException
	 */
	public static void deactivateBlob(String table, Database db, Connection conn, Profile _prof, String entityType, int entityID, 
			String attributeCode, int _iNLSID, String strNow) throws SQLException{
		
		StringBuffer strbSQL = new StringBuffer();

		String trace = table+" for entitytype "+entityType+" attributecode "+attributeCode;
		
		strbSQL.append("UPDATE "+table);
		strbSQL.append(" SET ValTo = '" + strNow + "'");
		strbSQL.append(" WHERE");
		strbSQL.append(" Enterprise = '" + _prof.getEnterprise() + "' AND");
		strbSQL.append(" EntityType = '" + entityType + "' AND");
		strbSQL.append(" EntityID = " + entityID + " AND");
		strbSQL.append(" AttributeCode = '" + attributeCode + "' AND");
		strbSQL.append(" NLSID = " + _iNLSID + " AND");
		strbSQL.append(" ValTo = '" + Database.c_strForever + "'");
		Statement statement = null;

		db.debug("deactivateBlob: "+trace+" ValTo: "+strNow);
		try {
			statement = conn.createStatement();
			statement.executeUpdate(strbSQL.toString());
		} catch (SQLException sx) {
			db.debug(D.EBUG_ERR, "deactivateBlob: "+trace+" failed " + sx);
			sx.printStackTrace();
			throw sx;
		} finally {
			try {
				if(statement!=null){
					statement.close();
				}
			} catch (SQLException x) {
				db.debug(D.EBUG_DETAIL, "deactivateBlob: "+trace+" ERROR failure closing update blob Statement "+x);
			} 
			statement=null;
		}
	}
	/**
	 * does a record already exist for this data in the blob table?
	 * @param db
	 * @param conn
	 * @param _prof
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @param blobExtension
	 * @param effFrom
	 * @param effTo
	 * @param _nlsID
	 * @return
	 * @throws SQLException 
	 */
	private static boolean checkBlobRecordExist(Database db, Connection conn, Profile _prof, String entityType, int entityID, 
			String attributeCode, int _nlsID) throws SQLException{

		// For building SQL statement
		StringBuffer strbSQL = new StringBuffer();

		String trace = "for entitytype "+entityType+" attributecode "+attributeCode;

		// for SELECT SQL statement
		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;
		boolean exist = false;
		// Check if the record exists
		strbSQL.append("SELECT Enterprise, EntityType, EntityID, AttributeCode FROM opicm.Blob");
		strbSQL.append(" WHERE Enterprise = '" + _prof.getEnterprise() + "'");
		strbSQL.append(" AND EntityType = '" + entityType + "'");
		strbSQL.append(" AND EntityID = " + entityID);
		strbSQL.append(" AND AttributeCode = '" + attributeCode + "'");
		strbSQL.append(" AND NLSID = " + _nlsID + " ");
		strbSQL.append(" AND ValFrom <= '" + _prof.getValOn() + "'");
		strbSQL.append(" AND '" + _prof.getValOn() + "' < ValTo");
		strbSQL.append(" AND EffFrom <= '" + _prof.getEffOn() + "'");
		strbSQL.append(" AND '" + _prof.getEffOn() + "' < EffTo with ur");

		Statement statement=null;
		db.debug("checkBlobRecordExist "+trace+" valon: "+_prof.getValOn()+" requested");
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(strbSQL.toString());
			rdrs = new ReturnDataResultSet(rs);
			exist = rdrs.getRowCount() > 0;
			db.debug("checkBlobRecordExist "+trace+" valon: "+_prof.getValOn()+" exist: "+exist);
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
			} catch (SQLException x) {
				db.debug(D.EBUG_DETAIL, "checkBlobRecordExist failure closing ResultSet "+x);
			}
			rs=null;
			try {
				if(statement!=null){
					statement.close();
				}
			} catch (SQLException x) {
				db.debug(D.EBUG_DETAIL, "checkBlobRecordExist failure closing Statement "+x);
			} 
			statement=null;
		}
		return exist;
	}

	/**
	 * insert new row into blob or blobx table
	 * @param sql
	 * @param db
	 * @param conn
	 * @param strNow
	 * @param _prof
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @param blobExtension
	 * @param _iNLSID
	 * @param effFrom
	 * @param effTo
	 * @param attributeValue
	 * @throws SQLException 
	 */
	private static void insertBlob(String sql,Database db, Connection conn,String strNow,Profile _prof, String entityType, int entityID, 
			String attributeCode, String blobExtension, int _iNLSID, String effFrom, String effTo, 
			byte[] attributeValue) throws SQLException
	{
		String table="into opicm.Blob";
		if(sql==INSERT_BLOBX){
			table = "into opicm.BlobX";
		}
		String strEnterprise = _prof.getEnterprise();
		int iOPWGID = _prof.getOPWGID();
		int iTranID = _prof.getTranID();

		String trace = table+" for entitytype "+entityType+" attributecode "+attributeCode;

		// Insert the new Blob or BlobX row
		PreparedStatement prepStatement = null;

		try {
			prepStatement = conn.prepareStatement(sql);

			prepStatement.setString(1, strEnterprise);
			prepStatement.setString(2, entityType);
			prepStatement.setInt(3, entityID);
			prepStatement.setString(4, attributeCode);
			prepStatement.setString(5, blobExtension);
			prepStatement.setInt(6, _iNLSID);
			prepStatement.setString(7, strNow);
			prepStatement.setString(8, Database.c_strForever);
			prepStatement.setString(9, effFrom);
			prepStatement.setString(10, effTo);
			prepStatement.setInt(11, iOPWGID);
			prepStatement.setInt(12, iTranID);

			db.debug("insertBlob "+trace+" valon: "+strNow+" of size " + attributeValue.length + " bytes requested");
			ByteArrayInputStream input = new ByteArrayInputStream(attributeValue);
			prepStatement.setBinaryStream(13, input, attributeValue.length);
			prepStatement.execute();
		} finally {
			try {
				if(prepStatement!=null){
					prepStatement.close();
				}
			} catch (SQLException x) {
				db.debug(D.EBUG_DETAIL, "insertBlob "+trace+" failure closing PreparedStatement "+x);
			} 
			prepStatement=null;
		}
	}
	/**
	 * update existing record in blob or blobx table
	 * @param table
	 * @param db
	 * @param conn
	 * @param _prof
	 * @param entityType
	 * @param entityID
	 * @param attributeCode
	 * @param blobExtension
	 * @param attributeValue
	 * @param _nlsID
	 * @throws SQLException
	 */
	private static void updateBlobRecord(String table,Database db, Connection conn, Profile _prof, String entityType, int entityID, 
			String attributeCode, String blobExtension, byte[] attributeValue, 
			int _nlsID) throws SQLException {
		StringBuffer strbSQL = new StringBuffer();
		String strEnterprise = _prof.getEnterprise();

		String strValOn = _prof.getValOn();
		String strEffOn = _prof.getEffOn();
		String trace = table+" for entitytype "+entityType+" attributecode "+attributeCode;
		
		// Update the existing BLOB(x) row
		strbSQL.append("UPDATE "+table);
		strbSQL.append(" SET (BlobExtension, AttributeValue) = ");
		strbSQL.append(" (?, ?)");
		strbSQL.append(" WHERE");
		strbSQL.append(" Enterprise = '" + strEnterprise + "' AND");
		strbSQL.append(" EntityType = '" + entityType + "' AND");
		strbSQL.append(" EntityID = " + entityID + " AND");
		strbSQL.append(" AttributeCode = '" + attributeCode + "' AND");
		strbSQL.append(" NLSID = " + _nlsID + " AND");
		strbSQL.append(" ValFrom <= '" + strValOn + "' AND");
		strbSQL.append(" '" + strValOn + "' < ValTo AND");
		strbSQL.append(" EffFrom <= '" + strEffOn + "' AND");
		strbSQL.append(" '" + strEffOn + "' < EffTo");

		PreparedStatement prepStatement  = null;

		db.debug("updateBlobRecord "+trace+" using valon: "+strValOn+" of size: " +attributeValue.length+ " bytes requested");
		  
		try {
			prepStatement  = conn.prepareStatement(strbSQL.toString());
			prepStatement.setString(1, blobExtension);
			ByteArrayInputStream input = new ByteArrayInputStream(attributeValue);
			prepStatement.setBinaryStream(2, input, attributeValue.length);
			prepStatement.execute();
		} finally {
			try {
				if(prepStatement!=null){
					prepStatement.close();
				}
			} catch (SQLException x) {
				db.debug(D.EBUG_DETAIL, "updateBlobRecord failure closing PreparedStatement "+x);
			} 
			prepStatement=null;
		}
	}
}
