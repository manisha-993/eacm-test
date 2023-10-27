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
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLElem
/*     */ {
/*     */   public static final String STATUS_FINAL = "0020";
/*     */   public static final String BHCOUNTRYLIST = "COUNTRYLIST";
/*     */   public static final int ATTRVAL = 0;
/*     */   public static final int FLAGVAL = 1;
/*     */   public static final int SHORTDESC = 2;
/*     */   public static final String UPDATE_ACTIVITY = "Update";
/*     */   public static final String DELETE_ACTIVITY = "Delete";
/* 124 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/* 125 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*     */   public static final String CHEAT = "@@";
/*     */   public static final String NEWCHEAT = "@amp;";
/* 129 */   protected static final String[] AVAIL_ORDER = new String[] { "146", "143", "149", "AVT220" };
/*     */   
/*     */   protected boolean isReq = false;
/*     */   protected String nodeName;
/* 133 */   protected String attrCode = null;
/* 134 */   protected Vector childVct = new Vector(1);
/* 135 */   protected Hashtable xmlAttrTbl = new Hashtable<>();
/* 136 */   protected int attrSrc = 0;
/*     */   
/* 138 */   private static int TEXT_LIMIT = 254;
/* 139 */   protected static int getTextLimit() { return TEXT_LIMIT; } public static void setTextLimit(int paramInt) {
/* 140 */     TEXT_LIMIT = paramInt;
/* 141 */   } private static int LONGTEXT_LIMIT = 32000;
/* 142 */   protected static int getLongTextLimit() { return LONGTEXT_LIMIT; } public static void setLongTextLimit(int paramInt) {
/* 143 */     LONGTEXT_LIMIT = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem(String paramString) {
/* 152 */     this(paramString, null, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem(String paramString1, String paramString2) {
/* 163 */     this(paramString1, paramString2, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem(String paramString1, String paramString2, int paramInt) {
/* 174 */     this.nodeName = paramString1;
/* 175 */     this.attrCode = paramString2;
/* 176 */     this.attrSrc = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 185 */     StringBuffer stringBuffer = new StringBuffer();
/* 186 */     stringBuffer.append("Node:" + this.nodeName + " attr:" + this.attrCode + " req:" + this.isReq + " childCnt:" + this.childVct.size());
/* 187 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 188 */       stringBuffer.append(NEWLINE + "  " + this.childVct.elementAt(b).toString());
/*     */     }
/* 190 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(XMLElem paramXMLElem) {
/* 198 */     this.childVct.add(paramXMLElem);
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
/* 210 */     StringBuffer stringBuffer = new StringBuffer(paramString);
/* 211 */     int i = 0;
/* 212 */     while (i != -1) {
/*     */       
/* 214 */       i = stringBuffer.toString().indexOf("@@", i);
/* 215 */       if (i != -1) {
/* 216 */         stringBuffer.replace(i, i + "@@".length(), "");
/*     */       }
/*     */     } 
/* 219 */     i = 0;
/* 220 */     while (i != -1) {
/*     */       
/* 222 */       i = stringBuffer.toString().indexOf("@amp;", i);
/* 223 */       if (i != -1) {
/* 224 */         stringBuffer.replace(i, i + "@amp;".length(), "&");
/*     */       }
/*     */     } 
/* 227 */     return stringBuffer.toString();
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
/*     */   public static String removeQuota(String paramString) {
/* 240 */     String str1 = "&lt;";
/* 241 */     String str2 = "&gt;";
/* 242 */     StringBuffer stringBuffer = new StringBuffer(paramString);
/* 243 */     int i = 0;
/* 244 */     while (i != -1) {
/*     */       
/* 246 */       i = stringBuffer.toString().indexOf(str1, i);
/* 247 */       if (i != -1) {
/* 248 */         stringBuffer.replace(i, i + str1.length(), "<");
/*     */       }
/*     */     } 
/* 251 */     i = 0;
/* 252 */     while (i != -1) {
/*     */       
/* 254 */       i = stringBuffer.toString().indexOf(str2, i);
/* 255 */       if (i != -1) {
/* 256 */         stringBuffer.replace(i, i + str2.length(), ">");
/*     */       }
/*     */     } 
/* 259 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addXMLAttribute(String paramString1, String paramString2) {
/* 270 */     this.xmlAttrTbl.put(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRequired() {
/* 277 */     this.isReq = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getEntities(EntityGroup paramEntityGroup) {
/* 286 */     Vector<EntityItem> vector = new Vector();
/* 287 */     if (paramEntityGroup != null) {
/* 288 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 289 */         vector.addElement(paramEntityGroup.getEntityItem(b));
/*     */       }
/*     */     }
/* 292 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getEntities(Vector paramVector) {
/* 302 */     return paramVector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DocumentFragment parseXml(Document paramDocument, String paramString) {
/* 312 */     DocumentFragment documentFragment = null;
/*     */     
/* 314 */     paramString = "<fragment>" + paramString + "</fragment>";
/*     */     
/*     */     try {
/* 317 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 318 */       Document document = documentBuilderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(paramString)));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 323 */       Node node = paramDocument.importNode(document.getDocumentElement(), true);
/*     */ 
/*     */       
/* 326 */       documentFragment = paramDocument.createDocumentFragment();
/*     */ 
/*     */       
/* 329 */       while (node.hasChildNodes()) {
/* 330 */         documentFragment.appendChild(node.removeChild(node.getFirstChild()));
/*     */       }
/* 332 */     } catch (Exception exception) {
/*     */       
/* 334 */       exception.printStackTrace(System.out);
/*     */     } 
/*     */ 
/*     */     
/* 338 */     return documentFragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addXMLAttrs(Element paramElement) {
/* 347 */     for (Enumeration<String> enumeration = this.xmlAttrTbl.keys(); enumeration.hasMoreElements(); ) {
/*     */       
/* 349 */       String str1 = enumeration.nextElement();
/* 350 */       String str2 = (String)this.xmlAttrTbl.get(str1);
/* 351 */       paramElement.setAttribute(str1, str2);
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
/*     */   public void addElements(Database paramDatabase, EntityList paramEntityList, Document paramDocument, Element paramElement, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 376 */     D.ebug(0, "Working on the item:" + this.nodeName);
/* 377 */     Element element = paramDocument.createElement(this.nodeName);
/* 378 */     addXMLAttrs(element);
/*     */     
/* 380 */     if (paramElement == null) {
/* 381 */       paramDocument.appendChild(element);
/*     */     } else {
/* 383 */       paramElement.appendChild(element);
/*     */     } 
/*     */     
/* 386 */     Node node = getContentNode(paramDocument, paramEntityItem, paramElement, paramStringBuffer);
/* 387 */     if (node != null) {
/* 388 */       element.appendChild(node);
/*     */     }
/*     */     
/* 391 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 392 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 393 */       xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
/*     */     } 
/*     */     
/* 396 */     if (!element.hasChildNodes())
/*     */     {
/* 398 */       element.appendChild(paramDocument.createTextNode("@@"));
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
/*     */   public void addElements(Database paramDatabase, Hashtable paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 424 */     D.ebug(0, "Working on the item:" + this.nodeName);
/* 425 */     Element element = paramDocument.createElement(this.nodeName);
/* 426 */     addXMLAttrs(element);
/* 427 */     if (paramElement == null) {
/* 428 */       paramDocument.appendChild(element);
/*     */     } else {
/* 430 */       paramElement.appendChild(element);
/*     */     } 
/*     */     
/* 433 */     Node node = getContentNode(paramDocument, paramDiffEntity, paramElement, paramStringBuffer);
/* 434 */     if (node != null) {
/* 435 */       element.appendChild(node);
/*     */     }
/*     */ 
/*     */     
/* 439 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 440 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 441 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */     } 
/*     */ 
/*     */     
/* 445 */     if (!element.hasChildNodes())
/*     */     {
/* 447 */       element.appendChild(paramDocument.createTextNode("@@"));
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
/*     */   protected boolean hasNodeValueForNLS(EntityItem paramEntityItem, StringBuffer paramStringBuffer) {
/* 459 */     boolean bool = false;
/* 460 */     if (this.attrCode == null) {
/* 461 */       for (byte b = 0; b < this.childVct.size() && !bool; b++) {
/* 462 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 463 */         if (xMLElem.hasNodeValueForNLS(paramEntityItem, paramStringBuffer)) {
/* 464 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 469 */       String[] arrayOfString = PokUtils.convertToArray(this.attrCode);
/* 470 */       Profile profile = paramEntityItem.getProfile();
/* 471 */       NLSItem nLSItem = profile.getReadLanguage();
/*     */       
/* 473 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 474 */         String str = arrayOfString[b];
/* 475 */         if (!str.equals("ENTITYTYPE") && !str.equals("ENTITYID") && !str.equals("NLSID") && 
/* 476 */           !str.equals("ENTITY1ID") && !str.equals("ENTITY2ID")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 482 */           EANAttribute eANAttribute = paramEntityItem.getAttribute(str);
/* 483 */           if (eANAttribute instanceof EANTextAttribute) {
/* 484 */             int i = nLSItem.getNLSID();
/*     */             
/* 486 */             if (((EANTextAttribute)eANAttribute).containsNLS(i) && 
/* 487 */               eANAttribute.toString().length() > 0) {
/* 488 */               bool = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 494 */       ABRUtil.append(paramStringBuffer, "XMLElem.hasNodeValueForNLS node:" + this.nodeName + " " + paramEntityItem.getKey() + " ReadLanguage " + nLSItem + " attr " + this.attrCode + " hasValue:" + bool + NEWLINE);
/*     */     } 
/*     */     
/* 497 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasNodeValueChgForNLS(DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 508 */     boolean bool = false;
/* 509 */     if (this.attrCode == null) {
/* 510 */       for (byte b = 0; b < this.childVct.size() && !bool; b++) {
/* 511 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 512 */         if (xMLElem.hasNodeValueChgForNLS(paramDiffEntity, paramStringBuffer)) {
/* 513 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 518 */       String[] arrayOfString = PokUtils.convertToArray(this.attrCode);
/*     */       
/* 520 */       EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 521 */       EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 522 */       NLSItem nLSItem = null;
/* 523 */       if (!paramDiffEntity.isDeleted()) {
/* 524 */         nLSItem = entityItem1.getProfile().getReadLanguage();
/*     */       } else {
/* 526 */         nLSItem = entityItem2.getProfile().getReadLanguage();
/*     */       } 
/*     */       
/* 529 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 530 */         String str = arrayOfString[b];
/* 531 */         if (!str.equals("ENTITYTYPE") && !str.equals("ENTITYID") && !str.equals("NLSID") && 
/* 532 */           !str.equals("ENTITY1ID") && !str.equals("ENTITY2ID")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 538 */           String str1 = "";
/* 539 */           String str2 = "";
/* 540 */           if (entityItem1 != null) {
/* 541 */             EANAttribute eANAttribute = entityItem1.getAttribute(str);
/* 542 */             if (eANAttribute instanceof EANTextAttribute) {
/* 543 */               int i = nLSItem.getNLSID();
/*     */               
/* 545 */               if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 546 */                 str1 = eANAttribute.toString();
/*     */               }
/*     */             } 
/*     */           } 
/* 550 */           if (entityItem2 != null) {
/* 551 */             EANAttribute eANAttribute = entityItem2.getAttribute(str);
/* 552 */             if (eANAttribute instanceof EANTextAttribute) {
/* 553 */               int i = nLSItem.getNLSID();
/*     */               
/* 555 */               if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 556 */                 str2 = eANAttribute.toString();
/*     */               }
/*     */             } 
/*     */           } 
/* 560 */           ABRUtil.append(paramStringBuffer, "XMLElem.hasNodeValueChgForNLS node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage " + nLSItem + " attr " + str + "\n currVal: " + str1 + "\n prevVal: " + str2 + NEWLINE);
/*     */ 
/*     */           
/* 563 */           if (!str1.equals(str2)) {
/* 564 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 570 */     return bool;
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
/*     */   protected Node getContentNode(Document paramDocument, DiffEntity paramDiffEntity, Element paramElement, StringBuffer paramStringBuffer) throws IOException {
/* 585 */     if (this.attrCode == null || paramDiffEntity == null) {
/* 586 */       return null;
/*     */     }
/*     */     
/* 589 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 590 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 591 */     EntityItem entityItem3 = entityItem1;
/* 592 */     if (paramDiffEntity.isDeleted()) {
/* 593 */       entityItem3 = entityItem2;
/*     */     }
/*     */ 
/*     */     
/* 597 */     return getContentNode(paramDocument, entityItem3, paramElement, paramStringBuffer);
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
/*     */   protected Node getContentNode(Document paramDocument, EntityItem paramEntityItem, Element paramElement, StringBuffer paramStringBuffer) throws IOException {
/* 612 */     if (this.attrCode == null || paramEntityItem == null) {
/* 613 */       return null;
/*     */     }
/*     */     
/* 616 */     if (this.attrCode.equals("ENTITYTYPE"))
/*     */     {
/* 618 */       return paramDocument.createTextNode(paramEntityItem.getEntityType());
/*     */     }
/*     */     
/* 621 */     if (this.attrCode.equals("ENTITYID"))
/*     */     {
/* 623 */       return paramDocument.createTextNode("" + paramEntityItem.getEntityID());
/*     */     }
/* 625 */     if (this.attrCode.equals("ENTITY1ID")) {
/*     */       
/* 627 */       String str = "@@";
/* 628 */       if (paramEntityItem.hasUpLinks()) {
/* 629 */         str = "" + paramEntityItem.getUpLink(0).getEntityID();
/* 630 */         ABRUtil.append(paramStringBuffer, "XMLElem getting " + this.attrCode + " from " + paramEntityItem.getUpLink(0).getKey() + NEWLINE);
/*     */       } 
/* 632 */       return paramDocument.createTextNode(str);
/*     */     } 
/* 634 */     if (this.attrCode.equals("ENTITY2ID")) {
/*     */       
/* 636 */       String str = "@@";
/* 637 */       if (paramEntityItem.hasDownLinks()) {
/* 638 */         str = "" + paramEntityItem.getDownLink(0).getEntityID();
/* 639 */         ABRUtil.append(paramStringBuffer, "XMLElem getting " + this.attrCode + " from " + paramEntityItem.getDownLink(0).getKey() + NEWLINE);
/*     */       } 
/* 641 */       return paramDocument.createTextNode(str);
/*     */     } 
/*     */     
/* 644 */     if (this.attrCode.equals("NLSID"))
/*     */     {
/* 646 */       return paramDocument.createTextNode("" + paramEntityItem.getProfile().getReadLanguage().getNLSID());
/*     */     }
/*     */     
/* 649 */     String[] arrayOfString = PokUtils.convertToArray(this.attrCode);
/*     */     
/* 651 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 652 */     EntityList entityList = entityGroup.getEntityList();
/* 653 */     Text text = null;
/* 654 */     StringBuffer stringBuffer = new StringBuffer();
/* 655 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 656 */       String str1 = "";
/* 657 */       String str2 = arrayOfString[b];
/* 658 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(str2);
/* 659 */       if (eANMetaAttribute == null) {
/* 660 */         if (this.isReq) {
/* 661 */           throw new IOException(this.nodeName + " is required but " + str2 + " is not in " + paramEntityItem
/* 662 */               .getEntityType() + " META data");
/*     */         }
/*     */ 
/*     */         
/* 666 */         str1 = "Error: Attribute " + str2 + " not found in " + paramEntityItem.getEntityType() + " META data.";
/* 667 */         stringBuffer.append(str1);
/*     */       } else {
/* 669 */         Profile profile = entityList.getProfile();
/*     */ 
/*     */         
/* 672 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(str2);
/* 673 */         if (eANAttribute instanceof EANTextAttribute) {
/* 674 */           NLSItem nLSItem = profile.getReadLanguage();
/* 675 */           int i = nLSItem.getNLSID();
/*     */           
/* 677 */           if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 678 */             str1 = eANAttribute.toString().trim();
/*     */           } else {
/*     */             
/* 681 */             str1 = "@@";
/*     */           } 
/*     */         } else {
/* 684 */           str1 = PokUtils.getAttributeValue(paramEntityItem, str2, ", ", "@@", false).trim();
/*     */         } 
/*     */         
/* 687 */         if (this.isReq && str1.equals("@@")) {
/* 688 */           throw new IOException(this.nodeName + " is required but " + str2 + " is not set in " + paramEntityItem
/* 689 */               .getKey());
/*     */         }
/* 691 */         if (eANMetaAttribute.getAttributeType().equals("X")) {
/*     */ 
/*     */           
/* 694 */           if (!str1.equals("@@")) {
/*     */             
/* 696 */             text = paramDocument.createTextNode(str1);
/*     */           } else {
/* 698 */             stringBuffer.append("@@");
/*     */           }
/*     */         
/* 701 */         } else if (eANMetaAttribute.getAttributeType().equals("U") || eANMetaAttribute.getAttributeType().equals("S")) {
/* 702 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str2);
/* 703 */           if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */             
/* 705 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 706 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */               
/* 708 */               if (arrayOfMetaFlag[b1].isSelected()) {
/* 709 */                 if (this.attrSrc == 1) {
/* 710 */                   stringBuffer.append(arrayOfMetaFlag[b1].getFlagCode()); break;
/* 711 */                 }  if (this.attrSrc == 2) {
/* 712 */                   stringBuffer.append(arrayOfMetaFlag[b1].getShortDescription()); break;
/*     */                 } 
/* 714 */                 stringBuffer.append(arrayOfMetaFlag[b1].toString());
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } else {
/* 720 */             stringBuffer.append("@@");
/*     */           } 
/* 722 */         } else if (eANMetaAttribute.getAttributeType().equals("F")) {
/*     */           
/* 724 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str2);
/* 725 */           if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/* 726 */             Vector<String> vector = new Vector(1);
/*     */             
/* 728 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get(); byte b1;
/* 729 */             for (b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */               
/* 731 */               if (arrayOfMetaFlag[b1].isSelected())
/*     */               {
/*     */                 
/* 734 */                 if (this.attrSrc == 1) {
/* 735 */                   vector.addElement(arrayOfMetaFlag[b1].getFlagCode());
/* 736 */                 } else if (this.attrSrc == 2) {
/* 737 */                   vector.addElement(arrayOfMetaFlag[b1].getShortDescription());
/*     */                 } else {
/* 739 */                   vector.addElement(arrayOfMetaFlag[b1].toString());
/*     */                 } 
/*     */               }
/*     */             } 
/*     */             
/* 744 */             for (b1 = 0; b1 < vector.size() - 1; b1++) {
/*     */               
/* 746 */               Element element = paramDocument.createElement(this.nodeName);
/*     */ 
/*     */               
/* 749 */               paramElement.appendChild(element);
/* 750 */               element.appendChild(paramDocument.createTextNode(vector.elementAt(b1).toString()));
/*     */             } 
/*     */             
/* 753 */             stringBuffer.append(vector.lastElement().toString());
/* 754 */             vector.clear();
/*     */           } else {
/* 756 */             stringBuffer.append("@@");
/*     */           } 
/*     */         } else {
/*     */           
/* 760 */           if (eANMetaAttribute.getAttributeType().equals("T")) {
/* 761 */             if (str1.length() > getTextLimit()) {
/* 762 */               str1 = str1.substring(0, getTextLimit());
/* 763 */               ABRUtil.append(paramStringBuffer, "XMLElem.getContentNode node:" + this.nodeName + " " + paramEntityItem.getKey() + " value was truncated for attr " + str2 + NEWLINE);
/*     */             }
/*     */           
/* 766 */           } else if (eANMetaAttribute.getAttributeType().equals("L") && 
/* 767 */             str1.length() > getLongTextLimit()) {
/* 768 */             str1 = str1.substring(0, getLongTextLimit());
/* 769 */             ABRUtil.append(paramStringBuffer, "XMLElem.getContentNode node:" + this.nodeName + " " + paramEntityItem.getKey() + " value was truncated for attr " + str2 + NEWLINE);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 774 */           stringBuffer.append(str1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 779 */     if (text == null && stringBuffer.length() > 0) {
/* 780 */       text = paramDocument.createTextNode(stringBuffer.toString());
/*     */     }
/* 782 */     return text;
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
/*     */   protected boolean hasChanges(Hashtable paramHashtable, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) {
/* 795 */     boolean bool = false;
/*     */     
/* 797 */     if (this.attrCode == null) {
/* 798 */       for (byte b = 0; b < this.childVct.size() && !bool; b++) {
/* 799 */         XMLElem xMLElem = this.childVct.elementAt(b);
/* 800 */         if (xMLElem.hasChanges(paramHashtable, paramDiffEntity, paramStringBuffer)) {
/* 801 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 806 */       String[] arrayOfString = PokUtils.convertToArray(this.attrCode);
/*     */       
/* 808 */       EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 809 */       EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 810 */       NLSItem nLSItem = null;
/* 811 */       if (!paramDiffEntity.isDeleted()) {
/* 812 */         nLSItem = entityItem1.getProfile().getReadLanguage();
/*     */       } else {
/* 814 */         nLSItem = entityItem2.getProfile().getReadLanguage();
/*     */       } 
/*     */       
/* 817 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 818 */         String str = arrayOfString[b];
/* 819 */         if (!str.equals("ENTITYTYPE") && !str.equals("ENTITYID") && !str.equals("NLSID")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 825 */           String str1 = "";
/* 826 */           String str2 = "";
/* 827 */           if (entityItem1 != null) {
/* 828 */             EANAttribute eANAttribute = entityItem1.getAttribute(str);
/* 829 */             if (eANAttribute instanceof EANTextAttribute) {
/* 830 */               int i = nLSItem.getNLSID();
/*     */               
/* 832 */               if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 833 */                 str1 = eANAttribute.toString();
/*     */               }
/*     */             } else {
/* 836 */               str1 = PokUtils.getAttributeValue(entityItem1, str, ", ", "", false);
/*     */             } 
/*     */           } 
/* 839 */           if (entityItem2 != null) {
/* 840 */             EANAttribute eANAttribute = entityItem2.getAttribute(str);
/* 841 */             if (eANAttribute instanceof EANTextAttribute) {
/* 842 */               int i = nLSItem.getNLSID();
/*     */               
/* 844 */               if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 845 */                 str2 = eANAttribute.toString();
/*     */               }
/*     */             } else {
/* 848 */               str2 = PokUtils.getAttributeValue(entityItem2, str, ", ", "", false);
/*     */             } 
/*     */           } 
/* 851 */           ABRUtil.append(paramStringBuffer, "XMLElem.hasChanges node:" + this.nodeName + " " + paramDiffEntity.getKey() + " ReadLanguage " + nLSItem + " attr " + str + "\n currVal: " + str1 + "\n prevVal: " + str2 + NEWLINE);
/*     */ 
/*     */           
/* 854 */           if (!str1.equals(str2)) {
/* 855 */             bool = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 862 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */