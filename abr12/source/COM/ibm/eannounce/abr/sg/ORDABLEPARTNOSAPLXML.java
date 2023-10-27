/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.SAPLCHQISOElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLGEOElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLIdElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLItemElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLMessageIDElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLNLSElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
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
/*     */ public class ORDABLEPARTNOSAPLXML
/*     */   extends SAPLXMLBase
/*     */ {
/*     */   private static final String SAPVE_NAME = "SAPLVEORDPARTN";
/* 105 */   private static final Vector SAPLXMLMAP_VCT = new Vector(); static {
/* 106 */     SAPLElem sAPLElem1 = new SAPLElem("wsnt:Notify");
/* 107 */     sAPLElem1.addXMLAttribute("xmlns:wsnt", "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd");
/* 108 */     sAPLElem1.addXMLAttribute("xmlns:ebi", "http://ibm.com/esh/ebi");
/* 109 */     SAPLXMLMAP_VCT.addElement(sAPLElem1);
/*     */     
/* 111 */     SAPLElem sAPLElem2 = new SAPLElem("wsnt:NotificationMessage");
/* 112 */     sAPLElem1.addChild(sAPLElem2);
/*     */     
/* 114 */     sAPLElem2.addChild(new SAPLTopicElem());
/* 115 */     SAPLElem sAPLElem3 = new SAPLElem("wsnt:Message");
/* 116 */     sAPLElem2.addChild(sAPLElem3);
/*     */     
/* 118 */     sAPLElem3.addChild((SAPLElem)new SAPLMessageIDElem());
/* 119 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("ebi:priority", "Normal"));
/* 120 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("PayloadFormat", "EACM_Material"));
/* 121 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("NativeCodePage", "1208"));
/*     */     
/* 123 */     SAPLElem sAPLElem4 = new SAPLElem("body");
/* 124 */     sAPLElem3.addChild(sAPLElem4);
/*     */     
/* 126 */     SAPLElem sAPLElem5 = new SAPLElem("Material");
/* 127 */     sAPLElem4.addChild(sAPLElem5);
/*     */ 
/*     */     
/* 130 */     sAPLElem5.addChild((SAPLElem)new SAPLFixedElem("EACMEntityType", "ORDABLEPARTNO"));
/* 131 */     sAPLElem5.addChild((SAPLElem)new SAPLIdElem("EACMEntityId"));
/* 132 */     sAPLElem5.addChild(new SAPLElem("Division", "ORDABLEPARTNO", "DIV", true, 1));
/* 133 */     sAPLElem5.addChild(new SAPLElem("LowEndIndicator", "ORDABLEPARTNO", "LOWENDFLG", true));
/* 134 */     sAPLElem5.addChild(new SAPLElem("PlantOfManufacture", "ORDABLEPARTNO", "PLNTOFMFR", true, 1));
/*     */ 
/*     */     
/* 137 */     sAPLElem5.addChild(new SAPLElem("ProductGroupingCode", "ORDERABLEPARTNO", "PRODGRPCD", true));
/* 138 */     sAPLElem5.addChild(new SAPLElem("ProductTypeCategory", "ORDERABLEPARTNO", "PRODTYPECATG", true, 1));
/* 139 */     sAPLElem5.addChild(new SAPLElem("ProductID", "ORDABLEPARTNO", "ORDABLEPARTNOATR", true));
/* 140 */     sAPLElem5.addChild(new SAPLElem("ProductTypeCode", "ORDERABLEPARTNO", "PRODTYPE", true, 1));
/* 141 */     sAPLElem5.addChild(new SAPLElem("ProfitCenter", "ORDERABLEPARTNO", "PRFTCTR", true, 1));
/* 142 */     sAPLElem5.addChild(new SAPLElem("SalesManualIndicator", "ORDABLEPARTNO", "SLEMANLVIEWABL", true));
/*     */     
/* 144 */     sAPLElem5.addChild(new SAPLElem("StockCategoryCode", "ORDERABLEPARTNO", "STOCKCATCD", true));
/* 145 */     sAPLElem5.addChild(new SAPLElem("VendorNumber", "ORDERABLEPARTNO", "VENDID", true));
/*     */     
/* 147 */     SAPLElem sAPLElem6 = new SAPLElem("DescriptionDataList");
/* 148 */     sAPLElem5.addChild(sAPLElem6);
/*     */     
/* 150 */     SAPLNLSElem sAPLNLSElem = new SAPLNLSElem("DescriptionData");
/* 151 */     sAPLElem6.addChild((SAPLElem)sAPLNLSElem);
/*     */     
/* 153 */     sAPLNLSElem.addChild(new SAPLElem("MaterialDescription", "ORDABLEPARTNO", "PRCFILENAM", true));
/* 154 */     sAPLNLSElem.addChild((SAPLElem)new SAPLCHQISOElem("DescriptionLanguage"));
/*     */     
/* 156 */     sAPLElem6 = new SAPLElem("GeographyList");
/* 157 */     sAPLElem5.addChild(sAPLElem6);
/*     */     
/* 159 */     SAPLGEOElem sAPLGEOElem = new SAPLGEOElem("Geography", "GEODATE", "GEODATEGAA");
/* 160 */     sAPLElem6.addChild((SAPLElem)sAPLGEOElem);
/* 161 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("RFAGEO", "GENERALAREA", "RFAGEO"));
/* 162 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("Country", "GENERALAREA", "GENAREANAME"));
/* 163 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("SalesOrg", "GENERALAREA", "SLEORG"));
/* 164 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("SalesOffice", "GENERALAREA", "SLEOFFC"));
/* 165 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("ProductAnnounceDateCountry", "GEODATE", "PUBFROM"));
/* 166 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("ProductWithdrawalDate", "GEODATE", "PUBTO"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getMQPropertiesFN() {
/* 177 */     Vector<String> vector = new Vector(2);
/* 178 */     vector.add("OIDHMQSERIES");
/* 179 */     vector.add("ESHMQSERIES");
/* 180 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getSaplVeName() {
/* 186 */     return "SAPLVEORDPARTN";
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector getSAPLXMLMap() {
/* 191 */     return SAPLXMLMAP_VCT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 200 */     return "1.2";
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
/*     */   static class SAPLTopicElem
/*     */     extends SAPLElem
/*     */   {
/*     */     SAPLTopicElem() {
/* 218 */       super("wsnt:Topic", null, null, false);
/* 219 */       addXMLAttribute("Dialect", "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addElements(Database param1Database, EntityList param1EntityList, Document param1Document, Element param1Element, StringBuffer param1StringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 242 */       Element element = param1Document.createElement(this.nodeName);
/* 243 */       addXMLAttrs(element);
/*     */       
/* 245 */       Vector vector = getEntities(param1EntityList.getEntityGroup("GEODATE"));
/* 246 */       String str1 = getCountryCodes(param1EntityList, vector, "GEODATEGAA", param1StringBuffer);
/* 247 */       String str2 = "esh:MaterialLegacy/Nomenclature/ORDABLEPARTNO/Country" + str1 + "/EndCountry";
/* 248 */       element.appendChild(param1Document.createTextNode(str2));
/* 249 */       param1Element.appendChild(element);
/*     */ 
/*     */       
/* 252 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 253 */         SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 254 */         sAPLElem.addElements(param1Database, param1EntityList, param1Document, element, param1StringBuffer);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ORDABLEPARTNOSAPLXML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */