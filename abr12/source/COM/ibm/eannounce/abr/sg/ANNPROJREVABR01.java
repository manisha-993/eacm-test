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
/*     */ import COM.ibm.opicmpdh.objects.Text;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
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
/*     */ public class ANNPROJREVABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
/*     */   public static final String DEF_NOT_POPULATED_HTML = "";
/*     */   public static final String ABR = "ANNPROJREVABR01";
/* 145 */   private EntityGroup m_egParent = null;
/* 146 */   private EntityItem m_ei = null;
/* 147 */   private final String RETURN = System.getProperty("line.separator");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 153 */     byte b = 0;
/* 154 */     String str1 = null;
/* 155 */     EntityGroup entityGroup1 = null;
/* 156 */     EntityGroup entityGroup2 = null;
/* 157 */     EntityGroup entityGroup3 = null;
/* 158 */     DatePackage datePackage = null;
/* 159 */     String str2 = null;
/* 160 */     EntityGroup entityGroup4 = null;
/* 161 */     String str3 = null;
/* 162 */     StringWriter stringWriter = null;
/*     */     try {
/* 164 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 168 */       buildRptHeader();
/*     */       
/* 170 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 171 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*     */       
/* 173 */       setDGTitle(setDGName(this.m_ei, "ANNPROJREVABR01"));
/*     */       
/* 175 */       b = 0;
/* 176 */       str1 = null;
/* 177 */       setReturnCode(0);
/*     */ 
/*     */       
/* 180 */       entityGroup1 = this.m_elist.getEntityGroup("ANNPROJ");
/* 181 */       logMessage("***************  getEntityGroup: " + entityGroup1
/* 182 */           .dump(false));
/* 183 */       logMessage("***************  getEntityItemCount: " + entityGroup1
/*     */           
/* 185 */           .getEntityItemCount());
/* 186 */       if (entityGroup1.getEntityItemCount() > 0) {
/* 187 */         println("<br /><b>Announcement Project</b>");
/*     */         
/* 189 */         b = 0;
/* 190 */         for (; b < entityGroup1.getEntityItemCount(); 
/* 191 */           b++) {
/* 192 */           EntityItem entityItem = entityGroup1.getEntityItem(b);
/* 193 */           EANAttribute eANAttribute = entityItem.getAttribute("ANNPROJNAME");
/* 194 */           if (eANAttribute.toString().length() > 0) {
/* 195 */             str1 = eANAttribute.toString();
/*     */           }
/* 197 */           println(
/* 198 */               getNavigateAttrAndOtherAttr(entityItem, entityGroup1, b));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 204 */       entityGroup2 = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/* 205 */       if (entityGroup2.getEntityItemCount() > 0) {
/* 206 */         println("<br /><b>Announcement</b>");
/* 207 */         println("<br /><br /><table summary=\"Announcement\" width=\"100%\">" + this.RETURN);
/* 208 */         for (b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 209 */           EntityItem entityItem = entityGroup2.getEntityItem(b);
/* 210 */           println(getAttributes(entityItem, entityGroup2, true, b));
/*     */         } 
/* 212 */         println("</table>");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 218 */       entityGroup3 = this.m_elist.getParentEntityGroup();
/*     */ 
/*     */       
/* 221 */       if (entityGroup3 != null) {
/*     */         
/* 223 */         println("<br /><b>Announcement Project Interlock</b>");
/* 224 */         setText("ANNPROJREVNAME", str1);
/*     */ 
/*     */ 
/*     */         
/* 228 */         datePackage = this.m_db.getDates();
/* 229 */         str2 = datePackage.getNow();
/*     */         
/* 231 */         this.m_prof.setValOn(str2);
/* 232 */         this.m_prof.setEffOn(str2);
/*     */ 
/*     */         
/* 235 */         start_ABRBuild();
/*     */         
/* 237 */         entityGroup4 = this.m_elist.getParentEntityGroup();
/*     */         
/* 239 */         if (entityGroup4 != null) {
/* 240 */           println("<br /><table summary=\"Announcement Project Interlock\" width=\"100%\">" + this.RETURN);
/* 241 */           EntityItem entityItem = entityGroup4.getEntityItem(0);
/* 242 */           println(getAttributes(entityItem, entityGroup4, false, 0));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 247 */       println("<br /><b>" + 
/*     */           
/* 249 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 252 */               getABRDescription(), 
/* 253 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 256 */       log(
/* 257 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 260 */               getABRDescription(), 
/* 261 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 262 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 263 */       setReturnCode(-2);
/* 264 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 268 */           .getMessage() + "</h3>");
/*     */       
/* 270 */       logError(lockPDHEntityException.getMessage());
/* 271 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 272 */       setReturnCode(-2);
/* 273 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 275 */           .getMessage() + "</h3>");
/*     */       
/* 277 */       logError(updatePDHEntityException.getMessage());
/* 278 */     } catch (Exception exception) {
/*     */       
/* 280 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 281 */       println("" + exception);
/*     */ 
/*     */       
/* 284 */       if (getABRReturnCode() != -2) {
/* 285 */         setReturnCode(-3);
/*     */       }
/* 287 */       exception.printStackTrace();
/*     */       
/* 289 */       stringWriter = new StringWriter();
/* 290 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 291 */       str3 = stringWriter.toString();
/* 292 */       println(str3);
/*     */       
/* 294 */       logMessage("Error in " + this.m_abri
/* 295 */           .getABRCode() + ":" + exception.getMessage());
/* 296 */       logMessage("" + exception);
/*     */     }
/*     */     finally {
/*     */       
/* 300 */       if (getReturnCode() == 0) {
/* 301 */         setReturnCode(0);
/*     */       }
/*     */       
/* 304 */       setDGString(getABRReturnCode());
/* 305 */       setDGRptName("ANNPROJREVABR01");
/* 306 */       setDGRptClass("ANNPROJREVABR01");
/*     */       
/* 308 */       if (!isReadOnly()) {
/* 309 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 314 */     printDGSubmitString();
/* 315 */     println(EACustom.getTOUDiv());
/* 316 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigateAttrAndOtherAttr(EntityItem paramEntityItem, EntityGroup paramEntityGroup, int paramInt) {
/* 327 */     StringBuffer stringBuffer = new StringBuffer();
/* 328 */     if (paramInt == 0) {
/* 329 */       stringBuffer.append(this.RETURN);
/* 330 */       stringBuffer.append("<table summary=\"Announcement Project\" width=\"100%\">" + this.RETURN + "<tr><th id=\"attrDesc" + paramInt + "\" class=\"PsgLabel\">Attribute Description</th><th id=\"attrValue" + paramInt + "\" class=\"PsgLabel\">Attribute Value</th></tr>");
/*     */     } 
/*     */     
/*     */     byte b;
/*     */     
/* 335 */     for (b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 336 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 337 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 338 */       String str = null;
/* 339 */       if (eANAttribute != null && eANAttribute.toString().length() > 0) {
/* 340 */         str = eANAttribute.toString();
/*     */       }
/* 342 */       if (eANMetaAttribute.isNavigate()) {
/* 343 */         stringBuffer.append("<tr><td headers=\"attrDesc" + paramInt + "\" class=\"PsgText\">" + eANMetaAttribute
/*     */             
/* 345 */             .getLongDescription() + "</td><td headers=\"attrValue" + paramInt + "\" class=\"PsgText\">" + ((str == null) ? "** Not Populated **" : str) + "</td></tr>");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 351 */     for (b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 352 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 353 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 354 */       String str = null;
/* 355 */       if (eANAttribute != null && eANAttribute.toString().length() > 0) {
/* 356 */         str = eANAttribute.toString();
/*     */       }
/* 358 */       if (!eANMetaAttribute.isNavigate()) {
/* 359 */         stringBuffer.append("<tr><td headers=\"attrDesc" + paramInt + "\" class=\"PsgText\">" + eANMetaAttribute
/*     */             
/* 361 */             .getLongDescription() + "</td><td headers=\"attrValue" + paramInt + "\" class=\"PsgText\">" + ((str == null) ? "** Not Populated **" : str) + "</td></tr>");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     if (paramInt == 0) {
/* 369 */       stringBuffer.append("</table>");
/*     */     }
/* 371 */     return stringBuffer.toString();
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
/*     */   private String getAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup, boolean paramBoolean, int paramInt) {
/* 384 */     StringBuffer stringBuffer = new StringBuffer();
/* 385 */     if (paramBoolean) {
/* 386 */       if (paramInt == 0) {
/* 387 */         stringBuffer.append("<tr>");
/* 388 */         for (byte b1 = 0; b1 < paramEntityGroup.getMetaAttributeCount(); b1++) {
/* 389 */           EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b1);
/* 390 */           if (eANMetaAttribute.isNavigate()) {
/* 391 */             stringBuffer.append("<th id=\"attribute" + b1 + "\" class=\"PsgLabel\">" + eANMetaAttribute
/*     */                 
/* 393 */                 .getAttributeCode() + "</th>");
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 398 */         stringBuffer.append("</tr>");
/*     */       } 
/* 400 */       stringBuffer.append("<tr>");
/* 401 */       for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 402 */         EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 403 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 404 */         String str = null;
/* 405 */         if (eANAttribute != null && eANAttribute.toString().length() > 0) {
/* 406 */           str = eANAttribute.toString();
/*     */         }
/*     */         
/* 409 */         if (eANMetaAttribute.isNavigate()) {
/* 410 */           stringBuffer.append("<td headers=\"attribute" + b + "\" class=\"PsgText\">" + ((str == null) ? "** Not Populated **" : str) + "</td>");
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 417 */       stringBuffer.append("</tr>");
/*     */     } else {
/* 419 */       stringBuffer.append(this.RETURN);
/* 420 */       stringBuffer.append("<tr><th id=\"attribute" + paramInt + "\" class=\"PsgLabel\">Attribute Description</th><th id=\"value" + paramInt + "\" class=\"PsgLabel\">Attribute Value</th></tr>");
/*     */ 
/*     */       
/* 423 */       for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 424 */         EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 425 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 426 */         String str = null;
/* 427 */         if (eANAttribute != null && eANAttribute.toString().length() > 0) {
/* 428 */           str = eANAttribute.toString();
/*     */         }
/* 430 */         stringBuffer.append("<tr><td headers=\"attribute" + paramInt + "\" class=\"PsgText\">" + eANMetaAttribute
/*     */             
/* 432 */             .getLongDescription() + "</td><td headers=\"value" + paramInt + "\" class=\"PsgText\">" + ((str == null) ? "** Not Populated **" : str) + "</td></tr>");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 438 */       stringBuffer.append("</table>");
/*     */     } 
/*     */     
/* 441 */     return stringBuffer.toString();
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
/*     */   public void setText(String paramString1, String paramString2) {
/*     */     try {
/* 459 */       logMessage("****before update: " + this.m_elist.dump(false));
/* 460 */       if (paramString2 != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 467 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 471 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */         
/* 473 */         DatePackage datePackage = this.m_db.getDates();
/* 474 */         String str1 = datePackage.getNow();
/* 475 */         String str2 = datePackage.getForever();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 484 */         ControlBlock controlBlock = new ControlBlock(str1, str2, str1, str2, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 489 */         Text text = new Text(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, controlBlock);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 494 */         Vector<Text> vector = new Vector();
/* 495 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 497 */         if (text != null) {
/*     */           try {
/* 499 */             vector.addElement(text);
/*     */             
/* 501 */             returnEntityKey.m_vctAttributes = vector;
/* 502 */             vector1.addElement(returnEntityKey);
/*     */             
/* 504 */             this.m_db.update(this.m_prof, vector1, false, false);
/* 505 */             this.m_db.commit();
/* 506 */           } catch (Exception exception) {
/* 507 */             logMessage(this + " trouble updating text value " + exception);
/*     */           } finally {
/* 509 */             this.m_db.freeStatement();
/* 510 */             this.m_db.isPending("finally after update in Text value");
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 533 */     catch (MiddlewareException middlewareException) {
/* 534 */       logMessage("set Text Value: " + middlewareException.getMessage());
/* 535 */     } catch (Exception exception) {
/* 536 */       logMessage("set Text Value:" + exception.getMessage());
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
/* 548 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 558 */     return "<br /><br />";
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
/* 569 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 579 */     return "1.14";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 589 */     return "ANNPROJREVABR01.java,v 1.14 2008/01/30 19:39:14 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 597 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 605 */     String str1 = getVersion();
/* 606 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 607 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 614 */     println(EACustom.getDocTypeHtml());
/* 615 */     println("<head>");
/* 616 */     println(EACustom.getMetaTags("ANNPROJREVABR01.java"));
/* 617 */     println(EACustom.getCSS());
/* 618 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 619 */     println("</head>");
/* 620 */     println("<body id=\"ibm-com\">");
/* 621 */     println(EACustom.getMastheadDiv());
/*     */     
/* 623 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/*     */     
/* 625 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 627 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 629 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 631 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 633 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 635 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ANNPROJREVABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */