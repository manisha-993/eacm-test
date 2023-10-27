/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.sg.rfc.Chw001ClfCreate;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwBomCreate;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwBomMaintain;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwCharMaintain;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwClassMaintain;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwConpMaintain;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwDepdMaintain;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwMachTypeMtc;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwMachTypeUpg;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwMatmCreate;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.ChwReadSalesBom;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.CommonUtils;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.MODEL;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.RdhChwFcProd;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.RdhClassificationMaint;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.RdhSvcMatmCreate;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.SVCMOD;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.UpdateParkStatus;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.XMLParse;
/*      */ import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.RFCConfig;
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
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ 
/*      */ public class MODELIERPABRSTATUS
/*      */   extends PokBaseABR {
/*   52 */   private StringBuffer rptSb = new StringBuffer();
/*   53 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   54 */   static final String NEWLINE = new String(FOOL_JTEST);
/*   55 */   private int abr_debuglvl = 0;
/*   56 */   private String navName = "";
/*   57 */   private Hashtable metaTbl = new Hashtable<>();
/*   58 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODEL' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*      */   
/*   60 */   private String COVNOTEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP  WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE !=T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   68 */   private String COVEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT TIMESTAMP AND T1.EFFTO > CURRENT TIMESTAMP  INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT TIMESTAMP  INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT TIMESTAMP AND M.EFFTO > CURRENT TIMESTAMP  WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   76 */   private static String FCTEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT TIMESTAMP  INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP  WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   84 */   String xml = null;
/*      */   
/*      */   public static void main(String[] paramArrayOfString) {
/*   87 */     System.out.println(FCTEQUALSQL);
/*      */   }
/*      */   
/*      */   public String getDescription() {
/*   91 */     return "MODELIERPABRSTATUS";
/*      */   }
/*      */   
/*      */   public String getABRVersion() {
/*   95 */     return "1.1";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  101 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  103 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  109 */     String str3 = "";
/*      */ 
/*      */     
/*  112 */     String str4 = "";
/*  113 */     String str5 = "";
/*      */     
/*  115 */     String[] arrayOfString = new String[10];
/*      */     
/*      */     try {
/*  118 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  119 */       arrayOfString[0] = getShortClassName(getClass());
/*  120 */       arrayOfString[1] = "ABR";
/*  121 */       str3 = messageFormat.format(arrayOfString);
/*  122 */       setDGTitle("MODELIERPABRSTATUS report");
/*  123 */       setDGString(getABRReturnCode());
/*  124 */       setDGRptName("MODELIERPABRSTATUS");
/*  125 */       setDGRptClass("MODELIERPABRSTATUS");
/*      */       
/*  127 */       setReturnCode(0);
/*      */       
/*  129 */       start_ABRBuild(false);
/*      */       
/*  131 */       this
/*  132 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */ 
/*      */ 
/*      */       
/*  136 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  138 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  145 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  146 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*      */       
/*  148 */       this.navName = getNavigationName();
/*  149 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/*  150 */       addDebug("navName=" + this.navName);
/*  151 */       addDebug("rootDesc" + str5);
/*      */ 
/*      */       
/*  154 */       Connection connection = this.m_db.getODSConnection();
/*  155 */       PreparedStatement preparedStatement = connection.prepareStatement(this.CACEHSQL);
/*  156 */       preparedStatement.setInt(1, entityItem.getEntityID());
/*  157 */       ResultSet resultSet = preparedStatement.executeQuery();
/*      */       
/*  159 */       while (resultSet.next()) {
/*  160 */         this.xml = resultSet.getString("XMLMESSAGE");
/*      */       }
/*  162 */       if (this.xml != null)
/*      */       {
/*  164 */         MODEL mODEL = (MODEL)XMLParse.getObjectFromXml(this.xml, MODEL.class);
/*  165 */         String str = "";
/*  166 */         if ("Hardware".equals(mODEL.getCATEGORY())) {
/*  167 */           processMachTypeNew(mODEL, connection);
/*  168 */           processMachTypeMODEL(mODEL, connection);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  181 */           if (exist(this.COVNOTEQUALSQL, mODEL.getMACHTYPE(), mODEL.getPDHDOMAIN())) {
/*  182 */             ChwMachTypeMtc chwMachTypeMtc = new ChwMachTypeMtc(mODEL, this.m_db.getPDHConnection(), connection);
/*  183 */             addDebug("Calling ChwMachTypeMtc");
/*      */             try {
/*  185 */               chwMachTypeMtc.execute();
/*  186 */               addMsg(chwMachTypeMtc.getRptSb());
/*  187 */             } catch (Exception exception) {
/*  188 */               addMsg(chwMachTypeMtc.getRptSb());
/*  189 */               throw exception;
/*      */             } 
/*      */             
/*  192 */             UpdateParkStatus updateParkStatus1 = new UpdateParkStatus("MD_CHW_IERP", mODEL.getMACHTYPE() + mODEL.getMODEL() + "MTC");
/*  193 */             runParkCaller((RdhBase)updateParkStatus1, mODEL.getMACHTYPE() + mODEL.getMODEL() + "MTC");
/*      */           } 
/*      */ 
/*      */           
/*  197 */           if (!CommonUtils.contains("Maintenance,MaintFeature", mODEL.getSUBCATEGORY()))
/*      */           {
/*  199 */             if (exist(this.COVEQUALSQL, mODEL.getMACHTYPE(), mODEL.getPDHDOMAIN()) || exist(FCTEQUALSQL, mODEL.getMACHTYPE(), mODEL.getPDHDOMAIN())) {
/*  200 */               ChwMachTypeUpg chwMachTypeUpg = new ChwMachTypeUpg(mODEL, this.m_db.getPDHConnection(), connection);
/*  201 */               addDebug("Calling ChwMachTypeUpg");
/*      */               try {
/*  203 */                 chwMachTypeUpg.execute();
/*  204 */                 addMsg(chwMachTypeUpg.getRptSb());
/*  205 */               } catch (Exception exception) {
/*  206 */                 addMsg(chwMachTypeUpg.getRptSb());
/*  207 */                 throw exception;
/*      */               } 
/*      */               
/*  210 */               UpdateParkStatus updateParkStatus1 = new UpdateParkStatus("MD_CHW_IERP", mODEL.getMACHTYPE() + mODEL.getMODEL() + "UPG");
/*  211 */               runParkCaller((RdhBase)updateParkStatus1, mODEL.getMACHTYPE() + mODEL.getMODEL() + "UPG");
/*      */             }
/*  213 */             else if (mODEL.getORDERCODE() != null && mODEL.getORDERCODE().trim().length() > 0 && CommonUtils.contains("M,B", mODEL.getORDERCODE())) {
/*  214 */               addDebug("Calling processMachTypeUpg");
/*  215 */               processMachTypeUpg(mODEL, connection);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*  220 */           Set<String> set = RFCConfig.getBHPlnts();
/*  221 */           addOutput("Start Bom Processing!");
/*  222 */           updateSalesBom(mODEL, "NEW", "BOMNEW", set);
/*  223 */           UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", mODEL.getMACHTYPE() + "BOMNEW");
/*  224 */           runParkCaller((RdhBase)updateParkStatus, mODEL.getMACHTYPE() + "BOMNEW");
/*  225 */           if ("M".equals(mODEL.getORDERCODE()) || "B".equals(mODEL.getORDERCODE())) {
/*  226 */             updateSalesBom(mODEL, "UPG", "BOMUPG", set);
/*  227 */             updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", mODEL.getMACHTYPE() + "BOMUPG");
/*  228 */             runParkCaller((RdhBase)updateParkStatus, mODEL.getMACHTYPE() + "BOMUPG");
/*      */           } 
/*  230 */           addOutput("Bom Processing Finished!");
/*  231 */           str = mODEL.getMACHTYPE() + mODEL.getMODEL();
/*  232 */           RdhChwFcProd rdhChwFcProd = new RdhChwFcProd(mODEL, str);
/*  233 */           runRfcCaller((RdhBase)rdhChwFcProd);
/*      */           
/*  235 */           updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", "M" + str);
/*  236 */           runParkCaller((RdhBase)updateParkStatus, "M" + str);
/*      */         
/*      */         }
/*  239 */         else if ("Service".equals(mODEL.getCATEGORY())) {
/*  240 */           processMachTypeMODEL_Svc(mODEL, connection);
/*  241 */           str = mODEL.getMACHTYPE() + mODEL.getMODEL();
/*  242 */           RdhChwFcProd rdhChwFcProd = new RdhChwFcProd(mODEL, str);
/*  243 */           runRfcCaller((RdhBase)rdhChwFcProd);
/*      */           
/*  245 */           UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", "M" + str);
/*  246 */           runParkCaller((RdhBase)updateParkStatus, "M" + str);
/*      */ 
/*      */         
/*      */         }
/*  250 */         else if ("Software".equals(mODEL.getCATEGORY())) {
/*  251 */           addError("It is not supported to feed software Model to iERP");
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  263 */     catch (Exception exception) {
/*  264 */       exception.printStackTrace();
/*      */       
/*  266 */       setReturnCode(-1);
/*  267 */       StringWriter stringWriter = new StringWriter();
/*  268 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  269 */       String str7 = "<pre>{0}</pre>";
/*  270 */       MessageFormat messageFormat = new MessageFormat(str6);
/*  271 */       setReturnCode(-3);
/*  272 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  274 */       arrayOfString[0] = exception.getMessage();
/*  275 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/*  276 */       messageFormat = new MessageFormat(str7);
/*  277 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/*  278 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/*  279 */       logError("Exception: " + exception.getMessage());
/*  280 */       logError(stringWriter.getBuffer().toString());
/*      */       
/*  282 */       setCreateDGEntity(true);
/*      */     }
/*      */     finally {
/*      */       
/*  286 */       StringBuffer stringBuffer = new StringBuffer();
/*  287 */       MessageFormat messageFormat = new MessageFormat(str2);
/*  288 */       arrayOfString[0] = this.m_prof.getOPName();
/*  289 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/*  290 */       arrayOfString[2] = this.m_prof.getWGName();
/*  291 */       arrayOfString[3] = getNow();
/*  292 */       arrayOfString[4] = stringBuffer.toString();
/*  293 */       arrayOfString[5] = str4 + " " + getABRVersion();
/*  294 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/*  295 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*      */       
/*  297 */       println(EACustom.getDocTypeHtml());
/*  298 */       println(this.rptSb.toString());
/*  299 */       printDGSubmitString();
/*  300 */       if (!isReadOnly()) {
/*  301 */         clearSoftLock();
/*      */       }
/*  303 */       println(EACustom.getTOUDiv());
/*  304 */       buildReportFooter();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateSalesBom(MODEL paramMODEL, String paramString1, String paramString2, Set<String> paramSet) throws Exception {
/*  310 */     for (String str1 : paramSet) {
/*      */       
/*  312 */       ChwBomCreate chwBomCreate = new ChwBomCreate(paramMODEL.getMACHTYPE() + paramString1, paramMODEL.getMACHTYPE() + paramString2, str1);
/*  313 */       addDebug("Calling ChwBomCreate");
/*  314 */       addDebug(chwBomCreate.generateJson());
/*      */       try {
/*  316 */         chwBomCreate.execute();
/*  317 */         addDebug(chwBomCreate.createLogEntry());
/*  318 */       } catch (Exception exception) {
/*  319 */         addOutput(exception.getMessage());
/*      */         
/*      */         continue;
/*      */       } 
/*  323 */       ChwReadSalesBom chwReadSalesBom = new ChwReadSalesBom(paramMODEL.getMACHTYPE() + paramString1, str1);
/*  324 */       addDebug("Calling ChwReadSalesBom");
/*  325 */       addDebug(chwReadSalesBom.generateJson());
/*      */       try {
/*  327 */         chwReadSalesBom.execute();
/*  328 */         addDebug(chwReadSalesBom.createLogEntry());
/*  329 */       } catch (Exception exception) {
/*  330 */         if (!exception.getMessage().contains("exists in Mast table but not defined to Stpo table")) {
/*      */ 
/*      */           
/*  333 */           addOutput(exception.getMessage());
/*      */           continue;
/*      */         } 
/*      */       } 
/*  337 */       addDebug("Bom Read result:" + chwReadSalesBom.getRETURN_MULTIPLE_OBJ().toString());
/*  338 */       List<HashMap<String, String>> list = (List)chwReadSalesBom.getRETURN_MULTIPLE_OBJ().get("stpo_api02");
/*  339 */       String str2 = paramMODEL.getMACHTYPE() + paramMODEL.getMODEL();
/*  340 */       if (list != null && list.size() > 0) {
/*  341 */         if (hasMatchComponent(list, str2)) {
/*  342 */           addDebug("updateSalesBom exist component " + str2); continue;
/*      */         } 
/*  344 */         String str = getMaxItemNo(list);
/*  345 */         str = generateItemNumberString(str);
/*      */         
/*  347 */         ChwBomMaintain chwBomMaintain1 = new ChwBomMaintain(paramMODEL.getMACHTYPE() + paramString1, paramMODEL.getMACHTYPE() + paramString2, str1, paramMODEL.getMACHTYPE() + paramMODEL.getMODEL(), str, "SC_" + paramMODEL.getMACHTYPE() + "_MOD_" + paramMODEL.getMODEL());
/*  348 */         addDebug("Calling chwBomMaintain");
/*  349 */         addDebug(chwBomMaintain1.generateJson());
/*      */         try {
/*  351 */           chwBomMaintain1.execute();
/*  352 */           addDebug(chwBomMaintain1.createLogEntry());
/*  353 */         } catch (Exception exception) {
/*  354 */           addOutput(exception.getMessage());
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  360 */       ChwBomMaintain chwBomMaintain = new ChwBomMaintain(paramMODEL.getMACHTYPE() + paramString1, paramMODEL.getMACHTYPE() + paramString2, str1, paramMODEL.getMACHTYPE() + paramMODEL.getMODEL(), "0005", "SC_" + paramMODEL.getMACHTYPE() + "_MOD_" + paramMODEL.getMODEL());
/*  361 */       addDebug("Calling chwBomMaintain");
/*  362 */       addDebug(chwBomMaintain.generateJson());
/*      */       try {
/*  364 */         chwBomMaintain.execute();
/*  365 */         addDebug(chwBomMaintain.createLogEntry());
/*  366 */       } catch (Exception exception) {
/*  367 */         addOutput(exception.getMessage());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getMaxItemNo(List<HashMap<String, String>> paramList) {
/*  376 */     ArrayList<Integer> arrayList = new ArrayList(); int i;
/*  377 */     for (i = 0; i < paramList.size(); i++) {
/*  378 */       String str = (String)((HashMap)paramList.get(i)).get("ITEM_NO");
/*  379 */       arrayList.add(Integer.valueOf(Integer.parseInt(str)));
/*      */     } 
/*  381 */     i = ((Integer)Collections.<Integer>max(arrayList)).intValue();
/*  382 */     return String.format("%04d", new Object[] { Integer.valueOf(i) });
/*      */   }
/*      */   
/*      */   private String generateItemNumberString(String paramString) {
/*  386 */     int i = Integer.parseInt(paramString) + 5;
/*  387 */     return String.format("%04d", new Object[] { Integer.valueOf(i) });
/*      */   }
/*      */   
/*      */   private boolean hasMatchComponent(List<HashMap<String, String>> paramList, String paramString) {
/*  391 */     for (byte b = 0; b < paramList.size(); b++) {
/*  392 */       String str = (String)((HashMap)paramList.get(b)).get("COMPONENT");
/*  393 */       if (str.trim().equals(paramString)) {
/*  394 */         return true;
/*      */       }
/*      */     } 
/*  397 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/*  405 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  414 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/*  417 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/*  418 */     if (eANList == null) {
/*  419 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/*  420 */       eANList = entityGroup.getMetaAttribute();
/*      */       
/*  422 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/*  424 */     for (byte b = 0; b < eANList.size(); b++) {
/*  425 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/*  426 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/*  427 */       if (b + 1 < eANList.size()) {
/*  428 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*  431 */     return stringBuffer.toString();
/*      */   }
/*      */   private String getTableMapingDate(String paramString, SVCMOD paramSVCMOD) {
/*  434 */     String str = null;
/*  435 */     if ("MG_PRODUCTTYPE".equals(paramString)) {
/*  436 */       if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/*  437 */         if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  438 */           if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operation Based".equals(paramSVCMOD.getGROUP())) {
/*  439 */             str = "S3";
/*      */           }
/*  441 */         } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  442 */           if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/*  443 */             str = "S6";
/*  444 */           } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/*  445 */             str = "S7";
/*  446 */           } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/*  447 */             str = "S5";
/*  448 */           } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/*  449 */             str = "S4";
/*  450 */           } else if ("OEM".equals(paramSVCMOD.getGROUP())) {
/*  451 */             str = "S4";
/*      */           }
/*  453 */           else if ("ICA/NEC".equals(paramSVCMOD.getGROUP())) {
/*  454 */             str = "S2";
/*      */           }
/*      */         
/*  457 */         } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && 
/*  458 */           "Non-Federated".equals(paramSVCMOD.getGROUP())) {
/*  459 */           str = "S2";
/*      */         }
/*      */       
/*  462 */       } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && 
/*  463 */         "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  464 */         str = "S8";
/*      */       }
/*      */     
/*  467 */     } else if ("MM_CUSTOM_TYPE".equals(paramString)) {
/*      */       
/*  469 */       if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/*  470 */         if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  471 */           if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operation Based".equals(paramSVCMOD.getGROUP())) {
/*  472 */             str = "OCI";
/*      */           }
/*  474 */         } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  475 */           if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/*  476 */             str = "PC";
/*  477 */           } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/*  478 */             str = "IC";
/*  479 */           } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/*  480 */             str = "TE";
/*  481 */           } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/*  482 */             str = "SA";
/*  483 */           } else if ("OEM".equals(paramSVCMOD.getGROUP())) {
/*  484 */             str = "SA";
/*  485 */           } else if ("ICA/NEC".equals(paramSVCMOD.getGROUP())) {
/*  486 */             str = "No characteristic";
/*      */           }
/*      */         
/*  489 */         } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && 
/*  490 */           "Non-Federated".equals(paramSVCMOD.getGROUP())) {
/*  491 */           str = "SPI";
/*      */         }
/*      */       
/*  494 */       } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && 
/*  495 */         "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  496 */         str = "IPSC";
/*      */       }
/*      */     
/*      */     }
/*  500 */     else if ("MM_CUSTOM_COSTING".equals(paramString)) {
/*      */       
/*  502 */       if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/*  503 */         if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  504 */           if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operation Based".equals(paramSVCMOD.getGROUP())) {
/*  505 */             str = "WBS";
/*      */           }
/*  507 */         } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  508 */           if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/*  509 */             str = "WBS";
/*  510 */           } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/*  511 */             str = "WBS";
/*  512 */           } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/*  513 */             str = "WBS";
/*  514 */           } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/*  515 */             str = "WBS";
/*  516 */           } else if ("OEM".equals(paramSVCMOD.getGROUP())) {
/*  517 */             str = "WBS";
/*  518 */           } else if ("ICA/NEC".equals(paramSVCMOD.getGROUP())) {
/*  519 */             str = "No characteristic";
/*      */           }
/*      */         
/*  522 */         } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && 
/*  523 */           "Non-Federated".equals(paramSVCMOD.getGROUP())) {
/*  524 */           str = "WBS";
/*      */         }
/*      */       
/*  527 */       } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && 
/*  528 */         "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  529 */         str = "No characteristic";
/*      */       }
/*      */     
/*      */     }
/*  533 */     else if ("MM_PROFIT_CENTER".equals(paramString)) {
/*      */       
/*  535 */       if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/*  536 */         if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  537 */           if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operation Based".equals(paramSVCMOD.getGROUP())) {
/*  538 */             str = "D";
/*      */           }
/*  540 */         } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  541 */           if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/*  542 */             str = "C";
/*  543 */           } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/*  544 */             str = "C";
/*  545 */           } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/*  546 */             str = "C";
/*  547 */           } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/*  548 */             str = "C";
/*  549 */           } else if ("OEM".equals(paramSVCMOD.getGROUP())) {
/*  550 */             str = "C";
/*  551 */           } else if ("ICA/NEC".equals(paramSVCMOD.getGROUP())) {
/*  552 */             str = "No characteristic";
/*      */           }
/*      */         
/*  555 */         } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && 
/*  556 */           "Non-Federated".equals(paramSVCMOD.getGROUP())) {
/*  557 */           str = "C";
/*      */         }
/*      */       
/*  560 */       } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && 
/*  561 */         "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  562 */         str = "No characteristic";
/*      */       }
/*      */     
/*      */     }
/*  566 */     else if ("MM_TAX_CATEGORY".equals(paramString)) {
/*      */       
/*  568 */       if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/*  569 */         if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  570 */           if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operation Based".equals(paramSVCMOD.getGROUP())) {
/*  571 */             str = "D";
/*      */           }
/*  573 */         } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  574 */           if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/*  575 */             str = "C";
/*  576 */           } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/*  577 */             str = "C";
/*  578 */           } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/*  579 */             str = "C";
/*  580 */           } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/*  581 */             str = "C";
/*  582 */           } else if ("OEM".equals(paramSVCMOD.getGROUP())) {
/*  583 */             str = "C";
/*  584 */           } else if ("ICA/NEC".equals(paramSVCMOD.getGROUP())) {
/*  585 */             str = "No characteristic";
/*      */           }
/*      */         
/*  588 */         } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && 
/*  589 */           "Non-Federated".equals(paramSVCMOD.getGROUP())) {
/*  590 */           str = "C";
/*      */         }
/*      */       
/*  593 */       } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && 
/*  594 */         "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/*  595 */         str = "No characteristic";
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  601 */     return str;
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
/*  612 */     String str = "";
/*  613 */     StringBuffer stringBuffer = new StringBuffer();
/*  614 */     StringCharacterIterator stringCharacterIterator = null;
/*  615 */     char c = ' ';
/*  616 */     if (paramString != null) {
/*  617 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/*  618 */       c = stringCharacterIterator.first();
/*  619 */       while (c != '￿') {
/*      */         
/*  621 */         switch (c) {
/*      */           
/*      */           case '<':
/*  624 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/*  627 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */ 
/*      */           
/*      */           case '"':
/*  632 */             stringBuffer.append("&quot;");
/*      */             break;
/*      */           
/*      */           case '\'':
/*  636 */             stringBuffer.append("&#" + c + ";");
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/*  645 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/*  648 */         c = stringCharacterIterator.next();
/*      */       } 
/*  650 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/*  653 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String convertToTag(String paramString) {
/*  664 */     String str = "";
/*  665 */     StringBuffer stringBuffer = new StringBuffer();
/*  666 */     StringCharacterIterator stringCharacterIterator = null;
/*  667 */     char c = ' ';
/*  668 */     if (paramString != null) {
/*  669 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/*  670 */       c = stringCharacterIterator.first();
/*  671 */       while (c != '￿') {
/*      */         
/*  673 */         switch (c) {
/*      */           
/*      */           case '<':
/*  676 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/*  679 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */           default:
/*  682 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/*  685 */         c = stringCharacterIterator.next();
/*      */       } 
/*  687 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/*  690 */     return str;
/*      */   }
/*      */   protected void addOutput(String paramString) {
/*  693 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   } protected void addMsg(StringBuffer paramStringBuffer) {
/*  695 */     this.rptSb.append(paramStringBuffer.toString() + NEWLINE);
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
/*  708 */     if (3 <= this.abr_debuglvl) {
/*  709 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/*  716 */     addOutput(paramString);
/*  717 */     setReturnCode(-1);
/*      */   }
/*      */   
/*      */   protected void runRfcCaller(RdhBase paramRdhBase) throws Exception {
/*  721 */     addDebug("Calling " + paramRdhBase.getRFCName());
/*  722 */     paramRdhBase.execute();
/*  723 */     addDebug(paramRdhBase.createLogEntry());
/*  724 */     if (paramRdhBase.getRfcrc() == 0) {
/*  725 */       addOutput(paramRdhBase.getRFCName() + " called successfully!");
/*      */     } else {
/*  727 */       addOutput(paramRdhBase.getRFCName() + " called  faild!");
/*  728 */       addOutput(paramRdhBase.getError_text());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void runParkCaller(RdhBase paramRdhBase, String paramString) throws Exception {
/*  733 */     addDebug("Calling " + paramRdhBase.getRFCName());
/*      */     try {
/*  735 */       paramRdhBase.execute();
/*  736 */     } catch (Exception exception) {
/*      */       
/*  738 */       exception.printStackTrace();
/*      */     } 
/*  740 */     addDebug(paramRdhBase.createLogEntry());
/*  741 */     if (paramRdhBase.getRfcrc() == 0) {
/*  742 */       addOutput("Parking records updated successfully for ZDMRELNUM=" + paramString);
/*      */     } else {
/*  744 */       addOutput(paramRdhBase.getRFCName() + " called faild!");
/*  745 */       addOutput(paramRdhBase.getError_text());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void processMachTypeMODEL(MODEL paramMODEL, Connection paramConnection) throws Exception {
/*  750 */     String str1 = "ZPRT";
/*  751 */     String str2 = paramMODEL.getMACHTYPE() + paramMODEL.getMODEL();
/*      */     
/*  753 */     ChwMatmCreate chwMatmCreate = new ChwMatmCreate(paramMODEL, str1, str2, str2);
/*  754 */     runRfcCaller((RdhBase)chwMatmCreate);
/*      */ 
/*      */     
/*  757 */     Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(paramMODEL, str1, str2, str2, paramConnection);
/*  758 */     addDebug("Calling Chw001ClfCreate");
/*      */     try {
/*  760 */       chw001ClfCreate.execute();
/*  761 */       addMsg(chw001ClfCreate.getRptSb());
/*  762 */     } catch (Exception exception) {
/*  763 */       addMsg(chw001ClfCreate.getRptSb());
/*  764 */       throw exception;
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
/*  778 */     String str3 = paramMODEL.getMACHTYPE() + paramMODEL.getMODEL();
/*  779 */     String str4 = "SC_" + paramMODEL.getMACHTYPE() + "_MOD_" + paramMODEL.getMODEL();
/*  780 */     String str5 = "5";
/*  781 */     String str6 = "SC_" + paramMODEL.getMACHTYPE() + "_MOD_" + paramMODEL.getMODEL();
/*  782 */     String str7 = "$PARENT.MK_T_" + paramMODEL.getMACHTYPE() + "_MOD='" + paramMODEL.getMODEL() + "'";
/*  783 */     ChwDepdMaintain chwDepdMaintain = new ChwDepdMaintain(str3, str4, str5, str6);
/*  784 */     chwDepdMaintain.addSourceLineCondition(str7);
/*  785 */     runRfcCaller((RdhBase)chwDepdMaintain);
/*      */ 
/*      */ 
/*      */     
/*  789 */     UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", paramMODEL.getMACHTYPE() + paramMODEL.getMODEL());
/*  790 */     runParkCaller((RdhBase)updateParkStatus, paramMODEL.getMACHTYPE() + paramMODEL.getMODEL());
/*      */   }
/*      */ 
/*      */   
/*      */   public void processMachTypeNew(MODEL paramMODEL, Connection paramConnection) throws Exception {
/*  795 */     String str1 = "ZMAT";
/*  796 */     String str2 = paramMODEL.getMACHTYPE() + "NEW";
/*  797 */     String str3 = paramMODEL.getMACHTYPE() + paramMODEL.getMODEL() + "NEW";
/*  798 */     ChwMatmCreate chwMatmCreate = new ChwMatmCreate(paramMODEL, str1, str2, str3);
/*  799 */     runRfcCaller((RdhBase)chwMatmCreate);
/*      */ 
/*      */     
/*  802 */     Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(paramMODEL, str1, str2, str3, paramConnection);
/*  803 */     addDebug("Calling Chw001ClfCreate");
/*      */     try {
/*  805 */       chw001ClfCreate.execute();
/*  806 */       addMsg(chw001ClfCreate.getRptSb());
/*  807 */     } catch (Exception exception) {
/*  808 */       addMsg(chw001ClfCreate.getRptSb());
/*  809 */       throw exception;
/*      */     } 
/*      */ 
/*      */     
/*  813 */     String str4 = str2;
/*  814 */     String str5 = "MK_REFERENCE";
/*  815 */     String str6 = "300";
/*  816 */     RdhClassificationMaint rdhClassificationMaint1 = new RdhClassificationMaint(str4, str5, str6, "H", str3);
/*  817 */     runRfcCaller((RdhBase)rdhClassificationMaint1);
/*      */ 
/*      */     
/*  820 */     str5 = "MK_T_VAO_NEW";
/*  821 */     rdhClassificationMaint1 = new RdhClassificationMaint(str4, str5, str6, "H", str3);
/*  822 */     runRfcCaller((RdhBase)rdhClassificationMaint1);
/*      */ 
/*      */     
/*  825 */     String str7 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  839 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(str3, "MK_T_" + paramMODEL.getMACHTYPE() + "_MOD", "CHAR", 6, str7, str7, str7, str7, "S", str7, str7, "X", paramMODEL.getMACHTYPE() + " Model Characteristic");
/*      */ 
/*      */     
/*  842 */     String str8 = "";
/*  843 */     List list = paramMODEL.getLANGUAGELIST();
/*  844 */     for (LANGUAGE lANGUAGE : list) {
/*  845 */       String str = lANGUAGE.getNLSID();
/*  846 */       if ("1".equals(str)) {
/*  847 */         str8 = lANGUAGE.getINVNAME();
/*      */         break;
/*      */       } 
/*      */     } 
/*  851 */     String str9 = CommonUtils.getFirstSubString(str8, 25) + " " + paramMODEL.getMODEL();
/*  852 */     chwCharMaintain.addValue(paramMODEL.getMODEL(), str9);
/*  853 */     runRfcCaller((RdhBase)chwCharMaintain);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  860 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(str3, "MK_" + paramMODEL.getMACHTYPE() + "_MOD", "MK_" + paramMODEL.getMACHTYPE() + "_MOD");
/*      */ 
/*      */     
/*  863 */     chwClassMaintain.addCharacteristic("MK_T_" + paramMODEL.getMACHTYPE() + "_MOD");
/*  864 */     runRfcCaller((RdhBase)chwClassMaintain);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  870 */     RdhClassificationMaint rdhClassificationMaint2 = new RdhClassificationMaint(str4, "MK_" + paramMODEL.getMACHTYPE() + "_MOD", "300", "H", str3);
/*      */ 
/*      */ 
/*      */     
/*  874 */     runRfcCaller((RdhBase)rdhClassificationMaint2);
/*      */ 
/*      */     
/*  877 */     String str10 = paramMODEL.getMACHTYPE() + "NEW";
/*  878 */     String str11 = "PR_" + paramMODEL.getMACHTYPE() + "_SET_MODEL";
/*  879 */     String str12 = "7";
/*  880 */     String str13 = paramMODEL.getMACHTYPE() + " Set Model";
/*  881 */     String str14 = "$self.mk_model2 = $self.mk_t_" + paramMODEL.getMACHTYPE() + "_mod";
/*  882 */     ChwDepdMaintain chwDepdMaintain = new ChwDepdMaintain(str3, str11, str12, str13);
/*  883 */     chwDepdMaintain.addSourceLineCondition(str14);
/*  884 */     runRfcCaller((RdhBase)chwDepdMaintain);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  893 */     ChwConpMaintain chwConpMaintain = new ChwConpMaintain(str4, "INITIAL", "SD01", "2", paramMODEL.getMACHTYPE() + "NEWUI", str3);
/*      */ 
/*      */ 
/*      */     
/*  897 */     chwConpMaintain.addConfigDependency("E2E", str7);
/*      */     
/*  899 */     chwConpMaintain.addConfigDependency("PR_" + paramMODEL.getMACHTYPE() + "_SET_MODEL", str7);
/*      */     
/*  901 */     chwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", str7);
/*      */     
/*  903 */     chwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", str7);
/*      */     
/*  905 */     chwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", str7);
/*  906 */     runRfcCaller((RdhBase)chwConpMaintain);
/*      */ 
/*      */     
/*  909 */     UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", str3);
/*  910 */     runParkCaller((RdhBase)updateParkStatus, str3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void processMachTypeUpg(MODEL paramMODEL, Connection paramConnection) throws Exception {
/*  915 */     String str1 = "ZMAT";
/*  916 */     String str2 = paramMODEL.getMACHTYPE() + "UPG";
/*  917 */     String str3 = paramMODEL.getMACHTYPE() + paramMODEL.getMODEL() + "UPG";
/*  918 */     ChwMatmCreate chwMatmCreate = new ChwMatmCreate(paramMODEL, str1, str2, str3);
/*  919 */     runRfcCaller((RdhBase)chwMatmCreate);
/*      */     
/*  921 */     Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(paramMODEL, str1, str2, str3, paramConnection);
/*  922 */     addDebug("Calling Chw001ClfCreate");
/*      */     try {
/*  924 */       chw001ClfCreate.execute();
/*  925 */       addMsg(chw001ClfCreate.getRptSb());
/*  926 */     } catch (Exception exception) {
/*  927 */       addMsg(chw001ClfCreate.getRptSb());
/*  928 */       throw exception;
/*      */     } 
/*  930 */     String str4 = str2;
/*  931 */     String str5 = "MK_REFERENCE";
/*  932 */     String str6 = "300";
/*  933 */     RdhClassificationMaint rdhClassificationMaint1 = new RdhClassificationMaint(str4, str5, str6, "H", str3);
/*  934 */     runRfcCaller((RdhBase)rdhClassificationMaint1);
/*      */     
/*  936 */     str5 = "MK_T_VAO_NEW";
/*  937 */     rdhClassificationMaint1 = new RdhClassificationMaint(str4, str5, str6, "H", str3);
/*  938 */     runRfcCaller((RdhBase)rdhClassificationMaint1);
/*      */     
/*  940 */     str5 = "MK_D_VAO_NEW";
/*  941 */     rdhClassificationMaint1 = new RdhClassificationMaint(str4, str5, str6, "H", str3);
/*  942 */     runRfcCaller((RdhBase)rdhClassificationMaint1);
/*      */     
/*  944 */     str5 = "MK_FC_EXCH";
/*  945 */     rdhClassificationMaint1 = new RdhClassificationMaint(str4, str5, str6, "H", str3);
/*  946 */     runRfcCaller((RdhBase)rdhClassificationMaint1);
/*      */     
/*  948 */     str5 = "MK_FC_CONV";
/*  949 */     rdhClassificationMaint1 = new RdhClassificationMaint(str4, str5, str6, "H", str3);
/*  950 */     runRfcCaller((RdhBase)rdhClassificationMaint1);
/*      */ 
/*      */     
/*  953 */     String str7 = "MK_T_" + paramMODEL.getMACHTYPE() + "_MOD";
/*  954 */     String str8 = "CHAR";
/*  955 */     byte b1 = 6;
/*  956 */     String str9 = "";
/*  957 */     String str10 = "";
/*  958 */     String str11 = "";
/*  959 */     String str12 = "";
/*  960 */     String str13 = "S";
/*  961 */     String str14 = "";
/*  962 */     String str15 = "";
/*  963 */     String str16 = "X";
/*  964 */     String str17 = paramMODEL.getMACHTYPE() + " Model Characteristic";
/*  965 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(str3, str7, str8, b1, str9, str10, str11, str12, str13, str14, str15, str16, str17);
/*  966 */     String str18 = null;
/*  967 */     List<LANGUAGE> list = paramMODEL.getLANGUAGELIST();
/*  968 */     String str19 = null;
/*  969 */     for (byte b2 = 0; b2 < list.size(); b2++) {
/*  970 */       if ("1".equals(((LANGUAGE)list.get(b2)).getNLSID())) {
/*  971 */         str19 = ((LANGUAGE)list.get(b2)).getINVNAME();
/*      */       }
/*      */     } 
/*      */     
/*  975 */     str18 = (str19 == null) ? "" : (str19.substring(0, Math.min(str19.length(), 24)) + " " + paramMODEL.getMODEL());
/*  976 */     chwCharMaintain.addValue(paramMODEL.getMODEL(), str18);
/*  977 */     runRfcCaller((RdhBase)chwCharMaintain);
/*      */     
/*  979 */     str5 = "MK_" + paramMODEL.getMACHTYPE() + "_MOD";
/*  980 */     str6 = str5;
/*  981 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(str3, str5, str6);
/*  982 */     chwClassMaintain.addCharacteristic("MK_T_" + paramMODEL.getMACHTYPE() + "_MOD");
/*  983 */     runRfcCaller((RdhBase)chwClassMaintain);
/*      */     
/*  985 */     str6 = "300";
/*  986 */     RdhClassificationMaint rdhClassificationMaint2 = new RdhClassificationMaint(str4, str5, str6, "H", str3);
/*  987 */     runRfcCaller((RdhBase)rdhClassificationMaint2);
/*      */ 
/*      */     
/*  990 */     String str20 = "INITIAL";
/*  991 */     String str21 = "SD01";
/*  992 */     String str22 = "2";
/*  993 */     String str23 = paramMODEL.getMACHTYPE() + "UPGUI";
/*  994 */     ChwConpMaintain chwConpMaintain = new ChwConpMaintain(str4, str20, str21, str22, str23, str3);
/*  995 */     chwConpMaintain.addConfigDependency("E2E", "");
/*  996 */     chwConpMaintain.addConfigDependency("PR_" + paramMODEL.getMACHTYPE() + "_SET_MODEL", "");
/*  997 */     chwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", "");
/*  998 */     chwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", "");
/*  999 */     chwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", "");
/* 1000 */     runRfcCaller((RdhBase)chwConpMaintain);
/*      */ 
/*      */     
/* 1003 */     UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", str3);
/* 1004 */     runParkCaller((RdhBase)updateParkStatus, str3);
/*      */   }
/*      */   
/*      */   public void processMachTypeMODEL_Svc(MODEL paramMODEL, Connection paramConnection) throws Exception {
/* 1008 */     String str1 = "ZPRT";
/* 1009 */     String str2 = paramMODEL.getMACHTYPE() + paramMODEL.getMODEL();
/*      */     
/* 1011 */     RdhSvcMatmCreate rdhSvcMatmCreate = new RdhSvcMatmCreate(paramMODEL);
/* 1012 */     runRfcCaller((RdhBase)rdhSvcMatmCreate);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1017 */     Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(paramMODEL, str1, str2, str2, paramConnection);
/* 1018 */     addDebug("Calling Chw001ClfCreate");
/*      */     try {
/* 1020 */       chw001ClfCreate.execute();
/* 1021 */       addMsg(chw001ClfCreate.getRptSb());
/* 1022 */     } catch (Exception exception) {
/* 1023 */       addMsg(chw001ClfCreate.getRptSb());
/* 1024 */       throw exception;
/*      */     } 
/*      */ 
/*      */     
/* 1028 */     UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", paramMODEL.getMACHTYPE() + paramMODEL.getMODEL());
/* 1029 */     runParkCaller((RdhBase)updateParkStatus, paramMODEL.getMACHTYPE() + paramMODEL.getMODEL());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean exist(String paramString1, String paramString2, String paramString3) {
/* 1034 */     boolean bool = false;
/*      */     try {
/* 1036 */       Connection connection = this.m_db.getPDHConnection();
/* 1037 */       String[] arrayOfString = new String[2];
/* 1038 */       arrayOfString[0] = paramString2;
/* 1039 */       arrayOfString[1] = paramString3;
/* 1040 */       String str = CommonUtils.getPreparedSQL(paramString1, (Object[])arrayOfString);
/* 1041 */       addDebug("querySql=" + str);
/* 1042 */       PreparedStatement preparedStatement = connection.prepareStatement(paramString1);
/* 1043 */       preparedStatement.setString(1, paramString2);
/* 1044 */       preparedStatement.setString(2, paramString3);
/*      */       
/* 1046 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 1047 */       if (resultSet.next()) {
/* 1048 */         int i = resultSet.getInt(1);
/* 1049 */         bool = (i > 0) ? true : false;
/*      */       } 
/* 1051 */     } catch (SQLException sQLException) {
/* 1052 */       sQLException.printStackTrace();
/* 1053 */     } catch (MiddlewareException middlewareException) {
/* 1054 */       middlewareException.printStackTrace();
/*      */     } 
/* 1056 */     return bool;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\MODELIERPABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */