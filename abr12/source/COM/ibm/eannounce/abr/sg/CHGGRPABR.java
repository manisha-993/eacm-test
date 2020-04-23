// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.text.*;
/**********************************************************************************
* CR0420044342 and CR042004453
*
CHGGRPABR_class=COM.ibm.eannounce.abr.sg.CHGGRPABR
CHGGRPABR_enabled=true
CHGGRPABR_idler_class=A
CHGGRPABR_keepfile=true
CHGGRPABR_read_only=true
CHGGRPABR_report_type=DGTYPE01
CHGGRPABR_vename=EXCHGGRP
*@author     Wendy Stimpson
*@created    Sept 17, 2004
*/
// CHGGRPABR.java,v
// Revision 1.9  2006/02/22 13:10:19  wendy
// Removed font tag, it is not allowed inAHE
//
// Revision 1.8  2006/02/21 21:17:19  wendy
// use AHE compliant title
//
// Revision 1.7  2006/02/21 20:20:30  wendy
// Pass string to meta description tag
//
// Revision 1.6  2006/01/25 20:13:27  wendy
// AHE copyright
//
// Revision 1.5  2006/01/24 16:59:16  yang
// Jtest Changes
//
// Revision 1.4  2005/11/14 12:44:38  wendy
// New AHE format
//
// Revision 1.3  2005/06/15 12:48:45  wendy
// Jtest chgs
//
// Revision 1.2  2004/11/12 16:26:20  wendy
// setDGRptClass to "CHGRPT"
//
// Revision 1.1  2004/09/30 15:25:47  wendy
// Init for OIM3.0a CR0420044342 and CR042004453
//
public class CHGGRPABR extends PokBaseABR
{

    private StringBuffer rptSb = new StringBuffer();
    private StringBuffer traceSb = new StringBuffer();

    /**
     *  Execute ABR.
     *
     */
    public void execute_run()
    {
        String HEADER = "<head>"+
            EACustom.getMetaTags(getDescription())+EACustom.NEWLINE+
            EACustom.getCSS()+EACustom.NEWLINE+
            EACustom.getTitle("{0} {1}")+EACustom.NEWLINE+
            "</head>"+EACustom.NEWLINE+"<body id=\"ibm-com\">"+
            EACustom.getMastheadDiv()+EACustom.NEWLINE+
            "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>"+EACustom.NEWLINE+
            "<!-- {2} -->"+EACustom.NEWLINE;

        MessageFormat msgf = null;
        Object[] args = new String[10];

        String navName = "";
        println(EACustom.getDocTypeHtml()); // Output the doctype and html

        try {
            CHGGRPRptGen chgGrp = null;

            start_ABRBuild();

            traceSb.append("CHGGRPABR entered for "+getEntityType()+":"+getEntityID());
            // debug display list of groups
            traceSb.append(EACustom.NEWLINE+CHGGRPRptGen.outputList(m_elist));
            rptSb.append("<!-- DEBUG: "+traceSb.toString()+" -->"+EACustom.NEWLINE);

            // NAME is navigate attributes
            navName = getNavigationName();

            rptSb.append("<h2>Change Group Report: "+
                getAttributeValue(m_elist.getParentEntityGroup().getEntityItem(0), "CHGGRPNAME","CHGGRPNAME")
                +"</h2>"+EACustom.NEWLINE);

            chgGrp = new CHGGRPRptGen(m_db, m_prof);
            //heading info
            rptSb.append(chgGrp.getHeaderAndDTS(m_elist)+EACustom.NEWLINE);
            // get section 1 MTM
            // broken up to keep session active for browser report, order is important
            rptSb.append(chgGrp.getMachType()+EACustom.NEWLINE);
            rptSb.append(chgGrp.getModelAndFeature()+EACustom.NEWLINE);
            rptSb.append(chgGrp.getProdStruct()+EACustom.NEWLINE);
            rptSb.append(chgGrp.getOtherStructure()+EACustom.NEWLINE);
            rptSb.append(chgGrp.getMTM()+EACustom.NEWLINE);
            // get section 2 Model Conv
            rptSb.append(chgGrp.getModelConversion()+EACustom.NEWLINE);
            // get section 3 Feature Trans
            rptSb.append(chgGrp.getFeatureTrans()+EACustom.NEWLINE);

            // release memory
            chgGrp.dereference();

            setReturnCode(PASS);
        }
        catch(Throwable exc)
        {
            String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            java.io.StringWriter exBuf = new java.io.StringWriter();
            setReturnCode(FAIL);
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            // Put exception into document
            msgf = new MessageFormat(Error_EXCEPTION);
            args[0] = exc.getMessage();
            rptSb.append(msgf.format(args)+EACustom.NEWLINE);
            msgf = new MessageFormat(Error_STACKTRACE);
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args)+EACustom.NEWLINE);
            logError("Exception: "+exc.getMessage());
            logError(exBuf.getBuffer().toString());
        }
        finally
        {
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("CHGRPT");
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }

        // Print everything up to </html>
        // Insert Header into beginning of report
        msgf = new MessageFormat(HEADER);
        args[0] = getShortClassName(getClass());
        args[1] = navName+((getReturnCode() == PASS) ? " Passed" : " Failed");
        args[2] = getABRVersion();
        rptSb.insert(0, msgf.format(args)+EACustom.NEWLINE);

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        println(EACustom.getTOUDiv());
        buildReportFooter();    // Print </html>
    }

    /**********************************************************************************
    *  Get Name based on navigation attributes
    *
    *@return    java.lang.String
    */
    private String getNavigationName() throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        EntityItem theItem = m_elist.getParentEntityGroup().getEntityItem(0);

        // NAME is navigate attributes
        EntityGroup eg =  new EntityGroup(null, m_db, m_prof, getRootEntityType(), "Navigate");
        EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            navName.append(getAttributeValue(theItem, ma.getAttributeCode(),ma.getAttributeCode()));
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
        String desc =  "Change Group Report.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return    java.lang.String
    */
    public String getABRVersion() {
        return "1.9";
    }

}
