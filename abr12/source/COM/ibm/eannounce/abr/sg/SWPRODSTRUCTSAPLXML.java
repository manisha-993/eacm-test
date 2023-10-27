/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.SAPLCHQISOElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLGEOAnnElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLGEOAvailElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLGEOElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLGEOFilteredElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLIdElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLItemElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLMessageIDElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLNLSElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANEntity;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SWPRODSTRUCTSAPLXML
/*     */   extends SAPLXMLBase
/*     */ {
/*     */   private static final String SAPVE_NAME = "SAPLVESWPRODSTRUCT";
/* 205 */   private static final Vector SAPLXMLMAP_VCT = new Vector(); static {
/* 206 */     SAPLElem sAPLElem1 = new SAPLElem("wsnt:Notify");
/* 207 */     sAPLElem1.addXMLAttribute("xmlns:wsnt", "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd");
/* 208 */     sAPLElem1.addXMLAttribute("xmlns:ebi", "http://ibm.com/esh/ebi");
/* 209 */     SAPLXMLMAP_VCT.addElement(sAPLElem1);
/*     */     
/* 211 */     SAPLElem sAPLElem2 = new SAPLElem("wsnt:NotificationMessage");
/* 212 */     sAPLElem1.addChild(sAPLElem2);
/*     */     
/* 214 */     sAPLElem2.addChild(new SAPLTopicElem());
/* 215 */     SAPLElem sAPLElem3 = new SAPLElem("wsnt:Message");
/* 216 */     sAPLElem2.addChild(sAPLElem3);
/*     */     
/* 218 */     sAPLElem3.addChild((SAPLElem)new SAPLMessageIDElem());
/* 219 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("ebi:priority", "Normal"));
/* 220 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("PayloadFormat", "EACM_Material"));
/* 221 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("NativeCodePage", "1208"));
/*     */     
/* 223 */     SAPLElem sAPLElem4 = new SAPLElem("body");
/* 224 */     sAPLElem3.addChild(sAPLElem4);
/*     */     
/* 226 */     SAPLElem sAPLElem5 = new SAPLElem("Material");
/* 227 */     sAPLElem4.addChild(sAPLElem5);
/*     */ 
/*     */     
/* 230 */     sAPLElem5.addChild((SAPLElem)new SAPLFixedElem("EACMEntityType", "SWPRODSTRUCT"));
/* 231 */     sAPLElem5.addChild((SAPLElem)new SAPLIdElem("EACMEntityId"));
/* 232 */     sAPLElem5.addChild(new SAPLElem("CableSpecsRequiredFlag", "MODEL", "CBLSPECSREQ"));
/* 233 */     sAPLElem5.addChild(new SAPLElem("CustomerSetupAllowanceDays", "MODEL", "CUSTSETUPALLOW"));
/* 234 */     sAPLElem5.addChild(new SAPLElem("Division", "PROJ", "DIV", 1));
/* 235 */     sAPLElem5.addChild(new SAPLElem("EAEligibility", "MODEL", "EAELIG"));
/* 236 */     sAPLElem5.addChild(new SAPLElem("GSAProductionStatus", "MODEL", "PRODCD"));
/* 237 */     sAPLElem5.addChild(new SAPLElem("HourlyServiceRate", "MODEL", "HRLYSVCRATECLSCD"));
/* 238 */     sAPLElem5.addChild(new SAPLElem("IBMCreditCorp", "MODEL", "IBMCREDCORP"));
/* 239 */     sAPLElem5.addChild(new SAPLElem("LicensedInternalCode", "MODEL", "LICNSINTERCD"));
/* 240 */     sAPLElem5.addChild(new SAPLElem("LineOfBusiness", "PROJ", "LINEOFBUS"));
/* 241 */     sAPLElem5.addChild(new SAPLElem("LowEndIndicator", "MODEL", "LOWENDFLG"));
/* 242 */     sAPLElem5.addChild(new SAPLElem("MachineMaintenanceGroupCode", "MODEL", "MACHMAINTGRPCD", 1));
/* 243 */     sAPLElem5.addChild(new SAPLElem("MidrangeSystemOption", "MODEL", "MIDRNGESYSOPT"));
/* 244 */     sAPLElem5.addChild(new SAPLElem("OEMIndicator", "MODEL", "OEMINDC"));
/* 245 */     sAPLElem5.addChild(new SAPLElem("PlantOfManufacture", "MODEL", "PLNTOFMFR", 1));
/* 246 */     sAPLElem5.addChild((SAPLElem)new SAPLGEOFilteredElem("InterplantPlantCode", "GEOMOD", "INTERPLNTCD", "COUNTRYLIST", "1652", 1));
/*     */ 
/*     */     
/* 249 */     sAPLElem5.addChild(new SAPLElem("ProductTypeCategory", "MODEL", "PRODTYPECATG", 1));
/* 250 */     sAPLElem5.addChild(new SAPLElem("ProductID", "MODEL", "MACHTYPEATR|MODELATR"));
/* 251 */     sAPLElem5.addChild(new SAPLElem("ProfitCenter", "MODEL", "PRFTCTR", 1));
/* 252 */     sAPLElem5.addChild(new SAPLElem("SalesManualIndicator", "MODEL", "SLEMANLVIEWABL"));
/* 253 */     sAPLElem5.addChild(new SAPLElem("VendorNumber", "MODEL", "VENDID"));
/* 254 */     sAPLElem5.addChild(new SAPLElem("VolumeDiscount", "MODEL", "VOLDISCNT"));
/* 255 */     sAPLElem5.addChild(new SAPLElem("ESAServiceLevelCategory", "MODEL", "ESASVCLEVCATG"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     SAPLNLSElem sAPLNLSElem1 = new SAPLNLSElem("DescriptionData");
/*     */     
/* 310 */     sAPLNLSElem1.addChild(new SAPLElem("MaterialDescription", "MODEL", "MKTGNAME"));
/* 311 */     sAPLNLSElem1.addChild((SAPLElem)new SAPLCHQISOElem("DescriptionLanguage"));
/* 312 */     SAPLElem sAPLElem7 = new SAPLElem("DescriptionDataList");
/*     */     
/* 314 */     sAPLElem7.addChild((SAPLElem)sAPLNLSElem1);
/*     */     
/* 316 */     sAPLElem5.addChild(sAPLElem7);
/*     */     
/* 318 */     sAPLElem7 = new SAPLElem("GeographyList");
/* 319 */     sAPLElem5.addChild(sAPLElem7);
/*     */     
/* 321 */     SAPLGEOElem sAPLGEOElem = new SAPLGEOElem("Geography", "AVAIL", "AVAILGAA");
/* 322 */     sAPLElem7.addChild((SAPLElem)sAPLGEOElem);
/* 323 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("RFAGEO", "GENERALAREA", "RFAGEO"));
/* 324 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("Country", "GENERALAREA", "GENAREANAME"));
/* 325 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("SalesOrg", "GENERALAREA", "SLEORG"));
/* 326 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("SalesOffice", "GENERALAREA", "SLEOFFC"));
/* 327 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAvailElem("AASOrderIndicator", "ORDERSYSNAME"));
/* 328 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAvailElem("AASViewableIndicator", "AASVIEWABL"));
/* 329 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOFilteredElem("LeaseRentalWithrawalDate", "AVAIL", "EFFECTIVEDATE", "AVAILTYPE", "AVT220"));
/*     */     
/* 331 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAnnElem("PackageName", "ANNNUMBER"));
/* 332 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAnnElem("AnnouncementType", "ANNTYPE"));
/* 333 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAnnElem("ProductAnnounceDateCountry", "ANNDATE"));
/* 334 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOFilteredElem("ProductWithdrawalDate", "AVAIL", "EFFECTIVEDATE", "AVAILTYPE", "149"));
/*     */ 
/*     */     
/* 337 */     sAPLElem7 = new SAPLElem("FeatureList");
/* 338 */     sAPLElem5.addChild(sAPLElem7);
/* 339 */     SAPLElem sAPLElem6 = new SAPLElem("Feature");
/* 340 */     sAPLElem7.addChild(sAPLElem6);
/* 341 */     sAPLElem6.addChild(new SAPLElem("LicensedInternalCode", "SWFEATURE", "LICNSINTERCD"));
/* 342 */     sAPLElem6.addChild(new SAPLElem("ProductID", "SWFEATURE", "FEATURECODE"));
/* 343 */     sAPLElem6.addChild(new SAPLElem("MOSPEligible", "SWFEATURE", "MOSPELIG"));
/* 344 */     sAPLElem6.addChild(new SAPLElem("VolumeDiscount", "SWFEATURE", "VOLDISCNT"));
/* 345 */     sAPLElem6.addChild(new SAPLElem("SalesManualIndicator", "SWFEATURE", "SLEMANLVIEWABL"));
/* 346 */     sAPLElem6.addChild(new SAPLElem("ProfitCenter", "SWFEATURE", "PRFTCTR", 1));
/*     */     
/* 348 */     sAPLElem7 = new SAPLElem("FeatureDescriptionDataList");
/*     */     
/* 350 */     sAPLElem6.addChild(sAPLElem7);
/*     */     
/* 352 */     SAPLNLSElem sAPLNLSElem2 = new SAPLNLSElem("FeatureDescriptionData");
/*     */     
/* 354 */     sAPLNLSElem2.addChild(new SAPLElem("FeatureDescription", "SWPRODSTRUCT", "MKTGNAME", true));
/* 355 */     sAPLNLSElem2.addChild((SAPLElem)new SAPLCHQISOElem("FeatureDescriptionLanguage"));
/*     */     
/* 357 */     sAPLElem7.addChild((SAPLElem)sAPLNLSElem2);
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
/*     */   protected Vector getMQPropertiesFN() {
/* 369 */     Vector<String> vector = new Vector(1);
/*     */     
/* 371 */     vector.add("ESHMQSERIES");
/* 372 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getSaplVeName() {
/* 378 */     return "SAPLVESWPRODSTRUCT";
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector getSAPLXMLMap() {
/* 383 */     return SAPLXMLMAP_VCT;
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
/*     */   
/*     */   protected void mergeLists(EntityList paramEntityList) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
/* 400 */     Profile profile = paramEntityList.getProfile();
/* 401 */     String str = "MODELPROJVE";
/*     */     
/* 403 */     EntityItem entityItem1 = paramEntityList.getEntityGroup("MODEL").getEntityItem(0);
/*     */ 
/*     */     
/* 406 */     EntityList entityList = this.saplAbr.getDB().getEntityList(profile, new ExtractActionItem(null, this.saplAbr
/* 407 */           .getDB(), profile, str), new EntityItem[] { new EntityItem(null, profile, "MODEL", entityItem1
/* 408 */             .getEntityID()) });
/* 409 */     addDebug("mergeLists:: Extract " + str + NEWLINE + PokUtils.outputList(entityList));
/*     */     
/* 411 */     EntityItem entityItem2 = entityList.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */     
/* 414 */     EntityGroup entityGroup1 = paramEntityList.getEntityGroup("MODELPROJA");
/* 415 */     EntityGroup entityGroup2 = entityList.getEntityGroup("MODELPROJA");
/*     */     
/* 417 */     EntityItem[] arrayOfEntityItem = entityGroup2.getEntityItemsAsArray();
/* 418 */     for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*     */       
/* 420 */       EntityItem entityItem = arrayOfEntityItem[b1];
/*     */       
/* 422 */       entityItem.removeUpLink((EANEntity)entityItem2);
/*     */       
/* 424 */       entityItem.putUpLink((EANEntity)entityItem1);
/*     */       
/* 426 */       entityGroup1.putEntityItem(entityItem);
/*     */       
/* 428 */       entityItem.reassign(entityGroup1);
/*     */       
/* 430 */       entityGroup2.removeEntityItem(entityItem);
/*     */     } 
/*     */ 
/*     */     
/* 434 */     EntityGroup entityGroup3 = paramEntityList.getEntityGroup("PROJ");
/* 435 */     EntityGroup entityGroup4 = entityList.getEntityGroup("PROJ");
/* 436 */     arrayOfEntityItem = entityGroup4.getEntityItemsAsArray(); byte b2;
/* 437 */     for (b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/*     */       
/* 439 */       EntityItem entityItem = arrayOfEntityItem[b2];
/*     */       
/* 441 */       entityGroup3.putEntityItem(entityItem);
/*     */       
/* 443 */       entityItem.reassign(entityGroup3);
/*     */       
/* 445 */       entityGroup4.removeEntityItem(entityItem);
/*     */     } 
/*     */ 
/*     */     
/* 449 */     entityGroup1 = paramEntityList.getEntityGroup("MODELGEOMOD");
/* 450 */     entityGroup2 = entityList.getEntityGroup("MODELGEOMOD");
/*     */     
/* 452 */     arrayOfEntityItem = entityGroup2.getEntityItemsAsArray();
/* 453 */     for (b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/*     */       
/* 455 */       EntityItem entityItem = arrayOfEntityItem[b2];
/*     */       
/* 457 */       entityItem.removeUpLink((EANEntity)entityItem2);
/*     */       
/* 459 */       entityItem.putUpLink((EANEntity)entityItem1);
/*     */       
/* 461 */       entityGroup1.putEntityItem(entityItem);
/*     */       
/* 463 */       entityItem.reassign(entityGroup1);
/*     */       
/* 465 */       entityGroup2.removeEntityItem(entityItem);
/*     */     } 
/*     */ 
/*     */     
/* 469 */     entityGroup3 = paramEntityList.getEntityGroup("GEOMOD");
/* 470 */     entityGroup4 = entityList.getEntityGroup("GEOMOD");
/* 471 */     arrayOfEntityItem = entityGroup4.getEntityItemsAsArray();
/* 472 */     for (b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/*     */       
/* 474 */       EntityItem entityItem = arrayOfEntityItem[b2];
/*     */       
/* 476 */       entityGroup3.putEntityItem(entityItem);
/*     */       
/* 478 */       entityItem.reassign(entityGroup3);
/*     */       
/* 480 */       entityGroup4.removeEntityItem(entityItem);
/*     */       
/* 482 */       entityGroup4.removeEntityItem(entityItem);
/*     */     } 
/*     */ 
/*     */     
/* 486 */     entityList.dereference();
/*     */     
/* 488 */     addDebug("mergeLists:: after merge Extract " + NEWLINE + PokUtils.outputList(paramEntityList));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 498 */     return "1.6";
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
/* 516 */       super("wsnt:Topic", null, null, false);
/* 517 */       addXMLAttribute("Dialect", "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete");
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
/* 540 */       Element element = param1Document.createElement(this.nodeName);
/* 541 */       addXMLAttrs(element);
/*     */ 
/*     */       
/* 544 */       Vector vector = SAPLGEOElem.getAvailEntities(param1EntityList.getEntityGroup("AVAIL"));
/* 545 */       String str1 = getCountryCodes(param1EntityList, vector, "AVAILGAA", param1StringBuffer);
/* 546 */       String str2 = "esh:MaterialLegacy/Nomenclature/SWPRODSTRUCT/Country" + str1 + "/EndCountry";
/* 547 */       element.appendChild(param1Document.createTextNode(str2));
/* 548 */       param1Element.appendChild(element);
/*     */ 
/*     */       
/* 551 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 552 */         SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 553 */         sAPLElem.addElements(param1Database, param1EntityList, param1Document, element, param1StringBuffer);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SWPRODSTRUCTSAPLXML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */