/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwYMdmOthWarranty;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.MODEL;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
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
/*     */ public class MODELWARRABRSTATUS extends PokBaseABR {
/*  29 */   private StringBuffer rptSb = new StringBuffer();
/*  30 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  31 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  32 */   private int abr_debuglvl = 0;
/*  33 */   private String navName = "";
/*  34 */   private Hashtable metaTbl = new Hashtable<>();
/*     */   
/*  36 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODEL' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*     */ 
/*     */ 
/*     */   
/*  40 */   String xml = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  45 */     return "MODELWARRABRSTATUS";
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/*  49 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  55 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/*  57 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     String str3 = "";
/*     */ 
/*     */     
/*  66 */     String str4 = "";
/*  67 */     String str5 = "";
/*     */     
/*  69 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/*  72 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  73 */       arrayOfString[0] = getShortClassName(getClass());
/*  74 */       arrayOfString[1] = "ABR";
/*  75 */       str3 = messageFormat.format(arrayOfString);
/*  76 */       setDGTitle("MODELWARRABRSTATUS report");
/*  77 */       setDGString(getABRReturnCode());
/*  78 */       setDGRptName("MODELWARRABRSTATUS");
/*  79 */       setDGRptClass("MODELWARRABRSTATUS");
/*     */       
/*  81 */       setReturnCode(0);
/*     */       
/*  83 */       start_ABRBuild(false);
/*     */       
/*  85 */       this
/*  86 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/*  90 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/*  92 */               getEntityType(), getEntityID()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  99 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 100 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*     */       
/* 102 */       this.navName = getNavigationName();
/* 103 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/* 104 */       addDebug("navName=" + this.navName);
/* 105 */       addDebug("rootDesc" + str5);
/* 106 */       Connection connection = this.m_db.getODSConnection();
/* 107 */       PreparedStatement preparedStatement = connection.prepareStatement(this.CACEHSQL);
/* 108 */       preparedStatement.setInt(1, entityItem.getEntityID());
/* 109 */       ResultSet resultSet = preparedStatement.executeQuery();
/*     */       
/* 111 */       while (resultSet.next()) {
/* 112 */         this.xml = resultSet.getString("XMLMESSAGE");
/*     */       }
/* 114 */       if (this.xml != null) {
/*     */         
/* 116 */         MODEL mODEL = (MODEL)XMLParse.getObjectFromXml(this.xml, MODEL.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 122 */         String str = mODEL.getWARRSVCCOVR();
/* 123 */         if (!"Warranty".equalsIgnoreCase(str)) {
/* 124 */           addOutput("WARRSVCCOVR value is not Warranty, so nothing to promote.");
/*     */         } else {
/* 126 */           ChwYMdmOthWarranty chwYMdmOthWarranty = new ChwYMdmOthWarranty(mODEL, this.m_db.getPDHConnection());
/* 127 */           if (chwYMdmOthWarranty.getZYTMDMOTHWARRMOD_LIST().size() > 0) {
/* 128 */             runRfcCaller((RdhBase)chwYMdmOthWarranty);
/* 129 */             UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", mODEL.getMACHTYPE() + mODEL.getMODEL());
/* 130 */             addDebug("Calling " + updateParkStatus.getRFCName());
/* 131 */             updateParkStatus.execute();
/* 132 */             addDebug(updateParkStatus.createLogEntry());
/* 133 */             if (updateParkStatus.getRfcrc() == 0) {
/* 134 */               addOutput("Parking records updated successfully for ZDMRELNUM=" + mODEL.getMACHTYPE() + mODEL.getMODEL());
/*     */             } else {
/* 136 */               addOutput(updateParkStatus.getRFCName() + " called faild!");
/* 137 */               addOutput(updateParkStatus.getError_text());
/*     */             } 
/*     */           } else {
/* 140 */             addOutput("No warranty linked to the MODEL, so nothing to promote.");
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } 
/* 146 */     } catch (Exception exception) {
/* 147 */       exception.printStackTrace();
/*     */       
/* 149 */       setReturnCode(-1);
/* 150 */       StringWriter stringWriter = new StringWriter();
/* 151 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 152 */       String str7 = "<pre>{0}</pre>";
/* 153 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 154 */       setReturnCode(-3);
/* 155 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 157 */       arrayOfString[0] = exception.getMessage();
/* 158 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/* 159 */       messageFormat = new MessageFormat(str7);
/* 160 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 161 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/* 162 */       logError("Exception: " + exception.getMessage());
/* 163 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 165 */       setCreateDGEntity(true);
/*     */     }
/*     */     finally {
/*     */       
/* 169 */       StringBuffer stringBuffer = new StringBuffer();
/* 170 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 171 */       arrayOfString[0] = this.m_prof.getOPName();
/* 172 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 173 */       arrayOfString[2] = this.m_prof.getWGName();
/* 174 */       arrayOfString[3] = getNow();
/* 175 */       arrayOfString[4] = stringBuffer.toString();
/* 176 */       arrayOfString[5] = str4 + " " + getABRVersion();
/* 177 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/* 178 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 180 */       println(EACustom.getDocTypeHtml());
/* 181 */       println(this.rptSb.toString());
/* 182 */       printDGSubmitString();
/* 183 */       if (!isReadOnly()) {
/* 184 */         clearSoftLock();
/*     */       }
/* 186 */       println(EACustom.getTOUDiv());
/* 187 */       buildReportFooter();
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
/* 202 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 212 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 215 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 216 */     if (eANList == null) {
/* 217 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 218 */       eANList = entityGroup.getMetaAttribute();
/*     */       
/* 220 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 222 */     for (byte b = 0; b < eANList.size(); b++) {
/* 223 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 224 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 225 */       if (b + 1 < eANList.size()) {
/* 226 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 229 */     return stringBuffer.toString();
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
/* 240 */     String str = "";
/* 241 */     StringBuffer stringBuffer = new StringBuffer();
/* 242 */     StringCharacterIterator stringCharacterIterator = null;
/* 243 */     char c = ' ';
/* 244 */     if (paramString != null) {
/* 245 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 246 */       c = stringCharacterIterator.first();
/* 247 */       while (c != '￿') {
/*     */         
/* 249 */         switch (c) {
/*     */           
/*     */           case '<':
/* 252 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 255 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 260 */             stringBuffer.append("&quot;");
/*     */             break;
/*     */           
/*     */           case '\'':
/* 264 */             stringBuffer.append("&#" + c + ";");
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 273 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 276 */         c = stringCharacterIterator.next();
/*     */       } 
/* 278 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 281 */     return str;
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
/* 292 */     String str = "";
/* 293 */     StringBuffer stringBuffer = new StringBuffer();
/* 294 */     StringCharacterIterator stringCharacterIterator = null;
/* 295 */     char c = ' ';
/* 296 */     if (paramString != null) {
/* 297 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 298 */       c = stringCharacterIterator.first();
/* 299 */       while (c != '￿') {
/*     */         
/* 301 */         switch (c) {
/*     */           
/*     */           case '<':
/* 304 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 307 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */           default:
/* 310 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 313 */         c = stringCharacterIterator.next();
/*     */       } 
/* 315 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 318 */     return str;
/*     */   }
/*     */   protected void addOutput(String paramString) {
/* 321 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   } protected void addMsg(StringBuffer paramStringBuffer) {
/* 323 */     this.rptSb.append(paramStringBuffer.toString() + NEWLINE);
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
/* 336 */     if (3 <= this.abr_debuglvl) {
/* 337 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 344 */     addOutput(paramString);
/* 345 */     setReturnCode(-1);
/*     */   }
/*     */   
/*     */   protected void runRfcCaller(RdhBase paramRdhBase) throws Exception {
/* 349 */     addDebug("Calling " + paramRdhBase.getRFCName());
/* 350 */     paramRdhBase.execute();
/* 351 */     addDebug(paramRdhBase.createLogEntry());
/* 352 */     if (paramRdhBase.getRfcrc() == 0) {
/* 353 */       addOutput(paramRdhBase.getRFCName() + " called successfully!");
/*     */     } else {
/* 355 */       addOutput(paramRdhBase.getRFCName() + " called  faild!");
/* 356 */       addOutput(paramRdhBase.getError_text());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\MODELWARRABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */