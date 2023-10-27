/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import org.junit.Assert;
/*    */ import org.junit.BeforeClass;
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
/*    */ public class ChwBomCreateTest
/*    */ {
/*    */   @BeforeClass
/*    */   public static void setUpBeforeClass() {}
/*    */   
/*    */   @Test
/*    */   public void testSuccess() throws ClassNotFoundException, SQLException {
/* 23 */     System.out.println("------------- Test ChwBomCreateTest start -------------");
/*    */     
/* 25 */     ChwBomCreate chwBomCreate = new ChwBomCreate("9080NEW", "1222");
/*    */     
/*    */     try {
/* 28 */       chwBomCreate.execute();
/* 29 */     } catch (Exception exception) {
/*    */       
/* 31 */       System.err.println(exception.getMessage());
/*    */     } 
/*    */     
/* 34 */     String str = chwBomCreate.createLogEntry();
/* 35 */     System.out.println(str);
/* 36 */     Assert.assertEquals(chwBomCreate.getRfcrc(), 0L);
/* 37 */     System.out.println("------------- Test ChwBomCreateTest end -------------");
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwBomCreateTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */