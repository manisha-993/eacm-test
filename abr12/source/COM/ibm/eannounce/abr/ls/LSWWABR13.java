//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * <pre>IBM Confidential
 * (c) Copyright International Business Machines Corporation, 2001
 * All Rights Reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 * Verifies Worldwide Course Entity
 *
 *
 * Change History (most recent at top)
 * $Log: LSWWABR13.java,v $
 * Revision 1.23  2014/01/13 13:50:51  wendy
 * migration to V17
 *
 * Revision 1.22  2006/06/26 12:42:34  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.21  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.20  2006/03/08 12:36:32  couto
 * Changed layout, using EACustom methods.
 * Fixed br tags. Chaged font tags. Fixed multiple return statements.
 *
 * Revision 1.19  2006/01/26 00:36:18  joan
 * Jtest
 *
 * Revision 1.18  2006/01/22 18:28:29  joan
 * changes for Jtest
 *
 * Revision 1.17  2005/01/26 23:36:59  joan
 * changes for Jtest
 *
 * Revision 1.16  2004/05/13 19:04:18  chris
 * CR Demo Changes
 *
 * Revision 1.15  2004/05/07 20:34:14  chris
 * Fix for 53848:51A197
 *
 * Revision 1.14  2004/03/09 15:58:01  chris
 * Changes for CR0701025842, CR1218022919, CR050902204
 *
 * Revision 1.13  2003/12/02 20:19:46  chris
 * Spec changes related to feedbacks 53243:653AE1 and 53251:75170B
 *
 * Revision 1.12  2003/11/12 20:35:28  chris
 * put Revision back
 *
 * Revision 1.11  2003/11/06 22:01:18  bala
 * EC drop
 *
 * Revision 1.6  2003/10/30 20:32:27  cstolpe
 * Changed setDGTitle
 *
 * Revision 1.5  2003/10/28 20:58:58  cstolpe
 * setRptClass in all and Linking in ABR 1
 *
 * Revision 1.4  2003/10/23 19:23:59  cstolpe
 * Check lifecycle and do VELOCK
 *
 * Revision 1.3  2003/10/20 19:26:54  cstolpe
 * latest
 *
 * Revision 1.2  2003/10/15 19:35:26  cstolpe
 * Latest Updates
 *
 * Revision 1.1  2003/10/09 18:51:56  cstolpe
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
public class LSWWABR13 extends PokBaseABR {
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
			int iResults = PASS;
			StringBuffer sb = new StringBuffer();
			
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
            sb.append(getAttributeValue(getEntityType(), getEntityID(), "LSWWID", DEF_NOT_POPULATED_HTML));
            sb.append("<br />");
            println(sb.toString());

            //if (checkFlagValues("LSWWLIFECYCLE", XR_LSWWLIFECYCLE_Available) ||
            //    checkFlagValues("LSWWLIFECYCLE", XR_LSWWLIFECYCLE_Retired))
            //{
            //    println("<br />LSWWABR13 not processed.  Current Lifecycle Status is not appropriate for this action.<br />");
            //    iResults = FAIL;
            //}
            //else {
            // check for all necessary relators
            m_bOutputAsList = true;
            println(NEW_LINE + "<b>Relator check:</b>");
            println("<ul>");
            if (!checkRelators()) {
                iResults = FAIL;
            }
            else {
                println("<li>All required relators are linked.</li>");
            }
            println("</ul>");
            m_bOutputAsList = false;

            if (iResults == PASS) {
                printMessage(MSG_IAB2009I, new String[]{getABREntityDesc(getEntityType(), getEntityID())});
                triggerWorkFlow("WFWWAV");
                triggerLock("EXTLSWWLOCK");
            }
            else {
                triggerWorkFlow("WFWWFL");
            }
            //}
            sb = new StringBuffer();
            sb.append(getShortClassName(getClass()));
            sb.append(" has ");
            sb.append((iResults == PASS) ? "Passed" : "Failed");
            sb.append(".<br />");
            println(sb.toString());

            setReturnCode(iResults);
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
            println("<br />" + exc);
            
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            println("<pre>");
            println(exBuf.getBuffer().toString());
            println("</pre>");
            setReturnCode(FAIL);
        }
        finally {
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("LSWWABR");
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }
        
		//Print everything up to </html>
		println(EACustom.getTOUDiv());
		printDGSubmitString();
		buildReportFooter();
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
     * Check for necessary relators
     *
     * @return        boolean
     */
    private boolean checkRelators() {
        boolean result = true;
		boolean bSC = false;
		boolean bCUR = false;
		
        // A Worldwide course must be linked to one and only one Curriculum
        if (!checkRelator2("LSCUR", "LSCURWW", UNIQUE2, false)) {
        //if (!checkUniqueParent("LSCUR", "LSCURWW")) { // Behavior changed by LF
            result = false;
        }

        // there must be one and only one owner
        if (!checkRelator2("LSPER", "LSWWPEROW", UNIQUE2, true)) {
        //if (!checkUniqueChild("LSPER", "LSWWPEROW")) { // Behavior changed by LF
            result = false;
        }

        // there must be one and only one course contact
        if (!checkRelator2("LSPER", "LSWWPERCON", UNIQUE2, true)) {
        //if (!checkUniqueChild("LSPER", "LSWWPERCON")) { // Behavior changed by LF
            result = false;
        }

        // there must be one and only one Sub-curriculum v1.2
        bSC = checkRelator2("LSSC", "LSWWSC", UNIQUE2, true);
        if (!bSC) {
        //if (!checkUniqueChild("LSSC", "LSWWSC")) { // Behavior changed by LF
            result = false;
        }
        // Curriculum must be available
        bCUR = checkForAvailableParents( getRootEntityType(), getRootEntityID(), "LSCUR","LSCURWW", "LSCURLIFECYCLE",XR_LSCURLIFECYCLE_Available);
        if (!bCUR) {
            result = false;
        }

        if (bSC && bCUR) {
            Vector vCUR = getParentEntityIds(getEntityType(), getEntityID(), "LSCUR", "LSCURWW");
            int iSC = getChildEntityId(getEntityType(), getEntityID(), "LSSC", "LSWWSC");
            boolean bFoundOne = false;
            Iterator itCUR = vCUR.iterator();
            while (itCUR.hasNext()) {
                Integer iCUR = (Integer) itCUR.next();
                Vector vCURChild = getChildrenEntityIds("LSCUR", iCUR.intValue(), "LSSC", "LSCURSC");
                Iterator itCURChild = vCURChild.iterator();
                while (itCURChild.hasNext()) {
                    Integer iCURChild = (Integer) itCURChild.next();
                    if (iSC == iCURChild.intValue()) {
                        bFoundOne = true;
                        println("<!-- SC:" + iCURChild.intValue() + " linked to CUR:" + iCUR + " is linked to this WW -->");
                    }
                    else {
                        println("<!-- SC:" + iCURChild.intValue() + " linked to CUR:" + iCUR + " is not linked to this WW -->");
                    }
                }
            }
            //int iSCParent = getParentEntityId("SC", iSC, "LSCUR", "LSCURSC"); something wrong with this method
            //    println("<!-- CUR:" + iCUR + ", SC:" + iSC + ", SC Parent:" + iSCParent + ", CUR Child:"+iCURChild+" -->");
            if (bFoundOne) {
                println("<!-- CUR is linked to SC -->");
            }
            else {
                printErrorMessage(
                        "There is in invalid pairing of %1# %2# and %3# %4# for this %5#.",
                        new String[]{
                        getEntityDescription("LSCUR"),
                        getAttributeValue("LSCUR", ((Integer) vCUR.firstElement()).intValue(), "LSCURTITLE"),
                        getEntityDescription("LSSC"),
                        getAttributeValue("LSSC", iSC, "LSSCTITLE"),
                        getEntityDescription(getEntityType())});
                result = false;
            }
        }

        return result;
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
     *  Gets the description attribute of the LSWWABR13 object
     *
     * @return    The description value
     */
    public String getDescription() {
        return "Verify Worldwide Course Entity";
    }


    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.23 $";
    }
    /**
    *  Get the Meta Flag description for the specified attribute and flag value
    *
    *@param  entityType  String specifying the entity type to find
    *@param  attrCode    String attribute code to get value for
    *@param  flagValue   String flag code to get m_hDisplay value for
    *@return             The attributeMetaFlagDescription value
    *@returns            String flag m_hDisplay text
    */
    public String getAttributeMetaFlagDescription(String entityType, String attrCode, String flagValue) {
		EntityGroup entGroup = null;
		String desc = null;
		EANMetaAttribute mAttr = null;
		boolean finish = false;
		
        if (flagValue == null) {
            logError("null FlagCode supplied! Returning Error");
            //return null;
            finish = true;
        }
        
		if (!finish) {
	        entGroup = m_elist.getEntityGroup(entityType);
	
	        if (entGroup == null) {
	            logError("Did not find EntityGroup: "+entityType+" in entity list to extract MetaFlagDescription");
	            //return null;
	            finish = true;
	        }
	        
			if (!finish) {
				mAttr = entGroup.getMetaAttribute(attrCode);
		        if (mAttr != null && mAttr instanceof EANMetaFlagAttribute) {
		            MetaFlag mf = ((EANMetaFlagAttribute) mAttr).getMetaFlag(flagValue);
		            desc = mf.getLongDescription();
		        }
		        else {
		            desc = attrCode + " not found or not a flag";
		        }
		    //EntityItem entItem = entGroup.getEntityItem(_strEtype);
		    //EANAttribute eattr = entItem.getAttribute(_strEtype);
		    //if (eattr instanceof EANFlagAttribute) {
		      //EANFlagAttribute ema = (EANFlagAttribute) eattr;
		      //desc = ema.getFlagLongDescription(_strFlagValue);
		    //}
			}
		}
        //return desc;
		return (finish ? null : desc);
    }
    /**
    *  Description of the Method
    *
    *@param  _strEntityType     Description of the Parameter
    *@param  _iEntityID         Description of the Parameter
    *@param  _strParentType     Description of the Parameter
    *@param  _strRelator        Description of the Parameter
    *@param  _strAttributeCode  Description of the Parameter
    *@param  _strFlagValue      Description of the Parameter
    *@return                    Description of the Return Value
    */
    protected boolean checkForAvailableParents(String _strEntityType, int _iEntityID, String _strParentType, String _strRelator, String _strAttributeCode, String _strFlagValue) {

        boolean bReturn = true;
        Iterator ids = getParentEntityIds(_strEntityType, _iEntityID, _strParentType, _strRelator).iterator();
        while (ids.hasNext()) {
            int iParentID = ((Integer) ids.next()).intValue();
            String flagVal = getAttributeFlagEnabledValue(_strParentType, iParentID, _strAttributeCode, "");
            if (!_strFlagValue.equals(flagVal)) {
				String flagDesc = "<em>** Not Populated **</em>";
                bReturn = false;
                
                if (flagVal != null) {
                    flagDesc = getAttributeMetaFlagDescription(_strParentType, _strAttributeCode, flagVal);
                }

        //ERR_IAB2001E="The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.";
                printErrorMessage(
                    ERR_IAB2001E,
                    new String[]{
                        getEntityDescription(getEntityType()),
                        "made available",
                        getABREntityDesc(_strParentType, iParentID),
                        flagDesc
                });
            } else {
        //MSG_IAB2015I="%1 has a current Life Cycle status value of %2.";
                printTestedMessage(
                    MSG_IAB2015I,
                    new String[]{
                        getABREntityDesc(_strParentType, iParentID),
                        getAttributeMetaFlagDescription(_strParentType, _strAttributeCode, _strFlagValue)
                });
            }
        }
        return bReturn;
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
