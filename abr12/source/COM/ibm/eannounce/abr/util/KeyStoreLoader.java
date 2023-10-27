/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import java.io.FileInputStream;
/*    */ import java.security.KeyStore;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyStoreLoader
/*    */ {
/*    */   public static KeyStore loadKeyStore(String paramString1, String paramString2, String paramString3) throws Exception {
/* 26 */     if (paramString1 == null || paramString1.trim().isEmpty())
/*    */     {
/* 28 */       paramString1 = "JKS";
/*    */     }
/*    */     
/* 31 */     FileInputStream fileInputStream = null;
/*    */     
/*    */     try {
/* 34 */       KeyStore keyStore = KeyStore.getInstance(paramString1);
/* 35 */       fileInputStream = new FileInputStream(paramString2);
/* 36 */       keyStore.load(fileInputStream, paramString3.toCharArray());
/* 37 */       return keyStore;
/*    */     } finally {
/*    */       
/* 40 */       if (fileInputStream != null)
/*    */       {
/* 42 */         fileInputStream.close();
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\KeyStoreLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */