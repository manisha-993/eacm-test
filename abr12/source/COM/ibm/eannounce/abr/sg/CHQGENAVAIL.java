//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//CHQGENAVAIL.java,v
//Revision 1.54  2008/01/30 19:39:17  wendy
//Cleanup RSA warnings
//
//Revision 1.53  2006/03/03 19:23:27  bala
//remove reference to Constants.CSS
//
//Revision 1.52  2006/01/31 23:33:53  joan
//changes
//
//Revision 1.51  2006/01/26 00:36:19  joan
//Jtest
//
//Revision 1.50  2005/12/06 20:38:04  joan
//fixes
//
//Revision 1.49  2005/12/06 19:43:35  joan
//fixes
//
//Revision 1.48  2005/12/06 19:40:48  joan
//fixes
//
//Revision 1.47  2005/10/11 21:33:25  joan
//fixes
//
//Revision 1.46  2005/08/23 15:59:27  joan
//put back the fixes
//
//Revision 1.45  2005/08/19 18:20:22  joan
//back out fix for MN
//
//Revision 1.44  2005/08/18 20:44:35  joan
//fix for MN 25063112
//
//Revision 1.43  2005/05/16 21:12:18  joan
//fixes
//
//Revision 1.42  2005/04/26 20:19:24  joan
//fix for findEntityItem
//
//Revision 1.41  2005/04/18 18:22:29  joan
//fixes
//
//Revision 1.40  2005/04/11 20:39:30  joan
//fixes
//
//Revision 1.39  2005/04/11 19:52:45  joan
//fixes
//
//Revision 1.38  2005/04/01 19:08:54  joan
//fixes
//
//Revision 1.37  2005/03/02 20:50:32  joan
//fixes
//
//Revision 1.36  2005/02/16 22:13:28  joan
//fixes
//
//Revision 1.35  2005/02/14 15:52:49  joan
//fixes
//
//Revision 1.34  2005/02/11 16:35:23  joan
//fixes
//
//Revision 1.33  2005/02/03 16:09:31  joan
//fixes
//
//Revision 1.32  2005/02/03 00:23:17  joan
//fixes
//
//Revision 1.31  2005/02/02 23:33:22  joan
//fixes
//
//Revision 1.29  2005/02/02 18:33:08  joan
//fixes
//
//Revision 1.28  2005/02/02 18:05:39  joan
//fixes
//
//Revision 1.27  2005/01/31 19:22:00  joan
//changes for CR2727
//
//Revision 1.25  2004/12/09 21:32:46  joan
//fixes
//
//Revision 1.24  2004/12/03 22:36:31  joan
//fixes for search
//
//Revision 1.23  2004/12/01 22:31:59  joan
//fixes
//
//Revision 1.22  2004/11/29 18:55:06  joan
//fixes
//
//Revision 1.21  2004/11/24 17:48:36  joan
//fixes
//
//Revision 1.20  2004/11/15 21:38:41  joan
//fixes
//
//Revision 1.19  2004/11/15 16:37:14  joan
//changes
//
//Revision 1.18  2004/11/13 01:03:45  joan
//fixes
//
//Revision 1.17  2004/11/13 00:29:59  joan
//fixes
//
//Revision 1.16  2004/11/09 16:24:09  joan
//fixes
//
//Revision 1.15  2004/11/08 23:10:16  joan
//remove comments
//
//Revision 1.14  2004/10/27 20:27:06  joan
//fix TIR
//
//Revision 1.13  2004/10/13 16:37:58  joan
//fixes
//
//Revision 1.12  2004/10/12 21:57:07  joan
//fixes
//
//Revision 1.11  2004/10/11 22:14:22  joan
//fixes
//
//Revision 1.10  2004/10/09 23:20:03  bala
//fix for typo in updateatribute attributecode
//
//Revision 1.9  2004/10/05 19:45:20  joan
//fixes
//
//Revision 1.8  2004/10/01 18:33:15  joan
//fixes
//
//Revision 1.7  2004/10/01 00:09:31  joan
//fixes
//
//Revision 1.6  2004/09/30 20:22:06  joan
//fixes
//
//Revision 1.5  2004/09/30 15:48:37  joan
//fixes
//
//Revision 1.4  2004/09/29 00:23:51  joan
//fix
//
//Revision 1.3  2004/09/29 00:21:14  joan
//fixes
//
//Revision 1.2  2004/09/29 00:17:41  joan
//fixes
//
//Revision 1.1  2004/09/29 00:09:44  joan
//add new abr
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
import java.sql.SQLException;
import java.sql.Date;

/**
 * CHQGENAVAIL
 *@author     Administrator
 *@created    August 30, 2002
 */
public class CHQGENAVAIL extends PokBaseABR {

    /**Class constants
     */
    public final static String ABR = new String("CHQGENAVAIL");
    /**Class constants
     */
    public final static String MTM = new String("MTM");
    /**Class constants
     */
    public final static String RELATEDFC = new String("RELATED FEATURES");
    /**Class constants
     */

    public final static String RELATEDFCCONV = new String("RELATED CONVERSIONS");
    /**Class constants
     */

    public final static String NEWFC = new String("NEW FEATURES");
    /**Class constants
     */

    public final static String FCCONVERSIONS = new String("FEATURE CONVERSIONS");
    /**Class constants
     */

    public final static String WDMTM = new String("WITHDRAW MTM");
    /**Class constants
     */

    public final static String WDFC = new String("WITHDRAWING FEATURES");
    /**Class constants
     */

    public final static String RFATITLE = new String("RFA TITLE");
    /**Class constants
     */

    public final static int EQUAL = 0;
    /**Class constants
     */

    public final static int LATER = 1;
    /**Class constants
     */

    public final static int EARLIER = 2;
    /**Class constants
     */

    public final static int MODEL = 0;
    /**Class constants
     */

    public final static int FEATURE = 1;
    /**Class constants
     */

    public final static int WITHDRAWMTM = 2;
    /**Class constants
     */

    public final static int WITHDRAWFC = 3;
    /**Class constants
     */

    public final static int FCONV = 4;
    /**Class constants
     */

    public final static int MCONV = 5;
    /**Class constants
     */
    public final static char QM = '?';

    private EntityGroup m_egParent = null;
    private EntityItem m_ei = null;
    private PDGUtility m_utility = new PDGUtility();
    private StringBuffer m_sbError = new StringBuffer();
    private OPICMList m_availAtts = new OPICMList();
    private OPICMList m_availWDAtts = new OPICMList();

    private String m_strAnnDate = null;
    private String m_strRFAProf = null;
    private String m_strInventoryGroup = null;
    private ExtractActionItem m_xaiAutoGen1 = null;
    private ExtractActionItem m_xaiAutoGen2 = null;
    private ExtractActionItem m_xaiAutoGen3 = null;
    private ExtractActionItem m_xaiAutoGen4 = null;
    private ExtractActionItem m_xaiAutoGen5 = null;
    private EANList m_newAvailList = new EANList();
    private final String[] m_updateAvailAttrs = {"COMNAME", "EFFECTIVEDATE", "GENAREASELECTION", "COUNTRYLIST"};
    private EntityGroup m_egAVAIL = null;

    /**
    *  Execute ABR.
    *
    */
    public void execute_run() {
        try {
            OPICMList list = null;
            // if it's the first time, build the report header


            start_ABRBuild();
            // Build the report header
            buildReportHeaderII();

            m_egParent = m_elist.getParentEntityGroup();
            m_ei = m_egParent.getEntityItem(0);
            println("<br><b>Announcement: " + m_ei.getKey() + "</b>");

            printNavigateAttributes(m_ei, m_egParent, true);
            setReturnCode(PASS);
            getAnnAtributes(m_elist, m_ei);

            m_xaiAutoGen1 = new ExtractActionItem(null, m_db, m_prof, "EXTAUTOGEN01");
            m_xaiAutoGen2 = new ExtractActionItem(null, m_db, m_prof, "EXTAUTOGEN02");
            m_xaiAutoGen3 = new ExtractActionItem(null, m_db, m_prof, "EXTAUTOGEN03");
            m_xaiAutoGen4 = new ExtractActionItem(null, m_db, m_prof, "EXTAUTOGEN04");
            m_xaiAutoGen5 = new ExtractActionItem(null, m_db, m_prof, "EXTAUTOGEN05");
            m_egAVAIL = new EntityGroup(null, m_db, m_prof, "AVAIL", "Edit", false);
            availHandling();
            m_prof = m_utility.setProfValOnEffOn(m_db, m_prof);
            if (m_strRFAProf != null && m_strRFAProf.length() > 0) {
                processRFAProf(m_strRFAProf);
            } else {
                setReturnCode(FAIL);
                m_sbError.append(NEW_LINE + "Error: RFAPROFILE is empty");
            }

            if (m_sbError.toString().length() > 0) {
                println("<h3><font color=red>" + replace(m_sbError.toString(), NEW_LINE, "<br>") + "</h3>");
            }

            list = new OPICMList();

            list.put("RFALOG", "RFALOG= " + m_sbError.toString());
            if (getReturnCode() == PASS) {
                list.put("CHQAUTOGEN", "CHQAUTOGEN=0020");
            } else {
                list.put("CHQAUTOGEN", "CHQAUTOGEN=0010");
            }

            m_utility.updateAttribute(m_db, m_prof, m_ei, list);
        } catch (LockPDHEntityException le) {
            setReturnCode(UPDATE_ERROR);
            println("<h3><font color=red>" + ERR_IAB1007E + "<br />" + le.getMessage() +  "</font></h3>");
            logError(le.getMessage());
        } catch (UpdatePDHEntityException le) {
            setReturnCode(UPDATE_ERROR);
            println("<h3><font color=red>UpdatePDH error: " + le.getMessage() + "</font></h3>");
            logError(le.getMessage());
        } catch (SBRException _sbrex) {
            String strError = _sbrex.toString();
            int i = strError.indexOf("(ok)");
            if (i < 0) {
                setReturnCode(UPDATE_ERROR);
                println("<h3><font color=red>Generate Data error: " + replace(strError, NEW_LINE, "<br>") + "</font></h3>");
                logError(_sbrex.toString());
            } else {
                strError = strError.substring(0,i);
                println(replace(strError, NEW_LINE, "<br>"));
            }
        } catch (Exception exc) {
            // Report this error to both the datbase log and the PrintWriter
            println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
            println("" + exc);
            exc.printStackTrace();
            exc.printStackTrace();
            // don't overwrite an update exception
            if (getABRReturnCode() != UPDATE_ERROR) {
                setReturnCode(INTERNAL_ERROR);
            }
        } finally {
      String strDgName = null;
            println(
                "<br /><b>" +
                buildMessage(
                    MSG_IAB2016I,
                    new String[]{
                        getABRDescription(),
                        (getReturnCode() == PASS ? "Passed" : "Failed")}) +
                    "</b>");

            log(buildLogMessage(MSG_IAB2016I,new String[]{getABRDescription(),(getReturnCode() == PASS ? "Passed" : "Failed")}));

              // set DG title
            strDgName = m_ei.toString();
            if (strDgName.length() > 64) {
                strDgName = strDgName.substring(0,64);
            }
            setDGTitle(strDgName);
            setDGRptName(ABR);

            // set DG submit string
            setDGString(getABRReturnCode());
            printDGSubmitString();      //Stuff into report for subscription and notification

              // Tack on the DGString
            buildReportFooter();
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }
    }

    private void getAnnAtributes(EntityList _el, EntityItem _ei) {
        String strTraceBase = "CHQGENAVAIL trace getAnnAttributes method ";
        System.out.println(strTraceBase);
        m_availAtts.put("AVAIL:AVAILTYPE", "map_AVAILTYPE=146");
        m_availWDAtts.put("AVAIL:AVAILTYPE", "map_AVAILTYPE=149");

        for (int i =0; i < _ei.getAttributeCount(); i++) {
      //int index = -1;
            EANAttribute att = _ei.getAttribute(i);
            StringBuffer sb = new StringBuffer();

            if (att instanceof EANTextAttribute) {
                sb.append(((String)att.get()).trim());
            } else if (att instanceof EANFlagAttribute) {
                MetaFlag[] mfa = (MetaFlag[])att.get();
                boolean bFirst = true;
                for (int f=0; f < mfa.length; f++) {
                    MetaFlag mf = mfa[f];
                    if (mf.isSelected()) {
                        sb.append(bFirst ? "" : ",");
                        sb.append(mf.getFlagCode());
                        bFirst = false;
                    }
                }
            }

            if (att.getKey().equals("ANNCODENAME")) {
                m_availAtts.put("AVAIL:ANNCODENAME", "map_ANNCODENAME=" + sb.toString());
                m_availWDAtts.put("AVAIL:ANNCODENAME", "map_ANNCODENAME=" + sb.toString());
            } else if (att.getKey().equals("ANNDATE")) {
                m_strAnnDate = sb.toString();
                m_availAtts.put("AVAIL:EFFECTIVEDATE", "EFFECTIVEDATE=" + m_strAnnDate);
                m_availWDAtts.put("AVAIL:EFFECTIVEDATE", "EFFECTIVEDATE=" + m_strAnnDate);
            } else if (att.getKey().equals("GENAREASELECTION")) {
                m_availAtts.put("AVAIL:GENAREASELECTION", "GENAREASELECTION=" + sb.toString());
                m_availWDAtts.put("AVAIL:GENAREASELECTION", "GENAREASELECTION=" + sb.toString());
            } else if (att.getKey().equals("COUNTRYLIST")) {
                m_availAtts.put("AVAIL:COUNTRYLIST", "COUNTRYLIST=" + sb.toString());
                m_availWDAtts.put("AVAIL:COUNTRYLIST", "COUNTRYLIST=" + sb.toString());
            } else if (att.getKey().equals("PDHDOMAIN")) {
                m_availAtts.put("AVAIL:PDHDOMAIN", "PDHDOMAIN=" + sb.toString());
                m_availWDAtts.put("AVAIL:PDHDOMAIN", "PDHDOMAIN=" + sb.toString());
            } else if (att.getKey().equals("RFAPROFILE")) {
                m_strRFAProf = sb.toString();
            } else if (att.getKey().equals("INVENTORYGROUP")) {
                m_strInventoryGroup = sb.toString();
            }
        }
    }

    private void processRFAProf(String _strRFAProf) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    String strTraceBase = "CHQGENAVAIL trace processRFAProf method ";
    StringTokenizer st = new StringTokenizer(_strRFAProf,NEW_LINE);
    String strCurSection = "";
        System.out.println(strTraceBase);

        while (st.hasMoreTokens()) {
            String s1 = st.nextToken().trim();
            System.out.println(strTraceBase + " s1 " + s1);
            System.out.println(strTraceBase + " strCurSection " + strCurSection);
            if (s1.length() > 0) {
                char c = s1.charAt(0);
                if (c == '*') {        // comments
                    continue;
                }
            } else {
                continue;
            }

            if (s1.equals(MTM)) {
                strCurSection = MTM;
            } else if (s1.equals(RELATEDFC)) {
                strCurSection = RELATEDFC;
            } else if (s1.equals(RELATEDFCCONV)) {
                strCurSection = RELATEDFCCONV;
            } else if (s1.equals(NEWFC)) {
                strCurSection = NEWFC;
            } else if (s1.equals(FCCONVERSIONS)) {
                strCurSection = FCCONVERSIONS;
            } else if (s1.equals(WDMTM)) {
                strCurSection = WDMTM;
            } else if (s1.equals(WDFC)) {
                strCurSection = WDFC;
            } else if (s1.equals(RFATITLE)) {
                strCurSection = RFATITLE;
            } else {
                if (strCurSection.equals(MTM)) {
                    modelAnnouncement(s1);
                } else if (strCurSection.equals(RELATEDFC) || strCurSection.equals(NEWFC)) {
                    featureAnnouncement(s1);
                } else if (strCurSection.equals(RELATEDFCCONV) || strCurSection.equals(FCCONVERSIONS)) {
                    fConvAnnouncement(s1);
                } else if (strCurSection.equals(WDMTM)) {
                    withdrawMTM(s1);
                } else if (strCurSection.equals(WDFC)) {
                    withdrawFeature(s1);
                } else if (strCurSection.equals(RFATITLE)) {
                    m_availAtts.put("AVAIL:COMNAME", "COMNAME=" + s1);
                    m_availWDAtts.put("AVAIL:COMNAME", "COMNAME=" + s1);
                }
            }
        }
    }

    private int dateCompare(String _strDate1, String _strDate2) {
        String strTraceBase = "CHQGENAVAIL trace dateCompare method ";
        System.out.println(strTraceBase + ":" + _strDate1 + ":" + _strDate2);
        try {

            Date date1 = Date.valueOf(_strDate1);
            Date date2 = Date.valueOf(_strDate2);

            long id1 = date1.getTime();
            long id2 = date2.getTime();
            long diff = id1 - id2;
        //logMessage(strTraceBase + ":Difference is :"+diff);
            if (diff < 0) {
                return EARLIER;
            } else if (diff > 0) {
                return LATER;
            }

        } catch (IllegalArgumentException ex) {
            println("Wrong date format: " + ":" + _strDate1 + ":" + _strDate2);
            throw ex;
        }
        return EQUAL;
    }

    private boolean modelAnnouncement(String _strMTM) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CHQGENAVAIL trace modelAnnouncement method ";
    boolean bError = false;
    boolean bWarning = false;
    String strHeader = new String("New Model Announcement.");
    String strMT = null;
    String strModel = null;
    StringBuffer sb = new StringBuffer();
    EntityItem[] aeiMODEL = null;
    EntityList el = null;
    EntityGroup eg = null;
    EntityItem[] aeiFMCON = null;
    EntityItem[] aeiTMCON = null;
    EntityGroup egMCON = null;
    EntityGroup egFCON = null;

    int i1 = _strMTM.indexOf(" ");
    int i2 = _strMTM.indexOf("-");

        System.out.println(strTraceBase + ":" + _strMTM);

        if (i1 >= 0) {
            _strMTM = _strMTM.substring(0,i1);
        }

        if (i2 < 0) {
            m_sbError.append(NEW_LINE + "Error: " + strHeader);
            m_sbError.append(NEW_LINE + "Error: " + _strMTM + " is not in format MMMM-mmm");
            return false;
        }

        strMT = _strMTM.substring(0, i2);
        strModel = _strMTM.substring(i2+1);

        sb.append("map_MACHTYPEATR=" + strMT + ";");
        sb.append("map_MODELATR=" + strModel);

        try {
            aeiMODEL = m_utility.dynaSearch(m_db, m_prof, m_ei, "SRDMODEL5", "MODEL", sb.toString());
        } catch (SBRException e) {
          System.out.println(e.toString());
            aeiMODEL = null;
        }
        if (aeiMODEL == null || aeiMODEL.length <= 0) {
            setReturnCode(FAIL);
            bError = true;
            m_sbError.append(NEW_LINE + "Error: " + strHeader);
            m_sbError.append(NEW_LINE + "Error: There isn't MODEL with machine type " + strMT + ", model " + strModel);
            return false;
        }

        el = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen1, aeiMODEL);
        eg = el.getParentEntityGroup();
        if (!eg.getEntityType().equals("MODEL")) {
            setReturnCode(FAIL);
            bError = true;
            m_sbError.append(NEW_LINE + "Error: " + strHeader);
            m_sbError.append(NEW_LINE + "Error: Parent Entity Group is not MODEL.");
            return false;
        }

        for (int i=0; i < eg.getEntityItemCount(); i++) {
      EntityGroup egPRODSTRUCT = null;
            EntityItem ei = eg.getEntityItem(i);
            String strModelDate = m_utility.getAttrValue(ei, "ANNDATE");
            if (strModelDate == null || strModelDate.length() <= 0 || dateCompare(strModelDate, m_strAnnDate) != EQUAL) {
                if (!bWarning) {
                    m_sbError.append(NEW_LINE + "Error: " + strHeader);
                }
                bWarning = true;
                m_sbError.append(NEW_LINE + "Warning: Model: " + ei.toString() + " Announce Date: " + strModelDate + " does not match with Announcement Announce Date:"+ m_strAnnDate);
                logMessage("Warning: Model: " + ei.toString() + " Announce Date: " + strModelDate + " does not match with Announcement Announce Date:"+ m_strAnnDate);
                continue;
            }

            bError = autogenAVAIL(el, ei, "MODELAVAIL", true, false, MODEL, bError);

            egPRODSTRUCT = el.getEntityGroup("PRODSTRUCT");
            //System.out.println(strTraceBase + " include Feature PRODSTRUCT size: " + egPRODSTRUCT.getEntityItemCount());
            // include Feature
            for (int j=0; j < egPRODSTRUCT.getEntityItemCount(); j++) {
                boolean bFoundError = false;
                EntityItem eiR = egPRODSTRUCT.getEntityItem(j);
                EntityItem eiC = (EntityItem) eiR.getDownLink(0);
                EntityItem eiP = (EntityItem) eiR.getUpLink(0);
                if (eiC.getKey().equals(ei.getKey())) {
                    String strRDate = m_utility.getAttrValue(eiR, "ANNDATE");
                    String strFeatureDate = m_utility.getAttrValue(eiP, "FIRSTANNDATE");
                    if (strRDate != null && strRDate.length() > 0) {
            int date = -1;
                        if (strFeatureDate != null && strFeatureDate.length() > 0) {
                            int date1 = dateCompare(strRDate, strFeatureDate);
                            int date2 = dateCompare(strRDate, strModelDate);
                            if (date1 == EARLIER || date2 == EARLIER) {
                                if (!bWarning) {
                                    m_sbError.append(NEW_LINE + "Error: " + strHeader);
                                }

                                m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiR.toString() + " Announce Date: " + strRDate + " is earlier than the Feature:" + strFeatureDate + " and Model: " + strModelDate);
                                bWarning = true;
                                continue;
                            }
                        }

                        date = dateCompare(strRDate, m_strAnnDate);
                        if (date == EQUAL) {
                            EntityItem[] aei = {eiR};
                            EntityList elOOFAVAIL = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen4, aei);
                            bFoundError = autogenAVAIL(elOOFAVAIL, eiR, "OOFAVAIL", true, false, MODEL, bError);
                            if (bFoundError) {
                                bError = bFoundError;
                            }
                        } else if (date == EARLIER) {
                            if (!bWarning) {
                                m_sbError.append(NEW_LINE + "Error: " + strHeader);
                            }

                            m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiR.toString() + " Announce Date: " + strRDate + " is earlier than the Profile Announcement Date:" + m_strAnnDate);
                            bWarning = true;
                        }
                    } else {
                        // check for FEATURE's FIRSTANNDATE
                        //EntityItem eiP = (EntityItem) eiR.getUpLink(0);
                        String strCDate = m_utility.getAttrValue(eiP, "FIRSTANNDATE");
                        if (strCDate != null && strCDate.length() > 0) {
                            int date = dateCompare(strCDate, m_strAnnDate);
                            if (strModelDate != null && strModelDate.length() > 0 && dateCompare(strModelDate, strCDate) == LATER) {
                                date = dateCompare(strModelDate, m_strAnnDate);
                            }

                            if (date == EQUAL) {
                                EntityItem[] aei = {eiR};
                                EntityList elOOFAVAIL = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen4, aei);

                                bFoundError = autogenAVAIL(elOOFAVAIL, eiR, "OOFAVAIL", true, false, MODEL, bError);
                                if (bFoundError) {
                                    bError = bFoundError;
                                }
                            }
                        } else {
                            int date = dateCompare(strModelDate, m_strAnnDate);

                            if (date == EQUAL) {
                                EntityItem[] aei = {eiR};
                                EntityList elOOFAVAIL = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen4, aei);

                                bFoundError = autogenAVAIL(elOOFAVAIL, eiR, "OOFAVAIL", true, false, MODEL, bError);
                                if (bFoundError) {
                                    bError = bFoundError;
                                }
                            }
                        }
                    }
                }
            }
        }

        // include model convert announcement


        sb = new StringBuffer();

        sb.append("map_FROMMACHTYPE=" + strMT + ";");
        sb.append("map_FROMMODEL=" + strModel);

        aeiFMCON = m_utility.dynaSearch(m_db, m_prof, m_ei, "SRDMODELCONVERT01", "MODELCONVERT", sb.toString());
        mConvertAnnouncement(el, aeiFMCON, strMT, strModel);
        //
        sb = new StringBuffer();

        sb.append("map_TOMACHTYPE=" + strMT + ";");
        sb.append("map_TOMODEL=" + strModel);

        aeiTMCON = m_utility.dynaSearch(m_db, m_prof, m_ei, "SRDMODELCONVERT01", "MODELCONVERT", sb.toString());
        mConvertAnnouncement(el, aeiTMCON, strMT, strModel);


        egMCON = el.getEntityGroup("MODELCONVERT");
        System.out.println(strTraceBase + " include model convert size: " + egMCON.getEntityItemCount());

        if (egMCON.getEntityItemCount() > 0) {
            mConvertAnnouncement(el, strMT, strModel);
        }

        egFCON = el.getEntityGroup("FCTRANSACTION");
        System.out.println(strTraceBase + " include FCTRANSACTION size: " + egFCON.getEntityItemCount());
        for (int i = 0; i < egFCON.getEntityItemCount(); i++) {
            EntityItem eiFCON = egFCON.getEntityItem(i);
            announceFCONV(eiFCON);
        }
        if (!bError && !bWarning) {
            m_sbError.append(NEW_LINE + "Success: " + strHeader);
        }
        return true;
    }

    private boolean mConvertAnnouncement(EntityList _el, EntityItem[] _aei, String _strMT, String _strModel) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CHQGENAVAIL trace mConvertAnnouncement method ";
    boolean bError = false;
    String strHeader = new String("New Model Convert Announcement.");
        System.out.println(strTraceBase + " model convert " );

//        EntityGroup egMCON = _el.getEntityGroup("MODELCONVERT");
        for (int i=0; i < _aei.length; i++) {
            boolean bFoundError = false;
      EntityItem[] aeiMODEL = null;

            EntityItem eiMCON = _aei[i];//egMCON.getEntityItem(i);
            String strFromMT = m_utility.getAttrValue(eiMCON, "FROMMACHTYPE");
            String strFromModel = m_utility.getAttrValue(eiMCON, "FROMMODEL");
            String strToMT = m_utility.getAttrValue(eiMCON, "TOMACHTYPE");
            String strToModel = m_utility.getAttrValue(eiMCON, "TOMODEL");
            if (strFromMT == null || strFromModel == null || strToMT == null || strToModel == null) {
                setReturnCode(FAIL);
                if (!bError) {
                    m_sbError.append(NEW_LINE + "Error: " + strHeader);
                    bError = true;
                }
                m_sbError.append(NEW_LINE + "Error: From machine type model and To Machine type model can not be empty.");
            } else {
                StringBuffer sb = new StringBuffer();

                System.out.println(strTraceBase + " fromMT: " + strFromMT + " fromModel: " + strFromModel + " toMT: " + strToMT + " toModel: " + strToModel);
                if (strFromMT.equals(_strMT) && strFromModel.equals(_strModel)) {
                    sb.append("map_MACHTYPEATR=" + strToMT + ";");
                    sb.append("map_MODELATR=" + strToModel);
                }

                if (strToMT.equals(_strMT) && strToModel.equals(_strModel)) {
                    sb.append("map_MACHTYPEATR=" + strFromMT + ";");
                    sb.append("map_MODELATR=" + strFromModel);
                }

                aeiMODEL = m_utility.dynaSearch(m_db, m_prof, m_ei, "SRDMODEL5", "MODEL", sb.toString());

                if (aeiMODEL != null && aeiMODEL.length > 0) {
                    for (int j=0; j < aeiMODEL.length; j++) {
                        EntityItem eiMODEL = aeiMODEL[j];
                        String strModelDate = m_utility.getAttrValue(eiMODEL, "ANNDATE");
                        if (strModelDate != null && strModelDate.length() > 0 && dateCompare(strModelDate, m_strAnnDate) != LATER) {
                            bFoundError = autogenAVAIL(_el, eiMCON, "MODELCONVERTAVAIL", true, false, MCONV, bError);
                            if (bFoundError) {
                                bError = bFoundError;
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (!bError) {
            m_sbError.append(NEW_LINE + "Success: " + strHeader);
        }

        return true;
    }

    private boolean mConvertAnnouncement(EntityList _el, String _strMT, String _strModel) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CHQGENAVAIL trace mConvertAnnouncement method ";
        boolean bError = false;
        String strHeader = new String("New Model Convert Announcement.");
        EntityGroup egMCON = _el.getEntityGroup("MODELCONVERT");

    System.out.println(strTraceBase + " machtype: " + _strMT + " model " + _strModel);
        for (int i=0; i < egMCON.getEntityItemCount(); i++) {
            boolean bFoundError = false;
            EntityItem eiMCON = egMCON.getEntityItem(i);
            String strFromMT = m_utility.getAttrValue(eiMCON, "FROMMACHTYPE");
            String strFromModel = m_utility.getAttrValue(eiMCON, "FROMMODEL");
            String strToMT = m_utility.getAttrValue(eiMCON, "TOMACHTYPE");
            String strToModel = m_utility.getAttrValue(eiMCON, "TOMODEL");
            if (strFromMT == null || strFromModel == null || strToMT == null || strToModel == null) {
                setReturnCode(FAIL);
                if (!bError) {
                    m_sbError.append(NEW_LINE + "Error: " + strHeader);
                    bError = true;
                }
                m_sbError.append(NEW_LINE + "Error: From machine type model and To Machine type model can not be empty.");
            } else {
                StringBuffer sb = new StringBuffer();
        EntityItem[] aeiMODEL = null;
                System.out.println(strTraceBase + " fromMT: " + strFromMT + " fromModel: " + strFromModel + " toMT: " + strToMT + " toModel: " + strToModel);
                if (strFromMT.equals(_strMT) && strFromModel.equals(_strModel)) {
                    sb.append("map_MACHTYPEATR=" + strToMT + ";");
                    sb.append("map_MODELATR=" + strToModel);
                }

                if (strToMT.equals(_strMT) && strToModel.equals(_strModel)) {
                    sb.append("map_MACHTYPEATR=" + strFromMT + ";");
                    sb.append("map_MODELATR=" + strFromModel);
                }

                aeiMODEL = m_utility.dynaSearch(m_db, m_prof, m_ei, "SRDMODEL5", "MODEL", sb.toString());

                if (aeiMODEL != null && aeiMODEL.length > 0) {
                    for (int j=0; j < aeiMODEL.length; j++) {
                        EntityItem eiMODEL = aeiMODEL[j];
                        String strModelDate = m_utility.getAttrValue(eiMODEL, "ANNDATE");
                        if (strModelDate != null && strModelDate.length() > 0 && dateCompare(strModelDate, m_strAnnDate) != LATER) {
                            bFoundError = autogenAVAIL(_el, eiMCON, "MODELCONVERTAVAIL", true, false, MCONV, bError);
                            if (bFoundError) {
                                bError = bFoundError;
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (!bError) {
            m_sbError.append(NEW_LINE + "Success: " + strHeader);
        }

        return true;
    }

    private boolean featureAnnouncement(String _strFeatureCode) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CHQGENAVAIL trace featureAnnouncement method ";
    boolean bError = false;
    boolean bWarning = false;
    String strHeader = new String("New Feature Announcement");
    int i1 = _strFeatureCode.indexOf(" ");
    StringBuffer sb = new StringBuffer();
    EntityItem[] aeiFEATURE = null;
    EntityList el = null;
    EntityGroup eg = null;
    EntityGroup egPRODSTRUCT = null;

        System.out.println(strTraceBase + ":" + _strFeatureCode);
        if (m_strInventoryGroup == null || m_strInventoryGroup.length() <= 0) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: " + strHeader);
            m_sbError.append(NEW_LINE + "Error: INVENTORYGROUP is empty");
            return false;
        }

        if (i1 >= 0) {
            _strFeatureCode = _strFeatureCode.substring(0,i1);
        }

        sb.append("map_FEATURECODE=" + _strFeatureCode + ";");

        aeiFEATURE = m_utility.dynaSearch(m_db, m_prof, m_ei, "SRDFEATURE1", "FEATURE", sb.toString());
        if (aeiFEATURE == null || aeiFEATURE.length <= 0) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: " + strHeader);
            m_sbError.append(NEW_LINE + "Error: There isn't FEATURE with Feature Code: " + _strFeatureCode);
            return false;
        }

        el = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen2, aeiFEATURE);
        eg = el.getParentEntityGroup();
        if (!eg.getEntityType().equals("FEATURE")) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: " + strHeader);
            m_sbError.append(NEW_LINE + "Error: Parent Entity Group is not FEATURE.");
            return false;
        }

        egPRODSTRUCT = el.getEntityGroup("PRODSTRUCT");
        for (int i=0; i < egPRODSTRUCT.getEntityItemCount(); i++) {
      EANList MTList = null;
      String strRDate = null;

            EntityItem eiR = egPRODSTRUCT.getEntityItem(i);
            boolean bFoundError = false;
            // check for MODEL
            EntityItem eiModel = (EntityItem) eiR.getDownLink(0);
            String strModelDate = m_utility.getAttrValue(eiModel, "ANNDATE");
            EntityItem eiFeature = (EntityItem) eiR.getUpLink(0);
            String strFeatureDate = m_utility.getAttrValue(eiFeature, "FIRSTANNDATE");

            if (strModelDate != null && strModelDate.length() > 0 && dateCompare(strModelDate, m_strAnnDate) != EARLIER) {
                //m_sbError.append(NEW_LINE + "Warning: Model: " + eiModel.toString() + " Announce Date: " + strModelDate + " is not earlier than the Profile Announcement Date:" + m_strAnnDate);
                continue;
            }

            // check for MACHTYPE
            //strMT = m_utility.getAttrValue(eiModel, "MACHTYPEATR");
            MTList = getParent(el, eiModel.getEntityType(), eiModel.getEntityID(), "MACHTYPE", "MACHINETYPEMODELA");
            if (MTList.size() > 0) {
                EntityItem eiMT = (EntityItem) MTList.getAt(0);
                String strIG = m_utility.getAttrValue(eiMT, "INVENTORYGROUP");
                if (!strIG.equals(m_strInventoryGroup)) {
                    System.out.println("MACHTYPE IG: " + strIG + " not equals " + m_strInventoryGroup);
                    continue;
                }
            }

            // include Feature
            strRDate = m_utility.getAttrValue(eiR, "ANNDATE");
            if (strRDate != null && strRDate.length() > 0) {
        int date = -1;
                if (strFeatureDate != null && strFeatureDate.length() > 0 && strModelDate != null && strModelDate.length() > 0) {
                    int date1 = dateCompare(strRDate, strFeatureDate);
                    int date2 = dateCompare(strRDate, strModelDate);
                    if (date1 == EARLIER || date2 == EARLIER) {
                        if (!bWarning) {
                            m_sbError.append(NEW_LINE + "Error: " + strHeader);
                        }

                        m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiR.toString() + " Announce Date: " + strRDate + " is earlier than the Feature:" + strFeatureDate + " and Model: " + strModelDate);
                        bWarning = true;
                        continue;
                    }
                }

                date = dateCompare(strRDate, m_strAnnDate);
                if (date == EQUAL) {
                    EntityItem[] aei = {eiR};
                    EntityList elOOFAVAIL = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen4, aei);
                    bFoundError = autogenAVAIL(elOOFAVAIL, eiR, "OOFAVAIL", true, false, FEATURE, bError);
                    if (bFoundError) {
                        bError = bFoundError;
                    }
                } else {
                    //m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiR.toString() + " Announce Date: " + strRDate + " is not equal the Profile Announcement Date:" + m_strAnnDate);
                    //bWarning = true;
                }
            } else {
                // check for FEATURE's FIRSTANNDATE
                EntityItem eiP = (EntityItem) eiR.getUpLink(0);
                //System.out.println(strTraceBase + " feature ei: " + eiP.dump(false));
                String strDate = m_utility.getAttrValue(eiP, "FIRSTANNDATE");
                if (strDate != null && strDate.length() > 0 && strModelDate != null && strModelDate.length() > 0) {
                    int date1 = dateCompare(strModelDate, strDate);
                    int date = dateCompare(strDate, m_strAnnDate);
                    if (date1 == LATER) {
                        date = dateCompare(strModelDate, m_strAnnDate);
                    }

                    if (date == EQUAL) {
                        EntityItem[] aei = {eiR};
                        EntityList elOOFAVAIL = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen4, aei);
                        bFoundError = autogenAVAIL(elOOFAVAIL, eiR, "OOFAVAIL", true, false, FEATURE, bError);
                        if (bFoundError) {
                            bError = bFoundError;
                        }
                    } else {
                        //m_sbError.append(NEW_LINE + "Warning: Feature: " + eiP.toString() + " Announce Date: " + strDate + " is not equal the Profile Announcement Date:" + m_strAnnDate);
                        //bWarning = true;
                    }
                } else {
                    if (strModelDate != null && strModelDate.length() > 0) {
                        int date = dateCompare(strModelDate, m_strAnnDate);

                        if (date == EQUAL) {
                            EntityItem[] aei = {eiR};
                            EntityList elOOFAVAIL = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen4, aei);
                            bFoundError = autogenAVAIL(elOOFAVAIL, eiR, "OOFAVAIL", true, false, MODEL, bError);
                            if (bFoundError) {
                                bError = bFoundError;
                            }
                        }
                    }
                }
            }
        }
        if (!bError && !bWarning) {
            m_sbError.append(NEW_LINE + "Success: " + strHeader);
        }

        return true;
    }

    private boolean withdrawMTM(String _strMTM) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CHQGENAVAIL trace withdrawMTM method ";
    boolean bError = false;
    boolean bWarning = false;
    String strHeader = new String("Withdrawing MTM.");
    int i1 = _strMTM.indexOf(" ");
    int i2 = _strMTM.indexOf("-");
    String strMT = null;
    String strModel = null;
    StringBuffer sb = new StringBuffer();
    EntityItem[] aeiMODEL = null;
    EntityList el = null;
    EntityGroup eg = null;

        System.out.println(strTraceBase + ":" + _strMTM);
        if (i1 >= 0) {
            _strMTM = _strMTM.substring(0,i1);
        }

        if (i2 < 0) {
            m_sbError.append(NEW_LINE + "Error: " + strHeader);
            m_sbError.append(NEW_LINE + "Error: " + _strMTM + " is not in format MMMM-mmm");
            return false;
        }

        strMT = _strMTM.substring(0, i2);
        strModel = _strMTM.substring(i2+1);

        sb.append("map_MACHTYPEATR=" + strMT + ";");
        sb.append("map_MODELATR=" + strModel);

        try {
            aeiMODEL = m_utility.dynaSearch(m_db, m_prof, m_ei, "SRDMODEL5", "MODEL", sb.toString());
        } catch (SBRException ex) {
          System.out.println(ex.toString());
            aeiMODEL = null;
        }
        if (aeiMODEL == null || aeiMODEL.length <= 0) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: " + strHeader);
            m_sbError.append(NEW_LINE + "Error: There isn't MODEL with machine type " + strMT + ", model " + strModel);
            return false;
        }

        el = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen1, aeiMODEL);
        eg = el.getParentEntityGroup();
        if (!eg.getEntityType().equals("MODEL")) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: " + strHeader);
            m_sbError.append(NEW_LINE + "Error: Parent Entity Group is not MODEL.");
            return false;
        }

        for (int i=0; i < eg.getEntityItemCount(); i++) {
      boolean bFoundError = false;
      EntityGroup egPRODSTRUCT = null;
            EntityItem ei = eg.getEntityItem(i);

            String strWDate = m_utility.getAttrValue(ei, "WITHDRAWDATE");

            int date = dateCompare(strWDate, m_strAnnDate);
            if (date != EQUAL) {
                if (!bWarning) {
                    m_sbError.append(NEW_LINE + "Error: " + strHeader);
                }
                bWarning = true;

                m_sbError.append(NEW_LINE + "Warning: Model: " + ei.toString() + " Withdraw Date: " + strWDate + " does not match the Profile Date:" + m_strAnnDate);
                continue;
            }


            bFoundError = autogenAVAIL(el, ei, "MODELAVAIL", true, true, WITHDRAWMTM, bError);
            if (bFoundError) {
                bError = bFoundError;
            }

            egPRODSTRUCT = el.getEntityGroup("PRODSTRUCT");
            for (int j=0; j < egPRODSTRUCT.getEntityItemCount(); j++) {
                EntityItem eiR = egPRODSTRUCT.getEntityItem(j);
                EntityItem eiC = (EntityItem) eiR.getDownLink(0);
                EntityItem eiP = (EntityItem) eiR.getUpLink(0);
                if (eiC.getKey().equals(ei.getKey())) {
                    String strRDate = m_utility.getAttrValue(eiR, "WITHDRAWDATE");
                    String strFeatureDate = m_utility.getAttrValue(eiP, "WITHDRAWANNDATE_T");
                    if (strRDate != null && strRDate.length() > 0) {
            int date2 = -1;
                        if (strFeatureDate != null && strFeatureDate.length() > 0) {
                            int date1 = dateCompare(strRDate, strFeatureDate);
                            if (date1 == LATER) {
                                if (!bWarning) {
                                    m_sbError.append(NEW_LINE + "Error: " + strHeader);
                                }

                                m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiR.toString() + " Withdraw Date: " + strRDate + " is later than the Feature:" + strFeatureDate);
                                bWarning = true;

                            }
                        }

                        date2 = dateCompare(strRDate, m_strAnnDate);
                        if (date2 == LATER) {
                            if (!bWarning) {
                                m_sbError.append(NEW_LINE + "Error: " + strHeader);
                            }

                            m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiR.toString() + " Announce Date: " + strRDate + " is later than the Profile Announcement Date:" + m_strAnnDate);
                            bWarning = true;
                        }
                    }
                }
            }
        }

        if (!bError && !bWarning) {
            m_sbError.append(NEW_LINE + "Success: " + strHeader);
        }

        return true;
    }

    private boolean withdrawFeature(String _strFeatureCode) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CHQGENAVAIL trace withdrawFeature method ";
    boolean bError = false;
    boolean bWarning = false;
    StringBuffer sb = new StringBuffer();
    String strHeader = new String("Withdrawing Features.");
    int i1 = _strFeatureCode.indexOf(" ");
    EntityItem[] aeiFEATURE = null;
    EntityList el = null;
    EntityGroup eg = null;
    EntityGroup egPRODSTRUCT = null;

        System.out.println(strTraceBase + ":" + _strFeatureCode);

        if (m_strInventoryGroup == null || m_strInventoryGroup.length() <= 0) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: Error Withdrawing Features");
            m_sbError.append(NEW_LINE + "Error: INVENTORYGROUP is empty");
            return false;
        }

        if (i1 >= 0) {
            _strFeatureCode = _strFeatureCode.substring(0,i1);
        }

        sb.append("map_FEATURECODE=" + _strFeatureCode + ";");

        aeiFEATURE = m_utility.dynaSearch(m_db, m_prof, m_ei, "SRDFEATURE1", "FEATURE", sb.toString());
        if (aeiFEATURE == null || aeiFEATURE.length <= 0) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: Error Withdrawing Features");
            m_sbError.append(NEW_LINE + "Error: There isn't FEATURE with Feature Code: " + _strFeatureCode);
            return false;
        }

        el = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen2, aeiFEATURE);
        eg = el.getParentEntityGroup();
        if (!eg.getEntityType().equals("FEATURE")) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: Error Withdrawing Features");
            m_sbError.append(NEW_LINE + "Error: Parent Entity Group is not FEATURE.");
            return false;
        }

        egPRODSTRUCT = el.getEntityGroup("PRODSTRUCT");

        if (egPRODSTRUCT.getEntityItemCount() <= 0) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: Error Withdrawing Features");
            m_sbError.append(NEW_LINE + "Error: No matching Product Strutures are found for feature code " + _strFeatureCode);
            return false;
        }

        for (int i=0; i < egPRODSTRUCT.getEntityItemCount(); i++) {
            EntityItem eiR = egPRODSTRUCT.getEntityItem(i);
            boolean bFoundError = false;
      EANList MTList = null;
      String strRDate = null;

//System.out.println(strTraceBase + " ei PRODSTRUCT: " + eiR.dump(false));
            // check for MODEL
            EntityItem eiModel = (EntityItem) eiR.getDownLink(0);
            EntityItem eiFeature = (EntityItem) eiR.getUpLink(0);
            String strModelDate = m_utility.getAttrValue(eiModel, "ANNDATE");
            //String strMT = m_utility.getAttrValue(eiModel, "MACHTYPEATR");
            String strWModelDate = m_utility.getAttrValue(eiModel, "WITHDRAWDATE");
            if (strModelDate != null && strModelDate.length() > 0 && dateCompare(strModelDate, m_strAnnDate) != EARLIER) {
                continue;
            }

            if (strWModelDate != null && strWModelDate.length() > 0 && dateCompare(strWModelDate, m_strAnnDate) != LATER) {
                continue;
            }

//System.out.println(strTraceBase + " 01");
            // check for MACHTYPE
            MTList = getParent(el, eiModel.getEntityType(), eiModel.getEntityID(), "MACHTYPE", "MACHINETYPEMODELA");
            if (MTList.size() > 0) {
                EntityItem eiMT = (EntityItem) MTList.getAt(0);
                String strIG = m_utility.getAttrValue(eiMT, "INVENTORYGROUP");
                if (!strIG.equals(m_strInventoryGroup)) {
                    System.out.println("MACHTYPE IG: " + strIG + " not equals " + m_strInventoryGroup);
                    continue;
                }
            }
//System.out.println(strTraceBase + " 02");

            // include Feature
            strRDate = m_utility.getAttrValue(eiR, "WITHDRAWDATE");
            if (strRDate != null && strRDate.length() > 0) {
        int date = -1;
                //If PRODSTRUCT.WITHDRAWDATE is not null
                 //then PRODSTRUCT.WITHDRAWDATE
                 //if PRODSTRUCT.WITHDRAWDATE > MODEL.WITHDRAWDATE or FEATURE.WITHDRAWANNDATE_T Then log an Error
                String strWFeatureDate = m_utility.getAttrValue(eiFeature, "WITHDRAWANNDATE_T");
                if (strWFeatureDate != null && strWFeatureDate.length() > 0) {
                    int date1 = dateCompare(strRDate, strWFeatureDate);
                    if (date1 == LATER) {
                        if (!bWarning) {
                            m_sbError.append(NEW_LINE + "Error: " + strHeader);
                        }

                        m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiR.toString() + " Withdraw Date: " + strRDate + " is later than the Feature:" + strWFeatureDate);
                        bWarning = true;
                    }
                }

                strWModelDate = m_utility.getAttrValue(eiModel, "WITHDRAWDATE");
                if (strWModelDate != null && strWModelDate.length() > 0) {
                    int date2 = dateCompare(strRDate, strWModelDate);
                    if (date2 == LATER) {
                        if (!bWarning) {
                            m_sbError.append(NEW_LINE + "Error: " + strHeader);
                        }

                        m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiR.toString() + " Withdraw Date: " + strRDate + " is later than the Model:" + strWModelDate);
                        bWarning = true;
                    }
                }

                date = dateCompare(strRDate, m_strAnnDate);
                if (date == EQUAL) {

                    EntityItem[] aei = {eiR};
                    EntityList elOOFAVAIL = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen4, aei);
                    bFoundError = autogenAVAIL(elOOFAVAIL, eiR, "OOFAVAIL", true, true, WITHDRAWFC, bError);
                    if (bFoundError) {
                        bError = bFoundError;
                    }
                } else {
                    //m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiR.toString() + " Withdraw Date: " + strRDate + " is not equal the Profile Date:" + m_strAnnDate);
                }
            } else {

                // check for MIN{FEATURE. WITHDRAWANNDATE_T, MODEL. WITHDRAWDATE }

                String strDate = m_utility.getAttrValue(eiFeature, "WITHDRAWANNDATE_T");
                if (strDate != null && strDate.length() > 0) {
//System.out.println(strTraceBase + " 06");
                    int date = dateCompare(strDate, m_strAnnDate);
                    if (strWModelDate != null && strWModelDate.length() > 0 && dateCompare(strDate, strWModelDate) == LATER) {
                        date = dateCompare(strWModelDate, m_strAnnDate);
                    }
                    if (date == EQUAL) {
                        EntityItem[] aei = {eiR};
                        EntityList elOOFAVAIL = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen4, aei);
                        bFoundError = autogenAVAIL(elOOFAVAIL, eiR, "OOFAVAIL", true, true, WITHDRAWFC, bError);
                        if (bFoundError) {
                            bError = bFoundError;
                        }
                    } else {
                        //m_sbError.append(NEW_LINE + "Warning: Feature: " + eiP.toString() + " Withdraw Date: " + strDate + " is not equal the Profile Date:" + m_strAnnDate);
                    }
                }
            }
        }
        if (!bError) {
            m_sbError.append(NEW_LINE + "Success: Withdrawing Features.");
        }

        return true;
    }

    private boolean fConvAnnouncement(String _strFConv) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CHQGENAVAIL trace fConvAnnouncement method ";
    boolean bError = false;
    StringTokenizer st = null;
    String strFromMT = null;
    String strFromModel = null;
    String strFromFC = null;
    String strToMT = null;
    String strToModel = null;
    String strToFC = null;
    StringBuffer sb = new StringBuffer();
    EntityItem[] aeiFCONV = null;

    int i1 = _strFConv.indexOf(" ");

        System.out.println(strTraceBase + ":" + _strFConv);
        if (i1 >= 0) {
            _strFConv = _strFConv.substring(0,i1);
        }

        st = new StringTokenizer(_strFConv, "\\");
        if (st.countTokens() != 6) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: Feature Conversion Announcement");
            m_sbError.append(NEW_LINE + "Error: Feature Conversion is not in format MMMM\\mmm\\ffff\\NNNN\\nnn\\ssss.");
            return false;
        }
        strFromMT = st.nextToken();
        strFromModel = st.nextToken();
        strFromFC = st.nextToken();
        strToMT = st.nextToken();
        strToModel = st.nextToken();
        strToFC = st.nextToken();

    if (!allQM(strFromMT)) {
      sb.append("map_FROMMACHTYPE=" + strFromMT.replace('?', '_') + ";");
    }
    if (!allQM(strFromModel)) {
          sb.append("map_FROMMODEL=" + strFromModel.replace('?', '_') + ";");
    }
    if (!allQM(strFromFC)) {
          sb.append("map_FROMFEATURECODE=" + strFromFC.replace('?', '_') + ";");
    }
    if (!allQM(strToMT)) {
          sb.append("map_TOMACHTYPE=" + strToMT.replace('?', '_') + ";");
    }
    if (!allQM(strToModel)) {
          sb.append("map_TOMODEL=" + strToModel.replace('?', '_') + ";");
    }
    if (!allQM(strToFC)) {
          sb.append("map_TOFEATURECODE=" + strToFC.replace('?', '_') + ";");
    }

        aeiFCONV = m_utility.dynaSearch(m_db, m_prof, m_ei, "SRDFCTRANSACTION01", "FCTRANSACTION", sb.toString());
        if (aeiFCONV == null || aeiFCONV.length <= 0) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: Feature Conversion Announcement");
            m_sbError.append(NEW_LINE + "Error: There isn't FCTRANSACTION with FROMMACHTYPE " + strFromMT + ", FROMMODEL " + strFromModel + ", FROMFEATURECODE " + strFromFC + ", TOMACHTYPE " + strToMT + ", TOMODEL " + strToModel + ", TOFEATURECODE " + strToFC );
            return false;
        } else {
            // check for other
        }

        for (int i=0; i < aeiFCONV.length; i++) {
            EntityItem eiFCONV = aeiFCONV[i];
            String strFConvDate = m_utility.getAttrValue(eiFCONV, "ANNDATE");
            boolean bFoundError = false;
            if (strFConvDate != null && strFConvDate.length() > 0 && dateCompare(strFConvDate, m_strAnnDate) == EARLIER) {
                String strFCWDate = m_utility.getAttrValue(eiFCONV, "WITHDRAWDATE");
                if (strFCWDate != null && strFCWDate.length() > 0 && dateCompare(strFCWDate, m_strAnnDate) == EQUAL) {
                    EntityItem[] aei = {eiFCONV};
                    EntityList el = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen3, aei);
                    // withdraw FCTRANSACTION
                    bFoundError = autogenAVAIL(el, eiFCONV, "FEATURETRNAVAIL", true, true, FCONV, bError);
                    if (bFoundError) {
                        bError = bFoundError;
                    }

                    continue;
                }
            }
            bFoundError = announceFCONV(eiFCONV);
            if (bFoundError) {
                bError = bFoundError;
            }
        }

        if (!bError) {
            m_sbError.append(NEW_LINE + "Success: Feature Conversion Announcement.");
        }

        return true;
    }

    private boolean announceFCONV(EntityItem _eiFCONV ) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CHQGENAVAIL trace announceFCONV method ";
    boolean bError = false;
    String strFConvDate = null;
    String strFTFromMT = null;
    String strFTFromModel = null;
    String strFTFromFC = null;
    String strFTToMT = null;
    String strFTToModel = null;
    String strFTToFC = null;
    StringBuffer sb = new StringBuffer();
    EntityList elFPROD = null;
    EntityList elTPROD = null;
    EntityItem eiFPROD = null;
    boolean bFAnnounce = false;
    EntityGroup egFPROD = null;
    EntityItem eiTPROD = null;
    boolean bTAnnounce = false;
    EntityGroup egTPROD = null;

        if (_eiFCONV == null || (!_eiFCONV.getEntityType().equals("FCTRANSACTION"))) {
            return false;
        }

        System.out.println(strTraceBase + " ei " + _eiFCONV.getKey());
        strFConvDate = m_utility.getAttrValue(_eiFCONV, "ANNDATE");
        if (strFConvDate == null || strFConvDate.length() <=0 || dateCompare(strFConvDate, m_strAnnDate) != EQUAL) {
            return false;
        }

        strFTFromMT = m_utility.getAttrValue(_eiFCONV, "FROMMACHTYPE");
        strFTFromModel = m_utility.getAttrValue(_eiFCONV, "FROMMODEL");
        strFTFromFC = m_utility.getAttrValue(_eiFCONV, "FROMFEATURECODE");
        strFTToMT = m_utility.getAttrValue(_eiFCONV, "TOMACHTYPE");
        strFTToModel = m_utility.getAttrValue(_eiFCONV, "TOMODEL");
        strFTToFC = m_utility.getAttrValue(_eiFCONV, "TOFEATURECODE");

        // get From and To PRODSTRUCT

        sb.append("map_MODEL:MACHTYPEATR=" + strFTFromMT + ";");
        sb.append("map_MODEL:MODELATR=" + strFTFromModel + ";");
        sb.append("map_FEATURE:FEATURECODE=" + strFTFromFC + ";");

        try {
            elFPROD = m_utility.dynaSearchIIForEntityList(m_db, m_prof, m_ei, "SRDPRODSTRUCT03", "PRODSTRUCT", sb.toString());
        } catch (SBRException ex) {
          System.out.println(ex.toString());
            elFPROD = null;
        }

        m_db.test(elFPROD != null, "EntityList for search FROM PRODSTRUCT is null");

        if (elFPROD.getEntityGroup("PRODSTRUCT") == null || elFPROD.getEntityGroup("PRODSTRUCT").getEntityItemCount() <= 0) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: New Feature Conversion Announcement.");
            m_sbError.append(NEW_LINE + "Error: No PRODSTRUCT exist for MACHTYPE " + strFTFromMT + ", MODEL " + strFTFromModel + ", and FEATURECODE " + strFTFromFC);
            return false;
        }

        sb = new StringBuffer();
        sb.append("map_MODEL:MACHTYPEATR=" + strFTToMT + ";");
        sb.append("map_MODEL:MODELATR=" + strFTToModel + ";");
        sb.append("map_FEATURE:FEATURECODE=" + strFTToFC + ";");

        try {
            elTPROD = m_utility.dynaSearchIIForEntityList(m_db, m_prof, m_ei, "SRDPRODSTRUCT03", "PRODSTRUCT", sb.toString());
        } catch (SBRException ex) {
      System.out.println(ex.toString());
            elTPROD = null;
        }

        m_db.test(elTPROD != null, "EntityList for search TO PRODSTRUCT is null");
        if (elTPROD.getEntityGroup("PRODSTRUCT") == null || elTPROD.getEntityGroup("PRODSTRUCT").getEntityItemCount() <= 0) {
            setReturnCode(FAIL);
            m_sbError.append(NEW_LINE + "Error: New Feature Conversion Announcement.");
            m_sbError.append(NEW_LINE + "Error: No PRODSTRUCT exist for MACHTYPE " + strFTToMT + ", MODEL " + strFTToModel + ", and FEATURECODE " + strFTToFC);
            return false;
        }

        egFPROD = elFPROD.getEntityGroup("PRODSTRUCT");
        for (int i=0; i < egFPROD.getEntityItemCount(); i++) {
      EntityItem eiFeature = null;
      EntityItem eiModel = null;
      String strPRODDate = null;
      String strFeatureDate = null;
      String strModelDate = null;

            eiFPROD = egFPROD.getEntityItem(i);
            eiFeature = (EntityItem) eiFPROD.getUpLink(0);
            eiModel = (EntityItem)eiFPROD.getDownLink(0);

            strPRODDate = m_utility.getAttrValue(eiFPROD, "ANNDATE");
            strFeatureDate = m_utility.getAttrValue(eiFeature, "FIRSTANNDATE");
            strModelDate = m_utility.getAttrValue(eiModel, "ANNDATE");
            if (strPRODDate != null && strPRODDate.length() > 0) {
                if (strFeatureDate != null && strFeatureDate.length() > 0 && strModelDate != null && strModelDate.length() > 0) {
                    if (dateCompare(strPRODDate, strModelDate) == EARLIER || dateCompare(strPRODDate, strFeatureDate) == EARLIER) {
                        m_sbError.append(NEW_LINE + "Error: New Feature Conversion Announcement.");
                        m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiFPROD.toString() + " Announce Date: " + strPRODDate + " is earlier than the Feature:" + strFeatureDate + " and Model: " + strModelDate);
                        bError = true;
                        break;
                    }
                }

                if (dateCompare(strPRODDate, m_strAnnDate) != LATER) {
                    bFAnnounce = true;
                    break;
                }
            } else {
                if (strFeatureDate != null && strFeatureDate.length() > 0 && strModelDate != null && strModelDate.length() > 0) {
                    int date1 = dateCompare(strModelDate, strFeatureDate);
                    int date = dateCompare(strFeatureDate, m_strAnnDate);
                    if (date1 == LATER) {
                        date = dateCompare(strModelDate, m_strAnnDate);
                    }

                    if (date != LATER) {
                        bFAnnounce = true;
                        break;
                    }
                }
            }
        }


        egTPROD = elTPROD.getEntityGroup("PRODSTRUCT");
        for (int i=0; i < egTPROD.getEntityItemCount(); i++) {
      EntityItem eiFeature = null;
      EntityItem eiModel = null;
      String strPRODDate = null;
      String strFeatureDate = null;
      String strModelDate = null;

            eiTPROD = egTPROD.getEntityItem(i);
            eiFeature = (EntityItem) eiTPROD.getUpLink(0);
            eiModel = (EntityItem)eiTPROD.getDownLink(0);

            strPRODDate = m_utility.getAttrValue(eiTPROD, "ANNDATE");
            strFeatureDate = m_utility.getAttrValue(eiFeature, "FIRSTANNDATE");
            strModelDate = m_utility.getAttrValue(eiModel, "ANNDATE");
            if (strPRODDate != null && strPRODDate.length() > 0) {
                if (strFeatureDate != null && strFeatureDate.length() > 0 && strModelDate != null && strModelDate.length() > 0) {
                    if (dateCompare(strPRODDate, strModelDate) == EARLIER || dateCompare(strPRODDate, strFeatureDate) == EARLIER) {
                        m_sbError.append(NEW_LINE + "Error: New Feature Conversion Announcement.");
                        m_sbError.append(NEW_LINE + "Warning: Product Structure: " + eiTPROD.toString() + " Announce Date: " + strPRODDate + " is earlier than the Feature:" + strFeatureDate + " and Model: " + strModelDate);
                        bError = true;
                        break;
                    }
                }

                if (dateCompare(strPRODDate, m_strAnnDate) != LATER) {
                    bTAnnounce = true;
                    break;
                }
            } else {
                if (strFeatureDate != null && strFeatureDate.length() > 0 && strModelDate != null && strModelDate.length() > 0) {
                    int date1 = dateCompare(strModelDate, strFeatureDate);
                    int date = dateCompare(strFeatureDate, m_strAnnDate);
                    if (date1 == LATER) {
                        date = dateCompare(strModelDate, m_strAnnDate);
                    }

                    if (date != LATER) {
                        bTAnnounce = true;
                        break;
                    }
                }
            }

        }

        if (bFAnnounce && bTAnnounce) {
            EntityItem[] aei = {_eiFCONV};
            EntityList el = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen3, aei);
            bError = autogenAVAIL(el, _eiFCONV, "FEATURETRNAVAIL", true, false, FCONV, bError);
        }
        return bError;
    }
