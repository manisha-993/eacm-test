/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AITFEEDANNFCTRANSACTIONABR
/*     */   extends AITFEEDXML
/*     */ {
/*  52 */   private Hashtable featureCodeMktgNameTbl = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processThis(AITFEEDABRSTATUS paramAITFEEDABRSTATUS, Profile paramProfile, EntityItem paramEntityItem) throws FileNotFoundException, TransformerConfigurationException, SAXException, MiddlewareRequestException, SQLException, MiddlewareException {
/*  58 */     AITFEEDSimpleSaxXML aITFEEDSimpleSaxXML = null;
/*     */     try {
/*  60 */       String str1 = getAttributeValue(paramEntityItem, "ANNNUMBER");
/*     */       
/*  62 */       aITFEEDSimpleSaxXML = new AITFEEDSimpleSaxXML(ABRServerProperties.getOutputPath() + getXMLFileName() + paramAITFEEDABRSTATUS.getABRTime());
/*  63 */       aITFEEDSimpleSaxXML.startDocument();
/*  64 */       aITFEEDSimpleSaxXML.startElement("ANNFCTRANSACTION", aITFEEDSimpleSaxXML.createAttribute("xmlns", "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/ANNFCTRANSACTION"));
/*  65 */       aITFEEDSimpleSaxXML.addElement("ANNNUMBER", str1);
/*  66 */       aITFEEDSimpleSaxXML.addElement("INVENTORYGROUP", getAttributeValue(paramEntityItem, "INVENTORYGROUP"));
/*  67 */       aITFEEDSimpleSaxXML.addElement("ANNTYPE", getAttributeValue(paramEntityItem, "ANNTYPE"));
/*  68 */       aITFEEDSimpleSaxXML.addElement("ANNDATE", getAttributeValue(paramEntityItem, "ANNDATE"));
/*  69 */       aITFEEDSimpleSaxXML.addElement("PDHDOMAIN", getAttributeValue(paramEntityItem, "PDHDOMAIN"));
/*     */       
/*  71 */       aITFEEDSimpleSaxXML.startElement("FCTRANSACTIONTLIST");
/*  72 */       StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  78 */       String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "ANNTYPE");
/*  79 */       List<Integer> list = null;
/*  80 */       if ("19".equals(str2)) {
/*  81 */         list = searchFCTRANSACTIONEntityIdsForRfaNumber("ANNRFANUMBER", str1, paramAITFEEDABRSTATUS);
/*  82 */       } else if ("14".equals(str2)) {
/*  83 */         list = searchFCTRANSACTIONEntityIdsForRfaNumber("WDRFANUMBER", str1, paramAITFEEDABRSTATUS);
/*     */       } else {
/*     */         
/*  86 */         throw new RuntimeException("Unsupport ANNTYPE " + str2 + " for AIT feed ABR");
/*     */       } 
/*     */       
/*  89 */       if (list != null && list.size() > 0) {
/*  90 */         for (byte b = 0; b < list.size(); b++) {
/*  91 */           int i = ((Integer)list.get(b)).intValue();
/*  92 */           EntityItem entityItem = paramAITFEEDABRSTATUS.getEntityItem(paramProfile, "FCTRANSACTION", i);
/*  93 */           aITFEEDSimpleSaxXML.startElement("FCTRANSACTIONELEMENT");
/*  94 */           aITFEEDSimpleSaxXML.addElement("ANNDATE", getAttributeValue(entityItem, "ANNDATE"));
/*  95 */           aITFEEDSimpleSaxXML.addElement("FROMMACHTYPE", getAttributeValue(entityItem, "FROMMACHTYPE"));
/*  96 */           aITFEEDSimpleSaxXML.addElement("FROMMODEL", getAttributeValue(entityItem, "FROMMODEL"));
/*  97 */           String str3 = getAttributeValue(entityItem, "FROMFEATURECODE");
/*  98 */           aITFEEDSimpleSaxXML.addElement("FROMFEATURECODE", str3);
/*  99 */           aITFEEDSimpleSaxXML.addElement("FROMMKTGNAME", getFeatureMktgName(str3, entityItem, paramAITFEEDABRSTATUS, stringBuffer));
/* 100 */           aITFEEDSimpleSaxXML.addElement("TOMACHTYPE", getAttributeValue(entityItem, "TOMACHTYPE"));
/* 101 */           aITFEEDSimpleSaxXML.addElement("TOMODEL", getAttributeValue(entityItem, "TOMODEL"));
/* 102 */           String str4 = getAttributeValue(entityItem, "TOFEATURECODE");
/* 103 */           aITFEEDSimpleSaxXML.addElement("TOFEATURECODE", str4);
/* 104 */           aITFEEDSimpleSaxXML.addElement("TOMKTGNAME", getFeatureMktgName(str4, entityItem, paramAITFEEDABRSTATUS, stringBuffer));
/* 105 */           aITFEEDSimpleSaxXML.addElement("FEATURETRANSACTIONCATEGORY", getAttributeValue(entityItem, "FTCAT"));
/* 106 */           aITFEEDSimpleSaxXML.addElement("RETURNEDPARTSMES", getAttributeValue(entityItem, "RETURNEDPARTS"));
/* 107 */           aITFEEDSimpleSaxXML.endElement("FCTRANSACTIONELEMENT");
/*     */         } 
/*     */       } else {
/* 110 */         paramAITFEEDABRSTATUS.addDebug("Not found FCTRANSACTIONs for ANNDATE of ANN:" + getAttributeValue(paramEntityItem, "ANNDATE") + paramEntityItem.getKey());
/*     */       } 
/* 112 */       paramAITFEEDABRSTATUS.addDebug("GenXML debug:" + AITFEEDABRSTATUS.NEWLINE + stringBuffer.toString());
/* 113 */       aITFEEDSimpleSaxXML.endElement("FCTRANSACTIONTLIST");
/* 114 */       aITFEEDSimpleSaxXML.endElement("ANNFCTRANSACTION");
/* 115 */       aITFEEDSimpleSaxXML.endDocument();
/*     */       
/* 117 */       this.featureCodeMktgNameTbl.clear();
/* 118 */       this.featureCodeMktgNameTbl = null;
/* 119 */       list = null;
/*     */     } finally {
/* 121 */       if (aITFEEDSimpleSaxXML != null) {
/* 122 */         aITFEEDSimpleSaxXML.close();
/*     */       }
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
/*     */   private List searchFCTRANSACTIONEntityIdsForRfaNumber(String paramString1, String paramString2, AITFEEDABRSTATUS paramAITFEEDABRSTATUS) {
/* 136 */     return searchEntityIdsByAttributeCodeAndValueForText("FCTRANSACTION", paramString1, paramString2, paramAITFEEDABRSTATUS);
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
/*     */   private List searchEntityIdsByAttributeCodeAndValueForText(String paramString1, String paramString2, String paramString3, AITFEEDABRSTATUS paramAITFEEDABRSTATUS) {
/* 148 */     ArrayList<Integer> arrayList = new ArrayList();
/* 149 */     PreparedStatement preparedStatement = null;
/* 150 */     String str = "SELECT entityid FROM opicm.text WHERE entitytype='" + paramString1 + "' AND attributecode='" + paramString2 + "' AND attributevalue='" + paramString3 + "' AND valto > CURRENT TIMESTAMP AND effto > CURRENT TIMESTAMP";
/* 151 */     paramAITFEEDABRSTATUS.addDebug("searchEntityIdsByAttributeCodeAndValueForText sql: " + str);
/*     */     try {
/* 153 */       preparedStatement = paramAITFEEDABRSTATUS.getDatabase().getPDHConnection().prepareStatement(str);
/* 154 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 155 */       while (resultSet.next()) {
/* 156 */         arrayList.add(new Integer(resultSet.getInt(1)));
/*     */       }
/* 158 */     } catch (MiddlewareException middlewareException) {
/* 159 */       middlewareException.printStackTrace();
/* 160 */       paramAITFEEDABRSTATUS.addDebug("searchEntityIdsByAttributeCodeAndValueForText MiddlewareException on " + middlewareException.getMessage());
/* 161 */     } catch (SQLException sQLException) {
/* 162 */       sQLException.printStackTrace();
/* 163 */       paramAITFEEDABRSTATUS.addDebug("searchEntityIdsByAttributeCodeAndValueForText SQLException on " + sQLException.getMessage());
/*     */     } finally {
/* 165 */       if (preparedStatement != null) {
/*     */         try {
/* 167 */           preparedStatement.close();
/* 168 */         } catch (SQLException sQLException) {
/* 169 */           sQLException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 173 */     paramAITFEEDABRSTATUS.addDebug("searchEntityIdsByAttributeCodeAndValueForText entity id size: " + arrayList.size());
/* 174 */     return arrayList;
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
/*     */   private int searchFEATUREEntityIdByFeatureCodeAndDomain(String paramString1, String paramString2, AITFEEDABRSTATUS paramAITFEEDABRSTATUS) {
/* 186 */     int i = -1;
/* 187 */     PreparedStatement preparedStatement = null;
/* 188 */     String str = "SELECT entityid FROM opicm.flag WHERE entitytype='FEATURE' AND entityid IN (SELECT entityid FROM opicm.text WHERE entitytype='FEATURE' AND attributecode='FEATURECODE' AND attributevalue='" + paramString1 + "' AND valto > CURRENT TIMESTAMP AND effto > CURRENT TIMESTAMP) AND attributecode='PDHDOMAIN' AND attributevalue='" + paramString2 + "' AND valto > CURRENT TIMESTAMP AND effto > CURRENT TIMESTAMP";
/*     */     try {
/* 190 */       preparedStatement = paramAITFEEDABRSTATUS.getDatabase().getPDHConnection().prepareStatement(str);
/* 191 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 192 */       while (resultSet.next()) {
/* 193 */         i = resultSet.getInt(1);
/*     */       }
/* 195 */     } catch (MiddlewareException middlewareException) {
/* 196 */       middlewareException.printStackTrace();
/* 197 */       paramAITFEEDABRSTATUS.addDebug("searchFEATUREEntityIdFeatureCodeAndDomain MiddlewareException on " + middlewareException.getMessage());
/* 198 */     } catch (SQLException sQLException) {
/* 199 */       sQLException.printStackTrace();
/* 200 */       paramAITFEEDABRSTATUS.addDebug("searchFEATUREEntityIdFeatureCodeAndDomain SQLException on " + sQLException.getMessage());
/*     */     } finally {
/* 202 */       if (preparedStatement != null) {
/*     */         try {
/* 204 */           preparedStatement.close();
/* 205 */         } catch (SQLException sQLException) {
/* 206 */           sQLException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 210 */     paramAITFEEDABRSTATUS.addDebug("searchFEATUREEntityIdFeatureCodeAndDomain entity id: " + i + " for FEATURECODE=" + paramString1 + " PDHDOMAIN=" + paramString2);
/* 211 */     return i;
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
/*     */ 
/*     */   
/*     */   private String getFeatureMktgName(String paramString, EntityItem paramEntityItem, AITFEEDABRSTATUS paramAITFEEDABRSTATUS, StringBuffer paramStringBuffer) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 227 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN");
/* 228 */     String str2 = paramString + "|" + str1;
/* 229 */     String str3 = (String)this.featureCodeMktgNameTbl.get(str2);
/* 230 */     if (str3 == null) {
/* 231 */       int i = searchFEATUREEntityIdByFeatureCodeAndDomain(paramString, str1, paramAITFEEDABRSTATUS);
/* 232 */       if (i > 0) {
/* 233 */         EntityItem entityItem = paramAITFEEDABRSTATUS.getEntityItem(paramAITFEEDABRSTATUS.getProfile(), "FEATURE", i);
/* 234 */         str3 = getAttributeValue(entityItem, "MKTGNAME");
/*     */       } else {
/* 236 */         str3 = "";
/* 237 */         paramAITFEEDABRSTATUS.addDebug("getFeatureMktgName not found the FEATURE set MKTGNAME to empty");
/*     */       } 
/*     */     } 
/* 240 */     this.featureCodeMktgNameTbl.put(str2, str3);
/* 241 */     return str3;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 245 */     return "$Revision: 1.6 $";
/*     */   }
/*     */   
/*     */   public String getXMLFileName() {
/* 249 */     return "ANNFCTRANSACTION.xml";
/*     */   }
/*     */   
/*     */   public String getVeName() {
/* 253 */     return "dummy";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\AITFEEDANNFCTRANSACTIONABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */