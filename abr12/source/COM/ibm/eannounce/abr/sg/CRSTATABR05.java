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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CRSTATABR05
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
/*     */   public static final String ABR = "CRSTATABR05";
/*     */   public static final String CHANGEREQUEST = "CHANGEREQUEST";
/*     */   public static final String CRSTATUS = "CRSTATUS";
/* 121 */   private EntityGroup m_egParent = null;
/* 122 */   private EntityItem m_ei = null;
/*     */   
/* 124 */   private final String m_strRelProMgmtValue = "116";
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
/* 135 */       String str = null;
/* 136 */       EntityItem entityItem = null;
/* 137 */       EntityGroup entityGroup1 = null;
/* 138 */       EntityGroup entityGroup2 = null;
/*     */       
/* 140 */       setReturnCode(0);
/*     */       
/* 142 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 146 */       buildRptHeader();
/* 147 */       setControlBlock();
/*     */       
/* 149 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 150 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*     */       
/* 152 */       setDGTitle(setDGName(this.m_ei, "CRSTATABR05"));
/*     */       
/* 154 */       entityGroup1 = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/*     */       
/* 156 */       if (entityGroup1 == null) {
/* 157 */         logMessage("****************Announcement Not found ");
/* 158 */         setReturnCode(-1);
/*     */       } 
/*     */       
/*     */       byte b;
/* 162 */       for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 163 */         entityItem = entityGroup1.getEntityItem(b);
/*     */         
/* 165 */         println("<br /><b>" + entityGroup1.getLongDescription() + "</b>");
/*     */         
/* 167 */         printNavAttributes(entityItem, entityGroup1, true, b);
/*     */ 
/*     */         
/* 170 */         str = getAttributeValue(entityItem
/* 171 */             .getEntityType(), entityItem
/* 172 */             .getEntityID(), "ANNDESC", "<em>** Not Populated ** </em>");
/*     */ 
/*     */         
/* 175 */         println("<br /><table summary=\"Announcement Description\" width=\"100%\"><tr><td class=\"PsgLabel\">Announcement Description</td></tr><tr><td class=\"PsgText\">" + str + "</td></tr></table>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 187 */         println("<br /><b>The following Change Request is now complete.</b>");
/*     */ 
/*     */         
/* 190 */         str = null;
/*     */       } 
/*     */ 
/*     */       
/* 194 */       entityGroup2 = this.m_elist.getParentEntityGroup();
/* 195 */       logMessage("************2 Root Entity Type and id " + 
/*     */           
/* 197 */           getEntityType() + ":" + 
/*     */           
/* 199 */           getEntityID());
/* 200 */       if (entityGroup2 == null) {
/* 201 */         logMessage("**************** CHANGEREQUEST Not found ");
/* 202 */         setReturnCode(-1);
/*     */       } else {
/*     */         
/* 205 */         println("<br /><br /><table summary=\"Chagen Request\" width=\"100%\">");
/* 206 */         println("<tr><th id=\"attrDesc\" class=\"PsgLabel\">Attribute Description</th><th id=\"attrValue\" class=\"PsgLabel\">Attribute Value</th></tr>");
/* 207 */         entityItem = entityGroup2.getEntityItem(0);
/* 208 */         for (b = 0; b < entityGroup2.getMetaAttributeCount(); b++) {
/* 209 */           EANMetaAttribute eANMetaAttribute = entityGroup2.getMetaAttribute(b);
/*     */           
/* 211 */           EANAttribute eANAttribute = entityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 212 */           if (eANMetaAttribute.isNavigate()) {
/* 213 */             String str1 = eANMetaAttribute.getAttributeCode();
/*     */             
/* 215 */             String str2 = str1.substring(str1
/* 216 */                 .length() - 7, str1
/* 217 */                 .length());
/*     */             
/* 219 */             String str3 = str1.substring(str1
/* 220 */                 .length() - 6, str1
/* 221 */                 .length());
/*     */             
/* 223 */             if (!str2.equals("COMMENT") && 
/* 224 */               !str3.equals("REVIEW")) {
/* 225 */               println("<tr><td headers=\"attrDesc\" class=\"PsgText\">" + eANMetaAttribute
/*     */                   
/* 227 */                   .getLongDescription() + "</td><td headers=\"attrValue\" class=\"PsgText\">" + ((eANAttribute == null) ? "** Not Populated **" : eANAttribute
/*     */ 
/*     */ 
/*     */                   
/* 231 */                   .toString()) + "</td></tr>");
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 238 */         println("</table>");
/*     */       } 
/*     */ 
/*     */       
/* 242 */       if (getReturnCode() == 0) {
/* 243 */         setFlagValue("CRSTATUS");
/*     */       }
/*     */       
/* 246 */       println("<br /><b>" + 
/*     */           
/* 248 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 251 */               getABRDescription(), 
/* 252 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 255 */       log(
/* 256 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 259 */               getABRDescription(), 
/* 260 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 261 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 262 */       setReturnCode(-2);
/* 263 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 267 */           .getMessage() + "</h3>");
/*     */       
/* 269 */       logError(lockPDHEntityException.getMessage());
/* 270 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 271 */       setReturnCode(-2);
/* 272 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 274 */           .getMessage() + "</h3>");
/*     */       
/* 276 */       logError(updatePDHEntityException.getMessage());
/* 277 */     } catch (Exception exception) {
/*     */       
/* 279 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 280 */       println("" + exception);
/*     */ 
/*     */       
/* 283 */       if (getABRReturnCode() != -2) {
/* 284 */         setReturnCode(-3);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 290 */       setDGString(getABRReturnCode());
/* 291 */       setDGRptName("CRSTATABR05");
/* 292 */       setDGRptClass("CRSTATABR05");
/*     */       
/* 294 */       if (!isReadOnly()) {
/* 295 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 300 */     printDGSubmitString();
/* 301 */     println(EACustom.getTOUDiv());
/* 302 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setControlBlock() {
/* 309 */     this.m_strNow = getNow();
/* 310 */     this.m_strForever = getForever();
/* 311 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 318 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
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
/* 329 */     String str = null;
/*     */     
/* 331 */     if (paramString.equals("CRSTATUS")) {
/*     */       
/* 333 */       logMessage("****** CRSTATUS set to: 116");
/* 334 */       str = "116";
/*     */     }
/*     */     else {
/*     */       
/* 338 */       str = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 343 */     if (str != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 351 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 355 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 363 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 368 */         Vector<SingleFlag> vector = new Vector();
/* 369 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 371 */         if (singleFlag != null) {
/* 372 */           vector.addElement(singleFlag);
/* 373 */           returnEntityKey.m_vctAttributes = vector;
/* 374 */           vector1.addElement(returnEntityKey);
/* 375 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 376 */           this.m_db.commit();
/*     */         } 
/* 378 */       } catch (MiddlewareException middlewareException) {
/* 379 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 380 */       } catch (Exception exception) {
/* 381 */         logMessage("setFlagValue: " + exception.getMessage());
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
/* 394 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 404 */     return "<br /><br />";
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
/* 415 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 425 */     return "1.13";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 435 */     return "CRSTATABR05.java,v 1.13 2008/01/30 20:02:00 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 445 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 453 */     String str1 = getVersion();
/* 454 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 455 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 462 */     println(EACustom.getDocTypeHtml());
/* 463 */     println("<head>");
/* 464 */     println(EACustom.getMetaTags("CRSTATABR05.java"));
/* 465 */     println(EACustom.getCSS());
/* 466 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 467 */     println("</head>");
/* 468 */     println("<body id=\"ibm-com\">");
/* 469 */     println(EACustom.getMastheadDiv());
/*     */     
/* 471 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/*     */     
/* 473 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 475 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 477 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 479 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 481 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 483 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup, boolean paramBoolean, int paramInt) {
/* 493 */     println("<br /><br /><table summary=\"" + paramEntityGroup.getLongDescription() + "\" width=\"100%\">" + "\n");
/*     */     
/* 495 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 496 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 497 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 498 */       if (paramBoolean) {
/* 499 */         if (b == 0) {
/* 500 */           println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute" + paramInt + "\">Navigation Attribute</th>");
/* 501 */           println("<th class=\"PsgLabel\" width=\"50%\" id=\"value" + paramInt + "\">Attribute Value</th></tr>");
/*     */         } 
/* 503 */         if (eANMetaAttribute.isNavigate()) {
/* 504 */           println("<tr><td headers=\"attribute" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td>");
/* 505 */           println("<td headers=\"value" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */         } 
/*     */       } else {
/*     */         
/* 509 */         if (b == 0) {
/* 510 */           println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute" + paramInt + "\">Attribute Description</th>");
/* 511 */           println("<th class=\"PsgLabel\" width=\"50%\" id=\"value" + paramInt + "\">Attribute Value</th></tr>");
/*     */         } 
/* 513 */         println("<tr><td headers=\"attribute" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td>");
/* 514 */         println("<td headers=\"value" + paramInt + "\" class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       } 
/*     */     } 
/*     */     
/* 518 */     println("</table>");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CRSTATABR05.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */