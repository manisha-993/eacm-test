// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
CHQAUTOGEN_class=COM.ibm.eannounce.abr.sg.CHQAUTOGEN
CHQAUTOGEN_enabled=true
CHQAUTOGEN_idler_class=A
CHQAUTOGEN_keepfile=true
CHQAUTOGEN_read_only=true
CHQAUTOGEN_report_type=DGTYPE01
CHQAUTOGEN_vename=EXRPT3ANN1

 * CR0629043214
 *
 * $Log: CHQAUTOGEN.java,v $
 * Revision 1.6  2014/01/13 13:54:18  wendy
 * migration to V17
 *
 * Revision 1.5  2006/07/16 23:43:23  chris
 * log revision
 *
 * Revision 1.4  2006/02/25 21:27:07  anhtuan
 * Remove redundant stuff.
 *
 * Revision 1.3  2006/02/23 03:43:20  anhtuan
 * AHE compliant.
 *
 * Revision 1.2  2006/02/09 23:50:33  yang
 * change class from sg to sgv30b
 *
 * Revision 1.1  2006/02/09 23:37:48  yang
 * Adding CHQAUTOGEN for version SG3.0b
 *
 * Revision 1.13  2006/01/26 14:36:22  anhtuan
 * AHE copyright.
 *
 * Revision 1.12  2006/01/25 18:18:37  anhtuan
 * Jtest.
 *
 * Revision 1.10  2005/11/01 17:42:14  anhtuan
 * Use AUTOGENRpt.GMLFORMAT instead of AUTOGENRpt.GMLFormat.
 *
 * Revision 1.9  2005/02/09 21:50:03  anhtuan
 * Fix.
 *
 * Revision 1.8  2004/11/15 02:53:20  anhtuan
 * *** empty log message ***
 *
 * Revision 1.7  2004/11/09 20:03:21  anhtuan
 * Turn off checking.
 *
 * Revision 1.6  2004/10/14 23:02:59  anhtuan
 * Uncomment stuff.
 *
 * Revision 1.5  2004/10/14 11:57:41  anhtuan
 * Comment out stuff.
 *
 * Revision 1.4  2004/10/11 16:15:17  anhtuan
 * Call init().
 *
 * Revision 1.3  2004/10/10 22:59:20  anhtuan
 * Put in <pre></pre> tags.
 *
 * Revision 1.2  2004/10/07 18:00:39  anhtuan
 * Check on ABR rule A0002, A0003.
 *
 * Revision 1.1  2004/10/05 18:37:45  anhtuan
 * Initial version. CR0629043214.
 *
 *
 *
 * </pre>
 *
 *@author     Anhtuan Nguyen
 *@created    Oct 5, 2004
 */
package COM.ibm.eannounce.abr.sgv30b;

//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

//import java.util.*;
import java.text.*;

/**********************************************************************************
* CHQAUTOGEN class
*
*
*/
public class CHQAUTOGEN extends PokBaseABR
{
    private StringBuffer rptSb = new StringBuffer();
    private StringBuffer traceSb = new StringBuffer();

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /**
    *  Execute ABR.
    *
    */
    public void execute_run()
    {
        String navName = "";
        String HEADER = "<head>"+
            EACustom.getMetaTags(getDescription()) + NEWLINE +
            EACustom.getCSS() + NEWLINE +
            EACustom.getTitle("{0} {1}") + NEWLINE +
            "</head>" + NEWLINE + "<body id=\"ibm-com\">" +
            EACustom.getMastheadDiv() + NEWLINE +
            "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE+
            "<!-- {2} -->" + NEWLINE;
        MessageFormat msgf;
        Object[] args = new String[10];
        boolean initResult;
        EntityGroup m_egParent = null;
        EntityItem m_eiParent = null;
        GeneralAreaList gal;
        AUTOGENRpt autogenRpt;
        log("AUTOGENRpt version is " +  AUTOGENRpt.VERSION);
		log("CHQAUTOGEN version is " +  getABRVersion());
        println(EACustom.getDocTypeHtml()); //Output the doctype and html
        try
        {
            start_ABRBuild();

            traceSb.append("CHQAUTOGEN entered for " + getEntityType() + ":" + getEntityID());
            // debug display list of groups
            traceSb.append(NEWLINE + AUTOGENRpt.outputList(m_elist));
            rptSb.append("<!-- DEBUG: "+traceSb.toString()+" -->" + NEWLINE);

            m_egParent = m_elist.getParentEntityGroup();
            m_eiParent = m_egParent.getEntityItem(0);

            if (m_egParent == null)
            {
                setReturnCode(FAIL);
                logMessage("CHQAUTOGEN: " + getVersion() + ":ERROR:1: m_egParent cannot be established.");
                msgf = new MessageFormat(HEADER);
                args[0] = getShortClassName(getClass());
                args[1] = " Failed";
                args[2] = getABRVersion();
                rptSb.insert(0, msgf.format(args) + NEWLINE);
                rptSb.append("<!-- DEBUG: m_egParent cannot be established. -->" + NEWLINE);
                println(rptSb.toString());
                println(EACustom.getTOUDiv());
                buildReportFooter();
                return;
            }
            if (m_eiParent == null)
            {
                setReturnCode(FAIL);
                logMessage("CHQAUTOGEN: " + getVersion() + ":ERROR:2: m_eiParent cannot be established.");
                msgf = new MessageFormat(HEADER);
                args[0] = getShortClassName(getClass());
                args[1] = " Failed";
                args[2] = getABRVersion();
                rptSb.insert(0, msgf.format(args) + NEWLINE);
                rptSb.append("<!-- DEBUG: m_eiParent cannot be established. -->" + NEWLINE);
                println(rptSb.toString());
                println(EACustom.getTOUDiv());
                buildReportFooter();
                return;
            }

            //Default set to pass
            setReturnCode(PASS);

            //if(!checkA0002(m_eiParent))
            //{
            //      setReturnCode(FAIL);
            //}

            //if(!checkA0003(m_eiParent))
            //{
            //      setReturnCode(FAIL);
            //}

            //NAME is navigate attributes
            navName = getNavigationName();

            gal = m_db.getGeneralAreaList(m_prof);
            autogenRpt = new AUTOGENRpt(m_elist, gal, m_db);

            initResult =  autogenRpt.init(rptSb);

            if(initResult)
            {
                //rptSb.append("<pre>" + NEWLINE);
                autogenRpt.retrieveAnswer(true, rptSb);
                //rptSb.append("</pre>" + NEWLINE + NEWLINE);

                // release memory
                autogenRpt.cleanUp();
            }
        }
        catch(Throwable exc)
        {
            java.io.StringWriter exBuf = new java.io.StringWriter();
            String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            msgf = new MessageFormat(Error_EXCEPTION);
            setReturnCode(FAIL);
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            // Put exception into document
            args[0] = exc.getMessage();
            rptSb.append(msgf.format(args) + NEWLINE);
            msgf = new MessageFormat(Error_STACKTRACE);
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args) + NEWLINE);
            logError("Exception: "+exc.getMessage());
            logError(exBuf.getBuffer().toString());
        }
        finally
        {
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass(""); // fixme get this value, blank for now per Rupal
            // make sure the lock is released
            if(!isReadOnly())
            {
                clearSoftLock();
            }
        }

        //Print everything up to </html>
        //Insert Header into beginning of report
        msgf = new MessageFormat(HEADER);
        args[0] = getShortClassName(getClass());
        args[1] = navName + ((getReturnCode() == PASS) ? " Passed" : " Failed");
        args[2] = getABRVersion();
        rptSb.insert(0, msgf.format(args) + NEWLINE);

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>
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
    public String getDescription()
    {
        String desc =  "AutoGen Report.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return    java.lang.String
    */
    public String getABRVersion()
    {
        return "$Revision: 1.6 $";
    }
}
