/*      */ package COM.ibm.eannounce.abr.rfa;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.ReportFormatter;
/*      */ import COM.ibm.eannounce.abr.util.XMLtoGML;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.GeneralAreaGroup;
/*      */ import COM.ibm.eannounce.objects.GeneralAreaItem;
/*      */ import COM.ibm.eannounce.objects.GeneralAreaList;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringReader;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.Array;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import javax.xml.transform.stream.StreamSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RFAHWABR01
/*      */   extends PokBaseABR
/*      */ {
/*  434 */   private final String DTDFILEPATH = ABRServerProperties.getDTDFilePath("RFAHWABR01");
/*  435 */   private final String BREAK_INDICATOR = "$$BREAKHERE$$";
/*  436 */   private final String strWorldwideTag = "US, AP, CAN, EMEA, LA";
/*      */ 
/*      */   
/*  439 */   EntityGroup grpAnnouncement = null;
/*  440 */   EntityItem eiAnnounce = null;
/*  441 */   EntityGroup grpAnnDeliv = null;
/*  442 */   EntityItem eiAnnDeliv = null;
/*  443 */   EntityGroup grpAnnPara = null;
/*  444 */   EntityItem eiAnnPara = null;
/*  445 */   EntityGroup grpAnnDepend = null;
/*  446 */   EntityItem eiAnnDepend = null;
/*  447 */   EntityGroup grpParamCode = null;
/*  448 */   EntityItem eiParamCode = null;
/*  449 */   EntityGroup grpDependCode = null;
/*  450 */   EntityItem eiDependCode = null;
/*  451 */   EntityGroup grpAnnProj = null;
/*  452 */   EntityItem eiAnnProj = null;
/*  453 */   EntityGroup grpErrataCause = null;
/*  454 */   EntityItem eiErrataCause = null;
/*  455 */   EntityGroup grpOrganUnit = null;
/*  456 */   EntityItem eiOrganUnit = null;
/*  457 */   EntityGroup grpOP = null;
/*  458 */   EntityItem eiOP = null;
/*  459 */   EntityGroup grpChannel = null;
/*  460 */   EntityItem eiChannel = null;
/*  461 */   EntityGroup grpPDSQuestions = null;
/*  462 */   EntityItem eiPDSQuestions = null;
/*  463 */   EntityGroup grpPriceInfo = null;
/*  464 */   EntityItem eiPriceInfo = null;
/*  465 */   EntityGroup grpCommOffInfo = null;
/*  466 */   EntityItem eiCommOFfInfo = null;
/*  467 */   EntityGroup grpDerive = null;
/*  468 */   EntityItem eiDerive = null;
/*  469 */   EntityGroup grpAnnReview = null;
/*  470 */   EntityItem eiAnnReview = null;
/*  471 */   EntityGroup grpConfigurator = null;
/*  472 */   EntityItem eiConfigurator = null;
/*  473 */   EntityGroup grpAnnToConfig = null;
/*  474 */   EntityItem eiAnnToConfig = null;
/*  475 */   EntityGroup grpAnnToOrgUnit = null;
/*  476 */   EntityItem eiAnnToOrgUnit = null;
/*  477 */   EntityGroup grpAnnToOP = null;
/*  478 */   EntityItem eiAnnToOP = null;
/*  479 */   EntityGroup grpCofAvail = null;
/*  480 */   EntityItem eiCofAvail = null;
/*  481 */   EntityGroup grpPdsQuestions = null;
/*  482 */   EntityItem eiPdsQuestions = null;
/*  483 */   EntityGroup grpCommOfIvo = null;
/*  484 */   EntityItem eiCommOFIvo = null;
/*  485 */   EntityGroup grpCommOF = null;
/*  486 */   EntityItem eiCommOF = null;
/*  487 */   EntityGroup grpRelatedANN = null;
/*  488 */   EntityItem eiRelatedANN = null;
/*  489 */   EntityGroup grpGeneralArea = null;
/*  490 */   EntityItem eiGeneralArea = null;
/*  491 */   EntityGroup grpAvail = null;
/*  492 */   EntityItem eiAvail = null;
/*  493 */   EntityGroup grpOrderOF = null;
/*  494 */   EntityItem eiOrderOF = null;
/*  495 */   EntityGroup grpCrossSell = null;
/*  496 */   EntityItem eiCrossSell = null;
/*  497 */   EntityGroup grpUpSell = null;
/*  498 */   EntityItem eiUpSell = null;
/*  499 */   EntityGroup grpStdAmendText = null;
/*  500 */   EntityItem eiStdAmendText = null;
/*  501 */   EntityGroup grpCOFCrypto = null;
/*  502 */   EntityItem eiCOFCrypto = null;
/*  503 */   EntityGroup grpOOFCrypto = null;
/*  504 */   EntityItem eiOOFCrypto = null;
/*  505 */   EntityGroup grpCrypto = null;
/*  506 */   EntityItem eiCrypto = null;
/*  507 */   EntityGroup grpCofOrganUnit = null;
/*  508 */   EntityItem eiCofOrganUnit = null;
/*  509 */   EntityGroup grpIndividual = null;
/*  510 */   EntityItem eiIndividual = null;
/*  511 */   EntityGroup grpPublication = null;
/*  512 */   EntityItem eiPublication = null;
/*  513 */   EntityGroup grpEducation = null;
/*  514 */   EntityItem eiEducation = null;
/*  515 */   EntityGroup grpAnnEducation = null;
/*  516 */   EntityItem eiAnnEducation = null;
/*  517 */   EntityGroup grpIvocat = null;
/*  518 */   EntityItem eiIvocat = null;
/*  519 */   EntityGroup grpBoilPlateText = null;
/*  520 */   EntityItem eiBoilPlateText = null;
/*  521 */   EntityGroup grpCatIncl = null;
/*  522 */   EntityItem eiCatIncl = null;
/*  523 */   EntityGroup grpAlternateOF = null;
/*  524 */   EntityItem eiAlternateOF = null;
/*  525 */   EntityGroup grpCofBPExhibit = null;
/*  526 */   EntityItem eiCofBPExhibit = null;
/*  527 */   EntityGroup grpBPExhibit = null;
/*  528 */   EntityItem eiBPExhibit = null;
/*  529 */   EntityGroup grpCofPubs = null;
/*  530 */   EntityItem eiCofPubs = null;
/*  531 */   EntityGroup grpEnvirinfo = null;
/*  532 */   EntityItem eiEnvirinfo = null;
/*  533 */   EntityGroup grpAltEnvirinfo = null;
/*  534 */   EntityItem eiAltEnvirinfo = null;
/*  535 */   EntityGroup grpPackaging = null;
/*  536 */   EntityItem eiPackaging = null;
/*  537 */   EntityGroup grpSalesmanchg = null;
/*  538 */   EntityItem eiSalesmanchg = null;
/*  539 */   EntityGroup grpAnnSalesmanchg = null;
/*  540 */   EntityItem eiAnnSalesmanchg = null;
/*  541 */   EntityGroup grpOrderOFAvail = null;
/*  542 */   EntityItem eiOrderOFAvail = null;
/*  543 */   EntityGroup grpOrganUnitIndiv = null;
/*  544 */   EntityItem eiOrganUnitIndiv = null;
/*  545 */   EntityGroup grpAnnToAnnDeliv = null;
/*  546 */   EntityItem eiAnnToAnnDeliv = null;
/*  547 */   EntityGroup grpAnnDelReqTrans = null;
/*  548 */   EntityItem eiAnnDelReqTrans = null;
/*  549 */   EntityGroup grpEmeaTranslation = null;
/*  550 */   EntityItem eiEmeaTranslation = null;
/*  551 */   EntityGroup grpAnnToDescArea = null;
/*  552 */   EntityItem eiAnnToDescArea = null;
/*  553 */   EntityGroup grpCofPrice = null;
/*  554 */   EntityItem eiCofPrice = null;
/*  555 */   EntityGroup grpCofChannel = null;
/*  556 */   EntityItem eiCofChannel = null;
/*  557 */   EntityItem eiCofCofMgmtGrp = null;
/*  558 */   EntityItem eiCofOofMgmtGrp = null;
/*  559 */   EntityItem eiOofMemberCofOmg = null;
/*  560 */   EntityItem eiCofMemberCofOmg = null;
/*  561 */   EntityItem eiCofShip = null;
/*  562 */   EntityItem eiShipInfo = null;
/*  563 */   EntityItem eiAnnCofa = null;
/*  564 */   EntityGroup grpAnnCofa = null;
/*  565 */   EntityGroup grpAnnCofOffMgmtGrpa = null;
/*  566 */   EntityItem eiAnnCofOffMgmtGrpa = null;
/*  567 */   EntityGroup grpAnnAvail = null;
/*  568 */   EntityItem eiAnnAvail = null;
/*  569 */   EntityGroup grpOpsys = null;
/*  570 */   EntityItem eiOpsys = null;
/*  571 */   EntityGroup grpAnnOp = null;
/*  572 */   EntityItem eiAnnOp = null;
/*      */   
/*  574 */   ReportFormatter rfaReport = null;
/*  575 */   XMLtoGML x2g = new XMLtoGML();
/*      */   
/*  577 */   String strSplit = null;
/*  578 */   int intSplitLen = 0;
/*  579 */   int intSplitAt = 0;
/*  580 */   int i = 0;
/*  581 */   int j = 0;
/*  582 */   int k = 0;
/*  583 */   int iTemp = 0;
/*  584 */   int[] iColWidths = null;
/*  585 */   int[] iSortCols = null;
/*  586 */   String strCondition1 = null;
/*  587 */   String strCondition2 = null;
/*  588 */   String strCondition3 = null;
/*  589 */   String strCondition4 = null;
/*  590 */   String strCondition5 = null;
/*  591 */   String strCondition6 = null;
/*  592 */   String strCondition7 = null;
/*      */   boolean bConditionOK = false;
/*  594 */   EntityItem eiNextItem = null;
/*  595 */   EntityItem eiNextItem1 = null;
/*  596 */   EntityItem eiNextItem2 = null;
/*  597 */   EntityItem eiNextItem3 = null;
/*  598 */   EntityGroup grpNextGroup = null;
/*  599 */   String[] strParamList1 = null;
/*  600 */   String[] strParamList2 = null;
/*  601 */   String[] strFilterAttr = null;
/*  602 */   String[] strFilterValue = null;
/*  603 */   String[] strHeader = null;
/*  604 */   String m_strSpaces = "                                                                                          ";
/*  605 */   Vector vReturnEntities1 = new Vector();
/*  606 */   Vector vReturnEntities2 = new Vector();
/*  607 */   Vector vReturnEntities3 = new Vector();
/*  608 */   Vector vReturnEntities4 = new Vector();
/*  609 */   Vector vPrintDetails = new Vector();
/*  610 */   Vector vPrintDetails1 = new Vector();
/*  611 */   Vector vPrintDetails2 = new Vector();
/*  612 */   Vector vPrintDetails3 = new Vector();
/*  613 */   Hashtable hNoDupeLines = new Hashtable<>();
/*      */   
/*  615 */   GeneralAreaList m_geList = null;
/*      */   
/*  617 */   EntityList eListFUP = null;
/*  618 */   EntityGroup grpOrderOF2 = null;
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*      */     try {
/*  624 */       start_ABRBuild();
/*  625 */       setReturnCode(0);
/*      */       
/*  627 */       logMessage("VE Dump********************");
/*  628 */       logMessage(this.m_elist.dump(false));
/*  629 */       logMessage("End VE Dump********************");
/*      */       
/*  631 */       this.m_geList = new GeneralAreaList(getDatabase(), getProfile());
/*  632 */       this.m_geList.buildTree();
/*      */ 
/*      */ 
/*      */       
/*  636 */       logMessage("Starting General area dump*********************************");
/*  637 */       logMessage(this.m_geList.dump(false));
/*  638 */       logMessage("Ending dump***********************************");
/*      */ 
/*      */       
/*  641 */       this.grpAnnouncement = this.m_elist.getParentEntityGroup();
/*  642 */       logMessage("************Root Entity Type and id " + getEntityType() + ":" + getEntityID());
/*  643 */       this.vReturnEntities1 = null;
/*  644 */       this.vReturnEntities2 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  649 */       if (this.grpAnnouncement == null) {
/*  650 */         logMessage("****************Announcement Not found ");
/*  651 */         setReturnCode(-1);
/*      */       } else {
/*      */         
/*  654 */         logMessage(this.grpAnnouncement.getEntityItemCount() + " Announcements found!");
/*  655 */         this.eiAnnounce = this.grpAnnouncement.getEntityItem(0);
/*      */         
/*  657 */         if (this.eiAnnounce == null) {
/*  658 */           logMessage("***********************Root Announcement entityItem cannot be retrieved from abr Item:" + getEntityType() + ":" + getEntityID());
/*  659 */           setReturnCode(-1);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  664 */         this.grpAnnDeliv = this.m_elist.getEntityGroup("ANNDELIVERABLE");
/*  665 */         this.eiAnnDeliv = null;
/*  666 */         if (this.grpAnnDeliv != null) {
/*  667 */           this.eiAnnDeliv = this.grpAnnDeliv.getEntityItem(0);
/*      */         } else {
/*  669 */           logMessage("**************ANNDELIVERABLE not found in list**");
/*      */         } 
/*  671 */         this.grpParamCode = this.m_elist.getEntityGroup("PARAMETERCODE");
/*  672 */         this.eiParamCode = null;
/*  673 */         if (this.grpParamCode != null) {
/*  674 */           this.eiParamCode = this.grpParamCode.getEntityItem(0);
/*      */         } else {
/*  676 */           logMessage("**************PARAMETERCODE not found in list**");
/*      */         } 
/*      */         
/*  679 */         this.grpDependCode = this.m_elist.getEntityGroup("DEPENDENCYCODE");
/*  680 */         this.eiDependCode = null;
/*  681 */         if (this.grpDependCode != null) {
/*  682 */           this.eiDependCode = this.grpDependCode.getEntityItem(0);
/*      */         } else {
/*  684 */           logMessage("**************DEPENDENCYCODE not found in list**");
/*      */         } 
/*      */         
/*  687 */         this.grpAnnProj = this.m_elist.getEntityGroup("ANNPROJ");
/*  688 */         this.eiAnnProj = null;
/*  689 */         if (this.grpAnnProj != null) {
/*  690 */           this.eiAnnProj = this.grpAnnProj.getEntityItem(0);
/*      */         } else {
/*  692 */           logMessage("**************ANNPROJ not found in list**");
/*      */         } 
/*      */         
/*  695 */         this.grpErrataCause = this.m_elist.getEntityGroup("ERRATACAUSE");
/*  696 */         this.eiErrataCause = null;
/*  697 */         if (this.grpErrataCause != null) {
/*  698 */           this.eiErrataCause = this.grpErrataCause.getEntityItem(0);
/*      */         } else {
/*  700 */           logMessage("**************ERRATACAUSE not found in list**");
/*      */         } 
/*      */         
/*  703 */         this.grpOrganUnit = this.m_elist.getEntityGroup("ORGANUNIT");
/*  704 */         this.eiOrganUnit = null;
/*  705 */         if (this.grpOrganUnit != null) {
/*  706 */           this.eiOrganUnit = this.grpOrganUnit.getEntityItem(0);
/*      */         } else {
/*  708 */           logMessage("**************ORGANUNIT not found in list**");
/*      */         } 
/*      */         
/*  711 */         this.grpOP = this.m_elist.getEntityGroup("OP");
/*  712 */         this.eiOP = null;
/*  713 */         if (this.grpOP != null) {
/*  714 */           this.eiOP = this.grpOP.getEntityItem(0);
/*      */         } else {
/*  716 */           logMessage("**************OP not found in list**");
/*      */         } 
/*      */         
/*  719 */         this.grpChannel = this.m_elist.getEntityGroup("CHANNEL");
/*  720 */         this.eiChannel = null;
/*  721 */         if (this.grpChannel != null) {
/*  722 */           this.eiChannel = this.grpChannel.getEntityItem(0);
/*      */         } else {
/*  724 */           logMessage("**************CHANNEL not found in list**");
/*      */         } 
/*      */         
/*  727 */         this.grpPDSQuestions = this.m_elist.getEntityGroup("PDSQUESTIONS");
/*  728 */         this.eiPDSQuestions = null;
/*  729 */         if (this.grpPDSQuestions != null) {
/*  730 */           this.eiPDSQuestions = this.grpPDSQuestions.getEntityItem(0);
/*      */         } else {
/*  732 */           logMessage("**************PDSQUESTIONS not found in list**");
/*      */         } 
/*      */         
/*  735 */         this.grpPriceInfo = this.m_elist.getEntityGroup("PRICEFININFO");
/*  736 */         this.eiPriceInfo = null;
/*  737 */         if (this.grpPriceInfo != null) {
/*  738 */           this.eiPriceInfo = this.grpPriceInfo.getEntityItem(0);
/*      */         } else {
/*  740 */           logMessage("**************PRICEFININFO not found in list**");
/*      */         } 
/*      */         
/*  743 */         this.grpCommOffInfo = this.m_elist.getEntityGroup("COMMERCIALOFINFO");
/*  744 */         this.eiCommOFfInfo = null;
/*  745 */         if (this.grpCommOffInfo != null) {
/*  746 */           this.eiCommOFfInfo = this.grpCommOffInfo.getEntityItem(0);
/*      */         } else {
/*  748 */           logMessage("**************COMMERCIALOFINFO not found in list**");
/*      */         } 
/*      */         
/*  751 */         this.grpDerive = this.m_elist.getEntityGroup("DERIVE");
/*  752 */         this.eiDerive = null;
/*  753 */         if (this.grpDerive != null) {
/*  754 */           this.eiDerive = this.grpDerive.getEntityItem(0);
/*      */         } else {
/*  756 */           logMessage("**************DERIVE not found in list**");
/*      */         } 
/*      */         
/*  759 */         this.grpAnnReview = this.m_elist.getEntityGroup("ANNREVIEW");
/*  760 */         this.eiAnnReview = null;
/*  761 */         if (this.grpAnnReview != null) {
/*  762 */           this.eiAnnReview = this.grpAnnReview.getEntityItem(0);
/*      */         } else {
/*  764 */           logMessage("**************ANNREVIEW not found in list**");
/*      */         } 
/*      */         
/*  767 */         this.grpConfigurator = this.m_elist.getEntityGroup("CONFIGURATOR");
/*  768 */         this.eiConfigurator = null;
/*  769 */         if (this.grpConfigurator != null) {
/*  770 */           this.eiConfigurator = this.grpConfigurator.getEntityItem(0);
/*      */         } else {
/*  772 */           logMessage("**************CONFIGURATOR not found in list**");
/*      */         } 
/*      */         
/*  775 */         this.grpAnnToConfig = this.m_elist.getEntityGroup("ANNTOCONFIG");
/*  776 */         this.eiAnnToConfig = null;
/*  777 */         if (this.grpAnnToConfig != null) {
/*  778 */           this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(0);
/*      */         } else {
/*  780 */           logMessage("**************ANNTOCONFIG not found in list**");
/*      */         } 
/*      */         
/*  783 */         this.grpAnnToOrgUnit = this.m_elist.getEntityGroup("ANNORGANUNIT");
/*  784 */         this.eiAnnToOrgUnit = null;
/*  785 */         if (this.grpAnnToConfig != null) {
/*  786 */           this.eiAnnToOrgUnit = this.grpAnnToOrgUnit.getEntityItem(0);
/*      */         } else {
/*  788 */           logMessage("**************ANNORGANUNIT not found in list**");
/*      */         } 
/*      */         
/*  791 */         this.grpAnnToOP = this.m_elist.getEntityGroup("ANNOP");
/*  792 */         this.eiAnnToOP = null;
/*  793 */         if (this.grpAnnToOP != null) {
/*  794 */           this.eiAnnToOP = this.grpAnnToOP.getEntityItem(0);
/*      */         } else {
/*  796 */           logMessage("**************ANNOP not found in list**");
/*      */         } 
/*      */         
/*  799 */         this.grpCofAvail = this.m_elist.getEntityGroup("COMMERCIALOFAVAIL");
/*  800 */         this.eiCofAvail = null;
/*  801 */         if (this.grpCofAvail != null) {
/*  802 */           this.eiCofAvail = this.grpCofAvail.getEntityItem(0);
/*      */         } else {
/*  804 */           logMessage("**************COMMERCIALOFAVAIL not found in list**");
/*      */         } 
/*      */         
/*  807 */         this.grpPdsQuestions = this.m_elist.getEntityGroup("PDSQUESTIONS");
/*  808 */         this.eiPdsQuestions = null;
/*  809 */         if (this.grpPdsQuestions != null) {
/*  810 */           this.eiPdsQuestions = this.grpPdsQuestions.getEntityItem(0);
/*      */         } else {
/*  812 */           logMessage("**************PDSQUESTIONS not found in list**");
/*      */         } 
/*      */         
/*  815 */         this.grpCommOfIvo = this.m_elist.getEntityGroup("COMMERCIALOFIVO");
/*  816 */         this.eiCommOFIvo = null;
/*  817 */         if (this.grpCommOfIvo != null) {
/*  818 */           this.eiCommOFIvo = this.grpCommOfIvo.getEntityItem(0);
/*      */         } else {
/*  820 */           logMessage("**************COMMERCIALOFIVO not found in list**");
/*      */         } 
/*      */         
/*  823 */         this.grpCommOF = this.m_elist.getEntityGroup("COMMERCIALOF");
/*  824 */         this.eiCommOF = null;
/*  825 */         if (this.grpCommOF != null) {
/*  826 */           this.eiCommOF = this.grpCommOF.getEntityItem(0);
/*      */         } else {
/*  828 */           logMessage("**************COMMERCIALOF not found in list**");
/*      */         } 
/*      */         
/*  831 */         this.grpRelatedANN = this.m_elist.getEntityGroup("RELATEDANN");
/*  832 */         this.eiRelatedANN = null;
/*  833 */         if (this.grpRelatedANN != null) {
/*  834 */           this.eiRelatedANN = this.grpRelatedANN.getEntityItem(0);
/*      */         } else {
/*  836 */           logMessage("**************RELATEDANN not found in list**");
/*      */         } 
/*      */         
/*  839 */         this.grpGeneralArea = this.m_elist.getEntityGroup("GENERALAREA");
/*  840 */         this.eiGeneralArea = null;
/*  841 */         if (this.grpGeneralArea != null) {
/*  842 */           this.eiGeneralArea = this.grpGeneralArea.getEntityItem(0);
/*      */         } else {
/*  844 */           logMessage("**************GENERALAREA not found in list**");
/*      */         } 
/*      */         
/*  847 */         this.grpAvail = this.m_elist.getEntityGroup("AVAIL");
/*  848 */         this.eiAvail = null;
/*  849 */         if (this.grpAvail != null) {
/*  850 */           this.eiAvail = this.grpAvail.getEntityItem(0);
/*      */         } else {
/*  852 */           logMessage("**************AVAIL not found in list**");
/*      */         } 
/*      */         
/*  855 */         this.grpOrderOF = this.m_elist.getEntityGroup("ORDEROF");
/*  856 */         this.eiOrderOF = null;
/*  857 */         if (this.grpOrderOF != null) {
/*  858 */           this.eiOrderOF = this.grpOrderOF.getEntityItem(0);
/*      */         } else {
/*  860 */           logMessage("**************ORDEROF not found in list**");
/*      */         } 
/*      */         
/*  863 */         this.grpCrossSell = this.m_elist.getEntityGroup("CROSSSELL");
/*  864 */         this.eiCrossSell = null;
/*  865 */         if (this.grpCrossSell != null) {
/*  866 */           this.eiCrossSell = this.grpCrossSell.getEntityItem(0);
/*      */         } else {
/*  868 */           logMessage("**************CROSSSELL not found in list**");
/*      */         } 
/*      */         
/*  871 */         this.grpUpSell = this.m_elist.getEntityGroup("UPSELL");
/*  872 */         this.eiUpSell = null;
/*  873 */         if (this.grpUpSell != null) {
/*  874 */           this.eiUpSell = this.grpUpSell.getEntityItem(0);
/*      */         } else {
/*  876 */           logMessage("**************UPSELL not found in list**");
/*      */         } 
/*      */         
/*  879 */         this.grpStdAmendText = this.m_elist.getEntityGroup("STANDAMENDTEXT");
/*  880 */         this.eiStdAmendText = null;
/*  881 */         if (this.grpStdAmendText != null) {
/*  882 */           this.eiStdAmendText = this.grpStdAmendText.getEntityItem(0);
/*      */         } else {
/*  884 */           logMessage("**************STANDAMENDTEXT not found in list**");
/*      */         } 
/*      */         
/*  887 */         this.grpCOFCrypto = this.m_elist.getEntityGroup("COFCRYPTO");
/*  888 */         this.eiCOFCrypto = null;
/*  889 */         if (this.grpCOFCrypto != null) {
/*  890 */           this.eiCOFCrypto = this.grpCOFCrypto.getEntityItem(0);
/*      */         } else {
/*  892 */           logMessage("**************COFCRYPTO not found in list**");
/*      */         } 
/*      */         
/*  895 */         this.grpOOFCrypto = this.m_elist.getEntityGroup("OOFCRYPTO");
/*  896 */         this.eiOOFCrypto = null;
/*  897 */         if (this.grpOOFCrypto != null) {
/*  898 */           this.eiOOFCrypto = this.grpOOFCrypto.getEntityItem(0);
/*      */         } else {
/*  900 */           logMessage("**************OOFCRYPTO not found in list**");
/*      */         } 
/*      */         
/*  903 */         this.grpCrypto = this.m_elist.getEntityGroup("CRYPTO");
/*  904 */         this.eiCrypto = null;
/*  905 */         if (this.grpCrypto != null) {
/*  906 */           this.eiCrypto = this.grpCrypto.getEntityItem(0);
/*      */         } else {
/*  908 */           logMessage("**************CRYPTO not found in list**");
/*      */         } 
/*      */         
/*  911 */         this.grpCofOrganUnit = this.m_elist.getEntityGroup("COFORGANUNIT");
/*  912 */         this.eiCofOrganUnit = null;
/*  913 */         if (this.grpCofOrganUnit != null) {
/*  914 */           this.eiCofOrganUnit = this.grpCofOrganUnit.getEntityItem(0);
/*      */         } else {
/*  916 */           logMessage("**************COFORGANUNIT not found in list**");
/*      */         } 
/*      */         
/*  919 */         this.grpIndividual = this.m_elist.getEntityGroup("INDIVIDUAL");
/*  920 */         this.eiIndividual = null;
/*  921 */         if (this.grpIndividual != null) {
/*  922 */           this.eiIndividual = this.grpIndividual.getEntityItem(0);
/*      */         } else {
/*  924 */           logMessage("**************INDIVIDUAL not found in list**");
/*      */         } 
/*      */         
/*  927 */         this.grpPublication = this.m_elist.getEntityGroup("PUBLICATION");
/*  928 */         this.eiPublication = null;
/*  929 */         if (this.grpPublication != null) {
/*  930 */           this.eiPublication = this.grpPublication.getEntityItem(0);
/*      */         } else {
/*  932 */           logMessage("**************PUBLICATION not found in list**");
/*      */         } 
/*      */         
/*  935 */         this.grpEducation = this.m_elist.getEntityGroup("EDUCATION");
/*  936 */         this.eiEducation = null;
/*  937 */         if (this.grpEducation != null) {
/*  938 */           this.eiEducation = this.grpEducation.getEntityItem(0);
/*      */         } else {
/*  940 */           logMessage("**************EDUCATION not found in list**");
/*      */         } 
/*      */         
/*  943 */         this.grpAnnEducation = this.m_elist.getEntityGroup("ANNEDUCATION");
/*  944 */         this.eiAnnEducation = null;
/*  945 */         if (this.grpAnnEducation != null) {
/*  946 */           this.eiAnnEducation = this.grpAnnEducation.getEntityItem(0);
/*      */         } else {
/*  948 */           logMessage("**************ANNEDUCATION not found in list**");
/*      */         } 
/*      */         
/*  951 */         this.grpIvocat = this.m_elist.getEntityGroup("IVOCAT");
/*  952 */         this.eiIvocat = null;
/*  953 */         if (this.grpIvocat != null) {
/*  954 */           this.eiIvocat = this.grpIvocat.getEntityItem(0);
/*      */         } else {
/*  956 */           logMessage("**************IVOCAT not found in list**");
/*      */         } 
/*      */         
/*  959 */         this.grpBoilPlateText = this.m_elist.getEntityGroup("BOILPLATETEXT");
/*  960 */         this.eiBoilPlateText = null;
/*  961 */         if (this.grpBoilPlateText != null) {
/*  962 */           this.eiBoilPlateText = this.grpBoilPlateText.getEntityItem(0);
/*      */         } else {
/*  964 */           logMessage("**************BOILPLATETEXT not found in list**");
/*      */         } 
/*      */         
/*  967 */         this.grpCatIncl = this.m_elist.getEntityGroup("CATINCL");
/*  968 */         this.eiCatIncl = null;
/*  969 */         if (this.grpCatIncl != null) {
/*  970 */           this.eiCatIncl = this.grpCatIncl.getEntityItem(0);
/*      */         } else {
/*  972 */           logMessage("**************CATINCL not found in list**");
/*      */         } 
/*      */         
/*  975 */         this.grpAlternateOF = this.m_elist.getEntityGroup("ALTERNATEOF");
/*  976 */         this.eiAlternateOF = null;
/*  977 */         if (this.grpAlternateOF != null) {
/*  978 */           this.eiAlternateOF = this.grpAlternateOF.getEntityItem(0);
/*      */         } else {
/*  980 */           logMessage("**************ALTERNATEOF not found in list**");
/*      */         } 
/*      */         
/*  983 */         this.grpCofBPExhibit = this.m_elist.getEntityGroup("COFBPEXHIBIT");
/*  984 */         this.eiCofBPExhibit = null;
/*  985 */         if (this.grpCofBPExhibit != null) {
/*  986 */           this.eiCofBPExhibit = this.grpCofBPExhibit.getEntityItem(0);
/*      */         } else {
/*  988 */           logMessage("**************COFBPEXHIBIT not found in list**");
/*      */         } 
/*      */         
/*  991 */         this.grpBPExhibit = this.m_elist.getEntityGroup("BPEXHIBIT");
/*  992 */         this.eiBPExhibit = null;
/*  993 */         if (this.grpBPExhibit != null) {
/*  994 */           this.eiBPExhibit = this.grpBPExhibit.getEntityItem(0);
/*      */         } else {
/*  996 */           logMessage("**************BPEXHIBIT not found in list**");
/*      */         } 
/*      */         
/*  999 */         this.grpCofPubs = this.m_elist.getEntityGroup("COFPUBS");
/* 1000 */         this.eiCofPubs = null;
/* 1001 */         if (this.grpCofPubs != null) {
/* 1002 */           this.eiCofPubs = this.grpCofPubs.getEntityItem(0);
/*      */         } else {
/* 1004 */           logMessage("**************COFPUBS not found in list**");
/*      */         } 
/*      */         
/* 1007 */         this.grpEnvirinfo = this.m_elist.getEntityGroup("ENVIRINFO");
/* 1008 */         this.eiEnvirinfo = null;
/* 1009 */         if (this.grpEnvirinfo != null) {
/* 1010 */           this.eiEnvirinfo = this.grpEnvirinfo.getEntityItem(0);
/*      */         } else {
/* 1012 */           logMessage("**************ENVIRINFO not found in list**");
/*      */         } 
/*      */         
/* 1015 */         this.grpAltEnvirinfo = this.m_elist.getEntityGroup("ALTDEPENENVIRINFO");
/* 1016 */         this.eiAltEnvirinfo = null;
/* 1017 */         if (this.grpAltEnvirinfo != null) {
/* 1018 */           this.eiAltEnvirinfo = this.grpAltEnvirinfo.getEntityItem(0);
/*      */         } else {
/* 1020 */           logMessage("**************ALTDEPENENVIRINFO not found in list**");
/*      */         } 
/*      */         
/* 1023 */         this.grpPackaging = this.m_elist.getEntityGroup("PACKAGING");
/* 1024 */         this.eiPackaging = null;
/* 1025 */         if (this.grpPackaging != null) {
/* 1026 */           this.eiPackaging = this.grpPackaging.getEntityItem(0);
/*      */         } else {
/* 1028 */           logMessage("**************PACKAGING not found in list**");
/*      */         } 
/*      */         
/* 1031 */         this.grpAnnSalesmanchg = this.m_elist.getEntityGroup("ANNSALESMANCHG");
/* 1032 */         this.eiAnnSalesmanchg = null;
/* 1033 */         if (this.grpAnnSalesmanchg != null) {
/* 1034 */           this.eiAnnSalesmanchg = this.grpAnnSalesmanchg.getEntityItem(0);
/*      */         } else {
/* 1036 */           logMessage("**************ANNSALESMANCHG not found in list**");
/*      */         } 
/* 1038 */         this.grpSalesmanchg = this.m_elist.getEntityGroup("SALESMANCHG");
/* 1039 */         this.eiSalesmanchg = null;
/* 1040 */         if (this.grpSalesmanchg != null) {
/* 1041 */           this.eiSalesmanchg = this.grpSalesmanchg.getEntityItem(0);
/*      */         } else {
/* 1043 */           logMessage("**************SALESMANCHG not found in list**");
/*      */         } 
/*      */         
/* 1046 */         this.grpOrderOFAvail = this.m_elist.getEntityGroup("OOFAVAIL");
/* 1047 */         this.eiOrderOFAvail = null;
/* 1048 */         if (this.grpOrderOFAvail != null) {
/* 1049 */           this.eiOrderOFAvail = this.grpOrderOFAvail.getEntityItem(0);
/*      */         } else {
/* 1051 */           logMessage("**************OOFAVAIL not found in list**");
/*      */         } 
/*      */         
/* 1054 */         this.grpAnnToAnnDeliv = this.m_elist.getEntityGroup("ANNTOANNDELIVER");
/* 1055 */         this.eiAnnToAnnDeliv = null;
/* 1056 */         if (this.grpAnnToAnnDeliv != null) {
/* 1057 */           this.eiAnnToAnnDeliv = this.grpAnnToAnnDeliv.getEntityItem(0);
/*      */         } else {
/* 1059 */           logMessage("**************ANNTOANNDELIVER not found in list**");
/*      */         } 
/*      */         
/* 1062 */         this.grpAnnDelReqTrans = this.m_elist.getEntityGroup("ANNDELREQTRANS");
/* 1063 */         this.eiAnnDelReqTrans = null;
/* 1064 */         if (this.grpAnnDelReqTrans != null) {
/* 1065 */           this.eiAnnDelReqTrans = this.grpAnnDelReqTrans.getEntityItem(0);
/*      */         } else {
/* 1067 */           logMessage("**************ANNDELREQTRANS not found in list**");
/*      */         } 
/*      */         
/* 1070 */         this.grpAnnToDescArea = this.m_elist.getEntityGroup("ANNTODESCAREA");
/* 1071 */         this.eiAnnToDescArea = null;
/* 1072 */         if (this.grpAnnToDescArea != null) {
/* 1073 */           this.eiAnnToDescArea = this.grpAnnToDescArea.getEntityItem(0);
/*      */         } else {
/* 1075 */           logMessage("**************ANNTODESCAREA not found in list**");
/*      */         } 
/*      */         
/* 1078 */         this.grpCofPrice = this.m_elist.getEntityGroup("COFPRICE");
/* 1079 */         this.eiCofPrice = null;
/* 1080 */         if (this.grpCofPrice != null) {
/* 1081 */           this.eiCofPrice = this.grpCofPrice.getEntityItem(0);
/*      */         } else {
/* 1083 */           logMessage("**************COFPRICE not found in list**");
/*      */         } 
/*      */         
/* 1086 */         this.grpAnnPara = this.m_elist.getEntityGroup("ANNPARA");
/* 1087 */         this.eiAnnPara = null;
/* 1088 */         if (this.grpAnnPara != null) {
/* 1089 */           this.eiAnnPara = this.grpAnnPara.getEntityItem(0);
/*      */         } else {
/* 1091 */           logMessage("**************ANNPARA not found in list**");
/*      */         } 
/*      */         
/* 1094 */         this.grpDependCode = this.m_elist.getEntityGroup("ANNDEPA");
/* 1095 */         this.eiDependCode = null;
/* 1096 */         if (this.grpDependCode != null) {
/* 1097 */           this.eiDependCode = this.grpDependCode.getEntityItem(0);
/*      */         } else {
/* 1099 */           logMessage("**************ANNDEPA not found in list**");
/*      */         } 
/*      */         
/* 1102 */         this.grpCofChannel = this.m_elist.getEntityGroup("COFCHANNEL");
/* 1103 */         this.eiCofChannel = null;
/* 1104 */         if (this.grpCofChannel != null) {
/* 1105 */           this.eiCofChannel = this.grpCofChannel.getEntityItem(0);
/*      */         } else {
/* 1107 */           logMessage("**************COFCHANNEL not found in list**");
/*      */         } 
/*      */         
/* 1110 */         this.grpAnnCofa = this.m_elist.getEntityGroup("ANNCOFA");
/* 1111 */         this.eiAnnCofa = null;
/* 1112 */         if (this.grpAnnCofa != null) {
/* 1113 */           this.eiAnnCofa = this.grpAnnCofa.getEntityItem(0);
/*      */         } else {
/* 1115 */           logMessage("**************ANNCOFA not found in list**");
/*      */         } 
/*      */         
/* 1118 */         this.grpAnnCofOffMgmtGrpa = this.m_elist.getEntityGroup("ANNCOFOOFMGMTGRPA");
/* 1119 */         this.eiAnnCofOffMgmtGrpa = null;
/* 1120 */         if (this.grpAnnCofOffMgmtGrpa != null) {
/* 1121 */           this.eiAnnCofOffMgmtGrpa = this.grpAnnCofOffMgmtGrpa.getEntityItem(0);
/*      */         } else {
/* 1123 */           logMessage("**************ANNCOFOOFMGMTGRPA not found in list**");
/*      */         } 
/*      */         
/* 1126 */         this.grpAnnAvail = this.m_elist.getEntityGroup("ANNAVAILA");
/* 1127 */         this.eiAnnAvail = null;
/* 1128 */         if (this.grpAnnAvail != null) {
/* 1129 */           this.eiAnnAvail = this.grpAnnAvail.getEntityItem(0);
/*      */         } else {
/* 1131 */           logMessage("**************ANNAVAILA not found in list**");
/*      */         } 
/*      */         
/* 1134 */         this.grpAnnOp = this.m_elist.getEntityGroup("ANNOP");
/* 1135 */         this.eiAnnOp = null;
/* 1136 */         if (this.grpAnnOp != null) {
/* 1137 */           this.eiAnnOp = this.grpAnnOp.getEntityItem(0);
/*      */         } else {
/* 1139 */           logMessage("**************ANNOP not found in list**");
/*      */         } 
/*      */         
/* 1142 */         this.rfaReport = new ReportFormatter();
/* 1143 */         this.rfaReport.setABRItem(getABRItem());
/*      */ 
/*      */         
/* 1146 */         DateFormat dateFormat = DateFormat.getDateInstance();
/* 1147 */         dateFormat.setCalendar(Calendar.getInstance());
/* 1148 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 1149 */         simpleDateFormat.setCalendar(Calendar.getInstance());
/* 1150 */         String str = simpleDateFormat.format(new Date());
/*      */ 
/*      */         
/* 1153 */         discard_nonPublishEntities();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1158 */         println(".*$REQUESTTYPE_BEGIN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1177 */         this.strCondition1 = getAttributeFlagEnabledValue(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "DISTRTYPE", "");
/* 1178 */         if (!this.strCondition1.equals("720")) {
/* 1179 */           this.strCondition1 = getAttributeValue(this.eiAnnounce, "SENDTOMAINMENU", "NoValuefoundSENDTOMAINMENU");
/*      */           
/* 1181 */           if (this.strCondition1.equals("Yes")) {
/*      */             
/* 1183 */             this.strCondition1 = getAttributeFlagEnabledValue(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "ANCYCLESTATUS", "");
/* 1184 */             this.strCondition1 = this.strCondition1.substring((this.strCondition1.indexOf("|") == -1) ? 0 : (this.strCondition1.indexOf("|") + 1));
/* 1185 */             this.strCondition2 = getAttributeValue(this.eiAnnounce, "ANNDATE", "");
/* 1186 */             this.i = Integer.valueOf(this.strCondition1).intValue();
/* 1187 */             this.bConditionOK = true;
/* 1188 */             switch (this.i) {
/*      */               case 112:
/*      */               case 114:
/*      */               case 115:
/* 1192 */                 this.strCondition3 = "preEdit";
/*      */                 break;
/*      */               case 116:
/*      */               case 117:
/* 1196 */                 if (this.strCondition2.compareTo(str) > 0) {
/* 1197 */                   this.strCondition3 = "final"; break;
/*      */                 } 
/* 1199 */                 this.strCondition3 = "correction";
/*      */                 break;
/*      */               
/*      */               default:
/* 1203 */                 println("ANCYCLESTATUS returned unexpected flag value");
/*      */                 break;
/*      */             } 
/*      */           } else {
/* 1207 */             this.strCondition3 = "return";
/*      */           } 
/*      */         } else {
/* 1210 */           this.strCondition3 = "finalEdit";
/*      */         } 
/* 1212 */         println(this.strCondition3);
/* 1213 */         println(".*$REQUESTTYPE_END");
/* 1214 */         println(".*$USERIDS_BEGIN");
/*      */         
/* 1216 */         println("'" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERTOKEN", "") : "'") + "'");
/* 1217 */         println(".*$USERIDS_END");
/* 1218 */         println(".*$VARIABLES_BEGIN");
/* 1219 */         processShortAnswers();
/* 1220 */         println(".*$ANSWERS_BEGIN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1227 */         processLongTo100();
/* 1228 */         processLongTo200();
/* 1229 */         processLongTo300();
/* 1230 */         processLongTo400();
/* 1231 */         processLongTo500();
/* 1232 */         processLongTo600();
/* 1233 */         processLongTo700();
/* 1234 */         processLongTo800();
/* 1235 */         processLongTo900();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1241 */         println(".*$ANSWERS_END");
/*      */       }
/*      */     
/*      */     }
/* 1245 */     catch (Exception exception) {
/*      */       
/* 1247 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 1248 */       println("" + exception);
/* 1249 */       logError(exception, "");
/* 1250 */       StringWriter stringWriter = new StringWriter();
/*      */       
/* 1252 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/* 1254 */       String str3 = stringWriter.toString();
/*      */       
/* 1256 */       println(str3);
/*      */ 
/*      */       
/* 1259 */       if (getABRReturnCode() != -2) {
/* 1260 */         setReturnCode(-3);
/*      */       }
/*      */     } finally {
/*      */       
/* 1264 */       if (getReturnCode() == 0) {
/* 1265 */         setReturnCode(0);
/*      */       }
/*      */       
/* 1268 */       String str1 = this.eiAnnounce.getNavAttrDescription();
/*      */       
/* 1270 */       if (str1.length() > 34) {
/* 1271 */         str1 = str1.substring(0, 34);
/*      */       }
/*      */       
/* 1274 */       String str2 = "Extract for " + str1 + " | " + ((getReturnCode() == 0) ? "Passed" : "Failed") + " | Complete";
/* 1275 */       setDGTitle(str2);
/*      */ 
/*      */       
/* 1278 */       setDGRptName("RFAHWABR01");
/* 1279 */       setDGRptClass("RFAHWABR01");
/*      */       
/* 1281 */       printDGSubmitString();
/*      */ 
/*      */       
/* 1284 */       setDGString(getABRReturnCode());
/*      */       
/* 1286 */       if (!isReadOnly()) {
/* 1287 */         clearSoftLock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector searchEntityGroup(EntityGroup paramEntityGroup, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean) {
/* 1307 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */     
/* 1310 */     EntityItem entityItem = null;
/* 1311 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 1312 */       entityItem = paramEntityGroup.getEntityItem(b);
/* 1313 */       if (paramArrayOfString1 != null) {
/* 1314 */         if (foundInEntity(entityItem, paramArrayOfString1, paramArrayOfString2, paramBoolean)) {
/* 1315 */           vector.add(entityItem);
/*      */         }
/*      */       } else {
/* 1318 */         vector.add(entityItem);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1323 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector searchEntityVector(Vector<EntityItem> paramVector, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean) {
/* 1328 */     Vector<EntityItem> vector = new Vector();
/*      */     
/* 1330 */     EntityItem entityItem = null;
/* 1331 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1332 */       entityItem = paramVector.elementAt(b);
/* 1333 */       if (paramArrayOfString1 != null) {
/* 1334 */         if (foundInEntity(entityItem, paramArrayOfString1, paramArrayOfString2, paramBoolean)) {
/* 1335 */           vector.add(entityItem);
/*      */         }
/*      */       } else {
/* 1338 */         vector.add(entityItem);
/*      */       } 
/*      */     } 
/*      */     
/* 1342 */     return vector;
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
/*      */   private Vector searchEntityVectorLink(Vector<EntityItem> paramVector, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean1, boolean paramBoolean2, String paramString) {
/* 1358 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */     
/* 1361 */     EntityItem entityItem1 = null;
/* 1362 */     EntityItem entityItem2 = null;
/* 1363 */     int i = 0;
/* 1364 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1365 */       entityItem1 = paramVector.elementAt(b);
/* 1366 */       if (paramBoolean2) {
/* 1367 */         i = entityItem1.getDownLinkCount();
/*      */       } else {
/* 1369 */         i = entityItem1.getUpLinkCount();
/*      */       } 
/* 1371 */       for (byte b1 = 0; b1 < i; b1++) {
/* 1372 */         if (paramBoolean2) {
/* 1373 */           entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/*      */         } else {
/* 1375 */           entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/*      */         } 
/* 1377 */         if (entityItem2 != null && 
/* 1378 */           entityItem2.getEntityType().equals(paramString)) {
/* 1379 */           if (paramArrayOfString1 != null) {
/* 1380 */             if (foundInEntity(entityItem2, paramArrayOfString1, paramArrayOfString2, paramBoolean1)) {
/* 1381 */               vector.add(entityItem2);
/*      */             }
/*      */           } else {
/* 1384 */             vector.add(entityItem2);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1390 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector searchInGeo(Vector<EntityItem> paramVector, String paramString) {
/* 1395 */     EntityItem entityItem = null;
/* 1396 */     boolean bool = false;
/*      */     
/* 1398 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1399 */       entityItem = paramVector.elementAt(b);
/* 1400 */       bool = false;
/* 1401 */       if (paramString.equals("US")) {
/* 1402 */         bool = this.m_geList.isRfaGeoUS(entityItem);
/* 1403 */       } else if (paramString.equals("AP")) {
/* 1404 */         bool = this.m_geList.isRfaGeoAP(entityItem);
/* 1405 */       } else if (paramString.equals("CAN")) {
/* 1406 */         bool = this.m_geList.isRfaGeoCAN(entityItem);
/* 1407 */       } else if (paramString.equals("EMEA")) {
/* 1408 */         bool = this.m_geList.isRfaGeoEMEA(entityItem);
/* 1409 */       } else if (paramString.equals("LA")) {
/* 1410 */         bool = this.m_geList.isRfaGeoLA(entityItem);
/*      */       } 
/* 1412 */       if (!bool) {
/* 1413 */         paramVector.remove(b);
/*      */       }
/*      */     } 
/* 1416 */     return paramVector;
/*      */   }
/*      */   
/*      */   private String getGeoTags(EntityItem paramEntityItem) {
/* 1420 */     String str = "";
/* 1421 */     if (this.m_geList.isRfaGeoUS(paramEntityItem)) {
/* 1422 */       str = "US";
/*      */     }
/* 1424 */     if (this.m_geList.isRfaGeoAP(paramEntityItem)) {
/* 1425 */       if (str.length() > 0) {
/* 1426 */         str = str + ", AP";
/*      */       } else {
/* 1428 */         str = "AP";
/*      */       } 
/*      */     }
/* 1431 */     if (this.m_geList.isRfaGeoCAN(paramEntityItem)) {
/* 1432 */       if (str.length() > 0) {
/* 1433 */         str = str + ", CAN";
/*      */       } else {
/* 1435 */         str = "CAN";
/*      */       } 
/*      */     }
/* 1438 */     if (this.m_geList.isRfaGeoEMEA(paramEntityItem)) {
/* 1439 */       if (str.length() > 0) {
/* 1440 */         str = str + ", EMEA";
/*      */       } else {
/* 1442 */         str = "EMEA";
/*      */       } 
/*      */     }
/* 1445 */     if (this.m_geList.isRfaGeoLA(paramEntityItem)) {
/* 1446 */       if (str.length() > 0) {
/* 1447 */         str = str + ", LA";
/*      */       } else {
/* 1449 */         str = "LA";
/*      */       } 
/*      */     }
/* 1452 */     return str;
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
/*      */   private Vector searchEntityGroupLink(EntityGroup paramEntityGroup, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean1, boolean paramBoolean2, String paramString) {
/* 1473 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */     
/* 1476 */     EntityItem entityItem1 = null;
/* 1477 */     EntityItem entityItem2 = null;
/* 1478 */     int i = 0;
/* 1479 */     if (paramEntityGroup == null) {
/* 1480 */       return vector;
/*      */     }
/* 1482 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 1483 */       entityItem1 = paramEntityGroup.getEntityItem(b);
/*      */       
/* 1485 */       if (paramBoolean2) {
/* 1486 */         i = entityItem1.getDownLinkCount();
/*      */       } else {
/* 1488 */         i = entityItem1.getUpLinkCount();
/*      */       } 
/* 1490 */       for (byte b1 = 0; b1 < i; b1++) {
/* 1491 */         if (paramBoolean2) {
/* 1492 */           entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/*      */         } else {
/*      */           
/* 1495 */           entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/*      */         } 
/* 1497 */         if (entityItem2 != null && 
/* 1498 */           entityItem2.getEntityType().equals(paramString)) {
/* 1499 */           if (paramArrayOfString1 != null) {
/* 1500 */             if (foundInEntity(entityItem2, paramArrayOfString1, paramArrayOfString2, paramBoolean1)) {
/* 1501 */               vector.add(entityItem2);
/*      */             }
/*      */           } else {
/* 1504 */             vector.add(entityItem2);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1510 */     return vector;
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
/*      */   private Vector searchEntityItemLink(EntityItem paramEntityItem, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean1, boolean paramBoolean2, String paramString) {
/* 1527 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */     
/* 1530 */     EntityItem entityItem = null;
/* 1531 */     int i = 0;
/* 1532 */     if (paramEntityItem == null) {
/* 1533 */       return vector;
/*      */     }
/* 1535 */     if (paramBoolean2) {
/* 1536 */       i = paramEntityItem.getDownLinkCount();
/*      */     } else {
/* 1538 */       i = paramEntityItem.getUpLinkCount();
/*      */     } 
/* 1540 */     for (byte b = 0; b < i; b++) {
/* 1541 */       if (paramBoolean2) {
/* 1542 */         entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/*      */       } else {
/* 1544 */         entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/*      */       } 
/* 1546 */       if (entityItem != null && 
/* 1547 */         entityItem.getEntityType().equals(paramString)) {
/* 1548 */         if (paramArrayOfString1 != null) {
/* 1549 */           if (foundInEntity(entityItem, paramArrayOfString1, paramArrayOfString2, paramBoolean1)) {
/* 1550 */             vector.add(entityItem);
/*      */           }
/*      */         } else {
/* 1553 */           vector.add(entityItem);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1559 */     return vector;
/*      */   }
/*      */ 
/*      */   
/*      */   private void printShortValueListInItem(EntityItem paramEntityItem, String[] paramArrayOfString, String paramString) {
/* 1564 */     int i = paramArrayOfString.length;
/* 1565 */     String str = null;
/* 1566 */     for (byte b = 0; b < i; b++) {
/* 1567 */       if (paramArrayOfString[b].trim().length() > 0) {
/*      */         
/* 1569 */         str = getAttributeShortFlagDesc(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramArrayOfString[b], paramString);
/*      */         
/* 1571 */         if (i > 1) {
/* 1572 */           this.vPrintDetails.add(str);
/* 1573 */         } else if (!str.equals(paramString)) {
/* 1574 */           this.vPrintDetails.add(str);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printValueListInItem(EntityItem paramEntityItem, String[] paramArrayOfString, String paramString, boolean paramBoolean) {
/* 1591 */     int i = paramArrayOfString.length;
/* 1592 */     String str = null;
/* 1593 */     for (byte b = 0; b < i; b++) {
/* 1594 */       if (paramArrayOfString[b].trim().length() > 0) {
/* 1595 */         str = getAttributeValue(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramArrayOfString[b], paramString);
/* 1596 */         if (str != null && 
/* 1597 */           paramBoolean && str.trim().length() > 0) {
/* 1598 */           str = transformXML(str);
/*      */         }
/*      */ 
/*      */         
/* 1602 */         if (i > 1) {
/* 1603 */           this.vPrintDetails.add(str);
/* 1604 */         } else if (!str.equals(paramString)) {
/* 1605 */           this.vPrintDetails.add(str);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printValueListInGroup(EntityGroup paramEntityGroup, String[] paramArrayOfString, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 1624 */     EntityItem entityItem = null;
/*      */     
/* 1626 */     if (paramEntityGroup != null) {
/* 1627 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 1628 */         entityItem = paramEntityGroup.getEntityItem(b);
/* 1629 */         if (paramString1 != null) {
/* 1630 */           if (foundInEntity(entityItem, new String[] { paramString1 }, new String[] { paramString2 }, true)) {
/* 1631 */             printValueListInItem(entityItem, paramArrayOfString, paramString3, paramBoolean);
/*      */           }
/*      */         } else {
/* 1634 */           printValueListInItem(entityItem, paramArrayOfString, paramString3, paramBoolean);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printValueListInVector(Vector<EntityItem> paramVector, String[] paramArrayOfString, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 1651 */     EntityItem entityItem = null;
/*      */     
/* 1653 */     if (paramVector != null) {
/* 1654 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 1655 */         entityItem = paramVector.elementAt(b);
/* 1656 */         if (paramBoolean1) {
/* 1657 */           printValueListInItem(entityItem, paramArrayOfString, paramString, paramBoolean2);
/*      */         } else {
/* 1659 */           printShortValueListInItem(entityItem, paramArrayOfString, paramString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean foundInEntity(EntityItem paramEntityItem, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean) {
/* 1768 */     boolean bool1 = false;
/* 1769 */     boolean bool2 = false;
/* 1770 */     String str = null;
/* 1771 */     if (paramEntityItem == null) {
/* 1772 */       logMessage("Passing null entity item to search");
/* 1773 */       return false;
/*      */     } 
/* 1775 */     EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/* 1776 */     EANMetaAttribute eANMetaAttribute = null;
/*      */     
/* 1778 */     for (byte b = 0; b < paramArrayOfString1.length; b++) {
/* 1779 */       bool2 = false;
/* 1780 */       eANMetaAttribute = entityGroup.getMetaAttribute(paramArrayOfString1[b]);
/* 1781 */       switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */         case 'F':
/*      */         case 'S':
/*      */         case 'U':
/* 1785 */           bool2 = true; break;
/*      */       } 
/* 1787 */       logMessage("Checking " + paramEntityItem.getEntityType() + ":" + paramEntityItem.getEntityID() + ":" + paramArrayOfString1[b] + " for value " + paramArrayOfString2[b]);
/* 1788 */       if (paramArrayOfString2[b].toLowerCase().equals(paramArrayOfString2[b].toUpperCase()) && bool2) {
/* 1789 */         if (flagvalueEquals(paramEntityItem, paramArrayOfString1[b], paramArrayOfString2[b])) {
/* 1790 */           bool1 = true;
/*      */         } else {
/* 1792 */           bool1 = false;
/*      */         } 
/*      */       } else {
/* 1795 */         str = getAttributeValue(paramEntityItem, paramArrayOfString1[b], "");
/* 1796 */         if (str.indexOf(paramArrayOfString2[b]) > -1) {
/* 1797 */           bool1 = true;
/*      */         } else {
/*      */           
/* 1800 */           bool1 = false;
/*      */         } 
/*      */       } 
/*      */       
/* 1804 */       if (bool1 ? 
/* 1805 */         !paramBoolean : 
/*      */ 
/*      */ 
/*      */         
/* 1809 */         paramBoolean) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1816 */     return bool1;
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
/* 1828 */     return null;
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
/* 1839 */     return "<br /><br />";
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
/*      */   protected String getStyle() {
/* 1851 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/* 1861 */     return new String("$Revision: 1.75 $");
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
/*      */   private void printReport(boolean paramBoolean, String[] paramArrayOfString, int[] paramArrayOfint1, Vector paramVector, int[] paramArrayOfint2) {
/* 1882 */     this.rfaReport.setSortable(true);
/* 1883 */     this.rfaReport.setSortColumns(paramArrayOfint2);
/* 1884 */     printReport(paramBoolean, paramArrayOfString, paramArrayOfint1, paramVector);
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
/*      */   private void printReport(boolean paramBoolean, String[] paramArrayOfString, int[] paramArrayOfint, Vector<String> paramVector) {
/* 1905 */     if (paramArrayOfint.length > 1 || paramBoolean) {
/* 1906 */       this.rfaReport.printHeaders(paramBoolean);
/* 1907 */       this.rfaReport.setHeader(paramArrayOfString);
/* 1908 */       this.rfaReport.setColWidth(paramArrayOfint);
/* 1909 */       this.rfaReport.setDetail(paramVector);
/*      */       
/* 1911 */       if (paramVector.size() > 0) {
/* 1912 */         this.rfaReport.printReport();
/*      */       }
/* 1914 */       this.rfaReport.setOffset(0);
/*      */     } else {
/* 1916 */       if (paramArrayOfint[0] == 69) {
/* 1917 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 1918 */           prettyPrint(paramVector.elementAt(b), paramArrayOfint[0]);
/*      */         }
/* 1920 */       } else if (paramVector.size() > 0) {
/* 1921 */         this.rfaReport.printHeaders(paramBoolean);
/* 1922 */         this.rfaReport.setColWidth(paramArrayOfint);
/* 1923 */         this.rfaReport.setDetail(paramVector);
/* 1924 */         this.rfaReport.printReport();
/*      */       } 
/* 1926 */       this.rfaReport.setOffset(0);
/* 1927 */       this.rfaReport.setColumnSeparator(" ");
/*      */     } 
/* 1929 */     this.rfaReport.setSortable(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetPrintvars() {
/* 1939 */     this.vPrintDetails.removeAllElements();
/* 1940 */     this.vPrintDetails = null;
/* 1941 */     this.vPrintDetails = new Vector();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayContents(Vector<EntityItem> paramVector) {
/* 1951 */     EntityItem entityItem = null;
/* 1952 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1953 */       entityItem = paramVector.elementAt(b);
/* 1954 */       logMessage("ET:" + entityItem.getEntityType() + ":EI:" + entityItem.getEntityID());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean discard_nonPublishEntities() {
/* 1960 */     boolean bool1 = false;
/*      */     
/* 1962 */     String str1 = getAttributeValue(getEntityType(), getEntityID(), "ANNDATE", "");
/* 1963 */     String str2 = null;
/* 1964 */     String str3 = null;
/* 1965 */     String str4 = null;
/*      */ 
/*      */ 
/*      */     
/* 1969 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 1970 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*      */     
/* 1972 */     boolean bool2 = false;
/*      */ 
/*      */     
/* 1975 */     this.grpOpsys = this.m_elist.getEntityGroup("OPSYS");
/* 1976 */     this.eiOpsys = null;
/* 1977 */     if (this.grpOpsys != null) {
/* 1978 */       this.eiOpsys = this.grpOpsys.getEntityItem(0);
/*      */     } else {
/* 1980 */       logMessage("discard_nonPublishEntities:**************OPSYS not found in list**");
/* 1981 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1986 */     EANAttribute eANAttribute = this.eiAnnounce.getAttribute("OSLEVEL");
/* 1987 */     if (eANAttribute == null) {
/* 1988 */       logMessage("discard_nonPublishEntities:OSLEVEL not found in Announcment");
/* 1989 */       return false;
/*      */     } 
/* 1991 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 1992 */     MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*      */     
/* 1994 */     for (this.i = 0; this.i < arrayOfMetaFlag.length; this.i++) {
/* 1995 */       if (arrayOfMetaFlag[this.i].isSelected()) {
/* 1996 */         hashtable1.put(arrayOfMetaFlag[this.i].getFlagCode(), "Found");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2002 */     for (this.i = 0; this.i < this.grpOpsys.getEntityItemCount(); this.i++) {
/* 2003 */       this.eiOpsys = this.grpOpsys.getEntityItem(this.i);
/* 2004 */       eANAttribute = this.eiOpsys.getAttribute("OSLEVEL");
/* 2005 */       eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 2006 */       arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*      */       
/* 2008 */       str2 = getAttributeValue(this.eiOpsys.getEntityType(), this.eiOpsys.getEntityID(), "PUBAFTER", "");
/*      */ 
/*      */       
/* 2011 */       for (this.i = 0; this.i < arrayOfMetaFlag.length; this.i++) {
/* 2012 */         if (arrayOfMetaFlag[this.i].isSelected()) {
/* 2013 */           str4 = arrayOfMetaFlag[this.i].getFlagCode();
/* 2014 */           if (hashtable1.get(str4) != null) {
/* 2015 */             if (str2.compareTo(str1) < 0) {
/* 2016 */               logMessage("discard_nonPublishEntities:**************Invalid condition...Announcement date:" + str1 + " is before publish date:" + str2);
/* 2017 */               return false;
/*      */             } 
/*      */ 
/*      */             
/* 2021 */             str3 = (String)hashtable2.get(str4);
/* 2022 */             if (str3 != null) {
/* 2023 */               if (str3.compareTo(str2) > 0) {
/* 2024 */                 hashtable2.remove(str4);
/* 2025 */                 hashtable2.put(str4, str2);
/*      */               } 
/*      */             } else {
/* 2028 */               hashtable2.put(str4, str2);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2039 */     for (byte b = 0; b < this.m_elist.getEntityGroupCount(); b++) {
/* 2040 */       EntityGroup entityGroup = this.m_elist.getEntityGroup(b);
/* 2041 */       logMessage("discard_nonPublishEntities:Scanning :" + entityGroup.getEntityType());
/* 2042 */       if (entityGroup.getMetaAttribute("OSLEVEL") != null)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2048 */         for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/* 2049 */           EntityItem entityItem = entityGroup.getEntityItem(b1);
/* 2050 */           String str = getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), "OSLEVEL", "None");
/* 2051 */           if (str.equals("None")) {
/* 2052 */             logMessage("discard_nonPublishEntities:OSLEVEL Not Populated ** Discarding entity" + entityItem.getKey());
/* 2053 */             entityGroup.removeEntityItem(entityItem);
/*      */           }
/*      */           else {
/*      */             
/* 2057 */             bool2 = false;
/* 2058 */             eANAttribute = entityItem.getAttribute("OSLEVEL");
/* 2059 */             eANFlagAttribute = (EANFlagAttribute)eANAttribute;
/* 2060 */             arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 2061 */             for (this.i = 0; this.i < arrayOfMetaFlag.length; this.i++) {
/* 2062 */               if (arrayOfMetaFlag[this.i].isSelected()) {
/* 2063 */                 str4 = arrayOfMetaFlag[this.i].getFlagCode();
/* 2064 */                 if (hashtable1.get(str4) != null) {
/* 2065 */                   bool2 = true;
/*      */                 }
/*      */               } 
/*      */             } 
/* 2069 */             if (!bool2) {
/* 2070 */               logMessage("discard_nonPublishEntities:Matching OSLEVEL not found  ** Discarding entity" + entityItem.getKey());
/* 2071 */               entityGroup.removeEntityItem(entityItem);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 2077 */     return bool1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processShortAnswers() {
/* 2084 */     if (this.eiAnnounce == null) {
/* 2085 */       println("Announce EntityItem IS NULL!");
/*      */     }
/* 2087 */     println("CNTLNO = '" + getAttributeValue(this.eiAnnounce, "ANNNUMBER", "Not Populated") + "'");
/* 2088 */     println("TYPE = '" + getAttributeValue(this.eiAnnounce, "OFANNTYPE", "Not Populated") + "'");
/* 2089 */     println("VERSION = '18.03'");
/* 2090 */     this.strSplit = "";
/* 2091 */     logMessage("_1A*******************");
/* 2092 */     this.strFilterAttr = new String[] { "DELIVERABLETYPE" };
/* 2093 */     this.strFilterValue = new String[] { "860" };
/* 2094 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnDeliv, this.strFilterAttr, this.strFilterValue, true);
/* 2095 */     this.eiAnnDeliv = (this.vReturnEntities1.size() > 0) ? this.vReturnEntities1.elementAt(0) : null;
/* 2096 */     this.bConditionOK = false;
/* 2097 */     if (this.eiAnnDeliv != null) {
/* 2098 */       this.bConditionOK = true;
/* 2099 */       this.strSplit = (this.eiAnnDeliv != null) ? getAttributeValue(this.eiAnnDeliv, "SUBJECTLINE_1", "") : "ANNDELIVERABLE NOT LINKED";
/*      */     } 
/* 2101 */     println(".*$P_1A = '" + this.strSplit + "'");
/* 2102 */     this.strSplit = "";
/* 2103 */     if (this.bConditionOK)
/*      */     {
/* 2105 */       this.strSplit = (this.eiAnnDeliv != null) ? getAttributeValue(this.eiAnnDeliv, "SUBJECTLINE_2", "") : "ANNDELIVERABLE NOT LINKED";
/*      */     }
/* 2107 */     println(".*$P_1B = '" + this.strSplit + "'");
/*      */     
/* 2109 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnPara, (String[])null, (String[])null, true, true, "PARAMETERCODE");
/* 2110 */     this.eiParamCode = (this.vReturnEntities1.size() > 0) ? this.vReturnEntities1.elementAt(0) : null;
/* 2111 */     println(".*$P_1C = '" + ((this.eiParamCode != null) ? getAttributeValue(this.eiParamCode, "PARAMETERCODENUMBER", "") : "PARAMETERCODE not linked") + "'");
/*      */     
/* 2113 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpDependCode, (String[])null, (String[])null, true, true, "DEPENDENCYCODE");
/* 2114 */     this.eiDependCode = (this.vReturnEntities1.size() > 0) ? this.vReturnEntities1.elementAt(0) : null;
/* 2115 */     this.strSplit = ((this.eiDependCode != null) ? getAttributeValue(this.eiDependCode, "DEPENCODENUMBER", "Not Populated") : "DEPENDENCYCODE not linked") + "'";
/* 2116 */     this.intSplitLen = this.strSplit.length();
/* 2117 */     logMessage("DEPENCODENUMBER returned " + this.strSplit + " length is " + this.intSplitLen);
/* 2118 */     this.intSplitAt = 47;
/* 2119 */     println(".*$P_1D = '" + this.strSplit.substring(0, (this.intSplitLen > this.intSplitAt) ? this.intSplitAt : this.intSplitLen));
/* 2120 */     this.intSplitAt += 47;
/* 2121 */     println(".*$P_1E = '" + ((this.intSplitLen > this.intSplitAt) ? this.strSplit.substring(48, this.intSplitAt) : "") + "'");
/* 2122 */     this.intSplitAt += 47;
/* 2123 */     println(".*$P_1F = '" + ((this.intSplitLen > this.intSplitAt) ? this.strSplit.substring(96, this.intSplitAt) : "") + "'");
/*      */     
/* 2125 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "MULTIPLEOFFERING", "0");
/* 2126 */     this.strSplit = this.strCondition1.equalsIgnoreCase("Yes") ? "1" : "0";
/* 2127 */     println(".*$P_2A = '" + this.strSplit + "'");
/*      */     
/* 2129 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "STATEOFGENDIRECT", "0");
/* 2130 */     this.strSplit = this.strCondition1.equalsIgnoreCase("Yes") ? "1" : "0";
/* 2131 */     println(".*$P_2B = '" + this.strSplit + "'");
/*      */     
/* 2133 */     println(".*$P_3A = '" + ((this.eiAnnProj != null) ? getAttributeValue(this.eiAnnProj.getEntityType(), this.eiAnnProj.getEntityID(), "AVAILDCP_T", "") : "'") + "'");
/* 2134 */     this.strCondition1 = (this.eiAnnReview != null) ? getAttributeValue(this.eiAnnReview.getEntityType(), this.eiAnnReview.getEntityID(), "ANREVIEW", "") : "";
/* 2135 */     this.strSplit = "";
/* 2136 */     this.strFilterAttr = new String[] { "ANNREVIEWDEF" };
/* 2137 */     this.strFilterValue = new String[] { "101" };
/* 2138 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnReview, this.strFilterAttr, this.strFilterValue, true);
/* 2139 */     this.eiAnnReview = (this.vReturnEntities1.size() > 0) ? this.vReturnEntities1.elementAt(0) : null;
/* 2140 */     this.strSplit = (this.eiAnnReview != null) ? getAttributeValue(this.eiAnnReview.getEntityType(), this.eiAnnReview.getEntityID(), "ANREVDATE", "") : "";
/* 2141 */     println(".*$P_3B = '" + this.strSplit + "'");
/* 2142 */     this.strSplit = "";
/* 2143 */     this.strFilterAttr = new String[] { "ANNREVIEWDEF" };
/* 2144 */     this.strFilterValue = new String[] { "102" };
/* 2145 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnReview, this.strFilterAttr, this.strFilterValue, true);
/* 2146 */     this.eiAnnReview = (this.vReturnEntities1.size() > 0) ? this.vReturnEntities1.elementAt(0) : null;
/* 2147 */     this.strSplit = (this.eiAnnReview != null) ? getAttributeValue(this.eiAnnReview.getEntityType(), this.eiAnnReview.getEntityID(), "ANREVDATE", "") : "";
/* 2148 */     println(".*$P_3C = '" + this.strSplit + "'");
/* 2149 */     println(".*$P_3D = '" + ((this.grpAnnouncement != null) ? getAttributeValue(this.eiAnnounce, "ANNDATE", "") : "") + "'");
/*      */ 
/*      */     
/* 2152 */     println(".*$P_4A = '0'");
/* 2153 */     println(".*$P_4B = '" + ((this.grpAnnouncement != null) ? getAttributeValue(this.eiAnnounce, "REVISIONNUMBER", "") : "'") + "'");
/*      */ 
/*      */ 
/*      */     
/* 2157 */     println(".*$P_6A = '0'");
/*      */     
/* 2159 */     println(".*$P_6B = '0'");
/* 2160 */     println(".*$P_6C = '0'");
/* 2161 */     println(".*$P_6D = '0'");
/* 2162 */     println(".*$P_6E = '0'");
/* 2163 */     println(".*$P_6F = '0'");
/* 2164 */     println(".*$P_6G = '0'");
/* 2165 */     println(".*$P_6H = '0'");
/* 2166 */     println(".*$P_6I = '0'");
/* 2167 */     println(".*$P_6J = '0'");
/* 2168 */     println(".*$P_6K = '0'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2179 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "EXECAPPREADY", "0");
/* 2180 */     println(".*$P_7A = '" + (this.strCondition1.equalsIgnoreCase("Yes") ? "1" : "0") + "'");
/* 2181 */     println(".*$P_7B = '" + ((this.grpAnnouncement != null) ? getAttributeValue(this.eiAnnounce, "EXECAPPRDATE_T", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2186 */     this.bConditionOK = false;
/* 2187 */     this.strFilterAttr = new String[] { "ORGANUNITTYPE" };
/* 2188 */     this.strFilterValue = new String[] { "4156" };
/* 2189 */     this.vReturnEntities1 = searchEntityGroup(this.grpOrganUnit, this.strFilterAttr, this.strFilterValue, false);
/* 2190 */     if (this.vReturnEntities1.size() > 0) {
/* 2191 */       this.eiOrganUnit = this.vReturnEntities1.elementAt(0);
/* 2192 */       this.bConditionOK = true;
/*      */     } 
/* 2194 */     this.strCondition1 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "NAME", "") : "";
/* 2195 */     println(".*$P_7C = '" + this.strCondition1.trim() + "'");
/* 2196 */     this.strCondition1 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "INITIALS", "") : "";
/* 2197 */     println(".*$P_7D = '" + this.strCondition1.trim() + "'");
/* 2198 */     this.strCondition1 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "STREETADDRESS", "") : "";
/*      */     
/* 2200 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "CITY", "") : "";
/*      */     
/* 2202 */     this.strCondition1 += (this.strCondition1.trim().length() > 0 && this.strCondition2.trim().length() > 0) ? (", " + this.strCondition2.trim()) : "";
/*      */     
/* 2204 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "STATE", "") : "";
/* 2205 */     this.strCondition1 += (this.strCondition1.trim().length() > 0 && this.strCondition2.trim().length() > 0) ? (", " + this.strCondition2.trim()) : "";
/*      */     
/* 2207 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "COUNTRY", "") : "";
/* 2208 */     this.strCondition1 += (this.strCondition1.trim().length() > 0 && this.strCondition2.trim().length() > 0) ? (", " + this.strCondition2.trim()) : "";
/*      */     
/* 2210 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "ZIPCODE", "") : "";
/* 2211 */     this.strCondition1 += (this.strCondition1.trim().length() > 0 && this.strCondition2.trim().length() > 0) ? (", " + this.strCondition2.trim()) : "";
/* 2212 */     println(".*$P_7E = '" + this.strCondition1 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2218 */     this.eiAnnToOP = null;
/* 2219 */     this.eiOP = null;
/* 2220 */     this.bConditionOK = false;
/* 2221 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2222 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2223 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "15")) {
/* 2224 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         break;
/*      */       } 
/*      */     } 
/* 2228 */     println(".*$P_8A = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2229 */     println(".*$P_8B = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "STREETADDRESS", "") : "") + "'");
/* 2230 */     this.strSplit = (this.eiOP != null) ? getAttributeValue(this.eiOP, "CITY", "") : "";
/* 2231 */     this.strSplit += ", " + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "STATE", "") : "");
/* 2232 */     this.strSplit += ", " + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "COUNTRY", "") : "");
/* 2233 */     println(".*$P_8C = '" + this.strSplit + "'");
/* 2234 */     println(".*$P_9A = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "SITE", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2239 */     this.eiAnnToOP = null;
/* 2240 */     this.eiOP = null;
/* 2241 */     this.bConditionOK = false;
/* 2242 */     logMessage("Searching fo ANNROLETYPE4");
/* 2243 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2244 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2245 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "4")) {
/* 2246 */         logMessage("Found Searching fo ANNROLETYPE4");
/* 2247 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 2252 */     println(".*$P_9B = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2253 */     println(".*$P_9C = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETUID", "") : "") + "'");
/* 2254 */     println(".*$P_9D = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETNODE", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2259 */     logMessage("Searching fo ANNROLETYPE9");
/* 2260 */     this.eiAnnToOP = null;
/* 2261 */     this.eiOP = null;
/* 2262 */     this.bConditionOK = false;
/* 2263 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2264 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2265 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "9")) {
/* 2266 */         logMessage("Found Searching fo ANNROLETYPE9");
/* 2267 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         break;
/*      */       } 
/*      */     } 
/* 2271 */     println(".*$P_10A = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2272 */     println(".*$P_10B = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "JOBTITLE", "") : "") + "'");
/* 2273 */     println(".*$P_10C = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "TIELINE", "") : "") + "'");
/* 2274 */     this.strSplit = (this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETNODE", "") : "";
/* 2275 */     this.strSplit += "/" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETUID", "") : "");
/* 2276 */     println(".*$P_10D = '" + this.strSplit + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2281 */     this.eiAnnToOP = null;
/* 2282 */     this.eiOP = null;
/* 2283 */     this.bConditionOK = false;
/* 2284 */     logMessage("Searching fo ANNROLETYPE7");
/* 2285 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2286 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2287 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "7")) {
/* 2288 */         logMessage("Found Searching fo ANNROLETYPE7");
/* 2289 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         break;
/*      */       } 
/*      */     } 
/* 2293 */     println(".*$P_11A = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2294 */     println(".*$P_11B = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "TELEPHONE", "") : "") + "'");
/* 2295 */     println(".*$P_11C = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETUID", "") : "") + "'");
/* 2296 */     println(".*$P_11D = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETNODE", "") : "") + "'");
/* 2297 */     println(".*$P_11E = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "EMAIL", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2302 */     logMessage("Searching fo ANNROLETYPE3");
/* 2303 */     this.eiAnnToOP = null;
/* 2304 */     this.eiOP = null;
/* 2305 */     this.bConditionOK = false;
/* 2306 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2307 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2308 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "3")) {
/* 2309 */         logMessage("Found Searching fo ANNROLETYPE3");
/* 2310 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         break;
/*      */       } 
/*      */     } 
/* 2314 */     println(".*$P_11F = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2315 */     println(".*$P_11G = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "TELEPHONE", "") : "") + "'");
/* 2316 */     println(".*$P_11H = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "EMAIL", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2322 */     logMessage("Searching fo ANNROLETYPE6");
/* 2323 */     this.eiAnnToOP = null;
/* 2324 */     this.eiOP = null;
/* 2325 */     this.bConditionOK = false;
/* 2326 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2327 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2328 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "6")) {
/* 2329 */         logMessage("Found Searching fo ANNROLETYPE9");
/* 2330 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         break;
/*      */       } 
/*      */     } 
/* 2334 */     println(".*$P_13A = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2335 */     println(".*$P_13B = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "EMAIL", "") : "") + "'");
/* 2336 */     println(".*$P_13D = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "TELEPHONE", "") : "") + "'");
/* 2337 */     println(".*$P_13E = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "SITE", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2345 */     this.bConditionOK = false;
/* 2346 */     this.eiChannel = null;
/* 2347 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 2348 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 2349 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 2350 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFCHANNEL");
/* 2351 */     this.strFilterAttr = new String[] { "CHANNELNAME", "CHANNELNAME" };
/* 2352 */     this.strFilterValue = new String[] { "364", "365" };
/* 2353 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "CHANNEL");
/* 2354 */     if (this.vReturnEntities3.size() > 0) {
/* 2355 */       println(".*$P_14A = '1'");
/*      */     } else {
/* 2357 */       println(".*$P_14A = '0'");
/*      */     } 
/* 2359 */     this.strFilterAttr = new String[] { "CHANNELNAME", "CHANNELNAME" };
/* 2360 */     this.strFilterValue = new String[] { "369", "370" };
/* 2361 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "CHANNEL");
/* 2362 */     if (this.vReturnEntities3.size() > 0) {
/* 2363 */       println(".*$P_14B = '1'");
/*      */     } else {
/* 2365 */       println(".*$P_14B = '0'");
/*      */     } 
/* 2367 */     this.strFilterAttr = new String[] { "CHANNELNAME", "CHANNELNAME", "CHANNELNAME" };
/* 2368 */     this.strFilterValue = new String[] { "361", "362", "363" };
/* 2369 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "CHANNEL");
/* 2370 */     if (this.vReturnEntities3.size() > 0) {
/* 2371 */       println(".*$P_15A = '1'");
/*      */     } else {
/* 2373 */       println(".*$P_15A = '0'");
/*      */     } 
/* 2375 */     this.strFilterAttr = new String[] { "CHANNELNAME" };
/* 2376 */     this.strFilterValue = new String[] { "375" };
/* 2377 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 2378 */     if (this.vReturnEntities3.size() > 0) {
/* 2379 */       println(".*$P_15B = '1'");
/*      */     } else {
/* 2381 */       println(".*$P_15B = '0'");
/*      */     } 
/*      */     
/* 2384 */     this.strFilterAttr = new String[] { "CHANNELNAME", "CHANNELNAME", "CHANNELNAME" };
/* 2385 */     this.strFilterValue = new String[] { "358", "359", "360" };
/* 2386 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "CHANNEL");
/* 2387 */     if (this.vReturnEntities3.size() > 0) {
/* 2388 */       this.eiChannel = this.vReturnEntities3.elementAt(0);
/* 2389 */       println(".*$P_15C = '1'");
/*      */     } else {
/* 2391 */       println(".*$P_15C = '0'");
/*      */     } 
/* 2393 */     this.strFilterAttr = new String[] { "CHANNELNAME", "CHANNELNAME", "CHANNELNAME" };
/* 2394 */     this.strFilterValue = new String[] { "371", "372", "373" };
/* 2395 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "CHANNEL");
/* 2396 */     if (this.vReturnEntities3.size() > 0) {
/* 2397 */       println(".*$P_15D = '1'");
/*      */     } else {
/* 2399 */       println(".*$P_15D = '0'");
/*      */     } 
/*      */     
/* 2402 */     this.strFilterAttr = new String[] { "CHANNELNAME" };
/* 2403 */     this.strFilterValue = new String[] { "376" };
/* 2404 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 2405 */     if (this.vReturnEntities3.size() > 0) {
/* 2406 */       println(".*$P_15E = '1'");
/*      */     } else {
/* 2408 */       println(".*$P_15E = '0'");
/*      */     } 
/*      */     
/* 2411 */     this.strFilterAttr = new String[] { "CHANNELNAME" };
/* 2412 */     this.strFilterValue = new String[] { "377" };
/* 2413 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 2414 */     if (this.vReturnEntities3.size() > 0) {
/* 2415 */       println(".*$P_15F = '1'");
/*      */     } else {
/* 2417 */       println(".*$P_15F = '0'");
/*      */     } 
/*      */     
/* 2420 */     this.strCondition1 = (this.grpAnnouncement != null) ? getAttributeFlagEnabledValue(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "GENAREANAMEINCL", "") : "";
/*      */ 
/*      */ 
/*      */     
/* 2424 */     this.strCondition2 = "0";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2433 */     println(".*$P_15G = '" + (this.m_geList.isRfaGeoUS(this.eiAnnounce) ? "1" : "0") + "'");
/* 2434 */     println(".*$P_15H = '" + (this.m_geList.isRfaGeoEMEA(this.eiAnnounce) ? "1" : "0") + "'");
/*      */     
/* 2436 */     this.bConditionOK = false;
/* 2437 */     this.j = 0;
/*      */     
/* 2439 */     GeneralAreaGroup generalAreaGroup = this.m_geList.getRfaGeoEMEAInclusion(this.eiAnnounce);
/* 2440 */     logMessage("_15I returned GEItemcount" + generalAreaGroup.getGeneralAreaItemCount());
/* 2441 */     if (generalAreaGroup.getGeneralAreaItemCount() >= 130) {
/* 2442 */       this.bConditionOK = true;
/*      */     }
/*      */     
/* 2445 */     println(".*$P_15I = '" + ((this.m_geList.isRfaGeoEMEA(this.eiAnnounce) && this.bConditionOK) ? "1" : "0") + "'");
/* 2446 */     println(".*$P_15J = '" + (this.m_geList.isRfaGeoAP(this.eiAnnounce) ? "1" : "0") + "'");
/* 2447 */     println(".*$P_15K = '" + (this.m_geList.isRfaGeoLA(this.eiAnnounce) ? "1" : "0") + "'");
/* 2448 */     println(".*$P_15L = '" + (this.m_geList.isRfaGeoCAN(this.eiAnnounce) ? "1" : "0") + "'");
/*      */     
/* 2450 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "CROSSPLATFORM", "");
/* 2451 */     println(".*$P_16A = '" + ((this.strCondition1.indexOf("Yes") > -1) ? "1" : "0") + "'");
/*      */     
/* 2453 */     println(".*$P_16B = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4767") ? "1" : "0") + "'");
/* 2454 */     println(".*$P_16C = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4770") ? "1" : "0") + "'");
/* 2455 */     println(".*$P_16D = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4769") ? "1" : "0") + "'");
/* 2456 */     println(".*$P_16E = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4772") ? "1" : "0") + "'");
/* 2457 */     println(".*$P_16F = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4764") ? "1" : "0") + "'");
/* 2458 */     println(".*$P_16G = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4768") ? "1" : "0") + "'");
/* 2459 */     println(".*$P_16H = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4766") ? "1" : "0") + "'");
/* 2460 */     println(".*$P_16I = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4773") ? "1" : "0") + "'");
/*      */     
/* 2462 */     println(".*$P_16J = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2907") ? "1" : "0") + "'");
/* 2463 */     println(".*$P_16K = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2913") ? "1" : "0") + "'");
/*      */     
/* 2465 */     println(".*$P_16L = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4771") ? "1" : "0") + "'");
/* 2466 */     println(".*$P_16M = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4765") ? "1" : "0") + "'");
/*      */     
/* 2468 */     println(".*$P_16N = '" + (flagvalueEquals(this.eiAnnounce, "ELECTRONICSERVICE", "010") ? "1" : "0") + "'");
/* 2469 */     println(".*$P_16O = '" + (flagvalueEquals(this.eiAnnounce, "ELECTRONICSERVICE", "011") ? "1" : "0") + "'");
/*      */     
/* 2471 */     println(".*$P_17A = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2892") ? "1" : "0") + "'");
/* 2472 */     println(".*$P_17B = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2891") ? "1" : "0") + "'");
/* 2473 */     println(".*$P_17C = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2895") ? "1" : "0") + "'");
/* 2474 */     println(".*$P_17D = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2896") ? "1" : "0") + "'");
/* 2475 */     println(".*$P_17E = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2897") ? "1" : "0") + "'");
/* 2476 */     println(".*$P_17F = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2894") ? "1" : "0") + "'");
/* 2477 */     println(".*$P_17G = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2893") ? "1" : "0") + "'");
/* 2478 */     println(".*$P_17H = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2898") ? "1" : "0") + "'");
/* 2479 */     println(".*$P_17I = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2899") ? "1" : "0") + "'");
/*      */     
/* 2481 */     this.strCondition1 = (this.eiOrganUnit != null) ? getAttributeValue(this.eiOrganUnit, "MNEMONIC", "") : "";
/* 2482 */     println(".*$P_18A = '" + ((this.strCondition1.indexOf("PCD") > -1) ? "1" : "0") + "'");
/*      */     
/* 2484 */     this.strCondition1 = (this.eiPdsQuestions != null) ? getAttributeFlagEnabledValue(this.eiPdsQuestions.getEntityType(), this.eiPdsQuestions.getEntityID(), "PCDCHANNELS", "") : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2499 */     println(".*$P_18B = '0'");
/* 2500 */     println(".*$P_18C = '0'");
/*      */     
/* 2502 */     println(".*$P_18D = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2905") ? "1" : "0") + "'");
/* 2503 */     println(".*$P_18E = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2914") ? "1" : "0") + "'");
/*      */ 
/*      */ 
/*      */     
/* 2507 */     println(".*$P_19A = '0'");
/* 2508 */     println(".*$P_19B = '0'");
/* 2509 */     println(".*$P_19C = '0'");
/* 2510 */     println(".*$P_19D = '0'");
/* 2511 */     println(".*$P_19E = '0'");
/* 2512 */     println(".*$P_19F = '0'");
/* 2513 */     println(".*$P_19G = '0'");
/*      */     
/* 2515 */     this.strCondition1 = (this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "GRADUATEDCHARGES", "") : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2522 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 2523 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/*      */     
/* 2525 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/*      */     
/* 2527 */     this.strFilterAttr = new String[] { "COFSUBGRP", "COFSUBGRP" };
/* 2528 */     this.strFilterValue = new String[] { "400", "402" };
/* 2529 */     this.vReturnEntities2 = searchEntityVector(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, false);
/* 2530 */     logMessage("****COMMERCIALOF*****");
/* 2531 */     displayContents(this.vReturnEntities2);
/* 2532 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "COFPRICE");
/* 2533 */     logMessage("****COFPRICE*****");
/* 2534 */     displayContents(this.vReturnEntities1);
/* 2535 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 2536 */     logMessage("****PRICEFININFO*****");
/* 2537 */     displayContents(this.vReturnEntities2);
/* 2538 */     this.strParamList1 = new String[] { "GRADUATEDCHARGES" };
/* 2539 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, false);
/*      */     
/* 2541 */     println(".*$P_20A = '" + ((this.vPrintDetails.size() > 0) ? "1" : "0") + "'");
/* 2542 */     resetPrintvars();
/*      */     
/* 2544 */     println(".*$P_20B = '" + ((this.eiCommOFIvo != null) ? "1" : "0") + "'");
/*      */ 
/*      */     
/* 2547 */     if (flagvalueEquals(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "PRODSTRUCTURE", "5020") || 
/* 2548 */       flagvalueEquals(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "PRODSTRUCTURE", "5021"))
/*      */     {
/* 2550 */       this.bConditionOK = true;
/*      */     }
/* 2552 */     println(".*$P_20C = '" + (this.bConditionOK ? getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "PRODSTRUCTURE", "'") : "'") + "'");
/*      */ 
/*      */     
/* 2555 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, (String[])null, (String[])null, true, true, "AVAIL");
/* 2556 */     this.strFilterAttr = new String[] { "OOFSUBCAT" };
/* 2557 */     this.strFilterValue = new String[] { "FeatureConvert" };
/* 2558 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "OOFAVAIL");
/* 2559 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 2560 */     if (this.vReturnEntities3.size() > 0) {
/* 2561 */       println(".*$P_20D = '1'");
/*      */     } else {
/* 2563 */       println(".*$P_20D = '0'");
/*      */     } 
/*      */     
/* 2566 */     this.strFilterAttr = new String[] { "RETURNEDPARTS" };
/* 2567 */     this.strFilterValue = new String[] { "5100" };
/* 2568 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 2569 */     if (this.vReturnEntities1.size() > 0) {
/* 2570 */       println(".*$P_20E = '1'");
/*      */     } else {
/* 2572 */       println(".*$P_20E = '0'");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2577 */     this.strFilterAttr = new String[] { "OFFERINGTYPES", "OFFERINGTYPES", "OFFERINGTYPES", "OFFERINGTYPES", "OFFERINGTYPES", "OFFERINGTYPES" };
/* 2578 */     this.strFilterValue = new String[] { "2892", "2891", "2896", "2898", "2909", "2901" };
/* 2579 */     this.bConditionOK = false;
/* 2580 */     if (foundInEntity(this.eiAnnounce, this.strFilterAttr, this.strFilterValue, false)) {
/* 2581 */       this.bConditionOK = true;
/*      */     }
/* 2583 */     println(".*$P_20F = '" + (this.bConditionOK ? "1" : "0") + "'");
/*      */     
/* 2585 */     this.strCondition1 = getAttributeFlagEnabledValue(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "NEWMACHMODELTYPE", "");
/* 2586 */     println(".*$P_21A = '" + ((this.strCondition1.indexOf("2864") > -1) ? "1" : "0") + "'");
/* 2587 */     println(".*$P_21B = '" + ((this.strCondition1.indexOf("2866") > -1) ? "1" : "0") + "'");
/* 2588 */     println(".*$P_21C = '" + ((this.strCondition1.indexOf("2865") > -1) ? "1" : "0") + "'");
/*      */ 
/*      */     
/* 2591 */     this.strFilterAttr = new String[] { "ORDERSYSNAME" };
/* 2592 */     this.strFilterValue = new String[] { "4143" };
/* 2593 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, this.strFilterAttr, this.strFilterValue, true, true, "AVAIL");
/* 2594 */     println(".*$P_21D = '" + ((this.vReturnEntities1.size() > 0) ? "1" : "0") + "'");
/* 2595 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "TAA", "");
/* 2596 */     println(".*$P_21E = '" + (this.strCondition1.equalsIgnoreCase("Yes") ? "1" : "0") + "'");
/*      */     
/* 2598 */     this.strCondition1 = getAttributeFlagEnabledValue(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "QUICKSHIPTYPE", "");
/* 2599 */     println(".*$P_21F = '" + ((this.strCondition1.indexOf("5031") > -1) ? "1" : "0") + "'");
/* 2600 */     println(".*$P_21G = '" + ((this.strCondition1.indexOf("5029") > -1) ? "1" : "0") + "'");
/* 2601 */     println(".*$P_21H = '" + ((this.strCondition1.indexOf("5030") > -1) ? "1" : "0") + "'");
/*      */     
/* 2603 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "HWSWANN", "");
/* 2604 */     println(".*$P_21I = '" + (this.strCondition1.equals("Yes") ? "1" : "0") + "'");
/*      */     
/* 2606 */     print(".*$P_21J = ");
/*      */     
/* 2608 */     this.strFilterAttr = new String[] { "STANDARDAMENDTEXT_TYPE" };
/* 2609 */     this.strFilterValue = new String[] { "5532" };
/* 2610 */     this.vReturnEntities1 = searchEntityGroup(this.grpStdAmendText, this.strFilterAttr, this.strFilterValue, true);
/* 2611 */     if (this.vReturnEntities1.size() > 0) {
/* 2612 */       println("'1'");
/*      */     } else {
/* 2614 */       println("'0'");
/*      */     } 
/*      */     
/* 2617 */     println(".*$P_22A = '" + (flagvalueEquals(this.eiAnnounce, "CONFIGSUPPORT", "677") ? "1" : "0") + "'");
/* 2618 */     println(".*$P_22B = '" + (flagvalueEquals(this.eiAnnounce, "CONFIGSUPPORT", "675") ? "1" : "0") + "'");
/* 2619 */     println(".*$P_22C = '" + (flagvalueEquals(this.eiAnnounce, "CONFIGSUPPORT", "676") ? "1" : "0") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2625 */     this.bConditionOK = false;
/* 2626 */     this.vReturnEntities2 = new Vector();
/* 2627 */     for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 2628 */       this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 2629 */       this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 2630 */       if (this.m_geList.isRfaGeoUS(this.eiConfigurator)) {
/* 2631 */         logMessage("P_23A US Configurator" + this.eiConfigurator.getEntityType() + ":" + this.eiConfigurator.getEntityID());
/* 2632 */         this.bConditionOK = true;
/* 2633 */         this.vReturnEntities2.addElement(this.eiConfigurator);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2638 */     this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(0) : null;
/*      */     
/* 2640 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2641 */     println(".*$P_23A = '" + this.strCondition2 + "'");
/*      */     
/* 2643 */     this.strCondition2 = this.bConditionOK ? (" " + getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "")) : "";
/* 2644 */     println(".*$P_23B = '" + this.strCondition2 + "'");
/*      */     
/* 2646 */     if (this.vReturnEntities2.size() > 1) {
/* 2647 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(1) : null;
/*      */     } else {
/* 2649 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 2652 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2653 */     println(".*$P_23C = '" + this.strCondition2 + "'");
/*      */     
/* 2655 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2656 */     println(".*$P_23D = '" + this.strCondition2 + "'");
/*      */     
/* 2658 */     if (this.vReturnEntities2.size() > 2) {
/* 2659 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/* 2661 */       this.bConditionOK = false;
/*      */     } 
/* 2663 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2664 */     println(".*$P_23E = '" + this.strCondition2 + "'");
/*      */     
/* 2666 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2667 */     println(".*$P_23F = '" + this.strCondition2 + "'");
/*      */     
/* 2669 */     if (this.vReturnEntities2.size() > 3) {
/* 2670 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(3) : null;
/*      */     } else {
/* 2672 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 2675 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2676 */     println(".*$P_23G = '" + this.strCondition2 + "'");
/*      */     
/* 2678 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2679 */     println(".*$P_23H = '" + this.strCondition2 + "'");
/*      */     
/* 2681 */     if (this.vReturnEntities2.size() > 4) {
/* 2682 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(4) : null;
/*      */     } else {
/* 2684 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 2687 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2688 */     println(".*$P_23I = '" + this.strCondition2 + "'");
/* 2689 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2690 */     println(".*$P_23J = '" + this.strCondition2 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2695 */     this.bConditionOK = false;
/* 2696 */     this.vReturnEntities2 = new Vector();
/* 2697 */     for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 2698 */       this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 2699 */       this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 2700 */       logMessage("P_24A AP Configurator" + this.eiConfigurator.getEntityType() + ":" + this.eiConfigurator.getEntityID());
/* 2701 */       if (this.m_geList.isRfaGeoAP(this.eiConfigurator)) {
/* 2702 */         this.bConditionOK = true;
/* 2703 */         this.vReturnEntities2.addElement(this.eiConfigurator);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2708 */     this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(0) : null;
/* 2709 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2710 */     println(".*$P_24A = '" + this.strCondition2 + "'");
/* 2711 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2712 */     println(".*$P_24B = '" + this.strCondition2 + "'");
/*      */     
/* 2714 */     if (this.vReturnEntities2.size() > 1) {
/* 2715 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(1) : null;
/*      */     } else {
/* 2717 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 2720 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2721 */     println(".*$P_24C = '" + this.strCondition2 + "'");
/* 2722 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2723 */     println(".*$P_24D = '" + this.strCondition2 + "'");
/*      */     
/* 2725 */     if (this.vReturnEntities2.size() > 2) {
/* 2726 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/* 2728 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 2731 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2732 */     println(".*$P_24E = '" + this.strCondition2 + "'");
/* 2733 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2734 */     println(".*$P_24F = '" + this.strCondition2 + "'");
/*      */     
/* 2736 */     if (this.vReturnEntities2.size() > 3) {
/* 2737 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(3) : null;
/*      */     } else {
/* 2739 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 2742 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2743 */     println(".*$P_24G = '" + this.strCondition2 + "'");
/* 2744 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2745 */     println(".*$P_24H = '" + this.strCondition2 + "'");
/*      */     
/* 2747 */     if (this.vReturnEntities2.size() > 4) {
/* 2748 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(4) : null;
/*      */     } else {
/* 2750 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 2753 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2754 */     println(".*$P_24I = '" + this.strCondition2 + "'");
/*      */     
/* 2756 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2757 */     println(".*$P_24J = '" + this.strCondition2 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2762 */     this.bConditionOK = false;
/* 2763 */     this.vReturnEntities2 = new Vector();
/* 2764 */     for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 2765 */       this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 2766 */       this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 2767 */       if (this.m_geList.isRfaGeoLA(this.eiConfigurator)) {
/* 2768 */         logMessage("P_25A LA Configurator" + this.eiConfigurator.getEntityType() + ":" + this.eiConfigurator.getEntityID());
/* 2769 */         this.bConditionOK = true;
/* 2770 */         this.vReturnEntities2.addElement(this.eiConfigurator);
/*      */       } 
/*      */     } 
/*      */     
/* 2774 */     this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(0) : null;
/* 2775 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2776 */     println(".*$P_25A = '" + this.strCondition2 + "'");
/* 2777 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2778 */     println(".*$P_25B = '" + this.strCondition2 + "'");
/*      */     
/* 2780 */     if (this.vReturnEntities2.size() > 1) {
/* 2781 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(1) : null;
/*      */     } else {
/* 2783 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 2786 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2787 */     println(".*$P_25C = '" + this.strCondition2 + "'");
/* 2788 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2789 */     println(".*$P_25D = '" + this.strCondition2 + "'");
/*      */     
/* 2791 */     if (this.vReturnEntities2.size() > 2) {
/* 2792 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/* 2794 */       this.bConditionOK = false;
/*      */     } 
/*      */ 
/*      */     
/* 2798 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2799 */     println(".*$P_25E = '" + this.strCondition2 + "'");
/* 2800 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2801 */     println(".*$P_25F = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2804 */     if (this.vReturnEntities2.size() > 3) {
/* 2805 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(3) : null;
/*      */     } else {
/* 2807 */       this.bConditionOK = false;
/*      */     } 
/* 2809 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2810 */     println(".*$P_25G = '" + this.strCondition2 + "'");
/* 2811 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2812 */     println(".*$P_25H = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2815 */     if (this.vReturnEntities2.size() > 4) {
/* 2816 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(4) : null;
/*      */     } else {
/*      */       
/* 2819 */       this.bConditionOK = false;
/*      */     } 
/* 2821 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2822 */     println(".*$P_25I = '" + this.strCondition2 + "'");
/* 2823 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2824 */     println(".*$P_25J = '" + this.strCondition2 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2829 */     this.bConditionOK = false;
/* 2830 */     this.vReturnEntities2 = new Vector();
/* 2831 */     for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 2832 */       this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 2833 */       this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 2834 */       if (this.m_geList.isRfaGeoCAN(this.eiConfigurator)) {
/* 2835 */         logMessage("P_26A CAN Configurator" + this.eiConfigurator.getEntityType() + ":" + this.eiConfigurator.getEntityID());
/* 2836 */         this.bConditionOK = true;
/* 2837 */         this.vReturnEntities2.addElement(this.eiConfigurator);
/*      */       } 
/*      */     } 
/*      */     
/* 2841 */     this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(0) : null;
/* 2842 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2843 */     println(".*$P_26A = '" + this.strCondition2 + "'");
/* 2844 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2845 */     println(".*$P_26B = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2848 */     if (this.vReturnEntities2.size() > 1) {
/* 2849 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(1) : null;
/*      */     } else {
/* 2851 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 2854 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2855 */     println(".*$P_26C = '" + this.strCondition2 + "'");
/* 2856 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2857 */     println(".*$P_26D = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2860 */     if (this.vReturnEntities2.size() > 2) {
/* 2861 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/* 2863 */       this.bConditionOK = false;
/*      */     } 
/* 2865 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2866 */     println(".*$P_26E = '" + this.strCondition2 + "'");
/* 2867 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2868 */     println(".*$P_26F = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2871 */     if (this.vReturnEntities2.size() > 3) {
/* 2872 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(3) : null;
/*      */     } else {
/* 2874 */       this.bConditionOK = false;
/*      */     } 
/* 2876 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2877 */     println(".*$P_26G = '" + this.strCondition2 + "'");
/* 2878 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2879 */     println(".*$P_26H = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2882 */     if (this.vReturnEntities2.size() > 4) {
/* 2883 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(4) : null;
/*      */     } else {
/* 2885 */       this.bConditionOK = false;
/*      */     } 
/* 2887 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2888 */     println(".*$P_26I = '" + this.strCondition2 + "'");
/* 2889 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2890 */     println(".*$P_26J = '" + this.strCondition2 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2895 */     this.bConditionOK = false;
/* 2896 */     this.vReturnEntities2 = new Vector();
/* 2897 */     for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 2898 */       this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 2899 */       this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 2900 */       if (this.m_geList.isRfaGeoEMEA(this.eiConfigurator)) {
/* 2901 */         this.bConditionOK = true;
/* 2902 */         logMessage("P_27A EMEA Configurator" + this.eiConfigurator.getEntityType() + ":" + this.eiConfigurator.getEntityID());
/*      */         
/* 2904 */         this.vReturnEntities2.addElement(this.eiConfigurator);
/*      */       } 
/*      */     } 
/*      */     
/* 2908 */     this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(0) : null;
/* 2909 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2910 */     println(".*$P_27A = '" + this.strCondition2 + "'");
/* 2911 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2912 */     println(".*$P_27B = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2915 */     if (this.vReturnEntities2.size() > 1) {
/* 2916 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(1) : null;
/*      */     } else {
/* 2918 */       this.bConditionOK = false;
/*      */     } 
/* 2920 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2921 */     println(".*$P_27C = '" + this.strCondition2 + "'");
/* 2922 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2923 */     println(".*$P_27D = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2926 */     if (this.vReturnEntities2.size() > 2) {
/* 2927 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/* 2929 */       this.bConditionOK = false;
/*      */     } 
/* 2931 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2932 */     println(".*$P_27E = '" + this.strCondition2 + "'");
/* 2933 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2934 */     println(".*$P_27F = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2937 */     if (this.vReturnEntities2.size() > 3) {
/* 2938 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(3) : null;
/*      */     } else {
/* 2940 */       this.bConditionOK = false;
/*      */     } 
/* 2942 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2943 */     println(".*$P_27G = '" + this.strCondition2 + "'");
/* 2944 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2945 */     println(".*$P_27H = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2948 */     if (this.vReturnEntities2.size() > 4) {
/* 2949 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(4) : null;
/*      */     } else {
/* 2951 */       this.bConditionOK = false;
/*      */     } 
/* 2953 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", "") : "";
/* 2954 */     println(".*$P_27I = '" + this.strCondition2 + "'");
/* 2955 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 2956 */     println(".*$P_27J = '" + this.strCondition2 + "'");
/*      */ 
/*      */     
/* 2959 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "COMPLEMENTARTBP", "");
/* 2960 */     println(".*$P_32A = '" + (this.strCondition1.equals("Yes") ? "1" : "0") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2966 */     if (this.strCondition1.equals("Yes")) {
/*      */       
/* 2968 */       this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 2969 */       this.strFilterValue = new String[] { "100", "208", "301" };
/*      */       
/* 2971 */       this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/*      */       
/* 2973 */       this.strFilterAttr = new String[] { "COFSUBGRP", "COFSUBGRP" };
/* 2974 */       this.strFilterValue = new String[] { "400", "402" };
/* 2975 */       this.vReturnEntities2 = searchEntityVector(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, false);
/*      */       
/* 2977 */       logMessage("****COMMERCIALOF*****");
/* 2978 */       displayContents(this.vReturnEntities2);
/* 2979 */       this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "COFPRICE");
/* 2980 */       logMessage("****COFPRICE*****");
/* 2981 */       displayContents(this.vReturnEntities1);
/* 2982 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 2983 */       logMessage("****PRICEFININFO*****");
/* 2984 */       displayContents(this.vReturnEntities2);
/* 2985 */       this.strCondition1 = "";
/*      */       
/* 2987 */       for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 2988 */         this.eiPriceInfo = this.vReturnEntities2.elementAt(this.i);
/* 2989 */         this.strCondition1 = getAttributeValue(this.eiPriceInfo, "COMPLEMENTARTBPFEE", "");
/* 2990 */         if (this.strCondition1.trim().length() > 0) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } else {
/* 2995 */       this.strCondition1 = "0";
/*      */     } 
/* 2997 */     println(".*$P_32B = '" + this.strCondition1 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3010 */     println(".*$P_33A = '0'");
/* 3011 */     println(".*$P_33B = '0'");
/* 3012 */     println(".*$P_33C = '0'");
/* 3013 */     println(".*$P_33D = '0'");
/* 3014 */     println(".*$P_33E = '0'");
/* 3015 */     println(".*$P_33F = '0'");
/* 3016 */     println(".*$P_33G = '0'");
/* 3017 */     println(".*$P_33H = '0'");
/* 3018 */     println(".*$P_33I = '0'");
/* 3019 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "PRICESLISTEDINANNLETTER", "");
/* 3020 */     println(".*$P_34A = '" + (this.strCondition1.equals("Yes") ? "1" : "0") + "'");
/* 3021 */     println(".*$P_35A = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2895") ? "1" : "0") + "'");
/* 3022 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "OFFERINGACCESS", "");
/* 3023 */     println(".*$P_51A = '" + (this.strCondition1.equals("Yes") ? "1" : "0") + "'");
/* 3024 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "MARKETEDIBMLOGO", "");
/* 3025 */     println(".*$P_51B = '" + (this.strCondition1.equals("Yes") ? "1" : "0") + "'");
/* 3026 */     println(".*$P_51C = '" + (flagvalueEquals(this.eiAnnounce, "LOGOACCESSREQTS", "2833") ? "1" : "0") + "'");
/* 3027 */     println(".*$P_51D = '" + (flagvalueEquals(this.eiAnnounce, "LOGOACCESSREQTS", "2834") ? "1" : "0") + "'");
/*      */     
/* 3029 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "PRELOADEDSW", "");
/* 3030 */     println(".*$P_52B = '" + (this.strCondition1.equals("Yes") ? "1" : "0") + "'");
/*      */     
/* 3032 */     println(".*$P_54A = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2891") ? "1" : "0") + "'");
/* 3033 */     println(".*$P_54B = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2902") ? "1" : "0") + "'");
/* 3034 */     println(".*$P_54C = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2903") ? "1" : "0") + "'");
/* 3035 */     println(".*$P_54D = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2904") ? "1" : "0") + "'");
/* 3036 */     println(".*$P_54E = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2893") ? "1" : "0") + "'");
/* 3037 */     println(".*$P_54F = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2910") ? "1" : "0") + "'");
/* 3038 */     println(".*$P_54G = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2911") ? "1" : "0") + "'");
/* 3039 */     println(".*$P_54H = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2906") ? "1" : "0") + "'");
/* 3040 */     println(".*$P_54I = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2908") ? "1" : "0") + "'");
/* 3041 */     println(".*$P_54J = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2909") ? "1" : "0") + "'");
/* 3042 */     println(".*$P_54K = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2985") ? "1" : "0") + "'");
/* 3043 */     println(".*$P_54L = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2892") ? "1" : "0") + "'");
/* 3044 */     println(".*$P_54M = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2896") ? "1" : "0") + "'");
/* 3045 */     println(".*$P_54N = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2898") ? "1" : "0") + "'");
/* 3046 */     println(".*$P_54O = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2899") ? "1" : "0") + "'");
/* 3047 */     println(".*$P_54P = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2912") ? "1" : "0") + "'");
/* 3048 */     println(".*$P_54Q = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2901") ? "1" : "0") + "'");
/* 3049 */     println(".*$P_54R = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2900") ? "1" : "0") + "'");
/* 3050 */     this.strCondition1 = getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "TGTCUSTOMERAUD", "");
/*      */     
/* 3052 */     println(".*$P_58A = '" + ((this.strCondition1.trim().length() > 61) ? this.strCondition1.substring(0, 61) : this.strCondition1) + "'");
/* 3053 */     println(".*$VARIABLES_END");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo100() {
/* 3061 */     println(".*$A_001_Begin");
/* 3062 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 3063 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 3064 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 3065 */     resetPrintvars();
/* 3066 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3067 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 3068 */       this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "COMNAME", " "));
/*      */     } 
/* 3070 */     this.strHeader = new String[] { "Announced Product Names" };
/* 3071 */     this.iColWidths = new int[] { 55 };
/* 3072 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 3073 */     resetPrintvars();
/* 3074 */     println(".*$A_001_End");
/*      */     
/* 3076 */     println(".*$A_002_Begin");
/* 3077 */     println(".*$A_002_End");
/* 3078 */     println(".*$A_007_Begin");
/* 3079 */     println(getAttributeValue(this.eiAnnounce, "ANNIMAGES", " "));
/* 3080 */     println(".*$A_007_End");
/* 3081 */     println(".*$A_040_Begin");
/* 3082 */     println(":xmp.");
/* 3083 */     println(".kp off");
/* 3084 */     this.strHeader = new String[] { "Role Type", "Name", "Telephone", "Node/ID" };
/* 3085 */     this.iColWidths = new int[] { 15, 18, 12, 17 };
/* 3086 */     this.strFilterAttr = new String[] { "ANNROLETYPE", "ANNROLETYPE", "ANNROLETYPE", "ANNROLETYPE" };
/* 3087 */     this.strFilterValue = new String[] { "11", "12", "13", "14" };
/* 3088 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnToOP, this.strFilterAttr, this.strFilterValue, false);
/* 3089 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3090 */       this.eiAnnToOP = this.vReturnEntities1.elementAt(this.i);
/* 3091 */       this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/* 3092 */       this.strCondition1 = getAttributeValue(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", " ");
/* 3093 */       this.vPrintDetails.add((this.strCondition1.length() >= 14) ? this.strCondition1.substring(0, 14) : this.strCondition1.substring(0, this.strCondition1.length()));
/* 3094 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3095 */       this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3096 */       this.vPrintDetails.add(this.strCondition1);
/* 3097 */       this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3098 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3099 */       this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3100 */       this.vPrintDetails.add(this.strCondition1);
/*      */     } 
/* 3102 */     if (this.vPrintDetails.size() > 0) {
/* 3103 */       println("This RFA and its requested schedule hae been reviewed with the");
/* 3104 */       println("following functional representatives");
/* 3105 */       println(":xmp.");
/* 3106 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 3107 */       resetPrintvars();
/* 3108 */       println(":exmp.");
/*      */     } 
/* 3110 */     println(".*$A_040_End");
/*      */     
/* 3112 */     println(".*$A_042_Begin");
/* 3113 */     println(":xmp.");
/* 3114 */     println(".in 0");
/* 3115 */     println(".kp off");
/* 3116 */     this.vPrintDetails1 = new Vector();
/* 3117 */     for (this.i = 0; this.i < this.grpAnnToOrgUnit.getEntityItemCount(); this.i++) {
/* 3118 */       this.eiAnnToOrgUnit = this.grpAnnToOrgUnit.getEntityItem(this.i);
/* 3119 */       this.eiOrganUnit = (EntityItem)this.eiAnnToOrgUnit.getDownLink(0);
/* 3120 */       this.vPrintDetails.add(getAttributeValue(this.eiAnnToOrgUnit.getEntityType(), this.eiAnnToOrgUnit.getEntityID(), "GENAREASELECTION", " "));
/* 3121 */       this.strCondition1 = getAttributeValue(this.eiAnnToOrgUnit.getEntityType(), this.eiAnnToOrgUnit.getEntityID(), "POLICYHITNAME", " ") + " ";
/* 3122 */       this.strCondition1 += "/" + getAttributeValue(this.eiAnnToOrgUnit.getEntityType(), this.eiAnnToOrgUnit.getEntityID(), "PRODHITNAME", " ");
/* 3123 */       this.vPrintDetails.add(this.strCondition1);
/* 3124 */       this.vPrintDetails.add(getAttributeValue(this.eiAnnToOrgUnit.getEntityType(), this.eiAnnToOrgUnit.getEntityID(), "DEPENDENCYTYPE", " "));
/*      */     } 
/* 3126 */     this.strHeader = new String[] { "Organization", "Policy/Product Hit Name", "Dependency Type" };
/* 3127 */     this.iColWidths = new int[] { 27, 23, 15 };
/*      */     
/* 3129 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 3130 */     resetPrintvars();
/*      */     
/* 3132 */     println(":exmp.");
/* 3133 */     println(".*$A_042_End");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3139 */     println(".*$A_044_Begin");
/* 3140 */     println(":xmp.");
/* 3141 */     println(".in 0");
/* 3142 */     println(".kp off");
/* 3143 */     for (this.i = 0; this.i < this.grpRelatedANN.getEntityItemCount(); this.i++) {
/* 3144 */       this.eiRelatedANN = this.grpRelatedANN.getEntityItem(this.i);
/*      */       
/* 3146 */       for (this.j = 0; this.j < this.eiRelatedANN.getDownLinkCount(); this.j++) {
/* 3147 */         this.eiNextItem = (EntityItem)this.eiRelatedANN.getDownLink(this.j);
/* 3148 */         logMessage("**********_044 next item" + this.eiNextItem.getEntityType() + this.eiNextItem.getEntityID());
/* 3149 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem, "ANNTITLE"));
/* 3150 */         this.vPrintDetails1.add(getAttributeValue(this.eiNextItem, "ANNNUMBER"));
/*      */       } 
/*      */     } 
/* 3153 */     this.strHeader = new String[] { "Announcement Title" };
/* 3154 */     this.iColWidths = new int[] { 69 };
/* 3155 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 3156 */     resetPrintvars();
/*      */     
/* 3158 */     this.strHeader = new String[] { "Announcement Number" };
/* 3159 */     this.iColWidths = new int[] { 19 };
/* 3160 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails1);
/* 3161 */     if (this.vPrintDetails1.size() > 0) {
/* 3162 */       this.vPrintDetails1.removeAllElements();
/*      */     }
/* 3164 */     println(":exmp.");
/* 3165 */     println(".*$A_044_End");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3170 */     println(".*$A_045_Begin");
/* 3171 */     println(":xmp.");
/* 3172 */     println(".in 0");
/* 3173 */     println(":hp2.Being released to::ehp2.");
/* 3174 */     println(".kp off");
/*      */ 
/*      */     
/* 3177 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 3178 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 3179 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/*      */     
/* 3181 */     this.strFilterAttr = new String[] { "OFINDVTYPE" };
/* 3182 */     this.strFilterValue = new String[] { "2918" };
/* 3183 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, true, true, "COFOP");
/*      */ 
/*      */     
/* 3186 */     this.vReturnEntities1 = searchInGeo(this.vReturnEntities2, "US");
/* 3187 */     resetPrintvars();
/* 3188 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3189 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 3190 */       this.eiOP = (EntityItem)this.eiNextItem.getDownLink(0);
/* 3191 */       this.vPrintDetails.add(getAttributeValue(this.eiNextItem.getEntityType(), this.eiNextItem.getEntityID(), "GENAREASELECTION", " "));
/* 3192 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3193 */       this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3194 */       this.vPrintDetails.add(this.strCondition1);
/* 3195 */       this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3196 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3197 */       this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3198 */       this.vPrintDetails.add(this.strCondition1);
/*      */     } 
/*      */ 
/*      */     
/* 3202 */     this.vReturnEntities1 = searchInGeo(this.vReturnEntities2, "EMEA");
/* 3203 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3204 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 3205 */       this.eiOP = (EntityItem)this.eiNextItem.getDownLink(0);
/* 3206 */       this.vPrintDetails.add(getAttributeValue(this.eiNextItem.getEntityType(), this.eiNextItem.getEntityID(), "GENAREASELECTION", " "));
/* 3207 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3208 */       this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3209 */       this.vPrintDetails.add(this.strCondition1);
/* 3210 */       this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3211 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3212 */       this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3213 */       this.vPrintDetails.add(this.strCondition1);
/*      */     } 
/*      */ 
/*      */     
/* 3217 */     this.vReturnEntities1 = searchInGeo(this.vReturnEntities2, "AP");
/* 3218 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3219 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 3220 */       this.eiOP = (EntityItem)this.eiNextItem.getDownLink(0);
/* 3221 */       this.vPrintDetails.add(getAttributeValue(this.eiNextItem.getEntityType(), this.eiNextItem.getEntityID(), "GENAREASELECTION", " "));
/* 3222 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3223 */       this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3224 */       this.vPrintDetails.add(this.strCondition1);
/* 3225 */       this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3226 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3227 */       this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3228 */       this.vPrintDetails.add(this.strCondition1);
/*      */     } 
/*      */ 
/*      */     
/* 3232 */     this.vReturnEntities1 = searchInGeo(this.vReturnEntities2, "LA");
/* 3233 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3234 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 3235 */       this.eiOP = (EntityItem)this.eiNextItem.getDownLink(0);
/* 3236 */       this.vPrintDetails.add(getAttributeValue(this.eiNextItem.getEntityType(), this.eiNextItem.getEntityID(), "GENAREASELECTION", " "));
/* 3237 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3238 */       this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3239 */       this.vPrintDetails.add(this.strCondition1);
/* 3240 */       this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3241 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3242 */       this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3243 */       this.vPrintDetails.add(this.strCondition1);
/*      */     } 
/*      */ 
/*      */     
/* 3247 */     this.vReturnEntities1 = searchInGeo(this.vReturnEntities2, "CA");
/* 3248 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3249 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 3250 */       this.eiOP = (EntityItem)this.eiNextItem.getDownLink(0);
/* 3251 */       this.vPrintDetails.add(getAttributeValue(this.eiNextItem.getEntityType(), this.eiNextItem.getEntityID(), "GENAREASELECTION", " "));
/* 3252 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3253 */       this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3254 */       this.vPrintDetails.add(this.strCondition1);
/* 3255 */       this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3256 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3257 */       this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3258 */       this.vPrintDetails.add(this.strCondition1);
/*      */     } 
/*      */     
/* 3261 */     this.strHeader = new String[] { "Geography", "Product Administrator", " Telephone", "    Node/Userid" };
/* 3262 */     this.iColWidths = new int[] { 10, 21, 12, 17 };
/* 3263 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 3264 */     resetPrintvars();
/*      */     
/* 3266 */     println(":exmp.");
/* 3267 */     println(".*$A_045_End");
/*      */     
/* 3269 */     println(".*$A_046_Begin");
/* 3270 */     println(getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "TELECOMMEQ", " "));
/* 3271 */     println(".*$A_046_End");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3276 */     println(".*$A_049_Begin");
/* 3277 */     println(":xmp.");
/* 3278 */     println(".kp off");
/* 3279 */     this.strFilterAttr = new String[] { "ORGANUNITTYPE", "ORGANUNITTYPE" };
/* 3280 */     this.strFilterValue = new String[] { "4156", "4157" };
/* 3281 */     this.strCondition4 = "";
/* 3282 */     this.vReturnEntities1 = searchEntityGroup(this.grpOrganUnit, this.strFilterAttr, this.strFilterValue, false);
/* 3283 */     if (this.vReturnEntities1.size() > 0) {
/* 3284 */       this.eiOrganUnit = this.vReturnEntities1.elementAt(0);
/* 3285 */       this.strCondition4 = getAttributeValue(this.eiOrganUnit, "NAME", " ");
/*      */     } 
/*      */     
/* 3288 */     this.eiAnnToOP = null;
/* 3289 */     this.eiOP = null;
/* 3290 */     this.strFilterAttr = new String[] { "ANNROLETYPE" };
/* 3291 */     this.strFilterValue = new String[] { "5" };
/* 3292 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnToOP, this.strFilterAttr, this.strFilterValue, true);
/* 3293 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "OP");
/*      */     
/* 3295 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 3296 */       this.eiOP = this.vReturnEntities2.elementAt(this.i);
/* 3297 */       this.strCondition2 = getAttributeValue(this.eiOP, "FIRSTNAME", " ");
/* 3298 */       this.strCondition1 = getAttributeValue(this.eiOP, "MIDDLENAME", " ");
/* 3299 */       this.strCondition2 += this.strCondition1.equals(" ") ? "" : (" " + this.strCondition1 + ".");
/* 3300 */       this.strCondition1 = getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3301 */       this.strCondition2 += this.strCondition1.equals(" ") ? "" : (" " + this.strCondition1);
/* 3302 */       println("                     " + this.strCondition2);
/* 3303 */       println("                     " + getAttributeValue(this.eiOP, "JOBTITLE", " "));
/* 3304 */       println("                     " + this.strCondition4);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3311 */     println(":exmp.");
/* 3312 */     println(".*$A_049_End");
/*      */     
/* 3314 */     println(".*$A_053_Begin");
/* 3315 */     prettyPrint(getAttributeValue(this.eiAnnounce, "ANNTITLE", " "), 69);
/* 3316 */     println(".*$A_053_End");
/*      */     
/* 3318 */     println(".*$A_056_Begin");
/* 3319 */     this.strFilterAttr = new String[] { "AVAILTYPE" };
/* 3320 */     this.strFilterValue = new String[] { "146" };
/* 3321 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, this.strFilterAttr, this.strFilterValue, true, true, "AVAIL");
/* 3322 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "OOFAVAIL");
/*      */ 
/*      */     
/* 3325 */     this.strCondition2 = "";
/* 3326 */     this.vReturnEntities4.removeAllElements();
/* 3327 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 3328 */       this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 3329 */       this.eiOrderOF = (EntityItem)this.eiNextItem.getUpLink(0);
/* 3330 */       this.eiAvail = (EntityItem)this.eiNextItem.getDownLink(0);
/* 3331 */       this.strCondition1 = getGeoTags(this.eiAvail);
/* 3332 */       this.strCondition4 = getAttributeValue(this.eiAvail, "EFFECTIVEDATE", " ");
/* 3333 */       this.strCondition3 = getAttributeValue(this.eiOrderOF, "FEATURECODE", " ");
/* 3334 */       this.strCondition2 = getAttributeValue(this.eiOrderOF, "INVNAME", " ");
/* 3335 */       this.vReturnEntities4.add(this.strCondition4 + "|" + this.strCondition3 + "|" + this.strCondition2 + "|" + this.strCondition1);
/*      */     } 
/* 3337 */     Object[] arrayOfObject = null;
/* 3338 */     arrayOfObject = this.vReturnEntities4.toArray();
/* 3339 */     Arrays.sort(arrayOfObject);
/*      */     
/* 3341 */     resetPrintvars();
/* 3342 */     this.strCondition7 = "";
/* 3343 */     this.strCondition5 = "";
/* 3344 */     for (this.i = 0; this.i < arrayOfObject.length; this.i++) {
/* 3345 */       this.strCondition1 = (String)arrayOfObject[this.i];
/* 3346 */       logMessage("String stored at " + this.i + ":" + this.strCondition1);
/* 3347 */       this.iTemp = this.strCondition1.indexOf("|");
/* 3348 */       this.strCondition2 = this.strCondition1.substring(0, this.iTemp);
/* 3349 */       this.strCondition1 = this.strCondition1.substring(this.iTemp + 1);
/* 3350 */       logMessage("Found | at   " + this.iTemp);
/* 3351 */       logMessage("Parsed Date  " + this.strCondition2);
/* 3352 */       this.iTemp = this.strCondition1.indexOf("|");
/* 3353 */       this.strCondition3 = this.strCondition1.substring(0, this.iTemp);
/* 3354 */       this.strCondition1 = this.strCondition1.substring(this.iTemp + 1);
/* 3355 */       logMessage("Found | at   " + this.iTemp);
/* 3356 */       logMessage("Parsed  Featurecode " + this.strCondition3);
/*      */       
/* 3358 */       this.iTemp = this.strCondition1.indexOf("|");
/* 3359 */       this.strCondition6 = this.strCondition1.substring(0, this.iTemp);
/* 3360 */       this.strCondition1 = this.strCondition1.substring(this.iTemp + 1);
/* 3361 */       logMessage("Found | at   " + this.iTemp);
/* 3362 */       logMessage("Parsed  Invname " + this.strCondition6);
/*      */       
/* 3364 */       this.strCondition4 = this.strCondition1;
/* 3365 */       logMessage("Parsed GEO  " + this.strCondition4);
/* 3366 */       if (!this.strCondition4.equals(this.strCondition5)) {
/* 3367 */         if (!this.strCondition5.equals("US, AP, CAN, EMEA, LA") && 
/* 3368 */           this.strCondition5.trim().length() > 0) {
/* 3369 */           logMessage("Ending GEO Break  " + this.strCondition5);
/* 3370 */           this.vPrintDetails.add("9999$$BREAKHERE$$.br;:hp2.<---" + this.strCondition5 + ":ehp2.");
/* 3371 */           this.vPrintDetails.add("");
/* 3372 */           this.vPrintDetails.add("");
/*      */         } 
/*      */         
/* 3375 */         if (!this.strCondition4.equals("US, AP, CAN, EMEA, LA") && 
/* 3376 */           this.strCondition4.trim().length() > 0) {
/* 3377 */           logMessage("Starting GEO Break  " + this.strCondition4);
/* 3378 */           this.vPrintDetails.add("    $$BREAKHERE$$:p.:hp2." + this.strCondition4 + "--->:ehp2.");
/* 3379 */           this.vPrintDetails.add("");
/* 3380 */           this.vPrintDetails.add("");
/*      */         } 
/*      */         
/* 3383 */         this.strCondition5 = this.strCondition4;
/*      */       } 
/*      */       
/* 3386 */       if (!this.strCondition7.equals(this.strCondition2)) {
/* 3387 */         this.vPrintDetails.add(this.strCondition2);
/* 3388 */         this.strCondition7 = this.strCondition2;
/*      */       } else {
/* 3390 */         this.vPrintDetails.add("");
/*      */       } 
/* 3392 */       this.vPrintDetails.add(this.strCondition3);
/* 3393 */       this.vPrintDetails.add(this.strCondition6);
/*      */     } 
/*      */ 
/*      */     
/* 3397 */     if (!this.strCondition5.equals("US, AP, CAN, EMEA, LA") && 
/* 3398 */       this.strCondition5.trim().length() > 0) {
/* 3399 */       logMessage("Final Ending GEO Break  " + this.strCondition5);
/* 3400 */       this.vPrintDetails.add("9999$$BREAKHERE$$.br;:hp2.<---" + this.strCondition5 + ":ehp2.");
/* 3401 */       this.vPrintDetails.add("");
/* 3402 */       this.vPrintDetails.add("");
/*      */     } 
/*      */ 
/*      */     
/* 3406 */     if (this.vPrintDetails.size() > 0) {
/* 3407 */       this.strHeader = new String[] { "Plan Avail", "Feature Code", "Description" };
/* 3408 */       this.iColWidths = new int[] { 10, 17, 28 };
/* 3409 */       println(":hp2.Planned Availability Date::ehp2.");
/* 3410 */       println(":xmp.");
/* 3411 */       println(".kp off");
/* 3412 */       this.rfaReport.setPrintDupeLines(false);
/* 3413 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 3414 */       this.rfaReport.setPrintDupeLines(true);
/* 3415 */       resetPrintvars();
/* 3416 */       println(":exmp.");
/*      */     } 
/*      */     
/* 3419 */     logMessage("A_056_End");
/* 3420 */     println(":exmp.");
/*      */     
/* 3422 */     println(".*$A_056_End");
/*      */     
/* 3424 */     println(".*$A_057_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3431 */     resetPrintvars();
/* 3432 */     this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT" };
/* 3433 */     this.strFilterValue = new String[] { "Hardware", "Model" };
/* 3434 */     this.vReturnEntities2 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 3435 */     logMessage("****ORDEROF*****");
/* 3436 */     displayContents(this.vReturnEntities2);
/* 3437 */     this.strCondition2 = "";
/* 3438 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 3439 */       this.eiOrderOF = this.vReturnEntities2.elementAt(this.i);
/* 3440 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "INVNAME", " "));
/* 3441 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", " "));
/* 3442 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MODEL", " "));
/* 3443 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 3444 */       this.vPrintDetails.add(this.strCondition1.equals("CIF") ? "Y" : "N");
/*      */     } 
/* 3446 */     this.iColWidths = new int[] { 30, 11, 6, 3 };
/* 3447 */     this.iSortCols = new int[] { 2 };
/*      */     
/* 3449 */     println(":h3.Models");
/* 3450 */     println(":xmp.");
/* 3451 */     println(".kp off");
/* 3452 */     println("                                Type        Model");
/* 3453 */     this.strHeader = new String[] { " Description", "Number", "Number", "CSU" };
/* 3454 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails, this.iSortCols);
/* 3455 */     resetPrintvars();
/* 3456 */     println(":exmp.");
/* 3457 */     println(":p.");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3504 */     this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT", "CHARGEFEE" };
/* 3505 */     this.strFilterValue = new String[] { "Hardware", "FeatureCode", "Yes" };
/* 3506 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/*      */     
/* 3508 */     logMessage("_057 Existing Features...Source ORDEROF");
/* 3509 */     displayContents(this.vReturnEntities1);
/*      */ 
/*      */ 
/*      */     
/* 3513 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "OOFFUP");
/* 3514 */     logMessage("_057 Existing Features...Source OOFFUP");
/* 3515 */     displayContents(this.vReturnEntities2);
/* 3516 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "FUP");
/* 3517 */     logMessage("_057 Existing Features...Source FUP");
/* 3518 */     displayContents(this.vReturnEntities3);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3524 */       EntityItem[] arrayOfEntityItem = (EntityItem[])Array.newInstance(EntityItem.class, this.vReturnEntities3.size());
/*      */ 
/*      */       
/* 3527 */       for (this.i = 0; this.i < this.vReturnEntities3.size(); this.i++) {
/* 3528 */         arrayOfEntityItem[this.i] = this.vReturnEntities3.elementAt(this.i);
/*      */       }
/* 3530 */       ExtractActionItem extractActionItem = new ExtractActionItem(null, getDatabase(), getProfile(), "EXTRFA02");
/* 3531 */       logMessage("Creating Entity List for EXTRFA02");
/* 3532 */       logMessage("Profile is " + getProfile());
/* 3533 */       logMessage("Extractaction Item is" + extractActionItem);
/* 3534 */       logMessage("Entity Item" + arrayOfEntityItem);
/* 3535 */       EntityList entityList = this.m_db.getEntityList(getProfile(), extractActionItem, arrayOfEntityItem);
/* 3536 */       logMessage("***************FUP Entitylist");
/* 3537 */       logMessage(entityList.dump(false));
/* 3538 */       logMessage("***************FUP Entitylist END");
/*      */       
/* 3540 */       this.grpOrderOF2 = entityList.getEntityGroup("ORDEROF");
/* 3541 */       this.eiOrderOF = null;
/* 3542 */       if (this.grpOrderOF2 != null) {
/* 3543 */         this.eiOrderOF = this.grpOrderOF2.getEntityItem(0);
/*      */       } else {
/* 3545 */         logMessage("**************ORDEROF not found in FUP Entitylist**");
/*      */       }
/*      */     
/* 3548 */     } catch (Exception exception) {
/* 3549 */       exception.printStackTrace();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3554 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "ANNCODENAME");
/* 3555 */     this.strCondition5 = getAttributeValue(this.eiAnnounce, "ANNDATE", "");
/*      */     
/* 3557 */     this.hNoDupeLines = new Hashtable<>();
/* 3558 */     this.vReturnEntities4 = new Vector();
/* 3559 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3560 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 3561 */       logMessage("_057 Existing Features..matching FEATCODE MACHTYPE OFSTATUS of " + this.eiOrderOF.getKey());
/* 3562 */       this.strFilterAttr = new String[] { "FEATURECODE", "MACHTYPE", "OFSTATUS" };
/* 3563 */       this.strFilterValue = new String[] { getAttributeValue(this.eiOrderOF, "FEATURECODE", " "), getAttributeValue(this.eiOrderOF, "MACHTYPE", " "), "112" };
/*      */ 
/*      */       
/* 3566 */       this.vReturnEntities3 = searchEntityGroup(this.grpOrderOF2, this.strFilterAttr, this.strFilterValue, true);
/* 3567 */       logMessage("_057 Existing Features....Found match for " + this.eiOrderOF.getKey());
/* 3568 */       displayContents(this.vReturnEntities3);
/*      */       
/* 3570 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 3571 */       logMessage("_057 Existing Features...Target OOFAVAIL");
/* 3572 */       displayContents(this.vReturnEntities2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3581 */       this.strParamList1 = new String[] { "ANNAVAILA", "ANNOUNCEMENT" };
/*      */       
/* 3583 */       for (this.j = 0; this.j < this.vReturnEntities2.size(); this.j++) {
/* 3584 */         this.eiOrderOFAvail = this.vReturnEntities2.elementAt(this.j);
/* 3585 */         this.eiNextItem = (EntityItem)this.eiOrderOFAvail.getUpLink(0);
/* 3586 */         logMessage("_057 Existing Features Qualifying ORDEROF" + this.eiNextItem.getKey());
/* 3587 */         this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 3588 */         this.strCondition2 = getAttributeFlagEnabledValue(this.eiAvail, "AVAILTYPE").trim();
/*      */         
/* 3590 */         if (this.strCondition2.equals("146")) {
/*      */           
/* 3592 */           this.eiNextItem2 = getUplinkedEntityItem(this.eiAvail, this.strParamList1);
/* 3593 */           logMessage("_057 Existing Features linked ANNOUNCEMENT" + this.eiNextItem2.getKey());
/*      */           
/* 3595 */           this.strCondition3 = getAttributeValue(this.eiNextItem2, "ANNCODENAME");
/* 3596 */           logMessage("_057 Existing Features linked ANNOUNCEMENT ANNCODENAME" + this.strCondition3);
/*      */           
/* 3598 */           this.strCondition4 = getAttributeValue(this.eiNextItem2, "ANCYCLESTATUS");
/* 3599 */           logMessage("_057 Existing Features linked ANNOUNCEMENT ANCYCLESTATUS" + this.strCondition4);
/*      */           
/* 3601 */           this.strCondition6 = getAttributeValue(this.eiNextItem2, "ANNDATE", "");
/* 3602 */           logMessage("_057 Existing Features linked ANNOUNCEMENT ANNDATE" + this.strCondition6);
/*      */           
/* 3604 */           this.strCondition4 = this.strCondition4.substring((this.strCondition4.indexOf("|") == -1) ? 0 : (this.strCondition4.indexOf("|") + 1));
/* 3605 */           if (!this.strCondition3.equals(this.strCondition1) && 
/* 3606 */             !this.strCondition4.equals("122") && this.strCondition5
/* 3607 */             .compareTo(this.strCondition6) > 0) {
/* 3608 */             this.vReturnEntities4.addElement(this.eiOrderOF);
/* 3609 */             this.hNoDupeLines.put(this.eiOrderOF.getKey(), "Announced Offering");
/*      */           } 
/*      */         } 
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
/* 3622 */     this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT", "CHARGEFEE" };
/* 3623 */     this.strFilterValue = new String[] { "Hardware", "FeatureCode", "Yes" };
/* 3624 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 3625 */     logMessage("_057***********");
/* 3626 */     displayContents(this.vReturnEntities1);
/* 3627 */     this.strCondition2 = "";
/* 3628 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3629 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/*      */       
/* 3631 */       if (this.hNoDupeLines.contains(this.eiOrderOF.getKey())) {
/* 3632 */         logMessage("New Priced Features: skipping previously announced offering :" + this.eiOrderOF.getKey());
/*      */       } else {
/*      */         
/* 3635 */         this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 3636 */         this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 3637 */         this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 3638 */         this.strCondition1 = getGeoTags(this.eiAvail);
/*      */         
/* 3640 */         if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && 
/* 3641 */           !this.strCondition1.equals(this.strCondition2)) {
/* 3642 */           if (this.strCondition2.trim().length() > 0) {
/* 3643 */             this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 3644 */             this.vPrintDetails.add(" ");
/* 3645 */             this.vPrintDetails.add(" ");
/* 3646 */             this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", " ") + "999999999999999");
/* 3647 */             this.vPrintDetails.add(" ");
/* 3648 */             this.vPrintDetails.add(" ");
/* 3649 */             this.vPrintDetails.add(" ");
/*      */           } 
/* 3651 */           this.vPrintDetails.add("$$BREAKHERE$$:p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 3652 */           this.vPrintDetails.add(" ");
/* 3653 */           this.vPrintDetails.add(" ");
/* 3654 */           this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", " "));
/* 3655 */           this.vPrintDetails.add(" ");
/* 3656 */           this.vPrintDetails.add(" ");
/* 3657 */           this.vPrintDetails.add(" ");
/* 3658 */           this.strCondition2 = this.strCondition1;
/*      */         } 
/*      */         
/* 3661 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "INVNAME", " "));
/* 3662 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", " "));
/* 3663 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MODEL", " "));
/* 3664 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", " "));
/* 3665 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "ORDERCODE", " "));
/* 3666 */         this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 3667 */         this.vPrintDetails.add(this.strCondition1.equals("CIF") ? "Y" : "N");
/* 3668 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " "));
/*      */       } 
/* 3670 */     }  if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && 
/* 3671 */       this.strCondition2.trim().length() > 0) {
/* 3672 */       this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 3673 */       this.vPrintDetails.add(" ");
/* 3674 */       this.vPrintDetails.add(" ");
/* 3675 */       this.vPrintDetails.add("999999999999999");
/* 3676 */       this.vPrintDetails.add(" ");
/* 3677 */       this.vPrintDetails.add(" ");
/* 3678 */       this.vPrintDetails.add(" ");
/*      */     } 
/*      */ 
/*      */     
/* 3682 */     if (this.vPrintDetails.size() > 0) {
/* 3683 */       println(":h3.New Priced Features");
/* 3684 */       println(":xmp.");
/* 3685 */       println(".kp off");
/* 3686 */       println("                                                     Order     Parts");
/*      */     } 
/* 3688 */     this.strHeader = new String[] { "Description", "Type", "Model", "Feature", "Code", "CSU", "Return" };
/* 3689 */     this.iColWidths = new int[] { 28, 9, 5, 7, 5, 3, 6 };
/* 3690 */     this.iSortCols = new int[] { 3 };
/* 3691 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails, this.iSortCols);
/* 3692 */     if (this.vPrintDetails.size() > 0) {
/* 3693 */       println("Order Codes:");
/* 3694 */       println("");
/* 3695 */       println("         B - Available on both initial and field upgrade orders");
/* 3696 */       println("         M - Available on field upgrade (MES) orders only");
/* 3697 */       println("         P - Available on initial orders from the plant only");
/* 3698 */       println("         S - Supported for migration only and cannot be ordered");
/* 3699 */       println("");
/* 3700 */       println(":exmp.");
/* 3701 */       println(":p.:hp2.US, LA, CAN--->:ehp2.");
/* 3702 */       println(":p.:hp2.Feature Removal Prices::ehp2.  Feature removals not associated");
/* 3703 */       println("with MES upgrades are available for a charge.");
/* 3704 */       println(".br;:hp2.<---US, LA, CAN:ehp2.");
/* 3705 */       println(":p.");
/*      */     } 
/* 3707 */     resetPrintvars();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3712 */     this.strHeader = new String[] { "Description", "Type", "Model", "Feature", "Code", "CSU", "Return" };
/* 3713 */     this.iColWidths = new int[] { 28, 9, 5, 7, 5, 3, 6 };
/*      */ 
/*      */ 
/*      */     
/* 3717 */     this.strCondition2 = "";
/* 3718 */     for (this.i = 0; this.i < this.vReturnEntities4.size(); this.i++) {
/* 3719 */       this.eiOrderOF = this.vReturnEntities4.elementAt(this.i);
/* 3720 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 3721 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 3722 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 3723 */       this.strCondition1 = getGeoTags(this.eiAvail);
/*      */       
/* 3725 */       if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && 
/* 3726 */         !this.strCondition1.equals(this.strCondition2)) {
/* 3727 */         if (this.strCondition2.trim().length() > 0) {
/* 3728 */           this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 3729 */           this.vPrintDetails.add(" ");
/* 3730 */           this.vPrintDetails.add(" ");
/* 3731 */           this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", " ") + "999999999999999");
/* 3732 */           this.vPrintDetails.add(" ");
/* 3733 */           this.vPrintDetails.add(" ");
/* 3734 */           this.vPrintDetails.add(" ");
/*      */         } 
/* 3736 */         this.vPrintDetails.add("$$BREAKHERE$$:p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 3737 */         this.vPrintDetails.add(" ");
/* 3738 */         this.vPrintDetails.add(" ");
/* 3739 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", " "));
/* 3740 */         this.vPrintDetails.add(" ");
/* 3741 */         this.vPrintDetails.add(" ");
/* 3742 */         this.vPrintDetails.add(" ");
/* 3743 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/*      */       
/* 3746 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "INVNAME", " "));
/* 3747 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", " "));
/* 3748 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MODEL", " "));
/* 3749 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", " "));
/* 3750 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "ORDERCODE", " "));
/* 3751 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 3752 */       this.vPrintDetails.add(this.strCondition1.equals("CIF") ? "Y" : "N");
/* 3753 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " "));
/*      */     } 
/* 3755 */     if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 3756 */       this.strCondition2.trim().length() > 0) {
/* 3757 */       this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 3758 */       this.vPrintDetails.add(" ");
/* 3759 */       this.vPrintDetails.add(" ");
/* 3760 */       this.vPrintDetails.add("999999999999999");
/* 3761 */       this.vPrintDetails.add(" ");
/* 3762 */       this.vPrintDetails.add(" ");
/* 3763 */       this.vPrintDetails.add(" ");
/*      */     } 
/*      */     
/* 3766 */     if (this.vPrintDetails.size() > 0) {
/* 3767 */       println(":h3.Existing Priced Features");
/* 3768 */       println(":xmp.");
/* 3769 */       println(".kp off");
/* 3770 */       println("                                                     Order     Parts");
/*      */     } 
/* 3772 */     this.iSortCols = new int[] { 3 };
/* 3773 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails, this.iSortCols);
/* 3774 */     if (this.vPrintDetails.size() > 0) {
/* 3775 */       println("Order Codes:");
/* 3776 */       println("");
/* 3777 */       println("         B - Available on both initial and field upgrade orders");
/* 3778 */       println("         M - Available on field upgrade (MES) orders only");
/* 3779 */       println("         P - Available on initial orders from the plant only");
/* 3780 */       println("         S - Supported for migration only and cannot be ordered");
/* 3781 */       println("");
/* 3782 */       println(":exmp.");
/* 3783 */       resetPrintvars();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3791 */     this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT", "CHARGEFEE" };
/* 3792 */     this.strFilterValue = new String[] { "Hardware", "FeatureCode", "No" };
/* 3793 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 3794 */     logMessage("_057 No Charge Orderofferings");
/* 3795 */     displayContents(this.vReturnEntities1);
/*      */     
/* 3797 */     this.strCondition2 = "";
/* 3798 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3799 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 3800 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 3801 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 3802 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 3803 */       this.strCondition1 = getGeoTags(this.eiAvail);
/*      */       
/* 3805 */       if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && 
/* 3806 */         !this.strCondition1.equals(this.strCondition2)) {
/* 3807 */         if (this.strCondition2.trim().length() > 0) {
/* 3808 */           this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 3809 */           this.vPrintDetails.add(" ");
/* 3810 */           this.vPrintDetails.add(" ");
/* 3811 */           this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", " ") + "999999999999999");
/* 3812 */           this.vPrintDetails.add(" ");
/* 3813 */           this.vPrintDetails.add(" ");
/* 3814 */           this.vPrintDetails.add(" ");
/*      */         } 
/* 3816 */         this.vPrintDetails.add("$$BREAKHERE$$:p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 3817 */         this.vPrintDetails.add(" ");
/* 3818 */         this.vPrintDetails.add(" ");
/* 3819 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", " "));
/* 3820 */         this.vPrintDetails.add(" ");
/* 3821 */         this.vPrintDetails.add(" ");
/* 3822 */         this.vPrintDetails.add(" ");
/* 3823 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/*      */       
/* 3826 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "INVNAME", " "));
/* 3827 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", " "));
/* 3828 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MODEL", " "));
/* 3829 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", " "));
/* 3830 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "ORDERCODE", " "));
/* 3831 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 3832 */       this.vPrintDetails.add(this.strCondition1.equals("CIF") ? "Y" : "N");
/* 3833 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " "));
/*      */     } 
/* 3835 */     if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 3836 */       this.strCondition2.trim().length() > 0) {
/* 3837 */       this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 3838 */       this.vPrintDetails.add(" ");
/* 3839 */       this.vPrintDetails.add(" ");
/* 3840 */       this.vPrintDetails.add("999999999999999");
/* 3841 */       this.vPrintDetails.add(" ");
/* 3842 */       this.vPrintDetails.add(" ");
/* 3843 */       this.vPrintDetails.add(" ");
/*      */     } 
/*      */     
/* 3846 */     if (this.vPrintDetails.size() > 0) {
/* 3847 */       println(":h3.New No-Charge Specify Codes");
/* 3848 */       println(":xmp.");
/* 3849 */       println(".kp off");
/* 3850 */       println("                                                     Order     Parts");
/*      */     } 
/* 3852 */     this.iSortCols = new int[] { 3 };
/* 3853 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails, this.iSortCols);
/* 3854 */     if (this.vPrintDetails.size() > 0) {
/* 3855 */       println("Order Codes:");
/* 3856 */       println("");
/* 3857 */       println("         B - Available on both initial and field upgrade orders");
/* 3858 */       println("         M - Available on field upgrade (MES) orders only");
/* 3859 */       println("         P - Available on initial orders from the plant only");
/* 3860 */       println("         S - Supported for migration only and cannot be ordered");
/* 3861 */       println("");
/* 3862 */       println(":exmp.");
/*      */     } 
/* 3864 */     resetPrintvars();
/* 3865 */     println(".*$A_057_End");
/*      */     
/* 3867 */     println(".*$A_058_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3873 */     this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT" };
/* 3874 */     this.strFilterValue = new String[] { "Hardware", "ModelConvert" };
/*      */     
/* 3876 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/*      */     
/* 3878 */     logMessage("Q58:Machine Type Conversions");
/* 3879 */     displayContents(this.vReturnEntities1);
/*      */     
/* 3881 */     this.i = 0;
/* 3882 */     if (this.vReturnEntities1.size() > 0) {
/* 3883 */       println(":h3.Machine Type Conversions");
/* 3884 */       println(":p.");
/* 3885 */       println(":xmp.");
/* 3886 */       println(".kp off");
/* 3887 */       println("");
/*      */       
/* 3889 */       println("    From          To                                     Rtn  Cont");
/* 3890 */       println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
/* 3891 */       println("------------- ------------- ---------------------------- ---- ---- ---");
/*      */     } 
/*      */     
/* 3894 */     this.strCondition2 = "";
/* 3895 */     this.hNoDupeLines = new Hashtable<>();
/* 3896 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3897 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 3898 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 3899 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 3900 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 3901 */       this.strCondition1 = getGeoTags(this.eiAvail);
/*      */       
/* 3903 */       if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && 
/* 3904 */         !this.strCondition1.equals(this.strCondition2)) {
/* 3905 */         if (this.strCondition2.trim().length() > 0) {
/* 3906 */           this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 3907 */           this.vPrintDetails.add(" ");
/* 3908 */           this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", " ") + "999999999999999");
/* 3909 */           this.vPrintDetails.add(" ");
/* 3910 */           this.vPrintDetails.add(" ");
/* 3911 */           this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", "    "));
/* 3912 */           this.vPrintDetails.add(" ");
/* 3913 */           this.vPrintDetails.add(" ");
/* 3914 */           this.vPrintDetails.add(" ");
/* 3915 */           this.vPrintDetails.add(" ");
/*      */         } 
/* 3917 */         this.vPrintDetails.add("$$BREAKHERE$$:p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 3918 */         this.vPrintDetails.add(" ");
/* 3919 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", " "));
/* 3920 */         this.vPrintDetails.add(" ");
/* 3921 */         this.vPrintDetails.add(" ");
/* 3922 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", "    "));
/* 3923 */         this.vPrintDetails.add(" ");
/* 3924 */         this.vPrintDetails.add(" ");
/* 3925 */         this.vPrintDetails.add(" ");
/* 3926 */         this.vPrintDetails.add(" ");
/* 3927 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/*      */       
/* 3930 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMMACHTYPE", "    "));
/* 3931 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMMODEL", "   "));
/* 3932 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", "    "));
/*      */       
/* 3934 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", "    "));
/* 3935 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MODEL", "   "));
/* 3936 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", "    "));
/*      */ 
/*      */       
/* 3939 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INVNAME", "");
/* 3940 */       this.vPrintDetails.add(this.strCondition1);
/*      */ 
/*      */       
/* 3943 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " ");
/* 3944 */       this.strCondition4 = this.strCondition1.equals("Yes") ? "  Y " : "  N ";
/* 3945 */       this.vPrintDetails.add(this.strCondition4);
/*      */       
/* 3947 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "CONTMAINTENANCE", " ");
/* 3948 */       this.strCondition4 = this.strCondition1.equals("Yes") ? "  Y " : "  N ";
/* 3949 */       this.vPrintDetails.add(this.strCondition4);
/*      */       
/* 3951 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 3952 */       this.strCondition4 = this.strCondition1.equals("CIF") ? " Y" : " N";
/* 3953 */       this.vPrintDetails.add(this.strCondition4);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3961 */     if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 3962 */       this.strCondition2.trim().length() > 0) {
/* 3963 */       this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 3964 */       this.vPrintDetails.add(" ");
/* 3965 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", " ") + "999999999999999");
/* 3966 */       this.vPrintDetails.add(" ");
/* 3967 */       this.vPrintDetails.add(" ");
/* 3968 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", "    "));
/* 3969 */       this.vPrintDetails.add(" ");
/* 3970 */       this.vPrintDetails.add(" ");
/* 3971 */       this.vPrintDetails.add(" ");
/* 3972 */       this.vPrintDetails.add(" ");
/*      */     } 
/*      */     
/* 3975 */     this.iColWidths = new int[] { 4, 3, 4, 4, 3, 4, 28, 4, 4, 3 };
/* 3976 */     this.iSortCols = new int[] { 2, 5 };
/* 3977 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails, this.iSortCols);
/* 3978 */     resetPrintvars();
/*      */ 
/*      */     
/* 3981 */     if (this.vPrintDetails.size() > 0) {
/* 3982 */       println(":exmp.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3988 */     this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT" };
/* 3989 */     this.strFilterValue = new String[] { "Hardware", "ModelUpgrade" };
/*      */     
/* 3991 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/*      */     
/* 3993 */     logMessage("Q58:Model Upgrades");
/* 3994 */     displayContents(this.vReturnEntities1);
/*      */     
/* 3996 */     this.i = 0;
/* 3997 */     if (this.vReturnEntities1.size() > 0) {
/* 3998 */       println(":h3.Model Upgrades");
/* 3999 */       println(":p.");
/* 4000 */       println(":xmp.");
/* 4001 */       println(".kp off");
/* 4002 */       println("");
/*      */       
/* 4004 */       println("    From          To                                     Rtn  Cont");
/* 4005 */       println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
/* 4006 */       println("------------- ------------- ---------------------------- ---- ---- ---");
/*      */     } 
/*      */     
/* 4009 */     this.strCondition2 = "";
/* 4010 */     this.hNoDupeLines = new Hashtable<>();
/* 4011 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4012 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 4013 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 4014 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 4015 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 4016 */       this.strCondition1 = getGeoTags(this.eiAvail);
/*      */       
/* 4018 */       if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && 
/* 4019 */         !this.strCondition1.equals(this.strCondition2)) {
/* 4020 */         if (this.strCondition2.trim().length() > 0) {
/* 4021 */           this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 4022 */           this.vPrintDetails.add(" ");
/* 4023 */           this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", " ") + "999999999999999");
/* 4024 */           this.vPrintDetails.add(" ");
/* 4025 */           this.vPrintDetails.add(" ");
/* 4026 */           this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", "    "));
/* 4027 */           this.vPrintDetails.add(" ");
/* 4028 */           this.vPrintDetails.add(" ");
/* 4029 */           this.vPrintDetails.add(" ");
/* 4030 */           this.vPrintDetails.add(" ");
/*      */         } 
/* 4032 */         this.vPrintDetails.add("$$BREAKHERE$$:p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4033 */         this.vPrintDetails.add(" ");
/* 4034 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", " "));
/* 4035 */         this.vPrintDetails.add(" ");
/* 4036 */         this.vPrintDetails.add(" ");
/* 4037 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", "    "));
/* 4038 */         this.vPrintDetails.add(" ");
/* 4039 */         this.vPrintDetails.add(" ");
/* 4040 */         this.vPrintDetails.add(" ");
/* 4041 */         this.vPrintDetails.add(" ");
/* 4042 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/*      */       
/* 4045 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMMACHTYPE", "    "));
/* 4046 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMMODEL", "   "));
/* 4047 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", "    "));
/*      */       
/* 4049 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", "    "));
/* 4050 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MODEL", "   "));
/* 4051 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", "    "));
/*      */ 
/*      */       
/* 4054 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INVNAME", "");
/* 4055 */       this.vPrintDetails.add(this.strCondition1);
/*      */ 
/*      */       
/* 4058 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " ");
/* 4059 */       this.strCondition4 = this.strCondition1.equals("Yes") ? "  Y " : "  N ";
/* 4060 */       this.vPrintDetails.add(this.strCondition4);
/*      */       
/* 4062 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "CONTMAINTENANCE", " ");
/* 4063 */       this.strCondition4 = this.strCondition1.equals("Yes") ? "  Y " : "  N ";
/* 4064 */       this.vPrintDetails.add(this.strCondition4);
/*      */       
/* 4066 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 4067 */       this.strCondition4 = this.strCondition1.equals("CIF") ? " Y" : " N";
/* 4068 */       this.vPrintDetails.add(this.strCondition4);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4076 */     if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 4077 */       this.strCondition2.trim().length() > 0) {
/* 4078 */       this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 4079 */       this.vPrintDetails.add(" ");
/* 4080 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", " ") + "999999999999999");
/* 4081 */       this.vPrintDetails.add(" ");
/* 4082 */       this.vPrintDetails.add(" ");
/* 4083 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", "    "));
/* 4084 */       this.vPrintDetails.add(" ");
/* 4085 */       this.vPrintDetails.add(" ");
/* 4086 */       this.vPrintDetails.add(" ");
/* 4087 */       this.vPrintDetails.add(" ");
/*      */     } 
/*      */     
/* 4090 */     this.iColWidths = new int[] { 4, 3, 4, 4, 3, 4, 28, 4, 4, 3 };
/* 4091 */     this.iSortCols = new int[] { 2, 5 };
/* 4092 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails, this.iSortCols);
/*      */     
/* 4094 */     if (this.vPrintDetails.size() > 0) {
/* 4095 */       println(":exmp.");
/*      */     }
/* 4097 */     resetPrintvars();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4106 */     this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT" };
/* 4107 */     this.strFilterValue = new String[] { "Hardware", "ModelConvert" };
/*      */     
/* 4109 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 4110 */     displayContents(this.vReturnEntities1);
/* 4111 */     if (this.vReturnEntities1.size() > 0) {
/* 4112 */       println(":h3.Model Conversions:");
/* 4113 */       println(":p.");
/* 4114 */       println(":xmp.");
/* 4115 */       println(".kp off");
/*      */       
/* 4117 */       println("      From   To      Return  Continuous");
/* 4118 */       println("Type  Model  Model   Parts   Maintenance  CSU");
/* 4119 */       println("----  -----  -----   ------  -----------  ---");
/*      */     } 
/*      */     
/* 4122 */     this.strCondition2 = "";
/* 4123 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4124 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 4125 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 4126 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 4127 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 4128 */       this.strCondition1 = getGeoTags(this.eiAvail);
/* 4129 */       if (!this.strCondition1.equals(this.strCondition2)) {
/* 4130 */         if (this.strCondition2.trim().length() > 0) {
/* 4131 */           println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */         }
/* 4133 */         println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4134 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/* 4136 */       String str = getAttributeValue(this.eiOrderOF, "FROMMACHTYPE", "    ");
/* 4137 */       print(str + "  ");
/*      */       
/* 4139 */       print(str + "    ");
/*      */       
/* 4141 */       print(getAttributeValue(this.eiOrderOF, "MODEL", "   "));
/* 4142 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " ");
/* 4143 */       print("      " + (this.strCondition1.equals("Yes") ? "Y" : "N"));
/* 4144 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "CONTMAINTENANCE", " ");
/* 4145 */       print("          " + (this.strCondition1.equals("Yes") ? "Y" : "N"));
/* 4146 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 4147 */       println("        " + (this.strCondition1.equals("CIF") ? "Y" : "N"));
/*      */     } 
/* 4149 */     if (this.strCondition2.trim().length() > 0) {
/* 4150 */       println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */     }
/*      */     
/* 4153 */     if (this.vReturnEntities1.size() > 0) {
/* 4154 */       println(":exmp.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4163 */     println(":h3.Feature Conversions");
/* 4164 */     println(":p.");
/*      */     
/* 4166 */     String[] arrayOfString1 = { "OOFCAT", "OOFSUBCAT", "OOFGRP" };
/* 4167 */     String[] arrayOfString2 = { "Hardware", "FeatureConvert", "Processor" };
/*      */     
/* 4169 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4170 */     displayContents(this.vReturnEntities1);
/* 4171 */     if (this.vReturnEntities1.size() > 0) {
/* 4172 */       println(":h4.Processor to processor feature conversions:");
/* 4173 */       println(":xmp.");
/* 4174 */       println(".kp off");
/* 4175 */       println("");
/* 4176 */       println("    From          To                                     Rtn  Cont");
/* 4177 */       println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
/* 4178 */       println("------------- ------------- ---------------------------- ---- ---- ---");
/*      */     } 
/*      */ 
/*      */     
/* 4182 */     this.strCondition2 = "";
/* 4183 */     this.hNoDupeLines = new Hashtable<>();
/* 4184 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4185 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 4186 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 4187 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 4188 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 4189 */       this.strCondition1 = getGeoTags(this.eiAvail);
/*      */       
/* 4191 */       if (!this.strCondition1.equals(this.strCondition2)) {
/* 4192 */         if (this.strCondition2.trim().length() > 0) {
/* 4193 */           println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */         }
/* 4195 */         println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4196 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/*      */       
/* 4199 */       this.strCondition4 = getAttributeValue(this.eiOrderOF, "FROMMACHTYPE", "    ");
/* 4200 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMMODEL", "   ");
/* 4201 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", "    ");
/* 4202 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MACHTYPE", "    ");
/* 4203 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MODEL", "   ");
/* 4204 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FEATURECODE", "    ");
/*      */       
/* 4206 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INVNAME", "");
/* 4207 */       this.strCondition4 += " " + this.strCondition1 + this.m_strSpaces.substring(0, 28 - ((this.strCondition1.length() > 28) ? 28 : this.strCondition1.length()));
/*      */       
/* 4209 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " ");
/* 4210 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4212 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "CONTMAINTENANCE", " ");
/* 4213 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4215 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 4216 */       this.strCondition4 += " " + (this.strCondition1.equals("CIF") ? " Y" : " N");
/* 4217 */       if (!this.hNoDupeLines.contains(this.strCondition4)) {
/* 4218 */         println(this.strCondition4);
/* 4219 */         this.hNoDupeLines.put(this.strCondition4, "found");
/*      */       } 
/*      */     } 
/*      */     
/* 4223 */     if (this.strCondition2.trim().length() > 0) {
/* 4224 */       println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */     }
/* 4226 */     if (this.vReturnEntities1.size() > 0) {
/* 4227 */       println(":exmp.");
/*      */     }
/*      */ 
/*      */     
/* 4231 */     arrayOfString1 = new String[] { "OOFCAT", "OOFSUBCAT", "OOFGRP" };
/* 4232 */     arrayOfString2 = new String[] { "Hardware", "FeatureConvert", "ICard" };
/*      */     
/* 4234 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4235 */     displayContents(this.vReturnEntities1);
/* 4236 */     if (this.vReturnEntities1.size() > 0) {
/* 4237 */       println(":h4.Interactive feature conversions:");
/* 4238 */       println(":xmp.");
/* 4239 */       println(".kp off");
/* 4240 */       println("");
/* 4241 */       println("    From          To                                     Rtn  Cont");
/* 4242 */       println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
/* 4243 */       println("------------- ------------- ---------------------------- ---- ---- ---");
/*      */     } 
/* 4245 */     this.strCondition2 = "";
/* 4246 */     this.hNoDupeLines = new Hashtable<>();
/* 4247 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4248 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 4249 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 4250 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 4251 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 4252 */       this.strCondition1 = getGeoTags(this.eiAvail);
/*      */       
/* 4254 */       if (!this.strCondition1.equals(this.strCondition2)) {
/* 4255 */         if (this.strCondition2.trim().length() > 0) {
/* 4256 */           println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */         }
/* 4258 */         println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4259 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/*      */       
/* 4262 */       this.strCondition4 = getAttributeValue(this.eiOrderOF, "FROMMACHTYPE", "    ");
/* 4263 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMMODEL", "   ");
/* 4264 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", "    ");
/* 4265 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MACHTYPE", "    ");
/* 4266 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MODEL", "   ");
/* 4267 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FEATURECODE", "    ");
/*      */       
/* 4269 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INVNAME", "");
/* 4270 */       this.strCondition4 += " " + this.strCondition1 + this.m_strSpaces.substring(0, 28 - ((this.strCondition1.length() > 28) ? 28 : this.strCondition1.length()));
/*      */       
/* 4272 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " ");
/* 4273 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4275 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "CONTMAINTENANCE", " ");
/* 4276 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4278 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 4279 */       this.strCondition4 += " " + (this.strCondition1.equals("CIF") ? " Y" : " N");
/* 4280 */       if (!this.hNoDupeLines.contains(this.strCondition4)) {
/* 4281 */         println(this.strCondition4);
/* 4282 */         this.hNoDupeLines.put(this.strCondition4, "found");
/*      */       } 
/*      */     } 
/*      */     
/* 4286 */     if (this.strCondition2.trim().length() > 0) {
/* 4287 */       println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */     }
/* 4289 */     resetPrintvars();
/* 4290 */     if (this.vReturnEntities1.size() > 0) {
/* 4291 */       println(":exmp.");
/*      */     }
/*      */ 
/*      */     
/* 4295 */     arrayOfString1 = new String[] { "OOFCAT", "OOFSUBCAT", "OOFGRP" };
/* 4296 */     arrayOfString2 = new String[] { "Hardware", "FeatureConvert", "Package" };
/*      */     
/* 4298 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4299 */     displayContents(this.vReturnEntities1);
/* 4300 */     if (this.vReturnEntities1.size() > 0) {
/* 4301 */       println(":h4.Package feature conversions:");
/* 4302 */       println(":xmp.");
/* 4303 */       println(".kp off");
/* 4304 */       println("");
/* 4305 */       println("    From          To                                     Rtn  Cont");
/* 4306 */       println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
/* 4307 */       println("------------- ------------- ---------------------------- ---- ---- ---");
/*      */     } 
/* 4309 */     this.strCondition2 = "";
/* 4310 */     this.hNoDupeLines = new Hashtable<>();
/* 4311 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4312 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 4313 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 4314 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 4315 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 4316 */       this.strCondition1 = getGeoTags(this.eiAvail);
/*      */       
/* 4318 */       if (!this.strCondition1.equals(this.strCondition2)) {
/* 4319 */         if (this.strCondition2.trim().length() > 0) {
/* 4320 */           println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */         }
/* 4322 */         println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4323 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/* 4325 */       this.strCondition4 = getAttributeValue(this.eiOrderOF, "FROMMACHTYPE", "    ");
/* 4326 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMMODEL", "   ");
/* 4327 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", "    ");
/* 4328 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MACHTYPE", "    ");
/* 4329 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MODEL", "   ");
/* 4330 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FEATURECODE", "    ");
/*      */       
/* 4332 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INVNAME", "");
/* 4333 */       this.strCondition4 += " " + this.strCondition1 + this.m_strSpaces.substring(0, 28 - ((this.strCondition1.length() > 28) ? 28 : this.strCondition1.length()));
/*      */       
/* 4335 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " ");
/* 4336 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4338 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "CONTMAINTENANCE", " ");
/* 4339 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4341 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 4342 */       this.strCondition4 += " " + (this.strCondition1.equals("CIF") ? " Y" : " N");
/* 4343 */       if (!this.hNoDupeLines.contains(this.strCondition4)) {
/* 4344 */         println(this.strCondition4);
/* 4345 */         this.hNoDupeLines.put(this.strCondition4, "found");
/*      */       } 
/*      */     } 
/* 4348 */     if (this.strCondition2.trim().length() > 0) {
/* 4349 */       println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */     }
/* 4351 */     resetPrintvars();
/* 4352 */     if (this.vReturnEntities1.size() > 0) {
/* 4353 */       println(":exmp.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4361 */     arrayOfString1 = new String[] { "OOFCAT", "OOFSUBCAT", "OOFGRP" };
/* 4362 */     arrayOfString2 = new String[] { "Hardware", "FeatureConvert", "Memory" };
/*      */     
/* 4364 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4365 */     displayContents(this.vReturnEntities1);
/* 4366 */     if (this.vReturnEntities1.size() > 0) {
/* 4367 */       println(":h4.Main Storage feature conversions:");
/* 4368 */       println(":xmp.");
/* 4369 */       println(".kp off");
/* 4370 */       println("");
/* 4371 */       println("    From          To                                     Rtn  Cont");
/* 4372 */       println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
/* 4373 */       println("------------- ------------- ---------------------------- ---- ---- ---");
/*      */     } 
/* 4375 */     this.strCondition2 = "";
/* 4376 */     this.hNoDupeLines = new Hashtable<>();
/* 4377 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4378 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 4379 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 4380 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 4381 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 4382 */       this.strCondition1 = getGeoTags(this.eiAvail);
/*      */       
/* 4384 */       if (!this.strCondition1.equals(this.strCondition2)) {
/* 4385 */         if (this.strCondition2.trim().length() > 0) {
/* 4386 */           println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */         }
/* 4388 */         println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4389 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/* 4391 */       this.strCondition4 = getAttributeValue(this.eiOrderOF, "FROMMACHTYPE", "    ");
/* 4392 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMMODEL", "   ");
/* 4393 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", "    ");
/* 4394 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MACHTYPE", "    ");
/* 4395 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MODEL", "   ");
/* 4396 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FEATURECODE", "    ");
/*      */       
/* 4398 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INVNAME", "");
/* 4399 */       this.strCondition4 += " " + this.strCondition1 + this.m_strSpaces.substring(0, 28 - ((this.strCondition1.length() > 28) ? 28 : this.strCondition1.length()));
/*      */       
/* 4401 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " ");
/* 4402 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4404 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "CONTMAINTENANCE", " ");
/* 4405 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4407 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 4408 */       this.strCondition4 += " " + (this.strCondition1.equals("CIF") ? " Y" : " N");
/* 4409 */       if (!this.hNoDupeLines.contains(this.strCondition4)) {
/* 4410 */         println(this.strCondition4);
/* 4411 */         this.hNoDupeLines.put(this.strCondition4, "found");
/*      */       } 
/*      */     } 
/* 4414 */     if (this.strCondition2.trim().length() > 0) {
/* 4415 */       println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */     }
/* 4417 */     resetPrintvars();
/* 4418 */     if (this.vReturnEntities1.size() > 0) {
/* 4419 */       println(":exmp.");
/*      */     }
/*      */ 
/*      */     
/* 4423 */     arrayOfString1 = new String[] { "OOFCAT", "OOFSUBCAT", "OOFGRP" };
/* 4424 */     arrayOfString2 = new String[] { "Hardware", "FeatureConvert", "Media" };
/*      */     
/* 4426 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4427 */     displayContents(this.vReturnEntities1);
/* 4428 */     if (this.vReturnEntities1.size() > 0) {
/* 4429 */       println(":h4.Disk/Tape/CD-ROM feature conversions:");
/* 4430 */       println(":xmp.");
/* 4431 */       println(".kp off");
/* 4432 */       println("");
/* 4433 */       println("    From          To                                     Rtn  Cont");
/* 4434 */       println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
/* 4435 */       println("------------- ------------- ---------------------------- ---- ---- ---");
/*      */     } 
/* 4437 */     this.strCondition2 = "";
/* 4438 */     this.hNoDupeLines = new Hashtable<>();
/* 4439 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4440 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 4441 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 4442 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 4443 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 4444 */       this.strCondition1 = getGeoTags(this.eiAvail);
/*      */       
/* 4446 */       if (!this.strCondition1.equals(this.strCondition2)) {
/* 4447 */         if (this.strCondition2.trim().length() > 0) {
/* 4448 */           println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */         }
/* 4450 */         println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4451 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/* 4453 */       this.strCondition4 = getAttributeValue(this.eiOrderOF, "FROMMACHTYPE", "    ");
/* 4454 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMMODEL", "   ");
/* 4455 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", "    ");
/* 4456 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MACHTYPE", "    ");
/* 4457 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MODEL", "   ");
/* 4458 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FEATURECODE", "    ");
/*      */       
/* 4460 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INVNAME", "");
/* 4461 */       this.strCondition4 += " " + this.strCondition1 + this.m_strSpaces.substring(0, 28 - ((this.strCondition1.length() > 28) ? 28 : this.strCondition1.length()));
/*      */       
/* 4463 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " ");
/* 4464 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4466 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "CONTMAINTENANCE", " ");
/* 4467 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4469 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 4470 */       this.strCondition4 += " " + (this.strCondition1.equals("CIF") ? " Y" : " N");
/* 4471 */       if (!this.hNoDupeLines.contains(this.strCondition4)) {
/* 4472 */         println(this.strCondition4);
/* 4473 */         this.hNoDupeLines.put(this.strCondition4, "found");
/*      */       } 
/*      */     } 
/* 4476 */     if (this.strCondition2.trim().length() > 0) {
/* 4477 */       println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */     }
/* 4479 */     resetPrintvars();
/* 4480 */     if (this.vReturnEntities1.size() > 0) {
/* 4481 */       println(":exmp.");
/*      */     }
/*      */ 
/*      */     
/* 4485 */     arrayOfString1 = new String[] { "OOFCAT", "OOFSUBCAT", "OOFGRP" };
/* 4486 */     arrayOfString2 = new String[] { "Hardware", "FeatureConvert", "N/A" };
/*      */     
/* 4488 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4489 */     displayContents(this.vReturnEntities1);
/* 4490 */     if (this.vReturnEntities1.size() > 0) {
/* 4491 */       println(":h4.Miscellaneous feature conversions:");
/* 4492 */       println(":xmp.");
/* 4493 */       println(".kp off");
/* 4494 */       println("");
/* 4495 */       println("    From          To                                     Rtn  Cont");
/* 4496 */       println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
/* 4497 */       println("------------- ------------- ---------------------------- ---- ---- ---");
/*      */     } 
/* 4499 */     this.strCondition2 = "";
/* 4500 */     this.hNoDupeLines = new Hashtable<>();
/* 4501 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4502 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 4503 */       this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFAVAIL");
/* 4504 */       this.eiOrderOFAvail = this.vReturnEntities3.elementAt(0);
/* 4505 */       this.eiAvail = (EntityItem)this.eiOrderOFAvail.getDownLink(0);
/* 4506 */       this.strCondition1 = getGeoTags(this.eiAvail);
/*      */       
/* 4508 */       if (!this.strCondition1.equals(this.strCondition2)) {
/* 4509 */         if (this.strCondition2.trim().length() > 0) {
/* 4510 */           println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */         }
/* 4512 */         println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4513 */         this.strCondition2 = this.strCondition1;
/*      */       } 
/* 4515 */       this.strCondition4 = getAttributeValue(this.eiOrderOF, "FROMMACHTYPE", "    ");
/* 4516 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMMODEL", "   ");
/* 4517 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", "    ");
/* 4518 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MACHTYPE", "    ");
/* 4519 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "MODEL", "   ");
/* 4520 */       this.strCondition4 += " " + getAttributeValue(this.eiOrderOF, "FEATURECODE", "    ");
/*      */       
/* 4522 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INVNAME", "");
/* 4523 */       this.strCondition4 += " " + this.strCondition1 + this.m_strSpaces.substring(0, 28 - ((this.strCondition1.length() > 28) ? 28 : this.strCondition1.length()));
/*      */       
/* 4525 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " ");
/* 4526 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4528 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "CONTMAINTENANCE", " ");
/* 4529 */       this.strCondition4 += " " + (this.strCondition1.equals("Yes") ? "  Y " : "  N ");
/*      */       
/* 4531 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 4532 */       this.strCondition4 += " " + (this.strCondition1.equals("CIF") ? " Y" : " N");
/* 4533 */       if (!this.hNoDupeLines.contains(this.strCondition4)) {
/* 4534 */         println(this.strCondition4);
/* 4535 */         this.hNoDupeLines.put(this.strCondition4, "found");
/*      */       } 
/*      */     } 
/* 4538 */     if (this.strCondition2.trim().length() > 0) {
/* 4539 */       println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */     }
/* 4541 */     resetPrintvars();
/* 4542 */     if (this.vReturnEntities1.size() > 0) {
/* 4543 */       println(":exmp.");
/*      */     }
/*      */     
/* 4546 */     println(".*$A_058_End");
/*      */     
/* 4548 */     println(".*$A_059_Begin");
/*      */     
/* 4550 */     println(".*$A_059_End");
/*      */     
/* 4552 */     println(".*$A_060_Begin");
/* 4553 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "NATLANGREGTSHW", "");
/* 4554 */     this.strCondition1 += getAttributeValue(this.eiAnnounce, "NATLANGREGTSSW", "");
/* 4555 */     println((this.strCondition1.length() > 0) ? "Yes" : "No");
/* 4556 */     println(".*$A_060_End");
/*      */     
/* 4558 */     println(".*$A_062_Begin");
/* 4559 */     println(":xmp.");
/* 4560 */     println(".kp off");
/*      */ 
/*      */ 
/*      */     
/* 4564 */     arrayOfString1 = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 4565 */     arrayOfString2 = new String[] { "Hardware", "System", "Base" };
/*      */     
/* 4567 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, arrayOfString1, arrayOfString2, true, true, "COMMERCIALOF");
/*      */     
/* 4569 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFCHANNEL");
/* 4570 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "CHANNEL");
/* 4571 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4572 */       this.eiChannel = this.vReturnEntities1.elementAt(this.i);
/* 4573 */       this.strCondition1 = getGeoTags(this.eiChannel);
/* 4574 */       if (this.strCondition1.trim().length() != 0) {
/*      */ 
/*      */         
/* 4577 */         logMessage("_062 " + this.eiChannel.getKey() + this.strCondition1);
/* 4578 */         this.vPrintDetails.add(getAttributeValue(this.eiChannel.getEntityType(), this.eiChannel.getEntityID(), "CHANNELNAME", " ") + " ");
/* 4579 */         this.vPrintDetails.add(getAttributeValue(this.eiChannel.getEntityType(), this.eiChannel.getEntityID(), "SUPPLIER", " ") + " ");
/*      */         
/* 4581 */         this.bConditionOK = false;
/* 4582 */         if (this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/* 4583 */           this.bConditionOK = true;
/*      */         }
/* 4585 */         this.vPrintDetails.add(this.bConditionOK ? "X" : " ");
/* 4586 */         this.vPrintDetails.add((this.m_geList.isRfaGeoAP(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4587 */         this.vPrintDetails.add((this.m_geList.isRfaGeoCAN(this.eiChannel) && this.bConditionOK) ? "X" : " ");
/* 4588 */         this.vPrintDetails.add((this.m_geList.isRfaGeoUS(this.eiChannel) && this.bConditionOK) ? "X" : " ");
/* 4589 */         this.vPrintDetails.add((this.m_geList.isRfaGeoEMEA(this.eiChannel) && this.bConditionOK) ? "X" : " ");
/* 4590 */         this.vPrintDetails.add((this.m_geList.isRfaGeoLA(this.eiChannel) && this.bConditionOK) ? "X" : " ");
/*      */       } 
/* 4592 */     }  this.strHeader = new String[] { "Route Description", "Supplier", "WW", "AP", "CAN", "US", "EMEA", "LA" };
/* 4593 */     this.iColWidths = new int[] { 26, 19, 2, 2, 3, 2, 4, 2 };
/* 4594 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 4595 */     println(":exmp.");
/* 4596 */     println(".*$A_062_End");
/*      */     
/* 4598 */     println(".*$A_063_Begin");
/* 4599 */     println(":xmp.");
/* 4600 */     println(".kp off");
/* 4601 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 4602 */     resetPrintvars();
/* 4603 */     println(":exmp.");
/* 4604 */     println(".*$A_063_End");
/*      */     
/* 4606 */     println(".*$A_065_Begin");
/* 4607 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "MKTGSTRATEGY", " "));
/* 4608 */     prettyPrint(this.strCondition1, 69);
/* 4609 */     println(".*$A_065_End");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4616 */     println(".*$A_066_Begin");
/* 4617 */     if (this.grpCrossSell != null) {
/* 4618 */       for (this.i = 0; this.i < this.grpCrossSell.getEntityItemCount(); this.i++) {
/* 4619 */         this.eiCrossSell = this.grpCrossSell.getEntityItem(this.i);
/* 4620 */         this.eiOrderOF = (EntityItem)this.eiCrossSell.getDownLink(0);
/* 4621 */         println(getAttributeValue(this.eiOrderOF, "DESCRIPTION", " "));
/* 4622 */         this.strCondition1 = getAttributeValue(this.eiCrossSell, "BENEFIT", " ");
/* 4623 */         if (this.strCondition1.trim().length() > 0) {
/* 4624 */           prettyPrint(transformXML(this.strCondition1), 69);
/*      */         }
/*      */       } 
/*      */     }
/* 4628 */     println(".*$A_066_End");
/*      */     
/* 4630 */     println(".*$A_067_Begin");
/*      */     
/* 4632 */     if (this.grpUpSell != null) {
/* 4633 */       for (this.i = 0; this.i < this.grpUpSell.getEntityItemCount(); this.i++) {
/* 4634 */         this.eiUpSell = this.grpUpSell.getEntityItem(this.i);
/* 4635 */         this.eiOrderOF = (EntityItem)this.eiUpSell.getDownLink(0);
/* 4636 */         println(getAttributeValue(this.eiOrderOF, "DESCRIPTION", " "));
/* 4637 */         this.strCondition1 = transformXML(getAttributeValue(this.eiUpSell, "BENEFIT", " "));
/* 4638 */         prettyPrint(this.strCondition1, 69);
/*      */       } 
/*      */     }
/* 4641 */     println(".*$A_067_End");
/*      */     
/* 4643 */     println(".*$A_071_Begin");
/* 4644 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "MKTGACTIONREQ", " "));
/* 4645 */     prettyPrint(this.strCondition1, 69);
/* 4646 */     println(".*$A_071_End");
/*      */     
/* 4648 */     println(".*$A_073_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4654 */     this.bConditionOK = false;
/*      */     
/* 4656 */     this.strCondition1 = getAttributeFlagEnabledValue(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "OFFERINGTYPES", " ");
/* 4657 */     this.bConditionOK = (this.strCondition1.indexOf("2891") > -1) ? true : this.bConditionOK;
/* 4658 */     this.bConditionOK = (this.strCondition1.indexOf("2892") > -1) ? true : this.bConditionOK;
/* 4659 */     this.bConditionOK = (this.strCondition1.indexOf("2896") > -1) ? true : this.bConditionOK;
/* 4660 */     this.bConditionOK = (this.strCondition1.indexOf("2899") > -1) ? true : this.bConditionOK;
/* 4661 */     this.bConditionOK = (this.strCondition1.indexOf("2901") > -1) ? true : this.bConditionOK;
/*      */     
/* 4663 */     println(this.bConditionOK ? "Yes" : "No");
/*      */     
/* 4665 */     println(".*$A_073_End");
/*      */     
/* 4667 */     println(".*$A_074_Begin");
/* 4668 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 4669 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5517", "", true);
/* 4670 */     this.iColWidths = new int[] { 69 };
/* 4671 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 4672 */     resetPrintvars();
/* 4673 */     println(".*$A_074_End");
/*      */     
/* 4675 */     println(".*$A_075_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4680 */     this.bConditionOK = false;
/* 4681 */     if ((this.grpCOFCrypto != null || this.grpOOFCrypto != null) && (
/* 4682 */       this.grpCOFCrypto.getEntityItemCount() > 0 || this.grpOOFCrypto.getEntityItemCount() > 0)) {
/* 4683 */       this.bConditionOK = true;
/*      */     }
/*      */     
/* 4686 */     println(this.bConditionOK ? "Yes" : "No");
/* 4687 */     println(".*$A_075_End");
/*      */     
/* 4689 */     println(".*$A_076_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4695 */     arrayOfString1 = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 4696 */     arrayOfString2 = new String[] { "Hardware", "System", "Base" };
/*      */ 
/*      */     
/* 4699 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, arrayOfString1, arrayOfString2, true, true, "COMMERCIALOF");
/* 4700 */     this.bConditionOK = false;
/* 4701 */     arrayOfString1 = new String[] { "EXPORTCLASS", "EXPORTCLASS" };
/* 4702 */     arrayOfString2 = new String[] { "1120", "1122" };
/* 4703 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4704 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 4705 */       if (foundInEntity(this.eiCommOF, arrayOfString1, arrayOfString2, false)) {
/* 4706 */         this.bConditionOK = true;
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4715 */     if (!this.bConditionOK) {
/* 4716 */       arrayOfString1 = new String[] { "OOFCAT" };
/* 4717 */       arrayOfString2 = new String[] { "Hardware" };
/* 4718 */       this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4719 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "OOFFUP");
/* 4720 */       arrayOfString1 = new String[] { "EXPORTCLASS", "EXPORTCLASS" };
/* 4721 */       arrayOfString2 = new String[] { "1120", "1122" };
/* 4722 */       this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, arrayOfString1, arrayOfString2, true, true, "FUP");
/* 4723 */       if (this.vReturnEntities1.size() > 0) {
/* 4724 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 4727 */     if (this.bConditionOK) {
/* 4728 */       println("Yes");
/*      */     } else {
/* 4730 */       println("No");
/*      */     } 
/* 4732 */     println(".*$A_076_End");
/*      */     
/* 4734 */     println(".*$A_078_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4739 */     this.bConditionOK = false;
/* 4740 */     arrayOfString1 = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 4741 */     arrayOfString2 = new String[] { "Hardware", "System", "Base" };
/* 4742 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, arrayOfString1, arrayOfString2, true, true, "COMMERCIALOF");
/* 4743 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFCRYPTO");
/* 4744 */     arrayOfString1 = new String[] { "CRYPTOSTRENGTH" };
/* 4745 */     arrayOfString2 = new String[] { "Yes" };
/* 4746 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, arrayOfString1, arrayOfString2, true, true, "CRYPTO");
/* 4747 */     if (!this.bConditionOK) {
/* 4748 */       arrayOfString1 = new String[] { "OOFCAT" };
/* 4749 */       arrayOfString2 = new String[] { "Hardware" };
/* 4750 */       this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4751 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "OOFCRYPTO");
/* 4752 */       arrayOfString1 = new String[] { "CRYPTOSTRENGTH" };
/* 4753 */       arrayOfString2 = new String[] { "Yes" };
/* 4754 */       this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, arrayOfString1, arrayOfString2, true, true, "CRYPTO");
/* 4755 */       if (this.vReturnEntities1.size() > 0) {
/* 4756 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 4759 */     if (this.bConditionOK) {
/* 4760 */       println("Yes");
/*      */     } else {
/* 4762 */       println("No");
/*      */     } 
/*      */     
/* 4765 */     println(".*$A_078_End");
/*      */     
/* 4767 */     println(".*$A_079_Begin");
/* 4768 */     this.bConditionOK = false;
/* 4769 */     arrayOfString1 = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 4770 */     arrayOfString2 = new String[] { "Hardware", "System", "Base" };
/* 4771 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, arrayOfString1, arrayOfString2, true, true, "COMMERCIALOF");
/* 4772 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFCRYPTO");
/* 4773 */     arrayOfString1 = new String[] { "CRYPTORETAIL" };
/* 4774 */     arrayOfString2 = new String[] { "Yes" };
/* 4775 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, arrayOfString1, arrayOfString2, true, true, "CRYPTO");
/* 4776 */     if (!this.bConditionOK) {
/* 4777 */       arrayOfString1 = new String[] { "OOFCAT" };
/* 4778 */       arrayOfString2 = new String[] { "Hardware" };
/* 4779 */       this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4780 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "OOFCRYPTO");
/* 4781 */       arrayOfString1 = new String[] { "CRYPTORETAIL" };
/* 4782 */       arrayOfString2 = new String[] { "Yes" };
/* 4783 */       this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, arrayOfString1, arrayOfString2, true, true, "CRYPTO");
/* 4784 */       if (this.vReturnEntities1.size() > 0) {
/* 4785 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 4788 */     if (this.bConditionOK) {
/* 4789 */       println("Yes");
/*      */     } else {
/* 4791 */       println("No");
/*      */     } 
/* 4793 */     println(".*$A_079_End");
/*      */     
/* 4795 */     println(".*$A_080_Begin");
/* 4796 */     this.bConditionOK = false;
/* 4797 */     arrayOfString1 = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 4798 */     arrayOfString2 = new String[] { "Hardware", "System", "Base" };
/* 4799 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, arrayOfString1, arrayOfString2, true, true, "COMMERCIALOF");
/* 4800 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFCRYPTO");
/* 4801 */     arrayOfString1 = new String[] { "CRYPTOFUNC" };
/* 4802 */     arrayOfString2 = new String[] { "Yes" };
/* 4803 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, arrayOfString1, arrayOfString2, true, true, "CRYPTO");
/* 4804 */     if (!this.bConditionOK) {
/* 4805 */       arrayOfString1 = new String[] { "OOFCAT" };
/* 4806 */       arrayOfString2 = new String[] { "Hardware" };
/* 4807 */       this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4808 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "OOFCRYPTO");
/* 4809 */       arrayOfString1 = new String[] { "CRYPTOFUNC" };
/* 4810 */       arrayOfString2 = new String[] { "Yes" };
/* 4811 */       this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, arrayOfString1, arrayOfString2, true, true, "CRYPTO");
/* 4812 */       if (this.vReturnEntities1.size() > 0) {
/* 4813 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 4816 */     if (this.bConditionOK) {
/* 4817 */       println("Yes");
/*      */     } else {
/* 4819 */       println("No");
/*      */     } 
/* 4821 */     println(".*$A_080_End");
/*      */     
/* 4823 */     println(".*$A_081_Begin");
/* 4824 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 4825 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5500", "", true);
/* 4826 */     this.iColWidths = new int[] { 69 };
/* 4827 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 4828 */     resetPrintvars();
/* 4829 */     println(".*$A_081_End");
/*      */     
/* 4831 */     println(".*$A_082_Begin");
/*      */     
/* 4833 */     if (this.bConditionOK) {
/* 4834 */       println("Yes");
/*      */     } else {
/* 4836 */       println("No");
/*      */     } 
/* 4838 */     println(".*$A_082_End");
/*      */     
/* 4840 */     println(".*$A_083_Begin");
/* 4841 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 4842 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5496", "", true);
/* 4843 */     this.iColWidths = new int[] { 69 };
/* 4844 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 4845 */     resetPrintvars();
/* 4846 */     println(".*$A_083_End");
/*      */     
/* 4848 */     println(".*$A_084_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4854 */     arrayOfString1 = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 4855 */     arrayOfString2 = new String[] { "Hardware", "System", "Base" };
/*      */ 
/*      */     
/* 4858 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, arrayOfString1, arrayOfString2, true, true, "COMMERCIALOF");
/* 4859 */     this.bConditionOK = false;
/* 4860 */     arrayOfString1 = new String[] { "EXPORTCLASS", "EXPORTCLASS" };
/* 4861 */     arrayOfString2 = new String[] { "1120", "1122" };
/* 4862 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4863 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 4864 */       if (foundInEntity(this.eiCommOF, arrayOfString1, arrayOfString2, false)) {
/* 4865 */         this.bConditionOK = true;
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4874 */     if (!this.bConditionOK) {
/* 4875 */       arrayOfString1 = new String[] { "OOFCAT" };
/* 4876 */       arrayOfString2 = new String[] { "Hardware" };
/* 4877 */       this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 4878 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "OOFFUP");
/* 4879 */       arrayOfString1 = new String[] { "EXPORTCLASS", "EXPORTCLASS" };
/* 4880 */       arrayOfString2 = new String[] { "1120", "1122" };
/* 4881 */       this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, arrayOfString1, arrayOfString2, true, true, "FUP");
/* 4882 */       if (this.vReturnEntities1.size() > 0) {
/* 4883 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 4886 */     if (this.bConditionOK) {
/* 4887 */       println("Yes");
/*      */     } else {
/* 4889 */       println("No");
/*      */     } 
/* 4891 */     println(".*$A_084_End");
/* 4892 */     println(".*$A_085_Begin");
/* 4893 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 4894 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5483", "", true);
/* 4895 */     this.iColWidths = new int[] { 69 };
/* 4896 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 4897 */     resetPrintvars();
/* 4898 */     println(".*$A_085_End");
/* 4899 */     println(".*$A_086_Begin");
/* 4900 */     println(".*$A_086_End");
/*      */     
/* 4902 */     println(".*$A_087_Begin");
/* 4903 */     this.bConditionOK = false;
/* 4904 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "PRODDEMOANN", "");
/* 4905 */     if (this.strCondition1.length() > 0) {
/* 4906 */       this.bConditionOK = true;
/*      */     }
/* 4908 */     if (!this.bConditionOK) {
/* 4909 */       this.strCondition1 = getAttributeValue(this.eiAnnounce, "PRODDEMOPLANAVAIL", "");
/* 4910 */       if (this.strCondition1.length() > 0) {
/* 4911 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 4914 */     if (!this.bConditionOK) {
/* 4915 */       this.strCondition1 = getAttributeValue(this.eiAnnounce, "SOLDEMOANN", "");
/* 4916 */       if (this.strCondition1.length() > 0) {
/* 4917 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 4920 */     if (!this.bConditionOK) {
/* 4921 */       this.strCondition1 = getAttributeValue(this.eiAnnounce, "SOLDEMOPLANAVAIL", "");
/* 4922 */       if (this.strCondition1.length() > 0) {
/* 4923 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/*      */     
/* 4927 */     if (!this.bConditionOK) {
/*      */ 
/*      */       
/* 4930 */       arrayOfString1 = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 4931 */       arrayOfString2 = new String[] { "Hardware", "System", "Base" };
/*      */ 
/*      */       
/* 4934 */       this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, arrayOfString1, arrayOfString2, true, true, "COMMERCIALOF");
/* 4935 */       arrayOfString1 = new String[] { "ORGANUNITROLETYPE" };
/* 4936 */       arrayOfString2 = new String[] { "4153" };
/* 4937 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, arrayOfString1, arrayOfString2, true, true, "COFORGANUNIT");
/* 4938 */       if (this.vReturnEntities2.size() > 0) {
/* 4939 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/*      */     
/* 4943 */     if (!this.bConditionOK) {
/*      */ 
/*      */       
/* 4946 */       arrayOfString1 = new String[] { "PUBTYPE" };
/* 4947 */       arrayOfString2 = new String[] { "5022" };
/* 4948 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPUBS");
/* 4949 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, arrayOfString1, arrayOfString2, true, true, "PUBLICATION");
/* 4950 */       if (this.vReturnEntities3.size() > 0) {
/* 4951 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 4956 */     println(this.bConditionOK ? "Yes" : "No");
/* 4957 */     println(".*$A_087_End");
/*      */     
/* 4959 */     println(".*$A_088_Begin");
/* 4960 */     this.bConditionOK = false;
/* 4961 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "PRODDEMOANN", "");
/* 4962 */     if (this.strCondition1.length() > 0) {
/* 4963 */       this.bConditionOK = true;
/*      */     }
/* 4965 */     if (!this.bConditionOK) {
/* 4966 */       this.strCondition1 = getAttributeValue(this.eiAnnounce, "PRODDEMOPLANAVAIL", "");
/* 4967 */       if (this.strCondition1.length() > 0) {
/* 4968 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 4971 */     if (!this.bConditionOK) {
/* 4972 */       this.strCondition1 = getAttributeValue(this.eiAnnounce, "SOLDEMOANN", "");
/* 4973 */       if (this.strCondition1.length() > 0) {
/* 4974 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 4977 */     if (!this.bConditionOK) {
/* 4978 */       this.strCondition1 = getAttributeValue(this.eiAnnounce, "SOLDEMOPLANAVAIL", "");
/* 4979 */       if (this.strCondition1.length() > 0) {
/* 4980 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 4983 */     println(this.bConditionOK ? "Yes" : "No");
/* 4984 */     println(".*$A_088_End");
/* 4985 */     println(".*$A_089_Begin");
/* 4986 */     println(":xmp.");
/* 4987 */     println(".kp off");
/* 4988 */     println("                                          Live Solution/");
/* 4989 */     println("                         Live Product     Integrated");
/* 4990 */     println("                         Demonstration    Demonstration");
/* 4991 */     println("                         -------------    --------------");
/* 4992 */     print("At announcement              ");
/* 4993 */     print(getAttributeValue(this.eiAnnounce, "PRODDEMOANN", "   "));
/* 4994 */     print("              ");
/* 4995 */     println(getAttributeValue(this.eiAnnounce, "PRODDEMOPLANAVAIL", "   "));
/* 4996 */     print("By planned availability      ");
/* 4997 */     print(getAttributeValue(this.eiAnnounce, "SOLDEMOANN", "   "));
/* 4998 */     print("              ");
/* 4999 */     println(getAttributeValue(this.eiAnnounce, "SOLDEMOPLANAVAIL", "   "));
/* 5000 */     println(":exmp.");
/* 5001 */     println(".*$A_089_End");
/*      */     
/* 5003 */     println(".*$A_090_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5008 */     this.bConditionOK = false;
/* 5009 */     arrayOfString1 = new String[] { "ORGANUNITROLETYPE" };
/* 5010 */     arrayOfString2 = new String[] { "4153" };
/* 5011 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, arrayOfString1, arrayOfString2, true, true, "COFORGANUNIT");
/* 5012 */     if (this.vReturnEntities2.size() > 0) {
/* 5013 */       this.bConditionOK = true;
/*      */     }
/* 5015 */     println(this.bConditionOK ? "Yes" : "No");
/* 5016 */     println(".*$A_090_End");
/*      */     
/* 5018 */     println(".*$A_091_Begin");
/*      */     
/* 5020 */     println(":xmp.");
/* 5021 */     println(".kp off");
/*      */ 
/*      */ 
/*      */     
/* 5025 */     logMessage("A_091");
/* 5026 */     displayContents(this.vReturnEntities2);
/* 5027 */     this.vPrintDetails1 = new Vector();
/* 5028 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 5029 */       this.eiCofOrganUnit = this.vReturnEntities2.elementAt(this.i);
/* 5030 */       this.eiOrganUnit = (EntityItem)this.eiCofOrganUnit.getDownLink(0);
/* 5031 */       for (this.k = 0; this.k < this.eiOrganUnit.getDownLinkCount(); this.k++) {
/* 5032 */         this.eiOrganUnitIndiv = (EntityItem)this.eiOrganUnit.getDownLink(this.k);
/* 5033 */         if (this.eiOrganUnitIndiv.getEntityType().equals("ORGANUNITINDIV")) {
/* 5034 */           this.eiOP = (EntityItem)this.eiOrganUnitIndiv.getDownLink(0);
/* 5035 */           this.vPrintDetails.add(getAttributeValue(this.eiCofOrganUnit, "STARTDATE", " "));
/* 5036 */           if (flagvalueEquals(this.eiOrganUnit, "ORGANUNITTYPE", "4155")) {
/* 5037 */             this.vPrintDetails.add(getAttributeValue(this.eiOrganUnit, "NAME", " "));
/* 5038 */             this.strCondition1 = getAttributeValue(this.eiOrganUnit, "CITY", " ");
/* 5039 */             this.strCondition1 += " : " + getAttributeValue(this.eiOrganUnit, "STATE", " ");
/* 5040 */             this.vPrintDetails.add(this.strCondition1);
/* 5041 */             this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ");
/* 5042 */             this.strCondition1 += " " + getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 5043 */             this.vPrintDetails1.add(this.strCondition1);
/* 5044 */             this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ");
/* 5045 */             this.strCondition1 += "/" + getAttributeValue(this.eiOP, "VNETUID", " ");
/* 5046 */             this.vPrintDetails1.add(this.strCondition1);
/*      */           } else {
/* 5048 */             this.vPrintDetails.add(" ");
/* 5049 */             this.vPrintDetails.add(" ");
/* 5050 */             this.vPrintDetails1.add(" ");
/* 5051 */             this.vPrintDetails1.add(" ");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 5056 */     this.strHeader = new String[] { "Available", "System Center Name", "Systems Center Location" };
/* 5057 */     this.iColWidths = new int[] { 10, 24, 29 };
/* 5058 */     if (this.vPrintDetails.size() > 0) {
/* 5059 */       println("   Date");
/* 5060 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5061 */       resetPrintvars();
/*      */     } 
/* 5063 */     this.strHeader = new String[] { "Contact Name", "   Node/Userid" };
/* 5064 */     this.iColWidths = new int[] { 43, 16 };
/* 5065 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails1);
/* 5066 */     this.vPrintDetails1.removeAllElements();
/* 5067 */     println(":exmp.");
/* 5068 */     println(".*$A_091_End");
/*      */     
/* 5070 */     println(".*$A_092_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5075 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPUBS");
/* 5076 */     this.strCondition2 = "";
/*      */     
/* 5078 */     if (this.grpCofPubs != null) {
/* 5079 */       for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 5080 */         this.eiCofPubs = this.vReturnEntities2.elementAt(this.i);
/* 5081 */         for (this.j = 0; this.j < this.eiCofPubs.getDownLinkCount(); this.j++) {
/* 5082 */           this.eiPublication = (EntityItem)this.eiCofPubs.getDownLink(this.j);
/* 5083 */           this.strCondition1 = getGeoTags(this.eiPublication);
/* 5084 */           if (!this.strCondition1.equals(this.strCondition2)) {
/* 5085 */             if (this.strCondition2.trim().length() > 0) {
/* 5086 */               this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 5087 */               this.vPrintDetails.add(" ");
/* 5088 */               this.vPrintDetails.add(" ");
/*      */             } 
/* 5090 */             this.vPrintDetails.add("$$BREAKHERE$$:p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 5091 */             this.vPrintDetails.add(" ");
/* 5092 */             this.vPrintDetails.add(" ");
/* 5093 */             this.strCondition2 = this.strCondition1;
/*      */           } 
/* 5095 */           this.strCondition1 = getAttributeValue(this.eiPublication, "PUBTYPE", " ");
/* 5096 */           if (this.strCondition1.equals("Promotion Material")) {
/* 5097 */             this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBTITLE", " "));
/* 5098 */             this.vPrintDetails.add(getAttributeValue(this.eiPublication, "ORDERPN", " "));
/* 5099 */             this.vPrintDetails.add(getAttributeValue(this.eiCofPubs.getEntityType(), this.eiCofPubs.getEntityID(), "PUBAVAIL", " "));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/* 5104 */     if (this.strCondition2.trim().length() > 0) {
/* 5105 */       this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 5106 */       this.vPrintDetails.add(" ");
/* 5107 */       this.vPrintDetails.add(" ");
/*      */     } 
/* 5109 */     this.strHeader = new String[] { "Title", "Order no.", "Avail" };
/* 5110 */     this.iColWidths = new int[] { 48, 12, 10 };
/* 5111 */     println("The following materials will be available from on the dates listed");
/* 5112 */     println("below.");
/* 5113 */     println(":p.:hp2.US--->:ehp2.");
/* 5114 */     println(":xmp.");
/* 5115 */     println(".kp off");
/* 5116 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5117 */     resetPrintvars();
/* 5118 */     println(":exmp.");
/* 5119 */     println(":p.");
/* 5120 */     println("Additional copies are available from the Publication Notification");
/* 5121 */     println("System (PNS) or the country publications ordering system");
/* 5122 */     println(".br;:hp2.<---US:ehp2.");
/* 5123 */     println(".*$A_092_End");
/*      */     
/* 5125 */     println(".*$A_093_Begin");
/* 5126 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5127 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5537", "", true);
/* 5128 */     this.iColWidths = new int[] { 69 };
/* 5129 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5130 */     resetPrintvars();
/* 5131 */     println(".*$A_093_End");
/*      */     
/* 5133 */     println(".*$A_094_Begin");
/* 5134 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5135 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5487", "", true);
/* 5136 */     this.iColWidths = new int[] { 69 };
/* 5137 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5138 */     resetPrintvars();
/* 5139 */     println(".*$A_094_End");
/*      */     
/* 5141 */     println(".*$A_095_Begin");
/* 5142 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5143 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5491", "", true);
/* 5144 */     this.iColWidths = new int[] { 69 };
/* 5145 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5146 */     resetPrintvars();
/* 5147 */     println(".*$A_095_End");
/*      */     
/* 5149 */     println(".*$A_096_Begin");
/* 5150 */     this.bConditionOK = true;
/* 5151 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "INSTALLSUPPORT", " ");
/* 5152 */     this.bConditionOK = !this.strCondition1.equals(" ");
/* 5153 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5154 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5512", "", true);
/* 5155 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5501", "", true);
/* 5156 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5516", "", true);
/* 5157 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5503", "", true);
/* 5158 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5511", "", true);
/* 5159 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5499", "", true);
/* 5160 */     this.bConditionOK = (this.vPrintDetails.size() > 0) ? true : this.bConditionOK;
/* 5161 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "DIRECTCUSTSUPPORT ", " ");
/* 5162 */     this.bConditionOK = (this.strCondition1.indexOf("B|C|D|E") <= 0) ? this.bConditionOK : true;
/* 5163 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5492", "", true);
/* 5164 */     this.bConditionOK = (this.vPrintDetails.size() > 0) ? true : this.bConditionOK;
/* 5165 */     println(this.bConditionOK ? "Yes" : "No");
/* 5166 */     resetPrintvars();
/* 5167 */     println(".*$A_096_End");
/*      */     
/* 5169 */     println(".*$A_097_Begin");
/* 5170 */     if (this.grpConfigurator != null) {
/* 5171 */       for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 5172 */         this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 5173 */         this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 5174 */         this.strCondition1 = getAttributeValue(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGTYPE", " ");
/* 5175 */         if (this.strCondition1.equals("Non-Standard")) {
/* 5176 */           this.strCondition1 = getAttributeValue(this.eiConfigurator.getEntityType(), this.eiConfigurator.getEntityID(), "CONFIGNAME", " ");
/* 5177 */           this.vPrintDetails.add(this.strCondition1);
/* 5178 */           this.strCondition1 = getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", " ");
/* 5179 */           this.vPrintDetails.add(this.strCondition1);
/*      */         } 
/*      */       } 
/*      */     }
/* 5183 */     this.iColWidths = new int[] { 49, 10 };
/* 5184 */     this.strHeader = new String[] { " Configurator Tool", "Date Available" };
/* 5185 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5186 */     resetPrintvars();
/* 5187 */     println(".*$A_097_End");
/*      */     
/* 5189 */     println(".*$A_099_Begin");
/* 5190 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "INSTALLSUPPORT", " "));
/* 5191 */     prettyPrint(this.strCondition1, 69);
/* 5192 */     println(".*$A_099_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo200() {
/* 5200 */     println(".*$A_110_Begin");
/* 5201 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/*      */     
/* 5203 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5512", "", true);
/* 5204 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5516", "", true);
/* 5205 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5503", "", true);
/* 5206 */     println((this.vPrintDetails.size() > 0) ? "Yes" : "No");
/* 5207 */     resetPrintvars();
/* 5208 */     println(".*$A_110_End");
/*      */     
/* 5210 */     println(".*$A_112_Begin");
/* 5211 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5212 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5512", "", true);
/* 5213 */     this.iColWidths = new int[] { 69 };
/* 5214 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5215 */     resetPrintvars();
/* 5216 */     println(".*$A_112_End");
/*      */     
/* 5218 */     println(".*$A_113_Begin");
/* 5219 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5220 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5511", "", true);
/* 5221 */     this.iColWidths = new int[] { 69 };
/* 5222 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5223 */     resetPrintvars();
/* 5224 */     println(".*$A_113_End");
/*      */     
/* 5226 */     println(".*$A_114_Begin");
/* 5227 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5228 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5499", "", true);
/* 5229 */     this.iColWidths = new int[] { 69 };
/* 5230 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5231 */     resetPrintvars();
/* 5232 */     println(".*$A_114_End");
/*      */     
/* 5234 */     println(".*$A_115_Begin");
/* 5235 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5236 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5501", "", true);
/* 5237 */     this.iColWidths = new int[] { 69 };
/* 5238 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5239 */     resetPrintvars();
/* 5240 */     println(".*$A_115_End");
/*      */     
/* 5242 */     println(".*$A_126_Begin");
/* 5243 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5244 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5492", "", true);
/* 5245 */     println((this.vPrintDetails.size() > 0) ? "Yes" : "No");
/* 5246 */     println(".*$A_126_End");
/*      */     
/* 5248 */     println(".*$A_127_Begin");
/* 5249 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5250 */     resetPrintvars();
/* 5251 */     println(".*$A_127_End");
/*      */     
/* 5253 */     println(".*$A_128_Begin");
/* 5254 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5255 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5518", "", true);
/* 5256 */     this.iColWidths = new int[] { 69 };
/* 5257 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5258 */     resetPrintvars();
/* 5259 */     println(".*$A_128_End");
/*      */     
/* 5261 */     println(".*$A_132_Begin");
/*      */ 
/*      */     
/* 5264 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 5265 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/*      */ 
/*      */     
/* 5268 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 5269 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPRICE");
/* 5270 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 5271 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 5272 */       this.eiCofPrice = this.vReturnEntities2.elementAt(this.i);
/* 5273 */       this.eiCommOF = (EntityItem)this.eiCofPrice.getUpLink(0);
/* 5274 */       this.eiPriceInfo = (EntityItem)this.eiCofPrice.getDownLink(0);
/* 5275 */       this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MACHTYPE", " "));
/* 5276 */       this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MODEL", " "));
/* 5277 */       this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "BILLINGPERIOD", " "));
/*      */     } 
/* 5279 */     this.strHeader = new String[] { "Machine", "Model", "Billing Period" };
/* 5280 */     this.iColWidths = new int[] { 7, 5, 14 };
/* 5281 */     if (this.vPrintDetails.size() > 0) {
/* 5282 */       println(":xmp.");
/* 5283 */       println(".kp off");
/* 5284 */       println("                Maintenance");
/* 5285 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5286 */       resetPrintvars();
/* 5287 */       println(":exmp.");
/*      */     } 
/* 5289 */     println(".*$A_132_End");
/*      */     
/* 5291 */     println(".*$A_136_Begin");
/* 5292 */     println(getAttributeValue(this.eiAnnounce, "IVOSPECBIDS", " "));
/* 5293 */     println(".*$A_136_End");
/*      */     
/* 5295 */     println(".*$A_137_Begin");
/*      */ 
/*      */ 
/*      */     
/* 5299 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COMMERCIALOFIVO");
/* 5300 */     if (this.vReturnEntities2 != null) {
/* 5301 */       for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 5302 */         this.eiCommOFIvo = this.vReturnEntities2.elementAt(this.i);
/* 5303 */         this.eiIvocat = (EntityItem)this.eiCommOFIvo.getDownLink(0);
/* 5304 */         this.vPrintDetails.add(" " + getAttributeValue(this.eiIvocat, "IVOEXHIBIT", " "));
/* 5305 */         this.vPrintDetails.add(getAttributeValue(this.eiIvocat, "IVONAME", " "));
/*      */       } 
/*      */     }
/*      */     
/* 5309 */     this.strHeader = new String[] { "CATEGORY", "     DESCRIPTION" };
/* 5310 */     this.iColWidths = new int[] { 11, 36 };
/* 5311 */     if (this.vPrintDetails.size() > 0) {
/* 5312 */       println(":xmp.");
/* 5313 */       println(".kp off");
/* 5314 */       println(".rc 1 on");
/* 5315 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5316 */       resetPrintvars();
/* 5317 */       println(":exmp.");
/*      */     } 
/* 5319 */     println(".*$A_137_End");
/*      */     
/* 5321 */     println(".*$A_138_Begin");
/*      */     
/* 5323 */     this.bConditionOK = true;
/* 5324 */     if (this.vReturnEntities2 != null) {
/* 5325 */       for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 5326 */         this.eiCommOFIvo = this.vReturnEntities2.elementAt(this.i);
/* 5327 */         this.eiIvocat = (EntityItem)this.eiCommOFIvo.getDownLink(0);
/* 5328 */         this.eiCommOF = (EntityItem)this.eiCommOFIvo.getUpLink(0);
/* 5329 */         if (this.bConditionOK) {
/* 5330 */           println("Option 1");
/* 5331 */           println("");
/* 5332 */           println(".sk");
/* 5333 */           print(":hp2.IVO Volume Discount:       :ehp2. ");
/* 5334 */           println(getAttributeValue(this.eiCommOFIvo, "IVOELIGBLE", " "));
/* 5335 */           println("Option 2");
/* 5336 */           println("");
/* 5337 */           println(".sk");
/* 5338 */           print(":hp2.Maximum Product Cap:       :ehp2. ");
/* 5339 */           println(getAttributeValue(this.eiCommOFIvo, "IVODISCOUNTCAP", " ") + "%");
/* 5340 */           this.bConditionOK = false;
/*      */         } 
/* 5342 */         this.vPrintDetails.add(getAttributeValue(this.eiIvocat, "IVOFORM", " "));
/* 5343 */         this.vPrintDetails.add(getAttributeValue(this.eiIvocat, "IVOEXHIBIT", " "));
/* 5344 */         this.vPrintDetails.add(getAttributeValue(this.eiIvocat, "IVONAME", " "));
/* 5345 */         this.strCondition1 = getAttributeValue(this.eiCommOF, "MACHTYPE", " ");
/* 5346 */         this.strCondition1 += getAttributeValue(this.eiCommOF, "MODEL", " ");
/* 5347 */         this.vPrintDetails.add(this.strCondition1);
/*      */       } 
/*      */     }
/* 5350 */     this.strHeader = new String[] { " FORM", "EXHIBIT", "CATEGORY / DESCRIPTION", "PRODUCT" };
/* 5351 */     this.iColWidths = new int[] { 5, 7, 35, 7 };
/* 5352 */     if (this.vPrintDetails.size() > 0) {
/* 5353 */       println(":xmp.");
/* 5354 */       println(".kp off");
/* 5355 */       println("Option 3");
/* 5356 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5357 */       resetPrintvars();
/*      */     } 
/* 5359 */     println(":exmp.");
/*      */     
/* 5361 */     println(".*$A_138_End");
/*      */     
/* 5363 */     println(".*$A_139_Begin");
/* 5364 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5365 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5525", "", true);
/* 5366 */     this.iColWidths = new int[] { 69 };
/* 5367 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5368 */     resetPrintvars();
/* 5369 */     println(".*$A_139_End");
/*      */     
/* 5371 */     println(".*$A_140_Begin");
/* 5372 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5373 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5527", "", true);
/* 5374 */     this.iColWidths = new int[] { 69 };
/* 5375 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5376 */     resetPrintvars();
/* 5377 */     println(".*$A_140_End");
/*      */     
/* 5379 */     println(".*$A_151_Begin");
/* 5380 */     println(getAttributeValue(this.eiAnnounce, "SPECIALBIDSTRANS", " "));
/* 5381 */     println(".*$A_151_End");
/*      */     
/* 5383 */     println(".*$A_176_Begin");
/* 5384 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5385 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5493", "", true);
/* 5386 */     this.iColWidths = new int[] { 69 };
/* 5387 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5388 */     resetPrintvars();
/* 5389 */     println(".*$A_176_End");
/*      */     
/* 5391 */     println(".*$A_186_Begin");
/* 5392 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5393 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5506", "", true);
/* 5394 */     this.iColWidths = new int[] { 69 };
/* 5395 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5396 */     resetPrintvars();
/* 5397 */     println(".*$A_186_End");
/*      */     
/* 5399 */     println(".*$A_190_Begin");
/* 5400 */     println(getAttributeValue(this.eiAnnounce, "MAINTPROCESS", " "));
/* 5401 */     println(".*$A_190_End");
/*      */     
/* 5403 */     println(".*$A_210_Begin");
/* 5404 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5405 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5533", "", true);
/* 5406 */     println((this.vPrintDetails.size() > 0) ? "Yes" : "No");
/* 5407 */     println(".*$A_210_End");
/*      */     
/* 5409 */     println(".*$A_214_Begin");
/* 5410 */     this.iColWidths = new int[] { 69 };
/* 5411 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5412 */     resetPrintvars();
/* 5413 */     println(".*$A_214_End");
/*      */     
/* 5415 */     println(".*$A_216_Begin");
/*      */     
/* 5417 */     println(".*$A_216_End");
/*      */     
/* 5419 */     println(".*$A_218_Begin");
/* 5420 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "SPECORDERINST", " "));
/* 5421 */     prettyPrint(this.strCondition1, 69);
/* 5422 */     println(".*$A_218_End");
/*      */     
/* 5424 */     println(".*$A_219_Begin");
/* 5425 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5426 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5521", "", true);
/* 5427 */     this.iColWidths = new int[] { 69 };
/* 5428 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5429 */     resetPrintvars();
/* 5430 */     println(".*$A_219_End");
/*      */     
/* 5432 */     println(".*$A_220_Begin");
/* 5433 */     println(getAttributeValue(this.eiAnnounce, "IBMNADISTNETWORK", " "));
/* 5434 */     println(".*$A_220_End");
/*      */     
/* 5436 */     println(".*$A_236_Begin");
/* 5437 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5438 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5519", "", true);
/* 5439 */     this.iColWidths = new int[] { 69 };
/* 5440 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5441 */     resetPrintvars();
/* 5442 */     println(".*$A_236_End");
/*      */     
/* 5444 */     println(".*$A_237_Begin");
/*      */ 
/*      */     
/* 5447 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 5448 */     logMessage("A_237");
/* 5449 */     displayContents(this.vReturnEntities1);
/*      */     
/* 5451 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 5452 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 5453 */       this.vReturnEntities2 = searchEntityItemLink(this.eiCommOF, (String[])null, (String[])null, true, true, "COFGBTA");
/* 5454 */       logMessage("A_237");
/* 5455 */       displayContents(this.vReturnEntities2);
/* 5456 */       this.eiNextItem = null;
/* 5457 */       this.eiNextItem1 = null;
/* 5458 */       if (this.vReturnEntities2.size() > 0) {
/* 5459 */         this.eiNextItem = this.vReturnEntities2.elementAt(0);
/* 5460 */         logMessage(this.eiNextItem.getEntityType() + ":" + this.eiNextItem.getEntityID());
/* 5461 */         this.eiNextItem1 = (EntityItem)this.eiNextItem.getDownLink(0);
/*      */       } 
/* 5463 */       this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MACHTYPE", " "));
/* 5464 */       this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MODEL", " "));
/* 5465 */       if (this.eiNextItem1 != null) {
/* 5466 */         logMessage(this.eiNextItem1.getEntityType() + ":" + this.eiNextItem1.getEntityID());
/* 5467 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem1, "SAPPRIMBRANDCODE", " "));
/* 5468 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem1, "SAPPRODFAMCODE", " "));
/*      */       } else {
/* 5470 */         this.vPrintDetails.add(" ");
/* 5471 */         this.vPrintDetails.add(" ");
/*      */       } 
/* 5473 */       this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "GBNAME", " "));
/*      */     } 
/* 5475 */     this.strHeader = new String[] { "Type", "Model", "Code", "Code", "Description" };
/* 5476 */     this.iColWidths = new int[] { 4, 7, 7, 7, 40 };
/*      */     
/* 5478 */     if (this.vPrintDetails.size() > 0) {
/* 5479 */       println(":xmp.");
/* 5480 */       println(".sp");
/* 5481 */       println("             Primary Product");
/* 5482 */       println("             Brand   Family");
/*      */       
/* 5484 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5485 */       resetPrintvars();
/* 5486 */       println(":exmp.");
/*      */     } 
/* 5488 */     println(".*$A_237_End");
/*      */     
/* 5490 */     println(".*$A_238_Begin");
/* 5491 */     this.strFilterAttr = new String[] { "TRANSPRINTCT" };
/* 5492 */     this.strFilterValue = new String[] { "Yes" };
/* 5493 */     this.vReturnEntities1 = searchEntityGroup(this.grpPublication, this.strFilterAttr, this.strFilterValue, true);
/* 5494 */     println((this.vReturnEntities1.size() > 0) ? "Yes" : "No");
/*      */     
/* 5496 */     println(".*$A_238_End");
/*      */     
/* 5498 */     println(".*$A_240_Begin");
/*      */     
/* 5500 */     this.strCondition1 = getAttributeFlagEnabledValue(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "PRODSTRUCTURE", " ");
/* 5501 */     println(this.strCondition1.equals("5021") ? "A" : (this.strCondition1.equals("5020") ? "B" : ""));
/* 5502 */     println(".*$A_240_End");
/*      */     
/* 5504 */     println(".*$A_245_Begin");
/* 5505 */     println(getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "BTOSTRUCTURE", " "));
/* 5506 */     println(".*$A_245_End");
/*      */     
/* 5508 */     println(".*$A_250_Begin");
/*      */     
/* 5510 */     println(".*$A_250_End");
/*      */     
/* 5512 */     println(".*$A_260_Begin");
/*      */     
/* 5514 */     println(".*$A_260_End");
/*      */     
/* 5516 */     println(".*$A_261_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5553 */     println(".*$A_267_Begin");
/*      */     
/* 5555 */     println(".*$A_267_End");
/*      */     
/* 5557 */     println(".*$A_282_Begin");
/* 5558 */     println(getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "REVENUEALLOC", " "));
/* 5559 */     println(".*$A_282_End");
/*      */     
/* 5561 */     println(".*$A_283_Begin");
/* 5562 */     println(getAttributeValue(this.eiAnnounce, "REVENUESHARE", " "));
/* 5563 */     println(".*$A_283_End");
/*      */     
/* 5565 */     println(".*$A_285_Begin");
/* 5566 */     println(getAttributeValue(this.eiAnnounce, "RPQTEXT", " "));
/* 5567 */     println(".*$A_285_End");
/*      */     
/* 5569 */     println(".*$A_286_Begin");
/* 5570 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5571 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5532", "", true);
/* 5572 */     this.iColWidths = new int[] { 69 };
/* 5573 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5574 */     resetPrintvars();
/* 5575 */     println(".*$A_286_End");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5582 */     println(".*$A_287_Begin");
/*      */ 
/*      */ 
/*      */     
/* 5586 */     this.strFilterAttr = new String[] { "DELIVERABLETYPE" };
/* 5587 */     this.strFilterValue = new String[] { "852" };
/* 5588 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnDeliv, this.strFilterAttr, this.strFilterValue, true);
/* 5589 */     logMessage("A_287****ANNDELIVERABLE");
/* 5590 */     displayContents(this.vReturnEntities1);
/* 5591 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "ANNDELREQTRANS");
/* 5592 */     logMessage("A_287****ANNDELREQTRANS");
/* 5593 */     displayContents(this.vReturnEntities2);
/* 5594 */     this.strFilterAttr = new String[] { "LANGUAGES", "LANGUAGES", "LANGUAGES", "LANGUAGES" };
/* 5595 */     this.strFilterValue = new String[] { "2802", "2803", "2797", "2796" };
/* 5596 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "EMEATRANSLATION");
/* 5597 */     logMessage("A_287****EMEATRANSLATION");
/* 5598 */     displayContents(this.vReturnEntities3);
/* 5599 */     this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "TRANSDELREVIEW");
/* 5600 */     logMessage("A_287****TRANSDELREVIEW");
/* 5601 */     displayContents(this.vReturnEntities4);
/* 5602 */     for (this.i = 0; this.i < this.vReturnEntities4.size(); this.i++) {
/* 5603 */       this.eiNextItem = this.vReturnEntities4.elementAt(this.i);
/* 5604 */       logMessage("A_287****" + this.eiNextItem.getEntityType() + ":" + this.eiNextItem.getEntityID());
/* 5605 */       this.eiEmeaTranslation = (EntityItem)this.eiNextItem.getUpLink(0);
/* 5606 */       this.eiOP = (EntityItem)this.eiNextItem.getDownLink(0);
/* 5607 */       this.vPrintDetails.add(getAttributeValue(this.eiEmeaTranslation, "LANGUAGES", " "));
/* 5608 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1);
/* 5609 */       this.strCondition1 += ". " + getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 5610 */       this.vPrintDetails.add(this.strCondition1);
/* 5611 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ");
/* 5612 */       this.strCondition1 += "/" + getAttributeValue(this.eiOP, "VNETUID", " ");
/* 5613 */       this.vPrintDetails.add(this.strCondition1);
/*      */     } 
/*      */ 
/*      */     
/* 5617 */     this.strHeader = new String[] { "Language", "Brand Reviewer Name", "  Node/Userid" };
/* 5618 */     this.iColWidths = new int[] { 17, 30, 17 };
/* 5619 */     println(":h4.Worldwide Customer Letter Translation");
/* 5620 */     println(":xmp.");
/* 5621 */     println(".kp off");
/* 5622 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5623 */     resetPrintvars();
/* 5624 */     println(":exmp.");
/* 5625 */     println(":p.Note: This section is deleted at PLET generation time.");
/* 5626 */     println(".*$A_287_End");
/*      */     
/* 5628 */     println(".*$A_288_Begin");
/*      */ 
/*      */     
/* 5631 */     this.strFilterAttr = new String[] { "DELIVERABLETYPE" };
/* 5632 */     this.strFilterValue = new String[] { "856" };
/* 5633 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnDeliv, this.strFilterAttr, this.strFilterValue, true);
/* 5634 */     logMessage("A_288****ANNDELIVERABLE");
/* 5635 */     displayContents(this.vReturnEntities1);
/* 5636 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "ANNDELREQTRANS");
/* 5637 */     logMessage("A_288****ANNDELREQTRANS");
/* 5638 */     displayContents(this.vReturnEntities2);
/* 5639 */     this.strFilterAttr = new String[] { "LANGUAGES", "LANGUAGES", "LANGUAGES", "LANGUAGES" };
/* 5640 */     this.strFilterValue = new String[] { "2802", "2803", "2797", "2796" };
/* 5641 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "EMEATRANSLATION");
/* 5642 */     logMessage("A_288****EMEATRANSLATION");
/* 5643 */     displayContents(this.vReturnEntities3);
/* 5644 */     this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "TRANSDELREVIEW");
/* 5645 */     logMessage("A_288****TRANSDELREVIEW");
/* 5646 */     displayContents(this.vReturnEntities4);
/* 5647 */     for (this.i = 0; this.i < this.vReturnEntities4.size(); this.i++) {
/* 5648 */       this.eiNextItem = this.vReturnEntities4.elementAt(this.i);
/* 5649 */       logMessage("A_288****" + this.eiNextItem.getEntityType() + ":" + this.eiNextItem.getEntityID());
/* 5650 */       this.eiEmeaTranslation = (EntityItem)this.eiNextItem.getUpLink(0);
/* 5651 */       this.eiOP = (EntityItem)this.eiNextItem.getDownLink(0);
/* 5652 */       this.vPrintDetails.add(getAttributeValue(this.eiEmeaTranslation, "LANGUAGES", " "));
/* 5653 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1);
/* 5654 */       this.strCondition1 += ". " + getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 5655 */       this.vPrintDetails.add(this.strCondition1);
/* 5656 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ");
/* 5657 */       this.strCondition1 += "/" + getAttributeValue(this.eiOP, "VNETUID", " ");
/* 5658 */       this.vPrintDetails.add(this.strCondition1);
/* 5659 */       this.vPrintDetails.add(getAttributeValue(this.eiEmeaTranslation, "PROPOSALINSERTID", " "));
/*      */     } 
/*      */     
/* 5662 */     this.strHeader = new String[] { "Language", "Brand Reviewer Name", "  Node/Userid", " InsertID" };
/* 5663 */     this.iColWidths = new int[] { 17, 30, 17, 10 };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5668 */     println(":xmp.");
/* 5669 */     println(".kp off");
/* 5670 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5671 */     resetPrintvars();
/* 5672 */     println(":exmp.");
/*      */     
/* 5674 */     println(".*$A_288_End");
/*      */     
/* 5676 */     println(".*$A_289_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5681 */     logMessage("A_289_Begin********************");
/*      */     
/* 5683 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 5684 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 5685 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/*      */     
/* 5687 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFCATINCL");
/* 5688 */     this.strFilterAttr = new String[] { "CATALOGNAME" };
/* 5689 */     this.strFilterValue = new String[] { "321" };
/* 5690 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CATINCL");
/* 5691 */     if (this.vReturnEntities3.size() > 0) {
/* 5692 */       println("Yes");
/*      */     } else {
/* 5694 */       println("No");
/*      */     } 
/* 5696 */     logMessage("A_289_End********************");
/* 5697 */     println(".*$A_289_End");
/*      */     
/* 5699 */     println(".*$A_290_Begin");
/* 5700 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "CATINCL");
/* 5701 */     this.strParamList1 = new String[] { "CATALOGTAXONOMY" };
/* 5702 */     printValueListInVector(this.vReturnEntities3, this.strParamList1, " ", true, false);
/*      */     
/* 5704 */     this.iColWidths = new int[] { 55 };
/* 5705 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5706 */     resetPrintvars();
/* 5707 */     println(".*$A_290_End");
/*      */     
/* 5709 */     println(".*$A_291_Begin");
/* 5710 */     this.strFilterAttr = new String[] { "CATALOGNAME" };
/* 5711 */     this.strFilterValue = new String[] { "321" };
/* 5712 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CATINCL");
/* 5713 */     if (this.vReturnEntities3.size() > 0) {
/* 5714 */       println("Yes");
/*      */     } else {
/* 5716 */       println("No");
/*      */     } 
/* 5718 */     println(".*$A_291_End");
/*      */     
/* 5720 */     println(".*$A_292_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5727 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 5728 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/*      */ 
/*      */     
/* 5731 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 5732 */     logMessage("*********A_292");
/* 5733 */     displayContents(this.vReturnEntities1);
/* 5734 */     this.strFilterAttr = new String[] { "REPLACEINSTTYPE" };
/* 5735 */     this.strFilterValue = new String[] { "1191" };
/* 5736 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, true, true, "ALTERNATEOF");
/* 5737 */     logMessage("*********A_292");
/* 5738 */     displayContents(this.vReturnEntities2);
/* 5739 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "COMMERCIALOF");
/* 5740 */     logMessage("*********A_292 ");
/* 5741 */     displayContents(this.vReturnEntities3);
/* 5742 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "COFCATINCL");
/* 5743 */     this.strFilterAttr = new String[] { "CATALOGNAME" };
/* 5744 */     this.strFilterValue = new String[] { "321" };
/* 5745 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CATINCL");
/* 5746 */     logMessage("*********A_292 CATALOGNAME");
/* 5747 */     displayContents(this.vReturnEntities3);
/* 5748 */     if (this.vReturnEntities3.size() > 0) {
/* 5749 */       println("Yes");
/*      */     } else {
/* 5751 */       println("No");
/*      */     } 
/* 5753 */     println(".*$A_292_End");
/*      */     
/* 5755 */     println(".*$A_293_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5760 */     this.strFilterAttr = new String[] { "REPLACEINSTTYPE" };
/* 5761 */     this.strFilterValue = new String[] { "1192" };
/* 5762 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, true, true, "ALTERNATEOF");
/* 5763 */     logMessage("    A_293_Begin");
/* 5764 */     displayContents(this.vReturnEntities2);
/* 5765 */     this.strFilterAttr = new String[] { "CATALOGNAME" };
/* 5766 */     this.strFilterValue = new String[] { "321" };
/* 5767 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 5768 */       this.eiAlternateOF = this.vReturnEntities2.elementAt(this.i);
/* 5769 */       this.vReturnEntities3 = searchEntityItemLink(this.eiAlternateOF, (String[])null, (String[])null, true, true, "COMMERCIALOF");
/* 5770 */       logMessage("    A_293_Begin DOWNLINKD COMOF");
/* 5771 */       displayContents(this.vReturnEntities3);
/* 5772 */       this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "COFCATINCL");
/* 5773 */       logMessage("    A_293_Begin COFCATINCL");
/* 5774 */       displayContents(this.vReturnEntities4);
/* 5775 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities4, this.strFilterAttr, this.strFilterValue, true, true, "CATINCL");
/* 5776 */       logMessage("    A_293_Begin CATINCL");
/* 5777 */       displayContents(this.vReturnEntities3);
/* 5778 */       if (this.vReturnEntities3.size() > 0) {
/* 5779 */         println(getAttributeValue(this.eiAlternateOF.getEntityType(), this.eiAlternateOF.getEntityID(), "REPLACEINST", " "));
/*      */       }
/*      */     } 
/* 5782 */     println(".*$A_293_End");
/*      */     
/* 5784 */     println(".*$A_294_Begin");
/*      */ 
/*      */     
/* 5787 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFCATINCL");
/* 5788 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "CATINCL");
/* 5789 */     this.strParamList1 = new String[] { "FEATUREBENEFIT" };
/* 5790 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 5791 */     this.iColWidths = new int[] { 69 };
/* 5792 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5793 */     resetPrintvars();
/* 5794 */     println(".*$A_294_End");
/*      */     
/* 5796 */     println(".*$A_295_Begin");
/* 5797 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "ACCESPEOWDISABLE", " "));
/* 5798 */     prettyPrint(this.strCondition1, 69);
/* 5799 */     println(".*$A_295_End");
/*      */     
/* 5801 */     println(".*$A_296_Begin");
/* 5802 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "ACCESPEOWDISABLECONSID", " "));
/* 5803 */     prettyPrint(this.strCondition1, 69);
/* 5804 */     println(".*$A_296_End");
/*      */     
/* 5806 */     println(".*$A_297_Begin");
/* 5807 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "USSEC508", " "));
/* 5808 */     prettyPrint(this.strCondition1, 69);
/* 5809 */     println(".*$A_297_End");
/*      */     
/* 5811 */     println(".*$A_298_Begin");
/* 5812 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "USSEC508LOGO", " "));
/* 5813 */     prettyPrint(this.strCondition1, 69);
/* 5814 */     println(".*$A_298_End");
/*      */     
/* 5816 */     println(".*$A_299_Begin");
/*      */ 
/*      */ 
/*      */     
/* 5820 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 5821 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/*      */ 
/*      */     
/* 5824 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 5825 */     this.vPrintDetails1 = new Vector();
/* 5826 */     this.vPrintDetails2 = new Vector();
/* 5827 */     logMessage("A_299");
/* 5828 */     displayContents(this.vReturnEntities1);
/* 5829 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 5830 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/*      */       
/* 5832 */       this.vReturnEntities2 = searchEntityItemLink(this.eiCommOF, (String[])null, (String[])null, true, true, "COFGBTA");
/* 5833 */       logMessage("A_299_1");
/* 5834 */       displayContents(this.vReturnEntities2);
/* 5835 */       this.eiNextItem = null;
/* 5836 */       this.eiNextItem1 = null;
/* 5837 */       if (this.vReturnEntities2.size() > 0) {
/* 5838 */         this.eiNextItem = this.vReturnEntities2.elementAt(0);
/* 5839 */         this.eiNextItem1 = (this.eiNextItem.getDownLinkCount() > 0) ? (EntityItem)this.eiNextItem.getDownLink(0) : null;
/*      */       } 
/* 5841 */       if (this.eiNextItem1 != null) {
/* 5842 */         this.strCondition1 = getAttributeValue(this.eiCommOF, "MACHTYPE", " ");
/* 5843 */         this.strCondition1 += "-" + getAttributeValue(this.eiCommOF, "MODEL", " ");
/* 5844 */         this.vPrintDetails.add(this.strCondition1);
/* 5845 */         this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "GBTNAME", " "));
/* 5846 */         this.vPrintDetails.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "SAPPRIMBRANDCODE", " ") : " ");
/*      */         
/* 5848 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "SAPPRODFAMCODE", " ") : " ");
/* 5849 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "OMBRANDCODE", " ") : " ");
/* 5850 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "OMPRODFAMCODE", " ") : " ");
/* 5851 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "BPDBBRANDCODE", " ") : " ");
/*      */         
/* 5853 */         this.vPrintDetails2.add(this.strCondition1);
/* 5854 */         this.vPrintDetails2.add(getAttributeValue(this.eiCommOF, "MATACCGRP", " "));
/* 5855 */         this.vPrintDetails2.add(getAttributeValue(this.eiCommOF, "ASSORTMODULE", " "));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5860 */     if (this.vPrintDetails.size() > 0) {
/* 5861 */       println(":xmp.");
/* 5862 */       println(".kp off");
/* 5863 */       println("Machine Type-Model   Description                        Primary Brand");
/* 5864 */       this.strHeader = new String[] { "", "", "   Code" };
/* 5865 */       this.iColWidths = new int[] { 19, 34, 14 };
/* 5866 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5867 */       resetPrintvars();
/* 5868 */       println("");
/* 5869 */       println("");
/* 5870 */       println("Product Family OM Brand OM Product Family BPDP Brand");
/* 5871 */       this.strHeader = new String[] { "   Code", "Code", "     Code", "   Code" };
/* 5872 */       this.iColWidths = new int[] { 14, 8, 17, 10 };
/* 5873 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails1);
/* 5874 */       this.vPrintDetails1 = new Vector();
/* 5875 */       println("");
/* 5876 */       println("");
/*      */       
/* 5878 */       this.strHeader = new String[] { " ", "   Group", "  Module" };
/* 5879 */       this.iColWidths = new int[] { 19, 17, 11 };
/* 5880 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails2);
/* 5881 */       this.vPrintDetails2 = new Vector();
/* 5882 */       println(":exmp.");
/*      */     } 
/* 5884 */     println(".*$A_299_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo300() {
/* 5893 */     println(".*$A_300_Begin");
/*      */ 
/*      */ 
/*      */     
/* 5897 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFBPEXHIBIT");
/*      */ 
/*      */     
/* 5900 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "BPEXHIBIT");
/* 5901 */     this.bConditionOK = true;
/* 5902 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 5903 */       if (this.bConditionOK) {
/* 5904 */         println(":h4.Exhibit");
/* 5905 */         println(":p.");
/* 5906 */         println("These products are added to the following iSeries 400 and AS/400 exhibits:");
/* 5907 */         println("");
/* 5908 */         println(":ul c.");
/* 5909 */         this.bConditionOK = false;
/*      */       } 
/* 5911 */       this.eiBPExhibit = this.vReturnEntities1.elementAt(this.i);
/* 5912 */       println(getAttributeValue(this.eiBPExhibit.getEntityType(), this.eiBPExhibit.getEntityID(), "BPTYPE", " "));
/* 5913 */       this.strCondition1 = getAttributeValue(this.eiBPExhibit.getEntityType(), this.eiBPExhibit.getEntityID(), "EXHIBITNAME", " ");
/* 5914 */       this.strCondition1 += " (" + getAttributeValue(this.eiBPExhibit.getEntityType(), this.eiBPExhibit.getEntityID(), "EXHIBITCODE", " ") + ")";
/* 5915 */       println(":li." + this.strCondition1);
/*      */     } 
/* 5917 */     if (!this.bConditionOK) {
/* 5918 */       println(":eul.");
/*      */     }
/*      */     
/* 5921 */     println(".*$A_300_End");
/*      */     
/* 5923 */     println(".*$A_302_Begin");
/* 5924 */     println(".*$A_302_End");
/*      */     
/* 5926 */     println(".*$A_303_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5931 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "OVERVIEWABSTRACT", " "));
/* 5932 */     prettyPrint(this.strCondition1, 69);
/* 5933 */     println(".*$A_303_End");
/*      */     
/* 5935 */     println(".*$A_304_Begin");
/* 5936 */     println(".*$A_304_End");
/*      */     
/* 5938 */     println(".*$A_306_Begin");
/* 5939 */     println(".*$A_306_End");
/*      */     
/* 5941 */     println(".*$A_308_Begin");
/* 5942 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "KEYPREREQ", " "));
/* 5943 */     prettyPrint(this.strCondition1, 69);
/* 5944 */     println(".*$A_308_End");
/*      */     
/* 5946 */     println(".*$A_310_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5951 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "ATAGLANCE", " "));
/* 5952 */     prettyPrint(this.strCondition1, 69);
/* 5953 */     println(".*$A_310_End");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5959 */     println(".*$A_312_Begin");
/* 5960 */     println(".*$A_312_End");
/*      */     
/* 5962 */     println(".*$A_314_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5967 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "FUNCVALUESQUAL", " "));
/* 5968 */     prettyPrint(this.strCondition1, 69);
/* 5969 */     println(".*$A_314_End");
/*      */     
/* 5971 */     println(".*$A_317_Begin");
/* 5972 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "PRODPOSITIONING", " "));
/* 5973 */     prettyPrint(this.strCondition1, 69);
/* 5974 */     println(".*$A_317_End");
/*      */     
/* 5976 */     println(".*$A_318_Begin");
/* 5977 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "STATEOFGENDIRECT", " "));
/* 5978 */     prettyPrint(this.strCondition1, 69);
/* 5979 */     println(".*$A_318_End");
/*      */     
/* 5981 */     println(".*$A_319_Begin");
/* 5982 */     println(".*$A_319_End");
/*      */     
/* 5984 */     println(".*$A_320_Begin");
/* 5985 */     this.strParamList1 = new String[] { "REFERENCEINFO" };
/* 5986 */     printValueListInGroup(this.grpRelatedANN, this.strParamList1, (String)null, (String)null, "", true);
/* 5987 */     this.iColWidths = new int[] { 69 };
/* 5988 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5989 */     resetPrintvars();
/* 5990 */     println(".*$A_320_End");
/*      */     
/* 5992 */     println(".*$A_321_Begin");
/* 5993 */     println(".*$A_321_End");
/*      */     
/* 5995 */     println(".*$A_323_Begin");
/* 5996 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 5997 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5529", "", true);
/* 5998 */     println((this.vPrintDetails.size() > 0) ? "Yes" : "No");
/* 5999 */     println(".*$A_323_End");
/*      */     
/* 6001 */     println(".*$A_324_Begin");
/* 6002 */     this.iColWidths = new int[] { 69 };
/* 6003 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6004 */     resetPrintvars();
/* 6005 */     println(".*$A_324_End");
/*      */     
/* 6007 */     println(".*$A_325_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6019 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 6020 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 6021 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, (String[])null, (String[])null, true, true, "COMMERCIALOF");
/* 6022 */     this.strFilterAttr = new String[] { "PUBTYPE" };
/* 6023 */     this.strFilterValue = new String[] { "5026" };
/* 6024 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPUBS");
/* 6025 */     logMessage("    A_325:COFPUB");
/* 6026 */     displayContents(this.vReturnEntities2);
/*      */ 
/*      */     
/* 6029 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 6030 */       this.eiCofPubs = this.vReturnEntities2.elementAt(this.i);
/* 6031 */       this.vReturnEntities3 = searchEntityItemLink(this.eiCofPubs, this.strFilterAttr, this.strFilterValue, true, true, "PUBLICATION");
/* 6032 */       this.eiPublication = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/* 6033 */       if (this.eiPublication != null) {
/* 6034 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBTITLE", " "));
/* 6035 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "ORDERPN", " "));
/* 6036 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBPN", " "));
/* 6037 */         this.vPrintDetails.add(getAttributeValue(this.eiCofPubs.getEntityType(), this.eiCofPubs.getEntityID(), "PUBAVAIL", " "));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6047 */     this.strFilterAttr = new String[] { "OOFCAT" };
/* 6048 */     this.strFilterValue = new String[] { "Hardware" };
/* 6049 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 6050 */     this.vReturnEntities3.removeAllElements();
/* 6051 */     if (this.vReturnEntities1.size() > 0) {
/* 6052 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "OOFFUP");
/* 6053 */       this.strFilterAttr = new String[] { "FUPCAT" };
/* 6054 */       this.strFilterValue = new String[] { "Hardware" };
/* 6055 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "FUP");
/* 6056 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "FUPOWNSPOFOMG");
/* 6057 */       this.strFilterAttr = new String[] { "FUPPOFMGCAT" };
/* 6058 */       this.strFilterValue = new String[] { "Hardware" };
/* 6059 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "FUPPOFMGMTGRP");
/* 6060 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "POFMEMBERFUPOMG");
/* 6061 */       this.strFilterAttr = new String[] { "POFCAT" };
/* 6062 */       this.strFilterValue = new String[] { "Hardware" };
/* 6063 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "PHYSICALOF");
/* 6064 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "POFPUBS");
/*      */     } 
/*      */     
/* 6067 */     this.strFilterAttr = new String[] { "PUBTYPE" };
/* 6068 */     this.strFilterValue = new String[] { "5026" };
/* 6069 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 6070 */       this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 6071 */       this.vReturnEntities3 = searchEntityItemLink(this.eiNextItem, this.strFilterAttr, this.strFilterValue, true, true, "PUBLICATION");
/* 6072 */       this.eiPublication = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/* 6073 */       if (this.eiPublication != null) {
/* 6074 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBTITLE", " "));
/* 6075 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "ORDERPN", " "));
/* 6076 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBPN", " "));
/* 6077 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem.getEntityType(), this.eiNextItem.getEntityID(), "PUBAVAIL", " "));
/*      */       } 
/*      */     } 
/*      */     
/* 6081 */     this.strHeader = new String[] { "          Title", "Number", "Number", "Date" };
/* 6082 */     this.iColWidths = new int[] { 30, 12, 12, 10 };
/*      */ 
/*      */ 
/*      */     
/* 6086 */     if (this.vPrintDetails.size() > 0) {
/* 6087 */       println("The following publications are shipped with the products.");
/* 6088 */       println("");
/* 6089 */       println(":xmp.");
/* 6090 */       println(".kp off");
/* 6091 */       println("                               Order        Part");
/* 6092 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 6093 */       resetPrintvars();
/* 6094 */       println(":exmp.");
/*      */     } 
/* 6096 */     println(".*$A_325_End");
/*      */     
/* 6098 */     println(".*$A_326_Begin");
/* 6099 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 6100 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 6101 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, (String[])null, (String[])null, true, true, "COMMERCIALOF");
/* 6102 */     this.strFilterAttr = new String[] { "PUBTYPE", "PUBTYPE", "PUBTYPE" };
/* 6103 */     this.strFilterValue = new String[] { "5023", "5024", "5025" };
/* 6104 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPUBS");
/* 6105 */     logMessage("    A_326:COFPUB");
/* 6106 */     displayContents(this.vReturnEntities2);
/*      */     
/* 6108 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 6109 */       this.eiCofPubs = this.vReturnEntities2.elementAt(this.i);
/* 6110 */       this.vReturnEntities3 = searchEntityItemLink(this.eiCofPubs, this.strFilterAttr, this.strFilterValue, false, true, "PUBLICATION");
/* 6111 */       this.eiPublication = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/*      */       
/* 6113 */       if (this.eiPublication != null) {
/* 6114 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBTITLE", " "));
/* 6115 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "ORDERPN", " "));
/* 6116 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBPN", " "));
/* 6117 */         this.vPrintDetails.add(getAttributeValue(this.eiCofPubs.getEntityType(), this.eiCofPubs.getEntityID(), "PUBAVAIL", " "));
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
/* 6128 */     this.strFilterAttr = new String[] { "OOFCAT" };
/* 6129 */     this.strFilterValue = new String[] { "Hardware" };
/* 6130 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 6131 */     this.vReturnEntities3.removeAllElements();
/* 6132 */     if (this.vReturnEntities1.size() > 0) {
/* 6133 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "OOFFUP");
/* 6134 */       this.strFilterAttr = new String[] { "FUPCAT" };
/* 6135 */       this.strFilterValue = new String[] { "Hardware" };
/* 6136 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "FUP");
/* 6137 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "FUPOWNSPOFOMG");
/* 6138 */       this.strFilterAttr = new String[] { "FUPPOFMGCAT" };
/* 6139 */       this.strFilterValue = new String[] { "Hardware" };
/* 6140 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "FUPPOFMGMTGRP");
/* 6141 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "POFMEMBERFUPOMG");
/* 6142 */       this.strFilterAttr = new String[] { "POFCAT" };
/* 6143 */       this.strFilterValue = new String[] { "Hardware" };
/* 6144 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "PHYSICALOF");
/* 6145 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "POFPUBS");
/*      */     } 
/* 6147 */     this.strFilterAttr = new String[] { "PUBTYPE", "PUBTYPE", "PUBTYPE" };
/* 6148 */     this.strFilterValue = new String[] { "5023", "5024", "5025" };
/*      */     
/* 6150 */     this.strParamList1 = new String[] { "POFPUBS" };
/* 6151 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 6152 */       this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 6153 */       this.vReturnEntities3 = searchEntityItemLink(this.eiNextItem, this.strFilterAttr, this.strFilterValue, false, true, "PUBLICATION");
/* 6154 */       this.eiPublication = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/* 6155 */       if (this.eiPublication != null) {
/* 6156 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBTITLE", " "));
/* 6157 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "ORDERPN", " "));
/* 6158 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBPN", " "));
/* 6159 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem, "PUBAVAIL", " "));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6165 */     if (this.vPrintDetails.size() > 0) {
/* 6166 */       println("To order, contact your IBM representative.");
/* 6167 */       println(":xmp.");
/* 6168 */       println(".kp off");
/* 6169 */       println("                               Order        Part");
/* 6170 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 6171 */       resetPrintvars();
/* 6172 */       this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 6173 */       printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5523", "", true);
/* 6174 */       this.iColWidths = new int[] { 69 };
/* 6175 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6176 */       resetPrintvars();
/* 6177 */       println(":exmp.");
/*      */     } 
/* 6179 */     println(".*$A_326_End");
/*      */     
/* 6181 */     println(".*$A_327_Begin");
/*      */ 
/*      */ 
/*      */     
/* 6185 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 6186 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 6187 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 6188 */     if (this.vReturnEntities1.size() > 0) {
/* 6189 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPUBS");
/* 6190 */       logMessage("    A_327:COFPUB");
/* 6191 */       displayContents(this.vReturnEntities2);
/* 6192 */       this.strFilterAttr = new String[] { "PUBTYPE", "PUBTYPE", "PUBTYPE", "PUBTYPE" };
/* 6193 */       this.strFilterValue = new String[] { "5023", "5024", "5025", "5026" };
/* 6194 */       this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, false, true, "PUBLICATION");
/* 6195 */       logMessage("    A_327:PUBLICATION");
/* 6196 */       displayContents(this.vReturnEntities1);
/*      */     } 
/* 6198 */     this.strParamList1 = new String[] { "COFPUBS" };
/* 6199 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 6200 */       this.eiPublication = this.vReturnEntities1.elementAt(this.i);
/* 6201 */       this.eiCofPubs = getUplinkedEntityItem(this.eiPublication, this.strParamList1);
/* 6202 */       logMessage("_327 COFPUBS " + this.eiCofPubs.getKey());
/* 6203 */       this.strCondition1 = getAttributeValue(this.eiPublication, "PUBLANGUAGE", " ");
/* 6204 */       if (!this.strCondition1.equals(" ")) {
/* 6205 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBTITLE", " "));
/* 6206 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "ORDERPN", " "));
/* 6207 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBPN", " "));
/* 6208 */         this.vPrintDetails.add(getAttributeValue(this.eiCofPubs, "PUBAVAIL ", " "));
/* 6209 */         this.vPrintDetails.add(this.strCondition1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6219 */     this.strHeader = new String[] { "TITLE", "ORDER NO.", "PART NO.", "AVAIL DATE", "LANGUAGE" };
/* 6220 */     this.iColWidths = new int[] { 25, 9, 8, 10, 15 };
/* 6221 */     if (this.vPrintDetails.size() > 0) {
/* 6222 */       println(":xmp.");
/* 6223 */       println(".kp off");
/* 6224 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 6225 */       resetPrintvars();
/* 6226 */       println(":exmp.");
/*      */     } 
/* 6228 */     println(".*$A_327_End");
/*      */     
/* 6230 */     println(".*$A_328_Begin");
/* 6231 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 6232 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5523", "", true);
/* 6233 */     this.iColWidths = new int[] { 69 };
/* 6234 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6235 */     resetPrintvars();
/* 6236 */     println(".*$A_328_End");
/*      */     
/* 6238 */     println(".*$A_329_Begin");
/* 6239 */     printValueListInGroup(this.grpStdAmendText, new String[] { "STANDARDAMENDTEXT" }, "STANDARDAMENDTEXT_TYPE", "5524", "", true);
/* 6240 */     this.iColWidths = new int[] { 69 };
/* 6241 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6242 */     resetPrintvars();
/* 6243 */     println(".*$A_329_End");
/*      */     
/* 6245 */     println(".*$A_330_Begin");
/* 6246 */     println(".*$A_330_End");
/*      */     
/* 6248 */     println(".*$A_332_Begin");
/*      */     
/* 6250 */     this.strParamList1 = new String[] { "STANDARDTEXT" };
/* 6251 */     printValueListInGroup(this.grpBoilPlateText, this.strParamList1, "BOILPLATETEXT_TYPE", "219", "", true);
/* 6252 */     this.iColWidths = new int[] { 69 };
/* 6253 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6254 */     resetPrintvars();
/* 6255 */     println(".*$A_332_End");
/*      */     
/* 6257 */     println(".*$A_338_Begin");
/* 6258 */     println(":xmp.");
/* 6259 */     println(".kp off");
/*      */     
/* 6261 */     this.iColWidths = new int[] { 40 };
/*      */     
/* 6263 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 6264 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 6265 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 6266 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 6267 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 6268 */       this.strCondition1 = "          " + getAttributeValue(this.eiCommOF, "MACHTYPE", " ");
/* 6269 */       this.strCondition1 += " " + getAttributeValue(this.eiCommOF, "MODEL", " ");
/*      */       
/* 6271 */       this.strHeader = new String[] { this.strCondition1 };
/*      */       
/* 6273 */       this.strCondition1 = getAttributeValue(this.eiCommOF, "TYPWIDTHMETRIC", "0");
/* 6274 */       this.strCondition1 += " " + getAttributeValue(this.eiCommOF, "TYPWIDTHUNITMETRIC", "0");
/* 6275 */       this.strCondition1 += " ( " + getAttributeValue(this.eiCommOF, "TYPWIDTHUS", "0");
/* 6276 */       this.strCondition1 += " " + getAttributeValue(this.eiCommOF, "TYPWIDTHUNITSUS", "0") + " )";
/* 6277 */       this.strCondition2 = getAttributeValue(this.eiCommOF, "TYPDEPTHMETRIC", "0");
/* 6278 */       this.strCondition2 += " " + getAttributeValue(this.eiCommOF, "TYPDEPTHUNITMETRIC", "0");
/* 6279 */       this.strCondition2 += " ( " + getAttributeValue(this.eiCommOF, "TYPDEPTHUS", "0");
/* 6280 */       this.strCondition2 += " " + getAttributeValue(this.eiCommOF, "TYPDEPTHUNITSUS", "0") + " )";
/* 6281 */       this.strCondition3 = getAttributeValue(this.eiCommOF, "TYPHEIGHTMETRIC", "0");
/* 6282 */       this.strCondition3 += " " + getAttributeValue(this.eiCommOF, "TYPHEIGHTUNITMETRIC", "0");
/* 6283 */       this.strCondition3 += " ( " + getAttributeValue(this.eiCommOF, "TYPHEIGHTUS", "0");
/* 6284 */       this.strCondition3 += " " + getAttributeValue(this.eiCommOF, "TYPHEIGHTUNITSUS", "0") + " )";
/* 6285 */       this.strCondition4 = getAttributeValue(this.eiCommOF, "TYPWEIGHTMETRIC", "0");
/* 6286 */       this.strCondition4 += " " + getAttributeValue(this.eiCommOF, "TYPWEIGHTUNITMETRIC", "0");
/* 6287 */       this.strCondition4 += " ( " + getAttributeValue(this.eiCommOF, "TYPWEIGHTUS", "0");
/* 6288 */       this.strCondition4 += " " + getAttributeValue(this.eiCommOF, "TYPWEIGHTUNITSUS", "0") + " )";
/* 6289 */       if (!this.strCondition1.equals("0 0 ( 0 0 )") || 
/* 6290 */         !this.strCondition2.equals("0 0 ( 0 0 )") || 
/* 6291 */         !this.strCondition3.equals("0 0 ( 0 0 )") || 
/* 6292 */         !this.strCondition4.equals("0 0 ( 0 0 )")) {
/* 6293 */         this.vPrintDetails.add("Width :   " + this.strCondition1);
/* 6294 */         this.vPrintDetails.add("Depth :   " + this.strCondition2);
/* 6295 */         this.vPrintDetails.add("Height:   " + this.strCondition3);
/* 6296 */         this.vPrintDetails.add("Weight:   " + this.strCondition4);
/* 6297 */         logMessage("A_338 Header" + this.strHeader[0]);
/*      */         
/* 6299 */         printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 6300 */         resetPrintvars();
/*      */       } 
/*      */     } 
/* 6303 */     println(":exmp.");
/* 6304 */     println(".*$A_338_End");
/*      */     
/* 6306 */     println(".*$A_339_Begin");
/* 6307 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "KEYSTANDANDS", " "));
/* 6308 */     prettyPrint(this.strCondition1, 69);
/* 6309 */     println(".*$A_339_End");
/*      */     
/* 6311 */     println(".*$A_340_Begin");
/* 6312 */     println(":xmp.");
/* 6313 */     println(".kp off");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6318 */     this.vReturnEntities2.removeAllElements();
/* 6319 */     logMessage("A_340.....COMMERCIALOF");
/* 6320 */     displayContents(this.vReturnEntities1);
/*      */ 
/*      */ 
/*      */     
/* 6324 */     if (this.vReturnEntities1.size() > 0) {
/* 6325 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFENVIR");
/*      */     }
/*      */     
/* 6328 */     logMessage("A_340.....COFENVIR");
/* 6329 */     this.iColWidths = new int[] { 55 };
/* 6330 */     displayContents(this.vReturnEntities2);
/* 6331 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 6332 */       this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 6333 */       this.eiCommOF = (EntityItem)this.eiNextItem.getUpLink(0);
/* 6334 */       this.eiEnvirinfo = (EntityItem)this.eiNextItem.getDownLink(0);
/* 6335 */       logMessage("A_340.....ENVIRINF:ET" + this.eiEnvirinfo.getEntityID());
/* 6336 */       this.strCondition1 = "               " + getAttributeValue(this.eiCommOF, "MACHTYPE", " ");
/* 6337 */       this.strCondition1 += " " + getAttributeValue(this.eiCommOF, "MODEL", " ");
/* 6338 */       this.strHeader = new String[] { this.strCondition1 };
/*      */ 
/*      */       
/* 6341 */       this.vReturnEntities3 = searchEntityItemLink(this.eiEnvirinfo, (String[])null, (String[])null, true, true, "ENVIRALTDEP");
/* 6342 */       for (this.j = 0; this.j < this.vReturnEntities3.size(); this.j++) {
/* 6343 */         this.eiNextItem = this.vReturnEntities3.elementAt(this.j);
/* 6344 */         this.bConditionOK = false;
/* 6345 */         logMessage("A_340....." + this.eiEnvirinfo.getEntityType() + this.eiEnvirinfo.getEntityID());
/* 6346 */         if (this.eiNextItem != null) {
/* 6347 */           logMessage("A_340....." + this.eiNextItem.getEntityType() + this.eiNextItem.getEntityID());
/* 6348 */           this.eiAltEnvirinfo = (EntityItem)this.eiNextItem.getDownLink(0);
/* 6349 */           logMessage("A_340....." + this.eiAltEnvirinfo.getEntityType() + this.eiAltEnvirinfo.getEntityID());
/*      */           
/* 6351 */           this.strCondition1 = getAttributeValue(this.eiAltEnvirinfo, "OPERTEMP_MIN", "0");
/* 6352 */           this.strCondition1 += " " + getAttributeValue(this.eiAltEnvirinfo, "OPERTEMP_MINUNITS", "0");
/* 6353 */           this.strCondition1 += " to " + getAttributeValue(this.eiAltEnvirinfo, "OPERTEMP_MAX", "0");
/* 6354 */           this.strCondition1 += " " + getAttributeValue(this.eiAltEnvirinfo, "OPERTEMP_MAXUNITS", "0");
/* 6355 */           if (!this.strCondition1.equals("0 0 to 0 0")) {
/* 6356 */             this.vPrintDetails.add("Temperature        :" + this.strCondition1);
/*      */           }
/*      */           
/* 6359 */           this.strCondition1 = getAttributeValue(this.eiAltEnvirinfo, "SYSOFFTEMP_MIN", "0");
/* 6360 */           this.strCondition1 += " " + getAttributeValue(this.eiAltEnvirinfo, "SYSOFFTEMP_MINUNITS", "0");
/* 6361 */           this.strCondition1 += " to " + getAttributeValue(this.eiAltEnvirinfo, "SYSOFFTEMP_MAX ", "0");
/* 6362 */           this.strCondition1 += " " + getAttributeValue(this.eiAltEnvirinfo, "SYSOFFTEMP_MAXUNITS", "0");
/* 6363 */           if (!this.strCondition1.equals("0 0 to 0 0")) {
/* 6364 */             this.vPrintDetails.add("System Off Temp.   :" + this.strCondition1);
/*      */           }
/*      */ 
/*      */           
/* 6368 */           this.strCondition1 = getAttributeValue(this.eiAltEnvirinfo, "ALTITUDE_MIN", "0");
/* 6369 */           this.strCondition2 = getAttributeValue(this.eiAltEnvirinfo, "ALTITUDE_MINUNITS", "0");
/* 6370 */           if (this.strCondition1.equals("0") || this.strCondition2.equals("0")) {
/* 6371 */             this.strCondition1 = getAttributeValue(this.eiAltEnvirinfo, "SHIPALTITUDE_MIN", "0");
/* 6372 */             this.strCondition2 = getAttributeValue(this.eiAltEnvirinfo, "SHIPALTITUDE_MINUNITS", "0");
/*      */           } 
/*      */           
/* 6375 */           this.strCondition3 = getAttributeValue(this.eiAltEnvirinfo, "ALTITUDE_MAX", "0");
/* 6376 */           this.strCondition4 = getAttributeValue(this.eiAltEnvirinfo, "ALTITUDE_MAXUNITS", "0");
/* 6377 */           if (this.strCondition3.equals("0") || this.strCondition4.equals("0")) {
/* 6378 */             this.strCondition3 = getAttributeValue(this.eiAltEnvirinfo, "SHIPALTITUDE_MAX", "0");
/* 6379 */             this.strCondition4 = getAttributeValue(this.eiAltEnvirinfo, "SHIPALTITUDE_MAXUNITS", "0");
/*      */           } 
/* 6381 */           if (!this.strCondition1.equals("0") || !this.strCondition2.equals("0") || !this.strCondition3.equals("0") || !this.strCondition4.equals("0")) {
/* 6382 */             this.vPrintDetails.add("Altitude           :" + this.strCondition1 + " " + (this.strCondition1.equals("0") ? "" : this.strCondition2) + " to " + this.strCondition3 + " " + this.strCondition4);
/*      */           }
/*      */           
/* 6385 */           this.strCondition1 = getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "OPERHUMID_MIN", "0");
/* 6386 */           this.strCondition2 = getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "OPERHUMID_MINUNITS", "0");
/* 6387 */           this.strCondition3 = getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "OPERHUMID_MAX", "0");
/* 6388 */           this.strCondition4 = getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "OPERHUMID_MAXUNITS", "0");
/* 6389 */           if (!this.strCondition1.equals("0") || !this.strCondition2.equals("0") || !this.strCondition3.equals("0") || !this.strCondition4.equals("0")) {
/* 6390 */             this.vPrintDetails.add("Relative Humidity  :" + this.strCondition1 + " " + (this.strCondition1.equals("0") ? "" : this.strCondition2) + " to " + this.strCondition3 + " " + this.strCondition4);
/*      */           }
/* 6392 */           this.strCondition1 = getAttributeValue(this.eiAltEnvirinfo, "WETBULB", "0");
/* 6393 */           this.strCondition1 += " " + getAttributeValue(this.eiAltEnvirinfo, "WETBULBUNITS", "0");
/* 6394 */           if (!this.strCondition1.equals("0 0")) {
/* 6395 */             this.vPrintDetails.add("Wet Bulb Reading   :" + this.strCondition1);
/*      */           }
/*      */           
/* 6398 */           this.strCondition1 = getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "ELECTPWR", "0");
/* 6399 */           this.strCondition1 += " " + getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "ELECTPWR_UNITS", "0");
/* 6400 */           if (!this.strCondition1.equals("0 0")) {
/* 6401 */             this.bConditionOK = true;
/* 6402 */             this.vPrintDetails.add("");
/* 6403 */             this.vPrintDetails.add("Power Requirements");
/* 6404 */             this.vPrintDetails.add("------------------");
/*      */             
/* 6406 */             this.vPrintDetails.add("Electrical Power   :" + this.strCondition1);
/*      */           } 
/* 6408 */           this.strCondition1 = getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "EXHAUSTCAPACITY", "0");
/* 6409 */           this.strCondition1 += " " + getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "EXHAUSTCAPACITY_UNITS", "0");
/* 6410 */           if (!this.strCondition1.equals("0 0")) {
/* 6411 */             if (!this.bConditionOK) {
/* 6412 */               this.bConditionOK = true;
/* 6413 */               this.vPrintDetails.add("");
/* 6414 */               this.vPrintDetails.add("Power Requirements");
/* 6415 */               this.vPrintDetails.add("------------------");
/*      */             } 
/* 6417 */             this.vPrintDetails.add("Exhaust Capacity   :" + this.strCondition1);
/*      */           } 
/*      */           
/* 6420 */           this.strCondition1 = getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "SOUNDEMISS", "0");
/* 6421 */           this.strCondition1 += " " + getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "SOUNDEMISSUNITS", "0");
/* 6422 */           if (!this.strCondition1.equals("0 0")) {
/* 6423 */             if (!this.bConditionOK) {
/* 6424 */               this.bConditionOK = true;
/* 6425 */               this.vPrintDetails.add("");
/* 6426 */               this.vPrintDetails.add("Power Requirements");
/* 6427 */               this.vPrintDetails.add("------------------");
/*      */             } 
/* 6429 */             this.vPrintDetails.add("Noise Level        :" + this.strCondition1);
/*      */           } 
/*      */           
/* 6432 */           this.strCondition1 = getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "LEAKAGESTARTCURRENT", "0");
/* 6433 */           this.strCondition1 += " " + getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "LEAKAGESTARTCURRENT_UNITS", "0");
/* 6434 */           if (!this.strCondition1.equals("0 0")) {
/* 6435 */             if (!this.bConditionOK) {
/* 6436 */               this.bConditionOK = true;
/* 6437 */               this.vPrintDetails.add("");
/* 6438 */               this.vPrintDetails.add("Power Requirements");
/* 6439 */               this.vPrintDetails.add("------------------");
/*      */             } 
/* 6441 */             this.vPrintDetails.add("Lkge & Startin Curr:" + this.strCondition1);
/*      */           } 
/*      */ 
/*      */           
/* 6445 */           this.strCondition1 = getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "POWERCONS", "0");
/* 6446 */           this.strCondition1 += " " + getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "POWERCONSUNITS", "0");
/* 6447 */           if (!this.strCondition1.equals("0 0")) {
/* 6448 */             if (!this.bConditionOK) {
/* 6449 */               this.bConditionOK = true;
/* 6450 */               this.vPrintDetails.add("");
/* 6451 */               this.vPrintDetails.add("Power Requirements");
/* 6452 */               this.vPrintDetails.add("------------------");
/*      */             } 
/* 6454 */             this.vPrintDetails.add("Power Consumption  :" + this.strCondition1);
/*      */           } 
/* 6456 */           this.strCondition1 = getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "PWRCOMSUMENERGYSAVMODE", "0");
/* 6457 */           this.strCondition1 += " " + getAttributeValue(this.eiEnvirinfo.getEntityType(), this.eiEnvirinfo.getEntityID(), "PWRCOMSUMENERGYSAVMODE_UNITS", "0");
/* 6458 */           if (!this.strCondition1.equals("0 0")) {
/* 6459 */             this.vPrintDetails.add("Power Cons. Saving :" + this.strCondition1);
/*      */           }
/*      */           
/* 6462 */           this.strCondition1 = getAttributeValue(this.eiCommOF, "COMPTHEOPERF", "0");
/* 6463 */           this.strCondition1 += " " + getAttributeValue(this.eiCommOF, "COMPTHEOPERFUNITS", "0");
/* 6464 */           if (!this.strCondition1.equals("0 0")) {
/* 6465 */             if (!this.bConditionOK) {
/* 6466 */               this.bConditionOK = true;
/* 6467 */               this.vPrintDetails.add("");
/* 6468 */               this.vPrintDetails.add("Power Requirements");
/* 6469 */               this.vPrintDetails.add("------------------");
/*      */             } 
/* 6471 */             this.vPrintDetails.add("Base Configuration :" + this.strCondition1);
/*      */           } 
/*      */           
/* 6474 */           printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 6475 */           resetPrintvars();
/*      */         } else {
/* 6477 */           logMessage("A_340.....is NULL");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6484 */     println(":exmp.");
/* 6485 */     println(".*$A_340_End");
/*      */     
/* 6487 */     println(".*$A_342_Begin");
/* 6488 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "HWREQTS", " "));
/* 6489 */     prettyPrint(this.strCondition1, 69);
/* 6490 */     println(".*$A_342_End");
/*      */     
/* 6492 */     println(".*$A_344_Begin");
/* 6493 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "NATLANGREGTSHW", " "));
/* 6494 */     prettyPrint(this.strCondition1, 69);
/* 6495 */     println(".*$A_344_End");
/*      */     
/* 6497 */     println(".*$A_345_Begin");
/*      */ 
/*      */     
/* 6500 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP", "COFSUBGRP", "APPLICATIONTYPE" };
/* 6501 */     this.strFilterValue = new String[] { "Software", "Application", "Base", "N/A", "33" };
/* 6502 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 6503 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 6504 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 6505 */       println(getAttributeValue(this.eiCommOF, "DESCRIPTION", " "));
/*      */     } 
/*      */     
/* 6508 */     println(".*$A_345_End");
/*      */     
/* 6510 */     println(".*$A_346_Begin");
/* 6511 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "NATLANGREGTSSW", " "));
/* 6512 */     prettyPrint(this.strCondition1, 69);
/* 6513 */     println(".*$A_346_End");
/*      */     
/* 6515 */     println(".*$A_348_Begin");
/*      */ 
/*      */     
/* 6518 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 6519 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 6520 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 6521 */     logMessage(" A_348_COMMERCIALOF");
/* 6522 */     displayContents(this.vReturnEntities1);
/* 6523 */     this.strParamList1 = new String[] { "COMPATIBILITY" };
/* 6524 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 6525 */     this.strFilterAttr = new String[] { "OOFCAT" };
/* 6526 */     this.strFilterValue = new String[] { "Hardware" };
/* 6527 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, (String[])null, (String[])null, true, true, "AVAIL");
/* 6528 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "OOFAVAIL");
/* 6529 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 6530 */     logMessage(" A_348_ORDEROF");
/* 6531 */     displayContents(this.vReturnEntities1);
/* 6532 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 6533 */     this.iColWidths = new int[] { 50 };
/* 6534 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6535 */     resetPrintvars();
/* 6536 */     println(".*$A_348_End");
/*      */     
/* 6538 */     println(".*$A_354_Begin");
/* 6539 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "LIMITATION", " "));
/* 6540 */     prettyPrint(this.strCondition1, 69);
/* 6541 */     println(".*$A_354_End");
/*      */     
/* 6543 */     println(".*$A_356_Begin");
/* 6544 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 6545 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5507", "", true);
/* 6546 */     this.iColWidths = new int[] { 69 };
/* 6547 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6548 */     resetPrintvars();
/* 6549 */     println(".*$A_356_End");
/*      */     
/* 6551 */     println(".*$A_357_Begin");
/*      */ 
/*      */     
/* 6554 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 6555 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 6556 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 6557 */     logMessage("A_357*************COMMERCIALOF");
/* 6558 */     this.strParamList1 = new String[] { "CUSTRESP" };
/* 6559 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 6560 */     this.strFilterAttr = new String[] { "OOFCAT" };
/* 6561 */     this.strFilterValue = new String[] { "Hardware" };
/* 6562 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, (String[])null, (String[])null, true, true, "AVAIL");
/* 6563 */     logMessage("A_357*************");
/* 6564 */     displayContents(this.vReturnEntities1);
/* 6565 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "OOFAVAIL");
/* 6566 */     logMessage("A_357*************");
/* 6567 */     displayContents(this.vReturnEntities2);
/* 6568 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 6569 */     logMessage("A_357*************");
/* 6570 */     displayContents(this.vReturnEntities1);
/* 6571 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 6572 */     this.iColWidths = new int[] { 50 };
/* 6573 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6574 */     resetPrintvars();
/* 6575 */     println(".*$A_357_End");
/*      */     
/* 6577 */     println(".*$A_360_Begin");
/* 6578 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "CABLEORDER", " "));
/* 6579 */     prettyPrint(this.strCondition1, 69);
/* 6580 */     println(".*$A_360_End");
/*      */     
/* 6582 */     println(".*$A_363_Begin");
/*      */ 
/*      */     
/* 6585 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 6586 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 6587 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 6588 */     this.strParamList1 = new String[] { "INSTALLABILITY" };
/* 6589 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 6590 */     this.strFilterAttr = new String[] { "OOFCAT" };
/* 6591 */     this.strFilterValue = new String[] { "Hardware" };
/* 6592 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, (String[])null, (String[])null, true, true, "AVAIL");
/* 6593 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "OOFAVAIL");
/* 6594 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 6595 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 6596 */     this.iColWidths = new int[] { 50 };
/* 6597 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6598 */     resetPrintvars();
/* 6599 */     println(".*$A_363_End");
/*      */     
/* 6601 */     println(".*$A_364_Begin");
/* 6602 */     println(getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "DIRECTCUSTSUPPORT", " ") + " ");
/* 6603 */     println(".*$A_364_End");
/*      */     
/* 6605 */     println(".*$A_365_Begin");
/* 6606 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "DIRECTCUSSUPMVS", " "));
/* 6607 */     prettyPrint(this.strCondition1, 69);
/* 6608 */     println(".*$A_365_End");
/*      */     
/* 6610 */     println(".*$A_366_Begin");
/* 6611 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "DIRECTCUSSUPIND", " "));
/* 6612 */     prettyPrint(this.strCondition1, 69);
/* 6613 */     println(".*$A_366_End");
/*      */     
/* 6615 */     println(".*$A_367_Begin");
/* 6616 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 6617 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5489", "", true);
/* 6618 */     this.iColWidths = new int[] { 69 };
/* 6619 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6620 */     resetPrintvars();
/* 6621 */     println(".*$A_367_End");
/*      */     
/* 6623 */     println(".*$A_369_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6651 */     println(".*$A_369_End");
/*      */     
/* 6653 */     println(".*$A_373_Begin");
/* 6654 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 6655 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5504", "", true);
/* 6656 */     this.iColWidths = new int[] { 69 };
/* 6657 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6658 */     resetPrintvars();
/* 6659 */     println(".*$A_373_End");
/*      */     
/* 6661 */     println(".*$A_377_Begin");
/* 6662 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 6663 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5497", "", true);
/* 6664 */     this.iColWidths = new int[] { 69 };
/* 6665 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6666 */     resetPrintvars();
/* 6667 */     println(".*$A_377_End");
/*      */     
/* 6669 */     println(".*$A_380_Begin");
/* 6670 */     println(".*$A_380_End");
/*      */     
/* 6672 */     println(".*$A_384_Begin");
/* 6673 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 6674 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5530", "", true);
/* 6675 */     this.iColWidths = new int[] { 69 };
/* 6676 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6677 */     resetPrintvars();
/* 6678 */     println(".*$A_384_End");
/*      */     
/* 6680 */     println(".*$A_391_Begin");
/* 6681 */     println(".*$A_391_End");
/*      */     
/* 6683 */     println(".*$A_393_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6689 */     this.strFilterAttr = new String[] { "OOFCAT" };
/* 6690 */     this.strFilterValue = new String[] { "Hardware" };
/* 6691 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, (String[])null, (String[])null, true, true, "AVAIL");
/* 6692 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "OOFAVAIL");
/* 6693 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 6694 */     logMessage("_393 ORDEROF'S");
/* 6695 */     displayContents(this.vReturnEntities1);
/* 6696 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 6697 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 6698 */       logMessage("_393 Processing " + this.eiOrderOF.getKey());
/*      */       
/* 6700 */       this.vReturnEntities2 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFFUP");
/* 6701 */       this.strFilterAttr = new String[] { "FUPCAT" };
/* 6702 */       this.strFilterValue = new String[] { "Hardware" };
/* 6703 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "FUP");
/* 6704 */       this.eiNextItem = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/* 6705 */       if (this.eiNextItem == null) {
/* 6706 */         logMessage("_393 No FUP linked to :" + this.eiOrderOF.getKey());
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 6712 */         this.vReturnEntities2 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFPRICE");
/* 6713 */         this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 6714 */         this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/* 6715 */         if (this.eiPriceInfo != null) {
/*      */ 
/*      */ 
/*      */           
/* 6719 */           this.strCondition1 = getAttributeValue(this.eiNextItem, "FEATURECODE", "");
/* 6720 */           if (this.strCondition1.equals("")) {
/* 6721 */             logMessage("_393 Feature code is empty");
/*      */           }
/*      */           else {
/*      */             
/* 6725 */             this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "INVNAME", " "));
/* 6726 */             this.vPrintDetails.add((this.eiNextItem != null) ? getAttributeValue(this.eiNextItem, "FEATURECODE", "") : " ");
/* 6727 */             this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", " "));
/* 6728 */             this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MODEL", " "));
/* 6729 */             this.vPrintDetails.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "RENTALCHARGE", " ") : " ");
/* 6730 */             this.vPrintDetails.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "PURCHASEPRICE", " ") : " ");
/*      */             
/* 6732 */             if (foundInEntity(this.eiOrderOF, new String[] { "INSTALL" }, new String[] { "CE" }, true) || foundInEntity(this.eiOrderOF, new String[] { "INSTALL" }, new String[] { "CIF" }, true)) {
/* 6733 */               this.vPrintDetails1.add("Y");
/*      */             } else {
/* 6735 */               this.vPrintDetails1.add("N");
/*      */             } 
/* 6737 */             if (foundInEntity(this.eiOrderOF, new String[] { "INSTALL" }, new String[] { "NA" }, true)) {
/* 6738 */               this.vPrintDetails1.add("Y");
/*      */             } else {
/* 6740 */               this.vPrintDetails1.add("N");
/*      */             } 
/* 6742 */             if (foundInEntity(this.eiOrderOF, new String[] { "CABLESREQUIRED" }, new String[] { "Yes" }, true)) {
/* 6743 */               this.vPrintDetails1.add("Y");
/*      */             } else {
/* 6745 */               this.vPrintDetails1.add("N");
/*      */             } 
/* 6747 */             this.strCondition1 = (this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "MESREMOVECHARGE", " ") : " ";
/* 6748 */             if (this.eiNextItem != null && this.strCondition1.length() > 0) {
/* 6749 */               this.vPrintDetails1.add("Y");
/*      */             } else {
/* 6751 */               this.vPrintDetails1.add("N");
/*      */             } 
/* 6753 */             this.vPrintDetails1.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "MESADDCHARGE", " ") : " ");
/* 6754 */             this.vPrintDetails1.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "MINMAINTMTHCHARGE", " ") : " ");
/* 6755 */             this.vPrintDetails1.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "ADDMAINTCHARGERATE", " ") : " ");
/* 6756 */             this.vPrintDetails1.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "MTHUSAGECHARGERATE", " ") : " ");
/*      */           } 
/*      */         } 
/*      */       } 
/* 6760 */     }  if (this.vPrintDetails.size() > 0 || this.vPrintDetails1.size() > 0) {
/* 6761 */       this.bConditionOK = true;
/* 6762 */       println(":xmp.");
/* 6763 */       println(".kp off");
/*      */     } 
/* 6765 */     if (this.vPrintDetails.size() > 0) {
/* 6766 */       this.iColWidths = new int[] { 19, 8, 9, 8, 7, 9 };
/* 6767 */       this.strHeader = new String[] { "Description        ", "Feature ", "Machine  ", "Model   ", "Rent   ", "Price" };
/* 6768 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 6769 */       resetPrintvars();
/*      */     } 
/* 6771 */     if (this.vPrintDetails1.size() > 0) {
/* 6772 */       logMessage("A_393" + this.vPrintDetails1.toString());
/* 6773 */       this.iColWidths = new int[] { 5, 5, 5, 7, 7, 7, 7, 7 };
/* 6774 */       this.strHeader = new String[] { "Field", "Plant", "Cable", " Chg.", " Chg.", " Chg.", " Chg.", " Chg." };
/* 6775 */       println("                    MES    MES     Min.    Add     Use");
/* 6776 */       println("                   Remov.  Add     Maint. Maint.   Rate");
/* 6777 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails1);
/* 6778 */       this.vPrintDetails1.removeAllElements();
/* 6779 */       this.vPrintDetails1 = null;
/*      */     } 
/* 6781 */     if (this.bConditionOK) {
/* 6782 */       println(":exmp.");
/*      */     }
/* 6784 */     println(".*$A_393_End");
/*      */     
/* 6786 */     println(".*$A_394_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6796 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP", "COFSUBGRP", "SERVICETYPE" };
/* 6797 */     this.strFilterValue = new String[] { "Service", "Maintenance", "Base", "N/A", "ServiceElect" };
/* 6798 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 6799 */     logMessage("A_394");
/* 6800 */     displayContents(this.vReturnEntities1);
/*      */     
/* 6802 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 6803 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/*      */ 
/*      */       
/* 6806 */       this.vReturnEntities2 = searchEntityItemLink(this.eiCommOF, (String[])null, (String[])null, true, true, "OOFMEMBERCOFOMG");
/* 6807 */       logMessage("A_394 OOFMEMBERCOFOMG");
/* 6808 */       displayContents(this.vReturnEntities2);
/* 6809 */       this.strFilterAttr = new String[] { "COFOOFMGCAT", "COFOOFMGSUBCAT" };
/* 6810 */       this.strFilterValue = new String[] { "Service", "Coverage" };
/* 6811 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "COFOOFMGMTGRP");
/* 6812 */       logMessage("A_394 COFOOFMGMTGRP");
/* 6813 */       displayContents(this.vReturnEntities3);
/* 6814 */       this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT" };
/* 6815 */       this.strFilterValue = new String[] { "Hardware", "FeatureCode" };
/* 6816 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities3, this.strFilterAttr, this.strFilterValue, true, true, "ORDEROF");
/* 6817 */       logMessage("A_394 ORDEROF");
/* 6818 */       displayContents(this.vReturnEntities2);
/*      */ 
/*      */       
/* 6821 */       this.vReturnEntities3 = searchEntityItemLink(this.eiCommOF, (String[])null, (String[])null, true, true, "COFPRICE");
/* 6822 */       logMessage("A_394 COFPRICE");
/* 6823 */       displayContents(this.vReturnEntities3);
/* 6824 */       this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 6825 */       logMessage("A_394 PRICEFININFO");
/* 6826 */       displayContents(this.vReturnEntities4);
/* 6827 */       this.eiPriceInfo = (this.vReturnEntities4.size() > 0) ? this.vReturnEntities4.elementAt(0) : null;
/*      */       
/* 6829 */       for (byte b = 0; b < this.vReturnEntities2.size(); b++) {
/* 6830 */         this.eiOrderOF = this.vReturnEntities2.elementAt(b);
/* 6831 */         this.vReturnEntities3 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFFUP");
/* 6832 */         logMessage("A_394 OOFFUP");
/* 6833 */         displayContents(this.vReturnEntities3);
/* 6834 */         this.strFilterAttr = new String[] { "FUPCAT" };
/* 6835 */         this.strFilterValue = new String[] { "Hardware" };
/* 6836 */         this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, this.strFilterAttr, this.strFilterValue, true, true, "FUP");
/* 6837 */         logMessage("A_394 FUP");
/* 6838 */         displayContents(this.vReturnEntities4);
/*      */ 
/*      */         
/* 6841 */         if (this.vReturnEntities4.size() != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 6846 */           this.strFilterAttr = new String[] { "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME" };
/* 6847 */           this.strFilterValue = new String[] { "109", "110", "111" };
/* 6848 */           if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, false)) {
/* 6849 */             logMessage("A_394 FUP eiCommOF:" + this.eiCommOF.getEntityID());
/* 6850 */             this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "INVNAME", " "));
/* 6851 */             this.eiNextItem = (this.vReturnEntities4.size() > 0) ? this.vReturnEntities4.elementAt(0) : null;
/* 6852 */             this.vPrintDetails.add(getAttributeValue(this.eiNextItem.getEntityType(), this.eiNextItem.getEntityID(), "FEATURECODE", " "));
/* 6853 */             this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", " "));
/* 6854 */             this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MODEL", " "));
/*      */           } else {
/* 6856 */             this.vPrintDetails.add(" ");
/* 6857 */             this.vPrintDetails.add(" ");
/* 6858 */             this.vPrintDetails.add(" ");
/* 6859 */             this.vPrintDetails.add(" ");
/*      */           } 
/* 6861 */           if (flagvalueEquals(this.eiCommOF, "SERVICECODENAME", "110")) {
/* 6862 */             this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "SRVCDLVRYTYPE", " "));
/*      */           } else {
/* 6864 */             this.vPrintDetails.add(" ");
/*      */           } 
/* 6866 */           this.strCondition1 = getAttributeValue(this.eiPriceInfo, "MAINTMTHCHARGE", "");
/* 6867 */           if (this.strCondition1.trim().length() == 0) {
/* 6868 */             this.strCondition1 = getAttributeValue(this.eiPriceInfo, "MAINTYRLYCHARGE", " ");
/*      */           }
/* 6870 */           this.vPrintDetails.add(this.strCondition1);
/* 6871 */           if (flagvalueEquals(this.eiCommOF, "SERVICECODENAME", "109")) {
/* 6872 */             this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "SRVCDLVRYTYPE", " "));
/*      */           } else {
/* 6874 */             this.vPrintDetails.add(" ");
/*      */           } 
/* 6876 */           this.vPrintDetails.add(this.strCondition1);
/* 6877 */           if (flagvalueEquals(this.eiCommOF, "SERVICECODENAME", "111")) {
/* 6878 */             this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "SRVCDLVRYTYPE", " "));
/*      */           } else {
/* 6880 */             this.vPrintDetails.add(" ");
/*      */           } 
/* 6882 */           this.vPrintDetails.add(this.strCondition1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 6887 */     if (this.vPrintDetails.size() > 0) {
/* 6888 */       this.iColWidths = new int[] { 28, 4, 4, 5, 4, 4, 5 };
/* 6889 */       this.strHeader = new String[] { "Description", "Code", "Type", "Model", "Mth-", "---", "Annl>" };
/* 6890 */       println(":xmp.");
/* 6891 */       println(".kp off");
/* 6892 */       println("                                                   On     On");
/* 6893 */       println("                                                  Site   Site");
/* 6894 */       println("                              Feat  Mach  Mach    24x7   9x5    Depot");
/* 6895 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 6896 */       resetPrintvars();
/* 6897 */       println(":exmp.");
/*      */     } 
/* 6899 */     println(".*$A_394_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo400() {
/* 6908 */     println(".*$A_423_Begin");
/* 6909 */     println(".*$A_423_End");
/*      */     
/* 6911 */     println(".*$A_424_Begin");
/*      */ 
/*      */     
/* 6914 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 6915 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 6916 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 6917 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPRICE");
/* 6918 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 6919 */     this.strParamList1 = new String[] { "IBMGLOBALFINAPPL" };
/* 6920 */     printValueListInVector(this.vReturnEntities3, this.strParamList1, " ", true, true);
/* 6921 */     this.iColWidths = new int[] { 15 };
/* 6922 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6923 */     resetPrintvars();
/*      */     
/* 6925 */     println(".*$A_424_End");
/*      */     
/* 6927 */     println(".*$A_425_Begin");
/* 6928 */     println(".*$A_425_End");
/*      */     
/* 6930 */     println(".*$A_426_Begin");
/* 6931 */     this.strParamList1 = new String[] { "SRVCDURATION" };
/*      */     
/* 6933 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 6934 */     this.strFilterValue = new String[] { "Service", "Warranty", "Base" };
/* 6935 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 6936 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 6937 */     this.iColWidths = new int[] { 15 };
/* 6938 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6939 */     resetPrintvars();
/* 6940 */     println(".*$A_426_End");
/*      */     
/* 6942 */     println(".*$A_427_Begin");
/* 6943 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 6944 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5538", "", true);
/* 6945 */     this.iColWidths = new int[] { 69 };
/* 6946 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6947 */     resetPrintvars();
/* 6948 */     println(".*$A_427_End");
/*      */     
/* 6950 */     println(".*$A_428_Begin");
/* 6951 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 6952 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5539", "", true);
/* 6953 */     this.iColWidths = new int[] { 69 };
/* 6954 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6955 */     resetPrintvars();
/* 6956 */     println(".*$A_428_End");
/*      */     
/* 6958 */     println(".*$A_429_Begin");
/* 6959 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 6960 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5540", "", true);
/* 6961 */     this.iColWidths = new int[] { 69 };
/* 6962 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6963 */     resetPrintvars();
/* 6964 */     println(".*$A_429_End");
/*      */     
/* 6966 */     println(".*$A_432_Begin");
/*      */     
/* 6968 */     this.strParamList1 = new String[] { "USAGEPLAN" };
/* 6969 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 6970 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 6971 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 6972 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 6973 */     this.iColWidths = new int[] { 55 };
/* 6974 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6975 */     resetPrintvars();
/* 6976 */     println(".*$A_432_End");
/*      */     
/* 6978 */     println(".*$A_435_Begin");
/*      */     
/* 6980 */     this.strParamList1 = new String[] { "AVGUSAGEPLANPROVISION" };
/* 6981 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 6982 */     this.iColWidths = new int[] { 55 };
/* 6983 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6984 */     resetPrintvars();
/* 6985 */     println(".*$A_435_End");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7016 */     println(".*$A_447_Begin");
/*      */     
/* 7018 */     println(getAttributeShortFlagDesc(this.eiAnnounce, "HOURRATECLASS", " ") + " ");
/* 7019 */     println(".*$A_447_End");
/*      */     
/* 7021 */     println(".*$A_448_Begin");
/*      */     
/* 7023 */     println(getAttributeShortFlagDesc(this.eiAnnounce, "HOURRATECLASSEMEA", " ") + " ");
/* 7024 */     println(".*$A_448_End");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7083 */     println(".*$A_454_Begin");
/* 7084 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 7085 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5531", "", true);
/* 7086 */     this.iColWidths = new int[] { 69 };
/* 7087 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 7088 */     resetPrintvars();
/* 7089 */     println(".*$A_454_End");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7113 */     println(".*$A_480_Begin");
/*      */ 
/*      */     
/* 7116 */     this.strFilterAttr = new String[] { "OOFCAT" };
/* 7117 */     this.strFilterValue = new String[] { "Hardware" };
/* 7118 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 7119 */     logMessage(".*$A_480_Begin");
/* 7120 */     displayContents(this.vReturnEntities1);
/* 7121 */     this.strCondition1 = "No";
/* 7122 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 7123 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 7124 */       if (foundInEntity(this.eiOrderOF, new String[] { "INSTALL" }, new String[] { "CE" }, true) || foundInEntity(this.eiOrderOF, new String[] { "INSTALL" }, new String[] { "CIF" }, true)) {
/* 7125 */         this.strCondition1 = "Yes";
/*      */         break;
/*      */       } 
/*      */     } 
/* 7129 */     println(this.strCondition1);
/* 7130 */     println(".*$A_480_End");
/*      */     
/* 7132 */     println(".*$A_481_Begin");
/*      */ 
/*      */     
/* 7135 */     this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT" };
/* 7136 */     this.strFilterValue = new String[] { "Hardware", "ModelUpgrade" };
/* 7137 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/*      */     
/* 7139 */     this.bConditionOK = false;
/* 7140 */     if (this.vReturnEntities1.size() > 0) {
/* 7141 */       this.bConditionOK = true;
/*      */     } else {
/* 7143 */       this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT" };
/* 7144 */       this.strFilterValue = new String[] { "Hardware", "ModelConvert" };
/* 7145 */       this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 7146 */       if (this.vReturnEntities1.size() > 0) {
/* 7147 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/*      */     
/* 7151 */     if (this.bConditionOK) {
/* 7152 */       println("Yes");
/*      */     } else {
/* 7154 */       println("No");
/*      */     } 
/* 7156 */     println(".*$A_481_End");
/*      */     
/* 7158 */     println(".*$A_482_Begin");
/* 7159 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 7160 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5534", "", true);
/* 7161 */     if (this.vPrintDetails.size() > 0) {
/* 7162 */       println("No");
/*      */     } else {
/* 7164 */       printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5535", "", true);
/* 7165 */       println((this.vPrintDetails.size() > 0) ? "Yes" : "No");
/*      */     } 
/* 7167 */     resetPrintvars();
/* 7168 */     println(".*$A_482_End");
/*      */ 
/*      */     
/* 7171 */     println(".*$A_483_Begin");
/* 7172 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 7173 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5534", "", true);
/* 7174 */     this.iColWidths = new int[] { 69 };
/* 7175 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 7176 */     resetPrintvars();
/* 7177 */     println(".*$A_483_End");
/*      */     
/* 7179 */     println(".*$A_490_Begin");
/*      */ 
/*      */     
/* 7182 */     this.strParamList1 = new String[] { "PERFORMANCEGROUP" };
/* 7183 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 7184 */     this.strFilterValue = new String[] { "Hardware", "System", "Initial" };
/* 7185 */     logMessage("A_490_Begin");
/* 7186 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 7187 */     displayContents(this.vReturnEntities1);
/* 7188 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFOWNSOOFOMG");
/* 7189 */     logMessage("A_490_Begin COFOWNSOOFOMG");
/* 7190 */     displayContents(this.vReturnEntities2);
/* 7191 */     this.strFilterAttr = new String[] { "COFOOFMGCAT", "COFOOFMGSUBCAT" };
/* 7192 */     this.strFilterValue = new String[] { "Hardware", "ProcIcard" };
/* 7193 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "COFOOFMGMTGRP");
/* 7194 */     logMessage("A_490_Begin COFOOFMGMTGRP");
/* 7195 */     displayContents(this.vReturnEntities3);
/* 7196 */     printValueListInVector(this.vReturnEntities3, this.strParamList1, " ", true, true);
/* 7197 */     this.iColWidths = new int[] { 69 };
/* 7198 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 7199 */     resetPrintvars();
/* 7200 */     println(".*$A_490_End");
/*      */     
/* 7202 */     println(".*$A_491_Begin");
/* 7203 */     println(getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "LICMACHINTCODE", ""));
/* 7204 */     println(".*$A_491_End");
/*      */     
/* 7206 */     println(".*$A_492_Begin");
/* 7207 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 7208 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5541", "", true);
/* 7209 */     this.iColWidths = new int[] { 69 };
/* 7210 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 7211 */     resetPrintvars();
/* 7212 */     println(".*$A_492_End");
/*      */     
/* 7214 */     println(".*$A_493_Begin");
/* 7215 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 7216 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5542", "", true);
/* 7217 */     this.iColWidths = new int[] { 69 };
/* 7218 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 7219 */     resetPrintvars();
/* 7220 */     println(".*$A_493_End");
/*      */     
/* 7222 */     println(".*$A_495_Begin");
/* 7223 */     println(".*$A_495_End");
/*      */     
/* 7225 */     println(".*$A_496_Begin");
/*      */ 
/*      */     
/* 7228 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 7229 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 7230 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 7231 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPRICE");
/* 7232 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7233 */     this.strParamList1 = new String[] { "EDUCATIONALLOW" };
/* 7234 */     printValueListInVector(this.vReturnEntities3, this.strParamList1, " ", true, true);
/*      */     
/* 7236 */     this.iColWidths = new int[] { 25 };
/* 7237 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 7238 */     resetPrintvars();
/* 7239 */     println(".*$A_496_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo500() {
/* 7247 */     println(".*$A_505_Begin");
/* 7248 */     println(".*$A_505_End");
/*      */     
/* 7250 */     println(".*$A_507_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7257 */     this.strFilterAttr = new String[] { "OOFCAT" };
/* 7258 */     this.strFilterValue = new String[] { "Hardware" };
/* 7259 */     this.vPrintDetails1 = new Vector();
/* 7260 */     this.vPrintDetails2 = new Vector();
/* 7261 */     this.vPrintDetails3 = new Vector();
/* 7262 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, (String[])null, (String[])null, true, true, "AVAIL");
/* 7263 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "OOFAVAIL");
/* 7264 */     logMessage("*********A_507 OOFAVAIL");
/* 7265 */     displayContents(this.vReturnEntities2);
/* 7266 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 7267 */     logMessage("*********A_507 ORDEROF");
/* 7268 */     displayContents(this.vReturnEntities1);
/* 7269 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 7270 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/*      */       
/* 7272 */       this.vReturnEntities2 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFFUP");
/* 7273 */       this.strFilterAttr = new String[] { "FUPCAT" };
/* 7274 */       this.strFilterValue = new String[] { "Hardware" };
/* 7275 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "FUP");
/* 7276 */       this.eiNextItem = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/*      */ 
/*      */       
/* 7279 */       this.vReturnEntities2 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFPRICE");
/* 7280 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7281 */       this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/*      */       
/* 7283 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "INVNAME", " "));
/* 7284 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", " "));
/* 7285 */       this.vPrintDetails1.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", " "));
/* 7286 */       this.vPrintDetails2.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", " "));
/* 7287 */       this.vPrintDetails3.add(getAttributeValue(this.eiOrderOF, "MACHTYPE", " "));
/*      */       
/* 7289 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MODEL", " "));
/* 7290 */       this.vPrintDetails1.add(getAttributeValue(this.eiOrderOF, "MODEL", " "));
/* 7291 */       this.vPrintDetails2.add(getAttributeValue(this.eiOrderOF, "MODEL", " "));
/* 7292 */       this.vPrintDetails3.add(getAttributeValue(this.eiOrderOF, "MODEL", " "));
/*      */       
/* 7294 */       this.vPrintDetails.add((this.eiNextItem != null) ? getAttributeValue(this.eiNextItem, "FEATURECODE", " ") : " ");
/* 7295 */       this.vPrintDetails1.add((this.eiNextItem != null) ? getAttributeValue(this.eiNextItem, "FEATURECODE", " ") : " ");
/*      */       
/* 7297 */       this.vPrintDetails.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "RENTALCHARGE", " ") : " ");
/* 7298 */       this.vPrintDetails1.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "PURCHASEPRICE", " ") : " ");
/* 7299 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "INSTALL", " ");
/* 7300 */       this.vPrintDetails1.add((this.strCondition1.equals("CE") || this.strCondition1.equals("CIF")) ? "Y" : "N");
/* 7301 */       this.vPrintDetails1.add(this.strCondition1.equals("N/A") ? "Y" : "N");
/* 7302 */       this.vPrintDetails1.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "MESREMOVECHARGE", " ") : " ");
/* 7303 */       this.vPrintDetails2.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "MESADDCHARGE", " ") : " ");
/* 7304 */       this.vPrintDetails2.add(getAttributeValue(this.eiOrderOF, "CABLESREQUIRED", "No"));
/* 7305 */       this.strCondition1 = (this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "MINMAINTMTHCHARGE", " ") : " ";
/* 7306 */       this.strCondition2 = (this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "MINMAINTYEARCHARGE", " ") : " ";
/* 7307 */       this.vPrintDetails3.add(!this.strCondition1.equals(" ") ? this.strCondition1 : this.strCondition2);
/* 7308 */       this.vPrintDetails3.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "ADDMAINTCHARGERATE", " ") : " ");
/* 7309 */       this.vPrintDetails3.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "MTHUSAGECHARGERATE", " ") : " ");
/*      */     } 
/*      */     
/* 7312 */     this.strHeader = new String[] { "Description", "MType", "Model", "FeatNo", "Rental Chg." };
/* 7313 */     this.iColWidths = new int[] { 17, 6, 5, 6, 11 };
/* 7314 */     println(":xmp.");
/* 7315 */     println(".kp off");
/* 7316 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 7317 */     resetPrintvars();
/*      */     
/* 7319 */     this.strHeader = new String[] { "MType", "Model", "FeatNo", "Purch. Price", "FieldInstall", "PlantInstall", "MESRemoval" };
/* 7320 */     this.iColWidths = new int[] { 6, 5, 6, 12, 12, 12, 10 };
/* 7321 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails1);
/* 7322 */     if (this.vPrintDetails1.size() > 0) {
/* 7323 */       this.vPrintDetails1.removeAllElements();
/*      */     }
/*      */     
/* 7326 */     this.strHeader = new String[] { "Machine Type", "Model", "MES Add chg.", "Cable Req." };
/* 7327 */     this.iColWidths = new int[] { 12, 9, 12, 12 };
/* 7328 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails2);
/* 7329 */     if (this.vPrintDetails2.size() > 0) {
/* 7330 */       this.vPrintDetails2.removeAllElements();
/*      */     }
/*      */     
/* 7333 */     this.strHeader = new String[] { "MType", "Model", "Min Maint Chg.", "Addl Maint Chg.", "Monthly Use Chg." };
/* 7334 */     this.iColWidths = new int[] { 6, 5, 14, 15, 15 };
/* 7335 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails3);
/* 7336 */     if (this.vPrintDetails3.size() > 0) {
/* 7337 */       this.vPrintDetails3.removeAllElements();
/*      */     }
/* 7339 */     println(":exmp.");
/* 7340 */     println(".*$A_507_End");
/*      */     
/* 7342 */     println(".*$A_509_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7350 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 7351 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 7352 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 7353 */     logMessage("$A_509***************");
/* 7354 */     displayContents(this.vReturnEntities1);
/* 7355 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPUBS");
/* 7356 */     logMessage("$A_509***************cofpubs");
/* 7357 */     displayContents(this.vReturnEntities2);
/* 7358 */     this.strFilterAttr = new String[] { "PUBTYPE", "PUBTYPE", "PUBTYPE", "PUBTYPE" };
/* 7359 */     this.strFilterValue = new String[] { "5022", "5023", "5024", "5025" };
/* 7360 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "PUBLICATION");
/* 7361 */     logMessage("$A_509***************PUBLICATION");
/* 7362 */     displayContents(this.vReturnEntities3);
/* 7363 */     for (this.i = 0; this.i < this.vReturnEntities3.size(); this.i++) {
/* 7364 */       this.eiPublication = this.vReturnEntities3.elementAt(this.i);
/* 7365 */       this.vReturnEntities2 = searchEntityItemLink(this.eiPublication, (String[])null, (String[])null, true, true, "PUBPRICE");
/* 7366 */       this.eiNextItem = (this.vReturnEntities2.size() > 0) ? this.vReturnEntities2.elementAt(0) : null;
/* 7367 */       logMessage("$A_509***************PUBPRICE");
/* 7368 */       displayContents(this.vReturnEntities2);
/* 7369 */       if (this.eiNextItem != null) {
/* 7370 */         this.eiPriceInfo = (EntityItem)this.eiNextItem.getDownLink(0);
/* 7371 */         this.strCondition1 = getAttributeValue(this.eiPublication, "PUBTITLE", " ");
/* 7372 */         this.vPrintDetails.add(this.strCondition1);
/* 7373 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "ORDERPN", " "));
/* 7374 */         this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " "));
/*      */       } else {
/* 7376 */         logMessage("$A_509***************NO PRICEINFO FOUND");
/*      */       } 
/*      */     } 
/* 7379 */     this.strHeader = new String[] { "Title", "Order No.", "Charge" };
/* 7380 */     this.iColWidths = new int[] { 15, 10, 10 };
/* 7381 */     println(":xmp.");
/* 7382 */     println(".kp off");
/* 7383 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 7384 */     resetPrintvars();
/* 7385 */     println(":exmp.");
/* 7386 */     println(".*$A_509_End");
/* 7387 */     println(".*$A_513_Begin");
/*      */ 
/*      */ 
/*      */     
/* 7391 */     this.strFilterAttr = new String[] { "COFCAT" };
/* 7392 */     this.strFilterValue = new String[] { "Service" };
/* 7393 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 7394 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPUBS");
/* 7395 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PUBLICATION");
/* 7396 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "PUBPRICE");
/* 7397 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7398 */     if (this.vReturnEntities3.size() > 0) {
/* 7399 */       println("Yes");
/*      */     } else {
/* 7401 */       println("No");
/*      */     } 
/* 7403 */     println(".*$A_513_End");
/*      */     
/* 7405 */     println(".*$A_515_Begin");
/*      */ 
/*      */     
/* 7408 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 7409 */     this.strFilterValue = new String[] { "Service", "Maintenance", "Base" };
/* 7410 */     logMessage("*****************A_515_Searching COMMERCIALOF");
/* 7411 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 7412 */     logMessage("*****************A_515_Begin");
/* 7413 */     displayContents(this.vReturnEntities1);
/*      */     
/* 7415 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 7416 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 7417 */       this.vReturnEntities2 = searchEntityItemLink(this.eiCommOF, (String[])null, (String[])null, true, true, "COFPRICE");
/* 7418 */       this.bConditionOK = false;
/* 7419 */       this.strCondition1 = " ";
/* 7420 */       this.strCondition2 = " ";
/* 7421 */       this.strCondition3 = " ";
/* 7422 */       this.strCondition4 = " ";
/* 7423 */       logMessage("*****************A_515_COFPRICE");
/* 7424 */       displayContents(this.vReturnEntities2);
/* 7425 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7426 */       logMessage("*****************A_515_PRICEFININFO");
/* 7427 */       displayContents(this.vReturnEntities3);
/* 7428 */       this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/*      */       
/* 7430 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7431 */       this.strFilterValue = new String[] { "6132", "6133", "100" };
/* 7432 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7433 */         logMessage("*****************1) A_515 " + this.eiCommOF.getKey() + ":" + this.eiPriceInfo.getKey());
/* 7434 */         this.strCondition1 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7435 */         this.bConditionOK = true;
/*      */       } 
/*      */ 
/*      */       
/* 7439 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7440 */       this.strFilterValue = new String[] { "6132", "6135", "100" };
/* 7441 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7442 */         logMessage("*****************2) A_515 " + this.eiCommOF.getKey() + ":" + this.eiPriceInfo.getKey());
/* 7443 */         this.strCondition2 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7444 */         this.bConditionOK = true;
/*      */       } 
/*      */ 
/*      */       
/* 7448 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7449 */       this.strFilterValue = new String[] { "6132", "6133", "101" };
/* 7450 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7451 */         logMessage("*****************3) A_515 " + this.eiCommOF.getKey() + ":" + this.eiPriceInfo.getKey());
/* 7452 */         this.strCondition3 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7453 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7456 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7457 */       this.strFilterValue = new String[] { "6132", "6135", "101" };
/* 7458 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7459 */         logMessage("*****************4) A_515 " + this.eiCommOF.getKey() + ":" + this.eiPriceInfo.getKey());
/* 7460 */         this.strCondition4 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7461 */         this.bConditionOK = true;
/*      */       } 
/* 7463 */       if (this.bConditionOK) {
/* 7464 */         this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MACHTYPE", " "));
/* 7465 */         this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MODEL", " "));
/* 7466 */         this.vPrintDetails.add(this.strCondition1);
/* 7467 */         this.vPrintDetails.add(this.strCondition2);
/* 7468 */         this.vPrintDetails.add(this.strCondition3);
/* 7469 */         this.vPrintDetails.add(this.strCondition4);
/*      */       } 
/*      */     } 
/* 7472 */     this.strHeader = new String[] { "Machine Type", "Model", "IOR 9x5", "IOE 9x5", "IOR 24x7", "IOE 24x7" };
/* 7473 */     this.iColWidths = new int[] { 12, 10, 10, 10, 10, 10 };
/* 7474 */     println(":xmp.");
/* 7475 */     println(".kp off");
/* 7476 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 7477 */     resetPrintvars();
/* 7478 */     println(":exmp.");
/* 7479 */     println(".*$A_515_End");
/*      */     
/* 7481 */     println(".*$A_516_Begin");
/*      */ 
/*      */     
/* 7484 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 7485 */     this.strFilterValue = new String[] { "Service", "Warranty", "Upgrade" };
/* 7486 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 7487 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 7488 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 7489 */       this.bConditionOK = false;
/* 7490 */       this.strCondition1 = " ";
/* 7491 */       this.strCondition2 = " ";
/* 7492 */       this.strCondition3 = " ";
/* 7493 */       this.strCondition4 = " ";
/* 7494 */       this.strCondition5 = " ";
/* 7495 */       this.vReturnEntities2 = searchEntityItemLink(this.eiCommOF, (String[])null, (String[])null, true, true, "COFPRICE");
/* 7496 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7497 */       this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/*      */       
/* 7499 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7500 */       this.strFilterValue = new String[] { "6131", "6133", "107" };
/* 7501 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7502 */         this.strCondition1 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7503 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7506 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7507 */       this.strFilterValue = new String[] { "6131", "6135", "107" };
/* 7508 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7509 */         this.strCondition2 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7510 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7513 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7514 */       this.strFilterValue = new String[] { "6131", "6133", "108" };
/* 7515 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7516 */         this.strCondition3 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7517 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7520 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7521 */       this.strFilterValue = new String[] { "6131", "6135", "108" };
/* 7522 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7523 */         this.strCondition4 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7524 */         this.bConditionOK = true;
/*      */       } 
/* 7526 */       if (this.bConditionOK) {
/* 7527 */         this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MACHTYPE", " "));
/* 7528 */         this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MODEL", " "));
/* 7529 */         this.vPrintDetails.add(this.strCondition1);
/* 7530 */         this.vPrintDetails.add(this.strCondition2);
/* 7531 */         this.vPrintDetails.add(this.strCondition3);
/* 7532 */         this.vPrintDetails.add(this.strCondition4);
/* 7533 */         this.vPrintDetails.add(this.strCondition5);
/*      */       } 
/*      */     } 
/*      */     
/* 7537 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 7538 */     this.strFilterValue = new String[] { "Service", "Maintenance", "Base" };
/* 7539 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 7540 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 7541 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 7542 */       this.bConditionOK = false;
/* 7543 */       this.strCondition1 = " ";
/* 7544 */       this.strCondition2 = " ";
/* 7545 */       this.strCondition3 = " ";
/* 7546 */       this.strCondition4 = " ";
/* 7547 */       this.strCondition5 = " ";
/* 7548 */       this.vReturnEntities2 = searchEntityItemLink(this.eiCommOF, (String[])null, (String[])null, true, true, "COFPRICE");
/* 7549 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7550 */       this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/*      */       
/* 7552 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7553 */       this.strFilterValue = new String[] { "6131", "6133", "109" };
/* 7554 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7555 */         this.strCondition1 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7556 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7559 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7560 */       this.strFilterValue = new String[] { "6131", "6135", "109" };
/* 7561 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7562 */         this.strCondition2 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7563 */         this.bConditionOK = true;
/*      */       } 
/*      */ 
/*      */       
/* 7567 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7568 */       this.strFilterValue = new String[] { "6131", "6133", "108" };
/* 7569 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7570 */         this.strCondition3 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7571 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7574 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7575 */       this.strFilterValue = new String[] { "6131", "6135", "108" };
/* 7576 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7577 */         this.strCondition4 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7578 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7581 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7582 */       this.strFilterValue = new String[] { "6131", "6134", "111" };
/* 7583 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7584 */         this.strCondition5 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7585 */         this.bConditionOK = true;
/*      */       } 
/* 7587 */       if (this.bConditionOK) {
/* 7588 */         this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MACHTYPE", " "));
/* 7589 */         this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MODEL", " "));
/* 7590 */         this.vPrintDetails.add(this.strCondition1);
/* 7591 */         this.vPrintDetails.add(this.strCondition2);
/* 7592 */         this.vPrintDetails.add(this.strCondition3);
/* 7593 */         this.vPrintDetails.add(this.strCondition4);
/* 7594 */         this.vPrintDetails.add(this.strCondition5);
/*      */       } 
/*      */     } 
/* 7597 */     this.strHeader = new String[] { "Machine Type", "Model", "IOR 9x5", "IOE 9x5", "IOR 24x7", "IOE 24x7", "EZ" };
/* 7598 */     this.iColWidths = new int[] { 12, 10, 9, 9, 9, 9, 9 };
/* 7599 */     println(":xmp.");
/* 7600 */     println(".kp off");
/* 7601 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 7602 */     resetPrintvars();
/* 7603 */     println(":exmp.");
/* 7604 */     println(".*$A_516_End");
/*      */     
/* 7606 */     println(".*$A_517_Begin");
/*      */ 
/*      */     
/* 7609 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 7610 */     this.strFilterValue = new String[] { "Service", "Maintenance", "Base" };
/* 7611 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 7612 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 7613 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 7614 */       this.bConditionOK = false;
/* 7615 */       this.strCondition1 = " ";
/* 7616 */       this.strCondition2 = " ";
/* 7617 */       this.strCondition3 = " ";
/* 7618 */       this.strCondition4 = " ";
/* 7619 */       this.strCondition5 = " ";
/* 7620 */       this.vReturnEntities2 = searchEntityItemLink(this.eiCommOF, (String[])null, (String[])null, true, true, "COFPRICE");
/* 7621 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7622 */       this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/*      */       
/* 7624 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7625 */       this.strFilterValue = new String[] { "6132", "6134", "102" };
/* 7626 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7627 */         this.strCondition1 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7628 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7631 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7632 */       this.strFilterValue = new String[] { "6132", "6133", "103" };
/* 7633 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7634 */         this.strCondition2 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7635 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7638 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7639 */       this.strFilterValue = new String[] { "6132", "6135", "104" };
/* 7640 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7641 */         this.strCondition3 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7642 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7645 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7646 */       this.strFilterValue = new String[] { "6132", "6133", "105" };
/* 7647 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7648 */         this.strCondition4 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7649 */         this.bConditionOK = true;
/*      */       } 
/*      */       
/* 7652 */       this.strFilterAttr = new String[] { "SERVICETYPE", "SRVCDLVRYTYPE", "SERVICECODENAME" };
/* 7653 */       this.strFilterValue = new String[] { "6132", "6135", "106" };
/* 7654 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, true)) {
/* 7655 */         this.strCondition5 = getAttributeValue(this.eiPriceInfo, "PRICEVALUE", " ");
/* 7656 */         this.bConditionOK = true;
/*      */       } 
/* 7658 */       if (this.bConditionOK) {
/* 7659 */         this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MACHTYPE", " "));
/* 7660 */         this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MODEL", " "));
/* 7661 */         this.vPrintDetails.add(this.strCondition1);
/* 7662 */         this.vPrintDetails.add(this.strCondition2);
/* 7663 */         this.vPrintDetails.add(this.strCondition3);
/* 7664 */         this.vPrintDetails.add(this.strCondition4);
/* 7665 */         this.vPrintDetails.add(this.strCondition5);
/*      */       } 
/*      */     } 
/* 7668 */     this.strHeader = new String[] { "Machine Type", "Model", "EZ", "IOR 9x5", "IOE 9x5", "IOR 24x7", "IOE 24x7" };
/* 7669 */     this.iColWidths = new int[] { 12, 10, 9, 9, 9, 9, 9 };
/* 7670 */     println(":xmp.");
/* 7671 */     println(".kp off");
/* 7672 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 7673 */     resetPrintvars();
/* 7674 */     println(":exmp.");
/* 7675 */     println(".*$A_517_End");
/*      */     
/* 7677 */     println(".*$A_518_Begin");
/* 7678 */     println(".*$A_518_End");
/*      */     
/* 7680 */     println(".*$A_520_Begin");
/* 7681 */     println(getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "AMCALLCENTER", ""));
/* 7682 */     println(".*$A_520_End");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7687 */     println(".*$A_525_Begin");
/*      */ 
/*      */     
/* 7690 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT" };
/* 7691 */     this.strFilterValue = new String[] { "Service", "Maintenance" };
/* 7692 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 7693 */     logMessage("A_525_***********SERVICE MAINTENANCE ");
/* 7694 */     displayContents(this.vReturnEntities1);
/* 7695 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/*      */ 
/*      */ 
/*      */       
/* 7699 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 7700 */       this.vReturnEntities2 = searchEntityItemLink(this.eiCommOF, (String[])null, (String[])null, true, true, "COFPRICE");
/* 7701 */       logMessage("A_525_***********COFPRICE ");
/* 7702 */       displayContents(this.vReturnEntities2);
/* 7703 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7704 */       logMessage("A_525_***********PRICEFININFO");
/* 7705 */       displayContents(this.vReturnEntities3);
/* 7706 */       this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/* 7707 */       this.strFilterAttr = new String[] { "COFGRP", "COFGRP" };
/* 7708 */       this.strFilterValue = new String[] { "Base", "Upgrade" };
/* 7709 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, false)) {
/* 7710 */         logMessage("A_525_***********COMMOF GRP OK");
/* 7711 */         this.strFilterAttr = new String[] { "SERVICETYPE", "SERVICETYPE" };
/* 7712 */         this.strFilterValue = new String[] { "6132", "6131" };
/* 7713 */         if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, false) && 
/* 7714 */           flagvalueEquals(this.eiCommOF, "SRVCDLVRYTYPE", "6135")) {
/* 7715 */           logMessage("A_525_***********COMMOF SERVICETYPE OK");
/* 7716 */           this.strFilterAttr = new String[] { "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME" };
/* 7717 */           this.strFilterValue = new String[] { "100", "101", "107", "108", "109", "110", "104", "106" };
/* 7718 */           if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, false)) {
/* 7719 */             logMessage("A_525_***********COMMOF SERVICECODENAMETYPE OK");
/* 7720 */             this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "SRVCDLVRYTYPE", ""));
/* 7721 */             this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "BILLABLEEXCHANGEPRICE", "Price not found"));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 7729 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT" };
/* 7730 */     this.strFilterValue = new String[] { "Service", "Warranty" };
/* 7731 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 7732 */     logMessage("A_525_***********SERVICE WARRANTY ");
/* 7733 */     displayContents(this.vReturnEntities1);
/* 7734 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/*      */ 
/*      */ 
/*      */       
/* 7738 */       this.eiCommOF = this.vReturnEntities1.elementAt(this.i);
/* 7739 */       this.vReturnEntities2 = searchEntityItemLink(this.eiCommOF, (String[])null, (String[])null, true, true, "COFPRICE");
/* 7740 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7741 */       this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/* 7742 */       this.strFilterAttr = new String[] { "COFGRP", "COFGRP" };
/* 7743 */       this.strFilterValue = new String[] { "Base", "Upgrade" };
/* 7744 */       if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, false)) {
/* 7745 */         this.strFilterAttr = new String[] { "SERVICETYPE", "SERVICETYPE" };
/* 7746 */         this.strFilterValue = new String[] { "6132", "6131" };
/* 7747 */         if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, false) && 
/* 7748 */           flagvalueEquals(this.eiCommOF.getEntityType(), this.eiCommOF.getEntityID(), "SRVCDLVRYTYPE", "6135")) {
/* 7749 */           this.strFilterAttr = new String[] { "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME", "SERVICECODENAME" };
/* 7750 */           this.strFilterValue = new String[] { "100", "101", "107", "108", "109", "110", "104", "106" };
/* 7751 */           if (foundInEntity(this.eiCommOF, this.strFilterAttr, this.strFilterValue, false)) {
/* 7752 */             this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "SRVCDLVRYTYPE", ""));
/* 7753 */             this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "BILLABLEEXCHANGEPRICE", "Price not found"));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 7760 */     this.iColWidths = new int[] { 30, 15 };
/* 7761 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 7762 */     resetPrintvars();
/* 7763 */     println(".*$A_525_End");
/*      */     
/* 7765 */     println(".*$A_526_Begin");
/*      */     
/* 7767 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 7768 */     this.strFilterValue = new String[] { "Hardware", "System", "Upgrade" };
/* 7769 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpCofAvail, this.strFilterAttr, this.strFilterValue, true, false, "COMMERCIALOF");
/* 7770 */     if (this.vReturnEntities1.size() > 0) {
/* 7771 */       println("Yes");
/*      */     } else {
/* 7773 */       println("No");
/*      */     } 
/* 7775 */     println(".*$A_526_End");
/*      */     
/* 7777 */     println(".*$A_527_Begin");
/* 7778 */     println(":h3.Model Upgrades:");
/* 7779 */     println(":p.");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7785 */     this.strFilterAttr = new String[] { "OOFCAT", "OOFSUBCAT" };
/* 7786 */     this.strFilterValue = new String[] { "Hardware", "ModelUpgrade" };
/*      */     
/* 7788 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 7789 */     displayContents(this.vReturnEntities1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7794 */     this.i = 0;
/* 7795 */     println(":xmp.");
/* 7796 */     println(".in 0");
/* 7797 */     println(".kp off");
/*      */     
/* 7799 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 7800 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 7801 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FROMMODEL", "   "));
/* 7802 */       this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "MODEL", "   "));
/* 7803 */       this.vReturnEntities2 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFPRICE");
/* 7804 */       logMessage("A_527_Begin      OOFPRICE");
/* 7805 */       displayContents(this.vReturnEntities2);
/* 7806 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7807 */       this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/* 7808 */       this.vPrintDetails.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "PRICEVALUE", "0.00") : "0.00");
/*      */     } 
/* 7810 */     this.iColWidths = new int[] { 5, 5, 16 };
/* 7811 */     this.strHeader = new String[] { "From", " To", "Purchase Price*" };
/* 7812 */     if (this.vPrintDetails.size() > 0) {
/* 7813 */       println("Model Model Model Conversion");
/* 7814 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 7815 */       println("* Parts removed or replaced become the property of IBM and must");
/* 7816 */       println("be returned.");
/*      */     } 
/*      */ 
/*      */     
/* 7820 */     resetPrintvars();
/* 7821 */     println(":exmp.");
/* 7822 */     println(".*$A_527_End");
/*      */     
/* 7824 */     println(".*$A_528_Begin");
/* 7825 */     String[] arrayOfString1 = { "OOFCAT", "OOFSUBCAT" };
/* 7826 */     String[] arrayOfString2 = { "Hardware", "FeatureConvert" };
/*      */     
/* 7828 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 7829 */     logMessage("A_528");
/* 7830 */     displayContents(this.vReturnEntities1);
/* 7831 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 7832 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 7833 */       this.vPrintDetails.add(" " + getAttributeValue(this.eiOrderOF, "FROMFEATURECODE", "    "));
/* 7834 */       this.vPrintDetails.add(" " + getAttributeValue(this.eiOrderOF, "FEATURECODE", "    "));
/*      */       
/* 7836 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "RETURNEDPARTS", " ");
/* 7837 */       this.vPrintDetails.add(" " + (this.strCondition1.equals("Yes") ? "  Y " : "  N "));
/* 7838 */       this.strCondition1 = getAttributeValue(this.eiOrderOF, "CONTMAINTENANCE", " ");
/* 7839 */       this.vPrintDetails.add(" " + (this.strCondition1.equals("Yes") ? "  Y " : "  N "));
/* 7840 */       this.vReturnEntities2 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFPRICE");
/* 7841 */       logMessage("A_528_Begin      OOFPRICE");
/* 7842 */       displayContents(this.vReturnEntities2);
/* 7843 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7844 */       this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/* 7845 */       this.vPrintDetails.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "PRICEVALUE", "0.00") : "0.00");
/*      */     } 
/* 7847 */     this.iColWidths = new int[] { 5, 5, 8, 11, 18 };
/* 7848 */     this.strHeader = new String[] { "From", "  To", " Parts*", "Maintenance", "Purchase Price" };
/* 7849 */     println(":xmp.");
/* 7850 */     println(".kp off");
/*      */     
/* 7852 */     if (this.vPrintDetails.size() > 0) {
/* 7853 */       println("Feat   Feat Returned Continuous  Feature Conversion");
/*      */     }
/* 7855 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 7856 */     resetPrintvars();
/* 7857 */     println("* Parts removed or replaced become the property of IBM and must");
/* 7858 */     println("  be returned");
/* 7859 */     println(":exmp.");
/*      */     
/* 7861 */     println(".*$A_528_End");
/*      */     
/* 7863 */     println(".*$A_529_Begin");
/*      */ 
/*      */     
/* 7866 */     arrayOfString1 = new String[] { "OOFCAT", "OOFSUBCAT" };
/* 7867 */     arrayOfString2 = new String[] { "Hardware", "FeatureCode" };
/* 7868 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, arrayOfString1, arrayOfString2, true, false, "ORDEROF");
/* 7869 */     logMessage("A_529_Begin");
/* 7870 */     displayContents(this.vReturnEntities1);
/* 7871 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 7872 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 7873 */       this.vReturnEntities2 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFPRICE");
/* 7874 */       logMessage("A_529_Begin      OOFPRICE");
/* 7875 */       displayContents(this.vReturnEntities2);
/* 7876 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 7877 */       logMessage("A_529_Begin      OOFPRICE");
/* 7878 */       displayContents(this.vReturnEntities3);
/*      */       
/* 7880 */       if (this.vReturnEntities3.size() > 0) {
/* 7881 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "INVNAME", " "));
/* 7882 */         this.vPrintDetails.add(getAttributeValue(this.eiOrderOF, "FEATURECODE", " "));
/*      */         
/* 7884 */         this.eiPriceInfo = (this.vReturnEntities3.size() > 0) ? this.vReturnEntities3.elementAt(0) : null;
/*      */         
/* 7886 */         this.vPrintDetails.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "FIELDINSTALLCHG", " ") : " ");
/*      */       } 
/*      */     } 
/* 7889 */     this.iColWidths = new int[] { 35, 10, 10 };
/* 7890 */     this.strHeader = new String[] { "Description", "Feature", "Charge" };
/* 7891 */     println(":xmp.");
/* 7892 */     println(".kp off");
/* 7893 */     if (this.vPrintDetails.size() > 0) {
/* 7894 */       println("The following charges apply to features ordered for field installation");
/* 7895 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 7896 */       resetPrintvars();
/*      */     } 
/* 7898 */     println(":exmp.");
/*      */     
/* 7900 */     println(".*$A_529_End");
/*      */     
/* 7902 */     println(".*$A_530_Begin");
/*      */ 
/*      */ 
/*      */     
/* 7906 */     GeneralAreaGroup generalAreaGroup = this.m_geList.getRfaGeoEMEAInclusion(this.eiAnnounce);
/* 7907 */     logMessage("A_530_Begin returned GEItemcount" + generalAreaGroup.getGeneralAreaItemCount());
/* 7908 */     generalAreaGroup = this.m_geList.getRfaGeoEMEAInclusion(this.eiAnnounce);
/* 7909 */     if (this.m_geList.isRfaGeoEMEA(this.eiAnnounce)) {
/* 7910 */       this.j = generalAreaGroup.getGeneralAreaItemCount();
/* 7911 */       if (this.j >= 130) {
/* 7912 */         println("All European, Middle Eastern and African Countries");
/*      */       }
/* 7914 */       else if (this.j < 60) {
/* 7915 */         println("Only in the following  European, Middle Eastern and African Countries: ");
/* 7916 */         this.strCondition1 = "";
/* 7917 */         for (byte b = 0; b < generalAreaGroup.getGeneralAreaItemCount(); b++) {
/* 7918 */           GeneralAreaItem generalAreaItem = generalAreaGroup.getGeneralAreaItem(b);
/* 7919 */           this.strCondition1 += ((this.strCondition1.length() > 0) ? " ," : "") + generalAreaItem.getName();
/*      */         } 
/*      */         
/* 7922 */         prettyPrint(this.strCondition1, 69);
/* 7923 */       } else if (this.j > 60 && this.j < 130) {
/* 7924 */         generalAreaGroup = this.m_geList.getRfaGeoEMEAExclusion(this.eiAnnounce);
/* 7925 */         println("All  European, Middle Eastern and African Countries except:");
/* 7926 */         this.strCondition1 = "";
/* 7927 */         for (byte b = 0; b < generalAreaGroup.getGeneralAreaItemCount(); b++) {
/* 7928 */           GeneralAreaItem generalAreaItem = generalAreaGroup.getGeneralAreaItem(b);
/* 7929 */           this.strCondition1 += ((this.strCondition1.length() > 0) ? " ," : "") + generalAreaItem.getName();
/*      */         } 
/* 7931 */         prettyPrint(this.strCondition1, 69);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 7936 */     println(".*$A_530_End");
/*      */     
/* 7938 */     println(".*$A_531_Begin");
/* 7939 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "EXTERNALLETATTACH", " "));
/* 7940 */     prettyPrint(this.strCondition1, 69);
/* 7941 */     println(".*$A_531_End");
/*      */     
/* 7943 */     println(".*$A_532_Begin");
/* 7944 */     println(".*$A_532_End");
/*      */     
/* 7946 */     println(".*$A_533_Begin");
/*      */     
/* 7948 */     println(".*$A_533_End");
/*      */     
/* 7950 */     println(".*$A_534_Begin");
/*      */     
/* 7952 */     println(".*$A_534_End");
/*      */     
/* 7954 */     println(".*$A_535_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7966 */     println(".*$A_535_End");
/*      */     
/* 7968 */     println(".*$A_536_Begin");
/* 7969 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 7970 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5526", " ", true);
/* 7971 */     this.iColWidths = new int[] { 69 };
/* 7972 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 7973 */     resetPrintvars();
/* 7974 */     println(".*$A_536_End");
/*      */     
/* 7976 */     println(".*$A_538_Begin");
/*      */     
/* 7978 */     println(".*$A_538_End");
/*      */     
/* 7980 */     println(".*$A_543_Begin");
/*      */     
/* 7982 */     println(".*$A_543_End");
/*      */     
/* 7984 */     println(".*$A_545_Begin");
/*      */     
/* 7986 */     println(".*$A_545_End");
/*      */     
/* 7988 */     println(".*$A_546_Begin");
/* 7989 */     println(".*$A_546_End");
/*      */     
/* 7991 */     println(".*$A_570_Begin");
/*      */     
/* 7993 */     println(".*$A_570_End");
/*      */     
/* 7995 */     println(".*$A_573_Begin");
/*      */     
/* 7997 */     println(".*$A_573_End");
/*      */     
/* 7999 */     println(".*$A_576_Begin");
/*      */     
/* 8001 */     println(".*$A_576_End");
/*      */     
/* 8003 */     println(".*$A_582_Begin");
/*      */     
/* 8005 */     println(".*$A_582_End");
/*      */     
/* 8007 */     println(".*$A_585_Begin");
/*      */     
/* 8009 */     println(".*$A_585_End");
/*      */     
/* 8011 */     println(".*$A_594_Begin");
/*      */     
/* 8013 */     println(".*$A_594_End");
/*      */     
/* 8015 */     println(".*$A_595_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 8026 */     println(".*$A_595_End");
/* 8027 */     println(".*$A_597_Begin");
/*      */     
/* 8029 */     println(".*$A_597_End");
/*      */     
/* 8031 */     println(".*$A_599_Begin");
/*      */     
/* 8033 */     println(".*$A_599_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo600() {
/* 8042 */     println(".*$A_600_Begin");
/*      */     
/* 8044 */     println(".*$A_600_End");
/*      */     
/* 8046 */     println(".*$A_604_Begin");
/*      */     
/* 8048 */     println(".*$A_604_End");
/*      */     
/* 8050 */     println(".*$A_607_Begin");
/*      */     
/* 8052 */     println(".*$A_607_End");
/*      */     
/* 8054 */     println(".*$A_608_Begin");
/*      */     
/* 8056 */     println(".*$A_608_End");
/*      */     
/* 8058 */     println(".*$A_620_Begin");
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
/*      */   private void processLongTo700() {
/* 8137 */     println(".*$A_702_Begin");
/*      */     
/* 8139 */     println(".*$A_702_End");
/*      */     
/* 8141 */     println(".*$A_715_Begin");
/*      */     
/* 8143 */     println(".*$A_715_End");
/*      */     
/* 8145 */     println(".*$A_773_Begin");
/*      */     
/* 8147 */     println(".*$A_773_End");
/*      */     
/* 8149 */     println(".*$A_780_Begin");
/*      */     
/* 8151 */     println(".*$A_780_End");
/*      */     
/* 8153 */     println(".*$A_782_Begin");
/* 8154 */     println(".*$A_782_End");
/*      */     
/* 8156 */     println(".*$A_793_Begin");
/* 8157 */     println(".*$A_793_End");
/*      */     
/* 8159 */     println(".*$A_794_Begin");
/* 8160 */     println(".*$A_794_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo800() {
/* 8169 */     println(".*$A_800_Begin");
/* 8170 */     println(".*$A_800_End");
/*      */     
/* 8172 */     println(".*$A_802_Begin");
/* 8173 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "MKTGSTRATEGY", " "));
/* 8174 */     prettyPrint(this.strCondition1, 69);
/* 8175 */     println(".*$A_802_End");
/*      */     
/* 8177 */     println(".*$A_803_Begin");
/*      */ 
/*      */     
/* 8180 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 8181 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 8182 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, (String[])null, (String[])null, true, true, "COMMERCIALOF");
/* 8183 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPUBS");
/* 8184 */     this.strFilterAttr = new String[] { "PUBTYPE" };
/* 8185 */     this.strFilterValue = new String[] { "5022" };
/* 8186 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "PUBLICATION");
/* 8187 */     this.strParamList1 = new String[] { "PUBTITLE", "ORDERPN" };
/* 8188 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 8189 */     this.iColWidths = new int[] { 45, 15 };
/* 8190 */     this.strHeader = new String[] { "Title", "Order No." };
/* 8191 */     if (this.vPrintDetails.size() > 0) {
/* 8192 */       println("The following materials will be sent to each business Partner");
/* 8193 */       println("");
/* 8194 */       println("Additional copies will be available for purchase from the IBM");
/* 8195 */       println("Fulfillment Center, P.O. Box 154, Dayton, OH 45401.");
/* 8196 */       println("");
/* 8197 */       println("");
/* 8198 */       println(":xmp.");
/* 8199 */       println(".kp off");
/* 8200 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 8201 */       resetPrintvars();
/* 8202 */       println(":exmp.");
/*      */     } 
/* 8204 */     println(".*$A_803_End");
/*      */     
/* 8206 */     println(".*$A_805_Begin");
/*      */     
/* 8208 */     println(".*$A_805_End");
/*      */     
/* 8210 */     println(".*$A_810_Begin");
/*      */     
/* 8212 */     this.strFilterAttr = new String[] { "EDUCATIONTYPE" };
/* 8213 */     this.strFilterValue = new String[] { "1141" };
/* 8214 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnEducation, this.strFilterAttr, this.strFilterValue, true, true, "EDUCATION");
/* 8215 */     this.strParamList1 = new String[] { "COURSECODE", "COURSENAME" };
/* 8216 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, false);
/* 8217 */     this.iColWidths = new int[] { 15, 15 };
/* 8218 */     this.strHeader = new String[] { "Course Code", "Code Name" };
/* 8219 */     this.bConditionOK = (this.vPrintDetails.size() > 0);
/* 8220 */     if (this.bConditionOK) {
/* 8221 */       println(":xmp.");
/* 8222 */       println(".kp off");
/* 8223 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/*      */     } 
/*      */     
/* 8226 */     resetPrintvars();
/* 8227 */     this.strParamList1 = new String[] { "TRAININGREQTS", "ENROLLMENT" };
/* 8228 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/*      */     
/* 8230 */     if (this.vPrintDetails.size() > 0 && !this.bConditionOK) {
/* 8231 */       println(":xmp.");
/* 8232 */       println(".kp off");
/*      */     } 
/* 8234 */     this.iColWidths = new int[] { 69 };
/* 8235 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8236 */     if (this.vPrintDetails.size() > 0 || this.bConditionOK) {
/* 8237 */       println(":exmp.");
/*      */     }
/* 8239 */     resetPrintvars();
/* 8240 */     println(".*$A_810_End");
/*      */     
/* 8242 */     println(".*$A_815_Begin");
/* 8243 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 8244 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5516", " ", true);
/* 8245 */     this.iColWidths = new int[] { 69 };
/* 8246 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8247 */     resetPrintvars();
/* 8248 */     println(".*$A_815_End");
/*      */     
/* 8250 */     println(".*$A_819_Begin");
/* 8251 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 8252 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5520", " ", true);
/* 8253 */     this.iColWidths = new int[] { 69 };
/* 8254 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8255 */     resetPrintvars();
/* 8256 */     println(".*$A_819_End");
/*      */     
/* 8258 */     println(".*$A_824_Begin");
/* 8259 */     println(".se PRODNUM = '" + getAttributeValue(this.eiAnnounce, "PRODNUM", "") + "'");
/* 8260 */     println(".*$A_824_End");
/*      */     
/* 8262 */     println(".*$A_825_Begin");
/* 8263 */     println(".*$A_825_End");
/*      */     
/* 8265 */     println(".*$A_826_Begin");
/* 8266 */     println(".*$A_826_End");
/*      */     
/* 8268 */     println(".*$A_830_Begin");
/*      */ 
/*      */ 
/*      */     
/* 8272 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 8273 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 8274 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, (String[])null, (String[])null, true, true, "COMMERCIALOF");
/* 8275 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFBPEXHIBIT");
/* 8276 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "BPEXHIBIT");
/* 8277 */     this.strParamList1 = new String[] { "MIDRANGEPRODCATTYPE" };
/* 8278 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", false, true);
/*      */     
/* 8280 */     this.iColWidths = new int[] { 55 };
/* 8281 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8282 */     println(".*$A_830_End");
/*      */     
/* 8284 */     println(".*$A_831_Begin");
/*      */ 
/*      */ 
/*      */     
/* 8288 */     this.strParamList1 = new String[] { "MIDRANGEPRODCAT" };
/* 8289 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 8290 */     this.iColWidths = new int[] { 15 };
/* 8291 */     this.strCondition1 = "";
/* 8292 */     if (this.vPrintDetails.size() > 0) {
/* 8293 */       this.strCondition1 = this.vPrintDetails.elementAt(0);
/*      */     }
/*      */     
/* 8296 */     println(".se IC = '" + this.strCondition1 + "'");
/* 8297 */     resetPrintvars();
/* 8298 */     println(".*$A_831_End");
/*      */     
/* 8300 */     println(".*$A_832_Begin");
/*      */     
/* 8302 */     println(".se IP = '" + getAttributeValue(this.eiAnnounce, "REMKTDISCPERCENT", "") + "'");
/*      */     
/* 8304 */     println(".*$A_832_End");
/*      */     
/* 8306 */     println(".*$A_839_Begin");
/*      */     
/* 8308 */     println(".*$A_839_End");
/*      */     
/* 8310 */     println(".*$A_840_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 8317 */     println(".*$A_840_End");
/*      */     
/* 8319 */     println(".*$A_841_Begin");
/*      */ 
/*      */     
/* 8322 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 8323 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 8324 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, (String[])null, (String[])null, true, true, "COMMERCIALOF");
/* 8325 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPRICE");
/* 8326 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 8327 */     this.strParamList1 = new String[] { "DISTSCHEDADISC" };
/* 8328 */     printValueListInVector(this.vReturnEntities3, this.strParamList1, " ", true, false);
/* 8329 */     this.iColWidths = new int[] { 68 };
/* 8330 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8331 */     resetPrintvars();
/* 8332 */     println(".*$A_841_End");
/*      */     
/* 8334 */     println(".*$A_842_Begin");
/*      */ 
/*      */     
/* 8337 */     this.strFilterAttr = new String[] { "OFFERINGTYPES", "OFFERINGTYPES", "OFFERINGTYPES" };
/* 8338 */     this.strFilterValue = new String[] { "2891", "2892", "2895" };
/* 8339 */     if (foundInEntity(this.eiAnnounce, this.strFilterAttr, this.strFilterValue, false)) {
/* 8340 */       println(getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "IRMRAGG", ""));
/*      */     }
/* 8342 */     println(".*$A_842_End");
/*      */     
/* 8344 */     println(".*$A_843_Begin");
/*      */ 
/*      */     
/* 8347 */     this.strFilterAttr = new String[] { "OFFERINGTYPES" };
/* 8348 */     this.strFilterValue = new String[] { "2898" };
/* 8349 */     if (foundInEntity(this.eiAnnounce, this.strFilterAttr, this.strFilterValue, false)) {
/* 8350 */       println(getAttributeValue(this.eiAnnounce, "IRMRAGG", ""));
/*      */     }
/* 8352 */     println(".*$A_843_End");
/*      */     
/* 8354 */     println(".*$A_844_Begin");
/* 8355 */     println(getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "MESORDERDISCOUNT", ""));
/* 8356 */     println(".*$A_844_End");
/*      */     
/* 8358 */     println(".*$A_845_Begin");
/*      */ 
/*      */     
/* 8361 */     this.strFilterAttr = new String[] { "OOFCAT" };
/* 8362 */     this.strFilterValue = new String[] { "Hardware" };
/* 8363 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpOrderOFAvail, this.strFilterAttr, this.strFilterValue, true, false, "ORDEROF");
/* 8364 */     logMessage("A_845_Begin");
/* 8365 */     displayContents(this.vReturnEntities1);
/* 8366 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 8367 */       this.strFilterAttr = new String[] { "OOFSUBCAT", "OOFSUBCAT", "OOFSUBCAT" };
/* 8368 */       this.strFilterValue = new String[] { "FeatureConvert", "ModelUpgrade", "ModelConvert" };
/* 8369 */       this.eiOrderOF = this.vReturnEntities1.elementAt(this.i);
/* 8370 */       if (foundInEntity(this.eiOrderOF, this.strFilterAttr, this.strFilterValue, false)) {
/* 8371 */         logMessage("A_845" + this.eiOrderOF.getEntityType() + ":" + this.eiOrderOF.getEntityID());
/* 8372 */         this.vReturnEntities2 = searchEntityItemLink(this.eiOrderOF, (String[])null, (String[])null, true, true, "OOFPRICE");
/* 8373 */         logMessage("A_845:OOFPRICE");
/* 8374 */         displayContents(this.vReturnEntities2);
/* 8375 */         this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "PRICEFININFO");
/* 8376 */         logMessage("A_845:PRICEFININFO");
/* 8377 */         displayContents(this.vReturnEntities3);
/* 8378 */         if (this.vReturnEntities3.size() > 0) {
/* 8379 */           this.eiPriceInfo = this.vReturnEntities3.elementAt(0);
/* 8380 */           this.vPrintDetails.add(getAttributeFlagEnabledValue(this.eiPriceInfo.getEntityType(), this.eiPriceInfo.getEntityID(), "MESREMARKETDISCOUNT", " ") + "%");
/*      */         } 
/*      */       } 
/*      */     } 
/* 8384 */     this.iColWidths = new int[] { 68 };
/* 8385 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8386 */     resetPrintvars();
/*      */     
/* 8388 */     println(".*$A_845_End");
/*      */     
/* 8390 */     println(".*$A_848_Begin");
/*      */ 
/*      */     
/* 8393 */     this.strFilterAttr = new String[] { "COFCAT", "COFSUBCAT", "COFGRP" };
/* 8394 */     this.strFilterValue = new String[] { "Hardware", "System", "Base" };
/* 8395 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnCofa, this.strFilterAttr, this.strFilterValue, true, true, "COMMERCIALOF");
/* 8396 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPRICE");
/* 8397 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 8398 */       this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 8399 */       this.eiPriceInfo = (EntityItem)this.eiNextItem.getDownLink(0);
/* 8400 */       this.eiCommOF = (EntityItem)this.eiNextItem.getUpLink(0);
/* 8401 */       this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MACHTYPE", " "));
/* 8402 */       this.vPrintDetails.add(getAttributeValue(this.eiCommOF, "MODEL", " "));
/* 8403 */       this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "MAXDISCOUNT", " "));
/*      */     } 
/* 8405 */     this.iColWidths = new int[] { 20, 20, 20 };
/* 8406 */     this.strHeader = new String[] { "Machine Type", "Model", "Discount % Gap" };
/* 8407 */     println(":xmp.");
/* 8408 */     println(".kp off");
/* 8409 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 8410 */     resetPrintvars();
/* 8411 */     println(":exmp.");
/* 8412 */     println(".*$A_848_End");
/*      */     
/* 8414 */     println(".*$A_851_Begin");
/* 8415 */     println(".*$A_851_End");
/*      */     
/* 8417 */     println(".*$A_852_Begin");
/* 8418 */     println(getAttributeShortFlagDesc(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "INVENTORYADJCAT", ""));
/* 8419 */     println(".*$A_852_End");
/*      */     
/* 8421 */     println(".*$A_856_Begin");
/* 8422 */     println(".*$A_856_End");
/*      */     
/* 8424 */     println(".*$A_858_Begin");
/*      */ 
/*      */ 
/*      */     
/* 8428 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "COFPUBS");
/* 8429 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 8430 */       this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 8431 */       this.eiPublication = (EntityItem)this.eiNextItem.getDownLink(0);
/* 8432 */       this.strCondition1 = getAttributeFlagEnabledValue(this.eiPublication.getEntityType(), this.eiPublication.getEntityID(), "PUBTYPE", " ");
/* 8433 */       if (flagvalueEquals(this.eiPublication.getEntityType(), this.eiPublication.getEntityID(), "PUBTYPE", "5022")) {
/* 8434 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem.getEntityType(), this.eiNextItem.getEntityID(), "PUBAVAIL", " "));
/* 8435 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBTITLE", " "));
/* 8436 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "ORDERPN", " "));
/* 8437 */         this.vPrintDetails.add(getAttributeValue(this.eiPublication, "PUBPN", " "));
/*      */       } 
/*      */     } 
/* 8440 */     this.strHeader = new String[] { "DATE", "TITLE", "ORDER NO.", "PART NO." };
/* 8441 */     this.iColWidths = new int[] { 12, 25, 15, 15 };
/* 8442 */     println(":xmp.");
/* 8443 */     println(".kp off");
/* 8444 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 8445 */     resetPrintvars();
/* 8446 */     println(":exmp.");
/* 8447 */     println(".*$A_858_End");
/*      */     
/* 8449 */     println(".*$A_859_Begin");
/* 8450 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/* 8451 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "5503", " ", true);
/* 8452 */     this.iColWidths = new int[] { 69 };
/* 8453 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8454 */     resetPrintvars();
/* 8455 */     println(".*$A_859_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo900() {
/* 8464 */     println(".*$A_905_Begin");
/* 8465 */     println(".*$A_905_End");
/*      */     
/* 8467 */     println(".*$A_916_Begin");
/*      */ 
/*      */     
/* 8470 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION" };
/* 8471 */     this.strFilterValue = new String[] { "1142" };
/*      */     
/* 8473 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8474 */     this.strFilterAttr = new String[] { "CHANGETYPE", "CHANGETYPE" };
/* 8475 */     this.strFilterValue = new String[] { "352", "353" };
/* 8476 */     this.strParamList1 = new String[] { "AFFECTHEADING", "CHANGETYPE", "EXISTINGTEXT", "NEWTEXT", "SEQNUMBER" };
/* 8477 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 8478 */       this.eiSalesmanchg = this.vReturnEntities1.elementAt(this.i);
/* 8479 */       if (foundInEntity(this.eiSalesmanchg, this.strFilterAttr, this.strFilterValue, false)) {
/* 8480 */         printValueListInItem(this.eiSalesmanchg, this.strParamList1, " ", true);
/*      */       }
/*      */     } 
/* 8483 */     this.strHeader = new String[] { "Heading", "Changetype", "Cur. Text", "NewText", "Sequence#" };
/* 8484 */     this.iColWidths = new int[] { 15, 10, 10, 10, 10 };
/* 8485 */     if (this.vPrintDetails.size() > 0) {
/* 8486 */       println(":xmp.");
/* 8487 */       println(".kp off");
/* 8488 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 8489 */       resetPrintvars();
/* 8490 */       println(":exmp.");
/*      */     } 
/* 8492 */     println(".*$A_916_End");
/*      */     
/* 8494 */     println(".*$A_920_Begin");
/*      */ 
/*      */     
/* 8497 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION" };
/* 8498 */     this.strFilterValue = new String[] { "1143" };
/*      */     
/* 8500 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8501 */     this.strParamList1 = new String[] { "REVISEDSALEMAN" };
/* 8502 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, false);
/* 8503 */     this.iColWidths = new int[] { 50 };
/* 8504 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8505 */     resetPrintvars();
/* 8506 */     println(".*$A_920_End");
/*      */     
/* 8508 */     println(".*$A_925_Begin");
/* 8509 */     println(getAttributeValue(this.eiAnnounce, "SALESMANGENREQ", ""));
/* 8510 */     println(".*$A_925_End");
/*      */     
/* 8512 */     println(".*$A_934_Begin");
/*      */ 
/*      */     
/* 8515 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8516 */     this.strFilterValue = new String[] { "1144", "1123" };
/* 8517 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8518 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8519 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8520 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8521 */     if (this.vPrintDetails.size() > 0) {
/* 8522 */       this.iColWidths = new int[] { 69 };
/* 8523 */       println(":xmp.");
/* 8524 */       println(".kp off");
/* 8525 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8526 */       resetPrintvars();
/* 8527 */       println(":exmp.");
/*      */     } 
/* 8529 */     println(".*$A_934_End");
/*      */     
/* 8531 */     println(".*$A_937_Begin");
/*      */ 
/*      */     
/* 8534 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8535 */     this.strFilterValue = new String[] { "1144", "1124" };
/* 8536 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8537 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8538 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8539 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8540 */     if (this.vPrintDetails.size() > 0) {
/* 8541 */       this.iColWidths = new int[] { 69 };
/* 8542 */       println(":xmp.");
/* 8543 */       println(".kp off");
/* 8544 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8545 */       resetPrintvars();
/* 8546 */       println(":exmp.");
/*      */     } 
/* 8548 */     println(".*$A_937_End");
/*      */     
/* 8550 */     println(".*$A_946_Begin");
/*      */ 
/*      */     
/* 8553 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8554 */     this.strFilterValue = new String[] { "1144", "1125" };
/* 8555 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8556 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8557 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8558 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8559 */     if (this.vPrintDetails.size() > 0) {
/* 8560 */       this.iColWidths = new int[] { 69 };
/* 8561 */       println(":xmp.");
/* 8562 */       println(".kp off");
/* 8563 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8564 */       resetPrintvars();
/* 8565 */       println(":exmp.");
/*      */     } 
/* 8567 */     println(".*$A_946_End");
/*      */     
/* 8569 */     println(".*$A_958_Begin");
/*      */ 
/*      */     
/* 8572 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8573 */     this.strFilterValue = new String[] { "1144", "1126" };
/* 8574 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8575 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8576 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8577 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8578 */     if (this.vPrintDetails.size() > 0) {
/* 8579 */       this.iColWidths = new int[] { 69 };
/* 8580 */       println(":xmp.");
/* 8581 */       println(".kp off");
/* 8582 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8583 */       resetPrintvars();
/* 8584 */       println(":exmp.");
/*      */     } 
/* 8586 */     println(".*$A_958_End");
/*      */     
/* 8588 */     println(".*$A_962_Begin");
/*      */     
/* 8590 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8591 */     this.strFilterValue = new String[] { "1144", "1127" };
/* 8592 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8593 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8594 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8595 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8596 */     this.iColWidths = new int[] { 69 };
/* 8597 */     if (this.vPrintDetails.size() > 0) {
/* 8598 */       println(":xmp.");
/* 8599 */       println(".kp off");
/* 8600 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8601 */       resetPrintvars();
/* 8602 */       println(":exmp.");
/*      */     } 
/* 8604 */     println(".*$A_962_End");
/*      */     
/* 8606 */     println(".*$A_964_Begin");
/*      */     
/* 8608 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8609 */     this.strFilterValue = new String[] { "1144", "1128" };
/* 8610 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8611 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8612 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8613 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8614 */     if (this.vPrintDetails.size() > 0) {
/* 8615 */       this.iColWidths = new int[] { 69 };
/* 8616 */       println(":xmp.");
/* 8617 */       println(".kp off");
/* 8618 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8619 */       resetPrintvars();
/* 8620 */       println(":exmp.");
/*      */     } 
/* 8622 */     println(".*$A_964_End");
/*      */     
/* 8624 */     println(".*$A_965_Begin");
/*      */     
/* 8626 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8627 */     this.strFilterValue = new String[] { "1144", "1121" };
/* 8628 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8629 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8630 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8631 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8632 */     if (this.vPrintDetails.size() > 0) {
/* 8633 */       this.iColWidths = new int[] { 69 };
/* 8634 */       println(":xmp.");
/* 8635 */       println(".kp off");
/* 8636 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8637 */       resetPrintvars();
/* 8638 */       println(":exmp.");
/*      */     } 
/* 8640 */     println(".*$A_965_End");
/*      */     
/* 8642 */     println(".*$A_967_Begin");
/*      */     
/* 8644 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8645 */     this.strFilterValue = new String[] { "1144", "1130" };
/* 8646 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8647 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8648 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8649 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8650 */     if (this.vPrintDetails.size() > 0) {
/* 8651 */       this.iColWidths = new int[] { 69 };
/* 8652 */       println(":xmp.");
/* 8653 */       println(".kp off");
/* 8654 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8655 */       resetPrintvars();
/* 8656 */       println(":exmp.");
/*      */     } 
/* 8658 */     println(".*$A_967_End");
/*      */     
/* 8660 */     println(".*$A_969_Begin");
/*      */     
/* 8662 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8663 */     this.strFilterValue = new String[] { "1144", "1131" };
/* 8664 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8665 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8666 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8667 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8668 */     if (this.vPrintDetails.size() > 0) {
/* 8669 */       this.iColWidths = new int[] { 69 };
/* 8670 */       println(":xmp.");
/* 8671 */       println(".kp off");
/* 8672 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8673 */       resetPrintvars();
/* 8674 */       println(":exmp.");
/*      */     } 
/* 8676 */     println(".*$A_969_End");
/*      */     
/* 8678 */     println(".*$A_978_Begin");
/*      */     
/* 8680 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8681 */     this.strFilterValue = new String[] { "1144", "1132" };
/* 8682 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8683 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8684 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8685 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8686 */     if (this.vPrintDetails.size() > 0) {
/* 8687 */       this.iColWidths = new int[] { 69 };
/* 8688 */       println(":xmp.");
/* 8689 */       println(".kp off");
/* 8690 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8691 */       resetPrintvars();
/* 8692 */       println(":exmp.");
/*      */     } 
/* 8694 */     println(".*$A_978_End");
/*      */     
/* 8696 */     println(".*$A_984_Begin");
/*      */     
/* 8698 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8699 */     this.strFilterValue = new String[] { "1144", "1133" };
/* 8700 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8701 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8702 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8703 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8704 */     if (this.vPrintDetails.size() > 0) {
/* 8705 */       this.iColWidths = new int[] { 69 };
/* 8706 */       println(":xmp.");
/* 8707 */       println(".kp off");
/* 8708 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8709 */       resetPrintvars();
/* 8710 */       println(":exmp.");
/*      */     } 
/* 8712 */     println(".*$A_984_End");
/*      */     
/* 8714 */     println(".*$A_985_Begin");
/*      */     
/* 8716 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8717 */     this.strFilterValue = new String[] { "1144", "1134" };
/* 8718 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8719 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8720 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8721 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8722 */     if (this.vPrintDetails.size() > 0) {
/* 8723 */       this.iColWidths = new int[] { 69 };
/* 8724 */       println(":xmp.");
/* 8725 */       println(".kp off");
/* 8726 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8727 */       resetPrintvars();
/* 8728 */       println(":exmp.");
/*      */     } 
/* 8730 */     println(".*$A_985_End");
/*      */     
/* 8732 */     println(".*$A_986_Begin");
/*      */     
/* 8734 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8735 */     this.strFilterValue = new String[] { "1144", "1135" };
/* 8736 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8737 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8738 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8739 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8740 */     if (this.vPrintDetails.size() > 0) {
/* 8741 */       this.iColWidths = new int[] { 69 };
/* 8742 */       println(":xmp.");
/* 8743 */       println(".kp off");
/* 8744 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8745 */       resetPrintvars();
/* 8746 */       println(":exmp.");
/*      */     } 
/* 8748 */     println(".*$A_986_End");
/*      */     
/* 8750 */     println(".*$A_987_Begin");
/*      */     
/* 8752 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8753 */     this.strFilterValue = new String[] { "1144", "1136" };
/* 8754 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8755 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8756 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8757 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8758 */     if (this.vPrintDetails.size() > 0) {
/* 8759 */       this.iColWidths = new int[] { 69 };
/* 8760 */       println(":xmp.");
/* 8761 */       println(".kp off");
/* 8762 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8763 */       resetPrintvars();
/* 8764 */       println(":exmp.");
/*      */     } 
/* 8766 */     println(".*$A_987_End");
/*      */     
/* 8768 */     println(".*$A_988_Begin");
/*      */     
/* 8770 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION", "AFFECTHEADING" };
/* 8771 */     this.strFilterValue = new String[] { "1144", "1137" };
/* 8772 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8773 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8774 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8775 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8776 */     if (this.vPrintDetails.size() > 0) {
/* 8777 */       this.iColWidths = new int[] { 69 };
/* 8778 */       println(":xmp.");
/* 8779 */       println(".kp off");
/* 8780 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8781 */       resetPrintvars();
/* 8782 */       println(":exmp.");
/*      */     } 
/* 8784 */     println(".*$A_988_End");
/*      */     
/* 8786 */     println(".*$A_989_Begin");
/*      */     
/* 8788 */     this.strFilterAttr = new String[] { "SMCHANGEOPTION" };
/* 8789 */     this.strFilterValue = new String[] { "1145" };
/* 8790 */     this.strParamList1 = new String[] { "NEWTEXT" };
/* 8791 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnSalesmanchg, this.strFilterAttr, this.strFilterValue, true, true, "SALESMANCHG");
/* 8792 */     this.vReturnEntities2 = sortEntities(this.vReturnEntities1, new String[] { "SEQNUMBER" });
/* 8793 */     printValueListInVector(this.vReturnEntities2, this.strParamList1, " ", true, true);
/* 8794 */     if (this.vPrintDetails.size() > 0) {
/* 8795 */       this.iColWidths = new int[] { 69 };
/* 8796 */       println(":xmp.");
/* 8797 */       println(".kp off");
/* 8798 */       printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8799 */       resetPrintvars();
/* 8800 */       println(":exmp.");
/*      */     } 
/* 8802 */     println(".*$A_989_End");
/*      */     
/* 8804 */     println(".*$A_990_Begin");
/* 8805 */     println(".*$A_990_End");
/*      */     
/* 8807 */     println(".*$A_991_Begin");
/* 8808 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "GENPRODSYSDESC", " "));
/* 8809 */     prettyPrint(this.strCondition1, 69);
/* 8810 */     println(".*$A_991_End");
/*      */     
/* 8812 */     println(".*$A_992_Begin");
/* 8813 */     this.strCondition1 = transformXML(getAttributeValue(this.eiAnnounce, "OPERPWRCONTROLS", " "));
/* 8814 */     prettyPrint(this.strCondition1, 69);
/* 8815 */     println(".*$A_992_End");
/*      */     
/* 8817 */     println(".*$A_993_Begin");
/* 8818 */     println(getAttributeValue(this.eiAnnounce, "DATASECCHAR", ""));
/* 8819 */     println(".*$A_993_End");
/*      */     
/* 8821 */     println(".*$A_994_Begin");
/* 8822 */     println(".*$A_994_End");
/*      */     
/* 8824 */     println(".*$A_995_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 8834 */     println(".*$A_995_End");
/*      */     
/* 8836 */     println(".*$A_996_Begin");
/*      */     
/* 8838 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnToDescArea, (String[])null, (String[])null, true, true, "ANNDESCAREA");
/* 8839 */     this.strParamList1 = new String[] { "WORLDTRADECONDSID" };
/* 8840 */     printValueListInVector(this.vReturnEntities1, this.strParamList1, " ", true, true);
/* 8841 */     this.iColWidths = new int[] { 15 };
/* 8842 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 8843 */     resetPrintvars();
/* 8844 */     println(".*$A_996_End");
/*      */     
/* 8846 */     println(".*$A_998_Begin");
/* 8847 */     println(".*$A_998_End");
/*      */   }
/*      */   
/*      */   private String transformXML(String paramString) {
/* 8851 */     ByteArrayOutputStream byteArrayOutputStream = null;
/* 8852 */     paramString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <!DOCTYPE eAnnounceData SYSTEM \"file:/" + this.DTDFILEPATH + "\" ><eAnnounceData>" + paramString + "</eAnnounceData>";
/*      */ 
/*      */     
/*      */     try {
/* 8856 */       StringReader stringReader = new StringReader(paramString);
/* 8857 */       StreamSource streamSource = new StreamSource(stringReader);
/* 8858 */       streamSource.setSystemId(paramString);
/*      */       
/* 8860 */       byteArrayOutputStream = (ByteArrayOutputStream)this.x2g.transform(streamSource);
/* 8861 */     } catch (Exception exception) {
/* 8862 */       println("Error: " + exception + "\n");
/* 8863 */       println("The following is the Offending xml");
/* 8864 */       println(paramString);
/* 8865 */       logError("Exception!" + exception.getMessage() + "\n:***:" + paramString + ":***:");
/*      */     } 
/*      */     
/* 8868 */     return (byteArrayOutputStream != null) ? byteArrayOutputStream.toString() : "";
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
/*      */   private EntityItem getLinkedEntityItem(EntityItem paramEntityItem, String[] paramArrayOfString, boolean paramBoolean) {
/* 8963 */     return getLinkedEntityItem(paramEntityItem, paramArrayOfString, paramBoolean, (String[])null, (String[])null);
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
/*      */   private EntityItem getLinkedEntityItem(EntityItem paramEntityItem, String[] paramArrayOfString1, boolean paramBoolean, String[] paramArrayOfString2, String[] paramArrayOfString3) {
/* 8982 */     logMessage("In getLinkedEntityItem :" + paramEntityItem.getKey());
/*      */     
/* 8984 */     EntityItem entityItem1 = paramEntityItem;
/* 8985 */     String str = paramArrayOfString1[paramArrayOfString1.length - 1];
/* 8986 */     EntityItem entityItem2 = null;
/* 8987 */     Vector<EntityItem> vector = new Vector();
/* 8988 */     logMessage("In getLinkedEntityItem 1");
/* 8989 */     for (byte b = 0; b < paramArrayOfString1.length; b++) {
/* 8990 */       if (paramArrayOfString2 == null) {
/* 8991 */         vector = searchEntityItemLink(entityItem1, (String[])null, (String[])null, true, paramBoolean, paramArrayOfString1[b]);
/*      */       }
/* 8993 */       else if (b == paramArrayOfString1.length - 1) {
/* 8994 */         logMessage("getLinkedEntityItem: matching from " + entityItem1.getKey() + " to target " + paramArrayOfString1[b] + " for values " + paramArrayOfString2.toString() + " == " + paramArrayOfString3.toString());
/* 8995 */         vector = searchEntityItemLink(entityItem1, paramArrayOfString2, paramArrayOfString3, true, paramBoolean, paramArrayOfString1[b]);
/*      */       } else {
/* 8997 */         vector = searchEntityItemLink(entityItem1, (String[])null, (String[])null, true, paramBoolean, paramArrayOfString1[b]);
/*      */       } 
/*      */       
/* 9000 */       logMessage("getLinkedEntityItem: Navigating " + entityItem1.getKey() + " to " + paramArrayOfString1[b]);
/* 9001 */       if (vector.size() > 0) {
/* 9002 */         entityItem1 = vector.elementAt(0);
/*      */       }
/*      */     } 
/* 9005 */     entityItem2 = entityItem1;
/*      */     
/* 9007 */     if (!entityItem2.getEntityType().equals(str)) {
/* 9008 */       logMessage("getLinkedEntityItem: could not find target ETYPE:" + str + " start " + paramEntityItem.getKey() + ":" + paramArrayOfString1.toString());
/*      */     }
/* 9010 */     return entityItem2;
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
/*      */   private EntityItem getUplinkedEntityItem(EntityItem paramEntityItem, String[] paramArrayOfString) {
/* 9049 */     logMessage("In getUplinkedEntityItem :" + paramEntityItem.getKey());
/* 9050 */     return getLinkedEntityItem(paramEntityItem, paramArrayOfString, false);
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
/*      */   public static String getVersion() {
/* 9076 */     return "$Id: RFAHWABR01.java,v 1.75 2008/03/19 19:38:57 wendy Exp $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 9084 */     return getVersion();
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\rfa\RFAHWABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */