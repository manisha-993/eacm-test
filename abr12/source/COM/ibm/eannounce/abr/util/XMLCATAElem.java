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
/*      */ public class XMLCATAElem
/*      */   extends XMLElem
/*      */ {
/*      */   private static final String DEFAULT_NO = "";
/*      */   private static final String DEFAULT_YES = "";
/*      */   
/*      */   public XMLCATAElem() {
/*   87 */     super("CATALOGOVERRIDEELEMENT");
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
/*      */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  106 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("BHCATLGOR");
/*  107 */     if (vector != null && vector.size() > 0) {
/*      */       
/*  109 */       Vector[] arrayOfVector = getModelAudience(paramDiffEntity, paramStringBuffer);
/*  110 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  111 */       for (byte b = 0; b < vector.size(); b++) {
/*  112 */         DiffEntity diffEntity = vector.elementAt(b);
/*      */         
/*  114 */         EntityItem entityItem = diffEntity.getCurrentEntityItem();
/*  115 */         String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  116 */         if ("0020".equals(str))
/*      */         {
/*      */           
/*  119 */           if (paramDiffEntity.getEntityType().equals("SWPRODSTRUCT")) {
/*      */             
/*  121 */             buildSWCtryAudRecs(treeMap, diffEntity, paramStringBuffer);
/*      */           } else {
/*      */             
/*  124 */             buildCtryAudRecs(treeMap, diffEntity, arrayOfVector, paramStringBuffer);
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  130 */       Collection collection = treeMap.values();
/*  131 */       Iterator<CtryAudRecord> iterator = collection.iterator();
/*  132 */       while (iterator.hasNext()) {
/*  133 */         CtryAudRecord ctryAudRecord = iterator.next();
/*  134 */         if (!ctryAudRecord.isDeleted()) {
/*      */ 
/*      */           
/*  137 */           ctryAudRecord.setAllFields(paramStringBuffer);
/*      */         } else {
/*      */           
/*  140 */           ctryAudRecord.addtocart = "@@";
/*  141 */           ctryAudRecord.buyable = "@@";
/*  142 */           ctryAudRecord.publish = "@@";
/*  143 */           ctryAudRecord.customizeable = "@@";
/*  144 */           ctryAudRecord.hide = "@@";
/*      */         } 
/*      */         
/*  147 */         if (ctryAudRecord.isDisplayable()) {
/*  148 */           createNodeSet(paramDocument, paramElement, ctryAudRecord, paramStringBuffer);
/*      */         } else {
/*  150 */           ABRUtil.append(paramStringBuffer, "XMLCATAElem.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*      */         } 
/*  152 */         ctryAudRecord.dereference();
/*      */       } 
/*      */ 
/*      */       
/*  156 */       treeMap.clear();
/*  157 */       arrayOfVector = null;
/*      */     } else {
/*  159 */       ABRUtil.append(paramStringBuffer, "XMLCATAElem.addElements no catlogor found" + NEWLINE);
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
/*      */   private void createNodeSet(Document paramDocument, Element paramElement, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/*  224 */     Element element1 = paramDocument.createElement(this.nodeName);
/*  225 */     addXMLAttrs(element1);
/*  226 */     paramElement.appendChild(element1);
/*      */ 
/*      */     
/*  229 */     Element element2 = paramDocument.createElement("CATALOGOVERRIDEACTION");
/*  230 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getAction()));
/*  231 */     element1.appendChild(element2);
/*  232 */     element2 = paramDocument.createElement("AUDIENCE");
/*  233 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getAudience()));
/*  234 */     element1.appendChild(element2);
/*  235 */     element2 = paramDocument.createElement("COUNTRY_FC");
/*  236 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getCountry()));
/*  237 */     element1.appendChild(element2);
/*  238 */     element2 = paramDocument.createElement("PUBFROM");
/*  239 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getPubFrom()));
/*  240 */     element1.appendChild(element2);
/*  241 */     element2 = paramDocument.createElement("PUBTO");
/*  242 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getPubTo()));
/*  243 */     element1.appendChild(element2);
/*  244 */     element2 = paramDocument.createElement("ADDTOCART");
/*  245 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getAddToCart()));
/*  246 */     element1.appendChild(element2);
/*  247 */     element2 = paramDocument.createElement("BUYABLE");
/*  248 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getBuyable()));
/*  249 */     element1.appendChild(element2);
/*  250 */     element2 = paramDocument.createElement("PUBLISH");
/*  251 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getPublish()));
/*  252 */     element1.appendChild(element2);
/*  253 */     element2 = paramDocument.createElement("CUSTOMIZEABLE");
/*  254 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getCustomizeable()));
/*  255 */     element1.appendChild(element2);
/*  256 */     element2 = paramDocument.createElement("HIDE");
/*  257 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getHide()));
/*  258 */     element1.appendChild(element2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector[] getModelAudience(DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  265 */     ABRUtil.append(paramStringBuffer, "XMLCATAElem.getModelAudience for " + paramDiffEntity.getKey() + NEWLINE);
/*      */     
/*  267 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramDiffEntity.getCurrentEntityItem().getAttribute("AUDIEN");
/*  268 */     Vector<String> vector1 = new Vector(1);
/*  269 */     Vector<String> vector2 = new Vector(1);
/*  270 */     Vector[] arrayOfVector = new Vector[2];
/*  271 */     arrayOfVector[0] = vector1;
/*  272 */     arrayOfVector[1] = vector2;
/*  273 */     ABRUtil.append(paramStringBuffer, "XMLCATAElem.getModelAudience cur audienceAtt " + eANFlagAttribute + NEWLINE);
/*  274 */     if (eANFlagAttribute != null) {
/*  275 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  276 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/*  278 */         if (arrayOfMetaFlag[b].isSelected()) {
/*  279 */           vector1.addElement(arrayOfMetaFlag[b].toString());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  284 */     if (!paramDiffEntity.isNew()) {
/*  285 */       eANFlagAttribute = (EANFlagAttribute)paramDiffEntity.getPriorEntityItem().getAttribute("AUDIEN");
/*  286 */       ABRUtil.append(paramStringBuffer, "XMLCATAElem.getModelAudience new audienceAtt " + eANFlagAttribute + NEWLINE);
/*  287 */       if (eANFlagAttribute != null) {
/*  288 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  289 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  291 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  292 */             vector2.addElement(arrayOfMetaFlag[b].toString());
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  298 */     return arrayOfVector;
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
/*      */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, Vector[] paramArrayOfVector, StringBuffer paramStringBuffer) {
/*  316 */     String str1 = "CATAUDIENCE";
/*      */     
/*  318 */     String str2 = "COUNTRYLIST";
/*  319 */     Vector vector1 = paramArrayOfVector[0];
/*  320 */     Vector vector2 = paramArrayOfVector[1];
/*      */     
/*  322 */     ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs " + paramDiffEntity.getKey() + " currAudVct:" + vector1 + " prevAudVct:" + vector2 + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  329 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  330 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*      */     
/*  332 */     if (paramDiffEntity.isDeleted()) {
/*      */       
/*  334 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str2);
/*  335 */       ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for deleted catlgor: ctryAtt " + 
/*  336 */           PokUtils.getAttributeFlagValue(entityItem2, str2) + NEWLINE);
/*      */       
/*  338 */       if (eANFlagAttribute != null) {
/*  339 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  340 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  342 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  343 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  344 */             EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute(str1);
/*  345 */             if (eANFlagAttribute1 != null && eANFlagAttribute1.toString().length() > 0) {
/*      */               
/*  347 */               MetaFlag[] arrayOfMetaFlag1 = (MetaFlag[])eANFlagAttribute1.get();
/*  348 */               for (byte b1 = 0; b1 < arrayOfMetaFlag1.length; b1++) {
/*  349 */                 String str3 = arrayOfMetaFlag1[b1].toString();
/*  350 */                 if (arrayOfMetaFlag1[b1].isSelected() && vector2.contains(str3)) {
/*  351 */                   String str4 = str + "|" + str3;
/*  352 */                   if (paramTreeMap.containsKey(str4)) {
/*      */                     
/*  354 */                     CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str4);
/*  355 */                     ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted " + paramDiffEntity.getKey() + " " + str4 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */                   } else {
/*      */                     
/*  358 */                     CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str3);
/*  359 */                     ctryAudRecord.setAction("Delete");
/*  360 */                     paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  361 */                     ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  362 */                         .getKey() + NEWLINE);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  371 */     } else if (paramDiffEntity.isNew()) {
/*  372 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str2);
/*  373 */       ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for new catlgor: ctryAtt " + 
/*  374 */           PokUtils.getAttributeFlagValue(entityItem1, str2) + NEWLINE);
/*      */       
/*  376 */       if (eANFlagAttribute != null) {
/*  377 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  378 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  380 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  381 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  382 */             EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem1.getAttribute(str1);
/*  383 */             if (eANFlagAttribute1 != null && eANFlagAttribute1.toString().length() > 0) {
/*      */               
/*  385 */               MetaFlag[] arrayOfMetaFlag1 = (MetaFlag[])eANFlagAttribute1.get();
/*  386 */               for (byte b1 = 0; b1 < arrayOfMetaFlag1.length; b1++) {
/*  387 */                 String str3 = arrayOfMetaFlag1[b1].toString();
/*  388 */                 ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for new catlgor: audience=" + str3 + NEWLINE);
/*  389 */                 if (arrayOfMetaFlag1[b1].isSelected() && vector1.contains(str3)) {
/*  390 */                   String str4 = str + "|" + str3;
/*  391 */                   if (paramTreeMap.containsKey(str4)) {
/*      */                     
/*  393 */                     CtryAudRecord ctryAudRecord = paramTreeMap.get(str4);
/*  394 */                     ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new " + paramDiffEntity.getKey() + " " + str4 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */                     
/*  396 */                     ctryAudRecord.setUpdateCatlgor(paramDiffEntity);
/*      */                   } else {
/*  398 */                     CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str3);
/*  399 */                     ctryAudRecord.setAction("Update");
/*  400 */                     paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  401 */                     ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  402 */                         .getKey() + NEWLINE);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  413 */       HashSet<String> hashSet1 = new HashSet();
/*  414 */       HashSet<String> hashSet2 = new HashSet();
/*      */       
/*  416 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str2);
/*  417 */       ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for current catlgor: ctryAtt " + 
/*  418 */           PokUtils.getAttributeFlagValue(entityItem1, str2) + NEWLINE);
/*      */       
/*  420 */       if (eANFlagAttribute != null) {
/*  421 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  422 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  424 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  425 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  426 */             EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem1.getAttribute(str1);
/*  427 */             if (eANFlagAttribute1 != null && eANFlagAttribute1.toString().length() > 0) {
/*      */               
/*  429 */               MetaFlag[] arrayOfMetaFlag1 = (MetaFlag[])eANFlagAttribute1.get();
/*  430 */               for (byte b1 = 0; b1 < arrayOfMetaFlag1.length; b1++) {
/*  431 */                 String str3 = arrayOfMetaFlag1[b1].toString();
/*  432 */                 if (arrayOfMetaFlag1[b1].isSelected() && vector1.contains(str3)) {
/*  433 */                   String str4 = str + "|" + str3;
/*  434 */                   hashSet2.add(str4);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  443 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str2);
/*  444 */       ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for prior catlgor: ctryAtt " + 
/*  445 */           PokUtils.getAttributeFlagValue(entityItem2, str2) + NEWLINE);
/*      */       
/*  447 */       if (eANFlagAttribute != null) {
/*  448 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  449 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  451 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  452 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  453 */             EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute(str1);
/*  454 */             if (eANFlagAttribute1 != null && eANFlagAttribute1.toString().length() > 0) {
/*      */               
/*  456 */               MetaFlag[] arrayOfMetaFlag1 = (MetaFlag[])eANFlagAttribute1.get();
/*  457 */               for (byte b1 = 0; b1 < arrayOfMetaFlag1.length; b1++) {
/*  458 */                 String str3 = arrayOfMetaFlag1[b1].toString();
/*  459 */                 if (arrayOfMetaFlag1[b1].isSelected() && vector2.contains(str3)) {
/*  460 */                   String str4 = str + "|" + str3;
/*  461 */                   hashSet1.add(str4);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  470 */       Iterator<String> iterator = hashSet2.iterator();
/*  471 */       while (iterator.hasNext()) {
/*  472 */         String str = iterator.next();
/*  473 */         if (!hashSet1.contains(str)) {
/*      */           
/*  475 */           if (paramTreeMap.containsKey(str)) {
/*  476 */             CtryAudRecord ctryAudRecord2 = paramTreeMap.get(str);
/*  477 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for added ctryAudience on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryAudRecord2 + NEWLINE);
/*      */             
/*  479 */             ctryAudRecord2.setUpdateCatlgor(paramDiffEntity); continue;
/*      */           } 
/*  481 */           StringTokenizer stringTokenizer1 = new StringTokenizer(str, "|");
/*  482 */           String[] arrayOfString1 = new String[2];
/*  483 */           arrayOfString1[0] = stringTokenizer1.nextToken();
/*  484 */           arrayOfString1[1] = stringTokenizer1.nextToken();
/*  485 */           CtryAudRecord ctryAudRecord1 = new CtryAudRecord(paramDiffEntity, arrayOfString1[0], arrayOfString1[1]);
/*  486 */           ctryAudRecord1.setAction("Update");
/*  487 */           paramTreeMap.put(ctryAudRecord1.getKey(), ctryAudRecord1);
/*  488 */           ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for added ctryAudience:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord1
/*  489 */               .getKey() + NEWLINE);
/*      */           
/*      */           continue;
/*      */         } 
/*  493 */         if (paramTreeMap.containsKey(str)) {
/*  494 */           CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str);
/*  495 */           ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for existing ctryAudience on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */           continue;
/*      */         } 
/*  498 */         StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  499 */         String[] arrayOfString = new String[2];
/*  500 */         arrayOfString[0] = stringTokenizer.nextToken();
/*  501 */         arrayOfString[1] = stringTokenizer.nextToken();
/*  502 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, arrayOfString[0], arrayOfString[1]);
/*  503 */         paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  504 */         ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for existing ctryAudience:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  505 */             .getKey() + NEWLINE);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  510 */       iterator = hashSet1.iterator();
/*  511 */       while (iterator.hasNext()) {
/*  512 */         String str = iterator.next();
/*  513 */         if (!hashSet2.contains(str)) {
/*      */           
/*  515 */           if (paramTreeMap.containsKey(str)) {
/*  516 */             CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str);
/*  517 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for delete ctryaudi on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */             continue;
/*      */           } 
/*  520 */           StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  521 */           String[] arrayOfString = new String[2];
/*  522 */           arrayOfString[0] = stringTokenizer.nextToken();
/*  523 */           arrayOfString[1] = stringTokenizer.nextToken();
/*  524 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, arrayOfString[0], arrayOfString[1]);
/*  525 */           ctryAudRecord.setAction("Delete");
/*  526 */           paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  527 */           ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for deleted ctryAudience:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  528 */               .getKey() + NEWLINE);
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
/*      */   private void buildSWCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  553 */     String str1 = "CATAUDIENCE";
/*      */     
/*  555 */     String str2 = "COUNTRYLIST";
/*      */ 
/*      */     
/*  558 */     ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs " + paramDiffEntity.getKey() + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  564 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  565 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*      */     
/*  567 */     if (paramDiffEntity.isDeleted()) {
/*      */       
/*  569 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str2);
/*  570 */       ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for deleted catlgor: ctryAtt " + 
/*  571 */           PokUtils.getAttributeFlagValue(entityItem2, str2) + NEWLINE);
/*      */       
/*  573 */       if (eANFlagAttribute != null) {
/*  574 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  575 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  577 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  578 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  579 */             EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute(str1);
/*  580 */             if (eANFlagAttribute1 != null && eANFlagAttribute1.toString().length() > 0) {
/*      */               
/*  582 */               MetaFlag[] arrayOfMetaFlag1 = (MetaFlag[])eANFlagAttribute1.get();
/*  583 */               for (byte b1 = 0; b1 < arrayOfMetaFlag1.length; b1++) {
/*  584 */                 String str3 = arrayOfMetaFlag1[b1].toString();
/*  585 */                 if (arrayOfMetaFlag1[b1].isSelected()) {
/*  586 */                   String str4 = str + "|" + str3;
/*  587 */                   if (paramTreeMap.containsKey(str4)) {
/*      */                     
/*  589 */                     CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str4);
/*  590 */                     ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted " + paramDiffEntity.getKey() + " " + str4 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */                   } else {
/*      */                     
/*  593 */                     CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str3);
/*  594 */                     ctryAudRecord.setAction("Delete");
/*  595 */                     paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  596 */                     ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  597 */                         .getKey() + NEWLINE);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  606 */     } else if (paramDiffEntity.isNew()) {
/*  607 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str2);
/*  608 */       ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for new catlgor: ctryAtt " + 
/*  609 */           PokUtils.getAttributeFlagValue(entityItem1, str2) + NEWLINE);
/*      */       
/*  611 */       if (eANFlagAttribute != null) {
/*  612 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  613 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  615 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  616 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  617 */             EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem1.getAttribute(str1);
/*  618 */             if (eANFlagAttribute1 != null && eANFlagAttribute1.toString().length() > 0) {
/*      */               
/*  620 */               MetaFlag[] arrayOfMetaFlag1 = (MetaFlag[])eANFlagAttribute1.get();
/*  621 */               for (byte b1 = 0; b1 < arrayOfMetaFlag1.length; b1++) {
/*  622 */                 String str3 = arrayOfMetaFlag1[b1].toString();
/*  623 */                 if (arrayOfMetaFlag1[b1].isSelected()) {
/*  624 */                   String str4 = str + "|" + str3;
/*  625 */                   if (paramTreeMap.containsKey(str4)) {
/*      */                     
/*  627 */                     CtryAudRecord ctryAudRecord = paramTreeMap.get(str4);
/*  628 */                     ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new " + paramDiffEntity.getKey() + " " + str4 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */                     
/*  630 */                     ctryAudRecord.setUpdateCatlgor(paramDiffEntity);
/*      */                   } else {
/*  632 */                     CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str3);
/*  633 */                     ctryAudRecord.setAction("Update");
/*  634 */                     paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  635 */                     ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  636 */                         .getKey() + NEWLINE);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  647 */       HashSet<String> hashSet1 = new HashSet();
/*  648 */       HashSet<String> hashSet2 = new HashSet();
/*      */       
/*  650 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str2);
/*  651 */       ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for current catlgor: ctryAtt " + 
/*  652 */           PokUtils.getAttributeFlagValue(entityItem1, str2) + NEWLINE);
/*      */       
/*  654 */       if (eANFlagAttribute != null) {
/*  655 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  656 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  658 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  659 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  660 */             EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem1.getAttribute(str1);
/*  661 */             if (eANFlagAttribute1 != null && eANFlagAttribute1.toString().length() > 0) {
/*      */               
/*  663 */               MetaFlag[] arrayOfMetaFlag1 = (MetaFlag[])eANFlagAttribute1.get();
/*  664 */               for (byte b1 = 0; b1 < arrayOfMetaFlag1.length; b1++) {
/*  665 */                 String str3 = arrayOfMetaFlag1[b1].toString();
/*  666 */                 if (arrayOfMetaFlag1[b1].isSelected()) {
/*  667 */                   String str4 = str + "|" + str3;
/*  668 */                   hashSet2.add(str4);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  677 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str2);
/*  678 */       ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for prior catlgor: ctryAtt " + 
/*  679 */           PokUtils.getAttributeFlagValue(entityItem2, str2) + NEWLINE);
/*      */       
/*  681 */       if (eANFlagAttribute != null) {
/*  682 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  683 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  685 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  686 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*  687 */             EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute(str1);
/*  688 */             if (eANFlagAttribute1 != null && eANFlagAttribute1.toString().length() > 0) {
/*      */               
/*  690 */               MetaFlag[] arrayOfMetaFlag1 = (MetaFlag[])eANFlagAttribute1.get();
/*  691 */               for (byte b1 = 0; b1 < arrayOfMetaFlag1.length; b1++) {
/*  692 */                 String str3 = arrayOfMetaFlag1[b1].toString();
/*  693 */                 if (arrayOfMetaFlag1[b1].isSelected()) {
/*  694 */                   String str4 = str + "|" + str3;
/*  695 */                   hashSet1.add(str4);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  704 */       Iterator<String> iterator = hashSet2.iterator();
/*  705 */       while (iterator.hasNext()) {
/*  706 */         String str = iterator.next();
/*  707 */         if (!hashSet1.contains(str)) {
/*      */           
/*  709 */           if (paramTreeMap.containsKey(str)) {
/*  710 */             CtryAudRecord ctryAudRecord2 = paramTreeMap.get(str);
/*  711 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for added ctryAudience on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryAudRecord2 + NEWLINE);
/*      */             
/*  713 */             ctryAudRecord2.setUpdateCatlgor(paramDiffEntity); continue;
/*      */           } 
/*  715 */           StringTokenizer stringTokenizer1 = new StringTokenizer(str, "|");
/*  716 */           String[] arrayOfString1 = new String[2];
/*  717 */           arrayOfString1[0] = stringTokenizer1.nextToken();
/*  718 */           arrayOfString1[1] = stringTokenizer1.nextToken();
/*  719 */           CtryAudRecord ctryAudRecord1 = new CtryAudRecord(paramDiffEntity, arrayOfString1[0], arrayOfString1[1]);
/*  720 */           ctryAudRecord1.setAction("Update");
/*  721 */           paramTreeMap.put(ctryAudRecord1.getKey(), ctryAudRecord1);
/*  722 */           ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for added ctryAudience:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord1
/*  723 */               .getKey() + NEWLINE);
/*      */           
/*      */           continue;
/*      */         } 
/*  727 */         if (paramTreeMap.containsKey(str)) {
/*  728 */           CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str);
/*  729 */           ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for existing ctryAudience on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */           continue;
/*      */         } 
/*  732 */         StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  733 */         String[] arrayOfString = new String[2];
/*  734 */         arrayOfString[0] = stringTokenizer.nextToken();
/*  735 */         arrayOfString[1] = stringTokenizer.nextToken();
/*  736 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, arrayOfString[0], arrayOfString[1]);
/*  737 */         paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  738 */         ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for existing ctryAudience:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  739 */             .getKey() + NEWLINE);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  744 */       iterator = hashSet1.iterator();
/*  745 */       while (iterator.hasNext()) {
/*  746 */         String str = iterator.next();
/*  747 */         if (!hashSet2.contains(str)) {
/*      */           
/*  749 */           if (paramTreeMap.containsKey(str)) {
/*  750 */             CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str);
/*  751 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for delete ctryaudi on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*      */             continue;
/*      */           } 
/*  754 */           StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/*  755 */           String[] arrayOfString = new String[2];
/*  756 */           arrayOfString[0] = stringTokenizer.nextToken();
/*  757 */           arrayOfString[1] = stringTokenizer.nextToken();
/*  758 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, arrayOfString[0], arrayOfString[1]);
/*  759 */           ctryAudRecord.setAction("Delete");
/*  760 */           paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  761 */           ABRUtil.append(paramStringBuffer, "XMLCATAElem.buildCtryAudRecs for deleted ctryAudience:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  762 */               .getKey() + NEWLINE);
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
/*      */   private static class CtryAudRecord
/*      */   {
/*  783 */     private String action = null;
/*      */     
/*      */     private String audience;
/*      */     
/*      */     private String country;
/*      */     
/*  789 */     private String pubfrom = "@@";
/*      */     
/*  791 */     private String pubto = "@@";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  801 */     private String addtocart = "";
/*      */     
/*  803 */     private String buyable = "";
/*      */     
/*  805 */     private String publish = "";
/*      */     
/*  807 */     private String customizeable = "";
/*      */     
/*  809 */     private String hide = "";
/*      */     
/*      */     private DiffEntity catlgorDiff;
/*      */     
/*      */     boolean isDisplayable() {
/*  814 */       return (this.action != null);
/*      */     }
/*      */     
/*      */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String1, String param1String2) {
/*  818 */       this.audience = param1String2;
/*  819 */       this.catlgorDiff = param1DiffEntity;
/*  820 */       this.country = param1String1;
/*      */     }
/*      */     
/*      */     void setAction(String param1String) {
/*  824 */       this.action = param1String;
/*      */     }
/*      */     
/*      */     void setUpdateCatlgor(DiffEntity param1DiffEntity) {
/*  828 */       this.catlgorDiff = param1DiffEntity;
/*  829 */       setAction("Update");
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
/*      */     void setAllFields(StringBuffer param1StringBuffer) {
/*  845 */       if (this.catlgorDiff != null) {
/*      */         
/*  847 */         ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.catlgorDiff.getKey() + XMLElem.NEWLINE);
/*  848 */         EntityItem entityItem1 = this.catlgorDiff.getCurrentEntityItem();
/*  849 */         EntityItem entityItem2 = this.catlgorDiff.getPriorEntityItem();
/*      */         
/*  851 */         if (entityItem1 != null) {
/*  852 */           this.pubfrom = PokUtils.getAttributeValue(entityItem1, "PUBFROM", "", "@@", false);
/*      */         }
/*  854 */         String str1 = "@@";
/*  855 */         if (entityItem2 != null) {
/*  856 */           str1 = PokUtils.getAttributeValue(entityItem2, "PUBFROM", ", ", "@@", false);
/*      */         }
/*  858 */         ABRUtil.append(param1StringBuffer, "XMLCATAElem.setAllFields pubfrom: " + this.pubfrom + " prevdate: " + str1 + XMLElem.NEWLINE);
/*      */ 
/*      */         
/*  861 */         if (!str1.equals(this.pubfrom)) {
/*  862 */           setAction("Update");
/*      */         }
/*      */         
/*  865 */         if (entityItem1 != null) {
/*  866 */           this.pubto = PokUtils.getAttributeValue(entityItem1, "PUBTO", "", "@@", false);
/*      */         }
/*  868 */         String str2 = "@@";
/*  869 */         if (entityItem2 != null) {
/*  870 */           str2 = PokUtils.getAttributeValue(entityItem2, "PUBTO", ", ", "@@", false);
/*      */         }
/*  872 */         ABRUtil.append(param1StringBuffer, "XMLCATAElem.setAllFields pubto: " + this.pubto + " prevdate: " + str2 + XMLElem.NEWLINE);
/*      */ 
/*      */         
/*  875 */         if (!str2.equals(this.pubto)) {
/*  876 */           setAction("Update");
/*      */         }
/*      */         
/*  879 */         if (entityItem1 != null) {
/*  880 */           this.addtocart = PokUtils.getAttributeValue(entityItem1, "CATADDTOCART", ", ", "", false);
/*      */         }
/*  882 */         String str3 = "";
/*  883 */         if (entityItem2 != null) {
/*  884 */           str3 = PokUtils.getAttributeValue(entityItem2, "CATADDTOCART", ", ", "", false);
/*      */         }
/*  886 */         ABRUtil.append(param1StringBuffer, "XMLCATAElem.setAllFields addtocart: " + this.addtocart + " prevaddtocart: " + str3 + XMLElem.NEWLINE);
/*      */ 
/*      */ 
/*      */         
/*  890 */         if (!str3.equals(this.addtocart)) {
/*  891 */           setAction("Update");
/*      */         }
/*      */         
/*  894 */         if (entityItem1 != null) {
/*  895 */           this.buyable = PokUtils.getAttributeValue(entityItem1, "CATBUYABLE", ", ", "", false);
/*      */         }
/*  897 */         String str4 = "";
/*  898 */         if (entityItem2 != null) {
/*  899 */           str4 = PokUtils.getAttributeValue(entityItem2, "CATBUYABLE", ", ", "", false);
/*      */         }
/*  901 */         ABRUtil.append(param1StringBuffer, "XMLCATAElem.setAllFields buyable: " + this.buyable + " prevbuyable: " + str4 + XMLElem.NEWLINE);
/*      */ 
/*      */         
/*  904 */         if (!str4.equals(this.buyable)) {
/*  905 */           setAction("Update");
/*      */         }
/*      */         
/*  908 */         if (entityItem1 != null) {
/*  909 */           this.publish = PokUtils.getAttributeValue(entityItem1, "CATPUBLISH", ", ", "", false);
/*      */         }
/*  911 */         String str5 = "";
/*  912 */         if (entityItem2 != null) {
/*  913 */           str5 = PokUtils.getAttributeValue(entityItem2, "CATPUBLISH", ", ", "", false);
/*      */         }
/*  915 */         ABRUtil.append(param1StringBuffer, "XMLCATAElem.setAllFields publish: " + this.publish + " prevpublish: " + str5 + XMLElem.NEWLINE);
/*      */ 
/*      */         
/*  918 */         if (!str5.equals(this.publish)) {
/*  919 */           setAction("Update");
/*      */         }
/*      */         
/*  922 */         if (entityItem1 != null) {
/*  923 */           this.customizeable = PokUtils.getAttributeValue(entityItem1, "CATCUSTIMIZE", ", ", "", false);
/*      */         }
/*  925 */         String str6 = "";
/*  926 */         if (entityItem2 != null) {
/*  927 */           str6 = PokUtils.getAttributeValue(entityItem2, "CATCUSTIMIZE", ", ", "", false);
/*      */         }
/*  929 */         ABRUtil.append(param1StringBuffer, "XMLCATAElem.setAllFields customizeable: " + this.customizeable + " prevcustomizeable: " + str6 + XMLElem.NEWLINE);
/*      */ 
/*      */ 
/*      */         
/*  933 */         if (!str6.equals(this.customizeable)) {
/*  934 */           setAction("Update");
/*      */         }
/*      */         
/*  937 */         if (entityItem1 != null) {
/*  938 */           this.hide = PokUtils.getAttributeValue(entityItem1, "CATHIDE", ", ", "", false);
/*      */         }
/*  940 */         String str7 = "";
/*  941 */         if (entityItem2 != null) {
/*  942 */           str7 = PokUtils.getAttributeValue(entityItem2, "CATHIDE", ", ", "", false);
/*      */         }
/*  944 */         ABRUtil.append(param1StringBuffer, "XMLCATAElem.setAllFields hide: " + this.hide + " prevhide: " + str7 + XMLElem.NEWLINE);
/*      */ 
/*      */         
/*  947 */         if (!str7.equals(this.hide)) {
/*  948 */           setAction("Update");
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     String getAction() {
/*  954 */       return this.action;
/*      */     }
/*      */     
/*      */     String getAudience() {
/*  958 */       return this.audience;
/*      */     }
/*      */     
/*      */     String getCountry() {
/*  962 */       return this.country;
/*      */     }
/*      */     
/*      */     String getPubFrom() {
/*  966 */       return this.pubfrom;
/*      */     }
/*      */     
/*      */     String getPubTo() {
/*  970 */       return this.pubto;
/*      */     }
/*      */     
/*      */     String getAddToCart() {
/*  974 */       return this.addtocart;
/*      */     }
/*      */     
/*      */     String getBuyable() {
/*  978 */       return this.buyable;
/*      */     }
/*      */     
/*      */     String getPublish() {
/*  982 */       return this.publish;
/*      */     }
/*      */     
/*      */     String getCustomizeable() {
/*  986 */       return this.customizeable;
/*      */     }
/*      */     
/*      */     String getHide() {
/*  990 */       return this.hide;
/*      */     }
/*      */     
/*      */     boolean isDeleted() {
/*  994 */       return "Delete".equals(this.action);
/*      */     }
/*      */     
/*      */     String getKey() {
/*  998 */       return this.country + "|" + this.audience;
/*      */     }
/*      */     
/*      */     void dereference() {
/* 1002 */       this.action = null;
/* 1003 */       this.audience = null;
/* 1004 */       this.country = null;
/* 1005 */       this.pubfrom = null;
/* 1006 */       this.pubto = null;
/* 1007 */       this.addtocart = null;
/* 1008 */       this.buyable = null;
/* 1009 */       this.publish = null;
/* 1010 */       this.customizeable = null;
/* 1011 */       this.hide = null;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1015 */       return this.catlgorDiff.getKey() + " " + getKey() + " action:" + this.action;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLCATAElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */