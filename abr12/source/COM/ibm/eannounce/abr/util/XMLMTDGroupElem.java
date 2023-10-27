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
/*     */ public class XMLMTDGroupElem
/*     */   extends XMLElem
/*     */ {
/*  87 */   private String path = null;
/*  88 */   private String etype = null;
/*     */   private boolean isMultUse = false;
/*  90 */   private int ilevel = 0;
/*  91 */   private String domian = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLMTDGroupElem(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 107 */     super(paramString1);
/* 108 */     this.etype = paramString2;
/* 109 */     this.path = paramString3;
/* 110 */     this.isMultUse = paramBoolean;
/*     */   }
/*     */   
/*     */   public XMLMTDGroupElem(String paramString1, String paramString2, String paramString3, boolean paramBoolean, int paramInt) {
/* 114 */     super(paramString1);
/* 115 */     this.etype = paramString2;
/* 116 */     this.path = paramString3;
/* 117 */     this.isMultUse = paramBoolean;
/* 118 */     this.ilevel = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDomain(DiffEntity paramDiffEntity) {
/* 124 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 125 */     if (paramDiffEntity.isDeleted()) {
/* 126 */       entityItem = paramDiffEntity.getPriorEntityItem();
/*     */     }
/*     */     
/* 129 */     this.domian = getDomain(entityItem);
/*     */     
/* 131 */     return this.domian;
/*     */   }
/*     */   
/*     */   public String getDomain(EntityItem paramEntityItem) {
/* 135 */     this.domian = PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN");
/* 136 */     return this.domian;
/*     */   }
/*     */   
/*     */   public boolean compareDonmain(EntityItem paramEntityItem) {
/* 140 */     D.ebug(0, "P domain:" + this.domian);
/* 141 */     D.ebug(0, "C domain:" + PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN"));
/* 142 */     return this.domian.equals(PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN"));
/*     */   }
/*     */   
/*     */   public boolean compareDonmain(DiffEntity paramDiffEntity) {
/* 146 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 147 */     if (paramDiffEntity.isDeleted()) {
/* 148 */       entityItem = paramDiffEntity.getPriorEntityItem();
/*     */     }
/* 150 */     D.ebug(0, "P domain:" + this.domian);
/* 151 */     D.ebug(0, "C domain:" + PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN"));
/* 152 */     return this.domian.equals(PokUtils.getAttributeFlagValue(entityItem, "PDHDOMAIN"));
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
/*     */   public XMLMTDGroupElem(String paramString1, String paramString2, String paramString3) {
/* 168 */     super(paramString1);
/* 169 */     this.etype = paramString2;
/* 170 */     this.path = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLMTDGroupElem(String paramString1, String paramString2) {
/* 181 */     super(paramString1);
/* 182 */     this.etype = paramString2;
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
/*     */   public XMLMTDGroupElem(String paramString) {
/* 194 */     super(paramString);
/* 195 */     this.etype = "ROOT";
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
/* 220 */     D.ebug(0, "Working on the item:" + this.nodeName);
/*     */ 
/*     */     
/* 223 */     D.ebug(0, "Working in addElementds hashtable!");
/* 224 */     Vector vector = getItems(paramHashtable, paramDiffEntity, paramStringBuffer);
/* 225 */     this.domian = getDomain(paramDiffEntity);
/*     */     
/* 227 */     if (vector == null) {
/* 228 */       if (this.nodeName == null) this.nodeName = "ERROR"; 
/* 229 */       Element element = paramDocument.createElement(this.nodeName);
/* 230 */       addXMLAttrs(element);
/*     */       
/* 232 */       if (paramElement == null) {
/* 233 */         paramDocument.appendChild(element);
/*     */       } else {
/* 235 */         paramElement.appendChild(element);
/*     */       } 
/*     */       
/* 238 */       element.appendChild(paramDocument.createTextNode("Error: " + this.etype + " not found in extract!"));
/*     */       
/* 240 */       if (this.isReq) {
/* 241 */         throw new IOException(this.nodeName + " is required but " + this.etype + " is not in extract");
/*     */       }
/*     */       
/* 244 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 245 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 246 */         xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */       } 
/*     */     } else {
/*     */       
/* 250 */       Vector<DiffEntity> vector1 = getEntities(vector);
/*     */       
/* 252 */       if (this.nodeName != null) {
/* 253 */         String str = null;
/* 254 */         if (paramElement == null) {
/* 255 */           str = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + this.nodeName;
/*     */         }
/*     */         
/* 258 */         Element element = paramDocument.createElementNS(str, this.nodeName);
/* 259 */         addXMLAttrs(element);
/*     */         
/* 261 */         if (paramElement == null) {
/* 262 */           ABRUtil.append(paramStringBuffer, "create root1: " + str + " " + this.nodeName);
/* 263 */           paramDocument.appendChild(element);
/* 264 */           element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str);
/*     */         } else {
/* 266 */           paramElement.appendChild(element);
/*     */         } 
/*     */ 
/*     */         
/* 270 */         if (vector1.size() == 0) {
/*     */ 
/*     */           
/* 273 */           element.appendChild(paramDocument.createTextNode("@@"));
/* 274 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " No entities found for " + this.etype + NEWLINE);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 280 */         for (byte b = 0; b < vector1.size(); b++) {
/*     */           
/* 282 */           DiffEntity diffEntity = vector1.elementAt(b);
/*     */ 
/*     */           
/* 285 */           if (!diffEntity.isRoot() && !hasChanges(paramHashtable, diffEntity, paramStringBuffer) && !compareDonmain(diffEntity)) {
/* 286 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " No Changes found in " + diffEntity
/* 287 */                 .getKey() + NEWLINE);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 292 */             for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 293 */               XMLElem xMLElem = this.childVct.elementAt(b1);
/* 294 */               xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, diffEntity, paramStringBuffer);
/*     */             } 
/*     */           } 
/*     */         } 
/* 298 */         vector1.clear();
/*     */         
/* 300 */         if (!element.hasChildNodes())
/*     */         {
/* 302 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 310 */         if (vector1.size() == 0) {
/* 311 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " No entities found for " + this.etype + NEWLINE);
/*     */ 
/*     */           
/* 314 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 315 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 316 */             xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, paramElement, (DiffEntity)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 322 */         for (byte b = 0; b < vector1.size(); b++) {
/*     */           
/* 324 */           DiffEntity diffEntity = vector1.elementAt(b);
/*     */ 
/*     */ 
/*     */           
/* 328 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 329 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 330 */             xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, paramElement, diffEntity, paramStringBuffer);
/*     */           } 
/*     */         } 
/*     */         
/* 334 */         vector1.clear();
/*     */       } 
/* 336 */       vector.clear();
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
/* 364 */     D.ebug(0, "Working on the item:" + this.nodeName);
/*     */     
/* 366 */     EntityGroup entityGroup = null;
/* 367 */     if ("ROOT".equals(this.etype)) {
/* 368 */       entityGroup = paramEntityList.getParentEntityGroup();
/*     */     } else {
/* 370 */       entityGroup = paramEntityList.getEntityGroup(this.etype);
/*     */     } 
/* 372 */     this.domian = getDomain(paramEntityItem);
/* 373 */     D.ebug(0, "P domain:" + this.domian);
/* 374 */     if (entityGroup == null) {
/* 375 */       Element element = paramDocument.createElement(this.nodeName);
/* 376 */       addXMLAttrs(element);
/* 377 */       if (paramElement == null) {
/* 378 */         paramDocument.appendChild(element);
/*     */       } else {
/* 380 */         paramElement.appendChild(element);
/*     */       } 
/*     */       
/* 383 */       element.appendChild(paramDocument.createTextNode("Error: " + this.etype + " not found in extract!"));
/*     */       
/* 385 */       if (this.isReq) {
/* 386 */         throw new IOException(this.nodeName + " is required but " + this.etype + " is not in extract");
/*     */       }
/*     */       
/* 389 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 390 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 391 */         xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
/*     */       } 
/*     */     } else {
/*     */       
/* 395 */       Vector<EntityItem> vector = getEntities(entityGroup);
/*     */ 
/*     */ 
/*     */       
/* 399 */       if (this.path != null && paramEntityItem != null) {
/* 400 */         EntityItem entityItem = paramEntityItem;
/* 401 */         Vector<EntityItem> vector1 = new Vector(1);
/* 402 */         Vector<EntityItem> vector2 = new Vector(1);
/* 403 */         vector2.add(entityItem);
/* 404 */         StringTokenizer stringTokenizer = new StringTokenizer(this.path, ":");
/* 405 */         while (stringTokenizer.hasMoreTokens()) {
/* 406 */           String str1 = stringTokenizer.nextToken();
/* 407 */           String str2 = this.etype;
/* 408 */           if (stringTokenizer.hasMoreTokens()) {
/* 409 */             str2 = stringTokenizer.nextToken();
/*     */           }
/* 411 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " path:" + this.path + " dir:" + str1 + " destination " + str2 + NEWLINE);
/*     */ 
/*     */           
/* 414 */           Vector<EntityItem> vector3 = new Vector();
/* 415 */           for (byte b = 0; b < vector2.size(); b++) {
/* 416 */             EntityItem entityItem1 = vector2.elementAt(b);
/* 417 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem: loop pitem " + entityItem1.getKey() + NEWLINE);
/* 418 */             Vector<EntityItem> vector4 = null;
/* 419 */             if (str1.equals("D")) {
/* 420 */               vector4 = entityItem1.getDownLink();
/*     */             } else {
/* 422 */               vector4 = entityItem1.getUpLink();
/*     */             } 
/* 424 */             for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 425 */               EntityItem entityItem2 = vector4.elementAt(b1);
/* 426 */               ABRUtil.append(paramStringBuffer, "XMLGroupElem: linkloop entity " + entityItem2.getKey() + NEWLINE);
/* 427 */               if (entityItem2.getEntityType().equals(str2)) {
/* 428 */                 if (stringTokenizer.hasMoreTokens()) {
/*     */                   
/* 430 */                   vector3.add(entityItem2);
/*     */                 }
/* 432 */                 else if (compareDonmain(entityItem2)) {
/* 433 */                   vector1.add(entityItem2);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/* 438 */           vector2 = vector3;
/*     */         } 
/* 440 */         vector = vector1;
/*     */       } 
/*     */       
/* 443 */       if (this.nodeName != null) {
/*     */         
/* 445 */         String str = null;
/* 446 */         if (paramElement == null) {
/* 447 */           str = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + this.nodeName;
/*     */         }
/*     */         
/* 450 */         Element element = paramDocument.createElementNS(str, this.nodeName);
/* 451 */         addXMLAttrs(element);
/*     */         
/* 453 */         if (paramElement == null) {
/* 454 */           ABRUtil.append(paramStringBuffer, "create root2: " + str + " " + this.nodeName);
/* 455 */           paramDocument.appendChild(element);
/* 456 */           element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", str);
/*     */         } else {
/* 458 */           paramElement.appendChild(element);
/*     */         } 
/*     */ 
/*     */         
/* 462 */         if (vector.size() == 0) {
/*     */ 
/*     */           
/* 465 */           element.appendChild(paramDocument.createTextNode("@@"));
/* 466 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " No entities found for " + this.etype + NEWLINE);
/*     */           
/* 468 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 469 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 470 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, (EntityItem)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 476 */         for (byte b = 0; b < vector.size(); b++) {
/* 477 */           EntityItem entityItem = vector.elementAt(b);
/* 478 */           if (compareDonmain(entityItem))
/*     */           {
/*     */             
/* 481 */             for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 482 */               XMLElem xMLElem = this.childVct.elementAt(b1);
/* 483 */               xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, entityItem, paramStringBuffer);
/*     */             }  } 
/*     */         } 
/* 486 */         vector.clear();
/*     */         
/* 488 */         if (!element.hasChildNodes())
/*     */         {
/* 490 */           element.appendChild(paramDocument.createTextNode("@@"));
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 499 */         if (vector.size() == 0) {
/* 500 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem: node:" + this.nodeName + " No entities found for " + this.etype + NEWLINE);
/*     */           
/* 502 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 503 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 504 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, (EntityItem)null, paramStringBuffer);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/* 509 */         for (byte b = 0; b < vector.size(); b++) {
/* 510 */           EntityItem entityItem = vector.elementAt(b);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 515 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 516 */             XMLElem xMLElem = this.childVct.elementAt(b1);
/* 517 */             xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, paramElement, entityItem, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 520 */         vector.clear();
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
/* 533 */     boolean bool1 = false;
/* 534 */     ABRUtil.append(paramStringBuffer, "XMLGroupElem.hasChanges entered for node:" + this.nodeName + " " + paramDiffEntity.getKey() + NEWLINE);
/*     */ 
/*     */ 
/*     */     
/* 538 */     String[] arrayOfString = PokUtils.convertToArray(this.etype);
/* 539 */     boolean bool2 = false; byte b;
/* 540 */     for (b = 0; b < arrayOfString.length; b++) {
/* 541 */       if (arrayOfString[b].equals(paramDiffEntity.getEntityType())) bool2 = true;
/*     */     
/*     */     } 
/* 544 */     if (paramDiffEntity != null && bool2) {
/* 545 */       for (b = 0; b < this.childVct.size() && !bool1; b++) {
/* 546 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 547 */         if (xMLElem.hasChanges(paramHashtable, paramDiffEntity, paramStringBuffer)) {
/* 548 */           bool1 = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 553 */       Vector<DiffEntity> vector = getItems(paramHashtable, paramDiffEntity, paramStringBuffer);
/* 554 */       if (vector != null) {
/*     */         byte b1;
/* 556 */         label40: for (b1 = 0; b1 < vector.size(); b1++) {
/* 557 */           DiffEntity diffEntity = vector.elementAt(b1);
/* 558 */           for (byte b2 = 0; b2 < this.childVct.size() && !bool1; b2++) {
/* 559 */             XMLElem xMLElem = this.childVct.elementAt(b2);
/* 560 */             if (xMLElem.hasChanges(paramHashtable, diffEntity, paramStringBuffer)) {
/* 561 */               bool1 = true;
/*     */               break label40;
/*     */             } 
/*     */           } 
/*     */         } 
/* 566 */         vector.clear();
/*     */       } 
/*     */     } 
/* 569 */     return bool1;
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
/* 590 */     Vector<DiffEntity> vector = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 595 */     if (this.path != null && paramDiffEntity != null) {
/* 596 */       if (this.isMultUse) {
/*     */         
/* 598 */         ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: path1=" + this.path + NEWLINE);
/*     */ 
/*     */         
/* 601 */         Vector<DiffEntity> vector1 = new Vector(1);
/* 602 */         Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 603 */         String str1 = "";
/* 604 */         Vector<DiffEntity> vector2 = (Vector)paramHashtable.get(this.etype);
/* 605 */         StringTokenizer stringTokenizer = new StringTokenizer(this.path, ":");
/* 606 */         String str2 = "@@";
/* 607 */         while (stringTokenizer.hasMoreTokens()) {
/* 608 */           String str = stringTokenizer.nextToken();
/* 609 */           if (stringTokenizer.hasMoreTokens()) {
/* 610 */             str2 = stringTokenizer.nextToken();
/* 611 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: node:" + this.nodeName + " path:" + this.path + " dir:" + str + " relator " + str2 + NEWLINE);
/*     */             break;
/*     */           } 
/*     */         } 
/* 615 */         for (byte b = 0; b < vector2.size(); b++) {
/* 616 */           DiffEntity diffEntity = vector2.elementAt(b);
/* 617 */           StringTokenizer stringTokenizer1 = new StringTokenizer(diffEntity.toString(), " ");
/* 618 */           String str = "@@";
/* 619 */           while (stringTokenizer1.hasMoreTokens()) {
/* 620 */             str = stringTokenizer1.nextToken();
/* 621 */             if (str.startsWith("path:"))
/*     */               break; 
/*     */           } 
/* 624 */           if (!"@@".equals(str)) {
/* 625 */             StringTokenizer stringTokenizer2 = new StringTokenizer(str, ":");
/*     */             
/* 627 */             if (stringTokenizer2.hasMoreTokens()) {
/* 628 */               String str3 = stringTokenizer2.nextToken();
/* 629 */               if (str3.equals("path")) {
/* 630 */                 str3 = stringTokenizer2.nextToken();
/*     */               }
/* 632 */               if (str3.startsWith(str2) && 
/* 633 */                 stringTokenizer2.hasMoreTokens() && 
/* 634 */                 stringTokenizer2.nextToken().startsWith(paramDiffEntity.getKey())) {
/*     */                 
/* 636 */                 str1 = diffEntity.getKey();
/* 637 */                 if (!hashtable.contains(str1)) {
/* 638 */                   hashtable.put(str1, str1);
/* 639 */                   vector1.add(diffEntity);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 648 */         vector = vector1;
/*     */       } else {
/*     */         
/* 651 */         ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: path2=" + this.path + NEWLINE);
/* 652 */         EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/* 653 */         if (paramDiffEntity.isDeleted()) {
/* 654 */           entityItem = paramDiffEntity.getPriorEntityItem();
/*     */         }
/* 656 */         Vector<DiffEntity> vector1 = new Vector(1);
/* 657 */         Vector<EntityItem> vector2 = new Vector(1);
/* 658 */         vector2.add(entityItem);
/* 659 */         StringTokenizer stringTokenizer = new StringTokenizer(this.path, ":");
/* 660 */         while (stringTokenizer.hasMoreTokens()) {
/* 661 */           String str1 = stringTokenizer.nextToken();
/* 662 */           String str2 = this.etype;
/* 663 */           if (stringTokenizer.hasMoreTokens()) {
/* 664 */             str2 = stringTokenizer.nextToken();
/*     */           }
/* 666 */           ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: node:" + this.nodeName + " path:" + this.path + " dir:" + str1 + " destination " + str2 + NEWLINE);
/*     */ 
/*     */           
/* 669 */           Vector<EntityItem> vector3 = new Vector();
/* 670 */           for (byte b = 0; b < vector2.size(); b++) {
/* 671 */             EntityItem entityItem1 = vector2.elementAt(b);
/* 672 */             ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: loop pitem " + entityItem1.getKey() + NEWLINE);
/* 673 */             Vector<EntityItem> vector4 = null;
/* 674 */             if (str1.equals("D")) {
/* 675 */               vector4 = entityItem1.getDownLink();
/*     */             } else {
/* 677 */               vector4 = entityItem1.getUpLink();
/*     */             } 
/* 679 */             for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 680 */               EntityItem entityItem2 = vector4.elementAt(b1);
/* 681 */               ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: linkloop entity " + entityItem2.getKey() + NEWLINE);
/* 682 */               if (entityItem2.getEntityType().equals(str2)) {
/* 683 */                 if (stringTokenizer.hasMoreTokens()) {
/*     */                   
/* 685 */                   vector3.add(entityItem2);
/*     */                 } else {
/*     */                   
/* 688 */                   DiffEntity diffEntity = (DiffEntity)paramHashtable.get(entityItem2.getKey());
/* 689 */                   if (diffEntity != null) {
/* 690 */                     if (this.ilevel > 0) {
/* 691 */                       if (diffEntity.getLevel() == this.ilevel) {
/* 692 */                         vector1.add(diffEntity);
/* 693 */                         ABRUtil.append(paramStringBuffer, "XMLGroupElem.getItems: find entity key=" + entityItem2.getKey() + "de.level=" + diffEntity.getLevel() + "ilevel=" + this.ilevel + NEWLINE);
/*     */                       } 
/*     */                     } else {
/* 696 */                       vector1.add(diffEntity);
/*     */                     } 
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/* 703 */           vector2.clear();
/* 704 */           vector2 = vector3;
/*     */         } 
/* 706 */         vector = vector1;
/* 707 */         vector2.clear();
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 714 */       ABRUtil.append(paramStringBuffer, "test XMLGroupElem.getItems: path3=" + this.path + NEWLINE);
/* 715 */       String[] arrayOfString = PokUtils.convertToArray(this.etype);
/* 716 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 717 */         String str = arrayOfString[b];
/* 718 */         Vector<? extends DiffEntity> vector1 = (Vector)paramHashtable.get(str);
/* 719 */         if (vector1 != null) {
/* 720 */           if (vector == null) {
/* 721 */             vector = new Vector<>(vector1);
/*     */           } else {
/* 723 */             vector.addAll(vector1);
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
/* 734 */     return vector;
/*     */   }
/*     */   
/*     */   protected Vector getEntities(EntityGroup paramEntityGroup) {
/* 738 */     Vector<EntityItem> vector = new Vector();
/* 739 */     if (paramEntityGroup != null)
/* 740 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 741 */         if (this.domian == null || compareDonmain(paramEntityGroup.getEntityItem(b)))
/*     */         {
/* 743 */           vector.addElement(paramEntityGroup.getEntityItem(b));
/*     */         }
/*     */       }  
/* 746 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getEntities(Vector<Object> paramVector) {
/* 752 */     Vector vector = new Vector();
/* 753 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 754 */       Object object = paramVector.get(b);
/* 755 */       if (!(object instanceof EntityItem) || 
/* 756 */         this.domian == null || compareDonmain((EntityItem)paramVector.get(b)))
/*     */       {
/*     */         
/* 759 */         if (!(object instanceof DiffEntity) || 
/* 760 */           this.domian == null || compareDonmain((DiffEntity)paramVector.get(b)))
/*     */         {
/*     */           
/* 763 */           vector.add(paramVector.get(b));
/*     */         }
/*     */       }
/*     */     } 
/* 767 */     return vector;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLMTDGroupElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */