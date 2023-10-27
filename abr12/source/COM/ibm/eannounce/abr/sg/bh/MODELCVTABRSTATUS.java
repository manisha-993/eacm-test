/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.SQLException;
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
/*      */ public class MODELCVTABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*      */   private static final String MODEL_SRCHACTION_NAME = "SRDMODEL06";
/*      */   private static final String CHECK_ANNDATE = "2012-01-01";
/*      */   private static final String AVAIL_CHK_DATE = "2011-07-01";
/*      */   private static final String COFGRP_Base = "150";
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  107 */     return true;
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  145 */     if (doR10processing()) {
/*  146 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  148 */       String str = PokUtils.getAttributeValue(entityItem, "ANNDATE", "", "", false);
/*  149 */       addDebug("nowRFR: " + entityItem.getKey() + " annDate " + str);
/*  150 */       if (str.length() > 0 && str.compareTo("2012-01-01") > 0) {
/*  151 */         addDebug("nowRFR: " + entityItem.getKey() + " is after " + "2012-01-01");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  156 */         doRFR_ADSXML(entityItem);
/*      */       }
/*      */       else {
/*      */         
/*  160 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
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
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {
/*  185 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  186 */     addDebug(entityItem.getKey() + " status now final");
/*  187 */     if (doR10processing()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  206 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  212 */       setFlagValue(this.m_elist.getProfile(), "RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLCRFRWFName() {
/*  220 */     return "WFLCMDLCONRFR"; } protected String getLCFinalWFName() {
/*  221 */     return "WFLCMDLCONFINAL";
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
/*      */   protected void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception {
/*  290 */     addHeading(2, paramEntityItem.getEntityGroup().getLongDescription() + " Checks:");
/*      */     
/*  292 */     int i = getCheck_W_W_E(paramString);
/*      */ 
/*      */     
/*  295 */     checkCanNotBeEarlier(paramEntityItem, "WITHDRAWDATE", "ANNDATE", i);
/*      */ 
/*      */ 
/*      */     
/*  299 */     checkCanNotBeEarlier(paramEntityItem, "WTHDRWEFFCTVDATE", "WITHDRAWDATE", i);
/*      */ 
/*      */     
/*  302 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/*  304 */     Vector vector1 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "149");
/*  305 */     Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "172");
/*      */     
/*  307 */     Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "146");
/*  308 */     Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "171");
/*      */     
/*  310 */     Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "143");
/*      */     
/*  312 */     addDebug("doDQChecking lastOrderAvailVct " + vector1.size() + " plannedAvailVct " + vector3
/*  313 */         .size());
/*      */     
/*  315 */     Hashtable hashtable1 = getAvailByCountry(vector3, i);
/*  316 */     addDebug("doDQChecking plAvailCtryTbl: " + hashtable1.keySet());
/*  317 */     Hashtable hashtable2 = getAvailByCountry(vector4, i);
/*  318 */     addDebug("doDQChecking mesPlAvailCtryTbl: " + hashtable2.keySet());
/*      */ 
/*      */ 
/*      */     
/*  322 */     addHeading(3, entityGroup.getLongDescription() + " Planned Avail Checks:");
/*  323 */     checkMdlCvtMesPlaAndPlaAvail(paramEntityItem, vector3, "146", paramString);
/*      */     
/*  325 */     addHeading(3, entityGroup.getLongDescription() + " MES Planned Avail Checks:");
/*  326 */     checkMdlCvtMesPlaAndPlaAvail(paramEntityItem, vector4, "171", paramString);
/*      */     
/*  328 */     addHeading(3, entityGroup.getLongDescription() + " First Order Avail Checks:");
/*  329 */     checkMdlCvtFirstOrderAvail(paramEntityItem, vector5, vector3, vector4, paramString);
/*      */     
/*  331 */     addHeading(3, entityGroup.getLongDescription() + " Last Order Avail Checks:");
/*  332 */     checkMdlCvtMesLoAndLoAvail(paramEntityItem, vector1, "149", hashtable1, paramString);
/*      */     
/*  334 */     addHeading(3, entityGroup.getLongDescription() + " MES Last Order Avail Checks:");
/*  335 */     checkMdlCvtMesLoAndLoAvail(paramEntityItem, vector2, "172", hashtable1, paramString);
/*  336 */     checkMdlCvtMesLoAndLoAvail(paramEntityItem, vector2, "172", hashtable2, paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  341 */     checkModelAvails(paramEntityItem, vector3, vector4, vector1, vector2, i);
/*      */     
/*  343 */     vector3.clear();
/*  344 */     vector1.clear();
/*  345 */     hashtable1.clear();
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
/*      */   private void checkModelAvails(EntityItem paramEntityItem, Vector<EntityItem> paramVector1, Vector<EntityItem> paramVector2, Vector<EntityItem> paramVector3, Vector<EntityItem> paramVector4, int paramInt) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  385 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", "", false);
/*  386 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", "", "", false);
/*      */     
/*  388 */     int i = getCheckLevel(paramInt, paramEntityItem, "ANNDATE");
/*      */ 
/*      */     
/*  391 */     EntityItem entityItem = searchForModel(str1, str2);
/*  392 */     addDebug("checkModelAvails machtype: " + str1 + " modelatr " + str2);
/*  393 */     if (entityItem != null) {
/*  394 */       EntityList entityList = getModelVE(entityItem);
/*      */ 
/*      */       
/*  397 */       EntityGroup entityGroup = entityList.getEntityGroup("AVAIL");
/*  398 */       if (entityGroup == null) {
/*  399 */         throw new MiddlewareException("AVAIL is missing from extract for " + entityList.getParentActionItem().getActionItemKey());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  408 */       Vector vector1 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "146");
/*  409 */       addDebug("checkModelAvails " + entityItem.getKey() + " mdlPlaAvailVct " + vector1.size());
/*      */       
/*  411 */       addHeading(3, entityGroup.getLongDescription() + " Model Planned Avail Checks:");
/*  412 */       if (paramVector1.size() > 0) {
/*  413 */         Hashtable hashtable = getAvailByCountry(vector1, paramInt);
/*  414 */         addDebug("checkModelAvails  mdlPlaAvailCtryTbl " + hashtable.keySet());
/*  415 */         for (byte b1 = 0; b1 < paramVector1.size(); b1++) {
/*  416 */           EntityItem entityItem1 = paramVector1.elementAt(b1);
/*  417 */           Vector<EntityItem> vector3 = new Vector();
/*      */           
/*  419 */           String str3 = checkCtryMismatch(entityItem1, hashtable, vector3, paramInt);
/*      */           
/*  421 */           String str4 = getAttrValueAndCheckLvl(entityItem1, "EFFECTIVEDATE", paramInt);
/*      */           
/*  423 */           for (byte b2 = 0; b2 < vector3.size(); b2++) {
/*  424 */             EntityItem entityItem2 = vector3.elementAt(b2);
/*      */             
/*  426 */             String str = getAttrValueAndCheckLvl(entityItem2, "EFFECTIVEDATE", paramInt);
/*  427 */             addDebug("checkModelAvails  model plannedavail: " + entityItem2
/*  428 */                 .getKey() + " EFFECTIVEDATE:" + str + " mdlcvt-plannedavail:" + entityItem1
/*  429 */                 .getKey() + " EFFECTIVEDATE:" + str4);
/*  430 */             boolean bool = checkDates(str, str4, 2);
/*  431 */             if (!bool) {
/*      */ 
/*      */               
/*  434 */               this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  435 */               this.args[1] = PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", "", false);
/*  436 */               this.args[2] = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", "", "", false);
/*  437 */               this.args[3] = getLD_NDN(entityItem2);
/*  438 */               this.args[4] = paramEntityItem.getEntityGroup().getLongDescription();
/*  439 */               this.args[5] = getLD_NDN(entityItem1);
/*  440 */               createMessage(paramInt, "MDLCVT_PLA_DATE_ERR", this.args);
/*      */             } 
/*      */           } 
/*  443 */           vector3.clear();
/*  444 */           if (str3.length() > 0) {
/*  445 */             addDebug("checkModelAvails mdlcvt plannedavail:" + entityItem1.getKey() + " COUNTRYLIST had ctry [" + str3 + "] that were not in any plannedavail MODELAVAIL");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  450 */             this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  451 */             this.args[1] = PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", "", false);
/*  452 */             this.args[2] = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", "", "", false);
/*  453 */             createMessage(i, "MDLCVT_PLANNEDAVAIL_ERR", this.args);
/*      */           } 
/*      */         } 
/*  456 */         hashtable.clear();
/*      */       } 
/*  458 */       if (paramVector2.size() > 0) {
/*  459 */         Hashtable hashtable = getAvailByCountry(vector1, paramInt);
/*  460 */         addDebug("checkModelAvails  mdlPlaAvailCtryTbl " + hashtable.keySet());
/*  461 */         for (byte b1 = 0; b1 < paramVector2.size(); b1++) {
/*  462 */           EntityItem entityItem1 = paramVector2.elementAt(b1);
/*  463 */           Vector<EntityItem> vector3 = new Vector();
/*      */           
/*  465 */           String str3 = checkCtryMismatch(entityItem1, hashtable, vector3, paramInt);
/*      */           
/*  467 */           String str4 = getAttrValueAndCheckLvl(entityItem1, "EFFECTIVEDATE", paramInt);
/*      */           
/*  469 */           for (byte b2 = 0; b2 < vector3.size(); b2++) {
/*  470 */             EntityItem entityItem2 = vector3.elementAt(b2);
/*      */             
/*  472 */             String str = getAttrValueAndCheckLvl(entityItem2, "EFFECTIVEDATE", paramInt);
/*  473 */             addDebug("checkModelAvails  model plannedavail: " + entityItem2
/*  474 */                 .getKey() + " EFFECTIVEDATE:" + str + " mdlcvt-mesplannedavail:" + entityItem1
/*  475 */                 .getKey() + " EFFECTIVEDATE:" + str4);
/*  476 */             boolean bool = checkDates(str, str4, 2);
/*  477 */             if (!bool) {
/*      */ 
/*      */               
/*  480 */               this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  481 */               this.args[1] = PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", "", false);
/*  482 */               this.args[2] = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", "", "", false);
/*  483 */               this.args[3] = getLD_NDN(entityItem2);
/*  484 */               this.args[4] = paramEntityItem.getEntityGroup().getLongDescription();
/*  485 */               this.args[5] = getLD_NDN(entityItem1);
/*  486 */               createMessage(paramInt, "MDLCVT_PLA_DATE_ERR", this.args);
/*      */             } 
/*      */           } 
/*  489 */           vector3.clear();
/*  490 */           if (str3.length() > 0) {
/*  491 */             addDebug("checkModelAvails mdlcvt mesplannedavail:" + entityItem1.getKey() + " COUNTRYLIST had ctry [" + str3 + "] that were not in any plannedavail MODELAVAIL");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  496 */             this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  497 */             this.args[1] = PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", "", false);
/*  498 */             this.args[2] = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", "", "", false);
/*  499 */             createMessage(i, "MDLCVT_PLANNEDAVAIL_ERR", this.args);
/*      */           } 
/*      */         } 
/*  502 */         hashtable.clear();
/*      */       } 
/*  504 */       vector1.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  515 */       Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "149");
/*  516 */       addDebug("checkModelAvails  mdlLoAvailVct " + vector2.size());
/*  517 */       addHeading(3, entityGroup.getLongDescription() + " Model Last Order Avail Checks:");
/*      */       
/*  519 */       if (paramVector3.size() > 0) {
/*  520 */         Hashtable hashtable = getAvailByCountry(vector2, paramInt);
/*  521 */         addDebug("checkModelAvails  mdlLoAvailCtryTbl " + hashtable.keySet());
/*  522 */         for (byte b1 = 0; b1 < paramVector3.size(); b1++) {
/*  523 */           EntityItem entityItem1 = paramVector3.elementAt(b1);
/*  524 */           Vector<EntityItem> vector3 = new Vector();
/*      */           
/*  526 */           String str3 = checkCtryMismatch(entityItem1, hashtable, vector3, paramInt);
/*  527 */           String str4 = getAttrValueAndCheckLvl(entityItem1, "EFFECTIVEDATE", paramInt);
/*      */           
/*  529 */           for (byte b2 = 0; b2 < vector3.size(); b2++) {
/*  530 */             EntityItem entityItem2 = vector3.elementAt(b2);
/*      */             
/*  532 */             String str = getAttrValueAndCheckLvl(entityItem2, "EFFECTIVEDATE", paramInt);
/*  533 */             addDebug("checkModelAvails  model lastorderavail: " + entityItem2
/*  534 */                 .getKey() + " EFFECTIVEDATE:" + str + " mdlcvt-lastorderavail:" + entityItem1
/*  535 */                 .getKey() + " EFFECTIVEDATE:" + str4);
/*  536 */             boolean bool = checkDates(str, str4, 1);
/*  537 */             if (!bool) {
/*      */ 
/*      */               
/*  540 */               this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  541 */               this.args[1] = PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", "", false);
/*  542 */               this.args[2] = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", "", "", false);
/*  543 */               this.args[3] = getLD_NDN(entityItem2);
/*  544 */               this.args[4] = paramEntityItem.getEntityGroup().getLongDescription();
/*  545 */               this.args[5] = getLD_NDN(entityItem1);
/*  546 */               createMessage(paramInt, "MDLCVT_LO_DATE_ERR", this.args);
/*      */             } 
/*      */           } 
/*  549 */           vector3.clear();
/*  550 */           if (str3.length() > 0) {
/*  551 */             addDebug("checkModelAvails mdlcvt lastorderavail:" + entityItem1.getKey() + " COUNTRYLIST had ctry [" + str3 + "] that were not in any lastorderavail MODELAVAIL");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  556 */             this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  557 */             this.args[1] = PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", "", false);
/*  558 */             this.args[2] = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", "", "", false);
/*  559 */             this.args[3] = getLD_NDN(entityItem1);
/*  560 */             this.args[4] = paramEntityItem.getEntityGroup().getLongDescription();
/*  561 */             createMessage(i, "MDLCVT_LASTORDERAVAIL_ERR", this.args);
/*      */           } 
/*      */         } 
/*  564 */         hashtable.clear();
/*      */       } 
/*  566 */       if (paramVector4.size() > 0) {
/*  567 */         Hashtable hashtable = getAvailByCountry(vector2, paramInt);
/*  568 */         addDebug("checkModelAvails  mdlLoAvailCtryTbl " + hashtable.keySet());
/*  569 */         for (byte b1 = 0; b1 < paramVector4.size(); b1++) {
/*  570 */           EntityItem entityItem1 = paramVector4.elementAt(b1);
/*  571 */           Vector<EntityItem> vector3 = new Vector();
/*      */           
/*  573 */           String str3 = checkCtryMismatch(entityItem1, hashtable, vector3, paramInt);
/*  574 */           String str4 = getAttrValueAndCheckLvl(entityItem1, "EFFECTIVEDATE", paramInt);
/*      */           
/*  576 */           for (byte b2 = 0; b2 < vector3.size(); b2++) {
/*  577 */             EntityItem entityItem2 = vector3.elementAt(b2);
/*      */             
/*  579 */             String str = getAttrValueAndCheckLvl(entityItem2, "EFFECTIVEDATE", paramInt);
/*  580 */             addDebug("checkModelAvails  model lastorderavail: " + entityItem2
/*  581 */                 .getKey() + " EFFECTIVEDATE:" + str + " mdlcvt-meslastorderavail:" + entityItem1
/*  582 */                 .getKey() + " EFFECTIVEDATE:" + str4);
/*  583 */             boolean bool = checkDates(str, str4, 1);
/*  584 */             if (!bool) {
/*      */ 
/*      */               
/*  587 */               this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  588 */               this.args[1] = PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", "", false);
/*  589 */               this.args[2] = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", "", "", false);
/*  590 */               this.args[3] = getLD_NDN(entityItem2);
/*  591 */               this.args[4] = paramEntityItem.getEntityGroup().getLongDescription();
/*  592 */               this.args[5] = getLD_NDN(entityItem1);
/*  593 */               createMessage(paramInt, "MDLCVT_LO_DATE_ERR", this.args);
/*      */             } 
/*      */           } 
/*  596 */           vector3.clear();
/*  597 */           if (str3.length() > 0) {
/*  598 */             addDebug("checkModelAvails mdlcvt meslastorderavail:" + entityItem1.getKey() + " COUNTRYLIST had ctry [" + str3 + "] that were not in any lastorderavail MODELAVAIL");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  603 */             this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  604 */             this.args[1] = PokUtils.getAttributeValue(paramEntityItem, "TOMACHTYPE", "", "", false);
/*  605 */             this.args[2] = PokUtils.getAttributeValue(paramEntityItem, "TOMODEL", "", "", false);
/*  606 */             this.args[3] = getLD_NDN(entityItem1);
/*  607 */             this.args[4] = paramEntityItem.getEntityGroup().getLongDescription();
/*  608 */             createMessage(i, "MDLCVT_LASTORDERAVAIL_ERR", this.args);
/*      */           } 
/*      */         } 
/*  611 */         hashtable.clear();
/*      */       } 
/*  613 */       vector2.clear();
/*      */ 
/*      */       
/*  616 */       Vector<EntityItem> vector = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "200");
/*  617 */       addDebug("checkModelAvails  mdlEomAvailVct " + vector.size());
/*  618 */       addHeading(3, entityGroup.getLongDescription() + " Model End of Marketing Avail Checks:");
/*  619 */       for (byte b = 0; b < vector.size(); b++) {
/*  620 */         EntityItem entityItem1 = vector.elementAt(b);
/*      */         
/*  622 */         checkCanNotBeLater(entityItem, entityItem1, "EFFECTIVEDATE", paramEntityItem, "WITHDRAWDATE", paramInt);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  627 */       addHeading(3, "Model Planned Avail Checks were not done because a MODEL was not found matching  TOMACHTYPE:" + str1 + " and TOMODEL:" + str2);
/*      */ 
/*      */       
/*  630 */       addDebug("checkModelAvails modelitem was not found");
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
/*      */   private EntityList getModelVE(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  643 */     String str = "DQVEMODELAVAIL";
/*      */     
/*  645 */     EntityList entityList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/*  646 */           .getProfile(), str), new EntityItem[] { new EntityItem(null, this.m_elist
/*  647 */             .getProfile(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID()) });
/*  648 */     addDebug("getModelVE:: Extract " + str + NEWLINE + PokUtils.outputList(entityList));
/*      */     
/*  650 */     return entityList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkMdlCvtMesLoAndLoAvail(EntityItem paramEntityItem, Vector<EntityItem> paramVector, String paramString1, Hashtable paramHashtable, String paramString2) throws SQLException, MiddlewareException {
/*  961 */     int i = getCheck_W_W_E(paramString2);
/*  962 */     int j = getCheckLevel(3, paramEntityItem, "ANNDATE");
/*  963 */     String str = PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "1980-01-01", false);
/*      */     
/*  965 */     addDebug("checkMdlCvtMesLoAndLoAvail lastOrderAvailVct " + paramVector.size() + " " + paramEntityItem.getKey() + " ANNDATE " + str);
/*      */     
/*      */     byte b;
/*      */     
/*  969 */     for (b = 0; b < paramVector.size(); b++) {
/*  970 */       EntityItem entityItem = paramVector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  975 */       int k = doCheck_W_N_N(paramString2);
/*  976 */       if (k != -1) {
/*  977 */         checkCanNotBeLater(entityItem, "EFFECTIVEDATE", paramEntityItem, "WTHDRWEFFCTVDATE", k);
/*      */       }
/*  979 */       else if (b == 0) {
/*  980 */         addDebug("checkMdlCvtMesLoAndLoAvail bypassing MODELCONVERT lastorder and WTHDRWEFFCTVDATE date check because status is:" + paramString2);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  986 */       k = doCheck_N_W_E(paramString2);
/*  987 */       if (k != -1) {
/*  988 */         k = getCheckLevel(k, paramEntityItem, "ANNDATE");
/*  989 */         Vector<EntityItem> vector = new Vector();
/*  990 */         checkCtryMismatch(entityItem, paramHashtable, vector, k);
/*  991 */         String str2 = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", k);
/*  992 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  993 */           EntityItem entityItem1 = vector.elementAt(b1);
/*      */           
/*  995 */           String str3 = getAttrValueAndCheckLvl(entityItem1, "EFFECTIVEDATE", k);
/*  996 */           addDebug("checkMdlCvtMesLoAndLoAvail plannedavail: " + entityItem1.getKey() + " EFFECTIVEDATE:" + str3 + " lastorder:" + entityItem
/*  997 */               .getKey() + " EFFECTIVEDATE:" + str2);
/*  998 */           boolean bool = checkDates(str2, str3, 1);
/*  999 */           if (!bool) {
/*      */ 
/*      */             
/* 1002 */             this.args[0] = getLD_NDN(entityItem);
/* 1003 */             this.args[1] = getLD_Value(entityItem, "EFFECTIVEDATE");
/* 1004 */             this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/* 1005 */             this.args[3] = getLD_NDN(entityItem1);
/* 1006 */             createMessage(k, "CANNOT_BE_EARLIER_ERR2", this.args);
/*      */           } 
/*      */         } 
/* 1009 */         vector.clear();
/*      */       }
/* 1011 */       else if (b == 0) {
/* 1012 */         addDebug("checkMdlCvtMesLoAndLoAvail bypassing MODELCONVERT lastorder and planned avail date check because status is:" + paramString2);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1018 */       String str1 = checkCtryMismatch(entityItem, paramHashtable, i);
/* 1019 */       if (str1.length() > 0) {
/* 1020 */         addDebug("checkMdlCvtMesLoAndLoAvail loavail " + entityItem.getKey() + " COUNTRYLIST had extra [" + str1 + "]");
/*      */ 
/*      */ 
/*      */         
/* 1024 */         this.args[0] = getLD_NDN(entityItem);
/* 1025 */         this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 1026 */         this.args[2] = str1;
/* 1027 */         if (paramString1.equals("149")) {
/* 1028 */           createMessage(i, "MDLCVT_MISSING_PLA_CTRY_ERR", this.args);
/*      */         } else {
/* 1030 */           createMessage(i, "MISSING_MES_PLA_CTRY_ERR", this.args);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1036 */     if ("2011-07-01".compareTo(str) <= 0) {
/* 1037 */       for (b = 0; b < paramVector.size(); b++) {
/* 1038 */         EntityItem entityItem = paramVector.elementAt(b);
/* 1039 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 1040 */         addDebug("checkMdlCvtMesLoAndLoAvail loavail " + entityItem.getKey() + " availAnntypeFlag " + str1);
/* 1041 */         if (str1 == null) {
/* 1042 */           str1 = "RFA";
/*      */         }
/*      */         
/* 1045 */         if ("RFA".equals(str1)) {
/*      */           
/* 1047 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 1048 */           addDebug("checkMdlCvtMesLoAndLoAvail rfa " + entityItem.getKey() + " annVct " + vector.size());
/* 1049 */           if (vector.size() != 0)
/*      */           {
/* 1051 */             for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 1052 */               EntityItem entityItem1 = vector.elementAt(b1);
/* 1053 */               checkCanNotBeLater(entityItem1, "ANNDATE", paramEntityItem, "WITHDRAWDATE", getCheck_W_W_E(paramString2));
/*      */             } 
/* 1055 */             vector.clear();
/*      */           }
/*      */           else
/*      */           {
/* 1059 */             this.args[0] = getLD_NDN(entityItem);
/* 1060 */             this.args[1] = this.m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
/* 1061 */             createMessage(j, "MUST_BE_IN_ERR", this.args);
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1068 */       addDebug("checkMdlCvtMesLoAndLoAvail LO " + paramEntityItem.getKey() + " ANNDATE " + str + " is not greater than " + "2011-07-01");
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
/*      */   private void checkMdlCvtFirstOrderAvail(EntityItem paramEntityItem, Vector<EntityItem> paramVector1, Vector paramVector2, Vector paramVector3, String paramString) throws SQLException, MiddlewareException {
/* 1094 */     int i = getCheck_W_W_E(paramString);
/* 1095 */     int j = getCheckLevel(3, paramEntityItem, "ANNDATE");
/* 1096 */     String str = PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "1980-01-01", false);
/*      */     
/* 1098 */     addDebug("checkMdlCvtFirstOrderAvail  firstOrderAvailVct " + paramVector1.size() + " " + paramEntityItem.getKey() + " ANNDATE " + str);
/*      */     
/*      */     byte b;
/*      */     
/* 1102 */     for (b = 0; b < paramVector1.size(); b++) {
/* 1103 */       EntityItem entityItem = paramVector1.elementAt(b);
/* 1104 */       if (i != -1) {
/*      */         
/* 1106 */         checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "ANNDATE", i);
/*      */       }
/* 1108 */       else if (b == 0) {
/* 1109 */         addDebug("checkMdlCvtFirstOrderAvail bypassing MODELCONVERT firstorder and ANNDATE date check because status is:" + paramString);
/*      */       } 
/*      */ 
/*      */       
/* 1113 */       Hashtable hashtable = getAvailByCountry(paramVector2, i);
/*      */       
/* 1115 */       String str1 = checkCtryMismatch(entityItem, hashtable, i);
/* 1116 */       if (str1.length() > 0) {
/* 1117 */         addDebug("checkMdlCvtFirstOrderAvail foavail " + entityItem.getKey() + " COUNTRYLIST had extra [" + str1 + "]");
/*      */ 
/*      */ 
/*      */         
/* 1121 */         this.args[0] = getLD_NDN(entityItem);
/* 1122 */         this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 1123 */         this.args[2] = str1;
/* 1124 */         createMessage(i, "MDLCVT_MISSING_PLA_CTRY_ERR", this.args);
/*      */       } 
/*      */ 
/*      */       
/* 1128 */       if (paramVector3 != null && paramVector3.size() > 0) {
/* 1129 */         Hashtable hashtable1 = getAvailByCountry(paramVector3, i);
/*      */         
/* 1131 */         str1 = checkCtryMismatch(entityItem, hashtable1, i);
/* 1132 */         if (str1.length() > 0) {
/* 1133 */           addDebug("checkMdlCvtFirstOrderAvail foavail " + entityItem.getKey() + " COUNTRYLIST had extra [" + str1 + "]");
/*      */ 
/*      */ 
/*      */           
/* 1137 */           this.args[0] = getLD_NDN(entityItem);
/* 1138 */           this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 1139 */           this.args[2] = str1;
/* 1140 */           createMessage(i, "MISSING_MES_PLA_CTRY_ERR", this.args);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1146 */     if ("2011-07-01".compareTo(str) <= 0) {
/* 1147 */       for (b = 0; b < paramVector1.size(); b++) {
/* 1148 */         EntityItem entityItem = paramVector1.elementAt(b);
/* 1149 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 1150 */         addDebug("checkMdlCvtFirstOrderAvail fo " + entityItem.getKey() + " availAnntypeFlag " + str1);
/* 1151 */         if (str1 == null) {
/* 1152 */           str1 = "RFA";
/*      */         }
/*      */         
/* 1155 */         if ("RFA".equals(str1)) {
/*      */           
/* 1157 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 1158 */           addDebug("checkMdlCvtFirstOrderAvail" + entityItem.getKey() + " annVct " + vector.size());
/* 1159 */           if (vector.size() != 0)
/*      */           {
/* 1161 */             for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 1162 */               EntityItem entityItem1 = vector.elementAt(b1);
/* 1163 */               checkCanNotBeLater(entityItem1, "ANNDATE", paramEntityItem, "GENAVAILDATE", i);
/*      */             } 
/* 1165 */             vector.clear();
/*      */           }
/*      */           else
/*      */           {
/* 1169 */             this.args[0] = getLD_NDN(entityItem);
/* 1170 */             this.args[1] = this.m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
/* 1171 */             createMessage(j, "MUST_BE_IN_ERR", this.args);
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1178 */       addDebug("checkMdlCvtFirstOrderAvail FO " + paramEntityItem.getKey() + " ANNDATE " + str + " is not greater than " + "2011-07-01");
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
/*      */   private void checkMdlCvtMesPlaAndPlaAvail(EntityItem paramEntityItem, Vector<EntityItem> paramVector, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 1215 */     int i = getCheck_W_W_E(paramString2);
/* 1216 */     String str = PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "1980-01-01", false);
/*      */     
/* 1218 */     addDebug("checkMdlCvtMesPlaAndPlaAvail " + paramEntityItem.getKey() + " ANNDATE " + str);
/*      */     
/* 1220 */     int j = getCheckLevel(3, paramEntityItem, "ANNDATE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1226 */     if (paramString1.equals("146")) {
/* 1227 */       checkPlannedAvailsExist(paramVector, 3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1235 */     if (paramString1.equals("146")) {
/* 1236 */       checkPlannedAvailsStatus(paramVector, paramEntityItem, j);
/*      */     }
/*      */     
/* 1239 */     if (i != -1) {
/* 1240 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 1241 */         EntityItem entityItem = paramVector.elementAt(b);
/* 1242 */         checkCanNotBeEarlier(entityItem, "EFFECTIVEDATE", paramEntityItem, "GENAVAILDATE", i);
/*      */       } 
/*      */     } else {
/* 1245 */       addDebug("checkMdlCvtMesPlaAndPlaAvail bypassing MODELCONVERT plannedavail and GENAVAILDATE date check because status is:" + paramString2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1251 */     if ("2011-07-01".compareTo(str) <= 0) {
/* 1252 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 1253 */         EntityItem entityItem = paramVector.elementAt(b);
/* 1254 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 1255 */         addDebug("checkMdlCvtMesPlaAndPlaAvail pla " + entityItem.getKey() + " availAnntypeFlag " + str1);
/* 1256 */         if (str1 == null) {
/* 1257 */           str1 = "RFA";
/*      */         }
/*      */         
/* 1260 */         if ("RFA".equals(str1)) {
/*      */           
/* 1262 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 1263 */           addDebug("checkMdlCvtMesPlaAndPlaAvail rfa " + entityItem.getKey() + " annVct " + vector.size());
/* 1264 */           if (vector.size() != 0) {
/*      */             
/* 1266 */             for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 1267 */               EntityItem entityItem1 = vector.elementAt(b1);
/* 1268 */               checkCanNotBeEarlier(entityItem1, "ANNDATE", paramEntityItem, "ANNDATE", i);
/*      */             } 
/* 1270 */             vector.clear();
/*      */           }
/*      */           else {
/*      */             
/* 1274 */             this.args[0] = getLD_NDN(entityItem);
/* 1275 */             this.args[1] = this.m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
/* 1276 */             createMessage(j, "MUST_BE_IN_ERR", this.args);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1282 */       addDebug("checkMdlCvtMesPlaAndPlaAvail PLA " + paramEntityItem.getKey() + " ANNDATE " + str + " is not greater than " + "2011-07-01");
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
/*      */   private EntityItem searchForModel(String paramString1, String paramString2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 1301 */     addDebug("searchForModel entered machtype " + paramString1 + " modelatr " + paramString2);
/* 1302 */     EntityItem entityItem = null;
/* 1303 */     Vector<String> vector1 = new Vector(1);
/* 1304 */     vector1.addElement("MACHTYPEATR");
/* 1305 */     vector1.addElement("MODELATR");
/* 1306 */     Vector<String> vector2 = new Vector(1);
/* 1307 */     vector2.addElement(paramString1);
/* 1308 */     vector2.addElement(paramString2);
/*      */     
/* 1310 */     EntityItem[] arrayOfEntityItem = null;
/*      */     try {
/* 1312 */       StringBuffer stringBuffer = new StringBuffer();
/* 1313 */       arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), this.m_prof, "SRDMODEL06", "MODEL", false, vector1, vector2, stringBuffer);
/*      */       
/* 1315 */       if (stringBuffer.length() > 0) {
/* 1316 */         addDebug(stringBuffer.toString());
/*      */       }
/* 1318 */     } catch (SBRException sBRException) {
/*      */       
/* 1320 */       StringWriter stringWriter = new StringWriter();
/* 1321 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 1322 */       addDebug("searchForModel SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/* 1324 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*      */       
/* 1326 */       boolean bool = true;
/* 1327 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1328 */         EntityItem entityItem1 = arrayOfEntityItem[b];
/*      */         
/* 1330 */         String str = PokUtils.getAttributeFlagValue(entityItem1, "COFGRP");
/* 1331 */         addDebug("searchForModel found " + arrayOfEntityItem[b].getKey() + " cofgrp " + str);
/* 1332 */         if ("150".equals(str)) {
/* 1333 */           if (entityItem != null) {
/* 1334 */             bool = false;
/*      */             break;
/*      */           } 
/* 1337 */           entityItem = entityItem1;
/*      */         } 
/*      */       } 
/*      */       
/* 1341 */       if (!bool) {
/* 1342 */         StringBuffer stringBuffer = new StringBuffer();
/* 1343 */         stringBuffer.append("More than one MODEL with COFGRP=Base found for " + paramString1 + " " + paramString2);
/* 1344 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 1345 */           stringBuffer.append("<br />" + arrayOfEntityItem[b1].getKey() + ":" + arrayOfEntityItem[b1]);
/*      */         }
/* 1347 */         addError(stringBuffer.toString(), (Object[])null);
/*      */       } 
/*      */     } 
/* 1350 */     vector1.clear();
/* 1351 */     vector2.clear();
/* 1352 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getStatusAttrCode() {
/* 1358 */     return "STATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1367 */     return "MODELCONVERT ABR.";
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
/* 1378 */     return "1.12";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\MODELCVTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */