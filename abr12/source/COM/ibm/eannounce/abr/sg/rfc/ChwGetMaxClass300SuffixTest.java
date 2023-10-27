/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import org.junit.Assert;
/*    */ import org.junit.BeforeClass;
/*    */ import org.junit.Test;
/*    */ 
/*    */ 
/*    */ public class ChwGetMaxClass300SuffixTest
/*    */ {
/* 10 */   private static String obj_id = null;
/*    */   
/*    */   @BeforeClass
/*    */   public static void setUpBeforeClass() {
/* 14 */     obj_id = "ST00001Y";
/*    */   }
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testGetMax_suffix() throws Exception {
/* 20 */     ChwGetMaxClass300Suffix chwGetMaxClass300Suffix = new ChwGetMaxClass300Suffix(obj_id, "MK_T_7778_ALPH_");
/*    */     try {
/* 22 */       chwGetMaxClass300Suffix.execute();
/* 23 */     } catch (Exception exception) {
/*    */       
/* 25 */       System.err.println(exception.getMessage());
/*    */     } 
/* 27 */     Assert.assertTrue((chwGetMaxClass300Suffix.getMax_suffix() >= 0));
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwGetMaxClass300SuffixTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */