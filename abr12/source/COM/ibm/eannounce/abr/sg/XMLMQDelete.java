/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.GregorianCalendar;
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
/*     */ public abstract class XMLMQDelete
/*     */   extends XMLMQAdapter
/*     */ {
/*     */   private static final String DELETED_SQL_ENTITY = "select valfrom, entityid, entitytype from opicm.entity where Enterprise = ? AND EntityType = ? and Valfrom BETWEEN ? AND ? and effto<current timestamp and  Valto>current timestamp ";
/*     */   private static final String DELETED_SQL_RELATOR = "select valfrom, entityid, entitytype from opicm.relator where Enterprise = ? AND EntityType = ? and Valfrom BETWEEN ? AND ? and effto<current timestamp and  Valto>current timestamp";
/*     */   
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  67 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "ADSTYPE");
/*  68 */     String str2 = paramProfile1.getValOn();
/*  69 */     String str3 = paramProfile2.getValOn();
/*     */ 
/*     */     
/*  72 */     String str4 = "";
/*  73 */     if (str1 != null) {
/*  74 */       str4 = (String)ADSABRSTATUS.ADSTYPES_TBL.get(str1);
/*     */     }
/*     */     
/*  77 */     paramADSABRSTATUS.addDebug("XMLMQDelete.processThis checking for deleted " + str4 + " between " + str2 + " and " + str3);
/*     */     
/*  79 */     Hashtable hashtable = getDeletedRoots(paramADSABRSTATUS, paramProfile2.getEnterprise(), str4, str2, str3);
/*  80 */     if (hashtable.size() == 0) {
/*     */       
/*  82 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", str4);
/*     */     } else {
/*  84 */       paramADSABRSTATUS.addDebug("XMLMQDelete.processThis checking found " + hashtable.size() + " deleted " + str4);
/*     */       
/*  86 */       Profile profile = paramProfile2.getNewInstance(paramADSABRSTATUS.getDB());
/*  87 */       profile.setEndOfDay(str3);
/*     */ 
/*     */       
/*  90 */       Vector vector = getMQPropertiesFN();
/*  91 */       if (vector == null) {
/*  92 */         paramADSABRSTATUS.addDebug("XMLMQDelete: No MQ properties files, nothing will be generated.");
/*     */         
/*  94 */         paramADSABRSTATUS.addXMLGenMsg("NOT_REQUIRED", "Deleted " + str4);
/*     */       } else {
/*     */         
/*  97 */         for (Enumeration<Integer> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/*  98 */           Integer integer = enumeration.nextElement();
/*  99 */           String str5 = "Deleted " + str4 + integer;
/* 100 */           String str6 = (String)hashtable.get(integer);
/*     */           
/* 102 */           ISOCalendar iSOCalendar = new ISOCalendar(str6);
/* 103 */           String str7 = iSOCalendar.getAdjustedDate();
/*     */           
/* 105 */           paramADSABRSTATUS.addDebug("XMLMQDelete.processThis checking deleted " + str4 + integer + " valfrom " + str6 + " adjust valfrom " + str7);
/*     */           
/* 107 */           profile.setValOnEffOn(str7, str7);
/*     */ 
/*     */           
/* 110 */           EntityList entityList = paramADSABRSTATUS.getDB().getEntityList(profile, new ExtractActionItem(null, paramADSABRSTATUS
/* 111 */                 .getDB(), profile, getVeName()), new EntityItem[] { new EntityItem(null, profile, str4, integer
/* 112 */                   .intValue()) });
/*     */           
/* 114 */           EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/* 115 */           if (paramADSABRSTATUS.domainNeedsChecks(entityItem)) {
/* 116 */             profile.setValOn(str6);
/*     */ 
/*     */             
/* 119 */             String str = getXMLForItem(paramADSABRSTATUS, entityList);
/* 120 */             paramADSABRSTATUS.addDebug("XMLMQDelete: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + str + ADSABRSTATUS.NEWLINE);
/* 121 */             paramADSABRSTATUS.notify(this, str5, str);
/*     */           } else {
/* 123 */             paramADSABRSTATUS.addXMLGenMsg("DOMAIN_NOT_LISTED", entityItem.getKey());
/*     */           } 
/* 125 */           entityList.dereference();
/*     */         } 
/*     */       } 
/*     */       
/* 129 */       hashtable.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getXMLForItem(ADSABRSTATUS paramADSABRSTATUS, EntityList paramEntityList) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException {
/* 144 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 145 */     DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 146 */     Document document = documentBuilder.newDocument();
/* 147 */     XMLElem xMLElem = getXMLMap();
/*     */     
/* 149 */     Element element = null;
/*     */     
/* 151 */     StringBuffer stringBuffer = new StringBuffer();
/* 152 */     xMLElem.addElements(paramADSABRSTATUS.getDB(), paramEntityList, document, element, null, stringBuffer);
/*     */     
/* 154 */     paramADSABRSTATUS.addDebug("XMLMQDelete: GenXML debug: " + ADSABRSTATUS.NEWLINE + stringBuffer.toString());
/* 155 */     return paramADSABRSTATUS.transformXML(this, document);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Hashtable getDeletedRoots(ADSABRSTATUS paramADSABRSTATUS, String paramString1, String paramString2, String paramString3, String paramString4) throws SQLException {
/* 162 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 163 */     ResultSet resultSet = null;
/* 164 */     Connection connection = null;
/* 165 */     PreparedStatement preparedStatement = null;
/*     */     try {
/* 167 */       connection = setupConnection();
/*     */       
/* 169 */       if (paramString2.equals("PRODSTRUCT") || paramString2.equals("SWPRODSTRUCT") || paramString2.equals("SVCPRODSTRUCT")) {
/* 170 */         preparedStatement = connection.prepareStatement("select valfrom, entityid, entitytype from opicm.relator where Enterprise = ? AND EntityType = ? and Valfrom BETWEEN ? AND ? and effto<current timestamp and  Valto>current timestamp");
/*     */       } else {
/* 172 */         preparedStatement = connection.prepareStatement("select valfrom, entityid, entitytype from opicm.entity where Enterprise = ? AND EntityType = ? and Valfrom BETWEEN ? AND ? and effto<current timestamp and  Valto>current timestamp ");
/*     */       } 
/*     */ 
/*     */       
/* 176 */       preparedStatement.setString(1, paramString1);
/* 177 */       preparedStatement.setString(2, paramString2);
/* 178 */       preparedStatement.setString(3, paramString3);
/* 179 */       preparedStatement.setString(4, paramString4);
/*     */       
/* 181 */       resultSet = preparedStatement.executeQuery();
/* 182 */       while (resultSet.next()) {
/* 183 */         String str = resultSet.getString(1);
/* 184 */         int i = resultSet.getInt(2);
/* 185 */         hashtable.put(new Integer(i), str);
/*     */       } 
/*     */     } finally {
/*     */       
/*     */       try {
/* 190 */         if (preparedStatement != null) {
/* 191 */           preparedStatement.close();
/* 192 */           preparedStatement = null;
/*     */         } 
/* 194 */       } catch (Exception exception) {
/* 195 */         System.err.println("getDeletedRoots(), unable to close statement. " + exception);
/* 196 */         paramADSABRSTATUS.addDebug("XMLMQDelete.getDeletedRoots unable to close statement. " + exception);
/*     */       } 
/* 198 */       if (resultSet != null) {
/* 199 */         resultSet.close();
/*     */       }
/* 201 */       closeConnection(connection);
/*     */     } 
/* 203 */     return hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 212 */     return "1.3";
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ISOCalendar
/*     */   {
/* 218 */     private GregorianCalendar calendar = new GregorianCalendar();
/*     */     private String microSecStr;
/*     */     private String isoDate;
/*     */     private static final int MAGIC_NUMBER = 20;
/*     */     
/*     */     ISOCalendar(String param1String) {
/* 224 */       this.isoDate = param1String;
/* 225 */       this.calendar.set(1, Integer.parseInt(this.isoDate.substring(0, 4)));
/* 226 */       this.calendar.set(2, Integer.parseInt(this.isoDate.substring(5, 7)) - 1);
/* 227 */       this.calendar.set(5, Integer.parseInt(this.isoDate.substring(8, 10)));
/* 228 */       this.calendar.set(11, Integer.parseInt(this.isoDate.substring(11, 13)));
/* 229 */       this.calendar.set(12, Integer.parseInt(this.isoDate.substring(14, 16)));
/* 230 */       this.calendar.set(13, Integer.parseInt(this.isoDate.substring(17, 19)));
/* 231 */       this.calendar.set(14, 0);
/* 232 */       this.microSecStr = this.isoDate.substring(20);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     String getAdjustedDate() {
/* 238 */       this.calendar.add(13, -20);
/*     */       
/* 240 */       StringBuffer stringBuffer = new StringBuffer(this.calendar.get(1) + "-");
/* 241 */       int i = this.calendar.get(2) + 1;
/* 242 */       if (i < 10) stringBuffer.append("0"); 
/* 243 */       stringBuffer.append(i + "-");
/* 244 */       i = this.calendar.get(5);
/* 245 */       if (i < 10) stringBuffer.append("0"); 
/* 246 */       stringBuffer.append(i + "-");
/* 247 */       i = this.calendar.get(11);
/* 248 */       if (i < 10) stringBuffer.append("0"); 
/* 249 */       stringBuffer.append(i + ".");
/* 250 */       i = this.calendar.get(12);
/* 251 */       if (i < 10) stringBuffer.append("0"); 
/* 252 */       stringBuffer.append(i + ".");
/* 253 */       i = this.calendar.get(13);
/* 254 */       if (i < 10) stringBuffer.append("0"); 
/* 255 */       stringBuffer.append(i + "." + this.microSecStr);
/*     */       
/* 257 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\XMLMQDelete.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */