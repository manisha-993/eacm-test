/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
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
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
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
/*     */ public class XMLDistinctGroupElem
/*     */   extends XMLElem
/*     */ {
/*  34 */   private String path = null;
/*  35 */   private String etype = null;
/*     */   private boolean isMultUse = false;
/*     */   private boolean isOnce = false;
/*  38 */   private int ilevel = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLDistinctGroupElem(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2) {
/*  55 */     super(paramString1);
/*  56 */     this.etype = paramString2;
/*  57 */     this.path = paramString3;
/*  58 */     this.isMultUse = paramBoolean1;
/*  59 */     this.isOnce = paramBoolean2;
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
/*     */   public XMLDistinctGroupElem(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  76 */     super(paramString1);
/*  77 */     this.etype = paramString2;
/*  78 */     this.path = paramString3;
/*  79 */     this.isMultUse = paramBoolean;
/*     */   }
/*     */   
/*     */   public XMLDistinctGroupElem(String paramString1, String paramString2, String paramString3, boolean paramBoolean, int paramInt) {
/*  83 */     super(paramString1);
/*  84 */     this.etype = paramString2;
/*  85 */     this.path = paramString3;
/*  86 */     this.isMultUse = paramBoolean;
/*  87 */     this.ilevel = paramInt;
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
/*     */   public XMLDistinctGroupElem(String paramString1, String paramString2, String paramString3) {
/* 103 */     super(paramString1);
/* 104 */     this.etype = paramString2;
/* 105 */     this.path = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLDistinctGroupElem(String paramString1, String paramString2) {
/* 116 */     super(paramString1);
/* 117 */     this.etype = paramString2;
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
/*     */   public XMLDistinctGroupElem(String paramString) {
/* 129 */     super(paramString);
/* 130 */     this.etype = "ROOT";
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
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 158 */     Vector vector = getItems(paramHashtable, paramDiffEntity, paramStringBuffer);
/*     */     
/* 160 */     if (vector == null) {
/* 161 */       if (this.nodeName == null) this.nodeName = "ERROR"; 
/* 162 */       Element element = paramDocument.createElement(this.nodeName);
/* 163 */       addXMLAttrs(element);
/*     */       
/* 165 */       if (paramElement == null) {
/* 166 */         paramDocument.appendChild(element);
/*     */       } else {
/* 168 */         paramElement.appendChild(element);
/*     */       } 
/*     */       
/* 171 */       element.appendChild(paramDocument.createTextNode("Error: " + this.etype + " not found in extract!"));
/*     */       
/* 173 */       if (this.isReq) {
/* 174 */         throw new IOException(this.nodeName + " is required but " + this.etype + " is not in extract");
/*     */       }
/*     */       
/* 177 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 178 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 179 */         xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */       } 
/*     */     } else {
/*     */       
/* 183 */       Vector<DiffEntity> vector1 = getEntities(vector);
/*     */       
/* 185 */       if (this.nodeName != null) {
/* 186 */         String str = null;
/* 187 */         if (paramElement == null) {
/* 188 */           str = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + this.nodeName;
/*     */         }
/*     */         
/* 191 */         Element element = paramDocument.createElementNS(str, this.nodeName);
/* 192 */         addXMLAttrs(element);
/*     */         
/* 194 */         if (paramElement == null) {
/* 195 */           ABRUtil.append(paramStringBuffer, "create root1: " + str + " " + this.nodeName);
/* 196 */           paramDocument.appendChild(element);
/* 197 */           element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str);
/*     */         } else {
/* 199 */           paramElement.appendChild(element);
/*     */         } 
/*     */ 
/*     */         
/* 203 */         if (vector1.size() == 0) {
/*     */ 
/*     */           
/* 206 */           element.appendChild(paramDocument.createTextNode("@@"));
/* 207 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " No entities found for " + this.etype + NEWLINE);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 213 */         for (byte b = 0; b < vector1.size(); b++) {
/*     */           
/* 215 */           DiffEntity diffEntity = vector1.elementAt(b);
/*     */ 
/*     */           
/* 218 */           if (!diffEntity.isRoot() && !hasChanges(paramHashtable, diffEntity, paramStringBuffer)) {
/* 219 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " No Changes found in " + diffEntity
/* 220 */                 .getKey() + NEWLINE);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 225 */             for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 226 */               XMLElem xMLElem = this.childVct.elementAt(b1);
/* 227 */               xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, diffEntity, paramStringBuffer);
/*     */             } 
/*     */           } 
/*     */         } 
/* 231 */         vector1.clear();
/*     */         
/* 233 */         if (!element.hasChildNodes())
/*     */         {
/* 235 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 243 */         if (vector1.size() == 0) {
/* 244 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " No entities found for " + this.etype + NEWLINE);
/*     */ 
/*     */           
/* 247 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 248 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 249 */             xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, paramElement, (DiffEntity)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 255 */         for (byte b = 0; b < vector1.size(); b++) {
/*     */           
/* 257 */           DiffEntity diffEntity = vector1.elementAt(b);
/*     */ 
/*     */           
/* 260 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 261 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 262 */             xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, paramElement, diffEntity, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 265 */         vector1.clear();
/*     */       } 
/* 267 */       vector.clear();
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
/* 296 */     EntityGroup entityGroup = null;
/* 297 */     if ("ROOT".equals(this.etype)) {
/* 298 */       entityGroup = paramEntityList.getParentEntityGroup();
/*     */     } else {
/* 300 */       entityGroup = paramEntityList.getEntityGroup(this.etype);
/*     */     } 
/*     */     
/* 303 */     if (entityGroup == null) {
/* 304 */       Element element = paramDocument.createElement(this.nodeName);
/* 305 */       addXMLAttrs(element);
/* 306 */       if (paramElement == null) {
/* 307 */         paramDocument.appendChild(element);
/*     */       } else {
/* 309 */         paramElement.appendChild(element);
/*     */       } 
/*     */       
/* 312 */       element.appendChild(paramDocument.createTextNode("Error: " + this.etype + " not found in extract!"));
/*     */       
/* 314 */       if (this.isReq) {
/* 315 */         throw new IOException(this.nodeName + " is required but " + this.etype + " is not in extract");
/*     */       }
/*     */       
/* 318 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 319 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 320 */         xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
/*     */       } 
/*     */     } else {
/*     */       
/* 324 */       Vector<EntityItem> vector = getEntities(entityGroup);
/*     */ 
/*     */ 
/*     */       
/* 328 */       if (this.path != null && paramEntityItem != null) {
/* 329 */         EntityItem entityItem = paramEntityItem;
/* 330 */         Vector<EntityItem> vector1 = new Vector(1);
/* 331 */         Vector<EntityItem> vector2 = new Vector(1);
/* 332 */         vector2.add(entityItem);
/* 333 */         StringTokenizer stringTokenizer = new StringTokenizer(this.path, ":");
/* 334 */         while (stringTokenizer.hasMoreTokens()) {
/* 335 */           String str1 = stringTokenizer.nextToken();
/* 336 */           String str2 = this.etype;
/* 337 */           if (stringTokenizer.hasMoreTokens()) {
/* 338 */             str2 = stringTokenizer.nextToken();
/*     */           }
/* 340 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " dir:" + str1 + " destination " + str2 + NEWLINE);
/*     */ 
/*     */           
/* 343 */           Vector<EntityItem> vector3 = new Vector();
/* 344 */           for (byte b = 0; b < vector2.size(); b++) {
/* 345 */             EntityItem entityItem1 = vector2.elementAt(b);
/* 346 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem: loop pitem " + entityItem1.getKey() + NEWLINE);
/* 347 */             Vector<EntityItem> vector4 = null;
/* 348 */             if (str1.equals("D")) {
/* 349 */               vector4 = entityItem1.getDownLink();
/*     */             } else {
/* 351 */               vector4 = entityItem1.getUpLink();
/*     */             } 
/* 353 */             for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 354 */               EntityItem entityItem2 = vector4.elementAt(b1);
/* 355 */               ABRUtil.append(paramStringBuffer, "XMLGroupElem: linkloop entity " + entityItem2.getKey() + NEWLINE);
/* 356 */               if (entityItem2.getEntityType().equals(str2)) {
/* 357 */                 if (stringTokenizer.hasMoreTokens()) {
/*     */                   
/* 359 */                   vector3.add(entityItem2);
/*     */                 } else {
/* 361 */                   vector1.add(entityItem2);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/* 366 */           vector2 = vector3;
/*     */         } 
/* 368 */         vector = vector1;
/*     */       } 
/*     */       
/* 371 */       if (this.nodeName != null) {
/*     */         
/* 373 */         String str = null;
/* 374 */         if (paramElement == null) {
/* 375 */           str = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + this.nodeName;
/*     */         }
/*     */         
/* 378 */         Element element = paramDocument.createElementNS(str, this.nodeName);
/* 379 */         addXMLAttrs(element);
/*     */         
/* 381 */         if (paramElement == null) {
/* 382 */           ABRUtil.append(paramStringBuffer, "create root2: " + str + " " + this.nodeName);
/* 383 */           paramDocument.appendChild(element);
/* 384 */           element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str);
/*     */         } else {
/* 386 */           paramElement.appendChild(element);
/*     */         } 
/*     */ 
/*     */         
/* 390 */         if (vector.size() == 0) {
/*     */ 
/*     */           
/* 393 */           element.appendChild(paramDocument.createTextNode("@@"));
/* 394 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " No entities found for " + this.etype + NEWLINE);
/*     */           
/* 396 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 397 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 398 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, (EntityItem)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 404 */         for (byte b = 0; b < vector.size(); b++) {
/* 405 */           EntityItem entityItem = vector.elementAt(b);
/*     */ 
/*     */           
/* 408 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 409 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 410 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, entityItem, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 413 */         vector.clear();
/*     */         
/* 415 */         if (!element.hasChildNodes())
/*     */         {
/* 417 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 426 */         if (vector.size() == 0) {
/* 427 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " No entities found for " + this.etype + NEWLINE);
/*     */           
/* 429 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 430 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 431 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, (EntityItem)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/* 436 */         for (byte b = 0; b < vector.size(); b++) {
/* 437 */           EntityItem entityItem = vector.elementAt(b);
/*     */ 
/*     */           
/* 440 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 441 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 442 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, entityItem, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 445 */         vector.clear();
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
/* 458 */     boolean bool1 = false;
/* 459 */     ABRUtil.append(paramStringBuffer, "XMLGroupElem.hasChanges entered for node:" + this.nodeName + " " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */ 
/*     */     
/* 463 */     String[] arrayOfString = PokUtils.convertToArray(this.etype);
/* 464 */     boolean bool2 = false; byte b;
/* 465 */     for (b = 0; b < arrayOfString.length; b++) {
/* 466 */       if (arrayOfString[b].equals(paramDiffEntity.getEntityType())) bool2 = true;
/*     */     
/*     */     } 
/* 469 */     if (paramDiffEntity != null && bool2) {
/* 470 */       for (b = 0; b < this.childVct.size() && !bool1; b++) {
/* 471 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 472 */         if (xMLElem.hasChanges(paramHashtable, paramDiffEntity, paramStringBuffer)) {
/* 473 */           bool1 = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 478 */       Vector<DiffEntity> vector = getItems(paramHashtable, paramDiffEntity, paramStringBuffer);
/* 479 */       if (vector != null) {
/*     */         byte b1;
/* 481 */         label40: for (b1 = 0; b1 < vector.size(); b1++) {
/* 482 */           DiffEntity diffEntity = vector.elementAt(b1);
/* 483 */           for (byte b2 = 0; b2 < this.childVct.size() && !bool1; b2++) {
/* 484 */             XMLElem xMLElem = this.childVct.elementAt(b2);
/* 485 */             if (xMLElem.hasChanges(paramHashtable, diffEntity, paramStringBuffer)) {
/* 486 */               bool1 = true;
/*     */               break label40;
/*     */             } 
/*     */           } 
/*     */         } 
/* 491 */         vector.clear();
/*     */       } 
/*     */     } 
/* 494 */     return bool1;
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
/*     */   private Vector getItems(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 515 */     Vector<DiffEntity> vector = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 520 */     if (this.path != null && paramDiffEntity != null) {
/*     */       
/* 522 */       ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: path2=" + this.path + NEWLINE);
/* 523 */       EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 524 */       if (paramDiffEntity.isDeleted()) {
/* 525 */         entityItem = paramDiffEntity.getPriorEntityItem();
/*     */       }
/* 527 */       Vector<DiffEntity> vector1 = new Vector(1);
/* 528 */       Vector<EntityItem> vector2 = new Vector(1);
/* 529 */       vector2.add(entityItem);
/* 530 */       StringTokenizer stringTokenizer = new StringTokenizer(this.path, ":");
/* 531 */       while (stringTokenizer.hasMoreTokens()) {
/* 532 */         String str1 = stringTokenizer.nextToken();
/* 533 */         String str2 = this.etype;
/* 534 */         if (stringTokenizer.hasMoreTokens()) {
/* 535 */           str2 = stringTokenizer.nextToken();
/*     */         }
/* 537 */         ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: node:" + this.nodeName + " path:" + this.path + " dir:" + str1 + " destination " + str2 + NEWLINE);
/*     */ 
/*     */         
/* 540 */         Vector<EntityItem> vector3 = new Vector();
/* 541 */         for (byte b = 0; b < vector2.size(); b++) {
/* 542 */           EntityItem entityItem1 = vector2.elementAt(b);
/* 543 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: loop pitem " + entityItem1.getKey() + NEWLINE);
/* 544 */           Vector<EntityItem> vector4 = null;
/* 545 */           if (str1.equals("D")) {
/* 546 */             vector4 = entityItem1.getDownLink();
/*     */           } else {
/* 548 */             vector4 = entityItem1.getUpLink();
/*     */           } 
/* 550 */           for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 551 */             EntityItem entityItem2 = vector4.elementAt(b1);
/* 552 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: linkloop entity " + entityItem2.getKey() + NEWLINE);
/* 553 */             if (entityItem2.getEntityType().equals(str2)) {
/* 554 */               if (stringTokenizer.hasMoreTokens()) {
/*     */                 
/* 556 */                 vector3.add(entityItem2);
/*     */               } else {
/*     */                 
/* 559 */                 DiffEntity diffEntity = (DiffEntity)paramHashtable.get(entityItem2.getKey());
/* 560 */                 if (diffEntity != null) {
/* 561 */                   if (this.ilevel > 0) {
/* 562 */                     if (diffEntity.getLevel() == this.ilevel) {
/* 563 */                       vector1.add(diffEntity);
/* 564 */                       ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: find entity key=" + entityItem2.getKey() + "de.level=" + diffEntity.getLevel() + "ilevel=" + this.ilevel + NEWLINE);
/*     */                     } 
/*     */                   } else {
/* 567 */                     vector1.add(diffEntity);
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             }
/* 572 */             if (this.isOnce && 
/* 573 */               vector1.size() == 1) {
/* 574 */               vector2.clear();
/* 575 */               return vector1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 581 */         vector2.clear();
/* 582 */         vector2 = vector3;
/*     */       } 
/* 584 */       vector = vector1;
/* 585 */       vector2.clear();
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 592 */       String[] arrayOfString = PokUtils.convertToArray(this.etype);
/* 593 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 594 */         String str = arrayOfString[b];
/* 595 */         Vector<? extends DiffEntity> vector1 = (Vector)paramHashtable.get(str);
/* 596 */         if (vector1 != null) {
/* 597 */           if (vector == null) {
/* 598 */             vector = new Vector<>(vector1);
/*     */           } else {
/* 600 */             vector.addAll(vector1);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 605 */       if (this.isOnce) {
/* 606 */         Vector<DiffEntity> vector1 = new Vector();
/* 607 */         if (vector.size() > 0) {
/* 608 */           DiffEntity diffEntity = vector.elementAt(0);
/* 609 */           vector1.add(diffEntity);
/*     */         } 
/* 611 */         return vector1;
/*     */       } 
/*     */       
/* 614 */       if (this.isMultUse) {
/*     */         
/* 616 */         HashMap<Object, Object> hashMap = new HashMap<>();
/* 617 */         Vector vector1 = new Vector();
/* 618 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 619 */           DiffEntity diffEntity = vector.elementAt(b1);
/* 620 */           ABRUtil.append(paramStringBuffer, "XMLDistinctGroupElem.getItems: dffFeature=" + diffEntity.getKey() + " isDelete" + diffEntity.isDeleted() + " isNew" + diffEntity.isNew() + " isUpdate" + diffEntity.isChanged() + NEWLINE);
/*     */ 
/*     */           
/* 623 */           if ("FEATURE|SWFEATURE".equals(this.etype)) {
/* 624 */             if (diffEntity.toString().indexOf("LSEOPRODSTRUCT") > 0 || diffEntity.toString().indexOf("LSEOSWPRODSTRUCT") > 0 || diffEntity.toString().indexOf("WWSEOPRODSTRUCT") > 0 || diffEntity.toString().indexOf("WWSEOSWPRODSTRUCT") > 0) {
/* 625 */               String str = diffEntity.getKey();
/* 626 */               if (!hashMap.containsKey(str)) {
/* 627 */                 hashMap.put(str, diffEntity);
/*     */               }
/*     */             } 
/*     */           } else {
/* 631 */             String str = diffEntity.getKey();
/* 632 */             if (!hashMap.containsKey(str)) {
/* 633 */               hashMap.put(str, diffEntity);
/*     */             }
/*     */           } 
/*     */         } 
/* 637 */         Collection collection = hashMap.values();
/* 638 */         vector1.addAll(collection);
/* 639 */         return vector1;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 648 */     return vector;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLDistinctGroupElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */