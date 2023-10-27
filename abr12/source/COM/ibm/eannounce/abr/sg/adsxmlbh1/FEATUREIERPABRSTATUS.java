/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.FEATURE;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhChwFcProd;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FEATUREIERPABRSTATUS
/*     */   extends PokBaseABR
/*     */ {
/*  37 */   private StringBuffer rptSb = new StringBuffer();
/*  38 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  39 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  40 */   private int abr_debuglvl = 0;
/*  41 */   private String navName = "";
/*  42 */   private Hashtable metaTbl = new Hashtable<>();
/*  43 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'FEATURE' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*  44 */   String xml = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  50 */     return "PIABRSTATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/*  56 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  62 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/*  64 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     String str3 = "";
/*     */ 
/*     */ 
/*     */     
/*  74 */     String str4 = "";
/*  75 */     String str5 = "";
/*     */     
/*  77 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/*  80 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  81 */       arrayOfString[0] = getShortClassName(getClass());
/*  82 */       arrayOfString[1] = "ABR";
/*  83 */       str3 = messageFormat.format(arrayOfString);
/*  84 */       setDGTitle("FEATUREIERPABRSTATUS report");
/*  85 */       setDGString(getABRReturnCode());
/*  86 */       setDGRptName("FEATUREIERPABRSTATUS");
/*  87 */       setDGRptClass("FEATUREIERPABRSTATUS");
/*     */       
/*  89 */       setReturnCode(0);
/*     */       
/*  91 */       start_ABRBuild(false);
/*     */       
/*  93 */       this
/*  94 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/*  98 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/* 100 */               getEntityType(), getEntityID()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 108 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*     */       
/* 110 */       this.navName = getNavigationName();
/* 111 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/* 112 */       addDebug("navName=" + this.navName);
/* 113 */       addDebug("rootDesc" + str5);
/*     */ 
/*     */       
/* 116 */       Connection connection = this.m_db.getODSConnection();
/* 117 */       PreparedStatement preparedStatement = connection.prepareStatement(this.CACEHSQL);
/* 118 */       preparedStatement.setInt(1, entityItem.getEntityID());
/* 119 */       ResultSet resultSet = preparedStatement.executeQuery();
/*     */       
/* 121 */       while (resultSet.next()) {
/* 122 */         this.xml = resultSet.getString("XMLMESSAGE");
/*     */       }
/* 124 */       if (this.xml != null) {
/*     */         
/* 126 */         FEATURE fEATURE = (FEATURE)XMLParse.getObjectFromXml(this.xml, FEATURE.class);
/* 127 */         String str = "FEATURE" + fEATURE.getENTITYID();
/* 128 */         RdhChwFcProd rdhChwFcProd = new RdhChwFcProd(fEATURE, str);
/* 129 */         addDebug("Calling " + rdhChwFcProd.getRFCName());
/* 130 */         rdhChwFcProd.execute();
/* 131 */         addDebug(rdhChwFcProd.createLogEntry());
/* 132 */         if (rdhChwFcProd.getRfcrc() == 0) {
/* 133 */           addOutput(rdhChwFcProd.getRFCName() + " called successfully!");
/*     */         } else {
/* 135 */           addOutput(rdhChwFcProd.getRFCName() + " called  faild!");
/* 136 */           addOutput(rdhChwFcProd.getError_text());
/*     */         } 
/*     */         
/* 139 */         UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", "F" + str);
/* 140 */         runParkCaller((RdhBase)updateParkStatus, "F" + str);
/*     */       } else {
/* 142 */         addOutput("XML file not exist in cache,RFC caller not called!");
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
/* 154 */     catch (Exception exception) {
/*     */       
/* 156 */       exception.printStackTrace();
/*     */       
/* 158 */       setReturnCode(-1);
/* 159 */       StringWriter stringWriter = new StringWriter();
/* 160 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 161 */       String str7 = "<pre>{0}</pre>";
/* 162 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 163 */       setReturnCode(-3);
/* 164 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 166 */       arrayOfString[0] = exception.getMessage();
/* 167 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 168 */       messageFormat = new MessageFormat(str7);
/* 169 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 170 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 171 */       logError("Exception: " + exception.getMessage());
/* 172 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 174 */       setCreateDGEntity(true);
/*     */     }
/*     */     finally {
/*     */       
/* 178 */       StringBuffer stringBuffer = new StringBuffer();
/* 179 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 180 */       arrayOfString[0] = this.m_prof.getOPName();
/* 181 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 182 */       arrayOfString[2] = this.m_prof.getWGName();
/* 183 */       arrayOfString[3] = getNow();
/* 184 */       arrayOfString[4] = stringBuffer.toString();
/* 185 */       arrayOfString[5] = str4 + " " + getABRVersion();
/* 186 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/* 187 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 189 */       println(EACustom.getDocTypeHtml());
/* 190 */       println(this.rptSb.toString());
/* 191 */       printDGSubmitString();
/* 192 */       if (!isReadOnly()) {
/* 193 */         clearSoftLock();
/*     */       }
/* 195 */       println(EACustom.getTOUDiv());
/* 196 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void runParkCaller(RdhBase paramRdhBase, String paramString) throws Exception {
/* 202 */     addDebug("Calling " + paramRdhBase.getRFCName());
/* 203 */     paramRdhBase.execute();
/* 204 */     addDebug(paramRdhBase.createLogEntry());
/* 205 */     if (paramRdhBase.getRfcrc() == 0) {
/* 206 */       addOutput("Parking records updated successfully for ZDMRELNUM=" + paramString);
/*     */     } else {
/* 208 */       addOutput(paramRdhBase.getRFCName() + " called faild!");
/* 209 */       addOutput(paramRdhBase.getError_text());
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
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 221 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 230 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 233 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 234 */     if (eANList == null) {
/* 235 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 236 */       eANList = entityGroup.getMetaAttribute();
/*     */       
/* 238 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 240 */     for (byte b = 0; b < eANList.size(); b++) {
/* 241 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 242 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 243 */       if (b + 1 < eANList.size()) {
/* 244 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 247 */     return stringBuffer.toString();
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
/* 259 */     String str = "";
/* 260 */     StringBuffer stringBuffer = new StringBuffer();
/* 261 */     StringCharacterIterator stringCharacterIterator = null;
/* 262 */     char c = ' ';
/* 263 */     if (paramString != null) {
/* 264 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 265 */       c = stringCharacterIterator.first();
/* 266 */       while (c != '￿') {
/*     */         
/* 268 */         switch (c) {
/*     */           
/*     */           case '<':
/* 271 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 274 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 279 */             stringBuffer.append("&quot;");
/*     */             break;
/*     */           
/*     */           case '\'':
/* 283 */             stringBuffer.append("&#" + c + ";");
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 292 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 295 */         c = stringCharacterIterator.next();
/*     */       } 
/* 297 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 300 */     return str;
/*     */   }
/*     */   protected void addOutput(String paramString) {
/* 303 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
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
/* 316 */     if (3 <= this.abr_debuglvl) {
/* 317 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 324 */     addOutput(paramString);
/* 325 */     setReturnCode(-1);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\FEATUREIERPABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */