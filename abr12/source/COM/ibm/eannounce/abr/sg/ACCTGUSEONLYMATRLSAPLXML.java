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
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
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
/*     */ public class ACCTGUSEONLYMATRLSAPLXML
/*     */   extends SAPLXMLBase
/*     */ {
/*     */   private static final String SAPVE_NAME = "SAPLVEACCTMATRL";
/* 104 */   private static final Vector SAPLXMLMAP_VCT = new Vector(); static {
/* 105 */     SAPLElem sAPLElem1 = new SAPLElem("wsnt:Notify");
/*     */     
/* 107 */     sAPLElem1.addXMLAttribute("xmlns:wsnt", "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd");
/* 108 */     sAPLElem1.addXMLAttribute("xmlns:ebi", "http://ibm.com/esh/ebi");
/*     */     
/* 110 */     SAPLXMLMAP_VCT.addElement(sAPLElem1);
/*     */     
/* 112 */     SAPLElem sAPLElem2 = new SAPLElem("wsnt:NotificationMessage");
/* 113 */     sAPLElem1.addChild(sAPLElem2);
/*     */     
/* 115 */     sAPLElem2.addChild(new SAPLTopicElem());
/* 116 */     SAPLElem sAPLElem3 = new SAPLElem("wsnt:Message");
/* 117 */     sAPLElem2.addChild(sAPLElem3);
/*     */     
/* 119 */     sAPLElem3.addChild((SAPLElem)new SAPLMessageIDElem());
/* 120 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("ebi:priority", "Normal"));
/* 121 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("PayloadFormat", "EACM_Material"));
/* 122 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("NativeCodePage", "1208"));
/*     */     
/* 124 */     SAPLElem sAPLElem4 = new SAPLElem("body");
/* 125 */     sAPLElem3.addChild(sAPLElem4);
/*     */     
/* 127 */     SAPLElem sAPLElem5 = new SAPLElem("Material");
/* 128 */     sAPLElem4.addChild(sAPLElem5);
/*     */ 
/*     */     
/* 131 */     sAPLElem5.addChild((SAPLElem)new SAPLFixedElem("EACMEntityType", "ACCTGUSEONLYMATRL"));
/* 132 */     sAPLElem5.addChild((SAPLElem)new SAPLIdElem("EACMEntityId"));
/* 133 */     sAPLElem5.addChild(new SAPLElem("Division", "ACCTGUSEONLYMATRL", "DIV", true, 1));
/* 134 */     sAPLElem5.addChild(new SAPLElem("ProductID", "ACCTGUSEONLYMATRL", "ACCTGUSEONLYMATRLID", true));
/* 135 */     sAPLElem5.addChild(new SAPLElem("ProductTypeCode", "ACCTGUSEONLYMATRL", "PRODTYPE", true, 1));
/* 136 */     sAPLElem5.addChild(new SAPLElem("ProfitCenter", "ACCTGUSEONLYMATRL", "PRFTCTR", true, 1));
/* 137 */     sAPLElem5.addChild(new SAPLElem("VendorNumber", "ACCTGUSEONLYMATRL", "VENDID", true));
/* 138 */     sAPLElem5.addChild(new SAPLElem("AccountAssignmentGroup", "ACCTGUSEONLYMATRL", "ACCTASGNGRP", true, 1));
/*     */     
/* 140 */     SAPLElem sAPLElem6 = new SAPLElem("DescriptionDataList");
/* 141 */     SAPLNLSElem sAPLNLSElem = new SAPLNLSElem("DescriptionData");
/*     */     
/* 143 */     sAPLNLSElem.addChild(new SAPLElem("MaterialDescription", "ACCTGUSEONLYMATRL", "PRCFILENAM", true));
/* 144 */     sAPLNLSElem.addChild((SAPLElem)new SAPLCHQISOElem("DescriptionLanguage"));
/*     */ 
/*     */     
/* 147 */     sAPLElem6.addChild((SAPLElem)sAPLNLSElem);
/*     */     
/* 149 */     sAPLElem5.addChild(sAPLElem6);
/*     */     
/* 151 */     sAPLElem6 = new SAPLElem("GeographyList");
/* 152 */     sAPLElem5.addChild(sAPLElem6);
/* 153 */     SAPLGEOElem sAPLGEOElem = new SAPLGEOElem("Geography", "GEODATE", "GEODATEGAA");
/* 154 */     sAPLElem6.addChild((SAPLElem)sAPLGEOElem);
/* 155 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("RFAGEO", "GENERALAREA", "RFAGEO"));
/* 156 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("Country", "GENERALAREA", "GENAREANAME"));
/* 157 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("SalesOrg", "GENERALAREA", "SLEORG"));
/* 158 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("SalesOffice", "GENERALAREA", "SLEOFFC"));
/* 159 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("ProductAnnounceDateCountry", "GEODATE", "PUBFROM"));
/* 160 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("ProductWithdrawalDate", "GEODATE", "PUBTO"));
/*     */     
/* 162 */     sAPLElem6 = new SAPLElem("PlantDataList");
/* 163 */     sAPLElem5.addChild(sAPLElem6);
/* 164 */     SAPLElem sAPLElem7 = new SAPLElem("PlantData");
/* 165 */     sAPLElem6.addChild(sAPLElem7);
/* 166 */     sAPLElem7.addChild(new SAPLElem("Plant", "ACCTGUSEONLYMATRL", "PLNTCD", true, 1));
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
/* 177 */     Vector<String> vector = new Vector(1);
/* 178 */     vector.add("OIDHMQSERIES");
/* 179 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getSaplVeName() {
/* 185 */     return "SAPLVEACCTMATRL";
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector getSAPLXMLMap() {
/* 190 */     return SAPLXMLMAP_VCT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 199 */     return "1.2";
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
/* 217 */       super("wsnt:Topic", null, null, false);
/* 218 */       addXMLAttribute("Dialect", "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete");
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
/* 241 */       Element element = param1Document.createElement(this.nodeName);
/* 242 */       addXMLAttrs(element);
/* 243 */       EntityItem entityItem = param1EntityList.getParentEntityGroup().getEntityItem(0);
/*     */       
/* 245 */       String str1 = "esh:MaterialManual/Scope/";
/* 246 */       String str2 = " ";
/*     */ 
/*     */       
/* 249 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("PRODTYPE");
/* 250 */       if (eANFlagAttribute != null) {
/*     */         
/* 252 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 253 */         for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */           
/* 255 */           if (arrayOfMetaFlag[b1].isSelected()) {
/* 256 */             str2 = arrayOfMetaFlag[b1].getFlagCode();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 262 */       element.appendChild(param1Document.createTextNode(str1 + str2));
/* 263 */       param1Element.appendChild(element);
/*     */ 
/*     */       
/* 266 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 267 */         SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 268 */         sAPLElem.addElements(param1Database, param1EntityList, param1Document, element, param1StringBuffer);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ACCTGUSEONLYMATRLSAPLXML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */