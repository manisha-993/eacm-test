/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.objects.Attribute;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FCABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*      */   private static final String FC_SRCHACTION_NAME = "SRDFEATURE9";
/*  125 */   private static final char[] FC_CODE_ILLEGAL = new char[] { 'O', 'I' };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  131 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getVEName(String paramString1, String paramString2) {
/*  142 */     if (paramString1.equals("0020")) {
/*  143 */       addDebug("Status already final, use diff ve");
/*  144 */       return "EXRPT3FEATURE2";
/*  145 */     }  if (paramString1.equals("0040") && paramString2.equals("REVIEW")) {
/*  146 */       addDebug("Status already rfr, use diff ve");
/*  147 */       return "EXRPT3FEATURE2";
/*      */     } 
/*  149 */     return this.m_abri.getVEName();
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
/*      */   protected void doAlreadyFinalProcessing(EntityItem paramEntityItem) throws Exception {
/*  163 */     if (doDARULEs()) {
/*  164 */       boolean bool = updateDerivedData(paramEntityItem);
/*  165 */       addDebug("doAlreadyFinalProcessing: " + paramEntityItem.getKey() + " chgsMade " + bool);
/*  166 */       if (bool) {
/*  167 */         setSinceFirstFinal(paramEntityItem, "ADSABRSTATUS");
/*  168 */         queueProdStruct(true);
/*      */       } else {
/*      */         
/*  171 */         this.args[0] = this.m_elist.getEntityGroup("CATDATA").getLongDescription();
/*  172 */         addResourceMsg("NO_CHGSFOUND", this.args);
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
/*      */   protected void doAlreadyRFRProcessing(EntityItem paramEntityItem) throws Exception {
/*  188 */     if (doDARULEs()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  198 */       boolean bool = updateDerivedData(paramEntityItem);
/*  199 */       addDebug("doAlreadyRFRProcessing: " + paramEntityItem.getKey() + " chgsMade " + bool);
/*  200 */       if (bool) {
/*  201 */         setRFRSinceFirstRFR(paramEntityItem);
/*  202 */         queueProdStruct(false);
/*      */       } else {
/*      */         
/*  205 */         this.args[0] = this.m_elist.getEntityGroup("CATDATA").getLongDescription();
/*  206 */         addResourceMsg("NO_CHGSFOUND", this.args);
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  239 */     if (doR10processing()) {
/*  240 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  241 */       setRFRSinceFirstRFR(entityItem);
/*  242 */       queueProdStruct(false);
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
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  270 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  272 */     if (doR10processing()) {
/*      */ 
/*      */       
/*  275 */       setSinceFirstFinal(entityItem, "ADSABRSTATUS");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  286 */       queueProdStruct(true);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  296 */     boolean bool = isQSMRPQ(entityItem);
/*  297 */     addDebug("completeNowFinalProcessing - isRPQ " + bool);
/*  298 */     if (bool) {
/*  299 */       setFlagValue(this.m_elist.getProfile(), "QSMRPQCREFABRSTATUS", getQueuedValueForItem(entityItem, "QSMRPQCREFABRSTATUS"), entityItem);
/*  300 */       setFlagValue(this.m_elist.getProfile(), "QSMRPQFULLABRSTATUS", getQueuedValueForItem(entityItem, "QSMRPQFULLABRSTATUS"), entityItem);
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
/*      */   private void queueProdStruct(boolean paramBoolean) {
/*  314 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  315 */     boolean bool = isRPQ(entityItem);
/*      */     
/*  317 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*  318 */     Vector vector = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "146");
/*      */ 
/*      */     
/*  321 */     addDebug("queueProdStruct - isRPQ " + bool);
/*  322 */     addDebug("queueProdStruct - ALL plannedAvailVct.size " + vector.size());
/*  323 */     if (bool || (!bool && vector.size() == 0)) {
/*  324 */       EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PRODSTRUCT");
/*  325 */       for (byte b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/*  326 */         EntityItem entityItem1 = entityGroup1.getEntityItem(b);
/*  327 */         if (statusIsRFR(entityItem1) && !paramBoolean) {
/*  328 */           addDebug("queueProdStruct - RFR PS " + entityItem1.getKey());
/*  329 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*  330 */         } else if (statusIsFinal(entityItem1) && paramBoolean) {
/*  331 */           addDebug("queueProdStruct - FINAL PS " + entityItem1.getKey());
/*  332 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*      */ 
/*      */           
/*  335 */           setFlagValue(this.m_elist.getProfile(), "RFCABRSTATUS", getQueuedValueForItem(entityItem1, "RFCABRSTATUS"), entityItem1);
/*      */         } 
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
/*      */   protected boolean updateDerivedData(EntityItem paramEntityItem) throws Exception {
/*  355 */     boolean bool = false;
/*      */     
/*  357 */     String str = PokUtils.getAttributeValue(paramEntityItem, "WITHDRAWDATEEFF_T", "", "9999-12-31", false);
/*  358 */     addDebug("updateDerivedData wdDate: " + str + " now: " + getCurrentDate());
/*  359 */     if (getCurrentDate().compareTo(str) <= 0) {
/*  360 */       bool = execDerivedData(paramEntityItem);
/*      */     }
/*  362 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLCRFRWFName() {
/*  369 */     return "WFLCFEATURERFR"; } protected String getLCFinalWFName() {
/*  370 */     return "WFLCFEATUREFINAL";
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
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  458 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*      */ 
/*      */ 
/*      */     
/*  462 */     setBHInvnameHW(paramEntityItem);
/*      */     
/*  464 */     if (getReturnCode() != 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  469 */     int i = getCheck_W_W_E(paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  475 */     int j = getCount("FEATUREMONITOR");
/*  476 */     if (j > 1) {
/*  477 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("MONITOR");
/*      */ 
/*      */       
/*  480 */       this.args[0] = entityGroup.getLongDescription();
/*  481 */       createMessage(i, "MORE_THAN_ONE_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  488 */     checkFeatureCode(paramEntityItem, 4);
/*      */ 
/*      */ 
/*      */     
/*  492 */     checkCanNotBeEarlier(paramEntityItem, "WITHDRAWANNDATE_T", "FIRSTANNDATE", i);
/*      */ 
/*      */ 
/*      */     
/*  496 */     checkCanNotBeEarlier(paramEntityItem, "WITHDRAWDATEEFF_T", "GENAVAILDATE", i);
/*      */ 
/*      */     
/*  499 */     if (!isRPQ(paramEntityItem)) {
/*  500 */       addDebug(paramEntityItem.getKey() + " was NOT an RPQ FCTYPE: " + getAttributeFlagEnabledValue(paramEntityItem, "FCTYPE"));
/*  501 */       checkAvails(paramEntityItem, paramString);
/*      */     } else {
/*      */       
/*  504 */       addDebug(paramEntityItem.getKey() + " was an RPQ FCTYPE: " + getAttributeFlagEnabledValue(paramEntityItem, "FCTYPE"));
/*      */ 
/*      */       
/*  507 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/*  508 */       addHeading(3, entityGroup.getLongDescription() + " RPQ Checks:");
/*  509 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  510 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */         
/*  513 */         checkCanNotBeEarlier(entityItem, "ANNDATE", paramEntityItem, "FIRSTANNDATE", i);
/*      */ 
/*      */ 
/*      */         
/*  517 */         checkCanNotBeEarlier(entityItem, "GENAVAILDATE", paramEntityItem, "FIRSTANNDATE", i);
/*      */ 
/*      */         
/*  520 */         checkCanNotBeLater(entityItem, "WITHDRAWDATE", paramEntityItem, "WITHDRAWANNDATE_T", i);
/*      */ 
/*      */ 
/*      */         
/*  524 */         checkCanNotBeLater(entityItem, "WTHDRWEFFCTVDATE", paramEntityItem, "WITHDRAWDATEEFF_T", i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkFeatureCode(EntityItem paramEntityItem, int paramInt) {
/*  531 */     if (domainInRuleList(paramEntityItem, "PWRFC_LIST")) {
/*  532 */       String str = PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", ", ", "", false);
/*  533 */       if (checkChars(FC_CODE_ILLEGAL, str)) {
/*  534 */         this.args[0] = getLD_Value(paramEntityItem, "FEATURECODE");
/*  535 */         createMessage(paramInt, "INVAILD_CHAR_IO_ERROR", this.args);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkAvails(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/*  605 */     int i = getCheck_W_W_E(paramString);
/*      */     
/*  607 */     ArrayList arrayList = new ArrayList();
/*  608 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PRODSTRUCT");
/*      */ 
/*      */     
/*  611 */     getCountriesAsList(paramEntityItem, arrayList, i);
/*  612 */     String str1 = getAttrValueAndCheckLvl(paramEntityItem, "FIRSTANNDATE", i);
/*  613 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem, "WITHDRAWANNDATE_T", i);
/*  614 */     addDebug("checkAvails " + paramEntityItem.getKey() + " FIRSTANNDATE: " + str1 + " WITHDRAWANNDATE_T: " + str2 + " featCtrylist " + arrayList);
/*      */ 
/*      */ 
/*      */     
/*  618 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("AVAIL");
/*      */ 
/*      */     
/*  621 */     Vector vector1 = PokUtils.getEntitiesWithMatchedAttr(entityGroup2, "AVAILTYPE", "146");
/*      */ 
/*      */ 
/*      */     
/*  625 */     addDebug("checkAvails ALL plannedAvailVct " + vector1.size());
/*  626 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(entityGroup2, "AVAILTYPE", "149");
/*  627 */     addDebug("checkAvails ALL loAvailVct " + vector2.size());
/*      */     
/*  629 */     Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(entityGroup2, "AVAILTYPE", "172");
/*  630 */     addDebug("checkAvails ALL mesloAvailVct " + vector3.size());
/*      */     
/*  632 */     addHeading(3, entityGroup2.getLongDescription() + " Planned Avail Checks:");
/*  633 */     checkPsPlanOrMesPlanAvail(vector1, paramEntityItem, arrayList, str1, i);
/*      */ 
/*      */     
/*  636 */     Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(entityGroup2, "AVAILTYPE", "171");
/*  637 */     addDebug("checkAvails ALL mesPlannedAvailVct " + vector4.size());
/*  638 */     addHeading(3, entityGroup2.getLongDescription() + " MES Planned Avail Checks:");
/*  639 */     checkPsPlanOrMesPlanAvail(vector4, paramEntityItem, arrayList, str1, i);
/*      */     
/*  641 */     addHeading(3, entityGroup2.getLongDescription() + " First Order Avail Checks:");
/*      */     
/*  643 */     Vector<EntityItem> vector = PokUtils.getEntitiesWithMatchedAttr(entityGroup2, "AVAILTYPE", "143");
/*  644 */     addDebug("checkAvails ALL firstOrderAvailVct " + vector.size());
/*      */     
/*  646 */     if (vector.size() > 0) {
/*      */       byte b1;
/*  648 */       for (b1 = 0; b1 < vector.size(); b1++) {
/*  649 */         EntityItem entityItem1 = vector.elementAt(b1);
/*  650 */         EntityItem entityItem2 = getAvailPS(entityItem1, "OOFAVAIL");
/*      */ 
/*      */         
/*  653 */         checkCanNotBeEarlier(entityItem2, entityItem1, "EFFECTIVEDATE", paramEntityItem, "FIRSTANNDATE", i);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  658 */       addDebug("\ncheckAvails checking firstorder ps avails ");
/*      */       
/*  660 */       for (b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++) {
/*  661 */         EntityItem entityItem = entityGroup1.getEntityItem(b1);
/*  662 */         Vector vector5 = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "AVAIL");
/*  663 */         Vector vector6 = PokUtils.getEntitiesWithMatchedAttr(vector5, "AVAILTYPE", "143");
/*  664 */         Vector vector7 = PokUtils.getEntitiesWithMatchedAttr(vector5, "AVAILTYPE", "146");
/*      */         
/*  666 */         Vector vector8 = PokUtils.getEntitiesWithMatchedAttr(vector5, "AVAILTYPE", "171");
/*  667 */         addDebug("checkAvails for " + entityItem.getKey() + " psplannedAvailVct " + vector7.size() + " psmesplannedAvailVct " + vector8.size() + " psfoAvailVct " + vector6
/*  668 */             .size());
/*  669 */         if (vector6.size() > 0) {
/*  670 */           Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  671 */           boolean bool1 = getAvailByOSN(hashtable1, vector7, true, 3);
/*      */           
/*  673 */           Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  674 */           boolean bool2 = getAvailByOSN(hashtable2, vector8, true, 3);
/*      */           
/*  676 */           Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*  677 */           boolean bool3 = getAvailByOSN(hashtable3, vector6, true, 3);
/*  678 */           addDebug("checkAvails " + entityItem.getKey() + " foOsnErrors " + bool3 + " foAvailOSNTbl.keys " + hashtable3
/*  679 */               .keySet() + " plaOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable1
/*  680 */               .keySet() + " mesplaOsnErrors " + bool2 + " mesplaAvailOSNTbl.keys " + hashtable2
/*  681 */               .keySet());
/*      */ 
/*      */ 
/*      */           
/*  685 */           if (!bool1 && !bool3)
/*      */           {
/*  687 */             checkAvailCtryByOSN(hashtable3, hashtable1, "MISSING_PLA_OSNCTRY_ERR", entityItem, true, i);
/*      */           }
/*  689 */           if (vector8 != null && vector8.size() > 0 && !bool2 && !bool3)
/*      */           {
/*  691 */             checkAvailCtryByOSN(hashtable3, hashtable2, "MISSING_PLA_OSNCTRY_ERR", entityItem, true, i);
/*      */           }
/*  693 */           hashtable1.clear();
/*  694 */           hashtable2.clear();
/*  695 */           hashtable3.clear();
/*      */         } 
/*  697 */         vector5.clear();
/*  698 */         vector6.clear();
/*  699 */         vector7.clear();
/*  700 */         vector8.clear();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  707 */     if (str1.length() > 0) {
/*      */       
/*  709 */       Vector<EntityItem> vector5 = new Vector<>(vector);
/*      */       
/*  711 */       removeNonRFAAVAIL(vector5);
/*      */       
/*  713 */       Vector<EntityItem> vector6 = PokUtils.getAllLinkedEntities(vector5, "AVAILANNA", "ANNOUNCEMENT");
/*      */       
/*  715 */       vector6 = PokUtils.getEntitiesWithMatchedAttr(vector6, "ANNTYPE", "19");
/*  716 */       addDebug("checkAvails any foavail NEW annVct " + vector6.size());
/*  717 */       for (byte b1 = 0; b1 < vector6.size(); b1++) {
/*  718 */         EntityItem entityItem = vector6.elementAt(b1);
/*  719 */         checkCanNotBeEarlier(entityItem, "ANNDATE", paramEntityItem, "FIRSTANNDATE", i);
/*      */       } 
/*  721 */       vector6.clear();
/*  722 */       vector5.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  728 */     addHeading(3, entityGroup2.getLongDescription() + " Last Order Avail Checks:");
/*  729 */     checkPsLastOrderOrMesLastOrderAvail("149", vector2, entityGroup1, paramEntityItem, str2, i);
/*      */     
/*  731 */     addHeading(3, entityGroup2.getLongDescription() + " MES Last Order Avail Checks:");
/*  732 */     checkPsLastOrderOrMesLastOrderAvail("172", vector3, entityGroup1, paramEntityItem, str2, i);
/*      */ 
/*      */ 
/*      */     
/*  736 */     addHeading(3, entityGroup2.getLongDescription() + " End of Marketing Avail Checks:");
/*      */     
/*  738 */     addDebug("\ncheckAvails checking eom ps avails");
/*      */     byte b;
/*  740 */     for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/*  741 */       EntityItem entityItem1 = entityGroup1.getEntityItem(b);
/*  742 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "MODEL");
/*  743 */       int j = getCheckLevel(i, entityItem2, "ANNDATE");
/*      */       
/*  745 */       Vector vector5 = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "AVAIL");
/*  746 */       Vector vector6 = PokUtils.getEntitiesWithMatchedAttr(vector5, "AVAILTYPE", "146");
/*  747 */       Vector vector7 = PokUtils.getEntitiesWithMatchedAttr(vector5, "AVAILTYPE", "171");
/*      */       
/*  749 */       Vector<EntityItem> vector8 = PokUtils.getEntitiesWithMatchedAttr(vector5, "AVAILTYPE", "200");
/*      */ 
/*      */       
/*  752 */       addDebug("checkAvails " + entityItem1.getKey() + " all avail: " + vector5.size() + " plaAvail: " + vector6
/*  753 */           .size() + " mesplaAvail: " + vector7.size() + " eomAvail: " + vector8.size());
/*      */       
/*  755 */       if (vector8.size() > 0) {
/*      */         
/*  757 */         for (byte b1 = 0; b1 < vector8.size(); b1++) {
/*  758 */           EntityItem entityItem = vector8.elementAt(b1);
/*      */           
/*  760 */           checkCanNotBeLater(entityItem1, entityItem, "EFFECTIVEDATE", paramEntityItem, "WITHDRAWANNDATE_T", i);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  765 */           String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/*  766 */           addDebug("checkAvails " + entityItem.getKey() + " availAnntypeFlag " + str);
/*  767 */           if (str == null) {
/*  768 */             str = "RFA";
/*      */           }
/*  770 */           if ("RFA".equals(str)) {
/*      */             
/*  772 */             Vector<EntityItem> vector9 = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*  773 */             addDebug("checkAvails EOM " + entityItem.getKey() + " annVct " + vector9.size());
/*  774 */             for (byte b2 = 0; b2 < vector9.size(); b2++) {
/*  775 */               EntityItem entityItem3 = vector9.elementAt(b2);
/*      */               
/*  777 */               String str3 = PokUtils.getAttributeFlagValue(entityItem3, "ANNTYPE");
/*  778 */               addDebug("checkAvails " + entityItem3.getKey() + " anntypeFlag " + str3);
/*      */ 
/*      */               
/*  781 */               if (!"14".equals(str3)) {
/*      */                 
/*  783 */                 this.args[0] = getLD_NDN(entityItem);
/*  784 */                 this.args[1] = getLD_NDN(entityItem3);
/*  785 */                 createMessage(4, "MUST_NOT_BE_IN_ERR2", this.args);
/*      */               
/*      */               }
/*      */               else {
/*      */                 
/*  790 */                 checkCanNotBeLater(entityItem3, "ANNDATE", paramEntityItem, "WITHDRAWANNDATE_T", i);
/*      */               } 
/*  792 */             }  vector9.clear();
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  797 */         Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  798 */         boolean bool1 = getAvailByOSN(hashtable1, vector6, true, 3);
/*      */         
/*  800 */         Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  801 */         boolean bool2 = getAvailByOSN(hashtable2, vector7, true, 3);
/*  802 */         Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*  803 */         boolean bool3 = getAvailByOSN(hashtable3, vector8, true, 3);
/*  804 */         addDebug("checkAvails " + entityItem1.getKey() + " plaOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable1
/*  805 */             .keySet() + " mesplaOsnErrors " + bool1 + " mesplaAvailOSNTbl.keys " + hashtable1
/*  806 */             .keySet() + " eomOsnErrors " + bool3 + " eomAvailOSNTbl.keys " + hashtable3
/*  807 */             .keySet());
/*      */ 
/*      */         
/*  810 */         if (!bool1 && !bool3)
/*      */         {
/*  812 */           checkAvailCtryByOSN(hashtable3, hashtable1, "MISSING_PLA_OSNCTRY_ERR", entityItem1, true, j);
/*      */         }
/*  814 */         if (vector7 != null && vector7.size() > 0 && !bool2 && !bool3)
/*      */         {
/*  816 */           checkAvailCtryByOSN(hashtable3, hashtable2, "MISSING_PLA_OSNCTRY_ERR", entityItem1, true, j);
/*      */         }
/*  818 */         hashtable1.clear();
/*  819 */         hashtable2.clear();
/*  820 */         hashtable3.clear();
/*      */       } 
/*  822 */       vector5.clear();
/*  823 */       vector6.clear();
/*  824 */       vector7.clear();
/*  825 */       vector8.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  831 */     addHeading(3, entityGroup2.getLongDescription() + " End of Service Avail Checks:");
/*  832 */     addDebug("\ncheckAvails checking eos ps avails ");
/*      */     
/*  834 */     for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/*  835 */       EntityItem entityItem1 = entityGroup1.getEntityItem(b);
/*  836 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "MODEL");
/*  837 */       int j = getCheckLevel(i, entityItem2, "ANNDATE");
/*      */       
/*  839 */       Vector vector5 = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "AVAIL");
/*  840 */       Vector vector6 = PokUtils.getEntitiesWithMatchedAttr(vector5, "AVAILTYPE", "149");
/*  841 */       Vector vector7 = PokUtils.getEntitiesWithMatchedAttr(vector5, "AVAILTYPE", "172");
/*      */       
/*  843 */       Vector vector8 = PokUtils.getEntitiesWithMatchedAttr(vector5, "AVAILTYPE", "151");
/*      */ 
/*      */       
/*  846 */       addDebug("checkAvails " + entityItem1.getKey() + " all avail: " + vector5.size() + " loAvail: " + vector6
/*  847 */           .size() + " mesloAvail: " + vector7.size() + " eosAvail: " + vector8.size());
/*      */ 
/*      */       
/*  850 */       if (vector8.size() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  912 */         Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  913 */         boolean bool1 = getAvailByOSN(hashtable1, vector6, true, 3);
/*      */         
/*  915 */         Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  916 */         boolean bool2 = getAvailByOSN(hashtable2, vector7, true, 3);
/*      */         
/*  918 */         Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*  919 */         boolean bool3 = getAvailByOSN(hashtable3, vector8, true, 3);
/*  920 */         addDebug("checkAvails " + entityItem1.getKey() + " loOsnErrors " + bool1 + " loAvailOSNTbl.keys " + hashtable1
/*  921 */             .keySet() + " mesloOsnErrors " + bool2 + " mesloAvailOSNTbl.keys " + hashtable2
/*  922 */             .keySet() + " eosOsnErrors " + bool3 + " eosAvailOSNTbl.keys " + hashtable3
/*  923 */             .keySet());
/*      */         
/*  925 */         if (!bool1 && !bool3) {
/*      */           
/*  927 */           checkAvailDatesByCtryByOSN(hashtable3, hashtable1, entityItem1, 1, j, "", false);
/*      */ 
/*      */           
/*  930 */           checkAvailCtryByOSN(hashtable3, hashtable1, "MISSING_LO_OSNCTRY_ERR", entityItem1, true, j);
/*      */         } 
/*  932 */         if (vector7 != null && vector7.size() > 0 && !bool2 && !bool3) {
/*      */           
/*  934 */           checkAvailDatesByCtryByOSN(hashtable3, hashtable2, entityItem1, 1, j, "", false);
/*      */ 
/*      */           
/*  937 */           checkAvailCtryByOSN(hashtable3, hashtable2, "MISSING_LO_OSNCTRY_ERR", entityItem1, true, j);
/*      */         } 
/*  939 */         hashtable1.clear();
/*  940 */         hashtable2.clear();
/*  941 */         hashtable3.clear();
/*      */       } 
/*      */       
/*  944 */       vector5.clear();
/*  945 */       vector6.clear();
/*  946 */       vector7.clear();
/*  947 */       vector8.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  952 */     vector.clear();
/*  953 */     arrayList.clear();
/*  954 */     vector2.clear();
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
/*      */   private void checkPsLastOrderOrMesLastOrderAvail(String paramString1, Vector<?> paramVector, EntityGroup paramEntityGroup, EntityItem paramEntityItem, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/*  968 */     addDebug("\ncheckAvails checking lastorder ps avails");
/*      */     
/*  970 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*  971 */       EntityItem entityItem1 = paramEntityGroup.getEntityItem(b);
/*      */       
/*  973 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "MODEL");
/*  974 */       int i = getCheckLevel(paramInt, entityItem2, "ANNDATE");
/*      */       
/*  976 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "AVAIL");
/*      */ 
/*      */ 
/*      */       
/*  980 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "146");
/*  981 */       Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", "171");
/*      */       
/*  983 */       Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(vector1, "AVAILTYPE", paramString1);
/*  984 */       ArrayList arrayList = getCountriesAsList(vector2, paramInt);
/*      */       
/*  986 */       addDebug("checkAvails " + entityItem1.getKey() + " " + entityItem2.getKey() + " all avail: " + vector1.size() + " avail type: " + paramString1 + " plaAvail: " + vector2
/*  987 */           .size() + " loAvail: " + vector4.size() + " mesplaAvail: " + vector3.size() + " plaAvlCtry:" + arrayList);
/*      */ 
/*      */       
/*  990 */       checkPsAvailCWithAvailA(vector4, vector2, entityItem1, paramEntityItem, paramInt, i);
/*  991 */       if ("172".equals(paramString1))
/*  992 */         checkPsAvailCWithAvailA(vector4, vector3, entityItem1, paramEntityItem, paramInt, i); 
/*  993 */       checkPsAvailCWithFeatureW(vector4, entityItem1, paramEntityItem, paramInt);
/*      */       
/*  995 */       vector1.clear();
/*  996 */       vector2.clear();
/*  997 */       arrayList.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1003 */     if (paramString2.length() > 0) {
/*      */       
/* 1005 */       Vector vector = new Vector(paramVector);
/*      */       
/* 1007 */       removeNonRFAAVAIL(vector);
/*      */       
/* 1009 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(vector, "AVAILANNA", "ANNOUNCEMENT");
/* 1010 */       addDebug("checkAvails annVct " + vector1.size());
/* 1011 */       vector1 = PokUtils.getEntitiesWithMatchedAttr(vector1, "ANNTYPE", "14");
/* 1012 */       addDebug("checkAvails EOL annVct " + vector1.size());
/* 1013 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1014 */         EntityItem entityItem = vector1.elementAt(b1);
/* 1015 */         checkCanNotBeLater(entityItem, "ANNDATE", paramEntityItem, "WITHDRAWANNDATE_T", paramInt);
/*      */       } 
/* 1017 */       vector1.clear();
/*      */       
/* 1019 */       vector.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkPsAvailCWithAvailA(Vector paramVector1, Vector paramVector2, EntityItem paramEntityItem1, EntityItem paramEntityItem2, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 1024 */     if (paramVector1.size() > 0) {
/* 1025 */       Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 1026 */       boolean bool1 = getAvailByOSN(hashtable1, paramVector2, true, 3);
/* 1027 */       addDebug("checkAvails " + paramEntityItem1.getKey() + " plaOsnErrors " + bool1 + " plaAvailOSNTbl.keys " + hashtable1
/* 1028 */           .keySet());
/*      */       
/* 1030 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/* 1031 */       boolean bool2 = getAvailByOSN(hashtable2, paramVector1, true, 3);
/* 1032 */       addDebug("checkAvails " + paramEntityItem1.getKey() + " loOsnErrors " + bool2 + " loAvailOSNTbl.keys " + hashtable2
/* 1033 */           .keySet());
/*      */ 
/*      */       
/* 1036 */       if (!bool1 && !bool2)
/*      */       {
/* 1038 */         checkAvailCtryByOSN(hashtable2, hashtable1, "MISSING_PLA_OSNCTRY_ERR", paramEntityItem1, true, paramInt2);
/*      */       }
/* 1040 */       hashtable1.clear();
/* 1041 */       hashtable2.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkPsAvailCWithFeatureW(Vector<EntityItem> paramVector, EntityItem paramEntityItem1, EntityItem paramEntityItem2, int paramInt) throws SQLException, MiddlewareException {
/* 1046 */     if (paramVector.size() > 0)
/*      */     {
/* 1048 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 1049 */         EntityItem entityItem = paramVector.elementAt(b);
/*      */ 
/*      */         
/* 1052 */         checkCanNotBeLater(paramEntityItem1, entityItem, "EFFECTIVEDATE", paramEntityItem2, "WITHDRAWDATEEFF_T", paramInt);
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
/*      */   private void checkPsPlanOrMesPlanAvail(Vector<EntityItem> paramVector, EntityItem paramEntityItem, ArrayList paramArrayList, String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 1073 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1074 */       EntityItem entityItem1 = paramVector.elementAt(b);
/* 1075 */       EntityItem entityItem2 = getAvailPS(entityItem1, "OOFAVAIL");
/*      */       
/* 1077 */       checkAvailCtryInEntity(entityItem2, entityItem1, paramEntityItem, paramArrayList, paramInt);
/*      */ 
/*      */ 
/*      */       
/* 1081 */       checkCanNotBeEarlier(entityItem2, entityItem1, "EFFECTIVEDATE", paramEntityItem, "FIRSTANNDATE", paramInt);
/*      */     } 
/*      */     
/* 1084 */     if (paramString.length() > 0) {
/*      */       
/* 1086 */       Vector<EntityItem> vector1 = new Vector<>(paramVector);
/*      */       
/* 1088 */       removeNonRFAAVAIL(vector1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1094 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(vector1, "AVAILANNA", "ANNOUNCEMENT");
/* 1095 */       addDebug("checkAvails all annVct " + vector2.size());
/* 1096 */       vector2 = PokUtils.getEntitiesWithMatchedAttr(vector2, "ANNTYPE", "19");
/* 1097 */       addDebug("checkAvails PLA NEW annVct " + vector2.size());
/* 1098 */       for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 1099 */         EntityItem entityItem = vector2.elementAt(b1);
/* 1100 */         checkCanNotBeEarlier(entityItem, "ANNDATE", paramEntityItem, "FIRSTANNDATE", paramInt);
/*      */       } 
/* 1102 */       vector2.clear();
/* 1103 */       vector1.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1114 */     return "FEATURE ABR.";
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
/*      */   public String getABRVersion() {
/* 1126 */     return "1.23";
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
/*      */   private void setBHInvnameHW(EntityItem paramEntityItem) throws Exception {
/* 1156 */     boolean bool = true;
/*      */     
/* 1158 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", ", ", "", false);
/* 1159 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "BHINVNAME", ", ", null, false);
/* 1160 */     String str3 = PokUtils.getAttributeValue(paramEntityItem, "INVNAME", ", ", null, false);
/* 1161 */     String str4 = getAttributeFlagEnabledValue(paramEntityItem, "INVENTORYGROUP");
/*      */     
/* 1163 */     int i = 254;
/*      */     
/* 1165 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("BHINVNAME");
/* 1166 */     if (eANMetaAttribute != null) {
/* 1167 */       i = eANMetaAttribute.getMaxLen();
/*      */     }
/*      */     
/* 1170 */     addDebug("setBHInvnameHW checking " + paramEntityItem.getKey() + " fcode: " + str1 + " bhinvname: " + str2 + " invname: " + str3 + " invgrp: " + str4 + " maxLen: " + i);
/*      */ 
/*      */     
/* 1173 */     if (str3 == null) {
/*      */       
/* 1175 */       this.args[0] = "";
/* 1176 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "INVNAME", "INVNAME");
/* 1177 */       createMessage(4, "REQ_NOTPOPULATED_ERR", this.args);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1187 */     if (str2 != null) {
/*      */       
/* 1189 */       String str5 = getTimestamp(paramEntityItem, "INVNAME");
/*      */       
/* 1191 */       String str6 = getTimestamp(paramEntityItem, "BHINVNAME");
/* 1192 */       addDebug("setBHInvnameHW invnameDts: " + str5 + " bhinvnameDts: " + str6);
/* 1193 */       bool = (str6.compareTo(str5) < 0) ? true : false;
/*      */     } 
/*      */     
/* 1196 */     if (bool) {
/*      */       
/* 1198 */       EntityItem[] arrayOfEntityItem = searchForFeature(str4, str3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1205 */       if (arrayOfEntityItem == null || arrayOfEntityItem.length == 1) {
/* 1206 */         str2 = str3;
/*      */       } else {
/* 1208 */         str2 = str1 + "-" + str3;
/*      */       } 
/*      */       
/* 1211 */       addDebug("setBHInvnameHW derived bhinvname: " + str2);
/*      */       
/* 1213 */       setTextValue(this.m_elist.getProfile(), "BHINVNAME", str2, paramEntityItem);
/*      */     } 
/*      */ 
/*      */     
/* 1217 */     if (str2.length() > i) {
/*      */ 
/*      */ 
/*      */       
/* 1221 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "BHINVNAME", "BHINVNAME");
/* 1222 */       this.args[1] = "" + i;
/* 1223 */       this.args[2] = str2;
/* 1224 */       createMessage(3, "DERIVED_LEN_ERR", this.args);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeAttrBeforeCommit(ReturnEntityKey paramReturnEntityKey) {
/* 1232 */     Attribute attribute = null;
/* 1233 */     for (byte b = 0; b < paramReturnEntityKey.m_vctAttributes.size(); b++) {
/* 1234 */       Attribute attribute1 = paramReturnEntityKey.m_vctAttributes.elementAt(b);
/* 1235 */       if (attribute1.getAttributeCode().equals("BHINVNAME")) {
/* 1236 */         attribute = attribute1;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1240 */     paramReturnEntityKey.m_vctAttributes.clear();
/*      */     
/* 1242 */     if (attribute != null) {
/* 1243 */       paramReturnEntityKey.m_vctAttributes.addElement(attribute);
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
/*      */   private EntityItem[] searchForFeature(String paramString1, String paramString2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 1260 */     Vector<String> vector1 = new Vector(2);
/* 1261 */     Vector<String> vector2 = new Vector(2);
/* 1262 */     vector1.addElement("INVNAME");
/* 1263 */     vector1.addElement("INVENTORYGROUP");
/*      */     
/* 1265 */     vector2.addElement(paramString2);
/* 1266 */     vector2.addElement(paramString1);
/*      */     
/* 1268 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/* 1270 */       StringBuffer stringBuffer = new StringBuffer();
/* 1271 */       addDebug("searchForFeature using attrs: " + vector1 + " values: " + vector2);
/* 1272 */       arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), this.m_elist.getProfile(), "SRDFEATURE9", "FEATURE", false, vector1, vector2, stringBuffer);
/*      */       
/* 1274 */       if (stringBuffer.length() > 0) {
/* 1275 */         addDebug(stringBuffer.toString());
/*      */       }
/* 1277 */     } catch (SBRException sBRException) {
/*      */       
/* 1279 */       StringWriter stringWriter = new StringWriter();
/* 1280 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 1281 */       addDebug("searchForFeature SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/*      */     
/* 1284 */     if (arrayOfEntityItem != null) {
/* 1285 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1286 */         addDebug("searchForFeature found " + arrayOfEntityItem[b].getKey());
/*      */       }
/*      */     }
/*      */     
/* 1290 */     vector1.clear();
/* 1291 */     vector2.clear();
/* 1292 */     return arrayOfEntityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkChars(char[] paramArrayOfchar, String paramString) {
/* 1302 */     for (byte b = 0; b < paramArrayOfchar.length; b++) {
/* 1303 */       if (paramString.indexOf(paramArrayOfchar[b]) >= 0) {
/* 1304 */         return true;
/*      */       }
/*      */     } 
/* 1307 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\FCABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */