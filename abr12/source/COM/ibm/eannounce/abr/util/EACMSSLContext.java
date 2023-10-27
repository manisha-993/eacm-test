/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import javax.net.ssl.KeyManager;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import javax.net.ssl.TrustManager;
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
/*    */ public class EACMSSLContext
/*    */ {
/*    */   private String algorithm;
/*    */   private EACMKeyManager keyManager;
/*    */   private EACMTrustManager trustManager;
/*    */   
/*    */   public EACMSSLContext(String paramString, EACMKeyManager paramEACMKeyManager, EACMTrustManager paramEACMTrustManager) {
/* 28 */     this.algorithm = paramString;
/* 29 */     this.keyManager = paramEACMKeyManager;
/* 30 */     this.trustManager = paramEACMTrustManager;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SSLContext getSSLContext() throws Exception {
/* 41 */     SSLContext sSLContext = SSLContext.getInstance(this.algorithm);
/* 42 */     sSLContext.init(getKeyManagers(), getTrustManagers(), null);
/* 43 */     return sSLContext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private KeyManager[] getKeyManagers() throws Exception {
/* 52 */     if (this.keyManager == null)
/*    */     {
/* 54 */       return null;
/*    */     }
/* 56 */     return this.keyManager.getKeyManagers();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private TrustManager[] getTrustManagers() throws Exception {
/* 65 */     if (this.trustManager == null)
/*    */     {
/* 67 */       return null;
/*    */     }
/* 69 */     return this.trustManager.getTrustManagers();
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\EACMSSLContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */