/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import org.junit.Assert;
/*     */ import org.junit.BeforeClass;
/*     */ import org.junit.Test;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChwClassMaintainTest
/*     */ {
/*  19 */   private static String obj_id = null;
/*     */ 
/*     */   
/*     */   @BeforeClass
/*     */   public static void setUpBeforeClass() {
/*  24 */     obj_id = "ST00001Y";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testExecuteNullCharAdded() {
/*  34 */     System.out.println("------------- Test ExecuteNullCharAdded start -------------");
/*  35 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", "Class for SWO 5655DB2");
/*     */ 
/*     */     
/*     */     try {
/*  39 */       chwClassMaintain.addCharacteristic((String)null);
/*  40 */       chwClassMaintain.execute();
/*  41 */     } catch (Exception exception) {
/*     */       
/*  43 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/*  46 */     String str = chwClassMaintain.createLogEntry();
/*  47 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 8L);
/*  48 */     System.out.println(str.substring(str.indexOf("Result")));
/*  49 */     System.out.println("------------- Test ExecuteNullCharAdded end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testExecuteEmptyCharAdded() {
/*  55 */     System.out.println("------------- Test ExecuteEmptyCharAdded start -------------");
/*  56 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", "Class for SWO 5655DB2");
/*     */ 
/*     */     
/*     */     try {
/*  60 */       chwClassMaintain.addCharacteristic("");
/*  61 */       chwClassMaintain.execute();
/*  62 */     } catch (Exception exception) {
/*     */       
/*  64 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/*  67 */     String str = chwClassMaintain.createLogEntry();
/*  68 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 8L);
/*  69 */     System.out.println(str.substring(str.indexOf("Result")));
/*  70 */     System.out.println("------------- Test ExecuteEmptyCharAdded end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testExecuteNoCharAdded() {
/*  76 */     System.out.println("------------- Test ExecuteNoCharAdded start -------------");
/*  77 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", "Class for SWO 5655DB2");
/*     */ 
/*     */     
/*     */     try {
/*  81 */       chwClassMaintain.execute();
/*  82 */     } catch (Exception exception) {
/*     */       
/*  84 */       exception.printStackTrace();
/*     */     } 
/*     */     
/*  87 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 0L);
/*  88 */     String str = chwClassMaintain.createLogEntry();
/*  89 */     System.out.println(str);
/*  90 */     System.out.println(str.substring(str.indexOf("CLA_CH_ATR"), str.indexOf("PIMS_IDENTITY") - 3));
/*  91 */     System.out.println("------------- Test ExecuteNoCharAdded end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testExecuteOneCharAdded() {
/*  97 */     System.out.println("------------- Test ExecuteOneCharAdded start -------------");
/*  98 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", "Class for SWO 5655DB2");
/*     */ 
/*     */     
/*     */     try {
/* 102 */       chwClassMaintain.addCharacteristic("TEST1");
/* 103 */       chwClassMaintain.execute();
/* 104 */     } catch (Exception exception) {
/*     */       
/* 106 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 109 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 0L);
/* 110 */     String str = chwClassMaintain.createLogEntry();
/* 111 */     System.out.println(str.substring(str.indexOf("CLA_CH_ATR"), str.indexOf("PIMS_IDENTITY") - 3));
/* 112 */     System.out.println("------------- Test ExecuteOneCharAdded end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testExecuteMoreThanOneCharsAdded() {
/* 118 */     System.out.println("------------- Test ExecuteMoreThanOneCharsAdded start -------------");
/* 119 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", "Class for SWO 5655DB2");
/*     */ 
/*     */     
/*     */     try {
/* 123 */       chwClassMaintain.addCharacteristic("TEST1");
/* 124 */       chwClassMaintain.addCharacteristic("TEST2");
/* 125 */       chwClassMaintain.addCharacteristic("TEST3");
/* 126 */       chwClassMaintain.execute();
/* 127 */     } catch (Exception exception) {
/*     */       
/* 129 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 132 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 0L);
/* 133 */     String str = chwClassMaintain.createLogEntry();
/* 134 */     System.out.println(str.substring(str.indexOf("CLA_CH_ATR"), str.indexOf("PIMS_IDENTITY") - 3));
/* 135 */     System.out.println("------------- Test ExecuteMoreThanOneCharsAdded end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testInvalidServiceNameSet() {
/* 141 */     System.out.println("------------- Test InvalidServiceNameSet start -------------");
/* 142 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "INVALID_SERVICE_NAME", "Class for SWO 5655DB2");
/*     */ 
/*     */     
/*     */     try {
/* 146 */       chwClassMaintain.execute();
/* 147 */     } catch (Exception exception) {
/*     */       
/* 149 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/* 152 */     String str = chwClassMaintain.createLogEntry();
/* 153 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 8L);
/* 154 */     System.out.println(str.substring(str.indexOf("Result")));
/* 155 */     System.out.println("------------- Test InvalidServiceNameSet end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testNullServiceNameSet() {
/* 161 */     System.out.println("------------- Test NullServiceNameSet start -------------");
/* 162 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, null, "Class for SWO 5655DB2");
/*     */     
/*     */     try {
/* 165 */       chwClassMaintain.execute();
/* 166 */     } catch (Exception exception) {
/*     */       
/* 168 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/* 171 */     String str = chwClassMaintain.createLogEntry();
/* 172 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 8L);
/* 173 */     System.out.println(str.substring(str.indexOf("Result")));
/* 174 */     System.out.println("------------- Test NullServiceNameSet end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testEmptyServiceNameSet() {
/* 180 */     System.out.println("------------- Test EmptyServiceNameSet start -------------");
/* 181 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "", "Class for SWO 5655DB2");
/*     */     
/*     */     try {
/* 184 */       chwClassMaintain.execute();
/* 185 */     } catch (Exception exception) {
/*     */       
/* 187 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/* 190 */     String str = chwClassMaintain.createLogEntry();
/* 191 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 8L);
/* 192 */     System.out.println(str.substring(str.indexOf("Result")));
/* 193 */     System.out.println("------------- Test EmptyServiceNameSet end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testNullClassDescIsSet() {
/* 199 */     System.out.println("------------- Test NullClassDescIsSet start -------------");
/* 200 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", null);
/*     */     
/*     */     try {
/* 203 */       chwClassMaintain.execute();
/* 204 */     } catch (Exception exception) {
/*     */       
/* 206 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/* 209 */     String str = chwClassMaintain.createLogEntry();
/* 210 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 8L);
/* 211 */     System.out.println(str.substring(str.indexOf("Result")));
/* 212 */     System.out.println("------------- Test NullClassDescIsSet end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testEmptyClassDescIsSet() {
/* 218 */     System.out.println("------------- Test EmptyClassDescIsSet start -------------");
/* 219 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", "");
/*     */     
/*     */     try {
/* 222 */       chwClassMaintain.execute();
/* 223 */     } catch (Exception exception) {
/*     */       
/* 225 */       System.err.println(exception.getMessage());
/*     */     } 
/*     */     
/* 228 */     String str = chwClassMaintain.createLogEntry();
/* 229 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 8L);
/* 230 */     System.out.println(str.substring(str.indexOf("Result")));
/* 231 */     System.out.println("------------- Test EmptyClassDescIsSet end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testAllFieldsGenerated() {
/* 237 */     System.out.println("------------- Test AllFieldsGenerated start -------------");
/* 238 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", "Class for SWO 5655DB2");
/*     */ 
/*     */     
/*     */     try {
/* 242 */       chwClassMaintain.addCharacteristic("TEST");
/* 243 */       chwClassMaintain.execute();
/* 244 */     } catch (Exception exception) {
/*     */       
/* 246 */       exception.printStackTrace();
/*     */     } 
/* 248 */     String[] arrayOfString1 = { "PIMS_IDENTITY", "RFA_NUM", "DEFAULT_MANDT", "CLIENT_NAME", "N45BZDM_GEO_TO_CLASS", "Z_GEO", "CLCLASSES", "CLASS", "CLASS_TYPE", "STATUS", "VAL_FROM", "VAL_TO", "CHECK_NO", "ORG_AREA", "CLA_DESCR", "CLASS", "CLASS_TYPE", "LANGUAGE", "CATCHWORD", "CLA_CH_ATR", "CLASS", "CLASS_TYPE", "CHARACT" };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     String str1 = chwClassMaintain.createLogEntry();
/* 254 */     String str2 = str1.substring(str1.indexOf("{"), str1.lastIndexOf("}"));
/* 255 */     for (String str : arrayOfString1)
/*     */     {
/* 257 */       Assert.assertTrue(str2.contains(str));
/*     */     }
/* 259 */     String[] arrayOfString2 = str2.split("\":");
/* 260 */     Assert.assertEquals(arrayOfString1.length, (arrayOfString2.length - 1));
/* 261 */     System.out.println("------------- Test AllFieldsGenerated end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testExecute() {
/* 267 */     System.out.println("------------- Test Execute start -------------");
/* 268 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, "MK_5655DB2_001", "Class for SWO 5655DB2");
/*     */ 
/*     */     
/*     */     try {
/* 272 */       chwClassMaintain.execute();
/* 273 */     } catch (Exception exception) {
/*     */       
/* 275 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 278 */     Assert.assertEquals(chwClassMaintain.getRfcrc(), 0L);
/*     */ 
/*     */ 
/*     */     
/* 282 */     String str = chwClassMaintain.createLogEntry();
/* 283 */     Assert.assertFalse(str.replace(", ", " ").contains("  "));
/* 284 */     Assert.assertFalse(str.replace(", ", "").contains(","));
/* 285 */     System.out.println("------------- Test Execute end -------------");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwClassMaintainTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */