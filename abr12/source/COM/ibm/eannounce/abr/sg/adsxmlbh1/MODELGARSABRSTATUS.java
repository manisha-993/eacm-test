/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.GARSYMDMSalesBom;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.MODEL;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhYMDMGars;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.UpdateParkStatus;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.StringCharacterIterator;
/*     */ 
/*     */ public class MODELGARSABRSTATUS extends PokBaseABR {
/*  22 */   private StringBuffer rptSb = new StringBuffer();
/*  23 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  24 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  25 */   private int abr_debuglvl = 0;
/*  26 */   private String navName = "";
/*  27 */   private Hashtable metaTbl = new Hashtable<>();
/*  28 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODEL' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*  29 */   String xml = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  34 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/*  36 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     String str3 = "";
/*     */ 
/*     */ 
/*     */     
/*  46 */     String str4 = "";
/*  47 */     String str5 = "";
/*     */     
/*  49 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/*  52 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  53 */       arrayOfString[0] = getShortClassName(getClass());
/*  54 */       arrayOfString[1] = "ABR";
/*  55 */       str3 = messageFormat.format(arrayOfString);
/*  56 */       setDGTitle("MODELGARSABRSTATUS report");
/*  57 */       setDGString(getABRReturnCode());
/*  58 */       setDGRptName("MODELGARSABRSTATUS");
/*  59 */       setDGRptClass("MODELGARSABRSTATUS");
/*     */       
/*  61 */       setReturnCode(0);
/*  62 */       start_ABRBuild(false);
/*     */       
/*  64 */       this
/*  65 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/*  69 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/*  71 */               getEntityType(), getEntityID()) });
/*     */       
/*  73 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  74 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*     */       
/*  76 */       this.navName = getNavigationName();
/*  77 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/*  78 */       addDebug("navName=" + this.navName);
/*  79 */       addDebug("rootDesc" + str5);
/*     */ 
/*     */       
/*  82 */       Connection connection = this.m_db.getODSConnection();
/*  83 */       PreparedStatement preparedStatement = connection.prepareStatement(this.CACEHSQL);
/*  84 */       preparedStatement.setInt(1, entityItem.getEntityID());
/*  85 */       ResultSet resultSet = preparedStatement.executeQuery();
/*  86 */       while (resultSet.next()) {
/*  87 */         this.xml = resultSet.getString("XMLMESSAGE");
/*     */       }
/*     */       
/*  90 */       if (this.xml != null) {
/*  91 */         MODEL mODEL = (MODEL)XMLParse.getObjectFromXml(this.xml, MODEL.class);
/*  92 */         RdhYMDMGars rdhYMDMGars = new RdhYMDMGars(mODEL, this.m_db.getPDHConnection());
/*  93 */         addDebug("Calling " + rdhYMDMGars.getRFCName());
/*  94 */         rdhYMDMGars.execute();
/*  95 */         addDebug(rdhYMDMGars.createLogEntry());
/*  96 */         if (rdhYMDMGars.getRfcrc() == 0) {
/*  97 */           addOutput(rdhYMDMGars.getRFCName() + " called successfully!");
/*     */         } else {
/*  99 */           addOutput(rdhYMDMGars.getRFCName() + " called  faild!");
/* 100 */           addOutput(rdhYMDMGars.getError_text());
/*     */         } 
/* 102 */         UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", rdhYMDMGars.getRFCNum());
/* 103 */         runParkCaller((RdhBase)updateParkStatus, rdhYMDMGars.getRFCNum());
/*     */         
/* 105 */         Set set = RFCConfig.getBHPlnts();
/* 106 */         for (String str : set) {
/* 107 */           GARSYMDMSalesBom gARSYMDMSalesBom = new GARSYMDMSalesBom(mODEL, str);
/* 108 */           addDebug("Calling " + gARSYMDMSalesBom.getRFCName());
/* 109 */           gARSYMDMSalesBom.execute();
/* 110 */           addDebug(gARSYMDMSalesBom.createLogEntry());
/* 111 */           if (gARSYMDMSalesBom.getRfcrc() == 0) {
/* 112 */             addOutput(gARSYMDMSalesBom.getRFCName() + " called successfully!");
/*     */           } else {
/* 114 */             addOutput(gARSYMDMSalesBom.getRFCName() + " called  faild!");
/* 115 */             addDebug(gARSYMDMSalesBom.getRFCName() + " webservice return code: " + gARSYMDMSalesBom.getRfcrc());
/* 116 */             addOutput(gARSYMDMSalesBom.getError_text());
/*     */           } 
/* 118 */           updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", gARSYMDMSalesBom.getRFCNum());
/* 119 */           runParkCaller((RdhBase)updateParkStatus, gARSYMDMSalesBom.getRFCNum());
/*     */         } 
/*     */       } else {
/* 122 */         addOutput("XML file not exeit in cache,RFC caller not called!");
/*     */       }
/*     */     
/* 125 */     } catch (Exception exception) {
/*     */       
/* 127 */       exception.printStackTrace();
/* 128 */       setReturnCode(-1);
/* 129 */       StringWriter stringWriter = new StringWriter();
/* 130 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 131 */       String str7 = "<pre>{0}</pre>";
/* 132 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 133 */       setReturnCode(-3);
/* 134 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 136 */       arrayOfString[0] = exception.getMessage();
/* 137 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 138 */       messageFormat = new MessageFormat(str7);
/* 139 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 140 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 141 */       logError("Exception: " + exception.getMessage());
/* 142 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 144 */       setCreateDGEntity(true);
/*     */     } finally {
/*     */       
/* 147 */       StringBuffer stringBuffer = new StringBuffer();
/* 148 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 149 */       arrayOfString[0] = this.m_prof.getOPName();
/* 150 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 151 */       arrayOfString[2] = this.m_prof.getWGName();
/* 152 */       arrayOfString[3] = getNow();
/* 153 */       arrayOfString[4] = stringBuffer.toString();
/* 154 */       arrayOfString[5] = str4 + " " + getABRVersion();
/* 155 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/* 156 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 158 */       println(EACustom.getDocTypeHtml());
/* 159 */       println(this.rptSb.toString());
/* 160 */       printDGSubmitString();
/* 161 */       if (!isReadOnly()) {
/* 162 */         clearSoftLock();
/*     */       }
/* 164 */       println(EACustom.getTOUDiv());
/* 165 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */   protected void runParkCaller(RdhBase paramRdhBase, String paramString) throws Exception {
/* 169 */     addDebug("Calling " + paramRdhBase.getRFCName());
/*     */     try {
/* 171 */       paramRdhBase.execute();
/* 172 */     } catch (Exception exception) {
/*     */       
/* 174 */       exception.printStackTrace();
/*     */     } 
/* 176 */     addDebug(paramRdhBase.createLogEntry());
/* 177 */     if (paramRdhBase.getRfcrc() == 0) {
/* 178 */       addOutput("Parking records updated successfully for ZDMRELNUM=" + paramString);
/*     */     } else {
/* 180 */       addOutput(paramRdhBase.getRFCName() + " called faild!");
/* 181 */       addOutput(paramRdhBase.getError_text());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static String convertToHTML(String paramString) {
/* 186 */     String str = "";
/* 187 */     StringBuffer stringBuffer = new StringBuffer();
/* 188 */     StringCharacterIterator stringCharacterIterator = null;
/* 189 */     char c = ' ';
/* 190 */     if (paramString != null) {
/* 191 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 192 */       c = stringCharacterIterator.first();
/* 193 */       while (c != '￿') {
/*     */         
/* 195 */         switch (c) {
/*     */           
/*     */           case '<':
/* 198 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 201 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 206 */             stringBuffer.append("&quot;");
/*     */             break;
/*     */           
/*     */           case '\'':
/* 210 */             stringBuffer.append("&#" + c + ";");
/*     */             break;
/*     */           default:
/* 213 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 216 */         c = stringCharacterIterator.next();
/*     */       } 
/* 218 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 221 */     return str;
/*     */   }
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 225 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 229 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 232 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 233 */     if (eANList == null) {
/* 234 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 235 */       eANList = entityGroup.getMetaAttribute();
/*     */       
/* 237 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 239 */     for (byte b = 0; b < eANList.size(); b++) {
/* 240 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 241 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 242 */       if (b + 1 < eANList.size()) {
/* 243 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 246 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 250 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 255 */     if (3 <= this.abr_debuglvl) {
/* 256 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void addError(String paramString) {
/* 261 */     addOutput(paramString);
/* 262 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 267 */     return "MODELGARSABRSTATUS";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 272 */     return "1.0";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\MODELGARSABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */