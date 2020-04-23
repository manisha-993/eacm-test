/**
 * <pre>
 * (c) Copyright International Business Machines Corporation, 2004
 * All Rights Reserved.
 *
 * CR0629041824
 *
 * CHECK  1
 *  Before the Variant status can change to Ready for Review, the following relators must exist:
 *
 *  Only 1 (Quantity = 1)           One or more (Quantity >=1)
 *  MB: through VARSBB, SBBMB           SBB: through VARSBB
 *  PP: through VARSBB, SBBPP
 *  DD: through VARDD
 *  CTO Parent: through CTOVAR
 *  CPG: through CPGVAR
 *  WAR: through VARWAR
 *
 * CHECK 2
 *  Slot Check: For each PSL (through VARSBB, SBBPSL), there should be a corresponding PSLAVAIL (through VARPSLAVAIL).
 * There is an association between PSL and PSLAVAIL, attribute SLOTAVAILTYPE, to find the corresponding PSL and PSLAVAIL.
 * Note, PSL elements are not required. If no PSL elements are linked, this check should pass (there should not be any
 * PSLAVAIL elements linked as well).
 *
 * NOTE: Each PSL that is linked to an SBB must have a PSLAVAIL associated with it.  Each PSLAVAIL, there should be
 * at least one of the associated PSL with a link to an SBB, 0 is fail, 1 or more is pass
 *
 * CHECK 3
 *  Bay Check: For each PBY (through VARSBB, SBBPBY), there should be a corresponding PBYAVAIL (through VARPBYAVAIL).
 * There is an association between PBY and PBYAVAIL, attribute BAYAVAILTYPE, to find the corresponding PBY and PBYAVAIL.
 * Note, PBY elements are not required. If no PBY elements are linked, this check should pass (there should not be any
 * PBYAVAIL elements linked as well).
 *
 * NOTE: Each PBY that is linked to an SBB must have a PBYAVAIL associated with it.  Each PBYAVAIL, there should be at
 * least one of the associated PBY with a link to an SBB, 0 is fail, 1 or more is pass
 *
 * $Log: VARABR004.java,v $
 * Revision 1.8  2005/10/06 14:59:17  wendy
 * Conform to new jtest config
 *
 * Revision 1.7  2005/04/19 18:57:12  chris
 * Roll back CR4220
 *
 * Revision 1.2  2004/09/20 12:13:14  wendy
 * Added check for status=ready for review
 *
 * Revision 1.1  2004/09/03 13:19:38  wendy
 * Init for CR0629041824
 *
 *
 * </pre>
 *
 *@author     Wendy Stimpson
 *@created    August 23, 2004
 */


package COM.ibm.eannounce.abr.psg;


import COM.ibm.opicmpdh.middleware.*;
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
public class VARABR004 extends PokBaseABR {
    private ResourceBundle bundle = null;

    private StringBuffer rptSb = new StringBuffer();
    private StringBuffer traceSb = new StringBuffer();

    private final String Error_MISSING = "<p>VARABR004 cannot pass because there is no &quot;{0}&quot; entity linked to the Variant.</p>"; //$NON-NLS-1$
    private final String Error_TOO_MANY = "<p>VARABR004 cannot pass because there is more than one &quot;{0}&quot; entity linked to the Variant.</p>"; //$NON-NLS-1$
    private final String Error_TOO_MANY_LINKS="<p>VARABR004 cannot pass because there is more than one &quot;{0}&quot; relator linked to the Variant.</p>";//$NON-NLS-1$
    private final String Check1_PASSED = "<p>Check 1 - Passed</p>"; //$NON-NLS-1$
    private final String Check2_PASSED = "<p>Check 2 - Passed</p>"; //$NON-NLS-1$
    private final String Check3_PASSED = "<p>Check 3 - Passed</p>"; //$NON-NLS-1$
    private final String Error_PSL = "<p>VARABR004 cannot Pass because there is a PSL but no PSLAVAIL, or there is a PSLAVAIL but no PSL, or the SLOTAVAILTYPE does not match.</p>"; //$NON-NLS-1$
    private final String Error_PBY = "<p>VARABR004 cannot Pass because there is a PBY but no PBYAVAIL, or there is a PBYAVAIL but no PBY, or the BAYAVAILTYPE does not match.</p>"; //$NON-NLS-1$
    private final String Error_VARSTATUS = "<p>VARABR004 cannot Pass because Status is not Ready for Review.</p>"; //$NON-NLS-1$
    private final String OK_MSG = "<p>All checks are valid.</p>"; //$NON-NLS-1$
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /**
     *  Execute ABR.
     * Check for required relators and also slot/slotavail and bay/bayavail relationships
     *
     */
    public void execute_run() {
        String navName = ""; //$NON-NLS-1$
        String HEADER = "<html><head><title>{0} {1}</title></head>"+NEWLINE+"<body><h1>{0}: {1}</h1>"+
        NEWLINE+"<p><b>Date: </b>{2}<br/>"+NEWLINE+"<b>User: </b>{3} ({4})<br />"+NEWLINE+"<b>Description: </b>{5}</p>"+NEWLINE+"<!-- {6} -->"; //$NON-NLS-1$

        // Insert Header into beginning of report
        MessageFormat msgf = new MessageFormat(bundle == null ? HEADER : bundle.getString("HEADER")); //$NON-NLS-1$
        Object[] args = new String[10];
        try {
            String varStatus = null;
            boolean success;
            start_ABRBuild();

            bundle =
                ResourceBundle.getBundle(
                    this.getClass().getName(),
                    getLocale(m_prof.getReadLanguage().getNLSID()));

            traceSb.append("VARABR004 entered for " + getEntityType() + ":" + getEntityID()); //$NON-NLS-1$  //$NON-NLS-2$
            // debug display list of groups
            traceSb.append(NEWLINE+"EntityList contains the following entities: "); //$NON-NLS-1$
            for (int i = 0; i < m_elist.getEntityGroupCount(); i++) {
                EntityGroup eg = m_elist.getEntityGroup(i);
                traceSb.append(NEWLINE + eg.getEntityType() + " : " + eg.getEntityItemCount() + " entity items. "); //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
                if (eg.getEntityItemCount() > 0) {
                    traceSb.append("IDs("); //$NON-NLS-1$
                    for (int e = 0; e < eg.getEntityItemCount(); e++) {
                        traceSb.append(" " + eg.getEntityItem(e).getEntityID()); //$NON-NLS-1$
                    }
                    traceSb.append(")"); //$NON-NLS-1$
                }
            }
            traceSb.append(NEWLINE); //$NON-NLS-1$

            // NAME is navigate attributes
            navName = getNavigationName();

            // if VE is not defined, throw exception
            if (m_elist.getEntityGroupCount() == 0) {
                throw new Exception("EntityList did not have any groups. Verify that extract is defined."); //$NON-NLS-1$
            }

            /*
                The report should show:
                Header
                ABR name VARABR004
                Date Generated
                Who last changed the entity ==> openid ==> userid and role
                Description: Variant Required Element Verification

            Body
            VARIANT PN: OFFERINGPNUMB
            Fail statement:
            Check 1- Passed, or VARABR004 cannot Pass because there is no XXX entity linked.
            Check 2- Passed, or VARABR004 cannot Pass because there is a PSL but no PSLAVAIL, or there is a PSLAVAIL but no PSL, or the SLOTAVAILTYPE does not match.
            Check 3- Passed, or VARABR004 cannot Pass because there is a PBY but no PBYAVAIL, or there is a PBYAVAIL but no PBY, or the BAYAVAILTYPE does not match.

            */

            rptSb.append("<h3>" + //$NON-NLS-1$
                m_elist.getParentEntityGroup().getLongDescription() + " PN: " + //$NON-NLS-1$
                getAttributeValue(getRootEntityType(), getRootEntityID(), "OFFERINGPNUMB") //$NON-NLS-1$
                +"</h3>"+NEWLINE); //$NON-NLS-1$

            //from Amy,  I prefer to add the check into abr004 that must be in R4R, else fail.
            //Change Request [0050] Draft [0010] Final [0020] Ready for Review [0040]
            varStatus = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "VARSTATUS", ""); //$NON-NLS-1$  //$NON-NLS-2$
            success = varStatus.equals("0040"); //$NON-NLS-1$
            if (!success) {
                rptSb.append(bundle == null ? Error_VARSTATUS : bundle.getString("Error_VARSTATUS") + NEWLINE); //$NON-NLS-1$  //$NON-NLS-2$
            }

            // check for relators, msgs will be added to rptsb
            if (!checkRelators()) {
                success = false;
            }
            else {
                rptSb.append(bundle == null ? Check1_PASSED : bundle.getString("Check1_PASSED") +NEWLINE); //$NON-NLS-1$  //$NON-NLS-2$
            }

            // check for slots
            if (!checkSlots()) {
                rptSb.append(bundle == null ? Error_PSL : bundle.getString("Error_PSL") + NEWLINE); //$NON-NLS-1$  //$NON-NLS-2$
                success = false;
            }
            else {
                rptSb.append(bundle == null ? Check2_PASSED : bundle.getString("Check2_PASSED") + NEWLINE); //$NON-NLS-1$  //$NON-NLS-2$
            }

            // check for bays
            if (!checkBays()) {
                rptSb.append(bundle == null ? Error_PBY : bundle.getString("Error_PBY") + NEWLINE); //$NON-NLS-1$  //$NON-NLS-2$
                success = false;
            }
            else {
                rptSb.append(bundle == null ? Check3_PASSED : bundle.getString("Check3_PASSED") +NEWLINE); //$NON-NLS-1$  //$NON-NLS-2$
            }

            // rptSb will have error msg if failure occurred
            if (success) {
                // set VARSTATUS to final
                triggerWorkFlow("WFVARFINAL");

                setReturnCode(PASS);

                rptSb.append(bundle == null ? OK_MSG : bundle.getString("OK_MSG") + NEWLINE); //$NON-NLS-1$  //$NON-NLS-2$
            } else {
                setReturnCode(FAIL);
            }
        }
        catch (Exception exc) {
            java.io.StringWriter exBuf = new java.io.StringWriter();
            // Put exception into document
            String Error_EXCEPTION = "<h3><font color=red>Error: {0}</font></h3>"; //$NON-NLS-1$
            String Error_STACKTRACE = "<pre>{0}</pre>"; //$NON-NLS-1$
            msgf = new MessageFormat(bundle == null ? Error_EXCEPTION : bundle.getString("Error_EXCEPTION")); //$NON-NLS-1$
            args = new String[1];
            setReturnCode(FAIL);
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            args[0] = exc.getMessage();
            rptSb.append(msgf.format(args) + NEWLINE); //$NON-NLS-1$
            msgf = new MessageFormat(bundle == null ? Error_STACKTRACE : bundle.getString("Error_STACKTRACE")); //$NON-NLS-1$
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args) + NEWLINE); //$NON-NLS-1$
            logError("Exception: " + exc.getMessage()); //$NON-NLS-1$
            logError(exBuf.getBuffer().toString());
        }
        finally {
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("WWABR"); // Amy says same as OFABR001  //$NON-NLS-1$
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }

        // Print everything up to </html>

        args[0] = getShortClassName(getClass());
        args[1] = navName + ((getReturnCode() == PASS) ? " Passed" : " Failed"); //$NON-NLS-1$  //$NON-NLS-2$
        args[2] = getNow();
        args[3] = m_prof.getOPName();
        args[4] = m_prof.getRoleDescription();
        args[5] = getDescription();
        args[6] = getABRVersion();
        rptSb.insert(0, msgf.format(args));
        rptSb.append("<!-- DEBUG: " + traceSb.toString() + " -->"+NEWLINE); //$NON-NLS-1$  //$NON-NLS-2$

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        buildReportFooter(); // Print </html>
    }

    /**********************************************************************************
    *  Verify the correct number of entities are linked, build report string with errors
    Only 1 (Quantity = 1)               One or more (Quantity >=1)
    MB: through VARSBB, SBBMB           SBB: through VARSBB
    PP: through VARSBB, SBBPP
    DD: through VARDD
    CTO Parent: through CTOVAR
    CPG: through CPGVAR
    WAR: through VARWAR
    *@return  true if all checks are valid
    */
    private boolean checkRelators() {
        boolean success = true;
        EntityGroup eg, reg;

        String[] rTypes = new String[] { "VARDD", "CTOVAR", "CPGVAR", "VARWAR", "SBBMB", "SBBPP" }; //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$  //$NON-NLS-4$  //$NON-NLS-5$  //$NON-NLS-6$
        // check for single relator DD, CTO, CPG, WAR, MB, PP
        String[] eTypes = new String[] { "DD", "CTO", "CPG", "WAR", "MB", "PP" }; //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$  //$NON-NLS-4$  //$NON-NLS-5$  //$NON-NLS-6$
        for (int i = 0; i < eTypes.length; i++) {
            eg = m_elist.getEntityGroup(eTypes[i]);
            reg = m_elist.getEntityGroup(rTypes[i]); //TIR USRO-R-NMHR-6AXL75 spec clarified, can't link same one more than once
            if (eg != null && reg !=null) {
                success = checkSingleRelator(eg, reg) && success;
            } else {
                if (eg == null){
                    traceSb.append(NEWLINE+"ERROR: " + eTypes[i] + " EntityGroup not found"); //$NON-NLS-1$  //$NON-NLS-2$
                }else {
                    traceSb.append(NEWLINE+"ERROR: " + rTypes[i] + " Relator EntityGroup not found"); //$NON-NLS-1$  //$NON-NLS-2$
                }
                success = false;
            }
        }

        // check for 1 or more SBB
        eg = m_elist.getEntityGroup("SBB"); //$NON-NLS-1$
        if (eg != null) {
            if (eg.getEntityItemCount() == 0) {
                MessageFormat msgf = new MessageFormat(bundle == null ? Error_MISSING : bundle.getString("Error_MISSING")); //$NON-NLS-1$
                Object[] args = new String[1];
                success = false;
                args[0] = eg.getLongDescription();
                rptSb.append(msgf.format(args) + NEWLINE); //$NON-NLS-1$
                traceSb.append(NEWLINE+"FAIL: " + eg.getEntityType() + " is invalid, has " + eg.getEntityItemCount() + " entities."); //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
            } else {
                traceSb.append(NEWLINE+"PASS: " + eg.getEntityType() + " EntityGroup is valid (has 1 or more)"); //$NON-NLS-1$  //$NON-NLS-2$
            }
        } else {
            traceSb.append(NEWLINE+"ERROR: SBB EntityGroup not found"); //$NON-NLS-1$
            success = false;
        }

        return success;
    }
    /**********************************************************************************
    *  Verify there is 1 and only 1 entity linked, build report string with errors
    *@return  true if all checks are valid
    */
    private boolean checkSingleRelator(EntityGroup eg, EntityGroup reg) {
        boolean success = false;
        MessageFormat msgf = null;
        Object[] args = new String[1];

        switch (eg.getEntityItemCount()) {
        case 0 :
            msgf = new MessageFormat(bundle == null ? Error_MISSING : bundle.getString("Error_MISSING")); //$NON-NLS-1$
            args[0] = eg.getLongDescription();
            rptSb.append(msgf.format(args) + NEWLINE); //$NON-NLS-1$
            traceSb.append(NEWLINE+"FAIL: " + eg.getEntityType() + " is invalid, has " + eg.getEntityItemCount() + " entities."); //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
            break;
        case 1 :    // only one linked
            // now check for same entity getting linked more than once
            if (reg.getEntityItemCount()==1) {
                traceSb.append(NEWLINE+"PASS: " + eg.getEntityType() + " EntityGroup is valid (has 1)"); //$NON-NLS-1$  //$NON-NLS-2$
                success = true;
            }
            else{
                msgf = new MessageFormat(bundle == null ? Error_TOO_MANY_LINKS : bundle.getString("Error_TOO_MANY_LINKS")); //$NON-NLS-1$
                args[0] = reg.getLongDescription();
                rptSb.append(msgf.format(args) + NEWLINE); //$NON-NLS-1$
                traceSb.append(NEWLINE+"FAIL: " + reg.getEntityType() + " is invalid, has " + reg.getEntityItemCount() + " entities."); //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
            }
            break;
        default :
            msgf = new MessageFormat(bundle == null ? Error_TOO_MANY : bundle.getString("Error_TOO_MANY")); //$NON-NLS-1$
            args[0] = eg.getLongDescription();
            rptSb.append(msgf.format(args) + NEWLINE); //$NON-NLS-1$
            traceSb.append(NEWLINE+"FAIL: " + eg.getEntityType() + " is invalid, has " + eg.getEntityItemCount() + " entities."); //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
            break;
        }

        return success;
    }

    /**********************************************************************************
    *  Slot Check: For each PSL (through VARSBB, SBBPSL), there should be a corresponding PSLAVAIL
    * (through VARPSLAVAIL). There is an association between PSLAVAIL and PSL, attribute SLOTAVAILTYPE,
    * to find the corresponding PSL and PSLAVAIL. Note, PSL elements are not required. If no PSL elements
    * are linked, this check should pass (there should not be any PSLAVAIL elements linked as well).
    *
    *   VAR->VARSBB->SBB->SBBPSL->PSL
    *   VAR->VARPSLAVAIL->PSLAVAIL--->PSLAVAILABILITYA--->PSL
    *
    *@return  true if all checks are valid
    */
    private boolean checkSlots() {
        EntityGroup relatorGrp;
        EntityGroup availGrp;
        String attrCode = "SLOTAVAILTYPE"; //$NON-NLS-1$
        String assocName = "PSLAVAILABILITYA"; //$NON-NLS-1$
        String entityType = "PSL"; //$NON-NLS-1$
        boolean success = false;
        traceSb.append(NEWLINE+NEWLINE+"****CheckSlots ******** "); //$NON-NLS-1$

        // get all PSL thru SBBPSL, can't go directly to the group because there may be some PSL
        // that only came thru PSLAVAILABILITYA
        relatorGrp = m_elist.getEntityGroup("SBBPSL"); //$NON-NLS-1$
        availGrp = m_elist.getEntityGroup("PSLAVAIL"); //$NON-NLS-1$
        if (relatorGrp == null) {
            traceSb.append(NEWLINE+"ERROR: SBBPSL EntityGroup not found"); //$NON-NLS-1$
        }
        else {
            if (availGrp == null) {
                traceSb.append(NEWLINE+"ERROR: PSLAVAIL EntityGroup not found"); //$NON-NLS-1$
            }else {

                // PSL are not required.. no SBBPSL would exist or PSLAVAIL to match
                if (relatorGrp.getEntityItemCount() == 0
                    && availGrp.getEntityItemCount() == 0) {
                    traceSb.append(NEWLINE+"PASS: No PSLAVAIL or SBBPSL pulled by extract."); //$NON-NLS-1$
                    success = true;
                }
                else {
                    // each PSL linked thru SBBPSL must have a assoc to a PSLAVAIL thru PSLAVAILABILITYA
                    success = checkForAvail(relatorGrp, assocName, entityType, attrCode);

                    // each PSLAVAIL will have a SLOTAVAILTYPE value that matches all PSL.SLOTAVAILTYPE associated with it
                    // at least one PSL must be linked to an SBBPSL
                    success =
                        checkForRelator(
                            relatorGrp.getEntityType(),
                            availGrp,
                            assocName,
                            entityType,
                            attrCode)
                        && success;
                }
            }
        }
        return success;
    }

    /**********************************************************************************
    *  Bay Check: For each PBY (through VARSBB, SBBPBY), there should be a corresponding PBYAVAIL
    * (through VARPBYAVAIL). There is an association between PBYAVAIL and PBY, attribute BAYAVAILTYPE,
    * to find the corresponding PBY and PBYAVAIL. Note, PBY elements are not required. If no PBY elements
    * are linked, this check should pass (there should not be any PBYAVAIL elements linked as well).
    *
    *   VAR->VARSBB->SBB->SBBPBY->PBY
    *   VAR->VARPBYAVAIL->PBYAVAIL--->PBYAVAILABILITYA--->PBY
    *
    *@return  true if all checks are valid
    */
    private boolean checkBays() {
        EntityGroup relatorGrp;
        EntityGroup availGrp;
        boolean success =false;
        String attrCode = "BAYAVAILTYPE"; // for PBY only  //$NON-NLS-1$
        String assocName = "PBYAVAILABILITYA"; //$NON-NLS-1$
        String entityType = "PBY"; //$NON-NLS-1$
        traceSb.append(NEWLINE+NEWLINE+"****CheckBays ******** "); //$NON-NLS-1$

        // get all PBY thru SBBPBY, can't go directly to the group because there may be some PBY
        // that only came thru PBYAVAILABILITYA
        relatorGrp = m_elist.getEntityGroup("SBBPBY"); //$NON-NLS-1$
        availGrp = m_elist.getEntityGroup("PBYAVAIL"); //$NON-NLS-1$
        if (relatorGrp == null) {
            traceSb.append(NEWLINE+"ERROR: SBBPBY EntityGroup not found"); //$NON-NLS-1$
        }
        else {
            if (availGrp == null) {
                traceSb.append(NEWLINE+"ERROR: PBYAVAIL EntityGroup not found"); //$NON-NLS-1$
            }else {

                // PBY are not required.. no SBBPBY would exist or PBYAVAIL to match
                if (relatorGrp.getEntityItemCount() == 0
                    && availGrp.getEntityItemCount() == 0) {
                    traceSb.append(NEWLINE+"PASS: No PBYAVAIL or SBBPBY pulled by extract."); //$NON-NLS-1$
                    success= true;
                }else {
                    // each PBY linked thru SBBPBY must have a assoc to a PBYAVAIL thru PBYAVAILABILITYA
                    success = checkForAvail(relatorGrp, assocName, entityType, attrCode);

                    // each PBYAVAIL will have a BAYAVAILTYPE value that matches all PBY.BAYAVAILTYPE associated with it
                    // at least one PBY must be linked to an SBBPBY
                    success =
                        checkForRelator(
                            relatorGrp.getEntityType(),
                            availGrp,
                            assocName,
                            entityType,
                            attrCode)
                        && success;
                }
            }
        }
        return success;
    }

    /**********************************************************************************
    *  Check that there is an Association defined for each entity linked to the relator group
    * For example, the PSL entity group will have PSL pulled thru SBBPSL and thru the
    * PSLAVAIL--->PSLAVAILABILITYA--->PSL association.  Only the PSL pulled thru SBBPSL require
    * an assocation to a PSLAVAIL.  Look at each PSL thru SBBPSL to find a PSLAVAILABILITYA uplink.
    *
    *@param   relatorGrp    EntityGroup with SBBPSL or SBBPBY relators
    *@param   assocName     String with Association name to find as an uplink PSLAVAILABILITYA or PBYAVAILABILITYA
    *@param   checkEntityType   String with entity type PSL or PBY that must be linked and associated
    *@param   attrCode      String with attribute code, debug info only
    *@return  true if all linked entities have associations
    */
    private boolean checkForAvail(
        EntityGroup relatorGrp,
        String assocName,
        String checkEntityType,
        String attrCode) {
        boolean success = true;

        traceSb.append(
            NEWLINE+"checkForAvail: Checking " + //$NON-NLS-1$
            checkEntityType +
            " pulled thru " + //$NON-NLS-1$
            relatorGrp.getEntityType() +
            " for Association to " + //$NON-NLS-1$
            assocName);

        // check each PSL linked to SBBPSL for association to a PSLAVAIL
        for (int ie = 0;
            ie < relatorGrp.getEntityItemCount();
            ie++) // start with SBBPSL group
        {
            EntityItem entityLink = relatorGrp.getEntityItem(ie);
            traceSb.append(NEWLINE+"Relator " + entityLink.getEntityType() + ":" + entityLink.getEntityID()); //$NON-NLS-1$  //$NON-NLS-2$
            for (int di = 0; di < entityLink.getDownLinkCount(); di++) {
                EANEntity entityItem = entityLink.getDownLink(di);
                // look for PSL, really can't be anything else here
                if (entityItem.getEntityType().equals(checkEntityType)) {
                    boolean assocFnd = false;
                    traceSb.append(NEWLINE+"  Entity " + entityItem.getEntityType() + ":" + entityItem.getEntityID()); //$NON-NLS-1$  //$NON-NLS-2$
                    traceSb.append(" " + attrCode + ": " + getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), attrCode)); //$NON-NLS-1$  //$NON-NLS-2$
                    // look for PSLAVAILABILITYA
                    for (int ui = 0; ui < entityItem.getUpLinkCount(); ui++) {
                        EANEntity entityAssoc = entityItem.getUpLink(ui);
                        // PSLAVAILABILITYA
                        if (entityAssoc.getEntityType().equals(assocName)) {
                            traceSb.append(NEWLINE+"    uplink (assoc) " + entityAssoc.getEntityType() + ":" + entityAssoc.getEntityID()); //$NON-NLS-1$  //$NON-NLS-2$
                            // the only place this can go is to the PSLAVAIL (or PBYAVAIL)
                            // Associations are only built Down, so this can only exist if
                            // the extract traveled down the VAR->VARPSLAVAIL->PSLAVAIL--->PSLAVAILABILITYA path
                            assocFnd = true;
                            for (int a = 0;
                                a < entityAssoc.getUpLinkCount();
                                a++) // PSLAVAIL
                            {
                                EANEntity entityAvail =
                                    entityAssoc.getUpLink(a);
                                traceSb.append(NEWLINE+"      uplink (entity) " + entityAvail.getEntityType() + ":" + entityAvail.getEntityID()); //$NON-NLS-1$  //$NON-NLS-2$
                                traceSb.append(" " + attrCode + ": " + getAttributeValue(entityAvail.getEntityType(), entityAvail.getEntityID(), attrCode)); //$NON-NLS-1$  //$NON-NLS-2$
                                traceSb.append(
                                    NEWLINE+"PASS: " + //$NON-NLS-1$
                                    entityItem.getEntityType() +
                                    ":" + //$NON-NLS-1$
                                    entityItem.getEntityID() +
                                    " was linked thru " + //$NON-NLS-1$
                                    entityLink.getEntityType() +
                                    ":" + entityLink.getEntityID() +
                                    " AND was associated thru " + //$NON-NLS-1$
                                    entityAssoc.getEntityType() +
                                    ":" + //$NON-NLS-1$
                                    entityAssoc.getEntityID() +
                                    " to " + //$NON-NLS-1$
                                    entityAvail.getEntityType() +
                                    ":" + //$NON-NLS-1$
                                    entityAvail.getEntityID());
                            }
                        }
                    }
                    if (!assocFnd) {
                        success = false;
                        traceSb.append(
                            NEWLINE+"FAIL: " +  //$NON-NLS-1$
                            entityItem.getEntityType() +
                            ":" +  //$NON-NLS-1$
                            entityItem.getEntityID() +
                            " " +  //$NON-NLS-1$
                            attrCode +
                            ": " +  //$NON-NLS-1$
                            getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), attrCode) +
                            " was linked thru " + //$NON-NLS-1$
                            entityLink.getEntityType() +
                            ":" +  //$NON-NLS-1$
                            entityLink.getEntityID() +
                            " BUT was not associated to " + //$NON-NLS-1$
                            assocName);

                        //rptSb.append();
                    }
                } // entity is checkEntityType (PSL or PBY)
            }
        }

        return success;
    }

    /**********************************************************************************
    *  Check that there is at least one entity linked to the relator for each AVAIL
    * For example, the PSLAVAIL entity group will be associated to PSL pulled thru PSLAVAILABILITYA.
    * One PSLAVAIL and the associated PSL will share the same SLOTAVAILTYPE value, yet all the PSL
    * may not be linked back to an SBB.  As long as one of these associated PSL is linked, the
    * check is valid.
    *
    *@param   relatorName   Relator type .. SBBPSL or SBBPBY relators
    *@param   availGrp      EntityGroup with PSLAVAIL or PBYAVAIL entities
    *@param   assocName     String with Association name to find as an uplink PSLAVAILABILITYA or PBYAVAILABILITYA
    *@param   checkEntityType   String with entity type PSL or PBY that must be linked and associated
    *@param   attrCode      String with attribute code, debug info only
    *@return  true if each PSLAVAIL or PBYAVAIL has at least one associated PSL or PBY linked to an SBBPSL or SBBPBY
    */
    private boolean checkForRelator(
        String relatorName,
        EntityGroup availGrp,
        String assocName,
        String checkEntityType,
        String attrCode) {
        boolean success = true;

        String availName = availGrp.getEntityType();
        traceSb.append("checkForRelator: Checking " + availName + " thru Association " + assocName + " for " + checkEntityType + " linked thru " + relatorName); //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$  //$NON-NLS-4$
        // check all PSLAVAIL for at least one PSL linked to SBBPSL also
        for (int ie = 0; ie < availGrp.getEntityItemCount(); ie++) {
            // PSLAVAIL
            boolean relFound = false;
            EntityItem entityItem = availGrp.getEntityItem(ie);
            traceSb.append(NEWLINE+"Entity " + entityItem.getEntityType() + ":" + entityItem.getEntityID()); //$NON-NLS-1$  //$NON-NLS-2$
            traceSb.append(" " + attrCode + ": " + getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), attrCode)); //$NON-NLS-1$  //$NON-NLS-2$

            // the association may pull extra PSL, as long as one is linked back to an SBB, this PSLAVAIL is valid
            // PSLAVAILABILITYA
            for (int di = 0; di < entityItem.getDownLinkCount(); di++) {
                EANEntity entityAssoc = entityItem.getDownLink(di);
                if (entityAssoc.getEntityType().equals(assocName)) {
                    traceSb.append(NEWLINE+"  downLink (assoc) " + entityAssoc.getEntityType() + ":" + entityAssoc.getEntityID()); //$NON-NLS-1$  //$NON-NLS-2$
                    // look for PSL
                    for (int d2 = 0; d2 < entityAssoc.getDownLinkCount(); d2++) { // PSL
                        EANEntity entity = entityAssoc.getDownLink(d2);
                        if (entity.getEntityType().equals(checkEntityType)) {
                            boolean rel2Found = false;
                            // one PSL here must also be linked to SBBPSL
                            traceSb.append(NEWLINE+"    downlink (entity) " + entity.getEntityType() + ":" + entity.getEntityID()); //+" uplink cnt: "+entity.getUpLinkCount());  //$NON-NLS-1$  //$NON-NLS-2$
                            traceSb.append(" " + attrCode + ": " + getAttributeValue(entity.getEntityType(), entity.getEntityID(), attrCode)); //$NON-NLS-1$  //$NON-NLS-2$
                            // look for any uplink to SBBPSL
                            for (int ui = 0; ui < entity.getUpLinkCount(); ui++) {
                                EANEntity relator = entity.getUpLink(ui);
                                if (relator
                                    .getEntityType()
                                    .equals(relatorName)) {
                                    traceSb.append(NEWLINE+"      uplink (relator?) " + relator.getEntityType() + ":" + relator.getEntityID()); //$NON-NLS-1$  //$NON-NLS-2$
                                    rel2Found = true;
                                    relFound = true;
                                    traceSb.append(
                                        NEWLINE+"PASS: " +  //$NON-NLS-1$
                                        entity.getEntityType() +
                                        ":" + //$NON-NLS-1$
                                        entity.getEntityID() +
                                        " was associated thru " + //$NON-NLS-1$
                                        entityAssoc.getEntityType() +
                                        ":" + //$NON-NLS-1$
                                        entityAssoc.getEntityID() +
                                        " to " + //$NON-NLS-1$
                                        entityItem.getEntityType() +
                                        ":" + //$NON-NLS-1$
                                        entityItem.getEntityID() +
                                        " AND was linked to " + //$NON-NLS-1$
                                        relator.getEntityType() +
                                        ":" + //$NON-NLS-1$
                                        relator.getEntityID());
                                }
                            }
                            if (!rel2Found) {
                                traceSb.append(
                                    NEWLINE+"INFO: " +  //$NON-NLS-1$
                                    entity.getEntityType() +
                                    ":" +  //$NON-NLS-1$
                                    entity.getEntityID() +
                                    " was associated thru " + //$NON-NLS-1$
                                    entityAssoc.getEntityType() +
                                    ":" +  //$NON-NLS-1$
                                    entityAssoc.getEntityID() +
                                    " to " + //$NON-NLS-1$
                                    entityItem.getEntityType() +
                                    ":" +  //$NON-NLS-1$
                                    entityItem.getEntityID() +
                                    " BUT was not linked to " + //$NON-NLS-1$
                                    relatorName);
                            }
                        }
                    }
                }
            }
            if (!relFound) {
                // a SBBPSL relator was not found for any of the associated PSL
                success = false;
                traceSb.append(
                    NEWLINE+"FAIL: NO " + //$NON-NLS-1$
                    relatorName +
                    " links found thru " + //$NON-NLS-1$
                    assocName +
                    " for " +  //$NON-NLS-1$
                    entityItem.getEntityType() +
                    ":" +  //$NON-NLS-1$
                    entityItem.getEntityID() + //$NON-NLS-1$
                    " " +  //$NON-NLS-1$
                    attrCode +
                    ": " +  //$NON-NLS-1$
                    getAttributeValue(entityItem.getEntityType(), entityItem.getEntityID(), attrCode)); //$NON-NLS-1$
                // rptSb.append();
            }
        }

        return success;
    }

    /**********************************************
     *  Triggers the specified workflow to advance VARSTATUS
     *
     * @param actionName Name of the workflow action.
     * @exception  java.sql.SQLException
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareException
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @exception  COM.ibm.eannounce.objects.WorkflowException
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

    /**********************************************************************************
    *  Get Name based on navigation attributes
    *
    *@return    java.lang.String
    */
    private String getNavigationName()
        throws java.sql.SQLException, MiddlewareException {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        EntityGroup eg = new EntityGroup(null, m_db, m_prof, getRootEntityType(), "Navigate"); //$NON-NLS-1$
        EANList metaList = eg.getMetaAttribute();
        // iterator does not maintain navigate order
        for (int ii = 0; ii < metaList.size(); ii++) {
            EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
            navName.append(
                getAttributeValue(
                    getRootEntityType(),
                    getRootEntityID(),
                    ma.getAttributeCode()));
            navName.append(" "); //$NON-NLS-1$
        }

        return navName.toString();
    }

    /***********************************************
    *  Get ABR description
    *
    *@return    java.lang.String
    */
    public String getDescription() {
        String desc = "Variant Required Element Verification."; //$NON-NLS-1$
        if (bundle != null) {
            desc = bundle.getString("DESCRIPTION"); //$NON-NLS-1$
        }

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return    java.lang.String
    */
    public String getABRVersion() {
        return "$Revision: 1.8 $";
    }

    /***********************************************
    *  Get the locale for resource file
    *
    *@return   Locale based on nlsid
    */
    private Locale getLocale(int nlsID) {
        Locale locale = null;
        switch (nlsID) {
        case 1 :
            locale = Locale.US;
            break;
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
}
