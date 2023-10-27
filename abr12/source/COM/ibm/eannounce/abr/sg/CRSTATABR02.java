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
/*     */ public class CRSTATABR02
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
/*     */   public static final String ABR = "CRSTATABR02";
/* 134 */   private EntityGroup m_egParent = null;
/* 135 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
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
/* 148 */     String str1 = null;
/*     */     
/* 150 */     String str2 = null;
/* 151 */     String str3 = null;
/* 152 */     String str4 = null;
/* 153 */     EntityGroup entityGroup1 = null;
/* 154 */     EntityGroup entityGroup2 = null;
/*     */     try {
/* 156 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 160 */       buildRptHeader();
/*     */       
/* 162 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 163 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*     */       
/* 165 */       setDGTitle(setDGName(this.m_ei, "CRSTATABR02"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 176 */       setReturnCode(0);
/*     */       
/* 178 */       entityGroup1 = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/*     */       
/* 180 */       if (getEntityType() == null) {
/* 181 */         logMessage("****************Announcement Not found ");
/* 182 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 186 */       for (byte b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 187 */         EntityItem entityItem = entityGroup1.getEntityItem(b);
/*     */         
/* 189 */         logMessage("************Entity Type returned is " + entityItem
/*     */             
/* 191 */             .getEntityType() + ", " + entityItem
/*     */             
/* 193 */             .getEntityID());
/*     */         
/* 195 */         println("<br /><b>" + entityGroup1.getLongDescription() + "</b>");
/*     */         
/* 197 */         printNavAttributes(entityItem, entityGroup1, true, b + 1);
/*     */ 
/*     */         
/* 200 */         str1 = getAttributeValue(entityItem
/* 201 */             .getEntityType(), entityItem
/* 202 */             .getEntityID(), "ANNDESC", "<em>** Not Populated ** </em>");
/*     */ 
/*     */         
/* 205 */         println("<br /><table summary=\"Announcement Description\" width=\"100%\"><tr><td class=\"PsgLabel\">Announcement Description</td></tr><tr><td class=\"PsgText\">" + str1 + "</td></tr></table>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 217 */         str4 = entityItem.getEntityType();
/* 218 */         str3 = getFlagCode(entityItem, "ANCYCLESTATUS");
/* 219 */         logMessage("*************** strStatus1: " + str3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 229 */         if (str4.equals("ANNOUNCEMENT") && str3
/* 230 */           .equals("111")) {
/* 231 */           str2 = "117";
/* 232 */         } else if (str4
/* 233 */           .equals("ANNOUNCEMENT") && str3
/* 234 */           .equals("112")) {
/* 235 */           str2 = "117";
/* 236 */         } else if (str4
/* 237 */           .equals("ANNOUNCEMENT") && str3
/* 238 */           .equals("113")) {
/* 239 */           str2 = "117";
/*     */         } 
/*     */       } 
/* 242 */       logMessage("******************AFter checking Status of announcement");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 263 */       if (str2 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 269 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 273 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */         
/* 275 */         DatePackage datePackage = this.m_db.getDates();
/* 276 */         String str5 = datePackage.getNow();
/* 277 */         String str6 = datePackage.getForever();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 285 */         ControlBlock controlBlock = new ControlBlock(str5, str6, str5, str6, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 290 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), "CRSTATUS", str2, 1, controlBlock);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 295 */         Vector<SingleFlag> vector = new Vector();
/* 296 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 298 */         if (singleFlag != null) {
/* 299 */           vector.addElement(singleFlag);
/*     */         }
/*     */         
/* 302 */         returnEntityKey.m_vctAttributes = vector;
/* 303 */         vector1.addElement(returnEntityKey);
/*     */         
/* 305 */         this.m_db.update(this.m_prof, vector1, false, false);
/* 306 */         this.m_db.commit();
/* 307 */         println("<br /><b>This Change Request was cancelled because the Announcement is not eligible for Change Requests.</b>");
/*     */       } else {
/*     */         
/* 310 */         println("<br /><b>Please review and approve as required.</b>");
/*     */       } 
/*     */       
/* 313 */       str1 = null;
/* 314 */       str2 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 319 */       this.m_prof.setValOn(getValOn());
/* 320 */       this.m_prof.setEffOn(getEffOn());
/* 321 */       start_ABRBuild();
/*     */       
/* 323 */       entityGroup2 = this.m_elist.getParentEntityGroup();
/* 324 */       if (entityGroup2 == null) {
/* 325 */         logMessage("**************** CHANGEREQUEST Not found ");
/* 326 */         setReturnCode(-1);
/*     */       } else {
/*     */         
/* 329 */         EntityItem entityItem = entityGroup2.getEntityItem(0);
/* 330 */         println("<br /><br /><b>" + entityGroup2.getLongDescription() + "</b>");
/*     */         
/* 332 */         printNavAttributes(entityItem, entityGroup1, false, 0);
/*     */       } 
/*     */       
/* 335 */       println("<br /><b>" + 
/*     */           
/* 337 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 340 */               getABRDescription(), 
/* 341 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 344 */       log(
/* 345 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 348 */               getABRDescription(), 
/* 349 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 350 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 351 */       setReturnCode(-2);
/* 352 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 356 */           .getMessage() + "</h3>");
/*     */       
/* 358 */       logError(lockPDHEntityException.getMessage());
/* 359 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 360 */       setReturnCode(-2);
/* 361 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 363 */           .getMessage() + "</h3>");
/*     */       
/* 365 */       logError(updatePDHEntityException.getMessage());
/* 366 */     } catch (Exception exception) {
/*     */       
/* 368 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 369 */       println("" + exception);
/*     */ 
/*     */       
/* 372 */       if (getABRReturnCode() != -2) {
/* 373 */         setReturnCode(-3);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 379 */       setDGString(getABRReturnCode());
/* 380 */       setDGRptName("CRSTATABR02");
/* 381 */       setDGRptClass("CRSTATABR02");
/*     */       
/* 383 */       if (!isReadOnly()) {
/* 384 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 389 */     printDGSubmitString();
/* 390 */     println(EACustom.getTOUDiv());
/* 391 */     buildReportFooter();
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
/* 402 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 412 */     return "<br /><br />";
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
/* 423 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 433 */     return "1.14";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 442 */     return "CRSTATABR02.java,v 1.14 2008/01/30 20:02:00 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 452 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 460 */     String str1 = getVersion();
/* 461 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 462 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 469 */     println(EACustom.getDocTypeHtml());
/* 470 */     println("<head>");
/* 471 */     println(EACustom.getMetaTags("CRSTATABR02.java"));
/* 472 */     println(EACustom.getCSS());
/* 473 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 474 */     println("</head>");
/* 475 */     println("<body id=\"ibm-com\">");
/* 476 */     println(EACustom.getMastheadDiv());
/*     */     
/* 478 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/*     */     
/* 480 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 482 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 484 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 486 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 488 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 490 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup, boolean paramBoolean, int paramInt) {
/* 500 */     println("<br /><br /><table summary=\"" + paramEntityGroup.getLongDescription() + "\" width=\"100%\">" + "\n");
/*     */     
/* 502 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 503 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 504 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 505 */       if (paramBoolean) {
/* 506 */         if (b == 0) {
/* 507 */           println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute" + paramInt + "\">Navigation Attribute</th>");
/* 508 */           println("<th class=\"PsgLabel\" width=\"50%\" id=\"value" + paramInt + "\">Attribute Value</th></tr>");
/*     */         } 
/* 510 */         if (eANMetaAttribute.isNavigate()) {
/* 511 */           println("<tr><td headers=\"attribute" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td>");
/* 512 */           println("<td headers=\"value" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */         } 
/*     */       } else {
/*     */         
/* 516 */         if (b == 0) {
/* 517 */           println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute" + paramInt + "\">Attribute Description</th>");
/* 518 */           println("<th class=\"PsgLabel\" width=\"50%\" id=\"value" + paramInt + "\">Attribute Value</th></tr>");
/*     */         } 
/* 520 */         println("<tr><td headers=\"attribute" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td>");
/* 521 */         println("<td headers=\"value" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       } 
/*     */     } 
/*     */     
/* 525 */     println("</table>");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CRSTATABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */