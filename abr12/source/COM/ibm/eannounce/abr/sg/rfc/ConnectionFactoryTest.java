/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
/*    */ import org.junit.Test;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConnectionFactoryTest
/*    */ {
/*    */   public static void main(String[] paramArrayOfString) throws ClassNotFoundException, SQLException {
/* 18 */     String str = "fvtcloudpdh";
/* 19 */     Connection connection = ConnectionFactory.getConnection(str);
/* 20 */     connection.close();
/*    */     
/* 22 */     str = "fvtcloudods";
/* 23 */     connection = ConnectionFactory.getConnection(str);
/* 24 */     connection.close();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void test() throws ClassNotFoundException, SQLException {
/* 31 */     String str1 = "devcloud";
/* 32 */     Connection connection = ConnectionFactory.getConnection(str1);
/*    */ 
/*    */     
/* 35 */     String str2 = "select count(*) as count from cache.XMLIDLCACHE with ur";
/* 36 */     PreparedStatement preparedStatement = connection.prepareStatement(str2);
/* 37 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 38 */     String str3 = "";
/* 39 */     while (resultSet.next()) {
/* 40 */       str3 = resultSet.getString("count");
/* 41 */       System.out.println("count=" + str3);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Object getCustomExistingDataWithKeysConcatenated(Connection paramConnection, String paramString1, String paramString2) throws SQLException {
/* 52 */     Object object = null;
/* 53 */     PreparedStatement preparedStatement = paramConnection.prepareStatement(paramString1);
/* 54 */     preparedStatement.setObject(1, paramString2);
/* 55 */     ResultSet resultSet = preparedStatement.executeQuery();
/*    */     
/*    */     try {
/* 58 */       if (resultSet.next()) {
/* 59 */         object = resultSet.getObject(1);
/*    */       }
/*    */     }
/*    */     finally {
/*    */       
/* 64 */       resultSet.close();
/* 65 */       preparedStatement.close();
/*    */     } 
/* 67 */     return object;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Timestamp getADD_DATE(Connection paramConnection, String paramString1, String paramString2) throws SQLException {
/* 74 */     Timestamp timestamp = (Timestamp)getCustomExistingDataWithKeysConcatenated(paramConnection, paramString1, paramString2);
/* 75 */     if (timestamp != null)
/*    */     {
/* 77 */       return timestamp;
/*    */     }
/* 79 */     return getNowTime();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Timestamp getNowTime() {
/* 85 */     Date date = new Date();
/* 86 */     return new Timestamp(date.getTime());
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ConnectionFactoryTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */