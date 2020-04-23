// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
SWFCABRSTATUS_class=COM.ibm.eannounce.abr.sg.SWFCABRSTATUS
SWFCABRSTATUS_enabled=true
SWFCABRSTATUS_idler_class=A
SWFCABRSTATUS_keepfile=true
SWFCABRSTATUS_read_only=true
SWFCABRSTATUS_report_type=DGTYPE01
SWFCABRSTATUS_vename=EXRPT3SWFEATURE1
SWFCABRSTATUS_CAT1=RPTCLASS.SWFCABRSTATUS
SWFCABRSTATUS_CAT2=
SWFCABRSTATUS_CAT3=RPTSTATUS
SWFCABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390


 *
 * SWFCABRSTATUS.java,v
 * Revision 1.20  2011/03/24 15:22:12  wendy
 * Add CATDATA support and SWPRODSTRUCT-EOMAVAIL checks
 *
 * Revision 1.19  2011/03/05 00:59:58  wendy
 * MNIN454169 - BH FS ABR Data Quality 20110303.doc
 *
 * Revision 1.13  2010/03/15 13:44:16  wendy
 * BH FS ABR Data Quality 20100313.doc updates
 *
 * Revision 1.12  2010/01/21 14:52:08  wendy
 * update cmts
 *
 * Revision 1.10  2010/01/20 16:09:34  wendy
 * cvs failure again
 *
 * Revision 1.7  2009/12/20 01:51:53  wendy
 * Updated to handle old data
 *
 * Revision 1.6  2009/11/04 15:08:07  wendy
 * BH Configurable Services - spec chgs
 *
 * Revision 1.5  2009/09/09 16:45:08  wendy
 * Check avails by (xx)prodstruct instead of as a group
 *
 * Revision 1.4  2009/08/17 15:30:10  wendy
 * Added headings
 *
 * Revision 1.3  2009/08/15 01:41:50  wendy
 * SR10, 11, 12, 15, 17 BH updates phase 4
 *
 * Revision 1.2  2009/08/02 19:08:10  wendy
 * SR10, 11, 12, 15, 17 BH updates phase 2
 *
 * Revision 1.1  2009/07/30 18:54:36  wendy
 * Moved to new pkg for BH SR10, 11, 12, 15, 17
  */
package COM.ibm.eannounce.abr.sg.bh;

//import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.Attribute;

import com.ibm.transform.oim.eacm.util.*;

import java.sql.SQLException;
import java.util.*;

/**********************************************************************************
* SWFCABRSTATUS class
* 
* BH FS ABR Data Quality 20110322.doc
* checks 16.20 added
* 
* MNIN454169 - BH FS ABR Data Quality 20110303.doc
* 
* BH FS ABR Data Qualtity Sets 20100629.xls
* need LIFECYCLE meta and workflow actions
* 
* from SG FS ABR Data Quality 20100521.doc
*
need meta
  - BHINVNAME added
*
A.	Checking

A Software Feature (SWFEATURE) is only GA since the MODEL (aka PID) handles special bids.

Software Feature has only two planning dates and does not have a list of countries. 

Software Product Structure (SWPRODSTRUCT) does not have any planning dates.

The checking is limited to checking the Software Feature dates with the Software Product Structure 
Availability (AVAIL) dates.

B.	Derivation

The following is performed in addition to the Checking.

For each SWPRODSTRUCT
Perform SetBHInvnameSW


C.	Status changed to Ready for Review

See the embedded SS.
D.	Status changed to Final

See the embedded SS.


*/
public class SWFCABRSTATUS extends DQABRSTATUS
{
	//private static final String SWPS_SRCHACTION_NAME = "";  // need search not restricted by domain
	
	//add the chunk to avoid outofmemory error
	private static final int MW_VENTITY_LIMIT;// get this from properties
	static{
		String velimit = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(
				"SWFCABRSTATUS","_velimit","5");//not set, will use the default value - 5
		int limit = Integer.parseInt(velimit);		
		if(limit <= 0){
			limit = 1000;// if we set it to zero, will set it to one large number in the code to get all.
		}
		MW_VENTITY_LIMIT = limit;
	}
	
	/**********************************
	* must be in list of domains
	*/
	protected boolean isVEneeded(String statusFlag) {
		return domainInList();
	}

/*
from sets ss:
177.000		SWFEATURE		Root Entity							
178.020	R1.0	IF			SWFEATURE	STATUS	=	"Ready for Review" (0040)			
178.040	R1.0	AND			SWFEATURE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
178.060	R1.0	SET			SWFEATURE				ADSABRSTATUS	&ADSFEEDRFR	
178.080	R1.0	END	178.020								
178.100	R1.0	IF			SWFEATURE	STATUS	=	"Final" (0020)			
178.120	R1.0	SET			SWFEATURE				ADSABRSTATUS		&ADSFEED
178.140	R1.0	END	178.100								
179.000	R1.0	END	177.000	SWFEATURE							
 */	
    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
	* C.	Status changed to Ready for Review
178.020	R1.0	IF			SWFEATURE	STATUS	=	"Ready for Review" (0040)			
178.040	R1.0	AND			SWFEATURE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
178.060	R1.0	SET			SWFEATURE				ADSABRSTATUS	&ADSFEEDRFR	
178.080	R1.0	END	178.020		
    */
    protected void completeNowR4RProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	//could use this.doRFR_ADSXML(rootItem);
		if(doR10processing()){
	    	EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);	
	    	String lifecycle = PokUtils.getAttributeFlagValue(rootItem, "LIFECYCLE");
	    	addDebug("completeNowR4RProcessing: "+rootItem.getKey()+" lifecycle "+lifecycle);
	  		if (lifecycle==null || lifecycle.length()==0){ 
				lifecycle = LIFECYCLE_Plan;
			}
	    	if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
	    			LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
	    		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
	    	}
		}
    }

    /**********************************
    * complete abr processing after status moved to final; (status was r4r)
    *D. STATUS changed to Final
    *
178.100	R1.0	IF			SWFEATURE	STATUS	=	"Final" (0020)			
178.120	R1.0	SET			SWFEATURE				ADSABRSTATUS		&ADSFEED
178.140	R1.0	END	178.100		
    */
    protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		if(doR10processing()){
			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
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
    protected String getLCRFRWFName(){ return "WFLCSWFEATRFR";} 
    protected String getLCFinalWFName(){ return "WFLCSWFEATFINAL";} 
 
    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	* XXX.	SWFEATURE

A.	Checking

checks from ss:
	1.00	SWFEATURE											
	2.00			WITHDRAWANNDATE_T									
	3.00			WITHDRAWDATEEFF_T	=>	SWFEATURE	WITHDRAWANNDATE_T		W	E	E		{LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} must not be earlier than the {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
	4.00			FCTYPE	<>	"Primary FC (100) |""Secondary FC"" (110)"			E	E	E	"Error RPQ logic"	{LD: SWFEATURE} {NDN: SWFEATURE} can not be {LD: FCTYPE} {FCTYPE}
4.20				CheckMAX	SWFEATURE	BHINVNAME		RE	RE	RE		LD(SWFEATURE) NDN(SWFEATURE) LD(BHINVNAME) has a derived value longer than (value of MAX) characters
	5.00	WHEN		FCTYPE	=	"Primary FC (100) |""Secondary FC"" (110)"						GA logic	
	6.00	AVAIL	A	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d									
	7.00	WHEN		AVAILTYPE	=	"Planned Availability"							
	9.00			EFFECTIVEDATE								Nothing to check, this is a comment	
	10.00	IF		NULL	<>	SWFEATURE	WITHDRAWDATEEFF_T						
	11.00	THEN		COUNTRYLIST	"IN aggregate G" B: AVAIL	COUNTRYLIST		RW	RE	RE		has a {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} for a country that does not have a "Last Order"
	12.00	END	7										
	13.00	AVAIL	B	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d									
	14.00	WHEN		AVAILTYPE	=	"Last Order"							
	15.00			EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWDATEEFF_T		W	E	E		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
	16.00			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		E*1	E*1	E*1		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
16.20	IF		"RFA" (RFA)	<>	A: AVAIL	AVAILANNTYPE						
16.22			CountOf	=	0			E	E	E	Only AVAIL.AVAILANNTYPE = "RFA" can be in an ANNOUNCEMENT	{LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
16.90	ELSE	16.20										
	17.00	ANNOUNCEMENT		B:AVAILANNA-d 									
	18.00	WHEN		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)							
	19.00			ANNDATE	<=	SWFEATURE	WITHDRAWANNDATE_T		W	E	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD:WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
	20.00	END	18	
20.20	END	16.20
	21.00	END	14	
21.02	AVAIL	B	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d									
21.04	WHEN		AVAILTYPE	=	"End of Marketing" (200)							
21.06			EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWANNDATE_T		W	W	E		must not be later than {LD: SWFEATURE} {NDN:SWPRODSTRUCT} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
21.08	END	21.04										
22.00	END	5.00										
										
	22.00	END	5										

	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
     	addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");
     	
		setBHInvnameSW(rootEntity);
		
		if(getReturnCode()!=PASS){ // BHINVNAME failure
			//If the BHINVNAME is successfully saved, then proceed with the CHECKS
			return;
		}
		
    	int checklvl = getCheck_W_E_E(statusFlag); 
    	//3.00			WITHDRAWDATEEFF_T	=>	SWFEATURE	WITHDRAWANNDATE_T		W	E	E		
    	//{LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} can not be earlier than the {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
    	checkCanNotBeEarlier(rootEntity, "WITHDRAWDATEEFF_T", "WITHDRAWANNDATE_T", checklvl);

    	//	5.00	WHEN		FCTYPE	=	"Primary FC (100) |""Secondary FC"" (110)"						GA logic	
    	if (!isRPQ(rootEntity)){
    		addDebug(rootEntity.getKey()+" was NOT an RPQ FCTYPE: "+getAttributeFlagEnabledValue(rootEntity, "FCTYPE"));
    		checkAvails(rootEntity, statusFlag,checklvl);
    	}else{
    		//4.00			FCTYPE	<>	"Primary FC (100) |""Secondary FC"" (110)"			E	E	E	"Error RPQ logic"	{LD: SWFEATURE} {NDN: SWFEATURE} can not be {LD: FCTYPE} {FCTYPE}
    		//FCTYPE_ERR = {0} can not be {1}
    		addDebug(rootEntity.getKey()+" was an RPQ FCTYPE: "+getAttributeFlagEnabledValue(rootEntity, "FCTYPE"));
    		args[0] = "";
    		args[1] = this.getLD_Value(rootEntity,"FCTYPE");
			addError("FCTYPE_ERR",args);
    	}
	}
    /**
5.00	WHEN		FCTYPE	=	"Primary FC (100) |""Secondary FC"" (110)"						GA logic	
6.00	AVAIL	A	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d									
7.00	WHEN		AVAILTYPE	=	"Planned Availability"							
delete 8			Count Of	=>	1			W	E	E		must have at least one "Planned Availability"
9.00			EFFECTIVEDATE								Nothing to check, this is a comment	
10.00	IF		NULL	<>	SWFEATURE	WITHDRAWDATEEFF_T						
11.00	THEN		COUNTRYLIST	"IN aggregate G"	B: AVAIL	COUNTRYLIST		RW	RE	RE	has a {LD: AVAIL} {NDN: AVAIL} for a country that does not have a "Last Order"
12.00	END	7										
13.00	AVAIL	B	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d									
14.00	WHEN		AVAILTYPE	=	"Last Order"							
15.00			EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWDATEEFF_T		W	E	E	{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  is later than the {LD: WITHDRAWDATEEFF_T}
16.00			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		E*1	E*1	E*1		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} has a country that does not have a "Planned Availability"
16.20	IF		"RFA" (RFA)	<>	A: AVAIL	AVAILANNTYPE						
16.22			CountOf	=	0			E	E	E	Only AVAIL.AVAILANNTYPE = "RFA" can be in an ANNOUNCEMENT	{LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
16.90	ELSE	16.20
17.00	ANNOUNCEMENT		B:AVAILANNA-d 									
18.00	WHEN		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)							
19.00			ANNDATE	<=	SWFEATURE	WITHDRAWANNDATE_T		W	E	E	{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} is later than the {LD:WITHDRAWANNDATE_T}
20.00	END	18	
20.20	END	16.20									
21.00	END	14	
21.02	AVAIL	B	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d									
21.04	WHEN		AVAILTYPE	=	"End of Marketing" (200)							
21.06			EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWANNDATE_T		W	W	E		must not be later than {LD: SWFEATURE} {NDN:SWPRODSTRUCT} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
21.08	END	21.04										
22.00	END	5.00										
	
here is what is going on
10:50:38 AM: Wayne Kehrli: SWFEATURE does not have COUNTRYLIST
10:50:54 AM: Wayne Kehrli: you must have at least one "Planned Availability"
10:51:03 AM: Wayne Kehrli: if the SWFEATURE has a withdraw date
10:51:24 AM: Wayne Kehrli: then there must be a "Last Order" for every "Planned Availability" Country
     */
	private void checkAvails(EntityItem rootEntity, String statusFlag, int checklvl) 
	throws java.sql.SQLException, MiddlewareException
	{
		//	get all AVAILS
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		if (availGrp ==null){
			throw new MiddlewareException("AVAIL is missing from extract for "+m_abri.getVEName());
		}

		String wdEffDate = PokUtils.getAttributeValue(rootEntity, "WITHDRAWDATEEFF_T",", ", null, false);
		String wdannDate = PokUtils.getAttributeValue(rootEntity, "WITHDRAWANNDATE_T",", ", null, false);
		addDebug("checkAvails "+rootEntity.getKey()+" WITHDRAWDATEEFF_T: "+wdEffDate+" WITHDRAWANNDATE_T: "+wdannDate);
		
		Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", LASTORDERAVAIL);
		Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", PLANNEDAVAIL);
			
	 	addHeading(3,availGrp.getLongDescription()+" Planned Avail Checks:");
	 	
		addDebug("checkAvails  lastOrderAvailVct "+lastOrderAvailVct.size()+" plannedAvailVct "+plannedAvailVct.size());
		//7.00	WHEN		AVAILTYPE	=	"Planned Availability"							
		//delete 8		Count Of	=>	1			W	E	E	must have at least one "Planned Availability"
		//checkPlannedAvailsExist(plannedAvailVct, checklvl);

		EntityGroup psGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
		
		//if the SWFEATURE has a withdraw date
		// then there must be a "Last Order" for every "Planned Availability" Country PER SWPRODSTRUCT
		//10.00	IF		NULL	<>	SWFEATURE	WITHDRAWDATEEFF_T						
		if (plannedAvailVct.size()>0 && //IN1722934 planned avail must exist also!
				wdEffDate != null){ //10	IF		NULL	<>	SWFEATURE	WITHDRAWDATEEFF_T
			if (lastOrderAvailVct.size()==0){
				//AVAIL_ERR = No {0} {1} found.
				args[0]="Last Order";
				args[1]=availGrp.getLongDescription();
				createMessage(getCheck_RW_RE_RE(statusFlag),"AVAIL_ERR",args);
				plannedAvailVct.clear();
			}else{
				addDebug("checkAvails: look for any plannedavail ctry that isnt in a lastorder by SWPS");
				// get all SWPRODSTRUCT - must compare each SWPS-PLA against its own SWPS-LO
				for(int p=0; p<psGrp.getEntityItemCount(); p++){
					EntityItem psitem = psGrp.getEntityItem(p);
					Vector availVct = PokUtils.getAllLinkedEntities(psitem, "SWPRODSTRUCTAVAIL", "AVAIL");
					Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
					Vector pslastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", LASTORDERAVAIL);
					ArrayList loAvailCtry = getCountriesAsList(pslastOrderAvailVct, getCheck_RW_RE_RE(statusFlag));

					addDebug("checkAvails "+psitem.getKey()+" all avail: "+availVct.size()+
							" plaAvail: "+psplannedAvailVct.size()+" loAvail: "+pslastOrderAvailVct.size()+
							" loAvailCtry:"+loAvailCtry);
					// now look for any plannedavail ctry that isnt in a lastorder
					for (int i=0; i<psplannedAvailVct.size(); i++){
						EntityItem avail = (EntityItem)psplannedAvailVct.elementAt(i);
						//11.00	A: AVAIL.COUNTRYLIST "IN aggregate G"	B: AVAIL	COUNTRYLIST		RW	RE	RE	
						String missingCtry = checkCtryMismatch(avail, loAvailCtry, getCheck_RW_RE_RE(statusFlag));
						if (missingCtry.length()>0){
							addDebug("checkAvails plannedavail "+avail.getKey()+" COUNTRYLIST had extra ["+missingCtry+"]");
							//has a {LD: AVAIL} {NDN: AVAIL} for a country that does not have a "Last Order"
							// AVAILCTRY_LASTORDER_ERR = has a {0} for a country that does not have a &quot;Last Order&quot;
							args[0]=getLD_NDN(psitem) +" "+ getLD_NDN(avail);
							createMessage(checklvl,"AVAILCTRY_LASTORDER_ERR",args);
						}		
					}

					availVct.clear();
					psplannedAvailVct.clear();
					pslastOrderAvailVct.clear();
					loAvailCtry.clear();
				}
			}
		}
		
		if (lastOrderAvailVct.size()>0){
			addHeading(3,availGrp.getLongDescription()+" Last Order Avail Checks:");
			addDebug("checkAvails: look for any lastorder ctry that isnt in a plannedavail by SWPS");
			for(int p=0; p<psGrp.getEntityItemCount(); p++){
				EntityItem psitem = psGrp.getEntityItem(p);
				EntityItem mdlItem = getDownLinkEntityItem(psitem, "MODEL");
				int oldDatachklvl = getCheckLevel(CHECKLEVEL_E,mdlItem,"ANNDATE");

				Vector availVct = PokUtils.getAllLinkedEntities(psitem, "SWPRODSTRUCTAVAIL", "AVAIL");
				Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
				Vector pslastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", LASTORDERAVAIL);
				ArrayList plaAvailCtry = getCountriesAsList(psplannedAvailVct, getCheck_RW_RE_RE(statusFlag));

				addDebug("checkAvails "+psitem.getKey()+" all avail: "+availVct.size()+
						" plaAvail: "+psplannedAvailVct.size()+" loAvail: "+pslastOrderAvailVct.size()+
						" plaAvailCtry:"+plaAvailCtry);
				// now look for any lastorder ctry that isnt in a plannedavail
				for (int i=0; i<pslastOrderAvailVct.size(); i++){
					EntityItem avail = (EntityItem)pslastOrderAvailVct.elementAt(i);
					//addDebug("checkAvails lastorderavail "+avail.getKey()+" "+psitem.getKey());

					//14.00	WHEN		AVAILTYPE	=	"Last Order"							
					//15.00			EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWDATEEFF_T		W	E	E	{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  is later than the {LD: WITHDRAWDATEEFF_T}
					checkCanNotBeLater(psitem,avail, "EFFECTIVEDATE", rootEntity, "WITHDRAWDATEEFF_T", checklvl);

					//16.00			B: AVAIL.COUNTRYLIST "IN aggregate G"	A: AVAIL	COUNTRYLIST		E*1	E*1	E*1 PER SWPRODSTRUCT
					checkPlannedAvailForCtryExists(psitem,avail, plaAvailCtry, oldDatachklvl);
					
					String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
					addDebug("checkAvails LO "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
					if (availAnntypeFlag==null){
						availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
					}
					
					//16.20	IF		"RFA" (RFA)	<>	A: AVAIL	AVAILANNTYPE
					//16.22			CountOf	=	0			E	E	E	Only AVAIL.AVAILANNTYPE = "RFA" can be in an ANNOUNCEMENT	{LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
					if (!AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
						Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
						addDebug("checkAvails norfa "+avail.getKey()+" annVct "+annVct.size());
						if(annVct.size()!=0){
							//MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
							args[0] = getLD_NDN(avail);
							for (int x=0; x<annVct.size(); x++){
								args[1] = getLD_NDN((EntityItem)annVct.elementAt(x));
								createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
							}
							annVct.clear();
						}
						continue; // bypass any non RFA from other checks
					}
					//16.90	ELSE	16.20
				}// end lastorder loop

				availVct.clear();
				psplannedAvailVct.clear();
				pslastOrderAvailVct.clear();
				plaAvailCtry.clear();
			}

			plannedAvailVct.clear();

			//17.00	ANNOUNCEMENT		B:AVAILANNA-d 									
			//18.00	WHEN		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)							
			if (wdannDate!=null){ // here date is not required, SWPRODSTRUCT key 24.00 is required
				Vector tmpLOVct = new Vector(lastOrderAvailVct);

				//16.90	ELSE	16.20 (IF AVAILANNTYPE	=	"RFA" (RFA))		
				removeNonRFAAVAIL(tmpLOVct);				// remove any that are not AVAILANNTYPE=RFA 

				Vector annVct = PokUtils.getAllLinkedEntities(tmpLOVct, "AVAILANNA", "ANNOUNCEMENT");
				addDebug("checkAvails annVct "+annVct.size());
				annVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNTYPE", ANNTYPE_EOL);
				addDebug("checkAvails EOL annVct "+annVct.size());
				for (int i=0; i<annVct.size(); i++){
					EntityItem annItem = (EntityItem)annVct.elementAt(i);
					//19.00			ANNDATE	<=	SWFEATURE	WITHDRAWANNDATE_T		W	E	E	{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}{LD:ANNDATE}{ANNDATE} can not be later than the  {LD:SWFEATURE} {LD:WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
					checkCanNotBeLater(annItem, "ANNDATE", rootEntity, "WITHDRAWANNDATE_T", checklvl);
				}
				annVct.clear();
				tmpLOVct.clear();
			}
			lastOrderAvailVct.clear();
		} //end last order avails exist
		//20.20	END	16.20
		//21.00	END	14.00
		
		addHeading(3,availGrp.getLongDescription()+" End of Marketing Avail Checks:");
		//21.02	AVAIL	B	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d									
		for(int p=0; p<psGrp.getEntityItemCount(); p++){
			EntityItem psitem = psGrp.getEntityItem(p);
			Vector availVctB = PokUtils.getAllLinkedEntities(psitem, "SWPRODSTRUCTAVAIL", "AVAIL");
			//21.04	WHEN		AVAILTYPE	=	"End of Marketing" (200)							
			Vector psEOMAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVctB, "AVAILTYPE", EOMAVAIL);

			addDebug("checkAvails "+psitem.getKey()+" all avail: "+availVctB.size()+
					" psEOMAvailVct: "+psEOMAvailVct.size());
			
			checklvl=getCheck_W_W_E(statusFlag);
			for (int i=0; i<psEOMAvailVct.size(); i++){
				EntityItem avail = (EntityItem)psEOMAvailVct.elementAt(i);
				//21.06			EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: SWFEATURE}{LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
				checkCanNotBeLater(psitem,avail, "EFFECTIVEDATE", rootEntity, "WITHDRAWANNDATE_T", checklvl);
				//21.08	END	21.04
			}
			
			availVctB.clear();
			psEOMAvailVct.clear();
		}
		//22.00	END	5.00
	}
    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "SWFEATURE ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
    	return "1.20";
    }
    /*****
     X.	SetBHInvnameSW

This checks Software Features (SWFEATURE) having a unique BHINVNAME within a PID (aka MODEL). 
If a user creates a SWFEATURE that does not have any relationship (SWPRODSTRUCT) to a Model (MODEL), 
then the check cannot be made (see logic below); however the derivation always applies.

The check is applicable to a Software Feature that is updated after it is related to a Model.

If Value of (BHINVNAME) is Empty (aka Null) 
OR
if VALFROM(INVNAME) > VALFROM(BHINVNAME)
then Derive New Value for BHINVNAME

Derive New Value for BHINVNAME as follows:
If the SWFEATURE is related to a MODEL via SWPRODSTRUCT, then 
	Search for SWPRODSTRUCT that is not restricted by PDHDOMAIN) using:
	�	MACHTYPEATR
	�	MODELATR
	�	INVNAME
	If  only one is found (i.e. itself),
	then BHINVNAME = INVNAME
	else BHINVNAME = FEATURECODE & �-� & INVNAME
else BHINVNAME = INVNAME

If the BHINVNAME is successfully saved, then proceed with the CHECKS.
     * @throws Exception 

     */
    private void setBHInvnameSW(EntityItem featItem) throws Exception
    {
    	//	get all SWPRODSTRUCT
		EntityGroup swpsGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
		boolean derive = true;
    	
		String fcode = PokUtils.getAttributeValue(featItem, "FEATURECODE",", ", "", false);
    	String bhinvname = PokUtils.getAttributeValue(featItem, "BHINVNAME",", ", null, false);
    	String invname = PokUtils.getAttributeValue(featItem, "INVNAME",", ", null, false);
    	
    	int maxLen = EANMetaAttribute.TEXT_MAX_LEN;
    	//6.20				CheckMAX	SWFEATURE	BHINVNAME		RE	RE	RE		LD(FEATURE) NDN(FEATURE) LD(BHINVNAME) has a derived value longer than (value of MAX) characters
		EANMetaAttribute metaAttr = featItem.getEntityGroup().getMetaAttribute("BHINVNAME");
		if(metaAttr!=null){
			maxLen = metaAttr.getMaxLen();
		}
		
    	addDebug("setBHInvnameSW checking "+featItem.getKey()+" fcode: "+fcode+
    			" bhinvname: "+bhinvname+" invname: "+invname+" maxLen: "+maxLen+
    			" swpsGrp.count: "+swpsGrp.getEntityItemCount());
    	
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
    		addDebug("setBHInvnameSW invnameDts: "+invnameDts+" bhinvnameDts: "+bhinvnameDts);
    		derive = bhinvnameDts.compareTo(invnameDts)<0;
    	}
    	
    	if(derive){
    		//If the SWFEATURE is related to a MODEL via SWPRODSTRUCT, then 
    		//Search for SWPRODSTRUCT that is not restricted by PDHDOMAIN) using:
    		//	�	MACHTYPEATR
    		//	�	MODELATR
    		//	�	INVNAME
    		if(swpsGrp.getEntityItemCount()>0){
    			//If the search only finds one SWFEATURE (i.e. itself), 
    			//then 
    			//	BHINVNAME = INVNAME
    			//else
    			//	BHINVNAME = FEATURECODE & "-" & INVNAME
    			if(uniqueSWPS(featItem,invname)){
    				bhinvname = invname;
    			}else{
    				bhinvname = fcode+"-"+invname;
    			}
    		}else{
    			//else BHINVNAME = INVNAME
    			bhinvname = invname;
    		}
	
    	   	addDebug("setBHInvnameSW derived bhinvname: "+bhinvname);

    	   	// always set now
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
     * using extract to improve performance
     * Search for SWPRODSTRUCT that is not restricted by PDHDOMAIN) using:
    			�	MACHTYPEATR
    			�	MODELATR
    			�	INVNAME
     * @param featItem
     * @param invname
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     */
    private boolean uniqueSWPS(EntityItem featItem, String invname) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
    	boolean unique = true;
    	// pull an extract from these MODELs to all of their SWFEATUREs
    	//	get all MODEL
		EntityGroup mdlGrp = m_elist.getEntityGroup("MODEL");
		EntityItem[] mdlArray = mdlGrp.getEntityItemsAsArray();
		addDebug("uniqueSWPS: the nubmer of model - total :"+mdlArray.length);
		Vector tmpMdlList = new Vector();
		for (int i = 0; i < mdlArray.length; i++) {
			tmpMdlList.add(mdlArray[i]);
			if((i+1)%MW_VENTITY_LIMIT ==0){ //use chunk to limit the number of model that we will deal with
				 unique = isUniqueSWPS(featItem, invname, tmpMdlList);
			     tmpMdlList.clear();
			     if(!unique)
			    	 break;
			}		
		}
		if(unique && tmpMdlList.size() > 0){//the number of model < limit or the rest models that were not checked.			
			unique = isUniqueSWPS(featItem, invname, tmpMdlList);
			tmpMdlList.clear();
		}
		return unique;
	}
    
    private boolean isUniqueSWPS(EntityItem featItem, String invname,
			Vector tmpMdlList) throws SQLException,
			MiddlewareException, MiddlewareRequestException {
		String VeName = "DQVESWPRODSTRUCT2";
		boolean unique = true;
		EntityItem ei[] = new EntityItem[tmpMdlList.size()];
		tmpMdlList.copyInto(ei);
		addDebug("isUniqueSWPS: the nubmer of model :"+tmpMdlList.size());
		EntityList mdlList = m_db.getEntityList(m_elist.getProfile(), 
				new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName), 
				ei);
		addDebug("isUniqueSWPS VE:"+VeName+"\n"+PokUtils.outputList(mdlList));
		
		EntityGroup swfcGrp = mdlList.getEntityGroup("SWFEATURE");
		for(int p=0; p<swfcGrp.getEntityItemCount(); p++){
			EntityItem swfcitem = swfcGrp.getEntityItem(p);
			String swfcInvname = PokUtils.getAttributeValue(swfcitem, "INVNAME",", ", null, false);
			addDebug("isUniqueSWPS checking "+swfcitem.getKey()+" swfcInvname "+swfcInvname);
			if (swfcitem.getKey().equals(featItem.getKey())){ // skip this one
				continue;
			}
			if (invname.equals(swfcInvname)){
				unique = false;
				break;
			}
		}
		mdlList.dereference();
		return unique;
	}
    /*private boolean uniqueSWPS(String invname,EntityGroup swpsGrp) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
    	int foundCnt = 0;
		Vector attrVct = new Vector(3);
		Vector valVct = new Vector();
		attrVct.addElement("SWFEATURE:INVNAME"); 
		attrVct.addElement("MODEL:MACHTYPEATR");
		attrVct.addElement("MODEL:MODELATR");
		
		for(int p=0; p<swpsGrp.getEntityItemCount() && foundCnt<2; p++){
			EntityItem psitem = swpsGrp.getEntityItem(p);
			EntityItem mdlItem = getDownLinkEntityItem(psitem, "MODEL");
			addDebug("uniqueSWPS psitem: "+psitem.getKey()+" mdlItem: "+mdlItem.getKey());
			valVct.clear();
			valVct.addElement(invname);
			valVct.addElement(PokUtils.getAttributeFlagValue(mdlItem, "MACHTYPEATR"));
			valVct.addElement(PokUtils.getAttributeValue(mdlItem, "MODELATR", "", "", false));
			EntityItem eia[]= null;
			try{
				StringBuffer debugSb = new StringBuffer();
				addDebug("uniqueSWPS for attrs: "+attrVct+" values: "+valVct);
				eia= ABRUtil.doSearch(getDatabase(), m_elist.getProfile(), 
						SWPS_SRCHACTION_NAME, "SWPRODSTRUCT", true, attrVct, valVct, debugSb);
				if (debugSb.length()>0){
					addDebug(debugSb.toString());
				}
			}catch(SBRException exc){
				// these exceptions are for missing flagcodes or failed business rules, dont pass back
				java.io.StringWriter exBuf = new java.io.StringWriter();
				exc.printStackTrace(new java.io.PrintWriter(exBuf));
				addDebug("uniqueSWPS SBRException: "+exBuf.getBuffer().toString());
			}
				
			if(eia!=null){
				for (int i=0; i<eia.length; i++){
					addDebug("uniqueSWPS found "+eia[i].getKey());
				}
				foundCnt+=eia.length;
			}		
		}

		attrVct.clear();
		valVct.clear();
		return foundCnt==1;
	}*/	
}
