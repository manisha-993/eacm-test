/*     */ package COM.ibm.eannounce.abr.util;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
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
/*     */ public class XMLSWSTMFAVAILElem extends XMLElem {
/*     */   public XMLSWSTMFAVAILElem() {
/*  25 */     super("AVAILABILITYELEMENT");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  34 */     ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1:parentItem: " + paramDiffEntity.getKey() + NEWLINE);
/*     */     
/*  36 */     boolean bool = false;
/*  37 */     boolean bool1 = false;
/*  38 */     String str = "1980-01-01-00.00.00.000000";
/*  39 */     boolean bool2 = true;
/*  40 */     bool = AvailUtil.iscompatmodel();
/*  41 */     ABRUtil.append(paramStringBuffer, "compatModel compatbility mode:" + bool);
/*  42 */     if (!bool) {
/*     */       
/*  44 */       String str1 = null;
/*  45 */       str1 = (String)paramHashtable.get("_chSTATUS");
/*  46 */       ABRUtil.append(paramStringBuffer, "the status is" + str1 + NEWLINE);
/*  47 */       if ("0020".equals(str1)) {
/*  48 */         bool1 = true;
/*     */       } else {
/*  50 */         bool1 = false;
/*     */       } 
/*  52 */       ABRUtil.append(paramStringBuffer, "isExistfinal :" + bool1);
/*     */     } 
/*     */     
/*  55 */     EntityItem entityItem = paramDiffEntity.getPriorEntityItem();
/*     */     
/*  57 */     if (entityItem != null && str.equals(entityItem.getProfile().getValOn())) {
/*  58 */       bool2 = false;
/*     */     }
/*  60 */     Vector<DiffEntity> vector = getPlanAvails(paramHashtable, paramStringBuffer);
/*     */     
/*  62 */     if (vector.size() > 0) {
/*  63 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  64 */       for (byte b = 0; b < vector.size(); b++) {
/*  65 */         DiffEntity diffEntity = vector.elementAt(b);
/*  66 */         buildCtryAudRecs(treeMap, diffEntity, paramStringBuffer);
/*     */       } 
/*     */ 
/*     */       
/*  70 */       Collection collection = treeMap.values();
/*  71 */       Iterator<CtryAudRecord> iterator = collection.iterator();
/*     */       
/*  73 */       while (iterator.hasNext()) {
/*  74 */         CtryAudRecord ctryAudRecord = iterator.next();
/*     */ 
/*     */         
/*  77 */         if (!ctryAudRecord.isDeleted()) {
/*  78 */           ctryAudRecord.setAllFields(paramDiffEntity, paramHashtable, bool1, bool, paramStringBuffer);
/*     */         }
/*     */         
/*  81 */         if (ctryAudRecord.isDisplayable() || ctryAudRecord.isrfrDisplayable()) {
/*  82 */           createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity, ctryAudRecord, paramStringBuffer);
/*     */         } else {
/*     */           
/*  85 */           ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*     */         } 
/*  87 */         ctryAudRecord.dereference();
/*     */       } 
/*     */       
/*  90 */       treeMap.clear();
/*     */     } else {
/*  92 */       ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.addElements no planned AVAILs found" + NEWLINE);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/*  97 */     ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.buildCtryAudRecs " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 104 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*     */     
/* 106 */     if (paramDiffEntity.isDeleted()) {
/*     */       
/* 108 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 109 */       ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.buildCtryAudRecs for deleted / update avail at T1: ctryAtt " + 
/*     */           
/* 111 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 112 */       if (eANFlagAttribute != null) {
/* 113 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 114 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 116 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 117 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 118 */             String str2 = str1;
/* 119 */             if (paramTreeMap.containsKey(str2)) {
/*     */               
/* 121 */               CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/* 122 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted " + paramDiffEntity
/* 123 */                   .getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*     */             } else {
/*     */               
/* 126 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 127 */               ctryAudRecord.setAction("Delete");
/* 128 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 129 */               ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.buildCtryAudRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 130 */                   .getKey() + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 135 */     } else if (paramDiffEntity.isNew()) {
/*     */       
/* 137 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 138 */       ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.buildCtryAudRecs for new avail:  ctryAtt and anncodeAtt " + 
/* 139 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + 
/* 140 */           PokUtils.getAttributeFlagValue(entityItem1, "ANNCODENAME") + NEWLINE);
/* 141 */       if (eANFlagAttribute != null) {
/* 142 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 143 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 145 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 146 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 147 */             String str2 = str1;
/* 148 */             if (paramTreeMap.containsKey(str2)) {
/* 149 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/* 150 */               ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new " + paramDiffEntity.getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*     */               
/* 152 */               ctryAudRecord.setUpdateAvail(paramDiffEntity);
/*     */             } else {
/* 154 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 155 */               ctryAudRecord.setAction("Update");
/* 156 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 157 */               ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.buildCtryAudRecs for new:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 158 */                   .getKey() + NEWLINE);
/*     */             }
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 166 */       HashSet<String> hashSet1 = new HashSet();
/* 167 */       HashSet<String> hashSet2 = new HashSet();
/*     */       
/* 169 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 170 */       ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.buildCtryAudRecs for curr avail: fAtt " + 
/* 171 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/* 172 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 174 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 175 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 177 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 178 */             hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 184 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 185 */       ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.buildCtryAudRecs for prev avail:  fAtt " + 
/* 186 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 187 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 189 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 190 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 192 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 193 */             hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 199 */       Iterator<String> iterator = hashSet2.iterator();
/* 200 */       while (iterator.hasNext()) {
/* 201 */         String str1 = iterator.next();
/* 202 */         if (!hashSet1.contains(str1)) {
/*     */           
/* 204 */           String str = str1;
/* 205 */           if (paramTreeMap.containsKey(str)) {
/* 206 */             CtryAudRecord ctryAudRecord2 = paramTreeMap.get(str);
/* 207 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for added ctry on " + paramDiffEntity.getKey() + " " + str + " already exists, replacing orig " + ctryAudRecord2 + NEWLINE);
/*     */             
/* 209 */             ctryAudRecord2.setUpdateAvail(paramDiffEntity); continue;
/*     */           } 
/* 211 */           CtryAudRecord ctryAudRecord1 = new CtryAudRecord(paramDiffEntity, str1);
/* 212 */           ctryAudRecord1.setAction("Update");
/* 213 */           paramTreeMap.put(ctryAudRecord1.getKey(), ctryAudRecord1);
/* 214 */           ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.buildCtryAudRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord1
/* 215 */               .getKey() + NEWLINE);
/*     */           
/*     */           continue;
/*     */         } 
/* 219 */         String str2 = str1;
/* 220 */         if (paramTreeMap.containsKey(str2)) {
/* 221 */           CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str2);
/* 222 */           ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for existing ctry but new aud on " + paramDiffEntity.getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*     */           continue;
/*     */         } 
/* 225 */         CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 226 */         paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 227 */         ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.buildCtryAudRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 228 */             .getKey() + NEWLINE);
/*     */       } 
/*     */ 
/*     */       
/* 232 */       iterator = hashSet1.iterator();
/* 233 */       while (iterator.hasNext()) {
/* 234 */         String str = iterator.next();
/* 235 */         if (!hashSet2.contains(str)) {
/*     */           
/* 237 */           String str1 = str;
/* 238 */           if (paramTreeMap.containsKey(str1)) {
/* 239 */             CtryAudRecord ctryAudRecord1 = paramTreeMap.get(str1);
/* 240 */             ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for delete ctry on " + paramDiffEntity.getKey() + " " + str1 + " already exists, keeping orig " + ctryAudRecord1 + NEWLINE);
/*     */             continue;
/*     */           } 
/* 243 */           CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str);
/* 244 */           ctryAudRecord.setAction("Delete");
/* 245 */           paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 246 */           ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.buildCtryAudRecs for delete ctry:" + paramDiffEntity.getKey() + " rec: " + ctryAudRecord
/* 247 */               .getKey() + NEWLINE);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getPlanAvails(Hashtable paramHashtable, StringBuffer paramStringBuffer) {
/* 256 */     Vector<DiffEntity> vector1 = new Vector(1);
/* 257 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/* 258 */     ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/* 259 */         .size())) + NEWLINE);
/* 260 */     if (vector2 == null) {
/* 261 */       return vector1;
/*     */     }
/*     */     
/* 264 */     for (byte b = 0; b < vector2.size(); b++) {
/* 265 */       DiffEntity diffEntity = vector2.elementAt(b);
/* 266 */       if (diffEntity.isDeleted()) {
/* 267 */         EntityItem entityItem = diffEntity.getPriorEntityItem();
/*     */         
/* 269 */         ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/* 270 */             PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/* 271 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 272 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 273 */           vector1.add(diffEntity);
/*     */         }
/*     */       } else {
/* 276 */         EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 277 */         ABRUtil.append(paramStringBuffer, "XMLSWSTMFAVAILElembh1.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/* 278 */             PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/* 279 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 280 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 281 */           vector1.add(diffEntity);
/*     */         }
/*     */       } 
/*     */     } 
/* 285 */     return vector1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNodeSet(Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/* 291 */     if (paramCtryAudRecord.isDisplayable()) {
/*     */       
/* 293 */       Element element1 = paramDocument.createElement(this.nodeName);
/* 294 */       addXMLAttrs(element1);
/* 295 */       paramElement.appendChild(element1);
/*     */       
/* 297 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/* 298 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/* 299 */       element1.appendChild(element2);
/*     */       
/* 301 */       element2 = paramDocument.createElement("COUNTRY_FC");
/* 302 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/* 303 */       element1.appendChild(element2);
/* 304 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/* 305 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/* 306 */       element1.appendChild(element2);
/*     */     } 
/*     */     
/* 309 */     if (paramCtryAudRecord.isrfrDisplayable()) {
/* 310 */       Element element1 = paramDocument.createElement(this.nodeName);
/*     */       
/* 312 */       addXMLAttrs(element1);
/* 313 */       paramElement.appendChild(element1);
/*     */ 
/*     */       
/* 316 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/* 317 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfraction()));
/* 318 */       element1.appendChild(element2);
/*     */       
/* 320 */       element2 = paramDocument.createElement("COUNTRY_FC");
/* 321 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/* 322 */       element1.appendChild(element2);
/*     */       
/* 324 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/* 325 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrplannedavailability()));
/* 326 */       element1.appendChild(element2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class CtryAudRecord
/*     */     extends CtryRecord
/*     */   {
/*     */     private String country;
/*     */     private DiffEntity availDiff;
/*     */     
/*     */     CtryAudRecord(DiffEntity param1DiffEntity, String param1String) {
/* 337 */       super((String)null);
/* 338 */       this.country = param1String;
/* 339 */       this.availDiff = param1DiffEntity;
/*     */     }
/*     */ 
/*     */     
/*     */     void setUpdateAvail(DiffEntity param1DiffEntity) {
/* 344 */       this.availDiff = param1DiffEntity;
/* 345 */       setAction("Update");
/*     */     }
/*     */ 
/*     */     
/*     */     void setAllFields(DiffEntity param1DiffEntity, Hashtable param1Hashtable, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/* 350 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + NEWLINE);
/*     */ 
/*     */       
/* 353 */       this.availStatus = "0020";
/* 354 */       this.rfravailStatus = "0040";
/*     */ 
/*     */       
/* 357 */       String[] arrayOfString1 = derivePlannedavailability(param1DiffEntity, false, param1StringBuffer);
/* 358 */       String[] arrayOfString2 = derivePlannedavailability(param1DiffEntity, true, param1StringBuffer);
/*     */       
/* 360 */       handleResults(arrayOfString1, arrayOfString2, this.country, param1Boolean1, param1Boolean2, param1StringBuffer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean handleResults(String[] param1ArrayOfString1, String[] param1ArrayOfString2, String param1String, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/* 367 */       String str1 = "@@";
/* 368 */       String str2 = "@@";
/*     */       
/* 370 */       this.plannedavailability = param1ArrayOfString1[0];
/* 371 */       this.rfrplannedavailability = param1ArrayOfString1[1];
/* 372 */       str1 = param1ArrayOfString2[0];
/* 373 */       str2 = param1ArrayOfString2[1];
/*     */       
/* 375 */       if ("Delete".equals(this.action)) {
/* 376 */         ABRUtil.append(param1StringBuffer, "setallfileds: coutry is delete:" + param1String);
/* 377 */         str2 = copyfinaltoRFR(str1, str2, true, param1StringBuffer);
/*     */         
/* 379 */         if (this.existfinalT1) {
/* 380 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry is exist final T1:" + param1String + NEWLINE);
/* 381 */           setAction("Delete");
/* 382 */           setrfrAction("Delete");
/* 383 */           setAllfieldsEmpty();
/*     */         } else {
/*     */           
/* 386 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry is not exist final T1:" + param1String + NEWLINE);
/* 387 */           setAction("@@");
/* 388 */           setrfrAction("Delete");
/* 389 */           setAllfieldsEmpty();
/*     */         }
/*     */       
/*     */       }
/* 393 */       else if ("Update".equals(this.action)) {
/* 394 */         ABRUtil.append(param1StringBuffer, "setallfileds: coutry is new:" + param1String + NEWLINE);
/* 395 */         this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, param1StringBuffer);
/*     */         
/* 397 */         if (this.existfinalT2) {
/* 398 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry is  exist final T2:" + param1String + NEWLINE);
/* 399 */           setAction("Update");
/* 400 */           setrfrAction("Update");
/*     */         } else {
/*     */           
/* 403 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry is not exist final T2:" + param1String + NEWLINE);
/* 404 */           setAction("@@");
/* 405 */           setrfrAction("Update");
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 410 */         ABRUtil.append(param1StringBuffer, "setallfileds: coutry is both exist T1 and T2:" + param1String + NEWLINE);
/* 411 */         str2 = copyfinaltoRFR(str1, str2, true, param1StringBuffer);
/* 412 */         this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, param1StringBuffer);
/*     */         
/* 414 */         if (this.existfinalT1 && !this.existfinalT2) {
/* 415 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry  exist final T1 but T2:" + param1String + NEWLINE);
/* 416 */           setAction("Delete");
/* 417 */           setfinalAllfieldsEmpty();
/*     */         }
/* 419 */         else if (this.existfinalT2 && !this.existfinalT1) {
/* 420 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry  exist final T2 but T1:" + param1String + NEWLINE);
/* 421 */           setAction("Update");
/* 422 */           setrfrAction("Update");
/*     */         }
/* 424 */         else if (this.existfinalT2 && this.existfinalT1) {
/* 425 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry  exist final T1 and T2:" + param1String + NEWLINE);
/* 426 */           compareT1vT2(this.plannedavailability, str1, false);
/* 427 */           ABRUtil.append(param1StringBuffer, "setallfileds: after compare action :" + this.action + NEWLINE);
/*     */         } else {
/*     */           
/* 430 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry  not exist final T1 and T2:" + param1String + NEWLINE);
/* 431 */           setAction("@@");
/*     */         } 
/* 433 */         compareT1vT2(this.rfrplannedavailability, str2, true);
/* 434 */         ABRUtil.append(param1StringBuffer, "setallfileds: after compare rfr values action:" + this.rfraction + NEWLINE);
/*     */       } 
/*     */       
/* 437 */       if (!param1Boolean2) {
/* 438 */         if (param1Boolean1) {
/* 439 */           setrfrAction("@@");
/*     */         } else {
/* 441 */           setAction("@@");
/*     */         } 
/*     */       }
/*     */       
/* 445 */       return this.existfinalT2;
/*     */     }
/*     */     
/*     */     private String[] derivePlannedavailability(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 449 */       String str1 = "@@";
/* 450 */       String str2 = "@@";
/* 451 */       String[] arrayOfString1 = new String[2];
/*     */       
/* 453 */       String[] arrayOfString2 = new String[2];
/* 454 */       ABRUtil.append(param1StringBuffer, "XMLSWSTMFAVAILElembh1.derivePlannedavailability availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*     */ 
/*     */       
/* 457 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 458 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 459 */         str1 = arrayOfString2[0];
/* 460 */         str2 = arrayOfString2[1];
/*     */       } 
/*     */       
/* 463 */       arrayOfString1[0] = str1;
/* 464 */       arrayOfString1[1] = str2;
/* 465 */       return arrayOfString1;
/*     */     }
/*     */     
/*     */     String getCountry() {
/* 469 */       return this.country;
/*     */     }
/*     */     
/*     */     String getKey() {
/* 473 */       return this.country;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 478 */       return (this.availDiff == null) ? " availDiff is null" : this.availDiff.getKey();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLSWSTMFAVAILElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */