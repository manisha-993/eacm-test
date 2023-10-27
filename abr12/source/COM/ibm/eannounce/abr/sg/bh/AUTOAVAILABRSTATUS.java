/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.LinkActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.eannounce.objects.WorkflowException;
/*      */ import COM.ibm.opicmpdh.middleware.LockException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
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
/*      */ public class AUTOAVAILABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*  113 */   private StringBuffer rptSb = new StringBuffer();
/*  114 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  115 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  116 */   private Object[] args = (Object[])new String[10];
/*      */   
/*  118 */   private ResourceBundle rsBundle = null;
/*  119 */   private Hashtable metaTbl = new Hashtable<>();
/*  120 */   private String navName = "";
/*      */ 
/*      */   
/*  123 */   private EntityItem genAvail = null;
/*      */   
/*      */   private static final String MODEL_SRCHACTION_NAME = "SRDMODEL16";
/*      */   
/*      */   private static final String MODEL_AVAIL_CREATEACTION_NAME = "CRPEERAVAIL";
/*      */   
/*      */   private static final String PS_AVAIL_CREATEACTION_NAME = "CRPEERAVAIL2";
/*      */   
/*      */   private static final String OOFAVAIL_LINKACTION_NAME = "LINKAVAILPRODSTRUCT2";
/*      */   private static final String MODELAVAIL_LINKACTION_NAME = "LINKAVAILMODEL";
/*  133 */   private static final String[] AALIST_ATTR = new String[] { "COMNAME", "AVAILTYPE", "EFFECTIVEDATE", "ORDERSYSNAME", "PDHDOMAIN", "GENAREASELECTION", "COUNTRYLIST" };
/*      */ 
/*      */   
/*      */   private static final String HARDWARE = "100";
/*      */ 
/*      */   
/*      */   private static final String BASE = "150";
/*      */ 
/*      */   
/*      */   private static final String ORDERCODE_INITIAL = "5957";
/*      */ 
/*      */   
/*      */   private static final String PLANNEDAVAIL = "146";
/*      */ 
/*      */   
/*      */   private static final String LASTORDERAVAIL = "149";
/*      */ 
/*      */   
/*      */   private static final String RPQ_ILISTED = "120";
/*      */   
/*      */   private static final String RPQ_PLISTED = "130";
/*      */   
/*      */   private static final String RPQ_RLISTED = "0140";
/*      */   
/*      */   private static final String PLACEHOLDER = "402";
/*      */   
/*  159 */   private static final Set FCTYPE_SET = new HashSet(); static {
/*  160 */     FCTYPE_SET.add("120");
/*  161 */     FCTYPE_SET.add("130");
/*  162 */     FCTYPE_SET.add("0140");
/*  163 */     FCTYPE_SET.add("402");
/*      */   }
/*  165 */   private static final Hashtable NDN_TBL = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  176 */     NDN nDN1 = new NDN("MODEL", "(TM)");
/*  177 */     nDN1.addAttr("MACHTYPEATR");
/*  178 */     nDN1.addAttr("MODELATR");
/*  179 */     nDN1.addAttr("COFCAT");
/*  180 */     nDN1.addAttr("COFSUBCAT");
/*  181 */     nDN1.addAttr("COFGRP");
/*  182 */     nDN1.addAttr("COFSUBGRP");
/*  183 */     NDN nDN2 = new NDN("FEATURE", "(FC)");
/*  184 */     nDN2.addAttr("FEATURECODE");
/*  185 */     nDN1.setNext(nDN2);
/*  186 */     NDN_TBL.put("PRODSTRUCT", nDN1);
/*      */   }
/*      */   
/*  189 */   private EntityGroup modelEG = null;
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
/*      */   public void execute_run() {
/*  211 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  213 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
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
/*  224 */     String str3 = "";
/*  225 */     String str4 = "";
/*      */     
/*  227 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */     
/*      */     try {
/*  231 */       long l = System.currentTimeMillis();
/*  232 */       start_ABRBuild();
/*      */ 
/*      */       
/*  235 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  237 */       EntityItem entityItem1 = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  239 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem1.getKey() + " extract: " + this.m_abri
/*  240 */           .getVEName() + " using DTS: " + this.m_prof.getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */       
/*  242 */       addDebug("Time to pull root VE: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */ 
/*      */       
/*  245 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  250 */       this.navName = getNavigationName(entityItem1);
/*  251 */       EntityItem entityItem2 = this.m_elist.getEntityGroup("ANNOUNCEMENT").getEntityItem(0);
/*      */       
/*  253 */       str3 = this.m_elist.getParentEntityGroup().getLongDescription() + " &quot;" + this.navName + "&quot; <br />for " + getLD_NDN(entityItem2);
/*      */ 
/*      */       
/*  256 */       Vector<EntityItem> vector = validateSetup(entityItem1, entityItem2);
/*  257 */       if (getReturnCode() == 0) {
/*  258 */         String str = PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE");
/*      */         
/*  260 */         for (byte b = 0; b < vector.size(); b++) {
/*      */ 
/*      */ 
/*      */           
/*  264 */           long l1 = System.currentTimeMillis();
/*      */           
/*  266 */           EntityItem entityItem = vector.elementAt(b);
/*  267 */           EntityList entityList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/*  268 */                 .getProfile(), "EXAAMODEL"), new EntityItem[] { entityItem });
/*      */ 
/*      */           
/*  271 */           addDebug(" Extract EXAAMODEL " + NEWLINE + PokUtils.outputList(entityList));
/*      */           
/*  273 */           if ("146".equals(str)) {
/*  274 */             createPlannedAvails(entityItem1, entityList, entityItem2);
/*      */           } else {
/*  276 */             createLastOrderAvails(entityItem1, entityList, entityItem2);
/*      */           } 
/*  278 */           addDebug("Time for " + entityItem.getKey() + ": " + Stopwatch.format(System.currentTimeMillis() - l1));
/*      */           
/*  280 */           entityList.dereference();
/*      */         } 
/*  282 */         vector.clear();
/*      */       } 
/*      */       
/*  285 */       addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     }
/*  287 */     catch (Throwable throwable) {
/*  288 */       StringWriter stringWriter = new StringWriter();
/*  289 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  290 */       String str7 = "<pre>{0}</pre>";
/*  291 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  292 */       setReturnCode(-3);
/*  293 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  295 */       this.args[0] = throwable.getMessage();
/*  296 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  297 */       messageFormat1 = new MessageFormat(str7);
/*  298 */       this.args[0] = stringWriter.getBuffer().toString();
/*  299 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  300 */       logError("Exception: " + throwable.getMessage());
/*  301 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  305 */       setDGTitle(this.navName);
/*  306 */       setDGRptName(getShortClassName(getClass()));
/*  307 */       setDGRptClass(getABRCode());
/*      */       
/*  309 */       if (!isReadOnly())
/*      */       {
/*  311 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  317 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  318 */     this.args[0] = getDescription();
/*  319 */     this.args[1] = this.navName;
/*  320 */     String str5 = messageFormat.format(this.args);
/*  321 */     messageFormat = new MessageFormat(str2);
/*  322 */     this.args[0] = this.m_prof.getOPName();
/*  323 */     this.args[1] = this.m_prof.getRoleDescription();
/*  324 */     this.args[2] = this.m_prof.getWGName();
/*  325 */     this.args[3] = getNow();
/*  326 */     this.args[4] = str3;
/*  327 */     this.args[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  328 */     this.args[6] = str4 + " " + getABRVersion();
/*      */     
/*  330 */     this.rptSb.insert(0, str5 + messageFormat.format(this.args) + NEWLINE);
/*      */     
/*  332 */     println(this.rptSb.toString());
/*  333 */     printDGSubmitString();
/*  334 */     println(EACustom.getTOUDiv());
/*  335 */     buildReportFooter();
/*      */     
/*  337 */     this.metaTbl.clear();
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
/*      */ 
/*      */   
/*      */   private void createPlannedAvails(EntityItem paramEntityItem1, EntityList paramEntityList, EntityItem paramEntityItem2) throws SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/*  376 */     LinkActionItem linkActionItem1 = new LinkActionItem(null, this.m_db, this.m_prof, "LINKAVAILPRODSTRUCT2");
/*  377 */     LinkActionItem linkActionItem2 = new LinkActionItem(null, this.m_db, this.m_prof, "LINKAVAILMODEL");
/*  378 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem2, "ANNCODENAME");
/*  379 */     String str2 = PokUtils.getAttributeValue(paramEntityItem2, "ANNDATE", "", "", false);
/*  380 */     ArrayList<?> arrayList = getCountriesAsList(paramEntityItem1);
/*      */     
/*  382 */     addDebug("createPlannedAvails " + paramEntityItem2.getKey() + " ANNCODENAME: " + str1 + " ANNDATE: " + str2);
/*  383 */     AttrSet attrSet = new AttrSet(paramEntityItem1, str1);
/*      */     
/*  385 */     EntityGroup entityGroup1 = paramEntityList.getParentEntityGroup();
/*      */     
/*  387 */     for (byte b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++) {
/*  388 */       EntityItem entityItem = entityGroup1.getEntityItem(b1);
/*  389 */       createOrRefAvail(entityItem, "CRPEERAVAIL", attrSet, linkActionItem2);
/*      */     } 
/*      */     
/*  392 */     EntityGroup entityGroup2 = paramEntityList.getEntityGroup("PRODSTRUCT");
/*  393 */     for (byte b2 = 0; b2 < entityGroup2.getEntityItemCount(); b2++) {
/*  394 */       EntityItem entityItem = entityGroup2.getEntityItem(b2);
/*  395 */       if (checkPsEligibility(true, entityItem, str2)) {
/*  396 */         EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(0);
/*  397 */         ArrayList arrayList1 = getCountriesAsList(entityItem1);
/*  398 */         if (arrayList1.containsAll(arrayList)) {
/*  399 */           addDebug("createPlannedAvails " + entityItem1.getKey() + " countries contains all autoavail ctrys");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  406 */           createOrRefAvail(entityItem, "CRPEERAVAIL2", attrSet, linkActionItem1);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  411 */           addDebug("createPlannedAvails skipping " + entityItem.getKey() + " " + entityItem1.getKey() + " ctrylist " + arrayList1 + " does not contain all of autoavail " + arrayList);
/*      */           
/*  413 */           ArrayList arrayList2 = getCountryDescAsList(paramEntityItem1);
/*  414 */           ArrayList<?> arrayList3 = getCountryDescAsList(entityItem1);
/*  415 */           arrayList2.removeAll(arrayList3);
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
/*  426 */           this.args[0] = getLD_NDN(entityItem);
/*  427 */           this.args[1] = getLD_NDN(entityItem1);
/*  428 */           this.args[2] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*  429 */           this.args[3] = paramEntityItem1.getEntityGroup().getLongDescription();
/*  430 */           this.args[4] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*  431 */           this.args[5] = arrayList2.toString();
/*  432 */           addOutput(getResourceMsg("SKIPPED_FCCTRY_MSG2", this.args));
/*      */           
/*  434 */           arrayList3.clear();
/*  435 */           arrayList2.clear();
/*      */         } 
/*  437 */         arrayList1.clear();
/*      */       } else {
/*  439 */         addDebug("createPlannedAvails skipping ineligible " + entityItem.getKey());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  444 */     arrayList.clear();
/*  445 */     attrSet.dereference();
/*  446 */     attrSet = null;
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
/*      */   private void createOrRefAvail(EntityItem paramEntityItem, String paramString, AttrSet paramAttrSet, LinkActionItem paramLinkActionItem) throws MiddlewareRequestException, RemoteException, SQLException, MiddlewareException, EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/*  477 */     if (this.genAvail == null) {
/*  478 */       addDebug("createOrRefAvail: No previous avail created");
/*  479 */       long l = System.currentTimeMillis();
/*  480 */       StringBuffer stringBuffer = new StringBuffer();
/*  481 */       this.genAvail = ABRUtil.createEntity(this.m_db, this.m_prof, paramString, paramEntityItem, "AVAIL", paramAttrSet
/*  482 */           .getAttrCodes(), paramAttrSet.getAttrValues(), stringBuffer);
/*  483 */       if (stringBuffer.length() > 0) {
/*  484 */         addDebug(stringBuffer.toString());
/*      */       }
/*  486 */       if (this.genAvail != null) {
/*  487 */         addDebug("createOrRefAvail: Time to create " + this.genAvail.getKey() + " with parent " + paramEntityItem
/*  488 */             .getKey() + " " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */ 
/*      */         
/*  491 */         this.args[0] = getLD_NDN(this.genAvail);
/*  492 */         this.args[1] = getLD_NDN(paramEntityItem);
/*  493 */         addOutput(getResourceMsg("CREATED_MSG", this.args));
/*      */       } else {
/*      */         
/*  496 */         this.args[0] = paramEntityItem.getEntityGroup().getEntityList().getEntityGroup("AVAIL").getLongDescription();
/*  497 */         addError("CREATE_ERR", this.args);
/*      */       } 
/*      */     } else {
/*  500 */       addDebug("createOrRefAvail: referencing previous " + this.genAvail.getKey());
/*      */       
/*  502 */       long l = System.currentTimeMillis();
/*  503 */       EntityItem[] arrayOfEntityItem1 = { paramEntityItem };
/*  504 */       EntityItem[] arrayOfEntityItem2 = { this.genAvail };
/*      */ 
/*      */       
/*  507 */       paramLinkActionItem.setParentEntityItems(arrayOfEntityItem1);
/*  508 */       paramLinkActionItem.setChildEntityItems(arrayOfEntityItem2);
/*  509 */       this.m_db.executeAction(this.m_prof, paramLinkActionItem);
/*      */       
/*  511 */       addDebug("createOrRefAvail: Time to link " + paramEntityItem.getKey() + " to " + this.genAvail.getKey() + ": " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */ 
/*      */       
/*  514 */       this.args[0] = getLD_NDN(this.genAvail);
/*  515 */       this.args[1] = getLD_NDN(paramEntityItem);
/*  516 */       addOutput(getResourceMsg("REFFED_MSG", this.args));
/*      */     } 
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
/*      */   private boolean checkPsEligibility(boolean paramBoolean, EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/*  547 */     boolean bool = false;
/*  548 */     String str1 = "ANNDATE";
/*  549 */     if (!paramBoolean) {
/*  550 */       str1 = "WTHDRWEFFCTVDATE";
/*      */     }
/*      */ 
/*      */     
/*  554 */     EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(0);
/*      */     
/*  556 */     if (PokUtils.contains(entityItem, "FCTYPE", FCTYPE_SET)) {
/*  557 */       addDebug("checkPsEligibility " + paramEntityItem.getKey() + " ineligible " + entityItem.getKey() + " FCTYPE:" + 
/*  558 */           PokUtils.getAttributeFlagValue(entityItem, "FCTYPE"));
/*      */       
/*  560 */       this.args[0] = getLD_NDN(paramEntityItem);
/*  561 */       this.args[1] = getLD_NDN(entityItem) + " has " + getLD_Value(entityItem, "FCTYPE");
/*  562 */       addOutput(getResourceMsg("SKIPPED_MSG", this.args));
/*  563 */       return bool;
/*      */     } 
/*      */     
/*  566 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, str1, "", null, false);
/*  567 */     String str3 = PokUtils.getAttributeFlagValue(paramEntityItem, "ORDERCODE");
/*      */     
/*  569 */     addDebug("checkPsEligibility " + paramEntityItem.getKey() + " " + str1 + ": " + str2 + " ordercode " + str3);
/*  570 */     if (str2 == null) {
/*  571 */       if (paramBoolean) {
/*      */         
/*  573 */         str1 = "FIRSTANNDATE";
/*  574 */         str2 = PokUtils.getAttributeValue(entityItem, str1, "", this.m_strForever, false);
/*  575 */         bool = (str2.compareTo(paramString) <= 0);
/*  576 */         if (!bool) {
/*  577 */           addDebug("checkPsEligibility " + entityItem.getKey() + " " + str1 + ": " + str2);
/*      */           
/*  579 */           this.args[0] = getLD_NDN(paramEntityItem);
/*  580 */           this.args[1] = getLD_NDN(entityItem);
/*  581 */           this.args[2] = getLD_Value(entityItem, str1);
/*  582 */           this.args[3] = paramString;
/*  583 */           addOutput(getResourceMsg("SKIPPED_DATE_MSG2", this.args));
/*      */         }
/*      */       
/*      */       }
/*  587 */       else if (!"5957".equals(str3)) {
/*  588 */         addDebug("checkPsEligibility " + paramEntityItem.getKey() + " ineligible lastorderavail ordercode " + str3);
/*      */         
/*  590 */         this.args[0] = getLD_NDN(paramEntityItem);
/*  591 */         this.args[1] = getLD_Value(paramEntityItem, "ORDERCODE");
/*  592 */         addOutput(getResourceMsg("SKIPPED_MSG", this.args));
/*      */       } else {
/*  594 */         bool = true;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  600 */       bool = str2.equals(paramString);
/*  601 */       if (!bool) {
/*      */         
/*  603 */         this.args[0] = getLD_NDN(paramEntityItem);
/*  604 */         this.args[1] = getLD_Value(paramEntityItem, str1);
/*  605 */         this.args[2] = paramString;
/*  606 */         addOutput(getResourceMsg("SKIPPED_DATE_MSG", this.args));
/*      */       } 
/*      */     } 
/*      */     
/*  610 */     return bool;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createLastOrderAvails(EntityItem paramEntityItem1, EntityList paramEntityList, EntityItem paramEntityItem2) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/*  652 */     LinkActionItem linkActionItem = new LinkActionItem(null, this.m_db, this.m_prof, "LINKAVAILPRODSTRUCT2");
/*  653 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem2, "ANNCODENAME");
/*  654 */     String str2 = PokUtils.getAttributeValue(paramEntityItem2, "ANNDATE", "", "", false);
/*  655 */     ArrayList<?> arrayList = getCountriesAsList(paramEntityItem1);
/*      */     
/*  657 */     addDebug("createLastOrderAvails " + paramEntityItem2.getKey() + " ANNCODENAME: " + str1 + " ANNDATE: " + str2);
/*  658 */     AttrSet attrSet = new AttrSet(paramEntityItem1, str1);
/*      */ 
/*      */     
/*  661 */     EntityGroup entityGroup = paramEntityList.getEntityGroup("PRODSTRUCT");
/*  662 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  663 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*  664 */       if (checkPsEligibility(false, entityItem, str2)) {
/*  665 */         EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(0);
/*  666 */         ArrayList arrayList1 = getCountriesAsList(entityItem1);
/*  667 */         if (arrayList1.containsAll(arrayList)) {
/*  668 */           addDebug("createLastOrderAvails " + entityItem1.getKey() + " countries contains all autoavail ctrys");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  675 */           createOrRefAvail(entityItem, "CRPEERAVAIL2", attrSet, linkActionItem);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  680 */           addDebug("createLastOrderAvails skipping " + entityItem.getKey() + " " + entityItem1.getKey() + " ctrylist " + arrayList1 + " does not contain all of autoavail " + arrayList);
/*      */           
/*  682 */           ArrayList arrayList2 = getCountryDescAsList(paramEntityItem1);
/*  683 */           ArrayList<?> arrayList3 = getCountryDescAsList(entityItem1);
/*  684 */           arrayList2.removeAll(arrayList3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  694 */           this.args[0] = getLD_NDN(entityItem);
/*  695 */           this.args[1] = getLD_NDN(entityItem1);
/*  696 */           this.args[2] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*  697 */           this.args[3] = paramEntityItem1.getEntityGroup().getLongDescription();
/*  698 */           this.args[4] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*  699 */           this.args[5] = arrayList2.toString();
/*  700 */           addOutput(getResourceMsg("SKIPPED_FCCTRY_MSG2", this.args));
/*      */           
/*  702 */           arrayList3.clear();
/*  703 */           arrayList2.clear();
/*      */         } 
/*      */       } else {
/*  706 */         addDebug("createLastOrderAvails skipping ineligible " + entityItem.getKey());
/*      */       } 
/*      */     } 
/*      */     
/*  710 */     arrayList.clear();
/*  711 */     attrSet.dereference();
/*  712 */     attrSet = null;
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
/*      */   private Vector validateSetup(EntityItem paramEntityItem1, EntityItem paramEntityItem2) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, MiddlewareShutdownInProgressException {
/*  741 */     ArrayList arrayList = getCountriesAsList(paramEntityItem2);
/*  742 */     ArrayList<?> arrayList1 = getCountriesAsList(paramEntityItem1);
/*      */     
/*  744 */     String str1 = PokUtils.getAttributeValue(paramEntityItem1, "MTMLIST", "", "", false);
/*      */     
/*  746 */     Vector<String> vector = new Vector();
/*  747 */     if (str1.length() > 0) {
/*  748 */       StringTokenizer stringTokenizer = new StringTokenizer(str1, ",");
/*  749 */       while (stringTokenizer.hasMoreTokens()) {
/*  750 */         String str = stringTokenizer.nextToken().trim();
/*  751 */         if (str.length() != 7) {
/*      */           
/*  753 */           this.args[0] = getLD_Value(paramEntityItem1, "MTMLIST");
/*  754 */           this.args[1] = str;
/*  755 */           addError("INVALID_MTMATTR_ERR", this.args);
/*      */           continue;
/*      */         } 
/*  758 */         vector.addElement(str);
/*      */       } 
/*      */     } else {
/*      */       
/*  762 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "MTMLIST", "MTMLIST");
/*  763 */       addError("INVALID_ATTR_ERR", this.args);
/*      */     } 
/*      */     
/*  766 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem1, "AVAILTYPE");
/*      */     
/*  768 */     addDebug("validateSetup " + paramEntityItem1.getKey() + " MTMLIST:" + str1 + " AVAILTYPE: " + str2 + " setupCtry " + arrayList1 + " " + paramEntityItem2
/*  769 */         .getKey() + " annCtry " + arrayList);
/*      */     
/*  771 */     if (arrayList1.size() == 0 || !arrayList.containsAll(arrayList1)) {
/*      */       
/*  773 */       this.args[0] = getLD_Value(paramEntityItem1, "COUNTRYLIST");
/*  774 */       this.args[1] = paramEntityItem2.getEntityGroup().getLongDescription();
/*  775 */       this.args[2] = getLD_Value(paramEntityItem2, "COUNTRYLIST");
/*  776 */       addError("INVALID_CTRY_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  782 */     if (!"146".equals(str2) && 
/*  783 */       !"149".equals(str2)) {
/*      */ 
/*      */       
/*  786 */       this.args[0] = getLD_Value(paramEntityItem1, "AVAILTYPE");
/*  787 */       addError("INVALID_ATTR_ERR", this.args);
/*      */     } 
/*      */     
/*  790 */     if (getReturnCode() != 0) {
/*  791 */       arrayList.clear();
/*  792 */       arrayList1.clear();
/*  793 */       return null;
/*      */     } 
/*      */     
/*  796 */     long l = System.currentTimeMillis();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  801 */     Vector<EntityItem> vector1 = searchForModel(vector);
/*      */     
/*  803 */     addDebug("Time to do model searches: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */ 
/*      */     
/*  806 */     if ("146".equals(str2)) {
/*  807 */       for (byte b = 0; b < vector1.size(); b++) {
/*  808 */         EntityItem entityItem = vector1.elementAt(b);
/*  809 */         checkDates(paramEntityItem2, entityItem, "ANNDATE");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  814 */     arrayList.clear();
/*  815 */     arrayList1.clear();
/*  816 */     arrayList = null;
/*  817 */     arrayList1 = null;
/*      */     
/*  819 */     return vector1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ArrayList getCountriesAsList(EntityItem paramEntityItem) {
/*  828 */     ArrayList<String> arrayList = new ArrayList();
/*  829 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("COUNTRYLIST");
/*  830 */     if (eANFlagAttribute != null) {
/*      */       
/*  832 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  833 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/*  835 */         if (arrayOfMetaFlag[b].isSelected()) {
/*  836 */           arrayList.add(arrayOfMetaFlag[b].getFlagCode());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  841 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ArrayList getCountryDescAsList(EntityItem paramEntityItem) {
/*  850 */     ArrayList<String> arrayList = new ArrayList();
/*  851 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("COUNTRYLIST");
/*  852 */     if (eANFlagAttribute != null) {
/*      */       
/*  854 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  855 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/*  857 */         if (arrayOfMetaFlag[b].isSelected()) {
/*  858 */           arrayList.add(arrayOfMetaFlag[b].toString());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  863 */     return arrayList;
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
/*      */   private void checkDates(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString) throws SQLException, MiddlewareException {
/*  878 */     if (this.modelEG == null) {
/*  879 */       this.modelEG = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem2.getEntityType(), "Edit", false);
/*      */     }
/*      */     
/*  882 */     EntityItem entityItem = new EntityItem(this.modelEG, this.m_prof, this.m_db, paramEntityItem2.getEntityType(), paramEntityItem2.getEntityID());
/*      */     
/*  884 */     String str1 = PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", "", "", false);
/*  885 */     String str2 = PokUtils.getAttributeValue(entityItem, paramString, "", "", false);
/*  886 */     addDebug("checkDates " + paramEntityItem1.getKey() + " anndate " + str1 + " " + entityItem.getKey() + " mdldate " + str2);
/*      */     
/*  888 */     if (!str1.equals(str2)) {
/*      */       
/*  890 */       this.args[0] = getLD_NDN(paramEntityItem1);
/*  891 */       this.args[1] = getLD_Value(paramEntityItem1, "ANNDATE");
/*  892 */       this.args[2] = getLD_NDN(entityItem);
/*  893 */       this.args[3] = getLD_Value(entityItem, paramString);
/*  894 */       addError("MDL_DATE_ERR", this.args);
/*      */     } 
/*      */     
/*  897 */     this.modelEG.removeEntityItem(entityItem);
/*  898 */     for (int i = entityItem.getAttributeCount() - 1; i >= 0; i--) {
/*  899 */       EANAttribute eANAttribute = entityItem.getAttribute(i);
/*  900 */       entityItem.removeAttribute(eANAttribute);
/*      */     } 
/*  902 */     entityItem.setParent(null);
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
/*      */   private Vector searchForModel(Vector<E> paramVector) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  920 */     Vector<EntityItem> vector1 = new Vector(1);
/*  921 */     Vector<String> vector2 = new Vector(3);
/*  922 */     Vector<String> vector3 = new Vector(3);
/*  923 */     vector3.addElement("MACHTYPEATR");
/*  924 */     vector3.addElement("MODELATR");
/*  925 */     vector3.addElement("COFCAT");
/*  926 */     Vector<EntityItem> vector4 = new Vector(1);
/*  927 */     for (byte b = 0; b < paramVector.size(); b++) {
/*  928 */       vector4.clear();
/*  929 */       vector2.clear();
/*      */       
/*  931 */       String str1 = paramVector.elementAt(b).toString();
/*  932 */       String str2 = str1.substring(0, 4);
/*  933 */       String str3 = str1.substring(4, 7);
/*      */       
/*  935 */       addDebug("searchForModel ttttmmm[" + b + "] machtype " + str2 + " modelatr " + str3);
/*  936 */       EntityItem[] arrayOfEntityItem = null;
/*      */       
/*  938 */       vector2.addElement(str2);
/*  939 */       vector2.addElement(str3);
/*  940 */       vector2.addElement("100");
/*      */       
/*      */       try {
/*  943 */         StringBuffer stringBuffer = new StringBuffer();
/*  944 */         arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), this.m_prof, "SRDMODEL16", "MODEL", false, vector3, vector2, stringBuffer);
/*      */         
/*  946 */         if (stringBuffer.length() > 0) {
/*  947 */           addDebug(stringBuffer.toString());
/*      */         }
/*  949 */       } catch (SBRException sBRException) {
/*      */         
/*  951 */         StringWriter stringWriter = new StringWriter();
/*  952 */         sBRException.printStackTrace(new PrintWriter(stringWriter));
/*  953 */         addDebug("searchForModel SBRException: " + stringWriter.getBuffer().toString());
/*      */       } 
/*  955 */       if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  956 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*  957 */           String str = PokUtils.getAttributeFlagValue(arrayOfEntityItem[b1], "COFGRP");
/*  958 */           addDebug("searchForModel found " + arrayOfEntityItem[b1].getKey() + " cofgrp " + str);
/*  959 */           if ("150".equals(str)) {
/*  960 */             vector4.add(arrayOfEntityItem[b1]);
/*      */           }
/*  962 */           arrayOfEntityItem[b1] = null;
/*      */         } 
/*      */       }
/*      */       
/*  966 */       if (vector4.size() == 0) {
/*      */         
/*  968 */         this.args[0] = str2;
/*  969 */         this.args[1] = str3;
/*  970 */         addError("MDL_NOTFOUND_ERR", this.args);
/*      */       } else {
/*      */         
/*  973 */         vector1.addAll(vector4);
/*      */       } 
/*      */     } 
/*      */     
/*  977 */     vector4.clear();
/*  978 */     vector3.clear();
/*  979 */     vector2.clear();
/*  980 */     vector4 = null;
/*  981 */     vector3 = null;
/*  982 */     vector2 = null;
/*      */     
/*  984 */     return vector1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getLD_Value(EntityItem paramEntityItem, String paramString) {
/*  992 */     return PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString, paramString) + ": " + 
/*  993 */       PokUtils.getAttributeValue(paramEntityItem, paramString, ",", "<em>** Not Populated **</em>", false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getLD_NDN(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1003 */     return paramEntityItem.getEntityGroup().getLongDescription() + " &quot;" + getNavigationName(paramEntityItem) + "&quot;";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 1010 */     super.dereference();
/*      */     
/* 1012 */     this.rsBundle = null;
/* 1013 */     this.modelEG = null;
/* 1014 */     this.rptSb = null;
/* 1015 */     this.args = null;
/*      */     
/* 1017 */     this.metaTbl = null;
/* 1018 */     this.navName = null;
/* 1019 */     this.genAvail = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1027 */     return "$Revision: 1.10 $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1034 */     return "AUTOAVAILABRSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/* 1040 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDebug(String paramString) {
/* 1046 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getResourceMsg(String paramString, Object[] paramArrayOfObject) {
/* 1057 */     String str = this.rsBundle.getString(paramString);
/* 1058 */     if (paramArrayOfObject != null) {
/* 1059 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1060 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1063 */     return str;
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
/*      */   private void addError(String paramString, Object[] paramArrayOfObject) {
/* 1075 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 1076 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 1079 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString("ERROR_PREFIX"));
/* 1080 */     Object[] arrayOfObject = new Object[2];
/* 1081 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 1082 */     arrayOfObject[1] = this.navName;
/*      */     
/* 1084 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1093 */     String str = this.rsBundle.getString(paramString2);
/*      */     
/* 1095 */     if (paramArrayOfObject != null) {
/* 1096 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1097 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1100 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1110 */     StringBuffer stringBuffer = new StringBuffer();
/* 1111 */     NDN nDN = (NDN)NDN_TBL.get(paramEntityItem.getEntityType());
/*      */ 
/*      */     
/* 1114 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1115 */     if (eANList == null) {
/*      */       
/* 1117 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1118 */       eANList = entityGroup.getMetaAttribute();
/* 1119 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1121 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1123 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1124 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1125 */       if (b + 1 < eANList.size()) {
/* 1126 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/* 1129 */     if (nDN != null) {
/* 1130 */       EntityList entityList = null;
/* 1131 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 1132 */       EntityItem entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*      */       
/* 1134 */       if (entityItem == null && 
/* 1135 */         paramEntityItem.getEntityType().endsWith("PRODSTRUCT")) {
/* 1136 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType() + " pulling small VE for this " + paramEntityItem
/* 1137 */             .getKey());
/*      */         
/* 1139 */         String str = "EXRPT3FM";
/* 1140 */         if (paramEntityItem.getEntityType().equals("SWPRODSTRUCT")) {
/* 1141 */           str = "EXRPT3SWFM";
/* 1142 */         } else if (paramEntityItem.getEntityType().equals("IPSCSTRUC")) {
/* 1143 */           str = "EXRPT3IPSCFM";
/*      */         } 
/* 1145 */         entityList = this.m_db.getEntityList(paramEntityItem.getProfile(), new ExtractActionItem(null, this.m_db, paramEntityItem
/* 1146 */               .getProfile(), str), new EntityItem[] { new EntityItem(null, paramEntityItem
/* 1147 */                 .getProfile(), paramEntityItem.getEntityType(), paramEntityItem
/* 1148 */                 .getEntityID()) });
/*      */         
/* 1150 */         paramEntityItem = entityList.getParentEntityGroup().getEntityItem(0);
/* 1151 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*      */       } 
/*      */ 
/*      */       
/* 1155 */       if (entityItem != null) {
/* 1156 */         stringBuffer1.append("(" + nDN.getTag());
/* 1157 */         for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 1158 */           String str = nDN.getAttr().elementAt(b1).toString();
/* 1159 */           stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 1160 */           if (b1 + 1 < nDN.getAttr().size()) {
/* 1161 */             stringBuffer1.append(" ");
/*      */           }
/*      */         } 
/* 1164 */         stringBuffer1.append(") ");
/*      */       } else {
/* 1166 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType());
/*      */       } 
/* 1168 */       nDN = nDN.getNext();
/* 1169 */       if (nDN != null) {
/* 1170 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/* 1171 */         if (entityItem != null) {
/* 1172 */           stringBuffer1.append("(" + nDN.getTag());
/* 1173 */           for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 1174 */             String str = nDN.getAttr().elementAt(b1).toString();
/* 1175 */             stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 1176 */             if (b1 + 1 < nDN.getAttr().size()) {
/* 1177 */               stringBuffer1.append(" ");
/*      */             }
/*      */           } 
/* 1180 */           stringBuffer1.append(") ");
/*      */         } else {
/* 1182 */           addDebug("NO entity found for next ndn.getEntityType(): " + nDN.getEntityType());
/*      */         } 
/*      */       } 
/* 1185 */       stringBuffer.insert(0, stringBuffer1.toString());
/*      */       
/* 1187 */       if (entityList != null) {
/* 1188 */         entityList.dereference();
/*      */       }
/*      */     } 
/*      */     
/* 1192 */     return stringBuffer.toString().trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem getNDNitem(EntityItem paramEntityItem, String paramString) {
/*      */     byte b;
/* 1201 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 1202 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 1203 */       if (entityItem.getEntityType().equals(paramString)) {
/* 1204 */         return entityItem;
/*      */       }
/*      */     } 
/* 1207 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 1208 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 1209 */       if (entityItem.getEntityType().equals(paramString)) {
/* 1210 */         return entityItem;
/*      */       }
/*      */     } 
/* 1213 */     return null;
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
/*      */   private class AttrSet
/*      */   {
/* 1240 */     private Vector attrCodeVct = new Vector();
/* 1241 */     private Hashtable attrValTbl = new Hashtable<>();
/*      */     
/*      */     void addSingle(EntityItem param1EntityItem, String param1String) {
/* 1244 */       String str = PokUtils.getAttributeFlagValue(param1EntityItem, param1String);
/* 1245 */       if (str == null) {
/* 1246 */         str = "";
/*      */       }
/* 1248 */       this.attrCodeVct.addElement(param1String);
/* 1249 */       this.attrValTbl.put(param1String, str);
/*      */     }
/*      */     
/*      */     void addText(EntityItem param1EntityItem, String param1String) {
/* 1253 */       String str = PokUtils.getAttributeValue(param1EntityItem, param1String, "", "", false);
/* 1254 */       this.attrCodeVct.addElement(param1String);
/* 1255 */       this.attrValTbl.put(param1String, str);
/*      */     }
/*      */     
/*      */     void addMult(ArrayList<?> param1ArrayList, String param1String) {
/* 1259 */       Vector vector = new Vector(param1ArrayList);
/* 1260 */       if (!this.attrCodeVct.contains(param1String)) {
/* 1261 */         this.attrCodeVct.addElement(param1String);
/*      */       }
/* 1263 */       this.attrValTbl.put(param1String, vector);
/*      */     }
/*      */     
/*      */     void addMult(EntityItem param1EntityItem, String param1String) {
/* 1267 */       String str = PokUtils.getAttributeFlagValue(param1EntityItem, param1String);
/* 1268 */       if (str == null) {
/* 1269 */         str = "";
/*      */       }
/* 1271 */       String[] arrayOfString = PokUtils.convertToArray(str);
/* 1272 */       Vector<String> vector = new Vector(arrayOfString.length);
/* 1273 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 1274 */         vector.addElement(arrayOfString[b]);
/*      */       }
/* 1276 */       if (!this.attrCodeVct.contains(param1String)) {
/* 1277 */         this.attrCodeVct.addElement(param1String);
/*      */       }
/* 1279 */       this.attrValTbl.put(param1String, vector);
/*      */     }
/*      */     
/*      */     AttrSet(EntityItem param1EntityItem, String param1String) {
/* 1283 */       this.attrCodeVct.addElement("ANNCODENAME");
/* 1284 */       this.attrValTbl.put("ANNCODENAME", param1String);
/*      */       
/* 1286 */       this.attrCodeVct.addElement("AVAILANNTYPE");
/* 1287 */       this.attrValTbl.put("AVAILANNTYPE", "RFA");
/*      */       
/* 1289 */       for (byte b = 0; b < AUTOAVAILABRSTATUS.AALIST_ATTR.length; b++) {
/* 1290 */         EANMetaAttribute eANMetaAttribute = param1EntityItem.getEntityGroup().getMetaAttribute(AUTOAVAILABRSTATUS.AALIST_ATTR[b]);
/* 1291 */         if (eANMetaAttribute != null)
/*      */         {
/*      */           
/* 1294 */           if (eANMetaAttribute.getAttributeType().equals("F")) {
/* 1295 */             addMult(param1EntityItem, AUTOAVAILABRSTATUS.AALIST_ATTR[b]);
/* 1296 */           } else if (eANMetaAttribute.getAttributeType().equals("U")) {
/* 1297 */             addSingle(param1EntityItem, AUTOAVAILABRSTATUS.AALIST_ATTR[b]);
/*      */           } else {
/* 1299 */             addText(param1EntityItem, AUTOAVAILABRSTATUS.AALIST_ATTR[b]);
/*      */           }  } 
/*      */       } 
/*      */     }
/* 1303 */     Vector getAttrCodes() { return this.attrCodeVct; } Hashtable getAttrValues() {
/* 1304 */       return this.attrValTbl;
/*      */     }
/*      */     
/*      */     void dereference() {
/* 1308 */       this.attrCodeVct.clear();
/* 1309 */       this.attrValTbl.clear();
/* 1310 */       this.attrCodeVct = null;
/* 1311 */       this.attrValTbl = null;
/*      */     } }
/*      */   
/*      */   private static class NDN {
/*      */     private String etype;
/*      */     private String tag;
/*      */     private NDN next;
/* 1318 */     private Vector attrVct = new Vector();
/*      */     NDN(String param1String1, String param1String2) {
/* 1320 */       this.etype = param1String1;
/* 1321 */       this.tag = param1String2;
/*      */     }
/* 1323 */     String getTag() { return this.tag; }
/* 1324 */     String getEntityType() { return this.etype; } Vector getAttr() {
/* 1325 */       return this.attrVct;
/*      */     } void addAttr(String param1String) {
/* 1327 */       this.attrVct.addElement(param1String);
/*      */     }
/* 1329 */     void setNext(NDN param1NDN) { this.next = param1NDN; } NDN getNext() {
/* 1330 */       return this.next;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\AUTOAVAILABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */