/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.EACustom;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.GENSALESMANPDG;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
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
/*     */ public class GENSALESMANABR
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2006  All Rights Reserved.";
/*  42 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  43 */   private static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*     */   private static final int MAX_TITLE_LEN = 64;
/*     */   
/*     */   private MessageFormat msgf;
/*  48 */   private Object[] args = (Object[])new String[10];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  54 */     String str1 = "Generate Sales Manual";
/*  55 */     String str2 = "";
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  60 */       start_ABRBuild();
/*     */       
/*  62 */       setReturnCode(0);
/*     */       
/*  64 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  65 */       str1 = entityItem.toString();
/*  66 */       if (str1.length() > 64) {
/*  67 */         str1 = str1.substring(0, 64);
/*     */       }
/*     */ 
/*     */       
/*  71 */       log("GENSALESMANABR generating data for " + entityItem.getKey() + " " + str1);
/*  72 */       GENSALESMANPDG gENSALESMANPDG = new GENSALESMANPDG(null, this.m_db, this.m_prof, "GENSALESMANPDG");
/*  73 */       gENSALESMANPDG.setEntityItem(entityItem);
/*  74 */       gENSALESMANPDG.executeAction(this.m_db, this.m_prof);
/*  75 */       str2 = getResults(entityItem, gENSALESMANPDG);
/*  76 */       log("GENSALESMANABR finish generating data");
/*  77 */     } catch (SBRException sBRException) {
/*  78 */       String str = sBRException.toString();
/*  79 */       setReturnCode(-1);
/*  80 */       this.msgf = new MessageFormat("<pre>Error:<br />{0}</pre>");
/*  81 */       this.args[0] = str;
/*  82 */       str2 = getErrorHtml(str1, this.msgf.format(this.args));
/*  83 */       logError(str);
/*  84 */     } catch (Throwable throwable) {
/*  85 */       StringBuffer stringBuffer = new StringBuffer();
/*  86 */       StringWriter stringWriter = new StringWriter();
/*  87 */       setReturnCode(-1);
/*  88 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/*  90 */       this.msgf = new MessageFormat("<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>");
/*  91 */       this.args[0] = throwable.getMessage();
/*  92 */       stringBuffer.append(this.msgf.format(this.args) + NEWLINE);
/*  93 */       this.msgf = new MessageFormat("<pre>{0}</pre>");
/*  94 */       this.args[0] = stringWriter.getBuffer().toString();
/*  95 */       stringBuffer.append(this.msgf.format(this.args) + NEWLINE);
/*  96 */       str2 = getErrorHtml(str1, stringBuffer.toString());
/*  97 */       logError("Exception: " + throwable.getMessage());
/*  98 */       logError(stringWriter.getBuffer().toString());
/*     */     } finally {
/*     */       
/* 101 */       setDGTitle(str1);
/* 102 */       setDGRptName(getShortClassName(getClass()));
/*     */       
/* 104 */       if (!isReadOnly()) {
/* 105 */         clearSoftLock();
/*     */       }
/*     */       
/* 108 */       println(str2);
/*     */       
/* 110 */       printDGSubmitString();
/* 111 */       buildReportFooter();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getResults(EntityItem paramEntityItem, GENSALESMANPDG paramGENSALESMANPDG) {
/* 119 */     int i = -1;
/*     */     
/* 121 */     String str = paramGENSALESMANPDG.getSalesManualRpt(this.m_prof);
/*     */ 
/*     */     
/* 124 */     i = str.indexOf("</body>");
/* 125 */     if (i > 0) {
/* 126 */       str = str.substring(0, i);
/*     */     }
/* 128 */     else if (str.length() == 0) {
/* 129 */       logError("GENSALESMANABR " + paramEntityItem.getKey() + " AFPDGRESULT attribute should be set and is empty!");
/*     */     } 
/*     */ 
/*     */     
/* 133 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getErrorHtml(String paramString1, String paramString2) {
/* 140 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     String str = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE + "<p><b>Date: </b>{2}<br /><b>User: </b>{3} ({4})<br /><b>email: </b>{5} <br /><b>Description: </b>{6}</p>" + NEWLINE + "<!-- {7} -->" + NEWLINE;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     this.msgf = new MessageFormat(str);
/* 152 */     this.args[0] = getShortClassName(getClass());
/* 153 */     this.args[1] = paramString1 + " Failed";
/* 154 */     this.args[2] = getNow();
/* 155 */     this.args[3] = this.m_prof.getOPName();
/* 156 */     this.args[4] = this.m_prof.getRoleDescription();
/* 157 */     this.args[5] = this.m_prof.getEmailAddress();
/* 158 */     this.args[6] = getDescription();
/* 159 */     this.args[7] = getABRVersion();
/*     */     
/* 161 */     stringBuffer.append(EACustom.getDocTypeHtml());
/*     */     
/* 163 */     stringBuffer.append(this.msgf.format(this.args) + NEWLINE);
/* 164 */     stringBuffer.append(paramString2 + NEWLINE);
/* 165 */     stringBuffer.append(EACustom.getTOUDiv() + NEWLINE);
/*     */     
/* 167 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 176 */     return "Sales Manual Generation ABR.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 185 */     return "1.3";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\GENSALESMANABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */