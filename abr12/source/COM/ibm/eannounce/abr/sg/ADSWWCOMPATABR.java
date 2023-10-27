/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANEntity;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
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
/*     */ public class ADSWWCOMPATABR
/*     */   extends XMLMQChanges
/*     */ {
/*     */   public void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException {
/*  93 */     String str = "MODELCG";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     Vector<String> vector = getAffectedRoots(paramADSABRSTATUS, getVeName(), paramProfile2, str, paramProfile1
/*  99 */         .getValOn(), paramProfile2.getValOn());
/*     */     
/* 101 */     if (vector.size() == 0) {
/*     */       
/* 103 */       paramADSABRSTATUS.addXMLGenMsg("NO_CHANGES_FND", str);
/*     */     } else {
/*     */       
/* 106 */       EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/* 107 */       for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 108 */         arrayOfEntityItem[b1] = new EntityItem(null, paramProfile2, str, 
/* 109 */             Integer.parseInt(vector.elementAt(b1)));
/*     */       }
/*     */ 
/*     */       
/* 113 */       EntityList entityList = paramADSABRSTATUS.getDB().getEntityList(paramProfile2, new ExtractActionItem(null, paramADSABRSTATUS
/* 114 */             .getDB(), paramProfile2, "dummy"), arrayOfEntityItem);
/*     */       
/* 116 */       EntityGroup entityGroup = entityList.getParentEntityGroup(); byte b2;
/* 117 */       for (b2 = 0; b2 < entityGroup.getEntityItemCount(); b2++) {
/* 118 */         EntityItem entityItem = entityGroup.getEntityItem(b2);
/* 119 */         paramADSABRSTATUS.addDebug("ADSWWCOMPATABR checking root " + entityItem.getKey());
/* 120 */         if (paramADSABRSTATUS.domainNeedsChecks(entityItem)) {
/*     */           
/* 122 */           processThis(paramADSABRSTATUS, paramProfile1, paramProfile2, entityItem, true);
/*     */         } else {
/* 124 */           paramADSABRSTATUS.addXMLGenMsg("DOMAIN_NOT_LISTED", entityItem.getKey());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 129 */       entityList.dereference();
/* 130 */       for (b2 = 0; b2 < arrayOfEntityItem.length; b2++) {
/* 131 */         arrayOfEntityItem[b2] = null;
/*     */       }
/* 133 */       arrayOfEntityItem = null;
/* 134 */       vector.clear();
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
/*     */   protected String generateXML(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException {
/* 154 */     String str = null;
/* 155 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("ROOT");
/* 156 */     DiffEntity diffEntity = vector.firstElement();
/*     */ 
/*     */     
/* 159 */     Vector<M2MInfo> vector1 = getModelsByOS(paramADSABRSTATUS, paramHashtable);
/* 160 */     if (vector1.size() == 0) {
/*     */       
/* 162 */       paramADSABRSTATUS.addXMLGenMsg("NO_OS_MATCH_FND", diffEntity.getKey());
/*     */     } else {
/* 164 */       paramADSABRSTATUS.addDebug("ADSWWCOMPATABR.generateXML found " + vector1.size() + " m2m for " + diffEntity.getKey());
/*     */       
/* 166 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 167 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 168 */       Document document = documentBuilder.newDocument();
/* 169 */       Element element1 = document.createElement("MODELCG_UPDATE");
/*     */       
/* 171 */       document.appendChild(element1);
/*     */       
/* 173 */       Element element2 = document.createElement("DTSOFMSG");
/* 174 */       element2.appendChild(document.createTextNode(diffEntity.getCurrentEntityItem().getProfile().getEndOfDay()));
/* 175 */       element1.appendChild(element2);
/*     */ 
/*     */       
/* 178 */       for (byte b = 0; b < vector1.size(); b++) {
/* 179 */         M2MInfo m2MInfo = vector1.elementAt(b);
/*     */         
/* 181 */         if (!m2MInfo.isDisplayable()) {
/* 182 */           paramADSABRSTATUS.addDebug("No changes found in " + m2MInfo);
/*     */         }
/*     */         else {
/*     */           
/* 186 */           Element element = document.createElement("MODEL2MODEL");
/* 187 */           element1.appendChild(element);
/*     */           
/* 189 */           element2 = document.createElement("PDHDOMAIN");
/* 190 */           element2.appendChild(document.createTextNode(m2MInfo.getPdhdomain()));
/* 191 */           element.appendChild(element2);
/*     */           
/* 193 */           element2 = document.createElement("ACTIVITY");
/* 194 */           element2.appendChild(document.createTextNode(m2MInfo.getActivity()));
/* 195 */           element.appendChild(element2);
/*     */           
/* 197 */           element2 = document.createElement("SYSTEMENTITYTYPE");
/* 198 */           element2.appendChild(document.createTextNode(m2MInfo.getSysType()));
/* 199 */           element.appendChild(element2);
/*     */           
/* 201 */           element2 = document.createElement("SYSTEMENTITYID");
/* 202 */           element2.appendChild(document.createTextNode(m2MInfo.getSysID()));
/* 203 */           element.appendChild(element2);
/*     */           
/* 205 */           element2 = document.createElement("SYSTEMOS");
/* 206 */           element2.appendChild(document.createTextNode(m2MInfo.getOS()));
/* 207 */           element.appendChild(element2);
/*     */           
/* 209 */           element2 = document.createElement("GROUPENTITYTYPE");
/* 210 */           element2.appendChild(document.createTextNode(m2MInfo.getGrpType()));
/* 211 */           element.appendChild(element2);
/*     */           
/* 213 */           element2 = document.createElement("GROUPENTITYID");
/* 214 */           element2.appendChild(document.createTextNode(m2MInfo.getGrpID()));
/* 215 */           element.appendChild(element2);
/*     */           
/* 217 */           element2 = document.createElement("OKTOPUB");
/* 218 */           element2.appendChild(document.createTextNode(m2MInfo.getOktopub()));
/* 219 */           element.appendChild(element2);
/*     */           
/* 221 */           element2 = document.createElement("GROUPOSENTITYTYPE");
/* 222 */           element2.appendChild(document.createTextNode(m2MInfo.getGrpOsType()));
/* 223 */           element.appendChild(element2);
/*     */           
/* 225 */           element2 = document.createElement("GROUPOSENTITYID");
/* 226 */           element2.appendChild(document.createTextNode(m2MInfo.getGrpOsID()));
/* 227 */           element.appendChild(element2);
/*     */           
/* 229 */           element2 = document.createElement("OPTIONOS");
/* 230 */           element2.appendChild(document.createTextNode(m2MInfo.getOptOS()));
/* 231 */           element.appendChild(element2);
/*     */           
/* 233 */           element2 = document.createElement("OPTIONENTITYTYPE");
/* 234 */           element2.appendChild(document.createTextNode(m2MInfo.getOptType()));
/* 235 */           element.appendChild(element2);
/*     */           
/* 237 */           element2 = document.createElement("OPTIONENTITYID");
/* 238 */           element2.appendChild(document.createTextNode(m2MInfo.getOptID()));
/* 239 */           element.appendChild(element2);
/*     */           
/* 241 */           element2 = document.createElement("COMPATIBILITYPUBLISHINGFLAG");
/* 242 */           element2.appendChild(document.createTextNode(m2MInfo.getCompatPubFlag()));
/* 243 */           element.appendChild(element2);
/*     */           
/* 245 */           element2 = document.createElement("RELATIONSHIPTYPE");
/* 246 */           element2.appendChild(document.createTextNode(m2MInfo.getRelType()));
/* 247 */           element.appendChild(element2);
/*     */           
/* 249 */           element2 = document.createElement("PUBLISHFROM");
/* 250 */           element2.appendChild(document.createTextNode(m2MInfo.getPubFrom()));
/* 251 */           element.appendChild(element2);
/*     */           
/* 253 */           element2 = document.createElement("PUBLISHTO");
/* 254 */           element2.appendChild(document.createTextNode(m2MInfo.getPubTo()));
/* 255 */           element.appendChild(element2);
/*     */ 
/*     */           
/* 258 */           m2MInfo.dereference();
/*     */         } 
/*     */       } 
/* 261 */       str = paramADSABRSTATUS.transformXML(this, document);
/*     */ 
/*     */ 
/*     */       
/* 265 */       vector1.clear();
/* 266 */       document = null;
/* 267 */       documentBuilderFactory = null;
/* 268 */       documentBuilder = null;
/* 269 */       element1 = null;
/* 270 */       element2 = null;
/* 271 */       System.gc();
/*     */     } 
/*     */     
/* 274 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector getModelsByOS(ADSABRSTATUS paramADSABRSTATUS, Hashtable paramHashtable) {
/* 282 */     Vector vector1 = new Vector(1);
/* 283 */     Vector<DiffEntity> vector = (Vector)paramHashtable.get("ROOT");
/* 284 */     DiffEntity diffEntity = vector.firstElement();
/* 285 */     EntityItem entityItem1 = diffEntity.getCurrentEntityItem();
/* 286 */     EntityItem entityItem2 = diffEntity.getPriorEntityItem();
/*     */ 
/*     */     
/* 289 */     Vector vector2 = PokUtils.getAllLinkedEntities(entityItem1, "MDLCGMDL", "MODEL");
/* 290 */     Vector vector3 = PokUtils.getAllLinkedEntities(entityItem2, "MDLCGMDL", "MODEL");
/*     */ 
/*     */     
/* 293 */     Vector vector4 = PokUtils.getAllLinkedEntities(entityItem1, "MDLCGMDLCGOS", "MODELCGOS");
/* 294 */     Vector vector5 = PokUtils.getAllLinkedEntities(entityItem2, "MDLCGMDLCGOS", "MODELCGOS");
/*     */     
/* 296 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*     */     
/* 298 */     buildOSTbl(paramADSABRSTATUS, hashtable, paramHashtable, diffEntity, vector2, vector4, true);
/* 299 */     buildOSTbl(paramADSABRSTATUS, hashtable, paramHashtable, diffEntity, vector3, vector5, false);
/*     */     
/* 301 */     vector3.clear();
/* 302 */     vector2.clear();
/* 303 */     vector4.clear();
/* 304 */     vector5.clear();
/* 305 */     if (hashtable.size() > 0) {
/* 306 */       vector1.addAll(hashtable.values());
/* 307 */       hashtable.clear();
/*     */     } 
/* 309 */     return vector1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildOSTbl(ADSABRSTATUS paramADSABRSTATUS, Hashtable<String, M2MInfo> paramHashtable1, Hashtable paramHashtable2, DiffEntity paramDiffEntity, Vector<EntityItem> paramVector1, Vector<EntityItem> paramVector2, boolean paramBoolean) {
/* 315 */     for (byte b = 0; b < paramVector1.size(); b++) {
/* 316 */       EntityItem entityItem = paramVector1.elementAt(b);
/* 317 */       DiffEntity diffEntity = (DiffEntity)paramHashtable2.get(entityItem.getKey());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 322 */       String str1 = paramADSABRSTATUS.getAttributeFlagEnabledValue(entityItem, "COFCAT");
/* 323 */       String str2 = paramADSABRSTATUS.getAttributeFlagEnabledValue(entityItem, "COFGRP");
/* 324 */       paramADSABRSTATUS.addDebug((paramBoolean ? "current " : "previous ") + entityItem.getKey() + " COFCAT: " + str1 + " COFGRP: " + str2);
/*     */       
/* 326 */       if ("150".equals(str2) && !"102".equals(str1)) {
/*     */         
/* 328 */         Vector<String> vector = new Vector(1);
/* 329 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("OSLEVEL");
/* 330 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */           
/* 332 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 333 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*     */             
/* 335 */             if (arrayOfMetaFlag[b1].isSelected()) {
/* 336 */               vector.add(arrayOfMetaFlag[b1].getFlagCode());
/*     */             }
/*     */           } 
/*     */         } 
/* 340 */         paramADSABRSTATUS.addDebug((paramBoolean ? "current " : "previous ") + entityItem.getKey() + " OSLEVEL: " + vector);
/* 341 */         if (vector.size() != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 346 */           for (byte b1 = 0; b1 < paramVector2.size(); b1++) {
/*     */             
/* 348 */             EntityItem entityItem1 = paramVector2.elementAt(b1);
/* 349 */             DiffEntity diffEntity1 = (DiffEntity)paramHashtable2.get(entityItem1.getKey());
/* 350 */             String str = paramADSABRSTATUS.getAttributeFlagEnabledValue(entityItem1, "OS");
/* 351 */             paramADSABRSTATUS.addDebug((paramBoolean ? "current " : "previous ") + entityItem1.getKey() + " OS: " + str);
/*     */ 
/*     */             
/* 354 */             String[] arrayOfString = oslvlMatch(str, vector);
/* 355 */             if (arrayOfString != null) {
/* 356 */               for (byte b2 = 0; b2 < entityItem1.getDownLinkCount(); b2++) {
/* 357 */                 EANEntity eANEntity = entityItem1.getDownLink(b2);
/* 358 */                 DiffEntity diffEntity2 = (DiffEntity)paramHashtable2.get(eANEntity.getKey());
/* 359 */                 for (byte b3 = 0; b3 < eANEntity.getDownLinkCount(); b3++) {
/* 360 */                   EANEntity eANEntity1 = eANEntity.getDownLink(b3);
/* 361 */                   DiffEntity diffEntity3 = (DiffEntity)paramHashtable2.get(eANEntity1.getKey());
/* 362 */                   for (byte b4 = 0; b4 < arrayOfString.length; b4++) {
/* 363 */                     String str3 = generateM2MKey(diffEntity, paramDiffEntity, arrayOfString[b4], diffEntity1, diffEntity2, diffEntity3, str);
/*     */                     
/* 365 */                     if (!paramHashtable1.containsKey(str3)) {
/* 366 */                       M2MInfo m2MInfo = new M2MInfo(diffEntity, paramDiffEntity, arrayOfString[b4], diffEntity1, diffEntity2, diffEntity3, str);
/*     */                       
/* 368 */                       paramHashtable1.put(str3, m2MInfo);
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           } 
/*     */           
/* 376 */           vector.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] oslvlMatch(String paramString, Vector paramVector) {
/* 400 */     String[] arrayOfString = null;
/* 401 */     if (paramVector.contains("10589")) {
/* 402 */       arrayOfString = new String[] { "10589" };
/* 403 */     } else if (paramVector.contains("10582")) {
/* 404 */       arrayOfString = new String[] { "10582" };
/* 405 */     } else if ("10589".equals(paramString)) {
/* 406 */       arrayOfString = new String[paramVector.size()];
/* 407 */       paramVector.copyInto((Object[])arrayOfString);
/* 408 */     } else if (paramString != null && paramVector.contains(paramString)) {
/* 409 */       arrayOfString = new String[] { paramString };
/*     */     } 
/*     */     
/* 412 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 419 */     return "MODELCG";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 424 */     return "ADSWWCOMPAT";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 433 */     return "1.3";
/*     */   }
/*     */ 
/*     */   
/*     */   private static String generateM2MKey(DiffEntity paramDiffEntity1, DiffEntity paramDiffEntity2, String paramString1, DiffEntity paramDiffEntity3, DiffEntity paramDiffEntity4, DiffEntity paramDiffEntity5, String paramString2) {
/* 438 */     return paramDiffEntity1.getKey() + "|" + paramDiffEntity2.getKey() + "|" + paramString1 + paramDiffEntity3
/* 439 */       .getKey() + "|" + paramDiffEntity4.getKey() + "|" + paramDiffEntity5.getKey() + "|" + paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class M2MInfo
/*     */   {
/* 447 */     private DiffEntity sysmdlDiff = null;
/* 448 */     private DiffEntity mdlcgDiff = null;
/* 449 */     private DiffEntity mdlcgosDiff = null;
/* 450 */     private DiffEntity mdlcgosmdlDiff = null;
/* 451 */     private DiffEntity optmdlDiff = null;
/* 452 */     private String activity = "@@";
/*     */     
/* 454 */     private String systemos = null;
/* 455 */     private String optionos = null;
/* 456 */     private String pdhdomain = null;
/* 457 */     private String oktopub = null;
/* 458 */     private String compatpubflag = null;
/* 459 */     private String pubfrom = null;
/* 460 */     private String pubto = null;
/* 461 */     private String reltype = null;
/*     */ 
/*     */ 
/*     */     
/*     */     M2MInfo(DiffEntity param1DiffEntity1, DiffEntity param1DiffEntity2, String param1String1, DiffEntity param1DiffEntity3, DiffEntity param1DiffEntity4, DiffEntity param1DiffEntity5, String param1String2) {
/* 466 */       this.sysmdlDiff = param1DiffEntity1;
/* 467 */       this.mdlcgDiff = param1DiffEntity2;
/* 468 */       this.mdlcgosDiff = param1DiffEntity3;
/* 469 */       this.mdlcgosmdlDiff = param1DiffEntity4;
/* 470 */       this.optmdlDiff = param1DiffEntity5;
/* 471 */       this.systemos = param1String1;
/* 472 */       this.optionos = param1String2;
/*     */       
/* 474 */       if (this.sysmdlDiff.isNew() || this.mdlcgDiff.isNew() || this.mdlcgosDiff.isNew() || this.mdlcgosmdlDiff
/* 475 */         .isNew() || this.optmdlDiff.isNew()) {
/* 476 */         this.activity = "Update";
/*     */       }
/* 478 */       if (this.sysmdlDiff.isDeleted() || this.mdlcgDiff.isDeleted() || this.mdlcgosDiff.isDeleted() || this.mdlcgosmdlDiff
/* 479 */         .isDeleted() || this.optmdlDiff.isDeleted()) {
/* 480 */         this.activity = "Delete";
/*     */       }
/*     */       
/* 483 */       checkOSValue(this.sysmdlDiff, this.systemos, "OSLEVEL");
/* 484 */       checkOSValue(this.mdlcgosDiff, this.optionos, "OS");
/*     */       
/* 486 */       this.pdhdomain = getValue(this.mdlcgDiff, "PDHDOMAIN");
/* 487 */       this.oktopub = getValue(this.mdlcgDiff, "OKTOPUB");
/* 488 */       this.compatpubflag = getValue(this.mdlcgosmdlDiff, "COMPATPUBFLG");
/* 489 */       this.pubfrom = getValue(this.mdlcgosmdlDiff, "PUBFROM");
/* 490 */       this.pubto = getValue(this.mdlcgosmdlDiff, "PUBTO");
/* 491 */       this.reltype = getFlagValue(this.mdlcgosmdlDiff, "RELTYPE");
/*     */     }
/*     */     boolean isDisplayable() {
/* 494 */       return (this.activity != null);
/*     */     }
/*     */     
/*     */     private void checkOSValue(DiffEntity param1DiffEntity, String param1String1, String param1String2) {
/* 498 */       if (this.activity == null) {
/* 499 */         EntityItem entityItem = param1DiffEntity.getCurrentEntityItem();
/* 500 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(param1String2);
/* 501 */         if (eANFlagAttribute == null || !eANFlagAttribute.isSelected(param1String1)) {
/* 502 */           this.activity = "Update";
/*     */         } else {
/* 504 */           entityItem = param1DiffEntity.getPriorEntityItem();
/* 505 */           eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute(param1String2);
/* 506 */           if (eANFlagAttribute == null || !eANFlagAttribute.isSelected(param1String1))
/* 507 */             this.activity = "Update"; 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     String getKey() {
/* 513 */       return ADSWWCOMPATABR.generateM2MKey(this.sysmdlDiff, this.mdlcgDiff, this.systemos, this.mdlcgosDiff, this.mdlcgosmdlDiff, this.optmdlDiff, this.optionos);
/*     */     }
/* 515 */     String getOS() { return this.systemos; }
/* 516 */     String getPdhdomain() { return this.pdhdomain; }
/* 517 */     String getOktopub() { return this.oktopub; }
/* 518 */     String getCompatPubFlag() { return this.compatpubflag; }
/* 519 */     String getPubFrom() { return this.pubfrom; }
/* 520 */     String getPubTo() { return this.pubto; }
/* 521 */     String getRelType() { return this.reltype; }
/* 522 */     String getSysType() { return this.sysmdlDiff.getEntityType(); }
/* 523 */     String getSysID() { return "" + this.sysmdlDiff.getEntityID(); }
/* 524 */     String getGrpType() { return this.mdlcgDiff.getEntityType(); }
/* 525 */     String getGrpID() { return "" + this.mdlcgDiff.getEntityID(); }
/* 526 */     String getGrpOsType() { return this.mdlcgosDiff.getEntityType(); }
/* 527 */     String getGrpOsID() { return "" + this.mdlcgosDiff.getEntityID(); }
/* 528 */     String getOptType() { return this.optmdlDiff.getEntityType(); }
/* 529 */     String getOptID() { return "" + this.optmdlDiff.getEntityID(); }
/* 530 */     String getOptOS() { return this.optionos; } String getActivity() {
/* 531 */       return this.activity;
/*     */     }
/*     */     void dereference() {
/* 534 */       this.sysmdlDiff = null;
/* 535 */       this.mdlcgDiff = null;
/* 536 */       this.systemos = null;
/* 537 */       this.activity = null;
/* 538 */       this.pdhdomain = null;
/* 539 */       this.oktopub = null;
/* 540 */       this.compatpubflag = null;
/* 541 */       this.pubfrom = null;
/* 542 */       this.pubto = null;
/* 543 */       this.reltype = null;
/* 544 */       this.optionos = null;
/*     */     }
/*     */     
/*     */     private String getValue(DiffEntity param1DiffEntity, String param1String) {
/* 548 */       String str1 = "@@";
/* 549 */       String str2 = "@@";
/* 550 */       EntityItem entityItem1 = param1DiffEntity.getCurrentEntityItem();
/* 551 */       EntityItem entityItem2 = param1DiffEntity.getPriorEntityItem();
/* 552 */       if (param1DiffEntity.isDeleted()) {
/* 553 */         str2 = PokUtils.getAttributeValue(entityItem2, param1String, ", ", "@@", false);
/* 554 */       } else if (param1DiffEntity.isNew()) {
/* 555 */         str1 = PokUtils.getAttributeValue(entityItem1, param1String, ", ", "@@", false);
/*     */       } else {
/* 557 */         str2 = PokUtils.getAttributeValue(entityItem2, param1String, ", ", "@@", false);
/* 558 */         str1 = PokUtils.getAttributeValue(entityItem1, param1String, ", ", "@@", false);
/*     */       } 
/*     */       
/* 561 */       if (!str2.equals(str1) && this.activity == null) {
/* 562 */         this.activity = "Update";
/*     */       }
/*     */       
/* 565 */       return str1;
/*     */     }
/*     */     
/*     */     private String getFlagValue(DiffEntity param1DiffEntity, String param1String) {
/* 569 */       String str1 = "@@";
/* 570 */       String str2 = "@@";
/* 571 */       EntityItem entityItem1 = param1DiffEntity.getCurrentEntityItem();
/* 572 */       EntityItem entityItem2 = param1DiffEntity.getPriorEntityItem();
/* 573 */       if (param1DiffEntity.isDeleted()) {
/* 574 */         str2 = PokUtils.getAttributeFlagValue(entityItem2, param1String);
/* 575 */         if (str2 == null) {
/* 576 */           str2 = "@@";
/*     */         }
/* 578 */       } else if (param1DiffEntity.isNew()) {
/* 579 */         str1 = PokUtils.getAttributeFlagValue(entityItem1, param1String);
/* 580 */         if (str1 == null) {
/* 581 */           str1 = "@@";
/*     */         }
/*     */       } else {
/* 584 */         str2 = PokUtils.getAttributeFlagValue(entityItem2, param1String);
/* 585 */         str1 = PokUtils.getAttributeFlagValue(entityItem1, param1String);
/* 586 */         if (str1 == null) {
/* 587 */           str1 = "@@";
/*     */         }
/* 589 */         if (str2 == null) {
/* 590 */           str2 = "@@";
/*     */         }
/*     */       } 
/*     */       
/* 594 */       if (!str2.equals(str1) && this.activity == null) {
/* 595 */         this.activity = "Update";
/*     */       }
/*     */       
/* 598 */       return str1;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 602 */       return getKey() + " activity:" + this.activity;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSWWCOMPATABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */