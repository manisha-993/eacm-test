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
 *
 * $Log: LSCTABR3.java,v $
 * Revision 1.12  2014/01/13 13:50:51  wendy
 * migration to V17
 *
 * Revision 1.11  2006/06/26 12:42:35  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.10  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.9  2006/03/07 17:03:05  couto
 * Added summary to table tag.
 *
 * Revision 1.8  2006/02/22 13:10:18  wendy
 * Removed font tag, it is not allowed inAHE
 *
 * Revision 1.7  2006/02/21 21:17:05  wendy
 * use AHE compliant title
 *
 * Revision 1.6  2006/02/21 20:20:57  wendy
 * Pass param to meta description tag
 *
 * Revision 1.5  2006/01/22 18:28:29  joan
 * changes for Jtest
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
 * It finds Available External Classroom In-Country courses & Prices per Curriculum
 * BOTH LSCC and LSWWCC for a country!  LSWWCC are in one group, LSCC are in another group.
 * It is invoked after navigating to LSCT.  All curriculums for a particular country
 * are displayed.
 *
 *@author     Wendy Stimpson
 *@created    April 14, 2005
 */
public class LSCTABR3 extends PokBaseABR
{
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
	
    private static final String[] COL_CODES = {
        "LSCURID",                  //$NON-NLS-1$
        "LSWWID",                   //$NON-NLS-1$
        "LSCRSID",                  //$NON-NLS-1$
        "LSCRGLOBALREPTITLE",       //$NON-NLS-1$
        "LSCRSEXTPRICE",            //$NON-NLS-1$
        "LSCRSPRIVPRICE",           //$NON-NLS-1$
        "LSCRSPRIVSTUDMAX",         //$NON-NLS-1$
        "LSCRSPRIVSTUDADDPRICE",    //$NON-NLS-1$
        "LSCRSDURATION",            //$NON-NLS-1$
        "LSCRSDURATIONUNITS"        //$NON-NLS-1$
    };

    private Vector rowItemVct = new Vector();
    private static final int MW_VENTITY_LIMIT = 500;
    private Vector headingVct = new Vector(COL_CODES.length);

    /**
     *  Execute ABR.
     */
    public void execute_run()
    {
        String reportName="";
        String navName="";
        try {
            String DefNotPopulated = EACustom.getDefNotPopulated();
            String extractName = "EXABR1CTWWCCF2";  // LSWWCC filtered on lifecycle and audience  //$NON-NLS-1$
            String pdhctLSCTID;
            EntityList list;
            EntityItem theEntityItem;
            String XR_LSCRSDELIVERY_Classroom = LSConstants.XR_LSCRSDELIVERY_0010;//"0010";
            Vector ccVct;
            boolean wroteTbl=false;
            int lineCnt=0;

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

            // get the LSCT entity
            theEntityItem = list.getParentEntityGroup().getEntityItem(0);

            reportName= getMetaAttributeDescription(list.getParentEntityGroup(),m_abri.getABRCode());
            navName=getNavigationName(theEntityItem);

            pdhctLSCTID = PokUtils.getAttributeValue(theEntityItem, "LSCTID", "",
                DefNotPopulated,true)+" "+
                PokUtils.getAttributeValue(theEntityItem, "LSCTDESC", "", "",true);

            println("<p class=\"ibm-intro ibm-alternate-three\"><em>Available External Classroom In-Country Courses and Prices by Curriculum for Country "+
                    pdhctLSCTID+"</em></p>");
            println("<p>The current language is "+m_prof.getReadLanguage().getNLSDescription()+".</p>");

            // for LSWW: LSCT<-LSWWCC
            // find LSWWCC
            // get entities that have a delivery method of classroom
            ccVct = PokUtils.getEntitiesWithMatchedAttr(list.getEntityGroup("LSWWCC"), "LSCRSDELIVERY",XR_LSCRSDELIVERY_Classroom);  //$NON-NLS-1$
            buildRowsSortedByLSCUR(ccVct, "EXRPT1WWCC4");
            // release memory
            list.dereference();
            ccVct.clear();

            extractName = "EXABR1CTCCF2";  // LSCC filtered on lifecycle and audience  //$NON-NLS-1$
            list = m_db.getEntityList(m_prof,
                new ExtractActionItem(null, m_db, m_prof, extractName),
                new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });
            // for LSCC: LSCT->LSCCF->LSCC
            // get entities that have a delivery method of classroom
            ccVct = PokUtils.getEntitiesWithMatchedAttr(list.getEntityGroup("LSCC"), "LSCRSDELIVERY",XR_LSCRSDELIVERY_Classroom);  //$NON-NLS-1$
            buildRowsSortedByLSCUR(ccVct, "EXRPT1CC2");
            // release memory
            list.dereference();
            ccVct.clear();
            ccVct=null;

            // now sort the curriculums
            Collections.sort(rowItemVct);

            // write out the courses and their lscur
            for (int x=0; x<rowItemVct.size(); x++)
            {
                RowItem rowItem = (RowItem)rowItemVct.elementAt(x);
                // output the information
                if (!wroteTbl)
                {
                    println("<table cellspacing=\"1\" cellpadding=\"0\" summary=\"Course Info\" class=\"basic-table\">"+EACustom.NEWLINE+
                        "<thead><tr "+PokUtils.getTableHeaderRowCSS()+">");

                    for (int ii=0; ii<headingVct.size(); ii++)
                    {
                        println("<th>"+
                            headingVct.elementAt(ii)+"</th>");
                    }
                    println("</tr></thead>");
                    wroteTbl=true;
                    headingVct.clear();
                    headingVct = null;
                }
                println("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">");

                for (int ii=0; ii<COL_CODES.length; ii++)
                {
                    println("<td>"+rowItem.get(ii)+"</td>");
                }
                println("</tr>");

                rowItem.dereference();
            }
            if (wroteTbl){
                println("</table>");}
            else
            {
                println("<p>No Available External Classroom In-Country courses found for Country "+pdhctLSCTID+".</p>");
            }

            rowItemVct.clear();
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

    /********************************************************************************
    * Find LSCUR and LSWW for output row
    *
    * @param ccVct Vector of LSCC or LSWWCC EntityItems
    * @param extractName String with VE name
    */
    private void buildRowsSortedByLSCUR(Vector ccVct, String extractName) throws Exception
    {
        Vector tmp = new Vector();

        // must get new smaller VEs for each entity
        if (ccVct.size() >0)
        {
            if (ccVct.size()>MW_VENTITY_LIMIT)
            {
                int numGrps = ccVct.size()/MW_VENTITY_LIMIT;
                int numUsed=0;
                for (int i=0; i<=numGrps; i++)
                {
                    tmp.clear();
                    for (int x=0; x<MW_VENTITY_LIMIT; x++)
                    {
                        if (numUsed == ccVct.size()){
                            break;}
                        tmp.addElement(ccVct.elementAt(numUsed++));
                    }
                    if (tmp.size()>0) {// could be 0 if num entities is multiple of limit
                        buildCurriculumTbl(tmp, extractName); //$NON-NLS-1$
                    }
                }
            }
            else
            {
                buildCurriculumTbl(ccVct, extractName); //$NON-NLS-1$
            }
        }
        tmp.clear();
        tmp=null;
    }

    /********************************************************************************
    * Sort LSWWCC and LSCC entities by curriculum.
    *
    */
    private void buildCurriculumTbl(Vector entityVct, String extractName)
        throws Exception
    {
        EntityList list;
        String entityType;
        EntityItem array[]= new EntityItem[entityVct.size()];
        entityVct.copyInto(array);

        list = m_db.getEntityList(
            m_prof,
            new ExtractActionItem(null, m_db, m_prof, extractName),
            array);

        entityType = array[0].getEntityType();
        if (entityType.equals("LSWWCC")) //$NON-NLS-1$
        {
            EntityGroup egrp;
            // fill in heading information once
            if (headingVct.size()==0)
            {
                EntityGroup egroup = list.getEntityGroup(entityType);
                headingVct.addElement("CC"); //$NON-NLS-1$
                headingVct.addElement(
                    PokUtils.getAttributeDescription(list.getEntityGroup("LSWW"),"LSWWID", //$NON-NLS-1$
                        "WorldWide Course Code")); //$NON-NLS-1$

                for (int i=2; i<COL_CODES.length; i++)
                {
                    headingVct.addElement(PokUtils.getAttributeDescription(egroup,COL_CODES[i],COL_CODES[i]));
                }
            }

            // new list is not linked to vector of entityItems.. so get parententitygroup
            egrp = list.getParentEntityGroup();
            for (int x=0, xc=egrp.getEntityItemCount(); x<xc; x++)
            {
                EntityItem wwItem;
                EntityItem curItem;
                // get each LSWWCC entity
                EntityItem crsItem = egrp.getEntityItem(x);

                // get LSWW parent
                Vector vct =PokUtils.getAllLinkedEntities(crsItem, "LSWWWWCC", "LSWW"); //$NON-NLS-1$

                if (vct.size()==0) {
                    continue;}
                wwItem = (EntityItem)vct.firstElement();

                // get LSCUR parent, assumes one and only one
                vct= PokUtils.getAllLinkedEntities(wwItem, "LSCURWW", "LSCUR"); //$NON-NLS-1$
                if (vct.size()==0) {
                    continue;}

                curItem =(EntityItem)vct.firstElement();
                vct.clear();

                rowItemVct.addElement(new RowItem(curItem,wwItem,crsItem));
            }
        }
        else
        if (entityType.equals("LSCC")) //$NON-NLS-1$
        {
            EntityGroup egrp;
            // fill in heading information once
            if (headingVct.size()==0)
            {
                EntityGroup egroup = list.getEntityGroup(entityType);
                headingVct.addElement("CC"); //$NON-NLS-1$
                headingVct.addElement("WorldWide Course Code"); //$NON-NLS-1$
                for (int i=2; i<COL_CODES.length; i++)
                {
                    headingVct.addElement(PokUtils.getAttributeDescription(egroup,COL_CODES[i],COL_CODES[i]));
                }
            }

            // new list is not linked to vector of entityItems.. so get parententitygroup
            egrp = list.getParentEntityGroup();

            for (int x=0, xc=egrp.getEntityItemCount(); x<xc; x++)
            {
                EntityItem curItem;
                // output each LSCC entity
                EntityItem crsItem = egrp.getEntityItem(x);

                // get LSCUR child, one and only one can exist
                Vector vct= PokUtils.getAllLinkedEntities(crsItem, "LSCCCUR", "LSCUR"); //$NON-NLS-1$

                if (vct.size()==0) {
                    continue;}
                curItem = (EntityItem)vct.firstElement();
                vct.clear();

                rowItemVct.addElement(new RowItem(curItem,null,crsItem));
            }
        }

        list.dereference();
        list = null;
    }

    /***********************************************
    *  Get ABR description
    *
    *@return    java.lang.String
    */
    public String getDescription() {
        return "Available External Classroom In-Country Courses and Prices by Curriculum for Country";
    }

    /***********************************************
    *  Get the version
    *
    *@return    java.lang.String
    */
    public String getABRVersion() {
        return "$Revision: 1.12 $";
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

    /********************************************************************************
    * Inner class used for a row in the table
    *
    */
    private class RowItem implements Comparable
    {
        private String[] colValues = new String[COL_CODES.length];
        private String lscurid;

        RowItem(EntityItem c, EntityItem w, EntityItem e) throws
            COM.ibm.opicmpdh.middleware.MiddlewareRequestException
        {
            lscurid = PokUtils.getAttributeValue(c, "LSCURID", "", "",true); //$NON-NLS-1$
            setColValue(0, c);
            setColValue(1, w); // lscc will not have lsww parent
            for (int i=2; i<COL_CODES.length; i++)
            {
                setColValue(i, e);
            }
        }
        void setColValue(int id, EntityItem eitem)
        {
            String value = "&nbsp;";
            if (eitem!=null) {
                value = PokUtils.getAttributeValue(eitem, COL_CODES[id], "",
                    EACustom.getDefNotPopulated(),true); //$NON-NLS-1$
            }
            colValues[id] = value;
        }
        void dereference()
        {
            for (int i=0; i<colValues.length; i++)
            {
                colValues[i] = null;
            }
            colValues = null;
            lscurid = null;
        }
        public int compareTo(Object o) {
            // order by LSCURID
            return lscurid.compareTo(((RowItem)o).lscurid);
        }
        String get(int column)
        {
            return colValues[column];
        }
    }
}
