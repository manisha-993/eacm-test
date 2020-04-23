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

import java.util.*;
import java.text.*;

/**
 * CR1008045141
 *  It finds Available In-Country courses for a Curriculum sorted by LSWW:LSWWID
 * BOTH LSCC and LSWWCC!  LSWWCC are sorted by LSWW in one group, LSCC are
 * in another group.
 * It will be invoked after navigating to a LSCUR
 *
 * $Log: LSCURABR1.java,v $
 * Revision 1.11  2014/01/13 13:50:51  wendy
 * migration to V17
 *
 * Revision 1.10  2006/06/26 12:42:34  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.9  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.8  2006/03/08 14:55:44  wendy
 * Added table, th and td tag attributes for AHE
 *
 * Revision 1.7  2006/02/22 13:10:18  wendy
 * Removed font tag, it is not allowed inAHE
 *
 * Revision 1.6  2006/02/21 21:17:05  wendy
 * use AHE compliant title
 *
 * Revision 1.5  2006/02/21 20:20:57  wendy
 * Pass param to meta description tag
 *
 * Revision 1.4  2005/11/14 12:44:38  wendy
 * New AHE format
 *
 * Revision 1.3  2005/04/20 02:15:27  wendy
 * Correct get nav name
 *
 * Revision 1.2  2005/04/20 01:45:15  wendy
 * TIR 6BK52V change subject line
 *
 * Revision 1.1  2005/04/15 14:42:26  wendy
 * Converted from XSL to avoid data volume problems
 *
 *@author     Wendy Stimpson
 *@created    April 14, 2005
 */
public class LSCURABR1 extends PokBaseABR
{
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
	
    private static final String[] COL_CODES =
    {
        "LSCRSID",                  //$NON-NLS-1$
        "LSCRGLOBALREPTITLE",       //$NON-NLS-1$
        "LSCRSEXPDATE",             //$NON-NLS-1$
    };

    /**
     *  Execute ABR.
     */
    public void execute_run()
    {
        String reportName="";
        String navName="";
        try {
            String DefNotPopulated = EACustom.getDefNotPopulated();
            EntityItem theEntityItem;
            String pdhcurLSCURID;
            String wwLSWWIDdesc;
            String wwLSWWEXPDATEdesc;
            String ctLSCTIDdesc;
            Vector pdhwwVct;
            EntityGroup ccGrp;
            boolean wroteTbl=false;
            int lineCnt=0;

            start_ABRBuild();

            reportName= getMetaAttributeDescription(m_elist.getParentEntityGroup(),m_abri.getABRCode());
            navName=getNavigationName();

            println(EACustom.getDocTypeHtml()); // Output the doctype and html

            println("<head>"+EACustom.NEWLINE+
	            EACustom.getMetaTags(getDescription())+EACustom.NEWLINE+
                EACustom.getCSS()+EACustom.NEWLINE+
            	EACustom.getTitle(getDescription())+EACustom.NEWLINE+
                "</head>"+EACustom.NEWLINE+
                "<body id=\"ibm-com\">");

            println(EACustom.getMastheadDiv());
            println("<!-- ABR version="+getABRVersion()+" -->");

            //VE has filters for available lscc and lswwcc
            // get the LSCUR entity
            theEntityItem = m_elist.getParentEntityGroup().getEntityItem(0);

            pdhcurLSCURID =PokUtils.getAttributeValue(theEntityItem, "LSCURID", "", DefNotPopulated,true);
            println("<p class=\"ibm-intro ibm-alternate-three\"><em>All Available In-Country Courses for Curriculum "+
                pdhcurLSCURID+", sorted by Worldwide Course</em></p>");
            println("<p>The current language is "+m_prof.getReadLanguage().getNLSDescription()+".</p>");

            wwLSWWIDdesc = PokUtils.getAttributeDescription(m_elist.getEntityGroup("LSWW"),"LSWWID", "WorldWide Course Code");
            wwLSWWEXPDATEdesc = PokUtils.getAttributeDescription(m_elist.getEntityGroup("LSWW"),"LSWWEXPDATE", "WorldWide Course Expiration Date");
            ctLSCTIDdesc = PokUtils.getAttributeDescription(m_elist.getEntityGroup("LSCT"),"LSCTID", "Country ID");

            // LSWW are children
            pdhwwVct = PokUtils.getAllLinkedEntities(theEntityItem, "LSCURWW", "LSWW");

            // sort by LSWWID course code
            pdhwwVct = PokUtils.sort(pdhwwVct, "LSWWID");
            // first write out the LSWWCC courses
            // get LSWWCC children for each LSWW
            for (int x=0, xc=pdhwwVct.size(); x<xc; x++)
            {
                // get the LSWW entity
                EntityItem pdhww = (EntityItem)pdhwwVct.elementAt(x);
                // find LSWWCC children, display only AVAILABLE courses (filtered in VE)
                Vector pdhccVct = PokUtils.getAllLinkedEntities(pdhww, "LSWWWWCC", "LSWWCC");
                if (pdhccVct.size()>0)
                {
                    if (!wroteTbl)
                    {
                        println("<table cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Available courses\">"+EACustom.NEWLINE+
                            "<tr "+PokUtils.getTableHeaderRowCSS()+">"+EACustom.NEWLINE+
                            "<th id=\"LSWWID\">"+wwLSWWIDdesc+"</th>"+EACustom.NEWLINE+
                            PokUtils.getTableHeader(m_elist.getEntityGroup("LSWWCC"), COL_CODES)+
                            "<th id=\"LSWWEXPDATE\">"+wwLSWWEXPDATEdesc+"</th>"+EACustom.NEWLINE+
                            "<th id=\"LSCTID\">"+ctLSCTIDdesc+"</th>"+EACustom.NEWLINE+
                            "</tr>");

                        wroteTbl=true;
                    }
                    // output each LSWWCC entity
                    for (int i=0,c=pdhccVct.size(); i<c; i++)
                    {
                        String pdhctLSCTID=DefNotPopulated;
                        Vector ctVct;
                        // get the LSWWCC entity
                        EntityItem pdhcc = (EntityItem)pdhccVct.elementAt(i);
                        //println("<!-- "+pdhww.getKey()+" "+pdhcc.getKey()+" -->");
                        println("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+EACustom.NEWLINE+
                            "<td headers=\"LSWWID\">"+
                            PokUtils.getAttributeValue(pdhww, "LSWWID", "", DefNotPopulated,true)+
                            "</td>");
                        print(PokUtils.getCellData(pdhcc, COL_CODES, true, "<br />", DefNotPopulated));
                        println("<td headers=\"LSWWEXPDATE\">"+
                            PokUtils.getAttributeValue(pdhww, "LSWWEXPDATE", "", DefNotPopulated,false)
                            +"</td>");

                        // get country code ->LSCT
                        // there is a single LSCT child from LSWWCC
                        ctVct = PokUtils.getAllLinkedEntities(pdhcc, "WWCCCTA", "LSCT");
                        if (ctVct.size()>0)
                        {
                            pdhctLSCTID = PokUtils.getAttributeValue((EntityItem)ctVct.firstElement(), "LSCTID", "", DefNotPopulated,true);
                            ctVct.clear();
                        }

                        println("<td headers=\"LSCTID\">"+pdhctLSCTID+"</td>");
                        println("</tr>");
                    }
                    pdhccVct.clear();
                }
            }

            pdhwwVct.clear();
            // now write the LSCC courses (VE filter on 'available' LSCC)
            ccGrp = m_elist.getEntityGroup("LSCC");

            for (int xp=0; xp<ccGrp.getEntityItemCount(); xp++)
            {
                String pdhctLSCTID=DefNotPopulated;
                Vector pdhccfVct;
                // get the LSCC entity
                EntityItem pdhcc = ccGrp.getEntityItem(xp);
                if (!wroteTbl)
                {
                    println("<table cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Available courses\">"+EACustom.NEWLINE+
                        "<tr "+PokUtils.getTableHeaderRowCSS()+">"+EACustom.NEWLINE+
                        "<th id=\"LSWWID\">"+wwLSWWIDdesc+"</th>"+EACustom.NEWLINE+
                        PokUtils.getTableHeader(ccGrp, COL_CODES)+
                        "<th id=\"LSWWEXPDATE\">"+wwLSWWEXPDATEdesc+"</th>"+EACustom.NEWLINE+
                        "<th id=\"LSCTID\">"+ctLSCTIDdesc+"</th>"+EACustom.NEWLINE+
                        "</tr>");

                    wroteTbl=true;
                }

                //println("<!-- "+pdhcc.getKey()+" -->");
                println("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+EACustom.NEWLINE+
                        "<td headers=\"LSWWID\">&nbsp;</td>");
                print(PokUtils.getCellData(pdhcc, COL_CODES, true, "<br />", DefNotPopulated));
                println("<td headers=\"LSWWEXPDATE\">&nbsp;</td>");

                // get country code LSCC<-LSCCF<-LSCT
                pdhccfVct = PokUtils.getAllLinkedEntities(pdhcc, "LSCCFCC", "LSCCF");
                if (pdhccfVct.size() >0)
                {
                    // there is a single LSCT parent from LSCCF
                    Vector ctVct=PokUtils.getAllLinkedEntities((EntityItem)pdhccfVct.firstElement(), "LSCTCCF", "LSCT");
                    if (ctVct.size()>0)
                    {
                        pdhctLSCTID =PokUtils.getAttributeValue((EntityItem)ctVct.firstElement(), "LSCTID", "", DefNotPopulated,true);
                        ctVct.clear();
                    }
                    pdhccfVct.clear();
                }

                println("<td headers=\"LSCTID\">"+pdhctLSCTID+"</td>");
                println("</tr>");
            }
            ccGrp=null;

            if (wroteTbl){
                println("</table>");}
            else
            {
                println("<p>No Available In-Country courses found for Curriculum "+pdhcurLSCURID+".</p>");
            }

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
    private String getNavigationName() throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        EntityGroup eg =  new EntityGroup(null, m_db, m_prof, getEntityType(), "Navigate");
        EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            navName.append(getAttributeValue(getEntityType(), getEntityID(), ma.getAttributeCode()));
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
        return "Available In-Country Courses sorted by WW Course";
    }

    /***********************************************
    *  Get the version
    *
    *@return    java.lang.String
    */
    public String getABRVersion() {
        return "$Revision: 1.11 $";
    }
}
