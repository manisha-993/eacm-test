/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import org.junit.Test;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChwMachTypeUpgTest
/*    */ {
/*    */   @Test
/*    */   public void testSuccess() throws Exception {
/* 13 */     System.out.println("------------- Test ChwMachTypeUpg start -------------");
/* 14 */     String str1 = "C:/EACM_DEV/xml/MODEL_UPDATE_MODEL1284872.xml";
/* 15 */     String str2 = CommonEntities.loadXml(str1);
/* 16 */     MODEL mODEL = CommonEntities.getModelFromXml(str2);
/* 17 */     String str3 = "fvtcloudpdh";
/* 18 */     Connection connection1 = ConnectionFactory.getConnection(str3);
/*    */     
/* 20 */     String str4 = "fvtcloudods";
/* 21 */     Connection connection2 = ConnectionFactory.getConnection(str4);
/*    */     
/* 23 */     ChwMachTypeUpg chwMachTypeUpg = new ChwMachTypeUpg(mODEL, connection1, connection2);
/* 24 */     chwMachTypeUpg.execute();
/* 25 */     System.out.println("------------- Test ChwMachTypeUpg end -------------");
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwMachTypeUpgTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */