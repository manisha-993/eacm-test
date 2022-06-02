package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * @version 1.0
 */

public class ConnectionFactory {
    //private static Logger logger = Logger.getLogger(ConnectionFactory.class.getName());

    //move the getconnection from RdhDb2Proxy.class
	public static final String KEY_RDH_DB_DATASOURCE = "rdh.dataSource";
	public static final String KEY_RDH_DB_JDBCDRIVERCLASS = "rdh.jdbcDriverClass";
	public static final String KEY_RDH_DB_DBUSER = "rdh.DBUser";
	public static final String KEY_RDH_DB_DBPASSWORD = "rdh.DBPassword";
	
	public static final String KEY_PROD_DB_DATASOURCE = "prod.dataSource";
	public static final String KEY_PROD_DB_JDBCDRIVERCLASS = "prod.jdbcDriverClass";
	public static final String KEY_PROD_DB_DBUSER = "prod.DBUser";
	public static final String KEY_PROD_DB_DBPASSWORD = "prod.DBPassword";
	
	
	
	//private Connection _connection;
	
	public static int commit(Connection _connection)
	{
		if(_connection == null) return -1;
		try {
			_connection.commit();
			return 0;
		}
		catch (SQLException ex) {
			System.out.println("RdhDb2Proxy.commit(), Commit error.\n" + ex.getMessage());
			return -1;
		}
	}

	public static int rollback(Connection _connection)
	{
		if(_connection == null) return -1;
		try {
			_connection.rollback();
			return 0;
		}
		catch (SQLException ex) {
			System.out.println("RdhDb2Proxy.rollback(), Rollback error.\n" + ex.getMessage());
			return -1;
		}
	}
	

	public static void closeConnection(Connection conn) {
	    try {
	        if (conn != null) {
	        	conn.commit();
	            conn.close();
	        }
	    }
	    catch (SQLException err) {
	        err.printStackTrace();
	    }
	}

	public static void closeConnection(Statement stmt, Connection conn) {
	    try {
	        if (stmt != null) {
	            stmt.close();
	        }
	        //closeConnection(conn);
	    }
	    catch (SQLException err) {
	        err.printStackTrace();
	    }
	}

	public static void closeConnection(PreparedStatement prestmt,
	                                   Connection conn) {
	    try {
	        if (prestmt != null) {
	            prestmt.close();
	        }
	        //closeConnection(conn);
	    }
	    catch (SQLException err) {
	        err.printStackTrace();
	    }
	}

	public static void closeConnection(ResultSet rs, Statement stmt,
	                                   Connection conn) {
	    try {
	        if (rs != null) {
	            rs.close();
	        }
	        if (stmt != null) {
	            stmt.close();
	        }
	        //closeConnection(conn);
	    }
	    catch (SQLException err) {
	        err.printStackTrace();
	    }
	}

	public static void closeConnection(ResultSet rs, PreparedStatement prestmt,
	                                   Connection conn) {
	    try {
	        if (rs != null) {
	            rs.close();
	        }
	        if (prestmt != null) {
	            prestmt.close();
	        }
	        //closeConnection(conn);
	    }
	    catch (SQLException err) {
	        err.printStackTrace();
	    }
	}

	public static Connection getTransConnection(String dbcpNameValue) {
	    if (dbcpNameValue == null || "".equals(dbcpNameValue)) {
	        return null;
	    }
	    Connection conn = null;
	    try {
	        Context ctx = new InitialContext();
	        javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(
	            dbcpNameValue);
	        conn = ds.getConnection();
	        //conn.setAutoCommit(false);
	    }
	    catch (Exception err) {
	        err.printStackTrace();
	    }
	    return conn;
	}

	public static void closeTransConnection(Connection conn,
	                                        boolean abortTransaction) {
	    try {
	        if (conn != null) {
	            if (abortTransaction) {
	                conn.rollback();
	            }
	            else {
	                conn.commit();
	            }
	            //conn.setAutoCommit(true);
	            conn.close();
	        }
	    }
	    catch (SQLException err) {
	        err.printStackTrace();
	    }
	}

	public static void closeTransConnection(Statement stmt, Connection conn,
	                                        boolean abortTransaction) {
	    try {
	        if (stmt != null) {
	            stmt.close();
	        }
	        closeTransConnection(conn, abortTransaction);
	    }
	    catch (SQLException err) {
	        err.printStackTrace();
	    }
	}

	public static void closeTransConnection(ResultSet rs, Statement stmt,
	                                        Connection conn,
	                                        boolean abortTransaction) {
	    try {
	        if (rs != null) {
	            rs.close();
	        }
	        if (stmt != null) {
	            stmt.close();
	        }
	        closeTransConnection(conn, abortTransaction);
	    }
	    catch (SQLException err) {
	        err.printStackTrace();
	    }
	}
	
	public static Connection getConnection(String prefix) throws SQLException, ClassNotFoundException {
		String datasource = ConfigUtils.getProperty(prefix + "_" +  ConnectionFactory.KEY_RDH_DB_DATASOURCE);
		String driver = ConfigUtils.getProperty(prefix + "_" +  ConnectionFactory.KEY_RDH_DB_JDBCDRIVERCLASS);
		String user = ConfigUtils.getProperty(prefix + "_" +  KEY_RDH_DB_DBUSER);
		String password = ConfigUtils.getProperty(prefix + "_" +  ConnectionFactory.KEY_RDH_DB_DBPASSWORD);
		Connection _connection = null;
		try {
			Class.forName(driver);
			_connection = DriverManager.getConnection(datasource, user, password);
			_connection.setAutoCommit(false);
			System.out.println("connect("+prefix+"), has connect to db successfully");
		}catch (SQLException ex) {
			System.out.println("connect("+prefix+"), Can not connect to database.\n" + ex.getMessage());
			System.out.println("datasource=" + datasource);
			throw ex;
		}
		catch (ClassNotFoundException ex) {
			
			System.out.println("connect("+prefix+"), Can not load Database driver.\n" + ex.getMessage());
			System.out.println("datasource=" + datasource);
			throw ex;
		}  
		return _connection;
		
	}
	
	public static Connection getRDHConnection() throws SQLException, ClassNotFoundException {
		
		String datasource = ConfigUtils.getProperty(ConnectionFactory.KEY_RDH_DB_DATASOURCE);
		String driver = ConfigUtils.getProperty(ConnectionFactory.KEY_RDH_DB_JDBCDRIVERCLASS);
		String user = ConfigUtils.getProperty(KEY_RDH_DB_DBUSER);
		String password = ConfigUtils.getProperty(ConnectionFactory.KEY_RDH_DB_DBPASSWORD);
		Connection _connection = null;
		try {
			Class.forName(driver);
			_connection = DriverManager.getConnection(datasource, user, password);
			_connection.setAutoCommit(false);
			System.out.println("connect(), has connect to db successfully");
		}catch (SQLException ex) {
			System.out.println("connect(), Can not connect to database.\n" + ex.getMessage());
			throw ex;
		}
		catch (ClassNotFoundException ex) {
			System.out.println("connect(), Can not load Database driver.\n" + ex.getMessage());
			throw ex;
		}  
		return _connection;
		
	}
	
//	public static Connection getMMLCConnection() throws SQLException, ClassNotFoundException {
//		
//		String datasource = ConfigUtils.getProperty(ConnectionFactory.KEY_PROD_DB_DATASOURCE);
//		String driver = ConfigUtils.getProperty(ConnectionFactory.KEY_PROD_DB_JDBCDRIVERCLASS);
//		String user = ConfigUtils.getProperty(KEY_PROD_DB_DBUSER);
//		String password = ConfigUtils.getProperty(ConnectionFactory.KEY_PROD_DB_DBPASSWORD);
//		Connection _connection = null;
//		try {
//			Class.forName(driver);
//			_connection = DriverManager.getConnection(datasource, user, password);
//			_connection.setAutoCommit(false);
//			System.out.println("connect(), has connect to db successfully");
//		}catch (SQLException ex) {
//			System.out.println("connect(), Can not connect to database.\n" + ex.getMessage());
//			throw ex;
//		}
//		catch (ClassNotFoundException ex) {
//			System.out.println("connect(), Can not load Database driver.\n" + ex.getMessage());
//			throw ex;
//		}  
//		return _connection;
//		
//	}
	
	
//	public static void main(String[] args){
//		String jdbc_url = "jdbc:db2://rdtst1e4.sby.ibm.com:50000/pprdshad";
//        String jdbc_user = "oimdev";
//        String jdbc_password = "2016oimdev";
//        String jdbc_driver = "com.ibm.db2.jcc.DB2Driver";
//        Connection conn = null;
//	    try {
//	        Class.forName(jdbc_driver);
//	        conn = DriverManager.getConnection(jdbc_url, jdbc_user,jdbc_password);
//	        if(conn!=null){
//	        	System.out.print("connect to the databse successfully");
//	        }
//	    }
//	    catch (ClassNotFoundException cne) {
//	       cne.printStackTrace();
//	    }
//	    catch (SQLException se) {
//	        se.printStackTrace();
//	    }
//	    //return conn;
//	}
	
	public static void main(String[] args){
		String jdbc_url = "jdbc:db2://rdtst1e4.sby.ibm.com:60021/pprdshad:sslConnection=true;sslTrustStoreLocation=./rdxsitkeystore.jks;sslTrustStorePassword=DB2RDX;";
        String jdbc_user = "oimdev";
        String jdbc_password = "2016oimdev";
        String jdbc_driver = "com.ibm.db2.jcc.DB2Driver";
        Connection conn = null;
	    try {
	        Class.forName(jdbc_driver);
	        conn = DriverManager.getConnection(jdbc_url, jdbc_user,jdbc_password);
	        if(conn!=null){
	        	System.out.print("connect to the databse successfully");
	        }
	    }
	    catch (ClassNotFoundException cne) {
	       cne.printStackTrace();
	    }
	    catch (SQLException se) {
	        se.printStackTrace();
	    }
	    //return conn;
	}
	


}