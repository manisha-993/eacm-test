/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ 
/*     */ public class ADSGENAREAABR
/*     */   extends XMLMQAdapter
/*     */ {
/*     */   private static final String GENAREA_SQL = "select genareacode,genareaname_fc,genareaname,sleorg,genareatype,genareaparent_fc,genareaparent,wwcoacode,isactive,LONGDESCRIPTION from price.generalarea p left join opicm.flag  f on f.entityid = p.entityid and f.entitytype='GENERALAREA' and f.ATTRIBUTECODE='RFAGEO' and f.EFFTO>current timestamp and f.valto>current timestamp left join opicm.metadescription m on m.DESCRIPTIONTYPE = 'RFAGEO' and m.DESCRIPTIONCLASS=f.ATTRIBUTEVALUE and p.nlsid=m.nlsid and m.EFFTO>current timestamp and m.valto>current timestamp where genareatype='Country' and p.nlsid=1 and p.Valfrom BETWEEN ? AND ? with ur";
/*     */   
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/* 127 */     String str1 = paramProfile1.getValOn();
/* 128 */     String str2 = paramProfile2.getValOn();
/* 129 */     String str3 = "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSGENAREA05ABR";
/* 130 */     paramADSABRSTATUS.addDebug("ADSGENAREAABR.processThis checking between " + str1 + " and " + str2);
/* 131 */     boolean bool1 = false;
/* 132 */     boolean bool2 = false;
/* 133 */     String str4 = "05";
/* 134 */     String str5 = "10";
/* 135 */     Hashtable hashtable = getMQPropertiesVN(paramEntityItem, paramADSABRSTATUS);
/* 136 */     bool1 = hashtable.containsKey(str4);
/* 137 */     bool2 = hashtable.containsKey(str5);
/* 138 */     if (bool1) {
/*     */       try {
/* 140 */         XMLMQ xMLMQ = (XMLMQ)Class.forName(str3).newInstance();
/* 141 */         xMLMQ.processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, paramEntityItem);
/* 142 */       } catch (InstantiationException instantiationException) {
/* 143 */         instantiationException.printStackTrace();
/* 144 */         throw new IOException("Can not instance " + str3 + " class!");
/* 145 */       } catch (IllegalAccessException illegalAccessException) {
/* 146 */         illegalAccessException.printStackTrace();
/* 147 */         throw new IOException("Can not access " + str3 + " class!");
/* 148 */       } catch (ClassNotFoundException classNotFoundException) {
/* 149 */         classNotFoundException.printStackTrace();
/* 150 */         throw new IOException("Can not find " + str3 + " class!");
/*     */       } 
/*     */     }
/* 153 */     if (bool2) {
/* 154 */       Vector<GenAreaInfo> vector = getGenarea(paramADSABRSTATUS, str1, str2);
/* 155 */       if (vector.size() == 0) {
/*     */         
/* 157 */         paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", "GENERALAREA");
/*     */       } else {
/* 159 */         paramADSABRSTATUS.addDebug("ADSGENAREAABR.processThis found " + vector.size() + " GENERALAREA");
/*     */         
/* 161 */         Vector vector1 = getPeriodicMQ(paramEntityItem);
/* 162 */         if (vector1 == null) {
/* 163 */           paramADSABRSTATUS.addDebug("ADSGENAREAABR: No MQ properties files, nothing will be generated.");
/*     */           
/* 165 */           paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "GENERALAREA");
/*     */         } else {
/* 167 */           DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 168 */           DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 169 */           Document document = documentBuilder.newDocument();
/* 170 */           String str6 = "GENERALAREA_UPDATE";
/* 171 */           String str7 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str6;
/*     */ 
/*     */           
/* 174 */           Element element1 = document.createElementNS(str7, str6);
/*     */           
/* 176 */           document.appendChild(element1);
/* 177 */           element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str7);
/* 178 */           element1.appendChild(document.createComment("GENERALAREA_UPDATE Version 1 Mod 0"));
/*     */           
/* 180 */           Element element2 = document.createElement("DTSOFMSG");
/* 181 */           element2.appendChild(document.createTextNode(paramProfile2.getEndOfDay()));
/* 182 */           element1.appendChild(element2);
/*     */ 
/*     */           
/* 185 */           for (byte b = 0; b < vector.size(); b++) {
/* 186 */             GenAreaInfo genAreaInfo = vector.elementAt(b);
/*     */             
/* 188 */             Element element = document.createElement("GENAREAELEMENT");
/* 189 */             element1.appendChild(element);
/*     */             
/* 191 */             element2 = document.createElement("ACTIVITY");
/* 192 */             element2.appendChild(document.createTextNode(genAreaInfo.isactive ? "Update" : "Delete"));
/* 193 */             element.appendChild(element2);
/*     */             
/* 195 */             element2 = document.createElement("GENAREACODE");
/* 196 */             element2.appendChild(document.createTextNode(genAreaInfo.genareacode));
/* 197 */             element.appendChild(element2);
/*     */             
/* 199 */             element2 = document.createElement("GENAREANAME_FC");
/* 200 */             element2.appendChild(document.createTextNode(genAreaInfo.genareaname_fc));
/* 201 */             element.appendChild(element2);
/*     */             
/* 203 */             element2 = document.createElement("GENAREANAME");
/* 204 */             element2.appendChild(document.createTextNode(genAreaInfo.genareaname));
/* 205 */             element.appendChild(element2);
/*     */             
/* 207 */             element2 = document.createElement("SLEORG");
/* 208 */             element2.appendChild(document.createTextNode(genAreaInfo.sleorg));
/* 209 */             element.appendChild(element2);
/*     */             
/* 211 */             element2 = document.createElement("GENAREATYPE");
/* 212 */             element2.appendChild(document.createTextNode(genAreaInfo.genareatype));
/* 213 */             element.appendChild(element2);
/*     */             
/* 215 */             element2 = document.createElement("GENAREAPARENT_FC");
/* 216 */             element2.appendChild(document.createTextNode(genAreaInfo.genareaparent_fc));
/* 217 */             element.appendChild(element2);
/*     */             
/* 219 */             element2 = document.createElement("GENAREAPARENT");
/* 220 */             element2.appendChild(document.createTextNode(genAreaInfo.genareaparent));
/* 221 */             element.appendChild(element2);
/*     */             
/* 223 */             element2 = document.createElement("WWCOACODE");
/* 224 */             element2.appendChild(document.createTextNode(genAreaInfo.wwcoacode));
/* 225 */             element.appendChild(element2);
/*     */             
/* 227 */             element2 = document.createElement("RFAGEO");
/* 228 */             element2.appendChild(document.createTextNode(genAreaInfo.rfageo));
/* 229 */             element.appendChild(element2);
/*     */             
/* 231 */             genAreaInfo.dereference();
/*     */           } 
/*     */           
/* 234 */           String str8 = paramADSABRSTATUS.transformXML(this, document);
/*     */ 
/*     */           
/* 237 */           boolean bool = false;
/* 238 */           String str9 = paramEntityItem.getEntityType();
/* 239 */           String str10 = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str9 + "_XSDNEEDED", "NO");
/* 240 */           if ("YES".equals(str10.toUpperCase())) {
/* 241 */             String str = ABRServerProperties.getValue("ADSABRSTATUS", "_" + str9 + "_XSDFILE", "NONE");
/* 242 */             if ("NONE".equals(str)) {
/* 243 */               paramADSABRSTATUS.addError("there is no xsdfile for " + str9 + " defined in the propertyfile ");
/*     */             } else {
/* 245 */               long l1 = System.currentTimeMillis();
/* 246 */               Class<?> clazz = getClass();
/* 247 */               StringBuffer stringBuffer = new StringBuffer();
/* 248 */               bool = ABRUtil.validatexml(clazz, stringBuffer, str, str8);
/* 249 */               if (stringBuffer.length() > 0) {
/* 250 */                 String str11 = stringBuffer.toString();
/* 251 */                 if (str11.indexOf("fail") != -1)
/* 252 */                   paramADSABRSTATUS.addError(str11); 
/* 253 */                 paramADSABRSTATUS.addOutput(str11);
/*     */               } 
/* 255 */               long l2 = System.currentTimeMillis();
/* 256 */               paramADSABRSTATUS.addDebugComment(3, "Time for validation: " + Stopwatch.format(l2 - l1));
/* 257 */               if (bool) {
/* 258 */                 paramADSABRSTATUS.addDebug("the xml for " + str9 + " passed the validation");
/*     */               }
/*     */             } 
/*     */           } else {
/* 262 */             paramADSABRSTATUS.addOutput("the xml for " + str9 + " doesn't need to be validated");
/* 263 */             bool = true;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 269 */           if (str8 != null && bool) {
/* 270 */             paramADSABRSTATUS.addDebug("ADSGENAREAABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str8 + ADSABRSTATUS.NEWLINE);
/* 271 */             paramADSABRSTATUS.notify(this, "GENERALAREA", str8, vector1);
/*     */           } 
/*     */         } 
/*     */         
/* 275 */         vector.clear();
/*     */       } 
/*     */     } 
/* 278 */     if (!bool1 && !bool2) {
/* 279 */       paramADSABRSTATUS.addError("Error: Invalid Version. Can not find request Version and Mod!");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getGenarea(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2) throws SQLException {
/* 286 */     Vector<GenAreaInfo> vector = new Vector();
/* 287 */     ResultSet resultSet = null;
/* 288 */     Connection connection = null;
/* 289 */     PreparedStatement preparedStatement = null;
/*     */     try {
/* 291 */       connection = setupConnection();
/* 292 */       preparedStatement = connection.prepareStatement("select genareacode,genareaname_fc,genareaname,sleorg,genareatype,genareaparent_fc,genareaparent,wwcoacode,isactive,LONGDESCRIPTION from price.generalarea p left join opicm.flag  f on f.entityid = p.entityid and f.entitytype='GENERALAREA' and f.ATTRIBUTECODE='RFAGEO' and f.EFFTO>current timestamp and f.valto>current timestamp left join opicm.metadescription m on m.DESCRIPTIONTYPE = 'RFAGEO' and m.DESCRIPTIONCLASS=f.ATTRIBUTEVALUE and p.nlsid=m.nlsid and m.EFFTO>current timestamp and m.valto>current timestamp where genareatype='Country' and p.nlsid=1 and p.Valfrom BETWEEN ? AND ? with ur");
/*     */ 
/*     */       
/* 295 */       preparedStatement.setString(1, paramString1);
/* 296 */       preparedStatement.setString(2, paramString2);
/*     */       
/* 298 */       resultSet = preparedStatement.executeQuery();
/* 299 */       while (resultSet.next()) {
/* 300 */         String str1 = resultSet.getString(1);
/* 301 */         String str2 = resultSet.getString(2);
/* 302 */         String str3 = resultSet.getString(3);
/* 303 */         String str4 = resultSet.getString(4);
/* 304 */         String str5 = resultSet.getString(5);
/* 305 */         String str6 = resultSet.getString(6);
/* 306 */         String str7 = resultSet.getString(7);
/* 307 */         String str8 = resultSet.getString(8);
/*     */         
/* 309 */         int i = resultSet.getInt(9);
/* 310 */         String str9 = resultSet.getString(10);
/*     */         
/* 312 */         paramADSABRSTATUS.addDebug("getGenarea code:" + str1 + " name_fc:" + str2 + " name:" + str3 + " org:" + str4 + " type:" + str5 + " parent_fc:" + str6 + " parent:" + str7 + " wwcoa:" + str8 + " active:" + i + " rfageo:" + str9);
/*     */ 
/*     */         
/* 315 */         vector.add(new GenAreaInfo(str1, str2, str3, str4, str5, str6, str7, str8, i, str9));
/*     */       } 
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/* 321 */         if (preparedStatement != null) {
/* 322 */           preparedStatement.close();
/* 323 */           preparedStatement = null;
/*     */         } 
/* 325 */       } catch (Exception exception) {
/* 326 */         System.err.println("getGenarea(), unable to close statement. " + exception);
/* 327 */         paramADSABRSTATUS.addDebug("getGenarea unable to close statement. " + exception);
/*     */       } 
/* 329 */       if (resultSet != null) {
/* 330 */         resultSet.close();
/*     */       }
/* 332 */       closeConnection(connection);
/*     */     } 
/* 334 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 343 */     return "1.6";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 350 */     return "GENERALAREA_UPDATE";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 355 */     return "ADSABRSTATUS";
/*     */   }
/*     */   
/* 358 */   private static class GenAreaInfo { String genareacode = "@@";
/* 359 */     String genareaname_fc = "@@";
/* 360 */     String genareaname = "@@";
/* 361 */     String sleorg = "@@";
/* 362 */     String genareatype = "@@";
/* 363 */     String genareaparent_fc = "@@";
/* 364 */     String genareaparent = "@@";
/* 365 */     String wwcoacode = "@@";
/* 366 */     String rfageo = "@@";
/*     */     boolean isactive = false;
/*     */     
/*     */     GenAreaInfo(String param1String1, String param1String2, String param1String3, String param1String4, String param1String5, String param1String6, String param1String7, String param1String8, int param1Int, String param1String9) {
/* 370 */       if (param1String1 != null) {
/* 371 */         this.genareacode = param1String1.trim();
/*     */       }
/* 373 */       if (param1String2 != null) {
/* 374 */         this.genareaname_fc = param1String2.trim();
/*     */       }
/* 376 */       if (param1String3 != null) {
/* 377 */         this.genareaname = param1String3.trim();
/*     */       }
/* 379 */       if (param1String4 != null) {
/* 380 */         this.sleorg = param1String4.trim();
/*     */       }
/* 382 */       if (param1String5 != null) {
/* 383 */         this.genareatype = param1String5.trim();
/*     */       }
/* 385 */       if (param1String6 != null) {
/* 386 */         this.genareaparent_fc = param1String6.trim();
/*     */       }
/* 388 */       if (param1String7 != null) {
/* 389 */         this.genareaparent = param1String7.trim();
/*     */       }
/* 391 */       if (param1String8 != null) {
/* 392 */         this.wwcoacode = param1String8.trim();
/*     */       }
/* 394 */       this.isactive = (param1Int == 1);
/* 395 */       if (param1String9 != null)
/* 396 */         this.rfageo = param1String9.trim(); 
/*     */     }
/*     */     
/*     */     void dereference() {
/* 400 */       this.genareacode = null;
/* 401 */       this.genareaname_fc = null;
/* 402 */       this.genareaname = null;
/* 403 */       this.sleorg = null;
/* 404 */       this.genareatype = null;
/* 405 */       this.genareaparent_fc = null;
/* 406 */       this.wwcoacode = null;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSGENAREAABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */