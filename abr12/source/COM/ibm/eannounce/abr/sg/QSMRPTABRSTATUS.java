//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import java.io.*;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;

import com.ibm.transform.oim.eacm.util.PokUtils;
import com.ibm.transform.oim.eacm.diff.*;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

/**********************************************************************************
* From "SG FS SysFeed QSM Load  20121128.doc" 
* CQ329697  - remove date checks
* "SG FS SysFeed QSM Load  20141030.doc" 
* Title: Update the change report (feed) to remove announcement date filter.
* Description: The current  QSM feed from EACM Lenovo to is restricted to only sending data within 30 days 
* window for announcement date . We need to remove this filter and allow it to send updates for past and future 
* announcements deltas.
* 
* 
* This handles 2 cases
* B.	Change Reports
* The Change Reports function can produce two different kinds of output for loading into QSM. They are:
1.	Excel Spreadsheet available for distribution via Subscription/Notification.
2.	Flat File Column Aligned suitable for FTP to QSM
A properties file will be used to indicate which of the outputs is desired. Support for 'both' is desireable 
if a significant effort is not required.

* 	These reports are produced when Status (STATUS) changes to Final (0020) as requested by a user with 
* the Power User Role:
* 	- First Time:  a full report with all attributes
* 	- Not the First Time:  only the changed values are shown; however, the RFA Number and the Announcement Date 
* will always be shown. 
* The report will not be produced if there are no changes to attributes of interest.
* The report is queued immediately and is run just as soon as system resources are available. 
* Once the report is produced, it is distributed immediately.
* 
* C.	Requested Reports
* These reports are produced as a result of a JUI user request. They are similar to the Change Reports except 
* that they always show all attributes. The report is queued by a user with the System Feed Admin (SYSFEEDADMIN) 
* Role and is run just as soon as system resources are available. Once the report is produced, it is distributed 
* immediately.
* 
* D.	Generate Reports

GenerateOutputAll pulls the data once using T2 as the Date Time Stamp, creates rows for data and 
shows all attributes.

GenerateOutputDelta for the Flat File, output all data for T2; for the spreadsheet, pull the data twice, 
once for T2 and once for T1. A row is created in the spreadsheet if one or more attributes change between 
T1 and T2. There are some attributes that are not checked for change; however, they are always shown if the 
row is created. The attributes watched for change are only shown if there is a change in value.

General Area Selection (GENAREASELECTION) has one value of interest which will be used to filter the 
data at the root entity. The data of interest is based on a priority of allowed GENAREASELECTION values. 
(i.e. only include one set of data if there is a match in the following order):
1.	1999 (World Wide)
2.	6204 (Latin America)
3.	6197 (Americas) RCQ00132040

Revision 13:
RCQ00132040-WI
Title: EACM SG:  Update QSM Feed Logic to Accept Additional GENAREAs

Description: The current QSM feed from EACM SG to QSM is sending data based on a GENAREA selection of "Latin America". 
 The data enablement teams are now using "Worldwide" and "Americas" GENAREA selections and the current feed needs 
 to be updated to accept those values.

*
QSMRPTABRSTATUS_class=COM.ibm.eannounce.abr.sg.QSMRPTABRSTATUS
QSMRPTABRSTATUS_enabled=true
QSMRPTABRSTATUS_idler_class=D
QSMRPTABRSTATUS_keepfile=true
QSMRPTABRSTATUS_read_only=true
QSMRPTABRSTATUS_report_type=DGTYPE01

The subscription information for the Change/Requested Reports will be:
CAT1= "GFSQSMRPT"
CAT2= value of (QSMFEEDRESEND)
CAT3= root entity type

*/
// QSMRPTABRSTATUS.java,v
// Revision 1.14  2009/12/16 13:26:27  wendy
// correct description from GFSQSMABRSTATUS to QSMRPTABRSTATUS
//
// Revision 1.13  2009/08/18 19:31:44  wendy
// Allow extractaction to cleanup
//
// Revision 1.12  2009/07/30 18:41:56  wendy
// Remove diffVE msg
//
// Revision 1.11  2009/07/27 16:09:45  wendy
// Put ftp msgs into tm log
//
// Revision 1.10  2009/07/13 14:01:55  wendy
// check for valid history
//
// Revision 1.9  2009/06/24 14:30:17  wendy
// Pass host, user, pw and filename to script
//
// Revision 1.8  2009/06/11 15:06:20  wendy
// Call script with fully qualified filename
//
// Revision 1.7  2009/05/28 17:15:51  wendy
// Support executing a script
//
// Revision 1.6  2009/03/13 14:32:45  wendy
// CQ22294-LA CTO (XCC) - Update QSM Reports to Accept WW GenArea (WI CQ22265)
//
// Revision 1.5  2009/02/18 20:01:58  wendy
// Remove ascii indicator from ftp url, it was not getting removed
//
// Revision 1.4  2009/02/04 21:26:44  wendy
// CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
// Revision 1.3  2008/12/11 23:17:27  wendy
// use updated mw code
//
// Revision 1.2  2008/12/10 16:39:39  wendy
// Spec change to reduce number of error rpts
//
// Revision 1.1  2008/09/30 12:50:12  wendy
// CQ00006066-WI LA CTO - EACM - Support GFS data Load of QSM (report)
//
//
public class QSMRPTABRSTATUS extends PokBaseABR
{
    private static final Hashtable ABR_TBL;
	static{
        ABR_TBL = new Hashtable();
        ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.QSMLSEOABR");
        ABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.sg.QSMLSEOBDLABR");
        ABR_TBL.put("ANNOUNCEMENT", "COM.ibm.eannounce.abr.sg.QSMANNABR");
    }
    //GENAREASELECTION	6197	Americas
    //GENAREASELECTION	6204	Latin America
	//GENAREASELECTION	1999	Worldwide 
    private static final String GENAREA_AMERICAS ="6197";//RCQ00132040
    private static final String GENAREA_LA ="6204";
    protected static final String GENAREA_WW ="1999"; //CQ22265
    
    protected static final String[] GEOS = new String[] {GENAREA_LA, GENAREA_AMERICAS};//RCQ00132040
		
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    
    private static final String QSMFEEDRESEND_YES = "Yes"; 
    private static final String QSMFEEDRESEND_NO = "No";
    private static final String QSMFEEDRESEND_NEW = "New";
    private static final int IDLEN = 6;
   
    private static final int MAX_7ZZZZZ = 483729407;
    private static final int BYPASS_8_9 = 120932352;
 	private static final int MIN_DZZZZZ = 846526463;
 	private static final int MAX_F00000 = 906992640;
 	private static final int BYPASS_E = 60466176;
    
	private HSSFWorkbook wb = null;		
    private Vector dtsVct;
    private EntityList listT2 = null;
    private ResourceBundle rsBundle = null;
    private Hashtable metaTbl = new Hashtable();
    private String navName = "";
    private QSMABRInterface qsmAbr=null;
    private Vector vctReturnsEntityKeys = new Vector();
    private String outputType=""; // used to create xls filename
    
    private boolean createSS = false; // CQ00016165
	private boolean createFF = false; // CQ00016165
    private String ffFileName = null; // CQ00016165
    private String ffPathName = null; // CQ00016165
	private int abr_debuglvl=D.EBUG_ERR;
	
    /**********************************
    * get the resource bundle
    */
    protected ResourceBundle getBundle() {
        return rsBundle;
    }

    /**********************************
     *  Execute ABR.
     *
     */
    public void execute_run()
    {
        /* only used if error rpt is generated
        The Report should identify:
            USERID (USERTOKEN)
            Role
            Workgroup
            Date/Time
            EntityType LongDescription
        */
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
             "</table>"+NEWLINE+
            "<!-- {5} -->" + NEWLINE;

        MessageFormat msgf;
        String rootDesc="";
        String abrversion="";
        
        Object[] args = new String[10];
        String sysfeedFlag = QSMFEEDRESEND_NEW;
       
        try {
            //Default set to pass
            setReturnCode(PASS);
            
            start_ABRBuild(false); // dont pull VE yet
            
            abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel(m_abri.getABRCode());

            // get properties file for the base class
            rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));

            setControlBlock(); // needed for attribute updates
                        
            //get the root entity using current timestamp, need this to get the timestamps or info for VE pulls
			m_elist = m_db.getEntityList(m_prof,
					new ExtractActionItem(null, m_db, m_prof,"dummy"),
					new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });
            
            EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
            sysfeedFlag = getAttributeFlagEnabledValue(rootEntity, "QSMFEEDRESEND");

    		addDebug("execute: "+rootEntity.getKey()+" sysfeedFlag: "+	sysfeedFlag);
    		if (sysfeedFlag==null){
    			sysfeedFlag = QSMFEEDRESEND_NEW;
    		}
   
            //NAME is navigate attributes - only used if error rpt is generated
            navName = getNavigationName();
            rootDesc = m_elist.getParentEntityGroup().getLongDescription();

// fixme remove this.. avoid msgs to userid for testing
//setCreateDGEntity(false);

			// get file types to create from properties file
			String fileformats = ABRServerProperties.getFileFormats(m_abri.getABRCode());
			
			StringTokenizer st1 = new StringTokenizer(fileformats,",");
			if (!st1.hasMoreTokens()){ // default to creating ss if no fileformats specified
				createSS = true;
			}
				
			while (st1.hasMoreTokens()) {
				String ff = st1.nextToken();
				if (ff.equalsIgnoreCase("xls")){
					createSS = true;
				}
				if (ff.equalsIgnoreCase("txt")){
					createFF = true;
				}
			}
			addDebug("fileformats: "+fileformats+" createSS: "+createSS+" createFF: "+createFF);
			
            try{		
            	// get times to use for VE pulls
            	getChangeTimes();

            	// find class to instantiate based on entitytype
            	// Load the specified ABR class in preparation for execution
            	String clsname = (String) ABR_TBL.get(getEntityType());
            	addDebug("creating instance of QSMABR  = '" + clsname + "'");	
            	 
            	if (clsname!=null){
            		qsmAbr = (QSMABRInterface) Class.forName(clsname).newInstance();
            		abrversion = getShortClassName(qsmAbr.getClass())+" "+qsmAbr.getVersion();

            		if (qsmAbr.canGenerateReport(rootEntity, this)){
            			// pull VE for T2, it will always be needed
            			String lastdts = getTime2DTS();

            			// set lastfinal date in profile
            			Profile lastprofile = switchRole("QSMRPT");
            			lastprofile.setValOnEffOn(lastdts, lastdts);

            			// create VE for lastfinal time
            			listT2 = getEntityListForDiff(lastprofile,qsmAbr.getVeName());
            			// use root from this list
            			rootEntity = listT2.getParentEntityGroup().getEntityItem(0);
            			
            			if (qsmAbr.canGenerateReport(listT2, this)){
            				//need new RFANUM if it is null or new was specified
            				generateRFANumber(rootEntity,QSMFEEDRESEND_NEW.equals(sysfeedFlag));
            				            				
            				if (QSMFEEDRESEND_NEW.equals(sysfeedFlag)){
            					// Build ss/text file with all attributes 
            					generateOutputAll(rootEntity);            			
            				}else if (QSMFEEDRESEND_YES.equals(sysfeedFlag)){            					
            					// Build ss/text file with all attributes
            					generateOutputAll(rootEntity);
            				}else if (QSMFEEDRESEND_NO.equals(sysfeedFlag)){
            					// check for dates
            				//CQ329697 - remove date check  
            				//  if (qsmAbr.withinDateRange(rootEntity, getNow().substring(0, 10), this)){
            						if (getTime1DTS().equals(m_strEpoch)) {
            							// Build ss/text file with all attributes
            							generateOutputAll(rootEntity);
            						}else{
            							// Build ss with changed attributes
            							if (createSS){
                							generateOutputDelta();
                							if (wb==null){
                								//NO_CHG_FOUND = No change of interest found.
                								String msg = getBundle().getString("NO_CHG_FOUND");
                								addOutput(msg);
                								D.ebug("QSMRPTABRSTATUS:"+getEntityType()+" "+msg);
                							}
            							}
            							
            							if (createFF){ // CQ00016165
            								generateFlatFile(rootEntity);
            							}            							
            						}
            				//	}
            				}else{ // must have been a real error to get an unknown flag
            					//output error msg
            					//INVALID_DATA = Invalid data condition; the report was not generated.
            					addError("INVALID_DATA",null);
            				}

            				if (createSS && wb != null){
            					// output the ss
            					outputSS(rootEntity);
            				}  
            				if (createFF && ffFileName != null){
            					// ftp the txt file
            					ftpFile(); 
            				}  
            			} // end generate rpt ok based on t2 list
            		} // end generate rpt ok based on just root entity
            	}else{
            		addError(getShortClassName(getClass())+" does not support "+getEntityType());
            	}
            	
            	if ((!createSS) && 
            			getReturnCode()==PokBaseABR.PASS){ // if only txt files are created, do not send any report
            		setNoReport();
            	}
            	// some conditions do not want any report
            	if (!createReport){
            		setCreateDGEntity(false);
            	}

            	dtsVct.clear();
            }catch(Exception e) {
            	throw e;
            }finally{
            	setFlagValue(getProfile(), "QSMFEEDRESEND", QSMFEEDRESEND_NO, rootEntity);
            	updatePDH();
            	if (listT2 != null){
            		listT2.dereference();
            	}
            }
        }
        catch(Throwable exc)  {
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
//          was an error make sure user gets report
			setCreateDGEntity(true);
        }
        finally {
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass(getABRCode());
            // make sure the lock is released
            if(!isReadOnly()) {
                clearSoftLock();
            }
        }
        //if no errors, then this just for debug - user will get the zip file
        //Print everything up to </html>
        //Insert Header into beginning of report
        msgf = new MessageFormat(HEADER);
        if (qsmAbr!=null){
        	args[0] = getShortClassName(qsmAbr.getClass());
        }else{
        	args[0] = getShortClassName(getClass());
        }
        args[1] = navName;
        String header1 = msgf.format(args);
        msgf = new MessageFormat(HEADER2);
        args[0] = m_prof.getOPName();
        args[1] = m_prof.getRoleDescription();
        args[2] = m_prof.getWGName();
        args[3] = getNow();
        args[4] = rootDesc;
        args[5] = abrversion+" "+getABRVersion();

        rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

        println(EACustom.getDocTypeHtml()); //Output the doctype and html        
		println(rptSb.toString()); // Output the Report
		printDGSubmitString();        
        
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>
        
        metaTbl.clear();
    }
    
	/***************************************************************
	 *  role will limit access to attributes of interest
	 * @param roleCode
	 * @return
	 * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
	 * @throws java.sql.SQLException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws java.rmi.RemoteException
	 * @throws IOException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	 */
	private Profile switchRole(String roleCode)
	throws COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.rmi.RemoteException,
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	{
		Profile profile2 = m_prof.getProfileForRoleCode(m_db, roleCode, roleCode, 1);
		if (profile2==null) {
			throw new MiddlewareException("Could not switch to "+roleCode+" role");
		}
		addDebug("Switched role from "+m_prof.getRoleCode()+" to "+profile2.getRoleCode());
		profile2.setReadLanguage(0);		
		
		return profile2;
	}
	/******************************************
	* get entitylist used for compares
	*/
	private EntityList getEntityListForDiff(Profile prof, String veName) throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		ExtractActionItem xai = new ExtractActionItem(null, m_db, prof, veName);
	//	xai.setSkipCleanup(true);
		EntityList list = m_db.getEntityList(prof, xai,
				new EntityItem[] { new EntityItem(null, prof, getEntityType(), getEntityID())});

		// debug display list of groups
		addDebug("EntityList for "+prof.getValOn()+" extract "+veName+" contains the following entities: \n"+
				PokUtils.outputList(list));

		return list;
	}
	
	/*****************************************************************
	 *  The following procedure will be used to generate unique QSM RFA Numbers for Special Bids:
	 *  -	A new attribute QSM RFA Number (QSMRFANUMBER) will be added to LSEO and LSEOBUNDLE
	 *  -	This ABR will generate this number the first time that the data is sent via a report or a request for a new RFA Number is made and a report is sent. 
	 *  	This is indicated by QSMFEEDRESEND = 'Yes' (Yes) | 'New' (New)
	 *  -	This new attribute will not be editable by users
	 *  
	 *  The Derived RFA number will be generated as follows:
	 *  -	A 6 character attribute with the current highest value will be saved in the PDH (entity = QSMRFANUM and attributecode = QSMRFANUMBER) with an initial value of '000000'
	 *  -	The ABR will pick up the current highest value, increase it by one using a unique number system and save it in the SEO and as the new highest value
	 *  -	The unique number system consists of Positions 2 through 6 of this attribute will be 0 through 9 and A through Z
	 *  -	Position 1 will be the same except it will not include either '8', '9' or 'E'
	 *  
	 * @param eitem
	 * @param createNew
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws EANBusinessRuleException 
	 */
	private void generateRFANumber(EntityItem eitem,boolean createNew) throws SQLException, 
		MiddlewareException, EANBusinessRuleException 
	{
		String attrCode = "QSMRFANUMBER";
		// if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = eitem.getEntityGroup().getMetaAttribute(attrCode);
        if (metaAttr==null) {
			addDebug("generateRFANumber: "+attrCode+" was not in meta for "+eitem.getEntityType()+", nothing to do");	        
			return;
		}
		// check value
        String rfanum = PokUtils.getAttributeValue(eitem, attrCode, "", null, false);
        addDebug("generateRFANumber: current RFAnum: "+rfanum);
        if (rfanum==null){
        	createNew=true;
        }
		
		// create new
        if (createNew){
        	rfanum = this.getRFANumber();
        	addDebug("generateRFANumber: created new RFAnum: "+rfanum);

        	Vector vctAtts = null;
        	// look at each key to see if this item is there yet
        	for (int i=0; i<vctReturnsEntityKeys.size(); i++){
        		ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
        		if (rek.getEntityID() == eitem.getEntityID() &&
        				rek.getEntityType().equals(eitem.getEntityType())){
        			vctAtts = rek.m_vctAttributes;
        			break;
        		}
        	}
        	if (vctAtts ==null){
        		ReturnEntityKey rek = new ReturnEntityKey(eitem.getEntityType(),
        				eitem.getEntityID(), true);
        		vctAtts = new Vector();
        		rek.m_vctAttributes = vctAtts;
        		vctReturnsEntityKeys.addElement(rek);
        	}
        	COM.ibm.opicmpdh.objects.Text sf = new COM.ibm.opicmpdh.objects.Text(getProfile().getEnterprise(),
        			eitem.getEntityType(), eitem.getEntityID(), attrCode, rfanum, 1, m_cbOn);
        	vctAtts.addElement(sf);
        	
        	// put it in the current item so it can be put into ss
            EntityGroup eg = eitem.getEntityGroup();
            EANMetaAttribute mAttr = eg.getMetaAttribute(attrCode);
            EANTextAttribute tAttr = new TextAttribute(eitem, m_prof, (MetaTextAttribute) mAttr);
            tAttr.put(rfanum);
        	eitem.putAttribute(tAttr);
        }
	}
	
    /******************************************
     * look for changes of interest and build a ss using these changes
     * @throws Exception
     */
    private void generateOutputDelta() throws Exception
    {
    	// pull VE for priortime
        String dts1 = getTime1DTS();

        // set prior date in profile
        Profile priorprofile = switchRole("QSMRPT");
        priorprofile.setValOnEffOn(dts1, dts1);

        // pull VE for prior time- get VE name from QSMabr
        EntityList listT1 = getEntityListForDiff(priorprofile,qsmAbr.getVeName());          
   	   	
    	// get the differences
		// get VE steps for later flattening the VE
		Hashtable hshMap =
			((ExtractActionItem)listT2.getParentActionItem()).generateVESteps(m_db, listT2.getProfile(),
				getEntityType());

		boolean usedebug = abr_debuglvl > D.EBUG_DETAIL;
		// flatten both VEs using the VE steps
		DiffVE diff = new DiffVE(listT1,listT2, hshMap,usedebug);
		addDebug("hshMap: "+hshMap);
		addDebug("time1 flattened VE: "+diff.getPriorDiffVE());
		addDebug("time2 flattened VE: "+diff.getCurrentDiffVE());

		// merge time1 and time2 flattened VEs into one with adds and deletes marked
		Vector diffVct = diff.diffVE();
		if(usedebug){
			addDebug("diffVE debug info:"+NEWLINE+diff.getDebug());
		}
		
		addDebug("diffVE flattened VE: "+diffVct);
		
		// group all changes by entitytype, except for root entity
		Hashtable diffTbl = new Hashtable();
		boolean chgsFnd = false;
		for (int x=0; x<diffVct.size(); x++){
			DiffEntity de = (DiffEntity)diffVct.elementAt(x);
			// keep track of any changes
			chgsFnd = chgsFnd || de.isChanged();
		//addDebug(" de.isChanged(): "+de.isChanged()+" "+de.toString());

			// must be able to find up and down links from a diffentity
			diffTbl.put(de.getKey(), de);

			String type = de.getEntityType();
			if (de.isRoot()){
				type = "ROOT";
			}
			Vector vct = (Vector)diffTbl.get(type);
			if (vct==null){
				vct = new Vector();
				diffTbl.put(type, vct);
			}
			vct.add(de);
		}
		
		if (chgsFnd){
			// make sure there is a vector for each entitygroup in the extract
			for (int i=0; i< listT2.getEntityGroupCount(); i++){
				String type = listT2.getEntityGroup(i).getEntityType();
				Vector vct = (Vector)diffTbl.get(type);
				if (vct==null){
					vct = new Vector();
					diffTbl.put(type, vct);
				}
			}
			// create ss
			generateOutputDelta(diffTbl);
			outputType = "Chgs";
		}else{
			addDebug("No changes found in the VEs between T1 and T2");
		}

		// release memory
		listT1.dereference();
		hshMap.clear();
		diffVct.clear();
		diff.dereference();
		diffTbl.clear();
    }
    
    /********************************************
     * Build ss using only differences from t1 to t2
     * @param diffTbl
     */
    private void generateOutputDelta(Hashtable diffTbl) 
    {
    	int nColumns = qsmAbr.getColumnCount();
    	wb = new HSSFWorkbook();
    	
    	// build the ss
		int rowIndex = 0;
		HSSFSheet sheet = wb.createSheet(getEntityType());
		wb.setSheetName(wb.getSheetIndex(getEntityType()), getEntityType());
											
		HSSFRow row = sheet.createRow(rowIndex);
		for (int columnIndex = 0; columnIndex < nColumns; columnIndex++) {
			HSSFCell cell = row.createCell((short)columnIndex);			
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(qsmAbr.getColumnLabel(columnIndex)));
		}   
		
	   	HashSet rowKeysSet = new HashSet(); //IN4397871
		// get all items that will generate a row for each geo 
		for (int g=0; g<GEOS.length; g++){
			addDebug("generateOutputDelta: looking for GEO "+GEOS[g]);
			Vector itemVct = qsmAbr.getRowItems(null,diffTbl, GEOS[g], this);
			//only output information once - if a second geo is found, skip it
			if(itemVct.size()>0){
				// look for changes
				for (int i=0; i<itemVct.size(); i++) {
					XLRow rowitem = (XLRow)itemVct.elementAt(i);
					boolean rowhaschgs = false;
					for (short columnIndex = 0; columnIndex < nColumns; columnIndex++) {
						if (qsmAbr.isChanged(rowitem.getRowType(), rowitem.getItemTbl(),columnIndex)){
							rowhaschgs=true;
							break;
						}
					}
					if (rowhaschgs){ // output the row
	    				if(rowKeysSet.contains(rowitem.getRowKey())){ //IN4397871
	    					// already added this row - must have had duplicate geo match
	    		    		addDebug("generateOutputDelta: already added "+rowitem.getRowKey());
	    					rowitem.dereference();
	    					continue;
	    				}
	    				rowKeysSet.add(rowitem.getRowKey());
						rowIndex++;
						row = sheet.createRow(rowIndex);
						for (short columnIndex = 0; columnIndex < nColumns; columnIndex++) {
							HSSFCell cell = row.createCell((short)columnIndex);	
							qsmAbr.setColumnValue(cell,rowitem.getRowType(), rowitem.getItemTbl(),columnIndex);					
						}
					}
					rowitem.dereference();
				}
				itemVct.clear();
    			//break; must look in all geos for items, duplicates will be skipped IN4397871
			}
		}    
		
		rowKeysSet.clear();
		rowKeysSet = null;
		
		if (rowIndex==0){
			addDebug("No changes of interest found to create rows");
			wb=null;
		} 
    }
    
    /********************************************
     * Build output using all 
     * @throws IOException
     */
    private void generateOutputAll(EntityItem rootEntity) throws IOException
    {
      	// build the ss
		if (createSS){
			generateSSOutputAll();
		}
		
		if (createFF){ // CQ00016165
			generateFlatFile(rootEntity);
		}		
    }
    /********************************************
     * Build output using all 
     * @throws IOException
     */
    private void generateSSOutputAll() throws IOException
    {
    	outputType = "All";

    	// build the ss
    	addDebug("generateSSOutputAll: creating SS");
    	int rowIndex = 0;
    	int nColumns = qsmAbr.getColumnCount();

    	wb = new HSSFWorkbook();
    	HSSFSheet sheet = wb.createSheet(getEntityType());
    	wb.setSheetName(wb.getSheetIndex(getEntityType()), getEntityType());

    	HSSFRow row = sheet.createRow(rowIndex);
    	for (int columnIndex = 0; columnIndex < nColumns; columnIndex++) {
    		HSSFCell cell = row.createCell((short)columnIndex);			
    		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    		cell.setCellValue(new HSSFRichTextString(qsmAbr.getColumnLabel(columnIndex)));
    	}

    	HashSet rowKeysSet = new HashSet();//IN4397871
    	// get rows for each GEO
    	for (int g=0; g<GEOS.length; g++){
    		addDebug("generateOutputAllss: looking for GEO "+GEOS[g]);
    		Vector itemVct = qsmAbr.getRowItems(listT2,null, GEOS[g], this);
    		//only output information once - if a second geo is found, skip it
    		if(itemVct.size()>0){
    			for (int i=0; i<itemVct.size(); i++) {
    				XLRow rowitem = (XLRow)itemVct.elementAt(i);
    				if(rowKeysSet.contains(rowitem.getRowKey())){ //IN4397871
    					// already added this row - must have had duplicate geo match
    		    		addDebug("generateOutputAllss: already added "+rowitem.getRowKey());
    					rowitem.dereference();
    					continue;
    				}
    				rowKeysSet.add(rowitem.getRowKey());
    				rowIndex++;
    				row = sheet.createRow(rowIndex);
    				for (short columnIndex = 0; columnIndex < nColumns; columnIndex++) {
    					HSSFCell cell = row.createCell(columnIndex);	
    					qsmAbr.setColumnValue(cell,rowitem.getRowType(), rowitem.getItemTbl(),columnIndex);	
    				}
    				rowitem.dereference();
    			}
    			itemVct.clear();
    			//break; must look in all geos for items, duplicates will be skipped IN4397871
    		}
    	}
    	rowKeysSet.clear();
    	rowKeysSet = null;

    	if (rowIndex==0){
    		addDebug("No data found to create rows in ss");
    		wb=null;
    	}	
    }
    
    /******************************************** 
     *  CQ00016165
     *  The Files will be named as follows:
1.	'EACM_TO_QSM_'
2.	The DB2 DTS for T2 with spaces and special characters replaced with an underscore
3.	'.txt'
     */
    private void setFileName(){
    	// FILE_PREFIX=EACM_TO_QSM_
    	String fileprefix = ABRServerProperties.getFilePrefix(m_abri.getABRCode());
    	StringBuffer sb = new StringBuffer(fileprefix.trim());
    	String dts = getTime2DTS();
    	// replace special characters
    	dts = dts.replace(' ', '_'); 
    	sb.append(dts+".txt");
    	String dir = getABRItem().getDirectory();
    	if (!dir.endsWith("/")){
    		dir=dir+"/";
    	}
    	ffFileName = sb.toString();
    	ffPathName = dir+ffFileName;
    	addDebug("ffPathName: "+ffPathName+" ffFileName: "+ffFileName);
    }
    /********************************************
     * Build a text file using all 
     *  CQ00016165
     *  The first record in the flat file is as follows:
	Row Type – column 01:01 – “1”
	Interface Date – column 03:12
	Interface Time – column 14:28
	RFA Number – column 30:35
	Announcement Type – column 37:39
	“New” for a new announcement
	“End” for a withdrawal announcement
	“SBD” for a special bid
The second record in the flat file is taken from the Spreadsheet column E with the columns 
specified by columns F and G.

The rest of the records are data based on the type of data described above.


Interface Date and Interface Time are the DB2 Date/Time Stamp of T2. The DB2 format is 
yyyy-mm-dd hh:mm:ss.uuuuuu and hence can be used to supply both the Interface Date and Interface Time.

1 2009-01-13 11:33:42.123456  43635 NEW
2 DATE______ TIME___________ RFANUM ANNDATE___ GADATE____ IBMPART_____ DESCRIPTION___________________ DIV P I PLT ID_ CLAS B USPN___ SEOTYPE___ ENTITYID__

     * @throws IOException 
     */
    private void generateFlatFile(EntityItem rootEntity) throws IOException
    {
    	// generate file name
    	setFileName();
    	
    	FileOutputStream fos = new FileOutputStream(ffPathName);
    	// OutputStreamWriter will convert from characters to bytes using
    	// the specified character encoding or the platform default if none
    	// is specified.  Output as unicode
    	OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");
   	
       	// build the text file 	
    	String dtsDb = getTime2DTS(); // format this
    	String date = dtsDb.substring(0, 10);
    	String time = dtsDb.substring(11,19);
    	String sec = dtsDb.substring(19);
    	time = time.replace('.',':');
		String dts = date+" "+time+sec;
  	
    	// add row 1
    	wOut.write("1 "+dts+" "+qsmAbr.getRowOne(rootEntity)+NEWLINE);
    	int rowIndex = 0;
		int nColumns = qsmAbr.getColumnCount();
		
		StringBuffer sb = new StringBuffer();
		addDebug("generateFlatFile: creating FF");
		// first two columns are not part of the ss output and not in the 'template'
		sb.append("2 "+qsmAbr.getRowTwoPrefix());
		// output column headers
		for (int columnIndex = 0; columnIndex < nColumns; columnIndex++) {
			sb.append(" "+XLColumn.formatToWidth(qsmAbr.getFFColumnLabel(columnIndex), 
					qsmAbr.getColumnWidth(columnIndex)));
		}
		sb.append(NEWLINE);
		wOut.write(sb.toString());
		wOut.flush();
 
	  	HashSet rowKeysSet = new HashSet(); //IN4397871
		// get rows for each GEO
		for (int g=0; g<GEOS.length; g++){
			addDebug("generateOutputAllff: looking for GEO "+GEOS[g]);
			Vector itemVct = qsmAbr.getRowItems(listT2,null, GEOS[g], this);
			//only output information once - if a second geo is found, skip it
			if(itemVct.size()>0){
				for (int i=0; i<itemVct.size(); i++) {
					XLRow rowitem = (XLRow)itemVct.elementAt(i);
    				if(rowKeysSet.contains(rowitem.getRowKey())){ //IN4397871
    					// already added this row - must have had duplicate geo match
    		    		addDebug("generateOutputAllff: already added "+rowitem.getRowKey());
    					rowitem.dereference();
    					continue;
    				}
    				rowKeysSet.add(rowitem.getRowKey());
					sb = new StringBuffer();
					sb.append("5 "+dts); 
					rowIndex++;
					for (short columnIndex = 0; columnIndex < nColumns; columnIndex++) {						
						sb.append(" "+ 
								qsmAbr.getColumnValue(rowitem.getRowType(), rowitem.getItemTbl(),columnIndex));	
					}
					sb.append(NEWLINE);
					wOut.write(sb.toString());
					wOut.flush();

					rowitem.dereference();
				}
				itemVct.clear();
    			//break; must look in all geos for items, duplicates will be skipped IN4397871
			}
		}
		
		rowKeysSet.clear();
		rowKeysSet = null;
			
		if (rowIndex==0){
			addDebug("No data found to create rows in text file");
			ffFileName = null;
		}
		
		 wOut.close(); 
    } 
    /**
     * username:password@ftp.whatever.com/dir/file.zip;
     * write this file to the specified server
     *  script "Usage: ftpfile.sh <host> <user id> <password> <filename>"
     * @throws IOException
     * @throws InterruptedException  
     */
    private void ftpFile() throws IOException, InterruptedException {
		String script = ABRServerProperties.getScript(m_abri.getABRCode());
		String ftpuser = ABRServerProperties.getFtpUserid(m_abri.getABRCode());
		String ftppw = ABRServerProperties.getFtpPassword(m_abri.getABRCode());
		String ftphost = ABRServerProperties.getFtpHost(m_abri.getABRCode());
		if (script.length()>0){
			addDebug("FTP of "+ffPathName+" will be done with a script: "+script);
			m_db.debug(D.EBUG_INFO, this+" FTP of "+ffPathName+" will be done with a script: "+script+
					" to "+ftphost+" "+ftpuser);
			String s = ABRUtil.runScript(script+" "+ftphost+" "+ftpuser+" "+ftppw+" "+ffPathName);
			addDebug("script results: "+s);
			m_db.debug(D.EBUG_INFO, this+" script results "+s);
		}else{
			//FTP_URL=opicmadm:mice8chs@eacm.lexington.ibm.com/bala/
			if (ftpuser.length()>0&&ftppw.length()>0&&ftphost.length()>0){
				String urlStr = ftpuser.trim()+":"+ftppw.trim()+"@"+ftphost.trim();
				addDebug("ftpFile urlStr: "+urlStr);

				if (!urlStr.endsWith("/")){
					urlStr=urlStr+"/";
				}
				//urlStr = urlStr+ffFileName+";type=a";
				urlStr = urlStr+ffFileName; // remove type=a, it is getting left on the file name for some reason
				addDebug("ftpFile writing "+ffPathName+" to urlStr: "+urlStr);
				m_db.debug(D.EBUG_INFO, this+" ftpFile writing "+ffPathName+" to urlStr: "+urlStr);
				ABRUtil.ftpWriteFile(urlStr, ffPathName);
			}else{
				addDebug("ftpFile.urlStr was not valid user:"+ftpuser+" host:"+ftphost);
				m_db.debug(D.EBUG_ERR, this+" ftpFile.urlStr was not valid user:"+ftpuser+" host:"+ftphost);
			}
		}
    }
    /********************************************
     * output the ss by attaching it to the dg entity
     * @param ei
     * @throws IOException
     */
    private void outputSS(EntityItem ei) throws IOException
    {	
    	// output the ss
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	String xlsname = ei.getKey()+getNow()+"_"+ "QSM"+outputType+".xls";
    	wb.write(bos);

    	byte[] tmp = bos.toByteArray();

    	ByteArrayOutputStream os = new ByteArrayOutputStream();
    	ZipOutputStream zos = new ZipOutputStream(os);
    	zos.putNextEntry(new ZipEntry(xlsname));
    	zos.write(tmp);
    	zos.closeEntry();
    	zos.flush();
    	zos.close();

    	setAttachByteForDG(os.toByteArray());
    	getABRItem().setFileExtension("zip");

    	if (getABRItem().getKeepFile()){//for debug write it to file
    		try {
    			FileOutputStream fos = new FileOutputStream(
    					getABRItem().getFileName()+".zip");
    			zos = new ZipOutputStream(fos);
    			zos.putNextEntry(new ZipEntry(xlsname));
    			zos.write(tmp);
    			zos.closeEntry();
    			zos.flush();
    			zos.close();
    			fos.flush();
    			fos.close();            
    		} catch (Throwable t) {
    			System.err.println("Error while writing debug zipfile: " + t);
    			t.printStackTrace();
    		}	
    	}	
    }
    /***********************************************
     * Update the PDH with the values in the vector, do all at once
     */
    private void updatePDH()
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    java.rmi.RemoteException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    COM.ibm.eannounce.objects.EANBusinessRuleException
    {
    	addDebug(" updating PDH for vctReturnsEntityKeys: "+vctReturnsEntityKeys);
    	if(vctReturnsEntityKeys.size()>0)
    	{
    		try	{
    			m_db.update(m_prof, vctReturnsEntityKeys, false, false);

    			for (int i=0; i<vctReturnsEntityKeys.size(); i++){
    				ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
    				// must commit text chgs.. not sure why
    				for (int ii=0; ii<rek.m_vctAttributes.size(); ii++){
    					Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(ii);
    					if (attr instanceof Text){
    						EntityGroup egrp = m_elist.getParentEntityGroup();
    						EntityItem item = egrp.getEntityItem(rek.getEntityType()+rek.getEntityID());
    						item.commit(m_db, null);
    					}
    				}
    			}
    		}
    		finally {
    			vctReturnsEntityKeys.clear();
    			m_db.commit();
    			m_db.freeStatement();
    			m_db.isPending("finally after updatePDH");
    		}
    	}
    }
    /**********************************************************************************
    * get timestamp(s) for ABR when attribute went to specified value
    * The extract should use a Time (T2) equal to the VALFROM of most recent value of 
    * QSMRPTABRSTATUS = 0050 (In Process).
    * 
    * T1 = VALFROM of the ABR attribute value of 0050 (In Process) that resulted in 
    * the first prior value of 0030 (Success).
    */
    private void getChangeTimes()
    throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	String flagcode = "0050";
    	String attCode = m_abri.getABRCode();
        dtsVct = new Vector(1);
        Vector chiVct = new Vector(1);

        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
        addDebug("getChangeTimes entered for "+rootEntity.getKey()+" "+attCode+" flag: "+flagcode);
        EANAttribute att = rootEntity.getAttribute(attCode);
        if (att != null) {
            AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(m_db,
                m_elist.getProfile(), att);
            if (achg.getChangeHistoryItemCount()>0){
                for (int i=achg.getChangeHistoryItemCount()-1; i>=0; i--) {
                    chiVct.add(achg.getChangeHistoryItem(i));
                }

                Collections.sort(chiVct, new ChiComparator()); // Multiflag attr require sort
                outerloop:for (int i=0; i<chiVct.size(); i++){
                    AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)chiVct.elementAt(i);
                    addDebug("getChangeTimes "+attCode+"["+i+"] isActive: "+
                        achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
                        achi.getChangeDate()+" flagcode: "+achi.getFlagCode());
        
                    if (flagcode.equals(achi.getFlagCode())){
                        dtsVct.add(achi.getChangeDate());
                        // now look for previous success and use in-process before that one
                        for (int ii=i+1; ii<chiVct.size(); ii++){
                        	achi = (AttributeChangeHistoryItem)chiVct.elementAt(ii);
                            addDebug("getChangeTimes2 "+attCode+"["+ii+"] isActive: "+
                                achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
                                achi.getChangeDate()+" flagcode: "+achi.getFlagCode());
                
                            if ("0030".equals(achi.getFlagCode())){
                            	ii++;
                            	// check for data problems.. 
                            	if (ii<chiVct.size()){
                            		achi = (AttributeChangeHistoryItem)chiVct.elementAt(ii);
                            		addDebug("getChangeTimes3 "+attCode+"["+ii+"] isActive: "+
                            				achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
                            				achi.getChangeDate()+" flagcode: "+achi.getFlagCode());
                            		dtsVct.add(achi.getChangeDate());
                            	}else{
                            		addDebug("ERROR: getChangeTimes for "+rootEntity.getKey()+" "+attCode+" did not have a previous inprocess flag");
                            		dtsVct.add(achi.getChangeDate()); // use one from last success
                            	}
                            	break outerloop;
                            }
                        }
                    }
                }

                chiVct.clear();
            } // has history items
            addDebug("getChangeTimes dts "+rootEntity.getKey()+" "+dtsVct);
        } else {// status attr !=null
            addDebug("ERROR: getChangeTimes for "+rootEntity.getKey()+" "+attCode+"  was null use current time");
            dtsVct.add(getNow());
        }
    }

    /**********************************
    * get last timestamp - now it is the 'inprocess' timestmp
    */
    private String getTime2DTS() { return (String)dtsVct.firstElement(); }

    /**********************************
    * get prior timestamp
    */
    private String getTime1DTS() {
        String prior = m_strEpoch;
        if (dtsVct.size()>1){
           prior = (String)dtsVct.lastElement();
        }
        return prior;
    }

    /***********************************************************
     * The Derived RFA number will be generated as follows:
     * -	A 6 character attribute with the current highest value will be saved in the PDH (entity = QSMRFANUM and attributecode = QSMRFANUMBER) with an initial value of '000000'
     * -	The ABR will pick up the current highest value, increase it by one using a unique number system and save it in the SEO and as the new highest value
     * -	The unique number system consists of Positions 2 through 6 of this attribute will be 0 through 9 and A through Z
     * -	Position 1 will be the same except it will not include either '8', '9' or 'E'
     * @return
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    private String getRFANumber() throws
    	java.sql.SQLException,
    	COM.ibm.opicmpdh.middleware.MiddlewareException
    {
    	int id = m_db.getNextEntityID(getProfile(), "QSMRFANUM");
    	int origid = id;
    	// prevent 8,9 in leading digit
    	if (id>MAX_7ZZZZZ){
			// add fudge factor to bypass leading 8 and 9 digits
			id +=BYPASS_8_9;
			addDebug("getRFANumber: origid:"+origid+" "+Integer.toString(origid, 36).toUpperCase()+
					" adjusted 8,9 id:"+id+" value: "+Integer.toString(id, 36).toUpperCase());
		}
		if (id>MIN_DZZZZZ && id <MAX_F00000){
			// add fudge factor to bypass leading E digits
			id +=BYPASS_E;
			addDebug("getRFANumber: origid:"+origid+" "+Integer.toString(origid, 36).toUpperCase()+
					" adjusted E id:"+id+" value: "+Integer.toString(id, 36).toUpperCase());			
		}
		
    	String rfanum  = Integer.toString(id, 36).toUpperCase();
    	StringBuffer sb = new StringBuffer(rfanum);
    	while(sb.length()<IDLEN){
    		sb.insert(0,"0");
    	}

    	return sb.toString();
    }
    private boolean createReport = true;
    protected void setNoReport(){
    	addDebug("Report will not be sent");
    	createReport = false;
    }
    /**********************************
    * add msg to report output
    */
    protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

    /**********************************
     * used for output
     *
     */
    protected void addOutput(String resrcCode, Object args[])
    {
    	String msg = getBundle().getString(resrcCode);
    	if (args != null){
    		MessageFormat msgf = new MessageFormat(msg);
    		msg = msgf.format(args);
    	}

    	addOutput(msg);
    }
    /**********************************
    * add debug info as html comment
    */
    protected void addDebug(String msg) { rptSb.append("<!-- "+msg+" -->"+NEWLINE);}

    /**********************************
    * add error info and fail abr
    */
    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }

    /**********************************
    * used for error output
    * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
    * (root EntityType)
    *
    * The entire message should be prefixed with 'Error: '
    *
    */
    protected void addError(String errCode, Object args[])
    {
		EntityGroup eGrp = m_elist.getParentEntityGroup();
		setReturnCode(FAIL);

		//ERROR_PREFIX = Error: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), errCode, args);
	}

    /**********************************
    * used for warning or error output
    *
    */
    private void addMessage(String msgPrefix, String errCode, Object args[])
    {
		String msg = getBundle().getString(errCode);
		// get message to output
		if (args!=null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix+" "+msg);
	}

    /**********************************************************************************
    *  Get Name based on navigation attributes for root entity
    *
    *@return java.lang.String
    */
    private String getNavigationName() throws java.sql.SQLException, MiddlewareException
    {
        return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
    }

    /**********************************************************************************
    *  Get Name based on navigation attributes for specified entity
    *
    *@return java.lang.String
    */
    private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        // check hashtable to see if we already got this meta
        EANList metaList = (EANList)metaTbl.get(theItem.getEntityType());
        if (metaList==null)
        {
            EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
            metaList = eg.getMetaAttribute();  // iterator does not maintain navigate order
            metaTbl.put(theItem.getEntityType(), metaList);
        }
        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
            if (ii+1<metaList.size()){
                navName.append(" ");
            }
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
        return "1.14";
    }
    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        return "QSMRPTABRSTATUS";
    }

    /***********************************************
     *  Sets the specified Flag Attribute on the specified Entity
     *
     *@param    profile Profile
     *@param    strAttributeCode The Flag Attribute Code
     *@param    strAttributeValue The Flag Attribute Value
     */
     private void setFlagValue(Profile profile, String strAttributeCode, String strAttributeValue,
     	EntityItem item)
     {
         logMessage(getDescription()+" ***** "+item.getKey()+" "+strAttributeCode+" set to: " + strAttributeValue);
         addDebug("setFlagValue entered "+item.getKey()+" for "+strAttributeCode+" set to: " +
         	strAttributeValue);

 		// if meta does not have this attribute, there is nothing to do
         EANMetaAttribute metaAttr = item.getEntityGroup().getMetaAttribute(strAttributeCode);
         if (metaAttr==null) {
 			addDebug("setFlagValue: "+strAttributeCode+" was not in meta for "+item.getEntityType()+", nothing to do");
         	logMessage(getDescription()+" ***** "+strAttributeCode+" was not in meta for "+
         		item.getEntityType()+", nothing to do");
 			return;
 		}

         if(strAttributeValue != null)
         {
 			//get the current value
 			String curval = //getAttributeFlagEnabledValue(item,strAttributeCode);
 				PokUtils.getAttributeFlagValue(item,strAttributeCode);
 			if (strAttributeValue.equals(curval)){
 				addDebug("setFlagValue: "+strAttributeCode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
 				logMessage("setFlagValue: "+strAttributeCode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
 				return;
 			}

 			Vector vctAtts = null;
 			// look at each key to see if root is there yet
 			for (int i=0; i<vctReturnsEntityKeys.size(); i++){
 				ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
 				if (rek.getEntityID() == item.getEntityID() &&
 					rek.getEntityType().equals(item.getEntityType())){
 					vctAtts = rek.m_vctAttributes;
 					break;
 				}
 			}
 			if (vctAtts ==null){
 				ReturnEntityKey rek = new ReturnEntityKey(item.getEntityType(),item.getEntityID(), true);
 				vctAtts = new Vector();
 				rek.m_vctAttributes = vctAtts;
 				vctReturnsEntityKeys.addElement(rek);
 			}

 			SingleFlag sf = new SingleFlag (profile.getEnterprise(), item.getEntityType(), item.getEntityID(),
 				strAttributeCode, strAttributeValue, 1, m_cbOn);

 			vctAtts.addElement(sf);
         }
     }

 
    /**********************************************************************************
     * This class is used to sort ChangeHistoryItem based on timestamp
     */
    private class ChiComparator implements java.util.Comparator
    {
        public int compare(Object o1, Object o2) {
            ChangeHistoryItem chi1 = (ChangeHistoryItem)o1;
            ChangeHistoryItem chi2 = (ChangeHistoryItem)o2;
            return chi2.getChangeDate().compareTo(chi1.getChangeDate()); // in descending order
        }
    }	
}
