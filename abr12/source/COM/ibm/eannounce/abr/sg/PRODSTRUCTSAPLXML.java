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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PRODSTRUCTSAPLXML
/*     */   extends SAPLXMLBase
/*     */ {
/*     */   private static final String SAPVE_NAME = "SAPLVEPRODSTRUCT";
/* 238 */   private static final Vector SAPLXMLMAP_VCT = new Vector(); static {
/* 239 */     SAPLElem sAPLElem1 = new SAPLElem("wsnt:Notify");
/* 240 */     sAPLElem1.addXMLAttribute("xmlns:wsnt", "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd");
/* 241 */     sAPLElem1.addXMLAttribute("xmlns:ebi", "http://ibm.com/esh/ebi");
/* 242 */     SAPLXMLMAP_VCT.addElement(sAPLElem1);
/*     */     
/* 244 */     SAPLElem sAPLElem2 = new SAPLElem("wsnt:NotificationMessage");
/* 245 */     sAPLElem1.addChild(sAPLElem2);
/*     */     
/* 247 */     sAPLElem2.addChild(new SAPLTopicElem());
/* 248 */     SAPLElem sAPLElem3 = new SAPLElem("wsnt:Message");
/* 249 */     sAPLElem2.addChild(sAPLElem3);
/*     */     
/* 251 */     sAPLElem3.addChild((SAPLElem)new SAPLMessageIDElem());
/* 252 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("ebi:priority", "Normal"));
/* 253 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("PayloadFormat", "EACM_Material"));
/* 254 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("NativeCodePage", "1208"));
/*     */     
/* 256 */     SAPLElem sAPLElem4 = new SAPLElem("body");
/* 257 */     sAPLElem3.addChild(sAPLElem4);
/*     */     
/* 259 */     SAPLElem sAPLElem5 = new SAPLElem("Material");
/* 260 */     sAPLElem4.addChild(sAPLElem5);
/*     */ 
/*     */     
/* 263 */     sAPLElem5.addChild((SAPLElem)new SAPLFixedElem("EACMEntityType", "PRODSTRUCT"));
/* 264 */     sAPLElem5.addChild((SAPLElem)new SAPLIdElem("EACMEntityId"));
/* 265 */     sAPLElem5.addChild(new SAPLElem("CableSpecsRequiredFlag", "MODEL", "CBLSPECSREQ"));
/* 266 */     sAPLElem5.addChild(new SAPLElem("CustomerSetupAllowanceDays", "MODEL", "CUSTSETUPALLOW"));
/* 267 */     sAPLElem5.addChild(new SAPLElem("Division", "PROJ", "DIV", 1));
/* 268 */     sAPLElem5.addChild(new SAPLElem("EAEligibility", "MODEL", "EAELIG"));
/* 269 */     sAPLElem5.addChild(new SAPLElem("GSAProductionStatus", "MODEL", "PRODCD"));
/* 270 */     sAPLElem5.addChild(new SAPLElem("HourlyServiceRate", "MODEL", "HRLYSVCRATECLSCD"));
/* 271 */     sAPLElem5.addChild(new SAPLElem("IBMCreditCorp", "MODEL", "IBMCREDCORP"));
/* 272 */     sAPLElem5.addChild(new SAPLElem("LicensedInternalCode", "MODEL", "LICNSINTERCD"));
/* 273 */     sAPLElem5.addChild(new SAPLElem("LineOfBusiness", "PROJ", "LINEOFBUS"));
/* 274 */     sAPLElem5.addChild(new SAPLElem("LowEndIndicator", "MODEL", "LOWENDFLG"));
/* 275 */     sAPLElem5.addChild(new SAPLElem("MachineMaintenanceGroupCode", "MODEL", "MACHMAINTGRPCD", 1));
/* 276 */     sAPLElem5.addChild(new SAPLElem("MidrangeSystemOption", "MODEL", "MIDRNGESYSOPT"));
/* 277 */     sAPLElem5.addChild(new SAPLElem("OEMIndicator", "MODEL", "OEMINDC"));
/* 278 */     sAPLElem5.addChild(new SAPLElem("PlantOfManufacture", "MODEL", "PLNTOFMFR", 1));
/* 279 */     sAPLElem5.addChild((SAPLElem)new SAPLGEOFilteredElem("InterplantPlantCode", "GEOMOD", "INTERPLNTCD", "COUNTRYLIST", "1652", 1));
/*     */ 
/*     */ 
/*     */     
/* 283 */     sAPLElem5.addChild(new SAPLElem("ProductTypeCategory", "MODEL", "PRODTYPECATG", 1));
/* 284 */     sAPLElem5.addChild(new SAPLElem("ProductID", "MODEL", "MACHTYPEATR|MODELATR"));
/* 285 */     sAPLElem5.addChild(new SAPLElem("ProfitCenter", "MODEL", "PRFTCTR", 1));
/* 286 */     sAPLElem5.addChild(new SAPLElem("SalesManualIndicator", "MODEL", "SLEMANLVIEWABL"));
/*     */     
/* 288 */     sAPLElem5.addChild(new SAPLElem("StockCategoryCode", "MODEL", "STOCKCATCD"));
/* 289 */     sAPLElem5.addChild(new SAPLElem("VendorNumber", "MODEL", "VENDID"));
/* 290 */     sAPLElem5.addChild(new SAPLElem("VolumeDiscount", "MODEL", "VOLDISCNT"));
/* 291 */     sAPLElem5.addChild(new SAPLElem("ESAServiceLevelCategory", "MODEL", "ESASVCLEVCATG"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 344 */     SAPLNLSElem sAPLNLSElem1 = new SAPLNLSElem("DescriptionData");
/*     */     
/* 346 */     sAPLNLSElem1.addChild(new SAPLElem("MaterialDescription", "MODEL", "MKTGNAME"));
/* 347 */     sAPLNLSElem1.addChild((SAPLElem)new SAPLCHQISOElem("DescriptionLanguage"));
/* 348 */     SAPLElem sAPLElem7 = new SAPLElem("DescriptionDataList");
/*     */     
/* 350 */     sAPLElem7.addChild((SAPLElem)sAPLNLSElem1);
/*     */     
/* 352 */     sAPLElem5.addChild(sAPLElem7);
/*     */     
/* 354 */     sAPLElem7 = new SAPLElem("GeographyList");
/* 355 */     sAPLElem5.addChild(sAPLElem7);
/*     */     
/* 357 */     SAPLGEOElem sAPLGEOElem = new SAPLGEOElem("Geography", "AVAIL", "AVAILGAA");
/* 358 */     sAPLElem7.addChild((SAPLElem)sAPLGEOElem);
/* 359 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("RFAGEO", "GENERALAREA", "RFAGEO"));
/* 360 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("Country", "GENERALAREA", "GENAREANAME"));
/* 361 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("SalesOrg", "GENERALAREA", "SLEORG"));
/* 362 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("SalesOffice", "GENERALAREA", "SLEOFFC"));
/* 363 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAvailElem("AASOrderIndicator", "ORDERSYSNAME"));
/* 364 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAvailElem("AASViewableIndicator", "AASVIEWABL"));
/* 365 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOFilteredElem("LeaseRentalWithrawalDate", "AVAIL", "EFFECTIVEDATE", "AVAILTYPE", "AVT220"));
/*     */     
/* 367 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAnnElem("PackageName", "ANNNUMBER"));
/* 368 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAnnElem("AnnouncementType", "ANNTYPE"));
/* 369 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAnnElem("ProductAnnounceDateCountry", "ANNDATE"));
/* 370 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOFilteredElem("ProductWithdrawalDate", "AVAIL", "EFFECTIVEDATE", "AVAILTYPE", "149"));
/*     */ 
/*     */     
/* 373 */     sAPLElem7 = new SAPLElem("FeatureList");
/* 374 */     sAPLElem5.addChild(sAPLElem7);
/* 375 */     SAPLElem sAPLElem6 = new SAPLElem("Feature");
/* 376 */     sAPLElem7.addChild(sAPLElem6);
/* 377 */     sAPLElem6.addChild(new SAPLElem("CableSpecsRequiredFlag", "FEATURE", "CBLSPECSREQ"));
/* 378 */     sAPLElem6.addChild(new SAPLElem("EAEligibility", "FEATURE", "EAELIG"));
/* 379 */     sAPLElem6.addChild(new SAPLElem("ESAOnsite", "FEATURE", "ESAONSITE"));
/* 380 */     sAPLElem6.addChild(new SAPLElem("LowEndIndicator", "FEATURE", "LOWENDFLG"));
/* 381 */     sAPLElem6.addChild(new SAPLElem("MachineMaintenanceGroupCode", "FEATURE", "MACHMAINTGRPCD", 1));
/* 382 */     sAPLElem6.addChild(new SAPLElem("ProductID", "FEATURE", "FEATURECODE"));
/* 383 */     sAPLElem6.addChild(new SAPLElem("MOSPEligible", "FEATURE", "MOSPELIG"));
/* 384 */     sAPLElem6.addChild(new SAPLElem("PlantOfManufacture", "FEATURE", "PLNTOFMFR", 1));
/* 385 */     sAPLElem6.addChild(new SAPLElem("VolumeDiscount", "FEATURE", "VOLDISCNT"));
/* 386 */     sAPLElem6.addChild(new SAPLElem("SalesManualIndicator", "FEATURE", "SLEMANLVIEWABL"));
/* 387 */     sAPLElem6.addChild(new SAPLElem("CentralFacilityMaintenance", "FEATURE", "CNTRLFACILMAINT"));
/* 388 */     sAPLElem6.addChild(new SAPLElem("ColorSpecificationIndicator", "FEATURE", "COLRSPECINDC"));
/* 389 */     sAPLElem6.addChild(new SAPLElem("CompatibilityFeature", "FEATURE", "COMPATFEAT"));
/* 390 */     sAPLElem6.addChild(new SAPLElem("DeferredCentralFacilityMaintenance", "FEATURE", "DEFERCNTRLFACILMAINT"));
/* 391 */     sAPLElem6.addChild(new SAPLElem("DPInstallProgram", "FEATURE", "DPINSTPGM"));
/* 392 */     sAPLElem6.addChild(new SAPLElem("EducationAllowance-HighSchool", "FEATURE", "EDUCALLOWMHGHSCH"));
/* 393 */     sAPLElem6.addChild(new SAPLElem("EducationAllowance-SecondarySchool", "FEATURE", "EDUCALLOWMSECONDRYSCH"));
/* 394 */     sAPLElem6.addChild(new SAPLElem("EducationAllowance-University", "FEATURE", "EDUCALLOWMUNVRSTY"));
/* 395 */     sAPLElem6.addChild(new SAPLElem("FieldInstall-hours", "FEATURE", "FLDINSTMHR"));
/* 396 */     sAPLElem6.addChild(new SAPLElem("HourlyServiceRateClassCode", "FEATURE", "HRLYSVCRATECLSCD"));
/* 397 */     sAPLElem6.addChild(new SAPLElem("OrdersRejected", "FEATURE", "ORDREJCTED"));
/* 398 */     sAPLElem6.addChild(new SAPLElem("ProfitCenter", "FEATURE", "PRFTCTR", 1));
/* 399 */     sAPLElem6.addChild(new SAPLElem("PurchaseOnly", "FEATURE", "PURCHONLY"));
/* 400 */     sAPLElem6.addChild(new SAPLElem("SingleUse-One-timeCharge", "FEATURE", "SNGLUSEMONEMTMECHRG"));
/* 401 */     sAPLElem6.addChild(new SAPLElem("StandardMaintenance", "FEATURE", "STDMAINT"));
/* 402 */     sAPLElem6.addChild(new SAPLElem("ServiceRepairCenter", "FEATURE", "SVCREPARCTR"));
/* 403 */     sAPLElem6.addChild(new SAPLElem("TimeandMaterials", "FEATURE", "TMENMATRLS"));
/* 404 */     sAPLElem6.addChild(new SAPLElem("Voltage", "FEATURE", "VOLT"));
/* 405 */     sAPLElem6.addChild(new SAPLElem("CustomerSetupAllowanceDays", "PRODSTRUCT", "CUSTSETUPALLOW", true));
/*     */     
/* 407 */     sAPLElem7 = new SAPLElem("FeatureDescriptionDataList");
/*     */     
/* 409 */     sAPLElem6.addChild(sAPLElem7);
/*     */     
/* 411 */     SAPLNLSElem sAPLNLSElem2 = new SAPLNLSElem("FeatureDescriptionData");
/*     */     
/* 413 */     sAPLNLSElem2.addChild(new SAPLElem("FeatureDescription", "FEATURE", "MKTGNAME"));
/* 414 */     sAPLNLSElem2.addChild((SAPLElem)new SAPLCHQISOElem("FeatureDescriptionLanguage"));
/*     */     
/* 416 */     sAPLElem7.addChild((SAPLElem)sAPLNLSElem2);
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
/* 428 */     Vector<String> vector = new Vector(1);
/*     */     
/* 430 */     vector.add("ESHMQSERIES");
/* 431 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getSaplVeName() {
/* 437 */     return "SAPLVEPRODSTRUCT";
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector getSAPLXMLMap() {
/* 442 */     return SAPLXMLMAP_VCT;
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
/* 459 */     Profile profile = paramEntityList.getProfile();
/* 460 */     String str = "MODELPROJVE";
/*     */     
/* 462 */     EntityItem entityItem1 = paramEntityList.getEntityGroup("MODEL").getEntityItem(0);
/*     */ 
/*     */     
/* 465 */     EntityList entityList = this.saplAbr.getDB().getEntityList(profile, new ExtractActionItem(null, this.saplAbr
/* 466 */           .getDB(), profile, str), new EntityItem[] { new EntityItem(null, profile, "MODEL", entityItem1
/* 467 */             .getEntityID()) });
/* 468 */     addDebug("mergeLists:: Extract " + str + NEWLINE + PokUtils.outputList(entityList));
/*     */     
/* 470 */     EntityItem entityItem2 = entityList.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */     
/* 473 */     EntityGroup entityGroup1 = paramEntityList.getEntityGroup("MODELPROJA");
/* 474 */     EntityGroup entityGroup2 = entityList.getEntityGroup("MODELPROJA");
/*     */     
/* 476 */     EntityItem[] arrayOfEntityItem = entityGroup2.getEntityItemsAsArray();
/* 477 */     for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*     */       
/* 479 */       EntityItem entityItem = arrayOfEntityItem[b1];
/*     */       
/* 481 */       entityItem.removeUpLink((EANEntity)entityItem2);
/*     */       
/* 483 */       entityItem.putUpLink((EANEntity)entityItem1);
/*     */       
/* 485 */       entityGroup1.putEntityItem(entityItem);
/*     */       
/* 487 */       entityItem.reassign(entityGroup1);
/*     */       
/* 489 */       entityGroup2.removeEntityItem(entityItem);
/*     */     } 
/*     */ 
/*     */     
/* 493 */     EntityGroup entityGroup3 = paramEntityList.getEntityGroup("PROJ");
/* 494 */     EntityGroup entityGroup4 = entityList.getEntityGroup("PROJ");
/* 495 */     arrayOfEntityItem = entityGroup4.getEntityItemsAsArray(); byte b2;
/* 496 */     for (b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/*     */       
/* 498 */       EntityItem entityItem = arrayOfEntityItem[b2];
/*     */       
/* 500 */       entityGroup3.putEntityItem(entityItem);
/*     */       
/* 502 */       entityItem.reassign(entityGroup3);
/*     */       
/* 504 */       entityGroup4.removeEntityItem(entityItem);
/*     */       
/* 506 */       entityGroup4.removeEntityItem(entityItem);
/*     */     } 
/*     */ 
/*     */     
/* 510 */     entityGroup1 = paramEntityList.getEntityGroup("MODELGEOMOD");
/* 511 */     entityGroup2 = entityList.getEntityGroup("MODELGEOMOD");
/*     */     
/* 513 */     arrayOfEntityItem = entityGroup2.getEntityItemsAsArray();
/* 514 */     for (b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/*     */       
/* 516 */       EntityItem entityItem = arrayOfEntityItem[b2];
/*     */       
/* 518 */       entityItem.removeUpLink((EANEntity)entityItem2);
/*     */       
/* 520 */       entityItem.putUpLink((EANEntity)entityItem1);
/*     */       
/* 522 */       entityGroup1.putEntityItem(entityItem);
/*     */       
/* 524 */       entityItem.reassign(entityGroup1);
/*     */       
/* 526 */       entityGroup2.removeEntityItem(entityItem);
/*     */     } 
/*     */ 
/*     */     
/* 530 */     entityGroup3 = paramEntityList.getEntityGroup("GEOMOD");
/* 531 */     entityGroup4 = entityList.getEntityGroup("GEOMOD");
/* 532 */     arrayOfEntityItem = entityGroup4.getEntityItemsAsArray();
/* 533 */     for (b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/*     */       
/* 535 */       EntityItem entityItem = arrayOfEntityItem[b2];
/*     */       
/* 537 */       entityGroup3.putEntityItem(entityItem);
/*     */       
/* 539 */       entityItem.reassign(entityGroup3);
/*     */       
/* 541 */       entityGroup4.removeEntityItem(entityItem);
/*     */       
/* 543 */       entityGroup4.removeEntityItem(entityItem);
/*     */     } 
/*     */ 
/*     */     
/* 547 */     entityList.dereference();
/*     */     
/* 549 */     addDebug("mergeLists:: after merge Extract " + NEWLINE + PokUtils.outputList(paramEntityList));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 559 */     return "1.6";
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
/* 577 */       super("wsnt:Topic", null, null, false);
/* 578 */       addXMLAttribute("Dialect", "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete");
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
/* 601 */       Element element = param1Document.createElement(this.nodeName);
/* 602 */       addXMLAttrs(element);
/*     */ 
/*     */       
/* 605 */       Vector vector = SAPLGEOElem.getAvailEntities(param1EntityList.getEntityGroup("AVAIL"));
/* 606 */       String str1 = getCountryCodes(param1EntityList, vector, "AVAILGAA", param1StringBuffer);
/* 607 */       String str2 = "esh:MaterialLegacy/Nomenclature/PRODSTRUCT/Country" + str1 + "/EndCountry";
/* 608 */       element.appendChild(param1Document.createTextNode(str2));
/* 609 */       param1Element.appendChild(element);
/*     */ 
/*     */       
/* 612 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 613 */         SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 614 */         sAPLElem.addElements(param1Database, param1EntityList, param1Document, element, param1StringBuffer);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\PRODSTRUCTSAPLXML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */