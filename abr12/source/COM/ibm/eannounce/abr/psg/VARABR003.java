/*     */ package COM.ibm.eannounce.abr.psg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.WorkflowActionItem;
/*     */ import COM.ibm.eannounce.objects.WorkflowException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
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
/*     */ public class VARABR003
/*     */   extends PokBaseABR
/*     */ {
/*  99 */   private ResourceBundle bundle = null;
/*     */   
/* 101 */   private StringBuffer rptSb = new StringBuffer();
/* 102 */   private StringBuffer traceSb = new StringBuffer();
/*     */   
/*     */   private static final String MISSING = "<p>VARABR003 cannot pass because there is no &quot;{0}&quot; entity linked to the Variant.</p>";
/*     */   private static final String TOO_MANY = "<p>VARABR003 cannot pass because there is more than one &quot;{0}&quot; entity linked to the Variant.</p>";
/* 106 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/* 107 */   static final String NEWLINE = new String(FOOL_JTEST);
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
/*     */   public void execute_run() {
/* 121 */     String str1 = "<html><head><title>{0} {1}</title></head>" + NEWLINE + "<body><h1>{0}: {1}</h1>" + NEWLINE + "<p><b>Date: </b>{2}<br/>" + NEWLINE + "<b>User: </b>{3} ({4})<br />" + NEWLINE + "<b>Description: </b>{5}</p>" + NEWLINE + "<!-- {6} -->";
/*     */ 
/*     */     
/* 124 */     String[] arrayOfString = new String[10];
/* 125 */     MessageFormat messageFormat = null;
/* 126 */     String str2 = "";
/*     */     try {
/* 128 */       start_ABRBuild();
/*     */       
/* 130 */       this.bundle = ResourceBundle.getBundle(getClass().getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */       
/* 132 */       this.traceSb.append("VARABR003 entered for " + getEntityType() + ":" + getEntityID());
/*     */       
/* 134 */       this.traceSb.append(NEWLINE + "EntityList contains the following entities: ");
/* 135 */       for (byte b = 0; b < this.m_elist.getEntityGroupCount(); b++) {
/*     */         
/* 137 */         EntityGroup entityGroup = this.m_elist.getEntityGroup(b);
/* 138 */         this.traceSb.append(NEWLINE + entityGroup.getEntityType() + " : " + entityGroup.getEntityItemCount() + " entity items. ");
/* 139 */         if (entityGroup.getEntityItemCount() > 0) {
/*     */           
/* 141 */           this.traceSb.append("IDs(");
/* 142 */           for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/* 143 */             this.traceSb.append(" " + entityGroup.getEntityItem(b1).getEntityID());
/*     */           }
/* 145 */           this.traceSb.append(")");
/*     */         } 
/*     */       } 
/* 148 */       this.traceSb.append(NEWLINE);
/*     */ 
/*     */       
/* 151 */       str2 = getNavigationName();
/*     */ 
/*     */       
/* 154 */       if (this.m_elist.getEntityGroupCount() == 0) {
/* 155 */         throw new Exception("EntityList did not have any groups. Verify that extract is defined.");
/*     */       }
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
/* 172 */       this.rptSb.append("<h3>" + this.m_elist
/* 173 */           .getParentEntityGroup().getLongDescription() + " PN: " + 
/* 174 */           getAttributeValue(getRootEntityType(), getRootEntityID(), "OFFERINGPNUMB") + "</h3>" + NEWLINE);
/*     */ 
/*     */       
/* 177 */       if (verifyVAR()) {
/*     */         
/* 179 */         String str = "<p>All relators are valid.</p>";
/*     */         
/* 181 */         triggerWorkFlow("WFVARRFR");
/* 182 */         setReturnCode(0);
/* 183 */         this.rptSb.append((this.bundle == null) ? str : (this.bundle.getString("OK_MSG") + NEWLINE));
/*     */       } else {
/*     */         
/* 186 */         setReturnCode(-1);
/*     */       }
/*     */     
/* 189 */     } catch (Exception exception) {
/*     */       
/* 191 */       String str3 = "<h3><font color=red>Error: {0}</font></h3>";
/* 192 */       String str4 = "<pre>{0}</pre>";
/* 193 */       StringWriter stringWriter = new StringWriter();
/* 194 */       messageFormat = new MessageFormat((this.bundle == null) ? str3 : this.bundle.getString("Error_EXCEPTION"));
/* 195 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*     */       
/* 197 */       arrayOfString[0] = exception.getMessage();
/* 198 */       this.rptSb.append(messageFormat.format(arrayOfString));
/* 199 */       messageFormat = new MessageFormat((this.bundle == null) ? str4 : this.bundle.getString("Error_STACKTRACE"));
/* 200 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 201 */       this.rptSb.append(messageFormat.format(arrayOfString));
/* 202 */       logError("Exception: " + exception.getMessage());
/* 203 */       logError(stringWriter.getBuffer().toString());
/* 204 */       setReturnCode(-1);
/*     */     }
/*     */     finally {
/*     */       
/* 208 */       setDGTitle(str2);
/* 209 */       setDGRptName(getShortClassName(getClass()));
/* 210 */       setDGRptClass("WWABR");
/*     */       
/* 212 */       if (!isReadOnly()) {
/* 213 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 220 */     messageFormat = new MessageFormat((this.bundle == null) ? str1 : this.bundle.getString("HEADER"));
/* 221 */     arrayOfString[0] = getShortClassName(getClass());
/* 222 */     arrayOfString[1] = str2 + ((getReturnCode() == 0) ? " Passed" : " Failed");
/* 223 */     arrayOfString[2] = getNow();
/* 224 */     arrayOfString[3] = this.m_prof.getOPName();
/* 225 */     arrayOfString[4] = this.m_prof.getRoleDescription();
/* 226 */     arrayOfString[5] = getDescription();
/* 227 */     arrayOfString[6] = getABRVersion();
/* 228 */     this.rptSb.insert(0, messageFormat.format(arrayOfString) + NEWLINE);
/* 229 */     this.rptSb.append("<!-- DEBUG: " + this.traceSb.toString() + " -->" + NEWLINE);
/*     */     
/* 231 */     println(this.rptSb.toString());
/* 232 */     printDGSubmitString();
/* 233 */     buildReportFooter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean verifyVAR() {
/* 242 */     boolean bool = true;
/*     */ 
/*     */     
/* 245 */     String[] arrayOfString = { "DD", "CTO", "CPG" };
/* 246 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*     */       
/* 248 */       EntityGroup entityGroup1 = this.m_elist.getEntityGroup(arrayOfString[b]);
/* 249 */       if (entityGroup1 != null) {
/*     */         
/* 251 */         bool = (checkSingleRelator(entityGroup1) && bool) ? true : false;
/*     */       }
/*     */       else {
/*     */         
/* 255 */         this.traceSb.append(NEWLINE + "ERROR: " + arrayOfString[b] + " EntityGroup not found");
/* 256 */         bool = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 261 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SBB");
/* 262 */     if (entityGroup != null) {
/*     */       
/* 264 */       if (entityGroup.getEntityItemCount() == 0)
/*     */       {
/* 266 */         MessageFormat messageFormat = new MessageFormat((this.bundle == null) ? "<p>VARABR003 cannot pass because there is no &quot;{0}&quot; entity linked to the Variant.</p>" : this.bundle.getString("Error_MISSING"));
/* 267 */         String[] arrayOfString1 = new String[1];
/* 268 */         bool = false;
/* 269 */         arrayOfString1[0] = entityGroup.getLongDescription();
/* 270 */         this.rptSb.append(messageFormat.format(arrayOfString1) + NEWLINE);
/* 271 */         this.traceSb.append(NEWLINE + "FAIL: " + entityGroup.getEntityType() + " is invalid, has " + entityGroup.getEntityItemCount() + " entities.");
/*     */       }
/*     */       else
/*     */       {
/* 275 */         this.traceSb.append(NEWLINE + entityGroup.getEntityType() + " EntityGroup is valid (has 1 or more)");
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 280 */       this.traceSb.append(NEWLINE + "ERROR: SBB EntityGroup not found");
/* 281 */       bool = false;
/*     */     } 
/*     */     
/* 284 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkSingleRelator(EntityGroup paramEntityGroup) {
/* 292 */     boolean bool = false;
/* 293 */     MessageFormat messageFormat = null;
/* 294 */     String[] arrayOfString = new String[1];
/*     */     
/* 296 */     switch (paramEntityGroup.getEntityItemCount())
/*     */     
/*     */     { case 0:
/* 299 */         messageFormat = new MessageFormat((this.bundle == null) ? "<p>VARABR003 cannot pass because there is no &quot;{0}&quot; entity linked to the Variant.</p>" : this.bundle.getString("Error_MISSING"));
/* 300 */         arrayOfString[0] = paramEntityGroup.getLongDescription();
/* 301 */         this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 302 */         this.traceSb.append(NEWLINE + "FAIL: " + paramEntityGroup.getEntityType() + " is invalid, has " + paramEntityGroup.getEntityItemCount() + " entities.");
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
/* 316 */         return bool;case 1: this.traceSb.append(NEWLINE + paramEntityGroup.getEntityType() + " EntityGroup is valid (has 1)"); bool = true; return bool; }  messageFormat = new MessageFormat((this.bundle == null) ? "<p>VARABR003 cannot pass because there is more than one &quot;{0}&quot; entity linked to the Variant.</p>" : this.bundle.getString("Error_TOO_MANY")); arrayOfString[0] = paramEntityGroup.getLongDescription(); this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE); this.traceSb.append(NEWLINE + "FAIL: " + paramEntityGroup.getEntityType() + " is invalid, has " + paramEntityGroup.getEntityItemCount() + " entities."); return bool;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void triggerWorkFlow(String paramString) throws SQLException, MiddlewareException, WorkflowException, MiddlewareShutdownInProgressException {
/* 336 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 337 */     EntityItem[] arrayOfEntityItem = new EntityItem[1];
/* 338 */     arrayOfEntityItem[0] = entityGroup.getEntityItem(0);
/* 339 */     WorkflowActionItem workflowActionItem = new WorkflowActionItem(null, this.m_db, this.m_prof, paramString);
/* 340 */     workflowActionItem.setEntityItems(arrayOfEntityItem);
/* 341 */     this.m_db.executeAction(this.m_prof, workflowActionItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 351 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 353 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/* 354 */     EANList eANList = entityGroup.getMetaAttribute();
/* 355 */     for (byte b = 0; b < eANList.size(); b++) {
/*     */       
/* 357 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 358 */       stringBuffer.append(getAttributeValue(getRootEntityType(), getRootEntityID(), eANMetaAttribute.getAttributeCode()));
/* 359 */       stringBuffer.append(" ");
/*     */     } 
/*     */     
/* 362 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 371 */     String str = "Variant Setup Verification.";
/* 372 */     if (this.bundle != null) {
/* 373 */       str = this.bundle.getString("DESCRIPTION");
/*     */     }
/*     */     
/* 376 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 385 */     return "$Revision: 1.5 $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Locale getLocale(int paramInt) {
/* 395 */     Locale locale = null;
/* 396 */     switch (paramInt)
/*     */     { case 1:
/* 398 */         locale = Locale.US;
/*     */       case 2:
/* 400 */         locale = Locale.GERMAN;
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
/* 420 */         return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\VARABR003.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */