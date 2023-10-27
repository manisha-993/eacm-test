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
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CRSTATABR03
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
/*     */   public static final String ABR = "CRSTATABR03";
/* 123 */   private EntityGroup m_egParent = null;
/* 124 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 134 */     String str1 = null;
/* 135 */     String str2 = null;
/* 136 */     String str3 = null;
/* 137 */     String str4 = null;
/* 138 */     String str5 = null;
/* 139 */     EntityGroup entityGroup = null;
/*     */     
/*     */     try {
/* 142 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 146 */       buildRptHeader();
/*     */       
/* 148 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 149 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*     */       
/* 151 */       setDGTitle(setDGName(this.m_ei, "CRSTATABR03"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 162 */       setReturnCode(0);
/*     */       
/* 164 */       println("<table summary=\"Announcement\" width=\"100%\">\n<tr><th id=\"number\" class=\"PsgLabel\">Announcement Number</th><th id=\"date\" class=\"PsgLabel\">Announcement Date</th><th id=\"type\" class=\"PsgLabel\">Announcement Type</th><th id=\"title\" class=\"PsgLabel\">Announcement Title</th><th id=\"description\" class=\"PsgLabel\">Announcement Description</th></tr>");
/*     */       
/* 166 */       entityGroup = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/* 167 */       logMessage("************Root Entity Type and id " + 
/*     */           
/* 169 */           getEntityType() + ":" + 
/*     */           
/* 171 */           getEntityID());
/*     */       
/* 173 */       if (entityGroup == null) {
/* 174 */         logMessage("****************Announcement Not found ");
/* 175 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 179 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 180 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*     */         
/* 182 */         logMessage("************Entity Type returned is " + entityItem
/*     */             
/* 184 */             .getEntityType());
/*     */         
/* 186 */         if (entityItem.getEntityType().equals("ANNOUNCEMENT")) {
/* 187 */           logMessage("******************Entitytype is Announcement");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 205 */           str1 = getAttributeValue(entityItem
/* 206 */               .getEntityType(), entityItem
/* 207 */               .getEntityID(), "ANNNUMBER", "<em>** Not Populated ** </em>");
/*     */ 
/*     */ 
/*     */           
/* 211 */           str2 = getAttributeValue(entityItem
/* 212 */               .getEntityType(), entityItem
/* 213 */               .getEntityID(), "ANNDATE", "<em>** Not Populated ** </em>");
/*     */ 
/*     */ 
/*     */           
/* 217 */           str3 = getAttributeValue(entityItem
/* 218 */               .getEntityType(), entityItem
/* 219 */               .getEntityID(), "ANNTYPE", "<em>** Not Populated ** </em>");
/*     */ 
/*     */ 
/*     */           
/* 223 */           str4 = getAttributeValue(entityItem
/* 224 */               .getEntityType(), entityItem
/* 225 */               .getEntityID(), "ANNTITLE", "<em>** Not Populated ** </em>");
/*     */ 
/*     */ 
/*     */           
/* 229 */           str5 = getAttributeValue(entityItem
/* 230 */               .getEntityType(), entityItem
/* 231 */               .getEntityID(), "ANNDESC", "<em>** Not Populated ** </em>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 237 */           logMessage("******************After getting all the Announcement Attributes");
/* 238 */           println("<tr><td headers=\"number\" class=\"PsgText\">" + str1 + "</td><td headers=\"date\" class=\"PsgText\">" + str2 + "</td><td headers=\"type\" class=\"PsgText\">" + str3 + "</td><td headers=\"title\" class=\"PsgText\">" + str4 + "</td><td headers=\"description\" class=\"PsgText\">" + str5 + "</td></tr>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 250 */           println("</table>\n<br />");
/*     */ 
/*     */ 
/*     */           
/* 254 */           str1 = null;
/* 255 */           str2 = null;
/* 256 */           str3 = null;
/* 257 */           str4 = null;
/* 258 */           str5 = null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 265 */       if (this.m_egParent == null) {
/* 266 */         logMessage("**************** CHANGEREQUEST Not found ");
/* 267 */         setReturnCode(-1);
/*     */       }
/*     */       else {
/*     */         
/* 271 */         String str = getAttributeValue(this.m_ei
/* 272 */             .getEntityType(), this.m_ei
/* 273 */             .getEntityID(), "CRSTATUS", "<em>** Not Populated ** </em>");
/*     */ 
/*     */         
/* 276 */         if (str.equals("Requires PDT Review")) {
/* 277 */           println("<br /><b>This Change Request was Rejected</b>");
/*     */         }
/* 279 */         else if (str
/* 280 */           .equals("Approved") || str
/* 281 */           .equals("Ready for Review")) {
/* 282 */           println("<br /><b>This Change Request was Cancelled</b>");
/*     */         } 
/*     */ 
/*     */         
/* 286 */         println("<br /><br /><b>" + this.m_egParent
/* 287 */             .getLongDescription() + "</b>");
/*     */         
/* 289 */         printNavAttributes(this.m_ei, this.m_egParent, false, 0);
/*     */       } 
/*     */ 
/*     */       
/* 293 */       println("<br /><b>" + 
/*     */           
/* 295 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 298 */               getABRDescription(), 
/* 299 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 302 */       log(
/* 303 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 306 */               getABRDescription(), 
/* 307 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 308 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 309 */       setReturnCode(-2);
/* 310 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 314 */           .getMessage() + "</h3>");
/*     */       
/* 316 */       logError(lockPDHEntityException.getMessage());
/* 317 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 318 */       setReturnCode(-2);
/* 319 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 321 */           .getMessage() + "</h3>");
/*     */       
/* 323 */       logError(updatePDHEntityException.getMessage());
/* 324 */     } catch (Exception exception) {
/*     */       
/* 326 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 327 */       println("" + exception);
/*     */ 
/*     */       
/* 330 */       if (getABRReturnCode() != -2) {
/* 331 */         setReturnCode(-3);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 337 */       setDGString(getABRReturnCode());
/* 338 */       setDGRptName("CRSTATABR03");
/* 339 */       setDGRptClass("CRSTATABR03");
/*     */       
/* 341 */       if (!isReadOnly()) {
/* 342 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 347 */     printDGSubmitString();
/* 348 */     println(EACustom.getTOUDiv());
/* 349 */     buildReportFooter();
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
/* 360 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 370 */     return "<br /><br />";
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
/* 381 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 391 */     return "1.15";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 401 */     return "CRSTATABR03.java,v 1.15 2008/01/30 20:02:00 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 411 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 419 */     String str1 = getVersion();
/* 420 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 421 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 428 */     println(EACustom.getDocTypeHtml());
/* 429 */     println("<head>");
/* 430 */     println(EACustom.getMetaTags("CRSTATABR03.java"));
/* 431 */     println(EACustom.getCSS());
/* 432 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 433 */     println("</head>");
/* 434 */     println("<body id=\"ibm-com\">");
/* 435 */     println(EACustom.getMastheadDiv());
/*     */     
/* 437 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/*     */     
/* 439 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 441 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 443 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 445 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 447 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 449 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup, boolean paramBoolean, int paramInt) {
/* 459 */     println("<br /><br /><table summary=\"" + paramEntityGroup.getLongDescription() + "\" width=\"100%\">" + "\n");
/*     */     
/* 461 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 462 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 463 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 464 */       if (paramBoolean) {
/* 465 */         if (b == 0) {
/* 466 */           println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute" + paramInt + "\">Navigation Attribute</th>");
/* 467 */           println("<th class=\"PsgLabel\" width=\"50%\" id=\"value" + paramInt + "\">Attribute Value</th></tr>");
/*     */         } 
/* 469 */         if (eANMetaAttribute.isNavigate()) {
/* 470 */           println("<tr><td headers=\"attribute" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td>");
/* 471 */           println("<td headers=\"value" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */         } 
/*     */       } else {
/*     */         
/* 475 */         if (b == 0) {
/* 476 */           println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute" + paramInt + "\">Attribute Description</th>");
/* 477 */           println("<th class=\"PsgLabel\" width=\"50%\" id=\"value" + paramInt + "\">Attribute Value</th></tr>");
/*     */         } 
/* 479 */         println("<tr><td headers=\"attribute" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td>");
/* 480 */         println("<td headers=\"value" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       } 
/*     */     } 
/*     */     
/* 484 */     println("</table>");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CRSTATABR03.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */