/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import org.junit.Test;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommonEntitiesTest
/*    */ {
/*    */   @Test
/*    */   public void testModel() {
/* 22 */     System.out.println("------------- Test CommonEntitiesTest start -------------");
/* 23 */     String str1 = "C:/EACM_DEV/xml/MODEL_UPDATE_MODEL1284872.xml";
/* 24 */     String str2 = CommonEntities.loadXml(str1);
/* 25 */     MODEL mODEL = CommonEntities.getModelFromXml(str2);
/* 26 */     System.out.println("model.getSVCLEVCD = " + mODEL.getSVCLEVCD());
/* 27 */     System.out.println("------------- Test CommonEntitiesTest end -------------");
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\CommonEntitiesTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */