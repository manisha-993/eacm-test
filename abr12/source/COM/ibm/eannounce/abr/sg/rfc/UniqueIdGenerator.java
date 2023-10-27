/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class UniqueIdGenerator
/*     */ {
/*  17 */   private char[] currentIdChars = null;
/*     */   private static final int LENGTH = 6;
/*     */   private static final String RFC_UNIQUE_FILE_PATH = "rfcabr_filepath";
/*  20 */   private String filePath = null;
/*  21 */   public String fullFilePath = null;
/*  22 */   private static Map<String, UniqueIdGenerator> map = new HashMap<>();
/*  23 */   private static Map<String, String> tempFileMapping = new HashMap<>();
/*  24 */   private BufferedWriter writer = null;
/*     */   
/*     */   public static final String TYPE_MODEL = "RFCMODELABR";
/*     */   
/*     */   public static final String TYPE_AUOM = "RFCAUOMTRLABR";
/*     */   public static final String TYPE_MODELCONVERT = "RFCMODELCONVERTABR";
/*     */   
/*     */   static {
/*  32 */     tempFileMapping.put("RFCPRODSTRUCTABR", "RFCMODELABR");
/*     */   }
/*     */   public static final String TYPE_PRODSTRUCT = "RFCPRODSTRUCTABR"; public static final String TYPE_SWPRODSTUCT = "RFCSWPRODSTRUCTABR"; public static final String TYPE_TRANSACTION = "RFCFCTRANSACTIONABR";
/*     */   private UniqueIdGenerator(String paramString) {
/*  36 */     this.filePath = ABRServerProperties.getValue("rfcabr_filepath", "./");
/*  37 */     this.fullFilePath = this.filePath + paramString + ".temp";
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized UniqueIdGenerator getUniqueIdGenerator(String paramString) {
/*  42 */     String str = (tempFileMapping.get(paramString) == null) ? paramString : tempFileMapping.get(paramString);
/*  43 */     UniqueIdGenerator uniqueIdGenerator = map.get(str);
/*  44 */     if (uniqueIdGenerator == null) {
/*  45 */       uniqueIdGenerator = new UniqueIdGenerator(str);
/*  46 */       map.put(str, uniqueIdGenerator);
/*     */     } 
/*  48 */     return uniqueIdGenerator;
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/*  65 */     StringReader stringReader = new StringReader(null);
/*     */   }
/*     */   
/*     */   public synchronized String getUniqueID(RFCABRSTATUS paramRFCABRSTATUS) {
/*  69 */     if (this.currentIdChars == null) {
/*     */       try {
/*  71 */         File file = new File(this.fullFilePath);
/*  72 */         if (file.exists()) {
/*  73 */           BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fullFilePath));
/*  74 */           String str1 = bufferedReader.readLine();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  81 */           if (str1 != null && str1.length() == 6) {
/*  82 */             this.currentIdChars = str1.toCharArray();
/*     */           }
/*     */         } 
/*  85 */       } catch (Exception exception) {
/*     */         
/*  87 */         exception.printStackTrace();
/*  88 */         paramRFCABRSTATUS.addError(exception.toString());
/*     */       } 
/*     */     }
/*     */     
/*  92 */     if (this.currentIdChars == null) {
/*  93 */       this.currentIdChars = (new String("000000")).toCharArray();
/*     */     } else {
/*  95 */       for (byte b = 5; b >= 0 && 
/*  96 */         increase(b); b--);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 103 */       this.writer = new BufferedWriter(new FileWriter(this.fullFilePath));
/* 104 */       this.writer.write(new String(this.currentIdChars) + "\n");
/* 105 */       this.writer.flush();
/* 106 */       this.writer.close();
/* 107 */     } catch (IOException iOException) {
/*     */       
/* 109 */       iOException.printStackTrace();
/* 110 */       paramRFCABRSTATUS.addError(iOException.toString());
/*     */     } 
/*     */     
/* 113 */     String str = new String(this.currentIdChars);
/* 114 */     paramRFCABRSTATUS.addDebug("UniqueIdGenerator getUniqueID:" + str);
/* 115 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean increase(int paramInt) {
/* 120 */     this.currentIdChars[paramInt] = (char)(this.currentIdChars[paramInt] + 1); char c = this.currentIdChars[paramInt];
/*     */     
/* 122 */     if (c == '9') {
/* 123 */       this.currentIdChars[paramInt] = 'A';
/* 124 */       return false;
/* 125 */     }  if (c == 'Z') {
/* 126 */       this.currentIdChars[paramInt] = '0';
/* 127 */       return true;
/*     */     } 
/* 129 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\UniqueIdGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */