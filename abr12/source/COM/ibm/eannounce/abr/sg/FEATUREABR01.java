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
/*     */ public class FEATUREABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.";
/*     */   public static final String ABR = "FEATUREABR01";
/*     */   public static final String SOF = "SOF";
/*     */   public static final String FEATSTATUS = "FEATSTATUS";
/*     */   public static final String OFDISTATUS = "OFDISTATUS";
/*     */   public static final String OFWWSDSTATUS = "OFWWSDSTATUS";
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
/*     */   public void execute_run() {
/* 126 */     String str1 = null;
/* 127 */     String str2 = null;
/* 128 */     String str3 = null;
/* 129 */     String str4 = null;
/* 130 */     String str5 = null;
/* 131 */     String str6 = null;
/* 132 */     String str7 = null;
/* 133 */     String str8 = null;
/* 134 */     String str9 = null;
/* 135 */     String str10 = null;
/*     */     
/*     */     try {
/* 138 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 142 */       buildRptHeader();
/*     */       
/* 144 */       setControlBlock();
/* 145 */       setReturnCode(0);
/*     */ 
/*     */       
/* 148 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 149 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 151 */       setDGTitle(setDGName(this.m_eiParent, "FEATUREABR01"));
/*     */       
/* 153 */       logMessage("************ Root Entity Type and id " + 
/*     */           
/* 155 */           getEntityType() + ":" + 
/*     */           
/* 157 */           getEntityID());
/*     */       
/* 159 */       if (this.m_egParent == null) {
/* 160 */         logMessage("**********Service Offering Not found");
/* 161 */         setReturnCode(-1);
/*     */       }
/*     */       else {
/*     */         
/* 165 */         EntityGroup entityGroup = this.m_eiParent.getEntityGroup();
/* 166 */         logMessage("***SOF true***" + entityGroup.dump(true));
/*     */         
/* 168 */         EANAttribute eANAttribute1 = this.m_eiParent.getAttribute("ABRSET");
/*     */ 
/*     */         
/* 171 */         str1 = (eANAttribute1 != null) ? eANAttribute1.toString() : "** Not Populated **";
/*     */         
/* 173 */         logMessage("***abrsetStatusvalue***: " + this.m_eiParent
/*     */             
/* 175 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 177 */             .getEntityID() + ":" + str1);
/*     */ 
/*     */ 
/*     */         
/* 181 */         str2 = getFlagCode(this.m_eiParent, "ABRSET");
/* 182 */         logMessage("****abrsetStatus:" + this.m_eiParent
/*     */             
/* 184 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 186 */             .getEntityID() + ":" + str2);
/*     */ 
/*     */ 
/*     */         
/* 190 */         EANAttribute eANAttribute2 = this.m_eiParent.getAttribute("FEATSTATUS");
/*     */         
/* 192 */         str3 = (eANAttribute2 != null) ? eANAttribute2.toString() : "** Not Populated **";
/* 193 */         logMessage("***strStatusvalue***: " + this.m_eiParent
/*     */             
/* 195 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 197 */             .getEntityID() + ":" + str3);
/*     */ 
/*     */ 
/*     */         
/* 201 */         EANAttribute eANAttribute3 = this.m_eiParent.getAttribute("OFDISTATUS");
/*     */         
/* 203 */         str4 = (eANAttribute3 != null) ? eANAttribute3.toString() : "** Not Populated **";
/* 204 */         logMessage("***strStatusvalue1***: " + this.m_eiParent
/*     */             
/* 206 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 208 */             .getEntityID() + ":" + str4);
/*     */ 
/*     */ 
/*     */         
/* 212 */         EANAttribute eANAttribute4 = this.m_eiParent.getAttribute("OFWWSDSTATUS");
/*     */         
/* 214 */         str5 = (eANAttribute4 != null) ? eANAttribute4.toString() : "** Not Populated **";
/* 215 */         logMessage("***strStatusvalue2***: " + this.m_eiParent
/*     */             
/* 217 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 219 */             .getEntityID() + ":" + str5);
/*     */ 
/*     */ 
/*     */         
/* 223 */         EANAttribute eANAttribute5 = this.m_eiParent.getAttribute("OFMKTGSTATUS");
/*     */         
/* 225 */         str6 = (eANAttribute5 != null) ? eANAttribute5.toString() : "** Not Populated **";
/* 226 */         logMessage("***strStatusvalue4***: " + this.m_eiParent
/*     */             
/* 228 */             .getEntityType() + ":" + this.m_eiParent
/*     */             
/* 230 */             .getEntityID() + ":" + str6);
/*     */ 
/*     */ 
/*     */         
/* 234 */         str7 = getFlagCode(this.m_eiParent, "FEATSTATUS");
/* 235 */         logMessage("****strStatus:" + this.m_eiParent
/*     */             
/* 237 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 239 */             .getEntityID() + ":" + str7);
/*     */ 
/*     */         
/* 242 */         str8 = getFlagCode(this.m_eiParent, "OFDISTATUS");
/* 243 */         logMessage("****strStatus1:" + this.m_eiParent
/*     */             
/* 245 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 247 */             .getEntityID() + ":" + str8);
/*     */ 
/*     */         
/* 250 */         str9 = getFlagCode(this.m_eiParent, "OFWWSDSTATUS");
/* 251 */         logMessage("****strStatus2:" + this.m_eiParent
/*     */             
/* 253 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 255 */             .getEntityID() + ":" + str9);
/*     */ 
/*     */         
/* 258 */         str10 = getFlagCode(this.m_eiParent, "OFMKTGSTATUS");
/* 259 */         logMessage("****strStatus4:" + this.m_eiParent
/*     */             
/* 261 */             .getEntityType() + "-" + this.m_eiParent
/*     */             
/* 263 */             .getEntityID() + ":" + str10);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 268 */         if (str2.equals("0020")) {
/* 269 */           logMessage("We are gettin here abrset 0020:" + eANAttribute1 + ": " + str2);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 274 */           if ((str7.equals("111") || str7
/* 275 */             .equals("114") || str7
/* 276 */             .equals("116")) && str8
/* 277 */             .equals("112") && str9
/* 278 */             .equals("112") && str10
/* 279 */             .equals("112")) {
/* 280 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 283 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 285 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 288 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 289 */             println("<br /><br /><b>Offering Life Cycle Status: " + str3 + "</b>" + str7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 295 */             println("<br /><br /><b>Offering DI Status: " + str4 + "</b>" + str8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 301 */             println("<br /><br /><b>Offering WWSD Status: " + str5 + "</b>" + str9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 307 */             println("<br /><br /><b>Offering MKTG Status: " + str6 + "</b>" + str10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 313 */             setFinalValue("FEATSTATUS");
/* 314 */             setReturnCode(0);
/*     */           } else {
/*     */             
/* 317 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 320 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 322 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 325 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 326 */             println("<br /><br /><b>OFroleSTATUS is not set to the correct value.</b>");
/* 327 */             println("<br /><br /><b>Offering Life Cycle Status: " + str3 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 332 */             println("<br /><br /><b>Offering DI Status: " + str4 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 337 */             println("<br /><br /><b>Offering WWSD Status: " + str5 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 342 */             println("<br /><br /><b>Offering MKTG Status: " + str6 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 347 */             setReturnCode(-1);
/*     */           }
/*     */         
/*     */         }
/* 351 */         else if (str2.equals("0010")) {
/* 352 */           logMessage("We are gettin here abrset 0010:" + eANAttribute1 + ": " + str2);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 357 */           if ((str7.equals("111") || str7.equals("115")) && (str8
/* 358 */             .equals("114") || str8.equals("112")) && (str9
/* 359 */             .equals("114") || str9.equals("112")) && (str10
/* 360 */             .equals("114") || str10
/* 361 */             .equals("112"))) {
/* 362 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 365 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 367 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 370 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 371 */             println("<br /><br /><b>Offering Life Cycle Status: " + str3 + "</b>" + str7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 377 */             println("<br /><br /><b>Offering DI Status: " + str4 + "</b>" + str8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 383 */             println("<br /><br /><b>Offering WWSD Status: " + str5 + "</b>" + str9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 389 */             println("<br /><br /><b>Offering MKTG Status: " + str6 + "</b>" + str10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 395 */             setReadyForReviewValue("FEATSTATUS");
/* 396 */             setReturnCode(0);
/*     */           } else {
/*     */             
/* 399 */             println("<br /><br /><b>Entity: " + this.m_eiParent
/*     */ 
/*     */                 
/* 402 */                 .getEntityType() + "   EntityId: " + this.m_eiParent
/*     */                 
/* 404 */                 .getEntityID() + "</b>");
/*     */ 
/*     */             
/* 407 */             println(dispNavAttributes(this.m_eiParent, entityGroup));
/* 408 */             println("<br /><br /><b>OFroleSTATUS is not set to the correct value.</b>");
/* 409 */             println("<br /><br /><b>Offering Life Cycle Status: " + str3 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 414 */             println("<br /><br /><b>Offering DI Status: " + str4 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 419 */             println("<br /><br /><b>Offering WWSD Status: " + str5 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 424 */             println("<br /><br /><b>Offering MKTG Status: " + str6 + "</b>");
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 429 */             setReturnCode(-1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 434 */       println("<br /><b>" + 
/*     */           
/* 436 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 439 */               getABRDescription(), 
/* 440 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 443 */       log(
/* 444 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 447 */               getABRDescription(), 
/* 448 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 450 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 451 */       setReturnCode(-2);
/* 452 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 456 */           .getMessage() + "</h3>");
/*     */       
/* 458 */       logError(lockPDHEntityException.getMessage());
/* 459 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 460 */       setReturnCode(-2);
/* 461 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 463 */           .getMessage() + "</h3>");
/*     */       
/* 465 */       logError(updatePDHEntityException.getMessage());
/* 466 */     } catch (Exception exception) {
/*     */       
/* 468 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 469 */       println("" + exception);
/*     */ 
/*     */       
/* 472 */       if (getABRReturnCode() != -2) {
/* 473 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 477 */       setDGString(getABRReturnCode());
/* 478 */       setDGRptName("FEATUREABR01");
/* 479 */       setDGRptClass("FEATUREABR01");
/*     */       
/* 481 */       if (!isReadOnly()) {
/* 482 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 487 */     printDGSubmitString();
/* 488 */     println(EACustom.getTOUDiv());
/* 489 */     buildReportFooter();
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
/* 500 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 510 */     return "<br /><br />";
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
/* 521 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 530 */     return "1.5";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setControlBlock() {
/* 539 */     this.m_strNow = getNow();
/* 540 */     this.m_strForever = getForever();
/* 541 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 548 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
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
/* 559 */     String str = null;
/*     */     
/* 561 */     if (paramString.equals("FEATSTATUS")) {
/* 562 */       logMessage("****** FEATURE Status set to: 114");
/*     */       
/* 564 */       str = "114";
/*     */     }
/*     */     else {
/*     */       
/* 568 */       str = null;
/*     */     } 
/*     */     
/* 571 */     if (str != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 578 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 582 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 589 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 594 */         Vector<SingleFlag> vector = new Vector();
/* 595 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 597 */         if (singleFlag != null) {
/* 598 */           vector.addElement(singleFlag);
/* 599 */           returnEntityKey.m_vctAttributes = vector;
/* 600 */           vector1.addElement(returnEntityKey);
/* 601 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 602 */           this.m_db.commit();
/*     */         } 
/* 604 */       } catch (MiddlewareException middlewareException) {
/* 605 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 606 */       } catch (Exception exception) {
/* 607 */         logMessage("setFlagValue: " + exception.getMessage());
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
/* 621 */     String str = null;
/*     */     
/* 623 */     if (paramString.equals("FEATSTATUS")) {
/* 624 */       logMessage("****** FEATURE Status set to: 112");
/* 625 */       str = "112";
/*     */     }
/*     */     else {
/*     */       
/* 629 */       str = null;
/*     */     } 
/*     */     
/* 632 */     if (str != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 639 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 643 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 650 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 655 */         Vector<SingleFlag> vector = new Vector();
/* 656 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 658 */         if (singleFlag != null) {
/* 659 */           vector.addElement(singleFlag);
/* 660 */           returnEntityKey.m_vctAttributes = vector;
/* 661 */           vector1.addElement(returnEntityKey);
/* 662 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 663 */           this.m_db.commit();
/*     */         } 
/* 665 */       } catch (MiddlewareException middlewareException) {
/* 666 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 667 */       } catch (Exception exception) {
/* 668 */         logMessage("setFlagValue: " + exception.getMessage());
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
/* 680 */     return "FEATUREABR01.java,v 1.5 2006/03/13 20:26:30 couto Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 690 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 698 */     String str1 = getVersion();
/* 699 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 700 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 707 */     println(EACustom.getDocTypeHtml());
/* 708 */     println("<head>");
/* 709 */     println(EACustom.getMetaTags("FEATUREABR01.java"));
/* 710 */     println(EACustom.getCSS());
/* 711 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 712 */     println("</head>");
/* 713 */     println("<body id=\"ibm-com\">");
/* 714 */     println(EACustom.getMastheadDiv());
/*     */     
/* 716 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/* 717 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 719 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 721 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 723 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 725 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 727 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private String dispNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 732 */     StringBuffer stringBuffer = new StringBuffer();
/* 733 */     stringBuffer.append("<br /><br /><table summary=\"Attributes\" width=\"100%\">\n");
/* 734 */     stringBuffer.append("<tr><th id=\"navAttr\" class=\"PsgLabel\" width=\"35%\">Navigation Attribute</th><th id=\"value\" class=\"PsgLabel\" width=\"65%\">Value</th></tr>");
/* 735 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 736 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 737 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 738 */       if (eANMetaAttribute.isNavigate()) {
/* 739 */         stringBuffer.append("<tr><td headers=\"navAttr\" class=\"PsgText\" width=\"35%\">" + eANMetaAttribute.getLongDescription() + "</td><td headers=\"value\" class=\"PsgText\" width=\"65%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       }
/*     */     } 
/* 742 */     stringBuffer.append("</table>");
/* 743 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\FEATUREABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */