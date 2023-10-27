/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.BlobAttribute;
/*     */ import COM.ibm.eannounce.objects.CreateActionItem;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANDataFoundation;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.LongTextAttribute;
/*     */ import COM.ibm.eannounce.objects.MetaBlobAttribute;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.eannounce.objects.MetaLongTextAttribute;
/*     */ import COM.ibm.eannounce.objects.MetaMultiFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.MetaSingleFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.MetaStatusAttribute;
/*     */ import COM.ibm.eannounce.objects.MetaTextAttribute;
/*     */ import COM.ibm.eannounce.objects.MetaXMLAttribute;
/*     */ import COM.ibm.eannounce.objects.MultiFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SingleFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.StatusAttribute;
/*     */ import COM.ibm.eannounce.objects.TextAttribute;
/*     */ import COM.ibm.eannounce.objects.XMLAttribute;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.io.SAXValidator;
/*     */ import org.dom4j.util.XMLErrorHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ABRUtil
/*     */ {
/*     */   private static final int BUFFER_SIZE = 8192;
/*     */   
/*     */   public static EntityItem[] doSearch(Database paramDatabase, Profile paramProfile, String paramString1, String paramString2, boolean paramBoolean, Vector<Object> paramVector1, Vector<Object> paramVector2, StringBuffer paramStringBuffer) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 136 */     if (paramVector1 == null) {
/* 137 */       throw new IllegalArgumentException("AttributeCode vector cannot be null");
/*     */     }
/* 139 */     if (paramVector2 == null) {
/* 140 */       throw new IllegalArgumentException("AttributeValue vector cannot be null");
/*     */     }
/* 142 */     if (paramVector2.size() != paramVector1.size()) {
/* 143 */       throw new IllegalArgumentException("AttributeValue vector must have the same number of elements as the AttributeCode vector");
/*     */     }
/* 145 */     PDGUtility pDGUtility = new PDGUtility();
/* 146 */     StringBuffer stringBuffer = new StringBuffer();
/* 147 */     for (byte b = 0; b < paramVector1.size(); b++) {
/* 148 */       if (stringBuffer.length() > 0) {
/* 149 */         stringBuffer.append(";");
/*     */       }
/* 151 */       Object object1 = paramVector2.elementAt(b);
/* 152 */       Object object2 = paramVector1.elementAt(b);
/* 153 */       if (object1 == null) {
/* 154 */         throw new IllegalArgumentException("AttributeValue vector cannot have a null value. Value[" + b + "] is null");
/*     */       }
/* 156 */       if (object2 == null) {
/* 157 */         throw new IllegalArgumentException("AttributeCode vector cannot have a null Attribute. Attribute[" + b + "] is null");
/*     */       }
/* 159 */       String str1 = paramVector2.elementAt(b).toString();
/* 160 */       String str2 = paramVector1.elementAt(b).toString();
/* 161 */       stringBuffer.append("map_" + str2 + "=" + str1);
/*     */     } 
/*     */     
/* 164 */     append(paramStringBuffer, "ABRUtil.doSearch: Using " + paramString1 + ", useListSrch:" + paramBoolean + " to search for " + paramString2 + " using " + stringBuffer.toString() + "\n");
/*     */     
/* 166 */     EntityItem[] arrayOfEntityItem = null;
/* 167 */     if (!paramBoolean) {
/* 168 */       arrayOfEntityItem = pDGUtility.dynaSearch(paramDatabase, paramProfile, null, paramString1, paramString2, stringBuffer.toString());
/*     */     } else {
/* 170 */       EntityList entityList = pDGUtility.dynaSearchIIForEntityList(paramDatabase, paramProfile, null, paramString1, paramString2, stringBuffer
/* 171 */           .toString());
/*     */       
/* 173 */       EntityGroup entityGroup = entityList.getEntityGroup(paramString2);
/* 174 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/* 175 */         arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*     */       } else {
/* 177 */         append(paramStringBuffer, "ABRUtil.doSearch: No " + paramString2 + " found\n");
/*     */       } 
/*     */     } 
/* 180 */     return arrayOfEntityItem;
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
/*     */   public static EntityItem createEntity(Database paramDatabase, Profile paramProfile, String paramString1, EntityItem paramEntityItem, String paramString2, Vector<String> paramVector, Hashtable paramHashtable, StringBuffer paramStringBuffer) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException {
/* 207 */     append(paramStringBuffer, "ABRUtil.createEntity: Using " + paramString1 + ", child:" + paramString2 + " parent: " + paramEntityItem.getKey() + "\n");
/* 208 */     EntityItem entityItem = null;
/*     */     
/* 210 */     CreateActionItem createActionItem = new CreateActionItem(null, paramDatabase, paramProfile, paramString1);
/* 211 */     EntityList entityList = new EntityList(paramDatabase, paramProfile, createActionItem, new EntityItem[] { paramEntityItem });
/*     */     
/* 213 */     EntityGroup entityGroup = entityList.getEntityGroup(paramString2);
/* 214 */     if (entityGroup != null && entityGroup.getEntityItemCount() == 1) {
/* 215 */       EntityItem entityItem1 = null;
/*     */       
/* 217 */       entityItem = entityGroup.getEntityItem(0);
/*     */       
/* 219 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 220 */         String str = paramVector.elementAt(b);
/*     */         
/* 222 */         EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(str);
/* 223 */         if (eANMetaAttribute == null) {
/* 224 */           append(paramStringBuffer, "ABRUtil.createEntity: MetaAttribute cannot be found " + entityGroup.getEntityType() + "." + str + "\n");
/*     */         } else {
/*     */           
/* 227 */           Object object = paramHashtable.get(str);
/* 228 */           if (object == null) {
/* 229 */             append(paramStringBuffer, "ABRUtil.createEntity: Value not found for " + str + "\n");
/*     */           } else {
/*     */             String str1; Vector vector;
/* 232 */             switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*     */ 
/*     */ 
/*     */               
/*     */               case 'L':
/*     */               case 'T':
/*     */               case 'X':
/* 239 */                 setText(entityItem, str, object.toString(), paramStringBuffer);
/*     */                 break;
/*     */ 
/*     */               
/*     */               case 'S':
/*     */               case 'U':
/* 245 */                 str1 = null;
/* 246 */                 if (object instanceof Vector) {
/* 247 */                   str1 = ((Vector<E>)object).firstElement().toString();
/* 248 */                   append(paramStringBuffer, "ABRUtil.createEntity: Vector passed in for " + eANMetaAttribute
/* 249 */                       .getAttributeType() + " type for " + str + " using " + str1 + " vct was " + object + "\n");
/*     */                 } else {
/*     */                   
/* 252 */                   str1 = object.toString();
/*     */                 } 
/* 254 */                 setUniqueFlag(entityItem, str, str1, paramStringBuffer);
/*     */                 break;
/*     */ 
/*     */               
/*     */               case 'F':
/* 259 */                 str1 = null;
/* 260 */                 if (object instanceof String) {
/* 261 */                   vector = new Vector();
/* 262 */                   vector.addElement(object);
/*     */                 } else {
/* 264 */                   vector = (Vector)object;
/*     */                 } 
/*     */                 
/* 267 */                 setMultiFlag(entityItem, str, vector, paramStringBuffer);
/*     */                 break;
/*     */ 
/*     */               
/*     */               default:
/* 272 */                 append(paramStringBuffer, "ABRUtil.createEntity: MetaAttribute Type=" + eANMetaAttribute.getAttributeType() + " is not supported yet " + entityGroup
/* 273 */                     .getEntityType() + "." + str + "\n");
/*     */                 break;
/*     */             } 
/*     */ 
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/* 281 */       entityItem.commit(paramDatabase, null);
/*     */       
/* 283 */       entityItem1 = (EntityItem)entityItem.getUpLink(0);
/* 284 */       if (entityItem1 != null && !entityItem1.getEntityGroup().isAssoc()) {
/* 285 */         entityItem1.commit(paramDatabase, null);
/*     */       }
/*     */       
/* 288 */       append(paramStringBuffer, "ABRUtil.createEntity() created Entity: " + entityItem.getKey() + ((entityItem1 == null) ? "" : (" and Relator: " + entityItem1
/* 289 */           .getKey())) + "\n");
/*     */     } 
/*     */ 
/*     */     
/* 293 */     return entityItem;
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
/*     */   public static void setUniqueFlag(EntityItem paramEntityItem, String paramString1, String paramString2, StringBuffer paramStringBuffer) throws MiddlewareRequestException, EANBusinessRuleException {
/* 310 */     if (paramString2 != null && paramString2.trim().length() == 0) {
/* 311 */       paramString2 = null;
/*     */     }
/* 313 */     if (paramString2 == null) {
/* 314 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 315 */       if (eANAttribute == null || eANAttribute.toString().trim().length() == 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 319 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)createAttr(paramEntityItem, paramString1, paramStringBuffer);
/* 320 */     if (eANFlagAttribute != null) {
/* 321 */       if (paramString2 == null) {
/* 322 */         eANFlagAttribute.put(null);
/* 323 */         append(paramStringBuffer, "ABRUtil.setUniqueFlag: deactivating " + paramString1 + "\n");
/*     */       } else {
/* 325 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*     */         byte b1;
/* 327 */         for (b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/* 328 */           arrayOfMetaFlag[b1].setSelected(false);
/*     */         }
/*     */         
/* 331 */         b1 = 0;
/* 332 */         for (byte b2 = 0; b2 < arrayOfMetaFlag.length; b2++) {
/* 333 */           if (arrayOfMetaFlag[b2].getFlagCode().equals(paramString2) || arrayOfMetaFlag[b2]
/* 334 */             .toString().equals(paramString2)) {
/*     */             
/* 336 */             arrayOfMetaFlag[b2].setSelected(true);
/* 337 */             eANFlagAttribute.put(arrayOfMetaFlag);
/* 338 */             b1 = 1;
/* 339 */             append(paramStringBuffer, "ABRUtil.setUniqueFlag: setting " + paramString1 + " to " + arrayOfMetaFlag[b2] + "[" + arrayOfMetaFlag[b2]
/* 340 */                 .getFlagCode() + "]\n");
/*     */             break;
/*     */           } 
/*     */         } 
/* 344 */         if (b1 == 0) {
/* 345 */           StringBuffer stringBuffer = new StringBuffer("FlagCode " + paramString2 + " could not be found in " + paramString1 + " MetaFlags [ ");
/*     */           
/* 347 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 348 */             stringBuffer.append(arrayOfMetaFlag[b].getFlagCode() + " ");
/*     */           }
/* 350 */           stringBuffer.append("]");
/* 351 */           throw new MiddlewareRequestException(stringBuffer.toString());
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
/*     */   public static boolean validatexml(Class paramClass, StringBuffer paramStringBuffer, String paramString1, String paramString2) {
/* 363 */     String str = "/COM/ibm/eannounce/abr/sg/adsxmlbh1";
/* 364 */     boolean bool = true;
/*     */     try {
/* 366 */       XMLErrorHandler xMLErrorHandler = new XMLErrorHandler();
/* 367 */       SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
/* 368 */       sAXParserFactory.setValidating(true);
/* 369 */       sAXParserFactory.setNamespaceAware(true);
/* 370 */       SAXParser sAXParser = sAXParserFactory.newSAXParser();
/* 371 */       Document document = DocumentHelper.parseText(paramString2);
/*     */ 
/*     */       
/* 374 */       paramString1 = (paramString1.indexOf("/") > -1) ? (str + paramString1.substring(paramString1.indexOf("/"))) : (str + "/" + paramString1.trim());
/*     */       
/* 376 */       InputStream inputStream = paramClass.getResourceAsStream(paramString1);
/*     */       
/* 378 */       append(paramStringBuffer, "validatexml packagePath=" + str + "\n");
/* 379 */       append(paramStringBuffer, "111 validatexml xsdfile=" + paramString1 + "\n");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 389 */       sAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 396 */       sAXParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", inputStream);
/*     */ 
/*     */ 
/*     */       
/* 400 */       SAXValidator sAXValidator = new SAXValidator(sAXParser.getXMLReader());
/* 401 */       sAXValidator.setErrorHandler((ErrorHandler)xMLErrorHandler);
/* 402 */       sAXValidator.validate(document);
/*     */       
/* 404 */       if (xMLErrorHandler.getErrors().hasContent()) {
/* 405 */         String str1 = xMLErrorHandler.getErrors().asXML();
/*     */         
/* 407 */         append(paramStringBuffer, "the validation for this xml failed because: " + str1 + "\n");
/* 408 */         bool = false;
/*     */       } else {
/* 410 */         append(paramStringBuffer, "the validation for this xml successfully\n");
/*     */       } 
/* 412 */     } catch (Exception exception) {
/*     */       
/* 414 */       append(paramStringBuffer, "Error:the validation for xml failed,because:" + exception.getMessage() + "\n");
/* 415 */       bool = false;
/*     */     } 
/* 417 */     return bool;
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
/*     */   public static void setMultiFlag(EntityItem paramEntityItem, String paramString, Vector paramVector, StringBuffer paramStringBuffer) throws MiddlewareRequestException, EANBusinessRuleException {
/* 436 */     if (paramVector == null || paramVector.size() == 0) {
/* 437 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 438 */       if (eANAttribute == null || eANAttribute.toString().trim().length() == 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 442 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)createAttr(paramEntityItem, paramString, paramStringBuffer);
/* 443 */     if (eANFlagAttribute != null) {
/* 444 */       if (paramVector == null || paramVector.size() == 0) {
/* 445 */         eANFlagAttribute.put(null);
/* 446 */         append(paramStringBuffer, "ABRUtil.setMultiFlag: deactivating " + paramString + "\n");
/*     */       } else {
/* 448 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*     */         byte b1;
/* 450 */         for (b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/* 451 */           arrayOfMetaFlag[b1].setSelected(false);
/*     */         }
/*     */         
/* 454 */         b1 = 0;
/* 455 */         for (byte b2 = 0; b2 < arrayOfMetaFlag.length; b2++) {
/* 456 */           if (paramVector.contains(arrayOfMetaFlag[b2].getFlagCode()) || paramVector
/* 457 */             .contains(arrayOfMetaFlag[b2].toString())) {
/*     */             
/* 459 */             arrayOfMetaFlag[b2].setSelected(true);
/* 460 */             eANFlagAttribute.put(arrayOfMetaFlag);
/* 461 */             b1++;
/* 462 */             append(paramStringBuffer, "ABRUtil.setMultiFlag: setting " + paramString + " to " + arrayOfMetaFlag[b2] + "[" + arrayOfMetaFlag[b2]
/* 463 */                 .getFlagCode() + "]\n");
/*     */           } 
/*     */         } 
/* 466 */         if (b1 != paramVector.size()) {
/* 467 */           StringBuffer stringBuffer = new StringBuffer("One or more FlagCodes in " + paramVector + " could not be found in " + paramString + " MetaFlags [ ");
/*     */           
/* 469 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 470 */             stringBuffer.append(arrayOfMetaFlag[b].getFlagCode() + " ");
/*     */           }
/* 472 */           stringBuffer.append("]");
/* 473 */           throw new MiddlewareRequestException(stringBuffer.toString());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setText(EntityItem paramEntityItem, String paramString1, String paramString2, StringBuffer paramStringBuffer) throws MiddlewareRequestException, EANBusinessRuleException {
/* 492 */     if (paramString2 != null && paramString2.trim().length() == 0) {
/* 493 */       paramString2 = null;
/*     */     }
/* 495 */     if (paramString2 == null) {
/* 496 */       EANAttribute eANAttribute1 = paramEntityItem.getAttribute(paramString1);
/* 497 */       if (eANAttribute1 == null || eANAttribute1.toString().trim().length() == 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 501 */     EANAttribute eANAttribute = createAttr(paramEntityItem, paramString1, paramStringBuffer);
/* 502 */     if (eANAttribute != null) {
/* 503 */       eANAttribute.put(paramString2);
/* 504 */       if (paramString2 != null) {
/* 505 */         append(paramStringBuffer, "ABRUtil.setText: setting " + paramString1 + " to " + paramString2 + "\n");
/*     */       } else {
/* 507 */         append(paramStringBuffer, "ABRUtil.setText: deactivating " + paramString1 + "\n");
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
/*     */   private static EANAttribute createAttr(EntityItem paramEntityItem, String paramString, StringBuffer paramStringBuffer) throws MiddlewareRequestException {
/*     */     MultiFlagAttribute multiFlagAttribute;
/* 523 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 524 */     if (eANAttribute == null)
/* 525 */     { EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 526 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString);
/* 527 */       if (eANMetaAttribute == null)
/* 528 */       { append(paramStringBuffer, "ABRUtil.createAttr: MetaAttribute cannot be found to Create " + paramEntityItem.getEntityType() + "." + paramString + "\n"); }
/*     */       else
/*     */       { TextAttribute textAttribute1; SingleFlagAttribute singleFlagAttribute1; StatusAttribute statusAttribute1; BlobAttribute blobAttribute1; LongTextAttribute longTextAttribute1; TextAttribute textAttribute2; SingleFlagAttribute singleFlagAttribute2; StatusAttribute statusAttribute2; BlobAttribute blobAttribute2; LongTextAttribute longTextAttribute2; XMLAttribute xMLAttribute; MultiFlagAttribute multiFlagAttribute1;
/* 531 */         switch (eANMetaAttribute.getAttributeType().charAt(0))
/*     */         
/*     */         { 
/*     */           case 'T':
/* 535 */             textAttribute2 = new TextAttribute((EANDataFoundation)paramEntityItem, null, (MetaTextAttribute)eANMetaAttribute);
/* 536 */             paramEntityItem.putAttribute((EANAttribute)textAttribute2);
/* 537 */             return (EANAttribute)textAttribute2;
/*     */ 
/*     */ 
/*     */           
/*     */           case 'U':
/* 542 */             singleFlagAttribute2 = new SingleFlagAttribute((EANDataFoundation)paramEntityItem, null, (MetaSingleFlagAttribute)eANMetaAttribute);
/* 543 */             paramEntityItem.putAttribute((EANAttribute)singleFlagAttribute2);
/* 544 */             return (EANAttribute)singleFlagAttribute2;
/*     */ 
/*     */ 
/*     */           
/*     */           case 'S':
/* 549 */             statusAttribute2 = new StatusAttribute((EANDataFoundation)paramEntityItem, null, (MetaStatusAttribute)eANMetaAttribute);
/* 550 */             paramEntityItem.putAttribute((EANAttribute)statusAttribute2);
/* 551 */             return (EANAttribute)statusAttribute2;
/*     */ 
/*     */ 
/*     */           
/*     */           case 'B':
/* 556 */             blobAttribute2 = new BlobAttribute((EANDataFoundation)paramEntityItem, null, (MetaBlobAttribute)eANMetaAttribute);
/* 557 */             paramEntityItem.putAttribute((EANAttribute)blobAttribute2);
/* 558 */             return (EANAttribute)blobAttribute2;
/*     */ 
/*     */ 
/*     */           
/*     */           case 'L':
/* 563 */             longTextAttribute2 = new LongTextAttribute((EANDataFoundation)paramEntityItem, null, (MetaLongTextAttribute)eANMetaAttribute);
/* 564 */             paramEntityItem.putAttribute((EANAttribute)longTextAttribute2);
/* 565 */             return (EANAttribute)longTextAttribute2;
/*     */ 
/*     */ 
/*     */           
/*     */           case 'X':
/* 570 */             xMLAttribute = new XMLAttribute((EANDataFoundation)paramEntityItem, null, (MetaXMLAttribute)eANMetaAttribute);
/* 571 */             paramEntityItem.putAttribute((EANAttribute)xMLAttribute);
/* 572 */             return (EANAttribute)xMLAttribute;
/*     */ 
/*     */ 
/*     */           
/*     */           case 'F':
/* 577 */             multiFlagAttribute1 = new MultiFlagAttribute((EANDataFoundation)paramEntityItem, null, (MetaMultiFlagAttribute)eANMetaAttribute);
/* 578 */             paramEntityItem.putAttribute((EANAttribute)multiFlagAttribute1);
/* 579 */             multiFlagAttribute = multiFlagAttribute1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 591 */             return (EANAttribute)multiFlagAttribute; }  append(paramStringBuffer, "ABRUtil.createAttr: MetaAttribute Type=" + eANMetaAttribute.getAttributeType() + " is not supported yet " + paramEntityItem.getEntityType() + "." + paramString + "\n"); }  }  return (EANAttribute)multiFlagAttribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale getLocale(int paramInt) {
/* 601 */     Locale locale = null;
/* 602 */     switch (paramInt)
/*     */     
/*     */     { case 1:
/* 605 */         locale = Locale.US;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 629 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/*     */   public static String ftpRead(String paramString) throws IOException {
/* 642 */     BufferedInputStream bufferedInputStream = null;
/* 643 */     String str = null;
/*     */     
/* 645 */     if (!paramString.startsWith("ftp://")) {
/* 646 */       paramString = "ftp://" + paramString;
/*     */     }
/*     */     try {
/* 649 */       URLConnection uRLConnection = (new URL(paramString)).openConnection();
/* 650 */       if (uRLConnection != null) {
/* 651 */         uRLConnection.setDoInput(true);
/* 652 */         bufferedInputStream = new BufferedInputStream(uRLConnection.getInputStream(), 8192);
/* 653 */         if (bufferedInputStream != null) {
/* 654 */           byte[] arrayOfByte1 = new byte[0];
/* 655 */           byte[] arrayOfByte2 = new byte[8192];
/*     */           
/*     */           int i;
/* 658 */           while ((i = bufferedInputStream.read(arrayOfByte2)) >= 0) {
/* 659 */             byte[] arrayOfByte = new byte[arrayOfByte1.length + i];
/* 660 */             System.arraycopy(arrayOfByte1, 0, arrayOfByte, 0, arrayOfByte1.length);
/* 661 */             System.arraycopy(arrayOfByte2, 0, arrayOfByte, arrayOfByte1.length, i);
/* 662 */             arrayOfByte1 = arrayOfByte;
/*     */           } 
/* 664 */           str = new String(arrayOfByte1);
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 668 */       if (bufferedInputStream != null) {
/*     */         try {
/* 670 */           bufferedInputStream.close();
/* 671 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/* 674 */         bufferedInputStream = null;
/*     */       } 
/*     */     } 
/*     */     
/* 678 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ftpReadToFile(String paramString1, String paramString2) throws IOException {
/* 689 */     BufferedInputStream bufferedInputStream = null;
/* 690 */     FileOutputStream fileOutputStream = null;
/*     */     
/* 692 */     if (!paramString1.startsWith("ftp://")) {
/* 693 */       paramString1 = "ftp://" + paramString1;
/*     */     }
/*     */     try {
/* 696 */       URLConnection uRLConnection = (new URL(paramString1)).openConnection();
/* 697 */       if (uRLConnection != null) {
/* 698 */         uRLConnection.setDoInput(true);
/* 699 */         bufferedInputStream = new BufferedInputStream(uRLConnection.getInputStream(), 8192);
/* 700 */         if (bufferedInputStream != null) {
/* 701 */           fileOutputStream = new FileOutputStream(paramString2);
/* 702 */           int i = 0;
/* 703 */           byte[] arrayOfByte = new byte[8192];
/* 704 */           while ((i = bufferedInputStream.read(arrayOfByte)) >= 0) {
/* 705 */             fileOutputStream.write(arrayOfByte, 0, i);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 710 */       if (bufferedInputStream != null) {
/*     */         try {
/* 712 */           bufferedInputStream.close();
/* 713 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/* 716 */         bufferedInputStream = null;
/*     */       } 
/* 718 */       if (fileOutputStream != null) {
/*     */         try {
/* 720 */           fileOutputStream.close();
/* 721 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/* 724 */         fileOutputStream = null;
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
/*     */   
/*     */   public static void ftpWrite(String paramString, StringBuffer paramStringBuffer) throws IOException {
/* 750 */     BufferedOutputStream bufferedOutputStream = null;
/* 751 */     if (!paramString.startsWith("ftp://")) {
/* 752 */       paramString = "ftp://" + paramString;
/*     */     }
/*     */     
/*     */     try {
/* 756 */       URLConnection uRLConnection = (new URL(paramString)).openConnection();
/* 757 */       if (uRLConnection != null) {
/* 758 */         uRLConnection.setDoOutput(true);
/*     */ 
/*     */         
/* 761 */         bufferedOutputStream = new BufferedOutputStream(uRLConnection.getOutputStream());
/*     */ 
/*     */         
/* 764 */         bufferedOutputStream.write(paramStringBuffer.toString().getBytes("UTF8"));
/* 765 */         bufferedOutputStream.flush();
/*     */       } 
/*     */     } finally {
/* 768 */       if (bufferedOutputStream != null) {
/*     */         try {
/* 770 */           bufferedOutputStream.close();
/* 771 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/* 774 */         bufferedOutputStream = null;
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
/*     */   public static void ftpWriteFile(String paramString1, String paramString2) throws IOException {
/* 795 */     BufferedOutputStream bufferedOutputStream = null;
/* 796 */     FileInputStream fileInputStream = null;
/* 797 */     if (!paramString1.startsWith("ftp://")) {
/* 798 */       paramString1 = "ftp://" + paramString1;
/*     */     }
/*     */     try {
/* 801 */       URLConnection uRLConnection = (new URL(paramString1)).openConnection();
/* 802 */       if (uRLConnection != null) {
/* 803 */         uRLConnection.setDoOutput(true);
/*     */         
/* 805 */         bufferedOutputStream = new BufferedOutputStream(uRLConnection.getOutputStream());
/*     */ 
/*     */         
/* 808 */         fileInputStream = new FileInputStream(paramString2);
/* 809 */         byte[] arrayOfByte = new byte[8192];
/*     */         while (true) {
/* 811 */           int i = fileInputStream.read(arrayOfByte);
/* 812 */           if (i <= 0) {
/*     */             break;
/*     */           }
/* 815 */           bufferedOutputStream.write(arrayOfByte, 0, i);
/*     */         } 
/* 817 */         bufferedOutputStream.flush();
/*     */       } 
/*     */     } finally {
/* 820 */       if (fileInputStream != null) {
/*     */         try {
/* 822 */           fileInputStream.close();
/* 823 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/* 826 */         fileInputStream = null;
/*     */       } 
/* 828 */       if (bufferedOutputStream != null) {
/*     */         try {
/* 830 */           bufferedOutputStream.close();
/* 831 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/* 834 */         bufferedOutputStream = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String runScript(String paramString) throws IOException, InterruptedException {
/* 845 */     StringBuffer stringBuffer = new StringBuffer();
/* 846 */     Runtime runtime = Runtime.getRuntime();
/* 847 */     Process process = runtime.exec(paramString);
/* 848 */     process.waitFor();
/* 849 */     int i = process.exitValue();
/* 850 */     if (i == 0) {
/* 851 */       stringBuffer.append(displayOutput(process.getInputStream()));
/*     */     } else {
/* 853 */       stringBuffer.append(paramString + " exited with RC = " + i);
/* 854 */       stringBuffer.append(displayOutput(process.getErrorStream()));
/* 855 */       stringBuffer.append(displayOutput(process.getInputStream()));
/*     */     } 
/* 857 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String displayOutput(InputStream paramInputStream) {
/* 862 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/*     */     try {
/* 865 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream)); String str;
/* 866 */       while ((str = bufferedReader.readLine()) != null) {
/* 867 */         stringBuffer.append(str + "\n");
/*     */       }
/* 869 */     } catch (IOException iOException) {
/* 870 */       iOException.printStackTrace();
/*     */     } 
/* 872 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String showMemory() {
/* 877 */     System.gc();
/* 878 */     Runtime runtime = Runtime.getRuntime();
/* 879 */     return "TotMem:" + runtime.totalMemory() + ", FreeMem:" + runtime.freeMemory() + ", UsedMem:" + ((runtime.totalMemory() - runtime.freeMemory()) / 1000L / 1000L) + "M";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void append(StringBuffer paramStringBuffer, String paramString) {
/* 887 */     int i = ABRServerProperties.getABRDebugLevel("ADSABRSTATUS");
/* 888 */     if (i == 4) {
/* 889 */       paramStringBuffer.append(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 897 */     String str1 = "opicmadm:mice8chs@eacm.lexington.ibm.com/bala/checkdel.sql;type=a";
/* 898 */     String str2 = "opicmadm:mice8chs@eacm.lexington.ibm.com/bala/ADSmetaissues.txt;type=i";
/* 899 */     String str3 = "opicmadm:mice8chs@eacm.lexington.ibm.com/bala/chqisoproblems.txt;type=a";
/* 900 */     StringBuffer stringBuffer = new StringBuffer();
/* 901 */     stringBuffer.append("as of 05/07/08\n");
/* 902 */     stringBuffer.append("----------------------------------------------\n");
/* 903 */     stringBuffer.append("IX. Catalog Category Navigation \n");
/* 904 */     stringBuffer.append("    - cant test because CATNAVIMG relator seems to be missing.  The VE is incomplete when forced to run.\n");
/* 905 */     stringBuffer.append("    gbl8104 doesnt find any changes because the meta is incomplete.\n");
/* 906 */     stringBuffer.append("<!-- EntityList for 1980-01-01-00.00.00.000000 extract ADSCATNAV contains the following entities: \n");
/* 907 */     stringBuffer.append("CATNAV : 1 parent items. IDs( 1265233)\n");
/* 908 */     stringBuffer.append("CATNAVIMG : 0 entity items. \n");
/* 909 */     stringBuffer.append(" -->\n");
/*     */     
/*     */     try {
/* 912 */       String str = ftpRead(str1);
/* 913 */       System.err.println("ftpRead returned:" + str + ":");
/* 914 */       ftpReadToFile(str1, "\\dev\\abr12\\source\\ftpd.txt");
/* 915 */       ftpWrite(str2, stringBuffer);
/* 916 */       ftpWriteFile(str3, "\\dev\\abr12\\source\\chqisoproblems.txt");
/* 917 */     } catch (Exception exception) {
/* 918 */       System.err.println(exception.getMessage());
/* 919 */       exception.printStackTrace(System.err);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\ABRUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */