/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
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
/*      */ public class ANNABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*  112 */   private static final String[] LSEOABRS = new String[] { "EPIMSABRSTATUS", "ADSABRSTATUS" };
/*  113 */   private static final String[] MODELABRS = new String[] { "ADSABRSTATUS" };
/*  114 */   private static final String[] SVCMODABRS = new String[] { "ADSABRSTATUS" };
/*  115 */   private static final String[] LSEOBDLABRS = new String[] { "EPIMSABRSTATUS" };
/*  116 */   private static final String[] RFCABRS = new String[] { "RFCABRSTATUS" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  122 */     return true;
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  219 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  220 */     String str = getAttributeFlagEnabledValue(entityItem, "ANNTYPE");
/*  221 */     addDebug(entityItem.getKey() + " status now R4R type " + str);
/*      */     
/*  223 */     doR10AAOfferings(this.m_elist.getEntityGroup("AVAIL"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  234 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  235 */     String str = getAttributeFlagEnabledValue(entityItem, "ANNTYPE");
/*  236 */     addDebug(entityItem.getKey() + " status now final type " + str);
/*      */ 
/*      */     
/*  239 */     if ("14".equals(str)) {
/*  240 */       addDebug(entityItem.getKey() + " is Final and EOL");
/*      */ 
/*      */       
/*  243 */       if (domainInRuleList(entityItem, "XCC_LIST")) {
/*  244 */         setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), entityItem);
/*      */       } else {
/*  246 */         addDebug("nowFinal:  " + entityItem.getKey() + " was EOL and domain not in XCCLIST");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  253 */     if ("19".equals(str) && 
/*  254 */       domainInRuleList(entityItem, "XCC_LIST")) {
/*  255 */       addDebug(entityItem.getKey() + " is Final and New and in xcclist");
/*      */       
/*  257 */       setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", getQueuedValue("WWPRTABRSTATUS"), entityItem);
/*      */       
/*  259 */       setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), entityItem);
/*      */     } 
/*      */ 
/*      */     
/*  263 */     boolean bool = doR10processing();
/*  264 */     boolean bool1 = false;
/*      */ 
/*      */     
/*  267 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*  268 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  269 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*      */       
/*  271 */       if (statusIsFinal(entityItem1)) {
/*      */ 
/*      */         
/*  274 */         if (domainInRuleList(entityItem, "XCC_LIST"))
/*      */         {
/*      */           
/*  277 */           verifyStatusAndSetABRStatus(entityItem1, "LSEOAVAIL", "LSEO", LSEOABRS);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  283 */         verifyStatusAndSetABRStatus(entityItem1, "MODELAVAIL", "MODEL", MODELABRS);
/*      */ 
/*      */         
/*  286 */         verifyStatusAndSetABRStatus(entityItem1, "SVCMODAVAIL", "SVCMOD", SVCMODABRS);
/*      */ 
/*      */         
/*  289 */         verifyStatusAndSetABRStatus(entityItem1, "LSEOBUNDLEAVAIL", "LSEOBUNDLE", LSEOBDLABRS);
/*      */ 
/*      */         
/*  292 */         if (bool) {
/*      */ 
/*      */           
/*  295 */           verifyStatusAndSetABRStatus(entityItem1, "MODELCONVERTAVAIL", "MODELCONVERT", SVCMODABRS);
/*      */           
/*  297 */           verifyStatusAndSetABRStatus(entityItem1, "MODELCONVERTAVAIL", "MODELCONVERT", RFCABRS);
/*      */ 
/*      */ 
/*      */           
/*  301 */           verifyStatusAndSetABRStatus(entityItem1, "OOFAVAIL", "PRODSTRUCT", SVCMODABRS);
/*      */           
/*  303 */           verifyStatusAndSetABRStatus(entityItem1, "OOFAVAIL", "PRODSTRUCT", RFCABRS);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  308 */           verifySVCSEOAndSetSVCMODABRStatus(entityItem1);
/*      */ 
/*      */ 
/*      */           
/*  312 */           verifyStatusAndSetABRStatus(entityItem1, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT", SVCMODABRS);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  319 */       if (!bool1) {
/*  320 */         addDebug("check if has Hardware or HIPO model for  " + entityItem1.getKey());
/*  321 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem1, "MODELAVAIL", "MODEL");
/*  322 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  323 */           EntityItem entityItem2 = vector.elementAt(b1);
/*  324 */           if (isHardwareOrHIPOModel(entityItem2)) {
/*  325 */             bool1 = true;
/*      */             break;
/*      */           } 
/*      */         } 
/*  329 */         vector.clear();
/*  330 */         Vector vector1 = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "PRODSTRUCT");
/*  331 */         addDebug("check if has PRODSTRUCT for  " + entityItem1.getKey() + " size " + vector1.size());
/*  332 */         if (vector1.size() > 0) {
/*  333 */           bool1 = true;
/*      */         }
/*  335 */         vector1.clear();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  340 */     doR10AAOfferings(entityGroup);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  345 */     if (isQsmANNTYPE(str) && bool1) {
/*  346 */       addDebug("ANNOUNCETMENT goes to final to trigger QSM");
/*  347 */       setFlagValue(this.m_elist.getProfile(), "QSMCREFABRSTATUS", getQueuedValueForItem(entityItem, "QSMCREFABRSTATUS"), entityItem);
/*  348 */       setFlagValue(this.m_elist.getProfile(), "QSMFULLABRSTATUS", getQueuedValueForItem(entityItem, "QSMFULLABRSTATUS"), entityItem);
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
/*      */   private void doR10AAOfferings(EntityGroup paramEntityGroup) {
/*  400 */     if (!doR10processing()) {
/*      */       return;
/*      */     }
/*      */     
/*  404 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*  405 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*      */ 
/*      */       
/*  408 */       if (statusIsRFRorFinal(entityItem)) {
/*  409 */         String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/*  410 */         addDebug("doR10AAOfferings " + entityItem.getKey() + "  availAnntypeFlag " + str);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  415 */         doRFR_ADSXML(entityItem, "LSEOAVAIL", "LSEO");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  421 */         doRFR_ADSXML(entityItem, "MODELAVAIL", "MODEL");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  427 */         doRFR_ADSXML(entityItem, "SVCMODAVAIL", "SVCMOD");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  433 */         doRFR_ADSXML(entityItem, "LSEOBUNDLEAVAIL", "LSEOBUNDLE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  439 */         doRFR_ADSXML(entityItem, "MODELCONVERTAVAIL", "MODELCONVERT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  445 */         doRFR_ADSXML(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  452 */         doRFR_SVCSEO_SVCMODADSXML(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  458 */         doRFR_ADSXML(entityItem, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doRFR_ADSXML(EntityItem paramEntityItem, String paramString1, String paramString2) {
/*  467 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, paramString2);
/*  468 */     for (byte b = 0; b < vector.size(); b++) {
/*  469 */       EntityItem entityItem = vector.elementAt(b);
/*  470 */       doRFR_ADSXML(entityItem);
/*      */     } 
/*  472 */     vector.clear();
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
/*      */   private void doRFR_SVCSEO_SVCMODADSXML(EntityItem paramEntityItem) {
/*  485 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCSEOAVAIL", "SVCSEO");
/*  486 */     for (byte b = 0; b < vector.size(); b++) {
/*  487 */       EntityItem entityItem = vector.elementAt(b);
/*  488 */       if (statusIsRFR(entityItem)) {
/*      */         
/*  490 */         Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "SVCMODSVCSEO", "SVCMOD");
/*  491 */         addDebug("doRFR_SVCSEO_SVCMODADSXML " + entityItem.getKey() + " svcmodVct.size " + vector1.size());
/*  492 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  493 */           EntityItem entityItem1 = vector1.elementAt(b1);
/*  494 */           doRFR_ADSXML(entityItem1);
/*      */         } 
/*  496 */         vector1.clear();
/*      */       } 
/*      */     } 
/*  499 */     vector.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifySVCSEOAndSetSVCMODABRStatus(EntityItem paramEntityItem) {
/*  510 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "SVCSEOAVAIL", "SVCSEO");
/*  511 */     for (byte b = 0; b < vector.size(); b++) {
/*  512 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/*  514 */       if (statusIsFinal(entityItem)) {
/*      */         
/*  516 */         Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "SVCMODSVCSEO", "SVCMOD");
/*  517 */         addDebug("verifySVCSEOAndSetSVCMODABRStatus " + entityItem.getKey() + " svcmodVct.size " + vector1.size());
/*  518 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  519 */           EntityItem entityItem1 = vector1.elementAt(b1);
/*      */           
/*  521 */           if (statusIsFinal(entityItem1))
/*      */           {
/*  523 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*      */           }
/*      */         } 
/*  526 */         vector1.clear();
/*      */       } 
/*      */     } 
/*  529 */     vector.clear();
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
/*      */   private void verifyStatusAndSetABRStatus(EntityItem paramEntityItem, String paramString1, String paramString2, String[] paramArrayOfString) throws MiddlewareRequestException, MiddlewareException, SQLException {
/*  544 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, paramString2);
/*  545 */     for (byte b = 0; b < vector.size(); b++) {
/*  546 */       EntityItem entityItem = vector.elementAt(b);
/*  547 */       if (statusIsFinal(entityItem)) {
/*  548 */         String str = entityItem.getEntityType();
/*  549 */         for (byte b1 = 0; b1 < paramArrayOfString.length; b1++) {
/*      */ 
/*      */ 
/*      */           
/*  553 */           if ("MODEL".equals(str) && "ADSABRSTATUS".equals(paramArrayOfString[b1])) {
/*  554 */             setSinceFirstFinal(entityItem, paramArrayOfString[b1]);
/*      */           } else {
/*  556 */             setFlagValue(this.m_elist.getProfile(), paramArrayOfString[b1], getQueuedValueForItem(entityItem, paramArrayOfString[b1]), entityItem);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  564 */         if ("MODEL".equals(str) && isHardwareOrHIPOModel(entityItem)) {
/*  565 */           setRFCSinceFirstFinal(entityItem, "RFCABRSTATUS");
/*      */         }
/*      */       } 
/*      */     } 
/*  569 */     vector.clear();
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
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  654 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "ANNTYPE");
/*  655 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/*  657 */     Vector vector1 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "149");
/*  658 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "172");
/*  659 */     Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "146");
/*  660 */     Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "171");
/*  661 */     Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "143");
/*  662 */     Vector vector6 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "200");
/*  663 */     Vector vector7 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "151");
/*      */     
/*  665 */     ArrayList arrayList = new ArrayList();
/*  666 */     getAttributeAsList(paramEntityItem, arrayList, "COUNTRYLIST", getCheck_RW_RW_RE(paramString));
/*      */     
/*  668 */     addDebug("doDQChecking " + paramEntityItem.getKey() + " ANNTYPE " + str);
/*      */ 
/*      */     
/*  671 */     removeNonRFAAVAIL(vector1);
/*  672 */     removeNonRFAAVAIL(vector2);
/*      */     
/*  674 */     removeNonRFAAndEPICAVAIL(vector3);
/*  675 */     removeNonRFAAndEPICAVAIL(vector4);
/*  676 */     removeNonRFAAVAIL(vector5);
/*  677 */     removeNonRFAAVAIL(vector6);
/*  678 */     removeNonRFAAVAIL(vector7);
/*      */     
/*  680 */     addDebug("doDQChecking  plannedAvailVct: " + vector3.size() + " mesPlannedAvailVct: " + vector4
/*  681 */         .size() + " firstOrderAvailVct: " + vector5
/*  682 */         .size() + " lastOrderAvailVct: " + vector1.size() + " mesLastOrderAvailVct: " + vector2
/*  683 */         .size() + " eomAvailVct: " + vector6
/*  684 */         .size() + " eosAvailVct: " + vector7.size() + " annCtry:" + arrayList);
/*      */ 
/*      */     
/*  687 */     if ("19".equals(str)) {
/*  688 */       addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " New Checks:");
/*  689 */       checkNewAnn(paramEntityItem, vector3, vector4, vector5, arrayList, paramString);
/*  690 */     } else if ("14".equals(str)) {
/*  691 */       addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " End Of Life - Withdrawal from mktg Checks:");
/*  692 */       checkEOLAnn(paramEntityItem, vector6, vector1, vector2, arrayList, paramString);
/*  693 */     } else if ("13".equals(str)) {
/*  694 */       addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " End Of Life - Discontinuance of service Checks:");
/*  695 */       checkEOLDSAnn(paramEntityItem, vector7, arrayList, paramString);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  702 */       addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " No Checks Done");
/*      */       
/*  704 */       this.args[0] = getLD_Value(paramEntityItem, "ANNTYPE");
/*  705 */       createMessage(4, "ANNTYPE_NOT_SUPPORTED_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  712 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Availability RFA checks:");
/*  713 */     checkAvailAnnType();
/*      */ 
/*      */     
/*  716 */     arrayList.clear();
/*  717 */     vector1.clear();
/*  718 */     vector3.clear();
/*  719 */     vector5.clear();
/*  720 */     vector6.clear();
/*  721 */     vector7.clear();
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
/*      */   private void checkNewAnn(EntityItem paramEntityItem, Vector<EntityItem> paramVector1, Vector<EntityItem> paramVector2, Vector<EntityItem> paramVector3, ArrayList paramArrayList, String paramString) throws Exception {
/*  760 */     int i = getCheck_W_W_E(paramString);
/*  761 */     int j = getCheck_RW_RW_RE(paramString);
/*      */     
/*  763 */     addDebug("checking keys 5, 9, 10");
/*  764 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Planned Avail Checks:");
/*      */     byte b1;
/*  766 */     for (b1 = 0; b1 < paramVector1.size(); b1++) {
/*  767 */       EntityItem entityItem = paramVector1.elementAt(b1);
/*      */ 
/*      */       
/*  770 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 3);
/*      */ 
/*      */       
/*  773 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", j);
/*      */ 
/*      */ 
/*      */       
/*  777 */       checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem, paramArrayList, j);
/*      */     } 
/*      */     
/*  780 */     addDebug("checking keys 11,20,21");
/*      */ 
/*      */     
/*  783 */     checkPlannedAvailsExist(paramVector1, 3);
/*      */ 
/*      */     
/*  786 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " MES Planned Avail Checks:");
/*      */     
/*  788 */     for (b1 = 0; b1 < paramVector2.size(); b1++) {
/*  789 */       EntityItem entityItem = paramVector2.elementAt(b1);
/*      */ 
/*      */       
/*  792 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 3);
/*      */ 
/*      */       
/*  795 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", j);
/*      */ 
/*      */ 
/*      */       
/*  799 */       checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem, paramArrayList, j);
/*      */     } 
/*      */     
/*  802 */     addDebug("checking keys 6,16,17");
/*  803 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " First Order Avail Checks:");
/*      */     
/*  805 */     for (b1 = 0; b1 < paramVector3.size(); b1++) {
/*  806 */       EntityItem entityItem = paramVector3.elementAt(b1);
/*      */ 
/*      */       
/*  809 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 4);
/*      */ 
/*      */ 
/*      */       
/*  813 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", i);
/*      */ 
/*      */ 
/*      */       
/*  817 */       checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem, paramArrayList, i);
/*      */     } 
/*      */     
/*  820 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Other Avail Checks:");
/*      */ 
/*      */     
/*  823 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/*  825 */     for (byte b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/*  826 */       EntityItem entityItem = entityGroup.getEntityItem(b2);
/*  827 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE");
/*  828 */       addDebug(entityItem.getKey() + " availtype " + str);
/*  829 */       if (!"146".equals(str) && !"171".equals(str) && !"143".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  835 */         this.args[0] = getLD_NDN(entityItem);
/*  836 */         this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/*  837 */         createMessage(4, "CANNOT_BE_IN_ERR", this.args);
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkEOLAnn(EntityItem paramEntityItem, Vector<EntityItem> paramVector1, Vector<EntityItem> paramVector2, Vector<EntityItem> paramVector3, ArrayList paramArrayList, String paramString) throws Exception {
/*  875 */     int i = getCheck_RW_RW_RE(paramString);
/*      */     
/*  877 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Last Order Avail Checks:");
/*      */     byte b1;
/*  879 */     for (b1 = 0; b1 < paramVector2.size(); b1++) {
/*  880 */       EntityItem entityItem = paramVector2.elementAt(b1);
/*      */       
/*  882 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 3);
/*      */ 
/*      */       
/*  885 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", i);
/*      */ 
/*      */ 
/*      */       
/*  889 */       checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem, paramArrayList, i);
/*      */     } 
/*      */ 
/*      */     
/*  893 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " MES Last Order Avail Checks:");
/*      */     
/*  895 */     for (b1 = 0; b1 < paramVector3.size(); b1++) {
/*  896 */       EntityItem entityItem = paramVector3.elementAt(b1);
/*      */       
/*  898 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 3);
/*      */ 
/*      */       
/*  901 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", i);
/*      */ 
/*      */ 
/*      */       
/*  905 */       checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem, paramArrayList, i);
/*      */     } 
/*      */ 
/*      */     
/*  909 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " End of Marketing Avail Checks:");
/*      */     
/*  911 */     for (b1 = 0; b1 < paramVector1.size(); b1++) {
/*  912 */       EntityItem entityItem = paramVector1.elementAt(b1);
/*      */       
/*  914 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 3);
/*      */ 
/*      */ 
/*      */       
/*  918 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", i);
/*      */ 
/*      */       
/*  921 */       checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem, paramArrayList, i);
/*      */     } 
/*      */ 
/*      */     
/*  925 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Other Avail Checks:");
/*  926 */     addDebug("checking keys 36,39 ");
/*      */ 
/*      */     
/*  929 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/*  931 */     for (byte b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/*  932 */       EntityItem entityItem = entityGroup.getEntityItem(b2);
/*  933 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE");
/*  934 */       addDebug(entityItem.getKey() + " availtype " + str);
/*  935 */       if (!"200".equals(str) && !"149".equals(str) && !"172".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  940 */         this.args[0] = getLD_NDN(entityItem);
/*  941 */         this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/*  942 */         createMessage(4, "CANNOT_BE_IN_ERR", this.args);
/*      */       } 
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
/*      */   private void checkEOLDSAnn(EntityItem paramEntityItem, Vector<EntityItem> paramVector, ArrayList paramArrayList, String paramString) throws Exception {
/*  985 */     int i = getCheck_RW_RW_RE(paramString);
/*      */     
/*  987 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " End of Service Avail Checks:");
/*      */     
/*  989 */     for (byte b1 = 0; b1 < paramVector.size(); b1++) {
/*  990 */       EntityItem entityItem = paramVector.elementAt(b1);
/*      */       
/*  992 */       checkStatusVsDQ(entityItem, "STATUS", paramEntityItem, 3);
/*      */ 
/*      */       
/*  995 */       checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", i);
/*      */ 
/*      */       
/*  998 */       checkAvailCtryInEntity((EntityItem)null, entityItem, paramEntityItem, paramArrayList, i);
/*      */     } 
/*      */ 
/*      */     
/* 1002 */     addHeading(3, paramEntityItem.getEntityGroup().getLongDescription() + " Other Avail Checks:");
/*      */ 
/*      */     
/* 1005 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/* 1007 */     for (byte b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/* 1008 */       EntityItem entityItem = entityGroup.getEntityItem(b2);
/* 1009 */       String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE");
/* 1010 */       addDebug(entityItem.getKey() + " availtype " + str);
/* 1011 */       if (!"151".equals(str) && !"201".equals(str)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1016 */         this.args[0] = getLD_NDN(entityItem);
/* 1017 */         this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 1018 */         createMessage(4, "CANNOT_BE_IN_ERR", this.args);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getStatusAttrCode() {
/* 1026 */     return "ANNSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1035 */     return "ANNOUNCEMENT ABR.";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1046 */     return "1.12";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\ANNABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */