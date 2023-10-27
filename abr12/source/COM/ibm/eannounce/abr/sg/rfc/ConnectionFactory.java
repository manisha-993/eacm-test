/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import com.ibm.eacm.AES256Utils;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnectionFactory
/*     */ {
/*     */   public static final String KEY_RDH_DB_DATASOURCE = "rdh.dataSource";
/*     */   public static final String KEY_RDH_DB_JDBCDRIVERCLASS = "rdh.jdbcDriverClass";
/*     */   public static final String KEY_RDH_DB_DBUSER = "rdh.DBUser";
/*     */   public static final String KEY_RDH_DB_DBPASSWORD = "rdh.DBPassword";
/*     */   public static final String KEY_PROD_DB_DATASOURCE = "prod.dataSource";
/*     */   public static final String KEY_PROD_DB_JDBCDRIVERCLASS = "prod.jdbcDriverClass";
/*     */   public static final String KEY_PROD_DB_DBUSER = "prod.DBUser";
/*     */   public static final String KEY_PROD_DB_DBPASSWORD = "prod.DBPassword";
/*     */   
/*     */   public static int commit(Connection paramConnection) {
/*  40 */     if (paramConnection == null) return -1; 
/*     */     try {
/*  42 */       paramConnection.commit();
/*  43 */       return 0;
/*     */     }
/*  45 */     catch (SQLException sQLException) {
/*  46 */       System.out.println("RdhDb2Proxy.commit(), Commit error.\n" + sQLException.getMessage());
/*  47 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int rollback(Connection paramConnection) {
/*  53 */     if (paramConnection == null) return -1; 
/*     */     try {
/*  55 */       paramConnection.rollback();
/*  56 */       return 0;
/*     */     }
/*  58 */     catch (SQLException sQLException) {
/*  59 */       System.out.println("RdhDb2Proxy.rollback(), Rollback error.\n" + sQLException.getMessage());
/*  60 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeConnection(Connection paramConnection) {
/*     */     try {
/*  67 */       if (paramConnection != null) {
/*  68 */         paramConnection.commit();
/*  69 */         paramConnection.close();
/*     */       }
/*     */     
/*  72 */     } catch (SQLException sQLException) {
/*  73 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void closeConnection(Statement paramStatement, Connection paramConnection) {
/*     */     try {
/*  79 */       if (paramStatement != null) {
/*  80 */         paramStatement.close();
/*     */       
/*     */       }
/*     */     }
/*  84 */     catch (SQLException sQLException) {
/*  85 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeConnection(PreparedStatement paramPreparedStatement, Connection paramConnection) {
/*     */     try {
/*  92 */       if (paramPreparedStatement != null) {
/*  93 */         paramPreparedStatement.close();
/*     */       
/*     */       }
/*     */     }
/*  97 */     catch (SQLException sQLException) {
/*  98 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeConnection(ResultSet paramResultSet, Statement paramStatement, Connection paramConnection) {
/*     */     try {
/* 105 */       if (paramResultSet != null) {
/* 106 */         paramResultSet.close();
/*     */       }
/* 108 */       if (paramStatement != null) {
/* 109 */         paramStatement.close();
/*     */       
/*     */       }
/*     */     }
/* 113 */     catch (SQLException sQLException) {
/* 114 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeConnection(ResultSet paramResultSet, PreparedStatement paramPreparedStatement, Connection paramConnection) {
/*     */     try {
/* 121 */       if (paramResultSet != null) {
/* 122 */         paramResultSet.close();
/*     */       }
/* 124 */       if (paramPreparedStatement != null) {
/* 125 */         paramPreparedStatement.close();
/*     */       
/*     */       }
/*     */     }
/* 129 */     catch (SQLException sQLException) {
/* 130 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Connection getTransConnection(String paramString) {
/* 135 */     if (paramString == null || "".equals(paramString)) {
/* 136 */       return null;
/*     */     }
/* 138 */     Connection connection = null;
/*     */     try {
/* 140 */       InitialContext initialContext = new InitialContext();
/* 141 */       DataSource dataSource = (DataSource)initialContext.lookup(paramString);
/*     */       
/* 143 */       connection = dataSource.getConnection();
/*     */     
/*     */     }
/* 146 */     catch (Exception exception) {
/* 147 */       exception.printStackTrace();
/*     */     } 
/* 149 */     return connection;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeTransConnection(Connection paramConnection, boolean paramBoolean) {
/*     */     try {
/* 155 */       if (paramConnection != null) {
/* 156 */         if (paramBoolean) {
/* 157 */           paramConnection.rollback();
/*     */         } else {
/*     */           
/* 160 */           paramConnection.commit();
/*     */         } 
/*     */         
/* 163 */         paramConnection.close();
/*     */       }
/*     */     
/* 166 */     } catch (SQLException sQLException) {
/* 167 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeTransConnection(Statement paramStatement, Connection paramConnection, boolean paramBoolean) {
/*     */     try {
/* 174 */       if (paramStatement != null) {
/* 175 */         paramStatement.close();
/*     */       }
/* 177 */       closeTransConnection(paramConnection, paramBoolean);
/*     */     }
/* 179 */     catch (SQLException sQLException) {
/* 180 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeTransConnection(ResultSet paramResultSet, Statement paramStatement, Connection paramConnection, boolean paramBoolean) {
/*     */     try {
/* 188 */       if (paramResultSet != null) {
/* 189 */         paramResultSet.close();
/*     */       }
/* 191 */       if (paramStatement != null) {
/* 192 */         paramStatement.close();
/*     */       }
/* 194 */       closeTransConnection(paramConnection, paramBoolean);
/*     */     }
/* 196 */     catch (SQLException sQLException) {
/* 197 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Connection getConnection(String paramString) throws SQLException, ClassNotFoundException {
/* 202 */     String str1 = ConfigUtils.getProperty(paramString + "_" + "rdh.dataSource");
/* 203 */     String str2 = ConfigUtils.getProperty(paramString + "_" + "rdh.jdbcDriverClass");
/* 204 */     String str3 = ConfigUtils.getProperty(paramString + "_" + "rdh.DBUser");
/* 205 */     String str4 = ConfigUtils.getProperty(paramString + "_" + "rdh.DBPassword");
/* 206 */     Connection connection = null;
/*     */     try {
/* 208 */       Class.forName(str2);
/* 209 */       connection = DriverManager.getConnection(str1, str3, AES256Utils.decrypt(str4));
/* 210 */       connection.setAutoCommit(false);
/* 211 */       System.out.println("connect(" + paramString + "), has connect to db successfully");
/* 212 */     } catch (SQLException sQLException) {
/* 213 */       System.out.println("connect(" + paramString + "), Can not connect to database.\n" + sQLException.getMessage());
/* 214 */       System.out.println("datasource=" + str1);
/* 215 */       throw sQLException;
/*     */     }
/* 217 */     catch (ClassNotFoundException classNotFoundException) {
/*     */       
/* 219 */       System.out.println("connect(" + paramString + "), Can not load Database driver.\n" + classNotFoundException.getMessage());
/* 220 */       System.out.println("datasource=" + str1);
/* 221 */       throw classNotFoundException;
/* 222 */     } catch (Exception exception) {
/*     */       
/* 224 */       exception.printStackTrace();
/*     */     } 
/* 226 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Connection getRDHConnection() throws SQLException, ClassNotFoundException {
/* 232 */     String str1 = ConfigUtils.getProperty("rdh.dataSource");
/* 233 */     String str2 = ConfigUtils.getProperty("rdh.jdbcDriverClass");
/* 234 */     String str3 = ConfigUtils.getProperty("rdh.DBUser");
/* 235 */     String str4 = ConfigUtils.getProperty("rdh.DBPassword");
/* 236 */     Connection connection = null;
/*     */     try {
/* 238 */       Class.forName(str2);
/* 239 */       connection = DriverManager.getConnection(str1, str3, AES256Utils.decrypt(str4));
/* 240 */       connection.setAutoCommit(false);
/* 241 */       System.out.println("connect(), has connect to db successfully");
/* 242 */     } catch (SQLException sQLException) {
/* 243 */       System.out.println("connect(), Can not connect to database.\n" + sQLException.getMessage());
/* 244 */       throw sQLException;
/*     */     }
/* 246 */     catch (ClassNotFoundException classNotFoundException) {
/* 247 */       System.out.println("connect(), Can not load Database driver.\n" + classNotFoundException.getMessage());
/* 248 */       throw classNotFoundException;
/* 249 */     } catch (Exception exception) {
/*     */       
/* 251 */       exception.printStackTrace();
/*     */     } 
/* 253 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 260 */     String str1 = "";
/* 261 */     String str2 = "";
/* 262 */     String str3 = "";
/* 263 */     String str4 = "com.ibm.db2.jcc.DB2Driver";
/* 264 */     Connection connection = null;
/*     */     try {
/* 266 */       Class.forName(str4);
/* 267 */       connection = DriverManager.getConnection(str1, str2, str3);
/* 268 */       if (connection != null) {
/* 269 */         System.out.print("connect to the databse successfully");
/*     */       }
/*     */     }
/* 272 */     catch (ClassNotFoundException classNotFoundException) {
/* 273 */       classNotFoundException.printStackTrace();
/*     */     }
/* 275 */     catch (SQLException sQLException) {
/* 276 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ConnectionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */