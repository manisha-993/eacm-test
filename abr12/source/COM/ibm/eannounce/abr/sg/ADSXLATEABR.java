/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class ADSXLATEABR
/*     */   extends XMLMQAdapter
/*     */ {
/*     */   private static final String ADSATTRIBUTE = "ADSATTRIBUTE";
/*     */   private static final String XLATE_SQL = "select descriptionclass,valfrom,longdescription, nlsid from opicm.metadescription where valto>current timestamp and enterprise=? and descriptiontype=? and Valfrom between ? and ? and nlsid in( ";
/*     */   
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  82 */     String str1 = paramProfile1.getValOn();
/*  83 */     String str2 = paramProfile2.getValOn();
/*     */     
/*  85 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("ADSATTRIBUTE");
/*  86 */     if (eANMetaAttribute == null) {
/*  87 */       throw new MiddlewareException("ADSATTRIBUTE not in meta for Periodic ABR " + paramEntityItem.getKey());
/*     */     }
/*     */     
/*  90 */     String str3 = PokUtils.getAttributeValue(paramEntityItem, "ADSATTRIBUTE", ", ", "", false);
/*  91 */     paramADSABRSTATUS.addDebug("ADSXLATEABR.processThis checking ADSATTRIBUTE " + str3 + " between " + str1 + " and " + str2);
/*     */     
/*  93 */     Vector<XlateInfo> vector = getXlated(paramADSABRSTATUS, paramProfile2.getEnterprise(), str3, str1, str2);
/*  94 */     if (vector.size() == 0) {
/*     */       
/*  96 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", "XLATE");
/*     */     } else {
/*  98 */       paramADSABRSTATUS.addDebug("ADSXLATEABR.processThis found " + vector.size() + " XLATE");
/*     */       
/* 100 */       Vector vector1 = getMQPropertiesFN();
/* 101 */       if (vector1 == null) {
/* 102 */         paramADSABRSTATUS.addDebug("ADSXLATEABR: No MQ properties files, nothing will be generated.");
/*     */         
/* 104 */         paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "XLATE");
/*     */       } else {
/* 106 */         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 107 */         DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 108 */         Document document = documentBuilder.newDocument();
/*     */         
/* 110 */         Element element1 = document.createElement("XLATE_UPDATE");
/*     */         
/* 112 */         document.appendChild(element1);
/* 113 */         Element element2 = document.createElement("DTSOFMSG");
/* 114 */         element2.appendChild(document.createTextNode(paramProfile2.getEndOfDay()));
/* 115 */         element1.appendChild(element2);
/*     */ 
/*     */         
/* 118 */         for (byte b = 0; b < vector.size(); b++) {
/* 119 */           XlateInfo xlateInfo = vector.elementAt(b);
/* 120 */           Element element = document.createElement("XLATEELEMENT");
/* 121 */           element1.appendChild(element);
/*     */           
/* 123 */           element2 = document.createElement("DTSOFUPDATE");
/* 124 */           element2.appendChild(document.createTextNode(xlateInfo.valfrom));
/* 125 */           element.appendChild(element2);
/*     */           
/* 127 */           element2 = document.createElement("ATTRIBUTECODE");
/* 128 */           element2.appendChild(document.createTextNode(str3));
/* 129 */           element.appendChild(element2);
/*     */           
/* 131 */           element2 = document.createElement("NLSID");
/* 132 */           element2.appendChild(document.createTextNode("" + xlateInfo.nlsid));
/* 133 */           element.appendChild(element2);
/*     */           
/* 135 */           element2 = document.createElement("DESCRIPTIONCLASS");
/* 136 */           element2.appendChild(document.createTextNode(xlateInfo.descriptionclass));
/* 137 */           element.appendChild(element2);
/*     */           
/* 139 */           element2 = document.createElement("LONGDESCRIPTION");
/* 140 */           element2.appendChild(document.createTextNode(xlateInfo.longdesc));
/* 141 */           element.appendChild(element2);
/*     */ 
/*     */           
/* 144 */           xlateInfo.dereference();
/*     */         } 
/*     */         
/* 147 */         String str = paramADSABRSTATUS.transformXML(this, document);
/* 148 */         paramADSABRSTATUS.addDebug("ADSXLATEABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str + ADSABRSTATUS.NEWLINE);
/* 149 */         paramADSABRSTATUS.notify(this, "XLATE", str);
/*     */       } 
/*     */ 
/*     */       
/* 153 */       vector.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getXlated(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2, String paramString3, String paramString4) throws SQLException {
/* 160 */     Vector<XlateInfo> vector = new Vector();
/* 161 */     ResultSet resultSet = null;
/* 162 */     Connection connection = null;
/* 163 */     PreparedStatement preparedStatement = null;
/*     */     try {
/* 165 */       connection = setupConnection();
/* 166 */       StringBuffer stringBuffer = new StringBuffer("select descriptionclass,valfrom,longdescription, nlsid from opicm.metadescription where valto>current timestamp and enterprise=? and descriptiontype=? and Valfrom between ? and ? and nlsid in( ");
/* 167 */       Vector<String> vector1 = new Vector(1);
/*     */       
/* 169 */       String str = ABRServerProperties.getNLSIDs(paramADSABRSTATUS.getABRAttrCode());
/* 170 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 171 */       while (stringTokenizer.hasMoreTokens()) {
/* 172 */         String str1 = stringTokenizer.nextToken();
/* 173 */         vector1.add(str1);
/* 174 */         stringBuffer.append("?");
/* 175 */         if (stringTokenizer.hasMoreTokens()) {
/* 176 */           stringBuffer.append(",");
/*     */         }
/*     */       } 
/* 179 */       stringBuffer.append(") order by descriptionclass");
/*     */       
/* 181 */       preparedStatement = connection.prepareStatement(stringBuffer.toString());
/*     */ 
/*     */       
/* 184 */       preparedStatement.setString(1, paramString1);
/* 185 */       preparedStatement.setString(2, paramString2);
/* 186 */       preparedStatement.setString(3, paramString3);
/* 187 */       preparedStatement.setString(4, paramString4);
/* 188 */       for (byte b = 0; b < vector1.size(); b++) {
/* 189 */         preparedStatement.setInt(5 + b, Integer.parseInt(vector1.elementAt(b).toString()));
/*     */       }
/*     */       
/* 192 */       resultSet = preparedStatement.executeQuery();
/* 193 */       while (resultSet.next()) {
/* 194 */         String str1 = resultSet.getString(1);
/* 195 */         String str2 = resultSet.getString(2);
/* 196 */         String str3 = resultSet.getString(3);
/* 197 */         int i = resultSet.getInt(4);
/* 198 */         paramADSABRSTATUS.addDebug("getXlated desc:" + str1 + " valfrom:" + str2 + " longdesc:" + str3 + " nlsid:" + i);
/* 199 */         vector.add(new XlateInfo(str1, str2, str3, i));
/*     */       } 
/*     */       
/* 202 */       vector1.clear();
/*     */     } finally {
/*     */       
/*     */       try {
/* 206 */         if (preparedStatement != null) {
/* 207 */           preparedStatement.close();
/* 208 */           preparedStatement = null;
/*     */         } 
/* 210 */       } catch (Exception exception) {
/* 211 */         System.err.println("getXlated(), unable to close statement. " + exception);
/* 212 */         paramADSABRSTATUS.addDebug("getXlated unable to close statement. " + exception);
/*     */       } 
/* 214 */       if (resultSet != null) {
/* 215 */         resultSet.close();
/*     */       }
/* 217 */       closeConnection(connection);
/*     */     } 
/* 219 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 226 */     return "XLATEMETA";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 235 */     return "1.4";
/*     */   }
/*     */   
/*     */   private static class XlateInfo {
/* 239 */     String descriptionclass = "@@";
/* 240 */     String valfrom = "@@";
/* 241 */     String longdesc = "@@";
/* 242 */     int nlsid = 1;
/*     */     XlateInfo(String param1String1, String param1String2, String param1String3, int param1Int) {
/* 244 */       if (param1String1 != null) {
/* 245 */         this.descriptionclass = param1String1.trim();
/*     */       }
/* 247 */       if (param1String2 != null) {
/* 248 */         this.valfrom = param1String2.trim();
/*     */       }
/* 250 */       if (param1String3 != null) {
/* 251 */         this.longdesc = param1String3.trim();
/*     */       }
/* 253 */       this.nlsid = param1Int;
/*     */     }
/*     */     void dereference() {
/* 256 */       this.descriptionclass = null;
/* 257 */       this.valfrom = null;
/* 258 */       this.longdesc = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSXLATEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */