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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RFCPRODSTRUCTABR
/*     */   extends RfcAbrAdapter
/*     */ {
/*     */   private static final String TYPERANGES_REX_SEPARATOR = "\\|";
/*     */   private static final String TYPERANGES_SEPARATOR = "|";
/*     */   private static final String TYPERANGE_SEPARATOR = "-";
/*     */   private boolean isTFPromoted = false;
/*     */   private boolean isTFChanged = false;
/*     */   private boolean isTFGeoPromoted = false;
/*     */   private boolean isTFGeoChanged = false;
/*     */   private boolean needUpdatParkingTable = false;
/* 129 */   private static List<String> tmfMarkChangedAttrs = new ArrayList<>(); static {
/* 130 */     tmfMarkChangedAttrs.add("INSTALL");
/* 131 */   } private static List<String> feaMarkChangedAttrs = new ArrayList<>(); static {
/* 132 */     feaMarkChangedAttrs.add("INVNAME");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RFCPRODSTRUCTABR(RFCABRSTATUS paramRFCABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
/* 138 */     super(paramRFCABRSTATUS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processThis() throws RfcAbrException, Exception {
/* 143 */     this.abr.addDebug("Start processThis()");
/* 144 */     EntityList entityList1 = null;
/* 145 */     EntityItem entityItem1 = null;
/* 146 */     EntityList entityList2 = null;
/* 147 */     EntityList entityList3 = null;
/* 148 */     EntityItem entityItem2 = null;
/* 149 */     boolean bool = false;
/* 150 */     EntityItem entityItem3 = null;
/* 151 */     TypeFeature typeFeature = null;
/*     */ 
/*     */     
/*     */     try {
/* 155 */       entityItem2 = getRooEntityItem();
/*     */       
/* 157 */       if (!"0020".equals(getAttributeFlagValue(entityItem2, "STATUS"))) {
/* 158 */         throw new RfcAbrException("The status is not final, it will not promote this " + entityItem2.getKey());
/*     */       }
/*     */       
/* 161 */       EntityItem entityItem4 = this.entityList.getEntityGroup("MODEL").getEntityItem(0);
/*     */       
/* 163 */       EntityItem entityItem5 = this.entityList.getEntityGroup("FEATURE").getEntityItem(0);
/*     */       
/* 165 */       Vector vector = PokUtils.getAllLinkedEntities(entityItem2, "OOFAVAIL", "AVAIL");
/* 166 */       Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/* 167 */       Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/* 168 */       this.abr.addDebug("OOFAVAIL all availVct: " + vector.size() + " plannedavail: " + vector1.size() + " Last Order avail " + vector2
/* 169 */           .size());
/*     */       
/* 171 */       String str1 = getAttributeValue(entityItem4, "MACHTYPEATR");
/* 172 */       String str2 = getAttributeValue(entityItem4, "MODELATR");
/* 173 */       String str3 = getAttributeValue(entityItem5, "FEATURECODE");
/*     */       
/* 175 */       String str4 = str1 + "-" + str2;
/*     */       
/* 177 */       List<EntityItem> list = getMACHTYPEsByType(str1);
/*     */       
/* 179 */       if (list.size() > 0) {
/* 180 */         entityItem3 = list.get(0);
/* 181 */         EntityList entityList = getEntityList(this.abr.getDatabase(), this.abr.getProfile(), "dummy", entityItem3
/* 182 */             .getEntityType(), entityItem3.getEntityID());
/* 183 */         this.abr.addDebug("getModelByVe2 EntityList for " + this.abr.getProfile() + " extract dummy contains the following entities: \n" + 
/* 184 */             PokUtils.outputList(entityList));
/* 185 */         entityItem3 = entityList.getParentEntityGroup().getEntityItem(0);
/*     */       } else {
/* 187 */         throw new RfcAbrException("There is no MACHTYPE for type " + str1 + ", it will not promote this PRODSTRUCT");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       typeFeature = new TypeFeature();
/* 194 */       boolean bool1 = isRPQ(entityItem5);
/* 195 */       if (bool1) {
/* 196 */         typeFeature.setFeatureID("Q");
/*     */       } else {
/* 198 */         typeFeature.setFeatureID("R");
/*     */       } 
/* 200 */       typeFeature.setFeature(str3);
/* 201 */       typeFeature.setType(str1);
/* 202 */       typeFeature.setDescription(getAttributeValue(entityItem5, "INVNAME"));
/* 203 */       typeFeature.setNoChargePurchase(getNoChargePurchase(entityItem5));
/* 204 */       typeFeature.setNetPriceMES(isNetPriceMES(entityItem4));
/* 205 */       typeFeature.setItemReturned(getItemReturn(entityItem2));
/* 206 */       typeFeature.setRemovalCharge(getRemovalCharge(entityItem2));
/* 207 */       typeFeature.setCustomerSetup("CE".equals(getAttributeValue(entityItem2, "INSTALL")));
/* 208 */       typeFeature.setCapOnDemand("202".equals(getAttributeFlagValue(entityItem5, "HWFCCAT")));
/* 209 */       typeFeature.setApprovalRPQ(isApprovalRPQ(entityItem5));
/* 210 */       typeFeature.setUFLinked(false);
/* 211 */       String str5 = calculateRange(entityItem3, entityItem5, typeFeature);
/* 212 */       this.abr.addDebug("thisRange: " + str5);
/* 213 */       typeFeature.setFeatureRange(str5);
/* 214 */       this.abr.addDebug("TypeFeature: " + typeFeature.toString());
/* 215 */       this.abr.addDebug("TypeFeature  ApprovalRPQ:" + typeFeature.getApprovalRPQ());
/*     */       
/* 217 */       TypeModelFeature typeModelFeature = new TypeModelFeature();
/* 218 */       typeModelFeature.setType(str1);
/* 219 */       typeModelFeature.setModel(str2);
/* 220 */       typeModelFeature.setFeature(str3);
/* 221 */       typeModelFeature.setAnnDocNo(str4);
/* 222 */       typeModelFeature.setFeatureID(typeFeature.getFeatureID());
/* 223 */       this.abr.addDebug("TypeModelFeature: " + typeModelFeature.toString());
/*     */       
/* 225 */       String str6 = "C";
/* 226 */       this.abr.addDebug("PimsIdentity:" + str6);
/*     */       
/* 228 */       List<SalesOrgPlants> list1 = null;
/*     */       
/* 230 */       bool = "Yes".equals(getAttributeFlagValue(entityItem2, "SYSFEEDRESEND"));
/* 231 */       this.abr.addDebug("Resend full: " + bool);
/*     */ 
/*     */       
/* 234 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = getAttributeHistory(entityItem2, "RFCABRSTATUS");
/* 235 */       boolean bool2 = existBefore(attributeChangeHistoryGroup, "0030");
/* 236 */       this.abr.addDebug("Exist passed RFCABRSTATUS before: " + bool2);
/* 237 */       if (!bool && bool2) {
/* 238 */         this.isTFPromoted = true;
/* 239 */         String str7 = getLatestValFromForAttributeValue(attributeChangeHistoryGroup, "0030");
/* 240 */         String str8 = this.abr.getCurrentTime();
/* 241 */         Profile profile = this.abr.switchRole("BHFEED");
/* 242 */         profile.setValOnEffOn(str7, str7);
/* 243 */         profile.setEndOfDay(str8);
/* 244 */         profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 245 */         profile.setLoginTime(str8);
/* 246 */         entityList1 = getEntityList(profile);
/* 247 */         this.abr.addDebug("EntityList for T1 " + profile.getValOn() + " extract " + getVeName() + " contains the following entities: \n" + 
/* 248 */             PokUtils.outputList(entityList1));
/*     */ 
/*     */         
/* 251 */         entityItem1 = entityList1.getParentEntityGroup().getEntityItem(0);
/*     */         
/* 253 */         this.isTFChanged = isTypeFeatureChanged(entityItem1, entityItem2);
/*     */       } 
/*     */       
/* 256 */       if (vector1.size() == 0) {
/* 257 */         this.abr.addDebug("TMF doesné¥æª link OFFAVAIL (Planed AVAIL)");
/* 258 */         String str7 = this.abr.getCurrentTime();
/* 259 */         Profile profile = this.abr.switchRole("BHFEED");
/* 260 */         profile.setValOnEffOn(str7, str7);
/* 261 */         profile.setEndOfDay(str7);
/* 262 */         profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 263 */         profile.setLoginTime(str7);
/* 264 */         entityList2 = getEntityList(this.abr.getDatabase(), profile, "RFCPRODSTRUCT2", entityItem4.getEntityType(), entityItem4
/* 265 */             .getEntityID());
/* 266 */         this.abr.addDebug("EntityList for " + profile.getValOn() + " extract RFCPRODSTRUCT2 contains the following entities: \n" + 
/* 267 */             PokUtils.outputList(entityList2));
/*     */         
/* 269 */         entityItem4 = entityList2.getParentEntityGroup().getEntityItem(0);
/* 270 */         Vector vector3 = PokUtils.getAllLinkedEntities(entityItem4, "MODELAVAIL", "AVAIL");
/* 271 */         Vector<EntityItem> vector4 = PokUtils.getEntitiesWithMatchedAttr(vector3, "AVAILTYPE", "146");
/*     */         
/* 273 */         this.abr.addDebug("MODELAVAIL all availVct: " + vector3
/* 274 */             .size() + " plannedavail: " + vector4.size());
/*     */ 
/*     */         
/* 277 */         String str8 = getTMFAnnDate(entityItem2, entityItem4, entityItem5, (EntityItem)null);
/*     */         
/* 279 */         List<SalesOrgPlants> list2 = null;
/* 280 */         if (vector4.size() == 0) {
/*     */           
/* 282 */           Vector vector5 = searchAllGeneralAreas();
/* 283 */           list2 = getAllSalesOrgPlant(vector5);
/* 284 */           if (this.isTFPromoted) {
/* 285 */             this.isTFGeoPromoted = false;
/* 286 */             this.isTFGeoChanged = true;
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 294 */           Vector vector5 = new Vector();
/* 295 */           for (byte b = 0; b < vector4.size(); b++) {
/* 296 */             EntityItem entityItem = vector4.get(b);
/* 297 */             vector5.addAll(PokUtils.getAllLinkedEntities(entityItem, "AVAILGAA", "GENERALAREA"));
/*     */           } 
/* 299 */           list2 = getAllSalesOrgPlant(vector5);
/* 300 */           List<String> list3 = getCountryListForRPQ(vector4, entityItem5);
/* 301 */           list2 = getAllSalesOrgPlantByCountryList(list2, list3);
/*     */ 
/*     */           
/* 304 */           if (this.isTFPromoted) {
/* 305 */             if (entityList1 != null && entityItem1 != null) {
/* 306 */               EntityItem entityItem6 = entityList1.getEntityGroup("FEATURE").getEntityItem(0);
/* 307 */               EntityItem entityItem7 = entityList1.getEntityGroup("MODEL").getEntityItem(0);
/* 308 */               entityList3 = getEntityList(this.abr.getDatabase(), entityItem7.getProfile(), "RFCPRODSTRUCT2", entityItem7
/* 309 */                   .getEntityType(), entityItem7.getEntityID());
/* 310 */               this.abr.addDebug("EntityList for " + entityItem7.getProfile().getValOn() + " extract RFCPRODSTRUCT2 contains the following entities: \n" + 
/*     */                   
/* 312 */                   PokUtils.outputList(this.entityList));
/* 313 */               entityItem7 = entityList3.getParentEntityGroup().getEntityItem(0);
/* 314 */               Vector vector6 = PokUtils.getAllLinkedEntities(entityItem7, "MODELAVAIL", "AVAIL");
/* 315 */               Vector vector7 = PokUtils.getEntitiesWithMatchedAttr(vector6, "AVAILTYPE", "146");
/*     */               
/* 317 */               this.abr.addDebug("MODELAVAIL T1 all availVct: " + vector6.size() + " plannedavail: " + vector7
/* 318 */                   .size());
/* 319 */               List<String> list4 = getCountryListForRPQ(vector7, entityItem6);
/* 320 */               list3.removeAll(list4);
/*     */ 
/*     */               
/* 323 */               String str = getTMFAnnDate(entityItem1, entityItem7, entityItem6, (EntityItem)null);
/* 324 */               this.isTFGeoChanged = !str8.equals(str);
/* 325 */               if (!this.isTFGeoChanged) {
/* 326 */                 this.isTFGeoPromoted = (list3.size() == 0);
/*     */                 
/* 328 */                 if (!this.isTFGeoPromoted) {
/* 329 */                   list2 = getAllSalesOrgPlantByCountryList(list2, list3);
/*     */                 }
/*     */               } 
/*     */             } else {
/*     */               
/* 334 */               this.abr.addDebug("t1EntityList not null:" + ((entityList1 != null) ? 1 : 0) + " t1TmfItem not null:" + ((entityItem1 != null) ? 1 : 0));
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 339 */         list1 = list2;
/* 340 */         this.abr.addDebug("isTFPromoted: " + this.isTFPromoted + " isTFChanged: " + this.isTFChanged + " isTFGeoPromoted: " + this.isTFGeoPromoted + " isTFGeoChanged: " + this.isTFGeoChanged);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 345 */         CHWAnnouncement cHWAnnouncement = new CHWAnnouncement();
/* 346 */         cHWAnnouncement.setAnnDocNo(str4);
/* 347 */         cHWAnnouncement.setAnnouncementType("New");
/* 348 */         cHWAnnouncement.setSegmentAcronym(getSegmentAcronymForAnn(entityItem2));
/* 349 */         this.abr.addDebug("CHWAnnouncement: " + cHWAnnouncement.toString());
/*     */         
/* 351 */         CHWGeoAnn cHWGeoAnn = new CHWGeoAnn();
/* 352 */         cHWGeoAnn.setAnnouncementDate((new SimpleDateFormat("yyyy-MM-dd")).parse(str8));
/* 353 */         this.abr.addDebug("CHWAnnouncementGEO: " + cHWGeoAnn.toString());
/*     */         
/* 355 */         promoteTypeModelFeature(typeFeature, typeModelFeature, cHWAnnouncement, cHWGeoAnn, list2, str6, str5);
/*     */       } else {
/* 357 */         for (byte b = 0; b < vector1.size(); b++) {
/* 358 */           EntityItem entityItem = vector1.get(b);
/* 359 */           this.abr.addDebug("Promote Type Feature for " + entityItem.getKey());
/*     */ 
/*     */           
/* 362 */           Vector vector3 = PokUtils.getAllLinkedEntities(entityItem, "AVAILGAA", "GENERALAREA");
/* 363 */           List<SalesOrgPlants> list2 = getAllSalesOrgPlant(vector3);
/* 364 */           if (this.isTFPromoted) {
/* 365 */             if (entityList1 != null && entityItem1 != null) {
/* 366 */               Vector vector4 = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "AVAIL");
/* 367 */               Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(vector4, "AVAILTYPE", "146");
/*     */ 
/*     */               
/* 370 */               List<String> list3 = getNewCountries(vector5, entityItem);
/*     */ 
/*     */               
/* 373 */               this.isTFGeoChanged = isTypeFeatureGeoChanged(vector5, entityItem);
/* 374 */               if (!this.isTFGeoChanged) {
/* 375 */                 this.isTFGeoPromoted = (list3.size() == 0);
/* 376 */                 if (!this.isTFGeoPromoted) {
/* 377 */                   list2 = getAllSalesOrgPlantByCountryList(list2, list3);
/*     */                 }
/*     */               } 
/*     */             } else {
/*     */               
/* 382 */               this.abr.addDebug("t1EntityList not null:" + ((entityList1 != null) ? 1 : 0) + " t1TmfItem not null:" + ((entityItem1 != null) ? 1 : 0));
/*     */             } 
/*     */           }
/*     */           
/* 386 */           this.abr.addDebug("isTFPromoted: " + this.isTFPromoted + " isTFChanged: " + this.isTFChanged + " isTFGeoPromoted: " + this.isTFGeoPromoted + " isTFGeoChanged: " + this.isTFGeoChanged);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 391 */           CHWAnnouncement cHWAnnouncement = new CHWAnnouncement();
/* 392 */           cHWAnnouncement.setAnnDocNo(str4);
/* 393 */           cHWAnnouncement.setAnnouncementType("New");
/* 394 */           cHWAnnouncement.setSegmentAcronym(getSegmentAcronymForAnn(entityItem2));
/* 395 */           this.abr.addDebug("CHWAnnouncement: " + cHWAnnouncement.toString());
/*     */           
/* 397 */           CHWGeoAnn cHWGeoAnn = new CHWGeoAnn();
/* 398 */           cHWGeoAnn.setAnnouncementDate((new SimpleDateFormat("yyyy-MM-dd"))
/* 399 */               .parse(getTMFAnnDate(entityItem2, entityItem4, entityItem5, entityItem)));
/* 400 */           this.abr.addDebug("CHWAnnouncementGEO: " + cHWGeoAnn.toString());
/*     */           
/* 402 */           promoteTypeModelFeature(typeFeature, typeModelFeature, cHWAnnouncement, cHWGeoAnn, list2, str6, str5);
/*     */         } 
/*     */       } 
/*     */       
/* 406 */       this.abr.addDebug("Start promote WDFM(Withdraw From Market) announcement for PRODSTRUCT");
/* 407 */       boolean bool3 = false;
/* 408 */       boolean bool4 = false;
/* 409 */       if (vector1.size() > 0 && vector2.size() > 0) {
/* 410 */         for (byte b = 0; b < vector2.size(); b++) {
/* 411 */           EntityItem entityItem = vector2.get(b);
/*     */           
/* 413 */           this.abr.addDebug("Promote WDFM Announcement for " + entityItem.getKey());
/*     */           
/* 415 */           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 416 */           Date date = simpleDateFormat.parse(getAttributeValue(entityItem, "EFFECTIVEDATE"));
/*     */           
/* 418 */           Vector vector3 = PokUtils.getAllLinkedEntities(entityItem, "AVAILGAA", "GENERALAREA");
/* 419 */           List<SalesOrgPlants> list2 = getAllSalesOrgPlant(vector3);
/*     */           
/* 421 */           if (!bool && bool2 && 
/* 422 */             entityList1 != null && entityItem1 != null) {
/* 423 */             Vector vector4 = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "AVAIL");
/* 424 */             Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(vector4, "AVAILTYPE", "149");
/*     */ 
/*     */ 
/*     */             
/* 428 */             bool4 = isTypeFeatureGeoChanged(vector5, entityItem);
/* 429 */             if (!bool4) {
/* 430 */               List<String> list3 = getNewCountries(vector5, entityItem);
/* 431 */               if (list3.size() > 0) {
/* 432 */                 bool3 = false;
/* 433 */                 list2 = getAllSalesOrgPlantByCountryList(list2, list3);
/*     */               } else {
/*     */                 
/* 436 */                 bool3 = true;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 441 */           this.abr.addDebug("isTfwPromoted: " + bool3 + " isTfwChanged: " + bool4);
/* 442 */           if (!bool3 || bool4) {
/* 443 */             LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(typeModelFeature);
/* 444 */             for (SalesOrgPlants salesOrgPlants : list2) {
/* 445 */               String str = salesOrgPlants.getSalesorg();
/* 446 */               LifecycleData lifecycleData = this.rdhRestProxy.r200(lifecycleDataGenerator.getMaterial(), lifecycleDataGenerator.getVarCond(), typeModelFeature
/* 447 */                   .getAnnDocNo(), "wdfm", str6, str);
/* 448 */               this.abr.addDebug("Call r200 successfully for SalesOrg " + str + " " + lifecycleData);
/* 449 */               updateWDFMLifecyle(lifecycleData, lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), date, typeModelFeature
/* 450 */                   .getAnnDocNo(), str6, str);
/* 451 */               this.abr.addDebug("updateWDFMLifecyle successfully for SalesOrg " + str);
/*     */             } 
/*     */           } 
/*     */         } 
/* 455 */       } else if (vector1.size() == 0) {
/* 456 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 457 */         String str = getAttributeValue(entityItem2, "WITHDRAWDATE");
/* 458 */         if (str != null && !"".equals(str)) {
/* 459 */           if (!bool && bool2) {
/* 460 */             String str7 = getAttributeValue(entityItem1, "WITHDRAWDATE");
/* 461 */             bool4 = !str.equals(str7);
/* 462 */             if (!bool4) {
/* 463 */               bool3 = this.isTFGeoPromoted;
/*     */             }
/*     */           } 
/* 466 */           Date date = simpleDateFormat.parse(str);
/* 467 */           this.abr.addDebug("isTfwPromoted: " + bool3 + " isTfwChanged: " + bool4 + " salesOrgPlantsForRPQ is null " + ((list1 == null) ? 1 : 0));
/*     */           
/* 469 */           if ((!bool3 || bool4) && list1 != null) {
/* 470 */             LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(typeModelFeature);
/* 471 */             for (SalesOrgPlants salesOrgPlants : list1) {
/* 472 */               String str7 = salesOrgPlants.getSalesorg();
/* 473 */               LifecycleData lifecycleData = this.rdhRestProxy.r200(lifecycleDataGenerator.getMaterial(), lifecycleDataGenerator.getVarCond(), typeModelFeature
/* 474 */                   .getAnnDocNo(), "wdfm", str6, str7);
/* 475 */               this.abr.addDebug("Call r200 successfully for SalesOrg " + str7 + " " + lifecycleData);
/* 476 */               updateWDFMLifecyle(lifecycleData, lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), date, typeModelFeature
/* 477 */                   .getAnnDocNo(), str6, str7);
/* 478 */               this.abr.addDebug("updateWDFMLifecyle successfully for SalesOrg " + str7);
/*     */             } 
/*     */           } 
/*     */         } else {
/* 482 */           this.abr.addDebug("TMF WITHDRAWDATE is empty");
/*     */         } 
/*     */       } 
/* 485 */       this.abr.addDebug("End promote WDFM(Withdraw From Market) announcement for PRODSTRUCT");
/*     */       
/* 487 */       if (this.needUpdatParkingTable) {
/* 488 */         if (needReleaseParkingTable()) {
/* 489 */           this.rdhRestProxy.r144(str4, "R", str6);
/* 490 */           this.abr.addDebug("Call R144 update parking table successfully for TypeFeature");
/*     */         } else {
/* 492 */           this.rdhRestProxy.r144(str4, "H", str6);
/*     */         }
/*     */       
/*     */       }
/* 496 */     } catch (Exception exception) {
/* 497 */       if (exception.getMessage().contains("Max number of characteristics to assign to classication")) {
/* 498 */         if (isAlphaNumeric(typeFeature.getFeature())) {
/* 499 */           String str = getAttributeValue(entityItem3, "RANGENAME");
/* 500 */           if (str == null || "".equals(str)) {
/* 501 */             String str1 = "A000";
/* 502 */             setTextValue("RANGENAME", str1, entityItem3);
/*     */           } else {
/* 504 */             setTextValue("RANGENAME", getRangePlusOne(getAttributeValue(entityItem3, "RANGENAME")), entityItem3);
/*     */           } 
/* 506 */           setTextValue("FEATURECOUNT", "1", entityItem3);
/* 507 */           throw new RfcAbrException("RFC ABR failed, Please try again. " + exception);
/*     */         } 
/* 509 */         throw exception;
/*     */       } 
/*     */       
/* 512 */       throw exception;
/*     */     }
/*     */     finally {
/*     */       
/* 516 */       if (bool && entityItem2 != null) {
/* 517 */         setFlagValue("SYSFEEDRESEND", "No", entityItem2);
/*     */       }
/*     */       
/* 520 */       if (entityList1 != null) {
/* 521 */         entityList1.dereference();
/* 522 */         entityList1 = null;
/*     */       } 
/* 524 */       if (entityList3 != null) {
/* 525 */         entityList3.dereference();
/* 526 */         entityList3 = null;
/*     */       } 
/* 528 */       if (this.entityList != null) {
/* 529 */         this.entityList.dereference();
/* 530 */         this.entityList = null;
/*     */       } 
/* 532 */       if (entityList2 != null) {
/* 533 */         entityList2.dereference();
/* 534 */         entityList2 = null;
/*     */       } 
/* 536 */       this.abr.addDebug("End processThis()");
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getVeName() {
/* 541 */     return "RFCPRODSTRUCT";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void promoteTypeModelFeature(TypeFeature paramTypeFeature, TypeModelFeature paramTypeModelFeature, CHWAnnouncement paramCHWAnnouncement, CHWGeoAnn paramCHWGeoAnn, List<SalesOrgPlants> paramList, String paramString1, String paramString2) throws RfcAbrException, Exception {
/* 547 */     this.abr.addDebug("Start promote Type Feature");
/* 548 */     if (!this.isTFPromoted || this.isTFChanged || !this.isTFGeoPromoted || this.isTFGeoChanged) {
/* 549 */       if ("Q".equals(paramTypeFeature.getFeatureID())) {
/* 550 */         this.abr.addDebug("Start RPQ Feature");
/* 551 */         this.rdhRestProxy.r126(paramTypeFeature, paramCHWAnnouncement, paramString1);
/* 552 */         if (!this.isTFPromoted) {
/* 553 */           callr127WithAccessAuthority(paramTypeFeature, paramString2, paramCHWAnnouncement, paramString1);
/*     */         }
/*     */         
/* 556 */         this.rdhRestProxy.r152(paramTypeFeature, paramCHWAnnouncement, paramString1);
/* 557 */         this.abr.addDebug("End RPQ Feature");
/*     */       } else {
/* 559 */         this.abr.addDebug("Start not RPQ Feature");
/* 560 */         this.rdhRestProxy.r129(paramTypeFeature, paramCHWAnnouncement, paramString1);
/* 561 */         if (paramTypeFeature.ifAlphaNumeric()) {
/* 562 */           callr130WithAccessAuthority(paramTypeFeature.getType(), paramString2, paramCHWAnnouncement, paramString1);
/* 563 */           this.rdhRestProxy.r176(paramTypeFeature.getType(), paramString2, "NEW", paramCHWAnnouncement, paramString1);
/* 564 */           this.rdhRestProxy.r176(paramTypeFeature.getType(), paramString2, "UPG", paramCHWAnnouncement, paramString1);
/*     */         } 
/* 566 */         this.rdhRestProxy.r134(paramTypeFeature, paramCHWAnnouncement, paramString1);
/* 567 */         this.rdhRestProxy.r153(paramTypeFeature, paramCHWAnnouncement, paramString1);
/* 568 */         this.abr.addDebug("End not RPQ Feature");
/*     */       } 
/*     */       
/* 571 */       this.abr.addDebug("Start updateAnnLifecyle for Type Feature");
/* 572 */       for (SalesOrgPlants salesOrgPlants : paramList) {
/* 573 */         String str = salesOrgPlants.getSalesorg();
/* 574 */         LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(paramTypeFeature);
/* 575 */         updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), paramCHWGeoAnn.getAnnouncementDate(), paramCHWAnnouncement
/* 576 */             .getAnnDocNo(), paramCHWAnnouncement.getAnnouncementType(), paramString1, str);
/*     */       } 
/* 578 */       this.abr.addDebug("End updateAnnLifecyle for Type Feature");
/*     */     } 
/*     */     
/* 581 */     if (!"Q".equals(paramTypeFeature.getFeatureID()) && paramTypeFeature.getNetPriceMES()) {
/* 582 */       this.abr.addDebug("Start not RPQ Feature and NetPriceMES");
/* 583 */       if (paramTypeFeature.ifAlphaNumeric()) {
/* 584 */         callr131WithAccessAuthority(paramTypeFeature.getType(), paramString2, paramCHWAnnouncement, paramString1);
/* 585 */         this.rdhRestProxy.r177(paramTypeFeature.getType(), paramString2, paramCHWAnnouncement, paramString1);
/*     */       } 
/* 587 */       this.rdhRestProxy.r135(paramTypeFeature, paramCHWAnnouncement, paramString1);
/* 588 */       this.abr.addDebug("End not RPQ Feature and NetPriceMES");
/*     */     } 
/*     */     
/* 591 */     if ("Q".equals(paramTypeFeature.getFeatureID())) {
/* 592 */       this.abr.addDebug("Start RPQ Feature");
/* 593 */       this.rdhRestProxy.r138(paramTypeFeature, "NEW", paramCHWAnnouncement, paramString1);
/* 594 */       this.rdhRestProxy.r138(paramTypeFeature, "UPG", paramCHWAnnouncement, paramString1);
/* 595 */       boolean bool = this.rdhRestProxy.r204(paramTypeFeature.getType() + "MTC");
/* 596 */       if (bool) {
/* 597 */         this.rdhRestProxy.r138(paramTypeFeature, "MTC", paramCHWAnnouncement, paramString1);
/*     */       }
/* 599 */       this.rdhRestProxy.r128(paramTypeFeature, paramString2, paramCHWAnnouncement, paramString1);
/* 600 */       this.abr.addDebug("End RPQ Feature");
/*     */     } 
/* 602 */     this.abr.addDebug("End promote Type Feature");
/*     */ 
/*     */     
/* 605 */     if (!this.isTFPromoted || !this.isTFGeoPromoted || this.isTFGeoChanged) {
/* 606 */       this.abr.addDebug("Start updateAnnLifecyle for Type Model Feature");
/* 607 */       for (SalesOrgPlants salesOrgPlants : paramList) {
/* 608 */         String str = salesOrgPlants.getSalesorg();
/* 609 */         LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(paramTypeModelFeature);
/* 610 */         updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), paramCHWGeoAnn.getAnnouncementDate(), paramTypeModelFeature
/* 611 */             .getAnnDocNo(), paramCHWAnnouncement.getAnnouncementType(), paramString1, str);
/*     */       } 
/* 613 */       this.abr.addDebug("End updateAnnLifecyle for Type Model Feature");
/*     */     } 
/*     */     
/* 616 */     this.needUpdatParkingTable = true;
/* 617 */     this.abr.addDebug("set needUpdatParkingTable to " + this.needUpdatParkingTable);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTypeFeatureChanged(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws RfcAbrException {
/* 622 */     if (isDiff(paramEntityItem1, paramEntityItem2, tmfMarkChangedAttrs)) {
/* 623 */       return true;
/*     */     }
/* 625 */     boolean bool1 = getItemReturn(paramEntityItem1);
/* 626 */     boolean bool2 = getItemReturn(paramEntityItem2);
/* 627 */     if (bool1 != bool2) {
/* 628 */       this.abr.addDebug("isTypeFeatureChanged ItemReturn T1: " + bool1 + " T2: " + bool2);
/* 629 */       return true;
/*     */     } 
/* 631 */     boolean bool3 = getRemovalCharge(paramEntityItem1);
/* 632 */     boolean bool4 = getRemovalCharge(paramEntityItem2);
/* 633 */     if (bool3 != bool4) {
/* 634 */       this.abr.addDebug("isTypeFeatureChanged RemovalCharge T1: " + bool3 + " T2: " + bool4);
/* 635 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 639 */     EntityItem entityItem1 = null;
/* 640 */     EntityItem entityItem2 = null;
/* 641 */     List<EntityItem> list1 = getLinkedRelator(paramEntityItem1, "MODEL");
/* 642 */     if (list1.size() > 0) {
/* 643 */       entityItem1 = list1.get(0);
/*     */     } else {
/* 645 */       throw new RfcAbrException("isTypeFeatureChanged not found MODEL at T1 for " + paramEntityItem1.getKey());
/*     */     } 
/* 647 */     List<EntityItem> list2 = getLinkedRelator(paramEntityItem2, "MODEL");
/* 648 */     if (list2.size() > 0) {
/* 649 */       entityItem2 = list2.get(0);
/*     */     } else {
/* 651 */       throw new RfcAbrException("isTypeFeatureChanged not found MODEL at T2 for " + paramEntityItem1.getKey());
/*     */     } 
/* 653 */     boolean bool5 = isNetPriceMES(entityItem1);
/* 654 */     boolean bool6 = isNetPriceMES(entityItem2);
/* 655 */     this.abr.addDebug("isTypeFeatureChanged NetPriceMES T1: " + bool5 + " T2: " + bool6);
/* 656 */     if (bool5 != bool6) {
/* 657 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 661 */     EntityItem entityItem3 = null;
/* 662 */     EntityItem entityItem4 = null;
/* 663 */     List<EntityItem> list3 = getLinkedRelator(paramEntityItem1, "FEATURE");
/* 664 */     if (list3.size() > 0) {
/* 665 */       entityItem3 = list3.get(0);
/*     */     } else {
/* 667 */       throw new RfcAbrException("isTypeFeatureChanged not found FEATURE at T1 for " + paramEntityItem1.getKey());
/*     */     } 
/* 669 */     List<EntityItem> list4 = getLinkedRelator(paramEntityItem2, "FEATURE");
/* 670 */     if (list4.size() > 0) {
/* 671 */       entityItem4 = list4.get(0);
/*     */     } else {
/* 673 */       throw new RfcAbrException("isTypeFeatureChanged not found FEATURE at T2 for " + paramEntityItem1.getKey());
/*     */     } 
/* 675 */     if (isDiff(entityItem3, entityItem4, feaMarkChangedAttrs)) {
/* 676 */       return true;
/*     */     }
/* 678 */     boolean bool7 = isRPQ(entityItem3);
/* 679 */     boolean bool8 = isRPQ(entityItem4);
/* 680 */     if (bool7 != bool8) {
/* 681 */       this.abr.addDebug("isTypeFeatureChanged isRPQ T1: " + bool7 + " T2: " + bool8);
/* 682 */       return true;
/*     */     } 
/* 684 */     boolean bool9 = getNoChargePurchase(entityItem3);
/* 685 */     boolean bool10 = getNoChargePurchase(entityItem4);
/* 686 */     if (bool9 != bool10) {
/* 687 */       this.abr.addDebug("isTypeFeatureChanged NoChargePurchase T1: " + bool9 + " T2: " + bool10);
/* 688 */       return true;
/*     */     } 
/* 690 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isTypeFeatureGeoChanged(Vector paramVector, EntityItem paramEntityItem) throws RfcAbrException {
/* 694 */     EntityItem entityItem = getEntityItemAtT1(paramVector, paramEntityItem);
/* 695 */     if (entityItem != null) {
/* 696 */       String str1 = getAttributeValue(entityItem, "EFFECTIVEDATE");
/* 697 */       String str2 = getAttributeValue(paramEntityItem, "EFFECTIVEDATE");
/* 698 */       if (!str2.equals(str1)) {
/* 699 */         this.abr.addDebug("isTypeFeatureGeoChanged true T1 Date " + str1 + " T2 Date " + str2);
/* 700 */         return true;
/*     */       } 
/*     */     } else {
/* 703 */       this.abr.addDebug("isTypeFeatureGeoChanged true AVAIL is null at T1 for " + paramEntityItem.getKey());
/* 704 */       return true;
/*     */     } 
/* 706 */     this.abr.addDebug("isTypeFeatureGeoChanged false");
/* 707 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean isRPQ(EntityItem paramEntityItem) {
/* 711 */     return !PokUtils.contains(paramEntityItem, "FCTYPE", FCTYPE_SET);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected String calculateRange(EntityItem paramEntityItem1, EntityItem paramEntityItem2, TypeFeature paramTypeFeature) throws SQLException, MiddlewareException, RfcAbrException {
/* 731 */     String str1 = getAttributeValue(paramEntityItem1, "MACHTYPEATR");
/* 732 */     String str2 = getAttributeValue(paramEntityItem2, "TYPERANGEL").trim();
/* 733 */     if (str2 == null || "".equals(str2)) {
/* 734 */       str2 = getAttributeValue(paramEntityItem2, "TYPERANGE");
/*     */     }
/* 736 */     String str3 = "";
/* 737 */     if ("Q".equals(paramTypeFeature.getFeatureID())) {
/* 738 */       str3 = "RPQ";
/* 739 */       setTextValue("RPQRANGE", "Yes", paramEntityItem1);
/* 740 */       this.abr.addDebug("calculateRange RPQ range");
/*     */     }
/* 742 */     else if (isAlphaNumeric(paramTypeFeature.getFeature())) {
/* 743 */       String str = getAttributeValue(paramEntityItem1, "RANGENAME");
/* 744 */       this.abr.addDebug("calculateRange AlphaNumeric rangeName " + str);
/* 745 */       if (str == null || "".equals(str)) {
/* 746 */         str3 = "A000";
/* 747 */         setTextValue("RANGENAME", str3, paramEntityItem1);
/* 748 */         setTextValue("FEATURECOUNT", "1", paramEntityItem1);
/* 749 */         setLongTextValue("TYPERANGEL", generateTypeRange(str1, str3, str2), paramEntityItem2);
/*     */       } else {
/* 751 */         str3 = str;
/* 752 */         String str4 = getAttributeValue(paramEntityItem1, "FEATURECOUNT");
/* 753 */         int i = 0;
/*     */         try {
/* 755 */           i = Integer.valueOf(str4).intValue();
/* 756 */         } catch (NumberFormatException numberFormatException) {
/* 757 */           this.abr.addDebug("calculateRange feaCount is not a number " + str4);
/*     */         } 
/* 759 */         this.abr.addDebug("calculateRange feaCount " + str4);
/* 760 */         if (!isTypeRangeExistTheType(str1, str3, str2)) {
/* 761 */           if (i < 999) {
/* 762 */             setTextValue("FEATURECOUNT", String.valueOf(++i), paramEntityItem1);
/* 763 */             setLongTextValue("TYPERANGEL", generateTypeRange(str1, str3, str2), paramEntityItem2);
/*     */           } else {
/* 765 */             str3 = getRangePlusOne(str);
/* 766 */             setTextValue("RANGENAME", str3, paramEntityItem1);
/* 767 */             setTextValue("FEATURECOUNT", "1", paramEntityItem1);
/* 768 */             setLongTextValue("TYPERANGEL", generateTypeRange(str1, str3, str2), paramEntityItem2);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } else {
/* 773 */       str3 = paramTypeFeature.getFeature().substring(0, 1) + "000";
/*     */     } 
/*     */     
/* 776 */     return str3;
/*     */   }
/*     */   
/*     */   protected String getRangePlusOne(String paramString) throws RfcAbrException {
/* 780 */     if (paramString.length() == 4) {
/* 781 */       String str1 = paramString.substring(0, 1);
/* 782 */       String str2 = paramString.substring(1, 4);
/* 783 */       int i = 0;
/*     */       try {
/* 785 */         i = Integer.valueOf(str2).intValue() + 1;
/* 786 */         this.abr.addDebug("getRangePlusOne rangeNum " + str2 + " + 1 = " + i);
/* 787 */       } catch (NumberFormatException numberFormatException) {
/* 788 */         this.abr.addDebug("getRangePlusOne last 3 letters are not all number. rangeNum " + str2);
/*     */       } 
/* 790 */       String str3 = String.valueOf(i);
/* 791 */       int j = 3 - str3.length();
/* 792 */       for (byte b = 0; b < j; b++) {
/* 793 */         str3 = "0" + str3;
/*     */       }
/* 795 */       str3 = str1 + str3;
/* 796 */       this.abr.addDebug("getRangePlusOne newRange " + str3);
/* 797 */       return str3;
/*     */     } 
/* 799 */     throw new RfcAbrException("getRangePlusOne lenght is not 4 of rangeName " + paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAlphaNumeric(String paramString) {
/* 804 */     int i = paramString.length();
/* 805 */     for (byte b = 0; b < i; b++) {
/* 806 */       if (Character.isLetter(paramString.charAt(b))) {
/* 807 */         return true;
/*     */       }
/*     */     } 
/* 810 */     return false;
/*     */   }
/*     */   
/*     */   protected String generateTypeRange(String paramString1, String paramString2, String paramString3) {
/* 814 */     this.abr.addDebug("generateTypeRange type " + paramString1 + " range " + paramString2 + " oldTypeRanges" + paramString3);
/* 815 */     if (isTypeRangeExistTheType(paramString1, paramString2, paramString3)) {
/* 816 */       return paramString3;
/*     */     }
/* 818 */     if ("".equals(paramString3)) {
/* 819 */       return paramString1 + "-" + paramString2;
/*     */     }
/* 821 */     return paramString3 + "|" + paramString1 + "-" + paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isTypeRangeExistTheType(String paramString1, String paramString2, String paramString3) {
/* 827 */     String[] arrayOfString = paramString3.split("\\|");
/* 828 */     for (String str : arrayOfString) {
/* 829 */       this.abr.addDebug("isTypeRangeExistTheType typeRange " + str + " type " + paramString1);
/* 830 */       String[] arrayOfString1 = str.split("-");
/* 831 */       if (arrayOfString1.length == 2 && 
/* 832 */         arrayOfString1[0].equals(paramString1) && arrayOfString1[1].equals(paramString2)) {
/* 833 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 837 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean getNoChargePurchase(EntityItem paramEntityItem) throws RfcAbrException {
/* 841 */     boolean bool = false;
/* 842 */     String str1 = getAttributeValue(paramEntityItem, "PRICEDFEATURE");
/* 843 */     String str2 = getAttributeValue(paramEntityItem, "ZEROPRICE");
/* 844 */     if ("No".equals(str1)) {
/* 845 */       bool = true;
/* 846 */     } else if ("No".equals(str2)) {
/* 847 */       bool = false;
/*     */     } 
/* 849 */     this.abr.addDebug("getNoChargePurchase() PRICEDFEATURE " + str1 + " ZEROPRICE " + str2 + " noChargePurchase " + bool);
/*     */     
/* 851 */     return bool;
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
/*     */   protected List<String> getCountryListForRPQ(Vector<EntityItem> paramVector, EntityItem paramEntityItem) throws RfcAbrException {
/* 864 */     List<String> list = getEntitiyAttributeValues(paramEntityItem, "COUNTRYLIST", "MF");
/* 865 */     this.abr.addDebug("getCountryListForRPQ " + paramEntityItem.getKey() + " COUNTRYLIST size " + list.size());
/* 866 */     ArrayList<String> arrayList = new ArrayList();
/* 867 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 868 */       EntityItem entityItem = paramVector.get(b);
/* 869 */       List<String> list1 = getEntitiyAttributeValues(entityItem, "COUNTRYLIST", "MF");
/* 870 */       this.abr.addDebug("getCountryListForRPQ " + entityItem
/* 871 */           .getKey() + " COUNTRYLIST size " + list1.size());
/* 872 */       arrayList.addAll(list1);
/*     */     } 
/* 874 */     list.retainAll(arrayList);
/* 875 */     this.abr.addDebug("getCountryListForRPQ applicable COUNTRYLIST size " + list.size() + " values " + list);
/*     */     
/* 877 */     return list;
/*     */   }
/*     */   
/*     */   protected boolean isApprovalRPQ(EntityItem paramEntityItem) throws RfcAbrException {
/* 881 */     String str = getAttributeFlagValue(paramEntityItem, "FCTYPE");
/* 882 */     this.abr.addDebug("isApprovalRPQ fcType" + str);
/* 883 */     if ("120".equals(str)) {
/* 884 */       return true;
/*     */     }
/* 886 */     return false;
/*     */   }
/*     */   
/*     */   private boolean getItemReturn(EntityItem paramEntityItem) throws RfcAbrException {
/* 890 */     boolean bool = false;
/* 891 */     String str = getAttributeFlagValue(paramEntityItem, "RETURNEDPARTS");
/* 892 */     if ("5100".equals(str)) {
/* 893 */       bool = true;
/* 894 */     } else if ("5101".equals(str)) {
/* 895 */       bool = false;
/*     */     } 
/* 897 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean getRemovalCharge(EntityItem paramEntityItem) throws RfcAbrException {
/* 901 */     boolean bool = false;
/* 902 */     String str = getAttributeFlagValue(paramEntityItem, "REMOVEPRICE");
/* 903 */     if ("5082".equals(str)) {
/* 904 */       bool = true;
/* 905 */     } else if ("5083".equals(str)) {
/* 906 */       bool = false;
/*     */     } 
/* 908 */     return bool;
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
/*     */   private String getTMFAnnDate(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, EntityItem paramEntityItem4) throws RfcAbrException {
/* 923 */     String str = null;
/* 924 */     if (paramEntityItem4 != null) {
/* 925 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem4, "AVAILANNA", "ANNOUNCEMENT");
/* 926 */       if (vector != null && vector.size() > 0) {
/* 927 */         EntityItem entityItem = vector.get(0);
/* 928 */         str = getAttributeValue(entityItem, "ANNDATE");
/*     */       } 
/*     */     } 
/* 931 */     if (str == null || "".equals(str)) {
/* 932 */       str = getAttributeValue(paramEntityItem1, "ANNDATE");
/*     */     }
/* 934 */     if (str == null || "".equals(str)) {
/* 935 */       String str1 = getAttributeValue(paramEntityItem2, "ANNDATE");
/* 936 */       String str2 = getAttributeValue(paramEntityItem3, "FIRSTANNDATE");
/* 937 */       if (str1.compareTo(str2) > 0) {
/* 938 */         str = str1;
/*     */       } else {
/* 940 */         str = str2;
/*     */       } 
/*     */     } 
/* 943 */     if (str == null || "".equals(str)) {
/* 944 */       throw new RfcAbrException("ANNDATE is null, it will not promote this PRODSTRUCT");
/*     */     }
/* 946 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RFCPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */