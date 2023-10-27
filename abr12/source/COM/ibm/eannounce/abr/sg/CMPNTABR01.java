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
/*     */ public class CMPNTABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.";
/*     */   public static final String ABR = "CMPNTABR01";
/*     */   public static final String SOF = "SOF";
/*     */   public static final String CMPNTSTATUS = "CMPNTSTATUS";
/*     */   public static final String OFDISTATUS = "OFDISTATUS";
/*     */   public static final String OFWWSDSTATUS = "OFWWSDSTATUS";
/*     */   public static final String OFDCMSTATUS = "OFDCMSTATUS";
/*     */   public static final String OFMKTGSTATUS = "OFMKTGSTATUS";
/*     */   public static final String ABRSET = "ABRSET";
/*     */   public static final String DEF_NOT_POPULATED_HTML = "** Not Populated **";
/* 102 */   private final String m_strReadyForReviewValue = "114";
/*     */   
/* 104 */   private final String m_strFinalValue = "112";
/*     */ 
/*     */   
/* 107 */   private EntityGroup m_egParent = null;
/*     */   
/* 109 */   private EntityItem m_eiParent = null;
/*     */   
/* 111 */   private ControlBlock m_cbOn = null;
/* 112 */   private String m_strNow = null;
/* 113 */   private String m_strForever = null;
/*     */ 
/*     */ 
/*     */ 
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
/* 127 */     String str1 = null;
/* 128 */     String str2 = null;
/* 129 */     String str3 = null;
/* 130 */     String str4 = null;
/* 131 */     String str5 = null;
/* 132 */     String str6 = null;
/* 133 */     String str7 = null;
/* 134 */     String str8 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 140 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 144 */       buildRptHeader();
/*     */       
/* 146 */       setControlBlock();
/* 147 */       setReturnCode(0);
/*     */ 
/*     */       
/* 150 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 151 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 153 */       setDGTitle(setDGName(this.m_eiParent, "CMPNTABR01"));
/*     */       
/* 155 */       logMessage("************ Root Entity Type and id " + 
/*     */           
/* 157 */           getEntityType() + ":" + 
/*     */           
/* 159 */           getEntityID());
/*     */       
/* 161 */       if (this.m_egParent == null) {
/* 162 */         logMessage("**********Service Offering Not found");
/* 163 */         setReturnCode(-1);
/*     */       }
/*     */       else {
/*     */         
/* 167 */         EntityGroup entityGroup = this.m_eiParent.getEntityGroup();
/* 168 */         logMessage("***CMPNT true***" + entityGroup.dump(true));
/*     */         
/* 170 */         EANAttribute eANAttribute1 = this.m_eiParent.getAttribute("ABRSET");
/*     */ 
/*     */         
/* 173 */         str1 = (eANAttribute1 != null) ? eANAttribute1.toString() : "** Not Populated **";
/*     */         
/* 175 */         logMessage("***abrsetStatusvalue***: " + this.m_eiParent
/*     */             
/* 177 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 179 */             .getEntityID() + ":" + str1);
/*     */ 
/*     */ 
/*     */         
/* 183 */         str2 = getFlagCode(this.m_eiParent, "ABRSET");
/* 184 */         logMessage("****abrsetStatus:" + this.m_eiParent
/*     */             
/* 186 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 188 */             .getEntityID() + ":" + str2);
/*     */ 
/*     */ 
/*     */         
/* 192 */         EANAttribute eANAttribute2 = this.m_eiParent.getAttribute("CMPNTSTATUS");
/*     */         
/* 194 */         str3 = (eANAttribute2 != null) ? eANAttribute2.toString() : "** Not Populated **";
/* 195 */         logMessage("***strStatusvalue***: " + this.m_eiParent
/*     */             
/* 197 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 199 */             .getEntityID() + ":" + str3);
/*     */ 
/*     */ 
/*     */         
/* 203 */         EANAttribute eANAttribute3 = this.m_eiParent.getAttribute("OFDISTATUS");
/*     */         
/* 205 */         str4 = (eANAttribute3 != null) ? eANAttribute3.toString() : "** Not Populated **";
/* 206 */         logMessage("***strStatusvalue1***: " + this.m_eiParent
/*     */             
/* 208 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 210 */             .getEntityID() + ":" + str4);
/*     */ 
/*     */ 
/*     */         
/* 214 */         EANAttribute eANAttribute4 = this.m_eiParent.getAttribute("OFWWSDSTATUS");
/*     */         
/* 216 */         str5 = (eANAttribute4 != null) ? eANAttribute4.toString() : "** Not Populated **";
/* 217 */         logMessage("***strStatusvalue2***: " + this.m_eiParent
/*     */             
/* 219 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 221 */             .getEntityID() + ":" + str5);
/*     */ 
/*     */ 
/*     */         
/* 225 */         EANAttribute eANAttribute5 = this.m_eiParent.getAttribute("OFDCMSTATUS");
/*     */         
/* 227 */         str6 = (eANAttribute5 != null) ? eANAttribute5.toString() : "** Not Populated **";
/* 228 */         logMessage("***strStatusvalue3***: " + this.m_eiParent
/*     */             
/* 230 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 232 */             .getEntityID() + ":" + str6);
/*     */ 
/*     */ 
/*     */         
/* 236 */         EANAttribute eANAttribute6 = this.m_eiParent.getAttribute("OFMKTGSTATUS");
/*     */         
/* 238 */         str7 = (eANAttribute6 != null) ? eANAttribute6.toString() : "** Not Populated **";
/* 239 */         logMessage("***strStatusvalue4***: " + this.m_eiParent
/*     */             
/* 241 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 243 */             .getEntityID() + ":" + str7);
/*     */ 
/*     */ 
/*     */         
/* 247 */         str8 = getFlagCode(this.m_eiParent, "CMPNTSTATUS");
/* 248 */         logMessage("****strStatus:" + this.m_eiParent
/*     */             
/* 250 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 252 */             .getEntityID() + ":" + str8);
/*     */ 
/*     */         
/* 255 */         String str9 = getFlagCode(this.m_eiParent, "OFDISTATUS");
/* 256 */         logMessage("****strStatus1:" + this.m_eiParent
/*     */             
/* 258 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 260 */             .getEntityID() + ":" + str9);
/*     */ 
/*     */         
/* 263 */         String str10 = getFlagCode(this.m_eiParent, "OFWWSDSTATUS");
/* 264 */         logMessage("****strStatus2:" + this.m_eiParent
/*     */             
/* 266 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 268 */             .getEntityID() + ":" + str10);
/*     */ 
/*     */         
/* 271 */         String str11 = getFlagCode(this.m_eiParent, "OFDCMSTATUS");
/* 272 */         logMessage("****strStatus3:" + this.m_eiParent
/*     */             
/* 274 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 276 */             .getEntityID() + ":" + str11);
/*     */ 
/*     */         
/* 279 */         String str12 = getFlagCode(this.m_eiParent, "OFMKTGSTATUS");
/* 280 */         logMessage("****strStatus4:" + this.m_eiParent
/*     */             
/* 282 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 284 */             .getEntityID() + ":" + str12);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 289 */         if (str2.equals("0020")) {
/* 290 */           logMessage("We are gettin here abrset 0020:" + eANAttribute1 + ": " + str2);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 295 */           if ((str8.equals("111") || str8
/* 296 */             .equals("114") || str8
/* 297 */             .equals("116")) && str9
/* 298 */             .equals("112") && str10
/* 299 */             .equals("112") && str11
/* 300 */             .equals("112") && str12
/* 301 */             .equals("112")) {
/* 302 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 305 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 307 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 310 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 311 */             println("<br /><br /><b>Offering Life Cycle Status: " + str3 + "</b>" + str8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 317 */             println("<br /><br /><b>Offering DI Status: " + str4 + "</b>" + str9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 323 */             println("<br /><br /><b>Offering WWSD Status: " + str5 + "</b>" + str10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 329 */             println("<br /><br /><b>Offering DCM Status: " + str6 + "</b>" + str11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 335 */             println("<br /><br /><b>Offering MKTG Status: " + str7 + "</b>" + str12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 341 */             setFinalValue("CMPNTSTATUS");
/* 342 */             setReturnCode(0);
/*     */           } else {
/*     */             
/* 345 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 348 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 350 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 353 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 354 */             println("<br /><br /><b>OFroleSTATUS is not set to the correct value.</b>");
/* 355 */             println("<br /><br /><b>Offering Life Cycle Status: " + str3 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 360 */             println("<br /><br /><b>Offering DI Status: " + str4 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 365 */             println("<br /><br /><b>Offering WWSD Status: " + str5 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 370 */             println("<br /><br /><b>Offering DCM Status: " + str6 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 375 */             println("<br /><br /><b>Offering MKTG Status: " + str7 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 380 */             setReturnCode(-1);
/*     */           }
/*     */         
/*     */         }
/* 384 */         else if (str2.equals("0010")) {
/* 385 */           logMessage("We are gettin here abrset 0010:" + eANAttribute1 + ": " + str2);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 390 */           if ((str8.equals("111") || str8.equals("115")) && (str9
/* 391 */             .equals("114") || str9.equals("112")) && (str10
/* 392 */             .equals("114") || str10.equals("112")) && (str11
/* 393 */             .equals("114") || str11.equals("112")) && (str12
/* 394 */             .equals("114") || str12
/* 395 */             .equals("112"))) {
/* 396 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 399 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 401 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 404 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 405 */             println("<br /><br /><b>Offering Life Cycle Status: " + str3 + "</b>" + str8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 411 */             println("<br /><br /><b>Offering DI Status: " + str4 + "</b>" + str9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 417 */             println("<br /><br /><b>Offering WWSD Status: " + str5 + "</b>" + str10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 423 */             println("<br /><br /><b>Offering DCM Status: " + str6 + "</b>" + str11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 429 */             println("<br /><br /><b>Offering MKTG Status: " + str7 + "</b>" + str12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 435 */             setReadyForReviewValue("CMPNTSTATUS");
/* 436 */             setReturnCode(0);
/*     */           } else {
/*     */             
/* 439 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 442 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 444 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 447 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 448 */             println("<br /><br /><b>OFroleSTATUS is not set to the correct value.</b>");
/* 449 */             println("<br /><br /><b>Offering Life Cycle Status: " + str3 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 454 */             println("<br /><br /><b>Offering DI Status: " + str4 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 459 */             println("<br /><br /><b>Offering WWSD Status: " + str5 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 464 */             println("<br /><br /><b>Offering DCM Status: " + str6 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 469 */             println("<br /><br /><b>Offering MKTG Status: " + str7 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 474 */             setReturnCode(-1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 479 */       println("<br /><b>" + 
/*     */           
/* 481 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 484 */               getABRDescription(), 
/* 485 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 488 */       log(
/* 489 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 492 */               getABRDescription(), 
/* 493 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 495 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 496 */       setReturnCode(-2);
/* 497 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 501 */           .getMessage() + "</h3>");
/*     */       
/* 503 */       logError(lockPDHEntityException.getMessage());
/* 504 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 505 */       setReturnCode(-2);
/* 506 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 508 */           .getMessage() + "</h3>");
/*     */       
/* 510 */       logError(updatePDHEntityException.getMessage());
/* 511 */     } catch (Exception exception) {
/*     */       
/* 513 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 514 */       println("" + exception);
/*     */ 
/*     */       
/* 517 */       if (getABRReturnCode() != -2) {
/* 518 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 522 */       setDGString(getABRReturnCode());
/* 523 */       setDGRptName("CMPNTABR01");
/* 524 */       setDGRptClass("CMPNTABR01");
/*     */       
/* 526 */       if (!isReadOnly()) {
/* 527 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 532 */     printDGSubmitString();
/* 533 */     println(EACustom.getTOUDiv());
/* 534 */     buildReportFooter();
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
/* 545 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 555 */     return "<br /><br />";
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
/* 566 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 575 */     return "1.6";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setControlBlock() {
/* 584 */     this.m_strNow = getNow();
/* 585 */     this.m_strForever = getForever();
/* 586 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 593 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
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
/* 606 */     if (paramString.equals("CMPNTSTATUS")) {
/* 607 */       logMessage("****** CMPNT Status set to: 114");
/*     */       
/* 609 */       str = "114";
/*     */     }
/*     */     else {
/*     */       
/* 613 */       str = null;
/*     */     } 
/*     */     
/* 616 */     if (str != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 623 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 627 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 634 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 639 */         Vector<SingleFlag> vector = new Vector();
/* 640 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 642 */         if (singleFlag != null) {
/* 643 */           vector.addElement(singleFlag);
/* 644 */           returnEntityKey.m_vctAttributes = vector;
/* 645 */           vector1.addElement(returnEntityKey);
/* 646 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 647 */           this.m_db.commit();
/*     */         } 
/* 649 */       } catch (MiddlewareException middlewareException) {
/* 650 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 651 */       } catch (Exception exception) {
/* 652 */         logMessage("setFlagValue: " + exception.getMessage());
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
/* 666 */     String str = null;
/*     */     
/* 668 */     if (paramString.equals("CMPNTSTATUS")) {
/* 669 */       logMessage("****** CMPNT Status set to: 112");
/* 670 */       str = "112";
/*     */     }
/*     */     else {
/*     */       
/* 674 */       str = null;
/*     */     } 
/*     */     
/* 677 */     if (str != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 684 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 688 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 695 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 700 */         Vector<SingleFlag> vector = new Vector();
/* 701 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 703 */         if (singleFlag != null) {
/* 704 */           vector.addElement(singleFlag);
/* 705 */           returnEntityKey.m_vctAttributes = vector;
/* 706 */           vector1.addElement(returnEntityKey);
/* 707 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 708 */           this.m_db.commit();
/*     */         } 
/* 710 */       } catch (MiddlewareException middlewareException) {
/* 711 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 712 */       } catch (Exception exception) {
/* 713 */         logMessage("setFlagValue: " + exception.getMessage());
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
/* 725 */     return "CMPNTABR01.java,v 1.6 2006/03/13 19:42:03 couto Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 735 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 743 */     String str1 = getVersion();
/* 744 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 745 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 752 */     println(EACustom.getDocTypeHtml());
/* 753 */     println("<head>");
/* 754 */     println(EACustom.getMetaTags("CMPNTABR01.java"));
/* 755 */     println(EACustom.getCSS());
/* 756 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 757 */     println("</head>");
/* 758 */     println("<body id=\"ibm-com\">");
/* 759 */     println(EACustom.getMastheadDiv());
/*     */     
/* 761 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/*     */     
/* 763 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 765 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 767 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 769 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 771 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 773 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private String dispNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 778 */     StringBuffer stringBuffer = new StringBuffer();
/* 779 */     stringBuffer.append("<br /><br /><table summary=\"Attributes\" width=\"100%\">\n");
/* 780 */     stringBuffer.append("<tr><th id=\"navAttr\" class=\"PsgLabel\" width=\"35%\">Navigation Attribute</th><th id=\"value\" class=\"PsgLabel\" width=\"65%\">Value</th></tr>");
/* 781 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 782 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 783 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 784 */       if (eANMetaAttribute.isNavigate()) {
/* 785 */         stringBuffer.append("<tr><td headers=\"navAttr\" class=\"PsgText\" width=\"35%\">" + eANMetaAttribute.getLongDescription() + "</td><td headers=\"value\" class=\"PsgText\" width=\"65%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       }
/*     */     } 
/* 788 */     stringBuffer.append("</table>");
/* 789 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CMPNTABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */