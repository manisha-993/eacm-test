/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.XLColumn;
/*      */ import COM.ibm.eannounce.abr.util.XLRow;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.ChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANDataFoundation;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaTextAttribute;
/*      */ import COM.ibm.eannounce.objects.TextAttribute;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.objects.Attribute;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import COM.ibm.opicmpdh.objects.Text;
/*      */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*      */ import com.ibm.transform.oim.eacm.diff.DiffVE;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipOutputStream;
/*      */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*      */ import org.apache.poi.hssf.usermodel.HSSFRichTextString;
/*      */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*      */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*      */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*      */ import org.apache.poi.ss.usermodel.RichTextString;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class QSMRPTABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*  152 */   private static final Hashtable ABR_TBL = new Hashtable<>(); private static final String GENAREA_AMERICAS = "6197"; private static final String GENAREA_LA = "6204"; protected static final String GENAREA_WW = "1999"; static {
/*  153 */     ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.QSMLSEOABR");
/*  154 */     ABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.sg.QSMLSEOBDLABR");
/*  155 */     ABR_TBL.put("ANNOUNCEMENT", "COM.ibm.eannounce.abr.sg.QSMANNABR");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  164 */   protected static final String[] GEOS = new String[] { "6204", "6197" };
/*      */   
/*  166 */   private StringBuffer rptSb = new StringBuffer();
/*  167 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  168 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*      */   private static final String QSMFEEDRESEND_YES = "Yes";
/*      */   
/*      */   private static final String QSMFEEDRESEND_NO = "No";
/*      */   
/*      */   private static final String QSMFEEDRESEND_NEW = "New";
/*      */   private static final int IDLEN = 6;
/*      */   private static final int MAX_7ZZZZZ = 483729407;
/*      */   private static final int BYPASS_8_9 = 120932352;
/*      */   private static final int MIN_DZZZZZ = 846526463;
/*      */   private static final int MAX_F00000 = 906992640;
/*      */   private static final int BYPASS_E = 60466176;
/*  181 */   private HSSFWorkbook wb = null;
/*      */   private Vector dtsVct;
/*  183 */   private EntityList listT2 = null;
/*  184 */   private ResourceBundle rsBundle = null;
/*  185 */   private Hashtable metaTbl = new Hashtable<>();
/*  186 */   private String navName = "";
/*  187 */   private QSMABRInterface qsmAbr = null;
/*  188 */   private Vector vctReturnsEntityKeys = new Vector();
/*  189 */   private String outputType = "";
/*      */   
/*      */   private boolean createSS = false;
/*      */   private boolean createFF = false;
/*  193 */   private String ffFileName = null;
/*  194 */   private String ffPathName = null;
/*  195 */   private int abr_debuglvl = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/*  201 */     return this.rsBundle;
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
/*      */   public void execute_run() {
/*  224 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  226 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  236 */     String str3 = "";
/*  237 */     String str4 = "";
/*      */     
/*  239 */     String[] arrayOfString = new String[10];
/*  240 */     String str5 = "New";
/*      */ 
/*      */     
/*      */     try {
/*  244 */       setReturnCode(0);
/*      */       
/*  246 */       start_ABRBuild(false);
/*      */       
/*  248 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */ 
/*      */       
/*  251 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  253 */       setControlBlock();
/*      */ 
/*      */       
/*  256 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  258 */               getEntityType(), getEntityID()) });
/*      */       
/*  260 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  261 */       str5 = getAttributeFlagEnabledValue(entityItem, "QSMFEEDRESEND");
/*      */       
/*  263 */       addDebug("execute: " + entityItem.getKey() + " sysfeedFlag: " + str5);
/*  264 */       if (str5 == null) {
/*  265 */         str5 = "New";
/*      */       }
/*      */ 
/*      */       
/*  269 */       this.navName = getNavigationName();
/*  270 */       str3 = this.m_elist.getParentEntityGroup().getLongDescription();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  276 */       String str = ABRServerProperties.getFileFormats(this.m_abri.getABRCode());
/*      */       
/*  278 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/*  279 */       if (!stringTokenizer.hasMoreTokens()) {
/*  280 */         this.createSS = true;
/*      */       }
/*      */       
/*  283 */       while (stringTokenizer.hasMoreTokens()) {
/*  284 */         String str7 = stringTokenizer.nextToken();
/*  285 */         if (str7.equalsIgnoreCase("xls")) {
/*  286 */           this.createSS = true;
/*      */         }
/*  288 */         if (str7.equalsIgnoreCase("txt")) {
/*  289 */           this.createFF = true;
/*      */         }
/*      */       } 
/*  292 */       addDebug("fileformats: " + str + " createSS: " + this.createSS + " createFF: " + this.createFF);
/*      */ 
/*      */       
/*      */       try {
/*  296 */         getChangeTimes();
/*      */ 
/*      */ 
/*      */         
/*  300 */         String str7 = (String)ABR_TBL.get(getEntityType());
/*  301 */         addDebug("creating instance of QSMABR  = '" + str7 + "'");
/*      */         
/*  303 */         if (str7 != null) {
/*  304 */           this.qsmAbr = (QSMABRInterface)Class.forName(str7).newInstance();
/*  305 */           str4 = getShortClassName(this.qsmAbr.getClass()) + " " + this.qsmAbr.getVersion();
/*      */           
/*  307 */           if (this.qsmAbr.canGenerateReport(entityItem, this)) {
/*      */             
/*  309 */             String str8 = getTime2DTS();
/*      */ 
/*      */             
/*  312 */             Profile profile = switchRole("QSMRPT");
/*  313 */             profile.setValOnEffOn(str8, str8);
/*      */ 
/*      */             
/*  316 */             this.listT2 = getEntityListForDiff(profile, this.qsmAbr.getVeName());
/*      */             
/*  318 */             entityItem = this.listT2.getParentEntityGroup().getEntityItem(0);
/*      */             
/*  320 */             if (this.qsmAbr.canGenerateReport(this.listT2, this)) {
/*      */               
/*  322 */               generateRFANumber(entityItem, "New".equals(str5));
/*      */               
/*  324 */               if ("New".equals(str5)) {
/*      */                 
/*  326 */                 generateOutputAll(entityItem);
/*  327 */               } else if ("Yes".equals(str5)) {
/*      */                 
/*  329 */                 generateOutputAll(entityItem);
/*  330 */               } else if ("No".equals(str5)) {
/*      */ 
/*      */ 
/*      */                 
/*  334 */                 if (getTime1DTS().equals(this.m_strEpoch)) {
/*      */                   
/*  336 */                   generateOutputAll(entityItem);
/*      */                 } else {
/*      */                   
/*  339 */                   if (this.createSS) {
/*  340 */                     generateOutputDelta();
/*  341 */                     if (this.wb == null) {
/*      */                       
/*  343 */                       String str9 = getBundle().getString("NO_CHG_FOUND");
/*  344 */                       addOutput(str9);
/*  345 */                       D.ebug("QSMRPTABRSTATUS:" + getEntityType() + " " + str9);
/*      */                     } 
/*      */                   } 
/*      */                   
/*  349 */                   if (this.createFF) {
/*  350 */                     generateFlatFile(entityItem);
/*      */                   }
/*      */                 }
/*      */               
/*      */               }
/*      */               else {
/*      */                 
/*  357 */                 addError("INVALID_DATA", (Object[])null);
/*      */               } 
/*      */               
/*  360 */               if (this.createSS && this.wb != null)
/*      */               {
/*  362 */                 outputSS(entityItem);
/*      */               }
/*  364 */               if (this.createFF && this.ffFileName != null)
/*      */               {
/*  366 */                 ftpFile();
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } else {
/*  371 */           addError(getShortClassName(getClass()) + " does not support " + getEntityType());
/*      */         } 
/*      */         
/*  374 */         if (!this.createSS && 
/*  375 */           getReturnCode() == 0) {
/*  376 */           setNoReport();
/*      */         }
/*      */         
/*  379 */         if (!this.createReport) {
/*  380 */           setCreateDGEntity(false);
/*      */         }
/*      */         
/*  383 */         this.dtsVct.clear();
/*  384 */       } catch (Exception exception) {
/*  385 */         throw exception;
/*      */       } finally {
/*  387 */         setFlagValue(getProfile(), "QSMFEEDRESEND", "No", entityItem);
/*  388 */         updatePDH();
/*  389 */         if (this.listT2 != null) {
/*  390 */           this.listT2.dereference();
/*      */         }
/*      */       }
/*      */     
/*  394 */     } catch (Throwable throwable) {
/*  395 */       StringWriter stringWriter = new StringWriter();
/*  396 */       String str7 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  397 */       String str8 = "<pre>{0}</pre>";
/*  398 */       MessageFormat messageFormat1 = new MessageFormat(str7);
/*  399 */       setReturnCode(-3);
/*  400 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  402 */       arrayOfString[0] = throwable.getMessage();
/*  403 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/*  404 */       messageFormat1 = new MessageFormat(str8);
/*  405 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/*  406 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/*  407 */       logError("Exception: " + throwable.getMessage());
/*  408 */       logError(stringWriter.getBuffer().toString());
/*      */       
/*  410 */       setCreateDGEntity(true);
/*      */     } finally {
/*      */       
/*  413 */       setDGTitle(this.navName);
/*  414 */       setDGRptName(getShortClassName(getClass()));
/*  415 */       setDGRptClass(getABRCode());
/*      */       
/*  417 */       if (!isReadOnly()) {
/*  418 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  424 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  425 */     if (this.qsmAbr != null) {
/*  426 */       arrayOfString[0] = getShortClassName(this.qsmAbr.getClass());
/*      */     } else {
/*  428 */       arrayOfString[0] = getShortClassName(getClass());
/*      */     } 
/*  430 */     arrayOfString[1] = this.navName;
/*  431 */     String str6 = messageFormat.format(arrayOfString);
/*  432 */     messageFormat = new MessageFormat(str2);
/*  433 */     arrayOfString[0] = this.m_prof.getOPName();
/*  434 */     arrayOfString[1] = this.m_prof.getRoleDescription();
/*  435 */     arrayOfString[2] = this.m_prof.getWGName();
/*  436 */     arrayOfString[3] = getNow();
/*  437 */     arrayOfString[4] = str3;
/*  438 */     arrayOfString[5] = str4 + " " + getABRVersion();
/*      */     
/*  440 */     this.rptSb.insert(0, str6 + messageFormat.format(arrayOfString) + NEWLINE);
/*      */     
/*  442 */     println(EACustom.getDocTypeHtml());
/*  443 */     println(this.rptSb.toString());
/*  444 */     printDGSubmitString();
/*      */     
/*  446 */     println(EACustom.getTOUDiv());
/*  447 */     buildReportFooter();
/*      */     
/*  449 */     this.metaTbl.clear();
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
/*      */   private Profile switchRole(String paramString) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  475 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/*  476 */     if (profile == null) {
/*  477 */       throw new MiddlewareException("Could not switch to " + paramString + " role");
/*      */     }
/*  479 */     addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile.getRoleCode());
/*  480 */     profile.setReadLanguage(0);
/*      */     
/*  482 */     return profile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityList getEntityListForDiff(Profile paramProfile, String paramString) throws SQLException, MiddlewareException {
/*  491 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, paramProfile, paramString);
/*      */     
/*  493 */     EntityList entityList = this.m_db.getEntityList(paramProfile, extractActionItem, new EntityItem[] { new EntityItem(null, paramProfile, 
/*  494 */             getEntityType(), getEntityID()) });
/*      */ 
/*      */     
/*  497 */     addDebug("EntityList for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/*  498 */         PokUtils.outputList(entityList));
/*      */     
/*  500 */     return entityList;
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
/*      */   private void generateRFANumber(EntityItem paramEntityItem, boolean paramBoolean) throws SQLException, MiddlewareException, EANBusinessRuleException {
/*  525 */     String str1 = "QSMRFANUMBER";
/*      */     
/*  527 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(str1);
/*  528 */     if (eANMetaAttribute == null) {
/*  529 */       addDebug("generateRFANumber: " + str1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/*  533 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, str1, "", null, false);
/*  534 */     addDebug("generateRFANumber: current RFAnum: " + str2);
/*  535 */     if (str2 == null) {
/*  536 */       paramBoolean = true;
/*      */     }
/*      */ 
/*      */     
/*  540 */     if (paramBoolean) {
/*  541 */       str2 = getRFANumber();
/*  542 */       addDebug("generateRFANumber: created new RFAnum: " + str2);
/*      */       
/*  544 */       Vector<Text> vector = null;
/*      */       
/*  546 */       for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/*  547 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/*  548 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/*  549 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/*  550 */           vector = returnEntityKey.m_vctAttributes;
/*      */           break;
/*      */         } 
/*      */       } 
/*  554 */       if (vector == null) {
/*      */         
/*  556 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/*  557 */         vector = new Vector();
/*  558 */         returnEntityKey.m_vctAttributes = vector;
/*  559 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */       } 
/*      */       
/*  562 */       Text text = new Text(getProfile().getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), str1, str2, 1, this.m_cbOn);
/*  563 */       vector.addElement(text);
/*      */ 
/*      */       
/*  566 */       EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*  567 */       EANMetaAttribute eANMetaAttribute1 = entityGroup.getMetaAttribute(str1);
/*  568 */       TextAttribute textAttribute = new TextAttribute((EANDataFoundation)paramEntityItem, this.m_prof, (MetaTextAttribute)eANMetaAttribute1);
/*  569 */       textAttribute.put(str2);
/*  570 */       paramEntityItem.putAttribute((EANAttribute)textAttribute);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateOutputDelta() throws Exception {
/*  581 */     String str = getTime1DTS();
/*      */ 
/*      */     
/*  584 */     Profile profile = switchRole("QSMRPT");
/*  585 */     profile.setValOnEffOn(str, str);
/*      */ 
/*      */     
/*  588 */     EntityList entityList = getEntityListForDiff(profile, this.qsmAbr.getVeName());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  593 */     Hashtable hashtable = ((ExtractActionItem)this.listT2.getParentActionItem()).generateVESteps(this.m_db, this.listT2.getProfile(), 
/*  594 */         getEntityType());
/*      */     
/*  596 */     boolean bool1 = (this.abr_debuglvl > 3) ? true : false;
/*      */     
/*  598 */     DiffVE diffVE = new DiffVE(entityList, this.listT2, hashtable, bool1);
/*  599 */     addDebug("hshMap: " + hashtable);
/*  600 */     addDebug("time1 flattened VE: " + diffVE.getPriorDiffVE());
/*  601 */     addDebug("time2 flattened VE: " + diffVE.getCurrentDiffVE());
/*      */ 
/*      */     
/*  604 */     Vector<DiffEntity> vector = diffVE.diffVE();
/*  605 */     if (bool1) {
/*  606 */       addDebug("diffVE debug info:" + NEWLINE + diffVE.getDebug());
/*      */     }
/*      */     
/*  609 */     addDebug("diffVE flattened VE: " + vector);
/*      */ 
/*      */     
/*  612 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/*  613 */     boolean bool2 = false; byte b;
/*  614 */     for (b = 0; b < vector.size(); b++) {
/*  615 */       DiffEntity diffEntity = vector.elementAt(b);
/*      */       
/*  617 */       bool2 = (bool2 || diffEntity.isChanged()) ? true : false;
/*      */ 
/*      */ 
/*      */       
/*  621 */       hashtable1.put(diffEntity.getKey(), diffEntity);
/*      */       
/*  623 */       String str1 = diffEntity.getEntityType();
/*  624 */       if (diffEntity.isRoot()) {
/*  625 */         str1 = "ROOT";
/*      */       }
/*  627 */       Vector<DiffEntity> vector1 = (Vector)hashtable1.get(str1);
/*  628 */       if (vector1 == null) {
/*  629 */         vector1 = new Vector();
/*  630 */         hashtable1.put(str1, vector1);
/*      */       } 
/*  632 */       vector1.add(diffEntity);
/*      */     } 
/*      */     
/*  635 */     if (bool2) {
/*      */       
/*  637 */       for (b = 0; b < this.listT2.getEntityGroupCount(); b++) {
/*  638 */         String str1 = this.listT2.getEntityGroup(b).getEntityType();
/*  639 */         Vector vector1 = (Vector)hashtable1.get(str1);
/*  640 */         if (vector1 == null) {
/*  641 */           vector1 = new Vector();
/*  642 */           hashtable1.put(str1, vector1);
/*      */         } 
/*      */       } 
/*      */       
/*  646 */       generateOutputDelta(hashtable1);
/*  647 */       this.outputType = "Chgs";
/*      */     } else {
/*  649 */       addDebug("No changes found in the VEs between T1 and T2");
/*      */     } 
/*      */ 
/*      */     
/*  653 */     entityList.dereference();
/*  654 */     hashtable.clear();
/*  655 */     vector.clear();
/*  656 */     diffVE.dereference();
/*  657 */     hashtable1.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateOutputDelta(Hashtable paramHashtable) {
/*  666 */     int i = this.qsmAbr.getColumnCount();
/*  667 */     this.wb = new HSSFWorkbook();
/*      */ 
/*      */     
/*  670 */     byte b1 = 0;
/*  671 */     HSSFSheet hSSFSheet = this.wb.createSheet(getEntityType());
/*  672 */     this.wb.setSheetName(this.wb.getSheetIndex(getEntityType()), getEntityType());
/*      */     
/*  674 */     HSSFRow hSSFRow = hSSFSheet.createRow(b1);
/*  675 */     for (byte b2 = 0; b2 < i; b2++) {
/*  676 */       HSSFCell hSSFCell = hSSFRow.createCell((short)b2);
/*  677 */       hSSFCell.setCellType(1);
/*  678 */       hSSFCell.setCellValue((RichTextString)new HSSFRichTextString(this.qsmAbr.getColumnLabel(b2)));
/*      */     } 
/*      */     
/*  681 */     HashSet<String> hashSet = new HashSet();
/*      */     
/*  683 */     for (byte b3 = 0; b3 < GEOS.length; b3++) {
/*  684 */       addDebug("generateOutputDelta: looking for GEO " + GEOS[b3]);
/*  685 */       Vector<XLRow> vector = this.qsmAbr.getRowItems(null, paramHashtable, GEOS[b3], this);
/*      */       
/*  687 */       if (vector.size() > 0) {
/*      */         
/*  689 */         byte b = 0; while (true) { XLRow xLRow; if (b < vector.size())
/*  690 */           { xLRow = vector.elementAt(b);
/*  691 */             boolean bool = false; short s;
/*  692 */             for (s = 0; s < i; s = (short)(s + 1)) {
/*  693 */               if (this.qsmAbr.isChanged(xLRow.getRowType(), xLRow.getItemTbl(), s)) {
/*  694 */                 bool = true;
/*      */                 break;
/*      */               } 
/*      */             } 
/*  698 */             if (bool)
/*  699 */             { if (hashSet.contains(xLRow.getRowKey()))
/*      */               
/*  701 */               { addDebug("generateOutputDelta: already added " + xLRow.getRowKey());
/*  702 */                 xLRow.dereference(); }
/*      */               else
/*      */               
/*  705 */               { hashSet.add(xLRow.getRowKey());
/*  706 */                 b1++;
/*  707 */                 hSSFRow = hSSFSheet.createRow(b1);
/*  708 */                 for (s = 0; s < i; s = (short)(s + 1)) {
/*  709 */                   HSSFCell hSSFCell = hSSFRow.createCell(s);
/*  710 */                   this.qsmAbr.setColumnValue(hSSFCell, xLRow.getRowType(), xLRow.getItemTbl(), s);
/*      */                 } 
/*      */                 
/*  713 */                 xLRow.dereference(); }  continue; }  } else { break; }  xLRow.dereference(); b++; }
/*      */         
/*  715 */         vector.clear();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  720 */     hashSet.clear();
/*  721 */     hashSet = null;
/*      */     
/*  723 */     if (b1 == 0) {
/*  724 */       addDebug("No changes of interest found to create rows");
/*  725 */       this.wb = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateOutputAll(EntityItem paramEntityItem) throws IOException {
/*  736 */     if (this.createSS) {
/*  737 */       generateSSOutputAll();
/*      */     }
/*      */     
/*  740 */     if (this.createFF) {
/*  741 */       generateFlatFile(paramEntityItem);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateSSOutputAll() throws IOException {
/*  750 */     this.outputType = "All";
/*      */ 
/*      */     
/*  753 */     addDebug("generateSSOutputAll: creating SS");
/*  754 */     byte b1 = 0;
/*  755 */     int i = this.qsmAbr.getColumnCount();
/*      */     
/*  757 */     this.wb = new HSSFWorkbook();
/*  758 */     HSSFSheet hSSFSheet = this.wb.createSheet(getEntityType());
/*  759 */     this.wb.setSheetName(this.wb.getSheetIndex(getEntityType()), getEntityType());
/*      */     
/*  761 */     HSSFRow hSSFRow = hSSFSheet.createRow(b1);
/*  762 */     for (byte b2 = 0; b2 < i; b2++) {
/*  763 */       HSSFCell hSSFCell = hSSFRow.createCell((short)b2);
/*  764 */       hSSFCell.setCellType(1);
/*  765 */       hSSFCell.setCellValue((RichTextString)new HSSFRichTextString(this.qsmAbr.getColumnLabel(b2)));
/*      */     } 
/*      */     
/*  768 */     HashSet<String> hashSet = new HashSet();
/*      */     
/*  770 */     for (byte b3 = 0; b3 < GEOS.length; b3++) {
/*  771 */       addDebug("generateOutputAllss: looking for GEO " + GEOS[b3]);
/*  772 */       Vector<XLRow> vector = this.qsmAbr.getRowItems(this.listT2, null, GEOS[b3], this);
/*      */       
/*  774 */       if (vector.size() > 0) {
/*  775 */         for (byte b = 0; b < vector.size(); b++) {
/*  776 */           XLRow xLRow = vector.elementAt(b);
/*  777 */           if (hashSet.contains(xLRow.getRowKey())) {
/*      */             
/*  779 */             addDebug("generateOutputAllss: already added " + xLRow.getRowKey());
/*  780 */             xLRow.dereference();
/*      */           } else {
/*      */             
/*  783 */             hashSet.add(xLRow.getRowKey());
/*  784 */             b1++;
/*  785 */             hSSFRow = hSSFSheet.createRow(b1); short s;
/*  786 */             for (s = 0; s < i; s = (short)(s + 1)) {
/*  787 */               HSSFCell hSSFCell = hSSFRow.createCell(s);
/*  788 */               this.qsmAbr.setColumnValue(hSSFCell, xLRow.getRowType(), xLRow.getItemTbl(), s);
/*      */             } 
/*  790 */             xLRow.dereference();
/*      */           } 
/*  792 */         }  vector.clear();
/*      */       } 
/*      */     } 
/*      */     
/*  796 */     hashSet.clear();
/*  797 */     hashSet = null;
/*      */     
/*  799 */     if (b1 == 0) {
/*  800 */       addDebug("No data found to create rows in ss");
/*  801 */       this.wb = null;
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
/*      */   private void setFileName() {
/*  814 */     String str1 = ABRServerProperties.getFilePrefix(this.m_abri.getABRCode());
/*  815 */     StringBuffer stringBuffer = new StringBuffer(str1.trim());
/*  816 */     String str2 = getTime2DTS();
/*      */     
/*  818 */     str2 = str2.replace(' ', '_');
/*  819 */     stringBuffer.append(str2 + ".txt");
/*  820 */     String str3 = getABRItem().getDirectory();
/*  821 */     if (!str3.endsWith("/")) {
/*  822 */       str3 = str3 + "/";
/*      */     }
/*  824 */     this.ffFileName = stringBuffer.toString();
/*  825 */     this.ffPathName = str3 + this.ffFileName;
/*  826 */     addDebug("ffPathName: " + this.ffPathName + " ffFileName: " + this.ffFileName);
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
/*      */   private void generateFlatFile(EntityItem paramEntityItem) throws IOException {
/*  857 */     setFileName();
/*      */     
/*  859 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*      */ 
/*      */ 
/*      */     
/*  863 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*      */ 
/*      */     
/*  866 */     String str1 = getTime2DTS();
/*  867 */     String str2 = str1.substring(0, 10);
/*  868 */     String str3 = str1.substring(11, 19);
/*  869 */     String str4 = str1.substring(19);
/*  870 */     str3 = str3.replace('.', ':');
/*  871 */     String str5 = str2 + " " + str3 + str4;
/*      */ 
/*      */     
/*  874 */     outputStreamWriter.write("1 " + str5 + " " + this.qsmAbr.getRowOne(paramEntityItem) + NEWLINE);
/*  875 */     byte b1 = 0;
/*  876 */     int i = this.qsmAbr.getColumnCount();
/*      */     
/*  878 */     StringBuffer stringBuffer = new StringBuffer();
/*  879 */     addDebug("generateFlatFile: creating FF");
/*      */     
/*  881 */     stringBuffer.append("2 " + this.qsmAbr.getRowTwoPrefix());
/*      */     
/*  883 */     for (byte b2 = 0; b2 < i; b2++) {
/*  884 */       stringBuffer.append(" " + XLColumn.formatToWidth(this.qsmAbr.getFFColumnLabel(b2), this.qsmAbr
/*  885 */             .getColumnWidth(b2)));
/*      */     }
/*  887 */     stringBuffer.append(NEWLINE);
/*  888 */     outputStreamWriter.write(stringBuffer.toString());
/*  889 */     outputStreamWriter.flush();
/*      */     
/*  891 */     HashSet<String> hashSet = new HashSet();
/*      */     
/*  893 */     for (byte b3 = 0; b3 < GEOS.length; b3++) {
/*  894 */       addDebug("generateOutputAllff: looking for GEO " + GEOS[b3]);
/*  895 */       Vector<XLRow> vector = this.qsmAbr.getRowItems(this.listT2, null, GEOS[b3], this);
/*      */       
/*  897 */       if (vector.size() > 0) {
/*  898 */         for (byte b = 0; b < vector.size(); b++) {
/*  899 */           XLRow xLRow = vector.elementAt(b);
/*  900 */           if (hashSet.contains(xLRow.getRowKey())) {
/*      */             
/*  902 */             addDebug("generateOutputAllff: already added " + xLRow.getRowKey());
/*  903 */             xLRow.dereference();
/*      */           } else {
/*      */             
/*  906 */             hashSet.add(xLRow.getRowKey());
/*  907 */             stringBuffer = new StringBuffer();
/*  908 */             stringBuffer.append("5 " + str5);
/*  909 */             b1++; short s;
/*  910 */             for (s = 0; s < i; s = (short)(s + 1)) {
/*  911 */               stringBuffer.append(" " + this.qsmAbr
/*  912 */                   .getColumnValue(xLRow.getRowType(), xLRow.getItemTbl(), s));
/*      */             }
/*  914 */             stringBuffer.append(NEWLINE);
/*  915 */             outputStreamWriter.write(stringBuffer.toString());
/*  916 */             outputStreamWriter.flush();
/*      */             
/*  918 */             xLRow.dereference();
/*      */           } 
/*  920 */         }  vector.clear();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  925 */     hashSet.clear();
/*  926 */     hashSet = null;
/*      */     
/*  928 */     if (b1 == 0) {
/*  929 */       addDebug("No data found to create rows in text file");
/*  930 */       this.ffFileName = null;
/*      */     } 
/*      */     
/*  933 */     outputStreamWriter.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ftpFile() throws IOException, InterruptedException {
/*  943 */     String str1 = ABRServerProperties.getScript(this.m_abri.getABRCode());
/*  944 */     String str2 = ABRServerProperties.getFtpUserid(this.m_abri.getABRCode());
/*  945 */     String str3 = ABRServerProperties.getFtpPassword(this.m_abri.getABRCode());
/*  946 */     String str4 = ABRServerProperties.getFtpHost(this.m_abri.getABRCode());
/*  947 */     if (str1.length() > 0) {
/*  948 */       addDebug("FTP of " + this.ffPathName + " will be done with a script: " + str1);
/*  949 */       this.m_db.debug(2, this + " FTP of " + this.ffPathName + " will be done with a script: " + str1 + " to " + str4 + " " + str2);
/*      */       
/*  951 */       String str = ABRUtil.runScript(str1 + " " + str4 + " " + str2 + " " + str3 + " " + this.ffPathName);
/*  952 */       addDebug("script results: " + str);
/*  953 */       this.m_db.debug(2, this + " script results " + str);
/*      */     
/*      */     }
/*  956 */     else if (str2.length() > 0 && str3.length() > 0 && str4.length() > 0) {
/*  957 */       String str = str2.trim() + ":" + str3.trim() + "@" + str4.trim();
/*  958 */       addDebug("ftpFile urlStr: " + str);
/*      */       
/*  960 */       if (!str.endsWith("/")) {
/*  961 */         str = str + "/";
/*      */       }
/*      */       
/*  964 */       str = str + this.ffFileName;
/*  965 */       addDebug("ftpFile writing " + this.ffPathName + " to urlStr: " + str);
/*  966 */       this.m_db.debug(2, this + " ftpFile writing " + this.ffPathName + " to urlStr: " + str);
/*  967 */       ABRUtil.ftpWriteFile(str, this.ffPathName);
/*      */     } else {
/*  969 */       addDebug("ftpFile.urlStr was not valid user:" + str2 + " host:" + str4);
/*  970 */       this.m_db.debug(0, this + " ftpFile.urlStr was not valid user:" + str2 + " host:" + str4);
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
/*      */   private void outputSS(EntityItem paramEntityItem) throws IOException {
/*  982 */     ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
/*  983 */     String str = paramEntityItem.getKey() + getNow() + "_QSM" + this.outputType + ".xls";
/*  984 */     this.wb.write(byteArrayOutputStream1);
/*      */     
/*  986 */     byte[] arrayOfByte = byteArrayOutputStream1.toByteArray();
/*      */     
/*  988 */     ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
/*  989 */     ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream2);
/*  990 */     zipOutputStream.putNextEntry(new ZipEntry(str));
/*  991 */     zipOutputStream.write(arrayOfByte);
/*  992 */     zipOutputStream.closeEntry();
/*  993 */     zipOutputStream.flush();
/*  994 */     zipOutputStream.close();
/*      */     
/*  996 */     setAttachByteForDG(byteArrayOutputStream2.toByteArray());
/*  997 */     getABRItem().setFileExtension("zip");
/*      */     
/*  999 */     if (getABRItem().getKeepFile()) {
/*      */       
/*      */       try {
/* 1002 */         FileOutputStream fileOutputStream = new FileOutputStream(getABRItem().getFileName() + ".zip");
/* 1003 */         zipOutputStream = new ZipOutputStream(fileOutputStream);
/* 1004 */         zipOutputStream.putNextEntry(new ZipEntry(str));
/* 1005 */         zipOutputStream.write(arrayOfByte);
/* 1006 */         zipOutputStream.closeEntry();
/* 1007 */         zipOutputStream.flush();
/* 1008 */         zipOutputStream.close();
/* 1009 */         fileOutputStream.flush();
/* 1010 */         fileOutputStream.close();
/* 1011 */       } catch (Throwable throwable) {
/* 1012 */         System.err.println("Error while writing debug zipfile: " + throwable);
/* 1013 */         throwable.printStackTrace();
/*      */       } 
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
/*      */   private void updatePDH() throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 1027 */     addDebug(" updating PDH for vctReturnsEntityKeys: " + this.vctReturnsEntityKeys);
/* 1028 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*      */       
/*      */       try {
/* 1031 */         this.m_db.update(this.m_prof, this.vctReturnsEntityKeys, false, false);
/*      */         
/* 1033 */         for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1034 */           ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/*      */           
/* 1036 */           for (byte b1 = 0; b1 < returnEntityKey.m_vctAttributes.size(); b1++) {
/* 1037 */             Attribute attribute = returnEntityKey.m_vctAttributes.elementAt(b1);
/* 1038 */             if (attribute instanceof Text) {
/* 1039 */               EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 1040 */               EntityItem entityItem = entityGroup.getEntityItem(returnEntityKey.getEntityType() + returnEntityKey.getEntityID());
/* 1041 */               entityItem.commit(this.m_db, null);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } finally {
/*      */         
/* 1047 */         this.vctReturnsEntityKeys.clear();
/* 1048 */         this.m_db.commit();
/* 1049 */         this.m_db.freeStatement();
/* 1050 */         this.m_db.isPending("finally after updatePDH");
/*      */       } 
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
/*      */   private void getChangeTimes() throws MiddlewareRequestException {
/* 1065 */     String str1 = "0050";
/* 1066 */     String str2 = this.m_abri.getABRCode();
/* 1067 */     this.dtsVct = new Vector(1);
/* 1068 */     Vector<ChangeHistoryItem> vector = new Vector(1);
/*      */     
/* 1070 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1071 */     addDebug("getChangeTimes entered for " + entityItem.getKey() + " " + str2 + " flag: " + str1);
/* 1072 */     EANAttribute eANAttribute = entityItem.getAttribute(str2);
/* 1073 */     if (eANAttribute != null) {
/*      */       
/* 1075 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = new AttributeChangeHistoryGroup(this.m_db, this.m_elist.getProfile(), eANAttribute);
/* 1076 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 1077 */         int i; for (i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1078 */           vector.add(attributeChangeHistoryGroup.getChangeHistoryItem(i));
/*      */         }
/*      */         
/* 1081 */         Collections.sort(vector, new ChiComparator());
/* 1082 */         label39: for (i = 0; i < vector.size(); i++) {
/* 1083 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)vector.elementAt(i);
/* 1084 */           addDebug("getChangeTimes " + str2 + "[" + i + "] isActive: " + attributeChangeHistoryItem
/* 1085 */               .isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1086 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*      */           
/* 1088 */           if (str1.equals(attributeChangeHistoryItem.getFlagCode())) {
/* 1089 */             this.dtsVct.add(attributeChangeHistoryItem.getChangeDate());
/*      */             
/* 1091 */             for (int j = i + 1; j < vector.size(); j++) {
/* 1092 */               attributeChangeHistoryItem = (AttributeChangeHistoryItem)vector.elementAt(j);
/* 1093 */               addDebug("getChangeTimes2 " + str2 + "[" + j + "] isActive: " + attributeChangeHistoryItem
/* 1094 */                   .isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1095 */                   .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*      */               
/* 1097 */               if ("0030".equals(attributeChangeHistoryItem.getFlagCode())) {
/* 1098 */                 j++;
/*      */                 
/* 1100 */                 if (j < vector.size()) {
/* 1101 */                   attributeChangeHistoryItem = (AttributeChangeHistoryItem)vector.elementAt(j);
/* 1102 */                   addDebug("getChangeTimes3 " + str2 + "[" + j + "] isActive: " + attributeChangeHistoryItem
/* 1103 */                       .isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1104 */                       .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1105 */                   this.dtsVct.add(attributeChangeHistoryItem.getChangeDate()); break label39;
/*      */                 } 
/* 1107 */                 addDebug("ERROR: getChangeTimes for " + entityItem.getKey() + " " + str2 + " did not have a previous inprocess flag");
/* 1108 */                 this.dtsVct.add(attributeChangeHistoryItem.getChangeDate());
/*      */                 
/*      */                 break label39;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1116 */         vector.clear();
/*      */       } 
/* 1118 */       addDebug("getChangeTimes dts " + entityItem.getKey() + " " + this.dtsVct);
/*      */     } else {
/* 1120 */       addDebug("ERROR: getChangeTimes for " + entityItem.getKey() + " " + str2 + "  was null use current time");
/* 1121 */       this.dtsVct.add(getNow());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTime2DTS() {
/* 1128 */     return this.dtsVct.firstElement();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTime1DTS() {
/* 1134 */     String str = this.m_strEpoch;
/* 1135 */     if (this.dtsVct.size() > 1) {
/* 1136 */       str = this.dtsVct.lastElement();
/*      */     }
/* 1138 */     return str;
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
/*      */   private String getRFANumber() throws SQLException, MiddlewareException {
/* 1155 */     int i = this.m_db.getNextEntityID(getProfile(), "QSMRFANUM");
/* 1156 */     int j = i;
/*      */     
/* 1158 */     if (i > 483729407) {
/*      */       
/* 1160 */       i += 120932352;
/* 1161 */       addDebug("getRFANumber: origid:" + j + " " + Integer.toString(j, 36).toUpperCase() + " adjusted 8,9 id:" + i + " value: " + 
/* 1162 */           Integer.toString(i, 36).toUpperCase());
/*      */     } 
/* 1164 */     if (i > 846526463 && i < 906992640) {
/*      */       
/* 1166 */       i += 60466176;
/* 1167 */       addDebug("getRFANumber: origid:" + j + " " + Integer.toString(j, 36).toUpperCase() + " adjusted E id:" + i + " value: " + 
/* 1168 */           Integer.toString(i, 36).toUpperCase());
/*      */     } 
/*      */     
/* 1171 */     String str = Integer.toString(i, 36).toUpperCase();
/* 1172 */     StringBuffer stringBuffer = new StringBuffer(str);
/* 1173 */     while (stringBuffer.length() < 6) {
/* 1174 */       stringBuffer.insert(0, "0");
/*      */     }
/*      */     
/* 1177 */     return stringBuffer.toString();
/*      */   }
/*      */   private boolean createReport = true;
/*      */   protected void setNoReport() {
/* 1181 */     addDebug("Report will not be sent");
/* 1182 */     this.createReport = false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1187 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString, Object[] paramArrayOfObject) {
/* 1195 */     String str = getBundle().getString(paramString);
/* 1196 */     if (paramArrayOfObject != null) {
/* 1197 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1198 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1201 */     addOutput(str);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1206 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1212 */     addOutput(paramString);
/* 1213 */     setReturnCode(-1);
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
/*      */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 1226 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 1227 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 1230 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 1231 */     Object[] arrayOfObject = new Object[2];
/* 1232 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 1233 */     arrayOfObject[1] = this.navName;
/*      */     
/* 1235 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1244 */     String str = getBundle().getString(paramString2);
/*      */     
/* 1246 */     if (paramArrayOfObject != null) {
/* 1247 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1248 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1251 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 1261 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1271 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1274 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1275 */     if (eANList == null) {
/*      */       
/* 1277 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1278 */       eANList = entityGroup.getMetaAttribute();
/* 1279 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1281 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1283 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1284 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1285 */       if (b + 1 < eANList.size()) {
/* 1286 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1290 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1300 */     return "1.14";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1309 */     return "QSMRPTABRSTATUS";
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
/*      */   private void setFlagValue(Profile paramProfile, String paramString1, String paramString2, EntityItem paramEntityItem) {
/* 1322 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 1323 */     addDebug("setFlagValue entered " + paramEntityItem.getKey() + " for " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */ 
/*      */     
/* 1327 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1328 */     if (eANMetaAttribute == null) {
/* 1329 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 1330 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 1331 */           .getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1335 */     if (paramString2 != null) {
/*      */ 
/*      */ 
/*      */       
/* 1339 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/* 1340 */       if (paramString2.equals(str)) {
/* 1341 */         addDebug("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/* 1342 */         logMessage("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/*      */         
/*      */         return;
/*      */       } 
/* 1346 */       Vector<SingleFlag> vector = null;
/*      */       
/* 1348 */       for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1349 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1350 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 1351 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 1352 */           vector = returnEntityKey.m_vctAttributes;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1356 */       if (vector == null) {
/* 1357 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 1358 */         vector = new Vector();
/* 1359 */         returnEntityKey.m_vctAttributes = vector;
/* 1360 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */       } 
/*      */       
/* 1363 */       SingleFlag singleFlag = new SingleFlag(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */ 
/*      */       
/* 1366 */       vector.addElement(singleFlag);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private class ChiComparator
/*      */     implements Comparator
/*      */   {
/*      */     private ChiComparator() {}
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/* 1377 */       ChangeHistoryItem changeHistoryItem1 = (ChangeHistoryItem)param1Object1;
/* 1378 */       ChangeHistoryItem changeHistoryItem2 = (ChangeHistoryItem)param1Object2;
/* 1379 */       return changeHistoryItem2.getChangeDate().compareTo(changeHistoryItem1.getChangeDate());
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMRPTABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */