// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.text.*;

/**********************************************************************************
* SAPLABRSTATUS class launch SAPLABR class to handle xml generation for specific entitytype
* From "SG FS ABR SAPL 20070830.doc"
* must use SG enterprise and GFS role to access SAPL attributes bertilc@us.ibm.com on xea6
*
*/
// SAPLABRSTATUS.java,v
// Revision 1.16  2008/01/30 19:39:16  wendy
// Cleanup RSA warnings
//
// Revision 1.15  2008/01/20 23:19:40  wendy
// Added check for domains
//
// Revision 1.14  2008/01/09 18:12:00  wendy
// Added support for LSCC - RQ0117074421
//
// Revision 1.13  2007/09/14 17:43:55  wendy
// Updated for GX
//
// Revision 1.12  2007/08/20 12:19:36  wendy
// DATAQUALITY flag code was updated
//
// Revision 1.11  2007/08/17 16:07:25  wendy
// Cleaned up cmts
//
// Revision 1.10  2007/08/17 16:02:10  wendy
// RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
// from 'SG FS xSeries ABRs 20070803.doc'
//
// Revision 1.9  2007/06/07 22:05:11  wendy
// Allow for deactivation of SAPASSORTMODULEPRIOR
//
// Revision 1.8  2007/05/16 19:22:19  wendy
// Make sure control block is set
//
// Revision 1.7  2007/05/16 18:42:02  wendy
// Added mq name to error msgs
//
// Revision 1.6  2007/05/04 13:40:05  wendy
// RQ022507238 chgs
//
// Revision 1.5  2007/05/01 21:00:55  wendy
// TIR72PGQ9 VE won't pull MODELPROJA from (SW)PRODSTRUCT root, use 2 VEs
//
// Revision 1.4  2007/05/01 12:00:08  wendy
// Updated SAPL table
//
// Revision 1.3  2007/04/20 21:51:05  wendy
// RQ0417075638 updates
//
// Revision 1.2  2007/04/18 20:15:49  wendy
// OIDH can't handle whitespace, prevent it
//
// Revision 1.1  2007/04/02 17:41:09  wendy
// Init for SAPL abr
//
public class SAPLABRSTATUS extends PokBaseABR
{
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    private static final Hashtable SAPL_TRANS_TBL;
    private static final Hashtable ABR_TBL;
    private boolean bdomainInList = false;

    private ResourceBundle saplBundle = null;

    protected static final String XMLNS_WSNT ="http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd";
    protected static final String XMLNS_EBI = "http://ibm.com/esh/ebi";

    protected final static String ESHMQSERIES = "ESHMQSERIES";
    protected final static String OIDHMQSERIES = "OIDHMQSERIES";
    private String xmlgen = "";  // set to Success or Failed or left blank if not required


/*
SAPL    10  Not Ready
SAPL    20  Ready
SAPL    30  Send
SAPL    40  Sent
SAPL    70  Update
SAPL    80  Update Sent
SAPL    90  N/A
*/
    static {
/*
Current Value   Set Value   Comment
Ready           Sent        First Time - ABR is successful
Send            Sent        First Time - Failed to Receive - Manual Trigger
Sent            Update Sent Received Once - Changed Data - Auto Trigger
Update Sent     Update      Received Once - send again
*/
        SAPL_TRANS_TBL = new Hashtable();
        SAPL_TRANS_TBL.put("20", "40");  // Ready->Sent
        SAPL_TRANS_TBL.put("30", "40");  // Send->Sent
        SAPL_TRANS_TBL.put("40", "80");  // Sent->Update Sent
        SAPL_TRANS_TBL.put("80", "70");  // Update Sent->Update

        ABR_TBL = new Hashtable();
        ABR_TBL.put("ACCTGUSEONLYMATRL", "COM.ibm.eannounce.abr.sg.ACCTGUSEONLYMATRLSAPLXML");
        ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.MODELSAPLXML");
        ABR_TBL.put("ORDABLEPARTNO", "COM.ibm.eannounce.abr.sg.ORDABLEPARTNOSAPLXML");
        ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.PRODSTRUCTSAPLXML");
        ABR_TBL.put("SWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.SWPRODSTRUCTSAPLXML");
        ABR_TBL.put("LSCC", "COM.ibm.eannounce.abr.ls.LSCCSAPLXML"); //RQ0117074421
    }

    /**********************************
    * get the resource bundle
    */
    protected ResourceBundle getBundle() {
		return saplBundle;
	}

    /**
     *  Execute ABR.
     *
     */
    public void execute_run()
    {
        /*
        The Report should identify:
            USERID (USERTOKEN)
            Role
            Workgroup
            Date/Time
            EntityType LongDescription
            ABRSTATUS that was set
            Data Quality Errors (if any) as described in other sections
            An indication if XML generation/feed was applicable
            If XML was applicable, an indication of whether it was successfully sent
        */
        String navName = "";
        // must split because too many arguments for messageformat, max of 10.. this was 11
        String HEADER = "<head>"+
             EACustom.getMetaTags(getDescription()) + NEWLINE +
             EACustom.getCSS() + NEWLINE +
             EACustom.getTitle("{0} {1}") + NEWLINE +
            "</head>" + NEWLINE + "<body id=\"ibm-com\">" +
             EACustom.getMastheadDiv() + NEWLINE +
            "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
        String HEADER2 = "<table>"+NEWLINE +
             "<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
             "<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
             "<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
             "<tr><th>Date: </th><td>{3}</td></tr>"+NEWLINE +
             "<tr><th>Description: </th><td>{4}</td></tr>"+NEWLINE +
             "<tr><th>XML generation: </th><td>{5}</td></tr>"+NEWLINE+
             "</table>"+NEWLINE+
            "<!-- {6} -->" + NEWLINE;

        MessageFormat msgf;
        EntityItem rootEntity;
        String rootDesc="";
        String abrversion="";
        Object[] args = new String[10];
        println(EACustom.getDocTypeHtml()); //Output the doctype and html
        try
        {
            long startTime = System.currentTimeMillis();

            start_ABRBuild(false); // dont pull VE yet, have to get name from SAPLXML class

            // find class to instantiate based on entitytype
    		// Load the specified ABR class in preparation for execution
    		String clsname = (String) ABR_TBL.get(getEntityType());
    	  	addDebug("creating instance of SAPLXML  = '" + clsname + "'");

      		SAPLXMLBase saplAbr = (SAPLXMLBase) Class.forName(clsname).newInstance();

            // get VE name from it
            String VEname = saplAbr.getSaplVeName();
            // create VE
			m_elist = m_db.getEntityList(m_prof,
				new ExtractActionItem(null, m_db,m_prof,VEname),
				new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

            addDebug("Time to get VE: "+(System.currentTimeMillis()-startTime)+" (mseconds)");

            setControlBlock(); // needed for attribute updates

            //get properties file for the base class
            saplBundle = ResourceBundle.getBundle(SAPLABRSTATUS.class.getName(), getLocale(m_prof.getReadLanguage().getNLSID()));

            // debug display list of groups
            addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " + getEntityType() + ":" + getEntityID()+
                " extract: "+VEname+NEWLINE + PokUtils.outputList(m_elist));

            //Default set to pass
            setReturnCode(PASS);

            //NAME is navigate attributes
            navName = getNavigationName();

            // get root from VE
			rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
            rootDesc = m_elist.getParentEntityGroup().getLongDescription();

// fixme remove this.. avoid msgs to userid for testing
//setCreateDGEntity(false);
            // check if pdhdomain is in domain list for this ABR
            domainNeedsChecks(rootEntity);

			if (bdomainInList){
				// call execute passing ve and rptsb
				boolean xmlOk = saplAbr.execute_run(this);
				xmlgen = saplAbr.getXMLGenMsg();
				abrversion = getShortClassName(saplAbr.getClass())+" "+saplAbr.getVersion();

				if (xmlOk){
					// if meta does not have this attribute, there is nothing to do
					EANMetaAttribute metaAttr = getEntityList().getParentEntityGroup().getMetaAttribute("SAPL");
					if (metaAttr==null) { //RQ0117074421
						addDebug("SAPL was not in meta, nothing to update");
					}else{
						// check value of SAPL attribute
						String curVal = getAttributeFlagEnabledValue(rootEntity, "SAPL");
						String newSaplValue = (String)SAPL_TRANS_TBL.get(curVal);

						if (!curVal.equals(newSaplValue)){
							// update SAPL
							setFlagValue(m_elist.getProfile(),"SAPL", newSaplValue);
						}else{
							addDebug("SAPL was not updated, it is already set to: "+newSaplValue);
						}
					}
				}
			}else{
				xmlgen = getBundle().getString("DOMAIN_NOT_LISTED"); //  = Domain was not in the list of supported Domains. Execution bypassed.
			}
        }
        catch(Throwable exc)
        {
            java.io.StringWriter exBuf = new java.io.StringWriter();
            String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            msgf = new MessageFormat(Error_EXCEPTION);
            setReturnCode(INTERNAL_ERROR);
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
            setDGRptClass(getABRCode());
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
        args[1] = navName;
        String header1 = msgf.format(args);
        msgf = new MessageFormat(HEADER2);
        args[0] = m_prof.getOPName();
        args[1] = m_prof.getRoleDescription();
        args[2] = m_prof.getWGName();
        args[3] = getNow();
        args[4] = rootDesc;
        args[5] = xmlgen;
        args[6] = abrversion+" "+getABRVersion();

        rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>
    }

    /**********************************
    * get entitylist
    */
    protected EntityList getEntityList() { return m_elist; }

    /**********************************
    * get database
    */
    protected Database getDB() { return m_db; }

    /**********************************
    * stringbuffer used for report output
    */
    protected void addOutput(String msg) { rptSb.append(msg+NEWLINE);}

    /**********************************
    * stringbuffer used for report output
    */
    protected void addDebug(String msg) { rptSb.append("<!-- "+msg+" -->"+NEWLINE);}

    /**********************************************************************************
    *  Get Name based on navigation attributes
    *
    *@return java.lang.String
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
            navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
            navName.append(" ");
        }

        return navName.toString();
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.16";
    }
    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        return "SAPLABRSTATUS";
    }

    /***********************************************
    *  Sets the specified Flag Attribute on the Root Entity
    *
    *@param    profile Profile
    *@param    strAttributeCode The Flag Attribute Code
    *@param    strAttributeValue The Flag Attribute Value
    */
    private void setFlagValue(Profile profile, String strAttributeCode, String strAttributeValue)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        logMessage(getDescription()+" ***** "+strAttributeCode+" set to: " + strAttributeValue);
        addDebug("setFlagValue entered for "+strAttributeCode+" set to: " + strAttributeValue);

        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		// if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(strAttributeCode);
        if (metaAttr==null) {
			addDebug("setFlagValue: "+strAttributeCode+" was not in meta for "+rootEntity.getEntityType()+", nothing to do");
        	logMessage(getDescription()+" ***** "+strAttributeCode+" was not in meta for "+
        		rootEntity.getEntityType()+", nothing to do");
			return;
		}

        if(strAttributeValue != null)
        {
            try
            {
                if (m_cbOn==null){
                    setControlBlock(); // needed for attribute updates
                }
                ReturnEntityKey rek = new ReturnEntityKey(getEntityType(), getEntityID(), true);

                SingleFlag sf = new SingleFlag (profile.getEnterprise(), rek.getEntityType(), rek.getEntityID(),
                    strAttributeCode, strAttributeValue, 1, m_cbOn);
                Vector vctAtts = new Vector();
                Vector vctReturnsEntityKeys = new Vector();
                vctAtts.addElement(sf);
                rek.m_vctAttributes = vctAtts;
                vctReturnsEntityKeys.addElement(rek);

                m_db.update(profile, vctReturnsEntityKeys, false, false);
                m_db.commit();
            }
            finally {
                m_db.freeStatement();
                m_db.isPending("finally after update in setflag value");
            }
        }
    }

    /**********************************************************************************
    *  Get Locale based on NLSID
    *
    *@return java.util.Locale
    */
    public static Locale getLocale(int nlsID)
    {
        Locale locale = null;
        switch (nlsID)
        {
        case 1:
            locale = Locale.US;
            break;
        case 2:
            locale = Locale.GERMAN;
            break;
        case 3:
            locale = Locale.ITALIAN;
            break;
        case 4:
            locale = Locale.JAPANESE;
            break;
        case 5:
            locale = Locale.FRENCH;
            break;
        case 6:
            locale = new Locale("es", "ES");
            break;
        case 7:
            locale = Locale.UK;
            break;
        default:
            locale = Locale.US;
            break;
        }
        return locale;
    }

    /*************************************************************************************
    * Check the PDHDOMAIN
    * xseries and converged prod need DQ checks in the ABRs but the other domains like iseries don't
    * because those Brands do not want any checking, they do not use STATUS, they want no process
    * criteria apply if PDHDOMAIN = (0050) 'xSeries' or (0390) 'Converged Products'
    *@param item    EntityItem
    * domainInList set to true if matches one of these domains
    */
    private void domainNeedsChecks(EntityItem item)
    {
    	String domains = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getDomains(m_abri.getABRCode());
    	addDebug("domainNeedsChecks pdhdomains needing checks: "+domains);
		if (domains.equals("all")){
			bdomainInList = true;
		}else{
	        Set testSet = new HashSet();
			StringTokenizer st1 = new StringTokenizer(domains,",");
			while (st1.hasMoreTokens()) {
		        testSet.add(st1.nextToken());
			}
	        bdomainInList = PokUtils.contains(item, "PDHDOMAIN", testSet);
	        testSet.clear();
		}

        if (!bdomainInList){
            addDebug("PDHDOMAIN did not include "+domains+", execution is bypassed ["+
                PokUtils.getAttributeValue(item, "PDHDOMAIN",", ", "", false)+"]");
        }
    }
}
