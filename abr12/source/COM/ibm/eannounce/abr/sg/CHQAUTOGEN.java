/*     */ package COM.ibm.eannounce.abr.sg;
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
/*     */ public class CHQAUTOGEN
/*     */   extends PokBaseABR
/*     */ {
/*  86 */   private StringBuffer rptSb = new StringBuffer();
/*  87 */   private StringBuffer traceSb = new StringBuffer();
/*     */   
/*  89 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  90 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  98 */     String str1 = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE + "<!-- {2} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */     
/* 108 */     String[] arrayOfString = new String[10];
/*     */     
/* 110 */     EntityGroup entityGroup = null;
/* 111 */     EntityItem entityItem = null;
/*     */ 
/*     */     
/* 114 */     println(EACustom.getDocTypeHtml());
/*     */     
/*     */     try {
/* 117 */       start_ABRBuild();
/*     */       
/* 119 */       this.traceSb.append("CHQAUTOGEN entered for " + getEntityType() + ":" + getEntityID());
/*     */       
/* 121 */       this.traceSb.append(NEWLINE + AUTOGENRpt.outputList(this.m_elist));
/* 122 */       this.rptSb.append("<!-- DEBUG: " + this.traceSb.toString() + " -->" + NEWLINE);
/*     */       
/* 124 */       entityGroup = this.m_elist.getParentEntityGroup();
/* 125 */       entityItem = entityGroup.getEntityItem(0);
/*     */       
/* 127 */       if (entityGroup == null) {
/*     */         
/* 129 */         setReturnCode(-1);
/* 130 */         logMessage("CHQAUTOGEN: " + getVersion() + ":ERROR:1: m_egParent cannot be established.");
/* 131 */         MessageFormat messageFormat1 = new MessageFormat(str2);
/* 132 */         arrayOfString[0] = getShortClassName(getClass());
/* 133 */         arrayOfString[1] = " Failed";
/* 134 */         arrayOfString[2] = getABRVersion();
/* 135 */         this.rptSb.insert(0, messageFormat1.format(arrayOfString) + NEWLINE);
/* 136 */         this.rptSb.append("<!-- DEBUG: m_egParent cannot be established. -->" + NEWLINE);
/* 137 */         println(this.rptSb.toString());
/* 138 */         println(EACustom.getTOUDiv());
/* 139 */         buildReportFooter();
/*     */         return;
/*     */       } 
/* 142 */       if (entityItem == null) {
/*     */         
/* 144 */         setReturnCode(-1);
/* 145 */         logMessage("CHQAUTOGEN: " + getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/* 146 */         MessageFormat messageFormat1 = new MessageFormat(str2);
/* 147 */         arrayOfString[0] = getShortClassName(getClass());
/* 148 */         arrayOfString[1] = " Failed";
/* 149 */         arrayOfString[2] = getABRVersion();
/* 150 */         this.rptSb.insert(0, messageFormat1.format(arrayOfString) + NEWLINE);
/* 151 */         this.rptSb.append("<!-- DEBUG: m_eiParent cannot be established. -->" + NEWLINE);
/* 152 */         println(this.rptSb.toString());
/* 153 */         println(EACustom.getTOUDiv());
/* 154 */         buildReportFooter();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 159 */       setReturnCode(0);
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
/* 172 */       str1 = getNavigationName();
/*     */       
/* 174 */       GeneralAreaList generalAreaList = this.m_db.getGeneralAreaList(this.m_prof);
/* 175 */       AUTOGENRpt aUTOGENRpt = new AUTOGENRpt(this.m_elist, generalAreaList, this.m_db);
/*     */       
/* 177 */       boolean bool = aUTOGENRpt.init(this.rptSb);
/*     */       
/* 179 */       if (bool)
/*     */       {
/*     */         
/* 182 */         aUTOGENRpt.retrieveAnswer(true, this.rptSb);
/*     */ 
/*     */ 
/*     */         
/* 186 */         aUTOGENRpt.cleanUp();
/*     */       }
/*     */     
/* 189 */     } catch (Throwable throwable) {
/*     */       
/* 191 */       StringWriter stringWriter = new StringWriter();
/* 192 */       String str3 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 193 */       String str4 = "<pre>{0}</pre>";
/* 194 */       MessageFormat messageFormat1 = new MessageFormat(str3);
/* 195 */       setReturnCode(-1);
/* 196 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 198 */       arrayOfString[0] = throwable.getMessage();
/* 199 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 200 */       messageFormat1 = new MessageFormat(str4);
/* 201 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 202 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/* 203 */       logError("Exception: " + throwable.getMessage());
/* 204 */       logError(stringWriter.getBuffer().toString());
/*     */     }
/*     */     finally {
/*     */       
/* 208 */       setDGTitle(str1);
/* 209 */       setDGRptName(getShortClassName(getClass()));
/* 210 */       setDGRptClass("");
/*     */       
/* 212 */       if (!isReadOnly())
/*     */       {
/* 214 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 220 */     MessageFormat messageFormat = new MessageFormat(str2);
/* 221 */     arrayOfString[0] = getShortClassName(getClass());
/* 222 */     arrayOfString[1] = str1 + ((getReturnCode() == 0) ? " Passed" : " Failed");
/* 223 */     arrayOfString[2] = getABRVersion();
/* 224 */     this.rptSb.insert(0, messageFormat.format(arrayOfString) + NEWLINE);
/*     */     
/* 226 */     println(this.rptSb.toString());
/* 227 */     printDGSubmitString();
/* 228 */     println(EACustom.getTOUDiv());
/* 229 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 239 */     StringBuffer stringBuffer = new StringBuffer();
/* 240 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */     
/* 243 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/* 244 */     EANList eANList = entityGroup.getMetaAttribute();
/* 245 */     for (byte b = 0; b < eANList.size(); b++) {
/*     */       
/* 247 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 248 */       stringBuffer.append(getAttributeValue(entityItem, eANMetaAttribute.getAttributeCode(), eANMetaAttribute.getAttributeCode()));
/* 249 */       stringBuffer.append(" ");
/*     */     } 
/*     */     
/* 252 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 262 */     return "AutoGen Report.";
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
/* 274 */     return "1.15";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CHQAUTOGEN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */