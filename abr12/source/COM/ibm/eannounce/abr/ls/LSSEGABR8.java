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
 * Verify Segment Entity can be retired.
 * LS ABR8 to validate LSSEG (Segment) retirement
 *
 * This ABR is entered after the LifeCycleStatus is set to 'Retired'.
 * If this ABR fails, LifeCycleStatus is set to 'Failed'.  If it passes the
 * Expiration date is set to today's date.
 *
 *
 * Change History (most recent at top)
 * $Log: LSSEGABR8.java,v $
 * Revision 1.22  2014/01/13 13:50:51  wendy
 * migration to V17
 *
 * Revision 1.21  2006/06/26 12:42:34  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.20  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.19  2006/03/07 14:28:12  couto
 * Changed layout, using EACustom methods.
 * Fixed br tags. Chaged font tags.
 *
 * Revision 1.18  2006/01/26 00:36:18  joan
 * Jtest
 *
 * Revision 1.17  2005/10/06 13:11:17  wendy
 * Conform to new jtest config
 *
 * Revision 1.16  2005/01/26 23:36:59  joan
 * changes for Jtest
 *
 * Revision 1.15  2003/12/02 20:19:46  chris
 * Spec changes related to feedbacks 53243:653AE1 and 53251:75170B
 *
 * Revision 1.14  2003/11/20 16:17:06  chris
 * Fix for feedback 53090:54A0D7
 *
 * Revision 1.13  2003/11/12 20:35:28  chris
 * put Revision back
 *
 * Revision 1.12  2003/11/12 20:23:36  chris
 * Fix for FB 52969:688DF0
 *
 * Revision 1.11  2003/11/06 22:01:17  bala
 * EC drop
 *
 * Revision 1.5  2003/10/30 20:32:26  cstolpe
 * Changed setDGTitle
 *
 * Revision 1.4  2003/10/28 20:58:58  cstolpe
 * setRptClass in all and Linking in ABR 1
 *
 * Revision 1.3  2003/10/23 19:23:59  cstolpe
 * Check lifecycle and do VELOCK
 *
 * Revision 1.2  2003/10/15 19:35:26  cstolpe
 * Latest Updates
 *
 * Revision 1.1  2003/10/09 18:51:56  cstolpe
 * Initial LS drop
 *
 * </pre>
 * @author     Wendy Stimpson
 * @created    October 8, 2001
 */
package COM.ibm.eannounce.abr.ls;

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
public class LSSEGABR8 extends PokBaseABR {
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

            log("setToday:" + getValOn());
            setToday(getValOn());
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
            sb.append(getAttributeValue(getEntityType(), getEntityID(), "LSSEGID", DEF_NOT_POPULATED_HTML));
            sb.append("<br />");
            println(sb.toString());

            //if (!checkFlagValues("LSSEGLIFECYCLE", XR_LSSEGLIFECYCLE_Available))  {
            //  println("<br />LSSEGABR8 not processed.  Current Lifecycle Status is not appropriate for this action.<br />");
            //  iResults = FAIL;
            //}
            //else {
            // check that all related entities are retired
            m_bOutputAsList = true;
            println("<b>Related entity check:</b>");
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
            if (iResults == PASS) {
                // update retire date
                //EditActionItem eaiOurItem = new EditActionItem(null, m_db, m_prof, "EDITLSABR");
                // Create an EntityItem
                //EntityItem eiWG = new EntityItem(null, m_prof, "LSSEG", m_prof.getWGID());
                //EntityItem[] aeiWG = {eiWG};
                // Create an entity list using the entityItem
                //EntityList elDGEntity = m_db.getEntityList(m_prof, eaiOurItem, aeiWG);
                // Get the entity Group from the entitylist
                //EntityGroup eg = elDGEntity.getEntityGroup(eiWG.getEntityType());
                //RowSelectableTable m_rst = eg.getEntityGroupTable();
                // Set a text value
                //EntityItem ei = (EntityItem) m_rst.getRow(0);
                //ei.put(ei.getEntityType() + ":" + "LSSEGEXPDATE", getToday().substring(0, 10));
                //m_rst.commit(m_db);
                // This may not work if it is not initially populated hopefully it can be done by the action
                EntityGroup eg = new EntityGroup(null, m_db, m_prof, getEntityType(), "Edit");
                EntityItem ei = new EntityItem(eg, m_prof, m_db, getEntityType(), getEntityID());
                EANMetaAttribute mAttr;
                eg.put(ei.getKey(), ei);
                mAttr = eg.getMetaAttribute("LSSEGEXPDATE");
                if (mAttr instanceof MetaTextAttribute) {
                    EANTextAttribute tAttr = new TextAttribute(ei, m_prof, (MetaTextAttribute) mAttr);
                    tAttr.put(getEffOn().substring(0,10));
                    ei.putAttribute(tAttr);
                    ei.commit(m_db, null);
                }

                printMessage(MSG_IAB2010I,
                    new String[]{getABREntityDesc(getEntityType(), getEntityID())});
                //MSG_IAB2009I = "%1# is now available for use.";
                //printMessage(MSG_IAB2009I, new String[]{getABREntityDesc(getEntityType(), getEntityID())});
                triggerWorkFlow("WFSEGRT");
            }
            else {
                triggerWorkFlow("WFSEGFL");
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
            java.io.StringWriter exBuf = new java.io.StringWriter();
            // Report this error to both the datbase log and the PrintWriter
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
            setDGRptClass("LSSEGABR");
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
     * @return                boolean
     * @exception  Exception  Description of the Exception
     */
    private boolean checkRetiredEntities() throws Exception {
        boolean bResult = true;

        if (!anyRetiredChildren(getRootEntityType(), getRootEntityID(), "LSCC", "SEGCCA", "LSCCLIFECYCLE", XR_LSCCLIFECYCLE_Retired)) {
            bResult = false;
        }
        if (!anyRetiredChildren(getRootEntityType(), getRootEntityID(), "LSWWCC", "SEGWWCCA", "LSWWCCLIFECYCLE", XR_LSWWCCLIFECYCLE_Retired)) {
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
        WorkflowActionItem wfai;
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
        return "Verify Segment Entity can be Retired.";
    }


    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.22 $";
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

        String desc = null;
        if (flagValue == null) {
            logError("null FlagCode supplied! Returning Error");
        }
        else{
            EntityGroup entGroup = m_elist.getEntityGroup(entityType);
            if (entGroup == null) {
                logError("Did not find EntityGroup: "+entityType+" in entity list to extract MetaFlagDescription");
            }else {
                EANMetaAttribute mAttr = entGroup.getMetaAttribute(attrCode);
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
        return desc;
    }
}