/*
    private boolean checkAnnounceProdStruct(EntityItem _eiPROD, EntityItem _eiFeature, EntityItem _eiModel, String _strAnnouncement) {
        String strPRODDate = m_utility.getAttrValue(_eiPROD, "ANNDATE");
        String strFeatureDate = m_utility.getAttrValue(_eiFeature, "FIRSTANNDATE");
        String strModelDate = m_utility.getAttrValue(_eiModel, "ANNDATE");
        if (strPRODDate != null && strPRODDate.length() > 0) {
            if (strFeatureDate != null && strFeatureDate.length() > 0 && strModelDate != null && strModelDate.length() > 0) {
                if (dateCompare(strPRODDate, strModelDate) == EARLIER || dateCompare(strPRODDate, strFeatureDate) == EARLIER) {
                    m_sbError.append(NEW_LINE + "Error: " + _strAnnouncement);
                    m_sbError.append(NEW_LINE + "Warning: Product Structure: " + _eiPROD.toString() + " Announce Date: " + strPRODDate + " is earlier than the Feature:" + strFeatureDate + " and Model: " + strModelDate);
                    return false;
                }
            }

            if (dateCompare(strPRODDate, m_strAnnDate) != LATER) {
                return true;
            }
        } else {
            if (strFeatureDate != null && strFeatureDate.length() > 0 && strModelDate != null && strModelDate.length() > 0) {
                int date1 = dateCompare(strModelDate, strFeatureDate);
                int date = dateCompare(strFeatureDate, m_strAnnDate);
                if (date1 == LATER) {
                    date = dateCompare(strModelDate, m_strAnnDate);
                }

                if (date != LATER) {
                    return true;
                }
            }

        }
        return false;
    }
*/
    private String getStrAttributes(OPICMList _attList) {
        StringBuffer sb = new StringBuffer();
        for (int i =0; i < _attList.size(); i++) {
            sb.append(i==0? "": ";");
            sb.append((String)_attList.getAt(i));
        }
        return sb.toString();
    }

    private void availHandling() throws SQLException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
        // find all AVAILabilities, the ANNCODENAME and AVAILTYPE must match
    EntityItem[] aeiAVAIL = null;

        StringBuffer sbSearch  = new StringBuffer();
        sbSearch.append("map_ANNCODENAME=" +  m_utility.getAttrValue(m_ei, "ANNCODENAME"));
        aeiAVAIL = m_utility.dynaSearch(m_db, m_prof, m_ei, "SRDAVAIL10", "AVAIL", sbSearch.toString());

        for (int i =0; i < aeiAVAIL.length; i++) {
      EANList relList = new EANList();
      boolean bFoundLink = false;

            EntityItem eiAVAIL = aeiAVAIL[i];
            EntityItem[] aei = {eiAVAIL};
            EntityList el = EntityList.getEntityList(m_db, m_prof, m_xaiAutoGen5, aei);
            EntityGroup eg = el.getParentEntityGroup();
            eiAVAIL = eg.getEntityItem(eiAVAIL.getKey());

            for (int j=0; j < eiAVAIL.getUpLinkCount(); j++) {
                EntityItem eiu = (EntityItem)eiAVAIL.getUpLink(j);
                if (!eiu.getEntityType().equals("COFOOFAVAIL")) {
                    relList.put(eiu);
                } else {
                    bFoundLink = true;
                }
            }
            if (bFoundLink && relList.size() > 0) {
                for (int k=0; k < relList.size(); k++) {
                    EntityItem eiR = (EntityItem)relList.getAt(k);
                    System.out.println("remove relator for avail : " + eiR.toString());
                    EANUtility.deactivateEntity(m_db, m_prof, eiR);
                }
            } else {
                System.out.println("remove avail : " + eiAVAIL.toString());
                EANUtility.deactivateEntity(m_db, m_prof, eiAVAIL);
            }
        }
    }

    private boolean autogenAVAIL(EntityList _el, EntityItem _ei, String _strRelatorType, boolean _bChildren, boolean _bWD, int _iAnnType, boolean _bPrevError) throws SQLException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
        String strTraceBase = "CHQGENAVAIL trace autogenAVAIL method ";

    boolean bError = false;
    boolean bExist = false;
    EANList availList = null;
    OPICMList availAttList = null;

    System.out.println(strTraceBase + ":" + _strRelatorType + ":" + _bWD);


        if (_ei == null) {
            System.out.println(strTraceBase + " _ei is null");
            return false;
        }

        availList = getChildren(_el, _ei.getEntityType(), _ei.getEntityID(), "AVAIL", _strRelatorType);
        availAttList = m_availAtts;
        if (_bWD) {
            availAttList = m_availWDAtts;
        }

        if (availList != null && availList.size() > 0) {
            EntityItem eiAVAIL = m_utility.findEntityItem(availList, "AVAIL", getStrAttributes(availAttList), true);

            if (eiAVAIL != null) {
        OPICMList attList = new OPICMList();
                bExist = true;

                // update Avail that matches ANNCODENAME and AVAILTYPE
                // check COMNAME, EFFECTIVEDATE, GENAREASELECTION, AND COUNTRYLIST

                for (int i=0; i < m_updateAvailAttrs.length; i++) {
                    //boolean bFirst = true;
                    String strAttrCode = m_updateAvailAttrs[i];
                    String strAttrOldValue = m_utility.getAttrValue(eiAVAIL, strAttrCode);
                    String strAttrNewValue = "";

                    if (_strRelatorType.equals("OOFAVAIL") && (strAttrCode.equals("GENAREASELECTION") || strAttrCode.equals("COUNTRYLIST"))) {
                        // get value from FEATURE

                        EntityItem eiFeature = (EntityItem)_ei.getUpLink(0);
                        strAttrNewValue = m_utility.getAttrValue(eiFeature, strAttrCode);
                    } else {
            int iEqual = -1;
                        String strTemp = (String)availAttList.get("AVAIL:" + strAttrCode);

                        if (strTemp != null) {
                            strAttrNewValue = strTemp;
                        }

                        iEqual = strAttrNewValue.indexOf("=");
                        if (iEqual >= 0) {
                            strAttrNewValue = strAttrNewValue.substring(iEqual+1);
                        }
                    }

//                    System.out.println(strTraceBase + " checking attribute: " + strAttrCode + ":" + strAttrOldValue + ":" + strAttrNewValue);
                    if (strAttrOldValue == null || (!strAttrOldValue.equals(strAttrNewValue))) {
//                        System.out.println(strTraceBase + " update attribute: " + strAttrCode + ":" + strAttrOldValue + ":" + strAttrNewValue);
                        m_sbError.append(NEW_LINE + "Warning: Availability " + eiAVAIL.toString() + " is overridden by the Profile. " + strAttrCode + ", old value: " + strAttrOldValue + ", new value: " + strAttrNewValue);
                        attList.put("AVAIL:" + strAttrCode , strAttrCode + "=" + strAttrNewValue);
                    }
                }

                if (attList.size() > 0) {
                    m_utility.updateAttribute(m_db, m_prof, eiAVAIL, attList);
                }
            }
        }

        if (!bExist) { // create avail
      EntityItem ei = null;

            if (_strRelatorType.equals("OOFAVAIL")) {
                //System.out.println(strTraceBase + " update avail attributes list " );
                // get value from FEATURE
                EntityItem eiFeature = (EntityItem)_ei.getUpLink(0);
                String strGeo = m_utility.getAttrValue(eiFeature, "GENAREASELECTION");
                String strCountryList = m_utility.getAttrValue(eiFeature, "COUNTRYLIST");
                availAttList.remove("AVAIL:GENAREASELECTION");
                availAttList.put("AVAIL:GENAREASELECTION", "map_GENAREASELECTION=" + strGeo);
                availAttList.remove("AVAIL:COUNTRYLIST");
                availAttList.put("AVAIL:COUNTRYLIST", "map_COUNTRYLIST=" + strCountryList);
            } else {
                availAttList.remove("AVAIL:GENAREASELECTION");
                availAttList.put("AVAIL:GENAREASELECTION", "map_GENAREASELECTION=" + m_utility.getAttrValue(m_ei, "GENAREASELECTION"));
                availAttList.remove("AVAIL:COUNTRYLIST");
                availAttList.put("AVAIL:COUNTRYLIST", "map_COUNTRYLIST=" + m_utility.getAttrValue(m_ei, "COUNTRYLIST"));
            }

            //System.out.println(strTraceBase + " creating new Avail for " + _strRelatorType);
            //System.out.println(strTraceBase + " geo: " + (String)availAttList.get("AVAIL:GENAREASELECTION"));
            //System.out.println(strTraceBase + " countrylist: " + (String)availAttList.get("AVAIL:COUNTRYLIST"));

            ei = m_utility.findEntityItem(m_newAvailList, "AVAIL", getStrAttributes(availAttList), true);
            if (ei == null) {
                ei = m_utility.createEntity(m_db, m_prof, "AVAIL", availAttList);
                m_prof = m_utility.setProfValOnEffOn(m_db, m_prof);
                ei = new EntityItem(m_egAVAIL, m_prof, m_db, ei.getEntityType(), ei.getEntityID());
                //System.out.println(strTraceBase + " refresh AVAIL: " + ei.dump(false));
                m_newAvailList.put(ei);
            }
      EntityItem[] aei = {ei};
            m_utility.linkEntities(m_db, m_prof, _ei, aei, _strRelatorType);
        }
        return bError;
    }
/*
    private void removeEntity(EntityList _el, EntityItem _eiP, EntityItem _eiC, String _strRelatorType, boolean _bChildren) throws SQLException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
        String strTraceBase = "CHQGENAVAIL removeEntity method ";

        EntityGroup eg = _el.getEntityGroup(_strRelatorType);
    System.out.println(strTraceBase + ":" + _eiP + ":" + _eiC);
        if (eg != null) {
            for (int i=0; i < eg.getEntityItemCount(); i++) {
                EntityItem eiR = eg.getEntityItem(i);
                EntityItem eiU = (EntityItem)eiR.getUpLink(0);
                EntityItem eiD = (EntityItem)eiR.getDownLink(0);
                if (eiU.getKey().equals(_eiP.getKey()) && eiD.getKey().equals(_eiC.getKey())) {
                    if (EANUtility.isOrphan(m_db, m_prof, eiR)) {
                        EANUtility.deactivateEntity(m_db, m_prof, _eiC);
                    } else {
                        Vector vctReturnRelatorKeys = new Vector();
                        vctReturnRelatorKeys.addElement(new ReturnRelatorKey(eiR.getEntityType(), eiR.getEntityID(), _eiP.getEntityType(), _eiP.getEntityID(), _eiC.getEntityType(), _eiC.getEntityID(), false));
                        m_utility.link(m_db, m_prof, vctReturnRelatorKeys);
                    }
                }
            }
        } else {
            System.out.println(strTraceBase + " eg is null for " + _strRelatorType);
        }
    }
*/
    private String replace(String _s, String _s1, String _s2) {
        String sResult = "";
        int iTab = _s.indexOf(_s1);

        while (_s.length() > 0 && iTab >=0) {
            sResult = sResult + _s.substring(0, iTab) + _s2;
            _s = _s.substring(iTab+_s1.length());
            iTab = _s.indexOf(_s1);
        }
        sResult = sResult + _s;
        return sResult;
    }

  private boolean allQM(String _str) {
    if (_str == null || _str.length() <= 0) {
      return false;
    }

    for (int i=0; i < _str.length(); i++) {
            char c = _str.charAt(i);
            if (c != QM) {
               return false;
            }
    }
    return true;
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
        return "Autogen AVAIL ABR.";
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
     * getRevision
     * @return
     * @author Joan
     */
    public String getRevision() {
        return new String("1.54");
    }

    /**
     * getVersion
     * @return
     * @author Joan
     */
    public static String getVersion() {
        return ("CHQGENAVAIL.java,v 1.54 2008/01/30 19:39:17 wendy Exp");
    }

    /**
     * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
     * @author Joan
     */
    public String getABRVersion() {
        return "CHQGENAVAIL.java";
    }
}

