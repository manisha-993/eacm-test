/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.D;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.T;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WWSEOABRWWMI
/*     */   extends PokBaseABR
/*     */ {
/*  70 */   private static final String ABR = new String("WWSEOABRWWMI");
/*  71 */   private static final String EG_MODEL = new String("MODEL");
/*  72 */   private static final String EG_FMLY = new String("FMLY");
/*  73 */   private static final String EG_SER = new String("SER");
/*  74 */   private static final String EG_DERIVEDDATA = new String("DERIVEDDATA");
/*  75 */   private static final String EG_PROC = new String("PROC");
/*  76 */   private static final String EG_PLANAR = new String("PLANAR");
/*  77 */   private static final String EG_HDD = new String("HDD");
/*  78 */   private static final String EG_HDC = new String("HDC");
/*  79 */   private static final String EG_OPTCALDVC = new String("OPTCALDVC");
/*  80 */   private static final String EG_MECHPKG = new String("MECHPKG");
/*  81 */   private static final String SPACE = new String(" ");
/*  82 */   private static final String STAR = new String("*");
/*  83 */   private static final String ATT_SERNAM = new String("SERNAM");
/*  84 */   private static final String ATT_WWMASTERINDX = new String("WWMASTERINDX");
/*  85 */   private static final String ATT_NOOFPROCSTD = new String("NOOFPROCSTD");
/*  86 */   private static final String ATT_CLOCKRATEUNIT = new String("CLOCKRATEUNIT");
/*  87 */   private static final String ATT_TOTL2CACHESTD = new String("TOTL2CACHESTD");
/*  88 */   private static final String ATT_MEMRYRAMSTD = new String("MEMRYRAMSTD");
/*  89 */   private static final String ATT_NOOFINSTHARDDRVS = new String("NOOFINSTHARDDRVS");
/*  90 */   private static final String ATT_HDDCAP = new String("HDDCAP");
/*  91 */   private static final String ATT_CAPUNIT = new String("CAPUNIT");
/*     */ 
/*     */   
/*  94 */   private static final String CLOCKRATEUNIT_GHZ = new String("200");
/*  95 */   private static final String MFRNAM_AMD = new String("0020");
/*  96 */   private static final String OPTCALDRIVETYPE_CDROM = new String("0010");
/*  97 */   private EntityGroup m_egParent = null;
/*  98 */   private EntityItem m_ei = null;
/*  99 */   private PDGUtility m_utility = new PDGUtility();
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
/* 110 */     String str1 = null;
/* 111 */     EntityGroup entityGroup1 = null;
/* 112 */     EntityGroup entityGroup2 = null;
/* 113 */     String str2 = System.getProperty("line.separator");
/* 114 */     byte b1 = -1;
/* 115 */     byte b2 = -1;
/*     */     try {
/* 117 */       start_ABRBuild();
/*     */       
/* 119 */       buildReportHeaderII();
/*     */       
/* 121 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 122 */       this.m_ei = this.m_egParent.getEntityItem(0);
/* 123 */       println("<br><b>WWSEO: " + this.m_ei.getKey() + "</b>");
/*     */       
/* 125 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/* 126 */       setReturnCode(0);
/*     */ 
/*     */       
/* 129 */       entityGroup1 = this.m_elist.getEntityGroup(EG_MODEL);
/* 130 */       entityGroup2 = this.m_elist.getEntityGroup(EG_FMLY);
/*     */       
/* 132 */       if (entityGroup1 == null || entityGroup1.getEntityItemCount() <= 0) {
/* 133 */         setReturnCode(-1);
/* 134 */         println("<br /><font color=red>Failed. There are no MODELs linked to WWSEO.</font>");
/*     */       } else {
/* 136 */         for (byte b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 137 */           EntityItem entityItem = entityGroup1.getEntityItem(b);
/* 138 */           String str3 = this.m_utility.getAttrValue(entityItem, "COFCAT");
/* 139 */           String str4 = this.m_utility.getAttrValue(entityItem, "COFSUBCAT");
/* 140 */           String str5 = this.m_utility.getAttrValue(entityItem, "COFGRP");
/* 141 */           D.ebug(3, getABRVersion() + " eiMODEL: " + entityItem.getKey() + ", " + str3 + ", " + str4 + ", " + str5);
/*     */           
/* 143 */           if (str3.equals("100") && str4.equals("126") && str5.equals("150")) {
/* 144 */             b1 = 1;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 150 */       if (entityGroup2 == null || entityGroup2.getEntityItemCount() <= 0) {
/* 151 */         setReturnCode(-1);
/* 152 */         println("<br /><font color=red>Failed. There are no FAMILYs linked to WWSEO.</font>");
/*     */       } else {
/* 154 */         for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 155 */           EntityItem entityItem = entityGroup2.getEntityItem(b);
/* 156 */           String str = this.m_utility.getAttrValue(entityItem, "FAMILY");
/* 157 */           D.ebug(3, getABRVersion() + " eiFMLY: " + entityItem.getKey() + ", " + str);
/*     */           
/* 159 */           if (str.equals("F00080")) {
/*     */             
/* 161 */             b2 = 1; break;
/*     */           } 
/* 163 */           if (str.equals("F00030") || str.equals("F01305") || str.equals("F00050") || str.equals("F01300") || str
/* 164 */             .equals("F01306") || str.equals("F01302") || str.equals("F01301")) {
/*     */ 
/*     */             
/* 167 */             b2 = 2;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 174 */       if (getReturnCode() == 0) {
/* 175 */         System.out.println(getABRVersion() + " iModelCase: " + b1 + ", iFamilyCase: " + b2);
/* 176 */         if (b1 == 1 && b2 == 1) {
/* 177 */           String str3 = getValue(this.m_elist, ATT_WWMASTERINDX, 1);
/* 178 */           String str4 = getValue(this.m_elist, "PRCFILENAM", 1);
/* 179 */           System.out.println(getABRVersion() + " strWWMASTERINDX: " + str3 + ", strPRCFILENAM: " + str4);
/* 180 */           OPICMList oPICMList = new OPICMList();
/* 181 */           oPICMList.put(ATT_WWMASTERINDX, "WWMASTERINDX=" + str3);
/* 182 */           oPICMList.put("PRCFILENAM", "PRCFILENAM=" + str4);
/* 183 */           this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/* 184 */         } else if (b1 == 1 && b2 == 2) {
/* 185 */           String str3 = getValue(this.m_elist, ATT_WWMASTERINDX, 2);
/* 186 */           String str4 = getValue(this.m_elist, "PRCFILENAM", 2);
/* 187 */           System.out.println(getABRVersion() + " strWWMASTERINDX: " + str3 + ", strPRCFILENAM: " + str4);
/* 188 */           OPICMList oPICMList = new OPICMList();
/* 189 */           oPICMList.put(ATT_WWMASTERINDX, "WWMASTERINDX=" + str3);
/* 190 */           oPICMList.put("PRCFILENAM", "PRCFILENAM=" + str4);
/* 191 */           this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */         } else {
/* 193 */           System.out.println(getABRVersion() + " doesn't satisfy the condition to have the attributes populated: " + this.m_ei.getKey());
/*     */         }
/*     */       
/*     */       } 
/* 197 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 198 */       setReturnCode(-2);
/* 199 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 203 */           .getMessage() + "</font></h3>");
/*     */       
/* 205 */       logError(lockPDHEntityException.getMessage());
/* 206 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 207 */       setReturnCode(-2);
/* 208 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 210 */           .getMessage() + "</font></h3>");
/*     */       
/* 212 */       logError(updatePDHEntityException.getMessage());
/* 213 */     } catch (SBRException sBRException) {
/* 214 */       String str = sBRException.toString();
/* 215 */       int i = str.indexOf("(ok)");
/* 216 */       if (i < 0) {
/* 217 */         setReturnCode(-2);
/* 218 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 220 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 222 */         logError(sBRException.toString());
/*     */       } else {
/* 224 */         str = str.substring(0, i);
/* 225 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 227 */     } catch (Exception exception) {
/*     */       
/* 229 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 230 */       println("" + exception);
/* 231 */       exception.printStackTrace();
/*     */       
/* 233 */       if (getABRReturnCode() != -2) {
/* 234 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 237 */       println("<br /><b>" + 
/*     */           
/* 239 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 242 */               getABRDescription(), 
/* 243 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 246 */       log(
/* 247 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 250 */               getABRDescription(), 
/* 251 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 254 */       str1 = this.m_ei.toString();
/* 255 */       if (str1.length() > 64) {
/* 256 */         str1 = str1.substring(0, 64);
/*     */       }
/* 258 */       setDGTitle(str1);
/* 259 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 262 */       setDGString(getABRReturnCode());
/* 263 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 267 */       buildReportFooter();
/*     */       
/* 269 */       if (!isReadOnly()) {
/* 270 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getString(String paramString, int paramInt) {
/* 276 */     return (paramString.length() > paramInt) ? paramString.substring(0, paramInt) : paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getValueDesc(EntityItem paramEntityItem, String paramString) {
/* 281 */     if (paramEntityItem == null) {
/* 282 */       return "";
/*     */     }
/*     */     
/* 285 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 286 */     if (eANAttribute != null)
/*     */     {
/* 288 */       return eANAttribute.toString();
/*     */     }
/* 290 */     return "";
/*     */   }
/*     */   
/*     */   private String getValue(EntityList paramEntityList, String paramString, int paramInt) throws MiddlewareRequestException {
/* 294 */     String str = "WWSEOABRWWMI getValue method ";
/* 295 */     StringBuffer stringBuffer = new StringBuffer();
/* 296 */     EntityGroup entityGroup1 = paramEntityList.getEntityGroup(EG_SER);
/* 297 */     T.est((entityGroup1 != null), "EntityGroup SER is null.");
/* 298 */     EntityItem entityItem1 = null;
/* 299 */     if (entityGroup1.getEntityItemCount() > 0) {
/* 300 */       entityItem1 = entityGroup1.getEntityItem(0);
/* 301 */       D.ebug(3, str + " eiSER: " + entityItem1.dump(false));
/*     */     } 
/* 303 */     if (entityItem1 == null) {
/* 304 */       System.out.println(str + " eiSER is null ");
/*     */     }
/* 306 */     EntityGroup entityGroup2 = paramEntityList.getEntityGroup(EG_DERIVEDDATA);
/* 307 */     T.est((entityGroup2 != null), "EntityGroup DERIVEDDATA is null.");
/* 308 */     EntityItem entityItem2 = null;
/* 309 */     if (entityGroup2.getEntityItemCount() > 0) {
/* 310 */       entityItem2 = entityGroup2.getEntityItem(0);
/* 311 */       D.ebug(3, str + " eiDERIVEDDATA: " + entityItem2.dump(false));
/*     */     } 
/*     */     
/* 314 */     if (entityItem2 == null) {
/* 315 */       System.out.println(str + " eiDERIVEDDATA is null ");
/*     */     }
/*     */     
/* 318 */     EntityGroup entityGroup3 = paramEntityList.getEntityGroup(EG_PROC);
/* 319 */     T.est((entityGroup3 != null), "EntityGroup PROC is null.");
/* 320 */     EntityItem entityItem3 = null;
/* 321 */     if (entityGroup3.getEntityItemCount() > 0) {
/* 322 */       entityItem3 = entityGroup3.getEntityItem(0);
/* 323 */       D.ebug(3, str + " eiPROC: " + entityItem3.dump(false));
/*     */     } 
/*     */     
/* 326 */     if (entityItem3 == null) {
/* 327 */       System.out.println(str + " eiPROC is null ");
/*     */     }
/*     */     
/* 330 */     EntityGroup entityGroup4 = paramEntityList.getEntityGroup(EG_PLANAR);
/* 331 */     T.est((entityGroup4 != null), "EntityGroup PLANAR is null.");
/* 332 */     EntityItem entityItem4 = null;
/* 333 */     if (entityGroup4.getEntityItemCount() > 0) {
/* 334 */       entityItem4 = entityGroup4.getEntityItem(0);
/* 335 */       D.ebug(3, str + " eiPLANAR: " + entityItem4.dump(false));
/*     */     } 
/*     */     
/* 338 */     if (entityItem4 == null) {
/* 339 */       System.out.println(str + " eiPLANAR is null ");
/*     */     }
/*     */     
/* 342 */     EntityGroup entityGroup5 = paramEntityList.getEntityGroup(EG_HDD);
/* 343 */     T.est((entityGroup5 != null), "EntityGroup HDD is null.");
/* 344 */     EntityItem entityItem5 = null;
/* 345 */     if (entityGroup5.getEntityItemCount() > 0) {
/* 346 */       entityItem5 = entityGroup5.getEntityItem(0);
/* 347 */       D.ebug(3, str + " eiHDD: " + entityItem5.dump(false));
/*     */     } 
/* 349 */     if (entityItem5 == null) {
/* 350 */       System.out.println(str + " eiHDD is null ");
/*     */     }
/*     */     
/* 353 */     EntityGroup entityGroup6 = paramEntityList.getEntityGroup(EG_HDC);
/* 354 */     T.est((entityGroup6 != null), "EntityGroup HDC is null.");
/* 355 */     EntityItem entityItem6 = null;
/* 356 */     if (entityGroup6.getEntityItemCount() > 0) {
/* 357 */       entityItem6 = entityGroup6.getEntityItem(0);
/* 358 */       D.ebug(3, str + " eiHDC: " + entityItem6.dump(false));
/*     */     } 
/* 360 */     if (entityItem6 == null) {
/* 361 */       System.out.println(str + " eiHDC is null ");
/*     */     }
/*     */     
/* 364 */     EntityGroup entityGroup7 = paramEntityList.getEntityGroup(EG_OPTCALDVC);
/* 365 */     T.est((entityGroup7 != null), "EntityGroup OPTCALDVC is null.");
/* 366 */     EntityItem entityItem7 = null;
/* 367 */     if (entityGroup7.getEntityItemCount() > 0) {
/* 368 */       entityItem7 = entityGroup7.getEntityItem(0);
/* 369 */       D.ebug(3, str + " eiOPTCALDVC: " + entityItem7.dump(false));
/*     */     } 
/* 371 */     if (entityItem7 == null) {
/* 372 */       System.out.println(str + " eiOPTCALDVC is null ");
/*     */     }
/*     */     
/* 375 */     EntityGroup entityGroup8 = paramEntityList.getEntityGroup(EG_MECHPKG);
/* 376 */     T.est((entityGroup8 != null), "EntityGroup MECHPKG is null.");
/* 377 */     EntityItem entityItem8 = null;
/* 378 */     if (entityGroup8.getEntityItemCount() > 0) {
/* 379 */       entityItem8 = entityGroup8.getEntityItem(0);
/* 380 */       D.ebug(3, str + " eiMECHPKG: " + entityItem8.dump(false));
/*     */     } 
/* 382 */     if (entityItem8 == null) {
/* 383 */       System.out.println(str + " eiMECHPKG is null ");
/*     */     }
/*     */     
/* 386 */     if (paramInt == 1) {
/* 387 */       D.ebug(3, getABRVersion() + " in case 1");
/* 388 */       if (paramString.equals(ATT_WWMASTERINDX)) {
/* 389 */         D.ebug(3, str + " for WWMASTERINDX.");
/*     */ 
/*     */         
/* 392 */         if (entityItem1 != null) {
/* 393 */           stringBuffer.append(getValueDesc(entityItem1, "SERNAM"));
/* 394 */           stringBuffer.append(SPACE + STAR + SPACE);
/*     */         } 
/* 396 */         D.ebug(3, str + " after 1 and 2: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 399 */         if (entityItem2 != null) {
/* 400 */           if (isDigit(this.m_utility.getAttrValue(entityItem2, ATT_NOOFPROCSTD))) {
/* 401 */             int i = Integer.parseInt(this.m_utility.getAttrValue(entityItem2, ATT_NOOFPROCSTD));
/* 402 */             if (i > 1) {
/* 403 */               stringBuffer.append(i + "x");
/*     */             }
/*     */           } 
/*     */         } else {
/* 407 */           System.out.println(getABRVersion() + " eiDERIVEDDATA is null ");
/*     */         } 
/* 409 */         D.ebug(3, str + " after 3: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 414 */         String str1 = this.m_utility.getAttrValue(entityItem3, "MFRNAM");
/* 415 */         if (str1.equals(MFRNAM_AMD)) {
/* 416 */           stringBuffer.append(this.m_utility.getAttrValueDesc(entityItem3, "MFRNAM"));
/*     */         } else {
/* 418 */           String str4 = this.m_utility.getAttrValue(entityItem3, ATT_CLOCKRATEUNIT);
/* 419 */           stringBuffer.append(getString(getValueDesc(entityItem3, "CLOCKRATE"), 4));
/* 420 */           stringBuffer.append(str4.equals(CLOCKRATEUNIT_GHZ) ? this.m_utility.getAttrValueDesc(entityItem3, ATT_CLOCKRATEUNIT) : "");
/*     */         } 
/* 422 */         stringBuffer.append(SPACE);
/* 423 */         D.ebug(3, str + " after 4 and 5: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 427 */         stringBuffer.append(getString(getValueDesc(entityItem2, ATT_TOTL2CACHESTD), 4));
/* 428 */         stringBuffer.append(getValueDesc(entityItem2, "TOTL2CACHESTDUNIT"));
/* 429 */         stringBuffer.append(SPACE);
/* 430 */         D.ebug(3, str + " after 6 and 7: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 434 */         String str2 = this.m_utility.getAttrValue(entityItem4, "L3CACHE");
/* 435 */         stringBuffer.append((str2.length() > 0) ? ("L2" + SPACE + getString(getValueDesc(entityItem4, "L3CACHE"), 4) + getValueDesc(entityItem4, "L3CACHEUNIT")) : "");
/* 436 */         stringBuffer.append(SPACE);
/* 437 */         D.ebug(3, str + " after 8 and 9: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 441 */         stringBuffer.append(getString(getValueDesc(entityItem2, ATT_MEMRYRAMSTD), 4));
/* 442 */         stringBuffer.append(getValueDesc(entityItem2, "MEMRYRAMSTDUNIT"));
/* 443 */         stringBuffer.append(SPACE);
/* 444 */         D.ebug(3, str + " after 10 and 11: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 448 */         stringBuffer.append(getValueDesc(entityItem5, ATT_HDDCAP));
/* 449 */         stringBuffer.append(getValueDesc(entityItem5, ATT_CAPUNIT));
/* 450 */         stringBuffer.append(SPACE);
/* 451 */         D.ebug(3, str + " after 12 and 13: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 455 */         stringBuffer.append(getString(getValueDesc(entityItem6, "BUS"), 4));
/* 456 */         stringBuffer.append(SPACE);
/* 457 */         D.ebug(3, str + " after 14 and 15: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 461 */         String str3 = this.m_utility.getAttrValue(entityItem7, "OPTCALDRIVETYPE");
/* 462 */         if (str3.equals(OPTCALDRIVETYPE_CDROM)) {
/* 463 */           stringBuffer.append(getString(getValueDesc(entityItem7, "CDROMSPED"), 3));
/*     */         } else {
/* 465 */           stringBuffer.append(getValueDesc(entityItem7, "CDROMSPED"));
/*     */         } 
/* 467 */         stringBuffer.append(SPACE);
/* 468 */         D.ebug(3, str + " after 16 and 17: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 472 */         stringBuffer.append(getValueDesc(entityItem4, "TOTCARDSLOT") + "x");
/* 473 */         stringBuffer.append(SPACE);
/* 474 */         D.ebug(3, str + " after 18 and 19: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 477 */         stringBuffer.append(getValueDesc(entityItem8, "TOTBAY"));
/* 478 */         D.ebug(3, str + " after 20: " + stringBuffer.toString());
/* 479 */       } else if (paramString.equals("PRCFILENAM")) {
/* 480 */         D.ebug(3, str + " for PRCFILENAM.");
/*     */ 
/*     */         
/* 483 */         stringBuffer.append(getValueDesc(entityItem1, ATT_SERNAM));
/* 484 */         stringBuffer.append(SPACE + STAR + SPACE);
/* 485 */         D.ebug(3, str + " after 1 and 2: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 488 */         if (isDigit(this.m_utility.getAttrValue(entityItem2, ATT_NOOFPROCSTD))) {
/* 489 */           int i = Integer.parseInt(this.m_utility.getAttrValue(entityItem2, ATT_NOOFPROCSTD));
/* 490 */           if (i > 1) {
/* 491 */             stringBuffer.append(i + "x");
/*     */           }
/*     */         } 
/* 494 */         D.ebug(3, str + " after 3: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 498 */         String str1 = this.m_utility.getAttrValue(entityItem3, ATT_CLOCKRATEUNIT);
/* 499 */         stringBuffer.append(getString(getValueDesc(entityItem3, "CLOCKRATE"), 4));
/* 500 */         stringBuffer.append(str1.equals(CLOCKRATEUNIT_GHZ) ? this.m_utility.getAttrValueDesc(entityItem3, ATT_CLOCKRATEUNIT) : "");
/* 501 */         stringBuffer.append(SPACE);
/* 502 */         D.ebug(3, str + " after 4 and 5: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 506 */         stringBuffer.append(getString(getValueDesc(entityItem2, ATT_TOTL2CACHESTD), 4));
/* 507 */         stringBuffer.append(SPACE);
/* 508 */         D.ebug(3, str + " after 6 and 7: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 511 */         stringBuffer.append(getString(getValueDesc(entityItem2, ATT_MEMRYRAMSTD), 4));
/* 512 */         D.ebug(3, str + " after 8: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 515 */         stringBuffer.append("/" + getValueDesc(entityItem5, ATT_HDDCAP));
/* 516 */         D.ebug(3, str + " after 9: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 519 */         String str2 = this.m_utility.getAttrValue(entityItem7, "OPTCALDRIVETYPE");
/* 520 */         if (str2.equals(OPTCALDRIVETYPE_CDROM)) {
/* 521 */           stringBuffer.append(getString(getValueDesc(entityItem7, "CDROMSPED"), 3));
/*     */         } else {
/* 523 */           stringBuffer.append(getValueDesc(entityItem7, "CDROMSPED"));
/*     */         } 
/* 525 */         D.ebug(3, str + " after 10: " + stringBuffer.toString());
/*     */       } 
/* 527 */     } else if (paramInt == 2) {
/* 528 */       D.ebug(3, str + " in case 2");
/* 529 */       if (paramString.equals(ATT_WWMASTERINDX)) {
/* 530 */         D.ebug(3, str + " for WWMASTERINDX.");
/*     */ 
/*     */         
/* 533 */         String str1 = getValueDesc(entityItem1, ATT_SERNAM);
/* 534 */         if (str1.indexOf("IBM eServer BladeCenter") >= 0) {
/* 535 */           str1 = replace(str1, "IBM eServer BladeCenter", "BC");
/* 536 */         } else if (str1.indexOf("IBM BladeCenter") >= 0) {
/* 537 */           str1 = replace(str1, "IBM BladeCenter", "BC");
/* 538 */         } else if (str1.indexOf("BladeCenter") >= 0) {
/* 539 */           str1 = replace(str1, "BladeCenter", "BC");
/* 540 */         } else if (str1.indexOf("Lenovo HyperScale") >= 0) {
/* 541 */           str1 = replace(str1, "Lenovo HyperScale", "HS");
/* 542 */         } else if (str1.indexOf("Lenovo Flex System") >= 0) {
/* 543 */           str1 = replace(str1, "Lenovo Flex System", "FS");
/* 544 */         } else if (str1.indexOf("IBM Flex System") >= 0) {
/* 545 */           str1 = replace(str1, "IBM Flex System", "FS");
/* 546 */         } else if (str1.indexOf("xSeries") >= 0) {
/* 547 */           str1 = replace(str1, "xSeries", "x");
/* 548 */         } else if (str1.indexOf("System x") >= 0) {
/* 549 */           str1 = replace(str1, "System x", "x");
/*     */         } 
/* 551 */         str1 = removeIBMSubString(str1);
/*     */         
/* 553 */         stringBuffer.append(str1);
/* 554 */         stringBuffer.append(SPACE + STAR + SPACE);
/* 555 */         D.ebug(3, str + " after 1 and 2: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 558 */         if (isDigit(this.m_utility.getAttrValue(entityItem2, ATT_NOOFPROCSTD))) {
/* 559 */           int i = Integer.parseInt(this.m_utility.getAttrValue(entityItem2, ATT_NOOFPROCSTD));
/* 560 */           if (i > 1) {
/* 561 */             stringBuffer.append(i + "x");
/*     */           }
/*     */         } 
/* 564 */         D.ebug(3, str + " after 3: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 569 */         String str2 = this.m_utility.getAttrValue(entityItem3, "MFRNAM");
/* 570 */         if (str2.equals(MFRNAM_AMD)) {
/* 571 */           stringBuffer.append(this.m_utility.getAttrValueDesc(entityItem3, "MFRNAM"));
/*     */         } else {
/* 573 */           String str3 = this.m_utility.getAttrValue(entityItem3, ATT_CLOCKRATEUNIT);
/* 574 */           stringBuffer.append(getString(getValueDesc(entityItem3, "CLOCKRATE"), 4));
/* 575 */           stringBuffer.append(str3.equals(CLOCKRATEUNIT_GHZ) ? this.m_utility.getAttrValueDesc(entityItem3, ATT_CLOCKRATEUNIT) : "");
/*     */         } 
/* 577 */         stringBuffer.append(SPACE);
/* 578 */         D.ebug(3, str + " after 4 and 5: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 582 */         stringBuffer.append(getString(getValueDesc(entityItem2, ATT_TOTL2CACHESTD), 4));
/* 583 */         stringBuffer.append(getValueDesc(entityItem2, "TOTL2CACHESTDUNIT"));
/* 584 */         stringBuffer.append(SPACE);
/* 585 */         D.ebug(3, str + " after 6 and 7: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 589 */         stringBuffer.append(getString(getValueDesc(entityItem2, ATT_MEMRYRAMSTD), 4));
/* 590 */         stringBuffer.append(getValueDesc(entityItem2, "MEMRYRAMSTDUNIT"));
/* 591 */         stringBuffer.append(SPACE);
/* 592 */         D.ebug(3, str + " after 8 and 9: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 595 */         if (isDigit(this.m_utility.getAttrValue(entityItem2, ATT_NOOFINSTHARDDRVS))) {
/* 596 */           int i = Integer.parseInt(this.m_utility.getAttrValue(entityItem2, ATT_NOOFINSTHARDDRVS));
/* 597 */           if (i > 1) {
/* 598 */             stringBuffer.append(i + "x");
/*     */           }
/*     */         } 
/* 601 */         D.ebug(3, str + " after 10: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 605 */         stringBuffer.append(getValueDesc(entityItem5, ATT_HDDCAP));
/* 606 */         stringBuffer.append(getValueDesc(entityItem5, ATT_CAPUNIT));
/* 607 */         stringBuffer.append(SPACE);
/* 608 */         D.ebug(3, str + " after 11 and 12: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 612 */         stringBuffer.append(getString(getValueDesc(entityItem6, "BUS"), 4));
/* 613 */         stringBuffer.append(SPACE);
/* 614 */         D.ebug(3, str + " after 13 and 14: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 618 */         stringBuffer.append(getValueDesc(entityItem4, "TOTCARDSLOT") + "x");
/* 619 */         stringBuffer.append(SPACE);
/* 620 */         D.ebug(3, str + " after 15 and 16: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 623 */         stringBuffer.append(getValueDesc(entityItem8, "TOTBAY"));
/* 624 */         D.ebug(3, str + " after 17: " + stringBuffer.toString());
/* 625 */       } else if (paramString.equals("PRCFILENAM")) {
/* 626 */         D.ebug(3, str + " for PRCFILENAM.");
/*     */ 
/*     */         
/* 629 */         String str1 = getValueDesc(entityItem1, ATT_SERNAM);
/* 630 */         if (str1.indexOf("IBM eServer BladeCenter") >= 0) {
/* 631 */           str1 = replace(str1, "IBM eServer BladeCenter", "BC");
/* 632 */         } else if (str1.indexOf("IBM BladeCenter") >= 0) {
/* 633 */           str1 = replace(str1, "IBM BladeCenter", "BC");
/* 634 */         } else if (str1.indexOf("BladeCenter") >= 0) {
/* 635 */           str1 = replace(str1, "BladeCenter", "BC");
/* 636 */         } else if (str1.indexOf("Lenovo HyperScale") >= 0) {
/* 637 */           str1 = replace(str1, "Lenovo HyperScale", "HS");
/* 638 */         } else if (str1.indexOf("Lenovo Flex System") >= 0) {
/* 639 */           str1 = replace(str1, "Lenovo Flex System", "FS");
/* 640 */         } else if (str1.indexOf("IBM Flex System") >= 0) {
/* 641 */           str1 = replace(str1, "IBM Flex System", "FS");
/* 642 */         } else if (str1.indexOf("xSeries") >= 0) {
/* 643 */           str1 = replace(str1, "xSeries", "x");
/* 644 */         } else if (str1.indexOf("System x") >= 0) {
/* 645 */           str1 = replace(str1, "System x", "x");
/*     */         } 
/* 647 */         str1 = removeIBMSubString(str1);
/*     */         
/* 649 */         stringBuffer.append(str1);
/*     */         
/* 651 */         stringBuffer.append(SPACE);
/* 652 */         D.ebug(3, str + " after 1 and 2: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 655 */         if (isDigit(this.m_utility.getAttrValue(entityItem2, ATT_NOOFPROCSTD))) {
/* 656 */           int i = Integer.parseInt(this.m_utility.getAttrValue(entityItem2, ATT_NOOFPROCSTD));
/* 657 */           if (i > 1) {
/* 658 */             stringBuffer.append(i + "x");
/*     */           }
/*     */         } 
/* 661 */         D.ebug(3, str + " after 3: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 665 */         String str2 = this.m_utility.getAttrValue(entityItem3, ATT_CLOCKRATEUNIT);
/* 666 */         stringBuffer.append(getString(getValueDesc(entityItem3, "CLOCKRATE"), 4));
/* 667 */         stringBuffer.append(str2.equals(CLOCKRATEUNIT_GHZ) ? this.m_utility.getAttrValueDesc(entityItem3, ATT_CLOCKRATEUNIT) : "");
/* 668 */         stringBuffer.append(SPACE);
/* 669 */         D.ebug(3, str + " after 4 and 5: " + stringBuffer.toString());
/*     */ 
/*     */ 
/*     */         
/* 673 */         stringBuffer.append(getString(getValueDesc(entityItem2, ATT_TOTL2CACHESTD), 4));
/* 674 */         stringBuffer.append(SPACE);
/* 675 */         D.ebug(3, str + " after 6 and 7: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 678 */         stringBuffer.append(getString(getValueDesc(entityItem2, ATT_MEMRYRAMSTD), 4));
/* 679 */         D.ebug(3, str + " after 8: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 682 */         stringBuffer.append("/" + getValueDesc(entityItem5, ATT_HDDCAP));
/* 683 */         D.ebug(3, str + " after 9: " + stringBuffer.toString());
/*     */ 
/*     */         
/* 686 */         if (isDigit(this.m_utility.getAttrValue(entityItem2, ATT_NOOFINSTHARDDRVS))) {
/* 687 */           int i = Integer.parseInt(this.m_utility.getAttrValue(entityItem2, ATT_NOOFINSTHARDDRVS));
/* 688 */           if (i > 1) {
/* 689 */             stringBuffer.append(i + "x");
/*     */           }
/*     */         } 
/* 692 */         D.ebug(3, str + " after 10: " + stringBuffer.toString());
/*     */         
/* 694 */         stringBuffer.append(getValueDesc(entityItem5, ATT_HDDCAP));
/* 695 */         stringBuffer.append(getValueDesc(entityItem5, ATT_CAPUNIT));
/* 696 */         D.ebug(3, str + " after 11: " + stringBuffer.toString());
/*     */       } 
/*     */     } 
/*     */     
/* 700 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private String removeIBMSubString(String paramString) {
/* 704 */     while (paramString.indexOf("IBM ") >= 0) {
/* 705 */       paramString = replace(paramString, "IBM ", "");
/*     */     }
/* 707 */     while (paramString.indexOf("IBM") >= 0) {
/* 708 */       paramString = replace(paramString, "IBM", "");
/*     */     }
/* 710 */     return paramString;
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 714 */     String str = "";
/* 715 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 717 */     while (paramString1.length() > 0 && i >= 0) {
/* 718 */       str = str + paramString1.substring(0, i) + paramString3;
/* 719 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 720 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 722 */     str = str + paramString1;
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
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 734 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 743 */     return "WWSEO ALWR With CD ABR.";
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
/* 754 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 764 */     return new String("1.14");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 774 */     return "WWSEOABRWWMI.java,v 1.14 2008/01/30 19:39:15 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 784 */     return "WWSEOABRWWMI.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWSEOABRWWMI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */