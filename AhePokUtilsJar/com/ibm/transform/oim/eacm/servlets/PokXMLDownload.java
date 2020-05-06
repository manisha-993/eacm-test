// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.transform.oim.eacm.servlets;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;
import COM.ibm.opicmpdh.objects.*;

/******************************************************************************
* This servlet generates the specified XML template and returns it as a stream
* to the caller.  The browser will present the user with a save file dialog.
*
* @author Wendy Stimpson
*
* Change History:
*/
// $Log: PokXMLDownload.java,v $
// Revision 1.7  2008/01/22 17:01:46  wendy
// Cleanup more RSA warnings
//
// Revision 1.6  2008/01/22 16:58:14  wendy
// Cleanup RSA warnings
//
// Revision 1.5  2006/01/25 18:31:30  wendy
// AHE copyright
//
// Revision 1.4  2005/12/16 21:03:24  wendy
// DQA changes for th id attributes and td headers attributes
//
// Revision 1.3  2005/12/16 14:09:07  wendy
// Change for DQA added type to script tag
//
// Revision 1.2  2005/10/03 18:05:27  wendy
// Conform to new jtest config
//
// Revision 1.1  2005/09/08 17:09:26  wendy
// Init OIM3.0b
//
//
public class PokXMLDownload extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";

    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.7 $";
    private static final Vector IGS_ATTR_WRAP_VCT;
    static
    {
        // these attribute values must be wrapped in CDATA syntax
        IGS_ATTR_WRAP_VCT = new Vector();
        //IGS_ATTR_WRAP_VCT.addElement("FEATUREBENEFIT"); no longer type T, now X so wrap is not needed
        IGS_ATTR_WRAP_VCT.addElement("DELIVERYMETHOD");
        IGS_ATTR_WRAP_VCT.addElement("WHYIBM");
        IGS_ATTR_WRAP_VCT.addElement("BPFAQS");
        IGS_ATTR_WRAP_VCT.addElement("BPDISTRIBINCENTIVE");
        IGS_ATTR_WRAP_VCT.addElement("BPTIER1INCENTIVE");
        IGS_ATTR_WRAP_VCT.addElement("BPTIER2INCENTIVE");
        IGS_ATTR_WRAP_VCT.addElement("PRICINGDETAILS");
        IGS_ATTR_WRAP_VCT.addElement("BPPROCESSDESCRIPTION");
        IGS_ATTR_WRAP_VCT.addElement("DMPPURPOSE");
        IGS_ATTR_WRAP_VCT.addElement("USAGECRITERIA");
        IGS_ATTR_WRAP_VCT.addElement("PROMOCRITERIA");
        IGS_ATTR_WRAP_VCT.addElement("PROMOPURPOSE");
        IGS_ATTR_WRAP_VCT.addElement("VENDORCONSID");
        IGS_ATTR_WRAP_VCT.addElement("PURCHASINGPROCESS");
    }

    private static final String[] ANNOUNCEMENT_ATTR = new String[]
    {
        "ANNNUMBER","ANNTITLE","ANNTYPE","ANNDATE","ATAGLANCE"
    };
    private static final String[] AVAIL_ATTR = new String[]
    {
        "AVAILTYPE","EFFECTIVEDATE","GENAREASELECTION","COUNTRYLIST"
    };
    private static final String[] SOF_ATTR = new String[]
    {
        "OFIDNUMBER","MKTGNAME","LOB","SOFCATEGORY","BUSTECHISSUE","INDUSTRYSOLUTIONS","ITCAPABILITY",
        "OVERVIEWABSTRACT","DESCRIPTION","FEATUREBENEFIT","DIFFEATURESBENEFITS","MKTGSTRATEGY","OTHERMKTGINFO",
        "CUSTWANTSNEEDS","CUSTCANDGUIDELINES","CUSTRESTRICTIONS","HANDOBJECTIONS","SALESACTREQ","SALESAPPROACH",
        "DELIVERYMETHOD","PREREQCOREQ","RESOURSKILLSET","VENDORCONSID","CROSSELL","UPSELL","COMPETITIVEOF",
        "STRENGTHWEAKNESS","WHYIBM","BPAVAILINFRASTRCT","BPFAQS",
        "PURCHASINGPROCESS", // CR0219041917 add
        "CUSTPAINPT","QUALQUEST","TGTCUSTEXECS","CUSTCONTACT","IBMCONTACT",  // CR1210035247 add
        "EXECPRESENTATION","BPSPECSHEET","CUSTINFOSHEET"
    };
    private static final String[] PARENTSOF_ATTR = new String[]
    {
        "OFIDNUMBER","MKTGNAME","LOB","SOFCATEGORY"
    };
    private static final String[] PARENTCMPNT_ATTR = new String[]
    {
        "COMPONENTID","MKTGNAME"
    };
    private static final String[] CMPNT_ATTR = new String[]
    {
        "COMPONENTID","MKTGNAME","BUSTECHISSUE","INDUSTRYSOLUTIONS","ITCAPABILITY","OVERVIEWABSTRACT",
        "DESCRIPTION","FEATUREBENEFIT","DIFFEATURESBENEFITS","MKTGSTRATEGY","OTHERMKTGINFO","CUSTWANTSNEEDS",
        "CUSTCANDGUIDELINES","CUSTRESTRICTIONS","HANDOBJECTIONS","SALESACTREQ","SALESAPPROACH","DELIVERYMETHOD",
        "PREREQCOREQ","RESOURSKILLSET","VENDORCONSID","CROSSELL","UPSELL","COMPETITIVEOF","STRENGTHWEAKNESS",
        "WHYIBM","BPAVAILINFRASTRCT","BPFAQS","PURCHASINGPROCESS",
        "CUSTPAINPT","QUALQUEST","TGTCUSTEXECS","CUSTCONTACT","IBMCONTACT",  // CR1210035247 add
        "EXECPRESENTATION","BPSPECSHEET","CUSTINFOSHEET"
    };
    private static final String[] FEATURE_ATTR = new String[]
    {
        "FEATURENUMBER","MKTGNAME","BUSTECHISSUE","INDUSTRYSOLUTIONS","ITCAPABILITY","OVERVIEWABSTRACT",
        "DESCRIPTION","FEATUREBENEFIT","DIFFEATURESBENEFITS","MKTGSTRATEGY","OTHERMKTGINFO","CUSTWANTSNEEDS",
        "CUSTCANDGUIDELINES","CUSTRESTRICTIONS","HANDOBJECTIONS","SALESACTREQ","SALESAPPROACH","DELIVERYMETHOD",
        "PREREQCOREQ","RESOURSKILLSET","CROSSELL","UPSELL","COMPETITIVEOF",
        "STRENGTHWEAKNESS","WHYIBM"
        //,"PURCHASINGPROCESS" CR0219041917 remove
    };
    private static final String[] PRICEFININFO_ATTR = new String[]
    {
        "BPDISTRIBINCENTIVE","BPTIER1INCENTIVE","BPTIER2INCENTIVE","CHARGES","PRICINGDETAILS"
    };
    private static final String[] CHANNEL_ATTR = new String[]
    {
        "CHANNELNAME","CHANNELTYPE","COUNTRYLIST"// removed for RFA Guide 19.04 ,"SUPPLIER"
    };
    private static final String[] OP_ATTR = new String[]
    {
        "FIRSTNAME","MIDDLENAME","LASTNAME","JOBTITLE","USERTOKEN"
    };
    private static final String[] SALESCNTCTOP_ATTR = new String[]
    {
        "GENAREASELECTION","COUNTRYLIST"
    };
    private static final String[] BPPROCESS_ATTR = new String[]
    {
        "BPPROCESSNAME","BPPROCESSDESCRIPTION"
    };
    private static final String[] BPDMP_ATTR = new String[]
    {
        "DMPNAME","DMPPURPOSE","USAGECRITERIA"
    };
    private static final String[] BPPROMOTION_ATTR = new String[]
    {
        "PROMONAME","PROMOCRITERIA","PROMOPURPOSE"
    };
    private static final String[] RELSOF_SOF_ATTR = new String[]
    {
        "OFIDNUMBER","MKTGNAME"
    };
    private static final String[] RELCMPNT_CMPNT_ATTR = new String[]
    {
        "COMPONENTID","MKTGNAME"
    };
    private static final String[] RELFEATURE_FEATURE_ATTR = new String[]
    {
        "FEATURENUMBER","MKTGNAME"
    };
    private static final String[] OF_REL_ATTR = new String[]
    {
        "TYPE","BENEFIT"
    };

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /*****************************************************************************
     * Process incoming HTTP GET requests
     *
     * @param request   request to the servlet
     * @param response  response from the servlet
     * @throws IOException
     * @throws ServletException
     * @concurrency $none
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        synchronized(request.getSession())
        {
            handleRequest(request,response);
        }
    }

    /*****************************************************************************
    * Process incoming HTTP POST requests
    *
    * @param req   request to the servlet
    * @param res   response from the servlet
    * @throws IOException
    * @throws ServletException
    */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        doGet(req,res);
    }

    /*****************************************************************************
    * Handle the request
    *
    * @param request   request to the servlet
    * @param response  response from the servlet
    * @throws IOException
    * @throws ServletException
    */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String data = null;
        byte[] dataBytes = null;
        Profile profile = null;
        DatabasePool dbpCurrent = null;
        Database dbCurrent = null;
        ProfileSet profileSet = null;
        com.ibm.transform.oim.eacm.bui.webapp.Constants constants = null;
        int entityId = 0;
        // get the session
        HttpSession session = request.getSession();
        // Get the session Id
        String strPurpose = getServletName();
        String strId = session.getId() + "." + strPurpose;

        //------------------------------------- Get the report parameter(s)
        String entityType   = request.getParameter("entityType");
        String entityIdStr  = request.getParameter("entityID");
        String attributeCode  = request.getParameter("attributeCode");
        String filename     = request.getParameter("filename");
        String debugStr     = request.getParameter("debug");
        String downloadType = request.getParameter("downloadType");
        if (downloadType==null) {
            downloadType="catalog";}

        entityId = Integer.valueOf(entityIdStr).intValue();

        // check that user has logged in
        constants = new com.ibm.transform.oim.eacm.bui.webapp.Constants(request);
        profileSet = (ProfileSet)session.getAttribute(constants.getSESProfileSet());

        // set headers for no cache
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");

        // if the ProfileSet is null, the user came to this
        // page without logging in or session timed out
        if (profileSet == null || session.getAttribute("strLoginName") == null)
        {
            PrintWriter out = null;
            /*
            The charset for the MIME body response can be specified with setContentType.
            For example, "text/html; charset=Shift_JIS". The charset can alternately be set
            using setLocale. If no charset is specified, ISO-8859-1 will be used.
            The setContentType or setLocale method must be called before getWriter for
            the charset to affect the construction of the writer.
            */
            response.setContentType("text/html; charset=utf-8");

            out = response.getWriter();
            // not logged in
            out.println("<script type=\"text/javascript\" language=\"javascript\">");
            out.println("alert(\"Your login to e-announce has expired.  Your report window will close.  Please re-login before running this report.\");");
            out.println("window.close();");
            out.println("</script>");
            return;
        }

        profile = profileSet.getActiveProfile();

        // Get a database for the scope of this method
        // Get the pool from this session
        dbpCurrent = (DatabasePool)getServletContext().getAttribute("WASPool");
        try {
            // Ask the pool for a connection to use
            // It will be freed when at the end of this method
            dbCurrent = dbpCurrent.getConnection(strPurpose, strId);

            // determine type of xml to generate
            if (downloadType.equals("blob")) // just get the blob in binary format
            {
                String extractName = "DUMMY";
                EntityList list = dbCurrent.getEntityList(
                    profile,
                    new ExtractActionItem(null, dbCurrent, profile, extractName),
                    new EntityItem[] { new EntityItem(null, profile, entityType, entityId) });

                EntityItem theEntityItem = list.getParentEntityGroup().getEntityItem(entityType+entityId);
                EANAttribute attr = theEntityItem.getAttribute(attributeCode);
                if (attr != null && attr instanceof EANBlobAttribute)
                {
                    EANBlobAttribute blobAtt = (EANBlobAttribute) attr;
                    filename = blobAtt.getBlobExtension();
                    try {
                        dataBytes = blobAtt.getBlobValue();
                        if (dataBytes == null) {
                            dataBytes = blobAtt.getBlobValue(null, dbCurrent);
                        }

                    } catch (Exception e) {
                        System.out.println(e.getMessage());}
                }

                // release memory
                list.dereference();
            }
            else if (downloadType.equals("catalog"))
            {
                String extractName = "EXRPT1CC1";//"EXTLSVECC1";
                EntityList list = null;
                EntityItem theEntityItem = null;
                filename=filename+".lsxml";
                // determine VE based on entity type
                if (entityType.equals("LSWWCC")) {
                    extractName = "EXRPT1WWCC1"; }//"EXTLSVEWWCC1";

                list = dbCurrent.getEntityList(
                    profile,
                    new ExtractActionItem(null, dbCurrent, profile, extractName),
                    new EntityItem[] { new EntityItem(null, profile, entityType, entityId) });

                theEntityItem = list.getParentEntityGroup().getEntityItem(entityType+entityId);
                data = getCatalogDownload(theEntityItem);

                dataBytes = data.getBytes("UTF8");
                // release memory
                list.dereference();
            }
            else if (downloadType.equals("igsxml")) // handle IGS xml
            {
                String extractName = "XXX";
                String timeStampAttrCode = "DOWNLOADTS"; // CR0202045351
                EntityList list = null;
                if (!filename.toLowerCase().endsWith(".xml")){
                    filename=filename+".xml";}

                // determine VE based on entity type
                if (entityType.equals("SOF")){
                    extractName = "EXRPT2SOF1";}
                else{
                    if (entityType.equals("FEATURE")){
                        extractName = "EXRPT2FEAT1"; }
                    else {
                        if (entityType.equals("CMPNT")){
                            extractName = "EXRPT2CMPNT1";
                        }
                    }
                }

                list = dbCurrent.getEntityList(
                    profile,
                    new ExtractActionItem(null, dbCurrent, profile, extractName),
                    new EntityItem[] { new EntityItem(null, profile, entityType, entityId) });

                if (entityType.equals("SOF")) {
                    data = getSOFDownload(dbCurrent, profile,list, debugStr!=null);}
                else {
                    if (entityType.equals("FEATURE")) {
                        data = getFEATUREDownload(dbCurrent, profile,list, debugStr!=null);}
                    else {
                        if (entityType.equals("CMPNT")) {
                            data = getCMPNTDownload(dbCurrent, profile,list, debugStr!=null);
                        }
                    }
                }

                if (data !=null)
                {
                    dataBytes = data.getBytes("UTF8");

                    // CR0202045351 save timestamp of report execution (xml download)
                    setXMLDownloadTimeStamp(dbCurrent, profile, list, timeStampAttrCode);
                }

                // release memory
                list.dereference();
            }

            /*
            From http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1
            19.5.1 Content-Disposition
            The Content-Disposition response-header field has been proposed as a means for the origin server to suggest a default filename if the user requests that the content is saved to a file. This usage is derived from the definition of Content-Disposition in RFC 1806 [35].

                    content-disposition = "Content-Disposition" ":"
                                          disposition-type *( ";" disposition-parm )
                    disposition-type = "attachment" | disp-extension-token
                    disposition-parm = filename-parm | disp-extension-parm
                    filename-parm = "filename" "=" quoted-string
                    disp-extension-token = token
                    disp-extension-parm = token "=" ( token | quoted-string )

            An example is

                    Content-Disposition: attachment; filename="fname.ext"

            The receiving user agent SHOULD NOT respect any directory path information present in the filename-parm parameter,
            which is the only parameter believed to apply to HTTP implementations at this time. The filename SHOULD be treated
            as a terminal component only.

            If this header is used in a response with the application/octet-stream content-type, the implied suggestion is
            that the user agent should not display the response, but directly enter a `save response as...' dialog.

            "Internet Explorer May Stop Responding When You Cancel a File Download"
            http://support.microsoft.com/default.aspx?scid=kb;en-us;324228
            */
//          response.setContentType("text/xml");
            response.setContentType("application/octet-stream");

            // When a Web server replies with a "Content-disposition: attachment" HTTP header forces a file download
            response.setHeader("Content-Disposition","attachment; filename="+filename);

/*        response.setHeader(
            "Content-Type",
            "application/octet-stream; name=\"" + filename + "\";");

        response.setHeader(
            "Content-Disposition",
            "inline; filename=\"" + filename + "\";"); does not provide dialog box!!
*/
            if (dataBytes!=null)
            {
                // get a binary output stream to the client
                ServletOutputStream outStr = response.getOutputStream();

                outStr.write(dataBytes);
                outStr.close();
            }
        }
        catch(java.sql.SQLException e) {
            throw new javax.servlet.ServletException(e);
        }
        catch(MiddlewareShutdownInProgressException e) {
            throw new javax.servlet.ServletException(e);
        }
        catch(MiddlewareException e) {
            throw new javax.servlet.ServletException(e);
        }
        finally {
            // Free the connection
            if (dbpCurrent != null)
            {
                dbpCurrent.freeConnection(dbCurrent, strId);
                dbpCurrent = null;
                dbCurrent = null;
            }
        }
    }

    /********************************************************************************
    * Fill in XML template.  It is only done for catalog preview and LSWWCC or LSCC entities.
    *
    * @param entityItem  EntityItem with data used to populate the xml template
    * @returns String
    */
    private static String getCatalogDownload(EntityItem entityItem)
    {
    /*
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE course-description SYSTEM "C:\coursedesc.dtd">  //CR0926025727
    <course-description coursecode="Course Code attribute data from OPICM (LSCRSID)">
    <title edit="no">Title attribute data from OPICM (LSCRSTITLE)</title>
    <duration edit="no">Duration and Duration Units attribute data from OPICM (LSCRSDURATION LSCRSDURATIONUNITS)</duration>
    <skill-level edit="no">Skill Level attribute data from OPICM (LSCRSCATSKILLLEV)</skill-level>
    <delivery edit="no"> Delivery Method attribute data from OPICM (LSCRSDELIVERY)</delivery>
    <price  edit="no">External Price attribute data from OPICM with $ (LSCRSEXTPRICE)</price>
    <new-course edit="no"> new course attribute data, possible values:yes|no from OPICM (LSCRSPRTCATET)</new-course>
    <overview> Overview XML attribute data goes here (LSCRSCOURSEOVERVIEW)</overview>
    <audience> Audience description attribute XML data goes here (LSCRSAUDIENCEDESC)</audience>
    <prerequisites> Prerequisite description attribute XML data goes here (LSCRSPREREQDESC)</prerequisites>
    <objectives> Objectives attribute XML data goes here (LSCRSOBJECTIVES)</objectives>
    <topics> Topics attriubte XML data goes here (LSCRSTOPICS)</topics>
    <machine-requirements > Machine Requirements attribute XML data goes here</machine-requirements> (LSCRSSTUDENTMACHREQTS)
    <volume-price>
    <table border="1"> (if there aren't any LSVPC entities, do not output the table )
    <tr><th id="order">Order No. (LSCRSVPCORDNUM)</th><th id="delivery">Delivery Method (LSCRSVPCDELTYPE)</th><th id="price">Price (LSCRSVPC)</th></tr>
    <tr publish= "general|webonly"><td headers="order">VP data1</td><td headers="delivery">VP data</td><td headers="price">VP data</td></tr> (repeat as necessary)
    </table>
    </volume-price>
    <ordering-info> Ordering Information attribute XML data goes here (LSCRSORD)</ordering-info>
    </course-description>
    */
        // build the output file
        StringBuffer sb = new StringBuffer();
        String price = null;
        Vector vpcVct = null;

        // start the xml
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append(NEWLINE+"<!DOCTYPE course-description SYSTEM \"C:\\coursedesc.dtd\">");  //CR0926025727
        sb.append(NEWLINE+"<course-description coursecode=\""+
            PokUtils.getAttributeValue(entityItem, "LSCRSID", "", "",false)+
            "\">");
        sb.append(NEWLINE+"<title edit=\"no\">"+
            PokUtils.getAttributeValue(entityItem, "LSCRSTITLE", "", "",false)+
            "</title>");
        sb.append(NEWLINE+"<duration edit=\"no\">"+
            PokUtils.getAttributeValue(entityItem, "LSCRSDURATION", "", "",false)+
            " "+
            PokUtils.getAttributeValue(entityItem, "LSCRSDURATIONUNITS", "", "",false)+
            "</duration>");
        sb.append(NEWLINE+"<skill-level edit=\"no\">"+
            PokUtils.getAttributeValue(entityItem, "LSCRSCATSKILLLEV", "", "",false)+
            "</skill-level>");
        sb.append(NEWLINE+"<delivery edit=\"no\">"+
            PokUtils.getAttributeValue(entityItem, "LSCRSDELIVERY", "", "",false)+
            "</delivery>");
        price = PokUtils.getAttributeValue(entityItem,"LSCRSEXTPRICE", "", null,false);

        if (price==null) {
            price = ""; }
        else {
            price = "$"+price;}
        sb.append(NEWLINE+"<price edit=\"no\">"+price+"</price>");
        sb.append(NEWLINE+"<new-course edit=\"no\">"+
            PokUtils.getAttributeValue(entityItem,"LSCRSPRTCATET", "", "",false)+
            "</new-course>");
        sb.append(NEWLINE+"<overview>"+
            PokUtils.getAttributeValue(entityItem,"LSCRSCOURSEOVERVIEW", "", "",false)+
            "</overview>");
        sb.append(NEWLINE+"<audience>"+
            PokUtils.getAttributeValue(entityItem,"LSCRSAUDIENCEDESC", "", "",false)+
            "</audience>");
        sb.append(NEWLINE+"<prerequisites>"+
            PokUtils.getAttributeValue(entityItem,"LSCRSPREREQDESC", "", "",false)+
            "</prerequisites>");
        sb.append(NEWLINE+"<objectives>"+
            PokUtils.getAttributeValue(entityItem,"LSCRSOBJECTIVES", "", "",false)+
            "</objectives>");
        sb.append(NEWLINE+"<topics>"+
            PokUtils.getAttributeValue(entityItem,"LSCRSTOPICS", "", "",false)+
            "</topics>");
        sb.append(NEWLINE+"<machine-requirements>"+
            PokUtils.getAttributeValue(entityItem,"LSCRSSTUDENTMACHREQTS", "", "",false)+
            "</machine-requirements>");
        sb.append(NEWLINE+"<volume-price>");

        // if there aren't any LSVPC entities, do not output the table
        vpcVct = PokUtils.getAllLinkedEntities(entityItem, entityItem.getEntityType()+"VPC", "LSVPC");
        if (vpcVct.size()>0)
        {
            String []attrCodes = {"LSCRSVPCORDNUM","LSCRSVPCDELTYPE","LSCRSVPC"};
            EntityGroup egroup = ((EntityItem)vpcVct.firstElement()).getEntityGroup();
            sb.append(NEWLINE+"<table border=\"1\"><tr>"+NEWLINE);
            for (int x=0; x<attrCodes.length; x++)
            {
                sb.append("<th id=\""+attrCodes[x]+"\">"+PokUtils.getAttributeDescription(egroup,attrCodes[x],attrCodes[x])+"</th>");
            }
            sb.append("</tr>"+NEWLINE);
            sb.append(getOrderInfo(vpcVct));
            sb.append("</table>"+NEWLINE);
        }

        sb.append("</volume-price>");
        sb.append(NEWLINE+"<ordering-info>"+
            PokUtils.getAttributeValue(entityItem,"LSCRSORD", "", "",false)+
            "</ordering-info>");
        sb.append(NEWLINE+"</course-description>");

        return sb.toString();
    }

    /********************************************************************************
    * Order info.. volume pricing.  Table rows is controlled by number of relators
    * Order is controlled by display order attribute.........
    *
    * @param vpcVct Vector of LSVPC entities
    * @returns String
    */
    private static String getOrderInfo(Vector vpcVct)
    {
    //<tr publish= "general|webonly"><td>VP data1</td><td>VP data</td><td>VP data</td></tr> (repeat as necessary)
        StringBuffer sb = new StringBuffer();

        // sort on LSCRSVPCDISPORD
        vpcVct = PokUtils.sort(vpcVct,"LSCRSVPCDISPORD");

        // output volumeprice info
        if (vpcVct.size()>0)
        {
            String []attrCodes = {"LSCRSVPCORDNUM","LSCRSVPCDELTYPE","LSCRSVPC"};
            for (int i=0; i<vpcVct.size(); i++)
            {
                EntityItem vpc = (EntityItem)vpcVct.elementAt(i);
                String pub = PokUtils.getAttributeValue(vpc, "LSVOLPRICEPUB", "", null,false);
                // default to general
                if (pub==null) {
                    pub="general";}
                sb.append("<tr publish=\""+pub+"\">");
                for (int x=0; x<attrCodes.length; x++)
                {
                    sb.append("<td headers=\""+attrCodes[x]+"\">"+
                        PokUtils.getAttributeValue(vpc, attrCodes[x], "", "",false)
                        +"</td>");
                }
                sb.append("</tr>"+NEWLINE);
            }
        }

        return sb.toString();
    }

    /* IGS Interwoven Feeds CR0304036823 */
    /********************************************************************************
    * Fill in XML template for SOF.
    * IGS Service Offering Template

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE eannounce_igs_service_offering >
    <eannounce_igs_service_offering>
    <date_of_report>            </date_of_report>
    <created_by_user>           </created_by_user>
    <igs_service_offering>  SOF
        <offering_identification_number>  OFIDNUMBER                   </offering_identification_number>
        <marketing_name>                  MKTGNAME                     </marketing_name>
        <line_of_business>                LOB                          </line_of_business>
        <offering_catagory>               SOFCATEGORY                  </offering_catagory>
        <business_and_technology_issue>   BUSTECHISSUE                 </business_and_technology_issue>
        <industry_solutions>              INDUSTRYSOLUTIONS            </industry_solutions>
        <it_capability>                   ITCAPABILITY                 </it_capability>
        <overview_abstract>               OVERVIEWABSTRACT             </overview_abstract>
        <description>                     DESCRIPTION                  </description>
        <features_and_benefits>           FEATUREBENEFIT               </features_and_benefits>
        <differentiating_features_and_benefits> DIFFEATURESBENEFITS    </differentiating_features_and_benefits>
        <marketing_strategy>              MKTGSTRATEGY                 </marketing_strategy>
        <other_marketing_info>            OTHERMKTGINFO                </other_marketing_info>
        <customer_wants_and_needs>        CUSTWANTSNEEDS               </customer_wants_and_needs>
        <customer_candidate_guidelines>   CUSTCANDGUIDELINES           </customer_candidate_guidelines>
        <customer_restriction>            CUSTRESTRICTIONS             </customer_restrictions>
        <handling_objections>             HANDOBJECTIONS               </handling_objections>
        <sales_action_required>           SALESACTREQ                  </sales_action_required>
        <sales_approach>                  SALESAPPROACH                </sales_approach>
        <delivery_method>                 DELIVERYMETHOD               </delivery_method>
        <prereq_coreq>                    PREREQCOREQ                  </prereq_coreq>
        <resource_and_skill_set_requirements> RESOURSKILLSET           </resource_and_skill_set_requirements>
        <vendor_considerations>           VENDORCONSID                 </vendor considerations>
        <cross_sell>                      CROSSELL                     </cross_sell>
        <up_sell>                         UPSELL                       </up_sell>
        <competitive_offerings>           COMPETITIVEOF                </competitive_offerings>
        <strength_and_weakness_summary>   STRENGTHWEAKNESS             </strength_and_weakness_summary>
        <why_ibm>                         WHYIBM                       </why_ibm>
        <bp_available_infrastructure>     BPAVAILINFRASTRCT            </bp_available_infrastructure>
        <bp_faqs>                         BPFAQS                       </bp_faqs>
        <purchasing_process>             PURCHASINGPROCESS             </purchasing_process>
        <executive_presentations>         EXECPRESENTATION  file name  </executive_presentations>
        <customer_specification_sheet>    BPSPECSHEET       file name  </customer_specification_sheet>
        <bp_customer_information_sheet>   CUSTINFOSHEET     file name  </bp_customer_information_sheet>
    </igs_service_offering>

    <price_financing_information> PRICEFININFO->SOFPRICE
        <bp_incentive_for_distributors>   BPDISTRIBINCENTIVE           </bp_incentive_for_distributors>
        <bp_incentive_for_tier1>          BPTIER1INCENTIVE             </bp_incentive_for_tier1>
        <bp_incentive_for_tier2>          BPTIER2INCENTIVE             </bp_incentive_for_tier2>
        <charges>                         CHARGES                      </charges>
        <pricing_details>                 PRICINGDETAILS               </pricing_details>
    </price_financing_information>

    <channel>  CHANNEL ->SOFCHANNEL
        <channel_name>                    CHANNELNAME                  </channel_name>
        <channel_type>                    CHANNELTYPE                  </channel_type>
        <country_list>                    COUNTRYLIST                  </country_list>
        <supplier>                        SUPPLIER                     </supplier>
    </channel>

    <service_offering_sales_contact> OP<-SOFSALESCNTCTOP<-SOF
        <first_name>                FIRSTNAME   </first_name>
        <middle_name>               MIDDLENAME  </middle_name>
        <last_name>                 LASTNAME    </last_name>
        <job_title>                 JOBTITLE    </job_title>
        <internet_address>          USERTOKEN   </internet_address>
        <general_area_selection>    onRel GENAREASELECTION      </general_area_selection>
        <country_list>              onRel COUNTRYLIST           </country_list>
    </service_offering_sales_contact>

    <bp_process> BPPROCESS association SOFBPPROCESSA
        <bp_process_name>           BPPROCESSNAME                      </bp_process_name>
        <bp_process_description>    BPPROCESSDESCRIPTION               </bp_process_description>
    </bp_process>

    <bp_direct_mail_program> BPDMP association SOFBPDMPA
        <bp_dmp_name>               DMPNAME                            </bp_dmp_name>
        <bp_dmp_purpose>            DMPPURPOSE                         </bp_dmp_purpose>
        <bp_dmp_usage_criteria>     USAGECRITERIA                      </bp_dmp_usage_criteria>
    </bp_direct_mail_program>

    <bp_promotion>   BPPROMOTION association SOFPROMOTIONA
        <bp_promotion_name>         PROMONAME                          </bp_promotion_name>
        <bp_promotion_criteria>     PROMOCRITERIA                      </bp_promotion_criteria>
        <bp_promotion_purpose>      PROMOPURPOSE                       </bp_promotion_purpose>
    </bp_promotion>

    <offering_related_offering> SOF<-SOFRELSOF<-SOF
        <offering_identification_number>   OFIDNUMBER                  </offering_identification_number>
        <marketing_name>            MKTGNAME                           </marketing_name>
        <type>                      onRel TYPE                         </type>
        <benefit>                   onRel BENEFIT                      </benefit>
    </offering_related_offering>

    <offering_related_component> CMPNT<-SOFRELCMPNT<-SOF
        <component_id>              COMPONENTID                        </component_id>
        <marketing_name>            MKTGNAME                           </marketing_name>
        <type>                      onRel TYPE                         </type>
        <benefit>                   onRel BENEFIT                      </benefit>
    </offering_related_component>

    <offering_related_features>  FEATURE<-SOFRELFEATURE<-SOF
        <feature_number>            FEATURENUMBER                      </feature_number>
        <marketing_name>            MKTGNAME                           </marketing_name>
        <type>                      onRel TYPE                         </type>
        <benefit>                   onRel BENEFIT                      </benefit>
    </offering_related_features>

    <offering_dev_project>OFDEVLPROJ association (SOFPRA)
        <project_number>    PROJNUMBER          </project_number>
    </offering_dev_project>

    <announcement> ANNOUNCEMENT association (ANNAVAILA)
        <announcement_number>       ANNNUMBER                          </announcement_number>
        <announcement_title>        ANNTITLE                           </announcement_title>
        <announcement_type>         ANNTYPE                            </announcement_type>
        <announce_date>             ANNDATE                            </announce_date>
        <at_a_glance>               ATAGLANCE                          </at_a_glance>
        <announcement_availability> AVAIL<-SOFAVAIL<-SOF
            <availability_type>     AVAILTYPE                          </availability_type>
            <effective_date>        EFFECTIVEDATE                      </effective_date>
            <general_area_selection> GENAREASELECTION                  </general_area_selection>
            <country_list>          COUNTRYLIST                        </country_list>
        </announcement_availability>
    </announcement>
    </eannounce_igs_service_offering>*/
    /**
    * IGS Service Offering Template
    * @param dbCurrent  Database object
    * @param profile  Profile for user
    * @param list  EntityList with data used to populate the xml template
    * @param debug  output debug info if true
    * @returns String
    */
    private static String getSOFDownload(Database dbCurrent, Profile profile,EntityList list, boolean debug)
    throws MiddlewareException
    {
        String rootName = "eannounce_igs_service_offering";
        String header = getIGSXMLHeader(dbCurrent, profile, rootName);
        StringBuffer sb = new StringBuffer(header);

        // output SOF root entity info
        sb.append(getXMLentity(list.getParentEntityGroup(), "igs_service_offering", SOF_ATTR,debug));

        // output PRICEFININFO info
        sb.append(getXMLentity(list.getEntityGroup("PRICEFININFO"), "price_financing_information", PRICEFININFO_ATTR,debug));

        // output CHANNEL info
        sb.append(getXMLentity(list.getEntityGroup("CHANNEL"), "channel", CHANNEL_ATTR,debug));

        // output SALES info
        sb.append(getXMLChildEntity(list.getEntityGroup("SOFSALESCNTCTOP"), "service_offering_sales_contact",
                OP_ATTR, SALESCNTCTOP_ATTR, debug));

        // output BPPROCESS info
        sb.append(getXMLentity(list.getEntityGroup("BPPROCESS"), "bp_process", BPPROCESS_ATTR,debug));

        // output BPDMP info
        sb.append(getXMLentity(list.getEntityGroup("BPDMP"), "bp_direct_mail_program", BPDMP_ATTR,debug));

        // output BPDMP info
        sb.append(getXMLentity(list.getEntityGroup("BPPROMOTION"), "bp_promotion", BPPROMOTION_ATTR,debug));

        // output offering related offering
        sb.append(getXMLChildEntity(list.getEntityGroup("SOFRELSOF"), "offering_related_offering",
                RELSOF_SOF_ATTR, OF_REL_ATTR, debug));

        // output offering related component
        sb.append(getXMLChildEntity(list.getEntityGroup("SOFRELCMPNT"), "offering_related_component",
                RELCMPNT_CMPNT_ATTR, OF_REL_ATTR, debug));

        // output offering related features
        sb.append(getXMLChildEntity(list.getEntityGroup("SOFRELFEATURE"), "offering_related_features",
                RELFEATURE_FEATURE_ATTR, OF_REL_ATTR, debug));

        // output OFDEVLPROJ info
        sb.append(getXMLentity(list.getEntityGroup("OFDEVLPROJ"), "offering_dev_project", new String[]{"PROJNUMBER"},debug));

        // output ANNOUNCEMENT info
        sb.append(getXMLAnnouncement(list.getEntityGroup("ANNOUNCEMENT"), debug));

        sb.append("</"+rootName+">");
        return sb.toString();
    }

    /********************************************************************************
    * Fill in XML template for CMPNT.
    * IGS Component Offering Template

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE eannounce_igs_component_offering  >
    <eannounce_igs_component_offering>
    <date_of_report>            </date_of_report>
    <created_by_user>           </created_by_user>
    <igs_parent_service_offering> SOF thru SOFCMPNT
        <offering_identification_number> OFIDNUMBER                    </offering_identification_number>
        <marketing_name>                 MKTGNAME                      </marketing_name>
        <line_of_business>               LOB                           </line_of_business>
        <offering_catagory>              SOFCATEGORY                   </offering_catagory>
    </igs_parent_service_offering>

    <igs_component_offering> CMPNT
        <component_id>                   COMPONENTID                   </component_id>
        <marketing_name>                 MKTGNAME                      </marketing_name>
        <business_and_technology_issue>  BUSTECHISSUE                  </business_and_technology_issue>
        <industry_solutions>             INDUSTRYSOLUTIONS             </industry_solutions>
        <it_capability>                  ITCAPABILITY                  </it_capability>
        <overview_abstract>              OVERVIEWABSTRACT              </overview_abstract>
        <description>                    DESCRIPTION                   </description>
        <features_and_benefits>          FEATUREBENEFIT                </features_and_benefits>
        <differentiating_features_and_benefits> DIFFEATURESBENEFITS    </differentiating_features_and_benefits>
        <marketing_strategy>             MKTGSTRATEGY                  </marketing_strategy>
        <other_marketing_info>           OTHERMKTGINFO                 </other_marketing_info>
        <customer_wants_and_needs>       CUSTWANTSNEEDS                </customer_wants_and_needs>
        <customer_candidate_guidelines>  CUSTCANDGUIDELINES            </customer_candidate_guidelines>
        <customer_restriction>           CUSTRESTRICTIONS              </customer_restrictions>
        <handling_objections>            HANDOBJECTIONS                </handling_objections>
        <sales_action_required>          SALESACTREQ                   </sales_action_required>
        <sales_approach>                 SALESAPPROACH                 </sales_approach>
        <delivery_method>                DELIVERYMETHOD                </delivery_method>
        <prereq_coreq>                   PREREQCOREQ                   </prereq_coreq>
        <resource_and_skill_set_requirements> RESOURSKILLSET           </resource_and_skill_set_requirements>
        <vendor_considerations>          ?????                         </vendor considerations>
        <cross_sell>                     CROSSELL                      </cross_sell>
        <up_sell>                        UPSELL                        </up_sell>
        <competitive_offerings>          COMPETITIVEOF                 </competitive_offerings>
        <strength_and_weakness_summary>  STRENGTHWEAKNESS              </strength_and_weakness_summary>
        <why_ibm>                        WHYIBM                        </why_ibm>
        <bp_available_infrastructure>    BPAVAILINFRASTRCT             </bp_available_infrastructure>
        <bp_faqs>                        ?????                         </bp_faqs>
        <purchasing_process>             PURCHASINGPROCESS             </purchasing_process>
        <executive_presentations>        EXECPRESENTATION  file name   </executive_presentations>
        <customer_specification_sheet>   BPSPECSHEET       file name   </customer_specification_sheet>
        <bp_customer_information_sheet>  CUSTINFOSHEET     file name   </bp_customer_information_sheet>
    </igs_component_offering>

    <price_financing_information> PRICEFININFO->CMPNTPRICE
        <bp_incentive_for_distributors>  BPDISTRIBINCENTIVE            </bp_incentive_for_distributors>
        <bp_incentive_for_tier1>         BPTIER1INCENTIVE              </bp_incentive_for_tier1>
        <bp_incentive_for_tier2>         BPTIER2INCENTIVE              </bp_incentive_for_tier2>
        <charges>                        CHARGES                       </charges>
        <pricing_details>                PRICINGDETAILS                </pricing_details>
    </price_financing_information>

    <channel> CHANNEL->CMPNTCHANNEL
        <channel_name>                   CHANNELNAME                   </channel_name>
        <channel_type>                   CHANNELTYPE                   </channel_type>
        <country_list>                   COUNTRYLIST                   </country_list>
        <supplier>                       SUPPLIER                      </supplier>
    </channel>

    <component_sales_contact> OP->CMPNTSALESCNTCTOP
        <first_name>                    FIRSTNAME                      </first_name>
        <middle_name>                   MIDDLENAME                     </middle_name>
        <last_name>                     LASTNAME                       </last_name>
        <job_title>                     JOBTITLE                       </job_title>
        <internet_address>              USERTOKEN                      </internet_address>
        <general_area_selection>    onRel GENAREASELECTION      </general_area_selection>
        <country_list>              onRel COUNTRYLIST           </country_list>
    </component_sales_contact>

    <bp_process> BPPROCESS association CMPNTBPPROCESSA
        <bp_process_name>           BPPROCESSNAME                      </bp_process_name>
        <bp_process_description>    BPPROCESSDESCRIPTION               </bp_process_description>
    </bp_process>

    <bp_direct_mail_program> BPDMP association CMPNTBPDMPA
        <bp_dmp_name>               DMPNAME                            </bp_dmp_name>
        <bp_dmp_purpose>            DMPPURPOSE                         </bp_dmp_purpose>
        <bp_dmp_usage_criteria>     USAGECRITERIA                      </bp_dmp_usage_criteria>
    </bp_direct_mail_program>

    <bp_promotion>   BPPROMOTION association CMPNTPROMOTIONA
        <bp_promotion_name>         PROMONAME                          </bp_promotion_name>
        <bp_promotion_criteria>     PROMOCRITERIA                      </bp_promotion_criteria>
        <bp_promotion_purpose>      PROMOPURPOSE                       </bp_promotion_purpose>
    </bp_promotion>

    <offering_related_component> SOF->SOFRELCMPNT
        <offering_identification_number>   OFIDNUMBER                  </offering_identification_number>
        <marketing_name>            MKTGNAME                           </marketing_name>
        <type>                      onRel TYPE                         </type>
        <benefit>                   onRel BENEFIT                      </benefit>
    </offering_related_component>

    <component_related_component> CMPNT->CMPNTRELCMPNT
        <component_id>              COMPONENTID                        </component_id>
        <marketing_name>            MKTGNAME                           </marketing_name>
        <type>                      onRel TYPE                         </type>
        <benefit>                   onRel BENEFIT                      </benefit>
    </component_related_component>

    <component_related_features> FEATURE->CMPNTRELFEATURE
        <feature_number>            FEATURENUMBER                      </feature_number>
        <marketing_name>            MKTGNAME                           </marketing_name>
        <type>                      onRel TYPE                         </type>
        <benefit>                   onRel BENEFIT                      </benefit>
    </component_related_features>

    <offering_dev_project>OFDEVLPROJ association (CMPNTPRA)
        <project_number>    PROJNUMBER          </project_number>
    </offering_dev_project>

    <announcement> ANNOUNCEMENT association (ANNAVAILA)
        <announcement_number>       ANNNUMBER                          </announcement_number>
        <announcement_title>        ANNTITLE                           </announcement_title>
        <announcement_type>         ANNTYPE                            </announcement_type>
        <announce_date>             ANNDATE                            </announce_date>
        <at_a_glance>               ATAGLANCE                          </at_a_glance>
        <announcement_availability> AVAIL ->CMPNTAVAIL
            <availability_type>     AVAILTYPE                          </availability_type>
            <effective_date>        EFFECTIVEDATE                      </effective_date>
            <general_area_selection> GENAREASELECTION                  </general_area_selection>
            <country_list>          COUNTRYLIST                        </country_list>
        </announcement_availability>
    </announcement>
    </eannounce_igs_component_offering>*/

    /********************************************************************************
    * Fill in XML template for CMPNT.
    * IGS Component Offering Template    * @param dbCurrent  Database object
    * @param profile  Profile for user
    * @param list  EntityList with data used to populate the xml template
    * @param debug  output debug info if true
    * @returns String
    */
    private static String getCMPNTDownload(Database dbCurrent, Profile profile,EntityList list, boolean debug)
        throws MiddlewareException
    {
        String rootName = "eannounce_igs_component_offering";
        String header = getIGSXMLHeader(dbCurrent, profile, rootName);
        StringBuffer sb = new StringBuffer(header);

        // get the entity
        EntityItem theEntityItem = list.getParentEntityGroup().getEntityItem(0);

        // output parent SOF entity info, must go thru SOFCMPNT
        Vector sofVct = PokUtils.getAllLinkedEntities(theEntityItem, "SOFCMPNT", "SOF");
        sb.append(getXMLentity(sofVct, "igs_parent_service_offering", PARENTSOF_ATTR,debug));
        sofVct.clear();

        // output CMPNT root entity info
        sb.append(getXMLentity(list.getParentEntityGroup(), "igs_component_offering", CMPNT_ATTR,debug));

        // output PRICEFININFO info
        sb.append(getXMLentity(list.getEntityGroup("PRICEFININFO"), "price_financing_information", PRICEFININFO_ATTR,debug));

        // output CHANNEL info
        sb.append(getXMLentity(list.getEntityGroup("CHANNEL"), "channel", CHANNEL_ATTR,debug));

        // output SALES info
        sb.append(getXMLChildEntity(list.getEntityGroup("CMPNTSALESCNTCTOP"), "component_sales_contact",
                OP_ATTR, SALESCNTCTOP_ATTR, debug));

        // output BPPROCESS info
        sb.append(getXMLentity(list.getEntityGroup("BPPROCESS"), "bp_process", BPPROCESS_ATTR,debug));

        // output BPDMP info
        sb.append(getXMLentity(list.getEntityGroup("BPDMP"), "bp_direct_mail_program", BPDMP_ATTR,debug));

        // output BPDMP info
        sb.append(getXMLentity(list.getEntityGroup("BPPROMOTION"), "bp_promotion", BPPROMOTION_ATTR,debug));

        // output offering related component
        sb.append(getXMLChildEntity(list.getEntityGroup("CMPNTRELSOF"), "offering_related_component",
                RELSOF_SOF_ATTR, OF_REL_ATTR, debug));

        // output component related component
        sb.append(getXMLChildEntity(list.getEntityGroup("CMPNTRELCMPNT"), "component_related_component",
                RELCMPNT_CMPNT_ATTR, OF_REL_ATTR, debug));

        // output component related features
        sb.append(getXMLChildEntity(list.getEntityGroup("CMPNTRELFEATURE"), "component_related_features",
                RELFEATURE_FEATURE_ATTR, OF_REL_ATTR, debug));

        // output OFDEVLPROJ info
        sb.append(getXMLentity(list.getEntityGroup("OFDEVLPROJ"), "offering_dev_project", new String[]{"PROJNUMBER"},debug));

        // output ANNOUNCEMENT info
        sb.append(getXMLAnnouncement(list.getEntityGroup("ANNOUNCEMENT"),  debug));

        sb.append("</"+rootName+">");
        return sb.toString();
    }

    /********************************************************************************
    * Fill in XML template for FEATURE.
    * IGS Feature Offering Template

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE eannounce_igs_feature_offering >
    <eannounce_igs_feature_offering>
    <date_of_report>               </date_of_report>
    <created_by_user>           </created_by_user>
    <igs_parent_service_offering> SOF->SOFCMPNT from CMPNT thru CMPNTFEATURE
        <offering_identification_number> OFIDNUMBER                    </offering_identification_number>
        <marketing_name>                 MKTGNAME                      </marketing_name>
        <line_of_business>               LOB                           </line_of_business>
        <offering_catagory>              SOFCATEGORY                   </offering_catagory>
    </igs_service_offering>

    <igs_parent_component_offering> CMPNT->CMPNTFEATURE
        <component_id>              COMPONENTID                        </component_id>
        <marketing_name>            MKTGNAME                           </marketing_name>
    </igs_parent_component_offering>

    <igs_feature_offering> FEATURE
        <feature_number>            FEATURENUMBER                      </feature_number>
        <marketing_name>            MKTGNAME                           </marketing_name>
        <business_and_technology_issue>  BUSTECHISSUE                  </business_and_technology_issue>
        <industry_solutions>        INDUSTRYSOLUTIONS                  </industry_solutions>
        <it_capability>             ITCAPABILITY                       </it_capability>
        <overview_abstract>         OVERVIEWABSTRACT                   </overview_abstract>
        <description>               DESCRIPTION                        </description>
        <features_and_benefits>     FEATUREBENEFIT                     </features_and_benefits>
        <differentiating_features_and_benefits> DIFFEATURESBENEFITS    </differentiating_features_and_benefits>
        <marketing_strategy>        MKTGSTRATEGY                       </marketing_strategy>
        <other_marketing_info>      OTHERMKTGINFO                      </other_marketing_info>
        <customer_wants_and_needs>  CUSTWANTSNEEDS                     </customer_wants_and_needs>
        <customer_candidate_guidelines>  CUSTCANDGUIDELINES            </customer_candidate_guidelines>
        <customer_restriction>      CUSTRESTRICTIONS                   </customer_restrictions>
        <handling_objections>       HANDOBJECTIONS                     </handling_objections>
        <sales_action_required>     SALESACTREQ                        </sales_action_required>
        <sales_approach>            SALESAPPROACH                      </sales_approach>
        <delivery_method>           DELIVERYMETHOD                     </delivery_method>
        <prereq_coreq>              PREREQCOREQ                        </prereq_coreq>
        <resource_and_skill_set_requirements> RESOURSKILLSET           </resource_and_skill_set_requirements>
        <cross_sell>                CROSSELL                           </cross_sell>
        <up_sell>                   UPSELL                             </up_sell>
        <competitive_offerings>     COMPETITIVEOF                      </competitive_offerings>
        <strength_and_weakness_summary> STRENGTHWEAKNESS               </strength_and_weakness_summary>
        <why_ibm>                   WHYIBM                             </why_ibm>
    </igs_feature_offering>

    <price_financing_information> PRICEFININFO->FEATUREPRICE
        <bp_incentive_for_distributors>   BPDISTRIBINCENTIVE           </bp_incentive_for_distributors>
        <bp_incentive_for_tier1>          BPTIER1INCENTIVE             </bp_incentive_for_tier1>
        <bp_incentive_for_tier2>          BPTIER2INCENTIVE             </bp_incentive_for_tier2>
        <charges>                         CHARGES                      </charges>
        <pricing_details>                 PRICINGDETAILS               </pricing_details>
    </price_financing_information>

    <channel>  CHANNEL->FEATURECHANNEL
        <channel_name>                    CHANNELNAME                  </channel_name>
        <channel_type>                    CHANNELTYPE                  </channel_type>
        <country_list>                    COUNTRYLIST                  </country_list>
        <supplier>                        SUPPLIER                     </supplier>
    </channel>
    <feature_sales_contact>OP->FEATRSALESCNTCTOP
        <first_name>                FIRSTNAME   </first_name>
        <middle_name>               MIDDLENAME  </middle_name>
        <last_name>                 LASTNAME    </last_name>
        <job_title>                 JOBTITLE    </job_title>
        <internet_address>          USERTOKEN   </internet_address>
        <general_area_selection>    onRel GENAREASELECTION      </general_area_selection>
        <country_list>              onRel COUNTRYLIST           </country_list>
    </feature_sales_contact>

    <offering_related_features>SOF->SOFRELFEATURE
        <offering_identification_number>   OFIDNUMBER                  </offering_identification_number>
        <marketing_name>            MKTGNAME                           </marketing_name>
        <type>                      onRel TYPE                         </type>
        <benefit>                   onRel BENEFIT                      </benefit>
    </offering_related_features>

    <component_related_features>CMPNT->CMPNTRELFEATURE
        <component_id>              COMPONENTID                        </component_id>
        <marketing_name>            MKTGNAME                           </marketing_name>
        <type>                      onRel TYPE                         </type>
        <benefit>                   onRel BENEFIT                      </benefit>
    </component_related_features>

    <feature_related_features> FEATURE->FEATURERELFEATURE
        <feature_number>            FEATURENUMBER                      </feature_number>
        <marketing_name>            MKTGNAME                           </marketing_name>
        <type>                      onRel TYPE                         </type>
        <benefit>                   onRel BENEFIT                      </benefit>
    </feature_related_features>

    <offering_dev_project>OFDEVLPROJ association (FEATUREPRA)
        <project_number>    PROJNUMBER          </project_number>
    </offering_dev_project>

    <announcement> ANNOUNCEMENT association (ANNAVAILA)
        <announcement_number>       ANNNUMBER                          </announcement_number>
        <announcement_title>        ANNTITLE                           </announcement_title>
        <announcement_type>         ANNTYPE                            </announcement_type>
        <announce_date>             ANNDATE                            </announce_date>
        <at_a_glance>               ATAGLANCE                          </at_a_glance>
        <announcement_availability> AVAIL ->FEATUREAVAIL
            <availability_type>     AVAILTYPE                          </availability_type>
            <effective_date>        EFFECTIVEDATE                      </effective_date>
            <general_area_selection> GENAREASELECTION                  </general_area_selection>
            <country_list>          COUNTRYLIST                        </country_list>
        </announcement_availability>
    </announcement>
    </eannounce_igs_feature_offering>*/

    /********************************************************************************
    * Fill in XML template for FEATURE.
    * IGS Feature Offering Template
    * @param dbCurrent  Database object
    * @param profile  Profile for user
    * @param list  EntityList with data used to populate the xml template
    * @param debug  output debug info if true
    * @returns String
    */
    private static String getFEATUREDownload(Database dbCurrent, Profile profile,EntityList list, boolean debug)
    throws MiddlewareException
    {
        String rootName = "eannounce_igs_feature_offering";
        String header = getIGSXMLHeader(dbCurrent, profile, rootName);
        StringBuffer sb = new StringBuffer(header);

        // get the entity
        EntityItem theEntityItem = list.getParentEntityGroup().getEntityItem(0);

        // get parent CMPNT
        Vector cmpntVct = PokUtils.getAllLinkedEntities(theEntityItem, "CMPNTFEATURE", "CMPNT");

        // output grandparent SOF entity info, must go thru SOFCMPNT
        Vector sofVct = PokUtils.getAllLinkedEntities(cmpntVct, "SOFCMPNT", "SOF");
        sb.append(getXMLentity(sofVct, "igs_parent_service_offering", PARENTSOF_ATTR,debug));
        sofVct.clear();

        // output parent CMPNT
        sb.append(getXMLentity(cmpntVct, "igs_parent_component_offering", PARENTCMPNT_ATTR,debug));
        cmpntVct.clear();

        // output FEATURE root entity info
        sb.append(getXMLentity(list.getParentEntityGroup(), "igs_feature_offering", FEATURE_ATTR,debug));

        // output PRICEFININFO info
        sb.append(getXMLentity(list.getEntityGroup("PRICEFININFO"), "price_financing_information", PRICEFININFO_ATTR,debug));

        // output CHANNEL info
        sb.append(getXMLentity(list.getEntityGroup("CHANNEL"), "channel", CHANNEL_ATTR,debug));

        // output SALES info
        sb.append(getXMLChildEntity(list.getEntityGroup("FEATRSALESCNTCTOP"), "feature_sales_contact",
                OP_ATTR, SALESCNTCTOP_ATTR, debug));

        // output offering related features
        sb.append(getXMLChildEntity(list.getEntityGroup("FEATURERELSOF"), "offering_related_features",
                RELSOF_SOF_ATTR, OF_REL_ATTR, debug));

        // output component related features
        sb.append(getXMLChildEntity(list.getEntityGroup("FEATURERELCMPNT"), "component_related_features",
                RELCMPNT_CMPNT_ATTR, OF_REL_ATTR, debug));

        // output feature related features
        sb.append(getXMLChildEntity(list.getEntityGroup("FEATURERELFEATURE"), "feature_related_features",
                RELFEATURE_FEATURE_ATTR, OF_REL_ATTR, debug));

        // output OFDEVLPROJ info
        sb.append(getXMLentity(list.getEntityGroup("OFDEVLPROJ"), "offering_dev_project", new String[]{"PROJNUMBER"},debug));

        // output ANNOUNCEMENT info
        sb.append(getXMLAnnouncement(list.getEntityGroup("ANNOUNCEMENT"), debug));

        sb.append("</"+rootName+">");
        return sb.toString();
    }

    /********************************************************************************
    * Get XML tag for the specified attribute
    *
    * @param eItem      EntityItem with data used to populate the xml template
    * @param attrCode   String with attribute code
    * @returns String
    */
    private static String getAttrTagValue(EntityItem eItem,String attrCode)
    {
        EANMetaAttribute metaAttr = eItem.getEntityGroup().getMetaAttribute(attrCode);
        String value = "";
        EANAttribute attr = null;
        String tag = null;
        String tagValue=null;
        if (metaAttr==null) {
            tagValue="<ERROR>"+attrCode+" NOT found in "+eItem.getEntityType()+" META data.</ERROR>"+NEWLINE;
        }
        else {
            tag = metaAttr.getActualLongDescription();
            //attr name: tolowercase and also replace / with _ as well as blank with _
            tag = tag.toLowerCase().replace(' ','_');
            tag = tag.replace('/','_');
            tag = tag.replace('-','_');

            attr = eItem.getAttribute(attrCode);

            // if this is a blob, just get the file name
            if (attr instanceof EANBlobAttribute)
            {
                EANBlobAttribute bAtt = (EANBlobAttribute) attr;
                // getBlobFileName() is a generated name, not the name of the file itself!!
                // the JUI truncates the file name to a max of 26 chars, not including file extension of ".lwp"
                value = bAtt.toString();
            }
            else
            {
                // multiflags are to be comma delimited
                value = PokUtils.getAttributeValue(eItem, attrCode, ", ", "",false);

                //ITSCMPNTCATNAME
                /* CR0220045238
                Agreed implementation is to concatenate the ITS category name (Offering Category) with the marketing name
                (Offering Component Name).  Since non-TSS groups do not use this category, their marketing names should continue to
                display properly.  This change affects all Offering Components, whether a parent component, a related component or
                cross sell and up sell.
                */
                if (eItem.getEntityType().equals("CMPNT")&& attrCode.equals("MKTGNAME"))
                {
                    String catName = PokUtils.getAttributeValue(eItem, "ITSCMPNTCATNAME", ", ", "",false);
                    value = catName+" "+value;
                }
                // FEATUREBENEFIT is now type X, add check for type X and don't wrap it
                if (value.length()>0 && IGS_ATTR_WRAP_VCT.contains(attrCode) &&
                    (!metaAttr.getAttributeType().equals("X"))) {
                    value = "<![CDATA["+value+"]]>";
                }
            }

            tagValue= "<"+tag+">"+value+"</"+tag+">"+NEWLINE;
        }
        return tagValue;
    }

    /********************************************************************************
    * Get today's date
    *
    * @param dbCurrent  Database object used to get server date
    * @returns String
    */
    private static String getNow(Database dbCurrent) throws MiddlewareException
    {
        DatePackage dbNow = dbCurrent.getDates();
        String strNow = dbNow.getNow();
        return strNow.substring(0,10);
    }

    /********************************************************************************
    * Fill in IGS XML template header
    *
    * @param dbCurrent  Database object used to get server date
    * @param profile    Profile for user info
    * @param rootName   String for root of XML document
    * @returns String
    */
    private static String getIGSXMLHeader(Database dbCurrent, Profile profile, String rootName)
    throws MiddlewareException
    {
        String now = getNow(dbCurrent);
        String user = profile.getOPName();
        // get resource bundle
        /*
        String resourceName = "IGS_ReportNls";  // need IGS_ReportNls.properties file
        ResourceBundle bundle = HVECUtils.getResourceBundle(profile, resourceName);
        String confMsg = "IBM Confidential until Announced";
        try{
            confMsg = bundle.getString("Label_IbmConfUntilAnn"); // CR0227041738
        }catch(Exception e){ // really want to ignore this.. but jtest won't
            String test = e.getMessage();
            if (test!=null) {
                test=null;
            }
        }*/
        return ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+NEWLINE+
            "<!DOCTYPE "+rootName+" [<!ENTITY nbsp \"&#160;\">]>"+NEWLINE+
            "<"+rootName+">"+NEWLINE+
            //what is the standard?         "<security>"+confMsg+"</security>"+NEWLINE+ // CR0227041738
            "<date_of_report>"+now+"</date_of_report>"+NEWLINE+
            "<created_by_user>"+user+"</created_by_user>"+NEWLINE);
    }

    /********************************************************************************
    * Fill in XML tag for an entity with all specified attributes, no nesting of entities done here
    *
    * @param eVct  Vector with data used to populate the xml template
    * @param tagName String with tag name for this entity type
    * @param attrCodeTbl Array of attribute codes
    * @param debug boolean if true, output debug info
    * @returns String
    */
    private static String getXMLentity(Vector eVct, String tagName, String[] attrCodeTbl,boolean debug)
    {
        StringBuffer sb = new StringBuffer();
        for(int ie=0; ie<eVct.size();ie++)
        {
            EntityItem entityItem = (EntityItem)eVct.elementAt(ie);
            sb.append("<"+tagName+(debug?" eid=\""+entityItem.getEntityID()+"\"":"")+">"+NEWLINE);  // start entity
            for (int i=0; i<attrCodeTbl.length; i++) {
                sb.append("   "+getAttrTagValue(entityItem,attrCodeTbl[i])); }
            sb.append("</"+tagName+">"+NEWLINE+NEWLINE); // end entity
        }
        return sb.toString();
    }

    /********************************************************************************
    * Fill in XML tag for an entity with all specified attributes, no nesting of entities done here
    *
    * @param eGrp  EntityGroup with data used to populate the xml template
    * @param tagName String with tag name for this entity type
    * @param attrCodeTbl Array of attribute codes
    * @param debug boolean if true, output debug info
    * @returns String
    */
    private static String getXMLentity(EntityGroup eGrp, String tagName, String[] attrCodeTbl,boolean debug)
    {
        StringBuffer sb = new StringBuffer();
        if (eGrp==null) {
            sb.append("<ERROR> null group for "+tagName+"</ERROR>"+NEWLINE);
        }
        else
        {
            for(int ie=0; ie<eGrp.getEntityItemCount();ie++)
            {
                EntityItem entityItem = eGrp.getEntityItem(ie);
                sb.append("<"+tagName+(debug?" eid=\""+entityItem.getEntityID()+"\"":"")+">"+NEWLINE);  // start entity
                for (int i=0; i<attrCodeTbl.length; i++) {
                    sb.append("   "+getAttrTagValue(entityItem,attrCodeTbl[i])); }
                sb.append("</"+tagName+">"+NEWLINE+NEWLINE); // end entity
            }
        }
        return sb.toString();
    }

    /********************************************************************************
    * Fill in XML tag for an entity with all specified attributes and attributes on the relator
    *
    * @param relGrp  EntityGroup with relators
    * @param tagName String with tag name for this entity type
    * @param attrTbl Array of destination attribute codes
    * @param relAttrTbl Array of relator attribute codes
    * @param debug boolean if true, output debug info
    * @returns String
    */
    private static String getXMLChildEntity(EntityGroup relGrp, String tagName, String[] attrTbl,
            String[]relAttrTbl, boolean debug)
    {
        StringBuffer sb = new StringBuffer();
        if (relGrp==null) {
            sb.append("<ERROR> null relator group for "+tagName+"</ERROR>"+NEWLINE);
        }
        else {
            // get all linked eType entities thru the relGrp
            for(int ie=0; ie<relGrp.getEntityItemCount();ie++)
            {
                EntityItem relItem = relGrp.getEntityItem(ie);

                // this relator is used as a downlink,
                for (int ui=0; ui<relItem.getDownLinkCount(); ui++)
                {
                    EANEntity entityLink = relItem.getDownLink(ui);
                    sb.append("<"+tagName+(debug?" eid=\""+entityLink.getEntityID()+"\"":"")+">"+NEWLINE);  // start entity
                    for (int i=0; i<attrTbl.length; i++) {
                        sb.append("   "+getAttrTagValue((EntityItem)entityLink,attrTbl[i])); }
                    // output relator attributes
                    for (int i=0; i<relAttrTbl.length; i++) {
                        sb.append("   "+getAttrTagValue(relItem,relAttrTbl[i])); }

                    sb.append("</"+tagName+">"+NEWLINE+NEWLINE); // end entity
                }
            }
        }

        return sb.toString();
    }

    /********************************************************************************
    * Fill in XML tag for ANNOUNCEMENT entity with AVAILs
    *
    * @param eGrp  EntityGroup with ANNOUNCEMENT entities
    * @param debug boolean if true, output debug info
    * @returns String
    */
    private static String getXMLAnnouncement(EntityGroup eGrp, boolean debug)
    {
        StringBuffer sb = new StringBuffer();
        if (eGrp==null) {
            sb.append("<ERROR> null group for announcement</ERROR>"+NEWLINE);
        }
        else {
            for(int ie=0; ie<eGrp.getEntityItemCount();ie++)
            {
                EntityItem annItem = eGrp.getEntityItem(ie); // ANNOUNCEMENT
                Vector availVct = null;
                sb.append("<announcement"+(debug?" eid=\""+annItem.getEntityID()+"\"":"")+">"+NEWLINE);  // start entity
                for (int i=0; i<ANNOUNCEMENT_ATTR.length; i++) {
                    sb.append("   "+getAttrTagValue(annItem,ANNOUNCEMENT_ATTR[i])); }

                // get AVAILSs
                availVct = PokUtils.getAllLinkedEntities(annItem, "AVAILANNA", "AVAIL");
                for (int v=0; v<availVct.size(); v++)
                {
                    EntityItem availItem = (EntityItem)availVct.elementAt(v);
                    sb.append("   <announcement_availability"+(debug?" eid=\""+availItem.getEntityID()+"\"":"")+">"+NEWLINE);  // start entity
                    for (int i=0; i<AVAIL_ATTR.length; i++) {
                        sb.append("      "+getAttrTagValue(availItem,AVAIL_ATTR[i])); }

                    sb.append("   </announcement_availability>"+NEWLINE); // end entity
                }

                availVct.clear();
                sb.append("</announcement>"+NEWLINE+NEWLINE); // end entity
            }
        }

        return sb.toString();
    }

    /********************************************************************************
    * CR0202045351 Set text attribute value the old way, bypass all business rule checks and role checks
    * Use server timestamp for current time
    * @param dbCurrent  Database object used to get server date
    * @param profile    Profile for user info
    * @param list       EntityList with root entity to update
    * @param attrCode   String with attribute code to set to current time
    */
    private static void setXMLDownloadTimeStamp(Database dbCurrent, Profile profile, EntityList list,
        String attrCode)
        throws COM.ibm.opicmpdh.middleware.MiddlewareException, java.sql.SQLException
    {
        EntityItem entityItem = list.getParentEntityGroup().getEntityItem(0);  // get root entity

        ReturnEntityKey rek = new ReturnEntityKey(entityItem.getEntityType(), entityItem.getEntityID(), true);
        DatePackage dbNow = dbCurrent.getDates();
        String strNow = dbNow.getNow();
        String strForever = dbNow.getForever();
        String attrValue = strNow;  // set to attribute to server time using ISO format '2004-02-03-14.43.08.496049'
        ControlBlock cbOn = new ControlBlock(strNow,strForever,strNow,strForever, profile.getOPWGID(), profile.getTranID());
        Text sf = new Text(profile.getEnterprise(), rek.getEntityType(), rek.getEntityID(), attrCode, attrValue, 1, cbOn);
        Vector vctAtts = new Vector();
        Vector vctReturnsEntityKeys = new Vector();
        try{
            vctAtts.addElement(sf);
            rek.m_vctAttributes = vctAtts;
            vctReturnsEntityKeys.addElement(rek);
            dbCurrent.update(profile, vctReturnsEntityKeys, false, false);
            dbCurrent.commit();
        }
        catch(java.sql.SQLException x)
        {
            throw x;
        }
        catch(COM.ibm.opicmpdh.middleware.MiddlewareException x)
        {
            throw x;
        }
        finally {
            dbCurrent.freeStatement();
            dbCurrent.isPending("setXMLTimeStamp::finally block after update Text value");
        }
    }
}

