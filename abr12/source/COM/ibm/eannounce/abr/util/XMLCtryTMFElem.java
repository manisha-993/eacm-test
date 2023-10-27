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
/*     */ 
/*     */ public class XMLCtryTMFElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLCtryTMFElem() {
/*  66 */     super("COUNTRYELEMENT");
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
/*  92 */     Vector<DiffEntity> vector = getPlannedAvails(paramHashtable, paramStringBuffer);
/*     */     
/*  94 */     if (vector.size() > 0) {
/*     */ 
/*     */       
/*  97 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  98 */       for (byte b = 0; b < vector.size(); b++) {
/*  99 */         DiffEntity diffEntity = vector.elementAt(b);
/*     */ 
/*     */         
/* 102 */         buildCtryRecs(treeMap, diffEntity, paramStringBuffer);
/*     */       } 
/*     */ 
/*     */       
/* 106 */       Collection collection = treeMap.values();
/* 107 */       Iterator<CtryRecord> iterator = collection.iterator();
/* 108 */       while (iterator.hasNext()) {
/* 109 */         CtryRecord ctryRecord = iterator.next();
/*     */         
/* 111 */         if (!ctryRecord.isDeleted()) {
/*     */           
/* 113 */           DiffEntity diffEntity1 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "143", "COUNTRYLIST", ctryRecord
/* 114 */               .getCountry(), paramStringBuffer);
/*     */           
/* 116 */           DiffEntity diffEntity2 = getEntityForAttrs(paramHashtable, "AVAIL", "AVAILTYPE", "149", "COUNTRYLIST", ctryRecord
/* 117 */               .getCountry(), paramStringBuffer);
/*     */           
/* 119 */           DiffEntity diffEntity3 = getEntityForAttrs(paramHashtable, "CATLGOR", "OFFCOUNTRY", ctryRecord
/* 120 */               .getCountry(), (String)null, (String)null, paramStringBuffer);
/*     */ 
/*     */           
/* 123 */           ctryRecord.setAllFields(diffEntity1, diffEntity2, diffEntity3, paramStringBuffer);
/*     */         } 
/* 125 */         if (ctryRecord.isDisplayable()) {
/* 126 */           createNodeSet(paramDocument, paramElement, ctryRecord, paramStringBuffer);
/*     */         } else {
/* 128 */           paramStringBuffer.append("XMLCtryTMFElem.addElements no changes found for " + ctryRecord + NEWLINE);
/*     */         } 
/* 130 */         ctryRecord.dereference();
/*     */       } 
/*     */ 
/*     */       
/* 134 */       treeMap.clear();
/*     */     } else {
/* 136 */       paramStringBuffer.append("XMLCtryTMFElem.addElements no planned AVAILs found" + NEWLINE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNodeSet(Document paramDocument, Element paramElement, CtryRecord paramCtryRecord, StringBuffer paramStringBuffer) {
/* 145 */     Element element1 = paramDocument.createElement(this.nodeName);
/* 146 */     addXMLAttrs(element1);
/* 147 */     paramElement.appendChild(element1);
/*     */ 
/*     */     
/* 150 */     Element element2 = paramDocument.createElement("COUNTRYACTION");
/* 151 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getAction()));
/* 152 */     element1.appendChild(element2);
/* 153 */     element2 = paramDocument.createElement("COUNTRY");
/* 154 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getCountry()));
/* 155 */     element1.appendChild(element2);
/* 156 */     element2 = paramDocument.createElement("STATUS");
/* 157 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getAvailStatus()));
/* 158 */     element1.appendChild(element2);
/* 159 */     element2 = paramDocument.createElement("EARLIESTSHIPDATE");
/* 160 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getShipDate()));
/* 161 */     element1.appendChild(element2);
/* 162 */     element2 = paramDocument.createElement("PUBFROM");
/* 163 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getPubFrom()));
/* 164 */     element1.appendChild(element2);
/* 165 */     element2 = paramDocument.createElement("PUBTO");
/* 166 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getPubTo()));
/* 167 */     element1.appendChild(element2);
/* 168 */     element2 = paramDocument.createElement("HIDE");
/* 169 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getHide()));
/* 170 */     element1.appendChild(element2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildCtryRecs(TreeMap<String, CtryRecord> paramTreeMap, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 177 */     paramStringBuffer.append("XMLCtryTMFElem.buildCtryRecs " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 184 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 185 */     if (paramDiffEntity.isDeleted()) {
/*     */       
/* 187 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 188 */       paramStringBuffer.append("XMLCtryTMFElem.buildCtryRecs for deleted avail: ctryAtt " + 
/* 189 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 190 */       if (eANFlagAttribute != null) {
/* 191 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 192 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 194 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 195 */             String str = arrayOfMetaFlag[b].getFlagCode();
/* 196 */             if (paramTreeMap.containsKey(str)) {
/*     */               
/* 198 */               CtryRecord ctryRecord = (CtryRecord)paramTreeMap.get(str);
/* 199 */               paramStringBuffer.append("WARNING buildCtryRecs for deleted " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryRecord + NEWLINE);
/*     */             } else {
/*     */               
/* 202 */               CtryRecord ctryRecord = new CtryRecord(paramDiffEntity, str);
/* 203 */               ctryRecord.setAction("Delete");
/* 204 */               paramTreeMap.put(ctryRecord.getKey(), ctryRecord);
/* 205 */               paramStringBuffer.append("XMLCtryTMFElem.buildCtryRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + ctryRecord
/* 206 */                   .getKey() + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 211 */     } else if (paramDiffEntity.isNew()) {
/*     */       
/* 213 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 214 */       paramStringBuffer.append("XMLCtryTMFElem.buildCtryRecs for new avail: ctryAtt " + 
/* 215 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/* 216 */       if (eANFlagAttribute != null) {
/* 217 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 218 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 220 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 221 */             String str = arrayOfMetaFlag[b].getFlagCode();
/* 222 */             if (paramTreeMap.containsKey(str)) {
/* 223 */               CtryRecord ctryRecord = paramTreeMap.get(str);
/* 224 */               paramStringBuffer.append("WARNING buildCtryRecs for new " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryRecord + NEWLINE);
/*     */               
/* 226 */               ctryRecord.setUpdateAvail(paramDiffEntity);
/*     */             } else {
/* 228 */               CtryRecord ctryRecord = new CtryRecord(paramDiffEntity, str);
/* 229 */               ctryRecord.setAction("Update");
/* 230 */               paramTreeMap.put(ctryRecord.getKey(), ctryRecord);
/* 231 */               paramStringBuffer.append("XMLCtryTMFElem.buildCtryRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryRecord
/* 232 */                   .getKey() + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 238 */       HashSet<String> hashSet1 = new HashSet();
/* 239 */       HashSet<String> hashSet2 = new HashSet();
/*     */       
/* 241 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 242 */       paramStringBuffer.append("XMLCtryTMFElem.buildCtryRecs for curr avail: fAtt " + 
/* 243 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/* 244 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 246 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 247 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 249 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 250 */             hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 256 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 257 */       paramStringBuffer.append("XMLCtryTMFElem.buildCtryRecs for prev avail: fAtt " + 
/* 258 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 259 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 261 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 262 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 264 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 265 */             hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 271 */       Iterator<String> iterator = hashSet2.iterator();
/* 272 */       while (iterator.hasNext()) {
/* 273 */         String str = iterator.next();
/* 274 */         if (!hashSet1.contains(str)) {
/* 275 */           if (paramTreeMap.containsKey(str)) {
/* 276 */             CtryRecord ctryRecord2 = paramTreeMap.get(str);
/* 277 */             paramStringBuffer.append("WARNING buildCtryRecs for added ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryRecord2 + NEWLINE);
/*     */             
/* 279 */             ctryRecord2.setUpdateAvail(paramDiffEntity); continue;
/*     */           } 
/* 281 */           CtryRecord ctryRecord1 = new CtryRecord(paramDiffEntity, str);
/* 282 */           ctryRecord1.setAction("Update");
/* 283 */           paramTreeMap.put(ctryRecord1.getKey(), ctryRecord1);
/* 284 */           paramStringBuffer.append("XMLCtryTMFElem.buildCtryRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryRecord1
/* 285 */               .getKey() + NEWLINE);
/*     */           
/*     */           continue;
/*     */         } 
/* 289 */         if (paramTreeMap.containsKey(str)) {
/* 290 */           CtryRecord ctryRecord1 = paramTreeMap.get(str);
/* 291 */           paramStringBuffer.append("WARNING buildCtryRecs for existing ctry " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryRecord1 + NEWLINE);
/*     */           continue;
/*     */         } 
/* 294 */         CtryRecord ctryRecord = new CtryRecord(paramDiffEntity, str);
/* 295 */         paramTreeMap.put(ctryRecord.getKey(), ctryRecord);
/* 296 */         paramStringBuffer.append("XMLCtryTMFElem.buildCtryRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryRecord
/* 297 */             .getKey() + NEWLINE);
/*     */       } 
/*     */ 
/*     */       
/* 301 */       iterator = hashSet1.iterator();
/* 302 */       while (iterator.hasNext()) {
/* 303 */         String str = iterator.next();
/* 304 */         if (!hashSet2.contains(str)) {
/* 305 */           if (paramTreeMap.containsKey(str)) {
/* 306 */             CtryRecord ctryRecord1 = paramTreeMap.get(str);
/* 307 */             paramStringBuffer.append("WARNING buildCtryRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, keeping orig " + ctryRecord1 + NEWLINE);
/*     */             continue;
/*     */           } 
/* 310 */           CtryRecord ctryRecord = new CtryRecord(paramDiffEntity, str);
/* 311 */           ctryRecord.setAction("Delete");
/* 312 */           paramTreeMap.put(ctryRecord.getKey(), ctryRecord);
/* 313 */           paramStringBuffer.append("XMLCtryTMFElem.buildCtryRecs for delete ctry:" + paramDiffEntity.getKey() + " rec: " + ctryRecord
/* 314 */               .getKey() + NEWLINE);
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
/*     */   
/*     */   private Vector getPlannedAvails(Hashtable paramHashtable, StringBuffer paramStringBuffer) {
/* 327 */     Vector<DiffEntity> vector1 = new Vector(1);
/* 328 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/*     */     
/* 330 */     paramStringBuffer.append("XMLCtryTMFElem.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/* 331 */         .size())) + NEWLINE);
/* 332 */     if (vector2 == null) {
/* 333 */       return vector1;
/*     */     }
/*     */ 
/*     */     
/* 337 */     for (byte b = 0; b < vector2.size(); b++) {
/* 338 */       DiffEntity diffEntity = vector2.elementAt(b);
/* 339 */       EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 340 */       EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/* 341 */       if (diffEntity.isDeleted()) {
/* 342 */         paramStringBuffer.append("XMLCtryTMFElem.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/* 343 */             PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE") + NEWLINE);
/* 344 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/* 345 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 346 */           vector1.add(diffEntity);
/*     */         }
/*     */       } else {
/* 349 */         paramStringBuffer.append("XMLCtryTMFElem.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/* 350 */             PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE") + NEWLINE);
/* 351 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/* 352 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 353 */           vector1.add(diffEntity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 358 */     return vector1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DiffEntity getEntityForAttrs(Hashtable paramHashtable, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, StringBuffer paramStringBuffer) {
/* 367 */     DiffEntity diffEntity = null;
/* 368 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get(paramString1);
/*     */     
/* 370 */     paramStringBuffer.append("XMLCtryTMFElem.getEntityForAttrs looking for " + paramString2 + ":" + paramString3 + " and " + paramString4 + ":" + paramString5 + " in " + paramString1 + " allVct.size:" + ((vector == null) ? "null" : ("" + vector
/* 371 */         .size())) + NEWLINE);
/* 372 */     if (vector == null) {
/* 373 */       return diffEntity;
/*     */     }
/*     */ 
/*     */     
/* 377 */     for (byte b = 0; b < vector.size(); b++) {
/* 378 */       DiffEntity diffEntity1 = vector.elementAt(b);
/* 379 */       EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/* 380 */       EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/* 381 */       if (diffEntity1.isDeleted()) {
/* 382 */         paramStringBuffer.append("XMLCtryTMFElem.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 383 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + ((paramString4 == null) ? "" : (" " + paramString4 + ":" + 
/* 384 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4))) + NEWLINE);
/* 385 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/* 386 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 387 */           if (paramString4 != null) {
/* 388 */             eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/* 389 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 390 */               diffEntity = diffEntity1;
/*     */             }
/*     */           } else {
/* 393 */             diffEntity = diffEntity1;
/*     */           }
/*     */         
/*     */         }
/* 397 */       } else if (diffEntity1.isNew()) {
/* 398 */         paramStringBuffer.append("XMLCtryTMFElem.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 399 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + ((paramString4 == null) ? "" : (" " + paramString4 + ":" + 
/* 400 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4))) + NEWLINE);
/* 401 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/* 402 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 403 */           if (paramString4 != null) {
/* 404 */             eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/* 405 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 406 */               diffEntity = diffEntity1;
/*     */               break;
/*     */             } 
/*     */           } else {
/* 410 */             diffEntity = diffEntity1;
/*     */             
/*     */             break;
/*     */           } 
/*     */         }
/*     */       } else {
/* 416 */         paramStringBuffer.append("XMLCtryTMFElem.getEntityForAttrs checking[" + b + "]: current " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 417 */             PokUtils.getAttributeFlagValue(entityItem1, paramString2) + ((paramString4 == null) ? "" : (" " + paramString4 + ":" + 
/* 418 */             PokUtils.getAttributeFlagValue(entityItem1, paramString4))) + NEWLINE);
/* 419 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/* 420 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 421 */           if (paramString4 != null) {
/* 422 */             eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/* 423 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 424 */               diffEntity = diffEntity1;
/*     */               break;
/*     */             } 
/*     */           } else {
/* 428 */             diffEntity = diffEntity1;
/*     */             break;
/*     */           } 
/*     */         }
/* 432 */         paramStringBuffer.append("XMLCtryTMFElem.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 433 */             PokUtils.getAttributeFlagValue(entityItem2, paramString2) + ((paramString4 == null) ? "" : (" " + paramString4 + ":" + 
/* 434 */             PokUtils.getAttributeFlagValue(entityItem2, paramString4))) + NEWLINE);
/* 435 */         eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/* 436 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 437 */           if (paramString4 != null) {
/* 438 */             eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/* 439 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 440 */               diffEntity = diffEntity1;
/*     */             }
/*     */           } else {
/*     */             
/* 444 */             diffEntity = diffEntity1;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 452 */     return diffEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CtryRecord
/*     */   {
/* 459 */     private String action = null;
/*     */     private String country;
/* 461 */     private String earliestshipdate = "@@";
/* 462 */     private String availStatus = "@@";
/* 463 */     private String pubfrom = "@@";
/* 464 */     private String pubto = "@@";
/* 465 */     private String hide = "@@"; private DiffEntity availDiff;
/*     */     
/*     */     boolean isDisplayable() {
/* 468 */       return (this.action != null);
/*     */     }
/*     */     CtryRecord(DiffEntity param1DiffEntity, String param1String) {
/* 471 */       this.country = param1String;
/* 472 */       this.availDiff = param1DiffEntity;
/*     */     } void setAction(String param1String) {
/* 474 */       this.action = param1String;
/*     */     } void setUpdateAvail(DiffEntity param1DiffEntity) {
/* 476 */       this.availDiff = param1DiffEntity;
/* 477 */       setAction("Update");
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
/*     */     void setAllFields(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, DiffEntity param1DiffEntity3, StringBuffer param1StringBuffer) {
/* 498 */       param1StringBuffer.append("CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + XMLElem.NEWLINE);
/*     */       
/* 500 */       EntityItem entityItem1 = this.availDiff.getCurrentEntityItem();
/* 501 */       EntityItem entityItem2 = this.availDiff.getPriorEntityItem();
/*     */ 
/*     */ 
/*     */       
/* 505 */       this.earliestshipdate = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", ", ", "@@", false);
/*     */       
/* 507 */       String str1 = "@@";
/* 508 */       if (entityItem2 != null) {
/* 509 */         str1 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", ", ", "@@", false);
/*     */       }
/* 511 */       param1StringBuffer.append("CtryRecord.setAllFields curshipdate: " + this.earliestshipdate + " prevdate: " + str1 + XMLElem.NEWLINE);
/*     */ 
/*     */       
/* 514 */       if (!str1.equals(this.earliestshipdate)) {
/* 515 */         setAction("Update");
/*     */       }
/*     */       
/* 518 */       this.availStatus = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/* 519 */       if (this.availStatus == null) {
/* 520 */         this.availStatus = "@@";
/*     */       }
/*     */       
/* 523 */       String str2 = "@@";
/* 524 */       if (entityItem2 != null) {
/* 525 */         str2 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 526 */         if (str2 == null) {
/* 527 */           str2 = "@@";
/*     */         }
/*     */       } 
/* 530 */       param1StringBuffer.append("CtryRecord.setAllFields curstatus: " + this.availStatus + " prevstatus: " + str2 + XMLElem.NEWLINE);
/*     */       
/* 532 */       if (!str2.equals(this.availStatus)) {
/* 533 */         setAction("Update");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 538 */       this.pubfrom = derivePubFrom(param1DiffEntity1, param1DiffEntity3, false, param1StringBuffer);
/* 539 */       String str3 = derivePubFrom(param1DiffEntity1, param1DiffEntity3, true, param1StringBuffer);
/* 540 */       param1StringBuffer.append("CtryRecord.setAllFields pubfromT2: " + this.pubfrom + " pubfromT1: " + str3 + XMLElem.NEWLINE);
/*     */       
/* 542 */       if (!this.pubfrom.equals(str3)) {
/* 543 */         setAction("Update");
/*     */       }
/*     */ 
/*     */       
/* 547 */       this.pubto = derivePubTo(param1DiffEntity2, param1DiffEntity3, false, param1StringBuffer);
/* 548 */       String str4 = derivePubTo(param1DiffEntity2, param1DiffEntity3, true, param1StringBuffer);
/* 549 */       if (!this.pubto.equals(str4)) {
/* 550 */         setAction("Update");
/*     */       }
/* 552 */       param1StringBuffer.append("CtryRecord.setAllFields action:" + this.action + " pubtoT2: " + this.pubto + " pubtoT1: " + str4 + XMLElem.NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 561 */       if (param1DiffEntity3 != null) {
/* 562 */         if (!param1DiffEntity3.isDeleted()) {
/* 563 */           boolean bool = true;
/*     */           
/* 565 */           if (param1DiffEntity3.isNew()) {
/* 566 */             setAction("Update");
/*     */           } else {
/* 568 */             entityItem1 = param1DiffEntity3.getCurrentEntityItem();
/* 569 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("OFFCOUNTRY");
/* 570 */             bool = (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) ? true : false;
/* 571 */             if (!bool) {
/* 572 */               setAction("Update");
/*     */             }
/*     */           } 
/*     */           
/* 576 */           if (bool) {
/* 577 */             entityItem1 = param1DiffEntity3.getCurrentEntityItem();
/* 578 */             this.hide = PokUtils.getAttributeValue(entityItem1, "CATHIDE", ", ", "@@", false);
/*     */           } 
/*     */         } else {
/* 581 */           setAction("Update");
/*     */         } 
/*     */         
/* 584 */         param1StringBuffer.append("CtryRecord.setAllFields after catlgor action:" + this.action + XMLElem.NEWLINE);
/*     */       } 
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
/*     */     private String derivePubTo(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 597 */       param1StringBuffer.append("CtryRecord.derivePubTo  loAvailDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1
/* 598 */           .getKey()) + " catlgorDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 599 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */       
/* 601 */       String str = "@@";
/* 602 */       if (param1Boolean) {
/* 603 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isNew()) {
/* 604 */           EntityItem entityItem = param1DiffEntity2.getPriorEntityItem();
/*     */           
/* 606 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("OFFCOUNTRY");
/* 607 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 608 */             str = PokUtils.getAttributeValue(entityItem, "PUBTO", "", "@@", false);
/*     */           }
/* 610 */           param1StringBuffer.append("CtryRecord.derivePubTo catlgor thedate: " + str + " OFFCOUNTRY: " + 
/* 611 */               PokUtils.getAttributeFlagValue(entityItem, "OFFCOUNTRY") + XMLElem.NEWLINE);
/*     */         } 
/* 613 */         if ("@@".equals(str))
/*     */         {
/* 615 */           if (param1DiffEntity1 != null && !param1DiffEntity1.isNew()) {
/* 616 */             EntityItem entityItem = param1DiffEntity1.getPriorEntityItem();
/* 617 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 618 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 619 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */             }
/* 621 */             param1StringBuffer.append("CtryRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/* 622 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */           } 
/*     */         }
/*     */       } else {
/* 626 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isDeleted()) {
/* 627 */           EntityItem entityItem = param1DiffEntity2.getCurrentEntityItem();
/*     */           
/* 629 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("OFFCOUNTRY");
/* 630 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 631 */             str = PokUtils.getAttributeValue(entityItem, "PUBTO", "", "@@", false);
/*     */           }
/* 633 */           param1StringBuffer.append("CtryRecord.derivePubTo catlgor thedate: " + str + " OFFCOUNTRY: " + 
/* 634 */               PokUtils.getAttributeFlagValue(entityItem, "OFFCOUNTRY") + XMLElem.NEWLINE);
/*     */         } 
/* 636 */         if ("@@".equals(str))
/*     */         {
/* 638 */           if (param1DiffEntity1 != null && !param1DiffEntity1.isDeleted()) {
/* 639 */             EntityItem entityItem = param1DiffEntity1.getCurrentEntityItem();
/* 640 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 641 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 642 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */             }
/* 644 */             param1StringBuffer.append("CtryRecord.derivePubTo loavail thedate: " + str + " COUNTRYLIST: " + 
/* 645 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 650 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String derivePubFrom(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 661 */       String str = "@@";
/* 662 */       param1StringBuffer.append("CtryRecord.derivePubFrom availDiff: " + this.availDiff.getKey() + " foAvailDiff: " + ((param1DiffEntity1 == null) ? "null" : param1DiffEntity1
/* 663 */           .getKey()) + " catlgorDiff: " + ((param1DiffEntity2 == null) ? "null" : param1DiffEntity2
/* 664 */           .getKey()) + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */       
/* 666 */       if (param1Boolean) {
/* 667 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isNew()) {
/* 668 */           EntityItem entityItem = param1DiffEntity2.getPriorEntityItem();
/*     */           
/* 670 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("OFFCOUNTRY");
/* 671 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 672 */             str = PokUtils.getAttributeValue(entityItem, "PUBFROM", "", "@@", false);
/*     */           }
/* 674 */           param1StringBuffer.append("CtryRecord.derivePubFrom catlgor thedate: " + str + " OFFCOUNTRY: " + 
/*     */               
/* 676 */               PokUtils.getAttributeFlagValue(entityItem, "OFFCOUNTRY") + XMLElem.NEWLINE);
/*     */         } 
/* 678 */         if ("@@".equals(str))
/*     */         {
/* 680 */           if (param1DiffEntity1 != null && !param1DiffEntity1.isNew()) {
/* 681 */             EntityItem entityItem = param1DiffEntity1.getPriorEntityItem();
/* 682 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 683 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 684 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */             }
/* 686 */             param1StringBuffer.append("CtryRecord.derivePubFrom foavail thedate: " + str + " COUNTRYLIST: " + 
/*     */                 
/* 688 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */           } 
/*     */         }
/* 691 */         if ("@@".equals(str))
/*     */         {
/* 693 */           if (!this.availDiff.isNew()) {
/* 694 */             EntityItem entityItem = this.availDiff.getPriorEntityItem();
/* 695 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 696 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 697 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */             }
/* 699 */             param1StringBuffer.append("CtryRecord.derivePubFrom plannedavail thedate: " + str + " COUNTRYLIST: " + 
/*     */                 
/* 701 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */           } 
/*     */         }
/*     */       } else {
/* 705 */         if (param1DiffEntity2 != null && !param1DiffEntity2.isDeleted()) {
/* 706 */           EntityItem entityItem = param1DiffEntity2.getCurrentEntityItem();
/*     */           
/* 708 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("OFFCOUNTRY");
/* 709 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 710 */             str = PokUtils.getAttributeValue(entityItem, "PUBFROM", "", "@@", false);
/* 711 */             param1StringBuffer.append("CtryRecord.derivePubFrom catlgor thedate: " + str + " OFFCOUNTRY: " + 
/*     */                 
/* 713 */                 PokUtils.getAttributeFlagValue(entityItem, "OFFCOUNTRY") + XMLElem.NEWLINE);
/*     */           } 
/*     */         } 
/* 716 */         if ("@@".equals(str))
/*     */         {
/* 718 */           if (param1DiffEntity1 != null && !param1DiffEntity1.isDeleted()) {
/* 719 */             EntityItem entityItem = param1DiffEntity1.getCurrentEntityItem();
/* 720 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 721 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 722 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */             }
/* 724 */             param1StringBuffer.append("CtryRecord.derivePubFrom foavail thedate: " + str + " COUNTRYLIST: " + 
/*     */                 
/* 726 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */           } 
/*     */         }
/* 729 */         if ("@@".equals(str))
/*     */         {
/* 731 */           if (!this.availDiff.isDeleted()) {
/* 732 */             EntityItem entityItem = this.availDiff.getCurrentEntityItem();
/* 733 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 734 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 735 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "@@", false);
/*     */             }
/* 737 */             param1StringBuffer.append("CtryRecord.derivePubFrom plannedavail thedate: " + str + " COUNTRYLIST: " + 
/*     */                 
/* 739 */                 PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 744 */       return str;
/*     */     }
/*     */     
/* 747 */     String getAction() { return this.action; }
/* 748 */     String getCountry() { return this.country; }
/* 749 */     String getShipDate() { return this.earliestshipdate; }
/* 750 */     String getPubFrom() { return this.pubfrom; }
/* 751 */     String getPubTo() { return this.pubto; }
/* 752 */     String getHide() { return this.hide; } String getAvailStatus() {
/* 753 */       return this.availStatus;
/*     */     }
/* 755 */     boolean isDeleted() { return "Delete".equals(this.action); } String getKey() {
/* 756 */       return this.country;
/*     */     } void dereference() {
/* 758 */       this.availDiff = null;
/* 759 */       this.action = null;
/* 760 */       this.availStatus = null;
/* 761 */       this.country = null;
/* 762 */       this.earliestshipdate = null;
/* 763 */       this.pubfrom = null;
/* 764 */       this.pubto = null;
/* 765 */       this.hide = null;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 769 */       return this.availDiff.getKey() + " " + getKey() + " action:" + this.action;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLCtryTMFElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */