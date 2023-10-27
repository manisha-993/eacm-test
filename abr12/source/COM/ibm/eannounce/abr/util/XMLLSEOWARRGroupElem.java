/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
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
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class XMLLSEOWARRGroupElem
/*     */   extends XMLElem
/*     */ {
/*  40 */   private String path = null;
/*  41 */   private String etype = null;
/*  42 */   private String special = null;
/*  43 */   private String prekey = "WARRRELATOR";
/*  44 */   private String countryKey = "AVAILCOUNTRYLIST";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLLSEOWARRGroupElem(String paramString1, String paramString2, String paramString3) {
/*  59 */     super(paramString1);
/*  60 */     this.etype = paramString2;
/*  61 */     this.path = paramString3;
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLLSEOWARRGroupElem(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  66 */     super(paramString1);
/*  67 */     this.etype = paramString2;
/*  68 */     this.path = paramString3;
/*  69 */     this.special = paramString4;
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
/*     */   public XMLLSEOWARRGroupElem(String paramString1, String paramString2) {
/*  82 */     super(paramString1);
/*  83 */     this.etype = paramString2;
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
/*     */   public XMLLSEOWARRGroupElem(String paramString) {
/*  95 */     super(paramString);
/*  96 */     this.etype = "ROOT";
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
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 123 */     EntityItem entityItem1 = null;
/* 124 */     EntityItem entityItem2 = null;
/* 125 */     Vector<EntityItem> vector = new Vector();
/* 126 */     Vector vector1 = null;
/* 127 */     if (paramDiffEntity.isDeleted()) {
/* 128 */       entityItem1 = paramDiffEntity.getPriorEntityItem();
/* 129 */       vector.add(entityItem1);
/* 130 */     } else if (paramDiffEntity.isNew()) {
/* 131 */       entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 132 */       vector.add(entityItem1);
/*     */     } else {
/* 134 */       entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 135 */       vector.add(entityItem1);
/* 136 */       entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 137 */       vector.add(entityItem2);
/*     */     } 
/*     */     
/* 140 */     if ("MODELWARR|PRODSTRUCTWARR".equals(this.etype)) {
/* 141 */       vector1 = getWARRRelatorVect(paramHashtable, entityItem1, paramStringBuffer);
/* 142 */     } else if ("WARR".equals(this.etype)) {
/* 143 */       vector1 = getWWARvct(paramHashtable, vector, paramStringBuffer);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 148 */     if (vector1 == null) {
/*     */       
/* 150 */       if ("SPECIAL".equals(this.special)) {
/* 151 */         ABRUtil.append(paramStringBuffer, "@@@@XMLLSEOWARRGroupElem: node is null:" + this.nodeName + " path:" + this.path);
/*     */         
/* 153 */         Element element = paramDocument.createElement("PUBFROM");
/* 154 */         element.appendChild(paramDocument.createTextNode(""));
/* 155 */         paramElement.appendChild(element);
/*     */         
/* 157 */         element = paramDocument.createElement("PUBTO");
/* 158 */         element.appendChild(paramDocument.createTextNode(""));
/* 159 */         paramElement.appendChild(element);
/*     */         
/* 161 */         element = paramDocument.createElement("DEFWARR");
/* 162 */         element.appendChild(paramDocument.createTextNode("ERRORNULL"));
/* 163 */         paramElement.appendChild(element);
/*     */       }
/*     */       else {
/*     */         
/* 167 */         if (this.nodeName == null) this.nodeName = "ERROR"; 
/* 168 */         Element element = paramDocument.createElement(this.nodeName);
/* 169 */         addXMLAttrs(element);
/*     */         
/* 171 */         if (paramElement == null) {
/* 172 */           paramDocument.appendChild(element);
/*     */         } else {
/* 174 */           paramElement.appendChild(element);
/*     */         } 
/*     */         
/* 177 */         element.appendChild(paramDocument.createTextNode("Error: " + this.etype + " not found in extract!"));
/*     */         
/* 179 */         if (this.isReq) {
/* 180 */           throw new IOException(this.nodeName + " is required but " + this.etype + " is not in extract");
/*     */         }
/*     */         
/* 183 */         for (byte b = 0; b < this.childVct.size(); b++) {
/* 184 */           XMLElem xMLElem = this.childVct.elementAt(b);
/* 185 */           xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 191 */       Vector<DiffEntity> vector2 = getEntities(vector1);
/*     */       
/* 193 */       if (this.nodeName != null) {
/* 194 */         String str = null;
/* 195 */         if (paramElement == null) {
/* 196 */           str = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + this.nodeName;
/*     */         }
/*     */         
/* 199 */         Element element = paramDocument.createElementNS(str, this.nodeName);
/* 200 */         addXMLAttrs(element);
/*     */         
/* 202 */         if (paramElement == null) {
/* 203 */           ABRUtil.append(paramStringBuffer, "create root1: " + str + " " + this.nodeName);
/* 204 */           paramDocument.appendChild(element);
/* 205 */           element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str);
/*     */         } else {
/* 207 */           paramElement.appendChild(element);
/*     */         } 
/*     */ 
/*     */         
/* 211 */         if (vector2.size() == 0) {
/*     */ 
/*     */           
/* 214 */           element.appendChild(paramDocument.createTextNode("@@"));
/* 215 */           ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem: node:" + this.nodeName + " path:" + this.path + " No entities found for " + this.etype + NEWLINE);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 221 */         for (byte b = 0; b < vector2.size(); b++) {
/*     */           
/* 223 */           DiffEntity diffEntity = vector2.elementAt(b);
/*     */ 
/*     */           
/* 226 */           if (!diffEntity.isRoot() && !hasChanges(paramHashtable, diffEntity, paramStringBuffer)) {
/* 227 */             ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem: node:" + this.nodeName + " path:" + this.path + " No Changes found in " + diffEntity
/* 228 */                 .getKey() + NEWLINE);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 233 */             for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 234 */               XMLElem xMLElem = this.childVct.elementAt(b1);
/* 235 */               xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, diffEntity, paramStringBuffer);
/*     */             } 
/*     */           } 
/*     */         } 
/* 239 */         vector2.clear();
/*     */         
/* 241 */         if (!element.hasChildNodes())
/*     */         {
/* 243 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 251 */         if (vector2.size() == 0) {
/* 252 */           ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem: node:" + this.nodeName + " path:" + this.path + " No entities found for " + this.etype + NEWLINE);
/*     */ 
/*     */           
/* 255 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 256 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 257 */             xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, paramElement, (DiffEntity)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 263 */         for (byte b = 0; b < vector2.size(); b++) {
/*     */           
/* 265 */           DiffEntity diffEntity = vector2.elementAt(b);
/*     */ 
/*     */           
/* 268 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 269 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 270 */             xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, paramElement, diffEntity, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 273 */         vector2.clear();
/*     */       } 
/* 275 */       vector1.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 304 */     EntityGroup entityGroup = null;
/* 305 */     if ("ROOT".equals(this.etype)) {
/* 306 */       entityGroup = paramEntityList.getParentEntityGroup();
/*     */     } else {
/* 308 */       entityGroup = paramEntityList.getEntityGroup(this.etype);
/*     */     } 
/*     */     
/* 311 */     if (entityGroup == null) {
/* 312 */       Element element = paramDocument.createElement(this.nodeName);
/* 313 */       addXMLAttrs(element);
/* 314 */       if (paramElement == null) {
/* 315 */         paramDocument.appendChild(element);
/*     */       } else {
/* 317 */         paramElement.appendChild(element);
/*     */       } 
/*     */       
/* 320 */       element.appendChild(paramDocument.createTextNode("Error: " + this.etype + " not found in extract!"));
/*     */       
/* 322 */       if (this.isReq) {
/* 323 */         throw new IOException(this.nodeName + " is required but " + this.etype + " is not in extract");
/*     */       }
/*     */       
/* 326 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 327 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 328 */         xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
/*     */       } 
/*     */     } else {
/*     */       
/* 332 */       Vector<EntityItem> vector = getEntities(entityGroup);
/*     */ 
/*     */ 
/*     */       
/* 336 */       if (this.path != null && paramEntityItem != null) {
/* 337 */         EntityItem entityItem = paramEntityItem;
/* 338 */         Vector<EntityItem> vector1 = new Vector(1);
/* 339 */         Vector<EntityItem> vector2 = new Vector(1);
/* 340 */         vector2.add(entityItem);
/* 341 */         StringTokenizer stringTokenizer = new StringTokenizer(this.path, ":");
/* 342 */         while (stringTokenizer.hasMoreTokens()) {
/* 343 */           String str1 = stringTokenizer.nextToken();
/* 344 */           String str2 = this.etype;
/* 345 */           if (stringTokenizer.hasMoreTokens()) {
/* 346 */             str2 = stringTokenizer.nextToken();
/*     */           }
/* 348 */           ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem: node:" + this.nodeName + " path:" + this.path + " dir:" + str1 + " destination " + str2 + NEWLINE);
/*     */ 
/*     */           
/* 351 */           Vector<EntityItem> vector3 = new Vector();
/* 352 */           for (byte b = 0; b < vector2.size(); b++) {
/* 353 */             EntityItem entityItem1 = vector2.elementAt(b);
/* 354 */             ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem: loop pitem " + entityItem1.getKey() + NEWLINE);
/* 355 */             Vector<EntityItem> vector4 = null;
/* 356 */             if (str1.equals("D")) {
/* 357 */               vector4 = entityItem1.getDownLink();
/*     */             } else {
/* 359 */               vector4 = entityItem1.getUpLink();
/*     */             } 
/* 361 */             for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 362 */               EntityItem entityItem2 = vector4.elementAt(b1);
/* 363 */               ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem: linkloop entity " + entityItem2.getKey() + NEWLINE);
/* 364 */               if (entityItem2.getEntityType().equals(str2)) {
/* 365 */                 if (stringTokenizer.hasMoreTokens()) {
/*     */                   
/* 367 */                   vector3.add(entityItem2);
/*     */                 } else {
/* 369 */                   vector1.add(entityItem2);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/* 374 */           vector2 = vector3;
/*     */         } 
/* 376 */         vector = vector1;
/*     */       } 
/*     */       
/* 379 */       if (this.nodeName != null) {
/*     */         
/* 381 */         String str = null;
/* 382 */         if (paramElement == null) {
/* 383 */           str = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + this.nodeName;
/*     */         }
/*     */         
/* 386 */         Element element = paramDocument.createElementNS(str, this.nodeName);
/* 387 */         addXMLAttrs(element);
/*     */         
/* 389 */         if (paramElement == null) {
/* 390 */           ABRUtil.append(paramStringBuffer, "create root2: " + str + " " + this.nodeName);
/* 391 */           paramDocument.appendChild(element);
/* 392 */           element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str);
/*     */         } else {
/* 394 */           paramElement.appendChild(element);
/*     */         } 
/*     */ 
/*     */         
/* 398 */         if (vector.size() == 0) {
/*     */ 
/*     */           
/* 401 */           element.appendChild(paramDocument.createTextNode("@@"));
/* 402 */           ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem: node:" + this.nodeName + " No entities found for " + this.etype + NEWLINE);
/*     */           
/* 404 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 405 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 406 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, (EntityItem)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 412 */         for (byte b = 0; b < vector.size(); b++) {
/* 413 */           EntityItem entityItem = vector.elementAt(b);
/*     */ 
/*     */           
/* 416 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 417 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 418 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, entityItem, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 421 */         vector.clear();
/*     */         
/* 423 */         if (!element.hasChildNodes())
/*     */         {
/* 425 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 434 */         if (vector.size() == 0) {
/* 435 */           ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem: node:" + this.nodeName + " No entities found for " + this.etype + NEWLINE);
/*     */           
/* 437 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 438 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 439 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, (EntityItem)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/* 444 */         for (byte b = 0; b < vector.size(); b++) {
/* 445 */           EntityItem entityItem = vector.elementAt(b);
/*     */ 
/*     */           
/* 448 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 449 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 450 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, entityItem, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 453 */         vector.clear();
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
/*     */   protected boolean hasChanges(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 466 */     boolean bool1 = false;
/* 467 */     ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem.hasChanges entered for node:" + this.nodeName + " " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */ 
/*     */     
/* 471 */     String[] arrayOfString = PokUtils.convertToArray(this.etype);
/* 472 */     boolean bool2 = false; byte b;
/* 473 */     for (b = 0; b < arrayOfString.length; b++) {
/* 474 */       if (arrayOfString[b].equals(paramDiffEntity.getEntityType())) bool2 = true;
/*     */     
/*     */     } 
/* 477 */     if (paramDiffEntity != null && bool2) {
/* 478 */       for (b = 0; b < this.childVct.size() && !bool1; b++) {
/* 479 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 480 */         if (xMLElem.hasChanges(paramHashtable, paramDiffEntity, paramStringBuffer)) {
/* 481 */           bool1 = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 486 */       Vector<EntityItem> vector = new Vector();
/* 487 */       EntityItem entityItem1 = null;
/* 488 */       EntityItem entityItem2 = null;
/* 489 */       Vector<DiffEntity> vector1 = null;
/* 490 */       if (paramDiffEntity.isDeleted()) {
/* 491 */         entityItem1 = paramDiffEntity.getPriorEntityItem();
/* 492 */         vector.add(entityItem1);
/* 493 */       } else if (paramDiffEntity.isNew()) {
/* 494 */         entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 495 */         vector.add(entityItem1);
/*     */       } else {
/* 497 */         entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 498 */         vector.add(entityItem1);
/* 499 */         entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 500 */         vector.add(entityItem2);
/*     */       } 
/*     */       
/* 503 */       if ("MODELWARR|PRODSTRUCTWARR".equals(this.etype)) {
/* 504 */         vector1 = getWARRRelatorVect(paramHashtable, entityItem1, paramStringBuffer);
/* 505 */       } else if ("WARR".equals(this.etype)) {
/* 506 */         vector1 = getWWARvct(paramHashtable, vector, paramStringBuffer);
/*     */       } 
/* 508 */       if (vector1 != null) {
/*     */         byte b1;
/* 510 */         label52: for (b1 = 0; b1 < vector1.size(); b1++) {
/* 511 */           DiffEntity diffEntity = vector1.elementAt(b1);
/* 512 */           for (byte b2 = 0; b2 < this.childVct.size() && !bool1; b2++) {
/* 513 */             XMLElem xMLElem = this.childVct.elementAt(b2);
/* 514 */             if (xMLElem.hasChanges(paramHashtable, diffEntity, paramStringBuffer)) {
/* 515 */               bool1 = true;
/*     */               break label52;
/*     */             } 
/*     */           } 
/*     */         } 
/* 520 */         vector1.clear();
/*     */       } 
/*     */     } 
/* 523 */     return bool1;
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
/*     */   private Vector getItems(EntityItem paramEntityItem, String paramString1, String paramString2, StringBuffer paramStringBuffer) {
/* 549 */     Vector<EntityItem> vector1 = new Vector(1);
/* 550 */     Vector<EntityItem> vector2 = new Vector(1);
/* 551 */     vector1.add(paramEntityItem);
/* 552 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1, ":");
/* 553 */     while (stringTokenizer.hasMoreTokens()) {
/* 554 */       String str1 = stringTokenizer.nextToken();
/* 555 */       String str2 = paramString2;
/* 556 */       if (stringTokenizer.hasMoreTokens()) {
/* 557 */         str2 = stringTokenizer.nextToken();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 562 */       Vector<EntityItem> vector = new Vector();
/* 563 */       for (byte b = 0; b < vector1.size(); b++) {
/* 564 */         EntityItem entityItem = vector1.elementAt(b);
/*     */         
/* 566 */         Vector<EntityItem> vector3 = null;
/* 567 */         if (str1.equals("D")) {
/* 568 */           vector3 = entityItem.getDownLink();
/*     */         } else {
/* 570 */           vector3 = entityItem.getUpLink();
/*     */         } 
/* 572 */         for (byte b1 = 0; b1 < vector3.size(); b1++) {
/* 573 */           EntityItem entityItem1 = vector3.elementAt(b1);
/*     */           
/* 575 */           if (entityItem1.getEntityType().equals(str2)) {
/* 576 */             if (stringTokenizer.hasMoreTokens()) {
/*     */               
/* 578 */               vector.add(entityItem1);
/*     */             } else {
/*     */               
/* 581 */               vector2.add(entityItem1);
/* 582 */               ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem.getItems: find entity key=" + entityItem1.getKey() + NEWLINE);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 587 */       vector1.clear();
/* 588 */       vector1 = vector;
/*     */     } 
/*     */     
/* 591 */     vector1.clear();
/* 592 */     return vector2;
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
/*     */   private boolean putWarrToHb(Hashtable<String, String[]> paramHashtable, EntityItem paramEntityItem, String paramString1, String paramString2, StringBuffer paramStringBuffer) {
/* 604 */     boolean bool = false;
/* 605 */     Vector<EntityItem> vector = new Vector(1);
/* 606 */     vector.add(paramEntityItem);
/* 607 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1, ":");
/* 608 */     while (stringTokenizer.hasMoreTokens()) {
/* 609 */       String str1 = stringTokenizer.nextToken();
/* 610 */       String str2 = paramString2;
/* 611 */       if (stringTokenizer.hasMoreTokens()) {
/* 612 */         str2 = stringTokenizer.nextToken();
/*     */       }
/* 614 */       ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem.putWarrToHb: node:" + this.nodeName + " path:" + paramString1 + " dir:" + str1 + " destination " + str2 + NEWLINE);
/*     */ 
/*     */       
/* 617 */       Vector<EntityItem> vector1 = new Vector();
/* 618 */       for (byte b = 0; b < vector.size(); b++) {
/* 619 */         EntityItem entityItem = vector.elementAt(b);
/* 620 */         ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem.putWarrToHb: loop pitem " + entityItem.getKey() + NEWLINE);
/* 621 */         Vector<EntityItem> vector2 = null;
/* 622 */         if (str1.equals("D")) {
/* 623 */           vector2 = entityItem.getDownLink();
/*     */         } else {
/* 625 */           vector2 = entityItem.getUpLink();
/*     */         } 
/* 627 */         for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 628 */           EntityItem entityItem1 = vector2.elementAt(b1);
/*     */           
/* 630 */           if (entityItem1.getEntityType().equals(str2)) {
/* 631 */             if (stringTokenizer.hasMoreTokens()) {
/*     */               
/* 633 */               vector1.add(entityItem1);
/*     */             } else {
/*     */               
/* 636 */               byte b2 = 0;
/* 637 */               String str = "@@";
/*     */               
/* 639 */               DiffEntity diffEntity = (DiffEntity)paramHashtable.get(entityItem.getKey());
/* 640 */               if (diffEntity.isDeleted()) {
/* 641 */                 boolean bool1 = checkMODELWARRcntry(paramHashtable, diffEntity.getPriorEntityItem(), paramStringBuffer);
/* 642 */                 if (bool1) {
/* 643 */                   str = "Delete";
/*     */                 }
/* 645 */                 b2 = 1;
/* 646 */               } else if (diffEntity.isNew()) {
/* 647 */                 boolean bool1 = checkMODELWARRcntry(paramHashtable, diffEntity.getCurrentEntityItem(), paramStringBuffer);
/* 648 */                 if (bool1) {
/* 649 */                   str = "Update";
/*     */                 }
/* 651 */                 b2 = 2;
/*     */               } else {
/* 653 */                 boolean bool1 = checkMODELWARRcntry(paramHashtable, diffEntity.getCurrentEntityItem(), paramStringBuffer);
/* 654 */                 boolean bool2 = checkMODELWARRcntry(paramHashtable, diffEntity.getPriorEntityItem(), paramStringBuffer);
/* 655 */                 if (bool2 == true && bool1 == true) {
/* 656 */                   str = "Update";
/* 657 */                   b2 = 3;
/* 658 */                 } else if (!bool2 && bool1 == true) {
/* 659 */                   str = "Update";
/* 660 */                   b2 = 4;
/* 661 */                 } else if (bool2 == true && !bool1) {
/* 662 */                   str = "Delete";
/* 663 */                   b2 = 5;
/*     */                 } 
/*     */               } 
/*     */               
/* 667 */               if (!"@@".equals(str)) {
/* 668 */                 bool = true;
/*     */                 
/* 670 */                 ABRUtil.append(paramStringBuffer, "check is " + b2 + NEWLINE);
/*     */ 
/*     */                 
/* 673 */                 String str3 = this.prekey + entityItem1.getKey();
/* 674 */                 if (!paramHashtable.containsKey(str3)) {
/* 675 */                   String[] arrayOfString = { entityItem1.getKey(), entityItem.getKey(), str };
/* 676 */                   paramHashtable.put(str3, arrayOfString);
/*     */                 } 
/*     */               } 
/* 679 */               ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroupElem.putWarrToHb: find entity key=" + entityItem1.getKey() + " Relator :" + entityItem.getKey() + " has country in <AVAILIBILITYLIST> countrylist :" + str + NEWLINE);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 684 */       vector.clear();
/* 685 */       vector = vector1;
/*     */     } 
/*     */     
/* 688 */     vector.clear();
/*     */     
/* 690 */     return bool;
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
/*     */   private Vector getWWARvct(Hashtable paramHashtable, Vector<EntityItem> paramVector, StringBuffer paramStringBuffer) {
/* 707 */     Vector<DiffEntity> vector = new Vector();
/* 708 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 709 */       EntityItem entityItem1 = paramVector.elementAt(b);
/* 710 */       Vector<EntityItem> vector1 = getItems(entityItem1, "U:WWSEOLSEO:U", "WWSEO", paramStringBuffer);
/* 711 */       if (vector1 == null || vector1.size() == 0) {
/* 712 */         return null;
/*     */       }
/* 714 */       boolean bool = false;
/* 715 */       boolean bool1 = false;
/* 716 */       EntityItem entityItem2 = vector1.elementAt(0);
/*     */ 
/*     */ 
/*     */       
/* 720 */       String str = PokUtils.getAttributeFlagValue(entityItem2, "SEOORDERCODE");
/* 721 */       if (str != null && "20".equals(str)) {
/* 722 */         bool = true;
/*     */       }
/* 724 */       if (bool) {
/* 725 */         bool1 = putWarrToHb(paramHashtable, entityItem2, "D:WWSEOPRODSTRUCT:D:PRODSTRUCT:D:PRODSTRUCTWARR:D", "WARR", paramStringBuffer);
/*     */         
/* 727 */         ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroup.getWWARvct: derive from PRODSTRUCT, return values: " + bool1 + NEWLINE);
/*     */       } 
/*     */ 
/*     */       
/* 731 */       if (!bool1) {
/* 732 */         putWarrToHb(paramHashtable, entityItem2, "U:MODELWWSEO:U:MODEL:D:MODELWARR:D", "WARR", paramStringBuffer);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 737 */     Iterator<String> iterator = (Iterator)paramHashtable.keys();
/* 738 */     while (iterator.hasNext()) {
/* 739 */       String str = iterator.next();
/* 740 */       if (str.startsWith(this.prekey)) {
/* 741 */         String[] arrayOfString = (String[])paramHashtable.get(str);
/* 742 */         if (arrayOfString != null) {
/* 743 */           DiffEntity diffEntity = (DiffEntity)paramHashtable.get(arrayOfString[0]);
/* 744 */           if (diffEntity != null) {
/* 745 */             vector.add(diffEntity);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 751 */     return vector;
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
/*     */   private Vector getWARRRelatorVect(Hashtable paramHashtable, EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 763 */     Vector<DiffEntity> vector = new Vector();
/* 764 */     Vector<EntityItem> vector1 = paramEntityItem.getUpLink();
/* 765 */     if (vector1 != null) {
/* 766 */       for (byte b = 0; b < vector1.size(); b++) {
/* 767 */         EntityItem entityItem = vector1.elementAt(b);
/* 768 */         String str = entityItem.getKey();
/* 769 */         ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroup.getWARRRelator: get from hashtable: key=" + str + NEWLINE);
/* 770 */         DiffEntity diffEntity = (DiffEntity)paramHashtable.get(str);
/* 771 */         if (diffEntity != null) {
/* 772 */           vector.add(diffEntity);
/*     */         }
/*     */       } 
/*     */     } else {
/* 776 */       return null;
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
/* 789 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkMODELWARRcntry(Hashtable paramHashtable, EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 799 */     String str = (String)paramHashtable.get(this.countryKey);
/* 800 */     ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroup.checkMODELWARRcntry countrylist from AVAILBILITYLSIT: " + str + NEWLINE);
/* 801 */     boolean bool = false;
/* 802 */     if (str != null) {
/* 803 */       if (str.equalsIgnoreCase("ALL")) {
/* 804 */         return true;
/*     */       }
/* 806 */       Vector<String> vector = new Vector();
/* 807 */       StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/* 808 */       while (stringTokenizer.hasMoreTokens())
/*     */       {
/* 810 */         vector.addElement(stringTokenizer.nextToken());
/*     */       }
/* 812 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("COUNTRYLIST");
/* 813 */       ABRUtil.append(paramStringBuffer, "XMLLSEOWARRGroup.checkMODELWARRcntry for " + paramEntityItem.getKey() + " : ctryAtt " + 
/* 814 */           PokUtils.getAttributeFlagValue(paramEntityItem, "COUNTRYLIST") + NEWLINE);
/* 815 */       if (eANFlagAttribute != null) {
/* 816 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 817 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */           
/* 819 */           if (arrayOfMetaFlag[b].isSelected()) {
/* 820 */             String str1 = arrayOfMetaFlag[b].getFlagCode();
/* 821 */             if (vector.contains(str1)) {
/* 822 */               bool = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 831 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLLSEOWARRGroupElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */