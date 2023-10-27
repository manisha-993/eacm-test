/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import java.security.KeyStore;
/*    */ import javax.net.ssl.TrustManager;
/*    */ import javax.net.ssl.TrustManagerFactory;
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
/*    */ public class EACMTrustManager
/*    */ {
/*    */   private KeyStore ks;
/*    */   
/*    */   public EACMTrustManager(String paramString1, String paramString2) throws Exception {
/* 27 */     this("JKS", paramString1, paramString2);
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
/*    */   public EACMTrustManager(String paramString1, String paramString2, String paramString3) throws Exception {
/* 39 */     this.ks = KeyStoreLoader.loadKeyStore(paramString1, paramString2, paramString3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TrustManager[] getTrustManagers() throws Exception {
/* 50 */     TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/* 51 */     trustManagerFactory.init(this.ks);
/* 52 */     return trustManagerFactory.getTrustManagers();
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\EACMTrustManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */