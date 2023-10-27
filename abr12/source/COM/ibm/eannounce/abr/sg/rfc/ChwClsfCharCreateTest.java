/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import org.junit.BeforeClass;
/*     */ import org.junit.Test;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChwClsfCharCreateTest
/*     */ {
/*  12 */   private static String obj_id = null;
/*     */ 
/*     */   
/*     */   @BeforeClass
/*     */   public static void setUpBeforeClass() {
/*  17 */     obj_id = "9080NEW";
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testCreateGroupChar() {
/*  23 */     System.out.println("------------- Test CreateGroupChar start -------------");
/*  24 */     ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
/*     */     
/*     */     try {
/*  27 */       obj_id = "9080NEW";
/*  28 */       String str1 = "T";
/*  29 */       String str2 = "9080";
/*  30 */       String str3 = "1234";
/*  31 */       String str4 = "FC1234";
/*     */     }
/*  33 */     catch (Exception exception) {
/*     */       
/*  35 */       System.err.println("@@@@" + exception.getMessage());
/*     */     } 
/*  37 */     System.out.println("------------- Test CreateGroupChar log Begin -------------");
/*  38 */     System.out.println(chwClsfCharCreate.getRptSb().toString());
/*  39 */     System.out.println("------------- Test CreateGroupChar log end -------------");
/*  40 */     System.out.println("------------- Test CreateGroupChar end -------------");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testCreateQTYChar() {
/*  47 */     System.out.println("------------- Test CreateQTYChar start -------------");
/*  48 */     ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
/*     */     
/*     */     try {
/*  51 */       obj_id = "9080NEW";
/*  52 */       String str1 = "T";
/*  53 */       String str2 = "9080";
/*  54 */       String str3 = "1234";
/*     */     }
/*  56 */     catch (Exception exception) {
/*     */       
/*  58 */       System.err.println("@@@@" + exception.getMessage());
/*     */     } 
/*  60 */     System.out.println("------------- Test CreateQTYChar log Begin -------------");
/*  61 */     System.out.println(chwClsfCharCreate.getRptSb().toString());
/*  62 */     System.out.println("------------- Test CreateQTYChar log end -------------");
/*  63 */     System.out.println("------------- Test CreateQTYChar end -------------");
/*     */   }
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testCreateRPQGroupChar() {
/*  69 */     System.out.println("------------- Test CreateRPQGroupChar start -------------");
/*  70 */     ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
/*     */     
/*     */     try {
/*  73 */       obj_id = "9080NEW";
/*  74 */       String str1 = "T";
/*  75 */       String str2 = "9080";
/*  76 */       String str3 = "1234";
/*  77 */       String str4 = "FC1234";
/*     */     }
/*  79 */     catch (Exception exception) {
/*     */       
/*  81 */       System.err.println("@@@@" + exception.getMessage());
/*     */     } 
/*  83 */     System.out.println("------------- Test CreateRPQGroupChar log Begin -------------");
/*  84 */     System.out.println(chwClsfCharCreate.getRptSb().toString());
/*  85 */     System.out.println("------------- Test CreateRPQGroupChar log end -------------");
/*  86 */     System.out.println("------------- Test CreateRPQGroupChar end -------------");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testCreateRPQQTYChar() {
/*  93 */     System.out.println("------------- Test CreateRPQQTYChar start -------------");
/*  94 */     ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
/*     */     
/*     */     try {
/*  97 */       obj_id = "9080NEW";
/*  98 */       String str1 = "T";
/*  99 */       String str2 = "9080";
/* 100 */       String str3 = "1234";
/*     */     }
/* 102 */     catch (Exception exception) {
/*     */       
/* 104 */       System.err.println("@@@@" + exception.getMessage());
/*     */     } 
/* 106 */     System.out.println("------------- Test CreateRPQQTYChar log Begin -------------");
/* 107 */     System.out.println(chwClsfCharCreate.getRptSb().toString());
/* 108 */     System.out.println("------------- Test CreateRPQQTYChar log end -------------");
/* 109 */     System.out.println("------------- Test CreateRPQQTYChar end -------------");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testCreateAlphaGroupChar() {
/* 116 */     System.out.println("------------- Test CreateAlphaGroupChar start -------------");
/* 117 */     ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
/*     */     
/*     */     try {
/* 120 */       obj_id = "9080NEW";
/* 121 */       String str1 = "T";
/* 122 */       String str2 = "9080";
/* 123 */       String str3 = "1234";
/* 124 */       String str4 = "FC1234";
/*     */     }
/* 126 */     catch (Exception exception) {
/*     */       
/* 128 */       System.err.println("@@@@" + exception.getMessage());
/*     */     } 
/* 130 */     System.out.println("------------- Test CreateAlphaGroupChar log Begin -------------");
/* 131 */     System.out.println(chwClsfCharCreate.getRptSb().toString());
/* 132 */     System.out.println("------------- Test CreateAlphaGroupChar log end -------------");
/* 133 */     System.out.println("------------- Test CreateAlphaGroupChar end -------------");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testCreateAlphaQTYChar() {
/* 140 */     System.out.println("------------- Test  CreateAlphaQTYChar start -------------");
/* 141 */     ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
/*     */     
/*     */     try {
/* 144 */       obj_id = "7890NEW";
/* 145 */       String str1 = "T";
/* 146 */       String str2 = "9080";
/* 147 */       String str3 = "1234";
/*     */     }
/* 149 */     catch (Exception exception) {
/*     */       
/* 151 */       System.err.println("@@@@" + exception.getMessage());
/*     */     } 
/* 153 */     System.out.println("------------- Test CreateAlphaQTYChar log Begin -------------");
/* 154 */     System.out.println(chwClsfCharCreate.getRptSb().toString());
/* 155 */     System.out.println("------------- Test CreateRPQQTYChar log end -------------");
/* 156 */     System.out.println("------------- Test CreateRPQQTYChar end -------------");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwClsfCharCreateTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */