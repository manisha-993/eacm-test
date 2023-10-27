/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EANTextAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
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
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStatusElem
/*     */   extends XMLElem
/*     */ {
/*     */   private static final String STATUS_RFR = "0040";
/*     */   private static final String m_strEpoch = "1980-01-01-00.00.00.000000";
/*     */   private static final String STATUS_PASSED = "0030";
/*     */   private static final String STATUS_QUEUE = "0020";
/*     */   private static final String STATUS_INPROCESS = "0050";
/*  73 */   private static Vector tarEntity = new Vector(); static {
/*  74 */     tarEntity.add("MODEL");
/*  75 */     tarEntity.add("PRODSTRUCT");
/*  76 */     tarEntity.add("LSEO");
/*  77 */     tarEntity.add("SWPRODSTRUCT");
/*  78 */     tarEntity.add("LSEOBUNDLE");
/*  79 */     tarEntity.add("SVCMOD");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  84 */   private static Hashtable RootEntity_Dates = new Hashtable<>(); static {
/*  85 */     RootEntity_Dates.put("MODEL", new String[] { "ANNDATE", "WTHDRWEFFCTVDATE", "WITHDRAWDATE" });
/*  86 */     RootEntity_Dates.put("LSEO1", new String[] { "LSEOPUBDATEMTRGT" });
/*     */     
/*  88 */     RootEntity_Dates.put("LSEO2", new String[] { "LSEOPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT" });
/*  89 */     RootEntity_Dates.put("PRODSTRUCT", new String[] { "ANNDATE", "GENAVAILDATE", "WTHDRWEFFCTVDATE", "WITHDRAWDATE" });
/*  90 */     RootEntity_Dates.put("SWPRODSTRUCT", new String[] { "GENAVAILDATE" });
/*  91 */     RootEntity_Dates.put("LSEOBUNDLE", new String[] { "BUNDLPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT" });
/*  92 */     RootEntity_Dates.put("SVCMOD", new String[] { "ANNDATE", "WTHDRWEFFCTVDATE", "WITHDRAWDATE" });
/*     */   }
/*     */   
/*     */   public XMLStatusElem(String paramString1, String paramString2, int paramInt) {
/*  96 */     super(paramString1, paramString2, paramInt);
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
/* 120 */     D.ebug(0, "Working on the item:" + this.nodeName);
/* 121 */     Element element = paramDocument.createElement(this.nodeName);
/* 122 */     addXMLAttrs(element);
/*     */     
/* 124 */     if (paramElement == null) {
/* 125 */       paramDocument.appendChild(element);
/*     */     } else {
/* 127 */       paramElement.appendChild(element);
/*     */     } 
/* 129 */     Profile profile = paramEntityItem.getProfile();
/* 130 */     AttributeChangeHistoryGroup attributeChangeHistoryGroup = null;
/* 131 */     attributeChangeHistoryGroup = getADSABRSTATUSHistory(profile, paramDatabase, paramEntityItem, this.nodeName, paramStringBuffer);
/* 132 */     Node node = null;
/* 133 */     if (isExistFinal(attributeChangeHistoryGroup, paramDocument, paramEntityItem, paramElement, paramStringBuffer)) {
/* 134 */       node = paramDocument.createTextNode("0020");
/*     */     } else {
/* 136 */       node = getContentNode(paramDocument, paramEntityItem, paramElement, paramStringBuffer);
/*     */     } 
/*     */     
/* 139 */     if (node != null) {
/* 140 */       element.appendChild(node);
/*     */     }
/*     */     
/* 143 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 144 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 145 */       xMLElem.addElements(paramDatabase, paramEntityList, paramDocument, element, paramEntityItem, paramStringBuffer);
/*     */     } 
/*     */     
/* 148 */     if (!element.hasChildNodes())
/*     */     {
/*     */       
/* 151 */       element.appendChild(paramDocument.createTextNode("@@"));
/*     */     }
/* 153 */     attributeChangeHistoryGroup = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExistFinal(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, Document paramDocument, EntityItem paramEntityItem, Element paramElement, StringBuffer paramStringBuffer) throws MiddlewareRequestException {
/* 159 */     boolean bool = false;
/* 160 */     if (this.attrCode == null || paramEntityItem == null) {
/* 161 */       return false;
/*     */     }
/* 163 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 164 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 165 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 166 */         if (attributeChangeHistoryItem != null) {
/* 167 */           String str = attributeChangeHistoryItem.getFlagCode();
/* 168 */           if (str != null && str.equals("0020")) {
/* 169 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 175 */       ABRUtil.append(paramStringBuffer, "XMLStatusElem.isExistFinal STATUS has no changed history!" + NEWLINE);
/* 176 */       bool = false;
/*     */     } 
/* 178 */     paramAttributeChangeHistoryGroup = null;
/* 179 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(Database paramDatabase, Hashtable<String, String> paramHashtable, Document paramDocument, Element paramElement, DiffEntity paramDiffEntity, StringBuffer paramStringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 187 */     D.ebug(0, "Working on the item:" + this.nodeName);
/* 188 */     Node node = null;
/* 189 */     Element element = paramDocument.createElement(this.nodeName);
/* 190 */     addXMLAttrs(element);
/* 191 */     if (paramElement == null) {
/* 192 */       paramDocument.appendChild(element);
/*     */     } else {
/* 194 */       paramElement.appendChild(element);
/*     */     } 
/* 196 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 197 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 198 */     EntityItem entityItem3 = entityItem1;
/* 199 */     if (paramDiffEntity.isDeleted()) {
/* 200 */       entityItem3 = entityItem2;
/*     */     }
/* 202 */     Profile profile = entityItem3.getProfile();
/* 203 */     AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = null;
/* 204 */     AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = null;
/* 205 */     attributeChangeHistoryGroup1 = getADSABRSTATUSHistory(profile, paramDatabase, entityItem3, "ADSABRSTATUS", paramStringBuffer);
/* 206 */     attributeChangeHistoryGroup2 = getADSABRSTATUSHistory(profile, paramDatabase, entityItem3, this.nodeName, paramStringBuffer);
/* 207 */     boolean bool = "0020".equals(PokUtils.getAttributeFlagValue(entityItem3, "STATUS"));
/*     */     
/* 209 */     if (tarEntity.contains(paramDiffEntity.getEntityType())) {
/* 210 */       ABRUtil.append(paramStringBuffer, "XMLStatusElem running" + paramDiffEntity.getEntityType() + NEWLINE);
/* 211 */       if (isDelta(entityItem2)) {
/* 212 */         if (bool) {
/* 213 */           node = paramDocument.createTextNode("0020");
/* 214 */           paramHashtable.put("_chSTATUS", "0020");
/* 215 */         } else if (isExistFinal(attributeChangeHistoryGroup2, paramDocument, entityItem3, paramElement, paramStringBuffer)) {
/*     */           
/* 217 */           ABRUtil.append(paramStringBuffer, "XMLStatusElem.addElements it's delta and existfinal. " + NEWLINE);
/* 218 */           Vector vector1 = setMeta(entityItem3);
/* 219 */           Vector vector2 = checkChgdAttr(entityItem3, entityItem2, vector1, paramStringBuffer);
/* 220 */           ABRUtil.append(paramStringBuffer, "XMLStatusElem.addElements checkChgdAttr: " + vector2.toString() + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 225 */           if (vector2 != null) {
/* 226 */             if (isDatechange(entityItem3, paramDiffEntity, vector2, paramStringBuffer)) {
/* 227 */               ABRUtil.append(paramStringBuffer, "XMLStatusElem.addElements only avail date related attricodes changed." + NEWLINE);
/* 228 */               node = paramDocument.createTextNode("0020");
/* 229 */               paramHashtable.put("_chSTATUS", "0020");
/*     */             } else {
/* 231 */               ABRUtil.append(paramStringBuffer, "XMLStatusElem.addElements not only avail date attricodes changed. " + NEWLINE);
/* 232 */               node = paramDocument.createTextNode("0040");
/* 233 */               paramHashtable.put("_chSTATUS", "0040");
/*     */             } 
/*     */           }
/*     */         } else {
/* 237 */           node = paramDocument.createTextNode("0040");
/* 238 */           paramHashtable.put("_chSTATUS", "0040");
/*     */         }
/*     */       
/* 241 */       } else if (bool) {
/* 242 */         node = paramDocument.createTextNode("0020");
/* 243 */         paramHashtable.put("_chSTATUS", "0020");
/* 244 */       } else if (isExistFinal(attributeChangeHistoryGroup2, paramDocument, entityItem3, paramElement, paramStringBuffer)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 254 */         ABRUtil.append(paramStringBuffer, "XMLStatusElem.addElements it's full xml and existfinal. " + NEWLINE);
/* 255 */         String str = getT1(attributeChangeHistoryGroup1, attributeChangeHistoryGroup2, paramStringBuffer);
/* 256 */         if (!"1980-01-01-00.00.00.000000".equals(str)) {
/*     */           
/* 258 */           Profile profile1 = profile.getNewInstance(paramDatabase);
/* 259 */           profile1.setValOnEffOn(str, str);
/* 260 */           EntityList entityList = paramDatabase.getEntityList(profile1, new ExtractActionItem(null, paramDatabase, profile1, "dummy"), new EntityItem[] { new EntityItem(null, profile1, entityItem3
/* 261 */                   .getEntityType(), entityItem3.getEntityID()) });
/* 262 */           entityItem2 = entityList.getParentEntityGroup().getEntityItem(0);
/*     */           
/* 264 */           Vector vector1 = setMeta(entityItem3);
/* 265 */           Vector vector2 = checkChgdAttr(entityItem3, entityItem2, vector1, paramStringBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 270 */           if (vector2 != null) {
/* 271 */             if (isDatechange(entityItem3, paramDiffEntity, vector2, paramStringBuffer)) {
/* 272 */               ABRUtil.append(paramStringBuffer, "XMLStatusElem.addElements only avail date related attricodes changed. " + NEWLINE);
/* 273 */               node = paramDocument.createTextNode("0020");
/* 274 */               paramHashtable.put("_chSTATUS", "0020");
/*     */             } else {
/* 276 */               ABRUtil.append(paramStringBuffer, "XMLStatusElem.addElements not only avail date related attricodes changed. " + NEWLINE);
/* 277 */               node = paramDocument.createTextNode("0040");
/* 278 */               paramHashtable.put("_chSTATUS", "0040");
/*     */             } 
/*     */           }
/* 281 */           entityList = null;
/*     */         } else {
/* 283 */           ABRUtil.append(paramStringBuffer, "XMLStatusElem.addElements send the whole data, T1 is 1980. " + NEWLINE);
/* 284 */           node = paramDocument.createTextNode("0040");
/* 285 */           paramHashtable.put("_chSTATUS", "0040");
/*     */         } 
/*     */       } else {
/* 288 */         node = paramDocument.createTextNode("0040");
/* 289 */         paramHashtable.put("_chSTATUS", "0040");
/*     */       } 
/*     */     } else {
/*     */       
/* 293 */       ABRUtil.append(paramStringBuffer, "XMLStatusElem.addElements " + paramDiffEntity.getEntityType() + " is not in the tarEntity list. " + NEWLINE);
/* 294 */       if (isExistFinal(attributeChangeHistoryGroup2, paramDocument, entityItem3, paramElement, paramStringBuffer)) {
/* 295 */         node = paramDocument.createTextNode("0020");
/* 296 */         paramHashtable.put("_chSTATUS", "0020");
/*     */       } else {
/* 298 */         node = getContentNode(paramDocument, entityItem3, paramElement, paramStringBuffer);
/* 299 */         paramHashtable.put("_chSTATUS", "0040");
/*     */       } 
/*     */     } 
/*     */     
/* 303 */     if (node != null) {
/* 304 */       element.appendChild(node);
/*     */     }
/*     */     
/* 307 */     for (byte b = 0; b < this.childVct.size(); b++) {
/* 308 */       XMLElem xMLElem = this.childVct.elementAt(b);
/* 309 */       xMLElem.addElements(paramDatabase, paramHashtable, paramDocument, element, paramDiffEntity, paramStringBuffer);
/*     */     } 
/*     */     
/* 312 */     if (!element.hasChildNodes())
/*     */     {
/*     */       
/* 315 */       element.appendChild(paramDocument.createTextNode("@@"));
/*     */     }
/* 317 */     attributeChangeHistoryGroup1 = null;
/* 318 */     attributeChangeHistoryGroup2 = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributeChangeHistoryGroup getADSABRSTATUSHistory(Profile paramProfile, Database paramDatabase, EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) throws MiddlewareException {
/* 324 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 325 */     if (eANAttribute != null) {
/* 326 */       return new AttributeChangeHistoryGroup(paramDatabase, paramProfile, eANAttribute);
/*     */     }
/* 328 */     ABRUtil.append(paramStringBuffer, paramString + " of " + paramEntityItem.getKey() + "  was null");
/* 329 */     return null;
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
/*     */   private String getT1(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, StringBuffer paramStringBuffer) throws MiddlewareRequestException {
/* 343 */     String str = "1980-01-01-00.00.00.000000";
/* 344 */     boolean bool = false;
/*     */     
/* 346 */     if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 0) {
/* 347 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/* 348 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/* 349 */         if (attributeChangeHistoryItem != null) {
/* 350 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030")) {
/* 351 */             bool = true;
/*     */           }
/* 353 */           if (bool && attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 354 */             String str1 = getTQStatus(paramAttributeChangeHistoryGroup2, attributeChangeHistoryItem.getChangeDate(), paramStringBuffer);
/* 355 */             if (str1.equals("0020")) {
/* 356 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i + 1);
/* 357 */               if (attributeChangeHistoryItem1.getFlagCode().equals("0050")) {
/* 358 */                 str = attributeChangeHistoryItem1.getChangeDate();
/* 359 */                 return str;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 366 */       ABRUtil.append(paramStringBuffer, "getT1 for STATUS has no changed history!");
/*     */     } 
/* 368 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getTQStatus(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString, StringBuffer paramStringBuffer) throws MiddlewareRequestException {
/* 373 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*     */       
/* 375 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 376 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 377 */         if (attributeChangeHistoryItem != null && 
/* 378 */           paramString.compareTo(attributeChangeHistoryItem.getChangeDate()) > 0) {
/* 379 */           return attributeChangeHistoryItem.getFlagCode();
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 384 */       ABRUtil.append(paramStringBuffer, "getTQStatus for STATUS has no changed history!");
/*     */     } 
/* 386 */     return "@@";
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isDelta(EntityItem paramEntityItem) {
/* 391 */     if (paramEntityItem != null && "1980-01-01-00.00.00.000000".equals(paramEntityItem.getProfile().getValOn())) {
/* 392 */       return false;
/*     */     }
/* 394 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isDatechange(EntityItem paramEntityItem, DiffEntity paramDiffEntity, Vector<String> paramVector, StringBuffer paramStringBuffer) {
/* 399 */     Vector<String> vector = new Vector();
/* 400 */     if (!"LSEO".equals(paramDiffEntity.getEntityType())) {
/* 401 */       String[] arrayOfString = (String[])RootEntity_Dates.get(paramDiffEntity.getEntityType());
/* 402 */       if (arrayOfString != null && arrayOfString.length > 0) {
/* 403 */         for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/* 404 */           vector.add(arrayOfString[b1]);
/*     */         }
/*     */       }
/*     */     } else {
/* 408 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(paramEntityItem, "WWSEOLSEO", "WWSEO");
/* 409 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 410 */         EntityItem entityItem = vector1.elementAt(b1);
/* 411 */         String str = PokUtils.getAttributeFlagValue(entityItem, "SPECBID");
/* 412 */         if ("11457".equals(str)) {
/* 413 */           String[] arrayOfString = (String[])RootEntity_Dates.get("LSEO1");
/* 414 */           if (arrayOfString != null && arrayOfString.length > 0) {
/* 415 */             for (byte b2 = 0; b2 < arrayOfString.length; b2++) {
/* 416 */               vector.add(arrayOfString[b2]);
/*     */             }
/*     */           }
/*     */         } else {
/* 420 */           String[] arrayOfString = (String[])RootEntity_Dates.get("LSEO2");
/* 421 */           if (arrayOfString != null && arrayOfString.length > 0) {
/* 422 */             for (byte b2 = 0; b2 < arrayOfString.length; b2++) {
/* 423 */               vector.add(arrayOfString[b2]);
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 429 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 430 */       String str = paramVector.elementAt(b);
/* 431 */       if (!vector.contains(str)) {
/* 432 */         ABRUtil.append(paramStringBuffer, "chgdattr " + str + " is not in availdate.");
/* 433 */         return false;
/*     */       } 
/*     */     } 
/* 436 */     return true;
/*     */   }
/*     */   
/*     */   private Vector setMeta(EntityItem paramEntityItem) {
/* 440 */     Vector<EANMetaAttribute> vector = new Vector();
/* 441 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 442 */     for (byte b = 0; b < entityGroup.getMetaAttributeCount(); b++) {
/* 443 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(b);
/* 444 */       String str = eANMetaAttribute.getAttributeCode();
/* 445 */       if (!eANMetaAttribute.getAttributeType().equals("A") && !str.equals("STATUS") && !str.equals("SYSFEEDRESEND")) {
/* 446 */         vector.add(eANMetaAttribute);
/*     */       }
/*     */     } 
/* 449 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector checkChgdAttr(EntityItem paramEntityItem1, EntityItem paramEntityItem2, Vector<EANMetaAttribute> paramVector, StringBuffer paramStringBuffer) {
/* 457 */     Vector<String> vector = new Vector();
/* 458 */     Profile profile1 = null;
/* 459 */     Profile profile2 = null;
/* 460 */     if (paramEntityItem1 != null) {
/* 461 */       profile1 = paramEntityItem1.getProfile();
/*     */     }
/* 463 */     if (paramEntityItem2 != null) {
/* 464 */       profile2 = paramEntityItem2.getProfile();
/*     */     }
/*     */     
/* 467 */     Profile profile3 = (profile1 == null) ? profile2 : profile1;
/* 468 */     NLSItem nLSItem = profile3.getReadLanguage();
/*     */ 
/*     */     
/* 471 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 472 */       EANMetaAttribute eANMetaAttribute = paramVector.elementAt(b);
/* 473 */       String str1 = eANMetaAttribute.getAttributeCode();
/* 474 */       EANAttribute eANAttribute = null;
/* 475 */       String str2 = "";
/* 476 */       String str3 = "";
/* 477 */       String str4 = "";
/*     */       
/* 479 */       if (paramEntityItem2 != null) {
/* 480 */         str4 = paramEntityItem2.getKey();
/* 481 */         eANAttribute = paramEntityItem2.getAttribute(str1);
/* 482 */         if (eANAttribute != null) {
/* 483 */           str2 = eANAttribute.toString();
/*     */         }
/*     */       } 
/* 486 */       if (paramEntityItem1 != null) {
/* 487 */         str4 = paramEntityItem1.getKey();
/* 488 */         eANAttribute = paramEntityItem1.getAttribute(str1);
/* 489 */         if (eANAttribute != null) {
/* 490 */           str3 = eANAttribute.toString();
/*     */         }
/*     */       } 
/* 493 */       if (!str3.equals(str2)) {
/* 494 */         vector.add(str1);
/* 495 */         ABRUtil.append(paramStringBuffer, "XMLStatusElem " + str4 + " has chgd " + str1 + NEWLINE);
/*     */       }
/* 497 */       else if (eANMetaAttribute instanceof COM.ibm.eannounce.objects.EANMetaTextAttribute) {
/*     */         
/* 499 */         for (byte b1 = 0; b1 < profile3.getReadLanguages().size(); b1++) {
/* 500 */           NLSItem nLSItem1 = profile3.getReadLanguage(b1);
/* 501 */           if (nLSItem1 != nLSItem) {
/*     */ 
/*     */             
/* 504 */             if (profile1 != null) {
/* 505 */               profile1.setReadLanguage(b1);
/*     */             }
/* 507 */             if (profile2 != null) {
/* 508 */               profile2.setReadLanguage(b1);
/*     */             }
/*     */             
/* 511 */             str2 = "";
/* 512 */             str3 = "";
/* 513 */             if (paramEntityItem2 != null) {
/* 514 */               eANAttribute = paramEntityItem2.getAttribute(str1);
/* 515 */               if (eANAttribute instanceof EANTextAttribute) {
/* 516 */                 int i = nLSItem1.getNLSID();
/*     */ 
/*     */                 
/* 519 */                 if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 520 */                   str2 = eANAttribute.toString();
/*     */                 }
/*     */               } 
/*     */             } 
/* 524 */             if (paramEntityItem1 != null) {
/* 525 */               eANAttribute = paramEntityItem1.getAttribute(str1);
/* 526 */               if (eANAttribute instanceof EANTextAttribute) {
/* 527 */                 int i = nLSItem1.getNLSID();
/*     */ 
/*     */                 
/* 530 */                 if (((EANTextAttribute)eANAttribute).containsNLS(i)) {
/* 531 */                   str3 = eANAttribute.toString();
/*     */                 }
/*     */               } 
/*     */             } 
/* 535 */             if (!str3.equals(str2)) {
/* 536 */               vector.add(str1);
/* 537 */               ABRUtil.append(paramStringBuffer, "XMLStatusElem " + str4 + " has chgd " + str1 + NEWLINE);
/*     */             } 
/*     */           } 
/* 540 */         }  if (profile1 != null) {
/* 541 */           profile1.setReadLanguage(nLSItem);
/*     */         }
/* 543 */         if (profile2 != null) {
/* 544 */           profile2.setReadLanguage(nLSItem);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 549 */     return vector;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XMLStatusElem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */