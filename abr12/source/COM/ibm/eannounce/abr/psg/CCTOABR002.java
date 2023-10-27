/*     */ package COM.ibm.eannounce.abr.psg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.WorkflowActionItem;
/*     */ import COM.ibm.eannounce.objects.WorkflowException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.SortUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
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
/*     */ public final class CCTOABR002
/*     */   extends PokBaseABR
/*     */ {
/*  92 */   private ResourceBundle messages = ResourceBundle.getBundle(getClass().getName(), getLocale(1));
/*  93 */   private StringBuffer report = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  99 */     StringBuffer stringBuffer1 = this.report;
/* 100 */     MessageFormat messageFormat = null;
/* 101 */     ResourceBundle resourceBundle = null;
/* 102 */     String[] arrayOfString = new String[10];
/* 103 */     StringBuffer stringBuffer2 = new StringBuffer();
/* 104 */     resourceBundle = this.messages;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 114 */       start_ABRBuild();
/*     */       
/* 116 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/* 117 */       Iterator<EANMetaAttribute> iterator = entityGroup.getMetaAttribute().values().iterator();
/* 118 */       while (iterator.hasNext()) {
/* 119 */         EANMetaAttribute eANMetaAttribute = iterator.next();
/* 120 */         stringBuffer2.append(
/* 121 */             getAttributeValue(
/* 122 */               getRootEntityType(), 
/* 123 */               getRootEntityID(), eANMetaAttribute
/* 124 */               .getAttributeCode()));
/* 125 */         if (iterator.hasNext()) {
/* 126 */           stringBuffer2.append(" ");
/*     */         }
/*     */       } 
/* 129 */       this
/* 130 */         .messages = ResourceBundle.getBundle(
/* 131 */           getClass().getName(), 
/* 132 */           getLocale(this.m_prof.getReadLanguage().getNLSID()));
/* 133 */       resourceBundle = this.messages;
/*     */ 
/*     */       
/* 136 */       messageFormat = new MessageFormat(resourceBundle.getString("PATH"));
/* 137 */       Iterator<EntityItem> iterator1 = this.m_elist.getEntityGroup("PR").getEntityItem().values().iterator();
/* 138 */       while (iterator1.hasNext()) {
/* 139 */         EntityItem entityItem = iterator1.next();
/* 140 */         arrayOfString[0] = getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), "BRANDCODE");
/* 141 */         arrayOfString[1] = getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), "FAMNAMEASSOC");
/* 142 */         arrayOfString[2] = getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), "SENAMEASSOC");
/* 143 */         arrayOfString[3] = getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), "NAME");
/* 144 */         stringBuffer1.append(messageFormat.format(arrayOfString));
/*     */       } 
/*     */ 
/*     */       
/* 148 */       printEntity(getRootEntityType(), getRootEntityID(), 0);
/*     */ 
/*     */       
/* 151 */       messageFormat = new MessageFormat(resourceBundle.getString("PARENT_STATUS"));
/* 152 */       Vector<Integer> vector = getEntityIds("CTO", "CTOCCTO");
/* 153 */       Iterator<Integer> iterator2 = vector.iterator();
/* 154 */       while (iterator2.hasNext()) {
/* 155 */         int i = ((Integer)iterator2.next()).intValue();
/* 156 */         arrayOfString[0] = getEntityDescription("CTO");
/* 157 */         arrayOfString[1] = getAttributeValue("CTO", i, "NAME");
/* 158 */         arrayOfString[2] = getAttributeDescription("CTO", "COFSTATUS");
/* 159 */         arrayOfString[3] = getAttributeValue("CTO", i, "COFSTATUS");
/* 160 */         stringBuffer1.append(messageFormat.format(arrayOfString));
/*     */       } 
/*     */ 
/*     */       
/* 164 */       String str1 = getAttributeFlagEnabledValue("CTO", ((Integer)vector.firstElement()).intValue(), "COFSTATUS", "");
/* 165 */       String str2 = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "CCOSOLSTATUS", "");
/* 166 */       if (str1 == null || str2 == null) {
/* 167 */         stringBuffer1.append(resourceBundle.getString("STATUS_ERROR"));
/* 168 */         setReturnCode(-1);
/*     */       } 
/* 170 */       setReturnCode(0);
/* 171 */       if ("0040".equals(str2)) {
/* 172 */         if ("0020".equals(str1)) {
/* 173 */           EntityGroup entityGroup1 = this.m_elist.getEntityGroup("CCTOSBB");
/* 174 */           if (entityGroup1 == null || entityGroup1.getEntityItemCount() == 0) {
/* 175 */             this.report.append(resourceBundle.getString("ERROR_NO_SBB"));
/* 176 */             setReturnCode(-1);
/*     */           }
/* 178 */           else if (checkOverrideDate()) {
/* 179 */             setReturnCode(-1);
/*     */           }
/* 181 */           else if (checkSBBLinkage()) {
/* 182 */             this.report.append(resourceBundle.getString("ERROR_SBB_LINKAGE"));
/* 183 */             setReturnCode(-1);
/*     */           } else {
/*     */             
/* 186 */             triggerWorkFlow("WFCCOSOLSTATUSFINAL");
/*     */ 
/*     */             
/* 189 */             stringBuffer1.append(resourceBundle.getString("PASS_MSG"));
/* 190 */             setReturnCode(0);
/*     */           } 
/*     */         } else {
/*     */           
/* 194 */           stringBuffer1.append(resourceBundle.getString("FAIL_MSG"));
/* 195 */           setReturnCode(-1);
/*     */         }
/*     */       
/* 198 */       } else if (!"0020".equals(str2)) {
/* 199 */         EntityGroup entityGroup1 = this.m_elist.getEntityGroup("CCTOSBB");
/* 200 */         if (entityGroup1 == null || entityGroup1.getEntityItemCount() == 0) {
/* 201 */           this.report.append(resourceBundle.getString("ERROR_NO_SBB"));
/* 202 */           setReturnCode(-1);
/*     */         }
/* 204 */         else if (checkSBBLinkage()) {
/* 205 */           this.report.append(resourceBundle.getString("ERROR_SBB_LINKAGE"));
/* 206 */           setReturnCode(-1);
/*     */         } else {
/*     */           
/* 209 */           triggerWorkFlow("WFCCOSOLSTATUSRR");
/* 210 */           stringBuffer1.append(resourceBundle.getString("PASS_MSG"));
/*     */         } 
/*     */       } else {
/*     */         
/* 214 */         stringBuffer1.append(resourceBundle.getString("STD_PASS_MSG"));
/*     */       } 
/* 216 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 217 */       setReturnCode(-1);
/* 218 */       messageFormat = new MessageFormat(resourceBundle.getString("LOCK_ERROR"));
/* 219 */       arrayOfString[0] = "IAB1007E: Could not get soft lock.  Rule execution is terminated.";
/* 220 */       arrayOfString[1] = lockPDHEntityException.getMessage();
/* 221 */       stringBuffer1.append(messageFormat.format(arrayOfString));
/* 222 */       logError(lockPDHEntityException.getMessage());
/* 223 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 224 */       setReturnCode(-1);
/* 225 */       messageFormat = new MessageFormat(resourceBundle.getString("UPDATE_ERROR"));
/* 226 */       arrayOfString[0] = updatePDHEntityException.getMessage();
/* 227 */       stringBuffer1.append(messageFormat.format(arrayOfString));
/* 228 */       logError(updatePDHEntityException.getMessage());
/* 229 */     } catch (Exception exception) {
/*     */       
/* 231 */       setReturnCode(-1);
/*     */       
/* 233 */       messageFormat = new MessageFormat(resourceBundle.getString("EXCEPTION_ERROR"));
/* 234 */       arrayOfString[0] = this.m_abri.getABRCode();
/* 235 */       arrayOfString[1] = exception.getMessage();
/* 236 */       stringBuffer1.append(messageFormat.format(arrayOfString));
/* 237 */       StringWriter stringWriter = new StringWriter();
/* 238 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 239 */       stringBuffer1.append("<pre>");
/* 240 */       stringBuffer1.append(stringWriter.getBuffer().toString());
/* 241 */       stringBuffer1.append("</pre>");
/*     */     } finally {
/* 243 */       setDGTitle(stringBuffer2.toString());
/* 244 */       setDGRptName(getShortClassName(getClass()));
/* 245 */       setDGRptClass("CTYABR");
/*     */       
/* 247 */       if (!isReadOnly()) {
/* 248 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */     
/* 252 */     stringBuffer2.append((getReturnCode() == 0) ? " Passed" : " Failed");
/* 253 */     messageFormat = new MessageFormat(resourceBundle.getString("HEADER"));
/* 254 */     arrayOfString[0] = getShortClassName(getClass());
/* 255 */     arrayOfString[1] = stringBuffer2.toString();
/* 256 */     arrayOfString[2] = getNow();
/* 257 */     arrayOfString[3] = this.m_prof.getOPName();
/* 258 */     arrayOfString[4] = this.m_prof.getRoleDescription();
/* 259 */     arrayOfString[5] = getDescription();
/* 260 */     arrayOfString[6] = getABRVersion();
/* 261 */     stringBuffer1.insert(0, messageFormat.format(arrayOfString));
/* 262 */     println(stringBuffer1.toString());
/* 263 */     printDGSubmitString();
/* 264 */     buildReportFooter();
/*     */   }
/*     */   
/*     */   private boolean checkSBBLinkage() {
/* 268 */     boolean bool = false;
/* 269 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CCTOSBB");
/* 270 */     if (entityGroup == null) {
/* 271 */       logError("Extract Definition Problem: No CCTOSBB");
/*     */     } else {
/*     */       
/* 274 */       EntityGroup entityGroup1 = this.m_elist.getEntityGroup("CTOSBB");
/* 275 */       if (entityGroup1 == null) {
/* 276 */         logError("Extract Definition Problem: No CTOSBB");
/*     */       } else {
/*     */         
/* 279 */         int i = entityGroup.getEntityItemCount();
/* 280 */         int j = entityGroup1.getEntityItemCount();
/* 281 */         HashSet<Integer> hashSet1 = new HashSet();
/* 282 */         HashSet<Integer> hashSet2 = new HashSet(); byte b;
/* 283 */         for (b = 0; b < i; b++) {
/* 284 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/* 285 */           entityItem = (EntityItem)entityItem.getDownLink(0);
/* 286 */           hashSet1.add(new Integer(entityItem.getEntityID()));
/*     */         } 
/* 288 */         for (b = 0; b < j; b++) {
/* 289 */           EntityItem entityItem = entityGroup1.getEntityItem(b);
/* 290 */           entityItem = (EntityItem)entityItem.getDownLink(0);
/* 291 */           hashSet2.add(new Integer(entityItem.getEntityID()));
/*     */         } 
/* 293 */         if (!hashSet2.containsAll(hashSet1)) {
/* 294 */           bool = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 298 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkOverrideDate() {
/* 306 */     boolean bool = false;
/* 307 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CCTOSBB");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 329 */     if (entityGroup != null) {
/* 330 */       int i = entityGroup.getEntityItemCount();
/* 331 */       for (byte b = 0; b < i; b++) {
/* 332 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 333 */         String str1 = getAttributeValue(entityItem, "SBBUNPUBLISHCTDATE", null);
/* 334 */         String str2 = getAttributeValue(entityItem, "SBBPUBLISHCTDATE", null);
/* 335 */         if (str2 != null) {
/* 336 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 337 */           String str = getAttributeValue(entityItem1, "SBBANNOUNCETGT", null);
/*     */           
/* 339 */           if (str != null && str2.compareTo(str) < 0) {
/*     */             
/* 341 */             this.report.append(this.messages.getString("ERROR_OVERRIDE_PUBLISH_DATE"));
/* 342 */             bool = true;
/*     */           } 
/*     */         } 
/*     */         
/* 346 */         if (str1 != null) {
/* 347 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 348 */           String str = getAttributeValue(entityItem1, "SBBWITHDRAWLDATE", null);
/*     */           
/* 350 */           if (str != null && str1.compareTo(str) > 0) {
/*     */             
/* 352 */             this.report.append(this.messages.getString("ERROR_OVERRIDE_WITHDRAW_DATE"));
/* 353 */             bool = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 358 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 367 */     return this.messages.getString("DESCRIPTION");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 376 */     return "$Revision: 1.27 $";
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
/*     */   private Locale getLocale(int paramInt) {
/* 434 */     Locale locale = null;
/* 435 */     switch (paramInt)
/*     */     { case 1:
/* 437 */         locale = Locale.US;
/*     */       case 2:
/* 439 */         locale = Locale.GERMAN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 459 */         return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/*     */   public void printEntity(String paramString, int paramInt1, int paramInt2) {
/* 473 */     String str = "";
/* 474 */     logMessage("In printEntity _strEntityType" + paramString + ":_iEntityID:" + paramInt1 + ":_iLevel:" + paramInt2);
/*     */ 
/*     */     
/* 477 */     switch (paramInt2) {
/*     */       case 0:
/* 479 */         str = "PsgReportSection";
/*     */         break;
/*     */       case 1:
/* 482 */         str = "PsgReportSectionII";
/*     */         break;
/*     */       case 2:
/* 485 */         str = "PsgReportSectionIII";
/*     */         break;
/*     */       case 3:
/* 488 */         str = "PsgReportSectionIV";
/*     */         break;
/*     */       default:
/* 491 */         str = "PsgReportSectionV";
/*     */         break;
/*     */     } 
/* 494 */     logMessage("Printing table width");
/* 495 */     this.report.append("<table width=\"100%\"><tr><td class=\"");
/* 496 */     this.report.append(str);
/* 497 */     this.report.append("\">");
/* 498 */     this.report.append(getEntityDescription(paramString));
/* 499 */     this.report.append(": ");
/* 500 */     this.report.append(getAttributeValue(paramString, paramInt1, "NAME", "<em>** Not Populated **</em>"));
/* 501 */     this.report.append("</td></tr></table>");
/* 502 */     logMessage("Printing Attributes");
/* 503 */     printAttributes(paramString, paramInt1, false, false);
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
/*     */   public void printAttributes(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 518 */     printAttributes(this.m_elist, paramString, paramInt, paramBoolean1, paramBoolean2);
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
/*     */   public void printAttributes(EntityList paramEntityList, String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 542 */     StringBuffer stringBuffer = this.report;
/* 543 */     EntityItem entityItem = null;
/* 544 */     EntityGroup entityGroup = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 551 */     SortUtil sortUtil = new SortUtil();
/*     */ 
/*     */     
/* 554 */     logMessage("in Print Attributes _strEntityType" + paramString + ":_iEntityID:" + paramInt);
/*     */     
/* 556 */     if (paramString.equals(getEntityType())) {
/*     */       
/* 558 */       entityGroup = paramEntityList.getParentEntityGroup();
/*     */     } else {
/* 560 */       entityGroup = paramEntityList.getEntityGroup(paramString);
/*     */     } 
/*     */     
/* 563 */     if (entityGroup == null) {
/* 564 */       stringBuffer.append("<h3>Warning: Cannot locate an EnityGroup for ");
/* 565 */       stringBuffer.append(paramString);
/* 566 */       stringBuffer.append(" so no attributes will be printed.</h3>");
/*     */       
/*     */       return;
/*     */     } 
/* 570 */     entityItem = entityGroup.getEntityItem(paramString, paramInt);
/*     */     
/* 572 */     if (entityItem == null) {
/*     */       
/* 574 */       entityItem = entityGroup.getEntityItem(0);
/* 575 */       stringBuffer.append("<h3>Warning: Attributes for ");
/* 576 */       stringBuffer.append(paramString);
/* 577 */       stringBuffer.append(":");
/* 578 */       stringBuffer.append(paramInt);
/* 579 */       stringBuffer.append(" cannot be printed as it is not available in the Extract.</h3>");
/* 580 */       stringBuffer.append("<h3>Warning: Root Entityis ");
/* 581 */       stringBuffer.append(getEntityType());
/* 582 */       stringBuffer.append(":");
/* 583 */       stringBuffer.append(getEntityID());
/* 584 */       stringBuffer.append(".</h3>");
/*     */       
/*     */       return;
/*     */     } 
/* 588 */     String str = entityGroup.getLongDescription();
/* 589 */     logMessage("Print Attributes Entity desc is " + str);
/* 590 */     logMessage("Attribute count is" + entityItem.getAttributeCount());
/* 591 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 592 */     Vector<String> vector = new Vector(); byte b;
/* 593 */     for (b = 0; b < entityItem.getAttributeCount(); b++) {
/* 594 */       EANAttribute eANAttribute = entityItem.getAttribute(b);
/*     */       
/* 596 */       String str2 = "";
/* 597 */       logMessage("printAttributes " + eANAttribute.dump(false));
/* 598 */       logMessage("printAttributes " + eANAttribute.dump(true));
/*     */ 
/*     */       
/* 601 */       String str1 = getAttributeValue(paramString, paramInt, eANAttribute
/*     */ 
/*     */           
/* 604 */           .getAttributeCode(), "<em>** Not Populated **</em>");
/*     */ 
/*     */ 
/*     */       
/* 608 */       if (paramBoolean2) {
/*     */         
/* 610 */         str2 = getMetaDescription(paramString, eANAttribute
/*     */             
/* 612 */             .getAttributeCode(), false);
/*     */       }
/*     */       else {
/*     */         
/* 616 */         str2 = getAttributeDescription(paramString, eANAttribute
/*     */             
/* 618 */             .getAttributeCode());
/*     */       } 
/*     */       
/* 621 */       if (str2.length() > str.length() && str2
/* 622 */         .substring(0, str.length()).equalsIgnoreCase(str))
/*     */       {
/* 624 */         str2 = str2.substring(str.length());
/*     */       }
/*     */       
/* 627 */       if (paramBoolean1 || str1 != null) {
/*     */         
/* 629 */         hashtable.put(str2, str1);
/*     */         
/* 631 */         vector.add(str2);
/*     */       } 
/*     */     } 
/* 634 */     int j = entityItem.getAttributeCount();
/* 635 */     String[] arrayOfString = new String[j];
/*     */     
/* 637 */     if (!paramBoolean1) {
/* 638 */       j = vector.size();
/* 639 */       arrayOfString = new String[j];
/* 640 */       for (b = 0; b < j; b++) {
/* 641 */         arrayOfString[b] = vector.elementAt(b);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 646 */     sortUtil.sort(arrayOfString);
/*     */ 
/*     */     
/* 649 */     stringBuffer.append("<table width=\"100%\">");
/* 650 */     int i = arrayOfString.length - arrayOfString.length / 2;
/* 651 */     for (b = 0; b < i; b++) {
/* 652 */       int k = i + b;
/* 653 */       stringBuffer.append("<tr><td class=\"PsgLabel\" valign=\"top\">");
/* 654 */       stringBuffer.append(arrayOfString[b]);
/* 655 */       stringBuffer.append("</td><td class=\"PsgText\" valign=\"top\">");
/* 656 */       stringBuffer.append(hashtable.get(arrayOfString[b]));
/* 657 */       stringBuffer.append("</td>");
/* 658 */       if (k < arrayOfString.length) {
/* 659 */         stringBuffer.append("<td class=\"PsgLabel\" valign=\"top\">");
/* 660 */         stringBuffer.append(arrayOfString[k]);
/* 661 */         stringBuffer.append("</td><td class=\"PsgText\" valign=\"top\">");
/* 662 */         stringBuffer.append(hashtable.get(arrayOfString[k]));
/* 663 */         stringBuffer.append("</td><tr>");
/*     */       } else {
/* 665 */         stringBuffer.append("<td class=\"PsgLabel\">");
/* 666 */         stringBuffer.append("</td><td class=\"PsgText\">");
/* 667 */         stringBuffer.append("</td><tr>");
/*     */       } 
/*     */     } 
/* 670 */     stringBuffer.append("</table><br />");
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
/*     */   private String getMetaDescription(String paramString1, String paramString2, boolean paramBoolean) {
/* 686 */     return getMetaDescription(this.m_elist, paramString1, paramString2, paramBoolean);
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
/*     */   private String getMetaDescription(EntityList paramEntityList, String paramString1, String paramString2, boolean paramBoolean) {
/* 704 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString1);
/* 705 */     EANMetaAttribute eANMetaAttribute = null;
/* 706 */     String str = null;
/*     */     
/* 708 */     if (entityGroup == null) {
/* 709 */       logError("Did not find EntityGroup: " + paramString1 + " in entity list to extract getMetaDescription");
/* 710 */       return null;
/*     */     } 
/*     */     
/* 713 */     if (entityGroup != null) {
/* 714 */       eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/*     */     }
/* 716 */     if (eANMetaAttribute != null) {
/* 717 */       if (paramBoolean) {
/* 718 */         str = eANMetaAttribute.getLongDescription();
/*     */       } else {
/* 720 */         str = eANMetaAttribute.getShortDescription();
/*     */       } 
/*     */     }
/* 723 */     return str;
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
/*     */   private void triggerWorkFlow(String paramString) throws SQLException, MiddlewareException, WorkflowException, MiddlewareShutdownInProgressException {
/* 740 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 741 */     EntityItem[] arrayOfEntityItem = new EntityItem[1];
/* 742 */     WorkflowActionItem workflowActionItem = new WorkflowActionItem(null, this.m_db, this.m_prof, paramString);
/* 743 */     arrayOfEntityItem[0] = entityGroup.getEntityItem(0);
/* 744 */     workflowActionItem.setEntityItems(arrayOfEntityItem);
/* 745 */     this.m_db.executeAction(this.m_prof, workflowActionItem);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\CCTOABR002.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */