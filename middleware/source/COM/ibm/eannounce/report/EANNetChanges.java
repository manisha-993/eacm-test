// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2001
// All Rights Reserved.
//
// $Log: EANNetChanges.java,v $
// Revision 1.27  2008/01/31 21:20:26  wendy
// Cleanup RSA warnings
//
// Revision 1.26  2005/08/30 21:00:55  joan
// remove comments made by cvs to compile
//
// Revision 1.25  2005/08/30 17:39:15  dave
// new cat comments
//
// Revision 1.23  2005/07/21 16:45:27  joan
// fixes for duplicate record of deleted
//
// Revision 1.24  2005/08/16 20:16:41  joan
// fix for entity end tag
//
// Revision 1.23  2005/07/21 16:45:27  joan
// fixes for duplicate record of deleted
//
// Revision 1.22  2005/05/13 01:07:07  dave
// attempting to capture null pointer for Wendy
//
// Revision 1.21  2005/02/09 22:21:18  dave
// more Jtest Cleanup
//
// Revision 1.20  2005/02/09 22:13:43  dave
// more JTest Cleanup
//
// Revision 1.19  2005/02/01 21:40:22  dave
// fix on Jtest block cleanup
//
// Revision 1.18  2005/01/27 02:02:17  dave
// Jtest format cleanup
//
// Revision 1.17  2004/04/23 19:56:56  dave
// reviewing pending CCE XML creation logic
//
// Revision 1.16  2003/10/29 22:59:56  dave
// fix for null pointer
//
// Revision 1.15  2003/09/29 01:52:08  dave
// moding flag table to house descriptions in
// nls for speeds and feeds attributes
//
// Revision 1.14  2003/09/14 00:58:22  dave
// more misc changes
//
// Revision 1.13  2003/09/02 19:48:28  dave
// made sure on Unique Flag changes that we use the current
// flag, and not the prior flag code when
// detecting net changes
//
// Revision 1.12  2003/08/11 22:56:36  joan
// fix 51629
//
// Revision 1.11  2003/07/17 20:00:41  dave
// import fix
//
// Revision 1.10  2003/07/17 19:49:37  dave
// added import statement
//
// Revision 1.9  2003/07/17 19:31:30  dave
// remove trace
//
// Revision 1.8  2003/07/17 18:37:50  dave
// EANNetChanges to clean up after itself
//
// Revision 1.7  2003/07/17 18:20:31  dave
// needed import Statement for ResultSet
//
// Revision 1.6  2003/07/17 18:18:19  dave
// syncing EANNetChanges Up
//
//


package COM.ibm.eannounce.report;


import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;




import java.sql.ResultSet;


/**
* This is a simple Thead manager that creates a ReportStub Thread for each ABR that it comes across
* see it this change sticks
*/
public class EANNetChanges {

    // Class initialization code
    /**
     * NEW_LINE Constant
     */
    public static final String NEW_LINE = "\n";
    /**
     * Automatically generated constructor for utility class
     */
    private EANNetChanges() {
    }

    static {
        D.isplay("EANNetChanges vechangexml:" + getVersion());
    }

    private static Profile c_prof1 = null;
    private static Profile c_prof2 = null;

    /**
     * main
     * @param args
     * @throws java.lang.Exception
     * @author Dave
     */
    public static void main(String args[]) throws Exception {
    }

    /**
     * Return the versoin of this class
     *
     * @return String
     */
    public final static String getVersion() {
        return "$EANNetChanges$";
    }

    /**
     * genVEChangeXML
     * @param _strAppRoot
     * @param _db
     * @param _prof
     * @param _strExtractActionItem
     * @param _strT1
     * @param _strT2
     * @param _strEntityType
     * @param _iEntityID
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     * @return
     * @author Dave
     */
    public static StringBuffer genVEChangeXML(String _strAppRoot, Database _db, Profile _prof, String _strExtractActionItem, String _strT1, String _strT2, String _strEntityType, int _iEntityID) throws MiddlewareRequestException, SQLException {

        try {
            // O.K.  here is one for T1

            EntityItem[] aei = new EntityItem[1];
            ExtractActionItem xai = null;
            EntityList elT1 = null;
            Hashtable hshMap = null;
            EntityItem pei = null;
            StringBuffer sbT1 = null;
            EntityList elT2 = null;
            StringBuffer sbT2 = null;

            c_prof1 = _prof.getNewInstance(_db);
            c_prof1.setValOn(_strT1);
            c_prof1.setEffOn(_strT1);

            aei[0] =new EntityItem(null, c_prof1, _strEntityType, _iEntityID);

            xai = new ExtractActionItem(null, _db, c_prof1, _strExtractActionItem);

            xai.setSkipCleanup(true);

            elT1 = EntityList.getEntityList(_db, c_prof1, xai, aei);
            hshMap = xai.generateVESteps(_db, c_prof1, _strEntityType);
            pei = elT1.getParentEntityGroup().getEntityItem(0);
            sbT1 = EntityList.pull(pei, 0, pei, new Hashtable(), 0, "Root", hshMap);

            // T2
            c_prof2 = c_prof1.getNewInstance(_db);
            c_prof2.setValOn(_strT2);
            c_prof2.setEffOn(_strT2);
            xai = new ExtractActionItem(null, _db, c_prof2, _strExtractActionItem);

            xai.setSkipCleanup(true);

            elT2 = EntityList.getEntityList(_db, c_prof2, xai, aei);
            pei = elT2.getParentEntityGroup().getEntityItem(0);
            sbT2 = EntityList.pull(pei, 0, pei, new Hashtable(), 0, "Root", hshMap);
            return genChangeEntityTreeXML(_db, _strAppRoot, elT1, elT2, compare(sbT1, sbT2));

        } catch (Exception x) {
            x.printStackTrace();
        }

        return new StringBuffer("Egg Timer Interrupt");

    }

    /**
     * compare
     * @param _sbT1
     * @param _sbT2
     * @return
     * @author Dave
     */
    public static final StringBuffer compare(StringBuffer _sbT1, StringBuffer _sbT2) {

        StringBuffer sbResult = new StringBuffer();
        StringTokenizer stT1 = new StringTokenizer(_sbT1.toString(), NEW_LINE);
        StringTokenizer stT2 = new StringTokenizer(_sbT2.toString(), NEW_LINE);
        Hashtable hshT1 = new Hashtable();
        Hashtable hshT2 = new Hashtable();
        Hashtable hshAdds = new Hashtable();
        Hashtable hshDels = new Hashtable();
        Hashtable hshUnch = new Hashtable();

        Iterator it = null;
        // O.K. put them in hashtables.. yee haa

        while (stT1.hasMoreTokens()) {
            hshT1.put(stT1.nextToken(), "YES");
        }
        while (stT2.hasMoreTokens()) {
            hshT2.put(stT2.nextToken(), "YES");
        }

        it = hshT2.keySet().iterator();
        while (it.hasNext()) {
            String strKey = (String) it.next();
            if (!hshT1.containsKey(strKey)) {
                hshAdds.put(strKey, "YES");
            } else {
                hshUnch.put(strKey, "YES");
            }
        }

        it = hshT1.keySet().iterator();
        while (it.hasNext()) {
            String strKey = (String) it.next();
            if (!hshT2.containsKey(strKey)) {
                hshDels.put(strKey, "YES");
            }
        }

        stT2 = new StringTokenizer(_sbT2.toString(), NEW_LINE);
        while (stT2.hasMoreTokens()) {
            String strKey = stT2.nextToken();
            sbResult.append(strKey + ":" + (hshAdds.containsKey(strKey) ? "ADD" : (hshDels.containsKey(strKey) ? "DEL" : "UNC")) + NEW_LINE);
        }

        return sbResult;

    }

    private static StringBuffer genChangeEntityTreeXML(Database _db, String _strAppRoot, EntityList _elT1, EntityList _elT2, StringBuffer _sb) {

        StringBuffer strbResult = new StringBuffer();
        Profile _profT1 = _elT1.getProfile();
        Profile _profT2 = _elT2.getProfile();

        Hashtable hshRoleInfo = new Hashtable();
        Hashtable hshDateInfo = new Hashtable();

        int iSessionIDStart = _elT1.getExtractSessionID();
        int iSessionIDFinish = _elT2.getExtractSessionID();
        String strStartDate = _profT1.getValOn();
        String strFinishDate = _profT2.getValOn();
        String strEnterprise = _profT2.getEnterprise();
        StringTokenizer st1 = null;

        int iStk = -1;
        ResultSet rs = null;

        D.ebug("genChangeEntityTreeXML:" + iSessionIDStart + ":" + iSessionIDFinish + ":" + strStartDate + ":" + strFinishDate);
        try {

            try {
                rs = _db.callGBL8167(new ReturnStatus(-1), strEnterprise, iSessionIDStart, iSessionIDFinish, strStartDate, strFinishDate);
                while (rs.next()) {
                   // String str1 = rs.getString(1).trim();
                   // String str2= rs.getString(2).trim();
                    String strDate = rs.getString(3).trim();
                    String strEntityType = rs.getString(4).trim();
                    int iEntityID = rs.getInt(5);
                    String strAttributeCode = rs.getString(6).trim();
                    String strAttributeValue = rs.getString(7).trim();
                    //int i1 = rs.getInt(8);
                    String strProfile = rs.getString(9).trim();
                    String strUser = rs.getString(10).trim();
                    String strKey = strEntityType + ":" + iEntityID + ":" + strAttributeCode + ":" + strAttributeValue;

                    hshRoleInfo.put(strKey, strProfile + " - " + strUser);
                    hshDateInfo.put(strKey, strDate);

                }

            } finally {
               if (rs != null) {
                  rs.close();
                  rs = null;
               }
               _db.commit();
               _db.freeStatement();
               _db.isPending();
            }
            //
            // O.k. now for the cleanup .. since it was bypassed for the comparison
            //

            _db.callGBL8105(new ReturnStatus(-1), iSessionIDStart);
            _db.freeStatement();
            _db.isPending();
            _db.callGBL8105(new ReturnStatus(-1), iSessionIDFinish);
            _db.freeStatement();
            _db.isPending();
            _db.commit();

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
		int iEntityS = 0;
		int iEntityE = 0;
        st1 = new StringTokenizer(_sb.toString(), NEW_LINE);

        strbResult.append("<!DOCTYPE vechangepackage [<!ENTITY nbsp \"&#160;\">]>" + NEW_LINE);
        strbResult.append("<vechangepackage startdate=\"" + _profT1.getValOn() + "\" enddate=\"" + _profT2.getValOn() + "\" submiter= \"" + _profT2.getEmailAddress() + "\" role=\"" + _profT2.getRoleCode() + "\" sourcenls=\"" + _elT1.getNLSID() + "\" syncnls=\"" + _elT2.getNLSID() + "\">" + NEW_LINE);

        while (st1.hasMoreTokens()) {
			String s1 = st1.nextToken();

            StringTokenizer st2 = new StringTokenizer(s1, ":");

            if (st2.hasMoreTokens()) {
                int iDepth = Integer.parseInt(st2.nextToken());
                int iLevel = Integer.parseInt(st2.nextToken());

                int iNavOrder = 1;

                String strClass = st2.nextToken();
                String strET = st2.nextToken();
                int iEID = Integer.parseInt(st2.nextToken());
                String strPET = st2.nextToken();
                int iPEID = Integer.parseInt(st2.nextToken());
                String strDir = st2.nextToken();
                String strStatus = st2.nextToken();
                String strKey = strET + iEID;

                EntityGroup egCurrent = null;
                EntityItem eiCurrent = null;
                EntityGroup egPrior = null;
                EntityItem eiPrior = null;

                strStatus = (strStatus.equals("ADD") ? "New" : (strStatus.equals("DEL") ? "Deleted" : "Unchanged"));

                if (iDepth < iStk) {
					for (int ii = iStk; ii >= iDepth; ii--) {
                        for (int iy = 0; iy <= ii; iy++) {
                            strbResult.append(" ");
                        }
                        strbResult.append("</entity>" + NEW_LINE);
                        ++iEntityE;
                    }
                }
                iStk = iDepth;
                for (int ii = 0; ii <= iDepth; ii++) {
                    strbResult.append(" ");
                }

                egCurrent = (strDir.equals("Root") ? _elT2.getParentEntityGroup() : _elT2.getEntityGroup(strET));
                eiCurrent = egCurrent.getEntityItem(strET + iEID);
                egPrior = (strDir.equals("Root") ? _elT1.getParentEntityGroup() : _elT1.getEntityGroup(strET));
                eiPrior = egPrior.getEntityItem(strET + iEID);

                strKey = strET + iEID;
                ++iEntityS;
                strbResult.append("<entity description=\"" + parseXMLSafe("NO", egCurrent.getLongDescription()) + "\" type=\"" + strET + "\" eid=\"" + iEID + "\" key=\"" + strKey + (iDepth == 0 ? "" : "\" parentkey =\"" + strPET + iPEID) + "\" depth=\"" + iDepth + "\" level=\"" + iLevel + "\" direction=\"" + strDir + "\" class=\"" + strClass + "\" status=\"" + strStatus + "\">" + NEW_LINE);
                for (int ii = 0; ii <= iDepth + 1; ii++) {
                    strbResult.append(" ");
                }
                strbResult.append("<currentvalue>" + (eiCurrent != null ? parseXMLSafe("NO", eiCurrent.toString()) : "") + "</currentvalue>" + NEW_LINE);
                for (int ii = 0; ii <= iDepth + 1; ii++) {
                    strbResult.append(" ");
                }
                strbResult.append("<priorvalue>" + (eiPrior != null ? parseXMLSafe("NO", eiPrior.toString()) : "") + "</priorvalue>" + NEW_LINE);

                // Attribute Section
                for (int iz = 0; iz < egCurrent.getMetaAttributeCount(); iz++) {
                    EANMetaAttribute ma = egCurrent.getMetaAttribute(iz);
                    String strAttributeCode = ma.getAttributeCode();
                    String strCurrent = null;
                    String strPrior = null;
                    EANAttribute attCurrent = null;
                    EANAttribute attPrior = null;

                    String strLongDesc = parseXMLSafe("NO", ma.getLongDescription());
                    String strShortDesc = parseXMLSafe("NO", ma.getShortDescription());
                    String strInfoKeyAttributeValue = "NA";

                    if (eiCurrent != null) {
                        attCurrent = eiCurrent.getAttribute(strAttributeCode);
                    }
                    if (eiPrior != null) {
                        attPrior = eiPrior.getAttribute(strAttributeCode);
                    }

                    strCurrent = parseXMLSafe(ma.getAttributeType(), (attCurrent == null ? "" : attCurrent.toString()));
                    strPrior = parseXMLSafe(ma.getAttributeType(), (attPrior == null ? "" : attPrior.toString()));

                    if (strLongDesc.startsWith("*")) {
                        strLongDesc = strLongDesc.substring(1);
                    }

                    if (strShortDesc.startsWith("*")) {
                        strShortDesc = strShortDesc.substring(1);
                    }

                    // Do all other attributes exception for F the standard way...
                    if (!ma.getAttributeType().equals("F")) {

                        boolean bSkipDetail = false;

                        String strAttStatus = null;
                        if (strStatus.equals("New")) {
                            if (attCurrent != null) {
                                strAttStatus = "New";
                            } else {
                                strAttStatus = "Unchanged";
                                bSkipDetail = true;
                            }
                        } else if (strStatus.equals("Deleted")) {
                            if (attPrior != null) {
                                strAttStatus = "Deleted";
                            } else {
                                strAttStatus = "Unchanged";
                                bSkipDetail = true;
                            }
                        } else if (attPrior == null && attCurrent == null) {
                            strAttStatus = "Unchanged";
                            bSkipDetail = true;
                        } else if (attPrior == null && attCurrent != null) {
                            strAttStatus = "New";
                        } else if (attPrior != null && attCurrent == null) {
                            strAttStatus = "Deleted";
                        } else if (strCurrent.equals(strPrior)) {
                            strAttStatus = "Unchanged";
                        } else {
                            strAttStatus = "Changed";
                        }

                        if (!bSkipDetail) {

                            String strDateInfo = null;
                            String strInfoKey = null;
                            String strRoleInfo = null;

                            for (int ii = 0; ii <= iDepth + 1; ii++) {
                                strbResult.append(" ");
                            }

                            // The only attribute values that count are for Multi Value Flags
                            // if we are flags here.. we want to get these out of current first
                            // if no current.. we need to see what prior was..

                            if (attCurrent != null) {
                                if (attCurrent instanceof EANFlagAttribute) {
                                    strInfoKeyAttributeValue = ((EANFlagAttribute) attCurrent).getFirstActiveFlagCode();
                                }
                            } else if (attPrior != null) {
                                if (attPrior instanceof EANFlagAttribute) {
                                    strInfoKeyAttributeValue = ((EANFlagAttribute) attPrior).getFirstActiveFlagCode();
                                }
                            }
                            strInfoKey = strET + ":" + iEID + ":" + strAttributeCode + ":" + strInfoKeyAttributeValue;

                            strRoleInfo = (String) hshRoleInfo.get(strInfoKey);
                            if (strRoleInfo == null) {
                                strRoleInfo = "No role Info";
                            } else {
                                strRoleInfo = parseXMLSafe("NO", strRoleInfo);
                            }
                            strDateInfo = (String) hshDateInfo.get(strInfoKey);
                            strbResult.append("<attribute " + (ma.isNavigate() ? "nav = \"" + (iNavOrder++) + "\" " : "") + "description = \"" + strLongDesc + "\" shortdescription =\"" + strShortDesc + "\" parentkey=\"" + strKey + "\" code=\"" + ma.getAttributeCode() + "\" type=\"" + ma.getAttributeType() + "\" changedatetime=\"" + strDateInfo + "\" changeperson=\"" + strRoleInfo + "\" status=\"" + strAttStatus + "\">" + NEW_LINE);

                            // Current Value
                            if (attCurrent != null) {
                                for (int ii = 0; ii <= iDepth + 2; ii++) {
                                    strbResult.append(" ");
                                }

                                //
                                // o.k. if we are a blob we will have to substitute the image thing here
                                //
                                if (ma.getAttributeType().equals("B")) {
                                    strCurrent = "<img src=\"" + _strAppRoot + "/GetBlobAttribute?entityID=" + iEID + "&amp;entityType=" + strET + "&amp;attributeCode=" + strAttributeCode + "&amp;date=" + _profT2.getValOn() + "\"/>";
                                }

                                strbResult.append("<currentvalue>" + strCurrent + "</currentvalue>" + NEW_LINE);

                            } else {
                                for (int ii = 0; ii <= iDepth + 2; ii++) {
                                    strbResult.append(" ");
                                }
                                strbResult.append("<currentvalue></currentvalue>" + NEW_LINE);
                            }

                            // Prior Value
                            if (attPrior != null) {
                                // current the indentation
                                for (int ii = 0; ii <= iDepth + 2; ii++) {
                                    strbResult.append(" ");
                                }
                                //
                                // o.k. if we are a blob we will have to substitute the image thing here
                                //
                                if (ma.getAttributeType().equals("B")) {
                                    strPrior = "<img src=\"" + _strAppRoot + "/GetBlobAttribute?entityID=" + iEID + "&amp;entityType=" + strET + "&amp;attributeCode=" + strAttributeCode + "&amp;date=" + _profT1.getValOn() + "\"/>";
                                }

                                strbResult.append("<priorvalue>" + strPrior + "</priorvalue>" + NEW_LINE);
                            } else {
                                for (int ii = 0; ii <= iDepth + 2; ii++) {
                                    strbResult.append(" ");
                                }
                                strbResult.append("<priorvalue></priorvalue>" + NEW_LINE);
                            }

                            for (int ii = 0; ii <= iDepth + 1; ii++) {
                                strbResult.append(" ");
                            }
                            strbResult.append("</attribute>" + NEW_LINE);

                        }
                    } else {

                        String strAttStatus = null;
                        // We need one attribute for every meta flag value...
                        EANFlagAttribute fac = (EANFlagAttribute) attCurrent;
                        EANFlagAttribute fap = (EANFlagAttribute) attPrior;

                        // Adds are things that are not in prior .. but in current.
                        if (fac != null) {
                            MetaFlag mfc[] = (MetaFlag[]) fac.get();
                            for (int ia = 0; ia < mfc.length; ia++) {
                                boolean bSkip = false;
                                if (fap == null && mfc[ia].isSelected()) {
                                    strAttStatus = "New";
                                } else if (fap == null && !mfc[ia].isSelected()) {
                                    bSkip = true;
                                } else if (!mfc[ia].isSelected() && fap.isSelected(mfc[ia])) {
                                    strAttStatus = "Deleted";
                                } else if (!mfc[ia].isSelected() && !fap.isSelected(mfc[ia])) {
                                    bSkip = true;
                                } else {
                                    strAttStatus = (mfc[ia].isSelected() && !fap.isSelected(mfc[ia]) ? "New" : "Unchanged");
                                }
                                if (!bSkip) {
                                    String strInfoKey = strET + ":" + iEID + ":" + strAttributeCode + ":" + mfc[ia].getFlagCode();
                                    String strRoleInfo = (String) hshRoleInfo.get(strInfoKey);
                                    String strDateInfo = (String) hshDateInfo.get(strInfoKey);

                                    for (int ii = 0; ii <= iDepth + 1; ii++) {
                                        strbResult.append(" ");
                                    }
                                    // The only attribute values that count are for Multi Value Flags
                                    if (strRoleInfo == null) {
                                        strRoleInfo = "No role Info";
                                    } else {
                                        strRoleInfo = parseXMLSafe("NO", strRoleInfo);
                                    }

                                    strbResult.append("<attribute " + (ma.isNavigate() ? "nav = \"" + (iNavOrder++) + "\" " : "") + "description = \"" + strLongDesc + "\" shortdescription =\"" + strShortDesc + "\" parentkey=\"" + strKey + "\" code=\"" + ma.getAttributeCode() + "\" type=\"" + ma.getAttributeType() + "\" changedatetime=\"" + strDateInfo + "\" changeperson=\"" + strRoleInfo + "\" status=\"" + strAttStatus + "\">" + NEW_LINE);
                                    for (int ii = 0; ii <= iDepth + 2; ii++) {
                                        strbResult.append(" ");
                                    }
                                    strbResult.append("<priorvalue>" + (strAttStatus.equals("New") ? "" : parseXMLSafe("NO", mfc[ia].getLongDescription())) + "</priorvalue>" + NEW_LINE);
                                    for (int ii = 0; ii <= iDepth + 2; ii++) {
                                        strbResult.append(" ");
                                    }
                                    strbResult.append("<currentvalue>" + (strAttStatus.equals("Deleted") ? "" : parseXMLSafe("NO", mfc[ia].getLongDescription())) + "</currentvalue>" + NEW_LINE);
                                    for (int ii = 0; ii <= iDepth + 1; ii++) {
                                        strbResult.append(" ");
                                    }
                                    strbResult.append("</attribute>" + NEW_LINE);
                                }
                            }
                        }
                        if (fap != null) {
                            MetaFlag mfp[] = (MetaFlag[]) fap.get();
                            strAttStatus = "";
                            for (int ia = 0; ia < mfp.length; ia++) {
                                boolean bSkip = false;
                                if (fac == null) {
                                    if (mfp[ia].isSelected()) {
                                        strAttStatus = "Deleted";
                                    } else {
                                        bSkip = true;
                                    }
                                } else {
// this causes duplicate record
//									strAttStatus = (mfp[ia].isSelected() && !fac.isSelected(mfp[ia]) ? "Deleted" : "Unchanged");
                                }


                                //
                                // DWB !!! i think we are supposed to skip here!
                                //

                                if (!bSkip && strAttStatus.equals("Deleted")) {
                                    String strInfoKey = strET + ":" + iEID + ":" + strAttributeCode + ":" + strInfoKeyAttributeValue;
                                    String strRoleInfo = (String) hshRoleInfo.get(strInfoKey);
                                    String strDateInfo = (String) hshDateInfo.get(strInfoKey);
                                    for (int ii = 0; ii <= iDepth + 1; ii++) {
                                        strbResult.append(" ");
                                    }
                                    strInfoKeyAttributeValue = mfp[ia].getFlagCode();
                                    if (strRoleInfo == null) {
                                        strRoleInfo = "No role Info";
                                    } else {
                                        strRoleInfo = parseXMLSafe("NO", strRoleInfo);
                                    }

                                    strbResult.append("<attribute description = \"" + strLongDesc + "\" shortdescription =\"" + strShortDesc + "\" parentkey=\"" + strKey + "\" code=\"" + ma.getAttributeCode() + "\" type=\"" + ma.getAttributeType() + "\" changedatetime=\"" + strDateInfo + "\" changeperson=\"" + strRoleInfo + "\" status=\"" + strAttStatus + "\">" + NEW_LINE);

                                    for (int ii = 0; ii <= iDepth + 2; ii++) {
                                        strbResult.append(" ");
                                    }
                                    strbResult.append("<priorvalue>" + parseXMLSafe("NO", mfp[ia].getLongDescription()) + "</priorvalue>" + NEW_LINE);
                                    for (int ii = 0; ii <= iDepth + 2; ii++) {
                                        strbResult.append(" ");
                                    }
                                    strbResult.append("<currentvalue></currentvalue>" + NEW_LINE);
                                    for (int ii = 0; ii <= iDepth + 1; ii++) {
                                        strbResult.append(" ");
                                    }
                                    strbResult.append("</attribute>" + NEW_LINE);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Finish it off
/*
        for (int ii = iStk; ii >= 0; ii--) {
            for (int iy = 0; iy <= ii; iy++) {
                strbResult.append(" ");
            }
            strbResult.append("</entity>" + NEW_LINE);
			System.out.println("EANNetChanges print end entity 2: " + (++iEntityE));
        }
*/
		int iEnd = iEntityS - iEntityE;
        for (int ii = iEnd; ii > 0; ii--) {
            for (int iy = 0; iy <= ii; iy++) {
                strbResult.append(" ");
            }
            strbResult.append("</entity>" + NEW_LINE);
        }

        strbResult.append("</vechangepackage>");

        return strbResult;

    }


    private static String parseXMLSafe(String _strAttributeType, String _str) {

        String strReturn = "";
        StringTokenizer st = null;
        if (_strAttributeType.equals("X")) {
            return _str;
        }

        st = new StringTokenizer(_str, "<&>", true);
        while (st.hasMoreTokens()) {
            String strToken = st.nextToken();
            if (strToken.length() > 0) {
                if (strToken.equals("&")) {
                    strReturn = strReturn + "&amp;";
                } else if (strToken.equals("<")) {
                    strReturn = strReturn + "&lt;";
                } else if (strToken.equals(">")) {
                    strReturn = strReturn + "&gt;";
                } else {
                    strReturn = strReturn + strToken;
                }
            }
        }
        return strReturn;
    }

}
