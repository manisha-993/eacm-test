//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * <pre>Verify Curriculum Entity can be retired.
 *
 *LSCURABR6 (Implements CR0914011132)
 *The report should show:
 *ABR name
 *Date Generated
 *Who last changed the entity ==> userid (openid)
 *Description: Verify Curriculum Entity can be retired.
 *This ABR is scheduled to run after the transition of the Curriculum Life Cycle Status to "Retired".
 *Rules:
 *Any linked In-Country Courses must have a Life Cycle Status value of  "Retired"
 *Any linked Worldwide Course must have a Life Cycle Status value of "Retired"
 *Any linked Sub-Curriculum must have a Life Cycle Status value or "Retired"
 *PASS:
 *set the Expiration Date to today's date and print:
 *IAB2010I: Curriculum Code: (Curriculum ID and Title) is now Retired.
 *FAIL:
 *set the Curriculum Life Cycle status to "Failed" and print the appropriate message:
 *Case 1:  IAB2001E: The Curriculum cannot be retired because "(Course Code : Title)" has a current Life Cycle status value of "xxx".
 *Case 2:  IAB2001E: The Curriculum cannot be retired because "(Sub-Curriculum Code : Title)" has a current Life Cycle status value of "xxx".
 *
 *
 * Change History (most recent at top)
 * $Log: LSCURABR6.java,v $
 * Revision 1.20  2014/01/13 13:50:50  wendy
 * migration to V17
 *
 * Revision 1.19  2006/06/26 12:42:35  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.18  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.17  2006/03/06 17:13:48  couto
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
 * Revision 1.6  2003/10/30 20:32:25  cstolpe
 * Changed setDGTitle
 *
 * Revision 1.5  2003/10/28 20:58:57  cstolpe
 * setRptClass in all and Linking in ABR 1
 *
 * Revision 1.4  2003/10/23 19:23:58  cstolpe
 * Check lifecycle and do VELOCK
 *
 * Revision 1.3  2003/10/20 19:26:54  cstolpe
 * latest
 *
 * Revision 1.2  2003/10/15 19:35:26  cstolpe
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
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
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
public class LSCURABR6 extends PokBaseABR {
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
			int iResults = PASS;

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

            //if (!checkFlagValues("LSCURLIFECYCLE", XR_LSCURLIFECYCLE_Available))    {
            //    println("<br />LSCURABR6 not processed.  Current Lifecycle Status is not appropriate for this action.<br />");
            //    iResults = FAIL;
            //}
            //else {
            // check that all related entities are retired
            m_bOutputAsList = true;
            println(NEW_LINE + "<b>Related entity check:</b>");
            println("<ul>");
            if (!checkRetiredEntities()) {
                iResults = FAIL;
            }
            else {
                println("<li>All related entities are retired.</li>");
            }
            println("</ul>");
            m_bOutputAsList = false;

            // update lifecycle status field based on checks
            if (iResults == FAIL) {
                triggerWorkFlow("WFCURFL");
            }
            else {
                setText("LSCUREXPDATE", getEffOn().substring(0,10));
                triggerWorkFlow("WFCURRT");
                //MSG_IAB2010I = "%1# is now Retired.";
                printMessage(MSG_IAB2010I, new String[]{getABREntityDesc(getEntityType(), getEntityID())});
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
     * stuff needs to be logged.. here down the road
     *
     * @return                Description of the Return Value
     * @exception  Exception  Description of the Exception
     */
    private boolean checkRetiredEntities() throws Exception {
        boolean bResult = true;

        if (!anyRetiredParents(getRootEntityType(), getRootEntityID(), "LSCC", "LSCCCUR", "LSCCLIFECYCLE", XR_LSCCLIFECYCLE_Retired)) {
            bResult = false;
        }

        // any linked WW courses must have a life cycle of retired
        if (!anyRetiredChildren(getRootEntityType(), getRootEntityID(), "LSWW", "LSCURWW",
                "LSWWLIFECYCLE", XR_LSWWLIFECYCLE_Retired)) {
            bResult = false;
        }

        // any linked SubCurriculum must have a life cycle of retired
        if (!anyRetiredChildren(getRootEntityType(), getRootEntityID(), "LSSC", "LSCURSC", "LSSCLIFECYCLE", XR_LSSCLIFECYCLE_Retired)) {
            bResult = false;
        }

        return bResult;
    }


    /**
     * copy one text attribute value from an entity to another entity
     *
     * @param _sAttributeCode
     * @param _sAttributeValue
     */
    public void setText(String _sAttributeCode, String _sAttributeValue) {
        try {
            logMessage("****before update: " + _sAttributeCode + _sAttributeValue);
            if( _sAttributeValue != null ) {
                EntityItem eiParm = new EntityItem(null,m_prof, getEntityType(), getEntityID());
                ReturnEntityKey rek = new ReturnEntityKey(eiParm.getEntityType(), eiParm.getEntityID(), true);
                DatePackage dbNow = m_db.getDates();
                String strNow = dbNow.getNow();
                String strForever = dbNow.getForever();
                ControlBlock cbOn = new ControlBlock(strNow,strForever,strNow,strForever, m_prof.getOPWGID(), m_prof.getTranID());
                Text sf = new Text (m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(), _sAttributeCode, _sAttributeValue, 1, cbOn);
                Vector vctAtts = new Vector();
                Vector vctReturnsEntityKeys = new Vector();
                if(sf != null) {
                    try{
                        vctAtts.addElement(sf);
                        rek.m_vctAttributes = vctAtts;
                        vctReturnsEntityKeys.addElement(rek);
                        m_db.update(m_prof, vctReturnsEntityKeys, false, false);
                        m_db.commit();
                    }
                    catch(Exception x){
                        logMessage(this + " trouble updating text value " + x);
                    }
                    finally {
                        m_db.freeStatement();
                        m_db.isPending("finally after update in Text value");
                    }
                }
            }
        }
        catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
            logMessage("set Text Value: " + e.getMessage());
        }
        catch (Exception e) {
            logMessage("set Text Value:" + e.getMessage());
        }
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
     * Get ABR's description
     *
     * @return    java.lang.String
     */
    public String getDescription() {
        return "Verify Curriculum Entity can be retired.";
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
    protected boolean anyRetiredParents(String _strEntityType, int _iEntityID, String _strParentType, String _strRelator, String _strAttributeCode, String _strFlagValue) {
        boolean bReturn = true;
        Iterator ids = getParentEntityIds(_strEntityType, _iEntityID, _strParentType, _strRelator).iterator();
        while (ids.hasNext()) {
            int iParentID = ((Integer) ids.next()).intValue();
            String flagVal = getAttributeFlagEnabledValue(_strParentType, iParentID, _strAttributeCode, "");
            log("anyRetiredParents2:flagVal=" + flagVal);
            if (!"".equals(flagVal) && !_strFlagValue.equals(flagVal)) {
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
                        "retired",
                        getABREntityDesc(_strParentType, iParentID),
                        flagDesc
                });
            } else {
        //MSG_IAB2015I="%1 has a current Life Cycle status value of %2.";
                String strMetaDesc = getAttributeMetaFlagDescription(_strParentType, _strAttributeCode, _strFlagValue);
                printTestedMessage(
                    MSG_IAB2015I,
                    new String[]{
                        getABREntityDesc(_strParentType, iParentID),
                        ((strMetaDesc == null) ? "Null value returned for "+_strParentType+":"+_strAttributeCode+":"+_strFlagValue : strMetaDesc)
                });
            }
        }
        return bReturn;
    }


  /*
   *  This checks to see if any Children of a specific entitytype is retired.  Error's may need to be handled by caller
   */
    /**
    *  Description of the Method
    *
    *@param  _strEntityType     Description of the Parameter
    *@param  _iEntityID         Description of the Parameter
    *@param  _strChildType      Description of the Parameter
    *@param  _strRelator        Description of the Parameter
    *@param  _strAttributeCode  Description of the Parameter
    *@param  _strFlagValue      Description of the Parameter
    *@return                    Description of the Return Value
    */
    protected boolean anyRetiredChildren(String _strEntityType, int _iEntityID, String _strChildType, String _strRelator, String _strAttributeCode, String _strFlagValue) {
        boolean bReturn = true;
        Iterator ids = getChildrenEntityIds(_strEntityType, _iEntityID, _strChildType, _strRelator).iterator();
        while (ids.hasNext()) {
            int iChildID = ((Integer) ids.next()).intValue();
            String flagVal = getAttributeFlagEnabledValue(_strChildType, iChildID, _strAttributeCode, "");
            if (!"".equals(flagVal) && !_strFlagValue.equals(flagVal)) {
				String flagDesc = "<em>** Not Populated **</em>";
                bReturn = false;
                
                if (flagVal != null) {
                    flagDesc = getAttributeMetaFlagDescription(_strChildType, _strAttributeCode, flagVal);
                }

        //ERR_IAB2001E="The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.";
                printErrorMessage(
                    ERR_IAB2001E,
                    new String[]{
                        getEntityDescription(getEntityType()),
                        "retired",
                        getABREntityDesc(_strChildType, iChildID),
                        flagDesc
                });
            } else {
        //MSG_IAB2015I="%1 has a current Life Cycle status value of %2.";
                printTestedMessage(
                    MSG_IAB2015I,
                    new String[]{
                        getABREntityDesc(_strChildType, iChildID),
                        getAttributeMetaFlagDescription(_strChildType, _strAttributeCode, _strFlagValue)
                });
            }
        }
        return bReturn;
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
}
