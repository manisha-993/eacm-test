//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * <pre>Verifies Curriculum Entity
 *LSCURABR5 Verifies Curriculum Entity(Implements CR0914011132)
 *===================================
 *The report should show:
 *ABR name
 *Date Generated
 *Who last changed the entity ==> userid (openid)
 *Description: Verifies Curriculum Entity
 *Narrative:
 *This ABR is scheduled to run after the Curriculum Life Cycle Status is
 *changed from "New" or  "Change Request" to "Verify".
 *Rules:
 *Check that there is one and only one relator between:
 *Curriculum and Owner
 *Curriculum and Division
 *PASS Logic:
 *set the Curriculum Life Cycle Status to "Available" and print:
 *IAB2009I: Curriculum Code: (Curriculum ID and Title) is now available for use.
 *FAIL Logic:
 *set the Curriculum Life Cycle status to "Failed" and print the appropriate message:
 *Case 1: IAB1002E: Relators not defined: The Curriculum "(Curriculum Code: Title)" must have one and only one "Curriculum to Owner" relator defined.
 *Case 2: IAB1003E: Too many relators defined. The Curriculum "(Curriculum Code: Title)" has more than one "Curriculum to Owner" relator defined.
 *
 *
 * Change History (most recent at top)
 * $Log: LSCURABR5.java,v $
 * Revision 1.20  2014/01/13 13:50:51  wendy
 * migration to V17
 *
 * Revision 1.19  2006/06/26 12:42:35  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.18  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.17  2006/03/06 17:11:01  couto
 * Changed layout, using EACustom methods.
 * Fixed br tags. Chaged font tags. Fixed multiple return statements.
 *
 * Revision 1.16  2006/01/23 17:25:15  joan
 * Jtest
 *
 * Revision 1.15  2006/01/22 18:28:29  joan
 * changes for Jtest
 *
 * Revision 1.14  2005/01/26 23:23:46  joan
 * changes for Jtest
 *
 * Revision 1.13  2003/12/02 20:19:46  chris
 * Spec changes related to feedbacks 53243:653AE1 and 53251:75170B
 *
 * Revision 1.12  2003/11/12 20:35:27  chris
 * put Revision back
 *
 * Revision 1.11  2003/11/06 22:01:17  bala
 * EC drop
 *
 * Revision 1.5  2003/10/30 20:32:25  cstolpe
 * Changed setDGTitle
 *
 * Revision 1.4  2003/10/28 20:58:57  cstolpe
 * setRptClass in all and Linking in ABR 1
 *
 * Revision 1.3  2003/10/23 19:23:58  cstolpe
 * Check lifecycle and do VELOCK
 *
 * Revision 1.2  2003/10/15 19:35:25  cstolpe
 * Latest Updates
 *
 * Revision 1.1  2003/10/09 18:51:55  cstolpe
 * Initial LS drop
 *
 * </pre>
 * @author     Chris Stolpe
 * @created    October 8, 2001
 */

package COM.ibm.eannounce.abr.ls;

//import COM.ibm.opicmpdh.transactions.*;
//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.eannounce.objects.WorkflowException;
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
public class LSCURABR5 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.";
	
    /**
     * Work of ABR is done here
     *
     */
    public void execute_run() {
        try {
			Object[] mfParms = new String[10];
			MessageFormat mfOut = new MessageFormat("<p class=\"ibm-intro ibm-alternate-three\"><em>{0}</em></p><p><b>Date: </b>{1}<br /><b>User: </b>{2} ({3})<br /><b>Description: </b>{4}</p><!-- {5} -->");
			StringBuffer sb = new StringBuffer();
			int iReturnCode = PASS;

            start_ABRBuild();
            setDGTitle(getDGTitle(true));

            mfParms[0] = getShortClassName(getClass());
            mfParms[1] = getNow();
            mfParms[2] = m_prof.getOPName();
            mfParms[3] = m_prof.getRoleDescription();
            mfParms[4] = getDescription();
            mfParms[5] = getABRVersion();
			println(EACustom.getDocTypeHtml());
			println("<head>");
			println(EACustom.getMetaTags(getDescription()));
			println(EACustom.getCSS());
			println(EACustom.getTitle(getShortClassName(getClass())));
			println("</head>");
			println("<body id=\"ibm-com\">");
			println(EACustom.getMastheadDiv());
			
            println(mfOut.format(mfParms));

            sb.append(getEntityDescription(getEntityType()));
            sb.append(" Code: ");
            sb.append(getAttributeValue(getEntityType(), getEntityID(), "LSCURID", DEF_NOT_POPULATED_HTML));
            sb.append("<br />");
            println(sb.toString());

            //if (checkFlagValues("LSCURLIFECYCLE", XR_LSCURLIFECYCLE_Available) ||
            //    checkFlagValues("LSCURLIFECYCLE", XR_LSCURLIFECYCLE_Retired))
            //{
            //    println("<br />LSCURABR5 not processed.  Current Lifecycle Status is not appropriate for this action.<br />");
            //    iReturnCode = FAIL;
            //}
            //else {
            // check for all necessary relators
            m_bOutputAsList = true;
            println(NEW_LINE + "<b>Relator check:</b>");
            println("<ul>");

            if (!checkRelators()) {
                iReturnCode = FAIL;
            }
            else {
                println("<li>All required relators are linked.</li>");
            }
            println("</ul>");
            m_bOutputAsList = false;
            println("<br />");

            if (iReturnCode == PASS) {
                printMessage(MSG_IAB2009I, new String[] {getABREntityDesc(getEntityType(), getEntityID())});
                triggerWorkFlow("WFCURAV");
                triggerLock("EXTLSCURLOCK");
            }
            else {
                triggerWorkFlow("WFCURFL");
            }
            //}
            sb = new StringBuffer();
            sb.append(getShortClassName(getClass()));
            sb.append(" has ");
            sb.append((iReturnCode == PASS) ? "Passed" : "Failed");
            sb.append(".<br />");
            println(sb.toString());
            setReturnCode(iReturnCode);
            setDGTitle(getDGTitle(false));
        }
        catch (LockPDHEntityException le) {
            setReturnCode(FAIL);
            println(
                    "<h3 style=\"color:#c00; font-weight:bold;\">" +
                    ERR_IAB1007E +
                    "<br />" +
                    le.getMessage() +
                    "</h3>");
            logError(le.getMessage());
        }
        catch (UpdatePDHEntityException le) {
            setReturnCode(FAIL);
            println(
                    "<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " +
                    le.getMessage() +
                    "</h3>");
            logError(le.getMessage());
        }
        catch (Exception exc) {
            // Report this error to both the datbase log and the PrintWriter
			java.io.StringWriter exBuf = new java.io.StringWriter();
            println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
            println("" + exc);
            
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            println("<pre>");
            println(exBuf.getBuffer().toString());
            println("</pre>");
            setReturnCode(FAIL);
        }
        finally {
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("LSCURABR");
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }
        
		//Print everything up to </html>
		println(EACustom.getTOUDiv());
		printDGSubmitString();
		buildReportFooter();    // Print </html>
    }

    /**
     * Get the DG title based on the entity Nav attributes if ignore is false and the ABR is
     * one of the Nav attributes it will use the ABR return code to determine the value otherwise
     * it uses the value of the attribute from the extract
     *
     * @param ignore Wether to ignore the ABR navigate attribute.
     * @exception  java.sql.SQLException  Description of the Exception
     */
    private  String getDGTitle(boolean ignore)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        EntityGroup eg =  new EntityGroup(null, m_db, m_prof, getRootEntityType(), "Navigate");
        Iterator itMeta = eg.getMetaAttribute().values().iterator();
        while (itMeta.hasNext()) {
            EANMetaAttribute ma = (EANMetaAttribute) itMeta.next();
            if (!ignore && ma.getAttributeCode().equals(getShortClassName(getClass()))) { // It's actual value is in proccess or queued
                navName.append((getReturnCode() == PASS) ? "Passed" : "Failed");
            }
            else {
                navName.append(getAttributeValue(getRootEntityType(), getRootEntityID(), ma.getAttributeCode()));
            }
            if (itMeta.hasNext()) {
                navName.append(" ");
            }
        }
        return navName.toString();
    }

    /**
     *  Description of the Method
     *
     * @return                Description of the Return Value
     * @exception  Exception  Description of the Exception
     */
    private boolean checkRelators() throws Exception {
        boolean bResult = true;

        // A Curriculum must be linked to one and only one Division
        if (!checkRelator2("LSDIV", "LSDIVCUR", UNIQUE2, false)) {
        //if (!checkUniqueParent("LSDIV", "LSDIVCUR")) { // Behavior changed by LF
            bResult = false;
        }

        // there must be one and only one owner
        if (!checkRelator2("LSPER", "LSCURPEROW", UNIQUE2, true)) {
        //if (!checkUniqueChild("LSPER", "LSCURPEROW")) { // Behavior changed by LF
            bResult = false;
        }

        return bResult;
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
		WorkflowActionItem wfai = null;
        EntityGroup eg = m_elist.getParentEntityGroup();
        EntityItem[] aItems = new EntityItem[1];
        aItems[0] = eg.getEntityItem(0);
        wfai = new WorkflowActionItem(null, m_db, m_prof, actionName);
        wfai.setEntityItems(aItems);
        m_db.executeAction(m_prof, wfai);
    }

    /**
     *  Triggers the specified lock action
     *
     * @param actionName Name of the lock action.
     * @exception  java.sql.SQLException  Description of the Exception
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareException  Description of the Exception
     */
    private void triggerLock(String actionName)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
		LockActionItem lai = null;
        EntityGroup eg = m_elist.getParentEntityGroup();
        EntityItem[] aItems = new EntityItem[1];
        aItems[0] = eg.getEntityItem(0);
        lai = new LockActionItem(null, m_db, m_prof, actionName);
        lai.setEntityItems(aItems);
        m_db.executeAction(m_prof, lai);
    }

    /**
     *  Gets the description attribute of the LSCURABR5 object
     *
     * @return    The description value
     */
    public String getDescription() {
        return "Validates Curriculum Entity";
    }


    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.20 $";
    }
    /**
    *  Check for entities through the specified relator
    *  behavior of getUniqueChild and getUniqueParent has changed. It no longer uses checkRelator
    *  checkRelator has private access in PokBaseABR so I cannot invoke it or override it. I had to copy it to each ABR that needed it.
    *
    *@param  _strEntityType   Description of the Parameter
    *@param  _strRelatorType  Description of the Parameter
    *@param  _iState          Description of the Parameter
    *@param  _bChild          Description of the Parameter
    *@return                  boolean
    */
    private boolean checkRelator2(String _strEntityType, String _strRelatorType, int _iState, boolean _bChild) {

        Vector vct = null;
		boolean ret = true;
		int iCount = -1;
        String strEntityDesc = getEntityDescription(_strRelatorType);
        String[] astrMessage = new String[]{
            getEntityDescription(getEntityType()),
            getABREntityDesc(getEntityType(), getEntityID()),
            strEntityDesc
        };

        if (_bChild) {
            vct = getChildrenEntityIds(getEntityType(), getEntityID(), _strEntityType, _strRelatorType);
        } else {
            vct = getParentEntityIds(getEntityType(), getEntityID(), _strEntityType, _strRelatorType);
        }

        iCount = vct.size();

    // avoid hardcoding description, even if entity id=0, the description
    // is valid so get it if possible


        switch (_iState) {
        case UNIQUE2:                                   // one and only one required
            switch (iCount) {
            case 0:
                printErrorMessage(ERR_IAB1002E, astrMessage);
                //return false;
                ret = false;
                break;
            case 1:
                break;
            default:
                printErrorMessage(ERR_IAB1003E, astrMessage);
                //return false;
                ret = false;
                break;
            }
            break;
        case ONE_OR_MORE2:                              // at least one is required
            if (iCount == 0) {
                printErrorMessage(ERR_IAB1004E, astrMessage);
                //return false;
                ret = false;
                break;
            }
            break;
        case ONE_OR_LESS2:                              // may have only one
            if (iCount > 1) {
                printErrorMessage(ERR_IAB1003E, astrMessage);
                //return false;
                ret = false;
                break;
            }
            break;
        default:
            break;
        }

        if (ret) {
        	printTestedMessage(MSG_IAB2012I, astrMessage);
        }
        return ret;
    }
    // used to control relator search
    private final static int UNIQUE2 = 0;
    private final static int ONE_OR_MORE2 = 1;
    private final static int ONE_OR_LESS2 = 2;
}
