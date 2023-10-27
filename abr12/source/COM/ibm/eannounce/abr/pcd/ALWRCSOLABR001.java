/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.ALWRCSOLABR001PDG;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EANObject;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.transactions.PartNo;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ALWRCSOLABR001
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String ABR = "ALWRCSOLABR001";
/*     */   public static final String FINAL = "0020";
/*     */   public static final String REVIEW = "0040";
/* 139 */   private EntityGroup m_egParent = null;
/* 140 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/* 162 */       start_ABRBuild();
/*     */ 
/*     */       
/* 165 */       buildReportHeaderII();
/*     */       
/* 167 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 168 */       this.m_ei = this.m_egParent.getEntityItem(0);
/* 169 */       println("<br><b>Country Solution: " + this.m_ei.getKey() + "</b>");
/*     */       
/* 171 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*     */       
/* 173 */       setReturnCode(0);
/*     */ 
/*     */       
/* 176 */       println("<br/><b>General Area (Region) = </b>" + 
/*     */           
/* 178 */           getAttributeValue(this.m_elist, this.m_ei
/*     */             
/* 180 */             .getEntityType(), this.m_ei
/* 181 */             .getEntityID(), "GENAREANAMEREGION"));
/*     */ 
/*     */ 
/*     */       
/* 185 */       log("ALWRCSOLABR001 checking OF");
/* 186 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("OF");
/*     */       
/* 188 */       if (entityGroup != null) {
/*     */         
/* 190 */         Vector<Integer> vector = getParentEntityIds(this.m_ei
/* 191 */             .getEntityType(), this.m_ei
/* 192 */             .getEntityID(), "OF", "OFCSOL");
/*     */ 
/*     */         
/* 195 */         if (vector.size() <= 0) {
/* 196 */           println("<br /><font color=red>Failed. There is no OF</font>");
/* 197 */           setReturnCode(-1);
/* 198 */         } else if (vector.size() > 1) {
/* 199 */           println("<br /><font color=red>Failed. There is not one and only one OF.</font>");
/* 200 */           setReturnCode(-1);
/*     */         } 
/*     */ 
/*     */         
/* 204 */         EANList eANList1 = new EANList();
/* 205 */         println("<br/></br/><b>Offering(s): </b>"); byte b;
/* 206 */         for (b = 0; b < vector.size(); b++) {
/* 207 */           int i = ((Integer)vector.elementAt(b)).intValue();
/* 208 */           EntityItem entityItem1 = entityGroup.getEntityItem("OF" + i);
/* 209 */           println("</br/><LI>" + entityItem1.toString());
/* 210 */           eANList1.put((EANObject)entityItem1);
/*     */         } 
/*     */ 
/*     */         
/* 214 */         EANList eANList2 = new EANList();
/* 215 */         EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PR");
/* 216 */         println("<br/><br /><b>Project(s):</b>");
/* 217 */         for (b = 0; b < eANList1.size(); b++) {
/* 218 */           EntityItem entityItem1 = (EntityItem)eANList1.getAt(b);
/*     */           
/* 220 */           Vector<String> vector1 = getParentEntityIds(entityItem1
/* 221 */               .getEntityType(), entityItem1
/* 222 */               .getEntityID(), "PR", "PROF");
/*     */ 
/*     */           
/* 225 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*     */             
/* 227 */             EntityItem entityItem2 = entityGroup1.getEntityItem(entityGroup1
/* 228 */                 .getEntityType() + vector1.elementAt(b1));
/* 229 */             if (eANList2.get(entityItem2.getKey()) == null) {
/* 230 */               println("<br /><LI>" + entityItem2.toString());
/* 231 */               eANList2.put((EANObject)entityItem2);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 237 */         EANList eANList3 = new EANList();
/* 238 */         EntityGroup entityGroup2 = this.m_elist.getEntityGroup("BR");
/* 239 */         println("<br/><br /><b>Brand(s):</b>");
/* 240 */         for (b = 0; b < eANList2.size(); b++) {
/* 241 */           EntityItem entityItem1 = (EntityItem)eANList2.getAt(b);
/*     */           
/* 243 */           Vector<String> vector1 = getChildrenEntityIds(entityItem1
/* 244 */               .getEntityType(), entityItem1
/* 245 */               .getEntityID(), "BR", "PRBRANDCODEA");
/*     */ 
/*     */           
/* 248 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*     */             
/* 250 */             EntityItem entityItem2 = entityGroup2.getEntityItem(entityGroup2
/* 251 */                 .getEntityType() + vector1.elementAt(b1));
/* 252 */             if (eANList3.get(entityItem2.getKey()) == null) {
/* 253 */               println("<br /><LI>" + entityItem2.toString());
/* 254 */               eANList3.put((EANObject)entityItem2);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 259 */         println("EntityGroup OF is null\n");
/* 260 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 264 */       log("ALWRCSOLABR001 checking CDG");
/* 265 */       entityGroup = this.m_elist.getEntityGroup("CDG");
/* 266 */       EntityItem entityItem = null;
/* 267 */       if (entityGroup != null) {
/*     */         
/* 269 */         Vector<Integer> vector = getChildrenEntityIds(this.m_ei
/* 270 */             .getEntityType(), this.m_ei
/* 271 */             .getEntityID(), "CDG", "CSOLCDG");
/*     */ 
/*     */         
/* 274 */         if (vector.size() <= 0) {
/* 275 */           println("<br /><font color=red>Failed. There is no CDG</font>");
/* 276 */           setReturnCode(-1);
/* 277 */         } else if (vector.size() > 1) {
/* 278 */           println("<br /><font color=red>Failed. There is not one and only one CDG.</font>");
/* 279 */           setReturnCode(-1);
/*     */         } else {
/* 281 */           int i = ((Integer)vector.elementAt(0)).intValue();
/* 282 */           entityItem = entityGroup.getEntityItem("CDG" + i);
/*     */         } 
/*     */         
/* 285 */         println("<br/></br/><b>Country Designator Group(s):</b>");
/* 286 */         for (byte b = 0; b < vector.size(); b++) {
/* 287 */           int i = ((Integer)vector.elementAt(b)).intValue();
/* 288 */           EntityItem entityItem1 = entityGroup.getEntityItem("CDG" + i);
/* 289 */           println("</br/><LI> " + entityItem1.toString());
/*     */         } 
/*     */       } else {
/* 292 */         println("EntityGroup CDG is null\n");
/* 293 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 297 */       log("ALWRCSOLABR001 checking CD");
/* 298 */       if (entityItem != null) {
/* 299 */         EntityGroup entityGroup1 = this.m_elist.getEntityGroup("CD");
/*     */         
/* 301 */         Vector<Integer> vector = getChildrenEntityIds(entityItem
/* 302 */             .getEntityType(), entityItem
/* 303 */             .getEntityID(), "CD", "CDGCD");
/*     */ 
/*     */         
/* 306 */         if (vector.size() <= 0) {
/* 307 */           println("</br/><font color=red>Failed. No CD exists in the CDG.</font>");
/* 308 */           println("</br/>" + entityItem.toString());
/* 309 */           setReturnCode(-1);
/*     */         } else {
/* 311 */           for (byte b = 0; b < vector.size(); b++) {
/* 312 */             int i = ((Integer)vector.elementAt(b)).intValue();
/* 313 */             EntityItem entityItem1 = entityGroup1.getEntityItem("CD" + i);
/*     */             
/* 315 */             String str = getAttributeValue(this.m_elist, entityItem1
/*     */                 
/* 317 */                 .getEntityType(), entityItem1
/* 318 */                 .getEntityID(), "COUNTRY_DESIG");
/*     */             
/* 320 */             if (str == null || str.length() <= 0) {
/* 321 */               println("</br/><font color=red>Failed. The attribute COUNTRY_DESIG is not populated.</font>");
/* 322 */               println("</br/>" + entityItem1.toString());
/* 323 */               setReturnCode(-1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 336 */       String str1 = getAttributeFlagEnabledValue(this.m_elist, this.m_ei.getEntityType(), this.m_ei.getEntityID(), "CSOLSTATUS").trim();
/* 337 */       if (!str1.equals("0020") && !str1.equals("0040")) {
/* 338 */         println("</br/><font color=red>Failed. The CSOL is not in Ready for Review or Final status. </font>");
/* 339 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 343 */       if (getReturnCode() == 0) {
/* 344 */         log("ALWRCSOLABR001 generating data");
/*     */         
/* 346 */         ALWRCSOLABR001PDG aLWRCSOLABR001PDG = new ALWRCSOLABR001PDG(null, this.m_db, this.m_prof, "ALWRCSOLABR001PDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 352 */         aLWRCSOLABR001PDG.setEntityItem(this.m_ei);
/* 353 */         aLWRCSOLABR001PDG.setABReList(this.m_elist);
/* 354 */         aLWRCSOLABR001PDG.executeAction(this.m_db, this.m_prof);
/* 355 */         StringBuffer stringBuffer = aLWRCSOLABR001PDG.getActivities();
/* 356 */         println("</br></br/><b>Generated Data:</b>");
/* 357 */         println("<br/>" + stringBuffer.toString());
/* 358 */         log("ALWRCSOLABR001 finish generating data");
/*     */ 
/*     */ 
/*     */         
/* 362 */         println("</br><br/><b>Relators: </b>");
/* 363 */         EANList eANList = aLWRCSOLABR001PDG.getSavedEIList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 369 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, this.m_abri.getVEName()); byte b;
/* 370 */         for (b = 0; b < eANList.size(); b++) {
/* 371 */           EntityItem entityItem1 = (EntityItem)eANList.getAt(b);
/* 372 */           println(pullResultReport(entityItem1, extractActionItem));
/*     */         } 
/*     */ 
/*     */         
/* 376 */         Vector<String> vector = new Vector();
/* 377 */         for (b = 0; b < eANList.size(); b++) {
/* 378 */           EntityItem entityItem1 = (EntityItem)eANList.getAt(b);
/*     */           
/* 380 */           for (byte b1 = 0; b1 < entityItem1.getAttributeCount(); b1++) {
/* 381 */             EANAttribute eANAttribute = entityItem1.getAttribute(b1);
/* 382 */             EANMetaAttribute eANMetaAttribute = eANAttribute.getMetaAttribute();
/* 383 */             if (eANAttribute instanceof COM.ibm.eannounce.objects.TextAttribute) {
/* 384 */               if (eANMetaAttribute.isUnique()) {
/* 385 */                 if (eANMetaAttribute.getUniqueClass().equals("LEVEL1")) {
/* 386 */                   vector.addElement(eANAttribute.toString() + eANMetaAttribute.getKey());
/* 387 */                 } else if (eANMetaAttribute
/* 388 */                   .getUniqueClass().equals("LEVEL2")) {
/* 389 */                   vector.addElement(eANAttribute.toString() + eANMetaAttribute.getKey() + entityItem1.getEntityType());
/*     */                 } 
/* 391 */               } else if (eANMetaAttribute.isComboUnique()) {
/* 392 */                 Vector vector1 = eANMetaAttribute.getComboUniqueAttributeCode();
/*     */                 
/* 394 */                 for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 395 */                   String str = eANMetaAttribute.getComboUniqueAttributeCode(b2);
/* 396 */                   EANAttribute eANAttribute1 = entityItem1.getAttribute(str);
/* 397 */                   if (eANAttribute1 instanceof EANFlagAttribute) {
/* 398 */                     String str5 = ((EANFlagAttribute)eANAttribute1).getFlagCodes();
/*     */ 
/*     */                     
/* 401 */                     StringTokenizer stringTokenizer = new StringTokenizer(str5, ":");
/*     */                     
/* 403 */                     while (stringTokenizer.hasMoreTokens()) {
/* 404 */                       String str6 = stringTokenizer.nextToken();
/* 405 */                       vector.addElement(eANAttribute
/* 406 */                           .toString() + ":" + eANMetaAttribute
/* 407 */                           .getKey() + ":" + str6 + ":" + str);
/*     */                     }
/*     */                   
/*     */                   }
/* 411 */                   else if (eANAttribute1 instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/* 412 */                     vector.addElement(eANAttribute
/* 413 */                         .toString() + ":" + eANMetaAttribute
/* 414 */                         .getKey() + ":" + eANAttribute1
/* 415 */                         .toString() + ":" + str);
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               } 
/* 420 */             } else if (eANAttribute instanceof COM.ibm.eannounce.objects.SingleFlagAttribute) {
/* 421 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 422 */               if (eANMetaAttribute.isUnique()) {
/* 423 */                 if (eANMetaAttribute.getUniqueClass().equals("EACHECK")) {
/* 424 */                   vector.addElement(eANAttribute
/* 425 */                       .toString() + eANMetaAttribute
/* 426 */                       .getKey() + entityItem1
/* 427 */                       .getEntityType());
/*     */                 }
/* 429 */               } else if (eANMetaAttribute.isComboUnique()) {
/* 430 */                 Vector vector1 = eANMetaAttribute.getComboUniqueAttributeCode();
/* 431 */                 for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 432 */                   String str = eANMetaAttribute.getComboUniqueAttributeCode(b2);
/* 433 */                   EANAttribute eANAttribute1 = entityItem1.getAttribute(str);
/* 434 */                   if (eANAttribute1 instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/* 435 */                     String str5 = eANAttribute1.toString();
/* 436 */                     vector.addElement(eANFlagAttribute.getFlagCodes() + ":" + eANMetaAttribute
/* 437 */                         .getKey() + ":" + str5 + ":" + str);
/*     */                   
/*     */                   }
/* 440 */                   else if (eANAttribute1 instanceof EANFlagAttribute) {
/* 441 */                     String str5 = ((EANFlagAttribute)eANAttribute1).getFlagCodes();
/*     */ 
/*     */                     
/* 444 */                     StringTokenizer stringTokenizer = new StringTokenizer(str5, ":");
/*     */                     
/* 446 */                     while (stringTokenizer.hasMoreTokens()) {
/* 447 */                       String str6 = stringTokenizer.nextToken();
/* 448 */                       vector.addElement(eANAttribute
/* 449 */                           .toString() + ":" + eANMetaAttribute
/* 450 */                           .getKey() + ":" + str6 + ":" + str);
/*     */                     }
/*     */                   
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               } 
/* 457 */             } else if (eANAttribute instanceof COM.ibm.eannounce.objects.MultiFlagAttribute) {
/* 458 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 459 */               if (eANMetaAttribute.isComboUnique()) {
/* 460 */                 Vector vector1 = eANMetaAttribute.getComboUniqueAttributeCode();
/* 461 */                 for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 462 */                   String str = eANMetaAttribute.getComboUniqueAttributeCode(b2);
/* 463 */                   EANAttribute eANAttribute1 = entityItem1.getAttribute(str);
/* 464 */                   if (eANAttribute1 instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/* 465 */                     String str5 = eANAttribute1.toString();
/* 466 */                     vector.addElement(eANFlagAttribute
/* 467 */                         .getFlagCodes() + ":" + eANMetaAttribute
/* 468 */                         .getKey() + ":" + str5 + ":" + str);
/*     */                   
/*     */                   }
/* 471 */                   else if (eANAttribute1 instanceof EANFlagAttribute) {
/* 472 */                     String str5 = ((EANFlagAttribute)eANAttribute1).getFlagCodes();
/*     */ 
/*     */                     
/* 475 */                     StringTokenizer stringTokenizer = new StringTokenizer(str5, ":");
/*     */                     
/* 477 */                     while (stringTokenizer.hasMoreTokens()) {
/* 478 */                       String str6 = stringTokenizer.nextToken();
/* 479 */                       vector.addElement(eANAttribute
/* 480 */                           .toString() + ":" + eANMetaAttribute
/* 481 */                           .getKey() + ":" + str6 + ":" + str);
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 492 */         for (b = 0; b < vector.size(); b++) {
/* 493 */           String str = vector.elementAt(b);
/* 494 */           PartNo.remove(this.m_db, str);
/*     */         } 
/*     */       } 
/*     */       
/* 498 */       println("<br/><br /><b>" + 
/*     */           
/* 500 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 503 */               getABRDescription(), 
/* 504 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 507 */       log(
/* 508 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 511 */               getABRDescription(), 
/* 512 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 513 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 514 */       setReturnCode(-2);
/* 515 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 519 */           .getMessage() + "</font></h3>");
/*     */       
/* 521 */       logError(lockPDHEntityException.getMessage());
/* 522 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 523 */       setReturnCode(-2);
/* 524 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 526 */           .getMessage() + "</font></h3>");
/*     */       
/* 528 */       logError(updatePDHEntityException.getMessage());
/* 529 */     } catch (SBRException sBRException) {
/* 530 */       setReturnCode(-2);
/* 531 */       println("<h3><font color=red>Generate Data error: " + sBRException
/*     */           
/* 533 */           .toString() + "</font></h3>");
/*     */       
/* 535 */       logError(sBRException.toString());
/* 536 */     } catch (Exception exception) {
/*     */       
/* 538 */       println("<br/>Error in " + this.m_abri
/*     */           
/* 540 */           .getABRCode() + ":" + exception
/*     */           
/* 542 */           .getMessage());
/* 543 */       println("" + exception);
/* 544 */       exception.printStackTrace();
/*     */       
/* 546 */       if (getABRReturnCode() != -2) {
/* 547 */         setReturnCode(-3);
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 552 */       String str1 = getABREntityDesc(this.m_ei.getEntityType(), this.m_ei.getEntityID());
/* 553 */       String str2 = getMetaAttributeDescription(this.m_ei, "ALWRCSOLABR001");
/*     */       
/* 555 */       String str3 = str2 + " for " + str1;
/* 556 */       if (str3.length() > 64) {
/* 557 */         str3 = str3.substring(0, 64);
/*     */       }
/*     */       
/* 560 */       setDGTitle(str3);
/*     */       
/* 562 */       printALWRInfo();
/*     */ 
/*     */       
/* 565 */       setDGString(getABRReturnCode());
/* 566 */       setDGRptName("ALWRCSOLABR001");
/* 567 */       setDGRptClass("ALWRCSOL");
/* 568 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 572 */       buildReportFooter();
/*     */       
/* 574 */       if (!isReadOnly()) {
/* 575 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String pullResultReport(EntityItem paramEntityItem, ExtractActionItem paramExtractActionItem) {
/* 584 */     log("ALWRCSOLABR001 pullResultReport");
/*     */     
/* 586 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     try {
/* 588 */       this.m_prof = refreshProfValOnEffOn(this.m_prof);
/* 589 */       EntityItem[] arrayOfEntityItem = { paramEntityItem };
/*     */       
/* 591 */       EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, paramExtractActionItem, arrayOfEntityItem);
/*     */       
/* 593 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/* 594 */       paramEntityItem = entityGroup.getEntityItem(paramEntityItem.getKey());
/*     */       byte b;
/* 596 */       for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 597 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getUpLink(b);
/* 598 */         EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 599 */         EntityItem entityItem3 = (EntityItem)entityItem1.getDownLink(0);
/* 600 */         stringBuffer.append("<br />Relator: " + entityItem1
/*     */             
/* 602 */             .getKey() + ", Parent: " + entityItem2
/*     */             
/* 604 */             .getKey() + ", Child: " + entityItem3
/*     */             
/* 606 */             .getKey());
/*     */       } 
/*     */       
/* 609 */       for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 610 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 611 */         EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 612 */         EntityItem entityItem3 = (EntityItem)entityItem1.getDownLink(0);
/* 613 */         stringBuffer.append("<br />Relator: " + entityItem1
/*     */             
/* 615 */             .getKey() + ", Parent: " + entityItem2
/*     */             
/* 617 */             .getKey() + ", Child: " + entityItem3
/*     */             
/* 619 */             .getKey());
/*     */       } 
/* 621 */     } catch (Exception exception) {
/* 622 */       exception.printStackTrace();
/*     */     } 
/* 624 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 635 */     return getAttributeValue(this.m_elist, paramString, paramInt, "PNUMB_CT") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 641 */       getAttributeValue(this.m_elist, paramString, paramInt, "GENAREANAMEREGION") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 647 */       getAttributeValue(this.m_elist, paramString, paramInt, "GENAREANAME") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 653 */       getAttributeValue(this.m_elist, paramString, paramInt, "TARG_ANN_DATE_CT") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 659 */       getAttributeValue(this.m_elist, paramString, paramInt, "NAME");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 668 */     return "The purpose of this ABR is to create one or more Country Solutions based on an existing Country Solution (CSOL).  A CSOL is generated for each Country Designator (CD)/Country combination in the related Country Designator Group (CDG) and the part number is determined by the CD.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 679 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 689 */     return "$Revision: 1.33 $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 699 */     return "$Id: ALWRCSOLABR001.java,v 1.33 2010/07/12 21:35:09 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 707 */     return "ALWRCSOLABR001.java,v 1.2";
/*     */   }
/*     */ 
/*     */   
/*     */   private void printALWRInfo() {
/* 712 */     String str1 = getABREntityDesc(this.m_ei.getEntityType(), this.m_ei.getEntityID());
/* 713 */     String str2 = getMetaAttributeDescription(this.m_ei, "ALWRCSOLABR001");
/*     */ 
/*     */     
/* 716 */     println("<br /><b>NAME = </b>" + str2 + " for " + str1);
/*     */     
/* 718 */     println("<br /><b> RPTNAME = </b>ALWRCSOLABR001");
/*     */     
/* 720 */     println("<br /><b> TASKSTATUS = </b>" + (
/*     */         
/* 722 */         (getReturnCode() == 0) ? "Passed" : "Failed"));
/* 723 */     println("<br/><b>PDH Domain = </b>" + 
/*     */         
/* 725 */         getAttributeValue(this.m_elist, this.m_ei
/*     */           
/* 727 */           .getEntityType(), this.m_ei
/* 728 */           .getEntityID(), "PDHDOMAIN"));
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ALWRCSOLABR001.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */