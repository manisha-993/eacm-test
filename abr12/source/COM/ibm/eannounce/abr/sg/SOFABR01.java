/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import java.sql.SQLException;
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
/*     */ public class SOFABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.";
/*     */   public static final String ABR = "SOFABR01";
/*     */   public static final String SOF = "SOF";
/*     */   public static final String SOFSTATUS = "SOFSTATUS";
/*     */   public static final String OFDISTATUS = "OFDISTATUS";
/*     */   public static final String OFWWSDSTATUS = "OFWWSDSTATUS";
/*     */   public static final String OFDCMSTATUS = "OFDCMSTATUS";
/*     */   public static final String OFMKTGSTATUS = "OFMKTGSTATUS";
/*     */   public static final String ABRSET = "ABRSET";
/*     */   public static final String DEF_NOT_POPULATED_HTML = "** Not Populated **";
/* 105 */   private final String m_strReadyForReviewValue = "114";
/*     */   
/* 107 */   private final String m_strFinalValue = "112";
/*     */ 
/*     */   
/* 110 */   private EntityGroup m_egParent = null;
/*     */   
/* 112 */   private EntityItem m_eiParent = null;
/*     */   
/* 114 */   private ControlBlock m_cbOn = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 123 */     String str1 = null;
/* 124 */     String str2 = null;
/* 125 */     String str3 = null;
/* 126 */     String str4 = null;
/* 127 */     String str5 = null;
/* 128 */     String str6 = null;
/* 129 */     EANAttribute eANAttribute1 = null;
/* 130 */     EANAttribute eANAttribute2 = null;
/* 131 */     EANAttribute eANAttribute3 = null;
/* 132 */     EANAttribute eANAttribute4 = null;
/* 133 */     String str7 = null;
/* 134 */     String str8 = null;
/* 135 */     String str9 = null;
/* 136 */     String str10 = null;
/* 137 */     String str11 = null;
/*     */     try {
/* 139 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 143 */       buildRptHeader();
/*     */       
/* 145 */       setControlBlock();
/* 146 */       setReturnCode(0);
/*     */ 
/*     */       
/* 149 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 150 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 152 */       setDGTitle(setDGName(this.m_eiParent, "SOFABR01"));
/*     */       
/* 154 */       logMessage("************ Root Entity Type and id " + 
/*     */           
/* 156 */           getEntityType() + ":" + 
/*     */           
/* 158 */           getEntityID());
/*     */       
/* 160 */       if (this.m_egParent == null) {
/* 161 */         logMessage("**********Service Offering Not found");
/* 162 */         setReturnCode(-1);
/*     */       }
/*     */       else {
/*     */         
/* 166 */         EntityGroup entityGroup = this.m_eiParent.getEntityGroup();
/*     */ 
/*     */         
/* 169 */         EANAttribute eANAttribute6 = this.m_eiParent.getAttribute("ABRSET");
/*     */         
/* 171 */         String str = (eANAttribute6 != null) ? eANAttribute6.toString() : "** Not Populated **";
/* 172 */         logMessage("***abrsetStatusvalue***: " + this.m_eiParent
/*     */             
/* 174 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 176 */             .getEntityID() + ":" + str);
/*     */ 
/*     */ 
/*     */         
/* 180 */         str1 = getFlagCode(this.m_eiParent, "ABRSET");
/* 181 */         logMessage("****abrsetStatus:" + this.m_eiParent
/*     */             
/* 183 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 185 */             .getEntityID() + ":" + str1);
/*     */ 
/*     */ 
/*     */         
/* 189 */         EANAttribute eANAttribute5 = this.m_eiParent.getAttribute("SOFSTATUS");
/*     */         
/* 191 */         str2 = (eANAttribute5 != null) ? eANAttribute5.toString() : "** Not Populated **";
/* 192 */         logMessage("***strStatusvalue***: " + this.m_eiParent
/*     */             
/* 194 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 196 */             .getEntityID() + ":" + str2);
/*     */ 
/*     */ 
/*     */         
/* 200 */         eANAttribute1 = this.m_eiParent.getAttribute("OFDISTATUS");
/*     */         
/* 202 */         str3 = (eANAttribute1 != null) ? eANAttribute1.toString() : "** Not Populated **";
/* 203 */         logMessage("***strStatusvalue1***: " + this.m_eiParent
/*     */             
/* 205 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 207 */             .getEntityID() + ":" + str3);
/*     */ 
/*     */ 
/*     */         
/* 211 */         eANAttribute2 = this.m_eiParent.getAttribute("OFWWSDSTATUS");
/*     */         
/* 213 */         str4 = (eANAttribute2 != null) ? eANAttribute2.toString() : "** Not Populated **";
/* 214 */         logMessage("***strStatusvalue2***: " + this.m_eiParent
/*     */             
/* 216 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 218 */             .getEntityID() + ":" + str4);
/*     */ 
/*     */ 
/*     */         
/* 222 */         eANAttribute3 = this.m_eiParent.getAttribute("OFDCMSTATUS");
/*     */         
/* 224 */         str5 = (eANAttribute3 != null) ? eANAttribute3.toString() : "** Not Populated **";
/* 225 */         logMessage("***strStatusvalue3***: " + this.m_eiParent
/*     */             
/* 227 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 229 */             .getEntityID() + ":" + str5);
/*     */ 
/*     */ 
/*     */         
/* 233 */         eANAttribute4 = this.m_eiParent.getAttribute("OFMKTGSTATUS");
/*     */         
/* 235 */         str6 = (eANAttribute4 != null) ? eANAttribute4.toString() : "** Not Populated **";
/* 236 */         logMessage("***strStatusvalue4***: " + this.m_eiParent
/*     */             
/* 238 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 240 */             .getEntityID() + ":" + str6);
/*     */ 
/*     */ 
/*     */         
/* 244 */         str7 = getFlagCode(this.m_eiParent, "SOFSTATUS");
/* 245 */         logMessage("****strStatus:" + this.m_eiParent
/*     */             
/* 247 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 249 */             .getEntityID() + ":" + str7);
/*     */ 
/*     */         
/* 252 */         str8 = getFlagCode(this.m_eiParent, "OFDISTATUS");
/* 253 */         logMessage("****strStatus1:" + this.m_eiParent
/*     */             
/* 255 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 257 */             .getEntityID() + ":" + str8);
/*     */ 
/*     */         
/* 260 */         str9 = getFlagCode(this.m_eiParent, "OFWWSDSTATUS");
/* 261 */         logMessage("****strStatus2:" + this.m_eiParent
/*     */             
/* 263 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 265 */             .getEntityID() + ":" + str9);
/*     */ 
/*     */         
/* 268 */         str10 = getFlagCode(this.m_eiParent, "OFDCMSTATUS");
/* 269 */         logMessage("****strStatus3:" + this.m_eiParent
/*     */             
/* 271 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 273 */             .getEntityID() + ":" + str10);
/*     */ 
/*     */         
/* 276 */         str11 = getFlagCode(this.m_eiParent, "OFMKTGSTATUS");
/* 277 */         logMessage("****strStatus4:" + this.m_eiParent
/*     */             
/* 279 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 281 */             .getEntityID() + ":" + str11);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 286 */         if (str1.equals("0020")) {
/* 287 */           logMessage("We are gettin here abrset 0020:" + eANAttribute6 + ": " + str1);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 292 */           if ((str7.equals("111") || str7
/* 293 */             .equals("114") || str7
/* 294 */             .equals("116")) && str8
/* 295 */             .equals("112") && str9
/* 296 */             .equals("112") && str10
/* 297 */             .equals("112") && str11
/* 298 */             .equals("112")) {
/* 299 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 302 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 304 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 307 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 308 */             println("<br /><br /><b>Offering Life Cycle Status: " + str2 + "</b>" + str7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 314 */             println("<br /><br /><b>Offering DI Status: " + str3 + "</b>" + str8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 320 */             println("<br /><br /><b>Offering WWSD Status: " + str4 + "</b>" + str9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 326 */             println("<br /><br /><b>Offering DCM Status: " + str5 + "</b>" + str10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 332 */             println("<br /><br /><b>Offering MKTG Status: " + str6 + "</b>" + str11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 338 */             setFinalValue("SOFSTATUS");
/* 339 */             setReturnCode(0);
/*     */           } else {
/*     */             
/* 342 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 345 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 347 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 350 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 351 */             println("<br /><br /><b>OFroleSTATUS is not set to the correct value.</b>");
/* 352 */             println("<br /><br /><b>Offering Life Cycle Status: " + str2 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 357 */             println("<br /><br /><b>Offering DI Status: " + str3 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 362 */             println("<br /><br /><b>Offering WWSD Status: " + str4 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 367 */             println("<br /><br /><b>Offering DCM Status: " + str5 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 372 */             println("<br /><br /><b>Offering MKTG Status: " + str6 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 377 */             setReturnCode(-1);
/*     */           }
/*     */         
/*     */         }
/* 381 */         else if (str1.equals("0010")) {
/* 382 */           logMessage("We are gettin here abrset 0010:" + eANAttribute6 + ": " + str1);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 387 */           if ((str7.equals("111") || str7.equals("115")) && (str8
/* 388 */             .equals("114") || str8.equals("112")) && (str9
/* 389 */             .equals("114") || str9.equals("112")) && (str10
/* 390 */             .equals("114") || str10.equals("112")) && (str11
/* 391 */             .equals("114") || str11
/* 392 */             .equals("112"))) {
/* 393 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 396 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 398 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 401 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 402 */             println("<br /><br /><b>Offering Life Cycle Status: " + str2 + "</b>" + str7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 408 */             println("<br /><br /><b>Offering DI Status: " + str3 + "</b>" + str8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 414 */             println("<br /><br /><b>Offering WWSD Status: " + str4 + "</b>" + str9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 420 */             println("<br /><br /><b>Offering DCM Status: " + str5 + "</b>" + str10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 426 */             println("<br /><br /><b>Offering MKTG Status: " + str6 + "</b>" + str11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 432 */             setReadyForReviewValue("SOFSTATUS");
/* 433 */             setReturnCode(0);
/*     */           } else {
/*     */             
/* 436 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 439 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 441 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 444 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 445 */             println("<br /><br /><b>OFroleSTATUS is not set to the correct value.</b>");
/* 446 */             println("<br /><br /><b>Offering Life Cycle Status: " + str2 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 451 */             println("<br /><br /><b>Offering DI Status: " + str3 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 456 */             println("<br /><br /><b>Offering WWSD Status: " + str4 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 461 */             println("<br /><br /><b>Offering DCM Status: " + str5 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 466 */             println("<br /><br /><b>Offering MKTG Status: " + str6 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 471 */             setReturnCode(-1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 476 */       println("<br /><b>" + 
/*     */           
/* 478 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 481 */               getABRDescription(), 
/* 482 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 485 */       log(
/* 486 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 489 */               getABRDescription(), 
/* 490 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 492 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 493 */       setReturnCode(-2);
/* 494 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 498 */           .getMessage() + "</h3>");
/*     */       
/* 500 */       logError(lockPDHEntityException.getMessage());
/* 501 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 502 */       setReturnCode(-2);
/* 503 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 505 */           .getMessage() + "</h3>");
/*     */       
/* 507 */       logError(updatePDHEntityException.getMessage());
/* 508 */     } catch (Exception exception) {
/*     */       
/* 510 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 511 */       println("" + exception);
/*     */ 
/*     */       
/* 514 */       if (getABRReturnCode() != -2) {
/* 515 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 519 */       setDGString(getABRReturnCode());
/* 520 */       setDGRptName("SOFABR01");
/* 521 */       setDGRptClass("SOFABR01");
/*     */       
/* 523 */       if (!isReadOnly()) {
/* 524 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 529 */     printDGSubmitString();
/* 530 */     println(EACustom.getTOUDiv());
/* 531 */     buildReportFooter();
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
/* 542 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 552 */     return "<br /><br />";
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
/* 563 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 572 */     return "1.8";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setControlBlock() {
/* 581 */     String str1 = null;
/* 582 */     String str2 = null;
/*     */     
/* 584 */     str1 = getNow();
/* 585 */     str2 = getForever();
/* 586 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 593 */       .m_cbOn = new ControlBlock(str1, str2, str1, str2, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadyForReviewValue(String paramString) {
/* 604 */     String str = null;
/*     */     
/* 606 */     if (paramString.equals("SOFSTATUS")) {
/* 607 */       logMessage("****** SOF Status set to: 114");
/* 608 */       str = "114";
/*     */     }
/*     */     else {
/*     */       
/* 612 */       str = null;
/*     */     } 
/*     */     
/* 615 */     if (str != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 622 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 626 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 633 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 638 */         Vector<SingleFlag> vector = new Vector();
/* 639 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 641 */         if (singleFlag != null) {
/* 642 */           vector.addElement(singleFlag);
/* 643 */           returnEntityKey.m_vctAttributes = vector;
/* 644 */           vector1.addElement(returnEntityKey);
/* 645 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 646 */           this.m_db.commit();
/*     */         } 
/* 648 */       } catch (MiddlewareException middlewareException) {
/* 649 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 650 */       } catch (Exception exception) {
/* 651 */         logMessage("setFlagValue: " + exception.getMessage());
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
/*     */   public void setFinalValue(String paramString) {
/* 665 */     String str = null;
/*     */     
/* 667 */     if (paramString.equals("SOFSTATUS")) {
/* 668 */       logMessage("****** SOF Status set to: 112");
/* 669 */       str = "112";
/*     */     }
/*     */     else {
/*     */       
/* 673 */       str = null;
/*     */     } 
/*     */     
/* 676 */     if (str != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 683 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 687 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 694 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 699 */         Vector<SingleFlag> vector = new Vector();
/* 700 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 702 */         if (singleFlag != null) {
/* 703 */           vector.addElement(singleFlag);
/* 704 */           returnEntityKey.m_vctAttributes = vector;
/* 705 */           vector1.addElement(returnEntityKey);
/* 706 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 707 */           this.m_db.commit();
/*     */         } 
/* 709 */       } catch (MiddlewareException middlewareException) {
/* 710 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 711 */       } catch (Exception exception) {
/* 712 */         logMessage("setFlagValue: " + exception.getMessage());
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
/*     */   public static String getVersion() {
/* 724 */     return "SOFABR01.java,v 1.8 2006/03/13 19:42:03 couto Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 734 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 742 */     String str1 = getVersion();
/* 743 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 744 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 751 */     println(EACustom.getDocTypeHtml());
/* 752 */     println("<head>");
/* 753 */     println(EACustom.getMetaTags("SOFABR01.java"));
/* 754 */     println(EACustom.getCSS());
/* 755 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 756 */     println("</head>");
/* 757 */     println("<body id=\"ibm-com\">");
/* 758 */     println(EACustom.getMastheadDiv());
/*     */     
/* 760 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/* 761 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 763 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 765 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 767 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 769 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 771 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private String dispNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 776 */     StringBuffer stringBuffer = new StringBuffer();
/* 777 */     stringBuffer.append("<br /><br /><table summary=\"Attributes\" width=\"100%\">\n");
/* 778 */     stringBuffer.append("<tr><th id=\"navAttr\" class=\"PsgLabel\" width=\"35%\">Navigation Attribute</th><th id=\"value\" class=\"PsgLabel\" width=\"65%\">Value</th></tr>");
/* 779 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 780 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 781 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 782 */       if (eANMetaAttribute.isNavigate()) {
/* 783 */         stringBuffer.append("<tr><td headers=\"navAttr\" class=\"PsgText\" width=\"35%\">" + eANMetaAttribute.getLongDescription() + "</td><td headers=\"value\" class=\"PsgText\" width=\"65%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       }
/*     */     } 
/* 786 */     stringBuffer.append("</table>");
/* 787 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SOFABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */