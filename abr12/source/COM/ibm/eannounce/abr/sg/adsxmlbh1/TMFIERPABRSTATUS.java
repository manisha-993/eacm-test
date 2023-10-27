/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwCharMaintain;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwClassMaintain;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwClsfCharCreate;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.CommonUtils;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.FEATURE;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.LANGUAGEELEMENT_FEATURE;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.MODEL;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.RdhChwFcProd;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.RdhClassificationMaint;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.TMF_UPDATE;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.UpdateParkStatus;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.XMLParse;
/*      */ import COM.ibm.eannounce.abr.util.DateUtility;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.StringCharacterIterator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ 
/*      */ public class TMFIERPABRSTATUS extends PokBaseABR {
/*      */   private StringBuffer rptSb;
/*      */   
/*      */   public TMFIERPABRSTATUS() {
/*   42 */     this.rptSb = new StringBuffer();
/*      */ 
/*      */     
/*   45 */     this.abr_debuglvl = 0;
/*   46 */     this.navName = "";
/*   47 */     this.metaTbl = new Hashtable<>();
/*   48 */     this.PRODSTRUCTSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'PRODSTRUCT' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*      */     
/*   50 */     this.MODELSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODEL' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*      */     
/*   52 */     this.FEATURESQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'FEATURE' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*      */     
/*   54 */     this.COVNOTEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE !=T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? WITH UR";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   62 */     this.COVEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP  WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? WITH UR";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   70 */     this.FCTEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP  WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? WITH UR";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   77 */     this.xml = null;
/*   78 */     this.FctypeEMap = new HashMap<>();
/*      */     
/*   80 */     this.FctypeEMap.put("RPQ-PLISTED", "");
/*   81 */     this.FctypeEMap.put("RPQ-ILISTED", "");
/*      */   }
/*      */   private static final char[] FOOL_JTEST = new char[] { '\n' }; static final String NEWLINE = new String(FOOL_JTEST); private int abr_debuglvl; private String navName; private Hashtable<String, EANList> metaTbl; private String PRODSTRUCTSQL; private String MODELSQL;
/*      */   
/*      */   public String getDescription() {
/*   86 */     return "TMFIERPABRSTATUS";
/*      */   }
/*      */   private String FEATURESQL; private String date; private String COVNOTEQUALSQL; private String COVEQUALSQL; private String FCTEQUALSQL; String xml; Map<String, String> FctypeEMap;
/*      */   public String getABRVersion() {
/*   90 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*   96 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*   98 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  104 */     String str3 = "";
/*      */ 
/*      */ 
/*      */     
/*  108 */     String str4 = "";
/*  109 */     String str5 = "";
/*      */     
/*  111 */     String[] arrayOfString = new String[10];
/*  112 */     this.date = "_" + DateUtility.getTodayStringWithSimpleFormat();
/*      */     try {
/*  114 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  115 */       arrayOfString[0] = getShortClassName(getClass());
/*  116 */       arrayOfString[1] = "ABR";
/*  117 */       str3 = messageFormat.format(arrayOfString);
/*  118 */       setDGTitle("TMFIERPABRSTATUS report");
/*  119 */       setDGString(getABRReturnCode());
/*  120 */       setDGRptName("TMFIERPABRSTATUS");
/*  121 */       setDGRptClass("TMFIERPABRSTATUS");
/*      */       
/*  123 */       setReturnCode(0);
/*      */       
/*  125 */       start_ABRBuild(false);
/*      */       
/*  127 */       this
/*  128 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */ 
/*      */ 
/*      */       
/*  132 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  134 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  141 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  142 */       addDebug("*****mlm rootEntity TMFIERPABRSTATUS = " + entityItem.getEntityType() + entityItem.getEntityID());
/*      */       
/*  144 */       this.navName = getNavigationName();
/*  145 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/*  146 */       addDebug("navName=" + this.navName);
/*  147 */       addDebug("rootDesc" + str5);
/*      */ 
/*      */       
/*  150 */       this.xml = getXMLByID(this.PRODSTRUCTSQL, entityItem.getEntityID());
/*      */ 
/*      */       
/*  153 */       if (this.xml != null) {
/*      */ 
/*      */         
/*  156 */         TMF_UPDATE tMF_UPDATE = (TMF_UPDATE)XMLParse.getObjectFromXml(this.xml, TMF_UPDATE.class);
/*  157 */         addDebug("get the tmf!");
/*      */         
/*  159 */         if (tMF_UPDATE == null)
/*  160 */           return;  String str6 = getXMLByID(this.FEATURESQL, Integer.parseInt(tMF_UPDATE.getFEATUREENTITYID()));
/*      */         
/*  162 */         String str7 = getXMLByID(this.MODELSQL, Integer.parseInt(tMF_UPDATE.getMODELENTITYID()));
/*      */ 
/*      */         
/*  165 */         FEATURE fEATURE = (FEATURE)XMLParse.getObjectFromXml(str6, FEATURE.class);
/*  166 */         addDebug("get the chwFeature=" + fEATURE);
/*  167 */         MODEL mODEL = (MODEL)XMLParse.getObjectFromXml(str7, MODEL.class);
/*  168 */         addDebug("get the chwModel=" + mODEL);
/*  169 */         MachineTypeNEW(tMF_UPDATE, fEATURE, mODEL);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  179 */         if (exist(this.COVNOTEQUALSQL, tMF_UPDATE.getMACHTYPE(), tMF_UPDATE.getPDHDOMAIN())) {
/*  180 */           MachineTypeMTC(tMF_UPDATE, fEATURE, mODEL);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  197 */         if (!CommonUtils.contains("Maintenance,MaintFeature", mODEL.getSUBCATEGORY()) && (
/*  198 */           exist(this.COVEQUALSQL, tMF_UPDATE.getMACHTYPE(), tMF_UPDATE.getPDHDOMAIN()) || 
/*  199 */           exist(this.FCTEQUALSQL, tMF_UPDATE.getMACHTYPE(), tMF_UPDATE.getPDHDOMAIN()) || 
/*  200 */           CommonUtils.contains("M,B", mODEL.getORDERCODE()))) {
/*  201 */           MachineTypeUPG(tMF_UPDATE, fEATURE, mODEL);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  208 */         String str8 = tMF_UPDATE.getMACHTYPE() + tMF_UPDATE.getMODEL() + tMF_UPDATE.getFEATURECODE();
/*  209 */         RdhChwFcProd rdhChwFcProd = new RdhChwFcProd(tMF_UPDATE, str8);
/*  210 */         runRfcCaller((RdhBase)rdhChwFcProd);
/*      */         
/*  212 */         UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", "T" + str8);
/*  213 */         runParkCaller((RdhBase)updateParkStatus, "T" + str8);
/*      */       } else {
/*  215 */         addDebug("no xml found in the ODS database");
/*      */       } 
/*  217 */     } catch (Exception exception) {
/*  218 */       exception.printStackTrace();
/*      */       
/*  220 */       setReturnCode(-1);
/*  221 */       StringWriter stringWriter = new StringWriter();
/*  222 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  223 */       String str7 = "<pre>{0}</pre>";
/*  224 */       MessageFormat messageFormat = new MessageFormat(str6);
/*  225 */       setReturnCode(-3);
/*  226 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  228 */       arrayOfString[0] = exception.getMessage();
/*  229 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/*  230 */       messageFormat = new MessageFormat(str7);
/*  231 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/*  232 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/*  233 */       logError("Exception: " + exception.getMessage());
/*  234 */       logError(stringWriter.getBuffer().toString());
/*      */       
/*  236 */       setCreateDGEntity(true);
/*      */     }
/*      */     finally {
/*      */       
/*  240 */       StringBuffer stringBuffer = new StringBuffer();
/*  241 */       MessageFormat messageFormat = new MessageFormat(str2);
/*  242 */       arrayOfString[0] = this.m_prof.getOPName();
/*  243 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/*  244 */       arrayOfString[2] = this.m_prof.getWGName();
/*  245 */       arrayOfString[3] = getNow();
/*  246 */       arrayOfString[4] = stringBuffer.toString();
/*  247 */       arrayOfString[5] = str4 + " " + getABRVersion();
/*  248 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/*  249 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*      */       
/*  251 */       println(EACustom.getDocTypeHtml());
/*  252 */       println(this.rptSb.toString());
/*  253 */       printDGSubmitString();
/*  254 */       if (!isReadOnly()) {
/*  255 */         clearSoftLock();
/*      */       }
/*  257 */       println(EACustom.getTOUDiv());
/*  258 */       buildReportFooter();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void runRfcCaller(RdhBase paramRdhBase) throws Exception {
/*  263 */     addDebug("Calling " + paramRdhBase.getRFCName());
/*  264 */     paramRdhBase.execute();
/*  265 */     addDebug(paramRdhBase.createLogEntry());
/*  266 */     if (paramRdhBase.getRfcrc() == 0) {
/*  267 */       addOutput(paramRdhBase.getRFCName() + " called successfully!");
/*      */     } else {
/*  269 */       addOutput(paramRdhBase.getRFCName() + " called  faild!");
/*  270 */       addOutput(paramRdhBase.getError_text());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getXMLByID(String paramString, int paramInt) throws MiddlewareException, SQLException {
/*  276 */     String str1 = "";
/*  277 */     Connection connection = this.m_db.getODSConnection();
/*  278 */     String[] arrayOfString = new String[1];
/*  279 */     arrayOfString[0] = String.valueOf(paramInt);
/*  280 */     String str2 = CommonUtils.getPreparedSQL(paramString, (Object[])arrayOfString);
/*  281 */     addDebug("querySql=" + str2);
/*  282 */     PreparedStatement preparedStatement = connection.prepareStatement(paramString);
/*  283 */     preparedStatement.setInt(1, paramInt);
/*  284 */     ResultSet resultSet = preparedStatement.executeQuery();
/*      */     
/*  286 */     while (resultSet.next()) {
/*  287 */       str1 = resultSet.getString("XMLMESSAGE");
/*      */     }
/*  289 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void runParkCaller(RdhBase paramRdhBase, String paramString) throws Exception {
/*  295 */     addDebug("Calling " + paramRdhBase.getRFCName());
/*  296 */     paramRdhBase.execute();
/*  297 */     addDebug(paramRdhBase.createLogEntry());
/*  298 */     if (paramRdhBase.getRfcrc() == 0) {
/*  299 */       addOutput("Parking records updated successfully for ZDMRELNUM=" + paramString);
/*      */     } else {
/*  301 */       addOutput(paramRdhBase.getRFCName() + " called faild!");
/*  302 */       addOutput(paramRdhBase.getError_text());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void MachineTypeUPG(TMF_UPDATE paramTMF_UPDATE, FEATURE paramFEATURE, MODEL paramMODEL) throws Exception {
/*  308 */     if (paramFEATURE == null)
/*  309 */       return;  if (paramMODEL == null)
/*  310 */       return;  String str1 = paramTMF_UPDATE.getFEATURECODE();
/*  311 */     String str2 = paramTMF_UPDATE.getFCTYPE();
/*  312 */     String str3 = paramTMF_UPDATE.getMACHTYPE() + "UPG";
/*  313 */     String str4 = "T";
/*  314 */     String str5 = paramTMF_UPDATE.getMACHTYPE();
/*  315 */     String str6 = getFeatureCodeDesc(paramFEATURE);
/*  316 */     String str7 = paramTMF_UPDATE.getMACHTYPE() + "UPG";
/*  317 */     String str8 = paramTMF_UPDATE.getMACHTYPE() + "UPG" + this.date;
/*  318 */     ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
/*      */     
/*  320 */     if (CommonUtils.isNoLetter(str1) && !this.FctypeEMap.containsKey(str2)) {
/*  321 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       
/*      */       try {
/*  324 */         str4 = "T";
/*  325 */         chwClsfCharCreate.CreateGroupChar(str3, str4, str5, str1, str6, str8);
/*  326 */         addMsg(chwClsfCharCreate.getRptSb());
/*  327 */       } catch (Exception exception) {
/*  328 */         addMsg(chwClsfCharCreate.getRptSb());
/*  329 */         throw exception;
/*      */       } 
/*  331 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       
/*      */       try {
/*  334 */         str4 = "D";
/*  335 */         chwClsfCharCreate.CreateGroupChar(str3, str4, str5, str1, str6, str8);
/*  336 */         addMsg(chwClsfCharCreate.getRptSb());
/*  337 */       } catch (Exception exception) {
/*  338 */         addMsg(chwClsfCharCreate.getRptSb());
/*  339 */         throw exception;
/*      */       } 
/*      */       
/*  342 */       String str11 = paramMODEL.getUNITCLASS();
/*  343 */       String str12 = paramTMF_UPDATE.getSYSTEMMAX();
/*  344 */       String str13 = paramTMF_UPDATE.getORDERCODE();
/*  345 */       if ("Non SIU- CPU".equalsIgnoreCase(str11) || str12.compareTo("1") > 0 || "B".equalsIgnoreCase(str13)) {
/*  346 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         
/*      */         try {
/*  349 */           str4 = "T";
/*  350 */           chwClsfCharCreate.CreateQTYChar(str3, str4, str5, str1, str8);
/*  351 */           addMsg(chwClsfCharCreate.getRptSb());
/*  352 */         } catch (Exception exception) {
/*  353 */           addMsg(chwClsfCharCreate.getRptSb());
/*  354 */           throw exception;
/*      */         } 
/*  356 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         try {
/*  358 */           str4 = "D";
/*  359 */           chwClsfCharCreate.CreateQTYChar(str3, str4, str5, str1, str8);
/*  360 */           addMsg(chwClsfCharCreate.getRptSb());
/*  361 */         } catch (Exception exception) {
/*  362 */           addMsg(chwClsfCharCreate.getRptSb());
/*  363 */           throw exception;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  369 */     if (CommonUtils.hasLetter(str1) && this.FctypeEMap.containsKey(str2)) {
/*      */       
/*  371 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       try {
/*  373 */         str4 = "T";
/*  374 */         chwClsfCharCreate.CreateRPQGroupChar(str3, str4, str5, str1, str6, str8);
/*  375 */         addMsg(chwClsfCharCreate.getRptSb());
/*  376 */       } catch (Exception exception) {
/*  377 */         addMsg(chwClsfCharCreate.getRptSb());
/*  378 */         throw exception;
/*      */       } 
/*      */       
/*  381 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       try {
/*  383 */         str4 = "D";
/*  384 */         chwClsfCharCreate.CreateRPQGroupChar(str3, str4, str5, str1, str6, str8);
/*  385 */         addMsg(chwClsfCharCreate.getRptSb());
/*  386 */       } catch (Exception exception) {
/*  387 */         addMsg(chwClsfCharCreate.getRptSb());
/*  388 */         throw exception;
/*      */       } 
/*      */       
/*  391 */       String str11 = paramMODEL.getUNITCLASS();
/*  392 */       String str12 = paramTMF_UPDATE.getSYSTEMMAX();
/*  393 */       String str13 = paramTMF_UPDATE.getORDERCODE();
/*  394 */       if ("Non SIU- CPU".equalsIgnoreCase(str11) || str12.compareTo("1") > 0 || "B".equalsIgnoreCase(str13)) {
/*      */         
/*  396 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         
/*      */         try {
/*  399 */           str4 = "T";
/*  400 */           chwClsfCharCreate.CreateRPQQTYChar(str3, str4, str5, str1, str8);
/*  401 */           addMsg(chwClsfCharCreate.getRptSb());
/*  402 */         } catch (Exception exception) {
/*  403 */           addMsg(chwClsfCharCreate.getRptSb());
/*  404 */           throw exception;
/*      */         } 
/*  406 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         try {
/*  408 */           str4 = "D";
/*  409 */           chwClsfCharCreate.CreateRPQQTYChar(str3, str4, str5, str1, str8);
/*  410 */           addMsg(chwClsfCharCreate.getRptSb());
/*  411 */         } catch (Exception exception) {
/*  412 */           addMsg(chwClsfCharCreate.getRptSb());
/*  413 */           throw exception;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  420 */     if (CommonUtils.hasLetter(str1) && 
/*  421 */       !this.FctypeEMap.containsKey(str2) && 
/*  422 */       !CommonUtils.getFirstSubString(str1, 3).equalsIgnoreCase("NEW")) {
/*      */       
/*  424 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       try {
/*  426 */         str4 = "T";
/*  427 */         chwClsfCharCreate.CreateAlphaGroupChar(str3, str4, str5, str1, str6, str8);
/*  428 */         addMsg(chwClsfCharCreate.getRptSb());
/*  429 */       } catch (Exception exception) {
/*  430 */         addMsg(chwClsfCharCreate.getRptSb());
/*  431 */         throw exception;
/*      */       } 
/*      */       
/*  434 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       try {
/*  436 */         str4 = "D";
/*  437 */         chwClsfCharCreate.CreateAlphaGroupChar(str3, str4, str5, str1, str6, str8);
/*  438 */         addMsg(chwClsfCharCreate.getRptSb());
/*  439 */       } catch (Exception exception) {
/*  440 */         addMsg(chwClsfCharCreate.getRptSb());
/*  441 */         throw exception;
/*      */       } 
/*      */       
/*  444 */       String str11 = paramTMF_UPDATE.getSYSTEMMAX();
/*  445 */       String str12 = paramTMF_UPDATE.getORDERCODE();
/*  446 */       if (str11.compareTo("1") > 0 || "B".equalsIgnoreCase(str12)) {
/*      */         
/*  448 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         try {
/*  450 */           str4 = "T";
/*  451 */           chwClsfCharCreate.CreateAlphaQTYChar(str3, str4, str5, str1, str7, str8);
/*  452 */           addMsg(chwClsfCharCreate.getRptSb());
/*  453 */         } catch (Exception exception) {
/*  454 */           addMsg(chwClsfCharCreate.getRptSb());
/*  455 */           throw exception;
/*      */         } 
/*  457 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         try {
/*  459 */           str4 = "D";
/*  460 */           chwClsfCharCreate.CreateAlphaQTYChar(str3, str4, str5, str1, str7, str8);
/*  461 */           addMsg(chwClsfCharCreate.getRptSb());
/*  462 */         } catch (Exception exception) {
/*  463 */           addMsg(chwClsfCharCreate.getRptSb());
/*  464 */           throw exception;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  471 */     String str9 = "";
/*  472 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(str8, "MK_D_" + str5 + "_REM_FC", "CHAR", 12, str9, str9, str9, str9, "-", str9, str9, "X", "Removed Features " + str5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  486 */     addDebug("Calling " + chwCharMaintain.getRFCName());
/*  487 */     chwCharMaintain.execute();
/*  488 */     addRfcResult((RdhBase)chwCharMaintain);
/*      */     
/*  490 */     str3 = paramTMF_UPDATE.getMACHTYPE() + "UPG";
/*  491 */     String str10 = "MK_D_" + str5 + "_REM_FC";
/*  492 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(str8, str10, str10);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  497 */     addDebug("Calling " + chwClassMaintain.getRFCName());
/*      */     
/*  499 */     chwClassMaintain.addCharacteristic(str10);
/*  500 */     chwClassMaintain.execute();
/*  501 */     addRfcResult((RdhBase)chwClassMaintain);
/*      */     
/*  503 */     str3 = paramTMF_UPDATE.getMACHTYPE() + "UPG";
/*  504 */     callRdhClassificationMaint(str3, "MK_D_" + str5 + "_REM_FC", str8);
/*      */     
/*  506 */     callRdhClassificationMaint(str3, "MK_REFERENCE", str8);
/*      */     
/*  508 */     callRdhClassificationMaint(str3, "MK_T_VAO_NEW", str8);
/*      */     
/*  510 */     callRdhClassificationMaint(str3, "MK_D_VAO_NEW", str8);
/*      */     
/*  512 */     callRdhClassificationMaint(str3, "MK_FC_EXCH", str8);
/*      */     
/*  514 */     callRdhClassificationMaint(str3, "MK_FC_CONV", str8);
/*      */ 
/*      */     
/*  517 */     UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", str8);
/*  518 */     addDebug("Calling " + updateParkStatus.getRFCName());
/*  519 */     updateParkStatus.execute();
/*  520 */     addOutput("Parking records updated successfully for ZDMRELNUM=" + str8);
/*  521 */     addDebug(updateParkStatus.createLogEntry());
/*      */   }
/*      */ 
/*      */   
/*      */   private void MachineTypeMTC(TMF_UPDATE paramTMF_UPDATE, FEATURE paramFEATURE, MODEL paramMODEL) throws Exception {
/*  526 */     addDebug("case TMF Calling MachineTypeMTC");
/*  527 */     if (paramFEATURE == null)
/*  528 */       return;  if (paramMODEL == null)
/*      */       return; 
/*  530 */     String str1 = paramTMF_UPDATE.getFEATURECODE();
/*  531 */     String str2 = paramTMF_UPDATE.getFCTYPE();
/*  532 */     String str3 = paramTMF_UPDATE.getMACHTYPE() + "MTC";
/*  533 */     String str4 = "T";
/*  534 */     String str5 = paramTMF_UPDATE.getMACHTYPE();
/*  535 */     String str6 = getFeatureCodeDesc(paramFEATURE);
/*  536 */     String str7 = paramTMF_UPDATE.getMACHTYPE() + "MTC";
/*  537 */     String str8 = paramTMF_UPDATE.getMACHTYPE() + "MTC" + this.date;
/*  538 */     ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
/*      */     
/*  540 */     if (CommonUtils.isNoLetter(str1) && !this.FctypeEMap.containsKey(str2)) {
/*      */       
/*  542 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       
/*      */       try {
/*  545 */         str4 = "T";
/*  546 */         chwClsfCharCreate.CreateGroupChar(str3, str4, str5, str1, str6, str8);
/*  547 */         addMsg(chwClsfCharCreate.getRptSb());
/*  548 */       } catch (Exception exception) {
/*  549 */         addMsg(chwClsfCharCreate.getRptSb());
/*  550 */         throw exception;
/*      */       } 
/*      */       
/*  553 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       try {
/*  555 */         str4 = "D";
/*  556 */         chwClsfCharCreate.CreateGroupChar(str3, str4, str5, str1, str6, str8);
/*  557 */         addMsg(chwClsfCharCreate.getRptSb());
/*  558 */       } catch (Exception exception) {
/*  559 */         addMsg(chwClsfCharCreate.getRptSb());
/*  560 */         throw exception;
/*      */       } 
/*      */       
/*  563 */       String str11 = paramMODEL.getUNITCLASS();
/*  564 */       String str12 = paramTMF_UPDATE.getSYSTEMMAX();
/*  565 */       String str13 = paramTMF_UPDATE.getORDERCODE();
/*  566 */       if ("Non SIU- CPU".equalsIgnoreCase(str11) || str12.compareTo("1") > 0 || "B".equalsIgnoreCase(str13)) {
/*  567 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         
/*      */         try {
/*  570 */           str4 = "T";
/*  571 */           chwClsfCharCreate.CreateQTYChar(str3, str4, str5, str1, str8);
/*  572 */           addMsg(chwClsfCharCreate.getRptSb());
/*  573 */         } catch (Exception exception) {
/*  574 */           addMsg(chwClsfCharCreate.getRptSb());
/*  575 */           throw exception;
/*      */         } 
/*      */         
/*  578 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         
/*      */         try {
/*  581 */           str4 = "D";
/*  582 */           chwClsfCharCreate.CreateQTYChar(str3, str4, str5, str1, str8);
/*  583 */           addMsg(chwClsfCharCreate.getRptSb());
/*  584 */         } catch (Exception exception) {
/*  585 */           addMsg(chwClsfCharCreate.getRptSb());
/*  586 */           throw exception;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  591 */     if (CommonUtils.hasLetter(str1) && this.FctypeEMap.containsKey(str2)) {
/*      */       
/*  593 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       try {
/*  595 */         str4 = "T";
/*  596 */         chwClsfCharCreate.CreateRPQGroupChar(str3, str4, str5, str1, str6, str8);
/*  597 */         addMsg(chwClsfCharCreate.getRptSb());
/*  598 */       } catch (Exception exception) {
/*  599 */         addMsg(chwClsfCharCreate.getRptSb());
/*  600 */         throw exception;
/*      */       } 
/*      */       
/*  603 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       try {
/*  605 */         str4 = "D";
/*  606 */         chwClsfCharCreate.CreateRPQGroupChar(str3, str4, str5, str1, str6, str8);
/*  607 */         addMsg(chwClsfCharCreate.getRptSb());
/*  608 */       } catch (Exception exception) {
/*  609 */         addMsg(chwClsfCharCreate.getRptSb());
/*  610 */         throw exception;
/*      */       } 
/*      */       
/*  613 */       String str11 = paramMODEL.getUNITCLASS();
/*  614 */       String str12 = paramTMF_UPDATE.getSYSTEMMAX();
/*  615 */       String str13 = paramTMF_UPDATE.getORDERCODE();
/*  616 */       if ("Non SIU- CPU".equalsIgnoreCase(str11) || str12.compareTo("1") > 0 || "B".equalsIgnoreCase(str13)) {
/*  617 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         
/*      */         try {
/*  620 */           str4 = "T";
/*  621 */           chwClsfCharCreate.CreateRPQQTYChar(str3, str4, str5, str1, str8);
/*  622 */           addMsg(chwClsfCharCreate.getRptSb());
/*  623 */         } catch (Exception exception) {
/*  624 */           addMsg(chwClsfCharCreate.getRptSb());
/*  625 */           throw exception;
/*      */         } 
/*  627 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         try {
/*  629 */           str4 = "D";
/*  630 */           chwClsfCharCreate.CreateRPQQTYChar(str3, str4, str5, str1, str8);
/*  631 */           addMsg(chwClsfCharCreate.getRptSb());
/*  632 */         } catch (Exception exception) {
/*  633 */           addMsg(chwClsfCharCreate.getRptSb());
/*  634 */           throw exception;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  641 */     if (CommonUtils.hasLetter(str1) && 
/*  642 */       !this.FctypeEMap.containsKey(str2) && 
/*  643 */       !CommonUtils.getFirstSubString(str1, 3).equalsIgnoreCase("NEW")) {
/*      */       
/*  645 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       try {
/*  647 */         str4 = "T";
/*  648 */         chwClsfCharCreate.CreateAlphaGroupChar(str3, str4, str5, str1, str6, str8);
/*  649 */         addMsg(chwClsfCharCreate.getRptSb());
/*  650 */       } catch (Exception exception) {
/*  651 */         addMsg(chwClsfCharCreate.getRptSb());
/*  652 */         throw exception;
/*      */       } 
/*      */       
/*  655 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       try {
/*  657 */         str4 = "D";
/*  658 */         chwClsfCharCreate.CreateAlphaGroupChar(str3, str4, str5, str1, str6, str8);
/*  659 */         addMsg(chwClsfCharCreate.getRptSb());
/*  660 */       } catch (Exception exception) {
/*  661 */         addMsg(chwClsfCharCreate.getRptSb());
/*  662 */         throw exception;
/*      */       } 
/*      */       
/*  665 */       String str11 = paramTMF_UPDATE.getSYSTEMMAX();
/*  666 */       String str12 = paramTMF_UPDATE.getORDERCODE();
/*  667 */       if (str11.compareTo("1") > 0 || "B".equalsIgnoreCase(str12)) {
/*      */         
/*  669 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         try {
/*  671 */           str4 = "T";
/*  672 */           chwClsfCharCreate.CreateAlphaQTYChar(str3, str4, str5, str1, str7, str8);
/*  673 */           addMsg(chwClsfCharCreate.getRptSb());
/*  674 */         } catch (Exception exception) {
/*  675 */           addMsg(chwClsfCharCreate.getRptSb());
/*  676 */           throw exception;
/*      */         } 
/*  678 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         try {
/*  680 */           str4 = "D";
/*  681 */           chwClsfCharCreate.CreateAlphaQTYChar(str3, str4, str5, str1, str7, str8);
/*  682 */           addMsg(chwClsfCharCreate.getRptSb());
/*  683 */         } catch (Exception exception) {
/*  684 */           addMsg(chwClsfCharCreate.getRptSb());
/*  685 */           throw exception;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  691 */     String str9 = "";
/*  692 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(str8, "MK_" + str5 + "_MTC_REM_FC", "CHAR", 12, str9, str9, str9, str9, "-", str9, str9, "X", "MTC Features Removed " + str5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  706 */     addDebug("Calling " + chwCharMaintain.getRFCName());
/*  707 */     chwCharMaintain.execute();
/*  708 */     addRfcResult((RdhBase)chwCharMaintain);
/*      */     
/*  710 */     String str10 = "MK_" + str5 + "_MTC";
/*  711 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(str8, str10, str10);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  716 */     addDebug("Calling " + chwClassMaintain.getRFCName());
/*      */     
/*  718 */     chwClassMaintain.addCharacteristic("MK_" + str5 + "_MTC_REM_FC");
/*  719 */     chwClassMaintain.execute();
/*  720 */     addRfcResult((RdhBase)chwClassMaintain);
/*      */ 
/*      */ 
/*      */     
/*  724 */     callRdhClassificationMaint(str3, "MK_" + str5 + "_MTC", str8);
/*      */     
/*  726 */     callRdhClassificationMaint(str3, "MK_REFERENCE", str8);
/*      */     
/*  728 */     callRdhClassificationMaint(str3, "MK_T_VAO_NEW", str8);
/*      */     
/*  730 */     callRdhClassificationMaint(str3, "MK_D_VAO_NEW", str8);
/*      */     
/*  732 */     callRdhClassificationMaint(str3, "MK_FC_EXCH", str8);
/*      */     
/*  734 */     callRdhClassificationMaint(str3, "MK_FC_CONV", str8);
/*      */ 
/*      */     
/*  737 */     UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", str8);
/*  738 */     addDebug("Calling " + updateParkStatus.getRFCName());
/*  739 */     updateParkStatus.execute();
/*  740 */     addOutput("Parking records updated successfully for ZDMRELNUM=" + str8);
/*  741 */     addDebug(updateParkStatus.createLogEntry());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void callRdhClassificationMaint(String paramString1, String paramString2, String paramString3) throws Exception {
/*  747 */     RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(paramString1, paramString2, "300", "H", paramString3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  753 */     addDebug("Calling " + rdhClassificationMaint.getRFCName());
/*  754 */     rdhClassificationMaint.execute();
/*  755 */     addRfcResult((RdhBase)rdhClassificationMaint);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void MachineTypeNEW(TMF_UPDATE paramTMF_UPDATE, FEATURE paramFEATURE, MODEL paramMODEL) throws Exception {
/*  761 */     if (paramFEATURE == null) {
/*      */       return;
/*      */     }
/*  764 */     if (paramMODEL == null) {
/*      */       return;
/*      */     }
/*      */     
/*  768 */     String str1 = paramTMF_UPDATE.getFCTYPE();
/*  769 */     String str2 = paramTMF_UPDATE.getMACHTYPE() + "NEW";
/*  770 */     String str3 = "T";
/*  771 */     String str4 = paramTMF_UPDATE.getMACHTYPE();
/*  772 */     String str5 = paramTMF_UPDATE.getFEATURECODE();
/*  773 */     String str6 = getFeatureCodeDesc(paramFEATURE);
/*  774 */     String str7 = paramTMF_UPDATE.getMACHTYPE() + "NEW";
/*  775 */     ChwClsfCharCreate chwClsfCharCreate = new ChwClsfCharCreate();
/*  776 */     String str8 = paramTMF_UPDATE.getMACHTYPE() + "NEW" + this.date;
/*      */     
/*  778 */     addDebug("TMF param feature_code=" + str5);
/*  779 */     addDebug("TMF param FCTYPE=" + str1);
/*  780 */     if (CommonUtils.isNoLetter(str5) && !this.FctypeEMap.containsKey(str1)) {
/*  781 */       addDebug("1.1 TMF Calling Call ChwClsfCharCreate.CreateGroupChar()");
/*      */       
/*  783 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       
/*      */       try {
/*  786 */         chwClsfCharCreate.CreateGroupChar(str2, str3, str4, str5, str6, str8);
/*  787 */         addMsg(chwClsfCharCreate.getRptSb());
/*  788 */       } catch (Exception exception) {
/*  789 */         addMsg(chwClsfCharCreate.getRptSb());
/*  790 */         throw exception;
/*      */       } 
/*      */ 
/*      */       
/*  794 */       String str9 = paramMODEL.getUNITCLASS();
/*  795 */       String str10 = paramTMF_UPDATE.getSYSTEMMAX();
/*  796 */       String str11 = paramTMF_UPDATE.getORDERCODE();
/*  797 */       addDebug("TMF param UNITCLASS=" + str9);
/*  798 */       addDebug("TMF param SYSTEMMAX=" + str10);
/*  799 */       addDebug("TMF param ORDERCODE=" + str11);
/*      */       
/*  801 */       if ("Non SIU- CPU".equalsIgnoreCase(str9) || str10.compareTo("1") > 0 || "B".equalsIgnoreCase(str11)) {
/*  802 */         addDebug("1.2 TMF Calling Call ChwClsfCharCreate.CreateQTYChar()");
/*  803 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         
/*      */         try {
/*  806 */           chwClsfCharCreate.CreateQTYChar(str2, str3, str4, str5, str8);
/*  807 */           addMsg(chwClsfCharCreate.getRptSb());
/*  808 */         } catch (Exception exception) {
/*  809 */           addMsg(chwClsfCharCreate.getRptSb());
/*  810 */           throw exception;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  817 */     addDebug("TMF param feature_code=" + str5);
/*  818 */     addDebug("TMF param FCTYPE=" + str1);
/*  819 */     if (CommonUtils.hasLetter(str5) && this.FctypeEMap.containsKey(str1)) {
/*  820 */       addDebug("2.1 TMF Calling Call ChwClsfCharCreate.CreateRPQGroupChar()");
/*      */       
/*  822 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       
/*      */       try {
/*  825 */         chwClsfCharCreate.CreateRPQGroupChar(str2, str3, str4, str5, str6, str8);
/*  826 */         addMsg(chwClsfCharCreate.getRptSb());
/*  827 */       } catch (Exception exception) {
/*  828 */         addMsg(chwClsfCharCreate.getRptSb());
/*  829 */         throw exception;
/*      */       } 
/*      */ 
/*      */       
/*  833 */       String str9 = paramMODEL.getUNITCLASS();
/*  834 */       String str10 = paramTMF_UPDATE.getSYSTEMMAX();
/*  835 */       String str11 = paramTMF_UPDATE.getORDERCODE();
/*  836 */       if ("Non SIU- CPU".equalsIgnoreCase(str9) || str10.compareTo("1") > 0 || "B".equalsIgnoreCase(str11)) {
/*  837 */         addDebug("2.2 TMF Calling Call ChwClsfCharCreate.CreateRPQQTYChar()");
/*  838 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         
/*      */         try {
/*  841 */           chwClsfCharCreate.CreateRPQQTYChar(str2, str3, str4, str5, str8);
/*  842 */           addMsg(chwClsfCharCreate.getRptSb());
/*  843 */         } catch (Exception exception) {
/*  844 */           addMsg(chwClsfCharCreate.getRptSb());
/*  845 */           throw exception;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  852 */     addDebug("TMF param feature_code=" + str5);
/*  853 */     addDebug("TMF param FCTYPE=" + str1);
/*  854 */     if (CommonUtils.hasLetter(str5) && 
/*  855 */       !this.FctypeEMap.containsKey(str1) && 
/*  856 */       !CommonUtils.getFirstSubString(str5, 3).equalsIgnoreCase("NEW")) {
/*  857 */       addDebug("3.1 TMF Calling Call ChwClsfCharCreate.CreateAlphaGroupChar()");
/*      */       
/*  859 */       chwClsfCharCreate = new ChwClsfCharCreate();
/*      */       
/*      */       try {
/*  862 */         chwClsfCharCreate.CreateAlphaGroupChar(str2, str3, str4, str5, str6, str8);
/*  863 */         addMsg(chwClsfCharCreate.getRptSb());
/*  864 */       } catch (Exception exception) {
/*  865 */         addMsg(chwClsfCharCreate.getRptSb());
/*  866 */         throw exception;
/*      */       } 
/*      */ 
/*      */       
/*  870 */       String str9 = paramTMF_UPDATE.getSYSTEMMAX();
/*  871 */       String str10 = paramTMF_UPDATE.getORDERCODE();
/*  872 */       if (str9.compareTo("1") > 0 || "B".equalsIgnoreCase(str10)) {
/*  873 */         addDebug("3.2 TMF Calling Call ChwClsfCharCreate.CreateAlphaQTYChar()");
/*  874 */         chwClsfCharCreate = new ChwClsfCharCreate();
/*      */         try {
/*  876 */           chwClsfCharCreate.CreateAlphaQTYChar(str2, str3, str4, str5, str7, str8);
/*  877 */           addMsg(chwClsfCharCreate.getRptSb());
/*  878 */         } catch (Exception exception) {
/*  879 */           addMsg(chwClsfCharCreate.getRptSb());
/*  880 */           throw exception;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  886 */     addDebug("5 TMF Calling Call TssClassificationMaint");
/*  887 */     callRdhClassificationMaint(str2, "MK_REFERENCE", str8);
/*      */     
/*  889 */     callRdhClassificationMaint(str2, "MK_T_VAO_NEW", str8);
/*      */ 
/*      */     
/*  892 */     UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", str8);
/*  893 */     addDebug("Calling " + updateParkStatus.getRFCName());
/*  894 */     updateParkStatus.execute();
/*  895 */     addOutput("Parking records updated successfully for ZDMRELNUM=" + str8);
/*  896 */     addDebug(updateParkStatus.createLogEntry());
/*      */   }
/*      */   
/*      */   protected void addRfcResult(RdhBase paramRdhBase) {
/*  900 */     addDebug(paramRdhBase.createLogEntry());
/*  901 */     if (paramRdhBase.getRfcrc() == 0) {
/*  902 */       addOutput(paramRdhBase.getRFCName() + " called successfully!");
/*      */     } else {
/*  904 */       addOutput(paramRdhBase.getRFCName() + " called  faild!");
/*  905 */       addOutput("return code is " + paramRdhBase.getRfcrc());
/*  906 */       addOutput(paramRdhBase.getError_text());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected String getFeatureCodeDesc(FEATURE paramFEATURE) {
/*  911 */     String str1 = "";
/*  912 */     addDebug("TMF getFeatureCodeDesc start");
/*  913 */     for (LANGUAGEELEMENT_FEATURE lANGUAGEELEMENT_FEATURE : paramFEATURE.getLANGUAGELIST()) {
/*      */       
/*  915 */       if ("1".equals(lANGUAGEELEMENT_FEATURE.getNLSID())) {
/*      */         
/*  917 */         str1 = lANGUAGEELEMENT_FEATURE.getBHINVNAME();
/*      */         break;
/*      */       } 
/*      */     } 
/*  921 */     addDebug("TMF getFeatureCodeDesc ...");
/*  922 */     String str2 = CommonUtils.getFirstSubString(str1, 30);
/*  923 */     addDebug("TMF getFeatureCodeDesc End");
/*  924 */     return str2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/*  933 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  942 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/*  945 */     EANList eANList = this.metaTbl.get(paramEntityItem.getEntityType());
/*  946 */     if (eANList == null) {
/*  947 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/*  948 */       eANList = entityGroup.getMetaAttribute();
/*      */       
/*  950 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/*  952 */     for (byte b = 0; b < eANList.size(); b++) {
/*  953 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/*  954 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/*  955 */       if (b + 1 < eANList.size()) {
/*  956 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*  959 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   public boolean exist(String paramString1, String paramString2, String paramString3) {
/*  963 */     boolean bool = false;
/*      */     try {
/*  965 */       Connection connection = this.m_db.getPDHConnection();
/*  966 */       String[] arrayOfString = new String[2];
/*  967 */       arrayOfString[0] = paramString2;
/*  968 */       arrayOfString[1] = paramString3;
/*  969 */       String str = CommonUtils.getPreparedSQL(paramString1, (Object[])arrayOfString);
/*  970 */       addDebug("querySql=" + str);
/*  971 */       PreparedStatement preparedStatement = connection.prepareStatement(paramString1);
/*  972 */       preparedStatement.setString(1, paramString2);
/*  973 */       preparedStatement.setString(2, paramString3);
/*      */       
/*  975 */       ResultSet resultSet = preparedStatement.executeQuery();
/*  976 */       if (resultSet.next()) {
/*  977 */         int i = resultSet.getInt(1);
/*  978 */         bool = (i > 0) ? true : false;
/*      */       } 
/*  980 */     } catch (SQLException sQLException) {
/*  981 */       sQLException.printStackTrace();
/*  982 */     } catch (MiddlewareException middlewareException) {
/*  983 */       middlewareException.printStackTrace();
/*      */     } 
/*      */     
/*  986 */     return bool;
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
/*      */   protected static String convertToTag(String paramString) {
/*  998 */     String str = "";
/*  999 */     StringBuffer stringBuffer = new StringBuffer();
/* 1000 */     StringCharacterIterator stringCharacterIterator = null;
/* 1001 */     char c = ' ';
/* 1002 */     if (paramString != null) {
/* 1003 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 1004 */       c = stringCharacterIterator.first();
/* 1005 */       while (c != '￿') {
/*      */         
/* 1007 */         switch (c) {
/*      */           
/*      */           case '<':
/* 1010 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/* 1013 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */           default:
/* 1016 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 1019 */         c = stringCharacterIterator.next();
/*      */       } 
/* 1021 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1024 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String convertToHTML(String paramString) {
/* 1035 */     String str = "";
/* 1036 */     StringBuffer stringBuffer = new StringBuffer();
/* 1037 */     StringCharacterIterator stringCharacterIterator = null;
/* 1038 */     char c = ' ';
/* 1039 */     if (paramString != null) {
/* 1040 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 1041 */       c = stringCharacterIterator.first();
/* 1042 */       while (c != '￿') {
/*      */         
/* 1044 */         switch (c) {
/*      */           
/*      */           case '<':
/* 1047 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/* 1050 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */ 
/*      */           
/*      */           case '"':
/* 1055 */             stringBuffer.append("&quot;");
/*      */             break;
/*      */           
/*      */           case '\'':
/* 1059 */             stringBuffer.append("&#" + c + ";");
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 1068 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 1071 */         c = stringCharacterIterator.next();
/*      */       } 
/* 1073 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1076 */     return str;
/*      */   }
/*      */   protected void addOutput(String paramString) {
/* 1079 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   } protected void addMsg(StringBuffer paramStringBuffer) {
/* 1081 */     this.rptSb.append(paramStringBuffer.toString() + NEWLINE);
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
/*      */   protected void addDebug(String paramString) {
/* 1094 */     if (3 <= this.abr_debuglvl) {
/* 1095 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1102 */     addOutput(paramString);
/* 1103 */     setReturnCode(-1);
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\TMFIERPABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */