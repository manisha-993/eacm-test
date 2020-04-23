//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//$Log: RFA_IGSSVS.java,v $
//Revision 1.157  2008/03/19 19:30:44  wendy
//Clean up RSA warnings
//
//Revision 1.156  2007/06/06 19:25:20  bala
//1 more
//
//Revision 1.155  2007/06/06 18:24:22  bala
//tweaks to header in 153 and 155
//
//Revision 1.154  2007/05/31 17:52:03  bala
//change col width for autobahn number
//
//Revision 1.153  2007/05/15 17:28:10  bala
//fix headers 153,155
//
//Revision 1.152  2007/05/07 20:58:55  bala
//Updates for RFA release 22.04
//
//Revision 1.151  2007/04/16 20:37:40  bala
//change Russia to Russian Federation for Q219
//
//Revision 1.150  2007/04/11 17:26:26  bala
//219 - ignore AVAIL geo since we are checking for individual countries
//
//Revision 1.149  2007/03/14 01:03:47  bala
//format fix for 219
//
//Revision 1.148  2007/02/27 17:59:55  bala
//fix for 219 and 182,183
//
//Revision 1.147  2007/02/22 20:52:53  bala
//fix for q219 and printsuppMktfininfo
//
//Revision 1.146  2007/02/16 00:33:27  bala
//fixes to 153 and 219
//
//Revision 1.145  2007/01/23 22:45:05  bala
//complete changes for Q219
//
//Revision 1.144  2007/01/23 21:31:02  bala
//updates for 21.10
//
//Revision 1.143  2006/10/12 20:39:30  bala
//more Q130 changes 27839116,25715789
//
//Revision 1.142  2006/09/26 21:23:55  bala
//MN26599023: bullets for Q182,183 etc
//
//Revision 1.141  2006/08/31 18:42:27  wendy
//MN29166812  A153 data missing because of SOFAVAIL checks
//
//Revision 1.140  2006/08/28 22:06:02  wendy
//MN29121594 fixes
//
//Revision 1.139  2006/08/11 18:32:42  wendy
//continuation of MN# 28837752 Q153 had no output
//
//Revision 1.138  2006/08/07 20:50:56  bala
//more bug fixes for printInternal
//
//Revision 1.137  2006/08/02 21:44:38  bala
//more fixes for 153
//
//Revision 1.136  2006/08/02 18:00:39  bala
//fix printInternalLetter
//
//Revision 1.135  2006/07/31 23:03:32  bala
//fix ArrayIndexOutOfBoundsException for Q153
//
//Revision 1.134  2006/06/15 15:42:07  bala
//dont print autobahn proj number if sof not linked to avail
//
//Revision 1.133  2006/06/09 19:40:37  wendy
//updates for TIR 6QKKNN
//
//Revision 1.132  2006/05/29 23:27:03  bala
//more updates
//
//Revision 1.131  2006/03/21 21:42:21  bala
//updates for RFA guide 20.10.
//
//Revision 1.130  2006/03/02 21:22:15  bala
//remover reference to constants.java (stylesheets)
//
//Revision 1.129  2005/08/23 21:41:02  yang
//more syntax
//
//Revision 1.128  2005/08/23 21:29:03  yang
//syntax
//
//Revision 1.127  2005/08/23 21:12:47  yang
//more logs
//
//Revision 1.126  2005/08/23 20:58:27  yang
//adding method searchEntityVectorLink1
//
//Revision 1.125  2005/08/23 20:45:27  yang
//more fixes
//
//Revision 1.124  2005/08/23 20:35:43  yang
//adding more log messages
//
//Revision 1.123  2005/08/23 20:21:16  yang
//more syntax
//
//Revision 1.122  2005/08/23 19:56:37  yang
//syntax
//
//Revision 1.121  2005/08/23 19:46:27  yang
//more fixes
//
//Revision 1.120  2005/08/23 18:37:33  yang
//more logs
//
//Revision 1.119  2005/08/23 17:15:53  yang
//more changes to question 15A
//
//Revision 1.118  2005/08/23 16:59:18  yang
//*** empty log message ***
 //
 //Revision 1.117  2005/08/23 16:57:59  yang
 //fixes
 //
 //Revision 1.116  2005/08/23 00:45:36  yang
 //more logs
 //
 //Revision 1.115  2005/08/22 23:09:03  yang
 //additional logs
 //
 //Revision 1.114  2005/08/22 22:30:07  yang
 //adding changes to CR 0822055510 (IGS Business Partner)
 //
 //Revision 1.113  2005/07/13 19:10:14  bala
 // q220 Ucase KOREA
 //
 //Revision 1.112  2005/07/11 17:32:01  bala
 //add korea to 220
 //
 //Revision 1.111  2005/07/08 20:45:04  bala
 //more changes to q220
 //
 //Revision 1.110  2005/07/01 17:03:05  bala
 //fix for q200..change TAIWAN to..
 //
 //Revision 1.109  2005/06/23 20:25:57  bala
 //fix for 15A
 //
 //Revision 1.108  2005/06/23 18:20:32  bala
 //fix nullpointer in 800
 //
 //Revision 1.107  2005/06/21 17:34:34  bala
 //q800 fix
 //
 //Revision 1.106  2005/06/20 19:32:50  bala
 //Fixes for Q155, 800,158
 //
 //Revision 1.105  2005/05/18 19:20:26  bala
 //remove extra tag from crossell/upsell Q119
 //
 //Revision 1.104  2005/05/12 20:15:37  bala
 //fix for 15A
 //
 //Revision 1.103  2005/05/12 01:36:21  bala
 //_15A fix and sorting of marketingmessages (RFASort)
 //
 //Revision 1.102  2005/05/09 18:59:24  bala
 //fix heading for Q154
 //
 //Revision 1.101  2005/05/05 16:24:49  bala
 //change the column width of report for q154 to be within 69 chars
 //
 //Revision 1.100  2005/05/05 16:14:48  bala
 //change FEATUREID to FEATURENUMBER in q155
 //
 //Revision 1.99  2005/05/04 20:56:04  bala
 //fix for 105
 //
 //Revision 1.98  2005/05/02 21:06:32  bala
 //add xmp exmp tags for q220
 //
 //Revision 1.97  2005/04/28 17:53:18  bala
 //change the RFA version
 //
 //Revision 1.96  2005/04/26 21:16:01  bala
 //suppress "Benefit" when no benefit found MN:23709175
 //
 //Revision 1.95  2005/04/22 16:05:25  bala
 //typo
 //
 //Revision 1.94  2005/04/22 15:54:05  bala
 //195,104,102
 //
 //Revision 1.93  2005/04/15 15:45:29  bala
 //Services RFA  Guide Update - 19.10 and 20.04
 //
 //Revision 1.92  2005/03/09 19:12:28  bala
 //fix for 208..not sorting by date since it was changed to 'RFA Format' before sort
 //
 //Revision 1.91  2005/03/07 18:07:18  bala
 //MN 23064407 for q_130
 //
 //Revision 1.90  2005/02/24 22:26:34  bala
 //fix for q126 and q800
 //
 //Revision 1.89  2005/02/08 18:29:12  joan
 //changes for Jtest
 //
 //Revision 1.88  2005/02/03 22:40:07  bala
 //'final' fixes to q130
 //
 //Revision 1.87  2005/01/25 20:38:42  bala
 //more fixes GEO tags for q130
 //
 //Revision 1.86  2005/01/20 22:42:17  bala
 //Q130 fix
 //
 //Revision 1.85  2004/12/09 23:41:46  bala
 //some more tag changes for q_126
 //
 //Revision 1.84  2004/11/23 22:42:25  bala
 //fix nullpointer for Q_116
 //
 //Revision 1.83  2004/11/19 19:00:31  bala
 //q 126, child mkt name has to print with the gml tags when there are multiple
 //multiple child data elements are present
 //
 //Revision 1.82  2004/11/09 16:48:09  bala
 //CR0916041642- change email to E-mail
 //
 //Revision 1.81  2004/11/05 22:54:47  bala
 //fix for 800 and 126
 //
 //Revision 1.80  2004/10/20 15:53:57  bala
 //fix colwidth for q800
 //
 //Revision 1.79  2004/10/06 20:24:03  bala
 //fix for printFeatureBenefit - not printing Mktname for 1 instance
 //
 //Revision 1.78  2004/09/22 21:31:31  bala
 //change format for q_148
 //
 //Revision 1.77  2004/08/16 20:37:00  bala
 //add .exmp for 130
 //
 //Revision 1.76  2004/07/22 23:51:55  bala
 //change to 130 again!
 //
 //Revision 1.75  2004/07/16 20:46:51  bala
 //more adjustements to 130
 //
 //Revision 1.74  2004/07/15 23:12:40  bala
 //add .xmp to 130
 //
 //Revision 1.73  2004/07/15 01:37:10  bala
 //spec change for q_130
 //
 //Revision 1.72  2004/07/01 21:12:32  bala
 //enable printing of offering name when only 1 instance of offering is found
 //
 //Revision 1.71  2004/06/23 17:01:51  bala
 //change date location for 208 and fix geo tag break
 //
 //Revision 1.70  2004/06/17 22:15:57  bala
 //116
 //
 //Revision 1.69  2004/06/15 20:21:40  bala
 //fixes for 208,155,15 series
 //
 //Revision 1.68  2004/06/09 21:41:03  bala
 //fixes for 208,155
 //
 //Revision 1.67  2004/06/08 17:08:02  bala
 //do not print question tags for _110 if no data exists, also changes for Q14A,b, 15 series and 16series
 //
 //Revision 1.66  2004/06/07 17:56:32  bala
 //add EMEA check for channel in q110
 //
 //Revision 1.65  2004/06/03 18:50:48  bala
 //change to Q110
 //
 //Revision 1.64  2004/05/17 21:56:20  bala
 //cosmetic changes
 //
 //Revision 1.63  2004/05/13 21:18:41  bala
 //swap columns for 153
 //
 //Revision 1.62  2004/05/13 20:18:30  bala
 //fix tag imbalance in Q130
 //
 //Revision 1.61  2004/05/13 17:24:02  bala
 //getRFADateFormat - print month in its entirety
 //
 //Revision 1.60  2004/05/11 20:56:11  bala
 //formatting fixes for 148 and removing .p in others
 //
 //Revision 1.59  2004/05/07 21:02:56  bala
 //fix 148, version tag, 200, 208
 //
 //Revision 1.58  2004/05/06 21:52:07  bala
 //fix rfadateformat for 208 for feature and component
 //
 //Revision 1.57  2004/04/29 22:47:47  bala
 //fix for 155
 //
 //Revision 1.56  2004/04/23 23:28:26  bala
 //fix for q110, 170
 //
 //Revision 1.55  2004/04/14 18:36:06  bala
 //change for q170
 //
 //Revision 1.54  2004/04/08 20:24:55  bala
 //deleted xmp and kpoff tags for question 40 because of script error
 //
 //Revision 1.53  2004/04/02 22:45:40  bala
 //fixed bug in getFeatureToSofMktMsg
 //
 //Revision 1.52  2004/04/01 23:30:29  bala
 //fixed nullpointer bug
 //
 //Revision 1.51  2004/03/31 23:33:23  bala
 //changes for 1.3, added getRFADateFormat method
 //
 //Revision 1.50  2004/03/02 22:31:42  bala
 //Alans change to get geo tags from multiple AVAIL's  linked to an offering
 //
 //Revision 1.49  2004/01/23 23:34:32  bala
 //one more fix to 800
 //
 //Revision 1.48  2004/01/23 01:08:06  bala
 //more changes to 800
 //
 //Revision 1.47  2004/01/21 00:08:57  bala
 //fix 110,126
 //
 //Revision 1.46  2004/01/15 19:54:00  bala
 //fix possible null pointers
 //
 //Revision 1.45  2004/01/15 18:47:44  bala
 //Q130 and Q800 changes
 //
 //Revision 1.44  2004/01/14 00:01:36  bala
 //Q130 change formatting, print all ops for offering
 //
 //Revision 1.43  2004/01/12 19:26:40  bala
 //dont suppress duplicates for Q130
 //
 //Revision 1.42  2004/01/12 18:35:39  bala
 //fix for Q 130 - change formatting
 //
 //Revision 1.41  2004/01/09 00:57:13  bala
 //fix for crossell/upsell navigation to its child offerings
 //
 //Revision 1.40  2004/01/06 03:09:18  bala
 //eod commit
 //
 //Revision 1.39  2003/12/31 22:34:19  bala
 //After Alans last minute changes
 //
 //Revision 1.38  2003/12/30 22:25:04  bala
 //eod commit
 //
 //Revision 1.37  2003/12/23 18:52:20  bala
 //fix for 155
 //
 //Revision 1.36  2003/12/18 00:40:01  bala
 //fixes
 //
 //Revision 1.35  2003/12/08 23:13:03  bala
 //change printFeatureBenefit to get crossell/upsell
 //
 //Revision 1.34  2003/12/02 18:49:06  bala
 //more tweaking for 110
 //
 //Revision 1.33  2003/12/01 23:45:59  bala
 //Mike Slocums tag fix for 110
 //
 //Revision 1.32  2003/12/01 20:16:03  bala
 //some more fixes
 //
 //Revision 1.31  2003/11/26 21:46:17  bala
 //more formatting changes
 //
 //Revision 1.30  2003/11/26 01:00:50  bala
 //fix for 110,148,155,208
 //
 //Revision 1.29  2003/11/24 19:38:08  bala
 //fix 208
 //
 //Revision 1.28  2003/11/19 01:23:14  bala
 //208,800
 //
 //Revision 1.27  2003/11/15 00:56:19  bala
 //170,153 and 154
 //
 //Revision 1.26  2003/11/13 23:19:26  bala
 //11/12 EOD commit
 //
 //Revision 1.25  2003/11/12 18:43:20  bala
 //Fix the tag problems as per Alans to get a clean extract
 //
 //Revision 1.24  2003/11/07 00:14:03  bala
 //Correct 1E
 //
 //Revision 1.23  2003/11/06 21:38:51  bala
 //plug in report type
 //
 //Revision 1.22  2003/11/06 03:57:45  bala
 //EOD commit
 //
 //Revision 1.20  2003/11/03 23:58:11  bala
 //EOD commit
 //
 //Revision 1.19  2003/11/01 01:45:23  bala
 //EOD drop
 //
 //Revision 1.18  2003/10/31 02:32:44  bala
 //EOD drop
 //
 //Revision 1.17  2003/10/29 02:27:23  bala
 //EOD commit
 //
 //Revision 1.16  2003/10/27 00:01:38  bala
 //EOD commit
 //
 //Revision 1.15  2003/10/25 23:32:02  bala
 //eod commit
 //
 //Revision 1.14  2003/10/24 00:46:22  bala
 //EOD commit
 //
 //Revision 1.13  2003/10/23 02:09:44  bala
 //eod commit
 //
 //Revision 1.12  2003/10/21 01:17:20  bala
 //Fix 130,4B,44
 //
 //Revision 1.11  2003/10/20 18:23:18  bala
 //fix 130,4B
 //
 //Revision 1.10  2003/10/17 23:34:31  bala
 //fix 045
 //
 //Revision 1.9  2003/10/16 23:54:07  bala
 //simply getLinkedentityItem
 //
 //Revision 1.8  2003/10/16 22:16:29  bala
 //fix compiler error
 //
 //Revision 1.7  2003/10/16 22:07:36  bala
 //sort mktmsg, Q49, add DISTRTYPE checking
 //
 //Revision 1.6  2003/10/15 19:56:54  bala
 //fix linked entityitem
 //
 //Revision 1.5  2003/10/15 16:52:42  bala
 //fix avail navigation and null pointer
 //
 //Revision 1.4  2003/10/13 18:04:39  bala
 //fix one more
 //
 //Revision 1.3  2003/10/10 21:15:11  bala
 //fix null pointer.
 //
 //Revision 1.2  2003/09/25 23:50:48  bala
 //add return statement
 //
 //Revision 1.1  2003/09/25 23:36:03  bala
 //RFA for Services
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

//Middleware Stuff
import COM.ibm.opicmpdh.middleware.*;
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
 *@created    September 16, 2003
 */
public class RFA_IGSSVS
    extends PokBaseABR {
  /**
   *  Execute ABR.
   */

  private final String DTDFILEPATH = ABRServerProperties.getDTDFilePath("RFA_IGSSVS");
  private final String BREAK_INDICATOR = "$$BREAKHERE$$";
  private final String strWorldwideTag = "US, AP, CAN, EMEA, LA";

  EntityGroup grpAnnouncement = null;
  EntityItem eiAnnounce = null;
  EntityGroup grpAnnDeliv = null;
  EntityItem eiAnnDeliv = null;
  EntityGroup grpAnnPara = null;
  EntityItem eiAnnPara = null;
  EntityGroup grpAnnDepend = null;
  EntityItem eiAnnDepend = null;
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
  EntityItem eiCommOffInfo = null;
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
  EntityGroup egSof = null;
  EntityItem eiSof = null;
  EntityGroup grpPdsQuestions = null;
  EntityItem eiPdsQuestions = null;
  EntityGroup grpCommOfIvo = null;
  EntityItem eiCommOfIvo = null;
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
  EntityGroup grpAnnCofOffMgmtGrpa = null;
  EntityItem eiAnnCofOffMgmtGrpa = null;
  EntityGroup grpAnnAvail = null;
  EntityItem eiAnnAvail = null;
  EntityGroup grpOpsys = null;
  EntityItem eiOpsys = null;
  EntityGroup grpAnnOp = null;
  EntityItem eiAnnOp = null;
  EntityGroup egComponent = null;
  EntityItem eiComponent = null;
  EntityGroup egFeature = null;
  EntityItem eiFeature = null;
  EntityGroup egFinof = null;
  EntityItem eiFinof = null;

  ReportFormatter rfaReport = null;
  XMLtoGML x2g = new XMLtoGML(); // create gml transformer

  String strSplit = null;
  int intSplitLen = 0;
  int intSplitAt = 0;
  int iTemp = 0;
  int i = 0;
  int j = 0;
  int k = 0;
  int iColWidths[] = null;
  String strCondition1 = null;
  String strCondition2 = null;
  String strCondition3 = null;
  String strCondition4 = null;
  String strCondition5 = null;
  String strCondition6 = null;
  boolean bConditionOK = false;
  boolean bConditionOK1 = false;
  boolean bIsAnnITS = false;
  EntityItem eiNextItem = null;
  EntityItem eiNextItem1 = null;
  EntityItem eiNextItem2 = null;
  EntityItem eiNextItem3 = null;
  EntityGroup grpNextGroup = null;
  String[] strParamList1 = null;
  String[] strParamList2 = null;
  String[] strFilterAttr = null;
  String[] strFilterValue = null;
  String[] strFilterAttr1 = null;
  String[] strFilterValue1 = null;
  String[] strEntityTypes = null;
  Object[] strAnswers = null;
  String[] strFeatureToSof = null;
  String[] strCmptToSof = null;
  String[] strSofToCmpt = new String[] {
      "SOFRELCMPNT", "CMPNT"};
  String[] strHeader = null;
  String[] strFeatureToCmpt = new String[] {
      "CMPNTFEATURE", "CMPNT"};
  String m_strSpaces = "                                                                                          ";
  Vector vReturnEntities1 = new Vector();
  Vector vReturnEntities2 = new Vector();
  Vector vReturnEntities3 = new Vector();
  Vector vReturnEntities4 = new Vector();
  Vector vReturnEntities5 = new Vector();
  Vector vAvailEntities = new Vector();
  Vector vSofFrmSofAvail = new Vector();
  Vector vCmpntFrmCmpntAvail = new Vector();
  Vector vFeatureFrmFeatureAvail = new Vector();
  Vector vSofSortedbyMkt = new Vector();
  Vector vFeatureSortedbyMkt = new Vector();
  Vector vCmptSortedbyMkt = new Vector();
  Vector vAllSortedOfferings = new Vector();

  Vector vPrintDetails = new Vector();
  Vector vPrintDetails1 = new Vector();
  Vector vPrintDetails2 = new Vector();
  Vector vPrintDetails3 = new Vector();
  Hashtable hNoDupeLines = new Hashtable();
  Enumeration hKeys = null;

  StringTokenizer st = null;
  GeneralAreaList m_geList = null;

  SortUtil mySort = new SortUtil();

  /**
   *  Description of the Method
   */
  public void execute_run() {
    try {
      start_ABRBuild();
      setReturnCode(PASS); //Set this to pass, will change it when it fails

      logMessage("VE Dump********************");
      logMessage(m_elist.dump(false));
      logMessage("End VE Dump********************");
      //Build a new General Area tree
      m_geList = new GeneralAreaList(getDatabase(), getProfile());
      m_geList.buildTree();

      logMessage("Starting General area dump*********************************");
      logMessage(m_geList.dump(false));
      logMessage("Ending dump***********************************");

      grpAnnouncement = m_elist.getParentEntityGroup(); //This will be for announcement
      logMessage("************Root Entity Type and id " + getEntityType() + ":" + getEntityID());
      vReturnEntities1 = null;
      vReturnEntities2 = null;

      if (grpAnnouncement == null) {
        logMessage("****************Announcement Not found ");
        setReturnCode(FAIL);

      }
      else {
        logMessage(grpAnnouncement.getEntityItemCount() + " Announcements found!");
        eiAnnounce = grpAnnouncement.getEntityItem(0); //Get the first Announcement
        /*
         *  Now go get the rest of the stuff
         */
        grpAnnDeliv = m_elist.getEntityGroup("ANNDELIVERABLE");
        eiAnnDeliv = null;
        if (grpAnnDeliv != null) {
          eiAnnDeliv = grpAnnDeliv.getEntityItem(0);
        }
        else {
          logMessage("**************ANNDELIVERABLE not found in list**");
        }
        grpParamCode = m_elist.getEntityGroup("PARAMETERCODE");
        eiParamCode = null;
        if (grpParamCode != null) {
          eiParamCode = grpParamCode.getEntityItem(0);
        }
        else {
          logMessage("**************PARAMETERCODE not found in list**");
        }

        grpDependCode = m_elist.getEntityGroup("DEPENDENCYCODE");
        eiDependCode = null;
        if (grpDependCode != null) {
          eiDependCode = grpDependCode.getEntityItem(0);
        }
        else {
          logMessage("**************DEPENDENCYCODE not found in list**");
        }

        grpAnnProj = m_elist.getEntityGroup("ANNPROJ");
        eiAnnProj = null;
        if (grpAnnProj != null) {
          eiAnnProj = grpAnnProj.getEntityItem(0);
        }
        else {
          logMessage("**************ANNPROJ not found in list**");
        }

        grpErrataCause = m_elist.getEntityGroup("ERRATACAUSE");
        eiErrataCause = null;
        if (grpErrataCause != null) {
          eiErrataCause = grpErrataCause.getEntityItem(0);
        }
        else {
          logMessage("**************ERRATACAUSE not found in list**");
        }

        grpOrganUnit = m_elist.getEntityGroup("ORGANUNIT");
        eiOrganUnit = null;
        if (grpOrganUnit != null) {
          eiOrganUnit = grpOrganUnit.getEntityItem(0);
        }
        else {
          logMessage("**************ORGANUNIT not found in list**");
        }

        grpOP = m_elist.getEntityGroup("OP");
        eiOP = null;
        if (grpOP != null) {
          eiOP = grpOP.getEntityItem(0);
        }
        else {
          logMessage("**************OP not found in list**");
        }

        grpChannel = m_elist.getEntityGroup("CHANNEL");
        eiChannel = null;
        if (grpChannel != null) {
          eiChannel = grpChannel.getEntityItem(0);
        }
        else {
          logMessage("**************CHANNEL not found in list**");
        }

        grpPDSQuestions = m_elist.getEntityGroup("PDSQUESTIONS");
        eiPDSQuestions = null;
        if (grpPDSQuestions != null) {
          eiPDSQuestions = grpPDSQuestions.getEntityItem(0);
        }
        else {
          logMessage("**************PDSQUESTIONS not found in list**");
        }

        grpPriceInfo = m_elist.getEntityGroup("PRICEFININFO");
        eiPriceInfo = null;
        if (grpPriceInfo != null) {
          eiPriceInfo = grpPriceInfo.getEntityItem(0);
        }
        else {
          logMessage("**************PRICEFININFO not found in list**");
        }

        grpCommOffInfo = m_elist.getEntityGroup("COMMERCIALOFINFO");
        eiCommOffInfo = null;
        if (grpCommOffInfo != null) {
          eiCommOffInfo = grpCommOffInfo.getEntityItem(0);
        }
        else {
          logMessage("**************COMMERCIALOFINFO not found in list**");
        }

        grpDerive = m_elist.getEntityGroup("DERIVE");
        eiDerive = null;
        if (grpDerive != null) {
          eiDerive = grpDerive.getEntityItem(0);
        }
        else {
          logMessage("**************DERIVE not found in list**");
        }

        grpAnnReview = m_elist.getEntityGroup("ANNREVIEW");
        eiAnnReview = null;
        if (grpAnnReview != null) {
          eiAnnReview = grpAnnReview.getEntityItem(0);
        }
        else {
          logMessage("**************ANNREVIEW not found in list**");
        }

        grpConfigurator = m_elist.getEntityGroup("CONFIGURATOR");
        eiConfigurator = null;
        if (grpConfigurator != null) {
          eiConfigurator = grpConfigurator.getEntityItem(0);
        }
        else {
          logMessage("**************CONFIGURATOR not found in list**");
        }

        grpAnnToConfig = m_elist.getEntityGroup("ANNTOCONFIG");
        eiAnnToConfig = null;
        if (grpAnnToConfig != null) {
          eiAnnToConfig = grpAnnToConfig.getEntityItem(0);
        }
        else {
          logMessage("**************ANNTOCONFIG not found in list**");
        }

        grpAnnToOrgUnit = m_elist.getEntityGroup("ANNORGANUNIT");
        eiAnnToOrgUnit = null;
        if (grpAnnToConfig != null) {
          eiAnnToOrgUnit = grpAnnToOrgUnit.getEntityItem(0);
        }
        else {
          logMessage("**************ANNORGANUNIT not found in list**");
        }

        grpAnnToOP = m_elist.getEntityGroup("ANNOP");
        eiAnnToOP = null;
        if (grpAnnToOP != null) {
          eiAnnToOP = grpAnnToOP.getEntityItem(0);
        }
        else {
          logMessage("**************ANNOP not found in list**");
        }

        grpCofAvail = m_elist.getEntityGroup("COMMERCIALOFAVAIL");
        eiCofAvail = null;
        if (grpCofAvail != null) {
          eiCofAvail = grpCofAvail.getEntityItem(0);
        }
        else {
          logMessage("**************COMMERCIALOFAVAIL not found in list**");
        }

        grpPdsQuestions = m_elist.getEntityGroup("PDSQUESTIONS");
        eiPdsQuestions = null;
        if (grpPdsQuestions != null) {
          eiPdsQuestions = grpPdsQuestions.getEntityItem(0);
        }
        else {
          logMessage("**************PDSQUESTIONS not found in list**");
        }

        grpCommOfIvo = m_elist.getEntityGroup("COMMERCIALOFIVO");
        eiCommOfIvo = null;
        if (grpCommOfIvo != null) {
          eiCommOfIvo = grpCommOfIvo.getEntityItem(0);
        }
        else {
          logMessage("**************COMMERCIALOFIVO not found in list**");
        }

        grpCommOF = m_elist.getEntityGroup("COMMERCIALOF");
        eiCommOF = null;
        if (grpCommOF != null) {
          eiCommOF = grpCommOF.getEntityItem(0);
        }
        else {
          logMessage("**************COMMERCIALOF not found in list**");
        }

        grpRelatedANN = m_elist.getEntityGroup("RELATEDANN");
        eiRelatedANN = null;
        if (grpRelatedANN != null) {
          eiRelatedANN = grpRelatedANN.getEntityItem(0);
        }
        else {
          logMessage("**************RELATEDANN not found in list**");
        }

        grpGeneralArea = m_elist.getEntityGroup("GENERALAREA");
        eiGeneralArea = null;
        if (grpGeneralArea != null) {
          eiGeneralArea = grpGeneralArea.getEntityItem(0);
        }
        else {
          logMessage("**************GENERALAREA not found in list**");
        }

        grpAvail = m_elist.getEntityGroup("AVAIL");
        eiAvail = null;
        if (grpAvail != null) {
          eiAvail = grpAvail.getEntityItem(0);
        }
        else {
          logMessage("**************AVAIL not found in list**");
        }

        grpOrderOF = m_elist.getEntityGroup("ORDEROF");
        eiOrderOF = null;
        if (grpOrderOF != null) {
          eiOrderOF = grpOrderOF.getEntityItem(0);
        }
        else {
          logMessage("**************ORDEROF not found in list**");
        }

        grpCrossSell = m_elist.getEntityGroup("CROSSSELL");
        eiCrossSell = null;
        if (grpCrossSell != null) {
          eiCrossSell = grpCrossSell.getEntityItem(0);
        }
        else {
          logMessage("**************CROSSSELL not found in list**");
        }

        grpUpSell = m_elist.getEntityGroup("UPSELL");
        eiUpSell = null;
        if (grpUpSell != null) {
          eiUpSell = grpUpSell.getEntityItem(0);
        }
        else {
          logMessage("**************UPSELL not found in list**");
        }

        grpStdAmendText = m_elist.getEntityGroup("STANDAMENDTEXT");
        eiStdAmendText = null;
        if (grpStdAmendText != null) {
          eiStdAmendText = grpStdAmendText.getEntityItem(0);
        }
        else {
          logMessage("**************STANDAMENDTEXT not found in list**");
        }

        grpCOFCrypto = m_elist.getEntityGroup("COFCRYPTO");
        eiCOFCrypto = null;
        if (grpCOFCrypto != null) {
          eiCOFCrypto = grpCOFCrypto.getEntityItem(0);
        }
        else {
          logMessage("**************COFCRYPTO not found in list**");
        }

        grpOOFCrypto = m_elist.getEntityGroup("OOFCRYPTO");
        eiOOFCrypto = null;
        if (grpOOFCrypto != null) {
          eiOOFCrypto = grpOOFCrypto.getEntityItem(0);
        }
        else {
          logMessage("**************OOFCRYPTO not found in list**");
        }

        grpCrypto = m_elist.getEntityGroup("CRYPTO");
        eiCrypto = null;
        if (grpCrypto != null) {
          eiCrypto = grpCrypto.getEntityItem(0);
        }
        else {
          logMessage("**************CRYPTO not found in list**");
        }

        grpCofOrganUnit = m_elist.getEntityGroup("COFORGANUNIT");
        eiCofOrganUnit = null;
        if (grpCofOrganUnit != null) {
          eiCofOrganUnit = grpCofOrganUnit.getEntityItem(0);
        }
        else {
          logMessage("**************COFORGANUNIT not found in list**");
        }

        grpIndividual = m_elist.getEntityGroup("INDIVIDUAL");
        eiIndividual = null;
        if (grpIndividual != null) {
          eiIndividual = grpIndividual.getEntityItem(0);
        }
        else {
          logMessage("**************INDIVIDUAL not found in list**");
        }

        grpPublication = m_elist.getEntityGroup("PUBLICATION");
        eiPublication = null;
        if (grpPublication != null) {
          eiPublication = grpPublication.getEntityItem(0);
        }
        else {
          logMessage("**************PUBLICATION not found in list**");
        }

        grpEducation = m_elist.getEntityGroup("EDUCATION");
        eiEducation = null;
        if (grpEducation != null) {
          eiEducation = grpEducation.getEntityItem(0);
        }
        else {
          logMessage("**************EDUCATION not found in list**");
        }

        grpAnnEducation = m_elist.getEntityGroup("ANNEDUCATION");
        eiAnnEducation = null;
        if (grpAnnEducation != null) {
          eiAnnEducation = grpAnnEducation.getEntityItem(0);
        }
        else {
          logMessage("**************ANNEDUCATION not found in list**");
        }

        grpIvocat = m_elist.getEntityGroup("IVOCAT");
        eiIvocat = null;
        if (grpIvocat != null) {
          eiIvocat = grpIvocat.getEntityItem(0);
        }
        else {
          logMessage("**************IVOCAT not found in list**");
        }

        grpBoilPlateText = m_elist.getEntityGroup("BOILPLATETEXT");
        eiBoilPlateText = null;
        if (grpBoilPlateText != null) {
          eiBoilPlateText = grpBoilPlateText.getEntityItem(0);
        }
        else {
          logMessage("**************BOILPLATETEXT not found in list**");
        }

        grpCatIncl = m_elist.getEntityGroup("CATINCL");
        eiCatIncl = null;
        if (grpCatIncl != null) {
          eiCatIncl = grpCatIncl.getEntityItem(0);
        }
        else {
          logMessage("**************CATINCL not found in list**");
        }

        grpAlternateOF = m_elist.getEntityGroup("ALTERNATEOF");
        eiAlternateOF = null;
        if (grpAlternateOF != null) {
          eiAlternateOF = grpAlternateOF.getEntityItem(0);
        }
        else {
          logMessage("**************ALTERNATEOF not found in list**");
        }

        grpCofBPExhibit = m_elist.getEntityGroup("COFBPEXHIBIT");
        eiCofBPExhibit = null;
        if (grpCofBPExhibit != null) {
          eiCofBPExhibit = grpCofBPExhibit.getEntityItem(0);
        }
        else {
          logMessage("**************COFBPEXHIBIT not found in list**");
        }

        grpBPExhibit = m_elist.getEntityGroup("BPEXHIBIT");
        eiBPExhibit = null;
        if (grpBPExhibit != null) {
          eiBPExhibit = grpBPExhibit.getEntityItem(0);
        }
        else {
          logMessage("**************BPEXHIBIT not found in list**");
        }

        grpCofPubs = m_elist.getEntityGroup("COFPUBS");
        eiCofPubs = null;
        if (grpCofPubs != null) {
          eiCofPubs = grpCofPubs.getEntityItem(0);
        }
        else {
          logMessage("**************COFPUBS not found in list**");
        }

        grpEnvirinfo = m_elist.getEntityGroup("ENVIRINFO");
        eiEnvirinfo = null;
        if (grpEnvirinfo != null) {
          eiEnvirinfo = grpEnvirinfo.getEntityItem(0);
        }
        else {
          logMessage("**************ENVIRINFO not found in list**");
        }

        grpAltEnvirinfo = m_elist.getEntityGroup("ALTDEPENENVIRINFO");
        eiAltEnvirinfo = null;
        if (grpAltEnvirinfo != null) {
          eiAltEnvirinfo = grpAltEnvirinfo.getEntityItem(0);
        }
        else {
          logMessage("**************ALTDEPENENVIRINFO not found in list**");
        }

        grpPackaging = m_elist.getEntityGroup("PACKAGING");
        eiPackaging = null;
        if (grpPackaging != null) {
          eiPackaging = grpPackaging.getEntityItem(0);
        }
        else {
          logMessage("**************PACKAGING not found in list**");
        }

        grpAnnSalesmanchg = m_elist.getEntityGroup("ANNSALESMANCHG");
        eiAnnSalesmanchg = null;
        if (grpAnnSalesmanchg != null) {
          eiAnnSalesmanchg = grpAnnSalesmanchg.getEntityItem(0);
        }
        else {
          logMessage("**************ANNSALESMANCHG not found in list**");
        }
        grpSalesmanchg = m_elist.getEntityGroup("SALESMANCHG");
        eiSalesmanchg = null;
        if (grpSalesmanchg != null) {
          eiSalesmanchg = grpSalesmanchg.getEntityItem(0);
        }
        else {
          logMessage("**************SALESMANCHG not found in list**");
        }

        grpOrderOFAvail = m_elist.getEntityGroup("OOFAVAIL");
        eiOrderOFAvail = null;
        if (grpOrderOFAvail != null) {
          eiOrderOFAvail = grpOrderOFAvail.getEntityItem(0);
        }
        else {
          logMessage("**************OOFAVAIL not found in list**");
        }

        grpAnnToAnnDeliv = m_elist.getEntityGroup("ANNTOANNDELIVER");
        eiAnnToAnnDeliv = null;
        if (grpAnnToAnnDeliv != null) {
          eiAnnToAnnDeliv = grpAnnToAnnDeliv.getEntityItem(0);
        }
        else {
          logMessage("**************ANNTOANNDELIVER not found in list**");
        }

        grpAnnDelReqTrans = m_elist.getEntityGroup("ANNDELREQTRANS");
        eiAnnDelReqTrans = null;
        if (grpAnnDelReqTrans != null) {
          eiAnnDelReqTrans = grpAnnDelReqTrans.getEntityItem(0);
        }
        else {
          logMessage("**************ANNDELREQTRANS not found in list**");
        }

        grpAnnToDescArea = m_elist.getEntityGroup("ANNTODESCAREA");
        eiAnnToDescArea = null;
        if (grpAnnToDescArea != null) {
          eiAnnToDescArea = grpAnnToDescArea.getEntityItem(0);
        }
        else {
          logMessage("**************ANNTODESCAREA not found in list**");
        }

        grpCofPrice = m_elist.getEntityGroup("COFPRICE");
        eiCofPrice = null;
        if (grpCofPrice != null) {
          eiCofPrice = grpCofPrice.getEntityItem(0);
        }
        else {
          logMessage("**************COFPRICE not found in list**");
        }

        grpAnnPara = m_elist.getEntityGroup("ANNPARAA");
        eiAnnPara = null;
        if (grpAnnPara != null) {
          eiAnnPara = grpAnnPara.getEntityItem(0);
        }
        else {
          logMessage("**************ANNPARAA not found in list**");
        }

        grpDependCode = m_elist.getEntityGroup("ANNDEPA");
        eiDependCode = null;
        if (grpDependCode != null) {
          eiDependCode = grpDependCode.getEntityItem(0);
        }
        else {
          logMessage("**************ANNDEPA not found in list**");
        }

        grpCofChannel = m_elist.getEntityGroup("COFCHANNEL");
        eiCofChannel = null;
        if (grpCofChannel != null) {
          eiCofChannel = grpCofChannel.getEntityItem(0);
        }
        else {
          logMessage("**************COFCHANNEL not found in list**");
        }

        grpAnnCofa = m_elist.getEntityGroup("ANNCOFA");
        eiAnnCofa = null;
        if (grpAnnCofa != null) {
          eiAnnCofa = grpAnnCofa.getEntityItem(0);
        }
        else {
          logMessage("**************ANNCOFA not found in list**");
        }

        grpAnnCofOffMgmtGrpa = m_elist.getEntityGroup("ANNCOFOOFMGMTGRPA");
        eiAnnCofOffMgmtGrpa = null;
        if (grpAnnCofOffMgmtGrpa != null) {
          eiAnnCofOffMgmtGrpa = grpAnnCofOffMgmtGrpa.getEntityItem(0);
        }
        else {
          logMessage("**************ANNCOFOOFMGMTGRPA not found in list**");
        }

        grpAnnAvail = m_elist.getEntityGroup("ANNAVAILA");
        eiAnnAvail = null;
        if (grpAnnAvail != null) {
          eiAnnAvail = grpAnnAvail.getEntityItem(0);
        }
        else {
          logMessage("**************ANNAVAILA not found in list**");
        }

        grpAnnOp = m_elist.getEntityGroup("ANNOP");
        eiAnnOp = null;
        if (grpAnnOp != null) {
          eiAnnOp = grpAnnOp.getEntityItem(0);
        }
        else {
          logMessage("**************ANNOP not found in list**");
        }

        egComponent = m_elist.getEntityGroup("CMPNT");
        eiComponent = null;
        if (egComponent != null) {
          eiComponent = egComponent.getEntityItem(0);
        }
        else {
          logMessage("**************CMPNT not found in list**");
        }

        egSof = m_elist.getEntityGroup("SOF");
        eiSof = null;
        if (egSof != null) {
          eiSof = egSof.getEntityItem(0);
        }
        else {
          logMessage("**************SOF not found in list**");
        }

        egFeature = m_elist.getEntityGroup("FEATURE");
        eiFeature = null;
        if (egFeature != null) {
          eiFeature = egFeature.getEntityItem(0);
        }
        else {
          logMessage("**************FEATURE not found in list**");
        }

        egFinof = m_elist.getEntityGroup("FINOF");
        eiFinof = null;
        if (egFinof != null) {
          eiFinof = egFinof.getEntityItem(0);
        }
        else {
          logMessage("**************FINOF not found in list**");
        }

        rfaReport = new ReportFormatter();
        rfaReport.setABRItem(getABRItem());

        // Set up all the date information ...
        DateFormat df = DateFormat.getDateInstance();
        df.setCalendar(Calendar.getInstance());
        SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
        fmtDate.setCalendar(Calendar.getInstance());
        String strDayToday = fmtDate.format(new Date());

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
                 122         CmptToSof                     Cancelled
         */
        strCondition1 = getAttributeFlagEnabledValue(eiAnnounce, "LOB");
        logMessage("**************Announcement LOB is " + strCondition1);
        bIsAnnITS = strCondition1 != null ? strCondition1.equals("101") : false; //Flag to be used for getting the SOF mktname

        //DISTRTYPE=720 THEn final edit
        strCondition1 = getAttributeFlagEnabledValue(eiAnnounce, "DISTRTYPE");
        if (!strCondition1.equals("720")) {
          strCondition1 = getAttributeValue(eiAnnounce, "SENDTOMAINMENU", "NoValuefoundSENDTOMAINMENU");
          //        println("Value of SENDTOMAINMENU is "+strCondition1);
          if (strCondition1.equals("Yes")) {
            //println("strCondition1");
            strCondition1 = getAttributeFlagEnabledValue(eiAnnounce, "ANCYCLESTATUS");
            strCondition1 = strCondition1.substring(strCondition1.indexOf("|") == -1 ? 0 :
                strCondition1.indexOf("|") + 1); //Read after the "|"
            strCondition2 = getAttributeValue(eiAnnounce, "ANNDATE", "");
            i = Integer.valueOf(strCondition1).intValue();
            bConditionOK = true;
            switch (i) {
              case 112: //Ready for final review
              case 114: //Approved
              case 115: //Approved with Risk
                strCondition3 = "preEdit";
                break;
              case 117: //Change (Approved w/Risk)
              case 116: //Released to Production Management
                if (strCondition2.compareTo(strDayToday) > 0) { //set to final only if announcement date > today
                  strCondition3 = "final";
                }
                else {
                  strCondition3 = "correction";
                }
                break;
              default:
                println("ANCYCLESTATUS returned unexpected flag value");
                break;
            }
          }
          else {
            strCondition3 = "return";
          }
        }
        else {
          strCondition3 = "finalEdit";
        }
        println(strCondition3); //print the request type
        println(".*$REQUESTTYPE_END");
        println(".*$USERIDS_BEGIN");
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

    }
    catch (Exception exc) {
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
    }
    finally {
      String strNavName = eiAnnounce.getNavAttrDescription();

      if (strNavName.length() > 34) {
        strNavName = strNavName.substring(0, 34);
      }

      String strDgName = "Extract for " + strNavName + " | " + (getReturnCode() == PASS ? "Passed" : "Failed") +
          " | Complete";
      setDGTitle(strDgName);

      setDGRptName("RFA_IGSSVS");
      setDGRptClass("RFA_IGSSVS");
      printDGSubmitString(); //Stuff into report for subscription and notification

      // set DG submit string
      setDGString(getABRReturnCode());
      // make sure the lock is released
      if (!isReadOnly()) {
        clearSoftLock();
      }
    }
  }

  /*
   * This will navigate from the given EntityItem downward or upward the list of entitytypes
   * in the array till it arrives at the final one, if a match is found, it will get the attribute
   * value
   */
  /**
   *  Gets the linkedEntityAttrValue attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom              Description of the Parameter
   *@param  _strEntityTypes      Description of the Parameter
   *@param  _strTargetAttribute  Description of the Parameter
   *@param  _bGoDown             Description of the Parameter
   *@return                      The linkedEntityAttrValue value
   */
  private String getLinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute,
                                          boolean _bGoDown) {
    return getLinkedEntityAttrValue(_eiFrom, _strEntityTypes, _strTargetAttribute, _bGoDown, null, null);
  }

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
   */
  private String getLinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute,
                                          boolean _bGoDown, String[] _strCheckAttribute, String[] _strCheckValues) {
    String strReturn = null;
    EntityItem eiLinkItem = null;
    if (_strCheckAttribute == null) {
      eiLinkItem = getLinkedEntityItem(_eiFrom, _strEntityTypes, _bGoDown);
    }
    else {
      eiLinkItem = getLinkedEntityItem(_eiFrom, _strEntityTypes, _bGoDown, _strCheckAttribute, _strCheckValues);
    }
    strReturn = getAttributeValue(eiLinkItem, _strTargetAttribute, " ");

    return strReturn;
  }

  /**
   *  Gets the downlinkedEntityAttrValue attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom              Description of the Parameter
   *@param  _strEntityTypes      Description of the Parameter
   *@param  _strTargetAttribute  Description of the Parameter
   *@return                      The downlinkedEntityAttrValue value
   */
  private String getDownlinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute) {
    return getLinkedEntityAttrValue(_eiFrom, _strEntityTypes, _strTargetAttribute, true);
  }

  private String getDownlinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute,
                                              String[] _strCheckAttribute, String[] _strCheckValues) {
    return getLinkedEntityAttrValue(_eiFrom, _strEntityTypes, _strTargetAttribute, true, _strCheckAttribute,
                                    _strCheckValues);
  }

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
  private String getUplinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute,
                                            String[] _strCheckAttribute, String[] _strCheckValues) {
    return getLinkedEntityAttrValue(_eiFrom, _strEntityTypes, _strTargetAttribute, false, _strCheckAttribute,
                                    _strCheckValues);
  }*/

  /**
   *  Gets the uplinkedEntityAttrValue attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom              Description of the Parameter
   *@param  _strEntityTypes      Description of the Parameter
   *@param  _strTargetAttribute  Description of the Parameter
   *@return                      The uplinkedEntityAttrValue value
   */
  private String getUplinkedEntityAttrValue(EntityItem _eiFrom, String _strEntityTypes[], String _strTargetAttribute) {
    return getLinkedEntityAttrValue(_eiFrom, _strEntityTypes, _strTargetAttribute, false);
  }

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
  private EntityItem getLinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[], boolean _bGoDown,
                                         String[] _strCheckAttribute, String[] _strCheckValues) {
    D.ebug(D.EBUG_SPEW, "In getLinkedEntityItem :" + _eiFrom.getKey());

    EntityItem eiLinkItem = _eiFrom;
    String strTargetEntityType = _strEntityTypes[_strEntityTypes.length - 1];
    EntityItem eiReturn = null;
    Vector vTemp = new Vector();
    D.ebug(D.EBUG_SPEW, "In getLinkedEntityItem 1");
    for (int k = 0; k < _strEntityTypes.length; k++) {
      if (_strCheckAttribute == null) {
        vTemp = searchEntityItemLink(eiLinkItem, null, null, true, _bGoDown, _strEntityTypes[k]);
      }
      else {
        if (k == _strEntityTypes.length - 1) { //Match attribute values only at the target
          D.ebug(D.EBUG_SPEW,
                 "getLinkedEntityItem: matching from " + eiLinkItem.getKey() + " to target " + _strEntityTypes[k] +
                 " for values " + _strCheckAttribute.toString() + " == " + _strCheckValues.toString());
          vTemp = searchEntityItemLink(eiLinkItem, _strCheckAttribute, _strCheckValues, true, _bGoDown,
                                       _strEntityTypes[k]);
        }
        else {
          vTemp = searchEntityItemLink(eiLinkItem, null, null, true, _bGoDown, _strEntityTypes[k]);
        }
      }
      D.ebug(D.EBUG_SPEW, "getLinkedEntityItem: Navigating " + eiLinkItem.getKey() + " to " + _strEntityTypes[k]);
      if (vTemp.size() > 0) {
        eiLinkItem = (EntityItem) vTemp.elementAt(0);
      }
    }
    eiReturn = eiLinkItem;

    if (!eiReturn.getEntityType().equals(strTargetEntityType)) {
      D.ebug(D.EBUG_SPEW,
             "getLinkedEntityItem: could not find target ETYPE:" + strTargetEntityType + " start " + _eiFrom.getKey() +
             ":" + _strEntityTypes.toString());
      eiReturn = null;
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
  private EntityItem getDownlinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[], String[] _strCheckAttribute,
                                             String[] _strCheckValues) {
    return getLinkedEntityItem(_eiFrom, _strEntityTypes, true, _strCheckAttribute, _strCheckValues);
  }*/

  /**
   *  Gets the downlinkedEntityItem attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom          Description of the Parameter
   *@param  _strEntityTypes  Description of the Parameter
   *@return                  The downlinkedEntityItem value
   */
  private EntityItem getDownlinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[]) {
    return getLinkedEntityItem(_eiFrom, _strEntityTypes, true);
  }

  /**
   *  Gets the uplinkedEntityItem attribute of the RFA_IGSSVS object
   *
   *@param  _eiFrom          Description of the Parameter
   *@param  _strEntityTypes  Description of the Parameter
   *@return                  The uplinkedEntityItem value
   */
  private EntityItem getUplinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[]) {
    D.ebug(D.EBUG_SPEW, "In getUplinkedEntityItem :" + _eiFrom.getKey());
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
  private EntityItem getUplinkedEntityItem(EntityItem _eiFrom, String _strEntityTypes[], String[] _strCheckAttribute,
                                           String[] _strCheckValues) {
    D.ebug(D.EBUG_SPEW, "In getUplinkedEntityItem :" + _eiFrom.getKey());
    return getLinkedEntityItem(_eiFrom, _strEntityTypes, false, _strCheckAttribute, _strCheckValues);
  }*/

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
  private Vector searchEntityGroup(EntityGroup _egFrom, String[] _strCheckAttribute, String[] _strCheckValues,
                                   boolean _bAllTrue) {
    Vector vRetval = new Vector();
   // String strAttrValue = null;
//logMessage("**********searchEntityGroup:Attributes:"_strCheckAttribute.toString()+":Values:"+_strCheckValues.toString());
    EntityItem eiCurrentItem = null;
    for (int i = 0; i < _egFrom.getEntityItemCount(); i++) {
      eiCurrentItem = _egFrom.getEntityItem(i);
      if (_strCheckAttribute != null) {
        if (foundInEntity(eiCurrentItem, _strCheckAttribute, _strCheckValues, _bAllTrue)) {
          vRetval.add(eiCurrentItem);
        }
      }
      else {
        vRetval.add(eiCurrentItem);
      }
    }
//logMessage("**********searchEntityGroup done");

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
  private Vector searchEntityVectorLink(Vector _vEntItems, String[] _strCheckAttribute, String[] _strCheckValues,
                                        boolean _bAllTrue, boolean _bDownLink, String _strLinkEtype) {
    Vector vRetval = new Vector();
   // boolean bConditionOK = false;
    //String strAttrValue = null;
    EntityItem eiCurrentItem = null;
    EntityItem eiLinkItem = null;
    int iLinkCount = 0;
    if (_vEntItems.size() == 0) {
      logMessage("searchEntityVectorLink:NO ITEMS FOUND");
    }
    for (int i = 0; i < _vEntItems.size(); i++) {
      eiCurrentItem = (EntityItem) _vEntItems.elementAt(i);
      if (_bDownLink) {
        iLinkCount = eiCurrentItem.getDownLinkCount();
      }
      else {
        iLinkCount = eiCurrentItem.getUpLinkCount();
      }
      for (int j = 0; j < iLinkCount; j++) {
        if (_bDownLink) {
          eiLinkItem = (EntityItem) eiCurrentItem.getDownLink(j);
        }
        else {
          eiLinkItem = (EntityItem) eiCurrentItem.getUpLink(j);
        }
        if (eiLinkItem != null) {
          if (eiLinkItem.getEntityType().equals(_strLinkEtype)) {
            D.ebug(D.EBUG_SPEW,
                   "searchEntityVectorLink:Linking from :" + eiCurrentItem.getKey() + " to " + eiLinkItem.getKey());
            if (_strCheckAttribute != null) {
              if (foundInEntity(eiLinkItem, _strCheckAttribute, _strCheckValues, _bAllTrue)) {
                if (!vRetval.contains(eiLinkItem)) {
                  vRetval.add(eiLinkItem);
                }
              }
            }
            else {
              if (!vRetval.contains(eiLinkItem)) {
                vRetval.add(eiLinkItem);
              }
            }
          }
        }
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
   *@param  _strLinkEtype       Linked entity type to search
   *@return                     Description of the Return Value
   */
  private Vector searchEntityVectorLink1(Vector _vEntItems, String[] _strCheckAttribute, String[] _strCheckValues,
                                         boolean _bAllTrue, String _strLinkEtype) {
    Vector vRetval = new Vector();
    //boolean bConditionOK = false;
    //String strAttrValue = null;
    EntityItem eiCurrentItem = null;
    //int iLinkCount = 0;
    D.ebug(D.EBUG_SPEW, "searchEntityVectorLink1-- _vEntItems.size(): " + _vEntItems.size() + "_strCheckAttribute: " +
           _strCheckAttribute + "_strCheckValues: " + _strCheckValues + "_bAllTrue: " + _bAllTrue +
           "_strLinkEtype: " + _strLinkEtype);
    if (_vEntItems.size() == 0) {
      logMessage("searchEntityVectorLink for searchEntityVectorLink1:NO ITEMS FOUND");
    }
    for (int i = 0; i < _vEntItems.size(); i++) {
      eiCurrentItem = (EntityItem) _vEntItems.elementAt(i);
      D.ebug(D.EBUG_SPEW, "eiCurrentItem" + eiCurrentItem + ": " + eiCurrentItem.getKey());
      D.ebug(D.EBUG_SPEW, "searchEntityVectorLink1 mystuff for i" + i + "of: " + _vEntItems.size());
      if (eiCurrentItem != null) {
        if (eiCurrentItem.getEntityType().equals(_strLinkEtype)) {
          D.ebug(D.EBUG_SPEW, "searchEntityVectorLink1:Linking from: " + eiCurrentItem.getKey());
          if (_strCheckAttribute != null) {
            D.ebug(D.EBUG_SPEW, "We're getting here: " + eiCurrentItem.getKey() + ": " + _strCheckAttribute + ": " +
                   _strCheckValues + ": " + _bAllTrue);
            if (foundInEntity(eiCurrentItem, _strCheckAttribute, _strCheckValues, _bAllTrue)) {
              D.ebug(D.EBUG_SPEW, "We're getting here2");
              if (!vRetval.contains(eiCurrentItem)) {
                D.ebug(D.EBUG_SPEW, "We're getting here3");
                vRetval.add(eiCurrentItem);
              }
            }
          }
          else {
            if (!vRetval.contains(eiCurrentItem)) {
              D.ebug(D.EBUG_SPEW, "We're getting here4");
              vRetval.add(eiCurrentItem);
            }
          }
        }
      }
    }
    return vRetval;
  }

  private Vector searchInGeo(Vector _vEntItems, String _strGeoToCheck) {
    String[] strGeoToCheck = new String[] {
        _strGeoToCheck};
    return searchInGeo(_vEntItems, strGeoToCheck);
  }

  /**
   *  Description of the Method
   *
   *@param  _vEntItems      Description of the Parameter
   *@param  _strGeoToCheck  Description of the Parameter
   *@return                 Description of the Return Value
   */
  private Vector searchInGeo(Vector _vEntItems, String[] _strGeoToCheck) {
    Vector vProcessVector = new Vector();
    EntityItem eiCheckItem = null;
    Hashtable hNoDupes = new Hashtable();

    boolean bFound = false;
    //Mark all the entities that are not part of the given geo
    for (int ix = 0; ix < _vEntItems.size(); ix++) {
      eiCheckItem = (EntityItem) _vEntItems.elementAt(ix);
      String strGeoToCheck = null;
      bFound = false;
      for (int iy = 0; iy < _strGeoToCheck.length; iy++) {
        strGeoToCheck = _strGeoToCheck[iy];
//logMessage("searchInGeo:checking for GEO:"+strGeoToCheck);
        if (strGeoToCheck.equals("US")) {
          bFound = m_geList.isRfaGeoUS(eiCheckItem);
//logMessage("searchInGeo:checking for GEO:"+strGeoToCheck+":"+eiCheckItem.getKey()+":found:"+bFound);
          break;
        }
        else if (strGeoToCheck.equals("AP")) {
          bFound = m_geList.isRfaGeoAP(eiCheckItem);
//logMessage("searchInGeo:checking for GEO:"+strGeoToCheck+":"+eiCheckItem.getKey()+":found:"+bFound);
          break;
        }
        else if (strGeoToCheck.equals("CAN")) {
          bFound = m_geList.isRfaGeoCAN(eiCheckItem);
//logMessage("searchInGeo:checking for GEO:"+strGeoToCheck+":"+eiCheckItem.getKey()+":found:"+bFound);
          break;
        }
        else if (strGeoToCheck.equals("EMEA")) {
          bFound = m_geList.isRfaGeoEMEA(eiCheckItem);
//logMessage("searchInGeo:checking for GEO:"+strGeoToCheck+":"+eiCheckItem.getKey()+":found:"+bFound);
          break;
        }
        else if (strGeoToCheck.equals("LA")) {
          bFound = m_geList.isRfaGeoLA(eiCheckItem);
//logMessage("searchInGeo:checking for GEO:"+strGeoToCheck+":"+eiCheckItem.getKey()+":found:"+bFound);
          break;
        }
      }
      if (bFound) { // we found it, now check whether we added this before
        if (!hNoDupes.containsKey(eiCheckItem.getKey())) {
          hNoDupes.put(eiCheckItem.getKey(), eiCheckItem);
          vProcessVector.add(eiCheckItem);
          D.ebug(D.EBUG_SPEW, "searchInGeo:checking for GEO:Adding:" + eiCheckItem.getKey());

        }
      }
    }
    return vProcessVector;
  }

  /**
   * Construct a single GEO tag string from a bunch of availability entities. Do not count
   * GEO's which have already been found in another avail.
   */
  private String getAllGeoTags(Vector _v) {

    String strReturn = "";
    EntityItem eiTempAvail = null;
    boolean bIsUS = false;
    boolean bIsAP = false;
    boolean bIsCAN = false;
    boolean bIsEMEA = false;
    boolean bIsLA = false;
    D.ebug(D.EBUG_SPEW, "getAllGeoTags:Vector is ");
    displayContents(_v);
    for (int x = 0; x < _v.size(); x++) {
      eiTempAvail = (EntityItem) _v.elementAt(x);

      if (m_geList == null) {
        logMessage("getGeoTags:GE List is null!");
      }
      if (!bIsUS) {
        if (m_geList.isRfaGeoUS(eiTempAvail)) {
          bIsUS = true;
        }
      }
      if (!bIsAP) {
        if (m_geList.isRfaGeoAP(eiTempAvail)) {
          bIsAP = true;
        }
      }
      if (!bIsCAN) {
        if (m_geList.isRfaGeoCAN(eiTempAvail)) {
          bIsCAN = true;
        }
      }
      if (!bIsEMEA) {
        if (m_geList.isRfaGeoEMEA(eiTempAvail)) {
          bIsEMEA = true;
        }
      }
      if (!bIsLA) {
        if (m_geList.isRfaGeoLA(eiTempAvail)) {
          bIsLA = true;
        }
      }
    }
    if (bIsUS) {
      strReturn = "US";
    }

    if (bIsAP) {
      if (strReturn.length() > 0) {
        strReturn += ", AP";
      }
      else {
        strReturn = "AP";
      }
    }

    if (bIsCAN) {
      if (strReturn.length() > 0) {
        strReturn += ", CAN";
      }
      else {
        strReturn = "CAN";
      }
    }
    if (bIsEMEA) {
      if (strReturn.length() > 0) {
        strReturn += ", EMEA";
      }
      else {
        strReturn = "EMEA";
      }
    }
    if (bIsLA) {
      if (strReturn.length() > 0) {
        strReturn += ", LA";
      }
      else {
        strReturn = "LA";
      }
    }
    logMessage("getAllGeoTags:returning GEO tag :" + strReturn);
    return strReturn;
  }

  /**
   *  Gets the geoTags attribute of the RFA_IGSSVS object
   *
   *@param  _entCheck  Description of the Parameter
   *@return            The geoTags value
   */
  private String getGeoTags(EntityItem _entCheck) {
    String strReturn = "";
    if (m_geList == null) {
      logMessage("getGeoTags:GE List is null!");
    }
    if (m_geList.isRfaGeoUS(_entCheck)) {
      strReturn = "US";
    }
    if (m_geList.isRfaGeoAP(_entCheck)) {
      if (strReturn.length() > 0) {
        strReturn += ", AP";
      }
      else {
        strReturn = "AP";
      }
    }
    if (m_geList.isRfaGeoCAN(_entCheck)) {
      if (strReturn.length() > 0) {
        strReturn += ", CAN";
      }
      else {
        strReturn = "CAN";
      }
    }
    if (m_geList.isRfaGeoEMEA(_entCheck)) {
      if (strReturn.length() > 0) {
        strReturn += ", EMEA";
      }
      else {
        strReturn = "EMEA";
      }
    }
    if (m_geList.isRfaGeoLA(_entCheck)) {
      if (strReturn.length() > 0) {
        strReturn += ", LA";
      }
      else {
        strReturn = "LA";
      }
    }
    logMessage("getGeoTags:returning GEO tag for entity:" + _entCheck.getKey() + ":" + strReturn);
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
   private Vector searchEntityGroupLink(EntityGroup _egFrom, String[] _strCheckAttribute, String[] _strCheckValues,
                                        boolean _bAllTrue, boolean _bDownLink, String _strLinkEtype) {
     Vector vRetval = new Vector();
     if (_egFrom == null) {
       logMessage("pASSED NULL ENTITYGROUP");
       return vRetval;
     }
     D.ebug(D.EBUG_SPEW, "searchEntityGroupLink..Group is " + _egFrom.getEntityType());
     //boolean bConditionOK = false;
     //String strAttrValue = null;
     EntityItem eiCurrentItem = null;
     EntityItem eiLinkItem = null;
     int iLinkCount = 0;
     for (int i = 0; i < _egFrom.getEntityItemCount(); i++) {
       eiCurrentItem = _egFrom.getEntityItem(i);
       D.ebug(D.EBUG_SPEW, "Searching " + eiCurrentItem + getEntityType() + ":" + eiCurrentItem.getEntityID());
       if (_bDownLink) {
         iLinkCount = eiCurrentItem.getDownLinkCount();
       }
       else {
         iLinkCount = eiCurrentItem.getUpLinkCount();
       }
       for (int j = 0; j < iLinkCount; j++) {
         if (_bDownLink) {
           eiLinkItem = (EntityItem) eiCurrentItem.getDownLink(j);
           D.ebug(D.EBUG_SPEW, "Getting Downlinked " + eiLinkItem + getEntityType() + ":" + eiLinkItem.getEntityID());
         }
         else {
           eiLinkItem = (EntityItem) eiCurrentItem.getUpLink(j);
         }
         if (eiLinkItem != null) {
           if (eiLinkItem.getEntityType().equals(_strLinkEtype)) {
             if (_strCheckAttribute != null) {
               if (foundInEntity(eiLinkItem, _strCheckAttribute, _strCheckValues, _bAllTrue)) {
                 vRetval.add(eiLinkItem);
               }
             }
             else {
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
   private Vector searchEntityItemLink(EntityItem _eiFrom, String[] _strCheckAttribute, String[] _strCheckValues,
                                       boolean _bAllTrue, boolean _bDownLink, String _strLinkEtype) {
     Vector vRetval = new Vector();
     //boolean bConditionOK = false;
     //String strAttrValue = null;
     EntityItem eiLinkItem = null;
     int iLinkCount = 0;
     D.ebug(D.EBUG_SPEW,
            "searchEntityItemLink: searching for " + _strLinkEtype + (_bDownLink ? " downlinks " : " uplinks ") +
            " from " + _eiFrom.getKey());
     if (_eiFrom == null) {
       return vRetval;
     }
     if (_bDownLink) {
       iLinkCount = _eiFrom.getDownLinkCount();
     }
     else {
       iLinkCount = _eiFrom.getUpLinkCount();
     }
     D.ebug(D.EBUG_SPEW,
            "searchEntityItemLink: found " + iLinkCount + (_bDownLink ? " downlinks " : " uplinks ") + " from " +
            _eiFrom.getKey());
     for (int j = 0; j < iLinkCount; j++) {
       if (_bDownLink) {
         eiLinkItem = (EntityItem) _eiFrom.getDownLink(j);
       }
       else {
         eiLinkItem = (EntityItem) _eiFrom.getUpLink(j);
       }
       if (eiLinkItem != null) {
         if (eiLinkItem.getEntityType().equals(_strLinkEtype)) {
           if (_strCheckAttribute != null) {
             if (foundInEntity(eiLinkItem, _strCheckAttribute, _strCheckValues, _bAllTrue)) {
               D.ebug(D.EBUG_SPEW, "searchEntityItemLink:adding to vector");
               vRetval.add(eiLinkItem);
             }
           }
           else {
             vRetval.add(eiLinkItem);
           }
         }
       }
     }

     return vRetval;
   }

//*****************************

   /**
    *  Description of the Method
    *
    *@param  _eiItem       Description of the Parameter
    *@param  _strAttrList  Description of the Parameter
    *@param  _strNoValue   Description of the Parameter
    */
   private void printShortValueListInItem(EntityItem _eiItem, String[] _strAttrList, String _strNoValue) {
     int intAttLen = _strAttrList.length;
     String strVal = null;
     for (int j = 0; j < intAttLen; j++) {
       if (_strAttrList[j].trim().length() > 0) { //This is a attribute name

         strVal = getAttributeShortFlagDesc(_eiItem.getEntityType(), _eiItem.getEntityID(), _strAttrList[j],
                                            _strNoValue);
         //add to vector if there is more than 1 attr passed and does not return default
         if (intAttLen > 1) {
           vPrintDetails.add(strVal);
         }
         else if (!strVal.equals(_strNoValue)) {
           vPrintDetails.add(strVal);
         }
       }
     }
   }

  /**
   *  Description of the Method
   *
   *@param  _eiItem       Description of the Parameter
   *@param  _strAttrList  Description of the Parameter
   *@param  _strNoValue   Description of the Parameter
   *@param  _bXmlConvert  Description of the Parameter
   */
  private void printValueListInItem(EntityItem _eiItem, String[] _strAttrList, String _strNoValue, boolean _bXmlConvert) {
    int intAttLen = _strAttrList.length;
    String strVal = null;
    for (int j = 0; j < intAttLen; j++) {
      if (_strAttrList[j].trim().length() > 0) { //This is a attribute name
        strVal = getAttributeValue(_eiItem.getEntityType(), _eiItem.getEntityID(), _strAttrList[j], _strNoValue);
        if (strVal != null) {
          if (_bXmlConvert && strVal.trim().length() > 0) {
            strVal = transformXML(strVal);
          }
        }
        //add to vector if there is more than 1 attr passed and does not return default
        if (intAttLen > 1) {
          vPrintDetails.add(strVal);
        }
        else if (!strVal.equals(_strNoValue)) {
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
   *@param  _bXmlConvert        Description of the Parameter
   */
  private void printValueListInGroup(EntityGroup _egGroup, String[] _strAttrList, String _strCheckAttribute,
                                     String _strCheckValue, String _strNoValue, boolean _bXmlConvert) {
    EntityItem eiItem = null;
    //String strCheckValue = null;
    if (_egGroup != null) {
      for (int i = 0; i < _egGroup.getEntityItemCount(); i++) {
        eiItem = _egGroup.getEntityItem(i);
        if (_strCheckAttribute != null) {
          if (foundInEntity(eiItem, new String[] {_strCheckAttribute}, new String[] {_strCheckValue}, true)) {
            printValueListInItem(eiItem, _strAttrList, _strNoValue, _bXmlConvert);
          }
        }
        else {
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
   *@param  _bLongValue    Description of the Parameter
   *@param  _bXMLConvert   Description of the Parameter
   */
  private void printValueListInVector(Vector _vEntityItems, String[] _strAttrList, String _strNoValue,
                                      boolean _bLongValue, boolean _bXMLConvert) {
    EntityItem eiItem = null;
    //String strCheckValue = null;
    if (_vEntityItems != null) {
      for (int i = 0; i < _vEntityItems.size(); i++) {
        eiItem = (EntityItem) _vEntityItems.elementAt(i);
        if (_bLongValue) {
          printValueListInItem(eiItem, _strAttrList, _strNoValue, _bXMLConvert);
        }
        else {
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
  private void printValueInGroup(EntityGroup _egGroup, String _strAttributeName, String _strNoValue,
                                 boolean _bLastNewLine) {
    EntityItem eiPrint = null;
    String[] strAttr = new String[] {
        _strAttributeName};
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
  private boolean foundInGroup(EntityGroup _egSearchGroup, String _strAttributeName, String _strSearchFor) {
    return foundInGroup(_egSearchGroup, new String[] {_strAttributeName}, new String[] {_strSearchFor}, true);
  }*/

  /**
   *  Description of the Method
   *
   *@param  _egSearchGroup     Description of the Parameter
   *@param  _strAttributeName  Description of the Parameter
   *@param  _strSearchFor      Description of the Parameter
   *@param  _bAllTrue          Description of the Parameter
   *@return                    Description of the Return Value
   * /
  private boolean foundInGroup(EntityGroup _egSearchGroup, String[] _strAttributeName, String[] _strSearchFor,
                               boolean _bAllTrue) {
    boolean bRetVal = false;
    EntityItem eiSearchItem = null;
    String strAttributeValue = null;
    if (_egSearchGroup != null) {
      for (int i = 0; i < _egSearchGroup.getEntityItemCount(); i++) {
        eiSearchItem = _egSearchGroup.getEntityItem(i);
        if (foundInEntity(eiSearchItem, _strAttributeName, _strSearchFor, _bAllTrue)) {
          bRetVal = true;
        }
        if (bRetVal) { //Keep on going for an "AND" condition till all the conditions are evaluated
          if (!_bAllTrue) {
            break;
          }
        }
        else {
          if (_bAllTrue) {
            break;
          }
        }
      }
    }

    return bRetVal;
  }*/
/*
  private Vector foundInEntityVector(Vector _v, String[] _strAttributeName, String[] _strSearchFor, boolean _bAllTrue) {
    Vector vReturn = new Vector();
    EntityItem eItem = null;
    for (int i = 0; i < _v.size(); i++) {
      eItem = (EntityItem) _v.elementAt(i);
      if (foundInEntity(eItem, _strAttributeName, _strSearchFor, _bAllTrue)) {
        vReturn.add(eItem);
      }
    }
    return vReturn;
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
  private boolean foundInEntity(EntityItem _eiSearchItem, String[] _strAttributeName, String[] _strSearchFor,
                                boolean _bAllTrue) {
    boolean bRetVal = false;
    String strAttrValue = null;
    if (_eiSearchItem == null) {
      logMessage("Passing null entity item to search");
      return false;
    }

    for (int j = 0; j < _strAttributeName.length; j++) {
      //logMessage("Checking mystuff" + j + ": " + _strAttributeName.length);
      //logMessage("Checking " + _eiSearchItem.getEntityType() + ":" + _eiSearchItem.getEntityID() + ":" + _strAttributeName[j] + " for value " + _strSearchFor[j]);
      if (_strSearchFor[j].toLowerCase().equals(_strSearchFor[j].toUpperCase())) { //This is a flag code
        if (flagvalueEquals(_eiSearchItem.getEntityType(), _eiSearchItem.getEntityID(), _strAttributeName[j],
                            _strSearchFor[j])) {
          bRetVal = true;
        }
        else {
          bRetVal = false;
        }
      }
      else {
        strAttrValue = getAttributeValue(_eiSearchItem, _strAttributeName[j], "");
        if (strAttrValue.indexOf(_strSearchFor[j]) > -1) {
          bRetVal = true; //Keep on going for an "AND" condition till all the conditions are evaluated
          //println(" fOUND!!");
        }
        else {
          bRetVal = false;
          //println(" not fOUND!!");
        }
      }
      if (bRetVal) { //Keep on going for an "AND" condition till all the conditions are evaluated
        if (!_bAllTrue) {
          break;
        }
      }
      else {
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
   *  Get the getRevision
   *
   *@return    java.lang.String
   */
  public String getRevision() {
    return new String("$Revision: 1.157 $");
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
  private void printReport(boolean _bHeader, String[] _strHeader, int[] _iColWidths, Vector _vPrintDetails,
                           int _iOffset) {
    rfaReport.setOffset(_iOffset);
    printReport(_bHeader, _strHeader, _iColWidths, _vPrintDetails);
  }*/

  /**
   *  Description of the Method
   *
   *@param  _bHeader        Description of the Parameter
   *@param  _strHeader      Description of the Parameter
   *@param  _iColWidths     Description of the Parameter
   *@param  _vPrintDetails  Description of the Parameter
   */
  private void printReport(boolean _bHeader, String[] _strHeader, int[] _iColWidths, Vector _vPrintDetails) {
    if ( (_iColWidths.length > 1 || _bHeader)) {
      rfaReport.printHeaders(_bHeader);
      rfaReport.setHeader(_strHeader);
      rfaReport.setColWidth(_iColWidths);
      rfaReport.setDetail(_vPrintDetails);
      if (_vPrintDetails.size() > 0) {
        rfaReport.printReport();
      }
      rfaReport.setOffset(0); //Reset previous offsets
    }
    else {
      if (_iColWidths[0] == 69) {
        for (int x = 0; x < _vPrintDetails.size(); x++) {
          prettyPrint( (String) _vPrintDetails.elementAt(x), _iColWidths[0]);
        }
      }
      else if (_vPrintDetails.size() > 0) {
        rfaReport.printHeaders(_bHeader);
        rfaReport.setColWidth(_iColWidths);
        rfaReport.setDetail(_vPrintDetails);
        rfaReport.printReport();
      }
      rfaReport.setOffset(0); //Reset previous offsets
      rfaReport.setColumnSeparator(" ");
    }
  }

  /**
   *  Description of the Method
   */
  private void resetPrintvars() {
    vPrintDetails.removeAllElements();
    vPrintDetails = null;
    vPrintDetails = new Vector();
    rfaReport.setSortable(false);

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

  /**
   *  Description of the Method
   */
  private void processShortAnswers() {
    if (eiAnnounce == null) {
      println("Announce EntityItem IS NULL!");
    }
    println("CNTLNO = '" + getAttributeValue(eiAnnounce, "ANNNUMBER", "Not Populated") + "'");
    println("TYPE = '" + getAttributeValue(eiAnnounce, "OFANNTYPE", "Not Populated") + "'");
//TIR 6QKKNN    println("VERSION = '20.05'");
    println("VERSION = '22.04'");
    strSplit = "";
    logMessage("_1A*******************");
    strFilterAttr = new String[] {
        "DELIVERABLETYPE"};
    strFilterValue = new String[] {
        "860"};
    vReturnEntities1 = searchEntityGroup(grpAnnDeliv, strFilterAttr, strFilterValue, true);
    eiAnnDeliv = vReturnEntities1.size() > 0 ? (EntityItem) vReturnEntities1.elementAt(0) : null;
    bConditionOK = false;
    if (eiAnnDeliv != null) {
      bConditionOK = true;
      strSplit = (eiAnnDeliv != null ? getAttributeValue(eiAnnDeliv, "SUBJECTLINE_1", "") : "ANNDELIVERABLE NOT LINKED");
    }
    println(".*$P_1A = '" + strSplit + "'");
    strSplit = "";
    if (bConditionOK) {
//      strSplit = (eiAnnDeliv != null ?A, "DELIVERABLETYPE", "Not Populated") : "ANNDELIVERABLE NOT LINKED") + "'";
      strSplit = (eiAnnDeliv != null ? getAttributeValue(eiAnnDeliv, "SUBJECTLINE_2", "") : "ANNDELIVERABLE NOT LINKED");
    }
    println(".*$P_1B = '" + strSplit + "'");
    //get the assoc from announcement to this
    strCondition2 = "";
    vReturnEntities1 = searchEntityGroupLink(grpAnnPara, null, null, true, true, "PARAMETERCODE");
    print(".*$P_1C = '");
    if (vReturnEntities1.size() > 0) {
      bConditionOK = false;
      for (i = 0; i < vReturnEntities1.size(); i++) {
        eiParamCode = (EntityItem) vReturnEntities1.elementAt(i);
        strCondition1 = getAttributeValue(eiParamCode, "PARAMETERCODENUMBER", "");
        if (!bConditionOK) {
          bConditionOK = true;
          strCondition2 = strCondition1;
        }
        else {
          strCondition2 += "," + strCondition1;
        }
        if (i == 1) {
          break;
        } //List only the first 2 parmcodes
      }
    }
    println(strCondition2 + "'");

    vReturnEntities1 = searchEntityGroupLink(grpDependCode, null, null, true, true, "DEPENDENCYCODE");
    print(".*$P_1D = '");
    if (vReturnEntities1.size() > 0) {
      bConditionOK = false;
      for (i = 0; i < vReturnEntities1.size(); i++) {
        eiDependCode = (EntityItem) vReturnEntities1.elementAt(i);
        strCondition1 = getAttributeValue(eiDependCode, "DEPENCODENUMBER", "Not Populated");
        if (!bConditionOK) {
          bConditionOK = true;
          strCondition2 = strCondition1;
        }
        else {
          strCondition2 += ", " + strCondition1;
        }
        if (i == 8) {
          break;
        } //List only the first 9 dependent codes
      }
    }
    println(strCondition2 + "'");

    strCondition2 = "";
    vReturnEntities1 = searchEntityGroupLink(grpDependCode, null, null, true, true, "DEPENDENCYCODE");
    print(".*$P_1E = '");
    if (vReturnEntities1.size() > 8) {
      bConditionOK = false;
      for (i = 9; i < vReturnEntities1.size(); i++) {
        eiDependCode = (EntityItem) vReturnEntities1.elementAt(i);
        strCondition1 = getAttributeValue(eiDependCode, "DEPENCODENUMBER", "Not Populated");
        if (!bConditionOK) {
          bConditionOK = true;
          strCondition2 = strCondition1;
        }
        else {
          strCondition2 += ", " + strCondition1;
        }
        if (i == 17) {
          break;
        } //List only the NEXT 9 dependent codes
      }
    }
    println(strCondition2 + "'");

    print(".*$P_1F = '");
    strCondition2 = "";
    if (vReturnEntities1.size() > 16) {
      bConditionOK = false;
      for (i = 17; i < vReturnEntities1.size(); i++) {
        eiDependCode = (EntityItem) vReturnEntities1.elementAt(i);
        strCondition1 = getAttributeValue(eiDependCode, "DEPENCODENUMBER", "Not Populated");
        if (!bConditionOK) {
          bConditionOK = true;
          strCondition2 = strCondition1;
        }
        else {
          strCondition2 += ", " + strCondition1;
        }
        if (i == 25) {
          break;
        } //List the LAST 9 dependent codes
      }
    }
    println(strCondition2 + "'");

    /*        Delete as per alan 11/5/03
         strCondition1 = getAttributeValue(eiAnnounce, "MULTIPLEOFFERING", "0");
         strSplit = (strCondition1.equalsIgnoreCase("Yes") ? "1" : "0");
         println(".*$P_2A = '" + strSplit + "'");

         strCondition1 = getAttributeValue(eiAnnounce, "STATEOFGENDIRECT", "0");
         strSplit = (strCondition1.equalsIgnoreCase("Yes") ? "1" : "0");
         println(".*$P_2B = '" + strSplit + "'");
     */

    //println(".*$P_3A = '" + (eiAnnProj != null ? getAttributeValue(eiAnnProj.getEntityType(), eiAnnProj.getEntityID(), "AVAILDCP_T", "") : "'") + "'");
    strCondition1 = (eiAnnReview != null ?
                     getAttributeValue(eiAnnReview.getEntityType(), eiAnnReview.getEntityID(), "ANREVIEW", "") : "");
    strSplit = "";
    strFilterAttr = new String[] {
        "ANNREVIEWDEF"};
    strFilterValue = new String[] {
        "101"};
    vReturnEntities1 = searchEntityGroup(grpAnnReview, strFilterAttr, strFilterValue, true);
    eiAnnReview = vReturnEntities1.size() > 0 ? (EntityItem) vReturnEntities1.elementAt(0) : null;
    strSplit = (eiAnnReview != null ?
                getAttributeValue(eiAnnReview.getEntityType(), eiAnnReview.getEntityID(), "ANREVDATE", "") : "");
    println(".*$P_3A = '" + strSplit + "'");
    strSplit = "";
    strFilterAttr = new String[] {
        "ANNREVIEWDEF"};
    strFilterValue = new String[] {
        "102"};
    vReturnEntities1 = searchEntityGroup(grpAnnReview, strFilterAttr, strFilterValue, true);
    eiAnnReview = vReturnEntities1.size() > 0 ? (EntityItem) vReturnEntities1.elementAt(0) : null;
    strSplit = (eiAnnReview != null ?
                getAttributeValue(eiAnnReview.getEntityType(), eiAnnReview.getEntityID(), "ANREVDATE", "") : "");
    println(".*$P_3B = '" + strSplit + "'");
    println(".*$P_3C = '" + (grpAnnouncement != null ? getAttributeValue(eiAnnounce, "ANNDATE", "") : "") + "'");
//Ask ALAN 4a, 4b....6K
//    println(".*$P_4A = '" + (grpAnnouncement != null ? getAttributeValue(eiAnnounce, "ANNTYPE", "") : "") + "'");
    println(".*$P_4A = '0'");
    println(".*$P_4B = '" + (grpAnnouncement != null ? getAttributeValue(eiAnnounce, "REVISIONLEVEL", "") : "'") + "'");
    //println(".*$P_4B = '0'");

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
    strFilterAttr = new String[] {
        "ORGANUNITTYPE"};
    strFilterValue = new String[] {
        "4156"};
    vReturnEntities1 = searchEntityGroup(grpOrganUnit, strFilterAttr, strFilterValue, false);
    if (vReturnEntities1.size() > 0) {
      eiOrganUnit = (EntityItem) vReturnEntities1.elementAt(0);
      bConditionOK = true;
    }
    strCondition1 = (bConditionOK ? getAttributeValue(eiOrganUnit, "NAME", "") : "");
    println(".*$P_7C = '" + strCondition1.trim() + "'");
    strCondition1 = (bConditionOK ? getAttributeValue(eiOrganUnit, "INITIALS", "") : "");
    println(".*$P_7D = '" + strCondition1.trim() + "'");
    strCondition1 = (bConditionOK ? getAttributeValue(eiOrganUnit, "STREETADDRESS", "") : "");

    strCondition2 = (bConditionOK ? getAttributeValue(eiOrganUnit, "CITY", "") : "");

    strCondition1 += strCondition1.length() > 0 && strCondition2.length() > 0 ? "," + strCondition2.trim() : "";

    strCondition2 = (bConditionOK ? getAttributeValue(eiOrganUnit, "STATE", "") : "");
    strCondition1 += strCondition1.length() > 0 && strCondition2.length() > 0 ? "," + strCondition2.trim() : "";

    strCondition2 = (bConditionOK ? getAttributeValue(eiOrganUnit, "COUNTRY", "") : "");
    strCondition1 += strCondition1.length() > 0 && strCondition2.length() > 0 ? "," + strCondition2.trim() : "";

    strCondition2 = (bConditionOK ? getAttributeValue(eiOrganUnit, "ZIPCODE", "") : "");
    strCondition1 += strCondition1.length() > 0 && strCondition2.length() > 0 ? "," + strCondition2.trim() : "";
    println(".*$P_7E = '" + strCondition1 + "'");

    /*
     *  Print 8A to 9A  if when ANNROLETYPE='Division President ' on ANNOP
     */
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(), eiAnnToOP.getEntityID(), "ANNROLETYPE", "15")) {
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
         Division Focal point
     */
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(), eiAnnToOP.getEntityID(), "ANNROLETYPE", "4")) {
        eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
        break;
      }
    }
    println(".*$P_9B = '" + (eiOP != null ? getAttributeValue(eiOP, "USERNAME", "") : "") + "'");
    println(".*$P_9C = '" + (eiOP != null ? getAttributeValue(eiOP, "VNETUID", "") : "") + "'");
    println(".*$P_9D = '" + (eiOP != null ? getAttributeValue(eiOP, "VNETNODE", "") : "") + "'");

    /*
         Sponsor
     */
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(), eiAnnToOP.getEntityID(), "ANNROLETYPE", "9")) {
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
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(), eiAnnToOP.getEntityID(), "ANNROLETYPE", "7")) {
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
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(), eiAnnToOP.getEntityID(), "ANNROLETYPE", "3")) {
        eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
        break;
      }
    }
    println(".*$P_11F = '" + (eiOP != null ? getAttributeValue(eiOP, "USERNAME", "") : "") + "'");
    println(".*$P_11G = '" + (eiOP != null ? getAttributeValue(eiOP, "TELEPHONE", "") : "") + "'");
    println(".*$P_11H = '" + (eiOP != null ? getAttributeValue(eiOP, "EMAIL", "") : "") + "'");
    println(".*$P_11I = '" + (eiOP != null ? getAttributeValue(eiOP, "VNETNODE", "") : "") + "'");

    /*
     *  Print 13A to 13E if when ANNROLETYPE='Marketing Comm Mgr' on ANNOP
     */
    eiAnnToOP = null;
    eiOP = null;
    bConditionOK = false;
    for (i = 0; i < grpAnnToOP.getEntityItemCount(); i++) {
      eiAnnToOP = grpAnnToOP.getEntityItem(i);
      if (flagvalueEquals(eiAnnToOP.getEntityType(), eiAnnToOP.getEntityID(), "ANNROLETYPE", "6")) {
        eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
        break;
      }
    }
    println(".*$P_13A = '" + (eiOP != null ? getAttributeValue(eiOP, "USERNAME", "") : "") + "'");
    println(".*$P_13B = '" + (eiOP != null ? getAttributeValue(eiOP, "TELEPHONE", "") : "") + "'");
    println(".*$P_13C = '" + (eiOP != null ? getAttributeValue(eiOP, "VNETUID", "") : "") + "'");
    println(".*$P_13D = '" + (eiOP != null ? getAttributeValue(eiOP, "VNETNODE", "") : "") + "'");
    println(".*$P_13E = '" + (eiOP != null ? getAttributeValue(eiOP, "SITE", "") : "") + "'");
    bConditionOK = false;
    eiChannel = null;
    strFilterAttr = new String[] {
        "AVAILTYPE"};
    strFilterValue = new String[] {
        "146"}; //Consider AVAILS OF type Planned Availability only 10/21  changed again 12/30
    //Check fb 52652
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, strFilterAttr, strFilterValue, true, true, "AVAIL");
    logMessage("****AVAIL*****");
    displayContents(vReturnEntities1);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "SOFAVAIL");
    logMessage("****SOFAVAIL*****");
    displayContents(vReturnEntities2);
    vSofFrmSofAvail = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "SOF");
    logMessage("****SOF*****");
    displayContents(vSofFrmSofAvail);
    /* This is a good time to store the components and the features from availability
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E)
     */
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "CMPNTAVAIL");
    logMessage("****CMPNTAVAIL*****");
    displayContents(vReturnEntities2);

    vCmpntFrmCmpntAvail = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "CMPNT");
    logMessage("****CMPNT*****");
    displayContents(vCmpntFrmCmpntAvail);

    //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E)
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "FEATUREAVAIL");
    logMessage("****FEATUREAVAIL*****");
    displayContents(vReturnEntities2);

    vFeatureFrmFeatureAvail = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "FEATURE");
    logMessage("****FEATURE*****");
    displayContents(vFeatureFrmFeatureAvail);

    vSofSortedbyMkt = sortEntities(vSofFrmSofAvail, new String[] {"MKTGNAME"});
    vReturnEntities5 = new Vector();
    vReturnEntities5.addAll(vSofFrmSofAvail);
    vReturnEntities5.addAll(vCmpntFrmCmpntAvail);
    vReturnEntities5.addAll(vFeatureFrmFeatureAvail);

    //Also sort them by mktmsg ...now they will have to be sorted by SOF mkgname - navigating all the way up
    strCmptToSof = new String[] {
        "SOFCMPNT", "SOF"};
    vCmptSortedbyMkt = sortEntities(vCmpntFrmCmpntAvail, new String[] {"MKTGNAME"});

    //String[] strFeatureToCmpt = new String[] {
    //    "CMPNTFEATURE", "CMPNT"};
    //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> CMPNTFEATURE (R) -> CMPNT (E) -> SOFCMPNT (R) -> SOF (E)
    vFeatureSortedbyMkt = sortEntities(vFeatureFrmFeatureAvail, new String[] {"MKTGNAME"});

    vAllSortedOfferings = RFAsort(vReturnEntities5);

    //Start 14A here
    /*
         ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOFCHANNEL (R) -> CHANNEL (E) when ROUTESTOMKTG = '110' (No) and CHANNELNAME = '373' (
         IBM sales Specialist) or
         ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNTCHANNEL (R) -> CHANNEL (E) when ROUTESTOMKTG = '110' (No) and
         CHANNELNAME = '373' (IBM sales Specialist) or
         ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURECHANNEL (R) -> CHANNEL (E)   when ROUTESTOMKTG = '110' (No)
         and CHANNELNAME = '373' (IBM sales Specialist)

         //Change here Bala
         ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOFCHANNEL (R) -> CHANNEL (E) -> CHGAA (A) -> GENERALAREA (E) when
     RFAGEO = ''200' (US) or "201" (LA) or "202" (CAN) or "204" (AP) and CHANNEL (E) when ROUTESTOMKTG = '110' (No) and
      CHANNELNAME = '373' (IBM sales Specialist)  or
         ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNTCHANNEL (R) -> CHANNEL (E) -> CHGAA (A) -> GENERALAREA (E) when
      RFAGEO = ''200' (US) or "201"
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFCHANNEL (R) -> CHANNEL (E) when ROUTESTOMKTG = '100' (Yes) (LA) or "202" (CAN) or "204" (AP) when
      ROUTESTOMKTG = '110' (No) and CHANNELNAME = '373' (IBM sales Specialist) or
         ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURECHANNEL (R) -> CHANNEL (E) -> CHGAA (A) -> GENERALAREA (E) when
      RFAGEO = ''200' (US) or "201" (LA) or "202" (CAN) or "204" (AP)when ROUTESTOMKTG = '110' (No) and CHANNELNAME = '373' (IBM sales Specialist)

     */
    /*     strFilterAttr = new String[]{"CHANNELNAME", "ROUTESTOMKTG"};
        strFilterValue = new String[]{"373", "110"};
        vReturnEntities2 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFCHANNEL");
        logMessage("****SOFCHANNEL*****");
        displayContents(vReturnEntities2);
     vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
        logMessage("****CHANNEL*****");
        displayContents(vReturnEntities3);
        strCondition1 = getAllGeoTags(vReturnEntities3);
        if (strCondition1.indexOf("US")>-1 ||
            strCondition1.indexOf("LA")>-1 ||
            strCondition1.indexOf("CAN")>-1)  {
        } else {
          vReturnEntities3 = new Vector();       //disregard channel if it doesnt belong to US, LA, CAN geos
        }
        if (vReturnEntities3.size() == 0) {     //Check for channel in components
          vReturnEntities2 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTCHANNEL");
          logMessage("****CMPNTCHANNEL*****");
          displayContents(vReturnEntities2);
     vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
          logMessage("****CHANNEL*****");
          displayContents(vReturnEntities3);
          strCondition1 = getAllGeoTags(vReturnEntities3);
          if (strCondition1.indexOf("US")>-1 ||
              strCondition1.indexOf("LA")>-1 ||
              strCondition1.indexOf("CAN")>-1)  {
          } else {
            vReturnEntities3 = new Vector();       //disregard channel if it doesnt belong to US, LA, CAN geos
          }


        }
        if (vReturnEntities3.size() == 0) {     //Check for channel in Features
          vReturnEntities2 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATURECHANNEL");
          logMessage("****FEATURECHANNEL*****");
          displayContents(vReturnEntities1);
     vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
          logMessage("****CHANNEL*****");
          displayContents(vReturnEntities3);
          strCondition1 = getAllGeoTags(vReturnEntities3);
          if (strCondition1.indexOf("US")>-1 ||
              strCondition1.indexOf("LA")>-1 ||
              strCondition1.indexOf("CAN")>-1)  {
          } else {
            vReturnEntities3 = new Vector();       //disregard channel if it doesnt belong to US, LA, CAN geos
          }
        }

        if (vReturnEntities3.size() > 0) {
          println(".*$P_14A = '1'");
        } else {
          println(".*$P_14A = '0'");
        }
        14A deleted 04/11/05
     */
    /*
         ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOFCHANNEL (R) -> CHANNEL (E) -> CHGAA (A) -> GENERALAREA (E) when RFAGEO = ''200' (US) or "201" (LA) or "202" (CAN)
        or "204" (AP) and when ROUTESTOMKTG = '110' (No) and CHANNELNAME = '1000' (Business Partners:  Consultants and Integrators) or
        '1100' (Business Partners:  Independent Software Vendor (ISV)) or '1200' (Business Partners:  Resellers) or
        ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNTCHANNEL (R) -> CHANNEL (E) -> CHGAA (A) -> GENERALAREA (E) when RFAGEO = ''200' (US) or "201" (LA)
         or "202" (CAN) or "204" (AP) and when ROUTESTOMKTG = '110' (No) and CHANNELNAME =  '1000' (Business Partners:  Consultants and Integrators) or
         '1100' (Business Partners:  Independent Software Vendor (ISV)) or '1200' (Business Partners:  Resellers) or
        ANNOUNCEMENT (E) -> ANNAVAIL (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURECHANNEL (R) -> CHANNEL (E) -> CHGAA (A) -> GENERALAREA (E) when RFAGEO = ''200' (US)
         or "201" (LA) or "202" (CAN) or "204" (AP) and when ROUTESTOMKTG = '110' (No) and CHANNELNAME =  '1000' (Business Partners:  Consultants and Integrators)
         or '1100' (Business Partners:  Independent Software Vendor (ISV)) or '1200' (Business Partners:  Resellers)
        tHIS IS FOR 15A
     */
    strFilterAttr = new String[] {
        "CHANNELNAME", "ROUTESTOMKTG"};
    strFilterValue = new String[] {
        "374", "110"};
    vReturnEntities2 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFCHANNEL");
    logMessage("14B****SOFCHANNEL*****");
    displayContents(vReturnEntities2);
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
    logMessage("14B****CHANNEL*****");
    displayContents(vReturnEntities3);
    strCondition1 = getAllGeoTags(vReturnEntities3);
    if (strCondition1.indexOf("US") > -1 || strCondition1.indexOf("LA") > -1 || strCondition1.indexOf("CAN") > -1) {
    }
    else {
      vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
    }
    if (vReturnEntities3.size() == 0) { //Check for channel in components
      vReturnEntities2 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTCHANNEL");
      logMessage("14B****CMPNTCHANNEL*****");
      displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
      logMessage("14B****CHANNEL*****");
      displayContents(vReturnEntities3);
      strCondition1 = getAllGeoTags(vReturnEntities3);
      if (strCondition1.indexOf("US") > -1 || strCondition1.indexOf("LA") > -1 || strCondition1.indexOf("CAN") > -1) {
      }
      else {
        vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
      }
    }
    if (vReturnEntities3.size() == 0) { //Check for channel in Features
      vReturnEntities2 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATURECHANNEL");
      logMessage("14B****FEATURECHANNEL*****");
      displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
      logMessage("14B****CHANNEL*****");
      displayContents(vReturnEntities3);
      strCondition1 = getAllGeoTags(vReturnEntities3);
      if (strCondition1.indexOf("US") > -1 || strCondition1.indexOf("LA") > -1 || strCondition1.indexOf("CAN") > -1) {
      }
      else {
        vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
      }
    }
    if (vReturnEntities3.size() > 0) {
      println(".*$P_14B = '1'");
    }
    else {
      println(".*$P_14B = '0'");
    }

    strFilterAttr = new String[] {
        "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME",
        "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME", "CHANNELNAME",
        "CHANNELNAME"};
    strFilterValue = new String[] {
        "375", "379", "380", "382", "383", "384", "385", "386", "387", "388", "1000", "1100",
        "1200"};
    vReturnEntities2 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFCHANNEL");
    logMessage("15A****SOFCHANNEL*****" + vReturnEntities2);
    displayContents(vReturnEntities2);
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
    displayContents(vReturnEntities3);
    logMessage("15A****CHANNEL from SOF*****" + vReturnEntities3 + "strFilterAttr: " + strFilterAttr +
               "strFilterValue: " + strFilterValue + "vReturnEntities3.size" + vReturnEntities3.size());

    if (vReturnEntities3.size() > 0) {
      strFilterAttr1 = new String[] {
          "ROUTESTOMKTG"};
      strFilterValue1 = new String[] {
          "110"};
      logMessage("15A****CHANNEL from SOF vReturnEntities3");
      displayContents(vReturnEntities3);
      vReturnEntities3 = searchEntityVectorLink1(vReturnEntities3, strFilterAttr1, strFilterValue1, true, "CHANNEL");
      logMessage("15A****CHANNEL from SOF checking for ROUTESTOMKTG*****" + vReturnEntities2 + "strFilterAttr1: " +
                 strFilterAttr1 + "strFilterValue1: " + strFilterValue1 + "vReturnEntities3.size: " +
                 vReturnEntities3.size());
      displayContents(vReturnEntities3);
    }
    else {
      vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
    }

    strCondition1 = getAllGeoTags(vReturnEntities3);
    if (strCondition1.indexOf("US") > -1 || strCondition1.indexOf("LA") > -1 || strCondition1.indexOf("CAN") > -1) {
    }
    else {
      vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
    }
    if (vReturnEntities3.size() == 0) { //Check for channel in components
      vReturnEntities2 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTCHANNEL");
      logMessage("15A****CMPNTCHANNEL*****" + vReturnEntities2);
      displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
      logMessage("15A****CHANNEL from CMPNT*****" + vReturnEntities3 + "strFilterAttr: " + strFilterAttr +
                 "strFilterValue: " + strFilterValue + "vReturnEntities3.size" + vReturnEntities3.size());
      displayContents(vReturnEntities3);

      if (vReturnEntities3.size() > 0) {
        strFilterAttr1 = new String[] {
            "ROUTESTOMKTG"};
        strFilterValue1 = new String[] {
            "110"};
        logMessage("15A****CHANNEL from CMPNT vReturnEntities3");
        displayContents(vReturnEntities3);
        vReturnEntities3 = searchEntityVectorLink1(vReturnEntities3, strFilterAttr1, strFilterValue1, true, "CHANNEL");
        logMessage("15A****CHANNEL from CMPNT checking for ROUTESTOMKTG*****" + vReturnEntities2 + "strFilterAttr1: " +
                   strFilterAttr1 + "strFilterValue1: " + strFilterValue1 + "vReturnEntities3.size: " +
                   vReturnEntities3.size());
        displayContents(vReturnEntities3);
      }
      else {
        vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
      }

      strCondition1 = getAllGeoTags(vReturnEntities3);
      if (strCondition1.indexOf("US") > -1 || strCondition1.indexOf("LA") > -1 || strCondition1.indexOf("CAN") > -1) {
      }
      else {
        vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
      }
    }
    if (vReturnEntities3.size() == 0) { //Check for channel in Features
      vReturnEntities2 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATURECHANNEL");
      logMessage("15A****FEATURECHANNEL*****" + vReturnEntities2);
      displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
      logMessage("15A****CHANNEL from FEATURE*****" + vReturnEntities3 + "strFilterAttr: " + strFilterAttr +
                 "strFilterValue: " + strFilterValue + "vReturnEntities3.size" + vReturnEntities3.size());
      displayContents(vReturnEntities3);

      if (vReturnEntities3.size() > 0) {
        strFilterAttr1 = new String[] {
            "ROUTESTOMKTG"};
        strFilterValue1 = new String[] {
            "110"};
        logMessage("15A****CHANNEL from SOF vReturnEntities3");
        displayContents(vReturnEntities3);
        vReturnEntities3 = searchEntityVectorLink1(vReturnEntities3, strFilterAttr1, strFilterValue1, true, "CHANNEL");
        logMessage("15A****CHANNEL from SOF checking for ROUTESTOMKTG*****" + vReturnEntities2 + "strFilterAttr1: " +
                   strFilterAttr1 + "strFilterValue1: " + strFilterValue1 + "vReturnEntities3.size" +
                   vReturnEntities3.size());
        displayContents(vReturnEntities3);
      }
      else {
        vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
      }

      strCondition1 = getAllGeoTags(vReturnEntities3);
      if (strCondition1.indexOf("US") > -1 || strCondition1.indexOf("LA") > -1 || strCondition1.indexOf("CAN") > -1) {
      }
      else {
        vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
      }
    }

    logMessage("15A****vReturnEntities3.size()*****: " + vReturnEntities3.size());
    if (vReturnEntities3.size() > 0) {
      println(".*$P_15A = '1'");
    }
    else {
      println(".*$P_15A = '0'");
    }
    /*
         strFilterAttr = new String[]{"CHANNELNAME"};
         strFilterValue = new String[]{"375"};
     */

    /*
        15B deleted 04/11/05
     */
    /*
        15C Deleted 04/11/05
     */
    /*
       strFilterAttr = new String[]{"CHANNELNAME"};
       strFilterValue = new String[]{"376"};
       vReturnEntities2 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFCHANNEL");
       logMessage("****SOFCHANNEL*****");
       displayContents(vReturnEntities2);
     vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
       logMessage("****CHANNEL*****");
       displayContents(vReturnEntities3);
       if (vReturnEntities3.size() == 0) {     //Check for channel in components
         vReturnEntities2 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTCHANNEL");
         logMessage("****CMPNTCHANNEL*****");
         displayContents(vReturnEntities2);
     vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
         logMessage("****CHANNEL*****");
         displayContents(vReturnEntities3);
       }
       if (vReturnEntities3.size() == 0) {     //Check for channel in Features
         vReturnEntities2 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATURECHANNEL");
         logMessage("****FEATURECHANNEL*****");
         displayContents(vReturnEntities1);
     vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
         logMessage("****CHANNEL*****");
         displayContents(vReturnEntities3);
       }
       if (vReturnEntities3.size() > 0) {
         println(".*$P_15D = '1'");
       } else {
         println(".*$P_15D = '0'");
       }

       strFilterAttr = new String[]{"CHANNELNAME"};
       strFilterValue = new String[]{"377"};
       vReturnEntities2 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFCHANNEL");
       logMessage("****SOFCHANNEL*****");
       displayContents(vReturnEntities2);
     vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
       logMessage("****CHANNEL*****");
       displayContents(vReturnEntities3);
       if (vReturnEntities3.size() == 0) {     //Check for channel in components
         vReturnEntities2 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTCHANNEL");
         logMessage("****CMPNTCHANNEL*****");
         displayContents(vReturnEntities2);
     vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
         logMessage("****CHANNEL*****");
         displayContents(vReturnEntities3);
       }
       if (vReturnEntities3.size() == 0) {     //Check for channel in Features
         vReturnEntities2 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATURECHANNEL");
         logMessage("****FEATURECHANNEL*****");
         displayContents(vReturnEntities1);
     vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true, "CHANNEL");
         logMessage("****CHANNEL*****");
         displayContents(vReturnEntities3);
       }
       if (vReturnEntities3.size() > 0) {
         println(".*$P_15E = '1'");
       } else {
         println(".*$P_15E = '0'");
       }
     */

    /*
        15F deleted 04/11/05
     */
    strFilterAttr = new String[] {
        "CHANNELNAME", "ROUTESTOMKTG"};
    strFilterValue = new String[] {
        "381", "110"};
    vReturnEntities2 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFCHANNEL");
    logMessage("15G****SOFCHANNEL*****");
    displayContents(vReturnEntities2);
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
    logMessage("15G****CHANNEL*****");
    displayContents(vReturnEntities3);
    strCondition1 = getAllGeoTags(vReturnEntities3);
    if (strCondition1.indexOf("US") > -1 || strCondition1.indexOf("LA") > -1 || strCondition1.indexOf("CAN") > -1) {
    }
    else {
      vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
    }
    if (vReturnEntities3.size() == 0) { //Check for channel in components
      vReturnEntities2 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTCHANNEL");
      logMessage("15G****CMPNTCHANNEL*****");
      displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
      logMessage("15G****CHANNEL*****");
      displayContents(vReturnEntities3);
      strCondition1 = getAllGeoTags(vReturnEntities3);
      if (strCondition1.indexOf("US") > -1 || strCondition1.indexOf("LA") > -1 || strCondition1.indexOf("CAN") > -1) {
      }
      else {
        vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
      }
    }
    if (vReturnEntities3.size() == 0) { //Check for channel in Features
      vReturnEntities2 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATURECHANNEL");
      logMessage("15G****FEATURECHANNEL*****");
      displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
      logMessage("15G****CHANNEL*****");
      displayContents(vReturnEntities3);
      strCondition1 = getAllGeoTags(vReturnEntities3);
      if (strCondition1.indexOf("US") > -1 || strCondition1.indexOf("LA") > -1 || strCondition1.indexOf("CAN") > -1) {
      }
      else {
        vReturnEntities3 = new Vector(); //disregard channel if it doesnt belong to US, LA, CAN geos
      }
    }
    if (vReturnEntities3.size() > 0) {
      println(".*$P_15G = '1'");
    }
    else {
      println(".*$P_15G = '0'");
    }

    strCondition1 = (grpAnnouncement != null ?
                     getAttributeFlagEnabledValue(eiAnnounce.getEntityType(), eiAnnounce.getEntityID(),
                                                  "GENAREANAMEINCL", "") : "");

    strCondition2 = "0";
    /*
         if 01 - Worldwide then set all to Yes
         if 02 - US then set 15G to Yes
         if 03 - Latin America then set 15K to Yes
         if 04 - Canada then set 15L to Yes
         if 05 - Europe then set 15H & 15I to Yes
         if 06 - Asia Pacific then set 15J to Yes
     */
    println(".*$P_15H = '" + (m_geList.isRfaGeoUS(eiAnnounce) ? "1" : "0") + "'");
    println(".*$P_15I = '" + (m_geList.isRfaGeoLA(eiAnnounce) ? "1" : "0") + "'");
    println(".*$P_15J = '" + (m_geList.isRfaGeoCAN(eiAnnounce) ? "1" : "0") + "'");
    println(".*$P_15K = '" + (m_geList.isRfaGeoEMEA(eiAnnounce) ? "1" : "0") + "'");
    println(".*$P_15L = '" + (m_geList.isRfaGeoAP(eiAnnounce) ? "1" : "0") + "'");

    //ANNOUNCEMENT (E) when COUNTRYLIST = '1438' (Aruba) or '1642' (Trinidad and Tobago) or '1629' (Suriname) or '1579' (Netherlands Antilles) or '1534' (Jamaica) or
    // '1513' (Grenada) or '1450' (Bermuda) or '1442' (Bahamas) or '1445' (Barbados)
    strCondition1 = getAttributeFlagEnabledValue(eiAnnounce, "COUNTRYLIST");
    st = new StringTokenizer(strCondition1, "|");
    bConditionOK = false;
    while (st.hasMoreTokens()) {
      strCondition2 = st.nextToken().trim(); //Take the spaces out
      logMessage("_15M:Got Country:" + i + ":" + strCondition2);
      if (strCondition2.equals("1438") || strCondition2.equals("1642") ||
          strCondition2.equals("1629") || strCondition2.equals("1579") ||
          strCondition2.equals("1534") || strCondition2.equals("1513") ||
          strCondition2.equals("1450") || strCondition2.equals("1442") ||
          strCondition2.equals("1445")) {
        bConditionOK = true;
        break;
      }
    }

    println(".*$P_15M = '" + (bConditionOK ? "1" : "0") + "'");

    println(".*$P_16A = '0'"); //Changed for V20.05
    println(".*$P_16B = '0'"); //Changed for V20.05

    strCondition1 = getAttributeValue(eiAnnounce, "CROSSPLATFORM", "");
    println(".*$P_17A = '" + (strCondition1.indexOf("Yes") > -1 ? "1" : "0") + "'");

    println(".*$P_17B = '" + (flagvalueEquals(eiAnnounce, "PLATFORM", "4767") ? "1" : "0") + "'");
    println(".*$P_17C = '" + (flagvalueEquals(eiAnnounce, "PLATFORM", "4770") ? "1" : "0") + "'");
    println(".*$P_17D = '" + (flagvalueEquals(eiAnnounce, "PLATFORM", "4769") ? "1" : "0") + "'");
    println(".*$P_17E = '" + (flagvalueEquals(eiAnnounce, "PLATFORM", "4772") ? "1" : "0") + "'");
    println(".*$P_17F = '" + (flagvalueEquals(eiAnnounce, "PLATFORM", "4764") ? "1" : "0") + "'");
    println(".*$P_17G = '" + (flagvalueEquals(eiAnnounce, "PLATFORM", "4768") ? "1" : "0") + "'");
    println(".*$P_17H = '" + (flagvalueEquals(eiAnnounce, "PLATFORM", "4766") ? "1" : "0") + "'");
    println(".*$P_17I = '" + (flagvalueEquals(eiAnnounce, "PLATFORM", "4773") ? "1" : "0") + "'");

    println(".*$P_17J = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES", "2907") ? "1" : "0") + "'");
//    println(".*$P_17K = '" + (flagvalueEquals(eiAnnounce, "OFFERINGTYPES", "2913") ? "1" : "0") + "'");

    println(".*$P_17L = '" + (flagvalueEquals(eiAnnounce, "ELECTRONICSERVICE", "010") ? "1" : "0") + "'");
    println(".*$P_17M = '" + (flagvalueEquals(eiAnnounce, "ELECTRONICSERVICE", "011") ? "1" : "0") + "'");

    println(".*$P_18A = '" + (flagvalueEquals(eiAnnounce, "CONFIGSUPPORT", "677") ? "1" : "0") + "'");
    println(".*$P_18B = '" + (flagvalueEquals(eiAnnounce, "CONFIGSUPPORT", "675") ? "1" : "0") + "'");

    /*
     *  Check for US configurator
     */
    bConditionOK = false;
    vReturnEntities2 = new Vector();
    for (i = 0; i < grpAnnToConfig.getEntityItemCount(); i++) {
      eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
      eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
      if (m_geList.isRfaGeoUS(eiConfigurator)) {
        logMessage("P_19A US Configurator" + eiConfigurator.getEntityType() + ":" + eiConfigurator.getEntityID());
        bConditionOK = true;
        vReturnEntities2.addElement(eiConfigurator); //Store all instances of the configurator
      }
    }

    //'ANNOUNCEMENT (E) ->ANNTOCONFIG (R) -> CONFIGURATOR (E) -> CONFIGGAA (A) -> GENERALAREA (E) when RFAGEO = '200' (US)
    eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(0) : null);

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_19A = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_19B = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 1) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(1) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_19C = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_19D = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 2) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_19E = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_19F = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 3) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_19G= '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_19H = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 4) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_19I= '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_19J = '" + strCondition2 + "'");

    /*
     *  Check for AP configurator
     */
    bConditionOK = false;
    vReturnEntities2 = new Vector();
    for (i = 0; i < grpAnnToConfig.getEntityItemCount(); i++) {
      eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
      eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
      if (m_geList.isRfaGeoAP(eiConfigurator)) {
        logMessage("P_20A ap Configurator" + eiConfigurator.getEntityType() + ":" + eiConfigurator.getEntityID());
        bConditionOK = true;
        vReturnEntities2.addElement(eiConfigurator); //Store all instances of the configurator
      }
    }

    //'ANNOUNCEMENT (E) ->ANNTOCONFIG (R) -> CONFIGURATOR (E) -> CONFIGGAA (A) -> GENERALAREA (E) when RFAGEO = '200' (US)
    eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(0) : null);

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_20A = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_20B = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 1) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(1) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_20C = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_20D = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 2) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_20E = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_20F = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 3) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_20G= '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_20H = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 4) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_20I= '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_20J = '" + strCondition2 + "'");

    /*
     *  Check for LA configurator
     */
    bConditionOK = false;
    vReturnEntities2 = new Vector();
    for (i = 0; i < grpAnnToConfig.getEntityItemCount(); i++) {
      eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
      eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
      if (m_geList.isRfaGeoLA(eiConfigurator)) {
        logMessage("P_21A LA Configurator" + eiConfigurator.getEntityType() + ":" + eiConfigurator.getEntityID());
        bConditionOK = true;
        vReturnEntities2.addElement(eiConfigurator); //Store all instances of the configurator
      }
    }

    //'ANNOUNCEMENT (E) ->ANNTOCONFIG (R) -> CONFIGURATOR (E) -> CONFIGGAA (A) -> GENERALAREA (E) when RFAGEO = '200' (US)
    eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(0) : null);

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_21A = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_21B = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 1) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(1) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_21C = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_21D = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 2) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_21E = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_21F = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 3) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_21G= '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_21H = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 4) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_21I= '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_21J = '" + strCondition2 + "'");

    /*
     *  Check for CAN configurator
     */
    bConditionOK = false;
    vReturnEntities2 = new Vector();
    for (i = 0; i < grpAnnToConfig.getEntityItemCount(); i++) {
      eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
      eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
      if (m_geList.isRfaGeoCAN(eiConfigurator)) {
        logMessage("P_22a CANConfigurator" + eiConfigurator.getEntityType() + ":" + eiConfigurator.getEntityID());
        bConditionOK = true;
        vReturnEntities2.addElement(eiConfigurator); //Store all instances of the configurator
      }
    }

    //'ANNOUNCEMENT (E) ->ANNTOCONFIG (R) -> CONFIGURATOR (E) -> CONFIGGAA (A) -> GENERALAREA (E) when RFAGEO = '200' (US)
    eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(0) : null);

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_22A = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_22B = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 1) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(1) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_22C = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_22D = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 2) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_22E = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_22F = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 3) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_22G= '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_22H = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 4) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_22I= '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_22J = '" + strCondition2 + "'");

    /*
     *  Check for EMEA configurator
     */
    bConditionOK = false;
    vReturnEntities2 = new Vector();
    for (i = 0; i < grpAnnToConfig.getEntityItemCount(); i++) {
      eiAnnToConfig = grpAnnToConfig.getEntityItem(i);
      eiConfigurator = (EntityItem) eiAnnToConfig.getDownLink(0);
      if (m_geList.isRfaGeoEMEA(eiConfigurator)) {
        logMessage("P_23A EMEA Configurator" + eiConfigurator.getEntityType() + ":" + eiConfigurator.getEntityID());
        bConditionOK = true;
        vReturnEntities2.addElement(eiConfigurator); //Store all instances of the configurator
      }
    }

    //'ANNOUNCEMENT (E) ->ANNTOCONFIG (R) -> CONFIGURATOR (E) -> CONFIGGAA (A) -> GENERALAREA (E) when RFAGEO = '200' (US)
    eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(0) : null);

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_23A = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_23B = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 1) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(1) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_23C = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_23D = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 2) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_23E = '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_23F = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 3) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_23G= '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_23H = '" + strCondition2 + "'");

    if (vReturnEntities2.size() > 4) {
      eiConfigurator = (bConditionOK ? (EntityItem) vReturnEntities2.elementAt(2) : null);
    }
    else {
      bConditionOK = false;
    }

    strCondition2 = (bConditionOK ? getAttributeShortFlagDesc(eiConfigurator, "CONFIGNAME", "") : "");
    println(".*$P_23I= '" + strCondition2 + "'");
    strCondition2 = (bConditionOK ? getAttributeValue(eiAnnToConfig, "CONFIGAVAILDATE", "") : "");
    println(".*$P_23J = '" + strCondition2 + "'");

    strCondition1 = getAttributeValue(eiAnnounce, "OFFERINGACCESS", "");
    println(".*$P_30A = '" + (strCondition1.equals("Yes") ? "1" : "0") + "'");
    strCondition1 = getAttributeValue(eiAnnounce, "MARKETEDIBMLOGO", "");
    println(".*$P_30B = '" + (strCondition1.equals("Yes") ? "1" : "0") + "'");
    println(".*$P_30C = '" + (flagvalueEquals(eiAnnounce, "LOGOACCESSREQTS", "2833") ? "1" : "0") + "'");
    println(".*$P_30D = '" + (flagvalueEquals(eiAnnounce, "LOGOACCESSREQTS", "2834") ? "1" : "0") + "'");
    strCondition1 = getAttributeShortFlagDesc(eiAnnounce, "TGTCUSTOMERAUD", "");

    strFilterAttr = new String[] {
        "CHANNELNAME", "ROUTESTOMKTG"};
    strFilterValue = new String[] {
        "381", "110"};
    vReturnEntities2 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFCHANNEL");
    logMessage("****SOFCHANNEL*****");
    displayContents(vReturnEntities2);
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
    logMessage("****CHANNEL*****");
    displayContents(vReturnEntities3);
    if (vReturnEntities3.size() == 0) { //Check for channel in components
      vReturnEntities2 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTCHANNEL");
      logMessage("****CMPNTCHANNEL*****");
      displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
      logMessage("****CHANNEL*****");
      displayContents(vReturnEntities3);
    }
    if (vReturnEntities3.size() == 0) { //Check for channel in Features
      vReturnEntities2 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATURECHANNEL");
      logMessage("****FEATURECHANNEL*****");
      displayContents(vReturnEntities1);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
      logMessage("****CHANNEL*****");
      displayContents(vReturnEntities3);
    }
    println(".*$P_31A = '" + (vReturnEntities3.size() > 0 ? "1" : "0") + "'");

    println(".*$P_43A = '" + (strCondition1.length() > 61 ? strCondition1.substring(0, 61) : strCondition1) + "'");
    println(".*$VARIABLES_END");
  }

  /**
   *  Description of the Method
   */
  private void processLongTo100() {
    println(".*$A_007_Begin");
    println(getAttributeValue(eiAnnounce, "ANNIMAGES", " "));
    println(".*$A_007_End");
    println(".*$A_040_Begin");
    /*     println(":xmp.");
        println(".kp off");
     */
    strHeader = new String[] {
        "Role Type", "Name", "Telephone", "Node/ID"};
    iColWidths = new int[] {
        15, 18, 12, 17};
    strFilterAttr = new String[] {
        "ANNROLETYPE", "ANNROLETYPE", "ANNROLETYPE", "ANNROLETYPE"};
    strFilterValue = new String[] {
        "11", "12", "13", "14"};
    vReturnEntities1 = searchEntityGroup(grpAnnToOP, strFilterAttr, strFilterValue, false);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiAnnToOP = (EntityItem) vReturnEntities1.elementAt(i);
      eiOP = (EntityItem) eiAnnToOP.getDownLink(0);
      strCondition1 = getAttributeValue(eiAnnToOP, "ANNROLETYPE", " ");
      vPrintDetails.add(strCondition1.length() >= 14 ? strCondition1.substring(0, 14) :
                        strCondition1.substring(0, strCondition1.length()));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ") + "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }
    if (vPrintDetails.size() > 0) {
      println("This RFA and its requested schedule hae been reviewed with the");
      println("following functional representatives");
      println(":xmp.");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println(":exmp.");
    }
    println(".*$A_040_End");

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
        vPrintDetails.add(getAttributeValue(eiNextItem, "ANNTITLE", " "));
        vPrintDetails.add(getAttributeValue(eiNextItem, "ANNNUMBER", " "));
      }
    }
    strHeader = new String[] {
        "Announcement Title", "Announcement Number"};
    iColWidths = new int[] {
        50, 19};
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println("");

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
    /*
         'ANNOUNCEMENT( E) -> ANNAVAILA (A) -> SOFAVAIL (R) -> SOF (E) -> SOFADMINOP (R) when RFAGEO = '200' (US) ->
              OP (E)
     */
    vReturnEntities4 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFADMINOP");
    logMessage("****SOFADMINOP*****");
    displayContents(vReturnEntities4);
    //Get the entities which belong to US geo
    vReturnEntities2.removeAllElements();
    vReturnEntities2.addAll(vReturnEntities4);
    vReturnEntities3 = searchInGeo(vReturnEntities2, "US");
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "OP");
    logMessage("****OP*****");
    displayContents(vReturnEntities1);

    resetPrintvars();
    strEntityTypes = new String[] {
        "SOFADMINOP"};
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOP = (EntityItem) vReturnEntities1.elementAt(i);
      logMessage("_045 US" + eiOP.getKey());
      eiNextItem = getUplinkedEntityItem(eiOP, strEntityTypes);
      strCondition1 = getGeoTags(eiNextItem);
      logMessage("_045 US" + eiOP.getKey() + strCondition1);
      if (strCondition1.equals(strWorldwideTag)) { //Ignore Worldwide tags
        continue;
      }

      logMessage("_045 US" + eiNextItem.getKey());
      vPrintDetails.add(getAttributeValue(eiNextItem, "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ") + "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

    //Get the entities which belong to EMEA geo
    vReturnEntities2.removeAllElements();
    vReturnEntities2.addAll(vReturnEntities4);
    vReturnEntities3 = searchInGeo(vReturnEntities2, "EMEA");
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "OP");
    logMessage("****OP*****");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOP = (EntityItem) vReturnEntities1.elementAt(i);
      eiNextItem = getUplinkedEntityItem(eiOP, strEntityTypes);
      strCondition1 = getGeoTags(eiNextItem);
      logMessage("_045 EMEA" + eiOP.getKey() + strCondition1);
      if (strCondition1.equals(strWorldwideTag)) { //Ignore Worldwide tags
        continue;
      }
      logMessage("_045 EMEA" + eiNextItem.getKey());
      vPrintDetails.add(getAttributeValue(eiNextItem, "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ") + "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

    //Get the entities which belong to AP geo
    vReturnEntities2.removeAllElements();
    vReturnEntities2.addAll(vReturnEntities4);
    vReturnEntities3 = searchInGeo(vReturnEntities2, "AP");
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "OP");
    logMessage("****OP*****");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOP = (EntityItem) vReturnEntities1.elementAt(i);
      eiNextItem = getUplinkedEntityItem(eiOP, strEntityTypes);
      strCondition1 = getGeoTags(eiNextItem);
      logMessage("_045 AP" + eiOP.getKey() + strCondition1);
      if (strCondition1.equals(strWorldwideTag)) { //Ignore Worldwide tags
        continue;
      }
      logMessage("_045 AP" + eiNextItem.getKey());
      vPrintDetails.add(getAttributeValue(eiNextItem, "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ") + "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

    //Get the entities which belong to LA geo
    vReturnEntities2.removeAllElements();
    vReturnEntities2.addAll(vReturnEntities4);
    vReturnEntities3 = searchInGeo(vReturnEntities2, "LA");
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "OP");
    logMessage("****OP*****");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOP = (EntityItem) vReturnEntities1.elementAt(i);
      eiNextItem = getUplinkedEntityItem(eiOP, strEntityTypes);
      strCondition1 = getGeoTags(eiNextItem);
      logMessage("_045 LA" + eiOP.getKey() + strCondition1);
      if (strCondition1.equals(strWorldwideTag)) { //Ignore Worldwide tags
        continue;
      }
      logMessage("_045 LA" + eiNextItem.getKey());
      vPrintDetails.add(getAttributeValue(eiNextItem, "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ") + "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

    //Get the entities which belong to CA geo
    vReturnEntities2.removeAllElements();
    vReturnEntities2.addAll(vReturnEntities4);
    vReturnEntities3 = searchInGeo(vReturnEntities2, "CA");
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "OP");
    logMessage("****OP*****");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiOP = (EntityItem) vReturnEntities1.elementAt(i);
      eiNextItem = getUplinkedEntityItem(eiOP, strEntityTypes);
      strCondition1 = getGeoTags(eiNextItem);
      logMessage("_045 CA" + eiOP.getKey());
      if (strCondition1.equals(strWorldwideTag)) { //Ignore Worldwide tags
        continue;
      }
      logMessage("_045 CA" + eiNextItem.getKey());
      vPrintDetails.add(getAttributeValue(eiNextItem, "GENAREASELECTION", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0, 1) + ". ";
      strCondition1 += getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ") + "/";
      strCondition1 += getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

    strHeader = new String[] {
        "Geography", "Product Administrator", " Telephone", "    Node/Userid"};
    iColWidths = new int[] {
        10, 21, 12, 17};
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();

    println(":exmp.");
    println(".*$A_045_End");

    println(".*$A_046_Begin");
    println(getAttributeShortFlagDesc(eiAnnounce, "TELECOMMEQ", " "));
    println(".*$A_046_End");

    /*
     *  Print OP details when ANNROLETYPE = 'Product Development Mgr' on relator ANNOP
     */
    println(".*$A_049_Begin");
    println(":xmp.");
    println(".kp off");
    eiAnnToOP = null;
    eiOP = null;
    strFilterAttr = new String[] {
        "ANNROLETYPE"};
    strFilterValue = new String[] {
        "5"}; //"Releasing Executive"
    vReturnEntities1 = searchEntityGroup(grpAnnToOP, strFilterAttr, strFilterValue, true);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "OP");
    strFilterAttr = new String[] {
        "ORGANUNITTYPE", "ORGANUNITTYPE"};
    strFilterValue = new String[] {
        "4156", "4157"};
    vReturnEntities3 = searchEntityGroupLink(grpAnnToOrgUnit, strFilterAttr, strFilterValue, false, true, "ORGANUNIT");

    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiOP = (EntityItem) vReturnEntities2.elementAt(i);
      strCondition2 = getAttributeValue(eiOP, "FIRSTNAME", " ");
      strCondition1 = getAttributeValue(eiOP, "MIDDLENAME", " ");
      strCondition2 += strCondition1.equals(" ") ? "" : " " + strCondition1 + ".";
      strCondition1 = getAttributeValue(eiOP, "LASTNAME", " ");
      strCondition2 += strCondition1.equals(" ") ? "" : " " + strCondition1;
      println("                     " + strCondition2);
      println("                     " + getAttributeValue(eiOP, "JOBTITLE", " "));
      for (j = 0; j < vReturnEntities3.size(); j++) {
        eiOrganUnit = (EntityItem) vReturnEntities3.elementAt(j);
        println("                     " + getAttributeValue(eiOrganUnit, "NAME", " "));
      }

    }
    /*     strParamList1 = new String[]{"FIRSTNAME","MIDDLENAME","LASTNAME", "JOBTITLE"};
         printValueListInVector(vReturnEntities2, strParamList1, " ", false, false);
         iColWidths = new int[]{10,10,10, 15};
         printReport(false, null, iColWidths, vPrintDetails);
         resetPrintvars();
     */
    println(":exmp.");
    println(".*$A_049_End");

    println(".*$A_100_Begin");
    prettyPrint(getAttributeValue(eiAnnounce, "ANNTITLE", " "), 69);
    println(".*$A_100_End");

  }

  /**
   *  Description of the Method
   */
  private void processLongTo200() {
    /*     println(".*$A_102_Begin");
        prettyPrint(getAttributeValue(eiAnnounce, "ONESENTDESC", " "), 69);
        println(".*$A_102_End");
     */
    /*TIR 6QKKNN*/
    println(".*$A_104_Begin");
    printVanillaSVSReport("FEATUREBENEFIT", true, false);
    println(".*$A_104_End");
    /*TIR 6QKKNN */
    /*TIR 6QKKNN put this before 108 because VM renames 105 to 108 */
    println(".*$A_106_Begin");
    printVanillaSVSReport("DIFFEATURESBENEFITS", true, false);
    println(".*$A_106_End");
    /*TIR 6QKKNN */
    eiChannel = null;
    strFilterAttr = new String[] {
        "AVAILTYPE"};
    strFilterValue = new String[] {
        "146"}; //Consider AVAILS OF type Planned Availability only 10/21  changed again 12/30
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, strFilterAttr, strFilterValue, true, true, "AVAIL");
    logMessage("_108****AVAIL*****");
    displayContents(vReturnEntities1);
    //Get the entities which belong to US/CAN/LA/AP geo
    vReturnEntities2.removeAllElements();
    vReturnEntities2 = searchInGeo(vReturnEntities1, new String[] {"US", "CAN", "LA", "AP"});

    logMessage("_108****AVAIL-US/CAN/LA/AP*****");
    displayContents(vReturnEntities2);
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "SOFAVAIL");
    logMessage("****SOFAVAIL*****");
    displayContents(vReturnEntities3);
    vReturnEntities5 = searchEntityVectorLink(vReturnEntities3, null, null, true, false, "SOF");
    logMessage("_108****SOF*****");
    displayContents(vReturnEntities5);

    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "CMPNTAVAIL");
    logMessage("****CMPNTAVAIL*****");
    displayContents(vReturnEntities3);
    vReturnEntities4 = searchEntityVectorLink(vReturnEntities3, null, null, true, false, "CMPNT");
    logMessage("_108****CMPNT*****");
    displayContents(vReturnEntities4);
    vReturnEntities5.addAll(vReturnEntities4);

    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "FEATUREAVAIL");
    logMessage("****FEATUREAVAIL*****");
    displayContents(vReturnEntities3);
    vReturnEntities4 = searchEntityVectorLink(vReturnEntities3, null, null, true, false, "FEATURE");
    logMessage("_108****FEATURE*****");
    displayContents(vReturnEntities4);

    vReturnEntities5.addAll(vReturnEntities4);
    logMessage("_108****SOFCMPNTFEATURE*****");
    displayContents(vReturnEntities5);

    vReturnEntities4 = sortEntities(vReturnEntities5, new String[] {"MKTGNAME"});
    bConditionOK = false;
    bConditionOK1 = false;
    for (i = 0; i < vReturnEntities4.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities4.elementAt(i);
      logMessage("_108****SOFCMPNTFEATURE*****" + eiNextItem.getKey());
      strCondition2 = eiNextItem.getEntityType();
      if (strCondition2.equals("SOF")) {
        vReturnEntities3 = searchEntityItemLink(eiNextItem, null, null, true, true, "SOFCHANNEL");
        strCondition3 = getSOFMktName(eiNextItem);
      }
      else if (strCondition2.equals("CMPNT")) {
        vReturnEntities3 = searchEntityItemLink(eiNextItem, null, null, true, true, "CMPNTCHANNEL");
        strCondition3 = getCmptToSofMktMsg(eiNextItem);

      }
      else if (strCondition2.equals("FEATURE")) {
        vReturnEntities3 = searchEntityItemLink(eiNextItem, null, null, true, true, "FEATURECHANNEL");
        strCondition3 = getfeatureToSofMktMsg(eiNextItem);

      }

      for (int j = 0; j < vReturnEntities3.size(); j++) {
        eiNextItem1 = (EntityItem) vReturnEntities3.elementAt(j);
        eiChannel = (EntityItem) eiNextItem1.getDownLink(0);
        if (!flagvalueEquals(eiChannel, "ROUTESTOMKTG", "110")) { //Process only channels with the ROUTESTOMKTG
          continue;
        }

        strCondition1 = getGeoTags(eiChannel);

        logMessage("A_108:" + eiNextItem.getKey() + ":" + eiChannel.getKey() + strCondition1);
        if (!strCondition1.equals(strWorldwideTag) &&
            (m_geList.isRfaGeoUS(eiChannel) || m_geList.isRfaGeoAP(eiChannel) || m_geList.isRfaGeoCAN(eiChannel) ||
             m_geList.isRfaGeoLA(eiChannel))) {
          if (!bConditionOK) { //Print question tags only if there is data to print
            println(".*$A_108_Begin");
            bConditionOK = true; //Reset this so that it doesnt print again
            bConditionOK1 = true; //Set indicator for End tag
          }
          println("");
          prettyPrint(strCondition3, 69);
          println("");
          strCondition1 = getAttributeValue(eiChannel, "CHANNELNAME", " ");
          if (strCondition1.indexOf("*") > -1) {
            println(":ul compact.");
            st = new StringTokenizer(strCondition1, "*");
            while (st.hasMoreTokens()) {
              println(":li." + st.nextToken().trim());
            }
            println(":eul.");
          }
        }
        else { //remove this after debugging
          logMessage("_108:Bypassing " + eiChannel.getKey() + ":Tag is :" + strCondition1);
        }
      } //vReturnEntities3

    } //vReturnEntities5
    if (bConditionOK1) {
      println(".*$A_108_End");
    }

// _106 DELETED 04/11/05

    /*
         'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R)  -> SOF (E) -> SOFCHANNEL (R) -> CHANNEL (E)
         -> CHGAA (A) -> GENERALAREA (E) when RFAGEO = ''203' (EMEA)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFCHANNEL (R) -> CHANNEL (E) when ROUTESTOMKTG = '110' (No)

         Changes as on 6/1/04
         RFA question 110 - should ALWAYS print the complete list of EMEA channels if an EMEA availability record exist

        To be able to do this the floowing logic is being change:   If an AVAIL (E) EXIST with RFAGEO = EMEA (203) and that CHANNEL
                                    DOES NOT exist with RFAGEO = EMEA (203)  and ROUTESTOMKGT = NO,
                                    then RFA question is left BLANK in the generated extract from e-announce

                                    ie:     .*$A_110_Begin
                                        .*$A_110_End

                                    If an AVAIL (E) EXIST with RFAGEO = EMEA (203) and that CHANNEL
                                    EXIST with RFAGEO = EMEA (203)  and ROUTESTOMKGT = NO,
                                    then RFA question 110 is outputed with the CHANNELNAME in the generated
                                    extract from e-announce

                                    ie:     .*$A_110_Begin
                                        IBM Technology Assessment and Consulting Services for DB2 Healthcheck
                                    IBM sales specialists
                                    IBM Business Partner - Solution Provider
                                    IBM Business Partner - Reseller
                                        .*$A_110_End
      ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> AVAILGAA (A) -> GENERALAREA (E) -> when RFAGEO = "203"S (EMEA) -> OFAVAIL (R) ->
        SOF (E) -> SOFCHANNEL (R) -> CHANNEL (E) when ROUTESTOMKTG = '110' (No) and When ANNOUNCEMENT (ANNCODENAMEU) = AVAIL (ANNCODENAME)

     */
    eiChannel = null;
    strFilterAttr = new String[] {
        "AVAILTYPE"};
    strFilterValue = new String[] {
        "146"}; //Consider AVAILS OF type Planned Availability only 10/21  changed again 12/30
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, strFilterAttr, strFilterValue, true, true, "AVAIL");
    logMessage("_110****AVAIL*****");
    displayContents(vReturnEntities1);
    //Get the entities which belong to EMEA geo
    vReturnEntities2.removeAllElements();
    vReturnEntities2 = searchInGeo(vReturnEntities1, "EMEA");
    logMessage("_110****AVAIL-EMEA*****");
    displayContents(vReturnEntities2);
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "SOFAVAIL");
    logMessage("****SOFAVAIL*****");
    displayContents(vReturnEntities3);
    vReturnEntities5 = searchEntityVectorLink(vReturnEntities3, null, null, true, false, "SOF");
    logMessage("_110****SOF*****");
    displayContents(vReturnEntities5);

    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "CMPNTAVAIL");
    logMessage("****CMPNTAVAIL*****");
    displayContents(vReturnEntities3);
    vReturnEntities4 = searchEntityVectorLink(vReturnEntities3, null, null, true, false, "CMPNT");
    logMessage("_110****CMPNT*****");
    displayContents(vReturnEntities4);
    vReturnEntities5.addAll(vReturnEntities4);

    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "FEATUREAVAIL");
    logMessage("****FEATUREAVAIL*****");
    displayContents(vReturnEntities3);
    vReturnEntities4 = searchEntityVectorLink(vReturnEntities3, null, null, true, false, "FEATURE");
    logMessage("_110****FEATURE*****");
    displayContents(vReturnEntities4);

    vReturnEntities5.addAll(vReturnEntities4);
    logMessage("_110****SOFCMPNTFEATURE*****");
    displayContents(vReturnEntities5);

    vReturnEntities4 = sortEntities(vReturnEntities5, new String[] {"MKTGNAME"});
    bConditionOK = false;
    bConditionOK1 = false;
    for (i = 0; i < vReturnEntities4.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities4.elementAt(i);
      logMessage("_110****SOFCMPNTFEATURE*****" + eiNextItem.getKey());
      strCondition2 = eiNextItem.getEntityType();
      if (strCondition2.equals("SOF")) {
        vReturnEntities3 = searchEntityItemLink(eiNextItem, null, null, true, true, "SOFCHANNEL");
        strCondition3 = getSOFMktName(eiNextItem);
      }
      else if (strCondition2.equals("CMPNT")) {
        vReturnEntities3 = searchEntityItemLink(eiNextItem, null, null, true, true, "CMPNTCHANNEL");
        strCondition3 = getCmptToSofMktMsg(eiNextItem);

      }
      else if (strCondition2.equals("FEATURE")) {
        vReturnEntities3 = searchEntityItemLink(eiNextItem, null, null, true, true, "FEATURECHANNEL");
        strCondition3 = getfeatureToSofMktMsg(eiNextItem);

      }

      for (int j = 0; j < vReturnEntities3.size(); j++) {
        eiNextItem1 = (EntityItem) vReturnEntities3.elementAt(j);
        eiChannel = (EntityItem) eiNextItem1.getDownLink(0);
        if (!flagvalueEquals(eiChannel, "ROUTESTOMKTG", "110")) { //Process only channels with the ROUTESTOMKTG
          continue;
        }

        strCondition1 = getGeoTags(eiChannel);
        logMessage("A_110:" + eiNextItem.getKey() + ":" + eiChannel.getKey() + strCondition1);
        if (!strCondition1.equals(strWorldwideTag) && m_geList.isRfaGeoEMEA(eiChannel)) {
          if (!bConditionOK) { //Print question tags only if there is data to print
            println(".*$A_110_Begin");
            bConditionOK = true; //Reset this so that it doesnt print again
            bConditionOK1 = true; //Set indicator for End tag
          }
          println("");
          prettyPrint(strCondition3, 69);
          println("");
          strCondition1 = getAttributeValue(eiChannel, "CHANNELNAME", " ");
          if (strCondition1.indexOf("*") > -1) {
            println(":ul compact.");
            st = new StringTokenizer(strCondition1, "*");
            while (st.hasMoreTokens()) {
              println(":li." + st.nextToken().trim());
            }
            println(":eul.");
          }
        }
        else { //remove this after debugging
          logMessage("_110:Tag is :" + strCondition1);
          logMessage("_110:EMEA is " + m_geList.isRfaGeoEMEA(eiChannel));
        }
      } //vReturnEntities3

    } //vReturnEntities5
    if (bConditionOK1) {
      println(".*$A_110_End");
    }

    /* The old one starts here */


    println(".*$A_116_Begin");
    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFCHANNEL (R) -> CHANNEL (E)
         Changes as on 6/1/04
     RFA question 116 - will only be outputed in the extract, if, CHANNEL (E) EXISTS and ROUTESTOMKGT = "Yes"

                                    ie: .*$A_110_Begin
                                        IBM Technology Assessment and Consulting Services for DB2 Healthcheck

                                        Route Description                                WW AP CAN US EMEA LA
                                            ---------------------------------------------    -- -- --- -- ---- --
     * SS/1 sales Specialist                                    X  X
     * SP/2 Solution Provider                                   X
                                        .*$A_110_End
      ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFCHANNEL (R) -> CHANNEL (E) when ROUTESTOMKTG = '100' (Yes)

     */
    //Store the mktname for the heading based on the number of sofs
    resetPrintvars();
    println(":xmp.");
    println(".kp off");
    iTemp = vSofFrmSofAvail.size();
    eiSof = vSofFrmSofAvail.size() > 0 ? (EntityItem) vSofFrmSofAvail.elementAt(0) : null;
    strCondition1 = (eiSof != null ? getSOFMktName(eiSof) : " ");
    logMessage("****SOF*****");
    displayContents(vReturnEntities1);

    vReturnEntities2 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFCHANNEL");
    logMessage("****SOFCHANNEL*****");
    displayContents(vReturnEntities2);

    strFilterAttr = new String[] {
        "ROUTESTOMKTG"};
    strFilterValue = new String[] {
        "100"};

    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
    logMessage("****CHANNEL*****");
    displayContents(vReturnEntities1);
    bConditionOK = false;
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiChannel = (EntityItem) vReturnEntities1.elementAt(i);
      logMessage("_116" + eiChannel.getKey() + ":" + getGeoTags(eiChannel));
      vPrintDetails.add(getAttributeValue(eiChannel, "CHANNELNAME", " ") + " ");
      bConditionOK = (m_geList.isRfaGeoAP(eiChannel) && m_geList.isRfaGeoCAN(eiChannel) &&
                      m_geList.isRfaGeoUS(eiChannel) && m_geList.isRfaGeoEMEA(eiChannel) &&
                      m_geList.isRfaGeoLA(eiChannel));
      vPrintDetails.add(bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoAP(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoCAN(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoUS(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoEMEA(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoLA(eiChannel) && !bConditionOK ? "X" : " ");
    }
    if (vPrintDetails.size() > 0) {
      strHeader = new String[] {
          "Route Description", "WW", "AP", "CAN", "US", "EMEA", "LA"};
      iColWidths = new int[] {
          26, 2, 2, 3, 2, 4, 2};
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "SOFCHANNEL");
      for (i = 0; i < vReturnEntities2.size(); i++) {
        eiNextItem = (EntityItem) vReturnEntities2.elementAt(i);
        logMessage("_116 :Finally:" + eiNextItem.getKey());
        eiSof = (EntityItem) eiNextItem.getUpLink(0);
        logMessage("_116 :Finally:" + eiSof.getKey());
        strCondition1 = getSOFMktName(eiSof);
        println(strCondition1);
      }
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println("");
    }
    /*
     ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTCHANNEL (R) -> CHANNEL (E)
     */
    //Store the mktname for the heading based on the number of sofs
    iTemp = vCmpntFrmCmpntAvail.size();
    eiComponent = vCmpntFrmCmpntAvail.size() > 0 ? (EntityItem) vCmpntFrmCmpntAvail.elementAt(0) : null;
    strCondition1 = vCmpntFrmCmpntAvail.size() > 0 ? getCmptToSofMktMsg(eiComponent) : " ";

    vReturnEntities2 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTCHANNEL");
    logMessage("****_116 CMPNTCHANNEL*****");
    displayContents(vReturnEntities2);
    strFilterAttr = new String[] {
        "ROUTESTOMKTG"};
    strFilterValue = new String[] {
        "100"};

    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
    logMessage("****_116 CHANNEL*****");
    displayContents(vReturnEntities1);

    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiChannel = (EntityItem) vReturnEntities1.elementAt(i);
      logMessage("_116 from CMPNT" + eiChannel.getKey());
      vPrintDetails.add(getAttributeValue(eiChannel, "CHANNELNAME", " ") + " ");
      strCondition1 = getGeoTags(eiChannel);
      logMessage("_116" + eiChannel.getKey() + ":" + getGeoTags(eiChannel));

      bConditionOK = strCondition1.equals(strWorldwideTag);

      vPrintDetails.add(bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoAP(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoCAN(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoUS(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoEMEA(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoLA(eiChannel) && !bConditionOK ? "X" : " ");
    }
    strHeader = new String[] {
        "Route Description", "WW", "AP", "CAN", "US", "EMEA", "LA"};
    iColWidths = new int[] {
        26, 2, 2, 3, 2, 4, 2};
    if (vReturnEntities1.size() > 0) {
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "CMPNTCHANNEL");
      for (i = 0; i < vReturnEntities2.size(); i++) {
        eiNextItem = (EntityItem) vReturnEntities2.elementAt(i);
        logMessage("_116 :Finally:" + eiNextItem.getKey());
        eiComponent = (EntityItem) eiNextItem.getUpLink(0);
        logMessage("_116 :Finally:" + eiComponent.getKey());
        strCondition1 = getCmptToSofMktMsg(eiComponent);
        println(strCondition1);
      }
    }
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println("");
    /*
     'ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> FEATURECHANNEL (R) -> CHANNEL (E)
     */
    //Store the mktname for the heading based on the number of features
    iTemp = vFeatureFrmFeatureAvail.size();
    eiFeature = vFeatureFrmFeatureAvail.size() > 0 ? (EntityItem) vFeatureFrmFeatureAvail.elementAt(0) : null;
    strCondition1 = (eiFeature != null) ? getfeatureToSofMktMsg(eiFeature) : " ";

    vReturnEntities2 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATURECHANNEL");
    logMessage("****FEATURECHANNEL*****");
    displayContents(vReturnEntities1);
    strFilterAttr = new String[] {
        "ROUTESTOMKTG"};
    strFilterValue = new String[] {
        "100"};

    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, true, true, "CHANNEL");
    logMessage("****_116 from FEATURE CHANNEL*****");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiChannel = (EntityItem) vReturnEntities1.elementAt(i);
      logMessage("_116 from FEATURE:" + eiChannel.getKey());
      logMessage("_116" + eiChannel.getKey() + ":" + getGeoTags(eiChannel));
      vPrintDetails.add(getAttributeValue(eiChannel, "CHANNELNAME", " ") + " ");
      strCondition1 = getGeoTags(eiChannel);
      bConditionOK = strCondition1.equals(strWorldwideTag);
      vPrintDetails.add(bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoAP(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoCAN(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoUS(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoEMEA(eiChannel) && !bConditionOK ? "X" : " ");
      vPrintDetails.add(m_geList.isRfaGeoLA(eiChannel) && !bConditionOK ? "X" : " ");
    }
    strHeader = new String[] {
        "Route Description", "WW", "AP", "CAN", "US", "EMEA", "LA"};
    iColWidths = new int[] {
        26, 2, 2, 3, 2, 4, 2};
    if (vReturnEntities1.size() > 0) {
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "FEATURECHANNEL");
      for (i = 0; i < vReturnEntities2.size(); i++) {
        eiNextItem = (EntityItem) vReturnEntities2.elementAt(i);
        logMessage("_116 :Finally:" + eiNextItem.getKey());
        eiFeature = (EntityItem) eiNextItem.getUpLink(0);
        logMessage("_116 :Finally:" + eiFeature.getKey());
        strCondition1 = getfeatureToSofMktMsg(eiFeature);
        println(strCondition1);
      }
    }
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();

    println(":exmp.");

    println(".*$A_116_End");

    println(".*$A_118_Begin");
    printVanillaSVSReport("MKTGSTRATEGY", true, false);
    println(".*$A_118_End");

    println(".*$A_119_Begin");
    /*
         Concatenate the answer by combining the answer from all three (3) entities -SOF, CMPNT and FREATURE, in that order respectfully
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFRELSOF (R) when TYPE = '110' (Cross-Sell) -> SOF (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTRELCMPNT (R) when TYPE = '110' (Cross-Sell) -> CMPNT (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> FEATURERELFEATURE (R) when TYPE = '110' (Cross-Sell) -> FEATURE (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFRELCMPNT (R) when TYPE = '110' (Cross-Sell) -> CMPNT (E)
     */
    printFeatureBenefit("110", "_119", true, true);

    println(".*$A_119_End");

    println(".*$A_120_Begin");
    /*
         Concatenate the answer by combining the answer from all three (3) entities -SOF, CMPNT and FREATURE, in that order respectfully
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFRELSOF (R) when TYPE = '100' (Upsell) -> SOF (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTRELCMPNT (R) when TYPE = '100' (Upsell) -> CMPNT (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> FEATURERELFEATURE (R) when TYPE = '100' (Upsell) -> FEATURE (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFRELCMPNT (R) when TYPE = '100' (Upsell) -> CMPNT (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFRELFEATURE (R) when TYPE = '100' (Upsell) -> FEATURE (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTRELFEATURE (R) when TYPE = '100' (Upsell) -> FEATURE (E)
     */
    printFeatureBenefit("100", "_120", true, true);

    println(".*$A_120_End");

    println(".*$A_122_Begin");
    printVanillaSVSReport("CUSTWANTSNEEDS", true, false);
    println(".*$A_122_End");

    println(".*$A_123_Begin");
    printVanillaSVSReport("CUSTPAINPT", true, false); //02/06/06 Rfa guide
    println(".*$A_123_End");

    println(".*$A_124_Begin");
    printVanillaSVSReport("RESOURSKILLSET", true, false);
    println(".*$A_124_End");

    println(".*$A_126_Begin");

    /*     ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFRELSOF (R) when TYPE = '120' (Related Services) -> SOF (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTRELCMPNT (R) when TYPE = '120' (Related Services) -> CMPNT (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> FEATURERELFEATURE (R) when TYPE = '120' (Related Services) -> FEATURE (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFRELCMPNT (R) when TYPE = '120' (Related Services) -> CMPNT (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFRELFEATURE (R) when TYPE = '120' (Related Services) -> FEATURE (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTRELFEATURE (R) when TYPE = '120' (Related Services) -> FEATURE (E)
     */
    printFeatureBenefit("120", "_126", false, true); //dont print the Benefit

    println(".*$A_126_End");

    println(".*$A_128_Begin");
    printVanillaSVSReport("OTHERMKTGINFO", true, false);

    println(".*$A_128_End");

    println(".*$A_130_Begin");
    strCondition2 = "";
    strCondition4 = "";
    resetPrintvars();
    bConditionOK = false;
    strHeader = new String[] {
        "Contact Name", "Telephone", "E-mail"};
    iColWidths = new int[] {
        25, 12, 29};
    strEntityTypes = new String[] {
        "SOFSALESCNTCTOP", "SOF"};
    strCondition2 = "";

    rfaReport.setPrintDupeLines(false);

    logMessage("_130 Begin********************");
    for (i = 0; i < vSofSortedbyMkt.size(); i++) {
      eiSof = (EntityItem) vSofSortedbyMkt.elementAt(i);
      //Now get the operators
      vReturnEntities2 = searchEntityItemLink(eiSof, null, null, true, true, "SOFSALESCNTCTOP");
      logMessage("****SOFSALESCNTCTOP*****");
      displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "OP");
      logMessage("****OP*****");
      displayContents(vReturnEntities3);
      if (vReturnEntities3.size() == 0) {
        continue; //Go to the next one if no operators found
      }
      vReturnEntities4 = sortEntities(vReturnEntities3, new String[] {"LASTNAME", "FIRSTNAME"});

      strCondition4 = getAttributeValue(eiSof, "MKTGNAME") + " ";
      for (j = 0; j < vReturnEntities4.size(); j++) {
        eiOP = (EntityItem) vReturnEntities4.elementAt(j);
        //Navigate Back to SOFSALESCNTCTOP to get the GEO tags
        vReturnEntities2 = searchEntityItemLink(eiOP, null, null, true, false, "SOFSALESCNTCTOP");
        for (int k = 0; k < vReturnEntities2.size(); k++) {
          eiNextItem = (EntityItem) vReturnEntities2.elementAt(k);
          eiNextItem1 = (EntityItem) eiNextItem.getUpLink(0);
          if (eiNextItem1.getKey().equals(eiSof.getKey())) {
            break;
          } //Multiple SOF's could be linked to an OP each relator having different GEO's
        }
        strCondition1 = getGeoTags(eiNextItem);
        logMessage("Getting GEO :" + strCondition1 + ":for:" + eiNextItem.getKey());
        if (!strCondition1.equals(strCondition2)) {

          if (!strCondition2.equals(strWorldwideTag)) {
            if (strCondition2.length() > 0) {
              vPrintDetails.add(BREAK_INDICATOR + ".br;:hp2.<---" + strCondition2 + ":ehp2.");
              vPrintDetails.add("");
              vPrintDetails.add("");
            }
          }

          if (strCondition4.trim().length() > 0) {
            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            vPrintDetails.add(BREAK_INDICATOR + strCondition4 + ":p.");
            logMessage("_130 " + BREAK_INDICATOR + " " + strCondition4 + ":p.");
            vPrintDetails.add("");
            vPrintDetails.add("");

            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            strCondition4 = "";
          }

          if (!strCondition1.equals(strWorldwideTag)) {
            if (strCondition1.trim().length() > 0) {
              vPrintDetails.add(BREAK_INDICATOR + ":p.:hp2." + strCondition1 + "--->:ehp2.");
              vPrintDetails.add("");
              vPrintDetails.add("");
            }
          }
          strCondition2 = strCondition1;
        }
        else { //Just print the Market Name if there
          if (strCondition4.trim().length() > 0) {
            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            vPrintDetails.add(BREAK_INDICATOR + strCondition4 + ":p.");
            logMessage("_130 " + BREAK_INDICATOR + " " + strCondition4 + ":p.");
            vPrintDetails.add("");
            vPrintDetails.add("");

            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            strCondition4 = "";
          }
        }

        //Add the details here
        strCondition3 = getAttributeValue(eiOP, "FIRSTNAME", " ");
        strCondition3 += " " + getAttributeValue(eiOP, "LASTNAME", " ");
        vPrintDetails.add(strCondition3);
        vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
        vPrintDetails.add(getAttributeValue(eiOP, "EMAIL", " "));
      }

    }
    if (!strCondition2.equals(strWorldwideTag)) {
      if (strCondition2.length() > 0) {
        vPrintDetails.add(BREAK_INDICATOR + ".br;:hp2.<---" + strCondition2 + ":ehp2.");
        vPrintDetails.add("");
        vPrintDetails.add("");
      }
      strCondition2 = strCondition1;
    }

    strEntityTypes = new String[] {
        "CMPNTSALESCNTCTOP", "CMPNT"};
    strCondition2 = "";

    logMessage("_130 Begin CMPNT********************");
    for (i = 0; i < vCmptSortedbyMkt.size(); i++) {
      eiComponent = (EntityItem) vCmptSortedbyMkt.elementAt(i);
      //Now get the operators
      vReturnEntities2 = searchEntityItemLink(eiComponent, null, null, true, true, "CMPNTSALESCNTCTOP");
      logMessage("****CMPNTSALESCNTCTOP*****");
      displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "OP");
      logMessage("****OP*****");
      displayContents(vReturnEntities3);
      if (vReturnEntities3.size() == 0) {
        continue; //Go to the next one if no operators found
      }
      vReturnEntities4 = sortEntities(vReturnEntities3, new String[] {"LASTNAME", "FIRSTNAME"});

      strCondition4 = getCmptToSofMktMsg(eiComponent);
      for (j = 0; j < vReturnEntities4.size(); j++) {
        eiOP = (EntityItem) vReturnEntities4.elementAt(j);
        //Navigate Back to CMPNTSALESCNTCTOP to get the GEO tags
        vReturnEntities2 = searchEntityItemLink(eiOP, null, null, true, false, "CMPNTSALESCNTCTOP");
        for (int k = 0; k < vReturnEntities2.size(); k++) {
          eiNextItem = (EntityItem) vReturnEntities2.elementAt(k);
          eiNextItem1 = (EntityItem) eiNextItem.getUpLink(0);
          if (eiNextItem1.getKey().equals(eiComponent.getKey())) {
            break;
          } //Multiple CMPNT's could be linked to an OP each relator having different GEO's
        }
        strCondition1 = getGeoTags(eiNextItem);
        logMessage("Getting GEO :" + strCondition1 + ":for:" + eiNextItem.getKey());
        if (!strCondition1.equals(strCondition2)) {
          if (!strCondition2.equals(strWorldwideTag)) {
            if (strCondition2.length() > 0) {
              vPrintDetails.add(BREAK_INDICATOR + ".br;:hp2.<---" + strCondition2 + ":ehp2.");
              vPrintDetails.add("");
              vPrintDetails.add("");
            }
          }
          if (strCondition4.trim().length() > 0) {
            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            vPrintDetails.add(BREAK_INDICATOR + strCondition4 + ":p.");
            logMessage("_130 " + BREAK_INDICATOR + " " + strCondition4 + ":p.");
            vPrintDetails.add("");
            vPrintDetails.add("");

            vPrintDetails.add(BREAK_INDICATOR); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            strCondition4 = "";
          }
          if (!strCondition1.equals(strWorldwideTag)) {
            if (strCondition1.trim().length() > 0) {
              vPrintDetails.add(BREAK_INDICATOR + ":p.:hp2." + strCondition1 + "--->:ehp2.");
              vPrintDetails.add("");
              vPrintDetails.add("");
            }
          }
          strCondition2 = strCondition1;
        }
        else { //Just print the Market Name if there
          if (strCondition4.trim().length() > 0) {
            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            vPrintDetails.add(BREAK_INDICATOR + strCondition4 + ":p.");
            logMessage("_130 " + BREAK_INDICATOR + " " + strCondition4 + ":p.");
            vPrintDetails.add("");
            vPrintDetails.add("");

            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            strCondition4 = "";
          }
        }

        //Add the details here
        strCondition3 = getAttributeValue(eiOP, "FIRSTNAME", " ");
        strCondition3 += " " + getAttributeValue(eiOP, "LASTNAME", " ");
        logMessage("_130: OP is " + strCondition1 + "->" + strCondition2 + ":OP" + strCondition3);
        vPrintDetails.add(strCondition3);
        vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
        vPrintDetails.add(getAttributeValue(eiOP, "EMAIL", " "));
      }
    }
    if (!strCondition2.equals(strWorldwideTag)) {
      if (strCondition2.length() > 0) {
        vPrintDetails.add(BREAK_INDICATOR + ".br;:hp2.<---" + strCondition2 + ":ehp2.");
        vPrintDetails.add("");
        vPrintDetails.add("");
      }
      strCondition2 = strCondition1;
    }

    //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> FEATURESALESCNTCTOP (R) -> OP (E)

    vReturnEntities2 = searchEntityVectorLink(vFeatureSortedbyMkt, null, null, true, true, "FEATRSALESCNTCTOP");
    logMessage("****FEATRSALESCNTCTOP*****");
    displayContents(vReturnEntities2);
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "OP");
    logMessage("****OP*****");
    displayContents(vReturnEntities3);
    strHeader = new String[] {
        "Contact Name", "Telephone", "E-mail"};
    iColWidths = new int[] {
        25, 12, 29};
    strEntityTypes = new String[] {
        "FEATRSALESCNTCTOP", "FEATURE"};
    logMessage("_130 Begin FEATURE********************");
    strCondition2 = "";
    for (i = 0; i < vFeatureSortedbyMkt.size(); i++) {
      eiFeature = (EntityItem) vFeatureSortedbyMkt.elementAt(i);
      //Now get the operators
      vReturnEntities2 = searchEntityItemLink(eiFeature, null, null, true, true, "FEATRSALESCNTCTOP");
      logMessage("****FEATRSALESCNTCTOP*****");
      displayContents(vReturnEntities2);
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, null, null, true, true, "OP");
      logMessage("****OP*****");
      displayContents(vReturnEntities3);
      if (vReturnEntities3.size() == 0) {
        continue; //Go to the next one if no operators found
      }
      vReturnEntities4 = sortEntities(vReturnEntities3, new String[] {"LASTNAME", "FIRSTNAME"});

      strCondition4 = getfeatureToSofMktMsg(eiFeature);
      for (j = 0; j < vReturnEntities4.size(); j++) {
        eiOP = (EntityItem) vReturnEntities4.elementAt(j);
        //Navigate Back to FEATRSALESCNTCTOP to get the GEO tags
        vReturnEntities2 = searchEntityItemLink(eiOP, null, null, true, false, "FEATRSALESCNTCTOP");
        for (int k = 0; k < vReturnEntities2.size(); k++) {
          eiNextItem = (EntityItem) vReturnEntities2.elementAt(k);
          eiNextItem1 = (EntityItem) eiNextItem.getUpLink(0);
          if (eiNextItem1.getKey().equals(eiFeature.getKey())) {
            break;
          } //Multiple features's could be linked to an OP each relator having different GEO's
        }
        strCondition1 = getGeoTags(eiNextItem);
        logMessage("Getting GEO :" + strCondition1 + ":for:" + eiNextItem.getKey());
        if (!strCondition1.equals(strCondition2)) {

          if (!strCondition2.equals(strWorldwideTag)) {
            if (strCondition2.length() > 0) {
              vPrintDetails.add(BREAK_INDICATOR + ".br;:hp2.<---" + strCondition2 + ":ehp2.");
              vPrintDetails.add("");
              vPrintDetails.add("");
            }
          }
          if (strCondition4.trim().length() > 0) {
            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            vPrintDetails.add(BREAK_INDICATOR + strCondition4 + ":p.");
            logMessage("_130 " + BREAK_INDICATOR + " " + strCondition4 + ":p.");
            vPrintDetails.add("");
            vPrintDetails.add("");

            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            strCondition4 = "";
          }
          if (!strCondition1.equals(strWorldwideTag)) {
            if (strCondition1.trim().length() > 0) {
              vPrintDetails.add(BREAK_INDICATOR + ":p.:hp2." + strCondition1 + "--->:ehp2.");
              vPrintDetails.add("");
              vPrintDetails.add("");
            }
          }
          strCondition2 = strCondition1;
        }
        else { //Just print the Market Name if there
          if (strCondition4.trim().length() > 0) {
            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            vPrintDetails.add(BREAK_INDICATOR + strCondition4 + ":p.");
            logMessage("_130 " + BREAK_INDICATOR + " " + strCondition4 + ":p.");
            vPrintDetails.add("");
            vPrintDetails.add("");

            vPrintDetails.add(""); //addl line break needed acc to Alan
            vPrintDetails.add("");
            vPrintDetails.add("");
            strCondition4 = "";
          }
        }
        //Add the details here
        strCondition3 = getAttributeValue(eiOP, "FIRSTNAME", " ");
        strCondition3 += " " + getAttributeValue(eiOP, "LASTNAME", " ");
        vPrintDetails.add(strCondition3);
        vPrintDetails.add(getAttributeValue(eiOP, "TELEPHONE", " "));
        vPrintDetails.add(getAttributeValue(eiOP, "EMAIL", " "));
      }
    }
    if (!strCondition2.equals(strWorldwideTag)) {
      if (strCondition2.length() > 0) {
        vPrintDetails.add(BREAK_INDICATOR + ".br;:hp2.<---" + strCondition2 + ":ehp2.");
        vPrintDetails.add("");
        vPrintDetails.add("");
      }
      strCondition2 = strCondition1;
    }
    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      println(".in 0");
    }
    printReport(true, strHeader, iColWidths, vPrintDetails);
    rfaReport.setPrintDupeLines(true);
    if (vPrintDetails.size() > 0) {
      println(":exmp.");
    }
    resetPrintvars();
    println(".*$A_130_End");

    println(".*$A_132_Begin");
    //Sales Action Required
    printVanillaSVSReport("SALESACTREQ", true, false);
//    printInternalLetter("SALESACTREQ",true);

    println(".*$A_132_End");

    println(".*$A_134_Begin");
    //Sales Approach
    printVanillaSVSReport("SALESAPPROACH", true, false);
//    printInternalLetter("SALESAPPROACH",true);
    println(".*$A_134_End");

    println(".*$A_136_Begin");
    printVanillaSVSReport("CUSTCANDGUIDELINES", true, false);
    println(".*$A_136_End");

    println(".*$A_138_Begin");
    printVanillaSVSReport("CUSTRESTRICTIONS", true, false);

    println(".*$A_138_End");

    println(".*$A_140_Begin");
    printVanillaSVSReport("HANDOBJECTIONS", true, false);
    println(".*$A_140_End");

    println(".*$A_144_Begin");
    printVanillaSVSReport("COMPETITIVEOF", true, false);
    println(".*$A_144_End");

    println(".*$A_146_Begin");
    printVanillaSVSReport("STRENGTHWEAKNESS", true, false);
    println(".*$A_146_End");

    println(".*$A_148_Begin");
    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFPRICE (R) -> PRICEINFO (E)
     ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTPRICE (R) -> PRICEINFO (E)
     ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> FEATUREPRICE (R) -> PRICEINFO (E)
     */
    strHeader = new String[] {
        "Offering Name"};
    iColWidths = new int[] {
        69};

    vReturnEntities1 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFPRICE");
    //vReturnEntities2 = searchEntityGroupLink(egSof, null, null, true, true, "SOFPRICE");
    vReturnEntities2 = new Vector();
    logMessage("A_148:From SOF");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i); //This is SOFPRICE
      logMessage("A_148:" + eiNextItem.getKey());
      eiSof = (EntityItem) eiNextItem.getUpLink(0);
      eiPriceInfo = (EntityItem) eiNextItem.getDownLink(0);
      logMessage("A_148:" + eiSof.getKey());
      logMessage("A_148:" + eiPriceInfo.getKey());
      strCondition1 = getAttributeValue(eiPriceInfo, "BILLINGAPP", " ");
      strCondition1 += ":" + eiSof.getKey(); //Add the key to the BILLINGAPP name

      vReturnEntities2.add(strCondition1);
    }

    vReturnEntities1 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTPRICE");
    logMessage("A_148:From CMPNTPRICE");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i); //This is CMPNTPRICE
      logMessage("A_148:" + eiNextItem.getKey());
      eiComponent = (EntityItem) eiNextItem.getUpLink(0);
      eiPriceInfo = (EntityItem) eiNextItem.getDownLink(0);
      logMessage("A_148:" + eiComponent.getKey());
      logMessage("A_148:" + eiPriceInfo.getKey());
      strCondition1 = getAttributeValue(eiPriceInfo, "BILLINGAPP", " ");
      strCondition1 += ":" + eiComponent.getKey(); //Add the key to the BILLINGAPP name

      vReturnEntities2.add(strCondition1);
    }

    vReturnEntities1 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATUREPRICE");
    logMessage("A_148:From CMPNTPRICE");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i); //This is CMPNTPRICE
      logMessage("A_148:" + eiNextItem.getKey());
      eiFeature = (EntityItem) eiNextItem.getUpLink(0);
      eiPriceInfo = (EntityItem) eiNextItem.getDownLink(0);
      logMessage("A_148:" + eiFeature.getKey());
      logMessage("A_148:" + eiPriceInfo.getKey());
      strCondition1 = getAttributeValue(eiPriceInfo, "BILLINGAPP", " ");
      strCondition1 += ":" + eiFeature.getKey(); //Add the key to the BILLINGAPP name

      vReturnEntities2.add(strCondition1);
    }

    resetPrintvars();

    vReturnEntities3 = mySort.alphabetizeVector(vReturnEntities2); //Sort the marketing messages
    if (vReturnEntities3.size() > 0) {
      println(":xmp.");
      println(".kp off");
      println(".in 0");
      println("");
      resetPrintvars();
      strCondition1 = (String) vReturnEntities3.elementAt(0); //Prepare for the control break sequence
      if (strCondition1.indexOf(":CMPNT") > 0) {
        strCondition2 = strCondition1.substring(0, strCondition1.indexOf(":CMPNT"));
      }
      else if (strCondition1.indexOf(":FEATURE") > 0) {
        strCondition2 = strCondition1.substring(0, strCondition1.indexOf(":FEATURE"));
      }
      else if (strCondition1.indexOf(":SOF") > 0) {
        strCondition2 = strCondition1.substring(0, strCondition1.indexOf(":SOF"));
      }
      print("Billing Application: ");
      bConditionOK = false;
      if (strCondition2.indexOf("*") > -1) {
        st = new StringTokenizer(strCondition2, "*");
        while (st.hasMoreTokens()) {
          if (bConditionOK) {
            print("                     ");
          }
          bConditionOK = true;
          println(st.nextToken().trim());
        }
      }
    }

    for (i = 0; i < vReturnEntities3.size(); i++) {
      logMessage("A_148:Sorted Vector returning: " + strCondition1);
      strCondition1 = (String) vReturnEntities3.elementAt(i);
      if (strCondition1.indexOf(":CMPNT") > 0) {
        strCondition4 = strCondition1.substring(0, strCondition1.indexOf(":CMPNT"));
      }
      else if (strCondition1.indexOf(":FEATURE") > 0) {
        strCondition4 = strCondition1.substring(0, strCondition1.indexOf(":FEATURE"));
      }
      else if (strCondition1.indexOf(":SOF") > 0) {
        strCondition4 = strCondition1.substring(0, strCondition1.indexOf(":SOF"));
      }
      logMessage("A_148 Current Mkt:" + strCondition4);
      logMessage("A_148 Prev Mkt :" + strCondition2);
      if (!strCondition2.equals(strCondition4)) { //check whether previous mktname is the same as current one
        logMessage("A_148 Current < New");
        strCondition2 = strCondition4;
        printReport(true, strHeader, iColWidths, vPrintDetails); //Print when they are not the same
        println("");
        print("Billing Application: ");
        bConditionOK = false; //Skip for the first one
        if (strCondition2.indexOf("*") > -1) {
          st = new StringTokenizer(strCondition2, "*");
          while (st.hasMoreTokens()) {
            if (bConditionOK) {
              print("                     ");
            }
            println(st.nextToken().trim());
            bConditionOK = true;
          }
        }
        resetPrintvars();
      }

      if (strCondition1.indexOf(":CMPNT") > 0) {
        strCondition5 = strCondition1.substring(strCondition1.indexOf(":CMPNT") + 1);
        logMessage("A_155:Parsed out" + strCondition5);
        eiComponent = egComponent.getEntityItem(strCondition5); //get the Component entity from group using parsed key
        strCondition1 = getCmptToSofMktMsg(eiComponent);
        vPrintDetails.add(strCondition1);
        logMessage("A_148:Print:" + strCondition1);
        //vPrintDetails.add(getAttributeValue(eiComponent, "COMPONENTID","" ));
        logMessage("A_148:Print:" + getAttributeValue(eiComponent, "COMPONENTID", ""));
      }
      else if (strCondition1.indexOf(":FEATURE") > 0) {
        strCondition5 = strCondition1.substring(strCondition1.indexOf(":FEATURE") + 1);
        logMessage("A_155:Parsed out" + strCondition5);
        eiFeature = egFeature.getEntityItem(strCondition5); //get the feature entity from the group using parsed key
        strCondition1 = getfeatureToSofMktMsg(eiFeature);
        vPrintDetails.add(strCondition1);
        //eiComponent = getUplinkedEntityItem(eiFeature, strFeatureToCmpt);
        //vPrintDetails.add(getAttributeValue(eiComponent, "COMPONENTID",""));
        logMessage("A_148:Print:" + strCondition1);
      }
      else if (strCondition1.indexOf(":SOF") > 0) {
        strCondition5 = strCondition1.substring(strCondition1.indexOf(":SOF") + 1);
        logMessage("A_148:Parsed out" + strCondition5);
        eiSof = egSof.getEntityItem(strCondition5); //get the SOF entity from the group using parsed key
        strCondition1 = getSOFMktName(eiSof);
        vPrintDetails.add(strCondition1);

        //vPrintDetails.add(getAttributeValue(eiSof, "OFIDNUMBER", " "));
        logMessage("A_148:Print:" + strCondition1);
        logMessage("A_148:Print:" + getAttributeValue(eiSof, "OFIDNUMBER", " "));
      }
    }

    if (vPrintDetails.size() > 0) {
      printReport(true, strHeader, iColWidths, vPrintDetails); //Print when processing is done
      resetPrintvars();
    }

    if (vReturnEntities3.size() > 0) {
      println(":exmp.");
    }

    println(".*$A_148_End");

    println(".*$A_150_Begin");
    strParamList1 = new String[] {
        "STANDARDAMENDTEXT"}; //Internal Letter/Contract Info
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "110", "", true);
    iColWidths = new int[] {
        69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_150_End");

    println(".*$A_151_Begin");
    //ANNOUNCEMENT (E) -> ANNTOANNDELIVER (R) -> ANNDELIVERABLE (E)  -> ANNDELREQTRANS (R) -> EMEATRANSLATION (E) -> TRANSDELREVIEW (R) -> OP (E)
    strFilterAttr = new String[] {
        "DELIVERABLETYPE"};
    strFilterValue = new String[] {
        "852"};
    vReturnEntities1 = searchEntityGroup(grpAnnDeliv, strFilterAttr, strFilterValue, true);
    logMessage("A_151****ANNDELIVERABLE");
    displayContents(vReturnEntities1);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "ANNDELREQTRANS");
    logMessage("A_151****ANNDELREQTRANS");
    displayContents(vReturnEntities2);
    strFilterAttr = new String[] {
        "LANGUAGES", "LANGUAGES", "LANGUAGES", "LANGUAGES"};
    strFilterValue = new String[] {
        "2802", "2803", "2797", "2796"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true,
                                              "EMEATRANSLATION");
    logMessage("A_151****EMEATRANSLATION");
    displayContents(vReturnEntities3);
    vReturnEntities4 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "TRANSDELREVIEW");
    logMessage("A_151****TRANSDELREVIEW");
    displayContents(vReturnEntities4);
    for (i = 0; i < vReturnEntities4.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities4.elementAt(i);
      logMessage("A_287****" + eiNextItem.getEntityType() + ":" + eiNextItem.getEntityID());
      eiEmeaTranslation = (EntityItem) eiNextItem.getUpLink(0);
      eiOP = (EntityItem) eiNextItem.getDownLink(0);
      vPrintDetails.add(getAttributeValue(eiEmeaTranslation, "LANGUAGES", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0, 1);
      strCondition1 += ". " + getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ");
      strCondition1 += "/" + getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
    }

    strHeader = new String[] {
        "Language", "Brand Reviewer Name", "  Node/Userid"};
    iColWidths = new int[] {
        17, 30, 17};
    println(":h4.Worldwide Customer Letter Translation");
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println(":exmp.");
    println(":p.Note: This section is deleted at PLET generation time.");
    println(".*$A_151_End");

    println(".*$A_152_Begin");
    //ANNOUNCEMENT (E) -> ANNTOANNDELIVER (R) -> ANNDELIVERABLE (E)  -> ANNDELREQTRANS (R) -> EMEATRANSLATION (E)
    //  -> TRANSDELREVIEW (R) -> OP (E)
    strFilterAttr = new String[] {
        "DELIVERABLETYPE"};
    strFilterValue = new String[] {
        "856"};
    vReturnEntities1 = searchEntityGroup(grpAnnDeliv, strFilterAttr, strFilterValue, true);
    logMessage("A_152****ANNDELIVERABLE");
    displayContents(vReturnEntities1);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "ANNDELREQTRANS");
    logMessage("A_152****ANNDELREQTRANS");
    displayContents(vReturnEntities2);
    strFilterAttr = new String[] {
        "LANGUAGES", "LANGUAGES", "LANGUAGES", "LANGUAGES"};
    strFilterValue = new String[] {
        "2802", "2803", "2797", "2796"};
    vReturnEntities3 = searchEntityVectorLink(vReturnEntities2, strFilterAttr, strFilterValue, false, true,
                                              "EMEATRANSLATION");
    logMessage("A_152****EMEATRANSLATION");
    displayContents(vReturnEntities3);
    vReturnEntities4 = searchEntityVectorLink(vReturnEntities3, null, null, true, true, "TRANSDELREVIEW");
    logMessage("A_152****TRANSDELREVIEW");
    displayContents(vReturnEntities4);
    vPrintDetails1 = new Vector();
    for (i = 0; i < vReturnEntities4.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities4.elementAt(i);
      eiEmeaTranslation = (EntityItem) eiNextItem.getUpLink(0);
      eiOP = (EntityItem) eiNextItem.getDownLink(0);
      logMessage("A_152****" + eiNextItem.getEntityType() + ":" + eiNextItem.getEntityID());
      vPrintDetails.add(getAttributeValue(eiEmeaTranslation, "LANGUAGES", " "));
      strCondition1 = getAttributeValue(eiOP, "FIRSTNAME", " ").substring(0, 1);
      strCondition1 += ". " + getAttributeValue(eiOP, "LASTNAME", " ");
      vPrintDetails.add(strCondition1);
      strCondition1 = getAttributeValue(eiOP, "VNETNODE", " ");
      strCondition1 += "/" + getAttributeValue(eiOP, "VNETUID", " ");
      vPrintDetails.add(strCondition1);
      vPrintDetails1.add(getAttributeValue(eiNextItem, "PROPOSALINSERTID", " "));
    }

    strHeader = new String[] {
        "Language", "Brand Reviewer Name", "  Node/Userid"};
    iColWidths = new int[] {
        17, 30, 17};
    println(":xmp.");
    println(".kp off");
    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    strCondition1 = "";
    for (i = 0, j = 0; i < vPrintDetails1.size(); i++) {
      strCondition1 += "PINo: " + (String) vPrintDetails1.elementAt(i) + "    ";
      if (j == 3) {
        println("Proposal Insert Document Ids");
        println(strCondition1);
        strCondition1 = "";
        j = 0;
      }
    }
    if (i < 3) {
      println("Proposal Insert Document Ids");
    }

    println(strCondition1);
    println(":exmp.");
    println(":p.Note: This section is deleted at PLET generation time.");

    println(".*$A_152_End");
    /*
        println(".*$A_153_Begin");
        logMessage("A_153_Begin");
        printInternalLetter(null);
        resetPrintvars();
        println(".*$A_153_End");

     */
    println(".*$A_153_Begin");
    logMessage("A_153_Begin");
    //    printInternalLetter("OFIDNUMBER", true);
    // MN29166812  data missing because of SOFAVAIL checks, replaced method with new one to avoid reuse conflicts
    printA153();
    resetPrintvars();
    println(".*$A_153_End");

    println(".*$A_154_Begin");
    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) tbd
     */
    //Start from CMPNT and navigate to SOF using SOFCMPNT

    strCondition2 = "";
    vReturnEntities1 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, false, "SOFCMPNT");
    vReturnEntities2 = new Vector();
    strSofToCmpt = new String[] {
        "SOFCMPNT", "CMPNT"}; //Change in navigation from SOF to extract mktmsg
    logMessage("A_154:From Component");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i); //This is SOFCMPNT
      logMessage("A_154:" + eiNextItem.getKey());
      eiSof = (EntityItem) eiNextItem.getUpLink(0);
      strCondition1 = getSOFMktName(eiSof, false);
      logMessage("A_154:" + eiSof.getKey());
      eiComponent = (EntityItem) eiNextItem.getDownLink(0);
      logMessage("A_154:" + eiComponent.getKey());
      strCondition1 += ":" + eiComponent.getKey(); //Add the key to the sof mkt name

      vReturnEntities2.add(strCondition1);
    }

    vReturnEntities1 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, false, "CMPNTFEATURE");
    logMessage("A_154:From Feature");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i); //This is CMPNTFEATURE
      logMessage("A_154:" + eiNextItem.getKey());
      eiComponent = (EntityItem) eiNextItem.getUpLink(0);
      logMessage("A_154:" + eiComponent.getKey());
      eiFeature = (EntityItem) eiNextItem.getDownLink(0);
      logMessage("A_154:" + eiFeature.getKey());

      eiSof = getUplinkedEntityItem(eiComponent, new String[] {"SOFCMPNT", "SOF"});
      strCondition1 = getSOFMktName(eiSof, false);
      //strCondition1 = getCmptToSofMktMsg(eiComponent);
      logMessage("A_154:Mktname" + strCondition1);
      strCondition1 += ":" + eiFeature.getKey(); //Add the key to the sof mkt name

      vReturnEntities2.add(strCondition1);
    }

    resetPrintvars();

    vReturnEntities3 = mySort.alphabetizeVector(vReturnEntities2); //Sort the marketing messages
    if (vReturnEntities3.size() > 0) {
      println(":xmp.");
      println(".kp off");
      println("");
      strHeader = new String[] {
          "Service Product/Component Name", "Component ID", "Number"};
      iColWidths = new int[] {
          45, 13, 10};
      resetPrintvars();
      strCondition1 = (String) vReturnEntities3.elementAt(0); //Prepare for the control break sequence
      if (strCondition1.indexOf(":CMPNT") > 0) {
        strCondition2 = strCondition1.substring(0, strCondition1.indexOf(":CMPNT"));
      }
      else if (strCondition1.indexOf(":FEATURE") > 0) {
        strCondition2 = strCondition1.substring(0, strCondition1.indexOf(":FEATURE"));
      }
      println(strCondition2);
    }

    for (i = 0; i < vReturnEntities3.size(); i++) {
      logMessage("A_154:Sorted Vector returning: " + strCondition1);
      strCondition1 = (String) vReturnEntities3.elementAt(i);
      if (strCondition1.indexOf(":CMPNT") > 0) {
        strCondition4 = strCondition1.substring(0, strCondition1.indexOf(":CMPNT"));
      }
      else if (strCondition1.indexOf(":FEATURE") > 0) {
        strCondition4 = strCondition1.substring(0, strCondition1.indexOf(":FEATURE"));
      }
      logMessage("A_154 Current Mkt:" + strCondition4);
      logMessage("A_154 Prev Mkt :" + strCondition2);
      if (!strCondition2.equals(strCondition4)) { //check whether previous mktname is the same as current one
        logMessage("A_154 Current < New checking");
        strCondition2 = strCondition4;
        println("                                                            Autobahn");
        println("                                                            Project");

        printReport(true, strHeader, iColWidths, vPrintDetails); //Print when they are not the same
        println("");
        println(strCondition2);
        resetPrintvars();
      }

      if (strCondition1.indexOf(":CMPNT") > 0) {
        strCondition5 = strCondition1.substring(strCondition1.indexOf(":CMPNT") + 1);
        logMessage("A_154:Parsed out" + strCondition5);
        eiComponent = egComponent.getEntityItem(strCondition5); //get the Component entity from group using parsed key
        strCondition3 = getAttributeValue(eiComponent, "ITSCMPNTCATNAME", "");
        strCondition3 = (strCondition3.length() > 0 ? strCondition3 + " " : "") +
            getAttributeValue(eiComponent, "MKTGNAME", " ");
        logMessage(":CMPNT :" + strCondition3);
        //strCondition3 = getCmptToSofMktMsg(eiComponent);
        vPrintDetails.add(strCondition3);

        strCondition3 = getAttributeValue(eiComponent, "COMPONENTID", "No Value" + eiComponent.getKey());
        vPrintDetails.add(strCondition3);

        //Navigate to SOF from CMPNT to see whether SOF is linked to AVAIL before printing PROJnumber
        eiSof = getUplinkedEntityItem(eiComponent, strCmptToSof);
        //Print Projnumber only if sof linked to avail
        vReturnEntities1 = searchEntityItemLink(eiSof, null, null, true, true, "SOFAVAIL");
        strCondition3 = " ";
        if (vReturnEntities1.size() > 0) {
          //Navigate to OFDEVLPROJ and get PROJNUMBER
          //Navigation: ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) - OFDEVLPROJCMPNT (R) - OFDEVLPROJ (E)

          vReturnEntities4 = searchEntityItemLink(eiComponent, null, null, true, false, "OFDEVLPROJCMPNT");
          if (vReturnEntities4.size() > 0) {
            eiNextItem2 = (EntityItem) vReturnEntities4.elementAt(0);
            eiNextItem3 = (EntityItem) eiNextItem2.getUpLink(0); //This will be the OFDEVLPROJ entity
            strCondition3 = getAttributeValue(eiNextItem3, "PROJNUMBER", "No Value" + eiNextItem3.getKey());
          }

        }

        vPrintDetails.add(strCondition3);

      }
      else if (strCondition1.indexOf(":FEATURE") > 0) {
        strCondition5 = strCondition1.substring(strCondition1.indexOf(":FEATURE") + 1);
        logMessage("A_154:Parsed out" + strCondition5);
        eiFeature = egFeature.getEntityItem(strCondition5); //get the feature entity from the group using parsed key
        //Navigate to component and get Mkt message
        eiNextItem1 = getUplinkedEntityItem(eiFeature, strFeatureToCmpt); //this is CMPNT

        //Then get  mktg msg
        strCondition3 = getAttributeValue(eiNextItem1, "ITSCMPNTCATNAME", "");
        strCondition3 = (strCondition3.length() > 0 ? strCondition3 + " " : "") +
            getAttributeValue(eiNextItem1, "MKTGNAME", " ");
        logMessage(":Feature :" + strCondition3);
        vPrintDetails.add(strCondition3);
        vPrintDetails.add(getAttributeValue(eiNextItem1, "COMPONENTID", "No Value" + eiFeature.getKey()));

        //Navigate to SOF from FEATURE to see whether SOF is linked to AVAIL before printing PROJnumber
        eiComponent = getUplinkedEntityItem(eiFeature, strFeatureToCmpt);
        strCondition3 = " ";
        if (eiComponent == null) {
          vReturnEntities1.removeAllElements();
        }
        else {

          eiSof = getUplinkedEntityItem(eiComponent, strCmptToSof);
          //Print Projnumber only if sof linked to avail
          vReturnEntities1 = searchEntityItemLink(eiSof, null, null, true, true, "SOFAVAIL");
        }
        if (vReturnEntities1.size() > 0) {
          //Navigate to OFDEVLPROJ and get PROJNUMBER
          //Navigation: ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> CMPNTFEATURE (R ) -> CMPNT (E) - OFDEVLPROJCMPNT (R) - OFDEVLPROJ (E)
          vReturnEntities4 = searchEntityItemLink(eiNextItem1, null, null, true, false, "OFDEVLPROJCMPNT");
          if (vReturnEntities4.size() > 0) {
            eiNextItem2 = (EntityItem) vReturnEntities4.elementAt(0);
            eiNextItem3 = (EntityItem) eiNextItem2.getUpLink(0); //This will be the OFDEVLPROJ entity
            strCondition3 = getAttributeValue(eiNextItem3, "PROJNUMBER", "No Value" + eiNextItem3.getKey());
          }

        }

        vPrintDetails.add(strCondition3);

      }
    }

    if (vPrintDetails.size() > 0) {
      println("                                              Service       Autobahn");
      println("                                              Product/      Project");
      printReport(true, strHeader, iColWidths, vPrintDetails); //Print when processing is done
      resetPrintvars();
    }

    if (vReturnEntities3.size() > 0) {
      println(":exmp.");
    }
    strSofToCmpt = new String[] {
        "SOFRELCMPNT", "CMPNT"}; //Set it back to what it was

    println(".*$A_154_End");

    println(".*$A_155_Begin");
    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> CMPNTFEATURE (R) -> CMPNT (E) -> SOFCMPNT (R) -> SOF (E)
     ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> CMPNTFEATURE (R) -> CMPNT (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E)


     */
    //Start from FEATURE and navigate to SOF using SOFCMPNT

    strCondition2 = "";

    vReturnEntities1 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, false, "CMPNTFEATURE");
    logMessage("A_155:From Feature");
    displayContents(vReturnEntities1);
    for (i = 0; i < vReturnEntities1.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities1.elementAt(i); //This is CMPNTFEATURE
      logMessage("A_155:" + eiNextItem.getKey());
      eiComponent = (EntityItem) eiNextItem.getUpLink(0);
      logMessage("A_155:" + eiComponent.getKey());
      eiFeature = (EntityItem) eiNextItem.getDownLink(0);
      logMessage("A_155:" + eiFeature.getKey());

      eiSof = getUplinkedEntityItem(eiComponent, new String[] {"SOFCMPNT", "SOF"});
      strCondition1 = getSOFMktName(eiSof, false);
      //strCondition1 = getCmptToSofMktMsg(eiComponent);
      logMessage("A_155:Mktname" + strCondition1);
      strCondition1 += ":" + eiFeature.getKey(); //Add the key to the sof mkt name

      vReturnEntities2.add(strCondition1);
    }

    resetPrintvars();

    vReturnEntities3 = mySort.alphabetizeVector(vReturnEntities2); //Sort the marketing messages
    if (vReturnEntities3.size() > 0) {
      println(":xmp.");
      println(".kp off");
      println("");
      // TIR 6QKKNN
//      strHeader = new String[]{"Feature Name", "Feature ID"};
//      iColWidths = new int[]{56, 14};
      /*
       Column Header Line 1	Column Position 	 Length
       Feature Name			1 - 12					12
       Feature ID				45 - 54					10
       Autobahn				56 - 63					8

       Column Header			Column Position 	 Length
       Project Number			56 - 69					14
       */
      strHeader = new String[] {
          "Feature Name", "Feature Id", "Number"}; // TIR 6QKKNN
      iColWidths = new int[] {
          45, 11, 10}; // TIR 6QKKNN

      resetPrintvars();
      strCondition1 = (String) vReturnEntities3.elementAt(0); //Prepare for the control break sequence
      if (strCondition1.indexOf(":CMPNT") > 0) {
        strCondition2 = strCondition1.substring(0, strCondition1.indexOf(":CMPNT"));
      }
      else if (strCondition1.indexOf(":FEATURE") > 0) {
        strCondition2 = strCondition1.substring(0, strCondition1.indexOf(":FEATURE"));
      }
      println(strCondition2);
    }

    for (i = 0; i < vReturnEntities3.size(); i++) {
      logMessage("A_155:Sorted Vector returning: " + strCondition1);
      strCondition1 = (String) vReturnEntities3.elementAt(i);
      if (strCondition1.indexOf(":CMPNT") > 0) {
        strCondition4 = strCondition1.substring(0, strCondition1.indexOf(":CMPNT"));
      }
      else if (strCondition1.indexOf(":FEATURE") > 0) {
        strCondition4 = strCondition1.substring(0, strCondition1.indexOf(":FEATURE"));
      }
      logMessage("A_155 Current Mkt:" + strCondition4);
      logMessage("A_155 Prev Mkt :" + strCondition2);
      if (!strCondition2.equals(strCondition4)) { //check whether previous mktname is the same as current one
        logMessage("A_155 Current < New");
        strCondition2 = strCondition4;
        println("                                              Service/    Autobahn");
        println("Service Component/                            Component   Project");
        printReport(true, strHeader, iColWidths, vPrintDetails); //Print when they are not the same
        println("");
        println(strCondition2);
        resetPrintvars();
      }

      if (strCondition1.indexOf(":CMPNT") > 0) { //This shouldnt apply since we are getting only features
        strCondition5 = strCondition1.substring(strCondition1.indexOf(":CMPNT") + 1);
        logMessage("A_155:Parsed out" + strCondition5);
        eiComponent = egComponent.getEntityItem(strCondition5); //get the Component entity from group using parsed key
        logMessage(":CMPNT :" + strCondition3);
        strCondition3 = getCmptToSofMktMsg(eiComponent);
        vPrintDetails.add(strCondition3);
        strCondition3 = getAttributeValue(eiComponent, "COMPONENTID", "No Value" + eiComponent.getKey());
        vPrintDetails.add(strCondition3);

        //Navigate to SOF from CMPNT to see whether SOF is linked to AVAIL before printing PROJnumber
        eiSof = getUplinkedEntityItem(eiComponent, strCmptToSof);
        //Print Projnumber only if sof linked to avail
        vReturnEntities1 = searchEntityItemLink(eiSof, null, null, true, true, "SOFAVAIL");
        // TIR 6QKKNN add autobahn project number
        strCondition3 = " ";
        if (vReturnEntities1.size() > 0) {
          //Navigate to OFDEVLPROJ and get PROJNUMBER
          //Navigation: ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) - OFDEVLPROJCMPNT (R) - OFDEVLPROJ (E)
          vReturnEntities4 = searchEntityItemLink(eiComponent, null, null, true, false, "OFDEVLPROJCMPNT");
          if (vReturnEntities4.size() > 0) {
            eiNextItem2 = (EntityItem) vReturnEntities4.elementAt(0);
            eiNextItem3 = (EntityItem) eiNextItem2.getUpLink(0); //This will be the OFDEVLPROJ entity
            strCondition3 = getAttributeValue(eiNextItem3, "PROJNUMBER", "No Value" + eiNextItem3.getKey());
          }

        }

        vPrintDetails.add(strCondition3);
        // end TIR 6QKKNN
      }
      else if (strCondition1.indexOf(":FEATURE") > 0) {
        strCondition5 = strCondition1.substring(strCondition1.indexOf(":FEATURE") + 1);
        logMessage("A_155 in FEATURE:Parsed out" + strCondition5);
        eiFeature = egFeature.getEntityItem(strCondition5); //get the feature entity from the group using parsed key
        //Navigate to component and get Mkt message
        //  eiNextItem1 = getUplinkedEntityItem(eiFeature, strFeatureToCmpt);

        //strCondition3 =  getAttributeValue(eiNextItem1, "MKTGNAME", " ");

        //strCondition3 = (strCondition3.length()>0 ? strCondition3+" " : "")+getAttributeValue(eiNextItem1, "MKTGNAME", " ");
        strCondition3 = getAttributeValue(eiFeature, "MKTGNAME", " ");
        logMessage(":Feature :" + strCondition3);
        vPrintDetails.add(strCondition3);
        vPrintDetails.add(getAttributeValue(eiFeature, "FEATURENUMBER", "No Value" + eiFeature.getKey()));

        //Navigate to SOF from FEATURE to see whether SOF is linked to AVAIL before printing PROJnumber
        eiComponent = getUplinkedEntityItem(eiFeature, strFeatureToCmpt);
        // TIR 6QKKNN add autobahn project number
        strCondition3 = " ";
        if (eiComponent == null) {
          vReturnEntities1.removeAllElements();
        }
        else {

          eiSof = getUplinkedEntityItem(eiComponent, strCmptToSof);
          //Print Projnumber only if sof linked to avail
          vReturnEntities1 = searchEntityItemLink(eiSof, null, null, true, true, "SOFAVAIL");
        }
        if (vReturnEntities1.size() > 0) {
          //Navigate to OFDEVLPROJ and get PROJNUMBER
          //Navigation: ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E) -> CMPNTFEATURE (R ) -> CMPNT (E) - OFDEVLPROJCMPNT (R) - OFDEVLPROJ (E)

          vReturnEntities4 = searchEntityItemLink(eiComponent, null, null, true, false, "OFDEVLPROJCMPNT");
          if (vReturnEntities4.size() > 0) {
            eiNextItem2 = (EntityItem) vReturnEntities4.elementAt(0);
            eiNextItem3 = (EntityItem) eiNextItem2.getUpLink(0); //This will be the OFDEVLPROJ entity
            strCondition3 = getAttributeValue(eiNextItem3, "PROJNUMBER", "No Value" + eiNextItem3.getKey());
          }

        }

        vPrintDetails.add(strCondition3);
        // end TIR 6QKKNN
      }
    }

    if (vPrintDetails.size() > 0) {
      println("                                              Service/    Autobahn");
      println("Service Component/                            Component   Project");
      printReport(true, strHeader, iColWidths, vPrintDetails); //Print when processing is done
      resetPrintvars();
    }

    if (vReturnEntities3.size() > 0) {
      println(":exmp.");
    }

    println(".*$A_155_End");

    println(".*$A_156_Begin");
    strParamList1 = new String[] {
        "STANDARDAMENDTEXT"}; //Internal Letter, EMEA - Business Partner Terms and Conditions

    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "120", "", true);
    iColWidths = new int[] {
        69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_156_End");

    println(".*$A_157_Begin");
    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> SOFAVAIL (R) -> SOF (E) -> SOFCATINCL (R) -> CATINCL (E)
         If SOFCATINCL (R) exist and CATALOGNAME = '321' (ibm.com) then answer 'Yes' otherwise or
     */
    bConditionOK = false;
    vReturnEntities1 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFCATINCL");
    strFilterAttr = new String[] {
        "CATALOGNAME"};
    strFilterValue = new String[] {
        "321"};
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, strFilterAttr, strFilterValue, true, true, "CATINCL");
    if (vReturnEntities2.size() > 0) {
      bConditionOK = true;
    }
    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTCATINCL (R) -> CATINCL (E)
         If CMPNTCATINCL (R) exist and CATALOGNAME = '321' (ibm.com) then answer 'Yes' or
     */
    if (!bConditionOK) {
      vReturnEntities1 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTCATINCL");
      strFilterAttr = new String[] {
          "CATALOGNAME"};
      strFilterValue = new String[] {
          "321"};
      vReturnEntities3 = searchEntityVectorLink(vReturnEntities1, strFilterAttr, strFilterValue, true, true, "CATINCL");
      if (vReturnEntities3.size() > 0) {
        bConditionOK = true;
      }
    }
    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> FEATUREAVAIL (R) -> FEATURE (E) -> FEATURECATINCL (R) -> CATINCL (E)
         If FEATURECATINCL (R) exist and CATALOGNAME = '321' (ibm.com) then answer 'Yes' otherwise answer 'No' if none of the relators exist
     */
    if (!bConditionOK) {
      vReturnEntities1 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATURECATINCL");
      strFilterAttr = new String[] {
          "CATALOGNAME"};
      strFilterValue = new String[] {
          "321"};
      vReturnEntities4 = searchEntityVectorLink(vReturnEntities1, strFilterAttr, strFilterValue, true, true, "CATINCL");
      if (vReturnEntities4.size() > 0) {
        bConditionOK = true;
      }
    }
    if (bConditionOK) {
      println("Yes");
    }
    else {
      println("No");
    }

    println(".*$A_157_End");

    println(".*$A_158_Begin");
    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> SOFAVAIL (R) -> SOF (E) -> SOFCATINCL (R) -> CATINCL (E)
         When ANNOUNCEMENT (ANNCODENAME) = AVAIL (ANNCODENAME) and CATINCL = (CATALOGNAME = '321' (ibm.com))
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTCATINCL (R) -> CATINCL (E)
         When ANNOUNCEMENT (ANNCODENAME) = AVAIL (ANNCODENAME) and CATINCL = (CATALOGNAME = '321' (ibm.com))
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> FEATUREAVAIL (R) -> FEATURE (E) -> FEATURECATINCL (R) -> CATINCL (E)
         When ANNOUNCEMENT (ANNCODENAME) = AVAIL (ANNCODENAME) and CATINCL = (CATALOGNAME = '321' (ibm.com))
     */
    printQ158(bConditionOK);

    println(".*$A_158_End");

    println(".*$A_160_Begin");
    println(transformXML(getAttributeValue(eiAnnounce, "ACCESPEOWDISABLE", " ")));
    println(".*$A_160_End");

    println(".*$A_161_Begin");
    println(transformXML(getAttributeValue(eiAnnounce, "ACCESPEOWDISABLECONSID", " ")));
    println(".*$A_161_End");

    println(".*$A_162_Begin");
    println(transformXML(getAttributeValue(eiAnnounce, "USSEC508", " ")));
    println(".*$A_162_End");

    println(".*$A_163_Begin");
    println(transformXML(getAttributeValue(eiAnnounce, "USSEC508LOGO", " ")));
    println(".*$A_163_End");

    println(".*$A_170_Begin"); //Internal Letter -  SPoC

    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> SOFAVAIL (R) -> SOF (E) -> SOFGBTA (A) -> GBT (E)
     */
    vPrintDetails1 = new Vector();
    vPrintDetails2 = new Vector();
    vPrintDetails3 = new Vector();
    logMessage("170 vSofFrmSofAvail");
    displayContents(vSofFrmSofAvail);
    for (i = 0; i < vSofFrmSofAvail.size(); i++) {
      eiSof = (EntityItem) vSofFrmSofAvail.elementAt(i);

      vReturnEntities2 = searchEntityItemLink(eiSof, null, null, true, true, "SOFGBTA");
      logMessage("A_170_1");
      displayContents(vReturnEntities2);
      eiNextItem = null;
      eiNextItem1 = null;
      if (vReturnEntities2.size() > 0) {
        eiNextItem = (EntityItem) vReturnEntities2.elementAt(0);
        eiNextItem1 = (eiNextItem.getDownLinkCount() > 0 ? (EntityItem) eiNextItem.getDownLink(0) : null); //this will be the GBT entity (Wayne)
      }
      if (eiNextItem1 != null) {
        vPrintDetails.add(getAttributeValue(eiSof, "OFIDNUMBER", " "));
        vPrintDetails.add(getAttributeValue(eiNextItem1, "GBNAME", " "));
        vPrintDetails.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "SAPPRIMBRANDCODE", " ") : " ");

        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "SAPPRODFAMCODE", " ") : " ");
        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "OMBRANDCODE", " ") : " ");
        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "OMPRODFAMCODE", " ") : " ");
        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "BPDBBRANDCODE", " ") : " ");

        vPrintDetails2.add(getAttributeValue(eiSof, "OFIDNUMBER", " "));
        vPrintDetails2.add(getAttributeValue(eiSof, "MATACCGRP", " "));
        vPrintDetails2.add(getAttributeValue(eiSof, "ASSORTMODULE", " "));
        //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> SOFAVAIL (R) -> SOF (E) -> SOFOFDEVLPROJA (R) -> OFDEVLPROJ (E)
        vReturnEntities3 = searchEntityItemLink(eiSof, null, null, true, true, "SOFOFDEVLPROJA");
        if (vReturnEntities3.size() > 0) {
          eiNextItem = (EntityItem) vReturnEntities3.elementAt(0);
          eiNextItem1 = (eiNextItem.getDownLinkCount() > 0 ? (EntityItem) eiNextItem.getDownLink(0) : null); //this will be the OFDEVLPROJ entity
          vPrintDetails2.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "DEVDIV", " ") : " ");
        }
        else {
          vPrintDetails2.add(" ");
        }

      }
      //Lets get to PriceINfo
      //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> SOFAVAIL (R) -> SOF (E) -> SOFPRICEININFO (R) -> PRICEININFO (E)
      vReturnEntities3 = searchEntityItemLink(eiSof, null, null, true, true, "SOFPRICE");
      if (vReturnEntities3.size() > 0) {
        eiNextItem = (EntityItem) vReturnEntities3.elementAt(0);
        eiPriceInfo = (eiNextItem.getDownLinkCount() > 0 ? (EntityItem) eiNextItem.getDownLink(0) : null); //this will be the PRICEININFO entity
        vPrintDetails3.add(eiPriceInfo != null ? getAttributeValue(eiPriceInfo, "AMORTIZATIONSTART", " ") : " ");
        vPrintDetails3.add(eiPriceInfo != null ? getAttributeValue(eiPriceInfo, "AMORTIZATIONLENGTH", " ") : " ");
      }
    }

    //Now do the same from CMPNT
    logMessage("170 vCmpntFrmCmpntAvail");
    displayContents(vCmpntFrmCmpntAvail);
    for (i = 0; i < vCmpntFrmCmpntAvail.size(); i++) {
      eiComponent = (EntityItem) vCmpntFrmCmpntAvail.elementAt(i);

      //Now get to the SOF.
      eiNextItem = null;
      eiNextItem1 = null;
      eiSof = getUplinkedEntityItem(eiComponent, strCmptToSof);
      if (eiSof != null) {
        vReturnEntities2 = searchEntityItemLink(eiSof, null, null, true, true, "SOFGBTA");
        logMessage("A_170_2");
        displayContents(vReturnEntities2);
        if (vReturnEntities2.size() > 0) {
          eiNextItem = (EntityItem) vReturnEntities2.elementAt(0);
          eiNextItem1 = (eiNextItem.getDownLinkCount() > 0 ? (EntityItem) eiNextItem.getDownLink(0) : null); //this will be the GBT entity (Wayne)
        }
      }
      if (eiNextItem1 != null) {
        vPrintDetails.add(getAttributeValue(eiSof, "OFIDNUMBER", " "));
        vPrintDetails.add(getAttributeValue(eiNextItem1, "GBNAME", " "));
        vPrintDetails.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "SAPPRIMBRANDCODE", " ") : " ");

        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "SAPPRODFAMCODE", " ") : " ");
        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "OMBRANDCODE", " ") : " ");
        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "OMPRODFAMCODE", " ") : " ");
        vPrintDetails1.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "BPDBBRANDCODE", " ") : " ");

        vPrintDetails2.add(getAttributeValue(eiSof, "OFIDNUMBER", " "));
        vPrintDetails2.add(getAttributeValue(eiSof, "MATACCGRP", " "));
        vPrintDetails2.add(getAttributeValue(eiSof, "ASSORTMODULE", " "));
        //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> SOFAVAIL (R) -> SOF (E) -> SOFOFDEVLPROJA (R) -> OFDEVLPROJ (E)
        vReturnEntities3 = searchEntityItemLink(eiSof, null, null, true, true, "SOFOFDEVLPROJA");
        if (vReturnEntities3.size() > 0) {
          eiNextItem = (EntityItem) vReturnEntities3.elementAt(0);
          eiNextItem1 = (eiNextItem.getDownLinkCount() > 0 ? (EntityItem) eiNextItem.getDownLink(0) : null); //this will be the OFDEVLPROJ entity
          vPrintDetails2.add(eiNextItem1 != null ? getAttributeValue(eiNextItem1, "DEVDIV", " ") : " ");
        }
        else {
          vPrintDetails2.add(" ");
        }

      }
      //Lets get to PriceINfo
      //ANNOUNCEMENT (E) -> ANNAVAILA (A) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTPRICEININFO (R) -> PRICEININFO (E)
      vReturnEntities3 = searchEntityItemLink(eiComponent, null, null, true, true, "CMPNTPRICE");
      if (vReturnEntities3.size() > 0) {
        eiNextItem = (EntityItem) vReturnEntities3.elementAt(0);
        eiPriceInfo = (eiNextItem.getDownLinkCount() > 0 ? (EntityItem) eiNextItem.getDownLink(0) : null); //this will be the PRICEININFO entity
        vPrintDetails3.add(eiPriceInfo != null ? getAttributeValue(eiPriceInfo, "AMORTIZATIONSTART", " ") : " ");
        vPrintDetails3.add(eiPriceInfo != null ? getAttributeValue(eiPriceInfo, "AMORTIZATIONLENGTH", " ") : " ");
      }
    }

    //And FEATURE  Feature has been deleted for this release


    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      println("Offering Id          Description                        Primary Brand");
      strHeader = new String[] {
          "", "", "   Code"};
      iColWidths = new int[] {
          19, 34, 14};
      printReport(true, strHeader, iColWidths, vPrintDetails);
      resetPrintvars();
      println("");
      println("");
      println("Product Family OM Brand OM Product Family BPDP Brand");
      strHeader = new String[] {
          "   Code", "Code", "     Code", "   Code"};
      iColWidths = new int[] {
          14, 8, 17, 10};
      printReport(true, strHeader, iColWidths, vPrintDetails1);
      vPrintDetails1 = new Vector();
      println("");
      println("");
      println("                    Material Account   Assortment Development");
      strHeader = new String[] {
          "Offering ID", "Assignment Group", "  Module", " Division"};
      iColWidths = new int[] {
          19, 17, 11, 11};
      printReport(true, strHeader, iColWidths, vPrintDetails2);
      vPrintDetails2 = new Vector();

      if (vPrintDetails3.size() > 0) {
        println("");
        println("");
        strHeader = new String[] {
            "Amortization Start", "Amortization Length"};
        iColWidths = new int[] {
            18, 20};
        printReport(true, strHeader, iColWidths, vPrintDetails3);
        vPrintDetails3 = new Vector();
      }
      println(":exmp.");
    }

    println(".*$A_170_End");

    println(".*$A_181_Begin"); //Internal Letter, Supplemental Information - Marketing Information - Global Financing available?
    /*
         CMPNT (E) -> CMPNTFINOF (R) -> FINOF
         If CMPNTFINOF exists or FEATUREFINOF exists, then answer = 'Yes'., otherwise answer = No'
         FEATURE (E) -> FEATUREFINOF (R) -> FINOF
         If CMPNTFINOF exists or FEATUREFINOF exists, then answer = 'Yes'., otherwise answer = No'
     */
    bConditionOK = false;
    vReturnEntities1 = searchEntityGroupLink(egComponent, null, null, true, true, "CMPNTFINOF");
    if (vReturnEntities1.size() > 0) {
      bConditionOK = true;
    }
    else {
      vReturnEntities1 = searchEntityGroupLink(egFeature, null, null, true, true, "FEATUREFINOF");
      if (vReturnEntities1.size() > 0) {
        bConditionOK = true;
      }
    }
    if (bConditionOK) {
      println("Yes");
    }
    else {
      println("No");
    }

    println(".*$A_181_End");

    println(".*$A_182_Begin"); //Internal Letter - Marketing Information - Global Financing - Marketing Messages for Internal Letter
    printIntSuppMktFinInfo("MKTGMSGINTERNAL");
    println(".*$A_182_End");

    println(".*$A_183_Begin"); //Supplemental Information - Marketing Information - Global Financing - Marketing Messages for External Letter
    printIntSuppMktFinInfo("MKTGMESEXTERNAL");
    println(".*$A_183_End");

    println(".*$A_184_Begin");

    /*
         CMPNT (E) -> CMPNTFINOF (R) -> FINOF
         If PROMOELIGIBILITYTCS exits, then aswer ='YES' otherwise answer = 'No"
         FEATURE (E) -> FEATUREFINOF (R) -> FINOF
     */
    resetPrintvars();
    bConditionOK = false;
    strParamList1 = new String[] {
        "PROMOELIGIBILITYTCS"};
    vReturnEntities1 = searchEntityGroupLink(egComponent, null, null, true, true, "CMPNTFINOF");
    if (vReturnEntities1.size() > 0) {
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "FINOF");
      printValueListInVector(vReturnEntities3, strParamList1, " ", true, false);
      if (vPrintDetails.size() > 0) {
        bConditionOK = true;
      }
    }
    else {
      vReturnEntities1 = searchEntityGroupLink(egFeature, null, null, true, true, "FEATUREFINOF");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "FINOF");
      printValueListInVector(vReturnEntities3, strParamList1, " ", true, false);
      if (vPrintDetails.size() > 0) {
        bConditionOK = true;
      }
    }
    if (bConditionOK) {
      println("Yes");
    }
    else {
      println("No");
    }
    resetPrintvars();
    println(".*$A_184_End");

    println(".*$A_185_Begin"); //Internal Letter, Supplemental Information - Marketing Information - Global Financing - Qualifying Ts & Cs of Promotion
    //CMPNT (E) -> CMPNTFINOF (R) -> FINOF
    //FEATURE (E) -> FEATUREFINOF (R) -> FINOF
    printIntSuppMktFinInfo("PROMOELIGIBILITYTCS");

    println(".*$A_185_End");

    println(".*$A_186_Begin");
    /*
         CMPNT (E) -> CMPNTFINOF (R) -> FINOF
         If MONTHLYPAYMENT exits, then aswer ='YES' otherwise answer = 'No"
         FEATURE (E) -> FEATUREFINOF (R) -> FINOF
     */
    resetPrintvars();
    bConditionOK = false;
    strParamList1 = new String[] {
        "MONTHLYPAYMENT"};
    vReturnEntities1 = searchEntityGroupLink(egComponent, null, null, true, true, "CMPNTFINOF");
    if (vReturnEntities1.size() > 0) {
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "FINOF");
      printValueListInVector(vReturnEntities3, strParamList1, " ", true, false);
      if (vPrintDetails.size() > 0) {
        bConditionOK = true;
      }
    }
    else {
      vReturnEntities1 = searchEntityGroupLink(egFeature, null, null, true, true, "FEATUREFINOF");
      vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "FINOF");
      printValueListInVector(vReturnEntities3, strParamList1, " ", true, false);
      if (vPrintDetails.size() > 0) {
        bConditionOK = true;
      }
    }
    if (bConditionOK) {
      println("Yes");
    }
    else {
      println("No");
    }
    resetPrintvars();

    println(".*$A_186_End");

    println(".*$A_187_Begin"); //Internal Letter, Supplemental Information - Marketing Information - Global Financing - Monthly Payment
    printIntSuppMktFinInfo("MONTHLYPAYMENT", false);

    println(".*$A_187_End");

    println(".*$A_188_Begin"); //Internal Letter, Supplemental Information - Marketing Information - Global Financing - Term
    //CMPNT (E) -> CMPNTFINOF (R) -> FINOF
    //FEATURE (E) -> FEATUREFINOF (R) -> FINOF
    printIntSuppMktFinInfo("PAYMENTTERM", false);

    println(".*$A_188_End");

    println(".*$A_189_Begin"); //Internal Letter, Supplemental Information - Marketing Information - Global Financing - Qualifying Ts & Cs
    printIntSuppMktFinInfo("ELIGIBILITYTCS");
    println(".*$A_189_End");

    println(".*$A_195_Begin");
    strParamList1 = new String[] {
        "STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "200", "", true);
    iColWidths = new int[] {
        69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_195_End");

    // TIR 6QKKNN
    println(".*$A_200_Begin"); //WWCL - At a Glance
    prettyPrint(transformXML(getAttributeValue(eiAnnounce, "ATAGLANCE", " ")), 69);
    println(".*$A_200_End");

    // TIR 6QKKNN
    println(".*$A_202_Begin"); //Fact Sheet, Release Memo, Internal Letter, WW Customer Announcement Letter, IBM PCD Letter, Business Partner Attachment - Overview
    prettyPrint(transformXML(getAttributeValue(eiAnnounce, "OVERVIEWABSTRACT", " ")), 69);
    printVanillaSVSReport("OVERVIEWABSTRACT", true, false);
    println(".*$A_202_End");

    println(".*$A_204_Begin"); //WWCAL, Pre-requisties and Co-requisties
    printVanillaSVSReport("PREREQCOREQ", true, false);
    println(".*$A_204_End");

    println(".*$A_208_Begin"); //Fact Letter, Internal Letter, WW Customer Announcement Letter - Planned Availability Date
    /*
     ANNOUNCEMENT (E) -> ANNAVAILA (A) ->AVAIL (E) when AVAILTYPE = '146' (Planned Availibility) -> SOFAVAIL (R) -> SOF (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) ->AVAIL (E) when AVAILTYPE = '146' (Planned Availibility) -> CMPNTAVAIL (R) -> CMPNT (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) ->AVAIL (E) when AVAILTYPE = '146' (Planned Availibility) -> FEATUREAVAIL (R) -> FEATURE (E)
     */
    //ANNOUNCEMENT (E) -> ANNAVAILA (A) ->AVAIL (E) when AVAILTYPE = '146' (Planned Availibility) -> SOFAVAIL (R) -> SOF (E)
    strFilterAttr = new String[] {
        "AVAILTYPE"};
    strFilterValue = new String[] {
        "146"};
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, strFilterAttr, strFilterValue, true, true, "AVAIL");
    logMessage("****AVAIL*****");
    displayContents(vReturnEntities1);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "SOFAVAIL");
    displayContents(vReturnEntities1);
    strCondition1 = "";
    strCondition2 = "";
    strCondition4 = "";
    vReturnEntities4 = new Vector();
    iTemp = vSofFrmSofAvail.size() + vCmpntFrmCmpntAvail.size() + vFeatureFrmFeatureAvail.size();
    logMessage("_208:Array Size=" + iTemp);

    //strAnswers = new String [iTemp];    //Set the size of the array here
    i = 0;
    logMessage("_208:SOFAVAIL");
    displayContents(vReturnEntities2);
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities2.elementAt(i); //this is SOFAVAIL
      eiSof = (EntityItem) eiNextItem.getUpLink(0);
      eiAvail = (EntityItem) eiNextItem.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);
      strCondition4 = getAttributeValue(eiAvail, "EFFECTIVEDATE", " ");
      strCondition3 = getSOFMktName(eiSof);
//      strAnswers[i] = strCondition4+"|"+strCondition3+"|"+strCondition1;
      vReturnEntities4.add(strCondition4 + "|" + strCondition3 + "|" + strCondition1);
    }
    j = (i > 0) ? i - 1 : i;
    logMessage("_208:After SOFAVAIL Ctr=" + j);

    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "CMPNTAVAIL");
    strCondition1 = "";
    strCondition2 = "";
    strCondition4 = "";
    logMessage("_208:CMPNTAVAIL");
    displayContents(vReturnEntities2);
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities2.elementAt(i); //this is CMPNTAVAIL
      eiComponent = (EntityItem) eiNextItem.getUpLink(0);
      eiAvail = (EntityItem) eiNextItem.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);
      strCondition4 = getAttributeValue(eiAvail, "EFFECTIVEDATE", " ");
      strCondition3 = getCmptToSofMktMsg(eiComponent);
//      strAnswers[i+j] = strCondition4+"|"+strCondition3+"|"+strCondition1;
      vReturnEntities4.add(strCondition4 + "|" + strCondition3 + "|" + strCondition1);
    }
    j = ( (i + j) > 0) ? (i + j) - 1 : 0;
    logMessage("_208:After CMPNTAVAIL Ctr=" + j);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "FEATUREAVAIL");
    strCondition1 = "";
    strCondition2 = "";
    strCondition4 = "";
    logMessage("_208:FEATUREAVAIL");
    displayContents(vReturnEntities2);
    for (i = 0; i < vReturnEntities2.size(); i++) {
      eiNextItem = (EntityItem) vReturnEntities2.elementAt(i); //this is FEATUREAVAIL
      logMessage("_208:FEATUREAVAIL" + eiNextItem.getKey());
      eiFeature = (EntityItem) eiNextItem.getUpLink(0);
      logMessage("_208:FEATURE" + eiAvail.getKey());
      eiAvail = (EntityItem) eiNextItem.getDownLink(0);
      strCondition1 = getGeoTags(eiAvail);
      strCondition4 = getAttributeValue(eiAvail, "EFFECTIVEDATE", " ");
      strCondition3 = getfeatureToSofMktMsg(eiFeature);
      logMessage("_208: FEATUREAVAIL Setting =" + (i + j));
      //strAnswers[i+j] = strCondition4+"|"+strCondition3+"|"+strCondition1;
      vReturnEntities4.add(strCondition4 + "|" + strCondition3 + "|" + strCondition1);
    }
    strAnswers = vReturnEntities4.toArray(); //Convert back into array
    //Sort the Array now
    Arrays.sort(strAnswers);
    strCondition5 = "";
    resetPrintvars();

    for (i = 0; i < strAnswers.length; i++) {
      strCondition1 = (String) strAnswers[i];
      logMessage("String stored at " + i + ":" + strCondition1);
      iTemp = strCondition1.indexOf("|");
      strCondition2 = strCondition1.substring(0, iTemp);
      strCondition1 = strCondition1.substring(iTemp + 1);
      logMessage("Found | at   " + iTemp);
      logMessage("Parsed Date  " + strCondition2);
      iTemp = strCondition1.indexOf("|");
      strCondition3 = strCondition1.substring(0, iTemp);
      strCondition1 = strCondition1.substring(iTemp + 1);
      logMessage("Found | at   " + iTemp);
      logMessage("Parsed  MktName " + strCondition3);

      strCondition4 = strCondition1;
      logMessage("Parsed GEO  " + strCondition4);

    }

    strCondition6 = "";
    if (strAnswers.length > 0) {
      println(":xmp.");
      println(".kp off");
      //println(".sp");
    }
    int iTemp = 0; //to check for number of marketingname within a geotag/date
    for (i = 0; i < strAnswers.length; i++) {
      strCondition1 = (String) strAnswers[i];
      logMessage("String stored at " + i + ":" + strCondition1);
      iTemp = strCondition1.indexOf("|");
      strCondition2 = strCondition1.substring(0, iTemp);
      strCondition1 = strCondition1.substring(iTemp + 1);
      logMessage("Found | at   " + iTemp);
      logMessage("Parsed Date  " + strCondition2);
      iTemp = strCondition1.indexOf("|");
      strCondition3 = strCondition1.substring(0, iTemp);
      strCondition1 = strCondition1.substring(iTemp + 1);
      logMessage("Found | at   " + iTemp);
      logMessage("Parsed  MktName " + strCondition3);

      strCondition4 = strCondition1;
      logMessage("Parsed GEO  " + strCondition4);

      if (!strCondition4.equals(strCondition5)) {
        if (!strCondition5.equals(strWorldwideTag)) {
          if (strCondition5.trim().length() > 0) {
            logMessage("Ending GEO Break  " + strCondition5);
            println(".br;:hp2.<---" + strCondition5 + ":ehp2.");
            println("");
          }
        }

        if (!strCondition4.equals(strWorldwideTag)) {
          if (strCondition4.trim().length() > 0) {
            logMessage("Starting GEO Break  " + strCondition4);
            println(":p.:hp2." + strCondition4 + "--->:ehp2.");
            iTemp = 0; //Reset the counter to 0 for each geo break
            strCondition6 = strCondition2;
            println(getRFADateFormat(strCondition2) + ""); //Date
          }
        }
        strCondition5 = strCondition4;
      }

      if (!strCondition6.equals(strCondition2)) { //Check for Date break
        strCondition6 = strCondition2;
        if (iTemp == 1) {
          println(".p"); //print if only 1 instance has printed for previous date
        }
        iTemp = 0;
        println(getRFADateFormat(strCondition2) + "");
      }

      prettyPrint(strCondition3, 69);
      iTemp++;

    }
    if (!strCondition5.equals(strWorldwideTag)) {
      if (strCondition5.trim().length() > 0) {
        logMessage("Final Ending GEO Break  " + strCondition5);
        println(".br;:hp2.<---" + strCondition5 + ":ehp2.");
        println("");
      }
    }
    if (strAnswers.length > 0) {
      println(":exmp.");
      resetPrintvars();
    }

    println(".*$A_208_End");

    println(".*$A_210_Begin"); //WW Customer Announcement Letter - Description

    prettyPrint(transformXML(getAttributeValue(eiAnnounce, "DESCRIPTION", " ")), 69);
    printVanillaSVSReport("DESCRIPTION", true, false);
    println(".*$A_210_End");

    println(".*$A_212_Begin"); //Supplemental Information - Discretionary Information

    strParamList1 = new String[] {
        "STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "130", "", true);
    iColWidths = new int[] {
        69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_212_End");

    println(".*$A_214_Begin"); //Supplemental Information - Charges
    printVanillaSVSReport("CHARGES", true, true);

    println(".*$A_214_End");

    println(".*$A_218_Begin");
    printVanillaSVSReport("BILLINGPERIOD", false, true);
    //printIntSuppMktFinInfo("BILLINGPERIOD");
    println(".*$A_218_End");

    println(".*$A_219_Begin");
    GeneralAreaGroup geGrEmea = m_geList.getRfaGeoEMEAInclusion(eiAnnounce);
    logMessage("A_219_Begin returned GEItemcount" + geGrEmea.getGeneralAreaItemCount());
    vReturnEntities1 = searchEntityGroupLink(grpAnnAvail, null, null, true, true, "AVAIL");
    displayContents(vReturnEntities1);

    //Process all the avails to get their respective effective dates and the countries selected in them
    vReturnEntities2 = new Vector();
    bConditionOK=false;
    //if (m_geList.isRfaGeoEMEA(eiAnnounce) && vReturnEntities1.size() > 0) {   //dont need this as we are looking for specific countries
      if ( vReturnEntities1.size() > 0) {
      Hashtable htCountries = new Hashtable();
      for (int y = 0; y < vReturnEntities1.size(); y++) {
        eiAvail = (EntityItem) vReturnEntities1.elementAt(y);

        strCondition2 = getRFADateFormat(getAttributeValue(eiAvail, "EFFECTIVEDATE", ""));
        if (y==0) {
          strCondition6=strCondition2;
        } else if (!strCondition2.equals(strCondition6)) {
          bConditionOK = true;            //Flag to print all the dates
        }
        strCondition3 = getAttributeLongFlagDesc(eiAvail, "COUNTRYLIST");
        if (strCondition3 == null) {
          break;
        }
        StringTokenizer st = new StringTokenizer(strCondition3, "|");

        for (int i = 0;  st.hasMoreTokens(); i++) {

          strCondition1 = st.nextToken().trim();          //Take the spaces out
          logMessage("_219:Got Country:" + i + ":" + strCondition1);
          if (strCondition1.compareToIgnoreCase("Austria") == 0 || strCondition1.compareToIgnoreCase("Belgium") == 0 ||
              strCondition1.compareToIgnoreCase("Bulgaria") == 0 || strCondition1.compareToIgnoreCase("Croatia") == 0 ||
              strCondition1.compareToIgnoreCase("Czech Republic") == 0 ||
              strCondition1.compareToIgnoreCase("Denmark") == 0 || strCondition1.compareToIgnoreCase("Finland") == 0 ||
              strCondition1.compareToIgnoreCase("France") == 0 || strCondition1.compareToIgnoreCase("Germany") == 0 ||
              strCondition1.compareToIgnoreCase("Greece") == 0 || strCondition1.compareToIgnoreCase("Hungary") == 0 ||
              strCondition1.compareToIgnoreCase("Ireland") == 0 || strCondition1.compareToIgnoreCase("Israel") == 0 ||
              strCondition1.compareToIgnoreCase("Italy") == 0 || strCondition1.compareToIgnoreCase("Luxembourg") == 0 ||
              strCondition1.compareToIgnoreCase("Netherlands") == 0 || strCondition1.compareToIgnoreCase("Norway") == 0 ||
              strCondition1.compareToIgnoreCase("Poland") == 0 || strCondition1.compareToIgnoreCase("Portugal") == 0 ||
              strCondition1.compareToIgnoreCase("Romania") == 0 || strCondition1.compareToIgnoreCase("Russian Federation") == 0 ||
              strCondition1.compareToIgnoreCase("Slovakia") == 0 || strCondition1.compareToIgnoreCase("Slovenia") == 0 ||
              strCondition1.compareToIgnoreCase("South Africa") == 0 || strCondition1.compareToIgnoreCase("Spain") == 0 ||
              strCondition1.compareToIgnoreCase("Switzerland") == 0 || strCondition1.compareToIgnoreCase("Sweden") == 0 ||
              strCondition1.compareToIgnoreCase("Turkey") == 0 ||
              strCondition1.compareToIgnoreCase("United Kingdom") == 0) {
            if (strCondition1.compareToIgnoreCase("United Kingdom") == 0) {
              vReturnEntities2.add("United Kingdom**");
              htCountries.put("United Kingdom**", strCondition2);
            }
            else if (strCondition1.compareToIgnoreCase("France") == 0) {
              vReturnEntities2.add("France*");
              htCountries.put("France*", strCondition2);
            }
            else {
              vReturnEntities2.add(strCondition1);
              htCountries.put(strCondition1, strCondition2);

            }

            logMessage("_219:MATCHED Country" + strCondition1);
          }

        }
      }
      vReturnEntities3 = mySort.alphabetizeVector(vReturnEntities2); //Sort the countries selected
      strCondition1="";
      for (i = 0; i < vReturnEntities3.size(); i++) {
        vPrintDetails.add( (String) vReturnEntities3.elementAt(i));
        strCondition2 = (String) htCountries.get( (String) vReturnEntities3.elementAt(i));
        if (!bConditionOK) { //Break when flag shows dates are different
          vPrintDetails.add("");
        }
        else {
          vPrintDetails.add(strCondition2);
        }

      }

      strHeader = new String[] {
          "Country", "Availability Date"};
      iColWidths = new int[] {
          35, 17};
      if (vPrintDetails.size() > 0) {
        println(":xmp.");
        println(".kp off");
        printReport(true, strHeader, iColWidths, vPrintDetails);
        resetPrintvars();
        println("* Except overseas territories");
        println("** UK Mainland Only");
        println(":exmp.");
      }

    }
    println(".*$A_219_End");

    println(".*$A_220_Begin");
    println("AP DISTRIBUTION:  TO ALL ASIA PACIFIC COUNTRIES FOR RELEASE.");
    TreeMap tmDistribution = new TreeMap();
    tmDistribution.put("ASEAN *", "No");
    tmDistribution.put("India/South Asia**", "No");
    tmDistribution.put("AUSTRALIA", "No");
    tmDistribution.put("People's Republic of China", "No");
    tmDistribution.put("HONG KONG S.A.R of the PRC", "No");
    tmDistribution.put("Macao S.A.R of the PRC", "No");
    tmDistribution.put("TAIWAN", "No");
    tmDistribution.put("KOREA", "No");
    tmDistribution.put("JAPAN", "No");
    tmDistribution.put("NEW ZEALAND", "No");

    if (m_geList.isRfaGeoAP(eiAnnounce)) {
      GeneralAreaGroup geGrAP = m_geList.getRfaGeoAPInclusion(eiAnnounce);
      for (int i = 0; i < geGrAP.getGeneralAreaItemCount(); i++) {
        GeneralAreaItem gai = geGrAP.getGeneralAreaItem(i);
        strCondition1 = gai.getName().toUpperCase();
        logMessage("_220:Found country" + strCondition1);
        if (strCondition1.equals("DARUSSALAM") || strCondition1.equals("BRUNEI") || strCondition1.equals("MYANMAR") ||
            strCondition1.equals("MALAYSIA") || strCondition1.equals("PHILIPPINES") || strCondition1.equals("SINGAPORE") ||
            strCondition1.equals("CAMBODIA") || strCondition1.equals("LAO PEOPLES DEMOCRATIC REPUBLIC") ||
            strCondition1.equals("THAILAND") || strCondition1.equals("VIETNAM")) {
          tmDistribution.put("ASEAN *", "Yes");
          //strCondition1.equals("SRI LANKA") || strCondition1.equals("INDIA") || strCondition1.equals("INDONESIA") ||
        }
        else if ( (strCondition1.equals("KOREA, REPUBLIC OF")) ||
                 (strCondition1.equals("KOREA, DEMOCRATIC PEOPLES REPUBLIC OF"))) {
          tmDistribution.put("KOREA", "Yes");
        }
        else if (  strCondition1.equals("MALDIVES") || strCondition1.equals("AFGHANISTAN") ||
                  strCondition1.equals("SRI LANKA") || strCondition1.equals("INDIA") || strCondition1.equals("BANGLADESH") ||
                 strCondition1.equals("NEPAL") ||(strCondition1.equals("BHUTAN"))) {
          tmDistribution.put("India/South Asia**", "Yes");
        }

        else if ( (strCondition1.equals("HONG KONG"))) {
          tmDistribution.put("HONG KONG S.A.R of the PRC", "Yes");
        }
        else if ( (strCondition1.equals("MACAO"))) {
          tmDistribution.put("Macao S.A.R of the PRC", "Yes");
        }

        else if ( (strCondition1.equals("CHINA"))) {
          tmDistribution.put("People's Republic of China", "Yes");
        }
        else if ( (strCondition1.equals("TAIWAN, PROVINCE OF CHINA"))) {
          tmDistribution.put("TAIWAN", "Yes");
        }
        else if (tmDistribution.containsKey(strCondition1)) {
          tmDistribution.put(strCondition1, "Yes");
        }
      }
    }
    Set hKeys = tmDistribution.keySet();
    Iterator ikeys = hKeys.iterator();
    resetPrintvars();
    while (ikeys.hasNext()) {
      strCondition1 = (String) ikeys.next();
      vPrintDetails.add(strCondition1);
      vPrintDetails.add( (String) tmDistribution.get(strCondition1));
    }
    tmDistribution.clear();
    tmDistribution = null;
    strHeader = new String[] {
        "CTRY/Region", "ANNOUNCED"};
    iColWidths = new int[] {
        32, 20};
    println(":xmp.");

    printReport(true, strHeader, iColWidths, vPrintDetails);
    resetPrintvars();
    println("* Brunei Darussalam, Indonesia, Cambodia, Lao People's Democratic ");
    println("Republic, Myanmar, Malaysia, Philippines, Singapore, Thailand, Vietnam ");
    println("**Bangladesh, Bhutan, India, Sri Lanka, Maldives, Nepal, Afghanistan ");
    println(":exmp.");

    println(".*$A_220_End");

  }

  /**
   *  Description of the Method
   */
  private void processLongTo300() {
    println(".*$A_300_Begin");
    prettyPrint(getAttributeValue(eiAnnounce, "AMCALLCENTER", " "), 69);
    println(".*$A_300_End");
  }

  /**
   *  Description of the Method
   */
  private void processLongTo400() {

  }

  /**
   *  Description of the Method
   */
  private void processLongTo500() {

  }

  /**
   *  Description of the Method
   */
  private void processLongTo600() {

  }

  /**
   *  Description of the Method
   */
  private void processLongTo700() { //IBM Personal Computing Division Letter

    println(".*$A_725_Begin");

    prettyPrint(transformXML(getAttributeValue(eiAnnounce, "IBMGRENLTR", " ")), 69);

    println(".*$A_725_End");

    println(".*$A_726_Begin");
    prettyPrint(transformXML(getAttributeValue(eiAnnounce, "LENOVOGRENLTR", " ")), 69);
    println(".*$A_726_End");

  }

  /**
   *  Description of the Method
   */
  private void processLongTo800() {

    println(".*$A_800_Begin"); //Business Partner Attachment
    strParamList1 = new String[] {
        "STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "150", "", true);
    iColWidths = new int[] {
        69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();

    /**
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E) -> SOFPRICE (R) -> PRICEINFO (E)
     */

    vReturnEntities5 = new Vector();
    vReturnEntities1 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFPRICE");
    vReturnEntities5.addAll(vReturnEntities1);
    vReturnEntities1 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTPRICE");
    vReturnEntities5.addAll(vReturnEntities1);
    vReturnEntities1 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATUREPRICE");
    vReturnEntities5.addAll(vReturnEntities1);
    if (vReturnEntities5.size() > 0) {
      logMessage("A_800:SOFPRICE");
      strCondition1 = "";
      for (i = 0; i < vSofFrmSofAvail.size(); i++) {
        eiSof = (EntityItem) vSofFrmSofAvail.elementAt(i);
        logMessage("_800" + eiSof.getKey());
        eiPriceInfo = getDownlinkedEntityItem(eiSof, new String[] {"SOFPRICE", "PRICEFININFO"});
        if (eiPriceInfo == null) {
          continue;
        }
        logMessage("_800" + eiPriceInfo.getKey());

        //Check whether the attrs are populated before we print
        strCondition1 = getAttributeValue(eiPriceInfo, "LPFEE", " ");
        strCondition1 += getAttributeValue(eiPriceInfo, "CONTRACTCLOSEFEE", " ");
        strCondition1 += getAttributeValue(eiPriceInfo, "REMKTGDISCOUNT", " ");
        strCondition1 += getAttributeValue(eiSof, "DISTRCODE", " ");
        //strCondition1 += getAttributeValue(eiComponent, "SVCCAT", " ");
        strCondition1 += getAttributeValue(eiSof, "VAE", " ");
        logMessage("_800 Value of strCondition1:" + strCondition1 + ":");
        if (strCondition1.trim().length() > 0) {
          vPrintDetails.add(getAttributeValue(eiSof, "MKTGNAME", " "));
          vPrintDetails.add(getAttributeValue(eiSof, "OFIDNUMBER", " "));
          vPrintDetails.add(getAttributeValue(eiSof, "MKTGNAME", " "));
          vPrintDetails.add(getAttributeValue(eiPriceInfo, "LPFEE", " "));
          vPrintDetails.add(getAttributeValue(eiPriceInfo, "CONTRACTCLOSEFEE", " "));
          vPrintDetails.add(getAttributeValue(eiPriceInfo, "REMKTGDISCOUNT", " "));
          vPrintDetails.add(getAttributeValue(eiSof, "DISTRCODE", " "));
          //vPrintDetails.add(getAttributeValue(eiComponent, "SVCCAT", " "));
          vPrintDetails.add(getAttributeValue(eiSof, "VAE", " "));
        }
        else {
          logMessage("_800:Skipping " + strCondition1 + " for " + eiSof.getKey() + ":Downlinked:" + eiPriceInfo.getKey());
        }
      }

      /*
           ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) -> SOFCMPT (R) -> SOF (E)
       ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E) -> CMPNTPRICE (R) -> PRICEINFO (E)
       */
      //Check whether we have downlinked pricefininfo rows, if not dont print
      vReturnEntities1 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTPRICE");
      logMessage("A_800:CMPNTPRICE");
      if (vReturnEntities1.size() > 0) {
        strCondition1 = "";
        for (i = 0; i < vCmpntFrmCmpntAvail.size(); i++) {
          eiComponent = (EntityItem) vCmpntFrmCmpntAvail.elementAt(i);
          logMessage("_800" + eiComponent.getKey());
          eiSof = getUplinkedEntityItem(eiComponent, new String[] {"SOFCMPNT", "SOF"});
          if (eiSof == null) {
            logMessage("_800 No linked SOF found from " + eiComponent.getKey());
          }
          else {
            logMessage("_800" + eiSof.getKey());
          }
          eiPriceInfo = getDownlinkedEntityItem(eiComponent, new String[] {"CMPNTPRICE", "PRICEFININFO"});
          if (eiPriceInfo == null) {
            continue;
          }
          logMessage("_800" + eiPriceInfo.getKey());

          //Check whether the attrs are populated before we print
          strCondition1 = getAttributeValue(eiPriceInfo, "LPFEE", " ");
          strCondition1 += getAttributeValue(eiPriceInfo, "CONTRACTCLOSEFEE", " ");
          strCondition1 += getAttributeValue(eiPriceInfo, "REMKTGDISCOUNT", " ");
          strCondition1 += getAttributeValue(eiComponent, "DISTRCODE", " ");
          //strCondition1 += getAttributeValue(eiComponent, "SVCCAT", " ");
          strCondition1 += getAttributeValue(eiComponent, "VAE", " ");
          if (strCondition1.trim().length() > 0) {
            vPrintDetails.add(getQ800SOFMktName(eiComponent, eiSof));
            if (bIsAnnITS) {
              strCondition2 = getAttributeShortFlagDesc(eiComponent, "ITSCMPNTCATNAME");
              if (strCondition2 == null) {
                strCondition2 = "";
              }
              if (strCondition2.trim().length() > 0) {
                vPrintDetails.add(getDownlinkedEntityAttrValue(eiComponent, new String[] {"CMPNTITSCMPNTCATA",
                    "ITSCMPNTCAT"}, "ITSCMPNTCATID"));
              }
              else {
                vPrintDetails.add(getAttributeValue(eiSof, "OFIDNUMBER", " "));
              }
            }
            else {
              vPrintDetails.add(getAttributeValue(eiSof, "OFIDNUMBER", " "));
            }
            strCondition1 = getCmptToSofMktMsg(eiComponent);
            vPrintDetails.add(strCondition1);
            vPrintDetails.add(getAttributeValue(eiPriceInfo, "LPFEE", " "));
            vPrintDetails.add(getAttributeValue(eiPriceInfo, "CONTRACTCLOSEFEE", " "));
            vPrintDetails.add(getAttributeValue(eiPriceInfo, "REMKTGDISCOUNT", " "));
            vPrintDetails.add(getAttributeValue(eiComponent, "DISTRCODE", " "));
            //vPrintDetails.add(getAttributeValue(eiComponent, "SVCCAT", " "));
            vPrintDetails.add(getAttributeValue(eiComponent, "VAE", " "));
          }
          else {
            logMessage("_800:Skipping " + strCondition1 + " for " + eiComponent.getKey() + ":Downlinked:" +
                       eiPriceInfo.getKey());
          }
        }
      }

      vReturnEntities1 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATUREPRICE");
      if (vReturnEntities1.size() > 0) {
        strCondition1 = "";
        for (i = 0; i < vFeatureFrmFeatureAvail.size(); i++) {
          eiFeature = (EntityItem) vFeatureFrmFeatureAvail.elementAt(i);
          logMessage("_800" + eiFeature.getKey());
          eiSof = getUplinkedEntityItem(eiFeature, strFeatureToSof);
          if (eiSof == null) {
            logMessage("_800 No linked SOF found from " + eiFeature.getKey());
          }
          else {
            logMessage("_800" + eiSof.getKey());
          }
          eiComponent = getUplinkedEntityItem(eiFeature, strFeatureToCmpt);
          logMessage("_800" + eiComponent.getKey());

          eiPriceInfo = getDownlinkedEntityItem(eiFeature, new String[] {"FEATUREPRICE", "PRICEFININFO"});
          if (eiPriceInfo == null) {
            continue;
          }
          logMessage("_800" + eiPriceInfo.getKey());

          //Check whether the attrs are populated before we print
          strCondition1 = getAttributeValue(eiPriceInfo, "LPFEE", " ");
          strCondition1 += getAttributeValue(eiPriceInfo, "CONTRACTCLOSEFEE", " ");
          strCondition1 += getAttributeValue(eiPriceInfo, "REMKTGDISCOUNT", " ");
          strCondition1 += getAttributeValue(eiComponent, "DISTRCODE", " ");
          //strCondition1 += getAttributeValue(eiComponent, "SVCCAT", " ");
          strCondition1 += getAttributeValue(eiComponent, "VAE", " ");
          if (strCondition1.trim().length() > 0) {
            vPrintDetails.add(getQ800SOFMktName(eiComponent, eiSof));
            if (bIsAnnITS) {
              strCondition2 = getAttributeShortFlagDesc(eiComponent, "ITSCMPNTCATNAME");
              if (strCondition2 == null) {
                strCondition2 = "";
              }
              if (strCondition2.trim().length() > 0) {
                vPrintDetails.add(getDownlinkedEntityAttrValue(eiComponent, new String[] {"CMPNTITSCMPNTCATA",
                    "ITSCMPNTCAT"}, "ITSCMPNTCATID"));
              }
              else {
                vPrintDetails.add(getAttributeValue(eiSof, "OFIDNUMBER", " "));
              }
            }
            else {
              vPrintDetails.add(getAttributeValue(eiSof, "OFIDNUMBER", " "));
            }
            strCondition1 = getCmptToSofMktMsg(eiComponent);
            vPrintDetails.add(strCondition1);
            vPrintDetails.add(getAttributeValue(eiPriceInfo, "LPFEE", " "));
            vPrintDetails.add(getAttributeValue(eiPriceInfo, "CONTRACTCLOSEFEE", " "));
            vPrintDetails.add(getAttributeValue(eiPriceInfo, "REMKTGDISCOUNT", " "));
            vPrintDetails.add(getAttributeValue(eiComponent, "DISTRCODE", " "));
            //vPrintDetails.add(getAttributeValue(eiComponent, "SVCCAT", " "));
            vPrintDetails.add(getAttributeValue(eiComponent, "VAE", " "));
          }
          else {
            logMessage("_800:Skipping " + strCondition1 + " for " + eiFeature.getKey() + ":Downlinked:" +
                       eiPriceInfo.getKey());
          }
        }
      }

      if (vPrintDetails.size() > 0) {
        //strHeader = new String[]{"Offering", "ID", "Description", "fee", "fee", "disc", "Dist", "cat", " E"};
        strHeader = new String[] {
            "Offering", "ID", "Description", "fee", "fee", "disc", "Dist", " E"};
        iColWidths = new int[] {
            15, 8, 20, 3, 5, 5, 4, 3};
        println(":xmp.");
        println("                                                  Close             V");
        println("                                              L/P cont  Remkt       A");

        printReport(true, strHeader, iColWidths, vPrintDetails);
        resetPrintvars();
        println(":exmp.");
      }
    }
    else {
      logMessage("Q800..no links to priceinfo found..");
    }

    println(".*$A_800_End");

    println(".*$A_801_Begin");
    prettyPrint(transformXML(getAttributeValue(eiAnnounce, "LENOVOBUSPRTNRATTCH", " ")), 69);
    println(".*$A_801_End");

    println(".*$A_805_Begin");
    strParamList1 = new String[] {
        "STANDARDAMENDTEXT"};
    printValueListInGroup(grpStdAmendText, strParamList1, "STANDARDAMENDTEXT_TYPE", "210", "", true);
    iColWidths = new int[] {
        69};
    printReport(false, null, iColWidths, vPrintDetails);
    resetPrintvars();
    println(".*$A_805_End");

  }

  /**
   *  Description of the Method
   */
  private void processLongTo900() {
  }

  /**
   *  Description of the Method
   *
   *@param  _strXml  Description of the Parameter
   *@return          Description of the Return Value
   */
  private String transformXML(String _strXml) {
    ByteArrayOutputStream gmlOS = null; // create output stream & transform
    _strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <!DOCTYPE eAnnounceData SYSTEM \"file:/" + DTDFILEPATH +
        "\" ><eAnnounceData>" + _strXml + "</eAnnounceData>";
//    _strXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?> <!DOCTYPE eAnnounceData SYSTEM \"file:///eAnnounceText.dtd\" ><eAnnounceData>"+_strXml+"</eAnnounceData>";
    try {
      // load xml file into stream
      StringReader srInput = new StringReader(_strXml);
      StreamSource xmlSource = new StreamSource(srInput); //create xml stream source
      xmlSource.setSystemId(_strXml); // resolve relative urls
      //XMLtoGML x2g = new XMLtoGML();// create gml transformer
      gmlOS = (ByteArrayOutputStream) x2g.transform(xmlSource); // xml to gml
    }
    catch (Exception e) {
      println("Error: " + e + "\n");
      println("The following is the Offending xml");
      println(_strXml);
      logError("Exception!" + e.getMessage() + "\n" + ":***:" + _strXml + ":***:");

    }
    return (gmlOS != null ? gmlOS.toString() : "");
  }

  /**
   *  Gets the version attribute of the RFA_IGSSVS class
   *
   *@return    The version value
   */
  public static String getVersion() {
    return ("$Id: RFA_IGSSVS.java,v 1.157 2008/03/19 19:30:44 wendy Exp $");
  }

  /**
   *  Gets the aBRVersion attribute of the RFA_IGSSVS object
   *
   *@return    The aBRVersion value
   */
  public String getABRVersion() {
    return getVersion();
  }

  /**
   *  Description of the Method
   *
   *@param  _strOfferingAttr  Description of the Parameter
   * /
  private void printVanillaSVSReport(String _strOfferingAttr) {
    printVanillaSVSReport(_strOfferingAttr, false, false);
  }*/

  /**
   *  Description of the Method
   *
   *@param  _strOfferingAttr  Description of the Parameter
   *@param  bXmlattr          Description of the Parameter
   *@param  bWithPriceAttr    Description of the Parameter
   */
  private void printVanillaSVSReport(String _strOfferingAttr, boolean bXmlattr, boolean bWithPriceAttr) {
    logMessage("printVanillaSVSReport: for " + _strOfferingAttr);
    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> SOFAVAIL (R) -> SOF (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> CMPNTAVAIL (R) -> CMPNT (E)
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> AVAIL (E) -> FEATUREAVAIL (R) -> FEATURE (E)
     */
    bConditionOK = false;
    if (bWithPriceAttr) { //If to get attribute from priceinfo, then check whether link to price info exist
      vReturnEntities1 = searchEntityVectorLink(vSofSortedbyMkt, null, null, true, true, "SOFPRICE");
      bConditionOK = vReturnEntities1.size() > 0;
      vReturnEntities1 = searchEntityVectorLink(vCmptSortedbyMkt, null, null, true, true, "CMPNTPRICE");
      bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
      vReturnEntities1 = searchEntityVectorLink(vFeatureSortedbyMkt, null, null, true, true, "FEATUREPRICE");
      bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
      if (!bConditionOK) {
        logMessage("No Priceinfo links found for " + _strOfferingAttr);
        return;
      }
    }

    strCondition4 = "";
    strCondition2 = "";
    //boolean bSofDone = vSofSortedbyMkt.size() == 0;
   // boolean bCmptDone = vCmptSortedbyMkt.size() == 0;
    //boolean bFeatDone = vFeatureSortedbyMkt.size() == 0;

    String strMarketingMsg = null;
    String strOFType = null;

    int iCountInstances = vSofSortedbyMkt.size();
    iCountInstances += vCmptSortedbyMkt.size();
    iCountInstances += vFeatureSortedbyMkt.size();

    logMessage("vSofSortedbyMkt" + vSofSortedbyMkt.size());
    logMessage("vCmptSortedbyMkt" + vCmptSortedbyMkt.size());
    logMessage("vFeatureSortedbyMkt" + vFeatureSortedbyMkt.size());

    if (!bWithPriceAttr) { //Check whether we have attr values to print
      strParamList1 = new String[] {
          _strOfferingAttr};
      printValueListInVector(vSofSortedbyMkt, strParamList1, " ", true, false);
      //bSofDone = vPrintDetails.size() == 0;
      resetPrintvars();

      printValueListInVector(vCmptSortedbyMkt, strParamList1, " ", true, false);
      //bCmptDone = vPrintDetails.size() == 0;
      resetPrintvars();

      printValueListInVector(vFeatureSortedbyMkt, strParamList1, " ", true, false);
      //bFeatDone = vPrintDetails.size() == 0;
      resetPrintvars();
    }

    //boolean bAlldone = bSofDone && bCmptDone && bFeatDone;

    i = 0;
    strFilterAttr = new String[] {
        "AVAILTYPE"};
    strFilterValue = new String[] {
        "146"}; //Consider AVAILS OF type Planned Availability only 10/21
    strCondition4 = "";
    for (i = 0; i < vAllSortedOfferings.size(); i++) { //get SOF, CMPT and FEATURE one after the other
      logMessage("  printVanillaSVSReport:I is" + i);
      eiNextItem = (EntityItem) vAllSortedOfferings.elementAt(i);
      strOFType = eiNextItem.getEntityType();
      if (strOFType.equals("SOF")) {
        eiSof = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("printVanillaSVSReport After sof" + eiSof.getKey());
        if (!bWithPriceAttr) {
          strCondition1 = getAttributeValue(eiSof, _strOfferingAttr, " ");
          bConditionOK = strCondition1.trim().length() > 0;
        }
        else {
          strEntityTypes = new String[] {
              "SOFPRICE", "PRICEFININFO"};
          strCondition1 = getDownlinkedEntityAttrValue(eiSof, strEntityTypes, _strOfferingAttr);
          bConditionOK = strCondition1.trim().length() > 0;
        }
        if (bConditionOK) { //Print only if attributes found
          //Check for multiple AVAILS linked to SOF
          vReturnEntities1 = searchEntityItemLink(eiSof, null, null, true, true, "SOFAVAIL");
          //So get the AVAILS
          vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
          if (vReturnEntities2.size() > 0) {
            strCondition1 = getAllGeoTags(vReturnEntities2);
            if (!strCondition1.equals(strCondition2)) {
              if (!strCondition2.equals(strWorldwideTag)) {
                if (strCondition2.length() > 0) {
                  println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
                }
              }
              if (!strCondition1.equals(strWorldwideTag)) {
                println("");
                println(":p.:hp2." + strCondition1 + "--->:ehp2.");
              }
              strCondition2 = strCondition1;
            }
            strMarketingMsg = getSOFMktName(eiSof);

            logMessage("Marketing Msg :" + strMarketingMsg);
            prettyPrint(strMarketingMsg, 69);
            /*
                        if (iCountInstances > 1) {//Print mkt msg if there is more than one instance
                          prettyPrint(strMarketingMsg, 69);
                        }

             */
            if (bXmlattr) {
              if (!bWithPriceAttr) {
                prettyPrint(transformXML(getAttributeValue(eiSof, _strOfferingAttr, " ")), 69);
                //println(":p.");
              }
              else {
                strEntityTypes = new String[] {
                    "SOFPRICE", "PRICEFININFO"};
                prettyPrint(transformXML(getDownlinkedEntityAttrValue(eiSof, strEntityTypes, _strOfferingAttr)), 69);
                //println(":p.");
              }
            }
            else {
              if (!bWithPriceAttr) {
                prettyPrint(getAttributeValue(eiSof, _strOfferingAttr, " "), 69);
              }
              else {
                prettyPrint(getDownlinkedEntityAttrValue(eiSof, strEntityTypes, _strOfferingAttr), 69);
              }
            }
          }
          else {
            logMessage("printVanillaSVSReport: NO AVAIL found for " + eiSof.getKey());
          }
        }
        else {
          logMessage("printVanillaSVSReport:Attribute not returned for " + eiSof.getKey());
        }
        println("");
      }

      if (strOFType.equals("CMPNT")) {
        eiComponent = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("after component" + eiComponent.getKey());
        if (!bWithPriceAttr) {
          strCondition1 = getAttributeValue(eiComponent, _strOfferingAttr, " ");
          bConditionOK = strCondition1.trim().length() > 0;
        }
        else {
          strEntityTypes = new String[] {
              "CMPNTPRICE", "PRICEFININFO"};
          strCondition1 = getDownlinkedEntityAttrValue(eiComponent, strEntityTypes, _strOfferingAttr);
          bConditionOK = strCondition1.trim().length() > 0;
        }
        if (bConditionOK) {
          //Check for multiple AVAILS linked to Component
          vReturnEntities1 = searchEntityItemLink(eiComponent, null, null, true, true, "CMPNTAVAIL");
          //So get the AVAILS
          vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
          if (vReturnEntities2.size() > 0) {
            strCondition1 = getAllGeoTags(vReturnEntities2);
            if (!strCondition1.equals(strCondition2)) {
              if (!strCondition2.equals(strWorldwideTag)) {
                if (strCondition2.length() > 0) {
                  println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
                }
              }
              if (!strCondition1.equals(strWorldwideTag)) {
                println("");
                println(":p.:hp2." + strCondition1 + "--->:ehp2.");
              }
              strCondition2 = strCondition1;
            }

            strMarketingMsg = getCmptToSofMktMsg(eiComponent);

            logMessage("1) Marketing Msg :" + strMarketingMsg);
            prettyPrint(strMarketingMsg, 69);
            /*             if (iCountInstances > 1) {
                          prettyPrint(strMarketingMsg, 69);
                        }
             */
            if (bXmlattr) {
              if (!bWithPriceAttr) {
                prettyPrint(transformXML(getAttributeValue(eiComponent, _strOfferingAttr, " ")), 69);
                //println(":p.");
              }
              else {
                strEntityTypes = new String[] {
                    "CMPNTPRICE", "PRICEFININFO"};
                prettyPrint(transformXML(getDownlinkedEntityAttrValue(eiComponent, strEntityTypes, _strOfferingAttr)),
                            69);
                //println(":p.");
              }
            }
            else {
              if (!bWithPriceAttr) {
                prettyPrint(getAttributeValue(eiComponent, _strOfferingAttr, " "), 69);
              }
              else {
                strEntityTypes = new String[] {
                    "CMPNTPRICE", "PRICEFININFO"};
                prettyPrint(getDownlinkedEntityAttrValue(eiComponent, strEntityTypes, _strOfferingAttr), 69);
              }
            }
          }
          else {
            logMessage("printVanillaSVSReport: No AVAIL found for " + eiComponent.getKey());
          }
        }
        else {
          logMessage("printVanillaSVSReport:Attribute not returned for " + eiComponent.getKey());
        }
        println("");
      }

      if (strOFType.equals("FEATURE")) {
        eiFeature = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("After feature " + eiFeature.getKey());
        if (!bWithPriceAttr) {
          strCondition1 = getAttributeValue(eiFeature, _strOfferingAttr, " ");
          bConditionOK = strCondition1.trim().length() > 0;
        }
        else {
          strEntityTypes = new String[] {
              "FEATUREPRICE", "PRICEFININFO"};
          strCondition1 = getDownlinkedEntityAttrValue(eiFeature, strEntityTypes, _strOfferingAttr);
          bConditionOK = strCondition1.trim().length() > 0;
        }
        if (bConditionOK) {
          //Check for multiple AVAILS linked to Feature
          vReturnEntities1 = searchEntityItemLink(eiFeature, null, null, true, true, "FEATUREAVAIL");
          //So get the AVAILS
          vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
          if (vReturnEntities2.size() > 0) {
            strCondition1 = getAllGeoTags(vReturnEntities2);
            if (!strCondition1.equals(strCondition2)) {
              if (!strCondition2.equals(strWorldwideTag)) {
                if (strCondition2.length() > 0) {
                  println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
                }
              }
              if (!strCondition1.equals(strWorldwideTag)) {
                println("");
                println(":p.:hp2." + strCondition1 + "--->:ehp2.");
              }
              strCondition2 = strCondition1;
            }
            /*             if (!strCondition1.equals(strWorldwideTag)) {
                          if (!strCondition1.equals(strCondition2)) {
                            if (strCondition2.length() > 0) {
                              println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
                            }
                            println("");
                            println(":p.:hp2." + strCondition1 + "--->:ehp2.");
                            strCondition2 = strCondition1;
                          }
                        }
             */
            strMarketingMsg = getfeatureToSofMktMsg(eiFeature);
            logMessage("2) Marketing Msg :" + strMarketingMsg);
            prettyPrint(strMarketingMsg, 69);
            /*             if (iCountInstances > 1) {
                          prettyPrint(strMarketingMsg, 69);
                        }
             */
            if (bXmlattr) {
              if (!bWithPriceAttr) {
                prettyPrint(transformXML(getAttributeValue(eiFeature, _strOfferingAttr, " ")), 69);
                //println(":p.");
              }
              else {
                strEntityTypes = new String[] {
                    "FEATUREPRICE", "PRICEFININFO"};
                prettyPrint(transformXML(getDownlinkedEntityAttrValue(eiFeature, strEntityTypes, _strOfferingAttr)), 69);
                //println(":p.");
              }
            }
            else {
              if (!bWithPriceAttr) {
                prettyPrint(getAttributeValue(eiFeature, _strOfferingAttr, " "), 69);
              }
              else {
                strEntityTypes = new String[] {
                    "CMPNTPRICE", "PRICEFININFO"};
                prettyPrint(getDownlinkedEntityAttrValue(eiFeature, strEntityTypes, _strOfferingAttr), 69);
              }
            }
          }
          else {
            logMessage("printVanillaSVSReport:No AVAIL found for " + eiFeature.getKey());
          }
        }
        else {
          logMessage("printVanillaSVSReport:Attribute not returned for " + eiFeature.getKey());
        }
        println("");
      }

    }

    if (!strCondition2.equals(strWorldwideTag)) {
      if (strCondition2.length() > 0) {
        println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
      }
    }

    println("");

  }

  private void printIntSuppMktFinInfo(String _strFinAttrCode) {
    printIntSuppMktFinInfo(_strFinAttrCode, true);
  }

  /**
   *  Description of the Method
   *
   *@param  _strFinAttrCode  Description of the Parameter
   */
  private void printIntSuppMktFinInfo(String _strFinAttrCode, boolean _bullets) {
    //CMPNT (E) -> CMPNTFINOF (R) -> FINOF
    //FEATURE (E) -> FEATUREFINOF (R) -> FINOF
    logMessage("printIntSuppMktFinInfo for " + _strFinAttrCode);

    bConditionOK = false;
    vReturnEntities1 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTFINOF");
    bConditionOK = vReturnEntities1.size() > 0;
    vReturnEntities1 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATUREFINOF");
    bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
    if (!bConditionOK) {
      logMessage("No Fininfo links found for " + _strFinAttrCode);
      return;
    }

    boolean bCmptDone = vCmpntFrmCmpntAvail.size() == 0;
    boolean bFeatDone = vFeatureFrmFeatureAvail.size() == 0;
    boolean bAlldone = (bCmptDone && bFeatDone) ? true : false;

    int iCountInstances = vCmpntFrmCmpntAvail.size(); //Count instances of entities
    iCountInstances += vFeatureFrmFeatureAvail.size();

    //String[] strCmptToFin = new String[] {
    //    "CMPNTFINOF", "FINOF"};
    //String[] strFeatToFin = new String[] {
     //   "FEATUREFINOF", "FINOF"};

   // Hashtable hNoDupes = new Hashtable();
    String strMarketingMsg = null;

    strCondition4 = "";

    logMessage("vComponents" + vCmpntFrmCmpntAvail.size());
    logMessage("vFeatures" + vFeatureFrmFeatureAvail.size());

    i = 0;
    while (!bAlldone) {

      if (!bCmptDone) {
        eiComponent = (EntityItem) vCmpntFrmCmpntAvail.elementAt(i);
        logMessage("printIntSuppMktFinInfo after component" + eiComponent.getKey());
        /*
               Get component details
         */
        strMarketingMsg = getCmptToSofMktMsg(eiComponent);
        /*         if (!hNoDupes.containsKey(strMarketingMsg)) {
          strCondition4 += strMarketingMsg+" ";
          hNoDupes.put(strMarketingMsg,"MM");
                 }
         */
        //Get the financial part now
        vReturnEntities1 = searchEntityItemLink(eiComponent, null, null, true, true, "CMPNTFINOF");
        vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "FINOF");

        if (vReturnEntities2.size() > 0) {
          logMessage("1) printIntSuppMktFinInfo Marketing Msg :" + strMarketingMsg);
          if (iCountInstances > 1) {
            println("Component Offerings");
            prettyPrint(strMarketingMsg, 69);
            if (vReturnEntities2.size() > 0 && _bullets) {
              println("The financing will enable:");
            }
          }
          //Now print the fininfo attribute
          for (j = 0; j < vReturnEntities2.size(); j++) {
            eiFinof = (EntityItem) vReturnEntities2.elementAt(j);
            strCondition1 = transformXML(getAttributeValue(eiFinof, _strFinAttrCode, " ")); //This will be an xml field

            if (strCondition1.trim().length() > 0) {
//              prettyPrint( ( (_bullets) ? ":li." : "") + strCondition1, 69);
              prettyPrint(strCondition1, 69);
            }
          }

          println("");
        }
        else {
          logMessage("No info returned for attr:" + eiComponent.getKey() + ":" + _strFinAttrCode);
        }
        println("");
      }

      /*
             Get Feature details
       */
      if (!bFeatDone) {
        eiFeature = (EntityItem) vFeatureFrmFeatureAvail.elementAt(i);
        logMessage("printIntSuppMktFinInfo after feature" + eiFeature.getKey());

        strMarketingMsg = getfeatureToSofMktMsg(eiFeature);
        /*         if (!hNoDupes.containsKey(strMarketingMsg)) {
          strCondition4 += strMarketingMsg+" ";
          hNoDupes.put(strMarketingMsg,"MM");
                 }
         */
        //Get the financial part now
        vReturnEntities1 = searchEntityItemLink(eiFeature, null, null, true, true, "FEATUREFINOF");
        vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "FINOF");
        if (vReturnEntities2.size() > 0) {
          logMessage("2) printIntSuppMktFinInfo Marketing Msg :" + strMarketingMsg);
          if (iCountInstances > 1) {
            println("Feature ");
            prettyPrint(strMarketingMsg, 69);
            if (vReturnEntities2.size() > 0 && _bullets) {
              println("The financing will enable:");
            }
          }

          //Now print the fininfo attribute
          for (j = 0; j < vReturnEntities2.size(); j++) {
            eiFinof = (EntityItem) vReturnEntities2.elementAt(j);
            strCondition1 = transformXML(getAttributeValue(eiFinof, _strFinAttrCode, " "));
            if (strCondition1.trim().length() > 0) {
//              prettyPrint( ( (_bullets) ? ":li." : "") + strCondition1, 69);
              prettyPrint(strCondition1, 69);

            }
          }
          println("");
        }
        else {
          logMessage("No info returned for attr:" + eiFeature.getKey() + ":" + _strFinAttrCode);
        }
        println("");
      }

      i++;
      bCmptDone = vCmpntFrmCmpntAvail.size() <= i;
      bFeatDone = vFeatureFrmFeatureAvail.size() <= i;

      bAlldone = (bCmptDone && bFeatDone) ? true : false;
    }

  }

  /**
   *  Description of the Method
   *
   *@param  _strType      Description of the Parameter
   *@param  _strQuestion  Description of the Parameter
   * /
  private void printFeatureBenefit(String _strType, String _strQuestion) {
    printFeatureBenefit(_strType, _strQuestion, true, false);
  }*/

  /**
   *  Description of the Method
   *
   *@param  _strType        Description of the Parameter
   *@param  _strQuestion    Description of the Parameter
   *@param  _bPrintBenefit  Description of the Parameter
   */
  private void printFeatureBenefit(String _strType, String _strQuestion, boolean _bPrintBenefit,
                                   boolean _bPrintCrossSell_UpSell) {
    /* ////If one and only one of the offering types exists and one and only one data element exists,
         then DO NOT print Header, ff more than one offering type exists and/or more than one data element exists,
         then DO print Header*/
    //Check for no. of data elements/offering types
    logMessage("printFeatureBenefit:" + _strType + ":Q:" + _strQuestion);
    bConditionOK = false;
    boolean bComptChildren = false;
    boolean bSofChildren = false;
    boolean bFeatureChildren = false;
    String[] strQAttr = new String[] {
        "TYPE"};
    String[] strQVal = new String[] {
        _strType};
    String strOFType = null;

    int iCountInstances = vSofSortedbyMkt.size();
    iCountInstances += vCmptSortedbyMkt.size();
    iCountInstances += vFeatureSortedbyMkt.size();

    String strCrossSell_UpSell = _strType.equals("110") ? "CROSSELL" : "UPSELL"; //Set the attr code depending on type
    strCrossSell_UpSell = _strType.equals("120") ? "" : strCrossSell_UpSell; //Set the attr code depending on type

    if (_bPrintBenefit && !_bPrintCrossSell_UpSell) { //Check for parents here
      vReturnEntities1 = searchEntityVectorLink(vSofSortedbyMkt, strQAttr, strQVal, true, false, "SOFRELSOF");
      bConditionOK = vReturnEntities1.size() > 0;
      vReturnEntities1 = searchEntityVectorLink(vCmptSortedbyMkt, strQAttr, strQVal, true, false, "CMPNTRELCMPNT");
      bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
      vReturnEntities1 = searchEntityVectorLink(vFeatureSortedbyMkt, strQAttr, strQVal, true, false,
                                                "FEATURERELFEATURE");
      bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
      if (!bConditionOK) {
        logMessage("No Parent links found for Q" + _strQuestion);
        return;
      }
    }

    if (_bPrintCrossSell_UpSell) { //Check for children
      vReturnEntities1 = searchEntityVectorLink(vSofSortedbyMkt, strQAttr, strQVal, true, true, "SOFRELSOF");
      bConditionOK = vReturnEntities1.size() > 0;
      bSofChildren = vReturnEntities1.size() > 1;
      vReturnEntities1 = searchEntityVectorLink(vSofSortedbyMkt, strQAttr, strQVal, true, true, "SOFRELCMPNT");
      bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
      bSofChildren = (!bSofChildren) ? vReturnEntities1.size() > 1 : bSofChildren;
      vReturnEntities1 = searchEntityVectorLink(vSofSortedbyMkt, strQAttr, strQVal, true, true, "SOFRELFEATURE");
      bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
      bSofChildren = (!bSofChildren) ? vReturnEntities1.size() > 1 : bSofChildren;
      if (!bConditionOK) {
        logMessage("No Children links found for SOF Q" + _strQuestion);
      }
      bConditionOK1 = false;
      vReturnEntities1 = searchEntityVectorLink(vCmptSortedbyMkt, strQAttr, strQVal, true, true, "CMPNTRELCMPNT");

      bConditionOK1 = !bConditionOK1 ? vReturnEntities1.size() > 0 : bConditionOK1;
      bComptChildren = vReturnEntities1.size() > 1;
      vReturnEntities1 = searchEntityVectorLink(vCmptSortedbyMkt, strQAttr, strQVal, true, true, "CMPNTRELFEATURE");
      bConditionOK1 = !bConditionOK1 ? vReturnEntities1.size() > 0 : bConditionOK1;
      bComptChildren = (!bComptChildren) ? vReturnEntities1.size() > 1 : bComptChildren;
      vReturnEntities1 = searchEntityVectorLink(vCmptSortedbyMkt, strQAttr, strQVal, true, true, "CMPNTRELSOF");
      bConditionOK1 = !bConditionOK1 ? vReturnEntities1.size() > 0 : bConditionOK1;
      bComptChildren = (!bComptChildren) ? vReturnEntities1.size() > 1 : bComptChildren;
      if (!bConditionOK1) {
        logMessage("No Children links found for CMPNT Q" + _strQuestion);
      }
      bConditionOK = !bConditionOK ? bConditionOK1 : bConditionOK;

      vReturnEntities1 = searchEntityVectorLink(vFeatureSortedbyMkt, strQAttr, strQVal, true, true, "FEATURERELFEATURE");
      bConditionOK1 = !bConditionOK1 ? vReturnEntities1.size() > 0 : bConditionOK1;
      bFeatureChildren = vReturnEntities1.size() > 1;
      vReturnEntities1 = searchEntityVectorLink(vFeatureSortedbyMkt, strQAttr, strQVal, true, true, "FEATURERELCMPNT");
      bConditionOK1 = !bConditionOK1 ? vReturnEntities1.size() > 0 : bConditionOK1;
      bFeatureChildren = (!bFeatureChildren) ? vReturnEntities1.size() > 1 : bFeatureChildren;
      vReturnEntities1 = searchEntityVectorLink(vFeatureSortedbyMkt, strQAttr, strQVal, true, true, "FEATURERELSOF");
      bConditionOK1 = !bConditionOK1 ? vReturnEntities1.size() > 0 : bConditionOK1;
      bFeatureChildren = (!bFeatureChildren) ? vReturnEntities1.size() > 1 : bFeatureChildren;
      if (!bConditionOK) {
        logMessage("No Children links found for Q" + _strQuestion);
      }
    }

    bConditionOK = false;
    bConditionOK1 = false;
    resetPrintvars();
    strFilterAttr = new String[] {
        "AVAILTYPE"};
    strFilterValue = new String[] {
        "146"}; //Consider AVAILS OF type Planned Availability only 10/21

    strCondition4 = "";
    strCondition2 = "";
    strCondition1 = "";

    //boolean bSofDone = vSofSortedbyMkt.size() == 0;
   // boolean bCmptDone = vCmptSortedbyMkt.size() == 0;
    //boolean bFeatDone = vFeatureSortedbyMkt.size() == 0;
    //boolean bAlldone = bSofDone && bCmptDone && bFeatDone;

    String strMarketingMsg = null;
    i = 0;
    for (i = 0; i < vAllSortedOfferings.size(); i++) { //get SOF, CMPT and FEATURE one after the other
      logMessage("  printFeatureBenefit:I is" + i);
      eiNextItem = (EntityItem) vAllSortedOfferings.elementAt(i);
      strOFType = eiNextItem.getEntityType();
      if (strOFType.equals("SOF")) {
        eiSof = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("printFeatureBenefit After sof" + eiSof.getKey());
        strEntityTypes = new String[] {
            "SOFRELSOF"};
        strCondition4 = getDownlinkedEntityAttrValue(eiSof, strEntityTypes, "BENEFIT", strQAttr, strQVal);

        if (_bPrintCrossSell_UpSell) {
          strCondition4 = getAttributeValue(eiSof, strCrossSell_UpSell, " ");
          if (strCondition4.trim().length() == 0) { //Check for related rows if the attribute is empty
            vReturnEntities1 = searchEntityItemLink(eiSof, strQAttr, strQVal, true, true, "SOFRELSOF");
            bConditionOK = vReturnEntities1.size() > 0;
            vReturnEntities1 = searchEntityItemLink(eiSof, strQAttr, strQVal, true, true, "SOFRELCMPNT");
            bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
            vReturnEntities1 = searchEntityItemLink(eiSof, strQAttr, strQVal, true, true, "SOFRELFEATURE");
            bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
            if (bConditionOK) {
              strCondition4 = ".";
            } //Let it go thru if there are relators
          }
        }
        if (strCondition4.trim().length() > 0) {
          //Check for multiple AVAILS linked to SOF
          vReturnEntities1 = searchEntityItemLink(eiSof, null, null, true, true, "SOFAVAIL");
          //So get the AVAILS
          vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
          if (vReturnEntities2.size() > 0) {
            strCondition1 = getAllGeoTags(vReturnEntities2);
            if (!strCondition1.equals(strCondition2)) {
              if (!strCondition2.equals(strWorldwideTag)) {
                if (strCondition2.length() > 0) {
                  println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
                }
              }
              if (!strCondition1.equals(strWorldwideTag)) {
                println("");
                println(":p.:hp2." + strCondition1 + "--->:ehp2.");
              }
              strCondition2 = strCondition1;
            }
            strMarketingMsg = getSOFMktName(eiSof);
            logMessage("Marketing Msg :" + strMarketingMsg);
            if (iCountInstances > 1 || bSofChildren) { //Print mkt msg if there is more than one instance
              prettyPrint(strMarketingMsg, 69);
              println(":p.");
              println(":ul c.");
            }
            else {
              println(":p.");
              prettyPrint(strMarketingMsg, 69);
            }
            if (_bPrintCrossSell_UpSell && ! (strCrossSell_UpSell.equals(""))) {
              strCondition4 = transformXML(getAttributeValue(eiSof, strCrossSell_UpSell, " "));
              //strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiSof, strEntityTypes, strCrossSell_UpSell, strQAttr, strQVal));
              if (strCondition4.trim().length() > 0) {
//                prettyPrint(":li." + strCondition4, 69);
                prettyPrint(strCondition4, 69);
              }
            }

            if (_bPrintCrossSell_UpSell) {

              strEntityTypes = new String[] {
                  "SOFRELSOF"};
              vReturnEntities1 = searchEntityItemLink(eiSof, strQAttr, strQVal, true, true, "SOFRELSOF");
              if (vReturnEntities1.size() > 0) {
                for (j = 0; j < vReturnEntities1.size(); j++) {
                  eiNextItem = (EntityItem) vReturnEntities1.elementAt(j);
                  eiNextItem1 = (EntityItem) eiNextItem.getDownLink(0); //GET THE SOF
                  strCondition4 = getSOFMktName(eiNextItem1);
                  logMessage("0) Child Marketing Msg for SOFRELSOF" + eiNextItem.getKey() + " Downlinked from " +
                             eiSof.getKey() + " is " + strCondition4);
                  //Print mkt msg if there is more than one offering instance and more than one Data Element
                  if ( (iCountInstances > 1 || vReturnEntities1.size() > 1) && strCondition4.trim().length() > 0) {
                    prettyPrint(":li." + strCondition4, 69);
//                  println(":p.");
                  }
                  else {
                    if (strCondition4.trim().length() > 0) {
                      println(":p.");
                      prettyPrint(strCondition4, 69);
                    }
                  }
                }
              }
              else {
                logMessage("0)  No Child Marketing Msg for SOFRELSOF" + eiSof.getKey());
              }

              strEntityTypes = new String[] {
                  "SOFRELCMPNT"};
              vReturnEntities1 = searchEntityItemLink(eiSof, strQAttr, strQVal, true, true, "SOFRELCMPNT");
              if (vReturnEntities1.size() > 0) {
                for (j = 0; j < vReturnEntities1.size(); j++) {
                  eiNextItem = (EntityItem) vReturnEntities1.elementAt(j);
                  eiNextItem1 = (EntityItem) eiNextItem.getDownLink(0); //Get the CMPNT
                  strCondition4 = getCmptToSofMktMsg(eiNextItem1);
                  logMessage("0)  Child Marketing Msg for SOFRELCMPNT" + eiNextItem.getKey() + " Downlinked from " +
                             eiSof.getKey() + " is " + strCondition4);
                  if ( (iCountInstances > 1 || vReturnEntities1.size() > 1) && strCondition4.trim().length() > 0) {
                    prettyPrint(":li." + strCondition4, 69);
//                  println(":p.");
                  }
                  else {
                    if (strCondition4.trim().length() > 0) {
                      println(":p.");
                      prettyPrint(strCondition4, 69);
                    }
                  }
                }
              }
              else {
                logMessage("0)  No Child Marketing Msg for SOFRELCMPNT" + eiSof.getKey());
              }

              strEntityTypes = new String[] {
                  "SOFRELFEATURE"};
              vReturnEntities1 = searchEntityItemLink(eiSof, strQAttr, strQVal, true, true, "SOFRELFEATURE");
              if (vReturnEntities1.size() > 0) {
                for (j = 0; j < vReturnEntities1.size(); j++) {
                  eiNextItem = (EntityItem) vReturnEntities1.elementAt(j);
                  eiNextItem1 = (EntityItem) eiNextItem.getDownLink(0); //GET THE FEATURE
                  strCondition4 = getfeatureToSofMktMsg(eiNextItem1);
                  logMessage("0)  Child Marketing Msg for SOFRELFEATURE" + eiNextItem.getKey() + " Downlinked from " +
                             eiSof.getKey() + " is " + strCondition4);
                  //Print mkt msg if there is more than one offering instance and more than one Data Element
                  if ( (iCountInstances > 1 || vReturnEntities1.size() > 1) && strCondition4.trim().length() > 0) {
                    prettyPrint(":li." + strCondition4, 69);
//                  println(":p.");
                  }
                  else {
                    if (strCondition4.trim().length() > 0) {
                      println(":p.");
                      prettyPrint(strCondition4, 69);
                    }
                  }
                }
              }
              else {
                logMessage("0)  No Child Marketing Msg for SOFRELFEATURE" + eiSof.getKey());
              }

              if (iCountInstances > 1 || bSofChildren) {
                println(":eul.");
              }

            }

            if (_bPrintBenefit) {
              strEntityTypes = new String[] {
                  "SOFRELSOF"};
              if (_bPrintCrossSell_UpSell) {
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiSof, strEntityTypes, "BENEFIT", strQAttr,
                    strQVal));
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }
                strEntityTypes = new String[] {
                    "SOFRELCMPNT"};
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiSof, strEntityTypes, "BENEFIT", strQAttr,
                    strQVal));
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }
                strEntityTypes = new String[] {
                    "SOFRELFEATURE"};
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiSof, strEntityTypes, "BENEFIT", strQAttr,
                    strQVal));
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }
              }
              else {
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiSof, strEntityTypes, "BENEFIT", strQAttr,
                    strQVal));
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }
              }
            }
          }
        }
        else {
          logMessage("No related rows found for " + eiSof.getKey());
        }
        println("");

      }

      if (strOFType.equals("CMPNT")) {
        eiComponent = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("after component" + eiComponent.getKey());

        strEntityTypes = new String[] {
            "CMPNTRELCMPNT"};
        strCondition4 = ".";
        if (_bPrintCrossSell_UpSell) {
          strCondition4 = getAttributeValue(eiComponent, strCrossSell_UpSell, "");
          logMessage("Value of " + strCrossSell_UpSell + " is |" + strCondition4 + "|");
          if (strCondition4.trim().length() == 0) { //Check for related rows if the attribute is empty
            logMessage("Checking for relators from " + eiComponent.getKey());
            vReturnEntities1 = searchEntityItemLink(eiComponent, strQAttr, strQVal, true, true, "CMPNTRELSOF");
            bConditionOK = vReturnEntities1.size() > 0;
            vReturnEntities1 = searchEntityItemLink(eiComponent, strQAttr, strQVal, true, true, "CMPNTRELCMPNT");
            bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
            vReturnEntities1 = searchEntityItemLink(eiComponent, strQAttr, strQVal, true, true, "CMPNTRELFEATURE");
            bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
            if (bConditionOK) {
              strCondition4 = ".";
            } //Let it go thru if there are relators
          }
        }
        else {
          strCondition4 = getDownlinkedEntityAttrValue(eiComponent, strEntityTypes, "BENEFIT", strQAttr, strQVal);
        }
        if (strCondition4.trim().length() > 0) {
          //Check for multiple AVAILS linked to Component
          vReturnEntities1 = searchEntityItemLink(eiComponent, null, null, true, true, "CMPNTAVAIL");
          //So get the AVAILS
          vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
          if (vReturnEntities2.size() > 0) {
            strCondition1 = getAllGeoTags(vReturnEntities2);
            if (!strCondition1.equals(strCondition2)) {
              if (!strCondition2.equals(strWorldwideTag)) {
                if (strCondition2.length() > 0) {
                  println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
                }
              }
              if (!strCondition1.equals(strWorldwideTag)) {
                println("");
                println(":p.:hp2." + strCondition1 + "--->:ehp2.");
              }
              strCondition2 = strCondition1;
            }
            strMarketingMsg = getCmptToSofMktMsg(eiComponent);
            logMessage("1) Marketing Msg :" + strMarketingMsg);
            if (iCountInstances > 1 || bComptChildren) {
              prettyPrint(strMarketingMsg, 69);
              println(":p.");
              println(":ul c.");
            }
            else {
              println(":p.");
              prettyPrint(strMarketingMsg, 69);
            }

            if (_bPrintCrossSell_UpSell && ! (strCrossSell_UpSell.equals(""))) {
              strCondition4 = transformXML(getAttributeValue(eiComponent, strCrossSell_UpSell, " "));
              if (strCondition4.trim().length() > 0) {
                //  println(":p.");
                prettyPrint("       " + strCondition4, 69);
              }
            }
            if (_bPrintCrossSell_UpSell) {
              strEntityTypes = new String[] {
                  "CMPNTRELCMPNT"};
              vReturnEntities1 = searchEntityItemLink(eiComponent, strQAttr, strQVal, true, true, "CMPNTRELCMPNT");
              if (vReturnEntities1.size() > 0) {
                for (j = 0; j < vReturnEntities1.size(); j++) {
                  eiNextItem = (EntityItem) vReturnEntities1.elementAt(j);
                  eiNextItem1 = (EntityItem) eiNextItem.getDownLink(0); //Get the CMPNT
                  strCondition4 = getCmptToSofMktMsg(eiNextItem1);
                  logMessage("1)  Child Marketing Msg for CMPNTRELCMPNT" + eiNextItem.getKey() + " Downlinked from " +
                             eiComponent.getKey() + " is " + strCondition4);
                  //Print mkt msg if there is more than one offering instance and more than one Data Element
                  if ( (iCountInstances > 1 || vReturnEntities1.size() > 1) && strCondition4.trim().length() > 0) {
                    prettyPrint(":li." + strCondition4, 69);
//                  println(":p.");
                  }
                  else {
                    if (strCondition4.trim().length() > 0) {
                      println(":p.");
                      prettyPrint(strCondition4, 69);
                    }
                  }
                }
              }
              else {
                logMessage("1)  No Child Marketing Msg for CMPNTRELCMPNT" + eiComponent.getKey());
              }

              strEntityTypes = new String[] {
                  "CMPNTRELFEATURE"};
              vReturnEntities1 = searchEntityItemLink(eiComponent, strQAttr, strQVal, true, true, "CMPNTRELFEATURE");
              if (vReturnEntities1.size() > 0) {
                for (j = 0; j < vReturnEntities1.size(); j++) {
                  eiNextItem = (EntityItem) vReturnEntities1.elementAt(j);
                  eiNextItem1 = (EntityItem) eiNextItem.getDownLink(0); //GET THE FEATURE
                  strCondition4 = getfeatureToSofMktMsg(eiNextItem1);
                  logMessage("1)  Child Marketing Msg for CMPNTRELFEATURE" + eiNextItem.getKey() + " Downlinked from " +
                             eiComponent.getKey() + " is " + strCondition4);
                  //Print mkt msg if there is more than one offering instance and more than one Data Element
                  if ( (iCountInstances > 1 || vReturnEntities1.size() > 1) && strCondition4.trim().length() > 0) {
                    prettyPrint(":li." + strCondition4, 69);
//                  println(":p.");
                  }
                  else {
                    if (strCondition4.trim().length() > 0) {
                      println(":p.");
                      prettyPrint(strCondition4, 69);
                    }
                  }
                }
              }
              else {
                logMessage("1)  No Child Marketing Msg for CMPNTRELFEATURE" + eiComponent.getKey());
              }

              strEntityTypes = new String[] {
                  "CMPNTRELSOF"};
              vReturnEntities1 = searchEntityItemLink(eiComponent, strQAttr, strQVal, true, true, "CMPNTRELSOF");
              if (vReturnEntities1.size() > 0) {
                for (j = 0; j < vReturnEntities1.size(); j++) {
                  eiNextItem = (EntityItem) vReturnEntities1.elementAt(j);
                  logMessage("****1) " + eiNextItem.getKey());
                  eiNextItem1 = (EntityItem) eiNextItem.getDownLink(0); //GET THE SOF
                  logMessage("****1) Downlinked from CMPTRELSOF" + eiNextItem1.getKey());
                  strCondition4 = getSOFMktName(eiNextItem1);
                  logMessage("1)  Child Marketing Msg for CMPNTRELSOF" + eiNextItem.getKey() + " Downlinked from " +
                             eiComponent.getKey() + " is " + strCondition4);
                  //Print mkt msg if there is more than one offering instance and more than one Data Element
                  if ( (iCountInstances > 1 || vReturnEntities1.size() > 1) && strCondition4.trim().length() > 0) {
                    prettyPrint(":li." + strCondition4, 69);
//                  println(":p.");
                  }
                  else {
                    if (strCondition4.trim().length() > 0) {
                      println(":p.");
                      prettyPrint(strCondition4, 69);
                    }
                  }
                }

              }
              else {
                logMessage("1)  No Child Marketing Msg for CMPNTRELSOF" + eiComponent.getKey());
              }
              if (iCountInstances > 1 || bComptChildren) {
                println(":eul.");
              }

            }

            if (_bPrintBenefit) {
              if (_bPrintCrossSell_UpSell) {
                strEntityTypes = new String[] {
                    "CMPNTRELCMPNT"};
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiComponent, strEntityTypes, "BENEFIT",
                    strQAttr, strQVal));
                logMessage("CMPNTRELCMPNT BENEFIT" + strCondition4);
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }

                strEntityTypes = new String[] {
                    "CMPNTRELFEATURE"};
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiComponent, strEntityTypes, "BENEFIT",
                    strQAttr, strQVal));
                logMessage("CMPNTRELFEATURE BENEFIT" + strCondition4);
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }

                strEntityTypes = new String[] {
                    "CMPNTRELSOF"};
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiComponent, strEntityTypes, "BENEFIT",
                    strQAttr, strQVal));
                logMessage("CMPNTRELSOF BENEFIT" + strCondition4);
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }
              }
              else {
                strEntityTypes = new String[] {
                    "CMPNTRELCMPNT"};
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiComponent, strEntityTypes, "BENEFIT",
                    strQAttr, strQVal));
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }
              }
            }

          }
        }
        else {
          logMessage("No related rows found for " + eiComponent.getKey());
        }
        println("");

      }

      if (strOFType.equals("FEATURE")) {
        eiFeature = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("After feature " + eiFeature.getKey());
        strEntityTypes = new String[] {
            "FEATURERELFEATURE"};
        if (_bPrintCrossSell_UpSell) {
          strCondition4 = getAttributeValue(eiFeature, strCrossSell_UpSell, "");
          if (strCondition4.trim().length() == 0) { //Check for related rows if the attribute is empty
            vReturnEntities1 = searchEntityItemLink(eiFeature, strQAttr, strQVal, true, true, "FEATURERELSOF");
            bConditionOK = vReturnEntities1.size() > 0;
            vReturnEntities1 = searchEntityItemLink(eiFeature, strQAttr, strQVal, true, true, "FEATURERELCMPNT");
            bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
            vReturnEntities1 = searchEntityItemLink(eiFeature, strQAttr, strQVal, true, true, "FEATURERELFEATURE");
            bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
            if (bConditionOK) {
              strCondition4 = ".";
            } //Let it go thru if there are relators
          }
        }
        else {
          strCondition4 = getDownlinkedEntityAttrValue(eiFeature, strEntityTypes, "BENEFIT", strQAttr, strQVal);
        }
        if (strCondition4.trim().length() > 0) {

          //Check for multiple AVAILS linked to Feature
          vReturnEntities1 = searchEntityItemLink(eiFeature, null, null, true, true, "FEATUREAVAIL");
          //So get the AVAILS
          vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
          if (vReturnEntities2.size() > 0) {
            strCondition1 = getAllGeoTags(vReturnEntities2);
            if (!strCondition1.equals(strCondition2)) {
              if (!strCondition2.equals(strWorldwideTag)) {
                if (strCondition2.length() > 0) {
                  println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
                }
              }
              if (!strCondition1.equals(strWorldwideTag)) {
                println("");
                println(":p.:hp2." + strCondition1 + "--->:ehp2.");
              }
              strCondition2 = strCondition1;
            }
            strMarketingMsg = getfeatureToSofMktMsg(eiFeature);
            logMessage("2) Marketing Msg :" + strMarketingMsg);
            if (iCountInstances > 1 || bFeatureChildren) {
              prettyPrint(strMarketingMsg, 69);
              println(":p.");
              println(":ul c.");
            }
            else {
              println(":p.");
              prettyPrint(strMarketingMsg, 69);
            }
            if (_bPrintCrossSell_UpSell && ! (strCrossSell_UpSell.equals(""))) {
              strCondition4 = transformXML(getAttributeValue(eiFeature, strCrossSell_UpSell, " "));
              if (strCondition4.trim().length() > 0) {
                //println(":p.");
                prettyPrint("       " + strCondition4, 69);
              }
            }

            if (_bPrintCrossSell_UpSell) {
              strEntityTypes = new String[] {
                  "FEATURERELFEATURE"};
              vReturnEntities1 = searchEntityItemLink(eiFeature, strQAttr, strQVal, true, true, "FEATURERELFEATURE");
              if (vReturnEntities1.size() > 0) {
                for (j = 0; j < vReturnEntities1.size(); j++) {
                  eiNextItem = (EntityItem) vReturnEntities1.elementAt(j);
                  eiNextItem1 = (EntityItem) eiNextItem.getDownLink(0); //GET THE FEATURE
                  strCondition4 = getfeatureToSofMktMsg(eiNextItem1);
                  logMessage("2)  Child Marketing Msg for FEATURERELFEATURE" + eiNextItem.getKey() +
                             " Downlinked from " + eiFeature.getKey() + " is " + strCondition4);
                  if ( (iCountInstances > 1 || vReturnEntities1.size() > 1) && strCondition4.trim().length() > 0) {
                    prettyPrint(":li." + strCondition4, 69);
                    //println(":p.");
                  }
                  else {
                    if (strCondition4.trim().length() > 0) {
                      println(":p.");
                      prettyPrint(strCondition4, 69);
                    }
                  }
                }
              }
              else {
                logMessage("2)  No Child Marketing Msg for FEATURERELFEATURE" + eiFeature.getKey());
              }

              strEntityTypes = new String[] {
                  "FEATURERELCMPNT"};
              vReturnEntities1 = searchEntityItemLink(eiFeature, strQAttr, strQVal, true, true, "FEATURERELCMPNT");
              if (vReturnEntities1.size() > 0) {
                for (j = 0; j < vReturnEntities1.size(); j++) {
                  eiNextItem = (EntityItem) vReturnEntities1.elementAt(j);
                  eiNextItem1 = (EntityItem) eiNextItem.getDownLink(0); //Get the CMPNT
                  strCondition4 = getCmptToSofMktMsg(eiNextItem1);
                  logMessage("2)  Child Marketing Msg for FEATURERELCMPNT" + eiNextItem.getKey() + " Downlinked from " +
                             eiFeature.getKey() + " is " + strCondition4);
                  if ( (iCountInstances > 1 || vReturnEntities1.size() > 1) && strCondition4.trim().length() > 0) {
                    prettyPrint(":li." + strCondition4, 69);
//                  println(":p.");
                  }
                  else {
                    if (strCondition4.trim().length() > 0) {
                      println("");
                      prettyPrint(strCondition4, 69);
                    }
                  }
                }
              }
              else {
                logMessage("2)  No Child Marketing Msg for FEATURERELCMPNT" + eiFeature.getKey());
              }

              strEntityTypes = new String[] {
                  "FEATURERELSOF"};
              vReturnEntities1 = searchEntityItemLink(eiFeature, strQAttr, strQVal, true, true, "FEATURERELSOF");
              if (vReturnEntities1.size() > 0) {
                for (j = 0; j < vReturnEntities1.size(); j++) {
                  eiNextItem = (EntityItem) vReturnEntities1.elementAt(j);
                  eiNextItem1 = (EntityItem) eiNextItem.getDownLink(0); //GET THE SOF
                  strCondition4 = getSOFMktName(eiNextItem1);
                  logMessage("2)  Child Marketing Msg for FEATURERELSOF" + eiNextItem.getKey() + " Downlinked from " +
                             eiFeature.getKey() + " is " + strCondition4);
                  if ( (iCountInstances > 1 || vReturnEntities1.size() > 1) && strCondition4.trim().length() > 0) {
                    prettyPrint(":li." + strCondition4, 69);
//                  println(":p.");
                  }
                  else {
                    if (strCondition4.trim().length() > 0) {
                      println(":p.");
                      prettyPrint(strCondition4, 69);
                    }
                  }
                }
              }
              else {
                logMessage("2)  No Child Marketing Msg for FEATURERELSOF" + eiFeature.getKey());
              }
              if (iCountInstances > 1 || bFeatureChildren) {
                println(":eul.");
              }

            }

            if (_bPrintBenefit) {
              strEntityTypes = new String[] {
                  "FEATURERELFEATURE"};
              if (_bPrintCrossSell_UpSell) {
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiFeature, strEntityTypes, "BENEFIT",
                    strQAttr, strQVal));
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }

                strEntityTypes = new String[] {
                    "FEATURERELCMPNT"};
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiFeature, strEntityTypes, "BENEFIT",
                    strQAttr, strQVal));
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }

                strEntityTypes = new String[] {
                    "FEATURERELSOF"};
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiFeature, strEntityTypes, "BENEFIT",
                    strQAttr, strQVal));
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }
              }
              else {
                strCondition4 = transformXML(getDownlinkedEntityAttrValue(eiFeature, strEntityTypes, "BENEFIT",
                    strQAttr, strQVal));
                if (strCondition4.trim().length() > 0) {
                  prettyPrint("Benefit:" + strCondition4, 69);
                }
              }

            }

          }
        }
        else {
          logMessage("No related rows found for " + eiFeature.getKey());
        }
        println("");
      }

    }
    if (!strCondition2.equals(strWorldwideTag)) {
      if (strCondition2.length() > 0) {
        println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
      }
    }
  }

  /**
   *  Description of the Method
   *
   *@param  _b  Description of the Parameter
   */
  private void printQ158(boolean _b) {
    /*
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> SOFAVAIL (R) -> SOF (E) -> SOFCATINCL (R) -> CATINCL (E)
         If SOFCATINCL (R) exist and CATALOGNAME = '321' (ibm.com) then answer 'Yes' otherwise or
         ANNOUNCEMENT (E) -> ANNAVAILA (A) -> CMPNTAVAIL (R) -> CMPNT (E) -> (SOFCMPNT (R) -> SOF (E))
         CMPNTCATINCL (R) -> CATINCL (E)
     */
    Vector vSofCats = new Vector();
    Vector vCmptCats = new Vector();
    Vector vFeatCats = new Vector();
    bConditionOK = false;
    vReturnEntities1 = searchEntityVectorLink(vSofFrmSofAvail, null, null, true, true, "SOFCATINCL");
    strFilterAttr = new String[] {
        "CATALOGNAME"};
    strFilterValue = new String[] {
        "321"};
    vSofCats = searchEntityVectorLink(vReturnEntities1, strFilterAttr, strFilterValue, true, true, "CATINCL");

    vReturnEntities1 = searchEntityVectorLink(vCmpntFrmCmpntAvail, null, null, true, true, "CMPNTCATINCL");
    vCmptCats = searchEntityVectorLink(vReturnEntities1, strFilterAttr, strFilterValue, true, true, "CATINCL");

    vReturnEntities1 = searchEntityVectorLink(vFeatureFrmFeatureAvail, null, null, true, true, "FEATURECATINCL");
    vFeatCats = searchEntityVectorLink(vReturnEntities1, strFilterAttr, strFilterValue, true, true, "CATINCL");

    strCondition4 = "";
    strCondition2 = "";
    boolean bSofDone = vSofCats.size() == 0;
    boolean bCmptDone = vCmptCats.size() == 0;
    boolean bFeatDone = vFeatCats.size() == 0;
    boolean bAlldone = bSofDone && bCmptDone && bFeatDone;

    //String strMarketingMsg = null;

    //Hashtable hNoDupes = new Hashtable();

    int iCountInstances = vSofCats.size();
    iCountInstances += vCmptCats.size();
    iCountInstances += vFeatCats.size();

    logMessage("vSofCats" + vSofCats.size());
    logMessage("vCmptCats" + vCmptCats.size());
    logMessage("vFeatCats" + vFeatCats.size());

    i = 0;
    strFilterAttr = new String[] {
        "AVAILTYPE"};
    strFilterValue = new String[] {
        "146"}; //Consider AVAILS OF type Planned Availability only 10/21
    strCondition4 = "";
    while (!bAlldone) { //get SOF, CMPT and FEATURE one after the other
      logMessage("  printQ158:I is" + i);
      if (!bSofDone) {
        eiCatIncl = (EntityItem) vSofCats.elementAt(i);
        strEntityTypes = new String[] {
            "SOFCATINCL", "SOF"};
        eiSof = getUplinkedEntityItem(eiCatIncl, strEntityTypes);
        logMessage("after SOF" + eiSof.getKey());
        //Check for multiple AVAILS linked to SOF
        vReturnEntities1 = searchEntityItemLink(eiSof, null, null, true, true, "SOFAVAIL");
        //So get the AVAILS
        vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
        strCondition1 = getAllGeoTags(vReturnEntities2);

        if (!strCondition1.equals(strCondition2)) {
          if (!strCondition2.equals(strWorldwideTag)) {
            if (strCondition2.length() > 0) {
              println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
            }
          }
          if (!strCondition1.equals(strWorldwideTag)) {
            println("");
            println(":p.:hp2." + strCondition1 + "--->:ehp2.");
          }
          strCondition2 = strCondition1;
        }
        if (_b) {
          strCondition4 = getSOFMktName(eiSof);
          logMessage("printQ158:" + strCondition4);
          prettyPrint(strCondition4, 69);
        }
        strCondition4 = transformXML(getAttributeValue(eiCatIncl, "CATALOGTAXONOMY", " "));
        prettyPrint(":p.:hp2." + strCondition4 + ":ehp2.", 69);
        println("");
      }

      if (!bCmptDone) {

        eiCatIncl = (EntityItem) vCmptCats.elementAt(i);
        strEntityTypes = new String[] {
            "CMPNTCATINCL", "CMPNT"};
        eiComponent = getUplinkedEntityItem(eiCatIncl, strEntityTypes);
        logMessage("after component" + eiComponent.getKey());

        //Check for multiple AVAILS linked to Component
        vReturnEntities1 = searchEntityItemLink(eiComponent, null, null, true, true, "CMPNTAVAIL");
        //So get the AVAILS
        vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
        strCondition1 = getAllGeoTags(vReturnEntities2);
        if (!strCondition1.equals(strCondition2)) {
          if (!strCondition2.equals(strWorldwideTag)) {
            if (strCondition2.length() > 0) {
              println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
            }
          }
          if (!strCondition1.equals(strWorldwideTag)) {
            println("");
            println(":p.:hp2." + strCondition1 + "--->:ehp2.");
          }
          strCondition2 = strCondition1;
        }
        if (_b) {
          strCondition4 = getCmptToSofMktMsg(eiComponent);
          logMessage("printQ158:1:" + strCondition4);
          prettyPrint(strCondition4, 69);
        }
        strCondition4 = transformXML(getAttributeValue(eiCatIncl, "CATALOGTAXONOMY", " "));
        prettyPrint(":p.:hp2." + strCondition4 + ":ehp2.", 69);
        println("");

      }
      if (!bFeatDone) {
        eiCatIncl = (EntityItem) vFeatCats.elementAt(i);
        strEntityTypes = new String[] {
            "FEATURECATINCL", "FEATURE"};
        eiFeature = getUplinkedEntityItem(eiCatIncl, strEntityTypes);
        logMessage("After feature " + eiFeature.getKey());

        //Check for multiple AVAILS linked to Feature
        vReturnEntities1 = searchEntityItemLink(eiFeature, null, null, true, true, "FEATUREAVAIL");
        //So get the AVAILS
        vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
        strCondition1 = getAllGeoTags(vReturnEntities2);
        if (!strCondition1.equals(strCondition2)) {
          if (!strCondition2.equals(strWorldwideTag)) {
            if (strCondition2.length() > 0) {
              println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
            }
          }
          if (!strCondition1.equals(strWorldwideTag)) {
            println("");
            println(":p.:hp2." + strCondition1 + "--->:ehp2.");
          }
          strCondition2 = strCondition1;
        }
        if (_b) {
          strCondition4 = getfeatureToSofMktMsg(eiFeature);
          logMessage("printQ158:2:" + strCondition4);
          prettyPrint(strCondition4, 69);
        }
        strCondition4 = transformXML(getAttributeValue(eiCatIncl, "CATALOGTAXONOMY", " "));
        prettyPrint(":p.:hp2." + strCondition4 + ":ehp2.", 69);
        println("");
      }

      if (bSofDone && bCmptDone && bFeatDone) {
        bAlldone = true;
      }

      i++;
      bSofDone = vSofCats.size() <= i;
      bCmptDone = vCmptCats.size() <= i;
      bFeatDone = vFeatCats.size() <= i;
    }

    if (!strCondition2.equals(strWorldwideTag)) {
      if (strCondition2.length() > 0) {
        println(".br;:hp2.<---" + strCondition2 + ":ehp2.");
      }
    }

    println("");

  }

  /****
   * Q153 The SOF Marketing Name (MKTGNAME) and OFIDNUMBER needs to be printed on ALL announcements
   * whether or not the SOF has an AVAIL. The SOF Marketing Name (MKTGNAME) and OFIDNUMEBR needs only
   * to be printed ONCE, eventhough it may have multiple CMPNT or FEATURES referencing it in any given announcement.
   * MN29166812  data missing because of SOFAVAIL checks
   */
  private void printA153() {
    String strSOFAttrCode = "OFIDNUMBER";

    logMessage("printA153: entered vAllSortedOfferings " + vAllSortedOfferings.size());
    displayContents(vAllSortedOfferings);

    // vAllSortedOfferings is SOF that had SOFAVAIL, CMPNT that had CMPNTAVAIL and FEATURE that had FEATUREAVAIL
    for (int i = 0; i < vAllSortedOfferings.size(); i++) { //get SOF, CMPT and FEATURE one after the other
      String strMarketingMsg;
      String strSOFidNum;
      String strProjNum;
      eiNextItem = (EntityItem) vAllSortedOfferings.elementAt(i);
      logMessage("printA153: loop [" + i + "] " + eiNextItem.getKey());

      if (eiNextItem.getEntityType().equals("SOF")) { // SOF had the AVAIL
        eiSof = eiNextItem;
        logMessage("printA153: in SOF " + eiSof.getKey());

        //get the SOF mktmsg
        strMarketingMsg = getSOFMktName(eiSof);
        if (strMarketingMsg.trim().length() > 0) { //Marketing Msg found
          logMessage("printA153: " + eiSof.getKey() + " Adding Marketing Msg :" + strMarketingMsg);
          vPrintDetails.add(strMarketingMsg);

          strSOFidNum = getAttributeValue(eiSof, strSOFAttrCode, " ");
          logMessage("printA153: Adding " + strSOFAttrCode + " :" + strSOFidNum);
          vPrintDetails.add(strSOFidNum);
          // TIR 6QKKNN add autobahn project number
          //Navigate to OFDEVLPROJ and get PROJNUMBER
          strProjNum = " ";
          vReturnEntities4 = searchEntityItemLink(eiSof, null, null, true, true, "SOFPRA");
          logMessage("printA153: Number of SOFPRA links found =" + vReturnEntities4.size());
          // Not Checking for link to AVAIL here since this is coming from entities derived from AVAIL
          if (vReturnEntities4.size() > 0) {
            // SOFPRA may be more than one.. future fix match ANNOUNCEMENT.ANNDATE to OFDEVLPROJ.ANNDATE
            eiNextItem2 = (EntityItem) vReturnEntities4.elementAt(0); // SOFPRA association
            eiNextItem3 = (EntityItem) eiNextItem2.getDownLink(0); //This will be the OFDEVLPROJ entity
            strProjNum = getAttributeValue(eiNextItem3, "PROJNUMBER", "No Value" + eiNextItem3.getKey());
          }

          logMessage("printA153: Adding PROJNUMBER:" + strProjNum);
          vPrintDetails.add(strProjNum);
          // end TIR 6QKKNN
        } // end marketing msg found
        else {
          logMessage("printA153: No Marketing Msg found. Skipping " + eiSof.getKey());
        }
      } // end SOF

      if (eiNextItem.getEntityType().equals("CMPNT")) { // CMPNT had the AVAIL
        eiComponent = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("printA153 After component" + eiComponent.getKey());
        eiSof = getUplinkedEntityItem(eiComponent, strCmptToSof);
        if (eiSof != null) {
          //get the SOF mktmsg
          strMarketingMsg = getSOFMktName(eiSof);
          if (strMarketingMsg.trim().length() > 0) {
            // SOF does not need to have an AVAIL, the CMPNT has the AVAIL
            logMessage("printA153 " + eiSof.getKey() + " for " + eiComponent.getKey() + " Adding Marketing Msg :" +
                       strMarketingMsg);
            vPrintDetails.add(strMarketingMsg);

            strSOFidNum = getAttributeValue(eiSof, strSOFAttrCode, " ");
            logMessage("printA153: Adding " + strSOFAttrCode + " :" + strSOFidNum);
            vPrintDetails.add(strSOFidNum);

            // TIR 6QKKNN add autobahn project number
            vReturnEntities4 = searchEntityItemLink(eiSof, null, null, true, true, "SOFPRA");
            logMessage("printA153 Number of SOFPRA links found =" + vReturnEntities4.size());

            strProjNum = " ";
            //Navigate to OFDEVLPROJ and get PROJNUMBER
            if (vReturnEntities4.size() > 0) {
              // SOFPRA may be more than one.. future fix match ANNOUNCEMENT.ANNDATE to OFDEVLPROJ.ANNDATE
              eiNextItem2 = (EntityItem) vReturnEntities4.elementAt(0); // This will be the assoc
              eiNextItem3 = (EntityItem) eiNextItem2.getDownLink(0); //This will be the OFDEVLPROJ entity
              strProjNum = getAttributeValue(eiNextItem3, "PROJNUMBER", "No Value" + eiNextItem3.getKey());
            }

            logMessage("printA153 adding PROJNUMBER :" + strProjNum);
            vPrintDetails.add(strProjNum);
            // end TIR 6QKKNN
          } // end marketing msg found
          else {
            logMessage("printA153 No Marketing msg found on " + eiSof.getKey() + " for " + eiComponent.getKey());
          }
        } // end SOF found for CMPNT
        else {
          logMessage("printA153 No SOF for " + eiComponent.getKey());
        }
      } // end CMPNT

      if (eiNextItem.getEntityType().equals("FEATURE")) {
        strFeatureToSof = new String[] {
            "CMPNTFEATURE", "CMPNT", "SOFCMPNT", "SOF"};
        eiFeature = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("printA153 After feature" + eiFeature.getKey());
        eiSof = getUplinkedEntityItem(eiFeature, strFeatureToSof);
        if (eiSof != null) {
          //get the SOF mktmsg
          strMarketingMsg = getSOFMktName(eiSof);
          if (strMarketingMsg.trim().length() > 0) {
            // SOF does not need an AVAIL, FEATURE has it
            logMessage("printA153 " + eiSof.getKey() + " for " + eiFeature.getKey() + " Adding Marketing Msg :" +
                       strMarketingMsg);
            vPrintDetails.add(strMarketingMsg);

            strSOFidNum = getAttributeValue(eiSof, strSOFAttrCode, " ");
            logMessage("printA153: Adding " + strSOFAttrCode + " :" + strSOFidNum);
            vPrintDetails.add(strSOFidNum);

            // TIR 6QKKNN add autobahn project number
            vReturnEntities4 = searchEntityItemLink(eiSof, null, null, true, true, "SOFPRA");
            logMessage("printA153 Number of SOFPRA links found =" + vReturnEntities4.size());

            strProjNum = " ";
            //Navigate to OFDEVLPROJ and get PROJNUMBER
            if (vReturnEntities4.size() > 0) {
              // SOFPRA may be more than one.. future fix match ANNOUNCEMENT.ANNDATE to OFDEVLPROJ.ANNDATE
              eiNextItem2 = (EntityItem) vReturnEntities4.elementAt(0); // this is the assoc
              eiNextItem3 = (EntityItem) eiNextItem2.getDownLink(0); //This will be the OFDEVLPROJ entity
              strProjNum = getAttributeValue(eiNextItem3, "PROJNUMBER", "No Value" + eiNextItem3.getKey());
            }

            logMessage("printA153 adding PROJNUMBER :" + strProjNum);
            vPrintDetails.add(strProjNum);
            // end TIR 6QKKNN
          } // end marketing msg found
          else {
            logMessage("printA153 No Marketing msg found on " + eiSof.getKey() + " for " + eiFeature.getKey());
          }
        } // end SOF found for FEATURE
        else {
          logMessage("printA153 No SOF found for " + eiFeature.getKey());
        }
      } // end FEATURE
    } // end loop of SOF, CMPNT, FEATURE linked to AVAILs

    if (vPrintDetails.size() > 0) {
      for (int ky = 0; ky < vPrintDetails.size(); ky++) {
        logMessage("printA153 Printvector :" + ky + ":" + (String) vPrintDetails.elementAt(ky));
      }

      println(":xmp.");
      println(".kp off");
      /*TIR 6QKKNN
       Column Header - Line 1	Column Position 	 Length
       Offering				1 - 8					8
       Offering				47 - 54					8
       Autobahn				56 - 63					8

       Column Header  Line 2	Column Position 	 Length
       Name					1 - 4					4
       ID						47 - 48					2
       Project Number			56 - 69					14
       */
      strHeader = new String[] {
          "Name", "ID", "Number"};
      iColWidths = new int[] {
          47, 16, 10};
      rfaReport.setSortColumns(new int[] {1});
      rfaReport.setSortable(true);
      println("                                                                 Autobahn");
      println("Service/Offering                                Service/Offering Project");
      printReport(true, strHeader, iColWidths, vPrintDetails);
      println(":exmp.");
      resetPrintvars();
    }
  }

 /* private void printInternalLetter(String _strSOFAttrCode) {
    printInternalLetter(_strSOFAttrCode, false);
  }*/

 /* private void printInternalLetter(String _strSOFAttrCode, boolean _bFromOffering) {
    String strOFType = null;
    logMessage("printInternalLetter:" + _strSOFAttrCode);
    bConditionOK = vSofSortedbyMkt.size() > 0;
    vReturnEntities1 = searchEntityVectorLink(vCmptSortedbyMkt, null, null, true, false, "SOFCMPNT");
    displayContents(vReturnEntities1);
    bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;

    vReturnEntities1 = searchEntityVectorLink(vFeatureSortedbyMkt, null, null, true, false, "CMPNTFEATURE");
    displayContents(vReturnEntities1);
    vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "CMPNT");
    vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "SOFCMPNT");
    displayContents(vReturnEntities1);
    bConditionOK = !bConditionOK ? vReturnEntities1.size() > 0 : bConditionOK;
    if (!bConditionOK) {
      logMessage("No SOF links found for " + _strSOFAttrCode);
      return;
    }

    strCondition4 = "";
    strCondition2 = "";
    strFeatureToSof = new String[] {
        "CMPNTFEATURE", "CMPNT", "SOFCMPNT", "SOF"};
    boolean bSofDone = vSofSortedbyMkt.size() == 0;
    boolean bCmptDone = vCmptSortedbyMkt.size() == 0;
    boolean bFeatDone = vFeatureSortedbyMkt.size() == 0;

    //String strMarketingMsg = null;

    int iCountInstances = vSofSortedbyMkt.size();
    iCountInstances += vCmptSortedbyMkt.size();
    iCountInstances += vFeatureSortedbyMkt.size();

    logMessage("vSofSortedbyMkt" + vSofSortedbyMkt.size());
    displayContents(vSofSortedbyMkt);
    logMessage("vCmptSortedbyMkt" + vCmptSortedbyMkt.size());
    displayContents(vCmptSortedbyMkt);
    logMessage("vFeatureSortedbyMkt" + vFeatureSortedbyMkt.size());
    displayContents(vFeatureSortedbyMkt);

    if (_strSOFAttrCode != null) { //Check whether we have attr values to print
      strParamList1 = new String[] {
          _strSOFAttrCode};
      printValueListInVector(vSofSortedbyMkt, strParamList1, " ", true, false);
      bSofDone = vPrintDetails.size() == 0;
      if (bSofDone) {
        logMessage("No " + _strSOFAttrCode + " populated from the SOF...So no details printed from SOF");
      }
      resetPrintvars();

      if (_bFromOffering) {
        printValueListInVector(vCmptSortedbyMkt, strParamList1, " ", true, false);
      }
      else {
        vReturnEntities1 = searchEntityVectorLink(vCmptSortedbyMkt, null, null, true, false, "SOFCMPNT");
        vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "SOF");
        printValueListInVector(vReturnEntities2, strParamList1, " ", true, false);
      }

      bCmptDone = vPrintDetails.size() == 0;
      if (bCmptDone) {
        logMessage("No " + _strSOFAttrCode + " populated from the COMPONENTS...So no details printed from COMPONENTS");
      }
      resetPrintvars();
      if (_bFromOffering) {
        printValueListInVector(vFeatureSortedbyMkt, strParamList1, " ", true, false);
      }
      else {
        vReturnEntities1 = searchEntityVectorLink(vFeatureSortedbyMkt, null, null, true, false, "CMPNTFEATURE");
        vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "CMPNT");
        vReturnEntities1 = searchEntityVectorLink(vReturnEntities2, null, null, true, false, "SOFCMPNT");
        vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, false, "SOF");
        printValueListInVector(vReturnEntities2, strParamList1, " ", true, false);
      }

      bFeatDone = vPrintDetails.size() == 0;
      if (bFeatDone) {
        logMessage("No " + _strSOFAttrCode + " populated from the features...So no details printed from FEATURES");
      }
      resetPrintvars();
    }
    //boolean bAlldone = bSofDone && bCmptDone && bFeatDone;

    i = 0;
    for (i = 0; i < vAllSortedOfferings.size(); i++) { //get SOF, CMPT and FEATURE one after the other
      logMessage("  printInternalLetter:I is " + i);
      eiNextItem = (EntityItem) vAllSortedOfferings.elementAt(i);
      strOFType = eiNextItem.getEntityType();
      if (strOFType.equals("SOF")) {
        eiSof = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("printInternalLetter After sof " + eiSof.getKey());

        //get the SOF mktmsg
        strCondition4 = getSOFMktName(eiSof);

        logMessage("Marketing Msg :" + strCondition4);

        if (strCondition4.trim().length() > 0) { //Marketing Msg found
          //Check for multiple AVAILS linked to SOF
          vReturnEntities1 = searchEntityItemLink(eiSof, null, null, true, true, "SOFAVAIL");
          //So get the AVAILS
          vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
          logMessage("vReturnEntities2 " + vReturnEntities2.size());
          displayContents(vReturnEntities2);
          if (vReturnEntities2.size() > 0) {
            strCondition1 = getAllGeoTags(vReturnEntities2);
            if (!strCondition1.equals(strCondition2)) { // GEO tags changed
              if (_strSOFAttrCode == null) {
                if (!strCondition2.equals(strWorldwideTag)) {
                  if (strCondition2.length() > 0) {
                    vPrintDetails.add("9999" + strCondition4 + BREAK_INDICATOR + ".br;:hp2.<---" + strCondition2 +
                                      ":ehp2.");
                  }
                }
                if (!strCondition1.equals(strWorldwideTag)) {
                  if (strCondition1.length() > 0) {
                    vPrintDetails.add("    " + strCondition4 + BREAK_INDICATOR + ":p.:hp2." + strCondition1 +
                                      "--->:ehp2.");
                  }
                }
                else {
                  logMessage("Adding Marketing Msg :" + strCondition4);
                  vPrintDetails.add(strCondition4);
                }
              }
              else {
                logMessage("Adding Marketing Msg :" + strCondition4);
                vPrintDetails.add(strCondition4);
              }

              strCondition2 = strCondition1;
            }
            else { // GEOs match, always need this mktmsg  MN29121594
              logMessage("GEOs didn't change, Adding Marketing Msg :" + strCondition4);
              vPrintDetails.add(strCondition4);
            }

            if (_strSOFAttrCode != null) {
              strCondition4 = getAttributeValue(eiSof, _strSOFAttrCode, " ");
              logMessage(_strSOFAttrCode + " :" + strCondition4);
              vPrintDetails.add(strCondition4);
              // TIR 6QKKNN add autobahn project number
              //Navigate to OFDEVLPROJ and get PROJNUMBER
              strCondition3 = " ";
              vReturnEntities4 = searchEntityItemLink(eiSof, null, null, true, true, "SOFPRA");
              logMessage("Number of SOFPRA links found =" + vReturnEntities4.size());
              // Not Checking for link to AVAIL here since this is coming from entities derived from AVAIL
              if (vReturnEntities4.size() > 0) {
                eiNextItem2 = (EntityItem) vReturnEntities4.elementAt(0); // SOFPRA association
                eiNextItem3 = (EntityItem) eiNextItem2.getDownLink(0); //This will be the OFDEVLPROJ entity
                strCondition3 = getAttributeValue(eiNextItem3, "PROJNUMBER", "No Value" + eiNextItem3.getKey());
              }

              vPrintDetails.add(strCondition3);
              // end TIR 6QKKNN
            }
          } // end SOFAVAILs found
        }
        else {
          logMessage("No related rows found for " + eiSof.getKey());
        }

      }

      if (strOFType.equals("CMPNT")) {
        eiComponent = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("printInternalLetter After component" + eiComponent.getKey());
        eiSof = getUplinkedEntityItem(eiComponent, strCmptToSof);
        if (eiSof != null) {
          //get the SOF mktmsg
          strCondition4 = getSOFMktName(eiSof);
          logMessage("Marketing Msg :" + strCondition4);
          if (strCondition4.trim().length() > 0) {
            //Check for multiple AVAILS linked to SOF
            vReturnEntities1 = searchEntityItemLink(eiSof, null, null, true, true, "SOFAVAIL");
            //So get the AVAILS
            vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
            if (vReturnEntities2.size() > 0) {
              strCondition1 = getAllGeoTags(vReturnEntities2);
              if (!strCondition1.equals(strCondition2)) {
                if (_strSOFAttrCode == null) {
                  if (!strCondition2.equals(strWorldwideTag)) {
                    if (strCondition2.length() > 0) {
                      vPrintDetails.add("9999" + strCondition4 + BREAK_INDICATOR + ".br;:hp2.<---" + strCondition2 +
                                        ":ehp2.");
                    }
                  }
                  if (!strCondition1.equals(strWorldwideTag)) {
                    vPrintDetails.add("    " + strCondition4 + BREAK_INDICATOR + ":p.:hp2." + strCondition1 +
                                      "--->:ehp2.");
                  }
                  else {
                    logMessage("Adding Marketing Msg :" + strCondition4);
                    vPrintDetails.add(strCondition4);
                  }
                }
                else {
                  logMessage("Adding Marketing Msg :" + strCondition4);
                  vPrintDetails.add(strCondition4);
                }

                strCondition2 = strCondition1;
              }
              else { // GEOs match, always need this mktmsg  MN29121594
                logMessage("GEOs didn't change, Adding Marketing Msg :" + strCondition4);
                vPrintDetails.add(strCondition4);
              }
            }
            / * continuation of MN# 28837752 Q153 had no output because the vector didn't have 3 columns of data
                Wendy	does the SOF need to have an AVAIL?
                Tim Ragosta	the way I read in the spec it does not
                Tim Ragosta	this the comment in the beginning of q153
                Tim Ragosta	The SOF Marketing Name (MKTGNAME) and OFIDNUMBER needs to be printed on ALL
             announcements whether or not the SOF has an AVAIL. The SOF Marketing Name (MKTGNAME) and
             OFIDNUMBER needs only to be printed ONCE, even though it may have multiple CMPNT or
             FEATURES referencing it in any given announcement.
             * /
            else { // need mktmsg in vector of details
              logMessage("No SOFAVAIL, Adding Marketing Msg :" + strCondition4);
              vPrintDetails.add(strCondition4);
            }

            if (_strSOFAttrCode != null) {
              strCondition4 = getAttributeValue(eiSof, _strSOFAttrCode, " ");
              logMessage(_strSOFAttrCode + " :" + strCondition4);
              vPrintDetails.add(strCondition4);

              // TIR 6QKKNN add autobahn project number
              vReturnEntities4 = searchEntityItemLink(eiSof, null, null, true, true, "SOFPRA");
              logMessage("Number of SOFPRA links found =" + vReturnEntities4.size());

              strCondition3 = " ";
              //Navigate to OFDEVLPROJ and get PROJNUMBER
              if (vReturnEntities4.size() > 0) {
                eiNextItem2 = (EntityItem) vReturnEntities4.elementAt(0); // This will be the assoc
                eiNextItem3 = (EntityItem) eiNextItem2.getDownLink(0); //This will be the OFDEVLPROJ entity
                strCondition3 = getAttributeValue(eiNextItem3, "PROJNUMBER", "No Value" + eiNextItem3.getKey());
              }

              logMessage("PROJNUMBER :" + strCondition3);
              vPrintDetails.add(strCondition3);
              // end TIR 6QKKNN
            }
          }
          else {
            logMessage("No related rows found for " + eiComponent.getKey());
          }
        }
      }

      if (strOFType.equals("FEATURE")) {
        eiFeature = (EntityItem) vAllSortedOfferings.elementAt(i);
        logMessage("printInternalLetter After feature" + eiFeature.getKey());
        eiSof = getUplinkedEntityItem(eiFeature, strFeatureToSof);
        logMessage("Uplinked sof from feature is :" + eiSof.getKey());
        if (eiSof != null) {
          //get the SOF mktmsg
          strCondition4 = getSOFMktName(eiSof);
          logMessage("Marketing Msg :" + strCondition4);

          if (strCondition4.trim().length() > 0) {

            //Check for multiple AVAILS linked to SOF
            vReturnEntities1 = searchEntityItemLink(eiSof, null, null, true, true, "SOFAVAIL");
            //So get the AVAILS
            vReturnEntities2 = searchEntityVectorLink(vReturnEntities1, null, null, true, true, "AVAIL");
            if (vReturnEntities2.size() > 0) {
              strCondition1 = getAllGeoTags(vReturnEntities2);
              if (!strCondition1.equals(strCondition2)) {
                if (_strSOFAttrCode == null) {
                  if (!strCondition2.equals(strWorldwideTag)) {
                    if (strCondition2.length() > 0) {
                      vPrintDetails.add("9999" + strCondition4 + BREAK_INDICATOR + ".br;:hp2.<---" + strCondition2 +
                                        ":ehp2.");
                    }
                  }
                  if (!strCondition1.equals(strWorldwideTag)) {
                    vPrintDetails.add("    " + strCondition4 + BREAK_INDICATOR + ":p.:hp2." + strCondition1 +
                                      "--->:ehp2.");
                  }
                  else {
                    logMessage("Adding Marketing Msg :" + strCondition4);
                    vPrintDetails.add(strCondition4);
                  }
                }
                else {
                  logMessage("Adding Marketing Msg :" + strCondition4);
                  vPrintDetails.add(strCondition4);
                }

                strCondition2 = strCondition1;
              }
              else { // GEOs match, always need this mktmsg  MN29121594
                logMessage("GEOs didn't change, Adding Marketing Msg :" + strCondition4);
                vPrintDetails.add(strCondition4);
              }

              if (_strSOFAttrCode != null) {
                strCondition4 = getAttributeValue(eiSof, _strSOFAttrCode, " ");
                logMessage(_strSOFAttrCode + " :" + strCondition4);
                vPrintDetails.add(strCondition4);

                // TIR 6QKKNN add autobahn project number
                vReturnEntities4 = searchEntityItemLink(eiSof, null, null, true, true, "SOFPRA");
                logMessage("Number of SOFPRA links found =" + vReturnEntities4.size());

                strCondition3 = " ";
                //Navigate to OFDEVLPROJ and get PROJNUMBER
                if (vReturnEntities4.size() > 0) {
                  eiNextItem2 = (EntityItem) vReturnEntities4.elementAt(0); // this is the assoc
                  eiNextItem3 = (EntityItem) eiNextItem2.getDownLink(0); //This will be the OFDEVLPROJ entity
                  strCondition3 = getAttributeValue(eiNextItem3, "PROJNUMBER", "No Value" + eiNextItem3.getKey());
                }

                logMessage("PROJNUMBER :" + strCondition3);
                vPrintDetails.add(strCondition3);
                // end TIR 6QKKNN
              }
            }
          }
          else {
            logMessage("No related rows found for " + eiFeature.getKey());
          }
        }
      }

    }
    if (_strSOFAttrCode == null) {
      if (!strCondition2.equals(strWorldwideTag)) {
        if (strCondition2.trim().length() > 0) {
          vPrintDetails.add("9999" + strCondition4 + BREAK_INDICATOR + ".br;:hp2.<---" + strCondition2 + ":ehp2.");
        }
      }
    }
    for (int ky = 0; ky < vPrintDetails.size(); ky++) {
      logMessage("Printvector :" + ky + ":" + (String) vPrintDetails.elementAt(ky));
    }
    if (vPrintDetails.size() > 0) {
      println(":xmp.");
      println(".kp off");
      if (_strSOFAttrCode != null) {
//TIR 6QKKNN        strHeader = new String[] {"Offering Name", "Offering ID"};
//TIR 6QKKNN        iColWidths = new int[] {56,11};
        / *TIR 6QKKNN
         Column Header - Line 1	Column Position 	 Length
         Offering				1 - 8					8
         Offering				47 - 54					8
         Autobahn				56 - 63					8

         Column Header  Line 2	Column Position 	 Length
         Name					1 - 4					4
         ID						47 - 48					2
         Project Number			56 - 69					14
         * /
        strHeader = new String[] {
            "Name", "ID", "Project Number"};
        iColWidths = new int[] {
            47, 9, 14};
        rfaReport.setSortColumns(new int[] {1});
        rfaReport.setSortable(true);
        println("Offering	                                       Offering  Autobahn");
        printReport(true, strHeader, iColWidths, vPrintDetails);
      }
      else {
        iColWidths = new int[] {
            56};
        rfaReport.setSortColumns(new int[] {0});
        rfaReport.setSortable(true);
        printReport(false, null, iColWidths, vPrintDetails);
      }
      resetPrintvars();
      println(":exmp.");
    }
  }*/

  /**
   *  Description of the Method
   *
   *@param  _eiFeature  Description of the Parameter
   *@return             Description of the Return Value
   */
  private String getfeatureToSofMktMsg(EntityItem _eiFeature) {
    String strReturn = "";
    String strMktMsg = "";
    if (_eiFeature == null) {
      return "";
    }
    //first get Feature mktg msg
    strReturn = getAttributeValue(_eiFeature, "MKTGNAME", " ");
    logMessage("getfeatureToSofMktMsg:Feature :" + strReturn);
    //Then Navigate to component and get Mkt message
    eiNextItem1 = getUplinkedEntityItem(_eiFeature, strFeatureToCmpt);
    strMktMsg = getAttributeValue(eiNextItem1, "MKTGNAME", " ");
    logMessage("getfeatureToSofMktMsg:Component :" + strMktMsg);
    strReturn = strMktMsg + " " + strReturn;
    //Next Navigate to sof and get mkt msg
    strMktMsg = "";
    if (bIsAnnITS && eiNextItem1 != null) {
      strMktMsg = getAttributeValue(eiNextItem1, "ITSCMPNTCATNAME", "");
      logMessage("getfeatureToSofMktMsg:ITSCMPNTCATNAME is: " + strMktMsg);
    }
    if (strMktMsg.trim().length() == 0 && eiNextItem1 != null) {
      strMktMsg = getUplinkedEntityAttrValue(eiNextItem1, strCmptToSof, "MKTGNAME");
      logMessage("getfeatureToSofMktMsg:ITSCMPNTCATNAME is empty...getting SOF MTGNAME: " + strMktMsg);
    }
    logMessage("getfeatureToSofMktMsg:Sof :" + strMktMsg);
    strReturn = strMktMsg + " " + strReturn;
    return strReturn;
  }

  /**
   *  Gets the cmptToSofMktMsg attribute of the RFA_IGSSVS object
   *
   *@param  _eiCmpt  Description of the Parameter
   *@return          The cmptToSofMktMsg value
   */
  private String getCmptToSofMktMsg(EntityItem _eiCmpt) {
    String strReturn = "";
    String strMktMsg = "";
    if (_eiCmpt == null) {
      return "";
    }
    strReturn = getAttributeValue(_eiCmpt, "MKTGNAME", " ");
    logMessage("getCmptToSofMktMsg:Component :" + strReturn);
    //Next Navigate to sof and get mkt msg
    if (bIsAnnITS) {
      strMktMsg = getAttributeValue(_eiCmpt, "ITSCMPNTCATNAME", "");
      logMessage("getCmptToSofMktMsg:ITSCMPNTCATNAME is: " + strMktMsg);
    }
    if (strMktMsg.trim().length() == 0) {
      strMktMsg = getUplinkedEntityAttrValue(_eiCmpt, strCmptToSof, "MKTGNAME");
      logMessage("getCmptToSofMktMsg:ITSCMPNTCATNAME is empty...getting SOF MTGNAME: " + strMktMsg);
    }
    logMessage("getCmptToSofMktMsg:Sof :" + strMktMsg);
    strReturn = strMktMsg + " " + strReturn;
    return strReturn;
  }

  private String getSOFMktName(EntityItem _eiSof) {
    return getSOFMktName(_eiSof, true);
  }

  private String getSOFMktName(EntityItem _eiSof, boolean _bDownLinkToCMPNT) {
    if (_eiSof == null) {
      return "";
    }
    String strMktMsg = "";
    if (bIsAnnITS && _bDownLinkToCMPNT) {
      logMessage("getSOFMktName:Getting downlinked cmpt from " + _eiSof.getKey());
      strMktMsg = getDownlinkedEntityAttrValue(_eiSof, strSofToCmpt, "ITSCMPNTCATNAME");
      logMessage("getSOFMktName:ITSCMPNTCATNAME is: " + strMktMsg);
    }
    if (strMktMsg.trim().length() == 0 || !_bDownLinkToCMPNT) {
      strMktMsg = getAttributeValue(_eiSof, "MKTGNAME", " ");
      logMessage("getSOFMktName:ITSCMPNTCATNAME is empty...getting SOF MTGNAME: " + strMktMsg);
    }
    return strMktMsg;
  }

  private String getQ800SOFMktName(EntityItem _eiCmpnt, EntityItem _eiSof) {
    /*
        Alan Crudo - question 800 does not use any of the --REL-- relators
        zzzzzzzzzzzzzz - what does it use?
        Alan Crudo - it is based on the SOF -> CMPNT -> FEATURE ->
        zzzzzzzzzzzzzz - right..but what is the relator type between them?
        Alan Crudo - SOFCMPNT, CMPNTFEATURE
        zzzzzzzzzzzzzz - ok
        Alan Crudo - question 800 is working finem except for column 1 labeled OFFERING
        zzzzzzzzzzzzzz - and offering code
        zzzzzzzzzzzzzz - ok..I will change it just for those columns
        Alan Crudo - yes, and that needs to come from the association
        zzzzzzzzzzzzzz - ok
        Alan Crudo - thanks

       Multiple avail problem
       Offerings currently sorted by name...and will print geos from multiple availablities
       Since request to group by geos, will have to sort by geos...

     */


    if (_eiCmpnt == null) {
      return "";
    }
    String strMktMsg = "";
    if (bIsAnnITS) {
      strMktMsg = getAttributeShortFlagDesc(_eiCmpnt, "ITSCMPNTCATNAME");
      logMessage("getQ800SOFMktName ShortaName:ITSCMPNTCATNAME is: " + strMktMsg);
      if (strMktMsg == null) {
        strMktMsg = "";
      }
    }
    if (strMktMsg.trim().length() == 0) {
      strMktMsg = getAttributeValue(_eiSof, "MKTGNAME", " ");
      logMessage("getQ800SOFMktName:ITSCMPNTCATNAME is empty...getting SOF MTGNAME: " + strMktMsg);
    }
    return strMktMsg;
  }

  private String getRFADateFormat(String _strDate) {
    String strReturn = "";
    if (_strDate.trim().length() == 0) {
      return " ";
    }
    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMMMMMMMM dd, yyyy"); //Spell out month in its entirety
    try {
      Date date = inputDateFormat.parse(_strDate);
      strReturn = outputDateFormat.format(date);

    }
    catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

    return strReturn;

  }

  private Vector RFAsort(Vector _v) {
    EntityItem[] eArray = getEntityArray(_v);
    RFAsort(eArray);
    return getEntityVector(eArray);
  }

  private void RFAsort(EntityItem[] _e) {
    EntityItem eiTemp = null;
    EntityItem eiTemp1 = null;
    EntityItem eiTemp3 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    if (_e == null) {
      return;
    }

    for (int i = _e.length; --i >= 0; ) {
      boolean swapped = false;

      for (int j = 0; j < i; j++) {
        eiTemp = (EntityItem) _e[j];
        eiTemp1 = (EntityItem) _e[j + 1];
        str3 = eiTemp.getEntityType();
        str4 = eiTemp.getEntityType();
        // go get the strings to be compared
        if (str3.equals("SOF")) {
          str1 = getSOFMktName(eiTemp);
        }
        else if (str3.equals("CMPNT")) {
          str1 = getCmptToSofMktMsg(eiTemp);
        }
        else if (str3.equals("FEATURE")) {
          str1 = getfeatureToSofMktMsg(eiTemp);
        }

        if (str4.equals("SOF")) {
          str2 = getSOFMktName(eiTemp1);
        }
        else if (str4.equals("CMPNT")) {
          str2 = getCmptToSofMktMsg(eiTemp1);
        }
        else if (str4.equals("FEATURE")) {
          str2 = getfeatureToSofMktMsg(eiTemp1);
        }

        logMessage("RFASort:Comparing:" + eiTemp.getKey() + ":" + str1 + ":with:" + eiTemp1.getKey() + ":" + str2);

        if (str1.compareTo(str2) > 0) {
          eiTemp3 = _e[j];
          _e[j] = _e[j + 1];
          _e[j + 1] = eiTemp3;
          swapped = true;
        }
      }

      if (!swapped) {
        return;
      }
    }
  }

}
