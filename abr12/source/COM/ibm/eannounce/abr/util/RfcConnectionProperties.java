/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import COM.ibm.opicmpdh.middleware.taskmaster.Log;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RfcConnectionProperties
/*    */   extends Properties
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private static final String PROPERTIES_FILENAME = "rfc.connection.properties";
/* 23 */   private static Properties c_prop = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 31 */     reloadProperties();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final void loadProperties() {
/* 44 */     FileInputStream fileInputStream = null;
/*    */     try {
/* 46 */       if (c_prop == null) {
/* 47 */         c_prop = new Properties();
/*    */         
/* 49 */         fileInputStream = new FileInputStream("rfc.connection.properties");
/*    */ 
/*    */         
/* 52 */         c_prop.load(fileInputStream);
/*    */       } 
/* 54 */     } catch (Exception exception) {
/*    */       
/* 56 */       Log.out("Unable to loadProperties for rfc.connection.properties " + exception);
/*    */     
/*    */     }
/*    */     finally {
/*    */ 
/*    */       
/* 62 */       if (fileInputStream != null) {
/*    */         try {
/* 64 */           fileInputStream.close();
/* 65 */         } catch (IOException iOException) {
/* 66 */           iOException.printStackTrace();
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final void reloadProperties() {
/* 75 */     loadProperties();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final String getServiceURI() {
/* 92 */     reloadProperties();
/*    */     
/* 94 */     return c_prop
/* 95 */       .getProperty("rdh.service.uri", "");
/*    */   }
/*    */   
/*    */   public static final String getVersion() {
/* 99 */     return "$Id: RfcConnectionProperties.java,v 1.0 2022/10/31 18:40:37 Bob Exp $";
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\RfcConnectionProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */