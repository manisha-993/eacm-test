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
/*     */ public final class CSOLABR002
/*     */   extends PokBaseABR
/*     */ {
/*  72 */   private MessageFormat mfOut = null;
/*  73 */   private Object[] mfParms = (Object[])new String[10];
/*  74 */   private ResourceBundle msgs = null;
/*  75 */   private StringBuffer rpt = new StringBuffer();
/*     */ 
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
/*  87 */     boolean bool = false;
/*  88 */     String str = null;
/*     */     
/*  90 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     try {
/*  92 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  99 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/*     */       
/* 101 */       Iterator<EANMetaAttribute> iterator = entityGroup.getMetaAttribute().values().iterator();
/* 102 */       while (iterator.hasNext()) {
/* 103 */         EANMetaAttribute eANMetaAttribute = iterator.next();
/* 104 */         stringBuffer.append(
/* 105 */             getAttributeValue(
/* 106 */               getRootEntityType(), 
/* 107 */               getRootEntityID(), eANMetaAttribute
/* 108 */               .getAttributeCode()));
/* 109 */         if (iterator.hasNext()) {
/* 110 */           stringBuffer.append(" ");
/*     */         }
/*     */       } 
/* 113 */       this
/* 114 */         .msgs = ResourceBundle.getBundle(
/* 115 */           getClass().getName(), 
/* 116 */           getLocale(this.m_prof.getReadLanguage().getNLSID()));
/* 117 */       this.mfParms = (Object[])new String[10];
/*     */ 
/*     */       
/* 120 */       this.mfOut = new MessageFormat(this.msgs.getString("PATH"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 126 */       Iterator<EntityItem> iterator1 = this.m_elist.getEntityGroup("PR").getEntityItem().values().iterator();
/* 127 */       while (iterator1.hasNext()) {
/* 128 */         EntityItem entityItem = iterator1.next();
/* 129 */         this.mfParms[0] = 
/* 130 */           getAttributeValue(entityItem
/* 131 */             .getEntityType(), entityItem
/* 132 */             .getEntityID(), "BRANDCODE");
/*     */         
/* 134 */         this.mfParms[1] = 
/* 135 */           getAttributeValue(entityItem
/* 136 */             .getEntityType(), entityItem
/* 137 */             .getEntityID(), "FAMNAMEASSOC");
/*     */         
/* 139 */         this.mfParms[2] = 
/* 140 */           getAttributeValue(entityItem
/* 141 */             .getEntityType(), entityItem
/* 142 */             .getEntityID(), "SENAMEASSOC");
/*     */         
/* 144 */         this.mfParms[3] = 
/* 145 */           getAttributeValue(entityItem
/* 146 */             .getEntityType(), entityItem
/* 147 */             .getEntityID(), "NAME");
/*     */         
/* 149 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 154 */       Vector vector = getParentEntityIds(
/* 155 */           getRootEntityType(), 
/* 156 */           getRootEntityID(), "OF", "OFCSOL");
/*     */ 
/*     */       
/* 159 */       Iterator<Integer> iterator2 = vector.iterator();
/* 160 */       while (iterator2.hasNext()) {
/* 161 */         Integer integer = iterator2.next();
/* 162 */         printEntity("OF", integer.intValue(), 0);
/*     */       } 
/*     */ 
/*     */       
/* 166 */       printEntity(getRootEntityType(), getRootEntityID(), 0);
/*     */       
/* 168 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 177 */       boolean bool1 = (getChildrenEntityIds(getRootEntityType(), getRootEntityID(), "KB", "CSOLKB").size() > 0) ? true : false;
/*     */       
/* 179 */       if (!bool1) {
/*     */ 
/*     */         
/* 182 */         Vector vector1 = getChildrenEntityIds(
/* 183 */             getRootEntityType(), 
/* 184 */             getRootEntityID(), "SBB", "CSOLSBB");
/*     */ 
/*     */         
/* 187 */         Iterator<Integer> iterator3 = vector1.iterator();
/* 188 */         while (iterator3.hasNext()) {
/* 189 */           Integer integer = iterator3.next();
/*     */           
/* 191 */           Vector vector2 = getChildrenEntityIds("SBB", integer
/*     */               
/* 193 */               .intValue(), "KB", "SBBKB");
/*     */ 
/*     */           
/* 196 */           bool1 = (vector2.size() > 0) ? true : false;
/* 197 */           if (bool1) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/* 202 */       bool = false;
/* 203 */       if (!bool1) {
/*     */         
/* 205 */         this.rpt.append(this.msgs.getString("CHECK2_PASS_MSG"));
/*     */       } else {
/*     */         
/* 208 */         Vector vector1 = getParentEntityIds(
/* 209 */             getRootEntityType(), 
/* 210 */             getRootEntityID(), "OF", "OFCSOL");
/*     */ 
/*     */         
/* 213 */         Iterator<Integer> iterator3 = vector1.iterator();
/* 214 */         while (iterator3.hasNext()) {
/* 215 */           Integer integer = iterator3.next();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 222 */           bool = (getChildrenEntityIds("OF", integer.intValue(), "KB", "OFKB").size() > 0) ? true : false;
/*     */           
/* 224 */           if (!bool) {
/*     */ 
/*     */             
/* 227 */             Vector vector2 = getChildrenEntityIds("OF", integer
/*     */                 
/* 229 */                 .intValue(), "SBB", "OFSBB");
/*     */ 
/*     */             
/* 232 */             Iterator<Integer> iterator4 = vector2.iterator();
/* 233 */             while (iterator4.hasNext()) {
/* 234 */               Integer integer1 = iterator4.next();
/*     */               
/* 236 */               Vector vector3 = getChildrenEntityIds("SBB", integer1
/*     */                   
/* 238 */                   .intValue(), "KB", "SBBKB");
/*     */ 
/*     */               
/* 241 */               bool = (vector3.size() > 0) ? true : false;
/* 242 */               if (bool) {
/*     */                 break;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 250 */         if (bool) {
/* 251 */           this.rpt.append(this.msgs.getString("CHECK2_PASS_MSG"));
/*     */         } else {
/* 253 */           this.rpt.append(this.msgs.getString("CHECK2_FAIL_MSG"));
/* 254 */           setReturnCode(-1);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 260 */       str = getAttributeFlagEnabledValue(
/* 261 */           getRootEntityType(), 
/* 262 */           getRootEntityID(), "CSOLSTATUS", "");
/*     */ 
/*     */       
/* 265 */       if (getReturnCode() == 0) {
/*     */ 
/*     */         
/* 268 */         int i = getParentEntityId(
/* 269 */             getRootEntityType(), 
/* 270 */             getRootEntityID(), "OF", "OFCSOL");
/*     */ 
/*     */ 
/*     */         
/* 274 */         String str1 = getAttributeFlagEnabledValue("OF", i, "STATUS", "");
/* 275 */         if (null == str || null == str1) {
/* 276 */           setReturnCode(-1);
/* 277 */           this.rpt.append(this.msgs.getString("STATUS_ERROR"));
/*     */         } 
/* 279 */         if ("0010".equals(str) || "0050"
/* 280 */           .equals(str)) {
/* 281 */           if ("0020".equals(str1) || "0040"
/* 282 */             .equals(str1)) {
/*     */             
/* 284 */             setControlBlock();
/* 285 */             setFlagValue("CSOLSTATUS", "0040");
/*     */           } else {
/* 287 */             this.rpt.append(this.msgs.getString("CHECK1_FAIL_MSG"));
/* 288 */             setReturnCode(-1);
/*     */           } 
/* 290 */         } else if ("0020".equals(str)) {
/* 291 */           setControlBlock();
/* 292 */           setFlagValue("CSOLSTATUS", "0050");
/* 293 */           setReturnCode(0);
/* 294 */           this.rpt.append(this.msgs.getString("STD_PASS_MSG"));
/* 295 */         } else if ("0040".equals(str)) {
/* 296 */           if ("0020".equals(str1)) {
/*     */             
/* 298 */             setControlBlock();
/* 299 */             setFlagValue("CSOLSTATUS", "0020");
/*     */           } else {
/* 301 */             this.rpt.append(this.msgs.getString("CHECK1_FAIL_MSG"));
/* 302 */             setReturnCode(-1);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 306 */         this.rpt.append(this.msgs.getString("SKIP_MSG"));
/*     */       } 
/* 308 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 309 */       setReturnCode(-1);
/* 310 */       this.mfOut = new MessageFormat(this.msgs.getString("LOCK_ERROR"));
/* 311 */       this.mfParms[0] = "IAB1007E: Could not get soft lock.  Rule execution is terminated.";
/* 312 */       this.mfParms[1] = lockPDHEntityException.getMessage();
/* 313 */       this.rpt.append(this.mfOut.format(this.mfParms));
/* 314 */       logError(lockPDHEntityException.getMessage());
/* 315 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 316 */       setReturnCode(-1);
/* 317 */       this.mfOut = new MessageFormat(this.msgs.getString("UPDATE_ERROR"));
/* 318 */       this.mfParms[0] = updatePDHEntityException.getMessage();
/* 319 */       this.rpt.append(this.mfOut.format(this.mfParms));
/* 320 */       logError(updatePDHEntityException.getMessage());
/* 321 */     } catch (Exception exception) {
/* 322 */       setReturnCode(-1);
/*     */       
/* 324 */       this.mfOut = new MessageFormat(this.msgs.getString("EXCEPTION_ERROR"));
/* 325 */       this.mfParms[0] = this.m_abri.getABRCode();
/* 326 */       this.mfParms[1] = exception.getMessage();
/* 327 */       this.rpt.append(this.mfOut.format(this.mfParms));
/* 328 */       StringWriter stringWriter = new StringWriter();
/* 329 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 330 */       this.rpt.append("<pre>");
/* 331 */       this.rpt.append(stringWriter.getBuffer().toString());
/* 332 */       this.rpt.append("</pre>");
/*     */     } finally {
/* 334 */       setDGTitle(stringBuffer.toString());
/* 335 */       setDGRptName(getShortClassName(getClass()));
/* 336 */       setDGRptClass("CTYABR");
/*     */       
/* 338 */       if (!isReadOnly()) {
/* 339 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */     
/* 343 */     stringBuffer.append((getReturnCode() == 0) ? " Passed" : " Failed");
/* 344 */     this.mfOut = new MessageFormat(this.msgs.getString("HEADER"));
/* 345 */     this.mfParms[0] = getShortClassName(getClass());
/* 346 */     this.mfParms[1] = stringBuffer.toString();
/* 347 */     this.mfParms[2] = getNow();
/* 348 */     this.mfParms[3] = this.m_prof.getOPName();
/* 349 */     this.mfParms[4] = this.m_prof.getRoleDescription();
/* 350 */     this.mfParms[5] = getDescription();
/* 351 */     this.mfParms[6] = getABRVersion();
/* 352 */     this.rpt.insert(0, this.mfOut.format(this.mfParms));
/* 353 */     println(this.rpt.toString());
/* 354 */     printDGSubmitString();
/* 355 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 364 */     return this.msgs.getString("DESCRIPTION");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 373 */     return "$Revision: 1.18 $";
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
/*     */   private void setFlagValue(String paramString1, String paramString2) {
/* 385 */     logMessage("****** strAttributeValue set to: " + paramString2);
/*     */     
/* 387 */     if (paramString2 != null)
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 394 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 398 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 405 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 410 */         Vector<SingleFlag> vector = new Vector();
/* 411 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 413 */         if (singleFlag != null) {
/* 414 */           vector.addElement(singleFlag);
/*     */           
/* 416 */           returnEntityKey.m_vctAttributes = vector;
/* 417 */           vector1.addElement(returnEntityKey);
/*     */           
/* 419 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 420 */           this.m_db.commit();
/*     */         } 
/* 422 */       } catch (MiddlewareException middlewareException) {
/* 423 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 424 */       } catch (Exception exception) {
/* 425 */         logMessage("setFlagValue: " + exception.getMessage());
/*     */       }  
/*     */   }
/*     */   
/*     */   private Locale getLocale(int paramInt) {
/* 430 */     Locale locale = null;
/* 431 */     switch (paramInt)
/*     */     { case 1:
/* 433 */         locale = Locale.US;
/*     */       case 2:
/* 435 */         locale = Locale.GERMAN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 455 */         return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/* 469 */     String str = null;
/*     */     
/* 471 */     logMessage("In printEntity _strEntityType" + paramString + ":_iEntityID:" + paramInt1 + ":_iLevel:" + paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 479 */     str = "";
/* 480 */     switch (paramInt2) {
/*     */       
/*     */       case 0:
/* 483 */         str = "PsgReportSection";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 488 */         str = "PsgReportSectionII";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 493 */         str = "PsgReportSectionIII";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 498 */         str = "PsgReportSectionIV";
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 503 */         str = "PsgReportSectionV";
/*     */         break;
/*     */     } 
/*     */     
/* 507 */     logMessage("Printing table width");
/* 508 */     this.rpt.append("<table width=\"100%\"><tr><td class=\"" + str + "\">" + 
/*     */ 
/*     */ 
/*     */         
/* 512 */         getEntityDescription(paramString) + ": " + 
/*     */         
/* 514 */         getAttributeValue(paramString, paramInt1, "NAME", "<em>** Not Populated **</em>") + "</td></tr></table>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 520 */     logMessage("Printing Attributes");
/* 521 */     printAttributes(paramString, paramInt1, false, false);
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
/* 536 */     printAttributes(this.m_elist, paramString, paramInt, paramBoolean1, paramBoolean2);
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
/* 560 */     EntityItem entityItem = null;
/* 561 */     EntityGroup entityGroup = null;
/*     */     
/* 563 */     SortUtil sortUtil = null;
/* 564 */     int i = 0;
/* 565 */     int j = 0;
/*     */     
/* 567 */     String[] arrayOfString = null;
/* 568 */     String str1 = null;
/* 569 */     String str2 = null;
/* 570 */     String str3 = null;
/*     */     
/* 572 */     logMessage("in Print Attributes _strEntityType" + paramString + ":_iEntityID:" + paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 579 */     if (paramString.equals(getEntityType())) {
/*     */       
/* 581 */       entityGroup = paramEntityList.getParentEntityGroup();
/*     */     } else {
/* 583 */       entityGroup = paramEntityList.getEntityGroup(paramString);
/*     */     } 
/*     */     
/* 586 */     if (entityGroup == null) {
/* 587 */       this.rpt.append("<h3>Warning: Cannot locate an EnityGroup for " + paramString + " so no attributes will be printed.</h3>");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 594 */     entityItem = entityGroup.getEntityItem(paramString, paramInt);
/*     */     
/* 596 */     if (entityItem == null) {
/*     */       
/* 598 */       entityItem = entityGroup.getEntityItem(0);
/* 599 */       this.rpt.append("<h3>Warning: Attributes for " + paramString + ":" + paramInt + " cannot be printed as it is not available in the Extract.</h3>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 605 */       this.rpt.append("<h3>Warning: Root Entityis " + 
/*     */           
/* 607 */           getEntityType() + ":" + 
/*     */           
/* 609 */           getEntityID() + ".</h3>");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 614 */     str1 = entityGroup.getLongDescription();
/* 615 */     logMessage("Print Attributes Entity desc is " + str1);
/* 616 */     logMessage("Attribute count is" + entityItem.getAttributeCount());
/* 617 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 618 */     Vector<String> vector = new Vector(); byte b;
/* 619 */     for (b = 0; b < entityItem.getAttributeCount(); b++) {
/*     */       
/* 621 */       EANAttribute eANAttribute = entityItem.getAttribute(b);
/* 622 */       logMessage("printAttributes " + eANAttribute.dump(false));
/* 623 */       logMessage("printAttributes " + eANAttribute.dump(true));
/*     */ 
/*     */       
/* 626 */       str2 = getAttributeValue(paramString, paramInt, eANAttribute
/*     */ 
/*     */           
/* 629 */           .getAttributeCode(), "<em>** Not Populated **</em>");
/*     */       
/* 631 */       str3 = "";
/*     */ 
/*     */       
/* 634 */       if (paramBoolean2) {
/*     */         
/* 636 */         str3 = getMetaDescription(paramString, eANAttribute
/*     */             
/* 638 */             .getAttributeCode(), false);
/*     */       }
/*     */       else {
/*     */         
/* 642 */         str3 = getAttributeDescription(paramString, eANAttribute
/*     */             
/* 644 */             .getAttributeCode());
/*     */       } 
/*     */       
/* 647 */       if (str3.length() > str1.length() && str3
/* 648 */         .substring(0, str1.length()).equalsIgnoreCase(str1))
/*     */       {
/* 650 */         str3 = str3.substring(str1.length());
/*     */       }
/*     */       
/* 653 */       if (paramBoolean1 || str2 != null) {
/*     */         
/* 655 */         hashtable.put(str3, str2);
/*     */         
/* 657 */         vector.add(str3);
/*     */       } 
/*     */     } 
/* 660 */     arrayOfString = new String[entityItem.getAttributeCount()];
/*     */     
/* 662 */     if (!paramBoolean1) {
/* 663 */       arrayOfString = new String[vector.size()];
/* 664 */       for (b = 0; b < arrayOfString.length; b++) {
/* 665 */         arrayOfString[b] = vector.elementAt(b);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 670 */     sortUtil = new SortUtil();
/* 671 */     sortUtil.sort(arrayOfString);
/*     */ 
/*     */     
/* 674 */     this.rpt.append("<table width=\"100%\">");
/* 675 */     j = arrayOfString.length - arrayOfString.length / 2;
/* 676 */     for (b = 0; b < j; b++) {
/* 677 */       this.rpt.append("<tr><td class=\"PsgLabel\" valign=\"top\">" + arrayOfString[b] + "</td><td class=\"PsgText\" valign=\"top\">" + hashtable
/*     */ 
/*     */ 
/*     */           
/* 681 */           .get(arrayOfString[b]) + "</td>");
/*     */       
/* 683 */       i = j + b;
/* 684 */       if (i < arrayOfString.length) {
/* 685 */         this.rpt.append("<td class=\"PsgLabel\" valign=\"top\">" + arrayOfString[i] + "</td><td class=\"PsgText\" valign=\"top\">" + hashtable
/*     */ 
/*     */ 
/*     */             
/* 689 */             .get(arrayOfString[i]) + "</td><tr>");
/*     */       } else {
/*     */         
/* 692 */         this.rpt.append("<td class=\"PsgLabel\"></td><td class=\"PsgText\"></td><tr>");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 698 */     this.rpt.append("</table>\n<br />");
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
/* 714 */     return getMetaDescription(this.m_elist, paramString1, paramString2, paramBoolean);
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
/* 731 */     EANMetaAttribute eANMetaAttribute = null;
/* 732 */     String str = null;
/* 733 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString1);
/* 734 */     if (entityGroup == null) {
/* 735 */       logError("Did not find EntityGroup: " + paramString1 + " in entity list to extract getMetaDescription");
/*     */ 
/*     */ 
/*     */       
/* 739 */       return null;
/*     */     } 
/*     */     
/* 742 */     eANMetaAttribute = null;
/* 743 */     if (entityGroup != null) {
/* 744 */       eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/*     */     }
/* 746 */     str = null;
/* 747 */     if (eANMetaAttribute != null) {
/* 748 */       if (paramBoolean) {
/* 749 */         str = eANMetaAttribute.getLongDescription();
/*     */       } else {
/* 751 */         str = eANMetaAttribute.getShortDescription();
/*     */       } 
/*     */     }
/* 754 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\CSOLABR002.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */