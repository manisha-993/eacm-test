/*     */ package COM.ibm.eannounce.abr.sg.psu;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.WorkflowException;
/*     */ import COM.ibm.opicmpdh.middleware.LockException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
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
/*     */ class PSUView
/*     */ {
/*     */   private static final String DB_PDH = "DBTYPE1";
/*     */   private PSUABRSTATUS abr;
/*     */   private EntityItem rootEntity;
/*  52 */   private Object[] args = (Object[])new String[3];
/*     */   
/*     */   private String entityTypeCol;
/*     */   
/*     */   private String entityIdCol;
/*     */   
/*     */   private String attrCol;
/*     */   
/*     */   private String attrActionCol;
/*     */   
/*     */   private String attrTypeCol;
/*     */   private String attrValueCol;
/*     */   private String entityTypeRefCol;
/*     */   private String entityIdRefCol;
/*     */   private String relatorTypeCol;
/*     */   private String relatorActCol;
/*     */   
/*     */   PSUView(PSUABRSTATUS paramPSUABRSTATUS, EntityItem paramEntityItem) {
/*  70 */     this.abr = paramPSUABRSTATUS;
/*  71 */     this.rootEntity = paramEntityItem;
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
/*     */   void execute(EntityGroup paramEntityGroup) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException {
/*  90 */     String str1 = PokUtils.getAttributeValue(this.rootEntity, "PSUVIEW", "", null, false);
/*     */     
/*  92 */     String str2 = PokUtils.getAttributeFlagValue(this.rootEntity, "PSUDBTYPE");
/*     */     
/*  94 */     this.abr.addDebug(3, "PSUView.execute: " + this.rootEntity.getKey() + " viewname: " + str1 + " dbtype: " + str2);
/*     */ 
/*     */     
/*  97 */     if (str1 == null) {
/*     */       
/*  99 */       this.args[0] = this.rootEntity.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(this.rootEntity);
/* 100 */       this.args[1] = PokUtils.getAttributeDescription(this.rootEntity.getEntityGroup(), "PSUVIEW", "PSUVIEW");
/* 101 */       this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */       
/*     */       return;
/*     */     } 
/* 105 */     if (str2 == null) {
/*     */       
/* 107 */       this.args[0] = this.rootEntity.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(this.rootEntity);
/* 108 */       this.args[1] = PokUtils.getAttributeDescription(this.rootEntity.getEntityGroup(), "PSUDBTYPE", "PSUDBTYPE");
/* 109 */       this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 115 */     byte b1 = 0;
/* 116 */     EntityItem entityItem = null;
/* 117 */     for (byte b2 = 0; b2 < paramEntityGroup.getEntityItemCount(); b2++) {
/* 118 */       EntityItem entityItem1 = paramEntityGroup.getEntityItem(b2);
/* 119 */       String str = PokUtils.getAttributeFlagValue(entityItem1, "PSUCLASS");
/* 120 */       this.abr.addDebug(3, "PSUView.execute: " + entityItem1.getKey() + " psuclass: " + str);
/* 121 */       if ("PSUC2".equalsIgnoreCase(str) || "PSUC1"
/* 122 */         .equalsIgnoreCase(str)) {
/* 123 */         b1++;
/* 124 */         entityItem = entityItem1;
/*     */       } 
/*     */     } 
/* 127 */     if (b1 != 1) {
/*     */       
/* 129 */       this.args[0] = this.abr.getLD_Value(this.rootEntity, "PSUCRITERIA");
/* 130 */       this.args[1] = paramEntityGroup.getLongDescription();
/* 131 */       this.abr.addError("VIEW_ERR", this.args);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 136 */     this.entityTypeCol = PokUtils.getAttributeValue(entityItem, "PSUENTITYTYPE", "", null, false);
/* 137 */     this.entityIdCol = PokUtils.getAttributeValue(entityItem, "PSUENTITYID", "", null, false);
/* 138 */     this.attrCol = PokUtils.getAttributeValue(entityItem, "PSUATTRIBUTE", "", null, false);
/* 139 */     this.attrActionCol = PokUtils.getAttributeValue(entityItem, "PSUATTRACTION", "", null, false);
/* 140 */     this.attrTypeCol = PokUtils.getAttributeValue(entityItem, "PSUATTRTYPE", "", null, false);
/* 141 */     this.attrValueCol = PokUtils.getAttributeValue(entityItem, "PSUATTRVALUE", "", null, false);
/* 142 */     this.entityTypeRefCol = PokUtils.getAttributeValue(entityItem, "PSUENTITYTYPEREF", "", null, false);
/* 143 */     this.entityIdRefCol = PokUtils.getAttributeValue(entityItem, "PSUENTITYIDREF", "", null, false);
/* 144 */     this.relatorTypeCol = PokUtils.getAttributeValue(entityItem, "PSURELATORTYPE", "", null, false);
/* 145 */     this.relatorActCol = PokUtils.getAttributeValue(entityItem, "PSURELATORACTION", "", null, false);
/*     */ 
/*     */     
/* 148 */     String str3 = PokUtils.getAttributeFlagValue(entityItem, "PSUCLASS");
/* 149 */     if ("PSUC1".equalsIgnoreCase(str3)) {
/* 150 */       this.abr.addDebug(2, "PSUView.execute: " + entityItem.getKey() + " update columns PSUENTITYTYPE: " + this.entityTypeCol + " PSUENTITYID: " + this.entityIdCol + " PSUATTRIBUTE: " + this.attrCol + " PSUATTRACTION: " + this.attrActionCol + " PSUATTRTYPE: " + this.attrTypeCol + " PSUATTRVALUE: " + this.attrValueCol);
/*     */     }
/*     */     else {
/*     */       
/* 154 */       this.abr.addDebug(2, "PSUView.execute: " + entityItem.getKey() + " reference columns PSUENTITYTYPE: " + this.entityTypeCol + " PSUENTITYID: " + this.entityIdCol + " PSUATTRIBUTE: " + this.attrCol + " PSUATTRACTION: " + this.attrActionCol + " PSUATTRTYPE: " + this.attrTypeCol + " PSUATTRVALUE: " + this.attrValueCol + " PSUENTITYTYPEREF: " + this.entityTypeRefCol + " PSUENTITYIDREF: " + this.entityIdRefCol + " PSURELATORTYPE: " + this.relatorTypeCol + " PSURELATORACTION: " + this.relatorActCol);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (this.entityTypeCol == null) {
/*     */       
/* 163 */       this.args[0] = entityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(entityItem);
/* 164 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityGroup, "PSUENTITYTYPE", "PSUENTITYTYPE");
/* 165 */       this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */     } 
/* 167 */     if (this.entityIdCol == null) {
/*     */       
/* 169 */       this.args[0] = entityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(entityItem);
/* 170 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityGroup, "PSUENTITYID", "PSUENTITYID");
/* 171 */       this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */     } 
/* 173 */     if (this.attrActionCol == null) {
/*     */       
/* 175 */       this.args[0] = entityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(entityItem);
/* 176 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityGroup, "PSUATTRACTION", "PSUATTRACTION");
/* 177 */       this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */     } 
/*     */     
/* 180 */     if ("PSUC1".equalsIgnoreCase(str3)) {
/* 181 */       checkUpdateColumns(entityItem);
/* 182 */     } else if ("PSUC2".equalsIgnoreCase(str3)) {
/* 183 */       checkReferenceColumns(entityItem);
/*     */     } 
/*     */     
/* 186 */     if (this.abr.getReturnCode() == 0) {
/* 187 */       processView(entityItem, str1, str2, str3);
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
/*     */   private void checkUpdateColumns(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 199 */     if (this.attrTypeCol == null) {
/*     */       
/* 201 */       this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 202 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSUATTRTYPE", "PSUATTRTYPE");
/* 203 */       this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */     } 
/*     */     
/* 206 */     if (this.attrCol == null) {
/*     */       
/* 208 */       this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 209 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSUATTRIBUTE", "PSUATTRIBUTE");
/* 210 */       this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkReferenceColumns(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 244 */     if (this.relatorActCol == null) {
/*     */       
/* 246 */       this.args[0] = paramEntityItem.getEntityGroup().getLongDescription() + ": " + this.abr.getNavigationName(paramEntityItem);
/* 247 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "PSURELATORACTION", "PSURELATORACTION");
/* 248 */       this.abr.addError("REQ_NOTPOPULATED_ERR", this.args);
/*     */     } 
/* 250 */     if (this.attrCol != null)
/*     */     {
/* 252 */       checkUpdateColumns(paramEntityItem);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getOrderBy() {
/* 260 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 262 */     stringBuffer.append(this.entityTypeCol + ",");
/* 263 */     stringBuffer.append(this.entityIdCol);
/* 264 */     if (this.attrActionCol != null) {
/* 265 */       stringBuffer.append("," + this.attrActionCol);
/*     */     }
/* 267 */     if (this.attrCol != null) {
/* 268 */       stringBuffer.append("," + this.attrCol);
/*     */     }
/*     */     
/* 271 */     return stringBuffer.toString();
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
/*     */   private void processView(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException {
/* 291 */     boolean bool1 = true;
/* 292 */     String str = null;
/* 293 */     int i = -1;
/* 294 */     PSUUpdateData pSUUpdateData = null;
/*     */     
/* 296 */     ResultSet resultSet = null;
/* 297 */     Statement statement = null;
/*     */ 
/*     */     
/* 300 */     int j = Integer.parseInt(PokUtils.getAttributeValue(this.rootEntity, "PSUMAX", "", "2147483647", false));
/* 301 */     this.abr.addDebug(3, "PSUView.processView: psuMax: " + j);
/*     */ 
/*     */     
/* 304 */     StringBuffer stringBuffer = new StringBuffer("select * from " + paramString1 + " order by ");
/* 305 */     stringBuffer.append(getOrderBy());
/* 306 */     stringBuffer.append(" with ur");
/*     */     
/* 308 */     this.abr.addDebug(2, "PSUView.processView: sql: " + stringBuffer.toString());
/*     */     
/* 310 */     long l = System.currentTimeMillis();
/* 311 */     byte b1 = 0;
/* 312 */     int k = 0;
/* 313 */     byte b2 = 0;
/*     */ 
/*     */     
/* 316 */     OPICMList oPICMList1 = new OPICMList();
/* 317 */     OPICMList oPICMList2 = new OPICMList();
/* 318 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 319 */     boolean bool2 = true;
/*     */     
/*     */     try {
/* 322 */       if (paramString2.equals("DBTYPE1")) {
/* 323 */         statement = this.abr.getDatabase().getPDHConnection().createStatement();
/*     */       } else {
/*     */         
/* 326 */         statement = this.abr.getDatabase().getODSConnection().createStatement();
/*     */       } 
/*     */       
/* 329 */       resultSet = statement.executeQuery(stringBuffer.toString());
/*     */       
/* 331 */       while (resultSet.next() && this.abr.getReturnCode() == 0) {
/* 332 */         b1++;
/*     */         
/* 334 */         String str1 = resultSet.getString(this.entityTypeCol);
/* 335 */         String str2 = resultSet.getString(this.entityIdCol);
/*     */         
/* 337 */         int m = 0;
/*     */         
/*     */         try {
/* 340 */           m = Integer.parseInt(str2);
/* 341 */         } catch (NumberFormatException numberFormatException) {
/* 342 */           this.abr.addDebug(0, "PSUView.processView[" + b1 + "]: Error entityTypeCol " + this.entityTypeCol + " " + str1 + ": entityIdCol " + this.entityIdCol + ": " + str2);
/*     */ 
/*     */           
/* 345 */           this.args[0] = this.entityIdCol;
/* 346 */           this.args[1] = str2;
/* 347 */           this.abr.addError("INVALID_FORMAT_ERR", this.args);
/*     */           
/*     */           break;
/*     */         } 
/* 351 */         if (this.abr.wasPreviouslyProcessed(str1, m)) {
/*     */           continue;
/*     */         }
/*     */         
/* 355 */         bool2 = false;
/* 356 */         if (str == null) {
/* 357 */           str = str1;
/*     */         }
/*     */         
/* 360 */         String str3 = null;
/* 361 */         String str4 = null;
/* 362 */         String str5 = null;
/* 363 */         String str6 = null;
/*     */         
/* 365 */         String str7 = null;
/* 366 */         String str8 = null;
/* 367 */         String str9 = null;
/* 368 */         String str10 = null;
/*     */         
/* 370 */         if (this.attrCol != null) {
/* 371 */           str3 = resultSet.getString(this.attrCol);
/* 372 */           str5 = resultSet.getString(this.attrTypeCol);
/* 373 */           if (!str5.equalsIgnoreCase("U") && 
/* 374 */             !str5.equalsIgnoreCase("A") && 
/* 375 */             !str5.equalsIgnoreCase("S") && 
/* 376 */             !str5.equalsIgnoreCase("F") && 
/* 377 */             !str5.equalsIgnoreCase("T")) {
/*     */             
/* 379 */             this.abr.addDebug(0, "PSUView.processView[" + b1 + "]: Error attrCol " + this.attrCol + " " + str3 + ": attrTypeCol " + this.attrTypeCol + ": " + str5);
/*     */ 
/*     */             
/* 382 */             this.args[0] = "Attribute Type";
/* 383 */             this.args[1] = str5;
/* 384 */             this.abr.addError("NOTSUPPORTED_ERR", this.args);
/*     */             break;
/*     */           } 
/*     */         } 
/* 388 */         if (this.attrActionCol != null) {
/* 389 */           str4 = resultSet.getString(this.attrActionCol);
/*     */         }
/*     */         
/* 392 */         if (this.attrValueCol != null) {
/* 393 */           str6 = resultSet.getString(this.attrValueCol);
/*     */         }
/*     */         
/* 396 */         if ("PSUC2".equalsIgnoreCase(paramString3)) {
/* 397 */           if (this.entityTypeRefCol != null) {
/* 398 */             str7 = resultSet.getString(this.entityTypeRefCol);
/* 399 */             str8 = resultSet.getString(this.entityIdRefCol);
/*     */           } 
/*     */           
/* 402 */           if (this.relatorTypeCol != null) {
/* 403 */             str9 = resultSet.getString(this.relatorTypeCol);
/*     */           }
/*     */           
/* 406 */           if (this.relatorActCol != null) {
/* 407 */             str10 = resultSet.getString(this.relatorActCol);
/*     */           }
/*     */         } 
/*     */         
/* 411 */         if ("PSUC1".equalsIgnoreCase(paramString3)) {
/* 412 */           this.abr.addDebug(4, "update[" + b1 + "] PSUENTITYTYPE: " + str1 + " PSUENTITYID: " + m + " PSUATTRIBUTE: " + str3 + " PSUATTRACTION: " + str4 + " PSUATTRTYPE: " + str5 + " PSUATTRVALUE: " + str6);
/*     */         }
/*     */         else {
/*     */           
/* 416 */           this.abr.addDebug(4, "reference[" + b1 + "] PSUENTITYTYPE: " + str1 + " PSUENTITYID: " + m + " PSUATTRIBUTE: " + str3 + " PSUATTRACTION: " + str4 + " PSUATTRTYPE: " + str5 + " PSUATTRVALUE: " + str6 + " PSUENTITYTYPEREF: " + str7 + " PSUENTITYIDREF: " + str8 + " PSURELATORTYPE: " + str9 + " PSURELATORACTION: " + str10);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 424 */         if (!str1.equals(str) || i != m) {
/*     */           
/* 426 */           if (oPICMList2.size() + k >= j) {
/* 427 */             this.abr.addDebug(1, "PSUList.execute:  psuMax: " + j + " updates has been reached, stopping processing");
/*     */             
/* 429 */             bool1 = false;
/*     */             break;
/*     */           } 
/* 432 */           if (oPICMList2.size() >= PSUABRSTATUS.UPDATE_SIZE) {
/* 433 */             k += oPICMList2.size() + b2;
/* 434 */             if (oPICMList1.size() > 0) {
/* 435 */               this.abr.getCurrentValues(oPICMList1);
/* 436 */               oPICMList1.removeAll();
/*     */             } 
/* 438 */             hashtable.clear();
/* 439 */             b2 = 0;
/* 440 */             this.abr.doUpdates(this.rootEntity, oPICMList2, false);
/*     */           } 
/*     */         } 
/*     */         
/* 444 */         if ("PSUC1".equalsIgnoreCase(paramString3)) {
/* 445 */           if (!str1.equals(str) || i != m) {
/*     */             
/* 447 */             pSUUpdateData = new PSUUpdateData(this.abr, str1, m);
/* 448 */             oPICMList2.put(pSUUpdateData);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 453 */           if ("N".equalsIgnoreCase(str4)) {
/*     */             
/* 455 */             this.abr.setAttribute(str5, pSUUpdateData, str3, str6);
/* 456 */           } else if ("D".equalsIgnoreCase(str4)) {
/*     */             
/* 458 */             this.abr.deactivateAttribute(str5, pSUUpdateData, str3, str6);
/* 459 */             oPICMList1.put(pSUUpdateData);
/*     */           } 
/* 461 */         } else if ("PSUC2".equalsIgnoreCase(paramString3)) {
/*     */           
/* 463 */           if ("N".equalsIgnoreCase(str4)) {
/*     */             
/* 465 */             if (!str1.equals(str) || i != m) {
/*     */               
/* 467 */               pSUUpdateData = new PSULinkData(this.abr, str1, m);
/* 468 */               oPICMList2.put(pSUUpdateData);
/*     */             } 
/*     */             
/* 471 */             int n = 0;
/*     */             
/*     */             try {
/* 474 */               n = Integer.parseInt(str8);
/* 475 */             } catch (NumberFormatException numberFormatException) {
/* 476 */               this.abr.addDebug(0, "PSUView.processView[" + b1 + "]: Error entityTypeRefCol " + this.entityTypeRefCol + " " + str7 + ": entityIdRefCol " + this.entityIdRefCol + ": " + str8);
/*     */ 
/*     */               
/* 479 */               this.args[0] = this.entityIdRefCol;
/* 480 */               this.args[1] = str8;
/* 481 */               this.abr.addError("INVALID_FORMAT_ERR", this.args);
/*     */               
/*     */               break;
/*     */             } 
/*     */             
/* 486 */             PSUUpdateData pSUUpdateData1 = ((PSULinkData)pSUUpdateData).addChild(this.abr, str7, n, str10);
/*     */ 
/*     */             
/* 489 */             if (str3 != null) {
/* 490 */               this.abr.setAttribute(str5, pSUUpdateData1, str3, str6);
/*     */             }
/* 492 */           } else if ("D".equalsIgnoreCase(str4)) {
/*     */ 
/*     */             
/* 495 */             pSUUpdateData = (PSUDeleteData)hashtable.get(str1);
/* 496 */             if (pSUUpdateData == null) {
/* 497 */               pSUUpdateData = new PSUDeleteData(this.abr, str1);
/* 498 */               ((PSUDeleteData)pSUUpdateData).addDeleteId(m);
/* 499 */               ((PSUDeleteData)pSUUpdateData).setActionName(str10);
/* 500 */               hashtable.put(str1, pSUUpdateData);
/* 501 */               oPICMList2.put(pSUUpdateData);
/*     */             } else {
/* 503 */               ((PSUDeleteData)pSUUpdateData).addDeleteId(m);
/* 504 */               b2++;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 509 */         if (!str1.equals(str)) {
/*     */           
/* 511 */           if (oPICMList1.size() > 0) {
/* 512 */             this.abr.getCurrentValues(oPICMList1);
/* 513 */             oPICMList1.removeAll();
/*     */           } 
/*     */ 
/*     */           
/* 517 */           str = str1;
/* 518 */           i = m; continue;
/* 519 */         }  if (i != m) {
/* 520 */           i = m;
/*     */         }
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 526 */       if (resultSet != null) {
/*     */         try {
/* 528 */           resultSet.close();
/* 529 */         } catch (SQLException sQLException) {}
/* 530 */         resultSet = null;
/*     */       } 
/* 532 */       if (statement != null) {
/*     */         try {
/* 534 */           statement.close();
/* 535 */         } catch (SQLException sQLException) {}
/* 536 */         statement = null;
/*     */       } 
/* 538 */       this.abr.getDatabase().commit();
/*     */     } 
/*     */ 
/*     */     
/* 542 */     if (this.abr.getReturnCode() != 0) {
/* 543 */       while (oPICMList2.size() > 0) {
/* 544 */         PSUUpdateData pSUUpdateData1 = (PSUUpdateData)oPICMList2.remove(0);
/* 545 */         pSUUpdateData1.dereference();
/*     */       } 
/*     */       
/* 548 */       oPICMList1.removeAll();
/*     */     } else {
/* 550 */       if (bool2) {
/*     */ 
/*     */         
/* 553 */         this.abr.addMessage("", "ALLSKIPPED", (Object[])null);
/* 554 */         bool1 = true;
/*     */       } 
/*     */       
/* 557 */       if (oPICMList2.size() > 0) {
/*     */         
/* 559 */         if (oPICMList1.size() > 0) {
/* 560 */           this.abr.getCurrentValues(oPICMList1);
/* 561 */           oPICMList1.removeAll();
/*     */         } 
/*     */         
/* 564 */         hashtable.clear();
/* 565 */         this.abr.doUpdates(this.rootEntity, oPICMList2, bool1);
/*     */       } 
/*     */     } 
/*     */     
/* 569 */     oPICMList1 = null;
/*     */     
/* 571 */     this.abr.addDebug(4, "Time to process " + b1 + " rows: " + Stopwatch.format(System.currentTimeMillis() - l));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void dereference() {
/* 577 */     this.abr = null;
/* 578 */     this.rootEntity = null;
/* 579 */     this.args = null;
/* 580 */     this.entityTypeCol = null;
/* 581 */     this.entityIdCol = null;
/* 582 */     this.attrCol = null;
/* 583 */     this.attrActionCol = null;
/* 584 */     this.attrTypeCol = null;
/* 585 */     this.attrValueCol = null;
/* 586 */     this.entityTypeRefCol = null;
/* 587 */     this.entityIdRefCol = null;
/* 588 */     this.relatorTypeCol = null;
/* 589 */     this.relatorActCol = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\psu\PSUView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */