/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.ALWRCVARABR001PDG;
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
/*     */ public class ALWRCVARABR001
/*     */   extends PokBaseABR
/*     */ {
/* 113 */   public static final String ABR = new String("ALWRCVARABR001");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public static final String FINAL = new String("0020");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public static final String REVIEW = new String("0040");
/*     */   
/* 125 */   private EntityGroup m_egParent = null;
/* 126 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 148 */       start_ABRBuild();
/*     */ 
/*     */       
/* 151 */       buildReportHeaderII();
/*     */       
/* 153 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 154 */       this.m_ei = this.m_egParent.getEntityItem(0);
/* 155 */       println("<br><b>Country Variant: " + this.m_ei.getKey() + "</b>");
/*     */       
/* 157 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*     */       
/* 159 */       setReturnCode(0);
/*     */ 
/*     */       
/* 162 */       println("<br/><b>General Area (Region) = </b>" + 
/*     */           
/* 164 */           getAttributeValue(this.m_elist, this.m_ei
/*     */             
/* 166 */             .getEntityType(), this.m_ei
/* 167 */             .getEntityID(), "GENAREANAMEREGION"));
/*     */ 
/*     */ 
/*     */       
/* 171 */       log("ALWRCVARABR001 checking VAR");
/* 172 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("VAR");
/*     */       
/* 174 */       if (entityGroup != null) {
/*     */         
/* 176 */         Vector<Integer> vector = getParentEntityIds(this.m_ei
/* 177 */             .getEntityType(), this.m_ei
/* 178 */             .getEntityID(), "VAR", "VARCVAR");
/*     */ 
/*     */         
/* 181 */         if (vector.size() <= 0) {
/* 182 */           println("<br /><font color=red>Failed. There is no VAR</font>");
/* 183 */           setReturnCode(-1);
/* 184 */         } else if (vector.size() > 1) {
/* 185 */           println("<br /><font color=red>Failed. There is not one and only one VAR.</font>");
/* 186 */           setReturnCode(-1);
/*     */         } 
/*     */ 
/*     */         
/* 190 */         EANList eANList1 = new EANList();
/* 191 */         println("<br/></br/><b>Variant(s): </b>"); byte b;
/* 192 */         for (b = 0; b < vector.size(); b++) {
/* 193 */           int i = ((Integer)vector.elementAt(b)).intValue();
/* 194 */           EntityItem entityItem1 = entityGroup.getEntityItem("VAR" + i);
/* 195 */           println("</br/><LI>" + entityItem1.toString());
/* 196 */           eANList1.put((EANObject)entityItem1);
/*     */         } 
/*     */ 
/*     */         
/* 200 */         EANList eANList2 = new EANList();
/* 201 */         EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PR");
/* 202 */         println("<br/><br /><b>Project(s):</b>");
/* 203 */         for (b = 0; b < eANList1.size(); b++) {
/* 204 */           EntityItem entityItem1 = (EntityItem)eANList1.getAt(b);
/*     */           
/* 206 */           Vector<String> vector1 = getParentEntityIds(entityItem1
/* 207 */               .getEntityType(), entityItem1
/* 208 */               .getEntityID(), "PR", "PRVAR");
/*     */ 
/*     */           
/* 211 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*     */             
/* 213 */             EntityItem entityItem2 = entityGroup1.getEntityItem(entityGroup1
/* 214 */                 .getEntityType() + vector1.elementAt(b1));
/* 215 */             if (eANList2.get(entityItem2.getKey()) == null) {
/* 216 */               println("<br /><LI>" + entityItem2.toString());
/* 217 */               eANList2.put((EANObject)entityItem2);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 223 */         EANList eANList3 = new EANList();
/* 224 */         EntityGroup entityGroup2 = this.m_elist.getEntityGroup("BR");
/* 225 */         println("<br/><br /><b>Brand(s):</b>");
/* 226 */         for (b = 0; b < eANList2.size(); b++) {
/* 227 */           EntityItem entityItem1 = (EntityItem)eANList2.getAt(b);
/*     */           
/* 229 */           Vector<String> vector1 = getChildrenEntityIds(entityItem1
/* 230 */               .getEntityType(), entityItem1
/* 231 */               .getEntityID(), "BR", "PRBRANDCODEA");
/*     */ 
/*     */           
/* 234 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*     */             
/* 236 */             EntityItem entityItem2 = entityGroup2.getEntityItem(entityGroup2
/* 237 */                 .getEntityType() + vector1.elementAt(b1));
/* 238 */             if (eANList3.get(entityItem2.getKey()) == null) {
/* 239 */               println("<br /><LI>" + entityItem2.toString());
/* 240 */               eANList3.put((EANObject)entityItem2);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 245 */         println("EntityGroup VAR is null\n");
/* 246 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 250 */       log("ALWRCVARABR001 checking CDG");
/* 251 */       entityGroup = this.m_elist.getEntityGroup("CDG");
/* 252 */       EntityItem entityItem = null;
/* 253 */       if (entityGroup != null) {
/*     */         
/* 255 */         Vector<Integer> vector = getChildrenEntityIds(this.m_ei
/* 256 */             .getEntityType(), this.m_ei
/* 257 */             .getEntityID(), "CDG", "CVARCDG");
/*     */ 
/*     */         
/* 260 */         if (vector.size() <= 0) {
/* 261 */           println("<br /><font color=red>Failed. There is no CDG</font>");
/* 262 */           setReturnCode(-1);
/* 263 */         } else if (vector.size() > 1) {
/* 264 */           println("<br /><font color=red>Failed. There is not one and only one CDG.</font>");
/* 265 */           setReturnCode(-1);
/*     */         } else {
/* 267 */           int i = ((Integer)vector.elementAt(0)).intValue();
/* 268 */           entityItem = entityGroup.getEntityItem("CDG" + i);
/*     */         } 
/*     */         
/* 271 */         println("<br/></br/><b>Country Designator Group(s):</b>");
/* 272 */         for (byte b = 0; b < vector.size(); b++) {
/* 273 */           int i = ((Integer)vector.elementAt(b)).intValue();
/* 274 */           EntityItem entityItem1 = entityGroup.getEntityItem("CDG" + i);
/* 275 */           println("</br/><LI> " + entityItem1.toString());
/*     */         } 
/*     */       } else {
/* 278 */         println("EntityGroup CDG is null\n");
/* 279 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 283 */       log("ALWRCVARABR001 checking CD");
/* 284 */       if (entityItem != null) {
/* 285 */         EntityGroup entityGroup1 = this.m_elist.getEntityGroup("CD");
/*     */         
/* 287 */         Vector<Integer> vector = getChildrenEntityIds(entityItem
/* 288 */             .getEntityType(), entityItem
/* 289 */             .getEntityID(), "CD", "CDGCD");
/*     */ 
/*     */         
/* 292 */         if (vector.size() <= 0) {
/* 293 */           println("</br/><font color=red>Failed. No CD exists in the CDG.</font>");
/* 294 */           println("</br/>" + entityItem.toString());
/* 295 */           setReturnCode(-1);
/*     */         } else {
/* 297 */           for (byte b = 0; b < vector.size(); b++) {
/* 298 */             int i = ((Integer)vector.elementAt(b)).intValue();
/* 299 */             EntityItem entityItem1 = entityGroup1.getEntityItem("CD" + i);
/*     */             
/* 301 */             String str = getAttributeValue(this.m_elist, entityItem1
/*     */                 
/* 303 */                 .getEntityType(), entityItem1
/* 304 */                 .getEntityID(), "COUNTRY_DESIG");
/*     */             
/* 306 */             if (str == null || str.length() <= 0) {
/* 307 */               println("</br/><font color=red>Failed. The attribute COUNTRY_DESIG is not populated.</font>");
/* 308 */               println("</br/>" + entityItem1.toString());
/* 309 */               setReturnCode(-1);
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
/* 322 */       String str1 = getAttributeFlagEnabledValue(this.m_elist, this.m_ei.getEntityType(), this.m_ei.getEntityID(), "STATUS_CVAR").trim();
/* 323 */       if (!str1.equals(FINAL) && !str1.equals(REVIEW)) {
/* 324 */         println("</br/><font color=red>Failed. the CVAR is not in Ready for Review or Final status. </font>");
/* 325 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 329 */       if (getReturnCode() == 0) {
/* 330 */         log("ALWRCVARABR001 generating data");
/* 331 */         ALWRCVARABR001PDG aLWRCVARABR001PDG = new ALWRCVARABR001PDG(null, this.m_db, this.m_prof, "ALWRCVARABR001PDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 337 */         aLWRCVARABR001PDG.setEntityItem(this.m_ei);
/* 338 */         aLWRCVARABR001PDG.setABReList(this.m_elist);
/* 339 */         aLWRCVARABR001PDG.executeAction(this.m_db, this.m_prof);
/* 340 */         StringBuffer stringBuffer = aLWRCVARABR001PDG.getActivities();
/* 341 */         println("</br></br/><b>Generated Data:</b>");
/* 342 */         println("<br/>" + stringBuffer.toString());
/* 343 */         log("ALWRCVARABR001 finish generating data");
/*     */ 
/*     */ 
/*     */         
/* 347 */         println("</br><br/><b>Relators: </b>");
/* 348 */         EANList eANList = aLWRCVARABR001PDG.getSavedEIList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 354 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, this.m_abri.getVEName()); byte b;
/* 355 */         for (b = 0; b < eANList.size(); b++) {
/* 356 */           EntityItem entityItem1 = (EntityItem)eANList.getAt(b);
/* 357 */           println(pullResultReport(entityItem1, extractActionItem));
/*     */         } 
/*     */ 
/*     */         
/* 361 */         Vector<String> vector = new Vector();
/* 362 */         for (b = 0; b < eANList.size(); b++) {
/* 363 */           EntityItem entityItem1 = (EntityItem)eANList.getAt(b);
/*     */           
/* 365 */           for (byte b1 = 0; b1 < entityItem1.getAttributeCount(); b1++) {
/* 366 */             EANAttribute eANAttribute = entityItem1.getAttribute(b1);
/* 367 */             EANMetaAttribute eANMetaAttribute = eANAttribute.getMetaAttribute();
/* 368 */             if (eANAttribute instanceof COM.ibm.eannounce.objects.TextAttribute) {
/* 369 */               if (eANMetaAttribute.isUnique()) {
/* 370 */                 if (eANMetaAttribute.getUniqueClass().equals("LEVEL1")) {
/* 371 */                   vector.addElement(eANAttribute
/* 372 */                       .toString() + eANMetaAttribute.getKey());
/* 373 */                 } else if (eANMetaAttribute
/* 374 */                   .getUniqueClass().equals("LEVEL2")) {
/* 375 */                   vector.addElement(eANAttribute
/* 376 */                       .toString() + eANMetaAttribute
/* 377 */                       .getKey() + entityItem1
/* 378 */                       .getEntityType());
/*     */                 } 
/* 380 */               } else if (eANMetaAttribute.isComboUnique()) {
/* 381 */                 Vector vector1 = eANMetaAttribute.getComboUniqueAttributeCode();
/*     */                 
/* 383 */                 for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 384 */                   String str = eANMetaAttribute.getComboUniqueAttributeCode(b2);
/* 385 */                   EANAttribute eANAttribute1 = entityItem1.getAttribute(str);
/* 386 */                   if (eANAttribute1 instanceof EANFlagAttribute) {
/* 387 */                     String str5 = ((EANFlagAttribute)eANAttribute1).getFlagCodes();
/*     */ 
/*     */                     
/* 390 */                     StringTokenizer stringTokenizer = new StringTokenizer(str5, ":");
/*     */                     
/* 392 */                     while (stringTokenizer.hasMoreTokens()) {
/* 393 */                       String str6 = stringTokenizer.nextToken();
/* 394 */                       vector.addElement(eANAttribute
/* 395 */                           .toString() + ":" + eANMetaAttribute
/* 396 */                           .getKey() + ":" + str6 + ":" + str);
/*     */                     }
/*     */                   
/*     */                   }
/* 400 */                   else if (eANAttribute1 instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/* 401 */                     vector.addElement(eANAttribute
/* 402 */                         .toString() + ":" + eANMetaAttribute
/* 403 */                         .getKey() + ":" + eANAttribute1
/* 404 */                         .toString() + ":" + str);
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               } 
/* 409 */             } else if (eANAttribute instanceof COM.ibm.eannounce.objects.SingleFlagAttribute) {
/* 410 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 411 */               if (eANMetaAttribute.isUnique()) {
/* 412 */                 if (eANMetaAttribute.getUniqueClass().equals("EACHECK")) {
/* 413 */                   vector.addElement(eANAttribute
/* 414 */                       .toString() + eANMetaAttribute
/* 415 */                       .getKey() + entityItem1
/* 416 */                       .getEntityType());
/*     */                 }
/* 418 */               } else if (eANMetaAttribute.isComboUnique()) {
/* 419 */                 Vector vector1 = eANMetaAttribute.getComboUniqueAttributeCode();
/* 420 */                 for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 421 */                   String str = eANMetaAttribute.getComboUniqueAttributeCode(b2);
/* 422 */                   EANAttribute eANAttribute1 = entityItem1.getAttribute(str);
/* 423 */                   if (eANAttribute1 instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/* 424 */                     String str5 = eANAttribute1.toString();
/* 425 */                     vector.addElement(eANFlagAttribute
/* 426 */                         .getFlagCodes() + ":" + eANMetaAttribute
/* 427 */                         .getKey() + ":" + str5 + ":" + str);
/*     */                   
/*     */                   }
/* 430 */                   else if (eANAttribute1 instanceof EANFlagAttribute) {
/* 431 */                     String str5 = ((EANFlagAttribute)eANAttribute1).getFlagCodes();
/*     */ 
/*     */                     
/* 434 */                     StringTokenizer stringTokenizer = new StringTokenizer(str5, ":");
/*     */                     
/* 436 */                     while (stringTokenizer.hasMoreTokens()) {
/* 437 */                       String str6 = stringTokenizer.nextToken();
/* 438 */                       vector.addElement(eANAttribute
/* 439 */                           .toString() + ":" + eANMetaAttribute
/* 440 */                           .getKey() + ":" + str6 + ":" + str);
/*     */                     }
/*     */                   
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               } 
/* 447 */             } else if (eANAttribute instanceof COM.ibm.eannounce.objects.MultiFlagAttribute) {
/* 448 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 449 */               if (eANMetaAttribute.isComboUnique()) {
/* 450 */                 Vector vector1 = eANMetaAttribute.getComboUniqueAttributeCode();
/* 451 */                 for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 452 */                   String str = eANMetaAttribute.getComboUniqueAttributeCode(b2);
/* 453 */                   EANAttribute eANAttribute1 = entityItem1.getAttribute(str);
/* 454 */                   if (eANAttribute1 instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/* 455 */                     String str5 = eANAttribute1.toString();
/* 456 */                     vector.addElement(eANFlagAttribute
/* 457 */                         .getFlagCodes() + ":" + eANMetaAttribute
/* 458 */                         .getKey() + ":" + str5 + ":" + str);
/*     */                   
/*     */                   }
/* 461 */                   else if (eANAttribute1 instanceof EANFlagAttribute) {
/* 462 */                     String str5 = ((EANFlagAttribute)eANAttribute1).getFlagCodes();
/*     */ 
/*     */                     
/* 465 */                     StringTokenizer stringTokenizer = new StringTokenizer(str5, ":");
/*     */                     
/* 467 */                     while (stringTokenizer.hasMoreTokens()) {
/* 468 */                       String str6 = stringTokenizer.nextToken();
/* 469 */                       vector.addElement(eANAttribute
/* 470 */                           .toString() + ":" + eANMetaAttribute
/* 471 */                           .getKey() + ":" + str6 + ":" + str);
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
/* 482 */         for (b = 0; b < vector.size(); b++) {
/* 483 */           String str = vector.elementAt(b);
/* 484 */           PartNo.remove(this.m_db, str);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 489 */       println("<br /><br /><b>" + 
/*     */           
/* 491 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 494 */               getABRDescription(), 
/* 495 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 498 */       log(
/* 499 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 502 */               getABRDescription(), 
/* 503 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 504 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 505 */       setReturnCode(-2);
/* 506 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 510 */           .getMessage() + "</font></h3>");
/*     */       
/* 512 */       logError(lockPDHEntityException.getMessage());
/* 513 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 514 */       setReturnCode(-2);
/* 515 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 517 */           .getMessage() + "</font></h3>");
/*     */       
/* 519 */       logError(updatePDHEntityException.getMessage());
/* 520 */     } catch (SBRException sBRException) {
/* 521 */       setReturnCode(-2);
/* 522 */       println("<h3><font color=red>Generate Data error: " + sBRException
/*     */           
/* 524 */           .toString() + "</font></h3>");
/*     */       
/* 526 */       logError(sBRException.toString());
/* 527 */     } catch (Exception exception) {
/*     */       
/* 529 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 530 */       println("" + exception);
/* 531 */       exception.printStackTrace();
/*     */       
/* 533 */       if (getABRReturnCode() != -2) {
/* 534 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 538 */       String str1 = getABREntityDesc(this.m_ei.getEntityType(), this.m_ei.getEntityID());
/* 539 */       String str2 = getMetaAttributeDescription(this.m_ei, ABR);
/*     */       
/* 541 */       String str3 = str2 + " for " + str1;
/* 542 */       if (str3.length() > 64) {
/* 543 */         str3 = str3.substring(0, 64);
/*     */       }
/*     */       
/* 546 */       setDGTitle(str3);
/*     */       
/* 548 */       printALWRInfo();
/*     */ 
/*     */       
/* 551 */       setDGString(getABRReturnCode());
/* 552 */       setDGRptName(ABR);
/* 553 */       setDGRptClass("ALWRCVAR");
/* 554 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 558 */       buildReportFooter();
/*     */       
/* 560 */       if (!isReadOnly()) {
/* 561 */         clearSoftLock();
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
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 574 */     return getAttributeValue(this.m_elist, paramString, paramInt, "PNUMB_CT") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 580 */       getAttributeValue(this.m_elist, paramString, paramInt, "GENAREANAMEREGION") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 586 */       getAttributeValue(this.m_elist, paramString, paramInt, "GENAREANAME") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 592 */       getAttributeValue(this.m_elist, paramString, paramInt, "TARGANNDATE_CVAR") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 598 */       getAttributeValue(this.m_elist, paramString, paramInt, "NAME");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 607 */     return "The purpose of this ABR is to create one or more Country Variant Solutions based on an existing Country Variant Solution (CVAR).  A CVAR is generated for each Country Designator (CD)/Country combination in the related Country Designator Group (CDG) and the part number is determined by the CD.";
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
/* 618 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 628 */     return new String("$Revision: 1.28 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 638 */     return "$Id: ALWRCVARABR001.java,v 1.28 2010/07/12 21:35:09 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 646 */     return "ALWRCVARABR001.java,v 1.2";
/*     */   }
/*     */ 
/*     */   
/*     */   private void printALWRInfo() {
/* 651 */     String str1 = getABREntityDesc(this.m_ei.getEntityType(), this.m_ei.getEntityID());
/* 652 */     String str2 = getMetaAttributeDescription(this.m_ei, ABR);
/*     */ 
/*     */     
/* 655 */     println("<br /><b>NAME = </b>" + str2 + " for " + str1);
/*     */     
/* 657 */     println("<br /><b> RPTNAME = </b>" + ABR);
/*     */     
/* 659 */     println("<br /><b> TASKSTATUS = </b>" + (
/*     */         
/* 661 */         (getReturnCode() == 0) ? "Passed" : "Failed"));
/*     */     
/* 663 */     println("<br/><b>PDH Domain = </b>" + 
/*     */         
/* 665 */         getAttributeValue(this.m_elist, this.m_ei
/*     */           
/* 667 */           .getEntityType(), this.m_ei
/* 668 */           .getEntityID(), "PDHDOMAIN"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String pullResultReport(EntityItem paramEntityItem, ExtractActionItem paramExtractActionItem) {
/* 676 */     log("ALWRCSOLABR001 pullResultReport");
/*     */     
/* 678 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     try {
/* 680 */       this.m_prof = refreshProfValOnEffOn(this.m_prof);
/* 681 */       EntityItem[] arrayOfEntityItem = { paramEntityItem };
/*     */       
/* 683 */       EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, paramExtractActionItem, arrayOfEntityItem);
/*     */       
/* 685 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/* 686 */       paramEntityItem = entityGroup.getEntityItem(paramEntityItem.getKey());
/*     */       byte b;
/* 688 */       for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 689 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getUpLink(b);
/* 690 */         EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 691 */         EntityItem entityItem3 = (EntityItem)entityItem1.getDownLink(0);
/* 692 */         stringBuffer.append("<br />Relator: " + entityItem1
/*     */             
/* 694 */             .getKey() + ", Parent: " + entityItem2
/*     */             
/* 696 */             .getKey() + ", Child: " + entityItem3
/*     */             
/* 698 */             .getKey());
/*     */       } 
/*     */       
/* 701 */       for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 702 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 703 */         EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 704 */         EntityItem entityItem3 = (EntityItem)entityItem1.getDownLink(0);
/* 705 */         stringBuffer.append("<br />Relator: " + entityItem1
/*     */             
/* 707 */             .getKey() + ", Parent: " + entityItem2
/*     */             
/* 709 */             .getKey() + ", Child: " + entityItem3
/*     */             
/* 711 */             .getKey());
/*     */       } 
/* 713 */     } catch (Exception exception) {
/* 714 */       exception.printStackTrace();
/*     */     } 
/* 716 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ALWRCVARABR001.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */