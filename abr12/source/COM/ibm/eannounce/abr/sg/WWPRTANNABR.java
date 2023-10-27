/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.SAPLElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLEnterpriseElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLIdElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLNotificationElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANEntity;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WWPRTANNABR
/*     */   extends EPIMSABRBase
/*     */ {
/*     */   private static final Vector FIRSTFINAL_XMLMAP_VCT;
/*     */   private static final Vector CHGFINAL_XMLMAP_VCT;
/*     */   private static final Hashtable ATTR_OF_INTEREST_TBL;
/*     */   private static final String PROPERTIES_FNAME = "WWPRTANNABR_AOI.properties";
/*  70 */   private static final String[] INDEPENDENT_OF_PRICEDFC = new String[] { "MODELAVAIL", "LSEOAVAIL", "LSEOBUNDLEAVAIL", "LSEOBUNDLELSEO", "LSEO", "LSEOBUNDLE", "AVAIL" };
/*     */   
/*  72 */   private static final String[] PRICED_FCREL = new String[] { "OOFAVAIL", "LSEOPRODSTRUCT", "WWSEOPRODSTRUCT" };
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  77 */     ATTR_OF_INTEREST_TBL = new Hashtable<>();
/*  78 */     loadAttrOfInterest("WWPRTANNABR_AOI.properties", ATTR_OF_INTEREST_TBL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     FIRSTFINAL_XMLMAP_VCT = new Vector();
/*  90 */     SAPLElem sAPLElem = new SAPLElem("WWPRTNotification");
/*  91 */     FIRSTFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/*  93 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("EntityType", "ANNOUNCEMENT"));
/*  94 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityId"));
/*  95 */     sAPLElem.addChild(new SAPLElem("Id", "ANNOUNCEMENT", "ANNNUMBER", true));
/*  96 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("Change", "no"));
/*  97 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("NotificationTime"));
/*  98 */     sAPLElem.addChild((SAPLElem)new SAPLEnterpriseElem("Enterprise"));
/*     */     
/* 100 */     CHGFINAL_XMLMAP_VCT = new Vector();
/* 101 */     sAPLElem = new SAPLElem("WWPRTNotification");
/*     */     
/* 103 */     CHGFINAL_XMLMAP_VCT.addElement(sAPLElem);
/*     */     
/* 105 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("EntityType", "ANNOUNCEMENT"));
/* 106 */     sAPLElem.addChild((SAPLElem)new SAPLIdElem("EntityId"));
/* 107 */     sAPLElem.addChild(new SAPLElem("Id", "ANNOUNCEMENT", "ANNNUMBER", true));
/* 108 */     sAPLElem.addChild((SAPLElem)new SAPLFixedElem("Change", "yes"));
/* 109 */     sAPLElem.addChild((SAPLElem)new SAPLNotificationElem("NotificationTime"));
/* 110 */     sAPLElem.addChild((SAPLElem)new SAPLEnterpriseElem("Enterprise"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getMQPropertiesFN() {
/* 117 */     Vector<String> vector = new Vector(1);
/* 118 */     vector.add("WWPRTMQSERIES");
/* 119 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getXMLMap(boolean paramBoolean) {
/* 127 */     if (paramBoolean) {
/* 128 */       return FIRSTFINAL_XMLMAP_VCT;
/*     */     }
/* 130 */     return CHGFINAL_XMLMAP_VCT;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute() throws Exception {
/* 302 */     EntityItem entityItem = this.epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
/*     */     
/* 304 */     String str1 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "ANNSTATUS");
/* 305 */     String str2 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*     */     
/* 307 */     addDebug("execute: " + entityItem.getKey() + " ANNSTATUS: " + 
/* 308 */         PokUtils.getAttributeValue(entityItem, "ANNSTATUS", ", ", "", false) + " [" + str1 + "] sysfeedFlag: " + str2);
/*     */ 
/*     */     
/* 311 */     if ("Yes".equals(str2)) {
/* 312 */       resendSystemFeed(entityItem, str1);
/*     */       
/*     */       return;
/*     */     } 
/* 316 */     if (!"0020".equals(str1)) {
/*     */       
/* 318 */       addError("ERROR_NOT_FINAL", null);
/*     */       
/*     */       return;
/*     */     } 
/* 322 */     if (this.epimsAbr.isFirstFinal()) {
/* 323 */       addDebug("Only one transition to Final found, must be first.");
/*     */       
/* 325 */       if (checkForAvails()) {
/*     */         
/* 327 */         notifyAndSetStatus(null);
/*     */       } else {
/* 329 */         addDebug("No AVAIL with AVAILTYPE=planned avail found");
/*     */       } 
/*     */     } else {
/* 332 */       addDebug("More than one transition to Final found, check for change of interest.");
/*     */       
/* 334 */       if (changeOfInterest()) {
/*     */ 
/*     */         
/* 337 */         notifyAndSetStatus(null);
/*     */       } else {
/* 339 */         addDebug("No change of interest found");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleResend(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, ParserConfigurationException, TransformerException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException {
/* 373 */     String str = this.epimsAbr.getAttributeFlagEnabledValue(paramEntityItem, "ANNTYPE");
/* 374 */     addDebug("annType: " + str);
/* 375 */     if (!"19".equals(str)) {
/*     */ 
/*     */       
/* 378 */       addError("ANN_NOT_NEW", null);
/*     */       
/*     */       return;
/*     */     } 
/* 382 */     if (!"0020".equals(paramString)) {
/*     */       
/* 384 */       addError("RESEND_NOT_FINAL", null);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 390 */     if (!checkForAvails()) {
/* 391 */       addError("ANN_NO_AVAIL", null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 396 */     notifyAndSetStatus(null);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkForAvails() throws SQLException, MiddlewareException {
/* 401 */     String str1 = this.epimsAbr.getLastFinalDTS();
/*     */     
/* 403 */     boolean bool = false;
/*     */     
/* 405 */     Profile profile = this.epimsAbr.getProfile().getNewInstance(this.epimsAbr.getDB());
/* 406 */     profile.setValOnEffOn(str1, str1);
/*     */     
/* 408 */     String str2 = getVeName();
/*     */     
/* 410 */     EntityList entityList = this.epimsAbr.getDB().getEntityList(profile, new ExtractActionItem(null, this.epimsAbr
/* 411 */           .getDB(), profile, str2), new EntityItem[] { new EntityItem(null, profile, this.epimsAbr
/* 412 */             .getEntityType(), this.epimsAbr.getEntityID()) });
/*     */ 
/*     */     
/* 415 */     addDebug("checkForAvails dts: " + str1 + " extract: " + str2 + NEWLINE + 
/* 416 */         PokUtils.outputList(entityList));
/*     */ 
/*     */     
/* 419 */     EntityGroup entityGroup = entityList.getEntityGroup("AVAIL");
/* 420 */     bool = (entityGroup.getEntityItemCount() > 0) ? true : false;
/*     */     
/* 422 */     entityList.dereference();
/*     */     
/* 424 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean changeOfInterest() throws SQLException, MiddlewareException {
/* 435 */     boolean bool = false;
/* 436 */     String str1 = this.epimsAbr.getLastFinalDTS();
/* 437 */     String str2 = this.epimsAbr.getPriorFinalDTS();
/* 438 */     boolean bool1 = false;
/*     */     
/* 440 */     if (str1.equals(str2)) {
/* 441 */       addDebug("changeOfInterest Only one transition to Final found, no changes can exist.");
/* 442 */       return bool;
/*     */     } 
/*     */ 
/*     */     
/* 446 */     Profile profile1 = this.epimsAbr.getProfile().getNewInstance(this.epimsAbr.getDB());
/* 447 */     profile1.setValOnEffOn(str1, str1);
/*     */ 
/*     */ 
/*     */     
/* 451 */     String str3 = getVeName();
/*     */     
/* 453 */     EntityList entityList1 = this.epimsAbr.getDB().getEntityList(profile1, new ExtractActionItem(null, this.epimsAbr
/* 454 */           .getDB(), profile1, str3), new EntityItem[] { new EntityItem(null, profile1, this.epimsAbr
/* 455 */             .getEntityType(), this.epimsAbr.getEntityID()) });
/*     */ 
/*     */     
/* 458 */     addDebug("changeOfInterest dts: " + str1 + " extract: " + str3 + NEWLINE + 
/* 459 */         PokUtils.outputList(entityList1));
/*     */ 
/*     */     
/* 462 */     Profile profile2 = this.epimsAbr.getProfile().getNewInstance(this.epimsAbr.getDB());
/* 463 */     profile2.setValOnEffOn(str2, str2);
/*     */ 
/*     */     
/* 466 */     EntityList entityList2 = this.epimsAbr.getDB().getEntityList(profile2, new ExtractActionItem(null, this.epimsAbr
/* 467 */           .getDB(), profile2, str3), new EntityItem[] { new EntityItem(null, profile2, this.epimsAbr
/* 468 */             .getEntityType(), this.epimsAbr.getEntityID()) });
/*     */ 
/*     */     
/* 471 */     addDebug("changeOfInterest dts: " + str2 + " extract: " + str3 + NEWLINE + 
/* 472 */         PokUtils.outputList(entityList2));
/*     */ 
/*     */     
/* 475 */     EntityGroup entityGroup = entityList1.getEntityGroup("AVAIL");
/* 476 */     if (entityGroup.getEntityItemCount() > 0) {
/* 477 */       bool1 = true;
/*     */     } else {
/* 479 */       addDebug("No AVAIL with AVAILTYPE=planned avail found in last final");
/* 480 */       entityGroup = entityList2.getEntityGroup("AVAIL");
/* 481 */       if (entityGroup.getEntityItemCount() > 0) {
/* 482 */         bool1 = true;
/*     */       } else {
/* 484 */         addDebug("No AVAIL with AVAILTYPE=planned avail found in prior final");
/*     */       } 
/*     */     } 
/*     */     
/* 488 */     if (bool1) {
/*     */       
/* 490 */       Hashtable hashtable1 = getStringRep(entityList1, getAttrOfInterest());
/* 491 */       Hashtable hashtable2 = getStringRep(entityList2, getAttrOfInterest());
/*     */       
/* 493 */       bool = changeOfInterest(hashtable1, hashtable2);
/* 494 */       hashtable1.clear();
/* 495 */       hashtable2.clear();
/*     */     } 
/*     */     
/* 498 */     entityList2.dereference();
/* 499 */     entityList1.dereference();
/*     */     
/* 501 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Hashtable getAttrOfInterest() {
/* 507 */     return ATTR_OF_INTEREST_TBL;
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
/*     */   protected Hashtable getStringRep(EntityList paramEntityList, Hashtable paramHashtable) {
/* 565 */     addDebug(NEWLINE + "getStringRep: entered for " + paramEntityList.getProfile().getValOn());
/* 566 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 567 */     EntityGroup entityGroup = null;
/* 568 */     String[] arrayOfString = null;
/*     */ 
/*     */     
/* 571 */     for (byte b1 = 0; b1 < INDEPENDENT_OF_PRICEDFC.length; b1++) {
/* 572 */       entityGroup = paramEntityList.getEntityGroup(INDEPENDENT_OF_PRICEDFC[b1]);
/* 573 */       arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType());
/* 574 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*     */ 
/*     */         
/* 577 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 578 */         String str = this.epimsAbr.generateString(entityItem, arrayOfString);
/* 579 */         addDebug("getStringRep: put " + entityItem.getKey() + " " + str);
/* 580 */         hashtable.put(entityItem.getKey(), str);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 585 */     arrayOfString = (String[])paramHashtable.get("MODEL");
/* 586 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityList.getEntityGroup("AVAIL"), "MODELAVAIL", "MODEL");
/* 587 */     addDebug("getStringRep: Model thru modelavail mdlVct " + vector.size()); byte b2;
/* 588 */     for (b2 = 0; b2 < vector.size(); b2++) {
/* 589 */       EntityItem entityItem = vector.elementAt(b2);
/* 590 */       String str = this.epimsAbr.generateString(entityItem, arrayOfString);
/* 591 */       addDebug("getStringRep: put " + entityItem.getKey() + " " + str);
/* 592 */       hashtable.put(entityItem.getKey(), str);
/*     */     } 
/*     */ 
/*     */     
/* 596 */     entityGroup = paramEntityList.getEntityGroup("FEATURE");
/* 597 */     for (b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/*     */       
/* 599 */       EntityItem entityItem = entityGroup.getEntityItem(b2);
/* 600 */       String str1 = null;
/* 601 */       String str2 = this.epimsAbr.getAttributeFlagEnabledValue(entityItem, "PRICEDFEATURE");
/* 602 */       addDebug(entityItem.getKey() + " PRICEDFEATURE: " + str2);
/* 603 */       if ("100".equals(str2)) {
/* 604 */         for (byte b = 0; b < PRICED_FCREL.length; b++) {
/*     */           
/* 606 */           Vector<EntityItem> vector1 = getAllLinkedEntities(entityItem, PRICED_FCREL[b]);
/* 607 */           if (vector1.size() > 0) {
/*     */             
/* 609 */             if (!hashtable.containsKey(entityItem.getKey())) {
/* 610 */               arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType());
/* 611 */               str1 = this.epimsAbr.generateString(entityItem, arrayOfString);
/* 612 */               addDebug("getStringRep: put " + entityItem.getKey() + " " + str1);
/* 613 */               hashtable.put(entityItem.getKey(), str1);
/*     */             } 
/*     */           } else {
/*     */             
/* 617 */             addDebug("getStringRep: " + entityItem.getKey() + " was not found thru " + PRICED_FCREL[b]);
/*     */           } 
/* 619 */           for (byte b3 = 0; b3 < vector1.size(); b3++) {
/* 620 */             EntityItem entityItem1 = vector1.elementAt(b3);
/* 621 */             arrayOfString = (String[])paramHashtable.get(entityItem1.getEntityType());
/* 622 */             str1 = this.epimsAbr.generateString(entityItem1, arrayOfString);
/* 623 */             addDebug("getStringRep: put " + entityItem1.getKey() + " " + str1);
/* 624 */             hashtable.put(entityItem1.getKey(), str1);
/*     */           } 
/* 626 */           vector1.clear();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 631 */     return hashtable;
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
/*     */   private Vector getAllLinkedEntities(EntityItem paramEntityItem, String paramString) {
/* 644 */     Vector<EANEntity> vector = new Vector(1);
/* 645 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*     */       
/* 647 */       EANEntity eANEntity = paramEntityItem.getDownLink(b);
/* 648 */       if (eANEntity.getEntityType().equals("PRODSTRUCT")) {
/*     */         byte b1;
/*     */         
/* 651 */         for (b1 = 0; b1 < eANEntity.getUpLinkCount(); b1++) {
/*     */           
/* 653 */           EANEntity eANEntity1 = eANEntity.getUpLink(b1);
/* 654 */           if (eANEntity1.getEntityType().equals(paramString) && !vector.contains(eANEntity1)) {
/* 655 */             vector.addElement(eANEntity1);
/*     */           }
/*     */         } 
/* 658 */         for (b1 = 0; b1 < eANEntity.getDownLinkCount(); b1++) {
/*     */           
/* 660 */           EANEntity eANEntity1 = eANEntity.getDownLink(b1);
/* 661 */           if (eANEntity1.getEntityType().equals(paramString) && !vector.contains(eANEntity1)) {
/* 662 */             vector.addElement(eANEntity1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 667 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getVeName() {
/* 673 */     return "WWPRTANNVE1";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 682 */     return "1.5";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWPRTANNABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */