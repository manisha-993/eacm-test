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
/*      */ public class XMLLSEOAVAILElem
/*      */   extends XMLElem
/*      */ {
/*      */   public XMLLSEOAVAILElem() {
/*  140 */     super("AVAILABILITYELEMENT");
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
/*      */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  163 */     boolean bool = isDerivefromLSEO(paramHashtable, paramDiffEntity, paramStringBuffer);
/*  164 */     if (bool == true) {
/*  165 */       createNodeFromLSEO(paramDatabase, paramDocument, paramElement, paramDiffEntity, paramStringBuffer);
/*      */     } else {
/*      */       
/*  168 */       Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, paramStringBuffer);
/*      */       
/*  170 */       if (vector.size() > 0) {
/*      */ 
/*      */ 
/*      */         
/*  174 */         TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  175 */         for (byte b = 0; b < vector.size(); b++) {
/*  176 */           DiffEntity diffEntity = vector.elementAt(b);
/*  177 */           buildCtryAudRecs(treeMap, diffEntity, paramStringBuffer);
/*      */         } 
/*      */ 
/*      */         
/*  181 */         Collection collection = treeMap.values();
/*  182 */         Iterator<CtryAudRecord> iterator = collection.iterator();
/*  183 */         while (iterator.hasNext()) {
/*  184 */           CtryAudRecord ctryAudRecord = iterator.next();
/*      */           
/*  186 */           if (!ctryAudRecord.isDeleted()) {
/*      */             
/*  188 */             DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/*  189 */                 .getCountry(), paramStringBuffer);
/*      */             
/*  191 */             DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  192 */                 .getCountry(), paramStringBuffer);
/*  193 */             DiffEntity diffEntity3 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/*  194 */                 .getCountry(), paramStringBuffer);
/*      */ 
/*      */             
/*  197 */             ctryAudRecord.setAllFields(diffEntity1, diffEntity2, diffEntity3, paramStringBuffer);
/*      */           } 
/*  199 */           if (ctryAudRecord.isDisplayable()) {
/*  200 */             createNodeSet(paramDocument, paramElement, ctryAudRecord, paramStringBuffer);
/*      */           } else {
/*  202 */             ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*      */           } 
/*  204 */           ctryAudRecord.dereference();
/*      */         } 
/*      */ 
/*      */         
/*  208 */         treeMap.clear();
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  218 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no planned AVAILs found" + NEWLINE);
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
/*      */   private void createNodeFromLSEO(Database paramDatabase, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException {
/*  252 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  253 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  254 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*      */     
/*  256 */     if (paramDiffEntity.isNew()) {
/*      */       
/*  258 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  259 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for new lseo: ctryAtt " + 
/*  260 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/*  261 */       if (eANFlagAttribute != null) {
/*  262 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  263 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  265 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  266 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  267 */             String str2 = str1;
/*  268 */             if (treeMap.containsKey(str2)) {
/*      */               
/*  270 */               CtryAudRecord ctryAudRecord = (CtryAudRecord)treeMap.get(str2);
/*  271 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for New " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */             } else {
/*      */               
/*  274 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str1);
/*  275 */               ctryAudRecord.setAction("Update");
/*  276 */               treeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  277 */               ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for New:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  278 */                   .getKey() + NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  283 */     } else if (!paramDiffEntity.isDeleted()) {
/*  284 */       HashSet<String> hashSet1 = new HashSet();
/*  285 */       HashSet<String> hashSet2 = new HashSet();
/*      */       
/*  287 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  288 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for current lseo: ctryAtt " + 
/*  289 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/*  290 */       if (eANFlagAttribute != null) {
/*  291 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  292 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  294 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  295 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  296 */             hashSet2.add(str);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  302 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  303 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for prior lseo: ctryAtt " + 
/*  304 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/*  305 */       if (eANFlagAttribute != null) {
/*  306 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  307 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  309 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  310 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  311 */             hashSet1.add(str);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  316 */       Iterator<String> iterator1 = hashSet2.iterator();
/*  317 */       while (iterator1.hasNext()) {
/*  318 */         String str = iterator1.next();
/*  319 */         if (!hashSet1.contains(str)) {
/*      */           
/*  321 */           if (treeMap.containsKey(str)) {
/*  322 */             CtryAudRecord ctryAudRecord2 = (CtryAudRecord)treeMap.get(str);
/*  323 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for added ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryAudRecord2 + NEWLINE);
/*      */             continue;
/*      */           } 
/*  326 */           CtryAudRecord ctryAudRecord1 = new CtryAudRecord(null, str);
/*  327 */           ctryAudRecord1.setAction("Update");
/*  328 */           treeMap.put(ctryAudRecord1.getKey(), ctryAudRecord1);
/*  329 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord1
/*  330 */               .getKey() + NEWLINE);
/*      */           
/*      */           continue;
/*      */         } 
/*  334 */         if (treeMap.containsKey(str)) {
/*  335 */           CtryAudRecord ctryAudRecord1 = (CtryAudRecord)treeMap.get(str);
/*  336 */           ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for existing ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */           continue;
/*      */         } 
/*  339 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str);
/*  340 */         treeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  341 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  342 */             .getKey() + NEWLINE);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  347 */       iterator1 = hashSet1.iterator();
/*  348 */       while (iterator1.hasNext()) {
/*  349 */         String str = iterator1.next();
/*  350 */         if (!hashSet2.contains(str)) {
/*      */           
/*  352 */           if (treeMap.containsKey(str)) {
/*  353 */             CtryAudRecord ctryAudRecord1 = (CtryAudRecord)treeMap.get(str);
/*  354 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */             continue;
/*      */           } 
/*  357 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str);
/*  358 */           ctryAudRecord.setAction("Delete");
/*  359 */           treeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  360 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for deleted ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  361 */               .getKey() + NEWLINE);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  367 */     Collection collection = treeMap.values();
/*  368 */     Iterator<CtryAudRecord> iterator = collection.iterator();
/*  369 */     while (iterator.hasNext()) {
/*      */       
/*  371 */       CtryAudRecord ctryAudRecord = iterator.next();
/*  372 */       if (!ctryAudRecord.isDeleted()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  378 */         if (entityItem1 != null) {
/*  379 */           ctryAudRecord.availStatus = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/*      */         }
/*      */         
/*  382 */         if (entityItem1 != null) {
/*  383 */           ctryAudRecord.pubfrom = PokUtils.getAttributeValue(entityItem1, "LSEOPUBDATEMTRGT", "", "@@", false);
/*      */         }
/*  385 */         String str1 = "@@";
/*  386 */         if (entityItem2 != null) {
/*  387 */           str1 = PokUtils.getAttributeValue(entityItem2, "LSEOPUBDATEMTRGT", ", ", "@@", false);
/*      */         }
/*  389 */         ABRUtil.append(paramStringBuffer, "CtryAudRecord.setAllFields pubfrom: " + ctryAudRecord.pubfrom + " prevdate: " + str1 + NEWLINE);
/*      */ 
/*      */         
/*  392 */         if (!str1.equals(ctryAudRecord.pubfrom)) {
/*  393 */           ctryAudRecord.setAction("Update");
/*      */         }
/*      */         
/*  396 */         if (entityItem1 != null) {
/*  397 */           ctryAudRecord.pubto = PokUtils.getAttributeValue(entityItem1, "LSEOUNPUBDATEMTRGT", "", "@@", false);
/*      */         }
/*  399 */         String str2 = "@@";
/*  400 */         if (entityItem2 != null) {
/*  401 */           str2 = PokUtils.getAttributeValue(entityItem2, "LSEOUNPUBDATEMTRGT", ", ", "@@", false);
/*      */         }
/*  403 */         ABRUtil.append(paramStringBuffer, "CtryAudRecord.setAllFields pubto: " + ctryAudRecord.pubto + " prevdate: " + str2 + NEWLINE);
/*      */ 
/*      */         
/*  406 */         if (!str2.equals(ctryAudRecord.pubto)) {
/*  407 */           ctryAudRecord.setAction("Update");
/*      */         }
/*      */       } 
/*  410 */       if (ctryAudRecord.isDisplayable()) {
/*  411 */         createNodeSet(paramDocument, paramElement, ctryAudRecord, paramStringBuffer);
/*      */       } else {
/*  413 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no changes found for " + ctryAudRecord.country + NEWLINE);
/*      */       } 
/*  415 */       ctryAudRecord.dereference();
/*      */     } 
/*  417 */     treeMap.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeSet(Document paramDocument, Element paramElement, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/*  425 */     Element element1 = paramDocument.createElement(this.nodeName);
/*  426 */     addXMLAttrs(element1);
/*  427 */     paramElement.appendChild(element1);
/*      */ 
/*      */     
/*  430 */     Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  431 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/*  432 */     element1.appendChild(element2);
/*  433 */     element2 = paramDocument.createElement("STATUS");
/*  434 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/*  435 */     element1.appendChild(element2);
/*  436 */     element2 = paramDocument.createElement("COUNTRY_FC");
/*  437 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  438 */     element1.appendChild(element2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  443 */     element2 = paramDocument.createElement("PUBFROM");
/*  444 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/*  445 */     element1.appendChild(element2);
/*  446 */     element2 = paramDocument.createElement("PUBTO");
/*  447 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/*  448 */     element1.appendChild(element2);
/*  449 */     element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  450 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/*  451 */     element1.appendChild(element2);
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
/*      */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  470 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs " + paramDiffEntity.getKey() + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  476 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  477 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  478 */     if (paramDiffEntity.isDeleted()) {
/*      */       
/*  480 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  481 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for deleted avail: ctryAtt " + 
/*  482 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/*  483 */       if (eANFlagAttribute != null) {
/*  484 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  485 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  487 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  488 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  489 */             String str2 = str1;
/*  490 */             if (paramTreeMap.containsKey(str2)) {
/*      */               
/*  492 */               CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/*  493 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */             } else {
/*      */               
/*  496 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/*  497 */               ctryAudRecord.setAction("Delete");
/*  498 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  499 */               ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  500 */                   .getKey() + NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  505 */     } else if (paramDiffEntity.isNew()) {
/*      */       
/*  507 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  508 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for new avail:  ctryAtt and anncodeAtt " + 
/*  509 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/*  510 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/*  511 */       if (eANFlagAttribute != null) {
/*  512 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  513 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  515 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  516 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  517 */             String str2 = str1;
/*  518 */             if (paramTreeMap.containsKey(str2)) {
/*  519 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  520 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new " + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */               
/*  522 */               ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*      */             } else {
/*  524 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/*  525 */               ctryAudRecord.setAction("Update");
/*  526 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  527 */               ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  528 */                   .getKey() + NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*  534 */       HashSet<String> hashSet1 = new HashSet();
/*  535 */       HashSet<String> hashSet2 = new HashSet();
/*      */       
/*  537 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  538 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for curr avail: fAtt and curranncodeAtt " + 
/*  539 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/*  540 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/*  541 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/*  543 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  544 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  546 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  547 */             hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  553 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  554 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for prev avail:  fAtt and prevanncodeAtt " + 
/*  555 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + 
/*  556 */           PokUtils.getAttributeFlagValue(entityItem2, "ANNCODENAME") + NEWLINE);
/*  557 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/*  559 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  560 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  562 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  563 */             hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  569 */       Iterator<String> iterator = hashSet2.iterator();
/*  570 */       while (iterator.hasNext()) {
/*  571 */         String str1 = iterator.next();
/*  572 */         if (!hashSet1.contains(str1)) {
/*      */           
/*  574 */           String str = str1;
/*  575 */           if (paramTreeMap.containsKey(str)) {
/*  576 */             CtryAudRecord ctryAudRecord2 = paramTreeMap.get(str);
/*  577 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for added ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryAudRecord2 + NEWLINE);
/*      */             
/*  579 */             ctryAudRecord2.setUpdateAvail(paramDiffEntity); continue;
/*      */           } 
/*  581 */           CtryAudRecord ctryAudRecord1 = new CtryAudRecord(paramDiffEntity, str1);
/*  582 */           ctryAudRecord1.setAction("Update");
/*  583 */           paramTreeMap.put(ctryAudRecord1.getKey(), ctryAudRecord1);
/*  584 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord1
/*  585 */               .getKey() + NEWLINE);
/*      */           
/*      */           continue;
/*      */         } 
/*  589 */         String str2 = str1;
/*  590 */         if (paramTreeMap.containsKey(str2)) {
/*  591 */           CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str2);
/*  592 */           ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for existing ctry but new aud on " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */           continue;
/*      */         } 
/*  595 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/*  596 */         paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  597 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  598 */             .getKey() + NEWLINE);
/*      */       } 
/*      */ 
/*      */       
/*  602 */       iterator = hashSet1.iterator();
/*  603 */       while (iterator.hasNext()) {
/*  604 */         String str = iterator.next();
/*  605 */         if (!hashSet2.contains(str)) {
/*      */           
/*  607 */           String str1 = str;
/*  608 */           if (paramTreeMap.containsKey(str1)) {
/*  609 */             CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str1);
/*  610 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str1 + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */             continue;
/*      */           } 
/*  613 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str);
/*  614 */           ctryAudRecord.setAction("Delete");
/*  615 */           paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  616 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for delete ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  617 */               .getKey() + NEWLINE);
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
/*      */   private Vector getPlannedAvails(Hashtable paramHashtable, StringBuffer paramStringBuffer) {
/*  629 */     Vector<DiffEntity> vector1 = new Vector(1);
/*  630 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/*      */     
/*  632 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/*  633 */         .size())) + NEWLINE);
/*  634 */     if (vector2 == null) {
/*  635 */       return vector1;
/*      */     }
/*      */ 
/*      */     
/*  639 */     for (byte b = 0; b < vector2.size(); b++) {
/*  640 */       DiffEntity diffEntity = vector2.elementAt(b);
/*  641 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/*  642 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*  643 */       if (diffEntity.isDeleted()) {
/*  644 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/*  645 */             PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/*  646 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/*  647 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  648 */           vector1.add(diffEntity);
/*      */         }
/*      */       } else {
/*  651 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/*  652 */             PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/*  653 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/*  654 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  655 */           vector1.add(diffEntity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  660 */     return vector1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DiffEntity getEntityForAttrs(Hashtable paramHashtable, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, StringBuffer paramStringBuffer) {
/*  669 */     DiffEntity diffEntity = null;
/*  670 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get(paramString1);
/*      */     
/*  672 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs looking for " + paramString2 + ":" + paramString3 + " and " + paramString4 + ":" + paramString5 + " in " + paramString1 + " allVct.size:" + ((vector == null) ? "null" : ("" + vector
/*  673 */         .size())) + NEWLINE);
/*  674 */     if (vector == null) {
/*  675 */       return diffEntity;
/*      */     }
/*      */     
/*  678 */     for (byte b = 0; b < vector.size(); b++) {
/*  679 */       DiffEntity diffEntity1 = vector.elementAt(b);
/*  680 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/*  681 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/*  682 */       if (diffEntity1.isDeleted()) {
/*  683 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  684 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/*  685 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/*  686 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/*  687 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  688 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/*  689 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  690 */             diffEntity = diffEntity1;
/*      */           }
/*      */         }
/*      */       
/*  694 */       } else if (diffEntity1.isNew()) {
/*  695 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  696 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/*  697 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/*  698 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/*  699 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  700 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/*  701 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  702 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } else {
/*  708 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: current " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  709 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/*  710 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/*  711 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/*  712 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  713 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/*  714 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  715 */             diffEntity = diffEntity1;
/*      */             break;
/*      */           } 
/*      */         } 
/*  719 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  720 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/*  721 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/*  722 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/*  723 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  724 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/*  725 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  726 */             diffEntity = diffEntity1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  734 */     return diffEntity;
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
/*      */   private boolean isDerivefromLSEO(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  749 */     boolean bool = false;
/*  750 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("WWSEO");
/*  751 */     ABRUtil.append(paramStringBuffer, "DerivefromLSEO looking for WWSEO.SPECBID. allVct.size:" + ((vector == null) ? "null" : ("" + vector
/*  752 */         .size())) + NEWLINE);
/*  753 */     if (vector != null) {
/*  754 */       if (vector.size() == 0) {
/*  755 */         ABRUtil.append(paramStringBuffer, "DerivefromLSEO No entities found for WWSEO" + NEWLINE);
/*      */       } else {
/*      */         
/*  758 */         for (byte b = 0; b < vector.size(); b++) {
/*  759 */           DiffEntity diffEntity = vector.elementAt(b);
/*  760 */           EntityItem entityItem = diffEntity.getCurrentEntityItem();
/*  761 */           if (!diffEntity.isDeleted()) {
/*  762 */             ABRUtil.append(paramStringBuffer, "XMLANNElem.DerivefromLSEO WWSEO checking[" + b + "]:New or Update" + diffEntity
/*  763 */                 .getKey() + " SPECBID: " + PokUtils.getAttributeValue(entityItem, "SPECBID", ", ", "@@", false) + NEWLINE);
/*      */             
/*  765 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("SPECBID");
/*  766 */             if (eANFlagAttribute != null && !eANFlagAttribute.isSelected("11457")) {
/*  767 */               bool = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  775 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CtryAudRecord
/*      */   {
/*  784 */     private String action = "@@";
/*      */     
/*      */     private String country;
/*  787 */     private String availStatus = "@@";
/*  788 */     private String pubfrom = "@@";
/*  789 */     private String pubto = "@@";
/*  790 */     private String endofservice = "@@"; private DiffEntity availDiff;
/*      */     
/*      */     boolean isDisplayable() {
/*  793 */       return !this.action.equals("@@");
/*      */     }
/*      */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String) {
/*  796 */       this.country = param1String;
/*  797 */       this.availDiff = param1DiffEntity;
/*      */     } void setAction(String param1String) {
/*  799 */       this.action = param1String;
/*      */     } void setUpdateAvail(DiffEntity param1DiffEntity) {
/*  801 */       this.availDiff = param1DiffEntity;
/*  802 */       setAction("Update");
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
/*      */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, StringBuffer param1StringBuffer) {
/*  827 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + XMLElem.NEWLINE);
/*      */       
/*  829 */       EntityItem entityItem1 = this.availDiff.getCurrentEntityItem();
/*  830 */       EntityItem entityItem2 = this.availDiff.getPriorEntityItem();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  855 */       if (entityItem1 != null) {
/*  856 */         this.availStatus = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/*  857 */         if (this.availStatus == null) {
/*  858 */           this.availStatus = "@@";
/*      */         }
/*      */       } 
/*      */       
/*  862 */       String str1 = "@@";
/*  863 */       if (entityItem2 != null) {
/*  864 */         str1 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/*  865 */         if (str1 == null) {
/*  866 */           str1 = "@@";
/*      */         }
/*      */       } 
/*  869 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields curstatus: " + this.availStatus + " prevstatus: " + str1 + XMLElem.NEWLINE);
/*      */ 
/*      */       
/*  872 */       if (!str1.equals(this.availStatus)) {
/*  873 */         setAction("Update");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  879 */       if (isNewCountry(param1DiffEntity1, param1StringBuffer)) {
/*  880 */         setAction("Update");
/*      */       }
/*      */       
/*  883 */       this.pubfrom = derivePubFrom(param1DiffEntity1, false, param1StringBuffer);
/*  884 */       String str2 = derivePubFrom(param1DiffEntity1, true, param1StringBuffer);
/*      */       
/*  886 */       if (!this.pubfrom.equals(str2)) {
/*  887 */         setAction("Update");
/*      */       }
/*      */       
/*  890 */       this.pubto = derivePubTo(param1DiffEntity2, false, param1StringBuffer);
/*  891 */       String str3 = derivePubTo(param1DiffEntity2, true, param1StringBuffer);
/*  892 */       if (!this.pubto.equals(str3)) {
/*  893 */         setAction("Update");
/*      */       }
/*      */       
/*  896 */       this.endofservice = deriveENDOFSERVICE(param1DiffEntity3, false, param1StringBuffer);
/*  897 */       String str4 = deriveENDOFSERVICE(param1DiffEntity3, true, param1StringBuffer);
/*  898 */       if (!this.endofservice.equals(str4)) {
/*  899 */         setAction("Update");
/*      */       }
/*  901 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields pubfrom: " + this.pubfrom + " pubto: " + this.pubto + " endofservice:" + this.endofservice + XMLElem.NEWLINE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isNewCountry(DiffEntity param1DiffEntity, StringBuffer param1StringBuffer) {
/*  910 */       boolean bool = false;
/*  911 */       if (param1DiffEntity != null && param1DiffEntity.isNew()) {
/*  912 */         bool = true;
/*  913 */         ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields isNewAvail" + param1DiffEntity.getKey() + XMLElem.NEWLINE);
/*  914 */       } else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/*  915 */         EANFlagAttribute eANFlagAttribute1 = null;
/*  916 */         EANFlagAttribute eANFlagAttribute2 = null;
/*  917 */         EntityItem entityItem1 = param1DiffEntity.getCurrentEntityItem();
/*  918 */         EntityItem entityItem2 = param1DiffEntity.getPriorEntityItem();
/*  919 */         if (entityItem1 != null) {
/*  920 */           eANFlagAttribute1 = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*      */         }
/*  922 */         if (entityItem2 != null) {
/*  923 */           eANFlagAttribute2 = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*      */         }
/*  925 */         if (eANFlagAttribute2 != null && !eANFlagAttribute2.isSelected(this.country) && eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(this.country)) {
/*  926 */           bool = true;
/*  927 */           ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields isNewCountry" + param1DiffEntity.getKey() + XMLElem.NEWLINE);
/*      */         } 
/*      */       } 
/*  930 */       return bool;
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
/*      */     private String deriveENDOFSERVICE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  942 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService  eofAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/*  943 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */ 
/*      */       
/*  946 */       String str = "@@";
/*  947 */       if (param1Boolean) {
/*      */         
/*  949 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/*  950 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/*  951 */           if (entityItem != null) {
/*  952 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  953 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  954 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */               
/*  956 */               ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService eofavail thedate: " + str + " COUNTRYLIST: " + 
/*  957 */                   PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */             } 
/*      */           } else {
/*  960 */             ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService eofAvail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  967 */       else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/*  968 */         EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/*  969 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  970 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  971 */           str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */         }
/*  973 */         ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService eofavail thedate: " + str + " COUNTRYLIST: " + 
/*  974 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  979 */       return str;
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
/*      */     private String derivePubTo(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  991 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo  loAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/*  992 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */ 
/*      */       
/*  995 */       String str = "@@";
/*  996 */       if (param1Boolean) {
/*      */         
/*  998 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/*  999 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 1000 */           if (entityItem != null) {
/* 1001 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1002 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1003 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/* 1005 */             ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/* 1006 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */           } else {
/* 1008 */             ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo loavail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1014 */       else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 1015 */         EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 1016 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1017 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1018 */           str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */         }
/* 1020 */         ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/* 1021 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1026 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String derivePubFrom(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1037 */       String str = "@@";
/* 1038 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubFrom availDiff: " + this.availDiff.getKey() + " foAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 1039 */           .getKey()) + "findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */       
/* 1041 */       if (param1Boolean) {
/*      */ 
/*      */         
/* 1044 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 1045 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 1046 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1047 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1048 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */           }
/* 1050 */           ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubFrom foavail thedate: " + str + XMLElem.NEWLINE);
/*      */         } 
/*      */         
/* 1053 */         if ("@@".equals(str))
/*      */         {
/* 1055 */           if (!this.availDiff.isNew() && this.availDiff != null) {
/* 1056 */             EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 1057 */             Vector<EntityItem> vector = entityItem.getDownLink();
/* 1058 */             for (byte b = 0; b < vector.size(); b++) {
/* 1059 */               EntityItem entityItem1 = vector.elementAt(b);
/* 1060 */               if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 1061 */                 Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1062 */                 EntityItem entityItem2 = vector1.elementAt(0);
/* 1063 */                 str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 1064 */                 ABRUtil.append(param1StringBuffer, "CtryAudRecord.getANNOUNCEMENT looking for downlink of AVAILANNA : Announcement " + (
/* 1065 */                     (vector1.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem2.getKey()) : entityItem2.getKey()) + XMLElem.NEWLINE);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } else {
/* 1071 */         if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 1072 */           EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 1073 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1074 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1075 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */           }
/* 1077 */           ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubFrom foavail thedate: " + str + " COUNTRYLIST: " + 
/*      */               
/* 1079 */               PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */         } 
/* 1081 */         if ("@@".equals(str) && 
/* 1082 */           !this.availDiff.isDeleted() && this.availDiff != null) {
/* 1083 */           EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 1084 */           Vector<EntityItem> vector = entityItem.getDownLink();
/* 1085 */           for (byte b = 0; b < vector.size(); b++) {
/* 1086 */             EntityItem entityItem1 = vector.elementAt(b);
/* 1087 */             if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 1088 */               Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1089 */               EntityItem entityItem2 = vector1.elementAt(0);
/* 1090 */               str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 1091 */               ABRUtil.append(param1StringBuffer, "CtryAudRecord.getANNOUNCEMENT looking for downlink of AVAILANNA : Announcement " + (
/* 1092 */                   (vector.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem2.getKey()) : entityItem2.getKey()) + XMLElem.NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1098 */       return str;
/*      */     }
/*      */     
/* 1101 */     String getAction() { return this.action; } String getCountry() {
/* 1102 */       return this.country;
/*      */     }
/* 1104 */     String getPubFrom() { return this.pubfrom; }
/* 1105 */     String getPubTo() { return this.pubto; }
/* 1106 */     String getEndOfService() { return this.endofservice; } String getAvailStatus() {
/* 1107 */       return this.availStatus;
/*      */     }
/* 1109 */     boolean isDeleted() { return "Delete".equals(this.action); } String getKey() {
/* 1110 */       return this.country;
/*      */     } void dereference() {
/* 1112 */       this.availDiff = null;
/* 1113 */       this.action = null;
/* 1114 */       this.country = null;
/* 1115 */       this.availStatus = null;
/*      */       
/* 1117 */       this.pubfrom = null;
/* 1118 */       this.pubto = null;
/* 1119 */       this.endofservice = null;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1123 */       return this.availDiff.getKey() + " " + getKey() + " action:" + this.action;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLLSEOAVAILElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */