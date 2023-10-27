/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwBulkYMDMProd;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwBulkYMDMSalesBom;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.MODEL;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.UpdateParkStatus;
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
/*     */ import java.util.Set;
/*     */ 
/*     */ public class TMFBULKABRSTATUS extends PokBaseABR {
/*  29 */   private StringBuffer rptSb = new StringBuffer();
/*  30 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  31 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  32 */   private int abr_debuglvl = 0;
/*  33 */   private String navName = "";
/*  34 */   private Hashtable metaTbl = new Hashtable<>();
/*  35 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODEL' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*  36 */   private String modelSQL = "select entity2id as MODELID from opicm.relator where ENTITYTYPE = 'PRODSTRUCT' and ENTITYID = ?  and VALTO > current timestamp and EFFTO > current timestamp with ur";
/*  37 */   private String tmfSQL = "SELECT f.ATTRIBUTEVALUE AS MACHTYPE, t1.ATTRIBUTEVALUE AS MODEL, t2.ATTRIBUTEVALUE AS FEATURECODE FROM opicm.RELATOR r JOIN OPICM.FLAG f ON f.ENTITYTYPE =r.ENTITY2TYPE  AND f.ENTITYID =r.ENTITY2ID AND f.ATTRIBUTECODE ='MACHTYPEATR' JOIN OPICM.TEXT t1 ON t1.ENTITYTYPE =r.ENTITY2TYPE AND t1.ENTITYID =r.ENTITY2ID AND t1.ATTRIBUTECODE ='MODELATR' and t1.nlsid=1 JOIN OPICM.TEXT t2 ON t2.ENTITYTYPE =r.ENTITY1TYPE AND t2.ENTITYID =r.ENTITY1ID AND t2.ATTRIBUTECODE ='FEATURECODE' WHERE r.ENTITYTYPE ='PRODSTRUCT' AND r.ENTITYID =? AND r.VALTO >CURRENT TIMESTAMP AND r.EFFTO > CURRENT TIMESTAMP AND f.VALTO >CURRENT TIMESTAMP AND f.EFFTO > CURRENT TIMESTAMP AND t1.VALTO >CURRENT TIMESTAMP AND t1.EFFTO > CURRENT TIMESTAMP AND t2.VALTO >CURRENT TIMESTAMP AND t2.EFFTO > CURRENT TIMESTAMP WITH ur";
/*     */   
/*  39 */   String xml = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  44 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/*  46 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     String str3 = "";
/*     */ 
/*     */ 
/*     */     
/*  56 */     String str4 = "";
/*  57 */     String str5 = "";
/*     */     
/*  59 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/*  62 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  63 */       arrayOfString[0] = getShortClassName(getClass());
/*  64 */       arrayOfString[1] = "ABR";
/*  65 */       str3 = messageFormat.format(arrayOfString);
/*  66 */       setDGTitle("TMFBULKABRSTATUS report");
/*  67 */       setDGString(getABRReturnCode());
/*  68 */       setDGRptName("TMFBULKABRSTATUS");
/*  69 */       setDGRptClass("TMFBULKABRSTATUS");
/*     */       
/*  71 */       setReturnCode(0);
/*     */       
/*  73 */       start_ABRBuild(false);
/*     */       
/*  75 */       this
/*  76 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/*  80 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/*  82 */               getEntityType(), getEntityID()) });
/*     */       
/*  84 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  85 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*     */       
/*  87 */       this.navName = getNavigationName();
/*  88 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/*  89 */       addDebug("navName=" + this.navName);
/*  90 */       addDebug("rootDesc" + str5);
/*     */ 
/*     */       
/*  93 */       String str6 = null;
/*  94 */       Connection connection1 = this.m_db.getPDHConnection();
/*  95 */       PreparedStatement preparedStatement1 = connection1.prepareStatement(this.modelSQL);
/*  96 */       preparedStatement1.setInt(1, entityItem.getEntityID());
/*  97 */       ResultSet resultSet1 = preparedStatement1.executeQuery();
/*  98 */       while (resultSet1.next()) {
/*  99 */         str6 = resultSet1.getString("MODELID");
/*     */       }
/*     */       
/* 102 */       String str7 = null;
/* 103 */       String str8 = null;
/* 104 */       String str9 = null;
/* 105 */       PreparedStatement preparedStatement2 = connection1.prepareStatement(this.tmfSQL);
/* 106 */       preparedStatement2.setInt(1, entityItem.getEntityID());
/* 107 */       ResultSet resultSet2 = preparedStatement2.executeQuery();
/* 108 */       while (resultSet2.next()) {
/* 109 */         str7 = resultSet2.getString("MACHTYPE");
/* 110 */         str8 = resultSet2.getString("MODEL");
/* 111 */         str9 = resultSet2.getString("FEATURECODE");
/*     */       } 
/*     */       
/* 114 */       addDebug("MACHTYPE:" + str7 + ",MODEL:" + str8 + ",FEATURECODE:" + str9);
/*     */       
/* 116 */       Connection connection2 = this.m_db.getODSConnection();
/* 117 */       PreparedStatement preparedStatement3 = connection2.prepareStatement(this.CACEHSQL);
/* 118 */       preparedStatement3.setString(1, str6);
/* 119 */       ResultSet resultSet3 = preparedStatement3.executeQuery();
/* 120 */       while (resultSet3.next()) {
/* 121 */         this.xml = resultSet3.getString("XMLMESSAGE");
/*     */       }
/*     */       
/* 124 */       if (this.xml != null) {
/*     */         
/* 126 */         MODEL mODEL = (MODEL)XMLParse.getObjectFromXml(this.xml, MODEL.class);
/* 127 */         ChwBulkYMDMProd chwBulkYMDMProd = new ChwBulkYMDMProd(mODEL, "PRODSTRUCT", String.valueOf(entityItem.getEntityID()), this.m_db.getODSConnection(), this.m_db.getPDHConnection());
/* 128 */         addDebug("Calling " + chwBulkYMDMProd.getRFCName());
/* 129 */         chwBulkYMDMProd.execute();
/* 130 */         addDebug(chwBulkYMDMProd.createLogEntry());
/* 131 */         if (chwBulkYMDMProd.getRfcrc() == 0) {
/* 132 */           addOutput(chwBulkYMDMProd.getRFCName() + " called successfully!");
/*     */         } else {
/* 134 */           addOutput(chwBulkYMDMProd.getRFCName() + " called  faild!");
/* 135 */           addOutput(chwBulkYMDMProd.getError_text());
/*     */         } 
/* 137 */         UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", chwBulkYMDMProd.getRFCNum());
/*     */         
/* 139 */         runParkCaller((RdhBase)updateParkStatus, chwBulkYMDMProd.getRFCNum());
/*     */         
/* 141 */         Set set = RFCConfig.getBHPlnts();
/* 142 */         for (String str : set) {
/* 143 */           ChwBulkYMDMSalesBom chwBulkYMDMSalesBom = new ChwBulkYMDMSalesBom(str7, str8, str9, str);
/* 144 */           addDebug("Calling " + chwBulkYMDMSalesBom.getRFCName());
/* 145 */           chwBulkYMDMSalesBom.execute();
/* 146 */           addDebug(chwBulkYMDMSalesBom.createLogEntry());
/* 147 */           if (chwBulkYMDMSalesBom.getRfcrc() == 0) {
/* 148 */             addOutput(chwBulkYMDMSalesBom.getRFCName() + " called successfully!");
/*     */           } else {
/* 150 */             addOutput(chwBulkYMDMSalesBom.getRFCName() + " called  faild!");
/* 151 */             addDebug(chwBulkYMDMSalesBom.getRFCName() + " webservice return code: " + chwBulkYMDMSalesBom.getRfcrc());
/* 152 */             addOutput(chwBulkYMDMSalesBom.getError_text());
/*     */           } 
/* 154 */           updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", chwBulkYMDMSalesBom.getRFCNum());
/* 155 */           runParkCaller((RdhBase)updateParkStatus, chwBulkYMDMSalesBom.getRFCNum());
/*     */         } 
/*     */       } else {
/*     */         
/* 159 */         addOutput("XML file not exist in cache,RFC caller not called!");
/*     */       }
/*     */     
/* 162 */     } catch (Exception exception) {
/*     */       
/* 164 */       exception.printStackTrace();
/* 165 */       setReturnCode(-1);
/* 166 */       StringWriter stringWriter = new StringWriter();
/* 167 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 168 */       String str7 = "<pre>{0}</pre>";
/* 169 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 170 */       setReturnCode(-3);
/* 171 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 173 */       arrayOfString[0] = exception.getMessage();
/* 174 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 175 */       messageFormat = new MessageFormat(str7);
/* 176 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 177 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 178 */       logError("Exception: " + exception.getMessage());
/* 179 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 181 */       setCreateDGEntity(true);
/*     */     } finally {
/*     */       
/* 184 */       StringBuffer stringBuffer = new StringBuffer();
/* 185 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 186 */       arrayOfString[0] = this.m_prof.getOPName();
/* 187 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 188 */       arrayOfString[2] = this.m_prof.getWGName();
/* 189 */       arrayOfString[3] = getNow();
/* 190 */       arrayOfString[4] = stringBuffer.toString();
/* 191 */       arrayOfString[5] = str4 + " " + getABRVersion();
/* 192 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/* 193 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 195 */       println(EACustom.getDocTypeHtml());
/* 196 */       println(this.rptSb.toString());
/* 197 */       printDGSubmitString();
/*     */       
/* 199 */       println(EACustom.getTOUDiv());
/* 200 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */   protected void runParkCaller(RdhBase paramRdhBase, String paramString) throws Exception {
/* 204 */     addDebug("Calling " + paramRdhBase.getRFCName());
/*     */     try {
/* 206 */       paramRdhBase.execute();
/* 207 */     } catch (Exception exception) {
/*     */       
/* 209 */       exception.printStackTrace();
/*     */     } 
/* 211 */     addDebug(paramRdhBase.createLogEntry());
/* 212 */     if (paramRdhBase.getRfcrc() == 0) {
/* 213 */       addOutput("Parking records updated successfully for ZDMRELNUM=" + paramString);
/*     */     } else {
/* 215 */       addOutput(paramRdhBase.getRFCName() + " called faild!");
/* 216 */       addOutput(paramRdhBase.getError_text());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static String convertToHTML(String paramString) {
/* 221 */     String str = "";
/* 222 */     StringBuffer stringBuffer = new StringBuffer();
/* 223 */     StringCharacterIterator stringCharacterIterator = null;
/* 224 */     char c = ' ';
/* 225 */     if (paramString != null) {
/* 226 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 227 */       c = stringCharacterIterator.first();
/* 228 */       while (c != '￿') {
/*     */         
/* 230 */         switch (c) {
/*     */           
/*     */           case '<':
/* 233 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 236 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 241 */             stringBuffer.append("&quot;");
/*     */             break;
/*     */           
/*     */           case '\'':
/* 245 */             stringBuffer.append("&#" + c + ";");
/*     */             break;
/*     */           default:
/* 248 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 251 */         c = stringCharacterIterator.next();
/*     */       } 
/* 253 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 256 */     return str;
/*     */   }
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 260 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 264 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 267 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 268 */     if (eANList == null) {
/* 269 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 270 */       eANList = entityGroup.getMetaAttribute();
/*     */       
/* 272 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 274 */     for (byte b = 0; b < eANList.size(); b++) {
/* 275 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 276 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 277 */       if (b + 1 < eANList.size()) {
/* 278 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 281 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 285 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 290 */     if (3 <= this.abr_debuglvl) {
/* 291 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void addError(String paramString) {
/* 296 */     addOutput(paramString);
/* 297 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 302 */     return "TMFBULKABRSTATUS";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 307 */     return "1.0";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\TMFBULKABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */