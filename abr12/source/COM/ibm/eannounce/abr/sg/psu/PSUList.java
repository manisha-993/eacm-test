/*     */ package COM.ibm.eannounce.abr.sg.psu;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.WorkflowException;
/*     */ import COM.ibm.opicmpdh.middleware.LockException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
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
/*     */ class PSUList
/*     */ {
/*     */   private static final String ID_PAD = "000000000000";
/*     */   private PSUABRSTATUS abr;
/*     */   private EntityItem rootEntity;
/*  40 */   private Object[] args = (Object[])new String[3];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PSUList(PSUABRSTATUS paramPSUABRSTATUS, EntityItem paramEntityItem) {
/*  47 */     this.abr = paramPSUABRSTATUS;
/*  48 */     this.rootEntity = paramEntityItem;
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
/*     */   void execute(EntityGroup paramEntityGroup) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException {
/*  68 */     EntityItem[] arrayOfEntityItem = paramEntityGroup.getEntityItemsAsArray();
/*     */     
/*  70 */     if (arrayOfEntityItem.length == 0) {
/*     */       
/*  72 */       this.args[0] = this.abr.getLD_Value(this.rootEntity, "PSUCRITERIA");
/*  73 */       this.args[1] = paramEntityGroup.getLongDescription();
/*  74 */       this.abr.addError("LIST_NO_CHILDREN_ERR", this.args);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  79 */     Arrays.sort(arrayOfEntityItem, new Comparator<EntityItem>() {
/*     */           public int compare(Object param1Object1, Object param1Object2) {
/*  81 */             EntityItem entityItem1 = (EntityItem)param1Object1;
/*  82 */             EntityItem entityItem2 = (EntityItem)param1Object2;
/*  83 */             String str1 = PSUList.this.getSortKey(entityItem1);
/*  84 */             String str2 = PSUList.this.getSortKey(entityItem2);
/*  85 */             return str1.compareToIgnoreCase(str2);
/*     */           }
/*     */         });
/*     */     
/*  89 */     processList(arrayOfEntityItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getSortKey(EntityItem paramEntityItem) {
/* 100 */     StringBuffer stringBuffer = new StringBuffer(PokUtils.getAttributeValue(paramEntityItem, "PSUENTITYTYPE", "", "", false));
/*     */     
/* 102 */     String str = PokUtils.getAttributeValue(paramEntityItem, "PSUENTITYID", "", "", false);
/* 103 */     if (str.length() < "000000000000".length()) {
/* 104 */       str = "000000000000".substring(0, "000000000000".length() - str.length()) + str;
/*     */     }
/* 106 */     stringBuffer.append(str);
/* 107 */     stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, "PSUCLASS", "", "", false));
/* 108 */     stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, "PSUATTRACTION", "", "", false));
/* 109 */     stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, "PSUATTRIBUTE", "", "", false));
/*     */     
/* 111 */     return stringBuffer.toString();
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
/*     */   private void processList(EntityItem[] paramArrayOfEntityItem) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException {
/* 131 */     int i = 0;
/* 132 */     byte b1 = 0;
/* 133 */     String str = null;
/* 134 */     int j = -1;
/* 135 */     PSUUpdateData pSUUpdateData = null;
/* 136 */     boolean bool1 = true;
/*     */ 
/*     */     
/* 139 */     int k = Integer.parseInt(PokUtils.getAttributeValue(this.rootEntity, "PSUMAX", "", "" + paramArrayOfEntityItem.length, false));
/*     */     
/* 141 */     this.abr.addDebug(3, "PSUList.processList: psuMax: " + k);
/*     */ 
/*     */     
/* 144 */     OPICMList oPICMList1 = new OPICMList();
/* 145 */     OPICMList oPICMList2 = new OPICMList();
/*     */     
/* 147 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 148 */     boolean bool2 = true;
/*     */ 
/*     */     
/* 151 */     for (byte b2 = 0; b2 < paramArrayOfEntityItem.length && this.abr.getReturnCode() == 0; b2++) {
/* 152 */       EntityItem entityItem = paramArrayOfEntityItem[b2];
/* 153 */       String str1 = PokUtils.getAttributeValue(entityItem, "PSUENTITYTYPE", "", null, false);
/* 154 */       String str2 = PokUtils.getAttributeValue(entityItem, "PSUENTITYID", "", null, false);
/* 155 */       this.abr.addDebug(4, "PSUList.processList[" + b2 + "]: " + entityItem.getKey() + " psuEntityType: " + str1 + " psuEntityId: " + str2);
/* 156 */       if (str1 == null) {
/*     */         
/* 158 */         this.args[0] = entityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(entityItem);
/* 159 */         this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "PSUENTITYTYPE", "PSUENTITYTYPE");
/* 160 */         this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */         
/*     */         break;
/*     */       } 
/* 164 */       int m = 0;
/*     */       
/* 166 */       if (str2 == null) {
/*     */         
/* 168 */         this.args[0] = entityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(entityItem);
/* 169 */         this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "PSUENTITYID", "PSUENTITYID");
/* 170 */         this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */         
/*     */         break;
/*     */       } 
/*     */       try {
/* 175 */         m = Integer.parseInt(str2);
/* 176 */       } catch (NumberFormatException numberFormatException) {
/*     */         
/* 178 */         this.args[0] = entityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(entityItem);
/* 179 */         this.args[1] = this.abr.getLD_Value(entityItem, "PSUENTITYID");
/* 180 */         this.abr.addError("INVALID_FORMAT_ERR", this.args);
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 186 */       if (!this.abr.wasPreviouslyProcessed(str1, m)) {
/*     */ 
/*     */ 
/*     */         
/* 190 */         bool2 = false;
/* 191 */         if (str == null) {
/* 192 */           str = str1;
/*     */         }
/*     */ 
/*     */         
/* 196 */         if (!str1.equals(str) || j != m) {
/*     */           
/* 198 */           if (oPICMList2.size() + i >= k) {
/* 199 */             this.abr.addDebug(1, "PSUList.processList:  psuMax: " + k + " updates has been reached, stopping processing");
/*     */             
/* 201 */             bool1 = false;
/*     */             break;
/*     */           } 
/* 204 */           if (oPICMList2.size() >= PSUABRSTATUS.UPDATE_SIZE) {
/* 205 */             i += oPICMList2.size() + b1;
/* 206 */             if (oPICMList1.size() > 0) {
/* 207 */               this.abr.getCurrentValues(oPICMList1);
/* 208 */               oPICMList1.removeAll();
/*     */             } 
/* 210 */             hashtable.clear();
/* 211 */             b1 = 0;
/* 212 */             this.abr.doUpdates(this.rootEntity, oPICMList2, false);
/*     */           } 
/*     */         } 
/*     */         
/* 216 */         String str3 = PokUtils.getAttributeFlagValue(entityItem, "PSUCLASS");
/* 217 */         this.abr.addDebug(4, "PSUList.processList: " + entityItem.getKey() + " psuClass: " + str3);
/* 218 */         if ("PSUC1".equalsIgnoreCase(str3)) {
/* 219 */           if (!str1.equals(str) || j != m) {
/*     */             
/* 221 */             pSUUpdateData = new PSUUpdateData(this.abr, str1, m);
/* 222 */             oPICMList2.put(pSUUpdateData);
/*     */           } 
/*     */ 
/*     */           
/* 226 */           if (!buildListUpdate(entityItem, pSUUpdateData, oPICMList1)) {
/*     */             break;
/*     */           }
/* 229 */         } else if ("PSUC2".equalsIgnoreCase(str3)) {
/* 230 */           String str4 = PokUtils.getAttributeValue(entityItem, "PSUATTRACTION", "", null, false);
/* 231 */           String str5 = PokUtils.getAttributeValue(entityItem, "PSURELATORACTION", "", null, false);
/* 232 */           String str6 = PokUtils.getAttributeValue(entityItem, "PSURELATORTYPE", "", null, false);
/* 233 */           this.abr.addDebug(4, "PSUList.processList: reference " + entityItem.getKey() + " psuAttrAct: " + str4 + " psuRelAction: " + str5 + " psuRelType: " + str6);
/*     */ 
/*     */           
/* 236 */           if (str5 == null) {
/*     */             
/* 238 */             this.args[0] = entityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(entityItem);
/* 239 */             this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "PSURELATORACTION", "PSURELATORACTION");
/* 240 */             this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 245 */           if ("N".equalsIgnoreCase(str4)) {
/* 246 */             if (str6 == null) {
/*     */               
/* 248 */               this.args[0] = entityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(entityItem);
/* 249 */               this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "PSURELATORTYPE", "PSURELATORTYPE");
/* 250 */               this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */               
/*     */               break;
/*     */             } 
/* 254 */             if (!str1.equals(str) || j != m) {
/*     */               
/* 256 */               pSUUpdateData = new PSULinkData(this.abr, str1, m);
/* 257 */               oPICMList2.put(pSUUpdateData);
/*     */             } 
/*     */ 
/*     */             
/* 261 */             if (!buildListReference(entityItem, (PSULinkData)pSUUpdateData)) {
/*     */               break;
/*     */             }
/* 264 */           } else if ("D".equalsIgnoreCase(str4)) {
/*     */ 
/*     */             
/* 267 */             pSUUpdateData = (PSUDeleteData)hashtable.get(str1);
/* 268 */             if (pSUUpdateData == null) {
/* 269 */               pSUUpdateData = new PSUDeleteData(this.abr, str1);
/* 270 */               ((PSUDeleteData)pSUUpdateData).addDeleteId(m);
/* 271 */               ((PSUDeleteData)pSUUpdateData).setActionName(str5);
/* 272 */               hashtable.put(str1, pSUUpdateData);
/* 273 */               oPICMList2.put(pSUUpdateData);
/*     */             } else {
/* 275 */               ((PSUDeleteData)pSUUpdateData).addDeleteId(m);
/* 276 */               b1++;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 281 */             this.args[0] = entityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(entityItem);
/* 282 */             if (str4 == null) {
/* 283 */               this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "PSUATTRACTION", "PSUATTRACTION");
/* 284 */               this.abr.addError("REQ_NOTPOPULATED_ERR", this.args); break;
/*     */             } 
/* 286 */             this.args[1] = this.abr.getLD_Value(entityItem, "PSUATTRACTION");
/* 287 */             this.args[2] = "D, N";
/* 288 */             this.abr.addError("NOTSUPPORTEDLIST_ERR", this.args);
/*     */ 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } else {
/* 295 */           this.args[0] = entityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(entityItem);
/* 296 */           if (str3 == null) {
/* 297 */             this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "PSUCLASS", "PSUCLASS");
/* 298 */             this.abr.addError("REQ_NOTPOPULATED_ERR", this.args); break;
/*     */           } 
/* 300 */           this.args[1] = this.abr.getLD_Value(entityItem, "PSUCLASS");
/* 301 */           this.abr.addError("NOTSUPPORTED_ERR", this.args);
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 306 */         if (!str1.equals(str)) {
/*     */           
/* 308 */           if (oPICMList1.size() > 0) {
/* 309 */             this.abr.getCurrentValues(oPICMList1);
/* 310 */             oPICMList1.removeAll();
/*     */           } 
/*     */ 
/*     */           
/* 314 */           str = str1;
/* 315 */           j = m;
/* 316 */         } else if (j != m) {
/* 317 */           j = m;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 323 */     if (this.abr.getReturnCode() != 0) {
/* 324 */       while (oPICMList2.size() > 0) {
/* 325 */         PSUUpdateData pSUUpdateData1 = (PSUUpdateData)oPICMList2.remove(0);
/* 326 */         pSUUpdateData1.dereference();
/*     */       } 
/*     */       
/* 329 */       oPICMList1.removeAll();
/* 330 */       hashtable.clear();
/*     */     } else {
/* 332 */       if (bool2) {
/*     */ 
/*     */         
/* 335 */         this.abr.addMessage("", "ALLSKIPPED", (Object[])null);
/* 336 */         bool1 = true;
/*     */       } 
/*     */       
/* 339 */       if (oPICMList2.size() > 0) {
/*     */         
/* 341 */         if (oPICMList1.size() > 0) {
/* 342 */           this.abr.getCurrentValues(oPICMList1);
/* 343 */           oPICMList1.removeAll();
/*     */         } 
/* 345 */         hashtable.clear();
/*     */       } 
/*     */       
/* 348 */       this.abr.doUpdates(this.rootEntity, oPICMList2, bool1);
/*     */     } 
/*     */     
/* 351 */     oPICMList1 = null;
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
/*     */   private boolean buildListUpdate(EntityItem paramEntityItem, PSUUpdateData paramPSUUpdateData, OPICMList paramOPICMList) throws SQLException, MiddlewareException {
/* 364 */     boolean bool = true;
/*     */     
/* 366 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "PSUATTRIBUTE", "", null, false);
/* 367 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "PSUATTRACTION", "", null, false);
/* 368 */     String str3 = PokUtils.getAttributeValue(paramEntityItem, "PSUATTRTYPE", "", null, false);
/* 369 */     String str4 = PokUtils.getAttributeValue(paramEntityItem, "PSUATTRVALUE", "", null, false);
/* 370 */     this.abr.addDebug(4, "PSUList.buildListUpdate: " + paramEntityItem.getKey() + " psuAttrAction: " + str2 + " psuAttr: " + str1 + " psuAttrType: " + str3 + " psuAttrValue: " + str4);
/*     */ 
/*     */ 
/*     */     
/* 374 */     if (str1 == null) {
/*     */       
/* 376 */       this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 377 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSUATTRIBUTE", "PSUATTRIBUTE");
/* 378 */       this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/* 379 */       bool = false;
/*     */     } 
/* 381 */     if (str3 == null) {
/*     */       
/* 383 */       this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 384 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSUATTRTYPE", "PSUATTRTYPE");
/* 385 */       this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/* 386 */       bool = false;
/*     */     }
/* 388 */     else if (!str3.equalsIgnoreCase("U") && 
/* 389 */       !str3.equalsIgnoreCase("A") && 
/* 390 */       !str3.equalsIgnoreCase("S") && 
/* 391 */       !str3.equalsIgnoreCase("F") && 
/* 392 */       !str3.equalsIgnoreCase("T")) {
/*     */ 
/*     */       
/* 395 */       this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 396 */       this.args[1] = this.abr.getLD_Value(paramEntityItem, "PSUATTRTYPE");
/* 397 */       this.args[2] = "U, A, S, F, T";
/* 398 */       this.abr.addError("NOTSUPPORTEDLIST_ERR", this.args);
/* 399 */       bool = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 404 */     if ("N".equalsIgnoreCase(str2)) {
/* 405 */       if (str4 == null) {
/*     */         
/* 407 */         this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 408 */         this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSUATTRVALUE", "PSUATTRVALUE");
/* 409 */         this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/* 410 */         bool = false;
/*     */       } 
/*     */       
/* 413 */       if (bool)
/*     */       {
/* 415 */         this.abr.setAttribute(str3, paramPSUUpdateData, str1, str4);
/*     */       }
/* 417 */     } else if ("D".equalsIgnoreCase(str2)) {
/*     */       
/* 419 */       if (bool) {
/* 420 */         if (str4 == null) {
/* 421 */           str4 = "temp";
/*     */         }
/*     */         
/* 424 */         this.abr.deactivateAttribute(str3, paramPSUUpdateData, str1, str4);
/* 425 */         paramOPICMList.put(paramPSUUpdateData);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 430 */       this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 431 */       if (str2 == null) {
/* 432 */         this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSUATTRACTION", "PSUATTRACTION");
/* 433 */         this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */       } else {
/* 435 */         this.args[1] = this.abr.getLD_Value(paramEntityItem, "PSUATTRACTION");
/* 436 */         this.args[2] = "D, N";
/* 437 */         this.abr.addError("NOTSUPPORTEDLIST_ERR", this.args);
/*     */       } 
/*     */       
/* 440 */       bool = false;
/*     */     } 
/*     */     
/* 443 */     return bool;
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
/*     */   private boolean buildListReference(EntityItem paramEntityItem, PSULinkData paramPSULinkData) throws SQLException, MiddlewareException {
/* 455 */     boolean bool = true;
/*     */     
/* 457 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "PSUENTITYTYPEREF", "", null, false);
/* 458 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "PSUENTITYIDREF", "", null, false);
/* 459 */     String str3 = PokUtils.getAttributeValue(paramEntityItem, "PSURELATORACTION", "", null, false);
/* 460 */     String str4 = PokUtils.getAttributeValue(paramEntityItem, "PSUATTRACTION", "", null, false);
/* 461 */     this.abr.addDebug(4, "PSUList.buildListReference: " + paramEntityItem.getKey() + " psuEntityTypeRef: " + str1 + " psuEntityIdRef: " + str2 + " psuRelatorAct: " + str3 + " psuAttrAct: " + str4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 466 */     if ("N".equalsIgnoreCase(str4)) {
/*     */       
/* 468 */       if (str1 == null) {
/*     */         
/* 470 */         this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 471 */         this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSUENTITYTYPEREF", "PSUENTITYTYPEREF");
/* 472 */         this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/* 473 */         bool = false;
/*     */       } 
/* 475 */       int i = 0;
/* 476 */       if (str2 == null) {
/*     */         
/* 478 */         this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 479 */         this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSUENTITYIDREF", "PSUENTITYIDREF");
/* 480 */         this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/* 481 */         bool = false;
/*     */       } else {
/*     */         
/*     */         try {
/* 485 */           i = Integer.parseInt(str2);
/* 486 */         } catch (NumberFormatException numberFormatException) {
/*     */           
/* 488 */           this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 489 */           this.args[1] = this.abr.getLD_Value(paramEntityItem, "PSUENTITYID");
/* 490 */           this.abr.addError("INVALID_FORMAT_ERR", this.args);
/* 491 */           bool = false;
/*     */         } 
/*     */       } 
/*     */       
/* 495 */       if (bool) {
/* 496 */         PSUUpdateData pSUUpdateData = paramPSULinkData.addChild(this.abr, str1, i, str3);
/*     */ 
/*     */         
/* 499 */         String str5 = PokUtils.getAttributeValue(paramEntityItem, "PSUATTRIBUTE", "", null, false);
/* 500 */         String str6 = PokUtils.getAttributeValue(paramEntityItem, "PSUATTRTYPE", "", null, false);
/* 501 */         String str7 = PokUtils.getAttributeValue(paramEntityItem, "PSUATTRVALUE", "", null, false);
/* 502 */         String str8 = PokUtils.getAttributeValue(paramEntityItem, "PSUATTRACTION", "", null, false);
/*     */         
/* 504 */         this.abr.addDebug(4, "PSUList.buildListReference: " + paramEntityItem.getKey() + " psuAttrAction: " + str8 + " psuAttr: " + str5 + " psuAttrType: " + str6 + " psuAttrValue: " + str7);
/*     */ 
/*     */ 
/*     */         
/* 508 */         if (str5 != null)
/*     */         {
/* 510 */           if (str6 == null) {
/*     */             
/* 512 */             this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 513 */             this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSUATTRTYPE", "PSUATTRTYPE");
/* 514 */             this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/* 515 */             bool = false;
/*     */           }
/* 517 */           else if (!str6.equalsIgnoreCase("U") && 
/* 518 */             !str6.equalsIgnoreCase("A") && 
/* 519 */             !str6.equalsIgnoreCase("S") && 
/* 520 */             !str6.equalsIgnoreCase("F") && 
/* 521 */             !str6.equalsIgnoreCase("T")) {
/*     */ 
/*     */             
/* 524 */             this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 525 */             this.args[1] = this.abr.getLD_Value(paramEntityItem, "PSUATTRTYPE");
/* 526 */             this.args[2] = "U, A, S, F, T";
/* 527 */             this.abr.addError("NOTSUPPORTEDLIST_ERR", this.args);
/* 528 */             bool = false;
/*     */           } 
/*     */           
/* 531 */           if (str7 == null) {
/*     */             
/* 533 */             this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 534 */             this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSUATTRVALUE", "PSUATTRVALUE");
/* 535 */             this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/* 536 */             bool = false;
/*     */           } 
/*     */           
/* 539 */           if (bool) {
/* 540 */             this.abr.setAttribute(str6, pSUUpdateData, str5, str7);
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 545 */     } else if ("D".equalsIgnoreCase(str4)) {
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 550 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void dereference() {
/* 557 */     this.abr = null;
/* 558 */     this.rootEntity = null;
/* 559 */     this.args = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\psu\PSUList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */