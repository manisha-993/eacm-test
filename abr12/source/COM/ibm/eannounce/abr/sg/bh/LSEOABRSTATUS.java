// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import com.ibm.transform.oim.eacm.util.*;

import java.sql.SQLException;
import java.util.*;


/**
LSEOABRSTATUS_class=COM.ibm.eannounce.abr.sg.LSEOABRSTATUS
LSEOABRSTATUS_enabled=true
LSEOABRSTATUS_idler_class=A
LSEOABRSTATUS_keepfile=true
LSEOABRSTATUS_report_type=DGTYPE01
LSEOABRSTATUS_vename=EXRPT3LSEO1
LSEOABRSTATUS_CAT1=RPTCLASS.LSEOABRSTATUS
LSEOABRSTATUS_CAT2=
LSEOABRSTATUS_CAT3=RPTSTATUS
LSEOABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
 
*
* 
*/
/**********************************************************************************
* LSEOABRSTATUS class
* BH FS ABR Data Quality 20120131.xls - EOS and CATLGOR updates delete 327.20 -needs updated VE EXRPT3LSEO1
* 
*BH FS ABR Data Quality 20111020e.xls BH Defect 67890 (support for old data)
*sets changes
* BH FS ABR Data Quality Checks 20110322.xls
* Delete 4.04 and 4.08, add 327.20
* 
*"SG FS ABR Data Quality 20100521.doc"
*needs updated VE  EXRPT3LSEO1 - for dropid 100169
*needs workflow actions for lifecycle
*needs LIFECYCLE attribute and transitions
*
* From "SG FS ABR Data Quality 20090729.doc"
A.	Checking

The LSEO checking has the following variations to deal with:
1.	The LSEO may be a Special Bid (or Generally Available) which determines if it does not have (or has) 
children AVAILs. WWSEO SPECBID indicates this.
e.g. Key 8
2.	There are three types of LSEOs: Hardware, Software or Service which determines the type of features it can 
include. MODEL COFCAT indicates this.
e.g. Key 27
3.	Features can be included (referenced) at either the world wide level (WWSEO � e.g. Key 42) or the country 
level (LSEO � e.g. Key 31).
4.	Features that are included may include RPQs or GA features.
Note: PRPQs are a new PID (MODEL) and Service Features do not indicate if they are special bids. Since the 
planning dates (those on the offering itself) are always checked, there is no need to check anything else for 
RPQs. (e.g. Key 28)
5.	Features that are included have to be Available for at least the same period of time as the LSEO. See the 
following details.

The fifth item in the list checks that:
1.	the �Planned Availability� date of the LSEO is not earlier than the �Planned Availability� date of the feature.
e.g. Key 33
2.	if the feature has a �Last Order� for a Country that the LSEO is offered in, then the LSEO must have a �Last Order� 
for this Country with a date that is not later than the feature �Last Order� date.
e.g. Key 38 thru 40
See also the description of �Match� in the Section on Rules


*C.	STATUS changed to Final
*
*1.	IF FirstTime(LSEO.STATUS = 0020 (Final)) THEN  ELSE obtain the value for SAPASSORTMODULE that
*was in effect at the 'last DTS that STATUS was Final' and Set SAPASSORTMODULEPRIOR equal to that value.
*2.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
*3.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
*
*/
//LSEOABRSTATUS.java,v
//Revision 1.12  2011/03/23 18:07:21  wendy
//Add CATDATA and chg date attr for checks and chg EOM AVAIL chk
//
//Revision 1.5  2010/01/21 22:20:26  wendy
//updates for BH FS ABR Data Quality 20100120.doc
//
//Revision 1.2  2009/12/07 22:43:55  wendy
//Updated for spec chg BH FS ABR Data Qualtity 20091207b.xls
//
//Revision 1.1  2009/08/18 18:00:15  wendy
//SR10, 11, 12, 15, 17 BH updates
//
public class LSEOABRSTATUS extends DQABRSTATUS
{
	//private static final String SEOORDERCODE_MES =	"20";
	
	/**********************************
	* must be domain of interest and ready4review
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/**
from sets ss
69.00		LSEO		Root Entity							
70.00		Perform			LSEO				SAPASSORTMODULEPRIOR		SetPriorAssortModule
71.00		Section		Two							
72.00		SET			LSEO				EPIMSABRSTATUS		&ABRWAITODS
74.00		IF		WWSEOLSEO-u	WWSEO	SPECBID	=	"No" (11457)			
75.00		IF		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
76.00		AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	WWPRTABRSTATUS		&ABRWAITODS2 
77.00		END	75.00								
78.00		IF		LSEOAVAIL-d	AVAIL	AVAILTYPE	=	"Planned Availability" (146)			
79.00		AND		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
80.00		AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	QSMRPTABRSTATUS 		&QSMWAITODS 
81.00		END	78.00								
82.00		IF		LSEOAVAIL-d	AVAIL	AVAILTYPE	=	"Last Order" (149)			
83.00		AND		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
84.00		AND			ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)	QSMRPTABRSTATUS 		&QSMWAITODS 
85.00		END	82.00								
		85.200	IF			LSEO	STATUS	=	"Ready for Review" (0040)		
		85.220	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
Add 2011-10-20		85.222	IF			LSEO	LSEOPUBDATEMTRGT	>	"2010-03-01" | Empty		
Change 2011-10-20		85.240	IF		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
		85.250	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
		85.260	OR		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020) | "Ready for Review" (0040)		
		85.280	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR
		85.290	END	85.250							
Add 2011-10-20		85.292	END	85.240							
Add 2011-10-20		85.294	ELSE	85.222							
Add 2011-10-20		85.296	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR
Add 2011-10-20		85.298	END	85.222							
		85.300	END	85.200							
								
		85.320	IF			LSEO	STATUS	=	"Final" (0020)			
Add 2011-10-20		85.322	IF			LSEO	LSEOPUBDATEMTRGT	>	"2010-03-01" | Empty			
Change 2011-10-20		85.340	IF		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)			
		85.350	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)			
		85.360	OR		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020)			
		85.380	SET			LSEO				ADSABRSTATUS		&ADSFEED
		85.390	END	85.350								
Add 2011-10-20		85.392	END	85.340								
Add 2011-10-20		85.394	ELSE	85.322								
Add 2011-10-20		85.396	SET			LSEO				ADSABRSTATUS		&ADSFEED
Add 2011-10-20		85.398	END	85.322								
		85.400	END	85.320								
							
								
86.00		ELSE	74.00		LSEO				WWPRTABRSTATUS		&ABRWAITODS2 
87.00		SET			LSEO				QSMRPTABRSTATUS 		&QSMWAITODS 
87.10	R1.0	IF			LSEO	STATUS	=	"Ready for Review" (0040)			
87.11	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
87.12	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
87.13	R1.0	END	87.10								
87.20		IF			LSEO	STATUS	=	"Final" (0020)			
87.23		SET			LSEO				ADSABRSTATUS		&ADSFEED
87.24		END	87.20								
88.00		END	74.00								
89.00		COMMENT		The following from LSEOBUNDLE so that we do not have to queue DQ ABR for LSEOBUNDLE							
90.00		IF			LSEO	STATUS	=	"Final" (0020)			
91.00		IF			LSEOBUNDLE	STATUS	=	"Final" (0020)			
92.00		IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	SPECBID	=	"No" (11457)			
93.00		IF	A	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020)			
94.00		AND		A: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
95.00		Perform		SETS_Section	LSEOBUNDLE	One					
96.00		END	93.00								
97.00		ELSE	92.00								
98.00		Perform		SETS_Section	LSEOBUNDLE	One					
99.00		END	92.00								
100.00		END	91.00								
100.20	R1.0	IF			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)			
100.22	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  
100.24	R1.0	IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	SPECBID	=	"No" (11457)
100.26	R1.0	IF	E	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)

100.27	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
100.28	R1.0	OR		E: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
100.30	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
100.31	R1.0	END	100.27					

100.32	R1.0	END	100.26					
100.34	R1.0	ELSE	100.24					
100.36	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
100.38	R1.0	END	100.24					
100.40	R1.0	END	100.20					
101.00		END	90.00					
101.20	R1.0	COMMENT		The following from LSEOBUNDLE so that we do not have to queue DQ ABR for LSEOBUNDLE				
101.22	R1.0	IF			LSEO	STATUS	=	"Ready for Review" (0040)
101.24	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
101.26	R1.0	IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)
101.28	R1.0	IF			LSEOBUNDLE	SPECBID	=	"No" (11457)
101.30	R1.0	IF	B	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
101.31	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
101.32	R1.0	OR		B: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
101.34	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
101.35	R1.0	END	101.31					
				
101.36	R1.0	END	101.30								
101.38	R1.0	ELSE	101.28					
101.40	R1.0	Perform		SETS_Section	LSEOBUNDLE	One					
101.42	R1.0	END	101.28								
101.44	R1.0	END	101.26								
101.46	R1.0	END	101.22								
102.00		END	71.00	Section Two							
103.00		END	69.00	LSEO							
				
	 */

	/**********************************
	* complete abr processing after status moved to readyForReview; (status was chgreq or draft)
	*/
	protected void completeNowR4RProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		doLSEOSectionTwo();
    }

	/**********************************
	* complete abr processing after status moved to final; (status was r4r)
	*C.	STATUS changed to Final
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		//	70.00	Perform			LSEO				SAPASSORTMODULEPRIOR	nofrf	SetPriorAssortModule
		checkAssortModule();

		doLSEOSectionTwo();
	}
	private void doLSEOSectionTwo() throws MiddlewareRequestException, SQLException, MiddlewareException{
		String specbid ="";
		EntityGroup eg = m_elist.getEntityGroup("WWSEO");

		if (eg.getEntityItemCount()>0){ // checks may not have been done
			EntityItem wwseoItem = eg.getEntityItem(0);
			specbid = getAttributeFlagEnabledValue(wwseoItem, "SPECBID");
			addDebug(wwseoItem.getKey()+" SPECBID: "+specbid);
		}
		doLSEOSectionTwo(new EntityItem[]{m_elist.getParentEntityGroup().getEntityItem(0)},specbid);
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
		//	NOW() <= LSEO Unpublish Date - Target (LSEOUNPUBDATEMTRGT)
		String wdDate = PokUtils.getAttributeValue(rootEntity, "LSEOUNPUBDATEMTRGT", "", FOREVER_DATE, false);
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
	protected String getLCRFRWFName(){ return "WFLCLSEORFR";}
	protected String getLCFinalWFName(){ return "WFLCLSEOFINAL";}

	/**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
									
1.00	LSEO		Root									
2.00			LSEOPUBDATEMTRGT									
3.00			LSEOUNPUBDATEMTRGT	=>	LSEO	LSEOPUBDATEMTRGT		W	W	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be earlier than {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
4.00			COUNTRYLIST									
4.02			WWSEOLSEO-u: MODELWWSEO-u									
Delete 20110322 4.04	IF		"Service" (102)	<>	MODEL	COFCAT						
4.06			"00 - No Product Hierarchy Code" (BHPH0000)	<>	LSEO	BHPRODHIERCD		E	E	E		must not have {LD: BHPRODHIERCD} {BHPRODHIERCD}
Delete 20110322 4.08	END	4.04										
5.00	WWSEO		WWSEOLSEO-u									
6.00			STATUS	=>	LSEO	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: WWSEO} {NDN: WWSEO} {LD: STATUS} {STATUS}
7.00			CountOf	=	1			E	E	E		must have only one parent {LD: WWSEO}
8.00	WHEN		SPECBID	=	"No" (11457)						GA	
9.00	AVAIL	A	LSEOAVAIL-d								LSEO: AVAIL	
10.00	WHEN		AVAILTYPE	=	"Planned Availability" (146)							
11.00			CountOf	=>	1			RW	RW	RE		must have at least one "Planned Availability"
12.00			EFFECTIVEDATE	=>	LSEO	LSEOPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
13.00			COUNTRYLIST	in	LSEO	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: LSEO} {LD: COUNTRYLIST}
Add 20111214	13.20		L	WWSEOLSEO-u: MODELWWSEO-u: MODELAVAIL-d: AVAIL									
Add 20111214	13.22	WHEN		AVAILTYPE	=	"Planned Availability" (146)							
Add 20111214	13.24			A: COUNTRYLIST	"IN aggregate G"	L: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: A: AVAIL} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
Add 20111214	13.26	END	13.22										
									
14.00	END	10										
15.00	AVAIL	C	LSEOAVAIL-d								LSEO: AVAIL	
16.00	WHEN		AVAILTYPE	=	"First Order" (143)							
17.00			EFFECTIVEDATE	=>	LSEO	LSEOPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
18.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
19.00	END	16										
20.00	AVAIL	B	LSEOAVAIL-d								LSEO: AVAIL	
21.00	WHEN		AVAILTYPE	=	"Last Order"							
22.00			EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
23.00			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
24.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
25.00	END	21										
200.00	AVAIL		LSEOAVAIL-d								LSEO: AVAIL	
201.00	WHEN		AVAILTYPE	=	"End of Marketing" (200)							
Delete 20110318 202.00			EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
203.00			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
204.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
205.00	END	201										
206.00	AVAIL		LSEOAVAIL-d								LSEO: AVAIL	
207.00	WHEN		AVAILTYPE	=	"End of Service" (151)							
xx208.00			EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
Change 20111212	208.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
209.00			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
210.00			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
211.00	END	207										
26.00	MODEL		WWSEOLSEO-u: MODELWWSEO-u								MODEL	
27.00	WHEN		COFCAT	=	"Hardware" (100)						Hardware SEO	
28.00	PRODSTRUCT	D	WWSEOPRODSTRUCT-d								WWSEO: PRODSTRUCT	
28.20			ANNDATE	<=	LSEO	LSEOPUBDATEMTRGT		W	W	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: WWSEO} {NDN: D: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
28.40			WTHDRWEFFCTVDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: WWSEO} {NDN: D: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
29.00	PRODSTRUCT	E	LSEOPRODSTRUCT-d								LSEO: PRODSTRUCT	
30.00			1	<=	Count Of	D + E		W	E	E		must include at least one {LD: FEATURE}
30.20			ANNDATE	<=	LSEO	LSEOPUBDATEMTRGT		W	W	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: LSEO} {NDN: D: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
30.40			WTHDRWEFFCTVDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: LSEO} {NDN: D: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
31.00	AVAIL		LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								LSEO: PRODSTRUCT	
32.00	WHEN		AVAILTYPE	=	"Planned Availability"							
33.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
34.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
35.00	END	32										
xx36.00	AVAIL		LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								LSEO: PRODSTRUCT	
xx37.00	WHEN		AVAILTYPE	=	"Last Order"							
xx38.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
xx39.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
xx40.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
xx41.00	END	37
36.00	AVAIL	N	LSEOPRODSTRUCT-d: PRODSTRUCT								LSEO: PRODSTRUCT	
36.02			N: + OOFAVAIL-d									
37.00	WHEN		AVAILTYPE	=	"Last Order"							
38.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
39.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
39.02	IF		N: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
39.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
39.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
39.08	THEN											
39.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
39.12	ELSE	39.08										
40.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
40.02	END	39.08										
41.00	END	37.00										
									

										
										
42.00	AVAIL		WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								WWSEO: PRODSTRUCT	
43.00	WHEN		AVAILTYPE	=	"Planned Availability"							
44.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
45.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
46.00	END	43										
xx47.00	AVAIL		WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								WWSEO: PRODSTRUCT	
xx48.00	WHEN		AVAILTYPE	=	"Last Order"							
xx49.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
xx50.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
xx51.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
xx52.00	END	48	
47.00	AVAIL	P	WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT								WWSEO: PRODSTRUCT	
47.02			P: + OOFAVAIL-d									
48.00	WHEN		AVAILTYPE	=	"Last Order"							
49.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
50.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
50.02	IF		P: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
50.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
50.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
50.08	THEN											
50.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
50.12	ELSE	50.08										
51.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
51.02	END	50.08																			
52.00	END	48.00																		
									
53.00	END	27										
54.00	MODEL		WWSEOLSEO-u: MODELWWSEO-u									
55.00	WHEN		COFCAT	=	"Software" (101)						Software SEO	
56.00	SWPRODSTRUCT	F	WWSEOSWPRODSTRUCT-d								WWSEO: SWPRODSTRUCT	
57.00	SWPRODSTRUCT	G	LSEOSWPRODSTRUCT-d								LSEO: SWPRODSTRUCT	
58.00			1	<=	Count Of	F + G		W	E	E		must include at least one {LD: SWFEATURE}
59.00	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
60.00	WHEN		AVAILTYPE	=	"Planned Availability"							
61.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} must not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
62.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
63.00	END	60										
xx64.00	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
xx65.00	WHEN		AVAILTYPE	=	"Last Order"							
xx66.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
xx67.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
xx68.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
xx69.00	END	65	
64.00	AVAIL	Q	LSEOSWPRODSTRUCT-d:								LSEO: SWPRODSTRUCT	
64.02			Q: SWPRODSTRUCTAVAIL-d									
65.00	WHEN		AVAILTYPE	=	"Last Order"							
66.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
67.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
67.02	IF		Q: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
67.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
67.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
67.08	THEN											
67.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
67.12	ELSE	67.08										
68.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
68.02	END	67.08										
69.00	END	65.00										
									
70.00	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
71.00	WHEN		AVAILTYPE	=	"Planned Availability"							
72.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} must not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
73.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
74.00	END	71										
xx75.00	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
xx76.00	WHEN		AVAILTYPE	=	"Last Order"							
xx77.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
xx78.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
xx79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
xx80.00	END	76	
75.00	AVAIL	R	WWSEOLSEO-u: WWSEOSWPRODSTRUCT-d								WWSEO: SWPRODSTRUCT	
75.02			R: SWPRODSTRUCTAVAIL-d									
76.00	WHEN		AVAILTYPE	=	"Last Order"							
77.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
78.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
78.02	IF		R: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
78.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
78.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
78.08	THEN											
78.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
78.12	ELSE	78.08										
79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
79.02	END	78.08										
80.00	END	76.00										
										
									
81.00	END	55										
107.00	END	8										
108.00	LSEO		Root									
109.00	WWSEO		WWSEOLSEO-u									
110.00	WHEN		SPECBID	=	"Yes" (11458)						Special Bid	
111.00	AVAIL		LSEOAVAIL-d									
112.00			CountOf	=	0			RE	RE	RE		Special Bid can not have {LD: AVAIL}
112.20		M	WWSEOLSEO-u: MODELWWSEO-u: MODELAVAIL-d: AVAIL									
112.22	WHEN		M: AVAILTYPE	=	"Planned Availability" (146)							
112.24			LSEO.COUNTRYLIST	"IN aggregate G"	M: AVAIL	COUNTRYLIST		W	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
112.26	END	112.22										

113.00	MODEL		WWSEOLSEO-u: MODELWWSEO-u									
114.00	WHEN		COFCAT	=	"Hardware" (100)						Hardware LSEO	
300.00	PRODSTRUCT	J	LSEOPRODSTRUCT-d: PRODSTRUCT									
301.00	FEATURE		J: + PRODSTRUCT-d									
xx302.00	WHEN		"Primary FC (100) | ""Secondary FC"" (110)"	<>	FEATURE	FCTYPE						
xx303.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
xx304.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
xx305.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
xx306.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
xx307.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
xx308.00	END	302.00		
302.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE						
303.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
305.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
305.20	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO						
305.28	THEN											
305.30			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
305.60	ELSE	305.28										
304.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
306.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
306.80	END	305.28										
307.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
308.00	END	302.00												
								
309.00	WHEN		"Primary FC (100) | ""Secondary FC"" (110)"	=	FEATURE	FCTYPE						
115.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
116.00	WHEN		AVAILTYPE	=	"Planned Availability"							can an LSEO include RPQs?
117.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
118.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	E	E		{LD: COUNTRYLIST}  includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
119.00	END	116										
xx120.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
xx121.00	WHEN		AVAILTYPE	=	"Last Order"							
xx122.00	IF		COUNTRYLIST	Match	LSEO	COUNTRYLIST						
xx123.00	THEN		EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be earlier than{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
xx124.00	END	121	
120.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
121.00	WHEN		AVAILTYPE	=	"Last Order"							
122.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
122.02	AND		J: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
122.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
122.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
122.08	THEN											
122.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
122.12	ELSE	122.08										
123.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
123.02	END	122.08										
124.00	END	121.00																			
124.20	END	309		
								
320.00	PRODSTRUCT	K	WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT								WWSEOPRODSTRUCT	
xx321.00	FEATURE		K: + PRODSTRUCT-d								FEATURE	
xx322.00	WHEN		"Primary FC (100) | ""Secondary FC"" (110)"	<>	FEATURE	FCTYPE					RPQ	
xx323.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
xx324.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
xx325.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
xx326.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
xx327.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
Delete 20111214 327.20			COUNTRYLIST	"IN aggregate G"	K: + PRODSTRUCT-u: MODELAVAIL-d: AVAIL where AVAILTYPE = "Planned Availability"	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
xx328.00	END	322	

321.00	FEATURE		K: + PRODSTRUCT-d								FEATURE	
322.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE					RPQ	
323.00	nochg		LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
323.02	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO						
323.04	THEN											
323.06			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
323.08	ELSE	323.04										
324.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
324.02	END	323.04										
324.20			LSEOPUBDATEMTRGT	=>	MODEL	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
324.22			LSEOUNPUBDATEMTRGT	<=	MODEL	WTHDRWEFFCTVDATE		E	E	E	MODEL AVAIL Planned Availability COUNTRYLIST is checked in ID 112.24	{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: MODEL} {NDN: MODEL} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
325.00	nochg		LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
326.00	nochg		LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
327.00	nochg		COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
327.20	this is deleted		COUNTRYLIST	"INaggregate G"	K: + PRODSTRUCT-u: MODELAVAIL-d: AVAIL where AVAILTYPE = "Planned Availability"	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
328.00	END	322.00										
									
									
329.00	WHEN		"Primary FC (100) | ""Secondary FC"" (110)"	=	FEATURE	FCTYPE					GA	
125.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
126.00	WHEN		AVAILTYPE	=	"Planned Availability"							
127.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
128.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	E	E		{LD: COUNTRYLIST}  includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
129.00	END	126										
130.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
131.00	WHEN		AVAILTYPE	=	"Last Order"							
132.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
132.02	AND		K: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
132.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
132.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
132.08	THEN											
132.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
132.12	ELSE	132.08										
133.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
133.02	END	132.08										
134.00	END	131.00																			
									
134.20	END	329										
135.00	END	114										
136.00	MODEL		WWSEOLSEO-u: MODELWWSEO-u								Software LSEO	
137.00	WHEN		COFCAT	=	"Software" (101)							
138.00	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
139.00	WHEN		AVAILTYPE	=	"Planned Availability"							
140.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
141.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
142.00	END	139	
							
xx143.00	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
xx144.00	WHEN		AVAILTYPE	=	"Last Order"							
xx145.00	IF		COUNTRYLIST	Match	LSEO	COUNTRYLIST						
xx146.00	THEN		EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  can not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} referenced by the {LD: LSEO}
xx147.00	END	144		
143.00	AVAIL	S	LSEOSWPRODSTRUCT-d:								LSEO: SWPRODSTRUCT	
143.04			S: + SWPRODSTRUCTAVAIL-d									
144.00	WHEN		AVAILTYPE	=	"Last Order"							
145.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
145.02	AND		S: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
145.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
145.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
145.08	THEN											
145.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
145.40	ELSE	145.08										
146.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must  not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
146.40	END	145.08										
147.00	END	144.00										
								
148.00	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
149.00	WHEN		AVAILTYPE	=	"Planned Availability"							
150.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
151.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
152.00	END	149										
old153.00	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
old154.00	WHEN		AVAILTYPE	=	"Last Order"							
old155.00	IF		COUNTRYLIST	Match	LSEO	COUNTRYLIST						
old156.00	THEN		EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  can not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} referenced by the {LD: WWSEO}
old157.00	END	154		
153.00	AVAIL	T	WWSEOLSEO-u: WWSEOSWPRODSTRUCT-d:								WWSEO: SWPRODSTRUCT	
153.02			T: + SWPRODSTRUCTAVAIL-d									
154.00	WHEN		AVAILTYPE	=	"Last Order"							
155.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
155.02	AND		T: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
155.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
155.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
155.08	THEN											
155.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
155.12	ELSE	155.08										
156.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must  not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
156.02	END	155.08										
157.00	END	154.00										
										
								
158.00	END	137										
159.00	MODEL		WWSEOLSEO-u: MODELWWSEO-u								Service LSEO	
160.00	WHEN		COFCAT	=	"Service" (102)						No structure to check	
181.00	END	160										
182.00	END	110										
350.00	MODEL		WWSEO-u: MODELWWSEO-d								Ensure that HW = Prodstruct and SW = SWPRODSTRUCT and SVC has not features	
351.00	WHEN		COFCAT	=	"Hardware" (100)							
354.00			LSEOSWPRODSTRUCT-d									
355.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOSWPRODSTRUCT}
356.00	END	351										
357.00	WHEN		COFCAT	=	"Software" (101)							
260.00			LSEOPRODSTRUCT-d									
261.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOPRODSTRUCT}
262.00	END	357										
263.00	WHEN		COFCAT	=	"Service" (102)							
266.00			LSEOPRODSTRUCT-d									
267.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOPRODSTRUCT}
270.00			LSEOSWPRODSTRUCT-d									
271.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOSWPRODSTRUCT}
272.00	END	263										
										
	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    { 
    	addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");
    	
 		int checklvl = getCheck_W_W_E(statusFlag); 
		String prodhierFlag = PokUtils.getAttributeFlagValue(rootEntity, "BHPRODHIERCD");
		
		addDebug("checking "+rootEntity.getKey()+" (3) prodhierFlag "+prodhierFlag);
		
		//3.00			LSEOUNPUBDATEMTRGT	=>	LSEO	LSEOPUBDATEMTRGT		W	W	E		
		//{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} can not be earlier than {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
		checkCanNotBeEarlier(rootEntity, "LSEOUNPUBDATEMTRGT", "LSEOPUBDATEMTRGT", checklvl);	
    	
		//4.02			WWSEOLSEO-u: MODELWWSEO-u									
		//Delete 20110322 4.04	IF		"Service" (102)	<>	MODEL	COFCAT						
		//if(!isSvcModel && 
		//Delete for lenovo Remove - RCQ00337764
//		if(BHPRODHIERCD_No_ProdHCode.equals(prodhierFlag)){
//			//4.06			"00 - No Product Hierarchy Code" (BHPH0000)	<>	LSEO	BHPRODHIERCD		E	E	E		must not have {LD: BHPRODHIERCD} {BHPRODHIERCD}
//			//MUST_NOT_HAVE_ERR1= must not have {0}
//			args[0] = this.getLD_Value(rootEntity, "BHPRODHIERCD");
//			createMessage(CHECKLEVEL_E,"MUST_NOT_HAVE_ERR1",args);
//		}
		//Delete 20110322 4.08	END	4.04
		
		//5.00	WWSEO		WWSEOLSEO-u											
		int cnt = getCount("WWSEOLSEO");
		if(cnt == 1) {
			EntityItem wwseoItem = m_elist.getEntityGroup("WWSEO").getEntityItem(0);
			addDebug("checking "+wwseoItem.getKey()+" (6)");
			//6.00			STATUS	=>	LSEO	DATAQUALITY		E	E	E		
			//{LD: STATUS} can not be higher than the {LD: WWSEO} {NDN: WWSEO} {LD: STATUS} {STATUS}
			checkStatusVsDQ(wwseoItem, "STATUS", rootEntity,CHECKLEVEL_E);
			
			String specbid = getAttributeFlagEnabledValue(wwseoItem, "SPECBID");
			addDebug(wwseoItem.getKey()+" SPECBID: "+specbid);
			if (SPECBID_NO.equals(specbid)){  // is No
				doGAChecks(rootEntity, wwseoItem, statusFlag);
			}else{
				doSpecBidChecks(rootEntity, wwseoItem, statusFlag);
			}
			
	    	Vector mdlvct =PokUtils.getAllLinkedEntities(wwseoItem, "MODELWWSEO", "MODEL");
			for (int m=0; m<mdlvct.size(); m++){ // there should really only be one
				EntityItem mdlItem = (EntityItem)mdlvct.elementAt(m);
				String modelCOFCAT = getAttributeFlagEnabledValue(mdlItem, "COFCAT");
				//boolean isSvcModel = SERVICE.equals(modelCOFCAT);
				addDebug(mdlItem.getKey()+" modelCOFCAT: "+modelCOFCAT);//+" isSvcModel "+isSvcModel);
				
				if(HARDWARE.equals(modelCOFCAT)){
					//350.00
					//351.00	WHEN		COFCAT	=	"Hardware" (100)							
					//354.00			LSEOSWPRODSTRUCT-d									
					//355.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOSWPRODSTRUCT}
					checkProdstructs(rootEntity, mdlItem,"WWSEOSWPRODSTRUCT","LSEOSWPRODSTRUCT");
					//356.00	END	351.00		
				} else if(SOFTWARE.equals(modelCOFCAT)){
					//357.00	WHEN		COFCAT	=	"Software" (101)							
					//260.00			LSEOPRODSTRUCT-d									
					//261.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOPRODSTRUCT}
					checkProdstructs(rootEntity, mdlItem,"WWSEOPRODSTRUCT","LSEOPRODSTRUCT");
					//262.00	END	357.00				
				} else if(SERVICE.equals(modelCOFCAT)){
					//263.00	WHEN		COFCAT	=	"Service" (102)							
					//266.00			LSEOPRODSTRUCT-d									
					//267.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOPRODSTRUCT}
					checkProdstructs(rootEntity, mdlItem,"WWSEOPRODSTRUCT","LSEOPRODSTRUCT");
					//270.00			LSEOSWPRODSTRUCT-d									
					//271.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOSWPRODSTRUCT}
					checkProdstructs(rootEntity, mdlItem,"WWSEOSWPRODSTRUCT","LSEOSWPRODSTRUCT");
					//272.00	END	263.00	
				}
			}

			mdlvct.clear();
		}else{ // must have 1 WWSEO
			//7.00		CountOf	=	1			E	E	E		must have only one parent {LD: WWSEO}
			EntityGroup eGrp = m_elist.getEntityGroup("WWSEO");
			args[0] = eGrp.getLongDescription();
			//REQUIRES_ONE_PARENT_ERR = must have only one parent {0}
			createMessage(CHECKLEVEL_E,"REQUIRES_ONE_PARENT_ERR",args);
		}
    }

    /**
     * Ensure that HW = Prodstruct and SW = SWPRODSTRUCT and SVC has not features
     * @param rootEntity
     * @param mdlItem
     * @param wwseoRelatorType
     * @param lseoRelatorType
     * @throws MiddlewareException
     */
    private void checkProdstructs(EntityItem rootEntity, EntityItem mdlItem,
    		String wwseoRelatorType, String lseoRelatorType) throws MiddlewareException{
		addHeading(3,m_elist.getParentEntityGroup().getLongDescription()+" "+
				m_elist.getEntityGroup(lseoRelatorType).getLongDescription()+" Checks:");
		int cnt = getCount(lseoRelatorType);
		if(cnt>0){ 
			//255.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOSWPRODSTRUCT}
			//261.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOPRODSTRUCT}
			//267.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOPRODSTRUCT}
			//271.00			CountOf	=	0			E	E	E		{COFCAT} {LD: LSEO} can not have {LD: LSEOSWPRODSTRUCT}
			EntityGroup eGrp = m_elist.getParentEntityGroup();
			args[0] = PokUtils.getAttributeValue(mdlItem, "COFCAT", "", "",false);
			args[1] = eGrp.getLongDescription();
			args[2] = m_elist.getEntityGroup(lseoRelatorType).getLongDescription();
			//PSLINK_ERR = a {0} {1} can not have a {2}
			createMessage(CHECKLEVEL_E,"PSLINK_ERR",args);
		}
    }
    
    //====================================================================================
    //========= SPECBID section ======================================
    //====================================================================================
    /***********************************
     * do checks when WWSEO is a specialbid
     * 
     * @param rootEntity
     * @param wwseoItem
     * @param statusFlag
     * @throws Exception
     * 
108.00	LSEO		Root									
109.00	WWSEO		WWSEOLSEO-u									
110.00	WHEN		SPECBID	=	"Yes" (11458)						Special Bid	
111.00	AVAIL		LSEOAVAIL-d									
112.00			CountOf	=	0			RE	RE	RE		Special Bid can not have {LD: AVAIL}

check hw model
									
check sw model
									
check svc model 
									
182.00	END	110.00			
     */
    private void doSpecBidChecks(EntityItem rootEntity, EntityItem wwseoItem, String statusFlag) throws Exception
    {
    	addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Special Bid Checks:");
		//111.00	AVAIL		LSEOAVAIL-d									
		//112.00			CountOf	=	0			RE	RE	RE		Special Bid can not have {LD: AVAIL}
		//SPECBID_AVAIL_ERR = Special Bid can not have {0}
		int cnt = getCount("LSEOAVAIL");
		if (cnt!=0){
			args[0] = m_elist.getEntityGroup("AVAIL").getLongDescription();
			createMessage(CHECKLEVEL_RE,"SPECBID_AVAIL_ERR",args);
		}
    	int checklvl = getCheck_W_E_E(statusFlag);
		
		String lseopubdate = getAttrValueAndCheckLvl(rootEntity, "LSEOPUBDATEMTRGT", checklvl);
		String lseounpubdate = getAttrValueAndCheckLvl(rootEntity, "LSEOUNPUBDATEMTRGT", checklvl);
		
		ArrayList lseoCtry = new ArrayList();
		getCountriesAsList(rootEntity, lseoCtry, getCheck_W_RE_RE(statusFlag));
		addDebug("doSpecBidChecks lseoCtry "+lseoCtry);
		
       	Vector mdlvct =PokUtils.getAllLinkedEntities(wwseoItem, "MODELWWSEO", "MODEL");
		
       	// LSEO ctrys must be a subset of the MODEL.PLA ctrys
       	if(mdlvct.size()>0){
       		//112.20		M	WWSEOLSEO-u: MODELWWSEO-u: MODELAVAIL-d: AVAIL															
       		EntityItem mdlitem = (EntityItem)mdlvct.firstElement();
       		EntityList mdllist = getModelVE(mdlitem);
       		mdlitem = mdllist.getParentEntityGroup().getEntityItem(0); // get the model with avail links
       		Vector availVct = PokUtils.getAllLinkedEntities(mdlitem, "MODELAVAIL", "AVAIL");
       		//112.22	WHEN		M: AVAILTYPE	=	"Planned Availability" (146)
       		Vector mdlPlaVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
       		addDebug("doSpecBidChecks "+mdlitem.getKey()+" mdlavailVct: "+availVct.size()+" mdlPlaVct "+
       				mdlPlaVct.size());
       		if(mdlPlaVct.size()>0){
       			// get model plannedavail countries
       			ArrayList mdlplaCtry = getCountriesAsList(mdlPlaVct, checklvl);
       			addDebug("doSpecBidChecks  mdlplaCtry "+mdlplaCtry);

       			//112.24			LSEO.COUNTRYLIST	"IN aggregate G"	M: AVAIL	COUNTRYLIST		W	E	E		
       			//{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
       			if(!mdlplaCtry.containsAll(lseoCtry)){
       				EntityItem avail  = (EntityItem)mdlPlaVct.firstElement();
       				//INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
       				ArrayList tmp = new ArrayList();
       				tmp.addAll(lseoCtry);
       				tmp.removeAll(mdlplaCtry); // these are the extra ctrys

       				args[0]="";
       				args[1]="";
       				args[2]=getLD_NDN(mdlitem)+" "+
       				avail.getEntityGroup().getLongDescription();
       				args[3]="Planned Availability";
       				args[4]=getUnmatchedDescriptions(avail, "COUNTRYLIST",tmp);
       				createMessage(checklvl,"INCLUDE_ERR2",args);
       				tmp.clear();
       			}
           		mdlplaCtry.clear();
       		}

       		availVct.clear();
       		mdlPlaVct.clear();
       		mdllist.dereference();

       		//112.26	END	112.22	
       	}
		
		for (int m=0; m<mdlvct.size(); m++){ // there should really only be one
			EntityItem mdlItem = (EntityItem)mdlvct.elementAt(m);
			String modelCOFCAT = getAttributeFlagEnabledValue(mdlItem, "COFCAT");
			addDebug("doSpecBidChecks "+mdlItem.getKey()+" SPECBID COFCAT: "+modelCOFCAT);

			if(HARDWARE.equals(modelCOFCAT)){
		    	//113.00	MODEL		WWSEOLSEO-u: MODELWWSEO-d									
				//114.00	WHEN		COFCAT	=	"Hardware" (100)						Hardware LSEO
				 doHWSpecBidChecks(rootEntity,  lseopubdate,  lseounpubdate, lseoCtry,wwseoItem,statusFlag);
			} else if(SOFTWARE.equals(modelCOFCAT)){
				//136.00	MODEL		WWSEO-u: MODELWWSEO-d			
				//137.00	WHEN		COFCAT	=	"Software" (101)						Software SEO	
				doSWSpecBidChecks(rootEntity, lseopubdate,  lseounpubdate, lseoCtry,wwseoItem,statusFlag);
			} else if(SERVICE.equals(modelCOFCAT)){
				//159.00	MODEL		WWSEO-u: MODELWWSEO-d								Service LSEO	
				//160.00	WHEN		COFCAT	=	"Service" (102)		
				//deleted doSVCSpecBidChecks(rootEntity, lseopubdate,  lseounpubdate, lseoCtry,wwseoItem,statusFlag);
				//181.00	END	160.00
			}
		}

		mdlvct.clear();	
		lseoCtry.clear();
    }

    /*************
     * 
     * @param rootEntity
     * @param wwseoItem
     * @param statusFlag
     * @throws Exception
     * 
113.00	MODEL		WWSEOLSEO-u: MODELWWSEO-u									
114.00	WHEN		COFCAT	=	"Hardware" (100)						Hardware LSEO	
300.00	PRODSTRUCT	J	LSEOPRODSTRUCT-d: PRODSTRUCT									
301.00	FEATURE		J: + PRODSTRUCT-d									
xx302.00	WHEN		"Primary FC (100) | ""Secondary FC"" (110)"	<>	FEATURE	FCTYPE						
xx303.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
xx304.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
xx305.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
xx306.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
xx307.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
xx308.00	END	302.00	
302.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE						
303.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
305.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
305.20	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO						
305.28	THEN											
305.30			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
305.60	ELSE	305.28										
304.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
306.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
306.80	END	305.28										
307.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
308.00	END	302.00												
309.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	=	FEATURE	FCTYPE						
115.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
116.00	WHEN		AVAILTYPE	=	"Planned Availability"							can an LSEO include RPQs?
117.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
118.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	E	E		{LD: COUNTRYLIST}  includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
119.00	END	116										
xx120.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
xx121.00	WHEN		AVAILTYPE	=	"Last Order"							
xx122.00	IF		COUNTRYLIST	Match	LSEO	COUNTRYLIST						
xx123.00	THEN		EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be earlier than{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
xx124.00	END	121		
120.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
121.00	WHEN		AVAILTYPE	=	"Last Order"							
122.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
122.02	AND		J: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
122.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
122.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
122.08	THEN											
122.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
122.12	ELSE	122.08										
123.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
123.02	END	122.08										
124.00	END	121.00										
									
								
124.20	END	309										
320.00	PRODSTRUCT	K	WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT 						WWSEOPRODSTRUCT	
xx321.00	FEATURE		K: + PRODSTRUCT-d							FEATURE	
xx322.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE					RPQ	
xx323.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
xx324.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
xx325.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
xx326.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
xx327.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
Delete 20111214 327.20		COUNTRYLIST	"IN aggregate G"	K: + PRODSTRUCT-u: MODELAVAIL-d: AVAIL where AVAILTYPE = "Planned Availability"	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
xx328.00	END	322.00	
321.00	FEATURE		K: + PRODSTRUCT-d								FEATURE	
322.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE					RPQ	
323.00	nochg		LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
323.02	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO						
323.04	THEN											
323.06			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
323.08	ELSE	323.04										
324.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
324.02	END	323.04										
324.20			LSEOPUBDATEMTRGT	=>	MODEL	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
324.22			LSEOUNPUBDATEMTRGT	<=	MODEL	WTHDRWEFFCTVDATE		E	E	E	MODEL AVAIL Planned Availability COUNTRYLIST is checked in ID 112.24	{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: MODEL} {NDN: MODEL} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
325.00	nochg		LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
326.00	nochg		LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
327.00	nochg		COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
327.20	this is deleted		COUNTRYLIST	"INaggregate G"	K: + PRODSTRUCT-u: MODELAVAIL-d: AVAIL where AVAILTYPE = "Planned Availability"	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
328.00	END	322.00	
									
329.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	=	FEATURE	FCTYPE					GA	
125.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
126.00	WHEN		AVAILTYPE	=	"Planned Availability"							
127.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
128.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	E	E		{LD: COUNTRYLIST}  includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
129.00	END	126										
130.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
131.00	WHEN		AVAILTYPE	=	"Last Order"							
132.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
132.02	AND		K: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
132.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
132.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
132.08	THEN											
132.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
132.12	ELSE	132.08										
133.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
133.02	END	132.08										
134.00	END	131.00										
									
										
134.20	END	329										
135.00	END	114										

     */
    private void doHWSpecBidChecks(EntityItem rootEntity, String lseopubdate,
    		String lseounpubdate,ArrayList lseoCtry,EntityItem wwseoItem,String statusFlag) throws Exception
    {
		Vector wwseopsVct = PokUtils.getAllLinkedEntities(wwseoItem, "WWSEOPRODSTRUCT", "PRODSTRUCT");
		Vector lseopsVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOPRODSTRUCT", "PRODSTRUCT");
		addDebug("doHWSpecBidChecks wwseopsVct "+wwseopsVct.size()+" lseopsVct "+lseopsVct.size());
		//30			1	<=	Count Of	D + E		W	E	E		must include at least one {LD: FEATURE}
		if (wwseopsVct.size()+lseopsVct.size() ==0){
			args[0] = m_elist.getEntityGroup("FEATURE").getLongDescription();
			//MINIMUM_ERR = must have at least one {0}
			createMessage(getCheck_W_E_E(statusFlag),"MINIMUM_ERR",args);
		}

		addHeading(3,"Hardware LSEO Special Bid Avail Checks:");
		addDebug("\ndoHWSpecBidChecks lseops chks");
		//AVAIL		LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
		checkHWSpecBidPsAvails(rootEntity, lseopsVct, lseopubdate, lseounpubdate, lseoCtry, false, statusFlag);
		addHeading(3,"Hardware WWSEO Special Bid Avail Checks:");
		addDebug("\ndoHWSpecBidChecks wwseops chks");
		//WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL
		checkHWSpecBidPsAvails(rootEntity, wwseopsVct, lseopubdate, lseounpubdate, lseoCtry, true, statusFlag);
		
		wwseopsVct.clear();
		lseopsVct.clear();
    }
    /***
     * 
     * @param rootEntity
     * @param psVct
     * @param psAvailRelType
     * @param statusFlag
     * @throws MiddlewareException
     * @throws SQLException
LSEO
138	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT		
139	WHEN		AVAILTYPE	=	"Planned Availability"							
140			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
141			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
142	END	139		
								
old143	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
old144	WHEN		AVAILTYPE	=	"Last Order"							
old145		IF	COUNTRYLIST	Match	LSEO	COUNTRYLIST		RE	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
old146		THEN	EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  can not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
old147	END	144										

++++++++++++++++++++++++++++++++++++++
WWSEO
148	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
149	WHEN		AVAILTYPE	=	"Planned Availability"							
150			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
151			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
152	END	149		
								
old153	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
old154	WHEN		AVAILTYPE	=	"Last Order"							
old155		IF	COUNTRYLIST	Match	LSEO	COUNTRYLIST		RE	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
old156		THEN	EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  can not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
old157	END	154	

     */
    private void checkSpecBidPsAvails(EntityItem rootEntity, Vector psVct, String psType, String psAvailRelType, 
    		String lseopubdate, String lseounpubdate, ArrayList lseoCtry, String statusFlag) 
    throws MiddlewareException, SQLException
    {
    	Vector psAvailVct = PokUtils.getAllLinkedEntities(psVct, psAvailRelType, "AVAIL");
    	// remove any that are not AVAILANNTYPE=RFA so they arent in the other checks
    	//removeNonRFAAVAIL(psAvailVct); 
    	
    	Vector psPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(psAvailVct, "AVAILTYPE", PLANNEDAVAIL);
     	ArrayList allPsPlaAvailCtry = getCountriesAsList(psPlaAvailVct, getCheck_W_RE_RE(statusFlag));
   		
    	addDebug("checkSpecBidPsAvails "+psType+"-"+psAvailRelType+" psAvailVct: "+
    			psAvailVct.size()+" psPlaAvailVct: "+psPlaAvailVct.size()+" allPsPlaAvailCtry "+allPsPlaAvailCtry);

    	if (psPlaAvailVct.size()>0){
    		//138	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT		
    		//148	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
    		//139	WHEN		AVAILTYPE	=	"Planned Availability"							
    		for (int i=0; i<psPlaAvailVct.size(); i++){
    			EntityItem psPlaAvail = (EntityItem)psPlaAvailVct.elementAt(i); 
    			EntityItem psitem = getAvailPS(psPlaAvail, psAvailRelType);
    			// this is by country now
         		if(hasAnyCountryMatch(psPlaAvail,lseoCtry)){
//         			141.01	ANNOUNCEMENT		ANNAVAILA																																																																																																																																																																																																																																																				
//         			141.02	WHEN		ANNTYPE	=	"New"																																																																																																																																																																																																																																																		
//         		Change Column E	141.03	ANNOUNCEMENT		ANNDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}																																																																																																																																																																																																																																											
//         			151.01	ANNOUNCEMENT		ANNAVAILA																																																																																																																																																																																																																																																				
//         			151.02	WHEN		ANNTYPE	=	"New"																																																																																																																																																																																																																																																		
//         		Change Column E	151.03			ANNDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than  {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}																																																																																																																																																																																																																																											
         			checkDateforPlanAvailToLSEO(rootEntity, statusFlag, psPlaAvail);
         			//140			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
         			//150			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
//         			checkDates(rootEntity, "LSEOPUBDATEMTRGT", lseopubdate,psitem, psPlaAvail,getCheck_W_E_E(statusFlag), 
//    					DATE_GR_EQ);
         		}else{
         	    	ArrayList availCtrylist = new ArrayList();
         	    	// fill in list with the avail countries
         	    	getCountriesAsList(psPlaAvail, availCtrylist,CHECKLEVEL_NOOP);
        			addDebug("checkSpecBidPsAvails skipping date check "+psPlaAvail.getKey()+" availCtrylist: "+availCtrylist+
        					" did not contain any lseoCtry: "+lseoCtry);
        			availCtrylist.clear();
         		}
    		}
    		
    		// this is the entire set of LSEOAVAIL.COUNTRYLIST must have LSEO.COUNTRYLIST not per LSEOAVAIL
			//141			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
    		//151			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
    		if (!allPsPlaAvailCtry.containsAll(lseoCtry)){
    			addDebug("checkSpecBidPsAvails  allPsPlaAvailCtry: "+allPsPlaAvailCtry+
						" did not contain all lseoCtry: "+lseoCtry);
    			// extra ctry on lseo 
    			//EXTRA_CTRY_ERR = {0} includes a Country that is not in {1} {2} Extra countries: {3}
    			//{LD: COUNTRYLIST} includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}    	
    			args[0]=PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
    	   		for (int i=0; i<psPlaAvailVct.size(); i++){
        			EntityItem psPlaAvail = (EntityItem)psPlaAvailVct.elementAt(i); 
        			EntityItem psitem = getAvailPS(psPlaAvail, psAvailRelType);
        	    	ArrayList availCtrylist = new ArrayList();
         	    	// fill in list with the avail countries
         	    	getCountriesAsList(psPlaAvail, availCtrylist,CHECKLEVEL_NOOP);
         	    	if (!availCtrylist.containsAll(lseoCtry)){
         	    		addDebug("checkSpecBidPsAvails psPlaAvail "+psPlaAvail.getKey()+" availCtrylist: "+availCtrylist+
         	    				" did not contain all lseoCtry: "+lseoCtry);
            			args[1]=getLD_NDN(psitem);
            			args[2]=getLD_NDN(psPlaAvail);
            			ArrayList tmp = (ArrayList)lseoCtry.clone();
            			tmp.removeAll(availCtrylist);
    					args[3]=getUnmatchedDescriptions(psPlaAvail, "COUNTRYLIST",tmp);
            			createMessage(getCheck_W_RE_RE(statusFlag),"EXTRA_CTRY_ERR",args);
         	    	}
        			availCtrylist.clear();
    	   		}
    		}
    
    		//142	END	139	
    		//152	END	149	
    		psAvailVct.clear();
    		psPlaAvailVct.clear();
    		allPsPlaAvailCtry.clear();
    	}
								
								
    	//143	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
    	//153	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d					WWSEO: SWPRODSTRUCT
    	//144	WHEN		AVAILTYPE	=	"Last Order"							
    	/*old Vector psLoAvailVct = PokUtils.getEntitiesWithMatchedAttr(psAvailVct, "AVAILTYPE", LASTORDERAVAIL);
    	Hashtable psLoAvailCtryTbl = getAvailByCountry(psLoAvailVct, CHECKLEVEL_RE);
    	addDebug("checkSpecBidPsAvails psLoAvailVct "+psLoAvailVct.size()+" psLoAvailCtryTbl: "+psLoAvailCtryTbl.keySet());
    	if(psLoAvailVct.size()>0){
    		Vector checkedAvailVct = new Vector();
    		Iterator itr = lseoCtry.iterator();
    		while (itr.hasNext()) {
    			String ctryflag = (String) itr.next();
    			EntityItem psloAvail = (EntityItem)psLoAvailCtryTbl.get(ctryflag);
    			//145	IF	COUNTRYLIST	Match	LSEO	COUNTRYLIST		RE	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
    			if(psloAvail!=null){
    				if (checkedAvailVct.contains(psloAvail)){
    					continue;
    				}
    				//146		THEN	EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  can not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
    				//156		THEN	EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  can not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
    				checkedAvailVct.add(psloAvail);
    				EntityItem psitem = getAvailPS(psloAvail,psAvailRelType);
    				checkDates(rootEntity, "LSEOUNPUBDATEMTRGT", lseounpubdate,psitem, psloAvail,
    						CHECKLEVEL_RE, DATE_LT_EQ);  
    			}
    		}
    		//147	END	144	
    		//157	END	154
    		
    		checkedAvailVct.clear();
        	psLoAvailVct.clear();
    		psLoAvailCtryTbl.clear();
    	}*/

		//release memory

       	psAvailVct.clear();
    }
    /****************
     * 
     * @param rootEntity
     * @param psVct
     * @param psType
     * @param psAvailRelType
     * @param lseopubdate
     * @param lseounpubdate
     * @param lseoCtry
     * @param statusFlag
     * @param refby
     * @throws MiddlewareException
     * @throws SQLException
     * 
113.00	MODEL		WWSEOLSEO-u: MODELWWSEO-u									
114.00	WHEN		COFCAT	=	"Hardware" (100)						Hardware LSEO	
300.00	PRODSTRUCT	J	LSEOPRODSTRUCT-d: PRODSTRUCT									
301.00	FEATURE		J: + PRODSTRUCT-d									
xx302.00	WHEN		"Primary FC (100) | ""Secondary FC"" (110)"	<>	FEATURE	FCTYPE						
xx303.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
xx304.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
xx305.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
xx306.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
xx307.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
xx308.00	END	302.00
302.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE						
303.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
305.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
305.20	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO						
305.28	THEN											
305.30			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
305.60	ELSE	305.28										
304.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
306.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
306.80	END	305.28										
307.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
308.00	END	302.00	
											
309.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	=	FEATURE	FCTYPE						
115.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
116.00	WHEN		AVAILTYPE	=	"Planned Availability"							can an LSEO include RPQs?
117.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
118.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	E	E		{LD: COUNTRYLIST}  includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
119.00	END	116										
xx120.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
xx121.00	WHEN		AVAILTYPE	=	"Last Order"							
xx122.00	IF		COUNTRYLIST	Match	LSEO	COUNTRYLIST						
xx123.00	THEN		EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be earlier than{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
xx124.00	END	121	
120.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
121.00	WHEN		AVAILTYPE	=	"Last Order"							
122.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
122.02	AND		J: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
122.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
122.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
122.08	THEN											
122.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
122.12	ELSE	122.08										
123.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
123.02	END	122.08										
124.00	END	121.00																			
124.20	END	309	
										
320.00	PRODSTRUCT	K	WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT 						WWSEOPRODSTRUCT	
xx321.00	FEATURE		K: + PRODSTRUCT-d							FEATURE	
xx322.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE					RPQ	
xx323.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
xx324.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
xx325.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
xx326.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
xx327.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
Delete 20111214 327.20		COUNTRYLIST	"IN aggregate G"	K: + PRODSTRUCT-u: MODELAVAIL-d: AVAIL where AVAILTYPE = "Planned Availability"	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
xx328.00	END	322.00	
	
321.00	FEATURE		K: + PRODSTRUCT-d								FEATURE	
322.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE					RPQ	
323.00	nochg		LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
323.02	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO						
323.04	THEN											
323.06			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
323.08	ELSE	323.04										
324.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
324.02	END	323.04										
324.20			LSEOPUBDATEMTRGT	=>	MODEL	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
324.22			LSEOUNPUBDATEMTRGT	<=	MODEL	WTHDRWEFFCTVDATE		E	E	E	MODEL AVAIL Planned Availability COUNTRYLIST is checked in ID 112.24	{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: MODEL} {NDN: MODEL} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
325.00	nochg		LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
326.00	nochg		LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
327.00	nochg		COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
327.20	this is deleted		COUNTRYLIST	"INaggregate G"	K: + PRODSTRUCT-u: MODELAVAIL-d: AVAIL where AVAILTYPE = "Planned Availability"	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
328.00	END	322.00	
							
329.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	=	FEATURE	FCTYPE					GA	
125.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
126.00	WHEN		AVAILTYPE	=	"Planned Availability"							
127.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
128.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	E	E		{LD: COUNTRYLIST}  includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
129.00	END	126										
130.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
131.00	WHEN		AVAILTYPE	=	"Last Order"							
132.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
132.02	AND		K: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
132.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
132.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
132.08	THEN											
132.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
132.12	ELSE	132.08										
133.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
133.02	END	132.08										
134.00	END	131.00																			
									
134.20	END	329										
135.00	END	114										

     */
    private void checkHWSpecBidPsAvails(EntityItem rootEntity, Vector psVct, 
    		String lseopubdate, String lseounpubdate, ArrayList lseoCtry, boolean thruWWSEOPS, String statusFlag) 
    throws MiddlewareException, SQLException
    {
    	//loop thru ps and check feature fctype
    	for (int i=0; i<psVct.size(); i++){
    		EntityItem psitem = (EntityItem)psVct.elementAt(i); 
    		EntityItem featitem = getUpLinkEntityItem(psitem, "FEATURE");
    		addDebug("checkHWSpecBidPsAvails  "+psitem.getKey()+" "+featitem.getKey());

      		if (!isRPQ(featitem)){
    			addDebug(featitem.getKey()+" was NOT an RPQ FCTYPE: "+getAttributeFlagEnabledValue(featitem, "FCTYPE"));
    			checkNonRPQAvails(rootEntity, psitem,lseopubdate, lseounpubdate, lseoCtry, statusFlag);
    		}else{
    			addDebug(featitem.getKey()+" was an RPQ FCTYPE: "+getAttributeFlagEnabledValue(featitem, "FCTYPE"));
    			//302.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE						
    			//303.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
    			//305.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
    			//305.20	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO						
    			//305.28	THEN											
    			//305.30			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
    			//305.60	ELSE	305.28										
    			//304.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
    			//306.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
    			//306.80	END	305.28										
    			//307.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
    		
    			//320.00	PRODSTRUCT	K	WWSEOLSEO-u: WWSEOPRODSTRUCT-d: PRODSTRUCT
    			//321.00	FEATURE		K: + PRODSTRUCT-d								FEATURE	
    			//322.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE					RPQ	
    			//323.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
    			//323.02	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO						
    			//323.04	THEN											
    			//323.06			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
    			//323.08	ELSE	323.04										
    			//324.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
    			//324.02	END	323.04										
    			//324.20			LSEOPUBDATEMTRGT	=>	MODEL	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
    			//324.22			LSEOUNPUBDATEMTRGT	<=	MODEL	WTHDRWEFFCTVDATE		E	E	E	MODEL AVAIL Planned Availability COUNTRYLIST is checked in ID 112.24	{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: MODEL} {NDN: MODEL} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
    			//325.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
    			//326.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
    			//327.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
 			
    			checkRPQFeature(rootEntity, psitem, featitem, thruWWSEOPS);
    			//308.00	END	302.00	
    			//328.00	END	322.00	
    		}
    	}
    }

    /***************
     * 
     * @param rootEntity
     * @param psitem
     * @param lseopubdate
     * @param lseounpubdate
     * @param lseoCtry
     * @param statusFlag
     * @throws MiddlewareException
     * @throws SQLException
     * 
309.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	=	FEATURE	FCTYPE						
115.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
116.00	WHEN		AVAILTYPE	=	"Planned Availability"							can an LSEO include RPQs?
117.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
118.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	E	E		{LD: COUNTRYLIST}  includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
119.00	END	116										
			
120.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
121.00	WHEN		AVAILTYPE	=	"Last Order"							
122.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
122.02	AND		J: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
122.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
122.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
122.08	THEN											
122.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
122.12	ELSE	122.08										
123.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
123.02	END	122.08										
124.00	END	121.00										
									
									
124.20	END	309	

329.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	=	FEATURE	FCTYPE					GA
125.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
126.00	WHEN		AVAILTYPE	=	"Planned Availability"							
127.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
128.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	E	E		{LD: COUNTRYLIST}  includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
129.00	END	126										
130.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
131.00	WHEN		AVAILTYPE	=	"Last Order"							
132.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
132.02	AND		K: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
132.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
132.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
132.08	THEN											
132.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
132.12	ELSE	132.08										
133.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
133.02	END	132.08										
134.00	END	131.00																			
									
134.20	END	329										
135.00	END	114	
     */
    private void checkNonRPQAvails(EntityItem rootEntity, EntityItem psitem, 
    		String lseopubdate, String lseounpubdate, ArrayList lseoCtry, String statusFlag) 
    throws MiddlewareException, SQLException
    {			
    	Vector psAvailVct = PokUtils.getAllLinkedEntities(psitem, "OOFAVAIL", "AVAIL");
    	// remove any that are not AVAILANNTYPE=RFA so they arent in the other checks
    	//removeNonRFAAVAIL(psAvailVct); 

    	Vector psPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(psAvailVct, "AVAILTYPE", PLANNEDAVAIL);
    	ArrayList allPsPlaAvailCtry = getCountriesAsList(psPlaAvailVct, getCheck_W_E_E(statusFlag));

    	addDebug("checkNonRPQAvails  psAvailVct: "+
    			psAvailVct.size()+" psPlaAvailVct: "+psPlaAvailVct.size()+" allPsPlaAvailCtry "+allPsPlaAvailCtry);
    	if (psPlaAvailVct.size()>0){
    		//115.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
    		//125.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
    		//116.00	WHEN		AVAILTYPE	=	"Planned Availability"							
    		for (int i=0; i<psPlaAvailVct.size(); i++){
    			EntityItem psPlaAvail = (EntityItem)psPlaAvailVct.elementAt(i); 
    			// this is by country now
    			if(hasAnyCountryMatch(psPlaAvail,lseoCtry)){
//    				Add	118.01	ANNOUNCEMENT		ANNAVAILA																																																																																																																																																																																																																																																		
//    				Add	118.02	WHEN		ANNTYPE	=	"New"																																																																																																																																																																																																																																																
//    				Change Column E &ID	118.03			ANNDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: ANNOUNCEMENT} {NDN:ANNOUNCEMENT}																																																																																																																																																																																																																																									
//    				128.01	ANNOUNCEMENT		ANNAVAILA																																																																																																																																																																																																																																																				
//    				128.02	WHEN		ANNTYPE	=	"New"																																																																																																																																																																																																																																																		
//    			Change Column E	128.03	ANNOUNCEMENT		ANNDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than  {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}																																																																																																																																																																																																																																											
    				addDebug("checkNonRPQAvails skipping date check: has matched countries ");
    				checkDateforPlanAvailToLSEO(rootEntity, statusFlag, psPlaAvail);
    				//117.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E	{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
    				//127.00			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E	{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
//    				checkDates(rootEntity, "LSEOPUBDATEMTRGT", lseopubdate,psitem, psPlaAvail,getCheck_W_E_E(statusFlag), 
//    						DATE_GR_EQ);
    			}else{
    				ArrayList availCtrylist = new ArrayList();
    				// fill in list with the avail countries
    				getCountriesAsList(psPlaAvail, availCtrylist,CHECKLEVEL_NOOP);
    				addDebug("checkNonRPQAvails skipping date check "+psPlaAvail.getKey()+" availCtrylist: "+availCtrylist+
    						" did not contain any lseoCtry: "+lseoCtry);
    				availCtrylist.clear();
    			}
    		}

    		// this is the entire set of PSAVAIL.COUNTRYLISTs, not per avail
    		//118.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	E	E		{LD: COUNTRYLIST}  includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
    		//128.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	E	E		{LD: COUNTRYLIST}  includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL} 
    		if (!allPsPlaAvailCtry.containsAll(lseoCtry)){
    			addDebug("checkNonRPQAvails allPsPlaAvailCtry "+allPsPlaAvailCtry+
    					" did not contain all lseoCtry: "+lseoCtry);
    			// extra ctry on lseo 
    			//EXTRA_CTRY_ERR = {0} includes a Country that is not in {1} {2} Extra countries: {3}
    			//{LD: COUNTRYLIST} includes a Country that is not in {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}    	
    			args[0]=PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");

    			for (int i=0; i<psPlaAvailVct.size(); i++){
    				EntityItem psPlaAvail = (EntityItem)psPlaAvailVct.elementAt(i); 
    				ArrayList availCtrylist = new ArrayList();
    				// fill in list with the avail countries
    				getCountriesAsList(psPlaAvail, availCtrylist,CHECKLEVEL_NOOP);
    				if (!availCtrylist.containsAll(lseoCtry)){
    					addDebug("checkNonRPQAvails "+psPlaAvail.getKey()+" availCtrylist: "+availCtrylist+
    							" did not contain all lseoCtry: "+lseoCtry);
    					args[1]=getLD_NDN(psitem);
    					args[2]=getLD_NDN(psPlaAvail);
              			ArrayList tmp = (ArrayList)lseoCtry.clone();
            			tmp.removeAll(availCtrylist);
    					args[3]=getUnmatchedDescriptions(psPlaAvail, "COUNTRYLIST",tmp);

    					createMessage(getCheck_W_E_E(statusFlag),"EXTRA_CTRY_ERR",args);
    				}
    				availCtrylist.clear();
    			}
    		}
    		//119.00	END	116.00	
    		//129.00	END	126.00	
    		psPlaAvailVct.clear();
    	}
    	
     	psAvailVct.clear();

		Vector lseovct = new Vector(1);
		lseovct.add(rootEntity);
		
    	/*120.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
    	121.00	WHEN		AVAILTYPE	=	"Last Order"							
    	122.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
    	122.02	AND		J: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
    	122.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
    	122.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
    	122.08	THEN											
    	122.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
    	122.12	ELSE	122.08										
    	123.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
    	123.02	END	122.08										
    	124.00	END	121.00										

    	130.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
    	131.00	WHEN		AVAILTYPE	=	"Last Order"							
    	132.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
    	132.02	AND		K: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
    	132.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
    	132.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
    	132.08	THEN											
    	132.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
    	132.12	ELSE	132.08										
    	133.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		*/
    	checkLseoPSLOAvail(psitem,"PRODSTRUCTCATLGOR", "OOFAVAIL",lseovct,CHECKLEVEL_E,CHECKLEVEL_E);
    	//133.02	END	132.08										
    	//134.00	END	131.00										

		//release memory
    	lseovct.clear();
    }

	private void checkDateforPlanAvailToLSEO(EntityItem rootEntity,
			String statusFlag, EntityItem psPlaAvail) throws SQLException,
			MiddlewareException {		
		Vector annVct= PokUtils.getAllLinkedEntities(psPlaAvail, "AVAILANNA", "ANNOUNCEMENT");
		addDebug("Avail infor: " + psPlaAvail.getKey()+ " ANNOUNCEMENT: annVct.size = "+annVct.size());
		for (int nn= 0;nn<annVct.size();nn++) {
			EntityItem annItem = (EntityItem)annVct.get(nn);
			String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
			addDebug("ANNOUNCEMENT: anntype = "+anntype);
			if("19".equalsIgnoreCase(anntype)){//New
				checkCanNotBeEarlier(rootEntity, "LSEOPUBDATEMTRGT", annItem, "ANNDATE",getCheck_W_E_E(statusFlag));							
			}
		}
	}
    
    /**
     * 
300.00	PRODSTRUCT	J	LSEOPRODSTRUCT-d: PRODSTRUCT									
301.00	FEATURE		J: + PRODSTRUCT-d
302.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE						
303.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
305.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
305.20	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO						
305.28	THEN											
305.30			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
305.60	ELSE	305.28										
304.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
306.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
306.80	END	305.28										
307.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
308.00	END	302.00	
    
320.00	PRODSTRUCT	K	WWSEOLSEO-u: WWSEOPRODSTRUCT-d: PRODSTRUCT
321.00	FEATURE		K: + PRODSTRUCT-d								FEATURE	
322.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE					RPQ	
323.00			LSEOPUBDATEMTRGT	=>	PRODSTRUCT	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
323.02	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO						
323.04	THEN											
323.06			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
323.08	ELSE	323.04										
324.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
324.02	END	323.04										
324.20			LSEOPUBDATEMTRGT	=>	MODEL	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
324.22			LSEOUNPUBDATEMTRGT	<=	MODEL	WTHDRWEFFCTVDATE		E	E	E	MODEL AVAIL Planned Availability COUNTRYLIST is checked in ID 112.24	{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: MODEL} {NDN: MODEL} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
325.00			LSEOPUBDATEMTRGT	=>	FEATURE	GENAVAILDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
326.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
327.00			COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
327.20	this is deleted		COUNTRYLIST	"INaggregate G"	K: + PRODSTRUCT-u: MODELAVAIL-d: AVAIL where AVAILTYPE = "Planned Availability"	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
328.00	END	322.00	
     * @param lseoItem
     * @param psItem
     * @param featItem
     * @param lseoCtry
     * @param thruWWSEOPS
     * @throws SQLException
     * @throws MiddlewareException
     */
    private void checkRPQFeature(EntityItem lseoItem, EntityItem psItem, EntityItem featItem, boolean thruWWSEOPS) 
    throws SQLException, MiddlewareException
    {
    	//302.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE	
    	//322.00	WHEN		"Primary FC (100) |""Secondary FC"" (110)"	<>	FEATURE	FCTYPE					RPQ	
    	ArrayList featCtrylist = new ArrayList();
    	// fill in list with the feature's countries
    	getCountriesAsList(featItem, featCtrylist,CHECKLEVEL_E);
    	addDebug("checkRPQFeature "+featItem.getKey()+" featCtrylist "+featCtrylist);
    	
    	//303.00		LSEOPUBDATEMTRGT	=>	PRODSTRUCT	ANNDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
    	//323.00		LSEOPUBDATEMTRGT	=>	PRODSTRUCT	ANNDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
		checkCanNotBeEarlier(lseoItem, "LSEOPUBDATEMTRGT", psItem,"ANNDATE", CHECKLEVEL_E);

    	//304.00		LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
    	//324.00		LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
    	checkCanNotBeLater(lseoItem, "LSEOUNPUBDATEMTRGT", psItem,"WTHDRWEFFCTVDATE", CHECKLEVEL_E);	
		
    	//305.00		LSEOPUBDATEMTRGT	=>	FEATURE	FIRSTANNDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
    	//325.00		LSEOPUBDATEMTRGT	=>	FEATURE	FIRSTANNDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: FEATURE} {NDN: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
    	checkCanNotBeEarlier(lseoItem, "LSEOPUBDATEMTRGT", featItem,"FIRSTANNDATE", CHECKLEVEL_E);									
	
    	//306.00		LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
    	//326.00		LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
		checkCanNotBeLater(lseoItem, "LSEOUNPUBDATEMTRGT", featItem,"WITHDRAWDATEEFF_T", CHECKLEVEL_E);
		
		//307.00		COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
    	//327.00		COUNTRYLIST	IN	FEATURE	COUNTRYLIST		E	E	E		{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
		checkAvailCtryInEntity(null,lseoItem, featItem,featCtrylist,CHECKLEVEL_E); 
		
		Vector catlgorVct = PokUtils.getAllLinkedEntities(psItem, "PRODSTRUCTCATLGOR", "CATLGOR");
		addDebug("checkRPQFeature: catlgorVct "+catlgorVct.size());
		if(catlgorVct.size()>0){
			Vector tmp = new Vector();
			for (int i=0;i<catlgorVct.size();i++){
				EntityItem catlgor = (EntityItem)catlgorVct.elementAt(i);
				String pubto = PokUtils.getAttributeValue(catlgor, "PUBTO", "", null, false);
				if(pubto!=null){
					tmp.add(catlgor);
				}
			}
			catlgorVct.clear();
			catlgorVct = tmp;
			addDebug("checkRPQFeature: catlgorVct with pubto values: "+catlgorVct.size());
		}
		boolean usedCatlgor = false;
		String lseopubdate = PokUtils.getAttributeValue(lseoItem, "LSEOPUBDATEMTRGT", "", null, false);
		addDebug("checkRPQFeature  lseopubdate "+lseopubdate);
		
		for (int i=0;i<catlgorVct.size();i++){
			EntityItem catlgor = (EntityItem)catlgorVct.elementAt(i);
			String pubto = PokUtils.getAttributeValue(catlgor, "PUBTO", "", null, false);
			addDebug("checkRPQFeature  "+catlgor.getKey()+" pubto "+pubto);
			//305.20	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO
			//323.02	IF		LSEOPUBDATEMTRGT	<	CATLGOR	PUBTO
			if(lseopubdate.compareTo(pubto)<0){
				usedCatlgor = true;
				//305.30			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
				//323.06			LSEOUNPUBDATEMTRGT	<=	CATLGOR	PUBTO		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
				checkCanNotBeLater4(psItem,lseoItem, "LSEOUNPUBDATEMTRGT", catlgor,"PUBTO", CHECKLEVEL_E);
				break;
			}
		}
		if(!usedCatlgor){
			addDebug("checkRPQFeature did not use catlgor");
			//305.60	ELSE	305.28										
			//304.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
			//323.08	ELSE	323.04										
			//324.00			LSEOUNPUBDATEMTRGT	<=	PRODSTRUCT	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
			checkCanNotBeLater(lseoItem, "LSEOUNPUBDATEMTRGT", psItem,"WTHDRWEFFCTVDATE", CHECKLEVEL_E);
			
			if(!thruWWSEOPS){
				//306.00	LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
				checkCanNotBeLater(lseoItem, "LSEOUNPUBDATEMTRGT", featItem,"WITHDRAWDATEEFF_T", CHECKLEVEL_E);
			}
			//306.80	END	305.28										
			//308.00	END	302.00	
			//324.02	END	323.04										
			//328.00	END	322.00	
		}
		
		//this is only done when prodstruct is from wwseoprodstruct!!!!
		//320.00	PRODSTRUCT	K	WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT
		//	if(getUpLinkEntityItem(psItem, "WWSEOPRODSTRUCT")!=null){ cant use this because same ps may have wwseops and lseops parent
		if(thruWWSEOPS){
    		EntityItem mdlitem = getDownLinkEntityItem(psItem, "MODEL");
    		addDebug("checkRPQFeature thruwwseops "+psItem.getKey()+" "+mdlitem.getKey());
			//324.20			LSEOPUBDATEMTRGT	=>	MODEL	ANNDATE		E	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
			checkCanNotBeEarlier(lseoItem, "LSEOPUBDATEMTRGT", mdlitem,"ANNDATE", CHECKLEVEL_E);	
			//324.22			LSEOUNPUBDATEMTRGT	<=	MODEL	WTHDRWEFFCTVDATE		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: MODEL} {NDN: MODEL} {LD: WTHDRWEFFCTVDATE} {NDN: WTHDRWEFFCTVDATE}
			checkCanNotBeLater(lseoItem, "LSEOUNPUBDATEMTRGT", mdlitem,"WTHDRWEFFCTVDATE", CHECKLEVEL_E);
			//326.00			LSEOUNPUBDATEMTRGT	<=	FEATURE	WITHDRAWDATEEFF_T		E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: FEATURE} {NDN: FEATURE} {LD: WITHDRAWDATEEFF_T} {NDN: WITHDRAWDATEEFF_T}
			checkCanNotBeLater(lseoItem, "LSEOUNPUBDATEMTRGT", featItem,"WITHDRAWDATEEFF_T", CHECKLEVEL_E);
			
			
			/*Delete 20111214
			//K: + PRODSTRUCT-u: MODELAVAIL-d: AVAIL where AVAILTYPE = "Planned Availability"
			EntityItem mdlitem = getDownLinkEntityItem(psItem, "MODEL");
			EntityList mdllist = getModelVE(mdlitem);
			mdlitem = mdllist.getParentEntityGroup().getEntityItem(0);
			Vector availVct = PokUtils.getAllLinkedEntities(mdlitem, "MODELAVAIL", "AVAIL");
			Vector plaavailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
			addDebug("checkRPQFeature "+mdlitem.getKey()+" availVct "+availVct.size()+" plaavailVct "+
					plaavailVct.size());
			if(plaavailVct.size()>0){
				Hashtable plaAvailCtryTbl = getAvailByCountry(plaavailVct, CHECKLEVEL_E);
				addDebug("checkRPQFeature plaAvailCtry "+plaAvailCtryTbl.keySet());
				//327.20		COUNTRYLIST	"IN aggregate G"	K: + PRODSTRUCT-u: MODELAVAIL-d: AVAIL where AVAILTYPE = "Planned Availability"	COUNTRYLIST		E	E	E		
				//{LD: LSEO} {NDN: LSEO} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
				if(!plaAvailCtryTbl.keySet().containsAll(lseoCtry)){
					ArrayList tmp = new ArrayList();
					tmp.addAll(lseoCtry);
					tmp.removeAll(plaAvailCtryTbl.keySet()); // these are the extra ctrys
					//INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
					args[0]="";
					args[1]="";
					args[2]=mdlitem.getEntityGroup().getLongDescription()+
					" "+m_elist.getEntityGroup("AVAIL").getLongDescription();
					args[3]="Planned Availability";
					args[4]=getUnmatchedDescriptions(lseoItem, "COUNTRYLIST",tmp);
					createMessage(CHECKLEVEL_E,"INCLUDE_ERR2",args);
					tmp.clear();
				}
				plaAvailCtryTbl.clear();
				
			}

			availVct.clear();
			plaavailVct.clear();
			mdllist.dereference();
			*/
		} // thru wwseops

    	//328.00	END	322.00	
		featCtrylist.clear();
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
    /****************************
     * 
     * @param rootEntity
     * @param wwseoItem
     * @param statusFlag
     * @throws Exception
     * 
136	MODEL		WWSEO-u: MODELWWSEO-d								Software LSEO	
137	WHEN		COFCAT	=	"Software" (101)							
138	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
139	WHEN		AVAILTYPE	=	"Planned Availability"							
140			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
141			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
142	END	139										
xx143	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
xx144	WHEN		AVAILTYPE	=	"Last Order"							
xx145			COUNTRYLIST	Match	LSEO	COUNTRYLIST		RE	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
xx146			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  can not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
xx147	END	144		
143.00	AVAIL	S	LSEOSWPRODSTRUCT-d:								LSEO: SWPRODSTRUCT	
143.04			S: + SWPRODSTRUCTAVAIL-d									
144.00	WHEN		AVAILTYPE	=	"Last Order"							
145.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
145.02	AND		S: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
145.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
145.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
145.08	THEN											
145.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
145.40	ELSE	145.08										
146.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must  not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
146.40	END	145.08										
147.00	END	144.00										
								
148	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
149	WHEN		AVAILTYPE	=	"Planned Availability"							
150			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
151			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST		W	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
152	END	149										
xx153	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
xx154	WHEN		AVAILTYPE	=	"Last Order"							
xx155			COUNTRYLIST	Match	LSEO	COUNTRYLIST		RE	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
xx156			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  can not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
xx157	END	154	
153.00	AVAIL	T	WWSEOLSEO-u: WWSEOSWPRODSTRUCT-d:								WWSEO: SWPRODSTRUCT	
153.02			T: + SWPRODSTRUCTAVAIL-d									
154.00	WHEN		AVAILTYPE	=	"Last Order"							
155.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
155.02	AND		T: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
155.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
155.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
155.08	THEN											
155.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
155.12	ELSE	155.08										
156.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must  not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
156.02	END	155.08										
157.00	END	154.00										
									
158	END	137	
     */
    private void doSWSpecBidChecks(EntityItem rootEntity,  String lseopubdate,
    		String lseounpubdate,ArrayList lseoCtry,EntityItem wwseoItem,String statusFlag) throws Exception
    {
		Vector wwseoswpsVct = PokUtils.getAllLinkedEntities(wwseoItem, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT");
		Vector lseoswpsVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOSWPRODSTRUCT", "SWPRODSTRUCT");		
		
		addDebug("doSWSpecBidChecks wwseoswpsVct "+wwseoswpsVct.size()+" lseoswpsVct "+lseoswpsVct.size());
		
		addHeading(3,"Software LSEO Special Bid Avail Checks:");
		addDebug("\ndoSWSpecBidChecks lseoswps chks");
		//AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
		checkSpecBidPsAvails(rootEntity, lseoswpsVct, "SWPRODSTRUCT", "SWPRODSTRUCTAVAIL", 
	    		lseopubdate, lseounpubdate, lseoCtry, statusFlag);
    	//143.00	AVAIL	S	LSEOSWPRODSTRUCT-d:								LSEO: SWPRODSTRUCT	
    	//143.04			S: + SWPRODSTRUCTAVAIL-d									
    	//144.00	WHEN		AVAILTYPE	=	"Last Order"							
    	//145.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
    	//145.02	AND		S: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
    	//145.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
    	//145.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
    	//145.08	THEN											
    	//145.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
    	//145.40	ELSE	145.08										
    	//146.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must  not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		Vector lseovct = new Vector(1);
		lseovct.add(rootEntity);
		for(int i=0; i<lseoswpsVct.size();i++){
			EntityItem psitem = (EntityItem)lseoswpsVct.elementAt(i);
			checkLseoPSLOAvail(psitem,"SWPRODSTRCATLGOR", "SWPRODSTRUCTAVAIL",lseovct,CHECKLEVEL_RE,CHECKLEVEL_E);
		}	
    	//146.40	END	145.08										
    	//147.00	END	144.00										

		addHeading(3,"Software WWSEO Special Bid Avail Checks:");
		addDebug("\ndoSWSpecBidChecks wwseoswps chks");
		//AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d				WWSEO: SWPRODSTRUCT AVAIL
		checkSpecBidPsAvails(rootEntity, wwseoswpsVct, "SWPRODSTRUCT", "SWPRODSTRUCTAVAIL", 
	    		lseopubdate, lseounpubdate, lseoCtry, statusFlag);
		
    	//153.00	AVAIL	T	WWSEOLSEO-u: WWSEOSWPRODSTRUCT-d:								WWSEO: SWPRODSTRUCT	
    	//153.02			T: + SWPRODSTRUCTAVAIL-d									
    	//154.00	WHEN		AVAILTYPE	=	"Last Order"							
    	//155.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
    	//155.02	AND		T: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
    	//155.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
    	//155.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
    	//155.08	THEN											
    	//155.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
    	//155.12	ELSE	155.08
    	//156.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must  not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		for(int i=0; i<wwseoswpsVct.size();i++){
			EntityItem psitem = (EntityItem)wwseoswpsVct.elementAt(i);
			checkLseoPSLOAvail(psitem,"SWPRODSTRCATLGOR", "SWPRODSTRUCTAVAIL",lseovct,CHECKLEVEL_RE,CHECKLEVEL_E);
		}	
    	
    	//156.02	END	155.08										
    	//157.00	END	154.00	
    	
		wwseoswpsVct.clear();
		lseoswpsVct.clear();
		lseovct.clear();
    }
    
    /**********************
     * 
     * @param rootEntity
     * @param wwseoItem
     * @param statusFlag
     * @throws Exception
     * 
deleted
159	MODEL		WWSEO-u: MODELWWSEO-d								Service LSEO	
160	WHEN		COFCAT	=	"Service" (102)							
161	AVAIL		LSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d			EFFECTIVEDATE					LSEO: SVCPRODSTRUCT	
162	WHEN		AVAILTYPE	=	"Planned Availability"	COUNTRYLIST						
163			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT		W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
164			COUNTRYLIST	Contains	LSEO	COUNTRYLIST		W	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
165	END	162										
166	AVAIL		LSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d			EFFECTIVEDATE					LSEO: SVCPRODSTRUCT	
167	WHEN		AVAILTYPE	=	"Last Order"	COUNTRYLIST						
168			COUNTRYLIST	Match	LSEO	COUNTRYLIST		RE	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
169			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  can not be later than {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
170	END	167										
171	AVAIL		WWSEOLSEO-u:WWSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d			EFFECTIVEDATE					WWSEO: SVCPRODSTRUCT	
172	WHEN		AVAILTYPE	=	"Planned Availability"	COUNTRYLIST						
173			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT		W	E	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
174			COUNTRYLIST	Contains	LSEO	COUNTRYLIST		W	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
175	END	172										
176	AVAIL		WWSEOLSEO-u:WWSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d			EFFECTIVEDATE					WWSEO: SVCPRODSTRUCT	
177	WHEN		AVAILTYPE	=	"Last Order"	COUNTRYLIST						
178			COUNTRYLIST	Match	LSEO	COUNTRYLIST		RE	RE	RE		{LD: COUNTRYLIST}  includes a Country that is not in {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
179			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  can not be later than {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
180	END	177										
181	END	160	
     * /
    private void doSVCSpecBidChecks(EntityItem rootEntity,  String lseopubdate,
    		String lseounpubdate,ArrayList lseoCtry,EntityItem wwseoItem,String statusFlag) throws Exception
    {
    	Vector wwseosvcpsVct = PokUtils.getAllLinkedEntities(wwseoItem, "WWSEOSVCPRODSTRUCT", "SVCPRODSTRUCT");
		Vector lseosvcpsVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOSVCPRODSTRUCT", "SVCPRODSTRUCT");		
		
		addDebug("doSVCSpecBidChecks wwseosvcpsVct "+wwseosvcpsVct.size()+" lseosvcpsVct "+lseosvcpsVct.size());
		addHeading(3,"Service LSEO Special Bid Avail Checks:");
		//AVAIL		LSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d								LSEO: SVCPRODSTRUCT	
		checkSpecBidPsAvails(rootEntity, lseosvcpsVct, "SVCPRODSTRUCT", "SVCPRODSTRUCTAVAIL", 
	    		lseopubdate, lseounpubdate, lseoCtry, statusFlag,
	    		rootEntity.getEntityGroup().getLongDescription());
		addHeading(3,"Service  WWSEO Special Bid Avail Checks:");
		//AVAIL		WWSEOLSEO-WWSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d				WWSEO: SVCPRODSTRUCT AVAIL
		checkSpecBidPsAvails(rootEntity, wwseosvcpsVct, "SVCPRODSTRUCT", "SVCPRODSTRUCTAVAIL", 
	    		lseopubdate, lseounpubdate, lseoCtry, statusFlag, 
	    		wwseoItem.getEntityGroup().getLongDescription());
		
		wwseosvcpsVct.clear();
		lseosvcpsVct.clear(); 	
    }*/
    
    //====================================================================================
    //=========NOT SPECBID section ======================================
    //====================================================================================
    /*****************
     * Do checks when WWSEO is not a special bid
     * - must have lseo plannedavails
     * - all lseo plannedavails effdate cannot be earlier than lseo.LSEOPUBDATEMTRGT
     * - all lseo plannedavails ctry must be a subset of lseo ctrys
     * 
     * - all lseo firstorderavails effdate cannot be earlier than lseo.LSEOPUBDATEMTRGT
     * - all lseo firstorderavails ctry must be a subset of lseo ctrys 
     * 
     * - all lseo lastorderavails effdate cannot be later than lseo.LSEOUNPUBDATEMTRGT
     * - all lseo lastorderavails effdate cannot be earlier than the plannedavail for the same ctry
     * - all lseo lastorderavails ctrys must be a subset of all lseo plannedavail ctrys 
     * 
     * 
     * @param rootEntity
     * @param statusFlag
     * @throws Exception
     * 
8	WHEN		SPECBID	=	"No" (11457)						GA	
9	AVAIL	A	LSEOAVAIL-d								LSEO: AVAIL	
10	WHEN		AVAILTYPE	=	"Planned Availability" (146)							
11			CountOf	=>	1			RW	RW	RE		must have at least one "Planned Availability"
12			EFFECTIVEDATE	=>	LSEO	LSEOPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
13			COUNTRYLIST	in	LSEO	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL}  includes a Country that is not in {LD: COUNTRYLIST}
14	END	10										
15	AVAIL	C	LSEOAVAIL-d								LSEO: AVAIL	
16	WHEN		AVAILTYPE	=	"First Order" (143)							
17			EFFECTIVEDATE	=>	LSEO	LSEOPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
18			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL}  includes a Country that is not in {LD: AVAIL} {NDN: A: AVAIL} {LD: COUNTRYLIST}
19	END	16										
20	AVAIL	B	LSEOAVAIL-d								LSEO: AVAIL	
21	WHEN		AVAILTYPE	=	"Last Order"							
22			EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  can not be later than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
23			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
24			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  includes a Country that is not in {LD: AVAIL} {NDN: A: AVAIL} {LD: COUNTRYLIST}
25	END	21
	
200	AVAIL	B	LSEOAVAIL-d								LSEO: AVAIL	
201	WHEN		AVAILTYPE	=	"End of Marketing" (200)							
Delete 20110318 202			EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
203			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
204			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
205	END	201										
206	AVAIL	B	LSEOAVAIL-d								LSEO: AVAIL	
207	WHEN		AVAILTYPE	=	"End of Service" (151)							
208xx			EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
Change 20111212	208.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
209			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
210			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
211	END	207										
									
check hw model
									
check sw model	
									
check svc model									
107	END	8	
     */
    private void doGAChecks(EntityItem rootEntity, EntityItem wwseoItem, String statusFlag) throws Exception
    {
    	addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" GA Checks:");
    	// get all AVAIL from LSEO
    	Vector lseoAvailVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOAVAIL", "AVAIL");
		// remove any that are not AVAILANNTYPE=RFA so they arent in the other checks
    	//removeNonRFAAVAIL(lseoAvailVct); 
    	
    	Vector loAvailVct = PokUtils.getEntitiesWithMatchedAttr(lseoAvailVct, "AVAILTYPE", LASTORDERAVAIL);
    	Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(lseoAvailVct, "AVAILTYPE", PLANNEDAVAIL);
    	Vector foAvailVct = PokUtils.getEntitiesWithMatchedAttr(lseoAvailVct, "AVAILTYPE", FIRSTORDERAVAIL);
    	Vector eosAvailVct = PokUtils.getEntitiesWithMatchedAttr(lseoAvailVct, "AVAILTYPE", EOSAVAIL);
    	Vector eomAvailVct = PokUtils.getEntitiesWithMatchedAttr(lseoAvailVct, "AVAILTYPE", EOMAVAIL);
    	addDebug("doGAChecks lseo-loAvailVct "+loAvailVct.size()+
    			" lseo-plannedAvailVct "+plannedAvailVct.size()+" lseo-foAvailVct "+foAvailVct.size()+
    			" lseo-eosAvailVct "+eosAvailVct.size()+" lseo-eomAvailVct "+eomAvailVct.size());
    	
		int checklvl = getCheck_W_W_E(statusFlag);

	  	ArrayList lseoCtry = new ArrayList();
    	// fill in list with the lseo's countries
    	getCountriesAsList(rootEntity, lseoCtry,checklvl);
    	
		addDebug("doGAChecks "+rootEntity.getKey()+" ctrylist: "+lseoCtry);
    	addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" GA Planned Avail Checks:");

		//10.00	WHEN		AVAILTYPE	=	"Planned Availability" (146)							
    	//11.00			CountOf	=>	1			RE	RE	RE		must have at least one "Planned Availability"
//		checkPlannedAvailsExist(plannedAvailVct, getCheck_RW_RW_RE(statusFlag));
		checkPlannedAvailsExist(plannedAvailVct, CHECKLEVEL_RE);
		
//		20121030 Add	12.20	WHEN		"Final" (FINAL)	=	LSEO	DATAQUALITY																																																																																																																																																																																																																																															
//		20121030 Add	12.22	IF		STATUS	=	"Ready for Review" (0040)																																																																																																																																																																																																																																																
//		20121030 Add	12.24	OR		STATUS	=	"Final" (0020)																																																																																																																																																																																																																																																
//		20121030 Add	12.26			CountOf	=>	1					RE		must have at least one "Planned Availability" that is either "Ready for Review" or "Final" in order to be "Final"																																																																																																																																																																																																																																									
//		20121030 Add	12.28	END	12.20	
		checkPlannedAvailsStatus(plannedAvailVct, rootEntity,CHECKLEVEL_RE);
    	
		if(plannedAvailVct.size()>0){
			// LSEO PLA ctrys must be a subset of the MODEL.PLA ctrys
			ArrayList mdlplaCtry = null;
			EntityList mdllist = null;
			Vector mdlVct = PokUtils.getAllLinkedEntities(m_elist.getEntityGroup("WWSEO"), "MODELWWSEO", "MODEL");
			if(mdlVct.size()>0){
				//Add 20111214	13.20		L	WWSEOLSEO-u: MODELWWSEO-u: MODELAVAIL-d: AVAIL	
				EntityItem mdlitem = (EntityItem)mdlVct.firstElement();
				mdllist = getModelVE(mdlitem);
				mdlitem = mdllist.getParentEntityGroup().getEntityItem(0); // get the model with avail links
				Vector availVct = PokUtils.getAllLinkedEntities(mdlitem, "MODELAVAIL", "AVAIL");
				//	Add 20111214	13.22	WHEN		AVAILTYPE	=	"Planned Availability" (146)
				Vector mdlPlaVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
			  	// get model plannedavail countries
			  	mdlplaCtry = getCountriesAsList(mdlPlaVct, checklvl);
				addDebug("doGAChecks "+mdlitem.getKey()+" mdlavailVct: "+availVct.size()+" mdlPlaVct "+
						mdlPlaVct.size()+" mdlplaCtry "+mdlplaCtry);
				availVct.clear();
				mdlPlaVct.clear();
			}
			for (int i=0; i<plannedAvailVct.size(); i++){
				EntityItem avail = (EntityItem)plannedAvailVct.elementAt(i);
				addDebug("doGAChecks lseo plannedavail "+avail.getKey()+" (12) ctrylist: "+
						PokUtils.getAttributeFlagValue(avail, "COUNTRYLIST"));
				//12.00			EFFECTIVEDATE	=>	LSEO	LSEOPUBDATEMTRGT		W	W	E		
				//{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
				checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "LSEOPUBDATEMTRGT",CHECKLEVEL_E);

				//13.00			COUNTRYLIST	in	LSEO	COUNTRYLIST		W	W	E		
				//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a country that is not in the {LD: LSEO} {LD: COUNTRYLIST}
				checkAvailCtryInEntity(null,avail, rootEntity,lseoCtry,CHECKLEVEL_E); 	

				if(mdlplaCtry != null && mdlplaCtry.size()>0){ // mdl planned avail exist
					// get lseo plannedavail countries
					ArrayList lseoplaCtry = new ArrayList();
					getCountriesAsList(avail, lseoplaCtry, checklvl);
					// LSEO PLA ctrys must be a subset of the MODEL.PLA ctrys
					//13.24			A: COUNTRYLIST	"IN aggregate G"	L: AVAIL	COUNTRYLIST		W	W	E		
					if(!mdlplaCtry.containsAll(lseoplaCtry)){
						//{LD: AVAIL} {NDN: A: AVAIL} must not include a country that is not in the {LD: MODEL} {LD: AVAIL}  Planned Availability
						//INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
						ArrayList tmp = new ArrayList();
						tmp.addAll(lseoplaCtry);
						tmp.removeAll(mdlplaCtry); // these are the extra ctrys

						args[0]="";
						args[1]=getLD_NDN(avail);
						args[2]=m_elist.getEntityGroup("MODEL").getLongDescription()+" "+
							avail.getEntityGroup().getLongDescription();
						args[3]="Planned Availability";
						args[4]=getUnmatchedDescriptions(avail, "COUNTRYLIST",tmp);
						createMessage(CHECKLEVEL_E,"INCLUDE_ERR2",args);
						tmp.clear();
					}
					//13.26	END	13.22
					lseoplaCtry.clear();
				}
			}// end plannedavail loop    		
	
			if(mdllist!=null){
				mdllist.dereference();
			}
			if(mdlplaCtry!=null){
				mdlplaCtry.clear();
			}
		}
    	//14.00	END	10
    	
		Hashtable plannedAvailCtryTbl =  getAvailByCountry(plannedAvailVct, getCheck_W_W_E(statusFlag));
		addDebug("doGAChecks lseo plannedAvailCtryTbl: "+plannedAvailCtryTbl.keySet());
		
    	addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" GA First Order Avail Checks:");
    	//15	AVAIL	C	LSEOAVAIL-d								LSEO: AVAIL	
    	//16	WHEN		AVAILTYPE	=	"First Order" (143)							
    	for (int i=0; i<foAvailVct.size(); i++){
    		EntityItem foavail = (EntityItem)foAvailVct.elementAt(i);
    		addDebug("doGAChecks lseo firstorder  "+foavail.getKey()+" (17) ctrylist: "+
				PokUtils.getAttributeFlagValue(foavail, "COUNTRYLIST"));
        	//17			EFFECTIVEDATE	=>	LSEO	LSEOPUBDATEMTRGT		W	W	E		
    		//{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
    		checkCanNotBeEarlier(foavail, "EFFECTIVEDATE", rootEntity, "LSEOPUBDATEMTRGT",checklvl);
    		
    		//18 COUNTRYLIST	in	A: AVAIL	COUNTRYLIST
    		//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
			checkPlannedAvailForCtryExists(foavail, plannedAvailCtryTbl.keySet(), checklvl);
    	}// end fo avail loop
		//19	END	16
    	
    	addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" GA Last Order Avail Checks:");
    	//20	AVAIL	B	LSEOAVAIL-d								LSEO: AVAIL	
    	//21	WHEN		AVAILTYPE	=	"Last Order"	
      	checkLseoLoEosEomAvails(rootEntity,plannedAvailCtryTbl,	loAvailVct, checklvl);
    	//25	END	21
    	
     	addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" GA End of Marketing Avail Checks:");
    	//200	AVAIL	B	LSEOAVAIL-d								LSEO: AVAIL	
    	//201	WHEN		AVAILTYPE	=	"End of Marketing" (200)	
    	checkLseoLoEosEomAvails(rootEntity,plannedAvailCtryTbl,	eomAvailVct, checklvl);
    	//205	END	201	
    	
     	addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" GA End of Service Avail Checks:");
    	//206	AVAIL	B	LSEOAVAIL-d								LSEO: AVAIL	
    	//207	WHEN		AVAILTYPE	=	"End of Service" (151)							
     	checkLseoLoEosEomAvails(rootEntity,plannedAvailCtryTbl,	eosAvailVct, checklvl);
     	//211	END	207	
    	
    	Vector mdlvct =PokUtils.getAllLinkedEntities(wwseoItem, "MODELWWSEO", "MODEL");
		for (int m=0; m<mdlvct.size(); m++){ // there should really only be one
			EntityItem mdlItem = (EntityItem)mdlvct.elementAt(m);
			String modelCOFCAT = getAttributeFlagEnabledValue(mdlItem, "COFCAT");
			addDebug(mdlItem.getKey()+" NOT SPECBID COFCAT: "+modelCOFCAT);

			if(HARDWARE.equals(modelCOFCAT)){
		    	//26	WWSEO		WWSEOLSEO-u: MODELWWSEO-d								WWSEO	
		    	//27	WHEN		COFCAT	=	"Hardware" (100)						Hardware SEO
				 doHWGAChecks(rootEntity, wwseoItem,plannedAvailVct,loAvailVct,statusFlag);
			} else if(SOFTWARE.equals(modelCOFCAT)){
				//54	MODEL		WWSEO-u: MODELWWSEO-d									
				//55	WHEN		COFCAT	=	"Software" (101)						Software SEO	
				doSWGAChecks(rootEntity, wwseoItem,plannedAvailVct,loAvailVct,statusFlag);
			} else if(SERVICE.equals(modelCOFCAT)){
				//82	MODEL		WWSEO-u: MODELWWSEO-d									
				//83	WHEN		COFCAT	=	"Service" (102)						Service LSEO		
				//deleted doSVCGAChecks(rootEntity, wwseoItem,plannedAvailVct,loAvailVct,statusFlag);
				//107	END	8	
			}
		}

		mdlvct.clear();
		lseoAvailVct.clear();
    	loAvailVct.clear();
    	plannedAvailVct.clear();
    	foAvailVct.clear();
    	eosAvailVct.clear();
    	eomAvailVct.clear();
    	lseoCtry.clear();
    	plannedAvailCtryTbl.clear();
    }
    /**************
     * 
     * @param rootEntity
     * @param plannedAvailCtryTbl
     * @param availVct
     * @param checklvl
     * @throws SQLException
     * @throws MiddlewareException
     * 
20	AVAIL	B	LSEOAVAIL-d								LSEO: AVAIL	
21	WHEN		AVAILTYPE	=	"Last Order"							
22			EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
23			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
24			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
25	END	21										

200	AVAIL	B	LSEOAVAIL-d								LSEO: AVAIL	
201	WHEN		AVAILTYPE	=	"End of Marketing" (200)							
Delete 20110318 202			EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
203			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
204			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
205	END	201		
								
206	AVAIL	B	LSEOAVAIL-d								LSEO: AVAIL	
207	WHEN		AVAILTYPE	=	"End of Service" (151)							
old208			EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be later than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
Change 20111212	208.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
209			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
210			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not have a "Planned Availability"
211	END	207	
     */
    private void checkLseoLoEosEomAvails(EntityItem rootEntity, Hashtable plannedAvailCtryTbl, 
    		Vector availVct, int checklvl) throws SQLException, MiddlewareException
    {
    	for (int i=0; i<availVct.size(); i++){
    		EntityItem loavail = (EntityItem)availVct.elementAt(i);
    		String availType = PokUtils.getAttributeFlagValue(loavail, "AVAILTYPE");

    		addDebug("checkLseoLoEosEomAvails lseo avail "+loavail.getKey()+" (22) ctrylist: "+
    				PokUtils.getAttributeFlagValue(loavail, "COUNTRYLIST")+" availType: "+availType);
    		if(!EOMAVAIL.equals(availType)){ //Delete 20110318 202
        		if(EOSAVAIL.equals(availType)){
            		//Change 20111212	208.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		
            		//{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
        			checkCanNotBeEarlier(loavail, "EFFECTIVEDATE", rootEntity, "LSEOUNPUBDATEMTRGT",checklvl);
        		}else{
        			//22		EFFECTIVEDATE	<=	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		
        			//{LD: AVAIL} {NDN: AVAIL}  can not be later than the {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}		
        			checkCanNotBeLater(loavail, "EFFECTIVEDATE", rootEntity, "LSEOUNPUBDATEMTRGT",checklvl);
        		}
    		}
    		
    		Vector plaVct = new Vector(); // hold onto lseo plannedavail for date checks incase same avail for mult ctrys
    		getMatchingAvails(loavail, plannedAvailCtryTbl, plaVct, checklvl);
    		// do the date checks now
    		for (int m=0; m<plaVct.size(); m++){
    			EntityItem plaAvail = (EntityItem)plaVct.elementAt(m);
    			//23			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		
    			//{LD: AVAIL} {NDN: AVAIL}  can not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
    			checkCanNotBeEarlier(loavail, "EFFECTIVEDATE", plaAvail, "EFFECTIVEDATE", checklvl);
    		}
    		plaVct.clear();  
			//24			COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	Yes	W	W	E		
    		//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
			checkPlannedAvailForCtryExists(loavail, plannedAvailCtryTbl.keySet(), checklvl);
    	}
    }
    /*************
     * 
     * 
26	WWSEO		WWSEOLSEO-u: MODELWWSEO-d								WWSEO	
27	WHEN		COFCAT	=	"Hardware" (100)						Hardware SEO	
28	PRODSTRUCT	D	WWSEOPRODSTRUCT-d								WWSEO: PRODSTRUCT	
28.2			ANNDATE	<=	LSEO	LSEOPUBDATEMTRGT		W	W	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: WWSEO} {NDN: D: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
28.4			WTHDRWEFFCTVDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: WWSEO} {NDN: D: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
29	PRODSTRUCT	E	LSEOPRODSTRUCT-d								LSEO: PRODSTRUCT	
30			1	<=	Count Of	D + E		W	E	E		must include at least one {LD: FEATURE}
30.2			ANNDATE	<=	LSEO	LSEOPUBDATEMTRGT		W	W	E		{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} must not be earlier than {LD: LSEO} {NDN: D: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
30.4			WTHDRWEFFCTVDATE	=>	LSEO	LSEOUNPUBDATEMTRGT		W	W	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} must not be later than {LD: LSEO} {NDN: D: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
31	AVAIL		LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								LSEO: PRODSTRUCT	
32	WHEN		AVAILTYPE	=	"Planned Availability"							
33			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
34			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
35	END	32		

xx36	AVAIL		LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								LSEO: PRODSTRUCT	
xx37	WHEN		AVAILTYPE	=	"Last Order"							
xx38	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
xx39	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
xx40	AND		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
xx41	END	37	
36.00	AVAIL	N	LSEOPRODSTRUCT-d: PRODSTRUCT								LSEO: PRODSTRUCT	
36.02			N: + OOFAVAIL-d									
37.00	WHEN		AVAILTYPE	=	"Last Order"							
38.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
39.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
39.02	IF		N: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
39.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
39.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
39.08	THEN											
39.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
39.12	ELSE	39.08										
40.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
40.02	END	39.08										
41.00	END	37.00										
								
									
42	AVAIL		WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								WWSEO: PRODSTRUCT	
43	WHEN		AVAILTYPE	=	"Planned Availability"							
44			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
45			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
46	END	43	
									
xx47	AVAIL		WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								WWSEO: PRODSTRUCT	
xx48	WHEN		AVAILTYPE	=	"Last Order"							
xx49	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
xx50	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
xx51	AND		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
xx52	END	48										
53	END	27	
47.00	AVAIL	P	WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT								WWSEO: PRODSTRUCT	
47.02			P: + OOFAVAIL-d									
48.00	WHEN		AVAILTYPE	=	"Last Order"							
49.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
50.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
50.02	IF		P: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
50.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
50.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
50.08	THEN											
50.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
50.12	ELSE	50.08										
51.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
51.02	END	50.08																			
52.00	END	48.00										
									

     */
    private void doHWGAChecks(EntityItem rootEntity, EntityItem wwseoItem,
    		Vector plannedAvailVctA, Vector loAvailVctB, String statusFlag) throws Exception
    {
    	addHeading(3,"Hardware Model GA Checks:");
    	int checklvl = getCheck_W_W_E(statusFlag);
    	
		Vector wwseopsVct = PokUtils.getAllLinkedEntities(wwseoItem, "WWSEOPRODSTRUCT", "PRODSTRUCT");
		Vector lseopsVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOPRODSTRUCT", "PRODSTRUCT");
		
		addDebug("doHWGAChecks wwseopsVct "+wwseopsVct.size()+" lseopsVct "+lseopsVct.size());
       	// get the set of countries for lseo planned avails
    	Hashtable lseoPlaAvailCtryTblA =  getAvailByCountry(plannedAvailVctA, getCheck_W_RE_RE(statusFlag));
    	addDebug("doHWGAChecks  lseoPlaAvailCtryTblA: "+lseoPlaAvailCtryTblA.keySet());
		//30			1	<=	Count Of	D + E		W	E	E		must include at least one {LD: FEATURE}
		if (wwseopsVct.size()+lseopsVct.size() ==0){
			args[0] = m_elist.getEntityGroup("FEATURE").getLongDescription();
			//MINIMUM_ERR = must have at least one {0}
			createMessage(getCheck_W_E_E(statusFlag),"MINIMUM_ERR",args);
		}

		String lseopubdate = getAttrValueAndCheckLvl(rootEntity, "LSEOPUBDATEMTRGT", checklvl);
		String lseounpubdate = getAttrValueAndCheckLvl(rootEntity, "LSEOUNPUBDATEMTRGT", checklvl);
		//28	PRODSTRUCT	D	WWSEOPRODSTRUCT-d								WWSEO: PRODSTRUCT	
		checkPsDates(rootEntity, wwseopsVct, wwseoItem.getEntityGroup(), 
	    		lseopubdate, lseounpubdate, checklvl);
		//29	PRODSTRUCT	E	LSEOPRODSTRUCT-d								LSEO: PRODSTRUCT	
		checkPsDates(rootEntity, lseopsVct, rootEntity.getEntityGroup(), 
	    		lseopubdate, lseounpubdate, checklvl);
//		20121106 Add	30.60			SYSTEMMAX		LSEOPRODSTRUCT	CONFQTY		E	E	E		{LD: LSEOPRODSTRUCT} {LD: CONFQTY} {CONFQTY} can not be greater than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: SYSTEMMAX} {SYSTEMMAX}																																																																																																																																																																																																																																											
		checkSystemMaxAndConfqty(rootEntity,"LSEOPRODSTRUCT",CHECKLEVEL_E);
		
		addDebug("\ndoHWGAChecks lseops chks");
		addHeading(3,"LSEO "+ m_elist.getEntityGroup("PRODSTRUCT").getLongDescription()+" Planned Avail Checks:");
		//31	AVAIL		LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								LSEO: PRODSTRUCT	
		//32	WHEN		AVAILTYPE	=	"Planned Availability"							
		//33			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		//34			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		checkGAPsPlaAvails(rootEntity, lseopsVct, "OOFAVAIL", plannedAvailVctA,statusFlag);
		//35	END	32	
		
		addHeading(3,"LSEO "+ m_elist.getEntityGroup("PRODSTRUCT").getLongDescription()+" Last Order Avail Checks:");
		//old36	AVAIL		LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								LSEO: PRODSTRUCT	
		//old37	WHEN		AVAILTYPE	=	"Last Order"							
		//old38	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
		//old39	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
		//old40	AND		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 	
		//oldmatchPsLastOrderAvail(loAvailVctB,plannedAvailVctA, lseopsVct, "OOFAVAIL");
		//old41	END	37	
		//36.00	AVAIL	N	LSEOPRODSTRUCT-d: PRODSTRUCT								LSEO: PRODSTRUCT	
		//36.02			N: + OOFAVAIL-d									
		//37.00	WHEN		AVAILTYPE	=	"Last Order"							
		//38.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
		//39.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
		//39.02	IF		N: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
		//39.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
		//39.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
		//39.08	THEN											
		//39.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
		//39.12	ELSE	39.08										
		//40.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
		matchPsLOAvail(loAvailVctB,lseoPlaAvailCtryTblA, lseopsVct, "OOFAVAIL","PRODSTRUCTCATLGOR",CHECKLEVEL_RE,CHECKLEVEL_E);
		//40.02	END	39.08										
		//41.00	END	37.00	
	   	
		addDebug("\ndoHWGAChecks wwseops chks");
		addHeading(3,"WWSEO "+ m_elist.getEntityGroup("PRODSTRUCT").getLongDescription()+" Planned Avail Checks:");
		//42	AVAIL		WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d					WWSEO: PRODSTRUCT	
		//43	WHEN		AVAILTYPE	=	"Planned Availability"							
		//44			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		//45			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
	   	checkGAPsPlaAvails(rootEntity, wwseopsVct, "OOFAVAIL", plannedAvailVctA, statusFlag);
		//46	END	43	
	   	
		addHeading(3,"WWSEO "+ m_elist.getEntityGroup("PRODSTRUCT").getLongDescription()+" Last Order Avail Checks:");
		//old47	AVAIL		WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d					WWSEO: PRODSTRUCT	
		//old48	WHEN		AVAILTYPE	=	"Last Order"							
		//old49	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
		//old50	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
		//old51	AND		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
		//oldmatchPsLastOrderAvail(loAvailVctB,plannedAvailVctA, lseopsVct, "OOFAVAIL");
		//old52	END	48		
		//47.00	AVAIL	P	WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT								WWSEO: PRODSTRUCT	
		//47.02			P: + OOFAVAIL-d									
		//48.00	WHEN		AVAILTYPE	=	"Last Order"							
		//49.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
		//50.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
		//50.02	IF		P: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
		//50.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
		//50.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
		//50.08	THEN											
		//50.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
		//50.12	ELSE	50.08										
		//51.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
		matchPsLOAvail(loAvailVctB,lseoPlaAvailCtryTblA, lseopsVct, "OOFAVAIL","PRODSTRUCTCATLGOR",CHECKLEVEL_RE,CHECKLEVEL_E);
		//51.02	END	50.08										
		//52.00	END	48.00										
		//53.00	END	27.00
	
		wwseopsVct.clear();
		lseopsVct.clear();
		lseoPlaAvailCtryTblA.clear();
    }
    
    
    /***************
	 * Check lseo planned avails against the (xx)prodstruct planned avails
	 * All lseo planned avails effdate must not be earlier than the prodstruct planned avail effdate by country
	 * All lseo planned avails countries must be a subset of all prodstruct planned avail countries
LSEO
31	AVAIL		LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								LSEO: PRODSTRUCT	
32	WHEN		AVAILTYPE	=	"Planned Availability"							
33			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E	{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
34			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE	{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
35	END	32			
----------
59	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
60	WHEN		AVAILTYPE	=	"Planned Availability"							
61			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
62			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
63	END	60	
-----------		

WWSEO

42	AVAIL		WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d					WWSEO: PRODSTRUCT	
43	WHEN		AVAILTYPE	=	"Planned Availability"							
44			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
45			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
46	END	43	
--------
70	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d						WWSEO: SWPRODSTRUCT	
71	WHEN		AVAILTYPE	=	"Planned Availability"							
72			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
73			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}

     * @param rootEntity
     * @param psVct
     * @param psAvailRelType
     * @param lseoPlaAvailVctA
     * @param statusFlag
     * @throws MiddlewareException
     * @throws SQLException
     */
    private void checkGAPsPlaAvails(EntityItem rootEntity, Vector psVct, 
    		String psAvailRelType, Vector lseoPlaAvailVctA,  String statusFlag) 
    throws MiddlewareException, SQLException
    {
    	//31	AVAIL		LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								LSEO: PRODSTRUCT
    	//42	AVAIL		WWSEOLSEO-u:WWSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d					WWSEO: PRODSTRUCT	
   		// go from (xx)prodstruct to their avails
    	Vector allpsAvailVct = PokUtils.getAllLinkedEntities(psVct, psAvailRelType, "AVAIL");
    	Vector allpsPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(allpsAvailVct, "AVAILTYPE", PLANNEDAVAIL);
        
       	// get the set of countries for lseo planned avails
    	Hashtable lseoPlaAvailCtryTblA =  getAvailByCountry(lseoPlaAvailVctA, getCheck_W_RE_RE(statusFlag));
    	addDebug("checkGAPsPlaAvails "+psAvailRelType+" allpsAvailVct: "+
    			allpsAvailVct.size()+" allpsPlaAvailVct: "+allpsPlaAvailVct.size()+
    			" lseoPlaAvailCtryTblA: "+lseoPlaAvailCtryTblA.keySet());
    	
    	int dateChecklvl = getCheck_W_E_E(statusFlag);
    	int ctryChecklvl =  getCheck_W_RE_RE(statusFlag);
		//look at each PS planned avail							
		for (int i=0; i<psVct.size(); i++){
			EntityItem psItem = (EntityItem)psVct.elementAt(i);
	    	Vector psAvailVct = PokUtils.getAllLinkedEntities(psItem, psAvailRelType, "AVAIL");
	    	Vector psPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(psAvailVct, "AVAILTYPE", PLANNEDAVAIL);
	    	addDebug("checkGAPsPlaAvails "+psAvailRelType+" "+psItem.getKey()+" psAvailVct: "+
	    			psAvailVct.size()+" psPlaAvailVct: "+psPlaAvailVct.size());
	    	for (int p=0; p<psPlaAvailVct.size(); p++){
	    		EntityItem psPlaAvail = (EntityItem)psPlaAvailVct.elementAt(p);

	    		//ps.plannedavail.EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	 (find countrymatch)
	    		Vector matchedPlaVctA = new Vector(); // hold onto plannedavail for date checks incase same avail for mult ctrys
	    		getMatchingAvails(psPlaAvail, lseoPlaAvailCtryTblA, matchedPlaVctA, ctryChecklvl);
	    		String date1 = getAttrValueAndCheckLvl(psPlaAvail, "EFFECTIVEDATE", dateChecklvl);
	    		for (int m=0; m<matchedPlaVctA.size(); m++){
	    			EntityItem plAvail = (EntityItem)matchedPlaVctA.elementAt(m);
	    			//		EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes		    					
	    			String pladate2 = getAttrValueAndCheckLvl(plAvail, "EFFECTIVEDATE", dateChecklvl);					
	    			addDebug("checkGAPsPlaAvails root plannedavail: "+
	    					plAvail.getKey()+" EFFECTIVEDATE:"+pladate2+" must not be earlier psplannedavail:"+
	    					psPlaAvail.getKey()+" EFFECTIVEDATE:"+date1);
	    			boolean isok = checkDates(date1, pladate2, DATE_LT_EQ);	//date1<=date2	
	    			if (!isok){
	    				//CANNOT_BE_EARLIER_ERR2 = {0} {1} must not be earlier than the {2} {3}
	    				//{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {NDN: F: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
	    				args[0]=getLD_NDN(plAvail);
	    				args[1]=this.getLD_Value(plAvail, "EFFECTIVEDATE");
	    				args[2]=getLD_NDN(psItem);
	    				args[3]=getLD_NDN(psPlaAvail);
	    				createMessage(dateChecklvl,"CANNOT_BE_EARLIER_ERR2",args);
	    			}
	    		}
	    		matchedPlaVctA.clear();
	    	}
	       	//release memory
	       	psAvailVct.clear();
	     	psPlaAvailVct.clear();
		}	
		//COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST
		// get the entire set of psavail countries
		ArrayList allPsPlaAvailCtry = getCountriesAsList(allpsPlaAvailVct, ctryChecklvl);
		addDebug("checkGAPsPlaAvails allPsPlaAvailCtry "+allPsPlaAvailCtry);
		addDebug("checkGAPsPlaAvails root plaAvailCtryTblA.keySet() "+lseoPlaAvailCtryTblA.keySet());
		if (!allPsPlaAvailCtry.containsAll(lseoPlaAvailCtryTblA.keySet())){
			// at least one doesnt match, find it/them
			// each root A:AVAIL country must be a subset of this entire set
			for (int i=0; i<lseoPlaAvailVctA.size(); i++){
				EntityItem plaAvailA = (EntityItem)lseoPlaAvailVctA.elementAt(i);
				ArrayList plaCtryA = new ArrayList();
				getCountriesAsList(plaAvailA, plaCtryA, ctryChecklvl);
				
				if (!allPsPlaAvailCtry.containsAll(plaCtryA)){
					addDebug("checkGAPsPlaAvails root plannedavailA "+plaAvailA.getKey()+
							" plaCtryA "+plaCtryA+" not in psPlaAvailCtry "+allPsPlaAvailCtry);
					//		COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		
					//{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} must not include a country that is not in {LD: PRODSTRUCT} {NDN: F: PRODSTRUCT} {LD: AVAIL}{NDN: AVAIL}
					//INCLUDE_ERR = {0} {1} must not include a country that is not in {2} {3}
					args[0]=getLD_NDN(plaAvailA);
					args[1]=PokUtils.getAttributeDescription(plaAvailA.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");

					//EntityItem psPlaAvail = (EntityItem)psPlaAvailVct.firstElement(); do all now
					//EntityItem psItem = getAvailPS(psPlaAvail, psreltype);
					//args[2]=getLD_NDN(psItem);
					//args[3]=getLD_NDN(psPlaAvail);
					//createMessage(ctryChecklvl,"INCLUDE_ERR",args);

					for (int ii=0; ii<allpsPlaAvailVct.size(); ii++){
						EntityItem psPlaAvail = (EntityItem)allpsPlaAvailVct.elementAt(ii); 
						ArrayList availCtrylist = new ArrayList();
						// fill in list with the avail countries
						getCountriesAsList(psPlaAvail, availCtrylist,CHECKLEVEL_NOOP);
						if (!availCtrylist.containsAll(plaCtryA)){
							addDebug("checkGAPsPlaAvails psPlaAvail "+psPlaAvail.getKey()+" availCtrylist: "+availCtrylist+
									" did not contain all root "+plaAvailA.getKey()+" plaCtryA: "+plaCtryA);
							EntityItem psItem = getAvailPS(psPlaAvail, psAvailRelType);
							
							ArrayList tmp = new ArrayList();
							tmp.addAll(availCtrylist);
							tmp.removeAll(plaCtryA); // these are the extra ctrys
							//INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
							if(tmp.size()==0){
								// mismatch the other way
								tmp.addAll(plaCtryA);
								tmp.removeAll(availCtrylist); // these are the extra ctrys
							}
							
							args[2]=getLD_NDN(psItem);
							args[3]=getLD_NDN(psPlaAvail);
							args[4]=getUnmatchedDescriptions(psPlaAvail, "COUNTRYLIST",tmp);
							createMessage(ctryChecklvl,"INCLUDE_ERR2",args);
							tmp.clear();
						}
						availCtrylist.clear();
					}
				}
				plaCtryA.clear();
			}
		}
     	//release memory		
		allPsPlaAvailCtry.clear();
       	allpsAvailVct.clear();
     	allpsPlaAvailVct.clear();
    	lseoPlaAvailCtryTblA.clear();	 	
    }
  
    /**
	36.00	AVAIL	N	LSEOPRODSTRUCT-d: PRODSTRUCT								LSEO: PRODSTRUCT	
	36.02			N: + OOFAVAIL-d									
	37.00	WHEN		AVAILTYPE	=	"Last Order"							
	38.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
	39.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
	39.02	IF		N: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
	39.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
	39.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
	39.08	THEN											
	39.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
	39.12	ELSE	39.08										
	40.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
	40.02	END	39.08										
	41.00	END	37.00
--------
    64.00	AVAIL	Q	LSEOSWPRODSTRUCT-d:								LSEO: SWPRODSTRUCT	
    64.02			Q: SWPRODSTRUCTAVAIL-d									
    65.00	WHEN		AVAILTYPE	=	"Last Order"							
    66.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
    67.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
    67.02	IF		Q: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
    67.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
    67.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
    67.08	THEN											
    67.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
    67.12	ELSE	67.08										
    68.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
    68.02	END	67.08										
    69.00	END	65.00
    ------
    75.00	AVAIL	R	WWSEOLSEO-u: WWSEOSWPRODSTRUCT-d								WWSEO: SWPRODSTRUCT	
	75.02			R: SWPRODSTRUCTAVAIL-d									
	76.00	WHEN		AVAILTYPE	=	"Last Order"							
	77.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
	78.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
	78.02	IF		R: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
	78.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
	78.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
	78.08	THEN											
	78.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
	78.12	ELSE	78.08										
	79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
	79.02	END	78.08										
	80.00	END	76.00			
     * 
     * @param lseoLOAvailVctB
     * @param lseoPlaAvailCtryTblA
     * @param psVct
     * @param psRelType
     * @param catlgorRel
     * @param checklvl
     * @param pubtochklvl
     * @throws MiddlewareException
     * @throws SQLException
     */
    private void matchPsLOAvail(Vector lseoLOAvailVctB,
    		Hashtable lseoPlaAvailCtryTblA, Vector psVct, String psRelType,
    		String catlgorRel,int checklvl, int pubtochklvl) throws MiddlewareException, SQLException
    {
		// get the set of countries for lseo lastorder avails
		Hashtable lseoLOAvailCtryTblB =  getAvailByCountry(lseoLOAvailVctB, checklvl);
		// get the set of countries for lseo planned avails
    	Set lseoPlaAvailCtrys = lseoPlaAvailCtryTblA.keySet();
    	addDebug("matchPsLOAvail lseoPlaAvailCtrys "+lseoPlaAvailCtrys+
    			" lseoLOAvailCtryTblB: "+lseoLOAvailCtryTblB.keySet());
    	
		//36.00	AVAIL	N	LSEOPRODSTRUCT-d: PRODSTRUCT								LSEO: PRODSTRUCT	
		//36.02			N: + OOFAVAIL-d									
		//37.00	WHEN		AVAILTYPE	=	"Last Order"
    	
    	//64.00	AVAIL	Q	LSEOSWPRODSTRUCT-d:								LSEO: SWPRODSTRUCT	
    	//64.02			Q: SWPRODSTRUCTAVAIL-d									
    	//65.00	WHEN		AVAILTYPE	=	"Last Order"			
    	
    	//75.00	AVAIL	R	WWSEOLSEO-u: WWSEOSWPRODSTRUCT-d								WWSEO: SWPRODSTRUCT	
		//75.02			R: SWPRODSTRUCTAVAIL-d									
		//76.00	WHEN		AVAILTYPE	=	"Last Order"							
	
		for(int i=0; i<psVct.size(); i++){
			EntityItem psitem = (EntityItem)psVct.elementAt(i);
	    	Vector psAvailVct = PokUtils.getAllLinkedEntities(psitem, psRelType, "AVAIL");
	    	Vector psLOAvailVct = PokUtils.getEntitiesWithMatchedAttr(psAvailVct, "AVAILTYPE", LASTORDERAVAIL);

	    	addDebug("matchPsLOAvail "+psitem.getKey()+" "+psRelType+" psAvailVct: "+
	    			psAvailVct.size()+" psLOAvailVct: "+psLOAvailVct.size());
			loavailloop:for(int p=0; p<psLOAvailVct.size(); p++){
				EntityItem psloAvail = (EntityItem)psLOAvailVct.elementAt(p);
				// get the countries for the ps lastorder avail
				ArrayList psloCtry = new ArrayList();
				getCountriesAsList(psloAvail, psloCtry, checklvl);
				addDebug("matchPsLOAvail "+psloAvail.getKey()+" psloCtry "+psloCtry);
				ArrayList tmp = (ArrayList)psloCtry.clone();
				tmp.retainAll(lseoPlaAvailCtrys);
				addDebug("matchPsLOAvail lseoplaavail matching psloavail ctrys: "+tmp);
				// 38.00	IF		PS.LastOrder.COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST
		    	//66.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST		
				//77.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
				if(tmp.size()>0){ // there is an intersection (match)
					Iterator itr = tmp.iterator();
					while(itr.hasNext()) {	
						String ctryflagcode = itr.next().toString();
						//39.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE
				    	//67.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
						//78.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
						if (!lseoLOAvailCtryTblB.containsKey(ctryflagcode)){
							EntityItem plaAvail = (EntityItem)lseoPlaAvailCtryTblA.get(ctryflagcode);
							addDebug("matchPsLOAvail lseo-plannedavail:"+plaAvail.getKey()+
									" PS-lastorderavail "+psloAvail.getKey()+
									" No lseo lastorderavail for ctry "+ctryflagcode);
							//{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
							//LOAVAIL_MATCH_ERR= {0} {1} is being withdrawn; therefore this {2} must have a corresponding {3} &quot;Last Order&quot; by Country
							args[0]=getLD_NDN(psitem);
							args[1]=getLD_NDN(psloAvail);
							args[2]=m_elist.getParentEntityGroup().getLongDescription();
							args[3]=psloAvail.getEntityGroup().getLongDescription();
							createMessage(checklvl,"LOAVAIL_MATCH_ERR",args);
							break; // end iterator loop
						}
					}
				}
				
				Vector catlgorVct = PokUtils.getAllLinkedEntities(psitem, catlgorRel, "CATLGOR");
				String effDate = PokUtils.getAttributeValue(psloAvail, "EFFECTIVEDATE",", ", "", false);
				addDebug("matchPsLOAvail:  catlgorVct "+catlgorVct.size()+" "+psloAvail.getKey()+" EFFECTIVEDATE "+effDate);
				for (int ic=0;ic<catlgorVct.size();ic++){
					EntityItem catlgor = (EntityItem)catlgorVct.elementAt(ic);
					String pubto = PokUtils.getAttributeValue(catlgor, "PUBTO", "", null, false);
					String offctry = PokUtils.getAttributeFlagValue(catlgor, "OFFCOUNTRY");
					addDebug("matchPsLOAvail: "+catlgor.getKey()+" pubto "+pubto+" offctry "+offctry);
					//39.02	IF		N: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)	
			    	//67.02	IF		Q: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)		
					//78.02	IF		R: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
					if(pubto!=null && offctry !=null){
						//39.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST	
						//39.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO
						//39.08	THEN
				    	//67.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
				    	//67.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
				    	//67.08	THEN		
						//78.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
						//78.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
						//78.08	THEN											
						if(lseoLOAvailCtryTblB.containsKey(offctry) &&	effDate.compareTo(pubto)<0){	
							EntityItem loAvail = (EntityItem)lseoLOAvailCtryTblB.get(offctry);
							addDebug("matchPsLOAvail: chk pubto against "+loAvail.getKey()+
									" EFFECTIVEDATE "+PokUtils.getAttributeValue(loAvail, "EFFECTIVEDATE",", ", "", false));
							//39.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		
							//{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
					    	//67.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
							//78.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
							checkCanNotBeLater4(psitem, loAvail,"EFFECTIVEDATE",catlgor, "PUBTO",pubtochklvl);	
							continue loavailloop;
						}
					}
				}
				catlgorVct.clear();
				addDebug("matchPsLOAvail: no catlgor chk done, chk ps loavail against lseo loavail");
				//39.12	ELSE	39.08										
		    	//67.12	ELSE	67.08			
				//78.12	ELSE	78.08										
				//must get the B:AVAILs for matching countries
				Vector lseoLoVct = new Vector(); // hold onto lseo loavail for date checks incase same avail for mult ctrys
				getMatchingAvails(psloAvail, lseoLOAvailCtryTblB, lseoLoVct, checklvl);
				// do the date checks now by ctry
				for (int m=0; m<lseoLoVct.size(); m++){
					EntityItem loAvail = (EntityItem)lseoLoVct.elementAt(m);
					//40.00		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		
			      	//{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
			    	//68.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
					//79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
					checkCanNotBeLater4(psitem, loAvail,"EFFECTIVEDATE",psloAvail, "EFFECTIVEDATE",checklvl);	
				}
				lseoLoVct.clear();
				
				//40.02	END	39.08
		    	//68.02	END	67.08										
				//79.02	END	78.08										
	
			}//end ps loavail loop
			psAvailVct.clear();
	    	psLOAvailVct.clear();
		}
    	//41.00	END	37.00
    	//69.00	END	65.00
		//80.00	END	76.00
    }
	/**
	 * this is a different format that most other messages
	 * 39.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		
		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 

	 * 	40.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		
		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 

	 * @param psitem
	 * @param item1
	 * @param attrCode1
	 * @param item2
	 * @param attrCode2
	 * @param checkLvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkCanNotBeLater4(EntityItem psitem, EntityItem item1, String attrCode1, EntityItem item2, String attrCode2,
			int checkLvl) throws SQLException, MiddlewareException
	{
		String psinfo="";
		if(psitem!=null){
			psinfo = this.getLD_NDN(psitem)+" ";
		}
		String date1 = getAttrValueAndCheckLvl(item1, attrCode1, checkLvl);
		String date2 = getAttrValueAndCheckLvl(item2, attrCode2, checkLvl);
		addDebug("checkCanNotBeLater4 "+item1.getKey()+" "+attrCode1+":"+date1+" "+item2.getKey()+" "+
				attrCode2+":"+date2);
		boolean isok = checkDates(date1, date2, DATE_LT_EQ);	//date1<=date2	msg is swapped date1>=date2 but date2 must not be later
		if(isok){ // look for missing meta
			if(date1.length()>0 && !Character.isDigit(date1.charAt(0))){
				isok=false;
			}
			if(date2.length()>0 && !Character.isDigit(date2.charAt(0))){
				isok=false;
			}
		}
		if (!isok){
			//CANNOT_BE_LATER_ERR = {0} {1} must not be later than the {2} {3}	
			//{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 	
			//{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
			if(item1.getEntityType().equals(this.getEntityType()) && item1.getEntityID()==this.getEntityID()){
				args[0]=""; // this is the root, dont repeat info
			}else{
				args[0]=getLD_NDN(item1);
			}
			args[1]=getLD_Value(item1, attrCode1);
			args[2]=psinfo+getLD_NDN(item2);
			args[3]=getLD_Value(item2, attrCode2);
			createMessage(checkLvl,"CANNOT_BE_LATER_ERR",args);
		}
	}
    /**************
     * For each lseo->plannedAvail.ctry that matches the ps->LastOrderAvail.ctry
     * there must be a lseo->LastOrderAvail.ctry	
     * @param lseoLOAvailVctB
     * @param lseoPlaAvailVctA
     * @param psVct
     * @throws MiddlewareException
     * @throws SQLException
     * 
LSEO							

---------
89	AVAIL		LSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d								LSEO: SVCPRODSTRUCT	
90	WHEN		AVAILTYPE	=	"Last Order"							
91	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
92	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
93			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
	

WWSEO

--------------
100	AVAIL		WWSEOLSEO-u:WWSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d								WWSEO: SVCPRODSTRUCT	
101	WHEN		AVAILTYPE	=	"Last Order"							
102	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
103	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
104		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
105	END	101		
     * /
    private void matchPsLastOrderAvail(Vector lseoLOAvailVctB,
    		Vector lseoPlaAvailVctA, Vector psVct, String psRelType) throws MiddlewareException, SQLException
    {
    	int checklvl = CHECKLEVEL_RE;
    	Vector psAvailVct = PokUtils.getAllLinkedEntities(psVct, psRelType, "AVAIL");
    	Vector psLOAvailVct = PokUtils.getEntitiesWithMatchedAttr(psAvailVct, "AVAILTYPE", LASTORDERAVAIL);

    	addDebug("matchPsLastOrderAvail "+psRelType+" psAvailVct: "+
    			psAvailVct.size()+" psLOAvailVct: "+psLOAvailVct.size());
    	
    	// get the ps lo avails by country
    	Hashtable psLOAvailCtryTbl =  getAvailByCountry(psLOAvailVct, checklvl);
    	addDebug("matchPsLastOrderAvail psLOAvailCtryTbl: "+psLOAvailCtryTbl.keySet());
    	
    	//	IF		PS-LastOrderAVAIL.COUNTRYLIST	Match	LSEO-PlannedAVAIL	COUNTRYLIST						
    	//	THEN	LSEOPlannedAVAIL.COUNTRYLIST	TheMatch	LSEO-LastOrderAVAIL	COUNTRYLIST		RE	RE	RE		
    	// get the set of countries for lseo lastorder avails
    	Hashtable lseoLOAvailCtryTblB =  getAvailByCountry(lseoLOAvailVctB, checklvl);;
    	addDebug("matchPsLastOrderAvail lseoLOAvailCtryTblB: "+lseoLOAvailCtryTblB.keySet());

    	// For each LSEO->plannedAvail.ctry that matches the PS->LastOrderAvail.ctry
    	// there must be a LSEO->LastOrderAvail.ctry
    	for (int i=0; i<lseoPlaAvailVctA.size(); i++){
    		EntityItem plaAvail = (EntityItem)lseoPlaAvailVctA.elementAt(i); 

    		EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(plaAvail, "COUNTRYLIST", checklvl);
    		if (ctrylist != null && ctrylist.toString().length()>0) {
    			// Get the selected Flag codes.
    			MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();

    			Vector psloVct = new Vector(); // hold onto ps lastorderavail in case mult ctrys match
    			for (int im = 0; im < mfArray.length; im++) {						
    				if (mfArray[im].isSelected() &&
    						!lseoLOAvailCtryTblB.containsKey(mfArray[im].getFlagCode())){
    					addDebug("matchPsLastOrderAvail (38,39) lseo-plannedavail:"+plaAvail.getKey()+
    							" No lseo lastorderavail for ctry "+mfArray[im].getFlagCode());
    					// get the ps-lastorderavail for this ctry
    					EntityItem psloAvail = (EntityItem)psLOAvailCtryTbl.get(mfArray[im].getFlagCode());
    					if (psloAvail!=null){
    						addDebug("matchPsLastOrderAvail (38,39) lseo-plannedavail:"+plaAvail.getKey()+
    								" PS-lastorderavail "+psloAvail.getKey()+" for ctry "+
    								mfArray[im].getFlagCode());
    						if (!psloVct.contains(psloAvail)){
    							psloVct.add(psloAvail);
    						}
    					}
    				}
    			}

    			// output msg for all ps lastorder that didnt have an lseo lastorder
    			for (int m=0; m<psloVct.size(); m++){
    				EntityItem psloAvail = (EntityItem)psloVct.elementAt(m);
    				EntityItem psitem = getAvailPS(psloAvail,psRelType);
    				//{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn; therefore this {LD: LSEO} must have a corresponding {LD: AVAIL} "Last Order" by Country
    		    	//LOAVAIL_MATCH_ERR= {0} {1} is being withdrawn; therefore this {2} must have a corresponding {3} &quot;Last Order&quot; by Country
    				args[0]=getLD_NDN(psitem);
    		    	args[1]=getLD_NDN(psloAvail);
    				args[2]=m_elist.getParentEntityGroup().getLongDescription();
    				args[3]=psloAvail.getEntityGroup().getLongDescription();
    				createMessage(checklvl,"LOAVAIL_MATCH_ERR",args);
    			}
    			psloVct.clear();           				
    		}
    	}
     	
    	//36	AVAIL		LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL-d								LSEO: PRODSTRUCT	
      	//37	WHEN		AVAILTYPE	=	"Last Order"    		
     	//40		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		
      	//{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
    	for (int i=0; i<psLOAvailVct.size(); i++){
			EntityItem psloAvail = (EntityItem)psLOAvailVct.elementAt(i);
			EntityItem psitem = getAvailPS(psloAvail,psRelType);
			
			addDebug("doGAChecks "+psitem.getKey()+" ps lastorder "+psloAvail.getKey()+" (22) ctrylist: "+
    				PokUtils.getAttributeFlagValue(psloAvail, "COUNTRYLIST"));

			Vector lseoLoVct = new Vector(); // hold onto lseo loavail for date checks incase same avail for mult ctrys
			checkCtryMismatch(psloAvail, lseoLOAvailCtryTblB, lseoLoVct, checklvl);
			// do the date checks now by ctry
			for (int m=0; m<lseoLoVct.size(); m++){
				EntityItem loAvail = (EntityItem)lseoLoVct.elementAt(m);
				//40		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		
		      	//{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
		      	checkDates(loAvail, psitem, psloAvail, checklvl, DATE_LT_EQ);
			}
			lseoLoVct.clear();
    	}

     	psAvailVct.clear();
    	psLOAvailVct.clear();
    	lseoLOAvailCtryTblB.clear();
    	psLOAvailCtryTbl.clear();
    } 
   
    //33			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		
    //{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
	//40		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		
  	//{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
    private void checkDates(EntityItem lseoAvail, EntityItem psItem, EntityItem psAvail,
    		int checklvl, int dateRule) throws SQLException, MiddlewareException
    {
    	String date2 = getAttrValueAndCheckLvl(psAvail, "EFFECTIVEDATE", checklvl);
    	String date1 = getAttrValueAndCheckLvl(lseoAvail, "EFFECTIVEDATE", checklvl);
    	addDebug("checkDates lseoavail: "+lseoAvail.getKey()+" EFFECTIVEDATE:"+date1+" can not be "+
    			(dateRule==DATE_GR_EQ?"earlier":"later")+" than "+psItem.getKey()+" "+
    			psAvail.getKey()+" EFFECTIVEDATE:"+date2);
 
    	boolean isok = checkDates(date1, date2, dateRule);	//date1<=date2	
    	if (!isok){
    		//CANNOT_BE_LATER_ERR1 = {0} must not be later than the {1} {2}
    		String errcode="CANNOT_BE_LATER_ERR1";
    		if (dateRule==DATE_GR_EQ){
    			//CANNOT_BE_EARLIER_ERR1 = {0} must not be earlier than the {1} {2} 
    			errcode="CANNOT_BE_EARLIER_ERR1";
    		}

    		args[0]=getLD_NDN(lseoAvail);
    		args[1]=getLD_NDN(psItem);
    		args[2]=getLD_NDN(psAvail);
    		createMessage(checklvl,errcode,args);
    	}
    }*/
	//117			EFFECTIVEDATE	<=	LSEO	LSEOPUBDATEMTRGT	Yes	W	E	E		
	//{LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
    private void checkDates(EntityItem lseo, String lseoAttr, String lseoDate1, EntityItem psItem, EntityItem psAvail,
    		int checklvl, int dateRule) throws SQLException, MiddlewareException
    {
    	String date2 = getAttrValueAndCheckLvl(psAvail, "EFFECTIVEDATE", checklvl);
    	addDebug("checkDates "+lseoAttr+":"+lseoDate1+" can not be "+(dateRule==DATE_GR_EQ?"earlier":"later")+
    			" than "+psItem.getKey()+" psavail: "+psAvail.getKey()+" EFFECTIVEDATE:"+date2);
    	boolean isok = checkDates(lseoDate1, date2, dateRule);	//date1<=date2	
    	if (!isok){
    		//CANNOT_BE_LATER_ERR1 = {0} must not be later than the {1} {2}
    		String errcode="";
    		if (dateRule==DATE_GR_EQ){
    			//CANNOT_BE_EARLIER_ERR1 = {0} must not be earlier than the {1} {2} 
    			errcode="CANNOT_BE_EARLIER_ERR1";
    		}else{
    			errcode="CANNOT_BE_LATER_ERR1";
    		}

    		args[0]=getLD_Value(lseo, lseoAttr, lseoDate1);
    		args[1]=getLD_NDN(psItem);
    		args[2]=getLD_NDN(psAvail);

    		createMessage(checklvl,errcode,args);
    	}
    }
    
    private void checkPsDates(EntityItem rootEntity, Vector psVct, EntityGroup msgGrp, 
    		String lseopubdate,	String lseounpubdate, int checklvl) throws Exception
    {
		for (int i=0; i<psVct.size(); i++){
			EntityItem psitem = (EntityItem)psVct.elementAt(i);
			//		ANNDATE	<=	LSEOPUBDATEMTRGT			W	W	E	
			//28 {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: WWSEO} {NDN: D: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
			//29 {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT} can not be earlier than {LD: LSEO} {NDN: D: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    		//CANNOT_BE_EARLIER_ERR3 = {0} {1} must not be earlier than the {2} {3} {4}  
    		String anndate1 = getAttrValueAndCheckLvl(psitem, "ANNDATE",checklvl);
    		addDebug("checkPsDates (28,29) "+rootEntity.getKey()+" "+
    				"LSEOPUBDATEMTRGT:"+lseopubdate+" cannot be earlier "+
    				psitem.getKey()+" ANNDATE:"+anndate1);
    		boolean isok = checkDates(anndate1, lseopubdate, DATE_LT_EQ);	//date1<=date2	
    		if (!isok){
    			args[0]="";
    			args[1]=getLD_Value(rootEntity, "LSEOPUBDATEMTRGT", lseopubdate);
    			args[2] = msgGrp.getLongDescription();
    			args[3]=getLD_NDN(psitem);
    			args[4]=getLD_Value(psitem, "ANNDATE", anndate1);
				createMessage(checklvl,"CANNOT_BE_EARLIER_ERR3",args);
    		}
    		
			//		WTHDRWEFFCTVDATE	=>	LSEOUNPUBDATEMTRGT			W	W	E	
    		//28 {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} can not be later than {LD: WWSEO} {NDN: D: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
    		//29 {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT} can not be later than {LD: LSEO} {NDN: D: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
	   		String wddate1 = getAttrValueAndCheckLvl(psitem, "WTHDRWEFFCTVDATE",checklvl);
    		addDebug("checkPsDates (28,29) "+rootEntity.getKey()+" "+
    				"LSEOUNPUBDATEMTRGT:"+lseounpubdate+" cannot be later "+
    				psitem.getKey()+" WTHDRWEFFCTVDATE:"+wddate1);
    		isok = checkDates(wddate1, lseounpubdate, DATE_GR_EQ);	//date1=>date2	
    		if (!isok){
    			//CANNOT_BE_LATER_ERR2 = {0} {1} must not be later than the {2} {3} {4}
    			args[0]="";
    			args[1]=getLD_Value(rootEntity, "LSEOUNPUBDATEMTRGT", lseounpubdate);
    			args[2] = msgGrp.getLongDescription();
    			args[3]=getLD_NDN(psitem);
    			args[4]=getLD_Value(psitem, "WTHDRWEFFCTVDATE", wddate1);
				createMessage(checklvl,"CANNOT_BE_LATER_ERR2",args);
    		}
		}
    }
    /*****************
     * 
     * @param rootEntity
     * @param statusFlag
     * @throws Exception
     * 
54	MODEL		WWSEO-u: MODELWWSEO-d									
55	WHEN		COFCAT	=	"Software" (101)						Software SEO	
56	SWPRODSTRUCT	F	WWSEOSWPRODSTRUCT-d								WWSEO: SWPRODSTRUCT	
57	SWPRODSTRUCT	G	LSEOSWPRODSTRUCT-d								LSEO: SWPRODSTRUCT	
									
     */
    private void doSWGAChecks(EntityItem rootEntity, EntityItem wwseoItem,
    		Vector plannedAvailVctA, Vector loAvailVctB, String statusFlag) throws Exception
    {
    	addHeading(3,"Software Model GA Checks:");
      	//56	SWPRODSTRUCT	F	WWSEOSWPRODSTRUCT-d								WWSEO: SWPRODSTRUCT	
    	//57	SWPRODSTRUCT	G	LSEOSWPRODSTRUCT-d								LSEO: SWPRODSTRUCT	
    	Vector wwseoswpsVct = PokUtils.getAllLinkedEntities(wwseoItem, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT");
    	Vector lseoswpsVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOSWPRODSTRUCT", "SWPRODSTRUCT");
    	
    	addDebug("doSWGAChecks wwseoswpsVct "+wwseoswpsVct.size()+" lseoswpsVct "+lseoswpsVct.size());
       	// get the set of countries for lseo planned avails
    	Hashtable lseoPlaAvailCtryTblA =  getAvailByCountry(plannedAvailVctA, getCheck_W_RE_RE(statusFlag));
    	addDebug("doSWGAChecks  lseoPlaAvailCtryTblA: "+lseoPlaAvailCtryTblA.keySet());
    	
    	//58			1	<=	Count Of	F + G		W	E	E		must include at least one {LD: SWFEATURE}
    	if (wwseoswpsVct.size()+lseoswpsVct.size() ==0){
    		args[0] = m_elist.getEntityGroup("SWFEATURE").getLongDescription();
    		//MINIMUM_ERR = must have at least one {0}
    		createMessage(getCheck_W_E_E(statusFlag),"MINIMUM_ERR",args);
    	}
    	addDebug("doSWGAChecks checking lseoswps");
    	addHeading(3,"LSEO "+ m_elist.getEntityGroup("SWPRODSTRUCT").getLongDescription()+" Planned Avail Checks:");
    	//59	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
    	//60	WHEN		AVAILTYPE	=	"Planned Availability"							
    	//61			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
    	//62			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
    	checkGAPsPlaAvails(rootEntity, lseoswpsVct,"SWPRODSTRUCTAVAIL", plannedAvailVctA,  statusFlag);
    	//63	END	60
		
    	addHeading(3,"LSEO "+ m_elist.getEntityGroup("SWPRODSTRUCT").getLongDescription()+" Last Order Avail Checks:");
		//old64	AVAIL		LSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								LSEO: SWPRODSTRUCT	
		//old65	WHEN		AVAILTYPE	=	"Last Order"							
		//old66	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
		//old67	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
		//old68	AND		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
    	//oldmatchPsLastOrderAvail(loAvailVctB,plannedAvailVctA, lseoswpsVct, "SWPRODSTRUCTAVAIL");
		//old69	END	65	
    	//64.00	AVAIL	Q	LSEOSWPRODSTRUCT-d:								LSEO: SWPRODSTRUCT	
    	//64.02			Q: SWPRODSTRUCTAVAIL-d									
    	//65.00	WHEN		AVAILTYPE	=	"Last Order"							
    	//66.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
    	//67.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
    	//67.02	IF		Q: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
    	//67.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
    	//67.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
    	//67.08	THEN											
    	//67.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
    	//67.12	ELSE	67.08										
    	//68.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
    	matchPsLOAvail(loAvailVctB,lseoPlaAvailCtryTblA, lseoswpsVct, "SWPRODSTRUCTAVAIL","SWPRODSTRCATLGOR",CHECKLEVEL_RE,CHECKLEVEL_E);
    	//68.02	END	67.08										
    	//69.00	END	65.00										

	 	addDebug("\ndoSWGAChecks checking wweoswps");
		addHeading(3,"WWSEO "+ m_elist.getEntityGroup("SWPRODSTRUCT").getLongDescription()+" Planned Avail Checks:");
		//70	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
		//71	WHEN		AVAILTYPE	=	"Planned Availability"							
		//72			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		//73			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		checkGAPsPlaAvails(rootEntity, wwseoswpsVct,"SWPRODSTRUCTAVAIL", plannedAvailVctA,  statusFlag);
		//74	END	71	
							
		addHeading(3,"WWSEO "+ m_elist.getEntityGroup("SWPRODSTRUCT").getLongDescription()+" Last Order Avail Checks:");
		//old75	AVAIL		WWSEOLSEO-u:WWSEOSWPRODSTRUCT-d:SWPRODSTRUCTAVAIL-d								WWSEO: SWPRODSTRUCT	
		//old76	WHEN		AVAILTYPE	=	"Last Order"							
		//old77	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
		//old78	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
		//old79			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
		//old80	END	76		
		//oldmatchPsLastOrderAvail(loAvailVctB,plannedAvailVctA, wwseoswpsVct, "SWPRODSTRUCTAVAIL");
		//old81	END	55
		//75.00	AVAIL	R	WWSEOLSEO-u: WWSEOSWPRODSTRUCT-d								WWSEO: SWPRODSTRUCT	
		//75.02			R: SWPRODSTRUCTAVAIL-d									
		//76.00	WHEN		AVAILTYPE	=	"Last Order"							
		//77.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
		//78.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
		//78.02	IF		R: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
		//78.04	AND		OFFCOUNTRY	IN	B: AVAIL	COUNTRYLIST						
		//78.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
		//78.08	THEN											
		//78.10			PUBTO	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR} 
		//78.12	ELSE	78.08										
		//79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} must not be later than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
		matchPsLOAvail(loAvailVctB,lseoPlaAvailCtryTblA, wwseoswpsVct, "SWPRODSTRUCTAVAIL","SWPRODSTRCATLGOR",CHECKLEVEL_RE,CHECKLEVEL_E);
		//79.02	END	78.08										
		//80.00	END	76.00										
	
		wwseoswpsVct.clear();
		lseoswpsVct.clear();
		lseoPlaAvailCtryTblA.clear();
    }
    
/*
82	MODEL		WWSEO-u: MODELWWSEO-d									
83	WHEN		COFCAT	=	"Service" (102)						Service LSEO	
deleted									
									
106	END	83										

 * /    
    private void doSVCGAChecks(EntityItem rootEntity, EntityItem wwseoItem,
    		Vector plannedAvailVctA, Vector loAvailVctB, String statusFlag) throws Exception
    {
    	addHeading(3,"Service Model GA Checks:");
    	Vector wwseosvcpsVct = PokUtils.getAllLinkedEntities(wwseoItem, "WWSEOSVCPRODSTRUCT", "SVCPRODSTRUCT");
    	Vector lseosvcpsVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOSVCPRODSTRUCT", "SVCPRODSTRUCT");
  
    	addDebug("doSVCGAChecks wwseosvcpsVct "+wwseosvcpsVct.size()+" lseosvcpsVct "+lseosvcpsVct.size());
    	
    	addHeading(3,"LSEO "+ m_elist.getEntityGroup("SVCPRODSTRUCT").getLongDescription()+" Planned Avail Checks:");
    	//84	AVAIL		LSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d								LSEO: SVCPRODSTRUCT	
    	//85	WHEN		AVAILTYPE	=	"Planned Availability"							
    	//86			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
    	//87			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
    	checkGAPsPlaAvails(rootEntity, lseosvcpsVct,"SVCPRODSTRUCTAVAIL", plannedAvailVctA,  statusFlag);
    	//88	END	85
		
    	addHeading(3,"LSEO "+ m_elist.getEntityGroup("SVCPRODSTRUCT").getLongDescription()+" Last Order Avail Checks:");
		//89	AVAIL		LSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d								LSEO: SVCPRODSTRUCT	
		//90	WHEN		AVAILTYPE	=	"Last Order"							
		//91	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
		//92	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
		//93	AND		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
		matchPsLastOrderAvail(loAvailVctB,plannedAvailVctA, lseosvcpsVct, "SVCPRODSTRUCTAVAIL");
		//94	END	90	
		
		addHeading(3,"WWSEO "+ m_elist.getEntityGroup("SVCPRODSTRUCT").getLongDescription()+" Planned Avail Checks:");
		//95	AVAIL		WWSEOLSEO-u:WWSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d								WWSEO: SVCPRODSTRUCT	
		//96	WHEN		AVAILTYPE	=	"Planned Availability"							
		//97			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: AVAIL} {NDN: A: AVAIL} can not be earlier than {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		//98			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that is not in  {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		checkGAPsPlaAvails(rootEntity, wwseosvcpsVct, "SVCPRODSTRUCTAVAIL", plannedAvailVctA,  statusFlag);
		//99	END	96	
		
    	addHeading(3,"WWSEO "+ m_elist.getEntityGroup("SVCPRODSTRUCT").getLongDescription()+" Last Order Avail Checks:");
		//100	AVAIL		WWSEOLSEO-u:WWSEOSVCPRODSTRUCT-d:SVCPRODSTRUCTAVAIL-d								WWSEO: SVCPRODSTRUCT	
		//101	WHEN		AVAILTYPE	=	"Last Order"							
		//102	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
		//103	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is being withdrawn and must have a corresponding {LD: LSEO} {LD: AVAIL} "Last Order" by Country
		//104	AND		EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: AVAIL} {NDN: B: AVAIL} can not be later than the {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} 
		matchPsLastOrderAvail(loAvailVctB,plannedAvailVctA, wwseosvcpsVct, "SVCPRODSTRUCTAVAIL");
		//105	END	101	
    	
    	wwseosvcpsVct.clear();
    	lseosvcpsVct.clear();
    }*/

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "LSEO ABR.";

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
