/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.Log;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
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
/*     */ public final class RfcConfigProperties
/*     */   extends Properties
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final String PROPERTIES_FILENAME = "rfc.properties";
/*  23 */   private static Properties c_prop = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  31 */     reloadProperties();
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
/*     */   private static final void loadProperties() {
/*  44 */     FileInputStream fileInputStream = null;
/*     */     try {
/*  46 */       if (c_prop == null) {
/*  47 */         c_prop = new Properties();
/*     */         
/*  49 */         fileInputStream = new FileInputStream("rfc.properties");
/*     */ 
/*     */         
/*  52 */         c_prop.load(fileInputStream);
/*     */       } 
/*  54 */     } catch (Exception exception) {
/*     */       
/*  56 */       Log.out("Unable to loadProperties for rfc.properties " + exception);
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/*  62 */       if (fileInputStream != null) {
/*     */         try {
/*  64 */           fileInputStream.close();
/*  65 */         } catch (IOException iOException) {
/*  66 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void reloadProperties() {
/*  75 */     loadProperties();
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
/*     */   public static final String getServiceTruststore() {
/*  92 */     reloadProperties();
/*  93 */     return c_prop
/*  94 */       .getProperty("rdh.service.truststore", "");
/*     */   }
/*     */   public static final String getServiceTruststorePassword() {
/*  97 */     reloadProperties();
/*  98 */     return c_prop
/*  99 */       .getProperty("rdh.service.truststore.password", "");
/*     */   }
/*     */   
/*     */   public static final String getServiceKeystore() {
/* 103 */     reloadProperties();
/* 104 */     return c_prop
/* 105 */       .getProperty("rdh.service.keystore", "");
/*     */   }
/*     */   public static final String getServiceKeystorePassword() {
/* 108 */     reloadProperties();
/* 109 */     return c_prop
/* 110 */       .getProperty("rdh.service.keystore.password", "");
/*     */   }
/*     */   public static final String getServiceKeystoreType() {
/* 113 */     reloadProperties();
/* 114 */     return c_prop
/* 115 */       .getProperty("rdh.keystore.type", "");
/*     */   }
/*     */   public static final String getSSLAlgorithm() {
/* 118 */     reloadProperties();
/* 119 */     return c_prop
/* 120 */       .getProperty("rdh.ssl.algorithm", "");
/*     */   }
/*     */   public static final String getZdmstatus() {
/* 123 */     reloadProperties();
/* 124 */     return c_prop
/* 125 */       .getProperty("rdh.zdmstatus", "");
/*     */   }
/*     */   public static final String getTssZdmstatus() {
/* 128 */     reloadProperties();
/* 129 */     return c_prop
/* 130 */       .getProperty("rdh.Tsszdmstatus", "");
/*     */   }
/*     */   public static String getPropertys(String paramString) {
/* 133 */     reloadProperties();
/* 134 */     return c_prop
/* 135 */       .getProperty(paramString, "");
/*     */   }
/*     */   public static String getCountry(String paramString) {
/* 138 */     reloadProperties();
/* 139 */     return c_prop
/* 140 */       .getProperty("COUNTRY." + paramString, null);
/*     */   }
/*     */   public static final String getMtposMachtype() {
/* 143 */     reloadProperties();
/* 144 */     return c_prop
/* 145 */       .getProperty("MTPOS.MACHTYPE", "");
/*     */   }
/*     */   public static String getZsabrtaxPropertys(String paramString) {
/* 148 */     reloadProperties();
/* 149 */     return c_prop
/* 150 */       .getProperty("ZSABRTAX." + paramString, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String getVersion() {
/* 157 */     return "$Id: TaskMasterProperties.java,v 1.29 2013/09/11 18:40:37 wendy Exp $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\RfcConfigProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */