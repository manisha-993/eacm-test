/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCATAElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMachtypeElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMktgInvElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNLSElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLRelatorElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLStatusElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLTMFAVAILElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLVMElem;
/*     */ import COM.ibm.eannounce.objects.EANEntity;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSSWPRODSTRUCTABR
/*     */   extends XMLMQRoot
/*     */ {
/* 152 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("TMF_UPDATE"); static {
/* 153 */     XMLMAP.addChild((XMLElem)new XMLVMElem("TMF_UPDATE", "1"));
/*     */     
/* 155 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 156 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 157 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*     */     
/* 159 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 160 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 161 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */     
/* 163 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("MODELENTITYTYPE", "ENTITY2TYPE", "MODEL"));
/* 164 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("MODELENTITYID", "ENTITY2ID", "MODEL"));
/*     */     
/* 166 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "MODEL", "D:MODEL");
/* 167 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 169 */     xMLGroupElem1.addChild((XMLElem)new XMLMachtypeElem("MACHTYPE", "MACHTYPEATR"));
/* 170 */     xMLGroupElem1.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */     
/* 172 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("FEATUREENTITYTYPE", "ENTITY1TYPE", "SWFEATURE"));
/* 173 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("FEATUREENTITYID", "ENTITY1ID", "SWFEATURE"));
/*     */     
/* 175 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "SWFEATURE", "U:SWFEATURE");
/* 176 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 177 */     xMLGroupElem2.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/* 178 */     xMLGroupElem2.addChild(new XMLElem("FCCAT", "SWFCCAT"));
/* 179 */     xMLGroupElem2.addChild(new XMLElem("FCTYPE", "FCTYPE"));
/*     */     
/* 181 */     XMLMAP.addChild(new XMLElem("ORDERCODE"));
/* 182 */     XMLMAP.addChild(new XMLElem("SYSTEMMAX", "SYSTEMMAX"));
/* 183 */     XMLMAP.addChild(new XMLElem("SYSTEMMIN"));
/* 184 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG", "CONFIGURATORFLAG", 2));
/*     */     
/* 186 */     XMLMAP.addChild(new XMLElem("BULKMESINDC"));
/* 187 */     XMLMAP.addChild(new XMLElem("INSTALL"));
/* 188 */     XMLMAP.addChild(new XMLElem("OSLIST"));
/*     */ 
/*     */     
/* 191 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 192 */     XMLMAP.addChild(xMLElem1);
/*     */ 
/*     */     
/* 195 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 196 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 198 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */     
/* 201 */     xMLNLSElem.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     xMLNLSElem.addChild((XMLElem)new XMLMktgInvElem("INVNAME", "INVNAME", "INVNAME", "U:SWFEATURE"));
/*     */ 
/*     */ 
/*     */     
/* 210 */     XMLElem xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 211 */     XMLMAP.addChild(xMLElem2);
/* 212 */     xMLElem2.addChild((XMLElem)new XMLTMFAVAILElem());
/*     */ 
/*     */     
/* 215 */     xMLElem2 = new XMLElem("CATALOGOVERRIDELIST");
/* 216 */     XMLMAP.addChild(xMLElem2);
/* 217 */     xMLElem2.addChild((XMLElem)new XMLCATAElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 225 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 232 */     return "ADSSWPRODSTRUCT";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName2() {
/* 239 */     return "ADSSWPRODSTRUCT2";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 246 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 254 */     return "TMF_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 263 */     return "$Revision: 1.12 $";
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
/*     */   protected void mergeLists(ADSABRSTATUS paramADSABRSTATUS, EntityList paramEntityList1, EntityList paramEntityList2) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
/* 275 */     paramADSABRSTATUS.addDebug("Entered ADSLSEOABR.mergeLists");
/*     */ 
/*     */     
/* 278 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 279 */     EntityItem entityItem = paramEntityList1.getParentEntityGroup().getEntityItem(0);
/* 280 */     hashtable.put(entityItem.getKey(), entityItem);
/*     */     
/* 282 */     for (byte b1 = 0; b1 < paramEntityList1.getEntityGroupCount(); b1++) {
/* 283 */       EntityGroup entityGroup1 = paramEntityList1.getEntityGroup(b1);
/* 284 */       EntityGroup entityGroup2 = paramEntityList2.getEntityGroup(entityGroup1.getEntityType());
/* 285 */       if (entityGroup2 != null) {
/* 286 */         for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 287 */           EntityItem entityItem1 = entityGroup2.getEntityItem(b);
/* 288 */           EntityItem entityItem2 = entityGroup1.getEntityItem(entityItem1.getKey());
/* 289 */           if (entityItem2 != null) {
/* 290 */             hashtable.put(entityItem2.getKey(), entityItem2);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 296 */     EntityGroup[] arrayOfEntityGroup = new EntityGroup[paramEntityList2.getEntityGroupCount()]; byte b2;
/* 297 */     for (b2 = 0; b2 < paramEntityList2.getEntityGroupCount(); b2++) {
/* 298 */       EntityGroup entityGroup = paramEntityList2.getEntityGroup(b2);
/* 299 */       arrayOfEntityGroup[b2] = entityGroup;
/*     */     } 
/*     */     
/* 302 */     for (b2 = 0; b2 < arrayOfEntityGroup.length; b2++) {
/* 303 */       EntityGroup entityGroup = arrayOfEntityGroup[b2];
/* 304 */       mergeItems(paramADSABRSTATUS, paramEntityList1, paramEntityList2, entityGroup.getEntityType(), hashtable);
/* 305 */       arrayOfEntityGroup[b2] = null;
/*     */     } 
/*     */ 
/*     */     
/* 309 */     arrayOfEntityGroup = null;
/* 310 */     hashtable.clear();
/* 311 */     hashtable = null;
/* 312 */     paramEntityList2.dereference();
/*     */     
/* 314 */     paramADSABRSTATUS.addDebug("mergeLists:: after merge Extract " + PokUtils.outputList(paramEntityList1));
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
/*     */   private void mergeItems(ADSABRSTATUS paramADSABRSTATUS, EntityList paramEntityList1, EntityList paramEntityList2, String paramString, Hashtable paramHashtable) {
/* 327 */     EntityGroup entityGroup1 = paramEntityList1.getEntityGroup(paramString);
/* 328 */     EntityGroup entityGroup2 = paramEntityList2.getEntityGroup(paramString);
/* 329 */     if (paramString.equals(paramEntityList2.getParentEntityGroup().getEntityType())) {
/* 330 */       entityGroup2 = paramEntityList2.getParentEntityGroup();
/*     */     }
/* 332 */     if (entityGroup1 != null) {
/*     */       
/* 334 */       EntityItem[] arrayOfEntityItem = entityGroup2.getEntityItemsAsArray();
/* 335 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 336 */         EntityItem entityItem1 = arrayOfEntityItem[b];
/* 337 */         EntityItem entityItem2 = entityGroup1.getEntityItem(entityItem1.getKey());
/* 338 */         if (entityItem2 == null) {
/*     */           
/* 340 */           entityGroup1.putEntityItem(entityItem1);
/*     */           
/* 342 */           entityItem1.reassign(entityGroup1);
/*     */           
/* 344 */           entityGroup2.removeEntityItem(entityItem1);
/*     */           
/* 346 */           moveLinks(paramADSABRSTATUS, entityItem2, entityItem1, paramHashtable);
/*     */         } else {
/*     */           
/* 349 */           moveLinks(paramADSABRSTATUS, entityItem2, entityItem1, paramHashtable);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 354 */       paramEntityList2.removeEntityGroup(entityGroup2);
/* 355 */       paramEntityList1.putEntityGroup(entityGroup2);
/* 356 */       for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 357 */         EntityItem entityItem = entityGroup2.getEntityItem(b);
/*     */         
/* 359 */         moveLinks(paramADSABRSTATUS, null, entityItem, paramHashtable);
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
/*     */   private void moveLinks(ADSABRSTATUS paramADSABRSTATUS, EntityItem paramEntityItem1, EntityItem paramEntityItem2, Hashtable paramHashtable) {
/* 373 */     if (paramEntityItem1 == null) {
/*     */ 
/*     */ 
/*     */       
/* 377 */       if (paramEntityItem2.getUpLinkCount() > 0) {
/* 378 */         EntityItem[] arrayOfEntityItem = new EntityItem[paramEntityItem2.getUpLinkCount()];
/* 379 */         paramEntityItem2.getUpLink().copyInto((Object[])arrayOfEntityItem);
/* 380 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 381 */           EntityItem entityItem1 = arrayOfEntityItem[b];
/* 382 */           EntityItem entityItem2 = (EntityItem)paramHashtable.get(entityItem1.getKey());
/* 383 */           if (entityItem2 != null) {
/*     */             
/* 385 */             paramEntityItem2.removeUpLink((EANEntity)entityItem1);
/* 386 */             paramEntityItem2.putUpLink((EANEntity)entityItem2);
/*     */           } 
/*     */           
/* 389 */           arrayOfEntityItem[b] = null;
/*     */         } 
/* 391 */         arrayOfEntityItem = null;
/*     */       } 
/* 393 */       if (paramEntityItem2.getDownLinkCount() > 0) {
/* 394 */         EntityItem[] arrayOfEntityItem = new EntityItem[paramEntityItem2.getDownLinkCount()];
/* 395 */         paramEntityItem2.getDownLink().copyInto((Object[])arrayOfEntityItem);
/* 396 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 397 */           EntityItem entityItem1 = arrayOfEntityItem[b];
/* 398 */           EntityItem entityItem2 = (EntityItem)paramHashtable.get(entityItem1.getKey());
/* 399 */           if (entityItem2 != null) {
/*     */             
/* 401 */             paramEntityItem2.removeDownLink((EANEntity)entityItem1);
/* 402 */             paramEntityItem2.putDownLink((EANEntity)entityItem2);
/*     */           } 
/*     */           
/* 405 */           arrayOfEntityItem[b] = null;
/*     */         } 
/* 407 */         arrayOfEntityItem = null;
/*     */       } 
/*     */     } else {
/*     */       
/* 411 */       if (paramEntityItem2.getUpLinkCount() > 0) {
/* 412 */         EntityItem[] arrayOfEntityItem = new EntityItem[paramEntityItem2.getUpLinkCount()];
/* 413 */         paramEntityItem2.getUpLink().copyInto((Object[])arrayOfEntityItem);
/* 414 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 415 */           EntityItem entityItem1 = arrayOfEntityItem[b];
/* 416 */           EntityItem entityItem2 = (EntityItem)paramHashtable.get(entityItem1.getKey());
/* 417 */           if (entityItem2 == null) {
/*     */             
/* 419 */             paramEntityItem2.removeUpLink((EANEntity)entityItem1);
/* 420 */             paramEntityItem1.putUpLink((EANEntity)entityItem1);
/*     */           } 
/*     */           
/* 423 */           arrayOfEntityItem[b] = null;
/*     */         } 
/* 425 */         arrayOfEntityItem = null;
/*     */       } 
/* 427 */       if (paramEntityItem2.getDownLinkCount() > 0) {
/* 428 */         EntityItem[] arrayOfEntityItem = new EntityItem[paramEntityItem2.getDownLinkCount()];
/* 429 */         paramEntityItem2.getDownLink().copyInto((Object[])arrayOfEntityItem);
/* 430 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 431 */           EntityItem entityItem1 = arrayOfEntityItem[b];
/* 432 */           EntityItem entityItem2 = (EntityItem)paramHashtable.get(entityItem1.getKey());
/* 433 */           if (entityItem2 == null) {
/*     */             
/* 435 */             paramEntityItem2.removeDownLink((EANEntity)entityItem1);
/* 436 */             paramEntityItem1.putDownLink((EANEntity)entityItem1);
/*     */           } 
/* 438 */           arrayOfEntityItem[b] = null;
/*     */         } 
/* 440 */         arrayOfEntityItem = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSSWPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */