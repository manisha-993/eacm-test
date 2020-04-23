//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.ls;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.text.*;

/**
 * CR1008045141
 *   It finds Available Classroom In-Country courses for a Country
 *   BOTH LSCC and LSWWCC!  LSWWCC in one group, LSCC are in another group.
 *
 * $Log: LSCTABR2.java,v $
 * Revision 1.14  2014/01/13 13:50:52  wendy
 * migration to V17
 *
 * Revision 1.13  2006/06/26 12:42:35  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.12  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.11  2006/03/07 16:58:54  couto
 * Added summary to table tags.
 *
 * Revision 1.10  2006/02/22 13:10:18  wendy
 * Removed font tag, it is not allowed inAHE
 *
 * Revision 1.9  2006/02/21 21:17:05  wendy
 * use AHE compliant title
 *
 * Revision 1.8  2006/02/21 20:20:57  wendy
 * Pass param to meta description tag
 *
 * Revision 1.7  2006/01/22 18:28:29  joan
 * changes for Jtest
 *
 * Revision 1.6  2005/11/14 12:44:38  wendy
 * New AHE format
 *
 * Revision 1.5  2005/04/20 02:15:27  wendy
 * Correct get nav name
 *
 * Revision 1.4  2005/04/20 01:45:15  wendy
 * TIR 6BK52V change subject line
 *
 * Revision 1.3  2005/04/15 14:40:58  wendy
 * remove extra newlines
 *
 * Revision 1.2  2005/04/14 19:18:18  wendy
 * Added version to output
 *
 * Revision 1.1  2005/04/14 18:53:31  wendy
 * Init
 *
 *@author     Wendy Stimpson
 *@created    April 14, 2005
 */
public class LSCTABR2 extends PokBaseABR
{
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
	
    private static final String[] ATTR_CODES = {
            "LSCRSID",                  //$NON-NLS-1$
            "LSCRGLOBALREPTITLE",       //$NON-NLS-1$
            "LSCRSDURATION",            //$NON-NLS-1$
            "LSCRSDURATIONUNITS",       //$NON-NLS-1$
            "LSCRSINTPRICE",            //$NON-NLS-1$
            "LSCRSEXTPRICE",            //$NON-NLS-1$
            "LSCRSPRIVPRICE",           //$NON-NLS-1$
            "LSCRSPRIVSTUDADDPRICE",    //$NON-NLS-1$
            "LSCRSEXTNEWPRICE",         //$NON-NLS-1$
            "LSCRSEXTNPRICEEFFDATE",    //$NON-NLS-1$
            "LSCRSPRIVNEWPRICE",        //$NON-NLS-1$
            "LSCRSPRIVSTUDNEWADDPRICE", //$NON-NLS-1$
            "LSCRSPRIVNPRICEEFFDATE",   //$NON-NLS-1$
            "LSCRSEXPDATE"              //$NON-NLS-1$
        };

    /**
     *  Execute ABR.
     */
    public void execute_run()
    {
        String reportName="";
        String navName="";
        try {
            EntityList list;
            EntityItem theEntityItem;
            String pdhctLSCTID;
            String extractName= "EXABR1CTWWCCF1";
            String DefNotPopulated = EACustom.getDefNotPopulated();
            boolean wroteTbl=false;
            int lineCnt=0;
            EntityGroup theGrp;

            start_ABRBuild(false);
            println(EACustom.getDocTypeHtml()); // Output the doctype and html

            println("<head>"+EACustom.NEWLINE+
	            EACustom.getMetaTags(getDescription())+EACustom.NEWLINE+
                EACustom.getCSS()+EACustom.NEWLINE+
            	EACustom.getTitle(getDescription())+EACustom.NEWLINE+
                "</head>"+EACustom.NEWLINE+
                "<body id=\"ibm-com\">");

            println(EACustom.getMastheadDiv());
            println("<!-- ABR version="+getABRVersion()+" -->");

            // get LSWWCC first
            list = m_db.getEntityList(m_prof,
                new ExtractActionItem(null, m_db, m_prof, extractName),
                new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

            reportName= getMetaAttributeDescription(list.getParentEntityGroup(),m_abri.getABRCode());

            // get the LSCT entity
            theEntityItem = list.getParentEntityGroup().getEntityItem(0);
            navName=getNavigationName(theEntityItem);

            pdhctLSCTID = PokUtils.getAttributeValue(theEntityItem, "LSCTID", "",
                DefNotPopulated,true)+" "+
                PokUtils.getAttributeValue(theEntityItem, "LSCTDESC", "", "",true);

            println("<p class=\"ibm-intro ibm-alternate-three\"><em>Available Classroom In-Country Courses for Country "+pdhctLSCTID+"</em></p>");
            println("<p>The current language is "+m_prof.getReadLanguage().getNLSDescription()+".</p>");

            // output info

            theGrp = list.getEntityGroup("LSWWCC");
            // output each LSWWCC entity
            for (int x=0; x<theGrp.getEntityItemCount(); x++)
            {
                EntityItem pdhcc;
                if (!wroteTbl)
                {
                    println("<table cellspacing=\"1\" cellpadding=\"0\" summary=\"Course Info\" class=\"basic-table\">"+EACustom.NEWLINE+
                        "<thead><tr "+PokUtils.getTableHeaderRowCSS()+">"+EACustom.NEWLINE+
                        PokUtils.getTableHeader(theGrp, ATTR_CODES)+
                        "</tr></thead>");
                    wroteTbl=true;
                }
                // output each LSWWCC entity
                pdhcc = theGrp.getEntityItem(x);
//              println("<!-- LSWWCC:"+pdhcc.getEntityID()+" -->");
                println("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+EACustom.NEWLINE+
                    PokUtils.getCellData(pdhcc, ATTR_CODES, true, "<br />", DefNotPopulated));
                println("</tr>");
            }

            // release memory
            list.dereference();

            // get LSCC
            extractName= "EXABR1CTCCF1";
            list = m_db.getEntityList(m_prof,
                new ExtractActionItem(null, m_db, m_prof, extractName),
                new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

            // output info
            theGrp = list.getEntityGroup("LSCC");
            // output each LSWWCC entity
            for (int x=0; x<theGrp.getEntityItemCount(); x++)
            {
                EntityItem pdhcc;
                if (!wroteTbl)
                {
                    println("<table cellspacing=\"1\" cellpadding=\"0\" summary=\"Course Info\" class=\"basic-table\">"+EACustom.NEWLINE+
                        "<thead><tr "+PokUtils.getTableHeaderRowCSS()+">"+EACustom.NEWLINE+
                        PokUtils.getTableHeader(theGrp, ATTR_CODES)+
                        "</tr></thead>");
                    wroteTbl=true;
                }
                // output each LSCC entity
                pdhcc = theGrp.getEntityItem(x);
//              println("<!-- LSCC:"+pdhcc.getEntityID()+" -->");
                println("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+EACustom.NEWLINE+
                    PokUtils.getCellData(pdhcc, ATTR_CODES, true, "<br />", DefNotPopulated));
                println("</tr>");
            }

            if (wroteTbl) {
                println("</table>");
            }
            else
            {
                println("<p>No Available Classroom In-Country courses found for Country "+pdhctLSCTID+".</p>");
            }

            // release memory
            list.dereference();

            setReturnCode(PASS);
        }
        catch (Exception exc)
        {
            String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            MessageFormat msgf = new MessageFormat(Error_EXCEPTION);
            Object[] args = new String[1];
            java.io.StringWriter exBuf = new java.io.StringWriter();
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            // Put exception into document
            args[0] = exc.getMessage();
            println(msgf.format(args));
            msgf = new MessageFormat(Error_STACKTRACE);
            args[0] = exBuf.getBuffer().toString();
            println(msgf.format(args));
            logError("Exception: "+exc.getMessage());
            logError(exBuf.getBuffer().toString());
            setReturnCode(FAIL);
        }
        finally
        {
            setDGRptName(navName); // first part of subject line, the status (PASSED or FAILED), then
            setDGTitle(reportName);  // this is last part of subject line
            setDGRptClass(m_abri.getABRCode());
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }

        // Print everything up to </html>
        println(EACustom.getTOUDiv());
		printDGSubmitString();
        buildReportFooter();    // Print </html>
    }

    /**********************************************************************************
    *  Get Name based on navigation attributes
    *
    *@return    java.lang.String
    */
    private String getNavigationName(EntityItem theEntityItem) throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        EntityGroup eg =  new EntityGroup(null, m_db, m_prof, theEntityItem.getEntityType(), "Navigate");
        EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            navName.append(getAttributeValue(theEntityItem, ma.getAttributeCode()));
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
        return "Available Classroom In-Country Courses by Country";
    }

    /***********************************************
    *  Get the version
    *
    *@return    java.lang.String
    */
    public String getABRVersion() {
        return "$Revision: 1.14 $";
    }
}
