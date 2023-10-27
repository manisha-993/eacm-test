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
/*     */ import COM.ibm.opicmpdh.middleware.DatePackage;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CRSTATABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
/*     */   public static final String ABR = "CRSTATABR01";
/* 144 */   private EntityGroup m_egParent = null;
/* 145 */   private EntityItem m_ei = null;
/*     */   
/* 147 */   private final String m_strReadyForReviewValue = "112";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 157 */     String str1 = null;
/* 158 */     String str2 = null;
/* 159 */     String str3 = null;
/* 160 */     String str4 = null;
/* 161 */     EntityGroup entityGroup1 = null;
/* 162 */     EntityGroup entityGroup2 = null;
/* 163 */     EntityItem entityItem = null;
/*     */     try {
/* 165 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 169 */       buildRptHeader();
/*     */       
/* 171 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 172 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*     */       
/* 174 */       setDGTitle(setDGName(this.m_ei, "CRSTATABR01"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 185 */       setReturnCode(0);
/*     */       
/* 187 */       entityGroup1 = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/* 188 */       if (entityGroup1 == null) {
/* 189 */         logMessage("****************Announcement Not found ");
/* 190 */         setReturnCode(-1);
/*     */       } 
/*     */       
/*     */       byte b;
/* 194 */       for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 195 */         entityItem = entityGroup1.getEntityItem(b);
/*     */         
/* 197 */         println("<br /><b>" + entityGroup1.getLongDescription() + "</b>");
/*     */         
/* 199 */         printNavAttributes(entityItem, entityGroup1, true, b);
/*     */ 
/*     */         
/* 202 */         str1 = getAttributeValue(entityItem
/* 203 */             .getEntityType(), entityItem
/* 204 */             .getEntityID(), "ANNDESC", "<em>** Not Populated ** </em>");
/*     */ 
/*     */         
/* 207 */         println("<br /><table summary=\"Announcement Description\" width=\"100%\"><tr><td class=\"PsgLabel\">Announcement Description</td></tr><tr><td class=\"PsgText\">" + str1 + "</td></tr></table>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 219 */         str2 = entityItem.getEntityType();
/* 220 */         str4 = getFlagCode(entityItem, "ANCYCLESTATUS");
/* 221 */         logMessage("*************** strStatus1: " + str4);
/*     */         
/* 223 */         if (str2.equals("ANNOUNCEMENT") && str4
/* 224 */           .equals("111")) {
/* 225 */           str3 = "117";
/* 226 */         } else if (str2
/* 227 */           .equals("ANNOUNCEMENT") && str4
/* 228 */           .equals("112")) {
/* 229 */           str3 = "117";
/* 230 */         } else if (str2
/* 231 */           .equals("ANNOUNCEMENT") && str4
/* 232 */           .equals("113")) {
/* 233 */           str3 = "117";
/*     */         } 
/*     */         
/* 236 */         logMessage("******************AFter checking Status of announcement");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 258 */         if (str3 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 264 */           EntityItem entityItem1 = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */           
/* 268 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem1.getEntityType(), entityItem1.getEntityID(), true);
/*     */           
/* 270 */           DatePackage datePackage = this.m_db.getDates();
/* 271 */           String str5 = datePackage.getNow();
/* 272 */           String str6 = datePackage.getForever();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 280 */           ControlBlock controlBlock = new ControlBlock(str5, str6, str5, str6, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 285 */           SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), "CRSTATUS", str3, 1, controlBlock);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 290 */           Vector<SingleFlag> vector = new Vector();
/* 291 */           Vector<ReturnEntityKey> vector1 = new Vector();
/*     */           
/* 293 */           if (singleFlag != null) {
/* 294 */             vector.addElement(singleFlag);
/*     */           }
/*     */           
/* 297 */           returnEntityKey.m_vctAttributes = vector;
/* 298 */           vector1.addElement(returnEntityKey);
/*     */           
/* 300 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 301 */           this.m_db.commit();
/* 302 */           logMessage("After update of CHANGEREQUEST COLUMN");
/* 303 */           println("<br /><b>This Change Request was cancelled because the Announcement is not eligible for Change Requests.</b>");
/*     */         } else {
/*     */           
/* 306 */           println("<br /><b>Please review and process this Change Request.</b>");
/*     */         } 
/*     */ 
/*     */         
/* 310 */         str3 = null;
/* 311 */         str1 = null;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 317 */       this.m_prof.setValOn(getValOn());
/* 318 */       this.m_prof.setEffOn(getEffOn());
/* 319 */       start_ABRBuild();
/*     */       
/* 321 */       entityGroup2 = this.m_elist.getParentEntityGroup();
/* 322 */       logMessage("************2 Root Entity Type and id " + 
/*     */           
/* 324 */           getEntityType() + ":" + 
/*     */           
/* 326 */           getEntityID());
/* 327 */       if (entityGroup2 == null) {
/* 328 */         logMessage("**************** CHANGEREQUEST Not found ");
/* 329 */         setReturnCode(-1);
/*     */       } else {
/*     */         
/* 332 */         println("<br /><br /><table summary=\"Change Request\" width=\"100%\">");
/* 333 */         println("<tr><th class=\"PsgLabel\" id=\"attributeDesc\">Attribute Description</th>");
/* 334 */         println("<th class=\"PsgLabel\" id=\"attrValue\">Attribute Value</th></tr>");
/*     */         
/* 336 */         entityItem = entityGroup2.getEntityItem(0);
/* 337 */         for (b = 0; b < entityGroup2.getMetaAttributeCount(); b++) {
/* 338 */           EANMetaAttribute eANMetaAttribute = entityGroup2.getMetaAttribute(b);
/*     */           
/* 340 */           EANAttribute eANAttribute = entityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/*     */           
/* 342 */           String str5 = eANMetaAttribute.getAttributeCode();
/*     */           
/* 344 */           String str6 = str5.substring(str5
/* 345 */               .length() - 7, str5
/* 346 */               .length());
/*     */           
/* 348 */           String str7 = str5.substring(str5
/* 349 */               .length() - 6, str5
/* 350 */               .length());
/*     */           
/* 352 */           if (!str6.equals("COMMENT") && 
/* 353 */             !str7.equals("REVIEW")) {
/* 354 */             println("<tr><td headers=\"attributeDesc\" class=\"PsgText\">" + eANMetaAttribute
/*     */                 
/* 356 */                 .getLongDescription() + "</td><td headers=\"attrValue\" class=\"PsgText\">" + ((eANAttribute == null) ? "** Not Populated **" : eANAttribute
/*     */ 
/*     */ 
/*     */                 
/* 360 */                 .toString()) + "</td></tr>");
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 366 */         println("</table>");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 379 */       println("<br /><b>" + 
/*     */           
/* 381 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 384 */               getABRDescription(), 
/* 385 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 388 */       log(
/* 389 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 392 */               getABRDescription(), 
/* 393 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 394 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 395 */       setReturnCode(-2);
/* 396 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 400 */           .getMessage() + "</h3>");
/*     */       
/* 402 */       logError(lockPDHEntityException.getMessage());
/* 403 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 404 */       setReturnCode(-2);
/* 405 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 407 */           .getMessage() + "</h3>");
/*     */       
/* 409 */       logError(updatePDHEntityException.getMessage());
/* 410 */     } catch (Exception exception) {
/*     */       
/* 412 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 413 */       println("" + exception);
/*     */ 
/*     */       
/* 416 */       if (getABRReturnCode() != -2) {
/* 417 */         setReturnCode(-3);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 423 */       setDGString(getABRReturnCode());
/* 424 */       setDGRptName("CRSTATABR01");
/* 425 */       setDGRptClass("CRSTATABR01");
/*     */       
/* 427 */       if (!isReadOnly()) {
/* 428 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 433 */     printDGSubmitString();
/* 434 */     println(EACustom.getTOUDiv());
/* 435 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setControlBlock() {
/* 442 */     this.m_strNow = getNow();
/* 443 */     this.m_strForever = getForever();
/* 444 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 451 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlagValue(String paramString) {
/* 462 */     String str = null;
/*     */     
/* 464 */     if (paramString.equals("CRSTATUS")) {
/* 465 */       logMessage("****** Change Request Status set to: 112");
/*     */ 
/*     */       
/* 468 */       str = "112";
/*     */     }
/*     */     else {
/*     */       
/* 472 */       str = null;
/*     */     } 
/*     */     
/* 475 */     if (str != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 482 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 486 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 493 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 498 */         Vector<SingleFlag> vector = new Vector();
/* 499 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 501 */         if (singleFlag != null) {
/* 502 */           vector.addElement(singleFlag);
/* 503 */           returnEntityKey.m_vctAttributes = vector;
/* 504 */           vector1.addElement(returnEntityKey);
/* 505 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 506 */           this.m_db.commit();
/*     */         } 
/* 508 */       } catch (MiddlewareException middlewareException) {
/* 509 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 510 */       } catch (Exception exception) {
/* 511 */         logMessage("setFlagValue: " + exception.getMessage());
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
/* 524 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 534 */     return "<br /><br />";
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
/* 545 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 554 */     return "1.16";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 564 */     return "CRSTATABR01.java,v 1.16 2008/01/30 20:02:00 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 574 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 582 */     String str1 = getVersion();
/* 583 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 584 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 591 */     println(EACustom.getDocTypeHtml());
/* 592 */     println("<head>");
/* 593 */     println(EACustom.getMetaTags("CRSTATABR01.java"));
/* 594 */     println(EACustom.getCSS());
/* 595 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 596 */     println("</head>");
/* 597 */     println("<body id=\"ibm-com\">");
/* 598 */     println(EACustom.getMastheadDiv());
/*     */     
/* 600 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/*     */     
/* 602 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 604 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 606 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 608 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 610 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 612 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup, boolean paramBoolean, int paramInt) {
/* 622 */     println("<br /><br /><table summary=\"" + paramEntityGroup.getLongDescription() + "\" width=\"100%\">" + "\n");
/*     */     
/* 624 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 625 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 626 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 627 */       if (paramBoolean) {
/* 628 */         if (b == 0) {
/* 629 */           println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute" + paramInt + "\">Navigation Attribute</th>");
/* 630 */           println("<th class=\"PsgLabel\" width=\"50%\" id=\"value" + paramInt + "\">Attribute Value</th></tr>");
/*     */         } 
/* 632 */         if (eANMetaAttribute.isNavigate()) {
/* 633 */           println("<tr><td headers=\"attribute" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td>");
/* 634 */           println("<td headers=\"value" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */         } 
/*     */       } else {
/*     */         
/* 638 */         if (b == 0) {
/* 639 */           println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute" + paramInt + "\">Attribute Description</th>");
/* 640 */           println("<th class=\"PsgLabel\" width=\"50%\" id=\"value" + paramInt + "\">Attribute Value</th></tr>");
/*     */         } 
/* 642 */         println("<tr><td headers=\"attribute" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td>");
/* 643 */         println("<td headers=\"value" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       } 
/*     */     } 
/*     */     
/* 647 */     println("</table>");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CRSTATABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */