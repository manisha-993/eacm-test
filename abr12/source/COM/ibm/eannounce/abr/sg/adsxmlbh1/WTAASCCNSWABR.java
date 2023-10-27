/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import java.util.zip.ZipOutputStream;
/*      */ 
/*      */ public class WTAASCCNSWABR extends PokBaseABR {
/*   30 */   private ResourceBundle rsBundle = null;
/*   31 */   private String annNumber = "";
/*   32 */   private String annDate = "";
/*      */   
/*   34 */   private File zipFile = null;
/*   35 */   private HashMap prodInfo = new HashMap<>();
/*   36 */   private StringBuffer rptSb = new StringBuffer();
/*   37 */   private StringBuffer xmlgenSb = new StringBuffer();
/*   38 */   private StringBuffer userxmlSb = new StringBuffer();
/*   39 */   private String t2DTS = "&nbsp;";
/*   40 */   private Object[] args = (Object[])new String[10];
/*      */   public static final String RPTPATH = "_rptpath";
/*   42 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   43 */   protected static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*   45 */   private static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("WTAASCCNSWABR");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*   52 */     String str1 = "";
/*      */     
/*      */     try {
/*   55 */       long l = System.currentTimeMillis();
/*      */       
/*   57 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*   60 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), 
/*   61 */           ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*   64 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*   69 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*   71 */               getEntityType(), getEntityID()) });
/*      */       
/*   73 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */       
/*   76 */       String str6 = getAttributeValue(entityItem, "CDOCNO");
/*   77 */       addDebug("ANNOUNCEMENT DOCNUM (CDOCNO): " + str6);
/*      */       
/*   79 */       this.annDate = getAttributeValue(entityItem, "ANNDATE");
/*   80 */       addDebug("ANNOUNCEMENT ANNDATE: " + this.annDate);
/*      */       
/*   82 */       this.annNumber = getAttributeValue(entityItem, "ANNNUMBER");
/*   83 */       addDebug("RFA number: " + this.annNumber);
/*      */       
/*   85 */       String str7 = getAttributeValue(entityItem, "ANNTYPE");
/*   86 */       addDebug("ANNOUNCEMENT type: " + str7);
/*      */       
/*   88 */       str1 = getNavigationName(entityItem);
/*   89 */       if (getReturnCode() == 0) {
/*   90 */         processThis(this, this.m_prof, entityItem);
/*      */         
/*   92 */         boolean bool = generateOutputFile(this.prodInfo);
/*   93 */         if (bool) {
/*      */           
/*   95 */           sendMail(this.zipFile);
/*      */ 
/*      */           
/*   98 */           if (!getABRItem().getKeepFile() && 
/*   99 */             this.zipFile.exists()) {
/*  100 */             this.zipFile.delete();
/*  101 */             addDebug("Check the keep file is false, delete the zip file");
/*      */           } 
/*      */         } else {
/*      */           
/*  105 */           setReturnCode(-1);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  110 */       addDebug("Total Time: " + 
/*  111 */           Stopwatch.format(System.currentTimeMillis() - l));
/*      */     }
/*  113 */     catch (Throwable throwable) {
/*  114 */       StringWriter stringWriter = new StringWriter();
/*  115 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  116 */       String str7 = "<pre>{0}</pre>";
/*  117 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  118 */       setReturnCode(-3);
/*  119 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  121 */       this.args[0] = throwable.getMessage();
/*  122 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  123 */       messageFormat1 = new MessageFormat(str7);
/*  124 */       this.args[0] = stringWriter.getBuffer().toString();
/*  125 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  126 */       logError("Exception: " + throwable.getMessage());
/*  127 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*  129 */       if ("&nbsp;".equals(this.t2DTS)) {
/*  130 */         this.t2DTS = getNow();
/*      */       }
/*  132 */       setDGTitle(str1);
/*  133 */       setDGRptName(getShortClassName(getClass()));
/*  134 */       setDGRptClass(getABRCode());
/*      */       
/*  136 */       if (!isReadOnly()) {
/*  137 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  143 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  156 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */     
/*  160 */     MessageFormat messageFormat = new MessageFormat(str2);
/*  161 */     this.args[0] = getShortClassName(getClass());
/*  162 */     this.args[1] = str1;
/*  163 */     String str3 = messageFormat.format(this.args);
/*  164 */     String str4 = buildAbrHeader();
/*      */ 
/*      */     
/*  167 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("RESULT_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  168 */     this.rptSb.insert(0, str5);
/*      */     
/*  170 */     println(this.rptSb.toString());
/*  171 */     printDGSubmitString();
/*  172 */     println(EACustom.getTOUDiv());
/*  173 */     buildReportFooter();
/*      */ 
/*      */     
/*  176 */     this.m_elist.dereference();
/*  177 */     this.m_elist = null;
/*  178 */     this.rsBundle = null;
/*  179 */     this.args = null;
/*  180 */     messageFormat = null;
/*  181 */     this.userxmlSb = null;
/*  182 */     this.rptSb = null;
/*  183 */     this.xmlgenSb = null;
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
/*      */   public void processThis(WTAASCCNSWABR paramWTAASCCNSWABR, Profile paramProfile, EntityItem paramEntityItem) throws TransformerConfigurationException, SAXException, MiddlewareRequestException, SQLException, MiddlewareException, IOException {
/*  204 */     boolean bool = getModelInfo(paramEntityItem);
/*  205 */     if (bool) {
/*  206 */       getSWFeatureInfo(paramEntityItem);
/*      */     } else {
/*  208 */       getProdInfo(paramEntityItem);
/*      */     } 
/*      */ 
/*      */     
/*  212 */     long l = System.currentTimeMillis();
/*  213 */     getPriceInfo(this.prodInfo);
/*  214 */     addDebug("get price info Time: " + 
/*  215 */         Stopwatch.format(System.currentTimeMillis() - l));
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
/*      */   private void getProdInfo(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  228 */     String str = null;
/*      */     
/*  230 */     ResultSet resultSet = null;
/*  231 */     PreparedStatement preparedStatement = null;
/*      */     
/*      */     try {
/*  234 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(
/*  235 */           queryForTMFInfo(paramEntityItem));
/*  236 */       resultSet = preparedStatement.executeQuery();
/*  237 */       while (resultSet.next()) {
/*  238 */         String str1 = resultSet.getString(6);
/*  239 */         String str2 = resultSet.getString(8);
/*      */         
/*  241 */         String str3 = resultSet.getString(1);
/*  242 */         String str4 = resultSet.getString(2);
/*  243 */         String str5 = resultSet.getString(3);
/*  244 */         String str6 = resultSet.getString(4);
/*  245 */         String str7 = resultSet.getString(5);
/*      */         
/*  247 */         String str8 = resultSet.getString(7);
/*  248 */         String str9 = resultSet.getString(9);
/*  249 */         String str10 = resultSet.getString(10);
/*  250 */         String str11 = resultSet.getString(11);
/*  251 */         String str12 = resultSet.getString(12);
/*  252 */         String str13 = resultSet.getString(13);
/*  253 */         String str14 = resultSet.getString(14);
/*  254 */         String str15 = resultSet.getString(15);
/*  255 */         String str16 = resultSet.getString(16);
/*  256 */         String str17 = resultSet.getString(17);
/*  257 */         String str18 = resultSet.getString(18);
/*      */         
/*  259 */         String str19 = resultSet.getString(19);
/*  260 */         String str20 = resultSet.getString(20);
/*  261 */         String str21 = resultSet.getString(21);
/*  262 */         String str22 = resultSet.getString(22);
/*  263 */         String str23 = resultSet.getString(23);
/*      */ 
/*      */         
/*  266 */         str = str1 + "_" + str2;
/*  267 */         if (this.prodInfo.get(str) != null) {
/*  268 */           SWFEATURE sWFEATURE1 = new SWFEATURE();
/*  269 */           sWFEATURE1.setSwFeatureId(str19);
/*  270 */           sWFEATURE1.setFEATURECODE(str20);
/*  271 */           sWFEATURE1.setPRICEDFEATURE(str21);
/*  272 */           sWFEATURE1.setCHARGEOPTION(str22);
/*  273 */           sWFEATURE1.setSFINVNAME(str23);
/*  274 */           WTAASCCNSWENTITY wTAASCCNSWENTITY1 = (WTAASCCNSWENTITY)this.prodInfo.get(str);
/*  275 */           ArrayList<SWFEATURE> arrayList1 = wTAASCCNSWENTITY1.getSWFEATURES();
/*  276 */           arrayList1.add(sWFEATURE1);
/*      */           
/*      */           continue;
/*      */         } 
/*  280 */         WTAASCCNSWENTITY wTAASCCNSWENTITY = new WTAASCCNSWENTITY();
/*  281 */         wTAASCCNSWENTITY.setCDOCNO(str3);
/*  282 */         wTAASCCNSWENTITY.setANNDATE(str4);
/*  283 */         wTAASCCNSWENTITY.setANNNUMBER(str5);
/*  284 */         wTAASCCNSWENTITY.setANNTYPE(str6);
/*  285 */         wTAASCCNSWENTITY.setRFASHRTTITLE(str7);
/*  286 */         wTAASCCNSWENTITY.setAVAILID(str1);
/*  287 */         wTAASCCNSWENTITY.setEFFECTIVEDATE(str8);
/*  288 */         wTAASCCNSWENTITY.setMODELID(str2);
/*  289 */         wTAASCCNSWENTITY.setPRODUCTVRM(str9);
/*  290 */         wTAASCCNSWENTITY.setMODELATR(str10);
/*  291 */         wTAASCCNSWENTITY.setMACHTYPEATR(str11);
/*  292 */         wTAASCCNSWENTITY.setINVNAME(str12);
/*  293 */         wTAASCCNSWENTITY.setMODMKTGDESC(str13);
/*  294 */         wTAASCCNSWENTITY.setUSGLIAPP(str15);
/*  295 */         wTAASCCNSWENTITY.setSFTWARRY(str14);
/*  296 */         wTAASCCNSWENTITY.setVOLUMEDISCOUNTELIG(str16);
/*  297 */         wTAASCCNSWENTITY.setEDUCALLOWMHGHSCH(str18);
/*  298 */         wTAASCCNSWENTITY.setKEYLCKPROTCT(str17);
/*      */         
/*  300 */         SWFEATURE sWFEATURE = new SWFEATURE();
/*  301 */         sWFEATURE.setSwFeatureId(str19);
/*  302 */         sWFEATURE.setFEATURECODE(str20);
/*  303 */         sWFEATURE.setPRICEDFEATURE(str21);
/*  304 */         sWFEATURE.setCHARGEOPTION(str22);
/*  305 */         sWFEATURE.setSFINVNAME(str23);
/*      */         
/*  307 */         ArrayList<SWFEATURE> arrayList = new ArrayList();
/*  308 */         arrayList.add(sWFEATURE);
/*  309 */         wTAASCCNSWENTITY.setSWFEATURES(arrayList);
/*      */         
/*  311 */         this.prodInfo.put(str, wTAASCCNSWENTITY);
/*      */       } 
/*      */     } finally {
/*      */       
/*  315 */       if (preparedStatement != null) {
/*      */         try {
/*  317 */           preparedStatement.close();
/*  318 */         } catch (SQLException sQLException) {
/*  319 */           sQLException.printStackTrace();
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
/*      */   private boolean getModelInfo(EntityItem paramEntityItem) throws MiddlewareException, SQLException {
/*  335 */     ResultSet resultSet = null;
/*  336 */     PreparedStatement preparedStatement = null;
/*  337 */     boolean bool = false;
/*      */     
/*      */     try {
/*  340 */       Connection connection = this.m_db.getPDHConnection();
/*      */       
/*  342 */       preparedStatement = connection.prepareStatement(queryForModelInfo(paramEntityItem));
/*  343 */       resultSet = preparedStatement.executeQuery();
/*      */       
/*  345 */       while (resultSet.next()) {
/*  346 */         String str1 = resultSet.getString(6);
/*  347 */         String str2 = resultSet.getString(8);
/*      */         
/*  349 */         String str3 = resultSet.getString(1);
/*  350 */         String str4 = resultSet.getString(2);
/*  351 */         String str5 = resultSet.getString(3);
/*  352 */         String str6 = resultSet.getString(4);
/*  353 */         String str7 = resultSet.getString(5);
/*      */         
/*  355 */         String str8 = resultSet.getString(7);
/*  356 */         String str9 = resultSet.getString(9);
/*  357 */         String str10 = resultSet.getString(10);
/*  358 */         String str11 = resultSet.getString(11);
/*  359 */         String str12 = resultSet.getString(12);
/*  360 */         String str13 = resultSet.getString(13);
/*  361 */         String str14 = resultSet.getString(14);
/*  362 */         String str15 = resultSet.getString(15);
/*  363 */         String str16 = resultSet.getString(16);
/*  364 */         String str17 = resultSet.getString(17);
/*  365 */         String str18 = resultSet.getString(18);
/*      */ 
/*      */         
/*  368 */         WTAASCCNSWENTITY wTAASCCNSWENTITY = new WTAASCCNSWENTITY();
/*  369 */         wTAASCCNSWENTITY.setCDOCNO(str3);
/*  370 */         wTAASCCNSWENTITY.setANNDATE(str4);
/*  371 */         wTAASCCNSWENTITY.setANNNUMBER(str5);
/*  372 */         wTAASCCNSWENTITY.setANNTYPE(str6);
/*  373 */         wTAASCCNSWENTITY.setRFASHRTTITLE(str7);
/*  374 */         wTAASCCNSWENTITY.setAVAILID(str1);
/*  375 */         wTAASCCNSWENTITY.setEFFECTIVEDATE(str8);
/*  376 */         wTAASCCNSWENTITY.setMODELID(str2);
/*  377 */         wTAASCCNSWENTITY.setPRODUCTVRM(str9);
/*  378 */         wTAASCCNSWENTITY.setMODELATR(str10);
/*  379 */         wTAASCCNSWENTITY.setMACHTYPEATR(str11);
/*  380 */         wTAASCCNSWENTITY.setINVNAME(str12);
/*  381 */         wTAASCCNSWENTITY.setMODMKTGDESC(str13);
/*  382 */         wTAASCCNSWENTITY.setUSGLIAPP(str15);
/*  383 */         wTAASCCNSWENTITY.setSFTWARRY(str14);
/*  384 */         wTAASCCNSWENTITY.setVOLUMEDISCOUNTELIG(str16);
/*  385 */         wTAASCCNSWENTITY.setEDUCALLOWMHGHSCH(str18);
/*  386 */         wTAASCCNSWENTITY.setKEYLCKPROTCT(str17);
/*      */         
/*  388 */         String str19 = str1 + "_" + str2;
/*  389 */         this.prodInfo.put(str19, wTAASCCNSWENTITY);
/*  390 */         bool = true;
/*      */       } 
/*      */     } finally {
/*      */       
/*  394 */       if (preparedStatement != null)
/*      */         try {
/*  396 */           preparedStatement.close();
/*  397 */         } catch (SQLException sQLException) {
/*  398 */           sQLException.printStackTrace();
/*      */         }  
/*      */     } 
/*  401 */     return bool;
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
/*  412 */     ResultSet resultSet = null;
/*  413 */     PreparedStatement preparedStatement = null;
/*      */     
/*      */     try {
/*  416 */       Connection connection = this.m_db.getPDHConnection();
/*  417 */       Iterator<Map.Entry> iterator = this.prodInfo.entrySet().iterator();
/*  418 */       while (iterator.hasNext()) {
/*  419 */         Map.Entry entry = iterator.next();
/*  420 */         WTAASCCNSWENTITY wTAASCCNSWENTITY = (WTAASCCNSWENTITY)entry.getValue();
/*  421 */         String str = wTAASCCNSWENTITY.getMODELID();
/*      */         
/*  423 */         ArrayList<SWFEATURE> arrayList = new ArrayList();
/*      */         
/*  425 */         preparedStatement = connection.prepareStatement(queryForFeatureInfo(str, paramEntityItem));
/*  426 */         resultSet = preparedStatement.executeQuery();
/*      */         
/*  428 */         while (resultSet.next()) {
/*  429 */           String str1 = resultSet.getString(1);
/*  430 */           String str2 = resultSet.getString(2);
/*  431 */           String str3 = resultSet.getString(3);
/*  432 */           String str4 = resultSet.getString(4);
/*  433 */           String str5 = resultSet.getString(5);
/*      */           
/*  435 */           SWFEATURE sWFEATURE = new SWFEATURE();
/*  436 */           sWFEATURE.setSwFeatureId(str1);
/*  437 */           sWFEATURE.setFEATURECODE(str5);
/*  438 */           sWFEATURE.setPRICEDFEATURE(str2);
/*  439 */           sWFEATURE.setCHARGEOPTION(str3);
/*  440 */           sWFEATURE.setSFINVNAME(str4);
/*      */           
/*  442 */           arrayList.add(sWFEATURE);
/*      */         } 
/*      */         
/*  445 */         wTAASCCNSWENTITY.setSWFEATURES(arrayList);
/*      */       } 
/*      */     } finally {
/*      */       
/*  449 */       if (preparedStatement != null) {
/*      */         try {
/*  451 */           preparedStatement.close();
/*  452 */         } catch (SQLException sQLException) {
/*  453 */           sQLException.printStackTrace();
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
/*      */   private void getPriceInfo(HashMap paramHashMap) throws SQLException, MiddlewareException {
/*  468 */     ResultSet resultSet = null;
/*  469 */     PreparedStatement preparedStatement = null;
/*  470 */     Iterator<Map.Entry> iterator = paramHashMap.entrySet().iterator();
/*      */     
/*      */     try {
/*  473 */       Connection connection = this.m_db.getODSConnection();
/*  474 */       while (iterator.hasNext()) {
/*  475 */         Map.Entry entry = iterator.next();
/*  476 */         WTAASCCNSWENTITY wTAASCCNSWENTITY = (WTAASCCNSWENTITY)entry.getValue();
/*  477 */         ArrayList<SWFEATURE> arrayList = wTAASCCNSWENTITY.getSWFEATURES();
/*  478 */         String str1 = wTAASCCNSWENTITY.getMODELATR();
/*  479 */         String str2 = wTAASCCNSWENTITY.getMACHTYPEATR();
/*  480 */         for (byte b = 0; b < arrayList.size(); b++) {
/*  481 */           SWFEATURE sWFEATURE = arrayList.get(b);
/*  482 */           String str3 = sWFEATURE.getFEATURECODE();
/*  483 */           String str4 = queryForFeaturePrice(str1, str2, str3);
/*      */           
/*  485 */           preparedStatement = connection.prepareStatement(str4);
/*  486 */           resultSet = preparedStatement.executeQuery();
/*  487 */           String str5 = null;
/*  488 */           byte b1 = 0;
/*  489 */           while (resultSet.next()) {
/*  490 */             str5 = resultSet.getString(1);
/*      */             
/*  492 */             addDebug("price info: price_value " + str5);
/*  493 */             sWFEATURE.setPRICEVALUE(covertPrice(str5, 7));
/*      */             
/*  495 */             b1++;
/*      */           } 
/*  497 */           if (b1 == 0) {
/*  498 */             str5 = "0      ";
/*      */             
/*  500 */             addDebug("price info: price_value " + str5);
/*  501 */             sWFEATURE.setPRICEVALUE(str5);
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  508 */       if (preparedStatement != null) {
/*      */         try {
/*  510 */           preparedStatement.close();
/*  511 */         } catch (SQLException sQLException) {
/*  512 */           sQLException.printStackTrace();
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
/*      */   private String covertPrice(String paramString, int paramInt) {
/*  524 */     String[] arrayOfString = splitStr(paramString, ".");
/*  525 */     String str = arrayOfString[0];
/*  526 */     for (int i = str.length(); i < paramInt; i++) {
/*  527 */       str = str + " ";
/*      */     }
/*  529 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String fixLength(String paramString1, int paramInt, String paramString2) {
/*  540 */     String str = null;
/*  541 */     if (paramString1 == null) {
/*  542 */       for (byte b = 4; b < paramInt; b++) {
/*  543 */         paramString1 = paramString1 + " ";
/*      */       }
/*  545 */       str = paramString1;
/*  546 */     } else if ("R".equals(paramString2)) {
/*  547 */       for (int i = paramString1.length(); i < paramInt; i++) {
/*  548 */         paramString1 = paramString1 + " ";
/*      */       }
/*  550 */       str = paramString1;
/*  551 */     } else if ("L".equals(paramString2)) {
/*  552 */       for (int i = paramString1.length(); i < paramInt; i++) {
/*  553 */         str = str + " ";
/*      */       }
/*  555 */       str = str + paramString1;
/*      */     } 
/*  557 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String queryForTMFInfo(EntityItem paramEntityItem) {
/*  565 */     StringBuffer stringBuffer = new StringBuffer();
/*  566 */     addDebug("Starting process data from DB");
/*      */     
/*  568 */     stringBuffer.append("select ann.CDOCNO, ann.ANNDATE, ann.ANNNUMBER, ann.ANNTYPE, ann.RFASHRTTITLE, f.entityid as availid, a1.EFFECTIVEDATE, m.entityid as modelid, m.PRODUCTVRM, m.MODELATR, m.MACHTYPEATR, m.INVNAME, m.MODMKTGDESC, m.SFTWARRY, m.USGLIAPP, m.VOLUMEDISCOUNTELIG, m.KEYLCKPROTCT, t.attributevalue as EDUCALLOWMHGHSCH, sf.entityid as featureid, sf.FEATURECODE, sf.PRICEDFEATURE, sf.CHARGEOPTION, sf.INVNAME from price.announcement ann ");
/*  569 */     stringBuffer.append("join price.avail a1 on a1.STATUS='Final' and a1.ANNCODENAME=ann.ANNCODENAME and a1.availType in ('Planned Availability','First Order')");
/*  570 */     stringBuffer.append("join opicm.flag f on f.entitytype='AVAIL' and f.attributecode='COUNTRYLIST' and f.attributevalue='1464' and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
/*  571 */     stringBuffer.append("join opicm.relator r1 on r1.entity1type='SWPRODSTRUCT' and r1.entity2type='AVAIL' and r1.entity2id=f.entityid ");
/*  572 */     stringBuffer.append("join price.swprodstruct sw on sw.entityid=r1.entity1id and sw.STATUS='Final' ");
/*  573 */     stringBuffer.append("join price.SWFEATURE sf on sf.entityid=sw.id1 and sf.STATUS='Final' ");
/*  574 */     stringBuffer.append("join price.model m on m.entityid=sw.id2 and m.STATUS='Final' and m.COFCAT='Software' ");
/*  575 */     stringBuffer.append("left join opicm.relator r3 on r3.entity1type='MODEL' and r3.entity1id=m.entityid and r3.entity2type='GEOMOD' ");
/*  576 */     stringBuffer.append("left join opicm.text t  on t.entitytype='GEOMOD' and t.entityid=r3.entity2id and t.attributecode='EDUCALLOWMHGHSCH' ");
/*  577 */     stringBuffer.append("where ann.entityid=");
/*  578 */     stringBuffer.append(paramEntityItem.getEntityID());
/*  579 */     stringBuffer.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 and r1.valto>current timestamp and r1.effto>current timestamp order by modelid,availid,featureid with ur");
/*      */     
/*  581 */     addDebug("SQL:" + stringBuffer);
/*  582 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String queryForModelInfo(EntityItem paramEntityItem) {
/*  590 */     addDebug("Get ANN related MODEL info---------------");
/*  591 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  593 */     stringBuffer.append("select ann.CDOCNO, ann.ANNDATE, ann.ANNNUMBER, ann.ANNTYPE, ann.RFASHRTTITLE, f.entityid as availid,a1.EFFECTIVEDATE,m.entityid as modelid,m.PRODUCTVRM,m.MODELATR,m.MACHTYPEATR,m.INVNAME,m.MODMKTGDESC, m.SFTWARRY, m.USGLIAPP, m.VOLUMEDISCOUNTELIG, m.KEYLCKPROTCT, t.attributevalue as EDUCALLOWMHGHSCH from price.announcement ann  ");
/*  594 */     stringBuffer.append("join price.avail a1 on a1.ANNCODENAME=ann.ANNCODENAME and a1.STATUS='Final' and a1.availType in ('Planned Availability','First Order') ");
/*  595 */     stringBuffer.append("join opicm.flag  f  on f.entitytype='AVAIL' and f.attributecode='COUNTRYLIST' and f.attributevalue='1464' and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
/*  596 */     stringBuffer.append("join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=f.entityid and r.valto>current timestamp and r.effto>current timestamp ");
/*  597 */     stringBuffer.append("join price.model m on m.entityid=r.entity1id and m.STATUS='Final' and m.COFCAT='Software' ");
/*  598 */     stringBuffer.append("left join opicm.relator r3 on r3.entity1type='MODEL' and r3.entity1id=m.entityid and r3.entity2type='GEOMOD' ");
/*  599 */     stringBuffer.append("left join opicm.text t  on t.entitytype='GEOMOD' and t.entityid=r3.entity2id and t.attributecode='EDUCALLOWMHGHSCH' ");
/*  600 */     stringBuffer.append("where ann.entityid = ");
/*  601 */     stringBuffer.append(paramEntityItem.getEntityID());
/*  602 */     stringBuffer.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 order by modelid,availid with ur");
/*      */     
/*  604 */     addDebug("SQL:" + stringBuffer);
/*  605 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String queryForFeatureInfo(String paramString, EntityItem paramEntityItem) {
/*  614 */     addDebug("Get ANN related Feature info---------------");
/*  615 */     StringBuffer stringBuffer = new StringBuffer();
/*  616 */     stringBuffer.append("select sf.entityid,sf.PRICEDFEATURE,sf.CHARGEOPTION,sf.INVNAME,sf.FEATURECODE from price.announcement ann ");
/*  617 */     stringBuffer.append("join price.avail a1 on a1.ANNCODENAME=ann.ANNCODENAME and a1.STATUS='Final' and a1.availType in ('Planned Availability','First Order') ");
/*  618 */     stringBuffer.append("join opicm.flag  f  on f.entitytype='AVAIL' and f.attributecode='COUNTRYLIST' and f.attributevalue='1464' and f.valto>current timestamp and f.effto>current timestamp and f.entityid = a1.entityid ");
/*  619 */     stringBuffer.append("join opicm.relator r1 on r1.entity1type='SWPRODSTRUCT' and r1.entity2type='AVAIL' and r1.entity2id=a1.entityid and r1.valto>current timestamp and r1.effto>current timestamp ");
/*  620 */     stringBuffer.append("join price.swprodstruct sw on sw.entityid=r1.entity1id and sw.STATUS='Final' and sw.id2 = ");
/*  621 */     stringBuffer.append(paramString);
/*  622 */     stringBuffer.append(" join price.SWFEATURE sf on sf.entityid=sw.id1 and sf.STATUS='Final' ");
/*  623 */     stringBuffer.append("where ann.entityid= ");
/*  624 */     stringBuffer.append(paramEntityItem.getEntityID());
/*  625 */     stringBuffer.append(" and ann.ANNSTATUS='Final' and ann.nlsid=1 with ur");
/*      */     
/*  627 */     addDebug("SQL:" + stringBuffer);
/*  628 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String queryForFeaturePrice(String paramString1, String paramString2, String paramString3) {
/*  637 */     StringBuffer stringBuffer = new StringBuffer();
/*  638 */     addDebug("Get SWFEATURE price info---------------");
/*  639 */     stringBuffer.append("select  price_value from price.price where action='I' and currency ='CAD' and offering_type='SWFEATURE' and machtypeatr='");
/*  640 */     stringBuffer.append(paramString2);
/*  641 */     stringBuffer.append("' and featurecode='");
/*  642 */     stringBuffer.append(paramString3);
/*  643 */     stringBuffer.append("' and start_date <= '");
/*  644 */     stringBuffer.append(this.annDate);
/*  645 */     stringBuffer.append("' and end_date >'");
/*  646 */     stringBuffer.append(this.annDate);
/*  647 */     stringBuffer.append("' and offering = '");
/*  648 */     stringBuffer.append(paramString2 + paramString1);
/*  649 */     stringBuffer.append("' and PRICE_POINT_VALUE = '");
/*  650 */     stringBuffer.append(paramString3);
/*  651 */     stringBuffer.append("' and price_point_type in ('SWF','WSF') and country = 'CA' and onshore='true' with ur");
/*      */     
/*  653 */     addDebug("SQL:" + stringBuffer);
/*  654 */     addDebug("SWFEATURE info: machtypeatr:" + paramString2 + ",featurecode:" + paramString3);
/*  655 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getReleaseNum(String paramString) {
/*  666 */     String str = "";
/*  667 */     if (paramString == null || "".equals(paramString)) {
/*  668 */       str = "0100";
/*      */     } else {
/*  670 */       String[] arrayOfString = splitStr(paramString, ".");
/*  671 */       if (arrayOfString.length > 1) {
/*  672 */         for (byte b = 1; b < arrayOfString.length; b++) {
/*  673 */           String str1 = arrayOfString[b].trim();
/*  674 */           if (str1.length() < 2) {
/*  675 */             str1 = "0" + str1;
/*      */           } else {
/*  677 */             str1 = str1.substring(0, 2);
/*      */           } 
/*  679 */           str = str + str1;
/*      */         } 
/*  681 */         if (str.length() > 4) {
/*  682 */           str = str.substring(0, 4);
/*  683 */         } else if (str.length() == 2) {
/*  684 */           str = str + "00";
/*      */         } 
/*  686 */       } else if (arrayOfString.length == 1) {
/*  687 */         str = "0100";
/*      */       } 
/*      */     } 
/*  690 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getVersionNum(String paramString) {
/*  700 */     String str = "";
/*  701 */     if (paramString == null || "".equals(paramString)) {
/*  702 */       str = "01";
/*      */     } else {
/*      */       
/*  705 */       String[] arrayOfString = splitStr(paramString, ".");
/*  706 */       String str1 = arrayOfString[0].trim();
/*  707 */       if (str1.startsWith("V")) {
/*  708 */         str1 = str1.substring(1, str1.length()).trim();
/*      */       }
/*  710 */       if (str1.length() < 2) {
/*  711 */         str = "0" + str1;
/*  712 */       } else if (str1.length() == 2) {
/*  713 */         str = str1;
/*      */       } else {
/*  715 */         str = str1.substring(0, 2);
/*      */       } 
/*      */     } 
/*      */     
/*  719 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String setFileName(String paramString) {
/*  728 */     StringBuffer stringBuffer = new StringBuffer(paramString.trim());
/*  729 */     String str1 = getNow();
/*      */     
/*  731 */     str1 = str1.replace(' ', '_');
/*  732 */     stringBuffer.append(str1 + ".cdata");
/*  733 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_rptpath", "/Dgq");
/*  734 */     if (!str2.endsWith("/")) {
/*  735 */       str2 = str2 + "/";
/*      */     }
/*  737 */     String str3 = str2 + stringBuffer.toString();
/*      */     
/*  739 */     addDebug("**** ffPathName: " + str3 + " ffFileName: " + stringBuffer.toString());
/*  740 */     return str3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean generateOutputFile(HashMap paramHashMap) {
/*  749 */     if (paramHashMap.size() == 0) {
/*  750 */       this.userxmlSb.append("File generate failed, for there is no qualified data related, please check \n");
/*  751 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  756 */       Iterator<Map.Entry> iterator = paramHashMap.entrySet().iterator();
/*  757 */       Vector<String> vector = new Vector(1);
/*  758 */       byte b = 1;
/*  759 */       while (iterator.hasNext()) {
/*  760 */         Map.Entry entry = iterator.next();
/*  761 */         WTAASCCNSWENTITY wTAASCCNSWENTITY = (WTAASCCNSWENTITY)entry.getValue();
/*  762 */         String str1 = wTAASCCNSWENTITY.getMODELATR();
/*  763 */         String str2 = wTAASCCNSWENTITY.getMACHTYPEATR();
/*  764 */         String str3 = setFileName(str2 + "-" + str1 + "(" + b + ")");
/*      */         
/*  766 */         generateFile(str3, wTAASCCNSWENTITY);
/*  767 */         vector.add(str3);
/*  768 */         b++;
/*      */       } 
/*      */       
/*  771 */       generateZipfile(vector);
/*  772 */       return true;
/*      */     }
/*  774 */     catch (Exception exception) {
/*  775 */       exception.printStackTrace();
/*  776 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateZipfile(Vector<String> paramVector) {
/*      */     try {
/*  784 */       FileOutputStream fileOutputStream = null;
/*  785 */       ZipOutputStream zipOutputStream = null;
/*  786 */       String str1 = getNow();
/*  787 */       String str2 = ABRServerProperties.getOutputPath() + this.annNumber + str1 + ".zip";
/*  788 */       this.zipFile = new File(str2);
/*      */       try {
/*  790 */         char c = 'ࠀ';
/*  791 */         fileOutputStream = new FileOutputStream(this.zipFile);
/*  792 */         zipOutputStream = new ZipOutputStream(fileOutputStream);
/*  793 */         for (byte b = 0; b < paramVector.size(); b++) {
/*  794 */           String str = paramVector.get(b);
/*  795 */           File file = new File(str);
/*  796 */           if (file.exists()) {
/*  797 */             zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
/*  798 */             BufferedInputStream bufferedInputStream = null;
/*      */             try {
/*  800 */               bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
/*      */               
/*  802 */               byte[] arrayOfByte = new byte[c]; int i;
/*  803 */               while ((i = bufferedInputStream.read(arrayOfByte, 0, c)) != -1) {
/*  804 */                 zipOutputStream.write(arrayOfByte, 0, i);
/*      */               }
/*  806 */               zipOutputStream.closeEntry();
/*  807 */               zipOutputStream.flush();
/*  808 */               addDebug("Zip file " + str + " successfully");
/*      */             } finally {
/*      */               
/*  811 */               if (bufferedInputStream != null) {
/*  812 */                 bufferedInputStream.close();
/*      */               }
/*      */             } 
/*      */             
/*  816 */             file.delete();
/*      */           } else {
/*  818 */             addError("Zip file..., Missing file " + str);
/*      */           } 
/*      */         } 
/*      */       } finally {
/*      */         
/*  823 */         if (zipOutputStream != null) {
/*  824 */           zipOutputStream.flush();
/*  825 */           zipOutputStream.close();
/*      */         } 
/*  827 */         if (fileOutputStream != null) {
/*  828 */           fileOutputStream.flush();
/*  829 */           fileOutputStream.close();
/*      */         } 
/*      */       } 
/*  832 */       paramVector.clear();
/*  833 */       paramVector = null;
/*      */     
/*      */     }
/*  836 */     catch (Exception exception) {
/*  837 */       exception.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void writeln(OutputStreamWriter paramOutputStreamWriter, String paramString) throws IOException {
/*  842 */     paramOutputStreamWriter.write(paramString + "\r\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateFile(String paramString, WTAASCCNSWENTITY paramWTAASCCNSWENTITY) throws Exception {
/*  853 */     FileOutputStream fileOutputStream = new FileOutputStream(paramString);
/*  854 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*      */     try {
/*  856 */       String str1 = paramWTAASCCNSWENTITY.getEFFECTIVEDATE();
/*  857 */       String str2 = paramWTAASCCNSWENTITY.getMACHTYPEATR() + "-" + paramWTAASCCNSWENTITY.getMODELATR();
/*  858 */       String str3 = paramWTAASCCNSWENTITY.getANNDATE();
/*  859 */       String str4 = paramWTAASCCNSWENTITY.getPRODUCTVRM();
/*  860 */       String str5 = getVersionNum(str4);
/*  861 */       String str6 = getReleaseNum(str4);
/*  862 */       String str7 = fixLength(paramWTAASCCNSWENTITY.getCDOCNO(), 8, "R");
/*  863 */       String str8 = "", str9 = "", str10 = "", str11 = "", str12 = "", str13 = "", str14 = "", str15 = "", str16 = "", str17 = "", str18 = "", str19 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  873 */       str8 = "RFA       :" + paramWTAASCCNSWENTITY.getANNNUMBER() + "\r\nDOCNUM    :" + str7 + "\r\nSECTION   :" + this.rsBundle.getString("SECTION") + "\r\nDATE      :" + str3 + "\r\nREVDATE   :" + str3 + "\r\nACTION    :" + this.rsBundle.getString("ACTION") + "\r\nCUSTOMER  :" + this.rsBundle.getString("CUSTOMER") + "\r\nALIAS     :" + this.rsBundle.getString("ALIAS") + "\r\nTYPE      :" + this.rsBundle.getString("TYPE") + "\r\nESW       :" + this.rsBundle.getString("ESW") + "";
/*  874 */       str9 = "AVAILDATE :" + str1;
/*  875 */       str10 = "TITLE     :" + str2 + "     " + getValue(paramWTAASCCNSWENTITY.getRFASHRTTITLE());
/*      */       
/*  877 */       String str20 = paramWTAASCCNSWENTITY.getKEYLCKPROTCT();
/*  878 */       if ("Yes".equals(str20)) {
/*  879 */         str20 = "Y";
/*      */       } else {
/*  881 */         str20 = "N";
/*      */       } 
/*  883 */       str11 = "PROG      :" + str2 + "     " + str5 + " PG Y " + str20 + " " + str20 + " US " + fixLength(getValue(paramWTAASCCNSWENTITY.getINVNAME()), 28);
/*  884 */       int i = 107 - str11.length();
/*  885 */       for (byte b1 = 1; b1 < i; b1++) {
/*  886 */         str11 = str11 + " ";
/*      */       }
/*  888 */       str11 = str11 + "\\\\ " + fixLength(getValue(paramWTAASCCNSWENTITY.getINVNAME()), 28);
/*      */       
/*  890 */       String str21 = paramWTAASCCNSWENTITY.getSFTWARRY();
/*  891 */       if ("No".equals(str21)) {
/*  892 */         str21 = "N";
/*      */       } else {
/*  894 */         str21 = "Y";
/*      */       } 
/*  896 */       String str22 = paramWTAASCCNSWENTITY.getUSGLIAPP();
/*  897 */       if ("Yes".equals(str22)) {
/*  898 */         str22 = "Y";
/*      */       } else {
/*  900 */         str22 = "N";
/*      */       } 
/*      */       
/*  903 */       String str23 = paramWTAASCCNSWENTITY.getEDUCALLOWMHGHSCH();
/*  904 */       if (str23 == null || "".equals(str23)) {
/*  905 */         str23 = "00";
/*      */       }
/*      */       
/*  908 */       String str24 = paramWTAASCCNSWENTITY.getVOLUMEDISCOUNTELIG();
/*  909 */       if ("No".equals(str24)) {
/*  910 */         str24 = "N";
/*      */       } else {
/*  912 */         str24 = "Y";
/*      */       } 
/*      */       
/*  915 */       str12 = "TERMS     :" + str2 + "     IPL N N N N " + str22 + " N N N 00 " + str21 + " Y N N N 6 " + str23 + " " + str23 + " " + str24 + " N PLA B 40";
/*  916 */       str13 = "RELEASE   :" + str2 + "     " + str6;
/*  917 */       str14 = "PROGACT   :" + str2 + "     " + str6 + " AV " + str1 + " ENABLESW " + str7 + " " + str3 + " " + paramWTAASCCNSWENTITY.getANNNUMBER();
/*  918 */       str15 = "PROGACT   :" + str2 + "     " + str6 + " AN " + str3 + " ENABLESW " + str7 + " " + str3 + " " + paramWTAASCCNSWENTITY.getANNNUMBER();
/*      */       
/*  920 */       str16 = "FUNCTION  :" + str2 + "     " + str6 + " X BAS REGISTRATION";
/*  921 */       str17 = "FUNCTION  :" + str2 + "     " + str6 + " Z PRC PROCESS FEATURES";
/*  922 */       str18 = "FUNCTION  :" + str2 + "     " + str6 + " A BAS BASE FUNCTION";
/*      */       
/*  924 */       str19 = "FEATURE   :" + str2 + "     " + str6 + " 3444 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Serial Number Only\r\nFEATURE   :" + str2 + "     " + str6 + " 3470 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Ship Media Only\r\nFEATURE   :" + str2 + "     " + str6 + " 3471 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Ship Doc Only\r\nFEATURE   :" + str2 + "     " + str6 + " 3480 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Ship Media Updates Only\r\nFEATURE   :" + str2 + "     " + str6 + " 3481 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Ship Doc Updates Only\r\nFEATURE   :" + str2 + "     " + str6 + " 3482 Z OT -    BASE  OTHER    -   -    N   N  NC N 0         Advanced Documentation";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  930 */       ArrayList<SWFEATURE> arrayList = paramWTAASCCNSWENTITY.getSWFEATURES();
/*  931 */       StringBuffer stringBuffer1 = new StringBuffer();
/*  932 */       for (byte b2 = 0; b2 < arrayList.size(); b2++) {
/*  933 */         SWFEATURE sWFEATURE = arrayList.get(b2);
/*  934 */         String str26 = null;
/*  935 */         String str27 = sWFEATURE.getPRICEDFEATURE();
/*  936 */         String str28 = sWFEATURE.getCHARGEOPTION();
/*  937 */         stringBuffer1.append("\r\n");
/*  938 */         if ("No".equals(str27)) {
/*  939 */           str26 = "NC";
/*  940 */           stringBuffer1.append("FEATURE   :" + str2 + "     " + str6 + " " + sWFEATURE.getFEATURECODE() + " Z OT -    BASE  OTHER    -   -    N   N  " + str26 + " N " + sWFEATURE.getPRICEVALUE() + "   " + sWFEATURE.getSFINVNAME());
/*  941 */         } else if ("Yes".equals(str27) && 
/*  942 */           str28 != null && !"".equals(str28)) {
/*      */           
/*  944 */           int k = str28.indexOf("-");
/*  945 */           str26 = str28.substring(0, k - 1);
/*  946 */           stringBuffer1.append("FEATURE   :" + str2 + "     " + str6 + " " + sWFEATURE.getFEATURECODE() + " A LC -    BASE  -        -   -    N   N  " + str26 + " N " + sWFEATURE.getPRICEVALUE() + "   " + sWFEATURE.getSFINVNAME());
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  952 */       StringBuffer stringBuffer2 = new StringBuffer();
/*  953 */       for (byte b3 = 0; b3 < arrayList.size(); b3++) {
/*  954 */         SWFEATURE sWFEATURE = arrayList.get(b3);
/*      */ 
/*      */         
/*  957 */         stringBuffer2.append("\r\n");
/*  958 */         stringBuffer2.append("FEATACT   :" + str2 + "     " + sWFEATURE.getFEATURECODE() + " AV " + paramWTAASCCNSWENTITY.getEFFECTIVEDATE() + " ENABLESW " + str7 + " " + str3 + " " + paramWTAASCCNSWENTITY.getANNNUMBER());
/*      */       } 
/*      */ 
/*      */       
/*  962 */       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*  963 */       String str25 = simpleDateFormat.format(new Date());
/*  964 */       int j = str1.compareTo(str25);
/*      */       
/*  966 */       if (j >= 0) {
/*  967 */         writeln(outputStreamWriter, str8);
/*  968 */         writeln(outputStreamWriter, str9);
/*  969 */         writeln(outputStreamWriter, str10);
/*  970 */         writeln(outputStreamWriter, str11);
/*  971 */         writeln(outputStreamWriter, str12);
/*  972 */         writeln(outputStreamWriter, str13);
/*  973 */         writeln(outputStreamWriter, str14);
/*  974 */         writeln(outputStreamWriter, str15);
/*  975 */         writeln(outputStreamWriter, str16);
/*  976 */         writeln(outputStreamWriter, str17);
/*  977 */         writeln(outputStreamWriter, str18);
/*  978 */         outputStreamWriter.write(str19);
/*  979 */         outputStreamWriter.write(stringBuffer1.toString());
/*      */       } else {
/*      */         
/*  982 */         writeln(outputStreamWriter, str8);
/*  983 */         writeln(outputStreamWriter, str10);
/*  984 */         writeln(outputStreamWriter, str11);
/*  985 */         writeln(outputStreamWriter, str13);
/*  986 */         writeln(outputStreamWriter, str15);
/*  987 */         writeln(outputStreamWriter, str16);
/*  988 */         writeln(outputStreamWriter, str17);
/*  989 */         outputStreamWriter.write(str18);
/*  990 */         outputStreamWriter.write(stringBuffer1.toString());
/*  991 */         outputStreamWriter.write(stringBuffer2.toString());
/*      */       } 
/*      */       
/*  994 */       this.userxmlSb.append(paramWTAASCCNSWENTITY.getMACHTYPEATR() + "-" + paramWTAASCCNSWENTITY.getMODELATR() + " File generate success \n");
/*  995 */     } catch (IOException iOException) {
/*      */       
/*  997 */       throw new Exception("File create failed " + paramString);
/*      */     } finally {
/*  999 */       outputStreamWriter.flush();
/* 1000 */       outputStreamWriter.close();
/*      */       try {
/* 1002 */         if (outputStreamWriter != null) {
/* 1003 */           outputStreamWriter.close();
/*      */         }
/* 1005 */       } catch (Exception exception) {
/* 1006 */         throw new Exception("File create failed " + paramString);
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
/*      */   private String fixLength(String paramString, int paramInt) {
/* 1020 */     if (paramString.length() >= paramInt) {
/* 1021 */       return paramString.substring(0, paramInt);
/*      */     }
/* 1023 */     String str = "";
/* 1024 */     for (byte b = 0; b < paramInt - paramString.length(); b++) {
/* 1025 */       str = str + " ";
/*      */     }
/* 1027 */     paramString = paramString + str;
/*      */     
/* 1029 */     return paramString;
/*      */   }
/*      */   
/*      */   public String getValue(String paramString) {
/* 1033 */     if (paramString == null) {
/* 1034 */       return "";
/*      */     }
/* 1036 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String dateFormat(String paramString) {
/* 1047 */     SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
/* 1048 */     SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
/* 1049 */     String str = null;
/*      */     try {
/* 1051 */       str = simpleDateFormat1.format(simpleDateFormat2.parse(paramString));
/* 1052 */     } catch (ParseException parseException) {
/* 1053 */       addDebug("date parse Exception: " + parseException);
/* 1054 */       parseException.printStackTrace();
/*      */     } 
/* 1056 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] splitStr(String paramString1, String paramString2) {
/* 1066 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1, paramString2);
/* 1067 */     String[] arrayOfString = new String[stringTokenizer.countTokens()];
/* 1068 */     byte b = 0;
/* 1069 */     while (stringTokenizer.hasMoreTokens()) {
/* 1070 */       arrayOfString[b] = stringTokenizer.nextToken();
/* 1071 */       b++;
/*      */     } 
/* 1073 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendMail(File paramFile) throws Exception {
/* 1084 */     FileInputStream fileInputStream = null;
/*      */     try {
/* 1086 */       fileInputStream = new FileInputStream(paramFile);
/* 1087 */       int i = fileInputStream.available();
/* 1088 */       byte[] arrayOfByte = new byte[i];
/* 1089 */       fileInputStream.read(arrayOfByte);
/* 1090 */       setAttachByteForDG(arrayOfByte);
/* 1091 */       getABRItem().setFileExtension("zip");
/* 1092 */       addDebug("Sending mail for file " + paramFile);
/* 1093 */     } catch (IOException iOException) {
/* 1094 */       addDebug("sendMail IO Exception");
/*      */     }
/*      */     finally {
/*      */       
/* 1098 */       if (fileInputStream != null) {
/* 1099 */         fileInputStream.close();
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
/*      */   protected String getAttributeFlagValue(EntityItem paramEntityItem, String paramString) {
/* 1112 */     return PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*      */   }
/*      */   
/*      */   public String getDescription() {
/* 1116 */     return "WTAASSTATUSABR";
/*      */   }
/*      */   
/*      */   public String getABRVersion() {
/* 1120 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1127 */     addOutput(paramString);
/* 1128 */     setReturnCode(-1);
/*      */   }
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1132 */     if (3 <= DEBUG_LVL) {
/* 1133 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/* 1145 */     if (paramInt <= DEBUG_LVL) {
/* 1146 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/* 1163 */     return this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, paramString), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*      */             
/* 1165 */             .getEntityType(), paramEntityItem.getEntityID()) });
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
/* 1183 */     EntityList entityList = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*      */             
/* 1185 */             .getEntityType(), paramEntityItem.getEntityID()) });
/* 1186 */     return entityList.getParentEntityGroup().getEntityItem(0);
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
/* 1204 */     EntityList entityList = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, paramString, paramInt) });
/*      */ 
/*      */     
/* 1207 */     return entityList.getParentEntityGroup().getEntityItem(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 1214 */     return this.m_db;
/*      */   }
/*      */   
/*      */   protected String getABRTime() {
/* 1218 */     return String.valueOf(System.currentTimeMillis());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/* 1225 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String buildAbrHeader() {
/* 1235 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1242 */     MessageFormat messageFormat = new MessageFormat(str);
/* 1243 */     this.args[0] = this.m_prof.getOPName();
/* 1244 */     this.args[1] = this.m_prof.getRoleDescription();
/* 1245 */     this.args[2] = this.m_prof.getWGName();
/* 1246 */     this.args[3] = this.t2DTS;
/* 1247 */     this.args[4] = "WATTS CCN SW ABR feed trigger<br/>" + this.xmlgenSb.toString();
/* 1248 */     this.args[5] = getABRVersion();
/* 1249 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1259 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1262 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1263 */     EANList eANList = entityGroup.getMetaAttribute();
/*      */     
/* 1265 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1266 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1267 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute
/* 1268 */             .getAttributeCode(), ", ", "", false));
/* 1269 */       stringBuffer.append(" ");
/*      */     } 
/* 1271 */     return stringBuffer.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WTAASCCNSWABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */