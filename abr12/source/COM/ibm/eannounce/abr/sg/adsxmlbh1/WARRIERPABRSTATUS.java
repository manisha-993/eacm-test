/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwYMdmOthWarranty;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.UpdateParkStatus;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.WARR;
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
/*     */ public class WARRIERPABRSTATUS extends PokBaseABR {
/*  29 */   private StringBuffer rptSb = new StringBuffer();
/*  30 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  31 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  32 */   private int abr_debuglvl = 0;
/*  33 */   private String navName = "";
/*  34 */   private Hashtable metaTbl = new Hashtable<>();
/*     */   
/*  36 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'WARR' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*     */   
/*  38 */   String xml = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  43 */     return "WARRIERPABRSTATUS";
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/*  47 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  53 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/*  55 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     String str3 = "";
/*     */ 
/*     */     
/*  64 */     String str4 = "";
/*  65 */     String str5 = "";
/*     */     
/*  67 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/*  70 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  71 */       arrayOfString[0] = getShortClassName(getClass());
/*  72 */       arrayOfString[1] = "ABR";
/*  73 */       str3 = messageFormat.format(arrayOfString);
/*  74 */       setDGTitle("WARRIERPABRSTATUS report");
/*  75 */       setDGString(getABRReturnCode());
/*  76 */       setDGRptName("WARRIERPABRSTATUS");
/*  77 */       setDGRptClass("WARRIERPABRSTATUS");
/*     */       
/*  79 */       setReturnCode(0);
/*     */       
/*  81 */       start_ABRBuild(false);
/*     */       
/*  83 */       this
/*  84 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/*  88 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/*  90 */               getEntityType(), getEntityID()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  98 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*     */       
/* 100 */       this.navName = getNavigationName();
/* 101 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/* 102 */       addDebug("navName=" + this.navName);
/* 103 */       addDebug("rootDesc" + str5);
/* 104 */       Connection connection = this.m_db.getODSConnection();
/* 105 */       PreparedStatement preparedStatement = connection.prepareStatement(this.CACEHSQL);
/* 106 */       preparedStatement.setInt(1, entityItem.getEntityID());
/* 107 */       ResultSet resultSet = preparedStatement.executeQuery();
/*     */       
/* 109 */       while (resultSet.next()) {
/* 110 */         this.xml = resultSet.getString("XMLMESSAGE");
/*     */       }
/* 112 */       if (this.xml != null) {
/* 113 */         addDebug("WARR XMLParse xml");
/* 114 */         WARR wARR = (WARR)XMLParse.getObjectFromXml(this.xml, WARR.class);
/*     */ 
/*     */         
/* 117 */         addDebug("Start to call chwYMdmOthWarranty");
/* 118 */         ChwYMdmOthWarranty chwYMdmOthWarranty = new ChwYMdmOthWarranty(wARR);
/* 119 */         if (chwYMdmOthWarranty.getZYTMDMOTHWARRUPD_LIST().size() > 0) {
/* 120 */           runRfcCaller((RdhBase)chwYMdmOthWarranty);
/* 121 */           UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", wARR.getWARRID());
/* 122 */           addDebug("Calling " + updateParkStatus.getRFCName());
/* 123 */           updateParkStatus.execute();
/* 124 */           addDebug(updateParkStatus.createLogEntry());
/* 125 */           if (updateParkStatus.getRfcrc() == 0) {
/* 126 */             addOutput("Parking records updated successfully for ZDMRELNUM=" + wARR.getWARRID());
/*     */           } else {
/* 128 */             addOutput(updateParkStatus.getRFCName() + " called faild!");
/* 129 */             addOutput(updateParkStatus.getError_text());
/*     */           } 
/*     */         } else {
/* 132 */           addOutput("WARRPRIOD must be Number in the WARR_UPDATE entity, please fix the Data and resend it.");
/*     */         } 
/* 134 */         addDebug("End to call chwYMdmOthWarranty");
/*     */       } 
/* 136 */     } catch (Exception exception) {
/* 137 */       exception.printStackTrace();
/*     */       
/* 139 */       setReturnCode(-1);
/* 140 */       StringWriter stringWriter = new StringWriter();
/* 141 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 142 */       String str7 = "<pre>{0}</pre>";
/* 143 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 144 */       setReturnCode(-3);
/* 145 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 147 */       arrayOfString[0] = exception.getMessage();
/* 148 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/* 149 */       messageFormat = new MessageFormat(str7);
/* 150 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 151 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/* 152 */       logError("Exception: " + exception.getMessage());
/* 153 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 155 */       setCreateDGEntity(true);
/*     */     }
/*     */     finally {
/*     */       
/* 159 */       StringBuffer stringBuffer = new StringBuffer();
/* 160 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 161 */       arrayOfString[0] = this.m_prof.getOPName();
/* 162 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 163 */       arrayOfString[2] = this.m_prof.getWGName();
/* 164 */       arrayOfString[3] = getNow();
/* 165 */       arrayOfString[4] = stringBuffer.toString();
/* 166 */       arrayOfString[5] = str4 + " " + getABRVersion();
/* 167 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/* 168 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 170 */       println(EACustom.getDocTypeHtml());
/* 171 */       println(this.rptSb.toString());
/* 172 */       printDGSubmitString();
/* 173 */       if (!isReadOnly()) {
/* 174 */         clearSoftLock();
/*     */       }
/* 176 */       println(EACustom.getTOUDiv());
/* 177 */       buildReportFooter();
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
/* 192 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 202 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 205 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 206 */     if (eANList == null) {
/* 207 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 208 */       eANList = entityGroup.getMetaAttribute();
/*     */       
/* 210 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 212 */     for (byte b = 0; b < eANList.size(); b++) {
/* 213 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 214 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 215 */       if (b + 1 < eANList.size()) {
/* 216 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 219 */     return stringBuffer.toString();
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
/* 230 */     String str = "";
/* 231 */     StringBuffer stringBuffer = new StringBuffer();
/* 232 */     StringCharacterIterator stringCharacterIterator = null;
/* 233 */     char c = ' ';
/* 234 */     if (paramString != null) {
/* 235 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 236 */       c = stringCharacterIterator.first();
/* 237 */       while (c != '￿') {
/*     */         
/* 239 */         switch (c) {
/*     */           
/*     */           case '<':
/* 242 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 245 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 250 */             stringBuffer.append("&quot;");
/*     */             break;
/*     */           
/*     */           case '\'':
/* 254 */             stringBuffer.append("&#" + c + ";");
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 263 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 266 */         c = stringCharacterIterator.next();
/*     */       } 
/* 268 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 271 */     return str;
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
/* 282 */     String str = "";
/* 283 */     StringBuffer stringBuffer = new StringBuffer();
/* 284 */     StringCharacterIterator stringCharacterIterator = null;
/* 285 */     char c = ' ';
/* 286 */     if (paramString != null) {
/* 287 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 288 */       c = stringCharacterIterator.first();
/* 289 */       while (c != '￿') {
/*     */         
/* 291 */         switch (c) {
/*     */           
/*     */           case '<':
/* 294 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 297 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */           default:
/* 300 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 303 */         c = stringCharacterIterator.next();
/*     */       } 
/* 305 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 308 */     return str;
/*     */   }
/*     */   protected void addOutput(String paramString) {
/* 311 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   } protected void addMsg(StringBuffer paramStringBuffer) {
/* 313 */     this.rptSb.append(paramStringBuffer.toString() + NEWLINE);
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
/* 326 */     if (3 <= this.abr_debuglvl) {
/* 327 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 334 */     addOutput(paramString);
/* 335 */     setReturnCode(-1);
/*     */   }
/*     */   
/*     */   protected void runRfcCaller(RdhBase paramRdhBase) throws Exception {
/* 339 */     addDebug("Calling " + paramRdhBase.getRFCName());
/* 340 */     paramRdhBase.execute();
/* 341 */     addDebug(paramRdhBase.createLogEntry());
/* 342 */     if (paramRdhBase.getRfcrc() == 0) {
/* 343 */       addOutput(paramRdhBase.getRFCName() + " called successfully!");
/*     */     } else {
/* 345 */       addOutput(paramRdhBase.getRFCName() + " called  faild!");
/* 346 */       addOutput(paramRdhBase.getError_text());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WARRIERPABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */