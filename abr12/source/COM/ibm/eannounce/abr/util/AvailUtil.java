/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AvailUtil
/*     */   extends XMLElem
/*     */ {
/*     */   public AvailUtil(String paramString) {
/*  57 */     super(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  62 */   static String compatModel = "V200309";
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean iscompatmodel() {
/*  67 */     String str = ABRServerProperties.getValue("ADSABRSTATUS", "_compatibility", "@@");
/*     */ 
/*     */     
/*  70 */     if (!compatModel.equals(str)) {
/*  71 */       return false;
/*     */     }
/*  73 */     return true;
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
/*     */   public static boolean isExistFinal(Database paramDatabase, DiffEntity paramDiffEntity, String paramString, StringBuffer paramStringBuffer) throws MiddlewareRequestException {
/*  89 */     boolean bool = false;
/*     */ 
/*     */     
/*  92 */     AttributeChangeHistoryGroup attributeChangeHistoryGroup = null;
/*  93 */     EntityItem entityItem = paramDiffEntity.getCurrentEntityItem();
/*  94 */     if (entityItem != null) {
/*  95 */       Profile profile = entityItem.getProfile();
/*  96 */       EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/*  97 */       if (eANAttribute != null) {
/*  98 */         attributeChangeHistoryGroup = new AttributeChangeHistoryGroup(paramDatabase, profile, eANAttribute);
/*     */       } else {
/* 100 */         ABRUtil.append(paramStringBuffer, paramString + " of " + entityItem.getKey() + "  was null");
/* 101 */         return false;
/*     */       } 
/*     */       
/* 104 */       if (attributeChangeHistoryGroup != null && attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 105 */         for (int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 106 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 107 */           if (attributeChangeHistoryItem != null) {
/* 108 */             String str = attributeChangeHistoryItem.getFlagCode();
/* 109 */             if (str != null && str.equals("0020")) {
/* 110 */               bool = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 116 */         ABRUtil.append(paramStringBuffer, "Entity STATUS has no changed history!");
/* 117 */         bool = false;
/*     */       } 
/*     */     } 
/* 120 */     attributeChangeHistoryGroup = null;
/* 121 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityItem getEntityItem(boolean paramBoolean, DiffEntity paramDiffEntity) {
/* 131 */     EntityItem entityItem = null;
/* 132 */     if (paramBoolean) {
/* 133 */       if (paramDiffEntity != null && !paramDiffEntity.isNew()) {
/* 134 */         entityItem = paramDiffEntity.getPriorEntityItem();
/*     */       }
/*     */     }
/* 137 */     else if (paramDiffEntity != null && !paramDiffEntity.isDeleted()) {
/* 138 */       entityItem = paramDiffEntity.getCurrentEntityItem();
/*     */     } 
/*     */     
/* 141 */     return entityItem;
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
/*     */   public static String[] getAvailAnnAttributeDate(boolean paramBoolean, DiffEntity paramDiffEntity, String paramString1, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer) {
/* 155 */     String[] arrayOfString = new String[2];
/* 156 */     EntityItem entityItem = getEntityItem(paramBoolean, paramDiffEntity);
/* 157 */     if (entityItem != null) {
/*     */ 
/*     */       
/* 160 */       Vector<EntityItem> vector = entityItem.getDownLink();
/* 161 */       ABRUtil.append(paramStringBuffer, "AvailUtil.getAvailAnnAttributeDate looking for downlink of AVAIL annVct.size: " + ((vector == null) ? "null" : ("" + vector
/* 162 */           .size())) + "Downlinkcount: " + entityItem
/* 163 */           .getDownLinkCount() + NEWLINE);
/* 164 */       for (byte b = 0; b < vector.size(); b++) {
/* 165 */         EntityItem entityItem1 = vector.elementAt(b);
/* 166 */         if (entityItem1.hasDownLinks() && entityItem1.getEntityType().equals("AVAILANNA")) {
/* 167 */           Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 168 */           EntityItem entityItem2 = vector1.elementAt(0);
/* 169 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("COUNTRYLIST");
/* 170 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 171 */             String str1 = PokUtils.getAttributeFlagValue(entityItem2, "ANNSTATUS");
/* 172 */             String str2 = PokUtils.getAttributeValue(entityItem2, paramString4, ", ", "@@", false);
/* 173 */             ABRUtil.append(paramStringBuffer, "AvailUtil.getAvailAnnAttributeDate annstatus=" + str1 + ";attribute code=" + paramString4 + ";attribute value=" + entityItem2 + NEWLINE);
/* 174 */             if (str1 != null && str1.equals("0020") && "@@".equals(paramString1)) {
/* 175 */               paramString1 = str2;
/* 176 */             } else if (str1 != null && str1.equals("0040") && "@@".equals(paramString2)) {
/* 177 */               paramString2 = str2;
/*     */             } 
/* 179 */             ABRUtil.append(paramStringBuffer, "AvailUtil.getAvailAnnAttributeDate thedate=" + paramString1 + ";rfrthedate=" + paramString2 + NEWLINE);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 184 */     arrayOfString[0] = paramString1;
/* 185 */     arrayOfString[1] = paramString2;
/* 186 */     return arrayOfString;
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
/*     */   public static String[] getAvailAttributeDate(boolean paramBoolean, DiffEntity paramDiffEntity, String paramString1, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer) {
/* 199 */     String[] arrayOfString = new String[2];
/* 200 */     EntityItem entityItem = getEntityItem(paramBoolean, paramDiffEntity);
/* 201 */     if (entityItem != null) {
/* 202 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 203 */       if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 204 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 205 */         String str2 = PokUtils.getAttributeValue(entityItem, paramString4, ", ", "@@", false);
/* 206 */         ABRUtil.append(paramStringBuffer, "AvailUtil.getAvailAttributeDate annstatus=" + str1 + ";attribute code=" + paramString4 + ";attribute value=" + str2 + NEWLINE);
/* 207 */         if (str1 != null && str1.equals("0020") && "@@".equals(paramString1)) {
/* 208 */           paramString1 = str2;
/* 209 */         } else if (str1 != null && str1.equals("0040") && "@@".equals(paramString2)) {
/* 210 */           paramString2 = str2;
/*     */         } 
/* 212 */         ABRUtil.append(paramStringBuffer, "AvailUtil.getAvailAttributeDate thedate=" + paramString1 + ";rfrthedate=" + paramString2 + NEWLINE);
/*     */       } 
/*     */     } 
/*     */     
/* 216 */     arrayOfString[0] = paramString1;
/* 217 */     arrayOfString[1] = paramString2;
/* 218 */     return arrayOfString;
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
/*     */   public static String[] getParentAttributeDate(boolean paramBoolean, DiffEntity paramDiffEntity, String paramString1, String paramString2, String paramString3, StringBuffer paramStringBuffer) {
/* 233 */     String[] arrayOfString = new String[2];
/* 234 */     EntityItem entityItem = getEntityItem(paramBoolean, paramDiffEntity);
/* 235 */     if (entityItem != null) {
/*     */       
/* 237 */       String str1 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 238 */       String str2 = PokUtils.getAttributeValue(entityItem, paramString3, "", "@@", false);
/* 239 */       if (str1 != null && str1.equals("0020") && "@@".equals(paramString1)) {
/* 240 */         paramString1 = str2;
/* 241 */       } else if (str1 != null && str1.equals("0040") && "@@".equals(paramString2)) {
/* 242 */         paramString2 = str2;
/*     */       } 
/* 244 */       ABRUtil.append(paramStringBuffer, "AvailUtil.getParentAttributeDate thedate=" + paramString1 + ";rfrthedate=" + paramString2 + NEWLINE);
/*     */     } 
/* 246 */     arrayOfString[0] = paramString1;
/* 247 */     arrayOfString[1] = paramString2;
/* 248 */     return arrayOfString;
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
/*     */   public static String[] getBHcatlgorAttributeDate(boolean paramBoolean, DiffEntity paramDiffEntity1, DiffEntity paramDiffEntity2, String paramString1, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer) {
/* 262 */     String[] arrayOfString = new String[2];
/* 263 */     EntityItem entityItem1 = getEntityItem(paramBoolean, paramDiffEntity2);
/* 264 */     EntityItem entityItem2 = getEntityItem(paramBoolean, paramDiffEntity1);
/*     */     
/* 266 */     if (entityItem1 != null) {
/* 267 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("COUNTRYLIST");
/* 268 */       if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 269 */         String str1 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 270 */         String str2 = PokUtils.getAttributeValue(entityItem1, paramString4, ", ", "@@", false);
/*     */         
/* 272 */         ABRUtil.append(paramStringBuffer, "AvailUtil.getBHcatlgorAttributeDate annstatus=" + str1 + ";attribute code=" + paramString4 + ";attribute value=" + str2 + NEWLINE);
/* 273 */         if (str1 != null && str1.equals("0020") && "@@".equals(paramString1)) {
/* 274 */           paramString1 = str2;
/* 275 */         } else if (str1 != null && str1.equals("0040") && "@@".equals(paramString2)) {
/* 276 */           paramString2 = str2;
/*     */         } 
/* 278 */         ABRUtil.append(paramStringBuffer, "AvailUtil.getBHcatlgorAttributeDate thedate=" + paramString1 + ";rfrthedate=" + paramString2 + NEWLINE);
/*     */       } 
/*     */     } 
/* 281 */     arrayOfString[0] = paramString1;
/* 282 */     arrayOfString[1] = paramString2;
/* 283 */     return arrayOfString;
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
/*     */   public static String[] getProdstructAttributeDate(boolean paramBoolean, DiffEntity paramDiffEntity1, DiffEntity paramDiffEntity2, Vector[] paramArrayOfVector, String paramString1, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer) {
/* 303 */     String[] arrayOfString = new String[2];
/* 304 */     EntityItem entityItem = getEntityItem(paramBoolean, paramDiffEntity2);
/* 305 */     Vector vector = paramBoolean ? paramArrayOfVector[0] : paramArrayOfVector[1];
/* 306 */     if (applicableCtry(vector, entityItem, paramString3)) {
/* 307 */       EntityItem entityItem1 = getEntityItem(paramBoolean, paramDiffEntity1);
/* 308 */       if (entityItem1 != null && paramDiffEntity1.getEntityType().equals("PRODSTRUCT")) {
/* 309 */         String str1 = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/*     */         
/* 311 */         String str2 = PokUtils.getAttributeValue(entityItem1, paramString4, ", ", "@@", false);
/* 312 */         ABRUtil.append(paramStringBuffer, "AvailUtil.getProdstructAttributeDate annstatus=" + str1 + ";attribute code=" + paramString4 + ";attribute value=" + str2 + NEWLINE);
/*     */         
/* 314 */         if (str1 != null && str1.equals("0020") && "@@".equals(paramString1)) {
/* 315 */           paramString1 = str2;
/* 316 */         } else if (str1 != null && str1.equals("0040") && "@@".equals(paramString2)) {
/* 317 */           paramString2 = str2;
/*     */         } 
/* 319 */         ABRUtil.append(paramStringBuffer, "AvailUtil.getProdstructAttributeDate thedate=" + paramString1 + ";rfrthedate=" + paramString2 + NEWLINE);
/*     */       } 
/*     */     } 
/* 322 */     arrayOfString[0] = paramString1;
/* 323 */     arrayOfString[1] = paramString2;
/* 324 */     return arrayOfString;
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
/*     */   public static String[] getModelFeatureAttributeDate(boolean paramBoolean, DiffEntity paramDiffEntity1, DiffEntity paramDiffEntity2, Vector[] paramArrayOfVector, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, StringBuffer paramStringBuffer) {
/* 341 */     String[] arrayOfString = new String[2];
/*     */     
/* 343 */     EntityItem entityItem = getEntityItem(paramBoolean, paramDiffEntity2);
/* 344 */     Vector vector = paramBoolean ? paramArrayOfVector[0] : paramArrayOfVector[1];
/* 345 */     String str1 = "@@";
/* 346 */     String str2 = "@@";
/*     */     
/* 348 */     if (applicableCtry(vector, entityItem, paramString3)) {
/* 349 */       EntityItem entityItem1 = getEntityItem(paramBoolean, paramDiffEntity1);
/* 350 */       if (entityItem1 != null) {
/*     */         
/* 352 */         if (entityItem1.hasDownLinks()) {
/* 353 */           for (byte b = 0; b < entityItem1.getDownLinkCount(); b++) {
/* 354 */             EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b);
/* 355 */             if (entityItem2.getEntityType().equals("MODEL")) {
/*     */ 
/*     */               
/* 358 */               str1 = PokUtils.getAttributeValue(entityItem2, paramString4, ", ", "@@", false);
/* 359 */               str2 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 360 */               ABRUtil.append(paramStringBuffer, "AvailUtil.getModelFeatureAttributeDate thedate=" + paramString1 + ";rfrthedate=" + paramString2 + NEWLINE);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/* 365 */         if (entityItem1.hasUpLinks()) {
/* 366 */           for (byte b = 0; b < entityItem1.getUpLinkCount(); b++) {
/* 367 */             EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(b);
/* 368 */             if (entityItem2.getEntityType().equals("FEATURE")) {
/*     */ 
/*     */               
/* 371 */               String str = PokUtils.getAttributeValue(entityItem2, paramString5, ", ", "@@", false);
/*     */ 
/*     */               
/* 374 */               if (!"@@".equals(str)) {
/* 375 */                 if (!"@@".equals(str1)) {
/*     */ 
/*     */ 
/*     */                   
/* 379 */                   if (str.compareTo(str1) > 0) {
/* 380 */                     str1 = str;
/* 381 */                     str2 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/*     */                   } 
/* 383 */                   ABRUtil.append(paramStringBuffer, "AvailUtil.getModelFeatureAttributeDate modelDate=" + str1 + ";featureDate=" + str + NEWLINE); break;
/*     */                 } 
/* 385 */                 str1 = str;
/* 386 */                 str2 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/*     */               } 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 394 */         if (str2 != null && str2.equals("0020") && "@@".equals(paramString1)) {
/* 395 */           paramString1 = str1;
/* 396 */         } else if (str2 != null && str2.equals("0040") && "@@".equals(paramString2)) {
/* 397 */           paramString2 = str1;
/*     */         } 
/* 399 */         ABRUtil.append(paramStringBuffer, "AvailUtil.getModelFeatureAttributeDate thedate=" + paramString1 + ";rfrthedate=" + paramString2 + NEWLINE);
/*     */       } 
/*     */     } 
/*     */     
/* 403 */     arrayOfString[0] = paramString1;
/* 404 */     arrayOfString[1] = paramString2;
/* 405 */     return arrayOfString;
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
/*     */   public static String[] getSwprodModelAnnDate(boolean paramBoolean, DiffEntity paramDiffEntity, String paramString1, String paramString2, StringBuffer paramStringBuffer) {
/* 418 */     String[] arrayOfString = new String[2];
/*     */     
/* 420 */     EntityItem entityItem = getEntityItem(paramBoolean, paramDiffEntity);
/* 421 */     if (entityItem != null && paramDiffEntity.getEntityType().equals("SWPRODSTRUCT") && 
/* 422 */       entityItem.hasDownLinks()) {
/* 423 */       for (byte b = 0; b < entityItem.getDownLinkCount(); b++) {
/* 424 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b);
/* 425 */         if (entityItem1.getEntityType().equals("MODEL")) {
/* 426 */           String str1 = PokUtils.getAttributeFlagValue(entityItem1, "STATUS");
/* 427 */           String str2 = PokUtils.getAttributeValue(entityItem1, "ANNDATE", ", ", "@@", false);
/* 428 */           ABRUtil.append(paramStringBuffer, "AvailUtil.getSwprodModelAnnDate modelstatus = " + str1 + ";modelanndate=" + str2 + NEWLINE);
/* 429 */           if (str1 != null && str1.equals("0020") && "@@".equals(paramString1)) {
/* 430 */             paramString1 = str2;
/* 431 */           } else if (str1 != null && str1.equals("0040") && "@@".equals(paramString2)) {
/* 432 */             paramString2 = str2;
/*     */           } 
/* 434 */           ABRUtil.append(paramStringBuffer, "AvailUtil.getSwprodModelAnnDate thedate=" + paramString1 + ";rfrthedate=" + paramString2 + NEWLINE);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 440 */     arrayOfString[0] = paramString1;
/* 441 */     arrayOfString[1] = paramString2;
/* 442 */     return arrayOfString;
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
/*     */   public static String[] getAvailAnnDateByAnntype(boolean paramBoolean, DiffEntity paramDiffEntity, String paramString1, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer) {
/* 460 */     String[] arrayOfString = new String[2];
/* 461 */     EntityItem entityItem = getEntityItem(paramBoolean, paramDiffEntity);
/*     */     
/* 463 */     if (entityItem != null) {
/*     */       
/* 465 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 466 */       if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/*     */         
/* 468 */         Vector<EntityItem> vector = entityItem.getDownLink();
/*     */         
/* 470 */         ABRUtil.append(paramStringBuffer, "AvailUtil.getEOSANNDate looking for downlink of AVAIL annVct.size: " + ((vector == null) ? "null" : ("" + vector
/* 471 */             .size())) + "Downlinkcount: " + entityItem
/* 472 */             .getDownLinkCount() + NEWLINE);
/* 473 */         for (byte b = 0; b < vector.size(); b++) {
/* 474 */           EntityItem entityItem1 = vector.elementAt(b);
/*     */           
/* 476 */           ABRUtil.append(paramStringBuffer, "AvailUtil.getEOSANNDate looking for downlink of AVAIL " + entityItem1.getKey() + "entitytype is: " + entityItem1
/* 477 */               .getEntityType() + NEWLINE);
/*     */           
/* 479 */           if (entityItem1.getEntityType().equals("AVAILANNA") && entityItem1.hasDownLinks()) {
/*     */             
/* 481 */             Vector<EntityItem> vector1 = entityItem1.getDownLink();
/* 482 */             for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 483 */               EntityItem entityItem2 = vector1.elementAt(b1);
/* 484 */               ABRUtil.append(paramStringBuffer, "AvailUtil.getEOSANNDate looking for downlink of AVAILANNA " + entityItem2.getKey() + "entitytype is: " + entityItem2
/* 485 */                   .getEntityType() + "Attriubte ANNTYPE is: " + 
/* 486 */                   PokUtils.getAttributeFlagValue(entityItem2, "ANNTYPE") + NEWLINE);
/* 487 */               EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)entityItem2.getAttribute("ANNTYPE");
/* 488 */               if (eANFlagAttribute1 != null && eANFlagAttribute1.isSelected(paramString4)) {
/* 489 */                 String str1 = PokUtils.getAttributeFlagValue(entityItem2, "ANNSTATUS");
/* 490 */                 String str2 = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 491 */                 if (str1 != null && str1.equals("0020") && "@@".equals(paramString1)) {
/* 492 */                   paramString1 = str2;
/* 493 */                 } else if (str1 != null && str1.equals("0040") && "@@".equals(paramString2)) {
/* 494 */                   paramString2 = str2;
/*     */                 } 
/*     */               } else {
/* 497 */                 ABRUtil.append(paramStringBuffer, "AvailUtil.getEOSANNDate ANNTYPE: " + 
/* 498 */                     PokUtils.getAttributeFlagValue(entityItem2, "ANNTYPE") + "is not equal End Of Life - Discontinuance of service(13)" + NEWLINE);
/*     */               } 
/*     */             } 
/*     */           } else {
/* 502 */             ABRUtil.append(paramStringBuffer, "AvailUtil.getEOSANNDate no downlink of AVAILANNA was found" + NEWLINE);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 509 */     arrayOfString[0] = paramString1;
/* 510 */     arrayOfString[1] = paramString2;
/* 511 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DiffEntity getEntityForAttrs(Hashtable paramHashtable, String paramString1, Vector<DiffEntity> paramVector, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean, StringBuffer paramStringBuffer) {
/* 520 */     DiffEntity diffEntity = null;
/* 521 */     ABRUtil.append(paramStringBuffer, "AvailUtil.getEntityForAttrs  at T1 " + paramBoolean + " looking for " + paramString2 + ":" + paramString3 + " and " + paramString4 + ":" + paramString5 + " in " + paramString1 + " allVct.size:" + ((paramVector == null) ? "null" : ("" + paramVector
/* 522 */         .size())) + NEWLINE);
/* 523 */     if (paramVector == null) {
/* 524 */       return diffEntity;
/*     */     }
/* 526 */     if (paramBoolean) {
/*     */       
/* 528 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 529 */         DiffEntity diffEntity1 = paramVector.elementAt(b);
/* 530 */         EntityItem entityItem = diffEntity1.getPriorEntityItem();
/* 531 */         if (!diffEntity1.isNew()) {
/* 532 */           ABRUtil.append(paramStringBuffer, "AvailUtil.getEntityForAttrs checking[" + b + "]: deleted/update " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 533 */               PokUtils.getAttributeFlagValue(entityItem, paramString2) + " " + paramString4 + ":" + 
/* 534 */               PokUtils.getAttributeFlagValue(entityItem, paramString4) + NEWLINE);
/* 535 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(paramString2);
/* 536 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 537 */             eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(paramString4);
/* 538 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 539 */               diffEntity = diffEntity1;
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 547 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 548 */         DiffEntity diffEntity1 = paramVector.elementAt(b);
/* 549 */         EntityItem entityItem1 = diffEntity1.getCurrentEntityItem();
/* 550 */         EntityItem entityItem2 = diffEntity1.getPriorEntityItem();
/* 551 */         if (diffEntity1.isDeleted()) {
/* 552 */           ABRUtil.append(paramStringBuffer, "AvailUtil.getEntityForAttrs checking[" + b + "]: deleted " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 553 */               PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/* 554 */               PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/* 555 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/* 556 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 557 */             eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/* 558 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 559 */               diffEntity = diffEntity1;
/*     */             }
/*     */           }
/*     */         
/*     */         }
/* 564 */         else if (diffEntity1.isNew()) {
/* 565 */           ABRUtil.append(paramStringBuffer, "AvailUtil.getEntityForAttrs checking[" + b + "]: new " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 566 */               PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/* 567 */               PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/* 568 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/* 569 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 570 */             eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/* 571 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 572 */               diffEntity = diffEntity1;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } else {
/* 578 */           ABRUtil.append(paramStringBuffer, "AvailUtil.getEntityForAttrs checking[" + b + "]: current " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 579 */               PokUtils.getAttributeFlagValue(entityItem1, paramString2) + " " + paramString4 + ":" + 
/* 580 */               PokUtils.getAttributeFlagValue(entityItem1, paramString4) + NEWLINE);
/* 581 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString2);
/* 582 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 583 */             eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute(paramString4);
/* 584 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 585 */               diffEntity = diffEntity1;
/*     */               break;
/*     */             } 
/*     */           } 
/* 589 */           ABRUtil.append(paramStringBuffer, "AvailUtil.getEntityForAttrs checking[" + b + "]: prior " + diffEntity1.getKey() + " " + paramString2 + ":" + 
/* 590 */               PokUtils.getAttributeFlagValue(entityItem2, paramString2) + " " + paramString4 + ":" + 
/* 591 */               PokUtils.getAttributeFlagValue(entityItem2, paramString4) + NEWLINE);
/* 592 */           eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString2);
/* 593 */           if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString3)) {
/* 594 */             eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute(paramString4);
/* 595 */             if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString5)) {
/* 596 */               diffEntity = diffEntity1;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 606 */     return diffEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean applicableCtry(Vector paramVector, EntityItem paramEntityItem, String paramString) {
/* 616 */     boolean bool = false;
/* 617 */     if (paramVector != null && paramVector.contains(paramString)) {
/* 618 */       if (paramEntityItem != null) {
/* 619 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("COUNTRYLIST");
/* 620 */         if (eANFlagAttribute != null && eANFlagAttribute.isSelected(paramString)) {
/* 621 */           bool = true;
/*     */         }
/*     */       } else {
/* 624 */         bool = true;
/*     */       } 
/*     */     }
/* 627 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\AvailUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */