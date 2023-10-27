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
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
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
/*     */ public class XMLCtryElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLCtryElem() {
/*  65 */     super("COUNTRYELEMENT");
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  91 */     Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, paramStringBuffer);
/*     */     
/*  93 */     if (vector.size() > 0) {
/*     */ 
/*     */       
/*  96 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  97 */       for (byte b = 0; b < vector.size(); b++) {
/*  98 */         DiffEntity diffEntity = vector.elementAt(b);
/*  99 */         buildCtryRecs(treeMap, diffEntity, paramStringBuffer);
/*     */       } 
/*     */ 
/*     */       
/* 103 */       Collection collection = treeMap.values();
/* 104 */       Iterator<CtryRecord> iterator = collection.iterator();
/* 105 */       while (iterator.hasNext()) {
/* 106 */         CtryRecord ctryRecord = iterator.next();
/*     */         
/* 108 */         if (!ctryRecord.isDeleted()) {
/*     */           
/* 110 */           DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryRecord
/* 111 */               .getCountry(), paramStringBuffer);
/*     */           
/* 113 */           DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryRecord
/* 114 */               .getCountry(), paramStringBuffer);
/*     */ 
/*     */           
/* 117 */           ctryRecord.setAllFields(diffEntity1, diffEntity2, paramStringBuffer);
/*     */         } 
/* 119 */         if (ctryRecord.isDisplayable()) {
/* 120 */           createNodeSet(paramDocument, paramElement, ctryRecord, paramStringBuffer);
/*     */         } else {
/* 122 */           paramStringBuffer.append("XMLCtryElem.addElements no changes found for " + ctryRecord + NEWLINE);
/*     */         } 
/* 124 */         ctryRecord.dereference();
/*     */       } 
/*     */ 
/*     */       
/* 128 */       treeMap.clear();
/*     */     } else {
/* 130 */       paramStringBuffer.append("XMLCtryElem.addElements no planned AVAILs found" + NEWLINE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNodeSet(Document paramDocument, Element paramElement, CtryRecord paramCtryRecord, StringBuffer paramStringBuffer) {
/* 139 */     Element element1 = paramDocument.createElement(this.nodeName);
/* 140 */     addXMLAttrs(element1);
/* 141 */     paramElement.appendChild(element1);
/*     */ 
/*     */     
/* 144 */     Element element2 = paramDocument.createElement("COUNTRYACTION");
/* 145 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getAction()));
/* 146 */     element1.appendChild(element2);
/* 147 */     element2 = paramDocument.createElement("COUNTRY");
/* 148 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getCountry()));
/* 149 */     element1.appendChild(element2);
/* 150 */     element2 = paramDocument.createElement("STATUS");
/* 151 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getAvailStatus()));
/* 152 */     element1.appendChild(element2);
/* 153 */     element2 = paramDocument.createElement("PUBFROM");
/* 154 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getPubFrom()));
/* 155 */     element1.appendChild(element2);
/* 156 */     element2 = paramDocument.createElement("PUBTO");
/* 157 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getPubTo()));
/* 158 */     element1.appendChild(element2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildCtryRecs(TreeMap<String, CtryRecord> paramTreeMap, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 165 */     paramStringBuffer.append("XMLCtryElem.buildCtryRecs " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 172 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 173 */     if (paramDiffEntity.isDeleted()) {
/*     */       
/* 175 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 176 */       paramStringBuffer.append("XMLCtryElem.buildCtryRecs for deleted avail: ctryAtt " + 
/* 177 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 178 */       if (eANFlagAttribute != null) {
/* 179 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 180 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 182 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 183 */             String str = arrayOfMetaFlag[b].getFlagCode();
/* 184 */             if (paramTreeMap.containsKey(str)) {
/*     */               
/* 186 */               CtryRecord ctryRecord = (CtryRecord)paramTreeMap.get(str);
/* 187 */               paramStringBuffer.append("WARNING buildCtryRecs for deleted " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryRecord + NEWLINE);
/*     */             } else {
/*     */               
/* 190 */               CtryRecord ctryRecord = new CtryRecord(paramDiffEntity, str);
/* 191 */               ctryRecord.setAction("Delete");
/* 192 */               paramTreeMap.put(ctryRecord.getKey(), ctryRecord);
/* 193 */               paramStringBuffer.append("XMLCtryElem.buildCtryRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + ctryRecord
/* 194 */                   .getKey() + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 199 */     } else if (paramDiffEntity.isNew()) {
/*     */       
/* 201 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 202 */       paramStringBuffer.append("XMLCtryElem.buildCtryRecs for new avail: ctryAtt " + 
/* 203 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/* 204 */       if (eANFlagAttribute != null) {
/* 205 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 206 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 208 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 209 */             String str = arrayOfMetaFlag[b].getFlagCode();
/* 210 */             if (paramTreeMap.containsKey(str)) {
/* 211 */               CtryRecord ctryRecord = paramTreeMap.get(str);
/* 212 */               paramStringBuffer.append("WARNING buildCtryRecs for new " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryRecord + NEWLINE);
/*     */               
/* 214 */               ctryRecord.setUpdateAvail(paramDiffEntity);
/*     */             } else {
/* 216 */               CtryRecord ctryRecord = new CtryRecord(paramDiffEntity, str);
/* 217 */               ctryRecord.setAction("Update");
/* 218 */               paramTreeMap.put(ctryRecord.getKey(), ctryRecord);
/* 219 */               paramStringBuffer.append("XMLCtryElem.buildCtryRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryRecord
/* 220 */                   .getKey() + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 226 */       HashSet<String> hashSet1 = new HashSet();
/* 227 */       HashSet<String> hashSet2 = new HashSet();
/*     */       
/* 229 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 230 */       paramStringBuffer.append("XMLCtryElem.buildCtryRecs for curr avail: fAtt " + 
/* 231 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/* 232 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 234 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 235 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 237 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 238 */             hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 244 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 245 */       paramStringBuffer.append("XMLCtryElem.buildCtryRecs for prev avail: fAtt " + 
/* 246 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 247 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 249 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 250 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 252 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 253 */             hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 259 */       Iterator<String> iterator = hashSet2.iterator();
/* 260 */       while (iterator.hasNext()) {
/* 261 */         String str = iterator.next();
/* 262 */         if (!hashSet1.contains(str)) {
/* 263 */           if (paramTreeMap.containsKey(str)) {
/* 264 */             CtryRecord ctryRecord2 = paramTreeMap.get(str);
/* 265 */             paramStringBuffer.append("WARNING buildCtryRecs for added ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryRecord2 + NEWLINE);
/*     */             
/* 267 */             ctryRecord2.setUpdateAvail(paramDiffEntity); continue;
/*     */           } 
/* 269 */           CtryRecord ctryRecord1 = new CtryRecord(paramDiffEntity, str);
/* 270 */           ctryRecord1.setAction("Update");
/* 271 */           paramTreeMap.put(ctryRecord1.getKey(), ctryRecord1);
/* 272 */           paramStringBuffer.append("XMLCtryElem.buildCtryRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryRecord1
/* 273 */               .getKey() + NEWLINE);
/*     */           
/*     */           continue;
/*     */         } 
/* 277 */         if (paramTreeMap.containsKey(str)) {
/* 278 */           CtryRecord ctryRecord1 = paramTreeMap.get(str);
/* 279 */           paramStringBuffer.append("WARNING buildCtryRecs for existing ctry " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryRecord1 + NEWLINE);
/*     */           continue;
/*     */         } 
/* 282 */         CtryRecord ctryRecord = new CtryRecord(paramDiffEntity, str);
/* 283 */         paramTreeMap.put(ctryRecord.getKey(), ctryRecord);
/* 284 */         paramStringBuffer.append("XMLCtryElem.buildCtryRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryRecord
/* 285 */             .getKey() + NEWLINE);
/*     */       } 
/*     */ 
/*     */       
/* 289 */       iterator = hashSet1.iterator();
/* 290 */       while (iterator.hasNext()) {
/* 291 */         String str = iterator.next();
/* 292 */         if (!hashSet2.contains(str)) {
/* 293 */           if (paramTreeMap.containsKey(str)) {
/* 294 */             CtryRecord ctryRecord1 = paramTreeMap.get(str);
/* 295 */             paramStringBuffer.append("WARNING buildCtryRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryRecord1 + NEWLINE);
/*     */             continue;
/*     */           } 
/* 298 */           CtryRecord ctryRecord = new CtryRecord(paramDiffEntity, str);
/* 299 */           ctryRecord.setAction("Delete");
/* 300 */           paramTreeMap.put(ctryRecord.getKey(), ctryRecord);
/* 301 */           paramStringBuffer.append("XMLCtryElem.buildCtryRecs for delete ctry:" + paramDiffEntity.getKey() + " rec: " + ctryRecord
/* 302 */               .getKey() + NEWLINE);
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
/* 314 */     Vector<DiffEntity> vector1 = new Vector(1);
/* 315 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/*     */     
/* 317 */     paramStringBuffer.append("XMLCtryElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/* 318 */         .size())) + NEWLINE);
/* 319 */     if (vector2 == null) {
/* 320 */       return vector1;
/*     */     }
/*     */     
/* 323 */     for (byte b = 0; b < vector2.size(); b++) {
/* 324 */       DiffEntity diffEntity = vector2.elementAt(b);
/* 325 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 326 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/* 327 */       if (diffEntity.isDeleted()) {
/* 328 */         paramStringBuffer.append("XMLCtryElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/* 329 */             PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/* 330 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/* 331 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 332 */           vector1.add(diffEntity);
/*     */         }
/*     */       } else {
/* 335 */         paramStringBuffer.append("XMLCtryElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/* 336 */             PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/* 337 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/* 338 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 339 */           vector1.add(diffEntity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     return vector1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DiffEntity getEntityForAttrs(Hashtable paramHashtable, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, StringBuffer paramStringBuffer) {
/* 353 */     DiffEntity diffEntity = null;
/* 354 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get(paramString1);
/*     */     
/* 356 */     paramStringBuffer.append("XMLCtryElem.getEntityForAttrs looking for " + paramString2 + ":" + paramString3 + " and " + paramString4 + ":" + paramString5 + " in " + paramString1 + " allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 357 */         .size())) + NEWLINE);
/* 358 */     if (vector == null) {
/* 359 */       return diffEntity;
/*     */     }
/*     */     
/* 362 */     for (byte b = 0; b < vector.size(); b++) {
/* 363 */       DiffEntity diffEntity1 = vector.elementAt(b);
/* 364 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/* 365 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/* 366 */       if (diffEntity1.isDeleted()) {
/* 367 */         paramStringBuffer.append("XMLCtryElem.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 368 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/* 369 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/* 370 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/* 371 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 372 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/* 373 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 374 */             diffEntity = diffEntity1;
/*     */           }
/*     */         }
/*     */       
/* 378 */       } else if (diffEntity1.isNew()) {
/* 379 */         paramStringBuffer.append("XMLCtryElem.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 380 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/* 381 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/* 382 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/* 383 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 384 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/* 385 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 386 */             diffEntity = diffEntity1;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 392 */         paramStringBuffer.append("XMLCtryElem.getEntityForAttrs checking[" + b + "]: current " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 393 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/* 394 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/* 395 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/* 396 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 397 */           eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/* 398 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 399 */             diffEntity = diffEntity1;
/*     */             break;
/*     */           } 
/*     */         } 
/* 403 */         paramStringBuffer.append("XMLCtryElem.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 404 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/* 405 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/* 406 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/* 407 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 408 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/* 409 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 410 */             diffEntity = diffEntity1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 418 */     return diffEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CtryRecord
/*     */   {
/* 425 */     private String action = null;
/*     */     private String country;
/* 427 */     private String availStatus = "@@";
/* 428 */     private String pubfrom = "@@";
/* 429 */     private String pubto = "@@"; private DiffEntity availDiff;
/*     */     
/*     */     boolean isDisplayable() {
/* 432 */       return (this.action != null);
/*     */     }
/*     */     CtryRecord(DiffEntity param1DiffEntity, String param1String) {
/* 435 */       this.country = param1String;
/* 436 */       this.availDiff = param1DiffEntity;
/*     */     } void setAction(String param1String) {
/* 438 */       this.action = param1String;
/*     */     } void setUpdateAvail(DiffEntity param1DiffEntity) {
/* 440 */       this.availDiff = param1DiffEntity;
/* 441 */       setAction("Update");
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
/*     */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, StringBuffer param1StringBuffer) {
/* 459 */       param1StringBuffer.append("CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + XMLElem.NEWLINE);
/* 460 */       EntityItem entityItem1 = this.availDiff.getCurrentEntityItem();
/* 461 */       EntityItem entityItem2 = this.availDiff.getPriorEntityItem();
/*     */ 
/*     */       
/* 464 */       this.availStatus = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/* 465 */       if (this.availStatus == null) {
/* 466 */         this.availStatus = "@@";
/*     */       }
/*     */       
/* 469 */       String str1 = "@@";
/* 470 */       if (entityItem2 != null) {
/* 471 */         str1 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 472 */         if (str1 == null) {
/* 473 */           str1 = "@@";
/*     */         }
/*     */       } 
/* 476 */       param1StringBuffer.append("CtryRecord.setAllFields curstatus: " + this.availStatus + " prevstatus: " + str1 + XMLElem.NEWLINE);
/*     */ 
/*     */       
/* 479 */       if (!str1.equals(this.availStatus)) {
/* 480 */         setAction("Update");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 485 */       this.pubfrom = derivePubFrom(param1DiffEntity1, false, param1StringBuffer);
/* 486 */       String str2 = derivePubFrom(param1DiffEntity1, true, param1StringBuffer);
/* 487 */       param1StringBuffer.append("CtryRecord.setAllFields pubfromT2: " + this.pubfrom + " pubfromT1: " + str2 + XMLElem.NEWLINE);
/*     */       
/* 489 */       if (!this.pubfrom.equals(str2)) {
/* 490 */         setAction("Update");
/*     */       }
/*     */ 
/*     */       
/* 494 */       this.pubto = derivePubTo(param1DiffEntity2, false, param1StringBuffer);
/* 495 */       String str3 = derivePubTo(param1DiffEntity2, true, param1StringBuffer);
/* 496 */       if (!this.pubto.equals(str3)) {
/* 497 */         setAction("Update");
/*     */       }
/* 499 */       param1StringBuffer.append("CtryRecord.setAllFields action:" + this.action + " pubtoT2: " + this.pubto + " pubtoT1: " + str3 + XMLElem.NEWLINE);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String derivePubTo(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 510 */       param1StringBuffer.append("CtryRecord.derivePubTo  loAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 511 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */       
/* 513 */       String str = "@@";
/* 514 */       if (param1Boolean) {
/*     */         
/* 516 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 517 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 518 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 519 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 520 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */           }
/* 522 */           param1StringBuffer.append("CtryRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/*     */               
/* 524 */               PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */         }
/*     */       
/*     */       }
/* 528 */       else if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 529 */         EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 530 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 531 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 532 */           str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */         }
/* 534 */         param1StringBuffer.append("CtryRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/*     */             
/* 536 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */       } 
/*     */ 
/*     */       
/* 540 */       return str;
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
/* 551 */       String str = "@@";
/* 552 */       param1StringBuffer.append("CtryRecord.derivePubFrom availDiff: " + this.availDiff.getKey() + " foAvailDiff: " + ((param1DiffEntity == null) ? "null" : param1DiffEntity
/* 553 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */       
/* 555 */       if (param1Boolean) {
/*     */         
/* 557 */         if (param1DiffEntity != null && !param1DiffEntity.isNew()) {
/* 558 */           EntityItem entityItem = param1DiffEntity.getPriorEntityItem();
/* 559 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 560 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 561 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */           }
/* 563 */           param1StringBuffer.append("CtryRecord.derivePubFrom foavail thedate: " + str + " COUNTRYLIST: " + 
/*     */               
/* 565 */               PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */         } 
/*     */         
/* 568 */         if ("@@".equals(str))
/*     */         {
/* 570 */           if (!this.availDiff.isNew()) {
/* 571 */             EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 572 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 573 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 574 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */             }
/* 576 */             param1StringBuffer.append("CtryRecord.derivePubFrom plannedavail thedate: " + str + " COUNTRYLIST: " + 
/*     */                 
/* 578 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */           } 
/*     */         }
/*     */       } else {
/*     */         
/* 583 */         if (param1DiffEntity != null && !param1DiffEntity.isDeleted()) {
/* 584 */           EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 585 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 586 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 587 */             str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */           }
/* 589 */           param1StringBuffer.append("CtryRecord.derivePubFrom foavail thedate: " + str + " COUNTRYLIST: " + 
/*     */               
/* 591 */               PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */         } 
/*     */         
/* 594 */         if ("@@".equals(str))
/*     */         {
/* 596 */           if (!this.availDiff.isDeleted()) {
/* 597 */             EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 598 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 599 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 600 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */             }
/* 602 */             param1StringBuffer.append("CtryRecord.derivePubFrom plannedavail thedate: " + str + " COUNTRYLIST: " + 
/*     */                 
/* 604 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 609 */       return str;
/*     */     }
/*     */     
/* 612 */     String getAction() { return this.action; }
/* 613 */     String getCountry() { return this.country; }
/* 614 */     String getAvailStatus() { return this.availStatus; }
/* 615 */     String getPubFrom() { return this.pubfrom; } String getPubTo() {
/* 616 */       return this.pubto;
/*     */     }
/* 618 */     boolean isDeleted() { return "Delete".equals(this.action); } String getKey() {
/* 619 */       return this.country;
/*     */     } void dereference() {
/* 621 */       this.availDiff = null;
/* 622 */       this.action = null;
/* 623 */       this.country = null;
/* 624 */       this.availStatus = null;
/* 625 */       this.pubfrom = null;
/* 626 */       this.pubto = null;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 630 */       return this.availDiff.getKey() + " " + getKey() + " action:" + this.action;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLCtryElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */