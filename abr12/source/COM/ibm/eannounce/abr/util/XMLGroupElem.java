/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLGroupElem
/*     */   extends XMLElem
/*     */ {
/*  87 */   private String path = null;
/*  88 */   private String etype = null;
/*     */   private boolean isMultUse = false;
/*  90 */   private int ilevel = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLGroupElem(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 106 */     super(paramString1);
/* 107 */     this.etype = paramString2;
/* 108 */     this.path = paramString3;
/* 109 */     this.isMultUse = paramBoolean;
/*     */   }
/*     */   
/*     */   public XMLGroupElem(String paramString1, String paramString2, String paramString3, boolean paramBoolean, int paramInt) {
/* 113 */     super(paramString1);
/* 114 */     this.etype = paramString2;
/* 115 */     this.path = paramString3;
/* 116 */     this.isMultUse = paramBoolean;
/* 117 */     this.ilevel = paramInt;
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
/*     */   public XMLGroupElem(String paramString1, String paramString2, String paramString3) {
/* 133 */     super(paramString1);
/* 134 */     this.etype = paramString2;
/* 135 */     this.path = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLGroupElem(String paramString1, String paramString2) {
/* 146 */     super(paramString1);
/* 147 */     this.etype = paramString2;
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
/*     */   public XMLGroupElem(String paramString) {
/* 159 */     super(paramString);
/* 160 */     this.etype = "ROOT";
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
/* 185 */     D.ebug(0, "Working on the item:" + this.nodeName);
/*     */ 
/*     */ 
/*     */     
/* 189 */     Vector vector = getItems(paramHashtable, paramDiffEntity, paramStringBuffer);
/*     */     
/* 191 */     if (vector == null) {
/* 192 */       if (this.nodeName == null) this.nodeName = "ERROR"; 
/* 193 */       Element element = paramDocument.createElement(this.nodeName);
/* 194 */       addXMLAttrs(element);
/*     */       
/* 196 */       if (paramElement == null) {
/* 197 */         paramDocument.appendChild(element);
/*     */       } else {
/* 199 */         paramElement.appendChild(element);
/*     */       } 
/*     */       
/* 202 */       element.appendChild(paramDocument.createTextNode("Error: " + this.etype + " not found in extract!"));
/*     */       
/* 204 */       if (this.isReq) {
/* 205 */         throw new IOException(this.nodeName + " is required but " + this.etype + " is not in extract");
/*     */       }
/*     */       
/* 208 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 209 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 210 */         xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */       } 
/*     */     } else {
/*     */       
/* 214 */       Vector<DiffEntity> vector1 = getEntities(vector);
/*     */       
/* 216 */       if (this.nodeName != null) {
/* 217 */         String str = null;
/* 218 */         if (paramElement == null) {
/* 219 */           str = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + this.nodeName;
/*     */         }
/*     */         
/* 222 */         Element element = paramDocument.createElementNS(str, this.nodeName);
/* 223 */         addXMLAttrs(element);
/*     */         
/* 225 */         if (paramElement == null) {
/* 226 */           ABRUtil.append(paramStringBuffer, "create root1: " + str + " " + this.nodeName);
/* 227 */           paramDocument.appendChild(element);
/* 228 */           element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str);
/*     */         } else {
/* 230 */           paramElement.appendChild(element);
/*     */         } 
/*     */ 
/*     */         
/* 234 */         if (vector1.size() == 0) {
/*     */ 
/*     */           
/* 237 */           element.appendChild(paramDocument.createTextNode("@@"));
/* 238 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " No entities found for " + this.etype + NEWLINE);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 244 */         for (byte b = 0; b < vector1.size(); b++) {
/*     */           
/* 246 */           DiffEntity diffEntity = vector1.elementAt(b);
/*     */ 
/*     */           
/* 249 */           if (!diffEntity.isRoot() && !hasChanges(paramHashtable, diffEntity, paramStringBuffer)) {
/* 250 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " No Changes found in " + diffEntity
/* 251 */                 .getKey() + NEWLINE);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 256 */             for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 257 */               XMLElem xMLElem = this.childVct.elementAt(b1);
/* 258 */               xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, diffEntity, paramStringBuffer);
/*     */             } 
/*     */           } 
/*     */         } 
/* 262 */         vector1.clear();
/*     */         
/* 264 */         if (!element.hasChildNodes())
/*     */         {
/* 266 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 274 */         if (vector1.size() == 0) {
/* 275 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " No entities found for " + this.etype + NEWLINE);
/*     */ 
/*     */           
/* 278 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 279 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 280 */             xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, paramElement, (DiffEntity)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 286 */         for (byte b = 0; b < vector1.size(); b++) {
/*     */           
/* 288 */           DiffEntity diffEntity = vector1.elementAt(b);
/*     */ 
/*     */           
/* 291 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 292 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 293 */             xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, paramElement, diffEntity, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 296 */         vector1.clear();
/*     */       } 
/* 298 */       vector.clear();
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
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 326 */     D.ebug(0, "Working on the item:" + this.nodeName);
/*     */     
/* 328 */     EntityGroup entityGroup = null;
/* 329 */     if ("ROOT".equals(this.etype)) {
/* 330 */       entityGroup = paramEntityList.getParentEntityGroup();
/*     */     } else {
/* 332 */       entityGroup = paramEntityList.getEntityGroup(this.etype);
/*     */     } 
/*     */     
/* 335 */     if (entityGroup == null) {
/* 336 */       Element element = paramDocument.createElement(this.nodeName);
/* 337 */       addXMLAttrs(element);
/* 338 */       if (paramElement == null) {
/* 339 */         paramDocument.appendChild(element);
/*     */       } else {
/* 341 */         paramElement.appendChild(element);
/*     */       } 
/*     */       
/* 344 */       element.appendChild(paramDocument.createTextNode("Error: " + this.etype + " not found in extract!"));
/*     */       
/* 346 */       if (this.isReq) {
/* 347 */         throw new IOException(this.nodeName + " is required but " + this.etype + " is not in extract");
/*     */       }
/*     */       
/* 350 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 351 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 352 */         xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
/*     */       } 
/*     */     } else {
/*     */       
/* 356 */       Vector<EntityItem> vector = getEntities(entityGroup);
/*     */ 
/*     */ 
/*     */       
/* 360 */       if (this.path != null && paramEntityItem != null) {
/* 361 */         EntityItem entityItem = paramEntityItem;
/* 362 */         Vector<EntityItem> vector1 = new Vector(1);
/* 363 */         Vector<EntityItem> vector2 = new Vector(1);
/* 364 */         vector2.add(entityItem);
/* 365 */         StringTokenizer stringTokenizer = new StringTokenizer(this.path, ":");
/* 366 */         while (stringTokenizer.hasMoreTokens()) {
/* 367 */           String str1 = stringTokenizer.nextToken();
/* 368 */           String str2 = this.etype;
/* 369 */           if (stringTokenizer.hasMoreTokens()) {
/* 370 */             str2 = stringTokenizer.nextToken();
/*     */           }
/* 372 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " dir:" + str1 + " destination " + str2 + NEWLINE);
/*     */ 
/*     */           
/* 375 */           Vector<EntityItem> vector3 = new Vector();
/* 376 */           for (byte b = 0; b < vector2.size(); b++) {
/* 377 */             EntityItem entityItem1 = vector2.elementAt(b);
/* 378 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem: loop pitem " + entityItem1.getKey() + NEWLINE);
/* 379 */             Vector<EntityItem> vector4 = null;
/* 380 */             if (str1.equals("D")) {
/* 381 */               vector4 = entityItem1.getDownLink();
/*     */             } else {
/* 383 */               vector4 = entityItem1.getUpLink();
/*     */             } 
/* 385 */             for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 386 */               EntityItem entityItem2 = vector4.elementAt(b1);
/* 387 */               ABRUtil.append(paramStringBuffer, "XMLGroupElem: linkloop entity " + entityItem2.getKey() + NEWLINE);
/* 388 */               if (entityItem2.getEntityType().equals(str2)) {
/* 389 */                 if (stringTokenizer.hasMoreTokens()) {
/*     */                   
/* 391 */                   vector3.add(entityItem2);
/*     */                 } else {
/* 393 */                   vector1.add(entityItem2);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/* 398 */           vector2 = vector3;
/*     */         } 
/* 400 */         vector = vector1;
/*     */       } 
/*     */       
/* 403 */       if (this.nodeName != null) {
/*     */         
/* 405 */         String str = null;
/* 406 */         if (paramElement == null) {
/* 407 */           str = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + this.nodeName;
/*     */         }
/*     */         
/* 410 */         Element element = paramDocument.createElementNS(str, this.nodeName);
/* 411 */         addXMLAttrs(element);
/*     */         
/* 413 */         if (paramElement == null) {
/* 414 */           ABRUtil.append(paramStringBuffer, "create root2: " + str + " " + this.nodeName);
/* 415 */           paramDocument.appendChild(element);
/* 416 */           element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str);
/*     */         } else {
/* 418 */           paramElement.appendChild(element);
/*     */         } 
/*     */ 
/*     */         
/* 422 */         if (vector.size() == 0) {
/*     */ 
/*     */           
/* 425 */           element.appendChild(paramDocument.createTextNode("@@"));
/* 426 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " No entities found for " + this.etype + NEWLINE);
/*     */           
/* 428 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 429 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 430 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, (EntityItem)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 436 */         for (byte b = 0; b < vector.size(); b++) {
/* 437 */           EntityItem entityItem = vector.elementAt(b);
/*     */ 
/*     */           
/* 440 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 441 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 442 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, entityItem, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 445 */         vector.clear();
/*     */         
/* 447 */         if (!element.hasChildNodes())
/*     */         {
/* 449 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 458 */         if (vector.size() == 0) {
/* 459 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " No entities found for " + this.etype + NEWLINE);
/*     */           
/* 461 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 462 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 463 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, (EntityItem)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/* 468 */         for (byte b = 0; b < vector.size(); b++) {
/* 469 */           EntityItem entityItem = vector.elementAt(b);
/*     */ 
/*     */           
/* 472 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 473 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 474 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, entityItem, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 477 */         vector.clear();
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
/* 490 */     boolean bool1 = false;
/* 491 */     ABRUtil.append(paramStringBuffer, "XMLGroupElem.hasChanges entered for node:" + this.nodeName + " " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */ 
/*     */     
/* 495 */     String[] arrayOfString = PokUtils.convertToArray(this.etype);
/* 496 */     boolean bool2 = false; byte b;
/* 497 */     for (b = 0; b < arrayOfString.length; b++) {
/* 498 */       if (arrayOfString[b].equals(paramDiffEntity.getEntityType())) bool2 = true;
/*     */     
/*     */     } 
/* 501 */     if (paramDiffEntity != null && bool2) {
/* 502 */       for (b = 0; b < this.childVct.size() && !bool1; b++) {
/* 503 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 504 */         if (xMLElem.hasChanges(paramHashtable, paramDiffEntity, paramStringBuffer)) {
/* 505 */           bool1 = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 510 */       Vector<DiffEntity> vector = getItems(paramHashtable, paramDiffEntity, paramStringBuffer);
/* 511 */       if (vector != null) {
/*     */         byte b1;
/* 513 */         label40: for (b1 = 0; b1 < vector.size(); b1++) {
/* 514 */           DiffEntity diffEntity = vector.elementAt(b1);
/* 515 */           for (byte b2 = 0; b2 < this.childVct.size() && !bool1; b2++) {
/* 516 */             XMLElem xMLElem = this.childVct.elementAt(b2);
/* 517 */             if (xMLElem.hasChanges(paramHashtable, diffEntity, paramStringBuffer)) {
/* 518 */               bool1 = true;
/*     */               break label40;
/*     */             } 
/*     */           } 
/*     */         } 
/* 523 */         vector.clear();
/*     */       } 
/*     */     } 
/* 526 */     return bool1;
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
/* 547 */     Vector<DiffEntity> vector = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 552 */     if (this.path != null && paramDiffEntity != null) {
/* 553 */       if (this.isMultUse) {
/*     */         
/* 555 */         ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: path1=" + this.path + NEWLINE);
/*     */ 
/*     */         
/* 558 */         Vector<DiffEntity> vector1 = new Vector(1);
/* 559 */         Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 560 */         String str1 = "";
/* 561 */         Vector<DiffEntity> vector2 = (Vector)paramHashtable.get(this.etype);
/* 562 */         StringTokenizer stringTokenizer = new StringTokenizer(this.path, ":");
/* 563 */         String str2 = "@@";
/* 564 */         while (stringTokenizer.hasMoreTokens()) {
/* 565 */           String str = stringTokenizer.nextToken();
/* 566 */           if (stringTokenizer.hasMoreTokens()) {
/* 567 */             str2 = stringTokenizer.nextToken();
/* 568 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: node:" + this.nodeName + " path:" + this.path + " dir:" + str + " relator " + str2 + NEWLINE);
/*     */             break;
/*     */           } 
/*     */         } 
/* 572 */         for (byte b = 0; b < vector2.size(); b++) {
/* 573 */           DiffEntity diffEntity = vector2.elementAt(b);
/* 574 */           StringTokenizer stringTokenizer1 = new StringTokenizer(diffEntity.toString(), " ");
/* 575 */           String str = "@@";
/* 576 */           while (stringTokenizer1.hasMoreTokens()) {
/* 577 */             str = stringTokenizer1.nextToken();
/* 578 */             if (str.startsWith("path:"))
/*     */               break; 
/*     */           } 
/* 581 */           if (!"@@".equals(str)) {
/* 582 */             StringTokenizer stringTokenizer2 = new StringTokenizer(str, ":");
/*     */             
/* 584 */             if (stringTokenizer2.hasMoreTokens()) {
/* 585 */               String str3 = stringTokenizer2.nextToken();
/* 586 */               if (str3.equals("path")) {
/* 587 */                 str3 = stringTokenizer2.nextToken();
/*     */               }
/* 589 */               if (str3.startsWith(str2) && 
/* 590 */                 stringTokenizer2.hasMoreTokens() && 
/* 591 */                 stringTokenizer2.nextToken().startsWith(paramDiffEntity.getKey())) {
/*     */                 
/* 593 */                 str1 = diffEntity.getKey();
/* 594 */                 if (!hashtable.contains(str1)) {
/* 595 */                   hashtable.put(str1, str1);
/* 596 */                   vector1.add(diffEntity);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 605 */         vector = vector1;
/*     */       } else {
/*     */         
/* 608 */         ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: path2=" + this.path + NEWLINE);
/* 609 */         EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 610 */         if (paramDiffEntity.isDeleted()) {
/* 611 */           entityItem = paramDiffEntity.getPriorEntityItem();
/*     */         }
/* 613 */         Vector<DiffEntity> vector1 = new Vector(1);
/* 614 */         Vector<EntityItem> vector2 = new Vector(1);
/* 615 */         vector2.add(entityItem);
/* 616 */         StringTokenizer stringTokenizer = new StringTokenizer(this.path, ":");
/* 617 */         while (stringTokenizer.hasMoreTokens()) {
/* 618 */           String str1 = stringTokenizer.nextToken();
/* 619 */           String str2 = this.etype;
/* 620 */           if (stringTokenizer.hasMoreTokens()) {
/* 621 */             str2 = stringTokenizer.nextToken();
/*     */           }
/* 623 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: node:" + this.nodeName + " path:" + this.path + " dir:" + str1 + " destination " + str2 + NEWLINE);
/*     */ 
/*     */           
/* 626 */           Vector<EntityItem> vector3 = new Vector();
/* 627 */           for (byte b = 0; b < vector2.size(); b++) {
/* 628 */             EntityItem entityItem1 = vector2.elementAt(b);
/* 629 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: loop pitem " + entityItem1.getKey() + NEWLINE);
/* 630 */             Vector<EntityItem> vector4 = null;
/* 631 */             if (str1.equals("D")) {
/* 632 */               vector4 = entityItem1.getDownLink();
/*     */             } else {
/* 634 */               vector4 = entityItem1.getUpLink();
/*     */             } 
/* 636 */             for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 637 */               EntityItem entityItem2 = vector4.elementAt(b1);
/* 638 */               ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: linkloop entity " + entityItem2.getKey() + NEWLINE);
/* 639 */               if (entityItem2.getEntityType().equals(str2)) {
/* 640 */                 if (stringTokenizer.hasMoreTokens()) {
/*     */                   
/* 642 */                   vector3.add(entityItem2);
/*     */                 } else {
/*     */                   
/* 645 */                   DiffEntity diffEntity = (DiffEntity)paramHashtable.get(entityItem2.getKey());
/* 646 */                   if (diffEntity != null) {
/* 647 */                     if (this.ilevel > 0) {
/* 648 */                       if (diffEntity.getLevel() == this.ilevel) {
/* 649 */                         vector1.add(diffEntity);
/* 650 */                         ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: find entity key=" + entityItem2.getKey() + "de.level=" + diffEntity.getLevel() + "ilevel=" + this.ilevel + NEWLINE);
/*     */                       } 
/*     */                     } else {
/* 653 */                       vector1.add(diffEntity);
/*     */                     } 
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/* 660 */           vector2.clear();
/* 661 */           vector2 = vector3;
/*     */         } 
/* 663 */         vector = vector1;
/* 664 */         vector2.clear();
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 671 */       ABRUtil.append(paramStringBuffer, "test XMLGroupElem.getItems: path3=" + this.path + NEWLINE);
/* 672 */       String[] arrayOfString = PokUtils.convertToArray(this.etype);
/* 673 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 674 */         String str = arrayOfString[b];
/* 675 */         Vector<? extends DiffEntity> vector1 = (Vector)paramHashtable.get(str);
/* 676 */         if (vector1 != null) {
/* 677 */           if (vector == null) {
/* 678 */             vector = new Vector<>(vector1);
/*     */           } else {
/* 680 */             vector.addAll(vector1);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 691 */     return vector;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLGroupElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */