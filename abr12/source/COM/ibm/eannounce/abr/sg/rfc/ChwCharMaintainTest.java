/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import org.junit.Assert;
/*     */ import org.junit.BeforeClass;
/*     */ import org.junit.Test;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChwCharMaintainTest
/*     */ {
/*  12 */   private static String obj_id = null;
/*     */ 
/*     */   
/*     */   @BeforeClass
/*     */   public static void setUpBeforeClass() {
/*  17 */     obj_id = "123456";
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testSuccess() {
/*  23 */     System.out.println("------------- Test CreateCharsWithoutCharDescr start -------------");
/*  24 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id, "MK_5799_BB1_2201", "CHAR", 4, "0", "X", "X", "CHO", "s", "X", "X", "X", "D");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  30 */       chwCharMaintain.execute();
/*  31 */     } catch (Exception exception) {
/*     */       
/*  33 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/*  36 */     String str = chwCharMaintain.createLogEntry();
/*  37 */     Assert.assertEquals(chwCharMaintain.getRfcrc(), 8L);
/*  38 */     System.out.println(str);
/*  39 */     System.out.println("------------- Test CreateCharsWithoutCharDescr end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testCreateCharsWithoutCharDescr() {
/*  45 */     System.out.println("------------- Test CreateCharsWithoutCharDescr start -------------");
/*  46 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id, "Test", "CHAR", 2, null, null, null, null, null, null, null, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  52 */       chwCharMaintain.execute();
/*  53 */     } catch (Exception exception) {
/*     */       
/*  55 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/*  58 */     String str = chwCharMaintain.createLogEntry();
/*  59 */     Assert.assertEquals(chwCharMaintain.getRfcrc(), 8L);
/*  60 */     System.out.println(str);
/*  61 */     System.out.println("------------- Test CreateCharsWithoutCharDescr end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testCreateCharsWithCharDescr() {
/*  67 */     System.out.println("------------- Test CreateCharsWithCharDescr start -------------");
/*  68 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id, "Test", "CHAR", 2, null, null, null, null, null, null, null, null, "TestDescr");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     String str = chwCharMaintain.createLogEntry();
/*  78 */     Assert.assertEquals(chwCharMaintain.getRfcrc(), 0L);
/*  79 */     System.out.println(str);
/*  80 */     System.out.println("------------- Test CreateCharsWithCharDescr end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testAddChars() {
/*  86 */     System.out.println("------------- Test AddChars start -------------");
/*  87 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id, "Test", "TestVal", "TestDescr");
/*     */     try {
/*  89 */       chwCharMaintain.execute();
/*  90 */     } catch (Exception exception) {
/*     */       
/*  92 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/*  95 */     String str = chwCharMaintain.createLogEntry();
/*  96 */     Assert.assertEquals(chwCharMaintain.getRfcrc(), 8L);
/*  97 */     System.out.println(str);
/*  98 */     System.out.println("------------- Test AddChars end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testAddValue() {
/* 104 */     System.out.println("------------- Test addValue start -------------");
/* 105 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id, "Test", "TestVal", "TestDescr");
/*     */     try {
/* 107 */       chwCharMaintain.addValue("TestVal2", "TestDescr2");
/*     */     }
/* 109 */     catch (Exception exception) {
/*     */       
/* 111 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/* 114 */     String str = chwCharMaintain.createLogEntry();
/* 115 */     Assert.assertTrue((str.indexOf("TestVal2") > 0));
/* 116 */     Assert.assertTrue((str.indexOf("TestVal") > 0));
/* 117 */     Assert.assertTrue((str.indexOf("TestDescr") > 0));
/* 118 */     Assert.assertTrue((str.indexOf("TestDescr2") > 0));
/*     */     
/* 120 */     System.out.println(str);
/* 121 */     System.out.println("------------- Test addValue end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testIsReadyToExecuteWithoutCharact() {
/* 127 */     System.out.println("------------- Test IsReadyToExecuteWithoutCharact start -------------");
/* 128 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id, "", "CHAR", 2, null, null, null, null, null, null, null, null, null);
/*     */     
/*     */     try {
/* 131 */       chwCharMaintain.execute();
/* 132 */     } catch (Exception exception) {
/*     */       
/* 134 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/* 137 */     String str = chwCharMaintain.createLogEntry();
/* 138 */     Assert.assertEquals(chwCharMaintain.getRfcrc(), 8L);
/* 139 */     Assert.assertTrue((str.indexOf("characts.CHARACT") > 0));
/* 140 */     System.out.println(str);
/* 141 */     System.out.println("------------- Test IsReadyToExecuteWithoutCharact end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testIsReadyToExecuteWithoutDataType() {
/* 147 */     System.out.println("------------- Test IsReadyToExecuteWithoutDataType start -------------");
/* 148 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id, "Test", "", 2, null, null, null, null, null, null, null, null, null);
/*     */     
/*     */     try {
/* 151 */       chwCharMaintain.execute();
/* 152 */     } catch (Exception exception) {
/*     */       
/* 154 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/* 157 */     String str = chwCharMaintain.createLogEntry();
/* 158 */     Assert.assertEquals(chwCharMaintain.getRfcrc(), 8L);
/* 159 */     Assert.assertTrue((str.indexOf("characts.DATATYPE") > 0));
/* 160 */     System.out.println(str);
/* 161 */     System.out.println("------------- Test IsReadyToExecuteWithoutDataType end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testIsReadyToExecuteWithoutValue() {
/* 167 */     System.out.println("------------- Test IsReadyToExecuteWithoutValue start -------------");
/* 168 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id, "Test", "", "TestDescr");
/*     */     try {
/* 170 */       chwCharMaintain.execute();
/* 171 */     } catch (Exception exception) {
/*     */       
/* 173 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/* 176 */     String str = chwCharMaintain.createLogEntry();
/* 177 */     Assert.assertEquals(chwCharMaintain.getRfcrc(), 8L);
/* 178 */     Assert.assertTrue((str.indexOf("char_vals[1].VALUE") > 0));
/* 179 */     System.out.println(str);
/* 180 */     System.out.println("------------- Test IsReadyToExecuteWithoutValue end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testIsReadyToExecuteWithoutValDescr() {
/* 186 */     System.out.println("------------- Test IsReadyToExecuteWithoutValDescr start -------------");
/* 187 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id, "Test", "TestVal", "");
/*     */     try {
/* 189 */       chwCharMaintain.execute();
/* 190 */     } catch (Exception exception) {
/*     */       
/* 192 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/* 195 */     String str = chwCharMaintain.createLogEntry();
/* 196 */     Assert.assertEquals(chwCharMaintain.getRfcrc(), 8L);
/* 197 */     Assert.assertTrue((str.indexOf("char_vals[1].CHARACT") > 0));
/* 198 */     System.out.println(str);
/* 199 */     System.out.println("------------- Test IsReadyToExecuteWithoutValDescr end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testSetRefreshVals() {
/* 205 */     System.out.println("------------- Test setRefreshVals start -------------");
/* 206 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(obj_id, "Test", "TestVal", "");
/*     */ 
/*     */     
/*     */     try {
/* 210 */       String str = chwCharMaintain.createLogEntry();
/*     */ 
/*     */       
/* 213 */       System.out.println(str);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 218 */     catch (Exception exception) {
/*     */       
/* 220 */       System.err.println(exception.getMessage());
/*     */     } 
/* 222 */     System.out.println("------------- Test setRefreshVals end -------------");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwCharMaintainTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */