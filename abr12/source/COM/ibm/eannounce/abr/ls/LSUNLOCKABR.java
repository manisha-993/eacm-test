//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * <pre>Unlocks the appropriate VE
 *
 * Change History (most recent at top)
 * $Log: LSUNLOCKABR.java,v $
 * Revision 1.8  2014/01/13 13:50:51  wendy
 * migration to V17
 *
 * Revision 1.7  2006/06/26 12:42:35  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.6  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.5  2006/03/08 12:53:24  couto
 * Changed layout, using EACustom methods.
 * Fixed br tags. Chaged font tags.
 *
 * Revision 1.4  2006/01/26 00:36:18  joan
 * Jtest
 *
 * Revision 1.3  2006/01/22 18:28:29  joan
 * changes for Jtest
 *
 * Revision 1.2  2005/01/26 23:36:59  joan
 * changes for Jtest
 *
 * Revision 1.1  2003/11/12 20:16:49  chris
 * new ABR
 *
 * Revision 1.2  2003/11/06 20:22:16  cstolpe
 * fix message
 *
 * Revision 1.1  2003/11/06 13:10:09  cstolpe
 * Initial Version
 *
 *
 * </pre>
 * @author     Chris Stolpe
 * @created    October 29, 2003
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
public class LSUNLOCKABR extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.";
	
    /**
     *  Description of the Method
     */
    public void execute_run() {
        try {
			EntityGroup eg =  null;
			EntityItem ei = null;
			Object[] mfParms = new String[10];
			MessageFormat mfOut = new MessageFormat("<p class=\"ibm-intro ibm-alternate-three\"><em>{0}</em></p><p><b>Date: </b>{1}<br /><b>User: </b>{2} ({3})<br /><b>Description: </b>{4}</p><!-- {5} -->");
			StringBuffer sb = new StringBuffer();
			String strEntityType = null;
			
            start_ABRBuild(false);
            eg =  new EntityGroup(null, m_db, m_prof, m_abri.getEntityType(), "Navigate");
            ei = new EntityItem(eg, m_prof, m_db, m_abri.getEntityType(), m_abri.getEntityID());
            eg.put(ei.getKey(), ei);

            setDGTitle(getDGTitle(true, ei));


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
            
            sb.append(eg.getLongDescription());
            sb.append(" Code: ");

            setReturnCode(PASS);
            strEntityType = getEntityType();
            if (strEntityType.equals("LSCC")) {
                sb.append(getAttributeValue(ei, "LSCRSID", DEF_NOT_POPULATED_HTML));
                triggerUnLock("EXTLSCCUNLOCK", ei);
            }
            else if (strEntityType.equals("LSWWCC")) {
                sb.append(getAttributeValue(ei, "LSCRSID", DEF_NOT_POPULATED_HTML));
                triggerUnLock("EXTLSWWCCUNLOCK", ei);
            }
            else if (strEntityType.equals("LSWW")) {
                sb.append(getAttributeValue(ei, "LSWWID", DEF_NOT_POPULATED_HTML));
                triggerUnLock("EXTLSWWUNLOCK", ei);
            }
            else if (strEntityType.equals("LSCUR")) {
                sb.append(getAttributeValue(ei, "LSCURID", DEF_NOT_POPULATED_HTML));
                triggerUnLock("EXTLSCURUNLOCK", ei);
            }
            else if (strEntityType.equals("LSSC")) {
                sb.append(getAttributeValue(ei, "LSSCID", DEF_NOT_POPULATED_HTML));
                triggerUnLock("EXTLSSCUNLOCK", ei);
            }
            else if (strEntityType.equals("LSPRG")) {
                sb.append(getAttributeValue(ei, "LSPRGID", DEF_NOT_POPULATED_HTML));
                triggerUnLock("EXTLSPRGUNLOCK", ei);
            }
            else if (strEntityType.equals("LSGRM")) {
                sb.append(getAttributeValue(ei, "LSGRMID", DEF_NOT_POPULATED_HTML));
                triggerUnLock("EXTLSGRMUNLOCK", ei);
            }
            else if (strEntityType.equals("LSSEG")) {
                sb.append(getAttributeValue(ei, "LSSEGID", DEF_NOT_POPULATED_HTML));
                triggerUnLock("EXTLSSEGUNLOCK", ei);
            }
            else {
                println("<p>ERROR: " + strEntityType + " is not supported.</p>");
                setReturnCode(FAIL);
            }
            sb.append("<br />");
            println(sb.toString());
            if (getReturnCode() == PASS) {
                println("<p>The " + eg.getLongDescription() + " is unlocked.</p>");
                sb = new StringBuffer();
                sb.append(getShortClassName(getClass()));
                sb.append(" has Passed.<br />");
                println(sb.toString());
            }
            setDGTitle(getDGTitle(false, ei));
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
            setDGRptClass(getEntityType() + "ABR");
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
    private  String getDGTitle(boolean ignore, EntityItem ei)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        EntityGroup eg =  new EntityGroup(null, m_db, m_prof, ei.getEntityType(), "Navigate");
        Iterator itMeta = eg.getMetaAttribute().values().iterator();
        while (itMeta.hasNext()) {
            EANMetaAttribute ma = (EANMetaAttribute) itMeta.next();
            if (!ignore && ma.getAttributeCode().equals(getShortClassName(getClass()))) { // It's actual value is in proccess or queued
                navName.append((getReturnCode() == PASS) ? "Passed" : "Failed");
            }
            else {
                navName.append(getAttributeValue(ei, ma.getAttributeCode()));
            }
            if (itMeta.hasNext()) {
                navName.append(" ");
            }
        }
        return navName.toString();
    }


    /**
     *  Triggers the specified lock action
     *
     * @param actionName Name of the lock action.
     * @exception  java.sql.SQLException  Description of the Exception
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareException  Description of the Exception
     */
    private void triggerUnLock(String actionName, EntityItem ei)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
		EntityItem[] aItems = null;
		LockActionItem lai = null;
        logMessage("LSUNLOCKABR:Executing " + actionName + " on " + ei.getKey());
        aItems = new EntityItem[] { ei };
        lai = new LockActionItem(null, m_db, m_prof, actionName);
        lai.setEntityItems(aItems);
        m_db.executeAction(m_prof, lai);
    }

    /**
     *  Gets the description attribute of the LSCCABR3 object
     *
     * @return    The description value
     */
    public String getDescription() {
        return "Unlocks the VE associated with the Entity";
    }


    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.8 $";
    }
}

