/*      */ package COM.ibm.eannounce.abr.util;
/*      */ 
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.opicmpdh.middleware.D;
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
/*      */ public class XMLTMFAVAILElem
/*      */   extends XMLElem {
/*   36 */   private String PRODSTRUCT = "PRODSTRUCT";
/*      */   
/*   38 */   private static XMLSLEORGGRPElem SLEORGGRP = new XMLSLEORGGRPElem();
/*      */   public XMLTMFAVAILElem() {
/*   40 */     super("AVAILABILITYELEMENT");
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
/*   63 */     D.ebug(0, "Working on the item:" + this.nodeName);
/*   64 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   72 */     boolean bool1 = false;
/*   73 */     boolean bool2 = false;
/*   74 */     boolean bool3 = false;
/*   75 */     String str1 = "";
/*   76 */     String str2 = "MODELAVAIL";
/*   77 */     String str3 = "1980-01-01-00.00.00.000000";
/*   78 */     boolean bool4 = true;
/*      */ 
/*      */     
/*   81 */     boolean bool5 = paramDiffEntity.getEntityType().equals(this.PRODSTRUCT) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   90 */     if (bool5) {
/*   91 */       str1 = "OOFAVAIL";
/*      */     } else {
/*   93 */       str1 = "SWPRODSTRUCTAVAIL";
/*      */     } 
/*   95 */     boolean bool6 = false;
/*   96 */     boolean bool7 = false;
/*   97 */     bool6 = AvailUtil.iscompatmodel();
/*      */     
/*   99 */     if (!bool6) {
/*      */       
/*  101 */       String str = null;
/*  102 */       str = (String)paramHashtable.get("_chSTATUS");
/*  103 */       ABRUtil.append(paramStringBuffer, "the status is" + str + NEWLINE);
/*  104 */       if ("0020".equals(str)) {
/*  105 */         bool7 = true;
/*      */       } else {
/*  107 */         bool7 = false;
/*      */       } 
/*      */     } 
/*      */     
/*  111 */     Vector<DiffEntity> vector = getAvailOfAvailType(paramHashtable, str1, paramStringBuffer);
/*  112 */     Vector vector1 = getAvailOfAvailType(paramHashtable, str2, paramStringBuffer);
/*  113 */     EntityItem entityItem = paramDiffEntity.getPriorEntityItem();
/*      */     
/*  115 */     if (entityItem != null && str3.equals(entityItem.getProfile().getValOn())) {
/*  116 */       bool4 = false;
/*      */     }
/*      */     
/*  119 */     if (bool4) {
/*  120 */       String str = getCtryAVAL(paramHashtable, paramDiffEntity, true, vector, paramStringBuffer);
/*  121 */       if (str == null) {
/*  122 */         bool3 = isDerivefromMODEL(paramHashtable, vector1, true, paramStringBuffer);
/*  123 */         if (bool3) {
/*      */           
/*  125 */           if (bool5) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  132 */             Vector[] arrayOfVector = getFeatureCtryVector(paramHashtable, paramDiffEntity, paramStringBuffer);
/*  133 */             Vector<DiffEntity> vector4 = getPlannedAvails(paramHashtable, vector1, paramStringBuffer);
/*  134 */             for (byte b = 0; b < vector4.size(); b++) {
/*  135 */               DiffEntity diffEntity = vector4.elementAt(b);
/*  136 */               buildCtryAudRecs(treeMap, diffEntity, arrayOfVector, true, paramStringBuffer);
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  142 */             Vector<DiffEntity> vector4 = getPlannedAvails(paramHashtable, vector1, paramStringBuffer);
/*      */ 
/*      */             
/*  145 */             for (byte b = 0; b < vector4.size(); b++) {
/*  146 */               DiffEntity diffEntity = vector4.elementAt(b);
/*  147 */               buildCtryAudRecs(treeMap, diffEntity, (Vector[])null, true, paramStringBuffer);
/*      */             }
/*      */           
/*      */           } 
/*  151 */         } else if (bool5) {
/*      */           
/*  153 */           buildFeatureCtryAudRecs(paramHashtable, paramDiffEntity, treeMap, true, paramStringBuffer);
/*      */         }
/*      */         else {
/*      */           
/*  157 */           buildWWCtryAudRecs(paramHashtable, paramDiffEntity, treeMap, paramDatabase, true, paramStringBuffer);
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  166 */         buildBHCatlgorRecs(paramHashtable, treeMap, true, paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  176 */         Vector<DiffEntity> vector4 = new Vector();
/*  177 */         if ("146".equals(str)) {
/*  178 */           vector4 = vector;
/*      */         } else {
/*  180 */           vector4 = vector;
/*      */         } 
/*  182 */         for (byte b = 0; b < vector4.size(); b++) {
/*  183 */           DiffEntity diffEntity = vector4.elementAt(b);
/*  184 */           buildCtryAudRecs(treeMap, diffEntity, (Vector[])null, true, paramStringBuffer);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  190 */     String str4 = getCtryAVAL(paramHashtable, paramDiffEntity, false, vector, paramStringBuffer);
/*      */     
/*  192 */     if (str4 == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  216 */       bool3 = isDerivefromMODEL(paramHashtable, vector1, false, paramStringBuffer);
/*  217 */       if (bool3) {
/*      */         
/*  219 */         if (bool5) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  226 */           Vector[] arrayOfVector = getFeatureCtryVector(paramHashtable, paramDiffEntity, paramStringBuffer);
/*  227 */           Vector<DiffEntity> vector4 = getPlannedAvails(paramHashtable, vector1, paramStringBuffer);
/*  228 */           for (byte b = 0; b < vector4.size(); b++) {
/*  229 */             DiffEntity diffEntity = vector4.elementAt(b);
/*  230 */             buildCtryAudRecs(treeMap, diffEntity, arrayOfVector, false, paramStringBuffer);
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  236 */           Vector<DiffEntity> vector4 = getPlannedAvails(paramHashtable, vector1, paramStringBuffer);
/*      */ 
/*      */           
/*  239 */           for (byte b = 0; b < vector4.size(); b++) {
/*  240 */             DiffEntity diffEntity = vector4.elementAt(b);
/*  241 */             buildCtryAudRecs(treeMap, diffEntity, (Vector[])null, false, paramStringBuffer);
/*      */           }
/*      */         
/*      */         } 
/*  245 */       } else if (bool5) {
/*      */         
/*  247 */         buildFeatureCtryAudRecs(paramHashtable, paramDiffEntity, treeMap, false, paramStringBuffer);
/*  248 */         bool1 = true;
/*      */       }
/*      */       else {
/*      */         
/*  252 */         buildWWCtryAudRecs(paramHashtable, paramDiffEntity, treeMap, paramDatabase, false, paramStringBuffer);
/*  253 */         bool2 = true;
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  261 */       buildBHCatlgorRecs(paramHashtable, treeMap, false, paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  267 */       Vector<DiffEntity> vector4 = new Vector();
/*  268 */       if ("146".equals(str4)) {
/*  269 */         vector4 = vector;
/*      */       } else {
/*  271 */         vector4 = vector;
/*      */       } 
/*  273 */       for (byte b = 0; b < vector4.size(); b++) {
/*  274 */         DiffEntity diffEntity = vector4.elementAt(b);
/*  275 */         buildCtryAudRecs(treeMap, diffEntity, (Vector[])null, false, paramStringBuffer);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  280 */     Collection collection = treeMap.values();
/*  281 */     Iterator<CtryAudRecord> iterator = collection.iterator();
/*      */     
/*  283 */     Hashtable hashtable = null;
/*  284 */     if (iterator.hasNext() && (bool1 || bool2)) {
/*  285 */       hashtable = searchSLORGNPLNTCODE(paramDatabase, paramDiffEntity.getCurrentEntityItem(), paramStringBuffer);
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
/*  298 */     while (iterator.hasNext()) {
/*  299 */       CtryAudRecord ctryAudRecord = iterator.next();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  327 */       DiffEntity diffEntity1 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "146", "COUNTRYLIST", ctryAudRecord
/*  328 */           .getCountry(), true, paramStringBuffer);
/*  329 */       DiffEntity diffEntity2 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "146", "COUNTRYLIST", ctryAudRecord
/*  330 */           .getCountry(), false, paramStringBuffer);
/*      */       
/*  332 */       DiffEntity diffEntity3 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/*  333 */           .getCountry(), false, paramStringBuffer);
/*      */       
/*  335 */       DiffEntity diffEntity4 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  336 */           .getCountry(), false, paramStringBuffer);
/*      */       
/*  338 */       DiffEntity diffEntity5 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/*  339 */           .getCountry(), false, paramStringBuffer);
/*      */       
/*  341 */       DiffEntity diffEntity6 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector, "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRecord
/*  342 */           .getCountry(), false, paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  354 */       DiffEntity diffEntity7 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector1, "AVAILTYPE", "146", "COUNTRYLIST", ctryAudRecord
/*  355 */           .getCountry(), false, paramStringBuffer);
/*      */       
/*  357 */       DiffEntity diffEntity8 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector1, "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  358 */           .getCountry(), false, paramStringBuffer);
/*      */       
/*  360 */       DiffEntity diffEntity9 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector1, "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/*  361 */           .getCountry(), false, paramStringBuffer);
/*      */       
/*  363 */       DiffEntity diffEntity10 = AvailUtil.getEntityForAttrs(paramHashtable, "AVAIL", vector1, "AVAILTYPE", "200", "COUNTRYLIST", ctryAudRecord
/*  364 */           .getCountry(), false, paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  370 */       Vector[] arrayOfVector = getFeatureCtryVector(paramHashtable, paramDiffEntity, paramStringBuffer);
/*      */       
/*  372 */       if (arrayOfVector == null) {
/*  373 */         arrayOfVector = new Vector[2];
/*      */       }
/*      */ 
/*      */       
/*  377 */       DiffEntity diffEntity11 = getCatlgor(paramHashtable, ctryAudRecord.getCountry(), paramStringBuffer);
/*      */       
/*  379 */       DiffEntity[] arrayOfDiffEntity = new DiffEntity[2];
/*  380 */       arrayOfDiffEntity[0] = diffEntity1;
/*  381 */       arrayOfDiffEntity[1] = diffEntity2;
/*      */ 
/*      */       
/*  384 */       ctryAudRecord.setAllFields(arrayOfDiffEntity, arrayOfVector, diffEntity11, paramDiffEntity, diffEntity3, diffEntity4, diffEntity5, diffEntity6, diffEntity8, diffEntity9, diffEntity10, diffEntity7, paramHashtable, bool7, bool6, paramStringBuffer);
/*      */       
/*  386 */       if (ctryAudRecord.isDisplayable() || ctryAudRecord.isrfrDisplayable()) {
/*  387 */         if (bool1 || bool2) {
/*  388 */           if (hashtable == null) {
/*  389 */             hashtable = searchSLORGNPLNTCODE(paramDatabase, paramDiffEntity.getCurrentEntityItem(), paramStringBuffer);
/*      */           }
/*  391 */           createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity, ctryAudRecord, hashtable, paramStringBuffer);
/*      */         }
/*  393 */         else if (isPlannedAvail(ctryAudRecord.availDiff)) {
/*  394 */           createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity, ctryAudRecord, paramStringBuffer);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  399 */           if (hashtable == null) {
/*  400 */             hashtable = searchSLORGNPLNTCODE(paramDatabase, paramDiffEntity.getCurrentEntityItem(), paramStringBuffer);
/*      */           }
/*  402 */           createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity, ctryAudRecord, hashtable, paramStringBuffer);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  407 */         ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*      */       } 
/*  409 */       ctryAudRecord.dereference();
/*      */     } 
/*      */ 
/*      */     
/*  413 */     if (hashtable != null) {
/*  414 */       hashtable.clear();
/*      */     }
/*  416 */     treeMap.clear();
/*  417 */     Vector vector2 = (Vector)paramHashtable.get("ANNOUNCEMENT");
/*  418 */     Vector vector3 = (Vector)paramHashtable.get("AVAIL");
/*  419 */     if (vector2 != null) {
/*  420 */       vector2.clear();
/*      */     }
/*  422 */     if (vector3 != null)
/*  423 */       vector3.clear(); 
/*      */   }
/*      */   
/*      */   private Hashtable searchSLORGNPLNTCODE(Database paramDatabase, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException {
/*  427 */     XMLSLEORGNPLNTCODESearchElem xMLSLEORGNPLNTCODESearchElem = new XMLSLEORGNPLNTCODESearchElem();
/*  428 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     try {
/*  430 */       hashtable = xMLSLEORGNPLNTCODESearchElem.doSearch(paramDatabase, paramEntityItem, paramStringBuffer);
/*  431 */     } catch (MiddlewareShutdownInProgressException middlewareShutdownInProgressException) {
/*  432 */       middlewareShutdownInProgressException.printStackTrace();
/*  433 */     } catch (SBRException sBRException) {
/*  434 */       StringWriter stringWriter = new StringWriter();
/*  435 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  436 */       ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.addElements search!! SBRException: " + stringWriter.getBuffer().toString() + NEWLINE);
/*  437 */       sBRException.printStackTrace();
/*      */     } 
/*  439 */     ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.addElements search!! " + NEWLINE);
/*      */     
/*  441 */     return hashtable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isPlannedAvail(DiffEntity paramDiffEntity) {
/*  449 */     boolean bool = false;
/*  450 */     if (paramDiffEntity == null) {
/*  451 */       return false;
/*      */     }
/*  453 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  454 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  455 */     if (paramDiffEntity.isDeleted()) {
/*  456 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/*  457 */       if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  458 */         bool = true;
/*      */       }
/*      */     } else {
/*  461 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/*  462 */       if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  463 */         bool = true;
/*      */       }
/*      */     } 
/*      */     
/*  467 */     return bool;
/*      */   }
/*      */ 
/*      */   
/*      */   private void buildWWCtryAudRecs(Hashtable paramHashtable, DiffEntity paramDiffEntity, TreeMap<String, CtryAudRecord> paramTreeMap, Database paramDatabase, boolean paramBoolean, StringBuffer paramStringBuffer) throws MiddlewareException, SQLException {
/*  472 */     ReturnDataResultSet returnDataResultSet = null;
/*  473 */     ResultSet resultSet = null;
/*  474 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/*  475 */     String str = "SG";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  481 */     ABRUtil.append(paramStringBuffer, "buildWWCtryAudRecs at " + (paramBoolean ? "T1" : "T2") + " Time." + NEWLINE);
/*      */     
/*  483 */     if (paramDiffEntity != null) {
/*  484 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*  485 */       str = entityItem.getProfile().getEnterprise();
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
/*      */     try {
/*  521 */       resultSet = paramDatabase.callGBL9999A(returnStatus, str, "GENAREASELECTION", "1999", "COUNTRYLIST");
/*  522 */       returnDataResultSet = new ReturnDataResultSet(resultSet);
/*      */     } finally {
/*  524 */       if (resultSet != null) {
/*  525 */         resultSet.close();
/*  526 */         resultSet = null;
/*      */       } 
/*  528 */       paramDatabase.commit();
/*  529 */       paramDatabase.freeStatement();
/*  530 */       paramDatabase.isPending();
/*      */     } 
/*  532 */     if (paramBoolean) {
/*  533 */       for (byte b = 0; b < returnDataResultSet.size(); b++) {
/*  534 */         String str1 = returnDataResultSet.getColumn(b, 0).trim();
/*  535 */         ABRUtil.append(paramStringBuffer, "CQ BHALM109267 derivefrommodel world wide counry " + str1 + NEWLINE);
/*      */         
/*  537 */         String str2 = str1;
/*  538 */         if (!paramTreeMap.containsKey(str2)) {
/*  539 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str1);
/*      */ 
/*      */ 
/*      */           
/*  543 */           ctryAudRecord.action = "Delete";
/*  544 */           paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  549 */       for (byte b = 0; b < returnDataResultSet.size(); b++) {
/*  550 */         String str1 = returnDataResultSet.getColumn(b, 0).trim();
/*  551 */         ABRUtil.append(paramStringBuffer, "CQ BHALM109267 derivefrommodel world wide counry " + str1 + NEWLINE);
/*      */         
/*  553 */         String str2 = str1;
/*  554 */         if (!paramTreeMap.containsKey(str2)) {
/*  555 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str1);
/*      */ 
/*      */ 
/*      */           
/*  559 */           ctryAudRecord.action = "Update";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  567 */           paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*      */         } else {
/*      */           
/*  570 */           CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  571 */           ctryAudRecord.setUpdateAvail((DiffEntity)null);
/*  572 */           ctryAudRecord.setAction("@@");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  580 */           ABRUtil.append(paramStringBuffer, "WARNING buildWWCtryAudRecs country" + str2 + "already exists, overwrite it. " + NEWLINE);
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
/*      */   private void buildFeatureCtryAudRecs(Hashtable paramHashtable, DiffEntity paramDiffEntity, TreeMap<String, CtryAudRecord> paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) throws MiddlewareException {
/*  594 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("FEATURE");
/*  595 */     ABRUtil.append(paramStringBuffer, "buildT1FeatureCtryAudRecs at " + (paramBoolean ? "T1" : "T2") + " Time." + NEWLINE);
/*  596 */     if (paramBoolean) {
/*  597 */       for (byte b = 0; b < vector.size(); b++) {
/*  598 */         DiffEntity diffEntity = vector.elementAt(b);
/*  599 */         EntityItem entityItem = diffEntity.getPriorEntityItem();
/*  600 */         if (!diffEntity.isNew()) {
/*      */ 
/*      */           
/*  603 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  604 */           ABRUtil.append(paramStringBuffer, "buildFeatureCtryAudRecs for deleted/udpate feature: ctryAtt " + 
/*  605 */               PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + NEWLINE);
/*  606 */           if (eANFlagAttribute != null) {
/*  607 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  608 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */               
/*  610 */               if (arrayOfMetaFlag[b1].isSelected()) {
/*  611 */                 String str1 = arrayOfMetaFlag[b1].getFlagCode();
/*  612 */                 String str2 = str1;
/*  613 */                 if (paramTreeMap.containsKey(str2)) {
/*      */                   
/*  615 */                   CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/*  616 */                   ABRUtil.append(paramStringBuffer, "WARNING buildFeatureCtryAudRecs for deleted/udpate " + diffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */                 } else {
/*      */                   
/*  619 */                   CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str1);
/*  620 */                   ctryAudRecord.setAction("Delete");
/*  621 */                   paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  622 */                   ABRUtil.append(paramStringBuffer, "buildFeatureCtryAudRecs for deleted/udpate:" + diffEntity.getKey() + " rec: " + ctryAudRecord
/*  623 */                       .getKey() + NEWLINE);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*  631 */       for (byte b = 0; b < vector.size(); b++) {
/*  632 */         DiffEntity diffEntity = vector.elementAt(b);
/*  633 */         EntityItem entityItem = diffEntity.getCurrentEntityItem();
/*  634 */         if (!diffEntity.isDeleted()) {
/*      */ 
/*      */           
/*  637 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  638 */           ABRUtil.append(paramStringBuffer, "buildFeatureCtryAudRecs for new /udpated feature:  ctryAtt " + 
/*  639 */               PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + NEWLINE);
/*  640 */           if (eANFlagAttribute != null) {
/*  641 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  642 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */               
/*  644 */               if (arrayOfMetaFlag[b1].isSelected()) {
/*  645 */                 String str1 = arrayOfMetaFlag[b1].getFlagCode();
/*  646 */                 String str2 = str1;
/*  647 */                 if (paramTreeMap.containsKey(str2)) {
/*  648 */                   CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  649 */                   ABRUtil.append(paramStringBuffer, "WARNING buildFeatureCtryAudRecs for new /updated " + diffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */                   
/*  651 */                   ctryAudRecord.setUpdateAvail((DiffEntity)null);
/*  652 */                   ctryAudRecord.setAction("@@");
/*      */                 } else {
/*  654 */                   CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str1);
/*  655 */                   ctryAudRecord.setAction("Update");
/*  656 */                   paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  657 */                   ABRUtil.append(paramStringBuffer, "buildFeatureCtryAudRecs for new/ updated:" + diffEntity.getKey() + " rec: " + ctryAudRecord
/*  658 */                       .getKey() + NEWLINE);
/*      */                 } 
/*      */               } 
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
/*      */ 
/*      */   
/*      */   private Vector[] getFeatureCtryVector(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws MiddlewareException {
/*  676 */     Vector<String> vector1 = new Vector(1);
/*  677 */     Vector<String> vector2 = new Vector(1);
/*  678 */     Vector[] arrayOfVector = new Vector[2];
/*  679 */     arrayOfVector[0] = vector2;
/*  680 */     arrayOfVector[1] = vector1;
/*  681 */     String str = "";
/*  682 */     if ("PRODSTRUCT".equals(paramDiffEntity.getEntityType())) {
/*  683 */       str = "FEATURE";
/*  684 */     } else if ("SWPRODSTRUCT".equals(paramDiffEntity.getEntityType())) {
/*  685 */       return null;
/*      */     } 
/*  687 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get(str);
/*      */     
/*  689 */     for (byte b = 0; b < vector.size(); b++) {
/*  690 */       DiffEntity diffEntity = vector.elementAt(b);
/*  691 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/*  692 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*  693 */       if (entityItem2 != null) {
/*      */ 
/*      */         
/*  696 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  697 */         ABRUtil.append(paramStringBuffer, "putFeatureCtryVector for deleted feature: ctryAtt " + 
/*  698 */             PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/*  699 */         if (eANFlagAttribute != null) {
/*  700 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  701 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */             
/*  703 */             if (arrayOfMetaFlag[b1].isSelected()) {
/*  704 */               String str1 = arrayOfMetaFlag[b1].getFlagCode();
/*  705 */               String str2 = str1;
/*  706 */               if (!vector2.contains(str2)) {
/*  707 */                 vector2.add(str2);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  713 */       if (entityItem1 != null) {
/*      */ 
/*      */         
/*  716 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  717 */         ABRUtil.append(paramStringBuffer, "putFeatureCtryVector for new feature:  ctryAtt " + 
/*  718 */             PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/*  719 */         if (eANFlagAttribute != null) {
/*  720 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  721 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */             
/*  723 */             if (arrayOfMetaFlag[b1].isSelected()) {
/*  724 */               String str1 = arrayOfMetaFlag[b1].getFlagCode();
/*  725 */               String str2 = str1;
/*  726 */               if (!vector1.contains(str2)) {
/*  727 */                 vector1.add(str2);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  734 */     return arrayOfVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeSet(Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/*  742 */     if (paramCtryAudRecord.isDisplayable()) {
/*  743 */       Element element1 = paramDocument.createElement(this.nodeName);
/*      */       
/*  745 */       addXMLAttrs(element1);
/*  746 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  749 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  750 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/*  751 */       element1.appendChild(element2);
/*  752 */       element2 = paramDocument.createElement("STATUS");
/*  753 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/*  754 */       element1.appendChild(element2);
/*  755 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  756 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  757 */       element1.appendChild(element2);
/*  758 */       element2 = paramDocument.createElement("ANNDATE");
/*  759 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnndate()));
/*  760 */       element1.appendChild(element2);
/*  761 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  762 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnnnumber()));
/*  763 */       element1.appendChild(element2);
/*  764 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  765 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getFirstorder()));
/*  766 */       element1.appendChild(element2);
/*  767 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  768 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/*  769 */       element1.appendChild(element2);
/*  770 */       element2 = paramDocument.createElement("PUBFROM");
/*  771 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/*  772 */       element1.appendChild(element2);
/*  773 */       element2 = paramDocument.createElement("PUBTO");
/*  774 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/*  775 */       element1.appendChild(element2);
/*  776 */       element2 = paramDocument.createElement("WDANNDATE");
/*  777 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getWdanndate()));
/*  778 */       element1.appendChild(element2);
/*      */       
/*  780 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  781 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEomannnum()));
/*  782 */       element1.appendChild(element2);
/*  783 */       element2 = paramDocument.createElement("LASTORDER");
/*  784 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getLastorder()));
/*  785 */       element1.appendChild(element2);
/*  786 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  787 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosanndate()));
/*  788 */       element1.appendChild(element2);
/*      */       
/*  790 */       element2 = paramDocument.createElement("ENDOFSERVICEANNNUMBER");
/*  791 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosannnum()));
/*  792 */       element1.appendChild(element2);
/*  793 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  794 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/*  795 */       element1.appendChild(element2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  803 */       SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramDiffEntity.getCurrentEntityItem(), paramCtryAudRecord.availDiff, null, paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */     } 
/*      */     
/*  806 */     if (paramCtryAudRecord.isrfrDisplayable()) {
/*  807 */       Element element1 = paramDocument.createElement(this.nodeName);
/*      */       
/*  809 */       addXMLAttrs(element1);
/*  810 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  813 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  814 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfraction()));
/*  815 */       element1.appendChild(element2);
/*  816 */       element2 = paramDocument.createElement("STATUS");
/*  817 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfravailStatus()));
/*  818 */       element1.appendChild(element2);
/*  819 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  820 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  821 */       element1.appendChild(element2);
/*  822 */       element2 = paramDocument.createElement("ANNDATE");
/*  823 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfranndate()));
/*  824 */       element1.appendChild(element2);
/*  825 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  826 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrannnumber()));
/*  827 */       element1.appendChild(element2);
/*  828 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  829 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrfirstorder()));
/*  830 */       element1.appendChild(element2);
/*  831 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  832 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrplannedavailability()));
/*  833 */       element1.appendChild(element2);
/*  834 */       element2 = paramDocument.createElement("PUBFROM");
/*  835 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubfrom()));
/*  836 */       element1.appendChild(element2);
/*  837 */       element2 = paramDocument.createElement("PUBTO");
/*  838 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubto()));
/*  839 */       element1.appendChild(element2);
/*  840 */       element2 = paramDocument.createElement("WDANNDATE");
/*  841 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrwdanndate()));
/*  842 */       element1.appendChild(element2);
/*      */       
/*  844 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  845 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEomannnum()));
/*  846 */       element1.appendChild(element2);
/*  847 */       element2 = paramDocument.createElement("LASTORDER");
/*  848 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrlastorder()));
/*  849 */       element1.appendChild(element2);
/*  850 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  851 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosanndate()));
/*  852 */       element1.appendChild(element2);
/*      */       
/*  854 */       element2 = paramDocument.createElement("ENDOFSERVICEANNNUMBER");
/*  855 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosannnum()));
/*  856 */       element1.appendChild(element2);
/*  857 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  858 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrendofservice()));
/*  859 */       element1.appendChild(element2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  867 */       SLEORGGRP.displayAVAILSLEORG(paramHashtable, paramDocument, element1, paramDiffEntity.getCurrentEntityItem(), paramCtryAudRecord.availDiff, null, paramCtryAudRecord.country, paramCtryAudRecord.action, paramStringBuffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeSet(Hashtable paramHashtable1, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, CtryAudRecord paramCtryAudRecord, Hashtable paramHashtable2, StringBuffer paramStringBuffer) {
/*  878 */     if (paramCtryAudRecord.isDisplayable()) {
/*  879 */       Element element1 = paramDocument.createElement(this.nodeName);
/*      */       
/*  881 */       addXMLAttrs(element1);
/*  882 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  885 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  886 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/*  887 */       element1.appendChild(element2);
/*  888 */       element2 = paramDocument.createElement("STATUS");
/*  889 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/*  890 */       element1.appendChild(element2);
/*  891 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  892 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  893 */       element1.appendChild(element2);
/*  894 */       element2 = paramDocument.createElement("ANNDATE");
/*  895 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnndate()));
/*  896 */       element1.appendChild(element2);
/*  897 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  898 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAnnnumber()));
/*  899 */       element1.appendChild(element2);
/*  900 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  901 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getFirstorder()));
/*  902 */       element1.appendChild(element2);
/*  903 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  904 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/*  905 */       element1.appendChild(element2);
/*  906 */       element2 = paramDocument.createElement("PUBFROM");
/*  907 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/*  908 */       element1.appendChild(element2);
/*  909 */       element2 = paramDocument.createElement("PUBTO");
/*  910 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/*  911 */       element1.appendChild(element2);
/*  912 */       element2 = paramDocument.createElement("WDANNDATE");
/*  913 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getWdanndate()));
/*  914 */       element1.appendChild(element2);
/*      */       
/*  916 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  917 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEomannnum()));
/*  918 */       element1.appendChild(element2);
/*  919 */       element2 = paramDocument.createElement("LASTORDER");
/*  920 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getLastorder()));
/*  921 */       element1.appendChild(element2);
/*  922 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  923 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosanndate()));
/*  924 */       element1.appendChild(element2);
/*      */       
/*  926 */       element2 = paramDocument.createElement("ENDOFSERVICEANNNUMBER");
/*  927 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosannnum()));
/*  928 */       element1.appendChild(element2);
/*  929 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  930 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/*  931 */       element1.appendChild(element2);
/*      */       
/*  933 */       createWorldWideAvilNote(paramDocument, paramCtryAudRecord, paramHashtable2, element1, paramStringBuffer);
/*      */     } 
/*  935 */     if (paramCtryAudRecord.isrfrDisplayable()) {
/*  936 */       Element element1 = paramDocument.createElement(this.nodeName);
/*      */       
/*  938 */       addXMLAttrs(element1);
/*  939 */       paramElement.appendChild(element1);
/*      */ 
/*      */       
/*  942 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/*  943 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfraction()));
/*  944 */       element1.appendChild(element2);
/*  945 */       element2 = paramDocument.createElement("STATUS");
/*  946 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfravailStatus()));
/*  947 */       element1.appendChild(element2);
/*  948 */       element2 = paramDocument.createElement("COUNTRY_FC");
/*  949 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/*  950 */       element1.appendChild(element2);
/*  951 */       element2 = paramDocument.createElement("ANNDATE");
/*  952 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfranndate()));
/*  953 */       element1.appendChild(element2);
/*  954 */       element2 = paramDocument.createElement("ANNNUMBER");
/*  955 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrannnumber()));
/*  956 */       element1.appendChild(element2);
/*  957 */       element2 = paramDocument.createElement("FIRSTORDER");
/*  958 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrfirstorder()));
/*  959 */       element1.appendChild(element2);
/*  960 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/*  961 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrplannedavailability()));
/*  962 */       element1.appendChild(element2);
/*  963 */       element2 = paramDocument.createElement("PUBFROM");
/*  964 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubfrom()));
/*  965 */       element1.appendChild(element2);
/*  966 */       element2 = paramDocument.createElement("PUBTO");
/*  967 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrpubto()));
/*  968 */       element1.appendChild(element2);
/*  969 */       element2 = paramDocument.createElement("WDANNDATE");
/*  970 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrwdanndate()));
/*  971 */       element1.appendChild(element2);
/*      */       
/*  973 */       element2 = paramDocument.createElement("ENDOFMARKETANNNUMBER");
/*  974 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEomannnum()));
/*  975 */       element1.appendChild(element2);
/*  976 */       element2 = paramDocument.createElement("LASTORDER");
/*  977 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrlastorder()));
/*  978 */       element1.appendChild(element2);
/*  979 */       element2 = paramDocument.createElement("EOSANNDATE");
/*  980 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfreosanndate()));
/*  981 */       element1.appendChild(element2);
/*      */       
/*  983 */       element2 = paramDocument.createElement("ENDOFSERVICEANNNUMBER");
/*  984 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEosannnum()));
/*  985 */       element1.appendChild(element2);
/*  986 */       element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/*  987 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrendofservice()));
/*  988 */       element1.appendChild(element2);
/*      */       
/*  990 */       createWorldWideAvilNote(paramDocument, paramCtryAudRecord, paramHashtable2, element1, paramStringBuffer);
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
/*      */   private void createWorldWideAvilNote(Document paramDocument, CtryAudRecord paramCtryAudRecord, Hashtable paramHashtable, Element paramElement, StringBuffer paramStringBuffer) throws DOMException {
/* 1005 */     String str = paramCtryAudRecord.getCountry();
/* 1006 */     Object object = paramHashtable.get(str);
/* 1007 */     if (object != null && "Update".equals(paramCtryAudRecord.action)) {
/*      */       
/* 1009 */       Element element1 = paramDocument.createElement("SLEORGGRPLIST");
/* 1010 */       paramElement.appendChild(element1);
/* 1011 */       Vector[] arrayOfVector = (Vector[])paramHashtable.get(str);
/* 1012 */       Hashtable hashtable = arrayOfVector[0].get(0);
/* 1013 */       Enumeration<String> enumeration = hashtable.keys();
/* 1014 */       Vector<String> vector1 = new Vector();
/* 1015 */       while (enumeration.hasMoreElements()) {
/* 1016 */         String str1 = enumeration.nextElement();
/* 1017 */         str1 = str1.trim();
/* 1018 */         if (vector1.contains(str1)) {
/*      */           continue;
/*      */         }
/* 1021 */         vector1.add(str1);
/* 1022 */         Element element4 = paramDocument.createElement("SLEORGGRPELEMENT");
/* 1023 */         element1.appendChild(element4);
/* 1024 */         Element element3 = paramDocument.createElement("SLEOORGGRPACTION");
/* 1025 */         element3.appendChild(paramDocument.createTextNode("Update"));
/* 1026 */         element4.appendChild(element3);
/* 1027 */         element3 = paramDocument.createElement("SLEORGGRP");
/* 1028 */         element3.appendChild(paramDocument.createTextNode("" + str1));
/* 1029 */         element4.appendChild(element3);
/*      */       } 
/* 1031 */       vector1.clear();
/*      */       
/* 1033 */       Element element2 = paramDocument.createElement("SLEORGNPLNTCODELIST");
/* 1034 */       paramElement.appendChild(element2);
/* 1035 */       Vector<EntityItem> vector = arrayOfVector[1];
/* 1036 */       Vector<String> vector2 = new Vector();
/* 1037 */       for (byte b = 0; b < vector.size(); b++) {
/* 1038 */         EntityItem entityItem = vector.get(b);
/* 1039 */         String str1 = PokUtils.getAttributeValue(entityItem, "SLEORG", ", ", "", false);
/* 1040 */         String str2 = PokUtils.getAttributeValue(entityItem, "PLNTCD", ", ", "", false);
/* 1041 */         String str3 = str1 + str2;
/* 1042 */         if (!vector2.contains(str3)) {
/*      */ 
/*      */           
/* 1045 */           vector2.add(str3);
/* 1046 */           Element element4 = paramDocument.createElement("SLEORGNPLNTCODEELEMENT");
/* 1047 */           element2.appendChild(element4);
/* 1048 */           Element element3 = paramDocument.createElement("SLEORGNPLNTCODEACTION");
/* 1049 */           element3.appendChild(paramDocument.createTextNode("Update"));
/* 1050 */           element4.appendChild(element3);
/* 1051 */           element3 = paramDocument.createElement("SLEORG");
/* 1052 */           element3.appendChild(paramDocument.createTextNode("" + str1));
/* 1053 */           element4.appendChild(element3);
/* 1054 */           element3 = paramDocument.createElement("PLNTCD");
/* 1055 */           element3.appendChild(paramDocument.createTextNode("" + str2));
/* 1056 */           element4.appendChild(element3);
/*      */         } 
/* 1058 */       }  vector2.clear();
/*      */     } else {
/*      */       
/* 1061 */       Element element1 = paramDocument.createElement("SLEORGGRPLIST");
/* 1062 */       paramElement.appendChild(element1);
/*      */       
/* 1064 */       Element element2 = paramDocument.createElement("SLEORGNPLNTCODELIST");
/* 1065 */       paramElement.appendChild(element2);
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
/*      */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, Vector[] paramArrayOfVector, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 1133 */     ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.buildCtryAudRecs " + (paramBoolean ? "T1 " : "T2 ") + paramDiffEntity.getKey() + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1141 */     if (paramBoolean) {
/* 1142 */       EntityItem entityItem = paramDiffEntity.getPriorEntityItem();
/* 1143 */       if (!paramDiffEntity.isNew()) {
/*      */         
/* 1145 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1146 */         ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.buildT1CtryAudRecs for delete/udpate avail: ctryAtt " + 
/* 1147 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + NEWLINE);
/* 1148 */         if (eANFlagAttribute != null) {
/* 1149 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1150 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/* 1152 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 1153 */               String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 1154 */               String str2 = str1;
/* 1155 */               if (paramTreeMap.containsKey(str2)) {
/*      */                 
/* 1157 */                 CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/* 1158 */                 ABRUtil.append(paramStringBuffer, "WARNING buildT1CtryAudRecs for delete/udpate" + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */               
/*      */               }
/* 1161 */               else if (paramArrayOfVector != null && paramArrayOfVector[0].contains(str2)) {
/* 1162 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 1163 */                 ctryAudRecord.setAction("Delete");
/* 1164 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 1165 */                 ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.buildT1CtryAudRecs for delete/udpate:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 1166 */                     .getKey() + NEWLINE);
/* 1167 */               } else if (paramArrayOfVector == null) {
/* 1168 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 1169 */                 ctryAudRecord.setAction("Delete");
/* 1170 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 1171 */                 ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.buildT1CtryAudRecs for delete/udpate:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 1172 */                     .getKey() + NEWLINE);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1180 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 1181 */       if (!paramDiffEntity.isDeleted()) {
/*      */         
/* 1183 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1184 */         ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.buildCtryAudRecs for new/udpate avail:  ctryAtt and anncodeAtt " + 
/* 1185 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + 
/* 1186 */             PokUtils.getAttributeFlagValue(entityItem, "ANNCODENAME") + NEWLINE);
/* 1187 */         if (eANFlagAttribute != null) {
/* 1188 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1189 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/* 1191 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 1192 */               String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 1193 */               String str2 = str1;
/* 1194 */               if (paramTreeMap.containsKey(str2)) {
/* 1195 */                 CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/* 1196 */                 if (isPlannedAvail(paramDiffEntity) || "Delete".equals(ctryAudRecord.action)) {
/* 1197 */                   ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new/update " + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */                   
/* 1199 */                   if ("Update".equals(ctryAudRecord.action)) {
/* 1200 */                     ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*      */                   } else {
/* 1202 */                     ctryAudRecord.setUpdateAvail(paramDiffEntity);
/* 1203 */                     ctryAudRecord.setAction("@@");
/*      */                   }
/*      */                 
/*      */                 } 
/* 1207 */               } else if (paramArrayOfVector != null && paramArrayOfVector[1].contains(str2)) {
/* 1208 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 1209 */                 ctryAudRecord.setAction("Update");
/* 1210 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 1211 */                 ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.buildCtryAudRecs for new/update:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 1212 */                     .getKey() + NEWLINE);
/* 1213 */               } else if (paramArrayOfVector == null) {
/* 1214 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 1215 */                 ctryAudRecord.setAction("Update");
/* 1216 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 1217 */                 ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.buildCtryAudRecs for new/update:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 1218 */                     .getKey() + NEWLINE);
/*      */               } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildBHCatlgorRecs(Hashtable paramHashtable, TreeMap<String, CtryAudRecord> paramTreeMap, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 1239 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("BHCATLGOR");
/* 1240 */     String str = "COUNTRYLIST";
/* 1241 */     ABRUtil.append(paramStringBuffer, "buildBHCatlgorRecs looking for T1" + paramBoolean + " in CATLGOR allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 1242 */         .size())) + NEWLINE);
/* 1243 */     if (vector != null) {
/* 1244 */       if (paramBoolean)
/* 1245 */       { for (byte b = 0; b < vector.size(); b++) {
/* 1246 */           DiffEntity diffEntity = vector.elementAt(b);
/* 1247 */           EntityItem entityItem = diffEntity.getPriorEntityItem();
/*      */           
/* 1249 */           if (!diffEntity.isNew()) {
/* 1250 */             String str1 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 1251 */             if ("0020".equals(str1)) {
/*      */ 
/*      */               
/* 1254 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(str);
/* 1255 */               if (eANFlagAttribute != null) {
/* 1256 */                 MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1257 */                 for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */                   
/* 1259 */                   if (arrayOfMetaFlag[b1].isSelected()) {
/* 1260 */                     String str2 = arrayOfMetaFlag[b1].getFlagCode();
/* 1261 */                     String str3 = str2;
/* 1262 */                     if (paramTreeMap.containsKey(str3)) {
/*      */                       
/* 1264 */                       CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str3);
/* 1265 */                       ABRUtil.append(paramStringBuffer, "WARNING buildBHCatlgorRecs for delete/udpate" + str3 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */                     } else {
/*      */                       
/* 1268 */                       CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str2);
/* 1269 */                       ctryAudRecord.setAction("Delete");
/* 1270 */                       paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 1271 */                       ABRUtil.append(paramStringBuffer, "buildBHCatlgorRecs for delete/udpate rec: " + ctryAudRecord
/* 1272 */                           .getKey() + NEWLINE);
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }  }
/* 1280 */       else { for (byte b = 0; b < vector.size(); b++) {
/* 1281 */           DiffEntity diffEntity = vector.elementAt(b);
/* 1282 */           EntityItem entityItem = diffEntity.getCurrentEntityItem();
/*      */           
/* 1284 */           if (!diffEntity.isDeleted()) {
/* 1285 */             String str1 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 1286 */             if ("0020".equals(str1)) {
/*      */ 
/*      */               
/* 1289 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(str);
/* 1290 */               if (eANFlagAttribute != null) {
/* 1291 */                 MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1292 */                 for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */                   
/* 1294 */                   if (arrayOfMetaFlag[b1].isSelected()) {
/* 1295 */                     String str2 = arrayOfMetaFlag[b1].getFlagCode();
/* 1296 */                     String str3 = str2;
/* 1297 */                     if (paramTreeMap.containsKey(str3)) {
/* 1298 */                       CtryAudRecord ctryAudRecord = paramTreeMap.get(str3);
/* 1299 */                       ctryAudRecord.setUpdateAvail((DiffEntity)null);
/* 1300 */                       ctryAudRecord.setAction("@@");
/* 1301 */                       ABRUtil.append(paramStringBuffer, "WARNING buildBHCatlgorRecs for new/update" + str3 + " already exists, replace orig with Update action" + ctryAudRecord + NEWLINE);
/*      */                     } else {
/*      */                       
/* 1304 */                       CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str2);
/* 1305 */                       ctryAudRecord.setAction("Update");
/* 1306 */                       paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 1307 */                       ABRUtil.append(paramStringBuffer, "buildBHCatlgorRecs for new/udpate rec: " + ctryAudRecord
/* 1308 */                           .getKey() + NEWLINE);
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
/*      */   private Vector getPlannedAvails(Hashtable paramHashtable, Vector<DiffEntity> paramVector, StringBuffer paramStringBuffer) {
/* 1323 */     Vector<DiffEntity> vector = new Vector(1);
/* 1324 */     ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((paramVector == null) ? "null" : ("" + paramVector
/* 1325 */         .size())) + NEWLINE);
/* 1326 */     if (paramVector == null) {
/* 1327 */       return vector;
/*      */     }
/*      */ 
/*      */     
/* 1331 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1332 */       DiffEntity diffEntity = paramVector.elementAt(b);
/* 1333 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 1334 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/* 1335 */       if (diffEntity.isDeleted()) {
/* 1336 */         ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/* 1337 */             PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/* 1338 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/* 1339 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1340 */           vector.add(diffEntity);
/*      */         }
/*      */       } else {
/* 1343 */         ABRUtil.append(paramStringBuffer, "XMLTMFAVAILElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/* 1344 */             PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/* 1345 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/* 1346 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1347 */           vector.add(diffEntity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1352 */     return vector;
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
/*      */   private String getCtryAVAL(Hashtable paramHashtable, DiffEntity paramDiffEntity, boolean paramBoolean, Vector<DiffEntity> paramVector, StringBuffer paramStringBuffer) {
/* 1515 */     String str1 = null;
/* 1516 */     String str2 = null;
/* 1517 */     DiffEntity diffEntity = null;
/* 1518 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("MODEL");
/* 1519 */     if (vector != null) {
/* 1520 */       diffEntity = vector.elementAt(0);
/*      */     }
/* 1522 */     if (isOldData(paramHashtable, diffEntity, paramVector, paramStringBuffer, paramBoolean)) {
/* 1523 */       ABRUtil.append(paramStringBuffer, "getCtryAVAL isOldData return null, skip to step 3.");
/* 1524 */       return null;
/*      */     } 
/*      */     
/* 1527 */     ABRUtil.append(paramStringBuffer, "getCtryAVAL looking for AVAILTYPE: 146,149 and 151 " + (paramBoolean ? "T1" : "T2") + " in AVAIL allVct.size:" + ((paramVector == null) ? "null" : ("" + paramVector
/* 1528 */         .size())) + NEWLINE);
/* 1529 */     if (paramVector != null) {
/* 1530 */       if (paramBoolean) {
/* 1531 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 1532 */           DiffEntity diffEntity1 = paramVector.elementAt(b);
/* 1533 */           EntityItem entityItem = diffEntity1.getPriorEntityItem();
/* 1534 */           if (!diffEntity1.isNew()) {
/* 1535 */             ABRUtil.append(paramStringBuffer, "getCtryAVAL.getAvails checking[" + b + "]:Delete or Update" + diffEntity1
/* 1536 */                 .getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/*      */             
/* 1538 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 1539 */             if (eANFlagAttribute != null && (eANFlagAttribute.isSelected("146") || eANFlagAttribute.isSelected("149") || eANFlagAttribute.isSelected("151"))) {
/* 1540 */               if (eANFlagAttribute.isSelected("146")) {
/* 1541 */                 str2 = "146";
/* 1542 */               } else if (eANFlagAttribute.isSelected("149")) {
/* 1543 */                 str2 = "149";
/*      */               } else {
/* 1545 */                 str2 = "151";
/*      */               } 
/* 1547 */               if (str1 == null) {
/* 1548 */                 str1 = str2;
/*      */               }
/* 1550 */               else if (str2.compareTo(str1) < 0) {
/* 1551 */                 str1 = str2;
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/* 1560 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 1561 */           DiffEntity diffEntity1 = paramVector.elementAt(b);
/* 1562 */           EntityItem entityItem = diffEntity1.getCurrentEntityItem();
/* 1563 */           if (!diffEntity1.isDeleted()) {
/* 1564 */             ABRUtil.append(paramStringBuffer, "getCtryAVAL.getAvails checking[" + b + "]:New or Update" + diffEntity1
/* 1565 */                 .getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/*      */             
/* 1567 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 1568 */             if (eANFlagAttribute != null && (eANFlagAttribute.isSelected("146") || eANFlagAttribute.isSelected("149") || eANFlagAttribute.isSelected("151"))) {
/* 1569 */               if (eANFlagAttribute.isSelected("146")) {
/* 1570 */                 str2 = "146";
/* 1571 */               } else if (eANFlagAttribute.isSelected("149")) {
/* 1572 */                 str2 = "149";
/*      */               } else {
/* 1574 */                 str2 = "151";
/*      */               } 
/* 1576 */               if (str1 == null) {
/* 1577 */                 str1 = str2;
/*      */               }
/* 1579 */               else if (str2.compareTo(str1) < 0) {
/* 1580 */                 str1 = str2;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1597 */     return str1;
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
/*      */   private boolean isOldData(Hashtable paramHashtable, DiffEntity paramDiffEntity, Vector<DiffEntity> paramVector, StringBuffer paramStringBuffer, boolean paramBoolean) {
/* 1616 */     boolean bool1 = false;
/* 1617 */     boolean bool2 = false;
/* 1618 */     String str = "2010-03-01";
/* 1619 */     if (paramBoolean) {
/* 1620 */       if (paramVector != null)
/*      */       {
/* 1622 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 1623 */           DiffEntity diffEntity = paramVector.elementAt(b);
/* 1624 */           EntityItem entityItem = diffEntity.getPriorEntityItem();
/*      */           
/* 1626 */           if (!diffEntity.isNew()) {
/* 1627 */             ABRUtil.append(paramStringBuffer, "T1 isOldData() getAvails checking[" + b + "]:New or Update" + diffEntity
/* 1628 */                 .getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/*      */             
/* 1630 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 1631 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1632 */               bool1 = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1640 */       if (paramDiffEntity != null) {
/* 1641 */         EntityItem entityItem = paramDiffEntity.getPriorEntityItem();
/* 1642 */         if (entityItem != null) {
/* 1643 */           String str1 = PokUtils.getAttributeValue(entityItem, "ANNDATE", ", ", "@@", false);
/* 1644 */           bool2 = (str1.compareTo(str) <= 0) ? true : false;
/* 1645 */           ABRUtil.append(paramStringBuffer, "T1 isOldData() get model ANNDATE" + entityItem.getKey() + " ANNDATE: " + str1 + NEWLINE);
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1650 */       if (paramVector != null)
/*      */       {
/* 1652 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 1653 */           DiffEntity diffEntity = paramVector.elementAt(b);
/* 1654 */           EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 1655 */           if (!diffEntity.isDeleted()) {
/* 1656 */             ABRUtil.append(paramStringBuffer, "T2 isOldData() getAvails checking[" + b + "]:New or Update" + diffEntity
/* 1657 */                 .getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/*      */             
/* 1659 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 1660 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1661 */               bool1 = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/* 1668 */       if (paramDiffEntity != null) {
/* 1669 */         EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 1670 */         if (entityItem != null) {
/* 1671 */           String str1 = PokUtils.getAttributeValue(entityItem, "ANNDATE", ", ", "@@", false);
/* 1672 */           bool2 = (str1.compareTo(str) <= 0) ? true : false;
/* 1673 */           ABRUtil.append(paramStringBuffer, "T2 isOldData() model ANNDATE" + entityItem.getKey() + " ANNDATE: " + str1 + NEWLINE);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1678 */     return (!bool1 && bool2);
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
/*      */   private boolean isDerivefromMODEL(Hashtable paramHashtable, Vector<DiffEntity> paramVector, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 1695 */     boolean bool = false;
/* 1696 */     ABRUtil.append(paramStringBuffer, "isDerivefromMODEL.getAvails looking for AVAILTYPE: 146 " + (paramBoolean ? "T1" : "T2") + " in AVAIL allVct.size:" + ((paramVector == null) ? "null" : ("" + paramVector
/* 1697 */         .size())) + NEWLINE);
/* 1698 */     if (paramVector != null) {
/* 1699 */       if (paramBoolean) {
/* 1700 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 1701 */           DiffEntity diffEntity = paramVector.elementAt(b);
/* 1702 */           EntityItem entityItem = diffEntity.getPriorEntityItem();
/* 1703 */           if (!diffEntity.isNew()) {
/* 1704 */             ABRUtil.append(paramStringBuffer, "DerivefromModel.getAvails checking[" + b + "]:Delete or Update" + diffEntity
/* 1705 */                 .getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/*      */             
/* 1707 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 1708 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1709 */               bool = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1716 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 1717 */           DiffEntity diffEntity = paramVector.elementAt(b);
/* 1718 */           EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 1719 */           if (!diffEntity.isDeleted()) {
/* 1720 */             ABRUtil.append(paramStringBuffer, "DerivefromModel.getAvails checking[" + b + "]:New or Update" + diffEntity
/* 1721 */                 .getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/*      */             
/* 1723 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 1724 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1725 */               bool = true;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/* 1732 */     return bool;
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
/*      */   private Vector getAvailOfAvailType(Hashtable paramHashtable, String paramString, StringBuffer paramStringBuffer) {
/* 1748 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("AVAIL");
/*      */     
/* 1750 */     Vector<DiffEntity> vector2 = new Vector(1);
/* 1751 */     if (vector1 != null) {
/* 1752 */       for (byte b = 0; b < vector1.size(); b++) {
/* 1753 */         DiffEntity diffEntity = vector1.elementAt(b);
/*      */         
/* 1755 */         ABRUtil.append(paramStringBuffer, "getAvailOfAvailType looking for AVAIL type " + paramString + " there are " + vector1.size() + " AVAILs, " + diffEntity.getKey() + NEWLINE);
/* 1756 */         if (diffEntity.isDeleted()) {
/* 1757 */           EntityItem entityItem = diffEntity.getPriorEntityItem();
/* 1758 */           if (entityItem.hasUpLinks()) {
/* 1759 */             for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 1760 */               EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b1);
/*      */               
/* 1762 */               if (entityItem1.getEntityType().equals(paramString)) {
/* 1763 */                 vector2.add(diffEntity);
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } else {
/* 1769 */           EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 1770 */           if (entityItem.hasUpLinks()) {
/* 1771 */             for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 1772 */               EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b1);
/*      */               
/* 1774 */               if (entityItem1.getEntityType().equals(paramString)) {
/* 1775 */                 vector2.add(diffEntity);
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/* 1784 */     return vector2;
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
/*      */   private DiffEntity getCatlgor(Hashtable paramHashtable, String paramString, StringBuffer paramStringBuffer) {
/* 1803 */     DiffEntity diffEntity = null;
/*      */     
/* 1805 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("BHCATLGOR");
/*      */     
/* 1807 */     String str = "COUNTRYLIST";
/* 1808 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor looking for " + str + ":" + paramString + " in CATLGOR allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 1809 */         .size())) + NEWLINE);
/* 1810 */     if (vector == null) {
/* 1811 */       return diffEntity;
/*      */     }
/*      */ 
/*      */     
/* 1815 */     for (byte b = 0; b < vector.size(); b++) {
/* 1816 */       DiffEntity diffEntity1 = vector.elementAt(b);
/* 1817 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/* 1818 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/*      */       
/* 1820 */       String str1 = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/* 1821 */       if ("0020".equals(str1))
/*      */       {
/*      */         
/* 1824 */         if (diffEntity1.isDeleted()) {
/* 1825 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + str + ":" + 
/* 1826 */               PokUtils.getAttributeFlagValue(entityItem2, str) + NEWLINE);
/* 1827 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str);
/* 1828 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString)) {
/* 1829 */             diffEntity = diffEntity1;
/*      */ 
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/* 1835 */         } else if (diffEntity1.isNew()) {
/* 1836 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: new " + diffEntity1.getKey() + " " + str + ":" + 
/* 1837 */               PokUtils.getAttributeFlagValue(entityItem1, str) + NEWLINE);
/* 1838 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str);
/* 1839 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString)) {
/* 1840 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } else {
/* 1845 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getCatlgor checking[" + b + "]: current " + diffEntity1.getKey() + " " + str + ":" + 
/* 1846 */               PokUtils.getAttributeFlagValue(entityItem1, str) + NEWLINE);
/* 1847 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str);
/* 1848 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString)) {
/* 1849 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/* 1853 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str);
/* 1854 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString)) {
/* 1855 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1863 */     return diffEntity;
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
/*      */     
/*      */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String) {
/* 1879 */       super((String)null);
/* 1880 */       this.country = param1String;
/* 1881 */       this.availDiff = param1DiffEntity;
/*      */     }
/*      */     
/*      */     void setUpdateAvail(DiffEntity param1DiffEntity) {
/* 1885 */       this.availDiff = param1DiffEntity;
/* 1886 */       setAction("Update");
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
/*      */     void setAllFields(DiffEntity[] param1ArrayOfDiffEntity, Vector[] param1ArrayOfVector, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, DiffEntity param1DiffEntity6, DiffEntity param1DiffEntity7, DiffEntity param1DiffEntity8, DiffEntity param1DiffEntity9, DiffEntity param1DiffEntity10, Hashtable param1Hashtable, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/* 1924 */       this.availStatus = "0020";
/* 1925 */       this.rfravailStatus = "0040";
/*      */       
/* 1927 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for country is belong to availDiff " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + ". OFFAVAIL Planned AVAIL is " + ((param1ArrayOfDiffEntity[1] == null) ? "null" : param1ArrayOfDiffEntity[1].getKey()) + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1932 */       String[] arrayOfString1 = deriveAnnDate(false, param1ArrayOfDiffEntity[1], param1DiffEntity2, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */       
/* 1935 */       String[] arrayOfString2 = deriveAnnDate(true, param1ArrayOfDiffEntity[0], param1DiffEntity2, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1942 */       String[] arrayOfString3 = deriveAnnNumber(false, param1ArrayOfDiffEntity[1], param1StringBuffer);
/*      */ 
/*      */       
/* 1945 */       String[] arrayOfString4 = deriveAnnNumber(true, param1ArrayOfDiffEntity[0], param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1955 */       String[] arrayOfString5 = deriveFIRSTORDER(false, param1ArrayOfDiffEntity[1], param1DiffEntity2, param1DiffEntity3, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */       
/* 1958 */       String[] arrayOfString6 = deriveFIRSTORDER(true, param1ArrayOfDiffEntity[0], param1DiffEntity2, param1DiffEntity3, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1966 */       String[] arrayOfString7 = derivePLANNEDAVAILABILITY(false, param1ArrayOfDiffEntity[1], param1DiffEntity2, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */       
/* 1969 */       String[] arrayOfString8 = derivePLANNEDAVAILABILITY(true, param1ArrayOfDiffEntity[0], param1DiffEntity2, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1976 */       String[] arrayOfString9 = derivePubFrom(false, param1ArrayOfDiffEntity[1], param1DiffEntity1, param1DiffEntity3, param1DiffEntity2, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */       
/* 1979 */       String[] arrayOfString10 = derivePubFrom(true, param1ArrayOfDiffEntity[0], param1DiffEntity1, param1DiffEntity3, param1DiffEntity2, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1984 */       String[] arrayOfString11 = derivePubTo(false, param1DiffEntity1, param1DiffEntity2, param1DiffEntity4, param1DiffEntity10, param1DiffEntity7, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */       
/* 1987 */       String[] arrayOfString12 = derivePubTo(true, param1DiffEntity1, param1DiffEntity2, param1DiffEntity4, param1DiffEntity10, param1DiffEntity7, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1993 */       String[] arrayOfString13 = deriveWDANNDATE(false, param1DiffEntity2, param1DiffEntity4, param1DiffEntity6, param1DiffEntity9, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */       
/* 1996 */       String[] arrayOfString14 = deriveWDANNDATE(true, param1DiffEntity2, param1DiffEntity4, param1DiffEntity6, param1DiffEntity9, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2001 */       String[] arrayOfString15 = deriveENDOFMARKETANNNUMBER(param1DiffEntity2, param1DiffEntity4, param1DiffEntity6, param1DiffEntity9, false, param1StringBuffer);
/* 2002 */       String[] arrayOfString16 = deriveENDOFMARKETANNNUMBER(param1DiffEntity2, param1DiffEntity4, param1DiffEntity6, param1DiffEntity9, true, param1StringBuffer);
/*      */ 
/*      */       
/* 2005 */       String[] arrayOfString17 = deriveLastOrder(false, param1DiffEntity1, param1DiffEntity2, param1DiffEntity4, param1DiffEntity7, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */       
/* 2008 */       String[] arrayOfString18 = deriveLastOrder(true, param1DiffEntity1, param1DiffEntity2, param1DiffEntity4, param1DiffEntity7, param1DiffEntity10, param1ArrayOfVector, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2014 */       String[] arrayOfString19 = deriveENDOFSERVICEDATE(false, param1DiffEntity2, param1DiffEntity5, param1DiffEntity8, param1StringBuffer);
/*      */ 
/*      */       
/* 2017 */       String[] arrayOfString20 = deriveENDOFSERVICEDATE(true, param1DiffEntity2, param1DiffEntity5, param1DiffEntity8, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2022 */       String[] arrayOfString21 = deriveEOSANNDATE(false, param1DiffEntity2, param1DiffEntity5, param1DiffEntity8, param1StringBuffer);
/*      */ 
/*      */       
/* 2025 */       String[] arrayOfString22 = deriveEOSANNDATE(true, param1DiffEntity2, param1DiffEntity5, param1DiffEntity8, param1StringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2030 */       String[] arrayOfString23 = deriveENDOFSERVICEANNNUMBER(param1DiffEntity2, param1DiffEntity5, param1DiffEntity8, false, param1StringBuffer);
/* 2031 */       String[] arrayOfString24 = deriveENDOFSERVICEANNNUMBER(param1DiffEntity2, param1DiffEntity5, param1DiffEntity8, true, param1StringBuffer);
/*      */       
/* 2033 */       handleResults(arrayOfString1, arrayOfString2, arrayOfString3, arrayOfString4, arrayOfString5, arrayOfString6, arrayOfString7, arrayOfString8, arrayOfString9, arrayOfString10, arrayOfString11, arrayOfString12, arrayOfString13, arrayOfString14, arrayOfString15, arrayOfString16, arrayOfString17, arrayOfString18, arrayOfString19, arrayOfString20, arrayOfString21, arrayOfString22, arrayOfString23, arrayOfString24, this.country, param1Boolean1, param1Boolean2, param1StringBuffer);
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
/*      */     private String[] derivePubTo(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, Vector[] param1ArrayOfVector, StringBuffer param1StringBuffer) {
/* 2093 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.derivePubTo  loAvailDiff: " + ((param1DiffEntity3 == null) ? "null" : param1DiffEntity3.getKey()) + " findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 2096 */       String str1 = "@@";
/* 2097 */       String str2 = "@@";
/* 2098 */       String[] arrayOfString1 = new String[2];
/*      */ 
/*      */       
/* 2101 */       String[] arrayOfString2 = new String[2];
/* 2102 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2104 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity2, param1DiffEntity1, str1, str2, this.country, "PUBTO", param1StringBuffer);
/* 2105 */         str1 = arrayOfString2[0];
/* 2106 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2108 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2110 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2111 */         str1 = arrayOfString2[0];
/* 2112 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2115 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */ 
/*      */         
/* 2119 */         arrayOfString2 = AvailUtil.getProdstructAttributeDate(param1Boolean, param1DiffEntity2, param1DiffEntity5, param1ArrayOfVector, str1, str2, this.country, "WTHDRWEFFCTVDATE", param1StringBuffer);
/* 2120 */         str1 = arrayOfString2[0];
/* 2121 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2124 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2137 */         arrayOfString2 = getModelFeatureSpDate(param1Boolean, param1DiffEntity2, param1DiffEntity5, param1DiffEntity4, str1, str2, param1StringBuffer);
/*      */         
/* 2139 */         str1 = arrayOfString2[0];
/* 2140 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2142 */       arrayOfString1[0] = str1;
/* 2143 */       arrayOfString1[1] = str2;
/* 2144 */       return arrayOfString1;
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
/*      */     private String[] derivePubFrom(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, Vector[] param1ArrayOfVector, StringBuffer param1StringBuffer) {
/* 2166 */       String str1 = "@@";
/* 2167 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.derivePubFrom  catlgorDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 2168 */           .getKey()) + " findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2170 */       String str2 = "@@";
/* 2171 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 2173 */       String[] arrayOfString2 = new String[2];
/* 2174 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2176 */         arrayOfString2 = AvailUtil.getBHcatlgorAttributeDate(param1Boolean, param1DiffEntity4, param1DiffEntity2, str1, str2, this.country, "PUBFROM", param1StringBuffer);
/* 2177 */         str1 = arrayOfString2[0];
/* 2178 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2180 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2182 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2183 */         str1 = arrayOfString2[0];
/* 2184 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2187 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 2190 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2191 */         str1 = arrayOfString2[0];
/* 2192 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2195 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2197 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2198 */         str1 = arrayOfString2[0];
/* 2199 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2202 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 2205 */         arrayOfString2 = AvailUtil.getProdstructAttributeDate(param1Boolean, param1DiffEntity4, param1DiffEntity5, param1ArrayOfVector, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2206 */         str1 = arrayOfString2[0];
/* 2207 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2210 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 2213 */         arrayOfString2 = AvailUtil.getModelFeatureAttributeDate(param1Boolean, param1DiffEntity4, param1DiffEntity5, param1ArrayOfVector, str1, str2, this.country, "ANNDATE", "FIRSTANNDATE", param1StringBuffer);
/* 2214 */         str1 = arrayOfString2[0];
/* 2215 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2217 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */ 
/*      */         
/* 2221 */         arrayOfString2 = getSWprodCountryDate(param1Boolean, param1DiffEntity4, param1DiffEntity5, str1, str2, this.country, param1StringBuffer);
/* 2222 */         str1 = arrayOfString2[0];
/* 2223 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */ 
/*      */       
/* 2227 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2229 */         arrayOfString2 = AvailUtil.getSwprodModelAnnDate(param1Boolean, param1DiffEntity4, str1, str2, param1StringBuffer);
/* 2230 */         str1 = arrayOfString2[0];
/* 2231 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2233 */       arrayOfString1[0] = str1;
/* 2234 */       arrayOfString1[1] = str2;
/* 2235 */       return arrayOfString1;
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
/*      */     private String[] getSWprodCountryDate(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, String param1String1, String param1String2, String param1String3, StringBuffer param1StringBuffer) {
/* 2248 */       String[] arrayOfString = new String[2];
/*      */       
/* 2250 */       EntityItem entityItem = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity2);
/*      */       
/* 2252 */       if (entityItem != null) {
/*      */         
/* 2254 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 2255 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(param1String3)) {
/* 2256 */           EntityItem entityItem1 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity1);
/* 2257 */           if (entityItem1 != null && 
/* 2258 */             param1DiffEntity1.getEntityType().equals("SWPRODSTRUCT"))
/*      */           {
/* 2260 */             if (entityItem1.hasDownLinks()) {
/* 2261 */               for (byte b = 0; b < entityItem1.getDownLinkCount(); b++) {
/* 2262 */                 EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b);
/* 2263 */                 if (entityItem2.getEntityType().equals("MODEL")) {
/* 2264 */                   String str1 = PokUtils.getAttributeValue(entityItem2, "GENAVAILDATE", ", ", "@@", false);
/* 2265 */                   String str2 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/*      */                   
/* 2267 */                   ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.derivePubFrom getting GENAVAILDATE from " + entityItem2.getKey() + " GENAVAILDATE is:" + param1String1 + NEWLINE);
/*      */                   
/* 2269 */                   if (str2 != null && str2.equals("0020") && "@@".equals(param1String1)) {
/* 2270 */                     param1String1 = str1; break;
/* 2271 */                   }  if (str2 != null && str2.equals("0040") && "@@".equals(param1String2)) {
/* 2272 */                     param1String2 = str1;
/*      */                   }
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }
/*      */         } 
/*      */       } 
/* 2282 */       arrayOfString[0] = param1String1;
/* 2283 */       arrayOfString[1] = param1String2;
/* 2284 */       return arrayOfString;
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
/*      */     private String[] deriveAnnNumber(boolean param1Boolean, DiffEntity param1DiffEntity, StringBuffer param1StringBuffer) {
/* 2297 */       String str1 = "@@";
/* 2298 */       String str2 = "@@";
/* 2299 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 2301 */       String[] arrayOfString2 = new String[2];
/* 2302 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveAnnNumber availDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 2305 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 2306 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/* 2307 */         str1 = arrayOfString2[0];
/* 2308 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2310 */       arrayOfString1[0] = str1;
/* 2311 */       arrayOfString1[1] = str2;
/* 2312 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveAnnDate(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, Vector[] param1ArrayOfVector, StringBuffer param1StringBuffer) {
/* 2323 */       String str1 = "@@";
/* 2324 */       String str2 = "@@";
/* 2325 */       String[] arrayOfString1 = new String[2];
/* 2326 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 2328 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveAnnDate availDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1.getKey()) + " plModelAvailDiff: " + ((param1DiffEntity3 == null) ? "null" : param1DiffEntity3
/* 2329 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2331 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 2332 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2333 */         str1 = arrayOfString2[0];
/* 2334 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2336 */       if ("@@".equals(str1) && "@@".equals(str2)) {
/* 2337 */         arrayOfString2 = AvailUtil.getProdstructAttributeDate(param1Boolean, param1DiffEntity2, param1DiffEntity3, param1ArrayOfVector, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2338 */         str1 = arrayOfString2[0];
/* 2339 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2341 */       if ("@@".equals(str1) && "@@".equals(str2)) {
/* 2342 */         arrayOfString2 = AvailUtil.getModelFeatureAttributeDate(param1Boolean, param1DiffEntity2, param1DiffEntity3, param1ArrayOfVector, str1, str2, this.country, "ANNDATE", "FIRSTANNDATE", param1StringBuffer);
/* 2343 */         str1 = arrayOfString2[0];
/* 2344 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2346 */       if ("@@".equals(str1) && "@@".equals(str2)) {
/* 2347 */         arrayOfString2 = AvailUtil.getSwprodModelAnnDate(param1Boolean, param1DiffEntity2, str1, str2, param1StringBuffer);
/* 2348 */         str1 = arrayOfString2[0];
/* 2349 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2352 */       arrayOfString1[0] = str1;
/* 2353 */       arrayOfString1[1] = str2;
/* 2354 */       return arrayOfString1;
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
/*      */     private String[] deriveFIRSTORDER(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, Vector[] param1ArrayOfVector, StringBuffer param1StringBuffer) {
/* 2374 */       String str1 = "@@";
/* 2375 */       String str2 = "@@";
/* 2376 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 2378 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveFIRSTORDER availDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1.getKey()) + " foAvailDiff: " + ((param1DiffEntity3 == null) ? "null" : param1DiffEntity3
/* 2379 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2384 */       String[] arrayOfString2 = new String[2];
/* 2385 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2387 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2388 */         str1 = arrayOfString2[0];
/* 2389 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2391 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2393 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2394 */         str1 = arrayOfString2[0];
/* 2395 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2397 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2399 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2400 */         str1 = arrayOfString2[0];
/* 2401 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2403 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2405 */         arrayOfString2 = AvailUtil.getProdstructAttributeDate(param1Boolean, param1DiffEntity2, param1DiffEntity4, param1ArrayOfVector, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2406 */         str1 = arrayOfString2[0];
/* 2407 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2409 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2411 */         arrayOfString2 = AvailUtil.getModelFeatureAttributeDate(param1Boolean, param1DiffEntity2, param1DiffEntity4, param1ArrayOfVector, str1, str2, this.country, "ANNDATE", "FIRSTANNDATE", param1StringBuffer);
/* 2412 */         str1 = arrayOfString2[0];
/* 2413 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2415 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2417 */         arrayOfString2 = AvailUtil.getSwprodModelAnnDate(param1Boolean, param1DiffEntity2, str1, str2, param1StringBuffer);
/* 2418 */         str1 = arrayOfString2[0];
/* 2419 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2421 */       arrayOfString1[0] = str1;
/* 2422 */       arrayOfString1[1] = str2;
/* 2423 */       return arrayOfString1;
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
/*      */     private String[] derivePLANNEDAVAILABILITY(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, Vector[] param1ArrayOfVector, StringBuffer param1StringBuffer) {
/* 2436 */       String str1 = "@@";
/* 2437 */       String str2 = "@@";
/* 2438 */       String[] arrayOfString1 = new String[2];
/* 2439 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.derivePLANNEDAVAILABILITY availDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1
/* 2440 */           .getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 2442 */       String[] arrayOfString2 = new String[2];
/* 2443 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2445 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity1, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2446 */         str1 = arrayOfString2[0];
/* 2447 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2449 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2451 */         arrayOfString2 = AvailUtil.getProdstructAttributeDate(param1Boolean, param1DiffEntity2, param1DiffEntity3, param1ArrayOfVector, str1, str2, this.country, "GENAVAILDATE", param1StringBuffer);
/* 2452 */         str1 = arrayOfString2[0];
/* 2453 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2455 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2457 */         arrayOfString2 = AvailUtil.getModelFeatureAttributeDate(param1Boolean, param1DiffEntity2, param1DiffEntity3, param1ArrayOfVector, str1, str2, this.country, "GENAVAILDATE", "GENAVAILDATE", param1StringBuffer);
/* 2458 */         str1 = arrayOfString2[0];
/* 2459 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2461 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2463 */         arrayOfString2 = getSwprodModelDate(param1Boolean, param1DiffEntity2, param1DiffEntity3, str1, str2, param1StringBuffer);
/* 2464 */         str1 = arrayOfString2[0];
/* 2465 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2467 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2469 */         arrayOfString2 = AvailUtil.getSwprodModelAnnDate(param1Boolean, param1DiffEntity2, str1, str2, param1StringBuffer);
/* 2470 */         str1 = arrayOfString2[0];
/* 2471 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2473 */       arrayOfString1[0] = str1;
/* 2474 */       arrayOfString1[1] = str2;
/* 2475 */       return arrayOfString1;
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
/*      */     public String[] getSwprodModelDate(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, String param1String1, String param1String2, StringBuffer param1StringBuffer) {
/* 2488 */       String[] arrayOfString = new String[2];
/* 2489 */       EntityItem entityItem = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity2);
/*      */       
/* 2491 */       if (entityItem != null) {
/* 2492 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 2493 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 2494 */           EntityItem entityItem1 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity1);
/* 2495 */           if (entityItem1 != null)
/*      */           {
/* 2497 */             if (entityItem1.getEntityType().equals("SWPRODSTRUCT") && 
/* 2498 */               entityItem1.hasDownLinks()) {
/* 2499 */               for (byte b = 0; b < entityItem1.getDownLinkCount(); b++) {
/* 2500 */                 EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b);
/* 2501 */                 if (entityItem2.getEntityType().equals("MODEL")) {
/* 2502 */                   String str1 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/*      */                   
/* 2504 */                   String str2 = PokUtils.getAttributeValue(entityItem2, "GENAVAILDATE", ", ", "@@", false);
/* 2505 */                   ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.derivePLANNEDAVAILABILITY getting GENAVAILDATE from " + entityItem2
/* 2506 */                       .getKey() + " GENAVAILDATE is:" + param1String1 + NEWLINE);
/*      */                   
/* 2508 */                   if (str1 != null && str1.equals("0020") && "@@".equals(param1String1)) {
/* 2509 */                     param1String1 = str2; break;
/* 2510 */                   }  if (str1 != null && str1.equals("0040") && "@@".equals(param1String2)) {
/* 2511 */                     param1String2 = str2;
/*      */                   }
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 2522 */       arrayOfString[0] = param1String1;
/* 2523 */       arrayOfString[1] = param1String2;
/* 2524 */       return arrayOfString;
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
/*      */     private String[] deriveWDANNDATE(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, Vector[] param1ArrayOfVector, StringBuffer param1StringBuffer) {
/* 2569 */       String str1 = "@@";
/* 2570 */       String str2 = "@@";
/* 2571 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 2573 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveWDANNDATE lastOrderAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 2576 */       String[] arrayOfString2 = new String[2];
/* 2577 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2579 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2580 */         str1 = arrayOfString2[0];
/* 2581 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2584 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2586 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 2587 */         str1 = arrayOfString2[0];
/* 2588 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2591 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 2594 */         arrayOfString2 = AvailUtil.getProdstructAttributeDate(param1Boolean, param1DiffEntity1, param1DiffEntity5, param1ArrayOfVector, str1, str2, this.country, "WITHDRAWDATE", param1StringBuffer);
/* 2595 */         str1 = arrayOfString2[0];
/* 2596 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2599 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2610 */         arrayOfString2 = getModelFeatureSpecialDate(param1Boolean, param1DiffEntity1, param1DiffEntity4, str1, str2, param1StringBuffer);
/*      */         
/* 2612 */         str1 = arrayOfString2[0];
/* 2613 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2616 */       arrayOfString1[0] = str1;
/* 2617 */       arrayOfString1[1] = str2;
/*      */       
/* 2619 */       return arrayOfString1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFMARKETANNNUMBER(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 2627 */       String str1 = "@@";
/* 2628 */       String str2 = "@@";
/* 2629 */       String[] arrayOfString1 = new String[2];
/* 2630 */       String[] arrayOfString2 = new String[2];
/* 2631 */       ABRUtil.append(param1StringBuffer, "XMLAVAILElem.deriveEndOfMarketAnnNumber lastOrderAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 2634 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2636 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/* 2637 */         str1 = arrayOfString2[0];
/* 2638 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2641 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2643 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/* 2644 */         str1 = arrayOfString2[0];
/* 2645 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2648 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 2651 */         arrayOfString2 = getModelRelatedEOMANNNUM(param1Boolean, param1DiffEntity1, param1DiffEntity4, str1, str2, param1StringBuffer);
/* 2652 */         str1 = arrayOfString2[0];
/* 2653 */         str2 = arrayOfString2[1];
/*      */       } 
/* 2655 */       arrayOfString1[0] = str1;
/* 2656 */       arrayOfString1[1] = str2;
/* 2657 */       return arrayOfString1;
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
/*      */     public String[] getModelRelatedEOMANNNUM(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, String param1String1, String param1String2, StringBuffer param1StringBuffer) {
/* 2672 */       String[] arrayOfString = new String[2];
/* 2673 */       String str1 = "@@";
/* 2674 */       String str2 = "@@";
/*      */       
/* 2676 */       EntityItem entityItem = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity1);
/*      */       
/* 2678 */       if (entityItem != null) {
/* 2679 */         boolean bool = false;
/* 2680 */         if (param1DiffEntity1.getEntityType().equals("PRODSTRUCT") && 
/* 2681 */           entityItem != null) {
/* 2682 */           String str = PokUtils.getAttributeFlagValue(entityItem, "ORDERCODE");
/* 2683 */           if (str != null && "5957".equals(str)) {
/* 2684 */             bool = true;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 2689 */         if (bool) {
/* 2690 */           EntityItem entityItem1 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity2);
/* 2691 */           if (entityItem1 != null) {
/*      */             
/* 2693 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 2694 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 2695 */               Vector<EntityItem> vector = entityItem1.getDownLink();
/* 2696 */               byte b = 0; if (b < vector.size()) {
/* 2697 */                 EntityItem entityItem2 = vector.elementAt(b);
/* 2698 */                 if (entityItem2.hasDownLinks() && entityItem2.getEntityType().equals("AVAILANNA")) {
/* 2699 */                   Vector<EntityItem> vector1 = entityItem2.getDownLink();
/* 2700 */                   EntityItem entityItem3 = vector1.elementAt(0);
/* 2701 */                   str1 = PokUtils.getAttributeValue(entityItem3, "ANNNUMBER", ", ", "@@", false);
/* 2702 */                   ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveEOMANNNUMBER looking for downlink of AVAILANNA : Announcement ANNNUMBER :" + str1 + NEWLINE);
/*      */ 
/*      */                   
/* 2705 */                   if (!"@@".equals(str1)) {
/* 2706 */                     str2 = PokUtils.getAttributeFlagValue(entityItem3, "STATUS");
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2716 */         if (str2 != null && str2.equals("0020") && "@@".equals(param1String1)) {
/* 2717 */           param1String1 = str1;
/* 2718 */         } else if (str2 != null && str2.equals("0040") && "@@".equals(param1String2)) {
/* 2719 */           param1String2 = str1;
/*      */         } 
/* 2721 */         ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveWDANNDATE thedate=" + param1String1 + ";rfrthedate=" + param1String2 + NEWLINE);
/*      */       } 
/* 2723 */       arrayOfString[0] = param1String1;
/* 2724 */       arrayOfString[1] = param1String2;
/* 2725 */       return arrayOfString;
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
/*      */     public String[] getModelFeatureSpecialDate(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, String param1String1, String param1String2, StringBuffer param1StringBuffer) {
/* 2740 */       String[] arrayOfString = new String[2];
/* 2741 */       String str1 = "@@";
/* 2742 */       String str2 = "@@";
/*      */       
/* 2744 */       EntityItem entityItem = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity1);
/*      */ 
/*      */       
/* 2747 */       if (entityItem != null) {
/*      */ 
/*      */         
/* 2750 */         boolean bool = false;
/* 2751 */         if (param1DiffEntity1.getEntityType().equals("PRODSTRUCT") && 
/* 2752 */           entityItem != null) {
/* 2753 */           String str = PokUtils.getAttributeFlagValue(entityItem, "ORDERCODE");
/* 2754 */           if (str != null && "5957".equals(str)) {
/* 2755 */             bool = true;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 2760 */         if (bool) {
/* 2761 */           if (entityItem.hasDownLinks()) {
/* 2762 */             for (byte b = 0; b < entityItem.getDownLinkCount(); b++) {
/* 2763 */               EntityItem entityItem2 = (EntityItem)entityItem.getDownLink(b);
/* 2764 */               if (entityItem2.getEntityType().equals("MODEL")) {
/* 2765 */                 str1 = PokUtils.getAttributeValue(entityItem2, "WITHDRAWDATE", ", ", "@@", false);
/* 2766 */                 str2 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 2767 */                 ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveWDANNDATE getting WITHDRAWDATE from " + entityItem2.getKey() + " WITHDRAWDATE is:" + str1 + NEWLINE);
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/* 2773 */           EntityItem entityItem1 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity2);
/* 2774 */           if (entityItem1 != null) {
/*      */             
/* 2776 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 2777 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 2778 */               Vector<EntityItem> vector = entityItem1.getDownLink();
/* 2779 */               for (byte b = 0; b < vector.size(); b++) {
/* 2780 */                 EntityItem entityItem2 = vector.elementAt(b);
/* 2781 */                 if (entityItem2.hasDownLinks() && entityItem2.getEntityType().equals("AVAILANNA")) {
/* 2782 */                   Vector<EntityItem> vector1 = entityItem2.getDownLink();
/* 2783 */                   EntityItem entityItem3 = vector1.elementAt(0);
/* 2784 */                   String str = PokUtils.getAttributeValue(entityItem3, "ANNDATE", ", ", "@@", false);
/* 2785 */                   ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveWDANNDATE looking for downlink of AVAILANNA : Announcement ANNDATE :" + str + NEWLINE);
/*      */ 
/*      */                   
/* 2788 */                   if (!"@@".equals(str)) {
/* 2789 */                     if (!"@@".equals(str1)) {
/*      */ 
/*      */ 
/*      */                       
/* 2793 */                       if (str.compareTo(str1) < 0) {
/* 2794 */                         str1 = str;
/* 2795 */                         str2 = PokUtils.getAttributeFlagValue(entityItem3, "STATUS");
/* 2796 */                         param1StringBuffer
/* 2797 */                           .append("XMLTMFAVAILElem.deriveWDANNDATE getting WITHDRAWDATE from Min {MODEL.WITHDRAWDATE and ANNDATE|MODELAVAIL ANNOUNCEMENT.ANNDATE}, WITHDRAWDATE is:" + str1 + NEWLINE);
/*      */                       } 
/*      */                       break;
/*      */                     } 
/* 2801 */                     str1 = str;
/* 2802 */                     str2 = PokUtils.getAttributeFlagValue(entityItem3, "STATUS");
/*      */                   } 
/*      */ 
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 2814 */         if (param1DiffEntity1.getEntityType().equals("PRODSTRUCT")) {
/* 2815 */           String str = PokUtils.getAttributeValue(entityItem, "WITHDRAWDATE", ", ", "@@", false);
/* 2816 */           ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveWDANNDATE looking for PRODSTRUCT.WITHDRAWDATE " + entityItem.getKey() + str + NEWLINE);
/*      */           
/* 2818 */           if (!"@@".equals(str)) {
/* 2819 */             if (!"@@".equals(str1)) {
/*      */ 
/*      */ 
/*      */               
/* 2823 */               if (str.compareTo(str1) < 0) {
/* 2824 */                 str1 = str;
/* 2825 */                 str2 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 2826 */                 param1StringBuffer
/* 2827 */                   .append("XMLTMFAVAILElem.deriveWDANNDATE getting WITHDRAWDATE from Min {MODEL.WITHDRAWDATE and ANNDATE|MODELAVAIL ANNOUNCEMENT.ANNDATE and PRODSTRUCT. WITHDRAWDATE}, WITHDRAWDATE is:" + str1 + NEWLINE);
/*      */               } 
/*      */             } else {
/*      */               
/* 2831 */               str1 = str;
/* 2832 */               str2 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/* 2837 */         if (entityItem.hasUpLinks()) {
/* 2838 */           for (byte b = 0; b < entityItem.getUpLinkCount(); b++) {
/* 2839 */             EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b);
/* 2840 */             if (entityItem1.getEntityType().equals("FEATURE") || entityItem1.getEntityType().equals("SWFEATURE")) {
/* 2841 */               String str = PokUtils.getAttributeValue(entityItem1, "WITHDRAWANNDATE_T", ", ", "@@", false);
/* 2842 */               ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveWDANNDATE getting WITHDRAWANNDATE_T from " + entityItem1.getKey() + " WITHDRAWANNDATE_T is:" + str + NEWLINE);
/*      */               
/* 2844 */               if (!"@@".equals(str)) {
/* 2845 */                 if (!"@@".equals(str1)) {
/*      */ 
/*      */ 
/*      */                   
/* 2849 */                   if (str.compareTo(str1) < 0) {
/* 2850 */                     str1 = str;
/* 2851 */                     str2 = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/* 2852 */                     param1StringBuffer
/* 2853 */                       .append("XMLTMFAVAILElem.deriveWDANNDATE getting WITHDRAWDATE from Min {MODEL.WITHDRAWDATE and FEATURE|SWFEATURE.WITHDRAWANNDATE_T}, WITHDRAWDATE is:" + str1 + NEWLINE);
/*      */                   } 
/*      */                   break;
/*      */                 } 
/* 2857 */                 str1 = str;
/* 2858 */                 str2 = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/*      */               } 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/* 2866 */         if (str2 != null && str2.equals("0020") && "@@".equals(param1String1)) {
/* 2867 */           param1String1 = str1;
/* 2868 */         } else if (str2 != null && str2.equals("0040") && "@@".equals(param1String2)) {
/* 2869 */           param1String2 = str1;
/*      */         } 
/* 2871 */         ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveWDANNDATE thedate=" + param1String1 + ";rfrthedate=" + param1String2 + NEWLINE);
/*      */       } 
/*      */       
/* 2874 */       arrayOfString[0] = param1String1;
/* 2875 */       arrayOfString[1] = param1String2;
/* 2876 */       return arrayOfString;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveLastOrder(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, Vector[] param1ArrayOfVector, StringBuffer param1StringBuffer) {
/* 2887 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveLastOrder  loAvailDiff: " + ((param1DiffEntity3 == null) ? "null" : param1DiffEntity3.getKey()) + " findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 2890 */       String str1 = "@@";
/* 2891 */       String str2 = "@@";
/* 2892 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 2894 */       String[] arrayOfString2 = new String[2];
/* 2895 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 2897 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity3, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 2898 */         str1 = arrayOfString2[0];
/* 2899 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2902 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */         
/* 2905 */         arrayOfString2 = AvailUtil.getProdstructAttributeDate(param1Boolean, param1DiffEntity2, param1DiffEntity4, param1ArrayOfVector, str1, str2, this.country, "WTHDRWEFFCTVDATE", param1StringBuffer);
/* 2906 */         str1 = arrayOfString2[0];
/* 2907 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2910 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2923 */         arrayOfString2 = getModelFeatureSpDate(param1Boolean, param1DiffEntity2, param1DiffEntity4, param1DiffEntity5, str1, str2, param1StringBuffer);
/* 2924 */         str1 = arrayOfString2[0];
/* 2925 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 2928 */       arrayOfString1[0] = str1;
/* 2929 */       arrayOfString1[1] = str2;
/* 2930 */       return arrayOfString1;
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
/*      */     private String[] getModelFeatureSpDate(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, String param1String1, String param1String2, StringBuffer param1StringBuffer) {
/* 2944 */       String[] arrayOfString = new String[2];
/* 2945 */       String str1 = "@@";
/* 2946 */       String str2 = "@@";
/*      */ 
/*      */       
/* 2949 */       EntityItem entityItem = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity1);
/*      */       
/* 2951 */       if (entityItem != null) {
/*      */ 
/*      */         
/* 2954 */         boolean bool = false;
/* 2955 */         if (param1DiffEntity1.getEntityType().equals("PRODSTRUCT") && 
/* 2956 */           entityItem != null) {
/* 2957 */           String str = PokUtils.getAttributeFlagValue(entityItem, "ORDERCODE");
/* 2958 */           if (str != null && "5957".equals(str)) {
/* 2959 */             bool = true;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2965 */         if (bool) {
/* 2966 */           EntityItem entityItem1 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity3);
/* 2967 */           if (entityItem.hasDownLinks()) {
/* 2968 */             for (byte b = 0; b < entityItem.getDownLinkCount(); b++) {
/* 2969 */               EntityItem entityItem3 = (EntityItem)entityItem.getDownLink(b);
/* 2970 */               if (entityItem1 != null) {
/* 2971 */                 EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 2972 */                 if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country) && 
/* 2973 */                   entityItem3.getEntityType().equals("MODEL")) {
/* 2974 */                   str1 = PokUtils.getAttributeValue(entityItem3, "WTHDRWEFFCTVDATE", ", ", "@@", false);
/* 2975 */                   str2 = PokUtils.getAttributeFlagValue(entityItem3, "STATUS");
/* 2976 */                   ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveLastOrder getting WTHDRWEFFCTVDATE from " + entityItem3.getKey() + " PubTo is:" + str1 + NEWLINE);
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           }
/*      */           
/* 2984 */           EntityItem entityItem2 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity2);
/* 2985 */           if (entityItem2 != null) {
/*      */             
/* 2987 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 2988 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 2989 */               String str = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", ", ", "@@", false);
/* 2990 */               ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveLastOrder lomodelavail entityDate: " + entityItem2 + NEWLINE);
/* 2991 */               if (!"@@".equals(str)) {
/* 2992 */                 if (!"@@".equals(str1)) {
/*      */ 
/*      */ 
/*      */                   
/* 2996 */                   if (str.compareTo(str1) < 0) {
/* 2997 */                     str1 = str;
/* 2998 */                     str2 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 2999 */                     param1StringBuffer
/* 3000 */                       .append("XMLTMFAVAILElem.deriveLastOrder getting PubTo from Min {MODEL.WTHDRWEFFCTVDATE and AVAIL.EFFECTIVEDATE MODELAVAIL.AVAIL}, PubTo is:" + str1 + NEWLINE);
/*      */                   } 
/*      */                 } else {
/*      */                   
/* 3004 */                   str1 = str;
/* 3005 */                   str2 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 3013 */         if (param1DiffEntity1 != null && param1DiffEntity1.getEntityType().equals("PRODSTRUCT")) {
/* 3014 */           String str = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", ", ", "@@", false);
/* 3015 */           ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveLastOrder looking for PRODSTRUCT.WTHDRWEFFCTVDATE" + entityItem.getKey() + str + NEWLINE);
/*      */           
/* 3017 */           if (!"@@".equals(str)) {
/* 3018 */             if (!"@@".equals(str1)) {
/*      */ 
/*      */               
/* 3021 */               if (str.compareTo(str1) < 0) {
/* 3022 */                 str1 = str;
/* 3023 */                 str2 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 3024 */                 param1StringBuffer
/* 3025 */                   .append("XMLTMFAVAILElem.deriveLastOrder getting PubTo from Min {PRODSTRUCT.WITHDRAWDATE and MODEL.WTHDRWEFFCTVDATE and ANNOUNCEMENT.ANNDATE MODELAVAIL}, PubTo is:" + str1 + NEWLINE);
/*      */               } 
/*      */             } else {
/*      */               
/* 3029 */               str1 = str;
/* 3030 */               str2 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/* 3035 */         if (entityItem.hasUpLinks()) {
/* 3036 */           for (byte b = 0; b < entityItem.getUpLinkCount(); b++) {
/* 3037 */             EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b);
/* 3038 */             if (entityItem1.getEntityType().equals("FEATURE") || entityItem1.getEntityType().equals("SWFEATURE")) {
/* 3039 */               String str = PokUtils.getAttributeValue(entityItem1, "WITHDRAWDATEEFF_T", ", ", "@@", false);
/* 3040 */               ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveLastOrder getting WITHDRAWDATEEFF_T from " + entityItem1.getKey() + " PubTo is:" + str + NEWLINE);
/*      */               
/* 3042 */               if (!"@@".equals(str)) {
/* 3043 */                 if (!"@@".equals(str1)) {
/*      */ 
/*      */ 
/*      */                   
/* 3047 */                   if (str.compareTo(str1) < 0) {
/* 3048 */                     str1 = str;
/* 3049 */                     str2 = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/* 3050 */                     param1StringBuffer
/* 3051 */                       .append("XMLTMFAVAILElem.deriveLastOrder getting PubTo from Min {MODEL.WTHDRWEFFCTVDATE and AVAIL.EFFECTIVEDATE MODELAVAIL and FEATURE|SWFEATURE.WITHDRAWDATEEFF_T}, PubTo is:" + str1 + NEWLINE);
/*      */                   } 
/*      */                   break;
/*      */                 } 
/* 3055 */                 str1 = str;
/* 3056 */                 str2 = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/*      */               } 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/* 3064 */         if (str2 != null && str2.equals("0020") && "@@".equals(param1String1)) {
/* 3065 */           param1String1 = str1;
/* 3066 */         } else if (str2 != null && str2.equals("0040") && "@@".equals(param1String2)) {
/* 3067 */           param1String2 = str1;
/*      */         } 
/* 3069 */         ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveLastOrder thedate=" + param1String1 + ";rfrthedate=" + param1String2 + NEWLINE);
/*      */       } 
/* 3071 */       arrayOfString[0] = param1String1;
/* 3072 */       arrayOfString[1] = param1String2;
/* 3073 */       return arrayOfString;
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
/*      */     private String[] deriveEOSANNDATE(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, StringBuffer param1StringBuffer) {
/* 3085 */       String str1 = "@@";
/* 3086 */       String str2 = "@@";
/* 3087 */       String[] arrayOfString1 = new String[2];
/* 3088 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveEOSANNDATE endAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 3090 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 3092 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 3093 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "ANNDATE", param1StringBuffer);
/* 3094 */         str1 = arrayOfString2[0];
/* 3095 */         str2 = arrayOfString2[1];
/*      */       } 
/* 3097 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 3098 */         arrayOfString2 = getModelFeatureEOSDate(param1Boolean, param1DiffEntity1, param1DiffEntity3, str1, str2, param1StringBuffer);
/* 3099 */         str1 = arrayOfString2[0];
/* 3100 */         str2 = arrayOfString2[1];
/*      */       } 
/* 3102 */       arrayOfString1[0] = str1;
/* 3103 */       arrayOfString1[1] = str2;
/* 3104 */       return arrayOfString1;
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
/*      */     private String[] getModelFeatureEOSDate(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, String param1String1, String param1String2, StringBuffer param1StringBuffer) {
/* 3117 */       String[] arrayOfString = new String[2];
/* 3118 */       EntityItem entityItem1 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity2);
/* 3119 */       EntityItem entityItem2 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity1);
/*      */ 
/*      */       
/* 3122 */       if (entityItem1 != null && entityItem2 != null) {
/*      */         
/* 3124 */         boolean bool = false;
/*      */ 
/*      */         
/* 3127 */         if (param1DiffEntity1.getEntityType().equals("PRODSTRUCT")) {
/* 3128 */           EntityItem entityItem = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity1);
/* 3129 */           if (entityItem != null) {
/* 3130 */             String str = PokUtils.getAttributeFlagValue(entityItem, "ORDERCODE");
/* 3131 */             if (str != null && "5957".equals(str)) {
/* 3132 */               bool = true;
/*      */             }
/*      */           }
/*      */         
/* 3136 */         } else if (param1DiffEntity1.getEntityType().equals("SWPRODSTRUCT")) {
/* 3137 */           bool = true;
/*      */         } 
/* 3139 */         if (bool) {
/*      */           
/* 3141 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 3142 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 3143 */             Vector<EntityItem> vector = entityItem1.getDownLink();
/* 3144 */             for (byte b = 0; b < vector.size(); b++) {
/* 3145 */               EntityItem entityItem = vector.elementAt(b);
/* 3146 */               if (entityItem.hasDownLinks() && entityItem.getEntityType().equals("AVAILANNA")) {
/* 3147 */                 Vector<EntityItem> vector1 = entityItem.getDownLink();
/* 3148 */                 EntityItem entityItem3 = vector1.elementAt(0);
/* 3149 */                 String str1 = PokUtils.getAttributeValue(entityItem3, "ANNDATE", ", ", "@@", false);
/* 3150 */                 ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveEOSANNDATE looking for MODELAVAIL downlink of AVAILANNA : Announcement " + (
/* 3151 */                     (vector1.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem3
/* 3152 */                     .getKey()) : entityItem3.getKey()) + NEWLINE);
/* 3153 */                 String str2 = PokUtils.getAttributeFlagValue(entityItem3, "ANNSTATUS");
/* 3154 */                 if (str2 != null && str2.equals("0020") && "@@".equals(param1String1)) {
/* 3155 */                   param1String1 = str1;
/* 3156 */                 } else if (str2 != null && str2.equals("0040") && "@@".equals(param1String2)) {
/* 3157 */                   param1String2 = str1;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 3164 */       arrayOfString[0] = param1String1;
/* 3165 */       arrayOfString[1] = param1String2;
/* 3166 */       return arrayOfString;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFSERVICEANNNUMBER(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 3176 */       String str1 = "@@";
/* 3177 */       String str2 = "@@";
/* 3178 */       String[] arrayOfString1 = new String[2];
/* 3179 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveEOSANNDATE endAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */       
/* 3181 */       String[] arrayOfString2 = new String[2];
/*      */       
/* 3183 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 3184 */         arrayOfString2 = AvailUtil.getAvailAnnAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "ANNNUMBER", param1StringBuffer);
/* 3185 */         str1 = arrayOfString2[0];
/* 3186 */         str2 = arrayOfString2[1];
/*      */       } 
/* 3188 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 3189 */         arrayOfString2 = getModelRelatedEOSANNNUM(param1Boolean, param1DiffEntity1, param1DiffEntity3, str1, str2, param1StringBuffer);
/* 3190 */         str1 = arrayOfString2[0];
/* 3191 */         str2 = arrayOfString2[1];
/*      */       } 
/* 3193 */       arrayOfString1[0] = str1;
/* 3194 */       arrayOfString1[1] = str2;
/* 3195 */       return arrayOfString1;
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
/*      */     private String[] getModelRelatedEOSANNNUM(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, String param1String1, String param1String2, StringBuffer param1StringBuffer) {
/* 3210 */       String[] arrayOfString = new String[2];
/* 3211 */       EntityItem entityItem1 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity2);
/* 3212 */       EntityItem entityItem2 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity1);
/*      */ 
/*      */       
/* 3215 */       if (entityItem1 != null && entityItem2 != null) {
/*      */         
/* 3217 */         boolean bool = false;
/*      */ 
/*      */         
/* 3220 */         if (param1DiffEntity1.getEntityType().equals("PRODSTRUCT")) {
/* 3221 */           EntityItem entityItem = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity1);
/* 3222 */           if (entityItem != null) {
/* 3223 */             String str = PokUtils.getAttributeFlagValue(entityItem, "ORDERCODE");
/* 3224 */             if (str != null && "5957".equals(str)) {
/* 3225 */               bool = true;
/*      */             }
/*      */           }
/*      */         
/* 3229 */         } else if (param1DiffEntity1.getEntityType().equals("SWPRODSTRUCT")) {
/* 3230 */           bool = true;
/*      */         } 
/* 3232 */         if (bool) {
/*      */           
/* 3234 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 3235 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 3236 */             Vector<EntityItem> vector = entityItem1.getDownLink();
/* 3237 */             for (byte b = 0; b < vector.size(); b++) {
/* 3238 */               EntityItem entityItem = vector.elementAt(b);
/* 3239 */               if (entityItem.hasDownLinks() && entityItem.getEntityType().equals("AVAILANNA")) {
/* 3240 */                 Vector<EntityItem> vector1 = entityItem.getDownLink();
/* 3241 */                 EntityItem entityItem3 = vector1.elementAt(0);
/* 3242 */                 String str1 = PokUtils.getAttributeValue(entityItem3, "ANNNUMBER", ", ", "@@", false);
/* 3243 */                 ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveEOSANNNUM looking for MODELAVAIL downlink of AVAILANNA : Announcement " + (
/* 3244 */                     (vector1.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem3
/* 3245 */                     .getKey()) : entityItem3.getKey()) + NEWLINE);
/* 3246 */                 String str2 = PokUtils.getAttributeFlagValue(entityItem3, "ANNSTATUS");
/* 3247 */                 if (str2 != null && str2.equals("0020") && "@@".equals(param1String1)) {
/* 3248 */                   param1String1 = str1;
/* 3249 */                 } else if (str2 != null && str2.equals("0040") && "@@".equals(param1String2)) {
/* 3250 */                   param1String2 = str1;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 3257 */       arrayOfString[0] = param1String1;
/* 3258 */       arrayOfString[1] = param1String2;
/* 3259 */       return arrayOfString;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String[] deriveENDOFSERVICEDATE(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, StringBuffer param1StringBuffer) {
/* 3269 */       String str1 = "@@";
/* 3270 */       String str2 = "@@";
/* 3271 */       ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveENDOFSERVICEDATE endAvailDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*      */ 
/*      */       
/* 3274 */       String[] arrayOfString1 = new String[2];
/*      */       
/* 3276 */       String[] arrayOfString2 = new String[2];
/* 3277 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 3279 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, param1DiffEntity2, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 3280 */         str1 = arrayOfString2[0];
/* 3281 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 3284 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/*      */         
/* 3286 */         arrayOfString2 = getModelFeatureENDSDate(param1Boolean, param1DiffEntity1, param1DiffEntity3, str1, str2, param1StringBuffer);
/* 3287 */         str1 = arrayOfString2[0];
/* 3288 */         str2 = arrayOfString2[1];
/*      */       } 
/*      */       
/* 3291 */       arrayOfString1[0] = str1;
/* 3292 */       arrayOfString1[1] = str2;
/* 3293 */       return arrayOfString1;
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
/*      */     private String[] getModelFeatureENDSDate(boolean param1Boolean, DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, String param1String1, String param1String2, StringBuffer param1StringBuffer) {
/* 3307 */       String[] arrayOfString = new String[2];
/* 3308 */       EntityItem entityItem = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity2);
/*      */ 
/*      */ 
/*      */       
/* 3312 */       if (entityItem != null) {
/*      */         
/* 3314 */         boolean bool = false;
/*      */ 
/*      */         
/* 3317 */         if (param1DiffEntity1.getEntityType().equals("PRODSTRUCT")) {
/* 3318 */           EntityItem entityItem1 = AvailUtil.getEntityItem(param1Boolean, param1DiffEntity1);
/* 3319 */           if (entityItem1 != null) {
/* 3320 */             String str = PokUtils.getAttributeFlagValue(entityItem1, "ORDERCODE");
/* 3321 */             if (str != null && "5957".equals(str)) {
/* 3322 */               bool = true;
/*      */             }
/*      */           }
/*      */         
/* 3326 */         } else if (param1DiffEntity1.getEntityType().equals("SWPRODSTRUCT")) {
/* 3327 */           bool = true;
/*      */         } 
/* 3329 */         if (bool) {
/*      */           
/* 3331 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 3332 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 3333 */             String str1 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/* 3334 */             String str2 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 3335 */             if (str2 != null && str2.equals("0020") && "@@".equals(param1String1)) {
/* 3336 */               param1String1 = str1;
/* 3337 */             } else if (str2 != null && str2.equals("0040") && "@@".equals(param1String2)) {
/* 3338 */               param1String2 = str1;
/*      */             } 
/* 3340 */             ABRUtil.append(param1StringBuffer, "XMLTMFAVAILElem.deriveENDOFSERVICEDATE endAvailDiff MODELAVAIL, thedate: " + param1String1 + NEWLINE);
/*      */           } 
/*      */         } 
/*      */       } 
/* 3344 */       arrayOfString[0] = param1String1;
/* 3345 */       arrayOfString[1] = param1String2;
/* 3346 */       return arrayOfString;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     String getCountry() {
/* 3352 */       return this.country;
/*      */     }
/*      */     
/*      */     String getKey() {
/* 3356 */       return this.country;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 3361 */       return (this.availDiff != null) ? (this.availDiff.getKey() + " action:" + this.action) : "There is no AVAIL! ";
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLTMFAVAILElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */