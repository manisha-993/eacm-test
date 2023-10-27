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
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.TreeMap;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XMLMAINTMFAVAILElem extends XMLElem {
/*     */   public XMLMAINTMFAVAILElem() {
/*  24 */     super("AVAILABILITYELEMENT");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  33 */     ABRUtil.append(paramStringBuffer, "XMLMAINTMFAVAILElembh1:parentItem: " + paramDiffEntity.getKey() + NEWLINE);
/*     */     
/*  35 */     boolean bool = false;
/*  36 */     boolean bool1 = false;
/*  37 */     String str = "1980-01-01-00.00.00.000000";
/*  38 */     boolean bool2 = true;
/*  39 */     bool = AvailUtil.iscompatmodel();
/*  40 */     ABRUtil.append(paramStringBuffer, "compatModel compatbility mode:" + bool);
/*  41 */     if (!bool) {
/*     */       
/*  43 */       String str1 = null;
/*  44 */       str1 = (String)paramHashtable.get("_chSTATUS");
/*  45 */       ABRUtil.append(paramStringBuffer, "the status is" + str1 + NEWLINE);
/*  46 */       if ("0020".equals(str1)) {
/*  47 */         bool1 = true;
/*     */       } else {
/*  49 */         bool1 = false;
/*     */       } 
/*  51 */       ABRUtil.append(paramStringBuffer, "isExistfinal :" + bool1);
/*     */     } 
/*     */     
/*  54 */     EntityItem entityItem = paramDiffEntity.getPriorEntityItem();
/*     */     
/*  56 */     if (entityItem != null && str.equals(entityItem.getProfile().getValOn())) {
/*  57 */       bool2 = false;
/*     */     }
/*  59 */     Vector<DiffEntity> vector = getPlanAvails(paramHashtable, paramStringBuffer);
/*     */     
/*  61 */     if (vector.size() > 0) {
/*  62 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  63 */       for (byte b = 0; b < vector.size(); b++) {
/*  64 */         DiffEntity diffEntity = vector.elementAt(b);
/*     */         
/*  66 */         if (bool2) { buildCtryAudRecs(treeMap, diffEntity, true, paramStringBuffer); }
/*     */         else
/*  68 */         { buildCtryAudRecs(treeMap, diffEntity, false, paramStringBuffer); }
/*     */       
/*     */       } 
/*  71 */       Collection collection = treeMap.values();
/*  72 */       Iterator<CtryAudRecord> iterator = collection.iterator();
/*     */       
/*  74 */       while (iterator.hasNext()) {
/*  75 */         CtryAudRecord ctryAudRecord = iterator.next();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  80 */         ctryAudRecord.setAllFields(paramDiffEntity, paramHashtable, bool1, bool, paramStringBuffer);
/*     */         
/*  82 */         if (ctryAudRecord.isDisplayable() || ctryAudRecord.isrfrDisplayable()) {
/*  83 */           createNodeSet(paramHashtable, paramDocument, paramElement, paramDiffEntity, ctryAudRecord, paramStringBuffer);
/*     */         } else {
/*     */           
/*  86 */           ABRUtil.append(paramStringBuffer, "XMLSVCMODAVAILElembh1.addElements no changes found for " + ctryAudRecord + NEWLINE);
/*     */         } 
/*  88 */         ctryAudRecord.dereference();
/*     */       } 
/*     */     } else {
/*  91 */       ABRUtil.append(paramStringBuffer, "XMLMAINTMFAVAILElembh1.addElements no planned AVAILs found" + NEWLINE);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void buildCtryAudRecs(TreeMap<String, CtryAudRecord> paramTreeMap, DiffEntity paramDiffEntity, boolean paramBoolean, StringBuffer paramStringBuffer) {
/*  96 */     ABRUtil.append(paramStringBuffer, "XMLMAINTMFAVAILElembh1.buildCtryAudRecs build T1 country list " + paramBoolean + " " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 103 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 104 */     if (paramBoolean) {
/* 105 */       if (!paramDiffEntity.isNew()) {
/*     */         
/* 107 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 108 */         ABRUtil.append(paramStringBuffer, "XMLMAINTMFAVAILElembh1.buildCtryAudRecs for deleted / update avail at T1: ctryAtt " + 
/*     */             
/* 110 */             PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 111 */         if (eANFlagAttribute != null) {
/* 112 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 113 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */             
/* 115 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 116 */               String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 117 */               String str2 = str1;
/* 118 */               if (paramTreeMap.containsKey(str2)) {
/*     */                 
/* 120 */                 CtryAudRecord ctryAudRecord = (CtryAudRecord)paramTreeMap.get(str2);
/* 121 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for deleted / update " + paramDiffEntity
/* 122 */                     .getKey() + " " + str2 + " already exists, keeping orig " + ctryAudRecord + NEWLINE);
/*     */               } else {
/*     */                 
/* 125 */                 CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 126 */                 ctryAudRecord.setAction("Delete");
/* 127 */                 paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/*     */               }
/*     */             
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 134 */     } else if (!paramDiffEntity.isDeleted()) {
/*     */       
/* 136 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 137 */       ABRUtil.append(paramStringBuffer, "XMLMAINTMFAVAILElembh1.buildCtryAudRecs for new /update avail:  ctryAtt " + 
/*     */           
/* 139 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/* 140 */       if (eANFlagAttribute != null) {
/* 141 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 142 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 144 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 145 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 146 */             String str2 = str1;
/* 147 */             if (paramTreeMap.containsKey(str2)) {
/* 148 */               CtryAudRecord ctryAudRecord = paramTreeMap.get(str2);
/* 149 */               if ("Delete".equals(ctryAudRecord.action)) {
/* 150 */                 ABRUtil.append(paramStringBuffer, "WARNING buildCtryAudRecs for new /udpate" + paramDiffEntity
/* 151 */                     .getKey() + " " + str2 + " already exists, replacing orig " + ctryAudRecord + NEWLINE);
/*     */                 
/* 153 */                 ctryAudRecord.setUpdateAvail(paramDiffEntity);
/* 154 */                 ctryAudRecord.setAction("@@");
/*     */               } 
/*     */             } else {
/* 157 */               CtryAudRecord ctryAudRecord = new CtryAudRecord(paramDiffEntity, str1);
/* 158 */               ctryAudRecord.setAction("Update");
/* 159 */               paramTreeMap.put(ctryAudRecord.getKey(), ctryAudRecord);
/* 160 */               ABRUtil.append(paramStringBuffer, "XMLMAINTMFAVAILElembh1.buildCtryAudRecs for new:" + paramDiffEntity
/* 161 */                   .getKey() + " rec: " + ctryAudRecord.getKey() + NEWLINE);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Vector getPlanAvails(Hashtable paramHashtable, StringBuffer paramStringBuffer) {
/* 171 */     Vector<DiffEntity> vector1 = new Vector(1);
/* 172 */     Vector<DiffEntity> vector2 = (Vector)paramHashtable.get("AVAIL");
/* 173 */     ABRUtil.append(paramStringBuffer, "XMLMAINTMFAVAILElembh1.getPlannedAvails looking for AVAILTYPE:146 in AVAIL allVct.size:" + ((vector2 == null) ? "null" : ("" + vector2
/* 174 */         .size())) + NEWLINE);
/* 175 */     if (vector2 == null) {
/* 176 */       return vector1;
/*     */     }
/*     */     
/* 179 */     for (byte b = 0; b < vector2.size(); b++) {
/* 180 */       DiffEntity diffEntity = vector2.elementAt(b);
/* 181 */       if (diffEntity.isDeleted()) {
/* 182 */         EntityItem entityItem = diffEntity.getPriorEntityItem();
/*     */         
/* 184 */         ABRUtil.append(paramStringBuffer, "XMLMAINTMFAVAILElembh1.getPlannedAvails checking[" + b + "]: deleted " + diffEntity.getKey() + " AVAILTYPE: " + 
/* 185 */             PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/* 186 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 187 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 188 */           vector1.add(diffEntity);
/*     */         }
/*     */       } else {
/* 191 */         EntityItem entityItem = diffEntity.getCurrentEntityItem();
/* 192 */         ABRUtil.append(paramStringBuffer, "XMLMAINTMFAVAILElembh1.getPlannedAvails checking[" + b + "]:" + diffEntity.getKey() + " AVAILTYPE: " + 
/* 193 */             PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE") + NEWLINE);
/* 194 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("AVAILTYPE");
/* 195 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 196 */           vector1.add(diffEntity);
/*     */         }
/*     */       } 
/*     */     } 
/* 200 */     return vector1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNodeSet(Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, CtryAudRecord paramCtryAudRecord, StringBuffer paramStringBuffer) {
/* 206 */     if (paramCtryAudRecord.isDisplayable()) {
/*     */       
/* 208 */       Element element1 = paramDocument.createElement(this.nodeName);
/* 209 */       addXMLAttrs(element1);
/* 210 */       paramElement.appendChild(element1);
/*     */       
/* 212 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/* 213 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getAction()));
/* 214 */       element1.appendChild(element2);
/*     */       
/* 216 */       element2 = paramDocument.createElement("COUNTRY_FC");
/* 217 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/* 218 */       element1.appendChild(element2);
/* 219 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/* 220 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getPlannedavailability()));
/* 221 */       element1.appendChild(element2);
/*     */     } 
/*     */     
/* 224 */     if (paramCtryAudRecord.isrfrDisplayable()) {
/* 225 */       Element element1 = paramDocument.createElement(this.nodeName);
/*     */       
/* 227 */       addXMLAttrs(element1);
/* 228 */       paramElement.appendChild(element1);
/*     */ 
/*     */       
/* 231 */       Element element2 = paramDocument.createElement("AVAILABILITYACTION");
/* 232 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfraction()));
/* 233 */       element1.appendChild(element2);
/*     */       
/* 235 */       element2 = paramDocument.createElement("COUNTRY_FC");
/* 236 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getCountry()));
/* 237 */       element1.appendChild(element2);
/*     */       
/* 239 */       element2 = paramDocument.createElement("PLANNEDAVAILABILITY");
/* 240 */       element2.appendChild(paramDocument.createTextNode("" + paramCtryAudRecord.getRfrplannedavailability()));
/* 241 */       element1.appendChild(element2);
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
/* 252 */       super((String)null);
/* 253 */       this.country = param1String;
/* 254 */       this.availDiff = param1DiffEntity;
/*     */     }
/*     */ 
/*     */     
/*     */     void setUpdateAvail(DiffEntity param1DiffEntity) {
/* 259 */       this.availDiff = param1DiffEntity;
/* 260 */       setAction("Update");
/*     */     }
/*     */ 
/*     */     
/*     */     void setAllFields(DiffEntity param1DiffEntity, Hashtable param1Hashtable, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/* 265 */       ABRUtil.append(param1StringBuffer, "CtryRecord.setAllFields entered for: " + this.availDiff.getKey() + " " + getKey() + NEWLINE);
/*     */ 
/*     */       
/* 268 */       this.availStatus = "0020";
/* 269 */       this.rfravailStatus = "0040";
/*     */ 
/*     */       
/* 272 */       String[] arrayOfString1 = derivePlannedavailability(param1DiffEntity, false, param1StringBuffer);
/* 273 */       String[] arrayOfString2 = derivePlannedavailability(param1DiffEntity, true, param1StringBuffer);
/*     */       
/* 275 */       handleResults(arrayOfString1, arrayOfString2, this.country, param1Boolean1, param1Boolean2, param1StringBuffer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean handleResults(String[] param1ArrayOfString1, String[] param1ArrayOfString2, String param1String, boolean param1Boolean1, boolean param1Boolean2, StringBuffer param1StringBuffer) {
/* 282 */       String str1 = "@@";
/* 283 */       String str2 = "@@";
/*     */       
/* 285 */       this.plannedavailability = param1ArrayOfString1[0];
/* 286 */       this.rfrplannedavailability = param1ArrayOfString1[1];
/* 287 */       str1 = param1ArrayOfString2[0];
/* 288 */       str2 = param1ArrayOfString2[1];
/*     */       
/* 290 */       if ("Delete".equals(this.action)) {
/* 291 */         ABRUtil.append(param1StringBuffer, "setallfileds: coutry is delete:" + param1String);
/* 292 */         str2 = copyfinaltoRFR(str1, str2, true, param1StringBuffer);
/*     */         
/* 294 */         if (this.existfinalT1) {
/* 295 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry is exist final T1:" + param1String + NEWLINE);
/* 296 */           setAction("Delete");
/* 297 */           setrfrAction("Delete");
/* 298 */           setAllfieldsEmpty();
/*     */         } else {
/*     */           
/* 301 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry is not exist final T1:" + param1String + NEWLINE);
/* 302 */           setAction("@@");
/* 303 */           setrfrAction("Delete");
/* 304 */           setAllfieldsEmpty();
/*     */         }
/*     */       
/*     */       }
/* 308 */       else if ("Update".equals(this.action)) {
/* 309 */         ABRUtil.append(param1StringBuffer, "setallfileds: coutry is new:" + param1String + NEWLINE);
/* 310 */         this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, param1StringBuffer);
/*     */         
/* 312 */         if (this.existfinalT2) {
/* 313 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry is  exist final T2:" + param1String + NEWLINE);
/* 314 */           setAction("Update");
/* 315 */           setrfrAction("Update");
/*     */         } else {
/*     */           
/* 318 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry is not exist final T2:" + param1String + NEWLINE);
/* 319 */           setAction("@@");
/* 320 */           setrfrAction("Update");
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 325 */         ABRUtil.append(param1StringBuffer, "setallfileds: coutry is both exist T1 and T2:" + param1String + NEWLINE);
/* 326 */         str2 = copyfinaltoRFR(str1, str2, true, param1StringBuffer);
/* 327 */         this.rfrplannedavailability = copyfinaltoRFR(this.plannedavailability, this.rfrplannedavailability, false, param1StringBuffer);
/*     */         
/* 329 */         if (this.existfinalT1 && !this.existfinalT2) {
/* 330 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry  exist final T1 but T2:" + param1String + NEWLINE);
/* 331 */           setAction("Delete");
/* 332 */           setfinalAllfieldsEmpty();
/*     */         }
/* 334 */         else if (this.existfinalT2 && !this.existfinalT1) {
/* 335 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry  exist final T2 but T1:" + param1String + NEWLINE);
/* 336 */           setAction("Update");
/* 337 */           setrfrAction("Update");
/*     */         }
/* 339 */         else if (this.existfinalT2 && this.existfinalT1) {
/* 340 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry  exist final T1 and T2:" + param1String + NEWLINE);
/* 341 */           compareT1vT2(this.plannedavailability, str1, false);
/* 342 */           ABRUtil.append(param1StringBuffer, "setallfileds: after compare action :" + this.action + NEWLINE);
/*     */         } else {
/*     */           
/* 345 */           ABRUtil.append(param1StringBuffer, "setallfileds: coutry  not exist final T1 and T2:" + param1String + NEWLINE);
/* 346 */           setAction("@@");
/*     */         } 
/* 348 */         compareT1vT2(this.rfrplannedavailability, str2, true);
/* 349 */         ABRUtil.append(param1StringBuffer, "setallfileds: after compare rfr values action:" + this.rfraction + NEWLINE);
/*     */       } 
/*     */       
/* 352 */       if (!param1Boolean2) {
/* 353 */         if (param1Boolean1) {
/* 354 */           setrfrAction("@@");
/*     */         } else {
/* 356 */           setAction("@@");
/*     */         } 
/*     */       }
/*     */       
/* 360 */       return this.existfinalT2;
/*     */     }
/*     */     
/*     */     private String[] derivePlannedavailability(DiffEntity param1DiffEntity, boolean param1Boolean, StringBuffer param1StringBuffer) {
/* 364 */       String str1 = "@@";
/* 365 */       String str2 = "@@";
/* 366 */       String[] arrayOfString1 = new String[2];
/*     */       
/* 368 */       String[] arrayOfString2 = new String[2];
/* 369 */       ABRUtil.append(param1StringBuffer, "XMLMAINTMFAVAILElembh1.derivePlannedavailability availDiff: " + ((this.availDiff == null) ? "null" : this.availDiff.getKey()) + "findT1:" + param1Boolean + NEWLINE);
/*     */ 
/*     */       
/* 372 */       if ("@@".equals(str1) || "@@".equals(str2)) {
/* 373 */         arrayOfString2 = AvailUtil.getAvailAttributeDate(param1Boolean, this.availDiff, str1, str2, this.country, "EFFECTIVEDATE", param1StringBuffer);
/* 374 */         str1 = arrayOfString2[0];
/* 375 */         str2 = arrayOfString2[1];
/*     */       } 
/*     */       
/* 378 */       arrayOfString1[0] = str1;
/* 379 */       arrayOfString1[1] = str2;
/* 380 */       return arrayOfString1;
/*     */     }
/*     */     
/*     */     String getCountry() {
/* 384 */       return this.country;
/*     */     }
/*     */     
/*     */     String getKey() {
/* 388 */       return this.country;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 393 */       return (this.availDiff == null) ? " availDiff is null" : this.availDiff.getKey();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLMAINTMFAVAILElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */