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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLSVCMODAVAILElembh1
/*      */   extends XMLElem
/*      */ {
/*  109 */   protected Vector childVct = new Vector(1);
/*  110 */   private static String availRelator = "";
/*  111 */   private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLSVCMODAVAILElembh1() {
/*  196 */     super("AVAILABILITYELEMENT");
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
/*  213 */     ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1:parentItem: " + paramDiffEntity.getKey() + NEWLINE);
/*  214 */     if (paramDiffEntity.getEntityType().equals("SVCMOD")) {
/*  215 */       availRelator = "SVCMODAVAIL";
/*      */     } else {
/*  217 */       availRelator = "SVCSEOAVAIL";
/*      */     } 
/*      */ 
/*      */     
/*  221 */     boolean bool = false;
/*  222 */     boolean bool1 = false;
/*  223 */     bool = AvailUtil.iscompatmodel();
/*  224 */     ABRUtil.append(paramStringBuffer, "compatModel compatbility mode:" + bool);
/*  225 */     if (!bool) {
/*      */       
/*  227 */       String str = null;
/*  228 */       str = (String)paramHashtable.get("_chSTATUS");
/*  229 */       ABRUtil.append(paramStringBuffer, "the status is" + str + NEWLINE);
/*  230 */       if ("0020".equals(str)) {
/*  231 */         bool1 = true;
/*      */       } else {
/*  233 */         bool1 = false;
/*      */       } 
/*  235 */       ABRUtil.append(paramStringBuffer, "isExistfinal :" + bool1);
/*      */     } 
/*      */ 
/*      */     
/*  239 */     Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, paramDiffEntity, paramStringBuffer);
/*      */     
/*  241 */     if (vector.size() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  248 */       TreeMap<Object, Object> treeMap = new TreeMap<>(); byte b;
/*  249 */       for (b = 0; b < vector.size(); b++) {
/*  250 */         DiffEntity diffEntity = vector.elementAt(b);
/*  251 */         buildCtryAudRecs(treeMap, diffEntity, true, paramStringBuffer);
/*      */       } 
/*      */ 
/*      */       
/*  255 */       for (b = 0; b < vector.size(); b++) {
/*  256 */         DiffEntity diffEntity = vector.elementAt(b);
/*  257 */         buildCtryAudRecs(treeMap, diffEntity, false, paramStringBuffer);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  264 */       Collection collection = treeMap.values();
/*  265 */       Iterator<CtryAudRecord> iterator = collection.iterator();
/*  266 */       while (iterator.hasNext()) {
/*  267 */         CtryAudRecord ctryAudRecord = iterator.next();
/*      */ 
/*      */ 
/*      */         
/*  271 */         DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/*  272 */             .getCountry(), paramStringBuffer);
/*      */         
/*  274 */         DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  275 */             .getCountry(), paramStringBuffer);
/*  276 */         DiffEntity diffEntity3 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/*  277 */             .getCountry(), paramStringBuffer);
/*      */         
/*  279 */         DiffEntity diffEntity4 = getEntityForAttrs(paramHashtable, paramDiffEntity, "AVAIL", "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRecord
/*  280 */             .getCountry(), paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  285 */         ctryAudRecord.setAllFields(paramDiffEntity, diffEntity1, diffEntity2, diffEntity3, diffEntity4, paramHashtable, bool1, bool, paramStringBuffer);
/*      */         
/*  287 */         if (ctryAudRecord.isDisplayable() || ctryAudRecord.isrfrDisplayable()) {
/*  288 */           createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity, ctryAudRecord, paramStringBuffer);
/*      */         } else {
/*      */           
/*  291 */           ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*      */         } 
/*  293 */         ctryAudRecord.dereference();
/*      */       } 
/*      */ 
/*      */       
/*  297 */       treeMap.clear();
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  307 */       ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.addElements no planned AVAILs found" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeSet(Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/*  317 */     if (paramCtryAudRecord.isDisplayable()) {
/*      */       
/*  319 */       Element element1 = paramDocument.createElement(this.nodeName);
/*  320 */       addXMLAttrs(element1);
/*  321 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  324 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  325 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/*  326 */       element1.appendChild(element2);
/*  327 */       element2 = paramDocument.createElement("STATUS");
/*  328 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/*  329 */       element1.appendChild(element2);
/*  330 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  331 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  332 */       element1.appendChild(element2);
/*  333 */       element2 = paramDocument.createElement("ANNDATE");
/*  334 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnndate()));
/*  335 */       element1.appendChild(element2);
/*  336 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  337 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnnnumber()));
/*  338 */       element1.appendChild(element2);
/*  339 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  340 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getFirstorder()));
/*  341 */       element1.appendChild(element2);
/*  342 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  343 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/*  344 */       element1.appendChild(element2);
/*  345 */       element2 = paramDocument.createElement("PUBFROM");
/*  346 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/*  347 */       element1.appendChild(element2);
/*  348 */       element2 = paramDocument.createElement("PUBTO");
/*  349 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/*  350 */       element1.appendChild(element2);
/*  351 */       element2 = paramDocument.createElement("WDANNDATE");
/*  352 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getWdanndate()));
/*  353 */       element1.appendChild(element2);
/*  354 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  355 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEomannnum()));
/*  356 */       element1.appendChild(element2);
/*  357 */       element2 = paramDocument.createElement("LASTORDER");
/*  358 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getLastorder()));
/*  359 */       element1.appendChild(element2);
/*  360 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  361 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosanndate()));
/*  362 */       element1.appendChild(element2);
/*  363 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  364 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/*  365 */       element1.appendChild(element2);
/*      */       
/*  367 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*  368 */       if (availRelator.equals("SVCMODAVAIL")) {
/*  369 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, entityItem, paramCtryAudRecord.availDiff, "D:SVCMODAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */       
/*      */       }
/*  372 */       else if (paramDiffEntity.isNew()) {
/*      */         
/*  374 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, entityItem, paramCtryAudRecord.availDiff, "NEWD:SVCMOD:D:SVCMODSVCSEO:D:SVCSEO:D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */       } else {
/*  376 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, entityItem, paramCtryAudRecord.availDiff, "D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  381 */     if (paramCtryAudRecord.isrfrDisplayable()) {
/*  382 */       Element element1 = paramDocument.createElement(this.nodeName);
/*      */       
/*  384 */       addXMLAttrs(element1);
/*  385 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  388 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  389 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfraction()));
/*  390 */       element1.appendChild(element2);
/*  391 */       element2 = paramDocument.createElement("STATUS");
/*  392 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfravailStatus()));
/*  393 */       element1.appendChild(element2);
/*  394 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  395 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  396 */       element1.appendChild(element2);
/*  397 */       element2 = paramDocument.createElement("ANNDATE");
/*  398 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfranndate()));
/*  399 */       element1.appendChild(element2);
/*  400 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  401 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrannnumber()));
/*  402 */       element1.appendChild(element2);
/*  403 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  404 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrfirstorder()));
/*  405 */       element1.appendChild(element2);
/*  406 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  407 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrplannedavailability()));
/*  408 */       element1.appendChild(element2);
/*  409 */       element2 = paramDocument.createElement("PUBFROM");
/*  410 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubfrom()));
/*  411 */       element1.appendChild(element2);
/*  412 */       element2 = paramDocument.createElement("PUBTO");
/*  413 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubto()));
/*  414 */       element1.appendChild(element2);
/*  415 */       element2 = paramDocument.createElement("WDANNDATE");
/*  416 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrwdanndate()));
/*  417 */       element1.appendChild(element2);
/*  418 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  419 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEomannnum()));
/*  420 */       element1.appendChild(element2);
/*  421 */       element2 = paramDocument.createElement("LASTORDER");
/*  422 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrlastorder()));
/*  423 */       element1.appendChild(element2);
/*  424 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  425 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosanndate()));
/*  426 */       element1.appendChild(element2);
/*  427 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  428 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrendofservice()));
/*  429 */       element1.appendChild(element2);
/*      */       
/*  431 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*  432 */       if (availRelator.equals("SVCMODAVAIL")) {
/*  433 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, entityItem, paramCtryAudRecord.availDiff, "D:SVCMODAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */       
/*      */       }
/*  436 */       else if (paramDiffEntity.isNew()) {
/*      */         
/*  438 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, entityItem, paramCtryAudRecord.availDiff, "NEWD:SVCMOD:D:SVCMODSVCSEO:D:SVCSEO:D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */       } else {
/*  440 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, entityItem, paramCtryAudRecord.availDiff, "D:SVCSEOAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
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
/*      */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  465 */     ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.buildCtryAudRecs build T1 country list " + paramBoolean + " " + paramDiffEntity.getKey() + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  471 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  472 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  473 */     if (paramBoolean) {
/*  474 */       if (!paramDiffEntity.isNew()) {
/*      */         
/*  476 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  477 */         ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.buildCtryAudRecs for deleted / update avail at T1: ctryAtt " + 
/*  478 */             PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/*  479 */         if (eANFlagAttribute != null) {
/*  480 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  481 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/*  483 */             if (arrayOfMetaFlag[b].isSelected()) {
/*  484 */               String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  485 */               String str2 = str1;
/*  486 */               if (paramTreeMap.containsKey(str2)) {
/*      */                 
/*  488 */                 CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/*  489 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted / update " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */               } else {
/*      */                 
/*  492 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/*  493 */                 ctryAudRecord.setAction("Delete");
/*  494 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  501 */     } else if (!paramDiffEntity.isDeleted()) {
/*      */       
/*  503 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  504 */       ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.buildCtryAudRecs for new /update avail:  ctryAtt and anncodeAtt " + 
/*  505 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/*  506 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/*  507 */       if (eANFlagAttribute != null) {
/*  508 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  509 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  511 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  512 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  513 */             String str2 = str1;
/*  514 */             if (paramTreeMap.containsKey(str2)) {
/*  515 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  516 */               if ("Delete".equals(ctryAudRecord.action)) {
/*  517 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new /udpate" + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */                 
/*  519 */                 ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*  520 */                 ctryAudRecord.setAction("@@");
/*      */               } 
/*      */             } else {
/*  523 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/*  524 */               ctryAudRecord.setAction("Update");
/*  525 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  526 */               ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  527 */                   .getKey() + NEWLINE);
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
/*      */   private Vector getPlannedAvails(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  540 */     Vector<DiffEntity> vector1 = new Vector(1);
/*  541 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/*  542 */     ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/*  543 */         .size())) + NEWLINE);
/*  544 */     if (vector2 == null) {
/*  545 */       return vector1;
/*      */     }
/*      */ 
/*      */     
/*  549 */     for (byte b = 0; b < vector2.size(); b++) {
/*  550 */       DiffEntity diffEntity = vector2.elementAt(b);
/*  551 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/*  552 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*  553 */       if (deriveTheSameEntry(diffEntity, paramDiffEntity, paramStringBuffer)) {
/*  554 */         if (diffEntity.isDeleted()) {
/*  555 */           ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/*  556 */               PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/*  557 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/*  558 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  559 */             vector1.add(diffEntity);
/*      */           }
/*      */         } else {
/*  562 */           ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/*  563 */               PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/*  564 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/*  565 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  566 */             vector1.add(diffEntity);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  572 */     return vector1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deriveTheSameEntry(DiffEntity paramDiffEntity1, DiffEntity paramDiffEntity2, StringBuffer paramStringBuffer) {
/*  582 */     boolean bool = false;
/*  583 */     if (paramDiffEntity1 != null) {
/*  584 */       if (availRelator.equals("SVCMODAVAIL")) {
/*  585 */         bool = paramDiffEntity1.getParentKey().startsWith(availRelator);
/*      */       } else {
/*  587 */         StringTokenizer stringTokenizer = new StringTokenizer(paramDiffEntity1.toString(), " ");
/*  588 */         String str = "@@";
/*  589 */         while (stringTokenizer.hasMoreTokens()) {
/*  590 */           str = stringTokenizer.nextToken();
/*  591 */           if (str.startsWith("path:"))
/*      */             break; 
/*      */         } 
/*  594 */         if (!"@@".equals(str)) {
/*  595 */           StringTokenizer stringTokenizer1 = new StringTokenizer(str, ":");
/*  596 */           while (stringTokenizer1.hasMoreTokens()) {
/*  597 */             String str1 = stringTokenizer1.nextToken();
/*  598 */             if (str1.startsWith(availRelator) && 
/*  599 */               stringTokenizer1.hasMoreTokens()) {
/*  600 */               if (stringTokenizer1.nextToken().startsWith(paramDiffEntity2.getKey())) {
/*  601 */                 bool = true;
/*      */               }
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  610 */     ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.deriveTheSameEntry is " + bool + " availrelator: " + availRelator + " ParentKey: " + paramDiffEntity1
/*  611 */         .getParentKey() + " Key: " + paramDiffEntity1.toString() + NEWLINE);
/*  612 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DiffEntity getEntityForAttrs(Hashtable paramHashtable, DiffEntity paramDiffEntity, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, StringBuffer paramStringBuffer) {
/*  621 */     DiffEntity diffEntity = null;
/*  622 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get(paramString1);
/*  623 */     ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.getEntityForAttrs looking for " + paramString2 + ":" + paramString3 + " and " + paramString4 + ":" + paramString5 + " in " + paramString1 + " allVct.size:" + ((vector == null) ? "null" : ("" + vector
/*  624 */         .size())) + NEWLINE);
/*  625 */     if (vector == null) {
/*  626 */       return diffEntity;
/*      */     }
/*      */     
/*  629 */     for (byte b = 0; b < vector.size(); b++) {
/*  630 */       DiffEntity diffEntity1 = vector.elementAt(b);
/*  631 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/*  632 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/*  633 */       if (deriveTheSameEntry(diffEntity1, paramDiffEntity, paramStringBuffer)) {
/*  634 */         if (diffEntity1.isDeleted()) {
/*  635 */           ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  636 */               PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/*  637 */               PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/*  638 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/*  639 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  640 */             eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/*  641 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  642 */               diffEntity = diffEntity1;
/*      */             }
/*      */           }
/*      */         
/*  646 */         } else if (diffEntity1.isNew()) {
/*  647 */           ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  648 */               PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/*  649 */               PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/*  650 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/*  651 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  652 */             eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/*  653 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  654 */               diffEntity = diffEntity1;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } else {
/*  660 */           ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.getEntityForAttrs checking[" + b + "]: current " + diffEntity1
/*  661 */               .getKey() + " " + paramString2 + ":" + PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/*  662 */               PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/*  663 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/*  664 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  665 */             eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/*  666 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  667 */               diffEntity = diffEntity1;
/*      */               break;
/*      */             } 
/*      */           } 
/*  671 */           ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  672 */               PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/*  673 */               PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/*  674 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/*  675 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  676 */             eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/*  677 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  678 */               diffEntity = diffEntity1;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  687 */     return diffEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CtryAudRecord
/*      */     extends CtryRecord
/*      */   {
/*      */     private String country;
/*      */ 
/*      */     
/*      */     private DiffEntity availDiff;
/*      */ 
/*      */ 
/*      */     
/*      */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String) {
/*  703 */       super((String)null);
/*  704 */       this.country = param1String;
/*  705 */       this.availDiff = param1DiffEntity;
/*      */     }
/*      */ 
/*      */     
/*      */     void setUpdateAvail(DiffEntity param1DiffEntity) {
/*  710 */       this.availDiff = param1DiffEntity;
/*  711 */       setAction("Update");
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
/*      */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, Hashtable param1Hashtable, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/*  737 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + NEWLINE);
/*      */ 
/*      */       
/*  740 */       this.availStatus = "0020";
/*  741 */       this.rfravailStatus = "0040";
/*      */ 
/*      */       
/*  744 */       String[] arrayOfString1 = deriveAnnDate(param1DiffEntity1, false, param1StringBuffer);
/*  745 */       String[] arrayOfString2 = deriveAnnDate(param1DiffEntity1, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/*  749 */       String[] arrayOfString3 = deriveAnnNumber(false, param1StringBuffer);
/*  750 */       String[] arrayOfString4 = deriveAnnNumber(true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/*  754 */       String[] arrayOfString5 = deriveFIRSTORDER(param1DiffEntity1, param1DiffEntity2, false, param1StringBuffer);
/*  755 */       String[] arrayOfString6 = deriveFIRSTORDER(param1DiffEntity1, param1DiffEntity2, true, param1StringBuffer);
/*      */ 
/*      */       
/*  758 */       String[] arrayOfString7 = derivePlannedavailability(param1DiffEntity1, false, param1StringBuffer);
/*  759 */       String[] arrayOfString8 = derivePlannedavailability(param1DiffEntity1, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  771 */       String[] arrayOfString9 = derivePubFrom(param1DiffEntity1, param1DiffEntity2, false, param1StringBuffer);
/*  772 */       String[] arrayOfString10 = derivePubFrom(param1DiffEntity1, param1DiffEntity2, true, param1StringBuffer);
/*      */ 
/*      */       
/*  775 */       String[] arrayOfString11 = derivePubTo(param1DiffEntity1, param1DiffEntity3, false, param1StringBuffer);
/*  776 */       String[] arrayOfString12 = derivePubTo(param1DiffEntity1, param1DiffEntity3, true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/*  780 */       String[] arrayOfString13 = deriveWDANNDATE(param1DiffEntity1, param1DiffEntity5, false, param1StringBuffer);
/*  781 */       String[] arrayOfString14 = deriveWDANNDATE(param1DiffEntity1, param1DiffEntity5, true, param1StringBuffer);
/*      */ 
/*      */       
/*  784 */       String[] arrayOfString15 = deriveENDOFMARKETANNNUMBER(param1DiffEntity5, false, param1StringBuffer);
/*  785 */       String[] arrayOfString16 = deriveENDOFMARKETANNNUMBER(param1DiffEntity5, true, param1StringBuffer);
/*      */ 
/*      */       
/*  788 */       String[] arrayOfString17 = deriveLastOrder(param1DiffEntity1, param1DiffEntity3, false, param1StringBuffer);
/*  789 */       String[] arrayOfString18 = deriveLastOrder(param1DiffEntity1, param1DiffEntity3, true, param1StringBuffer);
/*      */ 
/*      */       
/*  792 */       String[] arrayOfString19 = deriveEOSANNDATE(param1DiffEntity4, false, param1StringBuffer);
/*  793 */       String[] arrayOfString20 = deriveEOSANNDATE(param1DiffEntity4, true, param1StringBuffer);
/*      */ 
/*      */       
/*  796 */       String[] arrayOfString21 = deriveENDOFSERVICE(param1DiffEntity4, false, param1StringBuffer);
/*  797 */       String[] arrayOfString22 = deriveENDOFSERVICE(param1DiffEntity4, true, param1StringBuffer);
/*      */ 
/*      */       
/*  800 */       handleResults(arrayOfString1, arrayOfString2, arrayOfString3, arrayOfString4, arrayOfString5, arrayOfString6, arrayOfString7, arrayOfString8, arrayOfString9, arrayOfString10, arrayOfString11, arrayOfString12, arrayOfString13, arrayOfString14, arrayOfString15, arrayOfString16, arrayOfString17, arrayOfString18, arrayOfString21, arrayOfString22, arrayOfString19, arrayOfString20, this.country, param1Boolean1, param1Boolean2, param1StringBuffer);
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
/*      */     private String[] deriveENDOFSERVICE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  850 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveEndOfService  eofAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/*  851 */           .getKey()) + " findT1:" + param1Boolean + NEWLINE);
/*      */       
/*  853 */       String str1 = "@@";
/*  854 */       String str2 = "@@";
/*  855 */       String[] arrayOfString1 = new String[2];
/*  856 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  858 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  860 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  861 */         str1 = arrayOfString2[0];
/*  862 */         str2 = arrayOfString2[1];
/*      */       } 
/*  864 */       arrayOfString1[0] = str1;
/*  865 */       arrayOfString1[1] = str2;
/*  866 */       return arrayOfString1;
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
/*      */     private String[] derivePubTo(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  878 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubTo  loAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + " findT1:" + param1Boolean + NEWLINE);
/*      */       
/*  880 */       String str1 = "@@";
/*  881 */       String str2 = "@@";
/*  882 */       String[] arrayOfString1 = new String[2];
/*  883 */       String[] arrayOfString2 = new String[2];
/*      */ 
/*      */       
/*  886 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  888 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  889 */         str1 = arrayOfString2[0];
/*  890 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */ 
/*      */       
/*  894 */       if ("@@".equals(str1) || "@@".equals(str2))
/*      */       {
/*  896 */         if (param1DiffEntity1 != null && !param1DiffEntity1.isDeleted() && 
/*  897 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/*  898 */           arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "WTHDRWEFFCTVDATE", param1StringBuffer);
/*  899 */           str1 = arrayOfString2[0];
/*  900 */           str2 = arrayOfString2[1];
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  905 */       arrayOfString1[0] = str1;
/*  906 */       arrayOfString1[1] = str2;
/*  907 */       return arrayOfString1;
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
/*      */     private String[] derivePubFrom(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  920 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubFrom availDiff: " + this.availDiff.getKey() + " foAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/*  921 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/*  923 */       String str1 = "@@";
/*  924 */       String str2 = "@@";
/*  925 */       String[] arrayOfString1 = new String[2];
/*  926 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  928 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  930 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  931 */         str1 = arrayOfString2[0];
/*  932 */         str2 = arrayOfString2[1];
/*      */       } 
/*  934 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/*  937 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/*  938 */         str1 = arrayOfString2[0];
/*  939 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  942 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  944 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  945 */         str1 = arrayOfString2[0];
/*  946 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */ 
/*      */       
/*  950 */       if ("@@".equals(str1) || "@@".equals(str2))
/*      */       {
/*  952 */         if (param1DiffEntity1 != null && !param1DiffEntity1.isDeleted() && 
/*  953 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/*  954 */           arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "ANNDATE", param1StringBuffer);
/*  955 */           str1 = arrayOfString2[0];
/*  956 */           str2 = arrayOfString2[1];
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  961 */       arrayOfString1[0] = str1;
/*  962 */       arrayOfString1[1] = str2;
/*  963 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnNumber(boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  973 */       String str1 = "@@";
/*  974 */       String str2 = "@@";
/*  975 */       String[] arrayOfString1 = new String[2];
/*  976 */       String[] arrayOfString2 = new String[2];
/*  977 */       ABRUtil.append(param1StringBuffer, "XMLLSEOAVAILElembh1.deriveAnnNumber availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/*  979 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  981 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/*  982 */         str1 = arrayOfString2[0];
/*  983 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  986 */       arrayOfString1[0] = str1;
/*  987 */       arrayOfString1[1] = str2;
/*  988 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnDate(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  998 */       String str1 = "@@";
/*  999 */       String str2 = "@@";
/* 1000 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 1002 */       String[] arrayOfString2 = new String[2];
/* 1003 */       ABRUtil.append(param1StringBuffer, "XMLLSEOAVAILElembh1.deriveAnnDate availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 1006 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 1007 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 1008 */         str1 = arrayOfString2[0];
/* 1009 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1012 */       if ("@@".equals(str1) && "@@".equals(str2))
/*      */       {
/* 1014 */         if (param1DiffEntity != null && !param1DiffEntity.isDeleted() && 
/* 1015 */           "SVCMOD".equals(param1DiffEntity.getEntityType())) {
/* 1016 */           arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity, str1, str2, "ANNDATE", param1StringBuffer);
/* 1017 */           str1 = arrayOfString2[0];
/* 1018 */           str2 = arrayOfString2[1];
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 1023 */       arrayOfString1[0] = str1;
/* 1024 */       arrayOfString1[1] = str2;
/* 1025 */       return arrayOfString1;
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
/*      */     private String[] deriveFIRSTORDER(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1040 */       String str1 = "@@";
/* 1041 */       String str2 = "@@";
/* 1042 */       String[] arrayOfString1 = new String[2];
/* 1043 */       String[] arrayOfString2 = new String[2];
/* 1044 */       ABRUtil.append(param1StringBuffer, "XMLLSEOAVAILElembh1.deriveFIRSTORDER availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + "foAvailDiff " + param1DiffEntity2 + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 1045 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 1047 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1049 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1050 */         str1 = arrayOfString2[0];
/* 1051 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1054 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 1057 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 1058 */         str1 = arrayOfString2[0];
/* 1059 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1062 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1064 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1065 */         str1 = arrayOfString2[0];
/* 1066 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */ 
/*      */       
/* 1070 */       if ("@@".equals(str1) || "@@".equals(str2))
/*      */       {
/* 1072 */         if (param1DiffEntity1 != null && !param1DiffEntity1.isDeleted() && 
/* 1073 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/* 1074 */           arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "ANNDATE", param1StringBuffer);
/* 1075 */           str1 = arrayOfString2[0];
/* 1076 */           str2 = arrayOfString2[1];
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 1081 */       arrayOfString1[0] = str1;
/* 1082 */       arrayOfString1[1] = str2;
/* 1083 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] derivePlannedavailability(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1094 */       String str1 = "@@";
/* 1095 */       String str2 = "@@";
/* 1096 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 1098 */       String[] arrayOfString2 = new String[2];
/* 1099 */       ABRUtil.append(param1StringBuffer, "XMLLSEOAVAILElembh1.derivePlannedavailability availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 1102 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 1103 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1104 */         str1 = arrayOfString2[0];
/* 1105 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */ 
/*      */       
/* 1109 */       if ("@@".equals(str1) || "@@".equals(str2))
/*      */       {
/* 1111 */         if (param1DiffEntity != null && !param1DiffEntity.isDeleted() && 
/* 1112 */           "SVCMOD".equals(param1DiffEntity.getEntityType())) {
/* 1113 */           arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity, str1, str2, "ANNDATE", param1StringBuffer);
/* 1114 */           str1 = arrayOfString2[0];
/* 1115 */           str2 = arrayOfString2[1];
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 1120 */       arrayOfString1[0] = str1;
/* 1121 */       arrayOfString1[1] = str2;
/* 1122 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveEOSANNDATE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1132 */       String str1 = "@@";
/* 1133 */       String str2 = "@@";
/* 1134 */       String[] arrayOfString1 = new String[2];
/* 1135 */       String[] arrayOfString2 = new String[2];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1144 */       arrayOfString1[0] = str1;
/* 1145 */       arrayOfString1[1] = str2;
/* 1146 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveWDANNDATE(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1155 */       String str1 = "@@";
/* 1156 */       String str2 = "@@";
/* 1157 */       String[] arrayOfString1 = new String[2];
/* 1158 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1160 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1162 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity2, str1, str2, this.country, "14", param1StringBuffer);
/* 1163 */         str1 = arrayOfString2[0];
/* 1164 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */ 
/*      */       
/* 1168 */       if ("@@".equals(str1) || "@@".equals(str2))
/*      */       {
/* 1170 */         if (param1DiffEntity1 != null && !param1DiffEntity1.isDeleted() && 
/* 1171 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/* 1172 */           arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "WITHDRAWDATE", param1StringBuffer);
/* 1173 */           str1 = arrayOfString2[0];
/* 1174 */           str2 = arrayOfString2[1];
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 1179 */       arrayOfString1[0] = str1;
/* 1180 */       arrayOfString1[1] = str2;
/* 1181 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFMARKETANNNUMBER(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1190 */       String str1 = "@@";
/* 1191 */       String str2 = "@@";
/* 1192 */       String[] arrayOfString1 = new String[2];
/* 1193 */       String[] arrayOfString2 = new String[2];
/* 1194 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveEndOfMarketAnnNumber endMktAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 1196 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1198 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/* 1199 */         str1 = arrayOfString2[0];
/* 1200 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1203 */       arrayOfString1[0] = str1;
/* 1204 */       arrayOfString1[1] = str2;
/* 1205 */       return arrayOfString1;
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
/*      */     private String[] deriveLastOrder(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 1217 */       String str1 = "@@";
/* 1218 */       String str2 = "@@";
/* 1219 */       String[] arrayOfString1 = new String[2];
/* 1220 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1222 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1224 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity2, str1, str2, this.country, "14", param1StringBuffer);
/* 1225 */         str1 = arrayOfString2[0];
/* 1226 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */ 
/*      */       
/* 1230 */       if ("@@".equals(str1) || "@@".equals(str2))
/*      */       {
/* 1232 */         if (param1DiffEntity1 != null && !param1DiffEntity1.isDeleted() && 
/* 1233 */           "SVCMOD".equals(param1DiffEntity1.getEntityType())) {
/* 1234 */           arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "WITHDRAWDATE", param1StringBuffer);
/* 1235 */           str1 = arrayOfString2[0];
/* 1236 */           str2 = arrayOfString2[1];
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 1241 */       arrayOfString1[0] = str1;
/* 1242 */       arrayOfString1[1] = str2;
/* 1243 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */     
/*      */     String getCountry() {
/* 1248 */       return this.country;
/*      */     }
/*      */     
/*      */     String getKey() {
/* 1252 */       return this.country;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1257 */       return (this.availDiff == null) ? " availDiff is null" : this.availDiff.getKey();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLSVCMODAVAILElembh1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */