/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
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
/*     */ public class SWPRODSTRUCTABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*  92 */   private Object[] args = new Object[3];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doAlreadyFinalProcessing() {
/* 107 */     setFlagValue(this.m_elist.getProfile(), "SAPLABRSTATUS", "0020");
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 139 */     queueSapl();
/* 140 */     setFlagValue(this.m_elist.getProfile(), "EPIMSABRSTATUS", "0020");
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
/*     */   protected void completeNowFinalProcessingForOtherDomains() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 153 */     queueSapl();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 200 */     checkAllFeatures();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     checkRPQAvails(paramEntityItem, paramString);
/*     */     
/* 221 */     if ("0010".equals(paramString) || "0050".equals(paramString)) {
/*     */       
/* 223 */       EntityItem entityItem1 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/* 224 */       EntityItem entityItem2 = this.m_elist.getEntityGroup("SWFEATURE").getEntityItem(0);
/*     */ 
/*     */ 
/*     */       
/* 228 */       String str = getAttributeFlagEnabledValue(entityItem1, "STATUS");
/* 229 */       addDebug(entityItem1.getKey() + " check status " + str);
/* 230 */       if (str == null) {
/* 231 */         str = "0020";
/*     */       }
/*     */       
/* 234 */       if (!"0020".equals(str) && !"0040".equals(str)) {
/* 235 */         addDebug(entityItem1.getKey() + " is not Final or R4R");
/*     */         
/* 237 */         this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 238 */         this.args[1] = getNavigationName(entityItem1);
/* 239 */         addError("NOT_R4R_FINAL_ERR", this.args);
/*     */       } 
/*     */ 
/*     */       
/* 243 */       str = getAttributeFlagEnabledValue(entityItem2, "STATUS");
/* 244 */       addDebug(entityItem2.getKey() + " check status " + str);
/* 245 */       if (str == null) {
/* 246 */         str = "0020";
/*     */       }
/*     */       
/* 249 */       if (!"0020".equals(str) && !"0040".equals(str)) {
/* 250 */         addDebug(entityItem2.getKey() + " is not Final or R4R");
/*     */         
/* 252 */         this.args[0] = entityItem2.getEntityGroup().getLongDescription();
/* 253 */         this.args[1] = getNavigationName(entityItem2);
/* 254 */         addError("NOT_R4R_FINAL_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */     
/* 258 */     if ("0040".equals(paramString)) {
/*     */ 
/*     */ 
/*     */       
/* 262 */       checkStatus("MODEL");
/*     */ 
/*     */ 
/*     */       
/* 266 */       checkStatus("SWFEATURE");
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
/*     */   private void checkAllFeatures() throws Exception {
/* 280 */     Profile profile = this.m_elist.getProfile();
/* 281 */     String str = "DQVESWPRODSTRUCT2";
/*     */     
/* 283 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*     */ 
/*     */     
/* 286 */     EntityList entityList = this.m_db.getEntityList(profile, new ExtractActionItem(null, this.m_db, profile, str), new EntityItem[] { new EntityItem(null, profile, "MODEL", entityItem1
/*     */             
/* 288 */             .getEntityID()) });
/* 289 */     addDebug("checkAllFeatures:: Extract " + str + NEWLINE + PokUtils.outputList(entityList));
/*     */     
/* 291 */     EntityItem entityItem2 = entityList.getParentEntityGroup().getEntityItem(0);
/*     */     
/* 293 */     Vector<String> vector = new Vector();
/* 294 */     for (byte b = 0; b < entityItem2.getUpLinkCount(); b++) {
/* 295 */       EntityItem entityItem = (EntityItem)entityItem2.getUpLink(b);
/* 296 */       if (!entityItem.getEntityType().equals("SWPRODSTRUCT")) {
/* 297 */         addDebug("checkAllFeatures skipping uplink " + entityItem.getKey());
/*     */       } else {
/*     */         
/* 300 */         for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 301 */           EntityItem entityItem3 = (EntityItem)entityItem.getUpLink(b1);
/*     */           
/* 303 */           String str1 = PokUtils.getAttributeValue(entityItem3, "FEATURECODE", ", ", "", false);
/* 304 */           addDebug("checkAllFeatures checking " + entityItem.getKey() + " -- " + entityItem3.getKey() + " fcode: " + str1);
/* 305 */           if (vector.contains(str1)) {
/*     */ 
/*     */ 
/*     */             
/* 309 */             this.args[0] = entityItem3.getEntityGroup().getLongDescription();
/* 310 */             this.args[1] = getNavigationName(entityItem3);
/* 311 */             this.args[2] = entityItem2.getEntityGroup().getLongDescription();
/* 312 */             addError("NOT_UNIQUE_ERR", this.args);
/*     */           } else {
/* 314 */             vector.add(str1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 319 */     vector.clear();
/*     */     
/* 321 */     entityList.dereference();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkRPQAvails(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/* 332 */     HashSet<String> hashSet = new HashSet();
/*     */     
/* 334 */     hashSet.add("140");
/*     */     
/* 336 */     EntityItem entityItem = this.m_elist.getEntityGroup("SWFEATURE").getEntityItem(0);
/* 337 */     addDebug("checkRPQAvails checking " + entityItem.getKey());
/*     */ 
/*     */     
/* 340 */     if (!PokUtils.contains(entityItem, "FCTYPE", hashSet)) {
/* 341 */       addDebug("checkRPQAvails " + entityItem.getKey() + " is NOT an RPQ");
/*     */ 
/*     */       
/* 344 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*     */ 
/*     */       
/* 347 */       String str = getAttributeFlagEnabledValue(paramEntityItem, "SAPL");
/* 348 */       addDebug("checkRPQAvails checking entity: " + paramEntityItem.getKey() + " sapl " + str);
/* 349 */       HashSet<String> hashSet1 = new HashSet();
/* 350 */       hashSet1.add("10");
/* 351 */       hashSet1.add("90");
/*     */       
/* 353 */       if (PokUtils.contains(paramEntityItem, "SAPL", hashSet1)) {
/* 354 */         addDebug("checkRPQAvails " + paramEntityItem.getKey() + " has SAPL of 90 or 10");
/*     */ 
/*     */ 
/*     */         
/* 358 */         byte b1 = 0;
/* 359 */         for (byte b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/* 360 */           EntityItem entityItem1 = entityGroup.getEntityItem(b2);
/* 361 */           addDebug("checkRPQAvails looking for planned avail; checking: " + entityItem1.getKey());
/* 362 */           if (PokUtils.isSelected(entityItem1, "AVAILTYPE", "146")) {
/* 363 */             b1++;
/*     */           }
/*     */         } 
/* 366 */         if (b1 == 0)
/*     */         {
/*     */           
/* 369 */           addError("NO_PLANNEDAVAIL_ERR", (Object[])null);
/*     */         }
/*     */ 
/*     */         
/* 373 */         if ("0040".equals(paramString))
/*     */         {
/*     */ 
/*     */           
/* 377 */           checkAnnDate(paramEntityItem, entityGroup);
/*     */ 
/*     */ 
/*     */           
/* 381 */           checkMinWDate(paramEntityItem, entityGroup);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 387 */           checkLastOrderWDCountries(paramEntityItem, entityGroup);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 392 */           checkPlannedAvailWDCountries(entityGroup);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 397 */           checkAvailDates(entityGroup);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 402 */         addDebug("checkRPQAvails " + paramEntityItem.getKey() + " is not SAPL of 90 or 10");
/*     */       } 
/*     */     } else {
/* 405 */       addDebug("checkRPQAvails " + entityItem.getKey() + " is an RPQ");
/*     */     } 
/*     */     
/* 408 */     hashSet.clear();
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
/*     */   private void checkAvailDates(EntityGroup paramEntityGroup) throws SQLException, MiddlewareException {
/* 420 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 421 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 422 */       String str1 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 423 */       String str2 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/* 424 */       addDebug("checkAvailDates " + entityItem.getKey() + " EFFECTIVEDATE: " + str1 + " AVAILTYPE: " + str2);
/* 425 */       if (("146".equals(str2) || "143".equals(str2)) && str1
/* 426 */         .length() > 0) {
/* 427 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 428 */         addDebug("checkAvailDates " + entityItem.getKey() + " annVct: " + vector.size());
/* 429 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 430 */           EntityItem entityItem1 = vector.elementAt(b1);
/* 431 */           String str = PokUtils.getAttributeValue(entityItem1, "ANNDATE", ", ", "", false);
/* 432 */           addDebug("checkAvailDates " + entityItem1.getKey() + " annDate: " + str);
/* 433 */           if (str.length() > 0 && str1.compareTo(str) < 0) {
/*     */ 
/*     */             
/* 436 */             this.args[0] = entityItem.getEntityGroup().getLongDescription() + " " + getNavigationName(entityItem);
/* 437 */             this.args[1] = entityItem1.getEntityGroup().getLongDescription();
/* 438 */             this.args[2] = getNavigationName(entityItem1);
/* 439 */             this.args[3] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 440 */             addError("EARLY_DATE_ERR", this.args);
/*     */           } 
/*     */         } 
/* 443 */         vector.clear();
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
/*     */   private void checkAnnDate(EntityItem paramEntityItem, EntityGroup paramEntityGroup) throws SQLException, MiddlewareException {
/* 455 */     EntityItem entityItem = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/* 456 */     String str = PokUtils.getAttributeValue(entityItem, "ANNDATE", ", ", "", false);
/* 457 */     addDebug("checkAnnDate " + entityItem.getKey() + " (" + str + ")");
/*     */     
/* 459 */     if (str.length() > 0) {
/* 460 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 461 */         EntityItem entityItem1 = paramEntityGroup.getEntityItem(b);
/* 462 */         addDebug("checkAnnDate looking for planned avail; checking: " + entityItem1.getKey());
/* 463 */         if (PokUtils.isSelected(entityItem1, "AVAILTYPE", "146")) {
/* 464 */           String str1 = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", ", ", "", false);
/* 465 */           addDebug("checkAnnDate plannedavail " + entityItem1.getKey() + " EFFECTIVEDATE: " + str1);
/* 466 */           if (str1.length() > 0 && str.compareTo(str1) > 0) {
/*     */             
/* 468 */             this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 469 */             this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 470 */             addError("EARLY_PLANNEDAVAIL_ERR", this.args);
/*     */           } 
/*     */         } 
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
/*     */   private void checkMinWDate(EntityItem paramEntityItem, EntityGroup paramEntityGroup) throws SQLException, MiddlewareException {
/* 485 */     Vector<String> vector = new Vector(2);
/* 486 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/* 487 */     String str1 = PokUtils.getAttributeValue(entityItem1, "WITHDRAWDATE", ", ", null, false);
/* 488 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("SWFEATURE").getEntityItem(0);
/* 489 */     String str2 = PokUtils.getAttributeValue(entityItem2, "WITHDRAWDATEEFF_T", ", ", null, false);
/* 490 */     addDebug("checkMinWDate " + entityItem2.getKey() + " (" + str2 + ") " + entityItem1
/* 491 */         .getKey() + " (" + str1 + ")");
/*     */     
/* 493 */     if (str1 != null) {
/* 494 */       vector.add(str1);
/*     */     }
/* 496 */     if (str2 != null) {
/* 497 */       vector.add(str2);
/*     */     }
/*     */     
/* 500 */     if (vector.size() > 0) {
/* 501 */       Collections.sort(vector);
/* 502 */       String str = vector.firstElement().toString();
/* 503 */       vector.clear();
/* 504 */       addDebug("checkMinWDate looking for lastorder avail; after " + str);
/*     */       
/* 506 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 507 */         EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 508 */         addDebug("checkMinWDate looking for lastorder avail; checking: " + entityItem.getKey());
/* 509 */         if (PokUtils.isSelected(entityItem, "AVAILTYPE", "149")) {
/* 510 */           String str3 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 511 */           addDebug("checkMinWDate lastorder " + entityItem.getKey() + " EFFECTIVEDATE: " + str3);
/* 512 */           if (str3.length() > 0 && str3.compareTo(str) > 0) {
/*     */             
/* 514 */             this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 515 */             this.args[1] = entityItem2.getEntityGroup().getLongDescription();
/* 516 */             addError("LATE_LASTORDERAVAIL_ERR", this.args);
/*     */           } 
/*     */         } 
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
/*     */   private void checkLastOrderWDCountries(EntityItem paramEntityItem, EntityGroup paramEntityGroup) throws SQLException, MiddlewareException {
/* 532 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/* 533 */     String str1 = PokUtils.getAttributeValue(entityItem1, "WITHDRAWDATE", ", ", null, false);
/* 534 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("SWFEATURE").getEntityItem(0);
/* 535 */     String str2 = PokUtils.getAttributeValue(entityItem2, "WITHDRAWDATEEFF_T", ", ", null, false);
/* 536 */     addDebug("checkLastOrderWDCountries " + entityItem2.getKey() + " (" + str2 + ") " + entityItem1
/* 537 */         .getKey() + " (" + str1 + ")");
/*     */     
/* 539 */     if (str1 != null || str2 != null) {
/* 540 */       ArrayList<String> arrayList1 = new ArrayList();
/* 541 */       ArrayList<String> arrayList2 = new ArrayList();
/*     */       
/* 543 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 544 */         EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 545 */         addDebug("checkLastOrderWDCountries checking avail: " + entityItem.getKey());
/* 546 */         if (PokUtils.isSelected(entityItem, "AVAILTYPE", "149")) {
/* 547 */           addDebug("checkLastOrderWDCountries lastorder " + entityItem.getKey());
/* 548 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 549 */           if (eANFlagAttribute != null) {
/*     */             
/* 551 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 552 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++)
/*     */             {
/* 554 */               if (arrayOfMetaFlag[b1].isSelected() && !arrayList1.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/* 555 */                 arrayList1.add(arrayOfMetaFlag[b1].getFlagCode());
/*     */               }
/*     */             }
/*     */           
/*     */           } 
/* 560 */         } else if (PokUtils.isSelected(entityItem, "AVAILTYPE", "146")) {
/* 561 */           addDebug("checkLastOrderWDCountries plannedavail " + entityItem.getKey());
/* 562 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 563 */           if (eANFlagAttribute != null) {
/*     */             
/* 565 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 566 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */               
/* 568 */               if (arrayOfMetaFlag[b1].isSelected() && !arrayList2.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/* 569 */                 arrayList2.add(arrayOfMetaFlag[b1].getFlagCode());
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 576 */       addDebug("checkLastOrderWDCountries all plannedavail countries " + arrayList2);
/* 577 */       addDebug("checkLastOrderWDCountries all lastorderavail countries " + arrayList1);
/*     */       
/* 579 */       if (!arrayList1.containsAll(arrayList2)) {
/*     */ 
/*     */         
/* 582 */         this.args[0] = paramEntityGroup.getLongDescription();
/* 583 */         addError("NO_LASTORDER_ERR", this.args);
/*     */       } 
/* 585 */       arrayList1.clear();
/* 586 */       arrayList2.clear();
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
/*     */   private void checkPlannedAvailWDCountries(EntityGroup paramEntityGroup) throws SQLException, MiddlewareException {
/* 600 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/*     */     byte b;
/* 603 */     for (b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 604 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 605 */       addDebug("checkPlannedAvailWDCountries checking avail: " + entityItem.getKey());
/* 606 */       if (PokUtils.isSelected(entityItem, "AVAILTYPE", "146")) {
/* 607 */         addDebug("checkPlannedAvailWDCountries plannedavail " + entityItem.getKey());
/* 608 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 609 */         if (eANFlagAttribute != null) {
/*     */           
/* 611 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 612 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */             
/* 614 */             if (arrayOfMetaFlag[b1].isSelected() && !arrayList.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/* 615 */               arrayList.add(arrayOfMetaFlag[b1].getFlagCode());
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 622 */     addDebug("checkPlannedAvailWDCountries all plannedavail countries " + arrayList);
/*     */     
/* 624 */     for (b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 625 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 626 */       addDebug("checkPlannedAvailWDCountries checking avail: " + entityItem.getKey());
/* 627 */       if (PokUtils.isSelected(entityItem, "AVAILTYPE", "149")) {
/* 628 */         ArrayList<String> arrayList1 = new ArrayList();
/* 629 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 630 */         if (eANFlagAttribute != null) {
/*     */           
/* 632 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 633 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */             
/* 635 */             if (arrayOfMetaFlag[b1].isSelected() && !arrayList1.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/* 636 */               arrayList1.add(arrayOfMetaFlag[b1].getFlagCode());
/*     */             }
/*     */           } 
/*     */         } 
/* 640 */         addDebug("checkPlannedAvailWDCountries all lastorder " + entityItem.getKey() + " countries " + arrayList1);
/*     */         
/* 642 */         if (!arrayList.containsAll(arrayList1)) {
/*     */ 
/*     */           
/* 645 */           this.args[0] = paramEntityGroup.getLongDescription();
/* 646 */           this.args[1] = getNavigationName(entityItem);
/* 647 */           addError("NO_PLANNEDAVAIL_ERR2", this.args);
/*     */         } 
/* 649 */         arrayList1.clear();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 654 */     arrayList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 664 */     return "SWPRODSTRUCT ABR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 675 */     return "1.20";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SWPRODSTRUCTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */