/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.ParseException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class QSMWITHDRAWALFULLABR
/*      */   extends PokBaseABR
/*      */ {
/*   42 */   private StringBuffer rptSb = new StringBuffer();
/*   43 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   44 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*   46 */   private ResourceBundle rsBundle = null;
/*   47 */   private Hashtable metaTbl = new Hashtable<>();
/*   48 */   private String navName = "";
/*      */   
/*   50 */   private String ffFileName = null;
/*   51 */   private String ffPathName = null;
/*   52 */   private String ffFTPPathName = null;
/*   53 */   private String dir = null;
/*   54 */   private String dirDest = null;
/*   55 */   private final String QSMRPTPATH = "_rptpath";
/*   56 */   private final String QSMGENPATH = "_genpath";
/*   57 */   private final String QSMFTPPATH = "_ftppath";
/*   58 */   private int abr_debuglvl = 0;
/*      */   
/*      */   private static final String CREFINIPATH = "_inipath";
/*      */   private static final String FTPSCRPATH = "_script";
/*      */   private static final String TARGETFILENAME = "_wdtargetfilename";
/*      */   private static final String LOGPATH = "_logpath";
/*      */   private static final String BACKUPPATH = "_backuppath";
/*      */   private static final String FILEPREFIX = "_wdfilePrefix";
/*   66 */   private String lineStr = "";
/*      */   private static final String FAILD = "FAILD";
/*      */   private static final String IFTYPE = "IFTYPE";
/*      */   private static final String IOPUCTY = "IOPUCTY";
/*      */   private static final String ISLMPRN = "ISLMPRN";
/*      */   private static final String DSLMOPD = "DSLMOPD";
/*      */   private static final String DSLMWDN = "DSLMWDN";
/*      */   private static final String DUSALRW = "DUSALRW";
/*      */   private static final String QSMEDMW = "QSMEDMW";
/*      */   private static final String CPDAA = "CPDAA";
/*      */   private static final String BLANK = "BLANK";
/*      */   private static final String BLANK1 = "BLANK1";
/*      */   private static final String BLANK2 = "BLANK2";
/*   79 */   private String abrcode = "";
/*      */   
/*      */   private EntityItem rootEntity;
/*      */   private static final Hashtable COLUMN_LENGTH;
/*      */   private static final String ISLMTYP = "ISLMTYP";
/*      */   private static final String ISLMMOD = "ISLMMOD";
/*      */   private static final String ISLMFTR = "ISLMFTR";
/*      */   private static final String ISLMRFA = "ISLMRFA";
/*   87 */   private static final List geoWWList = Collections.unmodifiableList(new ArrayList()
/*      */       {
/*      */       
/*      */       });
/*      */ 
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
/*  102 */     COLUMN_LENGTH = new Hashtable<>();
/*  103 */     COLUMN_LENGTH.put("IFTYPE", "20");
/*  104 */     COLUMN_LENGTH.put("IOPUCTY", "3");
/*  105 */     COLUMN_LENGTH.put("ISLMPRN", "14");
/*  106 */     COLUMN_LENGTH.put("DSLMOPD", "10");
/*  107 */     COLUMN_LENGTH.put("DSLMWDN", "10");
/*  108 */     COLUMN_LENGTH.put("QSMEDMW", "10");
/*  109 */     COLUMN_LENGTH.put("CPDAA", "1");
/*  110 */     COLUMN_LENGTH.put("DUSALRW", "10");
/*  111 */     COLUMN_LENGTH.put("ISLMTYP", "4");
/*  112 */     COLUMN_LENGTH.put("ISLMMOD", "3");
/*  113 */     COLUMN_LENGTH.put("ISLMFTR", "6");
/*  114 */     COLUMN_LENGTH.put("BLANK", "1");
/*  115 */     COLUMN_LENGTH.put("ISLMRFA", "6");
/*  116 */     COLUMN_LENGTH.put("BLANK1", "32");
/*  117 */     COLUMN_LENGTH.put("BLANK2", "42");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/*  124 */     return this.rsBundle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processThis(QSMFULLABRSTATUS paramQSMFULLABRSTATUS, Profile paramProfile, Database paramDatabase, String paramString, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws Exception {
/*      */     try {
/*  135 */       this.m_prof = paramProfile;
/*  136 */       this.m_db = paramDatabase;
/*  137 */       this.rootEntity = paramEntityItem;
/*  138 */       this.rptSb = paramStringBuffer;
/*  139 */       this.abrcode = paramString;
/*      */       
/*  141 */       setReturnCode(0);
/*      */       
/*  143 */       this.m_elist = getEntityList(getT002ModelVEName());
/*      */       
/*  145 */       if (this.m_elist.getEntityGroupCount() > 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  154 */         generateFlatFile(paramEntityItem);
/*  155 */         exeFtpShell(this.ffPathName);
/*      */       }
/*      */     
/*  158 */     } catch (IOException iOException) {
/*      */       
/*  160 */       iOException.printStackTrace();
/*  161 */     } catch (ParseException parseException) {
/*      */       
/*  163 */       parseException.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setFileName(EntityItem paramEntityItem) {
/*  168 */     String str = ABRServerProperties.getValue(this.abrcode, "_wdfilePrefix", null);
/*      */ 
/*      */     
/*  171 */     StringBuffer stringBuffer = new StringBuffer(str.trim());
/*      */     
/*      */     try {
/*  174 */       DatePackage datePackage = this.m_db.getDates();
/*  175 */       String str1 = datePackage.getNow();
/*      */       
/*  177 */       str1 = str1.replace(' ', '_');
/*  178 */       stringBuffer.append(paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "_");
/*  179 */       stringBuffer.append(str1 + ".txt");
/*  180 */       this.dir = ABRServerProperties.getValue(this.abrcode, "_genpath", "/Dgq");
/*  181 */       if (!this.dir.endsWith("/")) {
/*  182 */         this.dir += "/";
/*      */       }
/*  184 */       this.ffFileName = stringBuffer.toString();
/*  185 */       this.ffPathName = this.dir + this.ffFileName;
/*  186 */     } catch (MiddlewareException middlewareException) {
/*      */       
/*  188 */       middlewareException.printStackTrace();
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
/*      */   private void generateFlatFile(EntityItem paramEntityItem) throws IOException, SQLException, MiddlewareException, ParseException {
/*  204 */     FileChannel fileChannel1 = null;
/*  205 */     FileChannel fileChannel2 = null;
/*      */ 
/*      */     
/*  208 */     setFileName(paramEntityItem);
/*  209 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*  210 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*      */ 
/*      */ 
/*      */     
/*  214 */     createT002Model(paramEntityItem, outputStreamWriter);
/*  215 */     createT006Feature(paramEntityItem, outputStreamWriter);
/*  216 */     createT020NPMesUpgrade(paramEntityItem, outputStreamWriter);
/*  217 */     createT632TypeModelFeatureRelation(paramEntityItem, outputStreamWriter);
/*      */     
/*  219 */     outputStreamWriter.close();
/*      */     
/*  221 */     this.dirDest = ABRServerProperties.getValue(this.abrcode, "_ftppath", "/Dgq");
/*  222 */     if (!this.dirDest.endsWith("/")) {
/*  223 */       this.dirDest += "/";
/*      */     }
/*      */     
/*  226 */     this.ffFTPPathName = this.dirDest + this.ffFileName;
/*  227 */     addDebug("******* " + this.ffFTPPathName);
/*      */     
/*      */     try {
/*  230 */       fileChannel1 = (new FileInputStream(this.ffPathName)).getChannel();
/*  231 */       fileChannel2 = (new FileOutputStream(this.ffFTPPathName)).getChannel();
/*  232 */       fileChannel2.transferFrom(fileChannel1, 0L, fileChannel1.size());
/*      */     } finally {
/*  234 */       fileChannel1.close();
/*  235 */       fileChannel2.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT002Model(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException {
/*  246 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*  247 */     String str1 = "";
/*  248 */     String str2 = "";
/*  249 */     boolean bool = false;
/*      */     
/*  251 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */       
/*  253 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  255 */       str1 = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/*  256 */       str2 = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/*  257 */       if (str2.equals("EPIC")) {
/*  258 */         bool = true;
/*      */       }
/*      */ 
/*      */       
/*  262 */       if (str1.equals("Planned Availability") || str1.equals("End of Service") || str1
/*  263 */         .equals("Last Order")) {
/*  264 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/*      */         
/*  266 */         if (eANFlagAttribute != null) {
/*  267 */           if (eANFlagAttribute.isSelected("6199")) {
/*  268 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific", str1, bool);
/*      */           }
/*  270 */           if (eANFlagAttribute.isSelected("6200")) {
/*  271 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North", str1, bool);
/*      */           }
/*      */           
/*  274 */           if (eANFlagAttribute.isSelected("6198")) {
/*  275 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa", str1, bool);
/*      */           }
/*      */           
/*  278 */           if (eANFlagAttribute.isSelected("6204")) {
/*  279 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America", str1, bool);
/*      */           }
/*  281 */           if (eANFlagAttribute.isSelected("6221")) {
/*  282 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only", str1, bool);
/*      */           }
/*      */         } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT002ModelRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString1, String paramString2, boolean paramBoolean) throws IOException {
/*  307 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "MODELAVAIL", "MODEL");
/*  308 */     addDebug("availmodel=" + paramEntityItem2);
/*      */     
/*  310 */     for (byte b = 0; b < vector.size(); b++) {
/*  311 */       StringBuffer stringBuffer = new StringBuffer();
/*  312 */       String str1 = "";
/*  313 */       String str2 = "";
/*  314 */       String str3 = "";
/*  315 */       String str4 = "";
/*  316 */       String str5 = "";
/*  317 */       String str6 = "";
/*  318 */       String str7 = "";
/*  319 */       String str8 = "";
/*      */       
/*  321 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/*  323 */       EntityItem entityItem2 = null;
/*      */       
/*  325 */       stringBuffer.append(getValue("IFTYPE", "M=(CHK&UPG)T001"));
/*  326 */       if (paramString1.equals("Latin America")) {
/*  327 */         str1 = "601";
/*  328 */         str8 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  329 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/*  330 */         str1 = "999";
/*  331 */         str8 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  332 */       } else if (paramString1.equals("Asia Pacific")) {
/*  333 */         str1 = "872";
/*  334 */         str8 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  335 */       } else if (paramString1.equals("US Only")) {
/*  336 */         str1 = "897";
/*  337 */         str8 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*  338 */       } else if (paramString1.equals("Canada and Caribbean North")) {
/*  339 */         str1 = "649";
/*  340 */         str8 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*      */       } 
/*  342 */       stringBuffer.append(getValue("IOPUCTY", str1));
/*  343 */       String str9 = PokUtils.getAttributeValue(entityItem1, "MACHTYPEATR", "", "");
/*  344 */       str9 = str9 + PokUtils.getAttributeValue(entityItem1, "MODELATR", "", "");
/*  345 */       stringBuffer.append(getValue("ISLMPRN", str9));
/*      */       
/*  347 */       str7 = PokUtils.getAttributeValue(entityItem1, "WTHDRWEFFCTVDATE", "", "");
/*      */       
/*  349 */       if (str7 != null) {
/*  350 */         if (str7.equals("")) {
/*  351 */           str4 = "2050-12-31";
/*      */         } else {
/*  353 */           str4 = str7;
/*      */         } 
/*      */       } else {
/*  356 */         str4 = "2050-12-31";
/*      */       } 
/*      */       
/*  359 */       entityItem2 = searchForAvailType(entityItem1, "Last Order");
/*  360 */       if (entityItem2 != null) {
/*  361 */         str5 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/*  362 */         str6 = "O";
/*      */       } else {
/*  364 */         str5 = "2050-12-31";
/*  365 */         str6 = "N";
/*      */       } 
/*      */       
/*  368 */       entityItem2 = searchForAvailType(entityItem1, "End of Service");
/*  369 */       if (entityItem2 != null) {
/*  370 */         str2 = PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false);
/*  371 */         str3 = PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false);
/*      */       } else {
/*  373 */         str2 = "2050-12-31";
/*  374 */         str3 = "2050-12-31";
/*      */       } 
/*      */       
/*  377 */       stringBuffer.append(getValue("DSLMWDN", str4));
/*  378 */       stringBuffer.append(getValue("QSMEDMW", str2));
/*  379 */       stringBuffer.append(getValue("DSLMOPD", str5));
/*  380 */       stringBuffer.append(getValue("DUSALRW", str3));
/*  381 */       stringBuffer.append(getValue("CPDAA", str6));
/*  382 */       stringBuffer.append(getValue("BLANK", " "));
/*  383 */       stringBuffer.append(getValue("ISLMRFA", str8));
/*      */       
/*  385 */       stringBuffer.append(NEWLINE);
/*  386 */       paramOutputStreamWriter.write(stringBuffer.toString());
/*  387 */       paramOutputStreamWriter.flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private EntityItem searchForAvailType(EntityItem paramEntityItem, String paramString) {
/*  393 */     EntityItem entityItem = null;
/*      */ 
/*      */     
/*  396 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "AVAIL");
/*      */     
/*  398 */     addDebug("*****mlm searchforavail withdrawalAVAIL " + vector);
/*      */     
/*  400 */     for (byte b = 0; b < vector.size(); b++) {
/*  401 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/*  403 */       String str = PokUtils.getAttributeValue(entityItem1, "AVAILTYPE", ",", "", false);
/*  404 */       addDebug("*****mlm searchforavail withdrawal model = " + paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "avail entity type = " + entityItem1
/*  405 */           .getEntityType() + " avail type = " + str);
/*  406 */       if (paramString.equals(str)) {
/*  407 */         entityItem = entityItem1;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  412 */     return entityItem;
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
/*      */   private void createT006Feature(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/*  430 */     String str = "";
/*  431 */     boolean bool = false;
/*      */     
/*  433 */     this.m_elist = getEntityList(getT006ProdstructVEName());
/*      */     
/*  435 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*  436 */     int i = entityGroup.getEntityItemCount();
/*      */     
/*  438 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  439 */       EANFlagAttribute eANFlagAttribute = null;
/*  440 */       String str1 = "";
/*      */       
/*  442 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  444 */       str1 = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/*  445 */       str = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/*  446 */       if (str.equals("EPIC")) {
/*  447 */         bool = true;
/*      */       }
/*      */ 
/*      */       
/*  451 */       if (str1.equals("Planned Availability") || str1.equals("End of Service") || str1
/*  452 */         .equals("Last Order")) {
/*  453 */         eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/*  454 */         if (eANFlagAttribute != null) {
/*  455 */           if (eANFlagAttribute.isSelected("6199")) {
/*  456 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific", str1, bool);
/*      */           }
/*  458 */           if (eANFlagAttribute.isSelected("6200")) {
/*  459 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North", str1, bool);
/*      */           }
/*      */           
/*  462 */           if (eANFlagAttribute.isSelected("6198")) {
/*  463 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa", str1, bool);
/*      */           }
/*      */           
/*  466 */           if (eANFlagAttribute.isSelected("6204")) {
/*  467 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America", str1, bool);
/*      */           }
/*  469 */           if (eANFlagAttribute.isSelected("6221")) {
/*  470 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only", str1, bool);
/*      */           }
/*      */         } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT006FeatureRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString1, String paramString2, boolean paramBoolean) throws IOException, SQLException, MiddlewareException {
/*  496 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "OOFAVAIL", "PRODSTRUCT");
/*      */     
/*  498 */     for (byte b = 0; b < vector.size(); b++) {
/*  499 */       StringBuffer stringBuffer = new StringBuffer();
/*  500 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/*  502 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, getT006FeatureVEName());
/*      */       
/*  504 */       EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, entityItem1
/*  505 */               .getEntityType(), entityItem1.getEntityID()) });
/*      */       
/*  507 */       addDebug("EntityList for " + this.m_prof.getValOn() + " extract QSMFULL2 contains the following entities: \n" + 
/*  508 */           PokUtils.outputList(entityList));
/*      */       
/*  510 */       EntityGroup entityGroup1 = entityList.getEntityGroup("FEATURE");
/*  511 */       EntityGroup entityGroup2 = entityList.getEntityGroup("MODEL");
/*  512 */       EntityItem entityItem2 = entityGroup1.getEntityItem(0);
/*  513 */       EntityItem entityItem3 = entityGroup2.getEntityItem(0);
/*      */       
/*  515 */       stringBuffer = new StringBuffer();
/*  516 */       String str1 = "";
/*  517 */       String str2 = "";
/*  518 */       String str3 = "";
/*      */       
/*  520 */       stringBuffer.append(getValue("IFTYPE", "F=(CHKT631&UPGT005)"));
/*      */       
/*  522 */       if (paramString1.equals("Latin America")) {
/*  523 */         str1 = "601";
/*  524 */         str3 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  525 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/*  526 */         str1 = "999";
/*  527 */         str3 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  528 */       } else if (paramString1.equals("Asia Pacific")) {
/*  529 */         str1 = "872";
/*  530 */         str3 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  531 */       } else if (paramString1.equals("US Only")) {
/*  532 */         str1 = "897";
/*  533 */         str3 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*  534 */       } else if (paramString1.equals("Canada and Caribbean North")) {
/*  535 */         str1 = "649";
/*  536 */         str3 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*      */       } 
/*  538 */       stringBuffer.append(getValue("IOPUCTY", str1));
/*  539 */       String str4 = PokUtils.getAttributeValue(entityItem3, "MACHTYPEATR", ",", "", false);
/*  540 */       str4 = str4 + PokUtils.getAttributeValue(entityItem2, "FEATURECODE", ",", "", false);
/*  541 */       stringBuffer.append(getValue("ISLMPRN", str4));
/*  542 */       stringBuffer.append(getValue("BLANK2", ""));
/*  543 */       stringBuffer.append(getValue("ISLMRFA", str3));
/*      */ 
/*      */       
/*  546 */       stringBuffer.append(NEWLINE);
/*  547 */       paramOutputStreamWriter.write(stringBuffer.toString());
/*  548 */       paramOutputStreamWriter.flush();
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
/*      */   private void createT020NPMesUpgrade(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/*  566 */     this.m_elist = getEntityList(getNPMesUpgradeVEName());
/*      */     
/*  568 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/*  570 */     String str = "";
/*  571 */     boolean bool = false;
/*      */     
/*  573 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  574 */       EANFlagAttribute eANFlagAttribute = null;
/*      */       
/*  576 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  578 */       str = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/*  579 */       if (str.equals("EPIC")) {
/*  580 */         bool = true;
/*      */       }
/*      */       
/*  583 */       eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/*  584 */       if (eANFlagAttribute != null) {
/*  585 */         if (eANFlagAttribute.isSelected("6199")) {
/*  586 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific", bool);
/*      */         }
/*  588 */         if (eANFlagAttribute.isSelected("6200")) {
/*  589 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North", bool);
/*      */         }
/*  591 */         if (eANFlagAttribute.isSelected("6198")) {
/*  592 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa", bool);
/*      */         }
/*  594 */         if (eANFlagAttribute.isSelected("6204")) {
/*  595 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America", bool);
/*      */         }
/*  597 */         if (eANFlagAttribute.isSelected("6221")) {
/*  598 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only", bool);
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT020NPMesUpgradeRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString, boolean paramBoolean) throws IOException, SQLException, MiddlewareException {
/*  621 */     StringBuffer stringBuffer = new StringBuffer();
/*  622 */     String str1 = "";
/*  623 */     String str2 = "";
/*  624 */     String str3 = "";
/*      */     
/*  626 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "MODELCONVERTAVAIL", "MODELCONVERT");
/*      */     
/*  628 */     for (byte b = 0; b < vector.size(); b++) {
/*      */       
/*  630 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/*  632 */       stringBuffer.append(getValue("IFTYPE", "N=(CHK&UPG)T019"));
/*      */       
/*  634 */       if (paramString.equals("Latin America")) {
/*  635 */         str2 = "601";
/*  636 */         str3 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  637 */       } else if (paramString.equals("Europe/Middle East/Africa")) {
/*  638 */         str2 = "999";
/*  639 */         str3 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  640 */       } else if (paramString.equals("Asia Pacific")) {
/*  641 */         str2 = "872";
/*  642 */         str3 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  643 */       } else if (paramString.equals("US Only")) {
/*  644 */         str2 = "897";
/*  645 */         str3 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*  646 */       } else if (paramString.equals("Canada and Caribbean North")) {
/*  647 */         str2 = "649";
/*  648 */         str3 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*      */       } 
/*  650 */       stringBuffer.append(getValue("IOPUCTY", str2));
/*      */       
/*  652 */       str1 = PokUtils.getAttributeValue(entityItem1, "TOMACHTYPE", "", "");
/*  653 */       str1 = str1 + PokUtils.getAttributeValue(entityItem1, "TOMODEL", "", "");
/*  654 */       stringBuffer.append(getValue("ISLMPRN", str1));
/*      */       
/*  656 */       EntityItem entityItem2 = searchForAvailTypeLO(entityItem1, "Last Order");
/*      */       
/*  658 */       if (entityItem2 != null) {
/*  659 */         stringBuffer.append(getValue("DSLMWDN", PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "")));
/*      */       } else {
/*  661 */         stringBuffer.append(getValue("DSLMWDN", "2050-12-31"));
/*      */       } 
/*  663 */       stringBuffer.append(getValue("BLANK1", ""));
/*  664 */       stringBuffer.append(getValue("ISLMRFA", str3));
/*      */ 
/*      */       
/*  667 */       stringBuffer.append(NEWLINE);
/*  668 */       paramOutputStreamWriter.write(stringBuffer.toString());
/*  669 */       paramOutputStreamWriter.flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem searchForAvailTypeLO(EntityItem paramEntityItem, String paramString) {
/*  676 */     EntityItem entityItem = null;
/*      */ 
/*      */     
/*  679 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELCONVERTAVAIL", "AVAIL");
/*      */     
/*  681 */     for (byte b = 0; b < vector.size(); b++) {
/*  682 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/*  684 */       String str = PokUtils.getAttributeValue(entityItem1, "AVAILTYPE", ",", "", false);
/*      */       
/*  686 */       if (paramString.equals(str)) {
/*  687 */         entityItem = entityItem1;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  692 */     return entityItem;
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
/*      */   private void createT632TypeModelFeatureRelation(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/*  709 */     String str1 = "";
/*  710 */     String str2 = "";
/*  711 */     boolean bool = false;
/*      */     
/*  713 */     this.m_elist = getEntityList(getT006ProdstructVEName());
/*      */     
/*  715 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*  716 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  717 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */       
/*  720 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/*  721 */       if (eANFlagAttribute != null && 
/*  722 */         eANFlagAttribute.isSelected("6221")) {
/*      */         
/*  724 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*      */         
/*  726 */         String str = "";
/*  727 */         str = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/*  728 */         str2 = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/*      */         
/*  730 */         if (str2.equals("EPIC")) {
/*  731 */           bool = true;
/*      */         }
/*      */         
/*  734 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/*  735 */           StringBuffer stringBuffer = new StringBuffer();
/*  736 */           EntityItem entityItem1 = vector.elementAt(b1);
/*      */           
/*  738 */           ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, getT006FeatureVEName());
/*      */           
/*  740 */           EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, entityItem1
/*  741 */                   .getEntityType(), entityItem1.getEntityID()) });
/*      */           
/*  743 */           EntityGroup entityGroup1 = entityList.getEntityGroup("FEATURE");
/*  744 */           EntityGroup entityGroup2 = entityList.getEntityGroup("MODEL");
/*  745 */           EntityItem entityItem2 = entityGroup1.getEntityItem(0);
/*  746 */           EntityItem entityItem3 = entityGroup2.getEntityItem(0);
/*      */           
/*  748 */           stringBuffer = new StringBuffer();
/*  749 */           stringBuffer.append(getValue("IFTYPE", "T=(CHK&UPG)T631"));
/*  750 */           stringBuffer.append(getValue("IOPUCTY", "897"));
/*  751 */           str1 = getRFANumber(paramEntityItem, bool, entityItem);
/*  752 */           stringBuffer.append(getValue("ISLMTYP", PokUtils.getAttributeValue(entityItem3, "MACHTYPEATR", "", "")));
/*  753 */           stringBuffer.append(getValue("ISLMMOD", PokUtils.getAttributeValue(entityItem3, "MODELATR", "", "")));
/*  754 */           stringBuffer.append(getValue("ISLMFTR", PokUtils.getAttributeValue(entityItem2, "FEATURECODE", "", "")));
/*  755 */           stringBuffer.append(getValue("BLANK", " "));
/*  756 */           if (str.equals("Last Order")) {
/*  757 */             stringBuffer.append(PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false));
/*      */           } else {
/*  759 */             stringBuffer.append(getValue("DSLMWDN", "2050-12-31"));
/*      */           } 
/*  761 */           stringBuffer.append(getValue("BLANK1", ""));
/*  762 */           stringBuffer.append(getValue("ISLMRFA", str1));
/*      */           
/*  764 */           stringBuffer.append(NEWLINE);
/*  765 */           paramOutputStreamWriter.write(stringBuffer.toString());
/*  766 */           paramOutputStreamWriter.flush();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getRFANumber(EntityItem paramEntityItem1, boolean paramBoolean, EntityItem paramEntityItem2) {
/*      */     String str;
/*  776 */     if (paramBoolean) {
/*  777 */       str = PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "");
/*      */     } else {
/*  779 */       str = "R" + PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*      */     } 
/*      */ 
/*      */     
/*  783 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getValue(String paramString1, String paramString2) {
/*  788 */     if (paramString2 == null)
/*  789 */       paramString2 = ""; 
/*  790 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/*  791 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1).toString());
/*  792 */     if (i == j)
/*  793 */       return paramString2; 
/*  794 */     if (i > j) {
/*  795 */       return paramString2.substring(0, j);
/*      */     }
/*  797 */     return paramString2 + getBlank(j - i);
/*      */   }
/*      */   
/*      */   protected String getBlank(int paramInt) {
/*  801 */     StringBuffer stringBuffer = new StringBuffer();
/*  802 */     while (paramInt > 0) {
/*  803 */       stringBuffer.append(" ");
/*  804 */       paramInt--;
/*      */     } 
/*  806 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityList getEntityList(String paramString) throws SQLException, MiddlewareException {
/*  814 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString);
/*      */     
/*  816 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, this.rootEntity
/*  817 */             .getEntityType(), this.rootEntity.getEntityID()) });
/*      */     
/*  819 */     addDebug("EntityList for " + this.m_prof.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/*  820 */         PokUtils.outputList(entityList));
/*  821 */     return entityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT002ModelVEName() {
/*  830 */     return "QSMFULL";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT006FeatureVEName() {
/*  839 */     return "QSMFULL2";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT006ProdstructVEName() {
/*  848 */     return "QSMFULL1";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNPMesUpgradeVEName() {
/*  857 */     return "QSMFULL4";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean exeFtpShell(String paramString) {
/*  865 */     String str1 = ABRServerProperties.getValue(this.abrcode, "_script", null) + " -f " + paramString;
/*  866 */     String str2 = ABRServerProperties.getValue(this.abrcode, "_inipath", null);
/*  867 */     if (str2 != null)
/*  868 */       str1 = str1 + " -i " + str2; 
/*  869 */     if (this.dir != null)
/*  870 */       str1 = str1 + " -d " + this.dir; 
/*  871 */     String str3 = ABRServerProperties.getValue(this.abrcode, "_wdfilePrefix", null);
/*  872 */     if (str3 != null)
/*  873 */       str1 = str1 + " -p " + str3; 
/*  874 */     addDebug("file=" + str3);
/*  875 */     String str4 = ABRServerProperties.getValue(this.abrcode, "_wdtargetfilename", null);
/*  876 */     if (str4 != null)
/*  877 */       str1 = str1 + " -t " + str4; 
/*  878 */     String str5 = ABRServerProperties.getValue(this.abrcode, "_logpath", null);
/*  879 */     if (str5 != null)
/*  880 */       str1 = str1 + " -l " + str5; 
/*  881 */     String str6 = ABRServerProperties.getValue(this.abrcode, "_backuppath", null);
/*  882 */     if (str6 != null)
/*  883 */       str1 = str1 + " -b " + str6; 
/*  884 */     Runtime runtime = Runtime.getRuntime();
/*  885 */     String str7 = "";
/*  886 */     BufferedReader bufferedReader = null;
/*  887 */     BufferedInputStream bufferedInputStream = null;
/*      */     
/*      */     try {
/*  890 */       Process process = runtime.exec(str1);
/*  891 */       if (process.waitFor() != 0) {
/*  892 */         return false;
/*      */       }
/*  894 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/*  895 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/*  896 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/*  897 */         str7 = str7 + this.lineStr;
/*  898 */         if (this.lineStr.indexOf("FAILD") > -1) {
/*  899 */           return false;
/*      */         }
/*      */       } 
/*  902 */     } catch (Exception exception) {
/*  903 */       exception.printStackTrace();
/*  904 */       return false;
/*      */     } finally {
/*  906 */       if (bufferedReader != null) {
/*      */         try {
/*  908 */           bufferedReader.close();
/*  909 */           bufferedInputStream.close();
/*  910 */         } catch (IOException iOException) {
/*  911 */           iOException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*  915 */     return !(str7 == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/*  922 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString, Object[] paramArrayOfObject) {
/*  930 */     String str = getBundle().getString(paramString);
/*  931 */     if (paramArrayOfObject != null) {
/*  932 */       MessageFormat messageFormat = new MessageFormat(str);
/*  933 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/*  936 */     addOutput(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/*  943 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/*  950 */     addOutput(paramString);
/*  951 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/*  962 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*  963 */     setReturnCode(-1);
/*      */ 
/*      */     
/*  966 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/*  967 */     Object[] arrayOfObject = new Object[2];
/*  968 */     arrayOfObject[0] = entityGroup.getLongDescription();
/*  969 */     arrayOfObject[1] = this.navName;
/*      */     
/*  971 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/*  979 */     String str = getBundle().getString(paramString2);
/*      */     
/*  981 */     if (paramArrayOfObject != null) {
/*  982 */       MessageFormat messageFormat = new MessageFormat(str);
/*  983 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/*  986 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/*  995 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1004 */     return "QSMWITHDRAWALFULLABR";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMWITHDRAWALFULLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */