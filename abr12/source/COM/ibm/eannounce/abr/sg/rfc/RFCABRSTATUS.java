/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*     */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import COM.ibm.opicmpdh.objects.LongText;
/*     */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*     */ import COM.ibm.opicmpdh.objects.Text;
/*     */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*     */ import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.StringCharacterIterator;
/*     */ import java.util.Hashtable;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RFCABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*     */   protected static final String NEWLINE = "\n";
/*  49 */   protected static final Hashtable READ_LANGS_TBL = new Hashtable<>();
/*     */   static {
/*  51 */     READ_LANGS_TBL.put("" + Profile.ENGLISH_LANGUAGE.getNLSID(), Profile.ENGLISH_LANGUAGE);
/*  52 */     READ_LANGS_TBL.put("" + Profile.GERMAN_LANGUAGE.getNLSID(), Profile.GERMAN_LANGUAGE);
/*  53 */     READ_LANGS_TBL.put("" + Profile.ITALIAN_LANGUAGE.getNLSID(), Profile.ITALIAN_LANGUAGE);
/*  54 */     READ_LANGS_TBL.put("" + Profile.JAPANESE_LANGUAGE.getNLSID(), Profile.JAPANESE_LANGUAGE);
/*  55 */     READ_LANGS_TBL.put("" + Profile.FRENCH_LANGUAGE.getNLSID(), Profile.FRENCH_LANGUAGE);
/*  56 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LANGUAGE.getNLSID(), Profile.SPANISH_LANGUAGE);
/*  57 */     READ_LANGS_TBL.put("" + Profile.UK_ENGLISH_LANGUAGE.getNLSID(), Profile.UK_ENGLISH_LANGUAGE);
/*  58 */     READ_LANGS_TBL.put("" + Profile.KOREAN_LANGUAGE.getNLSID(), Profile.KOREAN_LANGUAGE);
/*  59 */     READ_LANGS_TBL.put("" + Profile.CHINESE_LANGUAGE.getNLSID(), Profile.CHINESE_LANGUAGE);
/*  60 */     READ_LANGS_TBL.put("" + Profile.FRENCH_CANADIAN_LANGUAGE.getNLSID(), Profile.FRENCH_CANADIAN_LANGUAGE);
/*  61 */     READ_LANGS_TBL.put("" + Profile.CHINESE_SIMPLIFIED_LANGUAGE.getNLSID(), Profile.CHINESE_SIMPLIFIED_LANGUAGE);
/*  62 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LATINAMERICAN_LANGUAGE.getNLSID(), Profile.SPANISH_LATINAMERICAN_LANGUAGE);
/*  63 */     READ_LANGS_TBL.put("" + Profile.PORTUGUESE_BRAZILIAN_LANGUAGE.getNLSID(), Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);
/*     */   }
/*     */   
/*  66 */   private StringBuffer rptSb = new StringBuffer();
/*  67 */   private static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("RFCABRSTATUS");
/*     */   
/*     */   private ResourceBundle rsBundle;
/*     */   private RfcAbrFactory rfcAbrFactory;
/*     */   private String navName;
/*  72 */   private Object[] args = (Object[])new String[10];
/*  73 */   private String t2DTS = "&nbsp;";
/*     */   private MessageFormat msgf;
/*     */   
/*     */   public RFCABRSTATUS() {
/*  77 */     this.rfcAbrFactory = new RfcAbrFactory();
/*  78 */     this.rfcAbrFactory.setRfcAbrStatus(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  85 */     long l = System.currentTimeMillis();
/*     */     
/*     */     try {
/*  88 */       start_ABRBuild(false);
/*     */ 
/*     */       
/*  91 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */ 
/*     */       
/*  94 */       setReturnCode(-1);
/*     */ 
/*     */       
/*  97 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*  98 */               getEntityType(), getEntityID()) });
/*     */ 
/*     */       
/* 101 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */       
/* 103 */       this.navName = getNavigationName(entityItem);
/*     */       
/*     */       try {
/* 106 */         RfcAbr rfcAbr = this.rfcAbrFactory.getRfcTypeAbr(getEntityType());
/* 107 */         rfcAbr.processThis();
/* 108 */         setReturnCode(0);
/* 109 */         addOutput("Promoted " + entityItem.getKey() + " successfully");
/* 110 */       } catch (RfcAbrException rfcAbrException) {
/* 111 */         rfcAbrException.printStackTrace();
/* 112 */         addOutput("Error: " + rfcAbrException.getMessage());
/* 113 */         StringWriter stringWriter = new StringWriter();
/* 114 */         rfcAbrException.printStackTrace(new PrintWriter(stringWriter));
/* 115 */         addComment(stringWriter.getBuffer().toString());
/* 116 */         setReturnCode(-1);
/* 117 */       } catch (HWPIMSAbnormalException hWPIMSAbnormalException) {
/* 118 */         hWPIMSAbnormalException.printStackTrace();
/* 119 */         addOutput("Error message from RFC web service: " + hWPIMSAbnormalException.getMessage());
/* 120 */         StringWriter stringWriter = new StringWriter();
/* 121 */         hWPIMSAbnormalException.printStackTrace(new PrintWriter(stringWriter));
/* 122 */         addComment(stringWriter.getBuffer().toString());
/* 123 */         setReturnCode(-1);
/* 124 */       } catch (Exception exception) {
/* 125 */         exception.printStackTrace();
/* 126 */         throw exception;
/*     */       } 
/* 128 */     } catch (Exception exception) {
/* 129 */       StringWriter stringWriter = new StringWriter();
/* 130 */       String str1 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 131 */       String str2 = "<pre>{0}</pre>";
/* 132 */       this.msgf = new MessageFormat(str1);
/* 133 */       setReturnCode(-3);
/* 134 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 136 */       this.args[0] = exception.getMessage();
/* 137 */       this.rptSb.append(this.msgf.format(this.args) + "\n");
/* 138 */       this.msgf = new MessageFormat(str2);
/* 139 */       this.args[0] = stringWriter.getBuffer().toString();
/* 140 */       this.rptSb.append(this.msgf.format(this.args) + "\n");
/* 141 */       logError("Exception: " + exception.getMessage());
/* 142 */       logError(stringWriter.getBuffer().toString());
/* 143 */       exception.printStackTrace();
/*     */     } finally {
/* 145 */       if (this.t2DTS.equals("&nbsp;")) {
/* 146 */         this.t2DTS = getNow();
/*     */       }
/* 148 */       setDGTitle(this.navName);
/* 149 */       setDGRptName(getShortClassName(getClass()));
/* 150 */       setDGRptClass(getABRCode());
/*     */       
/* 152 */       if (!isReadOnly()) {
/* 153 */         clearSoftLock();
/*     */       }
/* 155 */       addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*     */     } 
/*     */ 
/*     */     
/* 159 */     buildReport();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 165 */     return getClass().getName();
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/* 169 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 180 */     this.rptSb.append("<p>" + convertToHTML(paramString) + "</p>" + "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOutputHeader(String paramString) {
/* 187 */     this.rptSb.append("<h2>" + convertToHTML(paramString) + "</h2>" + "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String convertToHTML(String paramString) {
/* 198 */     String str = "";
/* 199 */     StringBuffer stringBuffer = new StringBuffer();
/* 200 */     StringCharacterIterator stringCharacterIterator = null;
/* 201 */     char c = ' ';
/* 202 */     if (paramString != null) {
/* 203 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 204 */       c = stringCharacterIterator.first();
/* 205 */       while (c != '￿') {
/* 206 */         switch (c) {
/*     */           case '<':
/* 208 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 211 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case '"':
/* 218 */             stringBuffer.append("&quot;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\'':
/* 223 */             stringBuffer.append("&#" + c + ";");
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 233 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 236 */         c = stringCharacterIterator.next();
/*     */       } 
/* 238 */       str = stringBuffer.toString();
/*     */     } 
/* 240 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 247 */     addOutput(paramString);
/* 248 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 255 */     if (3 <= DEBUG_LVL) {
/* 256 */       this.rptSb.append("<!-- " + paramString + " -->" + "\n");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addComment(String paramString) {
/* 264 */     this.rptSb.append("<!-- " + paramString + " -->" + "\n");
/*     */   }
/*     */   
/*     */   protected String getCurrentTime() {
/* 268 */     return getNow();
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
/*     */   protected void setTextValue(String paramString1, String paramString2, EntityItem paramEntityItem) throws SQLException, MiddlewareBusinessRuleException, MiddlewareException {
/* 283 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 284 */     addDebug("setTextValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*     */ 
/*     */     
/* 287 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 288 */     if (eANMetaAttribute == null) {
/* 289 */       addDebug("setTextValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 290 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/* 294 */     if (paramString2 != null) {
/* 295 */       if (paramString2.equals(getAttributeValue(paramEntityItem, paramString2))) {
/* 296 */         addDebug("setTextValue " + paramEntityItem.getKey() + " " + paramString1 + " already matches: " + paramString2);
/*     */       } else {
/*     */         try {
/* 299 */           if (this.m_cbOn == null) {
/* 300 */             setControlBlock();
/*     */           }
/* 302 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/*     */           
/* 304 */           Text text = new Text(this.m_prof.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/* 305 */           Vector<Text> vector = new Vector();
/* 306 */           Vector<ReturnEntityKey> vector1 = new Vector();
/* 307 */           vector.addElement(text);
/* 308 */           returnEntityKey.m_vctAttributes = vector;
/* 309 */           vector1.addElement(returnEntityKey);
/* 310 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 311 */           addDebug(paramEntityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*     */         } finally {
/* 313 */           this.m_db.commit();
/* 314 */           this.m_db.freeStatement();
/* 315 */           this.m_db.isPending("finally after update in setText value");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setLongTextValue(String paramString1, String paramString2, EntityItem paramEntityItem) throws SQLException, MiddlewareBusinessRuleException, MiddlewareException {
/* 333 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 334 */     addDebug("setLongTextValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*     */ 
/*     */     
/* 337 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 338 */     if (eANMetaAttribute == null) {
/* 339 */       addDebug("setLongTextValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 340 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/*     */       
/*     */       return;
/*     */     } 
/* 344 */     if (paramString2 != null) {
/* 345 */       if (paramString2.equals(getAttributeValue(paramEntityItem, paramString2))) {
/* 346 */         addDebug("setLongTextValue " + paramEntityItem.getKey() + " " + paramString1 + " already matches: " + paramString2);
/*     */       } else {
/*     */         try {
/* 349 */           if (this.m_cbOn == null) {
/* 350 */             setControlBlock();
/*     */           }
/* 352 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/*     */           
/* 354 */           LongText longText = new LongText(this.m_prof.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/* 355 */           Vector<LongText> vector = new Vector();
/* 356 */           Vector<ReturnEntityKey> vector1 = new Vector();
/* 357 */           vector.addElement(longText);
/* 358 */           returnEntityKey.m_vctAttributes = vector;
/* 359 */           vector1.addElement(returnEntityKey);
/* 360 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 361 */           addDebug(paramEntityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*     */         } finally {
/* 363 */           this.m_db.commit();
/* 364 */           this.m_db.freeStatement();
/* 365 */           this.m_db.isPending("finally after update in setText value");
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
/*     */   
/*     */   protected void setFlagValue(String paramString1, String paramString2, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 379 */     logMessage(getDescription() + " ***** " + paramString1 + " set to: " + paramString2);
/* 380 */     addDebug("setFlagValue entered for " + paramString1 + " set to: " + paramString2);
/*     */ 
/*     */     
/* 383 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 384 */     if (eANMetaAttribute == null) {
/* 385 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 386 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/*     */       return;
/*     */     } 
/* 389 */     if (paramString2 != null) {
/* 390 */       if (paramString2.equals(getAttributeFlagEnabledValue(paramEntityItem, paramString1))) {
/* 391 */         addDebug("setFlagValue " + paramEntityItem.getKey() + " " + paramString1 + " already matches: " + paramString2);
/*     */       } else {
/*     */         try {
/* 394 */           if (this.m_cbOn == null) {
/* 395 */             setControlBlock();
/*     */           }
/* 397 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/*     */           
/* 399 */           SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/* 400 */           Vector<SingleFlag> vector = new Vector();
/* 401 */           Vector<ReturnEntityKey> vector1 = new Vector();
/* 402 */           vector.addElement(singleFlag);
/* 403 */           returnEntityKey.m_vctAttributes = vector;
/* 404 */           vector1.addElement(returnEntityKey);
/* 405 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 406 */           addDebug(paramEntityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*     */         } finally {
/* 408 */           this.m_db.commit();
/* 409 */           this.m_db.freeStatement();
/* 410 */           this.m_db.isPending("finally after update in setflag value");
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
/*     */ 
/*     */   
/*     */   protected Profile switchRole(String paramString) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 425 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/* 426 */     if (profile == null) {
/* 427 */       addDebug("Could not switch to " + paramString + " role");
/*     */     } else {
/* 429 */       addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile
/* 430 */           .getRoleCode());
/*     */ 
/*     */       
/* 433 */       String str = ABRServerProperties.getNLSIDs(this.m_abri.getABRCode());
/* 434 */       addDebug("switchRole nlsids: " + str);
/* 435 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 436 */       while (stringTokenizer.hasMoreTokens()) {
/* 437 */         String str1 = stringTokenizer.nextToken();
/* 438 */         NLSItem nLSItem = (NLSItem)READ_LANGS_TBL.get(str1);
/* 439 */         if (!profile.getReadLanguages().contains(nLSItem)) {
/* 440 */           profile.getReadLanguages().addElement(nLSItem);
/* 441 */           addDebug("added nlsitem " + nLSItem + " to new prof");
/*     */         } 
/*     */       } 
/*     */     } 
/* 445 */     return profile;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildReport() {
/* 451 */     println(EACustom.getDocTypeHtml());
/* 452 */     buildHeader();
/* 453 */     println(this.rptSb.toString());
/* 454 */     printDGSubmitString();
/* 455 */     println(EACustom.getTOUDiv());
/* 456 */     buildReportFooter();
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
/*     */   private void buildHeader() {
/* 470 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + "\n" + EACustom.getCSS() + "\n" + EACustom.getTitle("{0} {1}") + "\n" + "</head>" + "\n" + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + "\n" + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + "\n";
/*     */ 
/*     */ 
/*     */     
/* 474 */     this.msgf = new MessageFormat(str1);
/* 475 */     this.args[0] = getShortClassName(getClass());
/* 476 */     this.args[1] = this.navName;
/* 477 */     String str2 = this.msgf.format(this.args);
/* 478 */     println(str2);
/*     */     
/* 480 */     String str3 = "<table>\n<tr><th>Userid: </th><td>{0}</td></tr>\n<tr><th>Role: </th><td>{1}</td></tr>\n<tr><th>Workgroup: </th><td>{2}</td></tr>\n<tr><th>Date/Time: </th><td>{3}</td></tr>\n<tr><th>RFC ABR: </th><td>{4}</td></tr>\n</table>\n<!-- {5} -->\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 488 */     this.msgf = new MessageFormat(str3);
/* 489 */     this.args[0] = this.m_prof.getOPName();
/* 490 */     this.args[1] = this.m_prof.getRoleDescription();
/* 491 */     this.args[2] = this.m_prof.getWGName();
/* 492 */     this.args[3] = this.t2DTS;
/* 493 */     this.args[4] = (getReturnCode() == 0) ? "Passed" : "Failed";
/* 494 */     this.args[5] = getABRVersion();
/* 495 */     String str4 = this.msgf.format(this.args);
/* 496 */     println(str4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 506 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 508 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 509 */     EANList eANList = entityGroup.getMetaAttribute();
/* 510 */     for (byte b = 0; b < eANList.size(); b++) {
/* 511 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 512 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 513 */       stringBuffer.append(" ");
/*     */     } 
/* 515 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RFCABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */