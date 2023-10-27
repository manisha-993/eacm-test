/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import java.security.KeyStore;
/*    */ import javax.net.ssl.KeyManager;
/*    */ import javax.net.ssl.KeyManagerFactory;
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
/*    */ public class EACMKeyManager
/*    */ {
/*    */   private KeyStore ks;
/*    */   private String password;
/*    */   
/*    */   public EACMKeyManager(String paramString1, String paramString2) throws Exception {
/* 28 */     this("JKS", paramString1, paramString2);
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
/*    */   public EACMKeyManager(String paramString1, String paramString2, String paramString3) throws Exception {
/* 40 */     this.password = paramString3;
/* 41 */     this.ks = KeyStoreLoader.loadKeyStore(paramString1, paramString2, paramString3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public KeyManager[] getKeyManagers() throws Exception {
/* 52 */     KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
/* 53 */     keyManagerFactory.init(this.ks, this.password.toCharArray());
/* 54 */     return keyManagerFactory.getKeyManagers();
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\EACMKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */