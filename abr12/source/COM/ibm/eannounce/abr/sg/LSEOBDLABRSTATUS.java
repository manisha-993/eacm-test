/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EANTextAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LSEOBDLABRSTATUS
/*     */   extends DQABRSTATUS
/*     */ {
/* 147 */   private Object[] args = (Object[])new String[5];
/*     */ 
/*     */   
/*     */   private EntityList mdlList;
/*     */ 
/*     */   
/*     */   private static final String HARDWARE = "100";
/*     */   
/*     */   private static final String SOFTWARE = "101";
/*     */   
/* 157 */   private static final Set TESTSET = new HashSet(); static {
/* 158 */     TESTSET.add("100");
/* 159 */     TESTSET.add("101");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isVEneeded(String paramString) {
/* 165 */     return true;
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
/*     */   protected void doAlreadyFinalProcessing() {
/* 181 */     doFinalProcessing(this.m_elist.getParentEntityGroup().getEntityItem(0));
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
/*     */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 194 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", "0020");
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
/*     */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 218 */     checkAssortModule();
/* 219 */     setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", "0020");
/*     */     
/* 221 */     doFinalProcessing(this.m_elist.getParentEntityGroup().getEntityItem(0));
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
/*     */   private void doFinalProcessing(EntityItem paramEntityItem) {
/* 249 */     setFlagValue(this.m_elist.getProfile(), "EPIMSABRSTATUS", "0020");
/*     */     
/* 251 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "SPECBID");
/* 252 */     addDebug(paramEntityItem.getKey() + " SPECBID: " + str);
/* 253 */     if ("11457".equals(str)) {
/* 254 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("ANNOUNCEMENT"); byte b;
/* 255 */       for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 256 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 257 */         String str1 = getAttributeFlagEnabledValue(entityItem, "ANNSTATUS");
/* 258 */         String str2 = getAttributeFlagEnabledValue(entityItem, "ANNTYPE");
/* 259 */         addDebug(entityItem.getKey() + " status " + str1 + " type " + str2);
/* 260 */         if (str1 == null || str1.length() == 0) {
/* 261 */           str1 = "0020";
/*     */         }
/* 263 */         if ("0020".equals(str1) && "19".equals(str2)) {
/* 264 */           addDebug(entityItem.getKey() + " is Final and New");
/* 265 */           setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020", entityItem);
/*     */         } 
/*     */       } 
/*     */       
/* 269 */       entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 270 */       for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 271 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 272 */         String str1 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/* 273 */         addDebug(entityItem.getKey() + " type " + str1);
/*     */         
/* 275 */         if ("146".equals(str1) || "149".equals(str1)) {
/* 276 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 277 */           for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 278 */             EntityItem entityItem1 = vector.elementAt(b1);
/* 279 */             String str2 = getAttributeFlagEnabledValue(entityItem1, "ANNSTATUS");
/* 280 */             String str3 = getAttributeFlagEnabledValue(entityItem1, "ANNTYPE");
/* 281 */             addDebug(entityItem1.getKey() + " status " + str2 + " type " + str3);
/* 282 */             if (str2 == null || str2.length() == 0) {
/* 283 */               str2 = "0020";
/*     */             }
/*     */             
/* 286 */             if ("0020".equals(str2)) {
/* 287 */               if ("146".equals(str1) && "19".equals(str3)) {
/* 288 */                 addDebug(entityItem1.getKey() + " is Final and New");
/*     */ 
/*     */                 
/* 291 */                 setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), entityItem1);
/*     */               } 
/* 293 */               if ("149".equals(str1) && "14".equals(str3)) {
/* 294 */                 addDebug(entityItem1.getKey() + " is Final and EOL");
/*     */ 
/*     */                 
/* 297 */                 setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), entityItem1);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 305 */       setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020");
/*     */ 
/*     */       
/* 308 */       setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"));
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
/*     */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/* 324 */     int i = getCount("LSEOBUNDLELSEO");
/* 325 */     if (i < 2) {
/*     */       
/* 327 */       this.args[0] = this.m_elist.getEntityGroup("LSEO").getLongDescription();
/* 328 */       addError("MINIMUM_TWO_ERR", this.args);
/*     */     } 
/*     */ 
/*     */     
/* 332 */     checkLSEOStatus(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 342 */     i = getCount("SEOCGBDL");
/* 343 */     if (!PokUtils.contains(paramEntityItem, "BUNDLETYPE", TESTSET)) {
/* 344 */       if (i != 0)
/*     */       {
/* 346 */         this.args[0] = this.m_elist.getEntityGroup("SEOCG").getLongDescription();
/* 347 */         addError("SVC_SEOCG_ERR", this.args);
/*     */       }
/*     */     
/* 350 */     } else if (i == 0) {
/*     */       
/* 352 */       this.args[0] = this.m_elist.getEntityGroup("SEOCG").getLongDescription();
/* 353 */       addError("MUST_BE_IN_ATLEAST_ONE_ERR", this.args);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 359 */     checkCountry("LSEOBUNDLELSEO", "D", false);
/*     */ 
/*     */     
/* 362 */     getModelVE(paramEntityItem);
/*     */ 
/*     */     
/* 365 */     validateOS(paramEntityItem);
/*     */ 
/*     */     
/* 368 */     validateBUNDLETYPE(paramEntityItem);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "SPECBID");
/* 379 */     addDebug(paramEntityItem.getKey() + " SPECBID: " + str);
/* 380 */     if ("11457".equals(str)) {
/*     */ 
/*     */       
/* 383 */       Vector vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOBUNDLEAVAIL", "AVAIL");
/* 384 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/* 385 */       if (vector2.size() == 0) {
/* 386 */         this.args[0] = this.m_elist.getEntityGroup("AVAIL").getLongDescription();
/*     */         
/* 388 */         addError("NOT_SPECBID_AVAIL_ERR", this.args);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 393 */       checkCountry("LSEOBUNDLEAVAIL", "D", true);
/*     */     } else {
/* 395 */       i = getCount("LSEOBUNDLEAVAIL");
/*     */ 
/*     */ 
/*     */       
/* 399 */       if (i != 0) {
/* 400 */         this.args[0] = this.m_elist.getEntityGroup("AVAIL").getLongDescription();
/* 401 */         addError("SPECBID_AVAIL_ERR", this.args);
/*     */       } 
/*     */     } 
/*     */     
/* 405 */     if ("0040".equals(paramString)) {
/*     */ 
/*     */ 
/*     */       
/* 409 */       checkDates(paramEntityItem);
/*     */       
/* 411 */       if ("11457".equals(str)) {
/*     */ 
/*     */         
/* 414 */         EANTextAttribute eANTextAttribute = (EANTextAttribute)paramEntityItem.getAttribute("SAPASSORTMODULE");
/*     */         
/* 416 */         if (eANTextAttribute == null || !eANTextAttribute.containsNLS(1)) {
/* 417 */           this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "SAPASSORTMODULE", "SAPASSORTMODULE");
/* 418 */           addError("NOT_SPECBID_VALUE_ERR", this.args);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 425 */       checkAvailDates(paramEntityItem);
/*     */     } 
/*     */     
/* 428 */     this.mdlList.dereference();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getModelVE(EntityItem paramEntityItem) throws Exception {
/* 437 */     String str = "EXRPT3LSEOBDL2";
/*     */     
/* 439 */     this.mdlList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 440 */           .getProfile(), str), new EntityItem[] { new EntityItem(null, this.m_elist
/* 441 */             .getProfile(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID()) });
/* 442 */     addDebug("getModelVE:: Extract " + str + NEWLINE + PokUtils.outputList(this.mdlList));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 452 */     return "LSEOBUNDLE ABR.";
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
/* 463 */     return "1.33";
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
/*     */   private void checkLSEOStatus(String paramString) throws SQLException, MiddlewareException {
/* 476 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/* 477 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*     */       
/* 479 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 480 */       String str = getAttributeFlagEnabledValue(entityItem, "STATUS");
/* 481 */       addDebug("checkLSEOStatus " + entityItem.getKey() + " lseoStatus: " + str);
/* 482 */       if (str == null || str.length() == 0) {
/* 483 */         str = "0020";
/*     */       }
/* 485 */       if ("0040".equals(paramString)) {
/* 486 */         if (!"0020".equals(str)) {
/* 487 */           this.args[0] = entityGroup.getLongDescription();
/* 488 */           this.args[1] = getNavigationName(entityItem);
/*     */           
/* 490 */           addError("ONE_NOT_FINAL_ERR", this.args);
/*     */         }
/*     */       
/* 493 */       } else if (!"0020".equals(str) && !"0040".equals(str)) {
/*     */         
/* 495 */         this.args[0] = entityGroup.getLongDescription();
/* 496 */         this.args[1] = getNavigationName(entityItem);
/*     */         
/* 498 */         addError("ONE_NOT_R4RFINAL_ERR", this.args);
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
/*     */   private void checkDates(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 510 */     String str = getAttributeValue(paramEntityItem, "BUNDLPUBDATEMTRGT", "");
/* 511 */     addDebug("checkDates " + paramEntityItem.getKey() + " BUNDLPUBDATEMTRGT: " + str);
/* 512 */     if (str.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 516 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("LSEO");
/* 517 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*     */       
/* 519 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 520 */       String str1 = getAttributeValue(entityItem, "LSEOPUBDATEMTRGT", "");
/* 521 */       addDebug("checkDates " + entityItem.getKey() + " LSEOPUBDATEMTRGT: " + str1);
/* 522 */       if (str1.length() > 0 && str1.compareTo(str) > 0) {
/*     */         
/* 524 */         this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "BUNDLPUBDATEMTRGT", "BUNDLPUBDATEMTRGT");
/* 525 */         this.args[1] = entityGroup.getLongDescription();
/* 526 */         this.args[2] = getNavigationName(entityItem);
/* 527 */         this.args[3] = PokUtils.getAttributeDescription(entityGroup, "LSEOPUBDATEMTRGT", "LSEOPUBDATEMTRGT");
/*     */ 
/*     */         
/* 530 */         addError("EARLY_DATE_ERR", this.args);
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
/*     */   private void checkAvailDates(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 545 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOBUNDLEAVAIL", "AVAIL");
/* 546 */     String str1 = getAttributeValue(paramEntityItem, "BUNDLPUBDATEMTRGT", "");
/* 547 */     String str2 = getAttributeValue(paramEntityItem, "BUNDLUNPUBDATEMTRGT", "");
/* 548 */     addDebug("checkAvailDates " + paramEntityItem.getKey() + " BUNDLPUBDATEMTRGT: " + str1 + " BUNDLUNPUBDATEMTRGT: " + str2 + " availVct: " + vector
/* 549 */         .size());
/*     */     
/* 551 */     for (byte b = 0; b < vector.size(); b++) {
/* 552 */       EntityItem entityItem = vector.elementAt(b);
/* 553 */       String str3 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 554 */       String str4 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/* 555 */       addDebug("checkAvailDates " + entityItem.getKey() + " EFFECTIVEDATE: " + str3 + " AVAILTYPE: " + str4);
/*     */ 
/*     */       
/* 558 */       if (str1.trim().length() > 0 && (
/* 559 */         "146".equals(str4) || "143".equals(str4)) && str3
/* 560 */         .length() > 0 && str3.compareTo(str1) < 0) {
/*     */ 
/*     */         
/* 563 */         this.args[0] = entityItem.getEntityGroup().getLongDescription() + " " + getNavigationName(entityItem);
/* 564 */         this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "BUNDLPUBDATEMTRGT", "BUNDLPUBDATEMTRGT");
/* 565 */         this.args[2] = "";
/* 566 */         this.args[3] = "";
/* 567 */         addError("EARLY_DATE_ERR", this.args);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 572 */       if (str2.trim().length() > 0 && 
/* 573 */         "149".equals(str4) && str3.length() > 0 && str3.compareTo(str2) > 0) {
/*     */ 
/*     */         
/* 576 */         this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 577 */         this.args[1] = getNavigationName(entityItem);
/* 578 */         this.args[2] = "";
/* 579 */         this.args[3] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "BUNDLUNPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT");
/* 580 */         this.args[4] = "";
/* 581 */         addError("LATER_DATE_ERR", this.args);
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
/*     */   private void validateOS(EntityItem paramEntityItem) throws MiddlewareException {
/* 604 */     String str = "OSLEVEL";
/* 605 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(str);
/* 606 */     if (eANMetaAttribute == null) {
/* 607 */       addDebug("validateOS ERROR:Attribute " + str + " NOT found in LSEOBUNDLE META data.");
/*     */       
/*     */       return;
/*     */     } 
/* 611 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/* 613 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str);
/* 614 */     if (eANFlagAttribute != null) {
/*     */       
/* 616 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 617 */       for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */         
/* 619 */         if (arrayOfMetaFlag[b1].isSelected()) {
/* 620 */           arrayList.add(arrayOfMetaFlag[b1].getFlagCode());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 625 */     addDebug("validateOS " + paramEntityItem.getKey() + " oslevel " + arrayList);
/*     */ 
/*     */     
/* 628 */     EntityGroup entityGroup = this.mdlList.getEntityGroup("MODEL");
/*     */     
/* 630 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 631 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 632 */       String str1 = getAttributeFlagEnabledValue(entityItem, "COFCAT");
/* 633 */       String str2 = getAttributeFlagEnabledValue(entityItem, "APPLICATIONTYPE");
/* 634 */       addDebug("validateOS " + entityItem.getKey() + " COFCAT: " + str1 + " APPLICATIONTYPE: " + str2);
/* 635 */       if ("101".equals(str1) && "33".equals(str2)) {
/*     */         
/* 637 */         Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "MODELWWSEO", "WWSEO");
/*     */         
/* 639 */         EntityGroup entityGroup1 = this.m_elist.getEntityGroup("WWSEO");
/* 640 */         Vector<EntityItem> vector2 = new Vector(1);
/* 641 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 642 */           EntityItem entityItem1 = vector1.elementAt(b1);
/* 643 */           EntityItem entityItem2 = entityGroup1.getEntityItem(entityItem1.getKey());
/* 644 */           vector2.add(entityItem2);
/*     */         } 
/* 646 */         vector1.clear();
/* 647 */         Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(vector2, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT");
/* 648 */         addDebug("validateOS " + entityItem.getKey() + " wwseoVct " + vector2.size() + " swpsVct: " + vector3.size());
/*     */         
/* 650 */         for (byte b2 = 0; b2 < vector3.size(); b2++) {
/* 651 */           EntityItem entityItem1 = vector3.elementAt(b2);
/* 652 */           addDebug("validateOS " + entityItem1.getKey() + " uplinkcnt: " + entityItem1.getUpLinkCount());
/* 653 */           for (byte b3 = 0; b3 < entityItem1.getUpLinkCount(); b3++) {
/* 654 */             EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(b3);
/* 655 */             if (entityItem2.getEntityType().equals("SWFEATURE")) {
/* 656 */               String str3 = getAttributeFlagEnabledValue(entityItem2, "SWFCCAT");
/* 657 */               addDebug("validateOS " + entityItem2.getKey() + " SWFCCAT: " + str3);
/* 658 */               if ("319".equals(str3)) {
/* 659 */                 ArrayList<String> arrayList1 = new ArrayList();
/*     */                 
/* 661 */                 EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute(str);
/* 662 */                 if (eANFlagAttribute1 != null) {
/*     */                   
/* 664 */                   MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute1.get();
/* 665 */                   for (byte b4 = 0; b4 < arrayOfMetaFlag.length; b4++) {
/*     */                     
/* 667 */                     if (arrayOfMetaFlag[b4].isSelected()) {
/* 668 */                       arrayList1.add(arrayOfMetaFlag[b4].getFlagCode());
/*     */                     }
/*     */                   } 
/*     */                 } 
/* 672 */                 addDebug("validateOS ValueMetric " + entityItem2.getKey() + " oslevel " + arrayList1);
/* 673 */                 if (!arrayList.containsAll(arrayList1) || !arrayList1.containsAll(arrayList)) {
/*     */                   
/* 675 */                   this.args[0] = PokUtils.getAttributeDescription(entityItem2.getEntityGroup(), str, str);
/* 676 */                   addError("OSLEVEL_ERR", this.args);
/*     */                 } 
/* 678 */                 arrayList1.clear();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 683 */         vector3.clear();
/* 684 */         vector2.clear();
/*     */       } 
/*     */     } 
/* 687 */     arrayList.clear();
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
/*     */   private void validateBUNDLETYPE(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 704 */     String str = "BUNDLETYPE";
/* 705 */     ArrayList<String> arrayList1 = new ArrayList();
/* 706 */     ArrayList<String> arrayList2 = new ArrayList();
/* 707 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(str);
/* 708 */     if (eANMetaAttribute == null) {
/* 709 */       addDebug("validateBUNDLETYPE ERROR:Attribute " + str + " NOT found in LSEOBUNDLE META data.");
/*     */       
/*     */       return;
/*     */     } 
/* 713 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str);
/* 714 */     if (eANFlagAttribute != null) {
/*     */       
/* 716 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 717 */       for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */         
/* 719 */         if (arrayOfMetaFlag[b1].isSelected()) {
/* 720 */           arrayList2.add(arrayOfMetaFlag[b1].getFlagCode());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 725 */     addDebug("validateBUNDLETYPE " + paramEntityItem.getKey() + " bdlTypes " + arrayList2);
/*     */ 
/*     */     
/* 728 */     EntityGroup entityGroup = this.mdlList.getEntityGroup("MODEL");
/* 729 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 730 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*     */       
/* 732 */       String str1 = getAttributeFlagEnabledValue(entityItem, "COFCAT");
/* 733 */       if (str1 == null) {
/* 734 */         str1 = "";
/*     */       }
/* 736 */       addDebug("validateBUNDLETYPE " + entityItem.getKey() + " COFCAT: " + str1);
/* 737 */       if (!arrayList2.contains(str1)) {
/*     */ 
/*     */ 
/*     */         
/* 741 */         this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 742 */         this.args[1] = getNavigationName(entityItem);
/* 743 */         this.args[2] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), str, str);
/* 744 */         addError("MODEL_TYPE_ERR", this.args);
/*     */       } 
/* 746 */       arrayList1.add(str1);
/*     */     } 
/*     */ 
/*     */     
/* 750 */     addDebug("validateBUNDLETYPE all mdlTypes " + arrayList1);
/* 751 */     if (!arrayList1.containsAll(arrayList2)) {
/*     */ 
/*     */ 
/*     */       
/* 755 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), str, str);
/* 756 */       this.args[1] = this.m_elist.getEntityGroup("LSEO").getLongDescription();
/* 757 */       addError("BDLE_TYPE_ERR", this.args);
/*     */     } 
/* 759 */     arrayList1.clear();
/* 760 */     arrayList2.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\LSEOBDLABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */