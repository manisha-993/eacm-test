/*      */ package COM.ibm.eannounce.abr.sg.rfc;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
/*      */ import com.ibm.pprds.epimshw.HWPIMSException;
/*      */ import com.ibm.pprds.epimshw.HWPIMSNotFoundInMastException;
/*      */ import com.ibm.pprds.epimshw.util.ConfigManager;
/*      */ import com.ibm.rdh.chw.entity.BasicMaterialFromSAP;
/*      */ import com.ibm.rdh.chw.entity.CHWAnnouncement;
/*      */ import com.ibm.rdh.chw.entity.CHWGeoAnn;
/*      */ import com.ibm.rdh.chw.entity.DepData;
/*      */ import com.ibm.rdh.chw.entity.LifecycleData;
/*      */ import com.ibm.rdh.chw.entity.PlannedSalesStatus;
/*      */ import com.ibm.rdh.chw.entity.RevProfile;
/*      */ import com.ibm.rdh.chw.entity.TypeModel;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.IOException;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RFCMODELABR
/*      */   extends RfcAbrAdapter
/*      */ {
/*      */   private boolean isTMPromoted = false;
/*      */   private boolean isTMChanged = false;
/*      */   private boolean isTMGeoPromoted = false;
/*      */   private boolean isTMGeoChanged = false;
/*      */   private boolean needUpdateParkingTable = false;
/*  203 */   private Hashtable spItemCateg = new Hashtable<>();
/*      */ 
/*      */ 
/*      */   
/*  207 */   private String warrPeriod = null;
/*  208 */   private Vector configProfTypes = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  213 */   private static List<String> modelMarkChangedAttrs = new ArrayList<>(); static {
/*  214 */     modelMarkChangedAttrs.add("INVNAME");
/*  215 */     modelMarkChangedAttrs.add("INSTALL");
/*  216 */     modelMarkChangedAttrs.add("LICNSINTERCD");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RFCMODELABR(RFCABRSTATUS paramRFCABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
/*  223 */     super(paramRFCABRSTATUS);
/*      */   }
/*      */   
/*      */   public void processThis() throws RfcAbrException, HWPIMSAbnormalException, Exception {
/*  227 */     this.abr.addDebug("Start processThis()");
/*  228 */     EntityList entityList = null;
/*  229 */     EntityItem entityItem = null;
/*  230 */     boolean bool = false;
/*      */ 
/*      */     
/*      */     try {
/*  234 */       entityItem = getRooEntityItem();
/*      */       
/*  236 */       if (!"0020".equals(getAttributeFlagValue(entityItem, "STATUS"))) {
/*  237 */         throw new RfcAbrException("The status is not final, it will not promote this " + entityItem.getKey());
/*      */       }
/*      */       
/*  240 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "MODELAVAIL", "AVAIL");
/*  241 */       Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/*  242 */       Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "149");
/*  243 */       this.abr.addDebug("MODELAVAIL all availVct: " + vector1.size() + " plannedavail: " + vector2.size() + " Last Order avail " + vector3.size());
/*      */       
/*  245 */       Vector vector4 = PokUtils.getAllLinkedEntities(entityItem, "MODELMACHINETYPEA", "MACHTYPE");
/*  246 */       if (vector4.size() == 0) {
/*  247 */         throw new RfcAbrException("There is no MACHTYPE entity for " + entityItem.getKey());
/*      */       }
/*  249 */       String str1 = getAttributeFlagValue(entityItem, "MACHTYPEATR");
/*  250 */       String str2 = getAttributeValue(entityItem, "MODELATR");
/*  251 */       String str3 = str1 + "-" + str2;
/*      */ 
/*      */       
/*  254 */       TypeModel typeModel = new TypeModel();
/*  255 */       typeModel.setType(str1);
/*  256 */       typeModel.setModel(str2);
/*  257 */       String str4 = getDiv(entityItem);
/*  258 */       typeModel.setDiv(str4);
/*      */ 
/*      */       
/*  261 */       typeModel.setEanUPCCode("");
/*  262 */       typeModel.setProductHierarchy(getProdHireCd(entityItem));
/*  263 */       typeModel.setDescription(getAttributeValue(entityItem, "INVNAME"));
/*      */ 
/*      */       
/*  266 */       typeModel.setVendorID("");
/*  267 */       typeModel.setAcctAsgnGrp(getAttributeFlagValue(entityItem, "ACCTASGNGRP"));
/*      */       
/*  269 */       String str5 = getProfitCenter(str4);
/*  270 */       typeModel.setProfitCenter(str5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  277 */       typeModel.setCustomerSetup("CIF".equals(getAttributeValue(entityItem, "INSTALL")));
/*  278 */       typeModel.setLicenseCode("Yes".equals(getAttributeValue(entityItem, "LICNSINTERCD")));
/*  279 */       typeModel.setSystemType(getAttributeValue(entityItem, "SYSTEMTYPE"));
/*  280 */       typeModel.setCPU("S00010".equals(getAttributeFlagValue(entityItem, "SYSIDUNIT")));
/*  281 */       typeModel.setLoadingGroup(getLoadingGroup(entityItem));
/*  282 */       typeModel.setHasRevProfile(hasRevProfile(entityItem));
/*  283 */       typeModel.setRevProfile(getRevProfile(entityItem));
/*  284 */       this.abr.addDebug("TypeModel:" + typeModel.toString());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  289 */       String str6 = "ZIP";
/*  290 */       this.abr.addDebug("Flfilcd: " + str6);
/*      */ 
/*      */       
/*  293 */       String str7 = "C";
/*  294 */       this.abr.addDebug("PimsIdentity: " + str7);
/*  295 */       this.abr.addDebug("WarrPeriod: " + this.warrPeriod);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  305 */       bool = "Yes".equals(getAttributeFlagValue(entityItem, "SYSFEEDRESEND"));
/*  306 */       this.abr.addDebug("Resend full: " + bool);
/*      */ 
/*      */       
/*  309 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = getAttributeHistory(entityItem, "RFCABRSTATUS");
/*  310 */       boolean bool1 = existBefore(attributeChangeHistoryGroup, "0030");
/*  311 */       this.abr.addDebug("Passed RFCABRSTATUS exist before: " + bool1);
/*  312 */       EntityItem entityItem1 = null;
/*  313 */       Vector vector5 = new Vector();
/*  314 */       if (!bool && bool1) {
/*  315 */         this.isTMPromoted = true;
/*  316 */         String str = getLatestValFromForAttributeValue(attributeChangeHistoryGroup, "0030");
/*  317 */         if (str == null || "".equals(str)) {
/*  318 */           this.abr.addDebug("t1DTS is null");
/*      */         } else {
/*  320 */           String str8 = this.abr.getCurrentTime();
/*  321 */           Profile profile = this.abr.switchRole("BHFEED");
/*  322 */           profile.setValOnEffOn(str, str);
/*  323 */           profile.setEndOfDay(str8);
/*  324 */           profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*  325 */           profile.setLoginTime(str8);
/*  326 */           this.abr.addDebug("Get t1 entity list for t1DTS: " + str + " t2DTS: " + str8);
/*  327 */           entityList = getEntityList(profile);
/*  328 */           this.abr.addDebug("EntityList for T1 " + profile.getValOn() + " extract " + getVeName() + " contains the following entities: \n" + PokUtils.outputList(entityList));
/*  329 */           entityItem1 = entityList.getParentEntityGroup().getEntityItem(0);
/*      */           
/*  331 */           Vector vector = getRevProfile(entityItem1).getAuoMaterials();
/*  332 */           vector5 = getDeletedAuoMaterials(vector, typeModel.getRevProfile().getAuoMaterials());
/*  333 */           this.abr.addDebug("Delete AUO Materials size:" + vector5.size());
/*      */           
/*  335 */           this.isTMChanged = isTypeModelChanged(entityItem1, entityItem);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  340 */       boolean bool2 = false;
/*  341 */       if (entityItem1 != null) {
/*  342 */         bool2 = isProfitCenterChanged(entityItem1, entityItem);
/*      */       } else {
/*  344 */         bool2 = true;
/*  345 */         this.abr.addDebug("checkAndUpdateProfitCenter T1 model item is null");
/*      */       } 
/*  347 */       this.abr.addDebug("isProfitCenterChanged: " + bool2);
/*      */ 
/*      */       
/*  350 */       boolean bool3 = false;
/*  351 */       if (entityItem1 != null) {
/*  352 */         bool3 = isRevProfOrAuoMtrlChanged(entityItem1, entityItem);
/*      */       } else {
/*  354 */         bool3 = true;
/*      */       } 
/*  356 */       this.abr.addDebug("isRevProfOrAuoChanged: " + bool3);
/*      */       
/*  358 */       if (vector2.size() > 0) {
/*      */         
/*  360 */         for (byte b = 0; b < vector2.size(); b++) {
/*  361 */           EntityItem entityItem2 = vector2.get(b);
/*  362 */           this.abr.addDebug("Promote MODEL for " + entityItem2.getKey());
/*      */ 
/*      */           
/*  365 */           Vector vector = PokUtils.getAllLinkedEntities(entityItem2, "AVAILGAA", "GENERALAREA");
/*  366 */           List<SalesOrgPlants> list = getAllSalesOrgPlant(vector);
/*  367 */           Set<String> set = getAllPlant(list);
/*  368 */           if (set.size() == 0) {
/*  369 */             this.abr.addDebug("There is no plant, bypass " + entityItem2.getKey());
/*      */           } else {
/*      */             
/*  372 */             HashSet<String> hashSet = new HashSet();
/*      */             
/*  374 */             if (this.isTMPromoted) {
/*  375 */               if (entityList != null && entityItem1 != null) {
/*  376 */                 Vector vector7 = PokUtils.getAllLinkedEntities(entityItem1, "MODELAVAIL", "AVAIL");
/*  377 */                 Vector vector8 = PokUtils.getEntitiesWithMatchedAttr(vector7, "AVAILTYPE", "146");
/*      */                 
/*  379 */                 this.isTMGeoChanged = isTypeModelGeoChanged(vector8, entityItem2, hashSet);
/*  380 */                 List<String> list1 = getNewCountries(vector8, entityItem2);
/*  381 */                 this.isTMGeoPromoted = (list1.size() == 0);
/*      */                 
/*  383 */                 if (!this.isTMGeoChanged && !this.isTMGeoPromoted) {
/*  384 */                   list = getAllSalesOrgPlantByCountryList(list, list1);
/*      */                 }
/*      */               } else {
/*  387 */                 this.abr.addDebug("t1EntityList not null:" + ((entityList != null) ? 1 : 0) + " t1ModelItem not null:" + ((entityItem1 != null) ? 1 : 0));
/*      */               } 
/*      */             } else {
/*  390 */               this.isTMGeoPromoted = false;
/*      */             } 
/*  392 */             this.abr.addDebug("isTMPromoted: " + this.isTMPromoted + " isTMChanged: " + this.isTMChanged + " isTMGeoPromoted: " + this.isTMGeoPromoted + " isTMGeoChanged: " + this.isTMGeoChanged);
/*      */ 
/*      */             
/*  395 */             CHWAnnouncement cHWAnnouncement1 = new CHWAnnouncement();
/*  396 */             cHWAnnouncement1.setAnnDocNo(typeModel.getType());
/*  397 */             cHWAnnouncement1.setDiv(str4);
/*  398 */             cHWAnnouncement1.setSegmentAcronym(getSegmentAcronymForAnn(entityItem));
/*      */             
/*  400 */             CHWGeoAnn cHWGeoAnn1 = new CHWGeoAnn();
/*  401 */             SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  406 */             Vector<EntityItem> vector6 = PokUtils.getAllLinkedEntities(entityItem2, "AVAILANNA", "ANNOUNCEMENT");
/*  407 */             if (vector6 != null && vector6.size() > 0) {
/*  408 */               EntityItem entityItem3 = vector6.get(0);
/*  409 */               cHWAnnouncement1.setAnnouncementType(getAttributeValue(entityItem3, "ANNTYPE"));
/*  410 */               cHWGeoAnn1.setAnnouncementDate(simpleDateFormat1.parse(getAttributeValue(entityItem3, "ANNDATE")));
/*      */             } else {
/*  412 */               cHWAnnouncement1.setAnnouncementType("New");
/*  413 */               cHWGeoAnn1.setAnnouncementDate(simpleDateFormat1.parse(getAttributeValue(entityItem, "ANNDATE")));
/*      */             } 
/*  415 */             this.abr.addDebug("CHWAnnouncement: " + cHWAnnouncement1.toString());
/*  416 */             this.abr.addDebug("CHWAnnouncementGEO: " + cHWGeoAnn1.toString());
/*      */ 
/*      */             
/*  419 */             long l = System.currentTimeMillis();
/*  420 */             promoteType(this.isTMPromoted, bool, vector4, typeModel, cHWAnnouncement1, cHWGeoAnn1, str7, str6, list, set, vector);
/*      */ 
/*      */             
/*  423 */             this.abr.addDebug("promote type took " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */ 
/*      */             
/*  426 */             l = System.currentTimeMillis();
/*  427 */             promoteTypeModel(bool2, bool3, typeModel, cHWAnnouncement1, cHWGeoAnn1, list, set, str7, str6, vector, hashSet, vector5);
/*      */ 
/*      */ 
/*      */             
/*  431 */             this.abr.addDebug("promote type model took " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  436 */         Vector vector = searchAllGeneralAreas();
/*  437 */         List<SalesOrgPlants> list = getAllSalesOrgPlant(vector);
/*  438 */         Set<String> set = getAllPlant(list);
/*  439 */         if (set.size() > 0) {
/*      */ 
/*      */           
/*  442 */           Set<String> set1 = set;
/*  443 */           this.isTMGeoPromoted = false;
/*  444 */           this.isTMGeoChanged = true;
/*  445 */           this.abr.addDebug("isTMPromoted: " + this.isTMPromoted + " isTMChanged: " + this.isTMChanged + " isTMGeoPromoted: " + this.isTMGeoPromoted + " isTMGeoChanged: " + this.isTMGeoChanged);
/*      */ 
/*      */           
/*  448 */           CHWAnnouncement cHWAnnouncement1 = new CHWAnnouncement();
/*  449 */           cHWAnnouncement1.setAnnDocNo(typeModel.getType());
/*  450 */           cHWAnnouncement1.setDiv(str4);
/*  451 */           cHWAnnouncement1.setSegmentAcronym(getSegmentAcronymForAnn(entityItem));
/*      */           
/*  453 */           CHWGeoAnn cHWGeoAnn1 = new CHWGeoAnn();
/*  454 */           SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
/*      */           
/*  456 */           cHWAnnouncement1.setAnnouncementType("New");
/*  457 */           cHWGeoAnn1.setAnnouncementDate(simpleDateFormat1.parse(getAttributeValue(entityItem, "ANNDATE")));
/*  458 */           this.abr.addDebug("CHWAnnouncement: " + cHWAnnouncement1.toString());
/*  459 */           this.abr.addDebug("CHWAnnouncementGEO: " + cHWGeoAnn1.toString());
/*      */ 
/*      */           
/*  462 */           long l = System.currentTimeMillis();
/*  463 */           promoteType(this.isTMPromoted, bool, vector4, typeModel, cHWAnnouncement1, cHWGeoAnn1, str7, str6, list, set, vector);
/*      */ 
/*      */           
/*  466 */           this.abr.addDebug("promote type took " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */ 
/*      */           
/*  469 */           l = System.currentTimeMillis();
/*  470 */           promoteTypeModel(bool2, bool3, typeModel, cHWAnnouncement1, cHWGeoAnn1, list, set, str7, str6, vector, set1, vector5);
/*      */ 
/*      */ 
/*      */           
/*  474 */           this.abr.addDebug("promote type model took " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */ 
/*      */           
/*  477 */           String str = getAttributeValue(entityItem, "WTHDRWEFFCTVDATE");
/*  478 */           this.abr.addDebug("MODEL.WTHDRWEFFCTVDATE " + str);
/*  479 */           if (str != null && !"".equals(str.trim())) {
/*  480 */             Date date = simpleDateFormat1.parse(str);
/*  481 */             promoteWDFMAnnouncement(date, typeModel, list, str3, str7, str6, cHWAnnouncement1, cHWGeoAnn1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  489 */       boolean bool4 = false;
/*  490 */       boolean bool5 = false;
/*  491 */       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*      */       
/*  493 */       CHWAnnouncement cHWAnnouncement = new CHWAnnouncement();
/*  494 */       cHWAnnouncement.setAnnDocNo(typeModel.getType());
/*  495 */       cHWAnnouncement.setDiv(str4);
/*  496 */       cHWAnnouncement.setSegmentAcronym(getSegmentAcronymForAnn(entityItem));
/*      */       
/*  498 */       CHWGeoAnn cHWGeoAnn = new CHWGeoAnn();
/*      */       
/*  500 */       cHWAnnouncement.setAnnouncementType("New");
/*  501 */       cHWGeoAnn.setAnnouncementDate(simpleDateFormat.parse(getAttributeValue(entityItem, "ANNDATE")));
/*  502 */       this.abr.addDebug("CHWAnnouncement: " + cHWAnnouncement.toString());
/*  503 */       this.abr.addDebug("CHWAnnouncementGEO: " + cHWGeoAnn.toString());
/*      */       
/*  505 */       if (vector3.size() > 0) {
/*  506 */         for (byte b = 0; b < vector3.size(); b++) {
/*  507 */           EntityItem entityItem2 = vector3.get(b);
/*  508 */           this.abr.addDebug("Promote WDFM Announcement for " + entityItem2.getKey());
/*  509 */           Date date = simpleDateFormat.parse(getAttributeValue(entityItem2, "EFFECTIVEDATE"));
/*      */           
/*  511 */           Vector vector = PokUtils.getAllLinkedEntities(entityItem2, "AVAILGAA", "GENERALAREA");
/*  512 */           List<SalesOrgPlants> list = getAllSalesOrgPlant(vector);
/*      */           
/*  514 */           if (!bool && bool1 && 
/*  515 */             entityList != null && entityItem1 != null) {
/*  516 */             Vector vector6 = PokUtils.getAllLinkedEntities(entityItem1, "MODELAVAIL", "AVAIL");
/*  517 */             Vector vector7 = PokUtils.getEntitiesWithMatchedAttr(vector6, "AVAILTYPE", "149");
/*  518 */             bool5 = isTypeModelWithdrawChanged(vector7, entityItem2);
/*  519 */             List<String> list1 = getNewCountries(vector7, entityItem2);
/*  520 */             bool4 = (list1.size() == 0) ? true : false;
/*  521 */             if (!bool5 && !bool4) {
/*  522 */               list = getAllSalesOrgPlantByCountryList(list, list1);
/*      */             }
/*      */           } 
/*      */           
/*  526 */           this.abr.addDebug("isTmwPromoted: " + bool4 + " isTmwChanged: " + bool5);
/*      */           
/*  528 */           if (!bool4 || bool5) {
/*  529 */             cHWGeoAnn.setAnnouncementDate(date);
/*  530 */             this.abr.addDebug("CHWAnnouncementGEO: " + cHWGeoAnn.toString());
/*  531 */             promoteWDFMAnnouncement(date, typeModel, list, str3, str7, str6, cHWAnnouncement, cHWGeoAnn);
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  536 */       if (needReleaseParkingTable()) {
/*  537 */         this.rdhRestProxy.r144(str3, "R", str7);
/*  538 */         this.abr.addOutput("[R144] Update park status R");
/*      */       } else {
/*  540 */         this.rdhRestProxy.r144(str3, "H", str7);
/*  541 */         this.abr.addOutput("[R144] Update park status H");
/*      */       } 
/*      */     } finally {
/*      */       
/*  545 */       if (bool && entityItem != null) {
/*  546 */         setFlagValue("SYSFEEDRESEND", "No", entityItem);
/*      */       }
/*      */       
/*  549 */       if (entityList != null) {
/*  550 */         entityList.dereference();
/*  551 */         entityList = null;
/*      */       } 
/*  553 */       if (this.entityList != null) {
/*  554 */         this.entityList.dereference();
/*  555 */         this.entityList = null;
/*      */       } 
/*  557 */       this.abr.addDebug("End processThis()");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void promoteType(boolean paramBoolean1, boolean paramBoolean2, Vector paramVector1, TypeModel paramTypeModel, CHWAnnouncement paramCHWAnnouncement, CHWGeoAnn paramCHWGeoAnn, String paramString1, String paramString2, List<SalesOrgPlants> paramList, Set<String> paramSet, Vector paramVector2) throws RfcAbrException, SQLException, MiddlewareException, Exception {
/*  586 */     this.abr.addOutputHeader("Promote Type " + paramTypeModel.getType() + ":");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  593 */     if (!paramBoolean1 || paramBoolean2 || !isTypePromoted(paramVector1)) {
/*  594 */       this.abr.addDebug("Start Type:" + paramTypeModel.getType() + " full promoted");
/*      */       
/*  596 */       this.rdhRestProxy.r100(paramCHWAnnouncement, paramTypeModel, paramCHWGeoAnn, "NEW", null, null, paramString1);
/*  597 */       this.rdhRestProxy.r100(paramCHWAnnouncement, paramTypeModel, paramCHWGeoAnn, "UPG", null, null, paramString1);
/*  598 */       this.abr.addOutput("[R100] Create basic view for type NEW/UPG material");
/*      */       
/*  600 */       this.abr.addDebug("R101 Plant size:" + paramSet.size());
/*  601 */       for (String str1 : paramSet) {
/*  602 */         this.rdhRestProxy.r101(paramCHWAnnouncement, paramTypeModel, paramCHWGeoAnn, "NEW", paramTypeModel.getLoadingGroup(), null, null, paramString1, str1);
/*  603 */         this.rdhRestProxy.r101(paramCHWAnnouncement, paramTypeModel, paramCHWGeoAnn, "UPG", paramTypeModel.getLoadingGroup(), null, null, paramString1, str1);
/*  604 */         this.abr.addDebug("[R101] Create generic plant " + str1 + " view for type NEW/UPG material");
/*      */       } 
/*      */ 
/*      */       
/*  608 */       String str = this.configManager.getString("epimshw.extraPlant", true);
/*  609 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/*      */       
/*  611 */       while (stringTokenizer.hasMoreElements()) {
/*  612 */         String str1 = stringTokenizer.nextToken();
/*  613 */         this.rdhRestProxy.r189(paramCHWAnnouncement, paramTypeModel, str1, "NEW", null, null, paramString1, paramCHWGeoAnn);
/*  614 */         this.rdhRestProxy.r189(paramCHWAnnouncement, paramTypeModel, str1, "UPG", null, null, paramString1, paramCHWGeoAnn);
/*  615 */         this.abr.addOutput("[R189] Create CFI plant " + str1 + " view for type NEW/UPG material");
/*      */       } 
/*      */       
/*  618 */       this.abr.addDebug("R102 SalesOrgPlants size: " + paramList.size());
/*  619 */       for (SalesOrgPlants salesOrgPlants : paramList) {
/*  620 */         String str1 = salesOrgPlants.getSalesorg();
/*  621 */         Vector vector = getTaxListBySalesOrgPlants(salesOrgPlants);
/*  622 */         Vector<String> vector1 = salesOrgPlants.getPlants();
/*  623 */         if (vector1 != null && vector1.size() > 0) {
/*  624 */           for (String str2 : vector1) {
/*  625 */             if (str2.equals("1999")) {
/*  626 */               this.abr.addDebug("Skip plant for 1999");
/*      */               continue;
/*      */             } 
/*  629 */             this.rdhRestProxy.r102(paramCHWAnnouncement, paramTypeModel, str2, "NEW", null, null, paramString1, paramString2, str1, vector, paramCHWGeoAnn, null);
/*  630 */             this.rdhRestProxy.r102(paramCHWAnnouncement, paramTypeModel, str2, "UPG", null, null, paramString1, paramString2, str1, vector, paramCHWGeoAnn, null);
/*  631 */             this.abr.addOutput("[R102] Create sales " + str1 + " view for plant " + str2 + " for type NEW/UPG material");
/*      */           }  continue;
/*      */         } 
/*  634 */         this.abr.addDebug("R102 no plant for salesorg:" + str1);
/*      */       } 
/*      */ 
/*      */       
/*  638 */       if (isGENERALAREAContainsZAButNoUS(paramVector2)) {
/*  639 */         processTaxSupportForUSWhenProductIsSetForZA(paramTypeModel, paramCHWAnnouncement, paramCHWGeoAnn, paramString1, paramString2, false, null, null, null, null);
/*      */       }
/*      */       
/*  642 */       this.rdhRestProxy.r103(paramTypeModel, "NEW", paramCHWAnnouncement, null, null, paramString1);
/*  643 */       this.rdhRestProxy.r103(paramTypeModel, "UPG", paramCHWAnnouncement, null, null, paramString1);
/*  644 */       this.abr.addOutput("[R103] Create 001 classification for MG_COMMON for type NEW/UPG material");
/*  645 */       this.rdhRestProxy.r104(paramTypeModel, "NEW", paramCHWAnnouncement, null, null, paramString1);
/*  646 */       this.rdhRestProxy.r104(paramTypeModel, "UPG", paramCHWAnnouncement, null, null, paramString1);
/*  647 */       this.abr.addOutput("[R104] Create ZDM classification for type NEW/UPG material");
/*  648 */       this.rdhRestProxy.r106(paramTypeModel, paramCHWAnnouncement, paramString1);
/*  649 */       this.abr.addOutput("[R106] Create type models class");
/*      */ 
/*      */       
/*  652 */       for (byte b = 0; b <= 9; b++) {
/*  653 */         String str1 = b + "000";
/*  654 */         String str2 = paramTypeModel.getType();
/*  655 */         String str3 = paramTypeModel.getModel();
/*  656 */         this.rdhRestProxy.r130(str2, str3, str1, paramCHWAnnouncement, paramString1);
/*  657 */         this.abr.addOutput("[R130] Create type FEAT range " + str1 + " class");
/*  658 */         this.rdhRestProxy.r176(str2, str3, str1, "NEW", paramCHWAnnouncement, paramString1);
/*  659 */         this.rdhRestProxy.r176(str2, str3, str1, "UPG", paramCHWAnnouncement, paramString1);
/*  660 */         this.abr.addOutput("[R176] Create 300 classification for type FEAT for type NEW/UPG material for range " + str1);
/*      */ 
/*      */         
/*  663 */         if (isHIPOModel(str2, str3)) {
/*  664 */           this.abr.addDebug("HIPO model don't need to call R131 for range " + str1);
/*      */         } else {
/*  666 */           this.rdhRestProxy.r131(str2, str1, paramCHWAnnouncement, paramString1);
/*  667 */           this.abr.addOutput("[R131] Create type UF class for range " + str1);
/*  668 */           this.rdhRestProxy.r177(str2, str1, paramCHWAnnouncement, paramString1);
/*  669 */           this.abr.addOutput("[R177] Create 300 classification for type UF for UPG for range " + str1);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  680 */       this.rdhRestProxy.r123(paramTypeModel, null, "NEW", paramCHWAnnouncement, null, paramString1);
/*  681 */       this.rdhRestProxy.r123(paramTypeModel, null, "UPG", paramCHWAnnouncement, null, paramString1);
/*  682 */       this.abr.addOutput("[R123] Create 300 classification for type models for NEW/UPG");
/*  683 */       this.rdhRestProxy.r108(paramTypeModel, paramCHWAnnouncement, paramString1);
/*  684 */       this.abr.addOutput("[R108] Create type MOD characteristic");
/*  685 */       this.rdhRestProxy.r110(paramTypeModel, paramCHWAnnouncement, paramString1);
/*  686 */       this.abr.addOutput("[R110] Assign MOD characteristic to models class");
/*  687 */       this.rdhRestProxy.r150(paramTypeModel, paramCHWAnnouncement, paramString1);
/*  688 */       this.abr.addOutput("[R150] Create 012 classification for MOD");
/*  689 */       this.rdhRestProxy.r160(paramTypeModel, paramCHWAnnouncement, paramString1);
/*  690 */       this.abr.addOutput("[R160] Assign char to class FEAT_0000");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  696 */       setPromotedMachtypes(paramVector1);
/*      */       
/*  698 */       if (needReleaseParkingTable()) {
/*  699 */         this.rdhRestProxy.r144(paramCHWAnnouncement.getAnnDocNo(), "R", paramString1);
/*  700 */         this.abr.addOutput("[R144] Update park status R");
/*      */       } else {
/*  702 */         this.rdhRestProxy.r144(paramCHWAnnouncement.getAnnDocNo(), "H", paramString1);
/*  703 */         this.abr.addOutput("[R144] Update park status H");
/*      */       } 
/*      */       
/*  706 */       this.abr.addDebug("End Type:" + paramTypeModel.getType() + " full promoted");
/*      */     } else {
/*      */       
/*  709 */       this.abr.addDebug("Start Type:" + paramTypeModel.getType() + " already promoted");
/*      */       
/*  711 */       if (!this.isTMGeoPromoted) {
/*  712 */         boolean bool = false;
/*  713 */         this.abr.addDebug("R102 SalesOrgPlants size: " + paramList.size());
/*  714 */         for (SalesOrgPlants salesOrgPlants : paramList) {
/*  715 */           String str = salesOrgPlants.getSalesorg();
/*  716 */           Vector vector = getTaxListBySalesOrgPlants(salesOrgPlants);
/*  717 */           Vector<String> vector1 = salesOrgPlants.getPlants();
/*  718 */           if (vector1 != null && vector1.size() > 0) {
/*  719 */             for (String str1 : vector1) {
/*  720 */               if (str1.equals("1999")) {
/*  721 */                 this.abr.addDebug("Skip plant for 1999");
/*      */                 continue;
/*      */               } 
/*  724 */               this.rdhRestProxy.r102(paramCHWAnnouncement, paramTypeModel, str1, "NEW", null, null, paramString1, paramString2, str, vector, paramCHWGeoAnn, null);
/*  725 */               this.rdhRestProxy.r102(paramCHWAnnouncement, paramTypeModel, str1, "UPG", null, null, paramString1, paramString2, str, vector, paramCHWGeoAnn, null);
/*  726 */               this.abr.addOutput("[R102] Create sales " + str + " view for type NEW/UPG material");
/*  727 */               if (!bool) {
/*  728 */                 bool = true;
/*  729 */                 this.abr.addDebug("Set needUpdateParkingTableForType to " + bool);
/*      */               } 
/*      */             }  continue;
/*      */           } 
/*  733 */           this.abr.addDebug("R102 no plant for salesorg:" + str);
/*      */         } 
/*      */ 
/*      */         
/*  737 */         if (isGENERALAREAContainsZAButNoUS(paramVector2)) {
/*  738 */           processTaxSupportForUSWhenProductIsSetForZA(paramTypeModel, paramCHWAnnouncement, paramCHWGeoAnn, paramString1, paramString2, false, null, null, null, null);
/*      */         }
/*  740 */         if (bool) {
/*  741 */           if (needReleaseParkingTable()) {
/*  742 */             this.rdhRestProxy.r144(paramCHWAnnouncement.getAnnDocNo(), "R", paramString1);
/*  743 */             this.abr.addOutput("[R144] Update park status R");
/*      */           } else {
/*  745 */             this.rdhRestProxy.r144(paramCHWAnnouncement.getAnnDocNo(), "H", paramString1);
/*  746 */             this.abr.addOutput("[R144] Update park status H");
/*      */           } 
/*      */         }
/*      */       } else {
/*  750 */         this.abr.addDebug("isTMGeoPromoted is: " + this.isTMGeoPromoted + ", will not call r102");
/*      */       } 
/*  752 */       this.abr.addDebug("End Type:" + paramTypeModel.getType() + " already promoted");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void promoteTypeModel(boolean paramBoolean1, boolean paramBoolean2, TypeModel paramTypeModel, CHWAnnouncement paramCHWAnnouncement, CHWGeoAnn paramCHWGeoAnn, List<SalesOrgPlants> paramList, Set<String> paramSet1, String paramString1, String paramString2, Vector paramVector1, Set<String> paramSet2, Vector paramVector2) throws Exception {
/*  763 */     this.abr.addOutputHeader("Promote TypeModel " + paramTypeModel.getType() + paramTypeModel.getModel() + ":");
/*      */     
/*  765 */     paramCHWAnnouncement.setAnnDocNo(paramTypeModel.getType() + "-" + paramTypeModel.getModel());
/*  766 */     this.abr.addDebug("Set chwA annDocNo: " + paramCHWAnnouncement.getAnnDocNo());
/*  767 */     SharedProductComponents sharedProductComponents = new SharedProductComponents();
/*  768 */     PlannedSalesStatus plannedSalesStatus = new PlannedSalesStatus();
/*      */     
/*  770 */     String str1 = paramTypeModel.getType() + paramTypeModel.getModel();
/*  771 */     BasicMaterialFromSAP basicMaterialFromSAP = null;
/*      */     try {
/*  773 */       basicMaterialFromSAP = this.rdhRestProxy.r209(str1);
/*  774 */       this.abr.addDebug("Call R209 successfully, Read Basic View of Material from RDH, BasicMaterialFromSAP: MatlType=" + basicMaterialFromSAP.getMatlType() + " prodHierarchy=" + basicMaterialFromSAP.getProdHier());
/*  775 */     } catch (HWPIMSAbnormalException hWPIMSAbnormalException) {
/*  776 */       this.abr.addDebug("Get exception: " + hWPIMSAbnormalException.getMessage() + " from RFC, create a empty BasicMaterialFromSAP");
/*  777 */       basicMaterialFromSAP = new BasicMaterialFromSAP();
/*  778 */       basicMaterialFromSAP.setMatlType(null);
/*  779 */       basicMaterialFromSAP.setProdHier(null);
/*      */     } 
/*  781 */     this.abr.addOutput("[R209] Read basic view of material");
/*  782 */     String str2 = basicMaterialFromSAP.getMatlType();
/*  783 */     String str3 = paramTypeModel.getProductHierarchy();
/*  784 */     this.abr.addDebug("ProductHierarchy from EACM is " + str3);
/*  785 */     if (str2 != null && !str2.equals("") && isProductHierarchyDifferent(str3, basicMaterialFromSAP.getProdHier())) {
/*  786 */       this.abr.addDebug("Material exists and ProductHierarchy is different");
/*  787 */       Vector vector = getSupportedSalesOrgCol(paramList);
/*  788 */       this.abr.addDebug("All supported salesOrg size:" + vector.size() + " values:" + vector);
/*  789 */       callR260updateProdHierarchyOnSalesView(vector, paramTypeModel, paramCHWAnnouncement, str3, paramString1, paramCHWGeoAnn, paramTypeModel.getAcctAsgnGrp());
/*      */     } else {
/*  791 */       this.abr.addDebug("R260 will not be called");
/*      */     } 
/*      */ 
/*      */     
/*  795 */     if (!this.isTMGeoPromoted || this.isTMGeoChanged) {
/*  796 */       this.abr.addDebug("Type Model Promote : Check shared product");
/*  797 */       checkSharedProductInfo(paramTypeModel.getType(), paramTypeModel.getModel(), paramCHWAnnouncement.getAnnDocNo(), sharedProductComponents, paramString1, "", false, str2);
/*      */     } 
/*      */ 
/*      */     
/*  801 */     if (!this.isTMPromoted) {
/*  802 */       this.abr.addDebug("Start Type Model Promote : TypeModel not promoted");
/*  803 */       if (!sharedProductComponents.getSharedProduct()) {
/*  804 */         this.abr.addDebug("R115 plant size: " + paramSet1.size());
/*  805 */         for (String str4 : paramSet1) {
/*  806 */           this.rdhRestProxy.r115(paramCHWAnnouncement, paramTypeModel, paramCHWGeoAnn, paramString1, str4);
/*  807 */           this.abr.addOutput("[R115] Create Type/Model material basic view for plant " + str4);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  812 */       if (sharedProductComponents.getSharedProduct() && sharedProductComponents.getSharedProductMaterialType().equalsIgnoreCase("ZSEL")) {
/*  813 */         String str4 = paramTypeModel.getType() + paramTypeModel.getModel();
/*  814 */         if (!this.spItemCateg.containsKey(str4)) {
/*  815 */           this.spItemCateg.put(str4, "spitem_cat");
/*      */         }
/*      */       } 
/*      */       
/*  819 */       this.abr.addDebug("R116 plant size: " + paramSet1.size());
/*  820 */       for (String str4 : paramSet1) {
/*  821 */         this.rdhRestProxy.r116(paramCHWAnnouncement, paramTypeModel, str4, paramTypeModel.getLoadingGroup(), paramCHWGeoAnn, "CHW1", paramString1);
/*  822 */         this.abr.addOutput("[R116] Create CHW1 generic plant " + str4 + " view for Type/Model material");
/*  823 */         if (str4.equals("1999")) {
/*  824 */           this.abr.addDebug("Skip plant for 1999");
/*      */           continue;
/*      */         } 
/*  827 */         this.rdhRestProxy.r116(paramCHWAnnouncement, paramTypeModel, str4, paramTypeModel.getLoadingGroup(), paramCHWGeoAnn, "CHW2", paramString1);
/*  828 */         this.abr.addOutput("[R116] Create CHW2 generic plant " + str4 + " view for Type/Model material");
/*      */       } 
/*      */ 
/*      */       
/*  832 */       String str = this.configManager.getString("epimshw.extraPlant", true);
/*  833 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/*  834 */       while (stringTokenizer.hasMoreElements()) {
/*  835 */         String str4 = stringTokenizer.nextToken();
/*  836 */         boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  842 */         if (!sharedProductComponents.getSharedProduct() || (sharedProductComponents.getSharedProduct() && !bool)) {
/*  843 */           this.rdhRestProxy.r183(paramCHWAnnouncement.getAnnDocNo(), paramTypeModel.getType() + paramTypeModel.getModel(), str4, paramString1, paramTypeModel.getProfitCenter(), paramCHWGeoAnn, paramTypeModel.getDiv());
/*  844 */           this.abr.addOutput("[R183] Create CFI plant " + str4 + " view for Type/Model material");
/*      */         } 
/*      */       } 
/*  847 */       this.rdhRestProxy.r156(paramTypeModel.getType() + paramTypeModel.getModel(), paramTypeModel.getDiv(), paramCHWAnnouncement, paramString1, false);
/*  848 */       this.abr.addOutput("[R156] Create ZDM classification for Type/Model material");
/*      */ 
/*      */       
/*  851 */       this.abr.addDebug("salesorgPlantsVect size: " + paramList.size());
/*  852 */       plannedSalesStatus = checkForNewPlannedSalesStatus(paramCHWGeoAnn.getAnnouncementDate(), false);
/*      */       
/*  854 */       if (isGENERALAREAContainsZAButNoUS(paramVector1)) {
/*  855 */         processTaxSupportForUSWhenProductIsSetForZA(paramTypeModel, paramCHWAnnouncement, paramCHWGeoAnn, paramString1, paramString2, false, null, null, "r117", plannedSalesStatus);
/*      */       }
/*  857 */       for (SalesOrgPlants salesOrgPlants : paramList) {
/*  858 */         String str4 = salesOrgPlants.getSalesorg();
/*  859 */         if ((str4.equalsIgnoreCase("0147") && !sharedProductComponents.getSharedProductIn0147()) || !str4.equalsIgnoreCase("0147")) {
/*  860 */           this.abr.addDebug("salesOrg equals 0147 and (spComponents not in 0147 or salesOrg not equals 0147)");
/*  861 */           Vector vector = getTaxListBySalesOrgPlants(salesOrgPlants);
/*  862 */           Vector<String> vector1 = salesOrgPlants.getPlants();
/*  863 */           if (vector1 != null && vector1.size() > 0) {
/*  864 */             for (String str5 : vector1) {
/*  865 */               if (str5.equals("1999")) {
/*  866 */                 this.abr.addDebug("Skip plant for 1999");
/*      */                 continue;
/*      */               } 
/*  869 */               this.rdhRestProxy.r117(paramCHWAnnouncement, paramTypeModel.getType() + paramTypeModel.getModel(), paramTypeModel.getDiv(), paramTypeModel.getAcctAsgnGrp(), plannedSalesStatus, true, paramString1, paramString2, str4, paramTypeModel.getProductHierarchy(), vector, str5, paramCHWGeoAnn);
/*  870 */               this.abr.addOutput("[R177] Create sales " + str4 + " view for Type/Model material");
/*      */             } 
/*      */           } else {
/*  873 */             this.abr.addDebug("Not call R117 no plant for salesOrg: " + str4);
/*      */           } 
/*      */         } 
/*  876 */         LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(paramTypeModel);
/*  877 */         Date date = paramCHWGeoAnn.getAnnouncementDate();
/*  878 */         updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), date, paramCHWAnnouncement.getAnnDocNo(), paramCHWAnnouncement.getAnnouncementType(), paramString1, str4);
/*  879 */         this.abr.addDebug("updateAnnLifecyle successfully for salesOrg: " + str4);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  884 */       if (sharedProductComponents.getSharedProduct()) {
/*  885 */         this.rdhRestProxy.r118(paramTypeModel, paramCHWAnnouncement, paramString2, this.warrPeriod, true, paramString1);
/*  886 */         this.abr.addOutput("[R118] Create 001 classification for MM_FIELDS for Type/Model material");
/*  887 */         this.rdhRestProxy.r119(paramTypeModel.getType() + paramTypeModel.getModel(), paramCHWAnnouncement, true, true, paramString1);
/*  888 */         this.abr.addOutput("[R119] Create 001 classification for MG_COMMON for Type/Model material");
/*      */       } else {
/*  890 */         this.rdhRestProxy.r118(paramTypeModel, paramCHWAnnouncement, paramString2, this.warrPeriod, false, paramString1);
/*  891 */         this.abr.addOutput("[R118] Create 001 classification for MM_FIELDS for Type/Model material");
/*  892 */         this.rdhRestProxy.r119(paramTypeModel.getType() + paramTypeModel.getModel(), paramCHWAnnouncement, false, true, paramString1);
/*  893 */         this.abr.addOutput("[R119] Create 001 classification for MG_COMMON for Type/Model material");
/*      */       } 
/*      */       
/*  896 */       this.rdhRestProxy.r120(paramTypeModel, paramCHWAnnouncement, paramString1);
/*  897 */       this.abr.addOutput("[R120] Maintain model value for type MOD characteristic");
/*  898 */       this.rdhRestProxy.r121(paramTypeModel, paramCHWAnnouncement, paramString1);
/*  899 */       this.abr.addOutput("[R121] Create model selection dependency");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  905 */       if (ConfigManager.getConfigManager().getString("epimshw.enableSapLedger").equals("Y")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  920 */         this.abr.addDebug("Start check and update profit center");
/*  921 */         for (String str4 : paramSet1) {
/*  922 */           this.rdhRestProxy.r262(paramCHWAnnouncement, str1, str4, paramString1, paramTypeModel.getProfitCenter(), paramCHWGeoAnn);
/*      */         }
/*  924 */         this.abr.addDebug("End check and update profit center");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  930 */       this.configProfTypes.addElement(paramTypeModel.getType());
/*  931 */       createConfigurationProfiles(this.configProfTypes, paramCHWAnnouncement, paramString1);
/*      */ 
/*      */       
/*  934 */       this.abr.addDebug("End Type Model Promote : TypeModel not promoted");
/*      */     }
/*      */     else {
/*      */       
/*  938 */       this.abr.addDebug("The MODEL already promoted");
/*      */     } 
/*      */     
/*  941 */     if (this.isTMChanged || this.isTMGeoChanged || (this.isTMPromoted && !this.isTMGeoPromoted)) {
/*  942 */       this.abr.addDebug("Start Type Model Promote : TypeModel changed or TypeModelGeo changed or TypeModelGeo not promoted");
/*      */       
/*  944 */       if (this.isTMGeoChanged || !this.isTMGeoPromoted) {
/*      */         
/*  946 */         this.abr.addDebug("salesorgPlantsVect size: " + paramList.size());
/*  947 */         for (SalesOrgPlants salesOrgPlants : paramList) {
/*  948 */           String str = salesOrgPlants.getSalesorg();
/*  949 */           LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(paramTypeModel);
/*  950 */           Date date = paramCHWGeoAnn.getAnnouncementDate();
/*  951 */           updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), date, paramCHWAnnouncement.getAnnDocNo(), paramCHWAnnouncement.getAnnouncementType(), paramString1, str);
/*  952 */           this.abr.addDebug("updateAnnLifecyle successfully for salesOrg: " + str);
/*      */         } 
/*      */       } 
/*      */       
/*  956 */       this.abr.addDebug("R116 plant size: " + paramSet1.size());
/*  957 */       for (String str : paramSet1) {
/*  958 */         this.rdhRestProxy.r116(paramCHWAnnouncement, paramTypeModel, str, paramTypeModel.getLoadingGroup(), paramCHWGeoAnn, "CHW1", paramString1);
/*  959 */         this.abr.addOutput("[R116] Create CHW1 generic plant " + str + " view for Type/Model material");
/*  960 */         if (str.equals("1999")) {
/*  961 */           this.abr.addDebug("Skip plant for 1999");
/*      */           continue;
/*      */         } 
/*  964 */         this.rdhRestProxy.r116(paramCHWAnnouncement, paramTypeModel, str, paramTypeModel.getLoadingGroup(), paramCHWGeoAnn, "CHW2", paramString1);
/*  965 */         this.abr.addOutput("[R116] Create CHW2 generic plant " + str + " view for Type/Model material");
/*      */       } 
/*      */       
/*  968 */       this.rdhRestProxy.r156(paramTypeModel.getType() + paramTypeModel.getModel(), paramTypeModel.getDiv(), paramCHWAnnouncement, paramString1, false);
/*  969 */       this.abr.addOutput("[R156] Create ZDM classification for Type/Model material");
/*      */       
/*  971 */       this.abr.addDebug("checkSharedProductInfo");
/*  972 */       checkSharedProductInfo(paramTypeModel.getType(), paramTypeModel.getModel(), paramCHWAnnouncement.getAnnDocNo(), sharedProductComponents, paramString1, "", false, basicMaterialFromSAP.getMatlType());
/*      */       
/*  974 */       if (!sharedProductComponents.getSharedProduct()) {
/*  975 */         this.rdhRestProxy.r133(paramTypeModel, paramCHWAnnouncement, paramString1, paramCHWGeoAnn);
/*  976 */         this.abr.addOutput("[R133] Update material basic view for Type/Model material");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  981 */       this.rdhRestProxy.r120(paramTypeModel, paramCHWAnnouncement, paramString1);
/*  982 */       this.abr.addOutput("[R120] Maintain model value for type MOD characteristic");
/*  983 */       if (sharedProductComponents.getSharedProduct()) {
/*  984 */         this.rdhRestProxy.r118(paramTypeModel, paramCHWAnnouncement, paramString2, this.warrPeriod, true, paramString1);
/*  985 */         this.abr.addOutput("[R118] Create 001 classification for MM_FIELDS for Type/Model material");
/*      */       } else {
/*  987 */         this.rdhRestProxy.r118(paramTypeModel, paramCHWAnnouncement, paramString2, this.warrPeriod, false, paramString1);
/*  988 */         this.abr.addOutput("[R118] Create 001 classification for MM_FIELDS for Type/Model");
/*      */       } 
/*      */ 
/*      */       
/*  992 */       if (this.configManager.getString("epimshw.enableSapLedger").equals("Y"))
/*      */       {
/*  994 */         if (paramBoolean1) {
/*  995 */           for (String str : paramSet1) {
/*  996 */             this.rdhRestProxy.r262(paramCHWAnnouncement, str1, str, paramString1, paramTypeModel.getProfitCenter(), paramCHWGeoAnn);
/*  997 */             this.abr.addOutput("[R262] Create plant " + str + " view profit center for material");
/*      */           } 
/*      */         }
/*      */       }
/* 1001 */       if (paramBoolean1) {
/*      */         
/* 1003 */         String str = this.configManager.getString("epimshw.extraPlant", true);
/* 1004 */         this.abr.addDebug("Extra CFI Plant " + str);
/* 1005 */         StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1006 */         while (stringTokenizer.hasMoreElements()) {
/* 1007 */           String str4 = stringTokenizer.nextToken();
/* 1008 */           this.rdhRestProxy.r183(paramCHWAnnouncement.getAnnDocNo(), paramTypeModel.getType() + paramTypeModel.getModel(), str4, paramString1, paramTypeModel.getProfitCenter(), paramCHWGeoAnn, paramTypeModel.getDiv());
/* 1009 */           this.abr.addOutput("[R183] Create CFI plant " + str4 + " view for Type/Model material");
/*      */         } 
/*      */       } 
/* 1012 */       this.abr.addDebug("End Type Model Promote : TypeModel changed or TypeModelGeo changed or TypeModelGeo not promoted");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1017 */     this.rdhRestProxy.r175(paramTypeModel, null, paramCHWAnnouncement, "NEW", null, paramString1);
/* 1018 */     this.rdhRestProxy.r175(paramTypeModel, null, paramCHWAnnouncement, "UPG", null, paramString1);
/* 1019 */     this.abr.addOutput("[R175] Create 001 classification for MM_FIELDS for Type NEW/UPG");
/*      */     
/* 1021 */     if (paramTypeModel.isHasRevProfile()) {
/* 1022 */       this.rdhRestProxy.r205(paramTypeModel, null, "NEW", null, null, null, null, paramString1, paramCHWAnnouncement.getAnnDocNo());
/* 1023 */       this.rdhRestProxy.r205(paramTypeModel, null, "UPG", null, null, null, null, paramString1, paramCHWAnnouncement.getAnnDocNo());
/* 1024 */       this.abr.addOutput("[R205] Update BTPRODUCTS classification for MM_FIELDS for Type NEW/UPG");
/*      */     } else {
/* 1026 */       boolean bool = isTypeHasRevProfile(paramTypeModel.getType());
/* 1027 */       if (bool) {
/* 1028 */         boolean bool1 = paramTypeModel.isHasRevProfile();
/* 1029 */         paramTypeModel.setHasRevProfile(true);
/*      */         
/* 1031 */         this.rdhRestProxy.r205(paramTypeModel, null, "NEW", null, null, null, null, paramString1, paramCHWAnnouncement.getAnnDocNo());
/* 1032 */         this.rdhRestProxy.r205(paramTypeModel, null, "UPG", null, null, null, null, paramString1, paramCHWAnnouncement.getAnnDocNo());
/* 1033 */         this.abr.addOutput("[R205] Update BTPRODUCTS classification for MM_FIELDS for Type NEW/UPG");
/*      */         
/* 1035 */         paramTypeModel.setHasRevProfile(bool1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1047 */     if ((!this.isTMPromoted && paramTypeModel.isHasRevProfile()) || (this.isTMPromoted && paramBoolean2)) {
/* 1048 */       for (String str : paramSet1) {
/* 1049 */         updateRevenueProfileBom(paramTypeModel
/* 1050 */             .getType(), paramCHWAnnouncement
/* 1051 */             .getAnnDocNo(), paramTypeModel
/* 1052 */             .getRevProfile().getAuoMaterials(), null, paramTypeModel
/*      */             
/* 1054 */             .getRevProfile().getRevenueProfile(), "NEW", 
/*      */             
/* 1056 */             getOpwgId(), paramString1, str, paramVector2);
/*      */ 
/*      */ 
/*      */         
/* 1060 */         updateRevenueProfileBom(paramTypeModel
/* 1061 */             .getType(), paramCHWAnnouncement
/* 1062 */             .getAnnDocNo(), paramTypeModel
/* 1063 */             .getRevProfile().getAuoMaterials(), null, paramTypeModel
/*      */             
/* 1065 */             .getRevProfile().getRevenueProfile(), "UPG", 
/*      */             
/* 1067 */             getOpwgId(), paramString1, str, paramVector2);
/*      */ 
/*      */ 
/*      */         
/* 1071 */         updateRevenueProfileBom(paramTypeModel
/* 1072 */             .getType(), paramCHWAnnouncement
/* 1073 */             .getAnnDocNo(), paramTypeModel
/* 1074 */             .getRevProfile().getAuoMaterials(), null, paramTypeModel
/*      */             
/* 1076 */             .getRevProfile().getRevenueProfile(), "MOD" + paramTypeModel
/* 1077 */             .getModel(), 
/* 1078 */             getOpwgId(), paramString1, str, paramVector2);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1084 */     this.abr.addDebug("End Update Revenue Profile BOM for Type");
/*      */     
/* 1086 */     this.abr.addDebug("Start Update Sales BOM");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1091 */     if (!this.isTMPromoted || (this.isTMPromoted && !this.isTMGeoPromoted)) {
/* 1092 */       Set<String> set = this.isTMPromoted ? paramSet2 : paramSet1;
/* 1093 */       if (set != null && set.size() > 0) {
/* 1094 */         for (String str : set) {
/* 1095 */           updateSalesBom(paramTypeModel, paramCHWAnnouncement, this.spItemCateg, "NEW", str, paramString1);
/* 1096 */           updateSalesBom(paramTypeModel, paramCHWAnnouncement, this.spItemCateg, "UPG", str, paramString1);
/*      */         } 
/*      */       }
/*      */     } 
/* 1100 */     this.abr.addDebug("End Update Sales BOM");
/*      */     
/* 1102 */     if (!this.isTMPromoted || this.isTMChanged || this.isTMGeoChanged || !this.isTMGeoPromoted) {
/* 1103 */       this.needUpdateParkingTable = true;
/* 1104 */       this.abr.addDebug("set needUpdateParkingTable to " + this.needUpdateParkingTable);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void promoteWDFMAnnouncement(Date paramDate, TypeModel paramTypeModel, List<SalesOrgPlants> paramList, String paramString1, String paramString2, String paramString3, CHWAnnouncement paramCHWAnnouncement, CHWGeoAnn paramCHWGeoAnn) throws Exception {
/* 1111 */     this.abr.addOutputHeader("Promote WDFM Announcement:");
/* 1112 */     LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(paramTypeModel);
/*      */     
/* 1114 */     PlannedSalesStatus plannedSalesStatus = new PlannedSalesStatus();
/* 1115 */     Date date = new Date();
/*      */     
/* 1117 */     if (paramDate.before(date) || paramDate.equals(date)) {
/* 1118 */       plannedSalesStatus.setCurrentSalesStatus("ZJ");
/* 1119 */       plannedSalesStatus.setCurrentEffectiveDate(paramDate);
/*      */     } 
/*      */     
/* 1122 */     for (SalesOrgPlants salesOrgPlants : paramList) {
/* 1123 */       String str = salesOrgPlants.getSalesorg();
/* 1124 */       LifecycleData lifecycleData = this.rdhRestProxy.r200(lifecycleDataGenerator.getMaterial(), lifecycleDataGenerator.getVarCond(), paramString1, "wdfm", paramString2, str);
/* 1125 */       this.abr.addOutput("[R200] Read lifecycle row WDFM for sales " + str);
/*      */       
/* 1127 */       updateWDFMLifecyle(lifecycleData, lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), paramDate, paramString1, paramString2, str);
/*      */ 
/*      */       
/* 1130 */       Vector vector = getTaxListBySalesOrgPlants(salesOrgPlants);
/* 1131 */       Vector<String> vector1 = salesOrgPlants.getPlants();
/* 1132 */       if (vector1 != null && vector1.size() > 0) {
/* 1133 */         for (String str1 : vector1) {
/* 1134 */           if (str1.equals("1999")) {
/* 1135 */             this.abr.addDebug("Skip plant for 1999");
/*      */             continue;
/*      */           } 
/* 1138 */           this.rdhRestProxy.r117(paramCHWAnnouncement, paramTypeModel.getType() + paramTypeModel.getModel(), paramTypeModel.getDiv(), paramTypeModel
/* 1139 */               .getAcctAsgnGrp(), plannedSalesStatus, true, paramString2, paramString3, str, paramTypeModel
/* 1140 */               .getProductHierarchy(), vector, str1, paramCHWGeoAnn);
/* 1141 */           this.abr.addOutput("[R177] Create sales " + str + " view for Type/Model material");
/*      */         }  continue;
/*      */       } 
/* 1144 */       this.abr.addDebug("Not call R117 no plant for salesOrg: " + str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getComponentsintypeNEW(Vector paramVector1, Vector paramVector2) {
/* 1154 */     Vector<DepData> vector = new Vector();
/* 1155 */     Vector<String> vector1 = new Vector();
/* 1156 */     Enumeration<DepData> enumeration1 = paramVector1.elements();
/* 1157 */     Enumeration<DepData> enumeration2 = paramVector2.elements();
/*      */     
/* 1159 */     while (enumeration1.hasMoreElements()) {
/*      */       
/* 1161 */       DepData depData = enumeration1.nextElement();
/* 1162 */       String str = depData.getComponent();
/* 1163 */       vector1.addElement(str);
/* 1164 */       this.abr.addDebug("vector typeMTC component value" + depData
/* 1165 */           .getComponent());
/*      */     } 
/* 1167 */     this.abr.addDebug("vector size of vect1" + vector1.size());
/*      */     
/* 1169 */     while (enumeration2.hasMoreElements()) {
/*      */       
/* 1171 */       DepData depData = enumeration2.nextElement();
/* 1172 */       String str = depData.getComponent();
/* 1173 */       this.abr.addDebug("vector typeNEW component value" + depData
/* 1174 */           .getComponent());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1179 */       if (vector1.contains(str)) {
/*      */         
/* 1181 */         if (vector != null && vector.size() == 0) {
/* 1182 */           vector = null;
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/* 1187 */       if (vector == null)
/*      */       {
/* 1189 */         vector = new Vector();
/*      */       }
/* 1191 */       vector.add(depData);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1196 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getComponentsintypeMTCwithtypeNEW(Vector paramVector1, Vector paramVector2) {
/* 1205 */     Vector<DepData> vector = new Vector();
/* 1206 */     Vector<String> vector1 = new Vector();
/* 1207 */     Enumeration<DepData> enumeration1 = paramVector2.elements();
/* 1208 */     Enumeration<DepData> enumeration2 = paramVector1.elements();
/* 1209 */     this.abr.addDebug("Vecor Size for typeMTC" + paramVector1.size());
/* 1210 */     this.abr.addDebug("Vecor Size for typeNEW" + paramVector2.size());
/*      */     
/* 1212 */     DepData depData = new DepData();
/* 1213 */     while (enumeration1.hasMoreElements()) {
/*      */       
/* 1215 */       depData = enumeration1.nextElement();
/* 1216 */       String str = depData.getComponent();
/* 1217 */       this.abr.addDebug("vector typeNEW component value" + depData
/* 1218 */           .getComponent());
/* 1219 */       vector1.addElement(str);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1224 */     while (enumeration2.hasMoreElements()) {
/*      */       
/* 1226 */       DepData depData1 = enumeration2.nextElement();
/* 1227 */       String str = depData1.getComponent();
/* 1228 */       this.abr.addDebug("vector typeMTC component value" + depData1
/* 1229 */           .getComponent());
/* 1230 */       if (vector1.contains(str)) {
/*      */         
/* 1232 */         if (vector != null && vector.size() == 0) {
/* 1233 */           vector = null;
/*      */         }
/*      */         continue;
/*      */       } 
/* 1237 */       if (vector == null)
/*      */       {
/* 1239 */         vector = new Vector();
/*      */       }
/* 1241 */       vector.add(depData1);
/*      */     } 
/*      */     
/* 1244 */     return vector;
/*      */   }
/*      */   
/*      */   private boolean isTypePromoted(Vector<EntityItem> paramVector) throws RfcAbrException {
/* 1248 */     if (paramVector != null && paramVector.size() > 0) {
/* 1249 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 1250 */         EntityItem entityItem = paramVector.elementAt(b);
/* 1251 */         String str = getAttributeFlagValue(entityItem, "PROMOTED");
/* 1252 */         if ("PRYES".equals(str)) {
/* 1253 */           this.abr.addDebug("Entity " + entityItem.getKey() + " PROMOTED attr value: " + str);
/* 1254 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/* 1258 */     this.abr.addDebug("Not found promoted MACHTYPE");
/* 1259 */     return false;
/*      */   }
/*      */   
/*      */   private void setPromotedMachtypes(Vector<EntityItem> paramVector) throws SQLException, MiddlewareException {
/* 1263 */     if (paramVector != null && paramVector.size() > 0) {
/* 1264 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 1265 */         EntityItem entityItem = paramVector.elementAt(b);
/* 1266 */         setFlagValue("PROMOTED", "PRYES", entityItem);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isProductHierarchyDifferent(String paramString1, String paramString2) {
/* 1272 */     if (paramString1 != null && paramString2 != null && 
/* 1273 */       !paramString1.trim().equalsIgnoreCase(paramString2.trim())) {
/* 1274 */       this.abr.addDebug("ProductHierarchy is different " + paramString1 + " from db and " + paramString2 + " from MMLC");
/* 1275 */       return true;
/*      */     } 
/*      */     
/* 1278 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkSharedProductInfo(String paramString1, String paramString2, String paramString3, SharedProductComponents paramSharedProductComponents, String paramString4, String paramString5, boolean paramBoolean, String paramString6) throws HWPIMSException {
/* 1287 */     paramSharedProductComponents.setSharedProduct(false);
/* 1288 */     paramSharedProductComponents.setSharedProductIn0147(false);
/* 1289 */     this.abr.addDebug("Shared Product in Check Shared Product Info : " + paramSharedProductComponents.getSharedProduct());
/* 1290 */     this.abr.addDebug("This is NOT Sales Org with 0147 *****: " + paramSharedProductComponents.getSharedProductIn0147());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PlannedSalesStatus checkForNewPlannedSalesStatus(Date paramDate, boolean paramBoolean) throws HWPIMSException {
/* 1315 */     PlannedSalesStatus plannedSalesStatus = new PlannedSalesStatus();
/* 1316 */     plannedSalesStatus.setCurrentSalesStatus("");
/* 1317 */     plannedSalesStatus.setPlannedChangeSalesStatus("");
/* 1318 */     plannedSalesStatus.setOrigPlannedSalesStatus("");
/* 1319 */     plannedSalesStatus.setOrigSalesStatus("");
/* 1320 */     plannedSalesStatus.setOrigPlannedSalesStatus(null);
/*      */     
/* 1322 */     Date date = new Date();
/* 1323 */     if (paramDate.after(date)) {
/* 1324 */       plannedSalesStatus.setPlannedEffectiveDate(paramDate);
/* 1325 */       if (paramBoolean) {
/* 1326 */         plannedSalesStatus.setPlannedChangeSalesStatus("ZN");
/*      */       } else {
/* 1328 */         plannedSalesStatus.setPlannedChangeSalesStatus("Z0");
/*      */       } 
/* 1330 */       plannedSalesStatus.setCurrentSalesStatus("YA");
/* 1331 */       plannedSalesStatus.setCurrentEffectiveDate(date);
/*      */     }
/* 1333 */     else if (paramDate.before(date) || paramDate.equals(date)) {
/* 1334 */       plannedSalesStatus.setCurrentSalesStatus("Z0");
/* 1335 */       plannedSalesStatus.setCurrentEffectiveDate(paramDate);
/*      */     } 
/*      */     
/* 1338 */     return plannedSalesStatus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void callR260updateProdHierarchyOnSalesView(Vector paramVector, Object paramObject, CHWAnnouncement paramCHWAnnouncement, String paramString1, String paramString2, CHWGeoAnn paramCHWGeoAnn, String paramString3) throws HWPIMSAbnormalException {
/*      */     try {
/* 1346 */       if (paramVector != null) {
/* 1347 */         Vector vector = paramVector;
/* 1348 */         this.abr.addDebug("CR2020 : saleOrgVector size : " + vector.size());
/* 1349 */         Enumeration<String> enumeration = vector.elements();
/* 1350 */         while (enumeration.hasMoreElements()) {
/* 1351 */           String str = enumeration.nextElement();
/* 1352 */           this.abr.addDebug("CR2020 : saleOrgVector contents : " + str);
/* 1353 */           this.rdhRestProxy.r260(paramCHWAnnouncement, paramObject, paramString2, str, paramString1, paramCHWGeoAnn, paramString3);
/* 1354 */           this.abr.addOutput("[R260] Update product hierarchy on sales " + str + " view");
/*      */         } 
/*      */       } 
/* 1357 */     } catch (Exception exception) {
/* 1358 */       String str = "Error - Updating L3593 : UpdateProdHierarchyOnSalesView : ";
/* 1359 */       throw new HWPIMSAbnormalException(str, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateSalesBom(TypeModel paramTypeModel, CHWAnnouncement paramCHWAnnouncement, Hashtable paramHashtable, String paramString1, String paramString2, String paramString3) throws Exception {
/* 1379 */     String str1 = paramTypeModel.getType();
/* 1380 */     String str2 = paramTypeModel.getType() + paramTypeModel.getModel();
/* 1381 */     Vector<TypeModel> vector = new Vector();
/* 1382 */     vector.add(paramTypeModel);
/*      */     try {
/* 1384 */       Vector vector1 = this.rdhRestProxy.r210(str1, paramString1, paramString2);
/* 1385 */       this.abr.addOutput("[R210] Read sales BOM for type " + paramString1 + " plant " + paramString2);
/* 1386 */       if (vector1 != null && vector1.size() > 0) {
/* 1387 */         if (hasMatchComponent(vector1, str2)) {
/* 1388 */           this.abr.addDebug("updateSalesBom exist component " + str2 + " in RDH STPO table, do nothing!");
/*      */         } else {
/* 1390 */           this.abr.addDebug("updateSalesBom not exist component " + str2 + " in RDH STPO table, create it!");
/*      */           
/* 1392 */           this.rdhRestProxy.r143(str1, paramString2, vector, paramString1, paramCHWAnnouncement, paramHashtable, paramString3);
/* 1393 */           this.abr.addOutput("[R143] Assign TypeModel as SalesBOM item with dependencies " + paramString1);
/*      */         } 
/*      */       } else {
/* 1396 */         this.abr.addDebug("updateSalesBom there is no component return");
/*      */         
/* 1398 */         this.rdhRestProxy.r143(str1, paramString2, vector, paramString1, paramCHWAnnouncement, paramHashtable, paramString3);
/* 1399 */         this.abr.addOutput("[R143] Assign TypeModel as SalesBOM item with dependencies " + paramString1);
/*      */       } 
/* 1401 */     } catch (HWPIMSNotFoundInMastException hWPIMSNotFoundInMastException) {
/* 1402 */       this.abr.addDebug("updateSalesBom not found in MAST table");
/*      */       
/* 1404 */       this.rdhRestProxy.r142(str1, paramString2, vector, paramString1, paramCHWAnnouncement, paramHashtable, paramString3);
/* 1405 */       this.abr.addOutput("[R142] Create sales BOM " + paramString1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createConfigurationProfiles(Vector<String> paramVector, CHWAnnouncement paramCHWAnnouncement, String paramString) throws Exception {
/* 1413 */     int i = paramVector.size();
/* 1414 */     for (byte b = 0; b < i; b++) {
/*      */ 
/*      */       
/* 1417 */       this.rdhRestProxy.r148(paramVector.elementAt(b), paramCHWAnnouncement, paramString);
/* 1418 */       this.rdhRestProxy.r149(paramVector.elementAt(b), paramCHWAnnouncement, paramString);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Set<String> getAllPlant(List<SalesOrgPlants> paramList) {
/* 1423 */     HashSet<String> hashSet = new HashSet();
/* 1424 */     for (SalesOrgPlants salesOrgPlants : paramList) {
/* 1425 */       Vector<String> vector = salesOrgPlants.getPlants();
/* 1426 */       for (String str : vector) {
/* 1427 */         hashSet.add(str);
/*      */       }
/* 1429 */       if (vector.size() == 0) {
/* 1430 */         this.abr.addDebug("getAllPlants No plant found for country: " + salesOrgPlants.getGenAreaCode());
/*      */       }
/*      */     } 
/* 1433 */     this.abr.addDebug("getAllPlants All plants size: " + hashSet.size() + " values: " + hashSet);
/* 1434 */     return hashSet;
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<String> getAllSalesOrg(List<SalesOrgPlants> paramList) {
/* 1439 */     HashSet<String> hashSet = new HashSet();
/* 1440 */     for (SalesOrgPlants salesOrgPlants : paramList) {
/* 1441 */       String str = salesOrgPlants.getSalesorg();
/* 1442 */       hashSet.add(str);
/*      */     } 
/* 1444 */     this.abr.addDebug("getAllSalesOrg All salesOrg size: " + hashSet.size() + " values: " + hashSet);
/* 1445 */     return hashSet;
/*      */   }
/*      */   
/*      */   private Vector getSupportedSalesOrgCol(List<SalesOrgPlants> paramList) {
/* 1449 */     Vector<String> vector = new Vector();
/* 1450 */     for (SalesOrgPlants salesOrgPlants : paramList) {
/* 1451 */       String str = salesOrgPlants.getSalesorg();
/* 1452 */       if (!"".equals(str)) {
/* 1453 */         vector.add(str);
/*      */       }
/*      */     } 
/* 1456 */     return vector;
/*      */   }
/*      */   
/*      */   private boolean hasRevProfile(EntityItem paramEntityItem) throws RfcAbrException {
/* 1460 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODREVPROFILE", "REVPROF");
/* 1461 */     if (vector.size() > 0) {
/* 1462 */       return true;
/*      */     }
/* 1464 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isTypeModelChanged(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws RfcAbrException {
/* 1469 */     boolean bool = false;
/* 1470 */     if (isDiff(paramEntityItem1, paramEntityItem2, modelMarkChangedAttrs)) {
/* 1471 */       bool = true;
/* 1472 */     } else if (isProfitCenterChanged(paramEntityItem1, paramEntityItem2)) {
/* 1473 */       bool = true;
/*      */     } else {
/* 1475 */       String str1 = getProdHireCd(paramEntityItem1);
/* 1476 */       String str2 = getProdHireCd(paramEntityItem2);
/* 1477 */       if (!str1.equals(str2)) {
/* 1478 */         this.abr.addDebug("PRODHIRECODE value " + str1 + " at t1 is different with " + str2 + " at t2");
/* 1479 */         bool = true;
/*      */       } 
/* 1481 */       if (!bool) {
/* 1482 */         bool = isRevProfOrAuoMtrlChanged(paramEntityItem1, paramEntityItem2);
/* 1483 */         this.abr.addDebug("isRevProfOrAuoMtrlChanged= " + bool);
/*      */       } 
/*      */     } 
/* 1486 */     return bool;
/*      */   }
/*      */   
/*      */   private boolean isProfitCenterChanged(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws RfcAbrException {
/* 1490 */     String str1 = getProfitCenter(getDiv(paramEntityItem1));
/* 1491 */     String str2 = getProfitCenter(getDiv(paramEntityItem2));
/* 1492 */     this.abr.addDebug("isProfitCenterChanged ProfitCenter T1 " + str1 + " T2 " + str2);
/* 1493 */     return !str1.equals(str2);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isRevProfileChanged(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws RfcAbrException {
/* 1498 */     RevProfile revProfile1 = getRevProfile(paramEntityItem1);
/* 1499 */     RevProfile revProfile2 = getRevProfile(paramEntityItem2);
/* 1500 */     String str1 = revProfile1.getRevenueProfile();
/* 1501 */     String str2 = revProfile2.getRevenueProfile();
/* 1502 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isTypeModelGeoChanged(Vector<EntityItem> paramVector, EntityItem paramEntityItem, Set<String> paramSet) throws RfcAbrException {
/* 1513 */     if (isAnnDateChanged(paramVector, paramEntityItem)) {
/* 1514 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1518 */     HashSet<String> hashSet = new HashSet();
/* 1519 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1520 */       EntityItem entityItem = paramVector.get(b);
/* 1521 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "AVAILGAA", "GENERALAREA");
/* 1522 */       List<SalesOrgPlants> list1 = getAllSalesOrgPlant(vector1);
/* 1523 */       hashSet.addAll(getAllPlant(list1));
/*      */     } 
/*      */     
/* 1526 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "AVAILGAA", "GENERALAREA");
/* 1527 */     List<SalesOrgPlants> list = getAllSalesOrgPlant(vector);
/* 1528 */     Set<String> set = getAllPlant(list);
/* 1529 */     this.abr.addDebug("isTypeModelGeoChanged T1 all plant size: " + hashSet.size() + " values: " + hashSet);
/* 1530 */     this.abr.addDebug("isTypeModelGeoChanged T2 for " + paramEntityItem.getKey() + " plant size: " + set.size() + " values: " + set);
/*      */     
/* 1532 */     for (String str : set) {
/* 1533 */       if (!hashSet.contains(str)) {
/* 1534 */         paramSet.add(str);
/*      */       }
/*      */     } 
/* 1537 */     this.abr.addDebug("isTypeModelGeoChanged T2 for delta plant size: " + paramSet.size() + " values: " + paramSet);
/*      */     
/* 1539 */     if (!hashSet.containsAll(set)) {
/* 1540 */       return true;
/*      */     }
/* 1542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isTypeModelWithdrawChanged(Vector paramVector, EntityItem paramEntityItem) throws RfcAbrException {
/* 1546 */     EntityItem entityItem = getEntityItemAtT1(paramVector, paramEntityItem);
/* 1547 */     if (entityItem != null) {
/* 1548 */       String str1 = getAttributeValue(entityItem, "EFFECTIVEDATE");
/* 1549 */       String str2 = getAttributeValue(paramEntityItem, "EFFECTIVEDATE");
/* 1550 */       if (!str2.equals(str1)) {
/* 1551 */         this.abr.addDebug("isTypeModelWithdrawChanged true T1 Date " + str1 + " T2 Date " + str2);
/* 1552 */         return true;
/*      */       } 
/*      */     } else {
/* 1555 */       this.abr.addDebug("isTypeModelWithdrawChanged true AVAIL is null at T1 for " + paramEntityItem.getKey());
/* 1556 */       return true;
/*      */     } 
/* 1558 */     this.abr.addDebug("isTypeModelWithdrawChanged false");
/* 1559 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isAnnDateChanged(Vector paramVector, EntityItem paramEntityItem) throws RfcAbrException {
/* 1565 */     String str1 = "";
/* 1566 */     EntityItem entityItem = getEntityItemAtT1(paramVector, paramEntityItem);
/* 1567 */     if (entityItem != null) {
/* 1568 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 1569 */       if (vector1.size() > 0) {
/* 1570 */         EntityItem entityItem1 = vector1.get(0);
/* 1571 */         str1 = getAttributeValue(entityItem1, "ANNDATE");
/*      */       } else {
/* 1573 */         this.abr.addDebug("isAnnDateChanged Not found ANNOUNCEMENT for " + paramEntityItem.getKey() + " at T1 but at T2");
/* 1574 */         return true;
/*      */       } 
/*      */     } else {
/* 1577 */       this.abr.addDebug("isAnnDateChanged Not found " + paramEntityItem.getKey() + " at T1 but at T2");
/* 1578 */       return true;
/*      */     } 
/*      */     
/* 1581 */     String str2 = "";
/* 1582 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 1583 */     if (vector.size() > 0) {
/* 1584 */       EntityItem entityItem1 = vector.get(0);
/* 1585 */       str2 = getAttributeValue(entityItem1, "ANNDATE");
/*      */     } else {
/* 1587 */       this.abr.addDebug("isAnnDateChanged Not found ANNOUNCEMENT for " + paramEntityItem.getKey() + " at T2");
/*      */     } 
/* 1589 */     this.abr.addDebug("isAnnDateChanged ANNDATE " + str1 + " at T1 " + str2 + " at T2");
/* 1590 */     if (!str1.equals(str2)) {
/* 1591 */       return true;
/*      */     }
/* 1593 */     return false;
/*      */   }
/*      */   
/*      */   private boolean hasMatchComponent(Vector<DepData> paramVector, String paramString) {
/* 1597 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1598 */       DepData depData = paramVector.get(b);
/*      */ 
/*      */       
/* 1601 */       if (depData.getComponent().trim().equals(paramString)) {
/* 1602 */         return true;
/*      */       }
/*      */     } 
/* 1605 */     return false;
/*      */   }
/*      */   
/*      */   private String getProfitCenter(String paramString) {
/*      */     String str;
/* 1610 */     if (isAlphaNumeric(paramString)) {
/* 1611 */       str = paramString;
/*      */     } else {
/* 1613 */       str = "00000000" + paramString;
/*      */     } 
/*      */     
/* 1616 */     return str;
/*      */   }
/*      */   
/*      */   private boolean isAlphaNumeric(String paramString) {
/* 1620 */     int i = paramString.length();
/* 1621 */     for (byte b = 0; b < i; b++) {
/* 1622 */       if (Character.isLetter(paramString.charAt(b))) {
/* 1623 */         return true;
/*      */       }
/*      */     } 
/* 1626 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isTypeHasRevProfile(String paramString) {
/* 1635 */     boolean bool = false;
/* 1636 */     String str = "SELECT f.entityid FROM opicm.flag f \nJOIN opicm.relator r \nON f.entitytype=r.entity1type AND f.entityid=r.entity1id AND r.entitytype='MODREVPROFILE' AND f.entitytype='MODEL' AND f.attributecode='MACHTYPEATR' AND f.valto>current timestamp and f.effto > current timestamp AND r.valto>current timestamp and r.effto > current timestamp\nWHERE f.attributevalue=?";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1641 */     try (PreparedStatement null = this.abr.getDatabase().getPDHConnection().prepareStatement(str)) {
/* 1642 */       preparedStatement.setString(1, paramString);
/* 1643 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 1644 */       if (resultSet.next()) {
/* 1645 */         int i = resultSet.getInt(1);
/* 1646 */         this.abr.addDebug("isMTHasRevProfile MODEL" + i);
/* 1647 */         bool = true;
/*      */       }
/*      */     
/* 1650 */     } catch (MiddlewareException|SQLException middlewareException) {
/* 1651 */       middlewareException.printStackTrace();
/* 1652 */       this.abr.addDebug("isMTHasRevProfile Exception on " + middlewareException.getMessage());
/*      */     } 
/* 1654 */     this.abr.addDebug("isMTHasRevProfile=" + bool);
/* 1655 */     return bool;
/*      */   }
/*      */   
/*      */   public String getVeName() {
/* 1659 */     return "RFCMODEL";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RFCMODELABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */