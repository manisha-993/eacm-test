/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import org.junit.Test;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Chw001ClfCreateTest
/*    */ {
/*    */   @Test
/*    */   public void testSuccess() throws Exception {
/* 13 */     System.out.println("------------- Test Chw001ClfCreate start -------------");
/* 14 */     String str1 = "C:/EACM_DEV/xml/MODEL_UPDATE_MODEL1284872.xml";
/* 15 */     String str2 = CommonEntities.loadXml(str1);
/* 16 */     String str3 = "fvtcloudpdh";
/* 17 */     Connection connection1 = ConnectionFactory.getConnection(str3);
/*    */     
/* 19 */     String str4 = "fvtcloudods";
/* 20 */     Connection connection2 = ConnectionFactory.getConnection(str4);
/*    */     
/* 22 */     MODEL mODEL = CommonEntities.getModelFromXml(str2);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 29 */     System.out.println("------------- Test Chw001ClfCreate end -------------");
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\Chw001ClfCreateTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */