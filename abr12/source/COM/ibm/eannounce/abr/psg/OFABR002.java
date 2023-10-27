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
/*     */ import java.util.TreeSet;
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
/*     */ public final class OFABR002
/*     */   extends PokBaseABR
/*     */ {
/*  93 */   private MessageFormat mfOut = null;
/*  94 */   private Object[] mfParms = (Object[])new String[10];
/*  95 */   private ResourceBundle msgs = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String phaseOrder = "001000500020007000300040";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   private StringBuffer rpt = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 121 */     StringBuffer stringBuffer = new StringBuffer();
/* 122 */     EntityGroup entityGroup = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     int i = 0;
/* 129 */     int j = 0;
/* 130 */     String str1 = null;
/* 131 */     String str2 = null;
/*     */ 
/*     */     
/* 134 */     String str3 = null;
/*     */     
/*     */     try {
/* 137 */       start_ABRBuild();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/*     */       
/* 146 */       Iterator<EANMetaAttribute> iterator = entityGroup.getMetaAttribute().values().iterator();
/* 147 */       while (iterator.hasNext()) {
/* 148 */         EANMetaAttribute eANMetaAttribute = iterator.next();
/* 149 */         stringBuffer.append(
/* 150 */             getAttributeValue(
/* 151 */               getRootEntityType(), 
/* 152 */               getRootEntityID(), eANMetaAttribute
/* 153 */               .getAttributeCode()));
/* 154 */         if (iterator.hasNext()) {
/* 155 */           stringBuffer.append(" ");
/*     */         }
/*     */       } 
/* 158 */       this
/* 159 */         .msgs = ResourceBundle.getBundle(
/* 160 */           getClass().getName(), 
/* 161 */           getLocale(this.m_prof.getReadLanguage().getNLSID()));
/* 162 */       this.mfParms = (Object[])new String[10];
/*     */ 
/*     */       
/* 165 */       this.mfOut = new MessageFormat(this.msgs.getString("PATH"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 171 */       Iterator<EntityItem> iterator1 = this.m_elist.getEntityGroup("PR").getEntityItem().values().iterator();
/* 172 */       while (iterator1.hasNext()) {
/* 173 */         EntityItem entityItem = iterator1.next();
/* 174 */         this.mfParms[0] = 
/* 175 */           getAttributeValue(entityItem
/* 176 */             .getEntityType(), entityItem
/* 177 */             .getEntityID(), "BRANDCODE");
/*     */         
/* 179 */         this.mfParms[1] = 
/* 180 */           getAttributeValue(entityItem
/* 181 */             .getEntityType(), entityItem
/* 182 */             .getEntityID(), "FAMNAMEASSOC");
/*     */         
/* 184 */         this.mfParms[2] = 
/* 185 */           getAttributeValue(entityItem
/* 186 */             .getEntityType(), entityItem
/* 187 */             .getEntityID(), "SENAMEASSOC");
/*     */         
/* 189 */         this.mfParms[3] = 
/* 190 */           getAttributeValue(entityItem
/* 191 */             .getEntityType(), entityItem
/* 192 */             .getEntityID(), "NAME");
/*     */         
/* 194 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*     */       } 
/*     */       
/* 197 */       i = getRootEntityID();
/* 198 */       str1 = getRootEntityType();
/*     */ 
/*     */       
/* 201 */       printEntity(str1, i, 1);
/*     */ 
/*     */       
/* 204 */       this.mfOut = new MessageFormat(this.msgs.getString("PARENT_STATUS"));
/* 205 */       Vector<Integer> vector = getParentEntityIds(str1, i, "PR", "PROF");
/* 206 */       Iterator<Integer> iterator2 = vector.iterator();
/* 207 */       while (iterator2.hasNext()) {
/* 208 */         int k = ((Integer)iterator2.next()).intValue();
/* 209 */         this.mfParms[0] = getAttributeDescription("PR", "NAME");
/* 210 */         this.mfParms[1] = getAttributeValue("PR", k, "NAME");
/* 211 */         this.mfParms[2] = getAttributeDescription("PR", "PROJECTPHASE");
/* 212 */         this.mfParms[3] = getAttributeValue("PR", k, "PROJECTPHASE");
/* 213 */         this.rpt.append(this.mfOut.format(this.mfParms));
/*     */       } 
/*     */       
/* 216 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 221 */       str2 = getAttributeFlagEnabledValue(str1, i, "OFFERINGTYPE", "");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 230 */       str3 = getAttributeFlagEnabledValue(
/* 231 */           getRootEntityType(), 
/* 232 */           getRootEntityID(), "OFPROJECTPHASE", "");
/*     */ 
/*     */ 
/*     */       
/* 236 */       if ("0080".equals(str2) && "0050"
/* 237 */         .equals(str3)) {
/*     */         
/* 239 */         TreeSet<String> treeSet1 = new TreeSet();
/* 240 */         TreeSet<String> treeSet2 = new TreeSet();
/* 241 */         TreeSet<String> treeSet3 = new TreeSet();
/* 242 */         Vector vector1 = getChildrenEntityIds(str1, i, "SBB", "OFSBB");
/*     */         
/* 244 */         int k = 0;
/* 245 */         int m = 0;
/* 246 */         int n = 0;
/* 247 */         int i1 = 0;
/* 248 */         int i2 = 0;
/* 249 */         int i3 = 0;
/* 250 */         Iterator<Integer> iterator4 = vector1.iterator();
/* 251 */         while (iterator4.hasNext()) {
/* 252 */           Integer integer = iterator4.next();
/* 253 */           k += 
/* 254 */             getChildrenEntityIds("SBB", integer
/*     */               
/* 256 */               .intValue(), "GRA", "SBBGRA")
/*     */ 
/*     */             
/* 259 */             .size();
/* 260 */           m += 
/* 261 */             getChildrenEntityIds("SBB", integer
/*     */               
/* 263 */               .intValue(), "HD", "SBBHD")
/*     */ 
/*     */             
/* 266 */             .size();
/* 267 */           n += 
/* 268 */             getChildrenEntityIds("SBB", integer
/*     */               
/* 270 */               .intValue(), "MB", "SBBMB")
/*     */ 
/*     */             
/* 273 */             .size();
/* 274 */           i1 += 
/* 275 */             getChildrenEntityIds("SBB", integer
/*     */               
/* 277 */               .intValue(), "MEM", "SBBMEM")
/*     */ 
/*     */             
/* 280 */             .size();
/* 281 */           i2 += 
/* 282 */             getChildrenEntityIds("SBB", integer
/*     */               
/* 284 */               .intValue(), "PP", "SBBPP")
/*     */ 
/*     */             
/* 287 */             .size();
/* 288 */           i3 += 
/* 289 */             getChildrenEntityIds("SBB", integer
/*     */               
/* 291 */               .intValue(), "WAR", "SBBWAR")
/*     */ 
/*     */             
/* 294 */             .size();
/*     */         } 
/*     */ 
/*     */         
/* 298 */         j = getChildrenEntityIds(str1, i, "DD", "OFDD").size();
/* 299 */         k += 
/* 300 */           getChildrenEntityIds(str1, i, "GRA", "OFGRA").size();
/* 301 */         m += 
/* 302 */           getChildrenEntityIds(str1, i, "HD", "OFHD").size();
/* 303 */         n += 
/* 304 */           getChildrenEntityIds(str1, i, "MB", "OFMB").size();
/* 305 */         i1 += 
/* 306 */           getChildrenEntityIds(str1, i, "MEM", "OFMEM").size();
/* 307 */         i2 += 
/* 308 */           getChildrenEntityIds(str1, i, "PP", "OFPP").size();
/* 309 */         i3 += 
/* 310 */           getChildrenEntityIds(str1, i, "WAR", "OFWAR").size();
/*     */         
/* 312 */         if (j > 1) {
/* 313 */           treeSet3.add("DD");
/*     */         }
/* 315 */         if (k == 0) {
/* 316 */           treeSet2.add("GRA");
/*     */         }
/* 318 */         if (m == 0) {
/* 319 */           treeSet2.add("HD");
/*     */         }
/* 321 */         if (n == 0) {
/* 322 */           treeSet1.add("MB");
/*     */         }
/* 324 */         if (n > 1) {
/* 325 */           treeSet3.add("MB");
/*     */         }
/* 327 */         if (i1 == 0) {
/* 328 */           treeSet1.add("MEM");
/*     */         }
/* 330 */         if (i2 == 0) {
/* 331 */           treeSet1.add("PP");
/*     */         }
/* 333 */         if (i2 > 1) {
/* 334 */           treeSet3.add("PP");
/*     */         }
/* 336 */         if (i3 == 0) {
/* 337 */           treeSet1.add("WAR");
/*     */         }
/* 339 */         if (i3 > 1) {
/* 340 */           treeSet3.add("WAR");
/*     */         }
/* 342 */         if (treeSet3.isEmpty() && treeSet1
/* 343 */           .isEmpty() && treeSet2
/* 344 */           .isEmpty()) {
/* 345 */           this.rpt.append(this.msgs.getString("CHECK3_PASS_MSG"));
/*     */         } else {
/* 347 */           setReturnCode(-1);
/*     */         } 
/* 349 */         Iterator<String> iterator3 = treeSet1.iterator();
/* 350 */         this.mfOut = new MessageFormat(this.msgs.getString("CHECK3_MISSING_MSG"));
/* 351 */         while (iterator3.hasNext()) {
/* 352 */           String str = iterator3.next();
/* 353 */           this.mfParms[0] = getEntityDescription(str);
/* 354 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*     */         } 
/* 356 */         iterator3 = treeSet3.iterator();
/* 357 */         this
/* 358 */           .mfOut = new MessageFormat(this.msgs.getString("CHECK3_TOO_MANY_MSG"));
/* 359 */         while (iterator3.hasNext()) {
/* 360 */           String str = iterator3.next();
/* 361 */           this.mfParms[0] = getEntityDescription(str);
/* 362 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*     */         } 
/* 364 */         iterator3 = treeSet2.iterator();
/* 365 */         this
/* 366 */           .mfOut = new MessageFormat(this.msgs.getString("CHECK3_MISSING_MSG2"));
/* 367 */         while (iterator3.hasNext()) {
/* 368 */           String str = iterator3.next();
/* 369 */           this.mfParms[0] = getEntityDescription(str);
/* 370 */           this.rpt.append(this.mfOut.format(this.mfParms));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 376 */       if (getReturnCode() == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 381 */         String str4 = getAttributeFlagEnabledValue(
/* 382 */             getRootEntityType(), 
/* 383 */             getRootEntityID(), "BAVLFORSPECIALBID", "");
/*     */ 
/*     */         
/* 386 */         String str5 = null;
/* 387 */         if (vector.size() > 0)
/*     */         {
/* 389 */           str5 = getAttributeFlagEnabledValue("PR", ((Integer)vector
/*     */               
/* 391 */               .firstElement()).intValue(), "PROJECTPHASE", "");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 396 */         if (str5 == null || str3 == null) {
/* 397 */           setReturnCode(-1);
/* 398 */           this.rpt.append(this.msgs.getString("STATUS_ERROR"));
/* 399 */         } else if ("001000500020007000300040"
/* 400 */           .indexOf(str5) > "001000500020007000300040"
/* 401 */           .indexOf(str3)) {
/*     */           
/* 403 */           String str = null;
/* 404 */           if ("0020".equals(str4)) {
/*     */             
/* 406 */             str = str5;
/*     */           } else {
/*     */             
/* 409 */             int k = "001000500020007000300040".indexOf(str3) + 4;
/* 410 */             str = "001000500020007000300040".substring(k, k + 4);
/*     */           } 
/* 412 */           setControlBlock();
/* 413 */           setFlagValue("OFPROJECTPHASE", str);
/* 414 */           this.rpt.append(this.msgs.getString("CHECK1_PASS_MSG"));
/*     */         } else {
/* 416 */           this.rpt.append(this.msgs.getString("CHECK1_FAIL_MSG"));
/* 417 */           setReturnCode(-1);
/*     */         } 
/*     */       } 
/* 420 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 421 */       setReturnCode(-1);
/* 422 */       this.mfOut = new MessageFormat(this.msgs.getString("LOCK_ERROR"));
/* 423 */       this.mfParms[0] = "IAB1007E: Could not get soft lock.  Rule execution is terminated.";
/* 424 */       this.mfParms[1] = lockPDHEntityException.getMessage();
/* 425 */       this.rpt.append(this.mfOut.format(this.mfParms));
/* 426 */       logError(lockPDHEntityException.getMessage());
/* 427 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 428 */       setReturnCode(-1);
/* 429 */       this.mfOut = new MessageFormat(this.msgs.getString("UPDATE_ERROR"));
/* 430 */       this.mfParms[0] = updatePDHEntityException.getMessage();
/* 431 */       this.rpt.append(this.mfOut.format(this.mfParms));
/* 432 */       logError(updatePDHEntityException.getMessage());
/* 433 */     } catch (Exception exception) {
/* 434 */       setReturnCode(-1);
/*     */       
/* 436 */       this.mfOut = new MessageFormat(this.msgs.getString("EXCEPTION_ERROR"));
/* 437 */       this.mfParms[0] = this.m_abri.getABRCode();
/* 438 */       this.mfParms[1] = exception.getMessage();
/* 439 */       this.rpt.append(this.mfOut.format(this.mfParms));
/* 440 */       StringWriter stringWriter = new StringWriter();
/* 441 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 442 */       this.rpt.append("<pre>");
/* 443 */       this.rpt.append(stringWriter.getBuffer().toString());
/* 444 */       this.rpt.append("</pre>");
/*     */     } finally {
/*     */       
/* 447 */       setDGTitle(stringBuffer.toString());
/* 448 */       setDGRptName(getShortClassName(getClass()));
/* 449 */       setDGRptClass("WWABR");
/* 450 */       if (!isReadOnly()) {
/* 451 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */     
/* 455 */     stringBuffer.append((getReturnCode() == 0) ? " Passed" : " Failed");
/* 456 */     this.mfOut = new MessageFormat(this.msgs.getString("HEADER"));
/* 457 */     this.mfParms[0] = getShortClassName(getClass());
/* 458 */     this.mfParms[1] = stringBuffer.toString();
/* 459 */     this.mfParms[2] = getNow();
/* 460 */     this.mfParms[3] = this.m_prof.getOPName();
/* 461 */     this.mfParms[4] = this.m_prof.getRoleDescription();
/* 462 */     this.mfParms[5] = getDescription();
/* 463 */     this.mfParms[6] = getABRVersion();
/* 464 */     this.rpt.insert(0, this.mfOut.format(this.mfParms));
/* 465 */     println(this.rpt.toString());
/* 466 */     printDGSubmitString();
/* 467 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 476 */     return this.msgs.getString("DESCRIPTION");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 485 */     return "$Revision: 1.5 $";
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
/*     */   private void setFlagValue(String paramString1, String paramString2) {
/* 512 */     logMessage("****** strAttributeValue set to: " + paramString2);
/*     */     
/* 514 */     if (paramString2 != null)
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 521 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*     */ 
/*     */ 
/*     */         
/* 525 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 532 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 537 */         Vector<SingleFlag> vector = new Vector();
/* 538 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*     */         
/* 540 */         if (singleFlag != null) {
/* 541 */           vector.addElement(singleFlag);
/*     */           
/* 543 */           returnEntityKey.m_vctAttributes = vector;
/* 544 */           vector1.addElement(returnEntityKey);
/*     */           
/* 546 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 547 */           this.m_db.commit();
/*     */         } 
/* 549 */       } catch (MiddlewareException middlewareException) {
/* 550 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/* 551 */       } catch (Exception exception) {
/* 552 */         logMessage("setFlagValue: " + exception.getMessage());
/*     */       }  
/*     */   }
/*     */   
/*     */   private Locale getLocale(int paramInt) {
/* 557 */     Locale locale = null;
/* 558 */     switch (paramInt)
/*     */     { case 1:
/* 560 */         locale = Locale.US;
/*     */       case 2:
/* 562 */         locale = Locale.GERMAN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 582 */         return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/*     */   public void printEntity(String paramString, int paramInt1, int paramInt2) {
/* 595 */     String str = null;
/* 596 */     logMessage("In printEntity _strEntityType" + paramString + ":_iEntityID:" + paramInt1 + ":_iLevel:" + paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 604 */     str = "";
/* 605 */     switch (paramInt2) {
/*     */       
/*     */       case 0:
/* 608 */         str = "PsgReportSection";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 613 */         str = "PsgReportSectionII";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 618 */         str = "PsgReportSectionIII";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 623 */         str = "PsgReportSectionIV";
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 628 */         str = "PsgReportSectionV";
/*     */         break;
/*     */     } 
/*     */     
/* 632 */     logMessage("Printing table width");
/* 633 */     this.rpt.append("<table width=\"100%\"><tr><td class=\"" + str + "\">" + 
/*     */ 
/*     */ 
/*     */         
/* 637 */         getEntityDescription(paramString) + ": " + 
/*     */         
/* 639 */         getAttributeValue(paramString, paramInt1, "NAME", "<em>** Not Populated **</em>") + "</td></tr></table>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 645 */     logMessage("Printing Attributes");
/* 646 */     printAttributes(paramString, paramInt1, false, false);
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
/* 661 */     printAttributes(this.m_elist, paramString, paramInt, paramBoolean1, paramBoolean2);
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
/* 685 */     EntityItem entityItem = null;
/* 686 */     EntityGroup entityGroup = null;
/* 687 */     String str1 = null;
/*     */ 
/*     */     
/* 690 */     String str2 = null;
/* 691 */     String str3 = null;
/* 692 */     String[] arrayOfString = null;
/*     */     
/* 694 */     int i = 0;
/* 695 */     int j = 0;
/* 696 */     logMessage("in Print Attributes _strEntityType" + paramString + ":_iEntityID:" + paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 702 */     if (paramString.equals(getEntityType())) {
/*     */       
/* 704 */       entityGroup = paramEntityList.getParentEntityGroup();
/*     */     } else {
/* 706 */       entityGroup = paramEntityList.getEntityGroup(paramString);
/*     */     } 
/*     */     
/* 709 */     if (entityGroup == null) {
/* 710 */       this.rpt.append("<h3>Warning: Cannot locate an EnityGroup for " + paramString + " so no attributes will be printed.</h3>");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 717 */     entityItem = entityGroup.getEntityItem(paramString, paramInt);
/*     */     
/* 719 */     if (entityItem == null) {
/*     */       
/* 721 */       entityItem = entityGroup.getEntityItem(0);
/* 722 */       this.rpt.append("<h3>Warning: Attributes for " + paramString + ":" + paramInt + " cannot be printed as it is not available in the Extract.</h3>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 728 */       this.rpt.append("<h3>Warning: Root Entityis " + 
/*     */           
/* 730 */           getEntityType() + ":" + 
/*     */           
/* 732 */           getEntityID() + ".</h3>");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 737 */     str1 = entityGroup.getLongDescription();
/* 738 */     logMessage("Print Attributes Entity desc is " + str1);
/* 739 */     logMessage("Attribute count is" + entityItem.getAttributeCount());
/* 740 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 741 */     Vector<String> vector = new Vector(); byte b;
/* 742 */     for (b = 0; b < entityItem.getAttributeCount(); b++) {
/*     */       
/* 744 */       EANAttribute eANAttribute = entityItem.getAttribute(b);
/* 745 */       logMessage("printAttributes " + eANAttribute.dump(false));
/* 746 */       logMessage("printAttributes " + eANAttribute.dump(true));
/*     */ 
/*     */       
/* 749 */       str2 = getAttributeValue(paramString, paramInt, eANAttribute
/*     */ 
/*     */           
/* 752 */           .getAttributeCode(), "<em>** Not Populated **</em>");
/*     */       
/* 754 */       str3 = "";
/*     */ 
/*     */       
/* 757 */       if (paramBoolean2) {
/*     */         
/* 759 */         str3 = getMetaDescription(paramString, eANAttribute
/*     */             
/* 761 */             .getAttributeCode(), false);
/*     */       }
/*     */       else {
/*     */         
/* 765 */         str3 = getAttributeDescription(paramString, eANAttribute
/*     */             
/* 767 */             .getAttributeCode());
/*     */       } 
/*     */       
/* 770 */       if (str3.length() > str1.length() && str3
/* 771 */         .substring(0, str1.length()).equalsIgnoreCase(str1))
/*     */       {
/* 773 */         str3 = str3.substring(str1.length());
/*     */       }
/*     */       
/* 776 */       if (paramBoolean1 || str2 != null) {
/*     */         
/* 778 */         hashtable.put(str3, str2);
/*     */         
/* 780 */         vector.add(str3);
/*     */       } 
/*     */     } 
/* 783 */     arrayOfString = new String[entityItem.getAttributeCount()];
/*     */     
/* 785 */     if (!paramBoolean1) {
/* 786 */       arrayOfString = new String[vector.size()];
/* 787 */       for (b = 0; b < arrayOfString.length; b++) {
/* 788 */         arrayOfString[b] = vector.elementAt(b);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 793 */     SortUtil sortUtil = new SortUtil();
/* 794 */     sortUtil.sort(arrayOfString);
/*     */ 
/*     */     
/* 797 */     this.rpt.append("<table width=\"100%\">");
/* 798 */     i = arrayOfString.length - arrayOfString.length / 2;
/* 799 */     for (b = 0; b < i; b++) {
/* 800 */       this.rpt.append("<tr><td class=\"PsgLabel\" valign=\"top\">" + arrayOfString[b] + "</td><td class=\"PsgText\" valign=\"top\">" + hashtable
/*     */ 
/*     */ 
/*     */           
/* 804 */           .get(arrayOfString[b]) + "</td>");
/*     */       
/* 806 */       j = i + b;
/* 807 */       if (j < arrayOfString.length) {
/* 808 */         this.rpt.append("<td class=\"PsgLabel\" valign=\"top\">" + arrayOfString[j] + "</td><td class=\"PsgText\" valign=\"top\">" + hashtable
/*     */ 
/*     */ 
/*     */             
/* 812 */             .get(arrayOfString[j]) + "</td><tr>");
/*     */       } else {
/*     */         
/* 815 */         this.rpt.append("<td class=\"PsgLabel\"></td><td class=\"PsgText\"></td><tr>");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 821 */     this.rpt.append("</table>\n<br />");
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
/* 837 */     return getMetaDescription(this.m_elist, paramString1, paramString2, paramBoolean);
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
/* 854 */     EANMetaAttribute eANMetaAttribute = null;
/* 855 */     String str = null;
/* 856 */     EntityGroup entityGroup = paramEntityList.getEntityGroup(paramString1);
/* 857 */     if (entityGroup == null) {
/* 858 */       logError("Did not find EntityGroup: " + paramString1 + " in entity list to extract getMetaDescription");
/*     */ 
/*     */ 
/*     */       
/* 862 */       return null;
/*     */     } 
/*     */     
/* 865 */     eANMetaAttribute = null;
/* 866 */     if (entityGroup != null) {
/* 867 */       eANMetaAttribute = entityGroup.getMetaAttribute(paramString2);
/*     */     }
/* 869 */     str = null;
/* 870 */     if (eANMetaAttribute != null) {
/* 871 */       if (paramBoolean) {
/* 872 */         str = eANMetaAttribute.getLongDescription();
/*     */       } else {
/* 874 */         str = eANMetaAttribute.getShortDescription();
/*     */       } 
/*     */     }
/* 877 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\OFABR002.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */