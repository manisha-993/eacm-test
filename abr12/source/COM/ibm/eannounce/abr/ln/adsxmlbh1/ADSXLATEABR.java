/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
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
/*     */ public class ADSXLATEABR
/*     */   extends XMLMQAdapter
/*     */ {
/*     */   private static final String ADSATTRIBUTE = "ADSATTRIBUTE";
/*     */   private static final String ADSNLSID = "ADSNLSID";
/*     */   private static final String XLATE_SQL = "select descriptionclass,valfrom,longdescription, nlsid from opicm.metadescription where valto>current timestamp and enterprise=? and descriptiontype=? and Valfrom between ? and ? and nlsid in( ";
/*     */   
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/* 103 */     String str1 = paramProfile1.getValOn();
/* 104 */     String str2 = paramProfile2.getValOn();
/*     */     
/* 106 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("ADSATTRIBUTE");
/* 107 */     if (eANMetaAttribute == null) {
/* 108 */       throw new MiddlewareException("ADSATTRIBUTE not in meta for Periodic ABR " + paramEntityItem.getKey());
/*     */     }
/*     */ 
/*     */     
/* 112 */     String str3 = getShortDescription(paramEntityItem, "ADSATTRIBUTE", "short");
/* 113 */     String str4 = getShortDescription(paramEntityItem, "ADSNLSID", "flag");
/*     */     
/* 115 */     paramADSABRSTATUS.addDebug("ADSXLATEABR.processThis checking rootEntity =" + paramEntityItem.getKey() + ",Attribute of " + "ADSATTRIBUTE" + "=" + str3 + " between " + str1 + " and " + str2);
/* 116 */     paramADSABRSTATUS.addDebug("ADSXLATEABR.processThis checking rootEntity =" + paramEntityItem.getKey() + ",Attribute of " + "ADSNLSID" + "=" + str4);
/*     */ 
/*     */     
/* 119 */     Vector<XlateInfo> vector = new Vector();
/* 120 */     if (!str4.equals("")) {
/* 121 */       vector = getXlated(paramADSABRSTATUS, paramProfile2.getEnterprise(), str3, str1, str2, str4);
/*     */     }
/*     */     
/* 124 */     if (vector.size() == 0) {
/*     */       
/* 126 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", "XLATE");
/*     */     } else {
/* 128 */       paramADSABRSTATUS.addDebug("ADSXLATEABR.processThis found " + vector.size() + " XLATE");
/*     */ 
/*     */       
/* 131 */       Vector vector1 = getPeriodicMQ(paramEntityItem);
/*     */       
/* 133 */       if (vector1 == null) {
/* 134 */         paramADSABRSTATUS.addDebug("ADSXLATEABR: No MQ properties files, nothing will be generated.");
/*     */         
/* 136 */         paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "XLATE");
/*     */       } else {
/* 138 */         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 139 */         DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 140 */         Document document = documentBuilder.newDocument();
/*     */         
/* 142 */         Element element1 = document.createElementNS("http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/XLATE_UPDATE", "XLATE_UPDATE");
/* 143 */         element1.appendChild(document.createComment("XLATE_UPDATE Version 1 Mod 0"));
/*     */         
/* 145 */         document.appendChild(element1);
/* 146 */         Element element2 = document.createElement("DTSOFMSG");
/* 147 */         element2.appendChild(document.createTextNode(paramProfile2.getEndOfDay()));
/* 148 */         element1.appendChild(element2);
/*     */ 
/*     */         
/* 151 */         for (byte b = 0; b < vector.size(); b++) {
/* 152 */           XlateInfo xlateInfo = vector.elementAt(b);
/* 153 */           Element element = document.createElement("XLATEELEMENT");
/* 154 */           element1.appendChild(element);
/*     */           
/* 156 */           element2 = document.createElement("DTSOFUPDATE");
/* 157 */           element2.appendChild(document.createTextNode(xlateInfo.valfrom));
/* 158 */           element.appendChild(element2);
/*     */           
/* 160 */           element2 = document.createElement("ATTRIBUTECODE");
/* 161 */           element2.appendChild(document.createTextNode(str3));
/* 162 */           element.appendChild(element2);
/*     */           
/* 164 */           element2 = document.createElement("NLSID");
/* 165 */           element2.appendChild(document.createTextNode("" + xlateInfo.nlsid));
/* 166 */           element.appendChild(element2);
/*     */           
/* 168 */           element2 = document.createElement("DESCRIPTIONCLASS");
/* 169 */           element2.appendChild(document.createTextNode(xlateInfo.descriptionclass));
/* 170 */           element.appendChild(element2);
/*     */           
/* 172 */           element2 = document.createElement("LONGDESCRIPTION");
/* 173 */           element2.appendChild(document.createTextNode(xlateInfo.longdesc));
/* 174 */           element.appendChild(element2);
/*     */ 
/*     */           
/* 177 */           xlateInfo.dereference();
/*     */         } 
/*     */         
/* 180 */         String str = paramADSABRSTATUS.transformXML(this, document);
/* 181 */         if (!ADSABRSTATUS.USERXML_OFF_LOG) {
/* 182 */           paramADSABRSTATUS.addDebug("ADSXLATEABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str + ADSABRSTATUS.NEWLINE);
/*     */         }
/* 184 */         paramADSABRSTATUS.notify(this, "XLATE", str, vector1);
/*     */       } 
/*     */ 
/*     */       
/* 188 */       vector.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getShortDescription(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 199 */     String str = "";
/* 200 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString1);
/* 201 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */       
/* 203 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 204 */       StringBuffer stringBuffer = new StringBuffer();
/* 205 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */         
/* 207 */         if (arrayOfMetaFlag[b].isSelected()) {
/*     */           
/* 209 */           if (stringBuffer.length() > 0) {
/* 210 */             stringBuffer.append(",");
/*     */           }
/* 212 */           if (paramString2.equals("short")) {
/* 213 */             stringBuffer.append(arrayOfMetaFlag[b].getShortDescription());
/*     */           }
/* 215 */           else if (paramString2.equals("flag")) {
/* 216 */             stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/*     */           } else {
/*     */             
/* 219 */             stringBuffer.append(arrayOfMetaFlag[b].toString());
/*     */           } 
/*     */         } 
/*     */       } 
/* 223 */       str = stringBuffer.toString();
/*     */     } 
/* 225 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getXlated(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws SQLException {
/* 231 */     Vector<XlateInfo> vector = new Vector();
/* 232 */     ResultSet resultSet = null;
/* 233 */     Connection connection = null;
/* 234 */     PreparedStatement preparedStatement = null;
/*     */     try {
/* 236 */       connection = setupConnection();
/* 237 */       StringBuffer stringBuffer1 = new StringBuffer("select descriptionclass,valfrom,longdescription, nlsid from opicm.metadescription where valto>current timestamp and enterprise=? and descriptiontype=? and Valfrom between ? and ? and nlsid in( ");
/*     */       
/* 239 */       stringBuffer1.append(paramString5);
/* 240 */       stringBuffer1.append(") order by descriptionclass");
/*     */       
/* 242 */       preparedStatement = connection.prepareStatement(stringBuffer1.toString());
/*     */       
/* 244 */       StringBuffer stringBuffer2 = new StringBuffer();
/* 245 */       stringBuffer2.append(" select descriptionclass,valfrom,longdescription, nlsid from opicm.metadescription");
/* 246 */       stringBuffer2.append(" where valto>current timestamp and enterprise='" + paramString1 + "'");
/* 247 */       stringBuffer2.append(" and descriptiontype='" + paramString2 + "'");
/* 248 */       stringBuffer2.append(" and Valfrom between '" + paramString3 + "'");
/* 249 */       stringBuffer2.append(" and '" + paramString4 + "'");
/* 250 */       stringBuffer2.append(" and nlsid in(");
/*     */ 
/*     */       
/* 253 */       preparedStatement.setString(1, paramString1);
/* 254 */       preparedStatement.setString(2, paramString2);
/* 255 */       preparedStatement.setString(3, paramString3);
/* 256 */       preparedStatement.setString(4, paramString4);
/* 257 */       stringBuffer2.append(paramString5);
/* 258 */       stringBuffer2.append(") order by descriptionclass");
/* 259 */       paramADSABRSTATUS.addDebug("ADSXLATEABR.processThis sqlPrint= " + ADSABRSTATUS.NEWLINE + stringBuffer2.toString() + ADSABRSTATUS.NEWLINE);
/*     */       
/* 261 */       resultSet = preparedStatement.executeQuery();
/* 262 */       while (resultSet.next()) {
/* 263 */         String str1 = resultSet.getString(1);
/* 264 */         String str2 = resultSet.getString(2);
/* 265 */         String str3 = resultSet.getString(3);
/* 266 */         int i = resultSet.getInt(4);
/* 267 */         if (!ADSABRSTATUS.USERXML_OFF_LOG) {
/* 268 */           paramADSABRSTATUS.addDebug("getXlated desc:" + str1 + " valfrom:" + str2 + " longdesc:" + str3 + " nlsid:" + i);
/*     */         }
/* 270 */         vector.add(new XlateInfo(str1, str2, str3, i));
/*     */       } 
/*     */     } finally {
/*     */       
/*     */       try {
/* 275 */         if (preparedStatement != null) {
/* 276 */           preparedStatement.close();
/* 277 */           preparedStatement = null;
/*     */         } 
/* 279 */       } catch (Exception exception) {
/* 280 */         System.err.println("getXlated(), unable to close statement. " + exception);
/* 281 */         paramADSABRSTATUS.addDebug("getXlated unable to close statement. " + exception);
/*     */       } 
/* 283 */       if (resultSet != null) {
/* 284 */         resultSet.close();
/*     */       }
/* 286 */       closeConnection(connection);
/*     */     } 
/* 288 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 295 */     return "XLATE_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 304 */     return "1.2";
/*     */   }
/*     */   
/*     */   private static class XlateInfo {
/* 308 */     String descriptionclass = "@@";
/* 309 */     String valfrom = "@@";
/* 310 */     String longdesc = "@@";
/* 311 */     int nlsid = 1;
/*     */     XlateInfo(String param1String1, String param1String2, String param1String3, int param1Int) {
/* 313 */       if (param1String1 != null) {
/* 314 */         this.descriptionclass = param1String1.trim();
/*     */       }
/* 316 */       if (param1String2 != null) {
/* 317 */         this.valfrom = param1String2.trim();
/*     */       }
/* 319 */       if (param1String3 != null) {
/* 320 */         this.longdesc = param1String3.trim();
/*     */       }
/* 322 */       this.nlsid = param1Int;
/*     */     }
/*     */     void dereference() {
/* 325 */       this.descriptionclass = null;
/* 326 */       this.valfrom = null;
/* 327 */       this.longdesc = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSXLATEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */