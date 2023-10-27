/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class ADSGENAREAABR
/*     */   extends XMLMQAdapter
/*     */ {
/*     */   private static final String GENAREA_SQL = "select genareacode,genareaname_fc,genareaname,sleorg,genareatype,genareaparent_fc,genareaparent,wwcoacode,isactive  from price.generalarea where genareatype='Country' and nlsid=1 and Valfrom BETWEEN ? AND ?";
/*     */   
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/* 126 */     String str1 = paramProfile1.getValOn();
/* 127 */     String str2 = paramProfile2.getValOn();
/* 128 */     String str3 = "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSGENAREA05ABR";
/* 129 */     paramADSABRSTATUS.addDebug("ADSGENAREAABR.processThis checking between " + str1 + " and " + str2);
/* 130 */     boolean bool1 = false;
/* 131 */     boolean bool2 = false;
/* 132 */     String str4 = "05";
/* 133 */     String str5 = "10";
/* 134 */     Hashtable hashtable = getMQPropertiesVN(paramEntityItem, paramADSABRSTATUS);
/* 135 */     bool1 = hashtable.containsKey(str4);
/* 136 */     bool2 = hashtable.containsKey(str5);
/* 137 */     if (bool1) {
/*     */       try {
/* 139 */         XMLMQ xMLMQ = (XMLMQ)Class.forName(str3).newInstance();
/* 140 */         xMLMQ.processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem);
/* 141 */       } catch (InstantiationException instantiationException) {
/* 142 */         instantiationException.printStackTrace();
/* 143 */         throw new IOException("Can not instance " + str3 + " class!");
/* 144 */       } catch (IllegalAccessException illegalAccessException) {
/* 145 */         illegalAccessException.printStackTrace();
/* 146 */         throw new IOException("Can not access " + str3 + " class!");
/* 147 */       } catch (ClassNotFoundException classNotFoundException) {
/* 148 */         classNotFoundException.printStackTrace();
/* 149 */         throw new IOException("Can not find " + str3 + " class!");
/*     */       } 
/*     */     }
/* 152 */     if (bool2) {
/* 153 */       Vector<GenAreaInfo> vector = getGenarea(paramADSABRSTATUS, str1, str2);
/* 154 */       if (vector.size() == 0) {
/*     */         
/* 156 */         paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", "GENERALAREA");
/*     */       } else {
/* 158 */         paramADSABRSTATUS.addDebug("ADSGENAREAABR.processThis found " + vector.size() + " GENERALAREA");
/*     */         
/* 160 */         Vector vector1 = getPeriodicMQ(paramEntityItem);
/* 161 */         if (vector1 == null) {
/* 162 */           paramADSABRSTATUS.addDebug("ADSGENAREAABR: No MQ properties files, nothing will be generated.");
/*     */           
/* 164 */           paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "GENERALAREA");
/*     */         } else {
/* 166 */           DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 167 */           DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 168 */           Document document = documentBuilder.newDocument();
/* 169 */           String str6 = "GENERALAREA_UPDATE";
/* 170 */           String str7 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str6;
/*     */ 
/*     */           
/* 173 */           Element element1 = document.createElementNS(str7, str6);
/*     */           
/* 175 */           document.appendChild(element1);
/* 176 */           element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str7);
/* 177 */           element1.appendChild(document.createComment("GENERALAREA_UPDATE Version 1 Mod 0"));
/*     */           
/* 179 */           Element element2 = document.createElement("DTSOFMSG");
/* 180 */           element2.appendChild(document.createTextNode(paramProfile2.getEndOfDay()));
/* 181 */           element1.appendChild(element2);
/*     */ 
/*     */           
/* 184 */           for (byte b = 0; b < vector.size(); b++) {
/* 185 */             GenAreaInfo genAreaInfo = vector.elementAt(b);
/* 186 */             Element element = document.createElement("GENAREAELEMENT");
/* 187 */             element1.appendChild(element);
/*     */             
/* 189 */             element2 = document.createElement("ACTIVITY");
/* 190 */             element2.appendChild(document.createTextNode(genAreaInfo.isactive ? "Update" : "Delete"));
/* 191 */             element.appendChild(element2);
/*     */             
/* 193 */             element2 = document.createElement("GENAREACODE");
/* 194 */             element2.appendChild(document.createTextNode(genAreaInfo.genareacode));
/* 195 */             element.appendChild(element2);
/*     */             
/* 197 */             element2 = document.createElement("GENAREANAME_FC");
/* 198 */             element2.appendChild(document.createTextNode(genAreaInfo.genareaname_fc));
/* 199 */             element.appendChild(element2);
/*     */             
/* 201 */             element2 = document.createElement("GENAREANAME");
/* 202 */             element2.appendChild(document.createTextNode(genAreaInfo.genareaname));
/* 203 */             element.appendChild(element2);
/*     */             
/* 205 */             element2 = document.createElement("SLEORG");
/* 206 */             element2.appendChild(document.createTextNode(genAreaInfo.sleorg));
/* 207 */             element.appendChild(element2);
/*     */             
/* 209 */             element2 = document.createElement("GENAREATYPE");
/* 210 */             element2.appendChild(document.createTextNode(genAreaInfo.genareatype));
/* 211 */             element.appendChild(element2);
/*     */             
/* 213 */             element2 = document.createElement("GENAREAPARENT_FC");
/* 214 */             element2.appendChild(document.createTextNode(genAreaInfo.genareaparent_fc));
/* 215 */             element.appendChild(element2);
/*     */             
/* 217 */             element2 = document.createElement("GENAREAPARENT");
/* 218 */             element2.appendChild(document.createTextNode(genAreaInfo.genareaparent));
/* 219 */             element.appendChild(element2);
/*     */             
/* 221 */             element2 = document.createElement("WWCOACODE");
/* 222 */             element2.appendChild(document.createTextNode(genAreaInfo.wwcoacode));
/* 223 */             element.appendChild(element2);
/*     */ 
/*     */             
/* 226 */             genAreaInfo.dereference();
/*     */           } 
/*     */           
/* 229 */           String str8 = paramADSABRSTATUS.transformXML(this, document);
/*     */ 
/*     */           
/* 232 */           boolean bool = false;
/* 233 */           String str9 = paramEntityItem.getEntityType();
/* 234 */           String str10 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str9 + "_XSDNEEDED", "NO");
/* 235 */           if ("YES".equals(str10.toUpperCase())) {
/* 236 */             String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str9 + "_XSDFILE", "NONE");
/* 237 */             if ("NONE".equals(str)) {
/* 238 */               paramADSABRSTATUS.addError("there is no xsdfile for " + str9 + " defined in the propertyfile ");
/*     */             } else {
/* 240 */               long l1 = System.currentTimeMillis();
/* 241 */               Class<?> clazz = getClass();
/* 242 */               StringBuffer stringBuffer = new StringBuffer();
/* 243 */               bool = ABRUtil.validatexml(clazz, stringBuffer, str, str8);
/* 244 */               if (stringBuffer.length() > 0) {
/* 245 */                 String str11 = stringBuffer.toString();
/* 246 */                 if (str11.indexOf("fail") != -1)
/* 247 */                   paramADSABRSTATUS.addError(str11); 
/* 248 */                 paramADSABRSTATUS.addOutput(str11);
/*     */               } 
/* 250 */               long l2 = System.currentTimeMillis();
/* 251 */               paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l2 - l1));
/* 252 */               if (bool) {
/* 253 */                 paramADSABRSTATUS.addDebug("the xml for " + str9 + " passed the validation");
/*     */               }
/*     */             } 
/*     */           } else {
/* 257 */             paramADSABRSTATUS.addOutput("the xml for " + str9 + " doesn't need to be validated");
/* 258 */             bool = true;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 264 */           if (str8 != null && bool) {
/* 265 */             paramADSABRSTATUS.addDebug("ADSGENAREAABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str8 + ADSABRSTATUS.NEWLINE);
/* 266 */             paramADSABRSTATUS.notify(this, "GENERALAREA", str8, vector1);
/*     */           } 
/*     */         } 
/*     */         
/* 270 */         vector.clear();
/*     */       } 
/*     */     } 
/* 273 */     if (!bool1 && !bool2) {
/* 274 */       paramADSABRSTATUS.addError("Error: Invalid Version. Can not find request Version and Mod!");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getGenarea(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2) throws SQLException {
/* 281 */     Vector<GenAreaInfo> vector = new Vector();
/* 282 */     ResultSet resultSet = null;
/* 283 */     Connection connection = null;
/* 284 */     PreparedStatement preparedStatement = null;
/*     */     try {
/* 286 */       connection = setupConnection();
/* 287 */       preparedStatement = connection.prepareStatement("select genareacode,genareaname_fc,genareaname,sleorg,genareatype,genareaparent_fc,genareaparent,wwcoacode,isactive  from price.generalarea where genareatype='Country' and nlsid=1 and Valfrom BETWEEN ? AND ?");
/*     */ 
/*     */       
/* 290 */       preparedStatement.setString(1, paramString1);
/* 291 */       preparedStatement.setString(2, paramString2);
/*     */       
/* 293 */       resultSet = preparedStatement.executeQuery();
/* 294 */       while (resultSet.next()) {
/* 295 */         String str1 = resultSet.getString(1);
/* 296 */         String str2 = resultSet.getString(2);
/* 297 */         String str3 = resultSet.getString(3);
/* 298 */         String str4 = resultSet.getString(4);
/* 299 */         String str5 = resultSet.getString(5);
/* 300 */         String str6 = resultSet.getString(6);
/* 301 */         String str7 = resultSet.getString(7);
/* 302 */         String str8 = resultSet.getString(8);
/* 303 */         int i = resultSet.getInt(9);
/*     */         
/* 305 */         paramADSABRSTATUS.addDebug("getGenarea code:" + str1 + " name_fc:" + str2 + " name:" + str3 + " org:" + str4 + " type:" + str5 + " parent_fc:" + str6 + " parent:" + str7 + " wwcoa:" + str8 + " active:" + i);
/*     */ 
/*     */         
/* 308 */         vector.add(new GenAreaInfo(str1, str2, str3, str4, str5, str6, str7, str8, i));
/*     */       } 
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/* 314 */         if (preparedStatement != null) {
/* 315 */           preparedStatement.close();
/* 316 */           preparedStatement = null;
/*     */         } 
/* 318 */       } catch (Exception exception) {
/* 319 */         System.err.println("getGenarea(), unable to close statement. " + exception);
/* 320 */         paramADSABRSTATUS.addDebug("getGenarea unable to close statement. " + exception);
/*     */       } 
/* 322 */       if (resultSet != null) {
/* 323 */         resultSet.close();
/*     */       }
/* 325 */       closeConnection(connection);
/*     */     } 
/* 327 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 336 */     return "1.6";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 343 */     return "GENERALAREA_UPDATE";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 348 */     return "ADSABRSTATUS";
/*     */   }
/*     */   
/* 351 */   private static class GenAreaInfo { String genareacode = "@@";
/* 352 */     String genareaname_fc = "@@";
/* 353 */     String genareaname = "@@";
/* 354 */     String sleorg = "@@";
/* 355 */     String genareatype = "@@";
/* 356 */     String genareaparent_fc = "@@";
/* 357 */     String genareaparent = "@@";
/* 358 */     String wwcoacode = "@@";
/*     */     boolean isactive = false;
/*     */     
/*     */     GenAreaInfo(String param1String1, String param1String2, String param1String3, String param1String4, String param1String5, String param1String6, String param1String7, String param1String8, int param1Int) {
/* 362 */       if (param1String1 != null) {
/* 363 */         this.genareacode = param1String1.trim();
/*     */       }
/* 365 */       if (param1String2 != null) {
/* 366 */         this.genareaname_fc = param1String2.trim();
/*     */       }
/* 368 */       if (param1String3 != null) {
/* 369 */         this.genareaname = param1String3.trim();
/*     */       }
/* 371 */       if (param1String4 != null) {
/* 372 */         this.sleorg = param1String4.trim();
/*     */       }
/* 374 */       if (param1String5 != null) {
/* 375 */         this.genareatype = param1String5.trim();
/*     */       }
/* 377 */       if (param1String6 != null) {
/* 378 */         this.genareaparent_fc = param1String6.trim();
/*     */       }
/* 380 */       if (param1String7 != null) {
/* 381 */         this.genareaparent = param1String7.trim();
/*     */       }
/* 383 */       if (param1String8 != null) {
/* 384 */         this.wwcoacode = param1String8.trim();
/*     */       }
/* 386 */       this.isactive = (param1Int == 1);
/*     */     }
/*     */     
/*     */     void dereference() {
/* 390 */       this.genareacode = null;
/* 391 */       this.genareaname_fc = null;
/* 392 */       this.genareaname = null;
/* 393 */       this.sleorg = null;
/* 394 */       this.genareatype = null;
/* 395 */       this.genareaparent_fc = null;
/* 396 */       this.wwcoacode = null;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSGENAREAABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */