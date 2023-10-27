/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwBomCreate;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwBomMaintain;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwModelConvertMtc;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwModelConvertUpg;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwReadSalesBom;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.MODEL;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.MODELCONVERT;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.MTCYMDMFCMaint;
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
/*     */ import java.util.Set;
/*     */ 
/*     */ public class MODELCONVERTIERPABRSTATUS
/*     */   extends PokBaseABR {
/*  46 */   private StringBuffer rptSb = new StringBuffer();
/*  47 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  48 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  49 */   private int abr_debuglvl = 0;
/*  50 */   private String navName = "";
/*  51 */   private Hashtable metaTbl = new Hashtable<>();
/*  52 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODELCONVERT' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*  53 */   String xml = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  59 */     return "PIABRSTATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/*  65 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  71 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/*  73 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     String str3 = "";
/*     */ 
/*     */ 
/*     */     
/*  83 */     String str4 = "";
/*  84 */     String str5 = "";
/*     */     
/*  86 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/*  89 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  90 */       arrayOfString[0] = getShortClassName(getClass());
/*  91 */       arrayOfString[1] = "ABR";
/*  92 */       str3 = messageFormat.format(arrayOfString);
/*  93 */       setDGTitle("FEATUREIERPABRSTATUS report");
/*  94 */       setDGString(getABRReturnCode());
/*  95 */       setDGRptName("FEATUREIERPABRSTATUS");
/*  96 */       setDGRptClass("FEATUREIERPABRSTATUS");
/*     */       
/*  98 */       setReturnCode(0);
/*     */       
/* 100 */       start_ABRBuild(false);
/*     */       
/* 102 */       this
/* 103 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/* 107 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/* 109 */               getEntityType(), getEntityID()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 117 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*     */       
/* 119 */       this.navName = getNavigationName();
/* 120 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/* 121 */       addDebug("navName=" + this.navName);
/* 122 */       addDebug("rootDesc" + str5);
/*     */ 
/*     */       
/* 125 */       Connection connection = this.m_db.getODSConnection();
/* 126 */       PreparedStatement preparedStatement = connection.prepareStatement(this.CACEHSQL);
/* 127 */       preparedStatement.setInt(1, entityItem.getEntityID());
/* 128 */       ResultSet resultSet = preparedStatement.executeQuery();
/*     */       
/* 130 */       while (resultSet.next()) {
/* 131 */         this.xml = resultSet.getString("XMLMESSAGE");
/*     */       }
/* 133 */       if (this.xml != null) {
/*     */         
/* 135 */         MODELCONVERT mODELCONVERT = (MODELCONVERT)XMLParse.getObjectFromXml(this.xml, MODELCONVERT.class);
/* 136 */         String str6 = getModelFromXML(mODELCONVERT.getTOMACHTYPE(), mODELCONVERT.getTOMODEL(), connection);
/*     */         
/* 138 */         if (str6 == null) {
/* 139 */           addOutput("MODEL xml not found in cache fro MODEL:" + mODELCONVERT.getTOMODEL() + " MACHTYPE:" + mODELCONVERT.getTOMACHTYPE());
/*     */           return;
/*     */         } 
/* 142 */         MODEL mODEL = (MODEL)XMLParse.getObjectFromXml(str6, MODEL.class);
/*     */         
/* 144 */         if (mODEL == null) {
/* 145 */           addOutput("Not find the Model with the modelxml");
/*     */           return;
/*     */         } 
/* 148 */         String str7 = mODELCONVERT.getFROMMACHTYPE().equals(mODELCONVERT.getTOMACHTYPE()) ? "UPG" : "MTC";
/* 149 */         if (mODELCONVERT.getFROMMACHTYPE().equals(mODELCONVERT.getTOMACHTYPE())) {
/* 150 */           ChwModelConvertUpg chwModelConvertUpg = new ChwModelConvertUpg(mODEL, mODELCONVERT, this.m_db.getPDHConnection(), connection);
/*     */           try {
/* 152 */             chwModelConvertUpg.execute();
/* 153 */             addOutput(chwModelConvertUpg.getRptSb().toString());
/* 154 */           } catch (Exception exception) {
/*     */             
/* 156 */             addError(chwModelConvertUpg.getRptSb().toString());
/* 157 */             throw exception;
/*     */           } 
/*     */         } else {
/* 160 */           ChwModelConvertMtc chwModelConvertMtc = new ChwModelConvertMtc(mODEL, mODELCONVERT, this.m_db.getPDHConnection(), connection);
/*     */           try {
/* 162 */             chwModelConvertMtc.execute();
/* 163 */             addOutput(chwModelConvertMtc.getRptSb().toString());
/* 164 */           } catch (Exception exception) {
/*     */             
/* 166 */             addError(chwModelConvertMtc.getRptSb().toString());
/* 167 */             throw exception;
/*     */           } 
/*     */         } 
/* 170 */         MTCYMDMFCMaint mTCYMDMFCMaint = new MTCYMDMFCMaint(mODELCONVERT);
/*     */         
/* 172 */         addDebug("Calling " + mTCYMDMFCMaint.getRFCName());
/* 173 */         if (mTCYMDMFCMaint.getTbl_model().size() > 0) {
/* 174 */           mTCYMDMFCMaint.execute();
/* 175 */           addDebug(mTCYMDMFCMaint.createLogEntry());
/* 176 */           if (mTCYMDMFCMaint.getRfcrc() == 0) {
/* 177 */             addOutput(mTCYMDMFCMaint.getRFCName() + " called successfully!");
/*     */           } else {
/* 179 */             addOutput(mTCYMDMFCMaint.getRFCName() + " called  faild!");
/* 180 */             addOutput(mTCYMDMFCMaint.getError_text());
/*     */           } 
/*     */         } else {
/* 183 */           addOutput("No Tbl_model in the MTCYMDMFCMaint, will not call the RFC");
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
/*     */ 
/*     */ 
/*     */         
/* 202 */         String str8 = mODELCONVERT.getFROMMACHTYPE().equals(mODELCONVERT.getTOMACHTYPE()) ? "BOMUPG" : "BOMMTC";
/* 203 */         List<MODEL> list = getMODEL(mODELCONVERT.getTOMACHTYPE(), mODELCONVERT.getPDHDOMAIN());
/* 204 */         Set<String> set = RFCConfig.getBHPlnts();
/* 205 */         addOutput("Start Bom Processing!");
/* 206 */         updateSalesBom(mODELCONVERT, str7, str8, set, list);
/* 207 */         addOutput("Bom Processing Finished!");
/* 208 */         UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", mODELCONVERT.getTOMACHTYPE() + str8);
/* 209 */         addDebug("Calling " + updateParkStatus.getRFCName());
/*     */         try {
/* 211 */           updateParkStatus.execute();
/* 212 */         } catch (Exception exception) {
/*     */           
/* 214 */           exception.printStackTrace();
/*     */         } 
/* 216 */         addDebug(updateParkStatus.createLogEntry());
/* 217 */         if (updateParkStatus.getRfcrc() == 0) {
/* 218 */           addOutput("Parking records updated successfully for ZDMRELNUM=" + mODELCONVERT.getTOMACHTYPE() + str8);
/*     */         } else {
/* 220 */           addOutput(updateParkStatus.getRFCName() + " called faild!");
/* 221 */           addOutput(updateParkStatus.getError_text());
/*     */         } 
/*     */         
/* 224 */         String str9 = mODELCONVERT.getFROMMACHTYPE() + mODELCONVERT.getFROMMODEL() + mODELCONVERT.getTOMACHTYPE() + mODELCONVERT.getTOMODEL();
/* 225 */         updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", str9);
/* 226 */         addDebug("Calling " + updateParkStatus.getRFCName());
/* 227 */         updateParkStatus.execute();
/* 228 */         addDebug(updateParkStatus.createLogEntry());
/* 229 */         if (updateParkStatus.getRfcrc() == 0) {
/* 230 */           addOutput("Parking records updated successfully for ZDMRELNUM=" + str9);
/*     */         } else {
/* 232 */           addOutput(updateParkStatus.getRFCName() + " called faild!");
/* 233 */           addOutput(updateParkStatus.getError_text());
/*     */         } 
/*     */       } else {
/* 236 */         addOutput("XML file not exeit in cache,RFC caller not called!");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 248 */     catch (Exception exception) {
/*     */       
/* 250 */       exception.printStackTrace();
/*     */       
/* 252 */       setReturnCode(-1);
/* 253 */       StringWriter stringWriter = new StringWriter();
/* 254 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 255 */       String str7 = "<pre>{0}</pre>";
/* 256 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 257 */       setReturnCode(-3);
/* 258 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 260 */       arrayOfString[0] = exception.getMessage();
/* 261 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 262 */       messageFormat = new MessageFormat(str7);
/* 263 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 264 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 265 */       logError("Exception: " + exception.getMessage());
/* 266 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 268 */       setCreateDGEntity(true);
/*     */     }
/*     */     finally {
/*     */       
/* 272 */       StringBuffer stringBuffer = new StringBuffer();
/* 273 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 274 */       arrayOfString[0] = this.m_prof.getOPName();
/* 275 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 276 */       arrayOfString[2] = this.m_prof.getWGName();
/* 277 */       arrayOfString[3] = getNow();
/* 278 */       arrayOfString[4] = stringBuffer.toString();
/* 279 */       arrayOfString[5] = str4 + " " + getABRVersion();
/* 280 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/* 281 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 283 */       println(EACustom.getDocTypeHtml());
/* 284 */       println(this.rptSb.toString());
/* 285 */       printDGSubmitString();
/*     */       
/* 287 */       println(EACustom.getTOUDiv());
/* 288 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<MODEL> getMODEL(String paramString1, String paramString2) throws Exception {
/* 294 */     ArrayList<MODEL> arrayList = new ArrayList();
/*     */     
/* 296 */     String str = "SELECT distinct t2.ATTRIBUTEVALUE as MACHTYPEATR,substr(T1.ATTRIBUTEVALUE,1,3) as MODELATR FROM OPICM.flag F INNER JOIN OPICM.FLAG t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MACHTYPEATR' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.text t1 ON t1.ENTITYID =t2.ENTITYID AND t1.ENTITYTYPE =t2.ENTITYTYPE AND t1.ATTRIBUTECODE ='MODELATR' and T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP AND T1.NLSID=1 INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELIERPABRSTATUS') AND F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=? WITH UR";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     Connection connection = this.m_db.getPDHConnection();
/* 303 */     PreparedStatement preparedStatement = connection.prepareStatement(str);
/* 304 */     preparedStatement.setString(1, paramString1);
/* 305 */     preparedStatement.setString(2, paramString2);
/* 306 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 307 */     while (resultSet.next()) {
/* 308 */       MODEL mODEL = new MODEL();
/* 309 */       mODEL.setMACHTYPE(resultSet.getString("MACHTYPEATR"));
/* 310 */       mODEL.setMODEL(resultSet.getString("MODELATR"));
/* 311 */       arrayList.add(mODEL);
/*     */     } 
/*     */     
/* 314 */     return arrayList;
/*     */   }
/*     */   
/*     */   public void updateSalesBom(MODELCONVERT paramMODELCONVERT, String paramString1, String paramString2, Set<String> paramSet, List<MODEL> paramList) throws Exception {
/* 318 */     for (String str1 : paramSet) {
/*     */       
/* 320 */       ChwBomCreate chwBomCreate = new ChwBomCreate(paramMODELCONVERT.getTOMACHTYPE() + paramString1, paramMODELCONVERT.getTOMACHTYPE() + paramString2, str1);
/* 321 */       addDebug("Calling ChwBomCreate");
/* 322 */       addDebug(chwBomCreate.generateJson());
/*     */       try {
/* 324 */         chwBomCreate.execute();
/* 325 */         addDebug(chwBomCreate.createLogEntry());
/* 326 */       } catch (Exception exception) {
/* 327 */         addOutput(exception.getMessage());
/*     */         
/*     */         continue;
/*     */       } 
/* 331 */       String str2 = "./locks/MODELCONVERT" + paramMODELCONVERT.getTOMACHTYPE() + paramString1 + str1 + ".lock";
/* 332 */       File file = new File(str2);
/* 333 */       (new File(file.getParent())).mkdirs();
/* 334 */       try(FileOutputStream null = new FileOutputStream(file); FileChannel null = fileOutputStream.getChannel()) {
/*     */         while (true) {
/*     */           try {
/* 337 */             FileLock fileLock = fileChannel.tryLock();
/* 338 */             if (fileLock != null) {
/* 339 */               addDebug("Start lock, lock file " + str2);
/*     */ 
/*     */               
/* 342 */               ChwReadSalesBom chwReadSalesBom = new ChwReadSalesBom(paramMODELCONVERT.getTOMACHTYPE() + paramString1, str1);
/* 343 */               addDebug("Calling ChwReadSalesBom");
/* 344 */               addDebug(chwReadSalesBom.generateJson());
/*     */               try {
/* 346 */                 chwReadSalesBom.execute();
/* 347 */                 addDebug(chwReadSalesBom.createLogEntry());
/* 348 */               } catch (Exception exception) {
/* 349 */                 if (!exception.getMessage().contains("exists in Mast table but not defined to Stpo table")) {
/*     */ 
/*     */                   
/* 352 */                   addOutput(exception.getMessage());
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 356 */               addDebug("Bom Read result:" + chwReadSalesBom.getRETURN_MULTIPLE_OBJ().toString());
/* 357 */               List<HashMap<String, String>> list = (List)chwReadSalesBom.getRETURN_MULTIPLE_OBJ().get("stpo_api02");
/* 358 */               if (list != null && list.size() > 0) {
/* 359 */                 String str3 = getMaxItemNo(list);
/* 360 */                 for (MODEL mODEL : paramList) {
/* 361 */                   String str4 = mODEL.getMACHTYPE() + mODEL.getMODEL();
/* 362 */                   if (hasMatchComponent(list, str4)) {
/* 363 */                     addDebug("updateSalesBom exist component " + str4); continue;
/*     */                   } 
/* 365 */                   str3 = generateItemNumberString(str3);
/*     */                   
/* 367 */                   ChwBomMaintain chwBomMaintain = new ChwBomMaintain(mODEL.getMACHTYPE() + paramString1, paramMODELCONVERT.getTOMACHTYPE() + paramString2, str1, mODEL.getMACHTYPE() + mODEL.getMODEL(), str3, "SC_" + mODEL.getMACHTYPE() + "_MOD_" + mODEL.getMODEL());
/* 368 */                   addDebug("Calling chwBomMaintain");
/* 369 */                   addDebug(chwBomMaintain.generateJson());
/*     */                   try {
/* 371 */                     chwBomMaintain.execute();
/* 372 */                     addDebug(chwBomMaintain.createLogEntry());
/* 373 */                   } catch (Exception exception) {
/* 374 */                     addOutput(exception.getMessage());
/* 375 */                     str3 = getMaxItemNo(list);
/*     */                   } 
/*     */                 } 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 382 */               String str = "0005";
/*     */               
/* 384 */               for (MODEL mODEL : paramList) {
/*     */                 
/* 386 */                 ChwBomMaintain chwBomMaintain = new ChwBomMaintain(mODEL.getMACHTYPE() + paramString1, paramMODELCONVERT.getTOMACHTYPE() + paramString2, str1, mODEL.getMACHTYPE() + mODEL.getMODEL(), str, "SC_" + mODEL.getMACHTYPE() + "_MOD_" + mODEL.getMODEL());
/* 387 */                 addDebug("Calling chwBomMaintain");
/* 388 */                 addDebug(chwBomMaintain.generateJson());
/*     */                 try {
/* 390 */                   chwBomMaintain.execute();
/* 391 */                   addDebug(chwBomMaintain.createLogEntry());
/* 392 */                 } catch (Exception exception) {
/* 393 */                   addOutput(exception.getMessage());
/*     */                   continue;
/*     */                 } 
/* 396 */                 str = generateItemNumberString(str);
/*     */               } 
/*     */               
/*     */               break;
/*     */             } 
/*     */             
/* 402 */             addDebug("fileLock == null");
/* 403 */             Thread.sleep(5000L);
/*     */           }
/* 405 */           catch (OverlappingFileLockException overlappingFileLockException) {
/* 406 */             addDebug("other abr is running createSalesBOMforType" + paramString1);
/* 407 */             Thread.sleep(5000L);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getMaxItemNo(List<HashMap<String, String>> paramList) {
/* 416 */     ArrayList<Integer> arrayList = new ArrayList(); int i;
/* 417 */     for (i = 0; i < paramList.size(); i++) {
/* 418 */       String str = (String)((HashMap)paramList.get(i)).get("ITEM_NO");
/* 419 */       arrayList.add(Integer.valueOf(Integer.parseInt(str)));
/*     */     } 
/* 421 */     i = ((Integer)Collections.<Integer>max(arrayList)).intValue();
/* 422 */     return String.format("%04d", new Object[] { Integer.valueOf(i) });
/*     */   }
/*     */   
/*     */   private String generateItemNumberString(String paramString) {
/* 426 */     int i = Integer.parseInt(paramString) + 5;
/* 427 */     return String.format("%04d", new Object[] { Integer.valueOf(i) });
/*     */   }
/*     */   
/*     */   private boolean hasMatchComponent(List<HashMap<String, String>> paramList, String paramString) {
/* 431 */     for (byte b = 0; b < paramList.size(); b++) {
/* 432 */       String str = (String)((HashMap)paramList.get(b)).get("COMPONENT");
/* 433 */       if (str.trim().equals(paramString)) {
/* 434 */         return true;
/*     */       }
/*     */     } 
/* 437 */     return false;
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
/* 449 */     String str1 = "select XMLMESSAGE from cache.XMLIDLCACHE  where XMLCACHEVALIDTO > current timestamp  and  XMLENTITYTYPE = 'MODEL' and xmlexists('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/MODEL_UPDATE\";  $i/MODEL_UPDATE[MACHTYPE/text() = \"" + paramString1 + "\" and MODEL/text() =\"" + paramString2 + "\"]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\") FETCH FIRST 1 ROWS ONLY with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 455 */     addDebug("getModelFromXML cacheSql" + str1);
/* 456 */     PreparedStatement preparedStatement = paramConnection.prepareStatement(str1);
/* 457 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 458 */     String str2 = "";
/* 459 */     if (resultSet.next()) {
/* 460 */       str2 = resultSet.getString("XMLMESSAGE");
/* 461 */       addDebug("getModelFromXML xml");
/*     */     } 
/* 463 */     return str2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 472 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 481 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 484 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 485 */     if (eANList == null) {
/* 486 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 487 */       eANList = entityGroup.getMetaAttribute();
/*     */       
/* 489 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 491 */     for (byte b = 0; b < eANList.size(); b++) {
/* 492 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 493 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 494 */       if (b + 1 < eANList.size()) {
/* 495 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 498 */     return stringBuffer.toString();
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
/*     */   protected static String convertToHTML(String paramString) {
/* 510 */     String str = "";
/* 511 */     StringBuffer stringBuffer = new StringBuffer();
/* 512 */     StringCharacterIterator stringCharacterIterator = null;
/* 513 */     char c = ' ';
/* 514 */     if (paramString != null) {
/* 515 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 516 */       c = stringCharacterIterator.first();
/* 517 */       while (c != '￿') {
/*     */         
/* 519 */         switch (c) {
/*     */           
/*     */           case '<':
/* 522 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 525 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 530 */             stringBuffer.append("&quot;");
/*     */             break;
/*     */           
/*     */           case '\'':
/* 534 */             stringBuffer.append("&#" + c + ";");
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 543 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 546 */         c = stringCharacterIterator.next();
/*     */       } 
/* 548 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 551 */     return str;
/*     */   }
/*     */   protected void addOutput(String paramString) {
/* 554 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
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
/* 567 */     if (3 <= this.abr_debuglvl) {
/* 568 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 575 */     addOutput(paramString);
/* 576 */     setReturnCode(-1);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\MODELCONVERTIERPABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */