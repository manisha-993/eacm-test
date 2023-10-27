/*     */ package COM.ibm.eannounce.abr.sg.bh;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
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
/*     */ public class SWPRODSTRUCTABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/* 110 */   private EntityList mdlList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/* 116 */     return true;
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 178 */     EntityItem entityItem1 = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */     
/* 180 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*     */     
/* 182 */     boolean bool = isOldData(entityItem2, "ANNDATE");
/*     */     
/* 184 */     addDebug("nowRFR: " + entityItem2.getKey() + " olddata " + bool);
/*     */     
/* 186 */     if (doR10processing())
/*     */     {
/*     */ 
/*     */       
/* 190 */       if (!bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 198 */         doR4R_R10Processing(entityItem1, "SWPRODSTRUCTAVAIL");
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 205 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
/*     */       } 
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
/* 223 */     String str = PokUtils.getAttributeFlagValue(entityItem1, "LIFECYCLE");
/* 224 */     addDebug("completeNowR4RProcessing LIFECYCLE: " + str);
/*     */ 
/*     */ 
/*     */     
/* 228 */     if ("LF01".equals(str)) {
/* 229 */       String str1 = PokUtils.getAttributeValue(entityItem1, "SYSTEMMAX", ", ", "", false);
/* 230 */       EntityItem entityItem = this.m_elist.getEntityGroup("SWFEATURE").getEntityItem(0);
/* 231 */       String str2 = PokUtils.getAttributeValue(entityItem, "FEATURECODE", ", ", "", false);
/* 232 */       String str3 = PokUtils.getAttributeFlagValue(entityItem, "SWFCCAT");
/* 233 */       addDebug("completeNowR4RProcessing first time moving to RFR FEATURECODE: " + str2 + " SWFCCAT: " + str3 + " SYSTEMMAX: " + str1);
/*     */       
/* 235 */       if (str2.length() == 4 && "319".equals(str3)) {
/* 236 */         setFlagValue(this.m_elist.getProfile(), "SYSTEMMAX", "250");
/* 237 */       } else if (str2.length() == 6 && "319".equals(str3)) {
/* 238 */         setFlagValue(this.m_elist.getProfile(), "SYSTEMMAX", "999");
/*     */       } else {
/* 240 */         setFlagValue(this.m_elist.getProfile(), "SYSTEMMAX", "0");
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
/*     */   protected String getLCRFRWFName() {
/* 253 */     return "WFLCSWPRODSTRFR"; } protected String getLCFinalWFName() {
/* 254 */     return "WFLCSWPRODSTFINAL";
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
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 289 */     EntityItem entityItem = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*     */     
/* 291 */     boolean bool = isOldData(entityItem, "ANNDATE");
/*     */     
/* 293 */     addDebug("nowFinal: " + entityItem.getKey() + " olddata " + bool);
/*     */     
/* 295 */     if (doR10processing()) {
/*     */ 
/*     */ 
/*     */       
/* 299 */       if (!bool) {
/*     */ 
/*     */         
/* 302 */         EntityItem entityItem1 = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */         
/* 305 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem1, "SWPRODSTRUCTAVAIL", "AVAIL");
/*     */         
/* 307 */         for (byte b = 0; b < vector.size(); b++) {
/* 308 */           EntityItem entityItem2 = vector.elementAt(b);
/*     */           
/* 310 */           if (statusIsFinal(entityItem2)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 320 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 340 */         vector.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 351 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 359 */       if (isHIPOModel(entityItem)) {
/* 360 */         setFlagValue(this.m_elist.getProfile(), "RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
/*     */       }
/* 362 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"), entityItem);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 435 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*     */     
/* 437 */     int i = getCheck_W_E_E(paramString);
/* 438 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/* 439 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("SWFEATURE").getEntityItem(0);
/*     */ 
/*     */     
/* 442 */     getModelVE(entityItem1);
/*     */ 
/*     */     
/* 445 */     checkStatusVsDQ(entityItem2, "STATUS", paramEntityItem, 4);
/*     */ 
/*     */     
/* 448 */     checkStatusVsDQ(entityItem1, "STATUS", paramEntityItem, 4);
/*     */ 
/*     */     
/* 451 */     if (!isRPQ(entityItem2)) {
/* 452 */       addDebug(entityItem2.getKey() + " was NOT an RPQ FCTYPE: " + getAttributeFlagEnabledValue(entityItem2, "FCTYPE"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 457 */       checkAllFeatures(entityItem2, entityItem1, i);
/*     */ 
/*     */       
/* 460 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 461 */       Vector vector1 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "149");
/* 462 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "146");
/* 463 */       addDebug("doDQChecking lastOrderAvailVct " + vector1.size() + " plannedAvailVct " + vector2
/* 464 */           .size());
/*     */       
/* 466 */       checkAvails(paramEntityItem, entityItem2, entityItem1, paramString, vector1, vector2);
/*     */ 
/*     */       
/* 469 */       addHeading(3, entityGroup.getLongDescription() + " Model Planned Avail Checks:");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 477 */       checkPsModelAvail(this.mdlList, entityItem1, paramString, vector2, "SWPRODSTRUCTAVAIL");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 486 */       addHeading(3, entityGroup.getLongDescription() + " Model Last Order Avail Checks:");
/* 487 */       checkPsModelLastOrderAvail(this.mdlList, entityItem1, paramString, vector1, vector2);
/*     */       
/* 489 */       i = getCheck_W_E_E(paramString);
/* 490 */       String str1 = getAttrValueAndCheckLvl(entityItem1, "ANNDATE", i);
/* 491 */       String str2 = getAttrValueAndCheckLvl(entityItem1, "WTHDRWEFFCTVDATE", i);
/*     */       
/* 493 */       addHeading(3, entityGroup.getLongDescription() + " Model Checks:");
/*     */ 
/*     */       
/* 496 */       addDebug("doDQChecking check plannedavail and " + entityItem1.getKey() + " ANNDATE " + str1);
/* 497 */       checkModelDates(entityItem1, vector2, "ANNDATE", str1, i, 2);
/*     */ 
/*     */       
/* 500 */       addDebug("doDQChecking check lastorderavail and " + entityItem1.getKey() + " WTHDRWEFFCTVDATE " + str2);
/* 501 */       checkModelDates(entityItem1, vector1, "WTHDRWEFFCTVDATE", str2, i, 1);
/*     */       
/* 503 */       vector2.clear();
/* 504 */       vector1.clear();
/*     */     }
/*     */     else {
/*     */       
/* 508 */       addDebug(entityItem2.getKey() + " was an RPQ FCTYPE: " + getAttributeFlagEnabledValue(entityItem2, "FCTYPE"));
/* 509 */       this.args[0] = getLD_NDN(entityItem2);
/* 510 */       this.args[1] = getLD_Value(entityItem2, "FCTYPE");
/* 511 */       createMessage(4, "FCTYPE_ERR", this.args);
/*     */     } 
/*     */     
/* 514 */     if (this.mdlList != null) {
/* 515 */       this.mdlList.dereference();
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
/*     */   private void checkAvails(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, String paramString, Vector<EntityItem> paramVector1, Vector paramVector2) throws SQLException, MiddlewareException {
/* 556 */     String str1 = PokUtils.getAttributeValue(paramEntityItem2, "WITHDRAWDATEEFF_T", "", "", false);
/*     */ 
/*     */     
/* 559 */     String str2 = PokUtils.getAttributeValue(paramEntityItem3, "WTHDRWEFFCTVDATE", "", "", false);
/*     */     
/* 561 */     addDebug("checkAvails " + paramEntityItem2.getKey() + " WITHDRAWDATEEFF_T: " + str1 + " " + paramEntityItem3.getKey() + " WTHDRWEFFCTVDATE: " + str2);
/*     */ 
/*     */     
/* 564 */     ArrayList<?> arrayList = getCountriesAsList(paramVector2, getCheck_W_W_E(paramString));
/* 565 */     ArrayList arrayList1 = getCountriesAsList(paramVector1, getCheck_W_W_E(paramString));
/*     */     
/* 567 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " Planned Avail Checks:");
/*     */     
/* 569 */     addDebug("checkAvails all plannedavail countries " + arrayList + " lastOrderAvailCtry " + arrayList1);
/*     */ 
/*     */ 
/*     */     
/* 573 */     checkPlannedAvailsExist(paramVector2, getCheckLevel(3, paramEntityItem3, "ANNDATE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 579 */     checkPlannedAvailsStatus(paramVector2, paramEntityItem1, getCheckLevel(3, paramEntityItem3, "ANNDATE"));
/*     */ 
/*     */     
/* 582 */     int i = getCheck_W_E_E(paramString);
/*     */     
/* 584 */     addHeading(3, this.m_elist.getEntityGroup("AVAIL").getLongDescription() + " Last Order Avail Checks:");
/*     */ 
/*     */     
/* 587 */     for (byte b = 0; b < paramVector1.size(); b++) {
/* 588 */       EntityItem entityItem = paramVector1.elementAt(b);
/* 589 */       if (str1.length() > 0)
/*     */       {
/* 591 */         checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem2, "WITHDRAWDATEEFF_T", i);
/*     */       }
/*     */       
/* 594 */       if (str2.length() > 0)
/*     */       {
/* 596 */         checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem3, "WTHDRWEFFCTVDATE", i);
/*     */       }
/*     */       
/* 599 */       checkPlannedAvailForCtryExists(entityItem, arrayList, getCheckLevel(i, paramEntityItem3, "ANNDATE"));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 606 */     if (paramVector1.size() > 0 && (str1.length() > 0 || str2.length() > 0) && 
/* 607 */       !arrayList1.containsAll(arrayList)) {
/*     */ 
/*     */       
/* 610 */       this.args[0] = paramEntityItem2.getEntityGroup().getLongDescription();
/* 611 */       this.args[1] = getLD_Value(paramEntityItem2, "WITHDRAWDATEEFF_T");
/* 612 */       this.args[2] = paramEntityItem3.getEntityGroup().getLongDescription();
/* 613 */       this.args[3] = getLD_Value(paramEntityItem3, "WTHDRWEFFCTVDATE");
/* 614 */       createMessage(getCheckLevel(i, paramEntityItem3, "ANNDATE"), "LASTORDER_ERR", this.args);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 619 */     if (paramVector1.size() > 0) {
/* 620 */       Vector<EntityItem> vector1 = new Vector<>(paramVector1);
/*     */ 
/*     */       
/* 623 */       removeNonRFAAVAIL(vector1);
/*     */       
/* 625 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(vector1, "AVAILANNA", "ANNOUNCEMENT");
/* 626 */       addDebug("checkAvails annVct " + vector2.size());
/*     */       
/* 628 */       vector2 = PokUtils.getEntitiesWithMatchedAttr(vector2, "ANNTYPE", "14");
/* 629 */       addDebug("checkAvails EOL annVct " + vector2.size());
/* 630 */       for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 631 */         EntityItem entityItem = vector2.elementAt(b1);
/*     */         
/* 633 */         checkCanNotBeLater(entityItem, "ANNDATE", paramEntityItem2, "WITHDRAWANNDATE_T", getCheck_W_E_E(paramString));
/*     */       } 
/* 635 */       vector2.clear();
/* 636 */       vector1.clear();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 642 */     arrayList.clear();
/* 643 */     arrayList1.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getModelVE(EntityItem paramEntityItem) throws Exception {
/* 651 */     String str = "DQVEMODELAVAIL";
/*     */     
/* 653 */     this.mdlList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 654 */           .getProfile(), str), new EntityItem[] { new EntityItem(null, this.m_elist
/* 655 */             .getProfile(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID()) });
/* 656 */     addDebug("getModelVE:: Extract " + str + NEWLINE + PokUtils.outputList(this.mdlList));
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
/*     */   private void checkAllFeatures(EntityItem paramEntityItem1, EntityItem paramEntityItem2, int paramInt) throws Exception {
/* 674 */     Profile profile = this.m_elist.getProfile();
/* 675 */     String str1 = "DQVESWPRODSTRUCT2";
/*     */ 
/*     */     
/* 678 */     EntityList entityList = this.m_db.getEntityList(profile, new ExtractActionItem(null, this.m_db, profile, str1), new EntityItem[] { new EntityItem(null, profile, "MODEL", paramEntityItem2
/*     */             
/* 680 */             .getEntityID()) });
/* 681 */     addDebug("checkAllFeatures: Extract " + str1 + NEWLINE + PokUtils.outputList(entityList));
/*     */     
/* 683 */     EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/* 684 */     String str2 = PokUtils.getAttributeValue(paramEntityItem1, "BHINVNAME", ", ", "NULL", false);
/* 685 */     String str3 = PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", ", ", "", false);
/* 686 */     addDebug("checkAllFeatures: " + paramEntityItem1.getKey() + " origbhinvname " + str2 + " origfcode " + str3);
/*     */     
/* 688 */     Vector<String> vector1 = new Vector();
/* 689 */     Vector<String> vector2 = new Vector();
/*     */     
/* 691 */     for (byte b = 0; b < entityItem.getUpLinkCount(); b++) {
/* 692 */       EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b);
/* 693 */       if (!entityItem1.getEntityType().equals("SWPRODSTRUCT")) {
/* 694 */         addDebug("checkAllFeatures skipping uplink " + entityItem1.getKey());
/*     */       } else {
/*     */         
/* 697 */         for (byte b1 = 0; b1 < entityItem1.getUpLinkCount(); b1++) {
/* 698 */           EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/*     */ 
/*     */           
/* 701 */           String str4 = PokUtils.getAttributeValue(entityItem2, "BHINVNAME", ", ", "NULL", false);
/* 702 */           String str5 = PokUtils.getAttributeValue(entityItem2, "FEATURECODE", ", ", "", false);
/* 703 */           addDebug("checkAllFeatures checking " + entityItem1.getKey() + " - " + entityItem2.getKey() + " fcode: " + str5 + " bhinvname: " + str4);
/*     */           
/* 705 */           if (vector1.contains(str5)) {
/* 706 */             if (str3.equals(str5)) {
/*     */ 
/*     */ 
/*     */               
/* 710 */               String str = "";
/* 711 */               if (entityItem1.getEntityID() != getEntityID()) {
/* 712 */                 str = getLD_NDN(entityItem1) + " ";
/*     */               }
/*     */               
/* 715 */               this.args[0] = str + PokUtils.getAttributeDescription(entityItem2.getEntityGroup(), "FEATURECODE", "FEATURECODE");
/* 716 */               this.args[1] = entityItem.getEntityGroup().getLongDescription();
/* 717 */               createMessage(paramInt, "NOT_UNIQUE_ERR", this.args);
/*     */             } else {
/* 719 */               addDebug("checkAllFeatures fcode " + str5 + " is not unique but not with this swfeature");
/*     */             } 
/*     */           } else {
/* 722 */             vector1.add(str5);
/*     */           } 
/*     */ 
/*     */           
/* 726 */           if (vector2.contains(str4)) {
/* 727 */             if (str2.equals(str4)) {
/*     */               
/* 729 */               String str = "";
/* 730 */               if (entityItem1.getEntityID() != getEntityID()) {
/* 731 */                 str = getLD_NDN(entityItem1) + " ";
/*     */               }
/*     */               
/* 734 */               this.args[0] = str + PokUtils.getAttributeDescription(entityItem2.getEntityGroup(), "BHINVNAME", "BHINVNAME");
/* 735 */               this.args[1] = entityItem.getEntityGroup().getLongDescription();
/* 736 */               createMessage(4, "NOT_UNIQUE_ERR", this.args);
/*     */             } else {
/* 738 */               addDebug("checkAllFeatures bhinvname " + str4 + " is not unique but not with this swfeature");
/*     */             } 
/*     */           } else {
/* 741 */             vector2.add(str4);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 746 */     vector1.clear();
/* 747 */     vector2.clear();
/*     */     
/* 749 */     entityList.dereference();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 759 */     return "SWPRODSTRUCT ABR";
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
/* 770 */     return "1.14";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\SWPRODSTRUCTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */