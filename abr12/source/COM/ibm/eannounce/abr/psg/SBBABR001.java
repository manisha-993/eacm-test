/*     */ package COM.ibm.eannounce.abr.psg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EANDataFoundation;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANList;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import COM.ibm.eannounce.objects.MetaSingleFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.SingleFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.WorkflowActionItem;
/*     */ import COM.ibm.eannounce.objects.WorkflowException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.rmi.RemoteException;
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
/*     */ public class SBBABR001
/*     */   extends PokBaseABR
/*     */ {
/*  53 */   private Object[] mfParms = (Object[])new String[10];
/*  54 */   private ResourceBundle bundle = null;
/*  55 */   private StringBuffer rptSb = new StringBuffer();
/*  56 */   private StringBuffer traceSb = new StringBuffer();
/*     */   
/*     */   private static final double SCREENSIZENOM_IN_VALUE = 15.0D;
/*     */   
/*     */   private static final String CAFEE_Y1 = "0010";
/*     */   
/*     */   private static final String CAFEE_Y2 = "0020";
/*     */   
/*     */   private static final String DESCRIPTION = "SBB California Recycling Fee Calculation.<br/>This ABR calculates the California Recycling Fee based on Monitor Size.";
/*     */   
/*     */   private static final String SET_MSG = "<p>Set &quot;{0}&quot; to {1}.</p>";
/*     */   
/*     */   private static final String SIZE_MSG = "<p>&quot;{0}&quot; is {1}.</p>";
/*     */   
/*     */   private static final String NO_MON_MSG = "<p>ABR passed, but no {0} exists.</p>";
/*     */   
/*     */   private static final String ALREADYSET_MSG = "<p>&quot;{0}&quot; was already set to {1}.</p>";
/*     */   
/*     */   private static final String NULL_SCR = "<p>SBB cannot go Final, &quot;{0}&quot; is not populated.</p>";
/*     */   
/*     */   private static final String TOO_MANY = "<p>SBB cannot go Final, more than one {0} exists.</p>";
/*     */   
/*     */   private static final String SBBSTATUS = "<p>SBBABR001 cannot Pass because Status is not Ready for Review.</p>";
/*     */   private static final String CODE_ERROR = "<p>Error: did not find {0} in {1}.</p>";
/*     */   private static final String FLAG_ERROR = "<p>Error: {0} is not a MetaSingleFlagAttribute.</p>";
/*     */   private static final String VALUE_ERROR = "<p>Error: did not find a {0} code/value in the {1} attribute.</p>";
/*  82 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  83 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  98 */     String str1 = "<html><head><title>{0} {1}</title></head><body>" + NEWLINE + "<h1>{0} {1}</h1><p><b>Date: </b>{2}<br/><b>User: </b>{3} ({4})<br /><b>Description: </b>{5}<br /><b>SBB: </b>{6}</p>" + NEWLINE + "<!-- {7} -->";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     String str2 = "";
/* 106 */     String str3 = "";
/* 107 */     MessageFormat messageFormat = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 113 */       start_ABRBuild();
/*     */ 
/*     */       
/* 116 */       this.rptSb.append("<!-- DEBUG: SBBABR001 entered for " + 
/*     */           
/* 118 */           getEntityType() + ":" + 
/*     */           
/* 120 */           getEntityID() + NEWLINE + 
/*     */           
/* 122 */           outputList(this.m_elist) + " -->" + NEWLINE);
/*     */ 
/*     */ 
/*     */       
/* 126 */       this
/* 127 */         .bundle = ResourceBundle.getBundle(
/* 128 */           getClass().getName(), 
/* 129 */           getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*     */ 
/*     */       
/* 132 */       if (this.m_elist.getEntityGroupCount() == 0) {
/* 133 */         throw new Exception("EntityList did not have any groups. Verify that extract is defined.");
/*     */       }
/*     */       
/* 136 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */       
/* 139 */       str3 = getAttributeValue(entityItem
/* 140 */           .getEntityType(), entityItem
/* 141 */           .getEntityID(), "SBBPNUMB", "");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 147 */       String str = getAttributeFlagEnabledValue(
/* 148 */           getRootEntityType(), 
/* 149 */           getRootEntityID(), "SBBSTATUS", "");
/*     */ 
/*     */       
/* 152 */       boolean bool = str.equals("0040");
/* 153 */       if (!bool) {
/*     */         
/* 155 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("MON");
/* 156 */         this.rptSb.append((this.bundle == null) ? "<p>SBBABR001 cannot Pass because Status is not Ready for Review.</p>" : (this.bundle
/*     */ 
/*     */             
/* 159 */             .getString("Error_SBBSTATUS") + NEWLINE));
/* 160 */         if (entityGroup.getEntityItemCount() > 1) {
/* 161 */           this.mfParms[0] = entityGroup.getLongDescription();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 167 */           messageFormat = new MessageFormat((this.bundle == null) ? "<p>SBB cannot go Final, more than one {0} exists.</p>" : this.bundle.getString("Error_TOO_MANY"));
/* 168 */           this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/*     */         } 
/*     */       } else {
/* 171 */         bool = setCARecycleFee(entityItem);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 176 */       str2 = getNavigationName();
/*     */       
/* 178 */       if (bool) {
/*     */ 
/*     */         
/* 181 */         triggerWorkFlow(entityItem, "WFSBBSTATUS");
/*     */         
/* 183 */         EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("SBBSTATUS");
/* 184 */         this.mfParms[0] = eANMetaAttribute.getLongDescription();
/* 185 */         this.mfParms[1] = "Final";
/*     */ 
/*     */         
/* 188 */         messageFormat = new MessageFormat((this.bundle == null) ? "<p>Set &quot;{0}&quot; to {1}.</p>" : this.bundle.getString("SET_MSG"));
/* 189 */         this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/*     */         
/* 191 */         setReturnCode(0);
/*     */       } else {
/* 193 */         setReturnCode(-1);
/*     */       } 
/* 195 */     } catch (Throwable throwable) {
/* 196 */       String str4 = "<h3><font color=red>Error: {0}</font></h3>";
/*     */       
/* 198 */       String str5 = "<pre>{0}</pre>";
/* 199 */       StringWriter stringWriter = new StringWriter();
/* 200 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 206 */       messageFormat = new MessageFormat((this.bundle == null) ? str4 : this.bundle.getString("Error_EXCEPTION"));
/* 207 */       this.mfParms[0] = throwable.getMessage();
/* 208 */       this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 213 */       messageFormat = new MessageFormat((this.bundle == null) ? str5 : this.bundle.getString("Error_STACKTRACE"));
/* 214 */       this.mfParms[0] = stringWriter.getBuffer().toString();
/* 215 */       this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/* 216 */       logError("Exception: " + throwable.getMessage());
/* 217 */       logError(stringWriter.getBuffer().toString());
/* 218 */       setReturnCode(-1);
/*     */     } finally {
/* 220 */       setDGTitle(str2);
/* 221 */       setDGRptName(getShortClassName(getClass()));
/* 222 */       setDGRptClass("SBBABR001");
/*     */       
/* 224 */       if (!isReadOnly()) {
/* 225 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     messageFormat = new MessageFormat((this.bundle == null) ? str1 : this.bundle.getString("HEADER"));
/* 233 */     this.mfParms[0] = getShortClassName(getClass());
/* 234 */     this.mfParms[1] = str2 + (
/* 235 */       (getReturnCode() == 0) ? " Passed" : " Failed");
/* 236 */     this.mfParms[2] = getNow();
/* 237 */     this.mfParms[3] = this.m_prof.getOPName();
/* 238 */     this.mfParms[4] = this.m_prof.getRoleDescription();
/* 239 */     this.mfParms[5] = getDescription();
/* 240 */     this.mfParms[6] = str3;
/* 241 */     this.mfParms[7] = getABRVersion();
/* 242 */     this.rptSb.insert(0, messageFormat.format(this.mfParms) + NEWLINE);
/* 243 */     this.rptSb.append("<!-- DEBUG: " + this.traceSb.toString() + " -->");
/* 244 */     println(this.rptSb.toString());
/* 245 */     printDGSubmitString();
/* 246 */     buildReportFooter();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean setCARecycleFee(EntityItem paramEntityItem) throws MiddlewareRequestException, EANBusinessRuleException, MiddlewareBusinessRuleException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, SQLException {
/* 284 */     boolean bool = false;
/* 285 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("MON");
/*     */     
/* 287 */     String str = "CAFEE";
/* 288 */     MessageFormat messageFormat = null;
/*     */     
/* 290 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(str);
/* 291 */     if (eANMetaAttribute == null || !(eANMetaAttribute instanceof MetaSingleFlagAttribute))
/* 292 */     { this.mfParms[0] = str;
/* 293 */       if (eANMetaAttribute == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 298 */         messageFormat = new MessageFormat((this.bundle == null) ? "<p>Error: did not find {0} in {1}.</p>" : this.bundle.getString("Error_CODE"));
/* 299 */         this.mfParms[1] = paramEntityItem.getLongDescription();
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 305 */         messageFormat = new MessageFormat((this.bundle == null) ? "<p>Error: {0} is not a MetaSingleFlagAttribute.</p>" : this.bundle.getString("Error_FLAG"));
/*     */       } 
/* 307 */       this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE); }
/*     */     else
/*     */     
/* 310 */     { EntityGroup entityGroup1 = this.m_elist.getEntityGroup("SBBMON");
/* 311 */       if (entityGroup1.getEntityItemCount() > 1)
/* 312 */       { deactivateUniqueFlag(paramEntityItem, eANMetaAttribute);
/* 313 */         this.mfParms[0] = entityGroup.getLongDescription();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 319 */         messageFormat = new MessageFormat((this.bundle == null) ? "<p>SBB cannot go Final, more than one {0} exists.</p>" : this.bundle.getString("Error_TOO_MANY"));
/* 320 */         this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/* 321 */         bool = false; }
/*     */       else
/* 323 */       { switch (entityGroup.getEntityItemCount())
/*     */         { case 0:
/* 325 */             deactivateUniqueFlag(paramEntityItem, eANMetaAttribute);
/* 326 */             this.mfParms[0] = entityGroup.getLongDescription();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 332 */             messageFormat = new MessageFormat((this.bundle == null) ? "<p>ABR passed, but no {0} exists.</p>" : this.bundle.getString("NO_MON_MSG"));
/* 333 */             this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/* 334 */             bool = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 358 */             return bool;case 1: bool = setCARecycleFee(paramEntityItem, eANMetaAttribute, entityGroup.getEntityItem(0)); return bool; }  deactivateUniqueFlag(paramEntityItem, eANMetaAttribute); this.mfParms[0] = entityGroup.getLongDescription(); messageFormat = new MessageFormat((this.bundle == null) ? "<p>SBB cannot go Final, more than one {0} exists.</p>" : this.bundle.getString("Error_TOO_MANY")); this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE); bool = false; }  }  return bool;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean setCARecycleFee(EntityItem paramEntityItem1, EANMetaAttribute paramEANMetaAttribute, EntityItem paramEntityItem2) throws MiddlewareRequestException, EANBusinessRuleException, MiddlewareBusinessRuleException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, SQLException {
/* 384 */     boolean bool = false;
/*     */     
/* 386 */     String str = getAttributeValue(paramEntityItem2
/* 387 */         .getEntityType(), paramEntityItem2
/* 388 */         .getEntityID(), "SCREENSIZENOM_IN", null);
/*     */ 
/*     */ 
/*     */     
/* 392 */     EANMetaAttribute eANMetaAttribute = paramEntityItem2.getEntityGroup().getMetaAttribute("SCREENSIZENOM_IN");
/*     */ 
/*     */     
/* 395 */     MessageFormat messageFormat = new MessageFormat((this.bundle == null) ? "<p>&quot;{0}&quot; is {1}.</p>" : this.bundle.getString("SIZE_MSG"));
/* 396 */     this.mfParms[0] = eANMetaAttribute.getActualLongDescription();
/* 397 */     this.mfParms[1] = (str == null) ? "null" : str;
/* 398 */     this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/*     */     
/* 400 */     if (str == null) {
/*     */       
/* 402 */       this.traceSb.append("Setting CAFEE to null because SCREENSIZENOM_IN was null for " + paramEntityItem2
/*     */           
/* 404 */           .getKey() + NEWLINE);
/*     */       
/* 406 */       deactivateUniqueFlag(paramEntityItem1, paramEANMetaAttribute);
/*     */ 
/*     */       
/* 409 */       this.mfParms[0] = eANMetaAttribute.getActualLongDescription();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 414 */       messageFormat = new MessageFormat((this.bundle == null) ? "<p>SBB cannot go Final, &quot;{0}&quot; is not populated.</p>" : this.bundle.getString("Error_NULL_SCR"));
/* 415 */       this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/*     */     } else {
/* 417 */       double d = Double.parseDouble(str);
/* 418 */       String str1 = "0010";
/* 419 */       if (d >= 15.0D) {
/* 420 */         str1 = "0020";
/*     */       }
/*     */       
/* 423 */       this.traceSb.append("SCREENSIZENOM_IN was " + d + " for " + paramEntityItem2
/*     */ 
/*     */ 
/*     */           
/* 427 */           .getKey() + NEWLINE);
/*     */ 
/*     */       
/* 430 */       bool = setUniqueFlag(paramEntityItem1, paramEANMetaAttribute, str1);
/*     */     } 
/* 432 */     return bool;
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
/*     */   private void deactivateUniqueFlag(EntityItem paramEntityItem, EANMetaAttribute paramEANMetaAttribute) throws MiddlewareRequestException, EANBusinessRuleException, MiddlewareBusinessRuleException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, SQLException {
/* 449 */     String str1 = paramEANMetaAttribute.getAttributeCode();
/*     */     
/* 451 */     String str2 = getAttributeValue(paramEntityItem
/* 452 */         .getEntityType(), paramEntityItem
/* 453 */         .getEntityID(), str1, null);
/*     */ 
/*     */     
/* 456 */     this.traceSb.append("deactivateUniqueFlag: Setting " + str1 + " to null for " + paramEntityItem
/*     */ 
/*     */ 
/*     */         
/* 460 */         .getKey() + NEWLINE);
/*     */     
/* 462 */     this.traceSb.append("deactivateUniqueFlag: " + str1 + " current value " + str2 + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 469 */     if (str2 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 476 */       MessageFormat messageFormat = new MessageFormat((this.bundle == null) ? "<p>Set &quot;{0}&quot; to {1}.</p>" : this.bundle.getString("SET_MSG"));
/*     */ 
/*     */       
/* 479 */       paramEntityItem.put(paramEntityItem.getEntityType() + ":" + str1, null);
/* 480 */       paramEntityItem.commit(this.m_db, null);
/*     */       
/* 482 */       this.mfParms[0] = paramEANMetaAttribute.getLongDescription();
/* 483 */       this.mfParms[1] = "null";
/*     */       
/* 485 */       this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean setUniqueFlag(EntityItem paramEntityItem, EANMetaAttribute paramEANMetaAttribute, String paramString) throws MiddlewareRequestException, EANBusinessRuleException, MiddlewareBusinessRuleException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, SQLException {
/* 507 */     boolean bool = false;
/* 508 */     String str1 = paramEANMetaAttribute.getAttributeCode();
/*     */     
/* 510 */     String str2 = getAttributeValue(paramEntityItem
/* 511 */         .getEntityType(), paramEntityItem
/* 512 */         .getEntityID(), str1, null);
/*     */ 
/*     */ 
/*     */     
/* 516 */     String str3 = getAttributeFlagEnabledValue(paramEntityItem
/* 517 */         .getEntityType(), paramEntityItem
/* 518 */         .getEntityID(), str1, "");
/*     */ 
/*     */     
/* 521 */     this.traceSb.append("setUniqueFlag: Setting " + str1 + " to " + paramString + " for " + paramEntityItem
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 527 */         .getKey() + NEWLINE);
/*     */     
/* 529 */     this.traceSb.append("setUniqueFlag: " + str1 + " current value " + str2 + " curFlag: " + str3 + NEWLINE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 537 */     if (str3.equals(paramString)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 542 */       MessageFormat messageFormat = new MessageFormat((this.bundle == null) ? "<p>&quot;{0}&quot; was already set to {1}.</p>" : this.bundle.getString("ALREADYSET_MSG"));
/* 543 */       this.mfParms[0] = paramEANMetaAttribute.getLongDescription();
/* 544 */       this.mfParms[1] = str2;
/* 545 */       this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/* 546 */       bool = true;
/*     */     } else {
/*     */       SingleFlagAttribute singleFlagAttribute;
/*     */       
/* 550 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(str1);
/* 551 */       if (eANFlagAttribute == null) {
/* 552 */         singleFlagAttribute = new SingleFlagAttribute((EANDataFoundation)paramEntityItem, this.m_prof, (MetaSingleFlagAttribute)paramEANMetaAttribute);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 557 */         paramEntityItem.putAttribute((EANAttribute)singleFlagAttribute);
/*     */       } 
/*     */       
/* 560 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])singleFlagAttribute.get();
/* 561 */       byte b = 0;
/* 562 */       for (; b < arrayOfMetaFlag.length; 
/* 563 */         b++)
/*     */       {
/* 565 */         arrayOfMetaFlag[b].setSelected(false);
/*     */       }
/*     */       
/* 568 */       for (b = 0; b < arrayOfMetaFlag.length; b++) {
/* 569 */         if (arrayOfMetaFlag[b].getFlagCode().equals(paramString)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 574 */           MessageFormat messageFormat = new MessageFormat((this.bundle == null) ? "<p>Set &quot;{0}&quot; to {1}.</p>" : this.bundle.getString("SET_MSG"));
/* 575 */           arrayOfMetaFlag[b].setSelected(true);
/* 576 */           singleFlagAttribute.put(arrayOfMetaFlag);
/* 577 */           paramEntityItem.commit(this.m_db, null);
/*     */           
/* 579 */           this.mfParms[0] = paramEANMetaAttribute.getLongDescription();
/* 580 */           this.mfParms[1] = arrayOfMetaFlag[b].getLongDescription();
/* 581 */           this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/* 582 */           bool = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 587 */       if (!bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 593 */         MessageFormat messageFormat = new MessageFormat((this.bundle == null) ? "<p>Error: did not find a {0} code/value in the {1} attribute.</p>" : this.bundle.getString("Error_VALUE"));
/* 594 */         this.mfParms[0] = paramString;
/* 595 */         this.mfParms[1] = paramEANMetaAttribute.getLongDescription();
/* 596 */         this.rptSb.append(messageFormat.format(this.mfParms) + NEWLINE);
/*     */       } 
/*     */     } 
/* 599 */     return bool;
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
/*     */   private void triggerWorkFlow(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, WorkflowException, MiddlewareShutdownInProgressException {
/* 619 */     EntityItem[] arrayOfEntityItem = new EntityItem[1];
/* 620 */     arrayOfEntityItem[0] = paramEntityItem;
/* 621 */     WorkflowActionItem workflowActionItem = new WorkflowActionItem(null, this.m_db, this.m_prof, paramString);
/* 622 */     workflowActionItem.setEntityItems(arrayOfEntityItem);
/* 623 */     this.m_db.executeAction(this.m_prof, workflowActionItem);
/* 624 */     arrayOfEntityItem[0] = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 634 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 641 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/*     */     
/* 643 */     EANList eANList = entityGroup.getMetaAttribute();
/*     */     
/* 645 */     for (byte b = 0; b < eANList.size(); b++) {
/* 646 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 647 */       stringBuffer.append(
/* 648 */           getAttributeValue(
/* 649 */             getRootEntityType(), 
/* 650 */             getRootEntityID(), eANMetaAttribute
/* 651 */             .getAttributeCode()));
/* 652 */       stringBuffer.append(" ");
/*     */     } 
/*     */     
/* 655 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 664 */     String str = "SBB California Recycling Fee Calculation.<br/>This ABR calculates the California Recycling Fee based on Monitor Size.";
/* 665 */     if (this.bundle != null) {
/* 666 */       str = this.bundle.getString("DESCRIPTION");
/*     */     }
/* 668 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 677 */     return "$Revision: 1.5 $";
/*     */   }
/*     */   private Locale getLocale(int paramInt) {
/* 680 */     Locale locale = null;
/* 681 */     switch (paramInt)
/*     */     { case 1:
/* 683 */         locale = Locale.US;
/*     */       case 2:
/* 685 */         locale = Locale.GERMAN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 705 */         return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String outputList(EntityList paramEntityList) {
/* 714 */     StringBuffer stringBuffer = new StringBuffer();
/* 715 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 716 */     if (entityGroup != null) {
/* 717 */       stringBuffer.append(entityGroup
/* 718 */           .getEntityType() + " : " + entityGroup
/*     */           
/* 720 */           .getEntityItemCount() + " Parent entity items. ");
/*     */       
/* 722 */       if (entityGroup.getEntityItemCount() > 0) {
/* 723 */         stringBuffer.append("IDs(");
/* 724 */         for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/* 725 */           stringBuffer.append(" " + entityGroup.getEntityItem(b1).getEntityID());
/*     */         }
/* 727 */         stringBuffer.append(")");
/*     */       } 
/* 729 */       stringBuffer.append(NEWLINE);
/*     */     } 
/*     */     
/* 732 */     for (byte b = 0; b < paramEntityList.getEntityGroupCount(); b++) {
/* 733 */       EntityGroup entityGroup1 = paramEntityList.getEntityGroup(b);
/* 734 */       stringBuffer.append(entityGroup1
/* 735 */           .getEntityType() + " : " + entityGroup1
/*     */           
/* 737 */           .getEntityItemCount() + " entity items. ");
/*     */       
/* 739 */       if (entityGroup1.getEntityItemCount() > 0) {
/* 740 */         stringBuffer.append("IDs(");
/* 741 */         for (byte b1 = 0; b1 < entityGroup1.getEntityItemCount(); b1++) {
/* 742 */           stringBuffer.append(" " + entityGroup1.getEntityItem(b1).getEntityID());
/*     */         }
/* 744 */         stringBuffer.append(")");
/*     */       } 
/* 746 */       stringBuffer.append(NEWLINE);
/*     */     } 
/* 748 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\SBBABR001.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */