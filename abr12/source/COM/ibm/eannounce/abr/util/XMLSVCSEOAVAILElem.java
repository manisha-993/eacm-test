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
/*      */ import java.util.StringTokenizer;
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
/*      */ public class XMLSVCSEOAVAILElem
/*      */   extends XMLElem
/*      */ {
/*  101 */   protected Vector childVct = new Vector(1);
/*  102 */   private static String availRelator = "";
/*  103 */   private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
/*      */   
/*      */   protected boolean hasChanges(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  106 */     boolean bool = false;
/*  107 */     ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.hasChanges parentItem: " + paramDiffEntity.getKey() + NEWLINE);
/*  108 */     if (paramDiffEntity.getEntityType().equals("SVCMOD")) {
/*  109 */       availRelator = "SVCMODAVAIL";
/*      */     } else {
/*  111 */       availRelator = "SVCSEOAVAIL";
/*      */     } 
/*  113 */     Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, paramDiffEntity, paramStringBuffer);
/*      */     
/*  115 */     if (vector.size() > 0) {
/*      */ 
/*      */ 
/*      */       
/*  119 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  120 */       for (byte b = 0; b < vector.size(); b++) {
/*  121 */         DiffEntity diffEntity = vector.elementAt(b);
/*  122 */         buildCtryAudRecs(treeMap, diffEntity, paramStringBuffer);
/*      */       } 
/*      */ 
/*      */       
/*  126 */       Collection collection = treeMap.values();
/*  127 */       Iterator<CtryAudRecord> iterator = collection.iterator();
/*  128 */       while (iterator.hasNext()) {
/*  129 */         CtryAudRecord ctryAudRecord = iterator.next();
/*      */         
/*  131 */         if (!ctryAudRecord.isDeleted()) {
/*      */           
/*  133 */           DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/*  134 */               .getCountry(), paramStringBuffer);
/*      */           
/*  136 */           DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  137 */               .getCountry(), paramStringBuffer);
/*  138 */           DiffEntity diffEntity3 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/*  139 */               .getCountry(), paramStringBuffer);
/*      */           
/*  141 */           DiffEntity diffEntity4 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRecord
/*  142 */               .getCountry(), paramStringBuffer);
/*      */           
/*  144 */           ctryAudRecord.setAllFields(paramDiffEntity, diffEntity1, diffEntity2, diffEntity3, diffEntity4, paramHashtable, paramStringBuffer);
/*      */         } 
/*  146 */         if (ctryAudRecord.isDisplayable()) {
/*  147 */           bool = true;
/*  148 */           ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.hasChanges is true" + NEWLINE);
/*      */           break;
/*      */         } 
/*  151 */         ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.hasChanges no changes found for " + ctryAudRecord + NEWLINE);
/*      */         
/*  153 */         ctryAudRecord.dereference();
/*      */       } 
/*      */ 
/*      */       
/*  157 */       treeMap.clear();
/*      */     } else {
/*  159 */       ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.hasChanges no planned AVAILs found" + NEWLINE);
/*      */     } 
/*      */     
/*  162 */     return bool;
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
/*      */   public XMLSVCSEOAVAILElem() {
/*  184 */     super("AVAILABILITYELEMENT");
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
/*  201 */     ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem:parentItem: " + paramDiffEntity.getKey() + NEWLINE);
/*  202 */     if (paramDiffEntity.getEntityType().equals("SVCMOD")) {
/*  203 */       availRelator = "SVCMODAVAIL";
/*      */     } else {
/*  205 */       availRelator = "SVCSEOAVAIL";
/*      */     } 
/*      */     
/*  208 */     Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, paramDiffEntity, paramStringBuffer);
/*      */     
/*  210 */     if (vector.size() > 0) {
/*      */ 
/*      */ 
/*      */       
/*  214 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  215 */       for (byte b = 0; b < vector.size(); b++) {
/*  216 */         DiffEntity diffEntity = vector.elementAt(b);
/*  217 */         buildCtryAudRecs(treeMap, diffEntity, paramStringBuffer);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  222 */       Collection collection = treeMap.values();
/*  223 */       Iterator<CtryAudRecord> iterator = collection.iterator();
/*  224 */       while (iterator.hasNext()) {
/*  225 */         CtryAudRecord ctryAudRecord = iterator.next();
/*      */         
/*  227 */         if (!ctryAudRecord.isDeleted()) {
/*      */           
/*  229 */           DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/*  230 */               .getCountry(), paramStringBuffer);
/*      */           
/*  232 */           DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  233 */               .getCountry(), paramStringBuffer);
/*  234 */           DiffEntity diffEntity3 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/*  235 */               .getCountry(), paramStringBuffer);
/*      */           
/*  237 */           DiffEntity diffEntity4 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRecord
/*  238 */               .getCountry(), paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  243 */           ctryAudRecord.setAllFields(paramDiffEntity, diffEntity1, diffEntity2, diffEntity3, diffEntity4, paramHashtable, paramStringBuffer);
/*      */         } 
/*  245 */         if (ctryAudRecord.isDisplayable()) {
/*  246 */           createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity, ctryAudRecord, paramStringBuffer);
/*      */         } else {
/*      */           
/*  249 */           ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*      */         } 
/*  251 */         ctryAudRecord.dereference();
/*      */       } 
/*      */ 
/*      */       
/*  255 */       treeMap.clear();
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  265 */       ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.addElements no planned AVAILs found" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeSet(Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/*  274 */     Element element1 = paramDocument.createElement(this.nodeName);
/*  275 */     addXMLAttrs(element1);
/*  276 */     paramElement.appendChild(element1);
/*      */ 
/*      */     
/*  279 */     Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  280 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/*  281 */     element1.appendChild(element2);
/*  282 */     element2 = paramDocument.createElement("STATUS");
/*  283 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/*  284 */     element1.appendChild(element2);
/*  285 */     element2 = paramDocument.createElement("COUNTRY_FC");
/*  286 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  287 */     element1.appendChild(element2);
/*  288 */     element2 = paramDocument.createElement("ANNDATE");
/*  289 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnndate()));
/*  290 */     element1.appendChild(element2);
/*  291 */     element2 = paramDocument.createElement("ANNNUMBER");
/*  292 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnnnumber()));
/*  293 */     element1.appendChild(element2);
/*  294 */     element2 = paramDocument.createElement("FIRSTORDER");
/*  295 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getFirstorder()));
/*  296 */     element1.appendChild(element2);
/*  297 */     element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  298 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/*  299 */     element1.appendChild(element2);
/*  300 */     element2 = paramDocument.createElement("PUBFROM");
/*  301 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/*  302 */     element1.appendChild(element2);
/*  303 */     element2 = paramDocument.createElement("PUBTO");
/*  304 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/*  305 */     element1.appendChild(element2);
/*  306 */     element2 = paramDocument.createElement("WDANNDATE");
/*  307 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getWdanndate()));
/*  308 */     element1.appendChild(element2);
/*  309 */     element2 = paramDocument.createElement("LASTORDER");
/*  310 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getLastorder()));
/*  311 */     element1.appendChild(element2);
/*  312 */     element2 = paramDocument.createElement("EOSANNDATE");
/*  313 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosanndate()));
/*  314 */     element1.appendChild(element2);
/*  315 */     element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  316 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/*  317 */     element1.appendChild(element2);
/*  318 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*  319 */     if (availRelator.equals("SVCMODAVAIL")) {
/*  320 */       SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, entityItem, paramCtryAudRecord.availDiff, "D:SVCMODAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */     
/*      */     }
/*  323 */     else if (paramDiffEntity.isNew()) {
/*      */       
/*  325 */       SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, entityItem, paramCtryAudRecord.availDiff, "NEWD:SVCMOD:D:SVCMODSVCSEO:D:SVCSEO:D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */     } else {
/*  327 */       SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, entityItem, paramCtryAudRecord.availDiff, "D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
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
/*      */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  350 */     ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.buildCtryAudRecs " + paramDiffEntity.getKey() + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  356 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  357 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*      */     
/*  359 */     if (paramDiffEntity.isDeleted()) {
/*      */       
/*  361 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  362 */       ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.buildCtryAudRecs for deleted avail: ctryAtt " + 
/*  363 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/*  364 */       if (eANFlagAttribute != null) {
/*  365 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  366 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  368 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  369 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  370 */             String str2 = str1;
/*  371 */             if (paramTreeMap.containsKey(str2)) {
/*      */               
/*  373 */               CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/*  374 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */             } else {
/*      */               
/*  377 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/*  378 */               ctryAudRecord.setAction("Delete");
/*  379 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  380 */               ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.buildCtryAudRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  381 */                   .getKey() + NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  386 */     } else if (paramDiffEntity.isNew()) {
/*      */       
/*  388 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  389 */       ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.buildCtryAudRecs for new avail:  ctryAtt and anncodeAtt " + 
/*  390 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/*  391 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/*  392 */       if (eANFlagAttribute != null) {
/*  393 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  394 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  396 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  397 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  398 */             String str2 = str1;
/*  399 */             if (paramTreeMap.containsKey(str2)) {
/*  400 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  401 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new " + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */               
/*  403 */               ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*      */             } else {
/*  405 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/*  406 */               ctryAudRecord.setAction("Update");
/*  407 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  408 */               ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  409 */                   .getKey() + NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*  415 */       HashSet<String> hashSet1 = new HashSet();
/*  416 */       HashSet<String> hashSet2 = new HashSet();
/*      */       
/*  418 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  419 */       ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.buildCtryAudRecs for curr avail: fAtt and curranncodeAtt " + 
/*  420 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/*  421 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/*  422 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/*  424 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  425 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  427 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  428 */             hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  434 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  435 */       ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.buildCtryAudRecs for prev avail:  fAtt and prevanncodeAtt " + 
/*  436 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + 
/*  437 */           PokUtils.getAttributeFlagValue(entityItem2, "ANNCODENAME") + NEWLINE);
/*  438 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/*  440 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  441 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  443 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  444 */             hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  450 */       Iterator<String> iterator = hashSet2.iterator();
/*  451 */       while (iterator.hasNext()) {
/*  452 */         String str1 = iterator.next();
/*  453 */         if (!hashSet1.contains(str1)) {
/*      */           
/*  455 */           String str = str1;
/*  456 */           if (paramTreeMap.containsKey(str)) {
/*  457 */             CtryAudRecord ctryAudRecord2 = paramTreeMap.get(str);
/*  458 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for added ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryAudRecord2 + NEWLINE);
/*      */             
/*  460 */             ctryAudRecord2.setUpdateAvail(paramDiffEntity); continue;
/*      */           } 
/*  462 */           CtryAudRecord ctryAudRecord1 = new CtryAudRecord(paramDiffEntity, str1);
/*  463 */           ctryAudRecord1.setAction("Update");
/*  464 */           paramTreeMap.put(ctryAudRecord1.getKey(), ctryAudRecord1);
/*  465 */           ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.buildCtryAudRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord1
/*  466 */               .getKey() + NEWLINE);
/*      */           
/*      */           continue;
/*      */         } 
/*  470 */         String str2 = str1;
/*  471 */         if (paramTreeMap.containsKey(str2)) {
/*  472 */           CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str2);
/*  473 */           ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for existing ctry but new aud on " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */           continue;
/*      */         } 
/*  476 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/*  477 */         paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  478 */         ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.buildCtryAudRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  479 */             .getKey() + NEWLINE);
/*      */       } 
/*      */ 
/*      */       
/*  483 */       iterator = hashSet1.iterator();
/*  484 */       while (iterator.hasNext()) {
/*  485 */         String str = iterator.next();
/*  486 */         if (!hashSet2.contains(str)) {
/*      */           
/*  488 */           String str1 = str;
/*  489 */           if (paramTreeMap.containsKey(str1)) {
/*  490 */             CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str1);
/*  491 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str1 + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */             continue;
/*      */           } 
/*  494 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str);
/*  495 */           ctryAudRecord.setAction("Delete");
/*  496 */           paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  497 */           ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.buildCtryAudRecs for delete ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  498 */               .getKey() + NEWLINE);
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
/*      */   private Vector getPlannedAvails(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  510 */     Vector<DiffEntity> vector1 = new Vector(1);
/*  511 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/*  512 */     ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/*  513 */         .size())) + NEWLINE);
/*  514 */     if (vector2 == null) {
/*  515 */       return vector1;
/*      */     }
/*      */ 
/*      */     
/*  519 */     for (byte b = 0; b < vector2.size(); b++) {
/*  520 */       DiffEntity diffEntity = vector2.elementAt(b);
/*  521 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/*  522 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*  523 */       if (deriveTheSameEntry(diffEntity, paramDiffEntity, paramStringBuffer)) {
/*  524 */         if (diffEntity.isDeleted()) {
/*  525 */           ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/*  526 */               PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/*  527 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/*  528 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  529 */             vector1.add(diffEntity);
/*      */           }
/*      */         } else {
/*  532 */           ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/*  533 */               PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/*  534 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/*  535 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  536 */             vector1.add(diffEntity);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  542 */     return vector1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deriveTheSameEntry(DiffEntity paramDiffEntity1, DiffEntity paramDiffEntity2, StringBuffer paramStringBuffer) {
/*  552 */     boolean bool = false;
/*  553 */     if (paramDiffEntity1 != null) {
/*  554 */       if (availRelator.equals("SVCMODAVAIL")) {
/*  555 */         bool = paramDiffEntity1.getParentKey().startsWith(availRelator);
/*      */       } else {
/*  557 */         StringTokenizer stringTokenizer = new StringTokenizer(paramDiffEntity1.toString(), " ");
/*  558 */         String str = "@@";
/*  559 */         while (stringTokenizer.hasMoreTokens()) {
/*  560 */           str = stringTokenizer.nextToken();
/*  561 */           if (str.startsWith("path:"))
/*      */             break; 
/*      */         } 
/*  564 */         if (!"@@".equals(str)) {
/*  565 */           StringTokenizer stringTokenizer1 = new StringTokenizer(str, ":");
/*  566 */           while (stringTokenizer1.hasMoreTokens()) {
/*  567 */             String str1 = stringTokenizer1.nextToken();
/*  568 */             if (str1.startsWith(availRelator) && 
/*  569 */               stringTokenizer1.hasMoreTokens()) {
/*  570 */               if (stringTokenizer1.nextToken().startsWith(paramDiffEntity2.getKey())) {
/*  571 */                 bool = true;
/*      */               }
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  580 */     ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.deriveTheSameEntry is " + bool + " availrelator: " + availRelator + " ParentKey: " + paramDiffEntity1
/*  581 */         .getParentKey() + " Key: " + paramDiffEntity1.toString() + NEWLINE);
/*  582 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DiffEntity getEntityForAttrs(Hashtable paramHashtable, DiffEntity paramDiffEntity, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, StringBuffer paramStringBuffer) {
/*  591 */     DiffEntity diffEntity = null;
/*  592 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get(paramString1);
/*  593 */     ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.getEntityForAttrs looking for " + paramString2 + ":" + paramString3 + " and " + paramString4 + ":" + paramString5 + " in " + paramString1 + " allVct.size:" + ((vector == null) ? "null" : ("" + vector
/*  594 */         .size())) + NEWLINE);
/*  595 */     if (vector == null) {
/*  596 */       return diffEntity;
/*      */     }
/*      */     
/*  599 */     for (byte b = 0; b < vector.size(); b++) {
/*  600 */       DiffEntity diffEntity1 = vector.elementAt(b);
/*  601 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/*  602 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/*  603 */       if (deriveTheSameEntry(diffEntity1, paramDiffEntity, paramStringBuffer)) {
/*  604 */         if (diffEntity1.isDeleted()) {
/*  605 */           ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  606 */               PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/*  607 */               PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/*  608 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/*  609 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  610 */             eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/*  611 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  612 */               diffEntity = diffEntity1;
/*      */             }
/*      */           }
/*      */         
/*  616 */         } else if (diffEntity1.isNew()) {
/*  617 */           ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  618 */               PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/*  619 */               PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/*  620 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/*  621 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  622 */             eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/*  623 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  624 */               diffEntity = diffEntity1;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } else {
/*  630 */           ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.getEntityForAttrs checking[" + b + "]: current " + diffEntity1
/*  631 */               .getKey() + " " + paramString2 + ":" + PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/*  632 */               PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/*  633 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/*  634 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  635 */             eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/*  636 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  637 */               diffEntity = diffEntity1;
/*      */               break;
/*      */             } 
/*      */           } 
/*  641 */           ABRUtil.append(paramStringBuffer, "XMLSVCSEOAVAILElem.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  642 */               PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/*  643 */               PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/*  644 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/*  645 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  646 */             eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/*  647 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  648 */               diffEntity = diffEntity1;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  657 */     return diffEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CtryAudRecord
/*      */   {
/*  665 */     private String action = "@@";
/*      */ 
/*      */     
/*      */     private String country;
/*      */     
/*  670 */     private String availStatus = "@@";
/*      */     
/*  672 */     private String pubfrom = "@@";
/*      */     
/*  674 */     private String pubto = "@@";
/*      */     
/*  676 */     private String endofservice = "@@";
/*      */     
/*  678 */     private String anndate = "@@";
/*      */     
/*  680 */     private String firstorder = "@@";
/*      */     
/*  682 */     private String plannedavailability = "@@";
/*      */     
/*  684 */     private String wdanndate = "@@";
/*      */     
/*  686 */     private String lastorder = "@@";
/*      */     
/*  688 */     private String eosanndate = "@@";
/*      */     
/*  690 */     private String annnumber = "@@";
/*      */     
/*      */     private DiffEntity availDiff;
/*      */     
/*      */     boolean isDisplayable() {
/*  695 */       return !this.action.equals("@@");
/*      */     }
/*      */     
/*      */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String) {
/*  699 */       this.country = param1String;
/*  700 */       this.availDiff = param1DiffEntity;
/*      */     }
/*      */     
/*      */     void setAction(String param1String) {
/*  704 */       this.action = param1String;
/*      */     }
/*      */     
/*      */     void setUpdateAvail(DiffEntity param1DiffEntity) {
/*  708 */       this.availDiff = param1DiffEntity;
/*  709 */       setAction("Update");
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
/*      */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, Hashtable param1Hashtable, StringBuffer param1StringBuffer) {
/*  735 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + XMLElem.NEWLINE);
/*  736 */       EntityItem entityItem1 = param1DiffEntity1.getCurrentEntityItem();
/*  737 */       boolean bool = false;
/*  738 */       EntityItem entityItem2 = this.availDiff.getCurrentEntityItem();
/*  739 */       EntityItem entityItem3 = this.availDiff.getPriorEntityItem();
/*      */ 
/*      */       
/*  742 */       this.anndate = deriveAnnDate(false, param1StringBuffer);
/*  743 */       String str1 = deriveAnnDate(true, param1StringBuffer);
/*      */       
/*  745 */       if (!this.anndate.equals(str1)) {
/*  746 */         setAction("Update");
/*      */       }
/*      */       
/*  749 */       this.annnumber = deriveAnnNumber(false, param1StringBuffer);
/*  750 */       String str2 = deriveAnnNumber(true, param1StringBuffer);
/*      */       
/*  752 */       if (!this.annnumber.equals(str2)) {
/*  753 */         setAction("Update");
/*      */       }
/*      */       
/*  756 */       this.firstorder = deriveFIRSTORDER(param1DiffEntity2, false, param1StringBuffer);
/*  757 */       String str3 = deriveFIRSTORDER(param1DiffEntity2, true, param1StringBuffer);
/*      */       
/*  759 */       if (!this.firstorder.equals(str3)) {
/*  760 */         setAction("Update");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  765 */       if (entityItem2 != null) {
/*  766 */         this.plannedavailability = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", ", ", "@@", false);
/*      */       }
/*      */       
/*  769 */       String str4 = "@@";
/*  770 */       if (entityItem3 != null) {
/*  771 */         str4 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", ", ", "@@", false);
/*      */       }
/*  773 */       ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.setAllFields plannedavailability: " + this.plannedavailability + " prevdate: " + str4 + XMLElem.NEWLINE);
/*      */ 
/*      */ 
/*      */       
/*  777 */       if (!str4.equals(this.plannedavailability)) {
/*  778 */         setAction("Update");
/*      */       }
/*      */       
/*  781 */       if (entityItem2 != null) {
/*  782 */         this.availStatus = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/*  783 */         if (this.availStatus == null) {
/*  784 */           this.availStatus = "@@";
/*      */         }
/*      */       } 
/*      */       
/*  788 */       String str5 = "@@";
/*  789 */       if (entityItem3 != null) {
/*  790 */         str5 = PokUtils.getAttributeFlagValue(entityItem3, "STATUS");
/*  791 */         if (str5 == null) {
/*  792 */           str5 = "@@";
/*      */         }
/*      */       } 
/*  795 */       ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.setAllFields curstatus: " + this.availStatus + " prevstatus: " + str5 + XMLElem.NEWLINE);
/*      */ 
/*      */ 
/*      */       
/*  799 */       if (!str5.equals(this.availStatus)) {
/*  800 */         setAction("Update");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  806 */       if (isNewCountry(param1DiffEntity2, param1StringBuffer)) {
/*  807 */         setAction("Update");
/*      */       }
/*      */       
/*  810 */       this.pubfrom = derivePubFrom(param1DiffEntity1, param1DiffEntity2, false, param1StringBuffer);
/*  811 */       String str6 = derivePubFrom(param1DiffEntity1, param1DiffEntity2, true, param1StringBuffer);
/*      */       
/*  813 */       if (!this.pubfrom.equals(str6)) {
/*  814 */         setAction("Update");
/*      */       }
/*      */       
/*  817 */       this.pubto = derivePubTo(param1DiffEntity1, param1DiffEntity3, false, param1StringBuffer);
/*  818 */       String str7 = derivePubTo(param1DiffEntity1, param1DiffEntity3, true, param1StringBuffer);
/*  819 */       if (!this.pubto.equals(str7)) {
/*  820 */         setAction("Update");
/*      */       }
/*      */ 
/*      */       
/*  824 */       this.wdanndate = deriveWDANNDATE(param1DiffEntity1, param1DiffEntity5, false, param1StringBuffer);
/*  825 */       String str8 = deriveWDANNDATE(param1DiffEntity1, param1DiffEntity5, true, param1StringBuffer);
/*      */       
/*  827 */       if (!this.wdanndate.equals(str8)) {
/*  828 */         setAction("Update");
/*      */       }
/*      */       
/*  831 */       this.lastorder = deriveLastOrder(param1DiffEntity1, param1DiffEntity3, false, param1StringBuffer);
/*  832 */       String str9 = deriveLastOrder(param1DiffEntity1, param1DiffEntity3, true, param1StringBuffer);
/*  833 */       if (!this.lastorder.equals(str9)) {
/*  834 */         setAction("Update");
/*      */       }
/*      */       
/*  837 */       this.eosanndate = deriveEOSANNDATE(param1DiffEntity4, false, param1StringBuffer);
/*  838 */       String str10 = deriveEOSANNDATE(param1DiffEntity4, true, param1StringBuffer);
/*      */       
/*  840 */       if (!this.eosanndate.equals(str10)) {
/*  841 */         setAction("Update");
/*      */       }
/*      */       
/*  844 */       this.endofservice = deriveENDOFSERVICE(param1DiffEntity4, false, param1StringBuffer);
/*  845 */       String str11 = deriveENDOFSERVICE(param1DiffEntity4, true, param1StringBuffer);
/*  846 */       if (!this.endofservice.equals(str11)) {
/*  847 */         setAction("Update");
/*      */       }
/*  849 */       ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.setAllFields pubfrom: " + this.pubfrom + " pubto: " + this.pubto + " endofservice:" + this.endofservice + XMLElem.NEWLINE);
/*      */ 
/*      */       
/*  852 */       if (XMLSVCSEOAVAILElem.availRelator.equals("SVCMODAVAIL")) {
/*      */         
/*  854 */         bool = XMLSVCSEOAVAILElem.SLEORGGRP.hasChanges(param1Hashtable, entityItem1, this.availDiff, "D:SVCMODAVAIL:D:AVAIL:D:AVAILSLEORGA:D", this.country, param1StringBuffer);
/*      */       }
/*      */       else {
/*      */         
/*  858 */         bool = XMLSVCSEOAVAILElem.SLEORGGRP.hasChanges(param1Hashtable, entityItem1, this.availDiff, "D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", this.country, param1StringBuffer);
/*      */       } 
/*  860 */       if (bool) {
/*  861 */         setAction("Update");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isNewCountry(DiffEntity param1DiffEntity, StringBuffer param1StringBuffer) {
/*  872 */       boolean bool = false;
/*  873 */       if (param1DiffEntity != null && param1DiffEntity.isNew()) {
/*  874 */         bool = true;
/*  875 */         ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.setAllFields isNewAvail" + param1DiffEntity.getKey() + XMLElem.NEWLINE);
/*  876 */       } else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/*  877 */         EANFlagAttribute eANFlagAttribute1 = null;
/*  878 */         EANFlagAttribute eANFlagAttribute2 = null;
/*  879 */         EntityItem entityItem1 = param1DiffEntity.getCurrentEntityItem();
/*  880 */         EntityItem entityItem2 = param1DiffEntity.getPriorEntityItem();
/*  881 */         if (entityItem1 != null) {
/*  882 */           eANFlagAttribute1 = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*      */         }
/*  884 */         if (entityItem2 != null) {
/*  885 */           eANFlagAttribute2 = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*      */         }
/*  887 */         if (eANFlagAttribute2 != null && !eANFlagAttribute2.isSelected(this.country) && eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(this.country)) {
/*  888 */           bool = true;
/*  889 */           ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.setAllFields isNewCountry" + param1DiffEntity.getKey() + XMLElem.NEWLINE);
/*      */         } 
/*      */       } 
/*  892 */       return bool;
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
/*  904 */       ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveEndOfService  eofAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/*  905 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */       
/*  907 */       String str = "@@";
/*  908 */       if (param1Boolean) {
/*      */         
/*  910 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/*  911 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/*      */           
/*  913 */           if (entityItem != null) {
/*  914 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  915 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  916 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/*  922 */             ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveEndOfService eofAvail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  929 */       else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/*  930 */         EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/*  931 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*      */ 
/*      */         
/*  934 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  935 */           str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  943 */       return str;
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
/*      */     private String derivePubTo(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  955 */       ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.derivePubTo  loAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/*  956 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */       
/*  958 */       String str = "@@";
/*  959 */       if (param1Boolean) {
/*      */ 
/*      */ 
/*      */         
/*  963 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isNew()) {
/*  964 */           EntityItem entityItem = param1DiffEntity2.getPriorEntityItem();
/*  965 */           if (entityItem != null) {
/*  966 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  967 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  968 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/*  973 */             ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubTo loavail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*      */           } 
/*      */         } 
/*      */         
/*  977 */         if ("@@".equals(str) && 
/*  978 */           param1DiffEntity1 != null && !param1DiffEntity1.isNew() && 
/*  979 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/*  980 */           EntityItem entityItem = param1DiffEntity1.getPriorEntityItem();
/*  981 */           str = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", "", "@@", false);
/*  982 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubTo WTHDRWEFFCTVDATE of prior SVCMODEL thedate: " + str + XMLElem.NEWLINE);
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  988 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isDeleted()) {
/*  989 */           EntityItem entityItem = param1DiffEntity2.getCurrentEntityItem();
/*  990 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  991 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  992 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  997 */         if ("@@".equals(str) && 
/*  998 */           param1DiffEntity1 != null && !param1DiffEntity1.isDeleted() && 
/*  999 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/* 1000 */           EntityItem entityItem = param1DiffEntity1.getCurrentEntityItem();
/* 1001 */           str = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", "", "@@", false);
/* 1002 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubTo WTHDRWEFFCTVDATE of current SVCMODEL thedate: " + str + XMLElem.NEWLINE);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1007 */       return str;
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
/*      */     private String derivePubFrom(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1020 */       String str = "@@";
/* 1021 */       ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.derivePubFrom availDiff: " + this.availDiff.getKey() + " foAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 1022 */           .getKey()) + "findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */ 
/*      */       
/* 1025 */       if (param1Boolean) {
/* 1026 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isNew()) {
/* 1027 */           EntityItem entityItem = param1DiffEntity2.getPriorEntityItem();
/* 1028 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1029 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1030 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */           }
/* 1032 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubFrom foavail thedate: " + str + XMLElem.NEWLINE);
/*      */         } 
/*      */         
/* 1035 */         if ("@@".equals(str))
/*      */         {
/* 1037 */           if (this.availDiff != null && !this.availDiff.isNew()) {
/* 1038 */             EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 1039 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1040 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1041 */               Vector<EntityItem> vector = entityItem.getDownLink();
/* 1042 */               for (byte b = 0; b < vector.size(); b++) {
/* 1043 */                 EntityItem entityItem1 = vector.elementAt(b);
/* 1044 */                 if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 1045 */                   Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1046 */                   EntityItem entityItem2 = vector1.elementAt(0);
/* 1047 */                   str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1056 */         if ("@@".equals(str) && 
/* 1057 */           this.availDiff != null && !this.availDiff.isNew()) {
/* 1058 */           EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 1059 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1060 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1061 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */           }
/* 1063 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubFrom effectivedate of planedavail thedate: " + str + XMLElem.NEWLINE);
/*      */         } 
/*      */ 
/*      */         
/* 1067 */         if ("@@".equals(str) && 
/* 1068 */           param1DiffEntity1 != null && !param1DiffEntity1.isNew() && 
/* 1069 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/* 1070 */           EntityItem entityItem = param1DiffEntity1.getPriorEntityItem();
/* 1071 */           str = PokUtils.getAttributeValue(entityItem, "ANNDATE", "", "@@", false);
/* 1072 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubFrom anndate of prior MODEL thedate: " + str + XMLElem.NEWLINE);
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1078 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isDeleted()) {
/* 1079 */           EntityItem entityItem = param1DiffEntity2.getCurrentEntityItem();
/* 1080 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1081 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1082 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1088 */         if ("@@".equals(str) && 
/* 1089 */           this.availDiff != null && !this.availDiff.isDeleted()) {
/* 1090 */           EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 1091 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1092 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1093 */             Vector<EntityItem> vector = entityItem.getDownLink();
/* 1094 */             for (byte b = 0; b < vector.size(); b++) {
/* 1095 */               EntityItem entityItem1 = vector.elementAt(b);
/* 1096 */               if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 1097 */                 Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1098 */                 EntityItem entityItem2 = vector1.elementAt(0);
/* 1099 */                 str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1108 */         if ("@@".equals(str) && 
/* 1109 */           this.availDiff != null && !this.availDiff.isDeleted()) {
/* 1110 */           EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 1111 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1112 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1113 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */           }
/* 1115 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubFrom effectivedate of current planedavail thedate: " + str + XMLElem.NEWLINE);
/*      */         } 
/*      */ 
/*      */         
/* 1119 */         if ("@@".equals(str) && 
/* 1120 */           param1DiffEntity1 != null && !param1DiffEntity1.isDeleted() && 
/* 1121 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/* 1122 */           EntityItem entityItem = param1DiffEntity1.getCurrentEntityItem();
/* 1123 */           str = PokUtils.getAttributeValue(entityItem, "ANNDATE", "", "@@", false);
/* 1124 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubFrom anndate of current MODEL thedate: " + str + XMLElem.NEWLINE);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1129 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String deriveAnnNumber(boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1139 */       String str = "@@";
/* 1140 */       if (param1Boolean) {
/*      */         
/* 1142 */         if (this.availDiff != null && !this.availDiff.isNew()) {
/* 1143 */           EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 1144 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1145 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1146 */             Vector<EntityItem> vector = entityItem.getDownLink();
/* 1147 */             for (byte b = 0; b < vector.size(); b++) {
/* 1148 */               EntityItem entityItem1 = vector.elementAt(b);
/* 1149 */               if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 1150 */                 Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1151 */                 EntityItem entityItem2 = vector1.elementAt(0);
/* 1152 */                 str = PokUtils.getAttributeValue(entityItem2, "ANNNUMBER", ", ", "@@", false);
/* 1153 */                 ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveAnnNumber looking for downlink of AVAILANNA : Announcement " + (
/* 1154 */                     (vector1.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem2
/* 1155 */                     .getKey()) : entityItem2.getKey()) + XMLElem.NEWLINE);
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/* 1161 */       } else if (this.availDiff != null && !this.availDiff.isDeleted()) {
/* 1162 */         EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 1163 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1164 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1165 */           Vector<EntityItem> vector = entityItem.getDownLink();
/* 1166 */           for (byte b = 0; b < vector.size(); b++) {
/* 1167 */             EntityItem entityItem1 = vector.elementAt(b);
/* 1168 */             if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 1169 */               Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1170 */               EntityItem entityItem2 = vector1.elementAt(0);
/* 1171 */               str = PokUtils.getAttributeValue(entityItem2, "ANNNUMBER", ", ", "@@", false);
/* 1172 */               ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveAnnNumber looking for downlink of AVAILANNA : Announcement " + (
/* 1173 */                   (vector.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem2
/* 1174 */                   .getKey()) : entityItem2.getKey()) + XMLElem.NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1181 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String deriveAnnDate(boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1191 */       String str = "@@";
/* 1192 */       if (param1Boolean) {
/*      */         
/* 1194 */         if (this.availDiff != null && !this.availDiff.isNew()) {
/* 1195 */           EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 1196 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1197 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1198 */             Vector<EntityItem> vector = entityItem.getDownLink();
/* 1199 */             for (byte b = 0; b < vector.size(); b++) {
/* 1200 */               EntityItem entityItem1 = vector.elementAt(b);
/* 1201 */               if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 1202 */                 Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1203 */                 EntityItem entityItem2 = vector1.elementAt(0);
/* 1204 */                 str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 1205 */                 ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveANNDATE looking for downlink of AVAILANNA : Announcement " + (
/* 1206 */                     (vector1.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem2
/* 1207 */                     .getKey()) : entityItem2.getKey()) + XMLElem.NEWLINE);
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/* 1213 */       } else if (this.availDiff != null && !this.availDiff.isDeleted()) {
/* 1214 */         EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 1215 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1216 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1217 */           Vector<EntityItem> vector = entityItem.getDownLink();
/* 1218 */           for (byte b = 0; b < vector.size(); b++) {
/* 1219 */             EntityItem entityItem1 = vector.elementAt(b);
/* 1220 */             if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 1221 */               Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1222 */               EntityItem entityItem2 = vector1.elementAt(0);
/* 1223 */               str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 1224 */               ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveANNDATE looking for downlink of AVAILANNA : Announcement " + (
/* 1225 */                   (vector.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem2
/* 1226 */                   .getKey()) : entityItem2.getKey()) + XMLElem.NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1232 */       return str;
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
/*      */     private String deriveFIRSTORDER(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1247 */       String str = "@@";
/* 1248 */       ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveFIRSTORDER availDiff: " + this.availDiff.getKey() + " foAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 1249 */           .getKey()) + "findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */       
/* 1251 */       if (param1Boolean) {
/*      */ 
/*      */         
/* 1254 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 1255 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 1256 */           if (entityItem != null) {
/* 1257 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1258 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1259 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 1264 */             ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveFIRSTORDER loavail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*      */           } 
/*      */         } 
/*      */         
/* 1268 */         if ("@@".equals(str))
/*      */         {
/* 1270 */           if (this.availDiff != null && !this.availDiff.isNew()) {
/* 1271 */             EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 1272 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1273 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1274 */               Vector<EntityItem> vector = entityItem.getDownLink();
/* 1275 */               for (byte b = 0; b < vector.size(); b++) {
/* 1276 */                 EntityItem entityItem1 = vector.elementAt(b);
/* 1277 */                 if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 1278 */                   Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1279 */                   EntityItem entityItem2 = vector1.elementAt(0);
/* 1280 */                   str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1289 */         if ("@@".equals(str) && 
/* 1290 */           this.availDiff != null && !this.availDiff.isNew()) {
/* 1291 */           EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 1292 */           if (entityItem != null) {
/* 1293 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1294 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1295 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 1300 */             ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveFIRSTORDER effectivedate of planedavail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*      */           } 
/*      */         } 
/*      */         
/* 1304 */         ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveFIRSTORDER  thedate: " + str + XMLElem.NEWLINE);
/*      */       } else {
/*      */         
/* 1307 */         if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 1308 */           EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 1309 */           if (entityItem != null) {
/* 1310 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1311 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1312 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 1317 */             ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveFIRSTORDER foavail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*      */           } 
/*      */         } 
/*      */         
/* 1321 */         if ("@@".equals(str) && 
/* 1322 */           this.availDiff != null && !this.availDiff.isDeleted()) {
/* 1323 */           EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 1324 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1325 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1326 */             Vector<EntityItem> vector = entityItem.getDownLink();
/* 1327 */             for (byte b = 0; b < vector.size(); b++) {
/* 1328 */               EntityItem entityItem1 = vector.elementAt(b);
/* 1329 */               if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 1330 */                 Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1331 */                 EntityItem entityItem2 = vector1.elementAt(0);
/* 1332 */                 str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1341 */         if ("@@".equals(str) && 
/* 1342 */           this.availDiff != null && !this.availDiff.isDeleted()) {
/* 1343 */           EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 1344 */           if (entityItem != null) {
/* 1345 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1346 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1347 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 1352 */             ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveFIRSTORDER planedavail current Enityitem: " + entityItem + XMLElem.NEWLINE);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1357 */         ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveFIRSTORDER foavail thedate: " + str + XMLElem.NEWLINE);
/*      */       } 
/* 1359 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String deriveEOSANNDATE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1369 */       String str = "@@";
/* 1370 */       ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveEOSANNDATE availDiff: " + this.availDiff.getKey() + " endAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 1371 */           .getKey()) + "findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */       
/* 1373 */       if (param1Boolean) {
/*      */ 
/*      */         
/* 1376 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 1377 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 1378 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*      */           
/* 1380 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country))
/*      */           {
/* 1382 */             Vector<EntityItem> vector = entityItem.getDownLink();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1387 */             for (byte b = 0; b < vector.size(); b++) {
/* 1388 */               EntityItem entityItem1 = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1393 */               if (entityItem1.getEntityType().equals("AVAILANNA") && entityItem1.hasDownLinks()) {
/*      */                 
/* 1395 */                 Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1396 */                 for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1397 */                   EntityItem entityItem2 = vector1.elementAt(b1);
/*      */ 
/*      */ 
/*      */                   
/* 1401 */                   EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute("ANNTYPE");
/* 1402 */                   if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected("13")) {
/* 1403 */                     str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/*      */                   } else {
/* 1405 */                     ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveEOSANNDATE ANNTYPE: " + 
/* 1406 */                         PokUtils.getAttributeFlagValue(entityItem2, "ANNTYPE") + "is not equal End Of Life - Discontinuance of service(13)" + XMLElem.NEWLINE);
/*      */                   } 
/*      */                 } 
/*      */               } else {
/*      */                 
/* 1411 */                 ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveEOSANNDATE no downlink of AVAILANNA was found" + XMLElem.NEWLINE);
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1421 */       else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 1422 */         EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 1423 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*      */         
/* 1425 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*      */           
/* 1427 */           Vector<EntityItem> vector = entityItem.getDownLink();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1432 */           for (byte b = 0; b < vector.size(); b++) {
/* 1433 */             EntityItem entityItem1 = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1438 */             if (entityItem1.getEntityType().equals("AVAILANNA") && entityItem1.hasDownLinks()) {
/*      */               
/* 1440 */               Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1441 */               for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1442 */                 EntityItem entityItem2 = vector1.elementAt(b1);
/*      */ 
/*      */ 
/*      */                 
/* 1446 */                 EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute("ANNTYPE");
/* 1447 */                 if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected("13")) {
/* 1448 */                   str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/*      */                 } else {
/* 1450 */                   ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveEOSANNDATE ANNTYPE: " + 
/* 1451 */                       PokUtils.getAttributeFlagValue(entityItem2, "ANNTYPE") + "is not equal End Of Life - Discontinuance of service(13)" + XMLElem.NEWLINE);
/*      */                 } 
/*      */               } 
/*      */             } else {
/*      */               
/* 1456 */               ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveEOSANNDATE no downlink of AVAILANNA was found" + XMLElem.NEWLINE);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1466 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String deriveWDANNDATE(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1476 */       String str = "@@";
/* 1477 */       ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveWDANNDATE availDiff: " + this.availDiff.getKey() + " loAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 1478 */           .getKey()) + "findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */       
/* 1480 */       if (param1Boolean) {
/*      */ 
/*      */         
/* 1483 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isNew()) {
/* 1484 */           EntityItem entityItem = param1DiffEntity2.getPriorEntityItem();
/* 1485 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1486 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*      */             
/* 1488 */             Vector<EntityItem> vector = entityItem.getDownLink();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1493 */             for (byte b = 0; b < vector.size(); b++) {
/* 1494 */               EntityItem entityItem1 = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1499 */               if (entityItem1.getEntityType().equals("AVAILANNA") && entityItem1.hasDownLinks()) {
/*      */                 
/* 1501 */                 Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1502 */                 for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1503 */                   EntityItem entityItem2 = vector1.elementAt(b1);
/*      */ 
/*      */ 
/*      */                   
/* 1507 */                   EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute("ANNTYPE");
/* 1508 */                   if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected("14")) {
/* 1509 */                     str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/*      */                   } else {
/* 1511 */                     ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveWDANNDATE ANNTYPE: " + 
/* 1512 */                         PokUtils.getAttributeFlagValue(entityItem2, "ANNTYPE") + "is not equal 14(End Of Life - Withdrawal from mktg)" + XMLElem.NEWLINE);
/*      */                   } 
/*      */                 } 
/*      */               } else {
/* 1516 */                 ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveWDANNDATE no downlink of AVAILANNA was found" + XMLElem.NEWLINE);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1524 */         if ("@@".equals(str) && 
/* 1525 */           param1DiffEntity1 != null && !param1DiffEntity1.isNew() && 
/* 1526 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/* 1527 */           EntityItem entityItem = param1DiffEntity1.getPriorEntityItem();
/* 1528 */           str = PokUtils.getAttributeValue(entityItem, "WITHDRAWDATE", "", "@@", false);
/* 1529 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveWDANNDATE WITHDRAWDATE of prior MODEL thedate: " + str + XMLElem.NEWLINE);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1534 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isDeleted()) {
/* 1535 */           EntityItem entityItem = param1DiffEntity2.getCurrentEntityItem();
/* 1536 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1537 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*      */             
/* 1539 */             Vector<EntityItem> vector = entityItem.getDownLink();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1544 */             for (byte b = 0; b < vector.size(); b++) {
/* 1545 */               EntityItem entityItem1 = vector.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1550 */               if (entityItem1.getEntityType().equals("AVAILANNA") && entityItem1.hasDownLinks()) {
/*      */                 
/* 1552 */                 Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 1553 */                 for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1554 */                   EntityItem entityItem2 = vector1.elementAt(b1);
/*      */ 
/*      */ 
/*      */                   
/* 1558 */                   EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute("ANNTYPE");
/* 1559 */                   if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected("14")) {
/* 1560 */                     str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/*      */                   } else {
/* 1562 */                     ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveWDANNDATE ANNTYPE: " + 
/* 1563 */                         PokUtils.getAttributeFlagValue(entityItem2, "ANNTYPE") + "is not equal 14(End Of Life - Withdrawal from mktg)" + XMLElem.NEWLINE);
/*      */                   } 
/*      */                 } 
/*      */               } else {
/* 1567 */                 ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveWDANNDATE no downlink of AVAILANNA was found" + XMLElem.NEWLINE);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1574 */         if ("@@".equals(str) && 
/* 1575 */           param1DiffEntity1 != null && !param1DiffEntity1.isDeleted() && 
/* 1576 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/* 1577 */           EntityItem entityItem = param1DiffEntity1.getCurrentEntityItem();
/* 1578 */           str = PokUtils.getAttributeValue(entityItem, "WITHDRAWDATE", "", "@@", false);
/* 1579 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveWDANNDATE WITHDRAWDATE of current MODEL thedate: " + str + XMLElem.NEWLINE);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1584 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String deriveLastOrder(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1594 */       ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveLastOrder  loAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 1595 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */       
/* 1597 */       String str = "@@";
/* 1598 */       if (param1Boolean) {
/*      */ 
/*      */         
/* 1601 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isNew()) {
/* 1602 */           EntityItem entityItem = param1DiffEntity2.getPriorEntityItem();
/*      */           
/* 1604 */           if (entityItem != null) {
/* 1605 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1606 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1607 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 1612 */             ABRUtil.append(param1StringBuffer, "XMLSVCSEOAVAILElem.deriveLastOrder loavail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*      */           } 
/*      */         } 
/*      */         
/* 1616 */         if ("@@".equals(str) && 
/* 1617 */           param1DiffEntity1 != null && !param1DiffEntity1.isNew() && 
/* 1618 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/* 1619 */           EntityItem entityItem = param1DiffEntity1.getPriorEntityItem();
/* 1620 */           str = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", "", "@@", false);
/* 1621 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLastOrder WTHDRWEFFCTVDATE of prior MODEL thedate: " + str + XMLElem.NEWLINE);
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1627 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isDeleted()) {
/* 1628 */           EntityItem entityItem = param1DiffEntity2.getCurrentEntityItem();
/* 1629 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*      */           
/* 1631 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1632 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1637 */         if ("@@".equals(str) && 
/* 1638 */           param1DiffEntity1 != null && !param1DiffEntity1.isDeleted() && 
/* 1639 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/* 1640 */           EntityItem entityItem = param1DiffEntity1.getCurrentEntityItem();
/* 1641 */           str = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", "", "@@", false);
/* 1642 */           ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveLastOrder WTHDRWEFFCTVDATE of current MODEL thedate: " + str + XMLElem.NEWLINE);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1647 */       return str;
/*      */     }
/*      */     
/*      */     String getAction() {
/* 1651 */       return this.action;
/*      */     }
/*      */     
/*      */     String getCountry() {
/* 1655 */       return this.country;
/*      */     }
/*      */ 
/*      */     
/*      */     String getPubFrom() {
/* 1660 */       return this.pubfrom;
/*      */     }
/*      */     
/*      */     String getPubTo() {
/* 1664 */       return this.pubto;
/*      */     }
/*      */     
/*      */     String getEndOfService() {
/* 1668 */       return this.endofservice;
/*      */     }
/*      */     
/*      */     String getAvailStatus() {
/* 1672 */       return this.availStatus;
/*      */     }
/*      */     
/*      */     String getAnndate() {
/* 1676 */       return this.anndate;
/*      */     }
/*      */     
/*      */     String getFirstorder() {
/* 1680 */       return this.firstorder;
/*      */     }
/*      */     
/*      */     String getPlannedavailability() {
/* 1684 */       return this.plannedavailability;
/*      */     }
/*      */     
/*      */     String getWdanndate() {
/* 1688 */       return this.wdanndate;
/*      */     }
/*      */     
/*      */     String getLastorder() {
/* 1692 */       return this.lastorder;
/*      */     }
/*      */     
/*      */     String getEosanndate() {
/* 1696 */       return this.eosanndate;
/*      */     }
/*      */     
/*      */     String getAnnnumber() {
/* 1700 */       return this.annnumber;
/*      */     }
/*      */     
/*      */     boolean isDeleted() {
/* 1704 */       return "Delete".equals(this.action);
/*      */     }
/*      */     
/*      */     String getKey() {
/* 1708 */       return this.country;
/*      */     }
/*      */     
/*      */     void dereference() {
/* 1712 */       this.availDiff = null;
/* 1713 */       this.action = null;
/* 1714 */       this.country = null;
/* 1715 */       this.availStatus = null;
/* 1716 */       this.pubfrom = null;
/* 1717 */       this.pubto = null;
/* 1718 */       this.endofservice = null;
/* 1719 */       this.anndate = null;
/* 1720 */       this.firstorder = null;
/* 1721 */       this.plannedavailability = null;
/* 1722 */       this.wdanndate = null;
/* 1723 */       this.lastorder = null;
/* 1724 */       this.eosanndate = null;
/* 1725 */       this.annnumber = null;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1729 */       return this.availDiff.getKey() + " " + getKey() + " action:" + this.action;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLSVCSEOAVAILElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */