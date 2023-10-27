/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.ChwYMdmOthWarranty;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.CommonUtils;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.TMF_UPDATE;
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
/*     */ public class TMFWARRABRSTATUS
/*     */   extends PokBaseABR {
/*  31 */   private StringBuffer rptSb = new StringBuffer();
/*  32 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  33 */   static final String NEWLINE = new String(FOOL_JTEST);
/*  34 */   private int abr_debuglvl = 0;
/*  35 */   private String navName = "";
/*  36 */   private Hashtable metaTbl = new Hashtable<>();
/*     */   
/*  38 */   private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'PRODSTRUCT' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
/*     */   
/*  40 */   private String FEATURESQL = "SELECT attributevalue FROM OPICM.TEXT WHERE ENTITYTYPE='FEATURE' AND ENTITYID=? and  attributecode='MKTGNAME' AND NLSID=1 and valto>current timestamp and effto >current timestamp with ur";
/*     */ 
/*     */   
/*  43 */   String xml = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  48 */     return "TMFWARRABRSTATUS";
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/*  52 */     return "1.0";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  58 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*     */     
/*  60 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     String str3 = "";
/*     */ 
/*     */     
/*  69 */     String str4 = "";
/*  70 */     String str5 = "";
/*     */     
/*  72 */     String[] arrayOfString = new String[10];
/*     */     
/*     */     try {
/*  75 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  76 */       arrayOfString[0] = getShortClassName(getClass());
/*  77 */       arrayOfString[1] = "ABR";
/*  78 */       str3 = messageFormat.format(arrayOfString);
/*  79 */       setDGTitle("TMFWARRABRSTATUS report");
/*  80 */       setDGString(getABRReturnCode());
/*  81 */       setDGRptName("TMFWARRABRSTATUS");
/*  82 */       setDGRptClass("TMFWARRABRSTATUS");
/*     */       
/*  84 */       setReturnCode(0);
/*     */       
/*  86 */       start_ABRBuild(false);
/*     */       
/*  88 */       this
/*  89 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*     */ 
/*     */ 
/*     */       
/*  93 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*     */               
/*  95 */               getEntityType(), getEntityID()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 103 */       addDebug("*****mlm rootEntity = " + entityItem.getEntityType() + entityItem.getEntityID());
/*     */       
/* 105 */       this.navName = getNavigationName();
/* 106 */       str5 = this.m_elist.getParentEntityGroup().getLongDescription();
/* 107 */       addDebug("navName=" + this.navName);
/* 108 */       addDebug("rootDesc" + str5);
/* 109 */       Connection connection = this.m_db.getODSConnection();
/* 110 */       PreparedStatement preparedStatement = connection.prepareStatement(this.CACEHSQL);
/* 111 */       preparedStatement.setInt(1, entityItem.getEntityID());
/* 112 */       ResultSet resultSet = preparedStatement.executeQuery();
/*     */ 
/*     */       
/* 115 */       while (resultSet.next()) {
/* 116 */         this.xml = resultSet.getString("XMLMESSAGE");
/*     */       }
/* 118 */       if (this.xml != null)
/*     */       {
/* 120 */         TMF_UPDATE tMF_UPDATE = (TMF_UPDATE)XMLParse.getObjectFromXml(this.xml, TMF_UPDATE.class);
/*     */         
/* 122 */         String str = tMF_UPDATE.getWARRSVCCOVR();
/* 123 */         if (!"Warranty".equalsIgnoreCase(str)) {
/* 124 */           addOutput("WARRSVCCOVR value is not Warranty, so nothing to promote.");
/*     */         }
/*     */         else {
/*     */           
/* 128 */           String str6 = getAttributevalue(this.FEATURESQL, tMF_UPDATE.getFEATUREENTITYID());
/* 129 */           ChwYMdmOthWarranty chwYMdmOthWarranty = new ChwYMdmOthWarranty(tMF_UPDATE, str6);
/* 130 */           if (chwYMdmOthWarranty.getZYTMDMOTHWARRTMF_LIST().size() > 0) {
/* 131 */             runRfcCaller((RdhBase)chwYMdmOthWarranty);
/* 132 */             UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", tMF_UPDATE.getMACHTYPE() + tMF_UPDATE.getFEATURECODE());
/* 133 */             addDebug("Calling " + updateParkStatus.getRFCName());
/* 134 */             updateParkStatus.execute();
/* 135 */             addDebug(updateParkStatus.createLogEntry());
/* 136 */             if (updateParkStatus.getRfcrc() == 0) {
/* 137 */               addOutput("Parking records updated successfully for ZDMRELNUM=" + tMF_UPDATE.getMACHTYPE() + tMF_UPDATE.getFEATURECODE());
/*     */             } else {
/* 139 */               addOutput(updateParkStatus.getRFCName() + " called faild!");
/* 140 */               addOutput(updateParkStatus.getError_text());
/*     */             } 
/*     */           } else {
/* 143 */             addOutput("No warranty linked to the TMF, so nothing to promote.");
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 150 */     } catch (Exception exception) {
/* 151 */       exception.printStackTrace();
/*     */       
/* 153 */       setReturnCode(-1);
/* 154 */       StringWriter stringWriter = new StringWriter();
/* 155 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 156 */       String str7 = "<pre>{0}</pre>";
/* 157 */       MessageFormat messageFormat = new MessageFormat(str6);
/* 158 */       setReturnCode(-3);
/* 159 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 161 */       arrayOfString[0] = exception.getMessage();
/* 162 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/* 163 */       messageFormat = new MessageFormat(str7);
/* 164 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 165 */       this.rptSb.append(convertToTag(messageFormat.format(arrayOfString)) + NEWLINE);
/* 166 */       logError("Exception: " + exception.getMessage());
/* 167 */       logError(stringWriter.getBuffer().toString());
/*     */       
/* 169 */       setCreateDGEntity(true);
/*     */     }
/*     */     finally {
/*     */       
/* 173 */       StringBuffer stringBuffer = new StringBuffer();
/* 174 */       MessageFormat messageFormat = new MessageFormat(str2);
/* 175 */       arrayOfString[0] = this.m_prof.getOPName();
/* 176 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/* 177 */       arrayOfString[2] = this.m_prof.getWGName();
/* 178 */       arrayOfString[3] = getNow();
/* 179 */       arrayOfString[4] = stringBuffer.toString();
/* 180 */       arrayOfString[5] = str4 + " " + getABRVersion();
/* 181 */       this.rptSb.insert(0, convertToHTML(this.xml) + "\n");
/* 182 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*     */       
/* 184 */       println(EACustom.getDocTypeHtml());
/* 185 */       println(this.rptSb.toString());
/* 186 */       printDGSubmitString();
/* 187 */       if (!isReadOnly()) {
/* 188 */         clearSoftLock();
/*     */       }
/* 190 */       println(EACustom.getTOUDiv());
/* 191 */       buildReportFooter();
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
/* 206 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 216 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 219 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 220 */     if (eANList == null) {
/* 221 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 222 */       eANList = entityGroup.getMetaAttribute();
/*     */       
/* 224 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*     */     } 
/* 226 */     for (byte b = 0; b < eANList.size(); b++) {
/* 227 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 228 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 229 */       if (b + 1 < eANList.size()) {
/* 230 */         stringBuffer.append(" ");
/*     */       }
/*     */     } 
/* 233 */     return stringBuffer.toString();
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
/* 244 */     String str = "";
/* 245 */     StringBuffer stringBuffer = new StringBuffer();
/* 246 */     StringCharacterIterator stringCharacterIterator = null;
/* 247 */     char c = ' ';
/* 248 */     if (paramString != null) {
/* 249 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 250 */       c = stringCharacterIterator.first();
/* 251 */       while (c != '￿') {
/*     */         
/* 253 */         switch (c) {
/*     */           
/*     */           case '<':
/* 256 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 259 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 264 */             stringBuffer.append("&quot;");
/*     */             break;
/*     */           
/*     */           case '\'':
/* 268 */             stringBuffer.append("&#" + c + ";");
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           default:
/* 277 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 280 */         c = stringCharacterIterator.next();
/*     */       } 
/* 282 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 285 */     return str;
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
/* 296 */     String str = "";
/* 297 */     StringBuffer stringBuffer = new StringBuffer();
/* 298 */     StringCharacterIterator stringCharacterIterator = null;
/* 299 */     char c = ' ';
/* 300 */     if (paramString != null) {
/* 301 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 302 */       c = stringCharacterIterator.first();
/* 303 */       while (c != '￿') {
/*     */         
/* 305 */         switch (c) {
/*     */           
/*     */           case '<':
/* 308 */             stringBuffer.append("&lt;");
/*     */             break;
/*     */           case '>':
/* 311 */             stringBuffer.append("&gt;");
/*     */             break;
/*     */           default:
/* 314 */             stringBuffer.append(c);
/*     */             break;
/*     */         } 
/* 317 */         c = stringCharacterIterator.next();
/*     */       } 
/* 319 */       str = stringBuffer.toString();
/*     */     } 
/*     */     
/* 322 */     return str;
/*     */   }
/*     */   protected void addOutput(String paramString) {
/* 325 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*     */   } protected void addMsg(StringBuffer paramStringBuffer) {
/* 327 */     this.rptSb.append(paramStringBuffer.toString() + NEWLINE);
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
/* 340 */     if (3 <= this.abr_debuglvl) {
/* 341 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addError(String paramString) {
/* 348 */     addOutput(paramString);
/* 349 */     setReturnCode(-1);
/*     */   }
/*     */   
/*     */   protected void runRfcCaller(RdhBase paramRdhBase) throws Exception {
/* 353 */     addDebug("Calling " + paramRdhBase.getRFCName());
/* 354 */     paramRdhBase.execute();
/* 355 */     addDebug(paramRdhBase.createLogEntry());
/* 356 */     if (paramRdhBase.getRfcrc() == 0) {
/* 357 */       addOutput(paramRdhBase.getRFCName() + " called successfully!");
/*     */     } else {
/* 359 */       addOutput(paramRdhBase.getRFCName() + " called  faild!");
/* 360 */       addOutput(paramRdhBase.getError_text());
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getAttributevalue(String paramString1, String paramString2) {
/* 365 */     String str = "";
/*     */     try {
/* 367 */       Connection connection = this.m_db.getPDHConnection();
/* 368 */       String[] arrayOfString = new String[1];
/* 369 */       arrayOfString[0] = paramString2;
/* 370 */       String str1 = CommonUtils.getPreparedSQL(paramString1, (Object[])arrayOfString);
/* 371 */       addDebug("querySql=" + str1);
/* 372 */       PreparedStatement preparedStatement = connection.prepareStatement(paramString1);
/* 373 */       preparedStatement.setString(1, paramString2);
/*     */       
/* 375 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 376 */       if (resultSet.next()) {
/* 377 */         str = resultSet.getString("attributevalue");
/*     */       }
/* 379 */     } catch (SQLException sQLException) {
/* 380 */       sQLException.printStackTrace();
/* 381 */     } catch (MiddlewareException middlewareException) {
/* 382 */       middlewareException.printStackTrace();
/*     */     } 
/* 384 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\TMFWARRABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */