/*     */ package COM.ibm.eannounce.abr.psg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANEntity;
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
/*     */ public class VARABR004
/*     */   extends PokBaseABR
/*     */ {
/*  76 */   private ResourceBundle bundle = null;
/*     */   
/*  78 */   private StringBuffer rptSb = new StringBuffer();
/*  79 */   private StringBuffer traceSb = new StringBuffer();
/*     */   
/*  81 */   private final String Error_MISSING = "<p>VARABR004 cannot pass because there is no &quot;{0}&quot; entity linked to the Variant.</p>";
/*  82 */   private final String Error_TOO_MANY = "<p>VARABR004 cannot pass because there is more than one &quot;{0}&quot; entity linked to the Variant.</p>";
/*  83 */   private final String Error_TOO_MANY_LINKS = "<p>VARABR004 cannot pass because there is more than one &quot;{0}&quot; relator linked to the Variant.</p>";
/*  84 */   private final String Check1_PASSED = "<p>Check 1 - Passed</p>";
/*  85 */   private final String Check2_PASSED = "<p>Check 2 - Passed</p>";
/*  86 */   private final String Check3_PASSED = "<p>Check 3 - Passed</p>";
/*  87 */   private final String Error_PSL = "<p>VARABR004 cannot Pass because there is a PSL but no PSLAVAIL, or there is a PSLAVAIL but no PSL, or the SLOTAVAILTYPE does not match.</p>";
/*  88 */   private final String Error_PBY = "<p>VARABR004 cannot Pass because there is a PBY but no PBYAVAIL, or there is a PBYAVAIL but no PBY, or the BAYAVAILTYPE does not match.</p>";
/*  89 */   private final String Error_VARSTATUS = "<p>VARABR004 cannot Pass because Status is not Ready for Review.</p>";
/*  90 */   private final String OK_MSG = "<p>All checks are valid.</p>";
/*  91 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  92 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 100 */     String str1 = "";
/* 101 */     String str2 = "<html><head><title>{0} {1}</title></head>" + NEWLINE + "<body><h1>{0}: {1}</h1>" + NEWLINE + "<p><b>Date: </b>{2}<br/>" + NEWLINE + "<b>User: </b>{3} ({4})<br />" + NEWLINE + "<b>Description: </b>{5}</p>" + NEWLINE + "<!-- {6} -->";
/*     */ 
/*     */ 
/*     */     
/* 105 */     MessageFormat messageFormat = new MessageFormat((this.bundle == null) ? str2 : this.bundle.getString("HEADER"));
/* 106 */     String[] arrayOfString = new String[10];
/*     */     try {
/* 108 */       String str = null;
/*     */       
/* 110 */       start_ABRBuild();
/*     */       
/* 112 */       this
/* 113 */         .bundle = ResourceBundle.getBundle(
/* 114 */           getClass().getName(), 
/* 115 */           getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */       
/* 117 */       this.traceSb.append("VARABR004 entered for " + getEntityType() + ":" + getEntityID());
/*     */       
/* 119 */       this.traceSb.append(NEWLINE + "EntityList contains the following entities: ");
/* 120 */       for (byte b = 0; b < this.m_elist.getEntityGroupCount(); b++) {
/* 121 */         EntityGroup entityGroup = this.m_elist.getEntityGroup(b);
/* 122 */         this.traceSb.append(NEWLINE + entityGroup.getEntityType() + " : " + entityGroup.getEntityItemCount() + " entity items. ");
/* 123 */         if (entityGroup.getEntityItemCount() > 0) {
/* 124 */           this.traceSb.append("IDs(");
/* 125 */           for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/* 126 */             this.traceSb.append(" " + entityGroup.getEntityItem(b1).getEntityID());
/*     */           }
/* 128 */           this.traceSb.append(")");
/*     */         } 
/*     */       } 
/* 131 */       this.traceSb.append(NEWLINE);
/*     */ 
/*     */       
/* 134 */       str1 = getNavigationName();
/*     */ 
/*     */       
/* 137 */       if (this.m_elist.getEntityGroupCount() == 0) {
/* 138 */         throw new Exception("EntityList did not have any groups. Verify that extract is defined.");
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
/*     */ 
/*     */ 
/*     */       
/* 158 */       this.rptSb.append("<h3>" + this.m_elist
/* 159 */           .getParentEntityGroup().getLongDescription() + " PN: " + 
/* 160 */           getAttributeValue(getRootEntityType(), getRootEntityID(), "OFFERINGPNUMB") + "</h3>" + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 165 */       str = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "VARSTATUS", "");
/* 166 */       boolean bool = str.equals("0040");
/* 167 */       if (!bool) {
/* 168 */         this.rptSb.append((this.bundle == null) ? "<p>VARABR004 cannot Pass because Status is not Ready for Review.</p>" : (this.bundle.getString("Error_VARSTATUS") + NEWLINE));
/*     */       }
/*     */ 
/*     */       
/* 172 */       if (!checkRelators()) {
/* 173 */         bool = false;
/*     */       } else {
/*     */         
/* 176 */         this.rptSb.append((this.bundle == null) ? "<p>Check 1 - Passed</p>" : (this.bundle.getString("Check1_PASSED") + NEWLINE));
/*     */       } 
/*     */ 
/*     */       
/* 180 */       if (!checkSlots()) {
/* 181 */         this.rptSb.append((this.bundle == null) ? "<p>VARABR004 cannot Pass because there is a PSL but no PSLAVAIL, or there is a PSLAVAIL but no PSL, or the SLOTAVAILTYPE does not match.</p>" : (this.bundle.getString("Error_PSL") + NEWLINE));
/* 182 */         bool = false;
/*     */       } else {
/*     */         
/* 185 */         this.rptSb.append((this.bundle == null) ? "<p>Check 2 - Passed</p>" : (this.bundle.getString("Check2_PASSED") + NEWLINE));
/*     */       } 
/*     */ 
/*     */       
/* 189 */       if (!checkBays()) {
/* 190 */         this.rptSb.append((this.bundle == null) ? "<p>VARABR004 cannot Pass because there is a PBY but no PBYAVAIL, or there is a PBYAVAIL but no PBY, or the BAYAVAILTYPE does not match.</p>" : (this.bundle.getString("Error_PBY") + NEWLINE));
/* 191 */         bool = false;
/*     */       } else {
/*     */         
/* 194 */         this.rptSb.append((this.bundle == null) ? "<p>Check 3 - Passed</p>" : (this.bundle.getString("Check3_PASSED") + NEWLINE));
/*     */       } 
/*     */ 
/*     */       
/* 198 */       if (bool) {
/*     */         
/* 200 */         triggerWorkFlow("WFVARFINAL");
/*     */         
/* 202 */         setReturnCode(0);
/*     */         
/* 204 */         this.rptSb.append((this.bundle == null) ? "<p>All checks are valid.</p>" : (this.bundle.getString("OK_MSG") + NEWLINE));
/*     */       } else {
/* 206 */         setReturnCode(-1);
/*     */       }
/*     */     
/* 209 */     } catch (Exception exception) {
/* 210 */       StringWriter stringWriter = new StringWriter();
/*     */       
/* 212 */       String str3 = "<h3><font color=red>Error: {0}</font></h3>";
/* 213 */       String str4 = "<pre>{0}</pre>";
/* 214 */       messageFormat = new MessageFormat((this.bundle == null) ? str3 : this.bundle.getString("Error_EXCEPTION"));
/* 215 */       arrayOfString = new String[1];
/* 216 */       setReturnCode(-1);
/* 217 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 218 */       arrayOfString[0] = exception.getMessage();
/* 219 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 220 */       messageFormat = new MessageFormat((this.bundle == null) ? str4 : this.bundle.getString("Error_STACKTRACE"));
/* 221 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/* 222 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 223 */       logError("Exception: " + exception.getMessage());
/* 224 */       logError(stringWriter.getBuffer().toString());
/*     */     } finally {
/*     */       
/* 227 */       setDGTitle(str1);
/* 228 */       setDGRptName(getShortClassName(getClass()));
/* 229 */       setDGRptClass("WWABR");
/*     */       
/* 231 */       if (!isReadOnly()) {
/* 232 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 238 */     arrayOfString[0] = getShortClassName(getClass());
/* 239 */     arrayOfString[1] = str1 + ((getReturnCode() == 0) ? " Passed" : " Failed");
/* 240 */     arrayOfString[2] = getNow();
/* 241 */     arrayOfString[3] = this.m_prof.getOPName();
/* 242 */     arrayOfString[4] = this.m_prof.getRoleDescription();
/* 243 */     arrayOfString[5] = getDescription();
/* 244 */     arrayOfString[6] = getABRVersion();
/* 245 */     this.rptSb.insert(0, messageFormat.format(arrayOfString));
/* 246 */     this.rptSb.append("<!-- DEBUG: " + this.traceSb.toString() + " -->" + NEWLINE);
/*     */     
/* 248 */     println(this.rptSb.toString());
/* 249 */     printDGSubmitString();
/* 250 */     buildReportFooter();
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
/*     */   private boolean checkRelators() {
/* 265 */     boolean bool = true;
/*     */ 
/*     */     
/* 268 */     String[] arrayOfString1 = { "VARDD", "CTOVAR", "CPGVAR", "VARWAR", "SBBMB", "SBBPP" };
/*     */     
/* 270 */     String[] arrayOfString2 = { "DD", "CTO", "CPG", "WAR", "MB", "PP" };
/* 271 */     for (byte b = 0; b < arrayOfString2.length; b++) {
/* 272 */       EntityGroup entityGroup1 = this.m_elist.getEntityGroup(arrayOfString2[b]);
/* 273 */       EntityGroup entityGroup2 = this.m_elist.getEntityGroup(arrayOfString1[b]);
/* 274 */       if (entityGroup1 != null && entityGroup2 != null) {
/* 275 */         bool = (checkSingleRelator(entityGroup1, entityGroup2) && bool) ? true : false;
/*     */       } else {
/* 277 */         if (entityGroup1 == null) {
/* 278 */           this.traceSb.append(NEWLINE + "ERROR: " + arrayOfString2[b] + " EntityGroup not found");
/*     */         } else {
/* 280 */           this.traceSb.append(NEWLINE + "ERROR: " + arrayOfString1[b] + " Relator EntityGroup not found");
/*     */         } 
/* 282 */         bool = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 287 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SBB");
/* 288 */     if (entityGroup != null) {
/* 289 */       if (entityGroup.getEntityItemCount() == 0) {
/* 290 */         MessageFormat messageFormat = new MessageFormat((this.bundle == null) ? "<p>VARABR004 cannot pass because there is no &quot;{0}&quot; entity linked to the Variant.</p>" : this.bundle.getString("Error_MISSING"));
/* 291 */         String[] arrayOfString = new String[1];
/* 292 */         bool = false;
/* 293 */         arrayOfString[0] = entityGroup.getLongDescription();
/* 294 */         this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 295 */         this.traceSb.append(NEWLINE + "FAIL: " + entityGroup.getEntityType() + " is invalid, has " + entityGroup.getEntityItemCount() + " entities.");
/*     */       } else {
/* 297 */         this.traceSb.append(NEWLINE + "PASS: " + entityGroup.getEntityType() + " EntityGroup is valid (has 1 or more)");
/*     */       } 
/*     */     } else {
/* 300 */       this.traceSb.append(NEWLINE + "ERROR: SBB EntityGroup not found");
/* 301 */       bool = false;
/*     */     } 
/*     */     
/* 304 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkSingleRelator(EntityGroup paramEntityGroup1, EntityGroup paramEntityGroup2) {
/* 311 */     boolean bool = false;
/* 312 */     MessageFormat messageFormat = null;
/* 313 */     String[] arrayOfString = new String[1];
/*     */     
/* 315 */     switch (paramEntityGroup1.getEntityItemCount())
/*     */     { case 0:
/* 317 */         messageFormat = new MessageFormat((this.bundle == null) ? "<p>VARABR004 cannot pass because there is no &quot;{0}&quot; entity linked to the Variant.</p>" : this.bundle.getString("Error_MISSING"));
/* 318 */         arrayOfString[0] = paramEntityGroup1.getLongDescription();
/* 319 */         this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/* 320 */         this.traceSb.append(NEWLINE + "FAIL: " + paramEntityGroup1.getEntityType() + " is invalid, has " + paramEntityGroup1.getEntityItemCount() + " entities.");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 343 */         return bool;case 1: if (paramEntityGroup2.getEntityItemCount() == 1) { this.traceSb.append(NEWLINE + "PASS: " + paramEntityGroup1.getEntityType() + " EntityGroup is valid (has 1)"); bool = true; } else { messageFormat = new MessageFormat((this.bundle == null) ? "<p>VARABR004 cannot pass because there is more than one &quot;{0}&quot; relator linked to the Variant.</p>" : this.bundle.getString("Error_TOO_MANY_LINKS")); arrayOfString[0] = paramEntityGroup2.getLongDescription(); this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE); this.traceSb.append(NEWLINE + "FAIL: " + paramEntityGroup2.getEntityType() + " is invalid, has " + paramEntityGroup2.getEntityItemCount() + " entities."); }  return bool; }  messageFormat = new MessageFormat((this.bundle == null) ? "<p>VARABR004 cannot pass because there is more than one &quot;{0}&quot; entity linked to the Variant.</p>" : this.bundle.getString("Error_TOO_MANY")); arrayOfString[0] = paramEntityGroup1.getLongDescription(); this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE); this.traceSb.append(NEWLINE + "FAIL: " + paramEntityGroup1.getEntityType() + " is invalid, has " + paramEntityGroup1.getEntityItemCount() + " entities."); return bool;
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
/*     */   private boolean checkSlots() {
/* 360 */     String str1 = "SLOTAVAILTYPE";
/* 361 */     String str2 = "PSLAVAILABILITYA";
/* 362 */     String str3 = "PSL";
/* 363 */     boolean bool = false;
/* 364 */     this.traceSb.append(NEWLINE + NEWLINE + "****CheckSlots ******** ");
/*     */ 
/*     */ 
/*     */     
/* 368 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("SBBPSL");
/* 369 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("PSLAVAIL");
/* 370 */     if (entityGroup1 == null) {
/* 371 */       this.traceSb.append(NEWLINE + "ERROR: SBBPSL EntityGroup not found");
/*     */     
/*     */     }
/* 374 */     else if (entityGroup2 == null) {
/* 375 */       this.traceSb.append(NEWLINE + "ERROR: PSLAVAIL EntityGroup not found");
/*     */ 
/*     */     
/*     */     }
/* 379 */     else if (entityGroup1.getEntityItemCount() == 0 && entityGroup2
/* 380 */       .getEntityItemCount() == 0) {
/* 381 */       this.traceSb.append(NEWLINE + "PASS: No PSLAVAIL or SBBPSL pulled by extract.");
/* 382 */       bool = true;
/*     */     }
/*     */     else {
/*     */       
/* 386 */       bool = checkForAvail(entityGroup1, str2, str3, str1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 391 */       bool = (checkForRelator(entityGroup1
/* 392 */           .getEntityType(), entityGroup2, str2, str3, str1) && bool);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     return bool;
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
/*     */   private boolean checkBays() {
/* 418 */     boolean bool = false;
/* 419 */     String str1 = "BAYAVAILTYPE";
/* 420 */     String str2 = "PBYAVAILABILITYA";
/* 421 */     String str3 = "PBY";
/* 422 */     this.traceSb.append(NEWLINE + NEWLINE + "****CheckBays ******** ");
/*     */ 
/*     */ 
/*     */     
/* 426 */     EntityGroup entityGroup1 = this.m_elist.getEntityGroup("SBBPBY");
/* 427 */     EntityGroup entityGroup2 = this.m_elist.getEntityGroup("PBYAVAIL");
/* 428 */     if (entityGroup1 == null) {
/* 429 */       this.traceSb.append(NEWLINE + "ERROR: SBBPBY EntityGroup not found");
/*     */     
/*     */     }
/* 432 */     else if (entityGroup2 == null) {
/* 433 */       this.traceSb.append(NEWLINE + "ERROR: PBYAVAIL EntityGroup not found");
/*     */ 
/*     */     
/*     */     }
/* 437 */     else if (entityGroup1.getEntityItemCount() == 0 && entityGroup2
/* 438 */       .getEntityItemCount() == 0) {
/* 439 */       this.traceSb.append(NEWLINE + "PASS: No PBYAVAIL or SBBPBY pulled by extract.");
/* 440 */       bool = true;
/*     */     } else {
/*     */       
/* 443 */       bool = checkForAvail(entityGroup1, str2, str3, str1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 448 */       bool = (checkForRelator(entityGroup1
/* 449 */           .getEntityType(), entityGroup2, str2, str3, str1) && bool);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 458 */     return bool;
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
/*     */   private boolean checkForAvail(EntityGroup paramEntityGroup, String paramString1, String paramString2, String paramString3) {
/* 478 */     boolean bool = true;
/*     */     
/* 480 */     this.traceSb.append(NEWLINE + "checkForAvail: Checking " + paramString2 + " pulled thru " + paramEntityGroup
/*     */ 
/*     */ 
/*     */         
/* 484 */         .getEntityType() + " for Association to " + paramString1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     byte b = 0;
/* 490 */     for (; b < paramEntityGroup.getEntityItemCount(); 
/* 491 */       b++) {
/*     */       
/* 493 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 494 */       this.traceSb.append(NEWLINE + "Relator " + entityItem.getEntityType() + ":" + entityItem.getEntityID());
/* 495 */       for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 496 */         EANEntity eANEntity = entityItem.getDownLink(b1);
/*     */         
/* 498 */         if (eANEntity.getEntityType().equals(paramString2)) {
/* 499 */           boolean bool1 = false;
/* 500 */           this.traceSb.append(NEWLINE + "  Entity " + eANEntity.getEntityType() + ":" + eANEntity.getEntityID());
/* 501 */           this.traceSb.append(" " + paramString3 + ": " + getAttributeValue(eANEntity.getEntityType(), eANEntity.getEntityID(), paramString3));
/*     */           
/* 503 */           for (byte b2 = 0; b2 < eANEntity.getUpLinkCount(); b2++) {
/* 504 */             EANEntity eANEntity1 = eANEntity.getUpLink(b2);
/*     */             
/* 506 */             if (eANEntity1.getEntityType().equals(paramString1)) {
/* 507 */               this.traceSb.append(NEWLINE + "    uplink (assoc) " + eANEntity1.getEntityType() + ":" + eANEntity1.getEntityID());
/*     */ 
/*     */ 
/*     */               
/* 511 */               bool1 = true;
/* 512 */               byte b3 = 0;
/* 513 */               for (; b3 < eANEntity1.getUpLinkCount(); 
/* 514 */                 b3++) {
/*     */ 
/*     */                 
/* 517 */                 EANEntity eANEntity2 = eANEntity1.getUpLink(b3);
/* 518 */                 this.traceSb.append(NEWLINE + "      uplink (entity) " + eANEntity2.getEntityType() + ":" + eANEntity2.getEntityID());
/* 519 */                 this.traceSb.append(" " + paramString3 + ": " + getAttributeValue(eANEntity2.getEntityType(), eANEntity2.getEntityID(), paramString3));
/* 520 */                 this.traceSb.append(NEWLINE + "PASS: " + eANEntity
/*     */                     
/* 522 */                     .getEntityType() + ":" + eANEntity
/*     */                     
/* 524 */                     .getEntityID() + " was linked thru " + entityItem
/*     */                     
/* 526 */                     .getEntityType() + ":" + entityItem
/* 527 */                     .getEntityID() + " AND was associated thru " + eANEntity1
/*     */                     
/* 529 */                     .getEntityType() + ":" + eANEntity1
/*     */                     
/* 531 */                     .getEntityID() + " to " + eANEntity2
/*     */                     
/* 533 */                     .getEntityType() + ":" + eANEntity2
/*     */                     
/* 535 */                     .getEntityID());
/*     */               } 
/*     */             } 
/*     */           } 
/* 539 */           if (!bool1) {
/* 540 */             bool = false;
/* 541 */             this.traceSb.append(NEWLINE + "FAIL: " + eANEntity
/*     */                 
/* 543 */                 .getEntityType() + ":" + eANEntity
/*     */                 
/* 545 */                 .getEntityID() + " " + paramString3 + ": " + 
/*     */ 
/*     */ 
/*     */                 
/* 549 */                 getAttributeValue(eANEntity.getEntityType(), eANEntity.getEntityID(), paramString3) + " was linked thru " + entityItem
/*     */                 
/* 551 */                 .getEntityType() + ":" + entityItem
/*     */                 
/* 553 */                 .getEntityID() + " BUT was not associated to " + paramString1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 563 */     return bool;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkForRelator(String paramString1, EntityGroup paramEntityGroup, String paramString2, String paramString3, String paramString4) {
/* 586 */     boolean bool = true;
/*     */     
/* 588 */     String str = paramEntityGroup.getEntityType();
/* 589 */     this.traceSb.append("checkForRelator: Checking " + str + " thru Association " + paramString2 + " for " + paramString3 + " linked thru " + paramString1);
/*     */     
/* 591 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*     */       
/* 593 */       boolean bool1 = false;
/* 594 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 595 */       this.traceSb.append(NEWLINE + "Entity " + entityItem.getEntityType() + ":" + entityItem.getEntityID());
/* 596 */       this.traceSb.append(" " + paramString4 + ": " + getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), paramString4));
/*     */ 
/*     */ 
/*     */       
/* 600 */       for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 601 */         EANEntity eANEntity = entityItem.getDownLink(b1);
/* 602 */         if (eANEntity.getEntityType().equals(paramString2)) {
/* 603 */           this.traceSb.append(NEWLINE + "  downLink (assoc) " + eANEntity.getEntityType() + ":" + eANEntity.getEntityID());
/*     */           
/* 605 */           for (byte b2 = 0; b2 < eANEntity.getDownLinkCount(); b2++) {
/* 606 */             EANEntity eANEntity1 = eANEntity.getDownLink(b2);
/* 607 */             if (eANEntity1.getEntityType().equals(paramString3)) {
/* 608 */               boolean bool2 = false;
/*     */               
/* 610 */               this.traceSb.append(NEWLINE + "    downlink (entity) " + eANEntity1.getEntityType() + ":" + eANEntity1.getEntityID());
/* 611 */               this.traceSb.append(" " + paramString4 + ": " + getAttributeValue(eANEntity1.getEntityType(), eANEntity1.getEntityID(), paramString4));
/*     */               
/* 613 */               for (byte b3 = 0; b3 < eANEntity1.getUpLinkCount(); b3++) {
/* 614 */                 EANEntity eANEntity2 = eANEntity1.getUpLink(b3);
/* 615 */                 if (eANEntity2
/* 616 */                   .getEntityType()
/* 617 */                   .equals(paramString1)) {
/* 618 */                   this.traceSb.append(NEWLINE + "      uplink (relator?) " + eANEntity2.getEntityType() + ":" + eANEntity2.getEntityID());
/* 619 */                   bool2 = true;
/* 620 */                   bool1 = true;
/* 621 */                   this.traceSb.append(NEWLINE + "PASS: " + eANEntity1
/*     */                       
/* 623 */                       .getEntityType() + ":" + eANEntity1
/*     */                       
/* 625 */                       .getEntityID() + " was associated thru " + eANEntity
/*     */                       
/* 627 */                       .getEntityType() + ":" + eANEntity
/*     */                       
/* 629 */                       .getEntityID() + " to " + entityItem
/*     */                       
/* 631 */                       .getEntityType() + ":" + entityItem
/*     */                       
/* 633 */                       .getEntityID() + " AND was linked to " + eANEntity2
/*     */                       
/* 635 */                       .getEntityType() + ":" + eANEntity2
/*     */                       
/* 637 */                       .getEntityID());
/*     */                 } 
/*     */               } 
/* 640 */               if (!bool2) {
/* 641 */                 this.traceSb.append(NEWLINE + "INFO: " + eANEntity1
/*     */                     
/* 643 */                     .getEntityType() + ":" + eANEntity1
/*     */                     
/* 645 */                     .getEntityID() + " was associated thru " + eANEntity
/*     */                     
/* 647 */                     .getEntityType() + ":" + eANEntity
/*     */                     
/* 649 */                     .getEntityID() + " to " + entityItem
/*     */                     
/* 651 */                     .getEntityType() + ":" + entityItem
/*     */                     
/* 653 */                     .getEntityID() + " BUT was not linked to " + paramString1);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 661 */       if (!bool1) {
/*     */         
/* 663 */         bool = false;
/* 664 */         this.traceSb.append(NEWLINE + "FAIL: NO " + paramString1 + " links found thru " + paramString2 + " for " + entityItem
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 670 */             .getEntityType() + ":" + entityItem
/*     */             
/* 672 */             .getEntityID() + " " + paramString4 + ": " + 
/*     */ 
/*     */ 
/*     */             
/* 676 */             getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), paramString4));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 681 */     return bool;
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
/*     */   private void triggerWorkFlow(String paramString) throws SQLException, MiddlewareException, WorkflowException, MiddlewareShutdownInProgressException {
/* 700 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 701 */     EntityItem[] arrayOfEntityItem = new EntityItem[1];
/* 702 */     WorkflowActionItem workflowActionItem = new WorkflowActionItem(null, this.m_db, this.m_prof, paramString);
/* 703 */     arrayOfEntityItem[0] = entityGroup.getEntityItem(0);
/* 704 */     workflowActionItem.setEntityItems(arrayOfEntityItem);
/* 705 */     this.m_db.executeAction(this.m_prof, workflowActionItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 715 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 717 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/* 718 */     EANList eANList = entityGroup.getMetaAttribute();
/*     */     
/* 720 */     for (byte b = 0; b < eANList.size(); b++) {
/* 721 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 722 */       stringBuffer.append(
/* 723 */           getAttributeValue(
/* 724 */             getRootEntityType(), 
/* 725 */             getRootEntityID(), eANMetaAttribute
/* 726 */             .getAttributeCode()));
/* 727 */       stringBuffer.append(" ");
/*     */     } 
/*     */     
/* 730 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 739 */     String str = "Variant Required Element Verification.";
/* 740 */     if (this.bundle != null) {
/* 741 */       str = this.bundle.getString("DESCRIPTION");
/*     */     }
/*     */     
/* 744 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 753 */     return "$Revision: 1.8 $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Locale getLocale(int paramInt) {
/* 762 */     Locale locale = null;
/* 763 */     switch (paramInt)
/*     */     { case 1:
/* 765 */         locale = Locale.US;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 788 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\VARABR004.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */