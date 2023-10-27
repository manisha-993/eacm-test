/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhClassificationMaint;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhMatmCreate;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhTssFcProd;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhTssMatChar;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.SVCMOD;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.UpdateParkStatus;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.XMLParse;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.StringCharacterIterator;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class SVCMODIERPABRSTATUS
/*     */   extends PokBaseABR {
/*  32 */   private StringBuffer rptSb = new StringBuffer();
/*  33 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  34 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  35 */   private int abr_debuglvl = 0;
/*  36 */   private String navName = "";
/*  37 */   private Hashtable metaTbl = new Hashtable<>();
/*  38 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'SVCMOD' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*  39 */   String xml = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  45 */     return "PIABRSTATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/*  51 */     return "1.0";
/*     */   } public void execute_run() {
/*     */     MessageFormat messageFormat;
/*     */     StringBuffer sb;
/*  55 */     String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + 
/*  56 */       EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + 
/*  57 */       EACustom.getMastheadDiv() + NEWLINE + 
/*  58 */       "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*  59 */     String HEADER2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + 
/*  60 */       "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + 
/*  61 */       NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + 
/*  62 */       "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + 
/*  63 */       NEWLINE;
/*     */     
/*  65 */     String header1 = "";
/*     */ 
/*     */ 
/*     */     
/*  69 */     String abrversion = "";
/*  70 */     String rootDesc = "";
/*     */     
/*  72 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/*  75 */       MessageFormat msgf = new MessageFormat(HEADER);
/*  76 */       arrayOfString[0] = getShortClassName(getClass());
/*  77 */       arrayOfString[1] = "ABR";
/*  78 */       header1 = msgf.format(arrayOfString);
/*  79 */       setDGTitle("SVCMODIERPABRSTATUS report");
/*  80 */       setDGString(getABRReturnCode());
/*  81 */       setDGRptName("SVCMODIERPABRSTATUS");
/*  82 */       setDGRptClass("SVCMODIERPABRSTATUS");
/*     */       
/*  84 */       setReturnCode(0);
/*     */       
/*  86 */       start_ABRBuild(false);
/*     */       
/*  88 */       this.abr_debuglvl = 
/*  89 */         ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/*  93 */       this.m_elist = this.m_db.getEntityList(this.m_prof, 
/*  94 */           new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), 
/*  95 */           new EntityItem[] { new EntityItem(null, this.m_prof, getEntityType(), getEntityID()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       EntityItem rootEntity = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 103 */       addDebug("*****mlm rootEntity = " + rootEntity.getEntityType() + rootEntity.getEntityID());
/*     */       
/* 105 */       this.navName = getNavigationName();
/* 106 */       rootDesc = this.m_elist.getParentEntityGroup().getLongDescription();
/* 107 */       addDebug("navName=" + this.navName);
/* 108 */       addDebug("rootDesc" + rootDesc);
/*     */ 
/*     */       
/* 111 */       Connection connection = this.m_db.getODSConnection();
/* 112 */       PreparedStatement statement = connection.prepareStatement(this.CACEHSQL);
/* 113 */       statement.setInt(1, rootEntity.getEntityID());
/* 114 */       ResultSet resultSet = statement.executeQuery();
/*     */       
/* 116 */       while (resultSet.next()) {
/* 117 */         this.xml = resultSet.getString("XMLMESSAGE");
/*     */       }
/* 119 */       if (this.xml != null) {
/* 120 */         String mulcompindc = PokUtils.getAttributeValue(rootEntity, "MULCOMPINDC", "", "");
/* 121 */         SVCMOD svcmod = XMLParse.getSvcmodFromXml(this.xml);
/* 122 */         RdhMatmCreate create = new RdhMatmCreate(svcmod, mulcompindc);
/* 123 */         addDebug("Calling " + create.getRFCName());
/* 124 */         create.execute();
/* 125 */         addDebug(create.createLogEntry());
/* 126 */         if (create.getRfcrc() == 0) {
/* 127 */           addOutput(String.valueOf(create.getRFCName()) + " called  successfully!");
/*     */         } else {
/* 129 */           addOutput(String.valueOf(create.getRFCName()) + " called  faild!");
/* 130 */           addOutput(create.getError_text());
/*     */         } 
/*     */         
/* 133 */         String obj_id = String.valueOf(svcmod.getMACHTYPE()) + svcmod.getMODEL();
/* 134 */         String class_name = "MG_COMMON";
/* 135 */         String class_type = "001";
/* 136 */         RdhClassificationMaint cMaint = new RdhClassificationMaint(obj_id, class_name, class_type, obj_id);
/*     */         
/* 138 */         addDebug("Calling " + cMaint.getRFCName() + " ID=" + obj_id + " NAME=" + class_name + " type=" + class_type);
/*     */         
/* 140 */         String type = "MG_PRODUCTTYPE";
/* 141 */         String tableData = getTableMapingDate(type, svcmod);
/* 142 */         addDebug("addCharacteristic for type=" + type + " value=" + tableData);
/* 143 */         if (tableData != null && !"No characteristic".equals(tableData)) {
/* 144 */           addDebug("addCharacteristic for type=" + type + " value=" + tableData);
/* 145 */           cMaint.addCharacteristic(type, tableData);
/*     */         } else {
/* 147 */           addDebug("addCharacteristic for type=" + type + " value=");
/* 148 */           cMaint.addCharacteristic(type, "");
/*     */         } 
/* 150 */         cMaint.execute();
/* 151 */         addDebug(cMaint.createLogEntry());
/* 152 */         if (cMaint.getRfcrc() == 0) {
/* 153 */           addOutput(String.valueOf(cMaint.getRFCName()) + " called successfully!");
/*     */         } else {
/* 155 */           addOutput(String.valueOf(cMaint.getRFCName()) + " called  faild!");
/* 156 */           addOutput(cMaint.getError_text());
/*     */         } 
/* 158 */         class_name = "MM_CUSTOM_SERVICES";
/* 159 */         cMaint = new RdhClassificationMaint(obj_id, class_name, class_type, obj_id);
/* 160 */         type = "MM_CUSTOM_TYPE";
/* 161 */         tableData = getTableMapingDate(type, svcmod);
/* 162 */         if (tableData != null && !"No characteristic".equals(tableData)) {
/* 163 */           addDebug("addCharacteristic for type=" + type + " value=" + tableData);
/* 164 */           cMaint.addCharacteristic(type, tableData);
/*     */         } else {
/* 166 */           addDebug("addCharacteristic for type=" + type + " value=");
/* 167 */           cMaint.addCharacteristic(type, "");
/*     */         } 
/* 169 */         type = "MM_CUSTOM_COSTING";
/* 170 */         tableData = getTableMapingDate(type, svcmod);
/*     */         
/* 172 */         if (tableData != null && !"No characteristic".equals(tableData)) {
/* 173 */           addDebug("addCharacteristic for type=" + type + " value=" + tableData);
/* 174 */           cMaint.addCharacteristic(type, tableData);
/*     */         } else {
/* 176 */           addDebug("addCharacteristic for type=" + type + " value=");
/* 177 */           cMaint.addCharacteristic(type, "");
/*     */         } 
/* 179 */         type = "MM_PROFIT_CENTER";
/* 180 */         tableData = getTableMapingDate(type, svcmod);
/* 181 */         if (tableData != null && !"No characteristic".equals(tableData)) {
/* 182 */           addDebug("addCharacteristic for type=" + type + " value=" + tableData);
/* 183 */           cMaint.addCharacteristic(type, tableData);
/*     */         } else {
/* 185 */           addDebug("addCharacteristic for type=" + type + " value=");
/* 186 */           cMaint.addCharacteristic(type, "");
/*     */         } 
/* 188 */         type = "MM_TAX_CATEGORY";
/* 189 */         tableData = getTableMapingDate(type, svcmod);
/*     */         
/* 191 */         if (tableData != null && !"No characteristic".equals(tableData)) {
/* 192 */           addDebug("addCharacteristic for type=" + type + " value=" + tableData);
/* 193 */           cMaint.addCharacteristic(type, tableData);
/*     */         } else {
/* 195 */           addDebug("addCharacteristic for type=" + type + " value=");
/* 196 */           cMaint.addCharacteristic(type, "");
/*     */         } 
/* 198 */         cMaint.execute();
/* 199 */         addDebug(cMaint.createLogEntry());
/* 200 */         if (cMaint.getRfcrc() == 0) {
/* 201 */           addOutput(String.valueOf(cMaint.getRFCName()) + " called successfully!");
/*     */         } else {
/* 203 */           addOutput(String.valueOf(cMaint.getRFCName()) + " called  faild!");
/* 204 */           addOutput(cMaint.getError_text());
/*     */         } 
/*     */ 
/*     */         
/* 208 */         cMaint.execute(); addDebug(cMaint.createLogEntry());
/* 209 */         if (cMaint.getRfcrc() == 0) { addOutput(String.valueOf(cMaint.getRFCName()) + 
/* 210 */               " called successfully!"); } else { addOutput(String.valueOf(cMaint.getRFCName()) + 
/* 211 */               " called  faild!"); addOutput(cMaint.getError_text()); }
/*     */         
/* 213 */         class_name = "MM_FIELDS";
/* 214 */         cMaint = new RdhClassificationMaint(obj_id, class_name, class_type, obj_id);
/*     */         
/* 216 */         if ("Yes".equals(svcmod.getSOPRELEVANT())) {
/* 217 */           type = "MM_SOP_IND";
/* 218 */           addDebug("addCharacteristic for type=" + type + " value=" + "X");
/* 219 */           cMaint.addCharacteristic(type, "X");
/*     */         } 
/* 221 */         type = "MM_TASK_TYPE";
/* 222 */         addDebug("addCharacteristic for type=" + type + " value=" + svcmod.getSOPTASKTYPE());
/* 223 */         cMaint.addCharacteristic(type, svcmod.getSOPTASKTYPE());
/* 224 */         type = "MM_OPPORTUNITY_CODE";
/* 225 */         addDebug("addCharacteristic for type=" + type + " value=" + svcmod.getWWOCCODE());
/* 226 */         cMaint.addCharacteristic(type, svcmod.getWWOCCODE());
/* 227 */         cMaint.execute();
/* 228 */         addDebug(cMaint.createLogEntry());
/*     */         
/* 230 */         if (cMaint.getRfcrc() == 0) {
/* 231 */           addOutput(String.valueOf(cMaint.getRFCName()) + " called successfully!");
/* 232 */           addOutput(cMaint.getError_text());
/*     */         } else {
/* 234 */           addOutput(String.valueOf(cMaint.getRFCName()) + " called  faild!");
/* 235 */           addOutput(cMaint.getError_text());
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 244 */         if (svcmod.hasProds()) {
/* 245 */           RdhTssMatChar chart = new RdhTssMatChar(svcmod);
/* 246 */           addDebug("Calling " + chart.getRFCName());
/* 247 */           chart.execute();
/* 248 */           addDebug(chart.createLogEntry());
/* 249 */           if (chart.getRfcrc() == 0) {
/* 250 */             addOutput(String.valueOf(chart.getRFCName()) + " called successfully!");
/* 251 */             addOutput(chart.getError_text());
/*     */           } else {
/* 253 */             addOutput(String.valueOf(chart.getRFCName()) + " called  faild!");
/* 254 */             addOutput(chart.getError_text());
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 259 */         UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_TSS_IERP", String.valueOf(svcmod.getMACHTYPE()) + svcmod.getMODEL());
/* 260 */         addDebug("Calling " + updateParkStatus.getRFCName());
/* 261 */         updateParkStatus.execute();
/* 262 */         addDebug(updateParkStatus.createLogEntry());
/*     */         
/* 264 */         if (updateParkStatus.getRfcrc() == 0) {
/* 265 */           addOutput(String.valueOf(updateParkStatus.getRFCName()) + " called successfully!");
/* 266 */           addOutput(updateParkStatus.getError_text());
/*     */         } else {
/* 268 */           addOutput(String.valueOf(updateParkStatus.getRFCName()) + " called  faild!");
/* 269 */           addOutput(updateParkStatus.getError_text());
/*     */         } 
/* 271 */         addDebug("Check if RdhTssFcProd can run:");
/*     */ 
/*     */         
/* 274 */         RdhTssFcProd rdhTssFcProd = new RdhTssFcProd(svcmod);
/* 275 */         addDebug("Can run:" + rdhTssFcProd.canRun());
/* 276 */         if (rdhTssFcProd.canRun()) {
/* 277 */           addDebug("Calling " + rdhTssFcProd.getRFCName());
/* 278 */           rdhTssFcProd.execute();
/* 279 */           addDebug(rdhTssFcProd.createLogEntry());
/*     */           
/* 281 */           if (rdhTssFcProd.getRfcrc() == 0) {
/* 282 */             addOutput(String.valueOf(rdhTssFcProd.getRFCName()) + " called successfully!");
/* 283 */             addOutput(rdhTssFcProd.getError_text());
/*     */           } else {
/* 285 */             addOutput(String.valueOf(rdhTssFcProd.getRFCName()) + " called  faild!");
/* 286 */             addOutput(rdhTssFcProd.getError_text());
/*     */           } 
/*     */         } else {
/* 289 */           addDebug("skip " + rdhTssFcProd.getRFCName());
/*     */         } 
/*     */       } else {
/*     */         
/* 293 */         addOutput("XML file not exist in cache,RFC caller not called!");
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
/* 305 */     catch (Exception e) {
/*     */       
/* 307 */       e.printStackTrace();
/*     */       
/* 309 */       setReturnCode(-1);
/* 310 */       StringWriter exBuf = new StringWriter();
/* 311 */       String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 312 */       String Error_STACKTRACE = "<pre>{0}</pre>";
/* 313 */       MessageFormat msgf = new MessageFormat(Error_EXCEPTION);
/* 314 */       setReturnCode(-3);
/* 315 */       e.printStackTrace(new PrintWriter(exBuf));
/*     */       
/* 317 */       arrayOfString[0] = e.getMessage();
/* 318 */       this.rptSb.append(String.valueOf(msgf.format(arrayOfString)) + NEWLINE);
/* 319 */       msgf = new MessageFormat(Error_STACKTRACE);
/* 320 */       arrayOfString[0] = exBuf.getBuffer().toString();
/* 321 */       this.rptSb.append(String.valueOf(msgf.format(arrayOfString)) + NEWLINE);
/* 322 */       logError("Exception: " + e.getMessage());
/* 323 */       logError(exBuf.getBuffer().toString());
/*     */       
/* 325 */       setCreateDGEntity(true);
/*     */     }
/*     */     finally {
/*     */       
/* 329 */       StringBuffer stringBuffer = new StringBuffer();
/* 330 */       MessageFormat msgf = new MessageFormat(HEADER2);
/* 331 */       arrayOfString[0] = this.m_prof.getOPName();
/* 332 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 333 */       arrayOfString[2] = this.m_prof.getWGName();
/* 334 */       arrayOfString[3] = getNow();
/* 335 */       arrayOfString[4] = stringBuffer.toString();
/* 336 */       arrayOfString[5] = String.valueOf(abrversion) + " " + getABRVersion();
/* 337 */       this.rptSb.insert(0, String.valueOf(convertToHTML(this.xml)) + "\n");
/* 338 */       this.rptSb.insert(0, String.valueOf(header1) + msgf.format(arrayOfString) + NEWLINE);
/*     */       
/* 340 */       println(EACustom.getDocTypeHtml());
/* 341 */       println(this.rptSb.toString());
/* 342 */       printDGSubmitString();
/* 343 */       if (!isReadOnly()) {
/* 344 */         clearSoftLock();
/*     */       }
/* 346 */       println(EACustom.getTOUDiv());
/* 347 */       buildReportFooter();
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
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 362 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem theItem) throws SQLException, MiddlewareException {
/* 371 */     StringBuffer navName = new StringBuffer();
/*     */ 
/*     */     
/* 374 */     EANList metaList = (EANList)this.metaTbl.get(theItem.getEntityType());
/* 375 */     if (metaList == null) {
/* 376 */       EntityGroup eg = new EntityGroup(null, this.m_db, this.m_prof, theItem.getEntityType(), "Navigate");
/* 377 */       metaList = eg.getMetaAttribute();
/*     */       
/* 379 */       this.metaTbl.put(theItem.getEntityType(), metaList);
/*     */     } 
/* 381 */     for (int ii = 0; ii < metaList.size(); ii++) {
/* 382 */       EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
/* 383 */       navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
/* 384 */       if (ii + 1 < metaList.size()) {
/* 385 */         navName.append(" ");
/*     */       }
/*     */     } 
/* 388 */     return navName.toString();
/*     */   }
/*     */   private String getTableMapingDate(String key, SVCMOD svcmod) {
/* 391 */     String value = null;
/* 392 */     if ("MG_PRODUCTTYPE".equals(key)) {
/* 393 */       if ("Service".equals(svcmod.getCATEGORY())) {
/* 394 */         if ("Custom".equals(svcmod.getSUBCATEGORY())) {
/* 395 */           if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
/* 396 */             value = "S3";
/*     */           }
/* 398 */         } else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
/* 399 */           if ("Penalty".equals(svcmod.getGROUP())) {
/* 400 */             value = "S6";
/* 401 */           } else if ("Incident".equals(svcmod.getGROUP())) {
/* 402 */             value = "S7";
/* 403 */           } else if ("Travel".equals(svcmod.getGROUP())) {
/* 404 */             value = "S5";
/* 405 */           } else if ("Activity".equals(svcmod.getGROUP())) {
/* 406 */             value = "S4";
/* 407 */           } else if ("OEM".equals(svcmod.getGROUP())) {
/* 408 */             value = "S4";
/*     */           }
/* 410 */           else if ("ICA/NEC".equals(svcmod.getGROUP())) {
/* 411 */             value = "S2";
/*     */           }
/*     */         
/* 414 */         } else if ("Productized Services".equals(svcmod.getSUBCATEGORY()) && 
/* 415 */           "Non-Federated".equals(svcmod.getGROUP())) {
/* 416 */           value = "S2";
/*     */         }
/*     */       
/* 419 */       } else if ("IP".equals(svcmod.getCATEGORY()) && 
/* 420 */         "SC".equals(svcmod.getSUBCATEGORY())) {
/* 421 */         value = "S8";
/*     */       }
/*     */     
/* 424 */     } else if ("MM_CUSTOM_TYPE".equals(key)) {
/*     */       
/* 426 */       if ("Service".equals(svcmod.getCATEGORY())) {
/* 427 */         if ("Custom".equals(svcmod.getSUBCATEGORY())) {
/* 428 */           if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
/* 429 */             value = "OCI";
/*     */           }
/* 431 */         } else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
/* 432 */           if ("Penalty".equals(svcmod.getGROUP())) {
/* 433 */             value = "PC";
/* 434 */           } else if ("Incident".equals(svcmod.getGROUP())) {
/* 435 */             value = "IC";
/* 436 */           } else if ("Travel".equals(svcmod.getGROUP())) {
/* 437 */             value = "TE";
/* 438 */           } else if ("Activity".equals(svcmod.getGROUP())) {
/* 439 */             value = "SA";
/* 440 */           } else if ("OEM".equals(svcmod.getGROUP())) {
/* 441 */             value = "SA";
/* 442 */           } else if ("ICA/NEC".equals(svcmod.getGROUP())) {
/* 443 */             value = "No characteristic";
/*     */           }
/*     */         
/* 446 */         } else if ("Productized Services".equals(svcmod.getSUBCATEGORY()) && 
/* 447 */           "Non-Federated".equals(svcmod.getGROUP())) {
/* 448 */           value = "SPI";
/*     */         }
/*     */       
/* 451 */       } else if ("IP".equals(svcmod.getCATEGORY()) && 
/* 452 */         "SC".equals(svcmod.getSUBCATEGORY())) {
/* 453 */         value = "IPSC";
/*     */       }
/*     */     
/*     */     }
/* 457 */     else if ("MM_CUSTOM_COSTING".equals(key)) {
/*     */       
/* 459 */       if ("Service".equals(svcmod.getCATEGORY())) {
/* 460 */         if ("Custom".equals(svcmod.getSUBCATEGORY())) {
/* 461 */           if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
/* 462 */             value = "WBS";
/*     */           }
/* 464 */         } else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
/* 465 */           if ("Penalty".equals(svcmod.getGROUP())) {
/* 466 */             value = "WBS";
/* 467 */           } else if ("Incident".equals(svcmod.getGROUP())) {
/* 468 */             value = "WBS";
/* 469 */           } else if ("Travel".equals(svcmod.getGROUP())) {
/* 470 */             value = "WBS";
/* 471 */           } else if ("Activity".equals(svcmod.getGROUP())) {
/* 472 */             value = "WBS";
/* 473 */           } else if ("OEM".equals(svcmod.getGROUP())) {
/* 474 */             value = "WBS";
/* 475 */           } else if ("ICA/NEC".equals(svcmod.getGROUP())) {
/* 476 */             value = "No characteristic";
/*     */           }
/*     */         
/* 479 */         } else if ("Productized Services".equals(svcmod.getSUBCATEGORY()) && 
/* 480 */           "Non-Federated".equals(svcmod.getGROUP())) {
/* 481 */           value = "WBS";
/*     */         }
/*     */       
/* 484 */       } else if ("IP".equals(svcmod.getCATEGORY()) && 
/* 485 */         "SC".equals(svcmod.getSUBCATEGORY())) {
/* 486 */         value = "No characteristic";
/*     */       }
/*     */     
/*     */     }
/* 490 */     else if ("MM_PROFIT_CENTER".equals(key)) {
/*     */       
/* 492 */       if ("Service".equals(svcmod.getCATEGORY())) {
/* 493 */         if ("Custom".equals(svcmod.getSUBCATEGORY())) {
/* 494 */           if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
/* 495 */             value = "D";
/*     */           }
/* 497 */         } else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
/* 498 */           if ("Penalty".equals(svcmod.getGROUP())) {
/* 499 */             value = "C";
/* 500 */           } else if ("Incident".equals(svcmod.getGROUP())) {
/* 501 */             value = "C";
/* 502 */           } else if ("Travel".equals(svcmod.getGROUP())) {
/* 503 */             value = "C";
/* 504 */           } else if ("Activity".equals(svcmod.getGROUP())) {
/* 505 */             value = "C";
/* 506 */           } else if ("OEM".equals(svcmod.getGROUP())) {
/* 507 */             value = "C";
/* 508 */           } else if ("ICA/NEC".equals(svcmod.getGROUP())) {
/* 509 */             value = "No characteristic";
/*     */           }
/*     */         
/* 512 */         } else if ("Productized Services".equals(svcmod.getSUBCATEGORY()) && 
/* 513 */           "Non-Federated".equals(svcmod.getGROUP())) {
/* 514 */           value = "C";
/*     */         }
/*     */       
/* 517 */       } else if ("IP".equals(svcmod.getCATEGORY()) && 
/* 518 */         "SC".equals(svcmod.getSUBCATEGORY())) {
/* 519 */         value = "No characteristic";
/*     */       }
/*     */     
/*     */     }
/* 523 */     else if ("MM_TAX_CATEGORY".equals(key)) {
/*     */       
/* 525 */       if ("Service".equals(svcmod.getCATEGORY())) {
/* 526 */         if ("Custom".equals(svcmod.getSUBCATEGORY())) {
/* 527 */           if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
/* 528 */             value = "D";
/*     */           }
/* 530 */         } else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
/* 531 */           if ("Penalty".equals(svcmod.getGROUP())) {
/* 532 */             value = "C";
/* 533 */           } else if ("Incident".equals(svcmod.getGROUP())) {
/* 534 */             value = "C";
/* 535 */           } else if ("Travel".equals(svcmod.getGROUP())) {
/* 536 */             value = "C";
/* 537 */           } else if ("Activity".equals(svcmod.getGROUP())) {
/* 538 */             value = "C";
/* 539 */           } else if ("OEM".equals(svcmod.getGROUP())) {
/* 540 */             value = "C";
/* 541 */           } else if ("ICA/NEC".equals(svcmod.getGROUP())) {
/* 542 */             value = "No characteristic";
/*     */           }
/*     */         
/* 545 */         } else if ("Productized Services".equals(svcmod.getSUBCATEGORY()) && 
/* 546 */           "Non-Federated".equals(svcmod.getGROUP())) {
/* 547 */           value = "C";
/*     */         }
/*     */       
/* 550 */       } else if ("IP".equals(svcmod.getCATEGORY()) && 
/* 551 */         "SC".equals(svcmod.getSUBCATEGORY())) {
/* 552 */         value = "No characteristic";
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 558 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String convertToHTML(String txt) {
/* 569 */     String retVal = "";
/* 570 */     StringBuffer htmlSB = new StringBuffer();
/* 571 */     StringCharacterIterator sci = null;
/* 572 */     char ch = ' ';
/* 573 */     if (txt != null) {
/* 574 */       sci = new StringCharacterIterator(txt);
/* 575 */       ch = sci.first();
/* 576 */       while (ch != Character.MAX_VALUE) {
/*     */         
/* 578 */         switch (ch) {
/*     */           
/*     */           case '<':
/* 581 */             htmlSB.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 584 */             htmlSB.append("&gt;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 589 */             htmlSB.append("&quot;");
/*     */             break;
/*     */           
/*     */           case '\'':
/* 593 */             htmlSB.append("&#" + ch + ";");
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 602 */             htmlSB.append(ch);
/*     */             break;
/*     */         } 
/* 605 */         ch = sci.next();
/*     */       } 
/* 607 */       retVal = htmlSB.toString();
/*     */     } 
/*     */     
/* 610 */     return retVal;
/*     */   }
/*     */   protected void addOutput(String msg) {
/* 613 */     this.rptSb.append("<p>" + msg + "</p>" + NEWLINE);
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
/*     */   protected void addDebug(String msg) {
/* 626 */     if (3 <= this.abr_debuglvl) {
/* 627 */       this.rptSb.append("<!-- " + msg + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String msg) {
/* 634 */     addOutput(msg);
/* 635 */     setReturnCode(-1);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\SVCMODIERPABRSTATUS.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */