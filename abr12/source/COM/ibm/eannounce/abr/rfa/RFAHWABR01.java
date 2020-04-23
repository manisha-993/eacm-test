//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//$Log: RFAHWABR01.java,v $
//Revision 1.75  2008/03/19 19:38:57  wendy
//Clean up RSA warnings
//
//Revision 1.74  2006/03/03 19:23:59  bala
//remove reference to Constants.CSS
//
//Revision 1.73  2005/02/08 18:29:12  joan
//changes for Jtest
//
//Revision 1.72  2004/03/18 19:13:01  bala
//change for Q57 priced features - existing features to be
//filtered out of new features
//
//Revision 1.71  2003/12/31 22:35:02  bala
//End of year commit
//
//Revision 1.70  2003/12/26 19:25:01  bala
//update for 7E...spaces after commas
//
//Revision 1.69  2003/12/24 00:36:56  bala
//fix for 824,831,832 and existing priced feat to print source not quailified offering
//
//Revision 1.68  2003/12/20 14:36:50  bala
//EOD commit
//
//Revision 1.67  2003/12/18 18:16:22  bala
//fix for 20A
//
//Revision 1.66  2003/12/18 00:38:11  bala
//code for existing priced features and other tweaks
//
//Revision 1.65  2003/12/16 00:40:50  bala
//fix
//
//Revision 1.64  2003/12/08 22:58:22  bala
//add 1 more section in featureconversion
//
//Revision 1.63  2003/11/19 22:06:26  bala
//fix syntax
//
//Revision 1.62  2003/11/19 22:03:19  bala
//Q58 model upgrades print exmp before resetting vector
//
//Revision 1.61  2003/11/19 17:41:37  bala
//trim strcondition2 before checking length
//
//Revision 1.60  2003/11/18 19:32:03  bala
//some more section header suppression
//
//Revision 1.59  2003/11/18 19:23:57  bala
//do not print section headers if there is no data
//
//Revision 1.58  2003/11/17 18:42:01  bala
//general fixes
//
//Revision 1.57  2003/11/11 20:16:39  bala
//change Q447,448 to print the longdescription
//
//Revision 1.56  2003/11/11 20:05:42  bala
//Added ModelUpgrades to Q58
//
//Revision 1.55  2003/11/06 21:38:39  bala
//plug in report type
//
//Revision 1.54  2003/10/30 00:45:53  bala
//correct the rptname and the Title
//
//Revision 1.53  2003/10/24 00:49:58  bala
//change gml tag sequence for q287
//
//Revision 1.52  2003/10/21 01:16:58  bala
//setting DgTitle to navigation name
//
//Revision 1.51  2003/10/16 22:16:21  bala
//fix compiler error
//
//Revision 1.50  2003/10/16 22:06:47  bala
//add DISTRTYPE checking
//
//Revision 1.49  2003/10/09 00:38:52  bala
//fix sort, 89
//
//Revision 1.48  2003/10/02 21:41:08  bala
//fix for 292,517
//
//Revision 1.47  2003/10/02 01:19:14  bala
//fix navigation for 20D
//
//Revision 1.46  2003/10/01 22:25:15  bala
//fix typo
//
//Revision 1.45  2003/10/01 22:21:59  bala
//change 20D to new spec
//
//Revision 1.44  2003/10/01 00:44:13  bala
//formatting fix for 515,516,517
//
//Revision 1.43  2003/09/30 00:01:58  bala
//fixes for 9A, 325,326,290
//
//Revision 1.42  2003/09/27 02:01:27  bala
//fix for A44, 515,516,517,8Ato 10D
//
//Revision 1.41  2003/09/16 18:04:10  bala
//set the report name
//
//Revision 1.40  2003/09/11 22:43:30  bala
//add subscription sections to the abrs
//
//Revision 1.39  2003/08/25 22:54:18  bala
//fix 51830,51835,51867,68,78,79,51349,51880,51904,05
//
//Revision 1.38  2003/08/21 20:06:35  bala
//miscellaneous formatting fixes
//
//Revision 1.37  2003/08/07 20:40:24  bala
//fix typo
//
//Revision 1.36  2003/08/07 19:45:07  bala
//fix for 20A misleading spec
//
//Revision 1.35  2003/08/07 19:36:23  bala
//change sequence of discardnonpublishentities call
//
//Revision 1.34  2003/08/07 19:25:03  bala
//check for null value attribute before processing
//
//Revision 1.33  2003/08/07 19:04:59  bala
//fix for 51A,22A and 22B
//
//Revision 1.32  2003/07/31 18:23:48  bala
//get parentEntitygroup in printAttributes when parm
//entitytype is rootEntityType
//
//Revision 1.31  2003/06/11 15:41:56  bala
//change text in 92
//
//Revision 1.30  2003/06/11 15:23:23  bala
//92,15I,530
//
//Revision 1.29  2003/06/11 01:00:41  bala
//fix 340,92,810
//
//Revision 1.28  2003/06/10 18:04:47  bala
//fix _58A
//
//Revision 1.27  2003/06/10 00:25:53  bala
//530 fix
//
//Revision 1.26  2003/06/10 00:19:08  bala
//15I and 530 ..rfageoinclusion/exclusion stuff
//
//Revision 1.25  2003/06/09 23:18:57  bala
//346
//
//Revision 1.24  2003/06/09 23:13:00  bala
//turn back on control break tags
//
//Revision 1.23  2003/06/09 21:00:08  bala
//bug at 57
//
//Revision 1.22  2003/06/09 20:49:30  bala
//56
//
//Revision 1.21  2003/06/09 19:22:10  bala
//fixes to 58, 238
//
//Revision 1.20  2003/06/09 17:18:36  bala
//changed 346,4A..15A,controlbreaks for reports
//
//Revision 1.19  2003/06/08 06:41:07  bala
//Changes to 21J,54E,35A,15A,23A,528,58,525,58A,92,530,40,42,
//45,132,137,237,299,527,803
//
//Revision 1.18  2003/06/06 23:40:17  bala
//syntax fix
//
//Revision 1.17  2003/06/06 23:35:16  bala
//Fix 7C,D, all xml attributes, 1A,b
//
//Revision 1.16  2003/06/06 04:51:36  bala
//fixed 287,288,1A,1B,1C,7C,7D,23A-27J,Q58 formatting
//
//Revision 1.15  2003/06/05 19:27:51  bala
//fixes
//
//Revision 1.14  2003/06/05 17:30:50  bala
//syntax fix
//
//Revision 1.13  2003/06/05 17:20:56  bala
//changed 54A to R to check multiflags
//
//Revision 1.12  2003/06/04 23:59:19  bala
//237 fixes
//
//Revision 1.11  2003/06/04 22:54:18  bala
//fixes to Q58 etc
//
//Revision 1.10  2003/06/04 20:38:52  bala
//fix syntax
//
//Revision 1.9  2003/06/04 17:27:27  bala
//fixed 1a,1b,1c
//
//Revision 1.8  2003/06/04 03:53:06  dave
//un Staticing getABRVersion
//
//Revision 1.7  2003/06/04 03:44:23  dave
//minor syntax
//
//Revision 1.6  2003/06/04 03:41:42  dave
//adding getABRVersion
//
//Revision 1.5  2003/06/04 00:49:43  bala
//fixes
//
//Revision 1.4  2003/06/04 00:36:04  bala
//eod chekin
//
//Revision 1.3  2003/06/04 00:07:24  bala
//fixes
//
//Revision 1.2  2003/06/03 23:57:46  bala
//fixes
//
//Revision 1.1.1.1  2003/06/03 19:02:23  dave
//new 1.1.1 abr
//
//Revision 1.54  2003/06/03 18:13:10  bala
//1st shot at geo tags for Q57-58
//
//Revision 1.53  2003/06/02 22:07:16  bala
//checkin before drop
//
//Revision 1.52  2003/06/02 19:50:06  bala
//misc fixes
//
//Revision 1.51  2003/05/22 22:17:00  bala
//drop commit
//
//Revision 1.50  2003/05/22 01:27:02  bala
//commit checkpoint
//
//Revision 1.49  2003/05/16 21:53:21  bala
//fix A56
//
//Revision 1.48  2003/05/16 00:00:47  bala
//polish transformXML....adding xml document tags
//before and after xml string...checking for null return value
//
//Revision 1.47  2003/05/14 19:25:04  bala
//changed printValueListinItem and printValueingroup to
//take care of the xmltogml conversion
//
//Revision 1.46  2003/05/08 18:50:48  bala
//plug in for xmltogml
//
//Revision 1.45  2003/04/24 20:03:17  bala
//commit before drop
//
//Revision 1.44  2003/04/24 00:33:43  bala
//After changes for V11
//
//Revision 1.43  2002/12/18 23:34:19  bala
//Q57 and 58 Formatting fixes
//
//Revision 1.42  2002/12/14 00:57:48  bala
//formatting fixes for Q58
//
//Revision 1.41  2002/12/12 00:53:37  bala
//fixes
//
//Revision 1.40  2002/12/07 00:47:31  bala
//Fixes
//
//Revision 1.39  2002/12/05 03:03:27  bala
//fixes
//
//Revision 1.38  2002/11/28 00:52:53  bala
//fixes
//
//Revision 1.37  2002/11/27 01:47:09  bala
//fix
//
//Revision 1.36  2002/11/26 22:41:07  bala
//fixes
//
//Revision 1.35  2002/11/23 03:18:34  bala
//fixes
//
//Revision 1.34  2002/11/21 00:24:26  bala
//fixes
//
//Revision 1.33  2002/11/20 00:27:35  bala
//fix
//
//Revision 1.32  2002/11/19 23:17:55  bala
//more fixes
//
//Revision 1.31  2002/11/16 01:50:04  bala
//more fixes
//
//Revision 1.30  2002/11/14 23:54:18  bala
//more fixes
//
//Revision 1.29  2002/11/14 00:44:57  bala
//more fixes
//
//Revision 1.28  2002/11/10 19:42:52  bala
//put .xmp tags changed sections which were printing beyond 72 chars
//
//Revision 1.27  2002/11/08 23:35:19  bala
//fixes
//
//Revision 1.26  2002/11/08 00:20:32  bala
//changes as per ping
//
//Revision 1.25  2002/11/07 01:56:33  bala
//Use prettyPrint method
//
//Revision 1.24  2002/11/05 23:55:43  bala
//more fixes
//
//Revision 1.23  2002/11/04 21:36:43  bala
//fix
//
//Revision 1.22  2002/11/04 17:21:09  bala
//more cleanup
//
//Revision 1.21  2002/10/31 21:07:39  bala
//more cleanup
//
//Revision 1.20  2002/10/31 00:57:47  bala
//eod check in
//
//Revision 1.19  2002/10/18 22:43:03  bala
//formatting changes
//
//Revision 1.18  2002/10/11 00:04:09  bala
//Plug in ReportFormatter
//
//Revision 1.17  2002/10/09 18:04:20  bala
//fix Null pointer error
//
//Revision 1.16  2002/10/03 22:11:18  bala
//split the program into different methods to avoid 32 method size which will cause java to fall over
//
//Revision 1.15  2002/10/03 16:47:28  bala
//after successful compile
//
//Revision 1.14  2002/10/03 01:15:48  bala
//overwriting Naomi changes
//
//Revision 1.12  2002/09/25 01:28:22  bala
//EOD checkpoint
//
//Revision 1.11  2002/09/24 21:08:46  bala
//commit checkpoint
//
//Revision 1.10  2002/09/23 20:59:11  bala
//commit checkpoint on completion of 'short' answers
//
//Revision 1.9  2002/09/19 19:50:59  bala
//string outta range fix
//
//Revision 1.8  2002/09/19 19:32:46  bala
//fix unbalanced parenthesis
//
//Revision 1.7  2002/09/19 19:25:01  bala
//add more report lines
//
//Revision 1.6  2002/09/18 21:20:35  bala
//direct the entitylist dump to log file
//
//Revision 1.5  2002/09/17 23:58:36  bala
//more fixes
//
//Revision 1.4  2002/09/17 23:55:31  bala
//put in ";"
//
//Revision 1.3  2002/09/17 23:53:54  bala
//fix unbalanced parenthesis
//
//Revision 1.2  2002/09/17 23:43:37  bala
//remove typo
//
//Revision 1.1  2002/09/17 23:33:53  bala
//check in
//
package COM.ibm.eannounce.abr.rfa;

// Imported TraX classes
import javax.xml.transform.stream.StreamSource;
// Imported java classes
import java.util.*;
import java.text.*;
import java.util.Hashtable;
import java.io.*;
//import java.io.StringReader;
//import java.io.InputStream;
//import java.io.ByteArrayOutputStream;

import COM.ibm.opicmpdh.middleware.taskmaster.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

/**
 *  This will extract the RFA (Request for Announcement) information from the
 *  ANNOUNCEMENT entity through its related entities. All the smarts are in the
 *  EXTRACT (Virtual Entity) which is set up in the metalinkattr Table The
 *  extract id is EXTRFA01. To understand how this works, you will need to know
 *  how the PDH entities are set up in the PDH; basically, the cascading
 *  relationships and associations starting from the ANNOUNCEMENT entity. The
 *  Extract set up for this will mirror what is required from these
 *  relationships. This program will basically query the EntityList object
 *  returned by the Extract and print the information with the static text as
 *  required for the VM system which is downstream This is not the FINAL report
 *  at this moment in time, but just a sample... many other ABR's will be
 *  written which will check for the completeness of the information required
 *  for the FINAL RFA. This report is for the PACKAGED ANSWERS DATA STREAM
 *
 *@author     Bala
 *@created    September 17, 2002
 */
public class RFAHWABR01 extends PokBaseABR {
  /**
   *  Execute ABR.
   */

  private final String DTDFILEPATH = ABRServerProperties.getDTDFilePath("RFAHWABR01");
  private final String BREAK_INDICATOR= "$$BREAKHERE$$";
  private final String strWorldwideTag = "US, AP, CAN, EMEA, LA";


  EntityGroup grpAnnouncement = null;
  EntityItem eiAnnounce = null;
  EntityGroup grpAnnDeliv = null;
  EntityItem eiAnnDeliv = null;
  EntityGroup grpAnnPara=null;
  EntityItem eiAnnPara=null;
  EntityGroup grpAnnDepend=null;
  EntityItem eiAnnDepend=null;
  EntityGroup grpParamCode = null;
  EntityItem eiParamCode = null;
  EntityGroup grpDependCode = null;
  EntityItem eiDependCode = null;
  EntityGroup grpAnnProj = null;
  EntityItem eiAnnProj = null;
  EntityGroup grpErrataCause = null;
  EntityItem eiErrataCause = null;
  EntityGroup grpOrganUnit = null;
  EntityItem eiOrganUnit = null;
  EntityGroup grpOP = null;
  EntityItem eiOP = null;
  EntityGroup grpChannel = null;
  EntityItem eiChannel = null;
  EntityGroup grpPDSQuestions = null;
  EntityItem eiPDSQuestions = null;
  EntityGroup grpPriceInfo = null;
  EntityItem eiPriceInfo = null;
  EntityGroup grpCommOffInfo = null;
  EntityItem eiCommOFfInfo = null;
  EntityGroup grpDerive = null;
  EntityItem eiDerive = null;
  EntityGroup grpAnnReview = null;
  EntityItem eiAnnReview = null;
  EntityGroup grpConfigurator = null;
  EntityItem eiConfigurator = null;
  EntityGroup grpAnnToConfig = null;
  EntityItem eiAnnToConfig = null;
  EntityGroup grpAnnToOrgUnit = null;
  EntityItem eiAnnToOrgUnit = null;
  EntityGroup grpAnnToOP = null;
  EntityItem eiAnnToOP = null;
  EntityGroup grpCofAvail = null;
  EntityItem eiCofAvail = null;
  EntityGroup grpPdsQuestions = null;
  EntityItem eiPdsQuestions = null;
  EntityGroup grpCommOfIvo = null;
  EntityItem eiCommOFIvo = null;
  EntityGroup grpCommOF = null;
  EntityItem eiCommOF = null;
  EntityGroup grpRelatedANN = null;
  EntityItem eiRelatedANN = null;
  EntityGroup grpGeneralArea = null;
  EntityItem eiGeneralArea = null;
  EntityGroup grpAvail = null;
  EntityItem eiAvail = null;
  EntityGroup grpOrderOF = null;
  EntityItem eiOrderOF = null;
  EntityGroup grpCrossSell = null;
  EntityItem eiCrossSell = null;
  EntityGroup grpUpSell = null;
  EntityItem eiUpSell = null;
  EntityGroup grpStdAmendText = null;
  EntityItem eiStdAmendText = null;
  EntityGroup grpCOFCrypto = null;
  EntityItem eiCOFCrypto = null;
  EntityGroup grpOOFCrypto = null;
  EntityItem eiOOFCrypto = null;
  EntityGroup grpCrypto = null;
  EntityItem eiCrypto = null;
  EntityGroup grpCofOrganUnit = null;
  EntityItem eiCofOrganUnit = null;
  EntityGroup grpIndividual = null;
  EntityItem eiIndividual = null;
  EntityGroup grpPublication = null;
  EntityItem eiPublication = null;
  EntityGroup grpEducation = null;
  EntityItem eiEducation = null;
  EntityGroup grpAnnEducation = null;
  EntityItem eiAnnEducation = null;
  EntityGroup grpIvocat = null;
  EntityItem eiIvocat = null;
  EntityGroup grpBoilPlateText = null;
  EntityItem eiBoilPlateText = null;
  EntityGroup grpCatIncl = null;
  EntityItem eiCatIncl = null;
  EntityGroup grpAlternateOF = null;
  EntityItem eiAlternateOF = null;
  EntityGroup grpCofBPExhibit = null;
  EntityItem eiCofBPExhibit = null;
  EntityGroup grpBPExhibit = null;
  EntityItem eiBPExhibit = null;
  EntityGroup grpCofPubs = null;
  EntityItem eiCofPubs = null;
  EntityGroup grpEnvirinfo = null;
  EntityItem eiEnvirinfo = null;
  EntityGroup grpAltEnvirinfo = null;
  EntityItem eiAltEnvirinfo = null;
  EntityGroup grpPackaging = null;
  EntityItem eiPackaging = null;
  EntityGroup grpSalesmanchg = null;
  EntityItem eiSalesmanchg = null;
  EntityGroup grpAnnSalesmanchg = null;
  EntityItem eiAnnSalesmanchg = null;
  EntityGroup grpOrderOFAvail = null;
  EntityItem eiOrderOFAvail = null;
  EntityGroup grpOrganUnitIndiv = null;
  EntityItem eiOrganUnitIndiv = null;
  EntityGroup grpAnnToAnnDeliv = null;
  EntityItem eiAnnToAnnDeliv = null;
  EntityGroup grpAnnDelReqTrans = null;
  EntityItem eiAnnDelReqTrans = null;
  EntityGroup grpEmeaTranslation = null;
  EntityItem eiEmeaTranslation = null;
  EntityGroup grpAnnToDescArea = null;
  EntityItem eiAnnToDescArea = null;
  EntityGroup grpCofPrice = null;
  EntityItem eiCofPrice = null;
  EntityGroup grpCofChannel = null;
  EntityItem eiCofChannel = null;
  EntityItem eiCofCofMgmtGrp = null;
  EntityItem eiCofOofMgmtGrp = null;
  EntityItem eiOofMemberCofOmg = null;
  EntityItem eiCofMemberCofOmg = null;
  EntityItem eiCofShip = null;
  EntityItem eiShipInfo = null;
  EntityItem eiAnnCofa = null;
  EntityGroup grpAnnCofa = null;
  EntityGroup grpAnnCofOffMgmtGrpa=null;;
  EntityItem eiAnnCofOffMgmtGrpa=null;;
  EntityGroup grpAnnAvail=null;
  EntityItem eiAnnAvail=null;
  EntityGroup grpOpsys=null;
  EntityItem eiOpsys=null;
  EntityGroup grpAnnOp=null;
  EntityItem eiAnnOp=null;

  ReportFormatter rfaReport = null;
  XMLtoGML x2g = new XMLtoGML();// create gml transformer

  String strSplit = null;
  int intSplitLen = 0;
  int intSplitAt = 0;
  int i = 0;
  int j = 0;
  int k = 0;
  int iTemp = 0;
  int iColWidths[] = null;
  int iSortCols[] = null;
  String strCondition1 = null;
  String strCondition2 = null;
  String strCondition3 = null;
  String strCondition4 = null;
  String strCondition5 = null;
  String strCondition6 = null;
  String strCondition7 = null;
  boolean bConditionOK = false;
  EntityItem eiNextItem = null;
  EntityItem eiNextItem1 = null;
  EntityItem eiNextItem2 = null;
  EntityItem eiNextItem3 = null;
  EntityGroup grpNextGroup = null;
  String[] strParamList1 = null;
  String[] strParamList2 = null;
  String[] strFilterAttr = null;
  String[] strFilterValue = null;
  String[] strHeader = null;
  String   m_strSpaces="                                                                                          ";
  Vector vReturnEntities1 = new Vector();
  Vector vReturnEntities2 = new Vector();
  Vector vReturnEntities3 = new Vector();
  Vector vReturnEntities4 = new Vector();
  Vector vPrintDetails = new Vector();
  Vector vPrintDetails1 = new Vector();
  Vector vPrintDetails2 = new Vector();
  Vector vPrintDetails3 = new Vector();
  Hashtable hNoDupeLines = new Hashtable();

  GeneralAreaList m_geList = null;

  EntityList eListFUP =null;
  EntityGroup grpOrderOF2=null;         //Used in the 2nd extract
  /**
   *  Description of the Method
   */
  public void execute_run() {
    try {
      start_ABRBuild();
      setReturnCode(PASS);

      logMessage("VE Dump********************");
      logMessage(m_elist.dump(false));
      logMessage("End VE Dump********************");
      //Build a new General Area tree
      m_geList = new GeneralAreaList(getDatabase(),getProfile());
      m_geList.buildTree();



      logMessage("Starting General area dump*********************************");
      logMessage(m_geList.dump(false));
      logMessage("Ending dump***********************************");


      grpAnnouncement = m_elist.getParentEntityGroup();//This will be for announcement
      logMessage("************Root Entity Type and id " + getEntityType() + ":" + getEntityID());
      vReturnEntities1 = null;
      vReturnEntities2 = null;




      if (grpAnnouncement == null) {
        logMessage("****************Announcement Not found ");
        setReturnCode(FAIL);

      } else {
        logMessage(grpAnnouncement.getEntityItemCount() + " Announcements found!");
        eiAnnounce = grpAnnouncement.getEntityItem(0); //Get the first Announcement
        //eiAnnounce = grpAnnouncement.getEntityItem(getEntityType()+":"+getEntityID());
        if (eiAnnounce==null) {
          logMessage("***********************Root Announcement entityItem cannot be retrieved from abr Item:"+getEntityType()+":"+getEntityID());
          setReturnCode(FAIL);
        }
        /*
         *  Now go get the rest of the stuff
         */
        grpAnnDeliv = m_elist.getEntityGroup("ANNDELIVERABLE");
        eiAnnDeliv = null;
        if (grpAnnDeliv != null) {
          eiAnnDeliv = grpAnnDeliv.getEntityItem(0);
        } else {
          logMessage("**************ANNDELIVERABLE not found in list**");
        }
        grpParamCode = m_elist.getEntityGroup("PARAMETERCODE");
        eiParamCode = null;
        if (grpParamCode != null) {
          eiParamCode = grpParamCode.getEntityItem(0);
        } else {
          logMessage("**************PARAMETERCODE not found in list**");
        }

        grpDependCode = m_elist.getEntityGroup("DEPENDENCYCODE");
        eiDependCode = null;
        if (grpDependCode != null) {
          eiDependCode = grpDependCode.getEntityItem(0);
        } else {
          logMessage("**************DEPENDENCYCODE not found in list**");
        }

        grpAnnProj = m_elist.getEntityGroup("ANNPROJ");
        eiAnnProj = null;
        if (grpAnnProj != null) {
          eiAnnProj = grpAnnProj.getEntityItem(0);
        } else {
          logMessage("**************ANNPROJ not found in list**");
        }

        grpErrataCause = m_elist.getEntityGroup("ERRATACAUSE");
        eiErrataCause = null;
        if (grpErrataCause != null) {
          eiErrataCause = grpErrataCause.getEntityItem(0);
        } else {
          logMessage("**************ERRATACAUSE not found in list**");
        }

        grpOrganUnit = m_elist.getEntityGroup("ORGANUNIT");
        eiOrganUnit = null;
        if (grpOrganUnit != null) {
          eiOrganUnit = grpOrganUnit.getEntityItem(0);
        } else {
          logMessage("**************ORGANUNIT not found in list**");
        }

        grpOP = m_elist.getEntityGroup("OP");
        eiOP = null;
        if (grpOP != null) {
          eiOP = grpOP.getEntityItem(0);
        } else {
          logMessage("**************OP not found in list**");
        }

        grpChannel = m_elist.getEntityGroup("CHANNEL");
        eiChannel = null;
        if (grpChannel != null) {
          eiChannel = grpChannel.getEntityItem(0);
        } else {
          logMessage("**************CHANNEL not found in list**");
        }

        grpPDSQuestions = m_elist.getEntityGroup("PDSQUESTIONS");
        eiPDSQuestions = null;
        if (grpPDSQuestions != null) {
          eiPDSQuestions = grpPDSQuestions.getEntityItem(0);
        } else {
          logMessage("**************PDSQUESTIONS not found in list**");
        }

        grpPriceInfo = m_elist.getEntityGroup("PRICEFININFO");
        eiPriceInfo = null;
        if (grpPriceInfo != null) {
          eiPriceInfo = grpPriceInfo.getEntityItem(0);
        } else {
          logMessage("**************PRICEFININFO not found in list**");
        }

        grpCommOffInfo = m_elist.getEntityGroup("COMMERCIALOFINFO");
        eiCommOFfInfo = null;
        if (grpCommOffInfo != null) {
          eiCommOFfInfo = grpCommOffInfo.getEntityItem(0);
        } else {
          logMessage("**************COMMERCIALOFINFO not found in list**");
        }

        grpDerive = m_elist.getEntityGroup("DERIVE");
        eiDerive = null;
        if (grpDerive != null) {
          eiDerive = grpDerive.getEntityItem(0);
        } else {
          logMessage("**************DERIVE not found in list**");
        }

        grpAnnReview = m_elist.getEntityGroup("ANNREVIEW");
        eiAnnReview = null;
        if (grpAnnReview != null) {
          eiAnnReview = grpAnnReview.getEntityItem(0);
        } else {
          logMessage("**************ANNREVIEW not found in list**");
        }

        grpConfigurator = m_elist.getEntityGroup("CONFIGURATOR");
        eiConfigurator = null;
        if (grpConfigurator != null) {
          eiConfigurator = grpConfigurator.getEntityItem(0);
        } else {
          logMessage("**************CONFIGURATOR not found in list**");
        }

        grpAnnToConfig = m_elist.getEntityGroup("ANNTOCONFIG");
        eiAnnToConfig = null;
        if (grpAnnToConfig != null) {
          eiAnnToConfig = grpAnnToConfig.getEntityItem(0);
        } else {
          logMessage("**************ANNTOCONFIG not found in list**");
        }

        grpAnnToOrgUnit = m_elist.getEntityGroup("ANNORGANUNIT");
        eiAnnToOrgUnit = null;
        if (grpAnnToConfig != null) {
          eiAnnToOrgUnit = grpAnnToOrgUnit.getEntityItem(0);
        } else {
          logMessage("**************ANNORGANUNIT not found in list**");
        }

        grpAnnToOP = m_elist.getEntityGroup("ANNOP");
        eiAnnToOP = null;
        if (grpAnnToOP != null) {
          eiAnnToOP = grpAnnToOP.getEntityItem(0);
        } else {
          logMessage("**************ANNOP not found in list**");
        }

        grpCofAvail = m_elist.getEntityGroup("COMMERCIALOFAVAIL");
        eiCofAvail = null;
        if (grpCofAvail != null) {
          eiCofAvail = grpCofAvail.getEntityItem(0);
        } else {
          logMessage("**************COMMERCIALOFAVAIL not found in list**");
        }

        grpPdsQuestions = m_elist.getEntityGroup("PDSQUESTIONS");
        eiPdsQuestions = null;
        if (grpPdsQuestions != null) {
          eiPdsQuestions = grpPdsQuestions.getEntityItem(0);
        } else {
          logMessage("**************PDSQUESTIONS not found in list**");
        }

        grpCommOfIvo = m_elist.getEntityGroup("COMMERCIALOFIVO");
        eiCommOFIvo = null;
        if (grpCommOfIvo != null) {
          eiCommOFIvo = grpCommOfIvo.getEntityItem(0);
        } else {
          logMessage("**************COMMERCIALOFIVO not found in list**");
        }

        grpCommOF = m_elist.getEntityGroup("COMMERCIALOF");
        eiCommOF = null;
        if (grpCommOF != null) {
          eiCommOF = grpCommOF.getEntityItem(0);
        } else {
          logMessage("**************COMMERCIALOF not found in list**");
        }

        grpRelatedANN = m_elist.getEntityGroup("RELATEDANN");
        eiRelatedANN = null;
        if (grpRelatedANN != null) {
          eiRelatedANN = grpRelatedANN.getEntityItem(0);
        } else {
          logMessage("**************RELATEDANN not found in list**");
        }

        grpGeneralArea = m_elist.getEntityGroup("GENERALAREA");
        eiGeneralArea = null;
        if (grpGeneralArea != null) {
          eiGeneralArea = grpGeneralArea.getEntityItem(0);
        } else {
          logMessage("**************GENERALAREA not found in list**");
        }

        grpAvail = m_elist.getEntityGroup("AVAIL");
        eiAvail = null;
        if (grpAvail != null) {
          eiAvail = grpAvail.getEntityItem(0);
        } else {
          logMessage("**************AVAIL not found in list**");
        }

        grpOrderOF = m_elist.getEntityGroup("ORDEROF");
        eiOrderOF = null;
        if (grpOrderOF != null) {
          eiOrderOF = grpOrderOF.getEntityItem(0);
        } else {
          logMessage("**************ORDEROF not found in list**");
        }

        grpCrossSell = m_elist.getEntityGroup("CROSSSELL");
        eiCrossSell = null;
        if (grpCrossSell != null) {
          eiCrossSell = grpCrossSell.getEntityItem(0);
        } else {
          logMessage("**************CROSSSELL not found in list**");
        }

        grpUpSell = m_elist.getEntityGroup("UPSELL");
        eiUpSell = null;
        if (grpUpSell != null) {
          eiUpSell = grpUpSell.getEntityItem(0);
        } else {
          logMessage("**************UPSELL not found in list**");
        }

        grpStdAmendText = m_elist.getEntityGroup("STANDAMENDTEXT");
        eiStdAmendText = null;
        if (grpStdAmendText != null) {
          eiStdAmendText = grpStdAmendText.getEntityItem(0);
        } else {
          logMessage("**************STANDAMENDTEXT not found in list**");
        }

        grpCOFCrypto = m_elist.getEntityGroup("COFCRYPTO");
        eiCOFCrypto = null;
        if (grpCOFCrypto != null) {
          eiCOFCrypto = grpCOFCrypto.getEntityItem(0);
        } else {
          logMessage("**************COFCRYPTO not found in list**");
        }

        grpOOFCrypto = m_elist.getEntityGroup("OOFCRYPTO");
        eiOOFCrypto = null;
        if (grpOOFCrypto != null) {
          eiOOFCrypto = grpOOFCrypto.getEntityItem(0);
        } else {
          logMessage("**************OOFCRYPTO not found in list**");
        }

        grpCrypto = m_elist.getEntityGroup("CRYPTO");
        eiCrypto = null;
        if (grpCrypto != null) {
          eiCrypto = grpCrypto.getEntityItem(0);
        } else {
          logMessage("**************CRYPTO not found in list**");
        }

        grpCofOrganUnit = m_elist.getEntityGroup("COFORGANUNIT");
        eiCofOrganUnit = null;
        if (grpCofOrganUnit != null) {
          eiCofOrganUnit = grpCofOrganUnit.getEntityItem(0);
        } else {
          logMessage("**************COFORGANUNIT not found in list**");
        }

        grpIndividual = m_elist.getEntityGroup("INDIVIDUAL");
        eiIndividual = null;
        if (grpIndividual != null) {
          eiIndividual = grpIndividual.getEntityItem(0);
        } else {
          logMessage("**************INDIVIDUAL not found in list**");
        }

        grpPublication = m_elist.getEntityGroup("PUBLICATION");
        eiPublication = null;
        if (grpPublication != null) {
          eiPublication = grpPublication.getEntityItem(0);
        } else {
          logMessage("**************PUBLICATION not found in list**");
        }

        grpEducation = m_elist.getEntityGroup("EDUCATION");
        eiEducation = null;
        if (grpEducation != null) {
          eiEducation = grpEducation.getEntityItem(0);
        } else {
          logMessage("**************EDUCATION not found in list**");
        }

        grpAnnEducation = m_elist.getEntityGroup("ANNEDUCATION");
        eiAnnEducation = null;
        if (grpAnnEducation != null) {
          eiAnnEducation = grpAnnEducation.getEntityItem(0);
        } else {
          logMessage("**************ANNEDUCATION not found in list**");
        }

        grpIvocat = m_elist.getEntityGroup("IVOCAT");
        eiIvocat = null;
        if (grpIvocat != null) {
          eiIvocat = grpIvocat.getEntityItem(0);
        } else {
          logMessage("**************IVOCAT not found in list**");
        }

        grpBoilPlateText = m_elist.getEntityGroup("BOILPLATETEXT");
        eiBoilPlateText = null;
        if (grpBoilPlateText != null) {
          eiBoilPlateText = grpBoilPlateText.getEntityItem(0);
        } else {
          logMessage("**************BOILPLATETEXT not found in list**");
        }

        grpCatIncl = m_elist.getEntityGroup("CATINCL");
        eiCatIncl = null;
        if (grpCatIncl != null) {
          eiCatIncl = grpCatIncl.getEntityItem(0);
        } else {
          logMessage("**************CATINCL not found in list**");
        }

        grpAlternateOF = m_elist.getEntityGroup("ALTERNATEOF");
        eiAlternateOF = null;
        if (grpAlternateOF != null) {
          eiAlternateOF = grpAlternateOF.getEntityItem(0);
        } else {
          logMessage("**************ALTERNATEOF not found in list**");
        }

        grpCofBPExhibit = m_elist.getEntityGroup("COFBPEXHIBIT");
        eiCofBPExhibit = null;
        if (grpCofBPExhibit != null) {
          eiCofBPExhibit = grpCofBPExhibit.getEntityItem(0);
        } else {
          logMessage("**************COFBPEXHIBIT not found in list**");
        }

        grpBPExhibit = m_elist.getEntityGroup("BPEXHIBIT");
        eiBPExhibit = null;
        if (grpBPExhibit != null) {
          eiBPExhibit = grpBPExhibit.getEntityItem(0);
        } else {
          logMessage("**************BPEXHIBIT not found in list**");
        }

        grpCofPubs = m_elist.getEntityGroup("COFPUBS");
        eiCofPubs = null;
        if (grpCofPubs != null) {
          eiCofPubs = grpCofPubs.getEntityItem(0);
        } else {
          logMessage("**************COFPUBS not found in list**");
        }

        grpEnvirinfo = m_elist.getEntityGroup("ENVIRINFO");
        eiEnvirinfo = null;
        if (grpEnvirinfo != null) {
          eiEnvirinfo = grpEnvirinfo.getEntityItem(0);
        } else {
          logMessage("**************ENVIRINFO not found in list**");
        }

        grpAltEnvirinfo = m_elist.getEntityGroup("ALTDEPENENVIRINFO");
        eiAltEnvirinfo = null;
        if (grpAltEnvirinfo != null) {
          eiAltEnvirinfo = grpAltEnvirinfo.getEntityItem(0);
        } else {
          logMessage("**************ALTDEPENENVIRINFO not found in list**");
        }

        grpPackaging = m_elist.getEntityGroup("PACKAGING");
        eiPackaging = null;
        if (grpPackaging != null) {
          eiPackaging = grpPackaging.getEntityItem(0);
        } else {
          logMessage("**************PACKAGING not found in list**");
        }

        grpAnnSalesmanchg = m_elist.getEntityGroup("ANNSALESMANCHG");
        eiAnnSalesmanchg = null;
        if (grpAnnSalesmanchg != null) {
          eiAnnSalesmanchg = grpAnnSalesmanchg.getEntityItem(0);
        } else {
          logMessage("**************ANNSALESMANCHG not found in list**");
        }
        grpSalesmanchg = m_elist.getEntityGroup("SALESMANCHG");
        eiSalesmanchg = null;
        if (grpSalesmanchg != null) {
          eiSalesmanchg = grpSalesmanchg.getEntityItem(0);
        } else {
          logMessage("**************SALESMANCHG not found in list**");
        }

        grpOrderOFAvail = m_elist.getEntityGroup("OOFAVAIL");
        eiOrderOFAvail = null;
        if (grpOrderOFAvail != null) {
          eiOrderOFAvail = grpOrderOFAvail.getEntityItem(0);
        } else {
          logMessage("**************OOFAVAIL not found in list**");
        }

        grpAnnToAnnDeliv = m_elist.getEntityGroup("ANNTOANNDELIVER");
        eiAnnToAnnDeliv = null;
        if (grpAnnToAnnDeliv != null) {
          eiAnnToAnnDeliv = grpAnnToAnnDeliv.getEntityItem(0);
        } else {
          logMessage("**************ANNTOANNDELIVER not found in list**");
        }

        grpAnnDelReqTrans = m_elist.getEntityGroup("ANNDELREQTRANS");
        eiAnnDelReqTrans = null;
        if (grpAnnDelReqTrans != null) {
          eiAnnDelReqTrans = grpAnnDelReqTrans.getEntityItem(0);
        } else {
          logMessage("**************ANNDELREQTRANS not found in list**");
        }

        grpAnnToDescArea = m_elist.getEntityGroup("ANNTODESCAREA");
        eiAnnToDescArea = null;
        if (grpAnnToDescArea != null) {
          eiAnnToDescArea = grpAnnToDescArea.getEntityItem(0);
        } else {
          logMessage("**************ANNTODESCAREA not found in list**");
        }

        grpCofPrice = m_elist.getEntityGroup("COFPRICE");
        eiCofPrice = null;
        if (grpCofPrice != null) {
          eiCofPrice = grpCofPrice.getEntityItem(0);
        } else {
          logMessage("**************COFPRICE not found in list**");
        }

        grpAnnPara = m_elist.getEntityGroup("ANNPARA");
        eiAnnPara = null;
        if (grpAnnPara != null) {
          eiAnnPara = grpAnnPara.getEntityItem(0);
        } else {
          logMessage("**************ANNPARA not found in list**");
        }

        grpDependCode = m_elist.getEntityGroup("ANNDEPA");
        eiDependCode = null;
        if (grpDependCode != null) {
          eiDependCode = grpDependCode.getEntityItem(0);
        } else {
          logMessage("**************ANNDEPA not found in list**");
        }

        grpCofChannel = m_elist.getEntityGroup("COFCHANNEL");
        eiCofChannel = null;
        if (grpCofChannel != null) {
          eiCofChannel = grpCofChannel.getEntityItem(0);
        } else {
          logMessage("**************COFCHANNEL not found in list**");
        }

        grpAnnCofa = m_elist.getEntityGroup("ANNCOFA");
        eiAnnCofa = null;
        if (grpAnnCofa != null) {
          eiAnnCofa = grpAnnCofa.getEntityItem(0);
        } else {
          logMessage("**************ANNCOFA not found in list**");
        }

        grpAnnCofOffMgmtGrpa = m_elist.getEntityGroup("ANNCOFOOFMGMTGRPA");
        eiAnnCofOffMgmtGrpa = null;
        if (grpAnnCofOffMgmtGrpa != null) {
          eiAnnCofOffMgmtGrpa = grpAnnCofOffMgmtGrpa.getEntityItem(0);
        } else {
          logMessage("**************ANNCOFOOFMGMTGRPA not found in list**");
        }

        grpAnnAvail = m_elist.getEntityGroup("ANNAVAILA");
        eiAnnAvail = null;
        if (grpAnnAvail != null) {
          eiAnnAvail = grpAnnAvail.getEntityItem(0);
        } else {
          logMessage("**************ANNAVAILA not found in list**");
        }

        grpAnnOp = m_elist.getEntityGroup("ANNOP");
        eiAnnOp = null;
        if (grpAnnOp != null) {
          eiAnnOp = grpAnnOp.getEntityItem(0);
        } else {
          logMessage("**************ANNOP not found in list**");
        }

        rfaReport = new ReportFormatter();
        rfaReport.setABRItem(getABRItem());

        // Set up all the date information ...
        DateFormat df = DateFormat.getDateInstance();
        df.setCalendar(Calendar.getInstance());
        SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
        fmtDate.setCalendar(Calendar.getInstance());
        String strDayToday = fmtDate.format(new Date());

        //Discard all entities which cannot be published in this announcement
        discard_nonPublishEntities();

        /*
         *  Here we go! Start with the report header stuff
         */
        println(".*$REQUESTTYPE_BEGIN");
        /*
                DESCRIPTIONCLASS                 SHORTDESCRIPTION
        -------------------------------- ------------------------------------
        111                              Draft
        112                              Ready for Final Review
        113                              Change(Final Review)
        114                              Approved
        115                              Approved with Risk
        116                              Released to Production Management
        117                              Announced
        118                              Change (Approved)
        119                              Change (Approved w/Risk)
        120                              Change (Released)
        121                              Change (Announced)
        122                              Cancelled
        */

        //DISTRTYPE=720 THEn final edit
        strCondition1 = getAttributeFlagEnabledValue(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "DISTRTYPE", "");
        if (!strCondition1.equals("720")) {
          strCondition1 = getAttributeValue(eiAnnounce, "SENDTOMAINMENU", "NoValuefoundSENDTOMAINMENU");
  //        println("Value of SENDTOMAINMENU is "+strCondition1);
          if (strCondition1.equals("Yes"))  {
  //println("strCondition1");
            strCondition1 = getAttributeFlagEnabledValue(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "ANCYCLESTATUS", "");
            strCondition1 = strCondition1.substring(strCondition1.indexOf("|")==-1 ? 0 :strCondition1.indexOf("|")+1);    //Read after the "|"
            strCondition2 = getAttributeValue(eiAnnounce, "ANNDATE", "");
            i = Integer.valueOf(strCondition1).intValue();
            bConditionOK = true;
            switch (i)  {
              case 112:    //Ready for final review
              case 114:    //Approved
              case 115:    //Approved with Risk
                strCondition3="preEdit";
                break;
              case 117:    //Change (Approved w/Risk)
              case 116:    //Released to Production Management
                if (strCondition2.compareTo(strDayToday)>0 )  {    //set to final only if announcement date > today
                  strCondition3="final";
                } else  {
                  strCondition3="correction";
                }
                break;
              default:
                println("ANCYCLESTATUS returned unexpected flag value");
                break;
            }
          } else  {
            strCondition3="return";
          }
        }else {
            strCondition3="finalEdit";
        }
        println(strCondition3);           //print the request type
        println(".*$REQUESTTYPE_END");
        println(".*$USERIDS_BEGIN");
        //println("balavm at vmmachine");                // What to do with this?
        println("'" + (eiOP != null ? getAttributeValue(eiOP, "USERTOKEN", "") : "'") + "'");
        println(".*$USERIDS_END");
        println(".*$VARIABLES_BEGIN");
        processShortAnswers();
        println(".*$ANSWERS_BEGIN");

        /*
         *  ***************************************
         *  END OF SHORT ANSWERS
         *  Now we go get the LONG answers
         */
        processLongTo100();
        processLongTo200();
        processLongTo300();
        processLongTo400();
        processLongTo500();
        processLongTo600();
        processLongTo700();
        processLongTo800();
        processLongTo900();

        /*
         *  println(".*$A_9_Begin");
         *  println(".*$A_9_End");
         */
         println(".*$ANSWERS_END");

      }

    } catch (Exception exc) {
      // Report this error to both the datbase log and the PrintWriter
      println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      println("" + exc);
      logError(exc, "");
      StringWriter writer = new StringWriter();

      exc.printStackTrace(new PrintWriter(writer));

      String x = writer.toString();

      println(x);

      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }
    } finally {
      //Everything is fine...so lets pass
      if(getReturnCode() == PASS) {
        setReturnCode(PASS);
      }

        String strNavName = eiAnnounce.getNavAttrDescription();

        if (strNavName.length() > 34) {
          strNavName = strNavName.substring(0,34);
        }

        String strDgName = "Extract for " + strNavName + " | " + (getReturnCode() == PASS ? "Passed" : "Failed") + " | Complete";
        setDGTitle(strDgName);


      setDGRptName("RFAHWABR01");
      setDGRptClass("RFAHWABR01");

      printDGSubmitString();      //Stuff into report for subscription and notification

      // set DG submit string
      setDGString(getABRReturnCode());
      // make sure the lock is released
      if (!isReadOnly()) {
        clearSoftLock();
      }
    }
  }



  /**
   *  searchEntityGroup
   *
   *@param  _egFrom             Entity Group to search
   *@param  _strCheckAttribute  String array of attributes to check
   *@param  _strCheckValues     String array of corresponding attribute values
   *      to check in attribute array
   *@param  _bAllTrue           If true, will return a vector of entities
   *      fulfilling all the conditions (AND), else will return entityItem
   *      vector of items fulfilling any condition (OR)
   *@return                     The vector of EntityItems
   */
  private Vector searchEntityGroup(EntityGroup _egFrom, String[] _strCheckAttribute, String[] _strCheckValues, boolean _bAllTrue) {
    Vector vRetval = new Vector();
    //String strAttrValue = null;
//logMessage("**********searchEntityGroup:Attributes:"_strCheckAttribute.toString()+":Values:"+_strCheckValues.toString());
    EntityItem eiCurrentItem = null;
    for (int i = 0; i < _egFrom.getEntityItemCount(); i++) {
      eiCurrentItem = _egFrom.getEntityItem(i);
      if (_strCheckAttribute != null) {
        if (foundInEntity(eiCurrentItem, _strCheckAttribute, _strCheckValues, _bAllTrue)) {
          vRetval.add(eiCurrentItem);
        }
      } else {
        vRetval.add(eiCurrentItem);
      }
    }
//logMessage("**********searchEntityGroup done");

    return vRetval;
  }


    private Vector searchEntityVector(Vector _vFrom, String[] _strCheckAttribute, String[] _strCheckValues, boolean _bAllTrue) {
    Vector vRetval = new Vector();
    //String strAttrValue = null;
    EntityItem eiCurrentItem = null;
    for (int i = 0; i < _vFrom.size(); i++) {
      eiCurrentItem = (EntityItem) _vFrom.elementAt(i);
      if (_strCheckAttribute != null) {
        if (foundInEntity(eiCurrentItem, _strCheckAttribute, _strCheckValues, _bAllTrue)) {
          vRetval.add(eiCurrentItem);
        }
      } else {
        vRetval.add(eiCurrentItem);
      }
    }

    return vRetval;
  }

  /**
   *  Description of the Method
   *
   *@param  _vEntItems          Vector list of EntityItems
   *@param  _strCheckAttribute  array of attributes to check in the linked
   *      entity...a null array will get ALL entities matching _strlinkEtype
   *@param  _strCheckValues     array Values to match the array of attributes
   *@param  _bAllTrue           true if all conditions have to match
   *@param  _bDownLink          "true" if Downlink "false" if uplink
   *@param  _strLinkEtype       Linked entity type to search
   *@return                     Description of the Return Value
   */
  private Vector searchEntityVectorLink(Vector _vEntItems, String[] _strCheckAttribute, String[] _strCheckValues, boolean _bAllTrue, boolean _bDownLink, String _strLinkEtype) {
    Vector vRetval = new Vector();
   // boolean bConditionOK = false;
    //String strAttrValue = null;
    EntityItem eiCurrentItem = null;
    EntityItem eiLinkItem = null;
    int iLinkCount = 0;
    for (int i = 0; i < _vEntItems.size(); i++) {
      eiCurrentItem = (EntityItem) _vEntItems.elementAt(i);
      if (_bDownLink) {
        iLinkCount = eiCurrentItem.getDownLinkCount();
      } else {
        iLinkCount = eiCurrentItem.getUpLinkCount();
      }
      for (int j = 0; j < iLinkCount; j++) {
        if (_bDownLink) {
          eiLinkItem = (EntityItem) eiCurrentItem.getDownLink(j);
        } else {
          eiLinkItem = (EntityItem) eiCurrentItem.getUpLink(j);
        }
        if (eiLinkItem != null) {
          if (eiLinkItem.getEntityType().equals(_strLinkEtype)) {
            if (_strCheckAttribute != null) {
              if (foundInEntity(eiLinkItem, _strCheckAttribute, _strCheckValues, _bAllTrue)) {
                vRetval.add(eiLinkItem);
              }
            } else {
              vRetval.add(eiLinkItem);
            }
          }
        }
      }
    }
    return vRetval;
  }

  private Vector searchInGeo(Vector _vEntItems,String _strGeoToCheck) {
    //Vector vProcessVector = new Vector();
    EntityItem eiCheckItem = null;
    boolean bFound = false;
    //Mark all the entities that are not part of the given geo
    for (int ix=0;ix<_vEntItems.size();ix++) {
      eiCheckItem = (EntityItem) _vEntItems.elementAt(ix);
      bFound = false;
      if (_strGeoToCheck.equals("US"))  {
        bFound = m_geList.isRfaGeoUS(eiCheckItem);
      } else if (_strGeoToCheck.equals("AP"))  {
        bFound = m_geList.isRfaGeoAP(eiCheckItem);
      } else if (_strGeoToCheck.equals("CAN"))  {
        bFound = m_geList.isRfaGeoCAN(eiCheckItem);
      } else if (_strGeoToCheck.equals("EMEA"))  {
        bFound = m_geList.isRfaGeoEMEA(eiCheckItem);
      } else if (_strGeoToCheck.equals("LA"))  {
        bFound = m_geList.isRfaGeoLA(eiCheckItem);
      }
      if (!bFound)  {     //Does not belong to this geo...so remove it
        _vEntItems.remove(ix);
      }
    }
    return _vEntItems;
  }

  private String getGeoTags(EntityItem _entCheck) {
    String strReturn = "";
      if (m_geList.isRfaGeoUS(_entCheck))  {
        strReturn =  "US";
      }
      if (m_geList.isRfaGeoAP(_entCheck))  {
        if (strReturn.length()>0) {
          strReturn += ", AP";
        } else {
          strReturn =  "AP";
        }
      }
      if (m_geList.isRfaGeoCAN(_entCheck))  {
        if (strReturn.length()>0) {
          strReturn += ", CAN";
        } else {
          strReturn =  "CAN";
        }
      }
      if (m_geList.isRfaGeoEMEA(_entCheck))  {
        if (strReturn.length()>0) {
          strReturn += ", EMEA";
        } else {
          strReturn =  "EMEA";
        }
      }
      if (m_geList.isRfaGeoLA(_entCheck))  {
        if (strReturn.length()>0) {
          strReturn += ", LA";
        } else {
          strReturn =  "LA";
        }
      }
      return strReturn;
  }


  //***********************************************************************************************************


  /**
   *  Description of the Method
   *
   *@param  _egFrom             Starting entity group
   *@param  _strCheckAttribute  array of attributes to check in the linked
   *      entity...a null array will get ALL entities matching _strlinkEtype
   *@param  _strCheckValues     array Values to match the array of attributes
   *@param  _bAllTrue           true if all conditions have to match
   *@param  _bDownLink          "true" if Downlink "false" if uplink
   *@param  _strLinkEtype       Linked entity type to search
   *@return                     Description of the Return Value
   */
  private Vector searchEntityGroupLink(EntityGroup _egFrom, String[] _strCheckAttribute, String[] _strCheckValues, boolean _bAllTrue, boolean _bDownLink, String _strLinkEtype) {
//logMessage("In    searchEntityGroupLink");
    Vector vRetval = new Vector();
    //boolean bConditionOK = false;
    //String strAttrValue = null;
    EntityItem eiCurrentItem = null;
    EntityItem eiLinkItem = null;
    int iLinkCount = 0;
    if (_egFrom == null ) {
      return vRetval;
    }
    for (int i = 0; i < _egFrom.getEntityItemCount(); i++) {
      eiCurrentItem = _egFrom.getEntityItem(i);
//logMessage("Searching "+   eiCurrentItem+getEntityType()+":"+eiCurrentItem.getEntityID());
      if (_bDownLink) {
        iLinkCount = eiCurrentItem.getDownLinkCount();
      } else {
        iLinkCount = eiCurrentItem.getUpLinkCount();
      }
      for (int j = 0; j < iLinkCount; j++) {
        if (_bDownLink) {
          eiLinkItem = (EntityItem) eiCurrentItem.getDownLink(j);
//logMessage("Getting Downlinked "+   eiLinkItem+getEntityType()+":"+eiLinkItem.getEntityID());
        } else {
          eiLinkItem = (EntityItem) eiCurrentItem.getUpLink(j);
        }
        if (eiLinkItem != null) {
          if (eiLinkItem.getEntityType().equals(_strLinkEtype)) {
            if (_strCheckAttribute != null) {
              if (foundInEntity(eiLinkItem, _strCheckAttribute, _strCheckValues, _bAllTrue)) {
                vRetval.add(eiLinkItem);
              }
            } else {
              vRetval.add(eiLinkItem);
            }
          }
        }
      }
    }
    return vRetval;
  }

//********************************
  /**
   *  Description of the Method
   *
   *@param  _eiFrom             EntityItem to search from
   *@param  _strCheckAttribute  array of attributes to check in the linked
   *      entity...a null array will get ALL entities matching _strlinkEtype
   *@param  _strCheckValues     array Values to match the array of attributes
   *@param  _bAllTrue           true if all conditions have to match
   *@param  _bDownLink          "true" if Downlink "false" if uplink
   *@param  _strLinkEtype       Linked entity type to search
   *@return                     Description of the Return Value
   */
  private Vector searchEntityItemLink(EntityItem _eiFrom, String[] _strCheckAttribute, String[] _strCheckValues, boolean _bAllTrue, boolean _bDownLink, String _strLinkEtype) {
    Vector vRetval = new Vector();
   // boolean bConditionOK = false;
    //String strAttrValue = null;
    EntityItem eiLinkItem = null;
    int iLinkCount = 0;
    if (_eiFrom == null) {
      return vRetval;
    }
    if (_bDownLink) {
      iLinkCount = _eiFrom.getDownLinkCount();
    } else {
      iLinkCount = _eiFrom.getUpLinkCount();
    }
    for (int j = 0; j < iLinkCount; j++) {
      if (_bDownLink) {
        eiLinkItem = (EntityItem) _eiFrom.getDownLink(j);
      } else {
        eiLinkItem = (EntityItem) _eiFrom.getUpLink(j);
      }
      if (eiLinkItem != null) {
        if (eiLinkItem.getEntityType().equals(_strLinkEtype)) {
          if (_strCheckAttribute != null) {
            if (foundInEntity(eiLinkItem, _strCheckAttribute, _strCheckValues, _bAllTrue)) {
              vRetval.add(eiLinkItem);
            }
          } else {
            vRetval.add(eiLinkItem);
          }
        }
      }
    }

    return vRetval;
  }
//*****************************

  private void printShortValueListInItem(EntityItem _eiItem, String[] _strAttrList, String _strNoValue) {
    int intAttLen = _strAttrList.length;
    String strVal = null;
    for (int j = 0; j < intAttLen; j++) {
      if (_strAttrList[j].trim().length() > 0) {       //This is a attribute name

        strVal = getAttributeShortFlagDesc(_eiItem.getEntityType(), _eiItem.getEntityID(), _strAttrList[j], _strNoValue);
        //add to vector if there is more than 1 attr passed and does not return default
        if (intAttLen > 1) {
          vPrintDetails.add(strVal);
        } else if (!strVal.equals(_strNoValue)) {
          vPrintDetails.add(strVal);
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  _eiItem        Description of the Parameter
   *@param  _strAttrList   Description of the Parameter
   *@param  _strNoValue    Description of the Parameter
   *@param  _bLastNewLine  Description of the Parameter
   *@param  _bAllNewLine   Description of the Parameter
   */
  private void printValueListInItem(EntityItem _eiItem, String[] _strAttrList, String _strNoValue, boolean _bXmlConvert) {
    int intAttLen = _strAttrList.length;
    String strVal = null;
    for (int j = 0; j < intAttLen; j++) {
      if (_strAttrList[j].trim().length() > 0) {       //This is a attribute name
        strVal = getAttributeValue(_eiItem.getEntityType(), _eiItem.getEntityID(), _strAttrList[j], _strNoValue);
        if (strVal!=null  )  {
          if (_bXmlConvert && strVal.trim().length()>0) {
            strVal = transformXML(strVal);
          }
        }
        //add to vector if there is more than 1 attr passed and does not return default
        if (intAttLen > 1) {
          vPrintDetails.add(strVal);
        } else if (!strVal.equals(_strNoValue)) {
          vPrintDetails.add(strVal);
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  _egGroup            Description of the Parameter
   *@param  _strAttrList        Description of the Parameter
   *@param  _strCheckAttribute  Description of the Parameter
   *@param  _strCheckValue      Description of the Parameter
   *@param  _strNoValue         Description of the Parameter
   *@param  _bLastNewLine       Description of the Parameter
   *@param  _bAllNewLine        Description of the Parameter
   */
  private void printValueListInGroup(EntityGroup _egGroup, String[] _strAttrList, String _strCheckAttribute, String _strCheckValue, String _strNoValue, boolean _bXmlConvert) {
    EntityItem eiItem = null;
    //String strCheckValue = null;
    if (_egGroup != null) {
      for (int i = 0; i < _egGroup.getEntityItemCount(); i++) {
        eiItem = _egGroup.getEntityItem(i);
        if (_strCheckAttribute != null) {
          if (foundInEntity(eiItem, new String[]{_strCheckAttribute}, new String[]{_strCheckValue}, true)) {
            printValueListInItem(eiItem, _strAttrList, _strNoValue, _bXmlConvert);
          }
        } else {
          printValueListInItem(eiItem, _strAttrList, _strNoValue, _bXmlConvert);
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  _vEntityItems  Description of the Parameter
   *@param  _strAttrList   Description of the Parameter
   *@param  _strNoValue    Description of the Parameter
   *@param  _bLastNewLine  Description of the Parameter
   *@param  _bAllNewLine   Description of the Parameter
   */
  private void printValueListInVector(Vector _vEntityItems, String[] _strAttrList, String _strNoValue, boolean _bLongValue, boolean _bXMLConvert) {
    EntityItem eiItem = null;
    //String strCheckValue = null;
    if (_vEntityItems != null) {
      for (int i = 0; i < _vEntityItems.size(); i++) {
        eiItem = (EntityItem) _vEntityItems.elementAt(i);
        if (_bLongValue)  {
          printValueListInItem(eiItem, _strAttrList, _strNoValue,_bXMLConvert);
        } else {
          printShortValueListInItem(eiItem, _strAttrList, _strNoValue);
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  _egGroup      Description of the Parameter
   *@param  _strAttrList  Description of the Parameter
   * /
  private void printValueListInGroup(EntityGroup _egGroup, String[] _strAttrList) {
    int intAttLen = _strAttrList.length;
    EntityItem eiPrint = null;
    if (_egGroup != null) {
      for (int i = 0; i < _egGroup.getEntityItemCount(); i++) {
        eiPrint = _egGroup.getEntityItem(i);
        printValueListInItem(eiPrint, _strAttrList, " ", false);
      }
    }
  }*/


  /**
   *  Description of the Method
   *
   *@param  _egGroup           Description of the Parameter
   *@param  _strAttributeName  Description of the Parameter
   *@param  _bAllNewLine       Description of the Parameter
   * /
  private void printValueInGroup(EntityGroup _egGroup, String _strAttributeName, boolean _bAllNewLine) {
    printValueInGroup(_egGroup, _strAttributeName, " ", _bAllNewLine);
  }*/


  /**
   *  Description of the Method
   *
   *@param  _egGroup           Description of the Parameter
   *@param  _strAttributeName  Description of the Parameter
   *@param  _strNoValue        Description of the Parameter
   *@param  _bLastNewLine      Description of the Parameter
   * /
  private void printValueInGroup(EntityGroup _egGroup, String _strAttributeName, String _strNoValue, boolean _bLastNewLine) {
    EntityItem eiPrint = null;
    String[] strAttr = new String[]{_strAttributeName};
    if (_egGroup != null) {
      for (int i = 0; i < _egGroup.getEntityItemCount(); i++) {
        eiPrint = _egGroup.getEntityItem(i);
        printValueListInItem(eiPrint, strAttr, _strNoValue, false);
      }
    }
  }*/


  /**
   *  Description of the Method
   *
   *@param  _egSearchGroup     Description of the Parameter
   *@param  _strAttributeName  Description of the Parameter
   *@param  _strSearchFor      Value to match in the attribute, null will return
   *      true if any value found
   *@return                    Description of the Return Value
   * /
  private boolean foundInGroup(EntityGroup _egSearchGroup,String  _strAttributeName,String _strSearchFor) {
    return foundInGroup(_egSearchGroup, new String[] {_strAttributeName},new String[] {_strSearchFor},true);
  }*/

/*
   private boolean foundInGroup(EntityGroup _egSearchGroup, String []_strAttributeName, String [] _strSearchFor,boolean _bAllTrue) {
    boolean bRetVal = false;
    EntityItem eiSearchItem = null;
    String strAttributeValue = null;
    if (_egSearchGroup != null) {
      for (int i = 0; i < _egSearchGroup.getEntityItemCount(); i++) {
        eiSearchItem = _egSearchGroup.getEntityItem(i);
        if (foundInEntity(eiSearchItem,_strAttributeName,_strSearchFor,_bAllTrue) ) {
          bRetVal = true;
        }
        if (bRetVal)  {   //Keep on going for an "AND" condition till all the conditions are evaluated
          if (!_bAllTrue) {
            break;
          }
        } else {
          if (_bAllTrue) {
            break;
          }
        }
      }
    }

    return bRetVal;

  }*/


  /**
   *  Description of the Method 363 *@param _eiSearchItem Description of the
   *  Parameter
   *
   *@param  _strAttributeName  Description of the Parameter
   *@param  _strSearchFor      Description of the Parameter
   *@param  _bAllTrue          Description of the Parameter
   *@param  _eiSearchItem      Description of the Parameter
   *@return                    Description of the Return Value
   */
  private boolean foundInEntity(EntityItem _eiSearchItem, String[] _strAttributeName, String[] _strSearchFor, boolean _bAllTrue) {
    boolean bRetVal = false;
    boolean bisFlag = false;
    String strAttrValue = null;
    if (_eiSearchItem == null) {
      logMessage("Passing null entity item to search");
      return false;
    }
    EntityGroup entGroup = _eiSearchItem.getEntityGroup();
    EANMetaAttribute eanMetaAtt =  null ;

    for (int j = 0; j < _strAttributeName.length; j++) {
      bisFlag = false;
      eanMetaAtt = entGroup.getMetaAttribute(_strAttributeName[j]);
      switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'F':
        case 'U':
        case 'S':
        bisFlag=true;
      }
logMessage("Checking "+_eiSearchItem.getEntityType()+":"+_eiSearchItem.getEntityID()+":"+_strAttributeName[j]+ " for value "+_strSearchFor[j]);
      if (_strSearchFor[j].toLowerCase().equals(_strSearchFor[j].toUpperCase()) && bisFlag) {      //This is a flag code
        if (flagvalueEquals(_eiSearchItem,_strAttributeName[j],_strSearchFor[j])) {
          bRetVal = true;
        } else {
          bRetVal = false;
        }
      } else {
        strAttrValue = getAttributeValue(_eiSearchItem, _strAttributeName[j], "");
        if (strAttrValue.indexOf(_strSearchFor[j]) > -1) {
          bRetVal = true;                                //Keep on going for an "AND" condition till all the conditions are evaluated
          //println(" fOUND!!");
        } else {
          bRetVal = false;
          //println(" not fOUND!!");
        }
      }
      if (bRetVal)  {   //Keep on going for an "AND" condition till all the conditions are evaluated
        if (!_bAllTrue) {
          break;
        }
      } else {
        if (_bAllTrue) {
          break;
        }
      }
    }
    //logMessage(" Returning "+bRetVal);

    return bRetVal;
  }


  /**
   *  Get the entity description to use in error messages
   *
   *@param  entityType  Description of the Parameter
   *@param  entityId    Description of the Parameter
   *@return             String
   */
  protected String getABREntityDesc(String entityType, int entityId) {
    return null;
  }


  /**
   *  Get ABR description
   *
   *@return    java.lang.String
   */
  public String getDescription() {
    //return Constants.IAB3053I + "<br /><br />" + Constants.IAB3050I;
    return "<br /><br />";
  }


  /**
   *  Get any style that should be used for this page. Derived classes can
   *  override this to set styles They must include the <style>...</style> tags
   *
   *@return    String
   */
  protected String getStyle() {
    // Print out the PSG stylesheet
    return "";
  }


  /**
   *  Get the getRevision
   *
   *@return    java.lang.String
   */
  public String getRevision() {
    return new String("$Revision: 1.75 $");
  }




  /**
   *  Description of the Method
   *
   *@param  _bHeader        Description of the Parameter
   *@param  _strHeader      Description of the Parameter
   *@param  _iColWidths     Description of the Parameter
   *@param  _vPrintDetails  Description of the Parameter
   *@param  _iOffset        Description of the Parameter
   * /
  private void printReport(boolean _bHeader, String[] _strHeader, int[] _iColWidths, Vector _vPrintDetails, int _iOffset) {
    rfaReport.setOffset(_iOffset);
    printReport(_bHeader, _strHeader, _iColWidths, _vPrintDetails);
  }*/

  private void printReport(boolean _bHeader, String[] _strHeader, int[] _iColWidths, Vector _vPrintDetails,int [] _iSortColumns) {
    rfaReport.setSortable(true);
    rfaReport.setSortColumns(_iSortColumns);
    printReport(_bHeader, _strHeader, _iColWidths, _vPrintDetails);
  }

  /*
  This is the default call to printReport()
  private void printReport(boolean _bHeader, String[] _strHeader, int[] _iColWidths, Vector _vPrintDetails) {
    private int[] iNullSort = null;
    rfaReport.setSortable(false);
    printReport(_bHeader, _strHeader, _iColWidths, _vPrintDetails,iNullSort);
  }
   */

  /**
   *  Description of the Method
   *
   *@param  _bHeader        Description of the Parameter
   *@param  _strHeader      Description of the Parameter
   *@param  _iColWidths     Description of the Parameter
   *@param  _vPrintDetails  Description of the Parameter
   */
  private void printReport(boolean _bHeader, String[] _strHeader, int[] _iColWidths, Vector _vPrintDetails) {
    if ((_iColWidths.length > 1 || _bHeader)) {
      rfaReport.printHeaders(_bHeader);
      rfaReport.setHeader(_strHeader);
      rfaReport.setColWidth(_iColWidths);
      rfaReport.setDetail(_vPrintDetails);

      if (_vPrintDetails.size() > 0) {
        rfaReport.printReport();
      }
      rfaReport.setOffset(0);                          //Reset previous offsets
    } else {
      if (_iColWidths[0] == 69) {
        for (int x = 0; x < _vPrintDetails.size(); x++) {
          prettyPrint((String) _vPrintDetails.elementAt(x), _iColWidths[0]);
        }
      } else if (_vPrintDetails.size() > 0) {
        rfaReport.printHeaders(_bHeader);
        rfaReport.setColWidth(_iColWidths);
        rfaReport.setDetail(_vPrintDetails);
        rfaReport.printReport();
      }
      rfaReport.setOffset(0);                          //Reset previous offsets
      rfaReport.setColumnSeparator(" ");
    }
    rfaReport.setSortable(false);

  }



  /**
   *  Description of the Method
   */
  private void resetPrintvars() {
    vPrintDetails.removeAllElements();
    vPrintDetails = null;
    vPrintDetails = new Vector();
  }


  /**
   *  Description of the Method
   *
   *@param  _v  Description of the Parameter
   */
  private void displayContents(Vector _v) {
    EntityItem eiItem = null;
    for (int x = 0; x < _v.size(); x++) {
      eiItem = (EntityItem) _v.elementAt(x);
      logMessage("ET:" + eiItem.getEntityType() + ":EI:" + eiItem.getEntityID());
    }
  }


  private boolean discard_nonPublishEntities() {
    boolean bRetvalue = false;
    //String strAnnOSLevel = getAttributeFlagEnabledValue(getEntityType(),getEntityID(), "OSLEVEL", "");
    String strAnnDate = getAttributeValue(getEntityType(),getEntityID(), "ANNDATE", "");
    String strPubAfter = null;
    String strStoredPub = null;
    String strOpsysOSlevel = null;
    //String strEntityOSlevel = null;
    //Vector vAnnOSLevel = new Vector();

    Hashtable hAnnOSLevel = new Hashtable();
    Hashtable hOPSysOSLevel = new Hashtable();

    boolean bFoundOSTag = false;

    //Check for the existence of OPSYS entity
    grpOpsys = m_elist.getEntityGroup("OPSYS");
    eiOpsys = null;
    if (grpOpsys != null) {
      eiOpsys = grpOpsys.getEntityItem(0);
    } else {
      logMessage("discard_nonPublishEntities:**************OPSYS not found in list**");
      return false;
    }


    //Store all the OSLEVEL's selected in the announcement
    EANAttribute EANOSLevel = eiAnnounce.getAttribute("OSLEVEL");
    if (EANOSLevel == null) {
      logMessage("discard_nonPublishEntities:OSLEVEL not found in Announcment");
      return false;
    }
    EANFlagAttribute EANFAttr = (EANFlagAttribute) EANOSLevel;
    MetaFlag[] mfAttr = (MetaFlag[]) EANFAttr.get();

    for (i = 0; i < mfAttr.length; i++) {
      if (mfAttr[i].isSelected()) {
        hAnnOSLevel.put(mfAttr[i].getFlagCode(),"Found");
      }
    }


    //Now store all the OSLEVELS and its corresponding PUBAFTER date only if the OSLEVEL is selected in the announcement
    for (i=0;i<grpOpsys.getEntityItemCount();i++)  {
      eiOpsys = grpOpsys.getEntityItem(i);
      EANOSLevel = eiOpsys.getAttribute("OSLEVEL");
      EANFAttr = (EANFlagAttribute) EANOSLevel;
      mfAttr = (MetaFlag[]) EANFAttr.get();

      strPubAfter = getAttributeValue(eiOpsys.getEntityType(),eiOpsys.getEntityID(), "PUBAFTER", "");


      for (i = 0; i < mfAttr.length; i++) {
        if (mfAttr[i].isSelected()) {
          strOpsysOSlevel = mfAttr[i].getFlagCode();
          if (hAnnOSLevel.get(strOpsysOSlevel) != null) {           //Ok this OPSYS is used in the announcement
            if (strPubAfter.compareTo(strAnnDate) < 0 )  { //Announcement date is before pubafter date
              logMessage("discard_nonPublishEntities:**************Invalid condition...Announcement date:"+strAnnDate+" is before publish date:"+strPubAfter);
              return false;
            }

          //Check whether OSLEVEL previously stored, if so store the date if its greater than previous
            strStoredPub = (String) hOPSysOSLevel.get(strOpsysOSlevel);
            if (strStoredPub != null) {
              if (strStoredPub.compareTo(strPubAfter) > 0 )  {  //Stored date is before current date...
                hOPSysOSLevel.remove(strOpsysOSlevel);            //remove current hashkey and date
                hOPSysOSLevel.put(strOpsysOSlevel,strPubAfter);   //put the new one with the new date
              }
            } else {
              hOPSysOSLevel.put(strOpsysOSlevel,strPubAfter);   //put the new one
            }
          }
        }
      }



    }

    //Scan the entitylist for OSLEVEL Attribute
    for (int x1=0;x1<m_elist.getEntityGroupCount();x1++) {
      EntityGroup egCheckGroup = m_elist.getEntityGroup(x1);
      logMessage("discard_nonPublishEntities:Scanning :"+egCheckGroup.getEntityType());
      if (egCheckGroup.getMetaAttribute("OSLEVEL")==null) {
        continue;                               //Dont waste time...look at the next entityItem
      }

      //Now scan the EntityGroup for entityItems having OSLEVEL and
      //  discard the items whose OSlevels dont match the ones in announcment
      for (int x3=0;x3 <egCheckGroup.getEntityItemCount();x3++) {
        EntityItem eiCheckItem = egCheckGroup.getEntityItem(x3);
        String strCheckOSLevel =  getAttributeValue(eiCheckItem.getEntityType(),eiCheckItem.getEntityID(), "OSLEVEL", "None");
        if (strCheckOSLevel.equals("None")) {
          logMessage("discard_nonPublishEntities:OSLEVEL Not Populated ** Discarding entity"+eiCheckItem.getKey());
          egCheckGroup.removeEntityItem(eiCheckItem);
          continue;
        }
        //Now get the populated OSLevels and match it against the announcment oslevel
        bFoundOSTag = false;
        EANOSLevel = eiCheckItem.getAttribute("OSLEVEL");
        EANFAttr = (EANFlagAttribute) EANOSLevel;
        mfAttr = (MetaFlag[]) EANFAttr.get();
        for (i = 0; i < mfAttr.length; i++) {
          if (mfAttr[i].isSelected()) {
            strOpsysOSlevel = mfAttr[i].getFlagCode();
            if (hAnnOSLevel.get(strOpsysOSlevel) != null) {           //Ok this OPSYS is used in the announcement
              bFoundOSTag=true;
            }
          }
        }
        if (!bFoundOSTag)  {
          logMessage("discard_nonPublishEntities:Matching OSLEVEL not found  ** Discarding entity"+eiCheckItem.getKey());
          egCheckGroup.removeEntityItem(eiCheckItem);
        }
      }
    }


    return bRetvalue;
  }

  /**
   *  Description of the Method
   */
  private void processShortAnswers() {
    if (eiAnnounce == null) {
      println("Announce EntityItem IS NULL!");
    }
    println("CNTLNO = '" + getAttributeValue(eiAnnounce, "ANNNUMBER", "Not Populated") + "'");
    println("TYPE = '" + getAttributeValue(eiAnnounce, "OFANNTYPE", "Not Populated") + "'");
    println("VERSION = '18.03'");
    strSplit = "";
logMessage("_1A*******************");
    strFilterAttr = new String[]{"DELIVERABLETYPE"};
    strFilterValue = new String[]{"860"};
    vReturnEntities1 = searchEntityGroup(grpAnnDeliv, strFilterAttr, strFilterValue, true);
    eiAnnDeliv = vReturnEntities1.size()>0 ? (EntityItem) vReturnEntities1.elementAt(0) : null;
    bConditionOK = false;
    if (eiAnnDeliv!=null) {
      bConditionOK = true;
      strSplit =(eiAnnDeliv != null ? getAttributeValue(eiAnnDeliv, "SUBJECTLINE_1", "") : "ANNDELIVERABLE NOT LINKED") ;
    }
    println(".*$P_1A = '" + strSplit+"'");
    strSplit = "";
    if (bConditionOK) {
//      strSplit = (eiAnnDeliv != null ? getAttributeValue(eiAnnDeliv, "DELIVERABLETYPE", "Not Populated") : "ANNDELIVERABLE NOT LINKED") + "'";
      strSplit = (eiAnnDeliv != null ? getAttributeValue(eiAnnDeliv, "SUBJECTLINE_2", "") : "ANNDELIVERABLE NOT LINKED");
    }
    println(".*$P_1B = '" + strSplit+"'");
    //get the assoc from announcement to this
    vReturnEntities1 = searchEntityGroupLink(grpAnnPara, null, null, true, true, "PARAMETERCODE");
    eiParamCode =  vReturnEntities1.size() > 0 ? (EntityItem) vReturnEntities1.elementAt(0) : null;
    println(".*$P_1C = '" + (eiParamCode != null ? getAttributeValue(eiParamCode, "PARAMETERCODENUMBER", "") : "PARAMETERCODE not linked") + "'");

    vReturnEntities1 = searchEntityGroupLink(grpDependCode, null, null, true, true, "DEPENDENCYCODE");
    eiDependCode =  vReturnEntities1.size() > 0 ? (EntityItem) vReturnEntities1.elementAt(0) : null;
    strSplit = (eiDependCode != null ? getAttributeValue(eiDependCode, "DEPENCODENUMBER", "Not Populated") : "DEPENDENCYCODE not linked") + "'";
    intSplitLen = strSplit.length();
    logMessage("DEPENCODENUMBER returned " + strSplit + " length is " + intSplitLen);
    intSplitAt = 47;
    println(".*$P_1D = '" + strSplit.substring(0, (intSplitLen > intSplitAt ? intSplitAt : intSplitLen)));
    intSplitAt += 47;
    println(".*$P_1E = '" + (intSplitLen > intSplitAt ? strSplit.substring(48, intSplitAt) : "") + "'");
    intSplitAt += 47;
    println(".*$P_1F = '" + (intSplitLen > intSplitAt ? strSplit.substring(96, intSplitAt) : "") + "'");

    strCondition1 = getAttributeValue(eiAnnounce, "MULTIPLEOFFERING", "0");
    strSplit = (strCondition1.equalsIgnoreCase("Yes") ? "1" : "0");
    println(".*$P_2A = '" + strSplit + "'");

    strCondition1 = getAttributeValue(eiAnnounce, "STATEOFGENDIRECT", "0");
    strSplit = (strCondition1.equalsIgnoreCase("Yes") ? "1" : "0");
    println(".*$P_2B = '" + strSplit + "'");

    println(".*$P_3A = '" + (eiAnnProj != null ? getAttributeValue(eiAnnProj.getEntityType(), eiAnnProj.getEntityID(), "AVAILDCP_T", "") : "'") + "'");
    strCondition1 = (eiAnnReview != null ? getAttributeValue(eiAnnReview.getEntityType(), eiAnnReview.getEntityID(), "ANREVIEW", "") : "");
    strSplit = "";
    strFilterAttr = new String[] {"ANNREVIEWDEF"};
    strFilterValue = new String[] {"101"};
    vReturnEntities1 = searchEntityGroup(grpAnnReview, strFilterAttr, strFilterValue, true);
    eiAnnReview =  vReturnEntities1.size() > 0 ? (EntityItem) vReturnEntities1.elementAt(0) : null;
    strSplit = (eiAnnReview != null ? getAttributeValue(eiAnnReview.getEntityType(), eiAnnReview.getEntityID(), "ANREVDATE", "") : "");
    println(".*$P_3B = '" + strSplit + "'");
    strSplit = "";
    strFilterAttr = new String[] {"ANNREVIEWDEF"};
    strFilterValue = new String[] {"102"};
    vReturnEntities1 = searchEntityGroup(grpAnnReview, strFilterAttr, strFilterValue, true);
    eiAnnReview =  vReturnEntities1.size() > 0 ? (EntityItem) vReturnEntities1.elementAt(0) : null;
    strSplit = (eiAnnReview != null ? getAttributeValue(eiAnnReview.getEntityType(), eiAnnReview.getEntityID(), "ANREVDATE", "") : "");
    println(".*$P_3C = '" + strSplit + "'");
    println(".*$P_3D = '" + (grpAnnouncement != null ? getAttributeValue(eiAnnounce, "ANNDATE", "") : "") + "'");
//Ask ALAN 4a, 4b....6K
//    println(".*$P_4A = '" + (grpAnnouncement != null ? getAttributeValue(eiAnnounce, "ANNTYPE", "") : "") + "'");
    println(".*$P_4A = '0'");
    println(".*$P_4B = '" + (grpAnnouncement != null ? getAttributeValue(eiAnnounce, "REVISIONNUMBER", "") : "'") + "'");
//    println(".*$P_4B = '0'");

//    println(".*$P_6A = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");
    println(".*$P_6A = '0'");
//println(".*$P_6B = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");
    println(".*$P_6B = '0'");
    println(".*$P_6C = '0'");
    println(".*$P_6D = '0'");
    println(".*$P_6E = '0'");
    println(".*$P_6F = '0'");
    println(".*$P_6G = '0'");
    println(".*$P_6H = '0'");
    println(".*$P_6I = '0'");
    println(".*$P_6J = '0'");
    println(".*$P_6K = '0'");
//    println(".*$P_6C = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");
//    println(".*$P_6D = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");
//    println(".*$P_6E = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");
//    println(".*$P_6F = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");
//    println(".*$P_6G = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");
//    println(".*$P_6H = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");
//    println(".*$P_6I = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");
//    println(".*$P_6J = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");
//    println(".*$P_6K = '" + (eiErrataCause != null ? getAttributeValue(eiErrataCause.getEntityType(), eiErrataCause.getEntityID(), "CAUSE", "0") : "0") + "'");

    strCondition1 = getAttributeValue(eiAnnounce, "EXECAPPREADY", "0");
    println(".*$P_7A = '" + (strCondition1.equalsIgnoreCase("Yes") ? "1" : "0") + "'");
    println(".*$P_7B = '" + (grpAnnouncement != null ? getAttributeValue(eiAnnounce, "EXECAPPRDATE_T", "") : "") + "'");

    /*
     *  Print 7C to 7E if when ORGANUNITTYPE='Division' on ANNORGANUNIT
     */
     bConditionOK = false;
    strFilterAttr = new String[] {"ORGANUNITTYPE"};
    strFilterValue = new String[] {"4156"};
    vReturnEntities1 = searchEntityGroup(grpOrganUnit, strFilterAttr, strFilterValue, false);
    if (vReturnEntities1.size()>0)  {
      eiOrganUnit = (EntityItem) vReturnEntities1.elementAt(0);
      bConditionOK=true;
    }
    strCondition1 = (bConditionOK ? getAttributeValue(eiOrganUnit, "NAME", "") : "");
    println(".*$P_7C = '" +strCondition1.trim() + "'");
    strCondition1 = (bConditionOK ? getAttributeValue(eiOrganUnit, "INITIALS", "") : "");
    println(".*$P_7D = '" + strCondition1.trim() + "'");
    strCondition1 = (bConditionOK ? getAttributeValue(eiOrganUnit, "STREETADDRESS", "") : "");

    strCondition2 = (bConditionOK ? getAttributeValue(eiOrganUnit, "CITY", "") : "");

    strCondition1 += strCondition1.trim().length() >0 && strCondition2.trim().length() >0 ?  ", "+strCondition2.trim() : "";

    strCondition2 = (bConditionOK ? getAttributeValue(eiOrganUnit, "STATE", "") : "");
    strCondition1 += strCondition1.trim().length() >0 && strCondition2.trim().length() >0 ?  ", "+strCondition2.trim() : "";

    strCondition2 = (bConditionOK ? getAttributeValue(eiOrganUnit, "COUNTRY", "") : "");
    strCondition1 += strCondition1.trim().length() >0 && strCondition2.trim().length() >0 ?  ", "+strCondition2.trim() : "";

    strCondition2 = (bConditionOK ? getAttributeValue(eiOrganUnit, "ZIPCODE", "") : "");
    strCondition1 += strCondition1.trim().length() >0 && strCondition2.trim().length() >0 ?  ", "+strCondition2.trim() : "";
    println(".*$P_7E = '" + strCondition1 + "'");


    /*
     *  Print 8A to 9A  if when ANNROLETYPE='Division President ' on ANNOP
     */
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(),eiAnnToOP.getEntityID(),"ANNROLETYPE","15")) {
        eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
        break;
      }
    }
    println(".*$P_8A = '" + (eiOP != null ? getAttributeValue(eiOP, "USERNAME", "") : "") + "'");
    println(".*$P_8B = '" + (eiOP != null ? getAttributeValue(eiOP, "STREETADDRESS", "") : "") + "'");
    strSplit = (eiOP != null ? getAttributeValue(eiOP, "CITY", "") : "");
    strSplit = strSplit + ", " + (eiOP != null ? getAttributeValue(eiOP, "STATE", "") : "");
    strSplit = strSplit + ", " + (eiOP != null ? getAttributeValue(eiOP, "COUNTRY", "") : "");
    println(".*$P_8C = '" + strSplit + "'");
    println(".*$P_9A = '" + (eiOP != null ? getAttributeValue(eiOP, "SITE", "") : "") + "'");

    /*
     *  Print 9b to 9D  if when ANNROLETYPE='Division FOCAL PROINT ' on ANNOP
     */
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
logMessage("Searching fo ANNROLETYPE4");
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(),eiAnnToOP.getEntityID(),"ANNROLETYPE","4")) {
logMessage("Found Searching fo ANNROLETYPE4");
        eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
        break;
      }
    }

    println(".*$P_9B = '" + (eiOP != null ? getAttributeValue(eiOP, "USERNAME", "") : "") + "'");
    println(".*$P_9C = '" + (eiOP != null ? getAttributeValue(eiOP, "VNETUID", "") : "") + "'");
    println(".*$P_9D = '" + (eiOP != null ? getAttributeValue(eiOP, "VNETNODE", "") : "") + "'");

    /*
     *  Print 10 to 10c  if when ANNROLETYPE=' Sponsor' on ANNOP
     */
logMessage("Searching fo ANNROLETYPE9");
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(),eiAnnToOP.getEntityID(),"ANNROLETYPE","9")) {
logMessage("Found Searching fo ANNROLETYPE9");
        eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
        break;
      }
    }
    println(".*$P_10A = '" + (eiOP != null ? getAttributeValue(eiOP, "USERNAME", "") : "") + "'");
    println(".*$P_10B = '" + (eiOP != null ? getAttributeValue(eiOP, "JOBTITLE", "") : "") + "'");
    println(".*$P_10C = '" + (eiOP != null ? getAttributeValue(eiOP, "TIELINE", "") : "") + "'");
    strSplit = (eiOP != null ? getAttributeValue(eiOP, "VNETNODE", "") : "");
    strSplit = strSplit + "/" + (eiOP != null ? getAttributeValue(eiOP, "VNETUID", "") : "");
    println(".*$P_10D = '" + strSplit + "'");

    /*
     *  ANNROLETYPE='Sponsor Rep' on ANNOP
     */
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
logMessage("Searching fo ANNROLETYPE7");
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(),eiAnnToOP.getEntityID(),"ANNROLETYPE","7")) {
logMessage("Found Searching fo ANNROLETYPE7");
        eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
        break;
      }
    }
    println(".*$P_11A = '" + (eiOP != null ? getAttributeValue(eiOP, "USERNAME", "") : "") + "'");
    println(".*$P_11B = '" + (eiOP != null ? getAttributeValue(eiOP, "TELEPHONE", "") : "") + "'");
    println(".*$P_11C = '" + (eiOP != null ? getAttributeValue(eiOP, "VNETUID", "") : "") + "'");
    println(".*$P_11D = '" + (eiOP != null ? getAttributeValue(eiOP, "VNETNODE", "") : "") + "'");
    println(".*$P_11E = '" + (eiOP != null ? getAttributeValue(eiOP, "EMAIL", "") : "") + "'");

    /*
     *  Print 11F to 11I  if when ANNROLETYPE='Product Development Mgr' on ANNOP
     */
logMessage("Searching fo ANNROLETYPE3");
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(),eiAnnToOP.getEntityID(),"ANNROLETYPE","3")) {
logMessage("Found Searching fo ANNROLETYPE3");
        eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
        break;
      }
    }
    println(".*$P_11F = '" + (eiOP != null ? getAttributeValue(eiOP, "USERNAME", "") : "") + "'");
    println(".*$P_11G = '" + (eiOP != null ? getAttributeValue(eiOP, "TELEPHONE", "") : "") + "'");
    println(".*$P_11H = '" + (eiOP != null ? getAttributeValue(eiOP, "EMAIL", "") : "") + "'");
    //println(".*$P_11I = '" + (eiOP != null ? getAttributeValue(eiOP, "VNETNODE", "") : "") + "'");

    /*
     *  Print 13A to 13E if when ANNROLETYPE='Marketing Comm Mgr' on ANNOP
     */
logMessage("Searching fo ANNROLETYPE6");
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(),eiAnnToOP.getEntityID(),"ANNROLETYPE","6")) {
logMessage("Found Searching fo ANNROLETYPE9");
        eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
        break;
      }
    }
    println(".*$P_13A = '" + (eiOP != null ? getAttributeValue(eiOP, "USERNAME", "") : "") + "'");
    println(".*$P_13B = '" + (eiOP != null ? getAttributeValue(eiOP, "EMAIL", "") : "") + "'");
    println(".*$P_13D = '" + (eiOP != null ? getAttributeValue(eiOP, "TELEPHONE", "") : "") + "'");
    println(".*$P_13E = '" + (eiOP != null ? getAttributeValue(eiOP, "SITE", "") : "") + "'");

    /*
     *  Print 14A  if when CHANNELNAME=''SS/1 Sales Specialist' OR 'SS/3 Sales Specialist'on CHANNEL
     *  path is 'ANNOUNCEMENT->AVAIL->COMMERCIALOF-> CHANNEL  tbd..not extracted
     */
/* Bala make this COF with HW/system/ base to   Channel    (per feedback 51171
 */
    bConditionOK = false;
    eiChannel=null;
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFCHANNEL");
    strFilterAttr = new String[]{"CHANNELNAME", "CHANNELNAME"};
    strFilterValue = new String[]{"364", "365"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
    if (vReturnEntities3.size()>0) {
      println(".*$P_14A = '1'");
    } else {
      println(".*$P_14A = '0'");
    }
    strFilterAttr = new String[]{"CHANNELNAME", "CHANNELNAME"};
    strFilterValue = new String[]{"369", "370"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
    if (vReturnEntities3.size()>0) {
      println(".*$P_14B = '1'");
    } else {
      println(".*$P_14B = '0'");
    }
    strFilterAttr = new String[]{"CHANNELNAME", "CHANNELNAME","CHANNELNAME"};
    strFilterValue = new String[]{"361", "362","363"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
    if (vReturnEntities3.size()>0) {
      println(".*$P_15A = '1'");
    } else {
      println(".*$P_15A = '0'");
    }
    strFilterAttr = new String[]{"CHANNELNAME"};
    strFilterValue = new String[]{"375"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
    if (vReturnEntities3.size()>0) {
      println(".*$P_15B = '1'");
    } else {
      println(".*$P_15B = '0'");
    }

    strFilterAttr = new String[]{"CHANNELNAME", "CHANNELNAME","CHANNELNAME"};
    strFilterValue = new String[]{"358", "359","360"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
    if (vReturnEntities3.size()>0) {
      eiChannel = (EntityItem)vReturnEntities3.elementAt(0);
      println(".*$P_15C = '1'");
    } else {
      println(".*$P_15C = '0'");
    }
    strFilterAttr = new String[]{"CHANNELNAME", "CHANNELNAME","CHANNELNAME"};
    strFilterValue = new String[]{"371", "372","373"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
    if (vReturnEntities3.size()>0) {
      println(".*$P_15D = '1'");
    } else {
      println(".*$P_15D = '0'");
    }

    strFilterAttr = new String[]{"CHANNELNAME"};
    strFilterValue = new String[]{"376"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
    if (vReturnEntities3.size()>0) {
      println(".*$P_15E = '1'");
    } else {
      println(".*$P_15E = '0'");
    }

    strFilterAttr = new String[]{"CHANNELNAME"};
    strFilterValue = new String[]{"377"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
    if (vReturnEntities3.size()>0) {
      println(".*$P_15F = '1'");
    } else {
      println(".*$P_15F = '0'");
    }

    strCondition1 = (grpAnnouncement != null ? getAttributeFlagEnabledValue(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "GENAREANAMEINCL", "") : "");

//    strSplit += (grpAnnouncement != null ? getAttributeValue(eiAnnounce, "GENAREANAMEEXCL", "") : "");
    //  strSplit = (strSplit.trim().length() >0 ? "1":"0");
    strCondition2 = "0";
    /*
    if 01 - Worldwide then set all to Yes
    if 02 - US then set 15G to Yes
    if 03 - Latin America then set 15K to Yes
    if 04 - Canada then set 15L to Yes
    if 05 - Europe then set 15H & 15I to Yes
    if 06 - Asia Pacific then set 15J to Yes
    */
    println(".*$P_15G = '" +(m_geList.isRfaGeoUS(eiAnnounce) ? "1" : "0")+ "'");
    println(".*$P_15H = '" +(m_geList.isRfaGeoEMEA(eiAnnounce) ? "1" : "0")+ "'");
    //For 15 check whether this belongs to EMEA and has 130 country instances.
    bConditionOK=false;
    j=0;

    GeneralAreaGroup geGrEmea = m_geList.getRfaGeoEMEAInclusion(eiAnnounce);
    logMessage("_15I returned GEItemcount"+geGrEmea.getGeneralAreaItemCount());
    if (geGrEmea.getGeneralAreaItemCount() >= 130)  {
      bConditionOK = true;
    }

    println(".*$P_15I = '" +(m_geList.isRfaGeoEMEA(eiAnnounce) && bConditionOK ? "1" : "0")+ "'");
    println(".*$P_15J = '" +(m_geList.isRfaGeoAP(eiAnnounce) ? "1" : "0")+ "'");
    println(".*$P_15K = '" +(m_geList.isRfaGeoLA(eiAnnounce) ? "1" : "0")+ "'");
    println(".*$P_15L = '" +(m_geList.isRfaGeoCAN(eiAnnounce) ? "1" : "0")+ "'");

    strCondition1 = getAttributeValue(eiAnnounce, "CROSSPLATFORM", "");
    println(".*$P_16A = '" + (strCondition1.indexOf("Yes")>-1 ? "1" : "0") + "'");

    println(".*$P_16B = '" + (flagvalueEquals(eiAnnounce, "PLATFORM","4767") ? "1" : "0") + "'");
    println(".*$P_16C = '" + (flagvalueEquals(eiAnnounce, "PLATFORM","4770") ? "1" : "0") + "'");
    println(".*$P_16D = '" + (flagvalueEquals(eiAnnounce, "PLATFORM","4769") ? "1" : "0") + "'");
    println(".*$P_16E = '" + (flagvalueEquals(eiAnnounce, "PLATFORM","4772") ? "1" : "0") + "'");
    println(".*$P_16F = '" + (flagvalueEquals(eiAnnounce, "PLATFORM","4764") ? "1" : "0") + "'");
    println(".*$P_16G = '" + (flagvalueEquals(eiAnnounce, "PLATFORM","4768") ? "1" : "0") + "'");
    println(".*$P_16H = '" + (flagvalueEquals(eiAnnounce, "PLATFORM","4766") ? "1" : "0") + "'");
    println(".*$P_16I = '" + (flagvalueEquals(eiAnnounce, "PLATFORM","4773") ? "1" : "0") + "'");

    println(".*$P_16J = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2907") ? "1" : "0") + "'");
    println(".*$P_16K = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2913") ? "1" : "0") + "'");

    println(".*$P_16L = '" + (flagvalueEquals(eiAnnounce, "PLATFORM","4771") ? "1" : "0") + "'");
    println(".*$P_16M = '" + (flagvalueEquals(eiAnnounce, "PLATFORM","4765") ? "1" : "0") + "'");

    println(".*$P_16N = '" + (flagvalueEquals(eiAnnounce, "ELECTRONICSERVICE","010") ? "1" : "0") + "'");
    println(".*$P_16O = '" + (flagvalueEquals(eiAnnounce, "ELECTRONICSERVICE","011") ? "1" : "0") + "'");

    println(".*$P_17A = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2892") ? "1" : "0") + "'");
    println(".*$P_17B = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2891") ? "1" : "0") + "'");
    println(".*$P_17C = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2895") ? "1" : "0") + "'");
    println(".*$P_17D = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2896") ? "1" : "0") + "'");
    println(".*$P_17E = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2897") ? "1" : "0") + "'");
    println(".*$P_17F = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2894") ? "1" : "0") + "'");
    println(".*$P_17G = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2893") ? "1" : "0") + "'");
    println(".*$P_17H = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2898") ? "1" : "0") + "'");
    println(".*$P_17I = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2899") ? "1" : "0") + "'");

    strCondition1 = (eiOrganUnit != null ? getAttributeValue(eiOrganUnit, "MNEMONIC", "") : "");
    println(".*$P_18A = '" + (strCondition1.indexOf("PCD") > -1 ? "1" : "0") + "'");

    strCondition1 = (eiPdsQuestions != null ? getAttributeFlagEnabledValue(eiPdsQuestions.getEntityType(), eiPdsQuestions.getEntityID(), "PCDCHANNELS", "") : "");
/*
    println(".*$P_18B = '" + (strCondition1.indexOf("4650") > -1 ? "1" : "0") + "'");
    println(".*$P_18C = '" + (strCondition1.indexOf("4649") > -1 ? "1" : "0") + "'");


    strCondition1 = (eiPdsQuestions != null ? getAttributeFlagEnabledValue(eiPdsQuestions.getEntityType(), eiPdsQuestions.getEntityID(), "PCDCHANNELS", "") : "");
    println(".*$P_19A = '" + (strCondition1.indexOf("4655") > -1 ? "1" : "0") + "'");  //Cable Distributor
    println(".*$P_19B = '" + (strCondition1.indexOf("4654") > -1 ? "1" : "0") + "'");   //Terminal Distributor
    println(".*$P_19C = '" + (strCondition1.indexOf("4653") > -1 ? "1" : "0") + "'");   //Industrial Distributor
    println(".*$P_19D = '" + (strCondition1.indexOf("4656") > -1 ? "1" : "0") + "'");   //IBM Retailer
    println(".*$P_19E = '" + (strCondition1.indexOf("4651") > -1 ? "1" : "0") + "'");   //IBM Retailer (Meets Comp)
    println(".*$P_19F = '" + (strCondition1.indexOf("4652") > -1 ? "1" : "0") + "'");   //IBM Retailer (Superstore)
    println(".*$P_19G = '" + (strCondition1.indexOf("4648") > -1 ? "1" : "0") + "'");   //Personal Computer Distributor
*/
    println(".*$P_18B = '0'");
    println(".*$P_18C = '0'");

    println(".*$P_18D = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2905") ? "1" : "0") + "'");
    println(".*$P_18E = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2914") ? "1" : "0") + "'");



    println(".*$P_19A = '0'");
    println(".*$P_19B = '0'");
    println(".*$P_19C = '0'");
    println(".*$P_19D = '0'");
    println(".*$P_19E = '0'");
    println(".*$P_19F = '0'");
    println(".*$P_19G = '0'");

    strCondition1 = (eiPriceInfo != null ? getAttributeValue(eiPriceInfo, "GRADUATEDCHARGES", "") : "");
    /*
    ANNOUNCEMENT (ANNCODENAME) = COMMERCIALOF (ANNCODETAG) when
    COFCAT = '100' (Hardware) and COFSUBCAT = '208' (System) and COFGRP = '301' (Base) and COFSUBGRP = '400' (iSeries)
    -> COFPRICE (R) -> PRICEFININFO (E)"
    */

    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};

    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");

    strFilterAttr = new String[]{"COFSUBGRP","COFSUBGRP"};
    strFilterValue = new String[]{"400","402"};
    vReturnEntities2 = searchEntityVector(vReturnEntities1, strFilterAttr, strFilterValue, false);
    logMessage("****COMMERCIALOF*****");
    displayContents(vReturnEntities2);
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "COFPRICE");
    logMessage("****COFPRICE*****");
    displayContents(vReturnEntities1);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "PRICEFININFO");
    logMessage("****PRICEFININFO*****");
    displayContents(vReturnEntities2);
    strParamList1 = new String [] {"GRADUATEDCHARGES"};
    printValueListInVector(vReturnEntities2, strParamList1," ",true ,false);

    println(".*$P_20A = '" + (vPrintDetails.size()>0 ? "1" : "0") + "'");
    resetPrintvars();

    println(".*$P_20B = '" + (eiCommOFIvo != null ? "1" : "0") + "'");


    if (flagvalueEquals(eiAnnounce.getEntityType(),eiAnnounce.getEntityID(),"PRODSTRUCTURE","5020") ||
        flagvalueEquals(eiAnnounce.getEntityType(),eiAnnounce.getEntityID(),"PRODSTRUCTURE","5021")
      ) {
      bConditionOK=true;
    }
    println(".*$P_20C = '" + (bConditionOK ? getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "PRODSTRUCTURE", "'") : "'")+"'");

    //ANNOUNCEMENT (ANNCODENAME) = AVAIL  (ANNCODENAME) -> OOFSUBCAT = 'FeatureConvert'
    vReturnEntities1 = searchEntityGroupLink( grpAnnAvail, null, null, true, true, "AVAIL");
    strFilterAttr = new String[] {"OOFSUBCAT"};
    strFilterValue = new String[] {"FeatureConvert"};
    vReturnEntities2 = searchEntityVectorLink( vReturnEntities1,null , null, true, false, "OOFAVAIL");
    vReturnEntities3 = searchEntityVectorLink( vReturnEntities2, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    if (vReturnEntities3.size()>0) {
      println(".*$P_20D = '1'");
    } else {
      println(".*$P_20D = '0'");
    }

    strFilterAttr = new String[] {"RETURNEDPARTS"};
    strFilterValue = new String[] {"5100"};
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    if (vReturnEntities1.size()>0) {
      println(".*$P_20E = '1'");
    } else {
      println(".*$P_20E = '0'");
    }


    //'If OFFERINGTYPES = '2892' or '2891' or '2896' or '2898' or '2909' or '2901' then 'Y'. Else, 'N'.
    strFilterAttr = new String[] {"OFFERINGTYPES","OFFERINGTYPES","OFFERINGTYPES","OFFERINGTYPES","OFFERINGTYPES","OFFERINGTYPES"};
    strFilterValue = new String[] {"2892","2891","2896","2898","2909","2901"};
    bConditionOK = false;
    if (foundInEntity(eiAnnounce,strFilterAttr,strFilterValue,false)) {
      bConditionOK = true;
    }
    println(".*$P_20F = '" + (bConditionOK ? "1" : "0") + "'");

    strCondition1 = getAttributeFlagEnabledValue(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "NEWMACHMODELTYPE", "");
    println(".*$P_21A = '" + (strCondition1.indexOf("2864") > -1 ? "1" : "0") + "'");
    println(".*$P_21B = '" + (strCondition1.indexOf("2866") > -1 ? "1" : "0") + "'");
    println(".*$P_21C = '" + (strCondition1.indexOf("2865") > -1 ? "1" : "0") + "'");

    //'ANNOUNCEMENT (ANNCODENAME) = AVAIL (ANNCODENAME) -> when ORDERSYSNAME = '4143' (AAS)
    strFilterAttr = new String[] {"ORDERSYSNAME"};
    strFilterValue = new String[] {"4143"};
    vReturnEntities1 = searchEntityGroupLink( grpAnnAvail, strFilterAttr, strFilterValue, true, true, "AVAIL");
    println(".*$P_21D = '" + (vReturnEntities1.size() > 0 ? "1" : "0") + "'");
    strCondition1 = getAttributeValue(eiAnnounce, "TAA", "");
    println(".*$P_21E = '" + (strCondition1.equalsIgnoreCase("Yes") ? "1" : "0") + "'");

    strCondition1 = getAttributeFlagEnabledValue(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "QUICKSHIPTYPE", "");
    println(".*$P_21F = '" + (strCondition1.indexOf("5031") > -1 ? "1" : "0") + "'");
    println(".*$P_21G = '" + (strCondition1.indexOf("5029") > -1 ? "1" : "0") + "'");
    println(".*$P_21H = '" + (strCondition1.indexOf("5030") > -1 ? "1" : "0") + "'");

    strCondition1 = getAttributeValue(eiAnnounce, "HWSWANN", "");
    println(".*$P_21I = '" + (strCondition1.equals("Yes") ? "1" : "0") + "'");

    print(".*$P_21J = ");

    strFilterAttr = new String[]{"STANDARDAMENDTEXT_TYPE"};
    strFilterValue = new String[]{"5532"};
    vReturnEntities1 = searchEntityGroup(grpStdAmendText, strFilterAttr, strFilterValue, true);
    if (vReturnEntities1.size()>0) {
      println("'1'");
    } else {
      println("'0'");
    }

    println(".*$P_22A = '" + (flagvalueEquals(eiAnnounce, "CONFIGSUPPORT","677") ? "1" : "0") + "'");
    println(".*$P_22B = '" + (flagvalueEquals(eiAnnounce, "CONFIGSUPPORT","675") ? "1" : "0") + "'");
    println(".*$P_22C = '" + (flagvalueEquals(eiAnnounce, "CONFIGSUPPORT","676") ? "1" : "0") + "'");


    /*
     *  Check for US configurator
     */
    bConditionOK = false;
    vReturnEntities2 = new Vector();
    for (i=0; i<grpAnnToConfig.getEntityItemCount() ; i++)  {
      eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
      eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
      if (m_geList.isRfaGeoUS(eiConfigurator))  {
logMessage("P_23A US Configurator"+    eiConfigurator.getEntityType()+":"+eiConfigurator.getEntityID());
        bConditionOK = true;
        vReturnEntities2.addElement(eiConfigurator);          //Store all instances of the configurator
      }
    }

     //'ANNOUNCEMENT (E) ->ANNTOCONFIG (R) -> CONFIGURATOR (E) -> CONFIGGAA (A) -> GENERALAREA (E) when RFAGEO = '200' (US)
   eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(0) : null);

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
   println(".*$P_23A = '" + strCondition2 + "'");

    strCondition2 = (bConditionOK ? " " +getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_23B = '" + strCondition2 + "'");

   if (vReturnEntities2.size() > 1 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(1) : null);
   } else {
     bConditionOK = false;
   }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_23C = '" + strCondition2 + "'");

    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_23D = '" + strCondition2 + "'");

   if (vReturnEntities2.size() > 2 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(2) : null);
   } else {
     bConditionOK = false;
   }
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_23E = '" + strCondition2 + "'");

    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_23F = '" + strCondition2 + "'");

   if (vReturnEntities2.size() > 3 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(3) : null);
   } else {
     bConditionOK = false;
   }
    //US configurator4
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_23G = '" + strCondition2 + "'");

    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_23H = '" + strCondition2 + "'");

   if (vReturnEntities2.size() > 4)  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(4) : null);
   } else {
     bConditionOK = false;
   }
    //US configurator5
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_23I = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_23J = '" + strCondition2 + "'");

    /*
     *  Check for AP configurator
     */
    bConditionOK = false;
    vReturnEntities2 = new Vector();
    for (i=0; i<grpAnnToConfig.getEntityItemCount() ; i++)  {;
      eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
      eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
logMessage("P_24A AP Configurator"+    eiConfigurator.getEntityType()+":"+eiConfigurator.getEntityID());
      if (m_geList.isRfaGeoAP(eiConfigurator))  {
        bConditionOK = true;
        vReturnEntities2.addElement(eiConfigurator);          //Store all instances of the configurator
      }
    }


    eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(0) : null);
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_24A = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_24B = '" + strCondition2 + "'");

   if (vReturnEntities2.size() > 1 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(1) : null);
   } else {
     bConditionOK = false;
   }
    //AP configurator2
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_24C = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
   println(".*$P_24D = '" + strCondition2 + "'");

   if (vReturnEntities2.size() > 2 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(2) : null);
   } else {
     bConditionOK = false;
   }
    //AP configurator3
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
   println(".*$P_24E = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_24F = '" + strCondition2 + "'");

   if (vReturnEntities2.size() > 3 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(3) : null);
   } else {
     bConditionOK = false;
   }
    //AP configurator4
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_24G = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_24H = '" + strCondition2 + "'");

   if (vReturnEntities2.size() > 4)  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(4) : null);
   } else {
     bConditionOK = false;
   }
    //AP configurator5
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
   println(".*$P_24I = '" + strCondition2 + "'");

    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_24J = '" + strCondition2 + "'");

    /*
     *  Check for LA configurator
     */
    bConditionOK = false;
    vReturnEntities2 = new Vector();
    for (i=0; i<grpAnnToConfig.getEntityItemCount() ; i++)  {
      eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
      eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
      if (m_geList.isRfaGeoLA(eiConfigurator))  {
logMessage("P_25A LA Configurator"+    eiConfigurator.getEntityType()+":"+eiConfigurator.getEntityID());
        bConditionOK = true;
        vReturnEntities2.addElement(eiConfigurator);          //Store all instances of the configurator
      }
    }

   eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(0) : null);
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_25A = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_25B = '" + strCondition2 + "'");

   if (vReturnEntities2.size() > 1 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(1) : null);
   } else {
     bConditionOK = false;
   }
    //LA configurator2
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_25C = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_25D = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 2 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(2) : null);
   } else {
     bConditionOK = false;
   }

    //LA configurator3
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_25E = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
   println(".*$P_25F = '" + strCondition2 + "'");

    //LA configurator4
   if (vReturnEntities2.size() > 3 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(3) : null);
   } else {
     bConditionOK = false;
   }
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_25G = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
   println(".*$P_25H = '" + strCondition2 + "'");

    //LA configurator5
   if (vReturnEntities2.size() > 4)  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(4) : null);

   } else {
     bConditionOK = false;
   }
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_25I = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_25J = '" + strCondition2 + "'");

    /*
     *  Check for CAN configurator
     */
    bConditionOK = false;
    vReturnEntities2 = new Vector();
    for (i=0; i<grpAnnToConfig.getEntityItemCount() ; i++)  {
      eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
      eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
      if (m_geList.isRfaGeoCAN(eiConfigurator))  {
logMessage("P_26A CAN Configurator"+    eiConfigurator.getEntityType()+":"+eiConfigurator.getEntityID());
        bConditionOK = true;
        vReturnEntities2.addElement(eiConfigurator);          //Store all instances of the configurator
      }
    }

   eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(0) : null);
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_26A = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_26B = '" + strCondition2 + "'");
    //CAN configurator2

    if (vReturnEntities2.size() > 1 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(1) : null);
   } else {
     bConditionOK = false;
   }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_26C = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_26D = '" + strCondition2 + "'");

    //CAN configurator3
   if (vReturnEntities2.size() > 2 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(2) : null);
   } else {
     bConditionOK = false;
   }
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_26E = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_26F = '" + strCondition2 + "'");

    //CAN configurator4
   if (vReturnEntities2.size() > 3 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(3) : null);
   } else {
     bConditionOK = false;
   }
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
   println(".*$P_26G = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_26H = '" + strCondition2 + "'");

    //CAN configurator5
   if (vReturnEntities2.size() > 4)  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(4) : null);
   } else {
     bConditionOK = false;
   }
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_26I = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_26J = '" + strCondition2 + "'");

    /*
     *  Check for EMEA configurator
     */
    bConditionOK = false;
    vReturnEntities2 = new Vector();
    for (i=0; i<grpAnnToConfig.getEntityItemCount() ; i++)  {
      eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
      eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
      if (m_geList.isRfaGeoEMEA(eiConfigurator))  {
        bConditionOK = true;
logMessage("P_27A EMEA Configurator"+    eiConfigurator.getEntityType()+":"+eiConfigurator.getEntityID());

        vReturnEntities2.addElement(eiConfigurator);          //Store all instances of the configurator
      }
    }

   eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(0) : null);
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_27A = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_27B = '" + strCondition2 + "'");

    //EMEA configurator2
   if (vReturnEntities2.size() > 1 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(1) : null);
   } else {
     bConditionOK = false;
   }
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_27C = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_27D = '" + strCondition2 + "'");

    //EMEA configurator3
   if (vReturnEntities2.size() > 2 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(2) : null);
   } else {
     bConditionOK = false;
   }
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_27E = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
   println(".*$P_27F = '" + strCondition2 + "'");

    //EMEA configurator4
   if (vReturnEntities2.size() > 3 )  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(3) : null);
   } else {
     bConditionOK = false;
   }
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_27G = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_27H = '" + strCondition2 + "'");

    //EMEA configurator5
   if (vReturnEntities2.size() > 4)  {
     eiConfigurator = (bConditionOK  ?  (EntityItem) vReturnEntities2.elementAt(4) : null);
   } else {
     bConditionOK = false;
   }
    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", "") : "");
    println(".*$P_27I = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_27J = '" + strCondition2 + "'");


    strCondition1 = getAttributeValue(eiAnnounce, "COMPLEMENTARTBP", "");
    println(".*$P_32A = '" + (strCondition1.equals("Yes") ? "1" : "0") + "'");

    //Print 32B only if 32A is yes....check in multiple instances
    //tbD..Ask Alan...if there are multiple PRICEFININFO instances, then how to print?
    //'ANNOUNCEMENT (ANNCODENAME) = COMMERCIALOF (ANNCODENAME) when COFCAT = '100' and COFSUBCAT = '208' and COFGRP = '301' and COFSUBGRP = '400' -> COFPRICE (R) -> PRICEFININFO (E)

    if (strCondition1.equals("Yes"))  {

      strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
      strFilterValue = new String[]{"100", "208", "301"};

      vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");

      strFilterAttr = new String[]{"COFSUBGRP","COFSUBGRP"};
      strFilterValue = new String[]{"400","402"};
      vReturnEntities2 = searchEntityVector(vReturnEntities1, strFilterAttr, strFilterValue, false);

      logMessage("****COMMERCIALOF*****");
      displayContents(vReturnEntities2);
      vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "COFPRICE");
      logMessage("****COFPRICE*****");
      displayContents(vReturnEntities1);
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "PRICEFININFO");
      logMessage("****PRICEFININFO*****");
      displayContents(vReturnEntities2);
      strCondition1 = "";
      //Get the first populated COMPLEMENTARTBPFEE to print from all the priceinfo instances
      for (i=0;i<vReturnEntities2.size();i++) {
        eiPriceInfo = (EntityItem) vReturnEntities2.elementAt(i);
        strCondition1 = getAttributeValue(eiPriceInfo, "COMPLEMENTARTBPFEE", "");
        if (strCondition1.trim().length() > 0 )  {
          break;
        }
      }
    } else {
      strCondition1 = "0";
    }
    println(".*$P_32B = '" + strCondition1+"'");
/*
    strCondition1 = (eiPdsQuestions != null ? getAttributeValue(eiPdsQuestions.getEntityType(), eiPdsQuestions.getEntityID(), "CTOANNTYPE", "") : "");
    println(".*$P_33A = '" + (strCondition1.equals("Initial CTO Structure") ? "1" : "0") + "'");
    println(".*$P_33B = '" + (strCondition1.equals("CTO Building Block") ? "1" : "0") + "'");
    println(".*$P_33C = '" + (strCondition1.equals("Change to CTO Structure") ? "1" : "0") + "'");
    strCondition1 = (eiPdsQuestions != null ? getAttributeValue(eiPdsQuestions.getEntityType(), eiPdsQuestions.getEntityID(), "CTODELIVERABLES", "") : "");
    println(".*$P_33D = '" + (strCondition1.equals("Worldwide Customer Letter") ? "1" : "0") + "'");
    println(".*$P_33E = '" + (strCondition1.equals("Personal Computing Division letter") ? "1" : "0") + "'");
    println(".*$P_33F = '" + (strCondition1.equals("Business Partner Attachment") ? "1" : "0") + "'");
    strCondition1 = (eiPdsQuestions != null ? getAttributeValue(eiPdsQuestions.getEntityType(), eiPdsQuestions.getEntityID(), "CTOOFODYSSEY", "") : "");
    println(".*$P_33G = '" + (strCondition1.equals("Yes") ? "1" : "0") + "'");
*/
    println(".*$P_33A = '0'");
    println(".*$P_33B = '0'");
    println(".*$P_33C = '0'");
    println(".*$P_33D = '0'");
    println(".*$P_33E = '0'");
    println(".*$P_33F = '0'");
    println(".*$P_33G = '0'");
    println(".*$P_33H = '0'");
    println(".*$P_33I = '0'");
    strCondition1 = getAttributeValue(eiAnnounce, "PRICESLISTEDINANNLETTER", "");
    println(".*$P_34A = '" + (strCondition1.equals("Yes") ? "1" : "0") + "'");
    println(".*$P_35A = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2895") ? "1" : "0") + "'");
    strCondition1 = getAttributeValue(eiAnnounce, "OFFERINGACCESS", "");
    println(".*$P_51A = '" + (strCondition1.equals("Yes") ? "1" : "0") + "'");
    strCondition1 = getAttributeValue(eiAnnounce, "MARKETEDIBMLOGO", "");
    println(".*$P_51B = '" + (strCondition1.equals("Yes") ? "1" : "0") + "'");
    println(".*$P_51C = '" + (flagvalueEquals(eiAnnounce, "LOGOACCESSREQTS","2833") ? "1" : "0") + "'");
    println(".*$P_51D = '" + (flagvalueEquals(eiAnnounce, "LOGOACCESSREQTS","2834") ? "1" : "0") + "'");

    strCondition1 = getAttributeValue(eiAnnounce, "PRELOADEDSW", "");
    println(".*$P_52B = '" + (strCondition1.equals("Yes") ? "1" : "0") + "'");

    println(".*$P_54A = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2891") ? "1" : "0") + "'");
    println(".*$P_54B = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2902") ? "1" : "0") + "'");
    println(".*$P_54C = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2903") ? "1" : "0") + "'");
    println(".*$P_54D = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2904") ? "1" : "0") + "'");
    println(".*$P_54E = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2893") ? "1" : "0") + "'");
    println(".*$P_54F = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2910") ? "1" : "0") + "'");
    println(".*$P_54G = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2911") ? "1" : "0") + "'");
    println(".*$P_54H = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2906") ? "1" : "0") + "'");
    println(".*$P_54I = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2908") ? "1" : "0") + "'");
    println(".*$P_54J = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2909") ? "1" : "0") + "'");
    println(".*$P_54K = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2985") ? "1" : "0") + "'");
    println(".*$P_54L = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2892") ? "1" : "0") + "'");
    println(".*$P_54M = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2896") ? "1" : "0") + "'");
    println(".*$P_54N = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2898") ? "1" : "0") + "'");
    println(".*$P_54O = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2899") ? "1" : "0") + "'");
    println(".*$P_54P = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2912") ? "1" : "0") + "'");
    println(".*$P_54Q = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2901") ? "1" : "0") + "'");
    println(".*$P_54R = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES","2900") ? "1" : "0") + "'");
    strCondition1 = getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "TGTCUSTOMERAUD", "");

    println(".*$P_58A = '" + (strCondition1.trim().length() > 61 ? strCondition1.substring(0,61) : strCondition1) + "'");
    println(".*$VARIABLES_END");
  }


  /**
   *  Description of the Method
   */
  private void processLongTo100() {
    println(".*$A_001_Begin");
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    resetPrintvars();
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      vPrintDetails.add(getAttributeValue(eiCommOF, "COMNAME", " "));
    }
    strHeader = new String[]{"Announced Product Names"};
    iColWidths = new int[]{55};
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_001_End");

    println(".*$A_002_Begin");
    println(".*$A_002_End");
    println(".*$A_007_Begin");
    println(getAttributeValue(eiAnnounce, "ANNIMAGES", " "));
    println(".*$A_007_End");
    println(".*$A_040_Begin");
    println(":xmp.");
    println(".kp off");
    strHeader = new String[]{"Role Type", "Name", "Telephone", "Node/ID"};
    iColWidths = new int[]{15, 18, 12, 17 };
    strFilterAttr = new String [] {"ANNROLETYPE","ANNROLETYPE","ANNROLETYPE","ANNROLETYPE"};
    strFilterValue = new String [] {"11","12","13","14"};
    vReturnEntities1 = searchEntityGroup(grpAnnToOP, strFilterAttr, strFilterValue, false);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiAnnToOP = (EntityItem) vReturnEntities1.elementAt(i);
      eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
      strCondition1 = getAttributeValue(eiAnnToOP.getEntityType(), eiAnnToOP.getEntityID(), "ANNROLETYPE", " ");
      vPrintDetails.add(strCondition1.length()>=14 ?  strCondition1.substring(0,14) : strCondition1.substring(0,strCondition1.length()));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0,1)+". ";
      strCondition1+=getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ")+ "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }
    if (vPrintDetails.size()>0) {
      println("This RFA and its requested schedule hae been reviewed with the");
      println("following functional representatives");
      println(":xmp.");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_040_End");

    println(".*$A_042_Begin");
    println(":xmp.");
    println(".in 0");
    println(".kp off");
    vPrintDetails1 = new Vector();
    for (i = 0; i < grpAnnToOrgUnit.getEntityItemCount(); i++) {
      eiAnnToOrgUnit = grpAnnToOrgUnit.getEntityItem(i);
      eiOrganUnit = (EntityItem) eiAnnToOrgUnit.getDownLink(0);
      vPrintDetails.add(getAttributeValue(eiAnnToOrgUnit.getEntityType(), eiAnnToOrgUnit.getEntityID(), "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiAnnToOrgUnit.getEntityType(), eiAnnToOrgUnit.getEntityID(), "POLICYHITNAME", " ")+" ";
      strCondition1 += "/"+getAttributeValue(eiAnnToOrgUnit.getEntityType(), eiAnnToOrgUnit.getEntityID(), "PRODHITNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiAnnToOrgUnit.getEntityType(), eiAnnToOrgUnit.getEntityID(), "DEPENDENCYTYPE", " "));
    }
    strHeader = new String[]{"Organization", "Policy/Product Hit Name","Dependency Type"};
    iColWidths = new int[]{27,23,15};

    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();

    println(":exmp.");
    println(".*$A_042_End");

    /*
     *  Get the RElated announcement details...
     *  navigate via the relator RELATEDANN
     */
    println(".*$A_044_Begin");
    println(":xmp.");
    println(".in 0");
    println(".kp off");
    for (i = 0; i < grpRelatedANN.getEntityItemCount(); i++) {
      eiRelatedANN = grpRelatedANN.getEntityItem(i);
      //Now get the downlinked announcement item
      for (j = 0; j < eiRelatedANN.getDownLinkCount(); j++) {
        eiNextItem = (EntityItem) eiRelatedANN.getDownLink(j);
        logMessage("**********_044 next item" + eiNextItem.getEntityType() + eiNextItem.getEntityID());
        vPrintDetails.add(getAttributeValue(eiNextItem, "ANNTITLE"));
        vPrintDetails1.add(getAttributeValue(eiNextItem, "ANNNUMBER"));
      }
    }
    strHeader = new String[]{"Announcement Title"};
    iColWidths = new int[]{69};
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();

    strHeader = new String[]{"Announcement Number"};
    iColWidths = new int[]{19};
    printReport(true, strHeader, iColWidths, vPrintDetails1);
    if (vPrintDetails1.size() > 0) {
      vPrintDetails1.removeAllElements();
    }
    println(":exmp.");
    println(".*$A_044_End");

    /*
     *  Print OP details when ANNROLETYPE = 'Product Development Mgr' on relator ANNOP
     */
    println(".*$A_045_Begin");
    println(":xmp.");
    println(".in 0");
    println(":hp2.Being released to::ehp2.");
    println(".kp off");
    //'ANNOUNCEMENT( E) -> ANNCOFA (A) ->COMMERCIALOF (E) with classification 'Hardware-System-Base-% -> COFOP (R)
    // when OFINDVTYPE = '2918' (Product Administrator) and RFAGEO = '200' (US) -> OP (E)
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true,true,"COMMERCIALOF");

    strFilterAttr = new String[]{"OFINDVTYPE"};
    strFilterValue = new String[]{"2918"};
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, strFilterAttr, strFilterValue, true,true,"COFOP");

    //Get the entities which belong to US geo
    vReturnEntities1 = searchInGeo(vReturnEntities2,"US");
    resetPrintvars();
    for (i=0;i<vReturnEntities1.size();i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i);
      eiOP = (EntityItem) eiNextItem.getDownLink(0);
      vPrintDetails.add(getAttributeValue(eiNextItem.getEntityType(), eiNextItem.getEntityID(), "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0,1)+". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ")+ "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

        //Get the entities which belong to EMEA geo
    vReturnEntities1 = searchInGeo(vReturnEntities2,"EMEA");
    for (i=0;i<vReturnEntities1.size();i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i);
      eiOP = (EntityItem) eiNextItem.getDownLink(0);
      vPrintDetails.add(getAttributeValue(eiNextItem.getEntityType(), eiNextItem.getEntityID(), "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0,1)+". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ")+ "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

        //Get the entities which belong to AP geo
    vReturnEntities1 = searchInGeo(vReturnEntities2,"AP");
    for (i=0;i<vReturnEntities1.size();i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i);
      eiOP = (EntityItem) eiNextItem.getDownLink(0);
      vPrintDetails.add(getAttributeValue(eiNextItem.getEntityType(), eiNextItem.getEntityID(), "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0,1)+". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ")+ "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

        //Get the entities which belong to LA geo
    vReturnEntities1 = searchInGeo(vReturnEntities2,"LA");
    for (i=0;i<vReturnEntities1.size();i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i);
      eiOP = (EntityItem) eiNextItem.getDownLink(0);
      vPrintDetails.add(getAttributeValue(eiNextItem.getEntityType(), eiNextItem.getEntityID(), "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0,1)+". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ")+ "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

        //Get the entities which belong to CA geo
    vReturnEntities1 = searchInGeo(vReturnEntities2,"CA");
    for (i=0;i<vReturnEntities1.size();i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i);
      eiOP = (EntityItem) eiNextItem.getDownLink(0);
      vPrintDetails.add(getAttributeValue(eiNextItem.getEntityType(), eiNextItem.getEntityID(), "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0,1)+". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ")+ "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

    strHeader = new String[]{"Geography","Product Administrator", " Telephone", "    Node/Userid"};
    iColWidths = new int[]{10,21, 12, 17};
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();

    println(":exmp.");
    println(".*$A_045_End");

    println(".*$A_046_Begin");
    println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "TELECOMMEQ", " "));
    println(".*$A_046_End");

    /*
     *  Print OP details when ANNROLETYPE = 'Product Development Mgr' on relator ANNOP
     */
    println(".*$A_049_Begin");
    println(":xmp.");
    println(".kp off");
    strFilterAttr = new String[] {"ORGANUNITTYPE","ORGANUNITTYPE"};
    strFilterValue = new String[] {"4156","4157"};
    strCondition4 = "";
    vReturnEntities1 = searchEntityGroup(grpOrganUnit, strFilterAttr, strFilterValue, false);
    if (vReturnEntities1.size()>0)  {
      eiOrganUnit = (EntityItem) vReturnEntities1.elementAt(0);
      strCondition4 = getAttributeValue(eiOrganUnit, "NAME", " ");
    }

    eiAnnToOP = null;
    eiOP = null;
    strFilterAttr = new String[]{"ANNROLETYPE"};
    strFilterValue = new String[]{"5"}; //"Releasing Executive"
    vReturnEntities1 = searchEntityGroup(grpAnnToOP, strFilterAttr, strFilterValue, true);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true,true,"OP");

    for (i=0;i<vReturnEntities2.size();i++) {
      eiOP = (EntityItem) vReturnEntities2.elementAt(i);
      strCondition2 = getAttributeValue(eiOP, "FIRSTNAME", " ");
      strCondition1 = getAttributeValue(eiOP, "MIDDLENAME", " ");
      strCondition2 += strCondition1.equals(" ") ? "" : " "+strCondition1+".";
      strCondition1 = getAttributeValue(eiOP, "LASTNAME", " ");
      strCondition2 += strCondition1.equals(" ") ? "" : " "+strCondition1;
      println("                     "+strCondition2);
      println("                     "+getAttributeValue(eiOP, "JOBTITLE", " "));
      println("                     "+strCondition4);      //orgunit Name
    }
/*     strParamList1 = new String[]{"FIRSTNAME","MIDDLENAME","LASTNAME", "JOBTITLE"};
    printValueListInVector(vReturnEntities2, strParamList1, " ", false, false);
    iColWidths = new int[]{10,10,10, 15};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
 */    println(":exmp.");
    println(".*$A_049_End");

    println(".*$A_053_Begin");
    prettyPrint(getAttributeValue(eiAnnounce, "ANNTITLE", " "), 69);
    println(".*$A_053_End");

    println(".*$A_056_Begin");
    strFilterAttr = new String[]{"AVAILTYPE"};
    strFilterValue = new String[]{"146"}; //"Planned availability
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, strFilterAttr, strFilterValue, true,true,"AVAIL");
    vReturnEntities2= searchEntityVectorLink(vReturnEntities1, null, null, true, false, "OOFAVAIL");


    strCondition2="";
    vReturnEntities4.removeAllElements();
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities2.elementAt(i);//this is oofAVAIL
      eiOrderOF = (EntityItem) eiNextItem.getUpLink(0);
      eiAvail = (EntityItem) eiNextItem.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);
      strCondition4 =getAttributeValue(eiAvail, "EFFECTIVEDATE", " ") ;
      strCondition3 = getAttributeValue(eiOrderOF, "FEATURECODE", " ");
      strCondition2 = getAttributeValue(eiOrderOF, "INVNAME", " ");
      vReturnEntities4.add(strCondition4+"|"+strCondition3+"|"+strCondition2+"|"+strCondition1);
    }
    Object strAnswers[] = null;
    strAnswers = vReturnEntities4.toArray();      //Convert back into array
    Arrays.sort(strAnswers);

    resetPrintvars();
    strCondition7="";
    strCondition5="";
    for (i=0;i<strAnswers.length;i++) {
      strCondition1 = (String) strAnswers[i];
logMessage("String stored at "+i+":"+ strCondition1);
      iTemp = strCondition1.indexOf("|");
      strCondition2 = strCondition1.substring(0,iTemp);
      strCondition1 = strCondition1.substring(iTemp+1);
logMessage("Found | at   "+iTemp);
logMessage("Parsed Date  "+strCondition2);
      iTemp = strCondition1.indexOf("|");
      strCondition3 = strCondition1.substring(0,iTemp);
      strCondition1 = strCondition1.substring(iTemp+1);
logMessage("Found | at   "+iTemp);
logMessage("Parsed  Featurecode "+strCondition3);

      iTemp = strCondition1.indexOf("|");
      strCondition6 = strCondition1.substring(0,iTemp);
      strCondition1 = strCondition1.substring(iTemp+1);
logMessage("Found | at   "+iTemp);
logMessage("Parsed  Invname "+strCondition6);

      strCondition4 = strCondition1;
logMessage("Parsed GEO  "+strCondition4);
      if (!strCondition4.equals(strCondition5)) {
        if (!strCondition5.equals(strWorldwideTag)) {
          if (strCondition5.trim().length() > 0) {
logMessage("Ending GEO Break  "+strCondition5);
            vPrintDetails.add("9999"+BREAK_INDICATOR + ".br;:hp2.<---" + strCondition5 + ":ehp2.");
            vPrintDetails.add("");
            vPrintDetails.add("");
          }
        }
        if (!strCondition4.equals(strWorldwideTag)) {
          if (strCondition4.trim().length() > 0) {
logMessage("Starting GEO Break  "+strCondition4);
            vPrintDetails.add("    "+BREAK_INDICATOR + ":p.:hp2." + strCondition4 + "--->:ehp2.");
            vPrintDetails.add("");
            vPrintDetails.add("");
          }
        }
        strCondition5 = strCondition4;
      }

      if (!strCondition7.equals(strCondition2)) {         //Date break
        vPrintDetails.add(strCondition2);
        strCondition7=strCondition2;
      } else {
        vPrintDetails.add("");
      }
      vPrintDetails.add(strCondition3);
      vPrintDetails.add(strCondition6);


    }
    if (!strCondition5.equals(strWorldwideTag)) {
      if (strCondition5.trim().length() > 0) {
logMessage("Final Ending GEO Break  "+strCondition5);
        vPrintDetails.add("9999"+BREAK_INDICATOR + ".br;:hp2.<---" + strCondition5 + ":ehp2.");
        vPrintDetails.add("");
        vPrintDetails.add("");
      }
    }

    if (vPrintDetails.size() > 0) {
      strHeader = new String[]{"Plan Avail", "Feature Code","Description"};
      iColWidths = new int[]{10,17, 28};
      println(":hp2.Planned Availability Date::ehp2.");  ////As per Mike Slocum
      println(":xmp.");
      println(".kp off");
      rfaReport.setPrintDupeLines(false);
      printReport(true, strHeader, iColWidths, vPrintDetails);
      rfaReport.setPrintDupeLines(true);
      resetPrintvars();
      println(":exmp.");
    }

    logMessage("A_056_End");
    println(":exmp.");

    println(".*$A_056_End");

    println(".*$A_057_Begin");
    //New Models
    /*
     *  ANNOUNCEMENT->AVAIL->COMMERCIALOF
     *  1) with Hardware-System-Base)->COFOWNSOOFOMG ->COFOOFMGMTGRP->ORDEROF(with Hardware-Model) and
     *  Retrieve MODEL from the ORDEROF
     */
    resetPrintvars();
    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT"};
    strFilterValue = new String[]{"Hardware", "Model"};     //Hardware-Model
    vReturnEntities2 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    logMessage("****ORDEROF*****");
    displayContents(vReturnEntities2);
    strCondition2 = "";
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities2.elementAt(i);
      vPrintDetails.add(getAttributeValue(eiOrderOF, "INVNAME", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MACHTYPE", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MODEL", " "));
      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      vPrintDetails.add((strCondition1.equals("CIF")) ? "Y" : "N");
    }
    iColWidths = new int[]{30,11, 6,3};
    iSortCols = new int [] {2};     //Sort on Model
    //println(":h2.Product Number");          //As per Mike Slocum
    println(":h3.Models");
    println(":xmp.");
    println(".kp off");
    println("                                Type        Model");
    strHeader = new String[]{" Description", "Number", "Number","CSU"};
    printReport(true, strHeader, iColWidths, vPrintDetails,iSortCols);
    resetPrintvars();
    println(":exmp.");
    println(":p.");

    /*
     *  Priced features
     */
    /*
     *  ANNOUNCEMENT->AVAIL->COMMERCIALOF
     *  1) with Hardware-System-)->COFOWNSOOFOMG ->COFOOFMGMTGRP->ORDEROF(with Hardware) and
     *  Charge Fee = Priced Retrieve MODEL from the ORDEROF
     * First get existing price features so that it be eliminated from the New features
     */

    /*********************
     * Spec for existing priced features
     ************************************
      SOLUTION (reviewed by Kathy Fell on 4/16):

      The recommended solution is to create an extract with FUP as the root entity to extract entities
      and its attributes with the following navigation:

      Source ORDEROF => FUP => Qualified ORDEROF => AVAIL => ANNOUNCEMENT (with status not 'Cancelled')

      Qualified ORDEROF should have the following attribute values:

      Machine Type = Machine Type in Source ORDEROF
      Feature Code = Feature Code in Source ORDEROF
      Status = 'Final'

      Qualified AVAIL should have the following attribute values:

      AVAILTYPE = Planned Availability
      ANNCODETAG should contain ANNCODENAME different from the root Announcement
      Qualified AVAIL instance should contain the ANNCODENAME value which is different from ANNCODENAME in the root
      Announcement.

      Qualified ANNOUNCEMENT instances should contain different ANNCODENAME from the root Announcement and status
      not 'Canceled'.

      Compare the announcement date of the qualified Announcement to the announcement date of the root Announcement.
      Only if the date in one of the qualified Announcement is earlier than the root Announcement,
      the feature code is treated as a previous announced one.  In all other cases, the feature code is treated as
      a new feature code.

    */


    //Step1: Get all ORDEROF'S
    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT","CHARGEFEE"};
    strFilterValue = new String[]{"Hardware", "FeatureCode","Yes"};      //Hardware,
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    //This is the source Orderof.. Store the Machine Type and Feature Codes for checking later on
    logMessage("_057 Existing Features...Source ORDEROF");
    displayContents(vReturnEntities1);

    //Step 2, get to the FUP'S from here

    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "OOFFUP");
    logMessage("_057 Existing Features...Source OOFFUP");
    displayContents(vReturnEntities2);
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "FUP");
    logMessage("_057 Existing Features...Source FUP");
    displayContents(vReturnEntities3);

    /* Extract using the list of FUP's
     */
    try {
      //Create the entityItem array
      EntityItem[] eiAllFUP = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, vReturnEntities3.size());

      //Set the values of the array from the vector
      for (i=0;i<vReturnEntities3.size();i++) {
        eiAllFUP[i] = (EntityItem) vReturnEntities3.elementAt(i);
      }
      ExtractActionItem eaFupItem = new ExtractActionItem(null, getDatabase(), getProfile(), "EXTRFA02");
      logMessage("Creating Entity List for EXTRFA02");
      logMessage("Profile is " + getProfile());
      logMessage("Extractaction Item is" + eaFupItem);
      logMessage("Entity Item" + eiAllFUP);
      EntityList eListFUP = m_db.getEntityList(getProfile(), eaFupItem, eiAllFUP);
      logMessage("***************FUP Entitylist");
      logMessage(eListFUP.dump(false));
      logMessage("***************FUP Entitylist END" );

      grpOrderOF2 = eListFUP.getEntityGroup("ORDEROF");
      eiOrderOF = null;
      if (grpOrderOF2 != null) {
        eiOrderOF = grpOrderOF2.getEntityItem(0);
      } else {
        logMessage("**************ORDEROF not found in FUP Entitylist**");
      }

    } catch (Exception ee)  {
      ee.printStackTrace();
    }


    //Get the ANNCODENAME and Announcement date of the root announcement
    strCondition1 = getAttributeValue(eiAnnounce,"ANNCODENAME");
    strCondition5 = getAttributeValue(eiAnnounce, "ANNDATE", "");

    hNoDupeLines = new Hashtable();
    vReturnEntities4 = new Vector();
    for (i=0; i<vReturnEntities1.size(); i++) {
      eiOrderOF= (EntityItem) vReturnEntities1.elementAt(i);
      logMessage("_057 Existing Features..matching FEATCODE MACHTYPE OFSTATUS of "+eiOrderOF.getKey());
      strFilterAttr = new String[] {"FEATURECODE", "MACHTYPE","OFSTATUS"};
      strFilterValue = new String[] {getAttributeValue(eiOrderOF, "FEATURECODE", " "),getAttributeValue(eiOrderOF, "MACHTYPE", " "),"112"};

      //Search for the matching featurecodes
      vReturnEntities3 = searchEntityGroup(grpOrderOF2,strFilterAttr,strFilterValue,true);
      logMessage("_057 Existing Features....Found match for "+eiOrderOF.getKey());
      displayContents(vReturnEntities3);

      vReturnEntities2 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "OOFAVAIL");
      logMessage("_057 Existing Features...Target OOFAVAIL");
      displayContents(vReturnEntities2);


      /* We will navigate to ANNOUNCEMENT from OOFAVAIL and check the following things
      * AVAIL has to be planned availablity
      * linked announcement from avail has to have a diff ANNCODENAME than root announcement
      * linked announcement status should not be cancelled..
      * if all above conditions are true, then get the ORDEROF details for printing
      */
      strParamList1 = new String []{"ANNAVAILA","ANNOUNCEMENT"};

      for (j=0;j<  vReturnEntities2.size();j++) {
        eiOrderOFAvail = (EntityItem) vReturnEntities2.elementAt(j);
        eiNextItem = (EntityItem) eiOrderOFAvail.getUpLink(0);
        logMessage("_057 Existing Features Qualifying ORDEROF"+eiNextItem.getKey());
        eiAvail = (EntityItem) eiOrderOFAvail.getDownLink(0);
        strCondition2 = getAttributeFlagEnabledValue(eiAvail,"AVAILTYPE").trim();

        if (strCondition2.equals("146")) {
          //get the linked ANNOUNCEMENT from the qualifying AVAIL
          eiNextItem2 = getUplinkedEntityItem(eiAvail, strParamList1);
          logMessage("_057 Existing Features linked ANNOUNCEMENT"+eiNextItem2.getKey());

          strCondition3 = getAttributeValue(eiNextItem2,"ANNCODENAME");
          logMessage("_057 Existing Features linked ANNOUNCEMENT ANNCODENAME"+strCondition3);

          strCondition4 = getAttributeValue(eiNextItem2,"ANCYCLESTATUS");
          logMessage("_057 Existing Features linked ANNOUNCEMENT ANCYCLESTATUS"+strCondition4);

          strCondition6 = getAttributeValue(eiNextItem2, "ANNDATE", "");
          logMessage("_057 Existing Features linked ANNOUNCEMENT ANNDATE"+strCondition6);

          strCondition4 = strCondition4.substring(strCondition4.indexOf("|")==-1 ? 0 :strCondition4.indexOf("|")+1);   //Read after the "|"
          if (!strCondition3.equals(strCondition1) &&
              !strCondition4.equals("122")  &&
               strCondition5.compareTo(strCondition6)>0 )  {    // use only if root announcement date > old announcement
                 vReturnEntities4.addElement(eiOrderOF);   //Store this guy
                 hNoDupeLines.put(eiOrderOF.getKey(),"Announced Offering");
          }

        } else {
        }
      }


    }




    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT","CHARGEFEE"};
    strFilterValue = new String[]{"Hardware", "FeatureCode","Yes"};      //Hardware,
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
logMessage("_057***********");
displayContents(vReturnEntities1);
    strCondition2="";
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      //Check whether this has been announced before
      if (hNoDupeLines.contains(eiOrderOF.getKey())) {   //We found an announced offering, so skip it
        logMessage("New Priced Features: skipping previously announced offering :"+eiOrderOF.getKey());
        continue;
      }
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strWorldwideTag)) {
        if (!strCondition1.equals(strCondition2)) {
          if (strCondition2.trim().length()>0)  {
            vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", " ")+"999999999999999");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
          }
          vPrintDetails.add(BREAK_INDICATOR+":p.:hp2."+strCondition1+"--->:ehp2.");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", " "));     //We are sortng on this column
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          strCondition2 = strCondition1;
        }
      }
      vPrintDetails.add(getAttributeValue(eiOrderOF, "INVNAME", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MACHTYPE", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MODEL", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "ORDERCODE", " "));
      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      vPrintDetails.add((strCondition1.equals("CIF")) ? "Y" : "N");
      vPrintDetails.add(getAttributeValue(eiOrderOF, "RETURNEDPARTS", " "));
    }
    if (!strCondition1.equals(strWorldwideTag)) {
      if (strCondition2.trim().length()>0)  {
        vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
        vPrintDetails.add(" ");
        vPrintDetails.add(" ");
        vPrintDetails.add("999999999999999");
        vPrintDetails.add(" ");
        vPrintDetails.add(" ");
        vPrintDetails.add(" ");
      }
    }

    if (vPrintDetails.size()>0) {
      println(":h3.New Priced Features");
      println(":xmp.");
      println(".kp off");
      println("                                                     Order     Parts");
    }
    strHeader = new String[]{"Description", "Type", "Model", "Feature", "Code", "CSU","Return"};
    iColWidths = new int[]{28, 9 , 5, 7, 5, 3,6};
    iSortCols = new int [] {3};     //Sort on Feature
    printReport(true, strHeader, iColWidths, vPrintDetails,iSortCols);
    if (vPrintDetails.size()>0) {
      println("Order Codes:");
      println("");
      println("         B - Available on both initial and field upgrade orders");
      println("         M - Available on field upgrade (MES) orders only");
      println("         P - Available on initial orders from the plant only");
      println("         S - Supported for migration only and cannot be ordered");
      println("");
      println(":exmp.");
      println(":p.:hp2.US, LA, CAN--->:ehp2.");
      println(":p.:hp2.Feature Removal Prices::ehp2.  Feature removals not associated");
      println("with MES upgrades are available for a charge.");
      println(".br;:hp2.<---US, LA, CAN:ehp2.");
      println(":p.");
    }
    resetPrintvars();



    //ANNOUNCEMENT->AVAIL->ORDEROF->FUP->ORDEROF->AVAIL->ANNOUNCEMENT
    strHeader = new String[]{"Description", "Type", "Model", "Feature", "Code", "CSU","Return"};
    iColWidths = new int[]{28, 9 , 5, 7, 5, 3,6};


    //Now print the stuff
    strCondition2="";
    for (i = 0; i < vReturnEntities4.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities4.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strWorldwideTag)) {
        if (!strCondition1.equals(strCondition2)) {
          if (strCondition2.trim().length()>0)  {
            vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", " ")+"999999999999999");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
          }
          vPrintDetails.add(BREAK_INDICATOR+":p.:hp2."+strCondition1+"--->:ehp2.");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", " "));     //We are sortng on this column
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          strCondition2 = strCondition1;
        }
      }
      vPrintDetails.add(getAttributeValue(eiOrderOF, "INVNAME", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MACHTYPE", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MODEL", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "ORDERCODE", " "));
      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      vPrintDetails.add((strCondition1.equals("CIF")) ? "Y" : "N");
      vPrintDetails.add(getAttributeValue(eiOrderOF, "RETURNEDPARTS", " "));
    }
    if (!strCondition2.equals(strWorldwideTag)) {
      if (strCondition2.trim().length()>0)  {
        vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
        vPrintDetails.add(" ");
        vPrintDetails.add(" ");
        vPrintDetails.add("999999999999999");
        vPrintDetails.add(" ");
        vPrintDetails.add(" ");
        vPrintDetails.add(" ");
      }
    }
    if (vPrintDetails.size()>0) {
      println(":h3.Existing Priced Features");
      println(":xmp.");
      println(".kp off");
      println("                                                     Order     Parts");
    }
    iSortCols = new int [] {3};     //Sort on Feature
    printReport(true, strHeader, iColWidths, vPrintDetails,iSortCols);
    if (vPrintDetails.size()>0) {
      println("Order Codes:");
      println("");
      println("         B - Available on both initial and field upgrade orders");
      println("         M - Available on field upgrade (MES) orders only");
      println("         P - Available on initial orders from the plant only");
      println("         S - Supported for migration only and cannot be ordered");
      println("");
      println(":exmp.");
      resetPrintvars();
    }


    //Get the associated announcements from the
    /******************************************************************
     *   ....Print for Non Priced Features
     */
    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT","CHARGEFEE"};
    strFilterValue = new String[]{"Hardware", "FeatureCode","No"};      //Hardware,
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    logMessage("_057 No Charge Orderofferings");
    displayContents(vReturnEntities1);

    strCondition2="";
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strWorldwideTag)) {
        if (!strCondition1.equals(strCondition2)) {
          if (strCondition2.trim().length()>0)  {
            vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", " ")+"999999999999999");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
          }
          vPrintDetails.add(BREAK_INDICATOR+":p.:hp2."+strCondition1+"--->:ehp2.");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", " "));     //We are sortng on this column
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          strCondition2 = strCondition1;
        }
      }
      vPrintDetails.add(getAttributeValue(eiOrderOF, "INVNAME", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MACHTYPE", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MODEL", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "ORDERCODE", " "));
      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      vPrintDetails.add((strCondition1.equals("CIF")) ? "Y" : "N");
      vPrintDetails.add(getAttributeValue(eiOrderOF, "RETURNEDPARTS", " "));
    }
    if (!strCondition2.equals(strWorldwideTag)) {
      if (strCondition2.trim().length()>0)  {
        vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
        vPrintDetails.add(" ");
        vPrintDetails.add(" ");
        vPrintDetails.add("999999999999999");
        vPrintDetails.add(" ");
        vPrintDetails.add(" ");
        vPrintDetails.add(" ");
      }
    }
    if (vPrintDetails.size()>0) {
      println(":h3.New No-Charge Specify Codes");
      println(":xmp.");
      println(".kp off");
      println("                                                     Order     Parts");
    }
    iSortCols = new int [] {3};     //Sort on Feature
    printReport(true, strHeader, iColWidths, vPrintDetails,iSortCols);
    if (vPrintDetails.size()>0) {
      println("Order Codes:");
      println("");
      println("         B - Available on both initial and field upgrade orders");
      println("         M - Available on field upgrade (MES) orders only");
      println("         P - Available on initial orders from the plant only");
      println("         S - Supported for migration only and cannot be ordered");
      println("");
      println(":exmp.");
    }
    resetPrintvars();
    println(".*$A_057_End");

    println(".*$A_058_Begin");

    /*
     *  Model Conversions
     *  AVAIL > ORDEROF(with Hardware-Model)
     */
    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT"};
    strFilterValue = new String[]{"Hardware", "ModelConvert"};//Hardware,ModelConvert

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");

    logMessage("Q58:Machine Type Conversions");
    displayContents(vReturnEntities1);

    i = 0;
    if (vReturnEntities1.size()>0)  {
      println(":h3.Machine Type Conversions");
      println(":p.");
      println(":xmp.");
      println(".kp off");
      println("");

      println("    From          To                                     Rtn  Cont");
      println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
      println("------------- ------------- ---------------------------- ---- ---- ---");
    }

    strCondition2="";
    hNoDupeLines = new Hashtable();
    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strWorldwideTag)) {
        if (!strCondition1.equals(strCondition2)) {
          if (strCondition2.trim().length()>0)  {
            vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
            vPrintDetails.add(" ");
            vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMFEATURECODE", " ")+"999999999999999");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", "    "));
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
          }
          vPrintDetails.add(BREAK_INDICATOR+":p.:hp2."+strCondition1+"--->:ehp2.");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMFEATURECODE", " "));     //We are sortng on this column
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", "    "));
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          strCondition2 = strCondition1;
        }
      }
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMMACHTYPE", "    "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMMODEL","   "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMFEATURECODE", "    "));

      vPrintDetails.add(getAttributeValue(eiOrderOF, "MACHTYPE", "    "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MODEL", "   "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", "    "));


      strCondition1 = getAttributeValue(eiOrderOF, "INVNAME", "");
      vPrintDetails.add(strCondition1);
      //strCondition4 += (" "+strCondition1 + m_strSpaces.substring(0, 28 - (strCondition1.length()>28 ? 28 : strCondition1.length() )));

      strCondition1 = getAttributeValue(eiOrderOF, "RETURNEDPARTS", " ");
      strCondition4 = strCondition1.equals("Yes") ? "  Y " : "  N ";
      vPrintDetails.add(strCondition4);

      strCondition1 = getAttributeValue(eiOrderOF, "CONTMAINTENANCE", " ");
      strCondition4 =strCondition1.equals("Yes") ? "  Y " : "  N ";
      vPrintDetails.add(strCondition4);

      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      strCondition4 = (strCondition1.equals("CIF") ? " Y" : " N");
      vPrintDetails.add(strCondition4);

/*       if (!hNoDupeLines.contains(strCondition4)) {
        println(strCondition4);
        hNoDupeLines.put(strCondition4,"found");
      }
 */
    }
    if (!strCondition2.equals(strWorldwideTag)) {
      if (strCondition2.trim().length()>0)  {
          vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMFEATURECODE", " ")+"999999999999999");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", "    "));
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
      }
    }
    iColWidths = new int[]{4,3,4,4,3,4,28,4,4,3};
    iSortCols = new int [] {2,5};     //Sort on Feature
    printReport(false, null, iColWidths, vPrintDetails,iSortCols);
    resetPrintvars();


    if (vPrintDetails.size()>0) {
        println(":exmp.");
    }




    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT"};
    strFilterValue = new String[]{"Hardware", "ModelUpgrade"};//Hardware,ModelUpgrade

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");

    logMessage("Q58:Model Upgrades");
    displayContents(vReturnEntities1);

    i = 0;
    if (vReturnEntities1.size()>0)  {
      println(":h3.Model Upgrades");
      println(":p.");
      println(":xmp.");
      println(".kp off");
      println("");

      println("    From          To                                     Rtn  Cont");
      println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
      println("------------- ------------- ---------------------------- ---- ---- ---");
    }

    strCondition2="";
    hNoDupeLines = new Hashtable();
    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strWorldwideTag)) {
        if (!strCondition1.equals(strCondition2)) {
          if (strCondition2.trim().length()>0)  {
            vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
            vPrintDetails.add(" ");
            vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMFEATURECODE", " ")+"999999999999999");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", "    "));
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
          }
          vPrintDetails.add(BREAK_INDICATOR+":p.:hp2."+strCondition1+"--->:ehp2.");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMFEATURECODE", " "));     //We are sortng on this column
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", "    "));
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          strCondition2 = strCondition1;
        }
      }
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMMACHTYPE", "    "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMMODEL","   "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMFEATURECODE", "    "));

      vPrintDetails.add(getAttributeValue(eiOrderOF, "MACHTYPE", "    "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MODEL", "   "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", "    "));


      strCondition1 = getAttributeValue(eiOrderOF, "INVNAME", "");
      vPrintDetails.add(strCondition1);
      //strCondition4 += (" "+strCondition1 + m_strSpaces.substring(0, 28 - (strCondition1.length()>28 ? 28 : strCondition1.length() )));

      strCondition1 = getAttributeValue(eiOrderOF, "RETURNEDPARTS", " ");
      strCondition4 = strCondition1.equals("Yes") ? "  Y " : "  N ";
      vPrintDetails.add(strCondition4);

      strCondition1 = getAttributeValue(eiOrderOF, "CONTMAINTENANCE", " ");
      strCondition4 =strCondition1.equals("Yes") ? "  Y " : "  N ";
      vPrintDetails.add(strCondition4);

      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      strCondition4 = (strCondition1.equals("CIF") ? " Y" : " N");
      vPrintDetails.add(strCondition4);

/*       if (!hNoDupeLines.contains(strCondition4)) {
        println(strCondition4);
        hNoDupeLines.put(strCondition4,"found");
      }
 */
    }
    if (!strCondition2.equals(strWorldwideTag)) {
      if (strCondition2.trim().length()>0)  {
          vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMFEATURECODE", " ")+"999999999999999");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", "    "));
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
      }
    }
    iColWidths = new int[]{4,3,4,4,3,4,28,4,4,3};
    iSortCols = new int [] {2,5};     //Sort on Feature
    printReport(false, null, iColWidths, vPrintDetails,iSortCols);

    if (vPrintDetails.size()>0) {
        println(":exmp.");
    }
    resetPrintvars();




    /*
     *  Model Conversions
     *  AVAIL > ORDEROF(with Hardware-)
     */
    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT"};
    strFilterValue = new String[]{"Hardware", "ModelConvert"};//Hardware,ModelConvert

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    displayContents(vReturnEntities1);
    if (vReturnEntities1.size()>0)  {
      println(":h3.Model Conversions:");
      println(":p.");
      println(":xmp.");
      println(".kp off");

      println("      From   To      Return  Continuous");
      println("Type  Model  Model   Parts   Maintenance  CSU");
      println("----  -----  -----   ------  -----------  ---");
    }
    //       9402   740    890      Y          Y        N
    strCondition2 = "";
    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);
      if (!strCondition1.equals(strCondition2)) {
        if (strCondition2.trim().length()>0)  {
          println(".br;:hp2.<---"+strCondition2+":ehp2.");
        }
        println(":p.:hp2."+strCondition1+"--->:ehp2.");
        strCondition2 = strCondition1;
      }
      String strFromType = getAttributeValue(eiOrderOF, "FROMMACHTYPE", "    ");
      print(strFromType+"  ");
      //String strFromModel = getAttributeValue(eiOrderOF, "FROMMODEL", "   ");
      print(strFromType+"    ");

      print(getAttributeValue(eiOrderOF, "MODEL", "   "));
      strCondition1 = getAttributeValue(eiOrderOF, "RETURNEDPARTS", " ");
      print("      "+(strCondition1.equals("Yes") ? "Y" : "N"));
      strCondition1 = getAttributeValue(eiOrderOF, "CONTMAINTENANCE", " ");
      print("          "+(strCondition1.equals("Yes") ? "Y" : "N"));
      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      println("        "+(strCondition1.equals("CIF") ? "Y" : "N"));
    }
    if (strCondition2.trim().length()>0)  {
      println(".br;:hp2.<---"+strCondition2+":ehp2.");
    }

    if (vReturnEntities1.size()>0) {
        println(":exmp.");
    }



    /*
     *  Print for Feature Conversions
     */
    //***************************************************
    println(":h3.Feature Conversions");
    println(":p.");
           //9402 720 2063 9402 830 2349 Processor upgrade              Y   Y    N
    String[] strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT","OOFGRP"};
    String[] strFilterValue = new String[]{"Hardware", "FeatureConvert","Processor"};

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    displayContents(vReturnEntities1);
    if (vReturnEntities1.size()>0)  {
      println(":h4.Processor to processor feature conversions:");
      println(":xmp.");
      println(".kp off");
      println("");
      println("    From          To                                     Rtn  Cont");
      println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
      println("------------- ------------- ---------------------------- ---- ---- ---");
    }


    strCondition2="";
    hNoDupeLines = new Hashtable();
    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strCondition2)) {
        if (strCondition2.trim().length()>0)  {
          println(".br;:hp2.<---"+strCondition2+":ehp2.");
        }
        println(":p.:hp2."+strCondition1+"--->:ehp2.");
        strCondition2 = strCondition1;
      }

      strCondition4 = getAttributeValue(eiOrderOF, "FROMMACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMMODEL","   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMFEATURECODE", "    ");
      strCondition4 +=  " "+getAttributeValue(eiOrderOF, "MACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "MODEL", "   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FEATURECODE", "    ");

      strCondition1 = getAttributeValue(eiOrderOF, "INVNAME", "");
      strCondition4 += (" "+strCondition1 + m_strSpaces.substring(0, 28 - (strCondition1.length()>28 ? 28 : strCondition1.length() )));

      strCondition1 = getAttributeValue(eiOrderOF, "RETURNEDPARTS", " ");
      strCondition4 += " "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "CONTMAINTENANCE", " ");
      strCondition4 +=" "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      strCondition4 += " "+(strCondition1.equals("CIF") ? " Y" : " N");
      if (!hNoDupeLines.contains(strCondition4)) {
        println(strCondition4);
        hNoDupeLines.put(strCondition4,"found");
      }

    }
    if (strCondition2.trim().length()>0)  {
      println(".br;:hp2.<---"+strCondition2+":ehp2.");
    }
    if (vReturnEntities1.size()>0) {
        println(":exmp.");
    }


    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT","OOFGRP"};
    strFilterValue = new String[]{"Hardware", "FeatureConvert","ICard"};

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    displayContents(vReturnEntities1);
    if (vReturnEntities1.size()>0)  {
      println(":h4.Interactive feature conversions:");
      println(":xmp.");
      println(".kp off");
      println("");
      println("    From          To                                     Rtn  Cont");
      println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
      println("------------- ------------- ---------------------------- ---- ---- ---");
    }
    strCondition2="";
    hNoDupeLines = new Hashtable();
    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strCondition2)) {
        if (strCondition2.trim().length()>0)  {
          println(".br;:hp2.<---"+strCondition2+":ehp2.");
        }
        println(":p.:hp2."+strCondition1+"--->:ehp2.");
        strCondition2 = strCondition1;
      }

      strCondition4 = getAttributeValue(eiOrderOF, "FROMMACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMMODEL","   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMFEATURECODE", "    ");
      strCondition4 +=  " "+getAttributeValue(eiOrderOF, "MACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "MODEL", "   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FEATURECODE", "    ");

      strCondition1 = getAttributeValue(eiOrderOF, "INVNAME", "");
      strCondition4 += (" "+strCondition1 + m_strSpaces.substring(0, 28 - (strCondition1.length()>28 ? 28 : strCondition1.length() )));

      strCondition1 = getAttributeValue(eiOrderOF, "RETURNEDPARTS", " ");
      strCondition4 += " "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "CONTMAINTENANCE", " ");
      strCondition4 +=" "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      strCondition4 += " "+(strCondition1.equals("CIF") ? " Y" : " N");
      if (!hNoDupeLines.contains(strCondition4)) {
        println(strCondition4);
        hNoDupeLines.put(strCondition4,"found");
      }

    }
    if (strCondition2.trim().length()>0)  {
      println(".br;:hp2.<---"+strCondition2+":ehp2.");
    }
    resetPrintvars();
    if (vReturnEntities1.size()>0) {
        println(":exmp.");
    }


    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT","OOFGRP"};
    strFilterValue = new String[]{"Hardware", "FeatureConvert","Package"};

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    displayContents(vReturnEntities1);
    if (vReturnEntities1.size()>0)  {
      println(":h4.Package feature conversions:");
      println(":xmp.");
      println(".kp off");
      println("");
      println("    From          To                                     Rtn  Cont");
      println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
      println("------------- ------------- ---------------------------- ---- ---- ---");
    }
    strCondition2="";
    hNoDupeLines = new Hashtable();
    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strCondition2)) {
        if (strCondition2.trim().length()>0)  {
          println(".br;:hp2.<---"+strCondition2+":ehp2.");
        }
        println(":p.:hp2."+strCondition1+"--->:ehp2.");
        strCondition2 = strCondition1;
      }
      strCondition4 = getAttributeValue(eiOrderOF, "FROMMACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMMODEL","   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMFEATURECODE", "    ");
      strCondition4 +=  " "+getAttributeValue(eiOrderOF, "MACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "MODEL", "   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FEATURECODE", "    ");

      strCondition1 = getAttributeValue(eiOrderOF, "INVNAME", "");
      strCondition4 += (" "+strCondition1 + m_strSpaces.substring(0, 28 - (strCondition1.length()>28 ? 28 : strCondition1.length() )));

      strCondition1 = getAttributeValue(eiOrderOF, "RETURNEDPARTS", " ");
      strCondition4 += " "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "CONTMAINTENANCE", " ");
      strCondition4 +=" "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      strCondition4 += " "+(strCondition1.equals("CIF") ? " Y" : " N");
      if (!hNoDupeLines.contains(strCondition4)) {
        println(strCondition4);
        hNoDupeLines.put(strCondition4,"found");
      }
    }
    if (strCondition2.trim().length()>0)  {
      println(".br;:hp2.<---"+strCondition2+":ehp2.");
    }
    resetPrintvars();
    if (vReturnEntities1.size()>0) {
        println(":exmp.");
    }






    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT","OOFGRP"};
    strFilterValue = new String[]{"Hardware", "FeatureConvert","Memory"};

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    displayContents(vReturnEntities1);
    if (vReturnEntities1.size()>0)  {
      println(":h4.Main Storage feature conversions:");
      println(":xmp.");
      println(".kp off");
      println("");
      println("    From          To                                     Rtn  Cont");
      println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
      println("------------- ------------- ---------------------------- ---- ---- ---");
    }
    strCondition2="";
    hNoDupeLines = new Hashtable();
    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strCondition2)) {
        if (strCondition2.trim().length()>0)  {
          println(".br;:hp2.<---"+strCondition2+":ehp2.");
        }
        println(":p.:hp2."+strCondition1+"--->:ehp2.");
        strCondition2 = strCondition1;
      }
      strCondition4 = getAttributeValue(eiOrderOF, "FROMMACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMMODEL","   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMFEATURECODE", "    ");
      strCondition4 +=  " "+getAttributeValue(eiOrderOF, "MACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "MODEL", "   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FEATURECODE", "    ");

      strCondition1 = getAttributeValue(eiOrderOF, "INVNAME", "");
      strCondition4 += (" "+strCondition1 + m_strSpaces.substring(0, 28 - (strCondition1.length()>28 ? 28 : strCondition1.length() )));

      strCondition1 = getAttributeValue(eiOrderOF, "RETURNEDPARTS", " ");
      strCondition4 += " "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "CONTMAINTENANCE", " ");
      strCondition4 +=" "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      strCondition4 += " "+(strCondition1.equals("CIF") ? " Y" : " N");
      if (!hNoDupeLines.contains(strCondition4)) {
        println(strCondition4);
        hNoDupeLines.put(strCondition4,"found");
      }
    }
    if (strCondition2.trim().length()>0)  {
      println(".br;:hp2.<---"+strCondition2+":ehp2.");
    }
    resetPrintvars();
    if (vReturnEntities1.size()>0) {
        println(":exmp.");
    }


    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT","OOFGRP"};
    strFilterValue = new String[]{"Hardware", "FeatureConvert","Media"};

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    displayContents(vReturnEntities1);
    if (vReturnEntities1.size()>0)  {
      println(":h4.Disk/Tape/CD-ROM feature conversions:");
      println(":xmp.");
      println(".kp off");
      println("");
      println("    From          To                                     Rtn  Cont");
      println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
      println("------------- ------------- ---------------------------- ---- ---- ---");
    }
    strCondition2="";
    hNoDupeLines = new Hashtable();
    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strCondition2)) {
        if (strCondition2.trim().length()>0)  {
          println(".br;:hp2.<---"+strCondition2+":ehp2.");
        }
        println(":p.:hp2."+strCondition1+"--->:ehp2.");
        strCondition2 = strCondition1;
      }
      strCondition4 = getAttributeValue(eiOrderOF, "FROMMACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMMODEL","   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMFEATURECODE", "    ");
      strCondition4 +=  " "+getAttributeValue(eiOrderOF, "MACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "MODEL", "   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FEATURECODE", "    ");

      strCondition1 = getAttributeValue(eiOrderOF, "INVNAME", "");
      strCondition4 += (" "+strCondition1 + m_strSpaces.substring(0, 28 - (strCondition1.length()>28 ? 28 : strCondition1.length() )));

      strCondition1 = getAttributeValue(eiOrderOF, "RETURNEDPARTS", " ");
      strCondition4 += " "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "CONTMAINTENANCE", " ");
      strCondition4 +=" "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      strCondition4 += " "+(strCondition1.equals("CIF") ? " Y" : " N");
      if (!hNoDupeLines.contains(strCondition4)) {
        println(strCondition4);
        hNoDupeLines.put(strCondition4,"found");
      }
    }
    if (strCondition2.trim().length()>0)  {
      println(".br;:hp2.<---"+strCondition2+":ehp2.");
    }
    resetPrintvars();
    if (vReturnEntities1.size()>0) {
        println(":exmp.");
    }


    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT","OOFGRP"};
    strFilterValue = new String[]{"Hardware", "FeatureConvert","N/A"};

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    displayContents(vReturnEntities1);
    if (vReturnEntities1.size()>0)  {
      println(":h4.Miscellaneous feature conversions:");
      println(":xmp.");
      println(".kp off");
      println("");
      println("    From          To                                     Rtn  Cont");
      println("Type Mod  FC  Type Mod  FC  Description                  Part Mnt  CSU");
      println("------------- ------------- ---------------------------- ---- ---- ---");
    }
    strCondition2="";
    hNoDupeLines = new Hashtable();
    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFAVAIL");
      eiOrderOFAvail = (EntityItem) vReturnEntities3.elementAt(0);
      eiAvail =(EntityItem) eiOrderOFAvail.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);

      if (!strCondition1.equals(strCondition2)) {
        if (strCondition2.trim().length()>0)  {
          println(".br;:hp2.<---"+strCondition2+":ehp2.");
        }
        println(":p.:hp2."+strCondition1+"--->:ehp2.");
        strCondition2 = strCondition1;
      }
      strCondition4 = getAttributeValue(eiOrderOF, "FROMMACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMMODEL","   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FROMFEATURECODE", "    ");
      strCondition4 +=  " "+getAttributeValue(eiOrderOF, "MACHTYPE", "    ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "MODEL", "   ");
      strCondition4 += " "+getAttributeValue(eiOrderOF, "FEATURECODE", "    ");

      strCondition1 = getAttributeValue(eiOrderOF, "INVNAME", "");
      strCondition4 += (" "+strCondition1 + m_strSpaces.substring(0, 28 - (strCondition1.length()>28 ? 28 : strCondition1.length() )));

      strCondition1 = getAttributeValue(eiOrderOF, "RETURNEDPARTS", " ");
      strCondition4 += " "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "CONTMAINTENANCE", " ");
      strCondition4 +=" "+(strCondition1.equals("Yes") ? "  Y " : "  N ");

      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      strCondition4 += " "+(strCondition1.equals("CIF") ? " Y" : " N");
      if (!hNoDupeLines.contains(strCondition4)) {
        println(strCondition4);
        hNoDupeLines.put(strCondition4,"found");
      }
    }
    if (strCondition2.trim().length()>0)  {
      println(".br;:hp2.<---"+strCondition2+":ehp2.");
    }
    resetPrintvars();
    if (vReturnEntities1.size()>0) {
        println(":exmp.");
    }

    println(".*$A_058_End");

    println(".*$A_059_Begin");
    //?? NOT applicable for SG
    println(".*$A_059_End");

    println(".*$A_060_Begin");
    strCondition1 = getAttributeValue(eiAnnounce, "NATLANGREGTSHW", "");
    strCondition1 += getAttributeValue(eiAnnounce, "NATLANGREGTSSW", "");
    println(strCondition1.length() > 0 ? "Yes" : "No");
    println(".*$A_060_End");

    println(".*$A_062_Begin");
    println(":xmp.");
    println(".kp off");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF  (E)
    // with classification of 'Hardware-System-Base-%' -> COFCHANNEL (R) -> CHANNEL (E)

    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    //Search upwards from AVAIL to get COMMERCIALOF
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    // Get the downlinked Channel from the list of COMMERCIALOF
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFCHANNEL");
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "CHANNEL");
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiChannel = (EntityItem) vReturnEntities1.elementAt(i);
      strCondition1 = getGeoTags(eiChannel);
      if (strCondition1.trim().length()==0) {
        continue;       //Skip if no geo tags found
      }
      logMessage("_062 "+eiChannel.getKey()+strCondition1);
      vPrintDetails.add(getAttributeValue(eiChannel.getEntityType(), eiChannel.getEntityID(), "CHANNELNAME", " ") + " ");
      vPrintDetails.add(getAttributeValue(eiChannel.getEntityType(), eiChannel.getEntityID(), "SUPPLIER", " ") + " ");
//      strCondition1 = getAttributeValue(eiChannel.getEntityType(), eiChannel.getEntityID(), "GENAREANAME", " ");
      bConditionOK = false;
      if (strCondition1.equals(strWorldwideTag))  {
        bConditionOK=true;
      }
      vPrintDetails.add((bConditionOK) ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoAP(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add( m_geList.isRfaGeoCAN(eiChannel) && bConditionOK? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoUS(eiChannel) && bConditionOK ? "X" : " ");
      vPrintDetails.add( m_geList.isRfaGeoEMEA(eiChannel) && bConditionOK? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoLA(eiChannel) && bConditionOK ? "X" : " ");
    }
    strHeader = new String[]{"Route Description", "Supplier", "WW", "AP", "CAN","US", "EMEA", "LA"};
    iColWidths = new int[]{26, 19, 2, 2,3,2,4,2};
    printReport(true, strHeader, iColWidths, vPrintDetails);
    println(":exmp.");
    println(".*$A_062_End");

    println(".*$A_063_Begin");
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);//Print the same thing again
    resetPrintvars();
    println(":exmp.");
    println(".*$A_063_End");

    println(".*$A_065_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "MKTGSTRATEGY", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_065_End");

    /*
     *  Print from relator CROSSELL
     */
    //Start from AVAIL
    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) -> CROSSSELL (R) -> ORDEROF (E)
    println(".*$A_066_Begin");
    if (grpCrossSell != null) {
      for (i = 0; i < grpCrossSell.getEntityItemCount(); i++) {
        eiCrossSell = grpCrossSell.getEntityItem(i);
        eiOrderOF = (EntityItem) eiCrossSell.getDownLink(0);
        println(getAttributeValue(eiOrderOF, "DESCRIPTION", " "));
        strCondition1 = getAttributeValue(eiCrossSell, "BENEFIT", " ");
        if (strCondition1.trim().length()>0) {
          prettyPrint(transformXML(strCondition1),69);
        }
      }
    }
    println(".*$A_066_End");

    println(".*$A_067_Begin");
    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) -> UPSELL (R) -> ORDEROF (E)
    if (grpUpSell != null) {
      for (i = 0; i < grpUpSell.getEntityItemCount(); i++) {
        eiUpSell = grpUpSell.getEntityItem(i);
        eiOrderOF = (EntityItem) eiUpSell.getDownLink(0);
        println(getAttributeValue(eiOrderOF, "DESCRIPTION", " "));
        strCondition1 = transformXML(getAttributeValue(eiUpSell, "BENEFIT", " "));
        prettyPrint(strCondition1,69);
      }
    }
    println(".*$A_067_End");

    println(".*$A_071_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "MKTGACTIONREQ", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_071_End");

    println(".*$A_073_Begin");
    //'When OFFERINGTYPES = '2891' (IBM Application System/400 and iSeries)
    //  or '2892' (IBM RISC System/6000 and pSeries)
    //  or '2896' (IBM System/390 and zSeries)
    //  or '2899' (IBM High End xSeries)
    //  or '2901' (zSeries High End) then 'Yes', otherwise 'No'
    bConditionOK = false;

    strCondition1 = getAttributeFlagEnabledValue(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "OFFERINGTYPES", " ");
    bConditionOK = (strCondition1.indexOf("2891") > -1 ? true : bConditionOK) ;
    bConditionOK = (strCondition1.indexOf("2892") > -1 ? true : bConditionOK) ;
    bConditionOK = (strCondition1.indexOf("2896") > -1 ? true : bConditionOK) ;
    bConditionOK = (strCondition1.indexOf("2899") > -1 ? true : bConditionOK) ;
    bConditionOK = (strCondition1.indexOf("2901") > -1 ? true : bConditionOK) ;

    println(bConditionOK ? "Yes" : "No");

    println(".*$A_073_End");

    println(".*$A_074_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Sales Support
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5517", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_074_End");

    println(".*$A_075_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'
    //  -> COFCRYPTO (R) -> CRYPTO (E) or ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) ->
    //  OOFAVAIL (R) -> ORDEROF (E) -> OOFCRYPTO (R) -> CRYPTO

    bConditionOK = false;
    if (grpCOFCrypto != null || grpOOFCrypto != null) {
      if (grpCOFCrypto.getEntityItemCount() > 0 || grpOOFCrypto.getEntityItemCount() > 0 )  {
        bConditionOK = true;
      }
    }
    println((bConditionOK ? "Yes" : "No"));
    println(".*$A_075_End");

    println(".*$A_076_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'
    //  when EXPORTCLASS = '1120' (Obtained) or '1122' (Exempt) then answer 'Yes'

    //If either COMMERCIALOF (EXPORTCLASS) or FUP (EXPORTCLASS) is 1120' (Obtained) or '1122' (Exempt)
    //  the answer 'Yes', otherwise answer 'No'
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};

    //get COMMERCIALOF
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    bConditionOK = false;
    strFilterAttr = new String[] {"EXPORTCLASS","EXPORTCLASS"};
    strFilterValue  = new String[] {"1120","1122"};
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,false)) {
        bConditionOK = true;
        break;
      }
    }

    //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification of
    //  'Hardware-%-%-%'-> OOFFUP (R) -> FUP (E) when EXPORTCLASS = '1120' (Obtained) or '1122' (Exempt)
    //  then answer 'Yes'

    if (!bConditionOK ) {
      strFilterAttr = new String[] {"OOFCAT"};
      strFilterValue  = new String[] {"Hardware"};
      vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "OOFFUP");
      strFilterAttr = new String[] {"EXPORTCLASS","EXPORTCLASS"};
      strFilterValue  = new String[] {"1120","1122"};
      vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "FUP");
      if ( vReturnEntities1.size()>0 )  {
        bConditionOK=true;
      }
    }
    if (bConditionOK) {
      println("Yes");
    } else {
      println("No");
    }
    println(".*$A_076_End");

    println(".*$A_078_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIAL (E) with classification of 'Harsware-System-Base-%  -> COFCRYPTO
    //  (R) -> CRYPTO (E) or ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) ->
    //ORDEROF (E) with classification of 'Hardware-%-%-%' -> OOFCRYPTO (R) -> CRYPTO (E)
    //'Answer 'Yes' if either path yields CRYPTOSTRENGTH = 'Yes', answer 'No' if both paths yields CRYPTOSTRENGTH = 'No'
    bConditionOK = false;
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFCRYPTO");
    strFilterAttr = new String[]{"CRYPTOSTRENGTH"};
    strFilterValue = new String[]{"Yes"};
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CRYPTO");
    if (!bConditionOK)  {
      strFilterAttr = new String[] {"OOFCAT"};
      strFilterValue  = new String[] {"Hardware"};
      vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "OOFCRYPTO");
      strFilterAttr = new String[]{"CRYPTOSTRENGTH"};
      strFilterValue = new String[]{"Yes"};
      vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CRYPTO");
      if (vReturnEntities1.size() > 0)  {
        bConditionOK = true;
      }
    }
    if (bConditionOK) {
      println("Yes");
    } else {
      println("No");
    }

    println(".*$A_078_End");

    println(".*$A_079_Begin");
    bConditionOK = false;
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFCRYPTO");
    strFilterAttr = new String[]{"CRYPTORETAIL"};
    strFilterValue = new String[]{"Yes"};
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CRYPTO");
    if (!bConditionOK)  {
      strFilterAttr = new String[] {"OOFCAT"};
      strFilterValue  = new String[] {"Hardware"};
      vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "OOFCRYPTO");
      strFilterAttr = new String[]{"CRYPTORETAIL"};
      strFilterValue = new String[]{"Yes"};
      vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CRYPTO");
      if (vReturnEntities1.size() > 0)  {
        bConditionOK = true;
      }
    }
    if (bConditionOK) {
      println("Yes");
    } else {
      println("No");
    }
    println(".*$A_079_End");

    println(".*$A_080_Begin");
    bConditionOK = false;
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFCRYPTO");
    strFilterAttr = new String[]{"CRYPTOFUNC"};
    strFilterValue = new String[]{"Yes"};
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CRYPTO");
    if (!bConditionOK)  {
      strFilterAttr = new String[] {"OOFCAT"};
      strFilterValue  = new String[] {"Hardware"};
      vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "OOFCRYPTO");
      strFilterAttr = new String[]{"CRYPTOFUNC"};
      strFilterValue = new String[]{"Yes"};
      vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CRYPTO");
      if (vReturnEntities1.size() > 0)  {
        bConditionOK = true;
      }
    }
    if (bConditionOK) {
      println("Yes");
    } else {
      println("No");
    }
    println(".*$A_080_End");

    println(".*$A_081_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Cryptography - Export Regulations
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5500", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_081_End");

    println(".*$A_082_Begin");
    //Same conditon as of 0080
    if (bConditionOK) {
      println("Yes");
    } else {
      println("No");
    }
    println(".*$A_082_End");

    println(".*$A_083_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Cryptography - Export Regulations 2
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5496", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_083_End");

    println(".*$A_084_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'  when
    //  EXPORTCLASS = '1120' (Obtained) or '1122' (Exempt) then answer 'Yes'
    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification
    //  'Hardware-FeatureCode-%-%' -> OOFFUP (R) -> FUP (E) with classification of ' Hardware-%-%-%' when
    //  EXPORTCLASS = '1120' (Obtained) or '1122' (Exempt) then answer 'Yes'
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};

    //get COMMERCIALOF
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    bConditionOK = false;
    strFilterAttr = new String[] {"EXPORTCLASS","EXPORTCLASS"};
    strFilterValue  = new String[] {"1120","1122"};
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,false)) {
        bConditionOK = true;
        break;
      }
    }

    //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification of
    //  'Hardware-%-%-%'-> OOFFUP (R) -> FUP (E) when EXPORTCLASS = '1120' (Obtained) or '1122' (Exempt)
    //  then answer 'Yes'

    if (!bConditionOK ) {
      strFilterAttr = new String[] {"OOFCAT"};
      strFilterValue  = new String[] {"Hardware"};
      vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "OOFFUP");
      strFilterAttr = new String[] {"EXPORTCLASS","EXPORTCLASS"};
      strFilterValue  = new String[] {"1120","1122"};
      vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "FUP");
      if ( vReturnEntities1.size()>0 )  {
        bConditionOK=true;
      }
    }
    if (bConditionOK) {
      println("Yes");
    } else {
      println("No");
    }
    println(".*$A_084_End");
    println(".*$A_085_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Non-Crypto (limited to pwd,  authentication, digital sign) - Export classification2
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5483", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_085_End");
    println(".*$A_086_Begin");
    println(".*$A_086_End");

    println(".*$A_087_Begin");
    bConditionOK = false;
    strCondition1 = getAttributeValue(eiAnnounce, "PRODDEMOANN", "");
    if (strCondition1.length() > 0) {
      bConditionOK = true;
    }
    if (!bConditionOK)  {
      strCondition1 = getAttributeValue(eiAnnounce, "PRODDEMOPLANAVAIL", "");
      if (strCondition1.length() > 0) {
        bConditionOK = true;
      }
    }
    if (!bConditionOK)  {
      strCondition1 = getAttributeValue(eiAnnounce, "SOLDEMOANN", "");
      if (strCondition1.length() > 0) {
        bConditionOK = true;
      }
    }
    if (!bConditionOK)  {
      strCondition1 = getAttributeValue(eiAnnounce, "SOLDEMOPLANAVAIL", "");
      if (strCondition1.length() > 0) {
        bConditionOK = true;
      }
    }

    if (!bConditionOK)  {
      //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'  ->
      //  COFORGANUNIT (R) when ORGANUNITROLETYPE = '4153' (Benchmark Facility)
      strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
      strFilterValue = new String[]{"Hardware", "System", "Base"};

      //get COMMERCIALOF
      vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
      strFilterAttr = new String[]{"ORGANUNITROLETYPE"};
      strFilterValue = new String[]{"4153"};
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, strFilterAttr, strFilterValue, true, true, "COFORGANUNIT");
      if ( vReturnEntities2.size()> 0) {
        bConditionOK = true;
      }
    }
    //Use the previous Commercialof vector set
    if (!bConditionOK)  {
      //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'  ->
      //  COFPUBS (R) -> PUBLICATION (E) when PUBTYPE = '5022' (Promotion Material)
      strFilterAttr = new String[]{"PUBTYPE"};
      strFilterValue = new String[]{"5022"};
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPUBS");
      vReturnEntities3= searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "PUBLICATION");
      if ( vReturnEntities3.size()> 0) {
        bConditionOK = true;
      }
    }


    println(bConditionOK ? "Yes" : "No");
    println(".*$A_087_End");

    println(".*$A_088_Begin");
    bConditionOK = false;
    strCondition1 = getAttributeValue(eiAnnounce, "PRODDEMOANN", "");
    if (strCondition1.length() > 0) {
      bConditionOK = true;
    }
    if (!bConditionOK)  {
      strCondition1 = getAttributeValue(eiAnnounce, "PRODDEMOPLANAVAIL", "");
      if (strCondition1.length() > 0) {
        bConditionOK = true;
      }
    }
    if (!bConditionOK)  {
      strCondition1 = getAttributeValue(eiAnnounce, "SOLDEMOANN", "");
      if (strCondition1.length() > 0) {
        bConditionOK = true;
      }
    }
    if (!bConditionOK)  {
      strCondition1 = getAttributeValue(eiAnnounce, "SOLDEMOPLANAVAIL", "");
      if (strCondition1.length() > 0) {
        bConditionOK = true;
      }
    }
    println(bConditionOK ? "Yes" : "No");
    println(".*$A_088_End");
    println(".*$A_089_Begin");
    println(":xmp.");
    println(".kp off");
    println("                                          Live Solution/");
    println("                         Live Product     Integrated");
    println("                         Demonstration    Demonstration");
    println("                         -------------    --------------");
    print("At announcement              ");
    print(getAttributeValue(eiAnnounce, "PRODDEMOANN", "   "));
    print("              ");
    println(getAttributeValue(eiAnnounce, "PRODDEMOPLANAVAIL", "   "));
    print("By planned availability      ");
    print(getAttributeValue(eiAnnounce, "SOLDEMOANN", "   "));
    print("              ");
    println(getAttributeValue(eiAnnounce, "SOLDEMOPLANAVAIL", "   "));
    println(":exmp.");
    println(".*$A_089_End");

    println(".*$A_090_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'  ->
    //  COFORGANUNIT (R) when ORGANUNITROLETYPE = '4153' (Benchmark Facility)
    //Continuing to use the commercial of vector from 087
    //Continuing to use the commercial of vector from 090
    bConditionOK = false;
    strFilterAttr = new String[]{"ORGANUNITROLETYPE"};
    strFilterValue = new String[]{"4153"};
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, strFilterAttr, strFilterValue, true, true, "COFORGANUNIT");
    if ( vReturnEntities2.size()> 0) {
      bConditionOK = true;
    }
    println(bConditionOK ? "Yes" : "No");
    println(".*$A_090_End");

    println(".*$A_091_Begin");
    //Start Searching from COFORGANUNIT (downlink to organunit)
    println(":xmp.");
    println(".kp off");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'  ->
    //  COFORGANUNIT (R) when ORGANUNITROLETYPE = '4153' (Benchmark Facility)
    //Use the COFORGANUNIT vector from 090
logMessage("A_091");
displayContents(vReturnEntities2);
    vPrintDetails1 = new Vector();
    for (i=0; i< vReturnEntities2.size();i++) {
      eiCofOrganUnit = (EntityItem) vReturnEntities2.elementAt(i);
      eiOrganUnit =(EntityItem) eiCofOrganUnit.getDownLink(0);
      for (k = 0; k < eiOrganUnit.getDownLinkCount(); k++) {
        eiOrganUnitIndiv = (EntityItem) eiOrganUnit.getDownLink(k);
        if (eiOrganUnitIndiv.getEntityType().equals("ORGANUNITINDIV")) {
          eiOP = (EntityItem) eiOrganUnitIndiv.getDownLink(0);
          vPrintDetails.add(getAttributeValue(eiCofOrganUnit, "STARTDATE", " "));
          if (flagvalueEquals(eiOrganUnit,"ORGANUNITTYPE","4155")) {
            vPrintDetails.add(getAttributeValue(eiOrganUnit, "NAME", " "));
            strCondition1 = getAttributeValue(eiOrganUnit, "CITY", " ");
            strCondition1 += " : " + getAttributeValue(eiOrganUnit, "STATE", " ");
            vPrintDetails.add(strCondition1);
            strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ");
            strCondition1 += " " + getAttributeValue(eiOP, "LASTNAME", " ");
            vPrintDetails1.add(strCondition1);
            strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ");
            strCondition1 += "/"+getAttributeValue(eiOP, "VNETUID", " ");
            vPrintDetails1.add(strCondition1);
          } else {
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            vPrintDetails1.add(" ");
            vPrintDetails1.add(" ");
          }
        }
      }
    }
    strHeader = new String[]{"Available", "System Center Name","Systems Center Location"};
    iColWidths = new int[]{10, 24,29};
    if (vPrintDetails.size()>0) {
      println("   Date");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
    }
    strHeader = new String[] { "Contact Name","   Node/Userid"};
    iColWidths = new int[]{43, 16};
    printReport(true, strHeader, iColWidths, vPrintDetails1);
    vPrintDetails1.removeAllElements();
    println(":exmp.");
    println(".*$A_091_End");

    println(".*$A_092_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%' ->
    //  COFPUBS (R) -> PUBLICATION (E) with PUBTYPE = 'Promotion Material'

    //Continuing to use the commercial of vector from 090
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPUBS");
    strCondition2="";

    if (grpCofPubs != null) {
      for (i = 0; i < vReturnEntities2.size(); i++) {
        eiCofPubs = (EntityItem) vReturnEntities2.elementAt(i);
        for (j = 0; j < eiCofPubs.getDownLinkCount(); j++) {
          eiPublication = (EntityItem) eiCofPubs.getDownLink(j);
          strCondition1 = getGeoTags(eiPublication);
          if (!strCondition1.equals(strCondition2)) {
            if (strCondition2.trim().length()>0)  {
              vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
              vPrintDetails.add(" ");
              vPrintDetails.add(" ");
            }
            vPrintDetails.add(BREAK_INDICATOR+":p.:hp2."+strCondition1+"--->:ehp2.");
            vPrintDetails.add(" ");
            vPrintDetails.add(" ");
            strCondition2 = strCondition1;
          }
          strCondition1 = getAttributeValue(eiPublication, "PUBTYPE", " ");
          if (strCondition1.equals("Promotion Material")) {
            vPrintDetails.add(getAttributeValue(eiPublication, "PUBTITLE", " "));
            vPrintDetails.add(getAttributeValue(eiPublication, "ORDERPN", " "));
            vPrintDetails.add(getAttributeValue(eiCofPubs.getEntityType(), eiCofPubs.getEntityID(), "PUBAVAIL", " "));
          }
        }
      }
    }
    if (strCondition2.trim().length()>0)  {
      vPrintDetails.add(BREAK_INDICATOR+".br;:hp2.<---"+strCondition2+":ehp2.");
      vPrintDetails.add(" ");
      vPrintDetails.add(" ");
    }
    strHeader = new String[]{"Title", "Order no.","Avail"};
    iColWidths = new int[]{48, 12,10};
    println("The following materials will be available from on the dates listed");
    println("below.");
    println(":p.:hp2.US--->:ehp2.");
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(":p.");
    println("Additional copies are available from the Publication Notification");
    println("System (PNS) or the country publications ordering system");
    println(".br;:hp2.<---US:ehp2.");
    println(".*$A_092_End");

    println(".*$A_093_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Internal Letter - Education support
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5537", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_093_End");

    println(".*$A_094_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Internal Letter - Marketing Information - Offering Information - OITOOL
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5487", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_094_End");

    println(".*$A_095_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Business Partner Attachment - Offering Information - OITOOL
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5491", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_095_End");

    println(".*$A_096_Begin");
    bConditionOK = true;
    strCondition1 = getAttributeValue(eiAnnounce, "INSTALLSUPPORT", " ");
    bConditionOK = (!strCondition1.equals(" ") ? true : false);
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5512", "", true);
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5501", "", true);
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5516", "", true);
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5503", "", true);
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5511", "", true);
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5499", "", true);
    bConditionOK = (vPrintDetails.size() > 0 ? true : bConditionOK);
    strCondition1 = getAttributeValue(eiAnnounce, "DIRECTCUSTSUPPORT ", " ");
    bConditionOK = (strCondition1.indexOf("B|C|D|E")<=0 ? bConditionOK : true);
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5492", "", true);
    bConditionOK = (vPrintDetails.size() > 0 ? true : bConditionOK);
    println(bConditionOK ? "Yes" : "No");
    resetPrintvars();
    println(".*$A_096_End");

    println(".*$A_097_Begin");
    if (grpConfigurator != null) {
      for (i = 0; i < grpAnnToConfig.getEntityItemCount(); i++) {
        eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
        eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
        strCondition1 = getAttributeValue(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGTYPE", " ");
        if (strCondition1.equals("Non-Standard")) {
          strCondition1 = getAttributeValue(eiConfigurator.getEntityType(), eiConfigurator.getEntityID(), "CONFIGNAME", " ");
          vPrintDetails.add(strCondition1);
          strCondition1 = getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", " ");
          vPrintDetails.add(strCondition1);
        }
      }
    }
    iColWidths = new int[]{49, 10};
    strHeader = new String[]{" Configurator Tool", "Date Available"};
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_097_End");

    println(".*$A_099_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "INSTALLSUPPORT", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_099_End");
  }


  /**
   *  Description of the Method
   */
  private void processLongTo200() {
    println(".*$A_110_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    //Field Support
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5512", "", true);
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5516", "", true);
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5503", "", true);
    println(vPrintDetails.size() > 0 ? "Yes" : "No");
    resetPrintvars();
    println(".*$A_110_End");

    println(".*$A_112_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Field Support text
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5512", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_112_End");

    println(".*$A_113_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Design Reviews text
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5511", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_113_End");

    println(".*$A_114_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Installation or Plan Reviews text
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5499", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_114_End");

    println(".*$A_115_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //AP, EMEA Field Support text  (per Alan Crudo)
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5501", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_115_End");

    println(".*$A_126_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Installation Support - Systems Assurance policy Statement
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5492", "", true);
    println(vPrintDetails.size() > 0 ? "Yes" : "No");
    println(".*$A_126_End");

    println(".*$A_127_Begin");                         //Use the vector from 126
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_127_End");

    println(".*$A_128_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Helpware
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5518", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_128_End");

    println(".*$A_132_Begin");
    //'ANNOUNCEMENT (E) ->ANNCOFA (A) ->COMMERCIALOF (E) when classification = Hardware-System-Base-% -> COFPRICE (R)
    //  -> PRICEFININFO - List all billing period found
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};

    //get COMMERCIALOF
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPRICE");
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiCofPrice = (EntityItem) vReturnEntities2.elementAt(i);
      eiCommOF = (EntityItem) eiCofPrice.getUpLink(0);
      eiPriceInfo = (EntityItem)eiCofPrice.getDownLink(0);
      vPrintDetails.add(getAttributeValue(eiCommOF, "MACHTYPE", " "));
      vPrintDetails.add(getAttributeValue(eiCommOF, "MODEL", " "));
      vPrintDetails.add(getAttributeValue(eiPriceInfo, "BILLINGPERIOD", " "));
    }
    strHeader = new String[]{"Machine", "Model","Billing Period"};
    iColWidths = new int[]{7, 5,14};
    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      println("                Maintenance");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_132_End");

    println(".*$A_136_Begin");
    println(getAttributeValue(eiAnnounce, "IVOSPECBIDS", " "));
    println(".*$A_136_End");

    println(".*$A_137_Begin");
    //'ANNOUNCEMENT (E) ->ANNCOFA (A) ->COMMERCIALOF (E) when classification = Hardware-System-Base-% ->
    // Get the downlinked IVOCAT from the list of COMMERCIALOF
    //Use previous COMMERCIALOF vector
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COMMERCIALOFIVO");
    if (vReturnEntities2 != null) {
      for (i = 0; i < vReturnEntities2.size(); i++) {
        eiCommOFIvo = (EntityItem) vReturnEntities2.elementAt(i);
        eiIvocat = (EntityItem) eiCommOFIvo.getDownLink(0);//There can only be 1 in a relator
        vPrintDetails.add(" "+getAttributeValue(eiIvocat, "IVOEXHIBIT", " "));
        vPrintDetails.add(getAttributeValue(eiIvocat, "IVONAME", " "));

      }
    }
    strHeader = new String[]{"CATEGORY", "     DESCRIPTION"};
    iColWidths = new int[]{11, 36};
    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      println(".rc 1 on");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_137_End");

    println(".*$A_138_Begin");
    //Start with the same COMMERCIALOFIVO vector as for 137
    bConditionOK = true;
    if (vReturnEntities2 != null) {
      for (i = 0; i < vReturnEntities2.size(); i++) {
        eiCommOFIvo = (EntityItem) vReturnEntities2.elementAt(i);
        eiIvocat = (EntityItem) eiCommOFIvo.getDownLink(0);//There can only be 1 in a relator
        eiCommOF = (EntityItem) eiCommOFIvo.getUpLink(0);//Same holds good for uplink too
        if (bConditionOK) {
          println("Option 1");
          println("");
          println(".sk");
          print(":hp2.IVO Volume Discount:       :ehp2. ");
          println(getAttributeValue(eiCommOFIvo, "IVOELIGBLE", " "));
          println("Option 2");
          println("");
          println(".sk");
          print(":hp2.Maximum Product Cap:       :ehp2. ");
          println(getAttributeValue(eiCommOFIvo, "IVODISCOUNTCAP", " ") + "%");
          bConditionOK = false;
        }
        vPrintDetails.add(getAttributeValue(eiIvocat, "IVOFORM", " "));
        vPrintDetails.add(getAttributeValue(eiIvocat, "IVOEXHIBIT", " "));
        vPrintDetails.add(getAttributeValue(eiIvocat, "IVONAME", " "));
        strCondition1 = getAttributeValue(eiCommOF, "MACHTYPE", " ");
        strCondition1 += getAttributeValue(eiCommOF, "MODEL", " ");
        vPrintDetails.add(strCondition1);
      }
    }
    strHeader = new String[]{" FORM", "EXHIBIT", "CATEGORY / DESCRIPTION", "PRODUCT"};
    iColWidths = new int[]{5, 7, 35, 7};
    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      println("Option 3");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
    }
    println(":exmp.");

    println(".*$A_138_End");

    println(".*$A_139_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Text for new machine types being added to an existing IVO category
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5525", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_139_End");

    println(".*$A_140_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Discount Cap
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5527", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_140_End");

    println(".*$A_151_Begin");
    println(getAttributeValue(eiAnnounce, "SPECIALBIDSTRANS", " "));
    println(".*$A_151_End");

    println(".*$A_176_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Revised Exhibit-Graduated or Processor Based Charges
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5493", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_176_End");

    println(".*$A_186_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //IBM Trial and Loan Program
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5506", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_186_End");

    println(".*$A_190_Begin");
    println(getAttributeValue(eiAnnounce, "MAINTPROCESS", " "));
    println(".*$A_190_End");

    println(".*$A_210_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //BP Attachment - Ordering Info. MES Availability
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5533", "", true);
    println(vPrintDetails.size() > 0 ? "Yes" : "No");
    println(".*$A_210_End");

    println(".*$A_214_Begin");                         //Continue from 210
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_214_End");

    println(".*$A_216_Begin");
    //pcdonly
    println(".*$A_216_End");

    println(".*$A_218_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "SPECORDERINST", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_218_End");

    println(".*$A_219_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Quickship, Custom Quickship, or Replenishment solution Model
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5521", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_219_End");

    println(".*$A_220_Begin");
    println(getAttributeValue(eiAnnounce, "IBMNADISTNETWORK", " "));
    println(".*$A_220_End");

    println(".*$A_236_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Order Control Codes
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5519", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_236_End");

    println(".*$A_237_Begin");
    //'ANNOUNCEMENT (E) ->ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%' -
    //  > GBTCOFA (A) -> GBT (E)
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
logMessage("A_237");
displayContents(  vReturnEntities1);

    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities2= searchEntityItemLink(eiCommOF,null,null,true,true,"COFGBTA");
logMessage("A_237");
displayContents(  vReturnEntities2);
      eiNextItem = null;
      eiNextItem1 = null;
      if (vReturnEntities2.size()>0)  {
        eiNextItem= (EntityItem) vReturnEntities2.elementAt(0);
logMessage(eiNextItem.getEntityType()+":"+eiNextItem.getEntityID());
        eiNextItem1 = (EntityItem) eiNextItem.getDownLink(0);    //this will be the GBT entity
      }
      vPrintDetails.add(getAttributeValue(eiCommOF, "MACHTYPE", " "));
      vPrintDetails.add(getAttributeValue(eiCommOF, "MODEL", " "));
      if (eiNextItem1!=null)  {
logMessage(eiNextItem1.getEntityType()+":"+eiNextItem1.getEntityID());
        vPrintDetails.add(getAttributeValue(eiNextItem1, "SAPPRIMBRANDCODE", " "));
        vPrintDetails.add(getAttributeValue(eiNextItem1, "SAPPRODFAMCODE", " "));
      }else {
        vPrintDetails.add(" ");
        vPrintDetails.add(" ");
      }
      vPrintDetails.add(getAttributeValue(eiCommOF, "GBNAME", " "));    //Changed from COMNAME
    }
    strHeader = new String[]{"Type", "Model", "Code", "Code", "Description"};
    iColWidths = new int[]{4, 7,7,7,40};

    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".sp");
      println("             Primary Product");
      println("             Brand   Family");

      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_237_End");

    println(".*$A_238_Begin");
    strFilterAttr = new String[]{"TRANSPRINTCT"};
    strFilterValue = new String[]{"Yes"};
    vReturnEntities1 = searchEntityGroup(grpPublication, strFilterAttr, strFilterValue, true);
    println(vReturnEntities1.size() > 0 ? "Yes" : "No");

    println(".*$A_238_End");

    println(".*$A_240_Begin");
    //5021 = 'A' , 5020 = B
    strCondition1 = getAttributeFlagEnabledValue(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "PRODSTRUCTURE", " ");
    println(strCondition1.equals("5021") ? "A" : strCondition1.equals("5020") ? "B" : "");
    println(".*$A_240_End");

    println(".*$A_245_Begin");
    println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "BTOSTRUCTURE", " "));
    println(".*$A_245_End");

    println(".*$A_250_Begin");
    //not for SG
    println(".*$A_250_End");

    println(".*$A_260_Begin");
    //not for SG
    println(".*$A_260_End");

    println(".*$A_261_Begin");

/*    //Use the vector used to extract q 237
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      vPrintDetails.add(getAttributeValue(eiCommOF, "SAPPRIMBRANDCODE", " "));
      vPrintDetails.add(getAttributeValue(eiCommOF, "SAPPRODFAMCODE", " "));
      vPrintDetails.add(getAttributeValue(eiCommOF, "COMNAME", " "));
      vPrintDetails.add(getAttributeValue(eiCommOF, "OMBRANDCODE", " "));
      vPrintDetails.add(getAttributeValue(eiCommOF, "OMPRODFAMCODE", " "));
      vPrintDetails.add(getAttributeValue(eiCommOF, "BPDBBRANDCODE", " "));
        vPrintDetails.add(getAttributeValue(eiCommOF, "ASSORTMODULE", " "));
    }
    strHeader = new String[]{"BRAND", "FAMILY", "DESCRIPTION", "OMBR.CODE", "OMFAMCODE", "BDPBCODE", "ASSORTMODULE"};
    iColWidths = new int[]{5, 6, 12, 9, 9, 9, 12};
    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
    }
    println(":exmp.");
    println(".*$A_261_End");

    println(".*$A_263_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //RSS PartnerLink Catalog information - Retail Store Solutions
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5522", "", true, true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_263_End");

    println(".*$A_265_Begin");
    //pcd only
    println(".*$A_265_End");
*/

    println(".*$A_267_Begin");
    //pcd only
    println(".*$A_267_End");

    println(".*$A_282_Begin");
    println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "REVENUEALLOC", " "));
    println(".*$A_282_End");

    println(".*$A_283_Begin");
    println(getAttributeValue(eiAnnounce, "REVENUESHARE", " "));
    println(".*$A_283_End");

    println(".*$A_285_Begin");
    println(getAttributeValue(eiAnnounce, "RPQTEXT", " "));
    println(".*$A_285_End");

    println(".*$A_286_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //MES Bulk Order process
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5532", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_286_End");

    /*
     *  Print EMEATRANSLATION details when DELIVERABLETYPE (ANNDELIVERABLE) = 'WW CustLetter'
     *  ANNOUNCEMENT->ANNDELIVERABLE->EMEATRANSLATION->INDIVIDUAL
     *  start from ANNDELREQTRANS relator (E1type - ANNDELIVERABLE, E2type - EMEATRANSLATION)
     */
    println(".*$A_287_Begin");

    //ANNOUNCEMENT (E) -> ANNTOANNDELIVER (R) -> ANNDELIVERABLE (E)  -> ANNDELREQTRANS (R) -> EMEATRANSLATION (E)
    //  -> TRANSDELREVIEW (R) -> OP (E)
    strFilterAttr = new String[]{"DELIVERABLETYPE"};
    strFilterValue = new String[]{"852"};
    vReturnEntities1 = searchEntityGroup(grpAnnDeliv, strFilterAttr, strFilterValue, true);
logMessage("A_287****ANNDELIVERABLE");
displayContents(vReturnEntities1);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true,true,"ANNDELREQTRANS");
logMessage("A_287****ANNDELREQTRANS");
displayContents(vReturnEntities2);
    strFilterAttr = new String[]{"LANGUAGES","LANGUAGES","LANGUAGES","LANGUAGES"};
    strFilterValue = new String[]{"2802", "2803","2797","2796"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "EMEATRANSLATION");
logMessage("A_287****EMEATRANSLATION");
displayContents(vReturnEntities3);
    vReturnEntities4 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "TRANSDELREVIEW");
logMessage("A_287****TRANSDELREVIEW");
displayContents(vReturnEntities4);
    for (i=0;i<vReturnEntities4.size();i++)  {
      eiNextItem = (EntityItem) vReturnEntities4.elementAt(i);
      logMessage("A_287****"+eiNextItem.getEntityType()+":"+eiNextItem.getEntityID()   );
      eiEmeaTranslation = (EntityItem) eiNextItem.getUpLink(0);
      eiOP = (EntityItem) eiNextItem.getDownLink(0);
      vPrintDetails.add(getAttributeValue(eiEmeaTranslation, "LANGUAGES", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0,1);
      strCondition1 += ". "+getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ");
      strCondition1 += "/"+getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }


     strHeader = new String[]{"Language", "Brand Reviewer Name", "  Node/Userid"};
      iColWidths = new int[]{17,30, 17};
      println(":h4.Worldwide Customer Letter Translation");
      println(":xmp.");
      println(".kp off");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
      println(":p.Note: This section is deleted at PLET generation time.");
      println(".*$A_287_End");

    println(".*$A_288_Begin");
    //ANNOUNCEMENT (E) -> ANNTOANNDELIVER (R) -> ANNDELIVERABLE (E)  -> ANNDELREQTRANS (R) -> EMEATRANSLATION (E)
    //  -> TRANSDELREVIEW (R) -> OP (E)
    strFilterAttr = new String[]{"DELIVERABLETYPE"};
    strFilterValue = new String[]{"856"};
    vReturnEntities1 = searchEntityGroup(grpAnnDeliv, strFilterAttr, strFilterValue, true);
logMessage("A_288****ANNDELIVERABLE");
displayContents(vReturnEntities1);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true,true,"ANNDELREQTRANS");
logMessage("A_288****ANNDELREQTRANS");
displayContents(vReturnEntities2);
    strFilterAttr = new String[]{"LANGUAGES","LANGUAGES","LANGUAGES","LANGUAGES"};
    strFilterValue = new String[]{"2802", "2803","2797","2796"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "EMEATRANSLATION");
logMessage("A_288****EMEATRANSLATION");
displayContents(vReturnEntities3);
    vReturnEntities4 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "TRANSDELREVIEW");
logMessage("A_288****TRANSDELREVIEW");
displayContents(vReturnEntities4);
    for (i=0;i<vReturnEntities4.size();i++)  {
      eiNextItem = (EntityItem) vReturnEntities4.elementAt(i);
      logMessage("A_288****"+eiNextItem.getEntityType()+":"+eiNextItem.getEntityID()   );
      eiEmeaTranslation = (EntityItem) eiNextItem.getUpLink(0);
      eiOP = (EntityItem) eiNextItem.getDownLink(0);
      vPrintDetails.add(getAttributeValue(eiEmeaTranslation, "LANGUAGES", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0,1);
      strCondition1 += ". "+getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ");
      strCondition1 += "/"+getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiEmeaTranslation, "PROPOSALINSERTID", " "));
    }

   strHeader = new String[]{"Language", "Brand Reviewer Name", "  Node/Userid"," InsertID"};
    iColWidths = new int[]{17,30, 17,10};

/*     strHeader = new String[]{"LANG CODE", "FAMILY", "BRAND REV INIT", "VNET NODE", "VNET UID","INSERTID"};
    iColWidths = new int[]{12, 15, 15, 15,8,8};
 */
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");

    println(".*$A_288_End");

    println(".*$A_289_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'
    //  -> COFCATINCL (R) -> CATINCL (E)
    //If COFCATINCL (R) exist and CATALOGNAME = '321' (ibm.com) then answer 'Yes' otherwise answer 'No'
    //Use the COMMERCIALOF vector
 logMessage("A_289_Begin********************");

    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true,true,"COMMERCIALOF");

    vReturnEntities2= searchEntityVectorLink(vReturnEntities1,null,null,true,true,"COFCATINCL");
    strFilterAttr = new String[]{"CATALOGNAME"};
    strFilterValue = new String[]{"321"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2,strFilterAttr,strFilterValue,true,true,"CATINCL");
    if (vReturnEntities3.size()>0) {
      println("Yes");
    } else {
      println("No");
    }
 logMessage("A_289_End********************");
    println(".*$A_289_End");

    println(".*$A_290_Begin");
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2,null,null,true,true,"CATINCL");
    strParamList1 = new String [] {"CATALOGTAXONOMY"};
    printValueListInVector(vReturnEntities3, strParamList1," ",true ,false);

    iColWidths = new int[]{55};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_290_End");

    println(".*$A_291_Begin");
    strFilterAttr = new String[]{"CATALOGNAME"};
    strFilterValue = new String[]{"321"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2,strFilterAttr,strFilterValue,true,true,"CATINCL");
    if (vReturnEntities3.size()>0) {
      println("Yes");
    } else {
      println("No");
    }
    println(".*$A_291_End");

    println(".*$A_292_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'
    //  -> ALTERNATEOF (R) -> COMMERCIALOF (E) -> COFCATINCL (R) -> CATINCL (E)
    //'If COFCATINCL (R) exist and CATALOGNAME = '321' (ibm.com) on COMMERCIALOF (base) and
    //  COMMERCIAL (alternate) then answer 'Yes' when REPLACEINSTTYPE = '1191' (Complete),
    //  when REPLACEINSTTYPE = '1192' (Partial) answer 'No'
    //Use the COMMERCIALOF vector
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};

    //get COMMERCIALOF
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    logMessage("*********A_292");
    displayContents(vReturnEntities1);
    strFilterAttr = new String[]{"REPLACEINSTTYPE"};
    strFilterValue = new String[]{"1191"};
    vReturnEntities2= searchEntityVectorLink(vReturnEntities1,strFilterAttr,strFilterValue,true,true,"ALTERNATEOF");
    logMessage("*********A_292");
    displayContents(vReturnEntities2);
    vReturnEntities3= searchEntityVectorLink(vReturnEntities2,null,null,true,true,"COMMERCIALOF");
    logMessage("*********A_292 ");
    displayContents(vReturnEntities3);
    vReturnEntities2= searchEntityVectorLink(vReturnEntities3,null,null,true,true,"COFCATINCL");
    strFilterAttr = new String[]{"CATALOGNAME"};
    strFilterValue = new String[]{"321"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2,strFilterAttr,strFilterValue,true,true,"CATINCL");
    logMessage("*********A_292 CATALOGNAME");
    displayContents(vReturnEntities3);
    if (vReturnEntities3.size()>0) {
      println("Yes");
    } else {
      println("No");
    }
    println(".*$A_292_End");

    println(".*$A_293_Begin");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'
    //  -> ALTERNATEOF (R) -> COMMERCIALOF (E) -> COFCATINCL (R) -> CATINCL (E)
    //'If COFCATINCL (R) exist and CATALOGNAME = '321' (ibm.com) on COMMERCIALOF (base) and
    //  COMMERCIAL (alternate) when REPLACEINSTTYPE = '1192' (Partial)
    strFilterAttr = new String[]{"REPLACEINSTTYPE"};
    strFilterValue = new String[]{"1192"};
    vReturnEntities2= searchEntityVectorLink(vReturnEntities1,strFilterAttr,strFilterValue,true,true,"ALTERNATEOF");
logMessage("    A_293_Begin");
displayContents(vReturnEntities2);
    strFilterAttr = new String[]{"CATALOGNAME"};
    strFilterValue = new String[]{"321"};
    for (i=0;i<vReturnEntities2.size();i++) {
      eiAlternateOF = (EntityItem) vReturnEntities2.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiAlternateOF,null,null,true,true,"COMMERCIALOF");
logMessage("    A_293_Begin DOWNLINKD COMOF");
displayContents(vReturnEntities3);
      vReturnEntities4= searchEntityVectorLink(vReturnEntities3,null,null,true,true,"COFCATINCL");
logMessage("    A_293_Begin COFCATINCL");
displayContents(vReturnEntities4);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities4,strFilterAttr,strFilterValue,true,true,"CATINCL");
logMessage("    A_293_Begin CATINCL");
displayContents(vReturnEntities3);
      if (vReturnEntities3.size()>0){
        println(getAttributeValue(eiAlternateOF.getEntityType(), eiAlternateOF.getEntityID(), "REPLACEINST", " "));
      }
    }
    println(".*$A_293_End");

    println(".*$A_294_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%' -
    //  > COFCATINCL (R) -> CATINCL (E)
    vReturnEntities2= searchEntityVectorLink(vReturnEntities1,null,null,true,true,"COFCATINCL");
    vReturnEntities1= searchEntityVectorLink(vReturnEntities2,null,null,true,true,"CATINCL");
    strParamList1 = new String [] {"FEATUREBENEFIT"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_294_End");

    println(".*$A_295_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "ACCESPEOWDISABLE", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_295_End");

    println(".*$A_296_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "ACCESPEOWDISABLECONSID", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_296_End");

    println(".*$A_297_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "USSEC508", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_297_End");

    println(".*$A_298_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "USSEC508LOGO", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_298_End");

    println(".*$A_299_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification 'Hardware-System-Base-%' ->
    //  GBTCOFA (A) -> GBT (E)
    //Use previous commercialof vector
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};

    //get COMMERCIALOF
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    vPrintDetails1 = new Vector();
    vPrintDetails2 = new Vector();
logMessage( "A_299");
displayContents(  vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);

      vReturnEntities2= searchEntityItemLink(eiCommOF,null,null,true,true,"COFGBTA");
logMessage( "A_299_1");
displayContents(  vReturnEntities2);
      eiNextItem = null;
      eiNextItem1 = null;
      if (vReturnEntities2.size()>0)  {
        eiNextItem= (EntityItem) vReturnEntities2.elementAt(0);
        eiNextItem1 = (eiNextItem.getDownLinkCount()>0 ?(EntityItem) eiNextItem.getDownLink(0):null);    //this will be the GBT entity (Wayne)
      }
      if (eiNextItem1 != null)  {
        strCondition1 = getAttributeValue(eiCommOF, "MACHTYPE", " ");
        strCondition1 += "-"+getAttributeValue(eiCommOF, "MODEL", " ");
        vPrintDetails.add(strCondition1);
        vPrintDetails.add(getAttributeValue(eiCommOF, "GBTNAME", " "));
        vPrintDetails.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "SAPPRIMBRANDCODE", " "): " ");

        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "SAPPRODFAMCODE", " "): " ");
        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "OMBRANDCODE", " "): " ");
        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "OMPRODFAMCODE", " "): " ");
        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "BPDBBRANDCODE", " "): " ");

        vPrintDetails2.add(strCondition1);
        vPrintDetails2.add(getAttributeValue(eiCommOF, "MATACCGRP", " "));
        vPrintDetails2.add(getAttributeValue(eiCommOF, "ASSORTMODULE", " "));

      }

    }
    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      println("Machine Type-Model   Description                        Primary Brand");
      strHeader = new String[]{"","","   Code"};
      iColWidths = new int[]{19,34,14};
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println("");
      println("");
      println("Product Family OM Brand OM Product Family BPDP Brand");
      strHeader = new String[]{"   Code","Code","     Code","   Code"};
      iColWidths = new int[]{14,8,17,10};
      printReport(true, strHeader, iColWidths, vPrintDetails1);
      vPrintDetails1=new Vector();
      println("");
      println("");

      strHeader = new String[]{" ","   Group","  Module"};
      iColWidths = new int[]{19,17,11};
      printReport(true, strHeader, iColWidths, vPrintDetails2);
      vPrintDetails2=new Vector();
      println(":exmp.");
    }
    println(".*$A_299_End");
  }



  /**
   *  Description of the Method
   */
  private void processLongTo300() {
    println(".*$A_300_Begin");

    //Use the COMMERCIALOF vector from A_299
    //Get the COFBPEXHIBIT relator from COMMERCIALOF
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFBPEXHIBIT");

    //Get the BPEXHIBIT  from COFBPEXHIBIT
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "BPEXHIBIT");
    bConditionOK = true;
    for (i = 0; i < vReturnEntities1.size(); i++) {
      if (bConditionOK) {
        println(":h4.Exhibit");
        println(":p.");
        println("These products are added to the following iSeries 400 and AS/400 exhibits:");
        println("");
        println(":ul c.");
        bConditionOK = false;
      }
      eiBPExhibit = (EntityItem) vReturnEntities1.elementAt(i);
      println(getAttributeValue(eiBPExhibit.getEntityType(), eiBPExhibit.getEntityID(), "BPTYPE", " "));
      strCondition1 = getAttributeValue(eiBPExhibit.getEntityType(), eiBPExhibit.getEntityID(), "EXHIBITNAME", " ");
      strCondition1 += " (" + getAttributeValue(eiBPExhibit.getEntityType(), eiBPExhibit.getEntityID(), "EXHIBITCODE", " ") + ")";
      println(":li." + strCondition1);
    }
    if (!bConditionOK) {
      println(":eul.");
    }

    println(".*$A_300_End");

    println(".*$A_302_Begin");
    println(".*$A_302_End");

    println(".*$A_303_Begin");
//    vPrintDetails.add(getAttributeValue(eiAnnounce, "OVERVIEWABSTRACT", " "));
//  iColWidths = new int[]{69};
//    printReport(false, null, iColWidths, vPrintDetails);
//    resetPrintvars();
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "OVERVIEWABSTRACT", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_303_End");

    println(".*$A_304_Begin");
    println(".*$A_304_End");

    println(".*$A_306_Begin");
    println(".*$A_306_End");

    println(".*$A_308_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "KEYPREREQ", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_308_End");

    println(".*$A_310_Begin");
//    vPrintDetails.add(getAttributeValue(eiAnnounce, "ATAGLANCE", " ") );
//    iColWidths = new int[]{69};
//    printReport(false, null, iColWidths, vPrintDetails);
//    resetPrintvars();
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "ATAGLANCE", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_310_End");

//    println(".*$A_311_Begin");
//    println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "AMCALLCENTER", ""));
//    println(".*$A_311_End");

    println(".*$A_312_Begin");
    println(".*$A_312_End");

    println(".*$A_314_Begin");
//    vPrintDetails.add(getAttributeValue(eiAnnounce, "FUNCVALUESQUAL", " ") );
//    iColWidths = new int[]{69};
//    printReport(false, null, iColWidths, vPrintDetails);
//    resetPrintvars();
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "FUNCVALUESQUAL", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_314_End");

    println(".*$A_317_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "PRODPOSITIONING", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_317_End");

    println(".*$A_318_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "STATEOFGENDIRECT", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_318_End");

    println(".*$A_319_Begin");
    println(".*$A_319_End");

    println(".*$A_320_Begin");
    strParamList1 = new String[]{"REFERENCEINFO"};
    printValueListInGroup(grpRelatedANN, strParamList1,null,null,"", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_320_End");

    println(".*$A_321_Begin");
    println(".*$A_321_End");

    println(".*$A_323_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Changed to 5529 from 5528 as per Pings response
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5529", "", true);
    println(vPrintDetails.size() > 0 ? "Yes" : "No");
    println(".*$A_323_End");

    println(".*$A_324_Begin");                         //Continue from 323
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_324_End");

    println(".*$A_325_Begin");
    /*
     *  ANNOUNCEMENT -> AVAIL -> COMMERCIALOF -> PUBLICATION with PUBTYPE (PUBLICATION) = 'Bill Of Form (BOF)' -
     *  Also need to navigate through PHYSICALOF to find all publications associated with this announcement -
     *  ANNOUNCEMENT -> AVAIL -> COMMERCIALOF  -> COFOOFMGMTGRP -> ORDEROF -> OOFPOFMGMTGRP ->
     *  PHYSICALOF -> PUBLICATION with PUBTYPE (PUBLICATION) = 'Bill Of Form (BOF)'
     *  Removing the publication filter...
     */

    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification 'Hardware-System-Base-%' ->
    //  COFPUBS (R) -> PUBLICATION (E) when PUBTYPE = '5026' (Shipped with)

    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, null, null, true,true,"COMMERCIALOF");
    strFilterAttr = new String[]{"PUBTYPE"};
    strFilterValue = new String[]{"5026"};
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPUBS");
      logMessage("    A_325:COFPUB");
      displayContents(vReturnEntities2);
//    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "PUBLICATION");

    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiCofPubs = (EntityItem) vReturnEntities2.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiCofPubs, strFilterAttr, strFilterValue, true, true, "PUBLICATION");
      eiPublication = vReturnEntities3.size() > 0 ? (EntityItem)  vReturnEntities3.elementAt(0) : null;
      if (eiPublication!=null)  {
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBTITLE", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "ORDERPN", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBPN", " "));
        vPrintDetails.add(getAttributeValue(eiCofPubs.getEntityType(), eiCofPubs.getEntityID(), "PUBAVAIL", " "));
      }
    }


    //ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> OOFAVAIL (R) ->ORDEROF (E)
    //  with classification of 'Hardware-%-%-%' -> OOFFUP (R) -> FUP (E) with classification of 'Hardware-%-%-%' ->
    //  FUPOWNSPOFOMG (R) -> FUPPOFMGMTGRP (E) with classification of 'Hardware-%-%-%'  -> POFMEMBERFUPOMG (R) ->
    //  PHYSICALOF (E) with classification of 'Hardware-%-%-%' -> POFPUBS (R) -> PUBLICATION (E) when PUBTYPE = '5026'
    //  (Shipped with)
    strFilterAttr = new String[]{"OOFCAT"};
    strFilterValue = new String[]{"Hardware"};
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    vReturnEntities3.removeAllElements();
    if (vReturnEntities1.size() > 0) {
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "OOFFUP");
      strFilterAttr = new String[]{"FUPCAT"};
      strFilterValue = new String[]{"Hardware"};
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "FUP");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "FUPOWNSPOFOMG");
      strFilterAttr = new String[]{"FUPPOFMGCAT"};
      strFilterValue = new String[]{"Hardware"};
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "FUPPOFMGMTGRP");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "POFMEMBERFUPOMG");
      strFilterAttr = new String[]{"POFCAT"};
      strFilterValue = new String[]{"Hardware"};
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "PHYSICALOF");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "POFPUBS");
    }

    strFilterAttr = new String[]{"PUBTYPE"};
    strFilterValue = new String[]{"5026"};
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities2.elementAt(i);        //this will be POFPUBS
      vReturnEntities3 = searchEntityItemLink(eiNextItem, strFilterAttr, strFilterValue, true, true, "PUBLICATION");
      eiPublication = vReturnEntities3.size() > 0 ? (EntityItem)  vReturnEntities3.elementAt(0) : null;
      if (eiPublication != null)  {
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBTITLE", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "ORDERPN", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBPN", " "));
        vPrintDetails.add(getAttributeValue(eiNextItem.getEntityType(), eiNextItem.getEntityID(), "PUBAVAIL", " "));
      }
    }

    strHeader = new String[]{"          Title", "Number", "Number","Date"};
    iColWidths = new int[]{30,12, 12,10};



    if (vPrintDetails.size() > 0) {
      println("The following publications are shipped with the products.");
      println("");
      println(":xmp.");
      println(".kp off");
      println("                               Order        Part");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_325_End");

    println(".*$A_326_Begin");
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, null, null, true,true,"COMMERCIALOF");
    strFilterAttr = new String[]{"PUBTYPE","PUBTYPE","PUBTYPE"};
    strFilterValue = new String[]{"5023","5024","5025"};           //Bill of Form, Service, Other
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPUBS");
      logMessage("    A_326:COFPUB");
      displayContents(vReturnEntities2);

    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiCofPubs = (EntityItem) vReturnEntities2.elementAt(i);
      vReturnEntities3 = searchEntityItemLink(eiCofPubs, strFilterAttr, strFilterValue, false, true, "PUBLICATION");
      eiPublication = vReturnEntities3.size() > 0 ? (EntityItem)  vReturnEntities3.elementAt(0) : null;

      if (eiPublication!=null)  {
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBTITLE", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "ORDERPN", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBPN", " "));
        vPrintDetails.add(getAttributeValue(eiCofPubs.getEntityType(), eiCofPubs.getEntityID(), "PUBAVAIL", " "));
      }
    }

    /*
    ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> OOFAVAIL (R) ->ORDEROF (E) with classification
    of 'Hardware-%-%-%' -> OOFFUP (R) -> FUP (E) with classification of 'Hardware-%-%-%' -> FUPOWNSPOFOMG (R)
    -> FUPPOFMGMTGRP (E) with classification of 'Hardware-%-%-%'  -> POFMEMBERFUPOMG (R) -> PHYSICALOF (E)
    with classification of 'Hardware-%-%-%' -> POFPUBS (R) -> PUBLICATION (E) when PUBTYPE = '5023'
    (Bill of Form (BOF) or '5024' (Service) or '5025' (Other)
     */
    strFilterAttr = new String[]{"OOFCAT"};
    strFilterValue = new String[]{"Hardware"};
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    vReturnEntities3.removeAllElements();
    if (vReturnEntities1.size() > 0) {
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "OOFFUP");
      strFilterAttr = new String[]{"FUPCAT"};
      strFilterValue = new String[]{"Hardware"};
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "FUP");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "FUPOWNSPOFOMG");
      strFilterAttr = new String[]{"FUPPOFMGCAT"};
      strFilterValue = new String[]{"Hardware"};
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "FUPPOFMGMTGRP");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "POFMEMBERFUPOMG");
      strFilterAttr = new String[]{"POFCAT"};
      strFilterValue = new String[]{"Hardware"};
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "PHYSICALOF");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "POFPUBS");
    }
    strFilterAttr = new String[]{"PUBTYPE","PUBTYPE","PUBTYPE"};
    strFilterValue = new String[]{"5023","5024","5025"};           //Bill of Form, Service, Other

    strParamList1 = new String[] {"POFPUBS"};
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities2.elementAt(i);        //this will be POFPUBS
      vReturnEntities3 = searchEntityItemLink(eiNextItem, strFilterAttr, strFilterValue, false, true, "PUBLICATION");
      eiPublication = vReturnEntities3.size() > 0 ? (EntityItem)  vReturnEntities3.elementAt(0) : null;
      if (eiPublication != null)  {
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBTITLE", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "ORDERPN", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBPN", " "));
        vPrintDetails.add(getAttributeValue(eiNextItem, "PUBAVAIL", " "));
      }
    }

    //Print the same report (325) again

    if (vPrintDetails.size() > 0) {
      println("To order, contact your IBM representative.");
      println(":xmp.");
      println(".kp off");
      println("                               Order        Part");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Softcopy Publication
      printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5523", "", true);
      iColWidths = new int[]{69};
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_326_End");

    println(".*$A_327_Begin");    //tbd
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-% ->
    //  COFPUBS (R) -> PUBLICATION (E) when PUBTYPE = '5023' (Bill of Form (BOF) or '5024' (Service)
    //  or '5025' (Other) or '5026' (Shipped with) and PUBLANGUAGE exist
    strFilterAttr =  new String[] {"COFCAT","COFSUBCAT","COFGRP"};
    strFilterValue = new String[] {"Hardware","System","Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    if (vReturnEntities1.size() > 0) {
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPUBS");
      logMessage("    A_327:COFPUB");
      displayContents(vReturnEntities2);
      strFilterAttr =  new String[] {"PUBTYPE","PUBTYPE","PUBTYPE","PUBTYPE"};
      strFilterValue = new String[] {"5023","5024","5025","5026"};
      vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, null, null, false, true, "PUBLICATION");
      logMessage("    A_327:PUBLICATION");
      displayContents(vReturnEntities1);
    }
    strParamList1 = new String[] {"COFPUBS"};
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiPublication = (EntityItem) vReturnEntities1.elementAt(i);
      eiCofPubs = getUplinkedEntityItem(eiPublication, strParamList1);
      logMessage("_327 COFPUBS "+eiCofPubs.getKey());
      strCondition1 = getAttributeValue(eiPublication, "PUBLANGUAGE", " ");
      if (!strCondition1.equals(" ")) {
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBTITLE", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "ORDERPN", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBPN", " "));
        vPrintDetails.add(getAttributeValue(eiCofPubs, "PUBAVAIL ", " "));
        vPrintDetails.add(strCondition1);
      }
    }

    //ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> OOFAVAIL (R) ->ORDEROF (E) with classification of '
    //   Hardware-%-%-%' -> OOFFUP (R) -> FUP (E) with classification of 'Hardware-%-%-%' -> FUPOWNSPOFOMG (R)
    //  -> FUPPOFMGMTGRP (E) with classification of 'Hardware-%-%-%'  -> POFMEMBERFUPOMG (R) -> PHYSICALOF (E)
    //  with classification of 'Hardware-%-%-%' -> POFPUBS (R) -> PUBLICATION (E) when PUBTYPE = '5023'
    //  (Bill of Form (BOF) or '5024' (Service) or '5025' (Other) or '5026' (Shipped with)

    strHeader = new String[]{"TITLE", "ORDER NO.", "PART NO.","AVAIL DATE","LANGUAGE"};
    iColWidths = new int[]{25, 9, 8,10,15};
    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_327_End");

    println(".*$A_328_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Softcopy Publication
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5523", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_328_End");

    println(".*$A_329_Begin");                         //Source File Publications
    printValueListInGroup(grpStdAmendText, new String[]{"STANDARDAMENDTEXT"}, "STANDARDAMENDTEXT_TYPE", "5524", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_329_End");

    println(".*$A_330_Begin");
    println(".*$A_330_End");

    println(".*$A_332_Begin"); //WAYNE
    //'ANNOUNCEMENT (E) -> ANNDIBPA (A) -> BOILPLATETEXT (E) when BOILPLATETEXT_TYPE = '219' (EMC Conformance)
    strParamList1 = new String[]{"STANDARDTEXT"};
    printValueListInGroup(grpBoilPlateText, strParamList1, "BOILPLATETEXT_TYPE", "219", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_332_End");

    println(".*$A_338_Begin");
    println(":xmp.");
    println(".kp off");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'
    iColWidths = new int[]{40};

    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true,true,"COMMERCIALOF");
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      strCondition1 = "          " + getAttributeValue(eiCommOF, "MACHTYPE", " ");
      strCondition1 += " " + getAttributeValue(eiCommOF, "MODEL", " ");

      strHeader = new String[]{strCondition1};

      strCondition1 = getAttributeValue(eiCommOF, "TYPWIDTHMETRIC", "0");
      strCondition1 += " " + getAttributeValue(eiCommOF, "TYPWIDTHUNITMETRIC", "0");
      strCondition1 += " ( " + getAttributeValue(eiCommOF, "TYPWIDTHUS", "0");
      strCondition1 += " " + getAttributeValue(eiCommOF, "TYPWIDTHUNITSUS", "0") + " )";
      strCondition2 = getAttributeValue(eiCommOF, "TYPDEPTHMETRIC", "0");
      strCondition2 += " " + getAttributeValue(eiCommOF, "TYPDEPTHUNITMETRIC", "0");
      strCondition2 += " ( " + getAttributeValue(eiCommOF, "TYPDEPTHUS", "0");
      strCondition2 += " " + getAttributeValue(eiCommOF, "TYPDEPTHUNITSUS", "0") + " )";
      strCondition3 = getAttributeValue(eiCommOF, "TYPHEIGHTMETRIC", "0");
      strCondition3 += " " + getAttributeValue(eiCommOF, "TYPHEIGHTUNITMETRIC", "0");
      strCondition3 += " ( " + getAttributeValue(eiCommOF, "TYPHEIGHTUS", "0");
      strCondition3 += " " + getAttributeValue(eiCommOF, "TYPHEIGHTUNITSUS", "0") + " )";
      strCondition4 = getAttributeValue(eiCommOF, "TYPWEIGHTMETRIC", "0");
      strCondition4 += " " + getAttributeValue(eiCommOF, "TYPWEIGHTUNITMETRIC", "0");
      strCondition4 += " ( " + getAttributeValue(eiCommOF, "TYPWEIGHTUS", "0");
      strCondition4 += " " + getAttributeValue(eiCommOF, "TYPWEIGHTUNITSUS", "0") + " )";
      if (!(strCondition1.equals("0 0 ( 0 0 )") &&
          strCondition2.equals("0 0 ( 0 0 )") &&
          strCondition3.equals("0 0 ( 0 0 )") &&
          strCondition4.equals("0 0 ( 0 0 )"))) {
        vPrintDetails.add("Width :   " + strCondition1);
        vPrintDetails.add("Depth :   " + strCondition2);
        vPrintDetails.add("Height:   " + strCondition3);
        vPrintDetails.add("Weight:   " + strCondition4);
        logMessage("A_338 Header" + strHeader[0]);

        printReport(true, strHeader, iColWidths, vPrintDetails);
        resetPrintvars();
      }
    }
    println(":exmp.");
    println(".*$A_338_End");

    println(".*$A_339_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "KEYSTANDANDS", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_339_End");

    println(".*$A_340_Begin");
    println(":xmp.");
    println(".kp off");
    /*
     *  'ANNOUNCEMENT -> AVAIL -> COMMERCIALOF  (with classification = 'Hardware-System-Base-%') -> ENVIRINFO->ALTDEPENENVIRINFO
     *
     */
    vReturnEntities2.removeAllElements();
    logMessage("A_340.....COMMERCIALOF");
    displayContents(vReturnEntities1);
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIAL (E) with classification of 'Hardware-System-Base-%' ->
    //  COFENVIR (R) -> ENVIRINFO (E) -> ENVIRALTDEP (R) -> ALTDEPENENVIRINFO (E)

    if (vReturnEntities1.size() > 0) {
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFENVIR");
      //Start from COFENVIR relator
    }
    logMessage("A_340.....COFENVIR");
    iColWidths = new int[]{55};
    displayContents(vReturnEntities2);
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities2.elementAt(i);
      eiCommOF = (EntityItem) eiNextItem.getUpLink(0);
      eiEnvirinfo = (EntityItem) eiNextItem.getDownLink(0);
    logMessage("A_340.....ENVIRINF:ET"+eiEnvirinfo.getEntityID());
      strCondition1 = "               " + getAttributeValue(eiCommOF, "MACHTYPE", " ");
      strCondition1 += " " + getAttributeValue(eiCommOF, "MODEL", " ");
      strHeader = new String[]{strCondition1};

      //get the relator to navigate from ENVIRINFO to ALTDEPENENVIRINFO
      vReturnEntities3 = searchEntityItemLink(eiEnvirinfo, null, null, true, true, "ENVIRALTDEP");
      for (j=0;j<vReturnEntities3.size();j++) {
        eiNextItem = (EntityItem) vReturnEntities3.elementAt(j);
        bConditionOK = false;
        logMessage("A_340....." + eiEnvirinfo.getEntityType() + eiEnvirinfo.getEntityID());
        if (eiNextItem != null) {
          logMessage("A_340....." + eiNextItem.getEntityType() + eiNextItem.getEntityID());
          eiAltEnvirinfo = (EntityItem) eiNextItem.getDownLink(0);
          logMessage("A_340....." + eiAltEnvirinfo.getEntityType() + eiAltEnvirinfo.getEntityID());
          //We can start building the print vector now
          strCondition1 = getAttributeValue(eiAltEnvirinfo, "OPERTEMP_MIN", "0");
          strCondition1 += " " + getAttributeValue(eiAltEnvirinfo, "OPERTEMP_MINUNITS", "0");
          strCondition1 += " to " + getAttributeValue(eiAltEnvirinfo, "OPERTEMP_MAX", "0");
          strCondition1 += " " + getAttributeValue(eiAltEnvirinfo, "OPERTEMP_MAXUNITS", "0");
          if (!strCondition1.equals("0 0 to 0 0")) {
            vPrintDetails.add("Temperature        :" + strCondition1);
          }

          strCondition1 = getAttributeValue(eiAltEnvirinfo, "SYSOFFTEMP_MIN", "0");
          strCondition1 += " " + getAttributeValue(eiAltEnvirinfo, "SYSOFFTEMP_MINUNITS", "0");
          strCondition1 += " to " + getAttributeValue(eiAltEnvirinfo, "SYSOFFTEMP_MAX ", "0");
          strCondition1 += " " + getAttributeValue(eiAltEnvirinfo, "SYSOFFTEMP_MAXUNITS", "0");
          if (!strCondition1.equals("0 0 to 0 0")) {
            vPrintDetails.add("System Off Temp.   :" + strCondition1);
          }


          strCondition1 = getAttributeValue(eiAltEnvirinfo, "ALTITUDE_MIN", "0");
          strCondition2 = getAttributeValue(eiAltEnvirinfo, "ALTITUDE_MINUNITS", "0");
          if (strCondition1.equals("0") || strCondition2.equals("0")) {
            strCondition1 = getAttributeValue(eiAltEnvirinfo, "SHIPALTITUDE_MIN", "0");
            strCondition2 = getAttributeValue(eiAltEnvirinfo, "SHIPALTITUDE_MINUNITS", "0");
          }

          strCondition3 = getAttributeValue(eiAltEnvirinfo, "ALTITUDE_MAX", "0");
          strCondition4 = getAttributeValue(eiAltEnvirinfo, "ALTITUDE_MAXUNITS", "0");
          if (strCondition3.equals("0") || strCondition4.equals("0")) {
            strCondition3 = getAttributeValue(eiAltEnvirinfo, "SHIPALTITUDE_MAX", "0");
            strCondition4 = getAttributeValue(eiAltEnvirinfo, "SHIPALTITUDE_MAXUNITS", "0");
          }
          if (!strCondition1.equals("0") || !strCondition2.equals("0") || !strCondition3.equals("0") || !strCondition4.equals("0")) {
            vPrintDetails.add("Altitude           :" + strCondition1 + " " + (strCondition1.equals("0") ? "" : strCondition2) + " to " + strCondition3 + " " + strCondition4);
          }

          strCondition1 = getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "OPERHUMID_MIN", "0");
          strCondition2 = getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "OPERHUMID_MINUNITS", "0");
          strCondition3 = getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "OPERHUMID_MAX", "0");
          strCondition4 = getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "OPERHUMID_MAXUNITS", "0");
          if (!strCondition1.equals("0") || !strCondition2.equals("0") || !strCondition3.equals("0") || !strCondition4.equals("0")) {
            vPrintDetails.add("Relative Humidity  :"  + strCondition1 + " " + (strCondition1.equals("0") ? "" : strCondition2) + " to " + strCondition3 + " " + strCondition4);
          }
          strCondition1 = getAttributeValue(eiAltEnvirinfo, "WETBULB", "0");
          strCondition1 += " " + getAttributeValue(eiAltEnvirinfo, "WETBULBUNITS", "0");
          if (!strCondition1.equals("0 0")) {
            vPrintDetails.add("Wet Bulb Reading   :" + strCondition1);
          }

          strCondition1 = getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "ELECTPWR", "0");
          strCondition1 += " " + getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "ELECTPWR_UNITS", "0");
          if (!strCondition1.equals("0 0")) {
            bConditionOK = true;                //Check whether to print heading for power requirements
            vPrintDetails.add("");
            vPrintDetails.add("Power Requirements");
            vPrintDetails.add("------------------");

            vPrintDetails.add("Electrical Power   :" + strCondition1);
          }
          strCondition1 =  getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "EXHAUSTCAPACITY", "0");
          strCondition1 += " " + getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "EXHAUSTCAPACITY_UNITS", "0");
          if (!strCondition1.equals("0 0")) {
            if (!bConditionOK)  {
              bConditionOK = true;                //Check whether to print heading for power requirements
              vPrintDetails.add("");
              vPrintDetails.add("Power Requirements");
              vPrintDetails.add("------------------");
            }
            vPrintDetails.add("Exhaust Capacity   :" + strCondition1);
          }

          strCondition1 = getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "SOUNDEMISS", "0");
          strCondition1 += " " + getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "SOUNDEMISSUNITS", "0");
          if (!strCondition1.equals("0 0")) {
            if (!bConditionOK)  {
              bConditionOK = true;                //Check whether to print heading for power requirements
              vPrintDetails.add("");
              vPrintDetails.add("Power Requirements");
              vPrintDetails.add("------------------");
            }
            vPrintDetails.add("Noise Level        :" + strCondition1);
          }

          strCondition1 = getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "LEAKAGESTARTCURRENT", "0");
          strCondition1 += " " + getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "LEAKAGESTARTCURRENT_UNITS", "0");
          if (!strCondition1.equals("0 0")) {
            if (!bConditionOK)  {
              bConditionOK = true;                //Check whether to print heading for power requirements
              vPrintDetails.add("");
              vPrintDetails.add("Power Requirements");
              vPrintDetails.add("------------------");
            }
            vPrintDetails.add("Lkge & Startin Curr:" + strCondition1);
          }


          strCondition1 = getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "POWERCONS", "0");
          strCondition1 += " " + getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "POWERCONSUNITS", "0");
          if (!strCondition1.equals("0 0")) {
            if (!bConditionOK)  {
              bConditionOK = true;                //Check whether to print heading for power requirements
              vPrintDetails.add("");
              vPrintDetails.add("Power Requirements");
              vPrintDetails.add("------------------");
            }
            vPrintDetails.add("Power Consumption  :" + strCondition1);
          }
          strCondition1 = getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "PWRCOMSUMENERGYSAVMODE", "0");
          strCondition1 += " " + getAttributeValue(eiEnvirinfo.getEntityType(), eiEnvirinfo.getEntityID(), "PWRCOMSUMENERGYSAVMODE_UNITS", "0");
          if (!strCondition1.equals("0 0")) {
            vPrintDetails.add("Power Cons. Saving :" + strCondition1);
          }

          strCondition1 = getAttributeValue(eiCommOF, "COMPTHEOPERF", "0");
          strCondition1 += " " + getAttributeValue(eiCommOF, "COMPTHEOPERFUNITS", "0");
          if (!strCondition1.equals("0 0")) {
            if (!bConditionOK)  {
              bConditionOK = true;                //Check whether to print heading for power requirements
              vPrintDetails.add("");
              vPrintDetails.add("Power Requirements");
              vPrintDetails.add("------------------");
            }
            vPrintDetails.add("Base Configuration :" + strCondition1);
          }

          printReport(true, strHeader, iColWidths, vPrintDetails);
          resetPrintvars();
        } else {
          logMessage("A_340.....is NULL");
        }


      }
    }

    println(":exmp.");
    println(".*$A_340_End");

    println(".*$A_342_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "HWREQTS", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_342_End");

    println(".*$A_344_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "NATLANGREGTSHW", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_344_End");

    println(".*$A_345_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A)  -> COMMERCIALOF (E) when classification of 'Software-Application-Base-N/A'
    //  when APPLICATIONTYPE = '33' (OperatingSystem)
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT","COFGRP","COFSUBGRP","APPLICATIONTYPE"};
    strFilterValue = new String[]{"Software", "Application","Base","N/A","33"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      println(getAttributeValue(eiCommOF, "DESCRIPTION", " "));
    }

    println(".*$A_345_End");

    println(".*$A_346_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "NATLANGREGTSSW", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_346_End");

    println(".*$A_348_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'
    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification of 'Hardware-%-%-%'
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT","COFGRP"};
    strFilterValue = new String[]{"Hardware", "System","Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
logMessage(" A_348_COMMERCIALOF");
  displayContents(vReturnEntities1);
    strParamList1 = new String[]{"COMPATIBILITY"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    strFilterAttr = new String[]{"OOFCAT"};
    strFilterValue = new String[]{"Hardware"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, null, null, true, true, "AVAIL");
    vReturnEntities2= searchEntityVectorLink(vReturnEntities1, null, null, true, false, "OOFAVAIL");
    vReturnEntities1= searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, false, "ORDEROF");
logMessage(" A_348_ORDEROF");
  displayContents(vReturnEntities1);
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{50};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_348_End");

    println(".*$A_354_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "LIMITATION", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_354_End");

    println(".*$A_356_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //User Group Requirements
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5507", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_356_End");

    println(".*$A_357_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'
    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification of 'Hardware-%-%-%'
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT","COFGRP"};
    strFilterValue = new String[]{"Hardware", "System","Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    logMessage("A_357*************COMMERCIALOF");
    strParamList1 = new String[]{"CUSTRESP"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    strFilterAttr = new String[]{"OOFCAT"};
    strFilterValue = new String[]{"Hardware"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, null, null, true, true, "AVAIL");
    logMessage("A_357*************");
    displayContents(vReturnEntities1);
    vReturnEntities2= searchEntityVectorLink(vReturnEntities1, null, null, true, false, "OOFAVAIL");
    logMessage("A_357*************");
    displayContents(vReturnEntities2);
    vReturnEntities1= searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    logMessage("A_357*************");
    displayContents(vReturnEntities1);
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{50};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_357_End");

    println(".*$A_360_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "CABLEORDER", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_360_End");

    println(".*$A_363_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'
    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification of 'Hardware-%-%-%'
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT","COFGRP"};
    strFilterValue = new String[]{"Hardware", "System","Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    strParamList1 = new String[]{"INSTALLABILITY"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    strFilterAttr = new String[]{"OOFCAT"};
    strFilterValue = new String[]{"Hardware"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, null, null, true, true, "AVAIL");
    vReturnEntities2= searchEntityVectorLink(vReturnEntities1, null, null, true, false, "OOFAVAIL");
    vReturnEntities1= searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{50};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_363_End");

    println(".*$A_364_Begin");
    println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "DIRECTCUSTSUPPORT", " ") + " ");
    println(".*$A_364_End");

    println(".*$A_365_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "DIRECTCUSSUPMVS", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_365_End");

    println(".*$A_366_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "DIRECTCUSSUPIND", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_366_End");

    println(".*$A_367_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Installation Support - Direct Customer Support - Network Products
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5489", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_367_End");

    println(".*$A_369_Begin");
    /*
     *  'ANNOUNCEMENT->AVAIL->COMMERCIALOF->SHIPINFO
     */
    /*
     *  This is PCD only....so commenting this off
     *  strFilterAttr = new String[]{"COFCAT"};
     *  strFilterValue = new String[]{"Hardware"};
     *  vReturnEntities1 = searchEntityGroupLink(grpCofAvail, strFilterAttr, strFilterValue, true, false, "COMMERCIALOF");
     *  vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFSHIP");
     *  for (i = 0; i < vReturnEntities2.size(); i++) {
     *  eiCofShip = (EntityItem) vReturnEntities2.elementAt(i);
     *  eiCommOF = (EntityItem) eiCofShip.getUpLink(0);
     *  eiShipInfo = (EntityItem) eiCofShip.getDownLink(0);
     *  strCondition1 = getAttributeValue(eiCommOF, "MACHTYPE", " ");
     *  strCondition1 += " : " + getAttributeValue(eiCommOF, "MODEL", " ");
     *  vPrintDetails.add(strCondition1);
     *  vPrintDetails.add(getAttributeValue(eiShipInfo.getEntityType(), eiShipInfo.getEntityID(), "BOXINSHIPGRP", " "));
     *  vPrintDetails.add(getAttributeValue(eiShipInfo.getEntityType(), eiShipInfo.getEntityID(), "SHIPGROUPTEXT_LT", " "));
     *  }
     *  iColWidths = new int[]{15, 12, 15};
     *  strHeader = new String[]{"Product", "Shipping grp.", "No. of Boxes"};
     *  println(":xmp.");
     *  println(".kp off");
     *  printReport(true, strHeader, iColWidths, vPrintDetails);
     *  resetPrintvars();
     *  println(":exmp.");
     */
    println(".*$A_369_End");

    println(".*$A_373_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Accessories and/or Supplies
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5504", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_373_End");

    println(".*$A_377_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Security, Auditability, and Control
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5497", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_377_End");

    println(".*$A_380_Begin");
    println(".*$A_380_End");

    println(".*$A_384_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"}; //Changed to 5530 from 5529 as per Ping
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5530", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_384_End");

    println(".*$A_391_Begin");
    println(".*$A_391_End");

    println(".*$A_393_Begin");
    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification
    //  'Hardware-%-%-%' -> OOFFUP (R) -> FUP (E) with classification of 'Hardware-%-%-%' child

    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification
    //  'Hardware-%-%-%' -> OOFPRICE (R) -> PRICEFININFO (E)
    strFilterAttr = new String[]{"OOFCAT"};
    strFilterValue = new String[]{"Hardware"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, null, null, true, true, "AVAIL");
    vReturnEntities2= searchEntityVectorLink(vReturnEntities1, null, null, true, false, "OOFAVAIL");
    vReturnEntities1= searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    logMessage("_393 ORDEROF'S");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      logMessage("_393 Processing "+eiOrderOF.getKey());
      //Navigate to FUP
      vReturnEntities2 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFFUP");
      strFilterAttr = new String[]{"FUPCAT"};
      strFilterValue = new String[]{"Hardware"};
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "FUP");
      eiNextItem =(vReturnEntities3.size() >0 ) ? (EntityItem) vReturnEntities3.elementAt(0):null;    //This is the FUP entity
      if (eiNextItem==null) { //Ignore this guy if there is no FUP
        logMessage("_393 No FUP linked to :"+eiOrderOF.getKey());
        continue;
      }


      //Navigate to PRICEFININFO
      vReturnEntities2 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFPRICE");
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
      eiPriceInfo =(vReturnEntities3.size() >0 ) ? (EntityItem) vReturnEntities3.elementAt(0):null;
      if (eiPriceInfo==null) {    //Ignore this guy if there is no PRICEFININFO
        continue;
      }

      strCondition1=getAttributeValue(eiNextItem, "FEATURECODE", "");
      if (strCondition1.equals("")) {
        logMessage("_393 Feature code is empty");
        continue;
      }

      vPrintDetails.add(getAttributeValue(eiOrderOF, "INVNAME", " "));
      vPrintDetails.add((eiNextItem!=null) ? getAttributeValue(eiNextItem, "FEATURECODE", ""):" ");
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MACHTYPE", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MODEL", " "));
      vPrintDetails.add((eiPriceInfo!=null) ? getAttributeValue(eiPriceInfo, "RENTALCHARGE", " "):" ");
      vPrintDetails.add((eiPriceInfo!=null) ? getAttributeValue(eiPriceInfo, "PURCHASEPRICE", " "):" ");
      //vPrintDetails1.add(getAttributeValue(eiOrderOF, "INSTALL", " "));
      if (foundInEntity(eiOrderOF, new String[]{"INSTALL"}, new String[]{"CE"}, true) || foundInEntity(eiOrderOF, new String[]{"INSTALL"}, new String[]{"CIF"}, true)) {
        vPrintDetails1.add("Y");
      } else {
        vPrintDetails1.add("N");
      }
      if (foundInEntity(eiOrderOF, new String[]{"INSTALL"}, new String[]{"NA"}, true)) {
        vPrintDetails1.add("Y");
      } else {
        vPrintDetails1.add("N");
      }
      if (foundInEntity(eiOrderOF, new String[]{"CABLESREQUIRED"}, new String[]{"Yes"}, true)) {
        vPrintDetails1.add("Y");
      } else {
        vPrintDetails1.add("N");
      }
      strCondition1 =(eiPriceInfo!=null) ? getAttributeValue(eiPriceInfo, "MESREMOVECHARGE", " "):" ";
      if (eiNextItem!=null && strCondition1.length()>0) {
        vPrintDetails1.add("Y");
      } else {
        vPrintDetails1.add("N");
      }
      vPrintDetails1.add((eiPriceInfo!=null) ? getAttributeValue(eiPriceInfo, "MESADDCHARGE", " "): " ");
      vPrintDetails1.add((eiPriceInfo!=null) ? getAttributeValue(eiPriceInfo, "MINMAINTMTHCHARGE", " "): " ");
      vPrintDetails1.add((eiPriceInfo!=null) ? getAttributeValue(eiPriceInfo, "ADDMAINTCHARGERATE", " "): " ");
      vPrintDetails1.add((eiPriceInfo!=null) ? getAttributeValue(eiPriceInfo, "MTHUSAGECHARGERATE", " "): " ");
    }

    //Print this report in 2 parts
    if (vPrintDetails.size() > 0 || vPrintDetails1.size() > 0) {
      bConditionOK = true;
      println(":xmp.");
      println(".kp off");
    }
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{19, 8, 9, 8, 7, 9};
      strHeader = new String[]{"Description        ", "Feature ", "Machine  ", "Model   ", "Rent   ", "Price"};
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
    }
    if (vPrintDetails1.size() > 0) {
      logMessage("A_393" + vPrintDetails1.toString());
      iColWidths = new int[]{5, 5, 5, 7, 7, 7, 7, 7};
      strHeader = new String[]{"Field", "Plant", "Cable", " Chg.", " Chg.", " Chg.", " Chg.", " Chg."};
      println("                    MES    MES     Min.    Add     Use");
      println("                   Remov.  Add     Maint. Maint.   Rate");
      printReport(true, strHeader, iColWidths, vPrintDetails1);
      vPrintDetails1.removeAllElements();
      vPrintDetails1 = null;
    }
    if (bConditionOK) {
      println(":exmp.");
    }
    println(".*$A_393_End");

    println(".*$A_394_Begin");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification 'Service-Maintenance-Base-N/A and
    //  SERVICETYPE = '6131' (ServiceElect) -> OOFMEMBERCOFOMG (R) -> COFOOFMGMTGRP (E) with
    //  classification of 'Service-Coverage-%-%' -> COFOWNSOOFOMG (R) -> COMMERCIALOF (E) with
    //  classification of 'Hardware-System-Base-%'  -> OOFMEMBERCOFOMG (R) -> ORDEROF with
    //  classification of 'Hardware-FeatureCode-%-%' -> OOFFUP (R) -> FUP (E) with classification of 'Hardware-%-%-%'

    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification 'Service-Maintenance-Base-N/A
    //  and SERVICETYPE = '6131' (ServiceElect) -> COFPRICE (R) -> PRICEFININFO (E)

    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP","COFSUBGRP", "SERVICETYPE"};
    strFilterValue = new String[]{"Service", "Maintenance", "Base","N/A", "ServiceElect"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    logMessage("A_394");
    displayContents(vReturnEntities1);

    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
//---------------------------------------------------
      //Navigate to ORDEROF from COMMOF
      vReturnEntities2 = searchEntityItemLink(eiCommOF, null, null, true, true, "OOFMEMBERCOFOMG");
    logMessage("A_394 OOFMEMBERCOFOMG");
    displayContents(vReturnEntities2);
      strFilterAttr = new String[]{"COFOOFMGCAT", "COFOOFMGSUBCAT"};
      strFilterValue = new String[]{"Service", "Coverage"};
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "COFOOFMGMTGRP");
    logMessage("A_394 COFOOFMGMTGRP");
    displayContents(vReturnEntities3);
      strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT"};
      strFilterValue = new String[]{"Hardware", "FeatureCode"};
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities3, strFilterAttr, strFilterValue, true, true, "ORDEROF");
    logMessage("A_394 ORDEROF");
    displayContents(vReturnEntities2);

      //Navigate to PRICEINFO from COMMOF
      vReturnEntities3 = searchEntityItemLink(eiCommOF, null, null, true, true, "COFPRICE");
    logMessage("A_394 COFPRICE");
    displayContents(vReturnEntities3);
      vReturnEntities4 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "PRICEFININFO");
    logMessage("A_394 PRICEFININFO");
    displayContents(vReturnEntities4);
      eiPriceInfo =(vReturnEntities4.size()>0 ) ? (EntityItem) vReturnEntities4.elementAt(0):null;  //PRICEINFO

      for (int y=0;y<vReturnEntities2.size();y++) {
        eiOrderOF = (EntityItem) vReturnEntities2.elementAt(y);
        vReturnEntities3 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFFUP");
    logMessage("A_394 OOFFUP");
    displayContents(vReturnEntities3);
        strFilterAttr = new String[]{"FUPCAT"};
        strFilterValue = new String[]{"Hardware"};
        vReturnEntities4 = searchEntityVectorLink(vReturnEntities3, strFilterAttr, strFilterValue, true, true, "FUP");
    logMessage("A_394 FUP");
    displayContents(vReturnEntities4);

        //Dont process if the whole chain does not exist
        if (vReturnEntities4.size()==0) {
          continue;
        }
        //'When ANNOUNCEMENT (ANNCODENAME) = COMMERCIALOF (ANNCODENAME) and when SERVICECODENAME = '109' (E95)
        //  or '110' (E24) or '111' (EZA) then print INVNAME,FEATURECODE,MACHTYPE,MODEL
        strFilterAttr = new String[]{"SERVICECODENAME","SERVICECODENAME","SERVICECODENAME"};
        strFilterValue = new String[]{"109","110","111"};
        if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,false)) {
    logMessage("A_394 FUP eiCommOF:"+eiCommOF.getEntityID());
          vPrintDetails.add(getAttributeValue(eiOrderOF, "INVNAME", " "));
          eiNextItem = (vReturnEntities4.size()>0 ) ? (EntityItem) vReturnEntities4.elementAt(0):null;  //FUP
          vPrintDetails.add(getAttributeValue(eiNextItem.getEntityType(), eiNextItem.getEntityID(), "FEATURECODE", " "));
          vPrintDetails.add(getAttributeValue(eiOrderOF, "MACHTYPE", " "));
          vPrintDetails.add(getAttributeValue(eiOrderOF, "MODEL", " "));
        } else {
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
          vPrintDetails.add(" ");
        }
        if (flagvalueEquals(eiCommOF, "SERVICECODENAME","110")) {
          vPrintDetails.add(getAttributeValue(eiCommOF, "SRVCDLVRYTYPE", " "));
        } else {
          vPrintDetails.add(" ");
        }
        strCondition1 = getAttributeValue(eiPriceInfo, "MAINTMTHCHARGE", "");
        if (strCondition1.trim().length() == 0) {
          strCondition1= getAttributeValue(eiPriceInfo, "MAINTYRLYCHARGE", " ");
        }
        vPrintDetails.add(strCondition1);
        if (flagvalueEquals(eiCommOF, "SERVICECODENAME","109")) {
          vPrintDetails.add(getAttributeValue(eiCommOF, "SRVCDLVRYTYPE", " "));
        } else {
          vPrintDetails.add(" ");
        }
        vPrintDetails.add(strCondition1);
        if (flagvalueEquals(eiCommOF, "SERVICECODENAME","111")) {
          vPrintDetails.add(getAttributeValue(eiCommOF, "SRVCDLVRYTYPE", " "));
        } else {
          vPrintDetails.add(" ");
        }
        vPrintDetails.add(strCondition1);

      }

    }
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{28, 4, 4, 5, 4, 4, 5};
      strHeader = new String[]{"Description", "Code", "Type", "Model", "Mth-", "---", "Annl>"};
      println(":xmp.");
      println(".kp off");
      println("                                                   On     On");
      println("                                                  Site   Site");
      println("                              Feat  Mach  Mach    24x7   9x5    Depot");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_394_End");
  }



  /**
   *  Description of the Method
   */
  private void processLongTo400() {
    println(".*$A_423_Begin");
    println(".*$A_423_End");

    println(".*$A_424_Begin");
    //ANNOUNCEMENT (E) -> ANNCOF (A) -> COMMERCIALOF (E)  with classification of 'Hardware-System-Base-% ->
    //  COFPRICE (R) -> PRICEFININFO (E)
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPRICE");
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
    strParamList1 = new String[]{"IBMGLOBALFINAPPL"};
    printValueListInVector(vReturnEntities3, strParamList1, " ", true, true);
    iColWidths = new int[]{15};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();

    println(".*$A_424_End");

    println(".*$A_425_Begin");
    println(".*$A_425_End");

    println(".*$A_426_Begin");
    strParamList1 = new String[]{"SRVCDURATION"};
    //'ANNOUNCEMENT (E) -> ANNCOF (A) -> COMMERCIALOF (E)  with classification of 'Service-Warranty-Base-% ->
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Service", "Warranty", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{15};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_426_End");

    println(".*$A_427_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5538", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_427_End");

    println(".*$A_428_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5539", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_428_End");

    println(".*$A_429_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5540", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_429_End");

    println(".*$A_432_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'
    strParamList1 = new String[]{"USAGEPLAN"};
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{55};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_432_End");

    println(".*$A_435_Begin");
    // Use vector from 432
    strParamList1 = new String[]{"AVGUSAGEPLANPROVISION"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{55};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_435_End");
/*
    println(".*$A_438_Begin");
    strParamList1 = new String[]{"SRVCBUSHOURS", "SRVCBUSHOURS"};
    //'ANNOUNCEMENT->AVAIL->ORDEROF with classification = 'service-warrenty-base-%'
    strFilterAttr = new String[]{"ORDEROFCAT", "ORDEROFSUBCAT", "ORDEROFGRP"};
    strFilterValue = new String[]{"4018", "4083", "4083"};
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{30, 30};
    strHeader = new String[]{"24Hr. 7Days/Week", "7am 6pm Mon Fri"};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(".*$A_438_End");

    println(".*$A_441_Begin");
    //'ANNOUNCEMENT->AVAIL->ORDEROF with classification = 'service-warrenty-base-%' use previous vector
    strParamList1 = new String[]{"SRVCBUSHOURS"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{55};
    strHeader = new String[]{"24Hr. 7Days/Week"};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(".*$A_441_End");
*/
    println(".*$A_447_Begin");
//    println(getAttributeValue(eiAnnounce, "HOURRATECLASS", " ") + " ");
   println(getAttributeShortFlagDesc(eiAnnounce, "HOURRATECLASS", " ") + " ");
    println(".*$A_447_End");

    println(".*$A_448_Begin");
    //println(getAttributeValue(eiAnnounce, "HOURRATECLASSEMEA", " ") + " ");
    println(getAttributeShortFlagDesc(eiAnnounce, "HOURRATECLASSEMEA", " ") + " ");
    println(".*$A_448_End");
/*
    println(".*$A_451_Begin");
    //'ANNOUNCEMENT->AVAIL->COMMERCIALOF with classification = 'service-warrenty-base-%' TBD...Service not found for COFSUBCAT
    strParamList1 = new String[]{"MACHTYPE"};
    strParamList2 = new String[]{"'MIDRANGE3YEARDISCOUNT", "MIDRANGE5YEARDISOUNT"};
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"632", "643", "650"};
    vReturnEntities1 = searchEntityGroupLink(grpCofAvail, strFilterAttr, strFilterValue, true, false, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPRICE");
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities2.elementAt(i);
      eiCommOF = (EntityItem) eiNextItem.getUpLink(0);
      eiPriceInfo = (EntityItem) eiNextItem.getDownLink(0);
      printValueListInItem(eiCommOF, strParamList1, " ", false, false);
      printValueListInItem(eiPriceInfo, strParamList2, " ", true, false);
    }
    iColWidths = new int[]{20, 20, 20};
    strHeader = new String[]{"Eligible Types", "3 Yr Discount", "5 Yr Discount"};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(".*$A_451_End");

    println(".*$A_453_Begin");
    //'ANNOUNCEMENT -> AVAIL -> COMMERCIALOF (with classification = 'service-warrenty-base-%') -> PRICEFININFO
    //Use previous vector
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiPriceInfo = (EntityItem) vReturnEntities2.elementAt(i);
      vPrintDetails.add("Yes");
      strCondition1 = getAttributeValue(eiPriceInfo, "NETWORK3DISCOUNT", "");
      strCondition1 += " : " + getAttributeValue(eiPriceInfo, "SYSTEM3DISCOUNT", "");
      if (strCondition1.trim().length() == 1) {
        strCondition1 = getAttributeValue(eiPriceInfo, "NETWORK5DISCOUNT", "");
        strCondition1 += " : " + getAttributeValue(eiPriceInfo, "SYSTEM5DISCOUNT", "");
      }
      strCondition1 = getAttributeValue(eiPriceInfo, "NETWORK3DISCOUNT", "");
      if (strCondition1.trim().length() == 0) {
        strCondition1 = getAttributeValue(eiPriceInfo, "SYSTEM3DISCOUNT", "");
      }
      vPrintDetails.add(strCondition1);
      strCondition1 = getAttributeValue(eiPriceInfo, "NETWORK5DISCOUNT", "");
      if (strCondition1.trim().length() == 0) {
        strCondition1 = getAttributeValue(eiPriceInfo, "SYSTEM5DISCOUNT", "");
      }
      vPrintDetails.add(strCondition1);

    }
    iColWidths = new int[]{6, 25, 12, 12};
    strHeader = new String[]{"Yes/No", "Network and/or System", "3 Yr Discount", "5 Yr Discount"};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(".*$A_453_End");
*/
    println(".*$A_454_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5531", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_454_End");
/*
    println(".*$A_459_Begin");
    strParamList1 = new String[]{"MONTHLYRENTAL"};
    vReturnEntities1 = searchEntityGroupLink(grpCofAvail, null, null, true, false, "COMMERCIALOF");
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{25};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_459_End");

    println(".*$A_468_Begin");
    for (i = 0; i < grpPriceInfo.getEntityItemCount(); i++) {
      eiPriceInfo = grpPriceInfo.getEntityItem(i);
      strCondition1 = getAttributeValue(eiPriceInfo, "PURCHASEOPTPERCENT", " ");
      if (!strCondition1.equals(" ")) {
      t  vPrintDetails.add(strCondition1 + "%");
      }
    }
    iColWidths = new int[]{25};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_468_End");
*/
    println(".*$A_480_Begin");
    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E)
    //  with classification = 'Hardware-%-%-%'
    strFilterAttr = new String[]{"OOFCAT"};
    strFilterValue = new String[]{"Hardware"};
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    logMessage(".*$A_480_Begin");
    displayContents(vReturnEntities1);
    strCondition1 = "No";
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOrderOF =  (EntityItem) vReturnEntities1.elementAt(i);
      if (foundInEntity(eiOrderOF, new String[]{"INSTALL"}, new String[]{"CE"}, true) || foundInEntity(eiOrderOF, new String[]{"INSTALL"}, new String[]{"CIF"}, true)) {
        strCondition1 = "Yes";
        break;
      }
    }
    println(strCondition1);
    println(".*$A_480_End");

    println(".*$A_481_Begin");
    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E)
    //  with classification = 'Hardware-ModelUpgrade-%-%' or Hardware-ModelConvert-%-%'
    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT"};
    strFilterValue = new String[]{"Hardware", "ModelUpgrade"};
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");

    bConditionOK = false;
    if (vReturnEntities1.size() > 0) {
      bConditionOK = true;
    } else {
      strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT"};
      strFilterValue = new String[]{"Hardware", "ModelConvert"};
      vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
      if (vReturnEntities1.size() > 0) {
        bConditionOK = true;
      }
    }

    if (bConditionOK) {
      println("Yes");
    } else {
      println("No");
    }
    println(".*$A_481_End");

    println(".*$A_482_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5534", "", true);
    if (vPrintDetails.size() > 0 )  {
      println("No");
    } else {
      printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5535", "", true);
      println(vPrintDetails.size() > 0 ? "Yes" : "No");
    }
    resetPrintvars();
    println(".*$A_482_End");


    println(".*$A_483_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5534", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_483_End");

    println(".*$A_490_Begin");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification = 'Hardware-System-Inital-% ->
    //  COFOWNSOOFOMG (R) -> COFOOFMGMTGRP (E) with classification of 'Hardware-Proclcard-%-%' ALAN TO ProcIcard
    strParamList1 = new String[]{"PERFORMANCEGROUP"};
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Initial"};
logMessage("A_490_Begin");
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
displayContents(vReturnEntities1);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFOWNSOOFOMG");
logMessage("A_490_Begin COFOWNSOOFOMG");
displayContents(vReturnEntities2);
    strFilterAttr = new String[]{"COFOOFMGCAT", "COFOOFMGSUBCAT"};
    strFilterValue = new String[]{"Hardware", "ProcIcard"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "COFOOFMGMTGRP");
logMessage("A_490_Begin COFOOFMGMTGRP");
displayContents(vReturnEntities3);
    printValueListInVector(vReturnEntities3, strParamList1, " ", true, true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_490_End");

    println(".*$A_491_Begin");
    println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "LICMACHINTCODE", ""));
    println(".*$A_491_End");

    println(".*$A_492_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5541", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_492_End");

    println(".*$A_493_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5542", "", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_493_End");

    println(".*$A_495_Begin");
    println(".*$A_495_End");

    println(".*$A_496_Begin");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%' ->
    //  COFPRICE (R) -> PRICEFININFO (E)
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPRICE");
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
    strParamList1 = new String[]{"EDUCATIONALLOW"};
    printValueListInVector(vReturnEntities3, strParamList1, " ", true, true);

    iColWidths = new int[]{25};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_496_End");
  }


  /**
   *  Description of the Method
   */
  private void processLongTo500() {
    println(".*$A_505_Begin");
    println(".*$A_505_End");

    println(".*$A_507_Begin");
    //------------------------------------------------------------------------
    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification
    //  'Hardware-%-%-%' -> OOFFUP (R) -> FUP (E) with classification of 'Hardware-%-%-%' child

    //'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification
    //  'Hardware-%-%-%' -> OOFPRICE (R) -> PRICEFININFO (E)
    strFilterAttr = new String[]{"OOFCAT"};
    strFilterValue = new String[]{"Hardware"};//Hardware
    vPrintDetails1=new Vector();
    vPrintDetails2 = new Vector();
    vPrintDetails3 = new Vector();
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, null, null, true, true, "AVAIL");
    vReturnEntities2= searchEntityVectorLink(vReturnEntities1, null, null, true, false, "OOFAVAIL");
    logMessage("*********A_507 OOFAVAIL");
    displayContents(vReturnEntities2);
    vReturnEntities1= searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    logMessage("*********A_507 ORDEROF");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      //Navigate to FUP
      vReturnEntities2 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFFUP");
      strFilterAttr = new String[]{"FUPCAT"};
      strFilterValue = new String[]{"Hardware"};
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "FUP");
      eiNextItem =(vReturnEntities3.size() >0) ? (EntityItem) vReturnEntities3.elementAt(0):null;    //This is the FUP entity

      //Navigate to PRICEFININFO
      vReturnEntities2 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFPRICE");
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
      eiPriceInfo =(vReturnEntities3.size() >0) ? (EntityItem) vReturnEntities3.elementAt(0):null;

      vPrintDetails.add(getAttributeValue(eiOrderOF, "INVNAME", " "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MACHTYPE", " "));
      vPrintDetails1.add(getAttributeValue(eiOrderOF, "MACHTYPE", " "));
      vPrintDetails2.add(getAttributeValue(eiOrderOF, "MACHTYPE", " "));
      vPrintDetails3.add(getAttributeValue(eiOrderOF, "MACHTYPE", " "));

      vPrintDetails.add(getAttributeValue(eiOrderOF, "MODEL", " "));
      vPrintDetails1.add(getAttributeValue(eiOrderOF, "MODEL", " "));
      vPrintDetails2.add(getAttributeValue(eiOrderOF, "MODEL", " "));
      vPrintDetails3.add(getAttributeValue(eiOrderOF, "MODEL", " "));

      vPrintDetails.add(eiNextItem!=null ? getAttributeValue(eiNextItem, "FEATURECODE", " "):" ");
      vPrintDetails1.add(eiNextItem!=null ? getAttributeValue(eiNextItem, "FEATURECODE", " "):" ");
//      vPrintDetails.add(getAttributeValue(eiOrderOF, "AASPN", " "));
      vPrintDetails.add(eiPriceInfo!=null ? getAttributeValue(eiPriceInfo, "RENTALCHARGE", " "): " ");
      vPrintDetails1.add(eiPriceInfo!=null ? getAttributeValue(eiPriceInfo, "PURCHASEPRICE", " "): " ");
      strCondition1 = getAttributeValue(eiOrderOF, "INSTALL", " ");
      vPrintDetails1.add((strCondition1.equals("CE") || strCondition1.equals("CIF") ? "Y" : "N"));
      vPrintDetails1.add((strCondition1.equals("N/A") ? "Y" : "N"));
      vPrintDetails1.add(eiPriceInfo!=null ? getAttributeValue(eiPriceInfo, "MESREMOVECHARGE", " "):" ");
      vPrintDetails2.add(eiPriceInfo!=null ? getAttributeValue(eiPriceInfo, "MESADDCHARGE", " "):" ");
      vPrintDetails2.add(getAttributeValue(eiOrderOF, "CABLESREQUIRED", "No"));
      strCondition1 = eiPriceInfo!=null ? getAttributeValue(eiPriceInfo, "MINMAINTMTHCHARGE", " "): " ";
      strCondition2 = eiPriceInfo!=null ? getAttributeValue(eiPriceInfo, "MINMAINTYEARCHARGE", " "): " ";
      vPrintDetails3.add((!strCondition1.equals(" ") ? strCondition1 : strCondition2));
      vPrintDetails3.add(eiPriceInfo!=null ? getAttributeValue(eiPriceInfo, "ADDMAINTCHARGERATE", " "):" ");
      vPrintDetails3.add(eiPriceInfo!=null ?getAttributeValue(eiPriceInfo, "MTHUSAGECHARGERATE", " "):" ");
    }
    //-----------------------------------------------------------------------
    strHeader = new String[]{"Description", "MType", "Model", "FeatNo","Rental Chg."};
    iColWidths = new int[]{17, 6, 5, 6, 11};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();

    strHeader = new String[]{"MType", "Model","FeatNo", "Purch. Price", "FieldInstall", "PlantInstall", "MESRemoval"};
    iColWidths = new int[]{6, 5, 6,12, 12, 12, 10};
    printReport(true, strHeader, iColWidths, vPrintDetails1);
    if (vPrintDetails1.size() > 0) {
      vPrintDetails1.removeAllElements();
    }

    strHeader = new String[]{"Machine Type", "Model", "MES Add chg.", "Cable Req."};
    iColWidths = new int[]{12, 9, 12, 12};
    printReport(true, strHeader, iColWidths, vPrintDetails2);
    if (vPrintDetails2.size() > 0) {
      vPrintDetails2.removeAllElements();
    }

    strHeader = new String[]{"MType", "Model", "Min Maint Chg.", "Addl Maint Chg.", "Monthly Use Chg."};
    iColWidths = new int[]{6, 5, 14, 15, 15};
    printReport(true, strHeader, iColWidths, vPrintDetails3);
    if (vPrintDetails3.size() > 0) {
      vPrintDetails3.removeAllElements();
    }
    println(":exmp.");
    println(".*$A_507_End");

    println(".*$A_509_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%' ->
    //  COFPUBS (R) -> PUBLICATION (E) with PUBTYPE = '5022' (Promotion Material) or '5023' (Bill of Form (BOF))
    //  or '5024' (Service) or '5025' (Other)

    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%' ->
    //  COFPUBS (R) -> PUBLICATION (E) with PUBTYPE = '5022' (Promotion Material) or '5023' (Bill of Form (BOF))
    //  or '5024' (Service) or '5025' (Other) -> PUBPRICE (R) -> PRICEFININFO (E)
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
logMessage("$A_509***************");
displayContents(vReturnEntities1);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPUBS");
logMessage("$A_509***************cofpubs");
displayContents(vReturnEntities2);
    strFilterAttr = new String[]{"PUBTYPE", "PUBTYPE", "PUBTYPE","PUBTYPE"};
    strFilterValue = new String[]{"5022", "5023", "5024","5025"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "PUBLICATION");
logMessage("$A_509***************PUBLICATION");
displayContents(vReturnEntities3);
    for (i = 0; i < vReturnEntities3.size(); i++) {
      eiPublication = (EntityItem) vReturnEntities3.elementAt(i);
      vReturnEntities2 = searchEntityItemLink(eiPublication, null, null, true, true, "PUBPRICE");
      eiNextItem = (vReturnEntities2.size() > 0 ? (EntityItem) vReturnEntities2.elementAt(0) : null);
logMessage("$A_509***************PUBPRICE");
displayContents(vReturnEntities2);
      if (eiNextItem != null) {
        eiPriceInfo = (EntityItem) eiNextItem.getDownLink(0);
        strCondition1 = getAttributeValue(eiPublication, "PUBTITLE", " ");
        vPrintDetails.add(strCondition1);
        vPrintDetails.add(getAttributeValue(eiPublication, "ORDERPN", " "));
        vPrintDetails.add(getAttributeValue(eiPriceInfo, "PRICEVALUE", " "));
      } else {
logMessage("$A_509***************NO PRICEINFO FOUND");
      }
    }
    strHeader = new String[]{"Title", "Order No.", "Charge"};
    iColWidths = new int[]{15, 10, 10};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(".*$A_509_End");
    println(".*$A_513_Begin");
    //'When a path of ANNOUNCEMENT (ANNCODENAME) = COMMERCIALOF (ANNCODENAME) with classification of
    //  'Service-%-%-% and SERVICETYPE = '%' and SRVCDLRYTYPE = '%' -> COFPUBS (R) -> PUBLICATION (E) ->
    //  PUBPRICE (R) -> PRICEFININFO (E) exist then answer 'Yes' otherwise answer 'No'
    strFilterAttr = new String[]{"COFCAT"} ;
    strFilterValue = new String[]{"Service"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPUBS");
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PUBLICATION");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "PUBPRICE");
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
    if (vReturnEntities3.size() > 0) {
      println("Yes");
    } else {
      println("No");
    }
    println(".*$A_513_End");

    println(".*$A_515_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification = 'Service-Maintenance-Base-%'
    //  -> COFPRICE (R) -> PRICEFININFO (E)
    strFilterAttr = new String[]{"COFCAT","COFSUBCAT","COFGRP"};
    strFilterValue = new String[]{"Service","Maintenance","Base"};
    logMessage("*****************A_515_Searching COMMERCIALOF");
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    logMessage("*****************A_515_Begin");
    displayContents(vReturnEntities1);

    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities2 = searchEntityItemLink(eiCommOF, null, null, true, true, "COFPRICE");
      bConditionOK = false;
      strCondition1=" ";
      strCondition2=" ";
      strCondition3 = " ";
      strCondition4 = " ";
    logMessage("*****************A_515_COFPRICE");
    displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
    logMessage("*****************A_515_PRICEFININFO");
    displayContents(vReturnEntities3);
      eiPriceInfo =(vReturnEntities3.size() >0 ) ? (EntityItem) vReturnEntities3.elementAt(0):null;
      //  when SERVICETYPE = '6132' (ICA Legacy) and SRVCDLVRYTYPE = '6133' (IOR) and SERVICECODENAME = '100' (IW8)
      strFilterAttr = new String[]{"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue = new String[] {"6132","6133","100"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
    logMessage("*****************1) A_515 "+eiCommOF.getKey()+":"+eiPriceInfo.getKey());
        strCondition1 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6131' (ICA Legacy) and SRVCDLVRYTYPE = '6135' (IOE) and SERVICECODENAME = '100' (IW8) -> COFPRICE (R) -> PRICEININFO (E)

      strFilterAttr = new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue = new String[] {"6132","6135","100"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
    logMessage("*****************2) A_515 "+eiCommOF.getKey()+":"+eiPriceInfo.getKey());
        strCondition2 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }

      //SERVICETYPE = '6132' (ICA Legacy) and SRVCDLVRYTYPE = '6133' (IOR) and SERVICECODENAME = '101' (IWR) ->
      strFilterAttr =new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue =new String[] {"6132","6133","101"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
    logMessage("*****************3) A_515 "+eiCommOF.getKey()+":"+eiPriceInfo.getKey());
        strCondition3 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6132' (ICA Legacy) and SRVCDLVRYTYPE = '6135' (IOE) and SERVICECODENAME = '101' (IWR) -
      strFilterAttr =new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue =new String[] {"6132","6135","101"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
    logMessage("*****************4) A_515 "+eiCommOF.getKey()+":"+eiPriceInfo.getKey());
        strCondition4 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      if (bConditionOK) {
        vPrintDetails.add(getAttributeValue(eiCommOF, "MACHTYPE", " "));
        vPrintDetails.add(getAttributeValue(eiCommOF, "MODEL", " "));
        vPrintDetails.add(strCondition1);
        vPrintDetails.add(strCondition2);
        vPrintDetails.add(strCondition3);
        vPrintDetails.add(strCondition4);
      }
    }
    strHeader = new String[]{"Machine Type", "Model", "IOR 9x5", "IOE 9x5", "IOR 24x7", "IOE 24x7"};
    iColWidths = new int[]{12, 10, 10, 10, 10, 10};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(".*$A_515_End");

    println(".*$A_516_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification = 'Service-Warranty-Upgrade-%'
    //  -> COFPRICE (R) -> PRICEFININFO (E)
    strFilterAttr = new String[]{"COFCAT","COFSUBCAT","COFGRP"};
    strFilterValue = new String[]{"Service","Warranty","Upgrade"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      bConditionOK = false;
      strCondition1=" ";
      strCondition2=" ";
      strCondition3 = " ";
      strCondition4 = " ";
      strCondition5 = " ";
      vReturnEntities2 = searchEntityItemLink(eiCommOF, null, null, true, true, "COFPRICE");
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
      eiPriceInfo =(vReturnEntities3.size() >0) ? (EntityItem) vReturnEntities3.elementAt(0):null;
      //SERVICETYPE = '6131' (ServiceElect) and SRVCDLVRYTYPE = '6133' (IOR) and SERVICECODENAME = '107' (W95) ->
      strFilterAttr = new String[]{"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue =new String[] {"6131","6133","107"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition1 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6131' (ServiceElect) and SRVCDLVRYTYPE = '6135' (IOE) and SERVICECODENAME = '107' (W95)
      strFilterAttr =new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue = new String[]{"6131","6135","107"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition2 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6131' (ServiceElect) and SRVCDLVRYTYPE = '6133' (IOR) and SERVICECODENAME = '108' (W24)
      strFilterAttr = new String[]{"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue =new String[] {"6131","6133","108"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition3 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6131' (ServiceElect) and SRVCDLVRYTYPE = '6135' (IOE) and SERVICECODENAME = '108' (W24)
      strFilterAttr =new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue = new String[]{"6131","6135","108"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition4 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      if (bConditionOK) {
        vPrintDetails.add(getAttributeValue(eiCommOF, "MACHTYPE", " "));
        vPrintDetails.add(getAttributeValue(eiCommOF, "MODEL", " "));
        vPrintDetails.add(strCondition1);
        vPrintDetails.add(strCondition2);
        vPrintDetails.add(strCondition3);
        vPrintDetails.add(strCondition4);
        vPrintDetails.add(strCondition5);
      }
    }

    strFilterAttr = new String[]{"COFCAT","COFSUBCAT","COFGRP"};
    strFilterValue = new String[]{"Service","Maintenance","Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      bConditionOK = false;
      strCondition1=" ";
      strCondition2=" ";
      strCondition3 = " ";
      strCondition4 = " ";
      strCondition5 = " ";
      vReturnEntities2 = searchEntityItemLink(eiCommOF, null, null, true, true, "COFPRICE");
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
      eiPriceInfo =(vReturnEntities3.size() >0) ? (EntityItem) vReturnEntities3.elementAt(0):null;
        //SERVICETYPE = '6131' (ServiceElect) and SRVCDLVRYTYPE = '6133' (IOR) and SERVICECODENAME = '109' (E95)
      strFilterAttr = new String[]{"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue =new String[] {"6131","6133","109"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition1 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6131' (ServiceElect) and SRVCDLVRYTYPE = '6135' (IOE) and SERVICECODENAME = '109' (E95)
      strFilterAttr = new String[]{"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue = new String[]{"6131","6135","109"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition2 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }

      //SERVICETYPE = '6131' (ServiceElect) and SRVCDLVRYTYPE = '6133' (IOR) and SERVICECODENAME = '108' (E24)
      strFilterAttr =new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue =new String[] {"6131","6133","108"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition3 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6131' (ServiceElect) and SRVCDLVRYTYPE = '6135' (IOE) and SERVICECODENAME = '108' (E24)
      strFilterAttr =new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue =new String[] {"6131","6135","108"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition4 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6131' (ServiceElect) and SRVCDLVRYTYPE = '6134' (EasyServ) and SERVICECODENAME = '111' (EZA)
      strFilterAttr = new String[]{"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue =new String[] {"6131","6134","111"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition5 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      if (bConditionOK) {
        vPrintDetails.add(getAttributeValue(eiCommOF, "MACHTYPE", " "));
        vPrintDetails.add(getAttributeValue(eiCommOF, "MODEL", " "));
        vPrintDetails.add(strCondition1);
        vPrintDetails.add(strCondition2);
        vPrintDetails.add(strCondition3);
        vPrintDetails.add(strCondition4);
        vPrintDetails.add(strCondition5);
      }
    }
    strHeader = new String[]{"Machine Type", "Model", "IOR 9x5", "IOE 9x5", "IOR 24x7", "IOE 24x7", "EZ"}; //, "IOR 9x5", "IOE 9x5", "IOR 24x7", "IOE 24x7"};
    iColWidths = new int[]{12, 10, 9, 9, 9, 9, 9 }; //, 9, 9, 9, 9};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(".*$A_516_End");

    println(".*$A_517_Begin");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification = 'Service-Maintenance-Base-%'
    //  -> COFPRICE (R) -> PRICEFININFO (E)
    strFilterAttr = new String[]{"COFCAT","COFSUBCAT","COFGRP"};
    strFilterValue = new String[]{"Service","Maintenance","Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      bConditionOK = false;
      strCondition1=" ";
      strCondition2=" ";
      strCondition3 = " ";
      strCondition4 = " ";
      strCondition5 = " ";
      vReturnEntities2 = searchEntityItemLink(eiCommOF, null, null, true, true, "COFPRICE");
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
      eiPriceInfo =(vReturnEntities3.size() >0) ? (EntityItem) vReturnEntities3.elementAt(0):null;
      //SERVICETYPE = '6132' (ICA Legacy) and SRVCDLVRYTYPE = '6134' (EasyServ) and SERVICECODENAME = '102' (IZA) -
      strFilterAttr = new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue = new String[] {"6132","6134","102"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition1 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6132' (ICA Legacy) and SRVCDLVRYTYPE = '6133' (IOR) and SERVICECODENAME = '103' (IO8)
      strFilterAttr = new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue = new String[] {"6132","6133","103"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition2 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6132' (ICA Legacy) and SRVCDLVRYTYPE = '6135' (IOE) and SERVICECODENAME = '104' (IE5)
      strFilterAttr = new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue = new String[] {"6132","6135","104"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition3 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6132' (ICA Legacy) and SRVCDLVRYTYPE = '6133' (IOR) and SERVICECODENAME = '105' (IOR)
      strFilterAttr = new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue = new String[] {"6132","6133","105"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition4 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      //SERVICETYPE = '6132' (ICA Legacy) and SRVCDLVRYTYPE = '6135' (IOE) and SERVICECODENAME = '106' (IOE)
      strFilterAttr = new String[] {"SERVICETYPE","SRVCDLVRYTYPE","SERVICECODENAME"} ;
      strFilterValue =  new String[]{"6132","6135","106"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,true)) {
        strCondition5 = getAttributeValue(eiPriceInfo, "PRICEVALUE", " ");
        bConditionOK = true;
      }
      if (bConditionOK) {
        vPrintDetails.add(getAttributeValue(eiCommOF, "MACHTYPE", " "));
        vPrintDetails.add(getAttributeValue(eiCommOF, "MODEL", " "));
        vPrintDetails.add(strCondition1);
        vPrintDetails.add(strCondition2);
        vPrintDetails.add(strCondition3);
        vPrintDetails.add(strCondition4);
        vPrintDetails.add(strCondition5);
      }
    }
    strHeader = new String[]{"Machine Type", "Model","EZ", "IOR 9x5", "IOE 9x5", "IOR 24x7", "IOE 24x7"};
    iColWidths = new int[]{12, 10,9, 9, 9, 9, 9};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(".*$A_517_End");

    println(".*$A_518_Begin");
    println(".*$A_518_End");

    println(".*$A_520_Begin");
    println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "AMCALLCENTER", ""));
    println(".*$A_520_End");

//    println(".*$A_521_Begin");
//    println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "AMCALLCENTER", ""));
//    println(".*$A_521_End");
    println(".*$A_525_Begin");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification = 'Service-Maintenance-%'
    //   COFPRICE (R) -> PRICEFININFO (E)
    strFilterAttr = new String[]{"COFCAT","COFSUBCAT"};
    strFilterValue = new String[]{"Service","Maintenance"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
logMessage("A_525_***********SERVICE MAINTENANCE ");
displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      //SERVICETYPE = '6132' (ICA Legacy) or '6131' (ServiceElect) and SRVCDLVRYTYPE = '6135' (IOE)
      //  and SERVICECODENAME = '100' (IW8) or '101' (IWR) or '107' (W95) or '108' (W24) or '109' (E95) or
      //  '110' (E24) or '104' (IE5) or '106' (IOE)
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities2 = searchEntityItemLink(eiCommOF, null, null, true, true, "COFPRICE");
logMessage("A_525_***********COFPRICE ");
displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
logMessage("A_525_***********PRICEFININFO");
displayContents(vReturnEntities3);
      eiPriceInfo =(vReturnEntities3.size() >0) ? (EntityItem) vReturnEntities3.elementAt(0):null;
      strFilterAttr = new String[] {"COFGRP","COFGRP"};
      strFilterValue = new String[] {"Base","Upgrade"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,false))  {
logMessage("A_525_***********COMMOF GRP OK");
        strFilterAttr =  new String[] {"SERVICETYPE","SERVICETYPE"};
        strFilterValue = new String[] {"6132","6131"};
        if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,false) &&
          flagvalueEquals(eiCommOF,"SRVCDLVRYTYPE","6135")){
logMessage("A_525_***********COMMOF SERVICETYPE OK");
           strFilterAttr = new String[] {"SERVICECODENAME","SERVICECODENAME","SERVICECODENAME","SERVICECODENAME","SERVICECODENAME","SERVICECODENAME","SERVICECODENAME","SERVICECODENAME"} ;
           strFilterValue = new String[] {"100","101","107","108","109","110","104","106"};
          if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,false) )  {
logMessage("A_525_***********COMMOF SERVICECODENAMETYPE OK");
            vPrintDetails.add(getAttributeValue(eiCommOF, "SRVCDLVRYTYPE", ""));
            vPrintDetails.add(getAttributeValue(eiPriceInfo, "BILLABLEEXCHANGEPRICE", "Price not found"));
          }
        }
      }
    }

    //Repeat this again
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification = 'Service-Warranty
    strFilterAttr = new String[]{"COFCAT","COFSUBCAT"};
    strFilterValue = new String[]{"Service","Warranty"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
logMessage("A_525_***********SERVICE WARRANTY ");
displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      //SERVICETYPE = '6132' (ICA Legacy) or '6131' (ServiceElect) and SRVCDLVRYTYPE = '6135' (IOE)
      //  and SERVICECODENAME = '100' (IW8) or '101' (IWR) or '107' (W95) or '108' (W24) or '109' (E95) or
      //  '110' (E24) or '104' (IE5) or '106' (IOE) -> COFPRICE (R)
      eiCommOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities2 = searchEntityItemLink(eiCommOF, null, null, true, true, "COFPRICE");
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
      eiPriceInfo =(vReturnEntities3.size() >0 ) ? (EntityItem) vReturnEntities3.elementAt(0):null;
      strFilterAttr = new String[] {"COFGRP","COFGRP"};
      strFilterValue =  new String[]{"Base","Upgrade"};
      if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,false)) {
        strFilterAttr = new String[] {"SERVICETYPE","SERVICETYPE"};
        strFilterValue =  new String[]{"6132","6131"};
        if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,false) &&
          flagvalueEquals(eiCommOF.getEntityType(),eiCommOF.getEntityID(),"SRVCDLVRYTYPE","6135")){
           strFilterAttr = new String[] {"SERVICECODENAME","SERVICECODENAME","SERVICECODENAME","SERVICECODENAME","SERVICECODENAME","SERVICECODENAME","SERVICECODENAME","SERVICECODENAME"} ;
           strFilterValue = new String[] {"100","101","107","108","109","110","104","106"};
          if (foundInEntity(eiCommOF,strFilterAttr,strFilterValue,false) )  {
            vPrintDetails.add(getAttributeValue(eiCommOF, "SRVCDLVRYTYPE", ""));
            vPrintDetails.add(getAttributeValue(eiPriceInfo, "BILLABLEEXCHANGEPRICE", "Price not found"));
          }
        }
      }

    }

    iColWidths = new int[]{30,15};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_525_End");

    println(".*$A_526_Begin");
    //''COFCAT='Hardware' + COFSUBCAT='System' + COFGRP='Upgrade'
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Upgrade"};
    vReturnEntities1 = searchEntityGroupLink(grpCofAvail, strFilterAttr, strFilterValue, true, false, "COMMERCIALOF");
    if (vReturnEntities1.size() > 0) {
      println("Yes");
    } else {
      println("No");
    }
    println(".*$A_526_End");

    println(".*$A_527_Begin");
    println(":h3.Model Upgrades:");
    println(":p.");

    /*
     *  Model Conversions
     *  AVAIL > ORDEROF(with Hardware-ModelUpgrade)
     */
    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT"};
    strFilterValue = new String[]{"Hardware", "ModelUpgrade"};//Hardware,ModelConvert

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    displayContents(vReturnEntities1);

    /*
     *  Print for Model Upgrades
     */
    i = 0;
    println(":xmp.");
    println(".in 0");
    println(".kp off");

    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vPrintDetails.add(getAttributeValue(eiOrderOF, "FROMMODEL", "   "));
      vPrintDetails.add(getAttributeValue(eiOrderOF, "MODEL", "   "));
      vReturnEntities2 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFPRICE");
logMessage("A_527_Begin      OOFPRICE");
displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
      eiPriceInfo = vReturnEntities3.size() >0 ? (EntityItem) vReturnEntities3.elementAt(0) : null;
      vPrintDetails.add(eiPriceInfo!=null ? getAttributeValue(eiPriceInfo, "PRICEVALUE", "0.00"): "0.00");
    }
    iColWidths = new int[]{5,5,16};
    strHeader = new String[]{"From"," To","Purchase Price*"};
    if (vPrintDetails.size()>0) {
      println("Model Model Model Conversion");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      println("* Parts removed or replaced become the property of IBM and must");
      println("be returned.");
    }


    resetPrintvars();
    println(":exmp.");
    println(".*$A_527_End");

    println(".*$A_528_Begin");
    String[] strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT"};
    String[] strFilterValue = new String[]{"Hardware", "FeatureConvert"};

    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
    logMessage("A_528");
    displayContents(vReturnEntities1);
    for (i=0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vPrintDetails.add(" "+getAttributeValue(eiOrderOF, "FROMFEATURECODE", "    "));
      vPrintDetails.add(" "+getAttributeValue(eiOrderOF, "FEATURECODE", "    "));

      strCondition1 = getAttributeValue(eiOrderOF, "RETURNEDPARTS", " ");
      vPrintDetails.add(" "+(strCondition1.equals("Yes") ? "  Y " : "  N "));
      strCondition1 = getAttributeValue(eiOrderOF, "CONTMAINTENANCE", " ");
      vPrintDetails.add(" "+(strCondition1.equals("Yes") ? "  Y " : "  N "));
      vReturnEntities2 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFPRICE");
logMessage("A_528_Begin      OOFPRICE");
displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
      eiPriceInfo = vReturnEntities3.size() >0 ? (EntityItem) vReturnEntities3.elementAt(0) : null;
      vPrintDetails.add(eiPriceInfo!=null ? getAttributeValue(eiPriceInfo, "PRICEVALUE", "0.00"): "0.00");
    }
    iColWidths = new int[]{5,5,8,11,18};
    strHeader = new String[]{"From","  To"," Parts*","Maintenance","Purchase Price"};
    println(":xmp.");
    println(".kp off");
    //rfaReport.setColumnSeparator("    ");
    if (vPrintDetails.size() > 0) {
      println("Feat   Feat Returned Continuous  Feature Conversion");
    }
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println("* Parts removed or replaced become the property of IBM and must");
    println("  be returned");
    println(":exmp.");

    println(".*$A_528_End");

    println(".*$A_529_Begin");
    //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E)
    //  with classification of 'Hardware-FeatureCode-%-%'
    strFilterAttr = new String[]{"OOFCAT", "OOFSUBCAT"};
    strFilterValue = new String[]{"Hardware", "FeatureCode"};
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true,false, "ORDEROF");
logMessage("A_529_Begin");
displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      vReturnEntities2 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFPRICE");
logMessage("A_529_Begin      OOFPRICE");
displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
logMessage("A_529_Begin      OOFPRICE");
displayContents(vReturnEntities3);
      // OOFPRICE (R) -> PRICEFININFO (E) exist
      if (vReturnEntities3.size() > 0)  {
        vPrintDetails.add(getAttributeValue(eiOrderOF, "INVNAME", " "));
        vPrintDetails.add(getAttributeValue(eiOrderOF, "FEATURECODE", " "));

        eiPriceInfo = (vReturnEntities3.size() > 0 ? (EntityItem) vReturnEntities3.elementAt(0) : null);

        vPrintDetails.add(eiPriceInfo != null ? getAttributeValue(eiPriceInfo, "FIELDINSTALLCHG", " ") : " ");
      }
    }
    iColWidths = new int[]{35, 10, 10};
    strHeader = new String[]{"Description", "Feature", "Charge"};
    println(":xmp.");
    println(".kp off");
    if (vPrintDetails.size()>0) {
      println("The following charges apply to features ordered for field installation");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
    }
    println(":exmp.");

    println(".*$A_529_End");

    println(".*$A_530_Begin");

    //6/5 notes:  if reo geo = emea  = 130 print all,,,,
    //if emea is less than 60 instances then type teh inclusion.  if > type exclusion....  Go with exclusion.
    GeneralAreaGroup geGrEmea = m_geList.getRfaGeoEMEAInclusion(eiAnnounce);
    logMessage("A_530_Begin returned GEItemcount"+geGrEmea.getGeneralAreaItemCount());
    geGrEmea = m_geList.getRfaGeoEMEAInclusion(eiAnnounce);
    if (m_geList.isRfaGeoEMEA(eiAnnounce))  {
      j = geGrEmea.getGeneralAreaItemCount();
      if (j>=130) {
        println("All European, Middle Eastern and African Countries");
      } else {
        if (j<60)  {
          println("Only in the following  European, Middle Eastern and African Countries: ");
          strCondition1 = "";
          for (int i=0; i < geGrEmea.getGeneralAreaItemCount(); i++) {
            GeneralAreaItem gai = geGrEmea.getGeneralAreaItem(i);
            strCondition1 += (strCondition1.length() >0 ? " ,":"") + gai.getName();
          }

          prettyPrint(strCondition1, 69);
        }else if (j>60 && j<130)  {       //Get the exclusion list here
          geGrEmea = m_geList.getRfaGeoEMEAExclusion(eiAnnounce);
          println("All  European, Middle Eastern and African Countries except:");
          strCondition1="";
          for (int i=0; i < geGrEmea.getGeneralAreaItemCount(); i++) {
            GeneralAreaItem gai = geGrEmea.getGeneralAreaItem(i);
            strCondition1 += (strCondition1.length() >0 ? " ,":"") + gai.getName();
          }
          prettyPrint(strCondition1,69);
        }
      }

    }
    println(".*$A_530_End");

    println(".*$A_531_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "EXTERNALLETATTACH", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_531_End");

    println(".*$A_532_Begin");
    println(".*$A_532_End");

    println(".*$A_533_Begin");
    //println(getAttributeValue(eiAnnounce, "PRODPOSITIONING", ""));
    println(".*$A_533_End");

    println(".*$A_534_Begin");
    //println(getAttributeValue(eiAnnounce, "MKTSTRATEGY", ""));
    println(".*$A_534_End");

    println(".*$A_535_Begin");
    /*
    if (foundInGroup(grpCommOF, "COMPTHEOPERF", null)) {
      println("Yes");
    } else {
      if (foundInGroup(grpOrderOF, "COMPTHEOPERF", null)) {
        println("Yes");
      } else {
        println("No");
      }
    }
    */
    println(".*$A_535_End");

    println(".*$A_536_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5526", " ", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_536_End");

    println(".*$A_538_Begin");
    //println(getAttributeValue(eiAnnounce, "BUSINESS_SHOWS", ""));
    println(".*$A_538_End");

    println(".*$A_543_Begin");
    //println(getAttributeValue(eiAnnounce, "MAINTSERVMKT", ""));
    println(".*$A_543_End");

    println(".*$A_545_Begin");
    //println(getAttributeValue(eiAnnounce, "ENTRYSYSSERVAMEND", ""));
    println(".*$A_545_End");

    println(".*$A_546_Begin");
    println(".*$A_546_End");

    println(".*$A_570_Begin");
    //println(getAttributeValue(eiAnnounce, "MKTTECHSUPPORT", ""));
    println(".*$A_570_End");

    println(".*$A_573_Begin");
    //println(getAttributeValue(eiAnnounce, "PROMOPROGRAM", ""));
    println(".*$A_573_End");

    println(".*$A_576_Begin");
    //println(getAttributeValue(eiAnnounce, "DIBPTYPE", ""));
    println(".*$A_576_End");

    println(".*$A_582_Begin");
    //pcd
    println(".*$A_582_End");

    println(".*$A_585_Begin");
    //println(getAttributeValue(eiAnnounce, "ADSUPPORT", ""));
    println(".*$A_585_End");

    println(".*$A_594_Begin");
    //pcd
    println(".*$A_594_End");

    println(".*$A_595_Begin");
    /*
    strParamList1 = new String[]{"BDEPTH_METRIC", "BHEIGHT_METRIC", "BWIDTH_METRIC", "BWEIGHT_METRIC"};
    printValueListInGroup(grpPackaging, strParamList1);
    iColWidths = new int[]{10, 10, 10, 10};
    println(":xmp.");
    println(".kp off");
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    */
    println(".*$A_595_End");
    println(".*$A_597_Begin");
    //pcd
    println(".*$A_597_End");

    println(".*$A_599_Begin");
    //pcd
    println(".*$A_599_End");
  }


  /**
   *  Description of the Method
   */
  private void processLongTo600() {

    println(".*$A_600_Begin");
    //pcd
    println(".*$A_600_End");

    println(".*$A_604_Begin");
    //pcd
    println(".*$A_604_End");

    println(".*$A_607_Begin");
    //pcd
    println(".*$A_607_End");

    println(".*$A_608_Begin");
    //pcd
    println(".*$A_608_End");

    println(".*$A_620_Begin");

/*    printValueInGroup(grpAvail, "EFFECTIVEDATE", true);
    iColWidths = new int[]{15};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_620_End");

    println(".*$A_624_Begin");
    println(getAttributeValue(eiAnnounce, "TRANSPLAN", ""));
    println(".*$A_624_End");

    println(".*$A_630_Begin");
    //pcd
    println(".*$A_630_End");

    println(".*$A_631_Begin");
    //pcd
    println(".*$A_631_End");

    println(".*$A_634_Begin");
    //pcd
    println(".*$A_634_End");

    println(".*$A_638_Begin");
    //pcd
    println(".*$A_638_End");

    println(".*$A_642_Begin");
    //pcd
    println(".*$A_642_End");

    println(".*$A_648_Begin");
    //pcd
    println(".*$A_648_End");

    println(".*$A_651_Begin");
    //pcd
    println(".*$A_651_End");

    println(".*$A_654_Begin");
    //pcd
    println(".*$A_654_End");

    println(".*$A_659_Begin");
    //pcd
    println(".*$A_659_End");

    println(".*$A_664_Begin");
    //pcd
    println(".*$A_664_End");

    println(".*$A_669_Begin");
    printValueInGroup(grpOrderOF, "FRUHANDLING", true);
    iColWidths = new int[]{15};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_669_End");

    println(".*$A_679_Begin");
    //pcd
    println(".*$A_679_End");

    println(".*$A_682_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5510", " ", true, true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_682_End");
*/
  }


  /**
   *  Description of the Method
   */
  private void processLongTo700() {

    println(".*$A_702_Begin");
    //pcd
    println(".*$A_702_End");

    println(".*$A_715_Begin");
    //pcd
    println(".*$A_715_End");

    println(".*$A_773_Begin");
    //pcd
    println(".*$A_773_End");

    println(".*$A_780_Begin");
    //pcd
    println(".*$A_780_End");

    println(".*$A_782_Begin");
    println(".*$A_782_End");

    println(".*$A_793_Begin");
    println(".*$A_793_End");

    println(".*$A_794_Begin");
    println(".*$A_794_End");
  }


  /**
   *  Description of the Method
   */
  private void processLongTo800() {

    println(".*$A_800_Begin");
    println(".*$A_800_End");

    println(".*$A_802_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "MKTGSTRATEGY", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_802_End");

    println(".*$A_803_Begin");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%' ->
    //  COFPUBS (R) -> PUBLICATION (E) when PUBTYPE = '5022' (Promotion Material)
    strFilterAttr = new String[]{"COFCAT","COFSUBCAT","COFGRP"};
    strFilterValue = new String[]{"Hardware","System","Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, null, null, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPUBS");
    strFilterAttr = new String[]{"PUBTYPE"};
    strFilterValue = new String[]{"5022"};
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "PUBLICATION");
    strParamList1 = new String[]{"PUBTITLE", "ORDERPN"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{45, 15};
    strHeader = new String[]{"Title", "Order No."};
    if (vPrintDetails.size() >0)  {
      println("The following materials will be sent to each business Partner");
      println("");
      println("Additional copies will be available for purchase from the IBM");
      println("Fulfillment Center, P.O. Box 154, Dayton, OH 45401.");
      println("");
      println("");
      println(":xmp.");
      println(".kp off");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_803_End");

    println(".*$A_805_Begin");
    //println(getAttributeValue(eiAnnounce, "MAINTSERVMKT", ""));
    println(".*$A_805_End");

    println(".*$A_810_Begin");
    //ANNOUNCEMENT (E) -> ANNEDUCATION (R) -> EDUCATION (E) when EDUCATIONTYPE = '1141' (Business Partners)
    strFilterAttr = new String[]{"EDUCATIONTYPE"};
    strFilterValue = new String[]{"1141"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnEducation, strFilterAttr, strFilterValue, true, true, "EDUCATION");
    strParamList1 = new String[]{ "COURSECODE","COURSENAME"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, false);
    iColWidths = new int[]{15, 15};
    strHeader = new String[]{"Course Code","Code Name"};
    bConditionOK = vPrintDetails.size() > 0 ? true:false;
    if (bConditionOK) {                     //Print header if something is there to print
      println(":xmp.");
      println(".kp off");
      printReport(true, strHeader, iColWidths, vPrintDetails);
    }

    resetPrintvars();
    strParamList1 = new String[]{ "TRAININGREQTS", "ENROLLMENT"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);

    if (vPrintDetails.size() > 0 && !bConditionOK)  {       //Print header if we didnt print it before
      println(":xmp.");
      println(".kp off");
    }
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    if (vPrintDetails.size() > 0 || bConditionOK)  {        //Print footer if we printed details
      println(":exmp.");
    }
    resetPrintvars();
    println(".*$A_810_End");

    println(".*$A_815_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5516", " ", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_815_End");

    println(".*$A_819_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5520", " ", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_819_End");

    println(".*$A_824_Begin");
    println(".se PRODNUM = '"+getAttributeValue(eiAnnounce, "PRODNUM", "")+"'");
    println(".*$A_824_End");

    println(".*$A_825_Begin");
    println(".*$A_825_End");

    println(".*$A_826_Begin");
    println(".*$A_826_End");

    println(".*$A_830_Begin");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%'->
    //  COFBPEXHIBIT (R) -> BPEXHIBIT (E)
    //  COFPUBS (R) -> PUBLICATION (E) when PUBTYPE = '5022' (Promotion Material)
    strFilterAttr = new String[]{"COFCAT","COFSUBCAT","COFGRP"};
    strFilterValue = new String[]{"Hardware","System","Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, null, null, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFBPEXHIBIT");
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "BPEXHIBIT");
    strParamList1 = new String[]{"MIDRANGEPRODCATTYPE"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", false, true);

    iColWidths = new int[]{55};
    printReport(false, null, iColWidths, vPrintDetails);
    println(".*$A_830_End");

    println(".*$A_831_Begin");
    /*
     *  Use the same vector as above to print another attribute on BPEXHIBIT
     */
    strParamList1 = new String[]{"MIDRANGEPRODCAT"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{15};
    strCondition1="";
    if (vPrintDetails.size()>0) {
      strCondition1=(String) vPrintDetails.elementAt(0);
    }

    println(".se IC = '"+strCondition1+"'");
    resetPrintvars();
    println(".*$A_831_End");

    println(".*$A_832_Begin");

    println(".se IP = '"+getAttributeValue(eiAnnounce, "REMKTDISCPERCENT", "")+"'");

    println(".*$A_832_End");

    println(".*$A_839_Begin");
    //Not req by SG
    println(".*$A_839_End");

    println(".*$A_840_Begin");
/*
    printValueInGroup(grpPriceInfo, "PPRDISCOUNTPERCENT", true);
    iColWidths = new int[]{55};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
*/
    println(".*$A_840_End");

    println(".*$A_841_Begin");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%' ->
    //  COFPRICE (R) -> PRICEFININFO
    strFilterAttr = new String[]{"COFCAT","COFSUBCAT","COFGRP"};
    strFilterValue = new String[]{"Hardware","System","Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, null, null, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPRICE");
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
    strParamList1 = new String[]{"DISTSCHEDADISC"};
    printValueListInVector(vReturnEntities3, strParamList1, " ", true, false);
    iColWidths = new int[]{68};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_841_End");

    println(".*$A_842_Begin");
    //ANNOUNCEMENT(OFFERINGTYPE) = '2891' (IBM Application System/400 and iSeries) or '2892' (IBM RISC System/6000
    //  and pSeries) or '2895' (IBM Point of Sale Products) then answer RFA Question 842 with 'A, B, C, D)
    strFilterAttr = new String[]{"OFFERINGTYPES","OFFERINGTYPES","OFFERINGTYPES"};
    strFilterValue = new String[]{"2891","2892","2895"};
    if (foundInEntity(eiAnnounce,strFilterAttr,strFilterValue,false)) {
      println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "IRMRAGG", ""));
    }
    println(".*$A_842_End");

    println(".*$A_843_Begin");
    //If ANNOUNCEMENT(OFFERINGTYPE) = '2898' (IBM Storage Products) then answer RFA Question 843 with 'A, B, C, D, E)
    //  - USE THE LONG DESCRIPTION OF THE ALLOWED VALUE
    strFilterAttr = new String[]{"OFFERINGTYPES"};
    strFilterValue = new String[]{"2898"};
    if (foundInEntity(eiAnnounce,strFilterAttr,strFilterValue,false)) {
      println(getAttributeValue(eiAnnounce, "IRMRAGG", ""));
    }
    println(".*$A_843_End");

    println(".*$A_844_Begin");
    println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "MESORDERDISCOUNT", ""));
    println(".*$A_844_End");

    println(".*$A_845_Begin");
    //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> OOFAVAIL (R) -> ORDEROF (E) with classification of
    //  'Hardware-FeatureConvert-%-%' -> OOFPRICE (R) -> PRICEFININFO (E) or
    strFilterAttr = new String[]{"OOFCAT"};
    strFilterValue = new String[]{"Hardware"};
    vReturnEntities1 = searchEntityGroupLink(grpOrderOFAvail, strFilterAttr, strFilterValue, true, false, "ORDEROF");
logMessage("A_845_Begin");
displayContents(vReturnEntities1) ;
    for (i = 0; i < vReturnEntities1.size(); i++) {
      strFilterAttr =  new String[]{"OOFSUBCAT","OOFSUBCAT","OOFSUBCAT"};
      strFilterValue = new String[] {"FeatureConvert","ModelUpgrade","ModelConvert"};
      eiOrderOF = (EntityItem) vReturnEntities1.elementAt(i);
      if (foundInEntity(eiOrderOF,strFilterAttr,strFilterValue,false))  {
logMessage("A_845"+eiOrderOF.getEntityType()+":"+eiOrderOF.getEntityID());
        vReturnEntities2 = searchEntityItemLink(eiOrderOF, null, null, true, true, "OOFPRICE");
logMessage("A_845:OOFPRICE");
displayContents(vReturnEntities2);
        vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "PRICEFININFO");
logMessage("A_845:PRICEFININFO");
displayContents(vReturnEntities3);
        if (vReturnEntities3.size() > 0 ) {
          eiPriceInfo = (EntityItem) vReturnEntities3.elementAt(0);
          vPrintDetails.add(getAttributeFlagEnabledValue(eiPriceInfo.getEntityType(), eiPriceInfo.getEntityID(), "MESREMARKETDISCOUNT", " ") + "%");
        }
      }
    }
    iColWidths = new int[]{68};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();

    println(".*$A_845_End");

    println(".*$A_848_Begin");
    //ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of  'Hardware-System-Base-%' ->
    //  COFPRICE (R) -> PRICEFININFO (E)
    strFilterAttr = new String[]{"COFCAT", "COFSUBCAT", "COFGRP"};
    strFilterValue = new String[]{"Hardware", "System", "Base"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnCofa, strFilterAttr, strFilterValue, true, true, "COMMERCIALOF");
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPRICE");
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities2.elementAt(i);
      eiPriceInfo = (EntityItem) eiNextItem.getDownLink(0);
      eiCommOF = (EntityItem) eiNextItem.getUpLink(0);
      vPrintDetails.add(getAttributeValue(eiCommOF, "MACHTYPE", " "));
      vPrintDetails.add(getAttributeValue(eiCommOF, "MODEL", " "));
      vPrintDetails.add(getAttributeValue(eiPriceInfo, "MAXDISCOUNT", " "));
    }
    iColWidths = new int[]{20, 20, 20};
    strHeader = new String[]{"Machine Type", "Model", "Discount % Gap"};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(".*$A_848_End");

    println(".*$A_851_Begin");
    println(".*$A_851_End");

    println(".*$A_852_Begin");
    println(getAttributeShortFlagDesc(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(), "INVENTORYADJCAT", ""));
    println(".*$A_852_End");

    println(".*$A_856_Begin");
    println(".*$A_856_End");

    println(".*$A_858_Begin");
    //'ANNOUNCEMENT (E) -> ANNCOFA (A) -> COMMERCIALOF (E) with classification of 'Hardware-System-Base-%' ->
    //  COFPUBS (R) -> PUBLICATION (E) when PUBTYPE = '5022' (Promotion Materials)
    //Continue using the previous COMMOF vector
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "COFPUBS");
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities2.elementAt(i);
      eiPublication = (EntityItem) eiNextItem.getDownLink(0);
      strCondition1 = getAttributeFlagEnabledValue(eiPublication.getEntityType(), eiPublication.getEntityID(), "PUBTYPE", " ");
      if (flagvalueEquals(eiPublication.getEntityType(), eiPublication.getEntityID(), "PUBTYPE","5022")) {
        vPrintDetails.add(getAttributeValue(eiNextItem.getEntityType(), eiNextItem.getEntityID(), "PUBAVAIL", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBTITLE", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "ORDERPN", " "));
        vPrintDetails.add(getAttributeValue(eiPublication, "PUBPN", " "));
      }
    }
    strHeader = new String[]{"DATE", "TITLE", "ORDER NO.", "PART NO."};
    iColWidths = new int[]{12, 25, 15, 15};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(".*$A_858_End");

    println(".*$A_859_Begin");
    strParamList1 = new String[]{"STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "5503", " ", true);
    iColWidths = new int[]{69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_859_End");

  }


  /**
   *  Description of the Method
   */
  private void processLongTo900() {
    println(".*$A_905_Begin");
    println(".*$A_905_End");

    println(".*$A_916_Begin");
    //ANNOUNCEMENT (E) -> ANNSALESMANCHG (R) -> SALESMANCHG when SMCHANGEOPTION = '1142' (
    //  Option 1 - List changes to existing SM text) and CHANGETYPE = '352' (Change) or '353' (Add)
    strFilterAttr = new String[]{"SMCHANGEOPTION"};
    strFilterValue = new String[]{"1142"};

    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    strFilterAttr = new String[]{"CHANGETYPE","CHANGETYPE"};
    strFilterValue = new String[]{"352","353"};
    strParamList1 = new String[]{"AFFECTHEADING", "CHANGETYPE", "EXISTINGTEXT", "NEWTEXT", "SEQNUMBER"};
    for (i=0;i<vReturnEntities1.size();i++) {
      eiSalesmanchg = (EntityItem) vReturnEntities1.elementAt(i);
      if (foundInEntity(eiSalesmanchg,strFilterAttr,strFilterValue,false)) {
        printValueListInItem(eiSalesmanchg, strParamList1, " ", true);
      }
    }
    strHeader = new String[]{"Heading", "Changetype", "Cur. Text", "NewText", "Sequence#"};
    iColWidths = new int[]{15, 10, 10, 10, 10};
    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_916_End");

    println(".*$A_920_Begin");
    //'ANNOUNCEMENT (E) -> ANNSALESMANCHG (R) -> SALESMANCHG when SMCHANGEOPTION = '1143'
    //  (Option 2 - Revise SM text using the existing file from Boulder)
    strFilterAttr = new String[]{"SMCHANGEOPTION"};
    strFilterValue = new String[]{"1143"};

    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    strParamList1 = new String[]{"REVISEDSALEMAN"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, false);
    iColWidths = new int[]{50};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_920_End");

    println(".*$A_925_Begin");
    println(getAttributeValue(eiAnnounce, "SALESMANGENREQ", ""));
    println(".*$A_925_End");

    println(".*$A_934_Begin");
    //ANNOUNCEMENT (E) -> ANNSALESMANCHG (R) -> SALESMANCHG when SMCHANGEOPTION = '1144'
    //  (Option 3 - Generate new Sales Manual text) and AFFECTHEADING = '1123' (Announce a new model)
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1123"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_934_End");

    println(".*$A_937_Begin");
    //ANNOUNCEMENT (E) -> ANNSALESMANCHG (R) -> SALESMANCHG when SMCHANGEOPTION = '1144'
    //  (Option 3 - Generate new Sales Manual text) and AFFECTHEADING = '1124' (CAN, EMEA - Models)
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1124"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_937_End");

    println(".*$A_946_Begin");
    //ANNOUNCEMENT (E) -> ANNSALESMANCHG (R) -> SALESMANCHG when SMCHANGEOPTION = '1144'
    //  (Option 3 - Generate new Sales Manual text) and AFFECTHEADING = '1125' (Maximum Configuration)
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1125"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_946_End");

    println(".*$A_958_Begin");
    //ANNOUNCEMENT (E) -> ANNSALESMANCHG (R) -> SALESMANCHG when SMCHANGEOPTION = '1144'
    //  (Option 3 - Generate new Sales Manual text) and AFFECTHEADING = '1126' (Customer Set up)
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1126"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_958_End");

    println(".*$A_962_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Model Conversions'
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1127"};
    strParamList1 = new String[]{ "NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    iColWidths = new int[]{69};
    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
      }
    println(".*$A_962_End");

    println(".*$A_964_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Device Supported'
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1128"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_964_End");

    println(".*$A_965_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Technical Description'
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1121"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_965_End");

    println(".*$A_967_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Specify Feature Number and Name'
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1130"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_967_End");

    println(".*$A_969_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Specify Code Statements'
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1131"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_969_End");

    println(".*$A_978_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Special Feature number and name' (flag value 1132)
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1132"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_978_End");

    println(".*$A_984_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Accessory Name(s)'
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1133"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_984_End");

    println(".*$A_985_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Customer Replacement Parts'
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1134"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_985_End");

    println(".*$A_986_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Machine Elements'
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1135"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_986_End");

    println(".*$A_987_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Supplies'
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1136"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_987_End");

    println(".*$A_988_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 3' & AFFECTHEADING = 'Diskettes'
    strFilterAttr = new String[]{"SMCHANGEOPTION", "AFFECTHEADING"};
    strFilterValue = new String[]{"1144", "1137"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_988_End");

    println(".*$A_989_Begin");
    //'ANNOUNCEMENT->SALESMANCHG when SMCHANGEOPTION = 'Option 4'
    strFilterAttr = new String[]{"SMCHANGEOPTION"};
    strFilterValue = new String[]{"1145"};
    strParamList1 = new String[]{"NEWTEXT"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnSalesmanchg, strFilterAttr, strFilterValue, true,true,"SALESMANCHG");
    vReturnEntities2 = sortEntities(vReturnEntities1, new String[]{"SEQNUMBER"});
    printValueListInVector(vReturnEntities2, strParamList1, " ", true, true);
    if (vPrintDetails.size() > 0) {
      iColWidths = new int[]{69};
      println(":xmp.");
      println(".kp off");
      printReport(false, null, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_989_End");

    println(".*$A_990_Begin");
    println(".*$A_990_End");

    println(".*$A_991_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "GENPRODSYSDESC", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_991_End");

    println(".*$A_992_Begin");
    strCondition1 = transformXML(getAttributeValue(eiAnnounce, "OPERPWRCONTROLS", " "));
    prettyPrint(strCondition1,69);
    println(".*$A_992_End");

    println(".*$A_993_Begin");
    println(getAttributeValue(eiAnnounce, "DATASECCHAR", ""));
    println(".*$A_993_End");

    println(".*$A_994_Begin");
    println(".*$A_994_End");

    println(".*$A_995_Begin");
/*
    //'ANNOUNCEMENT->AVAIL->COMMERCIALOF (with COMMERCIALOF classification = '%-%-%-%')
    vReturnEntities1 = searchEntityGroupLink(grpCofAvail, null, null, true, false, "COMMERCIALOF");
    strParamList1 = new String[]{"USABILITYCHARACT"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{15};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
*/
    println(".*$A_995_End");

    println(".*$A_996_Begin");    //tbd How to do this?
    //'ANNOUNCEMENT->ANNDESCAREA use relator ANNTODESCAREA
    vReturnEntities1 = searchEntityGroupLink(grpAnnToDescArea, null, null, true, true, "ANNDESCAREA");
    strParamList1 = new String[]{"WORLDTRADECONDSID"};
    printValueListInVector(vReturnEntities1, strParamList1, " ", true, true);
    iColWidths = new int[]{15};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_996_End");

    println(".*$A_998_Begin");
    println(".*$A_998_End");
  }

  private String transformXML(String _strXml) {
    ByteArrayOutputStream gmlOS=null;// create output stream & transform
    _strXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?> <!DOCTYPE eAnnounceData SYSTEM \"file:/"+DTDFILEPATH+"\" ><eAnnounceData>"+_strXml+"</eAnnounceData>";
//    _strXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?> <!DOCTYPE eAnnounceData SYSTEM \"file:///eAnnounceText.dtd\" ><eAnnounceData>"+_strXml+"</eAnnounceData>";
    try {
      // load xml file into stream
      StringReader srInput = new StringReader(_strXml);
      StreamSource xmlSource = new StreamSource(srInput);//create xml stream source
      xmlSource.setSystemId(_strXml);// resolve relative urls
      //XMLtoGML x2g = new XMLtoGML();// create gml transformer
      gmlOS = (ByteArrayOutputStream) x2g.transform(xmlSource);// xml to gml
    } catch (Exception e) {
      println("Error: " + e + "\n");
      println("The following is the Offending xml");
      println(_strXml);
      logError("Exception!"+e.getMessage()+"\n"+":***:"+_strXml+":***:");

    }
    return (gmlOS!=null ? gmlOS.toString(): "")  ;
  }

  /**
   *  Gets the linkedEntityAttrValue attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom              Description of the Parameter
   *@param  _strEntityTypes      Description of the Parameter
   *@param  _strTargetAttribute  Description of the Parameter
   *@param  _bGoDown             Description of the Parameter
   *@return                      The linkedEntityAttrValue value
   * /
  private String getLinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute, boolean _bGoDown) {
    return getLinkedEntityAttrValue(_eiFrom, _strEntityTypes, _strTargetAttribute, _bGoDown, null, null);
  }*/


  /**
   *  Gets the linkedEntityAttrValue attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom              Description of the Parameter
   *@param  _strEntityTypes      Description of the Parameter
   *@param  _strTargetAttribute  Description of the Parameter
   *@param  _bGoDown             Description of the Parameter
   *@param  _strCheckAttribute   Description of the Parameter
   *@param  _strCheckValues      Description of the Parameter
   *@return                      The linkedEntityAttrValue value
   * /
  private String getLinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute, boolean _bGoDown, String[] _strCheckAttribute, String[] _strCheckValues) {
    String strReturn = null;
    EntityItem eiLinkItem = null;
    if (_strCheckAttribute == null) {
      eiLinkItem = getLinkedEntityItem(_eiFrom, _strEntityTypes, _bGoDown);
    } else {
      eiLinkItem = getLinkedEntityItem(_eiFrom, _strEntityTypes, _bGoDown, _strCheckAttribute, _strCheckValues);
    }
    strReturn = getAttributeValue(eiLinkItem, _strTargetAttribute, " ");

    return strReturn;
  }*/


  /**
   *  Gets the downlinkedEntityAttrValue attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom              Description of the Parameter
   *@param  _strEntityTypes      Description of the Parameter
   *@param  _strTargetAttribute  Description of the Parameter
   *@return                      The downlinkedEntityAttrValue value
   * /
  private String getDownlinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute) {
    return getLinkedEntityAttrValue(_eiFrom, _strEntityTypes, _strTargetAttribute, true);
  }
  private String getDownlinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute, String[] _strCheckAttribute, String[] _strCheckValues) {
    return getLinkedEntityAttrValue(_eiFrom, _strEntityTypes, _strTargetAttribute, true, _strCheckAttribute, _strCheckValues);
  }*/


  /**
   *  Gets the uplinkedEntityAttrValue attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom              Description of the Parameter
   *@param  _strEntityTypes      Description of the Parameter
   *@param  _strTargetAttribute  Description of the Parameter
   *@param  _strCheckAttribute   Description of the Parameter
   *@param  _strCheckValues      Description of the Parameter
   *@return                      The uplinkedEntityAttrValue value
   * /
  private String getUplinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute, String[] _strCheckAttribute, String[] _strCheckValues) {
    return getLinkedEntityAttrValue(_eiFrom, _strEntityTypes, _strTargetAttribute, false, _strCheckAttribute, _strCheckValues);
  }*/


  /**
   *  Gets the uplinkedEntityAttrValue attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom              Description of the Parameter
   *@param  _strEntityTypes      Description of the Parameter
   *@param  _strTargetAttribute  Description of the Parameter
   *@return                      The uplinkedEntityAttrValue value
   * /
  private String getUplinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute) {
    return getLinkedEntityAttrValue(_eiFrom, _strEntityTypes, _strTargetAttribute, false);
  }*/


  /**
   *  Gets the linkedEntityItem attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom          Description of the Parameter
   *@param  _strEntityTypes  Description of the Parameter
   *@param  _bGoDown         Description of the Parameter
   *@return                  The linkedEntityItem value
   */
  private EntityItem getLinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[], boolean _bGoDown) {
    return getLinkedEntityItem(_eiFrom, _strEntityTypes, _bGoDown, null, null);
  }


  /*
    * This will navigate from the given EntityItem downward or upward the list of entitytypes
    * in the array till it arrives at the final one, if a match is found, it will return the EntityItem
   */
  /**
   *  Gets the linkedEntityItem attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom             Description of the Parameter
   *@param  _strEntityTypes     Description of the Parameter
   *@param  _bGoDown            Description of the Parameter
   *@param  _strCheckAttribute  Description of the Parameter
   *@param  _strCheckValues     Description of the Parameter
   *@return                     The linkedEntityItem value
   */
  private EntityItem getLinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[], boolean _bGoDown, String[] _strCheckAttribute, String[] _strCheckValues) {
    logMessage("In getLinkedEntityItem :" + _eiFrom.getKey());

    EntityItem eiLinkItem = _eiFrom;
    String strTargetEntityType = _strEntityTypes[_strEntityTypes.length - 1];
    EntityItem eiReturn = null;
    Vector vTemp = new Vector();
    logMessage("In getLinkedEntityItem 1");
    for (int k = 0; k < _strEntityTypes.length; k++) {
      if (_strCheckAttribute == null) {
        vTemp = searchEntityItemLink(eiLinkItem, null, null, true, _bGoDown, _strEntityTypes[k]);
      } else {
        if (k == _strEntityTypes.length - 1) {//Match attribute values only at the target
          logMessage("getLinkedEntityItem: matching from " + eiLinkItem.getKey() + " to target " + _strEntityTypes[k] + " for values " + _strCheckAttribute.toString() + " == " + _strCheckValues.toString());
          vTemp = searchEntityItemLink(eiLinkItem, _strCheckAttribute, _strCheckValues, true, _bGoDown, _strEntityTypes[k]);
        } else {
          vTemp = searchEntityItemLink(eiLinkItem, null, null, true, _bGoDown, _strEntityTypes[k]);
        }
      }
      logMessage("getLinkedEntityItem: Navigating " + eiLinkItem.getKey() + " to " + _strEntityTypes[k]);
      if (vTemp.size() > 0) {
        eiLinkItem = (EntityItem) vTemp.elementAt(0);
      }
    }
    eiReturn = eiLinkItem;

    if (!eiReturn.getEntityType().equals(strTargetEntityType)) {
      logMessage("getLinkedEntityItem: could not find target ETYPE:" + strTargetEntityType + " start " + _eiFrom.getKey() + ":" + _strEntityTypes.toString());
    }
    return eiReturn;
  }



  /**
   *  Gets the downlinkedEntityItem attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom             Description of the Parameter
   *@param  _strEntityTypes     Description of the Parameter
   *@param  _strCheckAttribute  Description of the Parameter
   *@param  _strCheckValues     Description of the Parameter
   *@return                     The downlinkedEntityItem value
   * /
  private EntityItem getDownlinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[], String[] _strCheckAttribute, String[] _strCheckValues) {
    return getLinkedEntityItem(_eiFrom, _strEntityTypes, true, _strCheckAttribute, _strCheckValues);
  }*/


  /**
   *  Gets the downlinkedEntityItem attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom          Description of the Parameter
   *@param  _strEntityTypes  Description of the Parameter
   *@return                  The downlinkedEntityItem value
   * /
  private EntityItem getDownlinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[]) {
    return getLinkedEntityItem(_eiFrom, _strEntityTypes, true);
  }*/


  /**
   *  Gets the uplinkedEntityItem attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom          Description of the Parameter
   *@param  _strEntityTypes  Description of the Parameter
   *@return                  The uplinkedEntityItem value
   */
  private EntityItem getUplinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[]) {
    logMessage("In getUplinkedEntityItem :" + _eiFrom.getKey());
    return getLinkedEntityItem(_eiFrom, _strEntityTypes, false);
  }


  /**
   *  Gets the uplinkedEntityItem attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom             Description of the Parameter
   *@param  _strEntityTypes     Description of the Parameter
   *@param  _strCheckAttribute  Description of the Parameter
   *@param  _strCheckValues     Description of the Parameter
   *@return                     The uplinkedEntityItem value
   * /
  private EntityItem getUplinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[], String[] _strCheckAttribute, String[] _strCheckValues) {
    logMessage("In getUplinkedEntityItem :" + _eiFrom.getKey());
    return getLinkedEntityItem(_eiFrom, _strEntityTypes, false, _strCheckAttribute, _strCheckValues);
  }*/




  /**
   * @return
   * @author Administrator
   */
  public static String getVersion() {
    return ("$Id: RFAHWABR01.java,v 1.75 2008/03/19 19:38:57 wendy Exp $");
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
   * @author Administrator
   */
  public String getABRVersion() {
    return getVersion();
  }

}





/*
Amy van Zon ODM, IDLdata workgroup
zzzzzzzzzzzzzz  ok
Amy van Zon main page will have an action, send to global announcement
zzzzzzzzzzzzzz  ok...cool thanks
Amy van Zon select that, then pick my announcement 38346
zzzzzzzzzzzzzz  ok...will do that
Amy van Zon then click on action Send to production Management
Amy van Zon and that is it!!!
*/


