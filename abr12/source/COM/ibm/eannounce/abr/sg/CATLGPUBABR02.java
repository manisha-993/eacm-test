/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EANObject;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.eannounce.objects.SalesStatusInfo;
/*      */ import COM.ibm.eannounce.objects.SalesStatusItem;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnStatus;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Hashtable;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CATLGPUBABR02
/*      */   extends PokBaseABR
/*      */ {
/*      */   private static final String STARTDATE = "1980-01-01-00.00.00.000000";
/*      */   private static final String FOREVER = "9999-12-31-00.00.00.000000";
/*      */   private static final String ABR = "CATLGPUBABR02";
/*      */   private static final String ATT_CATSEOID = "CATSEOID";
/*      */   private static final String ATT_CATADDTOCART = "CATADDTOCART";
/*      */   private static final String ATT_CATBUYABLE = "CATBUYABLE";
/*      */   private static final String ATT_CATHIDE = "CATHIDE";
/*      */   private static final String ATT_CATSALESSTATUS = "CATSALESSTATUS";
/*      */   private static final String ATT_CATOFFTYPE = "CATOFFTYPE";
/*      */   private static final String ATT_CATMACHTYPE = "CATMACHTYPE";
/*      */   private static final String ATT_CATMODEL = "CATMODEL";
/*      */   private static final String ATT_COFCAT = "COFCAT";
/*      */   private static final String HW = "100";
/*      */   private static final String SW = "101";
/*      */   private static final String SVC = "102";
/*      */   private static final String ATT_PT = "PUBTO";
/*      */   private static final String ATT_SEOID = "SEOID";
/*      */   private static final String ATT_CATWORKFLOW = "CATWORKFLOW";
/*      */   private static final String ATT_CATLGOFFPUBLASTRUN = "CATLGOFFPUBLASTRUN";
/*      */   private static final String ATT_OFFCOUNTRY = "OFFCOUNTRY";
/*      */   private static final int I64 = 64;
/*  152 */   private PDGUtility m_utility = new PDGUtility();
/*  153 */   private String strUpdatedBy = null;
/*  154 */   private String strOFFCOUNTRY = null;
/*  155 */   private String strCurrentDate = null;
/*  156 */   private String strCATLGOFFPUBLASTRUN = null;
/*  157 */   private int updateCount = 0;
/*      */   private boolean outputWarning = false;
/*      */   private static final String SS_EXTRACTNAME = "EXCATLGPUBVE";
/*  160 */   private Hashtable sskeyTbl = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*      */     try {
/*  169 */       EntityGroup entityGroup = null;
/*  170 */       EntityItem entityItem = null;
/*  171 */       String str3 = null;
/*      */       
/*  173 */       String str4 = null;
/*      */       
/*  175 */       start_ABRBuild(false);
/*  176 */       println(EACustom.getDocTypeHtml());
/*      */       
/*  178 */       println("<head>" + EACustom.NEWLINE + 
/*  179 */           EACustom.getMetaTags(getDescription()) + EACustom.NEWLINE + 
/*  180 */           EACustom.getCSS() + EACustom.NEWLINE + 
/*  181 */           EACustom.getTitle(getDescription()) + EACustom.NEWLINE + "</head>" + EACustom.NEWLINE + "<body id=\"ibm-com\">");
/*      */ 
/*      */ 
/*      */       
/*  185 */       println(EACustom.getMastheadDiv());
/*      */       
/*  187 */       setReturnCode(0);
/*      */       
/*  189 */       str3 = this.m_db.getDates().getNow();
/*  190 */       this.strCurrentDate = str3.substring(0, 10);
/*  191 */       this.strUpdatedBy = "CATLGPUBABR02" + getRevision() + str3;
/*      */ 
/*      */       
/*  194 */       this.m_prof = this.m_utility.setProfValOnEffOn(this.m_db, this.m_prof);
/*      */       
/*  196 */       printHeader(str3);
/*      */ 
/*      */       
/*  199 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/*  200 */       entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*      */       
/*  202 */       println("<p class=\"ibm-intro ibm-alternate-three\"><em>Catalog Country: " + entityItem.getKey() + "</em></p>");
/*      */       
/*  204 */       printNavigateAttributes(entityItem, entityGroup);
/*      */ 
/*      */ 
/*      */       
/*  208 */       this.strCATLGOFFPUBLASTRUN = this.m_utility.getAttrValue(entityItem, "CATLGOFFPUBLASTRUN");
/*  209 */       if (this.strCATLGOFFPUBLASTRUN == null || this.strCATLGOFFPUBLASTRUN.length() <= 0) {
/*  210 */         OPICMList oPICMList1 = new OPICMList();
/*  211 */         this.strCATLGOFFPUBLASTRUN = "1980-01-01-00.00.00.000000";
/*  212 */         oPICMList1.put("CATLGOFFPUBLASTRUN", "CATLGOFFPUBLASTRUN=" + this.strCATLGOFFPUBLASTRUN);
/*  213 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList1);
/*      */       }
/*  215 */       else if (!this.m_utility.isDateFormat(this.strCATLGOFFPUBLASTRUN)) {
/*  216 */         println("<h2>CATLGOFFPUBLASTRUN is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000.</h2>");
/*  217 */         setReturnCode(-1);
/*      */       }
/*  219 */       else if (this.strCATLGOFFPUBLASTRUN.length() == 10) {
/*  220 */         OPICMList oPICMList1 = new OPICMList();
/*  221 */         this.strCATLGOFFPUBLASTRUN += "-00.00.00.000000";
/*  222 */         oPICMList1.put("CATLGOFFPUBLASTRUN", "CATLGOFFPUBLASTRUN=" + this.strCATLGOFFPUBLASTRUN);
/*  223 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList1);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  228 */       this.strOFFCOUNTRY = this.m_utility.getAttrValue(entityItem, "OFFCOUNTRY");
/*  229 */       str4 = this.m_utility.getAttrValueDesc(entityItem, "OFFCOUNTRY");
/*  230 */       if (this.strOFFCOUNTRY == null || this.strOFFCOUNTRY.length() <= 0) {
/*  231 */         println("<h2>OFFCOUNTRY is blank.</h2>");
/*  232 */         setReturnCode(-1);
/*      */       } 
/*      */       
/*  235 */       println("<!-- CATLGOFFPUBLASTRUN:" + this.strCATLGOFFPUBLASTRUN + " OFFCOUNTRY: " + str4 + " -->");
/*      */       
/*  237 */       if (getReturnCode() == 0) {
/*      */ 
/*      */ 
/*      */         
/*  241 */         EANList eANList = null;
/*      */         
/*  243 */         ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTCATLGCNTRYGAA1");
/*  244 */         String str5 = this.m_utility.getGENAREACODE(this.m_db, this.m_prof, extractActionItem, entityItem, this.strOFFCOUNTRY);
/*  245 */         String str6 = "";
/*  246 */         if (str5.indexOf(":") > 0) {
/*  247 */           int i = str5.indexOf(":");
/*  248 */           str6 = str5.substring(i + 1);
/*  249 */           str5 = str5.substring(0, i);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  255 */         eANList = getFilteredSalesStatus(str6, this.strCurrentDate);
/*  256 */         if (eANList.size() <= 0) {
/*  257 */           println("<h2>No updated salesstatus records found for SEO, MODEL or SWMODEL with offering country: " + str4 + ", GENAREACODE:" + str6 + "</h2>");
/*      */         }
/*      */         else {
/*      */           
/*  261 */           Vector<String> vector = new Vector();
/*  262 */           println("<!-- Found " + eANList.size() + " salesstatus records found SEO, MODEL or SWMODEL with offering country: " + str4 + ", GENAREACODE:" + str6 + " -->");
/*      */ 
/*      */           
/*  265 */           ExtractActionItem extractActionItem1 = new ExtractActionItem(null, this.m_db, this.m_prof, "EXCATLGPUBVE");
/*      */           
/*      */           byte b;
/*  268 */           for (b = 0; b < eANList.size(); b++) {
/*      */             
/*  270 */             SalesStatusItem salesStatusItem = (SalesStatusItem)eANList.getAt(b);
/*  271 */             String str = salesStatusItem.getMATERIALSTATUS();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  279 */             Vector<EntityItem> vector1 = findCatlgpub(salesStatusItem);
/*  280 */             println("<!-- Checked SS[" + b + "] " + salesStatusItem.dump(true) + " found " + vector1.size() + " CATLGPUB -->");
/*  281 */             for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*  282 */               String str7 = null;
/*  283 */               String str8 = null;
/*  284 */               int i = 0;
/*  285 */               EntityItem entityItem1 = vector1.elementAt(b1);
/*  286 */               D.ebug(4, "CATLGPUBABR02 found " + entityItem1.getKey());
/*      */               
/*  288 */               str7 = this.m_utility.getAttrValue(entityItem1, "PUBTO");
/*  289 */               if (str7.length() == 0) {
/*  290 */                 str7 = "9999-12-31-00.00.00.000000".substring(0, 10);
/*      */               }
/*  292 */               str8 = this.m_utility.getAttrValue(entityItem1, "CATOFFTYPE");
/*  293 */               println("<!-- found " + entityItem1.getKey() + " [" + b1 + "] curDate: " + this.strCurrentDate + " PUBTO: " + str7 + " " + "CATOFFTYPE" + ": " + str8 + " -->");
/*      */               
/*  295 */               i = this.m_utility.dateCompare(str7, this.strCurrentDate);
/*      */               
/*  297 */               if (str.equals("ZJ")) {
/*  298 */                 if (i == 1) {
/*  299 */                   String str9 = "Sales Status Withdraw prior to the Publish To date. <br />SALES_STATUS.MATERIALSTATUS: " + str;
/*      */                   
/*  301 */                   printAllAttributes(str9, entityItem1);
/*  302 */                   setReturnCode(-1);
/*      */                 } 
/*  304 */                 if (str8.equals("LSEO")) {
/*  305 */                   String str9 = this.m_utility.getAttrValue(entityItem1, "CATSEOID");
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  310 */                   if (!vector.contains(str9)) {
/*  311 */                     vector.add(str9);
/*      */                   
/*      */                   }
/*      */                 }
/*      */               
/*      */               }
/*  317 */               else if (i == 2) {
/*  318 */                 String str9 = "Sales Status is not Withdraw after the Publish To date. <br />SALES_STATUS.MATERIALSTATUS: " + str;
/*      */                 
/*  320 */                 printAllAttributes(str9, entityItem1);
/*  321 */                 setReturnCode(-1);
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  331 */               updateCatlgpub(entityItem1, str, extractActionItem1);
/*      */             } 
/*      */             
/*  334 */             vector1.clear();
/*      */           } 
/*      */           
/*  337 */           if (vector.size() > 0) {
/*  338 */             for (b = 0; b < vector.size(); b++) {
/*  339 */               String str = vector.elementAt(b);
/*      */ 
/*      */               
/*  342 */               checkParentLseoBundleCatlgpub(str, "ZJ");
/*      */             } 
/*      */             
/*  345 */             vector.clear();
/*      */           } 
/*      */           
/*  348 */           eANList.clear();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  354 */       OPICMList oPICMList = new OPICMList();
/*  355 */       oPICMList.put("CATLGOFFPUBLASTRUN", "CATLGOFFPUBLASTRUN=" + str3);
/*  356 */       this.m_utility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*  357 */       this.sskeyTbl.clear();
/*      */       
/*  359 */       println("<!-- Updated a total of " + this.updateCount + " CATLGPUB entities -->");
/*  360 */     } catch (SBRException sBRException) {
/*  361 */       String str2 = sBRException.toString();
/*  362 */       int i = str2.indexOf("(ok)");
/*  363 */       if (i < 0) {
/*  364 */         setReturnCode(-2);
/*  365 */         println("<h3><span style=\"color:#c00; font-weight:bold;\">Generate Data error: <pre>" + str2 + "</pre></span></h3>");
/*      */         
/*  367 */         logError(sBRException.toString());
/*      */       } else {
/*  369 */         str2 = str2.substring(0, i);
/*  370 */         println("<pre>" + str2 + "</pre>");
/*      */       } 
/*  372 */     } catch (Throwable throwable) {
/*  373 */       String[] arrayOfString = new String[1];
/*  374 */       StringWriter stringWriter = new StringWriter();
/*  375 */       String str3 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  376 */       String str4 = "<pre>{0}</pre>";
/*  377 */       MessageFormat messageFormat = new MessageFormat(str3);
/*  378 */       setReturnCode(-1);
/*  379 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  381 */       arrayOfString[0] = throwable.getMessage();
/*  382 */       println(messageFormat.format(arrayOfString));
/*  383 */       messageFormat = new MessageFormat(str4);
/*  384 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/*  385 */       println(messageFormat.format(arrayOfString));
/*  386 */       logError("Exception: " + throwable.getMessage());
/*  387 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*  389 */       String str1 = null;
/*  390 */       String str2 = buildMessage("IAB2016I: %1# has %2#.", new String[] { getABRDescription(), (getReturnCode() == 0) ? "Passed" : "Failed" });
/*  391 */       println("<p><b>" + str2 + "</b></p>");
/*  392 */       log(str2);
/*      */ 
/*      */       
/*  395 */       str1 = getABRDescription() + ":" + getEntityType() + ":" + getEntityID();
/*  396 */       if (str1.length() > 64) {
/*  397 */         str1 = str1.substring(0, 64);
/*      */       }
/*  399 */       setDGTitle(str1);
/*  400 */       setDGRptName("CATLGPUBABR02");
/*      */ 
/*      */       
/*  403 */       setDGString(getABRReturnCode());
/*      */       
/*  405 */       println(EACustom.getTOUDiv());
/*      */ 
/*      */ 
/*      */       
/*  409 */       printDGSubmitString();
/*  410 */       buildReportFooter();
/*      */ 
/*      */       
/*  413 */       if (!isReadOnly()) {
/*  414 */         clearSoftLock();
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
/*      */   private EANList getFilteredSalesStatus(String paramString1, String paramString2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  434 */     String str1 = "CATLGPUBABR02 getFilteredSalesStatus method ";
/*      */     
/*  436 */     ReturnDataResultSet returnDataResultSet = null;
/*  437 */     ResultSet resultSet = null;
/*  438 */     ReturnStatus returnStatus = new ReturnStatus(-1);
/*  439 */     EANList eANList = new EANList();
/*  440 */     String str2 = this.m_utility.getDate(paramString2, 1);
/*      */     
/*  442 */     D.ebug(4, str1 + paramString1 + " currentDate:" + paramString2 + " tomorrow:" + str2);
/*      */     try {
/*  444 */       resultSet = this.m_db.callGBL9305(returnStatus, paramString1);
/*  445 */       returnDataResultSet = new ReturnDataResultSet(resultSet);
/*      */     } finally {
/*  447 */       if (resultSet != null) {
/*  448 */         resultSet.close();
/*      */       }
/*  450 */       resultSet = null;
/*  451 */       this.m_db.commit();
/*  452 */       this.m_db.freeStatement();
/*  453 */       this.m_db.isPending();
/*      */     } 
/*  455 */     if (returnDataResultSet != null) {
/*  456 */       for (byte b = 0; b < returnDataResultSet.size(); b++) {
/*  457 */         String str3 = returnDataResultSet.getColumn(b, 0).trim();
/*  458 */         String str4 = returnDataResultSet.getColumn(b, 1).trim();
/*  459 */         String str5 = returnDataResultSet.getColumn(b, 2).trim();
/*  460 */         String str6 = returnDataResultSet.getColumn(b, 3).trim();
/*  461 */         String str7 = returnDataResultSet.getColumn(b, 4).trim();
/*  462 */         String str8 = returnDataResultSet.getColumn(b, 5).trim();
/*  463 */         String str9 = returnDataResultSet.getColumn(b, 6).trim();
/*  464 */         String str10 = returnDataResultSet.getColumn(b, 7).trim();
/*  465 */         this.m_db.debug(4, str1 + " gbl9305 result:" + str3 + ":" + str4 + ":" + str5 + ":" + str6 + ":" + str7 + ":" + str8 + ":" + str9 + ":" + str10);
/*      */ 
/*      */ 
/*      */         
/*  469 */         if ((str5.equalsIgnoreCase("MODEL") || str5
/*  470 */           .equalsIgnoreCase("SWMODEL") || str5
/*  471 */           .equalsIgnoreCase("SEO")) && 
/*  472 */           str10.equals("N")) {
/*  473 */           boolean bool = false;
/*      */           
/*  475 */           int i = this.m_utility.longDateCompare(str9, this.strCATLGOFFPUBLASTRUN);
/*  476 */           if (i == 1 && str8
/*  477 */             .compareTo(paramString2) <= 0) {
/*  478 */             bool = true;
/*      */           }
/*      */           
/*  481 */           if (str8.equals(str2)) {
/*  482 */             bool = true;
/*      */           }
/*  484 */           if (bool) {
/*  485 */             String str = str3 + str4 + str5 + str6;
/*  486 */             if (eANList.get(str) == null) {
/*  487 */               SalesStatusItem salesStatusItem = new SalesStatusItem(this.m_prof, str);
/*  488 */               salesStatusItem.setMATNR(str3);
/*  489 */               salesStatusItem.setVARCOND(str4);
/*  490 */               salesStatusItem.setVARCONDTYPE(str5);
/*  491 */               salesStatusItem.setSALESORG(str6);
/*  492 */               salesStatusItem.setMATERIALSTATUS(str7);
/*  493 */               salesStatusItem.setMATERIALSTATUSDATE(str8);
/*  494 */               salesStatusItem.setLASTUPDATED(str9);
/*  495 */               salesStatusItem.setMARKEDFORDELETION(str10);
/*  496 */               eANList.put((EANObject)salesStatusItem);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  503 */     return eANList;
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
/*      */   private void checkParentLseoBundleCatlgpub(String paramString1, String paramString2) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  525 */     String str = "SRDLSEO1";
/*  526 */     StringBuffer stringBuffer = new StringBuffer();
/*  527 */     stringBuffer.append("map_SEOID=" + paramString1);
/*      */     
/*  529 */     EntityItem[] arrayOfEntityItem = this.m_utility.dynaSearch(this.m_db, this.m_prof, null, str, "LSEO", stringBuffer.toString());
/*  530 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  531 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, "EXTLSEO1");
/*      */       
/*  533 */       EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, extractActionItem, arrayOfEntityItem);
/*  534 */       EntityGroup entityGroup = entityList.getEntityGroup("LSEOBUNDLE");
/*      */       byte b;
/*  536 */       for (b = 0; b < entityList.getParentEntityGroup().getEntityItemCount(); b++) {
/*  537 */         EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(b);
/*  538 */         println("<!-- found " + entityItem.getKey() + " for CATSEOID: " + paramString1 + " -->");
/*      */       } 
/*      */       
/*  541 */       if (entityGroup != null) {
/*  542 */         for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */           
/*  544 */           EntityItem entityItem = entityGroup.getEntityItem(b);
/*  545 */           String str1 = this.m_utility.getAttrValue(entityItem, "SEOID");
/*  546 */           println("<!-- found parent " + entityItem.getKey() + " for child LSEO.CATSEOID: " + paramString1 + " -->");
/*      */ 
/*      */           
/*  549 */           stringBuffer = new StringBuffer();
/*  550 */           stringBuffer.append("map_OFFCOUNTRY=" + this.strOFFCOUNTRY + ";");
/*  551 */           stringBuffer.append("map_CATSEOID=" + str1 + ";");
/*  552 */           stringBuffer.append("map_CATOFFTYPE=BUNDLE");
/*  553 */           str = "SRDCATLGPUB1";
/*  554 */           EntityItem[] arrayOfEntityItem1 = this.m_utility.dynaSearch(this.m_db, this.m_prof, null, str, "CATLGPUB", stringBuffer.toString());
/*      */           
/*  556 */           if (arrayOfEntityItem1 != null && arrayOfEntityItem1.length > 0) {
/*  557 */             for (byte b1 = 0; b1 < arrayOfEntityItem1.length; b1++) {
/*  558 */               int i = 0;
/*  559 */               EntityItem entityItem1 = arrayOfEntityItem1[b1];
/*  560 */               String str2 = this.m_utility.getAttrValue(entityItem1, "PUBTO");
/*  561 */               if (str2.length() == 0) {
/*  562 */                 str2 = "9999-12-31-00.00.00.000000".substring(0, 10);
/*      */               }
/*      */               
/*  565 */               println("<!-- parent " + entityItem.getKey() + " has " + entityItem1.getKey() + " curDate: " + this.strCurrentDate + " PUBTO: " + str2 + " SEOID: " + str1 + " -->");
/*      */               
/*  567 */               i = this.m_utility.dateCompare(str2, this.strCurrentDate);
/*  568 */               if (i == 1) {
/*  569 */                 String str3 = "LSEO Sales Status Withdraw prior to the LSEOBUNDLE. <br />SALES_STATUS.MATERIALSTATUS: " + paramString2;
/*      */                 
/*  571 */                 printAllAttributes(str3, entityItem1);
/*  572 */                 setReturnCode(-1);
/*      */               } 
/*      */             } 
/*      */           } else {
/*      */             
/*  577 */             println("<!-- NO CATLGPUB found for " + stringBuffer + " -->");
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  582 */         println("<h2>Error: VE for EXTLSEO1 is missing LSEOBUNDLE group </h2>");
/*  583 */         setReturnCode(-1);
/*      */       } 
/*      */       
/*  586 */       entityList.dereference();
/*      */     } else {
/*      */       
/*  589 */       println("<!-- NO LSEO found for " + stringBuffer + " -->");
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
/*      */   private Vector findCatlgpub(SalesStatusItem paramSalesStatusItem) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  606 */     Vector vector = new Vector();
/*  607 */     String str1 = paramSalesStatusItem.getMATNR().trim();
/*  608 */     String str2 = paramSalesStatusItem.getVARCONDTYPE();
/*  609 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  611 */     D.ebug(4, "CATLGPUBABR02 info from SalesStatusItem: " + paramSalesStatusItem.dump(true));
/*  612 */     if (str2.equalsIgnoreCase("MODEL") || str2.equalsIgnoreCase("SWMODEL")) {
/*  613 */       if (str1.length() >= 4) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  619 */         stringBuffer.append("map_CATMACHTYPE=" + str1.substring(0, 4) + ";");
/*  620 */         stringBuffer.append("map_CATMODEL=" + str1.substring(4) + ";");
/*  621 */         stringBuffer.append("map_OFFCOUNTRY=" + this.strOFFCOUNTRY + ";");
/*  622 */         stringBuffer.append("map_CATOFFTYPE=MODEL");
/*      */         
/*  624 */         doCatlgpubSearch(stringBuffer, vector);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  630 */         println("<!--findCatlgpub: skipping " + str2 + " with MATNR<4 " + str1 + " -->");
/*      */       } 
/*  632 */     } else if (str2.equalsIgnoreCase("SEO")) {
/*  633 */       StringBuffer stringBuffer1 = new StringBuffer();
/*  634 */       stringBuffer.append("map_CATSEOID=" + str1 + ";");
/*  635 */       stringBuffer.append("map_OFFCOUNTRY=" + this.strOFFCOUNTRY + ";");
/*  636 */       stringBuffer.append("map_CATOFFTYPE=LSEO");
/*      */       
/*  638 */       doCatlgpubSearch(stringBuffer, vector);
/*      */       
/*  640 */       stringBuffer1.append("map_CATSEOID=" + str1 + ";");
/*  641 */       stringBuffer1.append("map_OFFCOUNTRY=" + this.strOFFCOUNTRY + ";");
/*  642 */       stringBuffer1.append("map_CATOFFTYPE=BUNDLE");
/*      */       
/*  644 */       doCatlgpubSearch(stringBuffer1, vector);
/*      */     } 
/*      */     
/*  647 */     return vector;
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
/*      */   private void doCatlgpubSearch(StringBuffer paramStringBuffer, Vector<EntityItem> paramVector) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/*  659 */     String str = "SRDCATLGPUB1";
/*  660 */     EntityItem[] arrayOfEntityItem = null;
/*      */     
/*  662 */     D.ebug(4, "CATLGPUBABR02 looking for CATLGPUB matching: " + paramStringBuffer);
/*  663 */     arrayOfEntityItem = this.m_utility.dynaSearch(this.m_db, this.m_prof, null, str, "CATLGPUB", paramStringBuffer.toString());
/*  664 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/*  665 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  666 */         EntityItem entityItem = arrayOfEntityItem[b];
/*  667 */         paramVector.addElement(entityItem);
/*  668 */         arrayOfEntityItem[b] = null;
/*      */       } 
/*      */     } else {
/*  671 */       D.ebug(4, "CATLGPUBABR02 Unable to find CATLGPUB for " + paramStringBuffer);
/*  672 */       println("<!--doCatlgpubSearch: Unable to find CATLGPUB for " + paramStringBuffer + " -->");
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
/*      */   private void updateCatlgpub(EntityItem paramEntityItem, String paramString, ExtractActionItem paramExtractActionItem) throws MiddlewareException, MiddlewareShutdownInProgressException, SQLException, SBRException {
/*  688 */     OPICMList oPICMList = new OPICMList();
/*  689 */     String str1 = "";
/*  690 */     String str2 = "";
/*  691 */     String str3 = "";
/*      */ 
/*      */ 
/*      */     
/*  695 */     if (SalesStatusInfo.isSimpleSS(paramString)) {
/*  696 */       SalesStatusInfo salesStatusInfo = SalesStatusInfo.getSalesStatusInfo(paramString, "");
/*  697 */       println("<!--updateCatlgpub: sskey: " + paramString + " values " + salesStatusInfo + " -->");
/*      */       
/*  699 */       str3 = salesStatusInfo.getAddToCart();
/*  700 */       str1 = salesStatusInfo.getBuyable();
/*  701 */       str2 = salesStatusInfo.getHide();
/*      */     } else {
/*      */       
/*  704 */       SalesStatusInfo salesStatusInfo = findSalesStatusInfo(paramEntityItem, paramString, paramExtractActionItem);
/*      */ 
/*      */       
/*  707 */       str3 = salesStatusInfo.getAddToCart();
/*  708 */       str1 = salesStatusInfo.getBuyable();
/*  709 */       str2 = salesStatusInfo.getHide();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     if (!this.m_utility.getAttrValue(paramEntityItem, "CATSALESSTATUS").equals(paramString)) {
/*  732 */       oPICMList.put("CATSALESSTATUS", "CATSALESSTATUS=" + paramString);
/*      */     }
/*      */     
/*  735 */     if (!this.m_utility.getAttrValue(paramEntityItem, "CATADDTOCART").equals(str3)) {
/*  736 */       oPICMList.put("CATADDTOCART", "CATADDTOCART=" + str3);
/*      */     }
/*      */     
/*  739 */     if (!this.m_utility.getAttrValue(paramEntityItem, "CATBUYABLE").equals(str1)) {
/*  740 */       oPICMList.put("CATBUYABLE", "CATBUYABLE=" + str1);
/*      */     }
/*      */     
/*  743 */     if (!this.m_utility.getAttrValue(paramEntityItem, "CATHIDE").equals(str2)) {
/*  744 */       oPICMList.put("CATHIDE", "CATHIDE=" + str2);
/*      */     }
/*      */     
/*  747 */     if (oPICMList.size() > 0) {
/*  748 */       String str = this.m_utility.getAttrValue(paramEntityItem, "CATWORKFLOW");
/*      */       
/*  750 */       if (str.equals("Override")) {
/*  751 */         oPICMList.put("CATWORKFLOW", "CATWORKFLOW=SalesStatusOverride");
/*  752 */       } else if (str.equals("Accept")) {
/*  753 */         oPICMList.put("CATWORKFLOW", "CATWORKFLOW=Change");
/*  754 */       } else if (str.equals("New")) {
/*  755 */         oPICMList.put("CATWORKFLOW", "CATWORKFLOW=Change");
/*      */       } 
/*  757 */       if (this.strUpdatedBy != null && this.strUpdatedBy.length() > 0) {
/*  758 */         oPICMList.put("CATUPDATEDBY", "CATUPDATEDBY=" + this.strUpdatedBy);
/*      */       }
/*      */       
/*  761 */       println("<!--updateCatlgpub: Updating " + paramEntityItem.getKey() + " using " + oPICMList + " -->");
/*  762 */       this.m_utility.updateAttribute(this.m_db, this.m_prof, paramEntityItem, oPICMList);
/*  763 */       this.updateCount++;
/*      */     } else {
/*  765 */       println("<!--updateCatlgpub: NO changes found for " + paramEntityItem.getKey() + " -->");
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
/*      */   private SalesStatusInfo findSalesStatusInfo(EntityItem paramEntityItem, String paramString, ExtractActionItem paramExtractActionItem) throws MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
/*  791 */     String str1 = "";
/*  792 */     String str2 = "";
/*  793 */     String str3 = "";
/*  794 */     String str4 = "";
/*  795 */     EntityItem[] arrayOfEntityItem = { paramEntityItem };
/*      */     
/*  797 */     String str5 = this.m_utility.getAttrValue(paramEntityItem, "CATOFFTYPE");
/*  798 */     println("<!--findSalesStatusInfo: " + paramEntityItem.getKey() + " offtype: " + str5 + " ss: " + paramString + " -->");
/*  799 */     if ("MODEL".equals(str5)) {
/*      */       
/*  801 */       str4 = this.m_utility.getAttrValue(paramEntityItem, "CATMACHTYPE") + this.m_utility.getAttrValue(paramEntityItem, "CATMODEL");
/*      */     } else {
/*      */       
/*  804 */       str4 = this.m_utility.getAttrValue(paramEntityItem, "CATSEOID") + this.m_utility.getAttrValue(paramEntityItem, "CATSEOID");
/*      */     } 
/*      */     
/*  807 */     String str6 = (String)this.sskeyTbl.get(str4);
/*  808 */     if (str6 == null) {
/*      */       
/*  810 */       EntityList entityList = EntityList.getEntityList(this.m_db, this.m_prof, paramExtractActionItem, arrayOfEntityItem);
/*  811 */       println("<!--findSalesStatusInfo: Extract EXCATLGPUBVE " + PokUtils.outputList(entityList) + " -->");
/*      */       
/*  813 */       EntityGroup entityGroup = null;
/*  814 */       if ("MODEL".equals(str5)) {
/*  815 */         entityGroup = entityList.getEntityGroup("MODEL");
/*  816 */         if (entityGroup.getEntityItemCount() == 0) {
/*  817 */           println("<!--findSalesStatusInfo: ERROR no " + entityGroup.getEntityType() + " found -->");
/*      */         } else {
/*  819 */           EntityItem entityItem = entityGroup.getEntityItem(0);
/*  820 */           str1 = this.m_utility.getAttrValue(entityItem, "COFCAT");
/*  821 */           println("<!--findSalesStatusInfo: using " + entityItem.getKey() + " cofcat: " + str1 + " -->");
/*      */         } 
/*  823 */         str6 = str1;
/*  824 */       } else if ("BUNDLE".equals(str5)) {
/*  825 */         str2 = "yes";
/*  826 */         entityGroup = entityList.getEntityGroup("LSEOBUNDLE");
/*  827 */         if (entityGroup.getEntityItemCount() == 0) {
/*  828 */           println("<!--findSalesStatusInfo: ERROR no " + entityGroup.getEntityType() + " found -->");
/*      */         } else {
/*  830 */           EntityItem entityItem = entityGroup.getEntityItem(0);
/*  831 */           str3 = this.m_utility.getAttrValue(entityItem, "SPECBID");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  836 */           String str = this.m_utility.getAttrValue(entityItem, "BUNDLETYPE");
/*  837 */           if (str.length() == 0) {
/*  838 */             str1 = "100";
/*      */           }
/*  840 */           else if (str.indexOf("100") != -1) {
/*  841 */             str1 = "100";
/*  842 */           } else if (str.indexOf("101") != -1) {
/*  843 */             str1 = "101";
/*      */           } else {
/*  845 */             str1 = "102";
/*      */           } 
/*      */           
/*  848 */           println("<!--findSalesStatusInfo: using " + entityItem.getKey() + " specbid: " + str3 + " cofcat: " + str1 + " from btype: " + str + " -->");
/*      */         } 
/*      */ 
/*      */         
/*  852 */         str6 = str1 + ":" + str2 + ":" + str3;
/*      */       } else {
/*  854 */         entityGroup = entityList.getEntityGroup("MODEL");
/*  855 */         if (entityGroup.getEntityItemCount() == 0) {
/*  856 */           println("<!--findSalesStatusInfo: ERROR no " + entityGroup.getEntityType() + " found -->");
/*      */         } else {
/*  858 */           EntityItem entityItem = entityGroup.getEntityItem(0);
/*  859 */           str1 = this.m_utility.getAttrValue(entityItem, "COFCAT");
/*  860 */           println("<!--findSalesStatusInfo: using " + entityItem.getKey() + " cofcat: " + str1 + " -->");
/*      */         } 
/*  862 */         entityGroup = entityList.getEntityGroup("WWSEO");
/*  863 */         if (entityGroup.getEntityItemCount() == 0) {
/*  864 */           println("<!--findSalesStatusInfo: ERROR no " + entityGroup.getEntityType() + " found -->");
/*      */         } else {
/*  866 */           EntityItem entityItem = entityGroup.getEntityItem(0);
/*  867 */           str3 = this.m_utility.getAttrValue(entityItem, "SPECBID");
/*  868 */           println("<!--findSalesStatusInfo: using " + entityItem.getKey() + " specbid: " + str3 + " -->");
/*      */         } 
/*  870 */         entityGroup = entityList.getEntityGroup("LSEO");
/*  871 */         if (entityGroup.getEntityItemCount() == 0) {
/*  872 */           println("<!--findSalesStatusInfo: ERROR no " + entityGroup.getEntityType() + " found -->");
/*      */         } else {
/*  874 */           EntityItem entityItem = entityGroup.getEntityItem(0);
/*  875 */           str2 = this.m_utility.getAttrValue(entityItem, "PRCINDC");
/*  876 */           println("<!--findSalesStatusInfo: using " + entityItem.getKey() + " priced: " + str2 + " -->");
/*      */         } 
/*      */         
/*  879 */         str6 = str1 + ":" + str2 + ":" + str3;
/*      */       } 
/*  881 */       this.sskeyTbl.put(str4, str6);
/*  882 */       entityList.dereference();
/*      */     } else {
/*  884 */       println("<!--findSalesStatusInfo: sskey " + str6 + " already found for " + str4 + " -->");
/*      */     } 
/*      */     
/*  887 */     SalesStatusInfo salesStatusInfo = SalesStatusInfo.getSalesStatusInfo(paramString, str6);
/*  888 */     println("<!--findSalesStatusInfo: sskey: " + paramString + ":" + str6 + " returned " + salesStatusInfo + " -->");
/*      */     
/*  890 */     return salesStatusInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void printAllAttributes(String paramString, EntityItem paramEntityItem) {
/*  896 */     byte b1 = 0;
/*  897 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*  898 */     if (!this.outputWarning) {
/*  899 */       this.outputWarning = true;
/*  900 */       println("<h2>These attribute values are listed before salesstatus updates were applied.</h2>");
/*      */     } 
/*  902 */     println("<h2 class=\"orange-med\">" + paramString + "</h2>");
/*  903 */     println("<table width=\"100%\">");
/*  904 */     println("<tr style=\"background-color:#cef;\"><th width=\"50%\" title=\"" + paramEntityItem
/*  905 */         .getKey() + "\">Attribute Description</th><th width=\"50%\">Attribute Value</th></tr>");
/*      */ 
/*      */     
/*  908 */     for (byte b2 = 0; b2 < entityGroup.getMetaAttributeCount(); b2++) {
/*  909 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(b2);
/*  910 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/*  911 */       println("<tr class=\"" + ((b1++ % 2 != 0) ? "even" : "odd") + "\"><td width=\"50%\" >" + eANMetaAttribute
/*      */ 
/*      */           
/*  914 */           .getLongDescription() + "</td><td width=\"50%\">" + ((eANAttribute == null || eANAttribute
/*      */           
/*  916 */           .toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*      */     } 
/*      */     
/*  919 */     println("</table><br />");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printNavigateAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/*  927 */     byte b1 = 0;
/*  928 */     println("<table width=\"100%\">");
/*  929 */     println("<tr style=\"background-color:#cef;\"><th width=\"50%\">Navigation Attribute</th><th width=\"50%\">Attribute Value</th></tr>");
/*      */     
/*  931 */     for (byte b2 = 0; b2 < paramEntityGroup.getMetaAttributeCount(); b2++) {
/*  932 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b2);
/*  933 */       if (eANMetaAttribute.isNavigate()) {
/*  934 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/*  935 */         println("<tr class=\"" + ((b1++ % 2 != 0) ? "even" : "odd") + "\"><td width=\"50%\" >" + eANMetaAttribute
/*      */ 
/*      */             
/*  938 */             .getLongDescription() + "</td><td width=\"50%\">" + ((eANAttribute == null || eANAttribute
/*      */             
/*  940 */             .toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*      */       } 
/*      */     } 
/*      */     
/*  944 */     println("</table>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printHeader(String paramString) {
/*  951 */     println("<table width=\"750\" cellpadding=\"0\" cellspacing=\"0\">");
/*  952 */     println("<tr><th width=\"25%\">ABRName: </th><td>" + 
/*  953 */         getABRCode() + "</td></tr>");
/*  954 */     println("<tr><th width=\"25%\">Abr Version: </th><td>" + 
/*  955 */         getABRVersion() + "</td></tr>");
/*  956 */     println("<tr><th width=\"25%\">Enterprise: </th><td>" + 
/*  957 */         getEnterprise() + "</td></tr>");
/*  958 */     println("<tr><th width=\"25%\">Valid Date: </th><td>" + this.m_prof
/*  959 */         .getValOn() + "</td></tr>");
/*  960 */     println("<tr><th width=\"25%\">Effective Date: </th><td>" + this.m_prof
/*  961 */         .getEffOn() + "</td></tr>");
/*  962 */     println("<tr><th width=\"25%\">Date Generated: </th><td>" + paramString + "</td></tr></table>");
/*      */ 
/*      */     
/*  965 */     println("<h3>Description: </h3><p>" + getDescription() + "</p><hr>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getABREntityDesc(String paramString, int paramInt) {
/*  976 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  985 */     return "Sales Status ABR";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/*  995 */     return "1.28";
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
/*      */   public String getABRVersion() {
/* 1015 */     return "CATLGPUBABR02.java " + getRevision();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CATLGPUBABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */