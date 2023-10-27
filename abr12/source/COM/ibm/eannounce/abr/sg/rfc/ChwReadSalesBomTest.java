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
/*    */ public class ChwReadSalesBomTest
/*    */ {
/*    */   @BeforeClass
/*    */   public static void setUpBeforeClass() {}
/*    */   
/*    */   @Test
/*    */   public void testSuccess() throws ClassNotFoundException, SQLException {
/* 23 */     System.out.println("------------- Test ChwReadSalesBom start -------------");
/*    */     
/* 25 */     ChwReadSalesBom chwReadSalesBom = new ChwReadSalesBom("0001NEW", "1222");
/*    */     
/*    */     try {
/* 28 */       chwReadSalesBom.execute();
/* 29 */     } catch (Exception exception) {
/*    */       
/* 31 */       System.err.println(exception.getMessage());
/*    */     } 
/*    */     
/* 34 */     System.out.println("111");
/* 35 */     String str = chwReadSalesBom.createLogEntry();
/* 36 */     chwReadSalesBom.getError_text();
/* 37 */     System.out.println("222");
/* 38 */     System.out.println(str);
/* 39 */     System.out.println("333");
/* 40 */     System.out.println(chwReadSalesBom.getRETURN_MULTIPLE_OBJ());
/* 41 */     System.out.println("444");
/* 42 */     Assert.assertEquals(chwReadSalesBom.getRfcrc(), 0L);
/* 43 */     System.out.println("------------- Test ChwReadSalesBom end -------------");
/*    */   }
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testNotfound() throws ClassNotFoundException, SQLException {
/* 49 */     System.out.println("------------- Test ChwReadSalesBom testNotfound start -------------");
/*    */     
/* 51 */     ChwReadSalesBom chwReadSalesBom = new ChwReadSalesBom("0002NEW", "1222");
/*    */     
/*    */     try {
/* 54 */       chwReadSalesBom.execute();
/* 55 */     } catch (Exception exception) {
/*    */       
/* 57 */       System.err.println(exception.getMessage());
/*    */     } 
/*    */     
/* 60 */     String str = chwReadSalesBom.createLogEntry();
/* 61 */     System.out.println(str);
/* 62 */     Assert.assertEquals(chwReadSalesBom.getRfcrc(), 8L);
/* 63 */     System.out.println("------------- Test ChwReadSalesBom testNotfound end -------------");
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwReadSalesBomTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */