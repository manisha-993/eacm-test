/*     */ package COM.ibm.eannounce.abr.psg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
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
/*     */ import java.util.Set;
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
/*     */ public class CTOABR003
/*     */   extends PokBaseABR
/*     */ {
/*     */   private static final String PHASEORDER = "001000500020007000300040";
/*     */   private static final String DRAFT = "0010";
/*     */   private static final String READYFORREVIEW = "0040";
/*     */   private static final String CHANGEREQUEST = "0050";
/*     */   
/*     */   private void triggerWorkFlow(String paramString) throws SQLException, MiddlewareException, WorkflowException, MiddlewareShutdownInProgressException {
/* 122 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 123 */     EntityItem[] arrayOfEntityItem = new EntityItem[1];
/* 124 */     WorkflowActionItem workflowActionItem = new WorkflowActionItem(null, this.m_db, this.m_prof, paramString);
/* 125 */     arrayOfEntityItem[0] = entityGroup.getEntityItem(0);
/* 126 */     workflowActionItem.setEntityItems(arrayOfEntityItem);
/* 127 */     this.m_db.executeAction(this.m_prof, workflowActionItem);
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
/* 143 */   private ResourceBundle messages = ResourceBundle.getBundle(getClass().getName(), getLocale(1));
/* 144 */   private StringBuffer report = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 150 */     StringBuffer stringBuffer1 = this.report;
/*     */     
/* 152 */     MessageFormat messageFormat = null;
/* 153 */     String[] arrayOfString = new String[10];
/* 154 */     StringBuffer stringBuffer2 = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     ResourceBundle resourceBundle = this.messages;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 166 */       start_ABRBuild();
/* 167 */       this
/* 168 */         .messages = ResourceBundle.getBundle(
/* 169 */           getClass().getName(), 
/* 170 */           getLocale(this.m_prof.getReadLanguage().getNLSID()));
/* 171 */       resourceBundle = this.messages;
/*     */       
/* 173 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/* 174 */       Iterator<EANMetaAttribute> iterator2 = entityGroup.getMetaAttribute().values().iterator();
/* 175 */       while (iterator2.hasNext()) {
/* 176 */         EANMetaAttribute eANMetaAttribute = iterator2.next();
/* 177 */         stringBuffer2.append(
/* 178 */             getAttributeValue(
/* 179 */               getRootEntityType(), 
/* 180 */               getRootEntityID(), eANMetaAttribute
/* 181 */               .getAttributeCode()));
/* 182 */         if (iterator2.hasNext()) {
/* 183 */           stringBuffer2.append(" ");
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 188 */       messageFormat = new MessageFormat(resourceBundle.getString("PATH"));
/* 189 */       Iterator<EntityItem> iterator = this.m_elist.getEntityGroup("PR").getEntityItem().values().iterator();
/* 190 */       while (iterator.hasNext()) {
/* 191 */         EntityItem entityItem = iterator.next();
/* 192 */         arrayOfString[0] = getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), "BRANDCODE");
/* 193 */         arrayOfString[1] = getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), "FAMNAMEASSOC");
/* 194 */         arrayOfString[2] = getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), "SENAMEASSOC");
/* 195 */         arrayOfString[3] = getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), "NAME");
/* 196 */         stringBuffer1.append(messageFormat.format(arrayOfString));
/*     */       } 
/*     */ 
/*     */       
/* 200 */       Vector<Integer> vector = getEntityIds("PR", "PRCTO");
/* 201 */       Iterator<Integer> iterator1 = vector.iterator();
/* 202 */       while (iterator1.hasNext()) {
/* 203 */         int i = ((Integer)iterator1.next()).intValue();
/* 204 */         printEntity("PR", i, 0);
/*     */       } 
/*     */       
/* 207 */       printEntity(getRootEntityType(), getRootEntityID(), 0);
/*     */ 
/*     */       
/* 210 */       String str1 = getAttributeFlagEnabledValue("PR", ((Integer)vector.firstElement()).intValue(), "PROJECTPHASE", "");
/* 211 */       String str2 = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "COFPROJECTPHASE", "");
/*     */       
/* 213 */       setReturnCode(0);
/* 214 */       if (str1 == null || str2 == null) {
/* 215 */         stringBuffer1.append(resourceBundle.getString("STATUS_ERROR"));
/* 216 */         setReturnCode(-1);
/*     */       } 
/* 218 */       if ("001000500020007000300040".indexOf(str1) >= "001000500020007000300040".indexOf(str2)) {
/*     */         
/* 220 */         String str = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "COFSTATUS", "");
/* 221 */         HashSet hashSet = new HashSet();
/* 222 */         if ("0010".equals(str) || "0050".equals(str)) {
/*     */           
/* 224 */           EntityGroup entityGroup1 = this.m_elist.getEntityGroup("CG");
/*     */ 
/*     */ 
/*     */           
/* 228 */           if (entityGroup1 == null || entityGroup1.getEntityItemCount() == 0) {
/*     */             
/* 230 */             stringBuffer1.append(resourceBundle.getString("ERROR_NO_CG"));
/* 231 */             setReturnCode(-1);
/*     */ 
/*     */           
/*     */           }
/* 235 */           else if (isCGTYPEDuplicated(hashSet)) {
/* 236 */             stringBuffer1.append(resourceBundle.getString("ERROR_DUP_CG"));
/* 237 */             setReturnCode(-1);
/*     */           } 
/*     */ 
/*     */           
/* 241 */           EntityGroup entityGroup2 = this.m_elist.getEntityGroup("CFGR");
/* 242 */           if (entityGroup2 == null || entityGroup2.getEntityItemCount() == 0) {
/*     */             
/* 244 */             stringBuffer1.append(resourceBundle.getString("ERROR_NO_CFGR"));
/* 245 */             setReturnCode(-1);
/*     */           } 
/*     */           
/* 248 */           EntityGroup entityGroup3 = this.m_elist.getEntityGroup("SBB");
/* 249 */           if (entityGroup3 == null || entityGroup3.getEntityItemCount() == 0) {
/*     */             
/* 251 */             stringBuffer1.append(resourceBundle.getString("ERROR_NO_SBB"));
/* 252 */             setReturnCode(-1);
/*     */           } else {
/*     */             
/* 255 */             checkSBBTYPE(hashSet);
/*     */           } 
/*     */           
/* 258 */           if (getReturnCode() == 0) {
/* 259 */             triggerWorkFlow("WFCOFSTATUSRR");
/*     */           }
/*     */         } 
/* 262 */         if ("0040".equals(str)) {
/*     */           
/* 264 */           EntityGroup entityGroup1 = this.m_elist.getEntityGroup("IMG");
/* 265 */           EntityGroup entityGroup2 = this.m_elist.getEntityGroup("WAR");
/* 266 */           EntityGroup entityGroup3 = this.m_elist.getEntityGroup("CPG");
/* 267 */           EntityGroup entityGroup4 = this.m_elist.getEntityGroup("CTOSBB");
/* 268 */           if (entityGroup1 == null || entityGroup1.getEntityItemCount() == 0) {
/*     */             
/* 270 */             stringBuffer1.append(resourceBundle.getString("ERROR_NO_IMG"));
/* 271 */             setReturnCode(-1);
/*     */           } 
/* 273 */           if (entityGroup2 == null || entityGroup2.getEntityItemCount() != 1) {
/*     */             
/* 275 */             stringBuffer1.append(resourceBundle.getString("ERROR_WAR"));
/* 276 */             setReturnCode(-1);
/*     */           } 
/* 278 */           if (entityGroup3 == null || entityGroup3.getEntityItemCount() != 1) {
/*     */             
/* 280 */             stringBuffer1.append(resourceBundle.getString("ERROR_CPG"));
/* 281 */             setReturnCode(-1);
/*     */           } 
/*     */           
/* 284 */           if (isCGTYPEDuplicated(hashSet)) {
/* 285 */             stringBuffer1.append(resourceBundle.getString("ERROR_DUP_CG"));
/* 286 */             setReturnCode(-1);
/*     */           } 
/* 288 */           checkSBBTYPE(hashSet);
/*     */           
/* 290 */           if (entityGroup4 != null) {
/* 291 */             int i = entityGroup4.getEntityItemCount();
/* 292 */             for (byte b = 0; b < i; b++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 313 */               EntityItem entityItem = entityGroup4.getEntityItem(b);
/* 314 */               String str3 = getAttributeValue(entityItem, "SBBUNPUBLISHWWDATE", null);
/* 315 */               String str4 = getAttributeValue(entityItem, "SBBPUBLISHWWDATE", null);
/* 316 */               if (str4 != null) {
/* 317 */                 EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 318 */                 String str5 = getAttributeValue(entityItem1, "SBBANNOUNCETGT", null);
/*     */                 
/* 320 */                 if (str5 != null && str4.compareTo(str5) < 0) {
/*     */                   
/* 322 */                   stringBuffer1.append(resourceBundle.getString("ERROR_OVERRIDE_PUBLISH_DATE"));
/* 323 */                   setReturnCode(-1);
/*     */                 } 
/*     */               } 
/* 326 */               if (str3 != null) {
/* 327 */                 EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 328 */                 String str5 = getAttributeValue(entityItem1, "SBBWITHDRAWLDATE", null);
/*     */                 
/* 330 */                 if (str5 != null && str3.compareTo(str5) > 0) {
/*     */                   
/* 332 */                   stringBuffer1.append(resourceBundle.getString("ERROR_OVERRIDE_WITHDRAW_DATE"));
/* 333 */                   setReturnCode(-1);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 339 */           if (getReturnCode() == 0) {
/* 340 */             triggerWorkFlow("WFCOFSTATUSFINAL");
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         
/* 345 */         stringBuffer1.append(resourceBundle.getString("FAIL_MSG"));
/* 346 */         setReturnCode(-1);
/*     */       } 
/* 348 */       if (getReturnCode() == 0)
/*     */       {
/*     */         
/* 351 */         stringBuffer1.append(resourceBundle.getString("PASS_MSG"));
/*     */       
/*     */       }
/*     */     }
/* 355 */     catch (LockPDHEntityException lockPDHEntityException) {
/* 356 */       setReturnCode(-1);
/* 357 */       messageFormat = new MessageFormat(resourceBundle.getString("LOCK_ERROR"));
/* 358 */       arrayOfString[0] = "IAB1007E: Could not get soft lock.  Rule execution is terminated.";
/* 359 */       arrayOfString[1] = lockPDHEntityException.getMessage();
/* 360 */       stringBuffer1.append(messageFormat.format(arrayOfString));
/* 361 */       logError(lockPDHEntityException.getMessage());
/* 362 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 363 */       setReturnCode(-1);
/* 364 */       messageFormat = new MessageFormat(resourceBundle.getString("UPDATE_ERROR"));
/* 365 */       arrayOfString[0] = updatePDHEntityException.getMessage();
/* 366 */       stringBuffer1.append(messageFormat.format(arrayOfString));
/* 367 */       logError(updatePDHEntityException.getMessage());
/* 368 */     } catch (Exception exception) {
/* 369 */       StringWriter stringWriter = new StringWriter();
/* 370 */       setReturnCode(-1);
/*     */       
/* 372 */       messageFormat = new MessageFormat(resourceBundle.getString("EXCEPTION_ERROR"));
/* 373 */       arrayOfString[0] = this.m_abri.getABRCode();
/* 374 */       arrayOfString[1] = exception.getMessage();
/* 375 */       stringBuffer1.append(messageFormat.format(arrayOfString));
/* 376 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 377 */       stringBuffer1.append("<pre>");
/* 378 */       stringBuffer1.append(stringWriter.getBuffer().toString());
/* 379 */       stringBuffer1.append("</pre>");
/*     */     } finally {
/* 381 */       setDGTitle(stringBuffer2.toString());
/* 382 */       setDGRptName(getShortClassName(getClass()));
/* 383 */       setDGRptClass("WWABR");
/*     */       
/* 385 */       if (!isReadOnly()) {
/* 386 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */     
/* 390 */     stringBuffer2.append((getReturnCode() == 0) ? " Passed" : " Failed");
/* 391 */     messageFormat = new MessageFormat(resourceBundle.getString("HEADER"));
/* 392 */     arrayOfString[0] = getShortClassName(getClass());
/* 393 */     arrayOfString[1] = stringBuffer2.toString();
/* 394 */     arrayOfString[2] = getNow();
/* 395 */     arrayOfString[3] = this.m_prof.getOPName();
/* 396 */     arrayOfString[4] = this.m_prof.getRoleDescription();
/* 397 */     arrayOfString[5] = getDescription();
/* 398 */     arrayOfString[6] = getABRVersion();
/* 399 */     stringBuffer1.insert(0, messageFormat.format(arrayOfString));
/* 400 */     println(stringBuffer1.toString());
/* 401 */     printDGSubmitString();
/* 402 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkSBBTYPE(Set paramSet) {
/* 410 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SBB");
/*     */     
/* 412 */     String[] arrayOfString = new String[1];
/* 413 */     if (entityGroup == null) {
/* 414 */       logError("Extract Definition Problem: No SBB");
/*     */     }
/* 416 */     else if (paramSet != null) {
/* 417 */       int i = entityGroup.getEntityItemCount();
/* 418 */       for (byte b = 0; b < i; b++) {
/* 419 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 420 */         EANAttribute eANAttribute = entityItem.getAttribute("SBBTYPE");
/* 421 */         if (eANAttribute != null && eANAttribute instanceof EANFlagAttribute) {
/* 422 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 423 */           String str = eANFlagAttribute.getFirstActiveFlagCode();
/* 424 */           if (!paramSet.contains(str)) {
/*     */             
/* 426 */             MessageFormat messageFormat = new MessageFormat(this.messages.getString("ERROR_SBB_TYPE"));
/* 427 */             arrayOfString[0] = getAttributeValue(entityItem, "SBBPNUMB", "** Not Populated **");
/* 428 */             this.report.append(messageFormat.format(arrayOfString));
/* 429 */             setReturnCode(-1);
/*     */           } 
/*     */         } 
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
/*     */   private boolean isCGTYPEDuplicated(Set<String> paramSet) {
/* 443 */     boolean bool = false;
/* 444 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("CG");
/* 445 */     if (entityGroup == null) {
/* 446 */       logError("Extract Definition Problem: No CG");
/*     */     } else {
/*     */       
/* 449 */       int i = entityGroup.getEntityItemCount();
/* 450 */       if (paramSet == null) {
/* 451 */         paramSet = new HashSet();
/*     */       }
/* 453 */       for (byte b = 0; b < i; b++) {
/* 454 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 455 */         EANAttribute eANAttribute = entityItem.getAttribute("CGTYPE");
/* 456 */         if (eANAttribute != null && eANAttribute instanceof EANFlagAttribute) {
/* 457 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 458 */           String str = eANFlagAttribute.getFirstActiveFlagCode();
/* 459 */           if (paramSet.contains(str))
/*     */           {
/* 461 */             bool = true;
/*     */           }
/* 463 */           paramSet.add(str);
/*     */         } 
/*     */       } 
/*     */     } 
/* 467 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 476 */     return this.messages.getString("DESCRIPTION");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 485 */     return "$Revision: 1.30 $";
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
/*     */   private Locale getLocale(int paramInt) {
/* 542 */     Locale locale = null;
/* 543 */     switch (paramInt)
/*     */     { case 1:
/* 545 */         locale = Locale.US;
/*     */       case 2:
/* 547 */         locale = Locale.GERMAN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 567 */         return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/* 581 */     String str = "";
/* 582 */     logMessage("In printEntity _strEntityType" + paramString + ":_iEntityID:" + paramInt1 + ":_iLevel:" + paramInt2);
/*     */     
/* 584 */     switch (paramInt2) {
/*     */       case 0:
/* 586 */         str = "PsgReportSection";
/*     */         break;
/*     */       case 1:
/* 589 */         str = "PsgReportSectionII";
/*     */         break;
/*     */       case 2:
/* 592 */         str = "PsgReportSectionIII";
/*     */         break;
/*     */       case 3:
/* 595 */         str = "PsgReportSectionIV";
/*     */         break;
/*     */       default:
/* 598 */         str = "PsgReportSectionV";
/*     */         break;
/*     */     } 
/* 601 */     logMessage("Printing table width");
/* 602 */     this.report.append("<table width=\"100%\"><tr><td class=\"");
/* 603 */     this.report.append(str);
/* 604 */     this.report.append("\">");
/* 605 */     this.report.append(getEntityDescription(paramString));
/* 606 */     this.report.append(": ");
/* 607 */     this.report.append(getAttributeValue(paramString, paramInt1, "NAME", "<em>** Not Populated **</em>"));
/* 608 */     this.report.append("</td></tr></table>");
/* 609 */     logMessage("Printing Attributes");
/* 610 */     printAttributes(paramString, paramInt1, false, false);
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
/* 625 */     printAttributes(this.m_elist, paramString, paramInt, paramBoolean1, paramBoolean2);
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
/* 649 */     StringBuffer stringBuffer = this.report;
/* 650 */     EntityItem entityItem = null;
/* 651 */     EntityGroup entityGroup = null;
/*     */     
/* 653 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 654 */     Vector<String> vector = new Vector();
/*     */     
/* 656 */     SortUtil sortUtil = new SortUtil();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 661 */     logMessage("in Print Attributes _strEntityType" + paramString + ":_iEntityID:" + paramInt);
/*     */     
/* 663 */     if (paramString.equals(getEntityType())) {
/*     */       
/* 665 */       entityGroup = paramEntityList.getParentEntityGroup();
/*     */     } else {
/* 667 */       entityGroup = paramEntityList.getEntityGroup(paramString);
/*     */     } 
/*     */     
/* 670 */     if (entityGroup == null) {
/* 671 */       stringBuffer.append("<h3>Warning: Cannot locate an EnityGroup for ");
/* 672 */       stringBuffer.append(paramString);
/* 673 */       stringBuffer.append(" so no attributes will be printed.</h3>");
/*     */       
/*     */       return;
/*     */     } 
/* 677 */     entityItem = entityGroup.getEntityItem(paramString, paramInt);
/*     */     
/* 679 */     if (entityItem == null) {
/*     */       
/* 681 */       entityItem = entityGroup.getEntityItem(0);
/* 682 */       stringBuffer.append("<h3>Warning: Attributes for ");
/* 683 */       stringBuffer.append(paramString);
/* 684 */       stringBuffer.append(":");
/* 685 */       stringBuffer.append(paramInt);
/* 686 */       stringBuffer.append(" cannot be printed as it is not available in the Extract.</h3>");
/* 687 */       stringBuffer.append("<h3>Warning: Root Entityis ");
/* 688 */       stringBuffer.append(getEntityType());
/* 689 */       stringBuffer.append(":");
/* 690 */       stringBuffer.append(getEntityID());
/* 691 */       stringBuffer.append(".</h3>");
/*     */       
/*     */       return;
/*     */     } 
/* 695 */     String str = entityGroup.getLongDescription();
/* 696 */     logMessage("Print Attributes Entity desc is " + str);
/* 697 */     logMessage("Attribute count is" + entityItem.getAttributeCount()); byte b;
/* 698 */     for (b = 0; b < entityItem.getAttributeCount(); b++) {
/*     */ 
/*     */ 
/*     */       
/* 702 */       EANAttribute eANAttribute = entityItem.getAttribute(b);
/* 703 */       logMessage("printAttributes " + eANAttribute.dump(false));
/* 704 */       logMessage("printAttributes " + eANAttribute.dump(true));
/*     */ 
/*     */       
/* 707 */       String str1 = getAttributeValue(paramString, paramInt, eANAttribute
/*     */ 
/*     */           
/* 710 */           .getAttributeCode(), "<em>** Not Populated **</em>");
/*     */       
/* 712 */       String str2 = "";
/*     */ 
/*     */       
/* 715 */       if (paramBoolean2) {
/*     */         
/* 717 */         str2 = getMetaDescription(paramString, eANAttribute
/*     */             
/* 719 */             .getAttributeCode(), false);
/*     */       }
/*     */       else {
/*     */         
/* 723 */         str2 = getAttributeDescription(paramString, eANAttribute
/*     */             
/* 725 */             .getAttributeCode());
/*     */       } 
/*     */       
/* 728 */       if (str2.length() > str.length() && str2
/* 729 */         .substring(0, str.length()).equalsIgnoreCase(str))
/*     */       {
/* 731 */         str2 = str2.substring(str.length());
/*     */       }
/*     */       
/* 734 */       if (paramBoolean1 || str1 != null) {
/*     */         
/* 736 */         hashtable.put(str2, str1);
/*     */         
/* 738 */         vector.add(str2);
/*     */       } 
/*     */     } 
/* 741 */     int j = entityItem.getAttributeCount();
/* 742 */     String[] arrayOfString = new String[j];
/*     */     
/* 744 */     if (!paramBoolean1) {
/* 745 */       j = vector.size();
/* 746 */       arrayOfString = new String[j];
/* 747 */       for (b = 0; b < j; b++) {
/* 748 */         arrayOfString[b] = vector.elementAt(b);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 753 */     sortUtil.sort(arrayOfString);
/*     */ 
/*     */     
/* 756 */     stringBuffer.append("<table width=\"100%\">");
/* 757 */     int i = arrayOfString.length - arrayOfString.length / 2;
/* 758 */     for (b = 0; b < i; b++) {
/* 759 */       int k = i + b;
/* 760 */       stringBuffer.append("<tr><td class=\"PsgLabel\" valign=\"top\">");
/* 761 */       stringBuffer.append(arrayOfString[b]);
/* 762 */       stringBuffer.append("</td><td class=\"PsgText\" valign=\"top\">");
/* 763 */       stringBuffer.append(hashtable.get(arrayOfString[b]));
/* 764 */       stringBuffer.append("</td>");
/* 765 */       if (k < arrayOfString.length) {
/* 766 */         stringBuffer.append("<td class=\"PsgLabel\" valign=\"top\">");
/* 767 */         stringBuffer.append(arrayOfString[k]);
/* 768 */         stringBuffer.append("</td><td class=\"PsgText\" valign=\"top\">");
/* 769 */         stringBuffer.append(hashtable.get(arrayOfString[k]));
/* 770 */         stringBuffer.append("</td><tr>");
/*     */       } else {
/* 772 */         stringBuffer.append("<td class=\"PsgLabel\">");
/* 773 */         stringBuffer.append("</td><td class=\"PsgText\">");
/* 774 */         stringBuffer.append("</td><tr>");
/*     */       } 
/*     */     } 
/* 777 */     stringBuffer.append("</table><br />");
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
/* 793 */     return getMetaDescription(this.m_elist, paramString1, paramString2, paramBoolean);
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
/* 811 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString1);
/* 812 */     EANMetaAttribute eANMetaAttribute = null;
/* 813 */     String str = null;
/*     */     
/* 815 */     if (entityGroup == null) {
/* 816 */       logError("Did not find EntityGroup: " + paramString1 + " in entity list to extract getMetaDescription");
/* 817 */       return null;
/*     */     } 
/*     */     
/* 820 */     if (entityGroup != null) {
/* 821 */       eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/*     */     }
/* 823 */     if (eANMetaAttribute != null) {
/* 824 */       if (paramBoolean) {
/* 825 */         str = eANMetaAttribute.getLongDescription();
/*     */       } else {
/* 827 */         str = eANMetaAttribute.getShortDescription();
/*     */       } 
/*     */     }
/* 830 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\CTOABR003.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */