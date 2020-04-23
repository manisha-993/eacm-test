// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
FCABRSTATUS_class=COM.ibm.eannounce.abr.sg.FCABRSTATUS
FCABRSTATUS_enabled=true
FCABRSTATUS_idler_class=A
FCABRSTATUS_keepfile=true
FCABRSTATUS_read_only=true
FCABRSTATUS_report_type=DGTYPE01
FCABRSTATUS_vename=EXRPT3FEATURE1
FCABRSTATUS_CAT1=RPTCLASS.FCABRSTATUS
FCABRSTATUS_CAT2=
FCABRSTATUS_CAT3=RPTSTATUS
FCABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390

 *
 * FCABRSTATUS.java,v
 * Revision 1.21  2011/03/23 11:17:58  wendy
 * Add CATDATA and chg date attr for check 102
 *
 * Revision 1.20  2011/03/05 00:59:58  wendy
 * MNIN454169 - BH FS ABR Data Quality 20110303.doc
 *
 * Revision 1.15  2010/08/25 19:37:10  wendy
 * check for null from search
 *
 * Revision 1.11  2010/01/21 14:27:40  wendy
 * update cmts
 *
 * Revision 1.10  2010/01/20 15:23:41  wendy
 * updates for BH FS ABR Data Quality 20100118.doc
 *
 * Revision 1.8  2009/12/08 12:13:50  wendy
 * cvs failure - restore version and logging
 *
 * Revision 1.6  2009/12/02 18:56:31  wendy
 * Updated for spec chg BH FS ABR Data Qualtity 20091120.xls
 *
 * Revision 1.5  2009/11/04 15:08:07  wendy
 * BH Configurable Services - spec chgs
 *
 * Revision 1.4  2009/09/09 16:45:08  wendy
 * Check avails by (xx)prodstruct instead of as a group
 *
 * Revision 1.3  2009/08/17 15:30:10  wendy
 * Added headings
 *
 * Revision 1.2  2009/08/15 01:41:50  wendy
 * SR10, 11, 12, 15, 17 BH updates phase 4
 *
 * Revision 1.1  2009/07/30 18:54:36  wendy
 * Moved to new pkg for BH SR10, 11, 12, 15, 17

 *
 * </pre>
 */
package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.Attribute;

import com.ibm.transform.oim.eacm.util.*;

import java.sql.SQLException;
import java.util.*;


/**********************************************************************************
* FCABRSTATUS class
* 
* BH FS ABR Data Quality 20120131.doc - delete 115-120, add OSN filtering to AVAIL checks
* 
* BH FS ABR Data Quality 20110517.doc
* support already final or rfr - gen catdata and queue ads abr
* needs new VE EXRPT3FEATURE2
*   
* BH FS ABR Data Qualtity Checks 20110318.xls date attr chg
* 
* catdata updates, 
* updated VE EXRPT3FEATURE1
* 
* MNIN454169 - BH FS ABR Data Quality 20110303.doc
* 
* BH FS ABR Data Qualtity Sets 20100629.xls
* need LIFECYCLE meta and workflow actions
* 
* From "SG FS ABR Data Quality 20100521.doc"
* 
need meta
  - BHINVNAME added
  - SRDFEATURE9 created

*
XII.	FEATURE

A.	Checking

A Feature does not have to be in a product (i.e. be related to a MODEL via PRODSTRUCT) at the time it goes Final. 
Therefore, a FEATURE may not have Availability (AVAIL); however, if it does, then the dates will be checked.

B.	Derivation
The following is performed in addition to the Checking.

	Perform SetBHInvnameHW
C.	Status changed to Ready for Review

See the embedded SS.
D.	Status changed to Final

See the embedded SS.


*/
public class FCABRSTATUS extends DQABRSTATUS
{
	private static final String FC_SRCHACTION_NAME = "SRDFEATURE9"; // srch not restricted by PDHDOMAIN
	private static final char[] FC_CODE_ILLEGAL = {'O','I'};
	
	/**********************************
	* must be xseries or convergedproduct and ready4review
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;// always need CATDATA domainInList();
	}
	/**
	 * allow derived classes a way to override the VE used when status and dq are the same and checks
	 * are not needed, but some structure may be needed
	 * 
	 * @param statusFlag
	 * @param dataQualityFlag
	 * @return
	 */
	protected String getVEName(String statusFlag, String dataQualityFlag){
		if (statusFlag.equals(STATUS_FINAL)){
			addDebug("Status already final, use diff ve");
			return "EXRPT3FEATURE2";
		}else if (statusFlag.equals(STATUS_R4REVIEW) && dataQualityFlag.equals(DQ_R4REVIEW)){
			addDebug("Status already rfr, use diff ve");
			return "EXRPT3FEATURE2";	
		}
		return 	m_abri.getVEName();
	}
	
	/**********************************
	 * complete abr processing when status is already final; (dq was final too)
	 * 
	 * When this ABR is invoked and STATUS = DATAQUALITY = �Final�, then checking is not required. 
	 * CATDATA derivation is required. If the generation fails, the DQ ABR will fail. The DQ ABR will 
	 * process data for the selected offering and only utilizes CATADATA rules that have a Life Cycle of Production. 
	 * If the generation of CATDATA is successful, then only the setting of ADSABRSTATUS is processed. 
	 * This includes the necessary conditions and only column N (Final) applies.
	 */
    protected void doAlreadyFinalProcessing(EntityItem rootEntity) throws Exception {
    	// update darules and if there are changes queue ADSABRSTATUS
    	if(doDARULEs()){
    		boolean chgsMade = updateDerivedData(rootEntity);
    		addDebug("doAlreadyFinalProcessing: "+rootEntity.getKey()+" chgsMade "+chgsMade);
    		if(chgsMade){
    			 setSinceFirstFinal(rootEntity, "ADSABRSTATUS");
    		     queueProdStruct(true);
    		}else{
				//NO_CHGSFOUND = No {0} changes found.
				args[0] = m_elist.getEntityGroup("CATDATA").getLongDescription();
				addResourceMsg("NO_CHGSFOUND",args);
			}
    	}
	}
	
	/**********************************
	 * complete abr processing when status is already rfr; (dq was rfr too)
	 * 
	 * When this ABR is invoked and STATUS = DATAQUALITY = �Ready for Review�, then checking is not required. 
	 * CATDATA derivation is required. If the generation fails, the DQ ABR will fail. The DQ ABR will process 
	 * data for the selected offering and only utilizes CATADATA rules that have a Life Cycle of Production. 
	 * If the generation of CATDATA is successful, then only the setting of ADSABRSTATUS is processed. This 
	 * includes the necessary conditions and only column M (Ready for Review) applies.
	 */
    protected void doAlreadyRFRProcessing(EntityItem rootEntity) throws Exception {
    	// update darules and if there are changes queue ADSABRSTATUS
    	if(doDARULEs()){
//    		String lifecycle = PokUtils.getAttributeFlagValue(rootEntity, "LIFECYCLE");
//    		addDebug("doAlreadyRFRProcessing: "+rootEntity.getKey()+" lifecycle "+lifecycle);
//    		if (lifecycle==null || lifecycle.length()==0){ 
//    			lifecycle = LIFECYCLE_Plan;
//    		}
    		//59.02	R1.0	IF			FEATURE	STATUS	=	"Ready for Review" (0040)			
    		//59.04	R1.0	AND			FEATURE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
//    		if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//    				LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
    			boolean chgsMade = updateDerivedData(rootEntity);
    			addDebug("doAlreadyRFRProcessing: "+rootEntity.getKey()+" chgsMade "+chgsMade);
    			if(chgsMade){
    				setRFRSinceFirstRFR(rootEntity);
    		    	queueProdStruct(false);
    			}else{
    				//NO_CHGSFOUND = No {0} changes found.
    				args[0] = m_elist.getEntityGroup("CATDATA").getLongDescription();
    				addResourceMsg("NO_CHGSFOUND",args);
    			}
//    		}	
	   	}
	}
	/*
from sets ss:
	58.00		FEATURE		Root Entity							
Delete	59.00	Defer	SET			FEATURE				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
Add	59.02	R1.0	IF			FEATURE	STATUS	=	"Ready for Review" (0040)			
Add	59.04	R1.0	AND			FEATURE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
Add	59.06	R1.0	SET			FEATURE				ADSABRSTATUS	&ADSFEEDRFR	
Add	59.08	R1.0	END	59.02								
Add	59.10	R1.0	IF			FEATURE	STATUS	=	"Final" (0020)			
Add	59.12	R1.0	SET			FEATURE				ADSABRSTATUS		&ADSFEED
Add	59.14	R1.0	END	59.10								
	60.00		END	58.00	FEATURE							
						

	 */
    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
	* C.	Status changed to Ready for Review
20131105 Add		58.200	IF			FEATURE	STATUS	=	"Ready for Review" (0040)				FEATURE is RFR		
20131105 Add		58.220	SET			FEATURE	ADSABRSTATUS	=	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS	&ADSFEEDRFR		XML was sent		
20131105 Add		58.240	SET			FEATURE	ADSABRSTATUS	<>	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS	&ADSFEEDRFRFIRST		XML was never sent		

    */
    protected void completeNowR4RProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		if(doR10processing()){
	    	EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);	
	    	setRFRSinceFirstRFR(rootItem);
	    	queueProdStruct(false);
//	    	String lifecycle = PokUtils.getAttributeFlagValue(rootItem, "LIFECYCLE");
//	    	addDebug("completeNowR4RProcessing: "+rootItem.getKey()+" lifecycle "+lifecycle);
//	  		if (lifecycle==null || lifecycle.length()==0){ 
//				lifecycle = LIFECYCLE_Plan;
//			}
//	    	if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//	    			LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
//	    		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
//	    	}
		}
    }
    
    /**********************************
    * complete abr processing after status moved to final; (status was r4r)
    *D. STATUS changed to Final
    *
20131105 Add		58.260	ELSE	58.200									FEATURE is Final		
20131105 Add		58.280	SetSinceFinal			FEATURE	ADSABRSTATUS	=	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEED	XML sent Final		
20131105 Add		58.300	SetSinceFinal			FEATURE	ADSABRSTATUS	<>	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEEDFINALFIRST	XML not sent Final		
20131105 Add		58.320	END	58.200											

    */
    protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
//        addDebug(rootEntity.getKey()+" status now final");
		if(doR10processing()){
//			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
//		}        
	        setSinceFirstFinal(rootEntity, "ADSABRSTATUS");
	        //59.100	IF			FEATURE	STATUS	=	"Final" (0020)
	        //59.120	SET			FEATURE				RFCABRSTATUS		&OIMSFEED	
	        //59.140	END	59.100
	        //58.200	IF			FEATURE	STATUS	=	"Ready for Review" (0040)				FEATURE is RFR
	        //58.220	SET			FEATURE	RFCABRSTATUS	=	"Passed" (0030)				XML was sent
	        //58.240	SET			FEATURE	RFCABRSTATUS	<>	"Passed" (0030)				XML was never sent
	        //58.260	ELSE	58.200									FEATURE is Final
	        //58.280	SetSinceFinal			FEATURE	RFCABRSTATUS	=	"Passed" (0030)	RFCABRSTATUS		&OIMSFEED	XML sent Final
	        //58.300	SetSinceFinal			FEATURE	RFCABRSTATUS	<>	"Passed" (0030)	RFCABRSTATUS		&OIMSFEED	XML not sent Final
//	        setRFCSinceFirstFinal(rootEntity, "RFCABRSTATUS");
	        queueProdStruct(true);
		}
		// TODO
		/*
		 * Trigger the following attributes when FEATURE , PRODSTRUCTs or a MODEL goes to Final.
		 * FEATURE.QSMRPQCREFABRSTATUS
		 * FEATURE.QSMRPQFULLABRSTATUS
		 * So the Attriubtes are on FEATURE and everytime MODEL, FEATURE and/or PRODSTRUCTs goes to final u triger these two ABRs on a feature entity.
		 * This only applies when FEATURE.FCTYPE='RPQILISTED','RPQPLISTED','RPQRLISTED'
		 */
		boolean isRPQ = isQSMRPQ(rootEntity);
		addDebug("completeNowFinalProcessing - isRPQ " + isRPQ);
		if (isRPQ) {			
			setFlagValue(m_elist.getProfile(), "QSMRPQCREFABRSTATUS", getQueuedValueForItem(rootEntity, "QSMRPQCREFABRSTATUS"), rootEntity);
			setFlagValue(m_elist.getProfile(), "QSMRPQFULLABRSTATUS", getQueuedValueForItem(rootEntity, "QSMRPQFULLABRSTATUS"), rootEntity);
		}
    }
    
//    20131105 Add		58.340	IF			FEATURE	FCTYPE	<>	Primary FC (100) | "Secondary FC" (110)				does PRODSTRUCT xml need to be sent		
//    20131105 Add		58.360	OR		PRODSTRUCT-d: OOFAVAIL-d	AVAIL	CountOf	=	0				If GA & no Planned AVAILs		
//    20131105 Add		58.380				WHERE	AVAILTYPE	= 	"Planned Availability" (146)				Only count Planned Availability AVAILs		
//    20131105 Add		58.400	IF			PRODSTRUCT	STATUS	=	"Final" (0020)				Yes - need to send PRODSTRUCT XML		
//    20131105 Add		58.420	SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED	Queue PRODSTRUCT		
//    20131105 Add		58.440	END	58.400											
//    20131105 Add		58.460	IF			PRODSTRUCT	STATUS	=	"Ready for Review" (0040)						
//    20131105 Add		58.480	SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR		Queue PRODSTRUCT		
//    20131105 Add		58.500	END	58.460			
    private void queueProdStruct(boolean status){
    	EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
    	boolean isRPQ = isRPQ(rootEntity);
		// get all AVAILS from PRODSTRUCT-u:OOFAVAIL-d
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp,
				"AVAILTYPE", PLANNEDAVAIL);

		addDebug("queueProdStruct - isRPQ "+isRPQ);
		addDebug("queueProdStruct - ALL plannedAvailVct.size "+plannedAvailVct.size());
		if(isRPQ || (!isRPQ && plannedAvailVct.size() == 0)){
			EntityGroup psGrp = m_elist.getEntityGroup("PRODSTRUCT");
			for(int p=0; p<psGrp.getEntityItemCount(); p++){
    			EntityItem psitem = psGrp.getEntityItem(p);
    			if(statusIsRFR(psitem) && !status){
    				addDebug("queueProdStruct - RFR PS "+psitem.getKey());
    				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(psitem,"ADSABRSTATUS"), psitem);
    			}else if(statusIsFinal(psitem) && status){    	
    				addDebug("queueProdStruct - FINAL PS "+psitem.getKey());
    				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(psitem, "ADSABRSTATUS"), psitem);
    				//58.400	IF			PRODSTRUCT	STATUS	=	"Final" (0020)				Yes - need to send PRODSTRUCT XML
    				//58.420	SET			PRODSTRUCT				RFCABRSTATUS		&OIMSFEED	Queue PRODSTRUCT
    				setFlagValue(m_elist.getProfile(),"RFCABRSTATUS", getQueuedValueForItem(psitem, "RFCABRSTATUS"), psitem);
    			}
    		}
		}
    }
     
	/** 
	 * from BH FS ABR Catalog Attr Derivation 20110221.doc
	 * C.	Data Quality
	 * As part of the normal process for offering information, a user first creates data in �Draft�. The user 
	 * then asserts the Data Quality (DATAQUALITY) as being �Ready for Review� which queues the Data Quality ABR. 
	 * The DQ ABR checks to ensure that the data is �Ready for Review� and then advances Status (STATUS) to 
	 * �Ready for Review�.
	 *  
	 * The Data Quality ABR will be enhanced such that if the checks pass, then the DQ ABR will process the 
	 * corresponding DARULE. If DARULE is processed successfully, then the DQ ABR will set 
	 * STATUS = �Ready for Review�. If DARULE is not processed successfully, then the DQ ABR will �Fail� 
	 * and return Data Quality to the prior state (Draft or Change Request).
	 */
	protected boolean updateDerivedData(EntityItem rootEntity) throws Exception {
		boolean chgsMade = false;
		//	NOW() <= Global Withdrawal Date Effective (WITHDRAWDATEEFF_T)
		String wdDate = PokUtils.getAttributeValue(rootEntity, "WITHDRAWDATEEFF_T", "", FOREVER_DATE, false);
		addDebug("updateDerivedData wdDate: "+wdDate+" now: "+getCurrentDate());
		if(getCurrentDate().compareTo(wdDate)<=0){
			chgsMade = execDerivedData(rootEntity);
		}
		return chgsMade;
	}
	
    /* (non-Javadoc)
     * update LIFECYCLE value when STATUS is updated
     * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
     */
    protected String getLCRFRWFName(){ return "WFLCFEATURERFR";} 
    protected String getLCFinalWFName(){ return "WFLCFEATUREFINAL";} 

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
checking from ss:
1.00	FEATURE		Root									
2.00			FIRSTANNDATE									
3.00			GENAVAILDATE								Not used for GA products	
4.00			WITHDRAWANNDATE_T	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T} must not be earlier than the {LD: FIRSTANNDATE} {FIRSTANNDATE}
5.00			WITHDRAWDATEEFF_T	=>	FEATURE	GENAVAILDATE		W	W	E		{LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} must not be earlier than the {LD: GENAVAILDATE} {GENAVAILDATE}
6.00			COUNTRYLIST									
6.20				CheckMAX	FEATURE	BHINVNAME		RE	RE	RE		LD(FEATURE) NDN(FEATURE) LD(BHINVNAME) has a derived value longer than (value of MAX) characters
7.00	WHEN		FCTYPE	<>	"Primary FC (100) |""Secondary FC"" (110)"						RPQ logic	
8.00	PRODSTRUCT		ANNDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: ANNDATE} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
9.00			GENAVAILDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: GENAVAILDATE} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
10.00			WITHDRAWDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: WITHDRAWDATE} must not be later than {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
11.00			WTHDRWEFFCTVDATE	<=	FEATURE	WITHDRAWDATEEFF_T		W	W	E		{LD: WTHDRWEFFCTVDATE} must not be later than {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
12.00	END	7.00										
13.00	FEATURE		Root									
14.00	WHEN		FCTYPE	=	"Primary FC (100) | ""Secondary FC"" (110)"						GA logic
15.00	AVAIL	A	PRODSTRUCT-u:OOFAVAIL-d								AVAIL PA	
16.00	WHEN		AVAILTYPE	=	"Planned Availability"							
17.00			COUNTRYLIST	in	FEATURE	COUNTRYLIST		W	W	E		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
18.00			EFFECTIVEDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {ANNDATE}
19.00	ANNOUNCEMENT	B	A: + AVAILANNA-d 								ANNOUNCEMENT PA	
20.00	WHEN		ANNTYPE	=	New (19)							
21.00			ANNDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE} 
22.00	END	20.00										
23.00	END	16.00										
24.00	AVAIL	H	PRODSTRUCT-u:OOFAVAIL-d								AVAIL PA	
25.00	WHEN		AVAILTYPE	=	"First Order"							
xx26.00			COUNTRYLIST	in	FEATURE	COUNTRYLIST		W	W	E		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
"Change 20111213
Change 20111216"	26.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
27.00			EFFECTIVEDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {ANNDATE}
27.20	ANNOUNCEMENT		H: + AVAILANNA-d 								ANNOUNCEMENT PA	
27.40	WHEN		ANNTYPE	=	New (19)							
27.60			ANNDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE} 
	END	27.40										
28.00	END	25.00										
29.00	AVAIL	C	PRODSTRUCT-u:OOFAVAIL-d								AVAIL LO	
30.00	WHEN		AVAILTYPE	=	"Last Order"							
xx31.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST		W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	31.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
32.00			EFFECTIVEDATE	<=	FEATURE	WITHDRAWDATEEFF_T		W	W	E		{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} 
33.00	ANNOUNCEMENT	D	C: + AVAILANNA-d 								ANNOUNCEMENT LO	
34.00	WHEN		ANNTYPE	=	End Of Life - Withdrawal from mktg (14)							
35.00			ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}  
36.00	END	34.00										
37.00	END	30.00										
100.00	AVAIL	J	PRODSTRUCT-u: OOFAVAIL-d								PRODSTRUCT AVAIL	
101.00	WHEN		AVAILTYPE	=	"End of Marketing" (200)							
102.00			EFFECTIVEDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T}{WITHDRAWANNDATE_T}
xx103.00			COUNTRYLIST	IN	A:AVAIL	COUNTRYLIST		W	W	E*1		 {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	103.00			COUNTRYLIST	IN	A:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
104.00	ANNOUNCEMENT		J: + AVAILANNA-d 								PRODSTRUCT ANNOUNCEMENT	
105.00	IF		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)							
106.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T}  {WITHDRAWANNDATE_T}
107.00	ELSE		ANNTYPE	<>	"End Of Life - Withdrawal from mktg" (14)							
108.00			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
109.00	END	105.00										
110.00	END	101.00										
111.00	AVAIL	K	PRODSTRUCT-u: OOFAVAIL-d								PRODSTRUCT AVAIL	
112.00	WHEN		AVAILTYPE	=	"End of Service" (151)							
xx113.00			EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	Yes	W	W	E*1		{LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: J:AVAIL)
xx114.00			COUNTRYLIST	IN	C:AVAIL	COUNTRYLIST		W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
Change 20111216	113.00			EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: J:AVAIL)
Change 20111216	114.00			COUNTRYLIST	IN	C:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
Delete 20111212 115.00	ANNOUNCEMENT		K: + AVAILANNA-d 								PRODSTRUCT ANNOUNCEMENT	
116.00	IF		ANNTYPE	=	"End Of Life - Discontinuance of service" (13)							
117.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWDATEEFF_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
118.00	ELSE		ANNTYPE	<>	"End Of Life - Discontinuance of service" (13)							
119.00			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: K:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
120.00	END	116.00	end Delete 20111212 									
121.00	END	112.00										
38.00	END	14.00										
39.00	FEATURE		Root									
40.00	MONITOR	E	FEATUREMON-d								MONITOR	
41.00		F	CountOf		E: FEATUREMON							
42.00		G			FEATUREMONITOR	QTY						
43.00			F + G	<=	1			W	W	E		must not have more than one {LD: MONITOR}

	*/
	protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
	{
		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");

		//6.20				CheckMAX	FEATURE	BHINVNAME		RE	RE	RE		LD(FEATURE) NDN(FEATURE) LD(BHINVNAME) has a derived value longer than (value of MAX) characters
		// this is done for all FCTYPE
		setBHInvnameHW(rootEntity);
		
		if(getReturnCode()!=PASS){ // BHINVNAME failure
			//If the BHINVNAME is successfully saved, then proceed with the CHECKS
			return;
		}
		
		int checklvl = getCheck_W_W_E(statusFlag);
		//40.00	MONITOR	E	FEATUREMON-d								MONITOR
		//41.00		F	CountOf		E: FEATUREMON
		//42.00		G			FEATUREMONITOR	QTY
		//43.00			F + G	<=	1			W	W	E
		//must not have more than one {LD: MONITOR}
		int cnt = getCount("FEATUREMONITOR");
		if(cnt > 1)	{
			EntityGroup monGrp = m_elist.getEntityGroup("MONITOR");
			//ErrorMessage  has more than one LD(MONITOR)
			//MORE_THAN_ONE_ERR = Has more than one {0}
			args[0] = monGrp.getLongDescription();
			createMessage(checklvl,"MORE_THAN_ONE_ERR",args);
		}
		
		//		2013-05-13 Add	45.00	FEATURE		Root									
		//		2013-05-13 Add	46.00	IF		PDHDOMAIN	IN	ABR_PROPERTITIES	PWRFC_LIST 					Limit to Power Series	
		//		2013-05-13 Add	47.00			CheckChars	<>	IO						Col G is ALPHA UPPER	{LD:FEATURECODE}{FEATRECODE} has an invalid character of I or O.
		//		2013-05-13 Add	48.00	END	46.00										
		checkFeatureCode(rootEntity,CHECKLEVEL_E);// will change the check level when document is ready.

		//4.00			WITHDRAWANNDATE_T	=>	FEATURE	FIRSTANNDATE		W	W	E
		//{LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T} can not be earlier than the {LD: FIRSTANNDATE} {FIRSTANNDATE}
		checkCanNotBeEarlier(rootEntity, "WITHDRAWANNDATE_T", "FIRSTANNDATE", checklvl);

		//5.00			WITHDRAWDATEEFF_T	=>	FEATURE	GENAVAILDATE		W	W	E
		//{LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} can not be earlier than the {LD: GENAVAILDATE} {GENAVAILDATE}
		checkCanNotBeEarlier(rootEntity, "WITHDRAWDATEEFF_T", "GENAVAILDATE", checklvl);

		//14.00	WHEN		FCTYPE	=	"Primary FC (100) | ""Secondary FC"" (110)"						GA logic
		if (!isRPQ(rootEntity)){
			addDebug(rootEntity.getKey()+" was NOT an RPQ FCTYPE: "+getAttributeFlagEnabledValue(rootEntity, "FCTYPE"));
			checkAvails(rootEntity, statusFlag);
		}else{
			//7.00	WHEN		FCTYPE	<>	"Primary FC (100) |Secondary FC (110)"						RPQ logic
			addDebug(rootEntity.getKey()+" was an RPQ FCTYPE: "+getAttributeFlagEnabledValue(rootEntity, "FCTYPE"));

	    	//get all PRODSTRUCT
			EntityGroup psGrp = m_elist.getEntityGroup("PRODSTRUCT");
			addHeading(3,psGrp.getLongDescription()+" RPQ Checks:");
			for (int p=0; p<psGrp.getEntityItemCount(); p++){
				EntityItem psitem = psGrp.getEntityItem(p);
				//8.00	PRODSTRUCT		ANNDATE	=>	FEATURE	FIRSTANNDATE		W	W	E
				//{LD: ANNDATE} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
				checkCanNotBeEarlier(psitem, "ANNDATE", rootEntity,"FIRSTANNDATE", checklvl);

				//9.00			GENAVAILDATE	=>	FEATURE	FIRSTANNDATE		W	W	E
				//{LD: GENAVAILDATE} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
		    	checkCanNotBeEarlier(psitem, "GENAVAILDATE", rootEntity,"FIRSTANNDATE", checklvl);
		    	//10.00		WITHDRAWDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E
				//{LD: WITHDRAWDATE} must not be later than {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
		    	checkCanNotBeLater(psitem, "WITHDRAWDATE", rootEntity,"WITHDRAWANNDATE_T", checklvl);

				//11.00		WTHDRWEFFCTVDATE	<=	FEATURE	WITHDRAWDATEEFF_T		W	W	E
				//{LD: WTHDRWEFFCTVDATE} must not be later than {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
				checkCanNotBeLater(psitem, "WTHDRWEFFCTVDATE", rootEntity,"WITHDRAWDATEEFF_T", checklvl);
			}
		}
	}

	private void checkFeatureCode(EntityItem featItem, int checklvl) {
		// add PWRFC_LIST checking - power series
		if(domainInRuleList(featItem, "PWRFC_LIST")){
			String fcode = PokUtils.getAttributeValue(featItem, "FEATURECODE",", ", "", false);
			if(checkChars(FC_CODE_ILLEGAL, fcode)){
				args[0] = this.getLD_Value(featItem,"FEATURECODE");
				createMessage(checklvl,"INVAILD_CHAR_IO_ERROR",args);
			}
		}
		
	}
	/*
	 *

15.00	AVAIL	A	PRODSTRUCT-u:OOFAVAIL-d								AVAIL PA
16.00	WHEN		AVAILTYPE	=	"Planned Availability"
17.00			COUNTRYLIST	in	FEATURE	COUNTRYLIST		W	W	E		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
18.00			EFFECTIVEDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {ANNDATE}
19.00	ANNOUNCEMENT	B	A: + AVAILANNA-d 								ANNOUNCEMENT PA
20.00	WHEN		ANNTYPE	=	New (19)
21.00			ANNDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
22.00	END	20.00
23.00	END	16.00
24.00	AVAIL	H	PRODSTRUCT-u:OOFAVAIL-d								AVAIL PA
25.00	WHEN		AVAILTYPE	=	"First Order"
xx26.00			COUNTRYLIST	in	FEATURE	COUNTRYLIST		W	W	E		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
"Change 20111213
Change 20111216"	26.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
27.00			EFFECTIVEDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {ANNDATE}
27.20	ANNOUNCEMENT		H: + AVAILANNA-d 								ANNOUNCEMENT PA
27.40	WHEN		ANNTYPE	=	New (19)
27.60			ANNDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
	END	27.40
28.00	END	25.00
29.00	AVAIL	C	PRODSTRUCT-u:OOFAVAIL-d								AVAIL LO
30.00	WHEN		AVAILTYPE	=	"Last Order"
xx31.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST		W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	31.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
32.00			EFFECTIVEDATE	<=	FEATURE	WITHDRAWDATEEFF_T		W	W	E		{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
33.00	ANNOUNCEMENT	D	C: + AVAILANNA-d 								ANNOUNCEMENT LO
34.00	WHEN		ANNTYPE	=	End Of Life - Withdrawal from mktg (14)
35.00			ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T} 
36.00	END	34.00
37.00	END	30.00
100.00	AVAIL	J	PRODSTRUCT-u: OOFAVAIL-d								PRODSTRUCT AVAIL
101.00	WHEN		AVAILTYPE	=	"End of Marketing" (200)
102.00			EFFECTIVEDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T}{WITHDRAWANNDATE_T}
xx103.00		COUNTRYLIST	IN	A:AVAIL	COUNTRYLIST		W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	103.00			COUNTRYLIST	IN	A:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
104.00	ANNOUNCEMENT		J: + AVAILANNA-d 								PRODSTRUCT ANNOUNCEMENT
105.00	IF		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)
106.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T}  {WITHDRAWANNDATE_T}
107.00	ELSE		ANNTYPE	<>	"End Of Life - Withdrawal from mktg" (14)
108.00			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
109.00	END	105.00
110.00	END	101.00
111.00	AVAIL	K	PRODSTRUCT-u: OOFAVAIL-d								PRODSTRUCT AVAIL
112.00	WHEN		AVAILTYPE	=	"End of Service" (151)
xx113.00			EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	Yes	W	W	E*1		{LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: J:AVAIL)
xx114.00			COUNTRYLIST	IN	C:AVAIL	COUNTRYLIST		W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
Change 20111216	113.00			EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: J:AVAIL)
Change 20111216	114.00			COUNTRYLIST	IN	C:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
Delete 20111212 115.00	ANNOUNCEMENT		K: + AVAILANNA-d 								PRODSTRUCT ANNOUNCEMENT
116.00	IF		ANNTYPE	=	"End Of Life - Discontinuance of service" (13)
117.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWDATEEFF_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
118.00	ELSE		ANNTYPE	<>	"End Of Life - Discontinuance of service" (13)
119.00			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: K:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
120.00	END	116.00 Delete 20111212 
121.00	END	112.00
38.00	END	14.00

	 */
	private void checkAvails(EntityItem featItem, String statusFlag)
		throws java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		int checklvl = getCheck_W_W_E(statusFlag);

    	ArrayList featCtrylist = new ArrayList();
		EntityGroup psGrp = m_elist.getEntityGroup("PRODSTRUCT");
		
    	// fill in list with the feature's countries
    	getCountriesAsList(featItem, featCtrylist,checklvl);
       	String firstAnnDate = getAttrValueAndCheckLvl(featItem, "FIRSTANNDATE", checklvl);
    	String wdAnnDate = getAttrValueAndCheckLvl(featItem, "WITHDRAWANNDATE_T", checklvl);
    	addDebug("checkAvails "+featItem.getKey()+" FIRSTANNDATE: "+firstAnnDate+" WITHDRAWANNDATE_T: "+wdAnnDate+
    			" featCtrylist "+featCtrylist);

    	//get all AVAILS from PRODSTRUCT-u:OOFAVAIL-d
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");

		//15.00	AVAIL	A	PRODSTRUCT-u:OOFAVAIL-d								AVAIL PA
    	Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", PLANNEDAVAIL);
    	// remove any that are not AVAILANNTYPE=RFA so they arent in the other checks
    	//removeNonRFAAVAIL(plannedAvailVct); not in spec
		
    	addDebug("checkAvails ALL plannedAvailVct "+plannedAvailVct.size());
		Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", LASTORDERAVAIL);
	   	addDebug("checkAvails ALL loAvailVct "+lastOrderAvailVct.size());

	   	Vector mesLastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", MESLASTORDERAVAIL);
	   	addDebug("checkAvails ALL mesloAvailVct "+mesLastOrderAvailVct.size());
	   	
    	addHeading(3,availGrp.getLongDescription()+" Planned Avail Checks:");
    	checkPsPlanOrMesPlanAvail(plannedAvailVct, featItem, featCtrylist, firstAnnDate, checklvl);
    	
    	//15.00	AVAIL	A	PRODSTRUCT-u:OOFAVAIL-d								AVAIL PA
    	Vector mesPlannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", MESPLANNEDAVAIL);
    	addDebug("checkAvails ALL mesPlannedAvailVct "+mesPlannedAvailVct.size());
    	addHeading(3,availGrp.getLongDescription()+" MES Planned Avail Checks:");
    	checkPsPlanOrMesPlanAvail(mesPlannedAvailVct, featItem, featCtrylist, firstAnnDate, checklvl);

    	addHeading(3,availGrp.getLongDescription()+" First Order Avail Checks:");
    	//24.00	AVAIL	H	PRODSTRUCT-u:OOFAVAIL-d								AVAIL PA
		Vector firstOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", FIRSTORDERAVAIL);
    	addDebug("checkAvails ALL firstOrderAvailVct "+firstOrderAvailVct.size());

    	if(firstOrderAvailVct.size()>0){
    		//	25.00	WHEN		AVAILTYPE	=	"First Order"
    		for (int i=0; i<firstOrderAvailVct.size(); i++){
    			EntityItem avail = (EntityItem)firstOrderAvailVct.elementAt(i);
    			EntityItem psitem = getAvailPS(avail, "OOFAVAIL");
    			//27.00			EFFECTIVEDATE	=>	FEATURE	FIRSTANNDATE		W	W	E
    			//{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    			checkCanNotBeEarlier(psitem,avail, "EFFECTIVEDATE", featItem, "FIRSTANNDATE", checklvl);

    			//old 26.00 COUNTRYLIST	in	FEATURE	COUNTRYLIST			
    			//old checkAvailCtryInEntity(psitem, avail, featItem,featCtrylist,checklvl);
    		}
    		addDebug("\ncheckAvails checking firstorder ps avails ");
    		// get all PRODSTRUCT - must compare each PS-PLA against its own PS-FO
    		for(int p=0; p<psGrp.getEntityItemCount(); p++){
    			EntityItem psitem = psGrp.getEntityItem(p);
    			Vector availVct = PokUtils.getAllLinkedEntities(psitem, "OOFAVAIL", "AVAIL");
    			Vector psfoAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", FIRSTORDERAVAIL);
    			Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
    			// MES
    			Vector psMesPlannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESPLANNEDAVAIL);
				addDebug("checkAvails for "+psitem.getKey()+" psplannedAvailVct "+psplannedAvailVct.size()+" psmesplannedAvailVct "+psMesPlannedAvailVct.size()+
						" psfoAvailVct "+psfoAvailVct.size());
    			if(psfoAvailVct.size()>0){
    				Hashtable plaAvailOSNTbl = new Hashtable();
    				boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl,psplannedAvailVct,true,CHECKLEVEL_RE);
    				// MES
    				Hashtable mesplaAvailOSNTbl = new Hashtable();
    				boolean mesplaOsnErrors = getAvailByOSN(mesplaAvailOSNTbl,psMesPlannedAvailVct,true,CHECKLEVEL_RE);
    				
    				Hashtable foAvailOSNTbl = new Hashtable();
    				boolean foOsnErrors = getAvailByOSN(foAvailOSNTbl,psfoAvailVct,true,CHECKLEVEL_RE);
    				addDebug("checkAvails "+psitem.getKey()+" foOsnErrors "+
    						foOsnErrors+" foAvailOSNTbl.keys "+foAvailOSNTbl.keySet()+" plaOsnErrors "+
    						plaOsnErrors+" plaAvailOSNTbl.keys "+plaAvailOSNTbl.keySet()+" mesplaOsnErrors "+
    						mesplaOsnErrors+" mesplaAvailOSNTbl.keys "+mesplaAvailOSNTbl.keySet());
    				//Change 20111213
    				//Change 20111216	26.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E	
    				//	{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
    				if(!plaOsnErrors && !foOsnErrors){
    					// only do this check if no errors were found building the OSN buckets
    					checkAvailCtryByOSN(foAvailOSNTbl,plaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", psitem, true, checklvl);
    				}
    				if(psMesPlannedAvailVct != null && psMesPlannedAvailVct.size() > 0 && !mesplaOsnErrors && !foOsnErrors){
    					// only do this check if no errors were found building the OSN buckets
    					checkAvailCtryByOSN(foAvailOSNTbl,mesplaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", psitem, true, checklvl);
    				}
    				plaAvailOSNTbl.clear();
    				mesplaAvailOSNTbl.clear();
    				foAvailOSNTbl.clear();
    			}
    			availVct.clear();
    			psfoAvailVct.clear();
    			psplannedAvailVct.clear();
    			psMesPlannedAvailVct.clear();
    		}
    	}
		
		//27.20	ANNOUNCEMENT		H: + AVAILANNA-d 								ANNOUNCEMENT PA
		//27.40	WHEN		ANNTYPE	=	New (19)
		//27.60			ANNDATE	=>	FEATURE	FIRSTANNDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
		if (firstAnnDate.length()>0){
			//filter out non RFA avails
    		Vector tmpFOVct = new Vector(firstOrderAvailVct);
    		// remove any that are not AVAILANNTYPE=RFA
        	removeNonRFAAVAIL(tmpFOVct);

			Vector annVct = PokUtils.getAllLinkedEntities(tmpFOVct, "AVAILANNA", "ANNOUNCEMENT");

			annVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNTYPE", ANNTYPE_NEW);
			addDebug("checkAvails any foavail NEW annVct "+annVct.size());
			for (int ia=0; ia<annVct.size(); ia++){
				EntityItem annItem = (EntityItem)annVct.elementAt(ia);
				checkCanNotBeEarlier(annItem, "ANNDATE", featItem, "FIRSTANNDATE", checklvl);
			}
			annVct.clear();
			tmpFOVct.clear();
		}
		//END	27.40
		//28.00	END	25.00

		//29.00	AVAIL	C	PRODSTRUCT-u:OOFAVAIL-d								AVAIL LO
		addHeading(3,availGrp.getLongDescription()+" Last Order Avail Checks:");
		checkPsLastOrderOrMesLastOrderAvail(LASTORDERAVAIL, lastOrderAvailVct, psGrp, featItem, wdAnnDate, checklvl);
		
	   	addHeading(3,availGrp.getLongDescription()+" MES Last Order Avail Checks:");
	   	checkPsLastOrderOrMesLastOrderAvail(MESLASTORDERAVAIL, mesLastOrderAvailVct, psGrp, featItem, wdAnnDate, checklvl);

    	//100.00	AVAIL	J	PRODSTRUCT-u: OOFAVAIL-d								PRODSTRUCT AVAIL
    	//101.00	WHEN		AVAILTYPE	=	"End of Marketing" (200)
		addHeading(3,availGrp.getLongDescription()+" End of Marketing Avail Checks:");
		
		addDebug("\ncheckAvails checking eom ps avails");
		// get all PRODSTRUCT - must compare each PS-PLA against its own PS-EOM
		for(int p=0; p<psGrp.getEntityItemCount(); p++){
			EntityItem psitem = psGrp.getEntityItem(p);
			EntityItem mdlItem = getDownLinkEntityItem(psitem, "MODEL");
			int oldDatachklvl = getCheckLevel(checklvl,mdlItem,"ANNDATE");

			Vector availVct = PokUtils.getAllLinkedEntities(psitem, "OOFAVAIL", "AVAIL");
			Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
			Vector psmesplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESPLANNEDAVAIL);
			//101.00	WHEN		AVAILTYPE	=	"End of Marketing" (200)
			Vector psEOMAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", EOMAVAIL);
			//ArrayList plaAvlCtry = getCountriesAsList(psplannedAvailVct, checklvl);

			addDebug("checkAvails "+psitem.getKey()+" all avail: "+availVct.size()+
					" plaAvail: "+psplannedAvailVct.size()+" mesplaAvail: "+psmesplannedAvailVct.size()+" eomAvail: "+psEOMAvailVct.size());
				//	" plaAvlCtry:"+plaAvlCtry);
			if(psEOMAvailVct.size()>0){
		
				for (int i=0; i<psEOMAvailVct.size(); i++){
					EntityItem avail = (EntityItem)psEOMAvailVct.elementAt(i);
					//102.00			EFFECTIVEDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E	{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T}{WITHDRAWANNDATE_T}
					checkCanNotBeLater(psitem,avail, "EFFECTIVEDATE", featItem, "WITHDRAWANNDATE_T", checklvl);

					//old103.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST		W	W	E*1 - PER PRODSTRUCT
					//oldcheckPlannedAvailForCtryExists(psitem,avail, plaAvlCtry, oldDatachklvl);

					String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
					addDebug("checkAvails "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
					if (availAnntypeFlag==null){
						availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
					}
					if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){ // error was already logged
						//104.00	ANNOUNCEMENT		J: + AVAILANNA-d 								PRODSTRUCT ANNOUNCEMENT
						Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
						addDebug("checkAvails EOM "+avail.getKey()+" annVct "+annVct.size());
						for (int ie=0; ie<annVct.size(); ie++){
							EntityItem annItem = (EntityItem)annVct.elementAt(ie);

							String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
							addDebug("checkAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
							//107.00	ELSE		ANNTYPE	<>	"End Of Life - Withdrawal from mktg" (14)
							//108.00			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
							if(!ANNTYPE_EOL.equals(anntypeFlag)){
								//MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
								args[0] = getLD_NDN(avail);
								args[1] = getLD_NDN(annItem);
								createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
								continue;
							}
							//105.00	IF		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)
							//106.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T}  {WITHDRAWANNDATE_T}
							checkCanNotBeLater(annItem, "ANNDATE", featItem, "WITHDRAWANNDATE_T", checklvl);
						}
						annVct.clear();
					}
					//109.00	END	105.00
					//110.00	END	101.00
				}
				Hashtable plaAvailOSNTbl = new Hashtable();
				boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl,psplannedAvailVct,true,CHECKLEVEL_RE);
				// MES
				Hashtable mesplaAvailOSNTbl = new Hashtable();
				boolean mesplaOsnErrors = getAvailByOSN(mesplaAvailOSNTbl,psmesplannedAvailVct,true,CHECKLEVEL_RE);
				Hashtable eomAvailOSNTbl = new Hashtable();
				boolean eomOsnErrors = getAvailByOSN(eomAvailOSNTbl,psEOMAvailVct,true,CHECKLEVEL_RE);
				addDebug("checkAvails "+psitem.getKey()+" plaOsnErrors "+
						plaOsnErrors+" plaAvailOSNTbl.keys "+plaAvailOSNTbl.keySet()+" mesplaOsnErrors "+
								plaOsnErrors+" mesplaAvailOSNTbl.keys "+plaAvailOSNTbl.keySet()+" eomOsnErrors "+
						eomOsnErrors+" eomAvailOSNTbl.keys "+eomAvailOSNTbl.keySet());
				//Change 20111216	103.00			COUNTRYLIST	IN	A:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1
				//{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
				if(!plaOsnErrors && !eomOsnErrors){
					// only do this check if no errors were found building the OSN buckets
					checkAvailCtryByOSN(eomAvailOSNTbl,plaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", psitem, true,oldDatachklvl);
				}
				if(psmesplannedAvailVct != null && psmesplannedAvailVct.size() > 0 && !mesplaOsnErrors && !eomOsnErrors){
					// only do this check if no errors were found building the OSN buckets
					checkAvailCtryByOSN(eomAvailOSNTbl,mesplaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", psitem, true,oldDatachklvl);
				}
				plaAvailOSNTbl.clear();
				mesplaAvailOSNTbl.clear();
				eomAvailOSNTbl.clear();	
			}
			availVct.clear();
			psplannedAvailVct.clear();
			psmesplannedAvailVct.clear();
			psEOMAvailVct.clear();
			//plaAvlCtry.clear();
		}

    	//111.00	AVAIL	K	PRODSTRUCT-u: OOFAVAIL-d								PRODSTRUCT AVAIL
    	//112.00	WHEN		AVAILTYPE	=	"End of Service" (151)
		addHeading(3,availGrp.getLongDescription()+" End of Service Avail Checks:");
		addDebug("\ncheckAvails checking eos ps avails ");
		// get all PRODSTRUCT - must compare each PS-LO against its own PS-EOS
		for(int p=0; p<psGrp.getEntityItemCount(); p++){
			EntityItem psitem = psGrp.getEntityItem(p);
			EntityItem mdlItem = getDownLinkEntityItem(psitem, "MODEL");
			int oldDatachklvl = getCheckLevel(checklvl,mdlItem,"ANNDATE");

			Vector availVct = PokUtils.getAllLinkedEntities(psitem, "OOFAVAIL", "AVAIL");
			Vector psLoAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", LASTORDERAVAIL);
			Vector psmesLoAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESLASTORDERAVAIL);
			//112.00	WHEN		AVAILTYPE	=	"End of Service" (151)
			Vector psEOSAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", EOSAVAIL);
			//Hashtable loAvailCtryTbl = getAvailByCountry(psLoAvailVct, checklvl);

			addDebug("checkAvails "+psitem.getKey()+" all avail: "+availVct.size()+
					" loAvail: "+psLoAvailVct.size()+" mesloAvail: "+psmesLoAvailVct.size()+" eosAvail: "+psEOSAvailVct.size());
					//" loAvailCtryTbl:"+loAvailCtryTbl.keySet());

			if(psEOSAvailVct.size()>0){
				/*for (int i=0; i<psEOSAvailVct.size(); i++){
					EntityItem eosavail = (EntityItem)psEOSAvailVct.elementAt(i);

					Vector loVct = new Vector(); // hold onto loavail for date checks incase same avail for mult ctrys
					//old114 String missingCtry = checkCtryMismatch(eosavail, loAvailCtryTbl, loVct, checklvl);
					// do the date checks now
					for (int m=0; m<loVct.size(); m++){
						EntityItem loAvail = (EntityItem)loVct.elementAt(m);
						//xx113.00			EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	Yes	W	W	E*1		{LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: J:AVAIL)
//			Change 20111216	113.00			EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: J:AVAIL)
						checkCanNotBeEarlier(psitem, eosavail, "EFFECTIVEDATE",	loAvail, "EFFECTIVEDATE",oldDatachklvl);
					} 
					loVct.clear();*/
					/*old114.00			COUNTRYLIST	IN	C:AVAIL	COUNTRYLIST		W	W	E*1	{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
					if (missingCtry.length()>0){
						addDebug("checkAvails eosavail:"+eosavail.getKey()+
								" COUNTRYLIST had ctry ["+missingCtry+"] that were not in any loavail");
						//MISSING_LO_CTRY_ERR = {0} {1} includes a Country that does not have a &quot;Last Order Availability&quot; Extra countries are: {2}
						args[0]=getLD_NDN(psitem)+" "+getLD_NDN(eosavail);
						args[1] = PokUtils.getAttributeDescription(eosavail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
						args[2] = missingCtry;
						createMessage(oldDatachklvl,"MISSING_LO_CTRY_ERR",args);
					}*/

					/*
				Delete 20111212 115.00-120.00 
				String availAnntypeFlag = PokUtils.getAttributeFlagValue(eosavail, "AVAILANNTYPE");
				addDebug("checkAvails "+eosavail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
				if (availAnntypeFlag==null){
					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
				}

				if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){ // error was already logged
					//115.00	ANNOUNCEMENT		K: + AVAILANNA-d 								PRODSTRUCT ANNOUNCEMENT
					Vector annVct = PokUtils.getAllLinkedEntities(eosavail, "AVAILANNA", "ANNOUNCEMENT");
					addDebug("checkAvails EOS "+eosavail.getKey()+" annVct "+annVct.size());
					for (int ie=0; ie<annVct.size(); ie++){
						EntityItem annItem = (EntityItem)annVct.elementAt(ie);

						String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
						addDebug("checkAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
						//118.00	ELSE		ANNTYPE	<>	"End Of Life - Discontinuance of service" (13)
						//119.00			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: K:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
						if(!ANNTYPE_EOLDS.equals(anntypeFlag)){
							//MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
							args[0] = getLD_NDN(eosavail);
							args[1] = getLD_NDN(annItem);
							createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
							continue;
						}
						//116.00	IF		ANNTYPE	=	"End Of Life - Discontinuance of service" (13)
						//117.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWDATEEFF_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
						checkCanNotBeLater(annItem, "ANNDATE", featItem, "WITHDRAWDATEEFF_T", checklvl);
					}
					annVct.clear();
				}
		    	//120.00	END	116.00
					 */
					//121.00	END	112.00
		//		}// end eos avails loop for one prodstruct

				Hashtable loAvailOSNTbl = new Hashtable();
				boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl,psLoAvailVct,true,CHECKLEVEL_RE);
				// MES
				Hashtable mesloAvailOSNTbl = new Hashtable();
				boolean mesloOsnErrors = getAvailByOSN(mesloAvailOSNTbl,psmesLoAvailVct,true,CHECKLEVEL_RE);

				Hashtable eosAvailOSNTbl = new Hashtable();
				boolean eosOsnErrors = getAvailByOSN(eosAvailOSNTbl,psEOSAvailVct,true,CHECKLEVEL_RE);
				addDebug("checkAvails "+psitem.getKey()+" loOsnErrors "+
						loOsnErrors+" loAvailOSNTbl.keys "+loAvailOSNTbl.keySet()+" mesloOsnErrors "+
								mesloOsnErrors+" mesloAvailOSNTbl.keys "+mesloAvailOSNTbl.keySet()+" eosOsnErrors "+
						eosOsnErrors+" eosAvailOSNTbl.keys "+eosAvailOSNTbl.keySet());
				// only do this check if no errors were found building the OSN buckets
				if(!loOsnErrors && !eosOsnErrors){
					//Change 20111216	113.00			EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: J:AVAIL)
					checkAvailDatesByCtryByOSN(eosAvailOSNTbl,loAvailOSNTbl, psitem, DATE_GR_EQ,oldDatachklvl,"",false);
					//Change 20111216	114.00			COUNTRYLIST	IN	C:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
					//{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
					checkAvailCtryByOSN(eosAvailOSNTbl,loAvailOSNTbl, "MISSING_LO_OSNCTRY_ERR", psitem,true, oldDatachklvl);
				}
				if(psmesLoAvailVct != null && psmesLoAvailVct.size() > 0 && !mesloOsnErrors && !eosOsnErrors){
					//Change 20111216	113.00			EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: J:AVAIL)
					checkAvailDatesByCtryByOSN(eosAvailOSNTbl,mesloAvailOSNTbl, psitem, DATE_GR_EQ,oldDatachklvl,"",false);
					//Change 20111216	114.00			COUNTRYLIST	IN	C:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
					//{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
					checkAvailCtryByOSN(eosAvailOSNTbl,mesloAvailOSNTbl, "MISSING_LO_OSNCTRY_ERR", psitem,true, oldDatachklvl);
				}
				loAvailOSNTbl.clear();
				mesloAvailOSNTbl.clear();
				eosAvailOSNTbl.clear();			
			}// end eos avail exist

			availVct.clear();
			psLoAvailVct.clear();
			psmesLoAvailVct.clear();
			psEOSAvailVct.clear();
			//loAvailCtryTbl.clear();
		}// end psgrp loop
    	//38.00	END	14.00

	   	firstOrderAvailVct.clear();
	   	featCtrylist.clear();
		lastOrderAvailVct.clear();
	}
	
	/**
	 * Check PRODSTRUCT Last Order or MES Last Order AVAIL
	 * @param lastOrderAvailVct
	 * @param psGrp
	 * @param featItem
	 * @param wdAnnDate
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkPsLastOrderOrMesLastOrderAvail(String lastOrderOrMesLastOrderAvailType, Vector lastOrderAvailVct, EntityGroup psGrp, EntityItem featItem, String wdAnnDate, int checklvl) throws SQLException, MiddlewareException { 
		addDebug("\ncheckAvails checking lastorder ps avails");
		// get all PRODSTRUCT - must compare each PS-PLA against its own PS-LO
		for(int p=0; p<psGrp.getEntityItemCount(); p++){
			EntityItem psitem = psGrp.getEntityItem(p);

			EntityItem mdlItem = getDownLinkEntityItem(psitem, "MODEL");
			int oldDatachklvl = getCheckLevel(checklvl,mdlItem,"ANNDATE");

			Vector availVct = PokUtils.getAllLinkedEntities(psitem, "OOFAVAIL", "AVAIL");
			// remove any that are not AVAILANNTYPE=RFA so they arent in the other checks
	    	//removeNonRFAAVAIL(availVct);

			Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
			Vector psmesplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESPLANNEDAVAIL);
			//29.00	AVAIL	C	PRODSTRUCT-u:OOFAVAIL-d								AVAIL LO
			Vector pslastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", lastOrderOrMesLastOrderAvailType);
			ArrayList plaAvlCtry = getCountriesAsList(psplannedAvailVct, checklvl);

			addDebug("checkAvails "+psitem.getKey()+" "+mdlItem.getKey()+" all avail: "+availVct.size()+" avail type: "+lastOrderOrMesLastOrderAvailType+
					" plaAvail: "+psplannedAvailVct.size()+" loAvail: "+pslastOrderAvailVct.size()+" mesplaAvail: "+psmesplannedAvailVct.size()+
					" plaAvlCtry:"+plaAvlCtry);

			checkPsAvailCWithAvailA(pslastOrderAvailVct, psplannedAvailVct, psitem, featItem, checklvl, oldDatachklvl);
			if(MESLASTORDERAVAIL.equals(lastOrderOrMesLastOrderAvailType))
				checkPsAvailCWithAvailA(pslastOrderAvailVct, psmesplannedAvailVct, psitem, featItem, checklvl, oldDatachklvl);
			checkPsAvailCWithFeatureW(pslastOrderAvailVct, psitem, featItem, checklvl);
			
			availVct.clear();
			psplannedAvailVct.clear();
			plaAvlCtry.clear();
		}

		//33.00	ANNOUNCEMENT	D	C: + AVAILANNA-d 								ANNOUNCEMENT LO
		//34.00	WHEN		ANNTYPE	=	End Of Life - Withdrawal from mktg (14)
		//35.00			ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T} 
    	if (wdAnnDate.length()>0){
    		//filter out non RFA avails
    		Vector tmpLOVct = new Vector(lastOrderAvailVct);
    		// remove any that are not AVAILANNTYPE=RFA
        	removeNonRFAAVAIL(tmpLOVct);

    		Vector annVct = PokUtils.getAllLinkedEntities(tmpLOVct, "AVAILANNA", "ANNOUNCEMENT");
    		addDebug("checkAvails annVct "+annVct.size());
    		annVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNTYPE", ANNTYPE_EOL);
    		addDebug("checkAvails EOL annVct "+annVct.size());
    		for (int i=0; i<annVct.size(); i++){
    			EntityItem annItem = (EntityItem)annVct.elementAt(i);
    			checkCanNotBeLater(annItem, "ANNDATE", featItem, "WITHDRAWANNDATE_T", checklvl);
    		}
    		annVct.clear();
    		//lastOrderAvailVct.clear();
    		tmpLOVct.clear();
    	}
	}
	
	private void checkPsAvailCWithAvailA(Vector pslastOrderAvailVct, Vector psplannedAvailVct, EntityItem psitem, EntityItem featItem, int checklvl, int oldDatachklvl) throws SQLException, MiddlewareException {
		if(pslastOrderAvailVct.size()>0){
			Hashtable plaAvailOSNTbl = new Hashtable();
			boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl,psplannedAvailVct,true,CHECKLEVEL_RE);
			addDebug("checkAvails "+psitem.getKey()+" plaOsnErrors "+
					plaOsnErrors+" plaAvailOSNTbl.keys "+plaAvailOSNTbl.keySet());

			Hashtable loAvailOSNTbl = new Hashtable();
			boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl,pslastOrderAvailVct,true,CHECKLEVEL_RE);
			addDebug("checkAvails "+psitem.getKey()+" loOsnErrors "+
					loOsnErrors+" loAvailOSNTbl.keys "+loAvailOSNTbl.keySet());
			//Change 20111216	31.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
			//{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
			if(!plaOsnErrors && !loOsnErrors){
				// only do this check if no errors were found building the OSN buckets
				checkAvailCtryByOSN(loAvailOSNTbl,plaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", psitem, true, oldDatachklvl);
			}
			plaAvailOSNTbl.clear();
			loAvailOSNTbl.clear();			
		}
	}
	
	private void checkPsAvailCWithFeatureW(Vector pslastOrderAvailVct, EntityItem psitem, EntityItem featItem, int checklvl) throws SQLException, MiddlewareException {
		if(pslastOrderAvailVct.size()>0){
			//18.00	WHEN		AVAILTYPE	=	"Last Order" or "MES Last Order"
			for (int i=0; i<pslastOrderAvailVct.size(); i++){
				EntityItem avail = (EntityItem)pslastOrderAvailVct.elementAt(i);
				//32.00			EFFECTIVEDATE	<=	FEATURE	WITHDRAWDATEEFF_T		W	W	E
				//{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
				checkCanNotBeLater(psitem,avail, "EFFECTIVEDATE", featItem, "WITHDRAWDATEEFF_T", checklvl);

				//old31.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST		W	W	E*1 - PER PRODSTRUCT
				//oldcheckPlannedAvailForCtryExists(psitem,avail, plaAvlCtry, oldDatachklvl);
			}
		}
	}
	
	/**
	 * Check PRODSTRUCT Plan AVAIL or MES Plan AVAIL
	 * @param plannedAvailVct
	 * @param featItem
	 * @param featCtrylist
	 * @param firstAnnDate
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkPsPlanOrMesPlanAvail(Vector plannedAvailVct, EntityItem featItem, ArrayList featCtrylist, String firstAnnDate, int checklvl) throws SQLException, MiddlewareException {
		//15.00	AVAIL	A	PRODSTRUCT-u:OOFAVAIL-d								AVAIL PA
    	//16.00	WHEN		AVAILTYPE	=	"Planned Availability" or "MES Planned Availability"
    	for (int i=0; i<plannedAvailVct.size(); i++){
    		EntityItem avail = (EntityItem)plannedAvailVct.elementAt(i);
    		EntityItem psitem = getAvailPS(avail, "OOFAVAIL");
    		//17.00 COUNTRYLIST	in	FEATURE	COUNTRYLIST
    		checkAvailCtryInEntity(psitem, avail, featItem,featCtrylist,checklvl);

			//18.00			EFFECTIVEDATE	=>	FEATURE	FIRSTANNDATE		W	W	E
	    	//{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {ANNDATE}
			checkCanNotBeEarlier(psitem,avail, "EFFECTIVEDATE", featItem, "FIRSTANNDATE", checklvl);
		}

    	if (firstAnnDate.length()>0){
    		//filter out non RFA avails
    		Vector tmpPLAVct = new Vector(plannedAvailVct);
    		// remove any that are not AVAILANNTYPE=RFA
        	removeNonRFAAVAIL(tmpPLAVct);

        	//19.00	ANNOUNCEMENT	B	A: + AVAILANNA-d 								ANNOUNCEMENT PA
        	//20.00	WHEN		ANNTYPE	=	New (19)
        	//21.00			ANNDATE	=>	FEATURE	FIRSTANNDATE		W	W	E
        	//{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    		Vector annVct = PokUtils.getAllLinkedEntities(tmpPLAVct, "AVAILANNA", "ANNOUNCEMENT");
    		addDebug("checkAvails all annVct "+annVct.size());
    		annVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNTYPE", ANNTYPE_NEW);
    		addDebug("checkAvails PLA NEW annVct "+annVct.size());
    		for (int i=0; i<annVct.size(); i++){
    			EntityItem annItem = (EntityItem)annVct.elementAt(i);
    			checkCanNotBeEarlier(annItem, "ANNDATE", featItem, "FIRSTANNDATE", checklvl);
    		}
    		annVct.clear();
    		tmpPLAVct.clear();
    	}
	}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "FEATURE ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
    	return "1.23";
    }
    /**
    W.	SetBHInvnameHW

    This check runs for a FEATURE.

If Value of (BHINVNAME) is Empty (aka Null) 
OR
if VALFROM(INVNAME) > VALFROM(BHINVNAME)
then Derive New Value for BHINVNAME

Derive New Value for BHINVNAME as follows:
SEARCH FEATURE  not restricted by PDHDOMAIN based on 
�	INVNAME
�	INVENTORYGROUP

If the search only finds one FEATURE (i.e. itself), 
then 
	BHINVNAME = INVNAME
else
	BHINVNAME = FEATURECODE & "-" & INVNAME

If the BHINVNAME is successfully saved, then proceed with the CHECKS

     * @param featItem
     * @throws Exception 
     */
    private void setBHInvnameHW(EntityItem featItem) throws Exception
    {
    	boolean derive = true;
    	
		String fcode = PokUtils.getAttributeValue(featItem, "FEATURECODE",", ", "", false);
    	String bhinvname = PokUtils.getAttributeValue(featItem, "BHINVNAME",", ", null, false);
    	String invname = PokUtils.getAttributeValue(featItem, "INVNAME",", ", null, false);
    	String invgrp = getAttributeFlagEnabledValue(featItem, "INVENTORYGROUP");
    	
    	int maxLen = EANMetaAttribute.TEXT_MAX_LEN;
    	//6.20				CheckMAX	FEATURE	BHINVNAME		RE	RE	RE		LD(FEATURE) NDN(FEATURE) LD(BHINVNAME) has a derived value longer than (value of MAX) characters
		EANMetaAttribute metaAttr = featItem.getEntityGroup().getMetaAttribute("BHINVNAME");
		if(metaAttr!=null){
			maxLen = metaAttr.getMaxLen();
		}
		
    	addDebug("setBHInvnameHW checking "+featItem.getKey()+" fcode: "+fcode+
    			" bhinvname: "+bhinvname+" invname: "+invname+" invgrp: "+invgrp+" maxLen: "+maxLen);
    	
    	if(invname==null){
    		//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
    		args[0]="";
    		args[1]=PokUtils.getAttributeDescription(featItem.getEntityGroup(), "INVNAME", "INVNAME");
    		createMessage(CHECKLEVEL_E,"REQ_NOTPOPULATED_ERR",args); 
    		return;
    	}
    	/*
If Value of (BHINVNAME) is Empty (aka Null) 
OR
if VALFROM(INVNAME) > VALFROM(BHINVNAME)
then Derive New Value for BHINVNAME
    	 */
    	
    	if(bhinvname!=null){
    		// get the attributehistory for INVNAME
    		String invnameDts = getTimestamp(featItem, "INVNAME");
    		// get the attributehistory for BHINVNAME
    		String bhinvnameDts = getTimestamp(featItem, "BHINVNAME");
        	addDebug("setBHInvnameHW invnameDts: "+invnameDts+" bhinvnameDts: "+bhinvnameDts);
        	derive = bhinvnameDts.compareTo(invnameDts)<0;
    	}
    	
    	if(derive){
    		// search for FEATUREs with same INVNAME and INVENTORYGROUP
    		EntityItem eia[] = searchForFeature(invgrp, invname); 
    		//If the search only finds one FEATURE (i.e. itself), 
    		//then 
    		//	BHINVNAME = INVNAME
    		//else
    		//	BHINVNAME = FEATURECODE & "-" & INVNAME

    		if(eia==null || eia.length==1){ // null is an error in search
    			bhinvname = invname;
    		}else{
    			bhinvname = fcode+"-"+invname;
    		}
	  
    	   	addDebug("setBHInvnameHW derived bhinvname: "+bhinvname);
    	   	// always set this now
    	   	setTextValue(m_elist.getProfile(), "BHINVNAME", bhinvname, featItem);
    	}
    	
    	// check existing values too
    	if (bhinvname.length()>maxLen){
    		//If length (bhinvname) > maxLen, then set Return Code = Fail the data quality checks with an 
    		//ErrorMessage LD(FEATURE) NDN(FEATURE) LD(BHINVNAME) "has a derived value longer than (value of MAX) characters."
    		//DERIVED_LEN_ERR = {0} has a derived value longer than {1} characters: &quot;{2}&quot;.
    		args[0] = PokUtils.getAttributeDescription(featItem.getEntityGroup(), "BHINVNAME", "BHINVNAME");
    		args[1] = ""+maxLen;
    		args[2] = bhinvname;
    		createMessage(CHECKLEVEL_RE,"DERIVED_LEN_ERR",args);
    	}
    }    
	/**
	 * SWFEATURE and FEATURE may derive BHINVNAME that must be committed even though ABR fails
	 * @param rek
	 */
	protected void removeAttrBeforeCommit(ReturnEntityKey rek){
		Attribute bhinvnameAttr = null;
		for (int ii=0; ii<rek.m_vctAttributes.size(); ii++){
			Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(ii);
			if(attr.getAttributeCode().equals("BHINVNAME")){
				bhinvnameAttr = attr;
				break;
			}
		}
		rek.m_vctAttributes.clear();
		// put this one back
		if(bhinvnameAttr!=null){
			rek.m_vctAttributes.addElement(bhinvnameAttr);
		}
	}
    /**
     *  SEARCH FEATURE  not restricted by PDHDOMAIN based on 
     *      �	INVNAME
     *      �	INVENTORYGROUP
     * @param invgrp
     * @param invname
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     */
    private EntityItem[] searchForFeature(String invgrp, String invname) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector attrVct = new Vector(2);
		Vector valVct = new Vector(2);
		attrVct.addElement("INVNAME");
		attrVct.addElement("INVENTORYGROUP");

		valVct.addElement(invname);
		valVct.addElement(invgrp);

		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			addDebug("searchForFeature using attrs: "+attrVct+" values: "+valVct);
			eia= ABRUtil.doSearch(getDatabase(), m_elist.getProfile(), 
					FC_SRCHACTION_NAME, "FEATURE", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug("searchForFeature SBRException: "+exBuf.getBuffer().toString());
		}
			
		if(eia!=null){
			for (int i=0; i<eia.length; i++){
				addDebug("searchForFeature found "+eia[i].getKey());
			}
		}

		attrVct.clear();
		valVct.clear();
		return eia;
	}
    
    /**
     * check if each character of a text field against a specified list of characters.
     * @param arrayChars list of characters
     * @param str String in text field 
     * @return if contains, return true, else false
     */
    private boolean checkChars(char[] arrayChars, String str){
    	for (int i = 0; i < arrayChars.length; i++) {
			if(str.indexOf(arrayChars[i]) >= 0){
				return true;
			}
		}
    	return false;
    }
}