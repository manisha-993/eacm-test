/*      */ package COM.ibm.eannounce.abr.util;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.eannounce.objects.SBRException;
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
/*      */ public class XMLFCTRANSAVAILElem
/*      */   extends XMLElem
/*      */ {
/*   43 */   private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
/*      */   public XMLFCTRANSAVAILElem() {
/*   45 */     super("AVAILABILITYELEMENT");
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
/*      */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*   71 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*   72 */     String str = entityItem.getEntityType();
/*   73 */     Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, paramStringBuffer);
/*   74 */     if (vector.size() > 0) {
/*      */       
/*   76 */       EntityItem entityItem1 = null;
/*   77 */       if ("MODELCONVERT".equals(str)) {
/*      */         try {
/*   79 */           EntityItem[] arrayOfEntityItem = doSearch(paramDatabase, entityItem, paramStringBuffer);
/*   80 */           if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*   81 */             entityItem1 = arrayOfEntityItem[0];
/*   82 */             ABRUtil.append(paramStringBuffer, "XMLFCTRANSAVAILElem.addElements get FROMMODEL " + entityItem1.getKey() + NEWLINE);
/*      */           } 
/*   84 */         } catch (SBRException sBRException) {
/*   85 */           throw new MiddlewareException("Exception occur when get FROMMODEL doSearch(). " + sBRException.getMessage());
/*      */         } 
/*      */       } else {
/*      */         
/*   89 */         entityItem1 = entityItem;
/*      */       } 
/*      */       
/*   92 */       boolean bool1 = false;
/*   93 */       boolean bool2 = false;
/*   94 */       bool1 = AvailUtil.iscompatmodel();
/*      */       
/*   96 */       if (!bool1) {
/*   97 */         bool2 = AvailUtil.isExistFinal(paramDatabase, paramDiffEntity, "STATUS", paramStringBuffer);
/*      */       }
/*      */       
/*  100 */       TreeMap<Object, Object> treeMap = new TreeMap<>(); byte b;
/*  101 */       for (b = 0; b < vector.size(); b++) {
/*  102 */         DiffEntity diffEntity = vector.elementAt(b);
/*  103 */         buildCtryAudRecs(treeMap, diffEntity, true, paramStringBuffer);
/*      */       } 
/*      */ 
/*      */       
/*  107 */       for (b = 0; b < vector.size(); b++) {
/*  108 */         DiffEntity diffEntity = vector.elementAt(b);
/*  109 */         buildCtryAudRecs(treeMap, diffEntity, false, paramStringBuffer);
/*      */       } 
/*      */       
/*  112 */       Collection collection = treeMap.values();
/*  113 */       Iterator<CtryAudRecord> iterator = collection.iterator();
/*  114 */       while (iterator.hasNext()) {
/*  115 */         CtryAudRecord ctryAudRecord = iterator.next();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  120 */         DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/*  121 */             .getCountry(), paramStringBuffer);
/*      */         
/*  123 */         DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  124 */             .getCountry(), paramStringBuffer);
/*  125 */         DiffEntity diffEntity3 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/*  126 */             .getCountry(), paramStringBuffer);
/*  127 */         DiffEntity diffEntity4 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRecord
/*  128 */             .getCountry(), paramStringBuffer);
/*      */         
/*  130 */         ctryAudRecord.setAllFields(diffEntity1, diffEntity2, diffEntity3, diffEntity4, paramHashtable, entityItem1, paramDiffEntity, bool2, bool1, paramStringBuffer);
/*      */         
/*  132 */         if (ctryAudRecord.isDisplayable() || ctryAudRecord.isrfrDisplayable()) {
/*  133 */           createNodeSet(paramHashtable, paramDocument, paramElement, entityItem1, ctryAudRecord, paramStringBuffer);
/*      */         } else {
/*  135 */           ABRUtil.append(paramStringBuffer, "XMLAVAILElem.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*      */         } 
/*  137 */         ctryAudRecord.dereference();
/*      */       } 
/*      */ 
/*      */       
/*  141 */       treeMap.clear();
/*  142 */       Vector vector1 = (Vector)paramHashtable.get("ANNOUNCEMENT");
/*  143 */       Vector vector2 = (Vector)paramHashtable.get("AVAIL");
/*  144 */       if (vector1 != null) {
/*  145 */         vector1.clear();
/*      */       }
/*  147 */       if (vector2 != null) {
/*  148 */         vector2.clear();
/*      */       }
/*      */     } else {
/*  151 */       ABRUtil.append(paramStringBuffer, "XMLAVAILElem.addElements no planned AVAILs found" + NEWLINE);
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
/*      */   protected EntityItem[] doSearch(Database paramDatabase, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  166 */     String str1 = "SRDMODEL4";
/*  167 */     Vector<String> vector1 = new Vector(1);
/*  168 */     Vector<String> vector2 = new Vector(1);
/*  169 */     String str2 = "MODEL";
/*      */ 
/*      */ 
/*      */     
/*  173 */     vector1.addElement("FROMMACHTYPE");
/*  174 */     vector2.addElement("MACHTYPEATR");
/*  175 */     vector1.addElement("FROMMODEL");
/*  176 */     vector2.addElement("MODELATR");
/*      */     
/*  178 */     PDGUtility pDGUtility = new PDGUtility();
/*      */     
/*  180 */     StringBuffer stringBuffer = new StringBuffer();
/*  181 */     for (byte b = 0; b < vector1.size(); b++) {
/*  182 */       if (stringBuffer.length() > 0) {
/*  183 */         stringBuffer.append(";");
/*      */       }
/*  185 */       String str3 = vector1.elementAt(b).toString();
/*  186 */       String str4 = vector2.elementAt(b).toString();
/*  187 */       stringBuffer.append("map_" + str4 + "=" + PokUtils.getAttributeValue(paramEntityItem, str3, ", ", "", false));
/*      */     } 
/*      */     
/*  190 */     ABRUtil.append(paramStringBuffer, "XMLFCTRANSAVAILElem.doSearch Using " + str1 + " to search for " + str2 + " using " + stringBuffer.toString() + NEWLINE);
/*      */     
/*  192 */     EntityItem[] arrayOfEntityItem = null;
/*  193 */     arrayOfEntityItem = pDGUtility.dynaSearch(paramDatabase, paramEntityItem.getProfile(), null, str1, str2, stringBuffer.toString());
/*      */     
/*  195 */     return arrayOfEntityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeSet(Hashtable paramHashtable, Document paramDocument, Element paramElement, EntityItem paramEntityItem, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/*  201 */     if (paramCtryAudRecord.isDisplayable()) {
/*  202 */       Element element1 = paramDocument.createElement(this.nodeName);
/*  203 */       addXMLAttrs(element1);
/*  204 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  207 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  208 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/*  209 */       element1.appendChild(element2);
/*  210 */       element2 = paramDocument.createElement("STATUS");
/*  211 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/*  212 */       element1.appendChild(element2);
/*  213 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  214 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  215 */       element1.appendChild(element2);
/*  216 */       element2 = paramDocument.createElement("ANNDATE");
/*  217 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnndate()));
/*  218 */       element1.appendChild(element2);
/*  219 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  220 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnnnumber()));
/*  221 */       element1.appendChild(element2);
/*  222 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  223 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getFirstorder()));
/*  224 */       element1.appendChild(element2);
/*  225 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  226 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/*  227 */       element1.appendChild(element2);
/*  228 */       element2 = paramDocument.createElement("PUBFROM");
/*  229 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/*  230 */       element1.appendChild(element2);
/*  231 */       element2 = paramDocument.createElement("PUBTO");
/*  232 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/*  233 */       element1.appendChild(element2);
/*  234 */       element2 = paramDocument.createElement("WDANNDATE");
/*  235 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getWdanndate()));
/*  236 */       element1.appendChild(element2);
/*  237 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  238 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreomannnum()));
/*  239 */       element1.appendChild(element2);
/*  240 */       element2 = paramDocument.createElement("LASTORDER");
/*  241 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getLastorder()));
/*  242 */       element1.appendChild(element2);
/*  243 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  244 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosanndate()));
/*  245 */       element1.appendChild(element2);
/*  246 */       element2 = paramDocument.createElement("ENDOFSERVICEANNNUMBER");
/*  247 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosannnum()));
/*  248 */       element1.appendChild(element2);
/*  249 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  250 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/*  251 */       element1.appendChild(element2);
/*      */       
/*  253 */       if (paramEntityItem != null && "MODEL".equals(paramEntityItem.getEntityType())) {
/*  254 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramEntityItem, paramCtryAudRecord.availDiff, "D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */       } else {
/*  256 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramEntityItem, paramCtryAudRecord.availDiff, "D:FEATURETRNAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */       } 
/*      */     } 
/*  259 */     if (paramCtryAudRecord.isrfrDisplayable()) {
/*  260 */       Element element1 = paramDocument.createElement(this.nodeName);
/*  261 */       addXMLAttrs(element1);
/*  262 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  265 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  266 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfraction()));
/*  267 */       element1.appendChild(element2);
/*  268 */       element2 = paramDocument.createElement("STATUS");
/*  269 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfravailStatus()));
/*  270 */       element1.appendChild(element2);
/*  271 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  272 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  273 */       element1.appendChild(element2);
/*  274 */       element2 = paramDocument.createElement("ANNDATE");
/*  275 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfranndate()));
/*  276 */       element1.appendChild(element2);
/*  277 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  278 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrannnumber()));
/*  279 */       element1.appendChild(element2);
/*  280 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  281 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrfirstorder()));
/*  282 */       element1.appendChild(element2);
/*  283 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  284 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrplannedavailability()));
/*  285 */       element1.appendChild(element2);
/*  286 */       element2 = paramDocument.createElement("PUBFROM");
/*  287 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubfrom()));
/*  288 */       element1.appendChild(element2);
/*  289 */       element2 = paramDocument.createElement("PUBTO");
/*  290 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubto()));
/*  291 */       element1.appendChild(element2);
/*  292 */       element2 = paramDocument.createElement("WDANNDATE");
/*  293 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrwdanndate()));
/*  294 */       element1.appendChild(element2);
/*  295 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  296 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreomannnum()));
/*  297 */       element1.appendChild(element2);
/*  298 */       element2 = paramDocument.createElement("LASTORDER");
/*  299 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrlastorder()));
/*  300 */       element1.appendChild(element2);
/*  301 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  302 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosanndate()));
/*  303 */       element1.appendChild(element2);
/*  304 */       element2 = paramDocument.createElement("ENDOFSERVICEANNNUMBER");
/*  305 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosannnum()));
/*  306 */       element1.appendChild(element2);
/*  307 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  308 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrendofservice()));
/*  309 */       element1.appendChild(element2);
/*      */       
/*  311 */       if ("MODEL".equals(paramEntityItem.getEntityType())) {
/*  312 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramEntityItem, paramCtryAudRecord.availDiff, "D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */       } else {
/*  314 */         SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramEntityItem, paramCtryAudRecord.availDiff, "D:FEATURETRNAVAIL:D:AVAIL:D:AVAILSLEORGA:D", paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
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
/*  339 */     ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs build T1 country list " + paramBoolean + " " + paramDiffEntity.getKey() + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  345 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  346 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  347 */     if (paramBoolean) {
/*  348 */       if (!paramDiffEntity.isNew()) {
/*      */         
/*  350 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  351 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for deleted / update avail at T1: ctryAtt " + 
/*  352 */             PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/*  353 */         if (eANFlagAttribute != null) {
/*  354 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  355 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/*  357 */             if (arrayOfMetaFlag[b].isSelected()) {
/*  358 */               String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  359 */               String str2 = str1;
/*  360 */               if (paramTreeMap.containsKey(str2)) {
/*      */                 
/*  362 */                 CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/*  363 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted / update " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */               } else {
/*      */                 
/*  366 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/*  367 */                 ctryAudRecord.setAction("Delete");
/*  368 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  375 */     } else if (!paramDiffEntity.isDeleted()) {
/*      */       
/*  377 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  378 */       ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for new /update avail:  ctryAtt and anncodeAtt " + 
/*  379 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/*  380 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/*  381 */       if (eANFlagAttribute != null) {
/*  382 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  383 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  385 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  386 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/*  387 */             String str2 = str1;
/*  388 */             if (paramTreeMap.containsKey(str2)) {
/*  389 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  390 */               if ("Delete".equals(ctryAudRecord.action)) {
/*  391 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new /udpate" + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */                 
/*  393 */                 ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*  394 */                 ctryAudRecord.setAction("@@");
/*      */               } 
/*      */             } else {
/*  397 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/*  398 */               ctryAudRecord.setAction("Update");
/*  399 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  400 */               ABRUtil.append(paramStringBuffer, "XMLAVAILElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  401 */                   .getKey() + NEWLINE);
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
/*      */   private Vector getPlannedAvails(Hashtable paramHashtable, StringBuffer paramStringBuffer) {
/*  414 */     Vector<DiffEntity> vector1 = new Vector(1);
/*  415 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/*      */     
/*  417 */     ABRUtil.append(paramStringBuffer, "XMLAVAILElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/*  418 */         .size())) + NEWLINE);
/*  419 */     if (vector2 == null) {
/*  420 */       return vector1;
/*      */     }
/*      */ 
/*      */     
/*  424 */     for (byte b = 0; b < vector2.size(); b++) {
/*  425 */       DiffEntity diffEntity = vector2.elementAt(b);
/*  426 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/*  427 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*  428 */       if (diffEntity.isDeleted()) {
/*  429 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/*  430 */             PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/*  431 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/*  432 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  433 */           vector1.add(diffEntity);
/*      */         }
/*      */       } else {
/*  436 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/*  437 */             PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/*  438 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/*  439 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  440 */           vector1.add(diffEntity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  445 */     return vector1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DiffEntity getEntityForAttrs(Hashtable paramHashtable, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, StringBuffer paramStringBuffer) {
/*  455 */     DiffEntity diffEntity = null;
/*  456 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get(paramString1);
/*      */     
/*  458 */     ABRUtil.append(paramStringBuffer, "XMLAVAILElem.getEntityForAttrs looking for " + paramString2 + ":" + paramString3 + " and " + paramString4 + ":" + paramString5 + " in " + paramString1 + " allVct.size:" + ((vector == null) ? "null" : ("" + vector
/*  459 */         .size())) + NEWLINE);
/*  460 */     if (vector == null) {
/*  461 */       return diffEntity;
/*      */     }
/*      */     
/*  464 */     for (byte b = 0; b < vector.size(); b++) {
/*  465 */       DiffEntity diffEntity1 = vector.elementAt(b);
/*  466 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/*  467 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/*  468 */       if (diffEntity1.isDeleted()) {
/*  469 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  470 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/*  471 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/*  472 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/*  473 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  474 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/*  475 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  476 */             diffEntity = diffEntity1;
/*      */           }
/*      */         }
/*      */       
/*      */       }
/*  481 */       else if (diffEntity1.isNew()) {
/*  482 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  483 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/*  484 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/*  485 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/*  486 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  487 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/*  488 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  489 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } else {
/*  495 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.getEntityForAttrs checking[" + b + "]: current " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  496 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/*  497 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/*  498 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/*  499 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  500 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/*  501 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  502 */             diffEntity = diffEntity1;
/*      */             break;
/*      */           } 
/*      */         } 
/*  506 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  507 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/*  508 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/*  509 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/*  510 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  511 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/*  512 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  513 */             diffEntity = diffEntity1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  521 */     return diffEntity;
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
/*  535 */       super((String)null);
/*  536 */       this.country = param1String;
/*  537 */       this.availDiff = param1DiffEntity;
/*      */     }
/*      */     
/*      */     void setUpdateAvail(DiffEntity param1DiffEntity) {
/*  541 */       this.availDiff = param1DiffEntity;
/*  542 */       setAction("Update");
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
/*      */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, Hashtable param1Hashtable, EntityItem param1EntityItem, DiffEntity param1DiffEntity5, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/*  573 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for availDiff " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + NEWLINE);
/*      */       
/*  575 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for COFCATentity " + ((param1EntityItem == null) ? "null " : param1EntityItem.getKey()) + NEWLINE);
/*      */ 
/*      */       
/*  578 */       this.availStatus = "0020";
/*  579 */       this.rfravailStatus = "0040";
/*      */ 
/*      */ 
/*      */       
/*  583 */       String[] arrayOfString1 = deriveAnnDate(false, param1DiffEntity5, param1StringBuffer);
/*  584 */       String[] arrayOfString2 = deriveAnnDate(true, param1DiffEntity5, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/*  588 */       String[] arrayOfString3 = deriveAnnNumber(false, param1StringBuffer);
/*  589 */       String[] arrayOfString4 = deriveAnnNumber(true, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/*  593 */       String[] arrayOfString5 = deriveFIRSTORDER(false, param1DiffEntity5, param1DiffEntity1, param1StringBuffer);
/*  594 */       String[] arrayOfString6 = deriveFIRSTORDER(true, param1DiffEntity5, param1DiffEntity1, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/*  598 */       String[] arrayOfString7 = derivePLANNEDAVAILABILITY(false, param1DiffEntity5, param1StringBuffer);
/*  599 */       String[] arrayOfString8 = derivePLANNEDAVAILABILITY(true, param1DiffEntity5, param1StringBuffer);
/*      */ 
/*      */       
/*  602 */       String[] arrayOfString9 = derivePubFrom(false, param1DiffEntity1, param1DiffEntity5, param1StringBuffer);
/*  603 */       String[] arrayOfString10 = derivePubFrom(true, param1DiffEntity1, param1DiffEntity5, param1StringBuffer);
/*      */ 
/*      */       
/*  606 */       String[] arrayOfString11 = derivePubTo(false, param1DiffEntity5, param1DiffEntity2, param1StringBuffer);
/*  607 */       String[] arrayOfString12 = derivePubTo(true, param1DiffEntity5, param1DiffEntity2, param1StringBuffer);
/*      */ 
/*      */       
/*  610 */       String[] arrayOfString13 = deriveWDANNDATE(false, param1DiffEntity5, param1DiffEntity2, param1DiffEntity4, param1StringBuffer);
/*  611 */       String[] arrayOfString14 = deriveWDANNDATE(true, param1DiffEntity5, param1DiffEntity2, param1DiffEntity4, param1StringBuffer);
/*      */ 
/*      */       
/*  614 */       String[] arrayOfString15 = deriveENDOFMARKETANNNUMBER(param1DiffEntity5, param1DiffEntity2, param1DiffEntity4, false, param1StringBuffer);
/*  615 */       String[] arrayOfString16 = deriveENDOFMARKETANNNUMBER(param1DiffEntity5, param1DiffEntity2, param1DiffEntity4, false, param1StringBuffer);
/*      */ 
/*      */       
/*  618 */       String[] arrayOfString17 = deriveLastOrder(param1DiffEntity5, param1DiffEntity2, false, param1StringBuffer);
/*  619 */       String[] arrayOfString18 = deriveLastOrder(param1DiffEntity5, param1DiffEntity2, true, param1StringBuffer);
/*      */ 
/*      */       
/*  622 */       String[] arrayOfString19 = deriveEOSANNDATE(false, param1DiffEntity5, param1DiffEntity3, param1StringBuffer);
/*  623 */       String[] arrayOfString20 = deriveEOSANNDATE(true, param1DiffEntity5, param1DiffEntity3, param1StringBuffer);
/*      */ 
/*      */ 
/*      */       
/*  627 */       String[] arrayOfString21 = deriveENDOFSERVICE(false, param1DiffEntity5, param1DiffEntity3, param1StringBuffer);
/*  628 */       String[] arrayOfString22 = deriveENDOFSERVICE(true, param1DiffEntity5, param1DiffEntity3, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  633 */       String[] arrayOfString23 = deriveENDOFSERVICEANNNUMBER(param1DiffEntity5, param1DiffEntity3, false, param1StringBuffer);
/*  634 */       String[] arrayOfString24 = deriveENDOFSERVICEANNNUMBER(param1DiffEntity5, param1DiffEntity3, true, param1StringBuffer);
/*  635 */       handleResults(arrayOfString1, arrayOfString2, arrayOfString3, arrayOfString4, arrayOfString5, arrayOfString6, arrayOfString7, arrayOfString8, arrayOfString9, arrayOfString10, arrayOfString11, arrayOfString12, arrayOfString13, arrayOfString14, arrayOfString15, arrayOfString16, arrayOfString17, arrayOfString18, arrayOfString21, arrayOfString22, arrayOfString19, arrayOfString20, arrayOfString23, arrayOfString24, this.country, param1Boolean1, param1Boolean2, param1StringBuffer);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFSERVICEANNNUMBER(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  643 */       String str1 = "@@";
/*  644 */       String str2 = "@@";
/*  645 */       String[] arrayOfString1 = new String[2];
/*  646 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveENDOFSERVICEANNNUMBER endAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/*  648 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  650 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/*  653 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/*  654 */         str1 = arrayOfString2[0];
/*  655 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  658 */       arrayOfString1[0] = str1;
/*  659 */       arrayOfString1[1] = str2;
/*  660 */       return arrayOfString1;
/*      */     }
/*      */     private String[] deriveENDOFMARKETANNNUMBER(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  663 */       String str1 = "@@";
/*  664 */       String str2 = "@@";
/*  665 */       String[] arrayOfString1 = new String[2];
/*  666 */       String[] arrayOfString2 = new String[2];
/*  667 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveENDOFMARKETANNNUMBER lastOrderAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/*  669 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  671 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/*  672 */         str1 = arrayOfString2[0];
/*  673 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  676 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  678 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/*  679 */         str1 = arrayOfString2[0];
/*  680 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  683 */       arrayOfString1[0] = str1;
/*  684 */       arrayOfString1[1] = str2;
/*  685 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] derivePubTo(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, StringBuffer param1StringBuffer) {
/*  696 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePubTo  loAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + " findT1:" + param1Boolean + NEWLINE);
/*      */       
/*  698 */       String str1 = "@@";
/*  699 */       String str2 = "@@";
/*  700 */       String[] arrayOfString1 = new String[2];
/*  701 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  703 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  705 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  706 */         str1 = arrayOfString2[0];
/*  707 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  710 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  712 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "WTHDRWEFFCTVDATE", param1StringBuffer);
/*  713 */         str1 = arrayOfString2[0];
/*  714 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  717 */       arrayOfString1[0] = str1;
/*  718 */       arrayOfString1[1] = str2;
/*  719 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] derivePubFrom(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, StringBuffer param1StringBuffer) {
/*  729 */       String str1 = "@@";
/*  730 */       String str2 = "@@";
/*  731 */       String[] arrayOfString1 = new String[2];
/*  732 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  734 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  736 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  737 */         str1 = arrayOfString2[0];
/*  738 */         str2 = arrayOfString2[1];
/*      */       } 
/*  740 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/*  743 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/*  744 */         str1 = arrayOfString2[0];
/*  745 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  748 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  750 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  751 */         str1 = arrayOfString2[0];
/*  752 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  755 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  757 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, "ANNDATE", param1StringBuffer);
/*  758 */         str1 = arrayOfString2[0];
/*  759 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  762 */       arrayOfString1[0] = str1;
/*  763 */       arrayOfString1[1] = str2;
/*  764 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnNumber(boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  774 */       String str1 = "@@";
/*  775 */       String str2 = "@@";
/*  776 */       String[] arrayOfString1 = new String[2];
/*  777 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  779 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  781 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/*  782 */         str1 = arrayOfString2[0];
/*  783 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  786 */       arrayOfString1[0] = str1;
/*  787 */       arrayOfString1[1] = str2;
/*  788 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnDate(boolean param1Boolean, DiffEntity param1DiffEntity, StringBuffer param1StringBuffer) {
/*  797 */       String str1 = "@@";
/*  798 */       String str2 = "@@";
/*  799 */       String[] arrayOfString1 = new String[2];
/*      */       
/*  801 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  803 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*  804 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/*  805 */         str1 = arrayOfString2[0];
/*  806 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  809 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  811 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity, str1, str2, "ANNDATE", param1StringBuffer);
/*  812 */         str1 = arrayOfString2[0];
/*  813 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  816 */       arrayOfString1[0] = str1;
/*  817 */       arrayOfString1[1] = str2;
/*  818 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveFIRSTORDER(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, StringBuffer param1StringBuffer) {
/*  828 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveFIRSTORDER availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + " foAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/*  829 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*  830 */       String str1 = "@@";
/*  831 */       String str2 = "@@";
/*  832 */       String[] arrayOfString1 = new String[2];
/*  833 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  835 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  837 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  838 */         str1 = arrayOfString2[0];
/*  839 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  842 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  844 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/*  845 */         str1 = arrayOfString2[0];
/*  846 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  849 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  851 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  852 */         str1 = arrayOfString2[0];
/*  853 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  856 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  858 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "ANNDATE", param1StringBuffer);
/*  859 */         str1 = arrayOfString2[0];
/*  860 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  863 */       arrayOfString1[0] = str1;
/*  864 */       arrayOfString1[1] = str2;
/*  865 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] derivePLANNEDAVAILABILITY(boolean param1Boolean, DiffEntity param1DiffEntity, StringBuffer param1StringBuffer) {
/*  873 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.derivePLANNEDAVAILABILITY availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff
/*  874 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*  875 */       String str1 = "@@";
/*  876 */       String str2 = "@@";
/*  877 */       String[] arrayOfString1 = new String[2];
/*  878 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  880 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  882 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  883 */         str1 = arrayOfString2[0];
/*  884 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  887 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  889 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity, str1, str2, "GENAVAILDATE", param1StringBuffer);
/*  890 */         str1 = arrayOfString2[0];
/*  891 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  894 */       arrayOfString1[0] = str1;
/*  895 */       arrayOfString1[1] = str2;
/*  896 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveEOSANNDATE(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, StringBuffer param1StringBuffer) {
/*  905 */       String str1 = "@@";
/*  906 */       String str2 = "@@";
/*  907 */       String[] arrayOfString1 = new String[2];
/*  908 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  910 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/*  913 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity2, str1, str2, this.country, "13", param1StringBuffer);
/*  914 */         str1 = arrayOfString2[0];
/*  915 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  918 */       arrayOfString1[0] = str1;
/*  919 */       arrayOfString1[1] = str2;
/*  920 */       return arrayOfString1;
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
/*      */     private String[] deriveWDANNDATE(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, StringBuffer param1StringBuffer) {
/*  936 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveWDANNDATE lastOrderAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/*  938 */       String str1 = "@@";
/*  939 */       String str2 = "@@";
/*  940 */       String[] arrayOfString1 = new String[2];
/*  941 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  943 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  945 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity2, str1, str2, this.country, "14", param1StringBuffer);
/*  946 */         str1 = arrayOfString2[0];
/*  947 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  950 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  952 */         arrayOfString2 = AvailUtil.getAvailAnnDateByAnntype(param1Boolean, param1DiffEntity3, str1, str2, this.country, "14", param1StringBuffer);
/*  953 */         str1 = arrayOfString2[0];
/*  954 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  957 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  959 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "WITHDRAWDATE", param1StringBuffer);
/*  960 */         str1 = arrayOfString2[0];
/*  961 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/*  964 */       arrayOfString1[0] = str1;
/*  965 */       arrayOfString1[1] = str2;
/*  966 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFSERVICE(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, StringBuffer param1StringBuffer) {
/*  976 */       String str1 = "@@";
/*  977 */       String str2 = "@@";
/*  978 */       String[] arrayOfString1 = new String[2];
/*  979 */       String[] arrayOfString2 = new String[2];
/*      */       
/*  981 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/*  983 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/*  984 */         str1 = arrayOfString2[0];
/*  985 */         str2 = arrayOfString2[1];
/*      */       } 
/*  987 */       arrayOfString1[0] = str1;
/*  988 */       arrayOfString1[1] = str2;
/*  989 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLastOrder(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  999 */       String str1 = "@@";
/* 1000 */       String str2 = "@@";
/* 1001 */       String[] arrayOfString1 = new String[2];
/* 1002 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 1004 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1006 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 1007 */         str1 = arrayOfString2[0];
/* 1008 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1011 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 1013 */         arrayOfString2 = AvailUtil.getParentAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, "WTHDRWEFFCTVDATE", param1StringBuffer);
/* 1014 */         str1 = arrayOfString2[0];
/* 1015 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 1018 */       arrayOfString1[0] = str1;
/* 1019 */       arrayOfString1[1] = str2;
/* 1020 */       return arrayOfString1;
/*      */     }
/*      */     
/*      */     String getCountry() {
/* 1024 */       return this.country;
/*      */     }
/*      */     
/*      */     String getKey() {
/* 1028 */       return this.country;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1033 */       return (this.availDiff != null) ? (this.availDiff.getKey() + " action:" + this.action) : "There is no AVAIL! ";
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLFCTRANSAVAILElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */