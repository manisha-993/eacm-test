/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.sql.SQLException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ANQRLSEABR01
/*      */   extends PokBaseABR
/*      */ {
/*      */   public static final String COPYRIGHT = "(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
/*      */   public static final String ABR = "ANQRLSEABR01";
/*      */   public static final String ANNOUNCEMENT = "ANNOUNCEMENT";
/*      */   public static final String ANNOUNCEMENT_DESC = "Announcement";
/*      */   public static final String ANCYCLESTATUS = "ANCYCLESTATUS";
/*      */   public static final String ANCYCLESTATUS_DESC = "Announcement Lifecycle Status";
/*      */   public static final String EXECAPPRDATE_A = "EXECAPPRDATE_A";
/*      */   public static final String EXECAPPRDATE_A_DESC = "Executive approval date - Actual";
/*      */   public static final String EXECAPPREADY = "EXECAPPREADY";
/*      */   public static final String EXECAPPREADY_DESC = "Executive Approval Ready";
/*      */   public static final String ANNNUMBER = "ANNNUMBER";
/*      */   public static final String ANNDATE = "ANNDATE";
/*      */   public static final String ANNTYPE = "ANNTYPE";
/*      */   public static final String ANNTITLE = "ANNTITLE";
/*      */   public static final String ANNDESC = "ANNDESC";
/*      */   public static final String INEANN = "INEANN";
/*      */   public static final String ANNREVIEW = "ANNREVIEW";
/*      */   public static final String ANNREVIEW_DESC = "Annoucement Review";
/*      */   public static final String ANREVIEW = "ANREVIEW";
/*      */   public static final String ANNREVIEWDEF = "ANNREVIEWDEF";
/*      */   public static final String ANNREVIEWDEF_DESC = "Announce Review Definition";
/*      */   public static final String ANNOP = "ANNOP";
/*      */   public static final String ANNROLETYPE = "ANNROLETYPE";
/*      */   public static final String ANNROLETYPE_DESC = "Announcement Role Type";
/*      */   public static final String USERNAME = "USERNAME";
/*      */   public static final String RFAHWABR01 = "RFAHWABR01";
/*      */   public static final String DEF_NOT_POPULATED_HTML = "** Not Populated **";
/*      */   public static final String NULL = "";
/*  363 */   private EntityGroup m_egParent = null;
/*  364 */   private EntityGroup m_egANNREV = null;
/*  365 */   private EntityGroup m_egANNOP = null;
/*      */   
/*  367 */   private EntityItem m_eiParent = null;
/*      */   
/*  369 */   private final String m_strAppRiskValue = "Approved with Risk";
/*      */   
/*  371 */   private final String m_strAppValue_ANN = "Approved";
/*      */ 
/*      */   
/*  374 */   private final String m_strAppValue = "Approved";
/*      */   
/*  376 */   private final String m_strProcRiskValue = "Proceed with Risk";
/*      */   
/*  378 */   private final String m_strPDTLeadValue = "PDT Leadership Review";
/*      */   
/*  380 */   private final String m_strYesValue = "Yes";
/*      */ 
/*      */   
/*  383 */   private final String m_strNewValue = "New";
/*      */   
/*  385 */   private final String m_strRelExeRevValue = "Releasing Executive";
/*      */ 
/*      */   
/*  388 */   private final String m_strRelProMgmtValue = "116";
/*      */   
/*  390 */   private final String m_strSENDTOPMValue = "0020";
/*      */   
/*      */   private boolean m_bPass = false;
/*      */   
/*  394 */   private ControlBlock m_cbOn = null;
/*  395 */   private String m_strNow = null;
/*  396 */   private String m_strForever = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  403 */     EANAttribute eANAttribute = null;
/*  404 */     String str1 = null;
/*  405 */     StringWriter stringWriter = null;
/*  406 */     String str2 = null;
/*      */     try {
/*  408 */       start_ABRBuild();
/*      */ 
/*      */ 
/*      */       
/*  412 */       buildRptHeader();
/*      */       
/*  414 */       setControlBlock();
/*      */ 
/*      */       
/*  417 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  418 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*      */       
/*  420 */       setDGTitle(setDGName(this.m_eiParent, "ANQRLSEABR01"));
/*      */       
/*  422 */       logMessage("************ Root Entity Type and id " + 
/*      */           
/*  424 */           getEntityType() + ":" + 
/*      */           
/*  426 */           getEntityID());
/*      */       
/*  428 */       if (this.m_egParent == null) {
/*  429 */         logMessage("********** 1 ANNOUNCEMENT Not found");
/*  430 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/*  433 */         println("<h1><b>" + this.m_egParent
/*      */             
/*  435 */             .getLongDescription() + "</b></h1><br />");
/*      */         
/*  437 */         processRootEntity(this.m_egParent, "ANNOUNCEMENT");
/*  438 */         if (getReturnCode() == -1) {
/*      */           
/*  440 */           println(dispNavAttributes(this.m_eiParent, this.m_egParent));
/*  441 */           eANAttribute = this.m_eiParent.getAttribute("ANNDESC");
/*      */ 
/*      */           
/*  444 */           str1 = (eANAttribute != null) ? eANAttribute.toString() : "<em>** Not Populated **</em>";
/*      */           
/*  446 */           println("<br /><table summary=\"Announcement Description\" width=\"100%\"><tr><td class=\"PsgLabel\">Announcement Description</td></tr><tr><td class=\"PsgText\">" + str1 + "</td></tr></table>");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  453 */       println("<br /><b>" + 
/*      */           
/*  455 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */               
/*  458 */               getABRDescription(), 
/*  459 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */             }) + "</b>");
/*      */       
/*  462 */       log(
/*  463 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */               
/*  466 */               getABRDescription(), 
/*  467 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */             }));
/*  469 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  470 */       setReturnCode(-2);
/*  471 */       println("<h3 style=\"color:#c00; font-weight:bold;\">IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*      */ 
/*      */ 
/*      */           
/*  475 */           .getMessage() + "</h3>");
/*      */       
/*  477 */       logError(lockPDHEntityException.getMessage());
/*  478 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  479 */       setReturnCode(-2);
/*  480 */       println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + updatePDHEntityException
/*      */           
/*  482 */           .getMessage() + "</h3>");
/*      */       
/*  484 */       logError(updatePDHEntityException.getMessage());
/*  485 */     } catch (Exception exception) {
/*      */       
/*  487 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/*  488 */       println("" + exception);
/*      */ 
/*      */       
/*  491 */       if (getABRReturnCode() != -2) {
/*  492 */         setReturnCode(-3);
/*      */       }
/*  494 */       exception.printStackTrace();
/*      */       
/*  496 */       stringWriter = new StringWriter();
/*  497 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*  498 */       str2 = stringWriter.toString();
/*  499 */       println(str2);
/*      */       
/*  501 */       logMessage("Error in " + this.m_abri
/*  502 */           .getABRCode() + ":" + exception.getMessage());
/*  503 */       logMessage("" + exception);
/*      */ 
/*      */       
/*  506 */       if (getABRReturnCode() != -2) {
/*  507 */         setReturnCode(-3);
/*      */       
/*      */       }
/*      */     }
/*      */     finally {
/*      */       
/*  513 */       setDGString(getABRReturnCode());
/*  514 */       setDGRptName("ANQRLSEABR01");
/*  515 */       setDGRptClass("ANQRLSEABR01");
/*      */       
/*  517 */       if (!isReadOnly()) {
/*  518 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  523 */     printDGSubmitString();
/*  524 */     println(EACustom.getTOUDiv());
/*  525 */     buildReportFooter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getABREntityDesc(String paramString, int paramInt) {
/*  536 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  546 */     return "<br /><br />";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getStyle() {
/*  557 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/*  566 */     return "1.44";
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
/*      */   public void processRootEntity(EntityGroup paramEntityGroup, String paramString) throws LockPDHEntityException, UpdatePDHEntityException, Exception, SQLException, MiddlewareException {
/*  590 */     String str = null;
/*  591 */     EntityItem entityItem = paramEntityGroup.getEntityItem(0);
/*      */     
/*  593 */     logMessage("****************** Entity Type returned is " + entityItem
/*      */         
/*  595 */         .getEntityType());
/*      */     
/*  597 */     if (entityItem.getEntityType().equals(paramString)) {
/*  598 */       logMessage("****************** Entitytype is " + entityItem
/*  599 */           .getEntityType());
/*      */ 
/*      */       
/*  602 */       str = getAttributeValue(
/*  603 */           getEntityType(), 
/*  604 */           getEntityID(), "ANCYCLESTATUS", "<em>** Not Populated **</em>");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  609 */       logMessage("****** ANCYCLESTATUS strStatus is " + str);
/*      */       
/*  611 */       if (str.equals("Approved with Risk")) {
/*      */ 
/*      */         
/*  614 */         this.m_egANNREV = this.m_elist.getEntityGroup("ANNREVIEW");
/*  615 */         logMessage("************ Root Entity Type and id " + 
/*      */             
/*  617 */             getEntityType() + ":" + 
/*      */             
/*  619 */             getEntityID());
/*      */         
/*  621 */         if (this.m_egANNREV == null) {
/*  622 */           logMessage("********** 2 ANNREVIEW Not found");
/*  623 */           setReturnCode(-1);
/*      */         } else {
/*      */           
/*  626 */           processEntityStatus(this.m_egANNREV, "ANNREVIEW", "ANREVIEW");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  632 */           logMessage("****** processRootEntity m_bPass " + this.m_bPass);
/*      */           
/*  634 */           if (this.m_bPass) {
/*      */             
/*  636 */             String str1 = null;
/*  637 */             String str2 = null;
/*  638 */             String str3 = null;
/*  639 */             String str4 = null;
/*  640 */             String str5 = null;
/*  641 */             String str6 = null;
/*      */ 
/*      */             
/*  644 */             str1 = getAttributeValue(
/*  645 */                 getEntityType(), 
/*  646 */                 getEntityID(), "EXECAPPRDATE_A");
/*      */ 
/*      */             
/*  649 */             str2 = getAttributeValue(
/*  650 */                 getEntityType(), 
/*  651 */                 getEntityID(), "EXECAPPREADY", "<em>** Not Populated **</em>");
/*      */ 
/*      */ 
/*      */             
/*  655 */             str3 = getAttributeValue(
/*  656 */                 getEntityType(), 
/*  657 */                 getEntityID(), "INEANN", "<em>** Not Populated **</em>");
/*      */ 
/*      */ 
/*      */             
/*  661 */             str4 = getAttributeValue(
/*  662 */                 getEntityType(), 
/*  663 */                 getEntityID(), "ANNTYPE", "<em>** Not Populated **</em>");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  671 */             logMessage("****** EXECAPPRDATE_A is " + str1);
/*      */             
/*  673 */             logMessage("****** EXECAPPREADY is " + str2);
/*  674 */             logMessage("****** INEANN is " + str3);
/*  675 */             logMessage("****** ANNTYPE is " + str4);
/*      */ 
/*      */ 
/*      */             
/*  679 */             this.m_egANNOP = this.m_elist.getEntityGroup("ANNOP");
/*  680 */             logMessage("********** 3 Entity Type and id " + 
/*      */                 
/*  682 */                 getEntityType() + ":" + 
/*      */                 
/*  684 */                 getEntityID());
/*      */             
/*  686 */             if (this.m_egANNOP == null) {
/*  687 */               logMessage("********** 3 ANNOP Not found");
/*  688 */               setReturnCode(-1);
/*      */             }
/*      */             else {
/*      */               
/*  692 */               str5 = getRelatorAttributeValue(this.m_egANNOP, "ANNOP");
/*      */               
/*  694 */               str6 = getRelatorUserNameValue(this.m_egANNOP, "ANNOP");
/*      */ 
/*      */               
/*  697 */               logMessage("ANNROLETYPE is " + str5);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  702 */             if (!isPastDate(str1) || str2
/*  703 */               .length() <= 0 || 
/*  704 */               !str2.equals("Yes") || str5 == null || str6 == null || 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  709 */               !str3.equals("Yes") || 
/*  710 */               !str4.equals("New")) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  715 */               if (!isPastDate(str1)) {
/*  716 */                 println("<br /><b>Fail:Executive approval date - Actual is not today or past</b><br />");
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  722 */               if (str2.length() <= 0 || 
/*  723 */                 !str2.equals("Yes")) {
/*  724 */                 println("<br /><b>Fail:Executive Approval Ready is not Yes</b><br />");
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  736 */               if (str6 == null) {
/*  737 */                 println("<br /><b>Fail:the User Name is null</b><br />");
/*      */               }
/*      */               
/*  740 */               if (!str3.equals("Yes") || 
/*  741 */                 !str4.equals("New")) {
/*  742 */                 println("<br /><b>Fail:the Announcement must be a 'New' e-announce Announcement</b><br /><br />");
/*      */               }
/*      */               
/*  745 */               setReturnCode(-1);
/*  746 */               this.m_bPass = false;
/*      */             } else {
/*      */               
/*  749 */               setReturnCode(0);
/*      */               
/*  751 */               setFlagValue("ANCYCLESTATUS");
/*      */               
/*  753 */               setFlagValue("RFAHWABR01");
/*      */ 
/*      */ 
/*      */               
/*  757 */               this.m_prof.setValOn(getValOn());
/*  758 */               this.m_prof.setEffOn(getEffOn());
/*  759 */               start_ABRBuild();
/*      */               
/*  761 */               printPassMessage();
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  766 */             println("<br /><b>Fail:Announcement review not exist</b><br /><br />");
/*  767 */             setReturnCode(-1);
/*      */           }
/*      */         
/*      */         } 
/*  771 */       } else if (str.equals("Approved")) {
/*  772 */         setReturnCode(0);
/*  773 */         setFlagValue("ANCYCLESTATUS");
/*  774 */         setFlagValue("RFAHWABR01");
/*      */ 
/*      */         
/*  777 */         this.m_prof.setValOn(getValOn());
/*  778 */         this.m_prof.setEffOn(getEffOn());
/*  779 */         start_ABRBuild();
/*      */         
/*  781 */         printPassMessage();
/*      */       } else {
/*      */         
/*  784 */         println("<br /><b>Fail:Announcement Lifecycle Status is not 'Approved with Risk' or 'Approved'</b><br /><br />");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  792 */         setReturnCode(-1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setControlBlock() {
/*  802 */     this.m_strNow = getNow();
/*  803 */     this.m_strForever = getForever();
/*  804 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  811 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
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
/*      */   public void setFlagValue(String paramString) {
/*  825 */     String str = null;
/*      */     
/*  827 */     if (paramString.equals("ANCYCLESTATUS")) {
/*      */       
/*  829 */       str = "116";
/*      */     }
/*  831 */     else if (paramString.equals("RFAHWABR01")) {
/*      */       
/*  833 */       str = "0020";
/*      */     }
/*      */     else {
/*      */       
/*  837 */       str = null;
/*      */     } 
/*      */     
/*  840 */     logMessage("****** strAttributeValue set to: " + str);
/*      */     
/*  842 */     if (str != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  850 */         EntityItem entityItem = new EntityItem(null, this.m_prof, getEntityType(), getEntityID());
/*      */ 
/*      */ 
/*      */         
/*  854 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  862 */         SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString, str, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  867 */         Vector<SingleFlag> vector = new Vector();
/*  868 */         Vector<ReturnEntityKey> vector1 = new Vector();
/*      */         
/*  870 */         if (singleFlag != null) {
/*  871 */           vector.addElement(singleFlag);
/*      */           
/*  873 */           returnEntityKey.m_vctAttributes = vector;
/*  874 */           vector1.addElement(returnEntityKey);
/*      */           
/*  876 */           this.m_db.update(this.m_prof, vector1, false, false);
/*  877 */           this.m_db.commit();
/*      */         } 
/*  879 */       } catch (MiddlewareException middlewareException) {
/*  880 */         logMessage("setFlagValue: " + middlewareException.getMessage());
/*  881 */       } catch (Exception exception) {
/*  882 */         logMessage("setFlagValue: " + exception.getMessage());
/*      */       } 
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
/*      */   public void processEntityStatus(EntityGroup paramEntityGroup, String paramString1, String paramString2) {
/*  898 */     String str1 = null;
/*  899 */     String str2 = null;
/*      */     
/*  901 */     this.m_bPass = false;
/*      */     
/*  903 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*      */       
/*  905 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  906 */       logMessage("****************** Entity Type returned is " + entityItem
/*      */           
/*  908 */           .getEntityType() + " ei:" + b);
/*      */ 
/*      */ 
/*      */       
/*  912 */       if (entityItem.getEntityType().equals(paramString1)) {
/*  913 */         logMessage("****************** Entitytype is " + entityItem
/*      */             
/*  915 */             .getEntityType());
/*      */ 
/*      */         
/*  918 */         str1 = getAttributeValue(entityItem
/*  919 */             .getEntityType(), entityItem
/*  920 */             .getEntityID(), paramString2, "<em>** Not Populated **</em>");
/*      */ 
/*      */ 
/*      */         
/*  924 */         str2 = getAttributeValue(entityItem
/*  925 */             .getEntityType(), entityItem
/*  926 */             .getEntityID(), "ANNREVIEWDEF", "<em>** Not Populated **</em>");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  934 */         logMessage("****** ANREVIEW status is " + str1);
/*  935 */         logMessage("****** ANNREVIEWDEF is " + str2);
/*  936 */         logMessage("****** in for loop: " + b + " " + this.m_bPass);
/*      */         
/*  938 */         if ((str1.equals("Approved") || str1
/*  939 */           .equals("Proceed with Risk")) && str2
/*  940 */           .equals("PDT Leadership Review")) {
/*      */           
/*  942 */           this.m_bPass = true;
/*      */           
/*  944 */           logMessage("****** processEntityStatus m_bPass is " + this.m_bPass);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  951 */     if (!this.m_bPass && str1 != null && str2 != null) {
/*  952 */       if (!str2.equals("PDT Leadership Review")) {
/*  953 */         println("<br /><b>Fail:Announce Review Definition is not PDT Leadership Review</b><br />");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  961 */       if (!str1.equals("Approved") && 
/*  962 */         !str1.equals("Proceed with Risk")) {
/*  963 */         println("<br /><b>Fail:Annoucement Review is not Approved or Proceed with Risk</b><br /><br />");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  973 */       setReturnCode(-1);
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
/*      */   public String getRelatorAttributeValue(EntityGroup paramEntityGroup, String paramString) {
/*  990 */     String str = null;
/*  991 */     boolean bool = false;
/*  992 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/*      */       
/*  994 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/*  995 */       logMessage("****************** Entity Type returned is " + entityItem
/*      */           
/*  997 */           .getEntityType() + " ei:" + b);
/*      */ 
/*      */ 
/*      */       
/* 1001 */       if (entityItem != null && entityItem
/* 1002 */         .getEntityType().equals(paramString)) {
/* 1003 */         logMessage("****************** Entitytype is " + entityItem
/*      */             
/* 1005 */             .getEntityType());
/* 1006 */         logMessage("****************** _eg.getMetaAttributeCount is " + paramEntityGroup
/*      */             
/* 1008 */             .getMetaAttributeCount());
/*      */         
/* 1010 */         for (byte b1 = 0; b1 < paramEntityGroup.getMetaAttributeCount(); b1++) {
/*      */           
/* 1012 */           EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b1);
/* 1013 */           logMessage("****************** ma is" + eANMetaAttribute.toString());
/* 1014 */           logMessage("****************** getAttributeCode is 1" + eANMetaAttribute
/*      */               
/* 1016 */               .getAttributeCode());
/* 1017 */           logMessage("****************** ANNROLETYPE is 1ANNROLETYPE");
/*      */ 
/*      */           
/* 1020 */           if (eANMetaAttribute.getAttributeCode().equals("ANNROLETYPE") && eANMetaAttribute != null) {
/*      */ 
/*      */             
/* 1023 */             EANAttribute eANAttribute = entityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 1024 */             logMessage("****************** getAttributeCode is " + eANMetaAttribute
/*      */                 
/* 1026 */                 .getAttributeCode());
/* 1027 */             logMessage("****************** ANNROLETYPE is ANNROLETYPE");
/*      */             
/* 1029 */             if (eANAttribute != null) {
/* 1030 */               str = eANAttribute.toString();
/* 1031 */               if (str.equals("Releasing Executive")) {
/*      */                 
/* 1033 */                 logMessage("****************** strAnnExeAppName is " + str);
/*      */ 
/*      */                 
/* 1036 */                 logMessage("****************** m_strRelExeRevValue is Releasing Executive");
/*      */ 
/*      */ 
/*      */                 
/* 1040 */                 bool = true;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/* 1046 */         if (bool) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/* 1051 */     return str;
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
/*      */   public String getRelatorUserNameValue(EntityGroup paramEntityGroup, String paramString) {
/* 1066 */     EntityItem entityItem1 = null;
/* 1067 */     EntityItem entityItem2 = null;
/* 1068 */     String str = null;
/* 1069 */     boolean bool = false;
/*      */     
/* 1071 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 1072 */       entityItem1 = paramEntityGroup.getEntityItem(b);
/* 1073 */       logMessage("****************** Relator Type returned is " + entityItem1
/*      */           
/* 1075 */           .getEntityType() + " entANNOP: " + b);
/*      */ 
/*      */ 
/*      */       
/* 1079 */       if (entityItem1 != null && 
/* 1080 */         entityItem1.getEntityType().equals(paramString)) {
/* 1081 */         logMessage("****************** Relator type is " + entityItem1
/*      */             
/* 1083 */             .getEntityType());
/* 1084 */         logMessage("****************** _eg.getMetaAttributeCount is " + paramEntityGroup
/*      */             
/* 1086 */             .getMetaAttributeCount());
/*      */         
/* 1088 */         if (paramEntityGroup.isRelator()) {
/* 1089 */           byte b1 = 0;
/* 1090 */           for (; b1 < entityItem1.getDownLinkCount(); 
/* 1091 */             b1++) {
/* 1092 */             entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/*      */             
/* 1094 */             if (entityItem2 != null && 
/* 1095 */               entityItem2.getEntityType().equals("OP")) {
/*      */               
/* 1097 */               str = getAttributeValue(entityItem2
/* 1098 */                   .getEntityType(), entityItem2
/* 1099 */                   .getEntityID(), "USERNAME", "** Not Populated **");
/*      */ 
/*      */               
/* 1102 */               logMessage("****************** EntityType is " + entityItem2
/*      */                   
/* 1104 */                   .getEntityType());
/* 1105 */               logMessage("****************** EntityID is " + entityItem2
/*      */                   
/* 1107 */                   .getEntityID());
/* 1108 */               logMessage("****************** strUserName is " + str);
/*      */ 
/*      */ 
/*      */               
/* 1112 */               if (str
/* 1113 */                 .equals("** Not Populated **")) {
/*      */                 
/* 1115 */                 bool = true;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/* 1121 */           if (bool) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1129 */     return bool ? null : str;
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
/*      */   public boolean isPastDate(String paramString) {
/* 1146 */     boolean bool = false;
/*      */ 
/*      */     
/* 1149 */     if (paramString == null || paramString.equals("")) {
/* 1150 */       logMessage("****** effDateValue is null");
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1160 */       String str1 = getNow().substring(0, 4);
/* 1161 */       String str2 = getNow().substring(5, 7);
/* 1162 */       String str3 = getNow().substring(8, 10);
/*      */       
/* 1164 */       String str4 = null;
/* 1165 */       String str5 = null;
/* 1166 */       String str6 = null;
/*      */ 
/*      */       
/* 1169 */       if (paramString.length() > 9) {
/* 1170 */         str4 = paramString.substring(0, 4);
/* 1171 */         str5 = paramString.substring(5, 7);
/* 1172 */         str6 = paramString.substring(8, 10);
/*      */       } else {
/*      */         
/* 1175 */         str4 = paramString.substring(0, 4);
/* 1176 */         str5 = paramString.substring(5, 7);
/* 1177 */         str6 = paramString.substring(8);
/*      */       } 
/*      */       
/* 1180 */       int i = Integer.parseInt(str4);
/* 1181 */       int j = Integer.parseInt(str5);
/* 1182 */       int k = Integer.parseInt(str6);
/*      */       
/* 1184 */       int m = Integer.parseInt(str1);
/* 1185 */       int n = Integer.parseInt(str2);
/* 1186 */       int i1 = Integer.parseInt(str3);
/*      */ 
/*      */ 
/*      */       
/* 1190 */       logMessage("****** DateValue is " + i + "-" + j + "-" + k);
/*      */       
/* 1192 */       logMessage("****** m_strTimeStampNow is " + m + "-" + n + "-" + i1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1200 */       if (i < m) {
/*      */         
/* 1202 */         logMessage("is a Past Date");
/*      */         
/* 1204 */         bool = true;
/*      */       } 
/* 1206 */       if (!bool) {
/* 1207 */         if (i == m) {
/* 1208 */           if (j < n) {
/*      */             
/* 1210 */             logMessage("****** is a Past Date");
/*      */             
/* 1212 */             bool = true;
/*      */           } 
/* 1214 */           if (!bool && 
/* 1215 */             j == n && 
/* 1216 */             k <= i1) {
/*      */             
/* 1218 */             logMessage("****** is a Past Date");
/*      */             
/* 1220 */             bool = true;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1225 */         if (!bool) {
/*      */           
/* 1227 */           logMessage("****** is a Future Date");
/*      */           
/* 1229 */           bool = false;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1233 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printPassMessage() {
/* 1242 */     println("<br /><b>send to Production Management</b><br /><br />");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/* 1252 */     return "ANQRLSEABR01.java,v 1.44 2006/03/13 19:42:03 couto Exp";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1260 */     return getVersion();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildRptHeader() throws SQLException, MiddlewareException {
/* 1268 */     String str1 = getVersion();
/* 1269 */     String[] arrayOfString = { getABRDescription(), str1, getEnterprise() };
/* 1270 */     String str2 = buildMessage("<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#", arrayOfString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1277 */     println(EACustom.getDocTypeHtml());
/* 1278 */     println("<head>");
/* 1279 */     println(EACustom.getMetaTags("ANQRLSEABR01.java"));
/* 1280 */     println(EACustom.getCSS());
/* 1281 */     println(EACustom.getTitle(getShortClassName(getClass())));
/* 1282 */     println("</head>");
/* 1283 */     println("<body id=\"ibm-com\">");
/* 1284 */     println(EACustom.getMastheadDiv());
/*      */     
/* 1286 */     println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + str2 + "</em></p>");
/*      */     
/* 1288 */     println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" + 
/*      */         
/* 1290 */         getABRVersion() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" + 
/*      */         
/* 1292 */         getValOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" + 
/*      */         
/* 1294 */         getEffOn() + "</td></tr>" + "\n" + "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" + 
/*      */         
/* 1296 */         getNow() + "</td></tr>" + "\n" + "</table>");
/*      */     
/* 1298 */     println("<h3>Description: </h3>" + getDescription() + "\n" + "<hr />" + "\n");
/*      */   }
/*      */ 
/*      */   
/*      */   private String dispNavAttributes(EntityItem paramEntityItem, EntityGroup paramEntityGroup) {
/* 1303 */     StringBuffer stringBuffer = new StringBuffer();
/* 1304 */     stringBuffer.append("<br /><br /><table summary=\"layout\" width=\"100%\">\n");
/* 1305 */     stringBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Navigation Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
/* 1306 */     for (byte b = 0; b < paramEntityGroup.getMetaAttributeCount(); b++) {
/* 1307 */       EANMetaAttribute eANMetaAttribute = paramEntityGroup.getMetaAttribute(b);
/* 1308 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(eANMetaAttribute.getAttributeCode());
/* 1309 */       if (eANMetaAttribute.isNavigate()) {
/* 1310 */         stringBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">" + eANMetaAttribute.getLongDescription() + "</td><td class=\"PsgText\" width=\"65%\">" + ((eANAttribute == null || eANAttribute.toString().length() == 0) ? "** Not Populated **" : eANAttribute.toString()) + "</td></tr>");
/*      */       }
/*      */     } 
/* 1313 */     stringBuffer.append("</table>");
/* 1314 */     return stringBuffer.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ANQRLSEABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */