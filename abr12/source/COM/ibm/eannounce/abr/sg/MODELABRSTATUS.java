/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANEntity;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MODELABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/* 119 */   private Object[] args = (Object[])new String[10];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/* 125 */     return !"0020".equals(paramString);
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
/*     */   protected void doAlreadyFinalProcessing() {
/* 140 */     setFlagValue(this.m_elist.getProfile(), "SAPLABRSTATUS", "0020");
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 156 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", "0020");
/* 157 */     propagateOStoWWSEO(this.m_elist.getParentEntityGroup().getEntityItem(0), this.m_elist.getEntityGroup("WWSEO"));
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
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 182 */     checkAssortModule();
/* 183 */     propagateOStoWWSEO(this.m_elist.getParentEntityGroup().getEntityItem(0), this.m_elist.getEntityGroup("WWSEO"));
/* 184 */     queueSapl();
/* 185 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", "0020");
/* 186 */     setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020");
/* 187 */     setFlagValue(this.m_elist.getProfile(), "EPIMSABRSTATUS", "0020");
/*     */ 
/*     */     
/* 190 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/* 191 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 192 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 193 */       String str1 = getAttributeFlagEnabledValue(entityItem, "ANNSTATUS");
/* 194 */       String str2 = getAttributeFlagEnabledValue(entityItem, "ANNTYPE");
/* 195 */       addDebug(entityItem.getKey() + " status " + str1 + " type " + str2);
/* 196 */       if (str1 == null || str1.length() == 0) {
/* 197 */         str1 = "0020";
/*     */       }
/* 199 */       if ("0020".equals(str1) && "19".equals(str2)) {
/* 200 */         addDebug(entityItem.getKey() + " is Final and New");
/* 201 */         setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020", entityItem);
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
/*     */   protected void completeNowFinalProcessingForOtherDomains() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 215 */     queueSapl();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 286 */     int i = 0;
/* 287 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "COFCAT");
/* 288 */     if (str == null) {
/* 289 */       str = "";
/*     */     }
/* 291 */     addDebug(paramEntityItem.getKey() + " COFCAT: " + str);
/*     */ 
/*     */     
/* 294 */     if ("100".equals(str)) {
/* 295 */       checkHardware(paramEntityItem, paramString);
/*     */     }
/*     */     
/* 298 */     if ("102".equals(str)) {
/*     */ 
/*     */ 
/*     */       
/* 302 */       i = getCount("MDLCGMDL");
/* 303 */       if (i > 0) {
/* 304 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("MODELCG");
/*     */         
/* 306 */         this.args[0] = entityGroup.getLongDescription();
/* 307 */         addError("SVC_SEOCG_ERR", this.args);
/*     */       } 
/*     */ 
/*     */       
/* 311 */       i = getCount("SEOCGSEO");
/* 312 */       if (i > 0) {
/* 313 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("SEOCG");
/*     */         
/* 315 */         this.args[0] = entityGroup.getLongDescription();
/* 316 */         addError("SVC_SEOCG_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     checkModelAvails(paramEntityItem, paramString);
/*     */     
/* 327 */     if ("0040".equals(paramString))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 335 */       checkAvailDates();
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
/*     */   private void checkAnn(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 348 */     Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "AVAIL");
/* 349 */     Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 350 */     addDebug("checkAnn " + paramEntityItem.getKey() + " availVct " + vector1.size() + " plannedavailVector " + vector2.size());
/*     */     
/* 352 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/*     */     
/* 354 */     Vector vector3 = PokUtils.getAllLinkedEntities(entityGroup, "OOFAVAIL", "AVAIL");
/* 355 */     Vector<EntityItem> vector4 = PokUtils.getEntitiesWithMatchedAttr(vector3, "AVAILTYPE", "146");
/* 356 */     addDebug("checkAnn psavailVct: " + vector3.size() + " plannedPsAvailVector: " + vector4.size());
/*     */     
/* 358 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 359 */     HashSet<String> hashSet = new HashSet(); byte b;
/* 360 */     for (b = 0; b < vector2.size(); b++) {
/* 361 */       EntityItem entityItem = vector2.elementAt(b);
/* 362 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("ANNCODENAME");
/* 363 */       addDebug("modelavail " + entityItem.getKey() + " ANNCODENAME " + 
/* 364 */           PokUtils.getAttributeFlagValue(entityItem, "ANNCODENAME") + NEWLINE);
/* 365 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 367 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 368 */         for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */           
/* 370 */           if (arrayOfMetaFlag[b1].isSelected()) {
/* 371 */             hashtable.put(arrayOfMetaFlag[b1].getFlagCode(), entityItem);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 376 */     for (b = 0; b < vector4.size(); b++) {
/* 377 */       EntityItem entityItem = vector4.elementAt(b);
/* 378 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("ANNCODENAME");
/* 379 */       addDebug("psavail " + entityItem.getKey() + " ANNCODENAME " + 
/* 380 */           PokUtils.getAttributeFlagValue(entityItem, "ANNCODENAME") + NEWLINE);
/* 381 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 383 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 384 */         for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */           
/* 386 */           if (arrayOfMetaFlag[b1].isSelected()) {
/* 387 */             hashSet.add(arrayOfMetaFlag[b1].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 394 */     Iterator<String> iterator = hashtable.keySet().iterator();
/* 395 */     while (iterator.hasNext()) {
/* 396 */       String str = iterator.next();
/* 397 */       if (!hashSet.contains(str)) {
/* 398 */         EntityItem entityItem = (EntityItem)hashtable.get(str);
/*     */ 
/*     */ 
/*     */         
/* 402 */         this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 403 */         this.args[1] = getNavigationName(entityItem);
/* 404 */         this.args[2] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "ANNCODENAME", "ANNCODENAME");
/* 405 */         this.args[3] = this.m_elist.getEntityGroup("FEATURE").getLongDescription();
/* 406 */         addError("NO_ANNOUNCE_FEATURE", this.args);
/*     */       } 
/*     */     } 
/*     */     
/* 410 */     hashtable.clear();
/* 411 */     hashSet.clear();
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
/*     */   private void checkAvailDates() throws SQLException, MiddlewareException {
/* 425 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 426 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 427 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*     */       
/* 429 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "AVAIL");
/* 430 */       addDebug("checkAvailDates " + entityItem.getKey() + " availVct: " + vector.size());
/*     */       
/* 432 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 433 */         EntityItem entityItem1 = vector.elementAt(b1);
/* 434 */         String str1 = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", ", ", "", false);
/* 435 */         String str2 = getAttributeFlagEnabledValue(entityItem1, "AVAILTYPE");
/* 436 */         addDebug("checkAvailDates " + entityItem1.getKey() + " EFFECTIVEDATE: " + str1 + " AVAILTYPE: " + str2);
/* 437 */         if (("146".equals(str2) || "143".equals(str2)) && str1
/* 438 */           .length() > 0) {
/*     */ 
/*     */           
/* 441 */           Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "AVAILANNA", "ANNOUNCEMENT");
/* 442 */           addDebug("checkAvailDates " + entityItem1.getKey() + " annVct: " + vector1.size());
/* 443 */           for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 444 */             EntityItem entityItem2 = vector1.elementAt(b2);
/* 445 */             String str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "", false);
/* 446 */             addDebug("checkAvailDates " + entityItem2.getKey() + " annDate: " + str);
/* 447 */             if (str.length() > 0 && str1.compareTo(str) < 0) {
/*     */ 
/*     */               
/* 450 */               this.args[0] = entityItem1.getEntityGroup().getLongDescription() + " " + getNavigationName(entityItem1);
/* 451 */               this.args[1] = entityItem2.getEntityGroup().getLongDescription();
/* 452 */               this.args[2] = getNavigationName(entityItem2);
/* 453 */               this.args[3] = PokUtils.getAttributeDescription(entityItem2.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 454 */               addError("EARLY_DATE_ERR", this.args);
/*     */             } 
/*     */           } 
/* 457 */           vector1.clear();
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
/*     */   public String getDescription() {
/* 470 */     return "MODEL ABR";
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
/* 481 */     return "1.43";
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
/*     */   private void checkHardware(EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException {
/* 517 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "COFSUBCAT");
/*     */     
/* 519 */     if (str == null) {
/* 520 */       str = "";
/*     */     }
/* 522 */     addDebug("checkHardware " + paramEntityItem.getKey() + " COFSUBCAT: " + str);
/* 523 */     if ("126".equals(str)) {
/* 524 */       int i = 0;
/* 525 */       if ("0040".equals(paramString)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 530 */         i = getCount("MODELIMG");
/* 531 */         if (i == 0) {
/* 532 */           EntityGroup entityGroup = this.m_elist.getEntityGroup("IMG");
/*     */           
/* 534 */           this.args[0] = entityGroup.getLongDescription();
/* 535 */           addError("MINIMUM_ERR", this.args);
/*     */         } 
/*     */ 
/*     */         
/* 539 */         i = getCount("MODELWARR");
/* 540 */         if (i == 0) {
/* 541 */           EntityGroup entityGroup = this.m_elist.getEntityGroup("WARR");
/*     */           
/* 543 */           this.args[0] = entityGroup.getLongDescription();
/* 544 */           addError("MINIMUM_ERR", this.args);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 550 */       i = getCount("MDLCGMDL");
/* 551 */       if (i == 0) {
/* 552 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("MODELCG");
/*     */         
/* 554 */         this.args[0] = entityGroup.getLongDescription();
/* 555 */         addError("MUST_BE_IN_ATLEAST_ONE_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */     
/* 559 */     if ("0040".equals(paramString)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 564 */       int i = getCount("MODELMONITOR");
/* 565 */       if (i > 1) {
/* 566 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("MONITOR");
/*     */         
/* 568 */         this.args[0] = entityGroup.getLongDescription();
/* 569 */         addError("MORE_THAN_ONE_ERR", this.args);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 575 */       checkAnn(paramEntityItem);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 587 */       checkAnnDate(paramEntityItem);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAnnDate(EntityItem paramEntityItem) throws MiddlewareException, SQLException {
/* 608 */     String str = getAttributeValue(paramEntityItem, "ANNDATE", "");
/* 609 */     addDebug("checkAnnDate " + paramEntityItem.getKey() + " ANNDATE: " + str);
/* 610 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/* 611 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*     */       
/* 613 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*     */       
/* 615 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "AVAIL");
/* 616 */       String str1 = getAttributeValue(entityItem1, "ANNDATE", "");
/* 617 */       addDebug("checkAnnDate " + entityItem1.getKey() + " ANNDATE: " + str1 + " availVct: " + vector.size());
/* 618 */       EntityItem entityItem2 = (EntityItem)getUpLinkEntityItem(entityItem1, "FEATURE");
/* 619 */       String str2 = getAttributeValue(entityItem2, "FIRSTANNDATE", "");
/* 620 */       addDebug("checkAnnDate " + entityItem2.getKey() + " FIRSTANNDATE: " + str2);
/*     */ 
/*     */       
/* 623 */       if (str1.length() > 0) {
/*     */ 
/*     */ 
/*     */         
/* 627 */         if (str2.length() > 0 && str1.compareTo(str2) < 0) {
/*     */ 
/*     */           
/* 630 */           this.args[0] = entityGroup.getLongDescription() + " " + 
/* 631 */             PokUtils.getAttributeDescription(entityGroup, "ANNDATE", "ANNDATE");
/* 632 */           this.args[1] = entityItem2.getEntityGroup().getLongDescription();
/* 633 */           this.args[2] = PokUtils.getAttributeDescription(entityItem2.getEntityGroup(), "FIRSTANNDATE", "FIRSTANNDATE");
/* 634 */           this.args[3] = "";
/* 635 */           addError("EARLY_DATE_ERR", this.args);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 640 */         if (str.length() > 0 && str1.compareTo(str) < 0) {
/*     */ 
/*     */           
/* 643 */           this.args[0] = entityGroup.getLongDescription() + " " + 
/* 644 */             PokUtils.getAttributeDescription(entityGroup, "ANNDATE", "ANNDATE");
/* 645 */           this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 646 */           this.args[2] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 647 */           this.args[3] = "";
/* 648 */           addError("EARLY_DATE_ERR", this.args);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 653 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 654 */           EntityItem entityItem = vector.elementAt(b1);
/* 655 */           String str3 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 656 */           String str4 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/* 657 */           addDebug("checkAnnDate " + entityItem.getKey() + " EFFECTIVEDATE: " + str3 + " AVAILTYPE: " + str4);
/*     */           
/* 659 */           if ("146".equals(str4) && str3
/* 660 */             .length() > 0 && str3.compareTo(str1) < 0) {
/*     */ 
/*     */             
/* 663 */             this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 664 */             this.args[1] = getNavigationName(entityItem1);
/* 665 */             this.args[2] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 666 */             this.args[3] = entityItem.getEntityGroup().getLongDescription();
/* 667 */             this.args[4] = getNavigationName(entityItem);
/* 668 */             addError("LATER_DATE_ERR", this.args);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 672 */         addDebug("No ANNDATE found for " + entityItem1.getKey());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 677 */         if (str2.length() > 0) {
/* 678 */           for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 679 */             EntityItem entityItem = vector.elementAt(b1);
/* 680 */             addDebug("checkAnnDate no PRODSTRUCT.ANNDATE checking " + entityItem.getKey());
/* 681 */             if (PokUtils.isSelected(entityItem, "AVAILTYPE", "146")) {
/* 682 */               String str3 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 683 */               addDebug("checkAnnDate plannedavail " + entityItem.getKey() + " EFFECTIVEDATE: " + str3);
/* 684 */               if (str3.length() > 0 && str3.compareTo(str2) < 0) {
/*     */ 
/*     */                 
/* 687 */                 this.args[0] = entityItem2.getEntityGroup().getLongDescription();
/* 688 */                 this.args[1] = getNavigationName(entityItem1);
/* 689 */                 this.args[2] = PokUtils.getAttributeDescription(entityItem2.getEntityGroup(), "FIRSTANNDATE", "FIRSTANNDATE");
/* 690 */                 this.args[3] = entityItem.getEntityGroup().getLongDescription();
/* 691 */                 this.args[4] = getNavigationName(entityItem);
/* 692 */                 addError("LATER_DATE_ERR", this.args);
/*     */               } 
/*     */             } 
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
/*     */   
/*     */   private void checkModelAvails(EntityItem paramEntityItem, String paramString) throws MiddlewareException, SQLException {
/* 711 */     Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "AVAIL");
/* 712 */     Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "146");
/* 713 */     addDebug("checkModelAvails " + paramEntityItem.getKey() + " availVct " + vector.size() + " plannedavailVector " + vector1.size());
/* 714 */     vector.clear();
/* 715 */     if (vector1.size() == 0) {
/*     */ 
/*     */       
/* 718 */       this.args[0] = "Planned Availability";
/* 719 */       addError("MINIMUM_ERR", this.args);
/* 720 */     } else if ("0040".equals(paramString)) {
/*     */ 
/*     */       
/* 723 */       String str = getAttributeValue(paramEntityItem, "ANNDATE", "");
/* 724 */       addDebug("checkModelAvails " + paramEntityItem.getKey() + " ANNDATE: " + str);
/* 725 */       if (str.length() > 0) {
/* 726 */         for (byte b = 0; b < vector1.size(); b++) {
/* 727 */           EntityItem entityItem = vector1.elementAt(b);
/* 728 */           String str1 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 729 */           addDebug("checkModelAvails " + entityItem.getKey() + " EFFECTIVEDATE: " + str1);
/*     */           
/* 731 */           if (str1.length() > 0 && str1.compareTo(str) < 0) {
/*     */ 
/*     */             
/* 734 */             this.args[0] = "";
/* 735 */             this.args[1] = "";
/* 736 */             this.args[2] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "ANNDATE", "ANNDATE");
/* 737 */             this.args[3] = entityItem.getEntityGroup().getLongDescription();
/* 738 */             this.args[4] = getNavigationName(entityItem);
/* 739 */             addError("LATER_DATE_ERR", this.args);
/*     */           } 
/*     */         } 
/*     */       }
/* 743 */       vector1.clear();
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
/*     */   private EANEntity getUpLinkEntityItem(EntityItem paramEntityItem, String paramString) {
/* 756 */     EANEntity eANEntity = null;
/* 757 */     for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 758 */       EANEntity eANEntity1 = paramEntityItem.getUpLink(b);
/* 759 */       if (eANEntity1.getEntityType().equals(paramString)) {
/* 760 */         eANEntity = eANEntity1;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 765 */     return eANEntity;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\MODELABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */