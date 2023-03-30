package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.eannounce.abr.sg.rfc.*;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.abr.util.RFCConfig;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import com.ibm.transform.oim.eacm.util.PokUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;
import java.util.Hashtable;
import java.util.Set;

public class MODELGARSABRSTATUS extends PokBaseABR {
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = { '\n' };
    static final String NEWLINE = new String(FOOL_JTEST);
    private int abr_debuglvl = D.EBUG_ERR;
    private String navName = "";
    private Hashtable metaTbl = new Hashtable();
    private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODEL' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
    String xml = null;

    public void execute_run() {
        String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
                + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">"
                + EACustom.getMastheadDiv() + NEWLINE
                + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
        String HEADER2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
                + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>"
                + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE
                + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->"
                + NEWLINE;

        String header1 = "";


        MessageFormat msgf;
        String abrversion = "";
        String rootDesc = "";

        Object[] args = new String[10];

        try {
            msgf = new MessageFormat(HEADER);
            args[0] = getShortClassName(getClass());
            args[1] = "ABR";
            header1 = msgf.format(args);
            setDGTitle("MODELGARSABRSTATUS report");
            setDGString(getABRReturnCode());
            setDGRptName("MODELGARSABRSTATUS"); // Set the report name
            setDGRptClass("MODELGARSABRSTATUS"); // Set the report class
            // Default set to pass
            setReturnCode(PASS);
            start_ABRBuild(false); // pull the VE

            abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
                    .getABRDebugLevel(m_abri.getABRCode());

            // get the root entity using current timestamp, need this to get the
            // timestamps or info for VE pulls
            m_elist = m_db.getEntityList(m_prof,
                    new ExtractActionItem(null, m_db, m_prof,"dummy"),
                    new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

            EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
            addDebug("*****mlm rootEntity = " + rootEntity.getEntityType() + rootEntity.getEntityID());
            // NAME is navigate attributes - only used if error rpt is generated
            navName = getNavigationName();
            rootDesc = m_elist.getParentEntityGroup().getLongDescription();
            addDebug("navName=" + navName);
            addDebug("rootDesc" + rootDesc);
            // build the text file

            Connection connection = m_db.getODSConnection();
            PreparedStatement statement = connection.prepareStatement(CACEHSQL);
            statement.setInt(1, rootEntity.getEntityID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                xml = resultSet.getString("XMLMESSAGE");
            }

            if (xml != null) {
                MODEL model = XMLParse.getObjectFromXml(xml, MODEL.class);
                RdhYMDMGars abr = new RdhYMDMGars(model,m_db.getPDHConnection());
                this.addDebug("Calling " + abr.getRFCName());
                abr.execute();
                this.addDebug(abr.createLogEntry());
                if (abr.getRfcrc() == 0) {
                    this.addOutput(abr.getRFCName() + " called successfully!");
                } else {
                    this.addOutput(abr.getRFCName() + " called  faild!");
                    this.addOutput(abr.getError_text());
                }
                UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", abr.getRFCNum());
                runParkCaller(updateParkStatus,abr.getRFCNum());

                Set<String> plnts = RFCConfig.getBHPlnts();
                for(String plnt : plnts) {
                    GARSYMDMSalesBom bom = new GARSYMDMSalesBom(model,plnt);
                    this.addDebug("Calling " + bom.getRFCName());
                    bom.execute();
                    this.addDebug(bom.createLogEntry());
                    if (bom.getRfcrc() == 0) {
                        this.addOutput(bom.getRFCName() + " called successfully!");
                    } else {
                        this.addOutput(bom.getRFCName() + " called  faild!");
                        this.addDebug(bom.getRFCName() + " webservice return code: " + bom.getRfcrc());
                        this.addOutput(bom.getError_text());
                    }
                    updateParkStatus = new UpdateParkStatus("MD_CHW_IERP", bom.getRFCNum());
                    runParkCaller(updateParkStatus, bom.getRFCNum());
                }
            } else {
                this.addOutput("XML file not exist in cache,RFC caller not called!");
            }

        } catch (Exception e) {

            e.printStackTrace();
            setReturnCode(FAIL);
            java.io.StringWriter exBuf = new java.io.StringWriter();
            String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE = "<pre>{0}</pre>";
            msgf = new MessageFormat(Error_EXCEPTION);
            setReturnCode(INTERNAL_ERROR);
            e.printStackTrace(new java.io.PrintWriter(exBuf));
            // Put exception into document
            args[0] = e.getMessage();
            rptSb.append(msgf.format(args) + NEWLINE);
            msgf = new MessageFormat(Error_STACKTRACE);
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args) + NEWLINE);
            logError("Exception: " + e.getMessage());
            logError(exBuf.getBuffer().toString());
            // was an error make sure user gets report
            setCreateDGEntity(true);

        } finally {
            StringBuffer sb = new StringBuffer();
            msgf = new MessageFormat(HEADER2);
            args[0] = m_prof.getOPName();
            args[1] = m_prof.getRoleDescription();
            args[2] = m_prof.getWGName();
            args[3] = getNow();
            args[4] = sb.toString();
            args[5] = abrversion + " " + getABRVersion();
            rptSb.insert(0, convertToHTML(xml)+NEW_LINE);
            rptSb.insert(0, header1 + msgf.format(args) + NEWLINE);

            println(EACustom.getDocTypeHtml()); // Output the doctype and html
            println(rptSb.toString()); // Output the Report
            printDGSubmitString();
            if(!isReadOnly()) {
                clearSoftLock();
            }
            println(EACustom.getTOUDiv());
            buildReportFooter(); // Print </html>
        }
    }
    protected void runParkCaller(RdhBase caller, String zdmnum) throws Exception {
        this.addDebug("Calling " + caller.getRFCName());
        try {
            caller.execute();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        this.addDebug(caller.createLogEntry());
        if (caller.getRfcrc() == 0) {
            this.addOutput("Parking records updated successfully for ZDMRELNUM="+zdmnum);
        } else {
            this.addOutput(caller.getRFCName() + " called faild!");
            this.addOutput(caller.getError_text());
        }
    }
    protected static String convertToHTML(String txt)
    {
        String retVal="";
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt != null) {
            sci = new StringCharacterIterator(txt);
            ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                    case '<':
                        htmlSB.append("&lt;");
                        break;
                    case '>':
                        htmlSB.append("&gt;");
                        break;
                    case '"':
                        // double quotation marks could be saved as &quot; also. this will be &#34;
                        // this should be included too, but left out to be consistent with west coast
                        htmlSB.append("&quot;");
                        break;
                    case '\'':
                        //IE6 doesn't support &apos; to convert single quotation marks,we can use &#39; instead
                        htmlSB.append("&#"+((int)ch)+";");
                        break;
                    default:
                        htmlSB.append(ch);
                        break;
                }
                ch = sci.next();
            }
            retVal = htmlSB.toString();
        }

        return retVal;
    }

    private String getNavigationName() throws java.sql.SQLException, MiddlewareException {
        return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
    }

    private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        // check hashtable to see if we already got this meta
        EANList metaList = (EANList) metaTbl.get(theItem.getEntityType());
        if (metaList == null) {
            EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
            metaList = eg.getMetaAttribute(); // iterator does not maintain
            // navigate order
            metaTbl.put(theItem.getEntityType(), metaList);
        }
        for (int ii = 0; ii < metaList.size(); ii++) {
            EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
            navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
            if (ii + 1 < metaList.size()) {
                navName.append(" ");
            }
        }
        return navName.toString();
    }

    protected void addOutput(String msg) {
        rptSb.append("<p>"+msg+"</p>"+NEWLINE);
    }


    protected void addDebug(String msg) {
        if (D.EBUG_DETAIL <= abr_debuglvl) {
            rptSb.append("<!-- " + msg + " -->" + NEWLINE);
        }
    }

    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }

    @Override
    public String getDescription() {
        return "MODELGARSABRSTATUS";
    }

    @Override
    public String getABRVersion() {
        return "1.0";
    }
}
