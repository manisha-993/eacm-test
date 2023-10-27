/*      */ package COM.ibm.eannounce.abr.util;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.IOException;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
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
/*      */ public class XMLLSEOBUNDELAVAILElembh1
/*      */   extends XMLElem
/*      */ {
/*  205 */   private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLLSEOBUNDELAVAILElembh1() {
/*  225 */     super("AVAILABILITYELEMENT");
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
/*      */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  242 */     String str1 = null;
/*      */     
/*  244 */     EntityItem entityItem = null;
/*  245 */     entityItem = paramDiffEntity.getCurrentEntityItem();
/*  246 */     str1 = "D:LSEOBUNDLEAVAIL:D:AVAIL:D:AVAILSLEORGA:D";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  251 */     String str2 = "@@";
/*  252 */     boolean bool = false;
/*  253 */     boolean bool1 = false;
/*  254 */     bool = AvailUtil.iscompatmodel();
/*      */     
/*  256 */     if (!bool) {
/*      */       
/*  258 */       String str = null;
/*  259 */       str = (String)paramHashtable.get("_chSTATUS");
/*  260 */       ABRUtil.append(paramStringBuffer, "the status is" + str + NEWLINE);
/*  261 */       if ("0020".equals(str)) {
/*  262 */         bool1 = true;
/*      */       } else {
/*  264 */         bool1 = false;
/*      */       } 
/*      */     } 
/*      */     
/*  268 */     if (isDerivefromLSEOBUNDLE(paramDiffEntity, paramStringBuffer)) {
/*  269 */       str1 = "D:LSEOBUNDLESLEORGA:D";
/*  270 */       str2 = "LSEOBUNDLESPECBID";
/*  271 */       createNodeFromLSEOBUNDLE(paramHashtable, str2, paramDocument, paramElement, paramDiffEntity, entityItem, str1, bool1, bool, paramStringBuffer);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  277 */       str2 = "LSEOBUNDLE";
/*      */       
/*  279 */       Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, str2, paramStringBuffer);
/*      */       
/*  281 */       if (vector.size() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  287 */         TreeMap<Object, Object> treeMap = new TreeMap<>(); byte b;
/*  288 */         for (b = 0; b < vector.size(); b++) {
/*  289 */           DiffEntity diffEntity = vector.elementAt(b);
/*  290 */           buildCtryAudRecs(treeMap, diffEntity, true, paramStringBuffer);
/*      */         } 
/*      */ 
/*      */         
/*  294 */         for (b = 0; b < vector.size(); b++) {
/*  295 */           DiffEntity diffEntity = vector.elementAt(b);
/*  296 */           buildCtryAudRecs(treeMap, diffEntity, false, paramStringBuffer);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  301 */         Vector[] arrayOfVector = getModelAudience(paramDiffEntity, paramStringBuffer);
/*  302 */         Collection collection = treeMap.values();
/*  303 */         Iterator<CtryAudRecord> iterator = collection.iterator();
/*  304 */         StringBuffer stringBuffer = new StringBuffer();
/*  305 */         while (iterator.hasNext()) {
/*      */           
/*  307 */           CtryAudRecord ctryAudRecord = iterator.next();
/*  308 */           if ("LSEO".equals(str2)) {
/*  309 */             if (stringBuffer.length() == 0) {
/*  310 */               stringBuffer.append(ctryAudRecord.getCountry());
/*      */             } else {
/*  312 */               stringBuffer.append("|" + ctryAudRecord.getCountry());
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  318 */           DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, str2, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/*  319 */               .getCountry(), paramStringBuffer);
/*      */           
/*  321 */           DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, str2, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  322 */               .getCountry(), paramStringBuffer);
/*  323 */           DiffEntity diffEntity3 = getEntityForAttrs(paramHashtable, str2, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/*  324 */               .getCountry(), paramStringBuffer);
/*  325 */           DiffEntity diffEntity4 = getEntityForAttrs(paramHashtable, str2, "AVAIL", "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRecord
/*  326 */               .getCountry(), paramStringBuffer);
/*  327 */           DiffEntity diffEntity5 = getCatlgor(paramHashtable, str2, arrayOfVector, ctryAudRecord.getCountry(), paramStringBuffer);
/*      */           
/*  329 */           ctryAudRecord.setAllFields(paramDiffEntity, diffEntity5, diffEntity1, diffEntity2, diffEntity3, diffEntity4, paramHashtable, entityItem, ctryAudRecord.availDiff, str1, bool1, bool, paramStringBuffer);
/*      */           
/*  331 */           if (ctryAudRecord.isDisplayable() || ctryAudRecord.isrfrDisplayable()) {
/*  332 */             createNodeSet(paramHashtable, paramDocument, paramElement, entityItem, ctryAudRecord.availDiff, ctryAudRecord, str1, str2, paramStringBuffer);
/*      */           } else {
/*  334 */             ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*      */           } 
/*  336 */           ctryAudRecord.dereference();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  342 */         treeMap.clear();
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  348 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no planned AVAILs found" + NEWLINE);
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
/*      */   private Vector getSeoAndLseobundleAvail(Hashtable paramHashtable, String paramString, StringBuffer paramStringBuffer) {
/*  367 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("AVAIL");
/*      */     
/*  369 */     Vector<DiffEntity> vector2 = new Vector(1);
/*  370 */     if (vector1 != null) {
/*  371 */       if (paramString.equals("LSEOBUNDLE") || paramString.equals("LSEOBUNDLESPECBID")) {
/*  372 */         vector2 = vector1;
/*      */       } else {
/*  374 */         for (byte b = 0; b < vector1.size(); b++) {
/*  375 */           DiffEntity diffEntity = vector1.elementAt(b);
/*  376 */           if (diffEntity.toString().indexOf("MODELAVAIL") > -1) {
/*  377 */             if (paramString.equals("LSEOSPECBID"))
/*      */             {
/*  379 */               vector2.add(diffEntity);
/*      */             }
/*  381 */           } else if (diffEntity.toString().indexOf("LSEOAVAIL") > -1 && 
/*  382 */             paramString.equals("LSEO")) {
/*  383 */             vector2.add(diffEntity);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  389 */     return vector2;
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
/*      */   private Vector getLseoAndLseobundleCatlgor(Hashtable paramHashtable, String paramString, StringBuffer paramStringBuffer) {
/*  407 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("BHCATLGOR");
/*  408 */     Vector<DiffEntity> vector2 = new Vector(1);
/*  409 */     DiffEntity diffEntity = null;
/*  410 */     EntityItem entityItem = null;
/*  411 */     String str = "";
/*      */     
/*  413 */     if (vector1 != null) {
/*  414 */       if (paramString.equals("LSEOBUNDLE") || paramString.equals("LSEOBUNDLESPECBID")) {
/*  415 */         for (byte b = 0; b < vector1.size(); b++) {
/*  416 */           diffEntity = vector1.elementAt(b);
/*  417 */           entityItem = diffEntity.getCurrentEntityItem();
/*  418 */           str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  419 */           if ("0020".equals(str)) {
/*  420 */             vector2.add(diffEntity);
/*      */           }
/*      */         } 
/*      */       } else {
/*  424 */         for (byte b = 0; b < vector1.size(); b++) {
/*  425 */           diffEntity = vector1.elementAt(b);
/*  426 */           entityItem = diffEntity.getCurrentEntityItem();
/*  427 */           str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  428 */           if ("0020".equals(str) && 
/*  429 */             !diffEntity.getKey().equals("BHCATLGOR") && 
/*  430 */             diffEntity.toString().indexOf("LSEOBHCATLGOR") > -1) {
/*  431 */             vector2.add(diffEntity);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  438 */     return vector2;
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
/*      */   private void createNodeFromLSEOBUNDLE(Hashtable paramHashtable, String paramString1, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, EntityItem paramEntityItem, String paramString2, boolean paramBoolean1, boolean paramBoolean2, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException {
/*  623 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  624 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  625 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*      */     
/*  627 */     if (paramDiffEntity.isNew()) {
/*      */       
/*  629 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  630 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for new lseo: ctryAtt " + 
/*  631 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/*  632 */       if (eANFlagAttribute != null) {
/*  633 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  634 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  636 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  637 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  638 */             String str2 = str1;
/*  639 */             if (treeMap.containsKey(str2)) {
/*      */               
/*  641 */               CtryAudRecord ctryAudRecord = (CtryAudRecord)treeMap.get(str2);
/*  642 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for New " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */             } else {
/*      */               
/*  645 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str1);
/*  646 */               ctryAudRecord.setAction("Update");
/*  647 */               treeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  648 */               ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for New:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  649 */                   .getKey() + NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  654 */     } else if (!paramDiffEntity.isDeleted()) {
/*  655 */       HashSet<String> hashSet1 = new HashSet();
/*  656 */       HashSet<String> hashSet2 = new HashSet();
/*      */       
/*  658 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  659 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for current lseo: ctryAtt " + 
/*  660 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/*  661 */       if (eANFlagAttribute != null) {
/*  662 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  663 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  665 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  666 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  667 */             hashSet2.add(str);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  673 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  674 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for prior lseo: ctryAtt " + 
/*  675 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/*  676 */       if (eANFlagAttribute != null) {
/*  677 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  678 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  680 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  681 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  682 */             hashSet1.add(str);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  687 */       Iterator<String> iterator1 = hashSet2.iterator();
/*  688 */       while (iterator1.hasNext()) {
/*  689 */         String str = iterator1.next();
/*  690 */         if (!hashSet1.contains(str)) {
/*      */           
/*  692 */           if (treeMap.containsKey(str)) {
/*  693 */             CtryAudRecord ctryAudRecord2 = (CtryAudRecord)treeMap.get(str);
/*  694 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for added ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryAudRecord2 + NEWLINE);
/*      */             continue;
/*      */           } 
/*  697 */           CtryAudRecord ctryAudRecord1 = new CtryAudRecord(null, str);
/*  698 */           ctryAudRecord1.setAction("Update");
/*  699 */           treeMap.put(ctryAudRecord1.getKey(), ctryAudRecord1);
/*  700 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord1
/*  701 */               .getKey() + NEWLINE);
/*      */           
/*      */           continue;
/*      */         } 
/*  705 */         if (treeMap.containsKey(str)) {
/*  706 */           CtryAudRecord ctryAudRecord1 = (CtryAudRecord)treeMap.get(str);
/*  707 */           ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for existing ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */           continue;
/*      */         } 
/*  710 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str);
/*  711 */         treeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  712 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  713 */             .getKey() + NEWLINE);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  718 */       iterator1 = hashSet1.iterator();
/*  719 */       while (iterator1.hasNext()) {
/*  720 */         String str = iterator1.next();
/*  721 */         if (!hashSet2.contains(str)) {
/*      */           
/*  723 */           if (treeMap.containsKey(str)) {
/*  724 */             CtryAudRecord ctryAudRecord1 = (CtryAudRecord)treeMap.get(str);
/*  725 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */             continue;
/*      */           } 
/*  728 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str);
/*  729 */           ctryAudRecord.setAction("Delete");
/*  730 */           treeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  731 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for deleted ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  732 */               .getKey() + NEWLINE);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  738 */     Vector[] arrayOfVector = getModelAudience(paramDiffEntity, paramStringBuffer);
/*  739 */     Collection collection = treeMap.values();
/*  740 */     Iterator<CtryAudRecord> iterator = collection.iterator();
/*  741 */     while (iterator.hasNext()) {
/*      */       
/*  743 */       CtryAudRecord ctryAudRecord = iterator.next();
/*      */ 
/*      */       
/*  746 */       DiffEntity diffEntity = getCatlgor(paramHashtable, paramString1, arrayOfVector, ctryAudRecord.getCountry(), paramStringBuffer);
/*      */ 
/*      */ 
/*      */       
/*  750 */       ctryAudRecord.setAllLSEOFields(paramDiffEntity, diffEntity, paramBoolean1, paramBoolean2, paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  757 */       if (ctryAudRecord.isDisplayable() || ctryAudRecord.isrfrDisplayable()) {
/*  758 */         createNodeSet(paramHashtable, paramDocument, paramElement, paramEntityItem, paramDiffEntity, ctryAudRecord, paramString2, paramString1, paramStringBuffer);
/*      */       } else {
/*  760 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no changes found for " + ctryAudRecord.country + NEWLINE);
/*      */       } 
/*  762 */       ctryAudRecord.dereference();
/*      */     } 
/*  764 */     treeMap.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeSet(Hashtable paramHashtable, Document paramDocument, Element paramElement, EntityItem paramEntityItem, DiffEntity paramDiffEntity, CtryAudRecord paramCtryAudRecord, String paramString1, String paramString2, StringBuffer paramStringBuffer) {
/*  773 */     if (paramCtryAudRecord.isDisplayable()) {
/*  774 */       Element element1 = paramDocument.createElement(this.nodeName);
/*  775 */       addXMLAttrs(element1);
/*  776 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  779 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  780 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/*  781 */       element1.appendChild(element2);
/*  782 */       element2 = paramDocument.createElement("STATUS");
/*  783 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/*  784 */       element1.appendChild(element2);
/*  785 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  786 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  787 */       element1.appendChild(element2);
/*  788 */       element2 = paramDocument.createElement("ANNDATE");
/*  789 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnndate()));
/*  790 */       element1.appendChild(element2);
/*  791 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  792 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnnnumber()));
/*  793 */       element1.appendChild(element2);
/*  794 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  795 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getFirstorder()));
/*  796 */       element1.appendChild(element2);
/*  797 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  798 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/*  799 */       element1.appendChild(element2);
/*  800 */       element2 = paramDocument.createElement("PUBFROM");
/*  801 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/*  802 */       element1.appendChild(element2);
/*  803 */       element2 = paramDocument.createElement("PUBTO");
/*  804 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/*  805 */       element1.appendChild(element2);
/*  806 */       element2 = paramDocument.createElement("WDANNDATE");
/*  807 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getWdanndate()));
/*  808 */       element1.appendChild(element2);
/*  809 */       element2 = paramDocument.createElement("LASTORDER");
/*  810 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getLastorder()));
/*  811 */       element1.appendChild(element2);
/*  812 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  813 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosanndate()));
/*  814 */       element1.appendChild(element2);
/*  815 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  816 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/*  817 */       element1.appendChild(element2);
/*      */       
/*  819 */       if (paramString2.equals("LSEO")) {
/*  820 */         SLEORGGRP.displayAVAILSLEORGLSEO(paramHashtable, paramDocument, element1, paramEntityItem, paramDiffEntity, paramString1, paramCtryAudRecord.country, paramCtryAudRecord
/*  821 */             .getAction(), paramString2, paramStringBuffer);
/*      */       } else {
/*  823 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramEntityItem, paramDiffEntity, paramString1, paramCtryAudRecord.country, paramCtryAudRecord
/*  824 */             .getAction(), paramStringBuffer);
/*      */       } 
/*      */     } 
/*  827 */     if (paramCtryAudRecord.isrfrDisplayable()) {
/*  828 */       Element element1 = paramDocument.createElement(this.nodeName);
/*      */       
/*  830 */       addXMLAttrs(element1);
/*  831 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  834 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  835 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfraction()));
/*  836 */       element1.appendChild(element2);
/*  837 */       element2 = paramDocument.createElement("STATUS");
/*  838 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfravailStatus()));
/*  839 */       element1.appendChild(element2);
/*  840 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  841 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  842 */       element1.appendChild(element2);
/*  843 */       element2 = paramDocument.createElement("ANNDATE");
/*  844 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfranndate()));
/*  845 */       element1.appendChild(element2);
/*  846 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  847 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrannnumber()));
/*  848 */       element1.appendChild(element2);
/*  849 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  850 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrfirstorder()));
/*  851 */       element1.appendChild(element2);
/*  852 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  853 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrplannedavailability()));
/*  854 */       element1.appendChild(element2);
/*  855 */       element2 = paramDocument.createElement("PUBFROM");
/*  856 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubfrom()));
/*  857 */       element1.appendChild(element2);
/*  858 */       element2 = paramDocument.createElement("PUBTO");
/*  859 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubto()));
/*  860 */       element1.appendChild(element2);
/*  861 */       element2 = paramDocument.createElement("WDANNDATE");
/*  862 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrwdanndate()));
/*  863 */       element1.appendChild(element2);
/*  864 */       element2 = paramDocument.createElement("LASTORDER");
/*  865 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrlastorder()));
/*  866 */       element1.appendChild(element2);
/*  867 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  868 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosanndate()));
/*  869 */       element1.appendChild(element2);
/*  870 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  871 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrendofservice()));
/*  872 */       element1.appendChild(element2);
/*      */       
/*  874 */       if (paramString2.equals("LSEO")) {
/*  875 */         SLEORGGRP.displayAVAILSLEORGLSEO(paramHashtable, paramDocument, element1, paramEntityItem, paramDiffEntity, paramString1, paramCtryAudRecord.country, paramCtryAudRecord
/*  876 */             .getAction(), paramString2, paramStringBuffer);
/*      */       } else {
/*  878 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramEntityItem, paramDiffEntity, paramString1, paramCtryAudRecord.country, paramCtryAudRecord
/*  879 */             .getAction(), paramStringBuffer);
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
/*      */   private Vector[] getModelAudience(DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  901 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getModelAudience for " + paramDiffEntity.getKey() + NEWLINE);
/*      */     
/*  903 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramDiffEntity.getCurrentEntityItem().getAttribute("AUDIEN");
/*  904 */     Vector<String> vector1 = new Vector(1);
/*  905 */     Vector<String> vector2 = new Vector(1);
/*  906 */     Vector[] arrayOfVector = new Vector[2];
/*  907 */     arrayOfVector[0] = vector1;
/*  908 */     arrayOfVector[1] = vector2;
/*  909 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getModelAudience cur audienceAtt " + eANFlagAttribute + NEWLINE);
/*  910 */     if (eANFlagAttribute != null) {
/*  911 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  912 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/*  914 */         if (arrayOfMetaFlag[b].isSelected()) {
/*  915 */           vector1.addElement(arrayOfMetaFlag[b].toString());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  920 */     if (!paramDiffEntity.isNew()) {
/*  921 */       eANFlagAttribute = (EANFlagAttribute)paramDiffEntity.getPriorEntityItem().getAttribute("AUDIEN");
/*  922 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getModelAudience new audienceAtt " + eANFlagAttribute + NEWLINE);
/*  923 */       if (eANFlagAttribute != null) {
/*  924 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  925 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  927 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  928 */             vector2.addElement(arrayOfMetaFlag[b].toString());
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  934 */     return arrayOfVector;
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
/*      */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 1104 */     ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs build T1 country list " + paramBoolean + " " + paramDiffEntity.getKey() + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1110 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 1111 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 1112 */     if (paramBoolean) {
/* 1113 */       if (!paramDiffEntity.isNew()) {
/*      */         
/* 1115 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 1116 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for deleted / update avail at T1: ctryAtt " + 
/* 1117 */             PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 1118 */         if (eANFlagAttribute != null) {
/* 1119 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1120 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/* 1122 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 1123 */               String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 1124 */               String str2 = str1;
/* 1125 */               if (paramTreeMap.containsKey(str2)) {
/*      */                 
/* 1127 */                 CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/* 1128 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted / update " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */               } else {
/*      */                 
/* 1131 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 1132 */                 ctryAudRecord.setAction("Delete");
/* 1133 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1140 */     } else if (!paramDiffEntity.isDeleted()) {
/*      */       
/* 1142 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 1143 */       ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for new /update avail:  ctryAtt and anncodeAtt " + 
/* 1144 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/* 1145 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/* 1146 */       if (eANFlagAttribute != null) {
/* 1147 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1148 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/* 1150 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 1151 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 1152 */             String str2 = str1;
/* 1153 */             if (paramTreeMap.containsKey(str2)) {
/* 1154 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/* 1155 */               if ("Delete".equals(ctryAudRecord.action)) {
/* 1156 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new /udpate" + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */                 
/* 1158 */                 ctryAudRecord.setUpdateAvail(paramDiffEntity);
/* 1159 */                 ctryAudRecord.setAction("@@");
/*      */               } 
/*      */             } else {
/* 1162 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 1163 */               ctryAudRecord.setAction("Update");
/* 1164 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 1165 */               ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 1166 */                   .getKey() + NEWLINE);
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
/*      */   private Vector getPlannedAvails(Hashtable paramHashtable, String paramString, StringBuffer paramStringBuffer) {
/* 1178 */     Vector<DiffEntity> vector1 = new Vector(1);
/*      */     
/* 1180 */     Vector<DiffEntity> vector2 = getSeoAndLseobundleAvail(paramHashtable, paramString, paramStringBuffer);
/*      */     
/* 1182 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/* 1183 */         .size())) + NEWLINE);
/* 1184 */     if (vector2 == null) {
/* 1185 */       return vector1;
/*      */     }
/*      */ 
/*      */     
/* 1189 */     for (byte b = 0; b < vector2.size(); b++) {
/* 1190 */       DiffEntity diffEntity = vector2.elementAt(b);
/* 1191 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 1192 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/* 1193 */       if (diffEntity.isDeleted()) {
/* 1194 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/* 1195 */             PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/* 1196 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/* 1197 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1198 */           vector1.add(diffEntity);
/*      */         }
/*      */       } else {
/* 1201 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/* 1202 */             PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/* 1203 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/* 1204 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1205 */           vector1.add(diffEntity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1210 */     return vector1;
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
/*      */   private DiffEntity getEntityForAttrs(Hashtable paramHashtable, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, StringBuffer paramStringBuffer) {
/* 1224 */     DiffEntity diffEntity = null;
/*      */     
/* 1226 */     Vector<DiffEntity> vector = getSeoAndLseobundleAvail(paramHashtable, paramString1, paramStringBuffer);
/*      */     
/* 1228 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs looking for " + paramString3 + ":" + paramString4 + " and " + paramString5 + ":" + paramString6 + " in " + paramString2 + " allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 1229 */         .size())) + NEWLINE);
/* 1230 */     if (vector == null) {
/* 1231 */       return diffEntity;
/*      */     }
/*      */     
/* 1234 */     for (byte b = 0; b < vector.size(); b++) {
/* 1235 */       DiffEntity diffEntity1 = vector.elementAt(b);
/* 1236 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/* 1237 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/* 1238 */       if (diffEntity1.isDeleted()) {
/* 1239 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString3 + ":" + 
/* 1240 */             PokUtils.getAttributeFlagValue(entityItem2, paramString3) + " " + paramString5 + ":" + 
/* 1241 */             PokUtils.getAttributeFlagValue(entityItem2, paramString5) + NEWLINE);
/* 1242 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString3);
/* 1243 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString4)) {
/* 1244 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString5);
/* 1245 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString6)) {
/* 1246 */             diffEntity = diffEntity1;
/*      */           }
/*      */         }
/*      */       
/* 1250 */       } else if (diffEntity1.isNew()) {
/* 1251 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString3 + ":" + 
/* 1252 */             PokUtils.getAttributeFlagValue(entityItem1, paramString3) + " " + paramString5 + ":" + 
/* 1253 */             PokUtils.getAttributeFlagValue(entityItem1, paramString5) + NEWLINE);
/* 1254 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString3);
/* 1255 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString4)) {
/* 1256 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString5);
/* 1257 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString6)) {
/* 1258 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1264 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: current " + diffEntity1.getKey() + " " + paramString3 + ":" + 
/* 1265 */             PokUtils.getAttributeFlagValue(entityItem1, paramString3) + " " + paramString5 + ":" + 
/* 1266 */             PokUtils.getAttributeFlagValue(entityItem1, paramString5) + NEWLINE);
/* 1267 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString3);
/* 1268 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString4)) {
/* 1269 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString5);
/* 1270 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString6)) {
/* 1271 */             diffEntity = diffEntity1;
/*      */             break;
/*      */           } 
/*      */         } 
/* 1275 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString3 + ":" + 
/* 1276 */             PokUtils.getAttributeFlagValue(entityItem2, paramString3) + " " + paramString5 + ":" + 
/* 1277 */             PokUtils.getAttributeFlagValue(entityItem2, paramString5) + NEWLINE);
/* 1278 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString3);
/* 1279 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString4)) {
/* 1280 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString5);
/* 1281 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString6)) {
/* 1282 */             diffEntity = diffEntity1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1290 */     return diffEntity;
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
/*      */   private boolean isDerivefromLSEOBUNDLE(DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 1308 */     boolean bool = false;
/* 1309 */     if (paramDiffEntity.getEntityType().equals("LSEOBUNDLE")) {
/* 1310 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 1311 */       if (entityItem != null) {
/* 1312 */         ABRUtil.append(paramStringBuffer, "XMLANNElem.DerivefromLSEO" + entityItem.getKey() + " SPECBID: " + 
/* 1313 */             PokUtils.getAttributeValue(entityItem, "SPECBID", ", ", "@@", false) + NEWLINE);
/* 1314 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("SPECBID");
/* 1315 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("11458")) {
/* 1316 */           bool = true;
/*      */         }
/*      */       } 
/*      */     } 
/* 1320 */     return bool;
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
/*      */   private DiffEntity getCatlgor(Hashtable paramHashtable, String paramString1, Vector[] paramArrayOfVector, String paramString2, StringBuffer paramStringBuffer) {
/* 1338 */     DiffEntity diffEntity = null;
/*      */     
/* 1340 */     Vector<DiffEntity> vector = getLseoAndLseobundleCatlgor(paramHashtable, paramString1, paramStringBuffer);
/*      */     
/* 1342 */     String str = "COUNTRYLIST";
/* 1343 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor looking for " + str + ":" + paramString2 + " in CATLGOR allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 1344 */         .size())) + NEWLINE);
/* 1345 */     if (vector == null) {
/* 1346 */       return diffEntity;
/*      */     }
/*      */ 
/*      */     
/* 1350 */     for (byte b = 0; b < vector.size(); b++) {
/* 1351 */       DiffEntity diffEntity1 = vector.elementAt(b);
/* 1352 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/* 1353 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/* 1354 */       if (diffEntity1.isDeleted()) {
/* 1355 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + str + ":" + 
/* 1356 */             PokUtils.getAttributeFlagValue(entityItem2, str) + NEWLINE);
/* 1357 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str);
/* 1358 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString2)) {
/* 1359 */           diffEntity = diffEntity1;
/*      */           
/*      */           break;
/*      */         } 
/* 1363 */       } else if (diffEntity1.isNew()) {
/* 1364 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: new " + diffEntity1.getKey() + " " + str + ":" + 
/* 1365 */             PokUtils.getAttributeFlagValue(entityItem1, str) + NEWLINE);
/* 1366 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str);
/* 1367 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString2)) {
/* 1368 */           diffEntity = diffEntity1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } else {
/* 1373 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: current " + diffEntity1.getKey() + " " + str + ":" + 
/* 1374 */             PokUtils.getAttributeFlagValue(entityItem1, str) + NEWLINE);
/* 1375 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str);
/* 1376 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString2)) {
/* 1377 */           diffEntity = diffEntity1;
/*      */           
/*      */           break;
/*      */         } 
/* 1381 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: prior " + diffEntity1.getKey() + " " + str + ":" + 
/* 1382 */             PokUtils.getAttributeFlagValue(entityItem2, str) + NEWLINE);
/* 1383 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str);
/* 1384 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString2)) {
/* 1385 */           diffEntity = diffEntity1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1392 */     return diffEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CtryAudRecord
/*      */     extends CtryRecord
/*      */   {
/*      */     public String country;
/*      */     
/*      */     private DiffEntity availDiff;
/*      */ 
/*      */     
/*      */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String) {
/* 1406 */       super((String)null);
/* 1407 */       this.country = param1String;
/* 1408 */       this.availDiff = param1DiffEntity;
/*      */     }
/*      */     
/*      */     void setUpdateAvail(DiffEntity param1DiffEntity) {
/* 1412 */       this.availDiff = param1DiffEntity;
/* 1413 */       setAction("Update");
/*      */     }
/*      */ 
/*      */     
/*      */     String getCountry() {
/* 1418 */       return this.country;
/*      */     }
/*      */     
/*      */     String getKey() {
/* 1422 */       return this.country;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1427 */       return (this.availDiff != null) ? (this.availDiff.getKey() + " action:" + this.action) : "There is no AVAIL! ";
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
/*      */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, DiffEntity param1DiffEntity6, Hashtable param1Hashtable, EntityItem param1EntityItem, DiffEntity param1DiffEntity7, String param1String, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/* 1453 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + NEWLINE);
/*      */ 
/*      */       
/* 1456 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + NEWLINE);
/*      */       
/* 1458 */       this.availStatus = "0020";
/* 1459 */       this.rfravailStatus = "0040";
/*      */ 
/*      */       
/* 1462 */       String[] arrayOfString1 = deriveAnnDate(false, param1StringBuffer);
/* 1463 */       String[] arrayOfString2 = deriveAnnDate(true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1467 */       String[] arrayOfString3 = deriveAnnNumber(false, param1StringBuffer);
/* 1468 */       String[] arrayOfString4 = deriveAnnNumber(true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1472 */       String[] arrayOfString5 = deriveFIRSTORDER(param1DiffEntity1, param1DiffEntity3, false, param1StringBuffer);
/* 1473 */       String[] arrayOfString6 = deriveFIRSTORDER(param1DiffEntity1, param1DiffEntity3, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1476 */       String[] arrayOfString7 = derivePlannedavailability(false, param1StringBuffer);
/* 1477 */       String[] arrayOfString8 = derivePlannedavailability(true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1490 */       String[] arrayOfString9 = derivePubFrom(param1DiffEntity1, param1DiffEntity2, param1DiffEntity3, false, param1StringBuffer);
/* 1491 */       String[] arrayOfString10 = derivePubFrom(param1DiffEntity1, param1DiffEntity2, param1DiffEntity3, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1495 */       String[] arrayOfString11 = derivePubTo(param1DiffEntity1, param1DiffEntity2, param1DiffEntity4, false, param1StringBuffer);
/* 1496 */       String[] arrayOfString12 = derivePubTo(param1DiffEntity1, param1DiffEntity2, param1DiffEntity4, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1501 */       String[] arrayOfString13 = deriveWDANNDATE(param1DiffEntity6, false, param1StringBuffer);
/* 1502 */       String[] arrayOfString14 = deriveWDANNDATE(param1DiffEntity6, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1506 */       String[] arrayOfString15 = deriveLastOrder(param1DiffEntity1, param1DiffEntity4, false, param1StringBuffer);
/* 1507 */       String[] arrayOfString16 = deriveLastOrder(param1DiffEntity1, param1DiffEntity4, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1510 */       String[] arrayOfString17 = deriveEOSANNDATE(param1DiffEntity5, false, param1StringBuffer);
/* 1511 */       String[] arrayOfString18 = deriveEOSANNDATE(param1DiffEntity5, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1514 */       String[] arrayOfString19 = deriveENDOFSERVICE(param1DiffEntity5, false, param1StringBuffer);
/* 1515 */       String[] arrayOfString20 = deriveENDOFSERVICE(param1DiffEntity5, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1518 */       handleResults(arrayOfString1, arrayOfString2, arrayOfString3, arrayOfString4, arrayOfString5, arrayOfString6, arrayOfString7, arrayOfString8, arrayOfString9, arrayOfString10, arrayOfString11, arrayOfString12, arrayOfString13, arrayOfString14, arrayOfString15, arrayOfString16, arrayOfString19, arrayOfString20, arrayOfString17, arrayOfString18, this.country, param1Boolean1, param1Boolean2, param1StringBuffer);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFSERVICE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1583 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveEndOfService  eofAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 1584 */           .getKey()) + " findT1:" + param1Boolean + NEWLINE);
/* 1585 */       String str1 = "@@";
/* 1586 */       String str2 = "@@";
/* 1587 */       String[] arrayOfString1 = new String[2];
/* 1588 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1590 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1592 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1593 */         str1 = arrayOfString2[0];
/* 1594 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1597 */       arrayOfString1[0] = str1;
/* 1598 */       arrayOfString1[1] = str2;
/* 1599 */       return arrayOfString1;
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
/*      */     private String[] derivePubTo(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1611 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubTo  loAvailDiff: " + ((param1DiffEntity3 == null) ? "null" : param1DiffEntity3.getKey()) + " findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 1614 */       String str1 = "@@";
/* 1615 */       String str2 = "@@";
/* 1616 */       String[] arrayOfString1 = new String[2];
/* 1617 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1619 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1621 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity1, param1DiffEntity2, str1, str2, this.country, "PUBTO", param1StringBuffer);
/* 1622 */         str1 = arrayOfString2[0];
/* 1623 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1626 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1628 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1629 */         str1 = arrayOfString2[0];
/* 1630 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1633 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1635 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "BUNDLUNPUBDATEMTRGT", param1StringBuffer);
/* 1636 */         str1 = arrayOfString2[0];
/* 1637 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1640 */       arrayOfString1[0] = str1;
/* 1641 */       arrayOfString1[1] = str2;
/* 1642 */       return arrayOfString1;
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
/*      */     private String[] derivePubFrom(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1659 */       String str1 = "@@";
/* 1660 */       String str2 = "@@";
/* 1661 */       String[] arrayOfString1 = new String[2];
/* 1662 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1664 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1666 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity1, param1DiffEntity2, str1, str2, this.country, "PUBFROM", param1StringBuffer);
/* 1667 */         str1 = arrayOfString2[0];
/* 1668 */         str2 = arrayOfString2[1];
/*      */       } 
/* 1670 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1672 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1673 */         str1 = arrayOfString2[0];
/* 1674 */         str2 = arrayOfString2[1];
/*      */       } 
/* 1676 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 1679 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 1680 */         str1 = arrayOfString2[0];
/* 1681 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1684 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1686 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1687 */         str1 = arrayOfString2[0];
/* 1688 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1691 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1693 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "BUNDLPUBDATEMTRGT", param1StringBuffer);
/* 1694 */         str1 = arrayOfString2[0];
/* 1695 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1698 */       arrayOfString1[0] = str1;
/* 1699 */       arrayOfString1[1] = str2;
/* 1700 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnNumber(boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1710 */       String str1 = "@@";
/* 1711 */       String str2 = "@@";
/* 1712 */       String[] arrayOfString1 = new String[2];
/* 1713 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1715 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1717 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/* 1718 */         str1 = arrayOfString2[0];
/* 1719 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1722 */       arrayOfString1[0] = str1;
/* 1723 */       arrayOfString1[1] = str2;
/* 1724 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnDate(boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1734 */       String str1 = "@@";
/* 1735 */       String str2 = "@@";
/* 1736 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 1738 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1740 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 1741 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 1742 */         str1 = arrayOfString2[0];
/* 1743 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1746 */       arrayOfString1[0] = str1;
/* 1747 */       arrayOfString1[1] = str2;
/* 1748 */       return arrayOfString1;
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
/*      */     private String[] deriveFIRSTORDER(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1760 */       String str1 = "@@";
/* 1761 */       String str2 = "@@";
/* 1762 */       String[] arrayOfString1 = new String[2];
/* 1763 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1765 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1767 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1768 */         str1 = arrayOfString2[0];
/* 1769 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1772 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1774 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 1775 */         str1 = arrayOfString2[0];
/* 1776 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1779 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1781 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "BUNDLPUBDATEMTRGT", param1StringBuffer);
/* 1782 */         str1 = arrayOfString2[0];
/* 1783 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1786 */       arrayOfString1[0] = str1;
/* 1787 */       arrayOfString1[1] = str2;
/* 1788 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveEOSANNDATE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1798 */       String str1 = "@@";
/* 1799 */       String str2 = "@@";
/* 1800 */       String[] arrayOfString1 = new String[2];
/* 1801 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1803 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1805 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity, str1, str2, this.country, "13", param1StringBuffer);
/* 1806 */         str1 = arrayOfString2[0];
/* 1807 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1810 */       arrayOfString1[0] = str1;
/* 1811 */       arrayOfString1[1] = str2;
/* 1812 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveWDANNDATE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1822 */       String str1 = "@@";
/* 1823 */       String str2 = "@@";
/* 1824 */       String[] arrayOfString1 = new String[2];
/* 1825 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1827 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1829 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity, str1, str2, this.country, "14", param1StringBuffer);
/* 1830 */         str1 = arrayOfString2[0];
/* 1831 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1834 */       arrayOfString1[0] = str1;
/* 1835 */       arrayOfString1[1] = str2;
/* 1836 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLastOrder(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1846 */       String str1 = "@@";
/* 1847 */       String str2 = "@@";
/* 1848 */       String[] arrayOfString1 = new String[2];
/* 1849 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1851 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1853 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1854 */         str1 = arrayOfString2[0];
/* 1855 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1858 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1860 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "BUNDLUNPUBDATEMTRGT", param1StringBuffer);
/* 1861 */         str1 = arrayOfString2[0];
/* 1862 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1865 */       arrayOfString1[0] = str1;
/* 1866 */       arrayOfString1[1] = str2;
/* 1867 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] derivePlannedavailability(boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1878 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePlannedavailability  availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff
/* 1879 */           .getKey()) + " findT1:" + param1Boolean + NEWLINE);
/* 1880 */       String str1 = "@@";
/* 1881 */       String str2 = "@@";
/* 1882 */       String[] arrayOfString1 = new String[2];
/* 1883 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1885 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1887 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1888 */         str1 = arrayOfString2[0];
/* 1889 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1892 */       arrayOfString1[0] = str1;
/* 1893 */       arrayOfString1[1] = str2;
/* 1894 */       return arrayOfString1;
/*      */     }
/*      */     
/*      */     void setAllLSEOFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/* 1898 */       this.availStatus = "0020";
/* 1899 */       this.rfravailStatus = "0040";
/*      */       
/* 1901 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for country is belong to LSEO " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1.getKey()) + ". catlgorDiff is " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + NEWLINE);
/*      */ 
/*      */       
/* 1904 */       String[] arrayOfString1 = { "@@", "@@" };
/* 1905 */       String[] arrayOfString2 = deriveLSEOPubFrom(param1DiffEntity1, param1DiffEntity2, false, param1StringBuffer);
/* 1906 */       String[] arrayOfString3 = deriveLSEOPubFrom(param1DiffEntity1, param1DiffEntity2, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1909 */       String[] arrayOfString4 = deriveLSEOFirstOrder(param1DiffEntity1, false, param1StringBuffer);
/* 1910 */       String[] arrayOfString5 = deriveLSEOFirstOrder(param1DiffEntity1, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1915 */       String[] arrayOfString6 = deriveLSEOplannedavailability(param1DiffEntity1, false, param1StringBuffer);
/* 1916 */       String[] arrayOfString7 = deriveLSEOplannedavailability(param1DiffEntity1, true, param1StringBuffer);
/*      */       
/* 1918 */       String[] arrayOfString8 = deriveLSEOLastOrder(param1DiffEntity1, false, param1StringBuffer);
/* 1919 */       String[] arrayOfString9 = deriveLSEOLastOrder(param1DiffEntity1, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1922 */       String[] arrayOfString10 = deriveLSEOPubTo(param1DiffEntity1, param1DiffEntity2, false, param1StringBuffer);
/* 1923 */       String[] arrayOfString11 = deriveLSEOPubTo(param1DiffEntity1, param1DiffEntity2, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1930 */       handleResults(arrayOfString4, arrayOfString5, arrayOfString1, arrayOfString1, arrayOfString4, arrayOfString5, arrayOfString6, arrayOfString7, arrayOfString2, arrayOfString3, arrayOfString10, arrayOfString11, arrayOfString8, arrayOfString9, arrayOfString8, arrayOfString9, arrayOfString1, arrayOfString1, arrayOfString1, arrayOfString1, this.country, param1Boolean1, param1Boolean2, param1StringBuffer);
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
/*      */     private String[] deriveLSEOPubFrom(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1954 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLSEOPubFrom catlgorDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 1955 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 1957 */       String str1 = "@@";
/* 1958 */       String str2 = "@@";
/* 1959 */       String[] arrayOfString1 = new String[2];
/* 1960 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1962 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1964 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity1, param1DiffEntity2, str1, str2, this.country, "PUBFROM", param1StringBuffer);
/* 1965 */         str1 = arrayOfString2[0];
/* 1966 */         str2 = arrayOfString2[1];
/*      */       } 
/* 1968 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1970 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "BUNDLPUBDATEMTRGT", param1StringBuffer);
/* 1971 */         str1 = arrayOfString2[0];
/* 1972 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1975 */       arrayOfString1[0] = str1;
/* 1976 */       arrayOfString1[1] = str2;
/* 1977 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLSEOplannedavailability(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1988 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.plannedavailability parentDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 1989 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 1991 */       String str1 = "@@";
/* 1992 */       String str2 = "@@";
/* 1993 */       String[] arrayOfString1 = new String[2];
/* 1994 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1996 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1998 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity, str1, str2, "BUNDLPUBDATEMTRGT", param1StringBuffer);
/* 1999 */         str1 = arrayOfString2[0];
/* 2000 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2003 */       arrayOfString1[0] = str1;
/* 2004 */       arrayOfString1[1] = str2;
/* 2005 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLSEOFirstOrder(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2015 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLSEOFirstOrder parentDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 2016 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2018 */       String str1 = "@@";
/* 2019 */       String str2 = "@@";
/* 2020 */       String[] arrayOfString1 = new String[2];
/* 2021 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2023 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 2026 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity, str1, str2, "BUNDLPUBDATEMTRGT", param1StringBuffer);
/* 2027 */         str1 = arrayOfString2[0];
/* 2028 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2030 */       arrayOfString1[0] = str1;
/* 2031 */       arrayOfString1[1] = str2;
/* 2032 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLSEOLastOrder(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2042 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLSEOLastOrder parentDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 2043 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2045 */       String str1 = "@@";
/* 2046 */       String str2 = "@@";
/* 2047 */       String[] arrayOfString1 = new String[2];
/* 2048 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2050 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2052 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity, str1, str2, "BUNDLUNPUBDATEMTRGT", param1StringBuffer);
/* 2053 */         str1 = arrayOfString2[0];
/* 2054 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2056 */       arrayOfString1[0] = str1;
/* 2057 */       arrayOfString1[1] = str2;
/* 2058 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLSEOPubTo(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2069 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLSEOPubTo catlgorDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 2070 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2072 */       String str1 = "@@";
/* 2073 */       String str2 = "@@";
/* 2074 */       String[] arrayOfString1 = new String[2];
/* 2075 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2077 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2079 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity1, param1DiffEntity2, str1, str2, this.country, "PUBTO", param1StringBuffer);
/* 2080 */         str1 = arrayOfString2[0];
/* 2081 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2083 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2085 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "BUNDLUNPUBDATEMTRGT", param1StringBuffer);
/* 2086 */         str1 = arrayOfString2[0];
/* 2087 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2090 */       arrayOfString1[0] = str1;
/* 2091 */       arrayOfString1[1] = str2;
/* 2092 */       return arrayOfString1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLLSEOBUNDELAVAILElembh1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */