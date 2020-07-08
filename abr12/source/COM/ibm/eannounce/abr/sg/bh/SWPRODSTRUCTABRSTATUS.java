//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2009,2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import COM.ibm.opicmpdh.middleware.*;

import java.util.*;

/**********************************************************************************
 * SWPRODSTRUCTABRSTATUS class
 * 
 *BH FS ABR Data Quality 20111020e.xls BH Defect 67890 (support for old data)
 *sets changes
 * BH FS ABR Data Quality Checks 20110322.xls
 * Change 21.20
 * 
 * BH FS ABR Data Quality Sets 20101101.xls
 * sets chgs
 * 
 * BH FS ABR Data Quality Sets 20100629.xls
 * need LIFECYCLE meta and workflow actions
 * 
 * BH FS ABR Data Quality 20100521.doc
 * 
 *
 * From "SG FS ABR Data Quality 20090729.doc"
 *
The NDN of SWPRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
SWFEATURE.FEATURECODE

A.	Checking

Software Product Structure (SWPRODSTRUCT) does not have any planning dates.

A Software Feature (SWFEATURE) is only GA since the MODEL (aka PID) handles special bids.

Software Feature has only two planning dates and does not have a list of countries. 

The checking is limited to checking the Software Feature dates with the Software 
Product Structure Availability (AVAIL) dates.


B.	STATUS changed to Ready for Review

1.	Set ADSABRSTATUS = 0020 (Queued)
C.	STATUS changed to Final

1.	QueueSAPL
2.	Set EPIMSABRSTATUS =  &ABRWAITODS
3.	Set ADSABRSTATUS = 0020 (Queued)

Note:  SWFEATURE does not flow to WWPRT
D.	STATUS = Final

1.	Set SAPLABRSTATUS = 0020 (Queued) 


SWPRODSTRUCTABRSTATUS_class=COM.ibm.eannounce.abr.sg.SWPRODSTRUCTABRSTATUS
SWPRODSTRUCTABRSTATUS_enabled=true
SWPRODSTRUCTABRSTATUS_idler_class=A
SWPRODSTRUCTABRSTATUS_keepfile=true
SWPRODSTRUCTABRSTATUS_report_type=DGTYPE01
SWPRODSTRUCTABRSTATUS_vename=DQVESWPRODSTRUCT
SWPRODSTRUCTABRSTATUS_CAT1=RPTCLASS.SWPRODSTRUCTABRSTATUS
SWPRODSTRUCTABRSTATUS_CAT2=
SWPRODSTRUCTABRSTATUS_CAT3=RPTSTATUS
SWPRODSTRUCTABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
 */
//SWPRODSTRUCTABRSTATUS.java,v
//Revision 1.14  2010/07/12 16:48:44  wendy
//updates for BH FS ABR Data Qualtity Sets 20100629.xls

//Revision 1.10  2010/01/21 14:53:46  wendy
//update cmts

//Revision 1.6  2009/12/17 20:12:34  wendy
//Updated 'sets' when ABR passes

//Revision 1.5  2009/11/04 15:08:07  wendy
//BH Configurable Services - spec chgs

//Revision 1.4  2009/08/17 15:20:36  wendy
//Added headings

//Revision 1.3  2009/08/15 01:41:50  wendy
//SR10, 11, 12, 15, 17 BH updates phase 4

//Revision 1.2  2009/08/06 22:24:31  wendy
//SR10, 11, 12, 15, 17 BH updates phase 3

//Revision 1.1  2009/07/30 18:54:36  wendy
//Moved to new pkg for BH SR10, 11, 12, 15, 17

public class SWPRODSTRUCTABRSTATUS extends DQABRSTATUS
{
	private EntityList mdlList = null;

	/**********************************
	 * always check if not final, but need navigation name from model and fc
	 */
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/*
from sets ss:						
180.00		SWPRODSTRUCT		Root Entity							
180.20	R1.0	IF		SWPRODSTRUCT-d	MODEL	ANNDATE	>	"2010-03-01"			
Delete 2011-10-20		180.220		OR			MODEL	PDHDOMAIN	IN	XCC_LIST		
181.02	R1.0	IF			SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)			
181.04	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
181.06	R1.0	IF		SWPRODSTRUCTAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
181.07	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
181.08	R1.0	OR			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
181.10	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
181.11	R1.0	END	181.07							

181.12		END	181.06								
181.14	R1.0	END	181.02								
181.16	R1.0	IF			SWPRODSTRUCT	STATUS	=	"Final" (0020)			
181.18	R1.0	IF		SWPRODSTRUCTAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020)			
181.20	R1.0	AND			ANNOUNCEMENT	STATUS	=	"Final" (0020)			
181.22	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
181.24	R1.0	END	181.18								
181.26	R1.0	END	181.16								
Add 2011-10-20		181.280	R1.0	IF			SWPRODSTRUCT	PDHDOMAIN	IN	XCC_LIST			
		182.000	R1.0	SET			SWPRODSTRUCT				EPIMSABRSTATUS		&ABRWAITODS
Add 2011-10-20		182.020	R1.0	END	181.280								

182.10	R1.0	ELSE	180.20								
182.12	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
182.14	R1.0	END	180.20								
183.00	R1.0	END	180.00	SWPRODSTRUCT							

	 */
	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq)
	 * C.	Status changed to Ready for Review
180.00		SWPRODSTRUCT		Root Entity							
180.20	R1.0	IF		SWPRODSTRUCT-d	MODEL	ANNDATE	>	"2010-03-01"			
Delete 2011-10-20		180.220		OR			MODEL	PDHDOMAIN	IN	XCC_LIST			
181.02	R1.0	IF			SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)			
181.04	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
181.06	R1.0	IF		SWPRODSTRUCTAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
181.07	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
181.08	R1.0	OR			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
181.10	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
181.11	R1.0	END	181.07							

181.12		END	181.06								
181.14	R1.0	END	181.02								
Final processing...
182.10	R1.0	ELSE	180.20								
182.12	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
182.14	R1.0	END	180.20								
183.00	R1.0	END	180.00	SWPRODSTRUCT							

	 */
	protected void completeNowR4RProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);

		boolean oldData = this.isOldData(mdlItem, "ANNDATE");
		//boolean inxcclist = domainInRuleList(mdlItem, "XCC_LIST");
		addDebug("nowRFR: "+mdlItem.getKey()+" olddata "+oldData);

		if(this.doR10processing()){
			//180.00		SWPRODSTRUCT		Root Entity							
			//180.20	R1.0	IF		SWPRODSTRUCT-d	MODEL	ANNDATE	>	"2010-03-01"			
			//Delete 2011-10-20		180.220		OR			MODEL	PDHDOMAIN	IN	XCC_LIST	
			if (!oldData)// || inxcclist)
			{
				//181.02	R1.0	IF			SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)			
				//181.04	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
				//181.06	R1.0	IF		SWPRODSTRUCTAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
				//181.07	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
				//181.08	R1.0	OR			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
				//181.10	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
				doR4R_R10Processing(rootEntity,"SWPRODSTRUCTAVAIL");
				//181.11	R1.0	END	181.07	
				//181.12		END	181.06								
				//181.14	R1.0	END	181.02								
			}else{
				//182.10	R1.0	ELSE	180.20								
				//182.12	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
			}
		}
		//182.14	R1.0	END	180.20								
		//183.00	R1.0	END	180.00	SWPRODSTRUCT

		/*
		 * Story 1660439 Support Automatic Loading of SYSTEMMAX on SWPRODSTRUCT
		 * The requirement is to develop an ABR that would automatically populate the SYSTEMMAX value on the SWPRODSTRUCT when the SWPRODSTRUCT status was turned from draft to ready for review.   The following is the logic that would need to be used:

			If SWFEATURE:  SWFCCAT = ValueMetric and the FEATURECODE is 4 digits, set the SYSTEMMAX on the SWPRODSTRUCT to 250
			If SWFEATURE:  SWFCCAT = ValueMetric and the FEATURECODE is 6 digits, set the SYSTEMMAX on the SWPRODSTRUCT to 999
			Else set the SYSTEMMAX to 0
			
			The user should be able to update the data after it has been derived via the ABR.  The ABR should only run once on the initial change from draft to ready for review.
			
			Also,  all blank values should be set to the appropriate value via PDTool for active SW models.
		 */
		String lifecycle = PokUtils.getAttributeFlagValue(rootEntity, "LIFECYCLE");
		addDebug("completeNowR4RProcessing LIFECYCLE: " + lifecycle);
//		if (lifecycle == null || "".equals(lifecycle)) {
//			lifecycle = LIFECYCLE_Plan;
//		}
		if (LIFECYCLE_Plan.equals(lifecycle)) { // first time moving to RFR
			String systemMax = PokUtils.getAttributeValue(rootEntity, "SYSTEMMAX",", ", "", false);
			EntityItem fcItem = m_elist.getEntityGroup("SWFEATURE").getEntityItem(0); // has to exist
			String fcCode = PokUtils.getAttributeValue(fcItem, "FEATURECODE",", ", "", false);
			String swFcCat = PokUtils.getAttributeFlagValue(fcItem, "SWFCCAT");
			addDebug("completeNowR4RProcessing first time moving to RFR FEATURECODE: " + fcCode + " SWFCCAT: " + swFcCat + " SYSTEMMAX: " + systemMax);
//			if (systemMax == null || "".equals(systemMax)) {
				if (fcCode.length() == 4 && SWFCCAT_VALUE_METRIC.equals(swFcCat)) {
					setFlagValue(m_elist.getProfile(), "SYSTEMMAX", "250");
				} else if (fcCode.length() == 6 && SWFCCAT_VALUE_METRIC.equals(swFcCat)) {
					setFlagValue(m_elist.getProfile(), "SYSTEMMAX", "999");
				} else {
					setFlagValue(m_elist.getProfile(), "SYSTEMMAX", "0");
				}
//			} else {
//				addDebug("completeNowR4RProcessing SYSTEMMAX has a value, do nothing");
//			}
		}
		
	}

	/* (non-Javadoc)
	 * update LIFECYCLE value when STATUS is updated
	 * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
	 */
	protected String getLCRFRWFName(){ return "WFLCSWPRODSTRFR";}
	protected String getLCFinalWFName(){ return "WFLCSWPRODSTFINAL";}

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
D.	STATUS changed to Final
180.00		SWPRODSTRUCT		Root Entity							
180.20	R1.0	IF		SWPRODSTRUCT-d	MODEL	ANNDATE	>	"2010-03-01"			
Delete 2011-10-20		180.220		OR			MODEL	PDHDOMAIN	IN	XCC_LIST			
RFR processing
181.16	R1.0	IF			SWPRODSTRUCT	STATUS	=	"Final" (0020)			
181.18	R1.0	IF		SWPRODSTRUCTAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020)			
181.19	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)			
181.20	R1.0	OR			ANNOUNCEMENT	STATUS	=	"Final" (0020)			
181.22	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
181.23	R1.0	END	181.19								

181.24	R1.0	END	181.18								
181.26	R1.0	END	181.16								
Delete 2011-10-20d		181.280	R1.0	IF			SWPRODSTRUCT	PDHDOMAIN	IN	XCC_LIST			
Delete 2011-10-20d		182.000	R1.0	SET			SWPRODSTRUCT				EPIMSABRSTATUS		&ABRWAITODS
Delete 2011-10-20d		182.020	R1.0	END	181.280								

182.10	R1.0	ELSE	180.20								
182.12	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
182.14	R1.0	END	180.20								
183.00	R1.0	END	180.00	SWPRODSTRUCT							

	 *
	 *Note:  SWFEATURE does not flow to WWPRT
	 */
	protected void completeNowFinalProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);

		boolean oldData = this.isOldData(mdlItem, "ANNDATE");
		//boolean inxcclist = domainInRuleList(mdlItem, "XCC_LIST");
		addDebug("nowFinal: "+mdlItem.getKey()+" olddata "+oldData);

		if(doR10processing()){
			//180.00		SWPRODSTRUCT		Root Entity							
			//180.20	R1.0	IF		SWPRODSTRUCT-d	MODEL	ANNDATE	>	"2010-03-01"			
			//Delete 2011-10-20		180.220		OR			MODEL	PDHDOMAIN	IN	XCC_LIST		
			if (!oldData)// || inxcclist)
			{
				//181.16	R1.0	IF			SWPRODSTRUCT	STATUS	=	"Final" (0020)					
				EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

				//Add	181.16	R1.00	IF			SWPRODSTRUCT	STATUS	=	"Final" (0020)			
				Vector availVct= PokUtils.getAllLinkedEntities(rootEntity, "SWPRODSTRUCTAVAIL", "AVAIL");

				availloop:for (int ai=0; ai<availVct.size(); ai++){
					EntityItem avail = (EntityItem)availVct.elementAt(ai);
					//	Add	181.18		IF		SWPRODSTRUCTAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020)	
					if (statusIsFinal(avail)){
//						String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
//						if (availAnntypeFlag==null){
//							availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
//						}
//						addDebug("nowFinal: final "+avail.getKey()+" availanntype "+availAnntypeFlag);
//
//						//181.19	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
//						if(!AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
							//181.22	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED	
							setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
							break availloop;
//						}
//
//						Vector annVct= PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//						for (int i=0; i<annVct.size(); i++){
//							EntityItem annItem = (EntityItem)annVct.elementAt(i);
//							//181.20	R1.0	OR			ANNOUNCEMENT	STATUS	=	"Final" (0020)		
//							if (statusIsFinal(annItem,"ANNSTATUS")){
//								//181.22	R1.00	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
//								setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
//								break availloop;
//							}
//						}// end ann loop
//						annVct.clear();
						//181.23	R1.0	END	181.19
					} // end avail.status=final
					//	181.24		END	181.18	
					//	181.26	R1.0	END	181.16	
				}// end avail loop
				availVct.clear();
				
				//Delete 2011-10-20d	181.280	R1.0	IF			SWPRODSTRUCT	PDHDOMAIN	IN	XCC_LIST	
			//	if (domainInRuleList(rootEntity, "XCC_LIST")){
					//Delete 2011-10-20d 182.000	R1.0	SET			SWPRODSTRUCT				EPIMSABRSTATUS		&ABRWAITODS
			//		setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", getQueuedValue("EPIMSABRSTATUS"));
					//Delete 2011-10-20d	182.020	R1.0	END	181.280	
			//	}
			}else{
				//182.10	R1.0	ELSE	180.20								
				//182.12	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
			}
			//182.14	R1.0	END	180.20								
			//183.00	R1.0	END	180.00	SWPRODSTRUCT
			
			// [Work Item 1658567] RFCABR support HIPO
			// 1) DQABR changes to trigger -
			//  We need to update SWPRODSTRUCTABRSTATUS to allow triggering of RFCABRSTATUS when SWPRODSTRUCT goes to Final  for only MTM= 5313 HPO and 5372 IS5
			if (isHIPOModel(mdlItem)) {
				setFlagValue(m_elist.getProfile(),"RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
			}
			setFlagValue(m_elist.getProfile(), "ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"), mdlItem);
		}
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.DQABRSTATUS#doDQChecking(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
	 *
	 *XXXI.	SWPRODSTRUCT

The NDN of SWPRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
SWFEATURE.FEATURECODE

A.	Checking

Check from ss:   
1.00	SWPRODSTRUCT										
2.00	SWFEATURE		SWPRODSTRUCT-u								
3.00			WITHDRAWANNDATE_T								
4.00			WITHDRAWDATEEFF_T								
5.00			STATUS	=>	SWPRODSTRUCT	DATAQUALITY		E	E	E	{LD: STATUS} can not be higher than the {LD: SWFEATURE} {NDN: SWFEATURE}
6.00			FCTYPE	<>	"Primary FC (100) |""Secondary FC"" (110)"			E	E	E	If RPQ then Error - they are a different PID	{LD: SWFEATURE} {NDN: SWFEATURE} can not be {LD: FCTYPE} {FCTYPE}
7.00	WHEN		FCTYPE	=	"Primary FC (100) |""Secondary FC"" (110)"						
8.00	AVAIL	A	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d								
9.00	WHEN		AVAILTYPE	=	"Planned Availability"						
10.00			CountOf	=>	1		RE*1	RE*1	RE*1	must have at least one "Planned Availability"
11.00			EFFECTIVEDATE								
12.00			COUNTRYLIST								
13.00	END	9									
14.00	AVAIL	B	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d								
15.00	WHEN		AVAILTYPE	=	"Last Order"						
16.00			EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWDATEEFF_T		W	E	E	{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: SWFEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
17.00			EFFECTIVEDATE	<=	MODEL	WTHDRWEFFCTVDATE		W	E	E	{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
18.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST		W	E*1	E*1	{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
19.00	IF			Not Null	SWFEATURE	WITHDRAWDATEEFF_T					
20.00	OR			Not Null	MODEL	WTHDRWEFFCTVDATE					
21.00	THEN		COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	E*1	E*1	{LD: SWFEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} and/or {LD: MODEL} {WTHDRWEFFCTVDATE} is being withdrawn and is available in a Country that does not have a Last Order date
21.20	IF		AVAILANNTYPE	=	"RFA" (RFA)		
22.00	ANNOUNCEMENT		B: + AVAILANNA								
23.00	WHEN		ANNTYPE	=	End Of Life - Withdrawal from mktg (14)						
24.00			ANNDATE	<=	SWFEATURE	WITHDRAWANNDATE_T		W	E	E	{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: SWFEATURE} {NDN: SWFEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
25.00	END	23
25.20	END	21.20
									
26.00	END	15									
27.00	AVAIL		SWPRODSTRUCT-d: MODELAVAIL-d								
28.00	WHEN		AVAILTYPE	=	"Planned Availability"						
29.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
30.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	Yes	W	E	E	{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
31.00	END	28									
32.00	AVAIL		SWPRODSTRUCT-d: MODELAVAIL-d								
33.00	WHEN		AVAILTYPE	=	"Last Order"						
34.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	E	E	{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
35.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST					
36.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		E*1	E*1	E*1	must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
37.00	END	33									
38.00	SWFEATURE		SWPRODSTRUCT-d: MODEL								
39.00	THEN		MODEL: SWPRODSTRUCT-u: SWFEATURE								
40.00			FEATURECODE	Unique				W	E	E	{LD: FEATURECODE} is not unique for this {LD: MODEL}
Add	40.02			BHINVNAME	Unique				E	E	E		{LD: BHINVNAME} is not unique for this {LD: MODEL}
41.00	MODEL		SWPRODSTRUCT-d								
42.00			STATUS	=>	SWPRODSTRUCT	DATAQUALITY		E	E	E	{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL}
43.00			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	E	E	{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: ANNDATE} {ANNDATE}
44.00			WTHDRWEFFCTVDATE	=>	B: AVAIL	EFFECTIVEDATE		W	E	E		{LD: AVAIL} {NDN: AVAIL}  must not be later  than the {LD: MODEL} {NDN:MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
45.00	END	7									
	 */
	protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
	{
		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");

		int checklvl = getCheck_W_E_E(statusFlag); 
		EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0); // has to exist
		EntityItem fcItem = m_elist.getEntityGroup("SWFEATURE").getEntityItem(0); // has to exist

		// get VE2 to go from  MODEL to MODELAVAIL links
		getModelVE(mdlItem);

		//5.00			STATUS	=>	SWPRODSTRUCT	DATAQUALITY		E	E	E	{LD: STATUS} can not be higher than the {LD: SWFEATURE} {NDN: SWFEATURE}
		checkStatusVsDQ(fcItem, "STATUS", rootEntity,CHECKLEVEL_E);

		//42.00			STATUS	=>	SWPRODSTRUCT	DATAQUALITY		E	E	E	{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL}
		checkStatusVsDQ(mdlItem, "STATUS", rootEntity,CHECKLEVEL_E);    	

		//7.00	WHEN		FCTYPE	=	"Primary FC (100) |""Secondary FC"" (110)"						    	
		if (!isRPQ(fcItem)){
			addDebug(fcItem.getKey()+" was NOT an RPQ FCTYPE: "+getAttributeFlagEnabledValue(fcItem, "FCTYPE"));

			//38.00	SWFEATURE		SWPRODSTRUCT-d: MODEL								
			//39.00	THEN		MODEL: SWPRODSTRUCT-u: SWFEATURE								
			//40.00			FEATURECODE	Unique				W	E	E	{LD: FEATURECODE} is not unique for this {LD: MODEL}
			checkAllFeatures(fcItem,mdlItem,checklvl); 								

			// get all AVAIL from SWPRODSTRUCT
			EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
			Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", LASTORDERAVAIL);
			Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", PLANNEDAVAIL);
			addDebug("doDQChecking lastOrderAvailVct "+lastOrderAvailVct.size()+
					" plannedAvailVct "+plannedAvailVct.size());

			checkAvails(rootEntity,fcItem, mdlItem, statusFlag, lastOrderAvailVct,plannedAvailVct);

			// check MODELs AVAILs
			addHeading(3,availGrp.getLongDescription()+" Model Planned Avail Checks:");
			/*
    	27.00	AVAIL		SWPRODSTRUCT-d: MODELAVAIL-d								
    	28.00	WHEN		AVAILTYPE	=	"Planned Availability"						
    	29.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
    	30.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	Yes	W	E	E	{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
    	31.00	END	28									
			 */
			checkPsModelAvail(mdlList, mdlItem, statusFlag,plannedAvailVct,"SWPRODSTRUCTAVAIL");
			/*
    	32.00	AVAIL		SWPRODSTRUCT-d: MODELAVAIL-d								
    	33.00	WHEN		AVAILTYPE	=	"Last Order"						
    	34.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	E	E	{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
    	35.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST					
    	36.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		E*1	E*1	E*1 	must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
    	37.00	END	33										
			 */
			addHeading(3,availGrp.getLongDescription()+" Model Last Order Avail Checks:");
			checkPsModelLastOrderAvail(mdlList,mdlItem, statusFlag,lastOrderAvailVct,plannedAvailVct);

			checklvl = getCheck_W_E_E(statusFlag);
			String annDate = getAttrValueAndCheckLvl(mdlItem, "ANNDATE", checklvl);
			String wdDate = getAttrValueAndCheckLvl(mdlItem, "WTHDRWEFFCTVDATE", checklvl);

			addHeading(3,availGrp.getLongDescription()+" Model Checks:");
			//41.00	MODEL		SWPRODSTRUCT-d								
			//43.00			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	E	E	{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: ANNDATE} {ANNDATE}
			addDebug("doDQChecking check plannedavail and "+mdlItem.getKey()+" ANNDATE "+annDate);
			checkModelDates(mdlItem, plannedAvailVct, "ANNDATE", annDate,checklvl, DATE_LT_EQ);

			//44.00			WTHDRWEFFCTVDATE	=>	B: AVAIL	EFFECTIVEDATE		W	E	E		{LD: AVAIL} {NDN: AVAIL}  must not be later  than the {LD: MODEL} {NDN:MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
			addDebug("doDQChecking check lastorderavail and "+mdlItem.getKey()+" WTHDRWEFFCTVDATE "+wdDate);
			checkModelDates(mdlItem, lastOrderAvailVct, "WTHDRWEFFCTVDATE", wdDate,checklvl, DATE_GR_EQ);

			plannedAvailVct.clear();
			lastOrderAvailVct.clear();
			//45.00	END	7
		}else{
			//6.00			FCTYPE	<>	"Primary FC (100) |""Secondary FC"" (110)"			E	E	E	If RPQ then Error - they are a different PID	{LD: SWFEATURE} {NDN: SWFEATURE} can not be {LD: FCTYPE} {FCTYPE}
			addDebug(fcItem.getKey()+" was an RPQ FCTYPE: "+getAttributeFlagEnabledValue(fcItem, "FCTYPE"));
			args[0] = this.getLD_NDN(fcItem);
			args[1] = this.getLD_Value(fcItem,"FCTYPE");
			createMessage(CHECKLEVEL_E,"FCTYPE_ERR",args);
		}

		if(mdlList!=null){
			mdlList.dereference();
		}
	}

	/********************************
	 * Check SWPRODSTRUCT planned and lastorder avails
	 * @param swfcItem
	 * @param mdlItem
	 * @param statusFlag
	 * @param lastOrderAvailVct
	 * @param plannedAvailVct
	 * @throws java.sql.SQLException
	 * @throws MiddlewareException
    	8.00	AVAIL	A	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d								
    	9.00	WHEN		AVAILTYPE	=	"Planned Availability"						
    	10.00			CountOf	=>	1		RE*1	RE*1	RE*1	must have at least one "Planned Availability"
    	11.00			EFFECTIVEDATE								
    	12.00			COUNTRYLIST								
    	13.00	END	9									
    	14.00	AVAIL	B	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d								
    	15.00	WHEN		AVAILTYPE	=	"Last Order"						
    	16.00			EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWDATEEFF_T		W	E	E	{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: SWFEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
    	17.00			EFFECTIVEDATE	<=	MODEL	WTHDRWEFFCTVDATE		W	E	E	{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
    	18.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST		W	E*1	E*1	{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
    	19.00	IF			Not Null	SWFEATURE	WITHDRAWDATEEFF_T					
    	20.00	OR			Not Null	MODEL	WTHDRWEFFCTVDATE					
    	21.00	THEN		COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	E*1	E*1	{LD: SWFEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} and/or {LD: MODEL} {WTHDRWEFFCTVDATE} is being withdrawn and is available in a Country that does not have a Last Order date
    	21.20	IF		AVAILANNTYPE	=	"RFA" (RFA)
    	22.00	ANNOUNCEMENT		B: + AVAILANNA								
    	23.00	WHEN		ANNTYPE	=	End Of Life - Withdrawal from mktg (14)						
    	24.00			ANNDATE	<=	SWFEATURE	WITHDRAWANNDATE_T		W	E	E	{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: SWFEATURE} {NDN: SWFEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
    	25.00	END	23	
    	25.20	END	21.20								
    	26.00	END	15	
	 */
	private void checkAvails(EntityItem psItem,EntityItem swfcItem, EntityItem mdlItem, String statusFlag,
			Vector lastOrderAvailVct,Vector plannedAvailVct) 
	throws java.sql.SQLException, MiddlewareException
	{
		// these may be null so dont check level here
		//EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWDATEEFF_T	
		String wdEffDate = PokUtils.getAttributeValue(swfcItem, "WITHDRAWDATEEFF_T", "", "", false);
		//EFFECTIVEDATE	<=	MODEL	WTHDRWEFFCTVDATE
		//String wdDate = PokUtils.getAttributeValue(mdlItem, "WITHDRAWDATE", "", "", false);
		String mdlWdEffDate = PokUtils.getAttributeValue(mdlItem, "WTHDRWEFFCTVDATE", "", "", false);

		addDebug("checkAvails "+swfcItem.getKey()+" WITHDRAWDATEEFF_T: "+wdEffDate+" "+mdlItem.getKey()+
				" WTHDRWEFFCTVDATE: "+mdlWdEffDate);

		ArrayList plannedAvailCtry = getCountriesAsList(plannedAvailVct, getCheck_W_W_E(statusFlag));
		ArrayList lastOrderAvailCtry = getCountriesAsList(lastOrderAvailVct, getCheck_W_W_E(statusFlag));

		addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Planned Avail Checks:");

		addDebug("checkAvails all plannedavail countries "+plannedAvailCtry+" lastOrderAvailCtry "+lastOrderAvailCtry);
		//8.00	AVAIL	A	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d								
		//9.00	WHEN		AVAILTYPE	=	"Planned Availability"						
		//10.00			CountOf	=>	1			RE*1	RE*1	RE*1	must have at least one "Planned Availability"
		checkPlannedAvailsExist(plannedAvailVct, getCheckLevel(CHECKLEVEL_RE,mdlItem,"ANNDATE"));
//		20121030 Add	12.20	WHEN		"Final" (FINAL)	=	SWPRODSTRUCT	DATAQUALITY						
//		20121030 Add	12.22	IF		STATUS	=	"Ready for Review" (0040)							
//		20121030 Add	12.24	OR		STATUS	=	"Final" (0020)							
//		20121030 Add	12.26			CountOf	=>	1					RE*1		must have at least one "Planned Availability" that is either "Ready for Review" or "Final" in order to be "Final"
//		20121030 Add	12.28	END	12.20										
        checkPlannedAvailsStatus(plannedAvailVct, psItem, getCheckLevel(CHECKLEVEL_RE,mdlItem,"ANNDATE"));
		//13.00	END	9

		int checklvl = getCheck_W_E_E(statusFlag);

		addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Last Order Avail Checks:");
		//14.00	AVAIL	B	SWPRODSTRUCT-u: SWPRODSTRUCTAVAIL-d								
		//15.00	WHEN		AVAILTYPE	=	"Last Order"						
		for (int i=0; i<lastOrderAvailVct.size(); i++){
			EntityItem avail = (EntityItem)lastOrderAvailVct.elementAt(i);
			if (wdEffDate.length()>0){						
				//16.00			EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWDATEEFF_T		W	E	E	{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: SWFEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
				checkCanNotBeLater(avail, "EFFECTIVEDATE", swfcItem, "WITHDRAWDATEEFF_T", checklvl);
			}

			if (mdlWdEffDate.length()>0){						
				//17.00			EFFECTIVEDATE	<=	MODEL	WTHDRWEFFCTVDATE		W	E	E	{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
				checkCanNotBeLater(avail, "EFFECTIVEDATE", mdlItem, "WTHDRWEFFCTVDATE", checklvl);
			}
			//18.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST		W	E*1	E*1	{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
			checkPlannedAvailForCtryExists(avail, plannedAvailCtry, getCheckLevel(checklvl,mdlItem,"ANNDATE"));
		}// end lastorder loop			

		// WHEN		AVAILTYPE	=	"Last Order"
		//19.00	IF			Not Null	SWFEATURE	WITHDRAWDATEEFF_T					
		//20.00	OR			Not Null	MODEL	WTHDRWEFFCTVDATE					
		//21.00	THEN		B:AVAIL.COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	E*1	E*1	{LD: SWFEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} and/or {LD: MODEL} {WITHDRAWDATE} is being withdrawn and is available in a Country that does not have a Last Order date
		if (lastOrderAvailVct.size()>0 && (wdEffDate.length()>0 || mdlWdEffDate.length()>0)){
			if(!lastOrderAvailCtry.containsAll(plannedAvailCtry)){
				//{LD: SWFEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} and/or {LD: MODEL} {WTHDRWEFFCTVDATE} is being withdrawn and is available in a Country that does not have a Last Order date
				//LASTORDER_ERR = {0} {1} and/or {2} {3} is being withdrawn and is available in a Country that does not have a Last Order date					
				args[0]=swfcItem.getEntityGroup().getLongDescription();
				args[1] = getLD_Value(swfcItem, "WITHDRAWDATEEFF_T");
				args[2] = mdlItem.getEntityGroup().getLongDescription();
				args[3]=getLD_Value(mdlItem, "WTHDRWEFFCTVDATE");
				createMessage(getCheckLevel(checklvl,mdlItem,"ANNDATE"),"LASTORDER_ERR",args);
			}
		}

		//22.00	ANNOUNCEMENT		B: + AVAILANNA								
		if (lastOrderAvailVct.size()>0){
			Vector tmpLOVct = new Vector(lastOrderAvailVct);

			//21.20	IF AVAILANNTYPE	=	"RFA" (RFA)		
			removeNonRFAAVAIL(tmpLOVct);				// remove any that are not AVAILANNTYPE=RFA 

			Vector annVct = PokUtils.getAllLinkedEntities(tmpLOVct, "AVAILANNA", "ANNOUNCEMENT");
			addDebug("checkAvails annVct "+annVct.size());
			//23.00	WHEN		ANNTYPE	=	End Of Life - Withdrawal from mktg (14)						
			annVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNTYPE", ANNTYPE_EOL);
			addDebug("checkAvails EOL annVct "+annVct.size());
			for (int i=0; i<annVct.size(); i++){
				EntityItem annItem = (EntityItem)annVct.elementAt(i);
				//24.00			ANNDATE	<=	SWFEATURE	WITHDRAWANNDATE_T		W	E	E	{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: SWFEATURE} {NDN: SWFEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
				checkCanNotBeLater(annItem, "ANNDATE", swfcItem, "WITHDRAWANNDATE_T", getCheck_W_E_E(statusFlag));
			}
			annVct.clear();
			tmpLOVct.clear();
		}
		//25.00	END	23									
		//25.20	END	21.20
		//26.00	END	15	

		plannedAvailCtry.clear();
		lastOrderAvailCtry.clear();
	}

	/**********************************
	 * Must have MODELAVAIL in second VE because extracts wont go from SVCPRODSTRUCT thru MODEL to AVAIL
	 */
	private void getModelVE(EntityItem modelEntity) throws Exception
	{
		String VeName = "DQVEMODELAVAIL";

		mdlList = m_db.getEntityList(m_elist.getProfile(),
				new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
				new EntityItem[] { new EntityItem(null, m_elist.getProfile(), modelEntity.getEntityType(), modelEntity.getEntityID()) });
		addDebug("getModelVE:: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));
	}

	/**********************************
	 *
	 *   38.00	SWFEATURE		SWPRODSTRUCT-d: MODEL								
    	39.00	THEN		MODEL: SWPRODSTRUCT-u: SWFEATURE								
    	40.00			FEATURECODE	Unique				W	E	E	{LD: FEATURECODE} is not unique for this {LD: MODEL}
		40.02			BHINVNAME	Unique				E	E	E	{LD: BHINVNAME} is not unique for this {LD: MODEL}

Wendy   i think u can pull things tied to a feature when prodstruct is root..
bnair@us.ibm.c...   right, u can
Wendy   but seems like u cant pull things tied to a model when prodstruct is root
bnair@us.ibm.c...   right, because the entry which seeds the trsnavigate always says root is 'UP' when the root entity is a relator
bnair@us.ibm.c...   so thats a limitation with gbl8000
	 */
	private void checkAllFeatures(EntityItem origfcItem,EntityItem origmdlItem, int checklvl) throws Exception
	{
		Profile profile = m_elist.getProfile();
		String VeName = "DQVESWPRODSTRUCT2";

		// entityitem passed in is adjusted in extract, we don't want that so create new one
		EntityList mdlList = m_db.getEntityList(profile,
				new ExtractActionItem(null, m_db, profile, VeName),
				new EntityItem[] { new EntityItem(null, profile, "MODEL", origmdlItem.getEntityID()) });
		addDebug("checkAllFeatures: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));

		EntityItem mdlItem = mdlList.getParentEntityGroup().getEntityItem(0);
		String origbhinvname = PokUtils.getAttributeValue(origfcItem, "BHINVNAME",", ", "NULL", false);
		String origfcode = PokUtils.getAttributeValue(origfcItem, "FEATURECODE",", ", "", false);
		addDebug("checkAllFeatures: " +origfcItem.getKey()+" origbhinvname "+origbhinvname+" origfcode "+origfcode);

		Vector fcVct = new Vector();
		Vector bhinvVct = new Vector();

		for (int i=0; i<mdlItem.getUpLinkCount(); i++){ // look at prodstructs for this model
			EntityItem psItem = (EntityItem)mdlItem.getUpLink(i);
			if (!psItem.getEntityType().equals("SWPRODSTRUCT")){
				addDebug("checkAllFeatures skipping uplink "+psItem.getKey());
				continue;
			}
			for (int f=0; f<psItem.getUpLinkCount(); f++){ // look at features for this ps
				EntityItem featItem = (EntityItem)psItem.getUpLink(f);
				// look at all swfeatures for this model thru each swprodstruct
				// a value is required for BHINVNAME so multiple nulls are not unique
				String bhinvname = PokUtils.getAttributeValue(featItem, "BHINVNAME",", ", "NULL", false);
				String fcode = PokUtils.getAttributeValue(featItem, "FEATURECODE",", ", "", false);
				addDebug("checkAllFeatures checking "+psItem.getKey()+" - "+featItem.getKey()+" fcode: "+
						fcode+" bhinvname: "+bhinvname);
				if (fcVct.contains(fcode)){
					if(origfcode.equals(fcode)){
						//40			FEATURECODE	Unique				W	E	E		
						//{LD: FEATURECODE} is not unique for this {LD: MODEL}
						//NOT_UNIQUE_ERR = {0} is not unique in this {1}
						String info = "";
						if (psItem.getEntityID()!=this.getEntityID()){
							info = this.getLD_NDN(psItem)+" ";
						}

						args[0] = info+PokUtils.getAttributeDescription(featItem.getEntityGroup(), "FEATURECODE", "FEATURECODE");
						args[1] = mdlItem.getEntityGroup().getLongDescription();
						createMessage(checklvl,"NOT_UNIQUE_ERR",args);
					}else{
						addDebug("checkAllFeatures fcode "+fcode+" is not unique but not with this swfeature");
					}
				}else{
					fcVct.add(fcode);
				}

				//40.02			BHINVNAME	Unique				E	E	E		{LD: BHINVNAME} is not unique for this {LD: MODEL}
				if (bhinvVct.contains(bhinvname)){
					if(origbhinvname.equals(bhinvname)){
						//NOT_UNIQUE_ERR = {0} is not unique in this {1}
						String info = "";
						if (psItem.getEntityID()!=this.getEntityID()){
							info = this.getLD_NDN(psItem)+" ";
						}

						args[0] = info+ PokUtils.getAttributeDescription(featItem.getEntityGroup(), "BHINVNAME", "BHINVNAME");
						args[1] = mdlItem.getEntityGroup().getLongDescription();
						createMessage(CHECKLEVEL_E,"NOT_UNIQUE_ERR",args);
					}else{
						addDebug("checkAllFeatures bhinvname "+bhinvname+" is not unique but not with this swfeature");
					}
				}else{
					bhinvVct.add(bhinvname);
				}
			} // end prodstruct uplinks
		} // end model uplinks

		fcVct.clear();
		bhinvVct.clear();

		mdlList.dereference();
	}

	/***********************************************
	 *  Get ABR description
	 *
	 *@return java.lang.String
	 */
	public String getDescription()
	{
		String desc =  "SWPRODSTRUCT ABR";
		return desc;
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
}
