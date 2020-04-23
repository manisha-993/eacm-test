/**
 * <pre>
 * (c) Copyright International Business Machines Corporation, 2003
 * All Rights Reserved.
 *
 * $Log: OFABR001.java,v $
 * Revision 1.24  2008/01/30 20:00:55  wendy
 * Cleanup more RSA warnings
 *
 * Revision 1.23  2008/01/30 19:27:44  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.22  2006/10/09 19:30:51  yang
 * minor change
 *
 * Revision 1.21  2006/10/09 17:50:56  yang
 * adding check 4, check 5A, and check 5B
 *
 * Revision 1.20  2006/01/24 22:14:06  yang
 * Jtest changes
 *
 * Revision 1.19  2005/01/31 16:30:06  joan
 * make changes for Jtest
 *
 * Revision 1.18  2004/04/02 15:12:02  chris
 * Fix for Manage Now Ticket: 18604673
 *
 * Revision 1.17  2004/03/25 14:22:15  chris
 * Design changes for 53672:062264
 *
 * Revision 1.16  2003/11/12 20:38:06  chris
 * put Revision back
 *
 * Revision 1.15  2003/11/06 22:01:19  bala
 * EC drop
 *
 * Revision 1.14  2003/10/28 20:57:25  cstolpe
 * Added Bala's changes
 *
 * Revision 1.13  2003/10/23 19:25:36  cstolpe
 * Change message
 *
 * Revision 1.12  2003/10/15 19:36:08  cstolpe
 * Latest Spec changes
 *
 * Revision 1.11  2003/10/08 11:41:07  cstolpe
 * Fix related to FB 52441
 *
 * Revision 1.10  2003/09/22 14:56:21  cstolpe
 * Fix for FB52241 Family and Series association name changed
 *
 * Revision 1.9  2003/09/11 21:44:26  cstolpe
 * Latest Updates
 *
 * Revision 1.8  2003/09/04 20:51:21  cstolpe
 * I forget what these changes were fore I hope we need them
 *
 * Revision 1.7  2003/08/14 14:54:59  cstolpe
 * Use correct status attribute code.
 *
 * Revision 1.6  2003/08/01 14:55:40  cstolpe
 * Fix for feedback 51604:62FCEE
 *
 * Revision 1.5  2003/07/31 16:42:11  cstolpe
 * Remove java code to strip Revision. Done by build now.
 *
 * Revision 1.4  2003/07/28 15:34:43  cstolpe
 * Change DG submit (not final)
 *
 * Revision 1.3  2003/06/20 17:37:52  cstolpe
 * use AttributeChangeHistoryItem.getFlagCode()
 *
 * Revision 1.2  2003/06/19 17:50:48  cstolpe
 * Externalized strings to resource bundles
 *
 * Revision 1.1.1.1  2003/06/19 14:05:04  cstolpe
 * Initial Import
 *
 * </pre>
 *
 *@author     cstolpe
 *@created    May 8, 2003
 */
package COM.ibm.eannounce.abr.psg;

//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.text.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public final class OFABR001 extends PokBaseABR {
    private MessageFormat mfOut = null;
    private Object[] mfParms = new String[10];
    private ResourceBundle msgs = null;
    // Order is Concept, Plan, Develop, Launch, Lifecycle
   // private static final String phaseOrder = "001000500020007000300040";
    //OFPROJECTPHASE    10  Concept
    //OFPROJECTPHASE    50  Plan
    //OFPROJECTPHASE    20  Develop
    //OFPROJECTPHASE    70  Qualify
    //OFPROJECTPHASE    30  Launch
    //OFPROJECTPHASE    40  Life Cycle
    //private static final String statusOrder = "001000400020";
    //Draft             0010
    //Ready for Review  0040
    //Final             0020

    //PROJECTPHASE  10  Concept
    //PROJECTPHASE  50  Plan
    //PROJECTPHASE  20  Develop
    //PROJECTPHASE  70  Qualify
    //PROJECTPHASE  30  Launch
    //PROJECTPHASE  40  Life Cycle
    private StringBuffer rpt = new StringBuffer();
    /**
     *  Execute ABR.
     *
     */
    public void execute_run() {
        EntityGroup eg = null;
        Iterator itMeta;
        Iterator itEI;
        Iterator itMB;
        Iterator itPR;
        Iterator itMEM;
        Iterator i;
        Vector vctPR;
        Vector v1;
        int iOF = 0;
        int iDD = 0;
        int iCountDD = 0;
        String strOF = null;
        String strRAMSLOTSTOT = null;
        String strOPTRAMCONFIG = null;
        String strOFFERINGTYPE = null;
        String strBRANDCODE = null;
        String strSTATUS = null;
        String strRAMSLOTSAVAIL = null;
        java.io.StringWriter exBuf;
        StringBuffer navName = new StringBuffer();
        boolean tmp = true;
        try {
            start_ABRBuild();
            // NAME is navigate attributes
            eg =
                new EntityGroup(
                    null,
                    m_db,
                    m_prof,
                    getRootEntityType(),
                    "Navigate");
            itMeta = eg.getMetaAttribute().values().iterator();
            while (itMeta.hasNext()) {
                EANMetaAttribute ma = (EANMetaAttribute) itMeta.next();
                navName.append(
                    getAttributeValue(
                        getRootEntityType(),
                        getRootEntityID(),
                        ma.getAttributeCode()));
                if (itMeta.hasNext()) {
                    navName.append(" ");
                }
            }
            msgs =
                ResourceBundle.getBundle(
                    this.getClass().getName(),
                    getLocale(m_prof.getReadLanguage().getNLSID()));
            mfParms = new String[10];

            // Output Brand, Family, Series, Project
            mfOut = new MessageFormat(msgs.getString("PATH"));
            itEI =
                m_elist
                .getEntityGroup("PR")
                .getEntityItem()
                .values()
                .iterator();
            while (itEI.hasNext()) {
                EntityItem eiPR = (EntityItem) itEI.next();
                mfParms[0] =
                    getAttributeValue(
                        eiPR.getEntityType(),
                        eiPR.getEntityID(),
                        "BRANDCODE");
                mfParms[1] =
                    getAttributeValue(
                        eiPR.getEntityType(),
                        eiPR.getEntityID(),
                        "FAMNAMEASSOC");
                mfParms[2] =
                    getAttributeValue(
                        eiPR.getEntityType(),
                        eiPR.getEntityID(),
                        "SENAMEASSOC");
                mfParms[3] =
                    getAttributeValue(
                        eiPR.getEntityType(),
                        eiPR.getEntityID(),
                        "NAME");
                rpt.append(mfOut.format(mfParms));
            }

            iOF = getRootEntityID();
            strOF = getRootEntityType();

            // Print PSGOF
            printEntity(strOF, iOF, 1);

            // Output PR BRANDCODE
            mfOut = new MessageFormat(msgs.getString("PARENT_STATUS"));
            vctPR = getParentEntityIds(strOF, iOF, "PR", "PROF");
            itPR = vctPR.iterator();
            while (itPR.hasNext()) {
                int iPR = ((Integer) itPR.next()).intValue();
                mfParms[0] = getAttributeDescription("PR", "NAME");
                mfParms[1] = getAttributeValue("PR", iPR, "NAME");
                mfParms[2] = getAttributeDescription("PR", "PROJECTPHASE");
                mfParms[3] = getAttributeValue("PR", iPR, "PROJECTPHASE");
                rpt.append(mfOut.format(mfParms));
            }

            setReturnCode(PASS);
            // Check 1 only on status trigger if type = system
            //String strOFSTATUSPHASE =
            //  getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "OFSTATUSPHASE", "");
            strOFFERINGTYPE =
                getAttributeFlagEnabledValue(strOF, iOF, "OFFERINGTYPE", "");
            //BRANDCODE 10015   Netfinity
            if (vctPR.size() > 0) {
                strBRANDCODE =
                    getAttributeFlagEnabledValue(
                        "PR",
                        ((Integer) vctPR.firstElement()).intValue(),
                        "BRANDCODE",
                        "");
            }
            strSTATUS =
                getAttributeFlagEnabledValue(strOF, iOF, "STATUS", "");
            if ("0080".equals(strOFFERINGTYPE)) {
                // Memory Form Factor Check needs to be done in English
                m_elist.getProfile().setReadLanguage(Profile.ENGLISH_LANGUAGE);
                // Set RAMSLOTSAVAIL NOTE: DD is not required but there can be only one
                v1 = getChildrenEntityIds(strOF, iOF, "DD", "OFDD");
                iDD = -1;
                strRAMSLOTSAVAIL = "";
                if (!v1.isEmpty()) {
                    iDD = ((Integer) v1.elementAt(0)).intValue();
                    strRAMSLOTSAVAIL =
                        getAttributeValue("DD", iDD, "RAMSLOTSAVAIL", "");
                }

                // set RAMSLOTSTOT and OPTRAMCONFIG
                //v1 = getChildrenEntityIds(strOF, iOF, "MB", "OFMB");
                itMB =
                    m_elist
                    .getEntityGroup("MB")
                    .getEntityItem()
                    .values()
                    .iterator();
                strRAMSLOTSTOT = "";
                strOPTRAMCONFIG = "";
                if (itMB.hasNext()) {
                    EntityItem eiMB = (EntityItem) itMB.next();
                    strRAMSLOTSTOT =
                        getAttributeValue(
                            "MB",
                            eiMB.getEntityID(),
                            "RAMSLOTSTOT",
                            "");
                    strOPTRAMCONFIG =
                        getAttributeValue(
                            "MB",
                            eiMB.getEntityID(),
                            "OPTRAMCONFIG",
                            "");
                    // only on net finity
                }

                // Check MEMORYFORMFACTOR
                itMEM =
                    m_elist
                    .getEntityGroup("MEM")
                    .getEntityItem()
                    .values()
                    .iterator();
                //Iterator itMEM = getChildrenEntityIds(strOF, iOF, "MEM", "OFMEM").iterator();
                if (!itMEM.hasNext()) {
                    rpt.append(msgs.getString("CHECK2_FAIL_MSG1"));
                    setReturnCode(FAIL);
                } else {
                    boolean bFormFactorCheck = true;
                    while (itMEM.hasNext()) {
                        EntityItem eiMEM = (EntityItem) itMEM.next();
                        String strMEMORYFORMFACTOR =
                            getAttributeValue(
                                eiMEM.getEntityType(),
                                eiMEM.getEntityID(),
                                "MEMORYFORMFACTOR",
                                "");
                        //PR18642
                        int n = strMEMORYFORMFACTOR.indexOf(' ');
                        if (n > 0) {
                            strMEMORYFORMFACTOR =
                                strMEMORYFORMFACTOR.substring(0, n);
                        }
                        tmp = true;
                        // Compare the MEM:MEMORYFORMFACTOR description to MB:RAMSLOTSTOT and DD:RAMSLOTSAVAIL for all brands
                        tmp =
                            searchForWord(strRAMSLOTSTOT, strMEMORYFORMFACTOR);
                        bFormFactorCheck &= tmp;
                        if (iDD > -1) {
                            tmp =
                                searchForWord(
                                    strRAMSLOTSAVAIL,
                                    strMEMORYFORMFACTOR);
                            bFormFactorCheck &= tmp;
                        }
                        // Compare the MEM:MEMORYFORMFACTOR description to MB:OPTRAMCONFIG  for Netfinity only.
                        if ("10015".equals(strBRANDCODE)) {
                            tmp =
                                searchForWord(
                                    strOPTRAMCONFIG,
                                    strMEMORYFORMFACTOR);
                            bFormFactorCheck &= tmp;
                        }
                    } //end of for
                    if (bFormFactorCheck) {
                        rpt.append(msgs.getString("CHECK2_PASS_MSG"));
                    } else {
                        if ("10015".equals(strBRANDCODE)) {
                            rpt.append(msgs.getString("CHECK2_FAIL_MSG3"));
                        } else {
                            rpt.append(msgs.getString("CHECK2_FAIL_MSG2"));
                        }
                        setReturnCode(FAIL);
                    }
                }
            }

            // Check 2
            if ("0080".equals(strOFFERINGTYPE)) {
                // all status  and only phase when it is in plan plan
                TreeSet tsMissing = new TreeSet();
                TreeSet tsMissing2 = new TreeSet();
                TreeSet tsTooMany = new TreeSet();
                Vector vSBB = getChildrenEntityIds(strOF, iOF, "SBB", "OFSBB");
                // Check SBB first
                int iCountGRA = 0;
                int iCountHD = 0;
                int iCountMB = 0;
                int iCountMEM = 0;
                int iCountPP = 0;
                int iCountWAR = 0;
                Iterator itSBB = vSBB.iterator();
                while (itSBB.hasNext()) {
                    Integer iSBB = (Integer) itSBB.next();
                    iCountGRA
                        += getChildrenEntityIds(
                            "SBB",
                            iSBB.intValue(),
                            "GRA",
                            "SBBGRA")
                    .size();
                    iCountHD
                        += getChildrenEntityIds(
                            "SBB",
                            iSBB.intValue(),
                            "HD",
                            "SBBHD")
                    .size();
                    iCountMB
                        += getChildrenEntityIds(
                            "SBB",
                            iSBB.intValue(),
                            "MB",
                            "SBBMB")
                    .size();
                    iCountMEM
                        += getChildrenEntityIds(
                            "SBB",
                            iSBB.intValue(),
                            "MEM",
                            "SBBMEM")
                    .size();
                    iCountPP
                        += getChildrenEntityIds(
                            "SBB",
                            iSBB.intValue(),
                            "PP",
                            "SBBPP")
                    .size();
                    iCountWAR
                        += getChildrenEntityIds(
                            "SBB",
                            iSBB.intValue(),
                            "WAR",
                            "SBBWAR")
                    .size();
                }

                iCountDD =
                    getChildrenEntityIds(strOF, iOF, "DD", "OFDD").size();
                iCountGRA
                    += getChildrenEntityIds(strOF, iOF, "GRA", "OFGRA").size();
                iCountHD
                    += getChildrenEntityIds(strOF, iOF, "HD", "OFHD").size();
                iCountMB
                    += getChildrenEntityIds(strOF, iOF, "MB", "OFMB").size();
                iCountMEM
                    += getChildrenEntityIds(strOF, iOF, "MEM", "OFMEM").size();
                iCountPP
                    += getChildrenEntityIds(strOF, iOF, "PP", "OFPP").size();
                iCountWAR
                    += getChildrenEntityIds(strOF, iOF, "WAR", "OFWAR").size();

                if (iCountDD > 1) {
                    tsTooMany.add("DD");
                }
                if (iCountGRA == 0) {
                    tsMissing2.add("GRA");
                }
                if (iCountHD == 0) {
                    tsMissing2.add("HD");
                }
                if (iCountMB == 0) {
                    tsMissing.add("MB");
                }
                if (iCountMB > 1) {
                    tsTooMany.add("MB");
                }
                if (iCountMEM == 0) {
                    tsMissing.add("MEM");
                }
                if (iCountPP == 0) {
                    tsMissing.add("PP");
                }
                if (iCountPP > 1) {
                    tsTooMany.add("PP");
                }
                if (iCountWAR == 0) {
                    tsMissing.add("WAR");
                }
                if (iCountWAR > 1) {
                    tsTooMany.add("WAR");
                }
                if (tsTooMany.isEmpty()
                    && tsMissing.isEmpty()
                    && tsMissing2.isEmpty()) {
                    rpt.append(msgs.getString("CHECK3_PASS_MSG"));
                } else {
                    setReturnCode(FAIL);
                }
                i = tsMissing.iterator();
                mfOut = new MessageFormat(msgs.getString("CHECK3_MISSING_MSG"));
                while (i.hasNext()) {
                    String entityType = (String) i.next();
                    mfParms[0] = getEntityDescription(entityType);
                    rpt.append(mfOut.format(mfParms));
                }
                i = tsTooMany.iterator();
                mfOut =
                    new MessageFormat(msgs.getString("CHECK3_TOO_MANY_MSG"));
                while (i.hasNext()) {
                    String entityType = (String) i.next();
                    mfParms[0] = getEntityDescription(entityType);
                    rpt.append(mfOut.format(mfParms));
                }
                i = tsMissing2.iterator();
                mfOut =
                    new MessageFormat(msgs.getString("CHECK3_MISSING_MSG2"));
                while (i.hasNext()) {
                    String entityType = (String) i.next();
                    mfParms[0] = getEntityDescription(entityType);
                    rpt.append(mfOut.format(mfParms));
                }
            }
            // Check 3A & B
            // Compare project phase (only to first parent found)
            // Order is Concept, Plan, Develop, Launch, Lifecycle
            if (getReturnCode() == PASS) {
                // A or B determined by How Queued (recorded in OFSTATUSPHASE)
                //logMessage("OFABR001 strOFSTATUSPHASE = " + strOFSTATUSPHASE);

                String strBAVLFORSPECIALBID =
                    getAttributeFlagEnabledValue(
                        getRootEntityType(),
                        getRootEntityID(),
                        "BAVLFORSPECIALBID",
                        "");
                /*String strPROJECTPHASE = null;
                if (vctPR.size() > 0) {
                    strPROJECTPHASE =
                        getAttributeFlagEnabledValue(
                            "PR",((Integer) vctPR.firstElement()).intValue(),"PROJECTPHASE", "");
                }*/
                // Check 3A
                // Check 3B
                if ("0010".equals(strSTATUS)) { // Draft
                    if ("0020".equals(strBAVLFORSPECIALBID)) { // Yes
                        setControlBlock();
                        setFlagValue("STATUS", "0020"); // Final
                    } else {
                        setControlBlock();
                        setFlagValue("STATUS", "0040"); // Ready for Review
                    }
                } else if ("0040".equals(strSTATUS)) { // Ready for Review
                    setControlBlock();
                    setFlagValue("STATUS", "0020"); // Final
                } else if ("0050".equals(strSTATUS)) { // Change Request
                    if ("0020".equals(strBAVLFORSPECIALBID)) { // Yes
                        setControlBlock();
                        setFlagValue("STATUS", "0020"); // Final
                    } else {
                        setControlBlock();
                        setFlagValue("STATUS", "0040"); // Ready for Review
                    }
                }
            }



            //  Check 4
            //If OFFERINGTYPE = 'System' and BRCODE = 'Mobile'
            if (getReturnCode() == PASS) {
                System.out.println("***********Starting check 4*******************");
                strOFFERINGTYPE = null;
                strOFFERINGTYPE = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "OFFERINGTYPE", "");
                    System.out.println("strOFFERINGTYPE for check 4" + getRootEntityType() + ": " + getRootEntityID() + ": "+ strOFFERINGTYPE);
                if (strOFFERINGTYPE.equals("0080")) {
                strBRANDCODE = null;
                if (vctPR.size() > 0) {
                    strBRANDCODE = getAttributeFlagEnabledValue("PR", ((Integer) vctPR.firstElement()).intValue(), "BRANDCODE", "");
                        System.out.println("strBRANDCODE for check 4" + ": "+ strBRANDCODE);
                    if (strBRANDCODE.equals("10014")) {
                        String strSCREENSIZEVIEW_IN = null;
                        strSCREENSIZEVIEW_IN = getAttributeFlagEnabledValue("MON", ((Integer) vctPR.firstElement()).intValue(), "SCREENSIZEVIEW_IN", "");
                        System.out.println("strSCREENSIZEVIEW_IN for check 4" + ": "+ strSCREENSIZEVIEW_IN);
                        if (strSCREENSIZEVIEW_IN != null) {
                            rpt.append(msgs.getString("CHECK4_PASS_MSG"));
                        } else {
                            setReturnCode(FAIL);
                            rpt.append(msgs.getString("CHECK4_FAIL_MSG"));
                        }
                    }
                }
            }
                System.out.println("***********Finished check 4*******************");
        }


            //  Check 5A & 5B
            //  If OFFERINGTYPE = 'System' AND (BRCODE = 'Mobile' OR BRCODE = 'CDT')
            //  check 5A -  If DD.MEMRAMSTD is not null
            //  check 5B -  If DD.MEMRAMSTDUNITS is not null
            if (getReturnCode() == PASS) {
                System.out.println("***********Starting check 5*******************");
                strOFFERINGTYPE = null;
                strOFFERINGTYPE = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "OFFERINGTYPE", "");
                    System.out.println("strOFFERINGTYPE for check 5" + getRootEntityType() + ": " + getRootEntityID() + ": "+ strOFFERINGTYPE);
                if (strOFFERINGTYPE.equals("0080")) {
                strBRANDCODE = null;
                if (vctPR.size() > 0) {
                    strBRANDCODE = getAttributeFlagEnabledValue("PR", ((Integer) vctPR.firstElement()).intValue(), "BRANDCODE", "");
                        System.out.println("strBRANDCODE for check 5" + ": "+ strBRANDCODE);
                    if (strBRANDCODE.equals("10014") || strBRANDCODE.equals("10010")) {
                        String strMEMRAMSTD = null;
                        String strMEMRAMSTDUNITS = null;
                        strMEMRAMSTD = getAttributeFlagEnabledValue("DD", ((Integer) vctPR.firstElement()).intValue(), "MEMRAMSTD", "");
                        strMEMRAMSTDUNITS = getAttributeFlagEnabledValue("DD", ((Integer) vctPR.firstElement()).intValue(), "MEMRAMSTDUNITS", "");
                        System.out.println("strMEMRAMSTD for check 5A" + ": "+ strMEMRAMSTD);
                        System.out.println("strMEMRAMSTDUNITS for check 5B" + ": "+ strMEMRAMSTDUNITS);
                        if (strMEMRAMSTD != null) {
                            rpt.append(msgs.getString("CHECK5A_PASS_MSG"));
                        } else {
                            setReturnCode(FAIL);
                            rpt.append(msgs.getString("CHECK5A_FAIL_MSG"));
                        }
                        if (strMEMRAMSTDUNITS != null) {
                            rpt.append(msgs.getString("CHECK5B_PASS_MSG"));
                        } else {
                            setReturnCode(FAIL);
                            rpt.append(msgs.getString("CHECK5B_FAIL_MSG"));
                        }
                    }
                }
            }
                System.out.println("***********Finished check 5*******************");
        }



        } catch (LockPDHEntityException le) {
            setReturnCode(FAIL);
            mfOut = new MessageFormat(msgs.getString("LOCK_ERROR"));
            mfParms[0] = ERR_IAB1007E;
            mfParms[1] = le.getMessage();
            rpt.append(mfOut.format(mfParms));
            logError(le.getMessage());
        } catch (UpdatePDHEntityException le) {
            setReturnCode(FAIL);
            mfOut = new MessageFormat(msgs.getString("UPDATE_ERROR"));
            mfParms[0] = le.getMessage();
            rpt.append(mfOut.format(mfParms));
            logError(le.getMessage());
        } catch (Exception exc) {
            setReturnCode(FAIL);
            // Report this error to both the datbase log and the PrintWriter
            mfOut = new MessageFormat(msgs.getString("EXCEPTION_ERROR"));
            mfParms[0] = m_abri.getABRCode();
            mfParms[1] = exc.getMessage();
            rpt.append(mfOut.format(mfParms));
            exBuf = new java.io.StringWriter();
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            rpt.append("<pre>");
            rpt.append(exBuf.getBuffer().toString());
            rpt.append("</pre>");
        } finally {
            // make sure the lock is released
            setDGTitle(navName.toString());
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("WWABR");
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }
        // Insert Header into beginning of report
        navName.append((getReturnCode() == PASS) ? " Passed" : " Failed");
        mfOut = new MessageFormat(msgs.getString("HEADER"));
        mfParms[0] = getShortClassName(getClass());
        mfParms[1] = navName.toString();
        mfParms[2] = getNow();
        mfParms[3] = m_prof.getOPName();
        mfParms[4] = m_prof.getRoleDescription();
        mfParms[5] = getDescription();
        mfParms[6] = getABRVersion();
        rpt.insert(0, mfOut.format(mfParms));
        println(rpt.toString()); // Output the Report
        printDGSubmitString();
        buildReportFooter();
    }

    /**
     *  Get ABR description
     *
     *@return    java.lang.String
     */
    public String getDescription() {
        return msgs.getString("DESCRIPTION");
    }

    /**
     *  Get the version
     *
     *@return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.24 $";
    }

    private boolean searchForWord(String aText, String aStr) {
        int n = aStr.length();
        StringTokenizer st = new StringTokenizer(aText);
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            if (str.length() > n) {
                str = str.substring(0, n);
            }
            if (str.equalsIgnoreCase(aStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Sets the specified Flag Attribute on the Root Entity
     *
     *@param    strAttributeCode The Flag Attribute Code
     *@param    strAttributeValue The Flag Attribute Value
     */
    private void setFlagValue(
        String strAttributeCode,
        String strAttributeValue) {
        logMessage("****** strAttributeValue set to: " + strAttributeValue);

        if (strAttributeValue != null) {
            try {
                EntityItem eiParm =
                    new EntityItem(
                        null,
                        m_prof,
                        getEntityType(),
                        getEntityID());
                ReturnEntityKey rek =
                    new ReturnEntityKey(
                        eiParm.getEntityType(),
                        eiParm.getEntityID(),
                        true);

                SingleFlag sf =
                    new SingleFlag(
                        m_prof.getEnterprise(),
                        rek.getEntityType(),
                        rek.getEntityID(),
                        strAttributeCode,
                        strAttributeValue,
                        1,
                        m_cbOn);
                Vector vctAtts = new Vector();
                Vector vctReturnsEntityKeys = new Vector();

                if (sf != null) {
                    vctAtts.addElement(sf);

                    rek.m_vctAttributes = vctAtts;
                    vctReturnsEntityKeys.addElement(rek);

                    m_db.update(m_prof, vctReturnsEntityKeys, false, false);
                    m_db.commit();
                }
            } catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
                logMessage("setFlagValue: " + e.getMessage());
            } catch (Exception e) {
                logMessage("setFlagValue: " + e.getMessage());
            }
        }
    }
    private Locale getLocale(int nlsID) {
        Locale locale = null;
        switch (nlsID) {
        case 1 :
            locale = Locale.US;
        case 2 :
            locale = Locale.GERMAN;
            break;
        case 3 :
            locale = Locale.ITALIAN;
            break;
        case 4 :
            locale = Locale.JAPANESE;
            break;
        case 5 :
            locale = Locale.FRENCH;
            break;
        case 6 :
            locale = new Locale("es", "ES");
            break;
        case 7 :
            locale = Locale.UK;
            break;
        default :
            locale = Locale.US;
        }
        return locale;
    }
    /**
     *  Insert the method's description here. Creation date: (8/1/2001 8:23:46 AM)
     *
     *@param  _strEntityType  Description of the Parameter
     *@param  _iEntityID      Description of the Parameter
     *@param  _iLevel         Description of the Parameter
     */
    public void printEntity(
        String _strEntityType,
        int _iEntityID,
        int _iLevel) {
        String strPSGClass = null;

        logMessage(
            "In printEntity _strEntityType"
                + _strEntityType
                + ":_iEntityID:"
                + _iEntityID
                + ":_iLevel:"
                + _iLevel);

        strPSGClass = "";
        switch (_iLevel) {
        case 0 :
            {
                strPSGClass = "PsgReportSection";
                break;
            }
        case 1 :
            {
                strPSGClass = "PsgReportSectionII";
                break;
            }
        case 2 :
            {
                strPSGClass = "PsgReportSectionIII";
                break;
            }
        case 3 :
            {
                strPSGClass = "PsgReportSectionIV";
                break;
            }
        default :
            {
                strPSGClass = "PsgReportSectionV";
                break;
            }
        }
        logMessage("Printing table width");
        rpt.append(
            "<table width=\"100%\"><tr><td class=\""
                + strPSGClass
                + "\">"
                + getEntityDescription(_strEntityType)
                + ": "
                + getAttributeValue(
                    _strEntityType,
                    _iEntityID,
                    "NAME",
                    DEF_NOT_POPULATED_HTML)
                + "</td></tr></table>");
        logMessage("Printing Attributes");
        printAttributes(_strEntityType, _iEntityID, false, false);
    }
    /**
     *  Insert the method's description here. Creation date: (8/1/2001 8:23:46 AM)
     *
     *@param  _strEntityType  Description of the Parameter
     *@param  _iEntityID      Description of the Parameter
     *@param  _bAll           Description of the Parameter
     *@param  _bShortDesc     Description of the Parameter
     */
    public void printAttributes(
        String _strEntityType,
        int _iEntityID,
        boolean _bAll,
        boolean _bShortDesc) {
        printAttributes(
            m_elist,
            _strEntityType,
            _iEntityID,
            _bAll,
            _bShortDesc);
    }

    /**
     *  Description of the Method
     *
     *@param  _elist          Description of the Parameter
     *@param  _strEntityType  Description of the Parameter
     *@param  _iEntityID      Description of the Parameter
     *@param  _bAll           Description of the Parameter
     *@param  _bShortDesc     Description of the Parameter
     */
    public void printAttributes(
        EntityList _elist,
        String _strEntityType,
        int _iEntityID,
        boolean _bAll,
        boolean _bShortDesc) {
        //Get list of attributes for entity
        EntityItem entItem = null;
        EntityGroup eg = null;
        String streDesc = null;
        Hashtable htValues;
        Vector vctTmp;
        String strValue = null;
        String strDesc1 = null;
        String[] astrCodeDesc;
        SortUtil sort = null;
        int nRows = 0;
        int col2Index = 0;
        logMessage(
            "in Print Attributes _strEntityType"
                + _strEntityType
                + ":_iEntityID:"
                + _iEntityID);

        if (_strEntityType.equals(getEntityType())) {
            //get the parent entity group for root entity
            eg = _elist.getParentEntityGroup();
        } else {
            eg = _elist.getEntityGroup(_strEntityType);
        }

        if (eg == null) {
            rpt.append(
                "<h3>Warning: Cannot locate an EnityGroup for "
                    + _strEntityType
                    + " so no attributes will be printed.</h3>");
            return;
        }

        entItem = eg.getEntityItem(_strEntityType, _iEntityID);
        //EntityItem entItem = entGroup.getEntityItem(_strEntityType,_iEntityID);
        if (entItem == null) {
            // Lets display the EntityType, entityId in the root..)
            entItem = eg.getEntityItem(0);
            rpt.append(
                "<h3>Warning: Attributes for "
                    + _strEntityType
                    + ":"
                    + _iEntityID
                    + " cannot be printed as it is not available in the Extract.</h3>");
            rpt.append(
                "<h3>Warning: Root Entityis "
                    + getEntityType()
                    + ":"
                    + getEntityID()
                    + ".</h3>");
            return;
        }

        streDesc = eg.getLongDescription();
        logMessage("Print Attributes Entity desc is " + streDesc);
        logMessage("Attribute count is" + entItem.getAttributeCount());
        htValues = new Hashtable();
        vctTmp = new Vector();
        for (int i = 0; i < entItem.getAttributeCount(); i++) {

            EANAttribute EANatt = entItem.getAttribute(i);
            logMessage("printAttributes " + EANatt.dump(false));
            logMessage("printAttributes " + EANatt.dump(true));

            strValue =
                getAttributeValue(
                    _strEntityType,
                    _iEntityID,
                    EANatt.getAttributeCode(),
                    DEF_NOT_POPULATED_HTML);
            strDesc1 = "";

            // Use short description or long description?
            if (_bShortDesc) {
                strDesc1 =
                    getMetaDescription(
                        _strEntityType,
                        EANatt.getAttributeCode(),
                        false);
            } else {
                strDesc1 =
                    getAttributeDescription(
                        _strEntityType,
                        EANatt.getAttributeCode());
            }
            // Strip entity description from beginning of attribute description
            if (strDesc1.length() > streDesc.length()
                && strDesc1.substring(0, streDesc.length()).equalsIgnoreCase(
                    streDesc)) {
                strDesc1 = strDesc1.substring(streDesc.length());
            }
            // Did we only want populated attributes?
            if (_bAll || strValue != null) {
                // associate truncated description with its value
                htValues.put(strDesc1, strValue);
                // keep track of attributes to display
                vctTmp.add(strDesc1);
            }
        }
        astrCodeDesc = new String[entItem.getAttributeCount()];

        if (!_bAll) {
            astrCodeDesc = new String[vctTmp.size()];
            for (int i = 0; i < astrCodeDesc.length; i++) {
                astrCodeDesc[i] = (String) vctTmp.elementAt(i);
            }
        }

        // Sort on attribute code description
        sort = new SortUtil();
        sort.sort(astrCodeDesc);

        // Output attributes in a two column tale
        rpt.append("<table width=\"100%\">");
        nRows = astrCodeDesc.length - (astrCodeDesc.length / 2);
        for (int i = 0; i < nRows; i++) {
            rpt.append(
                "<tr><td class=\"PsgLabel\" valign=\"top\">"
                    + astrCodeDesc[i]
                    + "</td><td class=\"PsgText\" valign=\"top\">"
                    + htValues.get(astrCodeDesc[i])
                    + "</td>");
            col2Index = nRows + i;
            if (col2Index < astrCodeDesc.length) {
                rpt.append(
                    "<td class=\"PsgLabel\" valign=\"top\">"
                        + astrCodeDesc[col2Index]
                        + "</td><td class=\"PsgText\" valign=\"top\">"
                        + htValues.get(astrCodeDesc[col2Index])
                        + "</td><tr>");
            } else {
                rpt.append(
                    "<td class=\"PsgLabel\">"
                        + "</td><td class=\"PsgText\">"
                        + "</td><tr>");
            }
        }
        rpt.append("</table>\n<br />");
    }
    /**
     *  Get the meta attribute or task description, null if not found
     *
     *@param  _strEtype     Description of the Parameter
     *@param  _strAttrCode  Description of the Parameter
     *@param  _bLongDesc    Description of the Parameter
     *@return               The metaDescription value
     *@returns              String attribute description
     */

    private String getMetaDescription(
        String _strEtype,
        String _strAttrCode,
        boolean _bLongDesc) {
        return getMetaDescription(m_elist, _strEtype, _strAttrCode, _bLongDesc);
    }

    /**
     *  Gets the metaDescription attribute of the PokBaseABR object
     *
     *@param  _elist        Description of the Parameter
     *@param  _strEtype     Description of the Parameter
     *@param  _strAttrCode  Description of the Parameter
     *@param  _bLongDesc    Description of the Parameter
     *@return               The metaDescription value
     */
    private String getMetaDescription(
        EntityList _elist,
        String _strEtype,
        String _strAttrCode,
        boolean _bLongDesc) {
        EANMetaAttribute ema = null;
        String desc = null;
        EntityGroup entGroup = _elist.getEntityGroup(_strEtype);
        if (entGroup == null) {
            logError(
                "Did not find EntityGroup: "
                    + _strEtype
                    + " in entity list to extract getMetaDescription");
            return null;
        }

        ema = null;
        if (entGroup != null) {
            ema = entGroup.getMetaAttribute(_strAttrCode);
        }
        desc = null;
        if (ema != null) {
            if (_bLongDesc) {
                desc = ema.getLongDescription();
            } else {
                desc = ema.getShortDescription();
            }
        }
        return desc;
    }
}
