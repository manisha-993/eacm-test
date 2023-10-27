/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import com.ibm.eacm.AES256Utils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLSocketFactory;
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
/*     */ public class EACMWebServiceUtil
/*     */ {
/*  28 */   private static SSLSocketFactory sslSocketFactory = null;
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
/*     */   public static String callService(String paramString1, String paramString2) throws Exception {
/*  41 */     HttpURLConnection httpURLConnection = null;
/*  42 */     OutputStream outputStream = null;
/*  43 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/*  45 */     String str1 = RfcConnectionProperties.getServiceURI();
/*  46 */     String str2 = RfcConfigProperties.getServiceTruststore();
/*  47 */     String str3 = AES256Utils.decrypt(RfcConfigProperties.getServiceTruststorePassword());
/*  48 */     String str4 = RfcConfigProperties.getServiceKeystore();
/*  49 */     String str5 = AES256Utils.decrypt(RfcConfigProperties.getServiceKeystorePassword());
/*     */     
/*  51 */     String str6 = RfcConfigProperties.getServiceKeystoreType();
/*  52 */     String str7 = RfcConfigProperties.getSSLAlgorithm();
/*     */     
/*  54 */     D.ebug("service.uri = " + str1);
/*     */     
/*  56 */     D.ebug("service.truststore = " + str2);
/*  57 */     D.ebug("service.keystore = " + str4);
/*     */ 
/*     */     
/*  60 */     if (sslSocketFactory == null) {
/*     */       
/*  62 */       EACMKeyManager eACMKeyManager = null;
/*  63 */       EACMTrustManager eACMTrustManager = null;
/*  64 */       EACMSSLContext eACMSSLContext = null;
/*     */       
/*     */       try {
/*  67 */         eACMKeyManager = new EACMKeyManager(str6.toUpperCase(), str4, str5);
/*  68 */         eACMTrustManager = new EACMTrustManager(str6.toUpperCase(), str2, str3);
/*  69 */         eACMSSLContext = new EACMSSLContext(str7.toUpperCase(), eACMKeyManager, eACMTrustManager);
/*  70 */         sslSocketFactory = eACMSSLContext.getSSLContext().getSocketFactory();
/*  71 */       } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */         
/*  75 */         throw exception;
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     long l = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  84 */       URL uRL = new URL(str1 + paramString2);
/*  85 */       httpURLConnection = (HttpURLConnection)uRL.openConnection();
/*     */       
/*  87 */       if (httpURLConnection instanceof HttpsURLConnection)
/*     */       {
/*  89 */         ((HttpsURLConnection)httpURLConnection).setSSLSocketFactory(sslSocketFactory);
/*     */       }
/*     */       
/*  92 */       httpURLConnection.setDoOutput(true);
/*  93 */       httpURLConnection.setRequestMethod("POST");
/*  94 */       httpURLConnection.setRequestProperty("Content-Type", "application/json");
/*     */       
/*  96 */       outputStream = httpURLConnection.getOutputStream();
/*  97 */       outputStream.write(paramString1.getBytes());
/*  98 */       outputStream.flush();
/*     */       
/* 100 */       if (httpURLConnection.getResponseCode() != 200) {
/*     */         
/* 102 */         D.ebug(0, "Invoke WebService " + paramString2 + " Failed: HTTP error code = " + httpURLConnection
/* 103 */             .getResponseCode() + " Http response message = " + httpURLConnection
/* 104 */             .getResponseMessage());
/* 105 */         throw new RuntimeException("Invoke WebService " + paramString2 + " Failed: HTTP error code = " + httpURLConnection
/* 106 */             .getResponseCode() + " Http response message = " + httpURLConnection
/* 107 */             .getResponseMessage());
/*     */       } 
/*     */       
/* 110 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
/*     */       
/*     */       String str;
/* 113 */       while ((str = bufferedReader.readLine()) != null)
/*     */       {
/* 115 */         stringBuffer.append(str);
/*     */       }
/*     */       
/* 118 */       D.ebug("WebService: " + paramString2 + " Input data: " + paramString1 + " Output data: " + stringBuffer
/* 119 */           .toString());
/* 120 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 122 */       D.ebug(0, "Failed to load certification information: " + illegalArgumentException.getStackTrace());
/* 123 */       throw illegalArgumentException;
/*     */     } finally {
/*     */       
/* 126 */       if (outputStream != null)
/*     */       {
/* 128 */         outputStream.close();
/*     */       }
/* 130 */       if (httpURLConnection != null)
/*     */       {
/* 132 */         httpURLConnection.disconnect();
/*     */       }
/*     */     } 
/* 135 */     D.ebug(paramString2 + " return : " + stringBuffer.toString());
/*     */     
/* 137 */     D.ebug(paramString2 + " take time: " + (System.currentTimeMillis() - l));
/*     */     
/* 139 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\EACMWebServiceUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */