//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2009,2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

import com.ibm.transform.oim.eacm.util.*;

import java.sql.SQLException;
import java.util.*;

/**********************************************************************************
 * MODELCVTABRSTATUS class
 *
 *BH FS ABR Data Qualtity Checks 20110616.xls
 *changed RE*2 to RE for chk 8.00
 *
 *BH FS ABR Data Quality 20110322.doc
 *MODELCONVERTAVAIL checks 10.02, 15.02, 21.02
 *updated VE DQVEMODELCONVERT
 *
 *BH FS ABR Data Qualtity Checks 20110318.xls tomodel avail checks
 *
 *From "SG FS ABR Data Quality 20101026.doc"
 *sets chgs
 *
 * From "SG FS ABR Data Quality 20100914.doc"
 *
 *need workflow actions - WFLCMDLCONRFR and WFLCMDLCONFINAL
 *needs ADSABRSTATUS and LIFECYCLE attrs
 *
XXI.	MODELCONVERT

This is also referred to as 'Model Conversion'.

A.	Checking

Model Conversions (MODELCONVERT) must have Availability (AVAIL) which is checked against the 
planning dates on MODELCONVERT.

The Announcement dates for a Model Conversion are not checked.

The Availability dates for the Model being converted �TO� are checked to ensure that it is 
Available (AVAIL). The dates for the �TO� model are found as described in the next paragraph.

Key 23 (and 29) find the �TO� Model (MODEL) Availability (AVAIL) of interest by:
1.	Key 23 � SEARCH MODEL
Use a SEARCH action to find a MODEL using the two attributes shown from MODELCONVERT.
2.	Key 24 � traverse down the relator MODELAVAIL
 *
MDLCNTABRSTATTUS_class=COM.ibm.eannounce.abr.sg.bh.MODELCVTABRSTATUS
MDLCNTABRSTATTUS_enabled=true
MDLCNTABRSTATTUS_idler_class=A
MDLCNTABRSTATTUS_keepfile=true
MDLCNTABRSTATTUS_read_only=true
MDLCNTABRSTATTUS_report_type=DGTYPE01
MDLCNTABRSTATTUS_vename=DQVEMODELCONVERT
MDLCNTABRSTATTUS_CAT1=RPTCLASS.MDLCNTABRSTATTUS
MDLCNTABRSTATTUS_CAT2=
MDLCNTABRSTATTUS_CAT3=RPTSTATUS
#MDLCNTABRSTATTUS_domains=0010,0020,0030,0040,0050,0150,0160,0170,0190,0200,0210,0220,0230,0240,0250,0260,0270,0280,0290,0300,0310,0320,0330,0340,0350,0360,0370,0520,530,540,SG,550,PDIV49,PDIV71,PPWRS,PRSSS,PSANMOHE,PSANMOLE,PSTGV,PSWIDLU,PSSDS,PSQS,SVC
MDLCNTABRSTATTUS_ADSABRSTATUS_queuedValue=ABRXMLFEED
MDLCNTABRSTATTUS_ADSABRSTATUS_RFRqueuedValue=ABRXMLFEED
 */
//MODELCVTABRSTATUS.java,v
//Revision 1.12  2011/03/24 17:38:09  wendy
//TOMODEL EOM AVAIL checks added
//
//Revision 1.8  2010/01/21 14:38:49  wendy
//update cmts

//Revision 1.6  2009/12/21 12:55:33  wendy
//Updated to handle old data

//Revision 1.5  2009/11/04 15:08:07  wendy
//BH Configurable Services - spec chgs

//Revision 1.4  2009/08/17 15:35:46  wendy
//Added headings

//Revision 1.3  2009/08/15 01:41:50  wendy
//SR10, 11, 12, 15, 17 BH updates phase 4

//Revision 1.2  2009/08/06 22:24:31  wendy
//SR10, 11, 12, 15, 17 BH updates phase 3

public class MODELCVTABRSTATUS extends DQABRSTATUS
{
	private static final String MODEL_SRCHACTION_NAME = "SRDMODEL06"; // SRDMODEL06 - domain restricted
    private static final String CHECK_ANNDATE = "2012-01-01";
	private static final String AVAIL_CHK_DATE = "2011-07-01";
    private static final String COFGRP_Base= "150";//COFGRP �Base� (150) 
    
	/**********************************
	 * always needed
	 */
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}
	/*
from sets ss:
148.000		MODELCONVERT		Root Entity				
148.020	R1.0	IF			MODELCONVERT	ANNDATE	>	"2012-01-01"
149.020	R1.0	IF			MODELCONVERT	STATUS	=	"Ready for Review" (0040)			
149.040	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
149.060	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR	
149.080	R1.0	END	149.020								
149.100	R1.0	IF			MODELCONVERT	STATUS	=	"Final" (0020)			
149.120	R1.0	SET			MODELCONVERT				ADSABRSTATUS		&ADSFEED
149.140	R1.0	END	149.100								
149.160	R1.0	ELSE	148.020								
149.180	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
149.200	R1.0	END	148.020								
150.000	R1.0	END	148.000	MODELCONVERT							
	 */
	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq)
	 * C.	Status changed to Ready for Review
148.020	R1.0	IF			MODELCONVERT	ANNDATE	>	"2012-01-01"
149.020	R1.0	IF			MODELCONVERT	STATUS	=	"Ready for Review" (0040)			
149.040	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
149.060	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR	
149.080	R1.0	END	149.020								
...								
149.160	R1.0	ELSE	148.020								
149.180	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
149.200	R1.0	END	148.020								
150.000	R1.0	END	148.000	MODELCONVERT							

	 */
	protected void completeNowR4RProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		if(doR10processing()){
			EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);
			//148.02	R1.0	IF			MODELCONVERT	ANNDATE	>	"2012-01-01"	
			String annDate = PokUtils.getAttributeValue(rootItem, "ANNDATE", "", "", false);
			addDebug("nowRFR: "+rootItem.getKey()+" annDate "+annDate);
			if(annDate.length()>0 && annDate.compareTo(CHECK_ANNDATE)>0){
				addDebug("nowRFR: "+rootItem.getKey()+" is after "+CHECK_ANNDATE);
				//149.02	R1.0	IF			MODELCONVERT	STATUS	=	"Ready for Review" (0040)			
				//149.04	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
				//149.06	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR	
				//149.08	R1.0	END	149.02	
				doRFR_ADSXML(rootItem); 
			}else{
				//149.16	R1.0	ELSE	148.02								
				//149.18	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
				//149.20	R1.0	END	148.02		
			}			
			//150.00	R1.0	END	148.00	MODELCONVERT							
		}
	}

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
	 *C. STATUS changed to Final
	 *
148.020	R1.0	IF			MODELCONVERT	ANNDATE	>	"2012-01-01"			
...								
149.100	R1.0	IF			MODELCONVERT	STATUS	=	"Final" (0020)			
149.120	R1.0	SET			MODELCONVERT				ADSABRSTATUS		&ADSFEED
149.140	R1.0	END	149.100								
149.160	R1.0	ELSE	148.020								
149.180	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
149.200	R1.0	END	148.020	
	 */
	protected void completeNowFinalProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		EntityItem rootItem = m_elist.getParentEntityGroup().getEntityItem(0);
		addDebug(rootItem.getKey()+" status now final");
		if(doR10processing()){
			/*this is a noop
			String annDate = PokUtils.getAttributeValue(rootItem, "ANNDATE", "", "", false);
			addDebug("nowFinal: "+rootItem.getKey()+" annDate "+annDate);
			if(annDate.length()>0 && annDate.compareTo(CHECK_ANNDATE)>0){
				addDebug("nowFinal: "+rootItem.getKey()+" is after "+CHECK_ANNDATE);		

				//149.10	R1.0	IF			MODELCONVERT	STATUS	=	"Final" (0020)			
				//149.12	R1.0	SET			MODELCONVERT				ADSABRSTATUS		&ADSFEED
				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
				//149.14	R1.0	END	149.10		
			}else{
				//149.16	R1.0	ELSE	148.02								
				//149.18	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
				//149.20	R1.0	END	148.02					
			}
			//150.00	R1.0	END	148.00	MODELCONVERT	
			*/
			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
			//149.100	IF			MODELCONVERT	STATUS	=	"Final" (0020)
			//149.120	SET			MODELCONVERT				RFCABRSTATUS		&OIMSFEED
			//149.140	END	149.100
			//149.160	ELSE	148.020
			//149.180	SET			MODELCONVERT				RFCABRSTATUS		&OIMSFEED
			setFlagValue(m_elist.getProfile(),"RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
		}
	}

	/* (non-Javadoc)
	 * update LIFECYCLE value when STATUS is updated
	 * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
	 */
	protected String getLCRFRWFName(){ return "WFLCMDLCONRFR";} 
	protected String getLCFinalWFName(){ return "WFLCMDLCONFINAL";}

	/**********************************
	 * Note the ABR is only called when
	 * DATAQUALITY transitions from 'Draft to Ready for Review',
	 *   'Change Request to Ready for Review' and from 'Ready for Review to Final'
	 *   
checks from ss:
1.00	MODELCONVERT											
2.00			ANNDATE									
3.00			GENAVAILDATE									
4.00			WITHDRAWDATE	=>	MODELCONVERT	ANNDATE		W	W	E		{LD: WITHDRAWDATE} {WITHDRAWDATE} must not be earlier than {LD: ANNDATE} {ANNDATE}
5.00			WTHDRWEFFCTVDATE	=>	MODELCONVERT	WITHDRAWDATE		W	W	E		{LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATEE} must not be earlier than {LD: WITHDRAWDATE} {WITHDRAWDATE}
6.00	AVAIL	A	MODELCONVERTAVAIL-d								AVAIL	
7.00	WHEN		AVAILTYPE	=	"Planned Availability"							
8.00			CountOf	=>	1			RE	RE	RE		must have at least one "Planned Availability"
9.00			EFFECTIVEDATE	=>	MODELCONVERT	GENAVAILDATE		W				{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
10.00			COUNTRYLIST									
10.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
10.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE						
10.06	ANNOUNCEMENT		AVAILANNA-d									
10.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
10.10			ANNDATE	=>	MODELCONVERT	ANNDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be earlier than the {LD: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
10.12	END	10.02										
11.00	END	7.00										
12.00	AVAIL		MODELCONVERTAVAIL-d								AVAIL	
13.00	WHEN		AVAILTYPE	=	"First Order"							
14.00			EFFECTIVEDATE	=>	MODELCONVERT	ANNDATE		W				{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
15.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
15.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
15.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE						
15.06	ANNOUNCEMENT		AVAILANNA-d									
15.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
15.10			ANNDATE	<=	MODELCONVERT	GENAVAILDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
15.12	END	15.02										
16.00	END	13.00										
17.00	AVAIL	B	MODELCONVERTAVAIL-d								AVAIL	
18.00	WHEN		AVAILTYPE	=	"Last Order"							
19.00			EFFECTIVEDATE	<=	MODELCONVERT	WTHDRWEFFCTVDATE		W				{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: MODELCONVERT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
20.00			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes		W	E*2		{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
21.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E*2		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
21.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
21.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE						
21.06	ANNOUNCEMENT		AVAILANNA-d									
21.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
21.10			ANNDATE	<=	MODELCONVERT	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD: MODELCONVERT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
21.12	END	21.02										
22.00	END	18.00										
								
23.00	AVAIL		"SEARCH MODEL where MACHTYPEATR = TOMACHTYPE & MODELATR = TOMODEL &COFGRP = ""Base"" (150)"								Finds the "TO" MODEL	
24.00	NEXT		MODELAVAIL-d								"TO" MODEL AVAIL	
25.00	WHEN		AVAILTYPE	=	"Planned Availability"							
26.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL}  must be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
27.00			COUNTRYLIST	"Contains aggregte E"	A: AVAIL	COUNTRYLIST		W	W	E*2		has a "Planned Availability" for a Country that is not applicable for the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL}
28.00	END	25.00										
29.00	AVAIL		"SEARCH MODEL where MACHTYPEATR = TOMACHTYPE & MODELATR = TOMODEL &COFGRP = ""Base"" (150)"								Finds the "TO" MODEL	
30.00	NEXT		MODELAVAIL-d								"TO" MODEL AVAIL	
31.00	WHEN		AVAILTYPE	=	"Last Order"							
32.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	W	E		the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL}  must be later than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: B:AVAIL} "Last Order" date
33.00			COUNTRYLIST	"Contains aggregte E"	B: AVAIL	COUNTRYLIST		W	W	E*2		the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL} has a withdrawal and the {LD:MODELCONVERT} must have a "Last Order" for the corresponding countries
34.00	END	31.00										
35.00	WHEN		AVAILTYPE	=	"End of Marketing" (200)							
36.00			EFFECTIVEDATE	<=	MODELCONVERT	WITHDRAWDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: MODELCONVERT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
37.00	END	35.00										
38.00	END	29.00										

	 */
	protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
	{
		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");

		int checklvl = getCheck_W_W_E(statusFlag); 
		//4.00			WITHDRAWDATE	=>	MODELCONVERT	ANNDATE		W	W	E		
		//{LD: WITHDRAWDATE} {WITHDRAWDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
		checkCanNotBeEarlier(rootEntity, "WITHDRAWDATE", "ANNDATE", checklvl);

		//5.00			WTHDRWEFFCTVDATE	=>	MODELCONVERT	WITHDRAWDATE		W	W	E		
		//{LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATEE} can not be earlier than {LD: WITHDRAWDATE} {WITHDRAWDATE}	
		checkCanNotBeEarlier(rootEntity, "WTHDRWEFFCTVDATE", "WITHDRAWDATE", checklvl);

		//get all AVAILS
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");

		Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", LASTORDERAVAIL);
		Vector mesLastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", MESLASTORDERAVAIL);
		
		Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", PLANNEDAVAIL);
		Vector mesPlannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", MESPLANNEDAVAIL);

		Vector firstOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", FIRSTORDERAVAIL);
		
		addDebug("doDQChecking lastOrderAvailVct "+lastOrderAvailVct.size()+
				" plannedAvailVct "+plannedAvailVct.size());

		Hashtable plAvailCtryTbl = getAvailByCountry(plannedAvailVct,checklvl);
		addDebug("doDQChecking plAvailCtryTbl: "+plAvailCtryTbl.keySet());
		Hashtable mesPlAvailCtryTbl = getAvailByCountry(mesPlannedAvailVct,checklvl);
		addDebug("doDQChecking mesPlAvailCtryTbl: "+mesPlAvailCtryTbl.keySet());
//		Hashtable loAvailCtryTbl = getAvailByCountry(lastOrderAvailVct,checklvl);
//		addDebug("doDQChecking loAvailCtryTbl: "+loAvailCtryTbl.keySet());
		
		addHeading(3,availGrp.getLongDescription()+" Planned Avail Checks:");
		checkMdlCvtMesPlaAndPlaAvail(rootEntity, plannedAvailVct, PLANNEDAVAIL, statusFlag);
		
		addHeading(3,availGrp.getLongDescription()+" MES Planned Avail Checks:");
		checkMdlCvtMesPlaAndPlaAvail(rootEntity, mesPlannedAvailVct, MESPLANNEDAVAIL, statusFlag);
		
		addHeading(3,availGrp.getLongDescription()+" First Order Avail Checks:");
		checkMdlCvtFirstOrderAvail(rootEntity, firstOrderAvailVct, plannedAvailVct, mesPlannedAvailVct, statusFlag);
		
		addHeading(3,availGrp.getLongDescription()+" Last Order Avail Checks:");
		checkMdlCvtMesLoAndLoAvail(rootEntity, lastOrderAvailVct, LASTORDERAVAIL, plAvailCtryTbl, statusFlag);
		
		addHeading(3,availGrp.getLongDescription()+" MES Last Order Avail Checks:");
		checkMdlCvtMesLoAndLoAvail(rootEntity, mesLastOrderAvailVct, MESLASTORDERAVAIL, plAvailCtryTbl, statusFlag);
		checkMdlCvtMesLoAndLoAvail(rootEntity, mesLastOrderAvailVct, MESLASTORDERAVAIL, mesPlAvailCtryTbl, statusFlag);
		
//		checkAvails(rootEntity, plannedAvailVct, plAvailCtryTbl,
//				lastOrderAvailVct, statusFlag);

		checkModelAvails(rootEntity, plannedAvailVct, mesPlannedAvailVct, lastOrderAvailVct, mesLastOrderAvailVct, checklvl);

		plannedAvailVct.clear();
		lastOrderAvailVct.clear(); 
		plAvailCtryTbl.clear();
//		loAvailCtryTbl.clear();
	}

	/*******************************
	 * Find the TO Model and check avails
	 * All MODELCONVERT plannedavails must be a subset of MODEL plannedavails
	 * the MODELplannedavail effdate must be must be earlier than  MODELCONVERT plannedavail for the same ctry 
	 * All MODELCONVERT lastorderavails must be a subset of MODEL lastorderavails
	 * the MODELlastorderavail  effdate must be must be later than MODELCONVERT lastorderavail for the same ctry 
	 * @param rootEntity
	 * @param plannedAvailVct
	 * @param lastOrderAvailVct
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * 
23.00	AVAIL		"SEARCH MODEL where MACHTYPEATR = TOMACHTYPE & MODELATR = TOMODEL &COFGRP = ""Base"" (150)"								Finds the "TO" MODEL	
24.00	NEXT		MODELAVAIL-d								"TO" MODEL AVAIL	
25.00	WHEN		AVAILTYPE	=	"Planned Availability"							
26.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL}  must be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
27.00			COUNTRYLIST	"Contains aggregte E"	A: AVAIL	COUNTRYLIST		W	W	E*2		has a "Planned Availability" for a Country that is not applicable for the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL}
28.00	END	25.00										
29.00	AVAIL		"SEARCH MODEL where MACHTYPEATR = TOMACHTYPE & MODELATR = TOMODEL &COFGRP = ""Base"" (150)"								Finds the "TO" MODEL	
30.00	NEXT		MODELAVAIL-d								"TO" MODEL AVAIL	
31.00	WHEN		AVAILTYPE	=	"Last Order"							
32.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	W	E		the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL}  must be later than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: B:AVAIL} "Last Order" date
33.00			COUNTRYLIST	"Contains aggregte E"	B: AVAIL	COUNTRYLIST		W	W	E*2		the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL} has a withdrawal and the {LD:MODELCONVERT} must have a "Last Order" for the corresponding countries
34.00	END	31.00	
35.00	WHEN		AVAILTYPE	=	"End of Marketing" (200)							
36.00			EFFECTIVEDATE	<=	MODELCONVERT	WITHDRAWDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: MODELCONVERT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
37.00	END	35.00										
38.00	END	29.00										

	 */
	private void checkModelAvails(EntityItem rootEntity, Vector plannedAvailVct, Vector mesPlannedAvailVct , 
			Vector lastOrderAvailVct, Vector mesLastOrderAvailVct, int checklvl) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		String machtype = PokUtils.getAttributeValue(rootEntity, "TOMACHTYPE","", "", false);
		String modelatr = PokUtils.getAttributeValue(rootEntity, "TOMODEL","", "", false);

		int oldDataChklvl = getCheckLevel(checklvl,rootEntity,"ANNDATE");
		//23.00	AVAIL		SEARCH MODEL where MACHTYPEATR = TOMACHTYPE & MODELATR = TOMODEL
		//29.00	AVAIL		SEARCH MODEL where MACHTYPEATR = TOMACHTYPE & MODELATR = TOMODEL
		EntityItem mdlItem = searchForModel(machtype, modelatr);
		addDebug("checkModelAvails machtype: "+machtype+" modelatr "+modelatr);
		if (mdlItem!=null){
			EntityList mdlList = getModelVE(mdlItem);

			// get avails from mdl extract
			EntityGroup mdlAvailGrp = mdlList.getEntityGroup("AVAIL");
			if (mdlAvailGrp ==null){
				throw new MiddlewareException("AVAIL is missing from extract for "+mdlList.getParentActionItem().getActionItemKey());
			}
			//24.00	NEXT		MODELAVAIL-d								"TO" MODEL AVAIL	
			//25.00	WHEN		AVAILTYPE	=	"Planned Availability"							
			//26.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		
			//the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL}  must be earlier than the {LD: MODELCONVERT} "Planned Availability"
			//27.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	W	E*2		
			//has a "Planned Availability" for a Country that is not applicable for the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL}
			//28.00	END	25.00	    		
			Vector mdlPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", PLANNEDAVAIL);
			addDebug("checkModelAvails "+mdlItem.getKey()+" mdlPlaAvailVct "+mdlPlaAvailVct.size());

			addHeading(3,mdlAvailGrp.getLongDescription()+" Model Planned Avail Checks:");
			if (plannedAvailVct.size()>0){
				Hashtable mdlPlaAvailCtryTbl = getAvailByCountry(mdlPlaAvailVct,checklvl);
				addDebug("checkModelAvails  mdlPlaAvailCtryTbl "+mdlPlaAvailCtryTbl.keySet());
				for (int i=0; i<plannedAvailVct.size(); i++){ // these are the MODELCONVERT plannedavail
					EntityItem avail = (EntityItem)plannedAvailVct.elementAt(i); 
					Vector mdlPlaVct = new Vector(); // hold onto model plannedavail for date checks incase same avail for mult ctrys
					// 26.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E
					String missingCtry = checkCtryMismatch(avail, mdlPlaAvailCtryTbl, mdlPlaVct, checklvl);

					String date2 = getAttrValueAndCheckLvl(avail, "EFFECTIVEDATE", checklvl);
					// do the date checks now
					for (int m=0; m<mdlPlaVct.size(); m++){
						EntityItem mdlplaAvail = (EntityItem)mdlPlaVct.elementAt(m);
						//26.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		
						String mdldate1 = getAttrValueAndCheckLvl(mdlplaAvail, "EFFECTIVEDATE", checklvl);
						addDebug("checkModelAvails  model plannedavail: "+
								mdlplaAvail.getKey()+" EFFECTIVEDATE:"+mdldate1+" mdlcvt-plannedavail:"+
								avail.getKey()+" EFFECTIVEDATE:"+date2);
						boolean isok = checkDates(mdldate1, date2, DATE_LT_EQ);	//date1<=date2	
						if (!isok){
							//MDLCVT_PLA_DATE_ERR=the &quot;TO&quot; {0} {1}{2} {3}  must be earlier than the {4} {5}
							//the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL}  must be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
							args[0]=mdlItem.getEntityGroup().getLongDescription();
							args[1]=PokUtils.getAttributeValue(rootEntity, "TOMACHTYPE", "", "", false);
							args[2]=PokUtils.getAttributeValue(rootEntity, "TOMODEL", "", "", false);
							args[3]=getLD_NDN(mdlplaAvail);
							args[4]=rootEntity.getEntityGroup().getLongDescription();
							args[5]=getLD_NDN(avail);
							createMessage(checklvl,"MDLCVT_PLA_DATE_ERR",args);
						}
					}
					mdlPlaVct.clear();
					if (missingCtry.length()>0){
						addDebug("checkModelAvails mdlcvt plannedavail:"+avail.getKey()+
								" COUNTRYLIST had ctry ["+missingCtry+"] that were not in any plannedavail MODELAVAIL");
						//27.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	W	E*2		
						//has a "Planned Availability" for a Country that is not applicable for the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL}
						//MDLCVT_PLANNEDAVAIL_ERR=has a &quot;Planned Availability&quot; for a Country that is not applicable for the &quot;TO&quot; {0} {1}{2}
						args[0]=mdlItem.getEntityGroup().getLongDescription();
						args[1]=PokUtils.getAttributeValue(rootEntity, "TOMACHTYPE", "", "", false);
						args[2]=PokUtils.getAttributeValue(rootEntity, "TOMODEL", "", "", false);
						createMessage(oldDataChklvl,"MDLCVT_PLANNEDAVAIL_ERR",args);
					}
				}
				mdlPlaAvailCtryTbl.clear();
			}
			if (mesPlannedAvailVct.size()>0){
				Hashtable mdlPlaAvailCtryTbl = getAvailByCountry(mdlPlaAvailVct,checklvl);
				addDebug("checkModelAvails  mdlPlaAvailCtryTbl "+mdlPlaAvailCtryTbl.keySet());
				for (int i=0; i<mesPlannedAvailVct.size(); i++){ // these are the MODELCONVERT plannedavail
					EntityItem avail = (EntityItem)mesPlannedAvailVct.elementAt(i); 
					Vector mdlPlaVct = new Vector(); // hold onto model plannedavail for date checks incase same avail for mult ctrys
					// 26.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E
					String missingCtry = checkCtryMismatch(avail, mdlPlaAvailCtryTbl, mdlPlaVct, checklvl);

					String date2 = getAttrValueAndCheckLvl(avail, "EFFECTIVEDATE", checklvl);
					// do the date checks now
					for (int m=0; m<mdlPlaVct.size(); m++){
						EntityItem mdlPlaAvail = (EntityItem)mdlPlaVct.elementAt(m);
						//26.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		
						String mdldate1 = getAttrValueAndCheckLvl(mdlPlaAvail, "EFFECTIVEDATE", checklvl);
						addDebug("checkModelAvails  model plannedavail: "+
								mdlPlaAvail.getKey()+" EFFECTIVEDATE:"+mdldate1+" mdlcvt-mesplannedavail:"+
								avail.getKey()+" EFFECTIVEDATE:"+date2);
						boolean isok = checkDates(mdldate1, date2, DATE_LT_EQ);	//date1<=date2	
						if (!isok){
							//MDLCVT_PLA_DATE_ERR=the &quot;TO&quot; {0} {1}{2} {3}  must be earlier than the {4} {5}
							//the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL}  must be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
							args[0]=mdlItem.getEntityGroup().getLongDescription();
							args[1]=PokUtils.getAttributeValue(rootEntity, "TOMACHTYPE", "", "", false);
							args[2]=PokUtils.getAttributeValue(rootEntity, "TOMODEL", "", "", false);
							args[3]=getLD_NDN(mdlPlaAvail);
							args[4]=rootEntity.getEntityGroup().getLongDescription();
							args[5]=getLD_NDN(avail);
							createMessage(checklvl,"MDLCVT_PLA_DATE_ERR",args);
						}
					}
					mdlPlaVct.clear();
					if (missingCtry.length()>0){
						addDebug("checkModelAvails mdlcvt mesplannedavail:"+avail.getKey()+
								" COUNTRYLIST had ctry ["+missingCtry+"] that were not in any plannedavail MODELAVAIL");
						//27.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	W	E*2		
						//has a "MES Planned Availability" for a Country that is not applicable for the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL}
						//MDLCVT_PLANNEDAVAIL_ERR=has a &quot;Planned Availability&quot; for a Country that is not applicable for the &quot;TO&quot; {0} {1}{2}
						args[0]=mdlItem.getEntityGroup().getLongDescription();
						args[1]=PokUtils.getAttributeValue(rootEntity, "TOMACHTYPE", "", "", false);
						args[2]=PokUtils.getAttributeValue(rootEntity, "TOMODEL", "", "", false);
						createMessage(oldDataChklvl,"MDLCVT_PLANNEDAVAIL_ERR",args);
					}
				}
				mdlPlaAvailCtryTbl.clear();
			}
			mdlPlaAvailVct.clear();
			
			//29.00	AVAIL		SEARCH MODEL where MACHTYPEATR = TOMACHTYPE & MODELATR = TOMODEL								Finds the "TO" MODEL	
			//30.00	NEXT		MODELAVAIL-d								"TO" MODEL AVAIL	
			//31.00	WHEN		AVAILTYPE	=	"Last Order"							
			//32.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	W	E		
			//the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL}  must be later than the {LD: MODELCONVERT} "Last Order" date
			//33.00			COUNTRYLIST	"Contains aggregate E"	B: AVAIL	COUNTRYLIST		W	W	E*2		
			//the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL} has a withdrawal and the {LD:MODELCONVERT} must have a "Last Order" for the corresponding countries
			//34.00	END	31.00	

			Vector mdlLoAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", LASTORDERAVAIL);
			addDebug("checkModelAvails  mdlLoAvailVct "+mdlLoAvailVct.size());
			addHeading(3,mdlAvailGrp.getLongDescription()+" Model Last Order Avail Checks:");

			if (lastOrderAvailVct.size()>0){
				Hashtable mdlLoAvailCtryTbl = getAvailByCountry(mdlLoAvailVct,checklvl);
				addDebug("checkModelAvails  mdlLoAvailCtryTbl "+mdlLoAvailCtryTbl.keySet());
				for (int i=0; i<lastOrderAvailVct.size(); i++){ // these are the MODELCONVERT lastorderavail
					EntityItem avail = (EntityItem)lastOrderAvailVct.elementAt(i); 
					Vector mdlLoVct = new Vector(); // hold onto model lastorderavail for date checks incase same avail for mult ctrys
					//32.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	W	E
					String missingCtry = checkCtryMismatch(avail, mdlLoAvailCtryTbl, mdlLoVct, checklvl);
					String date2 = getAttrValueAndCheckLvl(avail, "EFFECTIVEDATE", checklvl);
					// do the date checks now
					for (int m=0; m<mdlLoVct.size(); m++){
						EntityItem mdlLoAvail = (EntityItem)mdlLoVct.elementAt(m);
						//32.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	W	E		
						String mdldate1 = getAttrValueAndCheckLvl(mdlLoAvail, "EFFECTIVEDATE", checklvl);
						addDebug("checkModelAvails  model lastorderavail: "+
								mdlLoAvail.getKey()+" EFFECTIVEDATE:"+mdldate1+" mdlcvt-lastorderavail:"+
								avail.getKey()+" EFFECTIVEDATE:"+date2);
						boolean isok = checkDates(mdldate1, date2, DATE_GR_EQ);	//date1=>date2	
						if (!isok){
							//MDLCVT_LO_DATE_ERR=the &quot;TO&quot; {0} {1}{2} {3} must be later than the {4} {5} &quot;Last Order&quot; date
							//the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL}  must be later than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: B:AVAIL} "Last Order" date
							args[0]=mdlItem.getEntityGroup().getLongDescription();
							args[1]=PokUtils.getAttributeValue(rootEntity, "TOMACHTYPE", "", "", false);
							args[2]=PokUtils.getAttributeValue(rootEntity, "TOMODEL", "", "", false);
							args[3]=getLD_NDN(mdlLoAvail);
							args[4]=rootEntity.getEntityGroup().getLongDescription();
							args[5]=getLD_NDN(avail);
							createMessage(checklvl,"MDLCVT_LO_DATE_ERR",args);
						}
					}
					mdlLoVct.clear();
					if (missingCtry.length()>0){
						addDebug("checkModelAvails mdlcvt lastorderavail:"+avail.getKey()+
								" COUNTRYLIST had ctry ["+missingCtry+"] that were not in any lastorderavail MODELAVAIL");
						//33.00			COUNTRYLIST	"Contains aggregate E"	B: AVAIL	COUNTRYLIST		W	W	E*2		
						//the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL} has a withdrawal and the {LD:MODELCONVERT} must have a "Last Order" for the corresponding countries
						//MDLCVT_LASTORDERAVAIL_ERR=the &quot;TO&quot; {0} {1}{2} {3} has a withdrawal and the {4} must have a &quot;Last Order&quot; for the corresponding countries
						args[0]=mdlItem.getEntityGroup().getLongDescription();
						args[1]=PokUtils.getAttributeValue(rootEntity, "TOMACHTYPE", "", "", false);
						args[2]=PokUtils.getAttributeValue(rootEntity, "TOMODEL", "", "", false);
						args[3]=getLD_NDN(avail);
						args[4]=rootEntity.getEntityGroup().getLongDescription();
						createMessage(oldDataChklvl,"MDLCVT_LASTORDERAVAIL_ERR",args);
					}
				}
				mdlLoAvailCtryTbl.clear();
			}
			if (mesLastOrderAvailVct.size()>0){
				Hashtable mdlLoAvailCtryTbl = getAvailByCountry(mdlLoAvailVct,checklvl);
				addDebug("checkModelAvails  mdlLoAvailCtryTbl "+mdlLoAvailCtryTbl.keySet());
				for (int i=0; i<mesLastOrderAvailVct.size(); i++){ // these are the MODELCONVERT lastorderavail
					EntityItem avail = (EntityItem)mesLastOrderAvailVct.elementAt(i); 
					Vector mdlLoVct = new Vector(); // hold onto model lastorderavail for date checks incase same avail for mult ctrys
					//32.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	W	E
					String missingCtry = checkCtryMismatch(avail, mdlLoAvailCtryTbl, mdlLoVct, checklvl);
					String date2 = getAttrValueAndCheckLvl(avail, "EFFECTIVEDATE", checklvl);
					// do the date checks now
					for (int m=0; m<mdlLoVct.size(); m++){
						EntityItem mdlLoAvail = (EntityItem)mdlLoVct.elementAt(m);
						//32.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	W	E		
						String mdldate1 = getAttrValueAndCheckLvl(mdlLoAvail, "EFFECTIVEDATE", checklvl);
						addDebug("checkModelAvails  model lastorderavail: "+
								mdlLoAvail.getKey()+" EFFECTIVEDATE:"+mdldate1+" mdlcvt-meslastorderavail:"+
								avail.getKey()+" EFFECTIVEDATE:"+date2);
						boolean isok = checkDates(mdldate1, date2, DATE_GR_EQ);	//date1=>date2	
						if (!isok){
							//MDLCVT_LO_DATE_ERR=the &quot;TO&quot; {0} {1}{2} {3} must be later than the {4} {5} &quot;Last Order&quot; date
							//the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL}  must be later than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: B:AVAIL} "Last Order" date
							args[0]=mdlItem.getEntityGroup().getLongDescription();
							args[1]=PokUtils.getAttributeValue(rootEntity, "TOMACHTYPE", "", "", false);
							args[2]=PokUtils.getAttributeValue(rootEntity, "TOMODEL", "", "", false);
							args[3]=getLD_NDN(mdlLoAvail);
							args[4]=rootEntity.getEntityGroup().getLongDescription();
							args[5]=getLD_NDN(avail);
							createMessage(checklvl,"MDLCVT_LO_DATE_ERR",args);
						}
					}
					mdlLoVct.clear();
					if (missingCtry.length()>0){
						addDebug("checkModelAvails mdlcvt meslastorderavail:"+avail.getKey()+
								" COUNTRYLIST had ctry ["+missingCtry+"] that were not in any lastorderavail MODELAVAIL");
						//33.00			COUNTRYLIST	"Contains aggregate E"	B: AVAIL	COUNTRYLIST		W	W	E*2		
						//the "TO" {LD: MODEL} {TOMACHTYPE}{TOMODEL} {LD: AVAIL} {NDN: AVAIL} has a withdrawal and the {LD:MODELCONVERT} must have a "Last Order" for the corresponding countries
						//MDLCVT_LASTORDERAVAIL_ERR=the &quot;TO&quot; {0} {1}{2} {3} has a withdrawal and the {4} must have a &quot;Last Order&quot; for the corresponding countries
						args[0]=mdlItem.getEntityGroup().getLongDescription();
						args[1]=PokUtils.getAttributeValue(rootEntity, "TOMACHTYPE", "", "", false);
						args[2]=PokUtils.getAttributeValue(rootEntity, "TOMODEL", "", "", false);
						args[3]=getLD_NDN(avail);
						args[4]=rootEntity.getEntityGroup().getLongDescription();
						createMessage(oldDataChklvl,"MDLCVT_LASTORDERAVAIL_ERR",args);
					}
				}
				mdlLoAvailCtryTbl.clear();
			}
			mdlLoAvailVct.clear();
			
			//35.00	WHEN		AVAILTYPE	=	"End of Marketing" (200)										
			Vector mdlEomAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", EOMAVAIL);
			addDebug("checkModelAvails  mdlEomAvailVct "+mdlEomAvailVct.size());
			addHeading(3,mdlAvailGrp.getLongDescription()+" Model End of Marketing Avail Checks:");
			for (int i=0; i<mdlEomAvailVct.size(); i++){ 
				EntityItem avail = (EntityItem)mdlEomAvailVct.elementAt(i); 
				//36.00			EFFECTIVEDATE	<=	MODELCONVERT	WITHDRAWDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: MODELCONVERT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
				checkCanNotBeLater(mdlItem,avail, "EFFECTIVEDATE", rootEntity, "WITHDRAWDATE", checklvl);
				//37.00	END	35.00		
			}
			//	38.00	END	29.00
		}else{
			addHeading(3,"Model Planned Avail Checks were not done because a MODEL was not found matching "+
					" TOMACHTYPE:"+machtype+" and TOMODEL:"+modelatr);

			addDebug("checkModelAvails modelitem was not found");
		}   
		
	}

	/**********************************
	 * Must have MODELAVAIL in second VE because extracts wont go from SVCPRODSTRUCT thru MODEL to AVAIL
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 */
	private EntityList getModelVE(EntityItem modelEntity) throws MiddlewareRequestException, SQLException, MiddlewareException 
	{
		String VeName = "DQVEMODELAVAIL";

		EntityList mdlList = m_db.getEntityList(m_elist.getProfile(),
				new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
				new EntityItem[] { new EntityItem(null, m_elist.getProfile(), modelEntity.getEntityType(), modelEntity.getEntityID()) });
		addDebug("getModelVE:: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));

		return mdlList;
	}

	/*******************
	 * check the MODELCONVERTAVAIL AVAILS
	 * There must be at least one plannedavail
	 * There must be a plannedavail ctry for each firstorder ctry
	 * There must be a plannedavail ctry for each lastorder ctry
	 * The lastorder effdate must be => the plannedavail effdate for the same ctry
	 * 
	 * @param rootEntity
	 * @param statusFlag
	 * 
6.00	AVAIL	A	MODELCONVERTAVAIL-d								AVAIL	
7.00	WHEN		AVAILTYPE	=	"Planned Availability"							
8.00			CountOf	=>	1			RE	RE	RE		must have at least one "Planned Availability"
9.00			EFFECTIVEDATE	=>	MODELCONVERT	GENAVAILDATE		W				{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
10.00			COUNTRYLIST									
10.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
10.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE						
10.06	ANNOUNCEMENT		AVAILANNA-d									
10.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
10.10			ANNDATE	=>	MODELCONVERT	ANNDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be earlier than the {LD: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
10.12	END	10.02										
11.00	END	7.00										
12.00	AVAIL		MODELCONVERTAVAIL-d								AVAIL	
13.00	WHEN		AVAILTYPE	=	"First Order"							
14.00			EFFECTIVEDATE	=>	MODELCONVERT	ANNDATE		W				{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
15.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
15.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
15.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE						
15.06	ANNOUNCEMENT		AVAILANNA-d									
15.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
15.10			ANNDATE	<=	MODELCONVERT	GENAVAILDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
15.12	END	15.02										
16.00	END	13.00										
17.00	AVAIL	B	MODELCONVERTAVAIL-d								AVAIL	
18.00	WHEN		AVAILTYPE	=	"Last Order"							
19.00			EFFECTIVEDATE	<=	MODELCONVERT	WTHDRWEFFCTVDATE		W				{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: MODELCONVERT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
20.00			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes		W	E*2		{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
21.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E*2		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
21.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
21.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE						
21.06	ANNOUNCEMENT		AVAILANNA-d									
21.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
21.10			ANNDATE	<=	MODELCONVERT	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD: MODELCONVERT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
21.12	END	21.02										
22.00	END	18.00										

	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
//	private void checkAvails(EntityItem rootEntity, Vector plannedAvailVct, String availType, Hashtable plAvailCtryTbl,
//			Vector lastOrderAvailVct, String statusFlag) 
//	throws SQLException, MiddlewareException
//	{
//		int checklvl = getCheck_W_W_E(statusFlag); 
//
//		String annDate = PokUtils.getAttributeValue(rootEntity, "ANNDATE", "", EPOCH_DATE, false);
//		addDebug("checkAvails "+rootEntity.getKey()+" ANNDATE "+annDate);
//		
//		//get all AVAILS
//		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
//		if(availType.equals(MESPLANNEDAVAIL)){
//			addHeading(3,availGrp.getLongDescription()+" Planned Avail Checks:");
//		}else{
//			addHeading(3,availGrp.getLongDescription()+" MES Planned Avail Checks:");
//		}
//		int checklvlRE2 = getCheckLevel(CHECKLEVEL_RE,rootEntity,"ANNDATE");
//		
//		//6.00	AVAIL	A	MODELCONVERTAVAIL-d								AVAIL	
//		//7.00	WHEN		AVAILTYPE	=	"Planned Availability" or "MES Planned Availability"
////20160126 7.10	IF		AVAILTYPE	= 	"Planned Availability" 
//		//8.00			CountOf	=>	1			RE	RE	RE		must have at least one "Planned Availability"
//		if(availType.equals(MESPLANNEDAVAIL)){
//			checkPlannedAvailsExist(plannedAvailVct, CHECKLEVEL_RE);//checklvlRE2);
//		}
////		20121030 Add	8.20	WHEN		"Final" (FINAL)	=	SWPRODSTRUCT	DATAQUALITY																																																																																																																																																																																																																																																		
////		20121030 Add	8.22	IF		STATUS	=	"Ready for Review" (0040)																																																																																																																																																																																																																																																			
////		20121030 Add	8.24	OR		STATUS	=	"Final" (0020)		
////		20160126 Add	8.25	IF		AVAILTYPE	= 	"Planned Availability" 
////		20121030 Add	8.26			CountOf	=>	1					RE*2		must have at least one "Planned Availability" that is either "Ready for Review" or "Final" in order to be "Final"																																																																																																																																																																																																																																												
////		20121030 Add	8.28	END	8.20	
//		if(availType.equals(MESPLANNEDAVAIL)){
//			checkPlannedAvailsStatus(plannedAvailVct, rootEntity,checklvlRE2);
//		}
////		20121030 Change K & L	9.00			EFFECTIVEDATE	=>	MODELCONVERT	GENAVAILDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
//		int tmplvl = getCheck_W_W_E(statusFlag);
//		if (tmplvl!=CHECKLEVEL_NOOP) {
//			for (int i=0; i<plannedAvailVct.size(); i++){
//				EntityItem avail = (EntityItem)plannedAvailVct.elementAt(i);
//				checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "GENAVAILDATE", tmplvl);
//			}
//		}else{
//			addDebug("checkAvails bypassing MODELCONVERT plannedavail and GENAVAILDATE date check because status is:"+
//					statusFlag);
//		}
//
//		//10.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
//		//10.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE							
//		if(AVAIL_CHK_DATE.compareTo(annDate)<=0){
//			for(int i=0; i<plannedAvailVct.size(); i++){
//				EntityItem avail = (EntityItem)plannedAvailVct.elementAt(i);
//				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
//				addDebug("checkAvails pla "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
//				if (availAnntypeFlag==null){
//					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
//				}
//				//10.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE	
//				if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//					//	10.06	ANNOUNCEMENT		AVAILANNA-d	
//					Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//					addDebug("checkAvails rfa "+avail.getKey()+" annVct "+annVct.size());
//					if(annVct.size()!=0){
//						//10.10			ANNDATE	=>	MODELCONVERT	ANNDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be earlier than the {LD: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
//						for (int x=0; x<annVct.size(); x++){
//							EntityItem annitem = (EntityItem)annVct.elementAt(x);
//							checkCanNotBeEarlier(annitem, "ANNDATE", rootEntity, "ANNDATE",	getCheck_W_W_E(statusFlag));
//						}
//						annVct.clear();
//					}else{
//						//10.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
//						//MUST_BE_IN_ERR = {0} must be in an {1}
//						args[0] = getLD_NDN(avail);
//						args[1] = m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
//						createMessage(checklvlRE2,"MUST_BE_IN_ERR",args);
//					}
//				}
//				//10.12	END	10.02	
//			}	
//		}else{
//			addDebug("checkAvails PLA "+rootEntity.getKey()+" ANNDATE "+annDate+" is not greater than "+AVAIL_CHK_DATE);
//		}
//		//11.00	END	7.00										
//		Vector firstOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", FIRSTORDERAVAIL);
//		addDebug("checkAvails  firstOrderAvailVct "+firstOrderAvailVct.size());
//
//		addHeading(3,availGrp.getLongDescription()+" First Order Avail Checks:");
//		//12.00	AVAIL		MODELCONVERTAVAIL-d								AVAIL	
//		//13.00	WHEN		AVAILTYPE	=	"First Order"							
//		for (int i=0; i<firstOrderAvailVct.size(); i++){
//			EntityItem avail = (EntityItem)firstOrderAvailVct.elementAt(i);
//			if (tmplvl!=CHECKLEVEL_NOOP){
//				//14.00			EFFECTIVEDATE	=>	MODELCONVERT	ANNDATE		W	N N			{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
//				checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "ANNDATE", tmplvl);
//			}else{
//				if (i==0){
//					addDebug("checkAvails bypassing MODELCONVERT firstorder and ANNDATE date check because status is:"+
//							statusFlag);
//				}
//			}
//
//			//15.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
//			checkPlannedAvailForCtryExists(avail, plAvailCtryTbl.keySet(), checklvl);
//		}
//		
//		//15.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE	
//		if(	AVAIL_CHK_DATE.compareTo(annDate)<=0){
//			for (int i=0; i<firstOrderAvailVct.size(); i++){
//				EntityItem avail = (EntityItem)firstOrderAvailVct.elementAt(i);
//				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
//				addDebug("checkAvails fo "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
//				if (availAnntypeFlag==null){
//					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
//				}
//				//15.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE	
//	
//				if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//					//15.06	ANNOUNCEMENT		AVAILANNA-d	
//					Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//					addDebug("checkAvails rfa "+avail.getKey()+" annVct "+annVct.size());
//					if(annVct.size()!=0){
//						//15.10			ANNDATE	<=	MODELCONVERT	GENAVAILDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
//						for (int x=0; x<annVct.size(); x++){
//							EntityItem annitem = (EntityItem)annVct.elementAt(x);
//							checkCanNotBeLater(annitem, "ANNDATE", rootEntity, "GENAVAILDATE",getCheck_W_W_E(statusFlag));
//						}
//						annVct.clear();
//					}else{
//						//15.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
//						//MUST_BE_IN_ERR = {0} must be in an {1}
//						args[0] = getLD_NDN(avail);
//						args[1] = m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
//						createMessage(checklvlRE2,"MUST_BE_IN_ERR",args);
//					}
//				}
//				//15.12	END	15.02	
//			}
//			//16.00	END	13.00
//		}else{
//			addDebug("checkAvails FO "+rootEntity.getKey()+" ANNDATE "+annDate+" is not greater than "+AVAIL_CHK_DATE);
//		}
//
//		//17.00	AVAIL	B	MODELCONVERTAVAIL-d								AVAIL	
//		addHeading(3,availGrp.getLongDescription()+" Last Order Avail Checks:");
//		// now look for any lastorder ctry that isnt in a plannedavail
//		for (int i=0; i<lastOrderAvailVct.size(); i++){
//			EntityItem avail = (EntityItem)lastOrderAvailVct.elementAt(i);
//			//addDebug("checkAvails lastorderavail "+avail.getKey());
//
//			//18.00	WHEN		AVAILTYPE	=	"Last Order"							
//			//19.00			EFFECTIVEDATE	<=	MODELCONVERT	WTHDRWEFFCTVDATE		W				{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: MODELCONVERT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
//			tmplvl = doCheck_W_N_N(statusFlag);
//			if (tmplvl!=CHECKLEVEL_NOOP){
//				checkCanNotBeLater(avail, "EFFECTIVEDATE", rootEntity, "WTHDRWEFFCTVDATE", tmplvl);
//			}else{
//				if (i==0){
//					addDebug("checkAvails bypassing MODELCONVERT lastorder and WTHDRWEFFCTVDATE date check because status is:"+
//							statusFlag);
//				}
//			}
//
//			// do the date checks now
//			tmplvl = doCheck_N_W_E(statusFlag);
//			if (tmplvl!=CHECKLEVEL_NOOP){
//				tmplvl = getCheckLevel(tmplvl,rootEntity,"ANNDATE");
//				Vector plaVct = new Vector(); // hold onto plannedavail for date checks incase same avail for mult ctrys
//				checkCtryMismatch(avail, plAvailCtryTbl, plaVct,tmplvl);
//				String date1 = getAttrValueAndCheckLvl(avail, "EFFECTIVEDATE", tmplvl);
//				for (int m=0; m<plaVct.size(); m++){
//					EntityItem plAvail = (EntityItem)plaVct.elementAt(m);
//					//20.00			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes		W	E*2		{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
//					String pladate2 = getAttrValueAndCheckLvl(plAvail, "EFFECTIVEDATE", tmplvl);
//					addDebug("checkAvails plannedavail: "+
//							plAvail.getKey()+" EFFECTIVEDATE:"+pladate2+" lastorder:"+
//							avail.getKey()+" EFFECTIVEDATE:"+date1);
//					boolean isok = checkDates(date1, pladate2, DATE_GR_EQ);	//date1=>date2	
//					if (!isok){
//						//CANNOT_BE_EARLIER_ERR2 = {0} {1} must not be earlier than the {2} {3}
//						//{LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
//						args[0]=getLD_NDN(avail);
//						args[1]=this.getLD_Value(avail, "EFFECTIVEDATE");
//						args[2]=rootEntity.getEntityGroup().getLongDescription();
//						args[3]=getLD_NDN(plAvail);
//						createMessage(tmplvl,"CANNOT_BE_EARLIER_ERR2",args);
//					}
//				}
//				plaVct.clear();
//			}else{
//				if (i==0){
//					addDebug("checkAvails bypassing MODELCONVERT lastorder and planned avail date check because status is:"+
//							statusFlag);
//				}
//			}
//
//			//21.00			COUNTRYLIST	"in	aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E*2		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
//			checkPlannedAvailForCtryExists(avail, plAvailCtryTbl.keySet(), getCheckLevel(checklvl,rootEntity,"ANNDATE"));
//		}
//		
//		//21.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE	
//		if(AVAIL_CHK_DATE.compareTo(annDate)<=0){
//			for (int i=0; i<lastOrderAvailVct.size(); i++){
//				EntityItem avail = (EntityItem)lastOrderAvailVct.elementAt(i);
//				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
//				addDebug("checkAvails lo "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
//				if (availAnntypeFlag==null){
//					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
//				}
//				//21.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE	
//				if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//					//21.06	ANNOUNCEMENT		AVAILANNA-d	
//					Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//					addDebug("checkAvails rfa "+avail.getKey()+" annVct "+annVct.size());
//					if(annVct.size()!=0){
//						//21.10			ANNDATE	<=	MODELCONVERT	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD: MODELCONVERT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
//						for (int x=0; x<annVct.size(); x++){
//							EntityItem annitem = (EntityItem)annVct.elementAt(x);
//							checkCanNotBeLater(annitem, "ANNDATE", rootEntity, "WITHDRAWDATE",getCheck_W_W_E(statusFlag));
//						}
//						annVct.clear();
//					}else{
//						//21.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
//						//MUST_BE_IN_ERR = {0} must be in an {1}
//						args[0] = getLD_NDN(avail);
//						args[1] = m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
//						createMessage(checklvlRE2,"MUST_BE_IN_ERR",args);
//					}
//				}
//				//21.12	END	21.02	
//				//22.00	END	18.00	
//			}// end lastorder loop
//		}else{
//			addDebug("checkAvails LO "+rootEntity.getKey()+" ANNDATE "+annDate+" is not greater than "+AVAIL_CHK_DATE);
//		}
//		
//		firstOrderAvailVct.clear();
//	}
	/***
	 * check MODELCONVERT "Last Order Avail" or "MES Last Order Avail"
	 	17.00	AVAIL	B	MODELCONVERTAVAIL-d
	 	18.00	WHEN		AVAILTYPE	=	"Last Order" or "MES Last Order"							
		19.00			EFFECTIVEDATE	<=	MODELCONVERT	WTHDRWEFFCTVDATE		W				{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: MODELCONVERT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
		20.00			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes		W	E*2		{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
		21.00			COUNTRYLIST	"in	aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E*2		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
		21.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
		21.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE						
		21.06	ANNOUNCEMENT		AVAILANNA-d									
		21.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
		21.10			ANNDATE	<=	MODELCONVERT	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD: MODELCONVERT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
		21.12	END	21.02										
		22.00	END	18.00										
	 * @param rootEntity
	 * @param mesLoOrLoAvailVct
	 * @param availType
	 * @param statusFlag
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkMdlCvtMesLoAndLoAvail(EntityItem rootEntity, Vector mesLoOrLoAvailVct, String availType, Hashtable mesPlOrPlAvailCtryTbl, String statusFlag)
			throws SQLException, MiddlewareException
	{
		int checklvl = getCheck_W_W_E(statusFlag); 
		int checklvlRE2 = getCheckLevel(CHECKLEVEL_RE,rootEntity,"ANNDATE");
		String annDate = PokUtils.getAttributeValue(rootEntity, "ANNDATE", "", EPOCH_DATE, false);
		
		addDebug("checkMdlCvtMesLoAndLoAvail lastOrderAvailVct "+mesLoOrLoAvailVct.size() + " " +rootEntity.getKey()+" ANNDATE "+annDate);
		
		//17.00	AVAIL	B	MODELCONVERTAVAIL-d								AVAIL	
		// now look for any lastorder ctry that isnt in a plannedavail
		for (int i=0; i<mesLoOrLoAvailVct.size(); i++){
			EntityItem avail = (EntityItem)mesLoOrLoAvailVct.elementAt(i);
			//addDebug("checkAvails lastorderavail "+avail.getKey());

			//18.00	WHEN		AVAILTYPE	=	"Last Order"							
			//19.00			EFFECTIVEDATE	<=	MODELCONVERT	WTHDRWEFFCTVDATE		W				{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: MODELCONVERT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
			int tmplvl = doCheck_W_N_N(statusFlag);
			if (tmplvl!=CHECKLEVEL_NOOP){
				checkCanNotBeLater(avail, "EFFECTIVEDATE", rootEntity, "WTHDRWEFFCTVDATE", tmplvl);
			}else{
				if (i==0){
					addDebug("checkMdlCvtMesLoAndLoAvail bypassing MODELCONVERT lastorder and WTHDRWEFFCTVDATE date check because status is:"+
							statusFlag);
				}
			}

			// do the date checks now
			tmplvl = doCheck_N_W_E(statusFlag);
			if (tmplvl!=CHECKLEVEL_NOOP){
				tmplvl = getCheckLevel(tmplvl,rootEntity,"ANNDATE");
				Vector plaVct = new Vector(); // hold onto plannedavail for date checks incase same avail for mult ctrys
				checkCtryMismatch(avail, mesPlOrPlAvailCtryTbl, plaVct,tmplvl);
				String date1 = getAttrValueAndCheckLvl(avail, "EFFECTIVEDATE", tmplvl);
				for (int m=0; m<plaVct.size(); m++){
					EntityItem plAvail = (EntityItem)plaVct.elementAt(m);
					//20.00			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes		W	E*2		{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
					String pladate2 = getAttrValueAndCheckLvl(plAvail, "EFFECTIVEDATE", tmplvl);
					addDebug("checkMdlCvtMesLoAndLoAvail plannedavail: "+plAvail.getKey()+" EFFECTIVEDATE:"+pladate2+" lastorder:"+
							avail.getKey()+" EFFECTIVEDATE:"+date1);
					boolean isok = checkDates(date1, pladate2, DATE_GR_EQ);	//date1=>date2	
					if (!isok){
						//CANNOT_BE_EARLIER_ERR2 = {0} {1} must not be earlier than the {2} {3}
						//{LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD: MODELCONVERT} {LD: AVAIL} {NDN: A: AVAIL}
						args[0]=getLD_NDN(avail);
						args[1]=this.getLD_Value(avail, "EFFECTIVEDATE");
						args[2]=rootEntity.getEntityGroup().getLongDescription();
						args[3]=getLD_NDN(plAvail);
						createMessage(tmplvl,"CANNOT_BE_EARLIER_ERR2",args);
					}
				}
				plaVct.clear();
			}else{
				if (i==0){
					addDebug("checkMdlCvtMesLoAndLoAvail bypassing MODELCONVERT lastorder and planned avail date check because status is:"+
							statusFlag);
				}
			}

			//21.00			COUNTRYLIST	"in	aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E*2		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
			String missingCtry = checkCtryMismatch(avail, mesPlOrPlAvailCtryTbl, checklvl);
			if (missingCtry.length()>0){
				addDebug("checkMdlCvtMesLoAndLoAvail loavail "+avail.getKey()+" COUNTRYLIST had extra ["+missingCtry+"]");

				//MDLCVT_MISSING_PLA_CTRY_ERR = {0} {1} includes a Country that does not have a &quot;Planned Availability&quot; Extra countries are: {2}
				//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
				args[0]=getLD_NDN(avail);
				args[1] = PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
				args[2]= missingCtry;
				if(availType.equals(LASTORDERAVAIL)){
					createMessage(checklvl,"MDLCVT_MISSING_PLA_CTRY_ERR",args);
				}else{
					createMessage(checklvl,"MISSING_MES_PLA_CTRY_ERR",args);
				}
			}
		}
		
		//21.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE	
		if(AVAIL_CHK_DATE.compareTo(annDate)<=0){
			for (int i=0; i<mesLoOrLoAvailVct.size(); i++){
				EntityItem avail = (EntityItem)mesLoOrLoAvailVct.elementAt(i);
				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
				addDebug("checkMdlCvtMesLoAndLoAvail loavail "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
				if (availAnntypeFlag==null){
					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
				}
				//21.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE	
				if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
					//21.06	ANNOUNCEMENT		AVAILANNA-d	
					Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
					addDebug("checkMdlCvtMesLoAndLoAvail rfa "+avail.getKey()+" annVct "+annVct.size());
					if(annVct.size()!=0){
						//21.10			ANNDATE	<=	MODELCONVERT	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD: MODELCONVERT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
						for (int x=0; x<annVct.size(); x++){
							EntityItem annitem = (EntityItem)annVct.elementAt(x);
							checkCanNotBeLater(annitem, "ANNDATE", rootEntity, "WITHDRAWDATE",getCheck_W_W_E(statusFlag));
						}
						annVct.clear();
					}else{
						//21.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
						//MUST_BE_IN_ERR = {0} must be in an {1}
						args[0] = getLD_NDN(avail);
						args[1] = m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
						createMessage(checklvlRE2,"MUST_BE_IN_ERR",args);
					}
				}
				//21.12	END	21.02	
				//22.00	END	18.00	
			}// end lastorder loop
		}else{
			addDebug("checkMdlCvtMesLoAndLoAvail LO "+rootEntity.getKey()+" ANNDATE "+annDate+" is not greater than "+AVAIL_CHK_DATE);
		}
	}
	/***
	 * check MODELCONVERT "First Order Avail"
	 	12.00	AVAIL		MODELCONVERTAVAIL-d
		13.00	WHEN		AVAILTYPE	=	"First Order"							
		14.00			EFFECTIVEDATE	=>	MODELCONVERT	ANNDATE		W				{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
		15.00			COUNTRYLIST	"in		aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
		15.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
		15.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE						
		15.06	ANNOUNCEMENT		AVAILANNA-d									
		15.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
		15.10			ANNDATE	<=	MODELCONVERT	GENAVAILDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
		15.12	END	15.02										
		16.00	END	13.00										
	 * @param rootEntity
	 * @param firstOrderAvailVct
	 * @param mesPlaOrPlaAvail
	 * @param availType
	 * @param statusFlag
	 */
	private void checkMdlCvtFirstOrderAvail(EntityItem rootEntity, Vector firstOrderAvailVct, Vector plannedAvailVct,
			Vector mesPlannedAvailVct, String statusFlag)
			throws SQLException, MiddlewareException
	{
		int checklvl = getCheck_W_W_E(statusFlag); 
		int checklvlRE2 = getCheckLevel(CHECKLEVEL_RE,rootEntity,"ANNDATE");		
		String annDate = PokUtils.getAttributeValue(rootEntity, "ANNDATE", "", EPOCH_DATE, false);
		
		addDebug("checkMdlCvtFirstOrderAvail  firstOrderAvailVct "+firstOrderAvailVct.size() + " " +rootEntity.getKey()+" ANNDATE "+annDate);
		
		//12.00	AVAIL		MODELCONVERTAVAIL-d								AVAIL	
		//13.00	WHEN		AVAILTYPE	=	"First Order"							
		for (int i=0; i<firstOrderAvailVct.size(); i++){
			EntityItem avail = (EntityItem)firstOrderAvailVct.elementAt(i);
			if (checklvl!=CHECKLEVEL_NOOP){
				//14.00			EFFECTIVEDATE	=>	MODELCONVERT	ANNDATE		W	N N			{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
				checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "ANNDATE", checklvl);
			}else{
				if (i==0){
					addDebug("checkMdlCvtFirstOrderAvail bypassing MODELCONVERT firstorder and ANNDATE date check because status is:"+
							statusFlag);
				}
			}
			Hashtable plannedAvailCtry = getAvailByCountry(plannedAvailVct,checklvl);
			//15.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
			String missingCtry = checkCtryMismatch(avail, plannedAvailCtry, checklvl);
			if (missingCtry.length()>0){
				addDebug("checkMdlCvtFirstOrderAvail foavail "+avail.getKey()+" COUNTRYLIST had extra ["+missingCtry+"]");

				//MDLCVT_MISSING_PLA_CTRY_ERR = {0} {1} includes a Country that does not have a &quot;Planned Availability&quot; Extra countries are: {2}
				//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
				args[0]=getLD_NDN(avail);
				args[1] = PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
				args[2]= missingCtry;
				createMessage(checklvl,"MDLCVT_MISSING_PLA_CTRY_ERR",args);
			}
			
			// MES Planned Avail
			if(mesPlannedAvailVct != null && mesPlannedAvailVct.size() > 0){
				Hashtable mesPlannedAvailCtry = getAvailByCountry(mesPlannedAvailVct,checklvl);
				//15.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
				missingCtry = checkCtryMismatch(avail, mesPlannedAvailCtry, checklvl);
				if (missingCtry.length()>0){
					addDebug("checkMdlCvtFirstOrderAvail foavail "+avail.getKey()+" COUNTRYLIST had extra ["+missingCtry+"]");
	
					//MISSING_MES_PLA_CTRY_ERR = {0} {1} includes a Country that does not have a &quot;MES Planned Availability&quot; Extra countries are: {2}
					//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
					args[0]=getLD_NDN(avail);
					args[1] = PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
					args[2]= missingCtry;
					createMessage(checklvl,"MISSING_MES_PLA_CTRY_ERR",args);
				}
			}
		}
		
		//15.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE	
		if(	AVAIL_CHK_DATE.compareTo(annDate)<=0){
			for (int i=0; i<firstOrderAvailVct.size(); i++){
				EntityItem avail = (EntityItem)firstOrderAvailVct.elementAt(i);
				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
				addDebug("checkMdlCvtFirstOrderAvail fo "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
				if (availAnntypeFlag==null){
					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
				}
				//15.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE	
				if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
					//15.06	ANNOUNCEMENT		AVAILANNA-d	
					Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
					addDebug("checkMdlCvtFirstOrderAvail"+avail.getKey()+" annVct "+annVct.size());
					if(annVct.size()!=0){
						//15.10			ANNDATE	<=	MODELCONVERT	GENAVAILDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
						for (int x=0; x<annVct.size(); x++){
							EntityItem annitem = (EntityItem)annVct.elementAt(x);
							checkCanNotBeLater(annitem, "ANNDATE", rootEntity, "GENAVAILDATE",checklvl);
						}
						annVct.clear();
					}else{
						//15.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
						//MUST_BE_IN_ERR = {0} must be in an {1}
						args[0] = getLD_NDN(avail);
						args[1] = m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
						createMessage(checklvlRE2,"MUST_BE_IN_ERR",args);
					}
				}
				//15.12	END	15.02	
			}
			//16.00	END	13.00
		}else{
			addDebug("checkMdlCvtFirstOrderAvail FO "+rootEntity.getKey()+" ANNDATE "+annDate+" is not greater than "+AVAIL_CHK_DATE);
		}
	}
	
	/****
	 * check MODELCONVERT "Planned Availability" or "MES Planned Availability"
		6.00	AVAIL	A	MODELCONVERTAVAIL-d								AVAIL	
		7.00	WHEN		AVAILTYPE	=	"Planned Availability" or "MES Planned Availability"							
		7.10	IF		AVAILTYPE	= 	"Planned Availability" 							
		8.00			CountOf	=>	1			RE*2	RE*2	RE*2		must have at least one "Planned Availability"
		8.10	END	7.10										
		8.20	WHEN		"Final" (FINAL)	=	SWPRODSTRUCT	DATAQUALITY						
		8.22	IF		STATUS	=	"Ready for Review" (0040)							
		8.24	OR		STATUS	=	"Final" (0020)							
		8.25	IF		AVAILTYPE	= 	"Planned Availability" 							
		8.26			CountOf	=>	1					RE*2		must have at least one "Planned Availability" that is either "Ready for Review" or "Final" in order to be "Final"
		8.27	END	8.25										
		8.28	END	8.20										
		9.00			EFFECTIVEDATE	=>	MODELCONVERT	GENAVAILDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
		10.00			COUNTRYLIST									
		10.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
		10.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE						
		10.06	ANNOUNCEMENT		AVAILANNA-d									
		10.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
		10.10			ANNDATE	=>	MODELCONVERT	ANNDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be earlier than the {LD: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
		10.12	END	10.02										
		11.00	END	7.00
	 * @param rootEntity
	 * @param plannedAvailVct
	 * @param availType
	 * @param statusFlag
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkMdlCvtMesPlaAndPlaAvail(EntityItem rootEntity, Vector mesPlOrPlAvailVct, String availType, String statusFlag)
			throws SQLException, MiddlewareException
	{
		int checklvl = getCheck_W_W_E(statusFlag); 
		String annDate = PokUtils.getAttributeValue(rootEntity, "ANNDATE", "", EPOCH_DATE, false);
		
		addDebug("checkMdlCvtMesPlaAndPlaAvail "+rootEntity.getKey()+" ANNDATE "+annDate);
		
		int checklvlRE2 = getCheckLevel(CHECKLEVEL_RE,rootEntity,"ANNDATE");
		
		//			6.00	AVAIL	A	MODELCONVERTAVAIL-d								AVAIL	
		//20160126	7.00	WHEN		AVAILTYPE	=	"Planned Availability" or "MES Planned Availability"
		//20160126	7.10	IF		AVAILTYPE	= 	"Planned Availability" 
		//			8.00			CountOf	=>	1			RE	RE	RE		must have at least one "Planned Availability"
		if(availType.equals(PLANNEDAVAIL)){
			checkPlannedAvailsExist(mesPlOrPlAvailVct, CHECKLEVEL_RE);//checklvlRE2);
		}
		//			8.20	WHEN		"Final" (FINAL)	=	SWPRODSTRUCT	DATAQUALITY																																																																																																																																																																																																																																																		
		//			8.22	IF		STATUS	=	"Ready for Review" (0040)																																																																																																																																																																																																																																																			
		//			8.24	OR		STATUS	=	"Final" (0020)		
		//20160126	8.25	IF		AVAILTYPE	= 	"Planned Availability" 
		//			8.26			CountOf	=>	1					RE*2		must have at least one "Planned Availability" that is either "Ready for Review" or "Final" in order to be "Final"																																																																																																																																																																																																																																												
		//			8.28	END	8.20	
		if(availType.equals(PLANNEDAVAIL)){
			checkPlannedAvailsStatus(mesPlOrPlAvailVct, rootEntity,checklvlRE2);
		}
		//		9.00			EFFECTIVEDATE	=>	MODELCONVERT	GENAVAILDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
		if (checklvl!=CHECKLEVEL_NOOP) {
			for (int i=0; i<mesPlOrPlAvailVct.size(); i++){
				EntityItem avail = (EntityItem)mesPlOrPlAvailVct.elementAt(i);
				checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "GENAVAILDATE", checklvl);
			}
		}else{
			addDebug("checkMdlCvtMesPlaAndPlaAvail bypassing MODELCONVERT plannedavail and GENAVAILDATE date check because status is:"+
					statusFlag);
		}

		//10.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE						
		//10.04	AND		"2011-07-01"	<=	MODELCONVERT	ANNDATE							
		if(AVAIL_CHK_DATE.compareTo(annDate)<=0){
			for(int i=0; i<mesPlOrPlAvailVct.size(); i++){
				EntityItem avail = (EntityItem)mesPlOrPlAvailVct.elementAt(i);
				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
				addDebug("checkMdlCvtMesPlaAndPlaAvail pla "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
				if (availAnntypeFlag==null){
					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
				}
				//10.02	IF		"RFA" (RFA)	=	AVAIL	AVAILANNTYPE	
				if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
					//	10.06	ANNOUNCEMENT		AVAILANNA-d	
					Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
					addDebug("checkMdlCvtMesPlaAndPlaAvail rfa "+avail.getKey()+" annVct "+annVct.size());
					if(annVct.size()!=0){
						//10.10			ANNDATE	=>	MODELCONVERT	ANNDATE		W	W	E		{LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be earlier than the {LD: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
						for (int x=0; x<annVct.size(); x++){
							EntityItem annitem = (EntityItem)annVct.elementAt(x);
							checkCanNotBeEarlier(annitem, "ANNDATE", rootEntity, "ANNDATE",	checklvl);
						}
						annVct.clear();
					}else{
						//10.08			CountOf	=>	1			RE*2	RE*2	RE*2		{LD: AVAIL} {NDN: AVAIL} must be in an {LD: ANNOUNCEMENT}
						//MUST_BE_IN_ERR = {0} must be in an {1}
						args[0] = getLD_NDN(avail);
						args[1] = m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
						createMessage(checklvlRE2,"MUST_BE_IN_ERR",args);
					}
				}
				//10.12	END	10.02	
			}	
		}else{
			addDebug("checkMdlCvtMesPlaAndPlaAvail PLA "+rootEntity.getKey()+" ANNDATE "+annDate+" is not greater than "+AVAIL_CHK_DATE);
		}
		//11.00	END	7.00
	}

	/*************************************
	 * @param machtype
	 * @param modelatr
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
23	AVAIL		SEARCH MODEL where MACHTYPEATR = TOMACHTYPE & MODELATR = TOMODEL& COFGRP = "Base" (150)
29	AVAIL		SEARCH MODEL where MACHTYPEATR = TOMACHTYPE & MODELATR = TOMODEL& COFGRP = "Base" (150)

	 */
	private EntityItem searchForModel(String machtype, String modelatr) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		addDebug("searchForModel entered machtype "+machtype+" modelatr "+modelatr);
		EntityItem model = null;
		Vector attrVct = new Vector(1);
		attrVct.addElement("MACHTYPEATR");
		attrVct.addElement("MODELATR");
		Vector valVct = new Vector(1);
		valVct.addElement(machtype);
		valVct.addElement(modelatr);

		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			eia= ABRUtil.doSearch(getDatabase(), m_prof, 
					MODEL_SRCHACTION_NAME, "MODEL", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug("searchForModel SBRException: "+exBuf.getBuffer().toString());
		}
		if (eia!=null && eia.length > 0){		
			//& COFGRP = "Base" (150) COFGRP_Base
			boolean isOneMdl=true;
			for (int i=0; i<eia.length; i++){
				EntityItem item = eia[i];
				// be filter based on cofgrp
				String cofgrp = PokUtils.getAttributeFlagValue(item, "COFGRP");
				addDebug("searchForModel found "+eia[i].getKey()+" cofgrp "+cofgrp);
				if (COFGRP_Base.equals(cofgrp))	{
					if (model!=null){
						isOneMdl = false;
						break;
					}
					model = item;
				}
			}
				
			if (!isOneMdl){
				StringBuffer sb = new StringBuffer();
				sb.append("More than one MODEL with COFGRP=Base found for "+machtype+" "+modelatr);
				for (int i=0; i<eia.length; i++){
					sb.append("<br />"+eia[i].getKey()+":"+eia[i]);
				}
				addError(sb.toString(),null);
			}
		}
		attrVct.clear();
		valVct.clear();
		return model;
	}

	/**********************************
	 * class has a different status attribute
	 */
	protected String getStatusAttrCode() { return "STATUS";}

	/***********************************************
	 *  Get ABR description
	 *
	 *@return java.lang.String
	 */
	public String getDescription()
	{
		String desc =  "MODELCONVERT ABR.";
		return desc;
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getABRVersion()
	{
		return "1.12";
	}
}
