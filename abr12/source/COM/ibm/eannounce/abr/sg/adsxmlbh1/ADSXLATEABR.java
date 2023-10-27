/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class ADSXLATEABR
/*     */   extends XMLMQAdapter
/*     */ {
/*     */   private static final String ADSATTRIBUTE = "ADSATTRIBUTE";
/*     */   private static final String ADSNLSID = "ADSNLSID";
/*     */   private static final String XLATE_SQL = "select descriptionclass,valfrom,longdescription, nlsid from opicm.metadescription where valto>current timestamp and enterprise=? and descriptiontype=? and Valfrom between ? and ? and nlsid in( ";
/*     */   
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/* 100 */     String str1 = paramProfile1.getValOn();
/* 101 */     String str2 = paramProfile2.getValOn();
/*     */     
/* 103 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("ADSATTRIBUTE");
/* 104 */     if (eANMetaAttribute == null) {
/* 105 */       throw new MiddlewareException("ADSATTRIBUTE not in meta for Periodic ABR " + paramEntityItem.getKey());
/*     */     }
/*     */ 
/*     */     
/* 109 */     String str3 = getShortDescription(paramEntityItem, "ADSATTRIBUTE", "short");
/* 110 */     String str4 = getShortDescription(paramEntityItem, "ADSNLSID", "flag");
/*     */     
/* 112 */     paramADSABRSTATUS.addDebug("ADSXLATEABR.processThis checking rootEntity =" + paramEntityItem.getKey() + ",Attribute of " + "ADSATTRIBUTE" + "=" + str3 + " between " + str1 + " and " + str2);
/* 113 */     paramADSABRSTATUS.addDebug("ADSXLATEABR.processThis checking rootEntity =" + paramEntityItem.getKey() + ",Attribute of " + "ADSNLSID" + "=" + str4);
/*     */ 
/*     */     
/* 116 */     Vector<XlateInfo> vector = new Vector();
/* 117 */     if (!str4.equals("")) {
/* 118 */       vector = getXlated(paramADSABRSTATUS, paramProfile2.getEnterprise(), str3, str1, str2, str4);
/*     */     }
/*     */     
/* 121 */     if (vector.size() == 0) {
/*     */       
/* 123 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", "XLATE");
/*     */     } else {
/* 125 */       paramADSABRSTATUS.addDebug("ADSXLATEABR.processThis found " + vector.size() + " XLATE");
/*     */ 
/*     */       
/* 128 */       Vector vector1 = getPeriodicMQ(paramEntityItem);
/*     */       
/* 130 */       if (vector1 == null) {
/* 131 */         paramADSABRSTATUS.addDebug("ADSXLATEABR: No MQ properties files, nothing will be generated.");
/*     */         
/* 133 */         paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "XLATE");
/*     */       } else {
/* 135 */         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 136 */         DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 137 */         Document document = documentBuilder.newDocument();
/*     */         
/* 139 */         Element element1 = document.createElementNS("http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/XLATE_UPDATE", "XLATE_UPDATE");
/* 140 */         element1.appendChild(document.createComment("XLATE_UPDATE Version 1 Mod 0"));
/*     */         
/* 142 */         document.appendChild(element1);
/* 143 */         Element element2 = document.createElement("DTSOFMSG");
/* 144 */         element2.appendChild(document.createTextNode(paramProfile2.getEndOfDay()));
/* 145 */         element1.appendChild(element2);
/*     */ 
/*     */         
/* 148 */         for (byte b = 0; b < vector.size(); b++) {
/* 149 */           XlateInfo xlateInfo = vector.elementAt(b);
/* 150 */           Element element = document.createElement("XLATEELEMENT");
/* 151 */           element1.appendChild(element);
/*     */           
/* 153 */           element2 = document.createElement("DTSOFUPDATE");
/* 154 */           element2.appendChild(document.createTextNode(xlateInfo.valfrom));
/* 155 */           element.appendChild(element2);
/*     */           
/* 157 */           element2 = document.createElement("ATTRIBUTECODE");
/* 158 */           element2.appendChild(document.createTextNode(str3));
/* 159 */           element.appendChild(element2);
/*     */           
/* 161 */           element2 = document.createElement("NLSID");
/* 162 */           element2.appendChild(document.createTextNode("" + xlateInfo.nlsid));
/* 163 */           element.appendChild(element2);
/*     */           
/* 165 */           element2 = document.createElement("DESCRIPTIONCLASS");
/* 166 */           element2.appendChild(document.createTextNode(xlateInfo.descriptionclass));
/* 167 */           element.appendChild(element2);
/*     */           
/* 169 */           element2 = document.createElement("LONGDESCRIPTION");
/* 170 */           element2.appendChild(document.createTextNode(xlateInfo.longdesc));
/* 171 */           element.appendChild(element2);
/*     */ 
/*     */           
/* 174 */           xlateInfo.dereference();
/*     */         } 
/*     */         
/* 177 */         String str = paramADSABRSTATUS.transformXML(this, document);
/* 178 */         if (!ADSABRSTATUS.USERXML_OFF_LOG) {
/* 179 */           paramADSABRSTATUS.addDebug("ADSXLATEABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str + ADSABRSTATUS.NEWLINE);
/*     */         }
/* 181 */         paramADSABRSTATUS.notify(this, "XLATE", str, vector1);
/*     */       } 
/*     */ 
/*     */       
/* 185 */       vector.clear();
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
/* 196 */     String str = "";
/* 197 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString1);
/* 198 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */       
/* 200 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 201 */       StringBuffer stringBuffer = new StringBuffer();
/* 202 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */         
/* 204 */         if (arrayOfMetaFlag[b].isSelected()) {
/*     */           
/* 206 */           if (stringBuffer.length() > 0) {
/* 207 */             stringBuffer.append(",");
/*     */           }
/* 209 */           if (paramString2.equals("short")) {
/* 210 */             stringBuffer.append(arrayOfMetaFlag[b].getShortDescription());
/*     */           }
/* 212 */           else if (paramString2.equals("flag")) {
/* 213 */             stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/*     */           } else {
/*     */             
/* 216 */             stringBuffer.append(arrayOfMetaFlag[b].toString());
/*     */           } 
/*     */         } 
/*     */       } 
/* 220 */       str = stringBuffer.toString();
/*     */     } 
/* 222 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getXlated(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws SQLException {
/* 228 */     Vector<XlateInfo> vector = new Vector();
/* 229 */     ResultSet resultSet = null;
/* 230 */     Connection connection = null;
/* 231 */     PreparedStatement preparedStatement = null;
/*     */     try {
/* 233 */       connection = setupConnection();
/* 234 */       StringBuffer stringBuffer1 = new StringBuffer("select descriptionclass,valfrom,longdescription, nlsid from opicm.metadescription where valto>current timestamp and enterprise=? and descriptiontype=? and Valfrom between ? and ? and nlsid in( ");
/*     */       
/* 236 */       stringBuffer1.append(paramString5);
/* 237 */       stringBuffer1.append(") order by descriptionclass");
/*     */       
/* 239 */       preparedStatement = connection.prepareStatement(stringBuffer1.toString());
/*     */       
/* 241 */       StringBuffer stringBuffer2 = new StringBuffer();
/* 242 */       stringBuffer2.append(" select descriptionclass,valfrom,longdescription, nlsid from opicm.metadescription");
/* 243 */       stringBuffer2.append(" where valto>current timestamp and enterprise='" + paramString1 + "'");
/* 244 */       stringBuffer2.append(" and descriptiontype='" + paramString2 + "'");
/* 245 */       stringBuffer2.append(" and Valfrom between '" + paramString3 + "'");
/* 246 */       stringBuffer2.append(" and '" + paramString4 + "'");
/* 247 */       stringBuffer2.append(" and nlsid in(");
/*     */ 
/*     */       
/* 250 */       preparedStatement.setString(1, paramString1);
/* 251 */       preparedStatement.setString(2, paramString2);
/* 252 */       preparedStatement.setString(3, paramString3);
/* 253 */       preparedStatement.setString(4, paramString4);
/* 254 */       stringBuffer2.append(paramString5);
/* 255 */       stringBuffer2.append(") order by descriptionclass");
/* 256 */       paramADSABRSTATUS.addDebug("ADSXLATEABR.processThis sqlPrint= " + ADSABRSTATUS.NEWLINE + stringBuffer2.toString() + ADSABRSTATUS.NEWLINE);
/*     */       
/* 258 */       resultSet = preparedStatement.executeQuery();
/* 259 */       while (resultSet.next()) {
/* 260 */         String str1 = resultSet.getString(1);
/* 261 */         String str2 = resultSet.getString(2);
/* 262 */         String str3 = resultSet.getString(3);
/* 263 */         int i = resultSet.getInt(4);
/* 264 */         if (!ADSABRSTATUS.USERXML_OFF_LOG) {
/* 265 */           paramADSABRSTATUS.addDebug("getXlated desc:" + str1 + " valfrom:" + str2 + " longdesc:" + str3 + " nlsid:" + i);
/*     */         }
/* 267 */         vector.add(new XlateInfo(str1, str2, str3, i));
/*     */       } 
/*     */     } finally {
/*     */       
/*     */       try {
/* 272 */         if (preparedStatement != null) {
/* 273 */           preparedStatement.close();
/* 274 */           preparedStatement = null;
/*     */         } 
/* 276 */       } catch (Exception exception) {
/* 277 */         System.err.println("getXlated(), unable to close statement. " + exception);
/* 278 */         paramADSABRSTATUS.addDebug("getXlated unable to close statement. " + exception);
/*     */       } 
/* 280 */       if (resultSet != null) {
/* 281 */         resultSet.close();
/*     */       }
/* 283 */       closeConnection(connection);
/*     */     } 
/* 285 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 292 */     return "XLATE_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 301 */     return "1.2";
/*     */   }
/*     */   
/*     */   private static class XlateInfo {
/* 305 */     String descriptionclass = "@@";
/* 306 */     String valfrom = "@@";
/* 307 */     String longdesc = "@@";
/* 308 */     int nlsid = 1;
/*     */     XlateInfo(String param1String1, String param1String2, String param1String3, int param1Int) {
/* 310 */       if (param1String1 != null) {
/* 311 */         this.descriptionclass = param1String1.trim();
/*     */       }
/* 313 */       if (param1String2 != null) {
/* 314 */         this.valfrom = param1String2.trim();
/*     */       }
/* 316 */       if (param1String3 != null) {
/* 317 */         this.longdesc = param1String3.trim();
/*     */       }
/* 319 */       this.nlsid = param1Int;
/*     */     }
/*     */     void dereference() {
/* 322 */       this.descriptionclass = null;
/* 323 */       this.valfrom = null;
/* 324 */       this.longdesc = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSXLATEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */