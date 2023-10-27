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
/*    */ public class ChwDepdMaintainTest
/*    */ {
/* 13 */   private static String obj_id = null;
/*    */   
/*    */   @BeforeClass
/*    */   public static void setUpBeforeClass() {
/* 17 */     obj_id = "5689R1N";
/*    */   }
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testCreateDepdWithoutDep_Extern() {
/* 23 */     ChwDepdMaintain chwDepdMaintain = new ChwDepdMaintain(obj_id, null, null, null);
/*    */     try {
/* 25 */       chwDepdMaintain.execute();
/* 26 */     } catch (Exception exception) {
/*    */       
/* 28 */       System.err.println(exception.getMessage());
/*    */     } 
/* 30 */     String str = chwDepdMaintain.createLogEntry();
/* 31 */     Assert.assertTrue((str.indexOf("RdhDepd_dep_ident.dep_extern") > 0));
/* 32 */     Assert.assertEquals(chwDepdMaintain.getRfcrc(), 8L);
/* 33 */     System.out.println(str);
/*    */   }
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testCreateDepdWithoutDep_type() {
/* 39 */     ChwDepdMaintain chwDepdMaintain = new ChwDepdMaintain(obj_id, "5609Test", null, null);
/*    */     try {
/* 41 */       chwDepdMaintain.execute();
/* 42 */     } catch (Exception exception) {
/*    */       
/* 44 */       System.err.println(exception.getMessage());
/*    */     } 
/* 46 */     String str = chwDepdMaintain.createLogEntry();
/* 47 */     Assert.assertTrue((str.indexOf("RdhDepd_depdat.dep_type") > 0));
/* 48 */     Assert.assertEquals(chwDepdMaintain.getRfcrc(), 8L);
/* 49 */     System.out.println(str);
/*    */   }
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testCreateDepdWithoutDescript() {
/* 55 */     ChwDepdMaintain chwDepdMaintain = new ChwDepdMaintain(obj_id, "5609Test", "0147", null);
/*    */     try {
/* 57 */       chwDepdMaintain.execute();
/* 58 */     } catch (Exception exception) {
/*    */       
/* 60 */       System.err.println(exception.getMessage());
/*    */     } 
/* 62 */     String str = chwDepdMaintain.createLogEntry();
/* 63 */     Assert.assertTrue((str.indexOf("RdhDepd_depdescr.descript") > 0));
/* 64 */     Assert.assertEquals(chwDepdMaintain.getRfcrc(), 8L);
/* 65 */     System.out.println(str);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testCreateDepd() {
/* 72 */     ChwDepdMaintain chwDepdMaintain = new ChwDepdMaintain(obj_id, "MK_S017LF1_EE_PSELECTED", "type", "descr");
/*    */     try {
/* 74 */       chwDepdMaintain.execute();
/* 75 */     } catch (Exception exception) {
/*    */       
/* 77 */       System.err.println(exception.getMessage());
/*    */     } 
/* 79 */     String str = chwDepdMaintain.createLogEntry();
/*    */     
/* 81 */     Assert.assertEquals(chwDepdMaintain.getRfcrc(), 0L);
/* 82 */     System.out.println(str);
/*    */   }
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testCreateDepdAddLine() {
/* 88 */     ChwDepdMaintain chwDepdMaintain = new ChwDepdMaintain(obj_id, "MK_S017LF1_EE_PSELECTED", "type", "descr");
/*    */     try {
/* 90 */       chwDepdMaintain.addSourceLineCondition("MK_5799_BB1_2201 = 'S01728S'");
/* 91 */       chwDepdMaintain.execute();
/* 92 */     } catch (Exception exception) {
/*    */       
/* 94 */       System.err.println(exception.getMessage());
/*    */     } 
/* 96 */     String str = chwDepdMaintain.createLogEntry();
/*    */     
/* 98 */     Assert.assertEquals(chwDepdMaintain.getRfcrc(), 8L);
/* 99 */     System.out.println(str);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwDepdMaintainTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */