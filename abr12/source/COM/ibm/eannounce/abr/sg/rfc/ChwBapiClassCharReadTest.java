/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import org.junit.Assert;
/*    */ import org.junit.BeforeClass;
/*    */ import org.junit.Test;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChwBapiClassCharReadTest
/*    */ {
/*    */   private static String obj_id;
/*    */   
/*    */   @BeforeClass
/*    */   public static void setUpBeforeClass() {
/* 18 */     obj_id = "7778NEW";
/*    */   }
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testConstructor() {
/* 24 */     System.out.println("-------------  testConstructor start -------------");
/* 25 */     ChwBapiClassCharRead chwBapiClassCharRead = new ChwBapiClassCharRead(obj_id, "EB26", "T", "Q");
/*    */     try {
/* 27 */       chwBapiClassCharRead.execute();
/* 28 */     } catch (Exception exception) {
/*    */       
/* 30 */       System.err.println(exception.getMessage());
/*    */     } 
/*    */     
/* 33 */     String str = chwBapiClassCharRead.createLogEntry();
/* 34 */     Assert.assertEquals(chwBapiClassCharRead.getRfcrc(), 8L);
/* 35 */     System.out.println(str);
/* 36 */     System.out.println("-------------  testConstructor end -------------");
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwBapiClassCharReadTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */