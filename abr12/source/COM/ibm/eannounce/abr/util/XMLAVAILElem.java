/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.TreeMap;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLAVAILElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLAVAILElem() {
/* 133 */     super("AVAILABILITYELEMENT");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 156 */     boolean bool = isDerivefromModel(paramHashtable, paramDiffEntity, paramStringBuffer);
/* 157 */     if (bool == true) {
/* 158 */       createNodeFromModel(paramDatabase, paramDocument, paramElement, paramDiffEntity, paramStringBuffer);
/*     */     } else {
/*     */       
/* 161 */       Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, paramStringBuffer);
/*     */       
/* 163 */       if (vector.size() > 0) {
/*     */ 
/*     */ 
/*     */         
/* 167 */         TreeMap<Object, Object> treeMap = new TreeMap<>();
/* 168 */         for (byte b = 0; b < vector.size(); b++) {
/* 169 */           DiffEntity diffEntity = vector.elementAt(b);
/* 170 */           buildCtryAudRecs(treeMap, diffEntity, paramStringBuffer);
/*     */         } 
/*     */ 
/*     */         
/* 174 */         Collection collection = treeMap.values();
/* 175 */         Iterator<CtryAudRecord> iterator = collection.iterator();
/* 176 */         while (iterator.hasNext()) {
/* 177 */           CtryAudRecord ctryAudRecord = iterator.next();
/*     */           
/* 179 */           if (!ctryAudRecord.isDeleted()) {
/*     */             
/* 181 */             DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryAudRecord
/* 182 */                 .getCountry(), paramStringBuffer);
/*     */             
/* 184 */             DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryAudRecord
/* 185 */                 .getCountry(), paramStringBuffer);
/* 186 */             DiffEntity diffEntity3 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "151", "COUNTRYLIST", ctryAudRecord
/* 187 */                 .getCountry(), paramStringBuffer);
/*     */ 
/*     */             
/* 190 */             ctryAudRecord.setAllFields(diffEntity1, diffEntity2, diffEntity3, paramStringBuffer);
/*     */           } 
/* 192 */           if (ctryAudRecord.isDisplayable()) {
/* 193 */             createNodeSet(paramDocument, paramElement, ctryAudRecord, paramStringBuffer);
/*     */           } else {
/* 195 */             ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*     */           } 
/* 197 */           ctryAudRecord.dereference();
/*     */         } 
/*     */ 
/*     */         
/* 201 */         treeMap.clear();
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 207 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.addElements no planned AVAILs found" + NEWLINE);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNodeFromModel(Database paramDatabase, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException {
/* 225 */     ReturnDataResultSet returnDataResultSet = null;
/* 226 */     ResultSet resultSet = null;
/* 227 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/* 228 */     String str1 = null;
/* 229 */     String str2 = "@@";
/* 230 */     String str3 = "@@";
/*     */     
/* 232 */     if (paramDiffEntity != null) {
/* 233 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 234 */       if (entityItem != null) {
/* 235 */         str1 = entityItem.getProfile().getEnterprise();
/* 236 */         str2 = PokUtils.getAttributeValue(entityItem, "ANNDATE", ", ", "@@", false);
/* 237 */         str3 = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", ", ", "@@", false);
/* 238 */         ABRUtil.append(paramStringBuffer, "XMLAVAILElem.addElements" + entityItem.getKey() + " thedate: " + str2 + " withdrawanndate: " + str3 + NEWLINE);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 243 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*     */     
/*     */     try {
/* 246 */       resultSet = paramDatabase.callGBL9999A(returnStatus, str1, "GENAREASELECTION", "1999", "COUNTRYLIST");
/* 247 */       returnDataResultSet = new ReturnDataResultSet(resultSet);
/*     */     } finally {
/* 249 */       if (resultSet != null) {
/* 250 */         resultSet.close();
/* 251 */         resultSet = null;
/*     */       } 
/* 253 */       paramDatabase.commit();
/* 254 */       paramDatabase.freeStatement();
/* 255 */       paramDatabase.isPending();
/*     */     } 
/* 257 */     for (byte b = 0; b < returnDataResultSet.size(); b++) {
/* 258 */       String str4 = returnDataResultSet.getColumn(b, 0).trim();
/* 259 */       ABRUtil.append(paramStringBuffer, "derivefrommodel world wide counry " + str4 + NEWLINE);
/*     */       
/* 261 */       String str5 = str4;
/* 262 */       if (!treeMap.containsKey(str5)) {
/* 263 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(null, str4);
/* 264 */         ctryAudRecord.action = "Update";
/* 265 */         ctryAudRecord.pubfrom = str2;
/* 266 */         ctryAudRecord.pubto = str3;
/* 267 */         treeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*     */       } 
/*     */     } 
/* 270 */     Collection collection = treeMap.values();
/* 271 */     Iterator<CtryAudRecord> iterator = collection.iterator();
/* 272 */     while (iterator.hasNext()) {
/* 273 */       CtryAudRecord ctryAudRecord = iterator.next();
/* 274 */       createNodeSet(paramDocument, paramElement, ctryAudRecord, paramStringBuffer);
/* 275 */       ctryAudRecord.dereference();
/*     */     } 
/* 277 */     treeMap.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNodeSet(Document paramDocument, Element paramElement, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/* 285 */     Element element1 = paramDocument.createElement(this.nodeName);
/* 286 */     addXMLAttrs(element1);
/* 287 */     paramElement.appendChild(element1);
/*     */ 
/*     */     
/* 290 */     Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/* 291 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/* 292 */     element1.appendChild(element2);
/* 293 */     element2 = paramDocument.createElement("STATUS");
/* 294 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAvailStatus()));
/* 295 */     element1.appendChild(element2);
/* 296 */     element2 = paramDocument.createElement("COUNTRY_FC");
/* 297 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/* 298 */     element1.appendChild(element2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     element2 = paramDocument.createElement("PUBFROM");
/* 304 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubFrom()));
/* 305 */     element1.appendChild(element2);
/* 306 */     element2 = paramDocument.createElement("PUBTO");
/* 307 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPubTo()));
/* 308 */     element1.appendChild(element2);
/* 309 */     element2 = paramDocument.createElement("ENDOFSERVICEDATE");
/* 310 */     element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getEndOfService()));
/* 311 */     element1.appendChild(element2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 330 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 337 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 338 */     if (paramDiffEntity.isDeleted()) {
/*     */       
/* 340 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 341 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for deleted avail: ctryAtt " + 
/* 342 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 343 */       if (eANFlagAttribute != null) {
/* 344 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 345 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 347 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 348 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 349 */             String str2 = str1;
/* 350 */             if (paramTreeMap.containsKey(str2)) {
/*     */               
/* 352 */               CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/* 353 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*     */             } else {
/*     */               
/* 356 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 357 */               ctryAudRecord.setAction("Delete");
/* 358 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 359 */               ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 360 */                   .getKey() + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 365 */     } else if (paramDiffEntity.isNew()) {
/*     */       
/* 367 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 368 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for new avail:  ctryAtt and anncodeAtt " + 
/* 369 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/* 370 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/* 371 */       if (eANFlagAttribute != null) {
/* 372 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 373 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 375 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 376 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 377 */             String str2 = str1;
/* 378 */             if (paramTreeMap.containsKey(str2)) {
/* 379 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/* 380 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new " + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*     */               
/* 382 */               ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*     */             } else {
/* 384 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 385 */               ctryAudRecord.setAction("Update");
/* 386 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 387 */               ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 388 */                   .getKey() + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 394 */       HashSet<String> hashSet1 = new HashSet();
/* 395 */       HashSet<String> hashSet2 = new HashSet();
/*     */       
/* 397 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 398 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for curr avail: fAtt and curranncodeAtt " + 
/* 399 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/* 400 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/* 401 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 403 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 404 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 406 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 407 */             hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 413 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 414 */       ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for prev avail:  fAtt and prevanncodeAtt " + 
/* 415 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + 
/* 416 */           PokUtils.getAttributeFlagValue(entityItem2, "ANNCODENAME") + NEWLINE);
/* 417 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 419 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 420 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 422 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 423 */             hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 429 */       Iterator<String> iterator = hashSet2.iterator();
/* 430 */       while (iterator.hasNext()) {
/* 431 */         String str1 = iterator.next();
/* 432 */         if (!hashSet1.contains(str1)) {
/*     */           
/* 434 */           String str = str1;
/* 435 */           if (paramTreeMap.containsKey(str)) {
/* 436 */             CtryAudRecord ctryAudRecord2 = paramTreeMap.get(str);
/* 437 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for added ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryAudRecord2 + NEWLINE);
/*     */             
/* 439 */             ctryAudRecord2.setUpdateAvail(paramDiffEntity); continue;
/*     */           } 
/* 441 */           CtryAudRecord ctryAudRecord1 = new CtryAudRecord(paramDiffEntity, str1);
/* 442 */           ctryAudRecord1.setAction("Update");
/* 443 */           paramTreeMap.put(ctryAudRecord1.getKey(), ctryAudRecord1);
/* 444 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord1
/* 445 */               .getKey() + NEWLINE);
/*     */           
/*     */           continue;
/*     */         } 
/* 449 */         String str2 = str1;
/* 450 */         if (paramTreeMap.containsKey(str2)) {
/* 451 */           CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str2);
/* 452 */           ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for existing ctry but new aud on " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*     */           continue;
/*     */         } 
/* 455 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 456 */         paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 457 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 458 */             .getKey() + NEWLINE);
/*     */       } 
/*     */ 
/*     */       
/* 462 */       iterator = hashSet1.iterator();
/* 463 */       while (iterator.hasNext()) {
/* 464 */         String str = iterator.next();
/* 465 */         if (!hashSet2.contains(str)) {
/*     */           
/* 467 */           String str1 = str;
/* 468 */           if (paramTreeMap.containsKey(str1)) {
/* 469 */             CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str1);
/* 470 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str1 + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*     */             continue;
/*     */           } 
/* 473 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str);
/* 474 */           ctryAudRecord.setAction("Delete");
/* 475 */           paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 476 */           ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.buildCtryAudRecs for delete ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 477 */               .getKey() + NEWLINE);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getPlannedAvails(Hashtable paramHashtable, StringBuffer paramStringBuffer) {
/* 489 */     Vector<DiffEntity> vector1 = new Vector(1);
/* 490 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/*     */     
/* 492 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/* 493 */         .size())) + NEWLINE);
/* 494 */     if (vector2 == null) {
/* 495 */       return vector1;
/*     */     }
/*     */ 
/*     */     
/* 499 */     for (byte b = 0; b < vector2.size(); b++) {
/* 500 */       DiffEntity diffEntity = vector2.elementAt(b);
/* 501 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 502 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/* 503 */       if (diffEntity.isDeleted()) {
/* 504 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/* 505 */             PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/* 506 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/* 507 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 508 */           vector1.add(diffEntity);
/*     */         }
/*     */       } else {
/* 511 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/* 512 */             PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/* 513 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/* 514 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 515 */           vector1.add(diffEntity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 520 */     return vector1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DiffEntity getEntityForAttrs(Hashtable paramHashtable, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, StringBuffer paramStringBuffer) {
/* 529 */     DiffEntity diffEntity = null;
/* 530 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get(paramString1);
/*     */     
/* 532 */     ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs looking for " + paramString2 + ":" + paramString3 + " and " + paramString4 + ":" + paramString5 + " in " + paramString1 + " allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 533 */         .size())) + NEWLINE);
/* 534 */     if (vector == null) {
/* 535 */       return diffEntity;
/*     */     }
/*     */     
/* 538 */     for (byte b = 0; b < vector.size(); b++) {
/* 539 */       DiffEntity diffEntity1 = vector.elementAt(b);
/* 540 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/* 541 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/* 542 */       if (diffEntity1.isDeleted()) {
/* 543 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 544 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/* 545 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/* 546 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/* 547 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 548 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/* 549 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 550 */             diffEntity = diffEntity1;
/*     */           }
/*     */         }
/*     */       
/* 554 */       } else if (diffEntity1.isNew()) {
/* 555 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 556 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/* 557 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/* 558 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/* 559 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 560 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/* 561 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 562 */             diffEntity = diffEntity1;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 568 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: current " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 569 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/* 570 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/* 571 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/* 572 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 573 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/* 574 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 575 */             diffEntity = diffEntity1;
/*     */             break;
/*     */           } 
/*     */         } 
/* 579 */         ABRUtil.append(paramStringBuffer, "XMLCtryAudElem.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 580 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/* 581 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/* 582 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/* 583 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 584 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/* 585 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 586 */             diffEntity = diffEntity1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 594 */     return diffEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDerivefromModel(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 612 */     boolean bool = true;
/*     */     
/* 614 */     if (paramDiffEntity != null && (
/* 615 */       paramDiffEntity.getEntityType().equals("MODEL") || paramDiffEntity.getEntityType().equals("SVCMOD"))) {
/* 616 */       String str = "2010-03-01";
/* 617 */       Vector<DiffEntity> vector = (Vector)paramHashtable.get("AVAIL");
/* 618 */       ABRUtil.append(paramStringBuffer, "DerivefromModel.getAvails looking for AVAILTYPE: 146 in AVAIL allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 619 */           .size())) + NEWLINE);
/* 620 */       if (vector != null)
/*     */       {
/* 622 */         for (byte b = 0; b < vector.size(); b++) {
/* 623 */           DiffEntity diffEntity = vector.elementAt(b);
/* 624 */           EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 625 */           if (!diffEntity.isDeleted()) {
/* 626 */             ABRUtil.append(paramStringBuffer, "XMLANNElem.DerivefromModel.getAvails checking[" + b + "]:New or Update" + diffEntity
/* 627 */                 .getKey() + " AVAILTYPE: " + PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/*     */             
/* 629 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 630 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 631 */               bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 648 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CtryAudRecord
/*     */   {
/* 658 */     private String action = "@@";
/*     */     
/*     */     private String country;
/* 661 */     private String availStatus = "@@";
/* 662 */     private String pubfrom = "@@";
/* 663 */     private String pubto = "@@";
/* 664 */     private String endofservice = "@@"; private DiffEntity availDiff;
/*     */     
/*     */     boolean isDisplayable() {
/* 667 */       return !this.action.equals("@@");
/*     */     }
/*     */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String) {
/* 670 */       this.country = param1String;
/* 671 */       this.availDiff = param1DiffEntity;
/*     */     } void setAction(String param1String) {
/* 673 */       this.action = param1String;
/*     */     } void setUpdateAvail(DiffEntity param1DiffEntity) {
/* 675 */       this.availDiff = param1DiffEntity;
/* 676 */       setAction("Update");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, StringBuffer param1StringBuffer) {
/* 701 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + XMLElem.NEWLINE);
/*     */       
/* 703 */       EntityItem entityItem1 = this.availDiff.getCurrentEntityItem();
/* 704 */       EntityItem entityItem2 = this.availDiff.getPriorEntityItem();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 729 */       if (entityItem1 != null) {
/* 730 */         this.availStatus = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/* 731 */         if (this.availStatus == null) {
/* 732 */           this.availStatus = "@@";
/*     */         }
/*     */       } 
/*     */       
/* 736 */       String str1 = "@@";
/* 737 */       if (entityItem2 != null) {
/* 738 */         str1 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 739 */         if (str1 == null) {
/* 740 */           str1 = "@@";
/*     */         }
/*     */       } 
/* 743 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields curstatus: " + this.availStatus + " prevstatus: " + str1 + XMLElem.NEWLINE);
/*     */ 
/*     */       
/* 746 */       if (!str1.equals(this.availStatus)) {
/* 747 */         setAction("Update");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 753 */       if (isNewCountry(param1DiffEntity1, param1StringBuffer)) {
/* 754 */         setAction("Update");
/*     */       }
/*     */       
/* 757 */       this.pubfrom = derivePubFrom(param1DiffEntity1, false, param1StringBuffer);
/* 758 */       String str2 = derivePubFrom(param1DiffEntity1, true, param1StringBuffer);
/*     */       
/* 760 */       if (!this.pubfrom.equals(str2)) {
/* 761 */         setAction("Update");
/*     */       }
/*     */       
/* 764 */       this.pubto = derivePubTo(param1DiffEntity2, false, param1StringBuffer);
/* 765 */       String str3 = derivePubTo(param1DiffEntity2, true, param1StringBuffer);
/* 766 */       if (!this.pubto.equals(str3)) {
/* 767 */         setAction("Update");
/*     */       }
/*     */       
/* 770 */       this.endofservice = deriveENDOFSERVICE(param1DiffEntity3, false, param1StringBuffer);
/* 771 */       String str4 = deriveENDOFSERVICE(param1DiffEntity3, true, param1StringBuffer);
/* 772 */       if (!this.endofservice.equals(str4)) {
/* 773 */         setAction("Update");
/*     */       }
/* 775 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields pubfrom: " + this.pubfrom + " pubto: " + this.pubto + " endofservice:" + this.endofservice + XMLElem.NEWLINE);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isNewCountry(DiffEntity param1DiffEntity, StringBuffer param1StringBuffer) {
/* 784 */       boolean bool = false;
/* 785 */       if (param1DiffEntity != null && param1DiffEntity.isNew()) {
/* 786 */         bool = true;
/* 787 */         ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields isNewAvail" + param1DiffEntity.getKey() + XMLElem.NEWLINE);
/* 788 */       } else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 789 */         EANFlagAttribute eANFlagAttribute1 = null;
/* 790 */         EANFlagAttribute eANFlagAttribute2 = null;
/* 791 */         EntityItem entityItem1 = param1DiffEntity.getCurrentEntityItem();
/* 792 */         EntityItem entityItem2 = param1DiffEntity.getPriorEntityItem();
/* 793 */         if (entityItem1 != null) {
/* 794 */           eANFlagAttribute1 = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/*     */         }
/* 796 */         if (entityItem2 != null) {
/* 797 */           eANFlagAttribute2 = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/*     */         }
/* 799 */         if (eANFlagAttribute2 != null && !eANFlagAttribute2.isSelected(this.country) && eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(this.country)) {
/* 800 */           bool = true;
/* 801 */           ABRUtil.append(param1StringBuffer, "CtryAudRecord.setAllFields isNewCountry" + param1DiffEntity.getKey() + XMLElem.NEWLINE);
/*     */         } 
/*     */       } 
/* 804 */       return bool;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String deriveENDOFSERVICE(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 816 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService  eofAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 817 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */ 
/*     */       
/* 820 */       String str = "@@";
/* 821 */       if (param1Boolean) {
/*     */         
/* 823 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 824 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 825 */           if (entityItem != null) {
/* 826 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 827 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 828 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */               
/* 830 */               ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService eofavail thedate: " + str + " COUNTRYLIST: " + 
/* 831 */                   PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */             } 
/*     */           } else {
/* 834 */             ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService eofAvail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 841 */       else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 842 */         EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 843 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 844 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 845 */           str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */         }
/* 847 */         ABRUtil.append(param1StringBuffer, "CtryAudRecord.deriveEndOfService eofavail thedate: " + str + " COUNTRYLIST: " + 
/* 848 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 853 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String derivePubTo(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 865 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo  loAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 866 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */ 
/*     */       
/* 869 */       String str = "@@";
/* 870 */       if (param1Boolean) {
/*     */         
/* 872 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 873 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 874 */           if (entityItem != null) {
/* 875 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 876 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 877 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */             }
/* 879 */             ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/* 880 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */           } else {
/* 882 */             ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo loavail priorEnityitem: " + entityItem + XMLElem.NEWLINE);
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 888 */       else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 889 */         EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 890 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 891 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 892 */           str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */         }
/* 894 */         ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/* 895 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 900 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String derivePubFrom(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 911 */       String str = "@@";
/* 912 */       ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubFrom availDiff: " + this.availDiff.getKey() + " foAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 913 */           .getKey()) + "findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */       
/* 915 */       if (param1Boolean) {
/*     */ 
/*     */         
/* 918 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 919 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 920 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 921 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 922 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */           }
/* 924 */           ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubFrom foavail thedate: " + str + XMLElem.NEWLINE);
/*     */         } 
/*     */         
/* 927 */         if ("@@".equals(str))
/*     */         {
/* 929 */           if (!this.availDiff.isNew() && this.availDiff != null) {
/* 930 */             EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 931 */             Vector<EntityItem> vector = entityItem.getDownLink();
/* 932 */             for (byte b = 0; b < vector.size(); b++) {
/* 933 */               EntityItem entityItem1 = vector.elementAt(b);
/* 934 */               if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 935 */                 Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 936 */                 EntityItem entityItem2 = vector1.elementAt(0);
/* 937 */                 str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 938 */                 ABRUtil.append(param1StringBuffer, "CtryAudRecord.getANNOUNCEMENT looking for downlink of AVAILANNA : Announcement " + (
/* 939 */                     (vector1.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem2.getKey()) : entityItem2.getKey()) + XMLElem.NEWLINE);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } else {
/* 945 */         if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 946 */           EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 947 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 948 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 949 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */           }
/* 951 */           ABRUtil.append(param1StringBuffer, "CtryAudRecord.derivePubFrom foavail thedate: " + str + " COUNTRYLIST: " + 
/*     */               
/* 953 */               PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */         } 
/* 955 */         if ("@@".equals(str) && 
/* 956 */           !this.availDiff.isDeleted() && this.availDiff != null) {
/* 957 */           EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 958 */           Vector<EntityItem> vector = entityItem.getDownLink();
/* 959 */           for (byte b = 0; b < vector.size(); b++) {
/* 960 */             EntityItem entityItem1 = vector.elementAt(b);
/* 961 */             if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 962 */               Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 963 */               EntityItem entityItem2 = vector1.elementAt(0);
/* 964 */               str = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 965 */               ABRUtil.append(param1StringBuffer, "CtryAudRecord.getANNOUNCEMENT looking for downlink of AVAILANNA : Announcement " + (
/* 966 */                   (vector.size() > 1) ? ("There were multiple ANNOUNCEMENTS returned, using first one." + entityItem2.getKey()) : entityItem2.getKey()) + XMLElem.NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 972 */       return str;
/*     */     }
/*     */     
/* 975 */     String getAction() { return this.action; } String getCountry() {
/* 976 */       return this.country;
/*     */     }
/* 978 */     String getPubFrom() { return this.pubfrom; }
/* 979 */     String getPubTo() { return this.pubto; }
/* 980 */     String getEndOfService() { return this.endofservice; } String getAvailStatus() {
/* 981 */       return this.availStatus;
/*     */     }
/* 983 */     boolean isDeleted() { return "Delete".equals(this.action); } String getKey() {
/* 984 */       return this.country;
/*     */     } void dereference() {
/* 986 */       this.availDiff = null;
/* 987 */       this.action = null;
/* 988 */       this.country = null;
/* 989 */       this.availStatus = null;
/*     */       
/* 991 */       this.pubfrom = null;
/* 992 */       this.pubto = null;
/* 993 */       this.endofservice = null;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 997 */       return this.availDiff.getKey() + " " + getKey() + " action:" + this.action;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLAVAILElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */