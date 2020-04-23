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
 * Sets Life Cycle Status to Available based on Life Cycle Status, Catalog Editing Status, and Pricing Status.
 *
 * Creation date: (10/10/2001 4:23:21 PM)
 *
 *
 * Change History (most recent at top)
 * $Log: LSWWCCABR2.java,v $
 * Revision 1.28  2014/01/13 13:50:51  wendy
 * migration to V17
 *
 * Revision 1.27  2008/01/30 19:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.26  2006/06/26 12:42:34  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.25  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.24  2006/03/08 12:43:51  couto
 * Changed layout, using EACustom methods.
 * Fixed br tags. Chaged font tags. Fixed multiple return statements.
 *
 * Revision 1.23  2006/01/26 00:36:19  joan
 * Jtest
 *
 * Revision 1.22  2006/01/22 18:28:30  joan
 * changes for Jtest
 *
 * Revision 1.21  2005/01/26 23:37:00  joan
 * changes for Jtest
 *
 * Revision 1.20  2004/07/02 13:19:31  chris
 * Fix TIR USRO-R-FPAO-62HH2X
 *
 * Revision 1.19  2004/06/18 21:43:55  chris
 * Redesign for CR050902204
 *
 * Revision 1.18  2004/05/13 19:04:18  chris
 * CR Demo Changes
 *
 * Revision 1.17  2004/03/09 15:58:01  chris
 * Changes for CR0701025842, CR1218022919, CR050902204
 *
 * Revision 1.16  2003/12/19 16:35:33  chris
 * Change Action name for 53265:4F348C
 *
 * Revision 1.15  2003/12/04 17:04:59  chris
 * Set LSCRSTAAUD when status goes Available
 *
 * Revision 1.14  2003/12/03 20:06:23  chris
 * Fix for FB 53023:09F4DF
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
 * Revision 1.5  2003/10/30 20:32:27  cstolpe
 * Changed setDGTitle
 *
 * Revision 1.4  2003/10/28 20:58:58  cstolpe
 * setRptClass in all and Linking in ABR 1
 *
 * Revision 1.3  2003/10/23 19:24:00  cstolpe
 * Check lifecycle and do VELOCK
 *
 * Revision 1.2  2003/10/15 19:35:26  cstolpe
 * Latest Updates
 *
 * Revision 1.1  2003/10/09 18:51:57  cstolpe
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
public class LSWWCCABR2 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.";
	
    /**
     * Execute LS ABR.
     *
     */
    public void execute_run() {
        try {
			Object[] mfParms = new String[10];
			MessageFormat mfOut = new MessageFormat("<p class=\"ibm-intro ibm-alternate-three\"><em>{0}</em></p><p><b>Date: </b>{1}<br /><b>User: </b>{2} ({3})<br /><b>Description: </b>{4}</p><!-- {5} -->");
			int iRetCode = PASS;

			StringBuffer sb = new StringBuffer();
			String strPricing = null;
			//String strLifeCycle = null;
			String strCAEdit = null;
			
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

            strPricing = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSWWCCPRICINGSTATUS","");
            //strLifeCycle = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSWWCCLIFECYCLE","");
            strCAEdit = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSWWCCCATEDITSTATUS","");
            if ("0030".equals(strPricing) && ("0010".equals(strCAEdit) || "0020".equals(strCAEdit) || "0050".equals(strCAEdit))) {
                println("Course Lifecycle status set to Available. ");
                setControlBlock();
                setFlagValue("LSCRSTAAUD", getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCRSAUDIENCE","0010"));
                triggerWorkFlow("WFLSWWCCAVCL");
                triggerLock("EXTLSWWCCLOCK");
            }
            else {
                if (!("0020".equals(strCAEdit) || "0050".equals(strCAEdit))) {
                    printMessage(
                        "Catalog Editing approval required before Course Lifecycle status is set to Available <!-- LSCCCATEDITSTATUS=%1# --><br />",
                        new String[]{
                            getAttributeMetaFlagDescription(getRootEntityType(),"LSWWCCCATEDITSTATUS", strCAEdit)
                        });
                }
            }

            sb = new StringBuffer();
            sb.append(getShortClassName(getClass()));
            sb.append(" has ");
            sb.append((iRetCode == PASS) ? "Passed" : "Failed");
            sb.append(".<br />");
            println(sb.toString());

            setReturnCode(iRetCode);
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
            
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            println("<pre>");
            println(exBuf.getBuffer().toString());
            println("</pre>");
            setReturnCode(FAIL);
        }
        finally {
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("LSWWCCABR");
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
        return "Sets Lifecycle Status to Available based on Lifecycle Status, Catalog Editing and Pricing Status";
    }


    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.28 $";
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
		boolean finish = false;
		EANMetaAttribute mAttr = null;
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
