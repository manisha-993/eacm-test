/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ 
/*     */ public class ADSGENAREA05ABR
/*     */   extends XMLMQAdapter
/*     */ {
/*     */   private static final String GENAREA_SQL = "select genareaname_fc as descriptionclass,genareacode,genareaname, sleorg, isactive from price.generalarea where genareatype='Country' and nlsid=1 and Valfrom BETWEEN ? AND ?";
/*     */   
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  92 */     String str1 = paramProfile1.getValOn();
/*  93 */     String str2 = paramProfile2.getValOn();
/*     */     
/*  95 */     paramADSABRSTATUS.addDebug("ADSGENAREA05ABR.processThis checking between " + str1 + " and " + str2);
/*     */     
/*  97 */     Vector<GenAreaInfo> vector = getGenarea(paramADSABRSTATUS, str1, str2);
/*  98 */     if (vector.size() == 0) {
/*     */       
/* 100 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", "GENERALAREA");
/*     */     } else {
/* 102 */       paramADSABRSTATUS.addDebug("ADSGENAREA05ABR.processThis found " + vector.size() + " GENERALAREA");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       Vector vector1 = getPeriodicMQ(paramEntityItem);
/* 108 */       if (vector1 == null) {
/* 109 */         paramADSABRSTATUS.addDebug("ADSGENAREA05ABR: No MQ properties files, nothing will be generated.");
/*     */         
/* 111 */         paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "GENERALAREA");
/*     */       } else {
/* 113 */         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 114 */         DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 115 */         Document document = documentBuilder.newDocument();
/* 116 */         String str3 = "GENERALAREA_UPDATE";
/* 117 */         String str4 = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + str3;
/*     */ 
/*     */         
/* 120 */         Element element = document.createElementNS(str4, str3);
/*     */         
/* 122 */         document.appendChild(element);
/* 123 */         element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str4);
/* 124 */         element.appendChild(document.createComment("GENERALAREA_UPDATE Version 0 Mod 5"));
/*     */         
/* 126 */         for (byte b = 0; b < vector.size(); b++) {
/* 127 */           GenAreaInfo genAreaInfo = vector.elementAt(b);
/* 128 */           Element element1 = document.createElement("GENAREAELEMENT");
/* 129 */           element.appendChild(element1);
/*     */           
/* 131 */           Element element2 = document.createElement("DTSOFMSG");
/* 132 */           element2.appendChild(document.createTextNode(paramProfile2.getEndOfDay()));
/* 133 */           element1.appendChild(element2);
/*     */           
/* 135 */           element2 = document.createElement("ACTIVITY");
/* 136 */           element2.appendChild(document.createTextNode(genAreaInfo.isactive ? "Update" : "Delete"));
/* 137 */           element1.appendChild(element2);
/*     */           
/* 139 */           element2 = document.createElement("DESCRIPTIONCLASS");
/* 140 */           element2.appendChild(document.createTextNode(genAreaInfo.descriptionclass));
/* 141 */           element1.appendChild(element2);
/*     */           
/* 143 */           element2 = document.createElement("GENAREACODE");
/* 144 */           element2.appendChild(document.createTextNode(genAreaInfo.genareacode));
/* 145 */           element1.appendChild(element2);
/*     */           
/* 147 */           element2 = document.createElement("GENAREANAME");
/* 148 */           element2.appendChild(document.createTextNode(genAreaInfo.genareaname));
/* 149 */           element1.appendChild(element2);
/*     */           
/* 151 */           element2 = document.createElement("SLEORG");
/* 152 */           element2.appendChild(document.createTextNode(genAreaInfo.sleorg));
/* 153 */           element1.appendChild(element2);
/*     */           
/* 155 */           genAreaInfo.dereference();
/*     */         } 
/*     */         
/* 158 */         String str5 = paramADSABRSTATUS.transformXML(this, document);
/* 159 */         paramADSABRSTATUS.addDebug("ADSGENAREA05ABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str5 + ADSABRSTATUS.NEWLINE);
/* 160 */         paramADSABRSTATUS.notify(this, "GENERALAREA", str5, vector1);
/*     */       } 
/*     */ 
/*     */       
/* 164 */       vector.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getGenarea(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2) throws SQLException {
/* 171 */     Vector<GenAreaInfo> vector = new Vector();
/* 172 */     ResultSet resultSet = null;
/* 173 */     Connection connection = null;
/* 174 */     PreparedStatement preparedStatement = null;
/*     */     try {
/* 176 */       connection = setupConnection();
/* 177 */       preparedStatement = connection.prepareStatement("select genareaname_fc as descriptionclass,genareacode,genareaname, sleorg, isactive from price.generalarea where genareatype='Country' and nlsid=1 and Valfrom BETWEEN ? AND ?");
/*     */ 
/*     */       
/* 180 */       preparedStatement.setString(1, paramString1);
/* 181 */       preparedStatement.setString(2, paramString2);
/*     */       
/* 183 */       resultSet = preparedStatement.executeQuery();
/* 184 */       while (resultSet.next()) {
/* 185 */         String str1 = resultSet.getString(1);
/* 186 */         String str2 = resultSet.getString(2);
/* 187 */         String str3 = resultSet.getString(3);
/* 188 */         String str4 = resultSet.getString(4);
/* 189 */         int i = resultSet.getInt(5);
/* 190 */         paramADSABRSTATUS.addDebug("getGenarea desc:" + str1 + " code:" + str2 + " name:" + str3 + " org:" + str4 + " active:" + i);
/* 191 */         vector.add(new GenAreaInfo(str1, str2, str3, str4, i));
/*     */       } 
/*     */     } finally {
/*     */       
/*     */       try {
/* 196 */         if (preparedStatement != null) {
/* 197 */           preparedStatement.close();
/* 198 */           preparedStatement = null;
/*     */         } 
/* 200 */       } catch (Exception exception) {
/* 201 */         System.err.println("getGenarea(), unable to close statement. " + exception);
/* 202 */         paramADSABRSTATUS.addDebug("getGenarea unable to close statement. " + exception);
/*     */       } 
/* 204 */       if (resultSet != null) {
/* 205 */         resultSet.close();
/*     */       }
/* 207 */       closeConnection(connection);
/*     */     } 
/* 209 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 218 */     return "1.6";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 225 */     return "GENERALAREA";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 230 */     return "ADSABRSTATUS";
/*     */   }
/*     */   
/* 233 */   private static class GenAreaInfo { String descriptionclass = "@@";
/* 234 */     String genareacode = "@@";
/* 235 */     String genareaname = "@@";
/* 236 */     String sleorg = "@@"; boolean isactive = false;
/*     */     
/*     */     GenAreaInfo(String param1String1, String param1String2, String param1String3, String param1String4, int param1Int) {
/* 239 */       if (param1String1 != null) {
/* 240 */         this.descriptionclass = param1String1.trim();
/*     */       }
/* 242 */       if (param1String2 != null) {
/* 243 */         this.genareacode = param1String2.trim();
/*     */       }
/* 245 */       if (param1String3 != null) {
/* 246 */         this.genareaname = param1String3.trim();
/*     */       }
/* 248 */       if (param1String4 != null) {
/* 249 */         this.sleorg = param1String4.trim();
/*     */       }
/* 251 */       this.isactive = (param1Int == 1);
/*     */     }
/*     */     void dereference() {
/* 254 */       this.descriptionclass = null;
/* 255 */       this.genareacode = null;
/* 256 */       this.genareaname = null;
/* 257 */       this.sleorg = null;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSGENAREA05ABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */