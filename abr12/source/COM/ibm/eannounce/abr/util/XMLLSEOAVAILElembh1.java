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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLLSEOAVAILElembh1
/*      */   extends XMLElem
/*      */ {
/*  209 */   private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
/*  210 */   private String countryKey = "AVAILCOUNTRYLIST";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLLSEOAVAILElembh1() {
/*  230 */     super("AVAILABILITYELEMENT");
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
/*      */   public void addElements(Database paramDatabase, Hashtable<String, String> paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  246 */     String str1 = paramDiffEntity.getEntityType();
/*  247 */     String str2 = null;
/*      */     
/*  249 */     EntityItem entityItem = null;
/*  250 */     if (str1.equals("LSEO")) {
/*  251 */       str2 = "D:AVAILSLEORGA:D";
/*  252 */       if (paramDiffEntity.getCurrentEntityItem() != null) {
/*      */         
/*  254 */         Vector<EntityItem> vector = paramDiffEntity.getCurrentEntityItem().getUpLink();
/*  255 */         for (byte b = 0; b < vector.size(); b++) {
/*  256 */           EntityItem entityItem1 = vector.get(b);
/*  257 */           if (entityItem1 != null && "WWSEOLSEO".equals(entityItem1.getEntityType())) {
/*  258 */             EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/*  259 */             if (entityItem2 != null && "WWSEO".equals(entityItem2.getEntityType())) {
/*  260 */               Vector<EntityItem> vector1 = entityItem2.getUpLink();
/*  261 */               for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  262 */                 EntityItem entityItem3 = vector1.get(b1);
/*  263 */                 if (entityItem3 != null && "MODELWWSEO".equals(entityItem3.getEntityType())) {
/*  264 */                   entityItem = (EntityItem)entityItem3.getUpLink(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   // Byte code: goto -> 193
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  282 */     boolean bool1 = false;
/*  283 */     boolean bool = false;
/*  284 */     bool1 = AvailUtil.iscompatmodel();
/*      */     
/*  286 */     if (!bool1) {
/*      */       
/*  288 */       String str = null;
/*  289 */       str = (String)paramHashtable.get("_chSTATUS");
/*  290 */       ABRUtil.append(paramStringBuffer, "the status is" + str + NEWLINE);
/*  291 */       if ("0020".equals(str)) {
/*  292 */         bool = true;
/*      */       } else {
/*  294 */         bool = false;
/*      */       } 
/*      */     } 
/*  297 */     String str3 = "@@";
/*      */     
/*  299 */     boolean bool2 = isDerivefromLSEO(paramHashtable, paramDiffEntity, paramStringBuffer);
/*      */     
/*  301 */     if (bool2) {
/*  302 */       str2 = null;
/*      */ 
/*      */       
/*  305 */       str3 = "LSEOSPECBID";
/*  306 */       createNodeFromLSEO(paramHashtable, str3, paramDocument, paramElement, paramDiffEntity, entityItem, str2, bool, bool1, paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  323 */       str3 = "LSEO";
/*  324 */       Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, str3, paramStringBuffer);
/*      */       
/*  326 */       if (vector.size() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  333 */         TreeMap<Object, Object> treeMap = new TreeMap<>(); byte b;
/*  334 */         for (b = 0; b < vector.size(); b++) {
/*  335 */           DiffEntity diffEntity = vector.elementAt(b);
/*  336 */           buildCtryAudRecs(treeMap, diffEntity, true, paramStringBuffer);
/*      */         } 
/*      */ 
/*      */         
/*  340 */         for (b = 0; b < vector.size(); b++) {
/*  341 */           DiffEntity diffEntity = vector.elementAt(b);
/*  342 */           buildCtryAudRecs(treeMap, diffEntity, false, paramStringBuffer);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  347 */         Vector[] arrayOfVector = getModelAudience(paramDiffEntity, paramStringBuffer);
/*  348 */         Collection collection = treeMap.values();
/*  349 */         Iterator<CtryAudRecord> iterator = collection.iterator();
/*  350 */         StringBuffer stringBuffer = new StringBuffer();
/*  351 */         while (iterator.hasNext()) {
/*      */           
/*  353 */           CtryAudRecord ctryAudRecord = iterator.next();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  366 */           DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, str3, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/*  367 */               .getCountry(), paramStringBuffer);
/*      */           
/*  369 */           DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, str3, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  370 */               .getCountry(), paramStringBuffer);
/*  371 */           DiffEntity diffEntity3 = getEntityForAttrs(paramHashtable, str3, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/*  372 */               .getCountry(), paramStringBuffer);
/*      */           
/*  374 */           DiffEntity diffEntity4 = getEntityForAttrs(paramHashtable, str3, "AVAIL", "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRecord
/*  375 */               .getCountry(), paramStringBuffer);
/*      */           
/*  377 */           DiffEntity diffEntity5 = getCatlgor(paramHashtable, str3, arrayOfVector, ctryAudRecord.getCountry(), paramStringBuffer);
/*      */ 
/*      */           
/*  380 */           ctryAudRecord.setAllFields(paramDiffEntity, diffEntity5, diffEntity1, diffEntity2, diffEntity3, diffEntity4, paramHashtable, entityItem, ctryAudRecord.availDiff, str2, paramElement, bool, bool1, paramStringBuffer);
/*      */ 
/*      */           
/*  383 */           if (ctryAudRecord.isDisplayable() || ctryAudRecord.isrfrDisplayable()) {
/*      */             
/*  385 */             if ("LSEO".equals(str3)) {
/*  386 */               if (stringBuffer.length() == 0) {
/*  387 */                 stringBuffer.append(ctryAudRecord.getCountry());
/*      */               } else {
/*  389 */                 stringBuffer.append("|" + ctryAudRecord.getCountry());
/*      */               } 
/*      */             }
/*      */             
/*  393 */             createNodeSet(paramHashtable, paramDocument, paramElement, entityItem, ctryAudRecord.availDiff, ctryAudRecord, str2, str3, paramStringBuffer);
/*      */           } else {
/*  395 */             ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*      */           } 
/*  397 */           ctryAudRecord.dereference();
/*      */         } 
/*      */         
/*  400 */         if ("LSEO".equals(str3)) {
/*  401 */           paramHashtable.put(this.countryKey, stringBuffer.toString());
/*      */         }
/*  403 */         treeMap.clear();
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  409 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no planned AVAILs found" + NEWLINE);
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
/*  428 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("AVAIL");
/*      */     
/*  430 */     Vector<DiffEntity> vector2 = new Vector(1);
/*  431 */     if (vector1 != null) {
/*  432 */       if (paramString.equals("LSEOBUNDLE") || paramString.equals("LSEOBUNDLESPECBID")) {
/*  433 */         vector2 = vector1;
/*      */       } else {
/*  435 */         for (byte b = 0; b < vector1.size(); b++) {
/*  436 */           DiffEntity diffEntity = vector1.elementAt(b);
/*  437 */           if (diffEntity.toString().indexOf("MODELAVAIL") > -1) {
/*  438 */             if (paramString.equals("LSEOSPECBID"))
/*      */             {
/*  440 */               vector2.add(diffEntity);
/*      */             }
/*  442 */           } else if (diffEntity.toString().indexOf("LSEOAVAIL") > -1 && 
/*  443 */             paramString.equals("LSEO")) {
/*  444 */             vector2.add(diffEntity);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  450 */     return vector2;
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
/*      */   private Vector getLseoAndLseobundleCatlgor(Hashtable paramHashtable, String paramString, StringBuffer paramStringBuffer) {
/*  465 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("BHCATLGOR");
/*  466 */     Vector<DiffEntity> vector2 = new Vector(1);
/*  467 */     DiffEntity diffEntity = null;
/*  468 */     EntityItem entityItem = null;
/*  469 */     String str = "";
/*      */     
/*  471 */     if (vector1 != null) {
/*  472 */       if (paramString.equals("LSEOBUNDLE") || paramString.equals("LSEOBUNDLESPECBID")) {
/*  473 */         for (byte b = 0; b < vector1.size(); b++) {
/*  474 */           diffEntity = vector1.elementAt(b);
/*  475 */           entityItem = diffEntity.getCurrentEntityItem();
/*  476 */           str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  477 */           if ("0020".equals(str)) {
/*  478 */             vector2.add(diffEntity);
/*      */           }
/*      */         } 
/*      */       } else {
/*  482 */         for (byte b = 0; b < vector1.size(); b++) {
/*  483 */           diffEntity = vector1.elementAt(b);
/*  484 */           entityItem = diffEntity.getCurrentEntityItem();
/*  485 */           str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  486 */           if ("0020".equals(str) && 
/*  487 */             !diffEntity.getKey().equals("BHCATLGOR") && 
/*  488 */             diffEntity.toString().indexOf("LSEOBHCATLGOR") > -1) {
/*  489 */             vector2.add(diffEntity);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  496 */     return vector2;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeFromLSEO(Hashtable<String, String> paramHashtable, String paramString1, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, EntityItem paramEntityItem, String paramString2, boolean paramBoolean1, boolean paramBoolean2, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException {
/*  855 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  856 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  857 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*      */     
/*  859 */     if (paramDiffEntity.isNew()) {
/*      */       
/*  861 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  862 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for new lseo: ctryAtt " + 
/*  863 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/*  864 */       if (eANFlagAttribute != null) {
/*  865 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  866 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  868 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  869 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  870 */             String str2 = str1;
/*  871 */             if (treeMap.containsKey(str2)) {
/*      */               
/*  873 */               CtryAudRecord ctryAudRecord = (CtryAudRecord)treeMap.get(str2);
/*  874 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for New " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */             } else {
/*      */               
/*  877 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str1);
/*  878 */               ctryAudRecord.setAction("Update");
/*  879 */               treeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  880 */               ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for New:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  881 */                   .getKey() + NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  886 */     } else if (!paramDiffEntity.isDeleted()) {
/*  887 */       HashSet<String> hashSet1 = new HashSet();
/*  888 */       HashSet<String> hashSet2 = new HashSet();
/*      */       
/*  890 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  891 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for current lseo: ctryAtt " + 
/*  892 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/*  893 */       if (eANFlagAttribute != null) {
/*  894 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  895 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  897 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  898 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  899 */             hashSet2.add(str);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  905 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  906 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for prior lseo: ctryAtt " + 
/*  907 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/*  908 */       if (eANFlagAttribute != null) {
/*  909 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  910 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  912 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  913 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  914 */             hashSet1.add(str);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  919 */       Iterator<String> iterator1 = hashSet2.iterator();
/*  920 */       while (iterator1.hasNext()) {
/*  921 */         String str = iterator1.next();
/*  922 */         if (!hashSet1.contains(str)) {
/*      */           
/*  924 */           if (treeMap.containsKey(str)) {
/*  925 */             CtryAudRecord ctryAudRecord2 = (CtryAudRecord)treeMap.get(str);
/*  926 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for added ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryAudRecord2 + NEWLINE);
/*      */             continue;
/*      */           } 
/*  929 */           CtryAudRecord ctryAudRecord1 = new CtryAudRecord(null, str);
/*  930 */           ctryAudRecord1.setAction("Update");
/*  931 */           treeMap.put(ctryAudRecord1.getKey(), ctryAudRecord1);
/*  932 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord1
/*  933 */               .getKey() + NEWLINE);
/*      */           
/*      */           continue;
/*      */         } 
/*  937 */         if (treeMap.containsKey(str)) {
/*  938 */           CtryAudRecord ctryAudRecord1 = (CtryAudRecord)treeMap.get(str);
/*  939 */           ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for existing ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */           continue;
/*      */         } 
/*  942 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str);
/*  943 */         treeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  944 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  945 */             .getKey() + NEWLINE);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  950 */       iterator1 = hashSet1.iterator();
/*  951 */       while (iterator1.hasNext()) {
/*  952 */         String str = iterator1.next();
/*  953 */         if (!hashSet2.contains(str)) {
/*      */           
/*  955 */           if (treeMap.containsKey(str)) {
/*  956 */             CtryAudRecord ctryAudRecord1 = (CtryAudRecord)treeMap.get(str);
/*  957 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */             continue;
/*      */           } 
/*  960 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str);
/*  961 */           ctryAudRecord.setAction("Delete");
/*  962 */           treeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  963 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for deleted ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  964 */               .getKey() + NEWLINE);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  970 */     Vector[] arrayOfVector = getModelAudience(paramDiffEntity, paramStringBuffer);
/*  971 */     Collection collection = treeMap.values();
/*  972 */     Iterator<CtryAudRecord> iterator = collection.iterator();
/*      */     
/*  974 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  976 */     while (iterator.hasNext()) {
/*      */       
/*  978 */       CtryAudRecord ctryAudRecord = iterator.next();
/*  979 */       DiffEntity diffEntity = getCatlgor(paramHashtable, paramString1, arrayOfVector, ctryAudRecord.getCountry(), paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  986 */       ctryAudRecord.setAllLSEOFields(paramDiffEntity, diffEntity, paramHashtable, paramEntityItem, paramString2, paramElement, paramBoolean1, paramBoolean2, paramStringBuffer);
/*      */       
/*  988 */       if (ctryAudRecord.isDisplayable() || ctryAudRecord.isrfrDisplayable()) {
/*  989 */         createNodeSet(paramHashtable, paramDocument, paramElement, paramEntityItem, paramDiffEntity, ctryAudRecord, paramString2, paramString1, paramStringBuffer);
/*      */         
/*  991 */         if (stringBuffer.length() == 0) {
/*  992 */           stringBuffer.append(ctryAudRecord.getCountry());
/*      */         } else {
/*  994 */           stringBuffer.append("|" + ctryAudRecord.getCountry());
/*      */         } 
/*      */       } else {
/*      */         
/*  998 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no changes found for " + ctryAudRecord.country + NEWLINE);
/*      */       } 
/* 1000 */       ctryAudRecord.dereference();
/*      */     } 
/*      */     
/* 1003 */     paramHashtable.put(this.countryKey, stringBuffer.toString());
/*      */     
/* 1005 */     treeMap.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeSet(Hashtable paramHashtable, Document paramDocument, Element paramElement, EntityItem paramEntityItem, DiffEntity paramDiffEntity, CtryAudRecord paramCtryAudRecord, String paramString1, String paramString2, StringBuffer paramStringBuffer) {
/* 1014 */     if (paramCtryAudRecord.isDisplayable()) {
/* 1015 */       Element element1 = paramDocument.createElement(this.nodeName);
/* 1016 */       addXMLAttrs(element1);
/* 1017 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/* 1020 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/* 1021 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/* 1022 */       element1.appendChild(element2);
/* 1023 */       element2 = paramDocument.createElement("STATUS");
/* 1024 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/* 1025 */       element1.appendChild(element2);
/* 1026 */       element2 = paramDocument.createElement("COUNTRY_FC");
/* 1027 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/* 1028 */       element1.appendChild(element2);
/* 1029 */       element2 = paramDocument.createElement("ANNDATE");
/* 1030 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnndate()));
/* 1031 */       element1.appendChild(element2);
/* 1032 */       element2 = paramDocument.createElement("ANNNUMBER");
/* 1033 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnnnumber()));
/* 1034 */       element1.appendChild(element2);
/* 1035 */       element2 = paramDocument.createElement("FIRSTORDER");
/* 1036 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getFirstorder()));
/* 1037 */       element1.appendChild(element2);
/* 1038 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/* 1039 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/* 1040 */       element1.appendChild(element2);
/* 1041 */       element2 = paramDocument.createElement("PUBFROM");
/* 1042 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/* 1043 */       element1.appendChild(element2);
/* 1044 */       element2 = paramDocument.createElement("PUBTO");
/* 1045 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/* 1046 */       element1.appendChild(element2);
/* 1047 */       element2 = paramDocument.createElement("WDANNDATE");
/* 1048 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getWdanndate()));
/* 1049 */       element1.appendChild(element2);
/* 1050 */       element2 = paramDocument.createElement("LASTORDER");
/* 1051 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getLastorder()));
/* 1052 */       element1.appendChild(element2);
/* 1053 */       element2 = paramDocument.createElement("EOSANNDATE");
/* 1054 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosanndate()));
/* 1055 */       element1.appendChild(element2);
/* 1056 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/* 1057 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/* 1058 */       element1.appendChild(element2);
/*      */       
/* 1060 */       if (paramString2.equals("LSEO")) {
/* 1061 */         SLEORGGRP.displayAVAILSLEORGLSEO(paramHashtable, paramDocument, element1, paramEntityItem, paramDiffEntity, paramString1, paramCtryAudRecord.country, paramCtryAudRecord
/* 1062 */             .getAction(), paramString2, paramStringBuffer);
/*      */       } else {
/* 1064 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramEntityItem, paramDiffEntity, paramString1, paramCtryAudRecord.country, paramCtryAudRecord
/* 1065 */             .getAction(), paramStringBuffer);
/*      */       } 
/*      */     } 
/*      */     
/* 1069 */     if (paramCtryAudRecord.isrfrDisplayable()) {
/* 1070 */       Element element1 = paramDocument.createElement(this.nodeName);
/*      */       
/* 1072 */       addXMLAttrs(element1);
/* 1073 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/* 1076 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/* 1077 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfraction()));
/* 1078 */       element1.appendChild(element2);
/* 1079 */       element2 = paramDocument.createElement("STATUS");
/* 1080 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfravailStatus()));
/* 1081 */       element1.appendChild(element2);
/* 1082 */       element2 = paramDocument.createElement("COUNTRY_FC");
/* 1083 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/* 1084 */       element1.appendChild(element2);
/* 1085 */       element2 = paramDocument.createElement("ANNDATE");
/* 1086 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfranndate()));
/* 1087 */       element1.appendChild(element2);
/* 1088 */       element2 = paramDocument.createElement("ANNNUMBER");
/* 1089 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrannnumber()));
/* 1090 */       element1.appendChild(element2);
/* 1091 */       element2 = paramDocument.createElement("FIRSTORDER");
/* 1092 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrfirstorder()));
/* 1093 */       element1.appendChild(element2);
/* 1094 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/* 1095 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrplannedavailability()));
/* 1096 */       element1.appendChild(element2);
/* 1097 */       element2 = paramDocument.createElement("PUBFROM");
/* 1098 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubfrom()));
/* 1099 */       element1.appendChild(element2);
/* 1100 */       element2 = paramDocument.createElement("PUBTO");
/* 1101 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubto()));
/* 1102 */       element1.appendChild(element2);
/* 1103 */       element2 = paramDocument.createElement("WDANNDATE");
/* 1104 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrwdanndate()));
/* 1105 */       element1.appendChild(element2);
/* 1106 */       element2 = paramDocument.createElement("LASTORDER");
/* 1107 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrlastorder()));
/* 1108 */       element1.appendChild(element2);
/* 1109 */       element2 = paramDocument.createElement("EOSANNDATE");
/* 1110 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosanndate()));
/* 1111 */       element1.appendChild(element2);
/* 1112 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/* 1113 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrendofservice()));
/* 1114 */       element1.appendChild(element2);
/*      */       
/* 1116 */       if (paramString2.equals("LSEO")) {
/* 1117 */         SLEORGGRP.displayAVAILSLEORGLSEO(paramHashtable, paramDocument, element1, paramEntityItem, paramDiffEntity, paramString1, paramCtryAudRecord.country, paramCtryAudRecord
/* 1118 */             .getAction(), paramString2, paramStringBuffer);
/*      */       } else {
/* 1120 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramEntityItem, paramDiffEntity, paramString1, paramCtryAudRecord.country, paramCtryAudRecord
/* 1121 */             .getAction(), paramStringBuffer);
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
/* 1143 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getModelAudience for " + paramDiffEntity.getKey() + NEWLINE);
/*      */     
/* 1145 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramDiffEntity.getCurrentEntityItem().getAttribute("AUDIEN");
/* 1146 */     Vector<String> vector1 = new Vector(1);
/* 1147 */     Vector<String> vector2 = new Vector(1);
/* 1148 */     Vector[] arrayOfVector = new Vector[2];
/* 1149 */     arrayOfVector[0] = vector1;
/* 1150 */     arrayOfVector[1] = vector2;
/* 1151 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getModelAudience cur audienceAtt " + eANFlagAttribute + NEWLINE);
/* 1152 */     if (eANFlagAttribute != null) {
/* 1153 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1154 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 1156 */         if (arrayOfMetaFlag[b].isSelected()) {
/* 1157 */           vector1.addElement(arrayOfMetaFlag[b].toString());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1162 */     if (!paramDiffEntity.isNew()) {
/* 1163 */       eANFlagAttribute = (EANFlagAttribute)paramDiffEntity.getPriorEntityItem().getAttribute("AUDIEN");
/* 1164 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getModelAudience new audienceAtt " + eANFlagAttribute + NEWLINE);
/* 1165 */       if (eANFlagAttribute != null) {
/* 1166 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1167 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/* 1169 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 1170 */             vector2.addElement(arrayOfMetaFlag[b].toString());
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1176 */     return arrayOfVector;
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
/*      */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 1339 */     ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs build T1 country list " + paramBoolean + " " + paramDiffEntity.getKey() + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1345 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 1346 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 1347 */     if (paramBoolean) {
/* 1348 */       if (!paramDiffEntity.isNew()) {
/*      */         
/* 1350 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 1351 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for deleted / update avail at T1: ctryAtt " + 
/* 1352 */             PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 1353 */         if (eANFlagAttribute != null) {
/* 1354 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1355 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/* 1357 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 1358 */               String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 1359 */               String str2 = str1;
/* 1360 */               if (paramTreeMap.containsKey(str2)) {
/*      */                 
/* 1362 */                 CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/* 1363 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted / update " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */               } else {
/*      */                 
/* 1366 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 1367 */                 ctryAudRecord.setAction("Delete");
/* 1368 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1375 */     } else if (!paramDiffEntity.isDeleted()) {
/*      */       
/* 1377 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 1378 */       ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for new /update avail:  ctryAtt and anncodeAtt " + 
/* 1379 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/* 1380 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/* 1381 */       if (eANFlagAttribute != null) {
/* 1382 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1383 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/* 1385 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 1386 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 1387 */             String str2 = str1;
/* 1388 */             if (paramTreeMap.containsKey(str2)) {
/* 1389 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/* 1390 */               if ("Delete".equals(ctryAudRecord.action)) {
/* 1391 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new /udpate" + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */                 
/* 1393 */                 ctryAudRecord.setUpdateAvail(paramDiffEntity);
/* 1394 */                 ctryAudRecord.setAction("@@");
/*      */               } 
/*      */             } else {
/* 1397 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 1398 */               ctryAudRecord.setAction("Update");
/* 1399 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 1400 */               ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 1401 */                   .getKey() + NEWLINE);
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
/*      */   private Vector getPlannedAvails(Hashtable paramHashtable, String paramString, StringBuffer paramStringBuffer) {
/* 1414 */     Vector<DiffEntity> vector1 = new Vector(1);
/*      */     
/* 1416 */     Vector<DiffEntity> vector2 = getSeoAndLseobundleAvail(paramHashtable, paramString, paramStringBuffer);
/*      */     
/* 1418 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/* 1419 */         .size())) + NEWLINE);
/* 1420 */     if (vector2 == null) {
/* 1421 */       return vector1;
/*      */     }
/*      */ 
/*      */     
/* 1425 */     for (byte b = 0; b < vector2.size(); b++) {
/* 1426 */       DiffEntity diffEntity = vector2.elementAt(b);
/* 1427 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 1428 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/* 1429 */       if (diffEntity.isDeleted()) {
/* 1430 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/* 1431 */             PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/* 1432 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/* 1433 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1434 */           vector1.add(diffEntity);
/*      */         }
/*      */       } else {
/* 1437 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/* 1438 */             PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/* 1439 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/* 1440 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1441 */           vector1.add(diffEntity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1446 */     return vector1;
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
/* 1460 */     DiffEntity diffEntity = null;
/*      */     
/* 1462 */     Vector<DiffEntity> vector = getSeoAndLseobundleAvail(paramHashtable, paramString1, paramStringBuffer);
/*      */     
/* 1464 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs looking for " + paramString3 + ":" + paramString4 + " and " + paramString5 + ":" + paramString6 + " in " + paramString2 + " allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 1465 */         .size())) + NEWLINE);
/* 1466 */     if (vector == null) {
/* 1467 */       return diffEntity;
/*      */     }
/*      */     
/* 1470 */     for (byte b = 0; b < vector.size(); b++) {
/* 1471 */       DiffEntity diffEntity1 = vector.elementAt(b);
/* 1472 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/* 1473 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/* 1474 */       if (diffEntity1.isDeleted()) {
/* 1475 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString3 + ":" + 
/* 1476 */             PokUtils.getAttributeFlagValue(entityItem2, paramString3) + " " + paramString5 + ":" + 
/* 1477 */             PokUtils.getAttributeFlagValue(entityItem2, paramString5) + NEWLINE);
/* 1478 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString3);
/* 1479 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString4)) {
/* 1480 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString5);
/* 1481 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString6)) {
/* 1482 */             diffEntity = diffEntity1;
/*      */           }
/*      */         }
/*      */       
/* 1486 */       } else if (diffEntity1.isNew()) {
/* 1487 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString3 + ":" + 
/* 1488 */             PokUtils.getAttributeFlagValue(entityItem1, paramString3) + " " + paramString5 + ":" + 
/* 1489 */             PokUtils.getAttributeFlagValue(entityItem1, paramString5) + NEWLINE);
/* 1490 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString3);
/* 1491 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString4)) {
/* 1492 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString5);
/* 1493 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString6)) {
/* 1494 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1500 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: current " + diffEntity1.getKey() + " " + paramString3 + ":" + 
/* 1501 */             PokUtils.getAttributeFlagValue(entityItem1, paramString3) + " " + paramString5 + ":" + 
/* 1502 */             PokUtils.getAttributeFlagValue(entityItem1, paramString5) + NEWLINE);
/* 1503 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString3);
/* 1504 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString4)) {
/* 1505 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString5);
/* 1506 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString6)) {
/* 1507 */             diffEntity = diffEntity1;
/*      */             break;
/*      */           } 
/*      */         } 
/* 1511 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString3 + ":" + 
/* 1512 */             PokUtils.getAttributeFlagValue(entityItem2, paramString3) + " " + paramString5 + ":" + 
/* 1513 */             PokUtils.getAttributeFlagValue(entityItem2, paramString5) + NEWLINE);
/* 1514 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString3);
/* 1515 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString4)) {
/* 1516 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString5);
/* 1517 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString6)) {
/* 1518 */             diffEntity = diffEntity1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1526 */     return diffEntity;
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
/*      */   private boolean isDerivefromLSEO(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 1542 */     boolean bool = false;
/* 1543 */     if (paramDiffEntity.getEntityType().equals("LSEO")) {
/* 1544 */       Vector<DiffEntity> vector = (Vector)paramHashtable.get("WWSEO");
/* 1545 */       ABRUtil.append(paramStringBuffer, "DerivefromLSEO looking for WWSEO.SPECBID. allVct.size:" + ((vector == null) ? "null" : ("" + vector.size())) + NEWLINE);
/*      */       
/* 1547 */       if (vector != null) {
/* 1548 */         if (vector.size() == 0) {
/* 1549 */           ABRUtil.append(paramStringBuffer, "DerivefromLSEO No entities found for WWSEO" + NEWLINE);
/*      */         } else {
/*      */           
/* 1552 */           for (byte b = 0; b < vector.size(); b++) {
/* 1553 */             DiffEntity diffEntity = vector.elementAt(b);
/* 1554 */             EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 1555 */             if (!diffEntity.isDeleted()) {
/* 1556 */               ABRUtil.append(paramStringBuffer, "XMLANNElem.DerivefromLSEO WWSEO checking[" + b + "]:New or Update" + diffEntity.getKey() + " SPECBID: " + 
/* 1557 */                   PokUtils.getAttributeValue(entityItem, "SPECBID", ", ", "@@", false) + NEWLINE);
/* 1558 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("SPECBID");
/* 1559 */               if (eANFlagAttribute != null && !eANFlagAttribute.isSelected("11457")) {
/* 1560 */                 bool = true;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1569 */     return bool;
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
/*      */   private DiffEntity getCatlgor(Hashtable paramHashtable, String paramString1, Vector[] paramArrayOfVector, String paramString2, StringBuffer paramStringBuffer) {
/* 1616 */     DiffEntity diffEntity = null;
/*      */     
/* 1618 */     Vector<DiffEntity> vector = getLseoAndLseobundleCatlgor(paramHashtable, paramString1, paramStringBuffer);
/*      */     
/* 1620 */     String str = "COUNTRYLIST";
/* 1621 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor looking for " + str + ":" + paramString2 + " in CATLGOR allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 1622 */         .size())) + NEWLINE);
/* 1623 */     if (vector == null) {
/* 1624 */       return diffEntity;
/*      */     }
/*      */ 
/*      */     
/* 1628 */     for (byte b = 0; b < vector.size(); b++) {
/* 1629 */       DiffEntity diffEntity1 = vector.elementAt(b);
/* 1630 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/* 1631 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/* 1632 */       if (diffEntity1.isDeleted()) {
/* 1633 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + str + ":" + 
/* 1634 */             PokUtils.getAttributeFlagValue(entityItem2, str) + NEWLINE);
/* 1635 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str);
/* 1636 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString2)) {
/* 1637 */           diffEntity = diffEntity1;
/*      */           
/*      */           break;
/*      */         } 
/* 1641 */       } else if (diffEntity1.isNew()) {
/* 1642 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: new " + diffEntity1.getKey() + " " + str + ":" + 
/* 1643 */             PokUtils.getAttributeFlagValue(entityItem1, str) + NEWLINE);
/* 1644 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str);
/* 1645 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString2)) {
/* 1646 */           diffEntity = diffEntity1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } else {
/* 1651 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: current " + diffEntity1.getKey() + " " + str + ":" + 
/* 1652 */             PokUtils.getAttributeFlagValue(entityItem1, str) + NEWLINE);
/*      */         
/* 1654 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str);
/* 1655 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString2)) {
/* 1656 */           diffEntity = diffEntity1;
/*      */           
/*      */           break;
/*      */         } 
/* 1660 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: prior " + diffEntity1.getKey() + " " + str + ":" + 
/* 1661 */             PokUtils.getAttributeFlagValue(entityItem2, str) + NEWLINE);
/* 1662 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str);
/* 1663 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString2)) {
/* 1664 */           diffEntity = diffEntity1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1671 */     return diffEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CtryAudRecord
/*      */     extends CtryRecord
/*      */   {
/*      */     public String country;
/*      */ 
/*      */     
/*      */     private DiffEntity availDiff;
/*      */ 
/*      */     
/*      */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String) {
/* 1686 */       super((String)null);
/* 1687 */       this.country = param1String;
/* 1688 */       this.availDiff = param1DiffEntity;
/*      */     }
/*      */     
/*      */     void setUpdateAvail(DiffEntity param1DiffEntity) {
/* 1692 */       this.availDiff = param1DiffEntity;
/* 1693 */       setAction("Update");
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
/*      */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, DiffEntity param1DiffEntity6, Hashtable param1Hashtable, EntityItem param1EntityItem, DiffEntity param1DiffEntity7, String param1String, Element param1Element, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/* 1718 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + NEWLINE);
/*      */       
/* 1720 */       this.availStatus = "0020";
/* 1721 */       this.rfravailStatus = "0040";
/*      */ 
/*      */       
/* 1724 */       String[] arrayOfString1 = deriveAnnDate(false, param1StringBuffer);
/* 1725 */       String[] arrayOfString2 = deriveAnnDate(true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1729 */       String[] arrayOfString3 = deriveAnnNumber(false, param1StringBuffer);
/* 1730 */       String[] arrayOfString4 = deriveAnnNumber(true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1734 */       String[] arrayOfString5 = deriveFIRSTORDER(param1DiffEntity1, param1DiffEntity3, false, param1StringBuffer);
/* 1735 */       String[] arrayOfString6 = deriveFIRSTORDER(param1DiffEntity1, param1DiffEntity3, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1738 */       String[] arrayOfString7 = derivePlannedavailability(false, param1StringBuffer);
/* 1739 */       String[] arrayOfString8 = derivePlannedavailability(true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1751 */       String[] arrayOfString9 = derivePubFrom(param1DiffEntity1, param1DiffEntity2, param1DiffEntity3, false, param1StringBuffer);
/* 1752 */       String[] arrayOfString10 = derivePubFrom(param1DiffEntity1, param1DiffEntity2, param1DiffEntity3, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1755 */       String[] arrayOfString11 = derivePubTo(param1DiffEntity1, param1DiffEntity2, param1DiffEntity4, false, param1StringBuffer);
/* 1756 */       String[] arrayOfString12 = derivePubTo(param1DiffEntity1, param1DiffEntity2, param1DiffEntity4, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1760 */       String[] arrayOfString13 = deriveWDANNDATE(param1DiffEntity6, false, param1StringBuffer);
/* 1761 */       String[] arrayOfString14 = deriveWDANNDATE(param1DiffEntity6, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/* 1765 */       String[] arrayOfString15 = deriveLastOrder(param1DiffEntity1, param1DiffEntity4, false, param1StringBuffer);
/* 1766 */       String[] arrayOfString16 = deriveLastOrder(param1DiffEntity1, param1DiffEntity4, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1769 */       String[] arrayOfString17 = deriveEOSANNDATE(param1DiffEntity5, false, param1StringBuffer);
/* 1770 */       String[] arrayOfString18 = deriveEOSANNDATE(param1DiffEntity5, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1773 */       String[] arrayOfString19 = deriveENDOFSERVICE(param1DiffEntity5, false, param1StringBuffer);
/* 1774 */       String[] arrayOfString20 = deriveENDOFSERVICE(param1DiffEntity5, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1777 */       handleResults(arrayOfString1, arrayOfString2, arrayOfString3, arrayOfString4, arrayOfString5, arrayOfString6, arrayOfString7, arrayOfString8, arrayOfString9, arrayOfString10, arrayOfString11, arrayOfString12, arrayOfString13, arrayOfString14, arrayOfString15, arrayOfString16, arrayOfString19, arrayOfString20, arrayOfString17, arrayOfString18, this.country, param1Boolean1, param1Boolean2, param1StringBuffer);
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
/*      */     void setAllLSEOFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, Hashtable param1Hashtable, EntityItem param1EntityItem, String param1String, Element param1Element, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/* 1836 */       this.availStatus = "0020";
/* 1837 */       this.rfravailStatus = "0040";
/*      */       
/* 1839 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for country is belong to LSEO " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1.getKey()) + ". catlgorDiff is " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + NEWLINE);
/*      */ 
/*      */       
/* 1842 */       String[] arrayOfString1 = { "@@", "@@" };
/* 1843 */       String[] arrayOfString2 = deriveLSEOPubFrom(param1DiffEntity1, param1DiffEntity2, false, param1StringBuffer);
/* 1844 */       String[] arrayOfString3 = deriveLSEOPubFrom(param1DiffEntity1, param1DiffEntity2, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1847 */       String[] arrayOfString4 = deriveLSEOFirstOrder(param1DiffEntity1, false, param1StringBuffer);
/* 1848 */       String[] arrayOfString5 = deriveLSEOFirstOrder(param1DiffEntity1, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1855 */       String[] arrayOfString6 = deriveLSEOLastOrder(param1DiffEntity1, false, param1StringBuffer);
/* 1856 */       String[] arrayOfString7 = deriveLSEOLastOrder(param1DiffEntity1, true, param1StringBuffer);
/*      */ 
/*      */       
/* 1859 */       String[] arrayOfString8 = deriveLSEOPubTo(param1DiffEntity1, param1DiffEntity2, false, param1StringBuffer);
/* 1860 */       String[] arrayOfString9 = deriveLSEOPubTo(param1DiffEntity1, param1DiffEntity2, true, param1StringBuffer);
/*      */       
/* 1862 */       handleResults(arrayOfString4, arrayOfString5, arrayOfString1, arrayOfString1, arrayOfString4, arrayOfString5, arrayOfString2, arrayOfString3, arrayOfString2, arrayOfString3, arrayOfString8, arrayOfString9, arrayOfString6, arrayOfString7, arrayOfString6, arrayOfString7, arrayOfString1, arrayOfString1, arrayOfString1, arrayOfString1, this.country, param1Boolean1, param1Boolean2, param1StringBuffer);
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
/* 1886 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLSEOPubFrom catlgorDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 1887 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 1889 */       String str1 = "@@";
/* 1890 */       String str2 = "@@";
/* 1891 */       String[] arrayOfString1 = new String[2];
/* 1892 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1894 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1896 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity1, param1DiffEntity2, str1, str2, this.country, "PUBFROM", param1StringBuffer);
/* 1897 */         str1 = arrayOfString2[0];
/* 1898 */         str2 = arrayOfString2[1];
/*      */       } 
/* 1900 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 1903 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "LSEOPUBDATEMTRGT", param1StringBuffer);
/* 1904 */         str1 = arrayOfString2[0];
/* 1905 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1908 */       arrayOfString1[0] = str1;
/* 1909 */       arrayOfString1[1] = str2;
/* 1910 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLSEOFirstOrder(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1920 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLSEOFirstOrder parentDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 1921 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 1923 */       String str1 = "@@";
/* 1924 */       String str2 = "@@";
/* 1925 */       String[] arrayOfString1 = new String[2];
/* 1926 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1928 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */ 
/*      */         
/* 1932 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity, str1, str2, "LSEOPUBDATEMTRGT", param1StringBuffer);
/* 1933 */         str1 = arrayOfString2[0];
/* 1934 */         str2 = arrayOfString2[1];
/*      */       } 
/* 1936 */       arrayOfString1[0] = str1;
/* 1937 */       arrayOfString1[1] = str2;
/* 1938 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLSEOLastOrder(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1948 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLSEOLastOrder parentDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 1949 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 1951 */       String str1 = "@@";
/* 1952 */       String str2 = "@@";
/* 1953 */       String[] arrayOfString1 = new String[2];
/* 1954 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1956 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 1959 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity, str1, str2, "LSEOUNPUBDATEMTRGT", param1StringBuffer);
/* 1960 */         str1 = arrayOfString2[0];
/* 1961 */         str2 = arrayOfString2[1];
/*      */       } 
/* 1963 */       arrayOfString1[0] = str1;
/* 1964 */       arrayOfString1[1] = str2;
/* 1965 */       return arrayOfString1;
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
/* 1976 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLSEOPubTo catlgorDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 1977 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 1979 */       String str1 = "@@";
/* 1980 */       String str2 = "@@";
/* 1981 */       String[] arrayOfString1 = new String[2];
/* 1982 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1984 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1986 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity1, param1DiffEntity2, str1, str2, this.country, "PUBTO", param1StringBuffer);
/* 1987 */         str1 = arrayOfString2[0];
/* 1988 */         str2 = arrayOfString2[1];
/*      */       } 
/* 1990 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 1993 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "LSEOUNPUBDATEMTRGT", param1StringBuffer);
/* 1994 */         str1 = arrayOfString2[0];
/* 1995 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1998 */       arrayOfString1[0] = str1;
/* 1999 */       arrayOfString1[1] = str2;
/* 2000 */       return arrayOfString1;
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
/*      */     private String[] deriveENDOFSERVICE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2041 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveEndOfService  eofAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 2042 */           .getKey()) + " findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2044 */       String str1 = "@@";
/* 2045 */       String str2 = "@@";
/* 2046 */       String[] arrayOfString1 = new String[2];
/* 2047 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2049 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2051 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2052 */         str1 = arrayOfString2[0];
/* 2053 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2055 */       arrayOfString1[0] = str1;
/* 2056 */       arrayOfString1[1] = str2;
/* 2057 */       return arrayOfString1;
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
/* 2069 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubTo  loAvailDiff: " + ((param1DiffEntity3 == null) ? "null" : param1DiffEntity3.getKey()) + " findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2071 */       String str1 = "@@";
/* 2072 */       String str2 = "@@";
/* 2073 */       String[] arrayOfString1 = new String[2];
/* 2074 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2076 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2078 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity1, param1DiffEntity2, str1, str2, this.country, "PUBTO", param1StringBuffer);
/* 2079 */         str1 = arrayOfString2[0];
/* 2080 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2083 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2085 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2086 */         str1 = arrayOfString2[0];
/* 2087 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2090 */       arrayOfString1[0] = str1;
/* 2091 */       arrayOfString1[1] = str2;
/* 2092 */       return arrayOfString1;
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
/*      */     private String[] derivePubFrom(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2104 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubFrom availDiff: " + this.availDiff.getKey() + " foAvailDiff: " + ((param1DiffEntity3 == null) ? "null" : param1DiffEntity3
/* 2105 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2107 */       String str1 = "@@";
/* 2108 */       String str2 = "@@";
/* 2109 */       String[] arrayOfString1 = new String[2];
/* 2110 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2112 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2114 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity1, param1DiffEntity2, str1, str2, this.country, "PUBFROM", param1StringBuffer);
/* 2115 */         str1 = arrayOfString2[0];
/* 2116 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2118 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2120 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2121 */         str1 = arrayOfString2[0];
/* 2122 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2124 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 2127 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2128 */         str1 = arrayOfString2[0];
/* 2129 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2132 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2134 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2135 */         str1 = arrayOfString2[0];
/* 2136 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2139 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2141 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "LSEOPUBDATEMTRGT", param1StringBuffer);
/* 2142 */         str1 = arrayOfString2[0];
/* 2143 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2146 */       arrayOfString1[0] = str1;
/* 2147 */       arrayOfString1[1] = str2;
/* 2148 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnNumber(boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2158 */       String str1 = "@@";
/* 2159 */       String str2 = "@@";
/* 2160 */       String[] arrayOfString1 = new String[2];
/* 2161 */       String[] arrayOfString2 = new String[2];
/* 2162 */       ABRUtil.append(param1StringBuffer, "XMLLSEOAVAILElembh1.deriveAnnNumber availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2164 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2166 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/* 2167 */         str1 = arrayOfString2[0];
/* 2168 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2171 */       arrayOfString1[0] = str1;
/* 2172 */       arrayOfString1[1] = str2;
/* 2173 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnDate(boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2183 */       String str1 = "@@";
/* 2184 */       String str2 = "@@";
/* 2185 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 2187 */       String[] arrayOfString2 = new String[2];
/* 2188 */       ABRUtil.append(param1StringBuffer, "XMLLSEOAVAILElembh1.deriveAnnDate availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 2191 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 2192 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2193 */         str1 = arrayOfString2[0];
/* 2194 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2197 */       arrayOfString1[0] = str1;
/* 2198 */       arrayOfString1[1] = str2;
/* 2199 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveFIRSTORDER(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2209 */       String str1 = "@@";
/* 2210 */       String str2 = "@@";
/* 2211 */       String[] arrayOfString1 = new String[2];
/* 2212 */       String[] arrayOfString2 = new String[2];
/* 2213 */       ABRUtil.append(param1StringBuffer, "XMLLSEOAVAILElembh1.deriveFIRSTORDER availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + "foAvailDiff " + param1DiffEntity2 + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 2214 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2216 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2218 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2219 */         str1 = arrayOfString2[0];
/* 2220 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2223 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 2226 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2227 */         str1 = arrayOfString2[0];
/* 2228 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2231 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2233 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2234 */         str1 = arrayOfString2[0];
/* 2235 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2238 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2240 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "LSEOPUBDATEMTRGT", param1StringBuffer);
/* 2241 */         str1 = arrayOfString2[0];
/* 2242 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2245 */       arrayOfString1[0] = str1;
/* 2246 */       arrayOfString1[1] = str2;
/* 2247 */       return arrayOfString1;
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
/*      */     private String[] derivePlannedavailability(boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2259 */       String str1 = "@@";
/* 2260 */       String str2 = "@@";
/* 2261 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 2263 */       String[] arrayOfString2 = new String[2];
/* 2264 */       ABRUtil.append(param1StringBuffer, "XMLLSEOAVAILElembh1.derivePlannedavailability availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 2267 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 2268 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2269 */         str1 = arrayOfString2[0];
/* 2270 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2273 */       arrayOfString1[0] = str1;
/* 2274 */       arrayOfString1[1] = str2;
/* 2275 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveEOSANNDATE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2285 */       String str1 = "@@";
/* 2286 */       String str2 = "@@";
/* 2287 */       String[] arrayOfString1 = new String[2];
/* 2288 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2290 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2292 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity, str1, str2, this.country, "13", param1StringBuffer);
/* 2293 */         str1 = arrayOfString2[0];
/* 2294 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2297 */       arrayOfString1[0] = str1;
/* 2298 */       arrayOfString1[1] = str2;
/* 2299 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveWDANNDATE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2309 */       String str1 = "@@";
/* 2310 */       String str2 = "@@";
/* 2311 */       String[] arrayOfString1 = new String[2];
/* 2312 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2314 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2316 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity, str1, str2, this.country, "14", param1StringBuffer);
/* 2317 */         str1 = arrayOfString2[0];
/* 2318 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2321 */       arrayOfString1[0] = str1;
/* 2322 */       arrayOfString1[1] = str2;
/* 2323 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLastOrder(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2332 */       String str1 = "@@";
/* 2333 */       String str2 = "@@";
/* 2334 */       String[] arrayOfString1 = new String[2];
/* 2335 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2337 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2339 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2340 */         str1 = arrayOfString2[0];
/* 2341 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2344 */       arrayOfString1[0] = str1;
/* 2345 */       arrayOfString1[1] = str2;
/* 2346 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */     
/*      */     String getCountry() {
/* 2351 */       return this.country;
/*      */     }
/*      */     
/*      */     String getKey() {
/* 2355 */       return this.country;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2359 */       return (this.availDiff != null) ? (this.availDiff.getKey() + " action:" + this.action) : "There is no AVAIL! ";
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLLSEOAVAILElembh1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */