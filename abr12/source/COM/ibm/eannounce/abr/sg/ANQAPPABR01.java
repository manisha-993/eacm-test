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
/*     */ public class ANQAPPABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
/*     */   public static final String ABR = "ANQAPPABR01";
/*  98 */   private EntityGroup m_egParent = null;
/*  99 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 106 */     byte b = 0;
/*     */ 
/*     */     
/* 109 */     EntityGroup entityGroup1 = null;
/* 110 */     String str1 = null;
/* 111 */     EntityGroup entityGroup2 = null;
/* 112 */     String str2 = null;
/*     */     
/*     */     try {
/* 115 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */       
/* 119 */       buildRptHeader();
/*     */       
/* 121 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 122 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*     */       
/* 124 */       setDGTitle(setDGName(this.m_ei, "ANQAPPABR01"));
/*     */       
/* 126 */       b = 0;
/* 127 */       boolean bool1 = false;
/* 128 */       boolean bool2 = false;
/* 129 */       setReturnCode(0);
/*     */ 
/*     */       
/* 132 */       entityGroup1 = this.m_elist.getEntityGroup("ANNREVIEW");
/* 133 */       str1 = null;
/* 134 */       logMessage("**********EntityItem: " + this.m_ei.dump(false));
/* 135 */       logMessage("**********egANNREVIEW: " + entityGroup1);
/*     */       
/* 137 */       if (entityGroup1 == null) {
/* 138 */         println("****************ANREVIEW Not found ");
/* 139 */         setReturnCode(-1);
/*     */       } else {
/* 141 */         logMessage("****************ANREVIEW Found ");
/* 142 */         b = 0;
/* 143 */         for (; b < entityGroup1.getEntityItemCount(); 
/* 144 */           b++) {
/* 145 */           EntityItem entityItem1 = entityGroup1.getEntityItem(b);
/*     */           
/* 147 */           String str3 = getAttributeValue(entityItem1
/* 148 */               .getEntityType(), entityItem1
/* 149 */               .getEntityID(), "ANREVIEW", "<em>** Not Populated ** </em>");
/*     */ 
/*     */ 
/*     */           
/* 153 */           String str4 = getAttributeValue(entityItem1
/* 154 */               .getEntityType(), entityItem1
/* 155 */               .getEntityID(), "ANNREVIEWDEF", "<em>** Not Populated ** </em>");
/*     */ 
/*     */ 
/*     */           
/* 159 */           logMessage("strANNREVIEW: " + str3 + ", strANNREVIEWDEF: " + str4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 165 */           if ((str3.equals("Approved") || str3
/* 166 */             .equals("Proceed with Risk")) && str4
/* 167 */             .equals("Final Review"))
/*     */           {
/* 169 */             if (str3.equals("Approved")) {
/* 170 */               str1 = "114";
/*     */             }
/* 172 */             else if (str3.equals("Proceed with Risk")) {
/* 173 */               str1 = "115";
/*     */             } 
/*     */           }
/*     */           
/* 177 */           logMessage("strANCYCLESTATUS " + str1);
/* 178 */           if (str1 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 184 */             EntityItem entityItem2 = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */             
/* 188 */             ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem2.getEntityType(), entityItem2.getEntityID(), true);
/*     */             
/* 190 */             DatePackage datePackage = this.m_db.getDates();
/* 191 */             String str5 = datePackage.getNow();
/* 192 */             String str6 = datePackage.getForever();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 200 */             ControlBlock controlBlock = new ControlBlock(str5, str6, str5, str6, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 205 */             SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), "ANCYCLESTATUS", str1, 1, controlBlock);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 210 */             Vector<SingleFlag> vector = new Vector();
/* 211 */             Vector<ReturnEntityKey> vector1 = new Vector();
/*     */             
/* 213 */             if (singleFlag != null) {
/* 214 */               vector.addElement(singleFlag);
/*     */             }
/*     */             
/* 217 */             returnEntityKey.m_vctAttributes = vector;
/* 218 */             vector1.addElement(returnEntityKey);
/*     */             
/* 220 */             this.m_db.update(this.m_prof, vector1, false, false);
/* 221 */             this.m_db.commit();
/* 222 */             bool1 = true;
/* 223 */             logMessage("After update of ANNOUNCEMENT COLUMN");
/*     */           } else {
/* 225 */             bool2 = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 232 */       this.m_prof.setValOn(getValOn());
/* 233 */       this.m_prof.setEffOn(getEffOn());
/* 234 */       start_ABRBuild();
/*     */ 
/*     */       
/* 237 */       entityGroup2 = this.m_elist.getParentEntityGroup();
/* 238 */       EntityItem entityItem = entityGroup2.getEntityItem(0);
/* 239 */       if (entityGroup2 == null) {
/* 240 */         logMessage("****************ANNOUNCEMENT Not found ");
/* 241 */         setReturnCode(-1);
/*     */       } else {
/* 243 */         EANAttribute eANAttribute = entityItem.getAttribute("ANNDESC");
/* 244 */         println("<br /><b>" + entityGroup2.getLongDescription() + "</b>");
/*     */         
/* 246 */         printNavAttributes(entityItem, entityGroup2, true);
/*     */ 
/*     */         
/* 249 */         str2 = (eANAttribute != null) ? eANAttribute.toString() : "<em>** Not Populated ** </em>";
/*     */         
/* 251 */         println("<br /><table summary=\"Announcement\" width=\"100%\"><tr><td class=\"PsgLabel\">Announcement Description</td></tr><tr><td class=\"PsgText\">" + str2 + "</td></tr></table>");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       if (bool1 && str1 != null) {
/*     */         
/* 259 */         EANAttribute eANAttribute = entityItem.getAttribute("ANCYCLESTATUS");
/* 260 */         if (eANAttribute != null) {
/* 261 */           EANMetaAttribute eANMetaAttribute = eANAttribute.getMetaAttribute();
/* 262 */           if (eANMetaAttribute != null && 
/* 263 */             eANAttribute instanceof COM.ibm.eannounce.objects.EANFlagAttribute) {
/* 264 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 265 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/* 266 */               String str = arrayOfMetaFlag[b1].getFlagCode();
/* 267 */               if (arrayOfMetaFlag[b1].isSelected() && str
/* 268 */                 .equals(str1)) {
/* 269 */                 logMessage("****************ANNOUNCEMENT strFlagCode: " + str);
/*     */ 
/*     */                 
/* 272 */                 println("<br /><b>Announcement set to '" + arrayOfMetaFlag[b1]
/*     */                     
/* 274 */                     .getLongDescription() + "' </b>");
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 284 */       if (!bool1 && bool2) {
/* 285 */         println("<br /><b>Completed Final Review not found </b>");
/* 286 */         setReturnCode(-1);
/*     */       } 
/*     */       
/* 289 */       println("<br /><b>" + 
/*     */           
/* 291 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 294 */               getABRDescription(), 
/* 295 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 298 */       log(
/* 299 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 302 */               getABRDescription(), 
/* 303 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 304 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 305 */       setReturnCode(-2);
/* 306 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 310 */           .getMessage() + "</h3>");
/*     */       
/* 312 */       logError(lockPDHEntityException.getMessage());
/* 313 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 314 */       setReturnCode(-2);
/* 315 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 317 */           .getMessage() + "</h3>");
/*     */       
/* 319 */       logError(updatePDHEntityException.getMessage());
/* 320 */     } catch (Exception exception) {
/*     */       
/* 322 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 323 */       println("" + exception);
/*     */ 
/*     */       
/* 326 */       if (getABRReturnCode() != -2) {
/* 327 */         setReturnCode(-3);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 333 */       setDGString(getABRReturnCode());
/* 334 */       setDGRptName("ANQAPPABR01");
/* 335 */       setDGRptClass("ANQAPPABR01");
/*     */       
/* 337 */       if (!isReadOnly()) {
/* 338 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 343 */     printDGSubmitString();
/* 344 */     println(EACustom.getTOUDiv());
/* 345 */     buildReportFooter();
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
/* 356 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 366 */     return "<br /><br />";
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
/* 377 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 387 */     return "1.14";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 396 */     return "ANQAPPABR01.java,v 1.14 2006/03/13 19:42:03 couto Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 404 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 412 */     String str1 = getVersion();
/* 413 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 414 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 421 */     println(EACustom.getDocTypeHtml());
/* 422 */     println("<head>");
/* 423 */     println(EACustom.getMetaTags("ANQAPPABR01.java"));
/* 424 */     println(EACustom.getCSS());
/* 425 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 426 */     println("</head>");
/* 427 */     println("<body id=\"ibm-com\">");
/* 428 */     println(EACustom.getMastheadDiv());
/*     */     
/* 430 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/*     */     
/* 432 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*     */         
/* 434 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*     */         
/* 436 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*     */         
/* 438 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*     */         
/* 440 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*     */     
/* 442 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup, boolean paramBoolean) {
/* 452 */     println("<br /><br /><table summary=\"" + paramEntityGroup.getLongDescription() + "\" width=\"100%\">" + "\n");
/*     */     
/* 454 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 455 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 456 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 457 */       if (paramBoolean) {
/* 458 */         if (b == 0) {
/* 459 */           println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute\">Navigation Attribute</th>");
/* 460 */           println("<th class=\"PsgLabel\" width=\"50%\" id=\"value\">Attribute Value</th></tr>");
/*     */         } 
/* 462 */         if (eANMetaAttribute.isNavigate()) {
/* 463 */           println("<tr><td headers=\"attribute\" class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td>");
/* 464 */           println("<td headers=\"value\" class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */         } 
/*     */       } else {
/*     */         
/* 468 */         if (b == 0) {
/* 469 */           println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute\">Attribute Description</th>");
/* 470 */           println("<th class=\"PsgLabel\" width=\"50%\" id=\"value\">Attribute Value</th></tr>");
/*     */         } 
/* 472 */         println("<tr><td headers=\"attribute\" class=\"PsgText\" width=\"50%\">" + eANMetaAttribute.getLongDescription() + "</td>");
/* 473 */         println("<td headers=\"value\" class=\"PsgText\" width=\"50%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*     */       } 
/*     */     } 
/*     */     
/* 477 */     println("</table>");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ANQAPPABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */