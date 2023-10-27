/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.rdh.chw.entity.CHWAnnouncement;
/*     */ import com.ibm.rdh.chw.entity.CHWGeoAnn;
/*     */ import com.ibm.rdh.chw.entity.LifecycleData;
/*     */ import com.ibm.rdh.chw.entity.TypeFeature;
/*     */ import com.ibm.rdh.chw.entity.TypeModelFeature;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RFCSWPRODSTRUCTABR
/*     */   extends RFCPRODSTRUCTABR
/*     */ {
/*     */   private boolean isTFPromoted = false;
/*     */   private boolean isTFChanged = false;
/*     */   private boolean isTFGeoPromoted = false;
/*     */   private boolean isTFGeoChanged = false;
/*     */   private boolean needUpdatParkingTable = false;
/*  59 */   private static List<String> tmfMarkChangedAttrs = new ArrayList<>(); static {
/*  60 */     tmfMarkChangedAttrs.add("INSTALL");
/*  61 */   } private static List<String> feaMarkChangedAttrs = new ArrayList<>(); static {
/*  62 */     feaMarkChangedAttrs.add("INVNAME");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RFCSWPRODSTRUCTABR(RFCABRSTATUS paramRFCABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
/*  68 */     super(paramRFCABRSTATUS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processThis() throws RfcAbrException, Exception {
/*  73 */     this.abr.addDebug("Start processThis()");
/*  74 */     EntityList entityList1 = null;
/*  75 */     EntityItem entityItem1 = null;
/*  76 */     EntityList entityList2 = null;
/*  77 */     EntityList entityList3 = null;
/*  78 */     EntityItem entityItem2 = null;
/*  79 */     boolean bool = false;
/*     */ 
/*     */     
/*     */     try {
/*  83 */       entityItem2 = getRooEntityItem();
/*     */       
/*  85 */       if (!"0020".equals(getAttributeFlagValue(entityItem2, "STATUS"))) {
/*  86 */         throw new RfcAbrException("The status is not final, it will not promote this SWPRODSTRUCT");
/*     */       }
/*     */       
/*  89 */       EntityItem entityItem3 = this.entityList.getEntityGroup("MODEL").getEntityItem(0);
/*     */       
/*  91 */       EntityItem entityItem4 = this.entityList.getEntityGroup("SWFEATURE").getEntityItem(0);
/*     */       
/*  93 */       Vector vector = PokUtils.getAllLinkedEntities(entityItem2, "SWPRODSTRUCTAVAIL", "AVAIL");
/*  94 */       Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/*  95 */       Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/*  96 */       this.abr.addDebug("SWPRODSTRUCTAVAIL all availVct: " + vector.size() + " plannedavail: " + vector1.size() + " Last Order avail " + vector2
/*  97 */           .size());
/*     */       
/*  99 */       String str1 = getAttributeValue(entityItem3, "MACHTYPEATR");
/* 100 */       String str2 = getAttributeValue(entityItem3, "MODELATR");
/* 101 */       String str3 = getAttributeValue(entityItem4, "FEATURECODE");
/*     */ 
/*     */       
/* 104 */       String str4 = str1 + "-" + str2;
/*     */       
/* 106 */       List<EntityItem> list = getMACHTYPEsByType(str1);
/* 107 */       EntityItem entityItem5 = null;
/* 108 */       if (list.size() > 0) {
/* 109 */         entityItem5 = list.get(0);
/* 110 */         EntityList entityList = getEntityList(this.abr.getDatabase(), this.abr.getProfile(), "dummy", entityItem5
/* 111 */             .getEntityType(), entityItem5.getEntityID());
/* 112 */         this.abr.addDebug("getModelByVe2 EntityList for " + this.abr.getProfile() + " extract dummy contains the following entities: \n" + 
/* 113 */             PokUtils.outputList(entityList));
/* 114 */         entityItem5 = entityList.getParentEntityGroup().getEntityItem(0);
/*     */       } else {
/* 116 */         throw new RfcAbrException("There is no MACHTYPE for type " + str1 + ", it will not promote this SWPRODSTRUCT");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       TypeFeature typeFeature = new TypeFeature();
/* 123 */       boolean bool1 = isRPQ(entityItem4);
/* 124 */       if (bool1) {
/* 125 */         typeFeature.setFeatureID("Q");
/*     */       } else {
/* 127 */         typeFeature.setFeatureID("R");
/*     */       } 
/*     */       
/* 130 */       typeFeature.setFeature(str3);
/* 131 */       typeFeature.setType(str1);
/* 132 */       typeFeature.setDescription(getAttributeValue(entityItem4, "INVNAME"));
/* 133 */       typeFeature.setNoChargePurchase(getNoChargePurchase(entityItem4));
/* 134 */       typeFeature.setNetPriceMES(isNetPriceMES(entityItem3));
/*     */ 
/*     */       
/* 137 */       typeFeature.setItemReturned(false);
/* 138 */       typeFeature.setRemovalCharge(false);
/* 139 */       typeFeature.setCustomerSetup(false);
/* 140 */       typeFeature.setCapOnDemand(false);
/* 141 */       typeFeature.setApprovalRPQ(isApprovalRPQ(entityItem4));
/* 142 */       typeFeature.setUFLinked(false);
/* 143 */       String str5 = calculateRange(entityItem5, entityItem4, typeFeature);
/* 144 */       this.abr.addDebug("thisRange: " + str5);
/* 145 */       typeFeature.setFeatureRange(str5);
/* 146 */       this.abr.addDebug("TypeFeature: " + typeFeature.toString());
/* 147 */       this.abr.addDebug("TypeFeature  ApprovalRPQ:" + typeFeature.getApprovalRPQ());
/*     */       
/* 149 */       TypeModelFeature typeModelFeature = new TypeModelFeature();
/* 150 */       typeModelFeature.setType(str1);
/* 151 */       typeModelFeature.setModel(str2);
/* 152 */       typeModelFeature.setFeature(str3);
/* 153 */       typeModelFeature.setAnnDocNo(str4);
/* 154 */       typeModelFeature.setFeatureID(typeFeature.getFeatureID());
/* 155 */       this.abr.addDebug("TypeModelFeature: " + typeModelFeature.toString());
/*     */       
/* 157 */       String str6 = "C";
/* 158 */       this.abr.addDebug("PimsIdentity:" + str6);
/*     */ 
/*     */       
/* 161 */       bool = "Yes".equals(getAttributeFlagValue(entityItem2, "SYSFEEDRESEND"));
/* 162 */       this.abr.addDebug("Resend full: " + bool);
/*     */ 
/*     */       
/* 165 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = getAttributeHistory(entityItem2, "RFCABRSTATUS");
/* 166 */       boolean bool2 = existBefore(attributeChangeHistoryGroup, "0030");
/* 167 */       this.abr.addDebug("Exist passed RFCABRSTATUS before: " + bool2);
/* 168 */       if (!bool && bool2) {
/* 169 */         this.isTFPromoted = true;
/* 170 */         String str7 = getLatestValFromForAttributeValue(attributeChangeHistoryGroup, "0030");
/* 171 */         String str8 = this.abr.getCurrentTime();
/* 172 */         Profile profile = this.abr.switchRole("BHFEED");
/* 173 */         profile.setValOnEffOn(str7, str7);
/* 174 */         profile.setEndOfDay(str8);
/* 175 */         profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 176 */         profile.setLoginTime(str8);
/* 177 */         entityList1 = getEntityList(profile);
/* 178 */         this.abr.addDebug("EntityList for T1 " + profile.getValOn() + " extract " + getVeName() + " contains the following entities: \n" + 
/* 179 */             PokUtils.outputList(entityList1));
/*     */ 
/*     */         
/* 182 */         entityItem1 = entityList1.getParentEntityGroup().getEntityItem(0);
/*     */         
/* 184 */         this.isTFChanged = isTypeFeatureChanged(entityItem1, entityItem2);
/*     */       } 
/*     */       
/* 187 */       if (vector1.size() == 0)
/*     */       {
/* 189 */         throw new RfcAbrException("There is no Planned Availability for " + entityItem2.getKey() + ", it will not promote this SWPRODSTRUCT");
/*     */       }
/*     */       byte b;
/* 192 */       for (b = 0; b < vector1.size(); b++) {
/* 193 */         EntityItem entityItem = vector1.get(b);
/* 194 */         this.abr.addDebug("Promote Type Feature for " + entityItem.getKey());
/*     */         
/* 196 */         String str = getTMFAnnDate(entityItem2, entityItem3, entityItem4, entityItem);
/* 197 */         this.abr.addDebug("T2 ANNDATE: " + str);
/*     */         
/* 199 */         Vector vector3 = PokUtils.getAllLinkedEntities(entityItem, "AVAILGAA", "GENERALAREA");
/* 200 */         List<SalesOrgPlants> list1 = getAllSalesOrgPlant(vector3);
/*     */         
/* 202 */         if (this.isTFPromoted) {
/* 203 */           if (entityList1 != null && entityItem1 != null) {
/* 204 */             EntityItem entityItem6 = entityList1.getEntityGroup("SWFEATURE").getEntityItem(0);
/* 205 */             EntityItem entityItem7 = entityList1.getEntityGroup("MODEL").getEntityItem(0);
/* 206 */             Vector vector4 = PokUtils.getAllLinkedEntities(entityItem1, "SWPRODSTRUCTAVAIL", "AVAIL");
/* 207 */             Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(vector4, "AVAILTYPE", "146");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 212 */             EntityItem entityItem8 = getEntityItemAtT1(vector5, entityItem);
/* 213 */             String str7 = getTMFAnnDate(entityItem1, entityItem7, entityItem6, entityItem8);
/* 214 */             this.abr.addDebug("T1 ANNDATE: " + str7);
/* 215 */             this.isTFGeoChanged = !str.equals(str7);
/*     */             
/* 217 */             if (!this.isTFGeoChanged) {
/* 218 */               List<String> list2 = getNewCountries(vector5, entityItem);
/* 219 */               this.isTFGeoPromoted = (list2.size() == 0);
/* 220 */               if (!this.isTFGeoPromoted) {
/* 221 */                 list1 = getAllSalesOrgPlantByCountryList(list1, list2);
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             
/* 226 */             this.abr.addDebug("t1EntityList not null:" + ((entityList1 != null) ? 1 : 0) + " t1TmfItem not null:" + ((entityItem1 != null) ? 1 : 0));
/*     */           } 
/*     */         }
/*     */         
/* 230 */         this.abr.addDebug("isTFPromoted: " + this.isTFPromoted + " isTFChanged: " + this.isTFChanged + " isTFGeoPromoted: " + this.isTFGeoPromoted + " isTFGeoChanged: " + this.isTFGeoChanged);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 235 */         CHWAnnouncement cHWAnnouncement = new CHWAnnouncement();
/* 236 */         cHWAnnouncement.setAnnDocNo(str4);
/* 237 */         cHWAnnouncement.setAnnouncementType("New");
/* 238 */         cHWAnnouncement.setSegmentAcronym(getSegmentAcronymForAnn(entityItem2));
/* 239 */         this.abr.addDebug("CHWAnnouncement: " + cHWAnnouncement.toString());
/*     */         
/* 241 */         CHWGeoAnn cHWGeoAnn = new CHWGeoAnn();
/* 242 */         cHWGeoAnn.setAnnouncementDate((new SimpleDateFormat("yyyy-MM-dd")).parse(str));
/* 243 */         this.abr.addDebug("CHWAnnouncementGEO: " + cHWGeoAnn.toString());
/*     */         
/* 245 */         promoteTypeModelFeature(typeFeature, typeModelFeature, cHWAnnouncement, cHWGeoAnn, list1, str6, str5);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 250 */       this.abr.addDebug("Start promote WDFM(Withdraw From Market) announcement for PRODSTRUCT");
/* 251 */       b = 0;
/* 252 */       boolean bool3 = false;
/* 253 */       if (vector2.size() > 0) {
/* 254 */         for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 255 */           EntityItem entityItem = vector2.get(b1);
/*     */           
/* 257 */           this.abr.addDebug("Promote WDFM Announcement for " + entityItem.getKey());
/*     */           
/* 259 */           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 260 */           Date date = simpleDateFormat.parse(getAttributeValue(entityItem, "EFFECTIVEDATE"));
/*     */           
/* 262 */           Vector vector3 = PokUtils.getAllLinkedEntities(entityItem, "AVAILGAA", "GENERALAREA");
/* 263 */           List<SalesOrgPlants> list1 = getAllSalesOrgPlant(vector3);
/*     */           
/* 265 */           if (!bool && bool2 && 
/* 266 */             entityList1 != null && entityItem1 != null) {
/* 267 */             Vector vector4 = PokUtils.getAllLinkedEntities(entityItem1, "SWPRODSTRUCTAVAIL", "AVAIL");
/* 268 */             Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(vector4, "AVAILTYPE", "149");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 273 */             bool3 = isSameEffectiveDate(vector5, entityItem);
/* 274 */             if (!bool3) {
/* 275 */               List<String> list2 = getNewCountries(vector5, entityItem);
/* 276 */               if (list2.size() > 0) {
/* 277 */                 b = 0;
/* 278 */                 list1 = getAllSalesOrgPlantByCountryList(list1, list2);
/*     */               } else {
/*     */                 
/* 281 */                 b = 1;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 286 */           this.abr.addDebug("isTfwPromoted: " + b + " isTfwChanged: " + bool3);
/* 287 */           if (b == 0 || bool3) {
/* 288 */             LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(typeModelFeature);
/* 289 */             for (SalesOrgPlants salesOrgPlants : list1) {
/* 290 */               String str = salesOrgPlants.getSalesorg();
/* 291 */               LifecycleData lifecycleData = this.rdhRestProxy.r200(lifecycleDataGenerator.getMaterial(), lifecycleDataGenerator.getVarCond(), typeModelFeature
/* 292 */                   .getAnnDocNo(), "wdfm", str6, str);
/* 293 */               this.abr.addDebug("Call r200 successfully for SalesOrg " + str + " " + lifecycleData);
/* 294 */               updateWDFMLifecyle(lifecycleData, lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), date, typeModelFeature
/* 295 */                   .getAnnDocNo(), str6, str);
/* 296 */               this.abr.addDebug("updateWDFMLifecyle successfully for SalesOrg " + str);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 301 */       this.abr.addDebug("End promote WDFM(Withdraw From Market) announcement for PRODSTRUCT");
/*     */       
/* 303 */       if (this.needUpdatParkingTable) {
/* 304 */         if (needReleaseParkingTable()) {
/* 305 */           this.rdhRestProxy.r144(str4, "R", str6);
/*     */         } else {
/* 307 */           this.rdhRestProxy.r144(str4, "H", str6);
/*     */         } 
/*     */       }
/*     */     } finally {
/*     */       
/* 312 */       if (bool && entityItem2 != null) {
/* 313 */         setFlagValue("SYSFEEDRESEND", "No", entityItem2);
/*     */       }
/*     */       
/* 316 */       if (entityList1 != null) {
/* 317 */         entityList1.dereference();
/* 318 */         entityList1 = null;
/*     */       } 
/* 320 */       if (entityList3 != null) {
/* 321 */         entityList3.dereference();
/* 322 */         entityList3 = null;
/*     */       } 
/* 324 */       if (this.entityList != null) {
/* 325 */         this.entityList.dereference();
/* 326 */         this.entityList = null;
/*     */       } 
/* 328 */       if (entityList2 != null) {
/* 329 */         entityList2.dereference();
/* 330 */         entityList2 = null;
/*     */       } 
/* 332 */       this.abr.addDebug("End processThis()");
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getVeName() {
/* 337 */     return "RFCSWPRODSTRUCT";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void promoteTypeModelFeature(TypeFeature paramTypeFeature, TypeModelFeature paramTypeModelFeature, CHWAnnouncement paramCHWAnnouncement, CHWGeoAnn paramCHWGeoAnn, List<SalesOrgPlants> paramList, String paramString1, String paramString2) throws RfcAbrException, Exception {
/* 343 */     this.abr.addDebug("Start promote Type Feature");
/* 344 */     if (!this.isTFPromoted || this.isTFChanged || !this.isTFGeoPromoted || this.isTFGeoChanged) {
/* 345 */       if ("Q".equals(paramTypeFeature.getFeatureID())) {
/* 346 */         this.abr.addDebug("Start RPQ Feature");
/* 347 */         this.rdhRestProxy.r126(paramTypeFeature, paramCHWAnnouncement, paramString1);
/* 348 */         if (!this.isTFPromoted) {
/* 349 */           callr127WithAccessAuthority(paramTypeFeature, paramString2, paramCHWAnnouncement, paramString1);
/*     */         }
/* 351 */         this.rdhRestProxy.r128(paramTypeFeature, paramString2, paramCHWAnnouncement, paramString1);
/* 352 */         this.rdhRestProxy.r152(paramTypeFeature, paramCHWAnnouncement, paramString1);
/* 353 */         this.abr.addDebug("End RPQ Feature");
/*     */       } else {
/* 355 */         this.abr.addDebug("Start not RPQ Feature");
/* 356 */         this.rdhRestProxy.r129(paramTypeFeature, paramTypeModelFeature.getModel(), paramCHWAnnouncement, paramString1);
/* 357 */         if (paramTypeFeature.ifAlphaNumeric()) {
/* 358 */           callr130WithAccessAuthority(paramTypeFeature.getType(), paramTypeModelFeature.getModel(), paramString2, paramCHWAnnouncement, paramString1);
/* 359 */           this.rdhRestProxy.r176(paramTypeFeature.getType(), paramTypeModelFeature.getModel(), paramString2, "NEW", paramCHWAnnouncement, paramString1);
/* 360 */           this.rdhRestProxy.r176(paramTypeFeature.getType(), paramTypeModelFeature.getModel(), paramString2, "UPG", paramCHWAnnouncement, paramString1);
/*     */         } 
/* 362 */         this.rdhRestProxy.r134(paramTypeFeature, paramTypeModelFeature.getModel(), paramCHWAnnouncement, paramString1);
/* 363 */         this.rdhRestProxy.r153(paramTypeFeature, paramTypeModelFeature.getModel(), paramCHWAnnouncement, paramString1);
/* 364 */         this.abr.addDebug("End not RPQ Feature");
/*     */       } 
/*     */       
/* 367 */       this.abr.addDebug("Start updateAnnLifecyle for Type Feature");
/* 368 */       for (SalesOrgPlants salesOrgPlants : paramList) {
/* 369 */         String str = salesOrgPlants.getSalesorg();
/* 370 */         LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(paramTypeFeature);
/* 371 */         updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), paramCHWGeoAnn.getAnnouncementDate(), paramCHWAnnouncement
/* 372 */             .getAnnDocNo(), paramCHWAnnouncement.getAnnouncementType(), paramString1, str);
/*     */       } 
/* 374 */       this.abr.addDebug("End updateAnnLifecyle for Type Feature");
/*     */     } 
/*     */     
/* 377 */     if ("Q".equals(paramTypeFeature.getFeatureID())) {
/* 378 */       this.abr.addDebug("Start RPQ Feature");
/* 379 */       this.rdhRestProxy.r138(paramTypeFeature, "NEW", paramCHWAnnouncement, paramString1);
/* 380 */       this.rdhRestProxy.r138(paramTypeFeature, "UPG", paramCHWAnnouncement, paramString1);
/* 381 */       boolean bool = this.rdhRestProxy.r204(paramTypeFeature.getType() + "MTC");
/* 382 */       if (bool) {
/* 383 */         this.rdhRestProxy.r138(paramTypeFeature, "MTC", paramCHWAnnouncement, paramString1);
/*     */       }
/* 385 */       this.abr.addDebug("End RPQ Feature");
/*     */     } 
/* 387 */     this.abr.addDebug("End promote Type Feature");
/*     */ 
/*     */     
/* 390 */     if (!this.isTFPromoted || !this.isTFGeoPromoted || this.isTFGeoChanged) {
/* 391 */       this.abr.addDebug("Start updateAnnLifecyle for Type Model Feature");
/* 392 */       for (SalesOrgPlants salesOrgPlants : paramList) {
/* 393 */         String str = salesOrgPlants.getSalesorg();
/* 394 */         LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(paramTypeModelFeature);
/* 395 */         updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), paramCHWGeoAnn.getAnnouncementDate(), paramTypeModelFeature
/* 396 */             .getAnnDocNo(), paramCHWAnnouncement.getAnnouncementType(), paramString1, str);
/*     */       } 
/* 398 */       this.abr.addDebug("End updateAnnLifecyle for Type Model Feature");
/*     */     } 
/*     */     
/* 401 */     this.needUpdatParkingTable = true;
/* 402 */     this.abr.addDebug("set needUpdatParkingTable to " + this.needUpdatParkingTable);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTypeFeatureChanged(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws RfcAbrException {
/* 407 */     if (isDiff(paramEntityItem1, paramEntityItem2, tmfMarkChangedAttrs)) {
/* 408 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 412 */     EntityItem entityItem1 = null;
/* 413 */     EntityItem entityItem2 = null;
/* 414 */     List<EntityItem> list1 = getLinkedRelator(paramEntityItem1, "MODEL");
/* 415 */     if (list1.size() > 0) {
/* 416 */       entityItem1 = list1.get(0);
/*     */     } else {
/* 418 */       throw new RfcAbrException("isTypeFeatureChanged not found MODEL at T1 for " + paramEntityItem1.getKey());
/*     */     } 
/* 420 */     List<EntityItem> list2 = getLinkedRelator(paramEntityItem2, "MODEL");
/* 421 */     if (list2.size() > 0) {
/* 422 */       entityItem2 = list2.get(0);
/*     */     } else {
/* 424 */       throw new RfcAbrException("isTypeFeatureChanged not found MODEL at T2 for " + paramEntityItem1.getKey());
/*     */     } 
/* 426 */     boolean bool1 = isNetPriceMES(entityItem1);
/* 427 */     boolean bool2 = isNetPriceMES(entityItem2);
/* 428 */     this.abr.addDebug("isTypeFeatureChanged NetPriceMES T1: " + bool1 + " T2: " + bool2);
/* 429 */     if (bool1 != bool2) {
/* 430 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 434 */     EntityItem entityItem3 = null;
/* 435 */     EntityItem entityItem4 = null;
/* 436 */     List<EntityItem> list3 = getLinkedRelator(paramEntityItem1, "SWFEATURE");
/* 437 */     if (list3.size() > 0) {
/* 438 */       entityItem3 = list3.get(0);
/*     */     } else {
/* 440 */       throw new RfcAbrException("isTypeFeatureChanged not found SWFEATURE at T1 for " + paramEntityItem1.getKey());
/*     */     } 
/* 442 */     List<EntityItem> list4 = getLinkedRelator(paramEntityItem2, "SWFEATURE");
/* 443 */     if (list4.size() > 0) {
/* 444 */       entityItem4 = list4.get(0);
/*     */     } else {
/* 446 */       throw new RfcAbrException("isTypeFeatureChanged not found SWFEATURE at T2 for " + paramEntityItem1.getKey());
/*     */     } 
/* 448 */     if (isDiff(entityItem3, entityItem4, feaMarkChangedAttrs)) {
/* 449 */       return true;
/*     */     }
/* 451 */     boolean bool3 = isRPQ(entityItem3);
/* 452 */     boolean bool4 = isRPQ(entityItem4);
/* 453 */     if (bool3 != bool4) {
/* 454 */       this.abr.addDebug("isTypeFeatureChanged isRPQ T1: " + bool3 + " T2: " + bool4);
/* 455 */       return true;
/*     */     } 
/* 457 */     boolean bool5 = getNoChargePurchase(entityItem3);
/* 458 */     boolean bool6 = getNoChargePurchase(entityItem4);
/* 459 */     if (bool5 != bool6) {
/* 460 */       this.abr.addDebug("isTypeFeatureChanged NoChargePurchase T1: " + bool5 + " T2: " + bool6);
/* 461 */       return true;
/*     */     } 
/* 463 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isSameEffectiveDate(Vector paramVector, EntityItem paramEntityItem) throws RfcAbrException {
/* 467 */     EntityItem entityItem = getEntityItemAtT1(paramVector, paramEntityItem);
/* 468 */     if (entityItem != null) {
/* 469 */       String str1 = getAttributeValue(entityItem, "EFFECTIVEDATE");
/* 470 */       String str2 = getAttributeValue(paramEntityItem, "EFFECTIVEDATE");
/* 471 */       if (!str2.equals(str1)) {
/* 472 */         this.abr.addDebug("isTypeFeatureGeoChanged true T1 Date " + str1 + " T2 Date " + str2);
/* 473 */         return true;
/*     */       } 
/*     */     } else {
/* 476 */       this.abr.addDebug("isTypeFeatureGeoChanged true AVAIL is null at T1 for " + paramEntityItem.getKey());
/* 477 */       return true;
/*     */     } 
/* 479 */     this.abr.addDebug("isTypeFeatureGeoChanged false");
/* 480 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getTMFAnnDate(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, EntityItem paramEntityItem4) throws RfcAbrException {
/* 490 */     String str = null;
/* 491 */     if (paramEntityItem4 != null) {
/* 492 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem4, "AVAILANNA", "ANNOUNCEMENT");
/* 493 */       if (vector != null && vector.size() > 0) {
/* 494 */         EntityItem entityItem = vector.get(0);
/* 495 */         str = getAttributeValue(entityItem, "ANNDATE");
/*     */       } 
/*     */     } 
/* 498 */     if (str == null || "".equals(str)) {
/* 499 */       str = getAttributeValue(paramEntityItem2, "ANNDATE");
/*     */     }
/* 501 */     if (str == null || "".equals(str)) {
/* 502 */       throw new RfcAbrException("ANNDATE is null, it will not promote this PRODSTRUCT");
/*     */     }
/* 504 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RFCSWPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */