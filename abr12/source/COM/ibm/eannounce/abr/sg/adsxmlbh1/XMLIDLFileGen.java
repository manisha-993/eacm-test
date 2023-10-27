/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/* 214 */       char c = '•';
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 219 */       MQQueue mQQueue = mQQueueManager.accessQueue(str11, c, null, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 225 */       MQGetMessageOptions mQGetMessageOptions = new MQGetMessageOptions();
/* 226 */       mQGetMessageOptions.options = 8193;
/* 227 */       if (mQQueue.getCurrentDepth() > 0) {
/*     */         
/* 229 */         Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 230 */         MessageDigest messageDigest = null;
/*     */         try {
/* 232 */           messageDigest = MessageDigest.getInstance("SHA-256");
/* 233 */         } catch (Exception exception) {
/* 234 */           log(exception.getMessage());
/*     */         } 
/* 236 */         while (mQQueue.getCurrentDepth() > 0) {
/* 237 */           MQMessage mQMessage = new MQMessage();
/*     */ 
/*     */           
/* 240 */           mQQueue.get(mQMessage, mQGetMessageOptions);
/* 241 */           String str18 = mQMessage.readLine();
/*     */           
/* 243 */           String str19 = str1.trim() + "_" + mQMessage.applicationIdData.trim() + "." + str3;
/* 244 */           String str20 = str2 + str19;
/*     */           
/* 246 */           if (!(new File(str20)).exists()) {
/* 247 */             (new File(str20)).createNewFile();
/* 248 */             String str = str19 + " START";
/* 249 */             saveDataToFile(str20, str, false);
/*     */           } 
/* 251 */           byte[] arrayOfByte1 = (str18 + NEWLINE).getBytes("UTF-8");
/* 252 */           messageDigest.update(arrayOfByte1);
/* 253 */           saveDataToFile(str20, str18, true);
/* 254 */           Object object = hashtable.get(str20);
/* 255 */           int j = 0;
/* 256 */           if (object != null) {
/* 257 */             j = ((Integer)object).intValue();
/*     */           }
/* 259 */           j++;
/*     */ 
/*     */           
/* 262 */           hashtable.put(str20, new Integer(j));
/* 263 */           if (i != 0 && j >= i) {
/* 264 */             log(str19 + " Reach the defined max count: " + i);
/*     */             break;
/*     */           } 
/*     */         } 
/* 268 */         byte[] arrayOfByte = messageDigest.digest();
/*     */ 
/*     */         
/* 271 */         StringBuffer stringBuffer = new StringBuffer("");
/* 272 */         for (byte b = 0; b < arrayOfByte.length; b++) {
/* 273 */           stringBuffer.append(Integer.toString((arrayOfByte[b] & 0xFF) + 256, 16).substring(1));
/*     */         }
/*     */         
/* 276 */         for (String str18 : hashtable.keySet()) {
/*     */           
/* 278 */           int j = ((Integer)hashtable.get(str18)).intValue();
/* 279 */           String str19 = (j + 2) + " " + stringBuffer.toString();
/* 280 */           saveDataToFile(str18, str19, true);
/* 281 */           log(str18 + " " + str19);
/*     */         } 
/*     */       } else {
/* 284 */         log("***No Messge in the MQ***");
/*     */       } 
/*     */       
/* 287 */       log("Closing the queue");
/* 288 */       mQQueue.close();
/*     */ 
/*     */       
/* 291 */       mQQueueManager.commit();
/* 292 */       mQQueueManager.disconnect();
/*     */     }
/* 294 */     catch (MQException mQException) {
/* 295 */       log("Error: An MQSeries error occurred : Completion code " + mQException.completionCode + " Reason code " + mQException.reasonCode);
/*     */     }
/* 297 */     catch (IOException iOException) {
/* 298 */       log("Error: An error occurred whilst writing to the message buffer: " + iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveDataToFile(String paramString1, String paramString2, boolean paramBoolean) {
/* 304 */     BufferedWriter bufferedWriter = null;
/*     */     try {
/* 306 */       bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paramString1, paramBoolean), "UTF-8"));
/*     */       
/* 308 */       bufferedWriter.write(paramString2 + NEWLINE);
/* 309 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 310 */       unsupportedEncodingException.printStackTrace();
/* 311 */     } catch (FileNotFoundException fileNotFoundException) {
/* 312 */       fileNotFoundException.printStackTrace();
/* 313 */     } catch (IOException iOException) {
/* 314 */       iOException.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 317 */         if (bufferedWriter != null)
/* 318 */           bufferedWriter.close(); 
/* 319 */       } catch (IOException iOException) {
/* 320 */         iOException.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getCurrentTimestamp() {
/* 326 */     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
/* 327 */     String str = "";
/* 328 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss.SSS");
/*     */     try {
/* 330 */       str = simpleDateFormat.format(timestamp);
/* 331 */     } catch (Exception exception) {
/* 332 */       exception.printStackTrace();
/*     */     } 
/* 334 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 341 */     String str = "IDLFILEGen";
/* 342 */     if (paramArrayOfString.length > 0) {
/* 343 */       str = paramArrayOfString[0];
/*     */     } else {
/* 345 */       System.out.println("Use the defalut properties file - IDLFILEGen.properties");
/*     */     } 
/* 347 */     ResourceBundle resourceBundle = ResourceBundle.getBundle(str);
/* 348 */     XMLIDLFileGen xMLIDLFileGen = new XMLIDLFileGen(resourceBundle);
/* 349 */     xMLIDLFileGen.getMsgToFile();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\XMLIDLFileGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */