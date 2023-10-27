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
/*      */ public class XMLCtryAudElem
/*      */   extends XMLElem
/*      */ {
/*      */   private static final String DEFAULT_NO = "No";
/*      */   private static final String DEFAULT_YES = "Yes";
/*      */   
/*      */   public XMLCtryAudElem() {
/*   89 */     super("COUNTRYAUDIENCEELEMENT");
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
/*      */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  130 */     Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, paramStringBuffer);
/*      */     
/*  132 */     if (vector.size() > 0) {
/*      */       
/*  134 */       Vector[] arrayOfVector = getModelAudience(paramDiffEntity, paramStringBuffer);
/*      */ 
/*      */       
/*  137 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  138 */       for (byte b = 0; b < vector.size(); b++) {
/*  139 */         DiffEntity diffEntity = vector.elementAt(b);
/*      */ 
/*      */         
/*  142 */         buildCtryAudRecs(treeMap, diffEntity, arrayOfVector, paramStringBuffer);
/*      */       } 
/*      */ 
/*      */       
/*  146 */       Collection collection = treeMap.values();
/*  147 */       Iterator<CtryAudRecord> iterator = collection.iterator();
/*  148 */       while (iterator.hasNext()) {
/*  149 */         CtryAudRecord ctryAudRecord = iterator.next();
/*      */         
/*  151 */         if (!ctryAudRecord.isDeleted()) {
/*      */           
/*  153 */           DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/*  154 */               .getCountry(), paramStringBuffer);
/*      */           
/*  156 */           DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/*  157 */               .getCountry(), paramStringBuffer);
/*      */           
/*  159 */           DiffEntity diffEntity3 = getCatlgor(paramHashtable, ctryAudRecord.getAudience(), ctryAudRecord.getCountry(), paramStringBuffer);
/*      */ 
/*      */           
/*  162 */           ctryAudRecord.setAllFields(diffEntity1, diffEntity2, diffEntity3, paramStringBuffer);
/*      */         } 
/*  164 */         if (ctryAudRecord.isDisplayable()) {
/*  165 */           createNodeSet(paramDocument, paramElement, ctryAudRecord, paramStringBuffer);
/*      */         } else {
/*  167 */           paramStringBuffer.append("XMLCtryAudElem.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*      */         } 
/*  169 */         ctryAudRecord.dereference();
/*      */       } 
/*      */ 
/*      */       
/*  173 */       treeMap.clear();
/*  174 */       arrayOfVector = null;
/*      */     } else {
/*  176 */       paramStringBuffer.append("XMLCtryAudElem.addElements no planned AVAILs found" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeSet(Document paramDocument, Element paramElement, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/*  185 */     Element element1 = paramDocument.createElement(this.nodeName);
/*  186 */     addXMLAttrs(element1);
/*  187 */     paramElement.appendChild(element1);
/*      */ 
/*      */     
/*  190 */     Element element2 = paramDocument.createElement("COUNTRYAUDIENCEACTION");
/*  191 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getAction()));
/*  192 */     element1.appendChild(element2);
/*  193 */     element2 = paramDocument.createElement("COUNTRY");
/*  194 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getCountry()));
/*  195 */     element1.appendChild(element2);
/*  196 */     element2 = paramDocument.createElement("STATUS");
/*  197 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getAvailStatus()));
/*  198 */     element1.appendChild(element2);
/*  199 */     element2 = paramDocument.createElement("AUDIENCE");
/*  200 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getAudience()));
/*  201 */     element1.appendChild(element2);
/*  202 */     element2 = paramDocument.createElement("EARLIESTSHIPDATE");
/*  203 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getShipDate()));
/*  204 */     element1.appendChild(element2);
/*  205 */     element2 = paramDocument.createElement("PUBFROM");
/*  206 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getPubFrom()));
/*  207 */     element1.appendChild(element2);
/*  208 */     element2 = paramDocument.createElement("PUBTO");
/*  209 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getPubTo()));
/*  210 */     element1.appendChild(element2);
/*  211 */     element2 = paramDocument.createElement("ADDTOCART");
/*  212 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getAddToCart()));
/*  213 */     element1.appendChild(element2);
/*  214 */     element2 = paramDocument.createElement("BUYABLE");
/*  215 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getBuyable()));
/*  216 */     element1.appendChild(element2);
/*  217 */     element2 = paramDocument.createElement("PUBLISH");
/*  218 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getPublish()));
/*  219 */     element1.appendChild(element2);
/*  220 */     element2 = paramDocument.createElement("CUSTOMIZEABLE");
/*  221 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getCustomizeable()));
/*  222 */     element1.appendChild(element2);
/*  223 */     element2 = paramDocument.createElement("HIDE");
/*  224 */     element2.appendChild(paramDocument.createTextNode(paramCtryAudRecord.getHide()));
/*  225 */     element1.appendChild(element2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector[] getModelAudience(DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  232 */     paramStringBuffer.append("XMLCtryAudElem.getModelAudience for " + paramDiffEntity.getKey() + NEWLINE);
/*      */     
/*  234 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramDiffEntity.getCurrentEntityItem().getAttribute("AUDIEN");
/*  235 */     Vector<String> vector1 = new Vector(1);
/*  236 */     Vector<String> vector2 = new Vector(1);
/*  237 */     Vector[] arrayOfVector = new Vector[2];
/*  238 */     arrayOfVector[0] = vector1;
/*  239 */     arrayOfVector[1] = vector2;
/*  240 */     paramStringBuffer.append("XMLCtryAudElem.getModelAudience cur audienceAtt " + eANFlagAttribute + NEWLINE);
/*  241 */     if (eANFlagAttribute != null) {
/*  242 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  243 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/*  245 */         if (arrayOfMetaFlag[b].isSelected()) {
/*  246 */           vector1.addElement(arrayOfMetaFlag[b].toString());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  251 */     if (!paramDiffEntity.isNew()) {
/*  252 */       eANFlagAttribute = (EANFlagAttribute)paramDiffEntity.getPriorEntityItem().getAttribute("AUDIEN");
/*  253 */       paramStringBuffer.append("XMLCtryAudElem.getModelAudience new audienceAtt " + eANFlagAttribute + NEWLINE);
/*  254 */       if (eANFlagAttribute != null) {
/*  255 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  256 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  258 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  259 */             vector2.addElement(arrayOfMetaFlag[b].toString());
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  265 */     return arrayOfVector;
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
/*  283 */     Vector<E> vector1 = paramArrayOfVector[0];
/*  284 */     Vector<E> vector2 = paramArrayOfVector[1];
/*      */     
/*  286 */     paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs " + paramDiffEntity.getKey() + " currAudVct:" + vector1 + " prevAudVct:" + vector2 + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  293 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/*  294 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*  295 */     if (paramDiffEntity.isDeleted()) {
/*      */       
/*  297 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  298 */       paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for deleted avail: ctryAtt " + 
/*  299 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/*  300 */       if (eANFlagAttribute != null) {
/*  301 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  302 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  304 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  305 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*      */             
/*  307 */             for (byte b1 = 0; b1 < vector2.size(); b1++) {
/*  308 */               String str1 = vector2.elementAt(b1).toString();
/*  309 */               String str2 = str + "|" + str1;
/*  310 */               if (paramTreeMap.containsKey(str2)) {
/*      */                 
/*  312 */                 CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/*  313 */                 paramStringBuffer.append("WARNING buildCtryAudRecs for deleted " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */               } else {
/*      */                 
/*  316 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str1);
/*  317 */                 ctryAudRecord.setAction("Delete");
/*  318 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  319 */                 paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  320 */                     .getKey() + NEWLINE);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  326 */     } else if (paramDiffEntity.isNew()) {
/*      */       
/*  328 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  329 */       paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for new avail: ctryAtt " + 
/*  330 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/*  331 */       if (eANFlagAttribute != null) {
/*  332 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  333 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  335 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  336 */             String str = arrayOfMetaFlag[b].getFlagCode();
/*      */             
/*  338 */             for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  339 */               String str1 = vector1.elementAt(b1).toString();
/*  340 */               String str2 = str + "|" + str1;
/*  341 */               if (paramTreeMap.containsKey(str2)) {
/*  342 */                 CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  343 */                 paramStringBuffer.append("WARNING buildCtryAudRecs for new " + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */                 
/*  345 */                 ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*      */               } else {
/*  347 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str1);
/*  348 */                 ctryAudRecord.setAction("Update");
/*  349 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  350 */                 paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  351 */                     .getKey() + NEWLINE);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*  358 */       HashSet<String> hashSet1 = new HashSet();
/*  359 */       HashSet<String> hashSet2 = new HashSet();
/*      */       
/*  361 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*  362 */       paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for curr avail: fAtt " + 
/*  363 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/*  364 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/*  366 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  367 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  369 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  370 */             hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  376 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*  377 */       paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for prev avail: fAtt " + 
/*  378 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/*  379 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/*  381 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  382 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/*  384 */           if (arrayOfMetaFlag[b].isSelected()) {
/*  385 */             hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  391 */       Iterator<String> iterator = hashSet2.iterator();
/*  392 */       while (iterator.hasNext()) {
/*  393 */         String str = iterator.next();
/*  394 */         if (!hashSet1.contains(str)) {
/*      */           
/*  396 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  397 */             String str1 = vector1.elementAt(b1).toString();
/*  398 */             String str2 = str + "|" + str1;
/*  399 */             if (paramTreeMap.containsKey(str2)) {
/*  400 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  401 */               paramStringBuffer.append("WARNING buildCtryAudRecs for added ctry on " + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */               
/*  403 */               ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*      */             } else {
/*  405 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str1);
/*  406 */               ctryAudRecord.setAction("Update");
/*  407 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  408 */               paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  409 */                   .getKey() + NEWLINE);
/*      */             } 
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         byte b;
/*  416 */         for (b = 0; b < vector1.size(); b++) {
/*  417 */           String str1 = vector1.elementAt(b).toString();
/*  418 */           String str2 = str + "|" + str1;
/*      */           
/*  420 */           if (!vector2.contains(str1)) {
/*  421 */             if (paramTreeMap.containsKey(str2)) {
/*  422 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  423 */               paramStringBuffer.append("WARNING buildCtryAudRecs for existing ctry but new aud on " + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*      */               
/*  425 */               ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*      */             } else {
/*  427 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str1);
/*  428 */               ctryAudRecord.setAction("Update");
/*  429 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  430 */               paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for existing ctry but new aud:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  431 */                   .getKey() + NEWLINE);
/*      */             }
/*      */           
/*  434 */           } else if (paramTreeMap.containsKey(str2)) {
/*  435 */             CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  436 */             paramStringBuffer.append("WARNING buildCtryAudRecs for existing ctry but new aud on " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */           } else {
/*      */             
/*  439 */             CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str1);
/*  440 */             paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  441 */             paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  442 */                 .getKey() + NEWLINE);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  447 */         for (b = 0; b < vector2.size(); b++) {
/*  448 */           String str1 = vector2.elementAt(b).toString();
/*  449 */           if (!vector1.contains(str1)) {
/*  450 */             String str2 = str + "|" + str1;
/*  451 */             if (paramTreeMap.containsKey(str2)) {
/*  452 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  453 */               paramStringBuffer.append("WARNING buildCtryAudRecs for existing ctry but del aud on " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */             
/*      */             }
/*      */             else {
/*      */               
/*  458 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str1);
/*  459 */               ctryAudRecord.setAction("Delete");
/*  460 */               paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for existing ctry but del aud:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  461 */                   .getKey() + NEWLINE);
/*  462 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  467 */       iterator = hashSet1.iterator();
/*  468 */       while (iterator.hasNext()) {
/*  469 */         String str = iterator.next();
/*  470 */         if (!hashSet2.contains(str))
/*      */         {
/*  472 */           for (byte b = 0; b < vector2.size(); b++) {
/*  473 */             String str1 = vector2.elementAt(b).toString();
/*  474 */             String str2 = str + "|" + str1;
/*  475 */             if (paramTreeMap.containsKey(str2)) {
/*  476 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/*  477 */               paramStringBuffer.append("WARNING buildCtryAudRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*      */             } else {
/*      */               
/*  480 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str, str1);
/*  481 */               ctryAudRecord.setAction("Delete");
/*  482 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*  483 */               paramStringBuffer.append("XMLCtryAudElem.buildCtryAudRecs for delete ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/*  484 */                   .getKey() + NEWLINE);
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
/*  497 */     Vector<DiffEntity> vector1 = new Vector(1);
/*  498 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/*      */     
/*  500 */     paramStringBuffer.append("XMLCtryAudElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/*  501 */         .size())) + NEWLINE);
/*  502 */     if (vector2 == null) {
/*  503 */       return vector1;
/*      */     }
/*      */ 
/*      */     
/*  507 */     for (byte b = 0; b < vector2.size(); b++) {
/*  508 */       DiffEntity diffEntity = vector2.elementAt(b);
/*  509 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/*  510 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*  511 */       if (diffEntity.isDeleted()) {
/*  512 */         paramStringBuffer.append("XMLCtryAudElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/*  513 */             PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/*  514 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/*  515 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  516 */           vector1.add(diffEntity);
/*      */         }
/*      */       } else {
/*  519 */         paramStringBuffer.append("XMLCtryAudElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/*  520 */             PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/*  521 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/*  522 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/*  523 */           vector1.add(diffEntity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  528 */     return vector1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DiffEntity getEntityForAttrs(Hashtable paramHashtable, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, StringBuffer paramStringBuffer) {
/*  537 */     DiffEntity diffEntity = null;
/*  538 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get(paramString1);
/*      */     
/*  540 */     paramStringBuffer.append("XMLCtryAudElem.getEntityForAttrs looking for " + paramString2 + ":" + paramString3 + " and " + paramString4 + ":" + paramString5 + " in " + paramString1 + " allVct.size:" + ((vector == null) ? "null" : ("" + vector
/*  541 */         .size())) + NEWLINE);
/*  542 */     if (vector == null) {
/*  543 */       return diffEntity;
/*      */     }
/*      */ 
/*      */     
/*  547 */     for (byte b = 0; b < vector.size(); b++) {
/*  548 */       DiffEntity diffEntity1 = vector.elementAt(b);
/*  549 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/*  550 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/*  551 */       if (diffEntity1.isDeleted()) {
/*  552 */         paramStringBuffer.append("XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  553 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/*  554 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/*  555 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/*  556 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  557 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/*  558 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  559 */             diffEntity = diffEntity1;
/*      */           }
/*      */         }
/*      */       
/*  563 */       } else if (diffEntity1.isNew()) {
/*  564 */         paramStringBuffer.append("XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  565 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/*  566 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/*  567 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/*  568 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  569 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/*  570 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  571 */             diffEntity = diffEntity1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } else {
/*  577 */         paramStringBuffer.append("XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: current " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  578 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/*  579 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/*  580 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/*  581 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  582 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/*  583 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  584 */             diffEntity = diffEntity1;
/*      */             break;
/*      */           } 
/*      */         } 
/*  588 */         paramStringBuffer.append("XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/*  589 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/*  590 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/*  591 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/*  592 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*  593 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/*  594 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/*  595 */             diffEntity = diffEntity1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  603 */     return diffEntity;
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
/*      */   private DiffEntity getCatlgor(Hashtable paramHashtable, String paramString1, String paramString2, StringBuffer paramStringBuffer) {
/*  625 */     DiffEntity diffEntity = null;
/*  626 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("CATLGOR");
/*  627 */     String str1 = "CATAUDIENCE";
/*  628 */     String str2 = "OFFCOUNTRY";
/*  629 */     paramStringBuffer.append("XMLCtryAudElem.getCatlgor looking for " + str1 + ":" + paramString1 + " and " + str2 + ":" + paramString2 + " in CATLGOR allVct.size:" + ((vector == null) ? "null" : ("" + vector
/*  630 */         .size())) + NEWLINE);
/*  631 */     if (vector == null) {
/*  632 */       return diffEntity;
/*      */     }
/*      */     
/*      */     byte b;
/*  636 */     label85: for (b = 0; b < vector.size(); b++) {
/*  637 */       DiffEntity diffEntity1 = vector.elementAt(b);
/*  638 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/*  639 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/*  640 */       if (diffEntity1.isDeleted()) {
/*  641 */         paramStringBuffer.append("XMLCtryAudElem.getCatlgor checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + str1 + ":" + 
/*  642 */             PokUtils.getAttributeFlagValue(entityItem2, str1) + " " + str2 + ":" + 
/*  643 */             PokUtils.getAttributeFlagValue(entityItem2, str2) + NEWLINE);
/*  644 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str1);
/*  645 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */           
/*  647 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  648 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */             
/*  650 */             if (arrayOfMetaFlag[b1].isSelected() && arrayOfMetaFlag[b1].toString().equals(paramString1)) {
/*  651 */               EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute(str2);
/*  652 */               if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(paramString2)) {
/*  653 */                 diffEntity = diffEntity1;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  660 */       } else if (diffEntity1.isNew()) {
/*  661 */         paramStringBuffer.append("XMLCtryAudElem.getCatlgor checking[" + b + "]: new " + diffEntity1.getKey() + " " + str1 + ":" + 
/*  662 */             PokUtils.getAttributeFlagValue(entityItem1, str1) + " " + str2 + ":" + 
/*  663 */             PokUtils.getAttributeFlagValue(entityItem1, str2) + NEWLINE);
/*  664 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str1);
/*  665 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */           
/*  667 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  668 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */             
/*  670 */             if (arrayOfMetaFlag[b1].isSelected() && arrayOfMetaFlag[b1].toString().equals(paramString1)) {
/*  671 */               EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem1.getAttribute(str2);
/*  672 */               if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(paramString2)) {
/*  673 */                 diffEntity = diffEntity1;
/*      */                 
/*      */                 break label85;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } else {
/*  681 */         paramStringBuffer.append("XMLCtryAudElem.getCatlgor checking[" + b + "]: current " + diffEntity1.getKey() + " " + str1 + ":" + 
/*  682 */             PokUtils.getAttributeFlagValue(entityItem1, str1) + " " + str2 + ":" + 
/*  683 */             PokUtils.getAttributeFlagValue(entityItem1, str2) + NEWLINE);
/*  684 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(str1);
/*  685 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */           
/*  687 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  688 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */             
/*  690 */             if (arrayOfMetaFlag[b1].isSelected() && arrayOfMetaFlag[b1].toString().equals(paramString1)) {
/*  691 */               EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem1.getAttribute(str2);
/*  692 */               if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(paramString2)) {
/*  693 */                 diffEntity = diffEntity1;
/*      */                 
/*      */                 break label85;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  700 */         paramStringBuffer.append("XMLCtryAudElem.getCatlgor checking[" + b + "]: prior " + diffEntity1.getKey() + " " + str1 + ":" + 
/*  701 */             PokUtils.getAttributeFlagValue(entityItem2, str1) + " " + str2 + ":" + 
/*  702 */             PokUtils.getAttributeFlagValue(entityItem2, str2) + NEWLINE);
/*  703 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(str1);
/*  704 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */           
/*  706 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  707 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */             
/*  709 */             if (arrayOfMetaFlag[b1].isSelected() && arrayOfMetaFlag[b1].toString().equals(paramString1)) {
/*  710 */               EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute(str2);
/*  711 */               if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(paramString2)) {
/*  712 */                 diffEntity = diffEntity1;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  722 */     return diffEntity;
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
/*      */   private static class CtryAudRecord
/*      */   {
/*  735 */     private String action = null;
/*      */     private String audience;
/*      */     private String country;
/*  738 */     private String earliestshipdate = "@@";
/*  739 */     private String availStatus = "@@";
/*  740 */     private String pubfrom = "@@";
/*  741 */     private String pubto = "@@";
/*  742 */     private String addtocart = "No";
/*  743 */     private String buyable = "No";
/*  744 */     private String publish = "No";
/*  745 */     private String customizeable = "Yes";
/*  746 */     private String hide = "No"; private DiffEntity availDiff;
/*      */     
/*      */     boolean isDisplayable() {
/*  749 */       return (this.action != null);
/*      */     }
/*      */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String1, String param1String2) {
/*  752 */       this.country = param1String1;
/*  753 */       this.audience = param1String2;
/*  754 */       this.availDiff = param1DiffEntity;
/*      */     } void setAction(String param1String) {
/*  756 */       this.action = param1String;
/*      */     } void setUpdateAvail(DiffEntity param1DiffEntity) {
/*  758 */       this.availDiff = param1DiffEntity;
/*  759 */       setAction("Update");
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
/*      */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, StringBuffer param1StringBuffer) {
/*  780 */       param1StringBuffer.append("CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + XMLElem.NEWLINE);
/*      */       
/*  782 */       EntityItem entityItem1 = this.availDiff.getCurrentEntityItem();
/*  783 */       EntityItem entityItem2 = this.availDiff.getPriorEntityItem();
/*      */ 
/*      */ 
/*      */       
/*  787 */       this.earliestshipdate = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", ", ", "@@", false);
/*      */       
/*  789 */       String str1 = "@@";
/*  790 */       if (entityItem2 != null) {
/*  791 */         str1 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", ", ", "@@", false);
/*      */       }
/*  793 */       param1StringBuffer.append("CtryAudRecord.setAllFields curshipdate: " + this.earliestshipdate + " prevdate: " + str1 + XMLElem.NEWLINE);
/*      */ 
/*      */       
/*  796 */       if (!str1.equals(this.earliestshipdate)) {
/*  797 */         setAction("Update");
/*      */       }
/*      */       
/*  800 */       this.availStatus = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/*  801 */       if (this.availStatus == null) {
/*  802 */         this.availStatus = "@@";
/*      */       }
/*      */       
/*  805 */       String str2 = "@@";
/*  806 */       if (entityItem2 != null) {
/*  807 */         str2 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/*  808 */         if (str2 == null) {
/*  809 */           str2 = "@@";
/*      */         }
/*      */       } 
/*  812 */       param1StringBuffer.append("CtryAudRecord.setAllFields curstatus: " + this.availStatus + " prevstatus: " + str2 + XMLElem.NEWLINE);
/*      */ 
/*      */       
/*  815 */       if (!str2.equals(this.availStatus)) {
/*  816 */         setAction("Update");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  821 */       this.pubfrom = derivePubFrom(param1DiffEntity1, param1DiffEntity3, false, param1StringBuffer);
/*  822 */       String str3 = derivePubFrom(param1DiffEntity1, param1DiffEntity3, true, param1StringBuffer);
/*  823 */       param1StringBuffer.append("CtryAudRecord.setAllFields pubfromT2: " + this.pubfrom + " pubfromT1: " + str3 + XMLElem.NEWLINE);
/*      */       
/*  825 */       if (!this.pubfrom.equals(str3)) {
/*  826 */         setAction("Update");
/*      */       }
/*      */ 
/*      */       
/*  830 */       this.pubto = derivePubTo(param1DiffEntity2, param1DiffEntity3, false, param1StringBuffer);
/*  831 */       String str4 = derivePubTo(param1DiffEntity2, param1DiffEntity3, true, param1StringBuffer);
/*  832 */       if (!this.pubto.equals(str4)) {
/*  833 */         setAction("Update");
/*      */       }
/*  835 */       param1StringBuffer.append("CtryAudRecord.setAllFields action:" + this.action + " pubtoT2: " + this.pubto + " pubtoT1: " + str4 + XMLElem.NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  844 */       if (param1DiffEntity3 != null) {
/*  845 */         if (!param1DiffEntity3.isDeleted()) {
/*  846 */           boolean bool = true;
/*      */           
/*  848 */           if (param1DiffEntity3.isNew()) {
/*  849 */             setAction("Update");
/*      */           } else {
/*  851 */             entityItem1 = param1DiffEntity3.getCurrentEntityItem();
/*  852 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("OFFCOUNTRY");
/*  853 */             bool = (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) ? true : false;
/*  854 */             if (!bool) {
/*  855 */               setAction("Update");
/*      */             }
/*      */           } 
/*      */           
/*  859 */           if (bool) {
/*  860 */             entityItem1 = param1DiffEntity3.getCurrentEntityItem();
/*  861 */             this.addtocart = PokUtils.getAttributeValue(entityItem1, "CATADDTOCART", ", ", "No", false);
/*  862 */             this.buyable = PokUtils.getAttributeValue(entityItem1, "CATBUYABLE", ", ", "No", false);
/*  863 */             this.publish = PokUtils.getAttributeValue(entityItem1, "CATPUBLISH", ", ", "No", false);
/*  864 */             this.customizeable = PokUtils.getAttributeValue(entityItem1, "CATCUSTIMIZE", ", ", "Yes", false);
/*  865 */             this.hide = PokUtils.getAttributeValue(entityItem1, "CATHIDE", ", ", "No", false);
/*      */           } 
/*      */         } else {
/*  868 */           setAction("Update");
/*      */         } 
/*      */         
/*  871 */         param1StringBuffer.append("CtryAudRecord.setAllFields after catlgor action:" + this.action + XMLElem.NEWLINE);
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
/*      */     private String derivePubTo(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  884 */       param1StringBuffer.append("CtryAudRecord.derivePubTo  loAvailDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1
/*  885 */           .getKey()) + " catlgorDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/*  886 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */       
/*  888 */       String str = "@@";
/*  889 */       if (param1Boolean) {
/*  890 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isNew()) {
/*  891 */           EntityItem entityItem = param1DiffEntity2.getPriorEntityItem();
/*      */           
/*  893 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("OFFCOUNTRY");
/*  894 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  895 */             str = PokUtils.getAttributeValue(entityItem, "PUBTO", "", "@@", false);
/*      */           }
/*  897 */           param1StringBuffer.append("CtryAudRecord.derivePubTo catlgor thedate: " + str + " OFFCOUNTRY: " + 
/*  898 */               PokUtils.getAttributeFlagValue(entityItem, "OFFCOUNTRY") + XMLElem.NEWLINE);
/*      */         } 
/*  900 */         if ("@@".equals(str))
/*      */         {
/*  902 */           if (param1DiffEntity1 != null && !param1DiffEntity1.isNew()) {
/*  903 */             EntityItem entityItem = param1DiffEntity1.getPriorEntityItem();
/*  904 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  905 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  906 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/*  908 */             param1StringBuffer.append("CtryAudRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/*  909 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */           } 
/*      */         }
/*      */       } else {
/*  913 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isDeleted()) {
/*  914 */           EntityItem entityItem = param1DiffEntity2.getCurrentEntityItem();
/*      */           
/*  916 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("OFFCOUNTRY");
/*  917 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  918 */             str = PokUtils.getAttributeValue(entityItem, "PUBTO", "", "@@", false);
/*      */           }
/*  920 */           param1StringBuffer.append("CtryAudRecord.derivePubTo catlgor thedate: " + str + " OFFCOUNTRY: " + 
/*  921 */               PokUtils.getAttributeFlagValue(entityItem, "OFFCOUNTRY") + XMLElem.NEWLINE);
/*      */         } 
/*  923 */         if ("@@".equals(str))
/*      */         {
/*  925 */           if (param1DiffEntity1 != null && !param1DiffEntity1.isDeleted()) {
/*  926 */             EntityItem entityItem = param1DiffEntity1.getCurrentEntityItem();
/*  927 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  928 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  929 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/*  931 */             param1StringBuffer.append("CtryAudRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/*  932 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  937 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String derivePubFrom(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/*  948 */       String str = "@@";
/*  949 */       param1StringBuffer.append("CtryAudRecord.derivePubFrom availDiff: " + this.availDiff.getKey() + " foAvailDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1
/*  950 */           .getKey()) + " catlgorDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/*  951 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*      */       
/*  953 */       if (param1Boolean) {
/*  954 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isNew()) {
/*  955 */           EntityItem entityItem = param1DiffEntity2.getPriorEntityItem();
/*      */           
/*  957 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("OFFCOUNTRY");
/*  958 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  959 */             str = PokUtils.getAttributeValue(entityItem, "PUBFROM", "", "@@", false);
/*      */           }
/*  961 */           param1StringBuffer.append("CtryAudRecord.derivePubFrom catlgor thedate: " + str + " OFFCOUNTRY: " + 
/*      */               
/*  963 */               PokUtils.getAttributeFlagValue(entityItem, "OFFCOUNTRY") + XMLElem.NEWLINE);
/*      */         } 
/*  965 */         if ("@@".equals(str))
/*      */         {
/*  967 */           if (param1DiffEntity1 != null && !param1DiffEntity1.isNew()) {
/*  968 */             EntityItem entityItem = param1DiffEntity1.getPriorEntityItem();
/*  969 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  970 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  971 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/*  973 */             param1StringBuffer.append("CtryAudRecord.derivePubFrom foavail thedate: " + str + " COUNTRYLIST: " + 
/*      */                 
/*  975 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */           } 
/*      */         }
/*  978 */         if ("@@".equals(str))
/*      */         {
/*  980 */           if (!this.availDiff.isNew()) {
/*  981 */             EntityItem entityItem = this.availDiff.getPriorEntityItem();
/*  982 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/*  983 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  984 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/*  986 */             param1StringBuffer.append("CtryAudRecord.derivePubFrom plannedavail thedate: " + str + " COUNTRYLIST: " + 
/*      */                 
/*  988 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */           } 
/*      */         }
/*      */       } else {
/*  992 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isDeleted()) {
/*  993 */           EntityItem entityItem = param1DiffEntity2.getCurrentEntityItem();
/*      */           
/*  995 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("OFFCOUNTRY");
/*  996 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/*  997 */             str = PokUtils.getAttributeValue(entityItem, "PUBFROM", "", "@@", false);
/*  998 */             param1StringBuffer.append("CtryAudRecord.derivePubFrom catlgor thedate: " + str + " OFFCOUNTRY: " + 
/*      */                 
/* 1000 */                 PokUtils.getAttributeFlagValue(entityItem, "OFFCOUNTRY") + XMLElem.NEWLINE);
/*      */           } 
/*      */         } 
/* 1003 */         if ("@@".equals(str))
/*      */         {
/* 1005 */           if (param1DiffEntity1 != null && !param1DiffEntity1.isDeleted()) {
/* 1006 */             EntityItem entityItem = param1DiffEntity1.getCurrentEntityItem();
/* 1007 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1008 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1009 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/* 1011 */             param1StringBuffer.append("CtryAudRecord.derivePubFrom foavail thedate: " + str + " COUNTRYLIST: " + 
/*      */                 
/* 1013 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */           } 
/*      */         }
/* 1016 */         if ("@@".equals(str))
/*      */         {
/* 1018 */           if (!this.availDiff.isDeleted()) {
/* 1019 */             EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 1020 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1021 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 1022 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*      */             }
/* 1024 */             param1StringBuffer.append("CtryAudRecord.derivePubFrom plannedavail thedate: " + str + " COUNTRYLIST: " + 
/*      */                 
/* 1026 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 1031 */       return str;
/*      */     }
/*      */     
/* 1034 */     String getAction() { return this.action; }
/* 1035 */     String getAudience() { return this.audience; }
/* 1036 */     String getCountry() { return this.country; }
/* 1037 */     String getShipDate() { return this.earliestshipdate; }
/* 1038 */     String getPubFrom() { return this.pubfrom; }
/* 1039 */     String getPubTo() { return this.pubto; }
/* 1040 */     String getAddToCart() { return this.addtocart; }
/* 1041 */     String getBuyable() { return this.buyable; }
/* 1042 */     String getPublish() { return this.publish; }
/* 1043 */     String getCustomizeable() { return this.customizeable; }
/* 1044 */     String getHide() { return this.hide; } String getAvailStatus() {
/* 1045 */       return this.availStatus;
/*      */     }
/* 1047 */     boolean isDeleted() { return "Delete".equals(this.action); } String getKey() {
/* 1048 */       return this.country + "|" + this.audience;
/*      */     } void dereference() {
/* 1050 */       this.availDiff = null;
/* 1051 */       this.action = null;
/* 1052 */       this.audience = null;
/* 1053 */       this.country = null;
/* 1054 */       this.availStatus = null;
/* 1055 */       this.earliestshipdate = null;
/* 1056 */       this.pubfrom = null;
/* 1057 */       this.pubto = null;
/* 1058 */       this.addtocart = null;
/* 1059 */       this.buyable = null;
/* 1060 */       this.publish = null;
/* 1061 */       this.customizeable = null;
/* 1062 */       this.hide = null;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1066 */       return this.availDiff.getKey() + " " + getKey() + " action:" + this.action;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLCtryAudElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */