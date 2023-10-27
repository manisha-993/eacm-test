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
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ANNREVABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
/*     */   public static final String ABR = "ANNREVABR01";
/* 165 */   private EntityGroup m_egParent = null;
/* 166 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 173 */     String str1 = null;
/* 174 */     String str2 = null;
/* 175 */     EntityGroup entityGroup1 = null;
/* 176 */     String str3 = null;
/* 177 */     EntityGroup entityGroup2 = null;
/* 178 */     EANAttribute eANAttribute = null;
/*     */     
/*     */     try {
/* 181 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 185 */       buildRptHeader();
/*     */       
/* 187 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 188 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*     */       
/* 190 */       setDGTitle(setDGName(this.m_ei, "ANNREVABR01"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       setReturnCode(0);
/*     */       
/* 204 */       entityGroup1 = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/*     */       
/* 206 */       if (entityGroup1 != null) {
/* 207 */         EntityItem entityItem = entityGroup1.getEntityItem(0);
/*     */         
/* 209 */         logMessage("ANNOUNCEMENT EntityItem Count: " + entityGroup1
/*     */             
/* 211 */             .getEntityItemCount());
/* 212 */         logMessage("ANNOUNCEMENT: " + entityItem
/*     */             
/* 214 */             .getEntityType() + ", " + 
/*     */             
/* 216 */             getEntityID());
/*     */ 
/*     */         
/* 219 */         eANAttribute = entityItem.getAttribute("ANNCODENAME");
/* 220 */         if (eANAttribute != null) {
/* 221 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 222 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 223 */             if (arrayOfMetaFlag[b].isSelected()) {
/* 224 */               str2 = arrayOfMetaFlag[b].getFlagCode();
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 232 */         str1 = getAttributeValue(entityItem
/* 233 */             .getEntityType(), entityItem
/* 234 */             .getEntityID(), "ANCYCLESTATUS", "<em>** Not Populated ** </em>");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 240 */       logMessage("ANNCODENAME from ANNOUNCEMENT: " + str2);
/* 241 */       logMessage("ANCYCLESTATUS from ANNOUNCEMENT " + str1);
/*     */ 
/*     */ 
/*     */       
/* 245 */       logMessage("******************After getting all the Announcement Attributes");
/*     */       
/* 247 */       if (str1 != null) {
/* 248 */         if (str1.equals("Draft")) {
/* 249 */           str3 = "101";
/*     */         }
/* 251 */         else if (str1
/* 252 */           .equals("Ready for Final Review") || str1
/* 253 */           .equals("Change(Final Review)")) {
/* 254 */           str3 = "102";
/*     */         }
/* 256 */         else if (str1
/* 257 */           .equals("Approved") || str1
/* 258 */           .equals("Approved with Risk") || str1
/* 259 */           .equals("Announced") || str1
/* 260 */           .equals("Released to Production Management") || str1
/* 261 */           .equals("Change (Approved)") || str1
/* 262 */           .equals("Change (Approved w/Risk)") || str1
/* 263 */           .equals("Change (Released)") || str1
/* 264 */           .equals("Change (Announced)")) {
/* 265 */           str3 = "103";
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 270 */       entityGroup2 = this.m_elist.getParentEntityGroup();
/* 271 */       if (entityGroup2 != null) {
/*     */         
/* 273 */         EntityItem entityItem = entityGroup2.getEntityItem(0);
/* 274 */         setSingleFlag(entityItem
/* 275 */             .getEntityType(), entityItem
/* 276 */             .getEntityID(), "ANNCODENAME", str2);
/*     */ 
/*     */         
/* 279 */         setSingleFlag(entityItem
/* 280 */             .getEntityType(), entityItem
/* 281 */             .getEntityID(), "ANNREVIEWDEF", str3);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 286 */         this.m_prof.setValOn(getValOn());
/* 287 */         this.m_prof.setEffOn(getEffOn());
/* 288 */         start_ABRBuild();
/*     */         
/* 290 */         entityGroup2 = this.m_elist.getParentEntityGroup();
/*     */         
/* 292 */         if (entityGroup2 != null) {
/* 293 */           println("<br /><br /><b>Annoucement Review:</b>");
/* 294 */           entityItem = entityGroup2.getEntityItem(0);
/* 295 */           println(
/* 296 */               getNavigateAttrAndOtherAttr("Announcement Review", entityItem, entityGroup2, 0));
/*     */         } 
/*     */       } else {
/* 299 */         logMessage("Could not find ANNREVIEW");
/* 300 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 304 */       entityGroup1 = this.m_elist.getEntityGroup("ANNOUNCEMENT");
/* 305 */       if (entityGroup1 != null) {
/* 306 */         EntityItem entityItem = entityGroup1.getEntityItem(0);
/* 307 */         println("<br /><br /><b>Annoucement:</b>");
/* 308 */         println("<table summary=\"Announcement\" width=\"100%\">");
/* 309 */         println("<tr><th class=\"PsgLabel\" id=\"attribute\">Navigation Attributes</th>");
/* 310 */         println("<th class=\"PsgLabel\" id=\"value\">Attribute Value</th></tr>");
/* 311 */         for (byte b = 0; b < entityGroup1.getMetaAttributeCount(); b++) {
/* 312 */           EANMetaAttribute eANMetaAttribute = entityGroup1.getMetaAttribute(b);
/* 313 */           if (eANMetaAttribute.isNavigate()) {
/*     */             
/* 315 */             EANAttribute eANAttribute1 = entityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 316 */             println("<tr><td headers=\"attribute\" class=\"PsgText\">" + eANMetaAttribute
/*     */                 
/* 318 */                 .getLongDescription() + "</td><td headers=\"value\" class=\"PsgText\">" + ((eANAttribute1 == null || eANAttribute1
/*     */ 
/*     */                 
/* 321 */                 .toString().length() == 0) ? "**Not Populated**" : eANAttribute1
/*     */                 
/* 323 */                 .toString()) + "</td></tr>");
/*     */           } 
/*     */         } 
/*     */         
/* 327 */         println("</table>\n<br />");
/*     */       } else {
/* 329 */         logMessage("Could not find ANNOUCEMENT");
/* 330 */         setReturnCode(-1);
/*     */       } 
/*     */       
/* 333 */       println("<br /><b>" + 
/*     */           
/* 335 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 338 */               getABRDescription(), 
/* 339 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 342 */       log(
/* 343 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 346 */               getABRDescription(), 
/* 347 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 348 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 349 */       setReturnCode(-2);
/* 350 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 354 */           .getMessage() + "</h3>");
/*     */       
/* 356 */       logError(lockPDHEntityException.getMessage());
/* 357 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 358 */       setReturnCode(-2);
/* 359 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 361 */           .getMessage() + "</h3>");
/*     */       
/* 363 */       logError(updatePDHEntityException.getMessage());
/* 364 */     } catch (Exception exception) {
/*     */       
/* 366 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 367 */       println("" + exception);
/*     */ 
/*     */       
/* 370 */       if (getABRReturnCode() != -2) {
/* 371 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 375 */       if (getReturnCode() == 0) {
/* 376 */         setReturnCode(0);
/*     */       }
/*     */       
/* 379 */       setDGString(getABRReturnCode());
/* 380 */       setDGRptName("ANNREVABR01");
/* 381 */       setDGRptClass("ANNREVABR01");
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
/*     */   private String getNavigateAttrAndOtherAttr(String paramString, EntityItem paramEntityItem, EntityGroup paramEntityGroup, int paramInt) {
/* 402 */     StringBuffer stringBuffer = new StringBuffer();
/* 403 */     if (paramInt == 0) {
/* 404 */       stringBuffer.append("<br /><table summary=\"" + paramString + "\" width=\"100%\">" + "\n");
/* 405 */       stringBuffer.append("<tr>\n<th class=\"PsgLabel\" id=\"description\">Attribute Description</th>\n<th class=\"PsgLabel\" id=\"attrvalue\">Attribute Value</th>\n</tr>\n");
/*     */     } 
/*     */ 
/*     */     
/*     */     byte b;
/*     */ 
/*     */     
/* 412 */     for (b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 413 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 414 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 415 */       String str = null;
/* 416 */       if (eANAttribute != null && eANAttribute.toString().length() > 0) {
/* 417 */         str = eANAttribute.toString();
/*     */       }
/* 419 */       if (eANMetaAttribute.isNavigate()) {
/* 420 */         stringBuffer.append("<tr><td headers=\"description\" class=\"PsgText\">" + eANMetaAttribute
/*     */             
/* 422 */             .getLongDescription() + "</td><td headers=\"attrvalue\" class=\"PsgText\">" + ((str == null) ? "** Not Populated **" : str) + "</td></tr>");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 428 */     for (b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 429 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 430 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 431 */       String str = null;
/* 432 */       if (eANAttribute != null && eANAttribute.toString().length() > 0) {
/* 433 */         str = eANAttribute.toString();
/*     */       }
/* 435 */       if (!eANMetaAttribute.isNavigate()) {
/* 436 */         stringBuffer.append("<tr><td headers=\"description\" class=\"PsgText\">" + eANMetaAttribute
/*     */             
/* 438 */             .getLongDescription() + "</td><td headers=\"value\" class=\"PsgText\">" + ((str == null) ? "** Not Populated **" : str) + "</td></tr>");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     if (paramInt == 0) {
/* 446 */       stringBuffer.append("</table>");
/*     */     }
/* 448 */     return stringBuffer.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSingleFlag(String paramString1, int paramInt, String paramString2, String paramString3) {
/*     */     try {
/* 517 */       println("****** " + paramString1 + ":" + paramInt + " " + paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 525 */       if (paramString3 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 532 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 536 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */         
/* 538 */         DatePackage datePackage = this.m_db.getDates();
/* 539 */         String str1 = datePackage.getNow();
/* 540 */         String str2 = datePackage.getForever();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 549 */         ControlBlock controlBlock = new ControlBlock(str1, str2, str1, str2, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 554 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString2, paramString3, 1, controlBlock);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 559 */         Vector<SingleFlag> vector = new Vector();
/* 560 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 562 */         if (singleFlag != null) {
/* 563 */           vector.addElement(singleFlag);
/*     */           
/* 565 */           returnEntityKey.m_vctAttributes = vector;
/* 566 */           vector1.addElement(returnEntityKey);
/*     */           
/* 568 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 569 */           this.m_db.commit();
/*     */         } 
/*     */       } 
/* 572 */     } catch (MiddlewareException middlewareException) {
/* 573 */       logMessage("set Text Value: " + middlewareException.getMessage());
/* 574 */     } catch (Exception exception) {
/* 575 */       logMessage("set Text Value:" + exception.getMessage());
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
/* 587 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 597 */     return "<br /><br />";
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
/* 608 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 618 */     return "1.12";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 627 */     return "ANNREVABR01.java,v 1.12 2008/01/30 19:39:16 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 634 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 642 */     String str1 = getVersion();
/* 643 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 644 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 651 */     println(EACustom.getDocTypeHtml());
/* 652 */     println("<head>");
/* 653 */     println(EACustom.getMetaTags("ANNREVABR01.java"));
/* 654 */     println(EACustom.getCSS());
/* 655 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 656 */     println("</head>");
/* 657 */     println("<body id=\"ibm-com\">");
/* 658 */     println(EACustom.getMastheadDiv());
/*     */     
/* 660 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/*     */     
/* 662 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 664 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 666 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 668 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 670 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 672 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ANNREVABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */