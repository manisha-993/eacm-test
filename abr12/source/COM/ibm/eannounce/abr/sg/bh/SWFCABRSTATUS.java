/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import COM.ibm.opicmpdh.objects.Attribute;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ public class SWFCABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/*     */   private static final int MW_VENTITY_LIMIT;
/*     */   
/*     */   static {
/* 125 */     String str = ABRServerProperties.getValue("SWFCABRSTATUS", "_velimit", "5");
/*     */     
/* 127 */     int i = Integer.parseInt(str);
/* 128 */     if (i <= 0) {
/* 129 */       i = 1000;
/*     */     }
/* 131 */     MW_VENTITY_LIMIT = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/* 138 */     return domainInList();
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 167 */     if (doR10processing()) {
/* 168 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 169 */       String str = PokUtils.getAttributeFlagValue(entityItem, "LIFECYCLE");
/* 170 */       addDebug("completeNowR4RProcessing: " + entityItem.getKey() + " lifecycle " + str);
/* 171 */       if (str == null || str.length() == 0) {
/* 172 */         str = "LF01";
/*     */       }
/* 174 */       if ("LF01".equals(str) || "LF02"
/* 175 */         .equals(str)) {
/* 176 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 194 */     if (doR10processing()) {
/* 195 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
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
/*     */   protected boolean updateDerivedData(EntityItem paramEntityItem) throws Exception {
/* 213 */     boolean bool = false;
/*     */     
/* 215 */     String str = PokUtils.getAttributeValue(paramEntityItem, "WITHDRAWDATEEFF_T", "", "9999-12-31", false);
/* 216 */     addDebug("updateDerivedData wdDate: " + str + " now: " + getCurrentDate());
/* 217 */     if (getCurrentDate().compareTo(str) <= 0) {
/* 218 */       bool = execDerivedData(paramEntityItem);
/*     */     }
/* 220 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLCRFRWFName() {
/* 227 */     return "WFLCSWFEATRFR"; } protected String getLCFinalWFName() {
/* 228 */     return "WFLCSWFEATFINAL";
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
/* 275 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*     */     
/* 277 */     setBHInvnameSW(paramEntityItem);
/*     */     
/* 279 */     if (getReturnCode() != 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 284 */     int i = getCheck_W_E_E(paramString);
/*     */ 
/*     */     
/* 287 */     checkCanNotBeEarlier(paramEntityItem, "WITHDRAWDATEEFF_T", "WITHDRAWANNDATE_T", i);
/*     */ 
/*     */     
/* 290 */     if (!isRPQ(paramEntityItem)) {
/* 291 */       addDebug(paramEntityItem.getKey() + " was NOT an RPQ FCTYPE: " + getAttributeFlagEnabledValue(paramEntityItem, "FCTYPE"));
/* 292 */       checkAvails(paramEntityItem, paramString, i);
/*     */     }
/*     */     else {
/*     */       
/* 296 */       addDebug(paramEntityItem.getKey() + " was an RPQ FCTYPE: " + getAttributeFlagEnabledValue(paramEntityItem, "FCTYPE"));
/* 297 */       this.args[0] = "";
/* 298 */       this.args[1] = getLD_Value(paramEntityItem, "FCTYPE");
/* 299 */       addError("FCTYPE_ERR", this.args);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAvails(EntityItem paramEntityItem, String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 340 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("AVAIL");
/* 341 */     if (entityGroup1 == null) {
/* 342 */       throw new MiddlewareException("AVAIL is missing from extract for " + this.m_abri.getVEName());
/*     */     }
/*     */     
/* 345 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "WITHDRAWDATEEFF_T", ", ", null, false);
/* 346 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "WITHDRAWANNDATE_T", ", ", null, false);
/* 347 */     addDebug("checkAvails " + paramEntityItem.getKey() + " WITHDRAWDATEEFF_T: " + str1 + " WITHDRAWANNDATE_T: " + str2);
/*     */     
/* 349 */     Vector<?> vector = PokUtils.getEntitiesWithMatchedAttr(entityGroup1, "AVAILTYPE", "149");
/* 350 */     Vector vector1 = PokUtils.getEntitiesWithMatchedAttr(entityGroup1, "AVAILTYPE", "146");
/*     */     
/* 352 */     addHeading(3, entityGroup1.getLongDescription() + " Planned Avail Checks:");
/*     */     
/* 354 */     addDebug("checkAvails  lastOrderAvailVct " + vector.size() + " plannedAvailVct " + vector1.size());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 359 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     if (vector1.size() > 0 && str1 != null)
/*     */     {
/* 366 */       if (vector.size() == 0) {
/*     */         
/* 368 */         this.args[0] = "Last Order";
/* 369 */         this.args[1] = entityGroup1.getLongDescription();
/* 370 */         createMessage(getCheck_RW_RE_RE(paramString), "AVAIL_ERR", this.args);
/* 371 */         vector1.clear();
/*     */       } else {
/* 373 */         addDebug("checkAvails: look for any plannedavail ctry that isnt in a lastorder by SWPS");
/*     */         
/* 375 */         for (byte b1 = 0; b1 < entityGroup2.getEntityItemCount(); b1++) {
/* 376 */           EntityItem entityItem = entityGroup2.getEntityItem(b1);
/* 377 */           Vector vector2 = PokUtils.getAllLinkedEntities(entityItem, "SWPRODSTRUCTAVAIL", "AVAIL");
/* 378 */           Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector2, "AVAILTYPE", "146");
/* 379 */           Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(vector2, "AVAILTYPE", "149");
/* 380 */           ArrayList arrayList = getCountriesAsList(vector4, getCheck_RW_RE_RE(paramString));
/*     */           
/* 382 */           addDebug("checkAvails " + entityItem.getKey() + " all avail: " + vector2.size() + " plaAvail: " + vector3
/* 383 */               .size() + " loAvail: " + vector4.size() + " loAvailCtry:" + arrayList);
/*     */ 
/*     */           
/* 386 */           for (byte b2 = 0; b2 < vector3.size(); b2++) {
/* 387 */             EntityItem entityItem1 = vector3.elementAt(b2);
/*     */             
/* 389 */             String str = checkCtryMismatch(entityItem1, arrayList, getCheck_RW_RE_RE(paramString));
/* 390 */             if (str.length() > 0) {
/* 391 */               addDebug("checkAvails plannedavail " + entityItem1.getKey() + " COUNTRYLIST had extra [" + str + "]");
/*     */ 
/*     */               
/* 394 */               this.args[0] = getLD_NDN(entityItem) + " " + getLD_NDN(entityItem1);
/* 395 */               createMessage(paramInt, "AVAILCTRY_LASTORDER_ERR", this.args);
/*     */             } 
/*     */           } 
/*     */           
/* 399 */           vector2.clear();
/* 400 */           vector3.clear();
/* 401 */           vector4.clear();
/* 402 */           arrayList.clear();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 407 */     if (vector.size() > 0) {
/* 408 */       addHeading(3, entityGroup1.getLongDescription() + " Last Order Avail Checks:");
/* 409 */       addDebug("checkAvails: look for any lastorder ctry that isnt in a plannedavail by SWPS");
/* 410 */       for (byte b1 = 0; b1 < entityGroup2.getEntityItemCount(); b1++) {
/* 411 */         EntityItem entityItem1 = entityGroup2.getEntityItem(b1);
/* 412 */         EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "MODEL");
/* 413 */         int i = getCheckLevel(4, entityItem2, "ANNDATE");
/*     */         
/* 415 */         Vector vector2 = PokUtils.getAllLinkedEntities(entityItem1, "SWPRODSTRUCTAVAIL", "AVAIL");
/* 416 */         Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector2, "AVAILTYPE", "146");
/* 417 */         Vector<EntityItem> vector4 = PokUtils.getEntitiesWithMatchedAttr(vector2, "AVAILTYPE", "149");
/* 418 */         ArrayList arrayList = getCountriesAsList(vector3, getCheck_RW_RE_RE(paramString));
/*     */         
/* 420 */         addDebug("checkAvails " + entityItem1.getKey() + " all avail: " + vector2.size() + " plaAvail: " + vector3
/* 421 */             .size() + " loAvail: " + vector4.size() + " plaAvailCtry:" + arrayList);
/*     */ 
/*     */         
/* 424 */         for (byte b2 = 0; b2 < vector4.size(); b2++) {
/* 425 */           EntityItem entityItem = vector4.elementAt(b2);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 430 */           checkCanNotBeLater(entityItem1, entityItem, "EFFECTIVEDATE", paramEntityItem, "WITHDRAWDATEEFF_T", paramInt);
/*     */ 
/*     */           
/* 433 */           checkPlannedAvailForCtryExists(entityItem1, entityItem, arrayList, i);
/*     */           
/* 435 */           String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 436 */           addDebug("checkAvails LO " + entityItem.getKey() + " availAnntypeFlag " + str);
/* 437 */           if (str == null) {
/* 438 */             str = "RFA";
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 443 */           if (!"RFA".equals(str)) {
/* 444 */             Vector<EntityItem> vector5 = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 445 */             addDebug("checkAvails norfa " + entityItem.getKey() + " annVct " + vector5.size());
/* 446 */             if (vector5.size() != 0) {
/*     */               
/* 448 */               this.args[0] = getLD_NDN(entityItem);
/* 449 */               for (byte b3 = 0; b3 < vector5.size(); b3++) {
/* 450 */                 this.args[1] = getLD_NDN(vector5.elementAt(b3));
/* 451 */                 createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*     */               } 
/* 453 */               vector5.clear();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 460 */         vector2.clear();
/* 461 */         vector3.clear();
/* 462 */         vector4.clear();
/* 463 */         arrayList.clear();
/*     */       } 
/*     */       
/* 466 */       vector1.clear();
/*     */ 
/*     */ 
/*     */       
/* 470 */       if (str2 != null) {
/* 471 */         Vector vector2 = new Vector(vector);
/*     */ 
/*     */         
/* 474 */         removeNonRFAAVAIL(vector2);
/*     */         
/* 476 */         Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(vector2, "AVAILANNA", "ANNOUNCEMENT");
/* 477 */         addDebug("checkAvails annVct " + vector3.size());
/* 478 */         vector3 = PokUtils.getEntitiesWithMatchedAttr(vector3, "ANNTYPE", "14");
/* 479 */         addDebug("checkAvails EOL annVct " + vector3.size());
/* 480 */         for (byte b2 = 0; b2 < vector3.size(); b2++) {
/* 481 */           EntityItem entityItem = vector3.elementAt(b2);
/*     */           
/* 483 */           checkCanNotBeLater(entityItem, "ANNDATE", paramEntityItem, "WITHDRAWANNDATE_T", paramInt);
/*     */         } 
/* 485 */         vector3.clear();
/* 486 */         vector2.clear();
/*     */       } 
/* 488 */       vector.clear();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 493 */     addHeading(3, entityGroup1.getLongDescription() + " End of Marketing Avail Checks:");
/*     */     
/* 495 */     for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 496 */       EntityItem entityItem = entityGroup2.getEntityItem(b);
/* 497 */       Vector vector2 = PokUtils.getAllLinkedEntities(entityItem, "SWPRODSTRUCTAVAIL", "AVAIL");
/*     */       
/* 499 */       Vector<EntityItem> vector3 = PokUtils.getEntitiesWithMatchedAttr(vector2, "AVAILTYPE", "200");
/*     */       
/* 501 */       addDebug("checkAvails " + entityItem.getKey() + " all avail: " + vector2.size() + " psEOMAvailVct: " + vector3
/* 502 */           .size());
/*     */       
/* 504 */       paramInt = getCheck_W_W_E(paramString);
/* 505 */       for (byte b1 = 0; b1 < vector3.size(); b1++) {
/* 506 */         EntityItem entityItem1 = vector3.elementAt(b1);
/*     */         
/* 508 */         checkCanNotBeLater(entityItem, entityItem1, "EFFECTIVEDATE", paramEntityItem, "WITHDRAWANNDATE_T", paramInt);
/*     */       } 
/*     */ 
/*     */       
/* 512 */       vector2.clear();
/* 513 */       vector3.clear();
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
/* 524 */     return "SWFEATURE ABR.";
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
/*     */   public String getABRVersion() {
/* 536 */     return "1.20";
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
/*     */   private void setBHInvnameSW(EntityItem paramEntityItem) throws Exception {
/* 570 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/* 571 */     boolean bool = true;
/*     */     
/* 573 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", ", ", "", false);
/* 574 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "BHINVNAME", ", ", null, false);
/* 575 */     String str3 = PokUtils.getAttributeValue(paramEntityItem, "INVNAME", ", ", null, false);
/*     */     
/* 577 */     int i = 254;
/*     */     
/* 579 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("BHINVNAME");
/* 580 */     if (eANMetaAttribute != null) {
/* 581 */       i = eANMetaAttribute.getMaxLen();
/*     */     }
/*     */     
/* 584 */     addDebug("setBHInvnameSW checking " + paramEntityItem.getKey() + " fcode: " + str1 + " bhinvname: " + str2 + " invname: " + str3 + " maxLen: " + i + " swpsGrp.count: " + entityGroup
/*     */         
/* 586 */         .getEntityItemCount());
/*     */     
/* 588 */     if (str3 == null) {
/*     */       
/* 590 */       this.args[0] = "";
/* 591 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "INVNAME", "INVNAME");
/* 592 */       createMessage(4, "REQ_NOTPOPULATED_ERR", this.args);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 602 */     if (str2 != null) {
/*     */       
/* 604 */       String str4 = getTimestamp(paramEntityItem, "INVNAME");
/*     */       
/* 606 */       String str5 = getTimestamp(paramEntityItem, "BHINVNAME");
/* 607 */       addDebug("setBHInvnameSW invnameDts: " + str4 + " bhinvnameDts: " + str5);
/* 608 */       bool = (str5.compareTo(str4) < 0) ? true : false;
/*     */     } 
/*     */     
/* 611 */     if (bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 617 */       if (entityGroup.getEntityItemCount() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 623 */         if (uniqueSWPS(paramEntityItem, str3)) {
/* 624 */           str2 = str3;
/*     */         } else {
/* 626 */           str2 = str1 + "-" + str3;
/*     */         } 
/*     */       } else {
/*     */         
/* 630 */         str2 = str3;
/*     */       } 
/*     */       
/* 633 */       addDebug("setBHInvnameSW derived bhinvname: " + str2);
/*     */ 
/*     */       
/* 636 */       setTextValue(this.m_elist.getProfile(), "BHINVNAME", str2, paramEntityItem);
/*     */     } 
/*     */ 
/*     */     
/* 640 */     if (str2.length() > i) {
/*     */ 
/*     */ 
/*     */       
/* 644 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "BHINVNAME", "BHINVNAME");
/* 645 */       this.args[1] = "" + i;
/* 646 */       this.args[2] = str2;
/* 647 */       createMessage(3, "DERIVED_LEN_ERR", this.args);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeAttrBeforeCommit(ReturnEntityKey paramReturnEntityKey) {
/* 655 */     Attribute attribute = null;
/* 656 */     for (byte b = 0; b < paramReturnEntityKey.m_vctAttributes.size(); b++) {
/* 657 */       Attribute attribute1 = paramReturnEntityKey.m_vctAttributes.elementAt(b);
/* 658 */       if (attribute1.getAttributeCode().equals("BHINVNAME")) {
/* 659 */         attribute = attribute1;
/*     */         break;
/*     */       } 
/*     */     } 
/* 663 */     paramReturnEntityKey.m_vctAttributes.clear();
/*     */     
/* 665 */     if (attribute != null) {
/* 666 */       paramReturnEntityKey.m_vctAttributes.addElement(attribute);
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
/*     */   private boolean uniqueSWPS(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 686 */     boolean bool = true;
/*     */ 
/*     */     
/* 689 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/* 690 */     EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/* 691 */     addDebug("uniqueSWPS: the nubmer of model - total :" + arrayOfEntityItem.length);
/* 692 */     Vector<EntityItem> vector = new Vector();
/* 693 */     for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 694 */       vector.add(arrayOfEntityItem[b]);
/* 695 */       if ((b + 1) % MW_VENTITY_LIMIT == 0) {
/* 696 */         bool = isUniqueSWPS(paramEntityItem, paramString, vector);
/* 697 */         vector.clear();
/* 698 */         if (!bool)
/*     */           break; 
/*     */       } 
/*     */     } 
/* 702 */     if (bool && vector.size() > 0) {
/* 703 */       bool = isUniqueSWPS(paramEntityItem, paramString, vector);
/* 704 */       vector.clear();
/*     */     } 
/* 706 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isUniqueSWPS(EntityItem paramEntityItem, String paramString, Vector paramVector) throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 712 */     String str = "DQVESWPRODSTRUCT2";
/* 713 */     boolean bool = true;
/* 714 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramVector.size()];
/* 715 */     paramVector.copyInto((Object[])arrayOfEntityItem);
/* 716 */     addDebug("isUniqueSWPS: the nubmer of model :" + paramVector.size());
/* 717 */     EntityList entityList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 718 */           .getProfile(), str), arrayOfEntityItem);
/*     */     
/* 720 */     addDebug("isUniqueSWPS VE:" + str + "\n" + PokUtils.outputList(entityList));
/*     */     
/* 722 */     EntityGroup entityGroup = entityList.getEntityGroup("SWFEATURE");
/* 723 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 724 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 725 */       String str1 = PokUtils.getAttributeValue(entityItem, "INVNAME", ", ", null, false);
/* 726 */       addDebug("isUniqueSWPS checking " + entityItem.getKey() + " swfcInvname " + str1);
/* 727 */       if (!entityItem.getKey().equals(paramEntityItem.getKey()))
/*     */       {
/*     */         
/* 730 */         if (paramString.equals(str1)) {
/* 731 */           bool = false;
/*     */           break;
/*     */         }  } 
/*     */     } 
/* 735 */     entityList.dereference();
/* 736 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\SWFCABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */