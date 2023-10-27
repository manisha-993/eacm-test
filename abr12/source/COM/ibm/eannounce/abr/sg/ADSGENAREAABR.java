/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
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
/*     */ public class ADSGENAREAABR
/*     */   extends XMLMQAdapter
/*     */ {
/*     */   private static final String GENAREA_SQL = "select genareaname_fc as descriptionclass,genareacode,genareaname, sleorg, isactive from price.generalarea where genareatype='Country' and nlsid=1 and Valfrom BETWEEN ? AND ?";
/*     */   
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  91 */     String str1 = paramProfile1.getValOn();
/*  92 */     String str2 = paramProfile2.getValOn();
/*     */     
/*  94 */     paramADSABRSTATUS.addDebug("ADSGENAREAABR.processThis checking between " + str1 + " and " + str2);
/*     */     
/*  96 */     Vector<GenAreaInfo> vector = getGenarea(paramADSABRSTATUS, str1, str2);
/*  97 */     if (vector.size() == 0) {
/*     */       
/*  99 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", "GENERALAREA");
/*     */     } else {
/* 101 */       paramADSABRSTATUS.addDebug("ADSGENAREAABR.processThis found " + vector.size() + " GENERALAREA");
/*     */       
/* 103 */       Vector vector1 = getMQPropertiesFN();
/* 104 */       if (vector1 == null) {
/* 105 */         paramADSABRSTATUS.addDebug("ADSGENAREAABR: No MQ properties files, nothing will be generated.");
/*     */         
/* 107 */         paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "GENERALAREA");
/*     */       } else {
/* 109 */         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 110 */         DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 111 */         Document document = documentBuilder.newDocument();
/* 112 */         String str3 = "GENERALAREA_UPDATE";
/* 113 */         String str4 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str3;
/*     */ 
/*     */         
/* 116 */         Element element = document.createElementNS(str4, str3);
/*     */         
/* 118 */         document.appendChild(element);
/* 119 */         element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str4);
/*     */ 
/*     */         
/* 122 */         for (byte b = 0; b < vector.size(); b++) {
/* 123 */           GenAreaInfo genAreaInfo = vector.elementAt(b);
/* 124 */           Element element1 = document.createElement("GENAREAELEMENT");
/* 125 */           element.appendChild(element1);
/*     */           
/* 127 */           Element element2 = document.createElement("DTSOFMSG");
/* 128 */           element2.appendChild(document.createTextNode(paramProfile2.getEndOfDay()));
/* 129 */           element1.appendChild(element2);
/*     */           
/* 131 */           element2 = document.createElement("ACTIVITY");
/* 132 */           element2.appendChild(document.createTextNode(genAreaInfo.isactive ? "Update" : "Delete"));
/* 133 */           element1.appendChild(element2);
/*     */           
/* 135 */           element2 = document.createElement("DESCRIPTIONCLASS");
/* 136 */           element2.appendChild(document.createTextNode(genAreaInfo.descriptionclass));
/* 137 */           element1.appendChild(element2);
/*     */           
/* 139 */           element2 = document.createElement("GENAREACODE");
/* 140 */           element2.appendChild(document.createTextNode(genAreaInfo.genareacode));
/* 141 */           element1.appendChild(element2);
/*     */           
/* 143 */           element2 = document.createElement("GENAREANAME");
/* 144 */           element2.appendChild(document.createTextNode(genAreaInfo.genareaname));
/* 145 */           element1.appendChild(element2);
/*     */           
/* 147 */           element2 = document.createElement("SLEORG");
/* 148 */           element2.appendChild(document.createTextNode(genAreaInfo.sleorg));
/* 149 */           element1.appendChild(element2);
/*     */           
/* 151 */           genAreaInfo.dereference();
/*     */         } 
/*     */         
/* 154 */         String str5 = paramADSABRSTATUS.transformXML(this, document);
/* 155 */         paramADSABRSTATUS.addDebug("ADSGENAREAABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str5 + ADSABRSTATUS.NEWLINE);
/* 156 */         paramADSABRSTATUS.notify(this, "GENERALAREA", str5);
/*     */       } 
/*     */ 
/*     */       
/* 160 */       vector.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getGenarea(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2) throws SQLException {
/* 167 */     Vector<GenAreaInfo> vector = new Vector();
/* 168 */     ResultSet resultSet = null;
/* 169 */     Connection connection = null;
/* 170 */     PreparedStatement preparedStatement = null;
/*     */     try {
/* 172 */       connection = setupConnection();
/* 173 */       preparedStatement = connection.prepareStatement("select genareaname_fc as descriptionclass,genareacode,genareaname, sleorg, isactive from price.generalarea where genareatype='Country' and nlsid=1 and Valfrom BETWEEN ? AND ?");
/*     */ 
/*     */       
/* 176 */       preparedStatement.setString(1, paramString1);
/* 177 */       preparedStatement.setString(2, paramString2);
/*     */       
/* 179 */       resultSet = preparedStatement.executeQuery();
/* 180 */       while (resultSet.next()) {
/* 181 */         String str1 = resultSet.getString(1);
/* 182 */         String str2 = resultSet.getString(2);
/* 183 */         String str3 = resultSet.getString(3);
/* 184 */         String str4 = resultSet.getString(4);
/* 185 */         int i = resultSet.getInt(5);
/* 186 */         paramADSABRSTATUS.addDebug("getGenarea desc:" + str1 + " code:" + str2 + " name:" + str3 + " org:" + str4 + " active:" + i);
/* 187 */         vector.add(new GenAreaInfo(str1, str2, str3, str4, i));
/*     */       } 
/*     */     } finally {
/*     */       
/*     */       try {
/* 192 */         if (preparedStatement != null) {
/* 193 */           preparedStatement.close();
/* 194 */           preparedStatement = null;
/*     */         } 
/* 196 */       } catch (Exception exception) {
/* 197 */         System.err.println("getGenarea(), unable to close statement. " + exception);
/* 198 */         paramADSABRSTATUS.addDebug("getGenarea unable to close statement. " + exception);
/*     */       } 
/* 200 */       if (resultSet != null) {
/* 201 */         resultSet.close();
/*     */       }
/* 203 */       closeConnection(connection);
/*     */     } 
/* 205 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 214 */     return "1.6";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 221 */     return "GENERALAREA";
/*     */   }
/*     */   
/* 224 */   private static class GenAreaInfo { String descriptionclass = "@@";
/* 225 */     String genareacode = "@@";
/* 226 */     String genareaname = "@@";
/* 227 */     String sleorg = "@@"; boolean isactive = false;
/*     */     
/*     */     GenAreaInfo(String param1String1, String param1String2, String param1String3, String param1String4, int param1Int) {
/* 230 */       if (param1String1 != null) {
/* 231 */         this.descriptionclass = param1String1.trim();
/*     */       }
/* 233 */       if (param1String2 != null) {
/* 234 */         this.genareacode = param1String2.trim();
/*     */       }
/* 236 */       if (param1String3 != null) {
/* 237 */         this.genareaname = param1String3.trim();
/*     */       }
/* 239 */       if (param1String4 != null) {
/* 240 */         this.sleorg = param1String4.trim();
/*     */       }
/* 242 */       this.isactive = (param1Int == 1);
/*     */     }
/*     */     void dereference() {
/* 245 */       this.descriptionclass = null;
/* 246 */       this.genareacode = null;
/* 247 */       this.genareaname = null;
/* 248 */       this.sleorg = null;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSGENAREAABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */