/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANObject;
/*      */ import COM.ibm.eannounce.objects.EANUtility;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*      */ import java.sql.Date;
/*      */ import java.sql.SQLException;
/*      */ import java.util.StringTokenizer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CHQGENAVAIL
/*      */   extends PokBaseABR
/*      */ {
/*  181 */   public static final String ABR = new String("CHQGENAVAIL");
/*      */ 
/*      */   
/*  184 */   public static final String MTM = new String("MTM");
/*      */ 
/*      */   
/*  187 */   public static final String RELATEDFC = new String("RELATED FEATURES");
/*      */ 
/*      */ 
/*      */   
/*  191 */   public static final String RELATEDFCCONV = new String("RELATED CONVERSIONS");
/*      */ 
/*      */ 
/*      */   
/*  195 */   public static final String NEWFC = new String("NEW FEATURES");
/*      */ 
/*      */ 
/*      */   
/*  199 */   public static final String FCCONVERSIONS = new String("FEATURE CONVERSIONS");
/*      */ 
/*      */ 
/*      */   
/*  203 */   public static final String WDMTM = new String("WITHDRAW MTM");
/*      */ 
/*      */ 
/*      */   
/*  207 */   public static final String WDFC = new String("WITHDRAWING FEATURES");
/*      */ 
/*      */ 
/*      */   
/*  211 */   public static final String RFATITLE = new String("RFA TITLE");
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int EQUAL = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int LATER = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int EARLIER = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int MODEL = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int FEATURE = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int WITHDRAWMTM = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int WITHDRAWFC = 3;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int FCONV = 4;
/*      */ 
/*      */   
/*      */   public static final int MCONV = 5;
/*      */ 
/*      */   
/*      */   public static final char QM = '?';
/*      */ 
/*      */   
/*  252 */   private EntityGroup m_egParent = null;
/*  253 */   private EntityItem m_ei = null;
/*  254 */   private PDGUtility m_utility = new PDGUtility();
/*  255 */   private StringBuffer m_sbError = new StringBuffer();
/*  256 */   private OPICMList m_availAtts = new OPICMList();
/*  257 */   private OPICMList m_availWDAtts = new OPICMList();
/*      */   
/*  259 */   private String m_strAnnDate = null;
/*  260 */   private String m_strRFAProf = null;
/*  261 */   private String m_strInventoryGroup = null;
/*  262 */   private ExtractActionItem m_xaiAutoGen1 = null;
/*  263 */   private ExtractActionItem m_xaiAutoGen2 = null;
/*  264 */   private ExtractActionItem m_xaiAutoGen3 = null;
/*  265 */   private ExtractActionItem m_xaiAutoGen4 = null;
/*  266 */   private ExtractActionItem m_xaiAutoGen5 = null;
/*  267 */   private EANList m_newAvailList = new EANList();
/*  268 */   private final String[] m_updateAvailAttrs = new String[] { "COMNAME", "EFFECTIVEDATE", "GENAREASELECTION", "COUNTRYLIST" };
/*  269 */   private EntityGroup m_egAVAIL = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*      */     try {
/*  277 */       OPICMList oPICMList = null;
/*      */ 
/*      */ 
/*      */       
/*  281 */       start_ABRBuild();
/*      */       
/*  283 */       buildReportHeaderII();
/*      */       
/*  285 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  286 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  287 */       println("<br><b>Announcement: " + this.m_ei.getKey() + "</b>");
/*      */       
/*  289 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  290 */       setReturnCode(0);
/*  291 */       getAnnAtributes(this.m_elist, this.m_ei);
/*      */       
/*  293 */       this.m_xaiAutoGen1 = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTAUTOGEN01");
/*  294 */       this.m_xaiAutoGen2 = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTAUTOGEN02");
/*  295 */       this.m_xaiAutoGen3 = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTAUTOGEN03");
/*  296 */       this.m_xaiAutoGen4 = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTAUTOGEN04");
/*  297 */       this.m_xaiAutoGen5 = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTAUTOGEN05");
/*  298 */       this.m_egAVAIL = new EntityGroup(null, this.m_db, this.m_prof, "AVAIL", "Edit", false);
/*  299 */       availHandling();
/*  300 */       this.m_prof = this.m_utility.setProfValOnEffOn(this.m_db, this.m_prof);
/*  301 */       if (this.m_strRFAProf != null && this.m_strRFAProf.length() > 0) {
/*  302 */         processRFAProf(this.m_strRFAProf);
/*      */       } else {
/*  304 */         setReturnCode(-1);
/*  305 */         this.m_sbError.append("\nError: RFAPROFILE is empty");
/*      */       } 
/*      */       
/*  308 */       if (this.m_sbError.toString().length() > 0) {
/*  309 */         println("<h3><font color=red>" + replace(this.m_sbError.toString(), "\n", "<br>") + "</h3>");
/*      */       }
/*      */       
/*  312 */       oPICMList = new OPICMList();
/*      */       
/*  314 */       oPICMList.put("RFALOG", "RFALOG= " + this.m_sbError.toString());
/*  315 */       if (getReturnCode() == 0) {
/*  316 */         oPICMList.put("CHQAUTOGEN", "CHQAUTOGEN=0020");
/*      */       } else {
/*  318 */         oPICMList.put("CHQAUTOGEN", "CHQAUTOGEN=0010");
/*      */       } 
/*      */       
/*  321 */       this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*  322 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  323 */       setReturnCode(-2);
/*  324 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException.getMessage() + "</font></h3>");
/*  325 */       logError(lockPDHEntityException.getMessage());
/*  326 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  327 */       setReturnCode(-2);
/*  328 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException.getMessage() + "</font></h3>");
/*  329 */       logError(updatePDHEntityException.getMessage());
/*  330 */     } catch (SBRException sBRException) {
/*  331 */       String str2 = sBRException.toString();
/*  332 */       int i = str2.indexOf("(ok)");
/*  333 */       if (i < 0) {
/*  334 */         setReturnCode(-2);
/*  335 */         println("<h3><font color=red>Generate Data error: " + replace(str2, "\n", "<br>") + "</font></h3>");
/*  336 */         logError(sBRException.toString());
/*      */       } else {
/*  338 */         str2 = str2.substring(0, i);
/*  339 */         println(replace(str2, "\n", "<br>"));
/*      */       } 
/*  341 */     } catch (Exception exception) {
/*      */       
/*  343 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/*  344 */       println("" + exception);
/*  345 */       exception.printStackTrace();
/*  346 */       exception.printStackTrace();
/*      */       
/*  348 */       if (getABRReturnCode() != -2) {
/*  349 */         setReturnCode(-3);
/*      */       }
/*      */     } finally {
/*  352 */       String str = null;
/*  353 */       println("<br /><b>" + 
/*      */           
/*  355 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */               
/*  358 */               getABRDescription(), 
/*  359 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */             }) + "</b>");
/*      */       
/*  362 */       log(buildLogMessage("IAB2016I: %1# has %2#.", new String[] { getABRDescription(), (getReturnCode() == 0) ? "Passed" : "Failed" }));
/*      */ 
/*      */       
/*  365 */       str = this.m_ei.toString();
/*  366 */       if (str.length() > 64) {
/*  367 */         str = str.substring(0, 64);
/*      */       }
/*  369 */       setDGTitle(str);
/*  370 */       setDGRptName(ABR);
/*      */ 
/*      */       
/*  373 */       setDGString(getABRReturnCode());
/*  374 */       printDGSubmitString();
/*      */ 
/*      */       
/*  377 */       buildReportFooter();
/*      */       
/*  379 */       if (!isReadOnly()) {
/*  380 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void getAnnAtributes(EntityList paramEntityList, EntityItem paramEntityItem) {
/*  386 */     String str = "CHQGENAVAIL trace getAnnAttributes method ";
/*  387 */     System.out.println(str);
/*  388 */     this.m_availAtts.put("AVAIL:AVAILTYPE", "map_AVAILTYPE=146");
/*  389 */     this.m_availWDAtts.put("AVAIL:AVAILTYPE", "map_AVAILTYPE=149");
/*      */     
/*  391 */     for (byte b = 0; b < paramEntityItem.getAttributeCount(); b++) {
/*      */       
/*  393 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(b);
/*  394 */       StringBuffer stringBuffer = new StringBuffer();
/*      */       
/*  396 */       if (eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/*  397 */         stringBuffer.append(((String)eANAttribute.get()).trim());
/*  398 */       } else if (eANAttribute instanceof COM.ibm.eannounce.objects.EANFlagAttribute) {
/*  399 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/*  400 */         boolean bool = true;
/*  401 */         for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*  402 */           MetaFlag metaFlag = arrayOfMetaFlag[b1];
/*  403 */           if (metaFlag.isSelected()) {
/*  404 */             stringBuffer.append(bool ? "" : ",");
/*  405 */             stringBuffer.append(metaFlag.getFlagCode());
/*  406 */             bool = false;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  411 */       if (eANAttribute.getKey().equals("ANNCODENAME")) {
/*  412 */         this.m_availAtts.put("AVAIL:ANNCODENAME", "map_ANNCODENAME=" + stringBuffer.toString());
/*  413 */         this.m_availWDAtts.put("AVAIL:ANNCODENAME", "map_ANNCODENAME=" + stringBuffer.toString());
/*  414 */       } else if (eANAttribute.getKey().equals("ANNDATE")) {
/*  415 */         this.m_strAnnDate = stringBuffer.toString();
/*  416 */         this.m_availAtts.put("AVAIL:EFFECTIVEDATE", "EFFECTIVEDATE=" + this.m_strAnnDate);
/*  417 */         this.m_availWDAtts.put("AVAIL:EFFECTIVEDATE", "EFFECTIVEDATE=" + this.m_strAnnDate);
/*  418 */       } else if (eANAttribute.getKey().equals("GENAREASELECTION")) {
/*  419 */         this.m_availAtts.put("AVAIL:GENAREASELECTION", "GENAREASELECTION=" + stringBuffer.toString());
/*  420 */         this.m_availWDAtts.put("AVAIL:GENAREASELECTION", "GENAREASELECTION=" + stringBuffer.toString());
/*  421 */       } else if (eANAttribute.getKey().equals("COUNTRYLIST")) {
/*  422 */         this.m_availAtts.put("AVAIL:COUNTRYLIST", "COUNTRYLIST=" + stringBuffer.toString());
/*  423 */         this.m_availWDAtts.put("AVAIL:COUNTRYLIST", "COUNTRYLIST=" + stringBuffer.toString());
/*  424 */       } else if (eANAttribute.getKey().equals("PDHDOMAIN")) {
/*  425 */         this.m_availAtts.put("AVAIL:PDHDOMAIN", "PDHDOMAIN=" + stringBuffer.toString());
/*  426 */         this.m_availWDAtts.put("AVAIL:PDHDOMAIN", "PDHDOMAIN=" + stringBuffer.toString());
/*  427 */       } else if (eANAttribute.getKey().equals("RFAPROFILE")) {
/*  428 */         this.m_strRFAProf = stringBuffer.toString();
/*  429 */       } else if (eANAttribute.getKey().equals("INVENTORYGROUP")) {
/*  430 */         this.m_strInventoryGroup = stringBuffer.toString();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void processRFAProf(String paramString) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  436 */     String str1 = "CHQGENAVAIL trace processRFAProf method ";
/*  437 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "\n");
/*  438 */     String str2 = "";
/*  439 */     System.out.println(str1);
/*      */     
/*  441 */     while (stringTokenizer.hasMoreTokens()) {
/*  442 */       String str = stringTokenizer.nextToken().trim();
/*  443 */       System.out.println(str1 + " s1 " + str);
/*  444 */       System.out.println(str1 + " strCurSection " + str2);
/*  445 */       if (str.length() > 0) {
/*  446 */         char c = str.charAt(0);
/*  447 */         if (c == '*') {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  454 */         if (str.equals(MTM)) {
/*  455 */           str2 = MTM; continue;
/*  456 */         }  if (str.equals(RELATEDFC)) {
/*  457 */           str2 = RELATEDFC; continue;
/*  458 */         }  if (str.equals(RELATEDFCCONV)) {
/*  459 */           str2 = RELATEDFCCONV; continue;
/*  460 */         }  if (str.equals(NEWFC)) {
/*  461 */           str2 = NEWFC; continue;
/*  462 */         }  if (str.equals(FCCONVERSIONS)) {
/*  463 */           str2 = FCCONVERSIONS; continue;
/*  464 */         }  if (str.equals(WDMTM)) {
/*  465 */           str2 = WDMTM; continue;
/*  466 */         }  if (str.equals(WDFC)) {
/*  467 */           str2 = WDFC; continue;
/*  468 */         }  if (str.equals(RFATITLE)) {
/*  469 */           str2 = RFATITLE; continue;
/*      */         } 
/*  471 */         if (str2.equals(MTM)) {
/*  472 */           modelAnnouncement(str); continue;
/*  473 */         }  if (str2.equals(RELATEDFC) || str2.equals(NEWFC)) {
/*  474 */           featureAnnouncement(str); continue;
/*  475 */         }  if (str2.equals(RELATEDFCCONV) || str2.equals(FCCONVERSIONS)) {
/*  476 */           fConvAnnouncement(str); continue;
/*  477 */         }  if (str2.equals(WDMTM)) {
/*  478 */           withdrawMTM(str); continue;
/*  479 */         }  if (str2.equals(WDFC)) {
/*  480 */           withdrawFeature(str); continue;
/*  481 */         }  if (str2.equals(RFATITLE)) {
/*  482 */           this.m_availAtts.put("AVAIL:COMNAME", "COMNAME=" + str);
/*  483 */           this.m_availWDAtts.put("AVAIL:COMNAME", "COMNAME=" + str);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private int dateCompare(String paramString1, String paramString2) {
/*  490 */     String str = "CHQGENAVAIL trace dateCompare method ";
/*  491 */     System.out.println(str + ":" + paramString1 + ":" + paramString2);
/*      */     
/*      */     try {
/*  494 */       Date date1 = Date.valueOf(paramString1);
/*  495 */       Date date2 = Date.valueOf(paramString2);
/*      */       
/*  497 */       long l1 = date1.getTime();
/*  498 */       long l2 = date2.getTime();
/*  499 */       long l3 = l1 - l2;
/*      */       
/*  501 */       if (l3 < 0L)
/*  502 */         return 2; 
/*  503 */       if (l3 > 0L) {
/*  504 */         return 1;
/*      */       }
/*      */     }
/*  507 */     catch (IllegalArgumentException illegalArgumentException) {
/*  508 */       println("Wrong date format: :" + paramString1 + ":" + paramString2);
/*  509 */       throw illegalArgumentException;
/*      */     } 
/*  511 */     return 0;
/*      */   }
/*      */   
/*      */   private boolean modelAnnouncement(String paramString) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  515 */     String str1 = "CHQGENAVAIL trace modelAnnouncement method ";
/*  516 */     boolean bool = false;
/*  517 */     boolean bool1 = false;
/*  518 */     String str2 = new String("New Model Announcement.");
/*  519 */     String str3 = null;
/*  520 */     String str4 = null;
/*  521 */     StringBuffer stringBuffer = new StringBuffer();
/*  522 */     EntityItem[] arrayOfEntityItem1 = null;
/*  523 */     EntityList entityList = null;
/*  524 */     EntityGroup entityGroup1 = null;
/*  525 */     EntityItem[] arrayOfEntityItem2 = null;
/*  526 */     EntityItem[] arrayOfEntityItem3 = null;
/*  527 */     EntityGroup entityGroup2 = null;
/*  528 */     EntityGroup entityGroup3 = null;
/*      */     
/*  530 */     int i = paramString.indexOf(" ");
/*  531 */     int j = paramString.indexOf("-");
/*      */     
/*  533 */     System.out.println(str1 + ":" + paramString);
/*      */     
/*  535 */     if (i >= 0) {
/*  536 */       paramString = paramString.substring(0, i);
/*      */     }
/*      */     
/*  539 */     if (j < 0) {
/*  540 */       this.m_sbError.append("\nError: " + str2);
/*  541 */       this.m_sbError.append("\nError: " + paramString + " is not in format MMMM-mmm");
/*  542 */       return false;
/*      */     } 
/*      */     
/*  545 */     str3 = paramString.substring(0, j);
/*  546 */     str4 = paramString.substring(j + 1);
/*      */     
/*  548 */     stringBuffer.append("map_MACHTYPEATR=" + str3 + ";");
/*  549 */     stringBuffer.append("map_MODELATR=" + str4);
/*      */     
/*      */     try {
/*  552 */       arrayOfEntityItem1 = this.m_utility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDMODEL5", "MODEL", stringBuffer.toString());
/*  553 */     } catch (SBRException sBRException) {
/*  554 */       System.out.println(sBRException.toString());
/*  555 */       arrayOfEntityItem1 = null;
/*      */     } 
/*  557 */     if (arrayOfEntityItem1 == null || arrayOfEntityItem1.length <= 0) {
/*  558 */       setReturnCode(-1);
/*  559 */       bool = true;
/*  560 */       this.m_sbError.append("\nError: " + str2);
/*  561 */       this.m_sbError.append("\nError: There isn't MODEL with machine type " + str3 + ", model " + str4);
/*  562 */       return false;
/*      */     } 
/*      */     
/*  565 */     entityList = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen1, arrayOfEntityItem1);
/*  566 */     entityGroup1 = entityList.getParentEntityGroup();
/*  567 */     if (!entityGroup1.getEntityType().equals("MODEL")) {
/*  568 */       setReturnCode(-1);
/*  569 */       bool = true;
/*  570 */       this.m_sbError.append("\nError: " + str2);
/*  571 */       this.m_sbError.append("\nError: Parent Entity Group is not MODEL.");
/*  572 */       return false;
/*      */     } 
/*      */     byte b;
/*  575 */     for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/*  576 */       EntityGroup entityGroup = null;
/*  577 */       EntityItem entityItem = entityGroup1.getEntityItem(b);
/*  578 */       String str = this.m_utility.getAttrValue(entityItem, "ANNDATE");
/*  579 */       if (str == null || str.length() <= 0 || dateCompare(str, this.m_strAnnDate) != 0) {
/*  580 */         if (!bool1) {
/*  581 */           this.m_sbError.append("\nError: " + str2);
/*      */         }
/*  583 */         bool1 = true;
/*  584 */         this.m_sbError.append("\nWarning: Model: " + entityItem.toString() + " Announce Date: " + str + " does not match with Announcement Announce Date:" + this.m_strAnnDate);
/*  585 */         logMessage("Warning: Model: " + entityItem.toString() + " Announce Date: " + str + " does not match with Announcement Announce Date:" + this.m_strAnnDate);
/*      */       }
/*      */       else {
/*      */         
/*  589 */         bool = autogenAVAIL(entityList, entityItem, "MODELAVAIL", true, false, 0, bool);
/*      */         
/*  591 */         entityGroup = entityList.getEntityGroup("PRODSTRUCT");
/*      */ 
/*      */         
/*  594 */         for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*  595 */           boolean bool2 = false;
/*  596 */           EntityItem entityItem1 = entityGroup.getEntityItem(b1);
/*  597 */           EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*  598 */           EntityItem entityItem3 = (EntityItem)entityItem1.getUpLink(0);
/*  599 */           if (entityItem2.getKey().equals(entityItem.getKey())) {
/*  600 */             String str5 = this.m_utility.getAttrValue(entityItem1, "ANNDATE");
/*  601 */             String str6 = this.m_utility.getAttrValue(entityItem3, "FIRSTANNDATE");
/*  602 */             if (str5 != null && str5.length() > 0) {
/*  603 */               int k = -1;
/*  604 */               if (str6 != null && str6.length() > 0) {
/*  605 */                 int m = dateCompare(str5, str6);
/*  606 */                 int n = dateCompare(str5, str);
/*  607 */                 if (m == 2 || n == 2) {
/*  608 */                   if (!bool1) {
/*  609 */                     this.m_sbError.append("\nError: " + str2);
/*      */                   }
/*      */                   
/*  612 */                   this.m_sbError.append("\nWarning: Product Structure: " + entityItem1.toString() + " Announce Date: " + str5 + " is earlier than the Feature:" + str6 + " and Model: " + str);
/*  613 */                   bool1 = true;
/*      */                   
/*      */                   continue;
/*      */                 } 
/*      */               } 
/*  618 */               k = dateCompare(str5, this.m_strAnnDate);
/*  619 */               if (k == 0) {
/*  620 */                 EntityItem[] arrayOfEntityItem = { entityItem1 };
/*  621 */                 EntityList entityList1 = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen4, arrayOfEntityItem);
/*  622 */                 bool2 = autogenAVAIL(entityList1, entityItem1, "OOFAVAIL", true, false, 0, bool);
/*  623 */                 if (bool2) {
/*  624 */                   bool = bool2;
/*      */                 }
/*  626 */               } else if (k == 2) {
/*  627 */                 if (!bool1) {
/*  628 */                   this.m_sbError.append("\nError: " + str2);
/*      */                 }
/*      */                 
/*  631 */                 this.m_sbError.append("\nWarning: Product Structure: " + entityItem1.toString() + " Announce Date: " + str5 + " is earlier than the Profile Announcement Date:" + this.m_strAnnDate);
/*  632 */                 bool1 = true;
/*      */               } 
/*      */               
/*      */               continue;
/*      */             } 
/*  637 */             String str7 = this.m_utility.getAttrValue(entityItem3, "FIRSTANNDATE");
/*  638 */             if (str7 != null && str7.length() > 0) {
/*  639 */               int k = dateCompare(str7, this.m_strAnnDate);
/*  640 */               if (str != null && str.length() > 0 && dateCompare(str, str7) == 1) {
/*  641 */                 k = dateCompare(str, this.m_strAnnDate);
/*      */               }
/*      */               
/*  644 */               if (k == 0) {
/*  645 */                 EntityItem[] arrayOfEntityItem = { entityItem1 };
/*  646 */                 EntityList entityList1 = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen4, arrayOfEntityItem);
/*      */                 
/*  648 */                 bool2 = autogenAVAIL(entityList1, entityItem1, "OOFAVAIL", true, false, 0, bool);
/*  649 */                 if (bool2) {
/*  650 */                   bool = bool2;
/*      */                 }
/*      */               } 
/*      */             } else {
/*  654 */               int k = dateCompare(str, this.m_strAnnDate);
/*      */               
/*  656 */               if (k == 0) {
/*  657 */                 EntityItem[] arrayOfEntityItem = { entityItem1 };
/*  658 */                 EntityList entityList1 = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen4, arrayOfEntityItem);
/*      */                 
/*  660 */                 bool2 = autogenAVAIL(entityList1, entityItem1, "OOFAVAIL", true, false, 0, bool);
/*  661 */                 if (bool2) {
/*  662 */                   bool = bool2;
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  674 */     stringBuffer = new StringBuffer();
/*      */     
/*  676 */     stringBuffer.append("map_FROMMACHTYPE=" + str3 + ";");
/*  677 */     stringBuffer.append("map_FROMMODEL=" + str4);
/*      */     
/*  679 */     arrayOfEntityItem2 = this.m_utility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDMODELCONVERT01", "MODELCONVERT", stringBuffer.toString());
/*  680 */     mConvertAnnouncement(entityList, arrayOfEntityItem2, str3, str4);
/*      */     
/*  682 */     stringBuffer = new StringBuffer();
/*      */     
/*  684 */     stringBuffer.append("map_TOMACHTYPE=" + str3 + ";");
/*  685 */     stringBuffer.append("map_TOMODEL=" + str4);
/*      */     
/*  687 */     arrayOfEntityItem3 = this.m_utility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDMODELCONVERT01", "MODELCONVERT", stringBuffer.toString());
/*  688 */     mConvertAnnouncement(entityList, arrayOfEntityItem3, str3, str4);
/*      */ 
/*      */     
/*  691 */     entityGroup2 = entityList.getEntityGroup("MODELCONVERT");
/*  692 */     System.out.println(str1 + " include model convert size: " + entityGroup2.getEntityItemCount());
/*      */     
/*  694 */     if (entityGroup2.getEntityItemCount() > 0) {
/*  695 */       mConvertAnnouncement(entityList, str3, str4);
/*      */     }
/*      */     
/*  698 */     entityGroup3 = entityList.getEntityGroup("FCTRANSACTION");
/*  699 */     System.out.println(str1 + " include FCTRANSACTION size: " + entityGroup3.getEntityItemCount());
/*  700 */     for (b = 0; b < entityGroup3.getEntityItemCount(); b++) {
/*  701 */       EntityItem entityItem = entityGroup3.getEntityItem(b);
/*  702 */       announceFCONV(entityItem);
/*      */     } 
/*  704 */     if (!bool && !bool1) {
/*  705 */       this.m_sbError.append("\nSuccess: " + str2);
/*      */     }
/*  707 */     return true;
/*      */   }
/*      */   
/*      */   private boolean mConvertAnnouncement(EntityList paramEntityList, EntityItem[] paramArrayOfEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  711 */     String str1 = "CHQGENAVAIL trace mConvertAnnouncement method ";
/*  712 */     boolean bool = false;
/*  713 */     String str2 = new String("New Model Convert Announcement.");
/*  714 */     System.out.println(str1 + " model convert ");
/*      */ 
/*      */     
/*  717 */     for (byte b = 0; b < paramArrayOfEntityItem.length; b++) {
/*  718 */       boolean bool1 = false;
/*  719 */       EntityItem[] arrayOfEntityItem = null;
/*      */       
/*  721 */       EntityItem entityItem = paramArrayOfEntityItem[b];
/*  722 */       String str3 = this.m_utility.getAttrValue(entityItem, "FROMMACHTYPE");
/*  723 */       String str4 = this.m_utility.getAttrValue(entityItem, "FROMMODEL");
/*  724 */       String str5 = this.m_utility.getAttrValue(entityItem, "TOMACHTYPE");
/*  725 */       String str6 = this.m_utility.getAttrValue(entityItem, "TOMODEL");
/*  726 */       if (str3 == null || str4 == null || str5 == null || str6 == null) {
/*  727 */         setReturnCode(-1);
/*  728 */         if (!bool) {
/*  729 */           this.m_sbError.append("\nError: " + str2);
/*  730 */           bool = true;
/*      */         } 
/*  732 */         this.m_sbError.append("\nError: From machine type model and To Machine type model can not be empty.");
/*      */       } else {
/*  734 */         StringBuffer stringBuffer = new StringBuffer();
/*      */         
/*  736 */         System.out.println(str1 + " fromMT: " + str3 + " fromModel: " + str4 + " toMT: " + str5 + " toModel: " + str6);
/*  737 */         if (str3.equals(paramString1) && str4.equals(paramString2)) {
/*  738 */           stringBuffer.append("map_MACHTYPEATR=" + str5 + ";");
/*  739 */           stringBuffer.append("map_MODELATR=" + str6);
/*      */         } 
/*      */         
/*  742 */         if (str5.equals(paramString1) && str6.equals(paramString2)) {
/*  743 */           stringBuffer.append("map_MACHTYPEATR=" + str3 + ";");
/*  744 */           stringBuffer.append("map_MODELATR=" + str4);
/*      */         } 
/*      */         
/*  747 */         arrayOfEntityItem = this.m_utility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDMODEL5", "MODEL", stringBuffer.toString());
/*      */         
/*  749 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  750 */           for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*  751 */             EntityItem entityItem1 = arrayOfEntityItem[b1];
/*  752 */             String str = this.m_utility.getAttrValue(entityItem1, "ANNDATE");
/*  753 */             if (str != null && str.length() > 0 && dateCompare(str, this.m_strAnnDate) != 1) {
/*  754 */               bool1 = autogenAVAIL(paramEntityList, entityItem, "MODELCONVERTAVAIL", true, false, 5, bool);
/*  755 */               if (bool1) {
/*  756 */                 bool = bool1;
/*      */               }
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*  764 */     if (!bool) {
/*  765 */       this.m_sbError.append("\nSuccess: " + str2);
/*      */     }
/*      */     
/*  768 */     return true;
/*      */   }
/*      */   
/*      */   private boolean mConvertAnnouncement(EntityList paramEntityList, String paramString1, String paramString2) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  772 */     String str1 = "CHQGENAVAIL trace mConvertAnnouncement method ";
/*  773 */     boolean bool = false;
/*  774 */     String str2 = new String("New Model Convert Announcement.");
/*  775 */     EntityGroup entityGroup = paramEntityList.getEntityGroup("MODELCONVERT");
/*      */     
/*  777 */     System.out.println(str1 + " machtype: " + paramString1 + " model " + paramString2);
/*  778 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  779 */       boolean bool1 = false;
/*  780 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  781 */       String str3 = this.m_utility.getAttrValue(entityItem, "FROMMACHTYPE");
/*  782 */       String str4 = this.m_utility.getAttrValue(entityItem, "FROMMODEL");
/*  783 */       String str5 = this.m_utility.getAttrValue(entityItem, "TOMACHTYPE");
/*  784 */       String str6 = this.m_utility.getAttrValue(entityItem, "TOMODEL");
/*  785 */       if (str3 == null || str4 == null || str5 == null || str6 == null) {
/*  786 */         setReturnCode(-1);
/*  787 */         if (!bool) {
/*  788 */           this.m_sbError.append("\nError: " + str2);
/*  789 */           bool = true;
/*      */         } 
/*  791 */         this.m_sbError.append("\nError: From machine type model and To Machine type model can not be empty.");
/*      */       } else {
/*  793 */         StringBuffer stringBuffer = new StringBuffer();
/*  794 */         EntityItem[] arrayOfEntityItem = null;
/*  795 */         System.out.println(str1 + " fromMT: " + str3 + " fromModel: " + str4 + " toMT: " + str5 + " toModel: " + str6);
/*  796 */         if (str3.equals(paramString1) && str4.equals(paramString2)) {
/*  797 */           stringBuffer.append("map_MACHTYPEATR=" + str5 + ";");
/*  798 */           stringBuffer.append("map_MODELATR=" + str6);
/*      */         } 
/*      */         
/*  801 */         if (str5.equals(paramString1) && str6.equals(paramString2)) {
/*  802 */           stringBuffer.append("map_MACHTYPEATR=" + str3 + ";");
/*  803 */           stringBuffer.append("map_MODELATR=" + str4);
/*      */         } 
/*      */         
/*  806 */         arrayOfEntityItem = this.m_utility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDMODEL5", "MODEL", stringBuffer.toString());
/*      */         
/*  808 */         if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  809 */           for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*  810 */             EntityItem entityItem1 = arrayOfEntityItem[b1];
/*  811 */             String str = this.m_utility.getAttrValue(entityItem1, "ANNDATE");
/*  812 */             if (str != null && str.length() > 0 && dateCompare(str, this.m_strAnnDate) != 1) {
/*  813 */               bool1 = autogenAVAIL(paramEntityList, entityItem, "MODELCONVERTAVAIL", true, false, 5, bool);
/*  814 */               if (bool1) {
/*  815 */                 bool = bool1;
/*      */               }
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*  823 */     if (!bool) {
/*  824 */       this.m_sbError.append("\nSuccess: " + str2);
/*      */     }
/*      */     
/*  827 */     return true;
/*      */   }
/*      */   
/*      */   private boolean featureAnnouncement(String paramString) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  831 */     String str1 = "CHQGENAVAIL trace featureAnnouncement method ";
/*  832 */     boolean bool = false;
/*  833 */     boolean bool1 = false;
/*  834 */     String str2 = new String("New Feature Announcement");
/*  835 */     int i = paramString.indexOf(" ");
/*  836 */     StringBuffer stringBuffer = new StringBuffer();
/*  837 */     EntityItem[] arrayOfEntityItem = null;
/*  838 */     EntityList entityList = null;
/*  839 */     EntityGroup entityGroup1 = null;
/*  840 */     EntityGroup entityGroup2 = null;
/*      */     
/*  842 */     System.out.println(str1 + ":" + paramString);
/*  843 */     if (this.m_strInventoryGroup == null || this.m_strInventoryGroup.length() <= 0) {
/*  844 */       setReturnCode(-1);
/*  845 */       this.m_sbError.append("\nError: " + str2);
/*  846 */       this.m_sbError.append("\nError: INVENTORYGROUP is empty");
/*  847 */       return false;
/*      */     } 
/*      */     
/*  850 */     if (i >= 0) {
/*  851 */       paramString = paramString.substring(0, i);
/*      */     }
/*      */     
/*  854 */     stringBuffer.append("map_FEATURECODE=" + paramString + ";");
/*      */     
/*  856 */     arrayOfEntityItem = this.m_utility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDFEATURE1", "FEATURE", stringBuffer.toString());
/*  857 */     if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/*  858 */       setReturnCode(-1);
/*  859 */       this.m_sbError.append("\nError: " + str2);
/*  860 */       this.m_sbError.append("\nError: There isn't FEATURE with Feature Code: " + paramString);
/*  861 */       return false;
/*      */     } 
/*      */     
/*  864 */     entityList = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen2, arrayOfEntityItem);
/*  865 */     entityGroup1 = entityList.getParentEntityGroup();
/*  866 */     if (!entityGroup1.getEntityType().equals("FEATURE")) {
/*  867 */       setReturnCode(-1);
/*  868 */       this.m_sbError.append("\nError: " + str2);
/*  869 */       this.m_sbError.append("\nError: Parent Entity Group is not FEATURE.");
/*  870 */       return false;
/*      */     } 
/*      */     
/*  873 */     entityGroup2 = entityList.getEntityGroup("PRODSTRUCT");
/*  874 */     for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/*  875 */       EANList eANList = null;
/*  876 */       String str3 = null;
/*      */       
/*  878 */       EntityItem entityItem1 = entityGroup2.getEntityItem(b);
/*  879 */       boolean bool2 = false;
/*      */       
/*  881 */       EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*  882 */       String str4 = this.m_utility.getAttrValue(entityItem2, "ANNDATE");
/*  883 */       EntityItem entityItem3 = (EntityItem)entityItem1.getUpLink(0);
/*  884 */       String str5 = this.m_utility.getAttrValue(entityItem3, "FIRSTANNDATE");
/*      */       
/*  886 */       if (str4 != null && str4.length() > 0 && dateCompare(str4, this.m_strAnnDate) != 2) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  893 */       eANList = getParent(entityList, entityItem2.getEntityType(), entityItem2.getEntityID(), "MACHTYPE", "MACHINETYPEMODELA");
/*  894 */       if (eANList.size() > 0) {
/*  895 */         EntityItem entityItem = (EntityItem)eANList.getAt(0);
/*  896 */         String str = this.m_utility.getAttrValue(entityItem, "INVENTORYGROUP");
/*  897 */         if (!str.equals(this.m_strInventoryGroup)) {
/*  898 */           System.out.println("MACHTYPE IG: " + str + " not equals " + this.m_strInventoryGroup);
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*      */       
/*  904 */       str3 = this.m_utility.getAttrValue(entityItem1, "ANNDATE");
/*  905 */       if (str3 != null && str3.length() > 0) {
/*  906 */         int j = -1;
/*  907 */         if (str5 != null && str5.length() > 0 && str4 != null && str4.length() > 0) {
/*  908 */           int k = dateCompare(str3, str5);
/*  909 */           int m = dateCompare(str3, str4);
/*  910 */           if (k == 2 || m == 2) {
/*  911 */             if (!bool1) {
/*  912 */               this.m_sbError.append("\nError: " + str2);
/*      */             }
/*      */             
/*  915 */             this.m_sbError.append("\nWarning: Product Structure: " + entityItem1.toString() + " Announce Date: " + str3 + " is earlier than the Feature:" + str5 + " and Model: " + str4);
/*  916 */             bool1 = true;
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*  921 */         j = dateCompare(str3, this.m_strAnnDate);
/*  922 */         if (j == 0) {
/*  923 */           EntityItem[] arrayOfEntityItem1 = { entityItem1 };
/*  924 */           EntityList entityList1 = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen4, arrayOfEntityItem1);
/*  925 */           bool2 = autogenAVAIL(entityList1, entityItem1, "OOFAVAIL", true, false, 1, bool);
/*  926 */           if (bool2) {
/*  927 */             bool = bool2;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  935 */       EntityItem entityItem4 = (EntityItem)entityItem1.getUpLink(0);
/*      */       
/*  937 */       String str6 = this.m_utility.getAttrValue(entityItem4, "FIRSTANNDATE");
/*  938 */       if (str6 != null && str6.length() > 0 && str4 != null && str4.length() > 0) {
/*  939 */         int j = dateCompare(str4, str6);
/*  940 */         int k = dateCompare(str6, this.m_strAnnDate);
/*  941 */         if (j == 1) {
/*  942 */           k = dateCompare(str4, this.m_strAnnDate);
/*      */         }
/*      */         
/*  945 */         if (k == 0) {
/*  946 */           EntityItem[] arrayOfEntityItem1 = { entityItem1 };
/*  947 */           EntityList entityList1 = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen4, arrayOfEntityItem1);
/*  948 */           bool2 = autogenAVAIL(entityList1, entityItem1, "OOFAVAIL", true, false, 1, bool);
/*  949 */           if (bool2) {
/*  950 */             bool = bool2;
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  957 */       else if (str4 != null && str4.length() > 0) {
/*  958 */         int j = dateCompare(str4, this.m_strAnnDate);
/*      */         
/*  960 */         if (j == 0) {
/*  961 */           EntityItem[] arrayOfEntityItem1 = { entityItem1 };
/*  962 */           EntityList entityList1 = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen4, arrayOfEntityItem1);
/*  963 */           bool2 = autogenAVAIL(entityList1, entityItem1, "OOFAVAIL", true, false, 0, bool);
/*  964 */           if (bool2) {
/*  965 */             bool = bool2;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*      */       continue;
/*      */     } 
/*  972 */     if (!bool && !bool1) {
/*  973 */       this.m_sbError.append("\nSuccess: " + str2);
/*      */     }
/*      */     
/*  976 */     return true;
/*      */   }
/*      */   
/*      */   private boolean withdrawMTM(String paramString) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  980 */     String str1 = "CHQGENAVAIL trace withdrawMTM method ";
/*  981 */     boolean bool = false;
/*  982 */     boolean bool1 = false;
/*  983 */     String str2 = new String("Withdrawing MTM.");
/*  984 */     int i = paramString.indexOf(" ");
/*  985 */     int j = paramString.indexOf("-");
/*  986 */     String str3 = null;
/*  987 */     String str4 = null;
/*  988 */     StringBuffer stringBuffer = new StringBuffer();
/*  989 */     EntityItem[] arrayOfEntityItem = null;
/*  990 */     EntityList entityList = null;
/*  991 */     EntityGroup entityGroup = null;
/*      */     
/*  993 */     System.out.println(str1 + ":" + paramString);
/*  994 */     if (i >= 0) {
/*  995 */       paramString = paramString.substring(0, i);
/*      */     }
/*      */     
/*  998 */     if (j < 0) {
/*  999 */       this.m_sbError.append("\nError: " + str2);
/* 1000 */       this.m_sbError.append("\nError: " + paramString + " is not in format MMMM-mmm");
/* 1001 */       return false;
/*      */     } 
/*      */     
/* 1004 */     str3 = paramString.substring(0, j);
/* 1005 */     str4 = paramString.substring(j + 1);
/*      */     
/* 1007 */     stringBuffer.append("map_MACHTYPEATR=" + str3 + ";");
/* 1008 */     stringBuffer.append("map_MODELATR=" + str4);
/*      */     
/*      */     try {
/* 1011 */       arrayOfEntityItem = this.m_utility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDMODEL5", "MODEL", stringBuffer.toString());
/* 1012 */     } catch (SBRException sBRException) {
/* 1013 */       System.out.println(sBRException.toString());
/* 1014 */       arrayOfEntityItem = null;
/*      */     } 
/* 1016 */     if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/* 1017 */       setReturnCode(-1);
/* 1018 */       this.m_sbError.append("\nError: " + str2);
/* 1019 */       this.m_sbError.append("\nError: There isn't MODEL with machine type " + str3 + ", model " + str4);
/* 1020 */       return false;
/*      */     } 
/*      */     
/* 1023 */     entityList = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen1, arrayOfEntityItem);
/* 1024 */     entityGroup = entityList.getParentEntityGroup();
/* 1025 */     if (!entityGroup.getEntityType().equals("MODEL")) {
/* 1026 */       setReturnCode(-1);
/* 1027 */       this.m_sbError.append("\nError: " + str2);
/* 1028 */       this.m_sbError.append("\nError: Parent Entity Group is not MODEL.");
/* 1029 */       return false;
/*      */     } 
/*      */     
/* 1032 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1033 */       boolean bool2 = false;
/* 1034 */       EntityGroup entityGroup1 = null;
/* 1035 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 1037 */       String str = this.m_utility.getAttrValue(entityItem, "WITHDRAWDATE");
/*      */       
/* 1039 */       int k = dateCompare(str, this.m_strAnnDate);
/* 1040 */       if (k != 0) {
/* 1041 */         if (!bool1) {
/* 1042 */           this.m_sbError.append("\nError: " + str2);
/*      */         }
/* 1044 */         bool1 = true;
/*      */         
/* 1046 */         this.m_sbError.append("\nWarning: Model: " + entityItem.toString() + " Withdraw Date: " + str + " does not match the Profile Date:" + this.m_strAnnDate);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1051 */         bool2 = autogenAVAIL(entityList, entityItem, "MODELAVAIL", true, true, 2, bool);
/* 1052 */         if (bool2) {
/* 1053 */           bool = bool2;
/*      */         }
/*      */         
/* 1056 */         entityGroup1 = entityList.getEntityGroup("PRODSTRUCT");
/* 1057 */         for (byte b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++) {
/* 1058 */           EntityItem entityItem1 = entityGroup1.getEntityItem(b1);
/* 1059 */           EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/* 1060 */           EntityItem entityItem3 = (EntityItem)entityItem1.getUpLink(0);
/* 1061 */           if (entityItem2.getKey().equals(entityItem.getKey())) {
/* 1062 */             String str5 = this.m_utility.getAttrValue(entityItem1, "WITHDRAWDATE");
/* 1063 */             String str6 = this.m_utility.getAttrValue(entityItem3, "WITHDRAWANNDATE_T");
/* 1064 */             if (str5 != null && str5.length() > 0) {
/* 1065 */               int m = -1;
/* 1066 */               if (str6 != null && str6.length() > 0) {
/* 1067 */                 int n = dateCompare(str5, str6);
/* 1068 */                 if (n == 1) {
/* 1069 */                   if (!bool1) {
/* 1070 */                     this.m_sbError.append("\nError: " + str2);
/*      */                   }
/*      */                   
/* 1073 */                   this.m_sbError.append("\nWarning: Product Structure: " + entityItem1.toString() + " Withdraw Date: " + str5 + " is later than the Feature:" + str6);
/* 1074 */                   bool1 = true;
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/* 1079 */               m = dateCompare(str5, this.m_strAnnDate);
/* 1080 */               if (m == 1) {
/* 1081 */                 if (!bool1) {
/* 1082 */                   this.m_sbError.append("\nError: " + str2);
/*      */                 }
/*      */                 
/* 1085 */                 this.m_sbError.append("\nWarning: Product Structure: " + entityItem1.toString() + " Announce Date: " + str5 + " is later than the Profile Announcement Date:" + this.m_strAnnDate);
/* 1086 */                 bool1 = true;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1093 */     if (!bool && !bool1) {
/* 1094 */       this.m_sbError.append("\nSuccess: " + str2);
/*      */     }
/*      */     
/* 1097 */     return true;
/*      */   }
/*      */   
/*      */   private boolean withdrawFeature(String paramString) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 1101 */     String str1 = "CHQGENAVAIL trace withdrawFeature method ";
/* 1102 */     boolean bool = false;
/* 1103 */     boolean bool1 = false;
/* 1104 */     StringBuffer stringBuffer = new StringBuffer();
/* 1105 */     String str2 = new String("Withdrawing Features.");
/* 1106 */     int i = paramString.indexOf(" ");
/* 1107 */     EntityItem[] arrayOfEntityItem = null;
/* 1108 */     EntityList entityList = null;
/* 1109 */     EntityGroup entityGroup1 = null;
/* 1110 */     EntityGroup entityGroup2 = null;
/*      */     
/* 1112 */     System.out.println(str1 + ":" + paramString);
/*      */     
/* 1114 */     if (this.m_strInventoryGroup == null || this.m_strInventoryGroup.length() <= 0) {
/* 1115 */       setReturnCode(-1);
/* 1116 */       this.m_sbError.append("\nError: Error Withdrawing Features");
/* 1117 */       this.m_sbError.append("\nError: INVENTORYGROUP is empty");
/* 1118 */       return false;
/*      */     } 
/*      */     
/* 1121 */     if (i >= 0) {
/* 1122 */       paramString = paramString.substring(0, i);
/*      */     }
/*      */     
/* 1125 */     stringBuffer.append("map_FEATURECODE=" + paramString + ";");
/*      */     
/* 1127 */     arrayOfEntityItem = this.m_utility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDFEATURE1", "FEATURE", stringBuffer.toString());
/* 1128 */     if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/* 1129 */       setReturnCode(-1);
/* 1130 */       this.m_sbError.append("\nError: Error Withdrawing Features");
/* 1131 */       this.m_sbError.append("\nError: There isn't FEATURE with Feature Code: " + paramString);
/* 1132 */       return false;
/*      */     } 
/*      */     
/* 1135 */     entityList = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen2, arrayOfEntityItem);
/* 1136 */     entityGroup1 = entityList.getParentEntityGroup();
/* 1137 */     if (!entityGroup1.getEntityType().equals("FEATURE")) {
/* 1138 */       setReturnCode(-1);
/* 1139 */       this.m_sbError.append("\nError: Error Withdrawing Features");
/* 1140 */       this.m_sbError.append("\nError: Parent Entity Group is not FEATURE.");
/* 1141 */       return false;
/*      */     } 
/*      */     
/* 1144 */     entityGroup2 = entityList.getEntityGroup("PRODSTRUCT");
/*      */     
/* 1146 */     if (entityGroup2.getEntityItemCount() <= 0) {
/* 1147 */       setReturnCode(-1);
/* 1148 */       this.m_sbError.append("\nError: Error Withdrawing Features");
/* 1149 */       this.m_sbError.append("\nError: No matching Product Strutures are found for feature code " + paramString);
/* 1150 */       return false;
/*      */     } 
/*      */     
/* 1153 */     for (byte b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 1154 */       EntityItem entityItem1 = entityGroup2.getEntityItem(b);
/* 1155 */       boolean bool2 = false;
/* 1156 */       EANList eANList = null;
/* 1157 */       String str3 = null;
/*      */ 
/*      */ 
/*      */       
/* 1161 */       EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/* 1162 */       EntityItem entityItem3 = (EntityItem)entityItem1.getUpLink(0);
/* 1163 */       String str4 = this.m_utility.getAttrValue(entityItem2, "ANNDATE");
/*      */       
/* 1165 */       String str5 = this.m_utility.getAttrValue(entityItem2, "WITHDRAWDATE");
/* 1166 */       if (str4 != null && str4.length() > 0 && dateCompare(str4, this.m_strAnnDate) != 2) {
/*      */         continue;
/*      */       }
/*      */       
/* 1170 */       if (str5 != null && str5.length() > 0 && dateCompare(str5, this.m_strAnnDate) != 1) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1176 */       eANList = getParent(entityList, entityItem2.getEntityType(), entityItem2.getEntityID(), "MACHTYPE", "MACHINETYPEMODELA");
/* 1177 */       if (eANList.size() > 0) {
/* 1178 */         EntityItem entityItem = (EntityItem)eANList.getAt(0);
/* 1179 */         String str = this.m_utility.getAttrValue(entityItem, "INVENTORYGROUP");
/* 1180 */         if (!str.equals(this.m_strInventoryGroup)) {
/* 1181 */           System.out.println("MACHTYPE IG: " + str + " not equals " + this.m_strInventoryGroup);
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*      */       
/* 1188 */       str3 = this.m_utility.getAttrValue(entityItem1, "WITHDRAWDATE");
/* 1189 */       if (str3 != null && str3.length() > 0) {
/* 1190 */         int j = -1;
/*      */ 
/*      */ 
/*      */         
/* 1194 */         String str = this.m_utility.getAttrValue(entityItem3, "WITHDRAWANNDATE_T");
/* 1195 */         if (str != null && str.length() > 0) {
/* 1196 */           int k = dateCompare(str3, str);
/* 1197 */           if (k == 1) {
/* 1198 */             if (!bool1) {
/* 1199 */               this.m_sbError.append("\nError: " + str2);
/*      */             }
/*      */             
/* 1202 */             this.m_sbError.append("\nWarning: Product Structure: " + entityItem1.toString() + " Withdraw Date: " + str3 + " is later than the Feature:" + str);
/* 1203 */             bool1 = true;
/*      */           } 
/*      */         } 
/*      */         
/* 1207 */         str5 = this.m_utility.getAttrValue(entityItem2, "WITHDRAWDATE");
/* 1208 */         if (str5 != null && str5.length() > 0) {
/* 1209 */           int k = dateCompare(str3, str5);
/* 1210 */           if (k == 1) {
/* 1211 */             if (!bool1) {
/* 1212 */               this.m_sbError.append("\nError: " + str2);
/*      */             }
/*      */             
/* 1215 */             this.m_sbError.append("\nWarning: Product Structure: " + entityItem1.toString() + " Withdraw Date: " + str3 + " is later than the Model:" + str5);
/* 1216 */             bool1 = true;
/*      */           } 
/*      */         } 
/*      */         
/* 1220 */         j = dateCompare(str3, this.m_strAnnDate);
/* 1221 */         if (j == 0)
/*      */         {
/* 1223 */           EntityItem[] arrayOfEntityItem1 = { entityItem1 };
/* 1224 */           EntityList entityList1 = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen4, arrayOfEntityItem1);
/* 1225 */           bool2 = autogenAVAIL(entityList1, entityItem1, "OOFAVAIL", true, true, 3, bool);
/* 1226 */           if (bool2) {
/* 1227 */             bool = bool2;
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1236 */         String str = this.m_utility.getAttrValue(entityItem3, "WITHDRAWANNDATE_T");
/* 1237 */         if (str != null && str.length() > 0) {
/*      */           
/* 1239 */           int j = dateCompare(str, this.m_strAnnDate);
/* 1240 */           if (str5 != null && str5.length() > 0 && dateCompare(str, str5) == 1) {
/* 1241 */             j = dateCompare(str5, this.m_strAnnDate);
/*      */           }
/* 1243 */           if (j == 0) {
/* 1244 */             EntityItem[] arrayOfEntityItem1 = { entityItem1 };
/* 1245 */             EntityList entityList1 = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen4, arrayOfEntityItem1);
/* 1246 */             bool2 = autogenAVAIL(entityList1, entityItem1, "OOFAVAIL", true, true, 3, bool);
/* 1247 */             if (bool2) {
/* 1248 */               bool = bool2;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*      */       continue;
/*      */     } 
/* 1256 */     if (!bool) {
/* 1257 */       this.m_sbError.append("\nSuccess: Withdrawing Features.");
/*      */     }
/*      */     
/* 1260 */     return true;
/*      */   }
/*      */   
/*      */   private boolean fConvAnnouncement(String paramString) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 1264 */     String str1 = "CHQGENAVAIL trace fConvAnnouncement method ";
/* 1265 */     boolean bool = false;
/* 1266 */     StringTokenizer stringTokenizer = null;
/* 1267 */     String str2 = null;
/* 1268 */     String str3 = null;
/* 1269 */     String str4 = null;
/* 1270 */     String str5 = null;
/* 1271 */     String str6 = null;
/* 1272 */     String str7 = null;
/* 1273 */     StringBuffer stringBuffer = new StringBuffer();
/* 1274 */     EntityItem[] arrayOfEntityItem = null;
/*      */     
/* 1276 */     int i = paramString.indexOf(" ");
/*      */     
/* 1278 */     System.out.println(str1 + ":" + paramString);
/* 1279 */     if (i >= 0) {
/* 1280 */       paramString = paramString.substring(0, i);
/*      */     }
/*      */     
/* 1283 */     stringTokenizer = new StringTokenizer(paramString, "\\");
/* 1284 */     if (stringTokenizer.countTokens() != 6) {
/* 1285 */       setReturnCode(-1);
/* 1286 */       this.m_sbError.append("\nError: Feature Conversion Announcement");
/* 1287 */       this.m_sbError.append("\nError: Feature Conversion is not in format MMMM\\mmm\\ffff\\NNNN\\nnn\\ssss.");
/* 1288 */       return false;
/*      */     } 
/* 1290 */     str2 = stringTokenizer.nextToken();
/* 1291 */     str3 = stringTokenizer.nextToken();
/* 1292 */     str4 = stringTokenizer.nextToken();
/* 1293 */     str5 = stringTokenizer.nextToken();
/* 1294 */     str6 = stringTokenizer.nextToken();
/* 1295 */     str7 = stringTokenizer.nextToken();
/*      */     
/* 1297 */     if (!allQM(str2)) {
/* 1298 */       stringBuffer.append("map_FROMMACHTYPE=" + str2.replace('?', '_') + ";");
/*      */     }
/* 1300 */     if (!allQM(str3)) {
/* 1301 */       stringBuffer.append("map_FROMMODEL=" + str3.replace('?', '_') + ";");
/*      */     }
/* 1303 */     if (!allQM(str4)) {
/* 1304 */       stringBuffer.append("map_FROMFEATURECODE=" + str4.replace('?', '_') + ";");
/*      */     }
/* 1306 */     if (!allQM(str5)) {
/* 1307 */       stringBuffer.append("map_TOMACHTYPE=" + str5.replace('?', '_') + ";");
/*      */     }
/* 1309 */     if (!allQM(str6)) {
/* 1310 */       stringBuffer.append("map_TOMODEL=" + str6.replace('?', '_') + ";");
/*      */     }
/* 1312 */     if (!allQM(str7)) {
/* 1313 */       stringBuffer.append("map_TOFEATURECODE=" + str7.replace('?', '_') + ";");
/*      */     }
/*      */     
/* 1316 */     arrayOfEntityItem = this.m_utility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDFCTRANSACTION01", "FCTRANSACTION", stringBuffer.toString());
/* 1317 */     if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/* 1318 */       setReturnCode(-1);
/* 1319 */       this.m_sbError.append("\nError: Feature Conversion Announcement");
/* 1320 */       this.m_sbError.append("\nError: There isn't FCTRANSACTION with FROMMACHTYPE " + str2 + ", FROMMODEL " + str3 + ", FROMFEATURECODE " + str4 + ", TOMACHTYPE " + str5 + ", TOMODEL " + str6 + ", TOFEATURECODE " + str7);
/* 1321 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1326 */     for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1327 */       EntityItem entityItem = arrayOfEntityItem[b];
/* 1328 */       String str = this.m_utility.getAttrValue(entityItem, "ANNDATE");
/* 1329 */       boolean bool1 = false;
/* 1330 */       if (str != null && str.length() > 0 && dateCompare(str, this.m_strAnnDate) == 2) {
/* 1331 */         String str8 = this.m_utility.getAttrValue(entityItem, "WITHDRAWDATE");
/* 1332 */         if (str8 != null && str8.length() > 0 && dateCompare(str8, this.m_strAnnDate) == 0) {
/* 1333 */           EntityItem[] arrayOfEntityItem1 = { entityItem };
/* 1334 */           EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen3, arrayOfEntityItem1);
/*      */           
/* 1336 */           bool1 = autogenAVAIL(entityList, entityItem, "FEATURETRNAVAIL", true, true, 4, bool);
/* 1337 */           if (bool1) {
/* 1338 */             bool = bool1;
/*      */           }
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/* 1344 */       bool1 = announceFCONV(entityItem);
/* 1345 */       if (bool1) {
/* 1346 */         bool = bool1;
/*      */       }
/*      */       continue;
/*      */     } 
/* 1350 */     if (!bool) {
/* 1351 */       this.m_sbError.append("\nSuccess: Feature Conversion Announcement.");
/*      */     }
/*      */     
/* 1354 */     return true;
/*      */   }
/*      */   
/*      */   private boolean announceFCONV(EntityItem paramEntityItem) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 1358 */     String str1 = "CHQGENAVAIL trace announceFCONV method ";
/* 1359 */     boolean bool = false;
/* 1360 */     String str2 = null;
/* 1361 */     String str3 = null;
/* 1362 */     String str4 = null;
/* 1363 */     String str5 = null;
/* 1364 */     String str6 = null;
/* 1365 */     String str7 = null;
/* 1366 */     String str8 = null;
/* 1367 */     StringBuffer stringBuffer = new StringBuffer();
/* 1368 */     EntityList entityList1 = null;
/* 1369 */     EntityList entityList2 = null;
/* 1370 */     EntityItem entityItem1 = null;
/* 1371 */     boolean bool1 = false;
/* 1372 */     EntityGroup entityGroup1 = null;
/* 1373 */     EntityItem entityItem2 = null;
/* 1374 */     boolean bool2 = false;
/* 1375 */     EntityGroup entityGroup2 = null;
/*      */     
/* 1377 */     if (paramEntityItem == null || !paramEntityItem.getEntityType().equals("FCTRANSACTION")) {
/* 1378 */       return false;
/*      */     }
/*      */     
/* 1381 */     System.out.println(str1 + " ei " + paramEntityItem.getKey());
/* 1382 */     str2 = this.m_utility.getAttrValue(paramEntityItem, "ANNDATE");
/* 1383 */     if (str2 == null || str2.length() <= 0 || dateCompare(str2, this.m_strAnnDate) != 0) {
/* 1384 */       return false;
/*      */     }
/*      */     
/* 1387 */     str3 = this.m_utility.getAttrValue(paramEntityItem, "FROMMACHTYPE");
/* 1388 */     str4 = this.m_utility.getAttrValue(paramEntityItem, "FROMMODEL");
/* 1389 */     str5 = this.m_utility.getAttrValue(paramEntityItem, "FROMFEATURECODE");
/* 1390 */     str6 = this.m_utility.getAttrValue(paramEntityItem, "TOMACHTYPE");
/* 1391 */     str7 = this.m_utility.getAttrValue(paramEntityItem, "TOMODEL");
/* 1392 */     str8 = this.m_utility.getAttrValue(paramEntityItem, "TOFEATURECODE");
/*      */ 
/*      */ 
/*      */     
/* 1396 */     stringBuffer.append("map_MODEL:MACHTYPEATR=" + str3 + ";");
/* 1397 */     stringBuffer.append("map_MODEL:MODELATR=" + str4 + ";");
/* 1398 */     stringBuffer.append("map_FEATURE:FEATURECODE=" + str5 + ";");
/*      */     
/*      */     try {
/* 1401 */       entityList1 = this.m_utility.dynaSearchIIForEntityList(this.m_db, this.m_prof, this.m_ei, "SRDPRODSTRUCT03", "PRODSTRUCT", stringBuffer.toString());
/* 1402 */     } catch (SBRException sBRException) {
/* 1403 */       System.out.println(sBRException.toString());
/* 1404 */       entityList1 = null;
/*      */     } 
/*      */     
/* 1407 */     this.m_db.test((entityList1 != null), "EntityList for search FROM PRODSTRUCT is null");
/*      */     
/* 1409 */     if (entityList1.getEntityGroup("PRODSTRUCT") == null || entityList1.getEntityGroup("PRODSTRUCT").getEntityItemCount() <= 0) {
/* 1410 */       setReturnCode(-1);
/* 1411 */       this.m_sbError.append("\nError: New Feature Conversion Announcement.");
/* 1412 */       this.m_sbError.append("\nError: No PRODSTRUCT exist for MACHTYPE " + str3 + ", MODEL " + str4 + ", and FEATURECODE " + str5);
/* 1413 */       return false;
/*      */     } 
/*      */     
/* 1416 */     stringBuffer = new StringBuffer();
/* 1417 */     stringBuffer.append("map_MODEL:MACHTYPEATR=" + str6 + ";");
/* 1418 */     stringBuffer.append("map_MODEL:MODELATR=" + str7 + ";");
/* 1419 */     stringBuffer.append("map_FEATURE:FEATURECODE=" + str8 + ";");
/*      */     
/*      */     try {
/* 1422 */       entityList2 = this.m_utility.dynaSearchIIForEntityList(this.m_db, this.m_prof, this.m_ei, "SRDPRODSTRUCT03", "PRODSTRUCT", stringBuffer.toString());
/* 1423 */     } catch (SBRException sBRException) {
/* 1424 */       System.out.println(sBRException.toString());
/* 1425 */       entityList2 = null;
/*      */     } 
/*      */     
/* 1428 */     this.m_db.test((entityList2 != null), "EntityList for search TO PRODSTRUCT is null");
/* 1429 */     if (entityList2.getEntityGroup("PRODSTRUCT") == null || entityList2.getEntityGroup("PRODSTRUCT").getEntityItemCount() <= 0) {
/* 1430 */       setReturnCode(-1);
/* 1431 */       this.m_sbError.append("\nError: New Feature Conversion Announcement.");
/* 1432 */       this.m_sbError.append("\nError: No PRODSTRUCT exist for MACHTYPE " + str6 + ", MODEL " + str7 + ", and FEATURECODE " + str8);
/* 1433 */       return false;
/*      */     } 
/*      */     
/* 1436 */     entityGroup1 = entityList1.getEntityGroup("PRODSTRUCT"); byte b;
/* 1437 */     for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 1438 */       EntityItem entityItem3 = null;
/* 1439 */       EntityItem entityItem4 = null;
/* 1440 */       String str9 = null;
/* 1441 */       String str10 = null;
/* 1442 */       String str11 = null;
/*      */       
/* 1444 */       entityItem1 = entityGroup1.getEntityItem(b);
/* 1445 */       entityItem3 = (EntityItem)entityItem1.getUpLink(0);
/* 1446 */       entityItem4 = (EntityItem)entityItem1.getDownLink(0);
/*      */       
/* 1448 */       str9 = this.m_utility.getAttrValue(entityItem1, "ANNDATE");
/* 1449 */       str10 = this.m_utility.getAttrValue(entityItem3, "FIRSTANNDATE");
/* 1450 */       str11 = this.m_utility.getAttrValue(entityItem4, "ANNDATE");
/* 1451 */       if (str9 != null && str9.length() > 0) {
/* 1452 */         if (str10 != null && str10.length() > 0 && str11 != null && str11.length() > 0 && (
/* 1453 */           dateCompare(str9, str11) == 2 || dateCompare(str9, str10) == 2)) {
/* 1454 */           this.m_sbError.append("\nError: New Feature Conversion Announcement.");
/* 1455 */           this.m_sbError.append("\nWarning: Product Structure: " + entityItem1.toString() + " Announce Date: " + str9 + " is earlier than the Feature:" + str10 + " and Model: " + str11);
/* 1456 */           bool = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/* 1461 */         if (dateCompare(str9, this.m_strAnnDate) != 1) {
/* 1462 */           bool1 = true;
/*      */           
/*      */           break;
/*      */         } 
/* 1466 */       } else if (str10 != null && str10.length() > 0 && str11 != null && str11.length() > 0) {
/* 1467 */         int i = dateCompare(str11, str10);
/* 1468 */         int j = dateCompare(str10, this.m_strAnnDate);
/* 1469 */         if (i == 1) {
/* 1470 */           j = dateCompare(str11, this.m_strAnnDate);
/*      */         }
/*      */         
/* 1473 */         if (j != 1) {
/* 1474 */           bool1 = true;
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1482 */     entityGroup2 = entityList2.getEntityGroup("PRODSTRUCT");
/* 1483 */     for (b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 1484 */       EntityItem entityItem3 = null;
/* 1485 */       EntityItem entityItem4 = null;
/* 1486 */       String str9 = null;
/* 1487 */       String str10 = null;
/* 1488 */       String str11 = null;
/*      */       
/* 1490 */       entityItem2 = entityGroup2.getEntityItem(b);
/* 1491 */       entityItem3 = (EntityItem)entityItem2.getUpLink(0);
/* 1492 */       entityItem4 = (EntityItem)entityItem2.getDownLink(0);
/*      */       
/* 1494 */       str9 = this.m_utility.getAttrValue(entityItem2, "ANNDATE");
/* 1495 */       str10 = this.m_utility.getAttrValue(entityItem3, "FIRSTANNDATE");
/* 1496 */       str11 = this.m_utility.getAttrValue(entityItem4, "ANNDATE");
/* 1497 */       if (str9 != null && str9.length() > 0) {
/* 1498 */         if (str10 != null && str10.length() > 0 && str11 != null && str11.length() > 0 && (
/* 1499 */           dateCompare(str9, str11) == 2 || dateCompare(str9, str10) == 2)) {
/* 1500 */           this.m_sbError.append("\nError: New Feature Conversion Announcement.");
/* 1501 */           this.m_sbError.append("\nWarning: Product Structure: " + entityItem2.toString() + " Announce Date: " + str9 + " is earlier than the Feature:" + str10 + " and Model: " + str11);
/* 1502 */           bool = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/* 1507 */         if (dateCompare(str9, this.m_strAnnDate) != 1) {
/* 1508 */           bool2 = true;
/*      */           
/*      */           break;
/*      */         } 
/* 1512 */       } else if (str10 != null && str10.length() > 0 && str11 != null && str11.length() > 0) {
/* 1513 */         int i = dateCompare(str11, str10);
/* 1514 */         int j = dateCompare(str10, this.m_strAnnDate);
/* 1515 */         if (i == 1) {
/* 1516 */           j = dateCompare(str11, this.m_strAnnDate);
/*      */         }
/*      */         
/* 1519 */         if (j != 1) {
/* 1520 */           bool2 = true;
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1528 */     if (bool1 && bool2) {
/* 1529 */       EntityItem[] arrayOfEntityItem = { paramEntityItem };
/* 1530 */       EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen3, arrayOfEntityItem);
/* 1531 */       bool = autogenAVAIL(entityList, paramEntityItem, "FEATURETRNAVAIL", true, false, 4, bool);
/*      */     } 
/* 1533 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getStrAttributes(OPICMList paramOPICMList) {
/* 1570 */     StringBuffer stringBuffer = new StringBuffer();
/* 1571 */     for (byte b = 0; b < paramOPICMList.size(); b++) {
/* 1572 */       stringBuffer.append((b == 0) ? "" : ";");
/* 1573 */       stringBuffer.append((String)paramOPICMList.getAt(b));
/*      */     } 
/* 1575 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private void availHandling() throws SQLException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
/* 1580 */     EntityItem[] arrayOfEntityItem = null;
/*      */     
/* 1582 */     StringBuffer stringBuffer = new StringBuffer();
/* 1583 */     stringBuffer.append("map_ANNCODENAME=" + this.m_utility.getAttrValue(this.m_ei, "ANNCODENAME"));
/* 1584 */     arrayOfEntityItem = this.m_utility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDAVAIL10", "AVAIL", stringBuffer.toString());
/*      */     
/* 1586 */     for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1587 */       EANList eANList = new EANList();
/* 1588 */       boolean bool = false;
/*      */       
/* 1590 */       EntityItem entityItem = arrayOfEntityItem[b];
/* 1591 */       EntityItem[] arrayOfEntityItem1 = { entityItem };
/* 1592 */       EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, this.m_xaiAutoGen5, arrayOfEntityItem1);
/* 1593 */       EntityGroup entityGroup = entityList.getParentEntityGroup();
/* 1594 */       entityItem = entityGroup.getEntityItem(entityItem.getKey());
/*      */       byte b1;
/* 1596 */       for (b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 1597 */         EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b1);
/* 1598 */         if (!entityItem1.getEntityType().equals("COFOOFAVAIL")) {
/* 1599 */           eANList.put((EANObject)entityItem1);
/*      */         } else {
/* 1601 */           bool = true;
/*      */         } 
/*      */       } 
/* 1604 */       if (bool && eANList.size() > 0) {
/* 1605 */         for (b1 = 0; b1 < eANList.size(); b1++) {
/* 1606 */           EntityItem entityItem1 = (EntityItem)eANList.getAt(b1);
/* 1607 */           System.out.println("remove relator for avail : " + entityItem1.toString());
/* 1608 */           EANUtility.deactivateEntity(this.m_db, this.m_prof, entityItem1);
/*      */         } 
/*      */       } else {
/* 1611 */         System.out.println("remove avail : " + entityItem.toString());
/* 1612 */         EANUtility.deactivateEntity(this.m_db, this.m_prof, entityItem);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean autogenAVAIL(EntityList paramEntityList, EntityItem paramEntityItem, String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt, boolean paramBoolean3) throws SQLException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
/* 1618 */     String str = "CHQGENAVAIL trace autogenAVAIL method ";
/*      */     
/* 1620 */     boolean bool1 = false;
/* 1621 */     boolean bool2 = false;
/* 1622 */     EANList eANList = null;
/* 1623 */     OPICMList oPICMList = null;
/*      */     
/* 1625 */     System.out.println(str + ":" + paramString + ":" + paramBoolean2);
/*      */ 
/*      */     
/* 1628 */     if (paramEntityItem == null) {
/* 1629 */       System.out.println(str + " _ei is null");
/* 1630 */       return false;
/*      */     } 
/*      */     
/* 1633 */     eANList = getChildren(paramEntityList, paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), "AVAIL", paramString);
/* 1634 */     oPICMList = this.m_availAtts;
/* 1635 */     if (paramBoolean2) {
/* 1636 */       oPICMList = this.m_availWDAtts;
/*      */     }
/*      */     
/* 1639 */     if (eANList != null && eANList.size() > 0) {
/* 1640 */       EntityItem entityItem = this.m_utility.findEntityItem(eANList, "AVAIL", getStrAttributes(oPICMList), true);
/*      */       
/* 1642 */       if (entityItem != null) {
/* 1643 */         OPICMList oPICMList1 = new OPICMList();
/* 1644 */         bool2 = true;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1649 */         for (byte b = 0; b < this.m_updateAvailAttrs.length; b++) {
/*      */           
/* 1651 */           String str1 = this.m_updateAvailAttrs[b];
/* 1652 */           String str2 = this.m_utility.getAttrValue(entityItem, str1);
/* 1653 */           String str3 = "";
/*      */           
/* 1655 */           if (paramString.equals("OOFAVAIL") && (str1.equals("GENAREASELECTION") || str1.equals("COUNTRYLIST"))) {
/*      */ 
/*      */             
/* 1658 */             EntityItem entityItem1 = (EntityItem)paramEntityItem.getUpLink(0);
/* 1659 */             str3 = this.m_utility.getAttrValue(entityItem1, str1);
/*      */           } else {
/* 1661 */             int i = -1;
/* 1662 */             String str4 = (String)oPICMList.get("AVAIL:" + str1);
/*      */             
/* 1664 */             if (str4 != null) {
/* 1665 */               str3 = str4;
/*      */             }
/*      */             
/* 1668 */             i = str3.indexOf("=");
/* 1669 */             if (i >= 0) {
/* 1670 */               str3 = str3.substring(i + 1);
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 1675 */           if (str2 == null || !str2.equals(str3)) {
/*      */             
/* 1677 */             this.m_sbError.append("\nWarning: Availability " + entityItem.toString() + " is overridden by the Profile. " + str1 + ", old value: " + str2 + ", new value: " + str3);
/* 1678 */             oPICMList1.put("AVAIL:" + str1, str1 + "=" + str3);
/*      */           } 
/*      */         } 
/*      */         
/* 1682 */         if (oPICMList1.size() > 0) {
/* 1683 */           this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1688 */     if (!bool2) {
/* 1689 */       EntityItem entityItem = null;
/*      */       
/* 1691 */       if (paramString.equals("OOFAVAIL")) {
/*      */ 
/*      */         
/* 1694 */         EntityItem entityItem1 = (EntityItem)paramEntityItem.getUpLink(0);
/* 1695 */         String str1 = this.m_utility.getAttrValue(entityItem1, "GENAREASELECTION");
/* 1696 */         String str2 = this.m_utility.getAttrValue(entityItem1, "COUNTRYLIST");
/* 1697 */         oPICMList.remove("AVAIL:GENAREASELECTION");
/* 1698 */         oPICMList.put("AVAIL:GENAREASELECTION", "map_GENAREASELECTION=" + str1);
/* 1699 */         oPICMList.remove("AVAIL:COUNTRYLIST");
/* 1700 */         oPICMList.put("AVAIL:COUNTRYLIST", "map_COUNTRYLIST=" + str2);
/*      */       } else {
/* 1702 */         oPICMList.remove("AVAIL:GENAREASELECTION");
/* 1703 */         oPICMList.put("AVAIL:GENAREASELECTION", "map_GENAREASELECTION=" + this.m_utility.getAttrValue(this.m_ei, "GENAREASELECTION"));
/* 1704 */         oPICMList.remove("AVAIL:COUNTRYLIST");
/* 1705 */         oPICMList.put("AVAIL:COUNTRYLIST", "map_COUNTRYLIST=" + this.m_utility.getAttrValue(this.m_ei, "COUNTRYLIST"));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1712 */       entityItem = this.m_utility.findEntityItem(this.m_newAvailList, "AVAIL", getStrAttributes(oPICMList), true);
/* 1713 */       if (entityItem == null) {
/* 1714 */         entityItem = this.m_utility.createEntity(this.m_db, this.m_prof, "AVAIL", oPICMList);
/* 1715 */         this.m_prof = this.m_utility.setProfValOnEffOn(this.m_db, this.m_prof);
/* 1716 */         entityItem = new EntityItem(this.m_egAVAIL, this.m_prof, this.m_db, entityItem.getEntityType(), entityItem.getEntityID());
/*      */         
/* 1718 */         this.m_newAvailList.put((EANObject)entityItem);
/*      */       } 
/* 1720 */       EntityItem[] arrayOfEntityItem = { entityItem };
/* 1721 */       this.m_utility.linkEntities(this.m_db, this.m_prof, paramEntityItem, arrayOfEntityItem, paramString);
/*      */     } 
/* 1723 */     return bool1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 1752 */     String str = "";
/* 1753 */     int i = paramString1.indexOf(paramString2);
/*      */     
/* 1755 */     while (paramString1.length() > 0 && i >= 0) {
/* 1756 */       str = str + paramString1.substring(0, i) + paramString3;
/* 1757 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 1758 */       i = paramString1.indexOf(paramString2);
/*      */     } 
/* 1760 */     str = str + paramString1;
/* 1761 */     return str;
/*      */   }
/*      */   
/*      */   private boolean allQM(String paramString) {
/* 1765 */     if (paramString == null || paramString.length() <= 0) {
/* 1766 */       return false;
/*      */     }
/*      */     
/* 1769 */     for (byte b = 0; b < paramString.length(); b++) {
/* 1770 */       char c = paramString.charAt(b);
/* 1771 */       if (c != '?') {
/* 1772 */         return false;
/*      */       }
/*      */     } 
/* 1775 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 1785 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1794 */     return "Autogen AVAIL ABR.";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getStyle() {
/* 1805 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/* 1814 */     return new String("1.54");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/* 1823 */     return "CHQGENAVAIL.java,v 1.54 2008/01/30 19:39:17 wendy Exp";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1831 */     return "CHQGENAVAIL.java";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CHQGENAVAIL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */