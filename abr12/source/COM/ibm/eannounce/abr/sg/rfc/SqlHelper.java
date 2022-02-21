package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 * @version 1.0
 */

public class SqlHelper {
    /**
     * 
     * @param sqlString
     * @param conn
     * @return
     */
    public static int runUpdateSql(String sqlString, Connection conn) {
        int updateRows = -1;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            updateRows = stmt.executeUpdate(sqlString);
            return updateRows;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        finally {
        	try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            ConnectionFactory.closeConnection(stmt, conn);
        }
    }



    /**
     * 
     * @param sqlString
     * @param conn
     * @return
     * @throws SQLException
     */
    public static int runUpdateSqlException(String sqlString, Connection conn) throws
        SQLException {
    	System.out.print("\t" + sqlString);
        int updateRows = -1;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            updateRows = stmt.executeUpdate(sqlString);
            return updateRows;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        finally {
        	try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            ConnectionFactory.closeConnection(stmt, conn);
        }
    }



    /**
     * 
     * @param sqlString
     * @param conn
     * @return
     */
    public static ResultSet runQuerySql(String sqlString, Connection conn) {
        //System.out.print("\t" + sqlString);
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlString);
            return rs;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
          //  ConnectionFactory.closeConnection(rs, stmt, conn);
        
    }



    /**
     * 
     * @param sqlString
     * @param conn
     * @return
     */
    public static boolean runProcedure(String sqlString, Connection conn) {
        System.out.print("\t" + sqlString);
        boolean isOk = false;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            isOk = stmt.execute(sqlString);
            return isOk;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
        	try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            ConnectionFactory.closeConnection(stmt, conn);
        }
    }


    public boolean isExist(String sqlString, Connection conn) {
    	System.out.print("\t" + sqlString);
    	ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlString);
            if (rs.next()) {
            	return true;
            }else{
            	return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.closeConnection(rs, stmt, conn);
        }        
        return true;
    	
    }
    
    public static String getDbDate(Connection conn) {
        String sqlString = "";
        sqlString = "select TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD') as yyyymmdd FROM SYSIBM.SYSDUMMY1";

        String dateString = "";
        try {
            dateString = (String) getSingleRow(sqlString,conn).get("yyyymmdd");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

 

   /**
    * 
    * @param sqlString
    * @param conn
    * @return
    */
    public static Hashtable<String, String> getSingleRow(String sqlString, Connection conn) {
        System.out.print("\t" + sqlString);
        Hashtable<String, String> hTable = new Hashtable<String, String>();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlString);
            hTable = getHashtable(false, rs);
            return hTable;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.closeConnection(rs, stmt, conn);
        }
        return hTable;
    }
    
    /**
     * 
     * @param sqlString
     * @param conn
     * @return
     */
     @SuppressWarnings("rawtypes")
	public static Hashtable getSingleRow(String sqlString,boolean isUpper, Connection conn) {
         System.out.print("\t" + sqlString);
         Hashtable<String, String> hTable = new Hashtable<String, String>();
         ResultSet rs = null;
         Statement stmt = null;
         try {
             stmt = conn.createStatement();
             rs = stmt.executeQuery(sqlString);
             hTable = getHashtable(isUpper, rs);
             return hTable;
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         finally {
             ConnectionFactory.closeConnection(rs, stmt, conn);
         }
         return hTable;
     }

    

	public static Hashtable<String, String> getHashtable(boolean isUpper, ResultSet rs) throws SQLException {
		Hashtable<String, String> hTable = new Hashtable<String, String>();
		int t;
		int numberOfColumns;
		String columnName;
		if (rs.next()) {
		     ResultSetMetaData rsmd = rs.getMetaData();
		     numberOfColumns = rsmd.getColumnCount();
		     for (int k = 1; k <= numberOfColumns; k++) {
		         t = rsmd.getColumnType(k);
		         if(isUpper){
		        	 columnName = rsmd.getColumnName(k).toUpperCase();
		         }else{
		        	 columnName = rsmd.getColumnName(k).toLowerCase();
		         }
		         
		         //columnName = rsmd.getColumnName(k);
		         if(t==12||t==1||t==3)
		         {
		             hTable.put(columnName,(rs.getString(k)==null)?"":rs.getString(k));
		         }
		         else if(t==93)
		         {
		             hTable.put(columnName,(rs.getDate(k)==null)?"":rs.getDate(k).toString());
		         }
		         else if(t==2||t==4||t==-5)
		         {
		             hTable.put(columnName,Long.toString(rs.getLong(k)));
		         }
		         else
		         {
		               hTable.put(columnName,rs.getString(k) );
		         }

		     }
		 }
		return hTable;
	}

   /**
    * 
    * @param sqlStatement
    * @param conn
    * @return
    */
    public static ArrayList<Hashtable<String, String>> getMultiRowInfo(String sqlStatement,Connection conn)
    {
        System.out.print("\t" + sqlStatement);
        ArrayList<Hashtable<String, String>> recordArray = new ArrayList<Hashtable<String, String>>();
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            stmt = conn.createStatement();
            rs=stmt.executeQuery(sqlStatement);
            recordArray = getList(rs);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionFactory.closeConnection(rs, stmt, conn);
        }
        return recordArray;
    }
    
    public void test(String sqlStatement,Connection conn){
    	ArrayList<Hashtable<String, String>> list= getMultiRowInfo(sqlStatement,conn);
    	for(int i=0;i<list.size();i++){
    		Hashtable<String, String> table = list.get(i);
    		String zdmclass = table.get("zdmclass");
    		String zdmlogsys = table.get("zdmlogsys");
    		String zdmobjkey = table.get("zdmobjkey");
    		
    		/**
    		 * select * from sapr3.ZDM_PARKTABLE_FULL where zdmclass='?' and zdmlogsys='?' and zdmobjkey='?'
			union
				select * from sapr3.ZDM_PARKTABLE_FULL where zdmclass='MD_CHW_NA' and zdmlogsys='FCQF100SW3' and zdmobjkey='300MK_2231_MTC'
    		 */
    		StringBuffer sql= new StringBuffer();
    		if(i>0) sql.append("union");
    		sql.append("select * from sapr3.ZDM_PARKTABLE_FULL where zdmclass='");
    		sql.append(zdmclass);
    		sql.append("' and zdmlogsys='");
    		sql.append(zdmlogsys);
    		sql.append("' and zdmlogsys='");
    		sql.append(zdmobjkey+"'");
    		
    		
    	}
    }
    
    
    /**
     * 
     * @param sqlStatement
     * @param conn
     * @return
     */
     public static ArrayList<Hashtable<String, String>> getMultiRowInfo(String sql,Connection conn, Object[] params)
     {
         System.out.print("\t" + sql);
         ArrayList<Hashtable<String, String>> recordArray = new ArrayList<Hashtable<String, String>>();
         ResultSet rs = null;
         PreparedStatement pstmt = null;
         try
         {
        	 pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        	 if (null != params && 0 < params.length) {
                 setParams(pstmt, params);
             }
             rs=pstmt.executeQuery();
             String gsql = getPreparedSQL(sql, params);
             System.out.print("\t" + gsql);
             
             recordArray = getList(rs);
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
         finally
         {
             ConnectionFactory.closeConnection(rs, pstmt, conn);
         }
         return recordArray;
     }



	public static ArrayList<Hashtable<String, String>> getList(ResultSet rs) throws SQLException {
		ArrayList<Hashtable<String, String>> recordArray = new ArrayList<Hashtable<String, String>>();
		ResultSetMetaData rsmd;
		int numberOfColumns;
		rsmd = rs.getMetaData();
		numberOfColumns = rsmd.getColumnCount();
		while(rs.next())
		{
		    Hashtable<String, String> hTable = getHashtable(rs, rsmd, numberOfColumns);
		    recordArray.add(hTable);
		}
		return recordArray;
	}



	public static Hashtable<String, String> getHashtable(ResultSet rs, ResultSetMetaData rsmd, int numberOfColumns) throws SQLException {
		int t;
		String columnName;
		Hashtable<String, String> hTable=new Hashtable<String, String>();		    
		for (int k=1;k<=numberOfColumns;k++)
		{
		    t = rsmd.getColumnType(k);
		    columnName = rsmd.getColumnName(k).toLowerCase();
		    if(t==12||t==1||t==3)
		    {
		    	hTable.put(columnName,(rs.getString(k)==null)?"":rs.getString(k));
		    }
		    else if(t==93)
		    {
		    	hTable.put(columnName,(rs.getDate(k)==null)?"":rs.getDate(k).toString());
		    }
		     else if(t==2||t==4||t==-5)
		     {
		        hTable.put(columnName,Long.toString(rs.getLong(k)));
		     }
		     else
		     {
		        hTable.put(columnName,rs.getString(k) );
		     }
		}
		return hTable;
	}
     
     /**
       * @param pstmt
       * @param params
     * @throws SQLException 
       */
      private static void setParams(PreparedStatement pstmt, Object[] params) throws SQLException {
          if (null != params) {
              for (int i = 0, paramNum = params.length; i < paramNum; i++) {
                  try {
                      if (null != params[i] &&
                          params[i] instanceof java.sql.Date) {
                          pstmt.setDate(i + 1,(java.sql.Date) params[i]);
                      } else {
                          pstmt.setObject(i + 1, params[i]);
                      }
                  } catch (SQLException e) {
                      System.out.println(e.getMessage());
                  }
              }
          }
      }
      
      /**
       * @param sql
       * @param params
       * @return
       */
      private static String getPreparedSQL(String sql, Object[] params) {
          int paramNum = 0;
          if (null != params)  paramNum = params.length;
          if (1 > paramNum) return sql;
          StringBuffer returnSQL = new StringBuffer();
          String[] subSQL = sql.split("\\?");
          for (int i = 0; i < paramNum; i++) {
              if (params[i] instanceof java.sql.Date) {
                  returnSQL.append(subSQL[i]).append(" '").append((java.sql.Date)params[i]).append("' ");
              } else {
                  returnSQL.append(subSQL[i]).append(" '").append(params[i]).append("' ");
              }
          }

          if (subSQL.length > params.length) {
              returnSQL.append(subSQL[subSQL.length - 1]);
          }
          return returnSQL.toString();
      }

     
}