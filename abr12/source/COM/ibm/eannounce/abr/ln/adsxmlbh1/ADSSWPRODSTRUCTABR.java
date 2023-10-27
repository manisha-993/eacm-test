/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSSWPRODSTRUCTABR
/*     */   extends XMLMQRoot
/*     */ {
/* 155 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("TMF_UPDATE"); static {
/* 156 */     XMLMAP.addChild((XMLElem)new XMLVMElem("TMF_UPDATE", "1"));
/*     */     
/* 158 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 159 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 160 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*     */     
/* 162 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 163 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 164 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */     
/* 166 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("MODELENTITYTYPE", "ENTITY2TYPE", "MODEL"));
/* 167 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("MODELENTITYID", "ENTITY2ID", "MODEL"));
/*     */     
/* 169 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "MODEL", "D:MODEL");
/* 170 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 172 */     xMLGroupElem1.addChild((XMLElem)new XMLMachtypeElem("MACHTYPE", "MACHTYPEATR"));
/* 173 */     xMLGroupElem1.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */     
/* 175 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("FEATUREENTITYTYPE", "ENTITY1TYPE", "SWFEATURE"));
/* 176 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("FEATUREENTITYID", "ENTITY1ID", "SWFEATURE"));
/*     */     
/* 178 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "SWFEATURE", "U:SWFEATURE");
/* 179 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 180 */     xMLGroupElem2.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/* 181 */     xMLGroupElem2.addChild(new XMLElem("FCCAT", "SWFCCAT"));
/* 182 */     xMLGroupElem2.addChild(new XMLElem("FCTYPE", "FCTYPE"));
/*     */     
/* 184 */     XMLMAP.addChild(new XMLElem("ORDERCODE"));
/* 185 */     XMLMAP.addChild(new XMLElem("SYSTEMMAX", "SYSTEMMAX"));
/* 186 */     XMLMAP.addChild(new XMLElem("SYSTEMMIN"));
/* 187 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG", "CONFIGURATORFLAG", 2));
/*     */     
/* 189 */     XMLMAP.addChild(new XMLElem("BULKMESINDC"));
/* 190 */     XMLMAP.addChild(new XMLElem("INSTALL"));
/* 191 */     XMLMAP.addChild(new XMLElem("OSLIST"));
/*     */ 
/*     */     
/* 194 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 195 */     XMLMAP.addChild(xMLElem1);
/*     */ 
/*     */     
/* 198 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 199 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 201 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */     
/* 204 */     xMLNLSElem.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     xMLNLSElem.addChild((XMLElem)new XMLMktgInvElem("INVNAME", "INVNAME", "INVNAME", "U:SWFEATURE"));
/*     */ 
/*     */ 
/*     */     
/* 213 */     XMLElem xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 214 */     XMLMAP.addChild(xMLElem2);
/* 215 */     xMLElem2.addChild((XMLElem)new XMLTMFAVAILElem());
/*     */ 
/*     */     
/* 218 */     xMLElem2 = new XMLElem("CATALOGOVERRIDELIST");
/* 219 */     XMLMAP.addChild(xMLElem2);
/* 220 */     xMLElem2.addChild((XMLElem)new XMLCATAElem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 228 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 235 */     return "ADSSWPRODSTRUCT";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName2() {
/* 242 */     return "ADSSWPRODSTRUCT2";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 249 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 257 */     return "TMF_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 266 */     return "$Revision: 1.1 $";
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
/* 278 */     paramADSABRSTATUS.addDebug("Entered ADSLSEOABR.mergeLists");
/*     */ 
/*     */     
/* 281 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 282 */     EntityItem entityItem = paramEntityList1.getParentEntityGroup().getEntityItem(0);
/* 283 */     hashtable.put(entityItem.getKey(), entityItem);
/*     */     
/* 285 */     for (byte b1 = 0; b1 < paramEntityList1.getEntityGroupCount(); b1++) {
/* 286 */       EntityGroup entityGroup1 = paramEntityList1.getEntityGroup(b1);
/* 287 */       EntityGroup entityGroup2 = paramEntityList2.getEntityGroup(entityGroup1.getEntityType());
/* 288 */       if (entityGroup2 != null) {
/* 289 */         for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 290 */           EntityItem entityItem1 = entityGroup2.getEntityItem(b);
/* 291 */           EntityItem entityItem2 = entityGroup1.getEntityItem(entityItem1.getKey());
/* 292 */           if (entityItem2 != null) {
/* 293 */             hashtable.put(entityItem2.getKey(), entityItem2);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 299 */     EntityGroup[] arrayOfEntityGroup = new EntityGroup[paramEntityList2.getEntityGroupCount()]; byte b2;
/* 300 */     for (b2 = 0; b2 < paramEntityList2.getEntityGroupCount(); b2++) {
/* 301 */       EntityGroup entityGroup = paramEntityList2.getEntityGroup(b2);
/* 302 */       arrayOfEntityGroup[b2] = entityGroup;
/*     */     } 
/*     */     
/* 305 */     for (b2 = 0; b2 < arrayOfEntityGroup.length; b2++) {
/* 306 */       EntityGroup entityGroup = arrayOfEntityGroup[b2];
/* 307 */       mergeItems(paramADSABRSTATUS, paramEntityList1, paramEntityList2, entityGroup.getEntityType(), hashtable);
/* 308 */       arrayOfEntityGroup[b2] = null;
/*     */     } 
/*     */ 
/*     */     
/* 312 */     arrayOfEntityGroup = null;
/* 313 */     hashtable.clear();
/* 314 */     hashtable = null;
/* 315 */     paramEntityList2.dereference();
/*     */     
/* 317 */     paramADSABRSTATUS.addDebug("mergeLists:: after merge Extract " + PokUtils.outputList(paramEntityList1));
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
/* 330 */     EntityGroup entityGroup1 = paramEntityList1.getEntityGroup(paramString);
/* 331 */     EntityGroup entityGroup2 = paramEntityList2.getEntityGroup(paramString);
/* 332 */     if (paramString.equals(paramEntityList2.getParentEntityGroup().getEntityType())) {
/* 333 */       entityGroup2 = paramEntityList2.getParentEntityGroup();
/*     */     }
/* 335 */     if (entityGroup1 != null) {
/*     */       
/* 337 */       EntityItem[] arrayOfEntityItem = entityGroup2.getEntityItemsAsArray();
/* 338 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 339 */         EntityItem entityItem1 = arrayOfEntityItem[b];
/* 340 */         EntityItem entityItem2 = entityGroup1.getEntityItem(entityItem1.getKey());
/* 341 */         if (entityItem2 == null) {
/*     */           
/* 343 */           entityGroup1.putEntityItem(entityItem1);
/*     */           
/* 345 */           entityItem1.reassign(entityGroup1);
/*     */           
/* 347 */           entityGroup2.removeEntityItem(entityItem1);
/*     */           
/* 349 */           moveLinks(paramADSABRSTATUS, entityItem2, entityItem1, paramHashtable);
/*     */         } else {
/*     */           
/* 352 */           moveLinks(paramADSABRSTATUS, entityItem2, entityItem1, paramHashtable);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 357 */       paramEntityList2.removeEntityGroup(entityGroup2);
/* 358 */       paramEntityList1.putEntityGroup(entityGroup2);
/* 359 */       for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 360 */         EntityItem entityItem = entityGroup2.getEntityItem(b);
/*     */         
/* 362 */         moveLinks(paramADSABRSTATUS, null, entityItem, paramHashtable);
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
/* 376 */     if (paramEntityItem1 == null) {
/*     */ 
/*     */ 
/*     */       
/* 380 */       if (paramEntityItem2.getUpLinkCount() > 0) {
/* 381 */         EntityItem[] arrayOfEntityItem = new EntityItem[paramEntityItem2.getUpLinkCount()];
/* 382 */         paramEntityItem2.getUpLink().copyInto((Object[])arrayOfEntityItem);
/* 383 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 384 */           EntityItem entityItem1 = arrayOfEntityItem[b];
/* 385 */           EntityItem entityItem2 = (EntityItem)paramHashtable.get(entityItem1.getKey());
/* 386 */           if (entityItem2 != null) {
/*     */             
/* 388 */             paramEntityItem2.removeUpLink((EANEntity)entityItem1);
/* 389 */             paramEntityItem2.putUpLink((EANEntity)entityItem2);
/*     */           } 
/*     */           
/* 392 */           arrayOfEntityItem[b] = null;
/*     */         } 
/* 394 */         arrayOfEntityItem = null;
/*     */       } 
/* 396 */       if (paramEntityItem2.getDownLinkCount() > 0) {
/* 397 */         EntityItem[] arrayOfEntityItem = new EntityItem[paramEntityItem2.getDownLinkCount()];
/* 398 */         paramEntityItem2.getDownLink().copyInto((Object[])arrayOfEntityItem);
/* 399 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 400 */           EntityItem entityItem1 = arrayOfEntityItem[b];
/* 401 */           EntityItem entityItem2 = (EntityItem)paramHashtable.get(entityItem1.getKey());
/* 402 */           if (entityItem2 != null) {
/*     */             
/* 404 */             paramEntityItem2.removeDownLink((EANEntity)entityItem1);
/* 405 */             paramEntityItem2.putDownLink((EANEntity)entityItem2);
/*     */           } 
/*     */           
/* 408 */           arrayOfEntityItem[b] = null;
/*     */         } 
/* 410 */         arrayOfEntityItem = null;
/*     */       } 
/*     */     } else {
/*     */       
/* 414 */       if (paramEntityItem2.getUpLinkCount() > 0) {
/* 415 */         EntityItem[] arrayOfEntityItem = new EntityItem[paramEntityItem2.getUpLinkCount()];
/* 416 */         paramEntityItem2.getUpLink().copyInto((Object[])arrayOfEntityItem);
/* 417 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 418 */           EntityItem entityItem1 = arrayOfEntityItem[b];
/* 419 */           EntityItem entityItem2 = (EntityItem)paramHashtable.get(entityItem1.getKey());
/* 420 */           if (entityItem2 == null) {
/*     */             
/* 422 */             paramEntityItem2.removeUpLink((EANEntity)entityItem1);
/* 423 */             paramEntityItem1.putUpLink((EANEntity)entityItem1);
/*     */           } 
/*     */           
/* 426 */           arrayOfEntityItem[b] = null;
/*     */         } 
/* 428 */         arrayOfEntityItem = null;
/*     */       } 
/* 430 */       if (paramEntityItem2.getDownLinkCount() > 0) {
/* 431 */         EntityItem[] arrayOfEntityItem = new EntityItem[paramEntityItem2.getDownLinkCount()];
/* 432 */         paramEntityItem2.getDownLink().copyInto((Object[])arrayOfEntityItem);
/* 433 */         for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 434 */           EntityItem entityItem1 = arrayOfEntityItem[b];
/* 435 */           EntityItem entityItem2 = (EntityItem)paramHashtable.get(entityItem1.getKey());
/* 436 */           if (entityItem2 == null) {
/*     */             
/* 438 */             paramEntityItem2.removeDownLink((EANEntity)entityItem1);
/* 439 */             paramEntityItem1.putDownLink((EANEntity)entityItem1);
/*     */           } 
/* 441 */           arrayOfEntityItem[b] = null;
/*     */         } 
/* 443 */         arrayOfEntityItem = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSSWPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */