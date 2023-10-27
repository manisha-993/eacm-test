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
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.middleware.SortUtil;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.text.MessageFormat;
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
/*     */ public final class CVARABR002
/*     */   extends PokBaseABR
/*     */ {
/*  74 */   private MessageFormat mfOut = null;
/*  75 */   private Object[] mfParms = (Object[])new String[10];
/*  76 */   private ResourceBundle msgs = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private StringBuffer rpt = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  96 */     EntityGroup entityGroup = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     String str1 = null;
/* 102 */     String str2 = null;
/*     */     
/* 104 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     try {
/* 106 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/*     */       
/* 115 */       Iterator<EANMetaAttribute> iterator = entityGroup.getMetaAttribute().values().iterator();
/* 116 */       while (iterator.hasNext()) {
/* 117 */         EANMetaAttribute eANMetaAttribute = iterator.next();
/* 118 */         stringBuffer.append(
/* 119 */             getAttributeValue(
/* 120 */               getRootEntityType(), 
/* 121 */               getRootEntityID(), eANMetaAttribute
/* 122 */               .getAttributeCode()));
/* 123 */         if (iterator.hasNext()) {
/* 124 */           stringBuffer.append(" ");
/*     */         }
/*     */       } 
/* 127 */       this
/* 128 */         .msgs = ResourceBundle.getBundle(
/* 129 */           getClass().getName(), 
/* 130 */           getLocale(this.m_prof.getReadLanguage().getNLSID()));
/* 131 */       this.mfParms = (Object[])new String[10];
/*     */ 
/*     */       
/* 134 */       this.mfOut = new MessageFormat(this.msgs.getString("PATH"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 140 */       Iterator<EntityItem> iterator2 = this.m_elist.getEntityGroup("PR").getEntityItem().values().iterator();
/* 141 */       while (iterator2.hasNext()) {
/* 142 */         EntityItem entityItem = iterator2.next();
/* 143 */         this.mfParms[0] = 
/* 144 */           getAttributeValue(entityItem
/* 145 */             .getEntityType(), entityItem
/* 146 */             .getEntityID(), "BRANDCODE");
/*     */         
/* 148 */         this.mfParms[1] = 
/* 149 */           getAttributeValue(entityItem
/* 150 */             .getEntityType(), entityItem
/* 151 */             .getEntityID(), "FAMNAMEASSOC");
/*     */         
/* 153 */         this.mfParms[2] = 
/* 154 */           getAttributeValue(entityItem
/* 155 */             .getEntityType(), entityItem
/* 156 */             .getEntityID(), "SENAMEASSOC");
/*     */         
/* 158 */         this.mfParms[3] = 
/* 159 */           getAttributeValue(entityItem
/* 160 */             .getEntityType(), entityItem
/* 161 */             .getEntityID(), "NAME");
/*     */         
/* 163 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*     */       } 
/*     */ 
/*     */       
/* 167 */       printEntity(getRootEntityType(), getRootEntityID(), 0);
/*     */ 
/*     */       
/* 170 */       this.mfOut = new MessageFormat(this.msgs.getString("PARENT_STATUS"));
/* 171 */       Vector<Integer> vector = getEntityIds("VAR", "VARCVAR");
/* 172 */       Iterator<Integer> iterator1 = vector.iterator();
/* 173 */       while (iterator1.hasNext()) {
/* 174 */         int i = ((Integer)iterator1.next()).intValue();
/* 175 */         this.mfParms[0] = getEntityDescription("VAR");
/* 176 */         this.mfParms[1] = getAttributeValue("VAR", i, "NAME");
/* 177 */         this.mfParms[2] = getAttributeDescription("VAR", "VARSTATUS");
/* 178 */         this.mfParms[3] = getAttributeValue("VAR", i, "VARSTATUS");
/* 179 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 184 */       str1 = getAttributeFlagEnabledValue("VAR", ((Integer)vector
/*     */           
/* 186 */           .firstElement()).intValue(), "VARSTATUS", "");
/*     */ 
/*     */ 
/*     */       
/* 190 */       str2 = getAttributeFlagEnabledValue(
/* 191 */           getRootEntityType(), 
/* 192 */           getRootEntityID(), "STATUS_CVAR", "");
/*     */ 
/*     */       
/* 195 */       if (str1 == null || str2 == null) {
/* 196 */         setReturnCode(-1);
/* 197 */         this.rpt.append(this.msgs.getString("STATUS_ERROR"));
/*     */       } 
/* 199 */       setReturnCode(0);
/* 200 */       if ("0040".equals(str2)) {
/* 201 */         if ("0020".equals(str1)) {
/* 202 */           setControlBlock();
/* 203 */           setFlagValue("STATUS_CVAR", "0020");
/* 204 */           this.rpt.append(this.msgs.getString("PASS_MSG"));
/* 205 */           setReturnCode(0);
/*     */         } else {
/* 207 */           setReturnCode(-1);
/* 208 */           this.rpt.append(this.msgs.getString("FAIL_MSG"));
/*     */         } 
/*     */       } else {
/* 211 */         this.rpt.append(this.msgs.getString("STD_PASS_MSG"));
/*     */       } 
/* 213 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 214 */       setReturnCode(-1);
/* 215 */       this.mfOut = new MessageFormat(this.msgs.getString("LOCK_ERROR"));
/* 216 */       this.mfParms[0] = "IAB1007E: Could not get soft lock.  Rule execution is terminated.";
/* 217 */       this.mfParms[1] = lockPDHEntityException.getMessage();
/* 218 */       this.rpt.append(this.mfOut.format(this.mfParms));
/* 219 */       logError(lockPDHEntityException.getMessage());
/* 220 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 221 */       setReturnCode(-1);
/* 222 */       this.mfOut = new MessageFormat(this.msgs.getString("UPDATE_ERROR"));
/* 223 */       this.mfParms[0] = updatePDHEntityException.getMessage();
/* 224 */       this.rpt.append(this.mfOut.format(this.mfParms));
/* 225 */       logError(updatePDHEntityException.getMessage());
/* 226 */     } catch (Exception exception) {
/* 227 */       setReturnCode(-1);
/*     */       
/* 229 */       this.mfOut = new MessageFormat(this.msgs.getString("EXCEPTION_ERROR"));
/* 230 */       this.mfParms[0] = this.m_abri.getABRCode();
/* 231 */       this.mfParms[1] = exception.getMessage();
/* 232 */       this.rpt.append(this.mfOut.format(this.mfParms));
/* 233 */       StringWriter stringWriter = new StringWriter();
/* 234 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 235 */       this.rpt.append("<pre>");
/* 236 */       this.rpt.append(stringWriter.getBuffer().toString());
/* 237 */       this.rpt.append("</pre>");
/*     */     } finally {
/* 239 */       setDGTitle(stringBuffer.toString());
/* 240 */       setDGRptName(getShortClassName(getClass()));
/* 241 */       setDGRptClass("CTYABR");
/*     */       
/* 243 */       if (!isReadOnly()) {
/* 244 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */     
/* 248 */     stringBuffer.append((getReturnCode() == 0) ? " Passed" : " Failed");
/* 249 */     this.mfOut = new MessageFormat(this.msgs.getString("HEADER"));
/* 250 */     this.mfParms[0] = getShortClassName(getClass());
/* 251 */     this.mfParms[1] = stringBuffer.toString();
/* 252 */     this.mfParms[2] = getNow();
/* 253 */     this.mfParms[3] = this.m_prof.getOPName();
/* 254 */     this.mfParms[4] = this.m_prof.getRoleDescription();
/* 255 */     this.mfParms[5] = getDescription();
/* 256 */     this.mfParms[6] = getABRVersion();
/* 257 */     this.rpt.insert(0, this.mfOut.format(this.mfParms));
/* 258 */     println(this.rpt.toString());
/* 259 */     printDGSubmitString();
/* 260 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 269 */     return this.msgs.getString("DESCRIPTION");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 278 */     return "$Revision: 1.22 $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFlagValue(String paramString1, String paramString2) {
/* 289 */     logMessage("****** strAttributeValue set to: " + paramString2);
/*     */     
/* 291 */     if (paramString2 != null)
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 298 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 302 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 309 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 314 */         Vector<SingleFlag> vector = new Vector();
/* 315 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 317 */         if (singleFlag != null) {
/* 318 */           vector.addElement(singleFlag);
/*     */           
/* 320 */           returnEntityKey.m_vctAttributes = vector;
/* 321 */           vector1.addElement(returnEntityKey);
/*     */           
/* 323 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 324 */           this.m_db.commit();
/*     */         } 
/* 326 */       } catch (MiddlewareException middlewareException) {
/* 327 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 328 */       } catch (Exception exception) {
/* 329 */         logMessage("setFlagValue: " + exception.getMessage());
/*     */       }  
/*     */   }
/*     */   
/*     */   private Locale getLocale(int paramInt) {
/* 334 */     Locale locale = null;
/* 335 */     switch (paramInt)
/*     */     { case 1:
/* 337 */         locale = Locale.US;
/*     */       case 2:
/* 339 */         locale = Locale.GERMAN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 359 */         return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/* 373 */     String str = null;
/*     */     
/* 375 */     logMessage("In printEntity _strEntityType" + paramString + ":_iEntityID:" + paramInt1 + ":_iLevel:" + paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     str = "";
/* 384 */     switch (paramInt2) {
/*     */       
/*     */       case 0:
/* 387 */         str = "PsgReportSection";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 392 */         str = "PsgReportSectionII";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 397 */         str = "PsgReportSectionIII";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 402 */         str = "PsgReportSectionIV";
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 407 */         str = "PsgReportSectionV";
/*     */         break;
/*     */     } 
/*     */     
/* 411 */     logMessage("Printing table width");
/* 412 */     this.rpt.append("<table width=\"100%\"><tr><td class=\"" + str + "\">" + 
/*     */ 
/*     */ 
/*     */         
/* 416 */         getEntityDescription(paramString) + ": " + 
/*     */         
/* 418 */         getAttributeValue(paramString, paramInt1, "NAME", "<em>** Not Populated **</em>") + "</td></tr></table>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 424 */     logMessage("Printing Attributes");
/* 425 */     printAttributes(paramString, paramInt1, false, false);
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
/* 440 */     printAttributes(this.m_elist, paramString, paramInt, paramBoolean1, paramBoolean2);
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
/* 464 */     EntityItem entityItem = null;
/* 465 */     EntityGroup entityGroup = null;
/*     */ 
/*     */     
/* 468 */     String[] arrayOfString = null;
/* 469 */     String str1 = null;
/* 470 */     String str2 = null;
/* 471 */     String str3 = null;
/* 472 */     int i = 0;
/* 473 */     int j = 0;
/* 474 */     SortUtil sortUtil = null;
/* 475 */     logMessage("in Print Attributes _strEntityType" + paramString + ":_iEntityID:" + paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 481 */     if (paramString.equals(getEntityType())) {
/*     */       
/* 483 */       entityGroup = paramEntityList.getParentEntityGroup();
/*     */     } else {
/* 485 */       entityGroup = paramEntityList.getEntityGroup(paramString);
/*     */     } 
/*     */     
/* 488 */     if (entityGroup == null) {
/* 489 */       this.rpt.append("<h3>Warning: Cannot locate an EnityGroup for " + paramString + " so no attributes will be printed.</h3>");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 496 */     entityItem = entityGroup.getEntityItem(paramString, paramInt);
/*     */     
/* 498 */     if (entityItem == null) {
/*     */       
/* 500 */       entityItem = entityGroup.getEntityItem(0);
/* 501 */       this.rpt.append("<h3>Warning: Attributes for " + paramString + ":" + paramInt + " cannot be printed as it is not available in the Extract.</h3>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 507 */       this.rpt.append("<h3>Warning: Root Entityis " + 
/*     */           
/* 509 */           getEntityType() + ":" + 
/*     */           
/* 511 */           getEntityID() + ".</h3>");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 516 */     str1 = entityGroup.getLongDescription();
/* 517 */     logMessage("Print Attributes Entity desc is " + str1);
/* 518 */     logMessage("Attribute count is" + entityItem.getAttributeCount());
/* 519 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 520 */     Vector<String> vector = new Vector(); byte b;
/* 521 */     for (b = 0; b < entityItem.getAttributeCount(); b++) {
/*     */       
/* 523 */       EANAttribute eANAttribute = entityItem.getAttribute(b);
/* 524 */       logMessage("printAttributes " + eANAttribute.dump(false));
/* 525 */       logMessage("printAttributes " + eANAttribute.dump(true));
/*     */ 
/*     */       
/* 528 */       str2 = getAttributeValue(paramString, paramInt, eANAttribute
/*     */ 
/*     */           
/* 531 */           .getAttributeCode(), "<em>** Not Populated **</em>");
/*     */       
/* 533 */       str3 = "";
/*     */ 
/*     */       
/* 536 */       if (paramBoolean2) {
/*     */         
/* 538 */         str3 = getMetaDescription(paramString, eANAttribute
/*     */             
/* 540 */             .getAttributeCode(), false);
/*     */       }
/*     */       else {
/*     */         
/* 544 */         str3 = getAttributeDescription(paramString, eANAttribute
/*     */             
/* 546 */             .getAttributeCode());
/*     */       } 
/*     */       
/* 549 */       if (str3.length() > str1.length() && str3
/* 550 */         .substring(0, str1.length()).equalsIgnoreCase(str1))
/*     */       {
/* 552 */         str3 = str3.substring(str1.length());
/*     */       }
/*     */       
/* 555 */       if (paramBoolean1 || str2 != null) {
/*     */         
/* 557 */         hashtable.put(str3, str2);
/*     */         
/* 559 */         vector.add(str3);
/*     */       } 
/*     */     } 
/* 562 */     arrayOfString = new String[entityItem.getAttributeCount()];
/*     */     
/* 564 */     if (!paramBoolean1) {
/* 565 */       arrayOfString = new String[vector.size()];
/* 566 */       for (b = 0; b < arrayOfString.length; b++) {
/* 567 */         arrayOfString[b] = vector.elementAt(b);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 572 */     sortUtil = new SortUtil();
/* 573 */     sortUtil.sort(arrayOfString);
/*     */ 
/*     */     
/* 576 */     this.rpt.append("<table width=\"100%\">");
/* 577 */     i = arrayOfString.length - arrayOfString.length / 2;
/* 578 */     for (b = 0; b < i; b++) {
/* 579 */       this.rpt.append("<tr><td class=\"PsgLabel\" valign=\"top\">" + arrayOfString[b] + "</td><td class=\"PsgText\" valign=\"top\">" + hashtable
/*     */ 
/*     */ 
/*     */           
/* 583 */           .get(arrayOfString[b]) + "</td>");
/*     */       
/* 585 */       j = i + b;
/* 586 */       if (j < arrayOfString.length) {
/* 587 */         this.rpt.append("<td class=\"PsgLabel\" valign=\"top\">" + arrayOfString[j] + "</td><td class=\"PsgText\" valign=\"top\">" + hashtable
/*     */ 
/*     */ 
/*     */             
/* 591 */             .get(arrayOfString[j]) + "</td><tr>");
/*     */       } else {
/*     */         
/* 594 */         this.rpt.append("<td class=\"PsgLabel\"></td><td class=\"PsgText\"></td><tr>");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 600 */     this.rpt.append("</table>\n<br />");
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
/* 616 */     return getMetaDescription(this.m_elist, paramString1, paramString2, paramBoolean);
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
/*     */   private String getMetaDescription(EntityList paramEntityList, String paramString1, String paramString2, boolean paramBoolean) {
/* 633 */     EANMetaAttribute eANMetaAttribute = null;
/* 634 */     String str = null;
/* 635 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString1);
/* 636 */     if (entityGroup == null) {
/* 637 */       logError("Did not find EntityGroup: " + paramString1 + " in entity list to extract getMetaDescription");
/*     */ 
/*     */ 
/*     */       
/* 641 */       return null;
/*     */     } 
/*     */     
/* 644 */     eANMetaAttribute = null;
/* 645 */     if (entityGroup != null) {
/* 646 */       eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/*     */     }
/* 648 */     str = null;
/* 649 */     if (eANMetaAttribute != null) {
/* 650 */       if (paramBoolean) {
/* 651 */         str = eANMetaAttribute.getLongDescription();
/*     */       } else {
/* 653 */         str = eANMetaAttribute.getShortDescription();
/*     */       } 
/*     */     }
/* 656 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\CVARABR002.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */