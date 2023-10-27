/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.ALWRCSOLABR002PDG;
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
/*     */ public class ALWRCSOLABR002
/*     */   extends PokBaseABR
/*     */ {
/* 116 */   public static final String ABR = new String("ALWRCSOLABR002");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public static final String FINAL = new String("0020");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public static final String REVIEW = new String("0040");
/*     */   
/* 128 */   private EntityGroup m_egParent = null;
/* 129 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 150 */       start_ABRBuild();
/*     */ 
/*     */       
/* 153 */       buildReportHeaderII();
/*     */       
/* 155 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 156 */       this.m_ei = this.m_egParent.getEntityItem(0);
/* 157 */       println("<br><b>Country Solution: " + this.m_ei.getKey() + "</b>");
/*     */       
/* 159 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*     */       
/* 161 */       setReturnCode(0);
/*     */ 
/*     */       
/* 164 */       println("<br/><b>Selected Countries = </b>" + 
/*     */           
/* 166 */           getAttributeValue(this.m_elist, this.m_ei
/*     */             
/* 168 */             .getEntityType(), this.m_ei
/* 169 */             .getEntityID(), "ALWRCTYALL"));
/*     */ 
/*     */ 
/*     */       
/* 173 */       println("<br/><b>General Area (Region) = </b>" + 
/*     */           
/* 175 */           getAttributeValue(this.m_elist, this.m_ei
/*     */             
/* 177 */             .getEntityType(), this.m_ei
/* 178 */             .getEntityID(), "GENAREANAMEREGION"));
/*     */ 
/*     */ 
/*     */       
/* 182 */       log("ALWRCSOLABR002 checking OF");
/* 183 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("OF");
/*     */       
/* 185 */       if (entityGroup != null) {
/*     */         
/* 187 */         Vector<Integer> vector = getParentEntityIds(this.m_ei
/* 188 */             .getEntityType(), this.m_ei
/* 189 */             .getEntityID(), "OF", "OFCSOL");
/*     */ 
/*     */         
/* 192 */         if (vector.size() <= 0) {
/* 193 */           println("<br /><font color=red>Failed. There is no OF.</font>");
/* 194 */           setReturnCode(-1);
/* 195 */         } else if (vector.size() > 1) {
/* 196 */           println("<br /><font color=red>Failed. There is not one and only one OF.</font>");
/* 197 */           setReturnCode(-1);
/*     */         } 
/*     */ 
/*     */         
/* 201 */         EANList eANList1 = new EANList();
/* 202 */         println("<br/></br/><b>Offering(s): </b>"); byte b;
/* 203 */         for (b = 0; b < vector.size(); b++) {
/* 204 */           int i = ((Integer)vector.elementAt(b)).intValue();
/* 205 */           EntityItem entityItem = entityGroup.getEntityItem("OF" + i);
/* 206 */           println("</br/><LI>" + entityItem.toString());
/* 207 */           eANList1.put((EANObject)entityItem);
/*     */         } 
/*     */ 
/*     */         
/* 211 */         EANList eANList2 = new EANList();
/* 212 */         EntityGroup entityGroup1 = this.m_elist.getEntityGroup("PR");
/* 213 */         println("<br/><br /><b>Project(s):</b>");
/* 214 */         for (b = 0; b < eANList1.size(); b++) {
/* 215 */           EntityItem entityItem = (EntityItem)eANList1.getAt(b);
/*     */           
/* 217 */           Vector<String> vector1 = getParentEntityIds(entityItem
/* 218 */               .getEntityType(), entityItem
/* 219 */               .getEntityID(), "PR", "PROF");
/*     */ 
/*     */           
/* 222 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*     */             
/* 224 */             EntityItem entityItem1 = entityGroup1.getEntityItem(entityGroup1
/* 225 */                 .getEntityType() + vector1.elementAt(b1));
/* 226 */             if (eANList2.get(entityItem1.getKey()) == null) {
/* 227 */               println("<br /><LI>" + entityItem1.toString());
/* 228 */               eANList2.put((EANObject)entityItem1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 234 */         EANList eANList3 = new EANList();
/* 235 */         EntityGroup entityGroup2 = this.m_elist.getEntityGroup("BR");
/* 236 */         println("<br/><br /><b>Brand(s):</b>");
/* 237 */         for (b = 0; b < eANList2.size(); b++) {
/* 238 */           EntityItem entityItem = (EntityItem)eANList2.getAt(b);
/*     */           
/* 240 */           Vector<String> vector1 = getChildrenEntityIds(entityItem
/* 241 */               .getEntityType(), entityItem
/* 242 */               .getEntityID(), "BR", "PRBRANDCODEA");
/*     */ 
/*     */           
/* 245 */           for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*     */             
/* 247 */             EntityItem entityItem1 = entityGroup2.getEntityItem(entityGroup2
/* 248 */                 .getEntityType() + vector1.elementAt(b1));
/* 249 */             if (eANList3.get(entityItem1.getKey()) == null) {
/* 250 */               println("<br /><LI>" + entityItem1.toString());
/* 251 */               eANList3.put((EANObject)entityItem1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 256 */         println("EntityGroup OF is null\n");
/* 257 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 261 */       log("ALWRCSOLABR002 checking CDG");
/* 262 */       entityGroup = this.m_elist.getEntityGroup("CDG");
/* 263 */       if (entityGroup != null) {
/*     */         
/* 265 */         Vector<Integer> vector = getChildrenEntityIds(this.m_ei
/* 266 */             .getEntityType(), this.m_ei
/* 267 */             .getEntityID(), "CDG", "CSOLCDG");
/*     */ 
/*     */         
/* 270 */         if (vector.size() > 0) {
/* 271 */           println("<br /><font color=red>Failed. There are CDGs linked to CSOL.</font>");
/* 272 */           setReturnCode(-1);
/* 273 */           println("<br/></br/><b>Country Designator Group(s):</b>");
/* 274 */           for (byte b = 0; b < vector.size(); b++) {
/* 275 */             int i = ((Integer)vector.elementAt(b)).intValue();
/* 276 */             EntityItem entityItem = entityGroup.getEntityItem("CDG" + i);
/* 277 */             println("</br/><LI> " + entityItem.toString());
/*     */           } 
/*     */         } 
/*     */       } else {
/* 281 */         println("EntityGroup CDG is null\n");
/* 282 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       String str1 = getAttributeFlagEnabledValue(this.m_elist, this.m_ei.getEntityType(), this.m_ei.getEntityID(), "CSOLSTATUS").trim();
/* 293 */       if (!str1.equals(FINAL) && !str1.equals(REVIEW)) {
/* 294 */         println("</br/><font color=red>Failed. The CSOL is not in Ready for Review or Final status. </font>");
/* 295 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 299 */       if (getReturnCode() == 0) {
/* 300 */         log("ALWRCSOLABR002 generating data");
/* 301 */         ALWRCSOLABR002PDG aLWRCSOLABR002PDG = new ALWRCSOLABR002PDG(null, this.m_db, this.m_prof, "ALWRCSOLABR002PDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 307 */         aLWRCSOLABR002PDG.setEntityItem(this.m_ei);
/* 308 */         aLWRCSOLABR002PDG.setABReList(this.m_elist);
/* 309 */         aLWRCSOLABR002PDG.executeAction(this.m_db, this.m_prof);
/* 310 */         StringBuffer stringBuffer = aLWRCSOLABR002PDG.getActivities();
/* 311 */         println("</br></br/><b>Generated Data:</b>");
/* 312 */         println("<br/>" + stringBuffer.toString());
/* 313 */         log("ALWRCSOLABR002 finish generating data");
/*     */ 
/*     */         
/* 316 */         println("</br><br/><b>Relators: </b>");
/* 317 */         EANList eANList = aLWRCSOLABR002PDG.getSavedEIList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 323 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, this.m_abri.getVEName()); byte b;
/* 324 */         for (b = 0; b < eANList.size(); b++) {
/* 325 */           EntityItem entityItem = (EntityItem)eANList.getAt(b);
/* 326 */           println(pullResultReport(entityItem, extractActionItem));
/*     */         } 
/*     */ 
/*     */         
/* 330 */         Vector<String> vector = new Vector();
/* 331 */         for (b = 0; b < eANList.size(); b++) {
/* 332 */           EntityItem entityItem = (EntityItem)eANList.getAt(b);
/*     */           
/* 334 */           for (byte b1 = 0; b1 < entityItem.getAttributeCount(); b1++) {
/* 335 */             EANAttribute eANAttribute = entityItem.getAttribute(b1);
/* 336 */             EANMetaAttribute eANMetaAttribute = eANAttribute.getMetaAttribute();
/* 337 */             if (eANAttribute instanceof COM.ibm.eannounce.objects.TextAttribute) {
/* 338 */               if (eANMetaAttribute.isUnique()) {
/* 339 */                 if (eANMetaAttribute.getUniqueClass().equals("LEVEL1")) {
/* 340 */                   vector.addElement(eANAttribute
/* 341 */                       .toString() + eANMetaAttribute.getKey());
/* 342 */                 } else if (eANMetaAttribute
/* 343 */                   .getUniqueClass().equals("LEVEL2")) {
/* 344 */                   vector.addElement(eANAttribute
/* 345 */                       .toString() + eANMetaAttribute
/* 346 */                       .getKey() + entityItem
/* 347 */                       .getEntityType());
/*     */                 } 
/* 349 */               } else if (eANMetaAttribute.isComboUnique()) {
/* 350 */                 Vector vector1 = eANMetaAttribute.getComboUniqueAttributeCode();
/*     */                 
/* 352 */                 for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 353 */                   String str = eANMetaAttribute.getComboUniqueAttributeCode(b2);
/* 354 */                   EANAttribute eANAttribute1 = entityItem.getAttribute(str);
/* 355 */                   if (eANAttribute1 instanceof EANFlagAttribute) {
/* 356 */                     String str5 = ((EANFlagAttribute)eANAttribute1).getFlagCodes();
/*     */ 
/*     */                     
/* 359 */                     StringTokenizer stringTokenizer = new StringTokenizer(str5, ":");
/*     */                     
/* 361 */                     while (stringTokenizer.hasMoreTokens()) {
/* 362 */                       String str6 = stringTokenizer.nextToken();
/* 363 */                       vector.addElement(eANAttribute
/* 364 */                           .toString() + ":" + eANMetaAttribute
/* 365 */                           .getKey() + ":" + str6 + ":" + str);
/*     */                     }
/*     */                   
/*     */                   }
/* 369 */                   else if (eANAttribute1 instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/* 370 */                     vector.addElement(eANAttribute
/* 371 */                         .toString() + ":" + eANMetaAttribute
/* 372 */                         .getKey() + ":" + eANAttribute1
/* 373 */                         .toString() + ":" + str);
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               } 
/* 378 */             } else if (eANAttribute instanceof COM.ibm.eannounce.objects.SingleFlagAttribute) {
/* 379 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 380 */               if (eANMetaAttribute.isUnique()) {
/* 381 */                 if (eANMetaAttribute.getUniqueClass().equals("EACHECK")) {
/* 382 */                   vector.addElement(eANAttribute
/* 383 */                       .toString() + eANMetaAttribute
/* 384 */                       .getKey() + entityItem
/* 385 */                       .getEntityType());
/*     */                 }
/* 387 */               } else if (eANMetaAttribute.isComboUnique()) {
/* 388 */                 Vector vector1 = eANMetaAttribute.getComboUniqueAttributeCode();
/* 389 */                 for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 390 */                   String str = eANMetaAttribute.getComboUniqueAttributeCode(b2);
/* 391 */                   EANAttribute eANAttribute1 = entityItem.getAttribute(str);
/* 392 */                   if (eANAttribute1 instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/* 393 */                     String str5 = eANAttribute1.toString();
/* 394 */                     vector.addElement(eANFlagAttribute
/* 395 */                         .getFlagCodes() + ":" + eANMetaAttribute
/* 396 */                         .getKey() + ":" + str5 + ":" + str);
/*     */                   
/*     */                   }
/* 399 */                   else if (eANAttribute1 instanceof EANFlagAttribute) {
/* 400 */                     String str5 = ((EANFlagAttribute)eANAttribute1).getFlagCodes();
/*     */ 
/*     */                     
/* 403 */                     StringTokenizer stringTokenizer = new StringTokenizer(str5, ":");
/*     */                     
/* 405 */                     while (stringTokenizer.hasMoreTokens()) {
/* 406 */                       String str6 = stringTokenizer.nextToken();
/* 407 */                       vector.addElement(eANAttribute
/* 408 */                           .toString() + ":" + eANMetaAttribute
/* 409 */                           .getKey() + ":" + str6 + ":" + str);
/*     */                     }
/*     */                   
/*     */                   }
/*     */                 
/*     */                 } 
/*     */               } 
/* 416 */             } else if (eANAttribute instanceof COM.ibm.eannounce.objects.MultiFlagAttribute) {
/* 417 */               EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 418 */               if (eANMetaAttribute.isComboUnique()) {
/* 419 */                 Vector vector1 = eANMetaAttribute.getComboUniqueAttributeCode();
/* 420 */                 for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 421 */                   String str = eANMetaAttribute.getComboUniqueAttributeCode(b2);
/* 422 */                   EANAttribute eANAttribute1 = entityItem.getAttribute(str);
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
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 451 */         for (b = 0; b < vector.size(); b++) {
/* 452 */           String str = vector.elementAt(b);
/* 453 */           PartNo.remove(this.m_db, str);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 458 */       println("<br /><b>" + 
/*     */           
/* 460 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 463 */               getABRDescription(), 
/* 464 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 467 */       log(
/* 468 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 471 */               getABRDescription(), 
/* 472 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 473 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 474 */       setReturnCode(-2);
/* 475 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 479 */           .getMessage() + "</font></h3>");
/*     */       
/* 481 */       logError(lockPDHEntityException.getMessage());
/* 482 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 483 */       setReturnCode(-2);
/* 484 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 486 */           .getMessage() + "</font></h3>");
/*     */       
/* 488 */       logError(updatePDHEntityException.getMessage());
/* 489 */     } catch (SBRException sBRException) {
/* 490 */       setReturnCode(-2);
/* 491 */       println("<h3><font color=red>Generate Data error: " + sBRException
/*     */           
/* 493 */           .toString() + "</font></h3>");
/*     */       
/* 495 */       logError(sBRException.toString());
/* 496 */     } catch (Exception exception) {
/*     */       
/* 498 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 499 */       println("" + exception);
/* 500 */       exception.printStackTrace();
/*     */       
/* 502 */       if (getABRReturnCode() != -2) {
/* 503 */         setReturnCode(-3);
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 508 */       String str1 = getABREntityDesc(this.m_ei.getEntityType(), this.m_ei.getEntityID());
/* 509 */       String str2 = getMetaAttributeDescription(this.m_ei, ABR);
/*     */       
/* 511 */       String str3 = str2 + " for " + str1;
/* 512 */       if (str3.length() > 64) {
/* 513 */         str3 = str3.substring(0, 64);
/*     */       }
/*     */       
/* 516 */       setDGTitle(str3);
/*     */       
/* 518 */       printALWRInfo();
/*     */ 
/*     */       
/* 521 */       setDGString(getABRReturnCode());
/* 522 */       setDGRptName(ABR);
/* 523 */       setDGRptClass("ALWRCSOL");
/* 524 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 528 */       buildReportFooter();
/*     */       
/* 530 */       if (!isReadOnly()) {
/* 531 */         clearSoftLock();
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
/* 544 */     return getAttributeValue(this.m_elist, paramString, paramInt, "PNUMB_CT") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 550 */       getAttributeValue(this.m_elist, paramString, paramInt, "GENAREANAMEREGION") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 556 */       getAttributeValue(this.m_elist, paramString, paramInt, "GENAREANAME") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 562 */       getAttributeValue(this.m_elist, paramString, paramInt, "TARG_ANN_DATE_CT") + ", " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 568 */       getAttributeValue(this.m_elist, paramString, paramInt, "NAME");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 577 */     return "The purpose of this ABR is to create one or more Country Solutions based on an existing Country Solution (CSOL).  A CSOL is generated for each Country selected in the originating CSOL and the part number is determined by the originating CSOL.";
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
/* 588 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 598 */     return new String("$Revision: 1.29 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 608 */     return "$Id: ALWRCSOLABR002.java,v 1.29 2010/07/12 21:35:09 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 616 */     return "ALWRCSOLABR002.java,v 1.2";
/*     */   }
/*     */ 
/*     */   
/*     */   private void printALWRInfo() {
/* 621 */     String str1 = getABREntityDesc(this.m_ei.getEntityType(), this.m_ei.getEntityID());
/* 622 */     String str2 = getMetaAttributeDescription(this.m_ei, ABR);
/*     */ 
/*     */     
/* 625 */     println("<br /><b>NAME = </b>" + str2 + " for " + str1);
/*     */     
/* 627 */     println("<br /><b> RPTNAME = </b>" + ABR);
/*     */     
/* 629 */     println("<br /><b> TASKSTATUS = </b>" + (
/*     */         
/* 631 */         (getReturnCode() == 0) ? "Passed" : "Failed"));
/*     */     
/* 633 */     println("<br /><b> PDH Domain = </b>" + 
/*     */         
/* 635 */         getAttributeValue(this.m_elist, this.m_ei
/*     */           
/* 637 */           .getEntityType(), this.m_ei
/* 638 */           .getEntityID(), "PDHDOMAIN"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String pullResultReport(EntityItem paramEntityItem, ExtractActionItem paramExtractActionItem) {
/* 646 */     log("ALWRCSOLABR001 pullResultReport");
/*     */     
/* 648 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     try {
/* 650 */       this.m_prof = refreshProfValOnEffOn(this.m_prof);
/* 651 */       EntityItem[] arrayOfEntityItem = { paramEntityItem };
/*     */       
/* 653 */       EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, paramExtractActionItem, arrayOfEntityItem);
/*     */       
/* 655 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/* 656 */       paramEntityItem = entityGroup.getEntityItem(paramEntityItem.getKey());
/*     */       byte b;
/* 658 */       for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 659 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getUpLink(b);
/* 660 */         EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 661 */         EntityItem entityItem3 = (EntityItem)entityItem1.getDownLink(0);
/* 662 */         stringBuffer.append("<br />Relator: " + entityItem1
/*     */             
/* 664 */             .getKey() + ", Parent: " + entityItem2
/*     */             
/* 666 */             .getKey() + ", Child: " + entityItem3
/*     */             
/* 668 */             .getKey());
/*     */       } 
/*     */       
/* 671 */       for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 672 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getDownLink(b);
/* 673 */         EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(0);
/* 674 */         EntityItem entityItem3 = (EntityItem)entityItem1.getDownLink(0);
/* 675 */         stringBuffer.append("<br />Relator: " + entityItem1
/*     */             
/* 677 */             .getKey() + ", Parent: " + entityItem2
/*     */             
/* 679 */             .getKey() + ", Child: " + entityItem3
/*     */             
/* 681 */             .getKey());
/*     */       } 
/* 683 */     } catch (Exception exception) {
/* 684 */       exception.printStackTrace();
/*     */     } 
/* 686 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ALWRCSOLABR002.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */