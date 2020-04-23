//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * <pre>This ABR copies data from parent WW Course to WW In-Country Course Template via LSWWWWCCTEMP.
 *
 *  This ABR6 is manually run from the java EUI. The OSA and WW Course Creator
 *  roles can run it. ABR6 will copy data from parent WW Course to WW In-Country
 *  Course Template via LSWWWWCCTEMP
 *
 * Change History (most recent at top)
 * $Log: LSWWCCABR6.java,v $
 * Revision 1.9  2014/01/13 13:50:50  wendy
 * migration to V17
 *
 * Revision 1.8  2006/06/26 12:42:35  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.7  2006/03/13 19:05:46  couto
 * Fixed copyright info.
 *
 * Revision 1.6  2006/03/08 12:52:16  couto
 * Changed layout, using EACustom methods.
 *
 * Revision 1.5  2006/01/26 00:36:19  joan
 * Jtest
 *
 * Revision 1.4  2006/01/22 18:28:30  joan
 * changes for Jtest
 *
 * Revision 1.3  2005/01/26 23:37:00  joan
 * changes for Jtest
 *
 * Revision 1.2  2003/12/01 17:54:43  chris
 * Fixes for FB 53162:82DEA0 and 53163:839FA1
 *
 * Revision 1.1  2003/11/12 20:16:25  chris
 * missing 1.0.1 ABR
 *
 * Revision 1.5  2003/11/10 17:44:29  cstolpe
 * Added comments to output
 *
 * Revision 1.4  2003/10/30 20:32:27  cstolpe
 * Changed setDGTitle
 *
 * Revision 1.3  2003/10/28 20:58:59  cstolpe
 * setRptClass in all and Linking in ABR 1
 *
 * Revision 1.2  2003/10/15 19:35:27  cstolpe
 * Latest Updates
 *
 * Revision 1.1  2003/10/09 18:51:57  cstolpe
 * Initial LS drop
 *
 * </pre>
 * @author     Onix Garcia
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
//import java.sql.SQLException;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class LSWWCCABR6 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.";
	
    //Seventeen attributes to use in copy/paste. The first = copy ; second = paste
    final static String ATTNAMES[][] = {
            {"LSCRSABSTRACT",           "LSCRSABSTRACT"},           // X
            {"LSCRSAUDIENCEDESC",       "LSCRSAUDIENCEDESC"},        // X
            {"LSCRSCERTPGMDESC",        "LSCRSCERTPGMDESC"},        // X
            {"LSCRSCOURSEMAT",          "LSCRSCOURSEMAT"},            // X
            {"LSCRSCOURSEOVERVIEW",     "LSCRSCOURSEOVERVIEW"},        // X
            {"LSCRSDELFORMATDESC",      "LSCRSDELFORMATDESC"},        // X
            {"LSCRSIBMTRADEMARKS",      "LSCRSIBMTRADEMARKS"},        // L
            {"LSCRSKEYWORD",            "LSCRSKEYWORD"},            // L
            {"LSCRSOBJECTIVES",         "LSCRSOBJECTIVES"},            // X
            {"LSCRSOTHCOMTMSONWERNAME", "LSCRSOTHCOMTMSONWERNAME"},    // L
            {"LSCRSOTHERCOMTMS",        "LSCRSOTHERCOMTMS"},        // L
            {"LSCRSPREREQDESC",         "LSCRSPREREQDESC"},            // X
            {"LSCRSREMARKS",            "LSCRSREMARKS"},            // X
            {"LSCRSSTUDENTMACHREQTS",   "LSCRSSTUDENTMACHREQTS"},    // X
            {"LSCRSTOPICS",             "LSCRSTOPICS"},                // X
            {"LSWWAVLDATE",             "LSCRSAVLDATE"},            // T
            {"LSWWEXPDATE",             "LSCRSEXPDATE"},            // T
            {"LSWWTITLE",               "LSCRGLOBALREPTITLE"}        // T FB 53163
    };


    /**
     *  Subclass of AbstractTask method that implements this ABR's functionality.
     *
     * @see    COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask
     */
    public void execute_run() {
        try {
			Object[] mfParms = new String[10];
			MessageFormat mfOut = new MessageFormat("<p class=\"ibm-intro ibm-alternate-three\"><em>{0}</em></p><p><b>Date: </b>{1}<br /><b>User: </b>{2} ({3})<br /><b>Description: </b>{4}</p><!-- {5} -->");
			int iReturnCode = PASS;
			int iWW = -1;
			String sWW = "LSWW";
			int iWWCC = -1;
			String sWWCC = null;
			Vector vctParent = new Vector();
			
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
                     
            iWWCC = getRootEntityID();
            sWWCC = getRootEntityType();

            vctParent = getParentEntityIds(sWWCC, iWWCC, sWW, "LSWWWWCCTEMP");
            if (vctParent.size() == 1) {
				EntityGroup eg = null;
				EntityItem ei = null;
				
                iWW = ((Integer) vctParent.firstElement()).intValue();
                eg = m_elist.getParentEntityGroup();
                ei = eg.getEntityItem(0);
                for (int i = 0; i < ATTNAMES.length; i++) {
                    String dataToPaste = getAttributeValue(sWW, iWW, ATTNAMES[i][0], null);
                    if (dataToPaste != null && !dataToPaste.equals(" ")) {
						EANMetaAttribute mAttr = null;
						
                        println("<!-- populating " + ATTNAMES[i][1] + " -->");
                        mAttr = eg.getMetaAttribute(ATTNAMES[i][1]);
                        if (mAttr instanceof MetaLongTextAttribute) {
                            EANTextAttribute tAttr = new LongTextAttribute(ei, m_prof, (MetaLongTextAttribute) mAttr);
                            tAttr.put(dataToPaste);
                            ei.putAttribute(tAttr);
                        }
                        else if (mAttr instanceof MetaTextAttribute) {
                            EANTextAttribute tAttr = new TextAttribute(ei, m_prof, (MetaTextAttribute) mAttr);
                            tAttr.put(dataToPaste);
                            ei.putAttribute(tAttr);
                        }
                        else if (mAttr instanceof MetaXMLAttribute) {
                            EANTextAttribute tAttr = new XMLAttribute(ei, m_prof, (MetaXMLAttribute) mAttr);
                            tAttr.put(dataToPaste);
                            ei.putAttribute(tAttr);
                        }
                        //setText(ATTNAMES[i][1], dataToPaste);
                    }
                    else {
                        println("<!-- No value to put in " + ATTNAMES[i][1] + " -->");
                    }
                }
                ei.commit(m_db, null);
            }
            else {
                String ERR_IAB2009E = "ERR_IAB2009E: Information cannot be copied from the Worldwide Course because Worldwide In-Country Course %1 , %2 is not a template.";
                printErrorMessage(
                    ERR_IAB2009E,
                    new String[]{
                        getAttributeValue(sWWCC, iWWCC, "LSCRSID", "Not Specified"),
                        getAttributeValue(sWWCC, iWWCC, "LSCRSTITLE", "Not Specified")
                    });
                iReturnCode = FAIL;
            }
            setDGTitle(getDGTitle(false));
            setReturnCode(iReturnCode); // FB 53162
        } /*catch (UpdatePDHEntityException le) {
            setReturnCode(FAIL);
            println(
                    "<h3><font color=red>UpdatePDH error: " +
                    le.getMessage() +
                    "</font></h3>");
            logError(le.getMessage());
        }*/ catch (Exception exc) {
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
     * copy one text attribute value from an entity to another entity
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
     */

    /**
     *  Triggers the specified workflow
     *
     * @param actionName Name of the workflow action.
     * @exception  java.sql.SQLException  Description of the Exception
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareException  Description of the Exception
     * @exception  COM.ibm.eannounce.objects.WorkflowException  Description of the Exception
    private void triggerWorkFlow(String actionName)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.eannounce.objects.WorkflowException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        EntityGroup eg = m_elist.getParentEntityGroup();
        EntityItem[] aItems = new EntityItem[1];
        aItems[0] = eg.getEntityItem(0);
        WorkflowActionItem wfai = new WorkflowActionItem(null, m_db, m_prof, actionName);
        wfai.setEntityItems(aItems);
        m_db.executeAction(m_prof, wfai);
    }
     */

    /**
     *  Get ABR Description
     *
     * @return    java.lang.String
     */
    public String getDescription() {
        return "This ABR copies data from parent WW Course to WW In-Country Course Template via LSWWWWCCTEMP.";
    }


    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.9 $";
    }
}
