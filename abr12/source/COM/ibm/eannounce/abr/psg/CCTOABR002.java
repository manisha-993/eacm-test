/**
 * (c) Copyright International Business Machines Corporation, 2003
 * All Rights Reserved.
 */


package COM.ibm.eannounce.abr.psg;


import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.text.*;


/**
 * <pre>
 * $Log: CCTOABR002.java,v $
 * Revision 1.27  2008/01/30 19:27:44  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.26  2005/08/23 01:53:46  wendy
 * TIR 6FCEVD changed date check
 *
 * Revision 1.25  2005/05/13 18:09:03  chris
 * Fix for TIR 6CCD4H and 6CCCRB. Pull WW not CT versions of publish and unpublish
 *
 * Revision 1.24  2005/05/05 20:38:50  chris
 * Reimplement CR4220 ABR checks without the locking
 *
 * Revision 1.22  2005/04/18 20:24:17  chris
 * Untested fix for TIR 6AYDSA.
 *
 * Revision 1.21  2005/04/13 13:08:16  chris
 * Fix for TIR 6AYDSA. SBBPUBLISHWWDATE must be after SBBANNOUNCETGT.
 *
 * Revision 1.20  2005/03/30 12:48:42  chris
 * Fix for TIR 6AYDSA. Check correct attribute codes
 *
 * Revision 1.19  2005/02/10 16:23:23  chris
 * Additional Checks and locking for CR4220
 *
 * Revision 1.18  2005/02/04 17:59:41  chris
 * Some JTest Cleanup
 *
 * Revision 1.17  2005/01/31 16:30:05  joan
 * make changes for Jtest
 *
 * Revision 1.16  2003/11/12 20:38:05  chris
 * put Revision back
 *
 * Revision 1.15  2003/11/06 22:01:18  bala
 * EC drop
 *
 * Revision 1.10  2003/10/28 20:57:25  cstolpe
 * Added Bala's changes
 *
 * Revision 1.9  2003/10/08 11:41:06  cstolpe
 * Fix related to FB 52441
 *
 * Revision 1.8  2003/09/22 18:38:42  cstolpe
 *  Fixes for FB 52200:4F0D30 and 52202:5537C6 neither defaulted to PASS or output a default  pass message
 *
 * Revision 1.7  2003/09/22 14:56:20  cstolpe
 * Fix for FB52241 Family and Series association name changed
 *
 * Revision 1.6  2003/09/11 21:44:24  cstolpe
 * Latest Updates
 *
 * Revision 1.5  2003/07/31 16:42:12  cstolpe
 * Remove java code to strip Revision. Done by build now.
 *
 * Revision 1.4  2003/07/28 15:34:41  cstolpe
 * Change DG submit (not final)
 *
 * Revision 1.3  2003/06/20 17:37:52  cstolpe
 * use AttributeChangeHistoryItem.getFlagCode()
 *
 * Revision 1.2  2003/06/19 17:50:48  cstolpe
 * Externalized strings to resource bundles
 *
 * Revision 1.1.1.1  2003/06/19 14:05:01  cstolpe
 * Initial Import
 * </pre>
 *
 *@author     cstolpe
 *@created    May 8, 2003
 */
public final class CCTOABR002 extends PokBaseABR {
    private ResourceBundle messages = ResourceBundle.getBundle(this.getClass().getName(), getLocale(1));
    private StringBuffer report = new StringBuffer();
    /**
     *  Execute ABR.
     *
     */
    public void execute_run() {
        StringBuffer rpt = report;
        MessageFormat mfOut = null;
        ResourceBundle msgs = null;
        Object[] mfParms = new String[10];
        StringBuffer navName = new StringBuffer();
        msgs = messages;
        try {
            EntityGroup eg;
            Iterator itMeta;
            Iterator itEI;
            Vector vctCTO;
            Iterator itCTO;
            String strCOFSTATUS;
            String strCCOSOLSTATUS;

            start_ABRBuild();
            // NAME is navigate attributes
            eg = new EntityGroup(null, m_db, m_prof, getRootEntityType(), "Navigate"); //$NON-NLS-1$
            itMeta = eg.getMetaAttribute().values().iterator();
            while (itMeta.hasNext()) {
                EANMetaAttribute ma = (EANMetaAttribute) itMeta.next();
                navName.append(
                    getAttributeValue(
                        getRootEntityType(),
                        getRootEntityID(),
                        ma.getAttributeCode()));
                if (itMeta.hasNext()) {
                    navName.append(" "); //$NON-NLS-1$
                }
            }
            messages =
                ResourceBundle.getBundle(
                    this.getClass().getName(),
                    getLocale(m_prof.getReadLanguage().getNLSID()));
            msgs = messages;

            // Output Brand, Family, Series, Project
            mfOut = new MessageFormat(msgs.getString("PATH")); //$NON-NLS-1$
            itEI = m_elist.getEntityGroup("PR").getEntityItem().values().iterator(); //$NON-NLS-1$
            while (itEI.hasNext()) {
                EntityItem eiPR = (EntityItem) itEI.next();
                mfParms[0] = getAttributeValue(eiPR.getEntityType(), eiPR.getEntityID(), "BRANDCODE"); //$NON-NLS-1$
                mfParms[1] = getAttributeValue(eiPR.getEntityType(), eiPR.getEntityID(), "FAMNAMEASSOC"); //$NON-NLS-1$
                mfParms[2] = getAttributeValue(eiPR.getEntityType(), eiPR.getEntityID(), "SENAMEASSOC"); //$NON-NLS-1$
                mfParms[3] = getAttributeValue(eiPR.getEntityType(), eiPR.getEntityID(), "NAME"); //$NON-NLS-1$
                rpt.append(mfOut.format(mfParms));
            }

            // Output Root CCTO
            printEntity(getRootEntityType(), getRootEntityID(), 0);

            // Output CTO parents
            mfOut = new MessageFormat(msgs.getString("PARENT_STATUS")); //$NON-NLS-1$
            vctCTO = getEntityIds("CTO", "CTOCCTO"); //$NON-NLS-1$  //$NON-NLS-2$
            itCTO = vctCTO.iterator();
            while (itCTO.hasNext()) {
                int id = ((Integer) itCTO.next()).intValue();
                mfParms[0] = getEntityDescription("CTO"); //$NON-NLS-1$
                mfParms[1] = getAttributeValue("CTO", id, "NAME"); //$NON-NLS-1$  //$NON-NLS-2$
                mfParms[2] = getAttributeDescription("CTO", "COFSTATUS"); //$NON-NLS-1$  //$NON-NLS-2$
                mfParms[3] = getAttributeValue("CTO", id, "COFSTATUS"); //$NON-NLS-1$  //$NON-NLS-2$
                rpt.append(mfOut.format(mfParms));
            }

            // Compare status (only to first parent found)
            strCOFSTATUS = getAttributeFlagEnabledValue("CTO", ((Integer) vctCTO.firstElement()).intValue(), "COFSTATUS", ""); //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
            strCCOSOLSTATUS = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "CCOSOLSTATUS", ""); //$NON-NLS-1$  //$NON-NLS-2$
            if (strCOFSTATUS == null || strCCOSOLSTATUS == null) {
                rpt.append(msgs.getString("STATUS_ERROR")); //$NON-NLS-1$
                setReturnCode(FAIL);
            }
            setReturnCode(PASS);
            if ("0040".equals(strCCOSOLSTATUS)) { // Ready for Review  //$NON-NLS-1$
                if ("0020".equals(strCOFSTATUS)) { // Final  //$NON-NLS-1$
                    EntityGroup egCCTOSBB = m_elist.getEntityGroup("CCTOSBB");  //$NON-NLS-1$
                    if (egCCTOSBB == null || egCCTOSBB.getEntityItemCount() == 0) {
                        report.append(msgs.getString("ERROR_NO_SBB")); //$NON-NLS-1$
                        setReturnCode(FAIL);
                    }
                    else if (checkOverrideDate()) {
                        setReturnCode(FAIL);
                    }
                    else if (checkSBBLinkage()) {
                        report.append(msgs.getString("ERROR_SBB_LINKAGE")); //$NON-NLS-1$
                        setReturnCode(FAIL);
                    }
                    else {
                        triggerWorkFlow("WFCCOSOLSTATUSFINAL");
                        //setControlBlock();
                        //setFlagValue("CCOSOLSTATUS", "0020"); //$NON-NLS-1$  //$NON-NLS-2$
                        rpt.append(msgs.getString("PASS_MSG")); //$NON-NLS-1$
                        setReturnCode(PASS);
                    }
                }
                else {
                    rpt.append(msgs.getString("FAIL_MSG")); //$NON-NLS-1$
                    setReturnCode(FAIL);
                }
            }
            else if (!"0020".equals(strCCOSOLSTATUS)) {  // Not Final  //$NON-NLS-1$
                EntityGroup egCCTOSBB = m_elist.getEntityGroup("CCTOSBB");  //$NON-NLS-1$
                if (egCCTOSBB == null || egCCTOSBB.getEntityItemCount() == 0) {
                    report.append(msgs.getString("ERROR_NO_SBB")); //$NON-NLS-1$
                    setReturnCode(FAIL);
                }
                else if (checkSBBLinkage()) {
                    report.append(msgs.getString("ERROR_SBB_LINKAGE")); //$NON-NLS-1$
                    setReturnCode(FAIL);
                }
                else {
                    triggerWorkFlow("WFCCOSOLSTATUSRR");
                    rpt.append(msgs.getString("PASS_MSG")); //$NON-NLS-1$
                }
            }
            else {
                rpt.append(msgs.getString("STD_PASS_MSG")); //$NON-NLS-1$
            }
        } catch (LockPDHEntityException le) {
            setReturnCode(FAIL);
            mfOut = new MessageFormat(msgs.getString("LOCK_ERROR")); //$NON-NLS-1$
            mfParms[0] = ERR_IAB1007E;
            mfParms[1] = le.getMessage();
            rpt.append(mfOut.format(mfParms));
            logError(le.getMessage());
        } catch (UpdatePDHEntityException le) {
            setReturnCode(FAIL);
            mfOut = new MessageFormat(msgs.getString("UPDATE_ERROR")); //$NON-NLS-1$
            mfParms[0] = le.getMessage();
            rpt.append(mfOut.format(mfParms));
            logError(le.getMessage());
        } catch (Exception exc) {
            java.io.StringWriter exBuf;
            setReturnCode(FAIL);
            // Report this error to both the datbase log and the PrintWriter
            mfOut = new MessageFormat(msgs.getString("EXCEPTION_ERROR")); //$NON-NLS-1$
            mfParms[0] = m_abri.getABRCode();
            mfParms[1] = exc.getMessage();
            rpt.append(mfOut.format(mfParms));
            exBuf = new java.io.StringWriter();
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            rpt.append("<pre>"); //$NON-NLS-1$
            rpt.append(exBuf.getBuffer().toString());
            rpt.append("</pre>"); //$NON-NLS-1$
        } finally {
            setDGTitle(navName.toString());
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("CTYABR"); //$NON-NLS-1$
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }
        // Insert Header into beginning of report
        navName.append((getReturnCode() == PASS) ? " Passed" : " Failed"); //$NON-NLS-1$  //$NON-NLS-2$
        mfOut = new MessageFormat(msgs.getString("HEADER")); //$NON-NLS-1$
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

    private boolean checkSBBLinkage() {
        boolean error = false;
        EntityGroup egCCTOSBB = m_elist.getEntityGroup("CCTOSBB");  //$NON-NLS-1$
        if (egCCTOSBB == null) {
            logError("Extract Definition Problem: No CCTOSBB");  //$NON-NLS-1$
        }
        else {
            EntityGroup egCTOSBB = m_elist.getEntityGroup("CTOSBB");  //$NON-NLS-1$
            if (egCTOSBB == null) {
                logError("Extract Definition Problem: No CTOSBB");  //$NON-NLS-1$
            }
            else {
                int nCCTOSBB = egCCTOSBB.getEntityItemCount();
                int nCTOSBB = egCTOSBB.getEntityItemCount();
                Set sCCTOSBB = new HashSet();
                Set sCTOSBB = new HashSet();
                for (int i = 0; i < nCCTOSBB; i++) {
                    EntityItem ei = egCCTOSBB.getEntityItem(i);
                    ei = (EntityItem) ei.getDownLink(0);
                    sCCTOSBB.add(new Integer(ei.getEntityID()));
                }
                for (int i = 0; i < nCTOSBB; i++) {
                    EntityItem ei = egCTOSBB.getEntityItem(i);
                    ei = (EntityItem) ei.getDownLink(0);
                    sCTOSBB.add(new Integer(ei.getEntityID()));
                }
                if (!sCTOSBB.containsAll(sCCTOSBB)) {
                    error = true;
                }
            }
        }
        return error;
    }

    /**
     * Returns true if  one of the override dates is in error
     * @return
     */
    private boolean checkOverrideDate() {
        boolean error = false;
        EntityGroup egCCTOSBB = m_elist.getEntityGroup("CCTOSBB");  //$NON-NLS-1$
        // If the relator to Sales Building Block has a Override date it must be before the withdrawal date
        /*
        For Country CTO SOL to SBB Publish Date, if the SBB Country Override is present (populated), it must be less than or
        equal to the Sales Building Block Publish-Target date.
        In other words, CCTOSBB:SBBPUBLISHCTDATE, if populated, must be later than or equal to SBB:SBBANNOUNCETGT.
        If this test fails, the following message is provided: Override dates must be later than the SBB Publish date.

        For Country CTO SOL to SBB Unpublish Date, if the SBB Country Override date is present (populated),
        it must be greater than or equal to the Sales Building Block Withdrawal Date.
        In other words, CCTOSBB:SBBUNPUBLISHCTDATE, if populated, must be earlier than or equal to SBB:SBBWITHDRAWLDATE.
        If this test fails, the following message is provided: Override date must be before SBB Withdrawal Date.

        Wendy   the spec seems to contradict itself with respect to date checking, i need to verify what it really means
        Wendy   so for publish, it is an error if CTOSBB:SBBPUBLISHWWDATE is before SBB:SBBANNOUNCETGT
        dorgrant@us.ib...   yes
        Wendy   and for unpublish it is an error if CTOSBB:SBBUNPUBLISHWWDATE is after SBB:SBBWITHDRAWLDATE
        dorgrant@us.ib...   yes
        Wendy   and the check is the same for CCTO, correct?
        Wendy   same confusing wording is used for those date checks too.
        dorgrant@us.ib...   yes, should be the same for CCTO
        */
        if (egCCTOSBB != null) {
            int nCTOSBB = egCCTOSBB.getEntityItemCount();
            for (int i = 0; i < nCTOSBB; i++) {
                EntityItem eiCTOSBB = egCCTOSBB.getEntityItem(i);
                String strSBBUNPUBLISHCTDATE = getAttributeValue(eiCTOSBB, "SBBUNPUBLISHCTDATE", null); //$NON-NLS-1$
                String strSBBPUBLISHCTDATE = getAttributeValue(eiCTOSBB, "SBBPUBLISHCTDATE", null); //$NON-NLS-1$
                if (strSBBPUBLISHCTDATE != null) {
                    EntityItem eiSBB = (EntityItem) eiCTOSBB.getDownLink(0);
                    String strSBBANNOUNCETGT = getAttributeValue(eiSBB, "SBBANNOUNCETGT",null); //$NON-NLS-1$
// TIR 6FCEVD       if (strSBBPUBLISHCTDATE.compareTo(strSBBANNOUNCETGT) < 1) {
                    if (strSBBANNOUNCETGT!=null && strSBBPUBLISHCTDATE.compareTo(strSBBANNOUNCETGT) <0) {
                        //error CCTOSBB:SBBPUBLISHCTDATE is before SBB:SBBANNOUNCETGT.
                        report.append(messages.getString("ERROR_OVERRIDE_PUBLISH_DATE")); //$NON-NLS-1$
                        error = true;
                    }
                }

                if (strSBBUNPUBLISHCTDATE != null) {
                    EntityItem eiSBB = (EntityItem) eiCTOSBB.getDownLink(0);
                    String strSBBWITHDRAWLDATE = getAttributeValue(eiSBB, "SBBWITHDRAWLDATE",null); //$NON-NLS-1$
// TIR 6FCEVD       if (strSBBUNPUBLISHCTDATE.compareTo(strSBBWITHDRAWLDATE) > -1) {
                    if (strSBBWITHDRAWLDATE!=null && strSBBUNPUBLISHCTDATE.compareTo(strSBBWITHDRAWLDATE) >0) {
                        // error CCTOSBB:SBBUNPUBLISHCTDATE is after SBB:SBBWITHDRAWLDATE.
                        report.append(messages.getString("ERROR_OVERRIDE_WITHDRAW_DATE")); //$NON-NLS-1$
                        error = true;
                    }
                }
            }
        }
        return error;
    }

    /**
     *  Get ABR description
     *
     *@return    java.lang.String
     */
    public String getDescription() {
        return messages.getString("DESCRIPTION"); //$NON-NLS-1$
    }

    /**
     *  Get the version
     *
     *@return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.27 $"; //$NON-NLS-1$
    }

    /**
     *  Sets the specified Flag Attribute on the Root Entity
     *
     *@param    strAttributeCode The Flag Attribute Code
     *@param    strAttributeValue The Flag Attribute Value
     *
    private void setFlagValue(
        String strAttributeCode,
        String strAttributeValue) {
        logMessage("****** strAttributeValue set to: " + strAttributeValue); //$NON-NLS-1$

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
                logMessage("setFlagValue: " + e.getMessage()); //$NON-NLS-1$
            } catch (Exception e) {
                logMessage("setFlagValue: " + e.getMessage()); //$NON-NLS-1$
            }
        }
    }*/

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
            locale = new Locale("es", "ES"); //$NON-NLS-1$  //$NON-NLS-2$
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
        int _iLevel)
    {
        String strPSGClass = ""; //$NON-NLS-1$
        logMessage("In printEntity _strEntityType" + _strEntityType + ":_iEntityID:" + _iEntityID + ":_iLevel:" + _iLevel); //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$


        switch (_iLevel) {
        case 0 :
            strPSGClass = "PsgReportSection"; //$NON-NLS-1$
            break;
        case 1 :
            strPSGClass = "PsgReportSectionII"; //$NON-NLS-1$
            break;
        case 2 :
            strPSGClass = "PsgReportSectionIII"; //$NON-NLS-1$
            break;
        case 3 :
            strPSGClass = "PsgReportSectionIV"; //$NON-NLS-1$
            break;
        default :
            strPSGClass = "PsgReportSectionV"; //$NON-NLS-1$
            break;
        }
        logMessage("Printing table width"); //$NON-NLS-1$
        report.append("<table width=\"100%\"><tr><td class=\""); //$NON-NLS-1$
        report.append(strPSGClass);
        report.append("\">"); //$NON-NLS-1$
        report.append(getEntityDescription(_strEntityType));
        report.append(": "); //$NON-NLS-1$
        report.append(getAttributeValue(_strEntityType, _iEntityID, "NAME", DEF_NOT_POPULATED_HTML));  //$NON-NLS-1$
        report.append("</td></tr></table>"); //$NON-NLS-1$
        logMessage("Printing Attributes"); //$NON-NLS-1$
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
        boolean _bShortDesc)
    {
        StringBuffer rpt = report;
        EntityItem entItem = null;
        EntityGroup eg = null;
        String streDesc;
        Hashtable htValues;
        Vector vctTmp;
        int nRows;
        int nAttr;
        String[] astrCodeDesc;
        SortUtil sort = new SortUtil();

        //Get list of attributes for entity
        logMessage("in Print Attributes _strEntityType" + _strEntityType + ":_iEntityID:" + _iEntityID); //$NON-NLS-1$  //$NON-NLS-2$

        if (_strEntityType.equals(getEntityType())) {
            //get the parent entity group for root entity
            eg = _elist.getParentEntityGroup();
        } else {
            eg = _elist.getEntityGroup(_strEntityType);
        }

        if (eg == null) {
            rpt.append("<h3>Warning: Cannot locate an EnityGroup for ");//$NON-NLS-1$
            rpt.append(_strEntityType);
            rpt.append(" so no attributes will be printed.</h3>"); //$NON-NLS-1$
            return;
        }

        entItem = eg.getEntityItem(_strEntityType, _iEntityID);
        //EntityItem entItem = entGroup.getEntityItem(_strEntityType,_iEntityID);
        if (entItem == null) {
            // Lets display the EntityType, entityId in the root..)
            entItem = eg.getEntityItem(0);
            rpt.append("<h3>Warning: Attributes for "); //$NON-NLS-1$
            rpt.append(_strEntityType);
            rpt.append(":"); //$NON-NLS-1$
            rpt.append(_iEntityID);
            rpt.append(" cannot be printed as it is not available in the Extract.</h3>"); //$NON-NLS-1$
            rpt.append("<h3>Warning: Root Entityis "); //$NON-NLS-1$
            rpt.append(getEntityType());
            rpt.append(":"); //$NON-NLS-1$
            rpt.append(getEntityID());
            rpt.append(".</h3>"); //$NON-NLS-1$
            return;
        }

        streDesc = eg.getLongDescription();
        logMessage("Print Attributes Entity desc is " + streDesc); //$NON-NLS-1$
        logMessage("Attribute count is" + entItem.getAttributeCount()); //$NON-NLS-1$
        htValues = new Hashtable();
        vctTmp = new Vector();
        for (int i = 0; i < entItem.getAttributeCount(); i++) {
            EANAttribute EANatt = entItem.getAttribute(i);
            String strValue;
            String strDesc1 = ""; //$NON-NLS-1$
            logMessage("printAttributes " + EANatt.dump(false)); //$NON-NLS-1$
            logMessage("printAttributes " + EANatt.dump(true)); //$NON-NLS-1$

            strValue =
                getAttributeValue(
                    _strEntityType,
                    _iEntityID,
                    EANatt.getAttributeCode(),
                    DEF_NOT_POPULATED_HTML);

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
        nAttr = entItem.getAttributeCount();
        astrCodeDesc = new String[nAttr];

        if (!_bAll) {
            nAttr = vctTmp.size();
            astrCodeDesc = new String[nAttr];
            for (int i = 0; i < nAttr; i++) {
                astrCodeDesc[i] = (String) vctTmp.elementAt(i);
            }
        }

        // Sort on attribute code description
        sort.sort(astrCodeDesc);

        // Output attributes in a two column tale
        rpt.append("<table width=\"100%\">"); //$NON-NLS-1$
        nRows = astrCodeDesc.length - (astrCodeDesc.length / 2);
        for (int i = 0; i < nRows; i++) {
            int col2Index = nRows + i;
            rpt.append("<tr><td class=\"PsgLabel\" valign=\"top\">"); //$NON-NLS-1$
            rpt.append(astrCodeDesc[i]);
            rpt.append("</td><td class=\"PsgText\" valign=\"top\">"); //$NON-NLS-1$
            rpt.append(htValues.get(astrCodeDesc[i]));
            rpt.append("</td>"); //$NON-NLS-1$
            if (col2Index < astrCodeDesc.length) {
                rpt.append("<td class=\"PsgLabel\" valign=\"top\">"); //$NON-NLS-1$
                rpt.append(astrCodeDesc[col2Index]);
                rpt.append("</td><td class=\"PsgText\" valign=\"top\">"); //$NON-NLS-1$
                rpt.append(htValues.get(astrCodeDesc[col2Index]));
                rpt.append("</td><tr>"); //$NON-NLS-1$
            } else {
                rpt.append("<td class=\"PsgLabel\">"); //$NON-NLS-1$
                rpt.append("</td><td class=\"PsgText\">"); //$NON-NLS-1$
                rpt.append("</td><tr>"); //$NON-NLS-1$
            }
        }
        rpt.append("</table><br />"); //$NON-NLS-1$
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
        boolean _bLongDesc)
    {
        EntityGroup entGroup = _elist.getEntityGroup(_strEtype);
        EANMetaAttribute ema = null;
        String desc = null;

        if (entGroup == null) {
            logError("Did not find EntityGroup: " + _strEtype + " in entity list to extract getMetaDescription"); //$NON-NLS-1$  //$NON-NLS-2$
            return null;
        }

        if (entGroup != null) {
            ema = entGroup.getMetaAttribute(_strAttrCode);
        }
        if (ema != null) {
            if (_bLongDesc) {
                desc = ema.getLongDescription();
            } else {
                desc = ema.getShortDescription();
            }
        }
        return desc;
    }
    /**
     *  Triggers the specified workflow
     *
     * @param actionName Name of the workflow action.
     * @exception  java.sql.SQLException  Description of the Exception
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareException  Description of the Exception
     * @exception  COM.ibm.eannounce.objects.WorkflowException  Description of the Exception
     */
    private void triggerWorkFlow(String actionName)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.eannounce.objects.WorkflowException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        EntityGroup eg = m_elist.getParentEntityGroup();
        EntityItem[] aItems = new EntityItem[1];
        WorkflowActionItem wfai = new WorkflowActionItem(null, m_db, m_prof, actionName);
        aItems[0] = eg.getEntityItem(0);
        wfai.setEntityItems(aItems);
        m_db.executeAction(m_prof, wfai);
    }
}
