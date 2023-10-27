/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EANTextAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentFragment;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAPLElem
/*     */ {
/*     */   public static final int ATTRVAL = 0;
/*     */   public static final int FLAGVAL = 1;
/*  54 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  55 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*     */   protected static final String CHEAT = "@@";
/*  58 */   protected static final String[] AVAIL_ORDER = new String[] { "146", "143", "149", "AVT220" };
/*     */   protected boolean isRoot = false;
/*     */   protected boolean isReq = false;
/*     */   protected String nodeName;
/*  62 */   protected String etype = null;
/*  63 */   protected String attrCode = null;
/*  64 */   protected Vector childVct = new Vector(1);
/*  65 */   protected Hashtable xmlAttrTbl = new Hashtable<>();
/*  66 */   protected int attrSrc = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAPLElem(String paramString) {
/*  75 */     this(paramString, null, null, false, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAPLElem(String paramString1, String paramString2, String paramString3) {
/*  86 */     this(paramString1, paramString2, paramString3, false, 0);
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
/*     */   public SAPLElem(String paramString1, String paramString2, String paramString3, int paramInt) {
/*  99 */     this(paramString1, paramString2, paramString3, false, paramInt);
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
/*     */   public SAPLElem(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 112 */     this(paramString1, paramString2, paramString3, paramBoolean, 0);
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
/*     */   public SAPLElem(String paramString1, String paramString2, String paramString3, boolean paramBoolean, int paramInt) {
/* 126 */     this.nodeName = paramString1;
/* 127 */     this.etype = paramString2;
/* 128 */     this.attrCode = paramString3;
/* 129 */     this.isRoot = paramBoolean;
/* 130 */     this.attrSrc = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 139 */     StringBuffer stringBuffer = new StringBuffer();
/* 140 */     stringBuffer.append("Node:" + this.nodeName + " type:" + this.etype + " attr:" + this.attrCode + " root:" + this.isRoot + " req:" + this.isReq + " childCnt:" + this.childVct
/* 141 */         .size());
/* 142 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 143 */       stringBuffer.append(NEWLINE + "  " + this.childVct.elementAt(b).toString());
/*     */     }
/* 145 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(SAPLElem paramSAPLElem) {
/* 153 */     this.childVct.add(paramSAPLElem);
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
/*     */   public static String removeCheat(String paramString) {
/* 165 */     StringBuffer stringBuffer = new StringBuffer(paramString);
/* 166 */     int i = 0;
/* 167 */     while (i != -1) {
/*     */       
/* 169 */       i = stringBuffer.toString().indexOf("@@", i);
/* 170 */       if (i != -1) {
/* 171 */         stringBuffer.replace(i, i + "@@".length(), "");
/*     */       }
/*     */     } 
/* 174 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addXMLAttribute(String paramString1, String paramString2) {
/* 183 */     this.xmlAttrTbl.put(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRequired() {
/* 190 */     this.isReq = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getEntities(EntityGroup paramEntityGroup) {
/* 199 */     Vector<EntityItem> vector = new Vector();
/* 200 */     if (paramEntityGroup != null) {
/* 201 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 202 */         vector.addElement(paramEntityGroup.getEntityItem(b));
/*     */       }
/*     */     }
/* 205 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DocumentFragment parseXml(Document paramDocument, String paramString) {
/* 215 */     DocumentFragment documentFragment = null;
/*     */     
/* 217 */     paramString = "<fragment>" + paramString + "</fragment>";
/*     */     
/*     */     try {
/* 220 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 221 */       Document document = documentBuilderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(paramString)));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 226 */       Node node = paramDocument.importNode(document.getDocumentElement(), true);
/*     */ 
/*     */       
/* 229 */       documentFragment = paramDocument.createDocumentFragment();
/*     */ 
/*     */       
/* 232 */       while (node.hasChildNodes()) {
/* 233 */         documentFragment.appendChild(node.removeChild(node.getFirstChild()));
/*     */       }
/* 235 */     } catch (Exception exception) {
/*     */       
/* 237 */       exception.printStackTrace(System.out);
/*     */     } 
/*     */ 
/*     */     
/* 241 */     return documentFragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addXMLAttrs(Element paramElement) {
/* 250 */     for (Enumeration<String> enumeration = this.xmlAttrTbl.keys(); enumeration.hasMoreElements(); ) {
/*     */       
/* 252 */       String str1 = enumeration.nextElement();
/* 253 */       String str2 = (String)this.xmlAttrTbl.get(str1);
/* 254 */       paramElement.setAttribute(str1, str2);
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
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 278 */     if (this.etype == null) {
/* 279 */       if (paramElement == null) {
/* 280 */         Element element = paramDocument.createElement(this.nodeName);
/* 281 */         addXMLAttrs(element);
/* 282 */         paramDocument.appendChild(element);
/* 283 */         for (byte b = 0; b < this.childVct.size(); b++) {
/* 284 */           SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 285 */           sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
/*     */         } 
/*     */       } else {
/* 288 */         Element element = paramDocument.createElement(this.nodeName);
/* 289 */         addXMLAttrs(element);
/* 290 */         paramElement.appendChild(element);
/* 291 */         for (byte b = 0; b < this.childVct.size(); b++) {
/* 292 */           SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 293 */           sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 298 */       EntityGroup entityGroup = null;
/* 299 */       if (this.isRoot) {
/* 300 */         entityGroup = paramEntityList.getParentEntityGroup();
/*     */       } else {
/* 302 */         entityGroup = paramEntityList.getEntityGroup(this.etype);
/*     */       } 
/* 304 */       if (entityGroup == null) {
/* 305 */         Element element = paramDocument.createElement(this.nodeName);
/* 306 */         addXMLAttrs(element);
/* 307 */         paramElement.appendChild(element);
/* 308 */         element.appendChild(paramDocument.createTextNode("Error: " + this.etype + " not found in extract!"));
/*     */         
/* 310 */         if (this.isReq) {
/* 311 */           throw new IOException(this.nodeName + " is required but " + this.etype + " is not in extract");
/*     */         }
/*     */         
/* 314 */         for (byte b = 0; b < this.childVct.size(); b++) {
/* 315 */           SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 316 */           sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
/*     */         } 
/*     */       } else {
/*     */         
/* 320 */         Vector<EntityItem> vector = getEntities(entityGroup);
/* 321 */         if (vector.size() == 0) {
/* 322 */           Element element = paramDocument.createElement(this.nodeName);
/* 323 */           addXMLAttrs(element);
/* 324 */           paramElement.appendChild(element);
/* 325 */           if (this.attrCode != null) {
/* 326 */             element.appendChild(paramDocument.createTextNode("@@"));
/*     */           }
/* 328 */           if (this.isReq) {
/* 329 */             throw new IOException(this.nodeName + " is required but " + this.etype + " is not in the data");
/*     */           }
/*     */           
/* 332 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 333 */             SAPLElem sAPLElem = this.childVct.elementAt(b1);
/* 334 */             sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
/*     */           } 
/*     */         } 
/*     */         
/* 338 */         for (byte b = 0; b < vector.size(); b++) {
/* 339 */           EntityItem entityItem = vector.elementAt(b);
/*     */           
/* 341 */           Element element = paramDocument.createElement(this.nodeName);
/* 342 */           addXMLAttrs(element);
/* 343 */           paramElement.appendChild(element);
/* 344 */           Node node = getContentNode(paramDocument, entityItem, paramElement);
/* 345 */           if (node != null) {
/* 346 */             element.appendChild(node);
/*     */           }
/*     */           
/* 349 */           for (byte b1 = 0; b1 < this.childVct.size(); b1++) {
/* 350 */             SAPLElem sAPLElem = this.childVct.elementAt(b1);
/* 351 */             sAPLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 354 */         vector.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addGEOElements(Vector paramVector, Document paramDocument, Element paramElement, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 379 */     throw new IOException("SAPLElem addElements(Vector..) needs to be overridden by derived class");
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
/*     */   
/*     */   protected boolean hasNodeValueForNLS(EntityItem paramEntityItem) {
/* 408 */     String[] arrayOfString = PokUtils.convertToArray(this.attrCode);
/* 409 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 411 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 412 */     EntityList entityList = entityGroup.getEntityList();
/* 413 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 414 */       String str = arrayOfString[b];
/* 415 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(str);
/* 416 */       if (eANMetaAttribute != null) {
/* 417 */         Profile profile = entityList.getProfile();
/*     */ 
/*     */         
/* 420 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(str);
/* 421 */         if (eANAttribute instanceof EANTextAttribute) {
/* 422 */           NLSItem nLSItem = profile.getReadLanguage();
/* 423 */           int i = nLSItem.getNLSID();
/*     */           
/* 425 */           if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 426 */             stringBuffer.append(eANAttribute.toString());
/*     */           }
/*     */         } else {
/* 429 */           stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, str, ", ", "", false));
/*     */         } 
/*     */       } 
/*     */     } 
/* 433 */     return (stringBuffer.length() > 0);
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
/*     */   protected boolean hasNodeValueForNLS(EntityList paramEntityList, StringBuffer paramStringBuffer) {
/* 445 */     boolean bool = false;
/* 446 */     if (this.etype == null) {
/* 447 */       for (byte b = 0; b < this.childVct.size() && !bool; b++) {
/* 448 */         SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 449 */         bool = sAPLElem.hasNodeValueForNLS(paramEntityList, paramStringBuffer);
/*     */       } 
/*     */     } else {
/*     */       
/* 453 */       EntityGroup entityGroup = null;
/* 454 */       if (this.isRoot) {
/* 455 */         entityGroup = paramEntityList.getParentEntityGroup();
/*     */       } else {
/* 457 */         entityGroup = paramEntityList.getEntityGroup(this.etype);
/*     */       } 
/* 459 */       if (entityGroup != null) {
/*     */         
/* 461 */         Vector<EntityItem> vector = getEntities(entityGroup);
/* 462 */         for (byte b = 0; b < vector.size() && !bool; b++) {
/* 463 */           EntityItem entityItem = vector.elementAt(b);
/* 464 */           bool = hasNodeValueForNLS(entityItem);
/* 465 */           if (bool) {
/*     */             break;
/*     */           }
/*     */           
/* 469 */           for (byte b1 = 0; b1 < this.childVct.size() && !bool; b1++) {
/* 470 */             SAPLElem sAPLElem = this.childVct.elementAt(b1);
/* 471 */             bool = sAPLElem.hasNodeValueForNLS(paramEntityList, paramStringBuffer);
/*     */           } 
/*     */         } 
/* 474 */         vector.clear();
/*     */       } 
/*     */     } 
/* 477 */     return bool;
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
/*     */   protected Node getContentNode(Document paramDocument, EntityItem paramEntityItem, Element paramElement) throws IOException {
/*     */     Text text;
/* 491 */     String[] arrayOfString = PokUtils.convertToArray(this.attrCode);
/*     */     
/* 493 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 494 */     EntityList entityList = entityGroup.getEntityList();
/* 495 */     DocumentFragment documentFragment = null;
/* 496 */     StringBuffer stringBuffer = new StringBuffer();
/* 497 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 498 */       String str1 = "";
/* 499 */       String str2 = arrayOfString[b];
/* 500 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(str2);
/* 501 */       if (eANMetaAttribute == null) {
/* 502 */         if (this.isReq) {
/* 503 */           throw new IOException(this.nodeName + " is required but " + str2 + " is not in " + paramEntityItem
/* 504 */               .getEntityType() + " META data");
/*     */         }
/*     */ 
/*     */         
/* 508 */         str1 = "Error: Attribute " + str2 + " not found in " + paramEntityItem.getEntityType() + " META data.";
/* 509 */         stringBuffer.append(str1);
/*     */       } else {
/* 511 */         Profile profile = entityList.getProfile();
/*     */ 
/*     */         
/* 514 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(str2);
/* 515 */         if (eANAttribute instanceof EANTextAttribute) {
/* 516 */           NLSItem nLSItem = profile.getReadLanguage();
/* 517 */           int i = nLSItem.getNLSID();
/*     */           
/* 519 */           if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 520 */             str1 = eANAttribute.toString();
/*     */           } else {
/*     */             
/* 523 */             str1 = "@@";
/*     */           } 
/*     */         } else {
/* 526 */           str1 = PokUtils.getAttributeValue(paramEntityItem, str2, ", ", "@@", false);
/*     */         } 
/*     */         
/* 529 */         if (this.isReq && str1.equals("@@")) {
/* 530 */           throw new IOException(this.nodeName + " is required but " + str2 + " is not set in " + paramEntityItem
/* 531 */               .getKey());
/*     */         }
/* 533 */         if (eANMetaAttribute.getAttributeType().equals("X")) {
/*     */ 
/*     */           
/* 536 */           documentFragment = parseXml(paramDocument, str1);
/*     */         }
/* 538 */         else if (eANMetaAttribute.getAttributeType().equals("U") && this.attrSrc == 1) {
/* 539 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str2);
/* 540 */           if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */             
/* 542 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 543 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */               
/* 545 */               if (arrayOfMetaFlag[b1].isSelected()) {
/* 546 */                 stringBuffer.append(arrayOfMetaFlag[b1].getFlagCode());
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } else {
/* 551 */             stringBuffer.append("@@");
/*     */           } 
/* 553 */         } else if (eANMetaAttribute.getAttributeType().equals("F")) {
/* 554 */           Element element = (Element)paramElement.getParentNode();
/*     */ 
/*     */           
/* 557 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str2);
/* 558 */           if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/* 559 */             Vector<String> vector = new Vector(1);
/*     */             
/* 561 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get(); byte b1;
/* 562 */             for (b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */               
/* 564 */               if (arrayOfMetaFlag[b1].isSelected())
/*     */               {
/* 566 */                 if (this.attrSrc == 1) {
/* 567 */                   vector.addElement(arrayOfMetaFlag[b1].getFlagCode());
/*     */                 } else {
/* 569 */                   vector.addElement(arrayOfMetaFlag[b1].toString());
/*     */                 } 
/*     */               }
/*     */             } 
/*     */             
/* 574 */             for (b1 = 0; b1 < vector.size() - 1; b1++) {
/* 575 */               Element element1 = paramDocument.createElement(paramElement.getTagName());
/* 576 */               Element element2 = paramDocument.createElement(this.nodeName);
/* 577 */               element.insertBefore(element1, paramElement);
/* 578 */               element1.appendChild(element2);
/* 579 */               element2.appendChild(paramDocument.createTextNode(vector.elementAt(b1).toString()));
/*     */             } 
/*     */             
/* 582 */             stringBuffer.append(vector.lastElement().toString());
/* 583 */             vector.clear();
/*     */           } else {
/* 585 */             stringBuffer.append("@@");
/*     */           } 
/*     */         } else {
/*     */           
/* 589 */           stringBuffer.append(str1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 594 */     if (documentFragment == null && stringBuffer.length() > 0) {
/* 595 */       text = paramDocument.createTextNode(stringBuffer.toString());
/*     */     }
/* 597 */     return text;
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
/*     */   protected String getCountryCodes(EntityList paramEntityList, Vector<EntityItem> paramVector, String paramString, StringBuffer paramStringBuffer) {
/* 610 */     StringBuffer stringBuffer = new StringBuffer();
/* 611 */     Vector<String> vector = new Vector(1);
/*     */     byte b;
/* 613 */     for (b = 0; b < paramVector.size(); b++) {
/* 614 */       EntityItem entityItem = paramVector.elementAt(b);
/* 615 */       Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, paramString, "GENERALAREA");
/*     */ 
/*     */       
/* 618 */       Vector<EntityItem> vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "GENAREATYPE", "2452");
/* 619 */       paramStringBuffer.append("SAPLElem:getCountryCodes: " + entityItem.getKey() + " has " + vector2.size() + " GENERALAREA.GENAREATYPE = 2452 " + NEWLINE);
/* 620 */       for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 621 */         EntityItem entityItem1 = vector2.elementAt(b1);
/* 622 */         String str = PokUtils.getAttributeValue(entityItem1, "GENAREACODE", ", ", "", false);
/*     */         
/* 624 */         if (!vector.contains(str)) {
/* 625 */           vector.add(str);
/*     */         }
/*     */       } 
/*     */     } 
/* 629 */     Collections.sort(vector);
/*     */     
/* 631 */     for (b = 0; b < vector.size(); b++) {
/* 632 */       String str = vector.elementAt(b).toString();
/* 633 */       stringBuffer.append("/" + str);
/*     */     } 
/* 635 */     if (stringBuffer.length() == 0) {
/* 636 */       stringBuffer.append("/ ");
/*     */     }
/*     */     
/* 639 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\SAPLElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */