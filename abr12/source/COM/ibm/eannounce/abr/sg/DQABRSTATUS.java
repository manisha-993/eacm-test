// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
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
* DQABRSTATUS class
* This class is an abstract base class for DataQuality ABRs
* derived classes must implement doDQChecking()
*  
* From "SG FS ABR Data Quality 20080916.doc"
*
* DATAQUALITY transitions from 'Draft to Ready for Review',
*   'Change Request to Ready for Review' and from 'Ready for Review to Final' will queue the ABR.
* If the ABR requested promotion of STATUS fails, the ABR will reset the DATAQUALITY to the
* prior value (see below).
*
* STATUS:  This is a state machine that has the following values which are only set by the ABR
*   Draft (default value on create)
*   Ready for Review
*   Change Request
*   Final
*
* Results:
*   The ABR will run and take action based on the Current Value of 'STATUS'.
*   The ABR will produce a report that is processed by 'Subscription/Notification' as described in the ADD for 30b.
*
* If there is no criteria specified, then the ABR should consider the checking is 'Passed'
*
* If the ABR checking is 'Passed', then the ABR will set STATUS = DATAQUALITY.
*
* If the ABR checking is 'Failed', then the ABR will set DATAQUALTY = STATUS.
*
* Although the LONGDESCRIPTIONs for these attributes are identical, the FLAG codes are not.
*   AttributeCode   FlagCode    LongDescription
*   ANNSTATUS       0010        Draft
*   ANNSTATUS       0020        Final
*   ANNSTATUS       0040        Ready for Review
*   ANNSTATUS       0050        Change Request
*   DATAQUALITY     CHANGE      Change Request
*   DATAQUALITY     DRAFT       Draft
*   DATAQUALITY     FINAL       Final
*   DATAQUALITY     REVIEW      Ready for Review
*   FBSTATUS        0010        Draft
*   FBSTATUS        0020        Final
*   FBSTATUS        0040        Ready for Review
*   FBSTATUS        0050        Change Request
*   MMSTATUS        0010        Draft
*   MMSTATUS        0020        Final
*   MMSTATUS        0040        Ready for Review
*   MMSTATUS        0050        Change Request
*   STATUS          0010        Draft
*   STATUS          0020        Final
*   STATUS          0040        Ready for Review
*   STATUS          0050        Change Request
*
* Domain support is specified in abr.server.properties
*Consider    Code    LongDescription
*Yes 0050    xSeries
*Yes 0090    SAN
*Yes 0150    CSP
*Yes 0190    Midrange Disk
*Yes 0210    SANB
*Yes 0230    Mid Range Tape
*Yes 0240    Complementary Stor Products
*Yes 0310    SANM
*Yes 0330    Mid Range Tape 2
*Yes 0340    Midrange Disk 2
*Yes 0360    Complementary Stor Products 2
*Yes 0390    Converged Products
*
*ANNABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
*/
// DQABRSTATUS.java,v
// Revision 1.31  2011/01/13 16:57:24  wendy
// Add getRFRQueuedValue
//
// Revision 1.30  2009/07/30 18:41:27  wendy
// Moved BH DQ ABRs to diff pkg
//
// Revision 1.28  2009/02/05 13:42:49  wendy
// CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
// Revision 1.27  2008/09/25 13:59:25  wendy
// CQ00006066-WI updates QSM2XPERWEEK chg to '0090'
//
// Revision 1.26  2008/09/22 15:06:18  wendy
// CQ00006066-WI updates
//
// Revision 1.25  2008/05/01 11:59:29  wendy
// Added SVCPRODSTRUCT NDN
//
// Revision 1.24  2008/04/24 21:14:31  wendy
// "SG FS ABR Data Quality 20080422.doc" spec updates
//
// Revision 1.23  2008/04/15 13:57:08  wendy
// Add more debug info
//
// Revision 1.22  2008/04/11 19:20:14  wendy
// changes for spec SG FS ABR Data Quality 200803xx.doc
//
// Revision 1.21  2008/03/31 18:51:53  wendy
// Add check for inprocess before setting an ABR to queued
//
// Revision 1.20  2008/03/13 18:21:01  wendy
// Correct country check, root must be subset in some cases
//
// Revision 1.19  2008/03/12 16:03:02  wendy
// Fix for production PRODSTRUCTs and status check
//
// Revision 1.18  2008/03/10 16:17:27  wendy
// Add pulling VE for PRODSTRUCT to get NDN if needed
//
// Revision 1.17  2008/02/13 19:58:49  wendy
// Make ABRWAITODSx into constants, allow easier change in future
//
// Revision 1.16  2008/01/30 19:39:17  wendy
// Cleanup RSA warnings
//
// Revision 1.15  2008/01/21 17:26:55  wendy
// Default null status to final
//
// Revision 1.14  2008/01/20 23:20:29  wendy
// Always do post-processing despite domain
//
// Revision 1.13  2007/12/19 17:35:01  wendy
// Made domainInList a method, moved check to derived classes
//
// Revision 1.12  2007/11/16 22:17:22  nancy
// Chgs for spec "SG FS ABR Data Quality 20071115.doc"
//
// Revision 1.11  2007/11/05 21:41:35  wendy
// Fix propagateOStoWWSEO()
//
// Revision 1.10  2007/10/25 20:38:28  wendy
// Spec chgs
//
// Revision 1.9  2007/10/24 21:02:18  wendy
// Added another checkStatus()
//
// Revision 1.8  2007/10/23 17:47:12  wendy
// Spec changes
//
// Revision 1.7  2007/10/16 13:38:52  wendy
// SAPLABRSTATUS meta chgs
//
// Revision 1.6  2007/10/15 20:22:35  wendy
// QueueSAPL when PDHDOMAIN is not in list of domains
//
// Revision 1.5  2007/10/15 18:45:31  settles
// Fix RPQ Avail entity list
//
// Revision 1.5  2007/10/15 13:18:37  settles
// Fix RPQAvails entity list population.
//
// Revision 1.4  2007/10/15 16:36:01  wendy
// If PDHDOMAIN is not in the domain list, do not do anything but advance status.
//
// Revision 1.3  2007/10/02 17:05:37  settles
// comment out suppression of messages
//
// Revision 1.2  2007/09/14 17:43:54  wendy
// Updated for GX
//
// Revision 1.1  2007/08/17 15:59:01  wendy
// RQ0713072645 base class for DQ ABRs
//
public abstract class DQABRSTATUS extends PokBaseABR
{
    private StringBuffer rptSb = new StringBuffer();
	private Hashtable metaTbl = new Hashtable();

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    private static final Hashtable STATUS_TBL;
    private static final Hashtable DQ_TBL;
    private ResourceBundle dqBundle = null;

    protected static final String STATUS_DRAFT = "0010";
    protected static final String STATUS_FINAL = "0020";
    protected static final String STATUS_R4REVIEW   = "0040";
    protected static final String STATUS_CHGREQ = "0050";
    protected static final String DQ_DRAFT = "DRAFT";
    protected static final String DQ_FINAL = "FINAL";
    protected static final String DQ_R4REVIEW   = "REVIEW";
    protected static final String DQ_CHGREQ = "0050";
/*
DATAQUALITY 0050    Change Request
DATAQUALITY DRAFT   Draft
DATAQUALITY FINAL   Final
DATAQUALITY REVIEW  Ready for Review

STATUS  0010    Draft
STATUS  0020    Final
STATUS  0040    Ready for Review
STATUS  0050    Change Request

*/
/*
COFCAT	100		Hardware
COFCAT	101		Software
COFCAT	102		Service

COFSUBCAT	126		System
*/
/*
SAPLABRSTATUS	0010	Untried
SAPLABRSTATUS	0020	Queued
SAPLABRSTATUS	0030	Passed
SAPLABRSTATUS	0040	Failed
SAPLABRSTATUS	0050	In Process
SAPLABRSTATUS	0060	Error - Update Unsuccessful
SAPLABRSTATUS	0070	Error - DG Not Available
SAPLABRSTATUS	0080	Error - ABR Internal Error
SAPLABRSTATUS	ABRENDOFDAY	Wait until End of the Day
SAPLABRSTATUS	ABRENDOFWEEK	Wait until End of the Week
SAPLABRSTATUS	ABRWAITODS 	Wait for Approved Data ODS
SAPLABRSTATUS	ABRWAITODS2	Wait for Data ODS

*/

    protected static final String HARDWARE = "100";
    protected static final String SOFTWARE = "101";
    protected static final String SERVICE = "102";
    protected static final String SYSTEM = "126";
    protected static final String BASE = "150";

    protected static final String ABR_QUEUED = "0020";
    protected static final String ABR_INPROCESS = "0050";
    protected static final String SAPLABR_QUEUED = "0020";

	// hold off on setting ABRWAITODSx, QUEUE immediately for now
	protected static final String ABRWAITODS = ABR_QUEUED;//"ABRWAITODS";
	protected static final String ABRWAITODS2 = ABR_QUEUED;//"ABRWAITODS2";
	
	//CQ00016165 protected static final String QSM2XPERWEEK = "0090"; // CQ00006066-WI

	protected static final String SAPL_NOTREADY = "10";
	protected static final String SAPL_NA = "90";

	protected static final String PLANNEDAVAIL = "146";
	protected static final String LASTORDERAVAIL = "149";
	protected static final String FIRSTORDERAVAIL = "143";

    private static final int PAUSE_TIME=5000;
    private static final int DATE_ID = 11;

    private String failedStr = "Failed";  // hang onto this so dont have to get over and over
    private String dqCheck = "Failed";  // Passed, Failed, or Not Required

    private Vector vctReturnsEntityKeys = new Vector();

    private static final Hashtable SAPL_TRANS_TBL;
    private String navName = "";
    private boolean bdomainInList = false;
    protected boolean domainInList() {return bdomainInList;}
    /**
     * CQ00016165 
     * ANNABRSTATUS_QSMRPTABRSTATUS_queuedValue=0090
     * @param abrcode
     * @return
     */
    protected String getQueuedValue(String abrcode){
    	return
    		COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRQueuedValue(
    				m_abri.getABRCode()+"_"+abrcode);

    }
	protected String getRFRQueuedValue(String abrcode){
		return
		COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRRFRQueuedValue(
				m_abri.getABRCode()+"_"+abrcode);

	}
	private static final Hashtable NDN_TBL;
	static{
		NDN_TBL = new Hashtable();
	/*
The NDN of PRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
FEATURE.FEATURECODE
	*/
		NDN ndnMdl = new NDN("MODEL", "(TM)");
		ndnMdl.addAttr("MACHTYPEATR");
		ndnMdl.addAttr("MODELATR");
		ndnMdl.addAttr("COFCAT");
		ndnMdl.addAttr("COFSUBCAT");
		ndnMdl.addAttr("COFGRP");
		ndnMdl.addAttr("COFSUBGRP");
		NDN ndnFc = new NDN("FEATURE", "(FC)");
		ndnFc.addAttr("FEATURECODE");
		ndnMdl.setNext(ndnFc);
		NDN_TBL.put("PRODSTRUCT",ndnMdl);
	/*
The NDN of SWPRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
SWFEATURE.FEATURECODE

	*/
		ndnMdl = new NDN("MODEL", "(TM)");
		ndnMdl.addAttr("MACHTYPEATR");
		ndnMdl.addAttr("MODELATR");
		ndnMdl.addAttr("COFCAT");
		ndnMdl.addAttr("COFSUBCAT");
		ndnMdl.addAttr("COFGRP");
		ndnMdl.addAttr("COFSUBGRP");
		ndnFc = new NDN("SWFEATURE", "(FC)");
		ndnFc.addAttr("FEATURECODE");
		ndnMdl.setNext(ndnFc);
		NDN_TBL.put("SWPRODSTRUCT",ndnMdl);

/*
The NDN of SVCPRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
SVCFEATURE.FEATURECODE
*/
		ndnMdl = new NDN("MODEL", "(TM)");
		ndnMdl.addAttr("MACHTYPEATR");
		ndnMdl.addAttr("MODELATR");
		ndnMdl.addAttr("COFCAT");
		ndnMdl.addAttr("COFSUBCAT");
		ndnMdl.addAttr("COFGRP");
		ndnMdl.addAttr("COFSUBGRP");
		ndnFc = new NDN("SVCFEATURE", "(FC)");
		ndnFc.addAttr("FEATURECODE");
		ndnMdl.setNext(ndnFc);
		NDN_TBL.put("SVCPRODSTRUCT",ndnMdl);


		SAPL_TRANS_TBL = new Hashtable();
		SAPL_TRANS_TBL.put("20", "40");  // Ready->Sent
		SAPL_TRANS_TBL.put("30", "40");  // Send->Sent
		SAPL_TRANS_TBL.put("40", "80");  // Sent->Update Sent
		SAPL_TRANS_TBL.put("80", "70");  // Update Sent->Update

        STATUS_TBL = new Hashtable();
        STATUS_TBL.put(DQ_DRAFT, STATUS_DRAFT);
        STATUS_TBL.put(DQ_FINAL, STATUS_FINAL);
        STATUS_TBL.put(DQ_R4REVIEW, STATUS_R4REVIEW);
        STATUS_TBL.put(DQ_CHGREQ, STATUS_CHGREQ);

        DQ_TBL = new Hashtable();
        DQ_TBL.put(STATUS_DRAFT, DQ_DRAFT);
        DQ_TBL.put(STATUS_FINAL, DQ_FINAL);
        DQ_TBL.put(STATUS_R4REVIEW, DQ_R4REVIEW);
        DQ_TBL.put(STATUS_CHGREQ, DQ_CHGREQ);
    }

	private static final String PROD_FIX_DTS="2008-03-12-00.00.00.000000";

    /**********************************
    * get the resource bundle
    */
    protected ResourceBundle getBundle() {
		return dqBundle;
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
             "<tr><th>Data quality check: </th><td>{5}</td></tr>"+NEWLINE +
             "</table>"+NEWLINE+
            "<!-- {6} -->" + NEWLINE;

        MessageFormat msgf;
        EntityItem rootEntity;
        String rootDesc=null;
     	String statusFlag=null;
     	String dataQualityFlag=null;
        Object[] args = new String[10];
        println(EACustom.getDocTypeHtml()); //Output the doctype and html
        try
        {
            String VEname = "";
            long startTime = System.currentTimeMillis();

            start_ABRBuild(false); // dont pull VE yet

            // there seems to be a race condition, the abr is running before all of the
            // attributes are saved
            // if ABR history is before DATAQUALITY,do extract with later profile
            rootEntity = checkTimestamps();

            dataQualityFlag = getAttributeFlagEnabledValue(rootEntity, "DATAQUALITY");
            statusFlag = getAttributeFlagEnabledValue(rootEntity, getStatusAttrCode());
            addDebug(getStatusAttrCode()+": "+
                PokUtils.getAttributeValue(rootEntity, getStatusAttrCode(),", ", "", false)+" ["+statusFlag+"] "+
                "DATAQUALITY: "+
                PokUtils.getAttributeValue(rootEntity, "DATAQUALITY",", ", "", false)+" ["+dataQualityFlag+"] ");

            if(null == statusFlag || statusFlag.length()==0)  {
                statusFlag = STATUS_FINAL; // default to final
            }
            if(null == dataQualityFlag || dataQualityFlag.length()==0){
                dataQualityFlag = STATUS_FINAL; // default to final
            }

            // check if pdhdomain is in domain list for this ABR
            domainNeedsChecks(rootEntity);

			// attempt to improve performance, dont pull VE if it isnt needed
            if (isVEneeded(statusFlag)) {
                VEname = m_abri.getVEName();
                // checking timestamps may have changed profile, so get it from the rootEntity
                m_elist = m_db.getEntityList(rootEntity.getProfile(),
                    new ExtractActionItem(null, m_db, rootEntity.getProfile(),VEname),
                    new EntityItem[] { new EntityItem(null, rootEntity.getProfile(), getEntityType(), getEntityID()) });
            }else{
                 // just need a list with root entity
                 VEname = "dummy";
            }

            addDebug("Time to get VE: "+(System.currentTimeMillis()-startTime)+" (mseconds)");

            setControlBlock(); // needed for attribute updates

            //get properties file for the base class
            dqBundle = ResourceBundle.getBundle(DQABRSTATUS.class.getName(), getLocale(m_prof.getReadLanguage().getNLSID()));

            // debug display list of groups
            addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " + getEntityType() + ":" + getEntityID()+
                " extract: "+VEname+" valon: "+rootEntity.getProfile().getValOn()+NEWLINE + PokUtils.outputList(m_elist));

            //Default set to pass
            setReturnCode(PASS);

            failedStr = dqBundle.getString("FAILED"); // "Failed"
            dqCheck = failedStr;  // overwrite local copy with bundle version

            //NAME is navigate attributes
            navName = getNavigationName();

            // get root from VE
            rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
            rootDesc = m_elist.getParentEntityGroup().getLongDescription();

// fixme remove this.. avoid msgs to test userid
//setCreateDGEntity(false);

            /* Note the ABR is only called when
            * DATAQUALITY transitions from 'Draft to Ready for Review',
            *   'Change Request to Ready for Review' and from 'Ready for Review to Final'
            * it can also be queued when SAPL goes to 'Send' or 'Update'
            */
            if (statusFlag.equals(STATUS_FINAL)){
                addDebug("Status already final, bypassing checks");
                // already final, so handle this processing
                dqCheck= dqBundle.getString("NOT_REQ"); //"Not Required";
                doAlreadyFinalProcessing();
            }
            else{
				if(domainInList()){
                	doDQChecking(rootEntity,statusFlag);
				}else{
        			addDebug("No checking required for domain");
				}

                if(getReturnCode() == PASS){
                    //Advance STATUS to match DATAQUALITY (diff flag codes)
                    String flagCode = (String) STATUS_TBL.get(dataQualityFlag);
                    setFlagValue(m_elist.getProfile(),getStatusAttrCode(), flagCode);
                    dqCheck=dqBundle.getString("PASSED"); // "Passed";

					//if (domainInList()){ // only do this processing if domain was in the list
			//always do the post-processing now, queued abrs will now check if domain is in their list
					// if status=final - do other processing
					if (flagCode.equals(STATUS_FINAL)){
						completeNowFinalProcessing();
					}
					// if status=readyForReview - do other processing
					else if (flagCode.equals(STATUS_R4REVIEW)){
						completeNowR4RProcessing();
					}
					/*}else{
        				addDebug("Domain processing bypassed because domain was not in the list of domains requiring processing");
						// if status=final - do processing for other domains
						if (flagCode.equals(STATUS_FINAL)){
	        				completeNowFinalProcessingForOtherDomains();
						}
					}*/
                }else{
                    // error msg was generated when set to fail
                    //Reset DATAQUALITY to match STATUS (diff flag codes)
                    String flagCode = (String) DQ_TBL.get(statusFlag);
					// remove any previous values
					for (int i=0; i<vctReturnsEntityKeys.size(); i++){
						ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
						if (rek.getEntityID() == getEntityID() &&
							rek.getEntityType().equals(getEntityType())){
								rek.m_vctAttributes.clear();
								break;
						}
					}
                    setFlagValue(m_elist.getProfile(),"DATAQUALITY", flagCode);
                }
            }
            // handle all pdh updates at once.. all work or all fail
            updatePDH(m_elist.getProfile());
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

            // attempt to reset DATAQUALITY to match STATUS (diff flag codes)
            if (statusFlag!=null){
                String flagCode = (String) DQ_TBL.get(statusFlag);
                try{
					// remove any previous values
					for (int i=0; i<vctReturnsEntityKeys.size(); i++){
						ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
						if (rek.getEntityID() == getEntityID() &&
							rek.getEntityType().equals(getEntityType())){
								rek.m_vctAttributes.clear();
								break;
						}
					}
					setFlagValue(m_prof,"DATAQUALITY", flagCode);
            		updatePDH(m_elist.getProfile());
                }catch(Exception x){}
            }
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

        args[5] = dqCheck;
        args[6] = getABRVersion();

        rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>

        metaTbl.clear();
    }

    /**********************************
    * check for dataquality
    * each derived class must handle its own checking
    */
    protected abstract void doDQChecking(EntityItem rootEntity,String statusFlag) throws Exception;

	/**********************************
	* complete abr processing after status moved to final; (status was r4r)
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {}

	/**********************************
	* complete abr processing after status moved to readyForReview; (status was chgreq or draft)
	*/
	protected void completeNowR4RProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {}

	/**********************************
	* complete abr processing when status is already final; (dq was final too)
	*/
	protected void doAlreadyFinalProcessing() {}

	/**********************************
	* complete abr processing after status moved to final; (status was r4r) for
	* those ABRs that have domains that are not in the list
	*/
	protected void completeNowFinalProcessingForOtherDomains() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {}

    /**********************************
    * override if derived class has a different status attribute
    */
    protected String getStatusAttrCode() { return "STATUS";}

    /**********************************
    * override if derived class has different criteria
    */
    protected boolean isVEneeded(String statusFlag) {
        return true;
    }

    /**********************************
    * stringbuffer used for report output
    */
    protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

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
    * used for warning output
    * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
    * (root EntityType)
    *
    * The entire message should be prefixed with 'Warning: '
    *
    */
    protected void addWarning(String errCode, Object args[])
    {
		EntityGroup eGrp = m_elist.getParentEntityGroup();

		//WARNING_PREFIX = Warning: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(getBundle().getString("WARNING_PREFIX"));
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

    /**********************************
    * stringbuffer used for report output
    */
    protected void addDebug(String msg) { rptSb.append("<!-- "+msg+" -->"+NEWLINE);}

    /**********************************
    * Get root entity item, may not need to pull entire VE
    */
    private EntityItem getRootEntityItem(Profile profile) throws java.sql.SQLException, MiddlewareException
    {
        m_elist = m_db.getEntityList(profile,
            new ExtractActionItem(null, m_db, profile,"dummy"),
            new EntityItem[] { new EntityItem(null, profile, getEntityType(), getEntityID()) });

        EntityItem  item  = m_elist.getParentEntityGroup().getEntityItem(0);
        return item;
    }

    /**********************************
    * there seems to be a race condition, the abr is running before all of the
    * attributes are saved
    * if ABR timestamp is before DATAQUALITY,do another extract
    * give PDH time to catch up to taskmaster
    */
    private EntityItem checkTimestamps() throws Exception
    {
        EntityItem theItem = getRootEntityItem(getProfile());

        if(!checkTimestamps(theItem))   { // if false, timestamps are wrong
            // sleep for 5 seconds to make sure pdh is updated with all values
            try{
                addDebug("Pausing for "+PAUSE_TIME+" ms");
                Thread.sleep(PAUSE_TIME);
            }catch(InterruptedException ie){
                System.out.println(ie);
            }

            // set profile to end of day and try again
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
            String eod = df.format(Calendar.getInstance().getTime()).substring(0,DATE_ID)
                    +"23.59.59.999990"; // end of current day
            Profile profile = m_prof.getNewInstance(m_db);
            profile.setValOnEffOn(eod, eod);
            addDebug("ABR started before DATAQUALITY was updated, pull extract again using valon: "+
                profile.getValOn());

            theItem = getRootEntityItem(profile);
        }

        return theItem;
    }

    /**********************************
    * get timestamps for DATAQUALITY and ABR and compare them
    * if profile timestamp is before DATAQUALITY return false
    */
    private boolean checkTimestamps(EntityItem theItem) throws Exception
    {
        boolean isOk = true;
        //EntityItem theItem = theList.getParentEntityGroup().getEntityItem(0);
        Profile profile = theItem.getProfile();

        String abrDts;
        String dqDts;
        String valon = profile.getValOn();

        abrDts = getTimestamp(theItem,getABRCode());
        dqDts  = getTimestamp(theItem,"DATAQUALITY");

        addDebug("DTS for "+getABRCode()+": "+abrDts+" DATAQUALITY: "+dqDts);
        addDebug("DTS for valon: "+valon+" effon "+profile.getEffOn());
        // if dts of valon is before dts of dq, do another pull
//        if (dqDts.length()==0 ||        // DQ isn't set yet!
// abr may be called on entity that
// didnt have STATUS or DATAQUALITY attr previously, so assume these are FINAL
        if (dqDts.length()>0 &&        // DQ is set
            valon.compareTo(dqDts)<0)   // current valon is BEFORE DQ
        {
            isOk = false;
        }

        return isOk;
    }

    /**********************************
    * get last timestamp for specified attribute
    */
    private String getTimestamp(EntityItem theItem, String attrCode) throws Exception
    {
        RowSelectableTable itemTable = //theItem.getEntityItemTable(); cant use this because
            // prodstruct can edit parent model and the model isnt in this small extract, so bypass item chks
            new RowSelectableTable(theItem, theItem.getLongDescription());

        String dts = "";
        String keyStr = theItem.getEntityType() + ":" + attrCode;
        int row = itemTable.getRowIndex(keyStr);
        if (row < 0) {
            row = itemTable.getRowIndex(keyStr + ":C");
        }
        if (row < 0) {
            row = itemTable.getRowIndex(keyStr + ":R");
        }
        if (row != -1)
        {
            EANAttribute attStatus = (EANAttribute) itemTable.getEANObject(row, 1);
            if (attStatus != null)
            {
                int cnt = 0;
                AttributeChangeHistoryGroup ahistGrp = m_db.getAttributeChangeHistoryGroup(m_prof, attStatus);
                rptSb.append("<!--"+theItem.getKey()+" "+attrCode+" change history ");
                for (int ci= ahistGrp.getChangeHistoryItemCount()-1; ci>=0; ci--) // go from most recent to earliest
                {
                    ChangeHistoryItem chi = ahistGrp.getChangeHistoryItem(ci);
                    rptSb.append(NEWLINE+"AttrChangeHistoryItem["+ci+"] chgDate: "+chi.getChangeDate()+
                            " value: "+
                            chi.get("ATTVAL",true).toString()+
                            " flagcode: "+chi.getFlagCode()+
                            " user: "+chi.getUser());
                    cnt++;
                    if (cnt>3) {  // just last 3 is enough to look at
                        break;
                    }
                } // each history item
                rptSb.append(" -->"+NEWLINE);
                if (ahistGrp.getChangeHistoryItemCount()>0){
                    int last = ahistGrp.getChangeHistoryItemCount()-1;
                    dts = ahistGrp.getChangeHistoryItem(last).getChangeDate();
                }
            }
            else {
                addDebug("EANAttribute was null for "+attrCode+" in "+theItem.getKey());
            }
        }
        else {
            addDebug("Row for "+attrCode+" was not found for "+theItem.getKey());
        }

        return dts;
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
    protected String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        NDN ndn = (NDN)NDN_TBL.get(theItem.getEntityType());
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
        if (ndn!=null){ // must get other attr from parent and child entities
        	EntityList psList = null;
        	StringBuffer sb = new StringBuffer();
			EntityItem ei = getNDNitem(theItem,ndn.getEntityType());
        	// performance issue with bringing back too many features and models for prodstruct error msg
			if (theItem.getEntityType().equals("PRODSTRUCT")){
				if (ei==null){
					addDebug("NO entity found for ndn.getEntityType(): "+ndn.getEntityType()+
						" pulling small VE for this "+theItem.getKey());
					// pull VE with just this prodstruct
					String PS_VE = "EXRPT3FM";  // just feature->ps->model
					psList = m_db.getEntityList(theItem.getProfile(),
						new ExtractActionItem(null, m_db, theItem.getProfile(),PS_VE),
						new EntityItem[] { new EntityItem(null, theItem.getProfile(), theItem.getEntityType(),
							theItem.getEntityID()) });

					theItem = psList.getParentEntityGroup().getEntityItem(0);
					ei = getNDNitem(theItem,ndn.getEntityType());
				}
			}

			if (ei!=null){
				sb.append("("+ndn.getTag());
				for (int y=0; y<ndn.getAttr().size(); y++){
					String attrcode = ndn.getAttr().elementAt(y).toString();
					sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
					if (y+1<ndn.getAttr().size()){
						sb.append(" ");
					}
				}
				sb.append(") ");
			}else{
				addDebug("NO entity found for ndn.getEntityType(): "+ndn.getEntityType());
			}
			ndn = ndn.getNext();
			if (ndn !=null){
				ei = getNDNitem(theItem,ndn.getEntityType());
				if (ei!=null){
					sb.append("("+ndn.getTag());
					for (int y=0; y<ndn.getAttr().size(); y++){
						String attrcode = ndn.getAttr().elementAt(y).toString();
						sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
						if (y+1<ndn.getAttr().size()){
							sb.append(" ");
						}
					}
					sb.append(") ");
				}else{
					addDebug("NO entity found for next ndn.getEntityType(): "+ndn.getEntityType());
				}
			}
			navName.insert(0,sb.toString());

			if (psList != null){
				psList.dereference();
			}
		} // end getting other entity info

        return navName.toString();
    }

    /**********************************************************************************
    *  Get Name based on navigation attributes for specified entity
    *
    *@return java.lang.String
    */
    protected String getTMFNavigationName(EntityItem theItem, EntityItem mdlItem, EntityItem featItem) throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        NDN ndn = (NDN)NDN_TBL.get(theItem.getEntityType());
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
        if (ndn!=null){ // must get other attr from parent and child entities
        	StringBuffer sb = new StringBuffer();
			EntityItem ei = mdlItem;
			sb.append("("+ndn.getTag());
			for (int y=0; y<ndn.getAttr().size(); y++){
				String attrcode = ndn.getAttr().elementAt(y).toString();
				sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
				if (y+1<ndn.getAttr().size()){
					sb.append(" ");
				}
			}
			sb.append(") ");

			ndn = ndn.getNext();
			if (ndn !=null){
				ei = featItem;
				sb.append("("+ndn.getTag());
				for (int y=0; y<ndn.getAttr().size(); y++){
					String attrcode = ndn.getAttr().elementAt(y).toString();
					sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
					if (y+1<ndn.getAttr().size()){
						sb.append(" ");
					}
				}
				sb.append(") ");
			}
			navName.insert(0,sb.toString());
		} // end getting other entity info

        return navName.toString();
    }

    /**********************************************************************************
    * Find entity item to use for building the navigation display name when more then
    * one entity is needed, like for PRODSTRUCT
    *
    *@return EntityItem
    */
    private EntityItem getNDNitem(EntityItem theItem,String etype){
		for (int i=0; i<theItem.getDownLinkCount(); i++){
			EntityItem ent = (EntityItem)theItem.getDownLink(i);
			if (ent.getEntityType().equals(etype)){
				return ent;
			}
		}
		for (int i=0; i<theItem.getUpLinkCount(); i++){
			EntityItem ent = (EntityItem)theItem.getUpLink(i);
			if (ent.getEntityType().equals(etype)){
				return ent;
			}
		}
		return null;
	}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.31";
    }

    /***********************************************
    * Sets the specified Text Attribute on the specified entity
    *
    *@param    profile Profile
    *@param _sAttributeCode
    *@param _sAttributeValue
    *@param eitem
    */
    protected void setTextValue(Profile profile, String _sAttributeCode, String _sAttributeValue,
    	EntityItem eitem)
    {
        logMessage(getDescription()+" ***** "+eitem.getKey()+" "+_sAttributeCode+" set to: " + _sAttributeValue);
        addDebug("setTextValue entered for "+eitem.getKey()+" "+_sAttributeCode+" set to: " + _sAttributeValue);

		// if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = eitem.getEntityGroup().getMetaAttribute(_sAttributeCode);
        if (metaAttr==null) {
			addDebug("setTextValue: "+_sAttributeCode+" was not in meta for "+eitem.getEntityType()+", nothing to do");
        	logMessage(getDescription()+" ***** "+_sAttributeCode+" was not in meta for "+
        		eitem.getEntityType()+", nothing to do");
			return;
		}

        if( _sAttributeValue != null ) {
			if (m_cbOn==null){
				setControlBlock(); // needed for attribute updates
			}
			ControlBlock cb = m_cbOn;
			if (_sAttributeValue.length()==0){ // deactivation is now needed
				EANAttribute att = eitem.getAttribute(_sAttributeCode);
				String efffrom = att.getEffFrom();
				cb = new ControlBlock(efffrom, efffrom, efffrom, efffrom, profile.getOPWGID());
				_sAttributeValue = att.toString();
				addDebug("setTextValue deactivating "+_sAttributeCode);
			}
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
			COM.ibm.opicmpdh.objects.Text sf = new COM.ibm.opicmpdh.objects.Text(profile.getEnterprise(),
				eitem.getEntityType(), eitem.getEntityID(), _sAttributeCode, _sAttributeValue, 1, cb);
			vctAtts.addElement(sf);
        }
    }

    /***********************************************
    *  Sets the specified Flag Attribute on the specififed Entity
    *
    *@param    profile Profile
    *@param    strAttributeCode The Flag Attribute Code
    *@param    strAttributeValue The Flag Attribute Value
    */
    protected void setFlagValue(Profile profile, String strAttributeCode, String strAttributeValue,
    	EntityItem item)
    {
        logMessage(getDescription()+" ***** "+item.getKey()+" "+strAttributeCode+" set to: " + strAttributeValue);
        addDebug("setFlagValue entered "+item.getKey()+" for "+strAttributeCode+" set to: " +
        	strAttributeValue);

		if (strAttributeValue!=null && strAttributeValue.trim().length()==0) {
			addDebug("setFlagValue: "+strAttributeCode+" was blank for "+item.getKey()+", it will be ignored");
			return;
		}
		
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

			// if this is queueing an abr, make sure it isnt 'in process' now
			if (metaAttr.getAttributeType().equals("A")){
				// if the specified abr is inprocess, wait
				checkForInProcess(profile,item,strAttributeCode);
			}

			if (m_cbOn==null){
				setControlBlock(); // needed for attribute updates
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

    // if the specified abr is inprocess, wait
	private void checkForInProcess(Profile profile,EntityItem item,String strAttributeCode){
		try{
			int maxTries = 0;
			String curval = // doesnt work on 'A' type attr getAttributeFlagEnabledValue(item,strAttributeCode);
			    PokUtils.getAttributeFlagValue(item,strAttributeCode);

			addDebug("checkForInProcess:  entered "+item.getKey()+" "+strAttributeCode+" is "+curval);

			if (ABR_INPROCESS.equals(curval)){
				DatePackage dpNow = m_db.getDates();
				// get current updates by setting to endofday
        		profile.setValOnEffOn(dpNow.getEndOfDay(),dpNow.getEndOfDay());

				while(ABR_INPROCESS.equals(curval) && maxTries<20){ // allow 10 minutes
					maxTries++;
					addDebug("checkForInProcess: "+strAttributeCode+" is "+curval+" sleeping 30 secs");
					Thread.sleep(30000);
					// get entity again
					EntityGroup eg = new EntityGroup(null,m_db, profile, item.getEntityType(), "Edit", false);
					EntityItem curItem = new EntityItem(eg, profile, m_db, item.getEntityType(), item.getEntityID());
					curval = PokUtils.getAttributeFlagValue(curItem,strAttributeCode);
					addDebug("checkForInProcess: "+strAttributeCode+" is now "+curval+" after sleeping");
				}
			}
		}catch(Exception exc){
			System.err.println("Exception in checkForInProcess "+exc);
			exc.printStackTrace();
		}
	}

    /***********************************************
    *  Sets the specified Flag Attribute on the Root Entity
    *
    *@param    profile Profile
    *@param    strAttributeCode The Flag Attribute Code
    *@param    strAttributeValue The Flag Attribute Value
    */
    protected void setFlagValue(Profile profile, String strAttributeCode, String strAttributeValue)
    {
    	EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		setFlagValue(profile, strAttributeCode, strAttributeValue, rootEntity);
    }

    /***********************************************
    * Sets the specified Multiflag Attribute on the specified entity
    *
    *@param    profile Profile
    *@param sAttributeCode
    *@param _sAttributeValue
    *@param strOldValue
    *@param eitem
    */
    protected void setMultiFlagValue(Profile profile, String sAttributeCode, String _sAttributeValue,
    	String strOldValue,	EntityItem eitem)
    {
        logMessage(getDescription()+" ***** "+eitem.getKey()+" "+sAttributeCode+" set to: " +
        	_sAttributeValue+" oldval "+strOldValue);
        addDebug("setMultiFlagValue entered for "+eitem.getKey()+" "+sAttributeCode+" set to: " +
        	_sAttributeValue+" oldval "+strOldValue);

		// if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = eitem.getEntityGroup().getMetaAttribute(sAttributeCode);
        if (metaAttr==null) {
			addDebug("setMultiFlagValue: "+sAttributeCode+" was not in meta for "+eitem.getEntityType()+", nothing to do");
        	logMessage(getDescription()+" ***** "+sAttributeCode+" was not in meta for "+
        		eitem.getEntityType()+", nothing to do");
			return;
		}

		if (_sAttributeValue == null){
			_sAttributeValue = "";
		}
		if (strOldValue ==null) {
			strOldValue="";
		}
		if (!_sAttributeValue.equals(strOldValue)) {
			if (m_cbOn==null){
				setControlBlock(); // needed for attribute updates
			}

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

			StringTokenizer st = new StringTokenizer(_sAttributeValue, "|");
			MultipleFlag mf = null;
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				mf = new MultipleFlag(profile.getEnterprise(),
					eitem.getEntityType(), eitem.getEntityID(), sAttributeCode, s, 1, m_cbOn);
				vctAtts.addElement(mf);
			}

			// to remove unwanted flags
			EANAttribute att = eitem.getAttribute(sAttributeCode);
			if (att!=null){ // if null, then was never set
				String efffrom = att.getEffFrom();
				ControlBlock	cbOff = new ControlBlock(efffrom, efffrom, efffrom, efffrom, profile.getOPWGID());

				st = new StringTokenizer(strOldValue, "|");
				while (st.hasMoreTokens()) {
					String s = st.nextToken();
					if (_sAttributeValue.indexOf(s) < 0) {
						mf = new MultipleFlag(profile.getEnterprise(),
							eitem.getEntityType(), eitem.getEntityID(), sAttributeCode, s, 1, cbOff);
						vctAtts.addElement(mf);
					}
				}
			}
        }else{
			addDebug(eitem.getKey()+" old and new values were the same for "+sAttributeCode);
		}
	}

    /***********************************************
    * Update the PDH with the values in the vector, do all at once
    *
    *@param    profile Profile
    */
    private void updatePDH(Profile profile)
    	throws java.sql.SQLException,
    	COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.rmi.RemoteException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.eannounce.objects.EANBusinessRuleException
    {
        logMessage(getDescription()+" updating PDH");
        addDebug("updatePDH entered for vctReturnsEntityKeys: "+vctReturnsEntityKeys);
        if(vctReturnsEntityKeys.size()>0)
        {
	        MessageFormat msgf;
	        Object[] args = new String[6];
            try
            {
                m_db.update(profile, vctReturnsEntityKeys, false, false);

				for (int i=0; i<vctReturnsEntityKeys.size(); i++){
					ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
					// must commit text chgs.. not sure why
					for (int ii=0; ii<rek.m_vctAttributes.size(); ii++){
						Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(ii);
						if (attr instanceof Text){
							EntityGroup egrp = null;
							if (rek.getEntityType().equals(getEntityType())){ // is root
								egrp = m_elist.getParentEntityGroup();
							}
							else{
								egrp = m_elist.getEntityGroup(rek.getEntityType());
							}

							EntityItem item = egrp.getEntityItem(rek.getEntityType()+rek.getEntityID());
							// must commit changes, not really sure why though
							item.commit(m_db, null);
						}
					}
				}
				try{  // dont allow an exception here to reset dataquality
					// output a message for each thing set
					for (int i=0; i<vctReturnsEntityKeys.size(); i++){
						ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
						EntityGroup egrp = null;
						if (rek.getEntityType().equals(getEntityType())){ // is root
							egrp = m_elist.getParentEntityGroup();
						}
						else{
							egrp = m_elist.getEntityGroup(rek.getEntityType());
						}

						EntityItem item = egrp.getEntityItem(rek.getEntityType()+rek.getEntityID());
						Hashtable mftbl = new Hashtable();

						for (int ii=0; ii<rek.m_vctAttributes.size(); ii++){
							Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(ii);
							String attrCode = attr.getAttributeCode();
							if (attrCode.equals("DATAQUALITY")){ // must be resetting DQ
								//DQ_RESET = Data quality was reset to {0}
								msgf = new MessageFormat(dqBundle.getString("DQ_RESET"));
								args[0] = PokUtils.getAttributeValue(item, getStatusAttrCode(),", ", "", false);
							}else if (attrCode.equals(getStatusAttrCode())){ // must be setting status
								//STATUS_SET = Status was set to {0}
								msgf = new MessageFormat(dqBundle.getString("STATUS_SET"));
								args[0] = PokUtils.getAttributeValue(item, "DATAQUALITY",", ", "", false);
							}else{
								//ATTR_SET = {0} was set to {1} for {2} {3}
								msgf = new MessageFormat(dqBundle.getString("ATTR_SET"));
								args[0] = PokUtils.getAttributeDescription(egrp, attrCode, attrCode);
								if (attr instanceof Text){
									ControlBlock attrCB = attr.getControlBlock();
									if (attrCB.getValFrom().equals(attrCB.getValTo()) &&
										attrCB.getValTo().equals(attrCB.getEffFrom()) &&
										attrCB.getEffFrom().equals(attrCB.getEffTo())){
										// attribute is getting deactivated
										args[1] = "Null";
									}else{
										args[1] = attr.getAttributeValue();
									}
								}else{
									args[1] = attr.getAttributeValue();
									// get flag description
									EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) egrp.getMetaAttribute(attrCode);
									if (mfa!=null){
										MetaFlag mf = mfa.getMetaFlag(attr.getAttributeValue());
										if (mf!=null){
											args[1] = mf.toString();
										}
									}else{
										addDebug("Error: "+attrCode+" not found in META for "+egrp.getEntityType());
									}
								}
								// build string of multipleflag values, only output once
								if (attr instanceof MultipleFlag){
									StringBuffer mfSb = (StringBuffer)mftbl.get(attrCode);
									if (mfSb==null){
										mfSb = new StringBuffer();
										mftbl.put(attrCode, mfSb);
									}
									if (mfSb.length()>0){
										mfSb.append(",");
									}

									ControlBlock attrCB = attr.getControlBlock();
									if (attrCB.getValFrom().equals(attrCB.getValTo()) &&
										attrCB.getValTo().equals(attrCB.getEffFrom()) &&
										attrCB.getEffFrom().equals(attrCB.getEffTo())){
										// attribute is getting deactivated leave mfsb empty
									}else{
										mfSb.append(args[1].toString());
									}
								}else{
									args[2] = egrp.getLongDescription();
									args[3] = getNavigationName(item);
								}
							}
							if (!(attr instanceof MultipleFlag)){
								addOutput(msgf.format(args));
							}
						}// end all rek attributes
						if (mftbl.size()>0){ // output multiflags now
							for (Enumeration e = mftbl.keys(); e.hasMoreElements();)
							{
								String attrCode = (String)e.nextElement();
								StringBuffer mfsb = (StringBuffer)mftbl.get(attrCode);
								//ATTR_SET = {0} was set to {1} for {2} {3}
								msgf = new MessageFormat(dqBundle.getString("ATTR_SET"));
								args[0] = PokUtils.getAttributeDescription(egrp, attrCode, attrCode);
								if(mfsb.length()==0){
									// attribute is getting deactivated
									args[1] = "Null";
								}else{
									args[1] = mfsb.toString();
								}
								args[2] = egrp.getLongDescription();
								args[3] = getNavigationName(item);
								addOutput(msgf.format(args));
							}
							mftbl.clear();
						}
					}
				}catch(Exception exc){
					exc.printStackTrace();
					addDebug("exception trying to output msg "+exc);
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
            addDebug("PDHDOMAIN did not include "+domains+", checking is bypassed ["+
                PokUtils.getAttributeValue(item, "PDHDOMAIN",", ", "", false)+"]");
        }
    }

    /**********************************************************************************
    * check status of this entitygroup - must be final
    *@param etype String of entitytype to check
    */
    protected void checkStatus(String etype) throws java.sql.SQLException, MiddlewareException
    {
		EntityGroup egrp = m_elist.getEntityGroup(etype);
		if (egrp ==null){
			throw new MiddlewareException(etype+" is missing from extract for "+m_abri.getVEName());
		}
		for (int i=0; i<egrp.getEntityItemCount(); i++){
            EntityItem item = egrp.getEntityItem(i);
			checkStatus(item);
		}
    }

    /**********************************************************************************
    * check status of this items in this Vector - must be final
    *@param itemVct Vector of EntityItems to check
    */
    protected void checkStatus(Vector itemVct) throws java.sql.SQLException, MiddlewareException
    {
		for (int i=0; i<itemVct.size(); i++){
			EntityItem item = (EntityItem)itemVct.elementAt(i);
			checkStatus(item);
		}
    }

	/*
	Wayne Kehrli: how hard is it to add the following
	Wayne Kehrli: if STATUS = Draft and entity last updated before 3/11/2008 - assume STATUS = Final
	Wendy: is this for all status checks?
	Wendy: all entity types?
	Wendy: biggest impact will be performance, i have to go get the timestamp in a separate api
	Wendy: would it be entityhistory or status-attributehistory?
	Wayne Kehrli: entity history
	Wayne Kehrli: current valfrom
	Wayne Kehrli: PRODSTRUCT only
	is STATUS=Final if the prodstruct entitySTATUS=Draft and was updated ON 3/11/2008 or just before 3/11/2008?
	before 3/12/2008
	Wayne Kehrli: all entity types:  if STATUS is not available, assume STATUS = Final
	for PRODSTRUCT only:  if STATUS = Draft and PRODSTRUCT entity last updated before 3/12/2008, then assume STATUS = Final
	*/
	private boolean wasUpdatedSinceDTS(EntityItem psitem) throws java.sql.SQLException, MiddlewareException
	{
		boolean wasUpdated = false;
		EntityChangeHistoryGroup eChg = new EntityChangeHistoryGroup(m_db, psitem.getProfile(), psitem);
		if (eChg ==null || eChg.getChangeHistoryItemCount()==0) {
			addDebug(psitem.getKey()+" No Change history found");
		}
		else{
			String lastchgdate = eChg.getChangeHistoryItem(eChg.getChangeHistoryItemCount()-1).getChangeDate();
			addDebug(psitem.getKey()+" last chgDate: "+lastchgdate+" vs. "+PROD_FIX_DTS);
			if (lastchgdate.compareTo(PROD_FIX_DTS)>=0){
				wasUpdated = true;
			}

			/*for (int i=0; i<eChg.getChangeHistoryItemCount(); i++)
			{
				ChangeHistoryItem chi = eChg.getChangeHistoryItem(i);
				addDebug(psitem.getKey()+" ChangeHistoryItem["+i+"] user: "+chi.getUser()+" chgDate: "+chi.getChangeDate()+" isValid: "+chi.isValid());
			}*/
		}

		return wasUpdated;
	}

    /**********************************************************************************
    * check status of this item - must be final
    * Fixed to handle 'old' data (i.e. data that now has a STATUS attribute but did not
    * prior to these changes.)
    *When checking the STATUS (e.g.VII. ANNOUNCEMENT B.1), if STATUS does not have a value (aka null),
    * then assume that it has a value of 0020 (Final).
	*@param item EntityItem to check
    */
    protected void checkStatus(EntityItem item) throws java.sql.SQLException, MiddlewareException
    {
		Object args[] = new Object[2];
		String status = getAttributeFlagEnabledValue(item , "STATUS");
		addDebug(item.getKey()+" check status "+status);
		if (status==null){
			status = STATUS_FINAL;
		}
		// production error with PRODSTRUCT.STATUS not being null preventing WWSEO going to Final
		// if STATUS = Draft and entity last updated before 3/12/2008 - assume STATUS = Final
		if (item.getEntityType().equals("PRODSTRUCT") && status.equals(STATUS_DRAFT)){
			addDebug(item.getKey()+" must get entityhistory and check date");
			if (!wasUpdatedSinceDTS(item)){
				addDebug(item.getKey()+" using Final for status because it was not updated after "+PROD_FIX_DTS);
				status = STATUS_FINAL;
			}
		}
		if (!STATUS_FINAL.equals(status)){
			addDebug(item.getKey()+" is not Final");

			//NOT_FINAL_ERR = {0} {1} is not Final.
			args[0] = item.getEntityGroup().getLongDescription();
			args[1] = getNavigationName(item);
			addError("NOT_FINAL_ERR",args);
		}
    }

    /**********************************************************************************
    * Given a root entity type, the path including the relative entity type is given.
    * This counts the number of Relatives of the EntityType specified via the path and
    * performs the comparison of this count to the # specified. This is a count of paths
    * to entities of the type specified so that even if the same instance of the entity
    * is linked multiple times it is counted multiple times.
	*
	* e.g.  FEATUREMON.QTY * CountOf( FEATUREMON-d: MON)  < = 1
	*
	* Note:  if FEATUREMON.QTY does not exist, then assume a default of 1
	*		A Feature can have a maximum of 1 Monitor.
	*
    */
    protected int getCount(String relatorType) throws MiddlewareException
    {
		int count = 0;

		EntityGroup relGrp = m_elist.getEntityGroup(relatorType);
		if (relGrp ==null){
			throw new MiddlewareException(relatorType+" is missing from extract for "+m_abri.getVEName());
		}
		if (relGrp.getEntityItemCount()>0){
			for(int i=0; i<relGrp.getEntityItemCount(); i++){
				int qty = 1;
				EntityItem relItem = relGrp.getEntityItem(i);
				EANAttribute attr = relItem.getAttribute("QTY");
            	if (attr != null){
					qty = Integer.parseInt(attr.get().toString());
				}
				count+=qty;
        		addDebug("getCount "+relItem.getKey()+" qty "+qty);
			}
		}

        addDebug("getCount Total count found for "+relatorType+" = "+count);
		return count;
	}

    /**********************************
    * RQ022507238
    * If STATUS is promoted to Final,
    *   then obtain the history of values for STATUS in order to determine the last date/time stamp (DTS)
    * that STATUS was Final.
    * If this is the first time that STATUS is Final, there is nothing to do; otherwise obtain the value
    * for SAPASSORTMODULE that was in effect at the "last DTS that STATUS was Final" and set SAPASSORTMODULEPRIOR
    * equal to that value.
    */
    protected void checkAssortModule() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		String sapAttr = "SAPASSORTMODULE";
		String sapAttrPrior = "SAPASSORTMODULEPRIOR";

        EANMetaAttribute metaAttr = m_elist.getParentEntityGroup().getMetaAttribute(sapAttrPrior);
        if (metaAttr==null) {
            throw new MiddlewareException(sapAttrPrior+" not found in META for "+rootEntity.getEntityType());
        }
        metaAttr = m_elist.getParentEntityGroup().getMetaAttribute(sapAttr);
        if (metaAttr==null) {
            throw new MiddlewareException(sapAttr+" not found in META for "+rootEntity.getEntityType());
        }

        // status will be final, just isn't yet.. find last occurance
        EANAttribute attStatus = rootEntity.getAttribute("STATUS");
        if (attStatus != null)
        {
            String lastfinaldate=null;
            AttributeChangeHistoryGroup histGrp = m_db.getAttributeChangeHistoryGroup(m_elist.getProfile(), attStatus);
            addDebug("checkAssortModule: ChangeHistoryGroup for "+rootEntity.getKey()+" Attribute: STATUS");
            if (histGrp.getChangeHistoryItemCount()>0){
                for (int i= histGrp.getChangeHistoryItemCount()-1; i>=0; i--)
                {
                    AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
                    addDebug("checkAssortModule: AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
                        " isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
                    if (chi.getFlagCode().equals(STATUS_FINAL)){
                        lastfinaldate = chi.getChangeDate();
                        break;
                    }
                }
            }
            if (lastfinaldate !=null){ // get value of SAPASSORTMODULE at this DTS
                String curSAPASSORTMODULE = PokUtils.getAttributeValue(rootEntity, sapAttr,", ", "", false).trim();
                String curSAPASSORTMODULEPRIOR = PokUtils.getAttributeValue(rootEntity, sapAttrPrior,", ", "", false).trim();
                // get entityitem for this time
                Profile profile = m_elist.getProfile().getNewInstance(m_db);
                profile.setValOnEffOn(lastfinaldate, lastfinaldate);
                EntityGroup eg = new EntityGroup(null,m_db, profile, rootEntity.getEntityType(), "Edit", false);
                EntityItem oldItem = new EntityItem(eg, profile, m_db, rootEntity.getEntityType(), rootEntity.getEntityID());
                String prevSAPASSORTMODULE = PokUtils.getAttributeValue(oldItem, sapAttr,", ", "", false).trim();
                addDebug("checkAssortModule: "+rootEntity.getKey()+" lastfinaldate "+lastfinaldate+
                    " "+sapAttr+" curr:"+curSAPASSORTMODULE+" prev:"+prevSAPASSORTMODULE+
                    " "+sapAttrPrior+" curr: "+curSAPASSORTMODULEPRIOR);

                if (!curSAPASSORTMODULEPRIOR.equals(prevSAPASSORTMODULE)){
                    setTextValue(m_elist.getProfile(),sapAttrPrior,prevSAPASSORTMODULE, rootEntity);
                }else{
                    addDebug("checkAssortModule: "+sapAttrPrior+" current:"+curSAPASSORTMODULEPRIOR+
                        " already matches "+sapAttr+" previous:"+prevSAPASSORTMODULE);
                }
            }else{
                addDebug("checkAssortModule: must be first time "+rootEntity.getKey()+" status went final");
            }
        }
        else {
            addDebug("checkAssortModule: Could not get AttributeHistory for "+rootEntity.getKey()+
                " STATUS, it was null");
        }
    }

    /**********************************************************************************
    * All countries for the item at the end of the linkType must be a subset of the
    * root entity countries or root entity must be a subset of the linked item
    *
	* AllValuesOf(ANNAVAILA:AVAIL-d. COUNTRYLIST) IN (ANNOUNCEMENT.COUNTRYLIST)
	* ErrorMessage LD(AVAIL) NDN(AVAIL)  'has a Country LD(COUNTRYLIST) that is not in the' LD(ANNOUNCEMENT) LD(COUNTRYLIST).
	*
    */
    protected void checkCountry(String linkType,String direction,boolean chkItemIsSubsetRoot) throws java.sql.SQLException, MiddlewareException
    {
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

    	ArrayList rootCtry = new ArrayList();
		//get the list of countries from the announcement
		EANFlagAttribute rootCtrylist = (EANFlagAttribute)rootEntity.getAttribute("COUNTRYLIST");
		if (rootCtrylist != null) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) rootCtrylist.get();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					rootCtry.add(mfArray[i].getFlagCode());
				}
			}
		}

	    addDebug("checkCountry "+rootEntity.getKey()+" countries "+rootCtry);

		//Look at each AVAIL (or LSEO) thru the linkItem
		EntityGroup linkGrp = m_elist.getEntityGroup(linkType);
		for (int i = 0; i < linkGrp.getEntityItemCount(); i++) {
			EntityItem linkitem = linkGrp.getEntityItem(i);
			if (direction.equals("D")){
				for (int d=0; d<linkitem.getDownLinkCount(); d++){
					EntityItem depitem = (EntityItem)linkitem.getDownLink(d);
					checkCountry(rootEntity, depitem, rootCtry,chkItemIsSubsetRoot);
				} //end for each dependent item
			}else{  // must go up the link
				for (int d=0; d<linkitem.getUpLinkCount(); d++){
					EntityItem depitem = (EntityItem)linkitem.getUpLink(d);
					checkCountry(rootEntity, depitem, rootCtry,chkItemIsSubsetRoot);
				} //end for each dependent item
			}
		}// end each link item

		rootCtry.clear();
	}

	private void checkCountry(EntityItem rootEntity, EntityItem depitem,ArrayList rootCtry,boolean chkItemIsSubsetRoot)
	throws java.sql.SQLException, MiddlewareException
	{
		Object[] args = new String[6];
		EntityGroup depGrp = depitem.getEntityGroup();
		ArrayList depCtry = new ArrayList();

		EANFlagAttribute ctrylist = (EANFlagAttribute)depitem.getAttribute("COUNTRYLIST");
		if (ctrylist != null) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
			for (int im = 0; im < mfArray.length; im++) {
				// get selection
				if (mfArray[im].isSelected()) {
					depCtry.add(mfArray[im].getFlagCode());
				}
			} //end for
		} //end of null chk

		addDebug("checkCountry "+depitem.getKey()+" chkItemIsSubsetRoot: "+chkItemIsSubsetRoot+" countries "+depCtry);

		//if ann does not have all avails countries, it is an error
		boolean ok=true;
		if (chkItemIsSubsetRoot){
			// other item countries must be a subset of root countries
			ok = rootCtry.containsAll(depCtry);
		}else{
			// root countries must be a subset of other item countries
			ok = depCtry.containsAll(rootCtry);
		}
		if (!ok) {
//LSEOBUNDLE root
// AllValuesOf(LSEOBUNDLE.COUNTRYLIST) IN (LSEOBUNDLELSEO-d: LSEO.COUNTRYLIST)
// ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEO) NDN(LSEO) LD(COUNTRYLIST).
// AllValuesOf(LSEOBUNDLEAVAIL-d:AVAIL. COUNTRYLIST) IN (LSEOBUNDLE.COUNTRYLIST)
// ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEOBUNDLE) NDN(LSEOBUNDLE) LD(COUNTRYLIST).
//LSEO root
// AllValuesOf(LSEOAVAIL-d:AVAIL. COUNTRYLIST) IN (LSEO.COUNTRYLIST)
// ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEO) LD(COUNTRYLIST).
//AVAIL root
// AllValuesOf(AVAIL.COUNTRYLIST) IN (LSEOAVAIL-u: LSEO.COUNTRYLIST)
// ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEO) NDN(LSEO) LD(COUNTRYLIST).
// AllValuesOf(AVAIL.COUNTRYLIST) IN (LSEOBUNDLEAVAIL-u: LSEOBUNDLE.COUNTRYLIST)
// ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEOBUNDLE) NDN(LSEOBUNDLE) LD(COUNTRYLIST).
//ANNOUNCEMENT root
// 5. AllValuesOf(ANNAVAILA:AVAIL-d. COUNTRYLIST) IN (ANNOUNCEMENT.COUNTRYLIST)
// ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(COUNTRYLIST).

			//COUNTRY_MISMATCH_ERR = {0} {1} {2} has a Country that is not in the {3} {4} {5}
			if (chkItemIsSubsetRoot){ // item must be subset of root
				args[0] = depGrp.getLongDescription();
				args[1] = getNavigationName(depitem);
				args[2] = PokUtils.getAttributeDescription(depGrp, "COUNTRYLIST", "COUNTRYLIST");
				args[3] = rootEntity.getEntityGroup().getLongDescription();
				args[4] = getNavigationName(rootEntity);
				args[5] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
			}else{ // root must be subset of item
				args[0] = "";//info is repeated rootEntity.getEntityGroup().getLongDescription();
				args[1] = "";//getNavigationName(rootEntity);
				args[2] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
				args[3] = depGrp.getLongDescription();
				args[4] = getNavigationName(depitem);
				args[5] = PokUtils.getAttributeDescription(depGrp, "COUNTRYLIST", "COUNTRYLIST");
			}
			addError("COUNTRY_MISMATCH_ERR",args);
		}

		depCtry.clear();
	}

	/**********************************
	* If the attribute STATUS is 'Final' and the attribute SAPL is any one of the 'Current Value'
	* in the table below, then SAPLABRSTATUS is queued
	* QueueSAPL
	*
	* IF SAPL = 20 (Ready) or 30 (Send) or 40 (Sent) or 80 (Update Sent) THEN
	* Set SAPLABRSTATUS = 0020 (Queued)
	* ELSE
	* END IF
	*/
	protected void queueSapl()
	{
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		// if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = m_elist.getParentEntityGroup().getMetaAttribute("SAPL");
        if (metaAttr==null) {
			addDebug("queueSapl Status=Final but SAPL was not in meta, nothing to do");
		}else{
			// check value of SAPL attribute
			String curVal = getAttributeFlagEnabledValue(rootEntity, "SAPL");
			if (curVal==null){
				curVal = "10"; // default to 'Not Ready'
			}

			// if current val is not in table then don't process
			String setValue = (String)SAPL_TRANS_TBL.get(curVal);
			if (setValue==null){
				Object[] args = new String[1];
				addDebug("queueSapl Status=Final but SAPL current value is not in list ["+curVal+"]");
				//SAPL_WRONG_VALUE = Status was &quot;Final&quot; but SAPL was &quot;{0}&quot; so SAPLABRSTATUS was not queued.
        		MessageFormat msgf = new MessageFormat(dqBundle.getString("SAPL_WRONG_VALUE"));
				args[0] = PokUtils.getAttributeValue(rootEntity, "SAPL",", ", "", false);
				addOutput(msgf.format(args));
			}else{
         		setFlagValue(m_elist.getProfile(),"SAPLABRSTATUS", SAPLABR_QUEUED);
			}
		}
	}

    /****************************************
	*I.	PropagateOStoWWSEO
	*
	*Syntax:  PropagateOStoWWSEOs
	*
	*For Hardware MODELs, propagate OS to all children WWSEOs.
	*
	*IF ValueOf(MODELWWSEO:MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODELWWSEO:MODEL.COFGRP) = 150 (Base)
	*THEN
	*If MODEL.OS changed since the last time this ABR was queued, then
	*MODELWWSEO:WWSEO.OS = MODEL.OS
	*END IF
	*END IF
	*
    */
    protected void propagateOStoWWSEO(EntityItem mdlItem, EntityGroup wwseoGrp) throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		String osAttr = "OS";

		if (wwseoGrp ==null){
			throw new MiddlewareException("WWSEO is missing from extract for "+m_abri.getVEName());
		}

		if (mdlItem ==null){ // could only happen if checks werent done
			addDebug("propagateOStoWWSEO: MODEL was null!!");
			return;
		}

		String modelCOFCAT = getAttributeFlagEnabledValue(mdlItem, "COFCAT");
		String modelCOFGRP = getAttributeFlagEnabledValue(mdlItem, "COFGRP");
		addDebug("propagateOStoWWSEO: "+mdlItem.getKey()+" COFGRP: "+modelCOFGRP+" wwseocnt: "+wwseoGrp.getEntityItemCount());
		if (wwseoGrp.getEntityItemCount()==0){
			// no WWSEO, nothing to do
			return;
		}

        EANMetaAttribute metaAttr = mdlItem.getEntityGroup().getMetaAttribute(osAttr);
        if (metaAttr==null) {
           addDebug("propagateOStoWWSEO ERROR:Attribute "+osAttr+" NOT found in MODEL META data.");
           return;
        }

		//IF ValueOf(MODELWWSEO:MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODELWWSEO:MODEL.COFGRP) = 150 (Base)
		if(HARDWARE.equals(modelCOFCAT) && BASE.equals(modelCOFGRP)) {
			//get attribute history
			EANAttribute attStatus = mdlItem.getAttribute("MODELABRSTATUS");
			if (attStatus != null)
			{
				String lastqueueddate=null;
				boolean breakNow = false;
				AttributeChangeHistoryGroup histGrp = m_db.getAttributeChangeHistoryGroup(m_elist.getProfile(), attStatus);
				addDebug("propagateOStoWWSEO: ChangeHistoryGroup for "+mdlItem.getKey()+" Attribute: MODELABRSTATUS");
				if (histGrp.getChangeHistoryItemCount()>0){
					for (int i= histGrp.getChangeHistoryItemCount()-1; i>=0; i--)
					{
						AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
						addDebug("propagateOStoWWSEO: AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
							" isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
						if (chi.getFlagCode().equals(ABR_QUEUED)){
							if (lastqueueddate != null){
								// want the queue time before this one
								breakNow = true;
							}
							lastqueueddate = chi.getChangeDate();
							if (breakNow){
								break;
							}
						}
					}
				}

				if (lastqueueddate !=null){ // get value of OS at this DTS
					String curMdlOs = getAttributeFlagEnabledValue(mdlItem, osAttr);
					if (curMdlOs==null){
						curMdlOs = "";
					}

					// get entityitem for this time
					Profile profile = m_elist.getProfile().getNewInstance(m_db);
					profile.setValOnEffOn(lastqueueddate, lastqueueddate);
					EntityGroup eg = new EntityGroup(null,m_db, profile, mdlItem.getEntityType(), "Edit", false);
					EntityItem oldItem = new EntityItem(eg, profile, m_db, mdlItem.getEntityType(), mdlItem.getEntityID());
					String prevMdlOs = getAttributeFlagEnabledValue(oldItem , osAttr);
					if (prevMdlOs==null){
						prevMdlOs = "";
					}

					addDebug("propagateOStoWWSEO: "+mdlItem.getKey()+" lastqueueddate "+lastqueueddate+
						" "+osAttr+" curr: "+curMdlOs+" prev:"+prevMdlOs);

					if (!curMdlOs.equals(prevMdlOs)){
						// look at each WWSEO
						for (int i=0; i<wwseoGrp.getEntityItemCount(); i++){
							EntityItem wwseoItem = wwseoGrp.getEntityItem(i);
							addDebug("propagateOStoWWSEO: updating "+wwseoItem.getKey()+" SEOID "+
								PokUtils.getAttributeValue(wwseoItem, "SEOID",", ", "", false));
							setFlagValue(m_elist.getProfile(),osAttr, curMdlOs,wwseoItem);
						}
					}else{
						addDebug("propagateOStoWWSEO: no changes in "+mdlItem.getKey()+" os since last queued");
					}
				}else{
					addDebug("propagateOStoWWSEO: Could not get queued DTS for "+mdlItem.getKey());
				}

			}
			else {
				addDebug("propagateOStoWWSEO: Could not get AttributeHistory for "+mdlItem.getKey()+
					" MODELABRSTATUS, it was null");
			}
		}
	}

	/****************************************
	* Check for withdrawn date on the avail
	*6.	AllValuesOf(LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL:AVAIL.EFFECTIVEDATE > NOW()
	*	WHERE AVAIL.AVAILTYPE = 149 (Last Order).
	*ErrorMessage 'references a withdrawn' LD(PRODSTRUCT) NDN(PRODSTRUCT)
				checkWDAvails("LSEOPRODSTRUCT","PRODSTRUCT","OOFAVAIL",strNow);
	*
	*7.	AllValuesOf(LSEOSWPRODSTRUCT-d: SWPRODSTRUCT: SWPRODSTRUCTAVAIL:AVAIL.EFFECTIVEDATE > NOW()
	*	WHERE AVAIL.AVAILTYPE = 149 (Last Order).
	*ErrorMessage 'references a withdrawn' LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT)
	*/
    protected void checkWDAvails(EntityItem rootEntity, String linktype,String etype, String rtype, String strNow)
	throws java.sql.SQLException, MiddlewareException
	{
		Object[] args = new String[2];

		Vector psVct = PokUtils.getAllLinkedEntities(rootEntity, linktype, etype);
		addDebug("checkWDAvails entered go thru: "+linktype+" to "+etype+" then thru "+rtype+" found: "+psVct.size()+" "+etype);
		for (int e=0; e<psVct.size(); e++){ // look at each entity
			EntityItem theItem = (EntityItem)psVct.elementAt(e);
			addDebug("checkWDAvails checking entity: "+theItem.getKey()+" for "+rtype);
			for (int i=0; i<theItem.getDownLinkCount(); i++){ // look at relators
				EntityItem link = (EntityItem)theItem.getDownLink(i);
				if (link.getEntityType().equals(rtype)){ // right relator
					// get AVAILS
					for (int ai=0; ai<link.getDownLinkCount(); ai++){ // look at avails
						EntityItem avail = (EntityItem)link.getDownLink(ai);
						addDebug("checkWDAvails checking  "+avail.getKey()+" for lastorder");
						if(PokUtils.isSelected(avail, "AVAILTYPE", LASTORDERAVAIL)){
							String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
							addDebug("checkWDAvails lastorder "+avail.getKey()+" EFFECTIVEDATE: "+effDate);
							if (effDate.length()>0 && effDate.compareTo(strNow)<=0){
								//WITHDRAWN_ERR = references a withdrawn {0} {1}
								args[0] = theItem.getEntityGroup().getLongDescription();
								args[1] = getNavigationName(theItem);
								addError("WITHDRAWN_ERR",args);
							}
						}
					} // end avails
				}
			}
		}
		psVct.clear();
	}

	/********************************
	* this looks at dates on last order avails.  they must be <= to withdrawn date on feature, swfeature
	* this also looks to see if the planned avail countries are a subset of the last order avail countries
	*
	* IF NotNull(SWFEATURE.WITHDRAWDATEEFF_T ) THEN
	* a.CompareAll(SWPRODSTUCT: SWPRODSTRUCTAVAIL-d: AVAIL.EFFECTIVEDATE) <= SWFEATURE. WITHDRAWDATEEFF_T WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
	* ErrorMessage LD(WITHDRAWDATEEFF_T) 'is earlier than' LD(AVAIL) NDN(AVAIL)
	* b.AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST) WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN (SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST) WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
	* ErrorMessage LD(WITHDRAWDATEEFF_T) 'exists and is available in a Country that does not have a Last Order' LD(AVAIL).
	*
	* IF NotNull(FEATURE.WITHDRAWDATEEFF_T ) THEN
	* a.CompareAll(PRODSTRUCT: OOFAVAIL-d: AVAIL.EFFECTIVEDATE) <= FEATURE. WITHDRAWDATEEFF_T WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
	* ErrorMessage LD(WITHDRAWDATEEFF_T) 'is earlier than' LD(AVAIL) NDN(AVAIL)
	* b.AllValuesOf(PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST) WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN (PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST) WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
	* ErrorMessage LD(WITHDRAWDATEEFF_T) 'exists and is available in a Country that does not have a Last Order' LD(AVAIL).
	*
	*/
	protected void checkLastOrderAvailCountry(EntityItem rootEntity, String wdAttrCode) throws java.sql.SQLException, MiddlewareException
	{
		String wdDate = PokUtils.getAttributeValue(rootEntity, wdAttrCode,", ", null, false);
		addDebug("checkLastOrderAvailCountry "+rootEntity.getKey()+" "+wdAttrCode+": "+wdDate);
		if (wdDate!=null){
			Object args[] = new Object[4];
			// get all 'Last order' AVAILS
			EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
			if (availGrp ==null){
				throw new MiddlewareException("AVAIL is missing from extract for "+m_abri.getVEName());
			}

			ArrayList lastOrderAvailCtry = new ArrayList();
			ArrayList plannedAvailCtry = new ArrayList();

			for (int ai=0; ai<availGrp.getEntityItemCount(); ai++){ // look at avails
				EntityItem avail = availGrp.getEntityItem(ai);
				addDebug("checkLastOrderAvailCountry checking avail: "+avail.getKey());
				if(PokUtils.isSelected(avail, "AVAILTYPE", LASTORDERAVAIL)){ // last order
					String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
					addDebug("checkLastOrderAvailCountry lastorder "+avail.getKey()+" EFFECTIVEDATE: "+effDate);
					//	a.	CompareAll(PRODSTRUCT: OOFAVAIL-d: AVAIL) <= PRODSTRUCT.WITHDRAWDATE WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
					//	a.  CompareAll(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.EFFECTIVEDATE) <= SWFEATURE. WITHDRAWDATEEFF_T WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
					if (effDate.length()>0 && effDate.compareTo(wdDate)>0){
						// 	ErrorMessage LD(WITHDRAWDATEEFF_T) 'is earlier than' LD(AVAIL) NDN(AVAIL)
						//EARLY_DATE_ERR = {0} is earlier than the {1} {2} {3}
						args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), wdAttrCode, wdAttrCode);
						args[1] = availGrp.getLongDescription();
						args[2] = getNavigationName(avail);
						args[3] = "";
						addError("EARLY_DATE_ERR",args);
					}
					EANFlagAttribute ctrylist = (EANFlagAttribute)avail.getAttribute("COUNTRYLIST");
					if (ctrylist != null) {
						// Get the selected Flag codes.
						MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
						for (int im = 0; im < mfArray.length; im++) {
							// get selection
							if (mfArray[im].isSelected() && !lastOrderAvailCtry.contains(mfArray[im].getFlagCode())) {
								lastOrderAvailCtry.add(mfArray[im].getFlagCode());
							}
						} //end for
					} //end of null chk

				}else if(PokUtils.isSelected(avail, "AVAILTYPE", PLANNEDAVAIL)){
					addDebug("checkLastOrderAvailCountry plannedavail "+avail.getKey());
					EANFlagAttribute ctrylist = (EANFlagAttribute)avail.getAttribute("COUNTRYLIST");
					if (ctrylist != null) {
						// Get the selected Flag codes.
						MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
						for (int im = 0; im < mfArray.length; im++) {
							// get selection
							if (mfArray[im].isSelected()&& !plannedAvailCtry.contains(mfArray[im].getFlagCode())) {
								plannedAvailCtry.add(mfArray[im].getFlagCode());
							}
						} //end for
					} //end of null chk
				}
			} // end avails
			//b.  AllValuesOf(PRODSTRUCT: OOFAVAIL-d: AVAIL WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
			//			IN (PRODSTRUCT: OOFAVAIL-d: AVAIL WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
			//b.  AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST) WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
			//			IN (SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST) WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
			addDebug("checkLastOrderAvailCountry all plannedavail countries "+plannedAvailCtry);
			addDebug("checkLastOrderAvailCountry all lastorderavail countries "+lastOrderAvailCtry);
			// each lastorder avail country must have a plannedavail too
			if (!lastOrderAvailCtry.containsAll(plannedAvailCtry)) {
				args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), wdAttrCode, wdAttrCode);
				args[1] = availGrp.getLongDescription();
				//ErrorMessage LD(WITHDRAWDATEEFF_T) 'exists and is available in a Country that does not have a Last Order' LD(AVAIL).
				//NO_LASTORDER_ERR2= {0} exists and is available in a Country that does not have a Last Order {1}.
				addError("NO_LASTORDER_ERR2",args);
			}
			lastOrderAvailCtry.clear();
			plannedAvailCtry.clear();
		}
	}

	// used to support getting navigation display name when other entities are needed
	private static class NDN {
		private String etype, tag;
		private NDN next;
		private Vector attrVct = new Vector();
		NDN(String t,String s){
			etype = t;
			tag = s;
		}
		String getTag() { return tag;}
		String getEntityType() { return etype;}
		Vector getAttr(){ return attrVct;}
		void addAttr(String a){
			attrVct.addElement(a);
		}
		void setNext(NDN n) { next = n;}
		NDN getNext() { return next;}
	}
}
