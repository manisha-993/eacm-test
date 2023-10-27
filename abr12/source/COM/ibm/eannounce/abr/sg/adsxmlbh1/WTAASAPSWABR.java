/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipOutputStream;
/*      */ import javax.xml.transform.TransformerConfigurationException;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WTAASAPSWABR
/*      */   extends PokBaseABR
/*      */ {
/*   60 */   private ResourceBundle rsBundle = null;
/*   61 */   private String annNumber = "";
/*   62 */   private String annDate = "";
/*   63 */   private File zipFile = null;
/*   64 */   private HashMap prodInfo = new HashMap<>();
/*   65 */   private Set modelSet = new HashSet();
/*   66 */   private Set featureSet = new HashSet();
/*   67 */   private StringBuffer rptSb = new StringBuffer();
/*   68 */   private StringBuffer xmlgenSb = new StringBuffer();
/*   69 */   private StringBuffer userxmlSb = new StringBuffer();
/*   70 */   private String t2DTS = "&nbsp;";
/*   71 */   private SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
/*   72 */   private Object[] args = (Object[])new String[10];
/*      */   public static final String RPTPATH = "_rptpath";
/*   74 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   75 */   protected static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*   77 */   private static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("WTAASAPSWABR");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*   84 */     String str1 = "";
/*      */     
/*      */     try {
/*   87 */       long l = System.currentTimeMillis();
/*      */       
/*   89 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*   92 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), 
/*   93 */           ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*   96 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  101 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  103 */               getEntityType(), getEntityID()) });
/*      */       
/*  105 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  106 */       addDebug("DEBUG: rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*      */       
/*  108 */       String str6 = getAttributeValue(entityItem, "ADOCNO");
/*  109 */       addDebug("ANNOUNCEMENT DOCNUM (CDOCNO): " + str6);
/*      */       
/*  111 */       this.annDate = getAttributeValue(entityItem, "ANNDATE");
/*  112 */       addDebug("ANNOUNCEMENT ANNDATE: " + this.annDate);
/*      */       
/*  114 */       this.annNumber = getAttributeValue(entityItem, "ANNNUMBER");
/*  115 */       addDebug("RFA number: " + this.annNumber);
/*      */       
/*  117 */       String str7 = getAttributeValue(entityItem, "ANNTYPE");
/*  118 */       addDebug("ANNOUNCEMENT type: " + str7);
/*      */       
/*  120 */       str1 = getNavigationName(entityItem);
/*      */       
/*  122 */       if (getReturnCode() == 0) {
/*  123 */         processThis(this, this.m_prof, entityItem);
/*      */         
/*  125 */         boolean bool = generateOutputFile(this.prodInfo);
/*  126 */         if (bool) {
/*      */           
/*  128 */           sendMail(this.zipFile);
/*      */           
/*  130 */           if (!getABRItem().getKeepFile() && 
/*  131 */             this.zipFile.exists()) {
/*  132 */             this.zipFile.delete();
/*  133 */             addDebug("Check the keep file is false, delete the zip file");
/*      */           } 
/*      */         } else {
/*      */           
/*  137 */           setReturnCode(-1);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  142 */       addDebug("Total Time: " + 
/*  143 */           Stopwatch.format(System.currentTimeMillis() - l));
/*      */     }
/*  145 */     catch (Throwable throwable) {
/*  146 */       StringWriter stringWriter = new StringWriter();
/*  147 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  148 */       String str7 = "<pre>{0}</pre>";
/*  149 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  150 */       setReturnCode(-3);
/*  151 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  153 */       this.args[0] = throwable.getMessage();
/*  154 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  155 */       messageFormat1 = new MessageFormat(str7);
/*  156 */       this.args[0] = stringWriter.getBuffer().toString();
/*  157 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  158 */       logError("Exception: " + throwable.getMessage());
/*  159 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*  161 */       if ("&nbsp;".equals(this.t2DTS)) {
/*  162 */         this.t2DTS = getNow();
/*      */       }
/*  164 */       setDGTitle(str1);
/*  165 */       setDGRptName(getShortClassName(getClass()));
/*  166 */       setDGRptClass(getABRCode());
/*      */       
/*  168 */       if (!isReadOnly()) {
/*  169 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  175 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  188 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */     
/*  192 */     MessageFormat messageFormat = new MessageFormat(str2);
/*  193 */     this.args[0] = getShortClassName(getClass());
/*  194 */     this.args[1] = str1;
/*  195 */     String str3 = messageFormat.format(this.args);
/*  196 */     String str4 = buildAbrHeader();
/*      */ 
/*      */     
/*  199 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("RESULT_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  200 */     this.rptSb.insert(0, str5);
/*      */     
/*  202 */     println(this.rptSb.toString());
/*  203 */     printDGSubmitString();
/*  204 */     println(EACustom.getTOUDiv());
/*  205 */     buildReportFooter();
/*      */ 
/*      */     
/*  208 */     this.m_elist.dereference();
/*  209 */     this.m_elist = null;
/*  210 */     this.rsBundle = null;
/*  211 */     this.args = null;
/*  212 */     messageFormat = null;
/*  213 */     this.userxmlSb = null;
/*  214 */     this.rptSb = null;
/*  215 */     this.xmlgenSb = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void processThis(WTAASAPSWABR paramWTAASAPSWABR, Profile paramProfile, EntityItem paramEntityItem) throws TransformerConfigurationException, SAXException, MiddlewareRequestException, SQLException, MiddlewareException, IOException {
/*  221 */     boolean bool = getModelInfo(paramEntityItem);
/*  222 */     if (bool) {
/*  223 */       getSWFeatureInfo(paramEntityItem);
/*      */     } else {
/*  225 */       getProdInfo(paramEntityItem);
/*      */     } 
/*      */ 
/*      */     
/*  229 */     long l = System.currentTimeMillis();
/*      */     
/*  231 */     addDebug("get price info Time: " + 
/*  232 */         Stopwatch.format(System.currentTimeMillis() - l));
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
/*      */   private void getProdInfo(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  244 */     ResultSet resultSet = null;
/*  245 */     PreparedStatement preparedStatement = null;
/*      */     
/*      */     try {
/*  248 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(
/*  249 */           queryForPROD(paramEntityItem));
/*  250 */       resultSet = preparedStatement.executeQuery();
/*  251 */       while (resultSet.next()) {
/*  252 */         String str3 = resultSet.getString(5);
/*  253 */         String str4 = resultSet.getString(7);
/*      */         
/*  255 */         String str5 = resultSet.getString(1);
/*  256 */         String str6 = resultSet.getString(2);
/*  257 */         String str7 = resultSet.getString(3);
/*  258 */         String str8 = resultSet.getString(4);
/*      */         
/*  260 */         String str9 = resultSet.getString(6);
/*  261 */         String str10 = resultSet.getString(8);
/*  262 */         String str11 = resultSet.getString(9);
/*  263 */         String str12 = resultSet.getString(10);
/*  264 */         String str13 = resultSet.getString(11);
/*  265 */         String str14 = resultSet.getString(12);
/*      */         
/*  267 */         String str15 = resultSet.getString(13);
/*  268 */         String str16 = resultSet.getString(14);
/*  269 */         String str17 = resultSet.getString(15);
/*  270 */         String str18 = resultSet.getString(16);
/*  271 */         String str19 = resultSet.getString(17);
/*      */ 
/*      */         
/*  274 */         String str1 = str3;
/*  275 */         String str2 = str12;
/*      */         
/*  277 */         if (this.prodInfo.get(str1) != null) {
/*  278 */           WTAASAPSWENTITY wTAASAPSWENTITY1 = (WTAASAPSWENTITY)this.prodInfo.get(str1);
/*      */           
/*  280 */           HashMap<String, ArrayList<PRODINFO>> hashMap1 = wTAASAPSWENTITY1.getPROD();
/*  281 */           if (hashMap1.get(str2) != null) {
/*  282 */             PRODINFO pRODINFO2 = new PRODINFO();
/*  283 */             pRODINFO2.setPRODUCTVRM(str10);
/*  284 */             pRODINFO2.setMACHTYPEATR(str12);
/*  285 */             pRODINFO2.setMODELATR(str11);
/*  286 */             pRODINFO2.setMODELID(str4);
/*  287 */             pRODINFO2.setMINVNAME(str13);
/*  288 */             pRODINFO2.setEDUCALLOWMHGHSCH(str14);
/*      */             
/*  290 */             pRODINFO2.setSwFeatureId(str15);
/*  291 */             pRODINFO2.setFEATURECODE(str16);
/*  292 */             pRODINFO2.setPRICEDFEATURE(str17);
/*  293 */             pRODINFO2.setCHARGEOPTION(str18);
/*  294 */             pRODINFO2.setSFINVNAME(str19);
/*      */             
/*  296 */             ArrayList<PRODINFO> arrayList2 = (ArrayList)hashMap1.get(str2);
/*  297 */             arrayList2.add(pRODINFO2); continue;
/*      */           } 
/*  299 */           PRODINFO pRODINFO1 = new PRODINFO();
/*  300 */           pRODINFO1.setPRODUCTVRM(str10);
/*  301 */           pRODINFO1.setMACHTYPEATR(str12);
/*  302 */           pRODINFO1.setMODELATR(str11);
/*  303 */           pRODINFO1.setMODELID(str4);
/*  304 */           pRODINFO1.setMINVNAME(str13);
/*  305 */           pRODINFO1.setEDUCALLOWMHGHSCH(str14);
/*      */           
/*  307 */           pRODINFO1.setSwFeatureId(str15);
/*  308 */           pRODINFO1.setFEATURECODE(str16);
/*  309 */           pRODINFO1.setPRICEDFEATURE(str17);
/*  310 */           pRODINFO1.setCHARGEOPTION(str18);
/*  311 */           pRODINFO1.setSFINVNAME(str19);
/*      */           
/*  313 */           ArrayList<PRODINFO> arrayList1 = new ArrayList();
/*  314 */           arrayList1.add(pRODINFO1);
/*  315 */           hashMap1.put(str2, arrayList1);
/*      */           
/*      */           continue;
/*      */         } 
/*  319 */         WTAASAPSWENTITY wTAASAPSWENTITY = new WTAASAPSWENTITY();
/*  320 */         wTAASAPSWENTITY.setADOCNO(str5);
/*  321 */         wTAASAPSWENTITY.setANNDATE(str6);
/*  322 */         wTAASAPSWENTITY.setANNNUMBER(str7);
/*  323 */         wTAASAPSWENTITY.setRFASHRTTITLE(str8);
/*  324 */         wTAASAPSWENTITY.setAVAILID(str3);
/*  325 */         wTAASAPSWENTITY.setEFFECTIVEDATE(str9);
/*      */         
/*  327 */         PRODINFO pRODINFO = new PRODINFO();
/*  328 */         pRODINFO.setPRODUCTVRM(str10);
/*  329 */         pRODINFO.setMODELID(str4);
/*  330 */         pRODINFO.setMACHTYPEATR(str12);
/*  331 */         pRODINFO.setMODELATR(str11);
/*  332 */         pRODINFO.setMINVNAME(str13);
/*  333 */         pRODINFO.setEDUCALLOWMHGHSCH(str14);
/*      */         
/*  335 */         pRODINFO.setSwFeatureId(str15);
/*  336 */         pRODINFO.setFEATURECODE(str16);
/*  337 */         pRODINFO.setPRICEDFEATURE(str17);
/*  338 */         pRODINFO.setCHARGEOPTION(str18);
/*  339 */         pRODINFO.setSFINVNAME(str19);
/*      */         
/*  341 */         ArrayList<PRODINFO> arrayList = new ArrayList();
/*  342 */         arrayList.add(pRODINFO);
/*  343 */         HashMap<Object, Object> hashMap = new HashMap<>();
/*  344 */         hashMap.put(str2, arrayList);
/*  345 */         wTAASAPSWENTITY.setPROD(hashMap);
/*      */         
/*  347 */         this.prodInfo.put(str1, wTAASAPSWENTITY);
/*      */       } 
/*      */     } finally {
/*      */       
/*  351 */       if (preparedStatement != null) {
/*      */         try {
/*  353 */           preparedStatement.close();
/*  354 */         } catch (SQLException sQLException) {
/*  355 */           sQLException.printStackTrace();
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
/*      */   private boolean getModelInfo(EntityItem paramEntityItem) throws MiddlewareException, SQLException {
/*  370 */     ResultSet resultSet = null;
/*  371 */     PreparedStatement preparedStatement = null;
/*  372 */     boolean bool = false;
/*      */     
/*      */     try {
/*  375 */       Connection connection = this.m_db.getPDHConnection();
/*      */       
/*  377 */       preparedStatement = connection.prepareStatement(queryForMODEL(paramEntityItem));
/*  378 */       resultSet = preparedStatement.executeQuery();
/*      */       
/*  380 */       while (resultSet.next()) {
/*  381 */         String str3 = resultSet.getString(5);
/*  382 */         String str4 = resultSet.getString(7);
/*      */         
/*  384 */         String str5 = resultSet.getString(1);
/*  385 */         String str6 = resultSet.getString(2);
/*  386 */         String str7 = resultSet.getString(3);
/*  387 */         String str8 = resultSet.getString(4);
/*      */         
/*  389 */         String str9 = resultSet.getString(6);
/*  390 */         String str10 = resultSet.getString(8);
/*      */         
/*  392 */         String str1 = str3;
/*  393 */         String str2 = str10;
/*      */         
/*  395 */         if (this.prodInfo.get(str1) != null) {
/*  396 */           WTAASAPSWENTITY wTAASAPSWENTITY = (WTAASAPSWENTITY)this.prodInfo.get(str1);
/*      */           
/*  398 */           HashMap<String, ArrayList<PRODINFO>> hashMap = wTAASAPSWENTITY.getPROD();
/*      */           
/*  400 */           PRODINFO pRODINFO = new PRODINFO();
/*  401 */           pRODINFO.setMODELID(str4);
/*      */           
/*  403 */           if (hashMap.get(str2) != null) {
/*  404 */             ArrayList<PRODINFO> arrayList = (ArrayList)hashMap.get(str2);
/*  405 */             if (!arrayList.contains(pRODINFO))
/*      */             {
/*      */               
/*  408 */               arrayList.add(pRODINFO);
/*      */             }
/*      */           } else {
/*  411 */             ArrayList<PRODINFO> arrayList = new ArrayList();
/*  412 */             arrayList.add(pRODINFO);
/*  413 */             hashMap.put(str2, arrayList);
/*      */           } 
/*  415 */           wTAASAPSWENTITY.setPROD(hashMap);
/*      */         } else {
/*  417 */           WTAASAPSWENTITY wTAASAPSWENTITY = new WTAASAPSWENTITY();
/*  418 */           wTAASAPSWENTITY.setADOCNO(str5);
/*  419 */           wTAASAPSWENTITY.setANNDATE(str6);
/*  420 */           wTAASAPSWENTITY.setANNNUMBER(str7);
/*  421 */           wTAASAPSWENTITY.setRFASHRTTITLE(str8);
/*  422 */           wTAASAPSWENTITY.setAVAILID(str3);
/*  423 */           wTAASAPSWENTITY.setEFFECTIVEDATE(str9);
/*      */           
/*  425 */           HashMap<Object, Object> hashMap = new HashMap<>();
/*  426 */           ArrayList<PRODINFO> arrayList = new ArrayList();
/*  427 */           PRODINFO pRODINFO = new PRODINFO();
/*  428 */           pRODINFO.setMODELID(str4);
/*  429 */           arrayList.add(pRODINFO);
/*  430 */           hashMap.put(str10, arrayList);
/*  431 */           wTAASAPSWENTITY.setPROD(hashMap);
/*      */           
/*  433 */           this.prodInfo.put(str1, wTAASAPSWENTITY);
/*      */         } 
/*  435 */         bool = true;
/*      */       } 
/*      */     } finally {
/*  438 */       if (preparedStatement != null) {
/*      */         try {
/*  440 */           preparedStatement.close();
/*  441 */         } catch (SQLException sQLException) {
/*  442 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*  446 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getSWFeatureInfo(EntityItem paramEntityItem) throws MiddlewareException, SQLException {
/*  457 */     ResultSet resultSet = null;
/*  458 */     PreparedStatement preparedStatement = null;
/*      */     
/*      */     try {
/*  461 */       Connection connection = this.m_db.getPDHConnection();
/*  462 */       Iterator<Map.Entry> iterator = this.prodInfo.entrySet().iterator();
/*  463 */       while (iterator.hasNext()) {
/*  464 */         Map.Entry entry = iterator.next();
/*  465 */         WTAASAPSWENTITY wTAASAPSWENTITY = (WTAASAPSWENTITY)entry.getValue();
/*  466 */         Iterator<Map.Entry> iterator1 = wTAASAPSWENTITY.getPROD().entrySet().iterator();
/*  467 */         while (iterator1.hasNext()) {
/*  468 */           Map.Entry entry1 = iterator1.next();
/*  469 */           ArrayList<PRODINFO> arrayList = (ArrayList)entry1.getValue();
/*  470 */           int i = arrayList.size();
/*  471 */           for (byte b = 0; b < i; b++) {
/*  472 */             PRODINFO pRODINFO = arrayList.get(b);
/*  473 */             String str = pRODINFO.getMODELID();
/*      */             
/*  475 */             preparedStatement = connection.prepareStatement(queryForSWFC(paramEntityItem, str));
/*  476 */             resultSet = preparedStatement.executeQuery();
/*      */             
/*  478 */             while (resultSet.next()) {
/*  479 */               String str1 = resultSet.getString(1);
/*  480 */               String str2 = resultSet.getString(2);
/*  481 */               String str3 = resultSet.getString(3);
/*  482 */               String str4 = resultSet.getString(4);
/*  483 */               String str5 = resultSet.getString(5);
/*  484 */               String str6 = resultSet.getString(6);
/*  485 */               String str7 = resultSet.getString(7);
/*  486 */               String str8 = resultSet.getString(8);
/*  487 */               String str9 = resultSet.getString(9);
/*  488 */               String str10 = resultSet.getString(10);
/*      */               
/*  490 */               PRODINFO pRODINFO1 = new PRODINFO();
/*  491 */               pRODINFO1.setPRODUCTVRM(str1);
/*  492 */               pRODINFO1.setMODELATR(str3);
/*  493 */               pRODINFO1.setMACHTYPEATR(str2);
/*  494 */               pRODINFO1.setMINVNAME(str4);
/*  495 */               pRODINFO1.setEDUCALLOWMHGHSCH(str5);
/*  496 */               pRODINFO1.setMODELID(str);
/*      */               
/*  498 */               pRODINFO1.setSwFeatureId(str6);
/*  499 */               pRODINFO1.setFEATURECODE(str10);
/*  500 */               pRODINFO1.setPRICEDFEATURE(str7);
/*  501 */               pRODINFO1.setCHARGEOPTION(str8);
/*  502 */               pRODINFO1.setSFINVNAME(str9);
/*      */               
/*  504 */               arrayList.add(pRODINFO1);
/*      */             } 
/*      */             
/*  507 */             pRODINFO.setMODELID(null);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  512 */       if (preparedStatement != null) {
/*      */         try {
/*  514 */           preparedStatement.close();
/*  515 */         } catch (SQLException sQLException) {
/*  516 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String queryForPROD(EntityItem paramEntityItem) {
/*  527 */     StringBuffer stringBuffer = new StringBuffer();
/*  528 */     addDebug("Get ANN related PRODSTRUCT info");
/*      */     
/*  530 */     stringBuffer.append("SELECT ADOCNO,ANNDATE,ANNNUMBER,CAST(RFASHRTTITLE AS VARCHAR(500)),AVAILID,EFFECTIVEDATE,MODELID,PRODUCTVRM,MODELATR,MACHTYPEATR,INVNAME,EDUCALLOWMHGHSCH,FEATUREID,FEATURECODE,PRICEDFEATURE,CHARGEOPTION,SFINVNAME FROM  ");
/*  531 */     stringBuffer.append("(select ann.ADOCNO, ann.ANNDATE, ann.ANNNUMBER, ann.RFASHRTTITLE,f.entityid as availid, a1.EFFECTIVEDATE, m.entityid as modelid, m.PRODUCTVRM, m.MODELATR, m.MACHTYPEATR, m.INVNAME, t.attributevalue as EDUCALLOWMHGHSCH, sf.entityid as featureid, sf.FEATURECODE, sf.PRICEDFEATURE, sf.CHARGEOPTION, sf.INVNAME as SFINVNAME from price.announcement ann ");
/*  532 */     stringBuffer.append("join price.avail a1 on a1.STATUS='Final' and a1.ANNCODENAME=ann.ANNCODENAME and a1.availType in ('Planned Availability','First Order')  ");
/*  533 */     stringBuffer.append("join opicm.flag f on f.entitytype='AVAIL' and f.attributecode='GENAREASELECTION' and f.attributevalue in ('1999','6199','6211','6219','6220') and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
/*      */     
/*  535 */     stringBuffer.append("join opicm.relator r1 on r1.entity1type='SWPRODSTRUCT' and r1.entity2type='AVAIL' and r1.entity2id=f.entityid and r1.valto>current timestamp and r1.effto>current timestamp  ");
/*  536 */     stringBuffer.append("join price.swprodstruct sw on sw.entityid=r1.entity1id and sw.STATUS='Final' ");
/*  537 */     stringBuffer.append("join price.SWFEATURE sf on sf.entityid=sw.id1 and sf.STATUS='Final' ");
/*  538 */     stringBuffer.append("join price.model m on m.entityid=sw.id2 and m.STATUS='Final' and m.COFCAT='Software' ");
/*  539 */     stringBuffer.append("left join opicm.relator r3 on r3.entity1type='MODEL' and r3.entity1id=m.entityid and r3.entity2type='GEOMOD' and r3.valto>current timestamp and r3.effto>current timestamp ");
/*  540 */     stringBuffer.append("left join opicm.text t  on t.entitytype='GEOMOD' and t.entityid=r3.entity2id and t.attributecode='EDUCALLOWMHGHSCH' and t.valto>current timestamp and t.effto>current timestamp ");
/*  541 */     stringBuffer.append("where ann.entityid=");
/*  542 */     stringBuffer.append(paramEntityItem.getEntityID());
/*  543 */     stringBuffer.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 order by m.MACHTYPEATR,m.MODELATR,availid,featureid) ");
/*  544 */     stringBuffer.append("GROUP BY AVAILID,ADOCNO,ANNDATE,ANNNUMBER,CAST(RFASHRTTITLE AS VARCHAR(500)),EFFECTIVEDATE,MODELID,PRODUCTVRM,MODELATR,MACHTYPEATR,INVNAME,EDUCALLOWMHGHSCH,FEATUREID,FEATURECODE,PRICEDFEATURE,CHARGEOPTION,SFINVNAME WITH UR ");
/*      */     
/*  546 */     addDebug("SQL:" + stringBuffer);
/*  547 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String queryForMODEL(EntityItem paramEntityItem) {
/*  555 */     addDebug("Get ANN related MODEL info---------------");
/*  556 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  558 */     stringBuffer.append("SELECT ADOCNO,ANNDATE,ANNNUMBER,CAST(RFASHRTTITLE AS VARCHAR(500)),AVAILID,EFFECTIVEDATE,MODELID,MACHTYPEATR FROM  ");
/*  559 */     stringBuffer.append("(select ann.ADOCNO, ann.ANNDATE, ann.ANNNUMBER, ann.RFASHRTTITLE, f.entityid as availid, a1.EFFECTIVEDATE, m.entityid as modelid, m.MACHTYPEATR from price.announcement ann  ");
/*  560 */     stringBuffer.append("join price.avail a1 on a1.ANNCODENAME=ann.ANNCODENAME and a1.STATUS='Final' and a1.availType in ('Planned Availability','First Order') ");
/*  561 */     stringBuffer.append("join opicm.flag  f  on f.entitytype='AVAIL' and f.attributecode='GENAREASELECTION' and f.attributevalue in ('1999','6199','6211','6219','6220') and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
/*  562 */     stringBuffer.append("join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=f.entityid and r.valto>current timestamp and r.effto>current timestamp ");
/*  563 */     stringBuffer.append("join price.model m on m.entityid=r.entity1id and m.STATUS='Final' and m.COFCAT='Software' ");
/*  564 */     stringBuffer.append("left join opicm.relator r3 on r3.entity1type='MODEL' and r3.entity1id=m.entityid and r3.entity2type='GEOMOD' and r3.valto>current timestamp and r3.effto>current timestamp ");
/*  565 */     stringBuffer.append("left join opicm.text t  on t.entitytype='GEOMOD' and t.entityid=r3.entity2id and t.attributecode='EDUCALLOWMHGHSCH' and t.valto>current timestamp and t.effto>current timestamp ");
/*  566 */     stringBuffer.append("where ann.entityid = ");
/*  567 */     stringBuffer.append(paramEntityItem.getEntityID());
/*  568 */     stringBuffer.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 order by m.MACHTYPEATR,modelid,availid) ");
/*  569 */     stringBuffer.append("GROUP BY AVAILID,ADOCNO,ANNDATE,ANNNUMBER,CAST(RFASHRTTITLE AS VARCHAR(500)),EFFECTIVEDATE,MODELID,MACHTYPEATR WITH UR ");
/*      */     
/*  571 */     addDebug("SQL:" + stringBuffer);
/*  572 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String queryForSWFC(EntityItem paramEntityItem, String paramString) {
/*  583 */     addDebug("Get ANN related SWFeature info---------------");
/*  584 */     StringBuffer stringBuffer = new StringBuffer();
/*  585 */     stringBuffer.append("SELECT PRODUCTVRM,MACHTYPEATR,MODELATR,INVNAME,CAST(EDUCALLOWMHGHSCH AS VARCHAR(500)),FEATUREID,PRICEDFEATURE,CHARGEOPTION,SFINVNAME,FEATURECODE FROM  ( ");
/*  586 */     stringBuffer.append("select m.PRODUCTVRM, m.MACHTYPEATR, m.MODELATR, m.INVNAME, t.attributevalue as EDUCALLOWMHGHSCH, sf.entityid as featureid,sf.PRICEDFEATURE,sf.CHARGEOPTION,sf.INVNAME as SFINVNAME,sf.FEATURECODE from price.announcement ann ");
/*  587 */     stringBuffer.append("join price.avail a1 on a1.ANNCODENAME=ann.ANNCODENAME and a1.STATUS='Final' and a1.availType in ('Planned Availability','First Order') ");
/*  588 */     stringBuffer.append("join opicm.flag  f  on f.entitytype='AVAIL' and f.attributecode='GENAREASELECTION' and f.attributevalue in ('1999','6199','6211','6219','6220') and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
/*  589 */     stringBuffer.append("join opicm.relator r1 on r1.entity1type='SWPRODSTRUCT' and r1.entity2type='AVAIL' and r1.entity2id=a1.entityid and r1.valto>current timestamp and r1.effto>current timestamp ");
/*  590 */     stringBuffer.append("join price.swprodstruct sw on sw.entityid=r1.entity1id and sw.STATUS='Final' and sw.id2 = ");
/*  591 */     stringBuffer.append(paramString);
/*  592 */     stringBuffer.append(" join price.MODEL m on m.entityid=sw.id2 and m.STATUS='Final' ");
/*  593 */     stringBuffer.append("left join opicm.relator r3 on r3.entity1type='MODEL' and r3.entity1id=m.entityid and r3.entity2type='GEOMOD' and r3.valto>current timestamp and r3.effto>current timestamp ");
/*  594 */     stringBuffer.append("left join opicm.text t  on t.entitytype='GEOMOD' and t.entityid=r3.entity2id and t.attributecode='EDUCALLOWMHGHSCH' and t.valto>current timestamp and t.effto>current timestamp ");
/*  595 */     stringBuffer.append(" join price.SWFEATURE sf on sf.entityid=sw.id1 and sf.STATUS='Final' ");
/*  596 */     stringBuffer.append("where ann.entityid= ");
/*  597 */     stringBuffer.append(paramEntityItem.getEntityID());
/*  598 */     stringBuffer.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 order by featureid ) ");
/*  599 */     stringBuffer.append("GROUP BY PRODUCTVRM,MACHTYPEATR,MODELATR,INVNAME,CAST(EDUCALLOWMHGHSCH AS VARCHAR(500)),FEATUREID,PRICEDFEATURE,CHARGEOPTION,SFINVNAME,FEATURECODE WITH UR ");
/*      */     
/*  601 */     addDebug("SQL:" + stringBuffer);
/*      */     
/*  603 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getModifyNum(String paramString) {
/*  612 */     String str = null;
/*  613 */     if (paramString == null || "".equals(paramString)) {
/*  614 */       str = "00";
/*      */     } else {
/*  616 */       String[] arrayOfString = splitStr(paramString, ".");
/*  617 */       if (arrayOfString.length > 2) {
/*  618 */         String str1 = arrayOfString[2].trim();
/*  619 */         if (str1.length() < 2) {
/*  620 */           str = "0" + str1;
/*  621 */         } else if (str1.length() == 2) {
/*  622 */           str = str1;
/*      */         } else {
/*  624 */           str = str1.substring(0, 2);
/*      */         } 
/*      */       } else {
/*  627 */         str = "00";
/*      */       } 
/*      */     } 
/*  630 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getReleaseNum(String paramString) {
/*  638 */     String str = null;
/*  639 */     if (paramString == null || "".equals(paramString)) {
/*  640 */       str = "01";
/*      */     } else {
/*  642 */       String[] arrayOfString = splitStr(paramString, ".");
/*  643 */       if (arrayOfString.length > 1) {
/*  644 */         String str1 = arrayOfString[1].trim();
/*  645 */         if (str1.length() < 2) {
/*  646 */           str = "0" + str1;
/*  647 */         } else if (str1.length() == 2) {
/*  648 */           str = str1;
/*      */         } else {
/*  650 */           str = str1.substring(0, 2).trim();
/*      */         } 
/*  652 */       } else if (arrayOfString.length == 1) {
/*      */         
/*  654 */         str = "00";
/*      */       } 
/*      */     } 
/*  657 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getVersionNum(String paramString) {
/*  666 */     String str = "";
/*  667 */     if (paramString == null || "".equals(paramString)) {
/*  668 */       str = "01";
/*      */     } else {
/*      */       
/*  671 */       String[] arrayOfString = splitStr(paramString, ".");
/*  672 */       String str1 = arrayOfString[0].trim();
/*  673 */       if (str1.startsWith("V")) {
/*  674 */         str1 = str1.substring(1, str1.length()).trim();
/*      */       }
/*  676 */       if (str1.length() < 2) {
/*  677 */         str = "0" + str1;
/*  678 */       } else if (str1.length() == 2) {
/*  679 */         str = str1;
/*      */       } 
/*      */     } 
/*      */     
/*  683 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] splitStr(String paramString1, String paramString2) {
/*  693 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1, paramString2);
/*  694 */     String[] arrayOfString = new String[stringTokenizer.countTokens()];
/*  695 */     byte b = 0;
/*  696 */     while (stringTokenizer.hasMoreTokens()) {
/*  697 */       arrayOfString[b] = stringTokenizer.nextToken();
/*  698 */       b++;
/*      */     } 
/*  700 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String setFileName(String paramString) {
/*  708 */     StringBuffer stringBuffer = new StringBuffer(paramString.trim());
/*  709 */     String str1 = getNow();
/*      */     
/*  711 */     str1 = str1.replace(' ', '_');
/*  712 */     stringBuffer.append(str1 + ".SCRIPT");
/*  713 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_rptpath", "/Dgq");
/*  714 */     if (!str2.endsWith("/")) {
/*  715 */       str2 = str2 + "/";
/*      */     }
/*  717 */     String str3 = str2 + stringBuffer.toString();
/*      */     
/*  719 */     addDebug("**** ffPathName: " + str3 + " ffFileName: " + stringBuffer.toString());
/*  720 */     return str3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean generateOutputFile(HashMap paramHashMap) {
/*  728 */     if (paramHashMap.size() == 0) {
/*  729 */       this.userxmlSb.append("File generate failed, for there is no qualified data related, please check ");
/*  730 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  735 */       Iterator<Map.Entry> iterator = paramHashMap.entrySet().iterator();
/*  736 */       Vector<String> vector = new Vector(1);
/*  737 */       byte b = 1;
/*  738 */       while (iterator.hasNext()) {
/*  739 */         Map.Entry entry = iterator.next();
/*  740 */         WTAASAPSWENTITY wTAASAPSWENTITY = (WTAASAPSWENTITY)entry.getValue();
/*  741 */         String str1 = wTAASAPSWENTITY.getADOCNO();
/*  742 */         str1 = (str1 != null) ? str1 : "";
/*      */         
/*  744 */         String str2 = setFileName("FP" + str1 + b);
/*      */         
/*  746 */         boolean bool = generateFile(str2, wTAASAPSWENTITY);
/*  747 */         if (bool) {
/*  748 */           vector.add(str2);
/*  749 */           b++;
/*      */         } 
/*      */       } 
/*      */       
/*  753 */       generateZipfile(vector);
/*  754 */       this.userxmlSb.append("File generate success");
/*  755 */       return true;
/*      */     }
/*  757 */     catch (Exception exception) {
/*  758 */       exception.printStackTrace();
/*  759 */       return false;
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
/*      */   private boolean generateFile(String paramString, WTAASAPSWENTITY paramWTAASAPSWENTITY) throws Exception {
/*  771 */     FileOutputStream fileOutputStream = new FileOutputStream(paramString);
/*  772 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*      */     try {
/*  774 */       String str1 = "";
/*  775 */       byte b1 = 1;
/*  776 */       ArrayList<String> arrayList = new ArrayList();
/*  777 */       String str2 = this.annNumber;
/*      */ 
/*      */       
/*  780 */       Iterator<Map.Entry> iterator = paramWTAASAPSWENTITY.getPROD().entrySet().iterator();
/*  781 */       while (iterator.hasNext()) {
/*  782 */         Map.Entry entry = iterator.next();
/*  783 */         String str5 = (String)entry.getKey();
/*      */         
/*  785 */         String str4 = generateFactSection(str2, str5, paramWTAASAPSWENTITY, b1);
/*  786 */         if (str4 != null) {
/*  787 */           arrayList.add(str4);
/*  788 */           b1++;
/*      */         } 
/*      */       } 
/*      */       
/*  792 */       String str3 = "IJ";
/*  793 */       str1 = str1 + generateHeadSection(str2, paramWTAASAPSWENTITY, 'P', str3) + "\r\n";
/*      */       
/*  795 */       b1 = 1; byte b2;
/*  796 */       for (b2 = 0; b2 < arrayList.size(); b2++) {
/*  797 */         String str = arrayList.get(b2);
/*      */         
/*  799 */         str1 = str1 + "  ---------------------------- FACT " + b1 + " OF " + arrayList.size() + " -------------------------------      \r\n" + str;
/*      */         
/*  801 */         b1++;
/*      */       } 
/*      */       
/*  804 */       outputStreamWriter.write(str1);
/*  805 */       this.userxmlSb.append(str2 + paramWTAASAPSWENTITY.getAVAILID() + " File generate success \n");
/*  806 */       b2 = 1; return b2;
/*  807 */     } catch (IOException iOException) {
/*  808 */       this.userxmlSb.append(iOException);
/*  809 */       throw new Exception("File create failed " + paramString);
/*  810 */     } catch (WTAASException wTAASException) {
/*  811 */       this.userxmlSb.append(wTAASException);
/*  812 */       return false;
/*      */     } finally {
/*  814 */       outputStreamWriter.flush();
/*  815 */       outputStreamWriter.close();
/*      */       try {
/*  817 */         if (outputStreamWriter != null) {
/*  818 */           outputStreamWriter.close();
/*      */         }
/*  820 */       } catch (Exception exception) {
/*  821 */         throw new Exception("File create failed " + paramString);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getValue(String paramString) {
/*  828 */     if (paramString == null) {
/*  829 */       return "";
/*      */     }
/*  831 */     return paramString;
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
/*      */   private String generateHeadSection(String paramString1, WTAASAPSWENTITY paramWTAASAPSWENTITY, char paramChar, String paramString2) throws Exception {
/*  844 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  845 */     hashMap.put("PANNRFA", paramString1);
/*      */     
/*  847 */     String str1 = formatValue(paramWTAASAPSWENTITY.getADOCNO(), "DOCID");
/*  848 */     String str2 = formatValue(paramWTAASAPSWENTITY.getADOCNO(), "LETNO");
/*  849 */     String str3 = formatDate(paramWTAASAPSWENTITY.getANNDATE(), "yyyy-MM-dd", "yyyyMMdd");
/*      */     
/*  851 */     String str4 = fixLength(paramWTAASAPSWENTITY.getRFASHRTTITLE(), 69);
/*  852 */     String str5 = fixLength(paramWTAASAPSWENTITY.getRFASHRTTITLE(), 78);
/*  853 */     String str6 = this.sdf.format(new Date());
/*  854 */     String str7 = fixLength(formatDate(paramWTAASAPSWENTITY.getANNDATE(), "yyyy-MM-dd", "EEEEE, MMMMM dd, yyyy").toUpperCase(), 39);
/*  855 */     String str8 = fixLength(formatDate(paramWTAASAPSWENTITY.getANNDATE(), "yyyy-MM-dd", "MMMMM dd, yyyy").toUpperCase(), 55);
/*      */     
/*  857 */     String str9 = ".*DOCID    F" + paramChar + "" + str1 + "\r\n.*LETNO    F" + paramChar + "" + str2 + "\r\n.*DATE     " + str3 + "\r\n.*REVISED  " + str3 + "\r\n.*TITLE    " + str4 + "                                       \r\n.ss                                                                           \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  865 */     str9 = str9 + ".*  Modified on " + str6 + " by " + paramString2 + "\r\n.fo on;.sk;.sv on;.tm 0;.bm 0;.pn off;.hy off;.ju off;.ti ?05               \r\n.pl 62;.ll 69;.co;.tr ! 40;.rf cancel;.rc 9 0;.rc 1 1                         \r\n.rc 2 2;.rc 3 3;.rc 4 4;.rc 5 5;.rc 6 6;.rc 7 7;.rc 8 8                       \r\n.fo off                                                                       \r\nCONFIDENTIAL UNTIL 8 AM NEW YORK TIME, " + str7 + "\r\n.fo off                                                                       \r\n.*DIST                                                                        \r\n:H2.AP Distribution:                                                          \r\n.sk                                                                           \r\nRefer to Fact Sheet details.                                                  \r\n.fo off;.sk                                                                   \r\n:H2.AP-RELEASE DATE:  " + str8 + "                                         \r\n.fo off;.sk                                                                   \r\nTHIS SECTION IS CLASSIFIED INTERNAL USE ONLY.                                 \r\n.in                                                                           \r\n.*LETTERNO                                                                    \r\n:H2.FACT SHEET NO. F" + paramChar + "" + str1 + "\r\n.*ELETTERNO                                                                   \r\nPROGRAMMING ANNOUNCEMENT                                                      \r\n.in                                                                           \r\nWORLD TRADE ASIA PACIFIC                                                      \r\n.sk;.in                                                                       \r\nRFA NUMBER:      " + paramString1 + "                                                        \r\n.sp                                                                           \r\n.fo left                                                                      \r\n.*TITLE                                                                       \r\n:H2.TITLE:                                                                    \r\n.br                                                                           \r\n" + str5 + "\r\n.*ETITLE                                                                      \r\n:h2.Fact sheet                                                                \r\n.sk;.fo off                                                                   \r\n ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  900 */     return str9;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String formatValue(String paramString1, String paramString2) throws WTAASException {
/*  906 */     String str = "      ";
/*  907 */     if (paramString1 == null)
/*  908 */       return str; 
/*  909 */     if (paramString1.length() < 9)
/*  910 */       throw new WTAASException("ADOC Number length is short than 9, please check"); 
/*  911 */     if ("DOCID".equals(paramString2)) {
/*  912 */       str = paramString1.substring(2, 4) + paramString1.substring(5, 9);
/*  913 */       return str;
/*  914 */     }  if ("LETNO".equals(paramString2)) {
/*  915 */       str = paramString1.substring(2);
/*  916 */       return str;
/*      */     } 
/*  918 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String generateFactSection(String paramString1, String paramString2, WTAASAPSWENTITY paramWTAASAPSWENTITY, int paramInt) throws Exception {
/*  925 */     String str1 = formatDate(paramWTAASAPSWENTITY.getANNDATE(), "yyyy-MM-dd", "dd/MM/yy");
/*  926 */     String str2 = paramWTAASAPSWENTITY.getADOCNO();
/*  927 */     String str3 = "  REMARKS:                                                                      \r\n";
/*  928 */     String str4 = null;
/*  929 */     Vector vector = new Vector();
/*  930 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*  931 */     String str5 = simpleDateFormat.format(new Date());
/*      */     
/*  933 */     int i = paramWTAASAPSWENTITY.getEFFECTIVEDATE().compareTo(str5);
/*  934 */     if (i > 0) {
/*  935 */       str4 = generateModelSection(paramString1, paramString2, paramWTAASAPSWENTITY, vector);
/*      */     }
/*      */     
/*  938 */     String str6 = generateFeatureSection(paramString1, paramString2, paramInt, paramWTAASAPSWENTITY);
/*      */     
/*  940 */     if (str4 == null && str6 == null) {
/*  941 */       return null;
/*      */     }
/*  943 */     if (str4 == null) {
/*  944 */       str4 = "  MAJOR TYPE/MODEL BM FILE UPDATE                                               \r\n                                                                                \r\n  ADDITIONAL TYPE AND/OR MODELS                                                 \r\n                                     1ST DEL                                    \r\n  DESCRIPTION (ENGLISH/LOCAL)        DATE      MV       TYPE-MDL                \r\n                                                                                \r\n  END OF ADDITIONAL TYPE AND/OR MODELS                                          ";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  953 */     if (str6 == null) {
/*  954 */       str6 = "  MAJOR TYPE/FEATURE BF FILE UPDATE                                             \r\n                                                                                \r\n                                                                                \r\n  ADDITIONAL FEATURES                                                           \r\n                                                                                \r\n  Product Type: " + paramString2 + "                                                            \r\n                                        D                                       \r\n                                  ME O  S                                       \r\n                                   D T  L  1st DEL/                             \r\n  DESCRIPTION (ENGLISH/LOCAL)     IA C  O  PROC GROUP    MV    FEAT    P/N      \r\n                                                                                \r\n  END OF ADDITIONAL FEATURES                                                    ";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  969 */     return "                                                                                \r\n              LICENSED PROGRAM ADMINISTRATIVE FACT SHEET                        \r\n                                                                                \r\n                    IBM CONFIDENTIAL UNTIL ANNOUNCED                            \r\n                                                                                \r\n  REFERENCE RFA/LA NO.: " + paramString1 + "          PROGRAM ANNOUNCEMENT: " + str2 + "          \r\n  PUBLICATION DATE: " + str1 + "                                                    \r\n                                                                                \r\n  RELEASED TO:  ALL AP ( X )    ASPA ONLY (  )    JAPAN ONLY (  )               \r\n                COUNTRY ONLY (SPECIFY)                                          \r\n                                                                                \r\n" + str3 + "                                                                                \r\n                                Distribution: BTO                               \r\n                                                                                \r\n" + str4 + "\r\n                                                                                \r\n" + str6 + "\r\n                                                                                \r\n  END OF FACT SHEET                                                             \r\n                                                                                \r\n";
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
/*      */   private String generateModelSection(String paramString1, String paramString2, WTAASAPSWENTITY paramWTAASAPSWENTITY, Vector<String> paramVector) throws Exception {
/* 1005 */     String str1 = "";
/* 1006 */     String str2 = "";
/* 1007 */     HashMap hashMap = paramWTAASAPSWENTITY.getPROD();
/* 1008 */     ArrayList<PRODINFO> arrayList = (ArrayList)hashMap.get(paramString2);
/* 1009 */     for (byte b = 0; b < arrayList.size(); b++) {
/* 1010 */       PRODINFO pRODINFO = arrayList.get(b);
/* 1011 */       if (pRODINFO.getMODELID() != null) {
/*      */ 
/*      */         
/* 1014 */         String str3 = formatDate(paramWTAASAPSWENTITY.getEFFECTIVEDATE(), "yyyy-MM-dd", "MMyy");
/* 1015 */         String str4 = "N";
/*      */ 
/*      */         
/* 1018 */         String str5 = (pRODINFO.getEDUCALLOWMHGHSCH() == null || pRODINFO.getEDUCALLOWMHGHSCH().trim().length() == 0) ? "000000" : matchformat(pRODINFO.getEDUCALLOWMHGHSCH());
/* 1019 */         String str6 = "N";
/*      */         
/* 1021 */         String str7 = pRODINFO.getMACHTYPEATR();
/* 1022 */         String str8 = pRODINFO.getMODELATR();
/* 1023 */         String str9 = formatDate(paramWTAASAPSWENTITY.getANNDATE(), "yyyy-MM-dd", "ddMMyy");
/* 1024 */         String str10 = pRODINFO.getPRODUCTVRM();
/* 1025 */         String str11 = getVersionNum(str10);
/* 1026 */         String str12 = getReleaseNum(str10);
/* 1027 */         String str13 = getModifyNum(str10);
/* 1028 */         String str14 = getValue(pRODINFO.getMINVNAME());
/*      */         
/* 1030 */         String str15 = "N";
/*      */         
/* 1032 */         String str16 = "N";
/* 1033 */         String str17 = "N";
/* 1034 */         String str18 = "Y";
/* 1035 */         String str19 = fixLength(str14, 35);
/*      */         
/* 1037 */         if (this.modelSet.add(pRODINFO.getMACHTYPEATR())) {
/*      */ 
/*      */           
/* 1040 */           str19 = fixLength(str14, 56);
/*      */           
/* 1042 */           str1 = "  Program No: " + str7 + "-" + str8 + "          Ver " + str11 + "   Rel " + str12 + "  Mod " + str13 + "                         \r\n  Description (English) " + str14 + "\r\n  Description (Local)                                                           \r\n  PP: Y PN:   PRPQ:   PO:   Media Feature: " + str6 + "             Use in Cty: Y          \r\n  Orders Rejected: N        DP Install,Post Order: NN    Price Print: Y         \r\n  Call Off: N               License,Install,Usage: " + str16 + "" + str17 + "" + str18 + "   S/W Warranty: " + str4 + "        \r\n  Auto Sftw Sched: Y        Mat Check,Pass: NN           DSLO Elig: " + str15 + "           \r\n  Restricted Material: N                                                        \r\n                                                                                \r\n  Product ID Code: 5             First Del Date: " + str3 + "/                          \r\n  Program Lib Code: 88/          Ser Support ID Code: M                         \r\n  Default Order Sys:             Education Allowance: " + str5 + "                    \r\n  Pre-Install Test: 00/000       Limited Billing Period (leave blank):   /      \r\n  Central Ser Code: 0            Central End Support:                           \r\n  A - B - C: A                   Local Ser Code:                                \r\n  Local Service:                 Order Confirmation: 05                         \r\n  Normal Billing Period: 0101    Product Lead:                                  \r\n  Normal M/A / ITC Code: T/B                                                    \r\n                                                                                \r\n  Points Effective Date: " + str9 + "     Points (MV): 000000*                        \r\n  Values Effective Date: " + str9 + "     Monthly Rental Value: 000                   \r\n  Initial Charge Value: 000                                                     \r\n                                                                                \r\n  BL FILE UPDATE                                                                \r\n                                                                                \r\n  Price Code: 0                   Announcement Date: " + str9 + "                     \r\n  Monthly Charge/SUC/OTC: 000                                                   \r\n                                                                                \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1071 */           paramVector.add(pRODINFO.getMODELID());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1078 */         else if (!paramVector.contains(pRODINFO.getMODELID())) {
/* 1079 */           str2 = str2 + "  " + str19 + str3 + "    000000*    " + str7 + "-" + str8 + "                \r\n                                                                                \r\n";
/*      */ 
/*      */           
/* 1082 */           paramVector.add(pRODINFO.getMODELID());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1087 */     if (str2.trim().length() == 0) {
/* 1088 */       str2 = "                                                                                \r\n";
/*      */     }
/* 1090 */     return "  MAJOR TYPE/MODEL BM FILE UPDATE                                               \r\n                                                                                \r\n" + str1 + "  ADDITIONAL TYPE AND/OR MODELS                                                 \r\n                                     1ST DEL                                    \r\n  DESCRIPTION (ENGLISH/LOCAL)        DATE      MV       TYPE-MDL                \r\n" + str2 + "  END OF ADDITIONAL TYPE AND/OR MODELS                                          ";
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
/*      */   private String generateFeatureSection(String paramString1, String paramString2, int paramInt, WTAASAPSWENTITY paramWTAASAPSWENTITY) throws Exception {
/* 1106 */     String str1 = "";
/* 1107 */     String str2 = "";
/* 1108 */     String str3 = "";
/* 1109 */     String str4 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1114 */     HashMap hashMap = paramWTAASAPSWENTITY.getPROD();
/* 1115 */     ArrayList arrayList = (ArrayList)hashMap.get(paramString2);
/* 1116 */     if (arrayList.size() > 0) {
/*      */       
/* 1118 */       String str = formatDate(paramWTAASAPSWENTITY.getEFFECTIVEDATE(), "yyyy-MM-dd", "MMyy");
/*      */       
/* 1120 */       String[] arrayOfString = featureTreatment(paramWTAASAPSWENTITY, paramString2, str, paramInt);
/* 1121 */       str2 = arrayOfString[0];
/* 1122 */       str3 = arrayOfString[1];
/* 1123 */       str1 = arrayOfString[2];
/*      */     }
/*      */     else {
/*      */       
/* 1127 */       return null;
/*      */     } 
/*      */     
/* 1130 */     if (str3.trim().length() == 0) {
/* 1131 */       str3 = "                                                                                \r\n";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1141 */     str4 = "  MAJOR TYPE/FEATURE BF FILE UPDATE                                             \r\n                                                                                \r\n" + str2 + "                                                                                \r\n  ADDITIONAL FEATURES                                                           \r\n                                                                                \r\n  Product Type: " + fixLength(str1, 4) + "                                                            \r\n                                        D                                       \r\n                                  ME O  S                                       \r\n                                   D T  L  1st DEL/                             \r\n  DESCRIPTION (ENGLISH/LOCAL)     IA C  O  PROC GROUP    MV    FEAT    P/N      \r\n" + str3 + "  END OF ADDITIONAL FEATURES                                                    ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1149 */     return str4;
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
/*      */   private String[] featureTreatment(WTAASAPSWENTITY paramWTAASAPSWENTITY, String paramString1, String paramString2, int paramInt) throws Exception {
/* 1164 */     String str1 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1176 */     String str2 = "";
/* 1177 */     String str3 = "";
/*      */     
/* 1179 */     HashMap hashMap = paramWTAASAPSWENTITY.getPROD();
/* 1180 */     ArrayList<PRODINFO> arrayList = (ArrayList)hashMap.get(paramString1);
/* 1181 */     for (byte b = 0; b < arrayList.size(); b++) {
/*      */       
/* 1183 */       String str = "N";
/* 1184 */       PRODINFO pRODINFO = arrayList.get(b);
/* 1185 */       if (pRODINFO.getMODELID() != null) {
/*      */         String str8, str9, str10, str12, str13;
/*      */         
/* 1188 */         str1 = pRODINFO.getMACHTYPEATR();
/* 1189 */         String str4 = pRODINFO.getMODELATR();
/* 1190 */         String str5 = pRODINFO.getFEATURECODE();
/* 1191 */         String str6 = getValue(pRODINFO.getSFINVNAME());
/* 1192 */         String str7 = formatDate(paramWTAASAPSWENTITY.getANNDATE(), "yyyy-MM-dd", "ddMMyy");
/*      */         
/* 1194 */         String str11 = "N";
/* 1195 */         if ("Yes".equals(pRODINFO.getPRICEDFEATURE())) {
/* 1196 */           str12 = "Y";
/* 1197 */           str8 = "000001";
/* 1198 */           str10 = "100";
/* 1199 */           str13 = "OTC";
/* 1200 */           str9 = "100";
/*      */         } else {
/* 1202 */           str12 = "N";
/* 1203 */           str8 = "000000";
/* 1204 */           str10 = "000";
/* 1205 */           str13 = "NC";
/* 1206 */           str9 = "000";
/*      */         } 
/*      */ 
/*      */         
/* 1210 */         if (!this.featureSet.contains(str1)) {
/*      */           
/* 1212 */           if ("OTC".equals(str13) || (str2.equals("") && b == arrayList.size() - 1))
/*      */           {
/* 1214 */             String str14 = fixLength(str13 + " " + str6 + "/" + str4, 56);
/*      */ 
/*      */ 
/*      */             
/* 1218 */             String str15 = (pRODINFO.getEDUCALLOWMHGHSCH() == null || pRODINFO.getEDUCALLOWMHGHSCH().trim().length() == 0) ? "000000" : matchformat(pRODINFO.getEDUCALLOWMHGHSCH());
/*      */             
/* 1220 */             str2 = "  Type/Model/Feature No.: " + str1 + "-" + str4 + "/" + str5 + "      Part Number:                       \r\n  Description (English) " + str14 + "\r\n  Description (Local)                                                           \r\n  Media Feature: " + str + "      Orders Rejected: N      Price Print: Y                  \r\n  Usable in Cty: Y      One Time Charge: " + str12 + "      DP Install Program: N           \r\n  Specify Feature: N    DSLO: N                 Restricted Material: N          \r\n                                                                                \r\n  1st Del Date/Min&Max Proc Grp: " + paramString2 + "/         Education Allowance: " + str15 + "      \r\n  Service Support ID Code: P/                                                   \r\n  Product Lead Time:             Pre-Install Test/Inter-Co Chg Code: 00/B       \r\n                                                                                \r\n  Points Effective Date: " + str7 + "  Points: " + str8 + "*                                \r\n  Values Effective Date: " + str7 + "  Monthly Rental Value: " + str9 + "                      \r\n  Initial Charge Value: 000                                                     \r\n                                                                                \r\n  BL FILE UPDATE                                                                \r\n                                                                                \r\n  Price Code: 0                   Announcement Date: " + str7 + "                     \r\n  Monthly Charge/SUC/OTC: " + str10 + "                                                   \r\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1242 */             this.featureSet.add(str1);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1249 */           String str14 = fixLength(str13 + " " + str6, 26) + "/" + str4;
/*      */           
/* 1251 */           str3 = str3 + "  " + str14 + "  " + str + "  " + str12 + "  " + str11 + "  " + paramString2 + "/      " + str8 + "*  " + str5 + "             \r\n                                                                                \r\n";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1256 */     return new String[] { str2, str3, str1 };
/*      */   }
/*      */   
/*      */   private String matchformat(String paramString) {
/* 1260 */     String str = "";
/* 1261 */     if (paramString.length() == 1) {
/* 1262 */       paramString = "0" + paramString;
/* 1263 */     } else if (paramString.length() > 2) {
/* 1264 */       paramString = paramString.substring(0, 2);
/*      */     } 
/* 1266 */     for (byte b = 0; b < 3; b++) {
/* 1267 */       str = str + paramString;
/*      */     }
/* 1269 */     return str;
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
/*      */   public static String formatDate(String paramString1, String paramString2, String paramString3) throws Exception {
/*      */     try {
/* 1283 */       SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(paramString2, Locale.US);
/* 1284 */       Date date = simpleDateFormat1.parse(paramString1);
/*      */ 
/*      */ 
/*      */       
/* 1288 */       if (!paramString1.equals(simpleDateFormat1.format(date))) {
/* 1289 */         throw new Exception("Invalid date");
/*      */       }
/*      */ 
/*      */       
/* 1293 */       SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(paramString3, Locale.US);
/* 1294 */       return simpleDateFormat2.format(date);
/* 1295 */     } catch (ParseException parseException) {
/* 1296 */       throw new Exception("Invalid format", parseException.getCause());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String fixLength(String paramString1, int paramInt, String paramString2) {
/* 1307 */     paramString1 = (paramString1 != null) ? paramString1 : "";
/* 1308 */     if (paramString2.equalsIgnoreCase("R")) {
/* 1309 */       if (paramString1.length() >= paramInt) {
/* 1310 */         return paramString1.substring(0, paramInt);
/*      */       }
/* 1312 */       String str = "";
/* 1313 */       for (byte b = 0; b < paramInt - paramString1.length(); b++) {
/* 1314 */         str = str + " ";
/*      */       }
/* 1316 */       paramString1 = paramString1 + str;
/*      */     }
/* 1318 */     else if (paramString2.equalsIgnoreCase("L")) {
/* 1319 */       if (paramString1.length() >= paramInt) {
/* 1320 */         return paramString1.substring(paramString1.length() - paramInt, paramString1.length());
/*      */       }
/* 1322 */       String str = "";
/* 1323 */       for (byte b = 0; b < paramInt - paramString1.length(); b++) {
/* 1324 */         str = str + " ";
/*      */       }
/* 1326 */       paramString1 = str + paramString1;
/*      */     } 
/*      */     
/* 1329 */     return paramString1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String fixLength(String paramString, int paramInt) {
/* 1339 */     return fixLength(paramString, paramInt, "R");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateZipfile(Vector<String> paramVector) {
/*      */     try {
/* 1349 */       FileOutputStream fileOutputStream = null;
/* 1350 */       ZipOutputStream zipOutputStream = null;
/* 1351 */       String str1 = getNow();
/* 1352 */       String str2 = ABRServerProperties.getOutputPath() + this.annNumber + str1 + ".zip";
/* 1353 */       this.zipFile = new File(str2);
/*      */       try {
/* 1355 */         char c = 'ࠀ';
/* 1356 */         fileOutputStream = new FileOutputStream(this.zipFile);
/* 1357 */         zipOutputStream = new ZipOutputStream(fileOutputStream);
/* 1358 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 1359 */           String str = paramVector.get(b);
/* 1360 */           File file = new File(str);
/* 1361 */           if (file.exists()) {
/* 1362 */             zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
/* 1363 */             BufferedInputStream bufferedInputStream = null;
/*      */             try {
/* 1365 */               bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
/*      */               
/* 1367 */               byte[] arrayOfByte = new byte[c]; int i;
/* 1368 */               while ((i = bufferedInputStream.read(arrayOfByte, 0, c)) != -1) {
/* 1369 */                 zipOutputStream.write(arrayOfByte, 0, i);
/*      */               }
/* 1371 */               zipOutputStream.closeEntry();
/* 1372 */               zipOutputStream.flush();
/* 1373 */               addDebug("Zip file " + str + " successfully");
/*      */             } finally {
/*      */               
/* 1376 */               if (bufferedInputStream != null) {
/* 1377 */                 bufferedInputStream.close();
/*      */               }
/*      */             } 
/*      */             
/* 1381 */             file.delete();
/*      */           } else {
/* 1383 */             addError("Zip file..., Missing file " + str);
/*      */           } 
/*      */         } 
/*      */       } finally {
/*      */         
/* 1388 */         if (zipOutputStream != null) {
/* 1389 */           zipOutputStream.flush();
/* 1390 */           zipOutputStream.close();
/*      */         } 
/* 1392 */         if (fileOutputStream != null) {
/* 1393 */           fileOutputStream.flush();
/* 1394 */           fileOutputStream.close();
/*      */         } 
/*      */       } 
/* 1397 */       paramVector.clear();
/* 1398 */       paramVector = null;
/*      */     
/*      */     }
/* 1401 */     catch (Exception exception) {
/* 1402 */       exception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendMail(File paramFile) throws Exception {
/* 1413 */     FileInputStream fileInputStream = null;
/*      */     try {
/* 1415 */       fileInputStream = new FileInputStream(paramFile);
/* 1416 */       int i = fileInputStream.available();
/* 1417 */       byte[] arrayOfByte = new byte[i];
/* 1418 */       fileInputStream.read(arrayOfByte);
/* 1419 */       setAttachByteForDG(arrayOfByte);
/* 1420 */       getABRItem().setFileExtension("zip");
/* 1421 */       addDebug("Sending mail for file " + paramFile);
/* 1422 */     } catch (IOException iOException) {
/* 1423 */       addDebug("sendMail IO Exception");
/*      */     }
/*      */     finally {
/*      */       
/* 1427 */       if (fileInputStream != null) {
/* 1428 */         fileInputStream.close();
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
/*      */   protected String getAttributeFlagValue(EntityItem paramEntityItem, String paramString) {
/* 1440 */     return PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*      */   }
/*      */   
/*      */   public String getDescription() {
/* 1444 */     return "WTAASAPSWABR";
/*      */   }
/*      */   
/*      */   public String getABRVersion() {
/* 1448 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1455 */     addOutput(paramString);
/* 1456 */     setReturnCode(-1);
/*      */   }
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1460 */     if (3 <= DEBUG_LVL) {
/* 1461 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/*      */   protected void addDebugComment(int paramInt, String paramString) {
/* 1473 */     if (paramInt <= DEBUG_LVL) {
/* 1474 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/*      */   protected EntityList getEntityList(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1491 */     return this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, paramString), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*      */             
/* 1493 */             .getEntityType(), paramEntityItem.getEntityID()) });
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
/*      */   protected EntityItem getEntityItem(Profile paramProfile, EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1511 */     EntityList entityList = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*      */             
/* 1513 */             .getEntityType(), paramEntityItem.getEntityID()) });
/* 1514 */     return entityList.getParentEntityGroup().getEntityItem(0);
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
/*      */   protected EntityItem getEntityItem(Profile paramProfile, String paramString, int paramInt) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1532 */     EntityList entityList = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, paramString, paramInt) });
/*      */ 
/*      */     
/* 1535 */     return entityList.getParentEntityGroup().getEntityItem(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 1542 */     return this.m_db;
/*      */   }
/*      */   
/*      */   protected String getABRTime() {
/* 1546 */     return String.valueOf(System.currentTimeMillis());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/* 1553 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String buildAbrHeader() {
/* 1563 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1570 */     MessageFormat messageFormat = new MessageFormat(str);
/* 1571 */     this.args[0] = this.m_prof.getOPName();
/* 1572 */     this.args[1] = this.m_prof.getRoleDescription();
/* 1573 */     this.args[2] = this.m_prof.getWGName();
/* 1574 */     this.args[3] = this.t2DTS;
/* 1575 */     this.args[4] = "WATTS AP SW ABR feed trigger<br/>" + this.xmlgenSb.toString();
/* 1576 */     this.args[5] = getABRVersion();
/* 1577 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1587 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1590 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1591 */     EANList eANList = entityGroup.getMetaAttribute();
/*      */     
/* 1593 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1594 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1595 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute
/* 1596 */             .getAttributeCode(), ", ", "", false));
/* 1597 */       stringBuffer.append(" ");
/*      */     } 
/* 1599 */     return stringBuffer.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WTAASAPSWABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */