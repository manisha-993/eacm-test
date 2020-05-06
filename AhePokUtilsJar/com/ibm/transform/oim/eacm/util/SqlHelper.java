package com.ibm.transform.oim.eacm.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SqlHelper {
	private static final String STATE_FAIL = "Fail";
	private static final String STATE_FOT = "Fot";
	private static final String STATE_MISS = "Miss";
	private static final String STATE_VALID = "Valid";
	private static final String STATE_ZERO = "Zero";

	public SqlHelper() {
		
	}

	public static String getState(String state) {
		String oldState = state.trim().toLowerCase();
		String result = null;
		if (oldState.equals("fail")) {
			result = STATE_FAIL;
		} else if (oldState.equals("fot")) {
			result = STATE_FOT;
		} else if (oldState.equals("miss")) {
			result = STATE_MISS;
		} else if (oldState.equals("valid")) {
			result = STATE_VALID;
		} else if (oldState.equals("zero")) {
			result = STATE_ZERO;
		}
		return result;
	}

	public static ArrayList getMultiRowInfo(String sqlStatement, Connection conn,List paramList) {
		ArrayList recordArray = new ArrayList();
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		int numberOfColumns = 0;
		int t = 0; // data type
		String columnName = "";
		PreparedStatement pstmt = null;
		try {
			
			pstmt = conn.prepareStatement(sqlStatement);
			for(int i=0;i<paramList.size();i++){
				pstmt.setString(i+1,(String) paramList.get(i));
			}
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			numberOfColumns = rsmd.getColumnCount();
			while (rs.next()) {
				Hashtable hTable = new Hashtable();
				// check data type
				// date date 91 TIMESTAMP 93
				// int 2,4 SMALLINT 5
				// DECIMAL 3
				// varchar 12
				// character 1
				for (int k = 1; k <= numberOfColumns; k++) {
					t = rsmd.getColumnType(k);
					columnName = rsmd.getColumnName(k).toLowerCase();
					if (t == 12 || t == 1 || t == 3) {
						hTable.put(columnName, (rs.getString(k) == null)
								? ""
								: rs.getString(k));
					} else if (t == 91) {
						hTable.put(columnName, (rs.getDate(k) == null)
								? ""
								: rs.getDate(k).toString());
					} else if (t == 93) {
						hTable.put(columnName, (rs.getTimestamp(k) == null)
								? ""
								: rs.getTimestamp(k).toString());
					} else if (t == 2 || t == 4 || t == 5) {
						hTable.put(columnName, (rs.getString(k) == null)
								? ""
								: Long.toString(rs.getLong(k)));
					} else {
						hTable.put(columnName, (rs.getString(k) == null)
								? ""
								: rs.getString(k));
					}
				}
				recordArray.add(hTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.clearParameters();
					pstmt.close();
					pstmt = null;
				}
				/*
				 * if (conn != null) { conn.close(); conn = null; }
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return recordArray;
	}	
}
