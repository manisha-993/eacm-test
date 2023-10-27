/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Hashtable;
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
/*     */ public abstract class SVCPACPRESELBase
/*     */   extends PokBaseABR
/*     */ {
/*     */   protected static final String ATT_CATMACHTYPE = "CATMACHTYPE";
/*     */   protected static final String ATT_CATSEOID = "CATSEOID";
/*     */   protected static final String ATT_SVCPACTYPE = "SVCPACTYPE";
/*     */   protected static final String ATT_OFFCOUNTRY = "OFFCOUNTRY";
/*     */   protected static final String ATT_COFSUBCAT = "COFSUBCAT";
/*     */   protected static final String ATT_PRESELINDC = "PRESELINDC";
/*     */   protected static final String PRESELINDC_Yes = "Yes";
/*     */   protected static final String ATT_PRESELSEOTYPE = "PRESELSEOTYPE";
/*     */   protected static final String PRESELSEOTYPE_LSEO = "PRE1";
/*     */   protected static final String PRESELSEOTYPE_LSEOBDL = "PRE2";
/*     */   protected static final String ATT_COFCAT = "COFCAT";
/*     */   protected static final String ATT_BUNDLETYPE = "BUNDLETYPE";
/*     */   protected static final String ATT_SVCPACBNDLTYPE = "SVCPACBNDLTYPE";
/*     */   protected static final String COFCAT_Service = "102";
/*  61 */   private StringBuffer rptSb = new StringBuffer();
/*  62 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  63 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  64 */   private String navName = "";
/*  65 */   private Hashtable metaTbl = new Hashtable<>();
/*  66 */   private ResourceBundle rsBundle = null;
/*  67 */   protected Object[] args = (Object[])new String[10];
/*     */   
/*  69 */   private String strCurrentDate = null;
/*     */   
/*  71 */   protected String offCountryFlag = null;
/*  72 */   protected String offCountry = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceBundle getBundle() {
/*  78 */     return this.rsBundle;
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
/*     */   public void execute_run() {
/*  93 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/*  95 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} --><br /><br />" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     String str3 = "";
/*     */     try {
/* 107 */       start_ABRBuild();
/* 108 */       setReturnCode(0);
/*     */       
/* 110 */       this.rsBundle = ResourceBundle.getBundle(SERVPACKPRESELECTABR.class.getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */       
/* 112 */       this.strCurrentDate = this.m_db.getDates().getNow().substring(0, 10);
/*     */       
/* 114 */       str3 = this.m_elist.getParentEntityGroup().getLongDescription();
/*     */ 
/*     */       
/* 117 */       this.navName = getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */       
/* 119 */       verifyRequest();
/*     */       
/* 121 */       if (getReturnCode() == 0) {
/* 122 */         executeThis();
/*     */       }
/*     */     }
/* 125 */     catch (Throwable throwable) {
/* 126 */       StringWriter stringWriter = new StringWriter();
/* 127 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 128 */       String str6 = "<pre>{0}</pre>";
/* 129 */       MessageFormat messageFormat1 = new MessageFormat(str5);
/* 130 */       setReturnCode(-3);
/* 131 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 133 */       this.args[0] = throwable.getMessage();
/* 134 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 135 */       messageFormat1 = new MessageFormat(str6);
/* 136 */       this.args[0] = stringWriter.getBuffer().toString();
/* 137 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 138 */       logError("Exception: " + throwable.getMessage());
/* 139 */       logError(stringWriter.getBuffer().toString());
/*     */     } finally {
/*     */       
/* 142 */       setDGTitle(this.navName);
/* 143 */       setDGRptName(getShortClassName(getClass()));
/* 144 */       setDGRptClass(getABRCode());
/*     */ 
/*     */       
/* 147 */       if (!isReadOnly()) {
/* 148 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 154 */     MessageFormat messageFormat = new MessageFormat(str1);
/*     */     
/* 156 */     this.args[0] = getShortClassName(getClass());
/* 157 */     this.args[1] = this.navName;
/* 158 */     String str4 = messageFormat.format(this.args);
/* 159 */     messageFormat = new MessageFormat(str2);
/* 160 */     this.args[0] = this.m_prof.getOPName();
/* 161 */     this.args[1] = this.m_prof.getRoleDescription();
/* 162 */     this.args[2] = this.m_prof.getWGName();
/* 163 */     this.args[3] = getNow();
/* 164 */     this.args[4] = str3;
/* 165 */     this.args[5] = getABRVersion();
/*     */     
/* 167 */     this.rptSb.insert(0, str4 + messageFormat.format(this.args) + NEWLINE);
/*     */     
/* 169 */     println(EACustom.getDocTypeHtml());
/* 170 */     println(this.rptSb.toString());
/* 171 */     printDGSubmitString();
/*     */     
/* 173 */     println(EACustom.getTOUDiv());
/* 174 */     buildReportFooter();
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
/*     */   protected abstract void executeThis() throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void verifyRequest();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector findLSEOForCheck1(String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 216 */     return findSEOForCheck1(paramString, "LSEO", "SRDLSEO1", "LSEOUNPUBDATEMTRGT", "LSEOPUBDATEMTRGT");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector findSEOForCheck1(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 223 */     Vector<EntityItem> vector = new Vector(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     EntityItem[] arrayOfEntityItem = searchForSEO(paramString1, paramString2, paramString3);
/* 231 */     if (arrayOfEntityItem == null || arrayOfEntityItem.length == 0) {
/*     */       
/* 233 */       this.args[0] = paramString2 + "s";
/* 234 */       this.args[1] = paramString1;
/* 235 */       addError("SEO_ERR", this.args);
/*     */     } else {
/* 237 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 238 */         EntityItem entityItem = arrayOfEntityItem[b];
/*     */         
/* 240 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 241 */         if (eANFlagAttribute == null || !eANFlagAttribute.isSelected(this.offCountryFlag)) {
/*     */           
/* 243 */           this.args[0] = getLD_NDN(entityItem);
/* 244 */           this.args[1] = this.offCountry;
/* 245 */           addError("CTRY_ERR", this.args);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 250 */         else if (checkDates(entityItem, paramString4, paramString5)) {
/* 251 */           vector.add(entityItem);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     return vector;
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
/*     */   protected Vector findLSEOBDLForCheck1(String paramString) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 278 */     return findSEOForCheck1(paramString, "LSEOBUNDLE", "SRDLSEOBUNDLE1", "BUNDLUNPUBDATEMTRGT", "BUNDLPUBDATEMTRGT");
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
/*     */   protected boolean verifySEOtechCompat(EntityItem[] paramArrayOfEntityItem, String paramString, ExtractActionItem paramExtractActionItem) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
/* 313 */     boolean bool = false;
/* 314 */     addDebug("verifySEOtechCompat match MACHTYPE: " + paramString);
/*     */     
/* 316 */     EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, paramExtractActionItem, paramArrayOfEntityItem);
/* 317 */     addDebug("verifySEOtechCompat Extract " + paramExtractActionItem.getActionItemKey() + " " + PokUtils.outputList(entityList));
/*     */     
/* 319 */     EntityGroup entityGroup = entityList.getEntityGroup("MODEL");
/* 320 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 321 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 322 */       String str = getAttributeFlagEnabledValue(entityItem, "MACHTYPEATR");
/* 323 */       addDebug("verifySEOtechCompat checking " + entityItem.getKey() + " MACHTYPEATR:" + str);
/* 324 */       if (paramString.equals(str)) {
/* 325 */         bool = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 329 */     entityList.dereference();
/*     */     
/* 331 */     return bool;
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
/*     */   private boolean checkDates(EntityItem paramEntityItem, String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 350 */     boolean bool = true;
/*     */     
/* 352 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, paramString2, "", "9999-12-31", false);
/* 353 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, paramString1, "", "9999-12-31", false);
/*     */     
/* 355 */     addDebug("checkDates " + paramEntityItem.getKey() + " strCurrentDate " + this.strCurrentDate + " " + paramString2 + ":" + str1 + " " + paramString1 + ":" + str2);
/*     */ 
/*     */     
/* 358 */     if (str1.compareTo(this.strCurrentDate) > 0 || this.strCurrentDate
/* 359 */       .compareTo(str2) > 0) {
/* 360 */       bool = false;
/*     */       
/* 362 */       this.args[0] = getLD_NDN(paramEntityItem);
/* 363 */       addError("DATE_ERR", this.args);
/*     */     } 
/*     */     
/* 366 */     return bool;
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
/*     */   private EntityItem[] searchForSEO(String paramString1, String paramString2, String paramString3) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 382 */     addDebug("searchForSEO " + paramString2 + " SEOID:" + paramString1);
/* 383 */     EntityItem[] arrayOfEntityItem = null;
/* 384 */     Vector<String> vector1 = new Vector(1);
/* 385 */     vector1.addElement("SEOID");
/*     */     
/* 387 */     Vector<String> vector2 = new Vector(1);
/* 388 */     vector2.addElement(paramString1);
/*     */     
/*     */     try {
/* 391 */       StringBuffer stringBuffer = new StringBuffer();
/* 392 */       arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), this.m_prof, paramString3, paramString2, false, vector1, vector2, stringBuffer);
/*     */       
/* 394 */       if (stringBuffer.length() > 0) {
/* 395 */         addDebug(stringBuffer.toString());
/*     */       }
/* 397 */     } catch (SBRException sBRException) {
/*     */       
/* 399 */       StringWriter stringWriter = new StringWriter();
/* 400 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 401 */       addDebug("searchForSEO SBRException: " + stringWriter.getBuffer().toString());
/*     */     } 
/* 403 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 404 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 405 */         addDebug("searchForSEO found " + arrayOfEntityItem[b].getKey());
/*     */       }
/*     */     }
/*     */     
/* 409 */     vector1.clear();
/* 410 */     vector2.clear();
/* 411 */     return arrayOfEntityItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLD_NDN(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 422 */     return paramEntityItem.getEntityGroup().getLongDescription() + " &quot;" + getNavigationName(paramEntityItem) + "&quot;";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLD_Value(EntityItem paramEntityItem, String paramString) {
/* 430 */     return PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString, paramString) + ": " + 
/* 431 */       PokUtils.getAttributeValue(paramEntityItem, paramString, ",", "<em>** Not Populated **</em>", false);
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
/*     */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 444 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 445 */     setReturnCode(-1);
/*     */ 
/*     */     
/* 448 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 449 */     Object[] arrayOfObject = new Object[2];
/* 450 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 451 */     arrayOfObject[1] = this.navName;
/*     */     
/* 453 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 462 */     String str = this.rsBundle.getString(paramString2);
/*     */     
/* 464 */     if (paramArrayOfObject != null) {
/* 465 */       MessageFormat messageFormat = new MessageFormat(str);
/* 466 */       str = messageFormat.format(paramArrayOfObject);
/*     */     } 
/*     */     
/* 469 */     addOutput(paramString1 + " " + str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 475 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addHeading(int paramInt, String paramString) {
/* 481 */     this.rptSb.append("<h" + paramInt + ">" + paramString + "</h" + paramInt + ">" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 487 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dereference() {
/* 493 */     this.metaTbl.clear();
/* 494 */     this.metaTbl = null;
/* 495 */     this.navName = null;
/* 496 */     this.rptSb = null;
/* 497 */     this.rsBundle = null;
/* 498 */     this.args = null;
/*     */     
/* 500 */     this.strCurrentDate = null;
/* 501 */     this.offCountry = null;
/* 502 */     this.offCountryFlag = null;
/*     */     
/* 504 */     super.dereference();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 514 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 517 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 518 */     if (eANList == null) {
/*     */       
/* 520 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 521 */       eANList = entityGroup.getMetaAttribute();
/* 522 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 524 */     for (byte b = 0; b < eANList.size(); b++) {
/*     */       
/* 526 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 527 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 528 */       if (b + 1 < eANList.size()) {
/* 529 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/*     */     
/* 533 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 543 */     return "$Revision: 1.2 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\SVCPACPRESELBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */