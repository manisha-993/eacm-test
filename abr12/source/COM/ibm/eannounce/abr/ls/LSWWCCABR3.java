//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * <pre>Sets Life Cycle Status to Failed
 *
 *
 * Change History (most recent at top)
 * $Log: LSWWCCABR3.java,v $
 * Revision 1.19  2014/01/13 13:50:51  wendy
 * migration to V17
 *
 * Revision 1.18  2006/06/26 12:42:34  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.17  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.16  2006/03/08 12:45:03  couto
 * Changed layout, using EACustom methods.
 * Fixed br tags. Chaged font tags.
 *
 * Revision 1.15  2006/01/26 00:36:19  joan
 * Jtest
 *
 * Revision 1.14  2006/01/22 18:28:30  joan
 * changes for Jtest
 *
 * Revision 1.13  2005/01/26 23:37:00  joan
 * changes for Jtest
 *
 * Revision 1.12  2003/11/12 20:35:28  chris
 * put Revision back
 *
 * Revision 1.11  2003/11/06 22:01:18  bala
 * EC drop
 *
 * Revision 1.3  2003/10/30 20:32:27  cstolpe
 * Changed setDGTitle
 *
 * Revision 1.2  2003/10/28 20:58:58  cstolpe
 * setRptClass in all and Linking in ABR 1
 *
 * Revision 1.1  2003/10/09 18:51:57  cstolpe
 * Initial LS drop
 *
 * </pre>
 * @author     Chris Stolpe
 * @created    October 8, 2001
 */
package COM.ibm.eannounce.abr.ls;

///import COM.ibm.opicmpdh.transactions.*;
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
public class LSWWCCABR3 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.";
	
    /**
     *  Description of the Method
     */
    public void execute_run() {
        try {
			Object[] mfParms = new String[10];
			MessageFormat mfOut = new MessageFormat("<p class=\"ibm-intro ibm-alternate-three\"><em>{0}</em></p><p><b>Date: </b>{1}<br /><b>User: </b>{2} ({3})<br /><b>Description: </b>{4}</p><!-- {5} -->");
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
            sb.append(getAttributeValue(getEntityType(), getEntityID(), "LSCRSID", DEF_NOT_POPULATED_HTML));
            sb.append("<br />");
            println(sb.toString());

            println("<p>The course was set to Failed because Catalog editing or Pricing was denied.</p>");
            triggerWorkFlow("WFLSWWCCFL");

            sb = new StringBuffer();
            sb.append(getShortClassName(getClass()));
            sb.append(" has Passed.<br />");
            println(sb.toString());

            setReturnCode(PASS);
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
     *  Gets the description attribute of the LSWWCCABR3 object
     *
     * @return    The description value
     */
    public String getDescription() {
        return "Sets Life Cycle Status to Failed";
    }


    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.19 $";
    }
}
