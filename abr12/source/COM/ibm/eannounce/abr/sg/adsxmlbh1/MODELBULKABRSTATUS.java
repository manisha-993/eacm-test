/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwBulkYMDMProd;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.MODEL;
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.StringCharacterIterator;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class MODELBULKABRSTATUS extends PokBaseABR {
/*  21 */   private StringBuffer rptSb = new StringBuffer();
/*  22 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  23 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  24 */   private int abr_debuglvl = 0;
/*  25 */   private String navName = "";
/*  26 */   private Hashtable metaTbl = new Hashtable<>();
/*  27 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODEL' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*  28 */   String xml = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  33 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/*  35 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  41 */     String str3 = "";
/*     */ 
/*     */ 
/*     */     
/*  45 */     String str4 = "";
/*  46 */     String str5 = "";
/*     */     
/*  48 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/*  51 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  52 */       arrayOfString[0] = getShortClassName(getClass());
/*  53 */       arrayOfString[1] = "ABR";
/*  54 */       str3 = messageFormat.format(arrayOfString);
/*  55 */       setDGTitle("MODELBULKABRSTATUS report");
/*  56 */       setDGString(getABRReturnCode());
/*  57 */       setDGRptName("MODELBULKABRSTATUS");
/*  58 */       setDGRptClass("MODELBULKABRSTATUS");
/*     */       
/*  60 */       setReturnCode(0);
/*  61 */       start_ABRBuild(false);
/*     */       
/*  63 */       this
/*  64 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/*  68 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/*  70 */               getEntityType(), getEntityID()) });
/*     */       
/*  72 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  73 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*     */       
/*  75 */       this.navName = getNavigationName();
/*  76 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/*  77 */       addDebug("navName=" + this.navName);
/*  78 */       addDebug("rootDesc" + str5);
/*     */ 
/*     */       
/*  81 */       Connection connection = this.m_db.getODSConnection();
/*  82 */       PreparedStatement preparedStatement = connection.prepareStatement(this.CACEHSQL);
/*  83 */       preparedStatement.setInt(1, entityItem.getEntityID());
/*  84 */       ResultSet resultSet = preparedStatement.executeQuery();
/*     */       
/*  86 */       while (resultSet.next()) {
/*  87 */         this.xml = resultSet.getString("XMLMESSAGE");
/*     */       }
/*     */       
/*  90 */       if (this.xml != null) {
/*  91 */         MODEL mODEL = (MODEL)XMLParse.getObjectFromXml(this.xml, MODEL.class);
/*  92 */         ChwBulkYMDMProd chwBulkYMDMProd = new ChwBulkYMDMProd(mODEL, "MODEL", "", this.m_db.getODSConnection(), this.m_db.getPDHConnection());
/*  93 */         addDebug("Calling " + chwBulkYMDMProd.getRFCName());
/*  94 */         chwBulkYMDMProd.execute();
/*  95 */         addDebug(chwBulkYMDMProd.createLogEntry());
/*  96 */         if (chwBulkYMDMProd.getRfcrc() == 0) {
/*  97 */           addOutput(chwBulkYMDMProd.getRFCName() + " called successfully!");
/*     */         } else {
/*  99 */           addOutput(chwBulkYMDMProd.getRFCName() + " called  faild!");
/* 100 */           addOutput(chwBulkYMDMProd.getError_text());
/*     */         } 
/*     */       } else {
/*     */         
/* 104 */         addOutput("XML file not exist in cache,RFC caller not called!");
/*     */       }
/*     */     
/* 107 */     } catch (Exception exception) {
/*     */       
/* 109 */       exception.printStackTrace();
/* 110 */       setReturnCode(-1);
/* 111 */       StringWriter stringWriter = new StringWriter();
/* 112 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 113 */       String str7 = "<pre>{0}</pre>";
/* 114 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 115 */       setReturnCode(-3);
/* 116 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 118 */       arrayOfString[0] = exception.getMessage();
/* 119 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 120 */       messageFormat = new MessageFormat(str7);
/* 121 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 122 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 123 */       logError("Exception: " + exception.getMessage());
/* 124 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 126 */       setCreateDGEntity(true);
/*     */     } finally {
/*     */       
/* 129 */       StringBuffer stringBuffer = new StringBuffer();
/* 130 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 131 */       arrayOfString[0] = this.m_prof.getOPName();
/* 132 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 133 */       arrayOfString[2] = this.m_prof.getWGName();
/* 134 */       arrayOfString[3] = getNow();
/* 135 */       arrayOfString[4] = stringBuffer.toString();
/* 136 */       arrayOfString[5] = str4 + " " + getABRVersion();
/* 137 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/* 138 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 140 */       println(EACustom.getDocTypeHtml());
/* 141 */       println(this.rptSb.toString());
/* 142 */       printDGSubmitString();
/* 143 */       if (!isReadOnly()) {
/* 144 */         clearSoftLock();
/*     */       }
/* 146 */       println(EACustom.getTOUDiv());
/* 147 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String convertToHTML(String paramString) {
/* 153 */     String str = "";
/* 154 */     StringBuffer stringBuffer = new StringBuffer();
/* 155 */     StringCharacterIterator stringCharacterIterator = null;
/* 156 */     char c = ' ';
/* 157 */     if (paramString != null) {
/* 158 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 159 */       c = stringCharacterIterator.first();
/* 160 */       while (c != '￿') {
/*     */         
/* 162 */         switch (c) {
/*     */           
/*     */           case '<':
/* 165 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 168 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 173 */             stringBuffer.append("&quot;");
/*     */             break;
/*     */           
/*     */           case '\'':
/* 177 */             stringBuffer.append("&#" + c + ";");
/*     */             break;
/*     */           default:
/* 180 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 183 */         c = stringCharacterIterator.next();
/*     */       } 
/* 185 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 188 */     return str;
/*     */   }
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 192 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 196 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 199 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 200 */     if (eANList == null) {
/* 201 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 202 */       eANList = entityGroup.getMetaAttribute();
/*     */       
/* 204 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 206 */     for (byte b = 0; b < eANList.size(); b++) {
/* 207 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 208 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 209 */       if (b + 1 < eANList.size()) {
/* 210 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 213 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   protected void addOutput(String paramString) {
/* 217 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDebug(String paramString) {
/* 222 */     if (3 <= this.abr_debuglvl) {
/* 223 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void addError(String paramString) {
/* 228 */     addOutput(paramString);
/* 229 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 234 */     return "MODELBULKABRSTATUS";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 239 */     return "1.0";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\MODELBULKABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */