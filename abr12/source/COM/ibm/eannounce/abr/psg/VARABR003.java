/**
 * <pre>
 * (c) Copyright International Business Machines Corporation, 2004
 * All Rights Reserved.
 *
 * CR0629041824
 *
 * 1.   VARABR003
 *  a)  Variant Setup Verification
 *      ABR is triggered by
 *          Workflow action Set Status to Ready for Review
 *          Change Catalog Editing Status to Ready for Review
 *
 * CHECK  1
 *  Before the Variant status can change to Ready for Review, the following relators must exist:
 *      Only 1 (Quantity = 1)   One or more (Quantity >=1)
 *          DD: through VARDD       SBB: through VARSBB
 *          CTO Parent: through CTOVAR
 *          CPG: through CPGVAR
 *
 *  b)  DG Criteria
 *      On Pass:  none
 *      On Fail: Last Editor
 *
 *  c)  VE Definition
 *
 *  Find the VAR for which this ABR was invoked
 * Level    Dir Parent  Relator Child
 *  0           VAR
 *  0       D   VAR     VARDD   DD
 *  0       D   VAR     VARSBB  SBB
 *  0       U   CTO     CTOVAR  VAR
 *  0       U   CPG     CPGVAR  VAR
 *
 * d)   Triggered by:  State transition
 *  Entity:     VAR
 *  Attribute:  VARSTATUS
 *  Action: Set Status to Ready for Review
 *  e)  ABR Logic
 *  CHECK  1
 *      If 1 and only 1 VARDD & CTOVAR & CPGVAR exist,
 *      And if at least 1 VARSBB exists,
 *      Then set VARABR003 Status to PASS
 *      Else,
 *          Set VARABR003 Status to Fail,
 *
 *  f)  Report
 *      The report should show:
 *          Header
 *          ABR name VARABR003
 *          Date Generated
 *          Who last changed the entity ==> openid ==> userid and role
 *          Description: Variant Setup Verification
 *      Body
 *          VARIANT PN: OFFERINGPNUMB
 *          Fail statement: VARABR003 cannot pass because there is no XXX entity linked to the Variant.
 *          Or
 *          VARABR003 cannot pass because there is more than 1 XXX entity linked to the Variant.
 *
 * $Log: VARABR003.java,v $
 * Revision 1.5  2006/01/24 22:15:51  yang
 * Jtest changes
 *
 * Revision 1.4  2005/10/06 14:59:17  wendy
 * Conform to new jtest config
 *
 * Revision 1.3  2005/02/08 18:29:11  joan
 * changes for Jtest
 *
 * Revision 1.2  2005/01/31 16:30:07  joan
 * make changes for Jtest
 *
 * Revision 1.1  2004/09/03 13:19:38  wendy
 * Init for CR0629041824
 *
 *
 * </pre>
 *
 *@author     Wendy Stimpson
 *@created    August 16, 2004
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
public class VARABR003 extends PokBaseABR
{
    private ResourceBundle bundle = null;

    private StringBuffer rptSb = new StringBuffer();
    private StringBuffer traceSb = new StringBuffer();

    private static final String MISSING="<p>VARABR003 cannot pass because there is no &quot;{0}&quot; entity linked to the Variant.</p>";
    private static final String TOO_MANY="<p>VARABR003 cannot pass because there is more than one &quot;{0}&quot; entity linked to the Variant.</p>";
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /**
     *  Execute ABR.
     *If 1 and only 1 VARDD & CTOVAR & CPGVAR exist,
     *     And if at least 1 VARSBB exists,
     *Then set VARABR003 Status to PASS
     *
     *Else,
     *     Set VARABR003 Status to Fail,
     *
     */
    public void execute_run()
    {
        String HEADER =
            "<html><head><title>{0} {1}</title></head>"+NEWLINE+"<body><h1>{0}: {1}</h1>"+NEWLINE+
            "<p><b>Date: </b>{2}<br/>"+NEWLINE+"<b>User: </b>{3} ({4})<br />"+NEWLINE+"<b>Description: </b>{5}</p>"+NEWLINE+"<!-- {6} -->";
        Object[] args = new String[10];
        MessageFormat msgf = null;
        String navName = "";
        try {
            start_ABRBuild();

            bundle = ResourceBundle.getBundle(this.getClass().getName(), getLocale(m_prof.getReadLanguage().getNLSID()));

            traceSb.append("VARABR003 entered for "+getEntityType()+":"+getEntityID());
            // debug display list of groups
            traceSb.append(NEWLINE+"EntityList contains the following entities: ");
            for (int i=0; i<m_elist.getEntityGroupCount(); i++)
            {
                EntityGroup eg =m_elist.getEntityGroup(i);
                traceSb.append(NEWLINE+eg.getEntityType()+" : "+eg.getEntityItemCount()+" entity items. ");
                if (eg.getEntityItemCount()>0)
                {
                    traceSb.append("IDs(");
                    for (int e=0; e<eg.getEntityItemCount(); e++) {
                        traceSb.append(" "+eg.getEntityItem(e).getEntityID());
                    }
                    traceSb.append(")");
                }
            }
            traceSb.append(NEWLINE);

            // NAME is navigate attributes
            navName = getNavigationName();

            // if VE is not defined, throw exception
            if (m_elist.getEntityGroupCount()==0) {
                throw new Exception("EntityList did not have any groups. Verify that extract is defined.");
            }

/*
    The report should show:
    Header
    ABR name VARABR003
    Date Generated
    Who last changed the entity ==> openid ==> userid and role
    Description: Variant Setup Verification

Body
VARIANT PN: OFFERINGPNUMB
Fail statement: VARABR003 cannot pass because there is no XXX entity linked to the Variant.
Or
VARABR003 cannot pass because there is more than 1 XXX entity linked to the Variant.
*/
            rptSb.append("<h3>"+
                m_elist.getParentEntityGroup().getLongDescription()+" PN: "+
                getAttributeValue(getRootEntityType(), getRootEntityID(), "OFFERINGPNUMB")
                +"</h3>"+NEWLINE);

            if(verifyVAR())  // rptSb will have error msg if failure occurred
            {
                String OK_MSG="<p>All relators are valid.</p>";
                // set VARSTATUS to ready for review
                triggerWorkFlow("WFVARRFR");
                setReturnCode(PASS);
                rptSb.append(bundle==null?OK_MSG:bundle.getString("OK_MSG")+NEWLINE);
            }
            else {
                setReturnCode(FAIL);
            }
        }
        catch (Exception exc)
        {
            String Error_EXCEPTION="<h3><font color=red>Error: {0}</font></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            java.io.StringWriter exBuf = new java.io.StringWriter();
            msgf = new MessageFormat(bundle==null?Error_EXCEPTION:bundle.getString("Error_EXCEPTION"));
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            // Put exception into document
            args[0] = exc.getMessage();
            rptSb.append(msgf.format(args));
            msgf = new MessageFormat(bundle==null?Error_STACKTRACE:bundle.getString("Error_STACKTRACE"));
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args));
            logError("Exception: "+exc.getMessage());
            logError(exBuf.getBuffer().toString());
            setReturnCode(FAIL);
        }
        finally
        {
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("WWABR");  // Amy says same as OFABR001
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }

        // Print everything up to </html>

        // Insert Header into beginning of report
        msgf = new MessageFormat(bundle==null?HEADER:bundle.getString("HEADER"));
        args[0] = getShortClassName(getClass());
        args[1] = navName+((getReturnCode() == PASS) ? " Passed" : " Failed");
        args[2] = getNow();
        args[3] = m_prof.getOPName();
        args[4] = m_prof.getRoleDescription();
        args[5] = getDescription();
        args[6] = getABRVersion();
        rptSb.insert(0, msgf.format(args)+NEWLINE);
        rptSb.append("<!-- DEBUG: "+traceSb.toString()+" -->"+NEWLINE);

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        buildReportFooter();    // Print </html>
    }

    /**********************************************************************************
    *  Verify the correct number of entities are linked, build report string with errors
    *@return  true if all checks are valid
    */
    private boolean verifyVAR()
    {
        boolean success = true;
        EntityGroup eg;
        // check for single relator DD, CTO and CPG
        String[] eTypes = new String[] { "DD", "CTO", "CPG" };
        for (int i=0; i<eTypes.length; i++)
        {
            eg = m_elist.getEntityGroup(eTypes[i]);
            if (eg!=null)
            {
                success = checkSingleRelator(eg) && success;
            }
            else
            {
                traceSb.append(NEWLINE+"ERROR: "+eTypes[i]+" EntityGroup not found");
                success = false;
            }
        }

        // check for 1 or more SBB
        eg = m_elist.getEntityGroup("SBB");
        if (eg!=null)
        {
            if (eg.getEntityItemCount()==0)
            {
                MessageFormat msgf = new MessageFormat(bundle==null?MISSING:bundle.getString("Error_MISSING"));
                Object[] args = new String[1];
                success = false;
                args[0] = eg.getLongDescription();
                rptSb.append(msgf.format(args)+NEWLINE);
                traceSb.append(NEWLINE+"FAIL: "+eg.getEntityType()+" is invalid, has "+eg.getEntityItemCount()+" entities.");
            }
            else
            {
                traceSb.append(NEWLINE+eg.getEntityType()+" EntityGroup is valid (has 1 or more)");
            }
        }
        else
        {
            traceSb.append(NEWLINE+"ERROR: SBB EntityGroup not found");
            success = false;
        }

        return success;
    }
    /**********************************************************************************
    *  Verify there is 1 and only 1 entity linked, build report string with errors
    *@return  true if all checks are valid
    */
    private boolean checkSingleRelator(EntityGroup eg)
    {
        boolean success = false;
        MessageFormat msgf = null;
        Object[] args = new String[1];

        switch(eg.getEntityItemCount())
        {
        case 0:
            msgf = new MessageFormat(bundle==null?MISSING:bundle.getString("Error_MISSING"));
            args[0] = eg.getLongDescription();
            rptSb.append(msgf.format(args)+NEWLINE);
            traceSb.append(NEWLINE+"FAIL: "+eg.getEntityType()+" is invalid, has "+eg.getEntityItemCount()+" entities.");
            break;
        case 1:
            traceSb.append(NEWLINE+eg.getEntityType()+" EntityGroup is valid (has 1)");
            success = true;
            break;
        default:
            msgf = new MessageFormat(bundle==null?TOO_MANY:bundle.getString("Error_TOO_MANY"));
            args[0] = eg.getLongDescription();
            rptSb.append(msgf.format(args)+NEWLINE);
            traceSb.append(NEWLINE+"FAIL: "+eg.getEntityType()+" is invalid, has "+eg.getEntityItemCount()+" entities.");
            break;
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
        WorkflowActionItem wfai;
        EntityGroup eg = m_elist.getParentEntityGroup();
        EntityItem[] aItems = new EntityItem[1];
        aItems[0] = eg.getEntityItem(0);
        wfai = new WorkflowActionItem(null, m_db, m_prof, actionName);
        wfai.setEntityItems(aItems);
        m_db.executeAction(m_prof, wfai);
    }

    /**********************************************************************************
    *  Get Name based on navigation attributes
    *
    *@return    java.lang.String
    */
    private String getNavigationName() throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        EntityGroup eg =  new EntityGroup(null, m_db, m_prof, getRootEntityType(), "Navigate");
        EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            navName.append(getAttributeValue(getRootEntityType(), getRootEntityID(), ma.getAttributeCode()));
            navName.append(" ");
        }

        return navName.toString();
    }

    /***********************************************
    *  Get ABR description
    *
    *@return    java.lang.String
    */
    public String getDescription() {
        String desc =  "Variant Setup Verification.";
        if (bundle!=null) {
            desc = bundle.getString("DESCRIPTION");
        }

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return    java.lang.String
    */
    public String getABRVersion() {
        return "$Revision: 1.5 $";
    }

    /***********************************************
    *  Get the locale for resource file
    *
    *@return   Locale based on nlsid
    */
    private Locale getLocale(int nlsID)
    {
        Locale locale = null;
        switch (nlsID) {
        case 1:
            locale = Locale.US;
        case 2:
            locale = Locale.GERMAN;
            break;
        case 3:
            locale = Locale.ITALIAN;
            break;
        case 4:
            locale = Locale.JAPANESE;
            break;
        case 5:
            locale = Locale.FRENCH;
            break;
        case 6:
            locale = new Locale("es", "ES");
            break;
        case 7:
            locale = Locale.UK;
            break;
        default:
            locale = Locale.US;
        }
        return locale;
    }

}
