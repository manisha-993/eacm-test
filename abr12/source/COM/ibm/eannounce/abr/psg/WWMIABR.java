/*      */ package COM.ibm.eannounce.abr.psg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*      */ import COM.ibm.opicmpdh.objects.Text;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Locale;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WWMIABR
/*      */   extends PokBaseABR
/*      */ {
/*   81 */   private ResourceBundle bundle = null;
/*      */   
/*   83 */   private StringBuffer rptSb = new StringBuffer();
/*   84 */   private StringBuffer traceSb = new StringBuffer();
/*      */   
/*      */   private static final String PR_BRANDCODE_CDT = "10010";
/*      */   private static final String PR_BRANDCODE_INTELLISTATION = "10011";
/*      */   private static final String PR_BRANDCODE_NETFINITY = "10015";
/*      */   private static final String PR_BRANDCODE_MOBILE = "10014";
/*      */   private static final String OF_OFFERINGTYPE_SYSTEM = "0080";
/*      */   private static final String FAM_FAMILYNAME_THINKPAD = "0330";
/*      */   private static final String FAM_FAMILYNAME_WORKPAD = "0340";
/*   93 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*   94 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int CHAR_LIMIT = 15;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int WWMI_LIMIT = 60;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int CONTRCTINVTITLE_LIMIT = 28;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  130 */     String str1 = "<html><head><title>{0} {1}</title></head>" + NEWLINE + "<body><h1>{0}: {1}</h1>" + NEWLINE + "<p><b>Date: </b>{2}<br/>" + NEWLINE + "<b>User: </b>{3} ({4})<br />" + NEWLINE + "<b>Description: </b>{5}</p>" + NEWLINE + "<!-- {6} -->";
/*      */ 
/*      */     
/*  133 */     String str2 = "";
/*  134 */     MessageFormat messageFormat = null;
/*  135 */     String[] arrayOfString = new String[10];
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  140 */       start_ABRBuild();
/*      */       
/*  142 */       this.bundle = ResourceBundle.getBundle(getClass().getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  144 */       this.traceSb.append("WWMIABR entered for " + getEntityType() + ":" + getEntityID());
/*      */       
/*  146 */       this.traceSb.append(NEWLINE + "EntityList contains the following entities: ");
/*  147 */       for (byte b = 0; b < this.m_elist.getEntityGroupCount(); b++) {
/*      */         
/*  149 */         EntityGroup entityGroup = this.m_elist.getEntityGroup(b);
/*  150 */         this.traceSb.append(NEWLINE + "" + entityGroup.getEntityType() + " : " + entityGroup.getEntityItemCount() + " entity items. ");
/*  151 */         if (entityGroup.getEntityItemCount() > 0) {
/*      */           
/*  153 */           this.traceSb.append("IDs(");
/*  154 */           for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/*  155 */             this.traceSb.append(" " + entityGroup.getEntityItem(b1).getEntityID());
/*      */           }
/*  157 */           this.traceSb.append(")");
/*      */         } 
/*      */       } 
/*  160 */       this.traceSb.append(NEWLINE + "");
/*      */ 
/*      */       
/*  163 */       str2 = getNavigationName();
/*      */       
/*  165 */       DerivedString derivedString1 = new DerivedString(60, "WWMI:");
/*  166 */       DerivedString derivedString2 = new DerivedString(28, "CONTRCTINVTITLE:");
/*      */       
/*  168 */       boolean bool = deriveWWMIandCONTRCTINVTITLE(derivedString1, derivedString2);
/*  169 */       if (bool) {
/*      */ 
/*      */ 
/*      */         
/*  173 */         if (derivedString1.getCurLen() > 0) {
/*      */           
/*  175 */           setTextDirectly(getEntityType() + "WWMI", derivedString1.toString().trim());
/*      */         }
/*      */         else {
/*      */           
/*  179 */           EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*  180 */           EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(getEntityType() + "WWMI");
/*  181 */           messageFormat = new MessageFormat(this.bundle.getString("Error_DERIVE_MSG"));
/*  182 */           arrayOfString[0] = eANMetaAttribute.getActualLongDescription();
/*  183 */           this.rptSb.append(messageFormat.format(arrayOfString));
/*  184 */           this.traceSb.append(NEWLINE + "" + getEntityType() + "WWMI derivation failed. String was empty!");
/*      */         } 
/*  186 */         if (derivedString2.getCurLen() > 0) {
/*      */           
/*  188 */           setTextDirectly(getEntityType() + "CONTRCTINVTITLE", derivedString2.toString().trim());
/*      */         }
/*      */         else {
/*      */           
/*  192 */           EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*  193 */           EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(getEntityType() + "CONTRCTINVTITLE");
/*  194 */           messageFormat = new MessageFormat(this.bundle.getString("Error_DERIVE_MSG"));
/*  195 */           arrayOfString[0] = eANMetaAttribute.getActualLongDescription();
/*  196 */           this.rptSb.append(messageFormat.format(arrayOfString));
/*  197 */           this.traceSb.append(NEWLINE + "" + getEntityType() + "CONTRCTINVTITLE derivation failed. String was empty!");
/*      */         } 
/*      */         
/*  200 */         if (derivedString1.getCurLen() == 0 && derivedString2.getCurLen() == 0) {
/*  201 */           bool = false;
/*      */         }
/*      */       } else {
/*      */         
/*  205 */         setReturnCode(-1);
/*      */       } 
/*      */       
/*  208 */       if (bool) {
/*  209 */         setReturnCode(0);
/*      */       } else {
/*      */         
/*  212 */         setReturnCode(-1);
/*      */       }
/*      */     
/*  215 */     } catch (Exception exception) {
/*      */       
/*  217 */       String str3 = "<h3><font color=red>Error: {0}</font></h3>";
/*  218 */       String str4 = "<pre>{0}</pre>";
/*  219 */       StringWriter stringWriter = new StringWriter();
/*      */       
/*  221 */       messageFormat = new MessageFormat((this.bundle == null) ? str3 : this.bundle.getString("Error_EXCEPTION"));
/*  222 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*  223 */       arrayOfString[0] = exception.getMessage();
/*  224 */       this.rptSb.append(messageFormat.format(arrayOfString));
/*  225 */       messageFormat = new MessageFormat((this.bundle == null) ? str4 : this.bundle.getString("Error_STACKTRACE"));
/*  226 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/*  227 */       this.rptSb.append(messageFormat.format(arrayOfString));
/*  228 */       logError(stringWriter.getBuffer().toString());
/*  229 */       setReturnCode(-1);
/*      */     }
/*      */     finally {
/*      */       
/*  233 */       setDGTitle(str2);
/*  234 */       setDGRptName(getShortClassName(getClass()));
/*  235 */       setDGRptClass("WWABR");
/*      */       
/*  237 */       if (!isReadOnly()) {
/*  238 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  249 */     logMessage("building rpt bdl: " + this.bundle);
/*      */ 
/*      */     
/*  252 */     messageFormat = new MessageFormat((this.bundle == null) ? str1 : this.bundle.getString("HEADER"));
/*  253 */     arrayOfString[0] = getShortClassName(getClass());
/*  254 */     arrayOfString[1] = str2 + ((getReturnCode() == 0) ? " Passed" : " Failed");
/*  255 */     arrayOfString[2] = getNow();
/*  256 */     arrayOfString[3] = this.m_prof.getOPName();
/*  257 */     arrayOfString[4] = this.m_prof.getRoleDescription();
/*  258 */     arrayOfString[5] = getDescription();
/*  259 */     arrayOfString[6] = getABRVersion();
/*  260 */     this.rptSb.insert(0, messageFormat.format(arrayOfString));
/*  261 */     this.rptSb.append("<!-- DEBUG: " + this.traceSb.toString() + " -->");
/*      */     
/*  263 */     println(this.rptSb.toString());
/*  264 */     printDGSubmitString();
/*  265 */     buildReportFooter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deriveWWMIandCONTRCTINVTITLE(DerivedString paramDerivedString1, DerivedString paramDerivedString2) {
/*  419 */     String str1 = "";
/*  420 */     String str2 = "";
/*  421 */     String str3 = "";
/*      */     
/*  423 */     boolean bool = true;
/*      */ 
/*      */     
/*  426 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PR");
/*  427 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */       
/*  429 */       egDebugInfo(entityGroup);
/*  430 */       str2 = getAttributeFlagEnabledValue("PR", entityGroup.getEntityItem(0).getEntityID(), "BRANDCODE", "");
/*  431 */       this.traceSb.append(NEWLINE + "PR.BRANDCODE = [" + str2 + "] " + 
/*  432 */           getAttributeValue("PR", entityGroup.getEntityItem(0).getEntityID(), "BRANDCODE", ""));
/*      */     } else {
/*      */       
/*  435 */       this.traceSb.append(NEWLINE + "PR EntityGroup not found");
/*      */     } 
/*      */ 
/*      */     
/*  439 */     if ("OF".equals(getEntityType())) {
/*      */       
/*  441 */       str1 = getAttributeFlagEnabledValue("OF", getEntityID(), "OFFERINGTYPE", "");
/*  442 */       this.traceSb.append(NEWLINE + "OF.OFFERINGTYPE = [" + str1 + "] " + 
/*  443 */           getAttributeValue("OF", getEntityID(), "OFFERINGTYPE", ""));
/*      */     } 
/*      */ 
/*      */     
/*  447 */     entityGroup = this.m_elist.getEntityGroup("FAM");
/*  448 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */       
/*  450 */       egDebugInfo(entityGroup);
/*  451 */       str3 = getAttributeFlagEnabledValue("FAM", entityGroup.getEntityItem(0).getEntityID(), "FAMILYNAME", "");
/*  452 */       this.traceSb.append(NEWLINE + "FAM.FAMILYNAME = [" + str3 + "] " + 
/*  453 */           getAttributeValue("FAM", entityGroup.getEntityItem(0).getEntityID(), "FAMILYNAME", ""));
/*      */     } else {
/*      */       
/*  456 */       this.traceSb.append(NEWLINE + "FAM EntityGroup not found");
/*      */     } 
/*      */ 
/*      */     
/*  460 */     if ("VAR".equals(getEntityType()) || ("OF"
/*  461 */       .equals(getEntityType()) && "0080".equals(str1))) {
/*      */ 
/*      */       
/*  464 */       if (str2.equals("10010") || str2
/*  465 */         .equals("10011")) {
/*      */         
/*  467 */         this.traceSb.append(NEWLINE + "Using Case CDT, Intellistation");
/*  468 */         bool = getCDT_Intellistation(paramDerivedString1, paramDerivedString2);
/*      */       }
/*  470 */       else if (str2.equals("10015")) {
/*      */         
/*  472 */         this.traceSb.append(NEWLINE + "Using Case Netfinity");
/*  473 */         bool = getNetfinity(paramDerivedString1, paramDerivedString2);
/*      */       }
/*  475 */       else if (str2.equals("10014")) {
/*      */         
/*  477 */         this.traceSb.append(NEWLINE + "Using Case Mobile");
/*      */         
/*  479 */         if (str3.equals("0330"))
/*      */         {
/*  481 */           this.traceSb.append(" ThinkPad");
/*  482 */           bool = getMobileThinkPad(paramDerivedString1, paramDerivedString2);
/*      */         
/*      */         }
/*  485 */         else if (str3.equals("0340"))
/*      */         {
/*  487 */           this.traceSb.append(" WorkPad");
/*  488 */           bool = getMobileWorkPad(paramDerivedString1, paramDerivedString2);
/*      */         }
/*      */         else
/*      */         {
/*  492 */           this.traceSb.append(" NOT ThinkPad or WorkPad, Using SYSTEM default");
/*  493 */           bool = getSystemDefault(paramDerivedString1, paramDerivedString2);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  498 */         this.traceSb.append(NEWLINE + "Using Case SYSTEM default");
/*  499 */         bool = getSystemDefault(paramDerivedString1, paramDerivedString2);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  504 */       this.traceSb.append(NEWLINE + "Using Case OF Type not SYSTEM default");
/*  505 */       bool = getOFNotSystemDefault(paramDerivedString1, paramDerivedString2);
/*      */     } 
/*      */     
/*  508 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setTextDirectly(String paramString1, String paramString2) throws MiddlewareException, SQLException {
/*  518 */     ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/*  519 */     DatePackage datePackage = this.m_db.getDates();
/*  520 */     String str1 = datePackage.getNow();
/*  521 */     String str2 = datePackage.getForever();
/*  522 */     ControlBlock controlBlock = new ControlBlock(str1, str2, str1, str2, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*  523 */     Text text = new Text(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, controlBlock);
/*  524 */     Vector<Text> vector = new Vector();
/*  525 */     Vector<ReturnEntityKey> vector1 = new Vector();
/*      */     
/*  527 */     this.traceSb.append(NEWLINE + "Orig " + paramString1 + ": !" + 
/*  528 */         getAttributeValue(getEntityType(), getEntityID(), paramString1, "") + "! New: !" + paramString2 + "!");
/*      */     
/*      */     try {
/*  531 */       MessageFormat messageFormat = new MessageFormat(this.bundle.getString("SET_MSG"));
/*  532 */       String[] arrayOfString = new String[2];
/*      */ 
/*      */ 
/*      */       
/*  536 */       vector.addElement(text);
/*  537 */       returnEntityKey.m_vctAttributes = vector;
/*  538 */       vector1.addElement(returnEntityKey);
/*  539 */       this.m_db.update(this.m_prof, vector1, false, false);
/*  540 */       this.m_db.commit();
/*      */       
/*  542 */       EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*  543 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString1);
/*  544 */       arrayOfString[0] = eANMetaAttribute.getActualLongDescription();
/*  545 */       arrayOfString[1] = paramString2;
/*  546 */       this.rptSb.append(messageFormat.format(arrayOfString));
/*      */     }
/*  548 */     catch (SQLException sQLException) {
/*      */       
/*  550 */       logMessage(this + " trouble updating text value " + sQLException);
/*  551 */       throw sQLException;
/*      */     }
/*  553 */     catch (MiddlewareException middlewareException) {
/*      */       
/*  555 */       logMessage(this + " trouble updating text value " + middlewareException);
/*  556 */       throw middlewareException;
/*      */     } finally {
/*      */       
/*  559 */       this.m_db.freeStatement();
/*  560 */       this.m_db.isPending("finally after update in Text value");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getCDT_Intellistation(DerivedString paramDerivedString1, DerivedString paramDerivedString2) {
/*  724 */     boolean bool = true;
/*  725 */     String str1 = "";
/*  726 */     String str2 = "";
/*  727 */     String str3 = "";
/*  728 */     String str4 = "";
/*  729 */     String str5 = "";
/*  730 */     String str6 = "";
/*  731 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SE");
/*  732 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */       
/*  734 */       str6 = getAttributeValue("SE", entityGroup.getEntityItem(0).getEntityID(), "SERIESNAME", "");
/*  735 */       egDebugInfo(entityGroup);
/*      */     } else {
/*      */       
/*  738 */       this.traceSb.append(NEWLINE + "SE EntityGroup not found");
/*      */     } 
/*      */     
/*  741 */     entityGroup = this.m_elist.getEntityGroup("DD");
/*  742 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */       
/*  744 */       String str7 = "";
/*  745 */       String str8 = "";
/*  746 */       String str9 = "";
/*  747 */       String str10 = "";
/*  748 */       String str11 = "";
/*  749 */       String str12 = "";
/*  750 */       String str13 = "";
/*  751 */       String str14 = "";
/*  752 */       String str15 = "";
/*  753 */       String str16 = "";
/*  754 */       String str17 = "";
/*  755 */       String str18 = "";
/*  756 */       String str19 = "";
/*  757 */       String str20 = "";
/*  758 */       String str21 = "";
/*      */       
/*  760 */       str3 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "MEMRAMSTD", "");
/*  761 */       str4 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "MEMRAMSTDUNITS", "");
/*  762 */       str1 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "TOT_L2_CACHE_STD", "");
/*  763 */       str2 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "TOTL2CACHESTDUNITS", "");
/*  764 */       str5 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "NUMPROCSTD", "");
/*  765 */       egDebugInfo(entityGroup);
/*      */       
/*  767 */       entityGroup = this.m_elist.getEntityGroup("PRC");
/*  768 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */ 
/*      */         
/*  771 */         egDebugInfo(entityGroup);
/*  772 */         str7 = getAttributeFlagEnabledValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCMFR", "");
/*  773 */         this.traceSb.append(NEWLINE + "PRC.PROCMFR value: " + 
/*  774 */             getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCMFR", "") + " flag: " + str7);
/*      */ 
/*      */         
/*  777 */         str9 = getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCCLKSPD", "");
/*  778 */         str10 = getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCCLKSPDUNITS", "");
/*      */         
/*  780 */         EANAttribute eANAttribute = entityGroup.getEntityItem(0).getAttribute("PROCESSORTYPE");
/*  781 */         if (eANAttribute != null && eANAttribute instanceof EANFlagAttribute) {
/*      */           
/*  783 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/*  784 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*  785 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/*  787 */             if (arrayOfMetaFlag[b].isSelected()) {
/*      */               
/*  789 */               str8 = arrayOfMetaFlag[b].getShortDescription();
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*  794 */         this.traceSb.append(NEWLINE + "PRC.PROCESSORTYPE value: " + 
/*  795 */             getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCESSORTYPE", "") + " flag: " + 
/*      */             
/*  797 */             getAttributeFlagEnabledValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCESSORTYPE", "") + " shortdesc: " + str8);
/*      */       }
/*      */       else {
/*      */         
/*  801 */         this.traceSb.append(NEWLINE + "PRC EntityGroup not found");
/*      */       } 
/*      */       
/*  804 */       entityGroup = this.m_elist.getEntityGroup("HD");
/*  805 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/*  807 */         egDebugInfo(entityGroup);
/*  808 */         str11 = getAttributeValue("HD", entityGroup.getEntityItem(0).getEntityID(), "HDDCAPACITY", "");
/*  809 */         str12 = getAttributeValue("HD", entityGroup.getEntityItem(0).getEntityID(), "HDDCAPACITYUNITS", "");
/*      */       } else {
/*      */         
/*  812 */         this.traceSb.append(NEWLINE + "HD EntityGroup not found");
/*      */       } 
/*      */       
/*  815 */       entityGroup = this.m_elist.getEntityGroup("HDC");
/*  816 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/*  818 */         egDebugInfo(entityGroup);
/*  819 */         str13 = getAttributeValue("HDC", entityGroup.getEntityItem(0).getEntityID(), "INTERFACEBUS_CO", "");
/*      */       } else {
/*      */         
/*  822 */         this.traceSb.append(NEWLINE + "HDC EntityGroup not found");
/*      */       } 
/*      */       
/*  825 */       entityGroup = this.m_elist.getEntityGroup("MB");
/*  826 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/*  828 */         egDebugInfo(entityGroup);
/*  829 */         str14 = getAttributeValue("MB", entityGroup.getEntityItem(0).getEntityID(), "TOTCARDSLOTS", "");
/*  830 */         str15 = getAttributeValue("MB", entityGroup.getEntityItem(0).getEntityID(), "L3CACHE", "");
/*  831 */         str16 = getAttributeValue("MB", entityGroup.getEntityItem(0).getEntityID(), "L3CACHEUNITS", "");
/*      */       } else {
/*      */         
/*  834 */         this.traceSb.append(NEWLINE + "MB EntityGroup not found");
/*      */       } 
/*      */       
/*  837 */       entityGroup = this.m_elist.getEntityGroup("PP");
/*  838 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/*  840 */         egDebugInfo(entityGroup);
/*  841 */         str17 = getAttributeValue("PP", entityGroup.getEntityItem(0).getEntityID(), "TOTBAYS", "");
/*      */       } else {
/*      */         
/*  844 */         this.traceSb.append(NEWLINE + "PP EntityGroup not found");
/*      */       } 
/*      */       
/*  847 */       entityGroup = this.m_elist.getEntityGroup("POS");
/*  848 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/*  850 */         egDebugInfo(entityGroup);
/*  851 */         str18 = getAttributeValue("POS", entityGroup.getEntityItem(0).getEntityID(), "POSABBREV", "");
/*      */       } else {
/*      */         
/*  854 */         this.traceSb.append(NEWLINE + "POS EntityGroup not found");
/*      */       } 
/*      */       
/*  857 */       entityGroup = this.m_elist.getEntityGroup("CDR");
/*  858 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/*  860 */         egDebugInfo(entityGroup);
/*  861 */         str19 = getAttributeValue("CDR", entityGroup.getEntityItem(0).getEntityID(), "CDROPTITYPE", "");
/*  862 */         str21 = getAttributeValue("CDR", entityGroup.getEntityItem(0).getEntityID(), "CDSPEED", "");
/*  863 */         str20 = getAttributeFlagEnabledValue("CDR", entityGroup.getEntityItem(0).getEntityID(), "CDROPTITYPE", "");
/*      */       } else {
/*      */         
/*  866 */         this.traceSb.append(NEWLINE + "CDR EntityGroup not found");
/*      */       } 
/*      */ 
/*      */       
/*  870 */       if (str6.length() > 0) {
/*      */ 
/*      */         
/*  873 */         appendAndTrace(paramDerivedString1, "SE.SERIESNAME", str6);
/*  874 */         paramDerivedString1.append(" ");
/*  875 */         paramDerivedString1.append("*");
/*  876 */         paramDerivedString1.append(" ");
/*      */ 
/*      */         
/*  879 */         appendAndTrace(paramDerivedString2, "SE.SERIESNAME", str6);
/*  880 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/*  883 */         this.traceSb.append(NEWLINE + "SE.SERIESNAME has no value");
/*      */       } 
/*      */       
/*  886 */       if (str5.length() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  894 */         if (!str5.equals("1") && !str5.equals("0")) {
/*      */           
/*  896 */           appendAndTrace(paramDerivedString1, "DD.NUMPROCSTD", str5);
/*  897 */           paramDerivedString1.append("x");
/*  898 */           appendAndTrace(paramDerivedString2, "DD.NUMPROCSTD", str5);
/*  899 */           paramDerivedString2.append("x");
/*      */         }
/*      */         else {
/*      */           
/*  903 */           this.traceSb.append(NEWLINE + "Not using DD.NUMPROCSTD: " + str5);
/*      */         } 
/*      */       } else {
/*      */         
/*  907 */         this.traceSb.append(NEWLINE + "DD.NUMPROCSTD has no value");
/*      */       } 
/*      */       
/*  910 */       if (str9.length() > 0 || str8.length() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  916 */         if (str7.equals("0020")) {
/*      */           
/*  918 */           if (str8.length() > 0) {
/*  919 */             appendAndTrace(paramDerivedString1, "PRC.PROCESSORTYPE", str8, 15);
/*      */           } else {
/*      */             
/*  922 */             this.traceSb.append(NEWLINE + "" + paramDerivedString1.getType() + " PRC.PROCMFR was AMD but no short desc for PRC.PROCESSORTYPE");
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  927 */           appendAndTrace(paramDerivedString1, "PRC.PROCCLKSPD", str9, 4);
/*  928 */           if (str10.toLowerCase().startsWith("g")) {
/*      */             
/*  930 */             appendAndTrace(paramDerivedString1, "PRC.PROCCLKSPDUNITS", str10);
/*      */           } else {
/*      */             
/*  933 */             this.traceSb.append(NEWLINE + "" + paramDerivedString1.getType() + " not using PRC.PROCCLKSPDUNITS = " + str10);
/*      */           } 
/*      */         } 
/*  936 */         paramDerivedString1.append(" ");
/*      */ 
/*      */         
/*  939 */         appendAndTrace(paramDerivedString2, "PRC.PROCCLKSPD", str9, 4);
/*  940 */         if (str10.toLowerCase().startsWith("g")) {
/*      */           
/*  942 */           appendAndTrace(paramDerivedString2, "PRC.PROCCLKSPDUNITS", str10, 1);
/*      */         } else {
/*      */           
/*  945 */           this.traceSb.append(NEWLINE + "" + paramDerivedString2.getType() + " not using PRC.PROCCLKSPDUNITS = " + str10);
/*      */         } 
/*      */         
/*  948 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/*  951 */         this.traceSb.append(NEWLINE + "PRC.PROCCLKSPD and PRC.PROCESSORTYPE has no value");
/*      */       } 
/*      */       
/*  954 */       if (str1.length() > 0) {
/*      */ 
/*      */ 
/*      */         
/*  958 */         appendAndTrace(paramDerivedString1, "DD.TOT_L2_CACHE_STD", str1, 4);
/*  959 */         appendAndTrace(paramDerivedString1, "DD.TOTL2CACHESTDUNITS", str2);
/*  960 */         paramDerivedString1.append(" ");
/*      */ 
/*      */         
/*  963 */         appendAndTrace(paramDerivedString2, "DD.TOT_L2_CACHE_STD", str1, 4);
/*  964 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/*  967 */         this.traceSb.append(NEWLINE + "DD.TOT_L2_CACHE_STD has no value");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  973 */       if (str15.length() > 0 && !str15.equals("0")) {
/*      */         
/*  975 */         paramDerivedString1.append("L2");
/*  976 */         paramDerivedString1.append(" ");
/*  977 */         appendAndTrace(paramDerivedString1, "MB.L3CACHE", str15, 4);
/*  978 */         appendAndTrace(paramDerivedString1, "MB.L3CACHEUNITS", str16);
/*  979 */         paramDerivedString1.append(" ");
/*  980 */         paramDerivedString1.append("L3");
/*  981 */         paramDerivedString1.append(" ");
/*      */       } else {
/*      */         
/*  984 */         this.traceSb.append(NEWLINE + "MB.L3CACHE has no value");
/*      */       } 
/*      */       
/*  987 */       if (str3.length() > 0) {
/*      */ 
/*      */ 
/*      */         
/*  991 */         appendAndTrace(paramDerivedString1, "DD.MEMRAMSTD", str3, 4);
/*  992 */         appendAndTrace(paramDerivedString1, "DD.MEMRAMSTDUNITS", str4);
/*  993 */         paramDerivedString1.append(" ");
/*      */ 
/*      */         
/*  996 */         appendAndTrace(paramDerivedString2, "DD.MEMRAMSTD", str3, 4);
/*  997 */         paramDerivedString2.append("/");
/*      */       } else {
/*      */         
/* 1000 */         this.traceSb.append(NEWLINE + "DD.MEMRAMSTD has no value");
/*      */       } 
/*      */       
/* 1003 */       if (str11.length() > 0) {
/*      */ 
/*      */         
/* 1006 */         appendAndTrace(paramDerivedString1, "HD.HDDCAPACITY", str11);
/* 1007 */         appendAndTrace(paramDerivedString1, "HD.HDDCAPACITYUNITS", str12);
/* 1008 */         paramDerivedString1.append(" ");
/*      */         
/* 1010 */         appendAndTrace(paramDerivedString2, "HD.HDDCAPACITY", str11);
/* 1011 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1014 */         this.traceSb.append(NEWLINE + "HD.HDDCAPACITY has no value");
/*      */       } 
/*      */       
/* 1017 */       if (str13.length() > 0) {
/*      */ 
/*      */         
/* 1020 */         appendAndTrace(paramDerivedString1, "HDC.INTERFACEBUS_CO", str13, 4);
/* 1021 */         paramDerivedString1.append(" ");
/*      */       } else {
/*      */         
/* 1024 */         this.traceSb.append(NEWLINE + "HDC.INTERFACEBUS_CO has no value");
/*      */       } 
/*      */       
/* 1027 */       if (str18.length() > 0) {
/*      */ 
/*      */         
/* 1030 */         appendAndTrace(paramDerivedString1, "POS.POSABBREV", str18);
/* 1031 */         paramDerivedString1.append(" ");
/*      */         
/* 1033 */         appendAndTrace(paramDerivedString2, "POS.POSABBREV", str18);
/* 1034 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1037 */         this.traceSb.append(NEWLINE + "POS.POSABBREV has no value");
/*      */       } 
/*      */       
/* 1040 */       if (str19.length() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1048 */         if (str20.equals("0010")) {
/*      */           
/* 1050 */           appendAndTrace(paramDerivedString1, "CDR.CDSPEED", str21, 3);
/* 1051 */           appendAndTrace(paramDerivedString2, "CDR.CDSPEED", str21, 3);
/*      */         }
/*      */         else {
/*      */           
/* 1055 */           appendAndTrace(paramDerivedString1, "CDR.CDROPTITYPE", str19);
/* 1056 */           appendAndTrace(paramDerivedString2, "CDR.CDROPTITYPE", str19);
/*      */         } 
/* 1058 */         paramDerivedString1.append(" ");
/*      */       } else {
/*      */         
/* 1061 */         this.traceSb.append(NEWLINE + "CDR.CDROPTITYPE has no value");
/*      */       } 
/*      */       
/* 1064 */       if (str14.length() > 0) {
/*      */ 
/*      */         
/* 1067 */         appendAndTrace(paramDerivedString1, "MB.TOTCARDSLOTS", str14);
/* 1068 */         paramDerivedString1.append("x");
/*      */       } else {
/*      */         
/* 1071 */         this.traceSb.append(NEWLINE + "MB.TOTCARDSLOTS has no value");
/*      */       } 
/*      */ 
/*      */       
/* 1075 */       appendAndTrace(paramDerivedString1, "PP.TOTBAYS", str17);
/*      */     }
/*      */     else {
/*      */       
/* 1079 */       MessageFormat messageFormat = new MessageFormat(this.bundle.getString("Error_NO_DD"));
/* 1080 */       String[] arrayOfString = new String[1];
/* 1081 */       this.traceSb.append(NEWLINE + "DD EntityGroup not found");
/*      */       
/* 1083 */       entityGroup = this.m_elist.getEntityGroup(getRootEntityType());
/* 1084 */       arrayOfString[0] = entityGroup.getLongDescription();
/* 1085 */       this.rptSb.append(messageFormat.format(arrayOfString));
/* 1086 */       bool = false;
/*      */     } 
/*      */     
/* 1089 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getNetfinity(DerivedString paramDerivedString1, DerivedString paramDerivedString2) {
/* 1127 */     boolean bool = true;
/* 1128 */     String str1 = "";
/* 1129 */     String str2 = "";
/* 1130 */     String str3 = "";
/* 1131 */     String str4 = "";
/* 1132 */     String str5 = "";
/* 1133 */     String str6 = "";
/* 1134 */     String str7 = "";
/*      */     
/* 1136 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SE");
/* 1137 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */       
/* 1139 */       egDebugInfo(entityGroup);
/* 1140 */       str1 = getAttributeValue("SE", entityGroup.getEntityItem(0).getEntityID(), "SERIESNAME", "");
/*      */     } else {
/*      */       
/* 1143 */       this.traceSb.append(NEWLINE + "SE EntityGroup not found");
/*      */     } 
/*      */     
/* 1146 */     entityGroup = this.m_elist.getEntityGroup("DD");
/* 1147 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */       
/* 1149 */       String str8 = "";
/* 1150 */       String str9 = "";
/* 1151 */       String str10 = "";
/* 1152 */       String str11 = "";
/* 1153 */       String str12 = "";
/* 1154 */       String str13 = "";
/* 1155 */       String str14 = "";
/* 1156 */       String str15 = "";
/* 1157 */       String str16 = "";
/*      */       
/* 1159 */       egDebugInfo(entityGroup);
/* 1160 */       str4 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "MEMRAMSTD", "");
/* 1161 */       str5 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "MEMRAMSTDUNITS", "");
/* 1162 */       str2 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "TOT_L2_CACHE_STD", "");
/* 1163 */       str3 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "TOTL2CACHESTDUNITS", "");
/* 1164 */       str6 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "NUMPROCSTD", "");
/* 1165 */       str7 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "NUMINSTHD", "");
/*      */       
/* 1167 */       entityGroup = this.m_elist.getEntityGroup("PRC");
/* 1168 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */ 
/*      */         
/* 1171 */         egDebugInfo(entityGroup);
/* 1172 */         str8 = getAttributeFlagEnabledValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCMFR", "");
/* 1173 */         this.traceSb.append(NEWLINE + "PRC.PROCMFR value: " + 
/* 1174 */             getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCMFR", "") + " flag: " + str8);
/*      */ 
/*      */         
/* 1177 */         str10 = getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCCLKSPD", "");
/* 1178 */         str11 = getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCCLKSPDUNITS", "");
/*      */         
/* 1180 */         EANAttribute eANAttribute = entityGroup.getEntityItem(0).getAttribute("PROCESSORTYPE");
/* 1181 */         if (eANAttribute != null && eANAttribute instanceof EANFlagAttribute) {
/*      */           
/* 1183 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 1184 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1185 */           for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */             
/* 1187 */             if (arrayOfMetaFlag[b].isSelected()) {
/*      */               
/* 1189 */               str9 = arrayOfMetaFlag[b].getShortDescription();
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1194 */         this.traceSb.append(NEWLINE + "PRC.PROCESSORTYPE value: " + 
/* 1195 */             getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCESSORTYPE", "") + " flag: " + 
/*      */             
/* 1197 */             getAttributeFlagEnabledValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCESSORTYPE", "") + " shortdesc: " + str9);
/*      */       }
/*      */       else {
/*      */         
/* 1201 */         this.traceSb.append(NEWLINE + "PRC EntityGroup not found");
/*      */       } 
/*      */       
/* 1204 */       entityGroup = this.m_elist.getEntityGroup("HD");
/* 1205 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/* 1207 */         egDebugInfo(entityGroup);
/* 1208 */         str12 = getAttributeValue("HD", entityGroup.getEntityItem(0).getEntityID(), "HDDCAPACITY", "");
/* 1209 */         str13 = getAttributeValue("HD", entityGroup.getEntityItem(0).getEntityID(), "HDDCAPACITYUNITS", "");
/*      */       } else {
/*      */         
/* 1212 */         this.traceSb.append(NEWLINE + "HD EntityGroup not found");
/*      */       } 
/*      */       
/* 1215 */       entityGroup = this.m_elist.getEntityGroup("HDC");
/* 1216 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/* 1218 */         egDebugInfo(entityGroup);
/* 1219 */         str14 = getAttributeValue("HDC", entityGroup.getEntityItem(0).getEntityID(), "INTERFACEBUS_CO", "");
/*      */       } else {
/*      */         
/* 1222 */         this.traceSb.append(NEWLINE + "HDC EntityGroup not found");
/*      */       } 
/*      */       
/* 1225 */       entityGroup = this.m_elist.getEntityGroup("MB");
/* 1226 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/* 1228 */         egDebugInfo(entityGroup);
/* 1229 */         str15 = getAttributeValue("MB", entityGroup.getEntityItem(0).getEntityID(), "TOTCARDSLOTS", "");
/*      */       } else {
/*      */         
/* 1232 */         this.traceSb.append(NEWLINE + "MB EntityGroup not found");
/*      */       } 
/*      */       
/* 1235 */       entityGroup = this.m_elist.getEntityGroup("PP");
/* 1236 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/* 1238 */         egDebugInfo(entityGroup);
/* 1239 */         str16 = getAttributeValue("PP", entityGroup.getEntityItem(0).getEntityID(), "TOTBAYS", "");
/*      */       } else {
/*      */         
/* 1242 */         this.traceSb.append(NEWLINE + "PP EntityGroup not found");
/*      */       } 
/*      */ 
/*      */       
/* 1246 */       if (str1.length() > 0) {
/*      */ 
/*      */         
/* 1249 */         appendAndTrace(paramDerivedString1, "SE.SERIESNAME", str1);
/* 1250 */         paramDerivedString1.append(" ");
/* 1251 */         paramDerivedString1.append("*");
/* 1252 */         paramDerivedString1.append(" ");
/*      */ 
/*      */         
/* 1255 */         appendAndTrace(paramDerivedString2, "SE.SERIESNAME", str1);
/* 1256 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1259 */         this.traceSb.append(NEWLINE + "SE.SERIESNAME has no value");
/*      */       } 
/*      */       
/* 1262 */       if (str6.length() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1270 */         if (!str6.equals("1") && !str6.equals("0")) {
/*      */           
/* 1272 */           appendAndTrace(paramDerivedString1, "DD.NUMPROCSTD", str6);
/* 1273 */           paramDerivedString1.append("x");
/* 1274 */           appendAndTrace(paramDerivedString2, "DD.NUMPROCSTD", str6);
/* 1275 */           paramDerivedString2.append("x");
/*      */         }
/*      */         else {
/*      */           
/* 1279 */           this.traceSb.append(NEWLINE + "Not using DD.NUMPROCSTD: " + str6);
/*      */         } 
/*      */       } else {
/*      */         
/* 1283 */         this.traceSb.append(NEWLINE + "DD.NUMPROCSTD has no value");
/*      */       } 
/*      */       
/* 1286 */       if (str10.length() > 0 || str9.length() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1292 */         if (str8.equals("0020")) {
/*      */           
/* 1294 */           if (str9.length() > 0) {
/* 1295 */             appendAndTrace(paramDerivedString1, "PRC.PROCESSORTYPE", str9, 15);
/*      */           } else {
/*      */             
/* 1298 */             this.traceSb.append(NEWLINE + "" + paramDerivedString1.getType() + " PRC.PROCMFR was AMD but no short desc for PRC.PROCESSORTYPE");
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1303 */           appendAndTrace(paramDerivedString1, "PRC.PROCCLKSPD", str10, 4);
/* 1304 */           if (str11.toLowerCase().startsWith("g")) {
/*      */             
/* 1306 */             appendAndTrace(paramDerivedString1, "PRC.PROCCLKSPDUNITS", str11);
/*      */           } else {
/*      */             
/* 1309 */             this.traceSb.append(NEWLINE + "" + paramDerivedString1.getType() + " not using PRC.PROCCLKSPDUNITS = " + str11);
/*      */           } 
/*      */         } 
/* 1312 */         paramDerivedString1.append(" ");
/*      */ 
/*      */         
/* 1315 */         appendAndTrace(paramDerivedString2, "PRC.PROCCLKSPD", str10, 4);
/* 1316 */         if (str11.toLowerCase().startsWith("g")) {
/*      */           
/* 1318 */           appendAndTrace(paramDerivedString2, "PRC.PROCCLKSPDUNITS", str11, 1);
/*      */         } else {
/*      */           
/* 1321 */           this.traceSb.append(NEWLINE + "" + paramDerivedString2.getType() + " not using PRC.PROCCLKSPDUNITS = " + str11);
/*      */         } 
/*      */         
/* 1324 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1327 */         this.traceSb.append(NEWLINE + "PRC.PROCCLKSPD and PRC.PROCESSORTYPE has no value");
/*      */       } 
/*      */       
/* 1330 */       if (str2.length() > 0) {
/*      */ 
/*      */ 
/*      */         
/* 1334 */         appendAndTrace(paramDerivedString1, "DD.TOT_L2_CACHE_STD", str2, 4);
/* 1335 */         appendAndTrace(paramDerivedString1, "DD.TOTL2CACHESTDUNITS", str3);
/* 1336 */         paramDerivedString1.append(" ");
/*      */         
/* 1338 */         appendAndTrace(paramDerivedString2, "DD.TOT_L2_CACHE_STD", str2, 4);
/* 1339 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1342 */         this.traceSb.append(NEWLINE + "DD.TOT_L2_CACHE_STD has no value");
/*      */       } 
/*      */       
/* 1345 */       if (str4.length() > 0) {
/*      */ 
/*      */         
/* 1348 */         appendAndTrace(paramDerivedString1, "DD.MEMRAMSTD", str4, 4);
/* 1349 */         appendAndTrace(paramDerivedString1, "DD.MEMRAMSTDUNITS", str5);
/* 1350 */         paramDerivedString1.append(" ");
/*      */ 
/*      */         
/* 1353 */         appendAndTrace(paramDerivedString2, "DD.MEMRAMSTD", str4, 4);
/* 1354 */         paramDerivedString2.append("/");
/*      */       } else {
/*      */         
/* 1357 */         this.traceSb.append(NEWLINE + "DD.MEMRAMSTD has no value");
/*      */       } 
/* 1359 */       if (str7.length() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1367 */         if (!str7.equals("1") && !str7.equals("0")) {
/*      */           
/* 1369 */           appendAndTrace(paramDerivedString1, "DD.NUMINSTHD", str7);
/* 1370 */           paramDerivedString1.append("x");
/* 1371 */           appendAndTrace(paramDerivedString2, "DD.NUMINSTHD", str7);
/* 1372 */           paramDerivedString2.append("x");
/*      */         } else {
/*      */           
/* 1375 */           this.traceSb.append(NEWLINE + "Not using DD.NUMINSTHD: " + str7);
/*      */         } 
/*      */       } else {
/*      */         
/* 1379 */         this.traceSb.append(NEWLINE + "DD.NUMINSTHD has no value");
/*      */       } 
/* 1381 */       if (str12.length() > 0) {
/*      */ 
/*      */         
/* 1384 */         appendAndTrace(paramDerivedString1, "HD.HDDCAPACITY", str12);
/* 1385 */         appendAndTrace(paramDerivedString1, "HD.HDDCAPACITYUNITS", str13);
/* 1386 */         paramDerivedString1.append(" ");
/*      */         
/* 1388 */         appendAndTrace(paramDerivedString2, "HD.HDDCAPACITY", str12);
/*      */       } else {
/*      */         
/* 1391 */         this.traceSb.append(NEWLINE + "HD.HDDCAPACITY has no value");
/*      */       } 
/* 1393 */       if (str14.length() > 0) {
/*      */ 
/*      */         
/* 1396 */         appendAndTrace(paramDerivedString1, "HDC.INTERFACEBUS_CO", str14, 4);
/* 1397 */         paramDerivedString1.append(" ");
/*      */       } else {
/*      */         
/* 1400 */         this.traceSb.append(NEWLINE + "HDC.INTERFACEBUS_CO has no value");
/*      */       } 
/* 1402 */       if (str15.length() > 0) {
/*      */ 
/*      */         
/* 1405 */         appendAndTrace(paramDerivedString1, "MB.TOTCARDSLOTS", str15);
/* 1406 */         paramDerivedString1.append("x");
/*      */       } else {
/*      */         
/* 1409 */         this.traceSb.append(NEWLINE + "MB.TOTCARDSLOTS has no value");
/*      */       } 
/*      */       
/* 1412 */       appendAndTrace(paramDerivedString1, "PP.TOTBAYS", str16);
/*      */     }
/*      */     else {
/*      */       
/* 1416 */       MessageFormat messageFormat = new MessageFormat(this.bundle.getString("Error_NO_DD"));
/* 1417 */       String[] arrayOfString = new String[1];
/* 1418 */       this.traceSb.append(NEWLINE + "DD EntityGroup not found");
/*      */       
/* 1420 */       entityGroup = this.m_elist.getEntityGroup(getRootEntityType());
/* 1421 */       arrayOfString[0] = entityGroup.getLongDescription();
/* 1422 */       this.rptSb.append(messageFormat.format(arrayOfString));
/* 1423 */       bool = false;
/*      */     } 
/*      */     
/* 1426 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getMobileThinkPad(DerivedString paramDerivedString1, DerivedString paramDerivedString2) {
/* 1463 */     boolean bool = true;
/* 1464 */     String str1 = "";
/* 1465 */     String str2 = "";
/* 1466 */     String str3 = "";
/* 1467 */     String str4 = "";
/* 1468 */     String str5 = "";
/* 1469 */     String str6 = "";
/* 1470 */     String str7 = "";
/* 1471 */     String str8 = "";
/* 1472 */     String str9 = "";
/*      */     
/* 1474 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SE");
/* 1475 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */       
/* 1477 */       egDebugInfo(entityGroup);
/* 1478 */       str1 = getAttributeValue("SE", entityGroup.getEntityItem(0).getEntityID(), "SERIESNAME", "");
/*      */     } else {
/*      */       
/* 1481 */       this.traceSb.append(NEWLINE + "SE EntityGroup not found");
/*      */     } 
/*      */     
/* 1484 */     entityGroup = this.m_elist.getEntityGroup("PRC");
/* 1485 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */ 
/*      */       
/* 1488 */       egDebugInfo(entityGroup);
/* 1489 */       str2 = getAttributeFlagEnabledValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCMFR", "");
/* 1490 */       this.traceSb.append(NEWLINE + "PRC.PROCMFR value: " + 
/* 1491 */           getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCMFR", "") + " flag: " + str2);
/*      */ 
/*      */       
/* 1494 */       str4 = getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCCLKSPD", "");
/* 1495 */       str5 = getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCCLKSPDUNITS", "");
/*      */       
/* 1497 */       EANAttribute eANAttribute = entityGroup.getEntityItem(0).getAttribute("PROCESSORTYPE");
/* 1498 */       if (eANAttribute != null && eANAttribute instanceof EANFlagAttribute) {
/*      */         
/* 1500 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 1501 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1502 */         for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */           
/* 1504 */           if (arrayOfMetaFlag[b].isSelected()) {
/*      */             
/* 1506 */             str3 = arrayOfMetaFlag[b].getShortDescription();
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1511 */       this.traceSb.append(NEWLINE + "PRC.PROCESSORTYPE value: " + 
/* 1512 */           getAttributeValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCESSORTYPE", "") + " flag: " + 
/*      */           
/* 1514 */           getAttributeFlagEnabledValue("PRC", entityGroup.getEntityItem(0).getEntityID(), "PROCESSORTYPE", "") + " shortdesc: " + str3);
/*      */     }
/*      */     else {
/*      */       
/* 1518 */       this.traceSb.append(NEWLINE + "PRC EntityGroup not found");
/*      */     } 
/*      */     
/* 1521 */     entityGroup = this.m_elist.getEntityGroup("DD");
/* 1522 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */       
/* 1524 */       String str10 = "";
/* 1525 */       String str11 = "";
/* 1526 */       String str12 = "";
/* 1527 */       String str13 = "";
/* 1528 */       String str14 = "";
/* 1529 */       String str15 = "";
/* 1530 */       String str16 = "";
/* 1531 */       String str17 = "";
/*      */       
/* 1533 */       egDebugInfo(entityGroup);
/* 1534 */       str8 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "MEMRAMSTD", "");
/* 1535 */       str6 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "TOT_L2_CACHE_STD", "");
/* 1536 */       str9 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "MEMRAMSTDUNITS", "");
/* 1537 */       str7 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "TOTL2CACHESTDUNITS", "");
/*      */       
/* 1539 */       entityGroup = this.m_elist.getEntityGroup("HD");
/* 1540 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/* 1542 */         egDebugInfo(entityGroup);
/* 1543 */         str10 = getAttributeValue("HD", entityGroup.getEntityItem(0).getEntityID(), "HDDCAPACITY", "");
/* 1544 */         str11 = getAttributeValue("HD", entityGroup.getEntityItem(0).getEntityID(), "HDDCAPACITYUNITS", "");
/*      */       } else {
/*      */         
/* 1547 */         this.traceSb.append(NEWLINE + "HD EntityGroup not found");
/*      */       } 
/*      */       
/* 1550 */       entityGroup = this.m_elist.getEntityGroup("POS");
/* 1551 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/* 1553 */         egDebugInfo(entityGroup);
/* 1554 */         str12 = getAttributeValue("POS", entityGroup.getEntityItem(0).getEntityID(), "POSABBREV", "");
/*      */       } else {
/*      */         
/* 1557 */         this.traceSb.append(NEWLINE + "POS EntityGroup not found");
/*      */       } 
/*      */       
/* 1560 */       entityGroup = this.m_elist.getEntityGroup("MON");
/* 1561 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/* 1563 */         egDebugInfo(entityGroup);
/* 1564 */         str13 = getAttributeValue("MON", entityGroup.getEntityItem(0).getEntityID(), "SCREENSIZENOM_IN", "");
/* 1565 */         str14 = getAttributeValue("MON", entityGroup.getEntityItem(0).getEntityID(), "TUBETYPE", "");
/*      */       } else {
/*      */         
/* 1568 */         this.traceSb.append(NEWLINE + "MON EntityGroup not found");
/*      */       } 
/*      */       
/* 1571 */       entityGroup = this.m_elist.getEntityGroup("CDR");
/* 1572 */       if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */         
/* 1574 */         egDebugInfo(entityGroup);
/* 1575 */         str15 = getAttributeValue("CDR", entityGroup.getEntityItem(0).getEntityID(), "CDROPTITYPE", "");
/* 1576 */         str17 = getAttributeValue("CDR", entityGroup.getEntityItem(0).getEntityID(), "CDSPEED", "");
/* 1577 */         str16 = getAttributeFlagEnabledValue("CDR", entityGroup.getEntityItem(0).getEntityID(), "CDROPTITYPE", "");
/*      */       }
/*      */       else {
/*      */         
/* 1581 */         this.traceSb.append(NEWLINE + "CDR EntityGroup not found");
/*      */       } 
/*      */ 
/*      */       
/* 1585 */       if (str1.length() > 0) {
/*      */ 
/*      */         
/* 1588 */         appendAndTrace(paramDerivedString1, "SE.SERIESNAME", str1);
/* 1589 */         paramDerivedString1.append(" ");
/* 1590 */         paramDerivedString1.append("*");
/* 1591 */         paramDerivedString1.append(" ");
/*      */         
/* 1593 */         appendAndTrace(paramDerivedString2, "SE.SERIESNAME", str1);
/* 1594 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1597 */         this.traceSb.append(NEWLINE + "SE.SERIESNAME has no value");
/*      */       } 
/*      */       
/* 1600 */       if (str4.length() > 0 || str3.length() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1606 */         if (str2.equals("0020")) {
/*      */           
/* 1608 */           if (str3.length() > 0) {
/* 1609 */             appendAndTrace(paramDerivedString1, "PRC.PROCESSORTYPE", str3, 15);
/*      */           } else {
/*      */             
/* 1612 */             this.traceSb.append(NEWLINE + "" + paramDerivedString1.getType() + " PRC.PROCMFR was AMD but no short desc for PRC.PROCESSORTYPE");
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1617 */           appendAndTrace(paramDerivedString1, "PRC.PROCCLKSPD", str4, 4);
/* 1618 */           if (str5.toLowerCase().startsWith("g")) {
/*      */             
/* 1620 */             appendAndTrace(paramDerivedString1, "PRC.PROCCLKSPDUNITS", str5);
/*      */           } else {
/*      */             
/* 1623 */             this.traceSb.append(NEWLINE + "" + paramDerivedString1.getType() + " not using PRC.PROCCLKSPDUNITS = " + str5);
/*      */           } 
/*      */         } 
/* 1626 */         paramDerivedString1.append(" ");
/*      */ 
/*      */         
/* 1629 */         appendAndTrace(paramDerivedString2, "PRC.PROCCLKSPD", str4, 4);
/* 1630 */         if (str5.toLowerCase().startsWith("g")) {
/*      */           
/* 1632 */           appendAndTrace(paramDerivedString2, "PRC.PROCCLKSPDUNITS", str5, 1);
/*      */         } else {
/*      */           
/* 1635 */           this.traceSb.append(NEWLINE + "" + paramDerivedString2.getType() + " not using PRC.PROCCLKSPDUNITS = " + str5);
/*      */         } 
/*      */         
/* 1638 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1641 */         this.traceSb.append(NEWLINE + "PRC.PROCCLKSPD and PRC.PROCESSORTYPE has no value");
/*      */       } 
/* 1643 */       if (str6.length() > 0) {
/*      */ 
/*      */ 
/*      */         
/* 1647 */         appendAndTrace(paramDerivedString1, "DD.TOT_L2_CACHE_STD", str6, 4);
/* 1648 */         appendAndTrace(paramDerivedString1, "DD.TOTL2CACHESTDUNITS", str7);
/* 1649 */         paramDerivedString1.append(" ");
/*      */         
/* 1651 */         appendAndTrace(paramDerivedString2, "DD.TOT_L2_CACHE_STD", str6, 4);
/* 1652 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1655 */         this.traceSb.append(NEWLINE + "DD.TOT_L2_CACHE_STD has no value");
/*      */       } 
/* 1657 */       if (str8.length() > 0) {
/*      */ 
/*      */         
/* 1660 */         appendAndTrace(paramDerivedString1, "DD.MEMRAMSTD", str8, 4);
/* 1661 */         appendAndTrace(paramDerivedString1, "DD.MEMRAMSTDUNITS", str9);
/* 1662 */         paramDerivedString1.append(" ");
/*      */ 
/*      */         
/* 1665 */         appendAndTrace(paramDerivedString2, "DD.MEMRAMSTD", str8, 4);
/* 1666 */         paramDerivedString2.append("/");
/*      */       } else {
/*      */         
/* 1669 */         this.traceSb.append(NEWLINE + "DD.MEMRAMSTD has no value");
/*      */       } 
/* 1671 */       if (str10.length() > 0) {
/*      */ 
/*      */         
/* 1674 */         appendAndTrace(paramDerivedString1, "HD.HDDCAPACITY", str10);
/* 1675 */         appendAndTrace(paramDerivedString1, "HD.HDDCAPACITYUNITS", str11);
/* 1676 */         paramDerivedString1.append(" ");
/*      */         
/* 1678 */         appendAndTrace(paramDerivedString2, "HD.HDDCAPACITY", str10);
/* 1679 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1682 */         this.traceSb.append(NEWLINE + "HD.HDDCAPACITY has no value");
/*      */       } 
/* 1684 */       if (str12.length() > 0) {
/*      */ 
/*      */         
/* 1687 */         appendAndTrace(paramDerivedString1, "POS.POSABBREV", str12);
/* 1688 */         paramDerivedString1.append(" ");
/*      */         
/* 1690 */         appendAndTrace(paramDerivedString2, "POS.POSABBREV", str12);
/* 1691 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1694 */         this.traceSb.append(NEWLINE + "POS.POSABBREV has no value");
/*      */       } 
/* 1696 */       if (str13.length() > 0) {
/*      */ 
/*      */         
/* 1699 */         appendAndTrace(paramDerivedString1, "MON.SCREENSIZENOM_IN", str13);
/* 1700 */         paramDerivedString1.append(" ");
/*      */         
/* 1702 */         appendAndTrace(paramDerivedString2, "MON.SCREENSIZENOM_IN", str13);
/* 1703 */         paramDerivedString2.append(" ");
/*      */       } else {
/*      */         
/* 1706 */         this.traceSb.append(NEWLINE + "MON.SCREENSIZENOM_IN has no value");
/*      */       } 
/* 1708 */       if (str14.length() > 0) {
/*      */ 
/*      */         
/* 1711 */         appendAndTrace(paramDerivedString1, "MON.TUBETYPE", str14, 4);
/* 1712 */         paramDerivedString1.append(" ");
/*      */       } else {
/*      */         
/* 1715 */         this.traceSb.append(NEWLINE + "MON.TUBETYPE has no value");
/*      */       } 
/* 1717 */       if (str15.length() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1725 */         if (str16.equals("0010")) {
/*      */           
/* 1727 */           appendAndTrace(paramDerivedString1, "CDR.CDSPEED", str17, 3);
/* 1728 */           appendAndTrace(paramDerivedString2, "CDR.CDSPEED", str17, 3);
/*      */         }
/*      */         else {
/*      */           
/* 1732 */           appendAndTrace(paramDerivedString1, "CDR.CDROPTITYPE", str15);
/* 1733 */           appendAndTrace(paramDerivedString2, "CDR.CDROPTITYPE", str15);
/*      */         } 
/*      */       } else {
/*      */         
/* 1737 */         this.traceSb.append(NEWLINE + "CDR.CDROPTITYPE has no value");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1742 */       MessageFormat messageFormat = new MessageFormat(this.bundle.getString("Error_NO_DD"));
/* 1743 */       String[] arrayOfString = new String[1];
/* 1744 */       this.traceSb.append(NEWLINE + "DD EntityGroup not found");
/*      */       
/* 1746 */       entityGroup = this.m_elist.getEntityGroup(getRootEntityType());
/* 1747 */       arrayOfString[0] = entityGroup.getLongDescription();
/* 1748 */       this.rptSb.append(messageFormat.format(arrayOfString));
/* 1749 */       bool = false;
/*      */     } 
/*      */     
/* 1752 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getMobileWorkPad(DerivedString paramDerivedString1, DerivedString paramDerivedString2) {
/* 1774 */     boolean bool = true;
/* 1775 */     String str1 = "";
/* 1776 */     String str2 = "";
/* 1777 */     String str3 = "";
/*      */     
/* 1779 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SE");
/* 1780 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */       
/* 1782 */       egDebugInfo(entityGroup);
/* 1783 */       str1 = getAttributeValue("SE", entityGroup.getEntityItem(0).getEntityID(), "SERIESNAME", "");
/*      */     } else {
/*      */       
/* 1786 */       this.traceSb.append(NEWLINE + "SE EntityGroup not found");
/*      */     } 
/*      */     
/* 1789 */     entityGroup = this.m_elist.getEntityGroup("DD");
/* 1790 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/*      */       
/* 1792 */       egDebugInfo(entityGroup);
/* 1793 */       str2 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "MEMRAMSTD", "");
/* 1794 */       str3 = getAttributeValue("DD", entityGroup.getEntityItem(0).getEntityID(), "MEMRAMSTDUNITS", "");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1805 */       if (str1.length() > 0) {
/*      */         
/* 1807 */         appendAndTrace(paramDerivedString2, "SE.SERIESNAME", str1);
/* 1808 */         paramDerivedString2.append(" ");
/*      */         
/* 1810 */         appendAndTrace(paramDerivedString1, "SE.SERIESNAME", str1);
/* 1811 */         paramDerivedString1.append(" ");
/* 1812 */         paramDerivedString1.append("*");
/* 1813 */         paramDerivedString1.append(" ");
/*      */       } else {
/*      */         
/* 1816 */         this.traceSb.append(NEWLINE + "SE.SERIESNAME has no value");
/*      */       } 
/*      */       
/* 1819 */       if (str2.length() > 0) {
/*      */         
/* 1821 */         appendAndTrace(paramDerivedString1, "DD.MEMRAMSTD", str2, 4);
/* 1822 */         appendAndTrace(paramDerivedString1, "DD.MEMRAMSTDUNITS", str3);
/*      */         
/* 1824 */         appendAndTrace(paramDerivedString2, "DD.MEMRAMSTD", str2, 4);
/*      */       } else {
/*      */         
/* 1827 */         this.traceSb.append(NEWLINE + "DD.MEMRAMSTD has no value");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1832 */       MessageFormat messageFormat = new MessageFormat(this.bundle.getString("Error_NO_DD"));
/* 1833 */       String[] arrayOfString = new String[1];
/* 1834 */       this.traceSb.append(NEWLINE + "DD EntityGroup not found");
/*      */       
/* 1836 */       entityGroup = this.m_elist.getEntityGroup(getRootEntityType());
/* 1837 */       arrayOfString[0] = entityGroup.getLongDescription();
/* 1838 */       this.rptSb.append(messageFormat.format(arrayOfString));
/* 1839 */       bool = false;
/*      */     } 
/*      */     
/* 1842 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getSystemDefault(DerivedString paramDerivedString1, DerivedString paramDerivedString2) {
/* 1859 */     String str2 = "VARPNUMBDESC";
/* 1860 */     if ("OF".equals(getEntityType())) {
/* 1861 */       str2 = "OFFERINGPNUMBDESC";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1868 */     String str1 = getAttributeValue(getRootEntityType(), getRootEntityID(), str2, "");
/*      */ 
/*      */     
/* 1871 */     boolean bool = paramDerivedString1.appendAndTrunc(str1);
/* 1872 */     this.traceSb.append(NEWLINE + "" + paramDerivedString1.getType() + " " + getRootEntityType() + "." + str2 + " = !" + str1 + "!");
/* 1873 */     if (bool) {
/* 1874 */       this.traceSb.append(" was truncated to !" + paramDerivedString1.toString() + "!");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1880 */     bool = paramDerivedString2.appendAndTrunc(str1);
/* 1881 */     this.traceSb.append(NEWLINE + "" + paramDerivedString2.getType() + " " + getRootEntityType() + "." + str2 + " = !" + str1 + "!");
/* 1882 */     if (bool) {
/* 1883 */       this.traceSb.append(" was truncated to !" + paramDerivedString2.toString() + "!");
/*      */     }
/*      */     
/* 1886 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getOFNotSystemDefault(DerivedString paramDerivedString1, DerivedString paramDerivedString2) {
/* 1902 */     String str = getAttributeValue(getRootEntityType(), getRootEntityID(), "OFFERINGPNUMBDESC", "");
/*      */     
/* 1904 */     boolean bool = paramDerivedString1.appendAndTrunc(str);
/* 1905 */     this.traceSb.append(NEWLINE + "" + paramDerivedString1.getType() + " OF.OFFERINGPNUMBDESC = !" + str + "!");
/* 1906 */     if (bool) {
/* 1907 */       this.traceSb.append(" was truncated to !" + paramDerivedString1.toString() + "!");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1912 */     bool = paramDerivedString2.appendAndTrunc(str);
/* 1913 */     this.traceSb.append(NEWLINE + "" + paramDerivedString2.getType() + " OF.OFFERINGPNUMBDESC = !" + str + "!");
/* 1914 */     if (bool) {
/* 1915 */       this.traceSb.append(" was truncated to !" + paramDerivedString2.toString() + "!");
/*      */     }
/*      */     
/* 1918 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean appendAndTrace(DerivedString paramDerivedString, String paramString1, String paramString2) {
/* 1927 */     boolean bool = false;
/* 1928 */     if (!paramDerivedString.isOk()) {
/*      */       
/* 1930 */       this.traceSb.append(NEWLINE + "" + paramDerivedString.getType() + " has previously exceeded or reached the max value, can not append " + paramString1 + " !" + paramString2 + "!");
/*      */     }
/*      */     else {
/*      */       
/* 1934 */       this.traceSb.append(NEWLINE + "" + paramDerivedString.getType() + " " + paramString1 + " = " + paramString2);
/* 1935 */       bool = paramDerivedString.append(paramString2);
/* 1936 */       if (!bool)
/*      */       {
/* 1938 */         this.traceSb.append(NEWLINE + "" + paramDerivedString.getType() + " Could not append " + paramString1 + " !" + paramString2 + "! maxlen: " + paramDerivedString
/* 1939 */             .getMaxLen() + " curlen: " + paramDerivedString.getCurLen());
/*      */       }
/* 1941 */       this.traceSb.append(" curStr: !" + paramDerivedString.toString() + "!");
/*      */     } 
/*      */     
/* 1944 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean appendAndTrace(DerivedString paramDerivedString, String paramString1, String paramString2, int paramInt) {
/* 1953 */     boolean bool = false;
/* 1954 */     if (!paramDerivedString.isOk()) {
/*      */       
/* 1956 */       this.traceSb.append(NEWLINE + "" + paramDerivedString.getType() + " has previously exceeded or reached the max value, can not append " + paramString1 + " !" + paramString2 + "!");
/*      */     }
/*      */     else {
/*      */       
/* 1960 */       this.traceSb.append(NEWLINE + "" + paramDerivedString.getType() + " " + paramString1 + " = " + paramString2);
/* 1961 */       if (paramString2.length() > paramInt) {
/* 1962 */         this.traceSb.append(" exceeds specified len: " + paramInt + ". It will be truncated!");
/*      */       }
/* 1964 */       bool = paramDerivedString.append(paramString2, paramInt);
/* 1965 */       if (!bool)
/*      */       {
/* 1967 */         this.traceSb.append(NEWLINE + "" + paramDerivedString.getType() + " Could not append " + paramString1 + " !" + paramString2 + "! maxlen: " + paramDerivedString
/* 1968 */             .getMaxLen() + " curlen: " + paramDerivedString.getCurLen());
/*      */       }
/* 1970 */       this.traceSb.append(" curStr: !" + paramDerivedString.toString() + "!");
/*      */     } 
/*      */     
/* 1973 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 1983 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 1985 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, getRootEntityType(), "Navigate");
/* 1986 */     EANList eANList = entityGroup.getMetaAttribute();
/* 1987 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1989 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1990 */       stringBuffer.append(getAttributeValue(getRootEntityType(), getRootEntityID(), eANMetaAttribute.getAttributeCode()));
/* 1991 */       stringBuffer.append(" ");
/*      */     } 
/*      */     
/* 1994 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 2004 */     String str = "The World Wide Master Index (OF.OFWWMI and VAR.VARWWMI) and the Offering Contract Invoice Title (OF.OFCONTRCTINVTITLE and VAR.VARCONTRCTINVTITLE) are derived from attributes on the series and SBB/elements attached to the offering.";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2009 */     if (this.bundle != null) {
/* 2010 */       str = this.bundle.getString("DESCRIPTION");
/*      */     }
/*      */     
/* 2013 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 2022 */     return "$Revision: 1.7 $";
/*      */   }
/*      */   
/*      */   private Locale getLocale(int paramInt) {
/* 2026 */     Locale locale = null;
/* 2027 */     switch (paramInt)
/*      */     { case 1:
/* 2029 */         locale = Locale.US;
/*      */       case 2:
/* 2031 */         locale = Locale.GERMAN;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2051 */         return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */ 
/*      */   
/*      */   private void egDebugInfo(EntityGroup paramEntityGroup) {
/* 2056 */     this.traceSb.append(NEWLINE + "" + paramEntityGroup.getEntityType() + " EntityGroup has EntityItem IDs:");
/* 2057 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 2058 */       this.traceSb.append(" " + paramEntityGroup.getEntityItem(b).getEntityID());
/*      */     }
/* 2060 */     this.traceSb.append(". Using entityItem ID: " + paramEntityGroup.getEntityItem(0).getEntityID());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class DerivedString
/*      */   {
/*      */     private int maxlen;
/*      */     
/* 2069 */     private StringBuffer sb = new StringBuffer();
/*      */     private String typeStr;
/*      */     private boolean bisOk = true;
/*      */     
/*      */     DerivedString(int param1Int, String param1String) {
/* 2074 */       this.maxlen = param1Int;
/* 2075 */       this.typeStr = param1String;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     boolean append(String param1String) {
/* 2081 */       boolean bool = true;
/* 2082 */       if (param1String.length() > 0) {
/* 2083 */         if (!this.bisOk) {
/* 2084 */           bool = false;
/*      */         
/*      */         }
/* 2087 */         else if (this.sb.length() + param1String.length() > this.maxlen) {
/*      */           
/* 2089 */           this.bisOk = false;
/* 2090 */           if (this.sb.length() == 0) {
/*      */             
/* 2092 */             this.sb.append(param1String);
/* 2093 */             this.sb.setLength(this.maxlen);
/* 2094 */             WWMIABR.this.traceSb.append(WWMIABR.NEWLINE + "Truncating and appending first str !" + param1String + "! ");
/*      */           } else {
/*      */             
/* 2097 */             bool = false;
/*      */           } 
/*      */         } else {
/*      */           
/* 2101 */           this.sb.append(param1String);
/*      */         } 
/*      */       }
/*      */       
/* 2105 */       return bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean append(String param1String, int param1Int) {
/* 2112 */       boolean bool = true;
/* 2113 */       if (param1String.length() > 0) {
/* 2114 */         if (!this.bisOk) {
/* 2115 */           bool = false;
/*      */         } else {
/*      */           
/* 2118 */           if (param1String.length() > param1Int) {
/* 2119 */             param1String = param1String.substring(0, param1Int);
/*      */           }
/*      */           
/* 2122 */           bool = append(param1String);
/*      */         } 
/*      */       }
/*      */       
/* 2126 */       return bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean appendAndTrunc(String param1String) {
/* 2133 */       boolean bool = false;
/*      */       
/* 2135 */       if (param1String.length() > 0) {
/* 2136 */         this.sb.append(param1String);
/* 2137 */         if (this.sb.length() > this.maxlen) {
/*      */           
/* 2139 */           this.sb.setLength(this.maxlen);
/* 2140 */           bool = true;
/*      */         } 
/*      */       } 
/* 2143 */       return bool;
/*      */     }
/*      */     
/* 2146 */     public String toString() { return this.sb.toString(); }
/* 2147 */     int getMaxLen() { return this.maxlen; }
/* 2148 */     int getCurLen() { return this.sb.length(); }
/* 2149 */     String getType() { return this.typeStr; }
/* 2150 */     void clear() { this.sb.setLength(0);
/* 2151 */       this.bisOk = true; } boolean isOk() {
/* 2152 */       return this.bisOk;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\WWMIABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */