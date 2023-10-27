/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.eannounce.objects.TestPDGII;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PRODSTRUCTABRSTATUS
/*      */   extends DQABRSTATUS
/*      */ {
/*      */   private static final String NOT_READY = "EPNOT";
/*  142 */   private Object[] args = new Object[8];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  148 */     return true;
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
/*      */   protected void doAlreadyFinalProcessing() {
/*  162 */     setFlagValue(this.m_elist.getProfile(), "SAPLABRSTATUS", "0020");
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
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  194 */     setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020");
/*      */ 
/*      */     
/*  197 */     EntityItem entityItem1 = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  199 */     String str1 = getAttributeFlagEnabledValue(entityItem1, "EPIMSSTATUS");
/*  200 */     if (str1 == null || str1.length() == 0) {
/*  201 */       str1 = "EPNOT";
/*      */     }
/*  203 */     addDebug("completeNowFinalProcessing:: EPIMSSTATUS " + str1);
/*  204 */     if (str1.equals("EPNOT")) {
/*  205 */       setFlagValue(this.m_elist.getProfile(), "EPIMSABRSTATUS", "0020");
/*      */     }
/*      */     
/*  208 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FEATURE");
/*  209 */     EntityItem entityItem2 = entityGroup.getEntityItem(0);
/*  210 */     String str2 = getAttributeFlagEnabledValue(entityItem2, "FCTYPE");
/*  211 */     addDebug(entityItem2.getKey() + " FCTYPE " + str2);
/*  212 */     if ("120".equals(str2) || "130".equals(str2) || "402".equals(str2)) {
/*  213 */       addDebug(entityItem2.getKey() + " is RPQ or placeholder");
/*      */       
/*  215 */       deriveEXTRACTRPQ(entityItem2);
/*      */       
/*  217 */       setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020");
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  222 */       entityGroup = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/*  223 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  224 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  225 */         String str3 = getAttributeFlagEnabledValue(entityItem, "ANNSTATUS");
/*  226 */         String str4 = getAttributeFlagEnabledValue(entityItem, "ANNTYPE");
/*  227 */         addDebug(entityItem.getKey() + " status " + str3 + " type " + str4);
/*  228 */         if (str3 == null || str3.length() == 0) {
/*  229 */           str3 = "0020";
/*      */         }
/*  231 */         if ("0020".equals(str3) && "19".equals(str4)) {
/*  232 */           addDebug(entityItem.getKey() + " is Final and New");
/*  233 */           setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", "0020", entityItem);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  288 */     checkAllFeatures();
/*      */     
/*  290 */     if ("0040".equals(paramString)) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  295 */       checkStatus("MODEL");
/*      */ 
/*      */ 
/*      */       
/*  299 */       checkStatus("FEATURE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  319 */       checkRPQAvails(paramEntityItem);
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
/*      */   private void checkRPQAvails(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  331 */     HashSet<String> hashSet = new HashSet();
/*      */     
/*  333 */     hashSet.add("120");
/*  334 */     hashSet.add("130");
/*  335 */     hashSet.add("402");
/*      */     
/*  337 */     EntityItem entityItem = this.m_elist.getEntityGroup("FEATURE").getEntityItem(0);
/*  338 */     addDebug("checkRPQAvails checking " + entityItem.getKey());
/*      */ 
/*      */     
/*  341 */     if (!PokUtils.contains(entityItem, "FCTYPE", hashSet)) {
/*  342 */       addDebug("checkRPQAvails " + entityItem.getKey() + " is NOT an RPQ");
/*      */ 
/*      */       
/*  345 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */ 
/*      */       
/*  348 */       String str = getAttributeFlagEnabledValue(paramEntityItem, "SAPL");
/*  349 */       addDebug("checkRPQAvails checking entity: " + paramEntityItem.getKey() + " sapl " + str);
/*  350 */       HashSet<String> hashSet1 = new HashSet();
/*  351 */       hashSet1.add("10");
/*  352 */       hashSet1.add("90");
/*      */       
/*  354 */       if (PokUtils.contains(paramEntityItem, "SAPL", hashSet1)) {
/*  355 */         addDebug("checkRPQAvails " + paramEntityItem.getKey() + " has SAPL of 90 or 10");
/*      */ 
/*      */ 
/*      */         
/*  359 */         Vector<EntityItem> vector = new Vector(1);
/*  360 */         for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  361 */           EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*  362 */           addDebug("checkRPQAvails looking for planned avail; checking: " + entityItem1.getKey());
/*  363 */           if (PokUtils.isSelected(entityItem1, "AVAILTYPE", "146")) {
/*  364 */             vector.add(entityItem1);
/*      */           }
/*      */         } 
/*  367 */         if (vector.size() == 0)
/*      */         {
/*      */           
/*  370 */           addError("NO_PLANNEDAVAIL_ERR", (Object[])null);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  375 */         checkMaxAnnDate(paramEntityItem, entityGroup);
/*      */ 
/*      */ 
/*      */         
/*  379 */         checkMinWDate(paramEntityItem, entityGroup);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  385 */         checkLastOrderWDCountries(paramEntityItem, entityGroup);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  390 */         checkPlannedAvailWDCountries(entityGroup);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  395 */         if (vector.size() > 0) {
/*  396 */           checkCountry(entityItem, vector);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  402 */         checkAvailDates(entityGroup);
/*      */         
/*  404 */         vector.clear();
/*      */       } else {
/*      */         
/*  407 */         addDebug("checkRPQAvails " + paramEntityItem.getKey() + " is not SAPL of 90 or 10");
/*      */       } 
/*      */     } else {
/*  410 */       addDebug("checkRPQAvails " + entityItem.getKey() + " is an RPQ");
/*      */     } 
/*      */     
/*  413 */     hashSet.clear();
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
/*      */   private void checkCountry(EntityItem paramEntityItem, Vector<EntityItem> paramVector) throws SQLException, MiddlewareException {
/*  425 */     HashSet<String> hashSet = new HashSet();
/*  426 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("COUNTRYLIST");
/*  427 */     addDebug("checkCountry " + paramEntityItem.getKey() + " COUNTRYLIST " + 
/*  428 */         PokUtils.getAttributeFlagValue(paramEntityItem, "COUNTRYLIST") + NEWLINE);
/*  429 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/*  431 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  432 */       for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */         
/*  434 */         if (arrayOfMetaFlag[b1].isSelected()) {
/*  435 */           hashSet.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  440 */     for (byte b = 0; b < paramVector.size(); b++) {
/*  441 */       EntityItem entityItem = paramVector.elementAt(b);
/*  442 */       eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  443 */       addDebug("checkCountry " + entityItem.getKey() + " COUNTRYLIST " + 
/*  444 */           PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + NEWLINE);
/*  445 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/*  447 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  448 */         for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */           
/*  450 */           if (arrayOfMetaFlag[b1].isSelected()) {
/*  451 */             addDebug("checkCountry " + entityItem.getKey() + " COUNTRYLIST " + arrayOfMetaFlag[b1].getFlagCode());
/*  452 */             if (!hashSet.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/*      */ 
/*      */               
/*  455 */               this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  456 */               this.args[1] = getNavigationName(entityItem);
/*  457 */               this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/*  458 */               this.args[3] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*  459 */               addError("NO_CTRY_MATCH", this.args);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  467 */     hashSet.clear();
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
/*      */   private void checkAvailDates(EntityGroup paramEntityGroup) throws SQLException, MiddlewareException {
/*  479 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*  480 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  481 */       String str1 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/*  482 */       String str2 = getAttributeFlagEnabledValue(entityItem, "AVAILTYPE");
/*  483 */       addDebug("checkAvailDates " + entityItem.getKey() + " EFFECTIVEDATE: " + str1 + " AVAILTYPE: " + str2);
/*  484 */       if (("146".equals(str2) || "143".equals(str2)) && str1
/*  485 */         .length() > 0) {
/*  486 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/*  487 */         addDebug("checkAvailDates " + entityItem.getKey() + " annVct: " + vector.size());
/*  488 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  489 */           EntityItem entityItem1 = vector.elementAt(b1);
/*  490 */           String str = PokUtils.getAttributeValue(entityItem1, "ANNDATE", ", ", "", false);
/*  491 */           addDebug("checkAvailDates " + entityItem1.getKey() + " annDate: " + str);
/*  492 */           if (str.length() > 0 && str1.compareTo(str) < 0) {
/*      */ 
/*      */             
/*  495 */             this.args[0] = entityItem.getEntityGroup().getLongDescription() + " " + getNavigationName(entityItem);
/*  496 */             this.args[1] = entityItem1.getEntityGroup().getLongDescription();
/*  497 */             this.args[2] = getNavigationName(entityItem1);
/*  498 */             this.args[3] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "ANNDATE", "ANNDATE");
/*  499 */             addError("EARLY_DATE_ERR", this.args);
/*      */           } 
/*      */         } 
/*  502 */         vector.clear();
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
/*      */   private void checkMaxAnnDate(EntityItem paramEntityItem, EntityGroup paramEntityGroup) throws SQLException, MiddlewareException {
/*  515 */     Vector<String> vector = new Vector(3);
/*  516 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", ", ", null, false);
/*  517 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*  518 */     String str2 = PokUtils.getAttributeValue(entityItem1, "ANNDATE", ", ", null, false);
/*  519 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("FEATURE").getEntityItem(0);
/*  520 */     String str3 = PokUtils.getAttributeValue(entityItem2, "FIRSTANNDATE", ", ", null, false);
/*  521 */     addDebug("checkMaxAnnDate " + entityItem2.getKey() + " (" + str3 + ") " + paramEntityItem.getKey() + " (" + str1 + ") " + entityItem1
/*  522 */         .getKey() + " (" + str2 + ")");
/*      */     
/*  524 */     if (str1 != null) {
/*  525 */       vector.add(str1);
/*      */     }
/*  527 */     if (str2 != null) {
/*  528 */       vector.add(str2);
/*      */     }
/*  530 */     if (str3 != null) {
/*  531 */       vector.add(str3);
/*      */     }
/*      */     
/*  534 */     if (vector.size() > 0) {
/*  535 */       Collections.sort(vector);
/*  536 */       String str = vector.lastElement().toString();
/*  537 */       vector.clear();
/*  538 */       addDebug("checkMaxAnnDate looking for planned avail; before " + str);
/*      */       
/*  540 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*  541 */         EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  542 */         addDebug("checkMaxAnnDate looking for planned avail; checking: " + entityItem.getKey());
/*  543 */         if (PokUtils.isSelected(entityItem, "AVAILTYPE", "146")) {
/*  544 */           String str4 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/*  545 */           addDebug("checkMaxAnnDate plannedavail " + entityItem.getKey() + " EFFECTIVEDATE: " + str4);
/*  546 */           if (str4.length() > 0 && str.compareTo(str4) > 0) {
/*      */             
/*  548 */             this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/*  549 */             this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "ANNDATE", "ANNDATE");
/*  550 */             addError("EARLY_PLANNEDAVAIL_ERR", this.args);
/*      */           } 
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
/*      */   private void checkMinWDate(EntityItem paramEntityItem, EntityGroup paramEntityGroup) throws SQLException, MiddlewareException {
/*  565 */     Vector<String> vector = new Vector(3);
/*  566 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "WITHDRAWDATE", ", ", null, false);
/*  567 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*  568 */     String str2 = PokUtils.getAttributeValue(entityItem1, "WITHDRAWDATE", ", ", null, false);
/*  569 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("FEATURE").getEntityItem(0);
/*  570 */     String str3 = PokUtils.getAttributeValue(entityItem2, "WITHDRAWDATEEFF_T", ", ", null, false);
/*  571 */     addDebug("checkMinWDate " + entityItem2.getKey() + " (" + str3 + ") " + paramEntityItem.getKey() + " (" + str1 + ") " + entityItem1
/*  572 */         .getKey() + " (" + str2 + ")");
/*      */     
/*  574 */     if (str1 != null) {
/*  575 */       vector.add(str1);
/*      */     }
/*  577 */     if (str2 != null) {
/*  578 */       vector.add(str2);
/*      */     }
/*  580 */     if (str3 != null) {
/*  581 */       vector.add(str3);
/*      */     }
/*      */     
/*  584 */     if (vector.size() > 0) {
/*  585 */       Collections.sort(vector);
/*  586 */       String str = vector.firstElement().toString();
/*  587 */       vector.clear();
/*  588 */       addDebug("checkMinWDate looking for lastorder avail; after " + str);
/*      */       
/*  590 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*  591 */         EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  592 */         addDebug("checkMinWDate looking for lastorder avail; checking: " + entityItem.getKey());
/*  593 */         if (PokUtils.isSelected(entityItem, "AVAILTYPE", "149")) {
/*  594 */           String str4 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/*  595 */           addDebug("checkMinWDate lastorder " + entityItem.getKey() + " EFFECTIVEDATE: " + str4);
/*  596 */           if (str4.length() > 0 && str4.compareTo(str) > 0) {
/*      */             
/*  598 */             this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/*  599 */             this.args[1] = entityItem2.getEntityGroup().getLongDescription();
/*  600 */             addError("LATE_LASTORDERAVAIL_ERR", this.args);
/*      */           } 
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
/*      */   private void checkLastOrderWDCountries(EntityItem paramEntityItem, EntityGroup paramEntityGroup) throws SQLException, MiddlewareException {
/*  616 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "WITHDRAWDATE", ", ", null, false);
/*  617 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*  618 */     String str2 = PokUtils.getAttributeValue(entityItem1, "WITHDRAWDATE", ", ", null, false);
/*  619 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("FEATURE").getEntityItem(0);
/*  620 */     String str3 = PokUtils.getAttributeValue(entityItem2, "WITHDRAWDATEEFF_T", ", ", null, false);
/*  621 */     addDebug("checkLastOrderWDCountries " + entityItem2.getKey() + " (" + str3 + ") " + paramEntityItem.getKey() + " (" + str1 + ") " + entityItem1
/*  622 */         .getKey() + " (" + str2 + ")");
/*      */     
/*  624 */     if (str1 != null || str2 != null || str3 != null) {
/*  625 */       ArrayList<String> arrayList1 = new ArrayList();
/*  626 */       ArrayList<String> arrayList2 = new ArrayList();
/*      */       
/*  628 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*  629 */         EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  630 */         addDebug("checkLastOrderWDCountries checking avail: " + entityItem.getKey());
/*  631 */         if (PokUtils.isSelected(entityItem, "AVAILTYPE", "149")) {
/*  632 */           addDebug("checkLastOrderWDCountries lastorder " + entityItem.getKey());
/*  633 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  634 */           if (eANFlagAttribute != null) {
/*      */             
/*  636 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  637 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++)
/*      */             {
/*  639 */               if (arrayOfMetaFlag[b1].isSelected() && !arrayList1.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/*  640 */                 arrayList1.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */               }
/*      */             }
/*      */           
/*      */           } 
/*  645 */         } else if (PokUtils.isSelected(entityItem, "AVAILTYPE", "146")) {
/*  646 */           addDebug("checkLastOrderWDCountries plannedavail " + entityItem.getKey());
/*  647 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  648 */           if (eANFlagAttribute != null) {
/*      */             
/*  650 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  651 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */               
/*  653 */               if (arrayOfMetaFlag[b1].isSelected() && !arrayList2.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/*  654 */                 arrayList2.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  661 */       addDebug("checkLastOrderWDCountries all plannedavail countries " + arrayList2);
/*  662 */       addDebug("checkLastOrderWDCountries all lastorderavail countries " + arrayList1);
/*      */       
/*  664 */       if (!arrayList1.containsAll(arrayList2)) {
/*      */ 
/*      */         
/*  667 */         this.args[0] = paramEntityGroup.getLongDescription();
/*  668 */         addError("NO_LASTORDER_ERR", this.args);
/*      */       } 
/*  670 */       arrayList1.clear();
/*  671 */       arrayList2.clear();
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
/*      */   private void checkPlannedAvailWDCountries(EntityGroup paramEntityGroup) throws SQLException, MiddlewareException {
/*  684 */     ArrayList<String> arrayList = new ArrayList();
/*      */     
/*      */     byte b;
/*  687 */     for (b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*  688 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  689 */       addDebug("checkPlannedAvailWDCountries checking avail: " + entityItem.getKey());
/*  690 */       if (PokUtils.isSelected(entityItem, "AVAILTYPE", "146")) {
/*  691 */         addDebug("checkPlannedAvailWDCountries plannedavail " + entityItem.getKey());
/*  692 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  693 */         if (eANFlagAttribute != null) {
/*      */           
/*  695 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  696 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */             
/*  698 */             if (arrayOfMetaFlag[b1].isSelected() && !arrayList.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/*  699 */               arrayList.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  706 */     addDebug("checkPlannedAvailWDCountries all plannedavail countries " + arrayList);
/*      */     
/*  708 */     for (b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*  709 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  710 */       addDebug("checkPlannedAvailWDCountries checking avail: " + entityItem.getKey());
/*  711 */       if (PokUtils.isSelected(entityItem, "AVAILTYPE", "149")) {
/*  712 */         ArrayList<String> arrayList1 = new ArrayList();
/*  713 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  714 */         if (eANFlagAttribute != null) {
/*      */           
/*  716 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  717 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */             
/*  719 */             if (arrayOfMetaFlag[b1].isSelected() && !arrayList1.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/*  720 */               arrayList1.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */             }
/*      */           } 
/*      */         } 
/*  724 */         addDebug("checkPlannedAvailWDCountries all lastorder " + entityItem.getKey() + " countries " + arrayList1);
/*      */         
/*  726 */         if (!arrayList.containsAll(arrayList1)) {
/*      */ 
/*      */           
/*  729 */           this.args[0] = paramEntityGroup.getLongDescription();
/*  730 */           this.args[1] = getNavigationName(entityItem);
/*  731 */           addError("NO_PLANNEDAVAIL_ERR2", this.args);
/*      */         } 
/*  733 */         arrayList1.clear();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  738 */     arrayList.clear();
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
/*      */   protected void checkAllFeatures() throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  768 */     PDGUtility pDGUtility = new PDGUtility();
/*  769 */     EntityItem entityItem1 = this.m_elist.getEntityGroup("MODEL").getEntityItem(0);
/*  770 */     EntityItem entityItem2 = this.m_elist.getEntityGroup("FEATURE").getEntityItem(0);
/*      */     
/*  772 */     StringBuffer stringBuffer = new StringBuffer();
/*  773 */     stringBuffer.append("map_FEATURE:FEATURECODE=" + PokUtils.getAttributeValue(entityItem2, "FEATURECODE", ", ", "", false) + ";");
/*  774 */     stringBuffer.append("map_MODEL:MACHTYPEATR=" + getAttributeFlagEnabledValue(entityItem1, "MACHTYPEATR"));
/*      */     
/*  776 */     addDebug("checkAllFeatures using SRDPRODSTRUCTV searching for " + stringBuffer.toString());
/*      */ 
/*      */     
/*  779 */     EntityList entityList = pDGUtility.dynaSearchIIForEntityList(this.m_db, this.m_prof, null, "SRDPRODSTRUCTV", "PRODSTRUCT", stringBuffer
/*  780 */         .toString());
/*      */     
/*  782 */     addDebug("checkAllFeatures search results " + PokUtils.outputList(entityList));
/*      */     
/*  784 */     EntityGroup entityGroup = entityList.getEntityGroup("FEATURE");
/*  785 */     if (entityGroup.getEntityItemCount() > 1) {
/*  786 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  787 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */         
/*  791 */         this.args[0] = entityGroup.getLongDescription();
/*  792 */         this.args[1] = getNavigationName(entityItem);
/*  793 */         this.args[2] = PokUtils.getAttributeDescription(this.m_elist.getEntityGroup("MODEL"), "MACHTYPEATR", "MACHTYPEATR");
/*  794 */         addError("NOT_IDENTICAL_ERR", this.args);
/*      */       } 
/*      */     } else {
/*      */       
/*  798 */       EntityItem entityItem = entityGroup.getEntityItem(0);
/*  799 */       addDebug("checkAllFeatures featItem " + entityItem.getKey());
/*      */       
/*  801 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  802 */       for (byte b = 0; b < entityItem.getDownLinkCount(); b++) {
/*  803 */         EntityItem entityItem3 = (EntityItem)entityItem.getDownLink(b);
/*  804 */         for (byte b1 = 0; b1 < entityItem3.getDownLinkCount(); b1++) {
/*  805 */           EntityItem entityItem4 = (EntityItem)entityItem3.getDownLink(b1);
/*  806 */           addDebug("checkAllFeatures adding psitem " + entityItem3.getKey() + " to vct for mdlitem " + entityItem4.getKey());
/*  807 */           Vector<EntityItem> vector = (Vector)hashtable.get(entityItem4.getKey());
/*  808 */           if (vector == null) {
/*  809 */             vector = new Vector(1);
/*  810 */             hashtable.put(entityItem4.getKey(), vector);
/*      */           } 
/*  812 */           vector.add(entityItem3);
/*      */         } 
/*      */       } 
/*  815 */       for (Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/*  816 */         String str = enumeration.nextElement();
/*  817 */         Vector<EntityItem> vector = (Vector)hashtable.get(str);
/*  818 */         EntityItem entityItem3 = entityList.getEntityGroup("MODEL").getEntityItem(str);
/*  819 */         if (vector.size() > 1) {
/*  820 */           for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  821 */             EntityItem entityItem4 = vector.elementAt(b1);
/*  822 */             addDebug("checkAllFeatures mdlItem " + entityItem3.getKey() + " duplicate psitem " + entityItem4.getKey());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  827 */             this.args[0] = entityItem4.getEntityGroup().getLongDescription();
/*  828 */             this.args[1] = getTMFNavigationName(entityItem4, entityItem3, entityItem);
/*  829 */             this.args[2] = entityItem3.getEntityGroup().getLongDescription();
/*  830 */             addError("NOT_UNIQUE_ERR", this.args);
/*      */           } 
/*  832 */           vector.clear();
/*      */         } 
/*      */       } 
/*      */       
/*  836 */       hashtable.clear();
/*      */     } 
/*      */     
/*  839 */     entityList.dereference();
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
/*      */   private void deriveEXTRACTRPQ(EntityItem paramEntityItem) throws SQLException, MiddlewareException, MiddlewareRequestException {
/*      */     try {
/* 1005 */       OPICMList oPICMList = new OPICMList();
/* 1006 */       String str1 = "PDGtemplates/PRODSTRUCTABRSTATUS1.txt";
/* 1007 */       PDGUtility pDGUtility = new PDGUtility();
/*      */       
/* 1009 */       EntityItem entityItem1 = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1010 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/* 1011 */       EntityItem entityItem2 = entityGroup.getEntityItem(0);
/*      */       
/* 1013 */       oPICMList.put("TIMESTAMP", this.m_elist.getProfile().getValOn());
/* 1014 */       oPICMList.put("PRODSTRUCT", entityItem1);
/* 1015 */       oPICMList.put("MODEL", entityItem2);
/* 1016 */       oPICMList.put("FEATURE", paramEntityItem);
/* 1017 */       oPICMList.put("PRODID", entityItem1.getEntityID() + "");
/* 1018 */       oPICMList.put("FEATUREID", paramEntityItem.getEntityID() + "");
/* 1019 */       oPICMList.put("MODELID", entityItem2.getEntityID() + "");
/* 1020 */       String str2 = "00000000000000" + paramEntityItem.getEntityID();
/* 1021 */       int i = str2.length();
/* 1022 */       if (i > 15) {
/* 1023 */         str2 = str2.substring(i - 15);
/*      */       }
/* 1025 */       oPICMList.put("ANN", "RPQ" + str2);
/* 1026 */       this.m_prof = pDGUtility.setProfValOnEffOn(this.m_db, this.m_prof);
/* 1027 */       TestPDGII testPDGII = new TestPDGII(this.m_db, this.m_prof, null, oPICMList, str1);
/* 1028 */       StringBuffer stringBuffer = testPDGII.getMissingEntities();
/* 1029 */       addDebug("deriveEXTRACTRPQ " + paramEntityItem.getKey() + " " + entityItem1.getKey() + " " + entityItem2
/* 1030 */           .getKey() + " sbmissing: " + stringBuffer);
/* 1031 */       pDGUtility.putCreateAction("EXTRACTRPQ", "CREXTRACTRPQ1");
/* 1032 */       pDGUtility.putSearchAction("EXTRACTRPQ", "SRDEXTRACTRPQ1");
/* 1033 */       pDGUtility.generateData(this.m_db, this.m_prof, stringBuffer, entityItem1);
/* 1034 */     } catch (MiddlewareShutdownInProgressException middlewareShutdownInProgressException) {
/* 1035 */       middlewareShutdownInProgressException.printStackTrace(System.out);
/* 1036 */       throw new MiddlewareException(middlewareShutdownInProgressException.getMessage());
/* 1037 */     } catch (SBRException sBRException) {
/* 1038 */       sBRException.printStackTrace(System.out);
/* 1039 */       throw new MiddlewareException(sBRException.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1049 */     return "PRODSTRUCTABRSTATUS ABR";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/* 1059 */     return new String("1.31");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/* 1069 */     return "PRODSTRUCTABRSTATUS.java,v 1.31 2008/08/05 19:32:01 wendy Exp";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1079 */     return "PRODSTRUCTABRSTATUS.java";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\PRODSTRUCTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */