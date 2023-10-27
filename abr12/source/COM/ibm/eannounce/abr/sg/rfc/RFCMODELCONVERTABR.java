/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
/*     */ import com.ibm.pprds.epimshw.HWPIMSNotFoundInMastException;
/*     */ import com.ibm.pprds.epimshw.util.ConfigManager;
/*     */ import com.ibm.rdh.chw.entity.CHWAnnouncement;
/*     */ import com.ibm.rdh.chw.entity.CHWGeoAnn;
/*     */ import com.ibm.rdh.chw.entity.DepData;
/*     */ import com.ibm.rdh.chw.entity.LifecycleData;
/*     */ import com.ibm.rdh.chw.entity.RevProfile;
/*     */ import com.ibm.rdh.chw.entity.TypeModel;
/*     */ import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.FileLock;
/*     */ import java.nio.channels.OverlappingFileLockException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class RFCMODELCONVERTABR
/*     */   extends RfcAbrAdapter
/*     */ {
/*     */   public static final String MTCFROMTYPE = "MTCFROMTYPE";
/*     */   public static final String MTCTOTYPE = "MTCTOTYPE";
/*     */   public static final String FROMTYPE = "FROMTYPE";
/*     */   public static final String TOTYPE = "TOTYPE";
/*     */   private boolean isRPQFromType = false;
/*     */   private boolean isRPQToType = false;
/*     */   
/*     */   public RFCMODELCONVERTABR(RFCABRSTATUS paramRFCABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
/* 135 */     super(paramRFCABRSTATUS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processThis() throws Exception {
/* 141 */     this.abr.addDebug("Start processThis()");
/* 142 */     EntityList entityList = null;
/* 143 */     EntityItem entityItem = null;
/* 144 */     boolean bool = false;
/*     */ 
/*     */     
/*     */     try {
/* 148 */       entityItem = getRooEntityItem();
/*     */       
/* 150 */       if (!"0020".equals(getAttributeFlagValue(entityItem, "STATUS"))) {
/* 151 */         throw new RfcAbrException("The status is not final, it will not promote this MODELCONVERT");
/*     */       }
/*     */       
/* 154 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "MODELCONVERTAVAIL", "AVAIL");
/* 155 */       Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 156 */       Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "149");
/* 157 */       this.abr.addDebug("MODELCONVERTAVAIL all availVct: " + vector1.size() + " plannedavail: " + vector2.size() + " LastOrder avail " + vector3
/* 158 */           .size());
/* 159 */       if (vector2.size() == 0) {
/* 160 */         throw new RfcAbrException("There is no plan avail for this MODELCONVERT, it will not promote this MODELCONVERT");
/*     */       }
/*     */       
/* 163 */       String str1 = getAttributeValue(entityItem, "FROMMACHTYPE");
/* 164 */       String str2 = getAttributeValue(entityItem, "FROMMODEL");
/* 165 */       String str3 = getAttributeValue(entityItem, "TOMACHTYPE");
/* 166 */       String str4 = getAttributeValue(entityItem, "TOMODEL");
/* 167 */       String str5 = str1 + "-" + str2 + "-" + str3 + "-" + str4;
/*     */ 
/*     */       
/* 170 */       EntityItem entityItem1 = getModelByMachTypeAndModel(str1, str2);
/* 171 */       entityItem1 = getModelByVe2(entityItem1.getEntityType(), entityItem1.getEntityID());
/* 172 */       EntityItem entityItem2 = getModelByMachTypeAndModel(str3, str4);
/* 173 */       entityItem2 = getModelByVe2(entityItem2.getEntityType(), entityItem2.getEntityID());
/*     */       
/* 175 */       Vector vector4 = PokUtils.getAllLinkedEntities(entityItem1, "MODELMACHINETYPEA", "MACHTYPE");
/* 176 */       if (vector4.size() == 0) {
/* 177 */         throw new RfcAbrException("There is no MACHTYPE entity for FROMMACHTYPE " + str1);
/*     */       }
/* 179 */       Vector vector5 = PokUtils.getAllLinkedEntities(entityItem2, "MODELMACHINETYPEA", "MACHTYPE");
/* 180 */       if (vector5.size() == 0) {
/* 181 */         throw new RfcAbrException("There is no MACHTYPE entity for TOMACHTYPE " + str3);
/*     */       }
/* 183 */       if (!isMachTypePromoted(vector5)) {
/* 184 */         throw new RfcAbrException("The MACHTYPE was not promoted, please promote the MACHTYPE first. type " + str3);
/*     */       }
/* 186 */       if (!isMachTypePromoted(vector4)) {
/* 187 */         throw new RfcAbrException("The MACHTYPE was not promoted, please promote the MACHTYPE first. type " + str1);
/*     */       }
/* 189 */       if (isRPQType(vector4)) {
/* 190 */         this.isRPQFromType = true;
/*     */       }
/* 192 */       if (isRPQType(vector5)) {
/* 193 */         this.isRPQToType = true;
/*     */       }
/* 195 */       this.abr.addDebug("isRPQFromType " + this.isRPQFromType + " isRPQToType " + this.isRPQToType);
/*     */ 
/*     */ 
/*     */       
/* 199 */       TypeModelUPGGeo typeModelUPGGeo = new TypeModelUPGGeo();
/* 200 */       typeModelUPGGeo.setFromType(str1);
/* 201 */       typeModelUPGGeo.setFromModel(str2);
/* 202 */       typeModelUPGGeo.setType(str3);
/* 203 */       typeModelUPGGeo.setModel(str4);
/*     */       
/* 205 */       String str6 = "C";
/* 206 */       this.abr.addDebug("PimsIdentity:" + str6);
/*     */       
/* 208 */       String str7 = "ZIP";
/* 209 */       this.abr.addDebug("Flfilcd: " + str7);
/*     */       
/* 211 */       boolean bool1 = false;
/* 212 */       boolean bool2 = false;
/* 213 */       boolean bool3 = false;
/*     */       
/* 215 */       EntityItem entityItem3 = null;
/* 216 */       String str8 = null;
/*     */ 
/*     */       
/* 219 */       bool = "Yes".equals(getAttributeFlagValue(entityItem, "SYSFEEDRESEND"));
/* 220 */       this.abr.addDebug("Resend full: " + bool);
/*     */       
/* 222 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = getAttributeHistory(entityItem, "RFCABRSTATUS");
/* 223 */       boolean bool4 = existBefore(attributeChangeHistoryGroup, "0030");
/* 224 */       this.abr.addDebug("Exist passed RFCABRSTATUS before: " + bool4);
/* 225 */       if (!bool && bool4) {
/* 226 */         str8 = getLatestValFromForAttributeValue(attributeChangeHistoryGroup, "0030");
/* 227 */         if (str8 == null) {
/* 228 */           this.abr.addDebug("t1DTS is null");
/*     */         } else {
/* 230 */           String str = this.abr.getCurrentTime();
/* 231 */           Profile profile = this.abr.getProfile().getNewInstance(this.abr.getDatabase());
/* 232 */           profile.setValOnEffOn(str8, str8);
/* 233 */           profile.setEndOfDay(str);
/* 234 */           profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 235 */           profile.setLoginTime(str);
/* 236 */           this.abr.addDebug("Get t1 entity list for t1DTS:" + str8 + " t2DTS:" + str);
/* 237 */           entityList = getEntityList(profile);
/*     */ 
/*     */           
/* 240 */           entityItem3 = entityList.getParentEntityGroup().getEntityItem(0);
/*     */         } 
/*     */       } 
/*     */       byte b1;
/* 244 */       for (b1 = 0; b1 < vector2.size(); b1++) {
/* 245 */         EntityItem entityItem4 = vector2.get(b1);
/* 246 */         this.abr.addDebug("Promote MODELCONVERT for " + entityItem4.getKey());
/*     */ 
/*     */         
/* 249 */         Vector vector = PokUtils.getAllLinkedEntities(entityItem4, "AVAILGAA", "GENERALAREA");
/* 250 */         List<SalesOrgPlants> list = getAllSalesOrgPlant(vector);
/* 251 */         Set<String> set = getAllPlant(list);
/*     */         
/* 253 */         if (!bool && bool4 && 
/* 254 */           entityList != null && entityItem3 != null) {
/* 255 */           Vector vector8 = PokUtils.getAllLinkedEntities(entityItem3, "MODELCONVERTAVAIL", "AVAIL");
/* 256 */           Vector vector9 = PokUtils.getEntitiesWithMatchedAttr(vector8, "AVAILTYPE", "146");
/*     */           
/* 258 */           bool2 = isTypeModelUpgrateChanged(vector9, entityItem4);
/* 259 */           List<String> list1 = getNewCountries(vector9, entityItem4);
/* 260 */           bool1 = (list1.size() == 0) ? true : false;
/* 261 */           if (!bool2 && 
/* 262 */             !bool1) {
/* 263 */             list = getAllSalesOrgPlantByCountryList(list, list1);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 268 */         this.abr.addDebug("isTMUpgPromoted: " + bool1 + " isTMUpgChanged: " + bool2);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 273 */         CHWAnnouncement cHWAnnouncement = new CHWAnnouncement();
/* 274 */         cHWAnnouncement.setAnnDocNo(str5);
/* 275 */         cHWAnnouncement.setSegmentAcronym(getSegmentAcronymForAnn(entityItem));
/*     */         
/* 277 */         CHWGeoAnn cHWGeoAnn = new CHWGeoAnn();
/* 278 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*     */         
/* 280 */         Vector<EntityItem> vector6 = PokUtils.getAllLinkedEntities(entityItem4, "AVAILANNA", "ANNOUNCEMENT");
/* 281 */         if (vector6 != null && vector6.size() > 0) {
/* 282 */           EntityItem entityItem5 = vector6.get(0);
/* 283 */           cHWAnnouncement.setAnnouncementType(getAttributeValue(entityItem5, "ANNTYPE"));
/* 284 */           cHWGeoAnn.setAnnouncementDate(simpleDateFormat.parse(getAttributeValue(entityItem5, "ANNDATE")));
/*     */         } else {
/* 286 */           cHWAnnouncement.setAnnouncementType("New");
/* 287 */           cHWGeoAnn.setAnnouncementDate(simpleDateFormat.parse(getAttributeValue(entityItem, "ANNDATE")));
/*     */         } 
/* 289 */         this.abr.addDebug("CHWAnnouncement: " + cHWAnnouncement.toString());
/* 290 */         this.abr.addDebug("CHWAnnouncementGEO: " + cHWGeoAnn.toString());
/*     */         
/* 292 */         if (!bool1 && typeModelUPGGeo.getType().equals(typeModelUPGGeo.getFromType())) {
/* 293 */           this.abr.addDebug("----------------------- Start Type Model Upgrate not promoted with same type -----------------------");
/* 294 */           String str = typeModelUPGGeo.getType();
/* 295 */           this.rdhRestProxy.r107(str, cHWAnnouncement, str6);
/* 296 */           this.abr.addDebug("Call R107 successfully");
/* 297 */           this.rdhRestProxy.r109(str, cHWAnnouncement, str6);
/* 298 */           this.abr.addDebug("Call R109 successfully");
/*     */           
/* 300 */           this.rdhRestProxy.r125(str, cHWAnnouncement, "UPG", str6);
/* 301 */           this.abr.addDebug("Call R125 successfully for UPG");
/* 302 */           this.rdhRestProxy.r111(str, cHWAnnouncement, str6);
/* 303 */           this.abr.addDebug("Call R111 successfully");
/*     */           
/* 305 */           boolean bool6 = this.rdhRestProxy.r204(typeModelUPGGeo.getType() + "MTC");
/* 306 */           this.abr.addDebug("Call R204 successfully typeMTCExists: " + bool6);
/* 307 */           if (bool6) {
/* 308 */             boolean bool7 = this.rdhRestProxy.r214(typeModelUPGGeo.getType());
/* 309 */             this.abr.addDebug("Call R214 successfully typeMCclassExists: " + bool7);
/* 310 */             if (bool7) {
/* 311 */               this.rdhRestProxy.r125(typeModelUPGGeo.getType(), cHWAnnouncement, "MTC", str6);
/* 312 */               this.abr.addDebug("Call R125 successfully for MTC");
/*     */             } 
/*     */           } 
/* 315 */           this.rdhRestProxy.r151(str, cHWAnnouncement, str6);
/* 316 */           this.abr.addDebug("Call R151 successfully");
/* 317 */           LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(typeModelUPGGeo);
/* 318 */           for (SalesOrgPlants salesOrgPlants : list) {
/* 319 */             String str9 = salesOrgPlants.getSalesorg();
/* 320 */             updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), cHWGeoAnn.getAnnouncementDate(), cHWAnnouncement.getAnnDocNo(), cHWAnnouncement.getAnnouncementType(), str6, str9);
/* 321 */             this.abr.addDebug("updateAnnLifecyle successfully for SalesOrg " + str9);
/*     */           } 
/* 323 */           this.abr.addDebug("----------------------- End Type Model Upgrate not promoted with same type -----------------------");
/* 324 */         } else if (bool2 && typeModelUPGGeo.getType().equals(typeModelUPGGeo.getFromType())) {
/* 325 */           this.abr.addDebug("----------------------- Start Type Model Upgrate already promoted and changed with same type -----------------------");
/* 326 */           LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(typeModelUPGGeo);
/* 327 */           for (SalesOrgPlants salesOrgPlants : list) {
/* 328 */             String str = salesOrgPlants.getSalesorg();
/* 329 */             updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), cHWGeoAnn.getAnnouncementDate(), cHWAnnouncement.getAnnDocNo(), cHWAnnouncement.getAnnouncementType(), str6, str);
/* 330 */             this.abr.addDebug("updateAnnLifecyle successfully for SalesOrg " + str);
/*     */           } 
/* 332 */           this.abr.addDebug("----------------------- End Type Model Upgrate already promoted and changed with same type -----------------------");
/*     */         } 
/* 334 */         Vector<TypeModelUPGGeo> vector7 = new Vector();
/* 335 */         vector7.add(typeModelUPGGeo);
/* 336 */         this.rdhRestProxy.r124(vector7, cHWAnnouncement, str6);
/* 337 */         this.abr.addDebug("Call R124 successfully");
/*     */         
/* 339 */         if (!typeModelUPGGeo.getType().equals(typeModelUPGGeo.getFromType())) {
/* 340 */           this.abr.addDebug("----------------------- Start Type Model Upgrate for different type -----------------------");
/*     */           
/* 342 */           if (!bool1) {
/* 343 */             this.abr.addDebug("----------------------- Start Type Model Upgrate not promoted for different type -----------------------");
/* 344 */             String str = ConfigManager.getConfigManager().getString("epimshw.extraPlant", true);
/*     */             
/* 346 */             this.abr.addDebug("Start createMTC for type " + typeModelUPGGeo.getType());
/* 347 */             createMTCForFromToType(typeModelUPGGeo.getType(), typeModelUPGGeo
/* 348 */                 .getModel(), entityItem2, "MTCTOTYPE", list, cHWAnnouncement, cHWGeoAnn, typeModelUPGGeo, str, str6, set, str7, vector);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 353 */             this.abr.addDebug("Start createMTC for type " + typeModelUPGGeo.getFromType());
/* 354 */             createMTCForFromToType(typeModelUPGGeo.getFromType(), typeModelUPGGeo
/* 355 */                 .getFromModel(), entityItem1, "MTCFROMTYPE", list, cHWAnnouncement, cHWGeoAnn, typeModelUPGGeo, str, str6, set, str7, vector);
/*     */ 
/*     */ 
/*     */             
/* 359 */             LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(typeModelUPGGeo);
/* 360 */             for (SalesOrgPlants salesOrgPlants : list) {
/* 361 */               String str9 = salesOrgPlants.getSalesorg();
/* 362 */               updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), cHWGeoAnn.getAnnouncementDate(), cHWAnnouncement.getAnnDocNo(), cHWAnnouncement.getAnnouncementType(), str6, str9);
/* 363 */               this.abr.addDebug("updateAnnLifecyle successfully for SalesOrg " + str9);
/*     */             } 
/*     */             
/* 366 */             this.rdhRestProxy.r163(typeModelUPGGeo.getType(), typeModelUPGGeo, cHWAnnouncement, str6);
/* 367 */             this.abr.addDebug("Call R163 successfully for type " + typeModelUPGGeo.getType());
/* 368 */             this.rdhRestProxy.r163(typeModelUPGGeo.getFromType(), typeModelUPGGeo, cHWAnnouncement, str6);
/* 369 */             this.abr.addDebug("Call R163 successfully for type " + typeModelUPGGeo.getFromType());
/*     */ 
/*     */             
/* 372 */             updateFromToRevenueProfileBom(entityItem1, "FROMTYPE", typeModelUPGGeo, cHWAnnouncement, set, str6, (Vector)null);
/*     */             
/* 374 */             updateFromToRevenueProfileBom(entityItem2, "TOTYPE", typeModelUPGGeo, cHWAnnouncement, set, str6, (Vector)null);
/*     */             
/* 376 */             this.abr.addDebug("----------------------- End Type Model Upgrate not promoted for different type -----------------------");
/*     */           } else {
/* 378 */             if (bool2) {
/* 379 */               this.abr.addDebug("----------------------- Start Type Model Upgrate changed for different type -----------------------");
/* 380 */               LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(typeModelUPGGeo);
/* 381 */               for (SalesOrgPlants salesOrgPlants : list) {
/* 382 */                 String str = salesOrgPlants.getSalesorg();
/* 383 */                 updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), cHWGeoAnn.getAnnouncementDate(), cHWAnnouncement.getAnnDocNo(), cHWAnnouncement.getAnnouncementType(), str6, str);
/* 384 */                 this.abr.addDebug("updateAnnLifecyle successfully for isTMUpgChanged " + bool2 + " SalesOrg " + str);
/*     */               } 
/* 386 */               this.abr.addDebug("----------------------- End Type Model Upgrate changed for different type -----------------------");
/*     */             } 
/*     */ 
/*     */             
/* 390 */             if (!bool && bool4 && str8 != null) {
/* 391 */               String str = this.abr.getCurrentTime();
/* 392 */               EntityItem entityItem5 = getModelByVe2(entityItem1.getEntityType(), entityItem1.getEntityID(), str8, str);
/* 393 */               if (isRevProfOrAuoMtrlChanged(entityItem5, entityItem1)) {
/* 394 */                 Vector vector8 = new Vector();
/* 395 */                 if (entityItem5 != null) {
/* 396 */                   vector8 = getDeletedAuoMaterials(getRevProfile(entityItem5).getAuoMaterials(), getRevProfile(entityItem1).getAuoMaterials());
/* 397 */                   this.abr.addDebug("Delete AUO Materials of from model size:" + vector8.size());
/*     */                 } 
/*     */                 
/* 400 */                 updateFromToRevenueProfileBom(entityItem1, "FROMTYPE", typeModelUPGGeo, cHWAnnouncement, set, str6, vector8);
/*     */               } 
/* 402 */               EntityItem entityItem6 = getModelByVe2(entityItem2.getEntityType(), entityItem2.getEntityID(), str8, str);
/* 403 */               if (isRevProfOrAuoMtrlChanged(entityItem6, entityItem2)) {
/* 404 */                 Vector vector8 = new Vector();
/* 405 */                 if (entityItem6 != null) {
/* 406 */                   vector8 = getDeletedAuoMaterials(getRevProfile(entityItem6).getAuoMaterials(), getRevProfile(entityItem2).getAuoMaterials());
/* 407 */                   this.abr.addDebug("Delete AUO Materials of to MODEL size:" + vector8.size());
/*     */                 } 
/*     */                 
/* 410 */                 updateFromToRevenueProfileBom(entityItem2, "TOTYPE", typeModelUPGGeo, cHWAnnouncement, set, str6, vector8);
/*     */               } 
/*     */             } else {
/* 413 */               this.abr.addDebug("WARN: RevProf and AuoMtrl change isPassedAbrExist " + bool4 + " t1DTS " + str8);
/*     */             } 
/*     */           } 
/*     */           
/* 417 */           createSalesBOMforTypeMTC(typeModelUPGGeo.getFromType(), typeModelUPGGeo.getFromModel(), cHWAnnouncement, str6);
/* 418 */           createSalesBOMforTypeMTC(typeModelUPGGeo.getType(), typeModelUPGGeo.getModel(), cHWAnnouncement, str6);
/*     */           
/* 420 */           this.abr.addDebug("----------------------- End Type Model Upgrate for different type -----------------------");
/*     */         } 
/*     */         
/* 423 */         if (!bool1 || bool2) {
/* 424 */           bool3 = true;
/* 425 */           this.abr.addDebug("Set needUpdateParkingTable to " + bool3);
/*     */         } 
/*     */       } 
/*     */       
/* 429 */       this.abr.addDebug("----------------------- Start Promote WDFM Announcement for MODELCONVERT Withdraw From Market -----------------------");
/* 430 */       b1 = 0;
/* 431 */       boolean bool5 = false;
/*     */       
/* 433 */       TypeModel typeModel = getTypeModelForCreateMTC(entityItem2);
/* 434 */       for (byte b2 = 0; b2 < vector3.size(); b2++) {
/*     */         
/* 436 */         EntityItem entityItem4 = vector3.get(b2);
/*     */         
/* 438 */         this.abr.addDebug("Promote WDFM Announcement for " + entityItem4.getKey());
/*     */         
/* 440 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 441 */         Date date = simpleDateFormat.parse(getAttributeValue(entityItem4, "EFFECTIVEDATE"));
/*     */         
/* 443 */         Vector vector = PokUtils.getAllLinkedEntities(entityItem4, "AVAILGAA", "GENERALAREA");
/* 444 */         List<SalesOrgPlants> list = getAllSalesOrgPlant(vector);
/*     */         
/* 446 */         if (!bool && bool4 && 
/* 447 */           entityList != null && entityItem3 != null) {
/* 448 */           Vector vector7 = PokUtils.getAllLinkedEntities(entityItem3, "MODELCONVERTAVAIL", "AVAIL");
/* 449 */           Vector vector8 = PokUtils.getEntitiesWithMatchedAttr(vector7, "AVAILTYPE", "149");
/*     */           
/* 451 */           bool5 = isTypeModelUpgrateChanged(vector8, entityItem4);
/* 452 */           if (!bool5) {
/* 453 */             List<String> list1 = getNewCountries(vector8, entityItem4);
/* 454 */             if (list1.size() > 0) {
/* 455 */               b1 = 0;
/* 456 */               list = getAllSalesOrgPlantByCountryList(list, list1);
/*     */             } else {
/* 458 */               bool1 = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 463 */         this.abr.addDebug("isTmuwPromoted: " + b1 + " isTmuwChanged: " + bool5);
/*     */         
/* 465 */         CHWAnnouncement cHWAnnouncement = new CHWAnnouncement();
/* 466 */         cHWAnnouncement.setAnnDocNo(str5);
/* 467 */         cHWAnnouncement.setSegmentAcronym(getSegmentAcronymForAnn(entityItem));
/*     */         
/* 469 */         CHWGeoAnn cHWGeoAnn = new CHWGeoAnn();
/*     */         
/* 471 */         Vector<EntityItem> vector6 = PokUtils.getAllLinkedEntities(entityItem4, "AVAILANNA", "ANNOUNCEMENT");
/* 472 */         if (vector6 != null && vector6.size() > 0) {
/* 473 */           EntityItem entityItem5 = vector6.get(0);
/* 474 */           cHWAnnouncement.setAnnouncementType(getAttributeValue(entityItem5, "ANNTYPE"));
/* 475 */           cHWGeoAnn.setAnnouncementDate(simpleDateFormat.parse(getAttributeValue(entityItem5, "ANNDATE")));
/*     */         } else {
/* 477 */           cHWAnnouncement.setAnnouncementType("New");
/* 478 */           cHWGeoAnn.setAnnouncementDate(simpleDateFormat.parse(getAttributeValue(entityItem, "ANNDATE")));
/*     */         } 
/* 480 */         this.abr.addDebug("CHWAnnouncement: " + cHWAnnouncement.toString());
/* 481 */         this.abr.addDebug("CHWAnnouncementGEO: " + cHWGeoAnn.toString());
/*     */ 
/*     */         
/* 484 */         if (b1 == 0 || bool5) {
/* 485 */           LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(typeModelUPGGeo);
/* 486 */           for (SalesOrgPlants salesOrgPlants : list) {
/* 487 */             String str = salesOrgPlants.getSalesorg();
/* 488 */             LifecycleData lifecycleData = this.rdhRestProxy.r200(lifecycleDataGenerator.getMaterial(), lifecycleDataGenerator.getVarCond(), str5, "wdfm", str6, str);
/* 489 */             this.abr.addDebug("Call r200 successfully for SalesOrg " + str + " " + lifecycleData);
/* 490 */             updateWDFMLifecyle(lifecycleData, lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), date, str5, str6, str);
/* 491 */             this.abr.addDebug("updateWDFMLifecyle successfully for SalesOrg " + str);
/*     */ 
/*     */             
/* 494 */             Vector<String> vector7 = salesOrgPlants.getPlants();
/* 495 */             Vector vector8 = getTaxListBySalesOrgPlants(salesOrgPlants);
/* 496 */             for (String str9 : vector7) {
/* 497 */               if (str9.equals("1999")) {
/* 498 */                 this.abr.addDebug("skip plant " + str9);
/*     */                 continue;
/*     */               } 
/* 501 */               this.rdhRestProxy.r102(cHWAnnouncement, typeModel, str9, "MTC", typeModelUPGGeo, "MTCTOTYPE", str6, str7, str, vector8, cHWGeoAnn, date);
/* 502 */               this.abr.addDebug("Call R102 MTC successfully for plant " + str9);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 507 */       this.abr.addDebug("----------------------- End Promote WDFM Announcement for MODELCONVERT Withdraw From Market -----------------------");
/*     */       
/* 509 */       if (needReleaseParkingTable()) {
/* 510 */         this.rdhRestProxy.r144(str5, "R", str6);
/* 511 */         this.abr.addDebug("Call R144 update parking table successfully");
/*     */       } else {
/* 513 */         this.rdhRestProxy.r144(str5, "H", str6);
/*     */       } 
/*     */     } finally {
/*     */       
/* 517 */       if (bool && entityItem != null) {
/* 518 */         setFlagValue("SYSFEEDRESEND", "No", entityItem);
/*     */       }
/*     */       
/* 521 */       if (entityList != null) {
/* 522 */         entityList.dereference();
/* 523 */         entityList = null;
/*     */       } 
/* 525 */       if (this.entityList != null) {
/* 526 */         this.entityList.dereference();
/* 527 */         this.entityList = null;
/*     */       } 
/* 529 */       this.abr.addDebug("End processThis()");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateFromToRevenueProfileBom(EntityItem paramEntityItem, String paramString1, TypeModelUPGGeo paramTypeModelUPGGeo, CHWAnnouncement paramCHWAnnouncement, Set<String> paramSet, String paramString2, Vector paramVector) throws Exception {
/* 535 */     RevProfile revProfile = getRevProfile(paramEntityItem);
/* 536 */     if (revProfile.getRevenueProfile() != null) {
/* 537 */       String str = "";
/* 538 */       if ("FROMTYPE".equals(paramString1)) {
/* 539 */         paramTypeModelUPGGeo.setFromTMRevProfileExist(true);
/* 540 */         paramTypeModelUPGGeo.setFromTMRevProfile(revProfile);
/* 541 */         str = paramTypeModelUPGGeo.getFromType();
/* 542 */       } else if ("TOTYPE".equals(paramString1)) {
/* 543 */         paramTypeModelUPGGeo.setToTMRevProfileExist(true);
/* 544 */         paramTypeModelUPGGeo.setToTMRevProfile(revProfile);
/* 545 */         str = paramTypeModelUPGGeo.getType();
/*     */       } else {
/* 547 */         throw new RfcAbrException("updateFromToRevenueProfileBom not found the fromToType " + paramString1);
/*     */       } 
/* 549 */       boolean bool = this.rdhRestProxy.r204(str + "MTC");
/* 550 */       this.abr.addDebug("Call R204 successfully for " + paramString1 + " typeMTCExists " + bool);
/* 551 */       if (bool) {
/* 552 */         this.rdhRestProxy.r205(null, paramTypeModelUPGGeo, "MTC", paramString1, null, null, null, paramString2, paramCHWAnnouncement.getAnnDocNo());
/* 553 */         this.abr.addDebug("Call R205 MTC successfully for " + paramString1);
/* 554 */         for (String str1 : paramSet) {
/* 555 */           updateRevenueProfileBom(str, paramCHWAnnouncement
/* 556 */               .getAnnDocNo(), revProfile
/* 557 */               .getAuoMaterials(), null, revProfile
/* 558 */               .getRevenueProfile(), "MTC", 
/* 559 */               getOpwgId(), paramString2, str1, paramVector);
/* 560 */           this.abr.addDebug("updateRevenueProfileBom successfully for plant " + str1);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 564 */       this.abr.addDebug("updateFromToRevenueProfileBom not found RevProfile for " + paramString1 + " " + paramEntityItem.getKey());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTypeModelUpgrateChanged(Vector paramVector, EntityItem paramEntityItem) throws RfcAbrException {
/* 570 */     EntityItem entityItem = getEntityItemAtT1(paramVector, paramEntityItem);
/* 571 */     if (entityItem != null) {
/* 572 */       String str1 = getAttributeValue(entityItem, "EFFECTIVEDATE");
/* 573 */       String str2 = getAttributeValue(paramEntityItem, "EFFECTIVEDATE");
/* 574 */       if (!str2.equals(str1)) {
/* 575 */         this.abr.addDebug("isTypeModelUpgrateChanged true T1 Date " + str1 + " T2 Date " + str2);
/* 576 */         return true;
/*     */       } 
/*     */     } else {
/* 579 */       this.abr.addDebug("isTypeModelUpgrateChanged true AVAIL is null at T1 for " + paramEntityItem.getKey());
/* 580 */       return true;
/*     */     } 
/* 582 */     this.abr.addDebug("isTypeModelUpgrateChanged false");
/* 583 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createMTCForFromToType(String paramString1, String paramString2, EntityItem paramEntityItem, String paramString3, List<SalesOrgPlants> paramList, CHWAnnouncement paramCHWAnnouncement, CHWGeoAnn paramCHWGeoAnn, TypeModelUPGGeo paramTypeModelUPGGeo, String paramString4, String paramString5, Set<String> paramSet, String paramString6, Vector paramVector) throws RfcAbrException, Exception {
/* 590 */     TypeModel typeModel = getTypeModelForCreateMTC(paramEntityItem);
/* 591 */     paramTypeModelUPGGeo.setProductHierarchy(typeModel.getProductHierarchy());
/* 592 */     paramTypeModelUPGGeo.setLoadingGroup(typeModel.getLoadingGroup());
/* 593 */     this.abr.addDebug("TypeModelUPGGeo ProductHierarchy " + paramTypeModelUPGGeo.getProductHierarchy() + " LoadingGroup " + paramTypeModelUPGGeo.getLoadingGroup());
/* 594 */     this.abr.addDebug("TypeModel " + typeModel.toString());
/* 595 */     createMTC(paramCHWAnnouncement, typeModel, paramCHWGeoAnn, paramTypeModelUPGGeo, paramString4, typeModel
/* 596 */         .getLoadingGroup(), paramString3, paramString5, paramList, paramSet, paramString6, paramVector);
/*     */     
/* 598 */     this.rdhRestProxy.r202(paramString1, paramCHWAnnouncement, paramString5);
/* 599 */     this.abr.addDebug("Call R202 successfully");
/*     */   }
/*     */   
/*     */   private TypeModel getTypeModelForCreateMTC(EntityItem paramEntityItem) throws HWPIMSAbnormalException, RfcAbrException {
/* 603 */     TypeModel typeModel = new TypeModel();
/* 604 */     typeModel.setType(getAttributeFlagValue(paramEntityItem, "MACHTYPEATR"));
/* 605 */     typeModel.setModel(getAttributeValue(paramEntityItem, "MODELATR"));
/* 606 */     typeModel.setLoadingGroup(getLoadingGroup(paramEntityItem));
/* 607 */     typeModel.setDiv(getDiv(paramEntityItem));
/* 608 */     typeModel.setProductHierarchy(getProdHireCd(paramEntityItem));
/* 609 */     typeModel.setProfitCenter(getAttributeFlagValue(paramEntityItem, "PRFTCTR"));
/* 610 */     typeModel.setVendorID("");
/* 611 */     return typeModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createMTC(CHWAnnouncement paramCHWAnnouncement, TypeModel paramTypeModel, CHWGeoAnn paramCHWGeoAnn, TypeModelUPGGeo paramTypeModelUPGGeo, String paramString1, String paramString2, String paramString3, String paramString4, List<SalesOrgPlants> paramList, Set<String> paramSet, String paramString5, Vector paramVector) throws RfcAbrException, Exception {
/* 621 */     this.abr.addDebug("----------------------- Start createMTC for " + paramString3 + " -----------------------");
/* 622 */     this.rdhRestProxy.r100(paramCHWAnnouncement, paramTypeModel, paramCHWGeoAnn, "MTC", paramTypeModelUPGGeo, paramString3, paramString4);
/* 623 */     this.abr.addDebug("Call R100 MTC successfully");
/* 624 */     for (String str : paramSet) {
/* 625 */       this.rdhRestProxy.r101(paramCHWAnnouncement, paramTypeModel, paramCHWGeoAnn, "MTC", paramString2, paramTypeModelUPGGeo, paramString3, paramString4, str);
/* 626 */       this.abr.addDebug("Call R101 MTC successfully for plant " + str);
/*     */     } 
/* 628 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1, ",");
/* 629 */     while (stringTokenizer.hasMoreElements()) {
/* 630 */       String str = stringTokenizer.nextToken();
/* 631 */       this.rdhRestProxy.r189(paramCHWAnnouncement, paramTypeModel, str, "MTC", paramTypeModelUPGGeo, paramString3, paramString4, paramCHWGeoAnn);
/* 632 */       this.abr.addDebug("Call R189 MTC successfully for plant " + str);
/*     */     } 
/*     */     
/* 635 */     for (SalesOrgPlants salesOrgPlants : paramList) {
/* 636 */       Vector<String> vector = salesOrgPlants.getPlants();
/* 637 */       String str = salesOrgPlants.getSalesorg();
/* 638 */       Vector vector1 = getTaxListBySalesOrgPlants(salesOrgPlants);
/* 639 */       for (String str1 : vector) {
/* 640 */         if (str1.equals("1999")) {
/* 641 */           this.abr.addDebug("skip plant " + str1);
/*     */           continue;
/*     */         } 
/* 644 */         this.rdhRestProxy.r102(paramCHWAnnouncement, paramTypeModel, str1, "MTC", paramTypeModelUPGGeo, paramString3, paramString4, paramString5, str, vector1, paramCHWGeoAnn, null);
/* 645 */         this.abr.addDebug("Call R102 MTC successfully for plant " + str1);
/*     */       } 
/*     */     } 
/*     */     
/* 649 */     if (isGENERALAREAContainsZAButNoUS(paramVector)) {
/* 650 */       processTaxSupportForUSWhenProductIsSetForZA(paramTypeModel, paramCHWAnnouncement, paramCHWGeoAnn, paramString4, paramString5, true, paramTypeModelUPGGeo, paramString3, null, null);
/*     */     }
/* 652 */     this.rdhRestProxy.r103(paramTypeModel, "MTC", paramCHWAnnouncement, paramTypeModelUPGGeo, paramString3, paramString4);
/* 653 */     this.abr.addDebug("Call R103 MTC successfully");
/* 654 */     this.rdhRestProxy.r104(paramTypeModel, "MTC", paramCHWAnnouncement, paramTypeModelUPGGeo, paramString3, paramString4);
/* 655 */     this.abr.addDebug("Call R104 MTC successfully");
/* 656 */     this.rdhRestProxy.r157(paramCHWAnnouncement, paramTypeModelUPGGeo, paramString3, paramString4);
/* 657 */     this.abr.addDebug("Call R157 successfully");
/* 658 */     this.rdhRestProxy.r159(paramCHWAnnouncement, paramTypeModelUPGGeo, paramString3, paramString4);
/* 659 */     this.abr.addDebug("Call R159 successfully");
/*     */     
/* 661 */     this.rdhRestProxy.r162(null, paramTypeModelUPGGeo, "MTC", paramCHWAnnouncement, paramString3, paramString4);
/* 662 */     this.abr.addDebug("Call R162 MTC successfully");
/* 663 */     this.rdhRestProxy.r168(paramTypeModelUPGGeo, paramCHWAnnouncement, paramString3, paramString4);
/* 664 */     this.abr.addDebug("Call R168 successfully");
/* 665 */     this.rdhRestProxy.r175(null, paramTypeModelUPGGeo, paramCHWAnnouncement, "MTC", paramString3, paramString4);
/* 666 */     this.abr.addDebug("Call R175 MTC successfully");
/* 667 */     this.rdhRestProxy.r164(paramTypeModelUPGGeo, paramCHWAnnouncement, paramString3, paramString4);
/* 668 */     this.abr.addDebug("Call R164 successfully");
/* 669 */     this.rdhRestProxy.r123(null, paramTypeModelUPGGeo, "MTC", paramCHWAnnouncement, paramString3, paramString4);
/* 670 */     this.abr.addDebug("Call R123 MTC successfully");
/* 671 */     this.rdhRestProxy.r165(paramCHWAnnouncement, paramTypeModelUPGGeo, paramString3, paramString4);
/* 672 */     this.abr.addDebug("Call R165 successfully");
/*     */ 
/*     */ 
/*     */     
/* 676 */     if (paramString3.equals("MTCTOTYPE")) {
/* 677 */       boolean bool = this.rdhRestProxy.r214(paramTypeModelUPGGeo.getType());
/* 678 */       this.abr.addDebug("Call R214 successfully for MTCTOTYPE typeMCclassExists " + bool);
/* 679 */       if (bool) {
/* 680 */         this.rdhRestProxy.r125(paramTypeModelUPGGeo.getType(), paramCHWAnnouncement, "MTC", paramString4);
/* 681 */         this.abr.addDebug("Call R125 MTC successfully for MTCTOTYPE");
/*     */       } 
/*     */     } 
/* 684 */     if (paramString3.equals("MTCFROMTYPE")) {
/* 685 */       boolean bool = this.rdhRestProxy.r214(paramTypeModelUPGGeo.getFromType());
/* 686 */       this.abr.addDebug("Call R214 successfully for MTCFROMTYPE typeMCclassExists " + bool);
/* 687 */       if (bool) {
/* 688 */         this.rdhRestProxy.r125(paramTypeModelUPGGeo.getFromType(), paramCHWAnnouncement, "MTC", paramString4);
/* 689 */         this.abr.addDebug("Call R125 MTC successfully for MTCFROMTYPE");
/*     */       } 
/*     */     } 
/* 692 */     if (paramString3.equals("MTCTOTYPE") && 
/* 693 */       this.isRPQToType) {
/* 694 */       this.rdhRestProxy.r138(paramTypeModelUPGGeo, "MTC", paramCHWAnnouncement, paramString3, paramString4);
/* 695 */       this.abr.addDebug("Call R138 MTC successfully for MTCTOTYPE");
/*     */     } 
/*     */     
/* 698 */     if (paramString3.equals("MTCFROMTYPE") && 
/* 699 */       this.isRPQFromType) {
/* 700 */       this.rdhRestProxy.r138(paramTypeModelUPGGeo, "MTC", paramCHWAnnouncement, paramString3, paramString4);
/* 701 */       this.abr.addDebug("Call R138 MTC successfully for MTCFROMTYPE");
/*     */     } 
/*     */     
/* 704 */     for (byte b = 0; b <= 9; b++) {
/* 705 */       String str = b + "000";
/* 706 */       this.rdhRestProxy.r136(paramTypeModelUPGGeo, str, paramCHWAnnouncement, "MTC", paramString3, paramString4);
/* 707 */       this.abr.addDebug("Call R136 MTC successfully for range " + str);
/* 708 */       this.rdhRestProxy.r137(paramTypeModelUPGGeo, str, paramCHWAnnouncement, "MTC", paramString3, paramString4);
/* 709 */       this.abr.addDebug("Call R137 MTC successfully for range " + str);
/*     */     } 
/* 711 */     this.abr.addDebug("----------------------- End createMTC for " + paramString3 + " -----------------------");
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isRPQType(Vector<EntityItem> paramVector) throws RfcAbrException {
/* 716 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 717 */       EntityItem entityItem = paramVector.get(b);
/* 718 */       if ("Yes".equals(getAttributeValue(entityItem, "RPQRANGE"))) {
/* 719 */         return true;
/*     */       }
/*     */     } 
/* 722 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private EntityItem getModelByMachTypeAndModel(String paramString1, String paramString2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, RfcAbrException {
/* 727 */     String str1 = "SRDMODEL4";
/* 728 */     String str2 = "MODEL";
/* 729 */     StringBuffer stringBuffer = new StringBuffer();
/* 730 */     Vector<String> vector1 = new Vector();
/* 731 */     Vector<String> vector2 = new Vector();
/* 732 */     vector1.add("MACHTYPEATR");
/* 733 */     vector2.add(paramString1);
/* 734 */     vector1.add("MODELATR");
/* 735 */     vector2.add(paramString2);
/* 736 */     vector1.add("COFCAT");
/* 737 */     vector2.add("100");
/* 738 */     this.abr.addDebug("getModelsByMachTypeAndModel searchAction " + str1 + " srchType " + str2 + " with role=" + this.abr
/* 739 */         .getProfile().getRoleCode());
/* 740 */     EntityItem[] arrayOfEntityItem = ABRUtil.doSearch(this.abr.getDatabase(), this.abr.getProfile(), str1, str2, false, vector1, vector2, stringBuffer);
/* 741 */     this.abr.addDebug("getModelsByMachTypeAndModel " + stringBuffer.toString());
/* 742 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 743 */       this.abr.addDebug("getModelsByMachTypeAndModel MODEL size " + arrayOfEntityItem.length);
/* 744 */       return arrayOfEntityItem[0];
/*     */     } 
/* 746 */     throw new RfcAbrException("There is no hardware MODEL Entity for MACHTYPEATR " + paramString1 + " MODELATR " + paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isMachTypePromoted(Vector<EntityItem> paramVector) throws RfcAbrException {
/* 751 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 752 */       EntityItem entityItem = paramVector.get(b);
/* 753 */       String str = getAttributeFlagValue(entityItem, "PROMOTED");
/* 754 */       if ("PRYES".equals(str)) {
/* 755 */         this.abr.addDebug("isMachTypePromoted " + entityItem.getKey() + " PROMOTED attr value " + str);
/* 756 */         return true;
/*     */       } 
/*     */     } 
/* 759 */     this.abr.addDebug("isMachTypePromoted not found promoted MACHTYPE");
/* 760 */     return false;
/*     */   }
/*     */   
/*     */   private EntityItem getModelByVe2(String paramString, int paramInt) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
/* 764 */     String str = this.abr.getCurrentTime();
/* 765 */     return getModelByVe2(paramString, paramInt, str, str);
/*     */   }
/*     */   
/*     */   private EntityItem getModelByVe2(String paramString1, int paramInt, String paramString2, String paramString3) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
/* 769 */     Profile profile = this.abr.switchRole("BHFEED");
/* 770 */     profile.setValOnEffOn(paramString2, paramString2);
/* 771 */     profile.setEndOfDay(paramString3);
/* 772 */     profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 773 */     profile.setLoginTime(paramString3);
/* 774 */     EntityList entityList = getEntityList(this.abr.getDatabase(), profile, getVeName2(), paramString1, paramInt);
/* 775 */     this.abr.addDebug("getModelByVe2 EntityList for " + profile.getValOn() + " extract " + getVeName2() + " contains the following entities: \n" + 
/* 776 */         PokUtils.outputList(entityList));
/* 777 */     return entityList.getParentEntityGroup().getEntityItem(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void createSalesBOMforTypeMTC(String paramString1, String paramString2, CHWAnnouncement paramCHWAnnouncement, String paramString3) throws Exception {
/* 782 */     this.abr.addDebug("----------------------- Start createSalesBOMforTypeMTC for " + paramString1 + " -----------------------");
/* 783 */     boolean bool = this.rdhRestProxy.r204(paramString1 + "MTC");
/* 784 */     this.abr.addDebug("Call R204 successfully typeMTCExists " + bool);
/* 785 */     if (bool) {
/* 786 */       ArrayList<String> arrayList = new ArrayList();
/* 787 */       arrayList.add("1222");
/* 788 */       arrayList.add("1999");
/* 789 */       for (String str1 : arrayList) {
/*     */         
/* 791 */         String str2 = "./locks/MODELCONVERT" + paramString1 + str1 + ".lock";
/* 792 */         File file = new File(str2);
/* 793 */         (new File(file.getParent())).mkdirs();
/* 794 */         try(FileOutputStream null = new FileOutputStream(file); FileChannel null = fileOutputStream.getChannel()) {
/*     */           while (true) {
/*     */             try {
/* 797 */               FileLock fileLock = fileChannel.tryLock();
/* 798 */               if (fileLock != null) {
/* 799 */                 this.abr.addDebug("Start lock, lock file " + str2);
/*     */                 
/* 801 */                 Vector vector = this.rdhRestProxy.r210(paramString1, "NEW", str1);
/* 802 */                 this.abr.addDebug("Call R210 NEW successfully for plant " + str1);
/* 803 */                 if (vector == null || vector.size() == 0) {
/* 804 */                   this.abr.addDebug("Call R210 NEW successfully typeNEW is null ");
/*     */                   break;
/*     */                 } 
/* 807 */                 this.abr.addDebug("Reading Sales Bom for TypeNEW: " + vector.size());
/*     */                 try {
/* 809 */                   Vector vector1 = this.rdhRestProxy.r210(paramString1, "MTC", str1);
/* 810 */                   this.abr.addDebug("Call R210 MTC successfully");
/* 811 */                   if (vector1 != null) {
/* 812 */                     this.abr.addDebug("Reading Sales Bom for TypeMTC: " + vector1.size());
/*     */                   }
/* 814 */                   if (vector1 == null || vector1.size() == 0) {
/*     */                     
/* 816 */                     Vector vector4 = getComponentByTypeModel(paramString1, paramString2, vector);
/* 817 */                     this.rdhRestProxy.r211(paramString1, str1, vector4, "MTC", paramCHWAnnouncement, paramString3);
/* 818 */                     this.abr.addDebug("createSalesBOMforTypeMTC no row returned for typeMTC, R211 create MTC components size: " + vector4.size()); break;
/*     */                   } 
/* 820 */                   Vector vector2 = getComponentsintypeMTCwithtypeNEW(vector1, vector);
/* 821 */                   if (vector2 != null) {
/* 822 */                     this.rdhRestProxy.r212(paramString1, str1, vector2, "MTC", paramCHWAnnouncement, paramString3);
/* 823 */                     this.abr.addDebug("createSalesBOMforTypeMTC r212 delete typeMTC size: " + vector2.size());
/*     */                   } 
/* 825 */                   Vector vector3 = getComponentsintypeNEW(vector1, vector);
/* 826 */                   if (vector3 != null) {
/* 827 */                     Vector vector4 = getComponentByTypeModel(paramString1, paramString2, vector3);
/* 828 */                     this.abr.addDebug("createSalesBOMforTypeMTC updateMTC size:" + vector4.size());
/* 829 */                     System.out.println("createSalesBOMforTypeMTC updateMTC size:" + vector4.size());
/* 830 */                     if (vector4.size() > 0) {
/* 831 */                       this.rdhRestProxy.r213(paramString1, str1, vector4, "MTC", paramCHWAnnouncement, paramString3);
/* 832 */                       this.abr.addDebug("Call R213 MTC successfully");
/*     */                     }
/*     */                   
/*     */                   }
/*     */                 
/* 837 */                 } catch (HWPIMSNotFoundInMastException hWPIMSNotFoundInMastException) {
/* 838 */                   this.abr.addDebug("Read Sales Bom has met an error: " + hWPIMSNotFoundInMastException);
/* 839 */                   Vector vector1 = getComponentByTypeModel(paramString1, paramString2, vector);
/* 840 */                   this.rdhRestProxy.r211(paramString1, str1, vector1, "MTC", paramCHWAnnouncement, paramString3);
/* 841 */                   this.abr.addDebug("createSalesBOMforTypeMTC no row returned for typeMTC, R211 create MTC components size: " + vector1.size());
/*     */                 } 
/*     */                 
/*     */                 break;
/*     */               } 
/* 846 */               this.abr.addDebug("fileLock == null");
/* 847 */               Thread.sleep(5000L);
/*     */             }
/* 849 */             catch (OverlappingFileLockException overlappingFileLockException) {
/* 850 */               this.abr.addDebug("other abr is running createSalesBOMforTypeMTC");
/* 851 */               Thread.sleep(5000L);
/*     */             } 
/*     */           } 
/*     */         } 
/* 855 */         this.abr.addDebug("End lock");
/*     */       } 
/*     */     } 
/*     */     
/* 859 */     this.abr.addDebug("----------------------- End createSalesBOMforTypeMTC for " + paramString1 + " -----------------------");
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
/*     */   private Vector getComponentByTypeModel(String paramString1, String paramString2, Vector paramVector) {
/* 871 */     Vector<DepData> vector = new Vector();
/* 872 */     String str = paramString1 + paramString2;
/* 873 */     for (DepData depData1 : paramVector) {
/* 874 */       DepData depData2 = depData1;
/* 875 */       if (str.equals(depData2.getComponent())) {
/* 876 */         depData2.setDep_Intern("SC_MK_" + paramString1 + "_MODEL_" + paramString2);
/* 877 */         vector.add(depData2);
/*     */       } 
/*     */     } 
/* 880 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getComponentsintypeMTCwithtypeNEW(Vector paramVector1, Vector paramVector2) {
/* 887 */     Vector<DepData> vector = new Vector();
/* 888 */     Vector<String> vector1 = new Vector();
/* 889 */     Enumeration<DepData> enumeration1 = paramVector2.elements();
/* 890 */     Enumeration<DepData> enumeration2 = paramVector1.elements();
/* 891 */     this.abr.addDebug("Vecor Size for typeMTC" + paramVector1.size());
/* 892 */     this.abr.addDebug("Vecor Size for typeNEW" + paramVector2.size());
/*     */     
/* 894 */     DepData depData = new DepData();
/* 895 */     while (enumeration1.hasMoreElements()) {
/* 896 */       depData = enumeration1.nextElement();
/* 897 */       String str = depData.getComponent();
/* 898 */       this.abr.addDebug("vector typeMTC component value" + depData
/* 899 */           .getComponent());
/* 900 */       vector1.addElement(str);
/*     */     } 
/* 902 */     while (enumeration2.hasMoreElements()) {
/* 903 */       DepData depData1 = enumeration2.nextElement();
/* 904 */       String str = depData1.getComponent();
/* 905 */       this.abr.addDebug("vector typeNEW component value" + depData1
/* 906 */           .getComponent());
/* 907 */       if (vector1.contains(str)) {
/* 908 */         if (vector != null && vector.size() == 0)
/* 909 */           vector = null;  continue;
/*     */       } 
/* 911 */       if (vector == null) {
/* 912 */         vector = new Vector();
/*     */       }
/* 914 */       vector.add(depData1);
/*     */     } 
/*     */     
/* 917 */     return vector;
/*     */   }
/*     */ 
/*     */   
/*     */   private Vector getComponentsintypeNEW(Vector paramVector1, Vector paramVector2) {
/* 922 */     Vector<DepData> vector = new Vector();
/* 923 */     Vector<String> vector1 = new Vector();
/* 924 */     Enumeration<DepData> enumeration1 = paramVector1.elements();
/* 925 */     Enumeration<DepData> enumeration2 = paramVector2.elements();
/* 926 */     while (enumeration1.hasMoreElements()) {
/* 927 */       DepData depData = enumeration1.nextElement();
/* 928 */       String str = depData.getComponent();
/* 929 */       vector1.addElement(str);
/* 930 */       this.abr.addDebug("vector typeMTC component value" + depData.getComponent());
/*     */     } 
/* 932 */     this.abr.addDebug("vector size of vect1" + vector1.size());
/*     */     
/* 934 */     while (enumeration2.hasMoreElements()) {
/* 935 */       DepData depData = enumeration2.nextElement();
/* 936 */       String str = depData.getComponent();
/* 937 */       this.abr.addDebug("vector typeNEW component value" + depData.getComponent());
/* 938 */       if (vector1.contains(str)) {
/* 939 */         if (vector != null && vector.size() == 0)
/* 940 */           vector = null;  continue;
/*     */       } 
/* 942 */       if (vector == null) {
/* 943 */         vector = new Vector();
/*     */       }
/* 945 */       vector.add(depData);
/*     */     } 
/*     */     
/* 948 */     return vector;
/*     */   }
/*     */   
/*     */   public String getVeName() {
/* 952 */     return "RFCMODELCONVERT";
/*     */   }
/*     */   
/*     */   private String getVeName2() {
/* 956 */     return "RFCMODELCONVERT2";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RFCMODELCONVERTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */