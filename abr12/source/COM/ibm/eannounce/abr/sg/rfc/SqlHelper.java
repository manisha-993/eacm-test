/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
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
/*     */ public class SqlHelper
/*     */ {
/*     */   public static int runUpdateSql(String paramString, Connection paramConnection) {
/*  26 */     int i = -1;
/*  27 */     Statement statement = null;
/*     */     try {
/*  29 */       statement = paramConnection.createStatement();
/*  30 */       i = statement.executeUpdate(paramString);
/*  31 */       return i;
/*     */     }
/*  33 */     catch (SQLException sQLException) {
/*  34 */       sQLException.printStackTrace();
/*  35 */       return -1;
/*     */     } finally {
/*     */       
/*     */       try {
/*  39 */         paramConnection.commit();
/*  40 */       } catch (SQLException sQLException) {
/*  41 */         sQLException.printStackTrace();
/*     */       } 
/*  43 */       ConnectionFactory.closeConnection(statement, paramConnection);
/*     */     } 
/*     */   }
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
/*     */   public static int runUpdateSqlException(String paramString, Connection paramConnection) throws SQLException {
/*  58 */     System.out.print("\t" + paramString);
/*  59 */     int i = -1;
/*  60 */     Statement statement = null;
/*     */     try {
/*  62 */       statement = paramConnection.createStatement();
/*  63 */       i = statement.executeUpdate(paramString);
/*  64 */       return i;
/*     */     }
/*  66 */     catch (SQLException sQLException) {
/*  67 */       sQLException.printStackTrace();
/*  68 */       throw sQLException;
/*     */     } finally {
/*     */       
/*     */       try {
/*  72 */         paramConnection.commit();
/*  73 */       } catch (SQLException sQLException) {
/*  74 */         sQLException.printStackTrace();
/*     */       } 
/*  76 */       ConnectionFactory.closeConnection(statement, paramConnection);
/*     */     } 
/*     */   }
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
/*     */   public static ResultSet runQuerySql(String paramString, Connection paramConnection) {
/*  90 */     Statement statement = null;
/*  91 */     ResultSet resultSet = null;
/*     */     try {
/*  93 */       statement = paramConnection.createStatement();
/*  94 */       resultSet = statement.executeQuery(paramString);
/*  95 */       return resultSet;
/*     */     }
/*  97 */     catch (SQLException sQLException) {
/*  98 */       sQLException.printStackTrace();
/*  99 */       return null;
/*     */     } 
/*     */   }
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
/*     */   public static boolean runProcedure(String paramString, Connection paramConnection) {
/* 114 */     System.out.print("\t" + paramString);
/* 115 */     boolean bool = false;
/* 116 */     Statement statement = null;
/*     */     try {
/* 118 */       statement = paramConnection.createStatement();
/* 119 */       bool = statement.execute(paramString);
/* 120 */       return bool;
/*     */     }
/* 122 */     catch (SQLException sQLException) {
/* 123 */       sQLException.printStackTrace();
/* 124 */       return false;
/*     */     } finally {
/*     */       
/*     */       try {
/* 128 */         paramConnection.commit();
/* 129 */       } catch (SQLException sQLException) {
/* 130 */         sQLException.printStackTrace();
/*     */       } 
/* 132 */       ConnectionFactory.closeConnection(statement, paramConnection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExist(String paramString, Connection paramConnection) {
/* 138 */     System.out.print("\t" + paramString);
/* 139 */     ResultSet resultSet = null;
/* 140 */     Statement statement = null;
/*     */     try {
/* 142 */       statement = paramConnection.createStatement();
/* 143 */       resultSet = statement.executeQuery(paramString);
/* 144 */       if (resultSet.next()) {
/* 145 */         return true;
/*     */       }
/* 147 */       return false;
/*     */     
/*     */     }
/* 150 */     catch (SQLException sQLException) {
/* 151 */       sQLException.printStackTrace();
/*     */     } finally {
/*     */       
/* 154 */       ConnectionFactory.closeConnection(resultSet, statement, paramConnection);
/*     */     } 
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getDbDate(Connection paramConnection) {
/* 161 */     String str1 = "";
/* 162 */     str1 = "select TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD') as yyyymmdd FROM SYSIBM.SYSDUMMY1";
/*     */     
/* 164 */     String str2 = "";
/*     */     try {
/* 166 */       str2 = getSingleRow(str1, paramConnection).get("yyyymmdd");
/*     */     }
/* 168 */     catch (Exception exception) {
/* 169 */       exception.printStackTrace();
/*     */     } 
/* 171 */     return str2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Hashtable<String, String> getSingleRow(String paramString, Connection paramConnection) {
/* 183 */     System.out.print("\t" + paramString);
/* 184 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 185 */     ResultSet resultSet = null;
/* 186 */     Statement statement = null;
/*     */     try {
/* 188 */       statement = paramConnection.createStatement();
/* 189 */       resultSet = statement.executeQuery(paramString);
/* 190 */       hashtable = (Hashtable)getHashtable(false, resultSet);
/* 191 */       return (Hashtable)hashtable;
/*     */     }
/* 193 */     catch (SQLException sQLException) {
/* 194 */       sQLException.printStackTrace();
/*     */     } finally {
/*     */       
/* 197 */       ConnectionFactory.closeConnection(resultSet, statement, paramConnection);
/*     */     } 
/* 199 */     return (Hashtable)hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Hashtable getSingleRow(String paramString, boolean paramBoolean, Connection paramConnection) {
/* 210 */     System.out.print("\t" + paramString);
/* 211 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 212 */     ResultSet resultSet = null;
/* 213 */     Statement statement = null;
/*     */     try {
/* 215 */       statement = paramConnection.createStatement();
/* 216 */       resultSet = statement.executeQuery(paramString);
/* 217 */       hashtable = (Hashtable)getHashtable(paramBoolean, resultSet);
/* 218 */       return hashtable;
/*     */     }
/* 220 */     catch (SQLException sQLException) {
/* 221 */       sQLException.printStackTrace();
/*     */     } finally {
/*     */       
/* 224 */       ConnectionFactory.closeConnection(resultSet, statement, paramConnection);
/*     */     } 
/* 226 */     return hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Hashtable<String, String> getHashtable(boolean paramBoolean, ResultSet paramResultSet) throws SQLException {
/* 232 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*     */ 
/*     */ 
/*     */     
/* 236 */     if (paramResultSet.next()) {
/* 237 */       ResultSetMetaData resultSetMetaData = paramResultSet.getMetaData();
/* 238 */       int i = resultSetMetaData.getColumnCount();
/* 239 */       for (byte b = 1; b <= i; b++) {
/* 240 */         String str; int j = resultSetMetaData.getColumnType(b);
/* 241 */         if (paramBoolean) {
/* 242 */           str = resultSetMetaData.getColumnName(b).toUpperCase();
/*     */         } else {
/* 244 */           str = resultSetMetaData.getColumnName(b).toLowerCase();
/*     */         } 
/*     */ 
/*     */         
/* 248 */         if (j == 12 || j == 1 || j == 3) {
/*     */           
/* 250 */           hashtable.put(str, (paramResultSet.getString(b) == null) ? "" : paramResultSet.getString(b));
/*     */         }
/* 252 */         else if (j == 93) {
/*     */           
/* 254 */           hashtable.put(str, (paramResultSet.getDate(b) == null) ? "" : paramResultSet.getDate(b).toString());
/*     */         }
/* 256 */         else if (j == 2 || j == 4 || j == -5) {
/*     */           
/* 258 */           hashtable.put(str, Long.toString(paramResultSet.getLong(b)));
/*     */         }
/*     */         else {
/*     */           
/* 262 */           hashtable.put(str, paramResultSet.getString(b));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     return (Hashtable)hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Hashtable<String, String>> getMultiRowInfo(String paramString, Connection paramConnection) {
/* 278 */     System.out.print("\t" + paramString);
/* 279 */     ArrayList<Hashtable<String, String>> arrayList = new ArrayList();
/* 280 */     ResultSet resultSet = null;
/* 281 */     Statement statement = null;
/*     */     
/*     */     try {
/* 284 */       statement = paramConnection.createStatement();
/* 285 */       resultSet = statement.executeQuery(paramString);
/* 286 */       arrayList = getList(resultSet);
/*     */     }
/* 288 */     catch (Exception exception) {
/*     */       
/* 290 */       exception.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       
/* 294 */       ConnectionFactory.closeConnection(resultSet, statement, paramConnection);
/*     */     } 
/* 296 */     return arrayList;
/*     */   }
/*     */   
/*     */   public void test(String paramString, Connection paramConnection) {
/* 300 */     ArrayList<Hashtable<String, String>> arrayList = getMultiRowInfo(paramString, paramConnection);
/* 301 */     for (byte b = 0; b < arrayList.size(); b++) {
/* 302 */       Hashtable hashtable = arrayList.get(b);
/* 303 */       String str1 = (String)hashtable.get("zdmclass");
/* 304 */       String str2 = (String)hashtable.get("zdmlogsys");
/* 305 */       String str3 = (String)hashtable.get("zdmobjkey");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 312 */       StringBuffer stringBuffer = new StringBuffer();
/* 313 */       if (b > 0) stringBuffer.append("union"); 
/* 314 */       stringBuffer.append("select * from sapr3.ZDM_PARKTABLE_FULL where zdmclass='");
/* 315 */       stringBuffer.append(str1);
/* 316 */       stringBuffer.append("' and zdmlogsys='");
/* 317 */       stringBuffer.append(str2);
/* 318 */       stringBuffer.append("' and zdmlogsys='");
/* 319 */       stringBuffer.append(str3 + "'");
/*     */     } 
/*     */   }
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
/*     */   public static ArrayList<Hashtable<String, String>> getMultiRowInfo(String paramString, Connection paramConnection, Object[] paramArrayOfObject) {
/* 334 */     System.out.print("\t" + paramString);
/* 335 */     ArrayList<Hashtable<String, String>> arrayList = new ArrayList();
/* 336 */     ResultSet resultSet = null;
/* 337 */     PreparedStatement preparedStatement = null;
/*     */     
/*     */     try {
/* 340 */       preparedStatement = paramConnection.prepareStatement(paramString, 1);
/* 341 */       if (null != paramArrayOfObject && 0 < paramArrayOfObject.length) {
/* 342 */         setParams(preparedStatement, paramArrayOfObject);
/*     */       }
/* 344 */       resultSet = preparedStatement.executeQuery();
/* 345 */       String str = getPreparedSQL(paramString, paramArrayOfObject);
/* 346 */       System.out.print("\t" + str);
/*     */       
/* 348 */       arrayList = getList(resultSet);
/*     */     }
/* 350 */     catch (Exception exception) {
/*     */       
/* 352 */       exception.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       
/* 356 */       ConnectionFactory.closeConnection(resultSet, preparedStatement, paramConnection);
/*     */     } 
/* 358 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Hashtable<String, String>> getList(ResultSet paramResultSet) throws SQLException {
/* 364 */     ArrayList<Hashtable<String, String>> arrayList = new ArrayList();
/*     */ 
/*     */     
/* 367 */     ResultSetMetaData resultSetMetaData = paramResultSet.getMetaData();
/* 368 */     int i = resultSetMetaData.getColumnCount();
/* 369 */     while (paramResultSet.next()) {
/*     */       
/* 371 */       Hashtable<String, String> hashtable = getHashtable(paramResultSet, resultSetMetaData, i);
/* 372 */       arrayList.add(hashtable);
/*     */     } 
/* 374 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Hashtable<String, String> getHashtable(ResultSet paramResultSet, ResultSetMetaData paramResultSetMetaData, int paramInt) throws SQLException {
/* 382 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 383 */     for (byte b = 1; b <= paramInt; b++) {
/*     */       
/* 385 */       int i = paramResultSetMetaData.getColumnType(b);
/* 386 */       String str = paramResultSetMetaData.getColumnName(b).toLowerCase();
/* 387 */       if (i == 12 || i == 1 || i == 3) {
/*     */         
/* 389 */         hashtable.put(str, (paramResultSet.getString(b) == null) ? "" : paramResultSet.getString(b));
/*     */       }
/* 391 */       else if (i == 93) {
/*     */         
/* 393 */         hashtable.put(str, (paramResultSet.getDate(b) == null) ? "" : paramResultSet.getDate(b).toString());
/*     */       }
/* 395 */       else if (i == 2 || i == 4 || i == -5) {
/*     */         
/* 397 */         hashtable.put(str, Long.toString(paramResultSet.getLong(b)));
/*     */       }
/*     */       else {
/*     */         
/* 401 */         hashtable.put(str, paramResultSet.getString(b));
/*     */       } 
/*     */     } 
/* 404 */     return (Hashtable)hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setParams(PreparedStatement paramPreparedStatement, Object[] paramArrayOfObject) throws SQLException {
/* 413 */     if (null != paramArrayOfObject) {
/* 414 */       byte b; int i; for (b = 0, i = paramArrayOfObject.length; b < i; b++) {
/*     */         try {
/* 416 */           if (null != paramArrayOfObject[b] && paramArrayOfObject[b] instanceof Date) {
/*     */             
/* 418 */             paramPreparedStatement.setDate(b + 1, (Date)paramArrayOfObject[b]);
/*     */           } else {
/* 420 */             paramPreparedStatement.setObject(b + 1, paramArrayOfObject[b]);
/*     */           } 
/* 422 */         } catch (SQLException sQLException) {
/* 423 */           System.out.println(sQLException.getMessage());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getPreparedSQL(String paramString, Object[] paramArrayOfObject) {
/* 435 */     int i = 0;
/* 436 */     if (null != paramArrayOfObject) i = paramArrayOfObject.length; 
/* 437 */     if (1 > i) return paramString; 
/* 438 */     StringBuffer stringBuffer = new StringBuffer();
/* 439 */     String[] arrayOfString = paramString.split("\\?");
/* 440 */     for (byte b = 0; b < i; b++) {
/* 441 */       if (paramArrayOfObject[b] instanceof Date) {
/* 442 */         stringBuffer.append(arrayOfString[b]).append(" '").append(paramArrayOfObject[b]).append("' ");
/*     */       } else {
/* 444 */         stringBuffer.append(arrayOfString[b]).append(" '").append(paramArrayOfObject[b]).append("' ");
/*     */       } 
/*     */     } 
/*     */     
/* 448 */     if (arrayOfString.length > paramArrayOfObject.length) {
/* 449 */       stringBuffer.append(arrayOfString[arrayOfString.length - 1]);
/*     */     }
/* 451 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\SqlHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */