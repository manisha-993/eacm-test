/*     */ package COM.ibm.eannounce.abr.sgv30b;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.GeneralAreaList;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
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
/*     */ public class CHQAUTOGEN
/*     */   extends PokBaseABR
/*     */ {
/*  98 */   private StringBuffer rptSb = new StringBuffer();
/*  99 */   private StringBuffer traceSb = new StringBuffer();
/*     */   
/* 101 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/* 102 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 110 */     String str1 = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE + "<!-- {2} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */     
/* 120 */     String[] arrayOfString = new String[10];
/*     */     
/* 122 */     EntityGroup entityGroup = null;
/* 123 */     EntityItem entityItem = null;
/*     */ 
/*     */     
/* 126 */     log("AUTOGENRpt version is $Revision: 1.21 $");
/* 127 */     log("CHQAUTOGEN version is " + getABRVersion());
/* 128 */     println(EACustom.getDocTypeHtml());
/*     */     
/*     */     try {
/* 131 */       start_ABRBuild();
/*     */       
/* 133 */       this.traceSb.append("CHQAUTOGEN entered for " + getEntityType() + ":" + getEntityID());
/*     */       
/* 135 */       this.traceSb.append(NEWLINE + AUTOGENRpt.outputList(this.m_elist));
/* 136 */       this.rptSb.append("<!-- DEBUG: " + this.traceSb.toString() + " -->" + NEWLINE);
/*     */       
/* 138 */       entityGroup = this.m_elist.getParentEntityGroup();
/* 139 */       entityItem = entityGroup.getEntityItem(0);
/*     */       
/* 141 */       if (entityGroup == null) {
/*     */         
/* 143 */         setReturnCode(-1);
/* 144 */         logMessage("CHQAUTOGEN: " + getVersion() + ":ERROR:1: m_egParent cannot be established.");
/* 145 */         MessageFormat messageFormat1 = new MessageFormat(str2);
/* 146 */         arrayOfString[0] = getShortClassName(getClass());
/* 147 */         arrayOfString[1] = " Failed";
/* 148 */         arrayOfString[2] = getABRVersion();
/* 149 */         this.rptSb.insert(0, messageFormat1.format(arrayOfString) + NEWLINE);
/* 150 */         this.rptSb.append("<!-- DEBUG: m_egParent cannot be established. -->" + NEWLINE);
/* 151 */         println(this.rptSb.toString());
/* 152 */         println(EACustom.getTOUDiv());
/* 153 */         buildReportFooter();
/*     */         return;
/*     */       } 
/* 156 */       if (entityItem == null) {
/*     */         
/* 158 */         setReturnCode(-1);
/* 159 */         logMessage("CHQAUTOGEN: " + getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/* 160 */         MessageFormat messageFormat1 = new MessageFormat(str2);
/* 161 */         arrayOfString[0] = getShortClassName(getClass());
/* 162 */         arrayOfString[1] = " Failed";
/* 163 */         arrayOfString[2] = getABRVersion();
/* 164 */         this.rptSb.insert(0, messageFormat1.format(arrayOfString) + NEWLINE);
/* 165 */         this.rptSb.append("<!-- DEBUG: m_eiParent cannot be established. -->" + NEWLINE);
/* 166 */         println(this.rptSb.toString());
/* 167 */         println(EACustom.getTOUDiv());
/* 168 */         buildReportFooter();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 173 */       setReturnCode(0);
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
/* 186 */       str1 = getNavigationName();
/*     */       
/* 188 */       GeneralAreaList generalAreaList = this.m_db.getGeneralAreaList(this.m_prof);
/* 189 */       AUTOGENRpt aUTOGENRpt = new AUTOGENRpt(this.m_elist, generalAreaList, this.m_db);
/*     */       
/* 191 */       boolean bool = aUTOGENRpt.init(this.rptSb);
/*     */       
/* 193 */       if (bool)
/*     */       {
/*     */         
/* 196 */         aUTOGENRpt.retrieveAnswer(true, this.rptSb);
/*     */ 
/*     */ 
/*     */         
/* 200 */         aUTOGENRpt.cleanUp();
/*     */       }
/*     */     
/* 203 */     } catch (Throwable throwable) {
/*     */       
/* 205 */       StringWriter stringWriter = new StringWriter();
/* 206 */       String str3 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 207 */       String str4 = "<pre>{0}</pre>";
/* 208 */       MessageFormat messageFormat1 = new MessageFormat(str3);
/* 209 */       setReturnCode(-1);
/* 210 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 212 */       arrayOfString[0] = throwable.getMessage();
/* 213 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 214 */       messageFormat1 = new MessageFormat(str4);
/* 215 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 216 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 217 */       logError("Exception: " + throwable.getMessage());
/* 218 */       logError(stringWriter.getBuffer().toString());
/*     */     }
/*     */     finally {
/*     */       
/* 222 */       setDGTitle(str1);
/* 223 */       setDGRptName(getShortClassName(getClass()));
/* 224 */       setDGRptClass("");
/*     */       
/* 226 */       if (!isReadOnly())
/*     */       {
/* 228 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 234 */     MessageFormat messageFormat = new MessageFormat(str2);
/* 235 */     arrayOfString[0] = getShortClassName(getClass());
/* 236 */     arrayOfString[1] = str1 + ((getReturnCode() == 0) ? " Passed" : " Failed");
/* 237 */     arrayOfString[2] = getABRVersion();
/* 238 */     this.rptSb.insert(0, messageFormat.format(arrayOfString) + NEWLINE);
/*     */     
/* 240 */     println(this.rptSb.toString());
/* 241 */     printDGSubmitString();
/* 242 */     println(EACustom.getTOUDiv());
/* 243 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 253 */     StringBuffer stringBuffer = new StringBuffer();
/* 254 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */     
/* 257 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/* 258 */     EANList eANList = entityGroup.getMetaAttribute();
/* 259 */     for (byte b = 0; b < eANList.size(); b++) {
/*     */       
/* 261 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 262 */       stringBuffer.append(getAttributeValue(entityItem, eANMetaAttribute.getAttributeCode(), eANMetaAttribute.getAttributeCode()));
/* 263 */       stringBuffer.append(" ");
/*     */     } 
/*     */     
/* 266 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 276 */     return "AutoGen Report.";
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
/*     */   public String getABRVersion() {
/* 288 */     return "$Revision: 1.6 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sgv30b\CHQAUTOGEN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */