/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.Chw001ClfCreate;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwBomCreate;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwBomMaintain;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwCharMaintain;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwClassMaintain;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwConpMaintain;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwDepdMaintain;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwFCTYMDMFCMaint;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwMatmCreate;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwReadSalesBom;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.CommonUtils;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.FCTRANSACTION;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.MODEL;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhClassificationMaint;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.UpdateParkStatus;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.XMLParse;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.RFCConfig;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.FileLock;
/*     */ import java.nio.channels.OverlappingFileLockException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.StringCharacterIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class FCTRANSACTIONIERPABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*  56 */   private StringBuffer rptSb = new StringBuffer();
/*  57 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  58 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  59 */   private int abr_debuglvl = 0;
/*  60 */   private String navName = "";
/*  61 */   private Hashtable metaTbl = new Hashtable<>();
/*  62 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'FCTRANSACTION' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*     */   
/*  64 */   private String COVNOTEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP  WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE !=T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private String COVEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT TIMESTAMP AND T1.EFFTO > CURRENT TIMESTAMP  INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT TIMESTAMP  INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT TIMESTAMP AND M.EFFTO > CURRENT TIMESTAMP  WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private String FCTEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT TIMESTAMP  INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP  INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP  WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private String MODEL_MACHTYPE = "SELECT DISTINCT t2.ATTRIBUTEVALUE AS MODEL, t3.ATTRIBUTEVALUE AS INVNAME FROM OPICM.flag F  INNER JOIN opicm.flag t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='MACHTYPEATR' AND T1.ATTRIBUTEVALUE = ? AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MODELATR' AND t2.NLSID=1 AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='INVNAME' AND t3.NLSID =1 AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t1.ENTITYID AND F1.ENTITYTYPE =t1.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELTIERPABRSTATUS')  AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=?  WITH ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   String xml = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 103 */     return "FCTRANSACTIONIERPABRSTATUS";
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/* 107 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 113 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/* 115 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     String str3 = "";
/*     */ 
/*     */     
/* 124 */     String str4 = "";
/* 125 */     String str5 = "";
/*     */     
/* 127 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/* 130 */       MessageFormat messageFormat = new MessageFormat(str1);
/* 131 */       arrayOfString[0] = getShortClassName(getClass());
/* 132 */       arrayOfString[1] = "ABR";
/* 133 */       str3 = messageFormat.format(arrayOfString);
/* 134 */       setDGTitle("FCTRANSACTIONIERPABRSTATUS report");
/* 135 */       setDGString(getABRReturnCode());
/* 136 */       setDGRptName("FCTRANSACTIONIERPABRSTATUS");
/* 137 */       setDGRptClass("FCTRANSACTIONIERPABRSTATUS");
/*     */       
/* 139 */       setReturnCode(0);
/*     */       
/* 141 */       start_ABRBuild(false);
/*     */       
/* 143 */       this
/* 144 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/* 148 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/* 150 */               getEntityType(), getEntityID()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 157 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 158 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*     */       
/* 160 */       this.navName = getNavigationName();
/* 161 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/* 162 */       addDebug("navName=" + this.navName);
/* 163 */       addDebug("rootDesc" + str5);
/*     */       
/* 165 */       String str = "";
/* 166 */       Connection connection = this.m_db.getODSConnection();
/* 167 */       PreparedStatement preparedStatement = connection.prepareStatement(this.CACEHSQL);
/* 168 */       preparedStatement.setInt(1, entityItem.getEntityID());
/* 169 */       ResultSet resultSet = preparedStatement.executeQuery();
/*     */       
/* 171 */       while (resultSet.next()) {
/* 172 */         this.xml = resultSet.getString("XMLMESSAGE");
/*     */       }
/* 174 */       if (this.xml != null)
/*     */       {
/* 176 */         FCTRANSACTION fCTRANSACTION = (FCTRANSACTION)XMLParse.getObjectFromXml(this.xml, FCTRANSACTION.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 186 */         String str6 = getModelFromXML(fCTRANSACTION.getTOMACHTYPE(), fCTRANSACTION.getTOMODEL(), connection);
/* 187 */         if ("".equals(str6)) {
/* 188 */           addOutput("modelxml is empty");
/*     */           return;
/*     */         } 
/* 191 */         MODEL mODEL = (MODEL)XMLParse.getObjectFromXml(str6, MODEL.class);
/* 192 */         if (mODEL == null) {
/* 193 */           addOutput("MODEL is Null");
/*     */           return;
/*     */         } 
/* 196 */         String str7 = fCTRANSACTION.getTOMACHTYPE() + "UPG";
/* 197 */         String str8 = fCTRANSACTION.getTOMACHTYPE() + "UPG";
/*     */         
/* 199 */         addDebug("FCTRANSACTION ChwMatmCreate ");
/* 200 */         ChwMatmCreate chwMatmCreate = new ChwMatmCreate(mODEL, "ZMAT", str7, str8);
/* 201 */         runRfcCaller((RdhBase)chwMatmCreate);
/*     */         
/* 203 */         Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(mODEL, "ZMAT", str7, str8, connection);
/* 204 */         addDebug("Calling Chw001ClfCreate");
/*     */         try {
/* 206 */           chw001ClfCreate.execute();
/* 207 */           addMsg(chw001ClfCreate.getRptSb());
/* 208 */         } catch (Exception exception) {
/* 209 */           addMsg(chw001ClfCreate.getRptSb());
/* 210 */           throw exception;
/*     */         } 
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
/* 226 */         ChwCharMaintain chwCharMaintain = new ChwCharMaintain(str8, "MK_T_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD", "CHAR", 6, str, str, str, str, "S", str, str, "X", fCTRANSACTION.getTOMACHTYPE() + " Model Characteristic");
/*     */ 
/*     */         
/* 229 */         List<Map<String, String>> list = getFromModelToModel(this.MODEL_MACHTYPE, fCTRANSACTION.getTOMACHTYPE(), mODEL.getPDHDOMAIN(), this.m_db.getPDHConnection());
/* 230 */         String str9 = "";
/* 231 */         String str10 = "";
/* 232 */         for (Map<String, String> map : list) {
/* 233 */           str9 = (String)map.get("MODEL");
/* 234 */           str10 = CommonUtils.getFirstSubString((String)map.get("INVNAME"), 25) + " " + (String)map.get("MODEL");
/* 235 */           chwCharMaintain.addValue(str9, str10);
/*     */         } 
/* 237 */         runRfcCaller((RdhBase)chwCharMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 244 */         ChwClassMaintain chwClassMaintain = new ChwClassMaintain(str8, "MK_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD", "MK_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD");
/*     */ 
/*     */         
/* 247 */         chwClassMaintain.addCharacteristic("MK_T_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD");
/* 248 */         runRfcCaller((RdhBase)chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 254 */         RdhClassificationMaint rdhClassificationMaint1 = new RdhClassificationMaint(str7, "MK_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD", "300", "H", str8);
/*     */ 
/*     */ 
/*     */         
/* 258 */         runRfcCaller((RdhBase)rdhClassificationMaint1);
/*     */ 
/*     */         
/* 261 */         if (!"***".equals(fCTRANSACTION.getTOMODEL())) {
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
/* 277 */           ChwCharMaintain chwCharMaintain1 = new ChwCharMaintain(str8, "MK_D_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD_CONV", "CHAR", 9, str, str, str, str, "-", str, str, "X", fCTRANSACTION.getTOMACHTYPE() + " Features Conversion");
/*     */ 
/*     */           
/* 280 */           str9 = fCTRANSACTION.getFROMMODEL() + "_" + fCTRANSACTION.getTOMODEL();
/*     */           
/* 282 */           String str11 = "From " + fCTRANSACTION.getTOMACHTYPE() + " Model " + fCTRANSACTION.getFROMMODEL() + " to " + fCTRANSACTION.getTOMODEL();
/* 283 */           chwCharMaintain1.addValue(str9, str11);
/* 284 */           runRfcCaller((RdhBase)chwCharMaintain1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 292 */           chwClassMaintain = new ChwClassMaintain(str8, "MK_D_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD_CONV", "MK_D_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD_CONV");
/*     */ 
/*     */           
/* 295 */           chwClassMaintain.addCharacteristic("MK_D_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD_CONV");
/* 296 */           runRfcCaller((RdhBase)chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 302 */           rdhClassificationMaint1 = new RdhClassificationMaint(str7, "MK_D_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD_CONV", "300", "H", str8);
/*     */ 
/*     */ 
/*     */           
/* 306 */           runRfcCaller((RdhBase)rdhClassificationMaint1);
/*     */         } 
/*     */         
/* 309 */         RdhClassificationMaint rdhClassificationMaint2 = new RdhClassificationMaint(str7, "MK_REFERENCE", "300", "H", str8);
/* 310 */         runRfcCaller((RdhBase)rdhClassificationMaint2);
/*     */ 
/*     */         
/* 313 */         rdhClassificationMaint2 = new RdhClassificationMaint(str7, "MK_T_VAO_NEW", "300", "H", str8);
/* 314 */         runRfcCaller((RdhBase)rdhClassificationMaint2);
/*     */ 
/*     */         
/* 317 */         rdhClassificationMaint2 = new RdhClassificationMaint(str7, "MK_D_VAO_NEW", "300", "H", str8);
/* 318 */         runRfcCaller((RdhBase)rdhClassificationMaint2);
/*     */ 
/*     */         
/* 321 */         rdhClassificationMaint2 = new RdhClassificationMaint(str7, "MK_FC_EXCH", "300", "H", str8);
/* 322 */         runRfcCaller((RdhBase)rdhClassificationMaint2);
/*     */ 
/*     */         
/* 325 */         rdhClassificationMaint2 = new RdhClassificationMaint(str7, "MK_FC_CONV", "300", "H", str8);
/* 326 */         runRfcCaller((RdhBase)rdhClassificationMaint2);
/*     */         
/* 328 */         for (Map<String, String> map : list) {
/* 329 */           String str11 = fCTRANSACTION.getTOMACHTYPE() + "UPG";
/*     */           
/* 331 */           String str12 = "SC_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD_" + (String)map.get("MODEL");
/* 332 */           String str13 = "5";
/*     */           
/* 334 */           String str14 = "SC_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD_" + (String)map.get("MODEL");
/*     */           
/* 336 */           ChwDepdMaintain chwDepdMaintain = new ChwDepdMaintain(str8, str12, str13, str14);
/*     */           
/* 338 */           String str15 = "$PARENT.MK_T_" + fCTRANSACTION.getTOMACHTYPE() + "_MOD='" + (String)map.get("MODEL") + "'";
/* 339 */           chwDepdMaintain.addSourceLineCondition(str15);
/* 340 */           runRfcCaller((RdhBase)chwDepdMaintain);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 349 */         ChwConpMaintain chwConpMaintain = new ChwConpMaintain(str7, "INITIAL", "SD01", "2", fCTRANSACTION.getTOMACHTYPE() + "UPGUI", str8);
/*     */ 
/*     */         
/* 352 */         chwConpMaintain.addConfigDependency("E2E", str);
/*     */         
/* 354 */         chwConpMaintain.addConfigDependency("PR_" + fCTRANSACTION.getTOMACHTYPE() + "_SET_MODEL", str);
/*     */         
/* 356 */         chwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", str);
/*     */         
/* 358 */         chwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", str);
/*     */         
/* 360 */         chwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", str);
/* 361 */         runRfcCaller((RdhBase)chwConpMaintain);
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
/* 376 */         ChwFCTYMDMFCMaint chwFCTYMDMFCMaint = new ChwFCTYMDMFCMaint(fCTRANSACTION);
/* 377 */         runRfcCaller((RdhBase)chwFCTYMDMFCMaint);
/*     */ 
/*     */         
/* 380 */         if (fCTRANSACTION.getTOMACHTYPE().equals(fCTRANSACTION.getFROMMACHTYPE())) {
/* 381 */           List<MODEL> list1 = getMODEL(fCTRANSACTION.getTOMACHTYPE(), fCTRANSACTION.getPDHDOMAIN());
/* 382 */           Set<String> set = RFCConfig.getBHPlnts();
/* 383 */           addOutput("Start Bom Processing!");
/* 384 */           updateSalesBom(fCTRANSACTION, set, list1);
/* 385 */           addOutput("Bom Processing Finished!");
/*     */           
/* 387 */           UpdateParkStatus updateParkStatus1 = new UpdateParkStatus("MD_CHW_IERP", fCTRANSACTION.getTOMACHTYPE() + "BOMUPG");
/* 388 */           addDebug("Calling " + updateParkStatus1.getRFCName());
/*     */           try {
/* 390 */             updateParkStatus1.execute();
/* 391 */           } catch (Exception exception) {
/*     */             
/* 393 */             exception.printStackTrace();
/*     */           } 
/* 395 */           addDebug(updateParkStatus1.createLogEntry());
/* 396 */           if (updateParkStatus1.getRfcrc() == 0) {
/* 397 */             addOutput("Parking records updated successfully for ZDMRELNUM=" + fCTRANSACTION.getTOMACHTYPE() + "BOMUPG");
/*     */           } else {
/* 399 */             addOutput(updateParkStatus1.getRFCName() + " called faild!");
/* 400 */             addOutput(updateParkStatus1.getError_text());
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 405 */         UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", str8);
/* 406 */         addDebug("Calling " + updateParkStatus.getRFCName());
/* 407 */         updateParkStatus.execute();
/* 408 */         addDebug(updateParkStatus.createLogEntry());
/* 409 */         if (updateParkStatus.getRfcrc() == 0) {
/* 410 */           addOutput("Parking records updated successfully for ZDMRELNUM=" + str8);
/*     */         } else {
/* 412 */           addOutput(updateParkStatus.getRFCName() + " called faild!");
/* 413 */           addOutput(updateParkStatus.getError_text());
/*     */         } 
/*     */         
/* 416 */         updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", chwFCTYMDMFCMaint.getRFCNum());
/* 417 */         addDebug("Calling " + updateParkStatus.getRFCName());
/* 418 */         updateParkStatus.execute();
/* 419 */         addDebug(updateParkStatus.createLogEntry());
/* 420 */         if (updateParkStatus.getRfcrc() == 0) {
/* 421 */           addOutput("Parking records updated successfully for ZDMRELNUM=" + chwFCTYMDMFCMaint.getRFCNum());
/*     */         } else {
/* 423 */           addOutput(updateParkStatus.getRFCName() + " called faild!");
/* 424 */           addOutput(updateParkStatus.getError_text());
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 435 */     catch (Exception exception) {
/* 436 */       exception.printStackTrace();
/*     */       
/* 438 */       setReturnCode(-1);
/* 439 */       StringWriter stringWriter = new StringWriter();
/* 440 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 441 */       String str7 = "<pre>{0}</pre>";
/* 442 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 443 */       setReturnCode(-3);
/* 444 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 446 */       arrayOfString[0] = exception.getMessage();
/* 447 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/* 448 */       messageFormat = new MessageFormat(str7);
/* 449 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 450 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/* 451 */       logError("Exception: " + exception.getMessage());
/* 452 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 454 */       setCreateDGEntity(true);
/*     */     }
/*     */     finally {
/*     */       
/* 458 */       StringBuffer stringBuffer = new StringBuffer();
/* 459 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 460 */       arrayOfString[0] = this.m_prof.getOPName();
/* 461 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 462 */       arrayOfString[2] = this.m_prof.getWGName();
/* 463 */       arrayOfString[3] = getNow();
/* 464 */       arrayOfString[4] = stringBuffer.toString();
/* 465 */       arrayOfString[5] = str4 + " " + getABRVersion();
/* 466 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/* 467 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 469 */       println(EACustom.getDocTypeHtml());
/* 470 */       println(this.rptSb.toString());
/* 471 */       printDGSubmitString();
/*     */       
/* 473 */       println(EACustom.getTOUDiv());
/* 474 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<MODEL> getMODEL(String paramString1, String paramString2) {
/* 481 */     ArrayList<MODEL> arrayList = new ArrayList();
/*     */     
/*     */     try {
/* 484 */       String str = "SELECT distinct t2.ATTRIBUTEVALUE as MACHTYPEATR,substr(T1.ATTRIBUTEVALUE,1,3) as MODELATR FROM OPICM.flag F INNER JOIN OPICM.FLAG t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MACHTYPEATR' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.text t1 ON t1.ENTITYID =t2.ENTITYID AND t1.ENTITYTYPE =t2.ENTITYTYPE AND t1.ATTRIBUTECODE ='MODELATR' and T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP AND T1.NLSID=1 INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELIERPABRSTATUS') AND F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 490 */       Connection connection = this.m_db.getPDHConnection();
/* 491 */       PreparedStatement preparedStatement = connection.prepareStatement(str);
/* 492 */       preparedStatement.setString(1, paramString1);
/* 493 */       preparedStatement.setString(2, paramString2);
/* 494 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 495 */       addDebug("GetMODEL SQL:" + str);
/* 496 */       while (resultSet.next()) {
/* 497 */         MODEL mODEL = new MODEL();
/* 498 */         mODEL.setMACHTYPE(resultSet.getString("MACHTYPEATR"));
/* 499 */         mODEL.setMODEL(resultSet.getString("MODELATR"));
/* 500 */         arrayList.add(mODEL);
/*     */       }
/*     */     
/* 503 */     } catch (Exception exception) {
/*     */       
/* 505 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 508 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSalesBom(FCTRANSACTION paramFCTRANSACTION, Set<String> paramSet, List<MODEL> paramList) throws Exception {
/* 513 */     for (String str1 : paramSet) {
/*     */       
/* 515 */       ChwBomCreate chwBomCreate = new ChwBomCreate(paramFCTRANSACTION.getTOMACHTYPE() + "UPG", paramFCTRANSACTION.getTOMACHTYPE() + "BOMUPG", str1);
/* 516 */       addDebug("Calling ChwBomCreate");
/* 517 */       addDebug(chwBomCreate.generateJson());
/*     */       try {
/* 519 */         chwBomCreate.execute();
/* 520 */         addDebug(chwBomCreate.createLogEntry());
/* 521 */       } catch (Exception exception) {
/* 522 */         addOutput(exception.getMessage());
/*     */         
/*     */         continue;
/*     */       } 
/* 526 */       String str2 = "./locks/FCTRANSACTION" + paramFCTRANSACTION.getTOMACHTYPE() + "UPG" + str1 + ".lock";
/* 527 */       File file = new File(str2);
/* 528 */       (new File(file.getParent())).mkdirs();
/* 529 */       try(FileOutputStream null = new FileOutputStream(file); FileChannel null = fileOutputStream.getChannel()) {
/*     */         while (true) {
/*     */           try {
/* 532 */             FileLock fileLock = fileChannel.tryLock();
/* 533 */             if (fileLock != null) {
/* 534 */               addDebug("Start lock, lock file " + str2);
/*     */ 
/*     */               
/* 537 */               ChwReadSalesBom chwReadSalesBom = new ChwReadSalesBom(paramFCTRANSACTION.getTOMACHTYPE() + "UPG", str1);
/* 538 */               addDebug("Calling ChwReadSalesBom");
/* 539 */               addDebug(chwReadSalesBom.generateJson());
/*     */               try {
/* 541 */                 chwReadSalesBom.execute();
/* 542 */                 addDebug(chwReadSalesBom.createLogEntry());
/* 543 */               } catch (Exception exception) {
/* 544 */                 if (!exception.getMessage().contains("exists in Mast table but not defined to Stpo table")) {
/*     */ 
/*     */                   
/* 547 */                   addOutput(exception.getMessage());
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 551 */               addDebug("Bom Read result:" + chwReadSalesBom.getRETURN_MULTIPLE_OBJ().toString());
/* 552 */               List<HashMap<String, String>> list = (List)chwReadSalesBom.getRETURN_MULTIPLE_OBJ().get("stpo_api02");
/* 553 */               if (list != null && list.size() > 0) {
/* 554 */                 String str3 = getMaxItemNo(list);
/* 555 */                 for (MODEL mODEL : paramList) {
/* 556 */                   String str4 = mODEL.getMACHTYPE() + mODEL.getMODEL();
/* 557 */                   if (hasMatchComponent(list, str4)) {
/* 558 */                     addDebug("updateSalesBom exist component " + str4); continue;
/*     */                   } 
/* 560 */                   str3 = generateItemNumberString(str3);
/*     */                   
/* 562 */                   ChwBomMaintain chwBomMaintain = new ChwBomMaintain(mODEL.getMACHTYPE() + "UPG", mODEL.getMACHTYPE() + "BOMUPG", str1, mODEL.getMACHTYPE() + mODEL.getMODEL(), str3, "SC_" + mODEL.getMACHTYPE() + "_MOD_" + mODEL.getMODEL());
/* 563 */                   addDebug("Calling chwBomMaintain");
/* 564 */                   addDebug(chwBomMaintain.generateJson());
/*     */                   try {
/* 566 */                     chwBomMaintain.execute();
/* 567 */                     addDebug(chwBomMaintain.createLogEntry());
/* 568 */                   } catch (Exception exception) {
/* 569 */                     addOutput(exception.getMessage());
/* 570 */                     str3 = getMaxItemNo(list);
/*     */                   } 
/*     */                 } 
/*     */                 
/*     */                 break;
/*     */               } 
/* 576 */               String str = "0005";
/*     */               
/* 578 */               for (MODEL mODEL : paramList) {
/*     */                 
/* 580 */                 ChwBomMaintain chwBomMaintain = new ChwBomMaintain(mODEL.getMACHTYPE() + "UPG", mODEL.getMACHTYPE() + "BOMUPG", str1, mODEL.getMACHTYPE() + mODEL.getMODEL(), str, "SC_" + mODEL.getMACHTYPE() + "_MOD_" + mODEL.getMODEL());
/* 581 */                 addDebug("Calling chwBomMaintain");
/* 582 */                 addDebug(chwBomMaintain.generateJson());
/*     */                 try {
/* 584 */                   chwBomMaintain.execute();
/* 585 */                   addDebug(chwBomMaintain.createLogEntry());
/* 586 */                 } catch (Exception exception) {
/* 587 */                   addOutput(exception.getMessage());
/*     */                   continue;
/*     */                 } 
/* 590 */                 str = generateItemNumberString(str);
/*     */               } 
/*     */               
/*     */               break;
/*     */             } 
/*     */             
/* 596 */             addDebug("fileLock == null");
/* 597 */             Thread.sleep(5000L);
/*     */           }
/* 599 */           catch (OverlappingFileLockException overlappingFileLockException) {
/* 600 */             addDebug("other abr is running createSalesBOMforTypeUPG");
/* 601 */             Thread.sleep(5000L);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getMaxItemNo(List<HashMap<String, String>> paramList) {
/* 611 */     ArrayList<Integer> arrayList = new ArrayList(); int i;
/* 612 */     for (i = 0; i < paramList.size(); i++) {
/* 613 */       String str = (String)((HashMap)paramList.get(i)).get("ITEM_NO");
/* 614 */       arrayList.add(Integer.valueOf(Integer.parseInt(str)));
/*     */     } 
/* 616 */     i = ((Integer)Collections.<Integer>max(arrayList)).intValue();
/* 617 */     return String.format("%04d", new Object[] { Integer.valueOf(i) });
/*     */   }
/*     */   
/*     */   private String generateItemNumberString(String paramString) {
/* 621 */     int i = Integer.parseInt(paramString) + 5;
/* 622 */     return String.format("%04d", new Object[] { Integer.valueOf(i) });
/*     */   }
/*     */   
/*     */   private boolean hasMatchComponent(List<HashMap<String, String>> paramList, String paramString) {
/* 626 */     for (byte b = 0; b < paramList.size(); b++) {
/* 627 */       String str = (String)((HashMap)paramList.get(b)).get("COMPONENT");
/* 628 */       if (str.trim().equals(paramString)) {
/* 629 */         return true;
/*     */       }
/*     */     } 
/* 632 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 641 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 650 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 653 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 654 */     if (eANList == null) {
/* 655 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 656 */       eANList = entityGroup.getMetaAttribute();
/*     */       
/* 658 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 660 */     for (byte b = 0; b < eANList.size(); b++) {
/* 661 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 662 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 663 */       if (b + 1 < eANList.size()) {
/* 664 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 667 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String convertToHTML(String paramString) {
/* 678 */     String str = "";
/* 679 */     StringBuffer stringBuffer = new StringBuffer();
/* 680 */     StringCharacterIterator stringCharacterIterator = null;
/* 681 */     char c = ' ';
/* 682 */     if (paramString != null) {
/* 683 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 684 */       c = stringCharacterIterator.first();
/* 685 */       while (c != '￿') {
/*     */         
/* 687 */         switch (c) {
/*     */           
/*     */           case '<':
/* 690 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 693 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 698 */             stringBuffer.append("&quot;");
/*     */             break;
/*     */           
/*     */           case '\'':
/* 702 */             stringBuffer.append("&#" + c + ";");
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 711 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 714 */         c = stringCharacterIterator.next();
/*     */       } 
/* 716 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 719 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String convertToTag(String paramString) {
/* 730 */     String str = "";
/* 731 */     StringBuffer stringBuffer = new StringBuffer();
/* 732 */     StringCharacterIterator stringCharacterIterator = null;
/* 733 */     char c = ' ';
/* 734 */     if (paramString != null) {
/* 735 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 736 */       c = stringCharacterIterator.first();
/* 737 */       while (c != '￿') {
/*     */         
/* 739 */         switch (c) {
/*     */           
/*     */           case '<':
/* 742 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 745 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */           default:
/* 748 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 751 */         c = stringCharacterIterator.next();
/*     */       } 
/* 753 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 756 */     return str;
/*     */   }
/*     */   protected void addOutput(String paramString) {
/* 759 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   } protected void addMsg(StringBuffer paramStringBuffer) {
/* 761 */     this.rptSb.append(paramStringBuffer.toString() + NEWLINE);
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
/*     */   protected void addDebug(String paramString) {
/* 774 */     if (3 <= this.abr_debuglvl) {
/* 775 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 782 */     addOutput(paramString);
/* 783 */     setReturnCode(-1);
/*     */   }
/*     */   
/*     */   protected void runRfcCaller(RdhBase paramRdhBase) throws Exception {
/* 787 */     addDebug("Calling " + paramRdhBase.getRFCName());
/* 788 */     paramRdhBase.execute();
/* 789 */     addDebug(paramRdhBase.createLogEntry());
/* 790 */     if (paramRdhBase.getRfcrc() == 0) {
/* 791 */       addOutput(paramRdhBase.getRFCName() + " called successfully!");
/*     */     } else {
/* 793 */       addOutput(paramRdhBase.getRFCName() + " called  faild!");
/* 794 */       addOutput(paramRdhBase.getError_text());
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<Map<String, String>> getFromModelToModel(String paramString1, String paramString2, String paramString3, Connection paramConnection) throws SQLException {
/* 799 */     ArrayList<HashMap<Object, Object>> arrayList = new ArrayList();
/* 800 */     String[] arrayOfString = new String[2];
/* 801 */     arrayOfString[0] = paramString2;
/* 802 */     arrayOfString[1] = paramString3;
/* 803 */     String str = CommonUtils.getPreparedSQL(paramString1, (Object[])arrayOfString);
/* 804 */     addDebug("querySql=" + str);
/*     */     
/* 806 */     PreparedStatement preparedStatement = paramConnection.prepareStatement(paramString1);
/* 807 */     preparedStatement.setString(1, paramString2);
/* 808 */     preparedStatement.setString(2, paramString3);
/* 809 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 810 */     while (resultSet.next()) {
/* 811 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 812 */       hashMap.put("MODEL", resultSet.getString("MODEL"));
/* 813 */       hashMap.put("INVNAME", resultSet.getString("INVNAME"));
/* 814 */       arrayList.add(hashMap);
/*     */     } 
/* 816 */     return (List)arrayList;
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
/*     */   private String getModelFromXML(String paramString1, String paramString2, Connection paramConnection) throws SQLException {
/* 828 */     String str1 = "select XMLMESSAGE,XMLENTITYID from cache.XMLIDLCACHE  where XMLCACHEVALIDTO > current timestamp  and  XMLENTITYTYPE = 'MODEL' and xmlexists('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/MODEL_UPDATE\";  $i/MODEL_UPDATE[MACHTYPE/text() = \"" + paramString1 + "\" and MODEL/text() =\"" + paramString2 + "\"]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\") FETCH FIRST 1 ROWS ONLY with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 834 */     addDebug("cacheSql=" + str1);
/* 835 */     PreparedStatement preparedStatement = paramConnection.prepareStatement(str1);
/* 836 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 837 */     String str2 = "";
/* 838 */     String str3 = "";
/* 839 */     if (resultSet.next()) {
/* 840 */       str2 = resultSet.getString("XMLMESSAGE");
/* 841 */       str3 = resultSet.getString("XMLENTITYID");
/* 842 */       addDebug("getModelFromXML for MODEL TOMACHTYPE=" + paramString1 + " and TOMODEL=" + paramString2 + " and XMLENTITYID=" + str3);
/*     */     } else {
/* 844 */       String str = "select XMLMESSAGE,XMLENTITYID from cache.XMLIDLCACHE  where XMLCACHEVALIDTO > current timestamp  and  XMLENTITYTYPE = 'MODEL' and xmlexists('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/MODEL_UPDATE\";  $i/MODEL_UPDATE[MACHTYPE/text() = \"" + paramString1 + "\"]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\") FETCH FIRST 1 ROWS ONLY with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 850 */       PreparedStatement preparedStatement1 = paramConnection.prepareStatement(str);
/* 851 */       ResultSet resultSet1 = preparedStatement1.executeQuery();
/* 852 */       if (resultSet1.next()) {
/* 853 */         str2 = resultSet1.getString("XMLMESSAGE");
/* 854 */         str3 = resultSet1.getString("XMLENTITYID");
/* 855 */         addDebug("getModelFromXML for MODEL only TOMACHTYPE=" + paramString1 + " and XMLENTITYID=" + str3);
/*     */       } else {
/* 857 */         addDebug("getModelFromXML for MODEL no XML");
/*     */       } 
/*     */     } 
/*     */     
/* 861 */     return str2;
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
/*     */   private String getTMFFromXML(String paramString1, String paramString2, String paramString3, Connection paramConnection) throws SQLException {
/* 877 */     String str1 = "select XMLMESSAGE from cache.XMLIDLCACHE  where XMLCACHEVALIDTO > current timestamp  and  XMLENTITYTYPE = 'PRODSTRUCT' and xmlexists('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/TMF_UPDATE\";  $i/TMF_UPDATE[MACHTYPE/text() = \"" + paramString1 + "\" and MODEL/text() =\"" + paramString2 + "\" and FEATURECODE/text() =\"" + paramString3 + "\"]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\") FETCH FIRST 1 ROWS ONLY with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 883 */     PreparedStatement preparedStatement = paramConnection.prepareStatement(str1);
/* 884 */     addDebug("Search for TMF cacheSql=" + str1);
/* 885 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 886 */     String str2 = "";
/* 887 */     if (resultSet.next()) {
/* 888 */       str2 = resultSet.getString("XMLMESSAGE");
/* 889 */       addDebug("getTMFFromXML");
/*     */     } 
/* 891 */     return str2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean exist(String paramString1, String paramString2, String paramString3) {
/* 896 */     boolean bool = false;
/*     */     try {
/* 898 */       Connection connection = this.m_db.getPDHConnection();
/* 899 */       String[] arrayOfString = new String[2];
/* 900 */       arrayOfString[0] = paramString2;
/* 901 */       arrayOfString[1] = paramString3;
/* 902 */       String str = CommonUtils.getPreparedSQL(paramString1, (Object[])arrayOfString);
/* 903 */       addDebug("querySql=" + str);
/* 904 */       PreparedStatement preparedStatement = connection.prepareStatement(paramString1);
/* 905 */       preparedStatement.setString(1, paramString2);
/* 906 */       preparedStatement.setString(2, paramString3);
/*     */       
/* 908 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 909 */       if (resultSet.next()) {
/* 910 */         int i = resultSet.getInt(1);
/* 911 */         bool = (i > 0) ? true : false;
/*     */       } 
/* 913 */     } catch (SQLException sQLException) {
/* 914 */       sQLException.printStackTrace();
/* 915 */     } catch (MiddlewareException middlewareException) {
/* 916 */       middlewareException.printStackTrace();
/*     */     } 
/* 918 */     return bool;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\FCTRANSACTIONIERPABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */