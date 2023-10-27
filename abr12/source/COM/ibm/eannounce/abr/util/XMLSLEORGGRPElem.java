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
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.StringTokenizer;
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
/*      */ public class XMLSLEORGGRPElem
/*      */   extends XMLElem
/*      */ {
/*  136 */   private String path = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLSLEORGGRPElem() {
/*  143 */     super("SLEORGGRPLIST");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLSLEORGGRPElem(String paramString) {
/*  151 */     super("SLEORGGRPLIST");
/*  152 */     this.path = paramString;
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
/*  169 */     if ("TAXCATG".equals(paramDiffEntity.getEntityType()) || "TAXGRP".equals(paramDiffEntity.getEntityType()) || "D:AVAILSLEORGA:D".equals(this.path)) {
/*  170 */       EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  171 */       if (paramDiffEntity.isDeleted()) {
/*  172 */         entityItem1 = paramDiffEntity.getPriorEntityItem();
/*      */       }
/*  174 */       EntityItem entityItem2 = null;
/*      */       
/*  176 */       if (entityItem1 != null) {
/*  177 */         Vector<EntityItem> vector = entityItem1.getUpLink();
/*  178 */         EntityItem entityItem = vector.elementAt(0);
/*  179 */         if (entityItem != null) {
/*  180 */           entityItem2 = entityItem.getUpLink().elementAt(0);
/*      */         }
/*      */       } 
/*  183 */       displayAVAILSLEORG(paramHashtable, paramDocument, paramElement, entityItem2, paramDiffEntity, this.path, "@@", "@@", paramStringBuffer);
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
/*      */   protected boolean hasChanges(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  196 */     boolean bool = false;
/*  197 */     if ("TAXCATG".equals(paramDiffEntity.getEntityType()) || "TAXGRP".equals(paramDiffEntity.getEntityType()) || "D:AVAILSLEORGA:D".equals(this.path)) {
/*  198 */       EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  199 */       EntityItem entityItem2 = null;
/*      */       
/*  201 */       if (entityItem1 != null) {
/*  202 */         Vector<EntityItem> vector = entityItem1.getUpLink();
/*  203 */         EntityItem entityItem = vector.elementAt(0);
/*  204 */         if (entityItem != null) {
/*  205 */           entityItem2 = entityItem.getUpLink().elementAt(0);
/*      */         }
/*      */       } 
/*  208 */       bool = hasChanges(paramHashtable, entityItem2, paramDiffEntity, this.path, "@@", paramStringBuffer);
/*      */     } 
/*  210 */     return bool;
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
/*      */   protected boolean hasChanges(Hashtable paramHashtable, EntityItem paramEntityItem, DiffEntity paramDiffEntity, String paramString1, String paramString2, StringBuffer paramStringBuffer) {
/*  225 */     boolean bool = false;
/*  226 */     if (paramEntityItem != null && paramDiffEntity != null) {
/*  227 */       String str1 = "";
/*  228 */       str1 = getCOFCAT(paramEntityItem);
/*  229 */       String str2 = null;
/*      */ 
/*      */       
/*  232 */       if ("TAXCATG".equals(paramDiffEntity.getEntityType()) || "TAXGRP".equals(paramDiffEntity.getEntityType()) || "D:AVAILSLEORGA:D".equals(paramString1)) {
/*  233 */         str2 = paramDiffEntity.getKey();
/*      */       } else {
/*  235 */         str2 = paramEntityItem.getKey();
/*      */       } 
/*  237 */       boolean bool1 = false;
/*  238 */       boolean bool2 = false;
/*  239 */       if ("SVCSEO".equals(paramEntityItem.getEntityType())) {
/*  240 */         bool2 = true;
/*      */       }
/*  242 */       Vector<DiffEntity> vector = getSLEORGNPLNTcode(paramHashtable, str2, paramString1, bool2, paramStringBuffer);
/*  243 */       if (vector.size() > 0) {
/*  244 */         Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  245 */         Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  246 */         Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*  247 */         Vector[] arrayOfVector = getSLEORGGRP(paramDiffEntity, paramStringBuffer);
/*      */         
/*  249 */         Vector vector1 = new Vector();
/*      */         
/*  251 */         for (byte b = 0; b < vector.size(); b++) {
/*  252 */           DiffEntity diffEntity = vector.elementAt(b);
/*  253 */           buildCtrySOPRecs(vector1, diffEntity, str1, arrayOfVector, paramString2, bool1, hashtable2, hashtable3, paramStringBuffer);
/*      */         } 
/*      */         
/*  256 */         bool = createCodeNodes((Document)null, paramStringBuffer, vector1, (Element)null);
/*  257 */         ABRUtil.append(paramStringBuffer, "XMLSLEORGGRPElem.hasChanges:  for SLEORGNPLNT " + bool + NEWLINE);
/*  258 */         getChgTbl(hashtable2, hashtable3, hashtable1);
/*  259 */         if (hashtable1.size() > 0) {
/*  260 */           bool = true;
/*  261 */           ABRUtil.append(paramStringBuffer, "XMLSLEORGGRPElem.hasChanges:  for SLEORGGRP " + bool + NEWLINE);
/*      */         } 
/*  263 */         vector1.clear();
/*  264 */         hashtable1.clear();
/*  265 */         hashtable2.clear();
/*  266 */         hashtable3.clear();
/*  267 */         arrayOfVector[0].clear();
/*  268 */         arrayOfVector[1].clear();
/*      */       } 
/*      */     } 
/*  271 */     return bool;
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
/*      */   protected boolean hasChangesMODEL(Hashtable paramHashtable, EntityItem paramEntityItem, DiffEntity paramDiffEntity, String paramString1, String paramString2, String paramString3, StringBuffer paramStringBuffer) {
/*  287 */     boolean bool = false;
/*  288 */     if (paramEntityItem != null && paramDiffEntity != null) {
/*  289 */       String str1 = "";
/*  290 */       str1 = getCOFCAT(paramEntityItem);
/*  291 */       String str2 = null;
/*      */ 
/*      */       
/*  294 */       if ("TAXCATG".equals(paramDiffEntity.getEntityType()) || "TAXGRP".equals(paramDiffEntity.getEntityType()) || "D:AVAILSLEORGA:D".equals(paramString1)) {
/*  295 */         str2 = paramDiffEntity.getKey();
/*      */       } else {
/*  297 */         str2 = paramEntityItem.getKey();
/*      */       } 
/*  299 */       boolean bool1 = false;
/*  300 */       boolean bool2 = false;
/*  301 */       if ("SVCSEO".equals(paramEntityItem.getEntityType())) {
/*  302 */         bool2 = true;
/*      */       }
/*      */       
/*  305 */       Vector<DiffEntity> vector = getSLEORGNPLNTcodeLSEO(paramHashtable, str2, paramString1, bool2, paramString3, paramStringBuffer);
/*  306 */       if (vector.size() > 0) {
/*  307 */         Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  308 */         Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  309 */         Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*  310 */         Vector[] arrayOfVector = getSLEORGGRP(paramDiffEntity, paramStringBuffer);
/*      */         
/*  312 */         Vector vector1 = new Vector();
/*      */         
/*  314 */         for (byte b = 0; b < vector.size(); b++) {
/*  315 */           DiffEntity diffEntity = vector.elementAt(b);
/*  316 */           buildCtrySOPRecs(vector1, diffEntity, str1, arrayOfVector, paramString2, bool1, hashtable2, hashtable3, paramStringBuffer);
/*      */         } 
/*      */         
/*  319 */         bool = createCodeNodes((Document)null, paramStringBuffer, vector1, (Element)null);
/*  320 */         ABRUtil.append(paramStringBuffer, "XMLSLEORGGRPElem.hasChanges:  for SLEORGNPLNT" + bool + NEWLINE);
/*  321 */         getChgTbl(hashtable2, hashtable3, hashtable1);
/*  322 */         if (hashtable1.size() > 0) {
/*  323 */           bool = true;
/*      */         }
/*  325 */         vector1.clear();
/*  326 */         hashtable1.clear();
/*  327 */         hashtable2.clear();
/*  328 */         hashtable3.clear();
/*  329 */         arrayOfVector[0].clear();
/*  330 */         arrayOfVector[1].clear();
/*      */       } 
/*      */     } 
/*  333 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getCOFCAT(EntityItem paramEntityItem) {
/*  342 */     String str = "@@";
/*  343 */     if (paramEntityItem != null) {
/*  344 */       String str1 = paramEntityItem.getEntityType();
/*  345 */       String str2 = null;
/*  346 */       if ("MODEL".equals(str1)) {
/*  347 */         str2 = "COFCAT";
/*  348 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str2);
/*  349 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */           
/*  351 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  352 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/*  354 */             if (arrayOfMetaFlag[b].isSelected()) {
/*  355 */               str = arrayOfMetaFlag[b].toString();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  362 */               if ("Service".equalsIgnoreCase(str))
/*      */               {
/*  364 */                 str = "Servicepac";
/*      */               }
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*  370 */       } else if ("SVCMOD".equals(str1)) {
/*  371 */         str = "Service";
/*  372 */       } else if ("SVCSEO".equals(str1)) {
/*  373 */         str = "Service";
/*  374 */       } else if ("FCTRANSACTION".equals(str1)) {
/*  375 */         str = "Hardware";
/*  376 */       } else if ("LSEOBUNDLE".equals(str1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  388 */         str2 = "BUNDLETYPE";
/*  389 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str2);
/*  390 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */           
/*  392 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  393 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */ 
/*      */             
/*  396 */             if (arrayOfMetaFlag[b].isSelected()) {
/*  397 */               if (str.equals("@@")) {
/*  398 */                 str = arrayOfMetaFlag[b].toString();
/*      */               } else {
/*  400 */                 str = str + "," + arrayOfMetaFlag[b].toString();
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  406 */         if (str.indexOf("Hardware") > -1) {
/*  407 */           str = "Hardware";
/*  408 */         } else if (str.indexOf("Software") > -1) {
/*  409 */           str = "Software";
/*      */         } else {
/*  411 */           str = "Service";
/*      */         } 
/*  413 */       } else if ("PRODSTRUCT".equals(str1)) {
/*  414 */         str = "Hardware";
/*  415 */       } else if ("SWPRODSTRUCT".equals(str1)) {
/*  416 */         str = "Software";
/*      */       } 
/*      */     } 
/*  419 */     return str;
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
/*      */   public void displayAVAILSLEORG(Hashtable paramHashtable, Document paramDocument, Element paramElement, EntityItem paramEntityItem, DiffEntity paramDiffEntity, String paramString1, String paramString2, String paramString3, StringBuffer paramStringBuffer) {
/*  454 */     if (paramEntityItem != null && paramDiffEntity != null && !"Delete".equals(paramString3)) {
/*      */       
/*  456 */       String str1 = "";
/*  457 */       str1 = getCOFCAT(paramEntityItem);
/*  458 */       String str2 = null;
/*      */ 
/*      */       
/*  461 */       if ("TAXCATG".equals(paramDiffEntity.getEntityType()) || "TAXGRP".equals(paramDiffEntity.getEntityType()) || "D:AVAILSLEORGA:D".equals(paramString1)) {
/*  462 */         str2 = paramDiffEntity.getKey();
/*      */       } else {
/*  464 */         str2 = paramEntityItem.getKey();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  474 */       boolean bool1 = false;
/*  475 */       boolean bool2 = false;
/*  476 */       if ("SVCSEO".equals(paramEntityItem.getEntityType())) {
/*      */         
/*  478 */         bool2 = true;
/*  479 */         if (paramString1.startsWith("NEW")) {
/*  480 */           bool1 = true;
/*      */         }
/*  482 */         paramString1 = "D:SVCMOD:D:SVCMODSVCSEO:D:SVCSEO:D:SVCSEOAVAIL:D:" + paramDiffEntity.getKey() + ":D:AVAILSLEORGA:D";
/*      */       } 
/*      */ 
/*      */       
/*  486 */       Vector<DiffEntity> vector = getSLEORGNPLNTcode(paramHashtable, str2, paramString1, bool2, paramStringBuffer);
/*  487 */       if (vector.size() > 0) {
/*  488 */         Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  489 */         Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  490 */         Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*  491 */         Vector[] arrayOfVector = getSLEORGGRP(paramDiffEntity, paramStringBuffer);
/*      */         
/*  493 */         Vector vector1 = new Vector();
/*      */         
/*  495 */         for (byte b = 0; b < vector.size(); b++) {
/*  496 */           DiffEntity diffEntity = vector.elementAt(b);
/*  497 */           buildCtrySOPRecs(vector1, diffEntity, str1, arrayOfVector, paramString2, bool1, hashtable2, hashtable3, paramStringBuffer);
/*      */         } 
/*      */ 
/*      */         
/*  501 */         getChgTbl(hashtable2, hashtable3, hashtable1);
/*  502 */         createGroupNodeSet(paramDocument, paramElement, hashtable1, paramStringBuffer);
/*      */         
/*  504 */         Element element = paramDocument.createElement("SLEORGNPLNTCODELIST");
/*  505 */         paramElement.appendChild(element);
/*  506 */         createCodeNodes(paramDocument, paramStringBuffer, vector1, element);
/*  507 */         vector1.clear();
/*  508 */         hashtable1.clear();
/*  509 */         hashtable2.clear();
/*  510 */         hashtable3.clear();
/*  511 */         arrayOfVector[0].clear();
/*  512 */         arrayOfVector[1].clear();
/*      */       } else {
/*  514 */         Element element = paramDocument.createElement(this.nodeName);
/*  515 */         paramElement.appendChild(element);
/*  516 */         element = paramDocument.createElement("SLEORGNPLNTCODELIST");
/*  517 */         paramElement.appendChild(element);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  522 */       Element element = paramDocument.createElement(this.nodeName);
/*  523 */       paramElement.appendChild(element);
/*  524 */       element = paramDocument.createElement("SLEORGNPLNTCODELIST");
/*  525 */       paramElement.appendChild(element);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean createCodeNodes(Document paramDocument, StringBuffer paramStringBuffer, Vector<CtrySOPRecord> paramVector, Element paramElement) {
/*  533 */     if (paramVector.size() == 0) {
/*  534 */       ABRUtil.append(paramStringBuffer, "XMLSLEORGGRPElem.createCodeNodes:  no related SLEORGGRP found!" + NEWLINE);
/*  535 */       return false;
/*      */     } 
/*      */     
/*  538 */     Vector<String> vector1 = new Vector();
/*  539 */     Vector<String> vector2 = new Vector();
/*  540 */     Vector<String> vector3 = new Vector();
/*  541 */     for (byte b1 = 0; b1 < paramVector.size(); b1++) {
/*  542 */       CtrySOPRecord ctrySOPRecord = paramVector.get(b1);
/*  543 */       ctrySOPRecord.setallFields(null, paramStringBuffer);
/*  544 */       String str = ctrySOPRecord.getSLEORG() + ctrySOPRecord.getPLNTCD();
/*  545 */       if (!ctrySOPRecord.isDisplayable()) {
/*  546 */         vector1.add(str);
/*  547 */       } else if ("Update".equals(ctrySOPRecord.getAction())) {
/*  548 */         vector2.add(str);
/*  549 */       } else if ("Delete".equals(ctrySOPRecord.getAction())) {
/*  550 */         vector3.add(str);
/*      */       } 
/*      */     } 
/*  553 */     Vector<String> vector4 = new Vector();
/*      */     
/*  555 */     for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/*  556 */       CtrySOPRecord ctrySOPRecord = paramVector.get(b2);
/*      */       
/*  558 */       String str1 = ctrySOPRecord.getSLEORG() + ctrySOPRecord.getPLNTCD();
/*  559 */       String str2 = ctrySOPRecord.getAction();
/*      */ 
/*      */       
/*  562 */       boolean bool = (!vector1.contains(str1) && (("Delete".equals(str2) && !vector2.contains(str1)) || ("Update".equals(str2) && !vector3.contains(str1)))) ? true : false;
/*  563 */       if (bool) {
/*  564 */         if (paramDocument != null && paramElement != null) {
/*  565 */           String str = ctrySOPRecord.getSLEORG() + ctrySOPRecord.getPLNTCD() + str2;
/*  566 */           if (!vector4.contains(str)) {
/*  567 */             createCodeNodeSet(paramDocument, paramElement, ctrySOPRecord, paramStringBuffer);
/*  568 */             vector4.add(str);
/*      */           } 
/*      */         } else {
/*  571 */           return true;
/*      */         } 
/*      */       }
/*  574 */       ctrySOPRecord.dereference();
/*      */     } 
/*  576 */     vector4.clear();
/*  577 */     return false;
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
/*      */   public void displayAVAILSLEORGLSEO(Hashtable paramHashtable, Document paramDocument, Element paramElement, EntityItem paramEntityItem, DiffEntity paramDiffEntity, String paramString1, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer) {
/*  596 */     if (paramEntityItem != null && paramDiffEntity != null && !"Delete".equals(paramString3)) {
/*      */       
/*  598 */       String str1 = "";
/*  599 */       str1 = getCOFCAT(paramEntityItem);
/*  600 */       String str2 = null;
/*      */ 
/*      */       
/*  603 */       if ("TAXCATG".equals(paramDiffEntity.getEntityType()) || "TAXGRP".equals(paramDiffEntity.getEntityType()) || "D:AVAILSLEORGA:D".equals(paramString1)) {
/*  604 */         str2 = paramDiffEntity.getKey();
/*      */       } else {
/*  606 */         str2 = paramEntityItem.getKey();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  616 */       boolean bool1 = false;
/*  617 */       boolean bool2 = false;
/*  618 */       if ("SVCSEO".equals(paramEntityItem.getEntityType())) {
/*      */         
/*  620 */         bool2 = true;
/*  621 */         if (paramString1.startsWith("NEW")) {
/*  622 */           bool1 = true;
/*      */         }
/*  624 */         paramString1 = "D:SVCMOD:D:SVCMODSVCSEO:D:SVCSEO:D:SVCSEOAVAIL:D:" + paramDiffEntity.getKey() + ":D:AVAILSLEORGA:D";
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  629 */       Vector<DiffEntity> vector = getSLEORGNPLNTcodeLSEO(paramHashtable, str2, paramString1, bool2, paramString4, paramStringBuffer);
/*  630 */       ABRUtil.append(paramStringBuffer, "XMLSLEORGGRPElem.displayAVAILSLEORG ctry = " + paramString2 + ";SLEORGNPLNTVct   size=" + vector.size() + NEWLINE);
/*  631 */       if (vector.size() > 0) {
/*  632 */         Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  633 */         Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*  634 */         Hashtable<Object, Object> hashtable3 = new Hashtable<>();
/*  635 */         Vector[] arrayOfVector = getSLEORGGRP(paramDiffEntity, paramStringBuffer);
/*      */         
/*  637 */         Vector vector1 = new Vector();
/*      */         
/*  639 */         for (byte b = 0; b < vector.size(); b++) {
/*  640 */           DiffEntity diffEntity = vector.elementAt(b);
/*  641 */           buildCtrySOPRecs(vector1, diffEntity, str1, arrayOfVector, paramString2, bool1, hashtable2, hashtable3, paramStringBuffer);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  646 */         getChgTbl(hashtable2, hashtable3, hashtable1);
/*      */         
/*  648 */         createGroupNodeSet(paramDocument, paramElement, hashtable1, paramStringBuffer);
/*      */         
/*  650 */         Element element = paramDocument.createElement("SLEORGNPLNTCODELIST");
/*  651 */         paramElement.appendChild(element);
/*  652 */         createCodeNodes(paramDocument, paramStringBuffer, vector1, element);
/*  653 */         vector1.clear();
/*  654 */         hashtable1.clear();
/*  655 */         hashtable2.clear();
/*  656 */         hashtable3.clear();
/*  657 */         arrayOfVector[0].clear();
/*  658 */         arrayOfVector[1].clear();
/*      */       } else {
/*  660 */         Element element = paramDocument.createElement(this.nodeName);
/*  661 */         paramElement.appendChild(element);
/*  662 */         element = paramDocument.createElement("SLEORGNPLNTCODELIST");
/*  663 */         paramElement.appendChild(element);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  668 */       Element element = paramDocument.createElement(this.nodeName);
/*  669 */       paramElement.appendChild(element);
/*  670 */       element = paramDocument.createElement("SLEORGNPLNTCODELIST");
/*  671 */       paramElement.appendChild(element);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void getChgTbl(Hashtable paramHashtable1, Hashtable paramHashtable2, Hashtable<String, String> paramHashtable3) {
/*  678 */     Enumeration<String> enumeration1 = paramHashtable1.keys();
/*  679 */     while (enumeration1.hasMoreElements()) {
/*  680 */       String str = enumeration1.nextElement();
/*  681 */       if (!paramHashtable2.containsKey(str)) {
/*  682 */         paramHashtable3.put(str, "Delete");
/*      */       }
/*      */     } 
/*      */     
/*  686 */     Enumeration<String> enumeration2 = paramHashtable2.keys();
/*  687 */     while (enumeration2.hasMoreElements()) {
/*  688 */       String str = enumeration2.nextElement();
/*  689 */       if (!paramHashtable1.containsKey(str)) {
/*  690 */         paramHashtable3.put(str, "Update");
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
/*      */   private void createCodeNodeSet(Document paramDocument, Element paramElement, CtrySOPRecord paramCtrySOPRecord, StringBuffer paramStringBuffer) {
/*  706 */     Element element1 = paramDocument.createElement("SLEORGNPLNTCODEELEMENT");
/*  707 */     paramElement.appendChild(element1);
/*  708 */     Element element2 = paramDocument.createElement("SLEORGNPLNTCODEACTION");
/*  709 */     element2.appendChild(paramDocument.createTextNode("" + paramCtrySOPRecord.getAction()));
/*  710 */     element1.appendChild(element2);
/*  711 */     element2 = paramDocument.createElement("SLEORG");
/*  712 */     element2.appendChild(paramDocument.createTextNode("" + paramCtrySOPRecord.getSLEORG()));
/*  713 */     element1.appendChild(element2);
/*  714 */     element2 = paramDocument.createElement("PLNTCD");
/*  715 */     element2.appendChild(paramDocument.createTextNode("" + paramCtrySOPRecord.getPLNTCD()));
/*  716 */     element1.appendChild(element2);
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
/*      */   private void createGroupNodeSet(Document paramDocument, Element paramElement, Hashtable paramHashtable, StringBuffer paramStringBuffer) {
/*  728 */     Element element = paramDocument.createElement(this.nodeName);
/*  729 */     paramElement.appendChild(element);
/*  730 */     Enumeration<String> enumeration = paramHashtable.keys();
/*  731 */     while (enumeration.hasMoreElements()) {
/*  732 */       String str1 = enumeration.nextElement();
/*  733 */       String str2 = (String)paramHashtable.get(str1);
/*  734 */       Element element1 = paramDocument.createElement("SLEORGGRPELEMENT");
/*  735 */       element.appendChild(element1);
/*  736 */       Element element2 = paramDocument.createElement("SLEOORGGRPACTION");
/*  737 */       element2.appendChild(paramDocument.createTextNode("" + str2));
/*  738 */       element1.appendChild(element2);
/*  739 */       element2 = paramDocument.createElement("SLEORGGRP");
/*  740 */       element2.appendChild(paramDocument.createTextNode("" + str1));
/*  741 */       element1.appendChild(element2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector[] getSLEORGGRP(DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  752 */     Vector<String> vector1 = new Vector(1);
/*  753 */     Vector<String> vector2 = new Vector(1);
/*  754 */     Vector[] arrayOfVector = new Vector[2];
/*  755 */     arrayOfVector[0] = vector1;
/*  756 */     arrayOfVector[1] = vector2;
/*  757 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*  758 */     if (entityItem != null) {
/*  759 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("SLEORGGRP");
/*      */ 
/*      */       
/*  762 */       if (eANFlagAttribute != null) {
/*  763 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  764 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  766 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  767 */             vector1.addElement(arrayOfMetaFlag[b].toString());
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  772 */     if (!paramDiffEntity.isNew()) {
/*  773 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramDiffEntity.getPriorEntityItem().getAttribute("SLEORGGRP");
/*      */       
/*  775 */       if (eANFlagAttribute != null) {
/*  776 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  777 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  779 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  780 */             vector2.addElement(arrayOfMetaFlag[b].toString());
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  786 */     return arrayOfVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getSLEORGNPLNTcodeLSEO(Hashtable paramHashtable, String paramString1, String paramString2, boolean paramBoolean, String paramString3, StringBuffer paramStringBuffer) {
/*  797 */     Vector<DiffEntity> vector1 = (Vector)paramHashtable.get("SLEORGNPLNTCODE");
/*  798 */     Vector<DiffEntity> vector2 = new Vector(1);
/*  799 */     if (vector1 != null) {
/*  800 */       for (byte b = 0; b < vector1.size(); b++) {
/*  801 */         DiffEntity diffEntity = vector1.elementAt(b);
/*  802 */         if (diffEntity.toString().indexOf("MODELAVAIL") > -1) {
/*  803 */           if (paramString3.equals("LSEOSPECBID"))
/*      */           {
/*  805 */             vector2.add(diffEntity);
/*      */           }
/*  807 */         } else if (diffEntity.toString().indexOf("LSEOAVAIL") > -1 && (
/*  808 */           paramString3.equals("LSEO") || paramString3.equals("LSEOSPECBID"))) {
/*  809 */           vector2.add(diffEntity);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  814 */     return vector2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getSLEORGNPLNTcode(Hashtable paramHashtable, String paramString1, String paramString2, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  825 */     Vector<DiffEntity> vector1 = new Vector(1);
/*  826 */     Vector vector = new Vector(1);
/*  827 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("SLEORGNPLNTCODE");
/*      */ 
/*      */ 
/*      */     
/*  831 */     if (vector2 == null) {
/*  832 */       return vector;
/*      */     }
/*      */     
/*  835 */     if (paramString2 == null) {
/*  836 */       return vector2;
/*      */     }
/*      */     
/*  839 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString2, ":");
/*  840 */     int i = (stringTokenizer.countTokens() - 1) / 2;
/*  841 */     String str = "@@";
/*  842 */     String[] arrayOfString = new String[i];
/*      */     
/*  844 */     while (stringTokenizer.hasMoreTokens()) {
/*  845 */       stringTokenizer.nextToken();
/*  846 */       if (stringTokenizer.hasMoreTokens()) {
/*  847 */         str = stringTokenizer.nextToken();
/*  848 */         arrayOfString[--i] = str;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  855 */     for (byte b = 0; b < vector2.size(); b++) {
/*  856 */       DiffEntity diffEntity = vector2.elementAt(b);
/*  857 */       StringTokenizer stringTokenizer1 = new StringTokenizer(diffEntity.toString(), " ");
/*  858 */       String str1 = "@@";
/*  859 */       while (stringTokenizer1.hasMoreTokens()) {
/*  860 */         str1 = stringTokenizer1.nextToken();
/*  861 */         if (str1.startsWith("path:"))
/*      */           break; 
/*      */       } 
/*  864 */       StringTokenizer stringTokenizer2 = new StringTokenizer(str1, ":");
/*  865 */       int j = stringTokenizer2.countTokens();
/*  866 */       String[] arrayOfString1 = new String[j];
/*  867 */       byte b1 = 0;
/*  868 */       while (stringTokenizer2.hasMoreTokens()) {
/*  869 */         String str2 = stringTokenizer2.nextToken();
/*  870 */         if (!"path".equals(str2)) {
/*  871 */           arrayOfString1[b1++] = str2;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  876 */       boolean bool = false;
/*  877 */       int k = arrayOfString.length;
/*  878 */       for (byte b2 = 0; b2 < k; b2++) {
/*  879 */         if (arrayOfString1[b2].startsWith(arrayOfString[b2])) {
/*  880 */           bool = true;
/*      */         } else {
/*  882 */           bool = false;
/*      */           break;
/*      */         } 
/*  885 */         if (b2 == k - 1 && bool && 
/*  886 */           b2 != 7 && !paramBoolean) {
/*  887 */           if (arrayOfString1[k] != null && arrayOfString1[k].startsWith(paramString1)) {
/*  888 */             bool = true;
/*      */           } else {
/*  890 */             bool = false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  895 */       if (bool) {
/*  896 */         vector1.add(diffEntity);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  901 */     return vector1;
/*      */   }
/*      */   private void addGrpTbl(EntityItem paramEntityItem, Hashtable<String, String> paramHashtable, Vector paramVector, StringBuffer paramStringBuffer, boolean paramBoolean) {
/*  904 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("SLEORGGRP");
/*  905 */     if (eANFlagAttribute != null) {
/*  906 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*      */       
/*  908 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/*  910 */         if (arrayOfMetaFlag[b].isSelected()) {
/*  911 */           String str = arrayOfMetaFlag[b].toString();
/*      */ 
/*      */           
/*  914 */           if (paramVector.size() == 0) {
/*  915 */             paramHashtable.put(str, "@@");
/*  916 */           } else if (paramVector.contains(str)) {
/*  917 */             paramHashtable.put(str, "@@");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildCtrySOPRecs(Vector<CtrySOPRecord> paramVector, DiffEntity paramDiffEntity, String paramString1, Vector[] paramArrayOfVector, String paramString2, boolean paramBoolean, Hashtable paramHashtable1, Hashtable paramHashtable2, StringBuffer paramStringBuffer) {
/*  951 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  952 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*      */     
/*  954 */     Vector vector1 = paramArrayOfVector[0];
/*  955 */     Vector vector2 = paramArrayOfVector[1];
/*  956 */     if (paramDiffEntity.isDeleted()) {
/*      */       
/*  958 */       boolean bool = checkSLEORGGRPAvailable(paramString2, entityItem2, paramString1, vector2, paramStringBuffer, false);
/*  959 */       if (bool) {
/*  960 */         CtrySOPRecord ctrySOPRecord = new CtrySOPRecord(paramDiffEntity);
/*  961 */         ctrySOPRecord.setAction("Delete");
/*  962 */         paramVector.add(ctrySOPRecord);
/*      */         
/*  964 */         addGrpTbl(entityItem2, paramHashtable1, vector2, paramStringBuffer, false);
/*      */       }
/*      */     
/*  967 */     } else if (paramDiffEntity.isNew()) {
/*      */       
/*  969 */       boolean bool = checkSLEORGGRPAvailable(paramString2, entityItem1, paramString1, vector1, paramStringBuffer, true);
/*      */       
/*  971 */       if (bool) {
/*  972 */         CtrySOPRecord ctrySOPRecord = new CtrySOPRecord(paramDiffEntity);
/*  973 */         ctrySOPRecord.setAction("Update");
/*  974 */         paramVector.add(ctrySOPRecord);
/*      */         
/*  976 */         addGrpTbl(entityItem1, paramHashtable2, vector1, paramStringBuffer, true);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  983 */       boolean bool1 = checkSLEORGGRPAvailable(paramString2, entityItem2, paramString1, vector2, paramStringBuffer, false);
/*  984 */       boolean bool2 = checkSLEORGGRPAvailable(paramString2, entityItem1, paramString1, vector1, paramStringBuffer, true);
/*      */       
/*  986 */       if (bool1 && bool2) {
/*  987 */         CtrySOPRecord ctrySOPRecord = new CtrySOPRecord(paramDiffEntity);
/*  988 */         paramVector.add(ctrySOPRecord);
/*  989 */         if (paramBoolean) {
/*  990 */           ctrySOPRecord.setAction("Update");
/*      */         }
/*      */         
/*  993 */         addGrpTbl(entityItem2, paramHashtable1, vector2, paramStringBuffer, false);
/*  994 */         addGrpTbl(entityItem1, paramHashtable2, vector1, paramStringBuffer, true);
/*      */       } 
/*      */       
/*  997 */       if (bool1 == true && !bool2) {
/*  998 */         CtrySOPRecord ctrySOPRecord = new CtrySOPRecord(paramDiffEntity);
/*  999 */         ctrySOPRecord.setAction("Delete");
/* 1000 */         paramVector.add(ctrySOPRecord);
/*      */         
/* 1002 */         addGrpTbl(entityItem2, paramHashtable1, vector2, paramStringBuffer, false);
/*      */       } 
/*      */ 
/*      */       
/* 1006 */       if (!bool1 && bool2 == true) {
/* 1007 */         CtrySOPRecord ctrySOPRecord = new CtrySOPRecord(paramDiffEntity);
/* 1008 */         ctrySOPRecord.setAction("Update");
/* 1009 */         paramVector.add(ctrySOPRecord);
/*      */ 
/*      */         
/* 1012 */         addGrpTbl(entityItem1, paramHashtable2, vector1, paramStringBuffer, true);
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
/*      */   private boolean checkSLEORGGRPAvailable(String paramString1, EntityItem paramEntityItem, String paramString2, Vector paramVector, StringBuffer paramStringBuffer, boolean paramBoolean) {
/* 1031 */     boolean bool = false;
/*      */     
/* 1033 */     String str = "";
/* 1034 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("MODCATG");
/* 1035 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 1037 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1038 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 1040 */         if (arrayOfMetaFlag[b].isSelected()) {
/*      */           
/* 1042 */           str = arrayOfMetaFlag[b].toString();
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1050 */     if (paramString2 != null && paramString2.indexOf(",") > -1)
/* 1051 */     { if ("".equals(str)) return false; 
/* 1052 */       if (paramString2.indexOf(str) == -1) return false;
/*      */        }
/* 1054 */     else if (!str.equals(paramString2)) { return false; }
/*      */     
/* 1056 */     if (paramVector.size() > 0) {
/* 1057 */       EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)paramEntityItem.getAttribute("SLEORGGRP");
/*      */       
/* 1059 */       if (eANFlagAttribute1 != null) {
/* 1060 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute1.get();
/*      */         
/* 1062 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/* 1064 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 1065 */             String str1 = arrayOfMetaFlag[b].toString();
/*      */             
/* 1067 */             if (paramVector.contains(str1))
/*      */             {
/* 1069 */               if (!"@@".equals(paramString1)) {
/* 1070 */                 EANFlagAttribute eANFlagAttribute2 = (EANFlagAttribute)paramEntityItem.getAttribute("COUNTRYLIST");
/*      */                 
/* 1072 */                 if (eANFlagAttribute2 != null && eANFlagAttribute2.isSelected(paramString1)) {
/* 1073 */                   bool = true;
/*      */                   break;
/*      */                 } 
/*      */               } else {
/* 1077 */                 bool = true;
/*      */                 break;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 1084 */     } else if (!"@@".equals(paramString1)) {
/*      */       
/* 1086 */       EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)paramEntityItem.getAttribute("COUNTRYLIST");
/* 1087 */       if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(paramString1)) {
/* 1088 */         bool = true;
/*      */       }
/*      */     } 
/* 1091 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CtrySOPRecord
/*      */   {
/* 1100 */     private String action = "@@";
/*      */     
/* 1102 */     private Vector SLEORGGRP = new Vector();
/*      */     
/* 1104 */     private String SLEORG = "@@";
/*      */     
/* 1106 */     private String PLNTCD = "@@";
/*      */     
/*      */     private DiffEntity sopDiff;
/*      */     
/*      */     boolean isDisplayable() {
/* 1111 */       return !this.action.equals("@@");
/*      */     }
/*      */     
/*      */     public void dereference() {
/* 1115 */       this.action = null;
/* 1116 */       this.SLEORGGRP = null;
/* 1117 */       this.SLEORG = null;
/* 1118 */       this.PLNTCD = null;
/*      */     }
/*      */     
/*      */     CtrySOPRecord(DiffEntity param1DiffEntity) {
/* 1122 */       this.sopDiff = param1DiffEntity;
/*      */     }
/*      */     
/*      */     void setAction(String param1String) {
/* 1126 */       this.action = param1String;
/*      */     }
/*      */     
/*      */     public String getAction() {
/* 1130 */       return this.action;
/*      */     }
/*      */     
/*      */     public String getPLNTCD() {
/* 1134 */       return this.PLNTCD;
/*      */     }
/*      */     
/*      */     public String getSLEORG() {
/* 1138 */       return this.SLEORG;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setallFields(String param1String, StringBuffer param1StringBuffer) {
/* 1148 */       EntityItem entityItem1 = this.sopDiff.getCurrentEntityItem();
/* 1149 */       EntityItem entityItem2 = this.sopDiff.getPriorEntityItem();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1159 */       if (!"Delete".equals(this.action)) {
/* 1160 */         String str1 = "@@";
/* 1161 */         if (entityItem1 != null) {
/* 1162 */           this.PLNTCD = PokUtils.getAttributeValue(entityItem1, "PLNTCD", ", ", "@@", false);
/*      */         }
/* 1164 */         if (entityItem2 != null) {
/* 1165 */           str1 = PokUtils.getAttributeValue(entityItem2, "PLNTCD", ", ", "@@", false);
/*      */         }
/* 1167 */         if (!this.PLNTCD.equals(str1)) {
/* 1168 */           setAction("Update");
/*      */         }
/*      */         
/* 1171 */         String str2 = "@@";
/* 1172 */         if (entityItem1 != null) {
/* 1173 */           this.SLEORG = PokUtils.getAttributeValue(entityItem1, "SLEORG", ", ", "@@", false);
/*      */         }
/* 1175 */         if (entityItem2 != null) {
/* 1176 */           str2 = PokUtils.getAttributeValue(entityItem2, "SLEORG", ", ", "@@", false);
/*      */         }
/* 1178 */         if (!this.SLEORG.equals(str2)) {
/* 1179 */           setAction("Update");
/*      */         }
/* 1181 */         if (entityItem1 != null) {
/* 1182 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("SLEORGGRP");
/* 1183 */           if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */             
/* 1185 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1186 */             for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */               
/* 1188 */               if (arrayOfMetaFlag[b].isSelected()) {
/* 1189 */                 this.SLEORGGRP.add(arrayOfMetaFlag[b].toString());
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 1194 */         if (this.sopDiff.isDeleted() && 
/* 1195 */           entityItem2 != null) {
/* 1196 */           this.PLNTCD = PokUtils.getAttributeValue(entityItem2, "PLNTCD", ", ", "@@", false);
/* 1197 */           this.SLEORG = PokUtils.getAttributeValue(entityItem2, "SLEORG", ", ", "@@", false);
/* 1198 */           setAction("Delete");
/*      */         }
/*      */       
/*      */       }
/* 1202 */       else if (entityItem2 != null) {
/* 1203 */         this.PLNTCD = PokUtils.getAttributeValue(entityItem2, "PLNTCD", ", ", "@@", false);
/* 1204 */         this.SLEORG = PokUtils.getAttributeValue(entityItem2, "SLEORG", ", ", "@@", false);
/* 1205 */         setAction("Delete");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1212 */       return "action:" + this.action + " SLEORGGP:" + this.SLEORGGRP + " SLEORG:" + this.SLEORG + " PLNTCD:" + this.PLNTCD;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLSLEORGGRPElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */