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
/*     */ public class XMLCtryImgElem
/*     */   extends XMLElem
/*     */ {
/*     */   public XMLCtryImgElem() {
/*  80 */     super("COUNTRYELEMENT");
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
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 105 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("IMG");
/* 106 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*     */     
/* 108 */     if (vector != null && vector.size() > 0) {
/* 109 */       for (byte b = 0; b < vector.size(); b++) {
/* 110 */         DiffEntity diffEntity = vector.elementAt(b);
/* 111 */         buildImgCtryRecs(treeMap, diffEntity, paramStringBuffer);
/*     */       } 
/*     */     } else {
/* 114 */       ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.addElements no IMGs found" + NEWLINE);
/*     */     } 
/*     */ 
/*     */     
/* 118 */     TreeMap treeMap1 = buildCtryRecs(treeMap, paramDiffEntity, paramStringBuffer);
/*     */ 
/*     */     
/* 121 */     Collection collection = treeMap1.values();
/* 122 */     Iterator<CtryRecord> iterator = collection.iterator();
/* 123 */     while (iterator.hasNext()) {
/* 124 */       CtryRecord ctryRecord = iterator.next();
/* 125 */       if (ctryRecord.isDisplayable()) {
/* 126 */         createNodeSet(paramDocument, paramElement, ctryRecord, paramStringBuffer);
/*     */       } else {
/* 128 */         ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.addElements no changes found for " + ctryRecord + NEWLINE);
/*     */       } 
/* 130 */       ctryRecord.dereference();
/*     */     } 
/*     */ 
/*     */     
/* 134 */     treeMap1.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNodeSet(Document paramDocument, Element paramElement, CtryRecord paramCtryRecord, StringBuffer paramStringBuffer) {
/* 141 */     Element element1 = paramDocument.createElement(this.nodeName);
/*     */     
/* 143 */     addXMLAttrs(element1);
/* 144 */     paramElement.appendChild(element1);
/*     */ 
/*     */     
/* 147 */     Element element2 = paramDocument.createElement("COUNTRYACTION");
/* 148 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getAction()));
/* 149 */     element1.appendChild(element2);
/* 150 */     element2 = paramDocument.createElement("COUNTRY_FC");
/* 151 */     element2.appendChild(paramDocument.createTextNode(paramCtryRecord.getCountry()));
/* 152 */     element1.appendChild(element2);
/* 153 */     Vector<ImgRecord> vector = paramCtryRecord.getImgRecVct();
/* 154 */     Element element3 = paramDocument.createElement("IMAGELIST");
/* 155 */     element1.appendChild(element3);
/* 156 */     if (vector != null)
/*     */     {
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
/* 169 */       for (byte b = 0; b < vector.size(); b++) {
/* 170 */         ImgRecord imgRecord = vector.elementAt(b);
/*     */         
/* 172 */         Element element = paramDocument.createElement("IMAGEELEMENT");
/* 173 */         element3.appendChild(element);
/*     */         
/* 175 */         element2 = paramDocument.createElement("IMAGEENTITYTYPE");
/* 176 */         element2.appendChild(paramDocument.createTextNode("IMG"));
/* 177 */         element.appendChild(element2);
/*     */         
/* 179 */         element2 = paramDocument.createElement("IMAGEENTITYID");
/* 180 */         element2.appendChild(paramDocument.createTextNode(imgRecord.getEntityID()));
/* 181 */         element.appendChild(element2);
/*     */         
/* 183 */         element2 = paramDocument.createElement("ACTIVITY");
/* 184 */         element2.appendChild(paramDocument.createTextNode(imgRecord.isDisplayable() ? imgRecord.getAction() : "Update"));
/* 185 */         element.appendChild(element2);
/*     */         
/* 187 */         if (!imgRecord.isDeleted()) {
/*     */           
/* 189 */           element2 = paramDocument.createElement("PUBFROM");
/* 190 */           element2.appendChild(paramDocument.createTextNode(imgRecord.getPubFrom()));
/* 191 */           element.appendChild(element2);
/*     */           
/* 193 */           element2 = paramDocument.createElement("PUBTO");
/* 194 */           element2.appendChild(paramDocument.createTextNode(imgRecord.getPubTo()));
/* 195 */           element.appendChild(element2);
/*     */           
/* 197 */           element2 = paramDocument.createElement("STATUS");
/* 198 */           element2.appendChild(paramDocument.createTextNode(imgRecord.getImgStatus()));
/* 199 */           element.appendChild(element2);
/*     */           
/* 201 */           element2 = paramDocument.createElement("IMAGEDESCRIPTION");
/* 202 */           element2.appendChild(paramDocument.createTextNode(imgRecord.getDesc()));
/* 203 */           element.appendChild(element2);
/*     */           
/* 205 */           element2 = paramDocument.createElement("MARKETINGIMAGEFILENAME");
/* 206 */           element2.appendChild(paramDocument.createTextNode(imgRecord.getFilename()));
/* 207 */           element.appendChild(element2);
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
/*     */   private void buildImgCtryRecs(TreeMap<String, Vector> paramTreeMap, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 219 */     ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildImgCtryRecs " + paramDiffEntity.getKey() + NEWLINE);
/*     */     
/* 221 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 222 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 223 */     if (paramDiffEntity.isDeleted()) {
/*     */       
/* 225 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 226 */       ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildImgCtryRecs for deleted " + paramDiffEntity.getKey() + " ctryAtt " + 
/* 227 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 228 */       if (eANFlagAttribute != null) {
/* 229 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 230 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 232 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 233 */             String str = arrayOfMetaFlag[b].getFlagCode();
/* 234 */             Vector<ImgRecord> vector = (Vector)paramTreeMap.get(str);
/* 235 */             if (vector == null) {
/* 236 */               vector = new Vector(1);
/* 237 */               paramTreeMap.put(str, vector);
/*     */             } 
/*     */             
/* 240 */             ImgRecord imgRecord = new ImgRecord(paramDiffEntity, str);
/* 241 */             imgRecord.setAction("Delete");
/* 242 */             vector.add(imgRecord);
/* 243 */             ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildImgCtryRecs for deleted:" + paramDiffEntity.getKey() + " rec: " + imgRecord
/* 244 */                 .getKey() + NEWLINE);
/*     */           } 
/*     */         } 
/*     */       } 
/* 248 */     } else if (paramDiffEntity.isNew()) {
/*     */       
/* 250 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 251 */       ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildImgCtryRecs for new " + paramDiffEntity.getKey() + " ctryAtt " + 
/* 252 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/* 253 */       if (eANFlagAttribute != null) {
/* 254 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 255 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 257 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 258 */             String str = arrayOfMetaFlag[b].getFlagCode();
/* 259 */             Vector<ImgRecord> vector = paramTreeMap.get(str);
/* 260 */             if (vector == null) {
/* 261 */               vector = new Vector(1);
/* 262 */               paramTreeMap.put(str, vector);
/*     */             } 
/* 264 */             ImgRecord imgRecord = new ImgRecord(paramDiffEntity, str);
/* 265 */             imgRecord.setAction("Update");
/* 266 */             imgRecord.setAllFields(paramStringBuffer);
/* 267 */             vector.add(imgRecord);
/* 268 */             ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildImgCtryRecs for new:" + paramDiffEntity.getKey() + " rec: " + imgRecord
/* 269 */                 .getKey() + NEWLINE);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 274 */       HashSet<String> hashSet1 = new HashSet();
/* 275 */       HashSet<String> hashSet2 = new HashSet();
/*     */       
/* 277 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 278 */       ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildImgCtryRecs for curr " + paramDiffEntity.getKey() + " fAtt " + 
/* 279 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/* 280 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 282 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 283 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 285 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 286 */             hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 292 */       eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 293 */       ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildImgCtryRecs for prev " + paramDiffEntity.getKey() + " fAtt " + 
/* 294 */           PokUtils.getAttributeFlagValue(entityItem2, "COUNTRYLIST") + NEWLINE);
/* 295 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 297 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 298 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 300 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 301 */             hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 307 */       Iterator<String> iterator = hashSet2.iterator();
/* 308 */       while (iterator.hasNext()) {
/* 309 */         String str = iterator.next();
/* 310 */         if (!hashSet1.contains(str)) {
/* 311 */           Vector<ImgRecord> vector1 = paramTreeMap.get(str);
/* 312 */           if (vector1 == null) {
/* 313 */             vector1 = new Vector(1);
/* 314 */             paramTreeMap.put(str, vector1);
/*     */           } 
/* 316 */           ImgRecord imgRecord1 = new ImgRecord(paramDiffEntity, str);
/* 317 */           imgRecord1.setAction("Update");
/* 318 */           imgRecord1.setAllFields(paramStringBuffer);
/* 319 */           vector1.add(imgRecord1);
/* 320 */           ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildImgCtryRecs for added ctry:" + paramDiffEntity.getKey() + " rec: " + imgRecord1
/* 321 */               .getKey() + NEWLINE);
/*     */           continue;
/*     */         } 
/* 324 */         Vector<ImgRecord> vector = paramTreeMap.get(str);
/* 325 */         if (vector == null) {
/* 326 */           vector = new Vector(1);
/* 327 */           paramTreeMap.put(str, vector);
/*     */         } 
/* 329 */         ImgRecord imgRecord = new ImgRecord(paramDiffEntity, str);
/* 330 */         imgRecord.setAllFields(paramStringBuffer);
/* 331 */         vector.add(imgRecord);
/* 332 */         ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildImgCtryRecs for existing ctry:" + paramDiffEntity.getKey() + " rec: " + imgRecord
/* 333 */             .getKey() + NEWLINE);
/*     */       } 
/*     */       
/* 336 */       iterator = hashSet1.iterator();
/* 337 */       while (iterator.hasNext()) {
/* 338 */         String str = iterator.next();
/* 339 */         if (!hashSet2.contains(str)) {
/* 340 */           Vector<ImgRecord> vector = paramTreeMap.get(str);
/* 341 */           if (vector == null) {
/* 342 */             vector = new Vector(1);
/* 343 */             paramTreeMap.put(str, vector);
/*     */           } 
/* 345 */           ImgRecord imgRecord = new ImgRecord(paramDiffEntity, str);
/* 346 */           imgRecord.setAction("Delete");
/* 347 */           vector.add(imgRecord);
/* 348 */           ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildImgCtryRecs for delete ctry:" + paramDiffEntity.getKey() + " rec: " + imgRecord
/* 349 */               .getKey() + NEWLINE);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private TreeMap buildCtryRecs(TreeMap paramTreeMap, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 357 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/* 358 */     HashSet<String> hashSet1 = new HashSet();
/* 359 */     HashSet<String> hashSet2 = new HashSet();
/* 360 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*     */ 
/*     */     
/* 363 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 364 */     ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildCtryRecs for current " + entityItem.getKey() + " fAtt " + 
/* 365 */         PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + NEWLINE);
/* 366 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */       
/* 368 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 369 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */         
/* 371 */         if (arrayOfMetaFlag[b].isSelected()) {
/* 372 */           hashSet2.add(arrayOfMetaFlag[b].getFlagCode());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 377 */     if (!paramDiffEntity.isDeleted()) {
/* 378 */       EntityItem entityItem1 = paramDiffEntity.getPriorEntityItem();
/* 379 */       eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 380 */       ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildCtryRecs for prev " + entityItem1.getKey() + " fAtt " + 
/* 381 */           PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST") + NEWLINE);
/* 382 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */         
/* 384 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 385 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 387 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 388 */             hashSet1.add(arrayOfMetaFlag[b].getFlagCode());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 395 */     Iterator<String> iterator = hashSet2.iterator();
/* 396 */     while (iterator.hasNext()) {
/* 397 */       String str = iterator.next();
/* 398 */       if (!hashSet1.contains(str)) {
/* 399 */         Vector vector1 = (Vector)paramTreeMap.get(str);
/* 400 */         CtryRecord ctryRecord1 = new CtryRecord(vector1, str);
/* 401 */         ctryRecord1.setAction("Update");
/* 402 */         treeMap.put(str, ctryRecord1);
/* 403 */         ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildCtryRecs for added rec: " + ctryRecord1.getKey() + NEWLINE);
/*     */         continue;
/*     */       } 
/* 406 */       Vector vector = (Vector)paramTreeMap.get(str);
/* 407 */       CtryRecord ctryRecord = new CtryRecord(vector, str);
/* 408 */       treeMap.put(str, ctryRecord);
/* 409 */       ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildCtryRecs for existing ctry rec: " + ctryRecord.getKey() + NEWLINE);
/*     */     } 
/*     */ 
/*     */     
/* 413 */     iterator = hashSet1.iterator();
/* 414 */     while (iterator.hasNext()) {
/* 415 */       String str = iterator.next();
/* 416 */       if (!hashSet2.contains(str)) {
/* 417 */         Vector vector = (Vector)paramTreeMap.get(str);
/* 418 */         CtryRecord ctryRecord = new CtryRecord(vector, str);
/* 419 */         ctryRecord.setAction("Delete");
/* 420 */         treeMap.put(str, ctryRecord);
/* 421 */         ABRUtil.append(paramStringBuffer, "XMLCtryImgElem.buildCtryRecs for delete ctry rec: " + ctryRecord
/* 422 */             .getKey() + NEWLINE);
/*     */       } 
/*     */     } 
/*     */     
/* 426 */     return treeMap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ImgRecord
/*     */   {
/* 433 */     private String action = null;
/*     */     private String country;
/* 435 */     private String imgStatus = "@@";
/* 436 */     private String pubfrom = "@@";
/* 437 */     private String pubto = "@@";
/* 438 */     private String desc = "@@";
/* 439 */     private String fname = "@@"; private DiffEntity imgDiff;
/*     */     
/*     */     boolean isDisplayable() {
/* 442 */       return (this.action != null);
/*     */     }
/*     */     ImgRecord(DiffEntity param1DiffEntity, String param1String) {
/* 445 */       this.country = param1String;
/* 446 */       this.imgDiff = param1DiffEntity;
/*     */     } void setAction(String param1String) {
/* 448 */       this.action = param1String;
/*     */     }
/*     */ 
/*     */     
/*     */     void setAllFields(StringBuffer param1StringBuffer) {
/* 453 */       ABRUtil.append(param1StringBuffer, "ImgRecord.setAllFields entered for: " + this.imgDiff.getKey() + " " + getKey() + XMLElem.NEWLINE);
/*     */       
/* 455 */       EntityItem entityItem1 = this.imgDiff.getCurrentEntityItem();
/* 456 */       EntityItem entityItem2 = this.imgDiff.getPriorEntityItem();
/*     */ 
/*     */       
/* 459 */       this.imgStatus = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/* 460 */       if (this.imgStatus == null) {
/* 461 */         this.imgStatus = "@@";
/*     */       }
/*     */       
/* 464 */       String str1 = "@@";
/* 465 */       if (entityItem2 != null) {
/* 466 */         str1 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 467 */         if (str1 == null) {
/* 468 */           str1 = "@@";
/*     */         }
/*     */       } 
/* 471 */       ABRUtil.append(param1StringBuffer, "ImgRecord.setAllFields curstatus: " + this.imgStatus + " prevstatus: " + str1 + XMLElem.NEWLINE);
/*     */ 
/*     */ 
/*     */       
/* 475 */       this.pubfrom = getValue(false, "PUBFROM", param1StringBuffer);
/* 476 */       String str2 = getValue(true, "PUBFROM", param1StringBuffer);
/* 477 */       ABRUtil.append(param1StringBuffer, "ImgRecord.setAllFields pubfromT2: " + this.pubfrom + " pubfromT1: " + str2 + XMLElem.NEWLINE);
/*     */       
/* 479 */       if (!this.pubfrom.equals(str2)) {
/* 480 */         setAction("Update");
/*     */       }
/*     */ 
/*     */       
/* 484 */       this.pubto = getValue(false, "PUBTO", param1StringBuffer);
/* 485 */       String str3 = getValue(true, "PUBTO", param1StringBuffer);
/* 486 */       if (!this.pubto.equals(str3)) {
/* 487 */         setAction("Update");
/*     */       }
/*     */       
/* 490 */       ABRUtil.append(param1StringBuffer, "ImgRecord.setAllFields action:" + this.action + " pubtoT2: " + this.pubto + " pubtoT1: " + str3 + XMLElem.NEWLINE);
/*     */ 
/*     */ 
/*     */       
/* 494 */       this.desc = getValue(false, "IMGDESC", param1StringBuffer);
/* 495 */       String str4 = getValue(true, "IMGDESC", param1StringBuffer);
/* 496 */       if (!this.desc.equals(str4)) {
/* 497 */         setAction("Update");
/*     */       }
/*     */       
/* 500 */       ABRUtil.append(param1StringBuffer, "ImgRecord.setAllFields action:" + this.action + " desc: " + this.desc + " descT1: " + str4 + XMLElem.NEWLINE);
/*     */ 
/*     */ 
/*     */       
/* 504 */       this.fname = getValue(false, "MKTGIMGFILENAM", param1StringBuffer);
/* 505 */       String str5 = getValue(true, "MKTGIMGFILENAM", param1StringBuffer);
/* 506 */       if (!this.fname.equals(str5)) {
/* 507 */         setAction("Update");
/*     */       }
/*     */       
/* 510 */       ABRUtil.append(param1StringBuffer, "ImgRecord.setAllFields action:" + this.action + " fname: " + this.fname + " fnameT1: " + str5 + XMLElem.NEWLINE);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String getValue(boolean param1Boolean, String param1String, StringBuffer param1StringBuffer) {
/* 519 */       String str = "@@";
/* 520 */       ABRUtil.append(param1StringBuffer, "ImgRecord.getValue imgDiff: " + this.imgDiff.getKey() + " findT1:" + param1Boolean + XMLElem.NEWLINE);
/*     */       
/* 522 */       if (param1Boolean) {
/* 523 */         if (!this.imgDiff.isNew()) {
/* 524 */           EntityItem entityItem = this.imgDiff.getPriorEntityItem();
/* 525 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 526 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 527 */             str = PokUtils.getAttributeValue(entityItem, param1String, ", ", "@@", false);
/*     */           }
/* 529 */           ABRUtil.append(param1StringBuffer, "ImgRecord.getValue value: " + str + " COUNTRYLIST: " + 
/*     */               
/* 531 */               PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */         }
/*     */       
/* 534 */       } else if (!this.imgDiff.isDeleted()) {
/* 535 */         EntityItem entityItem = this.imgDiff.getCurrentEntityItem();
/* 536 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 537 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(this.country)) {
/* 538 */           str = PokUtils.getAttributeValue(entityItem, param1String, ", ", "@@", false);
/*     */         }
/* 540 */         ABRUtil.append(param1StringBuffer, "ImgRecord.getValue value: " + str + " COUNTRYLIST: " + 
/*     */             
/* 542 */             PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST") + XMLElem.NEWLINE);
/*     */       } 
/*     */ 
/*     */       
/* 546 */       return str;
/*     */     }
/*     */     
/* 549 */     String getAction() { return this.action; }
/* 550 */     String getCountry() { return this.country; }
/* 551 */     String getPubFrom() { return this.pubfrom; }
/* 552 */     String getPubTo() { return this.pubto; }
/* 553 */     String getImgStatus() { return this.imgStatus; }
/* 554 */     String getDesc() { return this.desc; }
/* 555 */     String getFilename() { return this.fname; } String getEntityID() {
/* 556 */       return "" + this.imgDiff.getEntityID();
/*     */     }
/* 558 */     boolean isDeleted() { return "Delete".equals(this.action); } String getKey() {
/* 559 */       return this.country;
/*     */     } void dereference() {
/* 561 */       this.imgDiff = null;
/* 562 */       this.action = null;
/* 563 */       this.imgStatus = null;
/* 564 */       this.country = null;
/* 565 */       this.pubfrom = null;
/* 566 */       this.pubto = null;
/* 567 */       this.desc = null;
/* 568 */       this.fname = null;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 572 */       return this.imgDiff.getKey() + " " + getKey() + " action:" + this.action;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CtryRecord
/*     */   {
/* 581 */     private String action = null; private String country;
/*     */     private Vector imgRecVct;
/*     */     
/*     */     boolean isDisplayable() {
/* 585 */       return (this.action != null);
/*     */     }
/*     */     CtryRecord(Vector param1Vector, String param1String) {
/* 588 */       this.country = param1String;
/* 589 */       this.imgRecVct = param1Vector;
/* 590 */       if (this.imgRecVct != null)
/*     */       {
/* 592 */         for (byte b = 0; b < this.imgRecVct.size(); b++) {
/* 593 */           XMLCtryImgElem.ImgRecord imgRecord = this.imgRecVct.elementAt(b);
/* 594 */           if (imgRecord.isDisplayable()) {
/* 595 */             this.action = "Update";
/*     */             break;
/*     */           } 
/*     */         }  } 
/*     */     }
/*     */     void setAction(String param1String) {
/* 601 */       this.action = param1String;
/*     */     }
/* 603 */     String getAction() { return this.action; }
/* 604 */     String getCountry() { return this.country; } Vector getImgRecVct() {
/* 605 */       return this.imgRecVct;
/*     */     }
/* 607 */     boolean isDeleted() { return "Delete".equals(this.action); } String getKey() {
/* 608 */       return this.country;
/*     */     } void dereference() {
/* 610 */       if (this.imgRecVct != null) {
/* 611 */         for (byte b = 0; b < this.imgRecVct.size(); b++) {
/* 612 */           XMLCtryImgElem.ImgRecord imgRecord = this.imgRecVct.elementAt(b);
/* 613 */           imgRecord.dereference();
/*     */         } 
/*     */       }
/*     */       
/* 617 */       this.imgRecVct = null;
/* 618 */       this.action = null;
/* 619 */       this.country = null;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 623 */       return getKey() + " action:" + this.action;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLCtryImgElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */