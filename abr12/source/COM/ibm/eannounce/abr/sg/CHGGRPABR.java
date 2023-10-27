/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
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
/*     */ public class CHGGRPABR
/*     */   extends PokBaseABR
/*     */ {
/*  58 */   private StringBuffer rptSb = new StringBuffer();
/*  59 */   private StringBuffer traceSb = new StringBuffer();
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
/*     */   public void execute_run() {
/*  72 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + EACustom.NEWLINE + EACustom.getCSS() + EACustom.NEWLINE + EACustom.getTitle("{0} {1}") + EACustom.NEWLINE + "</head>" + EACustom.NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + EACustom.NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + EACustom.NEWLINE + "<!-- {2} -->" + EACustom.NEWLINE;
/*     */ 
/*     */ 
/*     */     
/*  76 */     MessageFormat messageFormat = null;
/*  77 */     String[] arrayOfString = new String[10];
/*     */     
/*  79 */     String str2 = "";
/*  80 */     println(EACustom.getDocTypeHtml());
/*     */     
/*     */     try {
/*  83 */       CHGGRPRptGen cHGGRPRptGen = null;
/*     */       
/*  85 */       start_ABRBuild();
/*     */       
/*  87 */       this.traceSb.append("CHGGRPABR entered for " + getEntityType() + ":" + getEntityID());
/*     */       
/*  89 */       this.traceSb.append(EACustom.NEWLINE + CHGGRPRptGen.outputList(this.m_elist));
/*  90 */       this.rptSb.append("<!-- DEBUG: " + this.traceSb.toString() + " -->" + EACustom.NEWLINE);
/*     */ 
/*     */       
/*  93 */       str2 = getNavigationName();
/*     */       
/*  95 */       this.rptSb.append("<h2>Change Group Report: " + 
/*  96 */           getAttributeValue(this.m_elist.getParentEntityGroup().getEntityItem(0), "CHGGRPNAME", "CHGGRPNAME") + "</h2>" + EACustom.NEWLINE);
/*     */ 
/*     */       
/*  99 */       cHGGRPRptGen = new CHGGRPRptGen(this.m_db, this.m_prof);
/*     */       
/* 101 */       this.rptSb.append(cHGGRPRptGen.getHeaderAndDTS(this.m_elist) + EACustom.NEWLINE);
/*     */ 
/*     */       
/* 104 */       this.rptSb.append(cHGGRPRptGen.getMachType() + EACustom.NEWLINE);
/* 105 */       this.rptSb.append(cHGGRPRptGen.getModelAndFeature() + EACustom.NEWLINE);
/* 106 */       this.rptSb.append(cHGGRPRptGen.getProdStruct() + EACustom.NEWLINE);
/* 107 */       this.rptSb.append(cHGGRPRptGen.getOtherStructure() + EACustom.NEWLINE);
/* 108 */       this.rptSb.append(cHGGRPRptGen.getMTM() + EACustom.NEWLINE);
/*     */       
/* 110 */       this.rptSb.append(cHGGRPRptGen.getModelConversion() + EACustom.NEWLINE);
/*     */       
/* 112 */       this.rptSb.append(cHGGRPRptGen.getFeatureTrans() + EACustom.NEWLINE);
/*     */ 
/*     */       
/* 115 */       cHGGRPRptGen.dereference();
/*     */       
/* 117 */       setReturnCode(0);
/*     */     }
/* 119 */     catch (Throwable throwable) {
/*     */       
/* 121 */       String str3 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 122 */       String str4 = "<pre>{0}</pre>";
/* 123 */       StringWriter stringWriter = new StringWriter();
/* 124 */       setReturnCode(-1);
/* 125 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 127 */       messageFormat = new MessageFormat(str3);
/* 128 */       arrayOfString[0] = throwable.getMessage();
/* 129 */       this.rptSb.append(messageFormat.format(arrayOfString) + EACustom.NEWLINE);
/* 130 */       messageFormat = new MessageFormat(str4);
/* 131 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 132 */       this.rptSb.append(messageFormat.format(arrayOfString) + EACustom.NEWLINE);
/* 133 */       logError("Exception: " + throwable.getMessage());
/* 134 */       logError(stringWriter.getBuffer().toString());
/*     */     }
/*     */     finally {
/*     */       
/* 138 */       setDGTitle(str2);
/* 139 */       setDGRptName(getShortClassName(getClass()));
/* 140 */       setDGRptClass("CHGRPT");
/*     */       
/* 142 */       if (!isReadOnly()) {
/* 143 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 149 */     messageFormat = new MessageFormat(str1);
/* 150 */     arrayOfString[0] = getShortClassName(getClass());
/* 151 */     arrayOfString[1] = str2 + ((getReturnCode() == 0) ? " Passed" : " Failed");
/* 152 */     arrayOfString[2] = getABRVersion();
/* 153 */     this.rptSb.insert(0, messageFormat.format(arrayOfString) + EACustom.NEWLINE);
/*     */     
/* 155 */     println(this.rptSb.toString());
/* 156 */     printDGSubmitString();
/* 157 */     println(EACustom.getTOUDiv());
/* 158 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 168 */     StringBuffer stringBuffer = new StringBuffer();
/* 169 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */     
/* 172 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/* 173 */     EANList eANList = entityGroup.getMetaAttribute();
/* 174 */     for (byte b = 0; b < eANList.size(); b++) {
/*     */       
/* 176 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 177 */       stringBuffer.append(getAttributeValue(entityItem, eANMetaAttribute.getAttributeCode(), eANMetaAttribute.getAttributeCode()));
/* 178 */       stringBuffer.append(" ");
/*     */     } 
/*     */     
/* 181 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 190 */     return "Change Group Report.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 201 */     return "1.9";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CHGGRPABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */