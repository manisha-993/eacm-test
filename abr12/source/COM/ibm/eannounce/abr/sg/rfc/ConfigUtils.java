/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigUtils
/*    */ {
/* 15 */   private static ResourceBundle bundle = null;
/*    */   
/* 17 */   private static String BUNDLENAME = "rdhclientconfig";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static ResourceBundle getResourceBundle() {
/* 26 */     if (bundle == null)
/*    */     {
/* 28 */       bundle = ResourceBundle.getBundle(BUNDLENAME);
/*    */     }
/* 30 */     return bundle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getBundleName() {
/* 40 */     return BUNDLENAME;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setBundleName(String paramString) {
/* 50 */     BUNDLENAME = paramString;
/* 51 */     bundle = null;
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
/*    */   public static String getProperty(String paramString) {
/* 63 */     if (paramString == null)
/* 64 */       return null; 
/* 65 */     String str = null;
/*    */     
/*    */     try {
/* 68 */       str = getResourceBundle().getString(paramString).trim();
/* 69 */     } catch (Throwable throwable) {
/*    */       
/* 71 */       System.out.println("Key '" + paramString + "'not found.");
/*    */     } 
/* 73 */     return str;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void refreshResourceBundle() {
/* 81 */     bundle = null;
/* 82 */     getResourceBundle();
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
/*    */   public static String getValue(String paramString1, String paramString2) {
/* 94 */     String str = getProperty(paramString1);
/* 95 */     if (str == null || str.trim().length() == 0)
/*    */     {
/* 97 */       str = paramString2;
/*    */     }
/* 99 */     return str;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ConfigUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */