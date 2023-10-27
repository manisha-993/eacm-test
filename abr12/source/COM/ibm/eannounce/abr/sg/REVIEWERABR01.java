/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
/*      */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*      */ import COM.ibm.opicmpdh.objects.Relator;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import COM.ibm.opicmpdh.objects.Text;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
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
/*      */ public class REVIEWERABR01
/*      */   extends PokBaseABR
/*      */ {
/*   56 */   public static final String ABR = new String("REVIEWERABR01");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   63 */   public static final String ANNCODENAME = new String("ANNCODENAME");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   68 */   public static final String ANNREVIEWSTATUS = new String("ANNREVIEWSTATUS");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   public static final String ANNREVIEWDEF = new String("ANNREVIEWDEF");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   public static final String ANREVIEW = new String("ANREVIEW");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   83 */   public static final String ANREVDATE = new String("ANREVDATE");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   88 */   public static final String ANREVIEWCOMMENT = new String("ANREVIEWCOMMENT");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   93 */   public static final String ANREVIEWDESCRIPTION = new String("ANREVIEWDESCRIPTION");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  101 */   public static final String NAME = new String("NAME");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   public static final String ROLECODE = new String("ROLECODE");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   public static final String INTERNETID = new String("INTERNETID");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   public static final String REVIEWERSTATUS = new String("REVIEWERSTATUS");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  123 */   public static final String FIRSTNAME = new String("FIRSTNAME");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   public static final String LASTNAME = new String("LASTNAME");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  133 */   public static final String USERNAME = new String("USERNAME");
/*      */   
/*  135 */   private EntityGroup m_egParent = null;
/*  136 */   private EntityItem m_eiParent = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  148 */     String str = null;
/*      */     
/*      */     try {
/*  151 */       start_ABRBuild();
/*  152 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  153 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*      */       
/*  155 */       if (this.m_egParent == null) {
/*  156 */         println(ABR + ":" + 
/*      */ 
/*      */             
/*  159 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*      */         
/*  161 */         setReturnCode(-1);
/*      */         return;
/*      */       } 
/*  164 */       if (this.m_eiParent == null) {
/*  165 */         println(ABR + ":" + 
/*      */ 
/*      */             
/*  168 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*      */         
/*  170 */         setReturnCode(-1);
/*      */         
/*      */         return;
/*      */       } 
/*  174 */       logMessage(ABR + ":" + 
/*      */ 
/*      */           
/*  177 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*      */           
/*  179 */           .getEntityType() + ":" + this.m_eiParent
/*      */           
/*  181 */           .getEntityID());
/*  182 */       buildReportHeader();
/*  183 */       setControlBlock();
/*  184 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*      */       
/*  186 */       displayHeader(this.m_egParent, this.m_eiParent);
/*      */ 
/*      */       
/*  189 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */       
/*  193 */       checkANNREVIEWstatus();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  198 */       logMessage(ABR + ":" + 
/*      */ 
/*      */           
/*  201 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*      */           
/*  203 */           .getEntityType() + ":" + this.m_eiParent
/*      */           
/*  205 */           .getEntityID());
/*      */       
/*  207 */       logMessage("***getReturnCode()***" + getReturnCode());
/*  208 */       if (getReturnCode() == 0) {
/*      */         
/*  210 */         this.m_prof.setValOn(getValOn());
/*  211 */         this.m_prof.setEffOn(getEffOn());
/*  212 */         start_ABRBuild();
/*      */       } 
/*      */       
/*  215 */       println("<br /><b>" + 
/*      */           
/*  217 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */               
/*  220 */               getABRDescription(), 
/*  221 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */             }) + "</b>");
/*      */       
/*  224 */       log(
/*  225 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*      */ 
/*      */               
/*  228 */               getABRDescription(), 
/*  229 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*      */             }));
/*  231 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  232 */       setReturnCode(-2);
/*  233 */       println("<h3>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*      */ 
/*      */ 
/*      */           
/*  237 */           .getMessage() + "</font></h3>");
/*      */       
/*  239 */       logError(lockPDHEntityException.getMessage());
/*  240 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  241 */       setReturnCode(-2);
/*  242 */       println("<h3>UpdatePDH error: " + updatePDHEntityException.getMessage() + "</font></h3>");
/*  243 */       logError(updatePDHEntityException.getMessage());
/*  244 */     } catch (Exception exception) {
/*      */       
/*  246 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/*  247 */       println("" + exception);
/*      */ 
/*      */       
/*  250 */       if (getABRReturnCode() != -2) {
/*  251 */         setReturnCode(-3);
/*      */       }
/*      */       
/*  254 */       exception.printStackTrace();
/*      */       
/*  256 */       StringWriter stringWriter = new StringWriter();
/*  257 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*  258 */       str = stringWriter.toString();
/*  259 */       println(str);
/*      */       
/*  261 */       logMessage("Error in " + this.m_abri
/*  262 */           .getABRCode() + ":" + exception.getMessage());
/*  263 */       logMessage("" + exception);
/*      */     }
/*      */     finally {
/*      */       
/*  267 */       if (getReturnCode() == 0) {
/*  268 */         setReturnCode(0);
/*      */         
/*  270 */         setDGString(getABRReturnCode());
/*  271 */         setDGRptName("REVIEWERABR01");
/*  272 */         setDGRptClass("REVIEWERABR01");
/*  273 */         printDGSubmitString();
/*      */ 
/*      */         
/*  276 */         buildReportFooter();
/*      */         
/*  278 */         if (!isReadOnly()) {
/*  279 */           clearSoftLock();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printFAILmessage(EntityGroup paramEntityGroup) {
/*  291 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  293 */         .getLongDescription() + " is not at final status.</b>");
/*      */     
/*  295 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage(EntityGroup paramEntityGroup) {
/*  304 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  306 */         .getLongDescription() + " does not exist (Warning - Pass)</b>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printFAILmessage_2(EntityGroup paramEntityGroup) {
/*  316 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  318 */         .getLongDescription() + " needs to be 'Final'status</b>");
/*      */     
/*  320 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage_2(EntityGroup paramEntityGroup) {
/*  329 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  331 */         .getLongDescription() + " needs to be 'Final'status (Warning - Pass)</b>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printFAILmessage_3(EntityGroup paramEntityGroup) {
/*  341 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  343 */         .getLongDescription() + " needs to be at least 'Ready for Final Review'</b>");
/*      */     
/*  345 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWARNINGmessage_3(EntityGroup paramEntityGroup) {
/*  354 */     println("<br><br><b>" + paramEntityGroup
/*      */         
/*  356 */         .getLongDescription() + " needs to be at least 'Ready for Final Review' (Warning - Pass)</b>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkANNREVIEWstatus() {
/*  366 */     String str1 = null;
/*  367 */     String str2 = null;
/*  368 */     String str3 = null;
/*  369 */     String str4 = null;
/*  370 */     String str5 = null;
/*  371 */     String str6 = null;
/*  372 */     String str7 = null;
/*  373 */     String str8 = null;
/*  374 */     String str9 = null;
/*  375 */     String str10 = null;
/*  376 */     String str11 = null;
/*  377 */     String str12 = null;
/*  378 */     String str13 = null;
/*  379 */     String str14 = null;
/*  380 */     String str15 = null;
/*      */     
/*  382 */     this.m_egParent = this.m_elist.getParentEntityGroup();
/*  383 */     this.m_eiParent = this.m_egParent.getEntityItem(0);
/*      */     
/*  385 */     if (this.m_egParent == null) {
/*  386 */       logMessage("********** 1 Announcement Review Not found");
/*  387 */       setReturnCode(-1);
/*      */     } else {
/*  389 */       for (byte b = 0; b < this.m_egParent.getEntityItemCount(); b++) {
/*      */         
/*  391 */         EntityItem entityItem = this.m_egParent.getEntityItem(b);
/*      */         
/*  393 */         if (entityItem != null) {
/*  394 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[15];
/*  395 */           arrayOfEANAttribute[0] = entityItem.getAttribute("ANREVASAREVIEW");
/*  396 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ANREVCANDNREVIEW");
/*  397 */           arrayOfEANAttribute[2] = entityItem.getAttribute("ANREVCDREVIEW");
/*  398 */           arrayOfEANAttribute[3] = entityItem.getAttribute("ANREVDCMREVIEW");
/*  399 */           arrayOfEANAttribute[4] = entityItem.getAttribute("ANREVDIREVIEW");
/*  400 */           arrayOfEANAttribute[5] = entityItem.getAttribute("ANREVGARREVIEW");
/*  401 */           arrayOfEANAttribute[6] = entityItem.getAttribute("ANREVIGFREVIEW");
/*  402 */           arrayOfEANAttribute[7] = entityItem.getAttribute("ANREVIMCREVIEW");
/*  403 */           arrayOfEANAttribute[8] = entityItem.getAttribute("ANREVIPMTREVIEW");
/*  404 */           arrayOfEANAttribute[9] = entityItem.getAttribute("ANREVLGLREVIEW");
/*  405 */           arrayOfEANAttribute[10] = entityItem.getAttribute("ANREVMKTGREVIEW");
/*  406 */           arrayOfEANAttribute[11] = entityItem.getAttribute("ANREVODMREVIEW");
/*  407 */           arrayOfEANAttribute[12] = entityItem.getAttribute("ANREVPDTREVIEW");
/*  408 */           arrayOfEANAttribute[13] = entityItem.getAttribute("ANREVPRCREVIEW");
/*  409 */           arrayOfEANAttribute[14] = entityItem.getAttribute("ANREVSANDDREVIEW");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  427 */           str1 = arrayOfEANAttribute[0].toString();
/*  428 */           str2 = arrayOfEANAttribute[1].toString();
/*  429 */           str3 = arrayOfEANAttribute[2].toString();
/*  430 */           str4 = arrayOfEANAttribute[3].toString();
/*  431 */           str5 = arrayOfEANAttribute[4].toString();
/*  432 */           str6 = arrayOfEANAttribute[5].toString();
/*  433 */           str7 = arrayOfEANAttribute[6].toString();
/*  434 */           str8 = arrayOfEANAttribute[7].toString();
/*  435 */           str9 = arrayOfEANAttribute[8].toString();
/*  436 */           str10 = arrayOfEANAttribute[9].toString();
/*  437 */           str11 = arrayOfEANAttribute[10].toString();
/*  438 */           str12 = arrayOfEANAttribute[11].toString();
/*  439 */           str13 = arrayOfEANAttribute[12].toString();
/*  440 */           str14 = arrayOfEANAttribute[13].toString();
/*  441 */           str15 = arrayOfEANAttribute[14].toString();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  459 */           println("<br><br><b>" + this.m_egParent
/*      */               
/*  461 */               .getLongDescription() + " Entity ID: " + entityItem
/*      */               
/*  463 */               .getEntityID());
/*  464 */           println(displayAttributes(entityItem, this.m_egParent, true));
/*  465 */           if (str1.equals("Required")) {
/*  466 */             logMessage("****ANREVASAREVIEW equals Required");
/*  467 */             checkOPWGASAstatus();
/*      */           } 
/*  469 */           if (str2.equals("Required")) {
/*  470 */             logMessage("****ANREVCANDNREVIEW equals Required");
/*  471 */             checkOPWGCNstatus();
/*      */           } 
/*  473 */           if (str3.equals("Required")) {
/*  474 */             logMessage("****ANREVCDREVIEW equals Required");
/*  475 */             checkOPWGCDstatus();
/*      */           } 
/*  477 */           if (str4.equals("Required")) {
/*  478 */             logMessage("****ANREVDCMREVIEW equals Required");
/*  479 */             checkOPWGDCMstatus();
/*      */           } 
/*  481 */           if (str5.equals("Required")) {
/*  482 */             logMessage("****ANREVDIREVIEW equals Required");
/*  483 */             checkOPWGDIstatus();
/*      */           } 
/*  485 */           if (str6.equals("Required")) {
/*  486 */             logMessage("****ANREVGARREVIEW equals Required");
/*  487 */             checkOPWGGARstatus();
/*      */           } 
/*  489 */           if (str7.equals("Required")) {
/*  490 */             logMessage("****ANREVIGFREVIEW equals Required");
/*  491 */             checkOPWGIGFstatus();
/*      */           } 
/*  493 */           if (str8.equals("Required")) {
/*  494 */             logMessage("****ANREVIMCREVIEW equals Required");
/*  495 */             checkOPWGIMCstatus();
/*      */           } 
/*  497 */           if (str9.equals("Required")) {
/*  498 */             logMessage("****ANREVIPMTREVIEW equals Required");
/*  499 */             checkOPWGIPMTstatus();
/*      */           } 
/*  501 */           if (str10.equals("Required")) {
/*  502 */             logMessage("****ANREVLGLREVIEW equals Required");
/*  503 */             checkOPWGLEGALstatus();
/*      */           } 
/*  505 */           if (str11.equals("Required")) {
/*  506 */             logMessage("****ANREVMKTGREVIEW equals Required");
/*  507 */             checkOPWGMRstatus();
/*      */           } 
/*  509 */           if (str12.equals("Required")) {
/*  510 */             logMessage("****ANREVODMREVIEW equals Required");
/*  511 */             checkOPWGODMstatus();
/*      */           } 
/*  513 */           if (str13.equals("Required")) {
/*  514 */             logMessage("****ANREVPDTREVIEW equals Required");
/*  515 */             checkOPWGPDTstatus();
/*      */           } 
/*  517 */           if (str14.equals("Required")) {
/*  518 */             logMessage("****ANREVPRCREVIEW equals Required");
/*  519 */             checkOPWGPRstatus();
/*      */           } 
/*  521 */           if (str15.equals("Required")) {
/*  522 */             logMessage("****ANREVSANDDREVIEW equals Required");
/*  523 */             checkOPWGWWSDstatus();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkREVIEWERstatus() {
/*  536 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("REVIEWER");
/*  537 */     if (entityGroup != null)
/*      */     {
/*  539 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  540 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  541 */         if (entityItem != null) {
/*  542 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[3];
/*  543 */           arrayOfEANAttribute[0] = entityItem.getAttribute(NAME);
/*  544 */           arrayOfEANAttribute[1] = entityItem.getAttribute(ROLECODE);
/*  545 */           arrayOfEANAttribute[2] = entityItem.getAttribute(INTERNETID);
/*  546 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/*  547 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*  548 */           logMessage("atts[2]:" + arrayOfEANAttribute[2]);
/*  549 */           println("<br><br><b>" + entityGroup
/*      */               
/*  551 */               .getLongDescription() + " Entity ID: " + entityItem
/*      */               
/*  553 */               .getEntityID());
/*  554 */           println(displayAttributes(entityItem, entityGroup, true));
/*      */         } 
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
/*      */   public void checkOPWGASAstatus() {
/*  567 */     ReturnEntityKey returnEntityKey = null;
/*  568 */     String str1 = null;
/*  569 */     String str2 = null;
/*  570 */     String str3 = null;
/*  571 */     String str4 = null;
/*  572 */     Text text1 = null;
/*  573 */     Text text2 = null;
/*  574 */     Text text3 = null;
/*  575 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  580 */     Vector<Relator> vector = null;
/*  581 */     ReturnRelatorKey returnRelatorKey = null;
/*  582 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/*  584 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/*  585 */     if (entityGroup != null)
/*      */     {
/*  587 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  588 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  589 */         if (entityItem != null) {
/*  590 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/*  591 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/*  592 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/*  593 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/*  594 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/*  596 */           str1 = arrayOfEANAttribute[1].toString();
/*  597 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/*  599 */           if (str1.equals("ASA")) {
/*  600 */             println("<br><br><b>" + entityGroup
/*      */                 
/*  602 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/*  604 */                 .getEntityID());
/*  605 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/*  607 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/*  609 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/*  610 */               logMessage("***eiRelator***:" + entityItem1);
/*  611 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/*  612 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/*  613 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/*  614 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/*  615 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/*  616 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/*  617 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/*  619 */               str2 = arrayOfEANAttribute1[0].toString();
/*  620 */               str3 = arrayOfEANAttribute1[1].toString();
/*  621 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/*  623 */               if (entityItem1 != null) {
/*      */                 try {
/*  625 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  631 */                   logMessage("eiParm:" + entityItem2);
/*  632 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  637 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/*  640 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  649 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  660 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "ASA", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  671 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  677 */                   Vector<Text> vector2 = new Vector();
/*  678 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/*  680 */                   if (text1 != null) {
/*  681 */                     vector2.addElement(text1);
/*  682 */                     vector2.addElement(text2);
/*  683 */                     vector2.addElement(text3);
/*  684 */                     vector2.addElement(singleFlag);
/*  685 */                     logMessage("vctAtts:" + vector2);
/*  686 */                     returnEntityKey.m_vctAttributes = vector2;
/*  687 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/*  690 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/*  693 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  698 */                     logMessage("ol:" + oPICMList);
/*  699 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/*  700 */                     this.m_db.commit();
/*  701 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/*  703 */                         .getEntityType() + " " + entityItem2
/*      */                         
/*  705 */                         .getEntityID());
/*      */                     
/*  707 */                     if (returnEntityKey != null) {
/*      */                       try {
/*  709 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  715 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  727 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/*  729 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  739 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/*  741 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/*  744 */                         vector = new Vector();
/*  745 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/*  748 */                         if (relator != null) {
/*  749 */                           vector.addElement(relator);
/*      */                           
/*  751 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/*  755 */                           vector1
/*  756 */                             .addElement(returnRelatorKey);
/*      */                           
/*  758 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/*  763 */                           this.m_db.commit();
/*      */                         } 
/*  765 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  771 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/*  773 */                             .getMessage());
/*  774 */                       } catch (Exception exception) {
/*  775 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/*  777 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/*  781 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  787 */                   logMessage("setReviewerValue: " + middlewareException
/*  788 */                       .getMessage());
/*  789 */                 } catch (Exception exception) {
/*  790 */                   logMessage("setReviewerValue: " + exception
/*  791 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGCNstatus() {
/*  808 */     ReturnEntityKey returnEntityKey = null;
/*  809 */     String str1 = null;
/*  810 */     String str2 = null;
/*  811 */     String str3 = null;
/*  812 */     String str4 = null;
/*  813 */     Text text1 = null;
/*  814 */     Text text2 = null;
/*  815 */     Text text3 = null;
/*  816 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  821 */     Vector<Relator> vector = null;
/*  822 */     ReturnRelatorKey returnRelatorKey = null;
/*  823 */     Vector<ReturnRelatorKey> vector1 = null;
/*  824 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/*  825 */     if (entityGroup != null)
/*      */     {
/*  827 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  828 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  829 */         if (entityItem != null) {
/*  830 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/*  831 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/*  832 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/*  833 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/*  834 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/*  836 */           str1 = arrayOfEANAttribute[1].toString();
/*  837 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/*  839 */           if (str1.equals("CN")) {
/*  840 */             println("<br><br><b>" + entityGroup
/*      */                 
/*  842 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/*  844 */                 .getEntityID());
/*  845 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/*  847 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/*  849 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/*  850 */               logMessage("***eiRelator***:" + entityItem1);
/*  851 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/*  852 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/*  853 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/*  854 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/*  855 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/*  856 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/*  857 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/*  859 */               str2 = arrayOfEANAttribute1[0].toString();
/*  860 */               str3 = arrayOfEANAttribute1[1].toString();
/*  861 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/*  863 */               if (entityItem1 != null) {
/*      */                 try {
/*  865 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  871 */                   logMessage("eiParm:" + entityItem2);
/*  872 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  877 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/*  880 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  889 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  900 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "CN", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  911 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  916 */                   logMessage("tx:" + text1);
/*  917 */                   logMessage("tx1:" + text2);
/*  918 */                   logMessage("tx2:" + text3);
/*  919 */                   logMessage("sf:" + singleFlag);
/*      */                   
/*  921 */                   Vector<Text> vector2 = new Vector();
/*  922 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/*  924 */                   if (text1 != null) {
/*  925 */                     vector2.addElement(text1);
/*  926 */                     vector2.addElement(text2);
/*  927 */                     vector2.addElement(text3);
/*  928 */                     vector2.addElement(singleFlag);
/*  929 */                     logMessage("vctAtts:" + vector2);
/*  930 */                     returnEntityKey.m_vctAttributes = vector2;
/*  931 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/*  934 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/*  937 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  942 */                     logMessage("ol:" + oPICMList);
/*  943 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/*  944 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/*  948 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/*  951 */                     logMessage("***acl A " + returnEntityKey
/*  952 */                         .getReturnID());
/*  953 */                     this.m_db.commit();
/*  954 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/*  956 */                         .getEntityType() + " " + entityItem2
/*      */                         
/*  958 */                         .getEntityID());
/*      */                     
/*  960 */                     if (returnEntityKey != null) {
/*      */                       try {
/*  962 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  968 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  980 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/*  982 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  992 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/*  994 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/*  997 */                         vector = new Vector();
/*  998 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 1001 */                         if (relator != null) {
/* 1002 */                           vector.addElement(relator);
/*      */                           
/* 1004 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 1008 */                           vector1
/* 1009 */                             .addElement(returnRelatorKey);
/*      */                           
/* 1011 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 1016 */                           this.m_db.commit();
/*      */                         } 
/* 1018 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1024 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 1026 */                             .getMessage());
/* 1027 */                       } catch (Exception exception) {
/* 1028 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 1030 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 1034 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1040 */                   logMessage("setReviewerValue: " + middlewareException
/* 1041 */                       .getMessage());
/* 1042 */                 } catch (Exception exception) {
/* 1043 */                   logMessage("setReviewerValue: " + exception
/* 1044 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGCDstatus() {
/* 1061 */     ReturnEntityKey returnEntityKey = null;
/* 1062 */     String str1 = null;
/* 1063 */     String str2 = null;
/* 1064 */     String str3 = null;
/* 1065 */     String str4 = null;
/* 1066 */     Text text1 = null;
/* 1067 */     Text text2 = null;
/* 1068 */     Text text3 = null;
/* 1069 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1074 */     Vector<Relator> vector = null;
/* 1075 */     ReturnRelatorKey returnRelatorKey = null;
/* 1076 */     Vector<ReturnRelatorKey> vector1 = null;
/* 1077 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 1078 */     if (entityGroup != null)
/*      */     {
/* 1080 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1081 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1082 */         if (entityItem != null) {
/* 1083 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 1084 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 1085 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 1086 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 1087 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 1089 */           str1 = arrayOfEANAttribute[1].toString();
/* 1090 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 1092 */           if (str1.equals("CD")) {
/* 1093 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 1095 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 1097 */                 .getEntityID());
/* 1098 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 1100 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 1102 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 1103 */               logMessage("***eiRelator***:" + entityItem1);
/* 1104 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 1105 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 1106 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 1107 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 1108 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 1109 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 1110 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 1112 */               str2 = arrayOfEANAttribute1[0].toString();
/* 1113 */               str3 = arrayOfEANAttribute1[1].toString();
/* 1114 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 1116 */               if (entityItem1 != null) {
/*      */                 try {
/* 1118 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1124 */                   logMessage("eiParm:" + entityItem2);
/* 1125 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1130 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 1133 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1142 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1153 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "CD", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1164 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1169 */                   logMessage("tx:" + text1);
/* 1170 */                   logMessage("tx1:" + text2);
/* 1171 */                   logMessage("tx2:" + text3);
/* 1172 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 1174 */                   Vector<Text> vector2 = new Vector();
/* 1175 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 1177 */                   if (text1 != null) {
/* 1178 */                     vector2.addElement(text1);
/* 1179 */                     vector2.addElement(text2);
/* 1180 */                     vector2.addElement(text3);
/* 1181 */                     vector2.addElement(singleFlag);
/* 1182 */                     logMessage("vctAtts:" + vector2);
/* 1183 */                     returnEntityKey.m_vctAttributes = vector2;
/* 1184 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 1187 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 1190 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1195 */                     logMessage("ol:" + oPICMList);
/* 1196 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 1197 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 1201 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 1204 */                     logMessage("***acl A " + returnEntityKey
/* 1205 */                         .getReturnID());
/* 1206 */                     this.m_db.commit();
/* 1207 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 1209 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 1211 */                         .getEntityID());
/*      */                     
/* 1213 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 1215 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1221 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1233 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 1235 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1245 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 1247 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 1250 */                         vector = new Vector();
/* 1251 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 1254 */                         if (relator != null) {
/* 1255 */                           vector.addElement(relator);
/*      */                           
/* 1257 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 1261 */                           vector1
/* 1262 */                             .addElement(returnRelatorKey);
/*      */                           
/* 1264 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 1269 */                           this.m_db.commit();
/*      */                         } 
/* 1271 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1277 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 1279 */                             .getMessage());
/* 1280 */                       } catch (Exception exception) {
/* 1281 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 1283 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 1287 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1293 */                   logMessage("setReviewerValue: " + middlewareException
/* 1294 */                       .getMessage());
/* 1295 */                 } catch (Exception exception) {
/* 1296 */                   logMessage("setReviewerValue: " + exception
/* 1297 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGDCMstatus() {
/* 1314 */     ReturnEntityKey returnEntityKey = null;
/* 1315 */     String str1 = null;
/* 1316 */     String str2 = null;
/* 1317 */     String str3 = null;
/* 1318 */     String str4 = null;
/* 1319 */     Text text1 = null;
/* 1320 */     Text text2 = null;
/* 1321 */     Text text3 = null;
/* 1322 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1327 */     Vector<Relator> vector = null;
/* 1328 */     ReturnRelatorKey returnRelatorKey = null;
/* 1329 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 1331 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 1332 */     if (entityGroup != null)
/*      */     {
/* 1334 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1335 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1336 */         if (entityItem != null) {
/* 1337 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 1338 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 1339 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 1340 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 1341 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 1343 */           str1 = arrayOfEANAttribute[1].toString();
/* 1344 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 1346 */           if (str1.equals("DCM")) {
/* 1347 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 1349 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 1351 */                 .getEntityID());
/* 1352 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 1354 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 1356 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 1357 */               logMessage("***eiRelator***:" + entityItem1);
/* 1358 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 1359 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 1360 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 1361 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 1362 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 1363 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 1364 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 1366 */               str2 = arrayOfEANAttribute1[0].toString();
/* 1367 */               str3 = arrayOfEANAttribute1[1].toString();
/* 1368 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 1370 */               if (entityItem1 != null) {
/*      */                 try {
/* 1372 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1378 */                   logMessage("eiParm:" + entityItem2);
/* 1379 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1384 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 1387 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1396 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1407 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "DCM", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1418 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1423 */                   logMessage("tx:" + text1);
/* 1424 */                   logMessage("tx1:" + text2);
/* 1425 */                   logMessage("tx2:" + text3);
/* 1426 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 1428 */                   Vector<Text> vector2 = new Vector();
/* 1429 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 1431 */                   if (text1 != null) {
/* 1432 */                     vector2.addElement(text1);
/* 1433 */                     vector2.addElement(text2);
/* 1434 */                     vector2.addElement(text3);
/* 1435 */                     vector2.addElement(singleFlag);
/* 1436 */                     logMessage("vctAtts:" + vector2);
/* 1437 */                     returnEntityKey.m_vctAttributes = vector2;
/* 1438 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 1441 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 1444 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1449 */                     logMessage("ol:" + oPICMList);
/* 1450 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 1451 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 1455 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 1458 */                     logMessage("***acl A " + returnEntityKey
/* 1459 */                         .getReturnID());
/* 1460 */                     this.m_db.commit();
/* 1461 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 1463 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 1465 */                         .getEntityID());
/*      */                     
/* 1467 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 1469 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1475 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1487 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 1489 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1499 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 1501 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 1504 */                         vector = new Vector();
/* 1505 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 1508 */                         if (relator != null) {
/* 1509 */                           vector.addElement(relator);
/*      */                           
/* 1511 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 1515 */                           vector1
/* 1516 */                             .addElement(returnRelatorKey);
/*      */                           
/* 1518 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 1523 */                           this.m_db.commit();
/*      */                         } 
/* 1525 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1531 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 1533 */                             .getMessage());
/* 1534 */                       } catch (Exception exception) {
/* 1535 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 1537 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 1541 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1547 */                   logMessage("setReviewerValue: " + middlewareException
/* 1548 */                       .getMessage());
/* 1549 */                 } catch (Exception exception) {
/* 1550 */                   logMessage("setReviewerValue: " + exception
/* 1551 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGDIstatus() {
/* 1568 */     ReturnEntityKey returnEntityKey = null;
/* 1569 */     String str1 = null;
/* 1570 */     String str2 = null;
/* 1571 */     String str3 = null;
/* 1572 */     String str4 = null;
/* 1573 */     Text text1 = null;
/* 1574 */     Text text2 = null;
/* 1575 */     Text text3 = null;
/* 1576 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1581 */     Vector<Relator> vector = null;
/* 1582 */     ReturnRelatorKey returnRelatorKey = null;
/* 1583 */     Vector<ReturnRelatorKey> vector1 = null;
/* 1584 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 1585 */     if (entityGroup != null)
/*      */     {
/* 1587 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1588 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1589 */         if (entityItem != null) {
/* 1590 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 1591 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 1592 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 1593 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 1594 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 1596 */           str1 = arrayOfEANAttribute[1].toString();
/* 1597 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 1599 */           if (str1.equals("DI")) {
/* 1600 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 1602 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 1604 */                 .getEntityID());
/* 1605 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 1607 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 1609 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 1610 */               logMessage("***eiRelator***:" + entityItem1);
/* 1611 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 1612 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 1613 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 1614 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 1615 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 1616 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 1617 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 1619 */               str2 = arrayOfEANAttribute1[0].toString();
/* 1620 */               str3 = arrayOfEANAttribute1[1].toString();
/* 1621 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 1623 */               if (entityItem1 != null) {
/*      */                 try {
/* 1625 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1631 */                   logMessage("eiParm:" + entityItem2);
/* 1632 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1637 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 1640 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1649 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1660 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "DI", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1671 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1676 */                   logMessage("tx:" + text1);
/* 1677 */                   logMessage("tx1:" + text2);
/* 1678 */                   logMessage("tx2:" + text3);
/* 1679 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 1681 */                   Vector<Text> vector2 = new Vector();
/* 1682 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 1684 */                   if (text1 != null) {
/* 1685 */                     vector2.addElement(text1);
/* 1686 */                     vector2.addElement(text2);
/* 1687 */                     vector2.addElement(text3);
/* 1688 */                     vector2.addElement(singleFlag);
/* 1689 */                     logMessage("vctAtts:" + vector2);
/* 1690 */                     returnEntityKey.m_vctAttributes = vector2;
/* 1691 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 1694 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 1697 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1702 */                     logMessage("ol:" + oPICMList);
/* 1703 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 1704 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 1708 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 1711 */                     logMessage("***acl A " + returnEntityKey
/* 1712 */                         .getReturnID());
/* 1713 */                     this.m_db.commit();
/* 1714 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 1716 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 1718 */                         .getEntityID());
/*      */                     
/* 1720 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 1722 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1728 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1740 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 1742 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1752 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 1754 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 1757 */                         vector = new Vector();
/* 1758 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 1761 */                         if (relator != null) {
/* 1762 */                           vector.addElement(relator);
/*      */                           
/* 1764 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 1768 */                           vector1
/* 1769 */                             .addElement(returnRelatorKey);
/*      */                           
/* 1771 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 1776 */                           this.m_db.commit();
/*      */                         } 
/* 1778 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1784 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 1786 */                             .getMessage());
/* 1787 */                       } catch (Exception exception) {
/* 1788 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 1790 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 1794 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1800 */                   logMessage("setReviewerValue: " + middlewareException
/* 1801 */                       .getMessage());
/* 1802 */                 } catch (Exception exception) {
/* 1803 */                   logMessage("setReviewerValue: " + exception
/* 1804 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGGARstatus() {
/* 1821 */     ReturnEntityKey returnEntityKey = null;
/* 1822 */     String str1 = null;
/* 1823 */     String str2 = null;
/* 1824 */     String str3 = null;
/* 1825 */     String str4 = null;
/* 1826 */     Text text1 = null;
/* 1827 */     Text text2 = null;
/* 1828 */     Text text3 = null;
/* 1829 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1834 */     Vector<Relator> vector = null;
/* 1835 */     ReturnRelatorKey returnRelatorKey = null;
/* 1836 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 1838 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 1839 */     if (entityGroup != null)
/*      */     {
/* 1841 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1842 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1843 */         if (entityItem != null) {
/* 1844 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 1845 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 1846 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 1847 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 1848 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 1850 */           str1 = arrayOfEANAttribute[1].toString();
/* 1851 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 1853 */           if (str1.equals("GAR")) {
/* 1854 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 1856 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 1858 */                 .getEntityID());
/* 1859 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 1861 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 1863 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 1864 */               logMessage("***eiRelator***:" + entityItem1);
/* 1865 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 1866 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 1867 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 1868 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 1869 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 1870 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 1871 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 1873 */               str2 = arrayOfEANAttribute1[0].toString();
/* 1874 */               str3 = arrayOfEANAttribute1[1].toString();
/* 1875 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 1877 */               if (entityItem1 != null) {
/*      */                 try {
/* 1879 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1885 */                   logMessage("eiParm:" + entityItem2);
/* 1886 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1891 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 1894 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1903 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1914 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "GAR", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1925 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1930 */                   logMessage("tx:" + text1);
/* 1931 */                   logMessage("tx1:" + text2);
/* 1932 */                   logMessage("tx2:" + text3);
/* 1933 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 1935 */                   Vector<Text> vector2 = new Vector();
/* 1936 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 1938 */                   if (text1 != null) {
/* 1939 */                     vector2.addElement(text1);
/* 1940 */                     vector2.addElement(text2);
/* 1941 */                     vector2.addElement(text3);
/* 1942 */                     vector2.addElement(singleFlag);
/* 1943 */                     logMessage("vctAtts:" + vector2);
/* 1944 */                     returnEntityKey.m_vctAttributes = vector2;
/* 1945 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 1948 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 1951 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1956 */                     logMessage("ol:" + oPICMList);
/* 1957 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 1958 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 1962 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 1965 */                     logMessage("***acl A " + returnEntityKey
/* 1966 */                         .getReturnID());
/* 1967 */                     this.m_db.commit();
/* 1968 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 1970 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 1972 */                         .getEntityID());
/*      */                     
/* 1974 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 1976 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1982 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1994 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 1996 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2006 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 2008 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 2011 */                         vector = new Vector();
/* 2012 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 2015 */                         if (relator != null) {
/* 2016 */                           vector.addElement(relator);
/*      */                           
/* 2018 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 2022 */                           vector1
/* 2023 */                             .addElement(returnRelatorKey);
/*      */                           
/* 2025 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2030 */                           this.m_db.commit();
/*      */                         } 
/* 2032 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2038 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 2040 */                             .getMessage());
/* 2041 */                       } catch (Exception exception) {
/* 2042 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 2044 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 2048 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2054 */                   logMessage("setReviewerValue: " + middlewareException
/* 2055 */                       .getMessage());
/* 2056 */                 } catch (Exception exception) {
/* 2057 */                   logMessage("setReviewerValue: " + exception
/* 2058 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGIGFstatus() {
/* 2075 */     ReturnEntityKey returnEntityKey = null;
/* 2076 */     String str1 = null;
/* 2077 */     String str2 = null;
/* 2078 */     String str3 = null;
/* 2079 */     String str4 = null;
/* 2080 */     Text text1 = null;
/* 2081 */     Text text2 = null;
/* 2082 */     Text text3 = null;
/* 2083 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2088 */     Vector<Relator> vector = null;
/* 2089 */     ReturnRelatorKey returnRelatorKey = null;
/* 2090 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 2092 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 2093 */     if (entityGroup != null)
/*      */     {
/* 2095 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2096 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 2097 */         if (entityItem != null) {
/* 2098 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 2099 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 2100 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 2101 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 2102 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 2104 */           str1 = arrayOfEANAttribute[1].toString();
/* 2105 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 2107 */           if (str1.equals("IGF")) {
/* 2108 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 2110 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 2112 */                 .getEntityID());
/* 2113 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 2115 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 2117 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 2118 */               logMessage("***eiRelator***:" + entityItem1);
/* 2119 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 2120 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 2121 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 2122 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 2123 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 2124 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 2125 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 2127 */               str2 = arrayOfEANAttribute1[0].toString();
/* 2128 */               str3 = arrayOfEANAttribute1[1].toString();
/* 2129 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 2131 */               if (entityItem1 != null) {
/*      */                 try {
/* 2133 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2139 */                   logMessage("eiParm:" + entityItem2);
/* 2140 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2145 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 2148 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2157 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2168 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "IGF", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2179 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2184 */                   logMessage("tx:" + text1);
/* 2185 */                   logMessage("tx1:" + text2);
/* 2186 */                   logMessage("tx2:" + text3);
/* 2187 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 2189 */                   Vector<Text> vector2 = new Vector();
/* 2190 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 2192 */                   if (text1 != null) {
/* 2193 */                     vector2.addElement(text1);
/* 2194 */                     vector2.addElement(text2);
/* 2195 */                     vector2.addElement(text3);
/* 2196 */                     vector2.addElement(singleFlag);
/* 2197 */                     logMessage("vctAtts:" + vector2);
/* 2198 */                     returnEntityKey.m_vctAttributes = vector2;
/* 2199 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 2202 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 2205 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 2210 */                     logMessage("ol:" + oPICMList);
/* 2211 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 2212 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 2216 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 2219 */                     logMessage("***acl A " + returnEntityKey
/* 2220 */                         .getReturnID());
/* 2221 */                     this.m_db.commit();
/* 2222 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 2224 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 2226 */                         .getEntityID());
/*      */                     
/* 2228 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 2230 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2236 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2248 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 2250 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2260 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 2262 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 2265 */                         vector = new Vector();
/* 2266 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 2269 */                         if (relator != null) {
/* 2270 */                           vector.addElement(relator);
/*      */                           
/* 2272 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 2276 */                           vector1
/* 2277 */                             .addElement(returnRelatorKey);
/*      */                           
/* 2279 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2284 */                           this.m_db.commit();
/*      */                         } 
/* 2286 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2292 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 2294 */                             .getMessage());
/* 2295 */                       } catch (Exception exception) {
/* 2296 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 2298 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 2302 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2308 */                   logMessage("setReviewerValue: " + middlewareException
/* 2309 */                       .getMessage());
/* 2310 */                 } catch (Exception exception) {
/* 2311 */                   logMessage("setReviewerValue: " + exception
/* 2312 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGIMCstatus() {
/* 2329 */     ReturnEntityKey returnEntityKey = null;
/* 2330 */     String str1 = null;
/* 2331 */     String str2 = null;
/* 2332 */     String str3 = null;
/* 2333 */     String str4 = null;
/* 2334 */     Text text1 = null;
/* 2335 */     Text text2 = null;
/* 2336 */     Text text3 = null;
/* 2337 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2342 */     Vector<Relator> vector = null;
/* 2343 */     ReturnRelatorKey returnRelatorKey = null;
/* 2344 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 2346 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 2347 */     if (entityGroup != null)
/*      */     {
/* 2349 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2350 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 2351 */         if (entityItem != null) {
/* 2352 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 2353 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 2354 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 2355 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 2356 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 2358 */           str1 = arrayOfEANAttribute[1].toString();
/* 2359 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 2361 */           if (str1.equals("IMC")) {
/* 2362 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 2364 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 2366 */                 .getEntityID());
/* 2367 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 2369 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 2371 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 2372 */               logMessage("***eiRelator***:" + entityItem1);
/* 2373 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 2374 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 2375 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 2376 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 2377 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 2378 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 2379 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 2381 */               str2 = arrayOfEANAttribute1[0].toString();
/* 2382 */               str3 = arrayOfEANAttribute1[1].toString();
/* 2383 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 2385 */               if (entityItem1 != null) {
/*      */                 try {
/* 2387 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2393 */                   logMessage("eiParm:" + entityItem2);
/* 2394 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2399 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 2402 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2411 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2422 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "IMC", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2433 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2438 */                   logMessage("tx:" + text1);
/* 2439 */                   logMessage("tx1:" + text2);
/* 2440 */                   logMessage("tx2:" + text3);
/* 2441 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 2443 */                   Vector<Text> vector2 = new Vector();
/* 2444 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 2446 */                   if (text1 != null) {
/* 2447 */                     vector2.addElement(text1);
/* 2448 */                     vector2.addElement(text2);
/* 2449 */                     vector2.addElement(text3);
/* 2450 */                     vector2.addElement(singleFlag);
/* 2451 */                     logMessage("vctAtts:" + vector2);
/* 2452 */                     returnEntityKey.m_vctAttributes = vector2;
/* 2453 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 2456 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 2459 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 2464 */                     logMessage("ol:" + oPICMList);
/* 2465 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 2466 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 2470 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 2473 */                     logMessage("***acl A " + returnEntityKey
/* 2474 */                         .getReturnID());
/* 2475 */                     this.m_db.commit();
/* 2476 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 2478 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 2480 */                         .getEntityID());
/*      */                     
/* 2482 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 2484 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2490 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2502 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 2504 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2514 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 2516 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 2519 */                         vector = new Vector();
/* 2520 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 2523 */                         if (relator != null) {
/* 2524 */                           vector.addElement(relator);
/*      */                           
/* 2526 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 2530 */                           vector1
/* 2531 */                             .addElement(returnRelatorKey);
/*      */                           
/* 2533 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2538 */                           this.m_db.commit();
/*      */                         } 
/* 2540 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2546 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 2548 */                             .getMessage());
/* 2549 */                       } catch (Exception exception) {
/* 2550 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 2552 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 2556 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2562 */                   logMessage("setReviewerValue: " + middlewareException
/* 2563 */                       .getMessage());
/* 2564 */                 } catch (Exception exception) {
/* 2565 */                   logMessage("setReviewerValue: " + exception
/* 2566 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGIPMTstatus() {
/* 2583 */     ReturnEntityKey returnEntityKey = null;
/* 2584 */     String str1 = null;
/* 2585 */     String str2 = null;
/* 2586 */     String str3 = null;
/* 2587 */     String str4 = null;
/* 2588 */     Text text1 = null;
/* 2589 */     Text text2 = null;
/* 2590 */     Text text3 = null;
/* 2591 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2596 */     Vector<Relator> vector = null;
/* 2597 */     ReturnRelatorKey returnRelatorKey = null;
/* 2598 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 2600 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 2601 */     if (entityGroup != null)
/*      */     {
/* 2603 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2604 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 2605 */         if (entityItem != null) {
/* 2606 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 2607 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 2608 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 2609 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 2610 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 2612 */           str1 = arrayOfEANAttribute[1].toString();
/* 2613 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 2615 */           if (str1.equals("IPMT")) {
/* 2616 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 2618 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 2620 */                 .getEntityID());
/* 2621 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 2623 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 2625 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 2626 */               logMessage("***eiRelator***:" + entityItem1);
/* 2627 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 2628 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 2629 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 2630 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 2631 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 2632 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 2633 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 2635 */               str2 = arrayOfEANAttribute1[0].toString();
/* 2636 */               str3 = arrayOfEANAttribute1[1].toString();
/* 2637 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 2639 */               if (entityItem1 != null) {
/*      */                 try {
/* 2641 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2647 */                   logMessage("eiParm:" + entityItem2);
/* 2648 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2653 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 2656 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2665 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2676 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "IPMT", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2687 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2692 */                   logMessage("tx:" + text1);
/* 2693 */                   logMessage("tx1:" + text2);
/* 2694 */                   logMessage("tx2:" + text3);
/* 2695 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 2697 */                   Vector<Text> vector2 = new Vector();
/* 2698 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 2700 */                   if (text1 != null) {
/* 2701 */                     vector2.addElement(text1);
/* 2702 */                     vector2.addElement(text2);
/* 2703 */                     vector2.addElement(text3);
/* 2704 */                     vector2.addElement(singleFlag);
/* 2705 */                     logMessage("vctAtts:" + vector2);
/* 2706 */                     returnEntityKey.m_vctAttributes = vector2;
/* 2707 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 2710 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 2713 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 2718 */                     logMessage("ol:" + oPICMList);
/* 2719 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 2720 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 2724 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 2727 */                     logMessage("***acl A " + returnEntityKey
/* 2728 */                         .getReturnID());
/* 2729 */                     this.m_db.commit();
/* 2730 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 2732 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 2734 */                         .getEntityID());
/*      */                     
/* 2736 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 2738 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2744 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2756 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 2758 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2768 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 2770 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 2773 */                         vector = new Vector();
/* 2774 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 2777 */                         if (relator != null) {
/* 2778 */                           vector.addElement(relator);
/*      */                           
/* 2780 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 2784 */                           vector1
/* 2785 */                             .addElement(returnRelatorKey);
/*      */                           
/* 2787 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2792 */                           this.m_db.commit();
/*      */                         } 
/* 2794 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2800 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 2802 */                             .getMessage());
/* 2803 */                       } catch (Exception exception) {
/* 2804 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 2806 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 2810 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2816 */                   logMessage("setReviewerValue: " + middlewareException
/* 2817 */                       .getMessage());
/* 2818 */                 } catch (Exception exception) {
/* 2819 */                   logMessage("setReviewerValue: " + exception
/* 2820 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGLEGALstatus() {
/* 2837 */     ReturnEntityKey returnEntityKey = null;
/* 2838 */     String str1 = null;
/* 2839 */     String str2 = null;
/* 2840 */     String str3 = null;
/* 2841 */     String str4 = null;
/* 2842 */     Text text1 = null;
/* 2843 */     Text text2 = null;
/* 2844 */     Text text3 = null;
/* 2845 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2850 */     Vector<Relator> vector = null;
/* 2851 */     ReturnRelatorKey returnRelatorKey = null;
/* 2852 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 2854 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 2855 */     if (entityGroup != null)
/*      */     {
/* 2857 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2858 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 2859 */         if (entityItem != null) {
/* 2860 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 2861 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 2862 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 2863 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 2864 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 2866 */           str1 = arrayOfEANAttribute[1].toString();
/* 2867 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 2869 */           if (str1.equals("LEGAL")) {
/* 2870 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 2872 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 2874 */                 .getEntityID());
/* 2875 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 2877 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 2879 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 2880 */               logMessage("***eiRelator***:" + entityItem1);
/* 2881 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 2882 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 2883 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 2884 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 2885 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 2886 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 2887 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 2889 */               str2 = arrayOfEANAttribute1[0].toString();
/* 2890 */               str3 = arrayOfEANAttribute1[1].toString();
/* 2891 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 2893 */               if (entityItem1 != null) {
/*      */                 try {
/* 2895 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2901 */                   logMessage("eiParm:" + entityItem2);
/* 2902 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2907 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 2910 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2919 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2930 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "LEGAL", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2941 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2946 */                   logMessage("tx:" + text1);
/* 2947 */                   logMessage("tx1:" + text2);
/* 2948 */                   logMessage("tx2:" + text3);
/* 2949 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 2951 */                   Vector<Text> vector2 = new Vector();
/* 2952 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 2954 */                   if (text1 != null) {
/* 2955 */                     vector2.addElement(text1);
/* 2956 */                     vector2.addElement(text2);
/* 2957 */                     vector2.addElement(text3);
/* 2958 */                     vector2.addElement(singleFlag);
/* 2959 */                     logMessage("vctAtts:" + vector2);
/* 2960 */                     returnEntityKey.m_vctAttributes = vector2;
/* 2961 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 2964 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 2967 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 2972 */                     logMessage("ol:" + oPICMList);
/* 2973 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 2974 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 2978 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 2981 */                     logMessage("***acl A " + returnEntityKey
/* 2982 */                         .getReturnID());
/* 2983 */                     this.m_db.commit();
/* 2984 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 2986 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 2988 */                         .getEntityID());
/*      */                     
/* 2990 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 2992 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2998 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3010 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 3012 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3022 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 3024 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 3027 */                         vector = new Vector();
/* 3028 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 3031 */                         if (relator != null) {
/* 3032 */                           vector.addElement(relator);
/*      */                           
/* 3034 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 3038 */                           vector1
/* 3039 */                             .addElement(returnRelatorKey);
/*      */                           
/* 3041 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 3046 */                           this.m_db.commit();
/*      */                         } 
/* 3048 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3054 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 3056 */                             .getMessage());
/* 3057 */                       } catch (Exception exception) {
/* 3058 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 3060 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 3064 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3070 */                   logMessage("setReviewerValue: " + middlewareException
/* 3071 */                       .getMessage());
/* 3072 */                 } catch (Exception exception) {
/* 3073 */                   logMessage("setReviewerValue: " + exception
/* 3074 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGMRstatus() {
/* 3091 */     ReturnEntityKey returnEntityKey = null;
/* 3092 */     String str1 = null;
/* 3093 */     String str2 = null;
/* 3094 */     String str3 = null;
/* 3095 */     String str4 = null;
/* 3096 */     Text text1 = null;
/* 3097 */     Text text2 = null;
/* 3098 */     Text text3 = null;
/* 3099 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3104 */     Vector<Relator> vector = null;
/* 3105 */     ReturnRelatorKey returnRelatorKey = null;
/* 3106 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 3108 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 3109 */     if (entityGroup != null)
/*      */     {
/* 3111 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 3112 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 3113 */         if (entityItem != null) {
/* 3114 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 3115 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 3116 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 3117 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 3118 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 3120 */           str1 = arrayOfEANAttribute[1].toString();
/* 3121 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 3123 */           if (str1.equals("MR")) {
/* 3124 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 3126 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 3128 */                 .getEntityID());
/* 3129 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 3131 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 3133 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 3134 */               logMessage("***eiRelator***:" + entityItem1);
/* 3135 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 3136 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 3137 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 3138 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 3139 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 3140 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 3141 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 3143 */               str2 = arrayOfEANAttribute1[0].toString();
/* 3144 */               str3 = arrayOfEANAttribute1[1].toString();
/* 3145 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 3147 */               if (entityItem1 != null) {
/*      */                 try {
/* 3149 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3155 */                   logMessage("eiParm:" + entityItem2);
/* 3156 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3161 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 3164 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3173 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3184 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "MR", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3195 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3200 */                   logMessage("tx:" + text1);
/* 3201 */                   logMessage("tx1:" + text2);
/* 3202 */                   logMessage("tx2:" + text3);
/* 3203 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 3205 */                   Vector<Text> vector2 = new Vector();
/* 3206 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 3208 */                   if (text1 != null) {
/* 3209 */                     vector2.addElement(text1);
/* 3210 */                     vector2.addElement(text2);
/* 3211 */                     vector2.addElement(text3);
/* 3212 */                     vector2.addElement(singleFlag);
/* 3213 */                     logMessage("vctAtts:" + vector2);
/* 3214 */                     returnEntityKey.m_vctAttributes = vector2;
/* 3215 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 3218 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 3221 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 3226 */                     logMessage("ol:" + oPICMList);
/* 3227 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 3228 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 3232 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 3235 */                     logMessage("***acl A " + returnEntityKey
/* 3236 */                         .getReturnID());
/* 3237 */                     this.m_db.commit();
/* 3238 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 3240 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 3242 */                         .getEntityID());
/*      */                     
/* 3244 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 3246 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3252 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3264 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 3266 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3276 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 3278 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 3281 */                         vector = new Vector();
/* 3282 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 3285 */                         if (relator != null) {
/* 3286 */                           vector.addElement(relator);
/*      */                           
/* 3288 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 3292 */                           vector1
/* 3293 */                             .addElement(returnRelatorKey);
/*      */                           
/* 3295 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 3300 */                           this.m_db.commit();
/*      */                         } 
/* 3302 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3308 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 3310 */                             .getMessage());
/* 3311 */                       } catch (Exception exception) {
/* 3312 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 3314 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 3318 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3324 */                   logMessage("setReviewerValue: " + middlewareException
/* 3325 */                       .getMessage());
/* 3326 */                 } catch (Exception exception) {
/* 3327 */                   logMessage("setReviewerValue: " + exception
/* 3328 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGODMstatus() {
/* 3345 */     ReturnEntityKey returnEntityKey = null;
/* 3346 */     String str1 = null;
/* 3347 */     String str2 = null;
/* 3348 */     String str3 = null;
/* 3349 */     String str4 = null;
/* 3350 */     Text text1 = null;
/* 3351 */     Text text2 = null;
/* 3352 */     Text text3 = null;
/* 3353 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3358 */     Vector<Relator> vector = null;
/* 3359 */     ReturnRelatorKey returnRelatorKey = null;
/* 3360 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 3362 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 3363 */     if (entityGroup != null)
/*      */     {
/* 3365 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 3366 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 3367 */         if (entityItem != null) {
/* 3368 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 3369 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 3370 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 3371 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 3372 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 3374 */           str1 = arrayOfEANAttribute[1].toString();
/* 3375 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 3377 */           if (str1.equals("ODM")) {
/* 3378 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 3380 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 3382 */                 .getEntityID());
/* 3383 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 3385 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 3387 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 3388 */               logMessage("***eiRelator***:" + entityItem1);
/* 3389 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 3390 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 3391 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 3392 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 3393 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 3394 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 3395 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 3397 */               str2 = arrayOfEANAttribute1[0].toString();
/* 3398 */               str3 = arrayOfEANAttribute1[1].toString();
/* 3399 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 3401 */               if (entityItem1 != null) {
/*      */                 try {
/* 3403 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3409 */                   logMessage("eiParm:" + entityItem2);
/* 3410 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3415 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 3418 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3427 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3438 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "ODM", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3449 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3454 */                   logMessage("tx:" + text1);
/* 3455 */                   logMessage("tx1:" + text2);
/* 3456 */                   logMessage("tx2:" + text3);
/* 3457 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 3459 */                   Vector<Text> vector2 = new Vector();
/* 3460 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 3462 */                   if (text1 != null) {
/* 3463 */                     vector2.addElement(text1);
/* 3464 */                     vector2.addElement(text2);
/* 3465 */                     vector2.addElement(text3);
/* 3466 */                     vector2.addElement(singleFlag);
/* 3467 */                     logMessage("vctAtts:" + vector2);
/* 3468 */                     returnEntityKey.m_vctAttributes = vector2;
/* 3469 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 3472 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 3475 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 3480 */                     logMessage("ol:" + oPICMList);
/* 3481 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 3482 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 3486 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 3489 */                     logMessage("***acl A " + returnEntityKey
/* 3490 */                         .getReturnID());
/* 3491 */                     this.m_db.commit();
/* 3492 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 3494 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 3496 */                         .getEntityID());
/*      */                     
/* 3498 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 3500 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3506 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3518 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 3520 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3530 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 3532 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 3535 */                         vector = new Vector();
/* 3536 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 3539 */                         if (relator != null) {
/* 3540 */                           vector.addElement(relator);
/*      */                           
/* 3542 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 3546 */                           vector1
/* 3547 */                             .addElement(returnRelatorKey);
/*      */                           
/* 3549 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 3554 */                           this.m_db.commit();
/*      */                         } 
/* 3556 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3562 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 3564 */                             .getMessage());
/* 3565 */                       } catch (Exception exception) {
/* 3566 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 3568 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 3572 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3578 */                   logMessage("setReviewerValue: " + middlewareException
/* 3579 */                       .getMessage());
/* 3580 */                 } catch (Exception exception) {
/* 3581 */                   logMessage("setReviewerValue: " + exception
/* 3582 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGPDTstatus() {
/* 3599 */     ReturnEntityKey returnEntityKey = null;
/* 3600 */     String str1 = null;
/* 3601 */     String str2 = null;
/* 3602 */     String str3 = null;
/* 3603 */     String str4 = null;
/* 3604 */     Text text1 = null;
/* 3605 */     Text text2 = null;
/* 3606 */     Text text3 = null;
/* 3607 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3612 */     Vector<Relator> vector = null;
/* 3613 */     ReturnRelatorKey returnRelatorKey = null;
/* 3614 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 3616 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 3617 */     if (entityGroup != null)
/*      */     {
/* 3619 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 3620 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 3621 */         if (entityItem != null) {
/* 3622 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 3623 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 3624 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 3625 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 3626 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 3628 */           str1 = arrayOfEANAttribute[1].toString();
/* 3629 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 3631 */           if (str1.equals("PDT")) {
/* 3632 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 3634 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 3636 */                 .getEntityID());
/* 3637 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 3639 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 3641 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 3642 */               logMessage("***eiRelator***:" + entityItem1);
/* 3643 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 3644 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 3645 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 3646 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 3647 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 3648 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 3649 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 3651 */               str2 = arrayOfEANAttribute1[0].toString();
/* 3652 */               str3 = arrayOfEANAttribute1[1].toString();
/* 3653 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 3655 */               if (entityItem1 != null) {
/*      */                 try {
/* 3657 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3663 */                   logMessage("eiParm:" + entityItem2);
/* 3664 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3669 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 3672 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3681 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3692 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "PDT", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3703 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3708 */                   logMessage("tx:" + text1);
/* 3709 */                   logMessage("tx1:" + text2);
/* 3710 */                   logMessage("tx2:" + text3);
/* 3711 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 3713 */                   Vector<Text> vector2 = new Vector();
/* 3714 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 3716 */                   if (text1 != null) {
/* 3717 */                     vector2.addElement(text1);
/* 3718 */                     vector2.addElement(text2);
/* 3719 */                     vector2.addElement(text3);
/* 3720 */                     vector2.addElement(singleFlag);
/* 3721 */                     logMessage("vctAtts:" + vector2);
/* 3722 */                     returnEntityKey.m_vctAttributes = vector2;
/* 3723 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 3726 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 3729 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 3734 */                     logMessage("ol:" + oPICMList);
/* 3735 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 3736 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 3740 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 3743 */                     logMessage("***acl A " + returnEntityKey
/* 3744 */                         .getReturnID());
/* 3745 */                     this.m_db.commit();
/* 3746 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 3748 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 3750 */                         .getEntityID());
/*      */                     
/* 3752 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 3754 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3760 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3772 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 3774 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3784 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 3786 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 3789 */                         vector = new Vector();
/* 3790 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 3793 */                         if (relator != null) {
/* 3794 */                           vector.addElement(relator);
/*      */                           
/* 3796 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 3800 */                           vector1
/* 3801 */                             .addElement(returnRelatorKey);
/*      */                           
/* 3803 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 3808 */                           this.m_db.commit();
/*      */                         } 
/* 3810 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 3816 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 3818 */                             .getMessage());
/* 3819 */                       } catch (Exception exception) {
/* 3820 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 3822 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 3826 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3832 */                   logMessage("setReviewerValue: " + middlewareException
/* 3833 */                       .getMessage());
/* 3834 */                 } catch (Exception exception) {
/* 3835 */                   logMessage("setReviewerValue: " + exception
/* 3836 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGPRstatus() {
/* 3853 */     ReturnEntityKey returnEntityKey = null;
/* 3854 */     String str1 = null;
/* 3855 */     String str2 = null;
/* 3856 */     String str3 = null;
/* 3857 */     String str4 = null;
/* 3858 */     Text text1 = null;
/* 3859 */     Text text2 = null;
/* 3860 */     Text text3 = null;
/* 3861 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3866 */     Vector<Relator> vector = null;
/* 3867 */     ReturnRelatorKey returnRelatorKey = null;
/* 3868 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 3870 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 3871 */     if (entityGroup != null)
/*      */     {
/* 3873 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 3874 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 3875 */         if (entityItem != null) {
/* 3876 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 3877 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 3878 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 3879 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 3880 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 3882 */           str1 = arrayOfEANAttribute[1].toString();
/* 3883 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 3885 */           if (str1.equals("PR")) {
/* 3886 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 3888 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 3890 */                 .getEntityID());
/* 3891 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 3893 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 3895 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 3896 */               logMessage("***eiRelator***:" + entityItem1);
/* 3897 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 3898 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 3899 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 3900 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 3901 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 3902 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 3903 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 3905 */               str2 = arrayOfEANAttribute1[0].toString();
/* 3906 */               str3 = arrayOfEANAttribute1[1].toString();
/* 3907 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 3909 */               if (entityItem1 != null) {
/*      */                 try {
/* 3911 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3917 */                   logMessage("eiParm:" + entityItem2);
/* 3918 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3923 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 3926 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3935 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3946 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "PR", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3957 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3962 */                   logMessage("tx:" + text1);
/* 3963 */                   logMessage("tx1:" + text2);
/* 3964 */                   logMessage("tx2:" + text3);
/* 3965 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 3967 */                   Vector<Text> vector2 = new Vector();
/* 3968 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 3970 */                   if (text1 != null) {
/* 3971 */                     vector2.addElement(text1);
/* 3972 */                     vector2.addElement(text2);
/* 3973 */                     vector2.addElement(text3);
/* 3974 */                     vector2.addElement(singleFlag);
/* 3975 */                     logMessage("vctAtts:" + vector2);
/* 3976 */                     returnEntityKey.m_vctAttributes = vector2;
/* 3977 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 3980 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 3983 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 3988 */                     logMessage("ol:" + oPICMList);
/* 3989 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 3990 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 3994 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 3997 */                     logMessage("***acl A " + returnEntityKey
/* 3998 */                         .getReturnID());
/* 3999 */                     this.m_db.commit();
/* 4000 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 4002 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 4004 */                         .getEntityID());
/*      */                     
/* 4006 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 4008 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 4014 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 4026 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 4028 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 4038 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 4040 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 4043 */                         vector = new Vector();
/* 4044 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 4047 */                         if (relator != null) {
/* 4048 */                           vector.addElement(relator);
/*      */                           
/* 4050 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 4054 */                           vector1
/* 4055 */                             .addElement(returnRelatorKey);
/*      */                           
/* 4057 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 4062 */                           this.m_db.commit();
/*      */                         } 
/* 4064 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 4070 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 4072 */                             .getMessage());
/* 4073 */                       } catch (Exception exception) {
/* 4074 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 4076 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 4080 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 4086 */                   logMessage("setReviewerValue: " + middlewareException
/* 4087 */                       .getMessage());
/* 4088 */                 } catch (Exception exception) {
/* 4089 */                   logMessage("setReviewerValue: " + exception
/* 4090 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPWGWWSDstatus() {
/* 4107 */     ReturnEntityKey returnEntityKey = null;
/* 4108 */     String str1 = null;
/* 4109 */     String str2 = null;
/* 4110 */     String str3 = null;
/* 4111 */     String str4 = null;
/* 4112 */     Text text1 = null;
/* 4113 */     Text text2 = null;
/* 4114 */     Text text3 = null;
/* 4115 */     SingleFlag singleFlag = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4120 */     Vector<Relator> vector = null;
/* 4121 */     ReturnRelatorKey returnRelatorKey = null;
/* 4122 */     Vector<ReturnRelatorKey> vector1 = null;
/*      */     
/* 4124 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OPWG");
/* 4125 */     if (entityGroup != null)
/*      */     {
/* 4127 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 4128 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 4129 */         if (entityItem != null) {
/* 4130 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[2];
/* 4131 */           arrayOfEANAttribute[0] = entityItem.getAttribute("NAME");
/* 4132 */           arrayOfEANAttribute[1] = entityItem.getAttribute("ROLECODE");
/* 4133 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 4134 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/*      */           
/* 4136 */           str1 = arrayOfEANAttribute[1].toString();
/* 4137 */           logMessage("strStatus for OPWG:" + str1);
/*      */           
/* 4139 */           if (str1.equals("WWSD")) {
/* 4140 */             println("<br><br><b>" + entityGroup
/*      */                 
/* 4142 */                 .getLongDescription() + " Entity ID: " + entityItem
/*      */                 
/* 4144 */                 .getEntityID());
/* 4145 */             println(displayAttributes(entityItem, entityGroup, true));
/*      */             
/* 4147 */             for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/*      */               
/* 4149 */               EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/* 4150 */               logMessage("***eiRelator***:" + entityItem1);
/* 4151 */               EANAttribute[] arrayOfEANAttribute1 = new EANAttribute[3];
/* 4152 */               arrayOfEANAttribute1[0] = entityItem1.getAttribute("FIRSTNAME");
/* 4153 */               arrayOfEANAttribute1[1] = entityItem1.getAttribute("LASTNAME");
/* 4154 */               arrayOfEANAttribute1[2] = entityItem1.getAttribute("USERTOKEN");
/* 4155 */               logMessage("opatts[0]:" + arrayOfEANAttribute1[0]);
/* 4156 */               logMessage("opatts[1]:" + arrayOfEANAttribute1[1]);
/* 4157 */               logMessage("opatts[2]:" + arrayOfEANAttribute1[2]);
/*      */               
/* 4159 */               str2 = arrayOfEANAttribute1[0].toString();
/* 4160 */               str3 = arrayOfEANAttribute1[1].toString();
/* 4161 */               str4 = arrayOfEANAttribute1[2].toString();
/*      */               
/* 4163 */               if (entityItem1 != null) {
/*      */                 try {
/* 4165 */                   EntityItem entityItem2 = new EntityItem(null, this.m_prof, "REVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 4171 */                   logMessage("eiParm:" + entityItem2);
/* 4172 */                   returnEntityKey = new ReturnEntityKey("REVIEWER", 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 4177 */                   logMessage("rek:" + returnEntityKey);
/*      */ 
/*      */                   
/* 4180 */                   text1 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, INTERNETID, str4, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 4189 */                   text2 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, NAME, str2 + " " + str3, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 4200 */                   text3 = new Text(this.m_prof.getEnterprise(), "REVIEWER", 0, ROLECODE, "WWSD", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 4211 */                   singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "REVIEWER", returnEntityKey.getEntityID(), REVIEWERSTATUS, "0010", 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 4216 */                   logMessage("tx:" + text1);
/* 4217 */                   logMessage("tx1:" + text2);
/* 4218 */                   logMessage("tx2:" + text3);
/* 4219 */                   logMessage("sf:" + singleFlag);
/*      */                   
/* 4221 */                   Vector<Text> vector2 = new Vector();
/* 4222 */                   Vector<ReturnEntityKey> vector3 = new Vector();
/*      */                   
/* 4224 */                   if (text1 != null) {
/* 4225 */                     vector2.addElement(text1);
/* 4226 */                     vector2.addElement(text2);
/* 4227 */                     vector2.addElement(text3);
/* 4228 */                     vector2.addElement(singleFlag);
/* 4229 */                     logMessage("vctAtts:" + vector2);
/* 4230 */                     returnEntityKey.m_vctAttributes = vector2;
/* 4231 */                     logMessage("rek.m_vctAttributes:" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 4234 */                     vector3.addElement(returnEntityKey);
/*      */ 
/*      */                     
/* 4237 */                     OPICMList oPICMList = this.m_db.update(this.m_prof, vector3, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 4242 */                     logMessage("ol:" + oPICMList);
/* 4243 */                     returnEntityKey = (ReturnEntityKey)oPICMList.getAt(0);
/* 4244 */                     logMessage("***rek.stuff()" + returnEntityKey.m_strEntityType + returnEntityKey.m_iEntityID);
/*      */ 
/*      */ 
/*      */                     
/* 4248 */                     logMessage("***rek.stuff()1" + returnEntityKey.m_vctAttributes);
/*      */ 
/*      */                     
/* 4251 */                     logMessage("***acl A " + returnEntityKey
/* 4252 */                         .getReturnID());
/* 4253 */                     this.m_db.commit();
/* 4254 */                     logMessage("eiParm.getEntityID()" + entityItem2
/*      */                         
/* 4256 */                         .getEntityType() + " " + entityItem2
/*      */                         
/* 4258 */                         .getEntityID());
/*      */                     
/* 4260 */                     if (returnEntityKey != null) {
/*      */                       try {
/* 4262 */                         EntityItem entityItem3 = new EntityItem(null, this.m_prof, "ANNREVREVIEWER", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 4268 */                         logMessage("eiRelator1:" + entityItem3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 4280 */                         returnRelatorKey = new ReturnRelatorKey("ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), true);
/*      */                         
/* 4282 */                         logMessage("rek1:" + returnRelatorKey);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 4292 */                         Relator relator = new Relator(this.m_prof.getEnterprise(), "ANNREVREVIEWER", 0, "ANNREVIEW", this.m_eiParent.getEntityID(), "REVIEWER", returnEntityKey.getReturnID(), this.m_cbOn);
/*      */                         
/* 4294 */                         logMessage("relator1:" + relator);
/*      */ 
/*      */                         
/* 4297 */                         vector = new Vector();
/* 4298 */                         vector1 = new Vector();
/*      */ 
/*      */                         
/* 4301 */                         if (relator != null) {
/* 4302 */                           vector.addElement(relator);
/*      */                           
/* 4304 */                           logMessage("vctAtts1:" + vector);
/*      */ 
/*      */ 
/*      */                           
/* 4308 */                           vector1
/* 4309 */                             .addElement(returnRelatorKey);
/*      */                           
/* 4311 */                           this.m_db.update(this.m_prof, vector1, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 4316 */                           this.m_db.commit();
/*      */                         } 
/* 4318 */                       } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 4324 */                         logMessage("setReviewerValue: " + middlewareException
/*      */                             
/* 4326 */                             .getMessage());
/* 4327 */                       } catch (Exception exception) {
/* 4328 */                         logMessage("setReviewerValue: " + exception
/*      */                             
/* 4330 */                             .getMessage());
/*      */                       } 
/*      */                     }
/*      */                   } 
/* 4334 */                 } catch (MiddlewareException middlewareException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 4340 */                   logMessage("setReviewerValue: " + middlewareException
/* 4341 */                       .getMessage());
/* 4342 */                 } catch (Exception exception) {
/* 4343 */                   logMessage("setReviewerValue: " + exception
/* 4344 */                       .getMessage());
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*      */   public void checkOPstatus() {
/* 4361 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("OP");
/* 4362 */     if (entityGroup != null)
/*      */     {
/* 4364 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 4365 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 4366 */         if (entityItem != null) {
/* 4367 */           EANAttribute[] arrayOfEANAttribute = new EANAttribute[3];
/* 4368 */           arrayOfEANAttribute[0] = entityItem.getAttribute(FIRSTNAME);
/* 4369 */           arrayOfEANAttribute[1] = entityItem.getAttribute(LASTNAME);
/* 4370 */           arrayOfEANAttribute[2] = entityItem.getAttribute(USERNAME);
/* 4371 */           logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/* 4372 */           logMessage("atts[1]:" + arrayOfEANAttribute[1]);
/* 4373 */           logMessage("atts[2]:" + arrayOfEANAttribute[2]);
/* 4374 */           println("<br><br><b>" + entityGroup
/*      */               
/* 4376 */               .getLongDescription() + " Entity ID: " + entityItem
/*      */               
/* 4378 */               .getEntityID());
/* 4379 */           println(displayAttributes(entityItem, entityGroup, true));
/*      */         } 
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
/*      */   public void displayHeader(EntityGroup paramEntityGroup, EntityItem paramEntityItem) {
/* 4392 */     if (paramEntityGroup != null && paramEntityGroup != null) {
/* 4393 */       println("<FONT SIZE=6><b>" + paramEntityGroup
/*      */           
/* 4395 */           .getLongDescription() + "</b></FONT><br>");
/*      */ 
/*      */       
/* 4398 */       println(displayNavAttributes(paramEntityItem, paramEntityGroup));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setControlBlock() {
/* 4406 */     this.m_strNow = getNow();
/* 4407 */     this.m_strForever = getForever();
/* 4408 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4415 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
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
/*      */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 4427 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 4438 */     return "<b>Announcement Review ABR Report</b>";
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
/* 4449 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/* 4458 */     return new String("1.67");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/* 4468 */     return "REVIEWERABR01.java,v 1.67 2008/01/30 19:39:17 wendy Exp";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 4478 */     return getVersion();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\REVIEWERABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */