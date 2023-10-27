/*      */ package COM.ibm.eannounce.abr.rfa;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.ReportFormatter;
/*      */ import COM.ibm.eannounce.abr.util.XMLtoGML;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.GeneralAreaGroup;
/*      */ import COM.ibm.eannounce.objects.GeneralAreaItem;
/*      */ import COM.ibm.eannounce.objects.GeneralAreaList;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.SortUtil;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringReader;
/*      */ import java.io.StringWriter;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RFA_IGSSVS
/*      */   extends PokBaseABR
/*      */ {
/*  519 */   private final String DTDFILEPATH = ABRServerProperties.getDTDFilePath("RFA_IGSSVS");
/*  520 */   private final String BREAK_INDICATOR = "$$BREAKHERE$$";
/*  521 */   private final String strWorldwideTag = "US, AP, CAN, EMEA, LA";
/*      */   
/*  523 */   EntityGroup grpAnnouncement = null;
/*  524 */   EntityItem eiAnnounce = null;
/*  525 */   EntityGroup grpAnnDeliv = null;
/*  526 */   EntityItem eiAnnDeliv = null;
/*  527 */   EntityGroup grpAnnPara = null;
/*  528 */   EntityItem eiAnnPara = null;
/*  529 */   EntityGroup grpAnnDepend = null;
/*  530 */   EntityItem eiAnnDepend = null;
/*  531 */   EntityGroup grpParamCode = null;
/*  532 */   EntityItem eiParamCode = null;
/*  533 */   EntityGroup grpDependCode = null;
/*  534 */   EntityItem eiDependCode = null;
/*  535 */   EntityGroup grpAnnProj = null;
/*  536 */   EntityItem eiAnnProj = null;
/*  537 */   EntityGroup grpErrataCause = null;
/*  538 */   EntityItem eiErrataCause = null;
/*  539 */   EntityGroup grpOrganUnit = null;
/*  540 */   EntityItem eiOrganUnit = null;
/*  541 */   EntityGroup grpOP = null;
/*  542 */   EntityItem eiOP = null;
/*  543 */   EntityGroup grpChannel = null;
/*  544 */   EntityItem eiChannel = null;
/*  545 */   EntityGroup grpPDSQuestions = null;
/*  546 */   EntityItem eiPDSQuestions = null;
/*  547 */   EntityGroup grpPriceInfo = null;
/*  548 */   EntityItem eiPriceInfo = null;
/*  549 */   EntityGroup grpCommOffInfo = null;
/*  550 */   EntityItem eiCommOffInfo = null;
/*  551 */   EntityGroup grpDerive = null;
/*  552 */   EntityItem eiDerive = null;
/*  553 */   EntityGroup grpAnnReview = null;
/*  554 */   EntityItem eiAnnReview = null;
/*  555 */   EntityGroup grpConfigurator = null;
/*  556 */   EntityItem eiConfigurator = null;
/*  557 */   EntityGroup grpAnnToConfig = null;
/*  558 */   EntityItem eiAnnToConfig = null;
/*  559 */   EntityGroup grpAnnToOrgUnit = null;
/*  560 */   EntityItem eiAnnToOrgUnit = null;
/*  561 */   EntityGroup grpAnnToOP = null;
/*  562 */   EntityItem eiAnnToOP = null;
/*  563 */   EntityGroup grpCofAvail = null;
/*  564 */   EntityItem eiCofAvail = null;
/*  565 */   EntityGroup egSof = null;
/*  566 */   EntityItem eiSof = null;
/*  567 */   EntityGroup grpPdsQuestions = null;
/*  568 */   EntityItem eiPdsQuestions = null;
/*  569 */   EntityGroup grpCommOfIvo = null;
/*  570 */   EntityItem eiCommOfIvo = null;
/*  571 */   EntityGroup grpCommOF = null;
/*  572 */   EntityItem eiCommOF = null;
/*  573 */   EntityGroup grpRelatedANN = null;
/*  574 */   EntityItem eiRelatedANN = null;
/*  575 */   EntityGroup grpGeneralArea = null;
/*  576 */   EntityItem eiGeneralArea = null;
/*  577 */   EntityGroup grpAvail = null;
/*  578 */   EntityItem eiAvail = null;
/*  579 */   EntityGroup grpOrderOF = null;
/*  580 */   EntityItem eiOrderOF = null;
/*  581 */   EntityGroup grpCrossSell = null;
/*  582 */   EntityItem eiCrossSell = null;
/*  583 */   EntityGroup grpUpSell = null;
/*  584 */   EntityItem eiUpSell = null;
/*  585 */   EntityGroup grpStdAmendText = null;
/*  586 */   EntityItem eiStdAmendText = null;
/*  587 */   EntityGroup grpCOFCrypto = null;
/*  588 */   EntityItem eiCOFCrypto = null;
/*  589 */   EntityGroup grpOOFCrypto = null;
/*  590 */   EntityItem eiOOFCrypto = null;
/*  591 */   EntityGroup grpCrypto = null;
/*  592 */   EntityItem eiCrypto = null;
/*  593 */   EntityGroup grpCofOrganUnit = null;
/*  594 */   EntityItem eiCofOrganUnit = null;
/*  595 */   EntityGroup grpIndividual = null;
/*  596 */   EntityItem eiIndividual = null;
/*  597 */   EntityGroup grpPublication = null;
/*  598 */   EntityItem eiPublication = null;
/*  599 */   EntityGroup grpEducation = null;
/*  600 */   EntityItem eiEducation = null;
/*  601 */   EntityGroup grpAnnEducation = null;
/*  602 */   EntityItem eiAnnEducation = null;
/*  603 */   EntityGroup grpIvocat = null;
/*  604 */   EntityItem eiIvocat = null;
/*  605 */   EntityGroup grpBoilPlateText = null;
/*  606 */   EntityItem eiBoilPlateText = null;
/*  607 */   EntityGroup grpCatIncl = null;
/*  608 */   EntityItem eiCatIncl = null;
/*  609 */   EntityGroup grpAlternateOF = null;
/*  610 */   EntityItem eiAlternateOF = null;
/*  611 */   EntityGroup grpCofBPExhibit = null;
/*  612 */   EntityItem eiCofBPExhibit = null;
/*  613 */   EntityGroup grpBPExhibit = null;
/*  614 */   EntityItem eiBPExhibit = null;
/*  615 */   EntityGroup grpCofPubs = null;
/*  616 */   EntityItem eiCofPubs = null;
/*  617 */   EntityGroup grpEnvirinfo = null;
/*  618 */   EntityItem eiEnvirinfo = null;
/*  619 */   EntityGroup grpAltEnvirinfo = null;
/*  620 */   EntityItem eiAltEnvirinfo = null;
/*  621 */   EntityGroup grpPackaging = null;
/*  622 */   EntityItem eiPackaging = null;
/*  623 */   EntityGroup grpSalesmanchg = null;
/*  624 */   EntityItem eiSalesmanchg = null;
/*  625 */   EntityGroup grpAnnSalesmanchg = null;
/*  626 */   EntityItem eiAnnSalesmanchg = null;
/*  627 */   EntityGroup grpOrderOFAvail = null;
/*  628 */   EntityItem eiOrderOFAvail = null;
/*  629 */   EntityGroup grpOrganUnitIndiv = null;
/*  630 */   EntityItem eiOrganUnitIndiv = null;
/*  631 */   EntityGroup grpAnnToAnnDeliv = null;
/*  632 */   EntityItem eiAnnToAnnDeliv = null;
/*  633 */   EntityGroup grpAnnDelReqTrans = null;
/*  634 */   EntityItem eiAnnDelReqTrans = null;
/*  635 */   EntityGroup grpEmeaTranslation = null;
/*  636 */   EntityItem eiEmeaTranslation = null;
/*  637 */   EntityGroup grpAnnToDescArea = null;
/*  638 */   EntityItem eiAnnToDescArea = null;
/*  639 */   EntityGroup grpCofPrice = null;
/*  640 */   EntityItem eiCofPrice = null;
/*  641 */   EntityGroup grpCofChannel = null;
/*  642 */   EntityItem eiCofChannel = null;
/*  643 */   EntityItem eiCofCofMgmtGrp = null;
/*  644 */   EntityItem eiCofOofMgmtGrp = null;
/*  645 */   EntityItem eiOofMemberCofOmg = null;
/*  646 */   EntityItem eiCofMemberCofOmg = null;
/*  647 */   EntityItem eiCofShip = null;
/*  648 */   EntityItem eiShipInfo = null;
/*  649 */   EntityItem eiAnnCofa = null;
/*  650 */   EntityGroup grpAnnCofa = null;
/*  651 */   EntityGroup grpAnnCofOffMgmtGrpa = null;
/*  652 */   EntityItem eiAnnCofOffMgmtGrpa = null;
/*  653 */   EntityGroup grpAnnAvail = null;
/*  654 */   EntityItem eiAnnAvail = null;
/*  655 */   EntityGroup grpOpsys = null;
/*  656 */   EntityItem eiOpsys = null;
/*  657 */   EntityGroup grpAnnOp = null;
/*  658 */   EntityItem eiAnnOp = null;
/*  659 */   EntityGroup egComponent = null;
/*  660 */   EntityItem eiComponent = null;
/*  661 */   EntityGroup egFeature = null;
/*  662 */   EntityItem eiFeature = null;
/*  663 */   EntityGroup egFinof = null;
/*  664 */   EntityItem eiFinof = null;
/*      */   
/*  666 */   ReportFormatter rfaReport = null;
/*  667 */   XMLtoGML x2g = new XMLtoGML();
/*      */   
/*  669 */   String strSplit = null;
/*  670 */   int intSplitLen = 0;
/*  671 */   int intSplitAt = 0;
/*  672 */   int iTemp = 0;
/*  673 */   int i = 0;
/*  674 */   int j = 0;
/*  675 */   int k = 0;
/*  676 */   int[] iColWidths = null;
/*  677 */   String strCondition1 = null;
/*  678 */   String strCondition2 = null;
/*  679 */   String strCondition3 = null;
/*  680 */   String strCondition4 = null;
/*  681 */   String strCondition5 = null;
/*  682 */   String strCondition6 = null;
/*      */   boolean bConditionOK = false;
/*      */   boolean bConditionOK1 = false;
/*      */   boolean bIsAnnITS = false;
/*  686 */   EntityItem eiNextItem = null;
/*  687 */   EntityItem eiNextItem1 = null;
/*  688 */   EntityItem eiNextItem2 = null;
/*  689 */   EntityItem eiNextItem3 = null;
/*  690 */   EntityGroup grpNextGroup = null;
/*  691 */   String[] strParamList1 = null;
/*  692 */   String[] strParamList2 = null;
/*  693 */   String[] strFilterAttr = null;
/*  694 */   String[] strFilterValue = null;
/*  695 */   String[] strFilterAttr1 = null;
/*  696 */   String[] strFilterValue1 = null;
/*  697 */   String[] strEntityTypes = null;
/*  698 */   Object[] strAnswers = null;
/*  699 */   String[] strFeatureToSof = null;
/*  700 */   String[] strCmptToSof = null;
/*  701 */   String[] strSofToCmpt = new String[] { "SOFRELCMPNT", "CMPNT" };
/*      */   
/*  703 */   String[] strHeader = null;
/*  704 */   String[] strFeatureToCmpt = new String[] { "CMPNTFEATURE", "CMPNT" };
/*      */   
/*  706 */   String m_strSpaces = "                                                                                          ";
/*  707 */   Vector vReturnEntities1 = new Vector();
/*  708 */   Vector vReturnEntities2 = new Vector();
/*  709 */   Vector vReturnEntities3 = new Vector();
/*  710 */   Vector vReturnEntities4 = new Vector();
/*  711 */   Vector vReturnEntities5 = new Vector();
/*  712 */   Vector vAvailEntities = new Vector();
/*  713 */   Vector vSofFrmSofAvail = new Vector();
/*  714 */   Vector vCmpntFrmCmpntAvail = new Vector();
/*  715 */   Vector vFeatureFrmFeatureAvail = new Vector();
/*  716 */   Vector vSofSortedbyMkt = new Vector();
/*  717 */   Vector vFeatureSortedbyMkt = new Vector();
/*  718 */   Vector vCmptSortedbyMkt = new Vector();
/*  719 */   Vector vAllSortedOfferings = new Vector();
/*      */   
/*  721 */   Vector vPrintDetails = new Vector();
/*  722 */   Vector vPrintDetails1 = new Vector();
/*  723 */   Vector vPrintDetails2 = new Vector();
/*  724 */   Vector vPrintDetails3 = new Vector();
/*  725 */   Hashtable hNoDupeLines = new Hashtable<>();
/*  726 */   Enumeration hKeys = null;
/*      */   
/*  728 */   StringTokenizer st = null;
/*  729 */   GeneralAreaList m_geList = null;
/*      */   
/*  731 */   SortUtil mySort = new SortUtil();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*      */     try {
/*  738 */       start_ABRBuild();
/*  739 */       setReturnCode(0);
/*      */       
/*  741 */       logMessage("VE Dump********************");
/*  742 */       logMessage(this.m_elist.dump(false));
/*  743 */       logMessage("End VE Dump********************");
/*      */       
/*  745 */       this.m_geList = new GeneralAreaList(getDatabase(), getProfile());
/*  746 */       this.m_geList.buildTree();
/*      */       
/*  748 */       logMessage("Starting General area dump*********************************");
/*  749 */       logMessage(this.m_geList.dump(false));
/*  750 */       logMessage("Ending dump***********************************");
/*      */       
/*  752 */       this.grpAnnouncement = this.m_elist.getParentEntityGroup();
/*  753 */       logMessage("************Root Entity Type and id " + getEntityType() + ":" + getEntityID());
/*  754 */       this.vReturnEntities1 = null;
/*  755 */       this.vReturnEntities2 = null;
/*      */       
/*  757 */       if (this.grpAnnouncement == null) {
/*  758 */         logMessage("****************Announcement Not found ");
/*  759 */         setReturnCode(-1);
/*      */       }
/*      */       else {
/*      */         
/*  763 */         logMessage(this.grpAnnouncement.getEntityItemCount() + " Announcements found!");
/*  764 */         this.eiAnnounce = this.grpAnnouncement.getEntityItem(0);
/*      */ 
/*      */ 
/*      */         
/*  768 */         this.grpAnnDeliv = this.m_elist.getEntityGroup("ANNDELIVERABLE");
/*  769 */         this.eiAnnDeliv = null;
/*  770 */         if (this.grpAnnDeliv != null) {
/*  771 */           this.eiAnnDeliv = this.grpAnnDeliv.getEntityItem(0);
/*      */         } else {
/*      */           
/*  774 */           logMessage("**************ANNDELIVERABLE not found in list**");
/*      */         } 
/*  776 */         this.grpParamCode = this.m_elist.getEntityGroup("PARAMETERCODE");
/*  777 */         this.eiParamCode = null;
/*  778 */         if (this.grpParamCode != null) {
/*  779 */           this.eiParamCode = this.grpParamCode.getEntityItem(0);
/*      */         } else {
/*      */           
/*  782 */           logMessage("**************PARAMETERCODE not found in list**");
/*      */         } 
/*      */         
/*  785 */         this.grpDependCode = this.m_elist.getEntityGroup("DEPENDENCYCODE");
/*  786 */         this.eiDependCode = null;
/*  787 */         if (this.grpDependCode != null) {
/*  788 */           this.eiDependCode = this.grpDependCode.getEntityItem(0);
/*      */         } else {
/*      */           
/*  791 */           logMessage("**************DEPENDENCYCODE not found in list**");
/*      */         } 
/*      */         
/*  794 */         this.grpAnnProj = this.m_elist.getEntityGroup("ANNPROJ");
/*  795 */         this.eiAnnProj = null;
/*  796 */         if (this.grpAnnProj != null) {
/*  797 */           this.eiAnnProj = this.grpAnnProj.getEntityItem(0);
/*      */         } else {
/*      */           
/*  800 */           logMessage("**************ANNPROJ not found in list**");
/*      */         } 
/*      */         
/*  803 */         this.grpErrataCause = this.m_elist.getEntityGroup("ERRATACAUSE");
/*  804 */         this.eiErrataCause = null;
/*  805 */         if (this.grpErrataCause != null) {
/*  806 */           this.eiErrataCause = this.grpErrataCause.getEntityItem(0);
/*      */         } else {
/*      */           
/*  809 */           logMessage("**************ERRATACAUSE not found in list**");
/*      */         } 
/*      */         
/*  812 */         this.grpOrganUnit = this.m_elist.getEntityGroup("ORGANUNIT");
/*  813 */         this.eiOrganUnit = null;
/*  814 */         if (this.grpOrganUnit != null) {
/*  815 */           this.eiOrganUnit = this.grpOrganUnit.getEntityItem(0);
/*      */         } else {
/*      */           
/*  818 */           logMessage("**************ORGANUNIT not found in list**");
/*      */         } 
/*      */         
/*  821 */         this.grpOP = this.m_elist.getEntityGroup("OP");
/*  822 */         this.eiOP = null;
/*  823 */         if (this.grpOP != null) {
/*  824 */           this.eiOP = this.grpOP.getEntityItem(0);
/*      */         } else {
/*      */           
/*  827 */           logMessage("**************OP not found in list**");
/*      */         } 
/*      */         
/*  830 */         this.grpChannel = this.m_elist.getEntityGroup("CHANNEL");
/*  831 */         this.eiChannel = null;
/*  832 */         if (this.grpChannel != null) {
/*  833 */           this.eiChannel = this.grpChannel.getEntityItem(0);
/*      */         } else {
/*      */           
/*  836 */           logMessage("**************CHANNEL not found in list**");
/*      */         } 
/*      */         
/*  839 */         this.grpPDSQuestions = this.m_elist.getEntityGroup("PDSQUESTIONS");
/*  840 */         this.eiPDSQuestions = null;
/*  841 */         if (this.grpPDSQuestions != null) {
/*  842 */           this.eiPDSQuestions = this.grpPDSQuestions.getEntityItem(0);
/*      */         } else {
/*      */           
/*  845 */           logMessage("**************PDSQUESTIONS not found in list**");
/*      */         } 
/*      */         
/*  848 */         this.grpPriceInfo = this.m_elist.getEntityGroup("PRICEFININFO");
/*  849 */         this.eiPriceInfo = null;
/*  850 */         if (this.grpPriceInfo != null) {
/*  851 */           this.eiPriceInfo = this.grpPriceInfo.getEntityItem(0);
/*      */         } else {
/*      */           
/*  854 */           logMessage("**************PRICEFININFO not found in list**");
/*      */         } 
/*      */         
/*  857 */         this.grpCommOffInfo = this.m_elist.getEntityGroup("COMMERCIALOFINFO");
/*  858 */         this.eiCommOffInfo = null;
/*  859 */         if (this.grpCommOffInfo != null) {
/*  860 */           this.eiCommOffInfo = this.grpCommOffInfo.getEntityItem(0);
/*      */         } else {
/*      */           
/*  863 */           logMessage("**************COMMERCIALOFINFO not found in list**");
/*      */         } 
/*      */         
/*  866 */         this.grpDerive = this.m_elist.getEntityGroup("DERIVE");
/*  867 */         this.eiDerive = null;
/*  868 */         if (this.grpDerive != null) {
/*  869 */           this.eiDerive = this.grpDerive.getEntityItem(0);
/*      */         } else {
/*      */           
/*  872 */           logMessage("**************DERIVE not found in list**");
/*      */         } 
/*      */         
/*  875 */         this.grpAnnReview = this.m_elist.getEntityGroup("ANNREVIEW");
/*  876 */         this.eiAnnReview = null;
/*  877 */         if (this.grpAnnReview != null) {
/*  878 */           this.eiAnnReview = this.grpAnnReview.getEntityItem(0);
/*      */         } else {
/*      */           
/*  881 */           logMessage("**************ANNREVIEW not found in list**");
/*      */         } 
/*      */         
/*  884 */         this.grpConfigurator = this.m_elist.getEntityGroup("CONFIGURATOR");
/*  885 */         this.eiConfigurator = null;
/*  886 */         if (this.grpConfigurator != null) {
/*  887 */           this.eiConfigurator = this.grpConfigurator.getEntityItem(0);
/*      */         } else {
/*      */           
/*  890 */           logMessage("**************CONFIGURATOR not found in list**");
/*      */         } 
/*      */         
/*  893 */         this.grpAnnToConfig = this.m_elist.getEntityGroup("ANNTOCONFIG");
/*  894 */         this.eiAnnToConfig = null;
/*  895 */         if (this.grpAnnToConfig != null) {
/*  896 */           this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(0);
/*      */         } else {
/*      */           
/*  899 */           logMessage("**************ANNTOCONFIG not found in list**");
/*      */         } 
/*      */         
/*  902 */         this.grpAnnToOrgUnit = this.m_elist.getEntityGroup("ANNORGANUNIT");
/*  903 */         this.eiAnnToOrgUnit = null;
/*  904 */         if (this.grpAnnToConfig != null) {
/*  905 */           this.eiAnnToOrgUnit = this.grpAnnToOrgUnit.getEntityItem(0);
/*      */         } else {
/*      */           
/*  908 */           logMessage("**************ANNORGANUNIT not found in list**");
/*      */         } 
/*      */         
/*  911 */         this.grpAnnToOP = this.m_elist.getEntityGroup("ANNOP");
/*  912 */         this.eiAnnToOP = null;
/*  913 */         if (this.grpAnnToOP != null) {
/*  914 */           this.eiAnnToOP = this.grpAnnToOP.getEntityItem(0);
/*      */         } else {
/*      */           
/*  917 */           logMessage("**************ANNOP not found in list**");
/*      */         } 
/*      */         
/*  920 */         this.grpCofAvail = this.m_elist.getEntityGroup("COMMERCIALOFAVAIL");
/*  921 */         this.eiCofAvail = null;
/*  922 */         if (this.grpCofAvail != null) {
/*  923 */           this.eiCofAvail = this.grpCofAvail.getEntityItem(0);
/*      */         } else {
/*      */           
/*  926 */           logMessage("**************COMMERCIALOFAVAIL not found in list**");
/*      */         } 
/*      */         
/*  929 */         this.grpPdsQuestions = this.m_elist.getEntityGroup("PDSQUESTIONS");
/*  930 */         this.eiPdsQuestions = null;
/*  931 */         if (this.grpPdsQuestions != null) {
/*  932 */           this.eiPdsQuestions = this.grpPdsQuestions.getEntityItem(0);
/*      */         } else {
/*      */           
/*  935 */           logMessage("**************PDSQUESTIONS not found in list**");
/*      */         } 
/*      */         
/*  938 */         this.grpCommOfIvo = this.m_elist.getEntityGroup("COMMERCIALOFIVO");
/*  939 */         this.eiCommOfIvo = null;
/*  940 */         if (this.grpCommOfIvo != null) {
/*  941 */           this.eiCommOfIvo = this.grpCommOfIvo.getEntityItem(0);
/*      */         } else {
/*      */           
/*  944 */           logMessage("**************COMMERCIALOFIVO not found in list**");
/*      */         } 
/*      */         
/*  947 */         this.grpCommOF = this.m_elist.getEntityGroup("COMMERCIALOF");
/*  948 */         this.eiCommOF = null;
/*  949 */         if (this.grpCommOF != null) {
/*  950 */           this.eiCommOF = this.grpCommOF.getEntityItem(0);
/*      */         } else {
/*      */           
/*  953 */           logMessage("**************COMMERCIALOF not found in list**");
/*      */         } 
/*      */         
/*  956 */         this.grpRelatedANN = this.m_elist.getEntityGroup("RELATEDANN");
/*  957 */         this.eiRelatedANN = null;
/*  958 */         if (this.grpRelatedANN != null) {
/*  959 */           this.eiRelatedANN = this.grpRelatedANN.getEntityItem(0);
/*      */         } else {
/*      */           
/*  962 */           logMessage("**************RELATEDANN not found in list**");
/*      */         } 
/*      */         
/*  965 */         this.grpGeneralArea = this.m_elist.getEntityGroup("GENERALAREA");
/*  966 */         this.eiGeneralArea = null;
/*  967 */         if (this.grpGeneralArea != null) {
/*  968 */           this.eiGeneralArea = this.grpGeneralArea.getEntityItem(0);
/*      */         } else {
/*      */           
/*  971 */           logMessage("**************GENERALAREA not found in list**");
/*      */         } 
/*      */         
/*  974 */         this.grpAvail = this.m_elist.getEntityGroup("AVAIL");
/*  975 */         this.eiAvail = null;
/*  976 */         if (this.grpAvail != null) {
/*  977 */           this.eiAvail = this.grpAvail.getEntityItem(0);
/*      */         } else {
/*      */           
/*  980 */           logMessage("**************AVAIL not found in list**");
/*      */         } 
/*      */         
/*  983 */         this.grpOrderOF = this.m_elist.getEntityGroup("ORDEROF");
/*  984 */         this.eiOrderOF = null;
/*  985 */         if (this.grpOrderOF != null) {
/*  986 */           this.eiOrderOF = this.grpOrderOF.getEntityItem(0);
/*      */         } else {
/*      */           
/*  989 */           logMessage("**************ORDEROF not found in list**");
/*      */         } 
/*      */         
/*  992 */         this.grpCrossSell = this.m_elist.getEntityGroup("CROSSSELL");
/*  993 */         this.eiCrossSell = null;
/*  994 */         if (this.grpCrossSell != null) {
/*  995 */           this.eiCrossSell = this.grpCrossSell.getEntityItem(0);
/*      */         } else {
/*      */           
/*  998 */           logMessage("**************CROSSSELL not found in list**");
/*      */         } 
/*      */         
/* 1001 */         this.grpUpSell = this.m_elist.getEntityGroup("UPSELL");
/* 1002 */         this.eiUpSell = null;
/* 1003 */         if (this.grpUpSell != null) {
/* 1004 */           this.eiUpSell = this.grpUpSell.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1007 */           logMessage("**************UPSELL not found in list**");
/*      */         } 
/*      */         
/* 1010 */         this.grpStdAmendText = this.m_elist.getEntityGroup("STANDAMENDTEXT");
/* 1011 */         this.eiStdAmendText = null;
/* 1012 */         if (this.grpStdAmendText != null) {
/* 1013 */           this.eiStdAmendText = this.grpStdAmendText.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1016 */           logMessage("**************STANDAMENDTEXT not found in list**");
/*      */         } 
/*      */         
/* 1019 */         this.grpCOFCrypto = this.m_elist.getEntityGroup("COFCRYPTO");
/* 1020 */         this.eiCOFCrypto = null;
/* 1021 */         if (this.grpCOFCrypto != null) {
/* 1022 */           this.eiCOFCrypto = this.grpCOFCrypto.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1025 */           logMessage("**************COFCRYPTO not found in list**");
/*      */         } 
/*      */         
/* 1028 */         this.grpOOFCrypto = this.m_elist.getEntityGroup("OOFCRYPTO");
/* 1029 */         this.eiOOFCrypto = null;
/* 1030 */         if (this.grpOOFCrypto != null) {
/* 1031 */           this.eiOOFCrypto = this.grpOOFCrypto.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1034 */           logMessage("**************OOFCRYPTO not found in list**");
/*      */         } 
/*      */         
/* 1037 */         this.grpCrypto = this.m_elist.getEntityGroup("CRYPTO");
/* 1038 */         this.eiCrypto = null;
/* 1039 */         if (this.grpCrypto != null) {
/* 1040 */           this.eiCrypto = this.grpCrypto.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1043 */           logMessage("**************CRYPTO not found in list**");
/*      */         } 
/*      */         
/* 1046 */         this.grpCofOrganUnit = this.m_elist.getEntityGroup("COFORGANUNIT");
/* 1047 */         this.eiCofOrganUnit = null;
/* 1048 */         if (this.grpCofOrganUnit != null) {
/* 1049 */           this.eiCofOrganUnit = this.grpCofOrganUnit.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1052 */           logMessage("**************COFORGANUNIT not found in list**");
/*      */         } 
/*      */         
/* 1055 */         this.grpIndividual = this.m_elist.getEntityGroup("INDIVIDUAL");
/* 1056 */         this.eiIndividual = null;
/* 1057 */         if (this.grpIndividual != null) {
/* 1058 */           this.eiIndividual = this.grpIndividual.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1061 */           logMessage("**************INDIVIDUAL not found in list**");
/*      */         } 
/*      */         
/* 1064 */         this.grpPublication = this.m_elist.getEntityGroup("PUBLICATION");
/* 1065 */         this.eiPublication = null;
/* 1066 */         if (this.grpPublication != null) {
/* 1067 */           this.eiPublication = this.grpPublication.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1070 */           logMessage("**************PUBLICATION not found in list**");
/*      */         } 
/*      */         
/* 1073 */         this.grpEducation = this.m_elist.getEntityGroup("EDUCATION");
/* 1074 */         this.eiEducation = null;
/* 1075 */         if (this.grpEducation != null) {
/* 1076 */           this.eiEducation = this.grpEducation.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1079 */           logMessage("**************EDUCATION not found in list**");
/*      */         } 
/*      */         
/* 1082 */         this.grpAnnEducation = this.m_elist.getEntityGroup("ANNEDUCATION");
/* 1083 */         this.eiAnnEducation = null;
/* 1084 */         if (this.grpAnnEducation != null) {
/* 1085 */           this.eiAnnEducation = this.grpAnnEducation.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1088 */           logMessage("**************ANNEDUCATION not found in list**");
/*      */         } 
/*      */         
/* 1091 */         this.grpIvocat = this.m_elist.getEntityGroup("IVOCAT");
/* 1092 */         this.eiIvocat = null;
/* 1093 */         if (this.grpIvocat != null) {
/* 1094 */           this.eiIvocat = this.grpIvocat.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1097 */           logMessage("**************IVOCAT not found in list**");
/*      */         } 
/*      */         
/* 1100 */         this.grpBoilPlateText = this.m_elist.getEntityGroup("BOILPLATETEXT");
/* 1101 */         this.eiBoilPlateText = null;
/* 1102 */         if (this.grpBoilPlateText != null) {
/* 1103 */           this.eiBoilPlateText = this.grpBoilPlateText.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1106 */           logMessage("**************BOILPLATETEXT not found in list**");
/*      */         } 
/*      */         
/* 1109 */         this.grpCatIncl = this.m_elist.getEntityGroup("CATINCL");
/* 1110 */         this.eiCatIncl = null;
/* 1111 */         if (this.grpCatIncl != null) {
/* 1112 */           this.eiCatIncl = this.grpCatIncl.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1115 */           logMessage("**************CATINCL not found in list**");
/*      */         } 
/*      */         
/* 1118 */         this.grpAlternateOF = this.m_elist.getEntityGroup("ALTERNATEOF");
/* 1119 */         this.eiAlternateOF = null;
/* 1120 */         if (this.grpAlternateOF != null) {
/* 1121 */           this.eiAlternateOF = this.grpAlternateOF.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1124 */           logMessage("**************ALTERNATEOF not found in list**");
/*      */         } 
/*      */         
/* 1127 */         this.grpCofBPExhibit = this.m_elist.getEntityGroup("COFBPEXHIBIT");
/* 1128 */         this.eiCofBPExhibit = null;
/* 1129 */         if (this.grpCofBPExhibit != null) {
/* 1130 */           this.eiCofBPExhibit = this.grpCofBPExhibit.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1133 */           logMessage("**************COFBPEXHIBIT not found in list**");
/*      */         } 
/*      */         
/* 1136 */         this.grpBPExhibit = this.m_elist.getEntityGroup("BPEXHIBIT");
/* 1137 */         this.eiBPExhibit = null;
/* 1138 */         if (this.grpBPExhibit != null) {
/* 1139 */           this.eiBPExhibit = this.grpBPExhibit.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1142 */           logMessage("**************BPEXHIBIT not found in list**");
/*      */         } 
/*      */         
/* 1145 */         this.grpCofPubs = this.m_elist.getEntityGroup("COFPUBS");
/* 1146 */         this.eiCofPubs = null;
/* 1147 */         if (this.grpCofPubs != null) {
/* 1148 */           this.eiCofPubs = this.grpCofPubs.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1151 */           logMessage("**************COFPUBS not found in list**");
/*      */         } 
/*      */         
/* 1154 */         this.grpEnvirinfo = this.m_elist.getEntityGroup("ENVIRINFO");
/* 1155 */         this.eiEnvirinfo = null;
/* 1156 */         if (this.grpEnvirinfo != null) {
/* 1157 */           this.eiEnvirinfo = this.grpEnvirinfo.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1160 */           logMessage("**************ENVIRINFO not found in list**");
/*      */         } 
/*      */         
/* 1163 */         this.grpAltEnvirinfo = this.m_elist.getEntityGroup("ALTDEPENENVIRINFO");
/* 1164 */         this.eiAltEnvirinfo = null;
/* 1165 */         if (this.grpAltEnvirinfo != null) {
/* 1166 */           this.eiAltEnvirinfo = this.grpAltEnvirinfo.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1169 */           logMessage("**************ALTDEPENENVIRINFO not found in list**");
/*      */         } 
/*      */         
/* 1172 */         this.grpPackaging = this.m_elist.getEntityGroup("PACKAGING");
/* 1173 */         this.eiPackaging = null;
/* 1174 */         if (this.grpPackaging != null) {
/* 1175 */           this.eiPackaging = this.grpPackaging.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1178 */           logMessage("**************PACKAGING not found in list**");
/*      */         } 
/*      */         
/* 1181 */         this.grpAnnSalesmanchg = this.m_elist.getEntityGroup("ANNSALESMANCHG");
/* 1182 */         this.eiAnnSalesmanchg = null;
/* 1183 */         if (this.grpAnnSalesmanchg != null) {
/* 1184 */           this.eiAnnSalesmanchg = this.grpAnnSalesmanchg.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1187 */           logMessage("**************ANNSALESMANCHG not found in list**");
/*      */         } 
/* 1189 */         this.grpSalesmanchg = this.m_elist.getEntityGroup("SALESMANCHG");
/* 1190 */         this.eiSalesmanchg = null;
/* 1191 */         if (this.grpSalesmanchg != null) {
/* 1192 */           this.eiSalesmanchg = this.grpSalesmanchg.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1195 */           logMessage("**************SALESMANCHG not found in list**");
/*      */         } 
/*      */         
/* 1198 */         this.grpOrderOFAvail = this.m_elist.getEntityGroup("OOFAVAIL");
/* 1199 */         this.eiOrderOFAvail = null;
/* 1200 */         if (this.grpOrderOFAvail != null) {
/* 1201 */           this.eiOrderOFAvail = this.grpOrderOFAvail.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1204 */           logMessage("**************OOFAVAIL not found in list**");
/*      */         } 
/*      */         
/* 1207 */         this.grpAnnToAnnDeliv = this.m_elist.getEntityGroup("ANNTOANNDELIVER");
/* 1208 */         this.eiAnnToAnnDeliv = null;
/* 1209 */         if (this.grpAnnToAnnDeliv != null) {
/* 1210 */           this.eiAnnToAnnDeliv = this.grpAnnToAnnDeliv.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1213 */           logMessage("**************ANNTOANNDELIVER not found in list**");
/*      */         } 
/*      */         
/* 1216 */         this.grpAnnDelReqTrans = this.m_elist.getEntityGroup("ANNDELREQTRANS");
/* 1217 */         this.eiAnnDelReqTrans = null;
/* 1218 */         if (this.grpAnnDelReqTrans != null) {
/* 1219 */           this.eiAnnDelReqTrans = this.grpAnnDelReqTrans.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1222 */           logMessage("**************ANNDELREQTRANS not found in list**");
/*      */         } 
/*      */         
/* 1225 */         this.grpAnnToDescArea = this.m_elist.getEntityGroup("ANNTODESCAREA");
/* 1226 */         this.eiAnnToDescArea = null;
/* 1227 */         if (this.grpAnnToDescArea != null) {
/* 1228 */           this.eiAnnToDescArea = this.grpAnnToDescArea.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1231 */           logMessage("**************ANNTODESCAREA not found in list**");
/*      */         } 
/*      */         
/* 1234 */         this.grpCofPrice = this.m_elist.getEntityGroup("COFPRICE");
/* 1235 */         this.eiCofPrice = null;
/* 1236 */         if (this.grpCofPrice != null) {
/* 1237 */           this.eiCofPrice = this.grpCofPrice.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1240 */           logMessage("**************COFPRICE not found in list**");
/*      */         } 
/*      */         
/* 1243 */         this.grpAnnPara = this.m_elist.getEntityGroup("ANNPARAA");
/* 1244 */         this.eiAnnPara = null;
/* 1245 */         if (this.grpAnnPara != null) {
/* 1246 */           this.eiAnnPara = this.grpAnnPara.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1249 */           logMessage("**************ANNPARAA not found in list**");
/*      */         } 
/*      */         
/* 1252 */         this.grpDependCode = this.m_elist.getEntityGroup("ANNDEPA");
/* 1253 */         this.eiDependCode = null;
/* 1254 */         if (this.grpDependCode != null) {
/* 1255 */           this.eiDependCode = this.grpDependCode.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1258 */           logMessage("**************ANNDEPA not found in list**");
/*      */         } 
/*      */         
/* 1261 */         this.grpCofChannel = this.m_elist.getEntityGroup("COFCHANNEL");
/* 1262 */         this.eiCofChannel = null;
/* 1263 */         if (this.grpCofChannel != null) {
/* 1264 */           this.eiCofChannel = this.grpCofChannel.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1267 */           logMessage("**************COFCHANNEL not found in list**");
/*      */         } 
/*      */         
/* 1270 */         this.grpAnnCofa = this.m_elist.getEntityGroup("ANNCOFA");
/* 1271 */         this.eiAnnCofa = null;
/* 1272 */         if (this.grpAnnCofa != null) {
/* 1273 */           this.eiAnnCofa = this.grpAnnCofa.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1276 */           logMessage("**************ANNCOFA not found in list**");
/*      */         } 
/*      */         
/* 1279 */         this.grpAnnCofOffMgmtGrpa = this.m_elist.getEntityGroup("ANNCOFOOFMGMTGRPA");
/* 1280 */         this.eiAnnCofOffMgmtGrpa = null;
/* 1281 */         if (this.grpAnnCofOffMgmtGrpa != null) {
/* 1282 */           this.eiAnnCofOffMgmtGrpa = this.grpAnnCofOffMgmtGrpa.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1285 */           logMessage("**************ANNCOFOOFMGMTGRPA not found in list**");
/*      */         } 
/*      */         
/* 1288 */         this.grpAnnAvail = this.m_elist.getEntityGroup("ANNAVAILA");
/* 1289 */         this.eiAnnAvail = null;
/* 1290 */         if (this.grpAnnAvail != null) {
/* 1291 */           this.eiAnnAvail = this.grpAnnAvail.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1294 */           logMessage("**************ANNAVAILA not found in list**");
/*      */         } 
/*      */         
/* 1297 */         this.grpAnnOp = this.m_elist.getEntityGroup("ANNOP");
/* 1298 */         this.eiAnnOp = null;
/* 1299 */         if (this.grpAnnOp != null) {
/* 1300 */           this.eiAnnOp = this.grpAnnOp.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1303 */           logMessage("**************ANNOP not found in list**");
/*      */         } 
/*      */         
/* 1306 */         this.egComponent = this.m_elist.getEntityGroup("CMPNT");
/* 1307 */         this.eiComponent = null;
/* 1308 */         if (this.egComponent != null) {
/* 1309 */           this.eiComponent = this.egComponent.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1312 */           logMessage("**************CMPNT not found in list**");
/*      */         } 
/*      */         
/* 1315 */         this.egSof = this.m_elist.getEntityGroup("SOF");
/* 1316 */         this.eiSof = null;
/* 1317 */         if (this.egSof != null) {
/* 1318 */           this.eiSof = this.egSof.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1321 */           logMessage("**************SOF not found in list**");
/*      */         } 
/*      */         
/* 1324 */         this.egFeature = this.m_elist.getEntityGroup("FEATURE");
/* 1325 */         this.eiFeature = null;
/* 1326 */         if (this.egFeature != null) {
/* 1327 */           this.eiFeature = this.egFeature.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1330 */           logMessage("**************FEATURE not found in list**");
/*      */         } 
/*      */         
/* 1333 */         this.egFinof = this.m_elist.getEntityGroup("FINOF");
/* 1334 */         this.eiFinof = null;
/* 1335 */         if (this.egFinof != null) {
/* 1336 */           this.eiFinof = this.egFinof.getEntityItem(0);
/*      */         } else {
/*      */           
/* 1339 */           logMessage("**************FINOF not found in list**");
/*      */         } 
/*      */         
/* 1342 */         this.rfaReport = new ReportFormatter();
/* 1343 */         this.rfaReport.setABRItem(getABRItem());
/*      */ 
/*      */         
/* 1346 */         DateFormat dateFormat = DateFormat.getDateInstance();
/* 1347 */         dateFormat.setCalendar(Calendar.getInstance());
/* 1348 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 1349 */         simpleDateFormat.setCalendar(Calendar.getInstance());
/* 1350 */         String str = simpleDateFormat.format(new Date());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1355 */         println(".*$REQUESTTYPE_BEGIN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1372 */         this.strCondition1 = getAttributeFlagEnabledValue(this.eiAnnounce, "LOB");
/* 1373 */         logMessage("**************Announcement LOB is " + this.strCondition1);
/* 1374 */         this.bIsAnnITS = (this.strCondition1 != null) ? this.strCondition1.equals("101") : false;
/*      */ 
/*      */         
/* 1377 */         this.strCondition1 = getAttributeFlagEnabledValue(this.eiAnnounce, "DISTRTYPE");
/* 1378 */         if (!this.strCondition1.equals("720")) {
/* 1379 */           this.strCondition1 = getAttributeValue(this.eiAnnounce, "SENDTOMAINMENU", "NoValuefoundSENDTOMAINMENU");
/*      */           
/* 1381 */           if (this.strCondition1.equals("Yes")) {
/*      */             
/* 1383 */             this.strCondition1 = getAttributeFlagEnabledValue(this.eiAnnounce, "ANCYCLESTATUS");
/* 1384 */             this.strCondition1 = this.strCondition1.substring((this.strCondition1.indexOf("|") == -1) ? 0 : (this.strCondition1
/* 1385 */                 .indexOf("|") + 1));
/* 1386 */             this.strCondition2 = getAttributeValue(this.eiAnnounce, "ANNDATE", "");
/* 1387 */             this.i = Integer.valueOf(this.strCondition1).intValue();
/* 1388 */             this.bConditionOK = true;
/* 1389 */             switch (this.i) {
/*      */               case 112:
/*      */               case 114:
/*      */               case 115:
/* 1393 */                 this.strCondition3 = "preEdit";
/*      */                 break;
/*      */               case 116:
/*      */               case 117:
/* 1397 */                 if (this.strCondition2.compareTo(str) > 0) {
/* 1398 */                   this.strCondition3 = "final";
/*      */                   break;
/*      */                 } 
/* 1401 */                 this.strCondition3 = "correction";
/*      */                 break;
/*      */               
/*      */               default:
/* 1405 */                 println("ANCYCLESTATUS returned unexpected flag value");
/*      */                 break;
/*      */             } 
/*      */           
/*      */           } else {
/* 1410 */             this.strCondition3 = "return";
/*      */           } 
/*      */         } else {
/*      */           
/* 1414 */           this.strCondition3 = "finalEdit";
/*      */         } 
/* 1416 */         println(this.strCondition3);
/* 1417 */         println(".*$REQUESTTYPE_END");
/* 1418 */         println(".*$USERIDS_BEGIN");
/* 1419 */         println("'" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERTOKEN", "") : "'") + "'");
/* 1420 */         println(".*$USERIDS_END");
/* 1421 */         println(".*$VARIABLES_BEGIN");
/* 1422 */         processShortAnswers();
/* 1423 */         println(".*$ANSWERS_BEGIN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1430 */         processLongTo100();
/* 1431 */         processLongTo200();
/* 1432 */         processLongTo300();
/* 1433 */         processLongTo400();
/* 1434 */         processLongTo500();
/* 1435 */         processLongTo600();
/* 1436 */         processLongTo700();
/* 1437 */         processLongTo800();
/* 1438 */         processLongTo900();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1444 */         println(".*$ANSWERS_END");
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1449 */     catch (Exception exception) {
/*      */       
/* 1451 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 1452 */       println("" + exception);
/* 1453 */       logError(exception, "");
/* 1454 */       StringWriter stringWriter = new StringWriter();
/*      */       
/* 1456 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/* 1458 */       String str3 = stringWriter.toString();
/*      */       
/* 1460 */       println(str3);
/*      */ 
/*      */       
/* 1463 */       if (getABRReturnCode() != -2) {
/* 1464 */         setReturnCode(-3);
/*      */       }
/*      */     } finally {
/*      */       
/* 1468 */       String str1 = this.eiAnnounce.getNavAttrDescription();
/*      */       
/* 1470 */       if (str1.length() > 34) {
/* 1471 */         str1 = str1.substring(0, 34);
/*      */       }
/*      */       
/* 1474 */       String str2 = "Extract for " + str1 + " | " + ((getReturnCode() == 0) ? "Passed" : "Failed") + " | Complete";
/*      */       
/* 1476 */       setDGTitle(str2);
/*      */       
/* 1478 */       setDGRptName("RFA_IGSSVS");
/* 1479 */       setDGRptClass("RFA_IGSSVS");
/* 1480 */       printDGSubmitString();
/*      */ 
/*      */       
/* 1483 */       setDGString(getABRReturnCode());
/*      */       
/* 1485 */       if (!isReadOnly()) {
/* 1486 */         clearSoftLock();
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
/*      */   private String getLinkedEntityAttrValue(EntityItem paramEntityItem, String[] paramArrayOfString, String paramString, boolean paramBoolean) {
/* 1507 */     return getLinkedEntityAttrValue(paramEntityItem, paramArrayOfString, paramString, paramBoolean, (String[])null, (String[])null);
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
/*      */   private String getLinkedEntityAttrValue(EntityItem paramEntityItem, String[] paramArrayOfString1, String paramString, boolean paramBoolean, String[] paramArrayOfString2, String[] paramArrayOfString3) {
/* 1523 */     String str = null;
/* 1524 */     EntityItem entityItem = null;
/* 1525 */     if (paramArrayOfString2 == null) {
/* 1526 */       entityItem = getLinkedEntityItem(paramEntityItem, paramArrayOfString1, paramBoolean);
/*      */     } else {
/*      */       
/* 1529 */       entityItem = getLinkedEntityItem(paramEntityItem, paramArrayOfString1, paramBoolean, paramArrayOfString2, paramArrayOfString3);
/*      */     } 
/* 1531 */     str = getAttributeValue(entityItem, paramString, " ");
/*      */     
/* 1533 */     return str;
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
/*      */   private String getDownlinkedEntityAttrValue(EntityItem paramEntityItem, String[] paramArrayOfString, String paramString) {
/* 1545 */     return getLinkedEntityAttrValue(paramEntityItem, paramArrayOfString, paramString, true);
/*      */   }
/*      */ 
/*      */   
/*      */   private String getDownlinkedEntityAttrValue(EntityItem paramEntityItem, String[] paramArrayOfString1, String paramString, String[] paramArrayOfString2, String[] paramArrayOfString3) {
/* 1550 */     return getLinkedEntityAttrValue(paramEntityItem, paramArrayOfString1, paramString, true, paramArrayOfString2, paramArrayOfString3);
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
/*      */   private String getUplinkedEntityAttrValue(EntityItem paramEntityItem, String[] paramArrayOfString, String paramString) {
/* 1579 */     return getLinkedEntityAttrValue(paramEntityItem, paramArrayOfString, paramString, false);
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
/*      */   private EntityItem getLinkedEntityItem(EntityItem paramEntityItem, String[] paramArrayOfString, boolean paramBoolean) {
/* 1591 */     return getLinkedEntityItem(paramEntityItem, paramArrayOfString, paramBoolean, (String[])null, (String[])null);
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
/* 1610 */     D.ebug(4, "In getLinkedEntityItem :" + paramEntityItem.getKey());
/*      */     
/* 1612 */     EntityItem entityItem1 = paramEntityItem;
/* 1613 */     String str = paramArrayOfString1[paramArrayOfString1.length - 1];
/* 1614 */     EntityItem entityItem2 = null;
/* 1615 */     Vector<EntityItem> vector = new Vector();
/* 1616 */     D.ebug(4, "In getLinkedEntityItem 1");
/* 1617 */     for (byte b = 0; b < paramArrayOfString1.length; b++) {
/* 1618 */       if (paramArrayOfString2 == null) {
/* 1619 */         vector = searchEntityItemLink(entityItem1, (String[])null, (String[])null, true, paramBoolean, paramArrayOfString1[b]);
/*      */       
/*      */       }
/* 1622 */       else if (b == paramArrayOfString1.length - 1) {
/* 1623 */         D.ebug(4, "getLinkedEntityItem: matching from " + entityItem1
/* 1624 */             .getKey() + " to target " + paramArrayOfString1[b] + " for values " + paramArrayOfString2
/* 1625 */             .toString() + " == " + paramArrayOfString3.toString());
/* 1626 */         vector = searchEntityItemLink(entityItem1, paramArrayOfString2, paramArrayOfString3, true, paramBoolean, paramArrayOfString1[b]);
/*      */       }
/*      */       else {
/*      */         
/* 1630 */         vector = searchEntityItemLink(entityItem1, (String[])null, (String[])null, true, paramBoolean, paramArrayOfString1[b]);
/*      */       } 
/*      */       
/* 1633 */       D.ebug(4, "getLinkedEntityItem: Navigating " + entityItem1.getKey() + " to " + paramArrayOfString1[b]);
/* 1634 */       if (vector.size() > 0) {
/* 1635 */         entityItem1 = vector.elementAt(0);
/*      */       }
/*      */     } 
/* 1638 */     entityItem2 = entityItem1;
/*      */     
/* 1640 */     if (!entityItem2.getEntityType().equals(str)) {
/* 1641 */       D.ebug(4, "getLinkedEntityItem: could not find target ETYPE:" + str + " start " + paramEntityItem
/* 1642 */           .getKey() + ":" + paramArrayOfString1
/* 1643 */           .toString());
/* 1644 */       entityItem2 = null;
/*      */     } 
/* 1646 */     return entityItem2;
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
/*      */   private EntityItem getDownlinkedEntityItem(EntityItem paramEntityItem, String[] paramArrayOfString) {
/* 1671 */     return getLinkedEntityItem(paramEntityItem, paramArrayOfString, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem getUplinkedEntityItem(EntityItem paramEntityItem, String[] paramArrayOfString) {
/* 1682 */     D.ebug(4, "In getUplinkedEntityItem :" + paramEntityItem.getKey());
/* 1683 */     return getLinkedEntityItem(paramEntityItem, paramArrayOfString, false);
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
/*      */   private Vector searchEntityGroup(EntityGroup paramEntityGroup, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean) {
/* 1715 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */     
/* 1718 */     EntityItem entityItem = null;
/* 1719 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 1720 */       entityItem = paramEntityGroup.getEntityItem(b);
/* 1721 */       if (paramArrayOfString1 != null) {
/* 1722 */         if (foundInEntity(entityItem, paramArrayOfString1, paramArrayOfString2, paramBoolean)) {
/* 1723 */           vector.add(entityItem);
/*      */         }
/*      */       } else {
/*      */         
/* 1727 */         vector.add(entityItem);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1732 */     return vector;
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
/*      */   private Vector searchEntityVectorLink(Vector<EntityItem> paramVector, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean1, boolean paramBoolean2, String paramString) {
/* 1749 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */     
/* 1752 */     EntityItem entityItem1 = null;
/* 1753 */     EntityItem entityItem2 = null;
/* 1754 */     int i = 0;
/* 1755 */     if (paramVector.size() == 0) {
/* 1756 */       logMessage("searchEntityVectorLink:NO ITEMS FOUND");
/*      */     }
/* 1758 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1759 */       entityItem1 = paramVector.elementAt(b);
/* 1760 */       if (paramBoolean2) {
/* 1761 */         i = entityItem1.getDownLinkCount();
/*      */       } else {
/*      */         
/* 1764 */         i = entityItem1.getUpLinkCount();
/*      */       } 
/* 1766 */       for (byte b1 = 0; b1 < i; b1++) {
/* 1767 */         if (paramBoolean2) {
/* 1768 */           entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/*      */         } else {
/*      */           
/* 1771 */           entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/*      */         } 
/* 1773 */         if (entityItem2 != null && 
/* 1774 */           entityItem2.getEntityType().equals(paramString)) {
/* 1775 */           D.ebug(4, "searchEntityVectorLink:Linking from :" + entityItem1
/* 1776 */               .getKey() + " to " + entityItem2.getKey());
/* 1777 */           if (paramArrayOfString1 != null) {
/* 1778 */             if (foundInEntity(entityItem2, paramArrayOfString1, paramArrayOfString2, paramBoolean1) && 
/* 1779 */               !vector.contains(entityItem2)) {
/* 1780 */               vector.add(entityItem2);
/*      */             
/*      */             }
/*      */           
/*      */           }
/* 1785 */           else if (!vector.contains(entityItem2)) {
/* 1786 */             vector.add(entityItem2);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1793 */     return vector;
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
/*      */   private Vector searchEntityVectorLink1(Vector<EntityItem> paramVector, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean, String paramString) {
/* 1809 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */     
/* 1812 */     EntityItem entityItem = null;
/*      */     
/* 1814 */     D.ebug(4, "searchEntityVectorLink1-- _vEntItems.size(): " + paramVector.size() + "_strCheckAttribute: " + paramArrayOfString1 + "_strCheckValues: " + paramArrayOfString2 + "_bAllTrue: " + paramBoolean + "_strLinkEtype: " + paramString);
/*      */ 
/*      */     
/* 1817 */     if (paramVector.size() == 0) {
/* 1818 */       logMessage("searchEntityVectorLink for searchEntityVectorLink1:NO ITEMS FOUND");
/*      */     }
/* 1820 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1821 */       entityItem = paramVector.elementAt(b);
/* 1822 */       D.ebug(4, "eiCurrentItem" + entityItem + ": " + entityItem.getKey());
/* 1823 */       D.ebug(4, "searchEntityVectorLink1 mystuff for i" + b + "of: " + paramVector.size());
/* 1824 */       if (entityItem != null && 
/* 1825 */         entityItem.getEntityType().equals(paramString)) {
/* 1826 */         D.ebug(4, "searchEntityVectorLink1:Linking from: " + entityItem.getKey());
/* 1827 */         if (paramArrayOfString1 != null) {
/* 1828 */           D.ebug(4, "We're getting here: " + entityItem.getKey() + ": " + paramArrayOfString1 + ": " + paramArrayOfString2 + ": " + paramBoolean);
/*      */           
/* 1830 */           if (foundInEntity(entityItem, paramArrayOfString1, paramArrayOfString2, paramBoolean)) {
/* 1831 */             D.ebug(4, "We're getting here2");
/* 1832 */             if (!vector.contains(entityItem)) {
/* 1833 */               D.ebug(4, "We're getting here3");
/* 1834 */               vector.add(entityItem);
/*      */             }
/*      */           
/*      */           }
/*      */         
/* 1839 */         } else if (!vector.contains(entityItem)) {
/* 1840 */           D.ebug(4, "We're getting here4");
/* 1841 */           vector.add(entityItem);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1847 */     return vector;
/*      */   }
/*      */   
/*      */   private Vector searchInGeo(Vector paramVector, String paramString) {
/* 1851 */     String[] arrayOfString = { paramString };
/*      */     
/* 1853 */     return searchInGeo(paramVector, arrayOfString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector searchInGeo(Vector<EntityItem> paramVector, String[] paramArrayOfString) {
/* 1864 */     Vector<EntityItem> vector = new Vector();
/* 1865 */     EntityItem entityItem = null;
/* 1866 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/* 1868 */     boolean bool = false;
/*      */     
/* 1870 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1871 */       entityItem = paramVector.elementAt(b);
/* 1872 */       String str = null;
/* 1873 */       bool = false;
/* 1874 */       for (byte b1 = 0; b1 < paramArrayOfString.length; b1++) {
/* 1875 */         str = paramArrayOfString[b1];
/*      */         
/* 1877 */         if (str.equals("US")) {
/* 1878 */           bool = this.m_geList.isRfaGeoUS(entityItem);
/*      */           
/*      */           break;
/*      */         } 
/* 1882 */         if (str.equals("AP")) {
/* 1883 */           bool = this.m_geList.isRfaGeoAP(entityItem);
/*      */           
/*      */           break;
/*      */         } 
/* 1887 */         if (str.equals("CAN")) {
/* 1888 */           bool = this.m_geList.isRfaGeoCAN(entityItem);
/*      */           
/*      */           break;
/*      */         } 
/* 1892 */         if (str.equals("EMEA")) {
/* 1893 */           bool = this.m_geList.isRfaGeoEMEA(entityItem);
/*      */           
/*      */           break;
/*      */         } 
/* 1897 */         if (str.equals("LA")) {
/* 1898 */           bool = this.m_geList.isRfaGeoLA(entityItem);
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1903 */       if (bool && 
/* 1904 */         !hashtable.containsKey(entityItem.getKey())) {
/* 1905 */         hashtable.put(entityItem.getKey(), entityItem);
/* 1906 */         vector.add(entityItem);
/* 1907 */         D.ebug(4, "searchInGeo:checking for GEO:Adding:" + entityItem.getKey());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1912 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getAllGeoTags(Vector<EntityItem> paramVector) {
/* 1921 */     String str = "";
/* 1922 */     EntityItem entityItem = null;
/* 1923 */     boolean bool1 = false;
/* 1924 */     boolean bool2 = false;
/* 1925 */     boolean bool3 = false;
/* 1926 */     boolean bool4 = false;
/* 1927 */     boolean bool5 = false;
/* 1928 */     D.ebug(4, "getAllGeoTags:Vector is ");
/* 1929 */     displayContents(paramVector);
/* 1930 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1931 */       entityItem = paramVector.elementAt(b);
/*      */       
/* 1933 */       if (this.m_geList == null) {
/* 1934 */         logMessage("getGeoTags:GE List is null!");
/*      */       }
/* 1936 */       if (!bool1 && 
/* 1937 */         this.m_geList.isRfaGeoUS(entityItem)) {
/* 1938 */         bool1 = true;
/*      */       }
/*      */       
/* 1941 */       if (!bool2 && 
/* 1942 */         this.m_geList.isRfaGeoAP(entityItem)) {
/* 1943 */         bool2 = true;
/*      */       }
/*      */       
/* 1946 */       if (!bool3 && 
/* 1947 */         this.m_geList.isRfaGeoCAN(entityItem)) {
/* 1948 */         bool3 = true;
/*      */       }
/*      */       
/* 1951 */       if (!bool4 && 
/* 1952 */         this.m_geList.isRfaGeoEMEA(entityItem)) {
/* 1953 */         bool4 = true;
/*      */       }
/*      */       
/* 1956 */       if (!bool5 && 
/* 1957 */         this.m_geList.isRfaGeoLA(entityItem)) {
/* 1958 */         bool5 = true;
/*      */       }
/*      */     } 
/*      */     
/* 1962 */     if (bool1) {
/* 1963 */       str = "US";
/*      */     }
/*      */     
/* 1966 */     if (bool2) {
/* 1967 */       if (str.length() > 0) {
/* 1968 */         str = str + ", AP";
/*      */       } else {
/*      */         
/* 1971 */         str = "AP";
/*      */       } 
/*      */     }
/*      */     
/* 1975 */     if (bool3) {
/* 1976 */       if (str.length() > 0) {
/* 1977 */         str = str + ", CAN";
/*      */       } else {
/*      */         
/* 1980 */         str = "CAN";
/*      */       } 
/*      */     }
/* 1983 */     if (bool4) {
/* 1984 */       if (str.length() > 0) {
/* 1985 */         str = str + ", EMEA";
/*      */       } else {
/*      */         
/* 1988 */         str = "EMEA";
/*      */       } 
/*      */     }
/* 1991 */     if (bool5) {
/* 1992 */       if (str.length() > 0) {
/* 1993 */         str = str + ", LA";
/*      */       } else {
/*      */         
/* 1996 */         str = "LA";
/*      */       } 
/*      */     }
/* 1999 */     logMessage("getAllGeoTags:returning GEO tag :" + str);
/* 2000 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getGeoTags(EntityItem paramEntityItem) {
/* 2010 */     String str = "";
/* 2011 */     if (this.m_geList == null) {
/* 2012 */       logMessage("getGeoTags:GE List is null!");
/*      */     }
/* 2014 */     if (this.m_geList.isRfaGeoUS(paramEntityItem)) {
/* 2015 */       str = "US";
/*      */     }
/* 2017 */     if (this.m_geList.isRfaGeoAP(paramEntityItem)) {
/* 2018 */       if (str.length() > 0) {
/* 2019 */         str = str + ", AP";
/*      */       } else {
/*      */         
/* 2022 */         str = "AP";
/*      */       } 
/*      */     }
/* 2025 */     if (this.m_geList.isRfaGeoCAN(paramEntityItem)) {
/* 2026 */       if (str.length() > 0) {
/* 2027 */         str = str + ", CAN";
/*      */       } else {
/*      */         
/* 2030 */         str = "CAN";
/*      */       } 
/*      */     }
/* 2033 */     if (this.m_geList.isRfaGeoEMEA(paramEntityItem)) {
/* 2034 */       if (str.length() > 0) {
/* 2035 */         str = str + ", EMEA";
/*      */       } else {
/*      */         
/* 2038 */         str = "EMEA";
/*      */       } 
/*      */     }
/* 2041 */     if (this.m_geList.isRfaGeoLA(paramEntityItem)) {
/* 2042 */       if (str.length() > 0) {
/* 2043 */         str = str + ", LA";
/*      */       } else {
/*      */         
/* 2046 */         str = "LA";
/*      */       } 
/*      */     }
/* 2049 */     logMessage("getGeoTags:returning GEO tag for entity:" + paramEntityItem.getKey() + ":" + str);
/* 2050 */     return str;
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
/*      */   private Vector searchEntityGroupLink(EntityGroup paramEntityGroup, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean1, boolean paramBoolean2, String paramString) {
/* 2070 */     Vector<EntityItem> vector = new Vector();
/* 2071 */     if (paramEntityGroup == null) {
/* 2072 */       logMessage("pASSED NULL ENTITYGROUP");
/* 2073 */       return vector;
/*      */     } 
/* 2075 */     D.ebug(4, "searchEntityGroupLink..Group is " + paramEntityGroup.getEntityType());
/*      */ 
/*      */     
/* 2078 */     EntityItem entityItem1 = null;
/* 2079 */     EntityItem entityItem2 = null;
/* 2080 */     int i = 0;
/* 2081 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 2082 */       entityItem1 = paramEntityGroup.getEntityItem(b);
/* 2083 */       D.ebug(4, "Searching " + entityItem1 + getEntityType() + ":" + entityItem1.getEntityID());
/* 2084 */       if (paramBoolean2) {
/* 2085 */         i = entityItem1.getDownLinkCount();
/*      */       } else {
/*      */         
/* 2088 */         i = entityItem1.getUpLinkCount();
/*      */       } 
/* 2090 */       for (byte b1 = 0; b1 < i; b1++) {
/* 2091 */         if (paramBoolean2) {
/* 2092 */           entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/* 2093 */           D.ebug(4, "Getting Downlinked " + entityItem2 + getEntityType() + ":" + entityItem2.getEntityID());
/*      */         } else {
/*      */           
/* 2096 */           entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/*      */         } 
/* 2098 */         if (entityItem2 != null && 
/* 2099 */           entityItem2.getEntityType().equals(paramString)) {
/* 2100 */           if (paramArrayOfString1 != null) {
/* 2101 */             if (foundInEntity(entityItem2, paramArrayOfString1, paramArrayOfString2, paramBoolean1)) {
/* 2102 */               vector.add(entityItem2);
/*      */             }
/*      */           } else {
/*      */             
/* 2106 */             vector.add(entityItem2);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2112 */     return vector;
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
/*      */   private Vector searchEntityItemLink(EntityItem paramEntityItem, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean1, boolean paramBoolean2, String paramString) {
/* 2130 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */     
/* 2133 */     EntityItem entityItem = null;
/* 2134 */     int i = 0;
/* 2135 */     D.ebug(4, "searchEntityItemLink: searching for " + paramString + (paramBoolean2 ? " downlinks " : " uplinks ") + " from " + paramEntityItem
/*      */         
/* 2137 */         .getKey());
/* 2138 */     if (paramEntityItem == null) {
/* 2139 */       return vector;
/*      */     }
/* 2141 */     if (paramBoolean2) {
/* 2142 */       i = paramEntityItem.getDownLinkCount();
/*      */     } else {
/*      */       
/* 2145 */       i = paramEntityItem.getUpLinkCount();
/*      */     } 
/* 2147 */     D.ebug(4, "searchEntityItemLink: found " + i + (paramBoolean2 ? " downlinks " : " uplinks ") + " from " + paramEntityItem
/*      */         
/* 2149 */         .getKey());
/* 2150 */     for (byte b = 0; b < i; b++) {
/* 2151 */       if (paramBoolean2) {
/* 2152 */         entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/*      */       } else {
/*      */         
/* 2155 */         entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/*      */       } 
/* 2157 */       if (entityItem != null && 
/* 2158 */         entityItem.getEntityType().equals(paramString)) {
/* 2159 */         if (paramArrayOfString1 != null) {
/* 2160 */           if (foundInEntity(entityItem, paramArrayOfString1, paramArrayOfString2, paramBoolean1)) {
/* 2161 */             D.ebug(4, "searchEntityItemLink:adding to vector");
/* 2162 */             vector.add(entityItem);
/*      */           } 
/*      */         } else {
/*      */           
/* 2166 */           vector.add(entityItem);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2172 */     return vector;
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
/*      */   private void printShortValueListInItem(EntityItem paramEntityItem, String[] paramArrayOfString, String paramString) {
/* 2185 */     int i = paramArrayOfString.length;
/* 2186 */     String str = null;
/* 2187 */     for (byte b = 0; b < i; b++) {
/* 2188 */       if (paramArrayOfString[b].trim().length() > 0) {
/*      */         
/* 2190 */         str = getAttributeShortFlagDesc(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramArrayOfString[b], paramString);
/*      */ 
/*      */         
/* 2193 */         if (i > 1) {
/* 2194 */           this.vPrintDetails.add(str);
/*      */         }
/* 2196 */         else if (!str.equals(paramString)) {
/* 2197 */           this.vPrintDetails.add(str);
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
/*      */   private void printValueListInItem(EntityItem paramEntityItem, String[] paramArrayOfString, String paramString, boolean paramBoolean) {
/* 2212 */     int i = paramArrayOfString.length;
/* 2213 */     String str = null;
/* 2214 */     for (byte b = 0; b < i; b++) {
/* 2215 */       if (paramArrayOfString[b].trim().length() > 0) {
/* 2216 */         str = getAttributeValue(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramArrayOfString[b], paramString);
/* 2217 */         if (str != null && 
/* 2218 */           paramBoolean && str.trim().length() > 0) {
/* 2219 */           str = transformXML(str);
/*      */         }
/*      */ 
/*      */         
/* 2223 */         if (i > 1) {
/* 2224 */           this.vPrintDetails.add(str);
/*      */         }
/* 2226 */         else if (!str.equals(paramString)) {
/* 2227 */           this.vPrintDetails.add(str);
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
/*      */   private void printValueListInGroup(EntityGroup paramEntityGroup, String[] paramArrayOfString, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 2245 */     EntityItem entityItem = null;
/*      */     
/* 2247 */     if (paramEntityGroup != null) {
/* 2248 */       for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 2249 */         entityItem = paramEntityGroup.getEntityItem(b);
/* 2250 */         if (paramString1 != null) {
/* 2251 */           if (foundInEntity(entityItem, new String[] { paramString1 }, new String[] { paramString2 }, true)) {
/* 2252 */             printValueListInItem(entityItem, paramArrayOfString, paramString3, paramBoolean);
/*      */           }
/*      */         } else {
/*      */           
/* 2256 */           printValueListInItem(entityItem, paramArrayOfString, paramString3, paramBoolean);
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
/* 2273 */     EntityItem entityItem = null;
/*      */     
/* 2275 */     if (paramVector != null) {
/* 2276 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 2277 */         entityItem = paramVector.elementAt(b);
/* 2278 */         if (paramBoolean1) {
/* 2279 */           printValueListInItem(entityItem, paramArrayOfString, paramString, paramBoolean2);
/*      */         } else {
/*      */           
/* 2282 */           printShortValueListInItem(entityItem, paramArrayOfString, paramString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2410 */     boolean bool = false;
/* 2411 */     String str = null;
/* 2412 */     if (paramEntityItem == null) {
/* 2413 */       logMessage("Passing null entity item to search");
/* 2414 */       return false;
/*      */     } 
/*      */     
/* 2417 */     for (byte b = 0; b < paramArrayOfString1.length; b++) {
/*      */ 
/*      */       
/* 2420 */       if (paramArrayOfString2[b].toLowerCase().equals(paramArrayOfString2[b].toUpperCase())) {
/* 2421 */         if (flagvalueEquals(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramArrayOfString1[b], paramArrayOfString2[b])) {
/*      */           
/* 2423 */           bool = true;
/*      */         } else {
/*      */           
/* 2426 */           bool = false;
/*      */         } 
/*      */       } else {
/*      */         
/* 2430 */         str = getAttributeValue(paramEntityItem, paramArrayOfString1[b], "");
/* 2431 */         if (str.indexOf(paramArrayOfString2[b]) > -1) {
/* 2432 */           bool = true;
/*      */         }
/*      */         else {
/*      */           
/* 2436 */           bool = false;
/*      */         } 
/*      */       } 
/*      */       
/* 2440 */       if (bool ? 
/* 2441 */         !paramBoolean : 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2446 */         paramBoolean) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2453 */     return bool;
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
/* 2464 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 2474 */     return "<br /><br />";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRevision() {
/* 2483 */     return new String("$Revision: 1.157 $");
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
/*      */   private void printReport(boolean paramBoolean, String[] paramArrayOfString, int[] paramArrayOfint, Vector<String> paramVector) {
/* 2510 */     if (paramArrayOfint.length > 1 || paramBoolean) {
/* 2511 */       this.rfaReport.printHeaders(paramBoolean);
/* 2512 */       this.rfaReport.setHeader(paramArrayOfString);
/* 2513 */       this.rfaReport.setColWidth(paramArrayOfint);
/* 2514 */       this.rfaReport.setDetail(paramVector);
/* 2515 */       if (paramVector.size() > 0) {
/* 2516 */         this.rfaReport.printReport();
/*      */       }
/* 2518 */       this.rfaReport.setOffset(0);
/*      */     } else {
/*      */       
/* 2521 */       if (paramArrayOfint[0] == 69) {
/* 2522 */         for (byte b = 0; b < paramVector.size(); b++) {
/* 2523 */           prettyPrint(paramVector.elementAt(b), paramArrayOfint[0]);
/*      */         }
/*      */       }
/* 2526 */       else if (paramVector.size() > 0) {
/* 2527 */         this.rfaReport.printHeaders(paramBoolean);
/* 2528 */         this.rfaReport.setColWidth(paramArrayOfint);
/* 2529 */         this.rfaReport.setDetail(paramVector);
/* 2530 */         this.rfaReport.printReport();
/*      */       } 
/* 2532 */       this.rfaReport.setOffset(0);
/* 2533 */       this.rfaReport.setColumnSeparator(" ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetPrintvars() {
/* 2541 */     this.vPrintDetails.removeAllElements();
/* 2542 */     this.vPrintDetails = null;
/* 2543 */     this.vPrintDetails = new Vector();
/* 2544 */     this.rfaReport.setSortable(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayContents(Vector<EntityItem> paramVector) {
/* 2554 */     EntityItem entityItem = null;
/* 2555 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 2556 */       entityItem = paramVector.elementAt(b);
/* 2557 */       logMessage("ET:" + entityItem.getEntityType() + ":EI:" + entityItem.getEntityID());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processShortAnswers() {
/* 2565 */     if (this.eiAnnounce == null) {
/* 2566 */       println("Announce EntityItem IS NULL!");
/*      */     }
/* 2568 */     println("CNTLNO = '" + getAttributeValue(this.eiAnnounce, "ANNNUMBER", "Not Populated") + "'");
/* 2569 */     println("TYPE = '" + getAttributeValue(this.eiAnnounce, "OFANNTYPE", "Not Populated") + "'");
/*      */     
/* 2571 */     println("VERSION = '22.04'");
/* 2572 */     this.strSplit = "";
/* 2573 */     logMessage("_1A*******************");
/* 2574 */     this.strFilterAttr = new String[] { "DELIVERABLETYPE" };
/*      */     
/* 2576 */     this.strFilterValue = new String[] { "860" };
/*      */     
/* 2578 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnDeliv, this.strFilterAttr, this.strFilterValue, true);
/* 2579 */     this.eiAnnDeliv = (this.vReturnEntities1.size() > 0) ? this.vReturnEntities1.elementAt(0) : null;
/* 2580 */     this.bConditionOK = false;
/* 2581 */     if (this.eiAnnDeliv != null) {
/* 2582 */       this.bConditionOK = true;
/* 2583 */       this.strSplit = (this.eiAnnDeliv != null) ? getAttributeValue(this.eiAnnDeliv, "SUBJECTLINE_1", "") : "ANNDELIVERABLE NOT LINKED";
/*      */     } 
/* 2585 */     println(".*$P_1A = '" + this.strSplit + "'");
/* 2586 */     this.strSplit = "";
/* 2587 */     if (this.bConditionOK)
/*      */     {
/* 2589 */       this.strSplit = (this.eiAnnDeliv != null) ? getAttributeValue(this.eiAnnDeliv, "SUBJECTLINE_2", "") : "ANNDELIVERABLE NOT LINKED";
/*      */     }
/* 2591 */     println(".*$P_1B = '" + this.strSplit + "'");
/*      */     
/* 2593 */     this.strCondition2 = "";
/* 2594 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnPara, (String[])null, (String[])null, true, true, "PARAMETERCODE");
/* 2595 */     print(".*$P_1C = '");
/* 2596 */     if (this.vReturnEntities1.size() > 0) {
/* 2597 */       this.bConditionOK = false;
/* 2598 */       for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 2599 */         this.eiParamCode = this.vReturnEntities1.elementAt(this.i);
/* 2600 */         this.strCondition1 = getAttributeValue(this.eiParamCode, "PARAMETERCODENUMBER", "");
/* 2601 */         if (!this.bConditionOK) {
/* 2602 */           this.bConditionOK = true;
/* 2603 */           this.strCondition2 = this.strCondition1;
/*      */         } else {
/*      */           
/* 2606 */           this.strCondition2 += "," + this.strCondition1;
/*      */         } 
/* 2608 */         if (this.i == 1) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/* 2613 */     println(this.strCondition2 + "'");
/*      */     
/* 2615 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpDependCode, (String[])null, (String[])null, true, true, "DEPENDENCYCODE");
/* 2616 */     print(".*$P_1D = '");
/* 2617 */     if (this.vReturnEntities1.size() > 0) {
/* 2618 */       this.bConditionOK = false;
/* 2619 */       for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 2620 */         this.eiDependCode = this.vReturnEntities1.elementAt(this.i);
/* 2621 */         this.strCondition1 = getAttributeValue(this.eiDependCode, "DEPENCODENUMBER", "Not Populated");
/* 2622 */         if (!this.bConditionOK) {
/* 2623 */           this.bConditionOK = true;
/* 2624 */           this.strCondition2 = this.strCondition1;
/*      */         } else {
/*      */           
/* 2627 */           this.strCondition2 += ", " + this.strCondition1;
/*      */         } 
/* 2629 */         if (this.i == 8) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/* 2634 */     println(this.strCondition2 + "'");
/*      */     
/* 2636 */     this.strCondition2 = "";
/* 2637 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpDependCode, (String[])null, (String[])null, true, true, "DEPENDENCYCODE");
/* 2638 */     print(".*$P_1E = '");
/* 2639 */     if (this.vReturnEntities1.size() > 8) {
/* 2640 */       this.bConditionOK = false;
/* 2641 */       for (this.i = 9; this.i < this.vReturnEntities1.size(); this.i++) {
/* 2642 */         this.eiDependCode = this.vReturnEntities1.elementAt(this.i);
/* 2643 */         this.strCondition1 = getAttributeValue(this.eiDependCode, "DEPENCODENUMBER", "Not Populated");
/* 2644 */         if (!this.bConditionOK) {
/* 2645 */           this.bConditionOK = true;
/* 2646 */           this.strCondition2 = this.strCondition1;
/*      */         } else {
/*      */           
/* 2649 */           this.strCondition2 += ", " + this.strCondition1;
/*      */         } 
/* 2651 */         if (this.i == 17) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/* 2656 */     println(this.strCondition2 + "'");
/*      */     
/* 2658 */     print(".*$P_1F = '");
/* 2659 */     this.strCondition2 = "";
/* 2660 */     if (this.vReturnEntities1.size() > 16) {
/* 2661 */       this.bConditionOK = false;
/* 2662 */       for (this.i = 17; this.i < this.vReturnEntities1.size(); this.i++) {
/* 2663 */         this.eiDependCode = this.vReturnEntities1.elementAt(this.i);
/* 2664 */         this.strCondition1 = getAttributeValue(this.eiDependCode, "DEPENCODENUMBER", "Not Populated");
/* 2665 */         if (!this.bConditionOK) {
/* 2666 */           this.bConditionOK = true;
/* 2667 */           this.strCondition2 = this.strCondition1;
/*      */         } else {
/*      */           
/* 2670 */           this.strCondition2 += ", " + this.strCondition1;
/*      */         } 
/* 2672 */         if (this.i == 25) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/* 2677 */     println(this.strCondition2 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2690 */     this
/* 2691 */       .strCondition1 = (this.eiAnnReview != null) ? getAttributeValue(this.eiAnnReview.getEntityType(), this.eiAnnReview.getEntityID(), "ANREVIEW", "") : "";
/* 2692 */     this.strSplit = "";
/* 2693 */     this.strFilterAttr = new String[] { "ANNREVIEWDEF" };
/*      */     
/* 2695 */     this.strFilterValue = new String[] { "101" };
/*      */     
/* 2697 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnReview, this.strFilterAttr, this.strFilterValue, true);
/* 2698 */     this.eiAnnReview = (this.vReturnEntities1.size() > 0) ? this.vReturnEntities1.elementAt(0) : null;
/* 2699 */     this
/* 2700 */       .strSplit = (this.eiAnnReview != null) ? getAttributeValue(this.eiAnnReview.getEntityType(), this.eiAnnReview.getEntityID(), "ANREVDATE", "") : "";
/* 2701 */     println(".*$P_3A = '" + this.strSplit + "'");
/* 2702 */     this.strSplit = "";
/* 2703 */     this.strFilterAttr = new String[] { "ANNREVIEWDEF" };
/*      */     
/* 2705 */     this.strFilterValue = new String[] { "102" };
/*      */     
/* 2707 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnReview, this.strFilterAttr, this.strFilterValue, true);
/* 2708 */     this.eiAnnReview = (this.vReturnEntities1.size() > 0) ? this.vReturnEntities1.elementAt(0) : null;
/* 2709 */     this
/* 2710 */       .strSplit = (this.eiAnnReview != null) ? getAttributeValue(this.eiAnnReview.getEntityType(), this.eiAnnReview.getEntityID(), "ANREVDATE", "") : "";
/* 2711 */     println(".*$P_3B = '" + this.strSplit + "'");
/* 2712 */     println(".*$P_3C = '" + ((this.grpAnnouncement != null) ? getAttributeValue(this.eiAnnounce, "ANNDATE", "") : "") + "'");
/*      */ 
/*      */     
/* 2715 */     println(".*$P_4A = '0'");
/* 2716 */     println(".*$P_4B = '" + ((this.grpAnnouncement != null) ? getAttributeValue(this.eiAnnounce, "REVISIONLEVEL", "") : "'") + "'");
/*      */ 
/*      */ 
/*      */     
/* 2720 */     println(".*$P_6A = '0'");
/*      */     
/* 2722 */     println(".*$P_6B = '0'");
/* 2723 */     println(".*$P_6C = '0'");
/* 2724 */     println(".*$P_6D = '0'");
/* 2725 */     println(".*$P_6E = '0'");
/* 2726 */     println(".*$P_6F = '0'");
/* 2727 */     println(".*$P_6G = '0'");
/* 2728 */     println(".*$P_6H = '0'");
/* 2729 */     println(".*$P_6I = '0'");
/* 2730 */     println(".*$P_6J = '0'");
/* 2731 */     println(".*$P_6K = '0'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2742 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "EXECAPPREADY", "0");
/* 2743 */     println(".*$P_7A = '" + (this.strCondition1.equalsIgnoreCase("Yes") ? "1" : "0") + "'");
/* 2744 */     println(".*$P_7B = '" + ((this.grpAnnouncement != null) ? getAttributeValue(this.eiAnnounce, "EXECAPPRDATE_T", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2749 */     this.bConditionOK = false;
/* 2750 */     this.strFilterAttr = new String[] { "ORGANUNITTYPE" };
/*      */     
/* 2752 */     this.strFilterValue = new String[] { "4156" };
/*      */     
/* 2754 */     this.vReturnEntities1 = searchEntityGroup(this.grpOrganUnit, this.strFilterAttr, this.strFilterValue, false);
/* 2755 */     if (this.vReturnEntities1.size() > 0) {
/* 2756 */       this.eiOrganUnit = this.vReturnEntities1.elementAt(0);
/* 2757 */       this.bConditionOK = true;
/*      */     } 
/* 2759 */     this.strCondition1 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "NAME", "") : "";
/* 2760 */     println(".*$P_7C = '" + this.strCondition1.trim() + "'");
/* 2761 */     this.strCondition1 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "INITIALS", "") : "";
/* 2762 */     println(".*$P_7D = '" + this.strCondition1.trim() + "'");
/* 2763 */     this.strCondition1 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "STREETADDRESS", "") : "";
/*      */     
/* 2765 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "CITY", "") : "";
/*      */     
/* 2767 */     this.strCondition1 += (this.strCondition1.length() > 0 && this.strCondition2.length() > 0) ? ("," + this.strCondition2.trim()) : "";
/*      */     
/* 2769 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "STATE", "") : "";
/* 2770 */     this.strCondition1 += (this.strCondition1.length() > 0 && this.strCondition2.length() > 0) ? ("," + this.strCondition2.trim()) : "";
/*      */     
/* 2772 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "COUNTRY", "") : "";
/* 2773 */     this.strCondition1 += (this.strCondition1.length() > 0 && this.strCondition2.length() > 0) ? ("," + this.strCondition2.trim()) : "";
/*      */     
/* 2775 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiOrganUnit, "ZIPCODE", "") : "";
/* 2776 */     this.strCondition1 += (this.strCondition1.length() > 0 && this.strCondition2.length() > 0) ? ("," + this.strCondition2.trim()) : "";
/* 2777 */     println(".*$P_7E = '" + this.strCondition1 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2782 */     this.eiAnnToOP = null;
/* 2783 */     this.eiOP = null;
/* 2784 */     this.bConditionOK = false;
/* 2785 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2786 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2787 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "15")) {
/* 2788 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         break;
/*      */       } 
/*      */     } 
/* 2792 */     println(".*$P_8A = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2793 */     println(".*$P_8B = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "STREETADDRESS", "") : "") + "'");
/* 2794 */     this.strSplit = (this.eiOP != null) ? getAttributeValue(this.eiOP, "CITY", "") : "";
/* 2795 */     this.strSplit += ", " + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "STATE", "") : "");
/* 2796 */     this.strSplit += ", " + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "COUNTRY", "") : "");
/* 2797 */     println(".*$P_8C = '" + this.strSplit + "'");
/*      */     
/* 2799 */     println(".*$P_9A = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "SITE", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2804 */     this.eiAnnToOP = null;
/* 2805 */     this.eiOP = null;
/* 2806 */     this.bConditionOK = false;
/* 2807 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2808 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2809 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "4")) {
/* 2810 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         break;
/*      */       } 
/*      */     } 
/* 2814 */     println(".*$P_9B = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2815 */     println(".*$P_9C = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETUID", "") : "") + "'");
/* 2816 */     println(".*$P_9D = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETNODE", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2821 */     this.eiAnnToOP = null;
/* 2822 */     this.eiOP = null;
/* 2823 */     this.bConditionOK = false;
/* 2824 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2825 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2826 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "9")) {
/* 2827 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 2832 */     println(".*$P_10A = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2833 */     println(".*$P_10B = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "JOBTITLE", "") : "") + "'");
/* 2834 */     println(".*$P_10C = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "TIELINE", "") : "") + "'");
/* 2835 */     this.strSplit = (this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETNODE", "") : "";
/* 2836 */     this.strSplit += "/" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETUID", "") : "");
/* 2837 */     println(".*$P_10D = '" + this.strSplit + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2842 */     this.eiAnnToOP = null;
/* 2843 */     this.eiOP = null;
/* 2844 */     this.bConditionOK = false;
/* 2845 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2846 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2847 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "7")) {
/* 2848 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         break;
/*      */       } 
/*      */     } 
/* 2852 */     println(".*$P_11A = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2853 */     println(".*$P_11B = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "TELEPHONE", "") : "") + "'");
/* 2854 */     println(".*$P_11C = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETUID", "") : "") + "'");
/* 2855 */     println(".*$P_11D = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETNODE", "") : "") + "'");
/* 2856 */     println(".*$P_11E = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "EMAIL", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2861 */     this.eiAnnToOP = null;
/* 2862 */     this.eiOP = null;
/* 2863 */     this.bConditionOK = false;
/* 2864 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2865 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2866 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "3")) {
/* 2867 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         break;
/*      */       } 
/*      */     } 
/* 2871 */     println(".*$P_11F = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2872 */     println(".*$P_11G = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "TELEPHONE", "") : "") + "'");
/* 2873 */     println(".*$P_11H = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "EMAIL", "") : "") + "'");
/* 2874 */     println(".*$P_11I = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETNODE", "") : "") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2879 */     this.eiAnnToOP = null;
/* 2880 */     this.eiOP = null;
/* 2881 */     this.bConditionOK = false;
/* 2882 */     for (this.i = 0; this.i < this.grpAnnToOP.getEntityItemCount(); this.i++) {
/* 2883 */       this.eiAnnToOP = this.grpAnnToOP.getEntityItem(this.i);
/* 2884 */       if (flagvalueEquals(this.eiAnnToOP.getEntityType(), this.eiAnnToOP.getEntityID(), "ANNROLETYPE", "6")) {
/* 2885 */         this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/*      */         break;
/*      */       } 
/*      */     } 
/* 2889 */     println(".*$P_13A = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "USERNAME", "") : "") + "'");
/* 2890 */     println(".*$P_13B = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "TELEPHONE", "") : "") + "'");
/* 2891 */     println(".*$P_13C = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETUID", "") : "") + "'");
/* 2892 */     println(".*$P_13D = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "VNETNODE", "") : "") + "'");
/* 2893 */     println(".*$P_13E = '" + ((this.eiOP != null) ? getAttributeValue(this.eiOP, "SITE", "") : "") + "'");
/* 2894 */     this.bConditionOK = false;
/* 2895 */     this.eiChannel = null;
/* 2896 */     this.strFilterAttr = new String[] { "AVAILTYPE" };
/*      */     
/* 2898 */     this.strFilterValue = new String[] { "146" };
/*      */ 
/*      */     
/* 2901 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, this.strFilterAttr, this.strFilterValue, true, true, "AVAIL");
/* 2902 */     logMessage("****AVAIL*****");
/* 2903 */     displayContents(this.vReturnEntities1);
/* 2904 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "SOFAVAIL");
/* 2905 */     logMessage("****SOFAVAIL*****");
/* 2906 */     displayContents(this.vReturnEntities2);
/* 2907 */     this.vSofFrmSofAvail = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, false, "SOF");
/* 2908 */     logMessage("****SOF*****");
/* 2909 */     displayContents(this.vSofFrmSofAvail);
/*      */ 
/*      */ 
/*      */     
/* 2913 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "CMPNTAVAIL");
/* 2914 */     logMessage("****CMPNTAVAIL*****");
/* 2915 */     displayContents(this.vReturnEntities2);
/*      */     
/* 2917 */     this.vCmpntFrmCmpntAvail = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, false, "CMPNT");
/* 2918 */     logMessage("****CMPNT*****");
/* 2919 */     displayContents(this.vCmpntFrmCmpntAvail);
/*      */ 
/*      */     
/* 2922 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "FEATUREAVAIL");
/* 2923 */     logMessage("****FEATUREAVAIL*****");
/* 2924 */     displayContents(this.vReturnEntities2);
/*      */     
/* 2926 */     this.vFeatureFrmFeatureAvail = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, false, "FEATURE");
/* 2927 */     logMessage("****FEATURE*****");
/* 2928 */     displayContents(this.vFeatureFrmFeatureAvail);
/*      */     
/* 2930 */     this.vSofSortedbyMkt = sortEntities(this.vSofFrmSofAvail, new String[] { "MKTGNAME" });
/* 2931 */     this.vReturnEntities5 = new Vector();
/* 2932 */     this.vReturnEntities5.addAll(this.vSofFrmSofAvail);
/* 2933 */     this.vReturnEntities5.addAll(this.vCmpntFrmCmpntAvail);
/* 2934 */     this.vReturnEntities5.addAll(this.vFeatureFrmFeatureAvail);
/*      */ 
/*      */     
/* 2937 */     this.strCmptToSof = new String[] { "SOFCMPNT", "SOF" };
/*      */     
/* 2939 */     this.vCmptSortedbyMkt = sortEntities(this.vCmpntFrmCmpntAvail, new String[] { "MKTGNAME" });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2944 */     this.vFeatureSortedbyMkt = sortEntities(this.vFeatureFrmFeatureAvail, new String[] { "MKTGNAME" });
/*      */     
/* 2946 */     this.vAllSortedOfferings = RFAsort(this.vReturnEntities5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3036 */     this.strFilterAttr = new String[] { "CHANNELNAME", "ROUTESTOMKTG" };
/*      */     
/* 3038 */     this.strFilterValue = new String[] { "374", "110" };
/*      */     
/* 3040 */     this.vReturnEntities2 = searchEntityVectorLink(this.vSofFrmSofAvail, (String[])null, (String[])null, true, true, "SOFCHANNEL");
/* 3041 */     logMessage("14B****SOFCHANNEL*****");
/* 3042 */     displayContents(this.vReturnEntities2);
/* 3043 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "CHANNEL");
/* 3044 */     logMessage("14B****CHANNEL*****");
/* 3045 */     displayContents(this.vReturnEntities3);
/* 3046 */     this.strCondition1 = getAllGeoTags(this.vReturnEntities3);
/* 3047 */     if (this.strCondition1.indexOf("US") <= -1 && this.strCondition1.indexOf("LA") <= -1 && this.strCondition1.indexOf("CAN") <= -1)
/*      */     {
/*      */       
/* 3050 */       this.vReturnEntities3 = new Vector();
/*      */     }
/* 3052 */     if (this.vReturnEntities3.size() == 0) {
/* 3053 */       this.vReturnEntities2 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTCHANNEL");
/* 3054 */       logMessage("14B****CMPNTCHANNEL*****");
/* 3055 */       displayContents(this.vReturnEntities2);
/* 3056 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 3057 */       logMessage("14B****CHANNEL*****");
/* 3058 */       displayContents(this.vReturnEntities3);
/* 3059 */       this.strCondition1 = getAllGeoTags(this.vReturnEntities3);
/* 3060 */       if (this.strCondition1.indexOf("US") <= -1 && this.strCondition1.indexOf("LA") <= -1 && this.strCondition1.indexOf("CAN") <= -1)
/*      */       {
/*      */         
/* 3063 */         this.vReturnEntities3 = new Vector();
/*      */       }
/*      */     } 
/* 3066 */     if (this.vReturnEntities3.size() == 0) {
/* 3067 */       this.vReturnEntities2 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATURECHANNEL");
/* 3068 */       logMessage("14B****FEATURECHANNEL*****");
/* 3069 */       displayContents(this.vReturnEntities2);
/* 3070 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 3071 */       logMessage("14B****CHANNEL*****");
/* 3072 */       displayContents(this.vReturnEntities3);
/* 3073 */       this.strCondition1 = getAllGeoTags(this.vReturnEntities3);
/* 3074 */       if (this.strCondition1.indexOf("US") <= -1 && this.strCondition1.indexOf("LA") <= -1 && this.strCondition1.indexOf("CAN") <= -1)
/*      */       {
/*      */         
/* 3077 */         this.vReturnEntities3 = new Vector();
/*      */       }
/*      */     } 
/* 3080 */     if (this.vReturnEntities3.size() > 0) {
/* 3081 */       println(".*$P_14B = '1'");
/*      */     } else {
/*      */       
/* 3084 */       println(".*$P_14B = '0'");
/*      */     } 
/*      */     
/* 3087 */     this.strFilterAttr = new String[] { "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME" };
/*      */ 
/*      */ 
/*      */     
/* 3091 */     this.strFilterValue = new String[] { "375", "379", "380", "382", "383", "384", "385", "386", "387", "388", "1000", "1100", "1200" };
/*      */ 
/*      */     
/* 3094 */     this.vReturnEntities2 = searchEntityVectorLink(this.vSofFrmSofAvail, (String[])null, (String[])null, true, true, "SOFCHANNEL");
/* 3095 */     logMessage("15A****SOFCHANNEL*****" + this.vReturnEntities2);
/* 3096 */     displayContents(this.vReturnEntities2);
/* 3097 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "CHANNEL");
/* 3098 */     displayContents(this.vReturnEntities3);
/* 3099 */     logMessage("15A****CHANNEL from SOF*****" + this.vReturnEntities3 + "strFilterAttr: " + this.strFilterAttr + "strFilterValue: " + this.strFilterValue + "vReturnEntities3.size" + this.vReturnEntities3
/* 3100 */         .size());
/*      */     
/* 3102 */     if (this.vReturnEntities3.size() > 0) {
/* 3103 */       this.strFilterAttr1 = new String[] { "ROUTESTOMKTG" };
/*      */       
/* 3105 */       this.strFilterValue1 = new String[] { "110" };
/*      */       
/* 3107 */       logMessage("15A****CHANNEL from SOF vReturnEntities3");
/* 3108 */       displayContents(this.vReturnEntities3);
/* 3109 */       this.vReturnEntities3 = searchEntityVectorLink1(this.vReturnEntities3, this.strFilterAttr1, this.strFilterValue1, true, "CHANNEL");
/* 3110 */       logMessage("15A****CHANNEL from SOF checking for ROUTESTOMKTG*****" + this.vReturnEntities2 + "strFilterAttr1: " + this.strFilterAttr1 + "strFilterValue1: " + this.strFilterValue1 + "vReturnEntities3.size: " + this.vReturnEntities3
/*      */           
/* 3112 */           .size());
/* 3113 */       displayContents(this.vReturnEntities3);
/*      */     } else {
/*      */       
/* 3116 */       this.vReturnEntities3 = new Vector();
/*      */     } 
/*      */     
/* 3119 */     this.strCondition1 = getAllGeoTags(this.vReturnEntities3);
/* 3120 */     if (this.strCondition1.indexOf("US") <= -1 && this.strCondition1.indexOf("LA") <= -1 && this.strCondition1.indexOf("CAN") <= -1)
/*      */     {
/*      */       
/* 3123 */       this.vReturnEntities3 = new Vector();
/*      */     }
/* 3125 */     if (this.vReturnEntities3.size() == 0) {
/* 3126 */       this.vReturnEntities2 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTCHANNEL");
/* 3127 */       logMessage("15A****CMPNTCHANNEL*****" + this.vReturnEntities2);
/* 3128 */       displayContents(this.vReturnEntities2);
/* 3129 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "CHANNEL");
/* 3130 */       logMessage("15A****CHANNEL from CMPNT*****" + this.vReturnEntities3 + "strFilterAttr: " + this.strFilterAttr + "strFilterValue: " + this.strFilterValue + "vReturnEntities3.size" + this.vReturnEntities3
/* 3131 */           .size());
/* 3132 */       displayContents(this.vReturnEntities3);
/*      */       
/* 3134 */       if (this.vReturnEntities3.size() > 0) {
/* 3135 */         this.strFilterAttr1 = new String[] { "ROUTESTOMKTG" };
/*      */         
/* 3137 */         this.strFilterValue1 = new String[] { "110" };
/*      */         
/* 3139 */         logMessage("15A****CHANNEL from CMPNT vReturnEntities3");
/* 3140 */         displayContents(this.vReturnEntities3);
/* 3141 */         this.vReturnEntities3 = searchEntityVectorLink1(this.vReturnEntities3, this.strFilterAttr1, this.strFilterValue1, true, "CHANNEL");
/* 3142 */         logMessage("15A****CHANNEL from CMPNT checking for ROUTESTOMKTG*****" + this.vReturnEntities2 + "strFilterAttr1: " + this.strFilterAttr1 + "strFilterValue1: " + this.strFilterValue1 + "vReturnEntities3.size: " + this.vReturnEntities3
/*      */             
/* 3144 */             .size());
/* 3145 */         displayContents(this.vReturnEntities3);
/*      */       } else {
/*      */         
/* 3148 */         this.vReturnEntities3 = new Vector();
/*      */       } 
/*      */       
/* 3151 */       this.strCondition1 = getAllGeoTags(this.vReturnEntities3);
/* 3152 */       if (this.strCondition1.indexOf("US") <= -1 && this.strCondition1.indexOf("LA") <= -1 && this.strCondition1.indexOf("CAN") <= -1)
/*      */       {
/*      */         
/* 3155 */         this.vReturnEntities3 = new Vector();
/*      */       }
/*      */     } 
/* 3158 */     if (this.vReturnEntities3.size() == 0) {
/* 3159 */       this.vReturnEntities2 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATURECHANNEL");
/* 3160 */       logMessage("15A****FEATURECHANNEL*****" + this.vReturnEntities2);
/* 3161 */       displayContents(this.vReturnEntities2);
/* 3162 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "CHANNEL");
/* 3163 */       logMessage("15A****CHANNEL from FEATURE*****" + this.vReturnEntities3 + "strFilterAttr: " + this.strFilterAttr + "strFilterValue: " + this.strFilterValue + "vReturnEntities3.size" + this.vReturnEntities3
/* 3164 */           .size());
/* 3165 */       displayContents(this.vReturnEntities3);
/*      */       
/* 3167 */       if (this.vReturnEntities3.size() > 0) {
/* 3168 */         this.strFilterAttr1 = new String[] { "ROUTESTOMKTG" };
/*      */         
/* 3170 */         this.strFilterValue1 = new String[] { "110" };
/*      */         
/* 3172 */         logMessage("15A****CHANNEL from SOF vReturnEntities3");
/* 3173 */         displayContents(this.vReturnEntities3);
/* 3174 */         this.vReturnEntities3 = searchEntityVectorLink1(this.vReturnEntities3, this.strFilterAttr1, this.strFilterValue1, true, "CHANNEL");
/* 3175 */         logMessage("15A****CHANNEL from SOF checking for ROUTESTOMKTG*****" + this.vReturnEntities2 + "strFilterAttr1: " + this.strFilterAttr1 + "strFilterValue1: " + this.strFilterValue1 + "vReturnEntities3.size" + this.vReturnEntities3
/*      */             
/* 3177 */             .size());
/* 3178 */         displayContents(this.vReturnEntities3);
/*      */       } else {
/*      */         
/* 3181 */         this.vReturnEntities3 = new Vector();
/*      */       } 
/*      */       
/* 3184 */       this.strCondition1 = getAllGeoTags(this.vReturnEntities3);
/* 3185 */       if (this.strCondition1.indexOf("US") <= -1 && this.strCondition1.indexOf("LA") <= -1 && this.strCondition1.indexOf("CAN") <= -1)
/*      */       {
/*      */         
/* 3188 */         this.vReturnEntities3 = new Vector();
/*      */       }
/*      */     } 
/*      */     
/* 3192 */     logMessage("15A****vReturnEntities3.size()*****: " + this.vReturnEntities3.size());
/* 3193 */     if (this.vReturnEntities3.size() > 0) {
/* 3194 */       println(".*$P_15A = '1'");
/*      */     } else {
/*      */       
/* 3197 */       println(".*$P_15A = '0'");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3275 */     this.strFilterAttr = new String[] { "CHANNELNAME", "ROUTESTOMKTG" };
/*      */     
/* 3277 */     this.strFilterValue = new String[] { "381", "110" };
/*      */     
/* 3279 */     this.vReturnEntities2 = searchEntityVectorLink(this.vSofFrmSofAvail, (String[])null, (String[])null, true, true, "SOFCHANNEL");
/* 3280 */     logMessage("15G****SOFCHANNEL*****");
/* 3281 */     displayContents(this.vReturnEntities2);
/* 3282 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 3283 */     logMessage("15G****CHANNEL*****");
/* 3284 */     displayContents(this.vReturnEntities3);
/* 3285 */     this.strCondition1 = getAllGeoTags(this.vReturnEntities3);
/* 3286 */     if (this.strCondition1.indexOf("US") <= -1 && this.strCondition1.indexOf("LA") <= -1 && this.strCondition1.indexOf("CAN") <= -1)
/*      */     {
/*      */       
/* 3289 */       this.vReturnEntities3 = new Vector();
/*      */     }
/* 3291 */     if (this.vReturnEntities3.size() == 0) {
/* 3292 */       this.vReturnEntities2 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTCHANNEL");
/* 3293 */       logMessage("15G****CMPNTCHANNEL*****");
/* 3294 */       displayContents(this.vReturnEntities2);
/* 3295 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 3296 */       logMessage("15G****CHANNEL*****");
/* 3297 */       displayContents(this.vReturnEntities3);
/* 3298 */       this.strCondition1 = getAllGeoTags(this.vReturnEntities3);
/* 3299 */       if (this.strCondition1.indexOf("US") <= -1 && this.strCondition1.indexOf("LA") <= -1 && this.strCondition1.indexOf("CAN") <= -1)
/*      */       {
/*      */         
/* 3302 */         this.vReturnEntities3 = new Vector();
/*      */       }
/*      */     } 
/* 3305 */     if (this.vReturnEntities3.size() == 0) {
/* 3306 */       this.vReturnEntities2 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATURECHANNEL");
/* 3307 */       logMessage("15G****FEATURECHANNEL*****");
/* 3308 */       displayContents(this.vReturnEntities2);
/* 3309 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 3310 */       logMessage("15G****CHANNEL*****");
/* 3311 */       displayContents(this.vReturnEntities3);
/* 3312 */       this.strCondition1 = getAllGeoTags(this.vReturnEntities3);
/* 3313 */       if (this.strCondition1.indexOf("US") <= -1 && this.strCondition1.indexOf("LA") <= -1 && this.strCondition1.indexOf("CAN") <= -1)
/*      */       {
/*      */         
/* 3316 */         this.vReturnEntities3 = new Vector();
/*      */       }
/*      */     } 
/* 3319 */     if (this.vReturnEntities3.size() > 0) {
/* 3320 */       println(".*$P_15G = '1'");
/*      */     } else {
/*      */       
/* 3323 */       println(".*$P_15G = '0'");
/*      */     } 
/*      */     
/* 3326 */     this
/* 3327 */       .strCondition1 = (this.grpAnnouncement != null) ? getAttributeFlagEnabledValue(this.eiAnnounce.getEntityType(), this.eiAnnounce.getEntityID(), "GENAREANAMEINCL", "") : "";
/*      */ 
/*      */     
/* 3330 */     this.strCondition2 = "0";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3339 */     println(".*$P_15H = '" + (this.m_geList.isRfaGeoUS(this.eiAnnounce) ? "1" : "0") + "'");
/* 3340 */     println(".*$P_15I = '" + (this.m_geList.isRfaGeoLA(this.eiAnnounce) ? "1" : "0") + "'");
/* 3341 */     println(".*$P_15J = '" + (this.m_geList.isRfaGeoCAN(this.eiAnnounce) ? "1" : "0") + "'");
/* 3342 */     println(".*$P_15K = '" + (this.m_geList.isRfaGeoEMEA(this.eiAnnounce) ? "1" : "0") + "'");
/* 3343 */     println(".*$P_15L = '" + (this.m_geList.isRfaGeoAP(this.eiAnnounce) ? "1" : "0") + "'");
/*      */ 
/*      */ 
/*      */     
/* 3347 */     this.strCondition1 = getAttributeFlagEnabledValue(this.eiAnnounce, "COUNTRYLIST");
/* 3348 */     this.st = new StringTokenizer(this.strCondition1, "|");
/* 3349 */     this.bConditionOK = false;
/* 3350 */     while (this.st.hasMoreTokens()) {
/* 3351 */       this.strCondition2 = this.st.nextToken().trim();
/* 3352 */       logMessage("_15M:Got Country:" + this.i + ":" + this.strCondition2);
/* 3353 */       if (this.strCondition2.equals("1438") || this.strCondition2.equals("1642") || this.strCondition2
/* 3354 */         .equals("1629") || this.strCondition2.equals("1579") || this.strCondition2
/* 3355 */         .equals("1534") || this.strCondition2.equals("1513") || this.strCondition2
/* 3356 */         .equals("1450") || this.strCondition2.equals("1442") || this.strCondition2
/* 3357 */         .equals("1445")) {
/* 3358 */         this.bConditionOK = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 3363 */     println(".*$P_15M = '" + (this.bConditionOK ? "1" : "0") + "'");
/*      */     
/* 3365 */     println(".*$P_16A = '0'");
/* 3366 */     println(".*$P_16B = '0'");
/*      */     
/* 3368 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "CROSSPLATFORM", "");
/* 3369 */     println(".*$P_17A = '" + ((this.strCondition1.indexOf("Yes") > -1) ? "1" : "0") + "'");
/*      */     
/* 3371 */     println(".*$P_17B = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4767") ? "1" : "0") + "'");
/* 3372 */     println(".*$P_17C = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4770") ? "1" : "0") + "'");
/* 3373 */     println(".*$P_17D = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4769") ? "1" : "0") + "'");
/* 3374 */     println(".*$P_17E = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4772") ? "1" : "0") + "'");
/* 3375 */     println(".*$P_17F = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4764") ? "1" : "0") + "'");
/* 3376 */     println(".*$P_17G = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4768") ? "1" : "0") + "'");
/* 3377 */     println(".*$P_17H = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4766") ? "1" : "0") + "'");
/* 3378 */     println(".*$P_17I = '" + (flagvalueEquals(this.eiAnnounce, "PLATFORM", "4773") ? "1" : "0") + "'");
/*      */     
/* 3380 */     println(".*$P_17J = '" + (flagvalueEquals(this.eiAnnounce, "OFFERINGTYPES", "2907") ? "1" : "0") + "'");
/*      */ 
/*      */     
/* 3383 */     println(".*$P_17L = '" + (flagvalueEquals(this.eiAnnounce, "ELECTRONICSERVICE", "010") ? "1" : "0") + "'");
/* 3384 */     println(".*$P_17M = '" + (flagvalueEquals(this.eiAnnounce, "ELECTRONICSERVICE", "011") ? "1" : "0") + "'");
/*      */     
/* 3386 */     println(".*$P_18A = '" + (flagvalueEquals(this.eiAnnounce, "CONFIGSUPPORT", "677") ? "1" : "0") + "'");
/* 3387 */     println(".*$P_18B = '" + (flagvalueEquals(this.eiAnnounce, "CONFIGSUPPORT", "675") ? "1" : "0") + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3392 */     this.bConditionOK = false;
/* 3393 */     this.vReturnEntities2 = new Vector();
/* 3394 */     for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 3395 */       this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 3396 */       this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 3397 */       if (this.m_geList.isRfaGeoUS(this.eiConfigurator)) {
/* 3398 */         logMessage("P_19A US Configurator" + this.eiConfigurator.getEntityType() + ":" + this.eiConfigurator.getEntityID());
/* 3399 */         this.bConditionOK = true;
/* 3400 */         this.vReturnEntities2.addElement(this.eiConfigurator);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3405 */     this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(0) : null;
/*      */     
/* 3407 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3408 */     println(".*$P_19A = '" + this.strCondition2 + "'");
/* 3409 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3410 */     println(".*$P_19B = '" + this.strCondition2 + "'");
/*      */     
/* 3412 */     if (this.vReturnEntities2.size() > 1) {
/* 3413 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(1) : null;
/*      */     } else {
/*      */       
/* 3416 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3419 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3420 */     println(".*$P_19C = '" + this.strCondition2 + "'");
/* 3421 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3422 */     println(".*$P_19D = '" + this.strCondition2 + "'");
/*      */     
/* 3424 */     if (this.vReturnEntities2.size() > 2) {
/* 3425 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3428 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3431 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3432 */     println(".*$P_19E = '" + this.strCondition2 + "'");
/* 3433 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3434 */     println(".*$P_19F = '" + this.strCondition2 + "'");
/*      */     
/* 3436 */     if (this.vReturnEntities2.size() > 3) {
/* 3437 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3440 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3443 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3444 */     println(".*$P_19G= '" + this.strCondition2 + "'");
/* 3445 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3446 */     println(".*$P_19H = '" + this.strCondition2 + "'");
/*      */     
/* 3448 */     if (this.vReturnEntities2.size() > 4) {
/* 3449 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3452 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3455 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3456 */     println(".*$P_19I= '" + this.strCondition2 + "'");
/* 3457 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3458 */     println(".*$P_19J = '" + this.strCondition2 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3463 */     this.bConditionOK = false;
/* 3464 */     this.vReturnEntities2 = new Vector();
/* 3465 */     for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 3466 */       this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 3467 */       this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 3468 */       if (this.m_geList.isRfaGeoAP(this.eiConfigurator)) {
/* 3469 */         logMessage("P_20A ap Configurator" + this.eiConfigurator.getEntityType() + ":" + this.eiConfigurator.getEntityID());
/* 3470 */         this.bConditionOK = true;
/* 3471 */         this.vReturnEntities2.addElement(this.eiConfigurator);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3476 */     this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(0) : null;
/*      */     
/* 3478 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3479 */     println(".*$P_20A = '" + this.strCondition2 + "'");
/* 3480 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3481 */     println(".*$P_20B = '" + this.strCondition2 + "'");
/*      */     
/* 3483 */     if (this.vReturnEntities2.size() > 1) {
/* 3484 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(1) : null;
/*      */     } else {
/*      */       
/* 3487 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3490 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3491 */     println(".*$P_20C = '" + this.strCondition2 + "'");
/* 3492 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3493 */     println(".*$P_20D = '" + this.strCondition2 + "'");
/*      */     
/* 3495 */     if (this.vReturnEntities2.size() > 2) {
/* 3496 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3499 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3502 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3503 */     println(".*$P_20E = '" + this.strCondition2 + "'");
/* 3504 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3505 */     println(".*$P_20F = '" + this.strCondition2 + "'");
/*      */     
/* 3507 */     if (this.vReturnEntities2.size() > 3) {
/* 3508 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3511 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3514 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3515 */     println(".*$P_20G= '" + this.strCondition2 + "'");
/* 3516 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3517 */     println(".*$P_20H = '" + this.strCondition2 + "'");
/*      */     
/* 3519 */     if (this.vReturnEntities2.size() > 4) {
/* 3520 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3523 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3526 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3527 */     println(".*$P_20I= '" + this.strCondition2 + "'");
/* 3528 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3529 */     println(".*$P_20J = '" + this.strCondition2 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3534 */     this.bConditionOK = false;
/* 3535 */     this.vReturnEntities2 = new Vector();
/* 3536 */     for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 3537 */       this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 3538 */       this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 3539 */       if (this.m_geList.isRfaGeoLA(this.eiConfigurator)) {
/* 3540 */         logMessage("P_21A LA Configurator" + this.eiConfigurator.getEntityType() + ":" + this.eiConfigurator.getEntityID());
/* 3541 */         this.bConditionOK = true;
/* 3542 */         this.vReturnEntities2.addElement(this.eiConfigurator);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3547 */     this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(0) : null;
/*      */     
/* 3549 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3550 */     println(".*$P_21A = '" + this.strCondition2 + "'");
/* 3551 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3552 */     println(".*$P_21B = '" + this.strCondition2 + "'");
/*      */     
/* 3554 */     if (this.vReturnEntities2.size() > 1) {
/* 3555 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(1) : null;
/*      */     } else {
/*      */       
/* 3558 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3561 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3562 */     println(".*$P_21C = '" + this.strCondition2 + "'");
/* 3563 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3564 */     println(".*$P_21D = '" + this.strCondition2 + "'");
/*      */     
/* 3566 */     if (this.vReturnEntities2.size() > 2) {
/* 3567 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3570 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3573 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3574 */     println(".*$P_21E = '" + this.strCondition2 + "'");
/* 3575 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3576 */     println(".*$P_21F = '" + this.strCondition2 + "'");
/*      */     
/* 3578 */     if (this.vReturnEntities2.size() > 3) {
/* 3579 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3582 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3585 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3586 */     println(".*$P_21G= '" + this.strCondition2 + "'");
/* 3587 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3588 */     println(".*$P_21H = '" + this.strCondition2 + "'");
/*      */     
/* 3590 */     if (this.vReturnEntities2.size() > 4) {
/* 3591 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3594 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3597 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3598 */     println(".*$P_21I= '" + this.strCondition2 + "'");
/* 3599 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3600 */     println(".*$P_21J = '" + this.strCondition2 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3605 */     this.bConditionOK = false;
/* 3606 */     this.vReturnEntities2 = new Vector();
/* 3607 */     for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 3608 */       this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 3609 */       this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 3610 */       if (this.m_geList.isRfaGeoCAN(this.eiConfigurator)) {
/* 3611 */         logMessage("P_22a CANConfigurator" + this.eiConfigurator.getEntityType() + ":" + this.eiConfigurator.getEntityID());
/* 3612 */         this.bConditionOK = true;
/* 3613 */         this.vReturnEntities2.addElement(this.eiConfigurator);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3618 */     this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(0) : null;
/*      */     
/* 3620 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3621 */     println(".*$P_22A = '" + this.strCondition2 + "'");
/* 3622 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3623 */     println(".*$P_22B = '" + this.strCondition2 + "'");
/*      */     
/* 3625 */     if (this.vReturnEntities2.size() > 1) {
/* 3626 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(1) : null;
/*      */     } else {
/*      */       
/* 3629 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3632 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3633 */     println(".*$P_22C = '" + this.strCondition2 + "'");
/* 3634 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3635 */     println(".*$P_22D = '" + this.strCondition2 + "'");
/*      */     
/* 3637 */     if (this.vReturnEntities2.size() > 2) {
/* 3638 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3641 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3644 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3645 */     println(".*$P_22E = '" + this.strCondition2 + "'");
/* 3646 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3647 */     println(".*$P_22F = '" + this.strCondition2 + "'");
/*      */     
/* 3649 */     if (this.vReturnEntities2.size() > 3) {
/* 3650 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3653 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3656 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3657 */     println(".*$P_22G= '" + this.strCondition2 + "'");
/* 3658 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3659 */     println(".*$P_22H = '" + this.strCondition2 + "'");
/*      */     
/* 3661 */     if (this.vReturnEntities2.size() > 4) {
/* 3662 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3665 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3668 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3669 */     println(".*$P_22I= '" + this.strCondition2 + "'");
/* 3670 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3671 */     println(".*$P_22J = '" + this.strCondition2 + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3676 */     this.bConditionOK = false;
/* 3677 */     this.vReturnEntities2 = new Vector();
/* 3678 */     for (this.i = 0; this.i < this.grpAnnToConfig.getEntityItemCount(); this.i++) {
/* 3679 */       this.eiAnnToConfig = this.grpAnnToConfig.getEntityItem(this.i);
/* 3680 */       this.eiConfigurator = (EntityItem)this.eiAnnToConfig.getDownLink(0);
/* 3681 */       if (this.m_geList.isRfaGeoEMEA(this.eiConfigurator)) {
/* 3682 */         logMessage("P_23A EMEA Configurator" + this.eiConfigurator.getEntityType() + ":" + this.eiConfigurator.getEntityID());
/* 3683 */         this.bConditionOK = true;
/* 3684 */         this.vReturnEntities2.addElement(this.eiConfigurator);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3689 */     this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(0) : null;
/*      */     
/* 3691 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3692 */     println(".*$P_23A = '" + this.strCondition2 + "'");
/* 3693 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3694 */     println(".*$P_23B = '" + this.strCondition2 + "'");
/*      */     
/* 3696 */     if (this.vReturnEntities2.size() > 1) {
/* 3697 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(1) : null;
/*      */     } else {
/*      */       
/* 3700 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3703 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3704 */     println(".*$P_23C = '" + this.strCondition2 + "'");
/* 3705 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3706 */     println(".*$P_23D = '" + this.strCondition2 + "'");
/*      */     
/* 3708 */     if (this.vReturnEntities2.size() > 2) {
/* 3709 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3712 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3715 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3716 */     println(".*$P_23E = '" + this.strCondition2 + "'");
/* 3717 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3718 */     println(".*$P_23F = '" + this.strCondition2 + "'");
/*      */     
/* 3720 */     if (this.vReturnEntities2.size() > 3) {
/* 3721 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3724 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3727 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3728 */     println(".*$P_23G= '" + this.strCondition2 + "'");
/* 3729 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3730 */     println(".*$P_23H = '" + this.strCondition2 + "'");
/*      */     
/* 3732 */     if (this.vReturnEntities2.size() > 4) {
/* 3733 */       this.eiConfigurator = this.bConditionOK ? this.vReturnEntities2.elementAt(2) : null;
/*      */     } else {
/*      */       
/* 3736 */       this.bConditionOK = false;
/*      */     } 
/*      */     
/* 3739 */     this.strCondition2 = this.bConditionOK ? getAttributeShortFlagDesc(this.eiConfigurator, "CONFIGNAME", "") : "";
/* 3740 */     println(".*$P_23I= '" + this.strCondition2 + "'");
/* 3741 */     this.strCondition2 = this.bConditionOK ? getAttributeValue(this.eiAnnToConfig, "CONFIGAVAILDATE", "") : "";
/* 3742 */     println(".*$P_23J = '" + this.strCondition2 + "'");
/*      */     
/* 3744 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "OFFERINGACCESS", "");
/* 3745 */     println(".*$P_30A = '" + (this.strCondition1.equals("Yes") ? "1" : "0") + "'");
/* 3746 */     this.strCondition1 = getAttributeValue(this.eiAnnounce, "MARKETEDIBMLOGO", "");
/* 3747 */     println(".*$P_30B = '" + (this.strCondition1.equals("Yes") ? "1" : "0") + "'");
/* 3748 */     println(".*$P_30C = '" + (flagvalueEquals(this.eiAnnounce, "LOGOACCESSREQTS", "2833") ? "1" : "0") + "'");
/* 3749 */     println(".*$P_30D = '" + (flagvalueEquals(this.eiAnnounce, "LOGOACCESSREQTS", "2834") ? "1" : "0") + "'");
/* 3750 */     this.strCondition1 = getAttributeShortFlagDesc(this.eiAnnounce, "TGTCUSTOMERAUD", "");
/*      */     
/* 3752 */     this.strFilterAttr = new String[] { "CHANNELNAME", "ROUTESTOMKTG" };
/*      */     
/* 3754 */     this.strFilterValue = new String[] { "381", "110" };
/*      */     
/* 3756 */     this.vReturnEntities2 = searchEntityVectorLink(this.vSofFrmSofAvail, (String[])null, (String[])null, true, true, "SOFCHANNEL");
/* 3757 */     logMessage("****SOFCHANNEL*****");
/* 3758 */     displayContents(this.vReturnEntities2);
/* 3759 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 3760 */     logMessage("****CHANNEL*****");
/* 3761 */     displayContents(this.vReturnEntities3);
/* 3762 */     if (this.vReturnEntities3.size() == 0) {
/* 3763 */       this.vReturnEntities2 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTCHANNEL");
/* 3764 */       logMessage("****CMPNTCHANNEL*****");
/* 3765 */       displayContents(this.vReturnEntities2);
/* 3766 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 3767 */       logMessage("****CHANNEL*****");
/* 3768 */       displayContents(this.vReturnEntities3);
/*      */     } 
/* 3770 */     if (this.vReturnEntities3.size() == 0) {
/* 3771 */       this.vReturnEntities2 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATURECHANNEL");
/* 3772 */       logMessage("****FEATURECHANNEL*****");
/* 3773 */       displayContents(this.vReturnEntities1);
/* 3774 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 3775 */       logMessage("****CHANNEL*****");
/* 3776 */       displayContents(this.vReturnEntities3);
/*      */     } 
/* 3778 */     println(".*$P_31A = '" + ((this.vReturnEntities3.size() > 0) ? "1" : "0") + "'");
/*      */     
/* 3780 */     println(".*$P_43A = '" + ((this.strCondition1.length() > 61) ? this.strCondition1.substring(0, 61) : this.strCondition1) + "'");
/* 3781 */     println(".*$VARIABLES_END");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo100() {
/* 3788 */     println(".*$A_007_Begin");
/* 3789 */     println(getAttributeValue(this.eiAnnounce, "ANNIMAGES", " "));
/* 3790 */     println(".*$A_007_End");
/* 3791 */     println(".*$A_040_Begin");
/*      */ 
/*      */ 
/*      */     
/* 3795 */     this.strHeader = new String[] { "Role Type", "Name", "Telephone", "Node/ID" };
/*      */     
/* 3797 */     this.iColWidths = new int[] { 15, 18, 12, 17 };
/*      */     
/* 3799 */     this.strFilterAttr = new String[] { "ANNROLETYPE", "ANNROLETYPE", "ANNROLETYPE", "ANNROLETYPE" };
/*      */     
/* 3801 */     this.strFilterValue = new String[] { "11", "12", "13", "14" };
/*      */     
/* 3803 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnToOP, this.strFilterAttr, this.strFilterValue, false);
/* 3804 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3805 */       this.eiAnnToOP = this.vReturnEntities1.elementAt(this.i);
/* 3806 */       this.eiOP = (EntityItem)this.eiAnnToOP.getDownLink(0);
/* 3807 */       this.strCondition1 = getAttributeValue(this.eiAnnToOP, "ANNROLETYPE", " ");
/* 3808 */       this.vPrintDetails.add((this.strCondition1.length() >= 14) ? this.strCondition1.substring(0, 14) : this.strCondition1
/* 3809 */           .substring(0, this.strCondition1.length()));
/* 3810 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3811 */       this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3812 */       this.vPrintDetails.add(this.strCondition1);
/* 3813 */       this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3814 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3815 */       this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3816 */       this.vPrintDetails.add(this.strCondition1);
/*      */     } 
/* 3818 */     if (this.vPrintDetails.size() > 0) {
/* 3819 */       println("This RFA and its requested schedule hae been reviewed with the");
/* 3820 */       println("following functional representatives");
/* 3821 */       println(":xmp.");
/* 3822 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 3823 */       resetPrintvars();
/* 3824 */       println(":exmp.");
/*      */     } 
/* 3826 */     println(".*$A_040_End");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3832 */     println(".*$A_044_Begin");
/* 3833 */     println(":xmp.");
/* 3834 */     println(".in 0");
/* 3835 */     println(".kp off");
/* 3836 */     for (this.i = 0; this.i < this.grpRelatedANN.getEntityItemCount(); this.i++) {
/* 3837 */       this.eiRelatedANN = this.grpRelatedANN.getEntityItem(this.i);
/*      */       
/* 3839 */       for (this.j = 0; this.j < this.eiRelatedANN.getDownLinkCount(); this.j++) {
/* 3840 */         this.eiNextItem = (EntityItem)this.eiRelatedANN.getDownLink(this.j);
/* 3841 */         logMessage("**********_044 next item" + this.eiNextItem.getEntityType() + this.eiNextItem.getEntityID());
/* 3842 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem, "ANNTITLE", " "));
/* 3843 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem, "ANNNUMBER", " "));
/*      */       } 
/*      */     } 
/* 3846 */     this.strHeader = new String[] { "Announcement Title", "Announcement Number" };
/*      */     
/* 3848 */     this.iColWidths = new int[] { 50, 19 };
/*      */     
/* 3850 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 3851 */     resetPrintvars();
/* 3852 */     println("");
/*      */     
/* 3854 */     println(":exmp.");
/* 3855 */     println(".*$A_044_End");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3860 */     println(".*$A_045_Begin");
/* 3861 */     println(":xmp.");
/* 3862 */     println(".in 0");
/* 3863 */     println(":hp2.Being released to::ehp2.");
/* 3864 */     println(".kp off");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3869 */     this.vReturnEntities4 = searchEntityVectorLink(this.vSofFrmSofAvail, (String[])null, (String[])null, true, true, "SOFADMINOP");
/* 3870 */     logMessage("****SOFADMINOP*****");
/* 3871 */     displayContents(this.vReturnEntities4);
/*      */     
/* 3873 */     this.vReturnEntities2.removeAllElements();
/* 3874 */     this.vReturnEntities2.addAll(this.vReturnEntities4);
/* 3875 */     this.vReturnEntities3 = searchInGeo(this.vReturnEntities2, "US");
/* 3876 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "OP");
/* 3877 */     logMessage("****OP*****");
/* 3878 */     displayContents(this.vReturnEntities1);
/*      */     
/* 3880 */     resetPrintvars();
/* 3881 */     this.strEntityTypes = new String[] { "SOFADMINOP" };
/*      */     
/* 3883 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3884 */       this.eiOP = this.vReturnEntities1.elementAt(this.i);
/* 3885 */       logMessage("_045 US" + this.eiOP.getKey());
/* 3886 */       this.eiNextItem = getUplinkedEntityItem(this.eiOP, this.strEntityTypes);
/* 3887 */       this.strCondition1 = getGeoTags(this.eiNextItem);
/* 3888 */       logMessage("_045 US" + this.eiOP.getKey() + this.strCondition1);
/* 3889 */       if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/*      */ 
/*      */ 
/*      */         
/* 3893 */         logMessage("_045 US" + this.eiNextItem.getKey());
/* 3894 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem, "GENAREASELECTION", " "));
/* 3895 */         this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3896 */         this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3897 */         this.vPrintDetails.add(this.strCondition1);
/* 3898 */         this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3899 */         this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3900 */         this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3901 */         this.vPrintDetails.add(this.strCondition1);
/*      */       } 
/*      */     } 
/*      */     
/* 3905 */     this.vReturnEntities2.removeAllElements();
/* 3906 */     this.vReturnEntities2.addAll(this.vReturnEntities4);
/* 3907 */     this.vReturnEntities3 = searchInGeo(this.vReturnEntities2, "EMEA");
/* 3908 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "OP");
/* 3909 */     logMessage("****OP*****");
/* 3910 */     displayContents(this.vReturnEntities1);
/* 3911 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3912 */       this.eiOP = this.vReturnEntities1.elementAt(this.i);
/* 3913 */       this.eiNextItem = getUplinkedEntityItem(this.eiOP, this.strEntityTypes);
/* 3914 */       this.strCondition1 = getGeoTags(this.eiNextItem);
/* 3915 */       logMessage("_045 EMEA" + this.eiOP.getKey() + this.strCondition1);
/* 3916 */       if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/*      */ 
/*      */         
/* 3919 */         logMessage("_045 EMEA" + this.eiNextItem.getKey());
/* 3920 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem, "GENAREASELECTION", " "));
/* 3921 */         this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3922 */         this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3923 */         this.vPrintDetails.add(this.strCondition1);
/* 3924 */         this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3925 */         this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3926 */         this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3927 */         this.vPrintDetails.add(this.strCondition1);
/*      */       } 
/*      */     } 
/*      */     
/* 3931 */     this.vReturnEntities2.removeAllElements();
/* 3932 */     this.vReturnEntities2.addAll(this.vReturnEntities4);
/* 3933 */     this.vReturnEntities3 = searchInGeo(this.vReturnEntities2, "AP");
/* 3934 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "OP");
/* 3935 */     logMessage("****OP*****");
/* 3936 */     displayContents(this.vReturnEntities1);
/* 3937 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3938 */       this.eiOP = this.vReturnEntities1.elementAt(this.i);
/* 3939 */       this.eiNextItem = getUplinkedEntityItem(this.eiOP, this.strEntityTypes);
/* 3940 */       this.strCondition1 = getGeoTags(this.eiNextItem);
/* 3941 */       logMessage("_045 AP" + this.eiOP.getKey() + this.strCondition1);
/* 3942 */       if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/*      */ 
/*      */         
/* 3945 */         logMessage("_045 AP" + this.eiNextItem.getKey());
/* 3946 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem, "GENAREASELECTION", " "));
/* 3947 */         this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3948 */         this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3949 */         this.vPrintDetails.add(this.strCondition1);
/* 3950 */         this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3951 */         this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3952 */         this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3953 */         this.vPrintDetails.add(this.strCondition1);
/*      */       } 
/*      */     } 
/*      */     
/* 3957 */     this.vReturnEntities2.removeAllElements();
/* 3958 */     this.vReturnEntities2.addAll(this.vReturnEntities4);
/* 3959 */     this.vReturnEntities3 = searchInGeo(this.vReturnEntities2, "LA");
/* 3960 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "OP");
/* 3961 */     logMessage("****OP*****");
/* 3962 */     displayContents(this.vReturnEntities1);
/* 3963 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3964 */       this.eiOP = this.vReturnEntities1.elementAt(this.i);
/* 3965 */       this.eiNextItem = getUplinkedEntityItem(this.eiOP, this.strEntityTypes);
/* 3966 */       this.strCondition1 = getGeoTags(this.eiNextItem);
/* 3967 */       logMessage("_045 LA" + this.eiOP.getKey() + this.strCondition1);
/* 3968 */       if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/*      */ 
/*      */         
/* 3971 */         logMessage("_045 LA" + this.eiNextItem.getKey());
/* 3972 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem, "GENAREASELECTION", " "));
/* 3973 */         this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 3974 */         this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 3975 */         this.vPrintDetails.add(this.strCondition1);
/* 3976 */         this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 3977 */         this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 3978 */         this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 3979 */         this.vPrintDetails.add(this.strCondition1);
/*      */       } 
/*      */     } 
/*      */     
/* 3983 */     this.vReturnEntities2.removeAllElements();
/* 3984 */     this.vReturnEntities2.addAll(this.vReturnEntities4);
/* 3985 */     this.vReturnEntities3 = searchInGeo(this.vReturnEntities2, "CA");
/* 3986 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "OP");
/* 3987 */     logMessage("****OP*****");
/* 3988 */     displayContents(this.vReturnEntities1);
/* 3989 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 3990 */       this.eiOP = this.vReturnEntities1.elementAt(this.i);
/* 3991 */       this.eiNextItem = getUplinkedEntityItem(this.eiOP, this.strEntityTypes);
/* 3992 */       this.strCondition1 = getGeoTags(this.eiNextItem);
/* 3993 */       logMessage("_045 CA" + this.eiOP.getKey());
/* 3994 */       if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/*      */ 
/*      */         
/* 3997 */         logMessage("_045 CA" + this.eiNextItem.getKey());
/* 3998 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem, "GENAREASELECTION", " "));
/* 3999 */         this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
/* 4000 */         this.strCondition1 += getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 4001 */         this.vPrintDetails.add(this.strCondition1);
/* 4002 */         this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 4003 */         this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ") + "/";
/* 4004 */         this.strCondition1 += getAttributeValue(this.eiOP, "VNETUID", " ");
/* 4005 */         this.vPrintDetails.add(this.strCondition1);
/*      */       } 
/*      */     } 
/* 4008 */     this.strHeader = new String[] { "Geography", "Product Administrator", " Telephone", "    Node/Userid" };
/*      */     
/* 4010 */     this.iColWidths = new int[] { 10, 21, 12, 17 };
/*      */     
/* 4012 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 4013 */     resetPrintvars();
/*      */     
/* 4015 */     println(":exmp.");
/* 4016 */     println(".*$A_045_End");
/*      */     
/* 4018 */     println(".*$A_046_Begin");
/* 4019 */     println(getAttributeShortFlagDesc(this.eiAnnounce, "TELECOMMEQ", " "));
/* 4020 */     println(".*$A_046_End");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4025 */     println(".*$A_049_Begin");
/* 4026 */     println(":xmp.");
/* 4027 */     println(".kp off");
/* 4028 */     this.eiAnnToOP = null;
/* 4029 */     this.eiOP = null;
/* 4030 */     this.strFilterAttr = new String[] { "ANNROLETYPE" };
/*      */     
/* 4032 */     this.strFilterValue = new String[] { "5" };
/*      */     
/* 4034 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnToOP, this.strFilterAttr, this.strFilterValue, true);
/* 4035 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "OP");
/* 4036 */     this.strFilterAttr = new String[] { "ORGANUNITTYPE", "ORGANUNITTYPE" };
/*      */     
/* 4038 */     this.strFilterValue = new String[] { "4156", "4157" };
/*      */     
/* 4040 */     this.vReturnEntities3 = searchEntityGroupLink(this.grpAnnToOrgUnit, this.strFilterAttr, this.strFilterValue, false, true, "ORGANUNIT");
/*      */     
/* 4042 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 4043 */       this.eiOP = this.vReturnEntities2.elementAt(this.i);
/* 4044 */       this.strCondition2 = getAttributeValue(this.eiOP, "FIRSTNAME", " ");
/* 4045 */       this.strCondition1 = getAttributeValue(this.eiOP, "MIDDLENAME", " ");
/* 4046 */       this.strCondition2 += this.strCondition1.equals(" ") ? "" : (" " + this.strCondition1 + ".");
/* 4047 */       this.strCondition1 = getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 4048 */       this.strCondition2 += this.strCondition1.equals(" ") ? "" : (" " + this.strCondition1);
/* 4049 */       println("                     " + this.strCondition2);
/* 4050 */       println("                     " + getAttributeValue(this.eiOP, "JOBTITLE", " "));
/* 4051 */       for (this.j = 0; this.j < this.vReturnEntities3.size(); this.j++) {
/* 4052 */         this.eiOrganUnit = this.vReturnEntities3.elementAt(this.j);
/* 4053 */         println("                     " + getAttributeValue(this.eiOrganUnit, "NAME", " "));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4063 */     println(":exmp.");
/* 4064 */     println(".*$A_049_End");
/*      */     
/* 4066 */     println(".*$A_100_Begin");
/* 4067 */     prettyPrint(getAttributeValue(this.eiAnnounce, "ANNTITLE", " "), 69);
/* 4068 */     println(".*$A_100_End");
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
/*      */   private void processLongTo200() {
/* 4081 */     println(".*$A_104_Begin");
/* 4082 */     printVanillaSVSReport("FEATUREBENEFIT", true, false);
/* 4083 */     println(".*$A_104_End");
/*      */ 
/*      */     
/* 4086 */     println(".*$A_106_Begin");
/* 4087 */     printVanillaSVSReport("DIFFEATURESBENEFITS", true, false);
/* 4088 */     println(".*$A_106_End");
/*      */     
/* 4090 */     this.eiChannel = null;
/* 4091 */     this.strFilterAttr = new String[] { "AVAILTYPE" };
/*      */     
/* 4093 */     this.strFilterValue = new String[] { "146" };
/*      */     
/* 4095 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, this.strFilterAttr, this.strFilterValue, true, true, "AVAIL");
/* 4096 */     logMessage("_108****AVAIL*****");
/* 4097 */     displayContents(this.vReturnEntities1);
/*      */     
/* 4099 */     this.vReturnEntities2.removeAllElements();
/* 4100 */     this.vReturnEntities2 = searchInGeo(this.vReturnEntities1, new String[] { "US", "CAN", "LA", "AP" });
/*      */     
/* 4102 */     logMessage("_108****AVAIL-US/CAN/LA/AP*****");
/* 4103 */     displayContents(this.vReturnEntities2);
/* 4104 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, false, "SOFAVAIL");
/* 4105 */     logMessage("****SOFAVAIL*****");
/* 4106 */     displayContents(this.vReturnEntities3);
/* 4107 */     this.vReturnEntities5 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, false, "SOF");
/* 4108 */     logMessage("_108****SOF*****");
/* 4109 */     displayContents(this.vReturnEntities5);
/*      */     
/* 4111 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, false, "CMPNTAVAIL");
/* 4112 */     logMessage("****CMPNTAVAIL*****");
/* 4113 */     displayContents(this.vReturnEntities3);
/* 4114 */     this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, false, "CMPNT");
/* 4115 */     logMessage("_108****CMPNT*****");
/* 4116 */     displayContents(this.vReturnEntities4);
/* 4117 */     this.vReturnEntities5.addAll(this.vReturnEntities4);
/*      */     
/* 4119 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, false, "FEATUREAVAIL");
/* 4120 */     logMessage("****FEATUREAVAIL*****");
/* 4121 */     displayContents(this.vReturnEntities3);
/* 4122 */     this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, false, "FEATURE");
/* 4123 */     logMessage("_108****FEATURE*****");
/* 4124 */     displayContents(this.vReturnEntities4);
/*      */     
/* 4126 */     this.vReturnEntities5.addAll(this.vReturnEntities4);
/* 4127 */     logMessage("_108****SOFCMPNTFEATURE*****");
/* 4128 */     displayContents(this.vReturnEntities5);
/*      */     
/* 4130 */     this.vReturnEntities4 = sortEntities(this.vReturnEntities5, new String[] { "MKTGNAME" });
/* 4131 */     this.bConditionOK = false;
/* 4132 */     this.bConditionOK1 = false;
/* 4133 */     for (this.i = 0; this.i < this.vReturnEntities4.size(); this.i++) {
/* 4134 */       this.eiNextItem = this.vReturnEntities4.elementAt(this.i);
/* 4135 */       logMessage("_108****SOFCMPNTFEATURE*****" + this.eiNextItem.getKey());
/* 4136 */       this.strCondition2 = this.eiNextItem.getEntityType();
/* 4137 */       if (this.strCondition2.equals("SOF")) {
/* 4138 */         this.vReturnEntities3 = searchEntityItemLink(this.eiNextItem, (String[])null, (String[])null, true, true, "SOFCHANNEL");
/* 4139 */         this.strCondition3 = getSOFMktName(this.eiNextItem);
/*      */       }
/* 4141 */       else if (this.strCondition2.equals("CMPNT")) {
/* 4142 */         this.vReturnEntities3 = searchEntityItemLink(this.eiNextItem, (String[])null, (String[])null, true, true, "CMPNTCHANNEL");
/* 4143 */         this.strCondition3 = getCmptToSofMktMsg(this.eiNextItem);
/*      */       
/*      */       }
/* 4146 */       else if (this.strCondition2.equals("FEATURE")) {
/* 4147 */         this.vReturnEntities3 = searchEntityItemLink(this.eiNextItem, (String[])null, (String[])null, true, true, "FEATURECHANNEL");
/* 4148 */         this.strCondition3 = getfeatureToSofMktMsg(this.eiNextItem);
/*      */       } 
/*      */ 
/*      */       
/* 4152 */       for (byte b = 0; b < this.vReturnEntities3.size(); b++) {
/* 4153 */         this.eiNextItem1 = this.vReturnEntities3.elementAt(b);
/* 4154 */         this.eiChannel = (EntityItem)this.eiNextItem1.getDownLink(0);
/* 4155 */         if (flagvalueEquals(this.eiChannel, "ROUTESTOMKTG", "110")) {
/*      */ 
/*      */ 
/*      */           
/* 4159 */           this.strCondition1 = getGeoTags(this.eiChannel);
/*      */           
/* 4161 */           logMessage("A_108:" + this.eiNextItem.getKey() + ":" + this.eiChannel.getKey() + this.strCondition1);
/* 4162 */           if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && (this.m_geList
/* 4163 */             .isRfaGeoUS(this.eiChannel) || this.m_geList.isRfaGeoAP(this.eiChannel) || this.m_geList.isRfaGeoCAN(this.eiChannel) || this.m_geList
/* 4164 */             .isRfaGeoLA(this.eiChannel))) {
/* 4165 */             if (!this.bConditionOK) {
/* 4166 */               println(".*$A_108_Begin");
/* 4167 */               this.bConditionOK = true;
/* 4168 */               this.bConditionOK1 = true;
/*      */             } 
/* 4170 */             println("");
/* 4171 */             prettyPrint(this.strCondition3, 69);
/* 4172 */             println("");
/* 4173 */             this.strCondition1 = getAttributeValue(this.eiChannel, "CHANNELNAME", " ");
/* 4174 */             if (this.strCondition1.indexOf("*") > -1) {
/* 4175 */               println(":ul compact.");
/* 4176 */               this.st = new StringTokenizer(this.strCondition1, "*");
/* 4177 */               while (this.st.hasMoreTokens()) {
/* 4178 */                 println(":li." + this.st.nextToken().trim());
/*      */               }
/* 4180 */               println(":eul.");
/*      */             } 
/*      */           } else {
/*      */             
/* 4184 */             logMessage("_108:Bypassing " + this.eiChannel.getKey() + ":Tag is :" + this.strCondition1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 4189 */     if (this.bConditionOK1) {
/* 4190 */       println(".*$A_108_End");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4225 */     this.eiChannel = null;
/* 4226 */     this.strFilterAttr = new String[] { "AVAILTYPE" };
/*      */     
/* 4228 */     this.strFilterValue = new String[] { "146" };
/*      */     
/* 4230 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, this.strFilterAttr, this.strFilterValue, true, true, "AVAIL");
/* 4231 */     logMessage("_110****AVAIL*****");
/* 4232 */     displayContents(this.vReturnEntities1);
/*      */     
/* 4234 */     this.vReturnEntities2.removeAllElements();
/* 4235 */     this.vReturnEntities2 = searchInGeo(this.vReturnEntities1, "EMEA");
/* 4236 */     logMessage("_110****AVAIL-EMEA*****");
/* 4237 */     displayContents(this.vReturnEntities2);
/* 4238 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, false, "SOFAVAIL");
/* 4239 */     logMessage("****SOFAVAIL*****");
/* 4240 */     displayContents(this.vReturnEntities3);
/* 4241 */     this.vReturnEntities5 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, false, "SOF");
/* 4242 */     logMessage("_110****SOF*****");
/* 4243 */     displayContents(this.vReturnEntities5);
/*      */     
/* 4245 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, false, "CMPNTAVAIL");
/* 4246 */     logMessage("****CMPNTAVAIL*****");
/* 4247 */     displayContents(this.vReturnEntities3);
/* 4248 */     this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, false, "CMPNT");
/* 4249 */     logMessage("_110****CMPNT*****");
/* 4250 */     displayContents(this.vReturnEntities4);
/* 4251 */     this.vReturnEntities5.addAll(this.vReturnEntities4);
/*      */     
/* 4253 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, false, "FEATUREAVAIL");
/* 4254 */     logMessage("****FEATUREAVAIL*****");
/* 4255 */     displayContents(this.vReturnEntities3);
/* 4256 */     this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, false, "FEATURE");
/* 4257 */     logMessage("_110****FEATURE*****");
/* 4258 */     displayContents(this.vReturnEntities4);
/*      */     
/* 4260 */     this.vReturnEntities5.addAll(this.vReturnEntities4);
/* 4261 */     logMessage("_110****SOFCMPNTFEATURE*****");
/* 4262 */     displayContents(this.vReturnEntities5);
/*      */     
/* 4264 */     this.vReturnEntities4 = sortEntities(this.vReturnEntities5, new String[] { "MKTGNAME" });
/* 4265 */     this.bConditionOK = false;
/* 4266 */     this.bConditionOK1 = false;
/* 4267 */     for (this.i = 0; this.i < this.vReturnEntities4.size(); this.i++) {
/* 4268 */       this.eiNextItem = this.vReturnEntities4.elementAt(this.i);
/* 4269 */       logMessage("_110****SOFCMPNTFEATURE*****" + this.eiNextItem.getKey());
/* 4270 */       this.strCondition2 = this.eiNextItem.getEntityType();
/* 4271 */       if (this.strCondition2.equals("SOF")) {
/* 4272 */         this.vReturnEntities3 = searchEntityItemLink(this.eiNextItem, (String[])null, (String[])null, true, true, "SOFCHANNEL");
/* 4273 */         this.strCondition3 = getSOFMktName(this.eiNextItem);
/*      */       }
/* 4275 */       else if (this.strCondition2.equals("CMPNT")) {
/* 4276 */         this.vReturnEntities3 = searchEntityItemLink(this.eiNextItem, (String[])null, (String[])null, true, true, "CMPNTCHANNEL");
/* 4277 */         this.strCondition3 = getCmptToSofMktMsg(this.eiNextItem);
/*      */       
/*      */       }
/* 4280 */       else if (this.strCondition2.equals("FEATURE")) {
/* 4281 */         this.vReturnEntities3 = searchEntityItemLink(this.eiNextItem, (String[])null, (String[])null, true, true, "FEATURECHANNEL");
/* 4282 */         this.strCondition3 = getfeatureToSofMktMsg(this.eiNextItem);
/*      */       } 
/*      */ 
/*      */       
/* 4286 */       for (byte b = 0; b < this.vReturnEntities3.size(); b++) {
/* 4287 */         this.eiNextItem1 = this.vReturnEntities3.elementAt(b);
/* 4288 */         this.eiChannel = (EntityItem)this.eiNextItem1.getDownLink(0);
/* 4289 */         if (flagvalueEquals(this.eiChannel, "ROUTESTOMKTG", "110")) {
/*      */ 
/*      */ 
/*      */           
/* 4293 */           this.strCondition1 = getGeoTags(this.eiChannel);
/* 4294 */           logMessage("A_110:" + this.eiNextItem.getKey() + ":" + this.eiChannel.getKey() + this.strCondition1);
/* 4295 */           if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && this.m_geList.isRfaGeoEMEA(this.eiChannel)) {
/* 4296 */             if (!this.bConditionOK) {
/* 4297 */               println(".*$A_110_Begin");
/* 4298 */               this.bConditionOK = true;
/* 4299 */               this.bConditionOK1 = true;
/*      */             } 
/* 4301 */             println("");
/* 4302 */             prettyPrint(this.strCondition3, 69);
/* 4303 */             println("");
/* 4304 */             this.strCondition1 = getAttributeValue(this.eiChannel, "CHANNELNAME", " ");
/* 4305 */             if (this.strCondition1.indexOf("*") > -1) {
/* 4306 */               println(":ul compact.");
/* 4307 */               this.st = new StringTokenizer(this.strCondition1, "*");
/* 4308 */               while (this.st.hasMoreTokens()) {
/* 4309 */                 println(":li." + this.st.nextToken().trim());
/*      */               }
/* 4311 */               println(":eul.");
/*      */             } 
/*      */           } else {
/*      */             
/* 4315 */             logMessage("_110:Tag is :" + this.strCondition1);
/* 4316 */             logMessage("_110:EMEA is " + this.m_geList.isRfaGeoEMEA(this.eiChannel));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 4321 */     if (this.bConditionOK1) {
/* 4322 */       println(".*$A_110_End");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4328 */     println(".*$A_116_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4346 */     resetPrintvars();
/* 4347 */     println(":xmp.");
/* 4348 */     println(".kp off");
/* 4349 */     this.iTemp = this.vSofFrmSofAvail.size();
/* 4350 */     this.eiSof = (this.vSofFrmSofAvail.size() > 0) ? this.vSofFrmSofAvail.elementAt(0) : null;
/* 4351 */     this.strCondition1 = (this.eiSof != null) ? getSOFMktName(this.eiSof) : " ";
/* 4352 */     logMessage("****SOF*****");
/* 4353 */     displayContents(this.vReturnEntities1);
/*      */     
/* 4355 */     this.vReturnEntities2 = searchEntityVectorLink(this.vSofFrmSofAvail, (String[])null, (String[])null, true, true, "SOFCHANNEL");
/* 4356 */     logMessage("****SOFCHANNEL*****");
/* 4357 */     displayContents(this.vReturnEntities2);
/*      */     
/* 4359 */     this.strFilterAttr = new String[] { "ROUTESTOMKTG" };
/*      */     
/* 4361 */     this.strFilterValue = new String[] { "100" };
/*      */ 
/*      */     
/* 4364 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 4365 */     logMessage("****CHANNEL*****");
/* 4366 */     displayContents(this.vReturnEntities1);
/* 4367 */     this.bConditionOK = false;
/* 4368 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4369 */       this.eiChannel = this.vReturnEntities1.elementAt(this.i);
/* 4370 */       logMessage("_116" + this.eiChannel.getKey() + ":" + getGeoTags(this.eiChannel));
/* 4371 */       this.vPrintDetails.add(getAttributeValue(this.eiChannel, "CHANNELNAME", " ") + " ");
/* 4372 */       this
/*      */         
/* 4374 */         .bConditionOK = (this.m_geList.isRfaGeoAP(this.eiChannel) && this.m_geList.isRfaGeoCAN(this.eiChannel) && this.m_geList.isRfaGeoUS(this.eiChannel) && this.m_geList.isRfaGeoEMEA(this.eiChannel) && this.m_geList.isRfaGeoLA(this.eiChannel));
/* 4375 */       this.vPrintDetails.add(this.bConditionOK ? "X" : " ");
/* 4376 */       this.vPrintDetails.add((this.m_geList.isRfaGeoAP(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4377 */       this.vPrintDetails.add((this.m_geList.isRfaGeoCAN(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4378 */       this.vPrintDetails.add((this.m_geList.isRfaGeoUS(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4379 */       this.vPrintDetails.add((this.m_geList.isRfaGeoEMEA(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4380 */       this.vPrintDetails.add((this.m_geList.isRfaGeoLA(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/*      */     } 
/* 4382 */     if (this.vPrintDetails.size() > 0) {
/* 4383 */       this.strHeader = new String[] { "Route Description", "WW", "AP", "CAN", "US", "EMEA", "LA" };
/*      */       
/* 4385 */       this.iColWidths = new int[] { 26, 2, 2, 3, 2, 4, 2 };
/*      */       
/* 4387 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "SOFCHANNEL");
/* 4388 */       for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 4389 */         this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 4390 */         logMessage("_116 :Finally:" + this.eiNextItem.getKey());
/* 4391 */         this.eiSof = (EntityItem)this.eiNextItem.getUpLink(0);
/* 4392 */         logMessage("_116 :Finally:" + this.eiSof.getKey());
/* 4393 */         this.strCondition1 = getSOFMktName(this.eiSof);
/* 4394 */         println(this.strCondition1);
/*      */       } 
/* 4396 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 4397 */       resetPrintvars();
/* 4398 */       println("");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4404 */     this.iTemp = this.vCmpntFrmCmpntAvail.size();
/* 4405 */     this.eiComponent = (this.vCmpntFrmCmpntAvail.size() > 0) ? this.vCmpntFrmCmpntAvail.elementAt(0) : null;
/* 4406 */     this.strCondition1 = (this.vCmpntFrmCmpntAvail.size() > 0) ? getCmptToSofMktMsg(this.eiComponent) : " ";
/*      */     
/* 4408 */     this.vReturnEntities2 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTCHANNEL");
/* 4409 */     logMessage("****_116 CMPNTCHANNEL*****");
/* 4410 */     displayContents(this.vReturnEntities2);
/* 4411 */     this.strFilterAttr = new String[] { "ROUTESTOMKTG" };
/*      */     
/* 4413 */     this.strFilterValue = new String[] { "100" };
/*      */ 
/*      */     
/* 4416 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 4417 */     logMessage("****_116 CHANNEL*****");
/* 4418 */     displayContents(this.vReturnEntities1);
/*      */     
/* 4420 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4421 */       this.eiChannel = this.vReturnEntities1.elementAt(this.i);
/* 4422 */       logMessage("_116 from CMPNT" + this.eiChannel.getKey());
/* 4423 */       this.vPrintDetails.add(getAttributeValue(this.eiChannel, "CHANNELNAME", " ") + " ");
/* 4424 */       this.strCondition1 = getGeoTags(this.eiChannel);
/* 4425 */       logMessage("_116" + this.eiChannel.getKey() + ":" + getGeoTags(this.eiChannel));
/*      */       
/* 4427 */       this.bConditionOK = this.strCondition1.equals("US, AP, CAN, EMEA, LA");
/*      */       
/* 4429 */       this.vPrintDetails.add(this.bConditionOK ? "X" : " ");
/* 4430 */       this.vPrintDetails.add((this.m_geList.isRfaGeoAP(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4431 */       this.vPrintDetails.add((this.m_geList.isRfaGeoCAN(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4432 */       this.vPrintDetails.add((this.m_geList.isRfaGeoUS(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4433 */       this.vPrintDetails.add((this.m_geList.isRfaGeoEMEA(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4434 */       this.vPrintDetails.add((this.m_geList.isRfaGeoLA(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/*      */     } 
/* 4436 */     this.strHeader = new String[] { "Route Description", "WW", "AP", "CAN", "US", "EMEA", "LA" };
/*      */     
/* 4438 */     this.iColWidths = new int[] { 26, 2, 2, 3, 2, 4, 2 };
/*      */     
/* 4440 */     if (this.vReturnEntities1.size() > 0) {
/* 4441 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "CMPNTCHANNEL");
/* 4442 */       for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 4443 */         this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 4444 */         logMessage("_116 :Finally:" + this.eiNextItem.getKey());
/* 4445 */         this.eiComponent = (EntityItem)this.eiNextItem.getUpLink(0);
/* 4446 */         logMessage("_116 :Finally:" + this.eiComponent.getKey());
/* 4447 */         this.strCondition1 = getCmptToSofMktMsg(this.eiComponent);
/* 4448 */         println(this.strCondition1);
/*      */       } 
/*      */     } 
/* 4451 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 4452 */     resetPrintvars();
/* 4453 */     println("");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4458 */     this.iTemp = this.vFeatureFrmFeatureAvail.size();
/* 4459 */     this.eiFeature = (this.vFeatureFrmFeatureAvail.size() > 0) ? this.vFeatureFrmFeatureAvail.elementAt(0) : null;
/* 4460 */     this.strCondition1 = (this.eiFeature != null) ? getfeatureToSofMktMsg(this.eiFeature) : " ";
/*      */     
/* 4462 */     this.vReturnEntities2 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATURECHANNEL");
/* 4463 */     logMessage("****FEATURECHANNEL*****");
/* 4464 */     displayContents(this.vReturnEntities1);
/* 4465 */     this.strFilterAttr = new String[] { "ROUTESTOMKTG" };
/*      */     
/* 4467 */     this.strFilterValue = new String[] { "100" };
/*      */ 
/*      */     
/* 4470 */     this.vReturnEntities1 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, true, true, "CHANNEL");
/* 4471 */     logMessage("****_116 from FEATURE CHANNEL*****");
/* 4472 */     displayContents(this.vReturnEntities1);
/* 4473 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4474 */       this.eiChannel = this.vReturnEntities1.elementAt(this.i);
/* 4475 */       logMessage("_116 from FEATURE:" + this.eiChannel.getKey());
/* 4476 */       logMessage("_116" + this.eiChannel.getKey() + ":" + getGeoTags(this.eiChannel));
/* 4477 */       this.vPrintDetails.add(getAttributeValue(this.eiChannel, "CHANNELNAME", " ") + " ");
/* 4478 */       this.strCondition1 = getGeoTags(this.eiChannel);
/* 4479 */       this.bConditionOK = this.strCondition1.equals("US, AP, CAN, EMEA, LA");
/* 4480 */       this.vPrintDetails.add(this.bConditionOK ? "X" : " ");
/* 4481 */       this.vPrintDetails.add((this.m_geList.isRfaGeoAP(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4482 */       this.vPrintDetails.add((this.m_geList.isRfaGeoCAN(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4483 */       this.vPrintDetails.add((this.m_geList.isRfaGeoUS(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4484 */       this.vPrintDetails.add((this.m_geList.isRfaGeoEMEA(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/* 4485 */       this.vPrintDetails.add((this.m_geList.isRfaGeoLA(this.eiChannel) && !this.bConditionOK) ? "X" : " ");
/*      */     } 
/* 4487 */     this.strHeader = new String[] { "Route Description", "WW", "AP", "CAN", "US", "EMEA", "LA" };
/*      */     
/* 4489 */     this.iColWidths = new int[] { 26, 2, 2, 3, 2, 4, 2 };
/*      */     
/* 4491 */     if (this.vReturnEntities1.size() > 0) {
/* 4492 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "FEATURECHANNEL");
/* 4493 */       for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 4494 */         this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 4495 */         logMessage("_116 :Finally:" + this.eiNextItem.getKey());
/* 4496 */         this.eiFeature = (EntityItem)this.eiNextItem.getUpLink(0);
/* 4497 */         logMessage("_116 :Finally:" + this.eiFeature.getKey());
/* 4498 */         this.strCondition1 = getfeatureToSofMktMsg(this.eiFeature);
/* 4499 */         println(this.strCondition1);
/*      */       } 
/*      */     } 
/* 4502 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 4503 */     resetPrintvars();
/*      */     
/* 4505 */     println(":exmp.");
/*      */     
/* 4507 */     println(".*$A_116_End");
/*      */     
/* 4509 */     println(".*$A_118_Begin");
/* 4510 */     printVanillaSVSReport("MKTGSTRATEGY", true, false);
/* 4511 */     println(".*$A_118_End");
/*      */     
/* 4513 */     println(".*$A_119_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4521 */     printFeatureBenefit("110", "_119", true, true);
/*      */     
/* 4523 */     println(".*$A_119_End");
/*      */     
/* 4525 */     println(".*$A_120_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4535 */     printFeatureBenefit("100", "_120", true, true);
/*      */     
/* 4537 */     println(".*$A_120_End");
/*      */     
/* 4539 */     println(".*$A_122_Begin");
/* 4540 */     printVanillaSVSReport("CUSTWANTSNEEDS", true, false);
/* 4541 */     println(".*$A_122_End");
/*      */     
/* 4543 */     println(".*$A_123_Begin");
/* 4544 */     printVanillaSVSReport("CUSTPAINPT", true, false);
/* 4545 */     println(".*$A_123_End");
/*      */     
/* 4547 */     println(".*$A_124_Begin");
/* 4548 */     printVanillaSVSReport("RESOURSKILLSET", true, false);
/* 4549 */     println(".*$A_124_End");
/*      */     
/* 4551 */     println(".*$A_126_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4560 */     printFeatureBenefit("120", "_126", false, true);
/*      */     
/* 4562 */     println(".*$A_126_End");
/*      */     
/* 4564 */     println(".*$A_128_Begin");
/* 4565 */     printVanillaSVSReport("OTHERMKTGINFO", true, false);
/*      */     
/* 4567 */     println(".*$A_128_End");
/*      */     
/* 4569 */     println(".*$A_130_Begin");
/* 4570 */     this.strCondition2 = "";
/* 4571 */     this.strCondition4 = "";
/* 4572 */     resetPrintvars();
/* 4573 */     this.bConditionOK = false;
/* 4574 */     this.strHeader = new String[] { "Contact Name", "Telephone", "E-mail" };
/*      */     
/* 4576 */     this.iColWidths = new int[] { 25, 12, 29 };
/*      */     
/* 4578 */     this.strEntityTypes = new String[] { "SOFSALESCNTCTOP", "SOF" };
/*      */     
/* 4580 */     this.strCondition2 = "";
/*      */     
/* 4582 */     this.rfaReport.setPrintDupeLines(false);
/*      */     
/* 4584 */     logMessage("_130 Begin********************");
/* 4585 */     for (this.i = 0; this.i < this.vSofSortedbyMkt.size(); this.i++) {
/* 4586 */       this.eiSof = this.vSofSortedbyMkt.elementAt(this.i);
/*      */       
/* 4588 */       this.vReturnEntities2 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFSALESCNTCTOP");
/* 4589 */       logMessage("****SOFSALESCNTCTOP*****");
/* 4590 */       displayContents(this.vReturnEntities2);
/* 4591 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "OP");
/* 4592 */       logMessage("****OP*****");
/* 4593 */       displayContents(this.vReturnEntities3);
/* 4594 */       if (this.vReturnEntities3.size() != 0) {
/*      */ 
/*      */         
/* 4597 */         this.vReturnEntities4 = sortEntities(this.vReturnEntities3, new String[] { "LASTNAME", "FIRSTNAME" });
/*      */         
/* 4599 */         this.strCondition4 = getAttributeValue(this.eiSof, "MKTGNAME") + " ";
/* 4600 */         for (this.j = 0; this.j < this.vReturnEntities4.size(); this.j++) {
/* 4601 */           this.eiOP = this.vReturnEntities4.elementAt(this.j);
/*      */           
/* 4603 */           this.vReturnEntities2 = searchEntityItemLink(this.eiOP, (String[])null, (String[])null, true, false, "SOFSALESCNTCTOP");
/* 4604 */           for (byte b = 0; b < this.vReturnEntities2.size(); b++) {
/* 4605 */             this.eiNextItem = this.vReturnEntities2.elementAt(b);
/* 4606 */             this.eiNextItem1 = (EntityItem)this.eiNextItem.getUpLink(0);
/* 4607 */             if (this.eiNextItem1.getKey().equals(this.eiSof.getKey())) {
/*      */               break;
/*      */             }
/*      */           } 
/* 4611 */           this.strCondition1 = getGeoTags(this.eiNextItem);
/* 4612 */           logMessage("Getting GEO :" + this.strCondition1 + ":for:" + this.eiNextItem.getKey());
/* 4613 */           if (!this.strCondition1.equals(this.strCondition2)) {
/*      */             
/* 4615 */             if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 4616 */               this.strCondition2.length() > 0) {
/* 4617 */               this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 4618 */               this.vPrintDetails.add("");
/* 4619 */               this.vPrintDetails.add("");
/*      */             } 
/*      */ 
/*      */             
/* 4623 */             if (this.strCondition4.trim().length() > 0) {
/* 4624 */               this.vPrintDetails.add("");
/* 4625 */               this.vPrintDetails.add("");
/* 4626 */               this.vPrintDetails.add("");
/* 4627 */               this.vPrintDetails.add("$$BREAKHERE$$" + this.strCondition4 + ":p.");
/* 4628 */               logMessage("_130 $$BREAKHERE$$ " + this.strCondition4 + ":p.");
/* 4629 */               this.vPrintDetails.add("");
/* 4630 */               this.vPrintDetails.add("");
/*      */               
/* 4632 */               this.vPrintDetails.add("");
/* 4633 */               this.vPrintDetails.add("");
/* 4634 */               this.vPrintDetails.add("");
/* 4635 */               this.strCondition4 = "";
/*      */             } 
/*      */             
/* 4638 */             if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && 
/* 4639 */               this.strCondition1.trim().length() > 0) {
/* 4640 */               this.vPrintDetails.add("$$BREAKHERE$$:p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4641 */               this.vPrintDetails.add("");
/* 4642 */               this.vPrintDetails.add("");
/*      */             } 
/*      */             
/* 4645 */             this.strCondition2 = this.strCondition1;
/*      */           
/*      */           }
/* 4648 */           else if (this.strCondition4.trim().length() > 0) {
/* 4649 */             this.vPrintDetails.add("");
/* 4650 */             this.vPrintDetails.add("");
/* 4651 */             this.vPrintDetails.add("");
/* 4652 */             this.vPrintDetails.add("$$BREAKHERE$$" + this.strCondition4 + ":p.");
/* 4653 */             logMessage("_130 $$BREAKHERE$$ " + this.strCondition4 + ":p.");
/* 4654 */             this.vPrintDetails.add("");
/* 4655 */             this.vPrintDetails.add("");
/*      */             
/* 4657 */             this.vPrintDetails.add("");
/* 4658 */             this.vPrintDetails.add("");
/* 4659 */             this.vPrintDetails.add("");
/* 4660 */             this.strCondition4 = "";
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 4665 */           this.strCondition3 = getAttributeValue(this.eiOP, "FIRSTNAME", " ");
/* 4666 */           this.strCondition3 += " " + getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 4667 */           this.vPrintDetails.add(this.strCondition3);
/* 4668 */           this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 4669 */           this.vPrintDetails.add(getAttributeValue(this.eiOP, "EMAIL", " "));
/*      */         } 
/*      */       } 
/*      */     } 
/* 4673 */     if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA")) {
/* 4674 */       if (this.strCondition2.length() > 0) {
/* 4675 */         this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 4676 */         this.vPrintDetails.add("");
/* 4677 */         this.vPrintDetails.add("");
/*      */       } 
/* 4679 */       this.strCondition2 = this.strCondition1;
/*      */     } 
/*      */     
/* 4682 */     this.strEntityTypes = new String[] { "CMPNTSALESCNTCTOP", "CMPNT" };
/*      */     
/* 4684 */     this.strCondition2 = "";
/*      */     
/* 4686 */     logMessage("_130 Begin CMPNT********************");
/* 4687 */     for (this.i = 0; this.i < this.vCmptSortedbyMkt.size(); this.i++) {
/* 4688 */       this.eiComponent = this.vCmptSortedbyMkt.elementAt(this.i);
/*      */       
/* 4690 */       this.vReturnEntities2 = searchEntityItemLink(this.eiComponent, (String[])null, (String[])null, true, true, "CMPNTSALESCNTCTOP");
/* 4691 */       logMessage("****CMPNTSALESCNTCTOP*****");
/* 4692 */       displayContents(this.vReturnEntities2);
/* 4693 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "OP");
/* 4694 */       logMessage("****OP*****");
/* 4695 */       displayContents(this.vReturnEntities3);
/* 4696 */       if (this.vReturnEntities3.size() != 0) {
/*      */ 
/*      */         
/* 4699 */         this.vReturnEntities4 = sortEntities(this.vReturnEntities3, new String[] { "LASTNAME", "FIRSTNAME" });
/*      */         
/* 4701 */         this.strCondition4 = getCmptToSofMktMsg(this.eiComponent);
/* 4702 */         for (this.j = 0; this.j < this.vReturnEntities4.size(); this.j++) {
/* 4703 */           this.eiOP = this.vReturnEntities4.elementAt(this.j);
/*      */           
/* 4705 */           this.vReturnEntities2 = searchEntityItemLink(this.eiOP, (String[])null, (String[])null, true, false, "CMPNTSALESCNTCTOP");
/* 4706 */           for (byte b = 0; b < this.vReturnEntities2.size(); b++) {
/* 4707 */             this.eiNextItem = this.vReturnEntities2.elementAt(b);
/* 4708 */             this.eiNextItem1 = (EntityItem)this.eiNextItem.getUpLink(0);
/* 4709 */             if (this.eiNextItem1.getKey().equals(this.eiComponent.getKey())) {
/*      */               break;
/*      */             }
/*      */           } 
/* 4713 */           this.strCondition1 = getGeoTags(this.eiNextItem);
/* 4714 */           logMessage("Getting GEO :" + this.strCondition1 + ":for:" + this.eiNextItem.getKey());
/* 4715 */           if (!this.strCondition1.equals(this.strCondition2)) {
/* 4716 */             if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 4717 */               this.strCondition2.length() > 0) {
/* 4718 */               this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 4719 */               this.vPrintDetails.add("");
/* 4720 */               this.vPrintDetails.add("");
/*      */             } 
/*      */             
/* 4723 */             if (this.strCondition4.trim().length() > 0) {
/* 4724 */               this.vPrintDetails.add("");
/* 4725 */               this.vPrintDetails.add("");
/* 4726 */               this.vPrintDetails.add("");
/* 4727 */               this.vPrintDetails.add("$$BREAKHERE$$" + this.strCondition4 + ":p.");
/* 4728 */               logMessage("_130 $$BREAKHERE$$ " + this.strCondition4 + ":p.");
/* 4729 */               this.vPrintDetails.add("");
/* 4730 */               this.vPrintDetails.add("");
/*      */               
/* 4732 */               this.vPrintDetails.add("$$BREAKHERE$$");
/* 4733 */               this.vPrintDetails.add("");
/* 4734 */               this.vPrintDetails.add("");
/* 4735 */               this.strCondition4 = "";
/*      */             } 
/* 4737 */             if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && 
/* 4738 */               this.strCondition1.trim().length() > 0) {
/* 4739 */               this.vPrintDetails.add("$$BREAKHERE$$:p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4740 */               this.vPrintDetails.add("");
/* 4741 */               this.vPrintDetails.add("");
/*      */             } 
/*      */             
/* 4744 */             this.strCondition2 = this.strCondition1;
/*      */           
/*      */           }
/* 4747 */           else if (this.strCondition4.trim().length() > 0) {
/* 4748 */             this.vPrintDetails.add("");
/* 4749 */             this.vPrintDetails.add("");
/* 4750 */             this.vPrintDetails.add("");
/* 4751 */             this.vPrintDetails.add("$$BREAKHERE$$" + this.strCondition4 + ":p.");
/* 4752 */             logMessage("_130 $$BREAKHERE$$ " + this.strCondition4 + ":p.");
/* 4753 */             this.vPrintDetails.add("");
/* 4754 */             this.vPrintDetails.add("");
/*      */             
/* 4756 */             this.vPrintDetails.add("");
/* 4757 */             this.vPrintDetails.add("");
/* 4758 */             this.vPrintDetails.add("");
/* 4759 */             this.strCondition4 = "";
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 4764 */           this.strCondition3 = getAttributeValue(this.eiOP, "FIRSTNAME", " ");
/* 4765 */           this.strCondition3 += " " + getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 4766 */           logMessage("_130: OP is " + this.strCondition1 + "->" + this.strCondition2 + ":OP" + this.strCondition3);
/* 4767 */           this.vPrintDetails.add(this.strCondition3);
/* 4768 */           this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 4769 */           this.vPrintDetails.add(getAttributeValue(this.eiOP, "EMAIL", " "));
/*      */         } 
/*      */       } 
/* 4772 */     }  if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA")) {
/* 4773 */       if (this.strCondition2.length() > 0) {
/* 4774 */         this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 4775 */         this.vPrintDetails.add("");
/* 4776 */         this.vPrintDetails.add("");
/*      */       } 
/* 4778 */       this.strCondition2 = this.strCondition1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4783 */     this.vReturnEntities2 = searchEntityVectorLink(this.vFeatureSortedbyMkt, (String[])null, (String[])null, true, true, "FEATRSALESCNTCTOP");
/* 4784 */     logMessage("****FEATRSALESCNTCTOP*****");
/* 4785 */     displayContents(this.vReturnEntities2);
/* 4786 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "OP");
/* 4787 */     logMessage("****OP*****");
/* 4788 */     displayContents(this.vReturnEntities3);
/* 4789 */     this.strHeader = new String[] { "Contact Name", "Telephone", "E-mail" };
/*      */     
/* 4791 */     this.iColWidths = new int[] { 25, 12, 29 };
/*      */     
/* 4793 */     this.strEntityTypes = new String[] { "FEATRSALESCNTCTOP", "FEATURE" };
/*      */     
/* 4795 */     logMessage("_130 Begin FEATURE********************");
/* 4796 */     this.strCondition2 = "";
/* 4797 */     for (this.i = 0; this.i < this.vFeatureSortedbyMkt.size(); this.i++) {
/* 4798 */       this.eiFeature = this.vFeatureSortedbyMkt.elementAt(this.i);
/*      */       
/* 4800 */       this.vReturnEntities2 = searchEntityItemLink(this.eiFeature, (String[])null, (String[])null, true, true, "FEATRSALESCNTCTOP");
/* 4801 */       logMessage("****FEATRSALESCNTCTOP*****");
/* 4802 */       displayContents(this.vReturnEntities2);
/* 4803 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, (String[])null, (String[])null, true, true, "OP");
/* 4804 */       logMessage("****OP*****");
/* 4805 */       displayContents(this.vReturnEntities3);
/* 4806 */       if (this.vReturnEntities3.size() != 0) {
/*      */ 
/*      */         
/* 4809 */         this.vReturnEntities4 = sortEntities(this.vReturnEntities3, new String[] { "LASTNAME", "FIRSTNAME" });
/*      */         
/* 4811 */         this.strCondition4 = getfeatureToSofMktMsg(this.eiFeature);
/* 4812 */         for (this.j = 0; this.j < this.vReturnEntities4.size(); this.j++) {
/* 4813 */           this.eiOP = this.vReturnEntities4.elementAt(this.j);
/*      */           
/* 4815 */           this.vReturnEntities2 = searchEntityItemLink(this.eiOP, (String[])null, (String[])null, true, false, "FEATRSALESCNTCTOP");
/* 4816 */           for (byte b = 0; b < this.vReturnEntities2.size(); b++) {
/* 4817 */             this.eiNextItem = this.vReturnEntities2.elementAt(b);
/* 4818 */             this.eiNextItem1 = (EntityItem)this.eiNextItem.getUpLink(0);
/* 4819 */             if (this.eiNextItem1.getKey().equals(this.eiFeature.getKey())) {
/*      */               break;
/*      */             }
/*      */           } 
/* 4823 */           this.strCondition1 = getGeoTags(this.eiNextItem);
/* 4824 */           logMessage("Getting GEO :" + this.strCondition1 + ":for:" + this.eiNextItem.getKey());
/* 4825 */           if (!this.strCondition1.equals(this.strCondition2)) {
/*      */             
/* 4827 */             if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 4828 */               this.strCondition2.length() > 0) {
/* 4829 */               this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 4830 */               this.vPrintDetails.add("");
/* 4831 */               this.vPrintDetails.add("");
/*      */             } 
/*      */             
/* 4834 */             if (this.strCondition4.trim().length() > 0) {
/* 4835 */               this.vPrintDetails.add("");
/* 4836 */               this.vPrintDetails.add("");
/* 4837 */               this.vPrintDetails.add("");
/* 4838 */               this.vPrintDetails.add("$$BREAKHERE$$" + this.strCondition4 + ":p.");
/* 4839 */               logMessage("_130 $$BREAKHERE$$ " + this.strCondition4 + ":p.");
/* 4840 */               this.vPrintDetails.add("");
/* 4841 */               this.vPrintDetails.add("");
/*      */               
/* 4843 */               this.vPrintDetails.add("");
/* 4844 */               this.vPrintDetails.add("");
/* 4845 */               this.vPrintDetails.add("");
/* 4846 */               this.strCondition4 = "";
/*      */             } 
/* 4848 */             if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA") && 
/* 4849 */               this.strCondition1.trim().length() > 0) {
/* 4850 */               this.vPrintDetails.add("$$BREAKHERE$$:p.:hp2." + this.strCondition1 + "--->:ehp2.");
/* 4851 */               this.vPrintDetails.add("");
/* 4852 */               this.vPrintDetails.add("");
/*      */             } 
/*      */             
/* 4855 */             this.strCondition2 = this.strCondition1;
/*      */           
/*      */           }
/* 4858 */           else if (this.strCondition4.trim().length() > 0) {
/* 4859 */             this.vPrintDetails.add("");
/* 4860 */             this.vPrintDetails.add("");
/* 4861 */             this.vPrintDetails.add("");
/* 4862 */             this.vPrintDetails.add("$$BREAKHERE$$" + this.strCondition4 + ":p.");
/* 4863 */             logMessage("_130 $$BREAKHERE$$ " + this.strCondition4 + ":p.");
/* 4864 */             this.vPrintDetails.add("");
/* 4865 */             this.vPrintDetails.add("");
/*      */             
/* 4867 */             this.vPrintDetails.add("");
/* 4868 */             this.vPrintDetails.add("");
/* 4869 */             this.vPrintDetails.add("");
/* 4870 */             this.strCondition4 = "";
/*      */           } 
/*      */ 
/*      */           
/* 4874 */           this.strCondition3 = getAttributeValue(this.eiOP, "FIRSTNAME", " ");
/* 4875 */           this.strCondition3 += " " + getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 4876 */           this.vPrintDetails.add(this.strCondition3);
/* 4877 */           this.vPrintDetails.add(getAttributeValue(this.eiOP, "TELEPHONE", " "));
/* 4878 */           this.vPrintDetails.add(getAttributeValue(this.eiOP, "EMAIL", " "));
/*      */         } 
/*      */       } 
/* 4881 */     }  if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA")) {
/* 4882 */       if (this.strCondition2.length() > 0) {
/* 4883 */         this.vPrintDetails.add("$$BREAKHERE$$.br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/* 4884 */         this.vPrintDetails.add("");
/* 4885 */         this.vPrintDetails.add("");
/*      */       } 
/* 4887 */       this.strCondition2 = this.strCondition1;
/*      */     } 
/* 4889 */     if (this.vPrintDetails.size() > 0) {
/* 4890 */       println(":xmp.");
/* 4891 */       println(".kp off");
/* 4892 */       println(".in 0");
/*      */     } 
/* 4894 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 4895 */     this.rfaReport.setPrintDupeLines(true);
/* 4896 */     if (this.vPrintDetails.size() > 0) {
/* 4897 */       println(":exmp.");
/*      */     }
/* 4899 */     resetPrintvars();
/* 4900 */     println(".*$A_130_End");
/*      */     
/* 4902 */     println(".*$A_132_Begin");
/*      */     
/* 4904 */     printVanillaSVSReport("SALESACTREQ", true, false);
/*      */ 
/*      */     
/* 4907 */     println(".*$A_132_End");
/*      */     
/* 4909 */     println(".*$A_134_Begin");
/*      */     
/* 4911 */     printVanillaSVSReport("SALESAPPROACH", true, false);
/*      */     
/* 4913 */     println(".*$A_134_End");
/*      */     
/* 4915 */     println(".*$A_136_Begin");
/* 4916 */     printVanillaSVSReport("CUSTCANDGUIDELINES", true, false);
/* 4917 */     println(".*$A_136_End");
/*      */     
/* 4919 */     println(".*$A_138_Begin");
/* 4920 */     printVanillaSVSReport("CUSTRESTRICTIONS", true, false);
/*      */     
/* 4922 */     println(".*$A_138_End");
/*      */     
/* 4924 */     println(".*$A_140_Begin");
/* 4925 */     printVanillaSVSReport("HANDOBJECTIONS", true, false);
/* 4926 */     println(".*$A_140_End");
/*      */     
/* 4928 */     println(".*$A_144_Begin");
/* 4929 */     printVanillaSVSReport("COMPETITIVEOF", true, false);
/* 4930 */     println(".*$A_144_End");
/*      */     
/* 4932 */     println(".*$A_146_Begin");
/* 4933 */     printVanillaSVSReport("STRENGTHWEAKNESS", true, false);
/* 4934 */     println(".*$A_146_End");
/*      */     
/* 4936 */     println(".*$A_148_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4942 */     this.strHeader = new String[] { "Offering Name" };
/*      */     
/* 4944 */     this.iColWidths = new int[] { 69 };
/*      */ 
/*      */     
/* 4947 */     this.vReturnEntities1 = searchEntityVectorLink(this.vSofFrmSofAvail, (String[])null, (String[])null, true, true, "SOFPRICE");
/*      */     
/* 4949 */     this.vReturnEntities2 = new Vector();
/* 4950 */     logMessage("A_148:From SOF");
/* 4951 */     displayContents(this.vReturnEntities1);
/* 4952 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4953 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 4954 */       logMessage("A_148:" + this.eiNextItem.getKey());
/* 4955 */       this.eiSof = (EntityItem)this.eiNextItem.getUpLink(0);
/* 4956 */       this.eiPriceInfo = (EntityItem)this.eiNextItem.getDownLink(0);
/* 4957 */       logMessage("A_148:" + this.eiSof.getKey());
/* 4958 */       logMessage("A_148:" + this.eiPriceInfo.getKey());
/* 4959 */       this.strCondition1 = getAttributeValue(this.eiPriceInfo, "BILLINGAPP", " ");
/* 4960 */       this.strCondition1 += ":" + this.eiSof.getKey();
/*      */       
/* 4962 */       this.vReturnEntities2.add(this.strCondition1);
/*      */     } 
/*      */     
/* 4965 */     this.vReturnEntities1 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTPRICE");
/* 4966 */     logMessage("A_148:From CMPNTPRICE");
/* 4967 */     displayContents(this.vReturnEntities1);
/* 4968 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4969 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 4970 */       logMessage("A_148:" + this.eiNextItem.getKey());
/* 4971 */       this.eiComponent = (EntityItem)this.eiNextItem.getUpLink(0);
/* 4972 */       this.eiPriceInfo = (EntityItem)this.eiNextItem.getDownLink(0);
/* 4973 */       logMessage("A_148:" + this.eiComponent.getKey());
/* 4974 */       logMessage("A_148:" + this.eiPriceInfo.getKey());
/* 4975 */       this.strCondition1 = getAttributeValue(this.eiPriceInfo, "BILLINGAPP", " ");
/* 4976 */       this.strCondition1 += ":" + this.eiComponent.getKey();
/*      */       
/* 4978 */       this.vReturnEntities2.add(this.strCondition1);
/*      */     } 
/*      */     
/* 4981 */     this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATUREPRICE");
/* 4982 */     logMessage("A_148:From CMPNTPRICE");
/* 4983 */     displayContents(this.vReturnEntities1);
/* 4984 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 4985 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 4986 */       logMessage("A_148:" + this.eiNextItem.getKey());
/* 4987 */       this.eiFeature = (EntityItem)this.eiNextItem.getUpLink(0);
/* 4988 */       this.eiPriceInfo = (EntityItem)this.eiNextItem.getDownLink(0);
/* 4989 */       logMessage("A_148:" + this.eiFeature.getKey());
/* 4990 */       logMessage("A_148:" + this.eiPriceInfo.getKey());
/* 4991 */       this.strCondition1 = getAttributeValue(this.eiPriceInfo, "BILLINGAPP", " ");
/* 4992 */       this.strCondition1 += ":" + this.eiFeature.getKey();
/*      */       
/* 4994 */       this.vReturnEntities2.add(this.strCondition1);
/*      */     } 
/*      */     
/* 4997 */     resetPrintvars();
/*      */     
/* 4999 */     this.vReturnEntities3 = this.mySort.alphabetizeVector(this.vReturnEntities2);
/* 5000 */     if (this.vReturnEntities3.size() > 0) {
/* 5001 */       println(":xmp.");
/* 5002 */       println(".kp off");
/* 5003 */       println(".in 0");
/* 5004 */       println("");
/* 5005 */       resetPrintvars();
/* 5006 */       this.strCondition1 = this.vReturnEntities3.elementAt(0);
/* 5007 */       if (this.strCondition1.indexOf(":CMPNT") > 0) {
/* 5008 */         this.strCondition2 = this.strCondition1.substring(0, this.strCondition1.indexOf(":CMPNT"));
/*      */       }
/* 5010 */       else if (this.strCondition1.indexOf(":FEATURE") > 0) {
/* 5011 */         this.strCondition2 = this.strCondition1.substring(0, this.strCondition1.indexOf(":FEATURE"));
/*      */       }
/* 5013 */       else if (this.strCondition1.indexOf(":SOF") > 0) {
/* 5014 */         this.strCondition2 = this.strCondition1.substring(0, this.strCondition1.indexOf(":SOF"));
/*      */       } 
/* 5016 */       print("Billing Application: ");
/* 5017 */       this.bConditionOK = false;
/* 5018 */       if (this.strCondition2.indexOf("*") > -1) {
/* 5019 */         this.st = new StringTokenizer(this.strCondition2, "*");
/* 5020 */         while (this.st.hasMoreTokens()) {
/* 5021 */           if (this.bConditionOK) {
/* 5022 */             print("                     ");
/*      */           }
/* 5024 */           this.bConditionOK = true;
/* 5025 */           println(this.st.nextToken().trim());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 5030 */     for (this.i = 0; this.i < this.vReturnEntities3.size(); this.i++) {
/* 5031 */       logMessage("A_148:Sorted Vector returning: " + this.strCondition1);
/* 5032 */       this.strCondition1 = this.vReturnEntities3.elementAt(this.i);
/* 5033 */       if (this.strCondition1.indexOf(":CMPNT") > 0) {
/* 5034 */         this.strCondition4 = this.strCondition1.substring(0, this.strCondition1.indexOf(":CMPNT"));
/*      */       }
/* 5036 */       else if (this.strCondition1.indexOf(":FEATURE") > 0) {
/* 5037 */         this.strCondition4 = this.strCondition1.substring(0, this.strCondition1.indexOf(":FEATURE"));
/*      */       }
/* 5039 */       else if (this.strCondition1.indexOf(":SOF") > 0) {
/* 5040 */         this.strCondition4 = this.strCondition1.substring(0, this.strCondition1.indexOf(":SOF"));
/*      */       } 
/* 5042 */       logMessage("A_148 Current Mkt:" + this.strCondition4);
/* 5043 */       logMessage("A_148 Prev Mkt :" + this.strCondition2);
/* 5044 */       if (!this.strCondition2.equals(this.strCondition4)) {
/* 5045 */         logMessage("A_148 Current < New");
/* 5046 */         this.strCondition2 = this.strCondition4;
/* 5047 */         printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5048 */         println("");
/* 5049 */         print("Billing Application: ");
/* 5050 */         this.bConditionOK = false;
/* 5051 */         if (this.strCondition2.indexOf("*") > -1) {
/* 5052 */           this.st = new StringTokenizer(this.strCondition2, "*");
/* 5053 */           while (this.st.hasMoreTokens()) {
/* 5054 */             if (this.bConditionOK) {
/* 5055 */               print("                     ");
/*      */             }
/* 5057 */             println(this.st.nextToken().trim());
/* 5058 */             this.bConditionOK = true;
/*      */           } 
/*      */         } 
/* 5061 */         resetPrintvars();
/*      */       } 
/*      */       
/* 5064 */       if (this.strCondition1.indexOf(":CMPNT") > 0) {
/* 5065 */         this.strCondition5 = this.strCondition1.substring(this.strCondition1.indexOf(":CMPNT") + 1);
/* 5066 */         logMessage("A_155:Parsed out" + this.strCondition5);
/* 5067 */         this.eiComponent = this.egComponent.getEntityItem(this.strCondition5);
/* 5068 */         this.strCondition1 = getCmptToSofMktMsg(this.eiComponent);
/* 5069 */         this.vPrintDetails.add(this.strCondition1);
/* 5070 */         logMessage("A_148:Print:" + this.strCondition1);
/*      */         
/* 5072 */         logMessage("A_148:Print:" + getAttributeValue(this.eiComponent, "COMPONENTID", ""));
/*      */       }
/* 5074 */       else if (this.strCondition1.indexOf(":FEATURE") > 0) {
/* 5075 */         this.strCondition5 = this.strCondition1.substring(this.strCondition1.indexOf(":FEATURE") + 1);
/* 5076 */         logMessage("A_155:Parsed out" + this.strCondition5);
/* 5077 */         this.eiFeature = this.egFeature.getEntityItem(this.strCondition5);
/* 5078 */         this.strCondition1 = getfeatureToSofMktMsg(this.eiFeature);
/* 5079 */         this.vPrintDetails.add(this.strCondition1);
/*      */ 
/*      */         
/* 5082 */         logMessage("A_148:Print:" + this.strCondition1);
/*      */       }
/* 5084 */       else if (this.strCondition1.indexOf(":SOF") > 0) {
/* 5085 */         this.strCondition5 = this.strCondition1.substring(this.strCondition1.indexOf(":SOF") + 1);
/* 5086 */         logMessage("A_148:Parsed out" + this.strCondition5);
/* 5087 */         this.eiSof = this.egSof.getEntityItem(this.strCondition5);
/* 5088 */         this.strCondition1 = getSOFMktName(this.eiSof);
/* 5089 */         this.vPrintDetails.add(this.strCondition1);
/*      */ 
/*      */         
/* 5092 */         logMessage("A_148:Print:" + this.strCondition1);
/* 5093 */         logMessage("A_148:Print:" + getAttributeValue(this.eiSof, "OFIDNUMBER", " "));
/*      */       } 
/*      */     } 
/*      */     
/* 5097 */     if (this.vPrintDetails.size() > 0) {
/* 5098 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5099 */       resetPrintvars();
/*      */     } 
/*      */     
/* 5102 */     if (this.vReturnEntities3.size() > 0) {
/* 5103 */       println(":exmp.");
/*      */     }
/*      */     
/* 5106 */     println(".*$A_148_End");
/*      */     
/* 5108 */     println(".*$A_150_Begin");
/* 5109 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/*      */     
/* 5111 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "110", "", true);
/* 5112 */     this.iColWidths = new int[] { 69 };
/*      */     
/* 5114 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5115 */     resetPrintvars();
/* 5116 */     println(".*$A_150_End");
/*      */     
/* 5118 */     println(".*$A_151_Begin");
/*      */     
/* 5120 */     this.strFilterAttr = new String[] { "DELIVERABLETYPE" };
/*      */     
/* 5122 */     this.strFilterValue = new String[] { "852" };
/*      */     
/* 5124 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnDeliv, this.strFilterAttr, this.strFilterValue, true);
/* 5125 */     logMessage("A_151****ANNDELIVERABLE");
/* 5126 */     displayContents(this.vReturnEntities1);
/* 5127 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "ANNDELREQTRANS");
/* 5128 */     logMessage("A_151****ANNDELREQTRANS");
/* 5129 */     displayContents(this.vReturnEntities2);
/* 5130 */     this.strFilterAttr = new String[] { "LANGUAGES", "LANGUAGES", "LANGUAGES", "LANGUAGES" };
/*      */     
/* 5132 */     this.strFilterValue = new String[] { "2802", "2803", "2797", "2796" };
/*      */     
/* 5134 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "EMEATRANSLATION");
/*      */     
/* 5136 */     logMessage("A_151****EMEATRANSLATION");
/* 5137 */     displayContents(this.vReturnEntities3);
/* 5138 */     this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "TRANSDELREVIEW");
/* 5139 */     logMessage("A_151****TRANSDELREVIEW");
/* 5140 */     displayContents(this.vReturnEntities4);
/* 5141 */     for (this.i = 0; this.i < this.vReturnEntities4.size(); this.i++) {
/* 5142 */       this.eiNextItem = this.vReturnEntities4.elementAt(this.i);
/* 5143 */       logMessage("A_287****" + this.eiNextItem.getEntityType() + ":" + this.eiNextItem.getEntityID());
/* 5144 */       this.eiEmeaTranslation = (EntityItem)this.eiNextItem.getUpLink(0);
/* 5145 */       this.eiOP = (EntityItem)this.eiNextItem.getDownLink(0);
/* 5146 */       this.vPrintDetails.add(getAttributeValue(this.eiEmeaTranslation, "LANGUAGES", " "));
/* 5147 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1);
/* 5148 */       this.strCondition1 += ". " + getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 5149 */       this.vPrintDetails.add(this.strCondition1);
/* 5150 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ");
/* 5151 */       this.strCondition1 += "/" + getAttributeValue(this.eiOP, "VNETUID", " ");
/* 5152 */       this.vPrintDetails.add(this.strCondition1);
/*      */     } 
/*      */     
/* 5155 */     this.strHeader = new String[] { "Language", "Brand Reviewer Name", "  Node/Userid" };
/*      */     
/* 5157 */     this.iColWidths = new int[] { 17, 30, 17 };
/*      */     
/* 5159 */     println(":h4.Worldwide Customer Letter Translation");
/* 5160 */     println(":xmp.");
/* 5161 */     println(".kp off");
/* 5162 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5163 */     resetPrintvars();
/* 5164 */     println(":exmp.");
/* 5165 */     println(":p.Note: This section is deleted at PLET generation time.");
/* 5166 */     println(".*$A_151_End");
/*      */     
/* 5168 */     println(".*$A_152_Begin");
/*      */ 
/*      */     
/* 5171 */     this.strFilterAttr = new String[] { "DELIVERABLETYPE" };
/*      */     
/* 5173 */     this.strFilterValue = new String[] { "856" };
/*      */     
/* 5175 */     this.vReturnEntities1 = searchEntityGroup(this.grpAnnDeliv, this.strFilterAttr, this.strFilterValue, true);
/* 5176 */     logMessage("A_152****ANNDELIVERABLE");
/* 5177 */     displayContents(this.vReturnEntities1);
/* 5178 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "ANNDELREQTRANS");
/* 5179 */     logMessage("A_152****ANNDELREQTRANS");
/* 5180 */     displayContents(this.vReturnEntities2);
/* 5181 */     this.strFilterAttr = new String[] { "LANGUAGES", "LANGUAGES", "LANGUAGES", "LANGUAGES" };
/*      */     
/* 5183 */     this.strFilterValue = new String[] { "2802", "2803", "2797", "2796" };
/*      */     
/* 5185 */     this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities2, this.strFilterAttr, this.strFilterValue, false, true, "EMEATRANSLATION");
/*      */     
/* 5187 */     logMessage("A_152****EMEATRANSLATION");
/* 5188 */     displayContents(this.vReturnEntities3);
/* 5189 */     this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities3, (String[])null, (String[])null, true, true, "TRANSDELREVIEW");
/* 5190 */     logMessage("A_152****TRANSDELREVIEW");
/* 5191 */     displayContents(this.vReturnEntities4);
/* 5192 */     this.vPrintDetails1 = new Vector();
/* 5193 */     for (this.i = 0; this.i < this.vReturnEntities4.size(); this.i++) {
/* 5194 */       this.eiNextItem = this.vReturnEntities4.elementAt(this.i);
/* 5195 */       this.eiEmeaTranslation = (EntityItem)this.eiNextItem.getUpLink(0);
/* 5196 */       this.eiOP = (EntityItem)this.eiNextItem.getDownLink(0);
/* 5197 */       logMessage("A_152****" + this.eiNextItem.getEntityType() + ":" + this.eiNextItem.getEntityID());
/* 5198 */       this.vPrintDetails.add(getAttributeValue(this.eiEmeaTranslation, "LANGUAGES", " "));
/* 5199 */       this.strCondition1 = getAttributeValue(this.eiOP, "FIRSTNAME", " ").substring(0, 1);
/* 5200 */       this.strCondition1 += ". " + getAttributeValue(this.eiOP, "LASTNAME", " ");
/* 5201 */       this.vPrintDetails.add(this.strCondition1);
/* 5202 */       this.strCondition1 = getAttributeValue(this.eiOP, "VNETNODE", " ");
/* 5203 */       this.strCondition1 += "/" + getAttributeValue(this.eiOP, "VNETUID", " ");
/* 5204 */       this.vPrintDetails.add(this.strCondition1);
/* 5205 */       this.vPrintDetails1.add(getAttributeValue(this.eiNextItem, "PROPOSALINSERTID", " "));
/*      */     } 
/*      */     
/* 5208 */     this.strHeader = new String[] { "Language", "Brand Reviewer Name", "  Node/Userid" };
/*      */     
/* 5210 */     this.iColWidths = new int[] { 17, 30, 17 };
/*      */     
/* 5212 */     println(":xmp.");
/* 5213 */     println(".kp off");
/* 5214 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5215 */     resetPrintvars();
/* 5216 */     this.strCondition1 = "";
/* 5217 */     for (this.i = 0, this.j = 0; this.i < this.vPrintDetails1.size(); this.i++) {
/* 5218 */       this.strCondition1 += "PINo: " + (String)this.vPrintDetails1.elementAt(this.i) + "    ";
/* 5219 */       if (this.j == 3) {
/* 5220 */         println("Proposal Insert Document Ids");
/* 5221 */         println(this.strCondition1);
/* 5222 */         this.strCondition1 = "";
/* 5223 */         this.j = 0;
/*      */       } 
/*      */     } 
/* 5226 */     if (this.i < 3) {
/* 5227 */       println("Proposal Insert Document Ids");
/*      */     }
/*      */     
/* 5230 */     println(this.strCondition1);
/* 5231 */     println(":exmp.");
/* 5232 */     println(":p.Note: This section is deleted at PLET generation time.");
/*      */     
/* 5234 */     println(".*$A_152_End");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5243 */     println(".*$A_153_Begin");
/* 5244 */     logMessage("A_153_Begin");
/*      */ 
/*      */     
/* 5247 */     printA153();
/* 5248 */     resetPrintvars();
/* 5249 */     println(".*$A_153_End");
/*      */     
/* 5251 */     println(".*$A_154_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5257 */     this.strCondition2 = "";
/* 5258 */     this.vReturnEntities1 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, false, "SOFCMPNT");
/* 5259 */     this.vReturnEntities2 = new Vector();
/* 5260 */     this.strSofToCmpt = new String[] { "SOFCMPNT", "CMPNT" };
/*      */     
/* 5262 */     logMessage("A_154:From Component");
/* 5263 */     displayContents(this.vReturnEntities1);
/* 5264 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 5265 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 5266 */       logMessage("A_154:" + this.eiNextItem.getKey());
/* 5267 */       this.eiSof = (EntityItem)this.eiNextItem.getUpLink(0);
/* 5268 */       this.strCondition1 = getSOFMktName(this.eiSof, false);
/* 5269 */       logMessage("A_154:" + this.eiSof.getKey());
/* 5270 */       this.eiComponent = (EntityItem)this.eiNextItem.getDownLink(0);
/* 5271 */       logMessage("A_154:" + this.eiComponent.getKey());
/* 5272 */       this.strCondition1 += ":" + this.eiComponent.getKey();
/*      */       
/* 5274 */       this.vReturnEntities2.add(this.strCondition1);
/*      */     } 
/*      */     
/* 5277 */     this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, false, "CMPNTFEATURE");
/* 5278 */     logMessage("A_154:From Feature");
/* 5279 */     displayContents(this.vReturnEntities1);
/* 5280 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 5281 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 5282 */       logMessage("A_154:" + this.eiNextItem.getKey());
/* 5283 */       this.eiComponent = (EntityItem)this.eiNextItem.getUpLink(0);
/* 5284 */       logMessage("A_154:" + this.eiComponent.getKey());
/* 5285 */       this.eiFeature = (EntityItem)this.eiNextItem.getDownLink(0);
/* 5286 */       logMessage("A_154:" + this.eiFeature.getKey());
/*      */       
/* 5288 */       this.eiSof = getUplinkedEntityItem(this.eiComponent, new String[] { "SOFCMPNT", "SOF" });
/* 5289 */       this.strCondition1 = getSOFMktName(this.eiSof, false);
/*      */       
/* 5291 */       logMessage("A_154:Mktname" + this.strCondition1);
/* 5292 */       this.strCondition1 += ":" + this.eiFeature.getKey();
/*      */       
/* 5294 */       this.vReturnEntities2.add(this.strCondition1);
/*      */     } 
/*      */     
/* 5297 */     resetPrintvars();
/*      */     
/* 5299 */     this.vReturnEntities3 = this.mySort.alphabetizeVector(this.vReturnEntities2);
/* 5300 */     if (this.vReturnEntities3.size() > 0) {
/* 5301 */       println(":xmp.");
/* 5302 */       println(".kp off");
/* 5303 */       println("");
/* 5304 */       this.strHeader = new String[] { "Service Product/Component Name", "Component ID", "Number" };
/*      */       
/* 5306 */       this.iColWidths = new int[] { 45, 13, 10 };
/*      */       
/* 5308 */       resetPrintvars();
/* 5309 */       this.strCondition1 = this.vReturnEntities3.elementAt(0);
/* 5310 */       if (this.strCondition1.indexOf(":CMPNT") > 0) {
/* 5311 */         this.strCondition2 = this.strCondition1.substring(0, this.strCondition1.indexOf(":CMPNT"));
/*      */       }
/* 5313 */       else if (this.strCondition1.indexOf(":FEATURE") > 0) {
/* 5314 */         this.strCondition2 = this.strCondition1.substring(0, this.strCondition1.indexOf(":FEATURE"));
/*      */       } 
/* 5316 */       println(this.strCondition2);
/*      */     } 
/*      */     
/* 5319 */     for (this.i = 0; this.i < this.vReturnEntities3.size(); this.i++) {
/* 5320 */       logMessage("A_154:Sorted Vector returning: " + this.strCondition1);
/* 5321 */       this.strCondition1 = this.vReturnEntities3.elementAt(this.i);
/* 5322 */       if (this.strCondition1.indexOf(":CMPNT") > 0) {
/* 5323 */         this.strCondition4 = this.strCondition1.substring(0, this.strCondition1.indexOf(":CMPNT"));
/*      */       }
/* 5325 */       else if (this.strCondition1.indexOf(":FEATURE") > 0) {
/* 5326 */         this.strCondition4 = this.strCondition1.substring(0, this.strCondition1.indexOf(":FEATURE"));
/*      */       } 
/* 5328 */       logMessage("A_154 Current Mkt:" + this.strCondition4);
/* 5329 */       logMessage("A_154 Prev Mkt :" + this.strCondition2);
/* 5330 */       if (!this.strCondition2.equals(this.strCondition4)) {
/* 5331 */         logMessage("A_154 Current < New checking");
/* 5332 */         this.strCondition2 = this.strCondition4;
/* 5333 */         println("                                                            Autobahn");
/* 5334 */         println("                                                            Project");
/*      */         
/* 5336 */         printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5337 */         println("");
/* 5338 */         println(this.strCondition2);
/* 5339 */         resetPrintvars();
/*      */       } 
/*      */       
/* 5342 */       if (this.strCondition1.indexOf(":CMPNT") > 0) {
/* 5343 */         this.strCondition5 = this.strCondition1.substring(this.strCondition1.indexOf(":CMPNT") + 1);
/* 5344 */         logMessage("A_154:Parsed out" + this.strCondition5);
/* 5345 */         this.eiComponent = this.egComponent.getEntityItem(this.strCondition5);
/* 5346 */         this.strCondition3 = getAttributeValue(this.eiComponent, "ITSCMPNTCATNAME", "");
/* 5347 */         this
/* 5348 */           .strCondition3 = ((this.strCondition3.length() > 0) ? (this.strCondition3 + " ") : "") + getAttributeValue(this.eiComponent, "MKTGNAME", " ");
/* 5349 */         logMessage(":CMPNT :" + this.strCondition3);
/*      */         
/* 5351 */         this.vPrintDetails.add(this.strCondition3);
/*      */         
/* 5353 */         this.strCondition3 = getAttributeValue(this.eiComponent, "COMPONENTID", "No Value" + this.eiComponent.getKey());
/* 5354 */         this.vPrintDetails.add(this.strCondition3);
/*      */ 
/*      */         
/* 5357 */         this.eiSof = getUplinkedEntityItem(this.eiComponent, this.strCmptToSof);
/*      */         
/* 5359 */         this.vReturnEntities1 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFAVAIL");
/* 5360 */         this.strCondition3 = " ";
/* 5361 */         if (this.vReturnEntities1.size() > 0) {
/*      */ 
/*      */ 
/*      */           
/* 5365 */           this.vReturnEntities4 = searchEntityItemLink(this.eiComponent, (String[])null, (String[])null, true, false, "OFDEVLPROJCMPNT");
/* 5366 */           if (this.vReturnEntities4.size() > 0) {
/* 5367 */             this.eiNextItem2 = this.vReturnEntities4.elementAt(0);
/* 5368 */             this.eiNextItem3 = (EntityItem)this.eiNextItem2.getUpLink(0);
/* 5369 */             this.strCondition3 = getAttributeValue(this.eiNextItem3, "PROJNUMBER", "No Value" + this.eiNextItem3.getKey());
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 5374 */         this.vPrintDetails.add(this.strCondition3);
/*      */       
/*      */       }
/* 5377 */       else if (this.strCondition1.indexOf(":FEATURE") > 0) {
/* 5378 */         this.strCondition5 = this.strCondition1.substring(this.strCondition1.indexOf(":FEATURE") + 1);
/* 5379 */         logMessage("A_154:Parsed out" + this.strCondition5);
/* 5380 */         this.eiFeature = this.egFeature.getEntityItem(this.strCondition5);
/*      */         
/* 5382 */         this.eiNextItem1 = getUplinkedEntityItem(this.eiFeature, this.strFeatureToCmpt);
/*      */ 
/*      */         
/* 5385 */         this.strCondition3 = getAttributeValue(this.eiNextItem1, "ITSCMPNTCATNAME", "");
/* 5386 */         this
/* 5387 */           .strCondition3 = ((this.strCondition3.length() > 0) ? (this.strCondition3 + " ") : "") + getAttributeValue(this.eiNextItem1, "MKTGNAME", " ");
/* 5388 */         logMessage(":Feature :" + this.strCondition3);
/* 5389 */         this.vPrintDetails.add(this.strCondition3);
/* 5390 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem1, "COMPONENTID", "No Value" + this.eiFeature.getKey()));
/*      */ 
/*      */         
/* 5393 */         this.eiComponent = getUplinkedEntityItem(this.eiFeature, this.strFeatureToCmpt);
/* 5394 */         this.strCondition3 = " ";
/* 5395 */         if (this.eiComponent == null) {
/* 5396 */           this.vReturnEntities1.removeAllElements();
/*      */         }
/*      */         else {
/*      */           
/* 5400 */           this.eiSof = getUplinkedEntityItem(this.eiComponent, this.strCmptToSof);
/*      */           
/* 5402 */           this.vReturnEntities1 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFAVAIL");
/*      */         } 
/* 5404 */         if (this.vReturnEntities1.size() > 0) {
/*      */ 
/*      */           
/* 5407 */           this.vReturnEntities4 = searchEntityItemLink(this.eiNextItem1, (String[])null, (String[])null, true, false, "OFDEVLPROJCMPNT");
/* 5408 */           if (this.vReturnEntities4.size() > 0) {
/* 5409 */             this.eiNextItem2 = this.vReturnEntities4.elementAt(0);
/* 5410 */             this.eiNextItem3 = (EntityItem)this.eiNextItem2.getUpLink(0);
/* 5411 */             this.strCondition3 = getAttributeValue(this.eiNextItem3, "PROJNUMBER", "No Value" + this.eiNextItem3.getKey());
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 5416 */         this.vPrintDetails.add(this.strCondition3);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5421 */     if (this.vPrintDetails.size() > 0) {
/* 5422 */       println("                                              Service       Autobahn");
/* 5423 */       println("                                              Product/      Project");
/* 5424 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5425 */       resetPrintvars();
/*      */     } 
/*      */     
/* 5428 */     if (this.vReturnEntities3.size() > 0) {
/* 5429 */       println(":exmp.");
/*      */     }
/* 5431 */     this.strSofToCmpt = new String[] { "SOFRELCMPNT", "CMPNT" };
/*      */ 
/*      */     
/* 5434 */     println(".*$A_154_End");
/*      */     
/* 5436 */     println(".*$A_155_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5446 */     this.strCondition2 = "";
/*      */     
/* 5448 */     this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, false, "CMPNTFEATURE");
/* 5449 */     logMessage("A_155:From Feature");
/* 5450 */     displayContents(this.vReturnEntities1);
/* 5451 */     for (this.i = 0; this.i < this.vReturnEntities1.size(); this.i++) {
/* 5452 */       this.eiNextItem = this.vReturnEntities1.elementAt(this.i);
/* 5453 */       logMessage("A_155:" + this.eiNextItem.getKey());
/* 5454 */       this.eiComponent = (EntityItem)this.eiNextItem.getUpLink(0);
/* 5455 */       logMessage("A_155:" + this.eiComponent.getKey());
/* 5456 */       this.eiFeature = (EntityItem)this.eiNextItem.getDownLink(0);
/* 5457 */       logMessage("A_155:" + this.eiFeature.getKey());
/*      */       
/* 5459 */       this.eiSof = getUplinkedEntityItem(this.eiComponent, new String[] { "SOFCMPNT", "SOF" });
/* 5460 */       this.strCondition1 = getSOFMktName(this.eiSof, false);
/*      */       
/* 5462 */       logMessage("A_155:Mktname" + this.strCondition1);
/* 5463 */       this.strCondition1 += ":" + this.eiFeature.getKey();
/*      */       
/* 5465 */       this.vReturnEntities2.add(this.strCondition1);
/*      */     } 
/*      */     
/* 5468 */     resetPrintvars();
/*      */     
/* 5470 */     this.vReturnEntities3 = this.mySort.alphabetizeVector(this.vReturnEntities2);
/* 5471 */     if (this.vReturnEntities3.size() > 0) {
/* 5472 */       println(":xmp.");
/* 5473 */       println(".kp off");
/* 5474 */       println("");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5487 */       this.strHeader = new String[] { "Feature Name", "Feature Id", "Number" };
/*      */       
/* 5489 */       this.iColWidths = new int[] { 45, 11, 10 };
/*      */ 
/*      */       
/* 5492 */       resetPrintvars();
/* 5493 */       this.strCondition1 = this.vReturnEntities3.elementAt(0);
/* 5494 */       if (this.strCondition1.indexOf(":CMPNT") > 0) {
/* 5495 */         this.strCondition2 = this.strCondition1.substring(0, this.strCondition1.indexOf(":CMPNT"));
/*      */       }
/* 5497 */       else if (this.strCondition1.indexOf(":FEATURE") > 0) {
/* 5498 */         this.strCondition2 = this.strCondition1.substring(0, this.strCondition1.indexOf(":FEATURE"));
/*      */       } 
/* 5500 */       println(this.strCondition2);
/*      */     } 
/*      */     
/* 5503 */     for (this.i = 0; this.i < this.vReturnEntities3.size(); this.i++) {
/* 5504 */       logMessage("A_155:Sorted Vector returning: " + this.strCondition1);
/* 5505 */       this.strCondition1 = this.vReturnEntities3.elementAt(this.i);
/* 5506 */       if (this.strCondition1.indexOf(":CMPNT") > 0) {
/* 5507 */         this.strCondition4 = this.strCondition1.substring(0, this.strCondition1.indexOf(":CMPNT"));
/*      */       }
/* 5509 */       else if (this.strCondition1.indexOf(":FEATURE") > 0) {
/* 5510 */         this.strCondition4 = this.strCondition1.substring(0, this.strCondition1.indexOf(":FEATURE"));
/*      */       } 
/* 5512 */       logMessage("A_155 Current Mkt:" + this.strCondition4);
/* 5513 */       logMessage("A_155 Prev Mkt :" + this.strCondition2);
/* 5514 */       if (!this.strCondition2.equals(this.strCondition4)) {
/* 5515 */         logMessage("A_155 Current < New");
/* 5516 */         this.strCondition2 = this.strCondition4;
/* 5517 */         println("                                              Service/    Autobahn");
/* 5518 */         println("Service Component/                            Component   Project");
/* 5519 */         printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5520 */         println("");
/* 5521 */         println(this.strCondition2);
/* 5522 */         resetPrintvars();
/*      */       } 
/*      */       
/* 5525 */       if (this.strCondition1.indexOf(":CMPNT") > 0) {
/* 5526 */         this.strCondition5 = this.strCondition1.substring(this.strCondition1.indexOf(":CMPNT") + 1);
/* 5527 */         logMessage("A_155:Parsed out" + this.strCondition5);
/* 5528 */         this.eiComponent = this.egComponent.getEntityItem(this.strCondition5);
/* 5529 */         logMessage(":CMPNT :" + this.strCondition3);
/* 5530 */         this.strCondition3 = getCmptToSofMktMsg(this.eiComponent);
/* 5531 */         this.vPrintDetails.add(this.strCondition3);
/* 5532 */         this.strCondition3 = getAttributeValue(this.eiComponent, "COMPONENTID", "No Value" + this.eiComponent.getKey());
/* 5533 */         this.vPrintDetails.add(this.strCondition3);
/*      */ 
/*      */         
/* 5536 */         this.eiSof = getUplinkedEntityItem(this.eiComponent, this.strCmptToSof);
/*      */         
/* 5538 */         this.vReturnEntities1 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFAVAIL");
/*      */         
/* 5540 */         this.strCondition3 = " ";
/* 5541 */         if (this.vReturnEntities1.size() > 0) {
/*      */ 
/*      */           
/* 5544 */           this.vReturnEntities4 = searchEntityItemLink(this.eiComponent, (String[])null, (String[])null, true, false, "OFDEVLPROJCMPNT");
/* 5545 */           if (this.vReturnEntities4.size() > 0) {
/* 5546 */             this.eiNextItem2 = this.vReturnEntities4.elementAt(0);
/* 5547 */             this.eiNextItem3 = (EntityItem)this.eiNextItem2.getUpLink(0);
/* 5548 */             this.strCondition3 = getAttributeValue(this.eiNextItem3, "PROJNUMBER", "No Value" + this.eiNextItem3.getKey());
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 5553 */         this.vPrintDetails.add(this.strCondition3);
/*      */       
/*      */       }
/* 5556 */       else if (this.strCondition1.indexOf(":FEATURE") > 0) {
/* 5557 */         this.strCondition5 = this.strCondition1.substring(this.strCondition1.indexOf(":FEATURE") + 1);
/* 5558 */         logMessage("A_155 in FEATURE:Parsed out" + this.strCondition5);
/* 5559 */         this.eiFeature = this.egFeature.getEntityItem(this.strCondition5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5566 */         this.strCondition3 = getAttributeValue(this.eiFeature, "MKTGNAME", " ");
/* 5567 */         logMessage(":Feature :" + this.strCondition3);
/* 5568 */         this.vPrintDetails.add(this.strCondition3);
/* 5569 */         this.vPrintDetails.add(getAttributeValue(this.eiFeature, "FEATURENUMBER", "No Value" + this.eiFeature.getKey()));
/*      */ 
/*      */         
/* 5572 */         this.eiComponent = getUplinkedEntityItem(this.eiFeature, this.strFeatureToCmpt);
/*      */         
/* 5574 */         this.strCondition3 = " ";
/* 5575 */         if (this.eiComponent == null) {
/* 5576 */           this.vReturnEntities1.removeAllElements();
/*      */         }
/*      */         else {
/*      */           
/* 5580 */           this.eiSof = getUplinkedEntityItem(this.eiComponent, this.strCmptToSof);
/*      */           
/* 5582 */           this.vReturnEntities1 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFAVAIL");
/*      */         } 
/* 5584 */         if (this.vReturnEntities1.size() > 0) {
/*      */ 
/*      */ 
/*      */           
/* 5588 */           this.vReturnEntities4 = searchEntityItemLink(this.eiComponent, (String[])null, (String[])null, true, false, "OFDEVLPROJCMPNT");
/* 5589 */           if (this.vReturnEntities4.size() > 0) {
/* 5590 */             this.eiNextItem2 = this.vReturnEntities4.elementAt(0);
/* 5591 */             this.eiNextItem3 = (EntityItem)this.eiNextItem2.getUpLink(0);
/* 5592 */             this.strCondition3 = getAttributeValue(this.eiNextItem3, "PROJNUMBER", "No Value" + this.eiNextItem3.getKey());
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 5597 */         this.vPrintDetails.add(this.strCondition3);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5602 */     if (this.vPrintDetails.size() > 0) {
/* 5603 */       println("                                              Service/    Autobahn");
/* 5604 */       println("Service Component/                            Component   Project");
/* 5605 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5606 */       resetPrintvars();
/*      */     } 
/*      */     
/* 5609 */     if (this.vReturnEntities3.size() > 0) {
/* 5610 */       println(":exmp.");
/*      */     }
/*      */     
/* 5613 */     println(".*$A_155_End");
/*      */     
/* 5615 */     println(".*$A_156_Begin");
/* 5616 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/*      */ 
/*      */     
/* 5619 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "120", "", true);
/* 5620 */     this.iColWidths = new int[] { 69 };
/*      */     
/* 5622 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 5623 */     resetPrintvars();
/* 5624 */     println(".*$A_156_End");
/*      */     
/* 5626 */     println(".*$A_157_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5631 */     this.bConditionOK = false;
/* 5632 */     this.vReturnEntities1 = searchEntityVectorLink(this.vSofFrmSofAvail, (String[])null, (String[])null, true, true, "SOFCATINCL");
/* 5633 */     this.strFilterAttr = new String[] { "CATALOGNAME" };
/*      */     
/* 5635 */     this.strFilterValue = new String[] { "321" };
/*      */     
/* 5637 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, true, true, "CATINCL");
/* 5638 */     if (this.vReturnEntities2.size() > 0) {
/* 5639 */       this.bConditionOK = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5645 */     if (!this.bConditionOK) {
/* 5646 */       this.vReturnEntities1 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTCATINCL");
/* 5647 */       this.strFilterAttr = new String[] { "CATALOGNAME" };
/*      */       
/* 5649 */       this.strFilterValue = new String[] { "321" };
/*      */       
/* 5651 */       this.vReturnEntities3 = searchEntityVectorLink(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, true, true, "CATINCL");
/* 5652 */       if (this.vReturnEntities3.size() > 0) {
/* 5653 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5660 */     if (!this.bConditionOK) {
/* 5661 */       this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATURECATINCL");
/* 5662 */       this.strFilterAttr = new String[] { "CATALOGNAME" };
/*      */       
/* 5664 */       this.strFilterValue = new String[] { "321" };
/*      */       
/* 5666 */       this.vReturnEntities4 = searchEntityVectorLink(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, true, true, "CATINCL");
/* 5667 */       if (this.vReturnEntities4.size() > 0) {
/* 5668 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 5671 */     if (this.bConditionOK) {
/* 5672 */       println("Yes");
/*      */     } else {
/*      */       
/* 5675 */       println("No");
/*      */     } 
/*      */     
/* 5678 */     println(".*$A_157_End");
/*      */     
/* 5680 */     println(".*$A_158_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5689 */     printQ158(this.bConditionOK);
/*      */     
/* 5691 */     println(".*$A_158_End");
/*      */     
/* 5693 */     println(".*$A_160_Begin");
/* 5694 */     println(transformXML(getAttributeValue(this.eiAnnounce, "ACCESPEOWDISABLE", " ")));
/* 5695 */     println(".*$A_160_End");
/*      */     
/* 5697 */     println(".*$A_161_Begin");
/* 5698 */     println(transformXML(getAttributeValue(this.eiAnnounce, "ACCESPEOWDISABLECONSID", " ")));
/* 5699 */     println(".*$A_161_End");
/*      */     
/* 5701 */     println(".*$A_162_Begin");
/* 5702 */     println(transformXML(getAttributeValue(this.eiAnnounce, "USSEC508", " ")));
/* 5703 */     println(".*$A_162_End");
/*      */     
/* 5705 */     println(".*$A_163_Begin");
/* 5706 */     println(transformXML(getAttributeValue(this.eiAnnounce, "USSEC508LOGO", " ")));
/* 5707 */     println(".*$A_163_End");
/*      */     
/* 5709 */     println(".*$A_170_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5714 */     this.vPrintDetails1 = new Vector();
/* 5715 */     this.vPrintDetails2 = new Vector();
/* 5716 */     this.vPrintDetails3 = new Vector();
/* 5717 */     logMessage("170 vSofFrmSofAvail");
/* 5718 */     displayContents(this.vSofFrmSofAvail);
/* 5719 */     for (this.i = 0; this.i < this.vSofFrmSofAvail.size(); this.i++) {
/* 5720 */       this.eiSof = this.vSofFrmSofAvail.elementAt(this.i);
/*      */       
/* 5722 */       this.vReturnEntities2 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFGBTA");
/* 5723 */       logMessage("A_170_1");
/* 5724 */       displayContents(this.vReturnEntities2);
/* 5725 */       this.eiNextItem = null;
/* 5726 */       this.eiNextItem1 = null;
/* 5727 */       if (this.vReturnEntities2.size() > 0) {
/* 5728 */         this.eiNextItem = this.vReturnEntities2.elementAt(0);
/* 5729 */         this.eiNextItem1 = (this.eiNextItem.getDownLinkCount() > 0) ? (EntityItem)this.eiNextItem.getDownLink(0) : null;
/*      */       } 
/* 5731 */       if (this.eiNextItem1 != null) {
/* 5732 */         this.vPrintDetails.add(getAttributeValue(this.eiSof, "OFIDNUMBER", " "));
/* 5733 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem1, "GBNAME", " "));
/* 5734 */         this.vPrintDetails.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "SAPPRIMBRANDCODE", " ") : " ");
/*      */         
/* 5736 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "SAPPRODFAMCODE", " ") : " ");
/* 5737 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "OMBRANDCODE", " ") : " ");
/* 5738 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "OMPRODFAMCODE", " ") : " ");
/* 5739 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "BPDBBRANDCODE", " ") : " ");
/*      */         
/* 5741 */         this.vPrintDetails2.add(getAttributeValue(this.eiSof, "OFIDNUMBER", " "));
/* 5742 */         this.vPrintDetails2.add(getAttributeValue(this.eiSof, "MATACCGRP", " "));
/* 5743 */         this.vPrintDetails2.add(getAttributeValue(this.eiSof, "ASSORTMODULE", " "));
/*      */         
/* 5745 */         this.vReturnEntities3 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFOFDEVLPROJA");
/* 5746 */         if (this.vReturnEntities3.size() > 0) {
/* 5747 */           this.eiNextItem = this.vReturnEntities3.elementAt(0);
/* 5748 */           this.eiNextItem1 = (this.eiNextItem.getDownLinkCount() > 0) ? (EntityItem)this.eiNextItem.getDownLink(0) : null;
/* 5749 */           this.vPrintDetails2.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "DEVDIV", " ") : " ");
/*      */         } else {
/*      */           
/* 5752 */           this.vPrintDetails2.add(" ");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 5758 */       this.vReturnEntities3 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFPRICE");
/* 5759 */       if (this.vReturnEntities3.size() > 0) {
/* 5760 */         this.eiNextItem = this.vReturnEntities3.elementAt(0);
/* 5761 */         this.eiPriceInfo = (this.eiNextItem.getDownLinkCount() > 0) ? (EntityItem)this.eiNextItem.getDownLink(0) : null;
/* 5762 */         this.vPrintDetails3.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "AMORTIZATIONSTART", " ") : " ");
/* 5763 */         this.vPrintDetails3.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "AMORTIZATIONLENGTH", " ") : " ");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5768 */     logMessage("170 vCmpntFrmCmpntAvail");
/* 5769 */     displayContents(this.vCmpntFrmCmpntAvail);
/* 5770 */     for (this.i = 0; this.i < this.vCmpntFrmCmpntAvail.size(); this.i++) {
/* 5771 */       this.eiComponent = this.vCmpntFrmCmpntAvail.elementAt(this.i);
/*      */ 
/*      */       
/* 5774 */       this.eiNextItem = null;
/* 5775 */       this.eiNextItem1 = null;
/* 5776 */       this.eiSof = getUplinkedEntityItem(this.eiComponent, this.strCmptToSof);
/* 5777 */       if (this.eiSof != null) {
/* 5778 */         this.vReturnEntities2 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFGBTA");
/* 5779 */         logMessage("A_170_2");
/* 5780 */         displayContents(this.vReturnEntities2);
/* 5781 */         if (this.vReturnEntities2.size() > 0) {
/* 5782 */           this.eiNextItem = this.vReturnEntities2.elementAt(0);
/* 5783 */           this.eiNextItem1 = (this.eiNextItem.getDownLinkCount() > 0) ? (EntityItem)this.eiNextItem.getDownLink(0) : null;
/*      */         } 
/*      */       } 
/* 5786 */       if (this.eiNextItem1 != null) {
/* 5787 */         this.vPrintDetails.add(getAttributeValue(this.eiSof, "OFIDNUMBER", " "));
/* 5788 */         this.vPrintDetails.add(getAttributeValue(this.eiNextItem1, "GBNAME", " "));
/* 5789 */         this.vPrintDetails.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "SAPPRIMBRANDCODE", " ") : " ");
/*      */         
/* 5791 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "SAPPRODFAMCODE", " ") : " ");
/* 5792 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "OMBRANDCODE", " ") : " ");
/* 5793 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "OMPRODFAMCODE", " ") : " ");
/* 5794 */         this.vPrintDetails1.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "BPDBBRANDCODE", " ") : " ");
/*      */         
/* 5796 */         this.vPrintDetails2.add(getAttributeValue(this.eiSof, "OFIDNUMBER", " "));
/* 5797 */         this.vPrintDetails2.add(getAttributeValue(this.eiSof, "MATACCGRP", " "));
/* 5798 */         this.vPrintDetails2.add(getAttributeValue(this.eiSof, "ASSORTMODULE", " "));
/*      */         
/* 5800 */         this.vReturnEntities3 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFOFDEVLPROJA");
/* 5801 */         if (this.vReturnEntities3.size() > 0) {
/* 5802 */           this.eiNextItem = this.vReturnEntities3.elementAt(0);
/* 5803 */           this.eiNextItem1 = (this.eiNextItem.getDownLinkCount() > 0) ? (EntityItem)this.eiNextItem.getDownLink(0) : null;
/* 5804 */           this.vPrintDetails2.add((this.eiNextItem1 != null) ? getAttributeValue(this.eiNextItem1, "DEVDIV", " ") : " ");
/*      */         } else {
/*      */           
/* 5807 */           this.vPrintDetails2.add(" ");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 5813 */       this.vReturnEntities3 = searchEntityItemLink(this.eiComponent, (String[])null, (String[])null, true, true, "CMPNTPRICE");
/* 5814 */       if (this.vReturnEntities3.size() > 0) {
/* 5815 */         this.eiNextItem = this.vReturnEntities3.elementAt(0);
/* 5816 */         this.eiPriceInfo = (this.eiNextItem.getDownLinkCount() > 0) ? (EntityItem)this.eiNextItem.getDownLink(0) : null;
/* 5817 */         this.vPrintDetails3.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "AMORTIZATIONSTART", " ") : " ");
/* 5818 */         this.vPrintDetails3.add((this.eiPriceInfo != null) ? getAttributeValue(this.eiPriceInfo, "AMORTIZATIONLENGTH", " ") : " ");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5825 */     if (this.vPrintDetails.size() > 0) {
/* 5826 */       println(":xmp.");
/* 5827 */       println(".kp off");
/* 5828 */       println("Offering Id          Description                        Primary Brand");
/* 5829 */       this.strHeader = new String[] { "", "", "   Code" };
/*      */       
/* 5831 */       this.iColWidths = new int[] { 19, 34, 14 };
/*      */       
/* 5833 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 5834 */       resetPrintvars();
/* 5835 */       println("");
/* 5836 */       println("");
/* 5837 */       println("Product Family OM Brand OM Product Family BPDP Brand");
/* 5838 */       this.strHeader = new String[] { "   Code", "Code", "     Code", "   Code" };
/*      */       
/* 5840 */       this.iColWidths = new int[] { 14, 8, 17, 10 };
/*      */       
/* 5842 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails1);
/* 5843 */       this.vPrintDetails1 = new Vector();
/* 5844 */       println("");
/* 5845 */       println("");
/* 5846 */       println("                    Material Account   Assortment Development");
/* 5847 */       this.strHeader = new String[] { "Offering ID", "Assignment Group", "  Module", " Division" };
/*      */       
/* 5849 */       this.iColWidths = new int[] { 19, 17, 11, 11 };
/*      */       
/* 5851 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails2);
/* 5852 */       this.vPrintDetails2 = new Vector();
/*      */       
/* 5854 */       if (this.vPrintDetails3.size() > 0) {
/* 5855 */         println("");
/* 5856 */         println("");
/* 5857 */         this.strHeader = new String[] { "Amortization Start", "Amortization Length" };
/*      */         
/* 5859 */         this.iColWidths = new int[] { 18, 20 };
/*      */         
/* 5861 */         printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails3);
/* 5862 */         this.vPrintDetails3 = new Vector();
/*      */       } 
/* 5864 */       println(":exmp.");
/*      */     } 
/*      */     
/* 5867 */     println(".*$A_170_End");
/*      */     
/* 5869 */     println(".*$A_181_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5876 */     this.bConditionOK = false;
/* 5877 */     this.vReturnEntities1 = searchEntityGroupLink(this.egComponent, (String[])null, (String[])null, true, true, "CMPNTFINOF");
/* 5878 */     if (this.vReturnEntities1.size() > 0) {
/* 5879 */       this.bConditionOK = true;
/*      */     } else {
/*      */       
/* 5882 */       this.vReturnEntities1 = searchEntityGroupLink(this.egFeature, (String[])null, (String[])null, true, true, "FEATUREFINOF");
/* 5883 */       if (this.vReturnEntities1.size() > 0) {
/* 5884 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 5887 */     if (this.bConditionOK) {
/* 5888 */       println("Yes");
/*      */     } else {
/*      */       
/* 5891 */       println("No");
/*      */     } 
/*      */     
/* 5894 */     println(".*$A_181_End");
/*      */     
/* 5896 */     println(".*$A_182_Begin");
/* 5897 */     printIntSuppMktFinInfo("MKTGMSGINTERNAL");
/* 5898 */     println(".*$A_182_End");
/*      */     
/* 5900 */     println(".*$A_183_Begin");
/* 5901 */     printIntSuppMktFinInfo("MKTGMESEXTERNAL");
/* 5902 */     println(".*$A_183_End");
/*      */     
/* 5904 */     println(".*$A_184_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5911 */     resetPrintvars();
/* 5912 */     this.bConditionOK = false;
/* 5913 */     this.strParamList1 = new String[] { "PROMOELIGIBILITYTCS" };
/*      */     
/* 5915 */     this.vReturnEntities1 = searchEntityGroupLink(this.egComponent, (String[])null, (String[])null, true, true, "CMPNTFINOF");
/* 5916 */     if (this.vReturnEntities1.size() > 0) {
/* 5917 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "FINOF");
/* 5918 */       printValueListInVector(this.vReturnEntities3, this.strParamList1, " ", true, false);
/* 5919 */       if (this.vPrintDetails.size() > 0) {
/* 5920 */         this.bConditionOK = true;
/*      */       }
/*      */     } else {
/*      */       
/* 5924 */       this.vReturnEntities1 = searchEntityGroupLink(this.egFeature, (String[])null, (String[])null, true, true, "FEATUREFINOF");
/* 5925 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "FINOF");
/* 5926 */       printValueListInVector(this.vReturnEntities3, this.strParamList1, " ", true, false);
/* 5927 */       if (this.vPrintDetails.size() > 0) {
/* 5928 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 5931 */     if (this.bConditionOK) {
/* 5932 */       println("Yes");
/*      */     } else {
/*      */       
/* 5935 */       println("No");
/*      */     } 
/* 5937 */     resetPrintvars();
/* 5938 */     println(".*$A_184_End");
/*      */     
/* 5940 */     println(".*$A_185_Begin");
/*      */ 
/*      */     
/* 5943 */     printIntSuppMktFinInfo("PROMOELIGIBILITYTCS");
/*      */     
/* 5945 */     println(".*$A_185_End");
/*      */     
/* 5947 */     println(".*$A_186_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5953 */     resetPrintvars();
/* 5954 */     this.bConditionOK = false;
/* 5955 */     this.strParamList1 = new String[] { "MONTHLYPAYMENT" };
/*      */     
/* 5957 */     this.vReturnEntities1 = searchEntityGroupLink(this.egComponent, (String[])null, (String[])null, true, true, "CMPNTFINOF");
/* 5958 */     if (this.vReturnEntities1.size() > 0) {
/* 5959 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "FINOF");
/* 5960 */       printValueListInVector(this.vReturnEntities3, this.strParamList1, " ", true, false);
/* 5961 */       if (this.vPrintDetails.size() > 0) {
/* 5962 */         this.bConditionOK = true;
/*      */       }
/*      */     } else {
/*      */       
/* 5966 */       this.vReturnEntities1 = searchEntityGroupLink(this.egFeature, (String[])null, (String[])null, true, true, "FEATUREFINOF");
/* 5967 */       this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "FINOF");
/* 5968 */       printValueListInVector(this.vReturnEntities3, this.strParamList1, " ", true, false);
/* 5969 */       if (this.vPrintDetails.size() > 0) {
/* 5970 */         this.bConditionOK = true;
/*      */       }
/*      */     } 
/* 5973 */     if (this.bConditionOK) {
/* 5974 */       println("Yes");
/*      */     } else {
/*      */       
/* 5977 */       println("No");
/*      */     } 
/* 5979 */     resetPrintvars();
/*      */     
/* 5981 */     println(".*$A_186_End");
/*      */     
/* 5983 */     println(".*$A_187_Begin");
/* 5984 */     printIntSuppMktFinInfo("MONTHLYPAYMENT", false);
/*      */     
/* 5986 */     println(".*$A_187_End");
/*      */     
/* 5988 */     println(".*$A_188_Begin");
/*      */ 
/*      */     
/* 5991 */     printIntSuppMktFinInfo("PAYMENTTERM", false);
/*      */     
/* 5993 */     println(".*$A_188_End");
/*      */     
/* 5995 */     println(".*$A_189_Begin");
/* 5996 */     printIntSuppMktFinInfo("ELIGIBILITYTCS");
/* 5997 */     println(".*$A_189_End");
/*      */     
/* 5999 */     println(".*$A_195_Begin");
/* 6000 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/*      */     
/* 6002 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "200", "", true);
/* 6003 */     this.iColWidths = new int[] { 69 };
/*      */     
/* 6005 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6006 */     resetPrintvars();
/* 6007 */     println(".*$A_195_End");
/*      */ 
/*      */     
/* 6010 */     println(".*$A_200_Begin");
/* 6011 */     prettyPrint(transformXML(getAttributeValue(this.eiAnnounce, "ATAGLANCE", " ")), 69);
/* 6012 */     println(".*$A_200_End");
/*      */ 
/*      */     
/* 6015 */     println(".*$A_202_Begin");
/* 6016 */     prettyPrint(transformXML(getAttributeValue(this.eiAnnounce, "OVERVIEWABSTRACT", " ")), 69);
/* 6017 */     printVanillaSVSReport("OVERVIEWABSTRACT", true, false);
/* 6018 */     println(".*$A_202_End");
/*      */     
/* 6020 */     println(".*$A_204_Begin");
/* 6021 */     printVanillaSVSReport("PREREQCOREQ", true, false);
/* 6022 */     println(".*$A_204_End");
/*      */     
/* 6024 */     println(".*$A_208_Begin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6031 */     this.strFilterAttr = new String[] { "AVAILTYPE" };
/*      */     
/* 6033 */     this.strFilterValue = new String[] { "146" };
/*      */     
/* 6035 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, this.strFilterAttr, this.strFilterValue, true, true, "AVAIL");
/* 6036 */     logMessage("****AVAIL*****");
/* 6037 */     displayContents(this.vReturnEntities1);
/* 6038 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "SOFAVAIL");
/* 6039 */     displayContents(this.vReturnEntities1);
/* 6040 */     this.strCondition1 = "";
/* 6041 */     this.strCondition2 = "";
/* 6042 */     this.strCondition4 = "";
/* 6043 */     this.vReturnEntities4 = new Vector();
/* 6044 */     this.iTemp = this.vSofFrmSofAvail.size() + this.vCmpntFrmCmpntAvail.size() + this.vFeatureFrmFeatureAvail.size();
/* 6045 */     logMessage("_208:Array Size=" + this.iTemp);
/*      */ 
/*      */     
/* 6048 */     this.i = 0;
/* 6049 */     logMessage("_208:SOFAVAIL");
/* 6050 */     displayContents(this.vReturnEntities2);
/* 6051 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 6052 */       this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 6053 */       this.eiSof = (EntityItem)this.eiNextItem.getUpLink(0);
/* 6054 */       this.eiAvail = (EntityItem)this.eiNextItem.getDownLink(0);
/* 6055 */       this.strCondition1 = getGeoTags(this.eiAvail);
/* 6056 */       this.strCondition4 = getAttributeValue(this.eiAvail, "EFFECTIVEDATE", " ");
/* 6057 */       this.strCondition3 = getSOFMktName(this.eiSof);
/*      */       
/* 6059 */       this.vReturnEntities4.add(this.strCondition4 + "|" + this.strCondition3 + "|" + this.strCondition1);
/*      */     } 
/* 6061 */     this.j = (this.i > 0) ? (this.i - 1) : this.i;
/* 6062 */     logMessage("_208:After SOFAVAIL Ctr=" + this.j);
/*      */     
/* 6064 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "CMPNTAVAIL");
/* 6065 */     this.strCondition1 = "";
/* 6066 */     this.strCondition2 = "";
/* 6067 */     this.strCondition4 = "";
/* 6068 */     logMessage("_208:CMPNTAVAIL");
/* 6069 */     displayContents(this.vReturnEntities2);
/* 6070 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 6071 */       this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 6072 */       this.eiComponent = (EntityItem)this.eiNextItem.getUpLink(0);
/* 6073 */       this.eiAvail = (EntityItem)this.eiNextItem.getDownLink(0);
/* 6074 */       this.strCondition1 = getGeoTags(this.eiAvail);
/* 6075 */       this.strCondition4 = getAttributeValue(this.eiAvail, "EFFECTIVEDATE", " ");
/* 6076 */       this.strCondition3 = getCmptToSofMktMsg(this.eiComponent);
/*      */       
/* 6078 */       this.vReturnEntities4.add(this.strCondition4 + "|" + this.strCondition3 + "|" + this.strCondition1);
/*      */     } 
/* 6080 */     this.j = (this.i + this.j > 0) ? (this.i + this.j - 1) : 0;
/* 6081 */     logMessage("_208:After CMPNTAVAIL Ctr=" + this.j);
/* 6082 */     this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, false, "FEATUREAVAIL");
/* 6083 */     this.strCondition1 = "";
/* 6084 */     this.strCondition2 = "";
/* 6085 */     this.strCondition4 = "";
/* 6086 */     logMessage("_208:FEATUREAVAIL");
/* 6087 */     displayContents(this.vReturnEntities2);
/* 6088 */     for (this.i = 0; this.i < this.vReturnEntities2.size(); this.i++) {
/* 6089 */       this.eiNextItem = this.vReturnEntities2.elementAt(this.i);
/* 6090 */       logMessage("_208:FEATUREAVAIL" + this.eiNextItem.getKey());
/* 6091 */       this.eiFeature = (EntityItem)this.eiNextItem.getUpLink(0);
/* 6092 */       logMessage("_208:FEATURE" + this.eiAvail.getKey());
/* 6093 */       this.eiAvail = (EntityItem)this.eiNextItem.getDownLink(0);
/* 6094 */       this.strCondition1 = getGeoTags(this.eiAvail);
/* 6095 */       this.strCondition4 = getAttributeValue(this.eiAvail, "EFFECTIVEDATE", " ");
/* 6096 */       this.strCondition3 = getfeatureToSofMktMsg(this.eiFeature);
/* 6097 */       logMessage("_208: FEATUREAVAIL Setting =" + (this.i + this.j));
/*      */       
/* 6099 */       this.vReturnEntities4.add(this.strCondition4 + "|" + this.strCondition3 + "|" + this.strCondition1);
/*      */     } 
/* 6101 */     this.strAnswers = this.vReturnEntities4.toArray();
/*      */     
/* 6103 */     Arrays.sort(this.strAnswers);
/* 6104 */     this.strCondition5 = "";
/* 6105 */     resetPrintvars();
/*      */     
/* 6107 */     for (this.i = 0; this.i < this.strAnswers.length; this.i++) {
/* 6108 */       this.strCondition1 = (String)this.strAnswers[this.i];
/* 6109 */       logMessage("String stored at " + this.i + ":" + this.strCondition1);
/* 6110 */       this.iTemp = this.strCondition1.indexOf("|");
/* 6111 */       this.strCondition2 = this.strCondition1.substring(0, this.iTemp);
/* 6112 */       this.strCondition1 = this.strCondition1.substring(this.iTemp + 1);
/* 6113 */       logMessage("Found | at   " + this.iTemp);
/* 6114 */       logMessage("Parsed Date  " + this.strCondition2);
/* 6115 */       this.iTemp = this.strCondition1.indexOf("|");
/* 6116 */       this.strCondition3 = this.strCondition1.substring(0, this.iTemp);
/* 6117 */       this.strCondition1 = this.strCondition1.substring(this.iTemp + 1);
/* 6118 */       logMessage("Found | at   " + this.iTemp);
/* 6119 */       logMessage("Parsed  MktName " + this.strCondition3);
/*      */       
/* 6121 */       this.strCondition4 = this.strCondition1;
/* 6122 */       logMessage("Parsed GEO  " + this.strCondition4);
/*      */     } 
/*      */ 
/*      */     
/* 6126 */     this.strCondition6 = "";
/* 6127 */     if (this.strAnswers.length > 0) {
/* 6128 */       println(":xmp.");
/* 6129 */       println(".kp off");
/*      */     } 
/*      */     
/* 6132 */     int i = 0;
/* 6133 */     for (this.i = 0; this.i < this.strAnswers.length; this.i++) {
/* 6134 */       this.strCondition1 = (String)this.strAnswers[this.i];
/* 6135 */       logMessage("String stored at " + this.i + ":" + this.strCondition1);
/* 6136 */       i = this.strCondition1.indexOf("|");
/* 6137 */       this.strCondition2 = this.strCondition1.substring(0, i);
/* 6138 */       this.strCondition1 = this.strCondition1.substring(i + 1);
/* 6139 */       logMessage("Found | at   " + i);
/* 6140 */       logMessage("Parsed Date  " + this.strCondition2);
/* 6141 */       i = this.strCondition1.indexOf("|");
/* 6142 */       this.strCondition3 = this.strCondition1.substring(0, i);
/* 6143 */       this.strCondition1 = this.strCondition1.substring(i + 1);
/* 6144 */       logMessage("Found | at   " + i);
/* 6145 */       logMessage("Parsed  MktName " + this.strCondition3);
/*      */       
/* 6147 */       this.strCondition4 = this.strCondition1;
/* 6148 */       logMessage("Parsed GEO  " + this.strCondition4);
/*      */       
/* 6150 */       if (!this.strCondition4.equals(this.strCondition5)) {
/* 6151 */         if (!this.strCondition5.equals("US, AP, CAN, EMEA, LA") && 
/* 6152 */           this.strCondition5.trim().length() > 0) {
/* 6153 */           logMessage("Ending GEO Break  " + this.strCondition5);
/* 6154 */           println(".br;:hp2.<---" + this.strCondition5 + ":ehp2.");
/* 6155 */           println("");
/*      */         } 
/*      */ 
/*      */         
/* 6159 */         if (!this.strCondition4.equals("US, AP, CAN, EMEA, LA") && 
/* 6160 */           this.strCondition4.trim().length() > 0) {
/* 6161 */           logMessage("Starting GEO Break  " + this.strCondition4);
/* 6162 */           println(":p.:hp2." + this.strCondition4 + "--->:ehp2.");
/* 6163 */           i = 0;
/* 6164 */           this.strCondition6 = this.strCondition2;
/* 6165 */           println(getRFADateFormat(this.strCondition2) + "");
/*      */         } 
/*      */         
/* 6168 */         this.strCondition5 = this.strCondition4;
/*      */       } 
/*      */       
/* 6171 */       if (!this.strCondition6.equals(this.strCondition2)) {
/* 6172 */         this.strCondition6 = this.strCondition2;
/* 6173 */         if (i == 1) {
/* 6174 */           println(".p");
/*      */         }
/* 6176 */         i = 0;
/* 6177 */         println(getRFADateFormat(this.strCondition2) + "");
/*      */       } 
/*      */       
/* 6180 */       prettyPrint(this.strCondition3, 69);
/* 6181 */       i++;
/*      */     } 
/*      */     
/* 6184 */     if (!this.strCondition5.equals("US, AP, CAN, EMEA, LA") && 
/* 6185 */       this.strCondition5.trim().length() > 0) {
/* 6186 */       logMessage("Final Ending GEO Break  " + this.strCondition5);
/* 6187 */       println(".br;:hp2.<---" + this.strCondition5 + ":ehp2.");
/* 6188 */       println("");
/*      */     } 
/*      */     
/* 6191 */     if (this.strAnswers.length > 0) {
/* 6192 */       println(":exmp.");
/* 6193 */       resetPrintvars();
/*      */     } 
/*      */     
/* 6196 */     println(".*$A_208_End");
/*      */     
/* 6198 */     println(".*$A_210_Begin");
/*      */     
/* 6200 */     prettyPrint(transformXML(getAttributeValue(this.eiAnnounce, "DESCRIPTION", " ")), 69);
/* 6201 */     printVanillaSVSReport("DESCRIPTION", true, false);
/* 6202 */     println(".*$A_210_End");
/*      */     
/* 6204 */     println(".*$A_212_Begin");
/*      */     
/* 6206 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/*      */     
/* 6208 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "130", "", true);
/* 6209 */     this.iColWidths = new int[] { 69 };
/*      */     
/* 6211 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6212 */     resetPrintvars();
/* 6213 */     println(".*$A_212_End");
/*      */     
/* 6215 */     println(".*$A_214_Begin");
/* 6216 */     printVanillaSVSReport("CHARGES", true, true);
/*      */     
/* 6218 */     println(".*$A_214_End");
/*      */     
/* 6220 */     println(".*$A_218_Begin");
/* 6221 */     printVanillaSVSReport("BILLINGPERIOD", false, true);
/*      */     
/* 6223 */     println(".*$A_218_End");
/*      */     
/* 6225 */     println(".*$A_219_Begin");
/* 6226 */     GeneralAreaGroup generalAreaGroup = this.m_geList.getRfaGeoEMEAInclusion(this.eiAnnounce);
/* 6227 */     logMessage("A_219_Begin returned GEItemcount" + generalAreaGroup.getGeneralAreaItemCount());
/* 6228 */     this.vReturnEntities1 = searchEntityGroupLink(this.grpAnnAvail, (String[])null, (String[])null, true, true, "AVAIL");
/* 6229 */     displayContents(this.vReturnEntities1);
/*      */ 
/*      */     
/* 6232 */     this.vReturnEntities2 = new Vector();
/* 6233 */     this.bConditionOK = false;
/*      */     
/* 6235 */     if (this.vReturnEntities1.size() > 0) {
/* 6236 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 6237 */       for (byte b = 0; b < this.vReturnEntities1.size(); b++) {
/* 6238 */         this.eiAvail = this.vReturnEntities1.elementAt(b);
/*      */         
/* 6240 */         this.strCondition2 = getRFADateFormat(getAttributeValue(this.eiAvail, "EFFECTIVEDATE", ""));
/* 6241 */         if (b == 0) {
/* 6242 */           this.strCondition6 = this.strCondition2;
/* 6243 */         } else if (!this.strCondition2.equals(this.strCondition6)) {
/* 6244 */           this.bConditionOK = true;
/*      */         } 
/* 6246 */         this.strCondition3 = getAttributeLongFlagDesc(this.eiAvail, "COUNTRYLIST");
/* 6247 */         if (this.strCondition3 == null) {
/*      */           break;
/*      */         }
/* 6250 */         StringTokenizer stringTokenizer = new StringTokenizer(this.strCondition3, "|");
/*      */         
/* 6252 */         for (byte b1 = 0; stringTokenizer.hasMoreTokens(); b1++) {
/*      */           
/* 6254 */           this.strCondition1 = stringTokenizer.nextToken().trim();
/* 6255 */           logMessage("_219:Got Country:" + b1 + ":" + this.strCondition1);
/* 6256 */           if (this.strCondition1.compareToIgnoreCase("Austria") == 0 || this.strCondition1.compareToIgnoreCase("Belgium") == 0 || this.strCondition1
/* 6257 */             .compareToIgnoreCase("Bulgaria") == 0 || this.strCondition1.compareToIgnoreCase("Croatia") == 0 || this.strCondition1
/* 6258 */             .compareToIgnoreCase("Czech Republic") == 0 || this.strCondition1
/* 6259 */             .compareToIgnoreCase("Denmark") == 0 || this.strCondition1.compareToIgnoreCase("Finland") == 0 || this.strCondition1
/* 6260 */             .compareToIgnoreCase("France") == 0 || this.strCondition1.compareToIgnoreCase("Germany") == 0 || this.strCondition1
/* 6261 */             .compareToIgnoreCase("Greece") == 0 || this.strCondition1.compareToIgnoreCase("Hungary") == 0 || this.strCondition1
/* 6262 */             .compareToIgnoreCase("Ireland") == 0 || this.strCondition1.compareToIgnoreCase("Israel") == 0 || this.strCondition1
/* 6263 */             .compareToIgnoreCase("Italy") == 0 || this.strCondition1.compareToIgnoreCase("Luxembourg") == 0 || this.strCondition1
/* 6264 */             .compareToIgnoreCase("Netherlands") == 0 || this.strCondition1.compareToIgnoreCase("Norway") == 0 || this.strCondition1
/* 6265 */             .compareToIgnoreCase("Poland") == 0 || this.strCondition1.compareToIgnoreCase("Portugal") == 0 || this.strCondition1
/* 6266 */             .compareToIgnoreCase("Romania") == 0 || this.strCondition1.compareToIgnoreCase("Russian Federation") == 0 || this.strCondition1
/* 6267 */             .compareToIgnoreCase("Slovakia") == 0 || this.strCondition1.compareToIgnoreCase("Slovenia") == 0 || this.strCondition1
/* 6268 */             .compareToIgnoreCase("South Africa") == 0 || this.strCondition1.compareToIgnoreCase("Spain") == 0 || this.strCondition1
/* 6269 */             .compareToIgnoreCase("Switzerland") == 0 || this.strCondition1.compareToIgnoreCase("Sweden") == 0 || this.strCondition1
/* 6270 */             .compareToIgnoreCase("Turkey") == 0 || this.strCondition1
/* 6271 */             .compareToIgnoreCase("United Kingdom") == 0) {
/* 6272 */             if (this.strCondition1.compareToIgnoreCase("United Kingdom") == 0) {
/* 6273 */               this.vReturnEntities2.add("United Kingdom**");
/* 6274 */               hashtable.put("United Kingdom**", this.strCondition2);
/*      */             }
/* 6276 */             else if (this.strCondition1.compareToIgnoreCase("France") == 0) {
/* 6277 */               this.vReturnEntities2.add("France*");
/* 6278 */               hashtable.put("France*", this.strCondition2);
/*      */             } else {
/*      */               
/* 6281 */               this.vReturnEntities2.add(this.strCondition1);
/* 6282 */               hashtable.put(this.strCondition1, this.strCondition2);
/*      */             } 
/*      */ 
/*      */             
/* 6286 */             logMessage("_219:MATCHED Country" + this.strCondition1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 6291 */       this.vReturnEntities3 = this.mySort.alphabetizeVector(this.vReturnEntities2);
/* 6292 */       this.strCondition1 = "";
/* 6293 */       for (this.i = 0; this.i < this.vReturnEntities3.size(); this.i++) {
/* 6294 */         this.vPrintDetails.add(this.vReturnEntities3.elementAt(this.i));
/* 6295 */         this.strCondition2 = (String)hashtable.get(this.vReturnEntities3.elementAt(this.i));
/* 6296 */         if (!this.bConditionOK) {
/* 6297 */           this.vPrintDetails.add("");
/*      */         } else {
/*      */           
/* 6300 */           this.vPrintDetails.add(this.strCondition2);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 6305 */       this.strHeader = new String[] { "Country", "Availability Date" };
/*      */       
/* 6307 */       this.iColWidths = new int[] { 35, 17 };
/*      */       
/* 6309 */       if (this.vPrintDetails.size() > 0) {
/* 6310 */         println(":xmp.");
/* 6311 */         println(".kp off");
/* 6312 */         printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 6313 */         resetPrintvars();
/* 6314 */         println("* Except overseas territories");
/* 6315 */         println("** UK Mainland Only");
/* 6316 */         println(":exmp.");
/*      */       } 
/*      */     } 
/*      */     
/* 6320 */     println(".*$A_219_End");
/*      */     
/* 6322 */     println(".*$A_220_Begin");
/* 6323 */     println("AP DISTRIBUTION:  TO ALL ASIA PACIFIC COUNTRIES FOR RELEASE.");
/* 6324 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/* 6325 */     treeMap.put("ASEAN *", "No");
/* 6326 */     treeMap.put("India/South Asia**", "No");
/* 6327 */     treeMap.put("AUSTRALIA", "No");
/* 6328 */     treeMap.put("People's Republic of China", "No");
/* 6329 */     treeMap.put("HONG KONG S.A.R of the PRC", "No");
/* 6330 */     treeMap.put("Macao S.A.R of the PRC", "No");
/* 6331 */     treeMap.put("TAIWAN", "No");
/* 6332 */     treeMap.put("KOREA", "No");
/* 6333 */     treeMap.put("JAPAN", "No");
/* 6334 */     treeMap.put("NEW ZEALAND", "No");
/*      */     
/* 6336 */     if (this.m_geList.isRfaGeoAP(this.eiAnnounce)) {
/* 6337 */       GeneralAreaGroup generalAreaGroup1 = this.m_geList.getRfaGeoAPInclusion(this.eiAnnounce);
/* 6338 */       for (byte b = 0; b < generalAreaGroup1.getGeneralAreaItemCount(); b++) {
/* 6339 */         GeneralAreaItem generalAreaItem = generalAreaGroup1.getGeneralAreaItem(b);
/* 6340 */         this.strCondition1 = generalAreaItem.getName().toUpperCase();
/* 6341 */         logMessage("_220:Found country" + this.strCondition1);
/* 6342 */         if (this.strCondition1.equals("DARUSSALAM") || this.strCondition1.equals("BRUNEI") || this.strCondition1.equals("MYANMAR") || this.strCondition1
/* 6343 */           .equals("MALAYSIA") || this.strCondition1.equals("PHILIPPINES") || this.strCondition1.equals("SINGAPORE") || this.strCondition1
/* 6344 */           .equals("CAMBODIA") || this.strCondition1.equals("LAO PEOPLES DEMOCRATIC REPUBLIC") || this.strCondition1
/* 6345 */           .equals("THAILAND") || this.strCondition1.equals("VIETNAM")) {
/* 6346 */           treeMap.put("ASEAN *", "Yes");
/*      */         
/*      */         }
/* 6349 */         else if (this.strCondition1.equals("KOREA, REPUBLIC OF") || this.strCondition1
/* 6350 */           .equals("KOREA, DEMOCRATIC PEOPLES REPUBLIC OF")) {
/* 6351 */           treeMap.put("KOREA", "Yes");
/*      */         }
/* 6353 */         else if (this.strCondition1.equals("MALDIVES") || this.strCondition1.equals("AFGHANISTAN") || this.strCondition1
/* 6354 */           .equals("SRI LANKA") || this.strCondition1.equals("INDIA") || this.strCondition1.equals("BANGLADESH") || this.strCondition1
/* 6355 */           .equals("NEPAL") || this.strCondition1.equals("BHUTAN")) {
/* 6356 */           treeMap.put("India/South Asia**", "Yes");
/*      */         
/*      */         }
/* 6359 */         else if (this.strCondition1.equals("HONG KONG")) {
/* 6360 */           treeMap.put("HONG KONG S.A.R of the PRC", "Yes");
/*      */         }
/* 6362 */         else if (this.strCondition1.equals("MACAO")) {
/* 6363 */           treeMap.put("Macao S.A.R of the PRC", "Yes");
/*      */         
/*      */         }
/* 6366 */         else if (this.strCondition1.equals("CHINA")) {
/* 6367 */           treeMap.put("People's Republic of China", "Yes");
/*      */         }
/* 6369 */         else if (this.strCondition1.equals("TAIWAN, PROVINCE OF CHINA")) {
/* 6370 */           treeMap.put("TAIWAN", "Yes");
/*      */         }
/* 6372 */         else if (treeMap.containsKey(this.strCondition1)) {
/* 6373 */           treeMap.put(this.strCondition1, "Yes");
/*      */         } 
/*      */       } 
/*      */     } 
/* 6377 */     Set set = treeMap.keySet();
/* 6378 */     Iterator<String> iterator = set.iterator();
/* 6379 */     resetPrintvars();
/* 6380 */     while (iterator.hasNext()) {
/* 6381 */       this.strCondition1 = iterator.next();
/* 6382 */       this.vPrintDetails.add(this.strCondition1);
/* 6383 */       this.vPrintDetails.add((String)treeMap.get(this.strCondition1));
/*      */     } 
/* 6385 */     treeMap.clear();
/* 6386 */     treeMap = null;
/* 6387 */     this.strHeader = new String[] { "CTRY/Region", "ANNOUNCED" };
/*      */     
/* 6389 */     this.iColWidths = new int[] { 32, 20 };
/*      */     
/* 6391 */     println(":xmp.");
/*      */     
/* 6393 */     printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 6394 */     resetPrintvars();
/* 6395 */     println("* Brunei Darussalam, Indonesia, Cambodia, Lao People's Democratic ");
/* 6396 */     println("Republic, Myanmar, Malaysia, Philippines, Singapore, Thailand, Vietnam ");
/* 6397 */     println("**Bangladesh, Bhutan, India, Sri Lanka, Maldives, Nepal, Afghanistan ");
/* 6398 */     println(":exmp.");
/*      */     
/* 6400 */     println(".*$A_220_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo300() {
/* 6408 */     println(".*$A_300_Begin");
/* 6409 */     prettyPrint(getAttributeValue(this.eiAnnounce, "AMCALLCENTER", " "), 69);
/* 6410 */     println(".*$A_300_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo400() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo500() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo600() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo700() {
/* 6439 */     println(".*$A_725_Begin");
/*      */     
/* 6441 */     prettyPrint(transformXML(getAttributeValue(this.eiAnnounce, "IBMGRENLTR", " ")), 69);
/*      */     
/* 6443 */     println(".*$A_725_End");
/*      */     
/* 6445 */     println(".*$A_726_Begin");
/* 6446 */     prettyPrint(transformXML(getAttributeValue(this.eiAnnounce, "LENOVOGRENLTR", " ")), 69);
/* 6447 */     println(".*$A_726_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo800() {
/* 6456 */     println(".*$A_800_Begin");
/* 6457 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/*      */     
/* 6459 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "150", "", true);
/* 6460 */     this.iColWidths = new int[] { 69 };
/*      */     
/* 6462 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6463 */     resetPrintvars();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6469 */     this.vReturnEntities5 = new Vector();
/* 6470 */     this.vReturnEntities1 = searchEntityVectorLink(this.vSofFrmSofAvail, (String[])null, (String[])null, true, true, "SOFPRICE");
/* 6471 */     this.vReturnEntities5.addAll(this.vReturnEntities1);
/* 6472 */     this.vReturnEntities1 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTPRICE");
/* 6473 */     this.vReturnEntities5.addAll(this.vReturnEntities1);
/* 6474 */     this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATUREPRICE");
/* 6475 */     this.vReturnEntities5.addAll(this.vReturnEntities1);
/* 6476 */     if (this.vReturnEntities5.size() > 0) {
/* 6477 */       logMessage("A_800:SOFPRICE");
/* 6478 */       this.strCondition1 = "";
/* 6479 */       for (this.i = 0; this.i < this.vSofFrmSofAvail.size(); this.i++) {
/* 6480 */         this.eiSof = this.vSofFrmSofAvail.elementAt(this.i);
/* 6481 */         logMessage("_800" + this.eiSof.getKey());
/* 6482 */         this.eiPriceInfo = getDownlinkedEntityItem(this.eiSof, new String[] { "SOFPRICE", "PRICEFININFO" });
/* 6483 */         if (this.eiPriceInfo != null) {
/*      */ 
/*      */           
/* 6486 */           logMessage("_800" + this.eiPriceInfo.getKey());
/*      */ 
/*      */           
/* 6489 */           this.strCondition1 = getAttributeValue(this.eiPriceInfo, "LPFEE", " ");
/* 6490 */           this.strCondition1 += getAttributeValue(this.eiPriceInfo, "CONTRACTCLOSEFEE", " ");
/* 6491 */           this.strCondition1 += getAttributeValue(this.eiPriceInfo, "REMKTGDISCOUNT", " ");
/* 6492 */           this.strCondition1 += getAttributeValue(this.eiSof, "DISTRCODE", " ");
/*      */           
/* 6494 */           this.strCondition1 += getAttributeValue(this.eiSof, "VAE", " ");
/* 6495 */           logMessage("_800 Value of strCondition1:" + this.strCondition1 + ":");
/* 6496 */           if (this.strCondition1.trim().length() > 0) {
/* 6497 */             this.vPrintDetails.add(getAttributeValue(this.eiSof, "MKTGNAME", " "));
/* 6498 */             this.vPrintDetails.add(getAttributeValue(this.eiSof, "OFIDNUMBER", " "));
/* 6499 */             this.vPrintDetails.add(getAttributeValue(this.eiSof, "MKTGNAME", " "));
/* 6500 */             this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "LPFEE", " "));
/* 6501 */             this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "CONTRACTCLOSEFEE", " "));
/* 6502 */             this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "REMKTGDISCOUNT", " "));
/* 6503 */             this.vPrintDetails.add(getAttributeValue(this.eiSof, "DISTRCODE", " "));
/*      */             
/* 6505 */             this.vPrintDetails.add(getAttributeValue(this.eiSof, "VAE", " "));
/*      */           } else {
/*      */             
/* 6508 */             logMessage("_800:Skipping " + this.strCondition1 + " for " + this.eiSof.getKey() + ":Downlinked:" + this.eiPriceInfo.getKey());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6517 */       this.vReturnEntities1 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTPRICE");
/* 6518 */       logMessage("A_800:CMPNTPRICE");
/* 6519 */       if (this.vReturnEntities1.size() > 0) {
/* 6520 */         this.strCondition1 = "";
/* 6521 */         for (this.i = 0; this.i < this.vCmpntFrmCmpntAvail.size(); this.i++) {
/* 6522 */           this.eiComponent = this.vCmpntFrmCmpntAvail.elementAt(this.i);
/* 6523 */           logMessage("_800" + this.eiComponent.getKey());
/* 6524 */           this.eiSof = getUplinkedEntityItem(this.eiComponent, new String[] { "SOFCMPNT", "SOF" });
/* 6525 */           if (this.eiSof == null) {
/* 6526 */             logMessage("_800 No linked SOF found from " + this.eiComponent.getKey());
/*      */           } else {
/*      */             
/* 6529 */             logMessage("_800" + this.eiSof.getKey());
/*      */           } 
/* 6531 */           this.eiPriceInfo = getDownlinkedEntityItem(this.eiComponent, new String[] { "CMPNTPRICE", "PRICEFININFO" });
/* 6532 */           if (this.eiPriceInfo != null) {
/*      */ 
/*      */             
/* 6535 */             logMessage("_800" + this.eiPriceInfo.getKey());
/*      */ 
/*      */             
/* 6538 */             this.strCondition1 = getAttributeValue(this.eiPriceInfo, "LPFEE", " ");
/* 6539 */             this.strCondition1 += getAttributeValue(this.eiPriceInfo, "CONTRACTCLOSEFEE", " ");
/* 6540 */             this.strCondition1 += getAttributeValue(this.eiPriceInfo, "REMKTGDISCOUNT", " ");
/* 6541 */             this.strCondition1 += getAttributeValue(this.eiComponent, "DISTRCODE", " ");
/*      */             
/* 6543 */             this.strCondition1 += getAttributeValue(this.eiComponent, "VAE", " ");
/* 6544 */             if (this.strCondition1.trim().length() > 0) {
/* 6545 */               this.vPrintDetails.add(getQ800SOFMktName(this.eiComponent, this.eiSof));
/* 6546 */               if (this.bIsAnnITS) {
/* 6547 */                 this.strCondition2 = getAttributeShortFlagDesc(this.eiComponent, "ITSCMPNTCATNAME");
/* 6548 */                 if (this.strCondition2 == null) {
/* 6549 */                   this.strCondition2 = "";
/*      */                 }
/* 6551 */                 if (this.strCondition2.trim().length() > 0) {
/* 6552 */                   this.vPrintDetails.add(getDownlinkedEntityAttrValue(this.eiComponent, new String[] { "CMPNTITSCMPNTCATA", "ITSCMPNTCAT" }, "ITSCMPNTCATID"));
/*      */                 }
/*      */                 else {
/*      */                   
/* 6556 */                   this.vPrintDetails.add(getAttributeValue(this.eiSof, "OFIDNUMBER", " "));
/*      */                 } 
/*      */               } else {
/*      */                 
/* 6560 */                 this.vPrintDetails.add(getAttributeValue(this.eiSof, "OFIDNUMBER", " "));
/*      */               } 
/* 6562 */               this.strCondition1 = getCmptToSofMktMsg(this.eiComponent);
/* 6563 */               this.vPrintDetails.add(this.strCondition1);
/* 6564 */               this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "LPFEE", " "));
/* 6565 */               this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "CONTRACTCLOSEFEE", " "));
/* 6566 */               this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "REMKTGDISCOUNT", " "));
/* 6567 */               this.vPrintDetails.add(getAttributeValue(this.eiComponent, "DISTRCODE", " "));
/*      */               
/* 6569 */               this.vPrintDetails.add(getAttributeValue(this.eiComponent, "VAE", " "));
/*      */             } else {
/*      */               
/* 6572 */               logMessage("_800:Skipping " + this.strCondition1 + " for " + this.eiComponent.getKey() + ":Downlinked:" + this.eiPriceInfo
/* 6573 */                   .getKey());
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 6578 */       this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATUREPRICE");
/* 6579 */       if (this.vReturnEntities1.size() > 0) {
/* 6580 */         this.strCondition1 = "";
/* 6581 */         for (this.i = 0; this.i < this.vFeatureFrmFeatureAvail.size(); this.i++) {
/* 6582 */           this.eiFeature = this.vFeatureFrmFeatureAvail.elementAt(this.i);
/* 6583 */           logMessage("_800" + this.eiFeature.getKey());
/* 6584 */           this.eiSof = getUplinkedEntityItem(this.eiFeature, this.strFeatureToSof);
/* 6585 */           if (this.eiSof == null) {
/* 6586 */             logMessage("_800 No linked SOF found from " + this.eiFeature.getKey());
/*      */           } else {
/*      */             
/* 6589 */             logMessage("_800" + this.eiSof.getKey());
/*      */           } 
/* 6591 */           this.eiComponent = getUplinkedEntityItem(this.eiFeature, this.strFeatureToCmpt);
/* 6592 */           logMessage("_800" + this.eiComponent.getKey());
/*      */           
/* 6594 */           this.eiPriceInfo = getDownlinkedEntityItem(this.eiFeature, new String[] { "FEATUREPRICE", "PRICEFININFO" });
/* 6595 */           if (this.eiPriceInfo != null) {
/*      */ 
/*      */             
/* 6598 */             logMessage("_800" + this.eiPriceInfo.getKey());
/*      */ 
/*      */             
/* 6601 */             this.strCondition1 = getAttributeValue(this.eiPriceInfo, "LPFEE", " ");
/* 6602 */             this.strCondition1 += getAttributeValue(this.eiPriceInfo, "CONTRACTCLOSEFEE", " ");
/* 6603 */             this.strCondition1 += getAttributeValue(this.eiPriceInfo, "REMKTGDISCOUNT", " ");
/* 6604 */             this.strCondition1 += getAttributeValue(this.eiComponent, "DISTRCODE", " ");
/*      */             
/* 6606 */             this.strCondition1 += getAttributeValue(this.eiComponent, "VAE", " ");
/* 6607 */             if (this.strCondition1.trim().length() > 0) {
/* 6608 */               this.vPrintDetails.add(getQ800SOFMktName(this.eiComponent, this.eiSof));
/* 6609 */               if (this.bIsAnnITS) {
/* 6610 */                 this.strCondition2 = getAttributeShortFlagDesc(this.eiComponent, "ITSCMPNTCATNAME");
/* 6611 */                 if (this.strCondition2 == null) {
/* 6612 */                   this.strCondition2 = "";
/*      */                 }
/* 6614 */                 if (this.strCondition2.trim().length() > 0) {
/* 6615 */                   this.vPrintDetails.add(getDownlinkedEntityAttrValue(this.eiComponent, new String[] { "CMPNTITSCMPNTCATA", "ITSCMPNTCAT" }, "ITSCMPNTCATID"));
/*      */                 }
/*      */                 else {
/*      */                   
/* 6619 */                   this.vPrintDetails.add(getAttributeValue(this.eiSof, "OFIDNUMBER", " "));
/*      */                 } 
/*      */               } else {
/*      */                 
/* 6623 */                 this.vPrintDetails.add(getAttributeValue(this.eiSof, "OFIDNUMBER", " "));
/*      */               } 
/* 6625 */               this.strCondition1 = getCmptToSofMktMsg(this.eiComponent);
/* 6626 */               this.vPrintDetails.add(this.strCondition1);
/* 6627 */               this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "LPFEE", " "));
/* 6628 */               this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "CONTRACTCLOSEFEE", " "));
/* 6629 */               this.vPrintDetails.add(getAttributeValue(this.eiPriceInfo, "REMKTGDISCOUNT", " "));
/* 6630 */               this.vPrintDetails.add(getAttributeValue(this.eiComponent, "DISTRCODE", " "));
/*      */               
/* 6632 */               this.vPrintDetails.add(getAttributeValue(this.eiComponent, "VAE", " "));
/*      */             } else {
/*      */               
/* 6635 */               logMessage("_800:Skipping " + this.strCondition1 + " for " + this.eiFeature.getKey() + ":Downlinked:" + this.eiPriceInfo
/* 6636 */                   .getKey());
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 6641 */       if (this.vPrintDetails.size() > 0) {
/*      */         
/* 6643 */         this.strHeader = new String[] { "Offering", "ID", "Description", "fee", "fee", "disc", "Dist", " E" };
/*      */         
/* 6645 */         this.iColWidths = new int[] { 15, 8, 20, 3, 5, 5, 4, 3 };
/*      */         
/* 6647 */         println(":xmp.");
/* 6648 */         println("                                                  Close             V");
/* 6649 */         println("                                              L/P cont  Remkt       A");
/*      */         
/* 6651 */         printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 6652 */         resetPrintvars();
/* 6653 */         println(":exmp.");
/*      */       } 
/*      */     } else {
/*      */       
/* 6657 */       logMessage("Q800..no links to priceinfo found..");
/*      */     } 
/*      */     
/* 6660 */     println(".*$A_800_End");
/*      */     
/* 6662 */     println(".*$A_801_Begin");
/* 6663 */     prettyPrint(transformXML(getAttributeValue(this.eiAnnounce, "LENOVOBUSPRTNRATTCH", " ")), 69);
/* 6664 */     println(".*$A_801_End");
/*      */     
/* 6666 */     println(".*$A_805_Begin");
/* 6667 */     this.strParamList1 = new String[] { "STANDARDAMENDTEXT" };
/*      */     
/* 6669 */     printValueListInGroup(this.grpStdAmendText, this.strParamList1, "STANDARDAMENDTEXT_TYPE", "210", "", true);
/* 6670 */     this.iColWidths = new int[] { 69 };
/*      */     
/* 6672 */     printReport(false, (String[])null, this.iColWidths, this.vPrintDetails);
/* 6673 */     resetPrintvars();
/* 6674 */     println(".*$A_805_End");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processLongTo900() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String transformXML(String paramString) {
/* 6691 */     ByteArrayOutputStream byteArrayOutputStream = null;
/* 6692 */     paramString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <!DOCTYPE eAnnounceData SYSTEM \"file:/" + this.DTDFILEPATH + "\" ><eAnnounceData>" + paramString + "</eAnnounceData>";
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 6697 */       StringReader stringReader = new StringReader(paramString);
/* 6698 */       StreamSource streamSource = new StreamSource(stringReader);
/* 6699 */       streamSource.setSystemId(paramString);
/*      */       
/* 6701 */       byteArrayOutputStream = (ByteArrayOutputStream)this.x2g.transform(streamSource);
/*      */     }
/* 6703 */     catch (Exception exception) {
/* 6704 */       println("Error: " + exception + "\n");
/* 6705 */       println("The following is the Offending xml");
/* 6706 */       println(paramString);
/* 6707 */       logError("Exception!" + exception.getMessage() + "\n:***:" + paramString + ":***:");
/*      */     } 
/*      */     
/* 6710 */     return (byteArrayOutputStream != null) ? byteArrayOutputStream.toString() : "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/* 6719 */     return "$Id: RFA_IGSSVS.java,v 1.157 2008/03/19 19:30:44 wendy Exp $";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 6728 */     return getVersion();
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
/*      */   private void printVanillaSVSReport(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 6748 */     logMessage("printVanillaSVSReport: for " + paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6754 */     this.bConditionOK = false;
/* 6755 */     if (paramBoolean2) {
/* 6756 */       this.vReturnEntities1 = searchEntityVectorLink(this.vSofSortedbyMkt, (String[])null, (String[])null, true, true, "SOFPRICE");
/* 6757 */       this.bConditionOK = (this.vReturnEntities1.size() > 0);
/* 6758 */       this.vReturnEntities1 = searchEntityVectorLink(this.vCmptSortedbyMkt, (String[])null, (String[])null, true, true, "CMPNTPRICE");
/* 6759 */       this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 6760 */       this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureSortedbyMkt, (String[])null, (String[])null, true, true, "FEATUREPRICE");
/* 6761 */       this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 6762 */       if (!this.bConditionOK) {
/* 6763 */         logMessage("No Priceinfo links found for " + paramString);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 6768 */     this.strCondition4 = "";
/* 6769 */     this.strCondition2 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6774 */     String str1 = null;
/* 6775 */     String str2 = null;
/*      */     
/* 6777 */     int i = this.vSofSortedbyMkt.size();
/* 6778 */     i += this.vCmptSortedbyMkt.size();
/* 6779 */     i += this.vFeatureSortedbyMkt.size();
/*      */     
/* 6781 */     logMessage("vSofSortedbyMkt" + this.vSofSortedbyMkt.size());
/* 6782 */     logMessage("vCmptSortedbyMkt" + this.vCmptSortedbyMkt.size());
/* 6783 */     logMessage("vFeatureSortedbyMkt" + this.vFeatureSortedbyMkt.size());
/*      */     
/* 6785 */     if (!paramBoolean2) {
/* 6786 */       this.strParamList1 = new String[] { paramString };
/*      */       
/* 6788 */       printValueListInVector(this.vSofSortedbyMkt, this.strParamList1, " ", true, false);
/*      */       
/* 6790 */       resetPrintvars();
/*      */       
/* 6792 */       printValueListInVector(this.vCmptSortedbyMkt, this.strParamList1, " ", true, false);
/*      */       
/* 6794 */       resetPrintvars();
/*      */       
/* 6796 */       printValueListInVector(this.vFeatureSortedbyMkt, this.strParamList1, " ", true, false);
/*      */       
/* 6798 */       resetPrintvars();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6803 */     this.i = 0;
/* 6804 */     this.strFilterAttr = new String[] { "AVAILTYPE" };
/*      */     
/* 6806 */     this.strFilterValue = new String[] { "146" };
/*      */     
/* 6808 */     this.strCondition4 = "";
/* 6809 */     for (this.i = 0; this.i < this.vAllSortedOfferings.size(); this.i++) {
/* 6810 */       logMessage("  printVanillaSVSReport:I is" + this.i);
/* 6811 */       this.eiNextItem = this.vAllSortedOfferings.elementAt(this.i);
/* 6812 */       str2 = this.eiNextItem.getEntityType();
/* 6813 */       if (str2.equals("SOF")) {
/* 6814 */         this.eiSof = this.vAllSortedOfferings.elementAt(this.i);
/* 6815 */         logMessage("printVanillaSVSReport After sof" + this.eiSof.getKey());
/* 6816 */         if (!paramBoolean2) {
/* 6817 */           this.strCondition1 = getAttributeValue(this.eiSof, paramString, " ");
/* 6818 */           this.bConditionOK = (this.strCondition1.trim().length() > 0);
/*      */         } else {
/*      */           
/* 6821 */           this.strEntityTypes = new String[] { "SOFPRICE", "PRICEFININFO" };
/*      */           
/* 6823 */           this.strCondition1 = getDownlinkedEntityAttrValue(this.eiSof, this.strEntityTypes, paramString);
/* 6824 */           this.bConditionOK = (this.strCondition1.trim().length() > 0);
/*      */         } 
/* 6826 */         if (this.bConditionOK) {
/*      */           
/* 6828 */           this.vReturnEntities1 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFAVAIL");
/*      */           
/* 6830 */           this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "AVAIL");
/* 6831 */           if (this.vReturnEntities2.size() > 0) {
/* 6832 */             this.strCondition1 = getAllGeoTags(this.vReturnEntities2);
/* 6833 */             if (!this.strCondition1.equals(this.strCondition2)) {
/* 6834 */               if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 6835 */                 this.strCondition2.length() > 0) {
/* 6836 */                 println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */               }
/*      */               
/* 6839 */               if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/* 6840 */                 println("");
/* 6841 */                 println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/*      */               } 
/* 6843 */               this.strCondition2 = this.strCondition1;
/*      */             } 
/* 6845 */             str1 = getSOFMktName(this.eiSof);
/*      */             
/* 6847 */             logMessage("Marketing Msg :" + str1);
/* 6848 */             prettyPrint(str1, 69);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 6855 */             if (paramBoolean1) {
/* 6856 */               if (!paramBoolean2) {
/* 6857 */                 prettyPrint(transformXML(getAttributeValue(this.eiSof, paramString, " ")), 69);
/*      */               }
/*      */               else {
/*      */                 
/* 6861 */                 this.strEntityTypes = new String[] { "SOFPRICE", "PRICEFININFO" };
/*      */                 
/* 6863 */                 prettyPrint(transformXML(getDownlinkedEntityAttrValue(this.eiSof, this.strEntityTypes, paramString)), 69);
/*      */               
/*      */               }
/*      */             
/*      */             }
/* 6868 */             else if (!paramBoolean2) {
/* 6869 */               prettyPrint(getAttributeValue(this.eiSof, paramString, " "), 69);
/*      */             } else {
/*      */               
/* 6872 */               prettyPrint(getDownlinkedEntityAttrValue(this.eiSof, this.strEntityTypes, paramString), 69);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 6877 */             logMessage("printVanillaSVSReport: NO AVAIL found for " + this.eiSof.getKey());
/*      */           } 
/*      */         } else {
/*      */           
/* 6881 */           logMessage("printVanillaSVSReport:Attribute not returned for " + this.eiSof.getKey());
/*      */         } 
/* 6883 */         println("");
/*      */       } 
/*      */       
/* 6886 */       if (str2.equals("CMPNT")) {
/* 6887 */         this.eiComponent = this.vAllSortedOfferings.elementAt(this.i);
/* 6888 */         logMessage("after component" + this.eiComponent.getKey());
/* 6889 */         if (!paramBoolean2) {
/* 6890 */           this.strCondition1 = getAttributeValue(this.eiComponent, paramString, " ");
/* 6891 */           this.bConditionOK = (this.strCondition1.trim().length() > 0);
/*      */         } else {
/*      */           
/* 6894 */           this.strEntityTypes = new String[] { "CMPNTPRICE", "PRICEFININFO" };
/*      */           
/* 6896 */           this.strCondition1 = getDownlinkedEntityAttrValue(this.eiComponent, this.strEntityTypes, paramString);
/* 6897 */           this.bConditionOK = (this.strCondition1.trim().length() > 0);
/*      */         } 
/* 6899 */         if (this.bConditionOK) {
/*      */           
/* 6901 */           this.vReturnEntities1 = searchEntityItemLink(this.eiComponent, (String[])null, (String[])null, true, true, "CMPNTAVAIL");
/*      */           
/* 6903 */           this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "AVAIL");
/* 6904 */           if (this.vReturnEntities2.size() > 0) {
/* 6905 */             this.strCondition1 = getAllGeoTags(this.vReturnEntities2);
/* 6906 */             if (!this.strCondition1.equals(this.strCondition2)) {
/* 6907 */               if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 6908 */                 this.strCondition2.length() > 0) {
/* 6909 */                 println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */               }
/*      */               
/* 6912 */               if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/* 6913 */                 println("");
/* 6914 */                 println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/*      */               } 
/* 6916 */               this.strCondition2 = this.strCondition1;
/*      */             } 
/*      */             
/* 6919 */             str1 = getCmptToSofMktMsg(this.eiComponent);
/*      */             
/* 6921 */             logMessage("1) Marketing Msg :" + str1);
/* 6922 */             prettyPrint(str1, 69);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 6927 */             if (paramBoolean1) {
/* 6928 */               if (!paramBoolean2) {
/* 6929 */                 prettyPrint(transformXML(getAttributeValue(this.eiComponent, paramString, " ")), 69);
/*      */               }
/*      */               else {
/*      */                 
/* 6933 */                 this.strEntityTypes = new String[] { "CMPNTPRICE", "PRICEFININFO" };
/*      */                 
/* 6935 */                 prettyPrint(transformXML(getDownlinkedEntityAttrValue(this.eiComponent, this.strEntityTypes, paramString)), 69);
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/* 6941 */             else if (!paramBoolean2) {
/* 6942 */               prettyPrint(getAttributeValue(this.eiComponent, paramString, " "), 69);
/*      */             } else {
/*      */               
/* 6945 */               this.strEntityTypes = new String[] { "CMPNTPRICE", "PRICEFININFO" };
/*      */               
/* 6947 */               prettyPrint(getDownlinkedEntityAttrValue(this.eiComponent, this.strEntityTypes, paramString), 69);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 6952 */             logMessage("printVanillaSVSReport: No AVAIL found for " + this.eiComponent.getKey());
/*      */           } 
/*      */         } else {
/*      */           
/* 6956 */           logMessage("printVanillaSVSReport:Attribute not returned for " + this.eiComponent.getKey());
/*      */         } 
/* 6958 */         println("");
/*      */       } 
/*      */       
/* 6961 */       if (str2.equals("FEATURE")) {
/* 6962 */         this.eiFeature = this.vAllSortedOfferings.elementAt(this.i);
/* 6963 */         logMessage("After feature " + this.eiFeature.getKey());
/* 6964 */         if (!paramBoolean2) {
/* 6965 */           this.strCondition1 = getAttributeValue(this.eiFeature, paramString, " ");
/* 6966 */           this.bConditionOK = (this.strCondition1.trim().length() > 0);
/*      */         } else {
/*      */           
/* 6969 */           this.strEntityTypes = new String[] { "FEATUREPRICE", "PRICEFININFO" };
/*      */           
/* 6971 */           this.strCondition1 = getDownlinkedEntityAttrValue(this.eiFeature, this.strEntityTypes, paramString);
/* 6972 */           this.bConditionOK = (this.strCondition1.trim().length() > 0);
/*      */         } 
/* 6974 */         if (this.bConditionOK) {
/*      */           
/* 6976 */           this.vReturnEntities1 = searchEntityItemLink(this.eiFeature, (String[])null, (String[])null, true, true, "FEATUREAVAIL");
/*      */           
/* 6978 */           this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "AVAIL");
/* 6979 */           if (this.vReturnEntities2.size() > 0) {
/* 6980 */             this.strCondition1 = getAllGeoTags(this.vReturnEntities2);
/* 6981 */             if (!this.strCondition1.equals(this.strCondition2)) {
/* 6982 */               if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 6983 */                 this.strCondition2.length() > 0) {
/* 6984 */                 println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */               }
/*      */               
/* 6987 */               if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/* 6988 */                 println("");
/* 6989 */                 println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/*      */               } 
/* 6991 */               this.strCondition2 = this.strCondition1;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 7004 */             str1 = getfeatureToSofMktMsg(this.eiFeature);
/* 7005 */             logMessage("2) Marketing Msg :" + str1);
/* 7006 */             prettyPrint(str1, 69);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 7011 */             if (paramBoolean1) {
/* 7012 */               if (!paramBoolean2) {
/* 7013 */                 prettyPrint(transformXML(getAttributeValue(this.eiFeature, paramString, " ")), 69);
/*      */               }
/*      */               else {
/*      */                 
/* 7017 */                 this.strEntityTypes = new String[] { "FEATUREPRICE", "PRICEFININFO" };
/*      */                 
/* 7019 */                 prettyPrint(transformXML(getDownlinkedEntityAttrValue(this.eiFeature, this.strEntityTypes, paramString)), 69);
/*      */               
/*      */               }
/*      */             
/*      */             }
/* 7024 */             else if (!paramBoolean2) {
/* 7025 */               prettyPrint(getAttributeValue(this.eiFeature, paramString, " "), 69);
/*      */             } else {
/*      */               
/* 7028 */               this.strEntityTypes = new String[] { "CMPNTPRICE", "PRICEFININFO" };
/*      */               
/* 7030 */               prettyPrint(getDownlinkedEntityAttrValue(this.eiFeature, this.strEntityTypes, paramString), 69);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 7035 */             logMessage("printVanillaSVSReport:No AVAIL found for " + this.eiFeature.getKey());
/*      */           } 
/*      */         } else {
/*      */           
/* 7039 */           logMessage("printVanillaSVSReport:Attribute not returned for " + this.eiFeature.getKey());
/*      */         } 
/* 7041 */         println("");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 7046 */     if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 7047 */       this.strCondition2.length() > 0) {
/* 7048 */       println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */     }
/*      */ 
/*      */     
/* 7052 */     println("");
/*      */   }
/*      */ 
/*      */   
/*      */   private void printIntSuppMktFinInfo(String paramString) {
/* 7057 */     printIntSuppMktFinInfo(paramString, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printIntSuppMktFinInfo(String paramString, boolean paramBoolean) {
/* 7068 */     logMessage("printIntSuppMktFinInfo for " + paramString);
/*      */     
/* 7070 */     this.bConditionOK = false;
/* 7071 */     this.vReturnEntities1 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTFINOF");
/* 7072 */     this.bConditionOK = (this.vReturnEntities1.size() > 0);
/* 7073 */     this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATUREFINOF");
/* 7074 */     this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7075 */     if (!this.bConditionOK) {
/* 7076 */       logMessage("No Fininfo links found for " + paramString);
/*      */       
/*      */       return;
/*      */     } 
/* 7080 */     boolean bool1 = (this.vCmpntFrmCmpntAvail.size() == 0) ? true : false;
/* 7081 */     boolean bool2 = (this.vFeatureFrmFeatureAvail.size() == 0) ? true : false;
/* 7082 */     boolean bool3 = (bool1 && bool2) ? true : false;
/*      */     
/* 7084 */     int i = this.vCmpntFrmCmpntAvail.size();
/* 7085 */     i += this.vFeatureFrmFeatureAvail.size();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7093 */     String str = null;
/*      */     
/* 7095 */     this.strCondition4 = "";
/*      */     
/* 7097 */     logMessage("vComponents" + this.vCmpntFrmCmpntAvail.size());
/* 7098 */     logMessage("vFeatures" + this.vFeatureFrmFeatureAvail.size());
/*      */     
/* 7100 */     this.i = 0;
/* 7101 */     while (!bool3) {
/*      */       
/* 7103 */       if (!bool1) {
/* 7104 */         this.eiComponent = this.vCmpntFrmCmpntAvail.elementAt(this.i);
/* 7105 */         logMessage("printIntSuppMktFinInfo after component" + this.eiComponent.getKey());
/*      */ 
/*      */ 
/*      */         
/* 7109 */         str = getCmptToSofMktMsg(this.eiComponent);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 7116 */         this.vReturnEntities1 = searchEntityItemLink(this.eiComponent, (String[])null, (String[])null, true, true, "CMPNTFINOF");
/* 7117 */         this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "FINOF");
/*      */         
/* 7119 */         if (this.vReturnEntities2.size() > 0) {
/* 7120 */           logMessage("1) printIntSuppMktFinInfo Marketing Msg :" + str);
/* 7121 */           if (i > 1) {
/* 7122 */             println("Component Offerings");
/* 7123 */             prettyPrint(str, 69);
/* 7124 */             if (this.vReturnEntities2.size() > 0 && paramBoolean) {
/* 7125 */               println("The financing will enable:");
/*      */             }
/*      */           } 
/*      */           
/* 7129 */           for (this.j = 0; this.j < this.vReturnEntities2.size(); this.j++) {
/* 7130 */             this.eiFinof = this.vReturnEntities2.elementAt(this.j);
/* 7131 */             this.strCondition1 = transformXML(getAttributeValue(this.eiFinof, paramString, " "));
/*      */             
/* 7133 */             if (this.strCondition1.trim().length() > 0)
/*      */             {
/* 7135 */               prettyPrint(this.strCondition1, 69);
/*      */             }
/*      */           } 
/*      */           
/* 7139 */           println("");
/*      */         } else {
/*      */           
/* 7142 */           logMessage("No info returned for attr:" + this.eiComponent.getKey() + ":" + paramString);
/*      */         } 
/* 7144 */         println("");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 7150 */       if (!bool2) {
/* 7151 */         this.eiFeature = this.vFeatureFrmFeatureAvail.elementAt(this.i);
/* 7152 */         logMessage("printIntSuppMktFinInfo after feature" + this.eiFeature.getKey());
/*      */         
/* 7154 */         str = getfeatureToSofMktMsg(this.eiFeature);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 7161 */         this.vReturnEntities1 = searchEntityItemLink(this.eiFeature, (String[])null, (String[])null, true, true, "FEATUREFINOF");
/* 7162 */         this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "FINOF");
/* 7163 */         if (this.vReturnEntities2.size() > 0) {
/* 7164 */           logMessage("2) printIntSuppMktFinInfo Marketing Msg :" + str);
/* 7165 */           if (i > 1) {
/* 7166 */             println("Feature ");
/* 7167 */             prettyPrint(str, 69);
/* 7168 */             if (this.vReturnEntities2.size() > 0 && paramBoolean) {
/* 7169 */               println("The financing will enable:");
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 7174 */           for (this.j = 0; this.j < this.vReturnEntities2.size(); this.j++) {
/* 7175 */             this.eiFinof = this.vReturnEntities2.elementAt(this.j);
/* 7176 */             this.strCondition1 = transformXML(getAttributeValue(this.eiFinof, paramString, " "));
/* 7177 */             if (this.strCondition1.trim().length() > 0)
/*      */             {
/* 7179 */               prettyPrint(this.strCondition1, 69);
/*      */             }
/*      */           } 
/*      */           
/* 7183 */           println("");
/*      */         } else {
/*      */           
/* 7186 */           logMessage("No info returned for attr:" + this.eiFeature.getKey() + ":" + paramString);
/*      */         } 
/* 7188 */         println("");
/*      */       } 
/*      */       
/* 7191 */       this.i++;
/* 7192 */       bool1 = (this.vCmpntFrmCmpntAvail.size() <= this.i) ? true : false;
/* 7193 */       bool2 = (this.vFeatureFrmFeatureAvail.size() <= this.i) ? true : false;
/*      */       
/* 7195 */       bool3 = (bool1 && bool2) ? true : false;
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
/*      */   private void printFeatureBenefit(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2) {
/* 7223 */     logMessage("printFeatureBenefit:" + paramString1 + ":Q:" + paramString2);
/* 7224 */     this.bConditionOK = false;
/* 7225 */     boolean bool1 = false;
/* 7226 */     boolean bool2 = false;
/* 7227 */     boolean bool3 = false;
/* 7228 */     String[] arrayOfString1 = { "TYPE" };
/*      */     
/* 7230 */     String[] arrayOfString2 = { paramString1 };
/*      */     
/* 7232 */     String str1 = null;
/*      */     
/* 7234 */     int i = this.vSofSortedbyMkt.size();
/* 7235 */     i += this.vCmptSortedbyMkt.size();
/* 7236 */     i += this.vFeatureSortedbyMkt.size();
/*      */     
/* 7238 */     String str2 = paramString1.equals("110") ? "CROSSELL" : "UPSELL";
/* 7239 */     str2 = paramString1.equals("120") ? "" : str2;
/*      */     
/* 7241 */     if (paramBoolean1 && !paramBoolean2) {
/* 7242 */       this.vReturnEntities1 = searchEntityVectorLink(this.vSofSortedbyMkt, arrayOfString1, arrayOfString2, true, false, "SOFRELSOF");
/* 7243 */       this.bConditionOK = (this.vReturnEntities1.size() > 0);
/* 7244 */       this.vReturnEntities1 = searchEntityVectorLink(this.vCmptSortedbyMkt, arrayOfString1, arrayOfString2, true, false, "CMPNTRELCMPNT");
/* 7245 */       this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7246 */       this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureSortedbyMkt, arrayOfString1, arrayOfString2, true, false, "FEATURERELFEATURE");
/*      */       
/* 7248 */       this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7249 */       if (!this.bConditionOK) {
/* 7250 */         logMessage("No Parent links found for Q" + paramString2);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 7255 */     if (paramBoolean2) {
/* 7256 */       this.vReturnEntities1 = searchEntityVectorLink(this.vSofSortedbyMkt, arrayOfString1, arrayOfString2, true, true, "SOFRELSOF");
/* 7257 */       this.bConditionOK = (this.vReturnEntities1.size() > 0);
/* 7258 */       bool2 = (this.vReturnEntities1.size() > 1) ? true : false;
/* 7259 */       this.vReturnEntities1 = searchEntityVectorLink(this.vSofSortedbyMkt, arrayOfString1, arrayOfString2, true, true, "SOFRELCMPNT");
/* 7260 */       this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7261 */       bool2 = !bool2 ? ((this.vReturnEntities1.size() > 1) ? true : false) : bool2;
/* 7262 */       this.vReturnEntities1 = searchEntityVectorLink(this.vSofSortedbyMkt, arrayOfString1, arrayOfString2, true, true, "SOFRELFEATURE");
/* 7263 */       this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7264 */       bool2 = !bool2 ? ((this.vReturnEntities1.size() > 1) ? true : false) : bool2;
/* 7265 */       if (!this.bConditionOK) {
/* 7266 */         logMessage("No Children links found for SOF Q" + paramString2);
/*      */       }
/* 7268 */       this.bConditionOK1 = false;
/* 7269 */       this.vReturnEntities1 = searchEntityVectorLink(this.vCmptSortedbyMkt, arrayOfString1, arrayOfString2, true, true, "CMPNTRELCMPNT");
/*      */       
/* 7271 */       this.bConditionOK1 = !this.bConditionOK1 ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK1;
/* 7272 */       bool1 = (this.vReturnEntities1.size() > 1) ? true : false;
/* 7273 */       this.vReturnEntities1 = searchEntityVectorLink(this.vCmptSortedbyMkt, arrayOfString1, arrayOfString2, true, true, "CMPNTRELFEATURE");
/* 7274 */       this.bConditionOK1 = !this.bConditionOK1 ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK1;
/* 7275 */       bool1 = !bool1 ? ((this.vReturnEntities1.size() > 1) ? true : false) : bool1;
/* 7276 */       this.vReturnEntities1 = searchEntityVectorLink(this.vCmptSortedbyMkt, arrayOfString1, arrayOfString2, true, true, "CMPNTRELSOF");
/* 7277 */       this.bConditionOK1 = !this.bConditionOK1 ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK1;
/* 7278 */       bool1 = !bool1 ? ((this.vReturnEntities1.size() > 1) ? true : false) : bool1;
/* 7279 */       if (!this.bConditionOK1) {
/* 7280 */         logMessage("No Children links found for CMPNT Q" + paramString2);
/*      */       }
/* 7282 */       this.bConditionOK = !this.bConditionOK ? this.bConditionOK1 : this.bConditionOK;
/*      */       
/* 7284 */       this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureSortedbyMkt, arrayOfString1, arrayOfString2, true, true, "FEATURERELFEATURE");
/* 7285 */       this.bConditionOK1 = !this.bConditionOK1 ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK1;
/* 7286 */       bool3 = (this.vReturnEntities1.size() > 1) ? true : false;
/* 7287 */       this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureSortedbyMkt, arrayOfString1, arrayOfString2, true, true, "FEATURERELCMPNT");
/* 7288 */       this.bConditionOK1 = !this.bConditionOK1 ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK1;
/* 7289 */       bool3 = !bool3 ? ((this.vReturnEntities1.size() > 1) ? true : false) : bool3;
/* 7290 */       this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureSortedbyMkt, arrayOfString1, arrayOfString2, true, true, "FEATURERELSOF");
/* 7291 */       this.bConditionOK1 = !this.bConditionOK1 ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK1;
/* 7292 */       bool3 = !bool3 ? ((this.vReturnEntities1.size() > 1) ? true : false) : bool3;
/* 7293 */       if (!this.bConditionOK) {
/* 7294 */         logMessage("No Children links found for Q" + paramString2);
/*      */       }
/*      */     } 
/*      */     
/* 7298 */     this.bConditionOK = false;
/* 7299 */     this.bConditionOK1 = false;
/* 7300 */     resetPrintvars();
/* 7301 */     this.strFilterAttr = new String[] { "AVAILTYPE" };
/*      */     
/* 7303 */     this.strFilterValue = new String[] { "146" };
/*      */ 
/*      */     
/* 7306 */     this.strCondition4 = "";
/* 7307 */     this.strCondition2 = "";
/* 7308 */     this.strCondition1 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7315 */     String str3 = null;
/* 7316 */     this.i = 0;
/* 7317 */     for (this.i = 0; this.i < this.vAllSortedOfferings.size(); this.i++) {
/* 7318 */       logMessage("  printFeatureBenefit:I is" + this.i);
/* 7319 */       this.eiNextItem = this.vAllSortedOfferings.elementAt(this.i);
/* 7320 */       str1 = this.eiNextItem.getEntityType();
/* 7321 */       if (str1.equals("SOF")) {
/* 7322 */         this.eiSof = this.vAllSortedOfferings.elementAt(this.i);
/* 7323 */         logMessage("printFeatureBenefit After sof" + this.eiSof.getKey());
/* 7324 */         this.strEntityTypes = new String[] { "SOFRELSOF" };
/*      */         
/* 7326 */         this.strCondition4 = getDownlinkedEntityAttrValue(this.eiSof, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2);
/*      */         
/* 7328 */         if (paramBoolean2) {
/* 7329 */           this.strCondition4 = getAttributeValue(this.eiSof, str2, " ");
/* 7330 */           if (this.strCondition4.trim().length() == 0) {
/* 7331 */             this.vReturnEntities1 = searchEntityItemLink(this.eiSof, arrayOfString1, arrayOfString2, true, true, "SOFRELSOF");
/* 7332 */             this.bConditionOK = (this.vReturnEntities1.size() > 0);
/* 7333 */             this.vReturnEntities1 = searchEntityItemLink(this.eiSof, arrayOfString1, arrayOfString2, true, true, "SOFRELCMPNT");
/* 7334 */             this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7335 */             this.vReturnEntities1 = searchEntityItemLink(this.eiSof, arrayOfString1, arrayOfString2, true, true, "SOFRELFEATURE");
/* 7336 */             this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7337 */             if (this.bConditionOK) {
/* 7338 */               this.strCondition4 = ".";
/*      */             }
/*      */           } 
/*      */         } 
/* 7342 */         if (this.strCondition4.trim().length() > 0) {
/*      */           
/* 7344 */           this.vReturnEntities1 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFAVAIL");
/*      */           
/* 7346 */           this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "AVAIL");
/* 7347 */           if (this.vReturnEntities2.size() > 0) {
/* 7348 */             this.strCondition1 = getAllGeoTags(this.vReturnEntities2);
/* 7349 */             if (!this.strCondition1.equals(this.strCondition2)) {
/* 7350 */               if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 7351 */                 this.strCondition2.length() > 0) {
/* 7352 */                 println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */               }
/*      */               
/* 7355 */               if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/* 7356 */                 println("");
/* 7357 */                 println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/*      */               } 
/* 7359 */               this.strCondition2 = this.strCondition1;
/*      */             } 
/* 7361 */             str3 = getSOFMktName(this.eiSof);
/* 7362 */             logMessage("Marketing Msg :" + str3);
/* 7363 */             if (i > 1 || bool2) {
/* 7364 */               prettyPrint(str3, 69);
/* 7365 */               println(":p.");
/* 7366 */               println(":ul c.");
/*      */             } else {
/*      */               
/* 7369 */               println(":p.");
/* 7370 */               prettyPrint(str3, 69);
/*      */             } 
/* 7372 */             if (paramBoolean2 && !str2.equals("")) {
/* 7373 */               this.strCondition4 = transformXML(getAttributeValue(this.eiSof, str2, " "));
/*      */               
/* 7375 */               if (this.strCondition4.trim().length() > 0)
/*      */               {
/* 7377 */                 prettyPrint(this.strCondition4, 69);
/*      */               }
/*      */             } 
/*      */             
/* 7381 */             if (paramBoolean2) {
/*      */               
/* 7383 */               this.strEntityTypes = new String[] { "SOFRELSOF" };
/*      */               
/* 7385 */               this.vReturnEntities1 = searchEntityItemLink(this.eiSof, arrayOfString1, arrayOfString2, true, true, "SOFRELSOF");
/* 7386 */               if (this.vReturnEntities1.size() > 0) {
/* 7387 */                 for (this.j = 0; this.j < this.vReturnEntities1.size(); this.j++) {
/* 7388 */                   this.eiNextItem = this.vReturnEntities1.elementAt(this.j);
/* 7389 */                   this.eiNextItem1 = (EntityItem)this.eiNextItem.getDownLink(0);
/* 7390 */                   this.strCondition4 = getSOFMktName(this.eiNextItem1);
/* 7391 */                   logMessage("0) Child Marketing Msg for SOFRELSOF" + this.eiNextItem.getKey() + " Downlinked from " + this.eiSof
/* 7392 */                       .getKey() + " is " + this.strCondition4);
/*      */                   
/* 7394 */                   if ((i > 1 || this.vReturnEntities1.size() > 1) && this.strCondition4.trim().length() > 0) {
/* 7395 */                     prettyPrint(":li." + this.strCondition4, 69);
/*      */ 
/*      */                   
/*      */                   }
/* 7399 */                   else if (this.strCondition4.trim().length() > 0) {
/* 7400 */                     println(":p.");
/* 7401 */                     prettyPrint(this.strCondition4, 69);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } else {
/*      */                 
/* 7407 */                 logMessage("0)  No Child Marketing Msg for SOFRELSOF" + this.eiSof.getKey());
/*      */               } 
/*      */               
/* 7410 */               this.strEntityTypes = new String[] { "SOFRELCMPNT" };
/*      */               
/* 7412 */               this.vReturnEntities1 = searchEntityItemLink(this.eiSof, arrayOfString1, arrayOfString2, true, true, "SOFRELCMPNT");
/* 7413 */               if (this.vReturnEntities1.size() > 0) {
/* 7414 */                 for (this.j = 0; this.j < this.vReturnEntities1.size(); this.j++) {
/* 7415 */                   this.eiNextItem = this.vReturnEntities1.elementAt(this.j);
/* 7416 */                   this.eiNextItem1 = (EntityItem)this.eiNextItem.getDownLink(0);
/* 7417 */                   this.strCondition4 = getCmptToSofMktMsg(this.eiNextItem1);
/* 7418 */                   logMessage("0)  Child Marketing Msg for SOFRELCMPNT" + this.eiNextItem.getKey() + " Downlinked from " + this.eiSof
/* 7419 */                       .getKey() + " is " + this.strCondition4);
/* 7420 */                   if ((i > 1 || this.vReturnEntities1.size() > 1) && this.strCondition4.trim().length() > 0) {
/* 7421 */                     prettyPrint(":li." + this.strCondition4, 69);
/*      */ 
/*      */                   
/*      */                   }
/* 7425 */                   else if (this.strCondition4.trim().length() > 0) {
/* 7426 */                     println(":p.");
/* 7427 */                     prettyPrint(this.strCondition4, 69);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } else {
/*      */                 
/* 7433 */                 logMessage("0)  No Child Marketing Msg for SOFRELCMPNT" + this.eiSof.getKey());
/*      */               } 
/*      */               
/* 7436 */               this.strEntityTypes = new String[] { "SOFRELFEATURE" };
/*      */               
/* 7438 */               this.vReturnEntities1 = searchEntityItemLink(this.eiSof, arrayOfString1, arrayOfString2, true, true, "SOFRELFEATURE");
/* 7439 */               if (this.vReturnEntities1.size() > 0) {
/* 7440 */                 for (this.j = 0; this.j < this.vReturnEntities1.size(); this.j++) {
/* 7441 */                   this.eiNextItem = this.vReturnEntities1.elementAt(this.j);
/* 7442 */                   this.eiNextItem1 = (EntityItem)this.eiNextItem.getDownLink(0);
/* 7443 */                   this.strCondition4 = getfeatureToSofMktMsg(this.eiNextItem1);
/* 7444 */                   logMessage("0)  Child Marketing Msg for SOFRELFEATURE" + this.eiNextItem.getKey() + " Downlinked from " + this.eiSof
/* 7445 */                       .getKey() + " is " + this.strCondition4);
/*      */                   
/* 7447 */                   if ((i > 1 || this.vReturnEntities1.size() > 1) && this.strCondition4.trim().length() > 0) {
/* 7448 */                     prettyPrint(":li." + this.strCondition4, 69);
/*      */ 
/*      */                   
/*      */                   }
/* 7452 */                   else if (this.strCondition4.trim().length() > 0) {
/* 7453 */                     println(":p.");
/* 7454 */                     prettyPrint(this.strCondition4, 69);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } else {
/*      */                 
/* 7460 */                 logMessage("0)  No Child Marketing Msg for SOFRELFEATURE" + this.eiSof.getKey());
/*      */               } 
/*      */               
/* 7463 */               if (i > 1 || bool2) {
/* 7464 */                 println(":eul.");
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/* 7469 */             if (paramBoolean1) {
/* 7470 */               this.strEntityTypes = new String[] { "SOFRELSOF" };
/*      */               
/* 7472 */               if (paramBoolean2) {
/* 7473 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiSof, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7475 */                 if (this.strCondition4.trim().length() > 0) {
/* 7476 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/* 7478 */                 this.strEntityTypes = new String[] { "SOFRELCMPNT" };
/*      */                 
/* 7480 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiSof, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7482 */                 if (this.strCondition4.trim().length() > 0) {
/* 7483 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/* 7485 */                 this.strEntityTypes = new String[] { "SOFRELFEATURE" };
/*      */                 
/* 7487 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiSof, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7489 */                 if (this.strCondition4.trim().length() > 0) {
/* 7490 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/*      */               } else {
/*      */                 
/* 7494 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiSof, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7496 */                 if (this.strCondition4.trim().length() > 0) {
/* 7497 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/* 7504 */           logMessage("No related rows found for " + this.eiSof.getKey());
/*      */         } 
/* 7506 */         println("");
/*      */       } 
/*      */ 
/*      */       
/* 7510 */       if (str1.equals("CMPNT")) {
/* 7511 */         this.eiComponent = this.vAllSortedOfferings.elementAt(this.i);
/* 7512 */         logMessage("after component" + this.eiComponent.getKey());
/*      */         
/* 7514 */         this.strEntityTypes = new String[] { "CMPNTRELCMPNT" };
/*      */         
/* 7516 */         this.strCondition4 = ".";
/* 7517 */         if (paramBoolean2) {
/* 7518 */           this.strCondition4 = getAttributeValue(this.eiComponent, str2, "");
/* 7519 */           logMessage("Value of " + str2 + " is |" + this.strCondition4 + "|");
/* 7520 */           if (this.strCondition4.trim().length() == 0) {
/* 7521 */             logMessage("Checking for relators from " + this.eiComponent.getKey());
/* 7522 */             this.vReturnEntities1 = searchEntityItemLink(this.eiComponent, arrayOfString1, arrayOfString2, true, true, "CMPNTRELSOF");
/* 7523 */             this.bConditionOK = (this.vReturnEntities1.size() > 0);
/* 7524 */             this.vReturnEntities1 = searchEntityItemLink(this.eiComponent, arrayOfString1, arrayOfString2, true, true, "CMPNTRELCMPNT");
/* 7525 */             this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7526 */             this.vReturnEntities1 = searchEntityItemLink(this.eiComponent, arrayOfString1, arrayOfString2, true, true, "CMPNTRELFEATURE");
/* 7527 */             this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7528 */             if (this.bConditionOK) {
/* 7529 */               this.strCondition4 = ".";
/*      */             }
/*      */           } 
/*      */         } else {
/*      */           
/* 7534 */           this.strCondition4 = getDownlinkedEntityAttrValue(this.eiComponent, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2);
/*      */         } 
/* 7536 */         if (this.strCondition4.trim().length() > 0) {
/*      */           
/* 7538 */           this.vReturnEntities1 = searchEntityItemLink(this.eiComponent, (String[])null, (String[])null, true, true, "CMPNTAVAIL");
/*      */           
/* 7540 */           this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "AVAIL");
/* 7541 */           if (this.vReturnEntities2.size() > 0) {
/* 7542 */             this.strCondition1 = getAllGeoTags(this.vReturnEntities2);
/* 7543 */             if (!this.strCondition1.equals(this.strCondition2)) {
/* 7544 */               if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 7545 */                 this.strCondition2.length() > 0) {
/* 7546 */                 println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */               }
/*      */               
/* 7549 */               if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/* 7550 */                 println("");
/* 7551 */                 println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/*      */               } 
/* 7553 */               this.strCondition2 = this.strCondition1;
/*      */             } 
/* 7555 */             str3 = getCmptToSofMktMsg(this.eiComponent);
/* 7556 */             logMessage("1) Marketing Msg :" + str3);
/* 7557 */             if (i > 1 || bool1) {
/* 7558 */               prettyPrint(str3, 69);
/* 7559 */               println(":p.");
/* 7560 */               println(":ul c.");
/*      */             } else {
/*      */               
/* 7563 */               println(":p.");
/* 7564 */               prettyPrint(str3, 69);
/*      */             } 
/*      */             
/* 7567 */             if (paramBoolean2 && !str2.equals("")) {
/* 7568 */               this.strCondition4 = transformXML(getAttributeValue(this.eiComponent, str2, " "));
/* 7569 */               if (this.strCondition4.trim().length() > 0)
/*      */               {
/* 7571 */                 prettyPrint("       " + this.strCondition4, 69);
/*      */               }
/*      */             } 
/* 7574 */             if (paramBoolean2) {
/* 7575 */               this.strEntityTypes = new String[] { "CMPNTRELCMPNT" };
/*      */               
/* 7577 */               this.vReturnEntities1 = searchEntityItemLink(this.eiComponent, arrayOfString1, arrayOfString2, true, true, "CMPNTRELCMPNT");
/* 7578 */               if (this.vReturnEntities1.size() > 0) {
/* 7579 */                 for (this.j = 0; this.j < this.vReturnEntities1.size(); this.j++) {
/* 7580 */                   this.eiNextItem = this.vReturnEntities1.elementAt(this.j);
/* 7581 */                   this.eiNextItem1 = (EntityItem)this.eiNextItem.getDownLink(0);
/* 7582 */                   this.strCondition4 = getCmptToSofMktMsg(this.eiNextItem1);
/* 7583 */                   logMessage("1)  Child Marketing Msg for CMPNTRELCMPNT" + this.eiNextItem.getKey() + " Downlinked from " + this.eiComponent
/* 7584 */                       .getKey() + " is " + this.strCondition4);
/*      */                   
/* 7586 */                   if ((i > 1 || this.vReturnEntities1.size() > 1) && this.strCondition4.trim().length() > 0) {
/* 7587 */                     prettyPrint(":li." + this.strCondition4, 69);
/*      */ 
/*      */                   
/*      */                   }
/* 7591 */                   else if (this.strCondition4.trim().length() > 0) {
/* 7592 */                     println(":p.");
/* 7593 */                     prettyPrint(this.strCondition4, 69);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } else {
/*      */                 
/* 7599 */                 logMessage("1)  No Child Marketing Msg for CMPNTRELCMPNT" + this.eiComponent.getKey());
/*      */               } 
/*      */               
/* 7602 */               this.strEntityTypes = new String[] { "CMPNTRELFEATURE" };
/*      */               
/* 7604 */               this.vReturnEntities1 = searchEntityItemLink(this.eiComponent, arrayOfString1, arrayOfString2, true, true, "CMPNTRELFEATURE");
/* 7605 */               if (this.vReturnEntities1.size() > 0) {
/* 7606 */                 for (this.j = 0; this.j < this.vReturnEntities1.size(); this.j++) {
/* 7607 */                   this.eiNextItem = this.vReturnEntities1.elementAt(this.j);
/* 7608 */                   this.eiNextItem1 = (EntityItem)this.eiNextItem.getDownLink(0);
/* 7609 */                   this.strCondition4 = getfeatureToSofMktMsg(this.eiNextItem1);
/* 7610 */                   logMessage("1)  Child Marketing Msg for CMPNTRELFEATURE" + this.eiNextItem.getKey() + " Downlinked from " + this.eiComponent
/* 7611 */                       .getKey() + " is " + this.strCondition4);
/*      */                   
/* 7613 */                   if ((i > 1 || this.vReturnEntities1.size() > 1) && this.strCondition4.trim().length() > 0) {
/* 7614 */                     prettyPrint(":li." + this.strCondition4, 69);
/*      */ 
/*      */                   
/*      */                   }
/* 7618 */                   else if (this.strCondition4.trim().length() > 0) {
/* 7619 */                     println(":p.");
/* 7620 */                     prettyPrint(this.strCondition4, 69);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } else {
/*      */                 
/* 7626 */                 logMessage("1)  No Child Marketing Msg for CMPNTRELFEATURE" + this.eiComponent.getKey());
/*      */               } 
/*      */               
/* 7629 */               this.strEntityTypes = new String[] { "CMPNTRELSOF" };
/*      */               
/* 7631 */               this.vReturnEntities1 = searchEntityItemLink(this.eiComponent, arrayOfString1, arrayOfString2, true, true, "CMPNTRELSOF");
/* 7632 */               if (this.vReturnEntities1.size() > 0) {
/* 7633 */                 for (this.j = 0; this.j < this.vReturnEntities1.size(); this.j++) {
/* 7634 */                   this.eiNextItem = this.vReturnEntities1.elementAt(this.j);
/* 7635 */                   logMessage("****1) " + this.eiNextItem.getKey());
/* 7636 */                   this.eiNextItem1 = (EntityItem)this.eiNextItem.getDownLink(0);
/* 7637 */                   logMessage("****1) Downlinked from CMPTRELSOF" + this.eiNextItem1.getKey());
/* 7638 */                   this.strCondition4 = getSOFMktName(this.eiNextItem1);
/* 7639 */                   logMessage("1)  Child Marketing Msg for CMPNTRELSOF" + this.eiNextItem.getKey() + " Downlinked from " + this.eiComponent
/* 7640 */                       .getKey() + " is " + this.strCondition4);
/*      */                   
/* 7642 */                   if ((i > 1 || this.vReturnEntities1.size() > 1) && this.strCondition4.trim().length() > 0) {
/* 7643 */                     prettyPrint(":li." + this.strCondition4, 69);
/*      */ 
/*      */                   
/*      */                   }
/* 7647 */                   else if (this.strCondition4.trim().length() > 0) {
/* 7648 */                     println(":p.");
/* 7649 */                     prettyPrint(this.strCondition4, 69);
/*      */                   }
/*      */                 
/*      */                 }
/*      */               
/*      */               } else {
/*      */                 
/* 7656 */                 logMessage("1)  No Child Marketing Msg for CMPNTRELSOF" + this.eiComponent.getKey());
/*      */               } 
/* 7658 */               if (i > 1 || bool1) {
/* 7659 */                 println(":eul.");
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/* 7664 */             if (paramBoolean1) {
/* 7665 */               if (paramBoolean2) {
/* 7666 */                 this.strEntityTypes = new String[] { "CMPNTRELCMPNT" };
/*      */                 
/* 7668 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiComponent, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7670 */                 logMessage("CMPNTRELCMPNT BENEFIT" + this.strCondition4);
/* 7671 */                 if (this.strCondition4.trim().length() > 0) {
/* 7672 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/*      */                 
/* 7675 */                 this.strEntityTypes = new String[] { "CMPNTRELFEATURE" };
/*      */                 
/* 7677 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiComponent, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7679 */                 logMessage("CMPNTRELFEATURE BENEFIT" + this.strCondition4);
/* 7680 */                 if (this.strCondition4.trim().length() > 0) {
/* 7681 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/*      */                 
/* 7684 */                 this.strEntityTypes = new String[] { "CMPNTRELSOF" };
/*      */                 
/* 7686 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiComponent, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7688 */                 logMessage("CMPNTRELSOF BENEFIT" + this.strCondition4);
/* 7689 */                 if (this.strCondition4.trim().length() > 0) {
/* 7690 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/*      */               } else {
/*      */                 
/* 7694 */                 this.strEntityTypes = new String[] { "CMPNTRELCMPNT" };
/*      */                 
/* 7696 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiComponent, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7698 */                 if (this.strCondition4.trim().length() > 0) {
/* 7699 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/*      */               }
/*      */             
/*      */             }
/*      */           } 
/*      */         } else {
/*      */           
/* 7707 */           logMessage("No related rows found for " + this.eiComponent.getKey());
/*      */         } 
/* 7709 */         println("");
/*      */       } 
/*      */ 
/*      */       
/* 7713 */       if (str1.equals("FEATURE")) {
/* 7714 */         this.eiFeature = this.vAllSortedOfferings.elementAt(this.i);
/* 7715 */         logMessage("After feature " + this.eiFeature.getKey());
/* 7716 */         this.strEntityTypes = new String[] { "FEATURERELFEATURE" };
/*      */         
/* 7718 */         if (paramBoolean2) {
/* 7719 */           this.strCondition4 = getAttributeValue(this.eiFeature, str2, "");
/* 7720 */           if (this.strCondition4.trim().length() == 0) {
/* 7721 */             this.vReturnEntities1 = searchEntityItemLink(this.eiFeature, arrayOfString1, arrayOfString2, true, true, "FEATURERELSOF");
/* 7722 */             this.bConditionOK = (this.vReturnEntities1.size() > 0);
/* 7723 */             this.vReturnEntities1 = searchEntityItemLink(this.eiFeature, arrayOfString1, arrayOfString2, true, true, "FEATURERELCMPNT");
/* 7724 */             this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7725 */             this.vReturnEntities1 = searchEntityItemLink(this.eiFeature, arrayOfString1, arrayOfString2, true, true, "FEATURERELFEATURE");
/* 7726 */             this.bConditionOK = !this.bConditionOK ? ((this.vReturnEntities1.size() > 0)) : this.bConditionOK;
/* 7727 */             if (this.bConditionOK) {
/* 7728 */               this.strCondition4 = ".";
/*      */             }
/*      */           } 
/*      */         } else {
/*      */           
/* 7733 */           this.strCondition4 = getDownlinkedEntityAttrValue(this.eiFeature, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2);
/*      */         } 
/* 7735 */         if (this.strCondition4.trim().length() > 0) {
/*      */ 
/*      */           
/* 7738 */           this.vReturnEntities1 = searchEntityItemLink(this.eiFeature, (String[])null, (String[])null, true, true, "FEATUREAVAIL");
/*      */           
/* 7740 */           this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "AVAIL");
/* 7741 */           if (this.vReturnEntities2.size() > 0) {
/* 7742 */             this.strCondition1 = getAllGeoTags(this.vReturnEntities2);
/* 7743 */             if (!this.strCondition1.equals(this.strCondition2)) {
/* 7744 */               if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 7745 */                 this.strCondition2.length() > 0) {
/* 7746 */                 println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */               }
/*      */               
/* 7749 */               if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/* 7750 */                 println("");
/* 7751 */                 println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/*      */               } 
/* 7753 */               this.strCondition2 = this.strCondition1;
/*      */             } 
/* 7755 */             str3 = getfeatureToSofMktMsg(this.eiFeature);
/* 7756 */             logMessage("2) Marketing Msg :" + str3);
/* 7757 */             if (i > 1 || bool3) {
/* 7758 */               prettyPrint(str3, 69);
/* 7759 */               println(":p.");
/* 7760 */               println(":ul c.");
/*      */             } else {
/*      */               
/* 7763 */               println(":p.");
/* 7764 */               prettyPrint(str3, 69);
/*      */             } 
/* 7766 */             if (paramBoolean2 && !str2.equals("")) {
/* 7767 */               this.strCondition4 = transformXML(getAttributeValue(this.eiFeature, str2, " "));
/* 7768 */               if (this.strCondition4.trim().length() > 0)
/*      */               {
/* 7770 */                 prettyPrint("       " + this.strCondition4, 69);
/*      */               }
/*      */             } 
/*      */             
/* 7774 */             if (paramBoolean2) {
/* 7775 */               this.strEntityTypes = new String[] { "FEATURERELFEATURE" };
/*      */               
/* 7777 */               this.vReturnEntities1 = searchEntityItemLink(this.eiFeature, arrayOfString1, arrayOfString2, true, true, "FEATURERELFEATURE");
/* 7778 */               if (this.vReturnEntities1.size() > 0) {
/* 7779 */                 for (this.j = 0; this.j < this.vReturnEntities1.size(); this.j++) {
/* 7780 */                   this.eiNextItem = this.vReturnEntities1.elementAt(this.j);
/* 7781 */                   this.eiNextItem1 = (EntityItem)this.eiNextItem.getDownLink(0);
/* 7782 */                   this.strCondition4 = getfeatureToSofMktMsg(this.eiNextItem1);
/* 7783 */                   logMessage("2)  Child Marketing Msg for FEATURERELFEATURE" + this.eiNextItem.getKey() + " Downlinked from " + this.eiFeature
/* 7784 */                       .getKey() + " is " + this.strCondition4);
/* 7785 */                   if ((i > 1 || this.vReturnEntities1.size() > 1) && this.strCondition4.trim().length() > 0) {
/* 7786 */                     prettyPrint(":li." + this.strCondition4, 69);
/*      */ 
/*      */                   
/*      */                   }
/* 7790 */                   else if (this.strCondition4.trim().length() > 0) {
/* 7791 */                     println(":p.");
/* 7792 */                     prettyPrint(this.strCondition4, 69);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } else {
/*      */                 
/* 7798 */                 logMessage("2)  No Child Marketing Msg for FEATURERELFEATURE" + this.eiFeature.getKey());
/*      */               } 
/*      */               
/* 7801 */               this.strEntityTypes = new String[] { "FEATURERELCMPNT" };
/*      */               
/* 7803 */               this.vReturnEntities1 = searchEntityItemLink(this.eiFeature, arrayOfString1, arrayOfString2, true, true, "FEATURERELCMPNT");
/* 7804 */               if (this.vReturnEntities1.size() > 0) {
/* 7805 */                 for (this.j = 0; this.j < this.vReturnEntities1.size(); this.j++) {
/* 7806 */                   this.eiNextItem = this.vReturnEntities1.elementAt(this.j);
/* 7807 */                   this.eiNextItem1 = (EntityItem)this.eiNextItem.getDownLink(0);
/* 7808 */                   this.strCondition4 = getCmptToSofMktMsg(this.eiNextItem1);
/* 7809 */                   logMessage("2)  Child Marketing Msg for FEATURERELCMPNT" + this.eiNextItem.getKey() + " Downlinked from " + this.eiFeature
/* 7810 */                       .getKey() + " is " + this.strCondition4);
/* 7811 */                   if ((i > 1 || this.vReturnEntities1.size() > 1) && this.strCondition4.trim().length() > 0) {
/* 7812 */                     prettyPrint(":li." + this.strCondition4, 69);
/*      */ 
/*      */                   
/*      */                   }
/* 7816 */                   else if (this.strCondition4.trim().length() > 0) {
/* 7817 */                     println("");
/* 7818 */                     prettyPrint(this.strCondition4, 69);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } else {
/*      */                 
/* 7824 */                 logMessage("2)  No Child Marketing Msg for FEATURERELCMPNT" + this.eiFeature.getKey());
/*      */               } 
/*      */               
/* 7827 */               this.strEntityTypes = new String[] { "FEATURERELSOF" };
/*      */               
/* 7829 */               this.vReturnEntities1 = searchEntityItemLink(this.eiFeature, arrayOfString1, arrayOfString2, true, true, "FEATURERELSOF");
/* 7830 */               if (this.vReturnEntities1.size() > 0) {
/* 7831 */                 for (this.j = 0; this.j < this.vReturnEntities1.size(); this.j++) {
/* 7832 */                   this.eiNextItem = this.vReturnEntities1.elementAt(this.j);
/* 7833 */                   this.eiNextItem1 = (EntityItem)this.eiNextItem.getDownLink(0);
/* 7834 */                   this.strCondition4 = getSOFMktName(this.eiNextItem1);
/* 7835 */                   logMessage("2)  Child Marketing Msg for FEATURERELSOF" + this.eiNextItem.getKey() + " Downlinked from " + this.eiFeature
/* 7836 */                       .getKey() + " is " + this.strCondition4);
/* 7837 */                   if ((i > 1 || this.vReturnEntities1.size() > 1) && this.strCondition4.trim().length() > 0) {
/* 7838 */                     prettyPrint(":li." + this.strCondition4, 69);
/*      */ 
/*      */                   
/*      */                   }
/* 7842 */                   else if (this.strCondition4.trim().length() > 0) {
/* 7843 */                     println(":p.");
/* 7844 */                     prettyPrint(this.strCondition4, 69);
/*      */                   }
/*      */                 
/*      */                 } 
/*      */               } else {
/*      */                 
/* 7850 */                 logMessage("2)  No Child Marketing Msg for FEATURERELSOF" + this.eiFeature.getKey());
/*      */               } 
/* 7852 */               if (i > 1 || bool3) {
/* 7853 */                 println(":eul.");
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/* 7858 */             if (paramBoolean1) {
/* 7859 */               this.strEntityTypes = new String[] { "FEATURERELFEATURE" };
/*      */               
/* 7861 */               if (paramBoolean2) {
/* 7862 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiFeature, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7864 */                 if (this.strCondition4.trim().length() > 0) {
/* 7865 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/*      */                 
/* 7868 */                 this.strEntityTypes = new String[] { "FEATURERELCMPNT" };
/*      */                 
/* 7870 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiFeature, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7872 */                 if (this.strCondition4.trim().length() > 0) {
/* 7873 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/*      */                 
/* 7876 */                 this.strEntityTypes = new String[] { "FEATURERELSOF" };
/*      */                 
/* 7878 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiFeature, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7880 */                 if (this.strCondition4.trim().length() > 0) {
/* 7881 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/*      */               } else {
/*      */                 
/* 7885 */                 this.strCondition4 = transformXML(getDownlinkedEntityAttrValue(this.eiFeature, this.strEntityTypes, "BENEFIT", arrayOfString1, arrayOfString2));
/*      */                 
/* 7887 */                 if (this.strCondition4.trim().length() > 0) {
/* 7888 */                   prettyPrint("Benefit:" + this.strCondition4, 69);
/*      */                 }
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           } 
/*      */         } else {
/*      */           
/* 7897 */           logMessage("No related rows found for " + this.eiFeature.getKey());
/*      */         } 
/* 7899 */         println("");
/*      */       } 
/*      */     } 
/*      */     
/* 7903 */     if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 7904 */       this.strCondition2.length() > 0) {
/* 7905 */       println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
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
/*      */   private void printQ158(boolean paramBoolean) {
/* 7922 */     Vector<EntityItem> vector1 = new Vector();
/* 7923 */     Vector<EntityItem> vector2 = new Vector();
/* 7924 */     Vector<EntityItem> vector3 = new Vector();
/* 7925 */     this.bConditionOK = false;
/* 7926 */     this.vReturnEntities1 = searchEntityVectorLink(this.vSofFrmSofAvail, (String[])null, (String[])null, true, true, "SOFCATINCL");
/* 7927 */     this.strFilterAttr = new String[] { "CATALOGNAME" };
/*      */     
/* 7929 */     this.strFilterValue = new String[] { "321" };
/*      */     
/* 7931 */     vector1 = searchEntityVectorLink(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, true, true, "CATINCL");
/*      */     
/* 7933 */     this.vReturnEntities1 = searchEntityVectorLink(this.vCmpntFrmCmpntAvail, (String[])null, (String[])null, true, true, "CMPNTCATINCL");
/* 7934 */     vector2 = searchEntityVectorLink(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, true, true, "CATINCL");
/*      */     
/* 7936 */     this.vReturnEntities1 = searchEntityVectorLink(this.vFeatureFrmFeatureAvail, (String[])null, (String[])null, true, true, "FEATURECATINCL");
/* 7937 */     vector3 = searchEntityVectorLink(this.vReturnEntities1, this.strFilterAttr, this.strFilterValue, true, true, "CATINCL");
/*      */     
/* 7939 */     this.strCondition4 = "";
/* 7940 */     this.strCondition2 = "";
/* 7941 */     boolean bool1 = (vector1.size() == 0) ? true : false;
/* 7942 */     boolean bool2 = (vector2.size() == 0) ? true : false;
/* 7943 */     boolean bool3 = (vector3.size() == 0) ? true : false;
/* 7944 */     boolean bool4 = (bool1 && bool2 && bool3) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7950 */     int i = vector1.size();
/* 7951 */     i += vector2.size();
/* 7952 */     i += vector3.size();
/*      */     
/* 7954 */     logMessage("vSofCats" + vector1.size());
/* 7955 */     logMessage("vCmptCats" + vector2.size());
/* 7956 */     logMessage("vFeatCats" + vector3.size());
/*      */     
/* 7958 */     this.i = 0;
/* 7959 */     this.strFilterAttr = new String[] { "AVAILTYPE" };
/*      */     
/* 7961 */     this.strFilterValue = new String[] { "146" };
/*      */     
/* 7963 */     this.strCondition4 = "";
/* 7964 */     while (!bool4) {
/* 7965 */       logMessage("  printQ158:I is" + this.i);
/* 7966 */       if (!bool1) {
/* 7967 */         this.eiCatIncl = vector1.elementAt(this.i);
/* 7968 */         this.strEntityTypes = new String[] { "SOFCATINCL", "SOF" };
/*      */         
/* 7970 */         this.eiSof = getUplinkedEntityItem(this.eiCatIncl, this.strEntityTypes);
/* 7971 */         logMessage("after SOF" + this.eiSof.getKey());
/*      */         
/* 7973 */         this.vReturnEntities1 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFAVAIL");
/*      */         
/* 7975 */         this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "AVAIL");
/* 7976 */         this.strCondition1 = getAllGeoTags(this.vReturnEntities2);
/*      */         
/* 7978 */         if (!this.strCondition1.equals(this.strCondition2)) {
/* 7979 */           if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 7980 */             this.strCondition2.length() > 0) {
/* 7981 */             println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */           }
/*      */           
/* 7984 */           if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/* 7985 */             println("");
/* 7986 */             println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/*      */           } 
/* 7988 */           this.strCondition2 = this.strCondition1;
/*      */         } 
/* 7990 */         if (paramBoolean) {
/* 7991 */           this.strCondition4 = getSOFMktName(this.eiSof);
/* 7992 */           logMessage("printQ158:" + this.strCondition4);
/* 7993 */           prettyPrint(this.strCondition4, 69);
/*      */         } 
/* 7995 */         this.strCondition4 = transformXML(getAttributeValue(this.eiCatIncl, "CATALOGTAXONOMY", " "));
/* 7996 */         prettyPrint(":p.:hp2." + this.strCondition4 + ":ehp2.", 69);
/* 7997 */         println("");
/*      */       } 
/*      */       
/* 8000 */       if (!bool2) {
/*      */         
/* 8002 */         this.eiCatIncl = vector2.elementAt(this.i);
/* 8003 */         this.strEntityTypes = new String[] { "CMPNTCATINCL", "CMPNT" };
/*      */         
/* 8005 */         this.eiComponent = getUplinkedEntityItem(this.eiCatIncl, this.strEntityTypes);
/* 8006 */         logMessage("after component" + this.eiComponent.getKey());
/*      */ 
/*      */         
/* 8009 */         this.vReturnEntities1 = searchEntityItemLink(this.eiComponent, (String[])null, (String[])null, true, true, "CMPNTAVAIL");
/*      */         
/* 8011 */         this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "AVAIL");
/* 8012 */         this.strCondition1 = getAllGeoTags(this.vReturnEntities2);
/* 8013 */         if (!this.strCondition1.equals(this.strCondition2)) {
/* 8014 */           if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 8015 */             this.strCondition2.length() > 0) {
/* 8016 */             println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */           }
/*      */           
/* 8019 */           if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/* 8020 */             println("");
/* 8021 */             println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/*      */           } 
/* 8023 */           this.strCondition2 = this.strCondition1;
/*      */         } 
/* 8025 */         if (paramBoolean) {
/* 8026 */           this.strCondition4 = getCmptToSofMktMsg(this.eiComponent);
/* 8027 */           logMessage("printQ158:1:" + this.strCondition4);
/* 8028 */           prettyPrint(this.strCondition4, 69);
/*      */         } 
/* 8030 */         this.strCondition4 = transformXML(getAttributeValue(this.eiCatIncl, "CATALOGTAXONOMY", " "));
/* 8031 */         prettyPrint(":p.:hp2." + this.strCondition4 + ":ehp2.", 69);
/* 8032 */         println("");
/*      */       } 
/*      */       
/* 8035 */       if (!bool3) {
/* 8036 */         this.eiCatIncl = vector3.elementAt(this.i);
/* 8037 */         this.strEntityTypes = new String[] { "FEATURECATINCL", "FEATURE" };
/*      */         
/* 8039 */         this.eiFeature = getUplinkedEntityItem(this.eiCatIncl, this.strEntityTypes);
/* 8040 */         logMessage("After feature " + this.eiFeature.getKey());
/*      */ 
/*      */         
/* 8043 */         this.vReturnEntities1 = searchEntityItemLink(this.eiFeature, (String[])null, (String[])null, true, true, "FEATUREAVAIL");
/*      */         
/* 8045 */         this.vReturnEntities2 = searchEntityVectorLink(this.vReturnEntities1, (String[])null, (String[])null, true, true, "AVAIL");
/* 8046 */         this.strCondition1 = getAllGeoTags(this.vReturnEntities2);
/* 8047 */         if (!this.strCondition1.equals(this.strCondition2)) {
/* 8048 */           if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 8049 */             this.strCondition2.length() > 0) {
/* 8050 */             println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */           }
/*      */           
/* 8053 */           if (!this.strCondition1.equals("US, AP, CAN, EMEA, LA")) {
/* 8054 */             println("");
/* 8055 */             println(":p.:hp2." + this.strCondition1 + "--->:ehp2.");
/*      */           } 
/* 8057 */           this.strCondition2 = this.strCondition1;
/*      */         } 
/* 8059 */         if (paramBoolean) {
/* 8060 */           this.strCondition4 = getfeatureToSofMktMsg(this.eiFeature);
/* 8061 */           logMessage("printQ158:2:" + this.strCondition4);
/* 8062 */           prettyPrint(this.strCondition4, 69);
/*      */         } 
/* 8064 */         this.strCondition4 = transformXML(getAttributeValue(this.eiCatIncl, "CATALOGTAXONOMY", " "));
/* 8065 */         prettyPrint(":p.:hp2." + this.strCondition4 + ":ehp2.", 69);
/* 8066 */         println("");
/*      */       } 
/*      */       
/* 8069 */       if (bool1 && bool2 && bool3) {
/* 8070 */         bool4 = true;
/*      */       }
/*      */       
/* 8073 */       this.i++;
/* 8074 */       bool1 = (vector1.size() <= this.i) ? true : false;
/* 8075 */       bool2 = (vector2.size() <= this.i) ? true : false;
/* 8076 */       bool3 = (vector3.size() <= this.i) ? true : false;
/*      */     } 
/*      */     
/* 8079 */     if (!this.strCondition2.equals("US, AP, CAN, EMEA, LA") && 
/* 8080 */       this.strCondition2.length() > 0) {
/* 8081 */       println(".br;:hp2.<---" + this.strCondition2 + ":ehp2.");
/*      */     }
/*      */ 
/*      */     
/* 8085 */     println("");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printA153() {
/* 8096 */     String str = "OFIDNUMBER";
/*      */     
/* 8098 */     logMessage("printA153: entered vAllSortedOfferings " + this.vAllSortedOfferings.size());
/* 8099 */     displayContents(this.vAllSortedOfferings);
/*      */     
/*      */     byte b;
/* 8102 */     for (b = 0; b < this.vAllSortedOfferings.size(); b++) {
/*      */ 
/*      */ 
/*      */       
/* 8106 */       this.eiNextItem = this.vAllSortedOfferings.elementAt(b);
/* 8107 */       logMessage("printA153: loop [" + b + "] " + this.eiNextItem.getKey());
/*      */       
/* 8109 */       if (this.eiNextItem.getEntityType().equals("SOF")) {
/* 8110 */         this.eiSof = this.eiNextItem;
/* 8111 */         logMessage("printA153: in SOF " + this.eiSof.getKey());
/*      */ 
/*      */         
/* 8114 */         String str1 = getSOFMktName(this.eiSof);
/* 8115 */         if (str1.trim().length() > 0) {
/* 8116 */           logMessage("printA153: " + this.eiSof.getKey() + " Adding Marketing Msg :" + str1);
/* 8117 */           this.vPrintDetails.add(str1);
/*      */           
/* 8119 */           String str2 = getAttributeValue(this.eiSof, str, " ");
/* 8120 */           logMessage("printA153: Adding " + str + " :" + str2);
/* 8121 */           this.vPrintDetails.add(str2);
/*      */ 
/*      */           
/* 8124 */           String str3 = " ";
/* 8125 */           this.vReturnEntities4 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFPRA");
/* 8126 */           logMessage("printA153: Number of SOFPRA links found =" + this.vReturnEntities4.size());
/*      */           
/* 8128 */           if (this.vReturnEntities4.size() > 0) {
/*      */             
/* 8130 */             this.eiNextItem2 = this.vReturnEntities4.elementAt(0);
/* 8131 */             this.eiNextItem3 = (EntityItem)this.eiNextItem2.getDownLink(0);
/* 8132 */             str3 = getAttributeValue(this.eiNextItem3, "PROJNUMBER", "No Value" + this.eiNextItem3.getKey());
/*      */           } 
/*      */           
/* 8135 */           logMessage("printA153: Adding PROJNUMBER:" + str3);
/* 8136 */           this.vPrintDetails.add(str3);
/*      */         }
/*      */         else {
/*      */           
/* 8140 */           logMessage("printA153: No Marketing Msg found. Skipping " + this.eiSof.getKey());
/*      */         } 
/*      */       } 
/*      */       
/* 8144 */       if (this.eiNextItem.getEntityType().equals("CMPNT")) {
/* 8145 */         this.eiComponent = this.vAllSortedOfferings.elementAt(b);
/* 8146 */         logMessage("printA153 After component" + this.eiComponent.getKey());
/* 8147 */         this.eiSof = getUplinkedEntityItem(this.eiComponent, this.strCmptToSof);
/* 8148 */         if (this.eiSof != null) {
/*      */           
/* 8150 */           String str1 = getSOFMktName(this.eiSof);
/* 8151 */           if (str1.trim().length() > 0) {
/*      */             
/* 8153 */             logMessage("printA153 " + this.eiSof.getKey() + " for " + this.eiComponent.getKey() + " Adding Marketing Msg :" + str1);
/*      */             
/* 8155 */             this.vPrintDetails.add(str1);
/*      */             
/* 8157 */             String str2 = getAttributeValue(this.eiSof, str, " ");
/* 8158 */             logMessage("printA153: Adding " + str + " :" + str2);
/* 8159 */             this.vPrintDetails.add(str2);
/*      */ 
/*      */             
/* 8162 */             this.vReturnEntities4 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFPRA");
/* 8163 */             logMessage("printA153 Number of SOFPRA links found =" + this.vReturnEntities4.size());
/*      */             
/* 8165 */             String str3 = " ";
/*      */             
/* 8167 */             if (this.vReturnEntities4.size() > 0) {
/*      */               
/* 8169 */               this.eiNextItem2 = this.vReturnEntities4.elementAt(0);
/* 8170 */               this.eiNextItem3 = (EntityItem)this.eiNextItem2.getDownLink(0);
/* 8171 */               str3 = getAttributeValue(this.eiNextItem3, "PROJNUMBER", "No Value" + this.eiNextItem3.getKey());
/*      */             } 
/*      */             
/* 8174 */             logMessage("printA153 adding PROJNUMBER :" + str3);
/* 8175 */             this.vPrintDetails.add(str3);
/*      */           }
/*      */           else {
/*      */             
/* 8179 */             logMessage("printA153 No Marketing msg found on " + this.eiSof.getKey() + " for " + this.eiComponent.getKey());
/*      */           } 
/*      */         } else {
/*      */           
/* 8183 */           logMessage("printA153 No SOF for " + this.eiComponent.getKey());
/*      */         } 
/*      */       } 
/*      */       
/* 8187 */       if (this.eiNextItem.getEntityType().equals("FEATURE")) {
/* 8188 */         this.strFeatureToSof = new String[] { "CMPNTFEATURE", "CMPNT", "SOFCMPNT", "SOF" };
/*      */         
/* 8190 */         this.eiFeature = this.vAllSortedOfferings.elementAt(b);
/* 8191 */         logMessage("printA153 After feature" + this.eiFeature.getKey());
/* 8192 */         this.eiSof = getUplinkedEntityItem(this.eiFeature, this.strFeatureToSof);
/* 8193 */         if (this.eiSof != null) {
/*      */           
/* 8195 */           String str1 = getSOFMktName(this.eiSof);
/* 8196 */           if (str1.trim().length() > 0) {
/*      */             
/* 8198 */             logMessage("printA153 " + this.eiSof.getKey() + " for " + this.eiFeature.getKey() + " Adding Marketing Msg :" + str1);
/*      */             
/* 8200 */             this.vPrintDetails.add(str1);
/*      */             
/* 8202 */             String str2 = getAttributeValue(this.eiSof, str, " ");
/* 8203 */             logMessage("printA153: Adding " + str + " :" + str2);
/* 8204 */             this.vPrintDetails.add(str2);
/*      */ 
/*      */             
/* 8207 */             this.vReturnEntities4 = searchEntityItemLink(this.eiSof, (String[])null, (String[])null, true, true, "SOFPRA");
/* 8208 */             logMessage("printA153 Number of SOFPRA links found =" + this.vReturnEntities4.size());
/*      */             
/* 8210 */             String str3 = " ";
/*      */             
/* 8212 */             if (this.vReturnEntities4.size() > 0) {
/*      */               
/* 8214 */               this.eiNextItem2 = this.vReturnEntities4.elementAt(0);
/* 8215 */               this.eiNextItem3 = (EntityItem)this.eiNextItem2.getDownLink(0);
/* 8216 */               str3 = getAttributeValue(this.eiNextItem3, "PROJNUMBER", "No Value" + this.eiNextItem3.getKey());
/*      */             } 
/*      */             
/* 8219 */             logMessage("printA153 adding PROJNUMBER :" + str3);
/* 8220 */             this.vPrintDetails.add(str3);
/*      */           }
/*      */           else {
/*      */             
/* 8224 */             logMessage("printA153 No Marketing msg found on " + this.eiSof.getKey() + " for " + this.eiFeature.getKey());
/*      */           } 
/*      */         } else {
/*      */           
/* 8228 */           logMessage("printA153 No SOF found for " + this.eiFeature.getKey());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 8233 */     if (this.vPrintDetails.size() > 0) {
/* 8234 */       for (b = 0; b < this.vPrintDetails.size(); b++) {
/* 8235 */         logMessage("printA153 Printvector :" + b + ":" + (String)this.vPrintDetails.elementAt(b));
/*      */       }
/*      */       
/* 8238 */       println(":xmp.");
/* 8239 */       println(".kp off");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 8251 */       this.strHeader = new String[] { "Name", "ID", "Number" };
/*      */       
/* 8253 */       this.iColWidths = new int[] { 47, 16, 10 };
/*      */       
/* 8255 */       this.rfaReport.setSortColumns(new int[] { 1 });
/* 8256 */       this.rfaReport.setSortable(true);
/* 8257 */       println("                                                                 Autobahn");
/* 8258 */       println("Service/Offering                                Service/Offering Project");
/* 8259 */       printReport(true, this.strHeader, this.iColWidths, this.vPrintDetails);
/* 8260 */       println(":exmp.");
/* 8261 */       resetPrintvars();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getfeatureToSofMktMsg(EntityItem paramEntityItem) {
/* 8651 */     String str1 = "";
/* 8652 */     String str2 = "";
/* 8653 */     if (paramEntityItem == null) {
/* 8654 */       return "";
/*      */     }
/*      */     
/* 8657 */     str1 = getAttributeValue(paramEntityItem, "MKTGNAME", " ");
/* 8658 */     logMessage("getfeatureToSofMktMsg:Feature :" + str1);
/*      */     
/* 8660 */     this.eiNextItem1 = getUplinkedEntityItem(paramEntityItem, this.strFeatureToCmpt);
/* 8661 */     str2 = getAttributeValue(this.eiNextItem1, "MKTGNAME", " ");
/* 8662 */     logMessage("getfeatureToSofMktMsg:Component :" + str2);
/* 8663 */     str1 = str2 + " " + str1;
/*      */     
/* 8665 */     str2 = "";
/* 8666 */     if (this.bIsAnnITS && this.eiNextItem1 != null) {
/* 8667 */       str2 = getAttributeValue(this.eiNextItem1, "ITSCMPNTCATNAME", "");
/* 8668 */       logMessage("getfeatureToSofMktMsg:ITSCMPNTCATNAME is: " + str2);
/*      */     } 
/* 8670 */     if (str2.trim().length() == 0 && this.eiNextItem1 != null) {
/* 8671 */       str2 = getUplinkedEntityAttrValue(this.eiNextItem1, this.strCmptToSof, "MKTGNAME");
/* 8672 */       logMessage("getfeatureToSofMktMsg:ITSCMPNTCATNAME is empty...getting SOF MTGNAME: " + str2);
/*      */     } 
/* 8674 */     logMessage("getfeatureToSofMktMsg:Sof :" + str2);
/* 8675 */     str1 = str2 + " " + str1;
/* 8676 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getCmptToSofMktMsg(EntityItem paramEntityItem) {
/* 8686 */     String str1 = "";
/* 8687 */     String str2 = "";
/* 8688 */     if (paramEntityItem == null) {
/* 8689 */       return "";
/*      */     }
/* 8691 */     str1 = getAttributeValue(paramEntityItem, "MKTGNAME", " ");
/* 8692 */     logMessage("getCmptToSofMktMsg:Component :" + str1);
/*      */     
/* 8694 */     if (this.bIsAnnITS) {
/* 8695 */       str2 = getAttributeValue(paramEntityItem, "ITSCMPNTCATNAME", "");
/* 8696 */       logMessage("getCmptToSofMktMsg:ITSCMPNTCATNAME is: " + str2);
/*      */     } 
/* 8698 */     if (str2.trim().length() == 0) {
/* 8699 */       str2 = getUplinkedEntityAttrValue(paramEntityItem, this.strCmptToSof, "MKTGNAME");
/* 8700 */       logMessage("getCmptToSofMktMsg:ITSCMPNTCATNAME is empty...getting SOF MTGNAME: " + str2);
/*      */     } 
/* 8702 */     logMessage("getCmptToSofMktMsg:Sof :" + str2);
/* 8703 */     str1 = str2 + " " + str1;
/* 8704 */     return str1;
/*      */   }
/*      */   
/*      */   private String getSOFMktName(EntityItem paramEntityItem) {
/* 8708 */     return getSOFMktName(paramEntityItem, true);
/*      */   }
/*      */   
/*      */   private String getSOFMktName(EntityItem paramEntityItem, boolean paramBoolean) {
/* 8712 */     if (paramEntityItem == null) {
/* 8713 */       return "";
/*      */     }
/* 8715 */     String str = "";
/* 8716 */     if (this.bIsAnnITS && paramBoolean) {
/* 8717 */       logMessage("getSOFMktName:Getting downlinked cmpt from " + paramEntityItem.getKey());
/* 8718 */       str = getDownlinkedEntityAttrValue(paramEntityItem, this.strSofToCmpt, "ITSCMPNTCATNAME");
/* 8719 */       logMessage("getSOFMktName:ITSCMPNTCATNAME is: " + str);
/*      */     } 
/* 8721 */     if (str.trim().length() == 0 || !paramBoolean) {
/* 8722 */       str = getAttributeValue(paramEntityItem, "MKTGNAME", " ");
/* 8723 */       logMessage("getSOFMktName:ITSCMPNTCATNAME is empty...getting SOF MTGNAME: " + str);
/*      */     } 
/* 8725 */     return str;
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
/*      */   private String getQ800SOFMktName(EntityItem paramEntityItem1, EntityItem paramEntityItem2) {
/* 8750 */     if (paramEntityItem1 == null) {
/* 8751 */       return "";
/*      */     }
/* 8753 */     String str = "";
/* 8754 */     if (this.bIsAnnITS) {
/* 8755 */       str = getAttributeShortFlagDesc(paramEntityItem1, "ITSCMPNTCATNAME");
/* 8756 */       logMessage("getQ800SOFMktName ShortaName:ITSCMPNTCATNAME is: " + str);
/* 8757 */       if (str == null) {
/* 8758 */         str = "";
/*      */       }
/*      */     } 
/* 8761 */     if (str.trim().length() == 0) {
/* 8762 */       str = getAttributeValue(paramEntityItem2, "MKTGNAME", " ");
/* 8763 */       logMessage("getQ800SOFMktName:ITSCMPNTCATNAME is empty...getting SOF MTGNAME: " + str);
/*      */     } 
/* 8765 */     return str;
/*      */   }
/*      */   
/*      */   private String getRFADateFormat(String paramString) {
/* 8769 */     String str = "";
/* 8770 */     if (paramString.trim().length() == 0) {
/* 8771 */       return " ";
/*      */     }
/* 8773 */     SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
/* 8774 */     SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MMMMMMMMM dd, yyyy");
/*      */     try {
/* 8776 */       Date date = simpleDateFormat1.parse(paramString);
/* 8777 */       str = simpleDateFormat2.format(date);
/*      */     
/*      */     }
/* 8780 */     catch (Exception exception) {
/* 8781 */       System.out.println(exception.getMessage());
/*      */     } 
/*      */     
/* 8784 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector RFAsort(Vector paramVector) {
/* 8789 */     EntityItem[] arrayOfEntityItem = getEntityArray(paramVector);
/* 8790 */     RFAsort(arrayOfEntityItem);
/* 8791 */     return getEntityVector(arrayOfEntityItem);
/*      */   }
/*      */   
/*      */   private void RFAsort(EntityItem[] paramArrayOfEntityItem) {
/* 8795 */     EntityItem entityItem1 = null;
/* 8796 */     EntityItem entityItem2 = null;
/* 8797 */     EntityItem entityItem3 = null;
/* 8798 */     String str1 = null;
/* 8799 */     String str2 = null;
/* 8800 */     String str3 = null;
/* 8801 */     String str4 = null;
/* 8802 */     if (paramArrayOfEntityItem == null) {
/*      */       return;
/*      */     }
/*      */     
/* 8806 */     for (int i = paramArrayOfEntityItem.length; --i >= 0; ) {
/* 8807 */       boolean bool = false;
/*      */       
/* 8809 */       for (byte b = 0; b < i; b++) {
/* 8810 */         entityItem1 = paramArrayOfEntityItem[b];
/* 8811 */         entityItem2 = paramArrayOfEntityItem[b + 1];
/* 8812 */         str3 = entityItem1.getEntityType();
/* 8813 */         str4 = entityItem1.getEntityType();
/*      */         
/* 8815 */         if (str3.equals("SOF")) {
/* 8816 */           str1 = getSOFMktName(entityItem1);
/*      */         }
/* 8818 */         else if (str3.equals("CMPNT")) {
/* 8819 */           str1 = getCmptToSofMktMsg(entityItem1);
/*      */         }
/* 8821 */         else if (str3.equals("FEATURE")) {
/* 8822 */           str1 = getfeatureToSofMktMsg(entityItem1);
/*      */         } 
/*      */         
/* 8825 */         if (str4.equals("SOF")) {
/* 8826 */           str2 = getSOFMktName(entityItem2);
/*      */         }
/* 8828 */         else if (str4.equals("CMPNT")) {
/* 8829 */           str2 = getCmptToSofMktMsg(entityItem2);
/*      */         }
/* 8831 */         else if (str4.equals("FEATURE")) {
/* 8832 */           str2 = getfeatureToSofMktMsg(entityItem2);
/*      */         } 
/*      */         
/* 8835 */         logMessage("RFASort:Comparing:" + entityItem1.getKey() + ":" + str1 + ":with:" + entityItem2.getKey() + ":" + str2);
/*      */         
/* 8837 */         if (str1.compareTo(str2) > 0) {
/* 8838 */           entityItem3 = paramArrayOfEntityItem[b];
/* 8839 */           paramArrayOfEntityItem[b] = paramArrayOfEntityItem[b + 1];
/* 8840 */           paramArrayOfEntityItem[b + 1] = entityItem3;
/* 8841 */           bool = true;
/*      */         } 
/*      */       } 
/*      */       
/* 8845 */       if (!bool)
/*      */         return; 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\rfa\RFA_IGSSVS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */