//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * <pre>Sets Life Cycle Status to Available based on Life Cycle Status, Catalog Editing Status, and Pricing Status.
 *
 * Change History (most recent at top)
 * $Log: LSCCABR5.java,v $
 * Revision 1.13  2014/01/13 13:50:50  wendy
 * migration to V17
 *
 * Revision 1.12  2006/06/26 12:42:34  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.11  2006/03/13 19:05:44  couto
 * Fixed copyright info.
 *
 * Revision 1.10  2006/03/03 17:49:47  couto
 * Changed layout, using EACustom methods.
 * Fixed br tags. Chaged font tags. Fixed multiple return statements.
 *
 * Revision 1.9  2006/01/23 17:25:15  joan
 * Jtest
 *
 * Revision 1.8  2006/01/22 18:28:29  joan
 * changes for Jtest
 *
 * Revision 1.7  2005/01/26 23:23:46  joan
 * changes for Jtest
 *
 * Revision 1.6  2004/06/18 21:43:55  chris
 * Redesign for CR050902204
 *
 *
 * </pre>
 * @author     Chris Stolpe
 * @created    June 17, 2001
 */
package COM.ibm.eannounce.abr.ls;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.LockPDHEntityException;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.LockActionItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.WorkflowActionItem;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.SingleFlag;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class LSCCABR5 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.";
	
    /**
     *  Description of the Method
     */
    public void execute_run() {
        try {
            Object[] mfParms = new String[10];
            MessageFormat mfOut = new MessageFormat("<p class=\"ibm-intro ibm-alternate-three\"><em>{0}</em></p><p><b>Date: </b>{1}<br /><b>User: </b>{2} ({3})<br /><b>Description: </b>{4}</p><!-- {5} -->");
            int iReturnCode = PASS;
            StringBuffer sb = new StringBuffer();
            String strPricing = null;
            //String strLifeCycle = null;
            String strCAEdit = null;
            boolean bDisplayOrd = false;
            boolean bFinal = true;
            Vector vctVPC = null;
            Iterator it = null;
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
            sb.append(getAttributeValue(getEntityType(), getEntityID(), "LSCRSID", DEF_NOT_POPULATED_HTML));
            sb.append("<br />");
            println(sb.toString());

            strPricing = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCCPRICINGSTATUS","");
            //strLifeCycle = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCCLIFECYCLE","");
            strCAEdit = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCCCATEDITSTATUS","");

            println("<b>Volume Price Display Order Check:</b>" + NEW_LINE + "<ul>");
            m_bOutputAsList = true;
            bDisplayOrd = checkVPCDisplayOrder();

            println("</ul>");
            m_bOutputAsList = false;
            vctVPC = getChildrenEntityIds(getEntityType(), getEntityID(), "LSVPC", "LSCCVPC");
            it = vctVPC.iterator();
            while (it.hasNext()) {
                int iChildID = ((Integer) it.next()).intValue();
                String strLSCRSVPCSTATUS = getAttributeFlagEnabledValue("LSVPC", iChildID, "LSCRSVPCSTATUS","");
                //System.err.println("strLSCRSVPCSTATUS="+strLSCRSVPCSTATUS);
                if ("0010".equals(strLSCRSVPCSTATUS)) {
                    bFinal = false;
                }
            }
            if (!bFinal || !bDisplayOrd) {
                triggerWorkFlow("WFLSCCCSREAVA"); // Requires Catalog Editing and Awaiting approvals
                if (!bFinal) {
                    printErrorMessage(
                        ERR_IAB2021E,
                        new String[]{
                            getEntityDescription("LSVPC"),
                            getEntityDescription("LSCC"),
                            getAttributeValue(getEntityType(), getEntityID(), "LSCRSID"),
                            getAttributeValue(getEntityType(), getEntityID(), "LSCRSTITLE")});
                }
                iReturnCode = FAIL;
                println(" Catalog Editing Status set to Needs Catalog Editing.");
            }
            if (strPricing.equals("0030") && (strCAEdit.equals("0020") || strCAEdit.equals("0050"))) {
                if (bFinal && bDisplayOrd) {
                    println("Course Lifecycle status set to Available. ");
                    setControlBlock();
                    setFlagValue("LSCRSTAAUD", getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCRSAUDIENCE","0010"));
                    triggerWorkFlow("WFLSCCAVCL");
                    triggerLock("EXTLSCCLOCK");
                    iReturnCode = PASS;
                }
            }
            else {
                if (!"0030".equals(strPricing)) {
                    printMessage(
                        "Cannot make course Available. Still waiting for Pricing Approval. <!-- LSCCPRICINGSTATUS=%1# --><br />",
                        new String[]{
                            getAttributeMetaFlagDescription(getRootEntityType(),"LSCCPRICINGSTATUS", strPricing)
                        });
                }
            }

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
            println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
            println("" + exc);
            setReturnCode(FAIL);
        }
        finally {
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("LSCCABR");
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
     * check the volume price display order is unique
     *
     */
    private boolean checkVPCDisplayOrder() {
        Iterator ids = getChildrenEntityIds(getEntityType(), getEntityID(), "LSVPC", "LSCCVPC").iterator();
        boolean hasVPC = ids.hasNext();
        Set displayOrder = new HashSet();
        boolean ret = true;
        while (ids.hasNext()) {
            Integer id = (Integer) ids.next();
            String dispOrd = getAttributeValue("LSVPC", id.intValue(), "LSCRSVPCDISPORD");
            println("<!-- dispOrd:" + dispOrd + " -->");
            if (displayOrder.contains(dispOrd)) {
                printErrorMessage(
                        "%1# Entities do not have unique %2#",
                        new String[]{
                        getEntityDescription("LSVPC"),
                        getAttributeDescription("LSVPC","LSCRSVPCDISPORD")});
                //return false;
                ret = false;
                break;
            }
            else {
                displayOrder.add(dispOrd);
            }
        }
        
        if (ret) {
	        if (hasVPC) {
	            println("<li>All Volume Prices have a unique Display Order.</li>");
	        }
	        else {
	            println("<li>There are no Volume Prices linked to this Course.</li>");
	        }
        }
        return ret;
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
     * Get ABR description
     *
     * @return    java.lang.String
     */
    public String getDescription() {
        return "Sets Lifecycle Status to Available based on Catalog Editing, Pricing Status, and the Volume Price checks. Run by the Catalog Editor.";
    }


    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.13 $";
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
     *  Sets the specified Flag Attribute on the Root Entity
     *
     *@param    strAttributeCode The Flag Attribute Code
     *@param    strAttributeValue The Flag Attribute Value
     */
    private void setFlagValue(String strAttributeCode, String strAttributeValue) {
        logMessage("****** strAttributeValue set to: " + strAttributeValue);

        if(strAttributeValue != null) {
            try {
                EntityItem eiParm = new EntityItem(null,m_prof, getEntityType(), getEntityID());
                ReturnEntityKey rek = new ReturnEntityKey(eiParm.getEntityType(), eiParm.getEntityID(), true);

                SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(), strAttributeCode, strAttributeValue, 1, m_cbOn);
                Vector vctAtts = new Vector();
                Vector vctReturnsEntityKeys = new Vector();

                if(sf != null) {
                    vctAtts.addElement(sf);

                    rek.m_vctAttributes = vctAtts;
                    vctReturnsEntityKeys.addElement(rek);

                    m_db.update(m_prof, vctReturnsEntityKeys, false, false);
                    m_db.commit();
                }
            }
            catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
                logMessage("setFlagValue: " + e.getMessage());
            }
            catch (Exception e) {
                logMessage("setFlagValue: " + e.getMessage());
            }
        }
    }


}

