/*      */ package COM.ibm.eannounce.abr.util;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*      */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLAVAILElembh1
/*      */   extends XMLElem
/*      */ {
/*  224 */   private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLAVAILElembh1() {
/*  240 */     super("AVAILABILITYELEMENT");
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
/*      */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  267 */     String str1 = "D:MODELAVAIL:D:AVAIL:D:AVAILSLEORGA:D";
/*  268 */     String str2 = "1980-01-01-00.00.00.000000";
/*  269 */     boolean bool1 = true;
/*  270 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("AVAIL");
/*      */     
/*  272 */     boolean bool2 = false;
/*  273 */     boolean bool3 = false;
/*  274 */     bool2 = AvailUtil.iscompatmodel();
/*  275 */     if (!bool2) {
/*      */ 
/*      */ 
/*      */       
/*  279 */       String str = null;
/*  280 */       str = (String)paramHashtable.get("_chSTATUS");
/*  281 */       ABRUtil.append(paramStringBuffer, "the status is" + str + NEWLINE);
/*  282 */       if ("0020".equals(str)) {
/*  283 */         bool3 = true;
/*      */       } else {
/*  285 */         bool3 = false;
/*      */       } 
/*      */     } 
/*      */     
/*  289 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  290 */     boolean bool4 = isDerivefromModel(paramHashtable, paramDiffEntity, paramStringBuffer, true);
/*  291 */     if (bool4 == true) {
/*  292 */       EntityItem entityItem = paramDiffEntity.getPriorEntityItem();
/*      */ 
/*      */ 
/*      */       
/*  296 */       if (entityItem != null && str2.equals(entityItem.getProfile().getValOn())) {
/*  297 */         bool1 = false;
/*      */       }
/*  299 */       boolean bool = isDerivefromModel(paramHashtable, paramDiffEntity, paramStringBuffer, false);
/*  300 */       if (bool1 && bool) {
/*  301 */         buildDeleteCtry(treeMap, paramDatabase, paramDiffEntity, paramStringBuffer);
/*  302 */       } else if (bool1) {
/*  303 */         buildBHCatlgorRecs(paramHashtable, treeMap, true, paramStringBuffer);
/*  304 */         for (byte b = 0; b < vector.size(); b++) {
/*  305 */           DiffEntity diffEntity = vector.elementAt(b);
/*  306 */           buildCtryAudRecs(treeMap, diffEntity, true, paramStringBuffer);
/*      */         } 
/*      */       } 
/*  309 */       buildWorldWideCountryAud(treeMap, paramHashtable, paramDatabase, paramDocument, paramElement, paramDiffEntity, str1, paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  316 */     else if (vector.size() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  349 */       EntityItem entityItem = paramDiffEntity.getPriorEntityItem();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  354 */       if (entityItem != null && str2.equals(entityItem.getProfile().getValOn())) {
/*  355 */         bool1 = false;
/*      */       }
/*  357 */       boolean bool = isDerivefromModel(paramHashtable, paramDiffEntity, paramStringBuffer, false);
/*  358 */       if (bool1 && bool) {
/*      */ 
/*      */         
/*  361 */         buildDeleteCtry(treeMap, paramDatabase, paramDiffEntity, paramStringBuffer);
/*  362 */       } else if (bool1) {
/*  363 */         buildBHCatlgorRecs(paramHashtable, treeMap, true, paramStringBuffer);
/*  364 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  365 */           DiffEntity diffEntity = vector.elementAt(b1);
/*  366 */           buildCtryAudRecs(treeMap, diffEntity, true, paramStringBuffer);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  376 */       buildBHCatlgorRecs(paramHashtable, treeMap, false, paramStringBuffer);
/*  377 */       for (byte b = 0; b < vector.size(); b++) {
/*  378 */         DiffEntity diffEntity = vector.elementAt(b);
/*  379 */         buildCtryAudRecs(treeMap, diffEntity, false, paramStringBuffer);
/*      */       } 
/*      */     } else {
/*  382 */       ABRUtil.append(paramStringBuffer, "XMLAVAILElem.addElements no planned AVAILs found" + NEWLINE);
/*      */     } 
/*      */ 
/*      */     
/*  386 */     Vector[] arrayOfVector = null;
/*      */     
/*  388 */     Collection collection = treeMap.values();
/*  389 */     Iterator<CtryAudRecord> iterator = collection.iterator();
/*  390 */     Hashtable hashtable = null;
/*  391 */     while (iterator.hasNext()) {
/*  392 */       CtryAudRecord ctryAudRecord = iterator.next();
/*      */ 
/*      */       
/*  395 */       DiffEntity diffEntity1 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "146", "COUNTRYLIST", ctryAudRecord
/*  396 */           .getCountry(), false, paramStringBuffer);
/*  397 */       DiffEntity diffEntity2 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "146", "COUNTRYLIST", ctryAudRecord
/*  398 */           .getCountry(), true, paramStringBuffer);
/*      */       
/*  400 */       DiffEntity diffEntity3 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/*  401 */           .getCountry(), false, paramStringBuffer);
/*      */       
/*  403 */       DiffEntity diffEntity4 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  404 */           .getCountry(), false, paramStringBuffer);
/*  405 */       DiffEntity diffEntity5 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "201", "COUNTRYLIST", ctryAudRecord
/*  406 */           .getCountry(), false, paramStringBuffer);
/*  407 */       DiffEntity diffEntity6 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/*  408 */           .getCountry(), false, paramStringBuffer);
/*  409 */       DiffEntity diffEntity7 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRecord
/*  410 */           .getCountry(), false, paramStringBuffer);
/*      */       
/*  412 */       DiffEntity diffEntity8 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILANNTYPE", "EPIC", "COUNTRYLIST", ctryAudRecord
/*  413 */           .getCountry(), false, paramStringBuffer);
/*      */       
/*  415 */       DiffEntity diffEntity9 = getCatlgor(paramHashtable, arrayOfVector, ctryAudRecord.getCountry(), paramStringBuffer);
/*      */       
/*  417 */       DiffEntity[] arrayOfDiffEntity = new DiffEntity[2];
/*  418 */       arrayOfDiffEntity[0] = diffEntity2;
/*  419 */       arrayOfDiffEntity[1] = diffEntity1;
/*      */       
/*  421 */       ctryAudRecord.setAllFields(paramDiffEntity, diffEntity9, diffEntity3, diffEntity4, diffEntity5, diffEntity6, diffEntity7, diffEntity8, paramHashtable, arrayOfDiffEntity, str1, bool3, bool2, paramStringBuffer);
/*      */ 
/*      */       
/*  424 */       if (ctryAudRecord.isDisplayable() || ctryAudRecord.isrfrDisplayable()) {
/*      */         
/*  426 */         if (isPlannedAvail(ctryAudRecord.availDiff)) {
/*  427 */           createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity.getCurrentEntityItem(), ctryAudRecord, diffEntity1, str1, paramStringBuffer);
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/*  435 */           if (hashtable == null) {
/*  436 */             hashtable = searchSLORGNPLNTCODE(paramDatabase, paramDiffEntity.getCurrentEntityItem(), paramStringBuffer);
/*      */           }
/*  438 */           createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity.getCurrentEntityItem(), ctryAudRecord, paramDiffEntity, str1, hashtable, paramStringBuffer);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  443 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.addElements no changes found for " + ctryAudRecord
/*  444 */             .getKey() + NEWLINE);
/*      */       } 
/*  446 */       ctryAudRecord.dereference();
/*      */     } 
/*      */     
/*  449 */     if (hashtable != null) {
/*  450 */       hashtable.clear();
/*      */     }
/*      */     
/*  453 */     treeMap.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   private Hashtable searchSLORGNPLNTCODE(Database paramDatabase, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException {
/*  458 */     XMLSLEORGNPLNTCODESearchElem xMLSLEORGNPLNTCODESearchElem = new XMLSLEORGNPLNTCODESearchElem();
/*  459 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     try {
/*  461 */       hashtable = xMLSLEORGNPLNTCODESearchElem.doSearch(paramDatabase, paramEntityItem, paramStringBuffer);
/*  462 */     } catch (MiddlewareShutdownInProgressException middlewareShutdownInProgressException) {
/*  463 */       middlewareShutdownInProgressException.printStackTrace();
/*  464 */     } catch (SBRException sBRException) {
/*  465 */       StringWriter stringWriter = new StringWriter();
/*  466 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  467 */       ABRUtil.append(paramStringBuffer, "XMLAVAILElem.addElements search!! SBRException: " + stringWriter
/*  468 */           .getBuffer().toString() + NEWLINE);
/*  469 */       sBRException.printStackTrace();
/*      */     } 
/*  471 */     ABRUtil.append(paramStringBuffer, "XMLAVAILElem.addElements search!! " + NEWLINE);
/*      */     
/*  473 */     return hashtable;
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
/*      */   private void buildBHCatlgorRecs(Hashtable paramHashtable, TreeMap<String, CtryAudRecord> paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  489 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("BHCATLGOR");
/*  490 */     String str = "COUNTRYLIST";
/*  491 */     ABRUtil.append(paramStringBuffer, "buildBHCatlgorRecs looking for " + str + " in CATLGOR allVct.size:" + ((vector == null) ? "null" : ("" + vector
/*  492 */         .size())) + NEWLINE);
/*  493 */     if (vector != null) {
/*  494 */       if (paramBoolean)
/*  495 */       { for (byte b = 0; b < vector.size(); b++) {
/*  496 */           DiffEntity diffEntity = vector.elementAt(b);
/*  497 */           EntityItem entityItem = diffEntity.getPriorEntityItem();
/*      */           
/*  499 */           if (!diffEntity.isNew()) {
/*  500 */             String str1 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  501 */             if ("0020".equals(str1)) {
/*      */ 
/*      */               
/*  504 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(str);
/*  505 */               if (eANFlagAttribute != null) {
/*  506 */                 MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  507 */                 for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */                   
/*  509 */                   if (arrayOfMetaFlag[b1].isSelected()) {
/*  510 */                     String str2 = arrayOfMetaFlag[b1].getFlagCode();
/*  511 */                     String str3 = str2;
/*  512 */                     if (paramTreeMap.containsKey(str3)) {
/*      */                       
/*  514 */                       CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str3);
/*  515 */                       ABRUtil.append(paramStringBuffer, "WARNING buildBHCatlgorRecs for delete/udpate" + str3 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */                     } else {
/*      */                       
/*  518 */                       CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str2);
/*  519 */                       ctryAudRecord.setAction("Delete");
/*  520 */                       paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  521 */                       ABRUtil.append(paramStringBuffer, "buildBHCatlgorRecs for delete/udpate rec: " + ctryAudRecord
/*  522 */                           .getKey() + NEWLINE);
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }  }
/*  530 */       else { for (byte b = 0; b < vector.size(); b++) {
/*  531 */           DiffEntity diffEntity = vector.elementAt(b);
/*  532 */           EntityItem entityItem = diffEntity.getCurrentEntityItem();
/*      */           
/*  534 */           if (!diffEntity.isDeleted()) {
/*  535 */             String str1 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  536 */             if ("0020".equals(str1)) {
/*      */ 
/*      */               
/*  539 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(str);
/*  540 */               if (eANFlagAttribute != null) {
/*  541 */                 MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  542 */                 for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */                   
/*  544 */                   if (arrayOfMetaFlag[b1].isSelected()) {
/*  545 */                     String str2 = arrayOfMetaFlag[b1].getFlagCode();
/*  546 */                     String str3 = str2;
/*  547 */                     if (paramTreeMap.containsKey(str3)) {
/*  548 */                       CtryAudRecord ctryAudRecord = paramTreeMap.get(str3);
/*  549 */                       ctryAudRecord.setUpdateAvail((DiffEntity)null);
/*  550 */                       ctryAudRecord.setAction("@@");
/*  551 */                       ABRUtil.append(paramStringBuffer, "WARNING buildBHCatlgorRecs for new/update" + str3 + " already exists, replace orig with Update action" + ctryAudRecord + NEWLINE);
/*      */                     } else {
/*      */                       
/*  554 */                       CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str2);
/*      */                       
/*  556 */                       paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  557 */                       ABRUtil.append(paramStringBuffer, "buildBHCatlgorRecs for new/udpate rec: " + ctryAudRecord
/*  558 */                           .getKey() + NEWLINE);
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildDeleteCtry(TreeMap<String, CtryAudRecord> paramTreeMap, Database paramDatabase, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException {
/*  575 */     ReturnDataResultSet returnDataResultSet = null;
/*  576 */     ResultSet resultSet = null;
/*  577 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/*  578 */     String str = "SG";
/*      */     
/*  580 */     if (paramDiffEntity != null) {
/*  581 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*  582 */       if (entityItem != null) {
/*  583 */         str = entityItem.getProfile().getEnterprise();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  589 */       resultSet = paramDatabase.callGBL9999A(returnStatus, str, "GENAREASELECTION", "1999", "COUNTRYLIST");
/*  590 */       returnDataResultSet = new ReturnDataResultSet(resultSet);
/*      */     } finally {
/*  592 */       if (resultSet != null) {
/*  593 */         resultSet.close();
/*  594 */         resultSet = null;
/*      */       } 
/*  596 */       paramDatabase.commit();
/*  597 */       paramDatabase.freeStatement();
/*  598 */       paramDatabase.isPending();
/*      */     } 
/*  600 */     for (byte b = 0; b < returnDataResultSet.size(); b++) {
/*  601 */       String str1 = returnDataResultSet.getColumn(b, 0).trim();
/*  602 */       ABRUtil.append(paramStringBuffer, "derivefrommodel world wide counry " + str1 + NEWLINE);
/*      */       
/*  604 */       String str2 = str1;
/*  605 */       if (!paramTreeMap.containsKey(str2)) {
/*  606 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str1);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  611 */         ctryAudRecord.action = "Delete";
/*  612 */         paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
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
/*      */   private void buildWorldWideCountryAud(TreeMap<String, CtryAudRecord> paramTreeMap, Hashtable paramHashtable, Database paramDatabase, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, String paramString, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException {
/*  634 */     ReturnDataResultSet returnDataResultSet = null;
/*  635 */     ResultSet resultSet = null;
/*  636 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/*  637 */     String str = "SG";
/*  638 */     EntityItem entityItem = null;
/*      */     
/*  640 */     if (paramDiffEntity != null) {
/*  641 */       entityItem = paramDiffEntity.getCurrentEntityItem();
/*  642 */       if (entityItem != null) {
/*  643 */         str = entityItem.getProfile().getEnterprise();
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/*  648 */       resultSet = paramDatabase.callGBL9999A(returnStatus, str, "GENAREASELECTION", "1999", "COUNTRYLIST");
/*  649 */       returnDataResultSet = new ReturnDataResultSet(resultSet);
/*      */     } finally {
/*  651 */       if (resultSet != null) {
/*  652 */         resultSet.close();
/*  653 */         resultSet = null;
/*      */       } 
/*  655 */       paramDatabase.commit();
/*  656 */       paramDatabase.freeStatement();
/*  657 */       paramDatabase.isPending();
/*      */     } 
/*  659 */     for (byte b = 0; b < returnDataResultSet.size(); b++) {
/*  660 */       String str1 = returnDataResultSet.getColumn(b, 0).trim();
/*  661 */       ABRUtil.append(paramStringBuffer, "derivefrommodel world wide counry " + str1 + NEWLINE);
/*      */       
/*  663 */       String str2 = str1;
/*  664 */       if (paramTreeMap.containsKey(str2)) {
/*  665 */         CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/*  666 */         ctryAudRecord.setUpdateAvail((DiffEntity)null);
/*  667 */         ctryAudRecord.setAction("@@");
/*      */       } else {
/*      */         
/*  670 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str1);
/*  671 */         ctryAudRecord.action = "Update";
/*  672 */         paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeSet(Hashtable paramHashtable, Document paramDocument, Element paramElement, EntityItem paramEntityItem, CtryAudRecord paramCtryAudRecord, DiffEntity paramDiffEntity, String paramString, StringBuffer paramStringBuffer) {
/*  682 */     if (paramCtryAudRecord.isDisplayable()) {
/*  683 */       Element element1 = paramDocument.createElement(this.nodeName);
/*  684 */       addXMLAttrs(element1);
/*  685 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  688 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  689 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/*  690 */       element1.appendChild(element2);
/*  691 */       element2 = paramDocument.createElement("STATUS");
/*  692 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/*  693 */       element1.appendChild(element2);
/*  694 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  695 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  696 */       element1.appendChild(element2);
/*  697 */       element2 = paramDocument.createElement("ANNDATE");
/*  698 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnndate()));
/*  699 */       element1.appendChild(element2);
/*  700 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  701 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnnnumber()));
/*  702 */       element1.appendChild(element2);
/*  703 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  704 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getFirstorder()));
/*  705 */       element1.appendChild(element2);
/*  706 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  707 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/*  708 */       element1.appendChild(element2);
/*  709 */       element2 = paramDocument.createElement("PUBFROM");
/*  710 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/*  711 */       element1.appendChild(element2);
/*  712 */       element2 = paramDocument.createElement("PUBTO");
/*  713 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/*  714 */       element1.appendChild(element2);
/*  715 */       element2 = paramDocument.createElement("WDANNDATE");
/*  716 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getWdanndate()));
/*  717 */       element1.appendChild(element2);
/*      */       
/*  719 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  720 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEomannnum()));
/*  721 */       element1.appendChild(element2);
/*  722 */       element2 = paramDocument.createElement("LASTORDER");
/*  723 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getLastorder()));
/*  724 */       element1.appendChild(element2);
/*  725 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  726 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosanndate()));
/*  727 */       element1.appendChild(element2);
/*      */       
/*  729 */       element2 = paramDocument.createElement("ENDOFSERVICEANNNUMBER");
/*  730 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosannnum()));
/*  731 */       element1.appendChild(element2);
/*  732 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  733 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/*  734 */       element1.appendChild(element2);
/*      */       
/*  736 */       element2 = paramDocument.createElement("ENDOFDEVELOPMENTDATE");
/*  737 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEodavaildate()));
/*  738 */       element1.appendChild(element2);
/*  739 */       element2 = paramDocument.createElement("EODANNDATE");
/*  740 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEodanndate()));
/*  741 */       element1.appendChild(element2);
/*      */       
/*  743 */       SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramEntityItem, paramDiffEntity, paramString, paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */ 
/*      */ 
/*      */       
/*  747 */       element2 = paramDocument.createElement("ORDERSYSNAME");
/*  748 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getOrderSysName()));
/*  749 */       element1.appendChild(element2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  754 */     if (paramCtryAudRecord.isrfrDisplayable()) {
/*  755 */       Element element1 = paramDocument.createElement(this.nodeName);
/*      */       
/*  757 */       addXMLAttrs(element1);
/*  758 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  761 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  762 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfraction()));
/*  763 */       element1.appendChild(element2);
/*  764 */       element2 = paramDocument.createElement("STATUS");
/*  765 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfravailStatus()));
/*  766 */       element1.appendChild(element2);
/*  767 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  768 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  769 */       element1.appendChild(element2);
/*  770 */       element2 = paramDocument.createElement("ANNDATE");
/*  771 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfranndate()));
/*  772 */       element1.appendChild(element2);
/*  773 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  774 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrannnumber()));
/*  775 */       element1.appendChild(element2);
/*  776 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  777 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrfirstorder()));
/*  778 */       element1.appendChild(element2);
/*  779 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  780 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrplannedavailability()));
/*  781 */       element1.appendChild(element2);
/*  782 */       element2 = paramDocument.createElement("PUBFROM");
/*  783 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubfrom()));
/*  784 */       element1.appendChild(element2);
/*  785 */       element2 = paramDocument.createElement("PUBTO");
/*  786 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubto()));
/*  787 */       element1.appendChild(element2);
/*  788 */       element2 = paramDocument.createElement("WDANNDATE");
/*  789 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrwdanndate()));
/*  790 */       element1.appendChild(element2);
/*      */       
/*  792 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  793 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreomannnum()));
/*  794 */       element1.appendChild(element2);
/*  795 */       element2 = paramDocument.createElement("LASTORDER");
/*  796 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrlastorder()));
/*  797 */       element1.appendChild(element2);
/*  798 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  799 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosanndate()));
/*  800 */       element1.appendChild(element2);
/*      */       
/*  802 */       element2 = paramDocument.createElement("ENDOFSERVICEANNNUMBER");
/*  803 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosannnum()));
/*  804 */       element1.appendChild(element2);
/*  805 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  806 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrendofservice()));
/*  807 */       element1.appendChild(element2);
/*      */       
/*  809 */       element2 = paramDocument.createElement("ENDOFDEVELOPMENTDATE");
/*  810 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreodavaildate()));
/*  811 */       element1.appendChild(element2);
/*  812 */       element2 = paramDocument.createElement("EODANNDATE");
/*  813 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreodanndate()));
/*  814 */       element1.appendChild(element2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  828 */       SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramEntityItem, paramDiffEntity, paramString, paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */ 
/*      */ 
/*      */       
/*  832 */       element2 = paramDocument.createElement("ORDERSYSNAME");
/*  833 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrOrderSysName()));
/*  834 */       element1.appendChild(element2);
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
/*      */   private void createNodeSet(Hashtable paramHashtable1, Document paramDocument, Element paramElement, EntityItem paramEntityItem, CtryAudRecord paramCtryAudRecord, DiffEntity paramDiffEntity, String paramString, Hashtable paramHashtable2, StringBuffer paramStringBuffer) {
/*  848 */     if (paramCtryAudRecord.isDisplayable()) {
/*  849 */       Element element1 = paramDocument.createElement(this.nodeName);
/*  850 */       addXMLAttrs(element1);
/*  851 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  854 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  855 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/*  856 */       element1.appendChild(element2);
/*  857 */       element2 = paramDocument.createElement("STATUS");
/*  858 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/*  859 */       element1.appendChild(element2);
/*  860 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  861 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  862 */       element1.appendChild(element2);
/*  863 */       element2 = paramDocument.createElement("ANNDATE");
/*  864 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnndate()));
/*  865 */       element1.appendChild(element2);
/*  866 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  867 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnnnumber()));
/*  868 */       element1.appendChild(element2);
/*  869 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  870 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getFirstorder()));
/*  871 */       element1.appendChild(element2);
/*  872 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  873 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/*  874 */       element1.appendChild(element2);
/*  875 */       element2 = paramDocument.createElement("PUBFROM");
/*  876 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/*  877 */       element1.appendChild(element2);
/*  878 */       element2 = paramDocument.createElement("PUBTO");
/*  879 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/*  880 */       element1.appendChild(element2);
/*  881 */       element2 = paramDocument.createElement("WDANNDATE");
/*  882 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getWdanndate()));
/*  883 */       element1.appendChild(element2);
/*      */       
/*  885 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  886 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEomannnum()));
/*  887 */       element1.appendChild(element2);
/*  888 */       element2 = paramDocument.createElement("LASTORDER");
/*  889 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getLastorder()));
/*  890 */       element1.appendChild(element2);
/*  891 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  892 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosanndate()));
/*  893 */       element1.appendChild(element2);
/*      */       
/*  895 */       element2 = paramDocument.createElement("ENDOFSERVICEANNNUMBER");
/*  896 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosannnum()));
/*  897 */       element1.appendChild(element2);
/*  898 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  899 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/*  900 */       element1.appendChild(element2);
/*      */       
/*  902 */       element2 = paramDocument.createElement("ENDOFDEVELOPMENTDATE");
/*  903 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEodavaildate()));
/*  904 */       element1.appendChild(element2);
/*  905 */       element2 = paramDocument.createElement("EODANNDATE");
/*  906 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEodanndate()));
/*  907 */       element1.appendChild(element2);
/*      */ 
/*      */       
/*  910 */       createWorldWideAvilNote(paramDocument, paramCtryAudRecord, paramHashtable2, element1, paramStringBuffer);
/*      */ 
/*      */       
/*  913 */       element2 = paramDocument.createElement("ORDERSYSNAME");
/*  914 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getOrderSysName()));
/*  915 */       element1.appendChild(element2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  920 */     if (paramCtryAudRecord.isrfrDisplayable()) {
/*  921 */       Element element1 = paramDocument.createElement(this.nodeName);
/*      */       
/*  923 */       addXMLAttrs(element1);
/*  924 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  927 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  928 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfraction()));
/*  929 */       element1.appendChild(element2);
/*  930 */       element2 = paramDocument.createElement("STATUS");
/*  931 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfravailStatus()));
/*  932 */       element1.appendChild(element2);
/*  933 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  934 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  935 */       element1.appendChild(element2);
/*  936 */       element2 = paramDocument.createElement("ANNDATE");
/*  937 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfranndate()));
/*  938 */       element1.appendChild(element2);
/*  939 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  940 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrannnumber()));
/*  941 */       element1.appendChild(element2);
/*  942 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  943 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrfirstorder()));
/*  944 */       element1.appendChild(element2);
/*  945 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  946 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrplannedavailability()));
/*  947 */       element1.appendChild(element2);
/*  948 */       element2 = paramDocument.createElement("PUBFROM");
/*  949 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubfrom()));
/*  950 */       element1.appendChild(element2);
/*  951 */       element2 = paramDocument.createElement("PUBTO");
/*  952 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubto()));
/*  953 */       element1.appendChild(element2);
/*  954 */       element2 = paramDocument.createElement("WDANNDATE");
/*  955 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrwdanndate()));
/*  956 */       element1.appendChild(element2);
/*      */       
/*  958 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  959 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreomannnum()));
/*  960 */       element1.appendChild(element2);
/*  961 */       element2 = paramDocument.createElement("LASTORDER");
/*  962 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrlastorder()));
/*  963 */       element1.appendChild(element2);
/*  964 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  965 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosanndate()));
/*  966 */       element1.appendChild(element2);
/*      */       
/*  968 */       element2 = paramDocument.createElement("ENDOFSERVICEANNNUMBER");
/*  969 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosannnum()));
/*  970 */       element1.appendChild(element2);
/*  971 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  972 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrendofservice()));
/*  973 */       element1.appendChild(element2);
/*      */       
/*  975 */       element2 = paramDocument.createElement("ENDOFDEVELOPMENTDATE");
/*  976 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreodavaildate()));
/*  977 */       element1.appendChild(element2);
/*  978 */       element2 = paramDocument.createElement("EODANNDATE");
/*  979 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreodanndate()));
/*  980 */       element1.appendChild(element2);
/*      */       
/*  982 */       createWorldWideAvilNote(paramDocument, paramCtryAudRecord, paramHashtable2, element1, paramStringBuffer);
/*      */ 
/*      */       
/*  985 */       element2 = paramDocument.createElement("ORDERSYSNAME");
/*  986 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrOrderSysName()));
/*  987 */       element1.appendChild(element2);
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
/*      */   private void createWorldWideAvilNote(Document paramDocument, CtryAudRecord paramCtryAudRecord, Hashtable paramHashtable, Element paramElement, StringBuffer paramStringBuffer) throws DOMException {
/* 1006 */     String str = paramCtryAudRecord.getCountry();
/* 1007 */     Object object = paramHashtable.get(str);
/* 1008 */     if (object != null && !"Delete".equals(paramCtryAudRecord.action)) {
/*      */       
/* 1010 */       Element element1 = paramDocument.createElement("SLEORGGRPLIST");
/* 1011 */       paramElement.appendChild(element1);
/* 1012 */       Vector[] arrayOfVector = (Vector[])paramHashtable.get(str);
/* 1013 */       Hashtable hashtable = arrayOfVector[0].get(0);
/* 1014 */       Enumeration<String> enumeration = hashtable.keys();
/* 1015 */       while (enumeration.hasMoreElements()) {
/* 1016 */         String str1 = enumeration.nextElement();
/* 1017 */         Element element4 = paramDocument.createElement("SLEORGGRPELEMENT");
/* 1018 */         element1.appendChild(element4);
/* 1019 */         Element element3 = paramDocument.createElement("SLEOORGGRPACTION");
/* 1020 */         element3.appendChild(paramDocument.createTextNode("Update"));
/* 1021 */         element4.appendChild(element3);
/* 1022 */         element3 = paramDocument.createElement("SLEORGGRP");
/* 1023 */         element3.appendChild(paramDocument.createTextNode("" + str1));
/* 1024 */         element4.appendChild(element3);
/*      */       } 
/*      */       
/* 1027 */       Element element2 = paramDocument.createElement("SLEORGNPLNTCODELIST");
/* 1028 */       paramElement.appendChild(element2);
/* 1029 */       Vector<EntityItem> vector = arrayOfVector[1];
/* 1030 */       Vector<String> vector1 = new Vector();
/* 1031 */       for (byte b = 0; b < vector.size(); b++) {
/* 1032 */         EntityItem entityItem = vector.get(b);
/* 1033 */         String str1 = PokUtils.getAttributeValue(entityItem, "SLEORG", ", ", "", false);
/* 1034 */         String str2 = PokUtils.getAttributeValue(entityItem, "PLNTCD", ", ", "", false);
/* 1035 */         String str3 = str1 + str2;
/* 1036 */         if (!vector1.contains(str3)) {
/*      */ 
/*      */           
/* 1039 */           vector1.add(str3);
/* 1040 */           Element element4 = paramDocument.createElement("SLEORGNPLNTCODEELEMENT");
/* 1041 */           element2.appendChild(element4);
/* 1042 */           Element element3 = paramDocument.createElement("SLEORGNPLNTCODEACTION");
/* 1043 */           element3.appendChild(paramDocument.createTextNode("Update"));
/* 1044 */           element4.appendChild(element3);
/* 1045 */           element3 = paramDocument.createElement("SLEORG");
/* 1046 */           element3.appendChild(paramDocument.createTextNode("" + str1));
/* 1047 */           element4.appendChild(element3);
/* 1048 */           element3 = paramDocument.createElement("PLNTCD");
/* 1049 */           element3.appendChild(paramDocument.createTextNode("" + str2));
/* 1050 */           element4.appendChild(element3);
/*      */         } 
/* 1052 */       }  vector1.clear();
/*      */     } else {
/*      */       
/* 1055 */       Element element1 = paramDocument.createElement("SLEORGGRPLIST");
/* 1056 */       paramElement.appendChild(element1);
/*      */       
/* 1058 */       Element element2 = paramDocument.createElement("SLEORGNPLNTCODELIST");
/* 1059 */       paramElement.appendChild(element2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 1316 */     ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs " + paramDiffEntity.getKey() + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1324 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 1325 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 1326 */     if (paramBoolean) {
/* 1327 */       if (!paramDiffEntity.isNew()) {
/*      */         
/* 1329 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 1330 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for deleted avail at T1: ctryAtt " + 
/* 1331 */             PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 1332 */         if (eANFlagAttribute != null) {
/* 1333 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1334 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/* 1336 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 1337 */               String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 1338 */               String str2 = str1;
/* 1339 */               if (paramTreeMap.containsKey(str2)) {
/*      */                 
/* 1341 */                 CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/* 1342 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */               } else {
/*      */                 
/* 1345 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 1346 */                 ctryAudRecord.setAction("Delete");
/* 1347 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1354 */     } else if (!paramDiffEntity.isDeleted()) {
/*      */       
/* 1356 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 1357 */       ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for new /update avail:  ctryAtt and anncodeAtt " + 
/*      */           
/* 1359 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/* 1360 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/* 1361 */       if (eANFlagAttribute != null) {
/* 1362 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1363 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/* 1365 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 1366 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 1367 */             String str2 = str1;
/* 1368 */             if (paramTreeMap.containsKey(str2)) {
/* 1369 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/* 1370 */               if (isPlannedAvail(paramDiffEntity) || "Delete".equals(ctryAudRecord.action)) {
/* 1371 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new /udpate" + paramDiffEntity
/* 1372 */                     .getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */                 
/* 1374 */                 if ("Update".equals(ctryAudRecord.action)) {
/* 1375 */                   ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*      */                 } else {
/* 1377 */                   ctryAudRecord.setUpdateAvail(paramDiffEntity);
/* 1378 */                   ctryAudRecord.setAction("@@");
/*      */                 } 
/*      */               } 
/*      */             } else {
/* 1382 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 1383 */               ctryAudRecord.setAction("Update");
/* 1384 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 1385 */               ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 1386 */                   .getKey() + NEWLINE);
/*      */             } 
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
/*      */   private boolean isPlannedAvail(DiffEntity paramDiffEntity) {
/* 1400 */     boolean bool = false;
/* 1401 */     if (paramDiffEntity == null) {
/* 1402 */       return false;
/*      */     }
/* 1404 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 1405 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 1406 */     if (paramDiffEntity.isDeleted()) {
/* 1407 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/* 1408 */       if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1409 */         bool = true;
/*      */       }
/*      */     } else {
/* 1412 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/* 1413 */       if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1414 */         bool = true;
/*      */       }
/*      */     } 
/*      */     
/* 1418 */     return bool;
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
/*      */   private boolean isDerivefromModel(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer, boolean paramBoolean) {
/* 1525 */     boolean bool = true;
/*      */     
/* 1527 */     if (paramBoolean) {
/* 1528 */       if (paramDiffEntity != null && 
/* 1529 */         paramDiffEntity.getEntityType().equals("MODEL")) {
/*      */         
/* 1531 */         Vector<DiffEntity> vector = (Vector)paramHashtable.get("AVAIL");
/* 1532 */         ABRUtil.append(paramStringBuffer, "T2 DerivefromModel.getAvails looking for AVAILTYPE: 146 in AVAIL allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 1533 */             .size())) + NEWLINE);
/* 1534 */         if (vector != null)
/*      */         {
/* 1536 */           for (byte b = 0; b < vector.size(); b++) {
/* 1537 */             DiffEntity diffEntity = vector.elementAt(b);
/* 1538 */             EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 1539 */             if (!diffEntity.isDeleted()) {
/* 1540 */               ABRUtil.append(paramStringBuffer, "T2 XMLANNElem.DerivefromModel.getAvails checking[" + b + "]:New or Update" + diffEntity
/*      */                   
/* 1542 */                   .getKey() + " AVAILTYPE: " + 
/* 1543 */                   PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/* 1544 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 1545 */               if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1546 */                 bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/* 1569 */     } else if (paramDiffEntity != null && 
/* 1570 */       paramDiffEntity.getEntityType().equals("MODEL")) {
/*      */       
/* 1572 */       Vector<DiffEntity> vector = (Vector)paramHashtable.get("AVAIL");
/* 1573 */       ABRUtil.append(paramStringBuffer, "T1 DerivefromModel.getAvails looking for AVAILTYPE: 146 in AVAIL allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 1574 */           .size())) + NEWLINE);
/* 1575 */       if (vector != null)
/*      */       {
/* 1577 */         for (byte b = 0; b < vector.size(); b++) {
/* 1578 */           DiffEntity diffEntity = vector.elementAt(b);
/* 1579 */           EntityItem entityItem = diffEntity.getPriorEntityItem();
/* 1580 */           if (!diffEntity.isNew()) {
/* 1581 */             ABRUtil.append(paramStringBuffer, "T1 XMLANNElem.DerivefromModel.getAvails checking[" + b + "]:New or Update" + diffEntity
/*      */                 
/* 1583 */                 .getKey() + " AVAILTYPE: " + 
/* 1584 */                 PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/* 1585 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 1586 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1587 */               bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1610 */     return bool;
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
/*      */   private DiffEntity getCatlgor(Hashtable paramHashtable, Vector[] paramArrayOfVector, String paramString, StringBuffer paramStringBuffer) {
/* 1633 */     DiffEntity diffEntity = null;
/*      */ 
/*      */     
/* 1636 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("BHCATLGOR");
/*      */     
/* 1638 */     String str = "COUNTRYLIST";
/* 1639 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor looking for " + str + ":" + paramString + " in CATLGOR allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 1640 */         .size())) + NEWLINE);
/* 1641 */     if (vector == null) {
/* 1642 */       return diffEntity;
/*      */     }
/*      */ 
/*      */     
/* 1646 */     for (byte b = 0; b < vector.size(); b++) {
/* 1647 */       DiffEntity diffEntity1 = vector.elementAt(b);
/* 1648 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/* 1649 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/*      */       
/* 1651 */       String str1 = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/* 1652 */       if ("0020".equals(str1))
/*      */       {
/*      */         
/* 1655 */         if (diffEntity1.isDeleted()) {
/* 1656 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + str + ":" + 
/* 1657 */               PokUtils.getAttributeFlagValue(entityItem2, str) + NEWLINE);
/* 1658 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str);
/* 1659 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString)) {
/* 1660 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/* 1664 */         } else if (diffEntity1.isNew()) {
/* 1665 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: new " + diffEntity1.getKey() + " " + str + ":" + 
/* 1666 */               PokUtils.getAttributeFlagValue(entityItem1, str) + NEWLINE);
/* 1667 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str);
/* 1668 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString)) {
/* 1669 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } else {
/* 1674 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: current " + diffEntity1
/* 1675 */               .getKey() + " " + str + ":" + 
/* 1676 */               PokUtils.getAttributeFlagValue(entityItem1, str) + NEWLINE);
/* 1677 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str);
/* 1678 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString)) {
/* 1679 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/* 1683 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: prior " + diffEntity1.getKey() + " " + str + ":" + 
/* 1684 */               PokUtils.getAttributeFlagValue(entityItem2, str) + NEWLINE);
/* 1685 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str);
/* 1686 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString)) {
/* 1687 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1694 */     return diffEntity;
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
/*      */   private static class CtryAudRecord
/*      */     extends CtryRecord
/*      */   {
/*      */     public String country;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private DiffEntity availDiff;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String) {
/* 1776 */       super((String)null);
/* 1777 */       this.country = param1String;
/* 1778 */       this.availDiff = param1DiffEntity;
/*      */     }
/*      */     
/*      */     void setUpdateAvail(DiffEntity param1DiffEntity) {
/* 1782 */       this.availDiff = param1DiffEntity;
/* 1783 */       setAction("Update");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, DiffEntity param1DiffEntity6, DiffEntity param1DiffEntity7, DiffEntity param1DiffEntity8, Hashtable param1Hashtable, DiffEntity[] param1ArrayOfDiffEntity, String param1String, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/* 1807 */       this.availStatus = "0020";
/* 1808 */       this.rfravailStatus = "0040";
/*      */ 
/*      */ 
/*      */       
/* 1812 */       String[] arrayOfString1 = deriveAnnDate(param1ArrayOfDiffEntity[1], param1DiffEntity1, false, param1StringBuffer);
/* 1813 */       String[] arrayOfString2 = deriveAnnDate(param1ArrayOfDiffEntity[0], param1DiffEntity1, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1817 */       String[] arrayOfString3 = deriveAnnNumber(param1ArrayOfDiffEntity[1], false, param1StringBuffer);
/* 1818 */       String[] arrayOfString4 = deriveAnnNumber(param1ArrayOfDiffEntity[0], true, param1StringBuffer);
/*      */ 
/*      */       
/* 1821 */       String[] arrayOfString5 = deriveFIRSTORDER(param1ArrayOfDiffEntity[1], param1DiffEntity1, param1DiffEntity3, false, param1StringBuffer);
/* 1822 */       String[] arrayOfString6 = deriveFIRSTORDER(param1ArrayOfDiffEntity[0], param1DiffEntity1, param1DiffEntity3, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1830 */       String[] arrayOfString7 = deriveplannedavailability(param1ArrayOfDiffEntity[1], param1DiffEntity1, false, param1StringBuffer);
/* 1831 */       String[] arrayOfString8 = deriveplannedavailability(param1ArrayOfDiffEntity[0], param1DiffEntity1, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1843 */       String[] arrayOfString9 = derivePubFrom(param1ArrayOfDiffEntity[1], param1DiffEntity1, param1DiffEntity2, param1DiffEntity3, false, param1StringBuffer);
/*      */       
/* 1845 */       String[] arrayOfString10 = derivePubFrom(param1ArrayOfDiffEntity[0], param1DiffEntity1, param1DiffEntity2, param1DiffEntity3, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1849 */       String[] arrayOfString11 = derivePubTo(param1DiffEntity1, param1DiffEntity2, param1DiffEntity4, false, param1StringBuffer);
/* 1850 */       String[] arrayOfString12 = derivePubTo(param1DiffEntity1, param1DiffEntity2, param1DiffEntity4, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1853 */       String[] arrayOfString13 = deriveWDANNDATE(param1DiffEntity1, param1DiffEntity7, false, param1StringBuffer);
/* 1854 */       String[] arrayOfString14 = deriveWDANNDATE(param1DiffEntity1, param1DiffEntity7, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1857 */       String[] arrayOfString15 = deriveENDOFMARKETANNNUMBER(param1DiffEntity7, false, param1StringBuffer);
/* 1858 */       String[] arrayOfString16 = deriveENDOFMARKETANNNUMBER(param1DiffEntity7, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1861 */       String[] arrayOfString17 = deriveLastOrder(param1DiffEntity1, param1DiffEntity4, false, param1StringBuffer);
/* 1862 */       String[] arrayOfString18 = deriveLastOrder(param1DiffEntity1, param1DiffEntity4, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1865 */       String[] arrayOfString19 = deriveEOSANNDATE(param1DiffEntity6, false, param1StringBuffer);
/* 1866 */       String[] arrayOfString20 = deriveEOSANNDATE(param1DiffEntity6, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1869 */       String[] arrayOfString21 = deriveENDOFDEVELOPMENTDATE(param1DiffEntity5, false, param1StringBuffer);
/* 1870 */       String[] arrayOfString22 = deriveENDOFDEVELOPMENTDATE(param1DiffEntity5, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1873 */       String[] arrayOfString23 = deriveEODANNDATE(param1DiffEntity5, false, param1StringBuffer);
/* 1874 */       String[] arrayOfString24 = deriveEODANNDATE(param1DiffEntity5, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1877 */       String[] arrayOfString25 = deriveENDOFSERVICEANNNUMBER(param1DiffEntity6, false, param1StringBuffer);
/* 1878 */       String[] arrayOfString26 = deriveENDOFSERVICEANNNUMBER(param1DiffEntity6, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1881 */       String[] arrayOfString27 = deriveENDOFSERVICE(param1DiffEntity6, false, param1StringBuffer);
/* 1882 */       String[] arrayOfString28 = deriveENDOFSERVICE(param1DiffEntity6, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1885 */       String[] arrayOfString29 = deriveORDERSYSNAME(param1ArrayOfDiffEntity[1], false, param1StringBuffer);
/* 1886 */       String[] arrayOfString30 = deriveORDERSYSNAME(param1ArrayOfDiffEntity[0], true, param1StringBuffer);
/*      */       
/* 1888 */       String[] arrayOfString31 = deriveRFAREFNUMBER(param1DiffEntity8, false, param1StringBuffer);
/* 1889 */       String[] arrayOfString32 = deriveRFAREFNUMBER(param1DiffEntity8, true, param1StringBuffer);
/* 1890 */       ABRUtil.append(param1StringBuffer, "ordersysnames:" + arrayOfString29[0] + "," + arrayOfString29[1] + ",ordersysnamesT1:" + arrayOfString30[0] + "," + arrayOfString30[1] + NEWLINE);
/*      */       
/* 1892 */       ABRUtil.append(param1StringBuffer, "rfaRefNumbers:" + arrayOfString31[0] + "," + arrayOfString31[1] + ",rfaRefNumbersT1:" + arrayOfString32[0] + "," + arrayOfString32[1] + NEWLINE);
/*      */       
/* 1894 */       ABRUtil.append(param1StringBuffer, "epicAvailDiff:" + param1DiffEntity8 + ",plAvailDiff:" + param1ArrayOfDiffEntity + NEWLINE);
/*      */       
/* 1896 */       handleResults(arrayOfString1, arrayOfString2, arrayOfString3, arrayOfString4, arrayOfString5, arrayOfString6, arrayOfString7, arrayOfString8, arrayOfString9, arrayOfString10, arrayOfString11, arrayOfString12, arrayOfString13, arrayOfString14, arrayOfString15, arrayOfString16, arrayOfString17, arrayOfString18, arrayOfString27, arrayOfString28, arrayOfString23, arrayOfString24, arrayOfString21, arrayOfString22, arrayOfString19, arrayOfString20, arrayOfString25, arrayOfString26, arrayOfString29, arrayOfString30, this.country, param1Boolean1, param1Boolean2, param1StringBuffer);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFDEVELOPMENTDATE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1924 */       String str1 = "@@";
/* 1925 */       String str2 = "@@";
/* 1926 */       String[] arrayOfString1 = new String[2];
/* 1927 */       String[] arrayOfString2 = new String[2];
/* 1928 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveENDOFDEVELOPMENTDATE endDevAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 1929 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/* 1930 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1932 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*      */         
/* 1934 */         str1 = arrayOfString2[0];
/* 1935 */         str2 = arrayOfString2[1];
/*      */       } 
/* 1937 */       arrayOfString1[0] = str1;
/* 1938 */       arrayOfString1[1] = str2;
/* 1939 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveEODANNDATE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1950 */       String str1 = "@@";
/* 1951 */       String str2 = "@@";
/* 1952 */       String[] arrayOfString1 = new String[2];
/* 1953 */       String[] arrayOfString2 = new String[2];
/* 1954 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveEODANNDATE endDevAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 1955 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/* 1956 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1958 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/*      */         
/* 1960 */         str1 = arrayOfString2[0];
/* 1961 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1964 */       arrayOfString1[0] = str1;
/* 1965 */       arrayOfString1[1] = str2;
/* 1966 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFSERVICE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1976 */       String str1 = "@@";
/* 1977 */       String str2 = "@@";
/* 1978 */       String[] arrayOfString1 = new String[2];
/* 1979 */       String[] arrayOfString2 = new String[2];
/* 1980 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveENDOFSERVICE endAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 1981 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/* 1982 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1984 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*      */         
/* 1986 */         str1 = arrayOfString2[0];
/* 1987 */         str2 = arrayOfString2[1];
/*      */       } 
/* 1989 */       arrayOfString1[0] = str1;
/* 1990 */       arrayOfString1[1] = str2;
/* 1991 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] derivePubTo(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2002 */       String str1 = "@@";
/* 2003 */       String str2 = "@@";
/* 2004 */       String[] arrayOfString1 = new String[2];
/* 2005 */       String[] arrayOfString2 = new String[2];
/* 2006 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubTo catlgorDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 2007 */           .getKey()) + " loAvailDiff: " + ((param1DiffEntity3 == null) ? "null" : param1DiffEntity3
/* 2008 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2010 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2012 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity1, param1DiffEntity2, str1, str2, this.country, "PUBTO", param1StringBuffer);
/*      */         
/* 2014 */         str1 = arrayOfString2[0];
/* 2015 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2018 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2020 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*      */         
/* 2022 */         str1 = arrayOfString2[0];
/* 2023 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2026 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2028 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "WTHDRWEFFCTVDATE", param1StringBuffer);
/*      */         
/* 2030 */         str1 = arrayOfString2[0];
/* 2031 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2034 */       arrayOfString1[0] = str1;
/* 2035 */       arrayOfString1[1] = str2;
/* 2036 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] derivePubFrom(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2054 */       String str1 = "@@";
/* 2055 */       String str2 = "@@";
/* 2056 */       String[] arrayOfString1 = new String[2];
/* 2057 */       String[] arrayOfString2 = new String[2];
/* 2058 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubFrom plAvailDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1
/* 2059 */           .getKey()) + " foAvailDiff: " + ((param1DiffEntity4 == null) ? "null" : param1DiffEntity4
/* 2060 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2062 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2064 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity2, param1DiffEntity3, str1, str2, this.country, "PUBFROM", param1StringBuffer);
/*      */         
/* 2066 */         str1 = arrayOfString2[0];
/* 2067 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2069 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2071 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity4, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*      */         
/* 2073 */         str1 = arrayOfString2[0];
/* 2074 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2076 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 2079 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/*      */         
/* 2081 */         str1 = arrayOfString2[0];
/* 2082 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2085 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2087 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*      */         
/* 2089 */         str1 = arrayOfString2[0];
/* 2090 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2093 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2095 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, "ANNDATE", param1StringBuffer);
/* 2096 */         str1 = arrayOfString2[0];
/* 2097 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2100 */       arrayOfString1[0] = str1;
/* 2101 */       arrayOfString1[1] = str2;
/* 2102 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnNumber(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2110 */       String str1 = "@@";
/* 2111 */       String str2 = "@@";
/* 2112 */       String[] arrayOfString1 = new String[2];
/* 2113 */       String[] arrayOfString2 = new String[2];
/* 2114 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveAnnNumber plAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 2115 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/* 2116 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2118 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/*      */         
/* 2120 */         str1 = arrayOfString2[0];
/* 2121 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2124 */       arrayOfString1[0] = str1;
/* 2125 */       arrayOfString1[1] = str2;
/* 2126 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnDate(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2136 */       String str1 = "@@";
/* 2137 */       String str2 = "@@";
/* 2138 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 2140 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2142 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveAnnDate plAvailDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1
/* 2143 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */ 
/*      */       
/* 2147 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 2148 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/*      */         
/* 2150 */         str1 = arrayOfString2[0];
/* 2151 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2154 */       if ("@@".equals(str1) && "@@".equals(str2)) {
/*      */         
/* 2156 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, "ANNDATE", param1StringBuffer);
/*      */         
/* 2158 */         str1 = arrayOfString2[0];
/* 2159 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2162 */       arrayOfString1[0] = str1;
/* 2163 */       arrayOfString1[1] = str2;
/* 2164 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveFIRSTORDER(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2177 */       String str1 = "@@";
/* 2178 */       String str2 = "@@";
/* 2179 */       String[] arrayOfString1 = new String[2];
/* 2180 */       String[] arrayOfString2 = new String[2];
/* 2181 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveFIRSTORDER plAvailDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1
/*      */           
/* 2183 */           .getKey()) + " foAvailDiff: " + ((param1DiffEntity3 == null) ? "null" : param1DiffEntity3
/* 2184 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2186 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2188 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*      */         
/* 2190 */         str1 = arrayOfString2[0];
/* 2191 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2194 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2196 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/*      */         
/* 2198 */         str1 = arrayOfString2[0];
/* 2199 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2202 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2204 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*      */         
/* 2206 */         str1 = arrayOfString2[0];
/* 2207 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2210 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2212 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, "ANNDATE", param1StringBuffer);
/*      */         
/* 2214 */         str1 = arrayOfString2[0];
/* 2215 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2218 */       arrayOfString1[0] = str1;
/* 2219 */       arrayOfString1[1] = str2;
/* 2220 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveplannedavailability(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2230 */       String str1 = "@@";
/* 2231 */       String str2 = "@@";
/* 2232 */       String[] arrayOfString1 = new String[2];
/* 2233 */       String[] arrayOfString2 = new String[2];
/* 2234 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveplannedavailability plAvailDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1
/* 2235 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/* 2236 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2238 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*      */         
/* 2240 */         str1 = arrayOfString2[0];
/* 2241 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2244 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2246 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, "GENAVAILDATE", param1StringBuffer);
/*      */         
/* 2248 */         str1 = arrayOfString2[0];
/* 2249 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2252 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2254 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, "ANNDATE", param1StringBuffer);
/*      */         
/* 2256 */         str1 = arrayOfString2[0];
/* 2257 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2260 */       arrayOfString1[0] = str1;
/* 2261 */       arrayOfString1[1] = str2;
/* 2262 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFSERVICEANNNUMBER(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2271 */       String str1 = "@@";
/* 2272 */       String str2 = "@@";
/* 2273 */       String[] arrayOfString1 = new String[2];
/* 2274 */       String[] arrayOfString2 = new String[2];
/* 2275 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveENDOFSERVICEANNNUMBER endAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 2278 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2280 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/* 2281 */         str1 = arrayOfString2[0];
/* 2282 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2285 */       arrayOfString1[0] = str1;
/* 2286 */       arrayOfString1[1] = str2;
/* 2287 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveEOSANNDATE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2297 */       String str1 = "@@";
/* 2298 */       String str2 = "@@";
/* 2299 */       String[] arrayOfString1 = new String[2];
/* 2300 */       String[] arrayOfString2 = new String[2];
/* 2301 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveEOSANNDATE endAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 2302 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2304 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2306 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity, str1, str2, this.country, "13", param1StringBuffer);
/*      */         
/* 2308 */         str1 = arrayOfString2[0];
/* 2309 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2312 */       arrayOfString1[0] = str1;
/* 2313 */       arrayOfString1[1] = str2;
/* 2314 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveWDANNDATE(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2330 */       String str1 = "@@";
/* 2331 */       String str2 = "@@";
/* 2332 */       String[] arrayOfString1 = new String[2];
/* 2333 */       String[] arrayOfString2 = new String[2];
/* 2334 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveWDANNDATE endMktAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 2335 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2337 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2339 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity2, str1, str2, this.country, "14", param1StringBuffer);
/*      */         
/* 2341 */         str1 = arrayOfString2[0];
/* 2342 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2345 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2347 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "WITHDRAWDATE", param1StringBuffer);
/*      */         
/* 2349 */         str1 = arrayOfString2[0];
/* 2350 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2353 */       arrayOfString1[0] = str1;
/* 2354 */       arrayOfString1[1] = str2;
/* 2355 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFMARKETANNNUMBER(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2364 */       String str1 = "@@";
/* 2365 */       String str2 = "@@";
/* 2366 */       String[] arrayOfString1 = new String[2];
/* 2367 */       String[] arrayOfString2 = new String[2];
/* 2368 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveEndOfMarketAnnNumber endMktAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2370 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2372 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/* 2373 */         str1 = arrayOfString2[0];
/* 2374 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2377 */       arrayOfString1[0] = str1;
/* 2378 */       arrayOfString1[1] = str2;
/* 2379 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLastOrder(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2390 */       String str1 = "@@";
/* 2391 */       String str2 = "@@";
/* 2392 */       String[] arrayOfString1 = new String[2];
/* 2393 */       String[] arrayOfString2 = new String[2];
/* 2394 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLastOrder loAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 2395 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2397 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2399 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*      */         
/* 2401 */         str1 = arrayOfString2[0];
/* 2402 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2405 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2407 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "WTHDRWEFFCTVDATE", param1StringBuffer);
/*      */         
/* 2409 */         str1 = arrayOfString2[0];
/* 2410 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2413 */       arrayOfString1[0] = str1;
/* 2414 */       arrayOfString1[1] = str2;
/* 2415 */       return arrayOfString1;
/*      */     }
/*      */     
/*      */     private String[] deriveORDERSYSNAME(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2419 */       String str1 = "@@";
/* 2420 */       String str2 = "@@";
/* 2421 */       String[] arrayOfString1 = new String[2];
/* 2422 */       String[] arrayOfString2 = new String[2];
/* 2423 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveORDERSYSNAME plAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 2424 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/* 2425 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2427 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "ORDERSYSNAME", param1StringBuffer);
/*      */         
/* 2429 */         str1 = arrayOfString2[0];
/* 2430 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2432 */       arrayOfString1[0] = str1;
/* 2433 */       arrayOfString1[1] = str2;
/* 2434 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveRFAREFNUMBER(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2451 */       String str1 = "@@";
/* 2452 */       String str2 = "@@";
/* 2453 */       String[] arrayOfString1 = new String[2];
/* 2454 */       String[] arrayOfString2 = new String[2];
/* 2455 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveRFAREFNUMBER plAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 2456 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/* 2457 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2459 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "RFAREFNUMBER", param1StringBuffer);
/*      */         
/* 2461 */         str1 = arrayOfString2[0];
/* 2462 */         str2 = arrayOfString2[1];
/* 2463 */         ABRUtil.append(param1StringBuffer, "deriveRFAREFNUMBER(),temps[0]:" + arrayOfString2[0] + ",temps[1]:" + arrayOfString2[1] + NEWLINE);
/*      */       } 
/* 2465 */       ABRUtil.append(param1StringBuffer, "deriveRFAREFNUMBER()" + str1 + "," + str2 + NEWLINE);
/* 2466 */       arrayOfString1[0] = str1;
/* 2467 */       arrayOfString1[1] = str2;
/* 2468 */       return arrayOfString1;
/*      */     }
/*      */     
/*      */     String getCountry() {
/* 2472 */       return this.country;
/*      */     }
/*      */     
/*      */     String getKey() {
/* 2476 */       return this.country;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2480 */       return ((this.availDiff == null) ? "This is no avail." : this.availDiff.getKey()) + " " + getKey() + " action:" + this.action;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLAVAILElembh1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */