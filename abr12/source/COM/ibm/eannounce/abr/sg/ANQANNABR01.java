/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ANQANNABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.";
/*     */   public static final String ABR = "ANQANNABR01";
/*     */   public static final String ANNOUNCEMENT = "ANNOUNCEMENT";
/*     */   public static final String ANCYCLESTATUS = "ANCYCLESTATUS";
/*     */   public static final String ANNNUMBER = "ANNNUMBER";
/*     */   public static final String ANNCODENAME = "ANNCODENAME";
/*     */   public static final String ANNTITLE = "ANNTITLE";
/*     */   public static final String ANNDATE = "ANNDATE";
/*     */   public static final String ANNOP = "ANNOP";
/*     */   public static final String ANNROLETYPE = "ANNROLETYPE";
/*     */   public static final String USERNAME = "USERNAME";
/*     */   public static final String DEF_NOT_POPULATED_HTML = "** Not Populated **";
/* 165 */   private EntityGroup m_egParent = null;
/* 166 */   private EntityGroup m_egANNOP = null;
/*     */   
/* 168 */   private EntityItem m_eiParent = null;
/* 169 */   private EntityItem m_eiANNOP = null;
/* 170 */   private EntityItem m_eiOP = null;
/*     */   
/* 172 */   private final String m_strRelProMgmt = "Released to Production Management";
/*     */   
/* 174 */   private final String m_strAnnounceValue = "117";
/*     */ 
/*     */   
/* 177 */   private final String m_strANNROLETYPE = "Releasing Executive";
/*     */ 
/*     */   
/* 180 */   ControlBlock m_cbOn = null;
/* 181 */   String m_strNow = null;
/* 182 */   String m_strForever = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/* 190 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 194 */       buildRptHeader();
/*     */       
/* 196 */       setControlBlock();
/*     */ 
/*     */       
/* 199 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 200 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 202 */       setDGTitle(setDGName(this.m_eiParent, "ANQANNABR01"));
/*     */       
/* 204 */       logMessage("************ Root Entity Type and id " + 
/*     */           
/* 206 */           getEntityType() + ":" + 
/*     */           
/* 208 */           getEntityID());
/*     */       
/* 210 */       if (this.m_egParent == null) {
/* 211 */         logMessage("********** 1 ANNOUNCEMENT Not found");
/* 212 */         setReturnCode(-1);
/*     */       } else {
/*     */         
/* 215 */         processRootEntity();
/*     */       } 
/*     */       
/* 218 */       println("<br /><b>" + 
/*     */           
/* 220 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 223 */               getABRDescription(), 
/* 224 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 227 */       log(
/* 228 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 231 */               getABRDescription(), 
/* 232 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 234 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 235 */       setReturnCode(-2);
/* 236 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 240 */           .getMessage() + "</h3>");
/*     */       
/* 242 */       logError(lockPDHEntityException.getMessage());
/* 243 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 244 */       setReturnCode(-2);
/* 245 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 247 */           .getMessage() + "</h3>");
/*     */       
/* 249 */       logError(updatePDHEntityException.getMessage());
/* 250 */     } catch (Exception exception) {
/*     */       
/* 252 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 253 */       println("" + exception);
/*     */ 
/*     */       
/* 256 */       if (getABRReturnCode() != -2) {
/* 257 */         setReturnCode(-3);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 263 */       setDGString(getABRReturnCode());
/* 264 */       setDGRptName("ANQANNABR01");
/* 265 */       setDGRptClass("ANQANNABR01");
/*     */       
/* 267 */       if (!isReadOnly()) {
/* 268 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 273 */     printDGSubmitString();
/* 274 */     println(EACustom.getTOUDiv());
/* 275 */     buildReportFooter();
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
/* 286 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 296 */     return "<br /><br />";
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
/* 307 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 316 */     return "1.14";
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
/*     */   public void processRootEntity() throws LockPDHEntityException, UpdatePDHEntityException, Exception, SQLException, MiddlewareException {
/* 335 */     String str1 = null;
/* 336 */     String str2 = null;
/* 337 */     String str3 = null;
/* 338 */     String str4 = null;
/*     */     
/* 340 */     logMessage("****************** Entity Type returned is " + this.m_eiParent
/*     */         
/* 342 */         .getEntityType() + " ei:" + this.m_eiParent
/*     */         
/* 344 */         .getEntityID());
/*     */     
/* 346 */     if (this.m_eiParent.getEntityType().equals("ANNOUNCEMENT")) {
/*     */ 
/*     */       
/* 349 */       str1 = getAttributeValue(
/* 350 */           getEntityType(), 
/* 351 */           getEntityID(), "ANCYCLESTATUS", "<em>** Not Populated **</em>");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 356 */       logMessage("****** ANCYCLESTATUS strStatus is " + str1);
/*     */       
/* 358 */       str2 = this.m_strNow.substring(0, 10).trim();
/* 359 */       println("<h1><b>e-announce report of Announcement</b></h1><br />");
/* 360 */       println("<h1>" + str2 + "</h1><br /><br />");
/*     */       
/* 362 */       if (str1.equals("Released to Production Management")) {
/*     */ 
/*     */         
/* 365 */         this.m_egANNOP = this.m_elist.getEntityGroup("ANNOP");
/* 366 */         printAttribute();
/*     */         
/* 368 */         if (this.m_egANNOP != null && this.m_egANNOP.isRelator()) {
/* 369 */           boolean bool = false;
/*     */           
/* 371 */           for (byte b = 0; b < this.m_egANNOP.getEntityItemCount(); b++) {
/*     */             
/* 373 */             this.m_eiANNOP = this.m_egANNOP.getEntityItem(b);
/*     */             
/* 375 */             str3 = getAttributeValue(this.m_eiANNOP
/* 376 */                 .getEntityType(), this.m_eiANNOP
/* 377 */                 .getEntityID(), "ANNROLETYPE", "<em>None</em>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 385 */             if (str3.equals("Releasing Executive")) {
/* 386 */               this.m_eiOP = (EntityItem)this.m_eiANNOP.getDownLink(0);
/*     */               
/* 388 */               str4 = getAttributeValue(this.m_eiOP
/* 389 */                   .getEntityType(), this.m_eiOP
/* 390 */                   .getEntityID(), "USERNAME", "<em>None</em>");
/*     */ 
/*     */ 
/*     */               
/* 394 */               println("<tr><td class=\"PsgLabel2\">Executive Approval Name</td>");
/* 395 */               println("<td class=\"PsgText2\">" + str4 + "</td></tr>");
/*     */ 
/*     */ 
/*     */               
/* 399 */               bool = true;
/*     */             } 
/*     */           } 
/*     */           
/* 403 */           if (!bool) {
/* 404 */             println("<tr><td class=\"PsgLabel2\">Executive Approval Name</td>");
/* 405 */             println("<td class=\"PsgText2\"><em>None</em></td></tr>");
/*     */           } 
/*     */           
/* 408 */           println("</table><br /><br />");
/* 409 */           setReturnCode(0);
/*     */         } else {
/*     */           
/* 412 */           println("<b>No relator for e-announce Team Member is defined</b><br /><br />");
/*     */ 
/*     */           
/* 415 */           logMessage("No relator e-announce Team Member is defined");
/*     */         } 
/* 417 */         setFlagValue("ANCYCLESTATUS");
/*     */       } else {
/*     */         
/* 420 */         println("<b>Announcement Lifecycle Status is not Released to Production Management</b><br /><br />");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 426 */         logMessage("Announcement Lifecycle Status is not Released to Production Management");
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
/*     */   public void setControlBlock() {
/* 439 */     this.m_strNow = getNow();
/* 440 */     this.m_strForever = getForever();
/* 441 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 448 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
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
/*     */   public void setFlagValue(String paramString) {
/* 460 */     String str = null;
/*     */     
/* 462 */     if (paramString.equals("ANCYCLESTATUS")) {
/* 463 */       str = "117";
/*     */     }
/*     */ 
/*     */     
/* 467 */     logMessage("****** strAttributeValue set to: " + str);
/*     */     
/* 469 */     if (str != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 476 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 480 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 487 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 492 */         Vector<SingleFlag> vector = new Vector();
/* 493 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 495 */         if (singleFlag != null) {
/* 496 */           vector.addElement(singleFlag);
/*     */           
/* 498 */           returnEntityKey.m_vctAttributes = vector;
/* 499 */           vector1.addElement(returnEntityKey);
/*     */           
/* 501 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 502 */           this.m_db.commit();
/*     */         } 
/* 504 */       } catch (MiddlewareException middlewareException) {
/* 505 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 506 */       } catch (Exception exception) {
/* 507 */         logMessage("setFlagValue: " + exception.getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printAttribute() {
/* 518 */     String str1 = null;
/* 519 */     String str2 = null;
/* 520 */     String str3 = null;
/* 521 */     String str4 = null;
/* 522 */     String str5 = null;
/*     */ 
/*     */     
/* 525 */     str1 = getAttributeValue(
/* 526 */         getEntityType(), 
/* 527 */         getEntityID(), "ANNNUMBER", "<em>** Not Populated **</em>");
/*     */ 
/*     */ 
/*     */     
/* 531 */     str2 = getAttributeValue(
/* 532 */         getEntityType(), 
/* 533 */         getEntityID(), "ANNCODENAME", "<em>** Not Populated **</em>");
/*     */ 
/*     */ 
/*     */     
/* 537 */     str3 = getAttributeValue(
/* 538 */         getEntityType(), 
/* 539 */         getEntityID(), "ANNTITLE", "<em>** Not Populated **</em>");
/*     */ 
/*     */ 
/*     */     
/* 543 */     str4 = getAttributeValue(
/* 544 */         getEntityType(), 
/* 545 */         getEntityID(), "ANNDATE", "<em>** Not Populated **</em>");
/*     */ 
/*     */ 
/*     */     
/* 549 */     str5 = this.m_strNow.substring(0, 10).trim();
/* 550 */     println("<h1>The following Announcement has been updated to</h1><br />");
/* 551 */     println("<h1>the Status of 'Announced' as of (" + str5 + ")</h1><br /><br />");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 556 */     println("<table summary=\"" + this.m_egParent.getLongDescription() + "\" width=\"100%\">");
/* 557 */     println("<tr>");
/* 558 */     println("<td><h1><b>" + this.m_egParent
/*     */         
/* 560 */         .getLongDescription() + "</b></h1>");
/*     */     
/* 562 */     println("</td></tr>");
/* 563 */     println("</table>");
/*     */     
/* 565 */     println("<table summary=\"layout\" width=\"100%\">");
/* 566 */     println("<tr><td class=\"PsgLabel2\">Announcement Number</td>");
/* 567 */     println("<td class=\"PsgText2\">" + str1 + "</td></tr>");
/*     */     
/* 569 */     println("<tr><td class=\"PsgLabel2\">Announcement Code Name</td>");
/* 570 */     println("<td class=\"PsgText2\">" + str2 + "</td></tr>");
/*     */     
/* 572 */     println("<tr><td class=\"PsgLabel2\">Announcement Title</td>");
/* 573 */     println("<td class=\"PsgText2\">" + str3 + "</td></tr>");
/*     */     
/* 575 */     println("<tr><td class=\"PsgLabel2\">Announce Date</td>");
/* 576 */     println("<td class=\"PsgText2\">" + str4 + "</td></tr>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 586 */     return "ANQANNABR01.java,v 1.14 2006/03/13 19:42:03 couto Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 594 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 602 */     String str1 = getVersion();
/* 603 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 604 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 611 */     println(EACustom.getDocTypeHtml());
/* 612 */     println("<head>");
/* 613 */     println(EACustom.getMetaTags("ANQANNABR01.java"));
/* 614 */     println(EACustom.getCSS());
/* 615 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 616 */     println("</head>");
/* 617 */     println("<body id=\"ibm-com\">");
/* 618 */     println(EACustom.getMastheadDiv());
/*     */     
/* 620 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/* 621 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 623 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 625 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 627 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 629 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 631 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ANQANNABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */