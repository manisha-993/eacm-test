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
/*    */ public class ChwAssignCharToClassTest
/*    */ {
/*    */   private static String obj_id;
/*    */   
/*    */   @BeforeClass
/*    */   public static void setUpBeforeClass() {
/* 18 */     obj_id = "9080NEW";
/*    */   }
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testConstructor() {
/* 24 */     System.out.println("-------------  testConstructor start -------------");
/* 25 */     ChwAssignCharToClass chwAssignCharToClass = new ChwAssignCharToClass(obj_id, "MK_REFERENCE", "TEST1", "US", obj_id);
/*    */     try {
/* 27 */       String str1 = chwAssignCharToClass.createLogEntry();
/* 28 */       Assert.assertTrue((str1.indexOf("\"J_KLART\":\"300\"") > 0));
/* 29 */       Assert.assertTrue((str1.indexOf("\"J_CLASS\":\"MK_REFERENCE\"") > 0));
/* 30 */       chwAssignCharToClass.execute();
/* 31 */     } catch (Exception exception) {
/*    */       
/* 33 */       System.err.println(exception.getMessage());
/*    */     } 
/*    */     
/* 36 */     String str = chwAssignCharToClass.createLogEntry();
/* 37 */     Assert.assertEquals(chwAssignCharToClass.getRfcrc(), 8L);
/* 38 */     System.out.println(str);
/* 39 */     System.out.println("-------------  testConstructor end -------------");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testIsReadyToExecuteWithoutJCLASS() {
/* 46 */     System.out.println("-------------  testIsReadyToExecuteWithoutJCLASS start -------------");
/* 47 */     ChwAssignCharToClass chwAssignCharToClass = new ChwAssignCharToClass(obj_id, "", "CLSNAME", "CLSTYPE", obj_id);
/*    */     try {
/* 49 */       chwAssignCharToClass.execute();
/* 50 */     } catch (Exception exception) {
/*    */       
/* 52 */       System.err.println(exception.getMessage());
/*    */     } 
/*    */     
/* 55 */     String str = chwAssignCharToClass.createLogEntry();
/* 56 */     Assert.assertEquals(chwAssignCharToClass.getRfcrc(), 8L);
/* 57 */     Assert.assertTrue((str.indexOf("ChwAssignCharToClass.j_class") > 0));
/* 58 */     System.out.println(str);
/* 59 */     System.out.println("-------------  testIsReadyToExecuteWithoutJCLASS end -------------");
/*    */   }
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testIsReadyToExecuteWithoutMerkma() {
/* 65 */     System.out.println("-------------  testIsReadyToExecuteWithoutMerkma start -------------");
/* 66 */     ChwAssignCharToClass chwAssignCharToClass = new ChwAssignCharToClass(obj_id, "Test", "", "XXX", obj_id);
/*    */     
/*    */     try {
/* 69 */       chwAssignCharToClass.execute();
/* 70 */     } catch (Exception exception) {
/*    */       
/* 72 */       System.err.println(exception.getMessage());
/*    */     } 
/*    */     
/* 75 */     String str = chwAssignCharToClass.createLogEntry();
/* 76 */     Assert.assertEquals(chwAssignCharToClass.getRfcrc(), 8L);
/* 77 */     Assert.assertTrue((str.indexOf("RdhClaa_rmclm.merkma") > 0));
/* 78 */     System.out.println(str);
/* 79 */     System.out.println("-------------  testIsReadyToExecuteWithoutMerkma end -------------");
/*    */   }
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testIsReadyToExecuteWithoutJCLASS2() {
/* 85 */     System.out.println("-------------  testIsReadyToExecuteWithoutJCLASS start -------------");
/* 86 */     ChwAssignCharToClass chwAssignCharToClass = new ChwAssignCharToClass(obj_id, "", "CLSNAME", "CLSTYPE", obj_id);
/*    */     try {
/* 88 */       chwAssignCharToClass.execute();
/* 89 */     } catch (Exception exception) {
/*    */       
/* 91 */       System.err.println(exception.getMessage());
/*    */     } 
/*    */     
/* 94 */     String str = chwAssignCharToClass.createLogEntry();
/* 95 */     Assert.assertEquals(chwAssignCharToClass.getRfcrc(), 8L);
/* 96 */     Assert.assertTrue((str.indexOf("ChwAssignCharToClass.j_class") > 0));
/* 97 */     System.out.println(str);
/* 98 */     System.out.println("-------------  testIsReadyToExecuteWithoutJCLASS end -------------");
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwAssignCharToClassTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */