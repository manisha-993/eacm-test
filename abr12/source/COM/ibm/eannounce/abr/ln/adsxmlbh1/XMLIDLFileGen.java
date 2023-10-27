/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import com.ibm.eacm.AES256Utils;
/*     */ import com.ibm.mq.MQEnvironment;
/*     */ import com.ibm.mq.MQException;
/*     */ import com.ibm.mq.MQGetMessageOptions;
/*     */ import com.ibm.mq.MQMessage;
/*     */ import com.ibm.mq.MQQueue;
/*     */ import com.ibm.mq.MQQueueManager;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Hashtable;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
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
/*     */ public class XMLIDLFileGen
/*     */ {
/*  95 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  96 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   private ResourceBundle rsbd;
/*  98 */   private String reportfile = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLIDLFileGen(ResourceBundle paramResourceBundle) {
/* 105 */     this.rsbd = paramResourceBundle;
/*     */   }
/*     */   
/*     */   private void setReportFile(String paramString) {
/* 109 */     this.reportfile = paramString;
/*     */   }
/*     */   
/*     */   private void log(String paramString) {
/* 113 */     saveDataToFile(this.reportfile, paramString, true);
/*     */   }
/*     */   
/*     */   private String getValfromProperties(String paramString) {
/*     */     try {
/* 118 */       return this.rsbd.getString(paramString);
/* 119 */     } catch (MissingResourceException missingResourceException) {
/* 120 */       log(paramString + " is not existed in properties file. ");
/*     */       
/* 122 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getMsgToFile() {
/* 127 */     String str1 = "IDL_";
/* 128 */     String str2 = "./report/";
/*     */     try {
/* 130 */       str1 = this.rsbd.getString("FILE_PREFIX");
/* 131 */       str2 = this.rsbd.getString("FILE_PATH");
/* 132 */     } catch (Exception exception) {}
/* 133 */     if (!(new File(str2)).exists()) {
/* 134 */       (new File(str2)).mkdir();
/*     */     }
/* 136 */     String str3 = getCurrentTimestamp();
/* 137 */     String str4 = str2 + str1.trim() + "." + str3 + ".REPORT";
/* 138 */     setReportFile(str4);
/*     */ 
/*     */ 
/*     */     
/* 142 */     String str5 = getValfromProperties("MQUSERID");
/* 143 */     String str6 = getValfromProperties("MQPASSWORD");
/* 144 */     String str7 = getValfromProperties("MQPORT");
/* 145 */     String str8 = getValfromProperties("MQHOSTNAME");
/* 146 */     String str9 = getValfromProperties("MQCHANNEL");
/* 147 */     String str10 = getValfromProperties("MQMANAGER");
/* 148 */     String str11 = getValfromProperties("MQQUEUE");
/* 149 */     String str12 = getValfromProperties("MQSSL");
/* 150 */     String str13 = getValfromProperties("KSTORE");
/* 151 */     String str14 = getValfromProperties("KSPASSWORD");
/* 152 */     String str15 = getValfromProperties("TSTORE");
/* 153 */     String str16 = getValfromProperties("TSPASSWORD");
/*     */ 
/*     */     
/* 156 */     String str17 = getValfromProperties("MSGMAX_IN_FILE");
/* 157 */     if (str17 == null || str17.trim().length() == 0) {
/* 158 */       str17 = "0";
/*     */     }
/* 160 */     int i = Integer.parseInt(str17);
/*     */     
/* 162 */     if (str5 != null && str5.length() > 0) {
/* 163 */       MQEnvironment.userID = str5;
/*     */     }
/* 165 */     if (str6 != null && str6.length() > 0) {
/* 166 */       MQEnvironment.password = AES256Utils.decrypt(str6);
/*     */     }
/* 168 */     if (str7 != null && str7.length() > 0) {
/* 169 */       MQEnvironment.port = Integer.parseInt(str7);
/*     */     }
/* 171 */     if (str12 != null && str12.length() > 0) {
/* 172 */       MQEnvironment.sslCipherSuite = str12;
/* 173 */       if (str13 != null && str13.length() > 0) {
/* 174 */         System.setProperty("javax.net.ssl.keyStore", str13);
/*     */       }
/* 176 */       if (str14 != null && str14.length() > 0) {
/* 177 */         System.setProperty("javax.net.ssl.keyStorePassword", 
/* 178 */             AES256Utils.decrypt(str14));
/*     */       }
/* 180 */       if (str15 != null && str15.length() > 0) {
/* 181 */         System.setProperty("javax.net.ssl.trustStore", str15);
/*     */       }
/* 183 */       if (str16 != null && str16.length() > 0) {
/* 184 */         System.setProperty("javax.net.ssl.trustStorePassword", 
/* 185 */             AES256Utils.decrypt(str16));
/*     */       }
/*     */     } 
/*     */     
/* 189 */     log("MQ Infor:" + str5 + ":" + str6 + ":" + str7 + ":" + str8 + ":" + str9 + ":" + str10 + ":" + str11);
/*     */ 
/*     */ 
/*     */     
/* 193 */     MQEnvironment.hostname = str8;
/*     */ 
/*     */ 
/*     */     
/* 197 */     MQEnvironment.channel = str9;
/*     */     
/* 199 */     MQEnvironment.properties.put("transport", "MQSeries");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 206 */       log("***Starting to get MQ***");
/* 207 */       log("Creating a connection to the queue manager");
/*     */       
/* 209 */       MQQueueManager mQQueueManager = new MQQueueManager(str10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 215 */       char c = '•';
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 220 */       MQQueue mQQueue = mQQueueManager.accessQueue(str11, c, null, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 226 */       MQGetMessageOptions mQGetMessageOptions = new MQGetMessageOptions();
/* 227 */       mQGetMessageOptions.options = 8193;
/* 228 */       if (mQQueue.getCurrentDepth() > 0) {
/*     */         
/* 230 */         Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 231 */         MessageDigest messageDigest = null;
/*     */         try {
/* 233 */           messageDigest = MessageDigest.getInstance("SHA-256");
/* 234 */         } catch (Exception exception) {
/* 235 */           log(exception.getMessage());
/*     */         } 
/* 237 */         while (mQQueue.getCurrentDepth() > 0) {
/* 238 */           MQMessage mQMessage = new MQMessage();
/*     */ 
/*     */           
/* 241 */           mQQueue.get(mQMessage, mQGetMessageOptions);
/* 242 */           String str18 = mQMessage.readLine();
/*     */           
/* 244 */           String str19 = str1.trim() + "_" + mQMessage.applicationIdData.trim() + "." + str3;
/* 245 */           String str20 = str2 + str19;
/*     */           
/* 247 */           if (!(new File(str20)).exists()) {
/* 248 */             (new File(str20)).createNewFile();
/* 249 */             String str = str19 + " START";
/* 250 */             saveDataToFile(str20, str, false);
/*     */           } 
/* 252 */           byte[] arrayOfByte1 = (str18 + NEWLINE).getBytes("UTF-8");
/* 253 */           messageDigest.update(arrayOfByte1);
/* 254 */           saveDataToFile(str20, str18, true);
/* 255 */           Object object = hashtable.get(str20);
/* 256 */           int j = 0;
/* 257 */           if (object != null) {
/* 258 */             j = ((Integer)object).intValue();
/*     */           }
/* 260 */           j++;
/*     */ 
/*     */           
/* 263 */           hashtable.put(str20, new Integer(j));
/* 264 */           if (i != 0 && j >= i) {
/* 265 */             log(str19 + " Reach the defined max count: " + i);
/*     */             break;
/*     */           } 
/*     */         } 
/* 269 */         byte[] arrayOfByte = messageDigest.digest();
/*     */ 
/*     */         
/* 272 */         StringBuffer stringBuffer = new StringBuffer("");
/* 273 */         for (byte b = 0; b < arrayOfByte.length; b++) {
/* 274 */           stringBuffer.append(Integer.toString((arrayOfByte[b] & 0xFF) + 256, 16).substring(1));
/*     */         }
/*     */         
/* 277 */         for (String str18 : hashtable.keySet()) {
/*     */           
/* 279 */           int j = ((Integer)hashtable.get(str18)).intValue();
/* 280 */           String str19 = (j + 2) + " " + stringBuffer.toString();
/* 281 */           saveDataToFile(str18, str19, true);
/* 282 */           log(str18 + " " + str19);
/*     */         } 
/*     */       } else {
/* 285 */         log("***No Messge in the MQ***");
/*     */       } 
/*     */       
/* 288 */       log("Closing the queue");
/* 289 */       mQQueue.close();
/*     */ 
/*     */       
/* 292 */       mQQueueManager.commit();
/* 293 */       mQQueueManager.disconnect();
/*     */     }
/* 295 */     catch (MQException mQException) {
/* 296 */       log("Error: An MQSeries error occurred : Completion code " + mQException.completionCode + " Reason code " + mQException.reasonCode);
/*     */     }
/* 298 */     catch (IOException iOException) {
/* 299 */       log("Error: An error occurred whilst writing to the message buffer: " + iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveDataToFile(String paramString1, String paramString2, boolean paramBoolean) {
/* 305 */     BufferedWriter bufferedWriter = null;
/*     */     try {
/* 307 */       bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paramString1, paramBoolean), "UTF-8"));
/*     */       
/* 309 */       bufferedWriter.write(paramString2 + NEWLINE);
/* 310 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 311 */       unsupportedEncodingException.printStackTrace();
/* 312 */     } catch (FileNotFoundException fileNotFoundException) {
/* 313 */       fileNotFoundException.printStackTrace();
/* 314 */     } catch (IOException iOException) {
/* 315 */       iOException.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 318 */         if (bufferedWriter != null)
/* 319 */           bufferedWriter.close(); 
/* 320 */       } catch (IOException iOException) {
/* 321 */         iOException.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getCurrentTimestamp() {
/* 327 */     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
/* 328 */     String str = "";
/* 329 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss.SSS");
/*     */     try {
/* 331 */       str = simpleDateFormat.format(timestamp);
/* 332 */     } catch (Exception exception) {
/* 333 */       exception.printStackTrace();
/*     */     } 
/* 335 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 342 */     String str = "IDLFILEGen";
/* 343 */     if (paramArrayOfString.length > 0) {
/* 344 */       str = paramArrayOfString[0];
/*     */     } else {
/* 346 */       System.out.println("Use the defalut properties file - IDLFILEGen.properties");
/*     */     } 
/* 348 */     ResourceBundle resourceBundle = ResourceBundle.getBundle(str);
/* 349 */     XMLIDLFileGen xMLIDLFileGen = new XMLIDLFileGen(resourceBundle);
/* 350 */     xMLIDLFileGen.getMsgToFile();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\XMLIDLFileGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */