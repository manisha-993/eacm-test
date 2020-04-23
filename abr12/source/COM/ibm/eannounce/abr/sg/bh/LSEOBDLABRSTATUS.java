// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import java.sql.SQLException;
import java.util.*;

/**
LSEOBDLABRSTATUS_class=COM.ibm.eannounce.abr.sg.LSEOBDLABRSTATUS
LSEOBDLABRSTATUS_enabled=true
LSEOBDLABRSTATUS_idler_class=A
LSEOBDLABRSTATUS_keepfile=true
LSEOBDLABRSTATUS_report_type=DGTYPE01
LSEOBDLABRSTATUS_vename=EXRPT3LSEOBDL1
LSEOBDLABRSTATUS_CAT1=RPTCLASS.LSEOBDLABRSTATUS
LSEOBDLABRSTATUS_CAT2=
LSEOBDLABRSTATUS_CAT3=RPTSTATUS
LSEOBDLABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
 
*/

/**********************************************************************************
* LSEOBDLABRSTATUS class
* 
* BH FS ABR Data Quality 20120116.doc - XCC_LIST2
 *BH FS ABR Data Quality 20111020e.xls BH Defect 67890 (support for old data)
 *sets changes
* BH FS ABR Data Quality Checks 20110825.xls - support of the Solutions requirements.
* 	47.00	WHEN		SPECBID	=	"Yes" (11458)						LSEO Special Bid	
Add 20110823	47.20			E E E A GA Bundle can not include a Special  Bid LSEO (WWSEO)	A GA Bundle can not include a Special  Bid LSEO {LD: LSEO} {NDN: LSEO} 
Deleted keys 48.00, 49.00 and 50.00

added checking counts of model types key 83.00
* 
* BH FS ABR Data Quality 20110322.doc
* Delete checks 6.02 and 6.06 and validateOS chg
* 
* from BH FS ABR Catalog Attr Derivation 20110221b.doc
* need updated EXRPT3LSEOBDL1
* 
* From "BH FS ABR Data Quality Checks 20110311.xls" - MN IN495108
* 
* From "SG FS ABR Data Quality 20101112.doc"
* need updated EXRPT3LSEOBDL1.. add mm, img and fb
* 
*From "SG FS ABR Data Quality 20100615.doc"
*needs workflow actions for lifecycle
*needs LIFECYCLE attribute and transitions
* From "SG FS ABR Data Quality 20090729.doc"
*A.	Checking

The LSEOBUNDLE checking has the following variations to deal with:
1.	The LSEOBUNDLE may be a Special Bid (or Generally Available) which determines if it does 
not have (or has) children AVAILs.
LSEOBUNDLE SPECBID indicates this.
e.g. Key 7
2.	The included LSEOs may be: Hardware, Software or Service. Bundle Type (BUNDLETYPE) needs to be verified.
e.g. Key 6
3.	The OSLEVEL is based on a SWFEATURE that is in one of the LSEOs and needs to be verified.
e.g. Key 5
4.	LSEOs can be Special Bids (no AVAILs) or GA (with AVAILs) for date checking
e.g. Key 29

1.	OSLEVEL

See the Section of Rules.

e.g. Key 5

2.	BUNDLETYPE

See the Section of Rules.

e.g. Key 6

*
*/
//LSEOBDLABRSTATUS.java,v
//Revision 1.18  2011/03/24 23:18:24  wendy
//BH FS ABR Data Quality Checks 20110322.xls updates
//
//Revision 1.16  2011/03/14 19:46:19  wendy
//MN IN495108 - dont check IMG if BUNDLETYPE does not have HW
//
//Revision 1.11  2011/01/05 13:50:31  wendy
//make sure lseo has wwseo parent
//
//Revision 1.9  2010/12/02 14:48:35  wendy
//SG FS ABR Data Quality 20101112.doc updates
//
//Revision 1.6  2010/01/21 22:20:26  wendy
//updates for BH FS ABR Data Quality 20100120.doc
//
//Revision 1.2  2009/12/07 22:49:56  wendy
//Updated for spec chg BH FS ABR Data Qualtity 20091207b.xls
//
//Revision 1.1  2009/08/17 15:36:32  wendy
//SR10, 11, 12, 15, 17 BH updates phase 4
//
//
public class LSEOBDLABRSTATUS  extends DQABRSTATUS
{
	private EntityList mdlList;
	
	//BUNDLETYPE	100	Hardware
	//BUNDLETYPE	101	Software
	//BUNDLETYPE	102	Service

	private static final Set TESTSET;
	private static final Set HWTESTSET;
	
	static{
		TESTSET = new HashSet();
		TESTSET.add(HARDWARE);
		TESTSET.add(SOFTWARE);//MN38219508
		HWTESTSET = new HashSet();
		HWTESTSET.add(HARDWARE); //MN IN495108
	}
	
	/**********************************
	* check all states
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/*
from sets ss
104.00		LSEOBUNDLE		Root Entity						
105.00		Perform			LSEOBUNDLE				SAPASSORTMODULEPRIOR	
106.00		Section		One						
107.00	R1.0	SET			LSEOBUNDLE				COMPATGENABR	&COMPATGENRFR
109.00		SET			LSEOBUNDLE				EPIMSABRSTATUS	
110.00		IF			LSEOBUNDLE	SPECBID	=	"No" (11457)		
111.00		IF		LSEOBUNDLEAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)		
112.00		AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)		
112.20		AND			LSEOBUNDLE	STATUS	=	"Final" (0020)		
112.22		AND		LSEOBUNDLEAVAIL-d:	AVAIL	STATUS	=	"Final" (0020)		
112.24		SET			ANNOUNCEMENT				WWPRTABRSTATUS	
113.00		END	111.00							
114.00		IF		LSEOBUNDLEAVAIL-d	AVAIL	AVAILTYPE	=	"Planned Availability" (146)		
115.00		AND		LSEOBUNDLEAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)		
116.00		AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)		
116.20		AND			LSEOBUNDLE	STATUS	=	"Final" (0020)		
116.22		AND		LSEOBUNDLEAVAIL-d:	AVAIL	STATUS	=	"Final" (0020)		
116.24		SET			ANNOUNCEMENT				QSMRPTABRSTATUS 	
117.00		END	114.00							
118.00		IF		LSEOBUNDLEAVAIL-d	AVAIL	AVAILTYPE	=	"Last Order" (149)		
119.00		AND		LSEOBUNDLEAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)		
120.00		AND			ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)		
120.02		AND			LSEOBUNDLE	STATUS	=	"Final" (0020)		
120.04		AND		LSEOBUNDLEAVAIL-d:	AVAIL	STATUS	=	"Final" (0020)		
120.06		SET			ANNOUNCEMENT				QSMRPTABRSTATUS 	
121.00		END	118.00							
Change 2011-10-20		121.240	IF			LSEOBUNDLE	STATUS	=	"Final" (0020)			
Add 2011-10-20		121.242	IF			LSEOBUNDLE	BUNDLPUBDATEMTRGT	>	"2010-03-01" | Empty			
Add 2011-10-20		121.244	IF	A	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020)			
		121.250	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)			
		121.260	OR		A: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020)			
		121.270	SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
Add 2011-10-20		121.271	END	121.250								
Add 2011-10-20		121.272	END	121.244								
Add 2011-10-20		121.273	ELSE	121.242								
Add 2011-10-20		121.274	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEED	
Add 2011-10-20		121.275	END	121.242								
Change 2011-10-20		121.280	END	121.240								
						
		121.500	IF			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
		121.520	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
Add 2011-10-20		121.522	IF			LSEOBUNDLE	BUNDLPUBDATEMTRGT	>	"2010-03-01" | Empty		
Change 2011-10-20		121.540	IF		LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
		121.550	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
		121.560	OR		A: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020) | "Ready for Review" (0040)		
		121.580	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
Add 2011-10-20		121.582	END	121.550							
Add 2011-10-20		121.584	END	121.540							
Add 2011-10-20		121.586	ELSE	121.522							
Add 2011-10-20		121.588	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
Add 2011-10-20		121.590	END	121.522							
		121.600	END	121.500							
						
122.00		ELSE	110.00		LSEOBUNDLE				WWPRTABRSTATUS	
123.00		SET			LSEOBUNDLE				QSMRPTABRSTATUS 	
123.18		IF			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
123.19		IF			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
123.20	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
123.22		ELSE	123.18							
123.23		IF			LSEOBUNDLE	STATUS	=	"Final"		
123.24	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	
123.26		END	123.18							
124.00		END	110.00							
125.00		END	106.00	Section One						
126.00		END	104.00	LSEOBUNDLE						

	 */
    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
    *D.	STATUS changed to Ready for Review
    */
    protected void completeNowR4RProcessing()throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		//106.00	187 Section		One	
		doLSEOBDLSectionOne(m_elist.getParentEntityGroup().getEntityItem(0));
		//	125.00	END	187	Section One	
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
		//105.00	Perform			LSEOBUNDLE				SAPASSORTMODULEPRIOR	norfr	SetPriorAssortModule
		checkAssortModule();
		
		//106.00  Section		One	
		doLSEOBDLSectionOne(m_elist.getParentEntityGroup().getEntityItem(0));
		//125.00	END	106	Section One	
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
		//	NOW() <= Bundle Unpublish Date - Target (BUNDLUNPUBDATEMTRGT)
		String wdDate = PokUtils.getAttributeValue(rootEntity, "BUNDLUNPUBDATEMTRGT", "", FOREVER_DATE, false);
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
    protected String getLCRFRWFName(){ return "WFLCLSEOBDLRFR";}
    protected String getLCFinalWFName(){ return "WFLCLSEOBDLFINAL";}

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
1	LSEOBUNDLE		Root									
2			BUNDLPUBDATEMTRGT									
3			BUNDLUNPUBDATEMTRGT	=>	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		{LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT} can not be earlier than the{LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
4			COUNTRYLIST									
5			OSLEVEL	Validate				W	W	E		{LD: OSLEVEL} {OSLEVEL} does not match the Software LSEO's OS
6			BUNDLETYPE	Validate				W	W	E		
	1) {LD: BUNDLETYPE} does not reflect all of the LSEO's MODEL's {LD: COFCAT}
	2) {LD: BUNDLETYPE} has a value that does not exist in any LSEO's MODEL's {LD: COFCAT}
	
Delete 20110322 6.02	IF		"Hardware" (100) or "Software" (101)	IN	LSEOBUNDLE	BUNDLETYPE						
6.04			"00 - No Product Hierarchy Code" (BHPH0000)	<>	LSEOBUNDLE	BHPRODHIERCD		E	E	E		must not have {LD: BHPRODHIERCD} {BHPRODHIERCD}
Delete 20110322 6.06	END	6.02	

42	LSEO		LSEOBUNDLELSEO-d								LSEO	
43			Count Of	=>	2			E	E	E		must have two or more {LD: LSEO}
44			STATUS	=>	LSEOBUNDLE	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
45			COUNTRYLIST	Contains	LSEOBUNDLE	COUNTRYLIST		W	W	E	{LD: COUNTRYLIST} must be included in the {LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST}

80	LSEOBUNDLE		Root									
7	WHEN		SPECBID	=	"No" (11457)						LSEOBUNDLE GA	
81			SAPASSORTMODULE	Validate				E	E	E		is not a Special Bid, but it does not have a value for LD(SAPASSORTMODULE)
8	SEOCG		SEOCGBDL-u								SEOCG	
9	IF		"Hardware" (100) or "Software" (101)	IN	LSEOBUNDLE	BUNDLETYPE						
10	THEN		Count of	=>	1			W	W	E	Hardware or Software	must be included in a {LD: SEOCG}
11	ELSE		Count of	=	0			E	E	E	Service	must not be included in a {LD: SEOCG}
12	AVAIL	A	LSEOBUNDLEAVAIL-d								Bundle AVAIL	
13	WHEN		AVAILTYPE	=	"Planned Availability"							
14			Count of	=>	1			RW	RW	RE		must have at least one "Planned Availability"
15			EFFECTIVEDATE	=>	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
16			COUNTRYLIST	in	LSEOBUNDLE	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL}  includes a Country that is not in {LD: COUNTRYLIST}
17	END	13										
18	AVAIL		LSEOBUNDLEAVAIL-d								Bundle AVAIL	
19	WHEN		AVAILTYPE	=	"First Order"							
20			EFFECTIVEDATE	=>	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
21			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL}  includes a Country that is not in {LD: COUNTRYLIST}
22	END	19										
23	AVAIL	B	LSEOBUNDLEAVAIL-d								Bundle AVAIL	
24	WHEN		AVAILTYPE	=	"Last Order"							
25			EFFECTIVEDATE	<=	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL} can not be later than the {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
26			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
27			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL}  includes a Country that is not in {LD: AVAIL} {NDN: A: AVAIL}
28	END	24	
28.20	IMG		LSEOBUNDLEIMG-d								IMG	
28.24	IF		PDHDOMAIN	IN	ABR_PROPERTITIES	XCC_LIST2					XCC Unique Rules	
28.25	AND		"Hardware" (100)	IN	LSEOBUNDLE	BUNDLETYPE
Add	28.28			CountOf	=>	1		W	W	E				Required if XCC	must have at least one Image
Add	28.32	END										Optional for all others	
Add	28.36			UniqueCoverage		IMG		Yes		W	E		{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
Add	28.40			COUNTRYLIST	"Containsaggregate E"	A: AVAIL	COUNTRYLIST			W	E		must have a {LD: IMG} for every country in the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.44			MIN(PUBFROM)	<=	A: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.48			MAX(PUBTO)	=>	B: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: C: AVAIL}
Add	28.52	MM		LSEOBUNDLEMM-d								MM	
Add	28.56			UniqueCoverage		MM		Yes		W	E	Optional	{LD: MM} {NDN: MM} have gaps in the date range or they overlap.
Add	28.60			COUNTRYLIST	"Containsaggregate E"	A: AVAIL	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: MM} for every country in the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.64			MIN(PUBFROM)	<=	A: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: MM} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.68			MAX(PUBTO)	=>	B: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: MM} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: C: AVAIL}
Add	28.72	FB		LSEOBUNDLEFB-d								FB	
Add	28.76			UniqueCoverage		FB		Yes		W	E	Optional	{LD: FB} {NDN: FB} have gaps in the date range or they overlap.
Add	28.80			COUNTRYLIST	"Containsaggregate E"	A: AVAIL	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: FB} for every country in the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.84			MIN(PUBFROM)	<=	A: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: FB} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.88			MAX(PUBTO)	=>	B: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: FB} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: C: AVAIL}
									
29	WWSEO		LSEOBUNDLELSEO-d: WWSEO-u									
30	WHEN		SPECBID	=	"No" (11457)						LSEO GA	
31	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
32	WHEN		AVAILTYPE	=	"Planned Availability"							
33			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
34			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
35	END	32
Change 20111213	36.00	AVAIL		LSEOBUNDLELSEO-d: LSEOAVAIL-d		LSEO AVAIL
37	WHEN		AVAILTYPE	=	"Last Order"							
38	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
39	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must have a corresponding {LD: LSEOBUNDLE} {LD: AVAIL} by Country
40			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
41	END	37										
46	END	30										
	47.00	WHEN		SPECBID	=	"Yes" (11458)						LSEO Special Bid	
Add 20110823	47.20											A GA Bundle can not include a Special  Bid LSEO (WWSEO)	A GA Bundle can not include a Special  Bid LSEO {LD: LSEO} {NDN: LSEO} 
Delete 20110823	48.00			LSEOPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {NDN: LSEOPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Delete 20110823	49.00			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Delete 20110823	50.00			LSEOUNPUBDATEMTRGT	=>	B: AVAIL	EFFECTIVEDATE		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {NDN: LSEOUNPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
	51.00	END	47.00										
									
52	END	7	
									
53	LSEOBUNDLE		Root								LSEOBUNDLE Special Bid	
54	WHEN		SPECBID	=	"Yes" (11458)							
55	WWSEO		LSEOBUNDLE-d: WWSEO-u									
56	WHEN		SPECBID	=	"Yes" (11458)						LSEO Special Bid	
57	LSEO		LSEOBUNDLELSEO-d									
58			LSEOPUBDATEMTRGT	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {NDN: LSEOPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {NDN: A: BUNDLPUBDATEMTRGT}
59			COUNTRYLIST	Contains	LSEOBUNDLE	COUNTRYLIST		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
60			LSEOUNPUBDATEMTRGT	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {NDN: LSEOUNPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {NDN: A:BUNDLUNPUBDATEMTRGT}
61	END	56										
62	WWSEO		LSEOBUNDLELSEO-d: WWSEO-u									
63	WHEN		SPECBID	=	"No" (11457)						LSEO GA	
64	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
65	WHEN		AVAILTYPE	=	"Planned Availability"							
66			EFFECTIVEDATE	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes	W	E	E		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: LSEOBUNDLE} {BUNDLPUBDATEMTRGT}
67			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST		W	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
68	END	65										
69	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
70	WHEN		AVAILTYPE	=	"Last Order"							
71			EFFECTIVEDATE	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes	W	W	E		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
72			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST		W	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
73	END	70										
74	END	63	
Add	75.00			LSEOBUNDLEIMG-d								IMG	
Add	75.02			UniqueCoverage		IMG		Yes		W	E	Optional	{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
75.04			COUNTRYLIST	"Containsaggregate E"	LSEOBUNDLE	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	"must have a {LD: IMG} for every country in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
Note: identify the country that does not have an Image"
Add	75.06			MIN(PUBFROM)	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT {BUNDLPUBDATEMTRGT
Add	75.08			MAX(PUBTO)	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
Add	75.10	MM		LSEOBUNDLEMM-d								MM	
Add	75.12			UniqueCoverage		MM		Yes		W	E	Optional	{LD: MM} {NDN: MM} have gaps in the date range or they overlap.
75.14			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	"must have a {LD: MM} for every country in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
Note: identify the country that does not have a MM"
Add	75.16			MIN(PUBFROM)	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT {BUNDLPUBDATEMTRGT
Add	75.18			MAX(PUBTO)	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
Add	75.20	FB		LSEOBUNDLEFB-d								FB	
Add	75.22			UniqueCoverage		FB		Yes		W	E	Optional	{LD: FB} {NDN: FB} have gaps in the date range or they overlap.
75.24			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	"must have a {LD: FB} for every country in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
Note: identify the country that does not have a FB"
75.26			MIN(PUBFROM)	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
75.28			MAX(PUBTO)	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBTO as late as or later than the  LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
									
79	END	54										
82	MODEL		LSEOBUNDLELSEO-d: WWSEO-u: MODELWWSEO									
Change 20110823	83.00	IF		COFCAT	=	"HARDWARE"						
Change 20110823	84.00			CountOf	=	1			E	E	E	Counts all LSEOs that are Hardware and lists them if there is more than one
Add 20110823	84.20	WHEN		COFCAT	=	"Software"						
Add 20110823	84.22			CountOf	=>	1			RE	RE	RE	Counts all LSEOs that are Software and throw and error if there is none
Add 20110823	84.24	END	84.20									
Add 20110823	84.26	ELSE	83.00									
Add 20110823	84.40	IF		COFCAT	=	"Software"						
Add 20110823	84.42			CountOf	=>	2			E	E	E	Counts all LSEOs that are Software and  throw an error if less than two
Add 20110823	84.44	ELSE	84.40									
Add 20110823	84.60	IF		COFCAT	=	"Service"						
Add 20110823	84.64			CountOf	=>	2			E	E	E	Counts all LSEOs that are Service and and  throw an error if less than two
Add 20110823	84.66	ELSE	84.60									
Add 20110823	84.80											Should not happen
Add 20110823	84.82	END	84.60									
Add 20110823	84.84	END	84.40									
Change 20110823	85.00	END	83.00										

	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
 		int checklvl = getCheck_W_W_E(statusFlag); 
	
		addDebug("checking "+rootEntity.getKey()+" (3)");
		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");
		//3			BUNDLUNPUBDATEMTRGT	=>	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		
    	//{LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT} can not be earlier than the{LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
		checkCanNotBeEarlier(rootEntity, "BUNDLUNPUBDATEMTRGT", "BUNDLPUBDATEMTRGT", checklvl);
    						
		// get VE2 to go from WWSEO-MODELWWSEO-MODEL and other MODEL links
		getModelVE(rootEntity);
		
		//5			OSLEVEL	Validate				W	W	E		
		//{LD: OSLEVEL} {OSLEVEL} does not match the Software LSEO's OS
		validateOS(rootEntity, checklvl);
		
    	//6			BUNDLETYPE	Validate				W	W	E		
    	//1) {LD: BUNDLETYPE} does not reflect all of the LSEO's MODEL's {LD: COFCAT}
    	//2) {LD: BUNDLETYPE} has a value that does not exist in any LSEO's MODEL's {LD: COFCAT}
		validateBUNDLETYPE(rootEntity, checklvl);
    	
	   	EntityGroup lseoGrp = m_elist.getEntityGroup("LSEO");
	   	
	  	ArrayList lseoBdlCtry = new ArrayList();
    	// fill in list with the lseobdl's countries
    	getCountriesAsList(rootEntity, lseoBdlCtry,checklvl);
    	
    	addHeading(2,lseoGrp.getLongDescription()+" Checks:");
    	
		//42	LSEO		LSEOBUNDLELSEO-d								LSEO
	   	//repeated	75	LSEO		LSEOBUNDLELSEO-d								LSEO	
		//43			Count Of	=>	2			E	E	E		must have two or more {LD: LSEO}
	   	int cnt = getCount("LSEOBUNDLELSEO");
	   	if(cnt <2) {
	   		//MINIMUM_TWO_ERR = must have two or more {0}
	   		args[0] = lseoGrp.getLongDescription();
	   		createMessage(CHECKLEVEL_E,"MINIMUM_TWO_ERR",args);
	   	}else{
	   		//make sure not linked twice
	   		cnt = getCount("LSEO");
		   	if(cnt <2) {
		   		//MINIMUM_TWO_ERR = must have two or more {0}
		   		args[0] = lseoGrp.getLongDescription();
		   		createMessage(CHECKLEVEL_E,"MINIMUM_TWO_ERR",args);
		   	}
	   	}
		
	   	for (int i=0; i<lseoGrp.getEntityItemCount(); i++){
	   		EntityItem lseo = lseoGrp.getEntityItem(i);
	   		//44			STATUS	=>	LSEOBUNDLE	DATAQUALITY		E	E	E {LD: STATUS} can not be higher than the {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
			checkStatusVsDQ(lseo, "STATUS", rootEntity,CHECKLEVEL_E);
			ArrayList lseoCtry = new ArrayList();
	    	// fill in list with the lseo's countries
	    	getCountriesAsList(lseo, lseoCtry,checklvl);
	    	//EACH LSEO must have all LSEOBUNDLE countries
			//45	COUNTRYLIST	Contains	LSEOBUNDLE	COUNTRYLIST		W	W	E	{LD: COUNTRYLIST} must be included in the {LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST} 
    		if (!lseoCtry.containsAll(lseoBdlCtry)){
    			addDebug("lseo (45)  "+lseo.getKey()+" ctrylist "+lseoCtry+" did not contain lseobdl list "+lseoBdlCtry);
    			//MUST_INCLUDE_ERR={0} must be included in the {1} {0}
    			args[0] =PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
    			args[1]=getLD_NDN(lseo);
    			createMessage(checklvl,"MUST_INCLUDE_ERR",args);
    		}

    		lseoCtry.clear();
	   	}

		String specbid = getAttributeFlagEnabledValue(rootEntity, "SPECBID");
		addDebug(rootEntity.getKey()+" SPECBID: "+specbid);
		//7	WHEN		SPECBID	=	"No" (11457)						LSEOBUNDLE GA	
		// to
		//52	END	7
		if (SPECBID_NO.equals(specbid)){  // is No
			addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" GA Checks:");
			doNotSpecBidChecks(rootEntity, statusFlag);
		}else{ // specbid=yes
			//53	LSEOBUNDLE		Root								LSEOBUNDLE Special Bid	
			//54	WHEN		SPECBID	=	"Yes" (11458)
			// to
			//79	END	54
			addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Special Bid Checks:");
			doSpecBidChecks(rootEntity, statusFlag);
		}
		
		//82-85
		addHeading(2,m_elist.getEntityGroup("LSEO").getLongDescription()+" "+
				PokUtils.getAttributeDescription(m_elist.getEntityGroup("MODEL"), "COFCAT", "COFCAT")
				+" Check:");
		checkModels();

		mdlList.dereference(); // not needed any more
	}

	/**
	 * 
Change 20110823	83.00	IF		COFCAT	=	"HARDWARE"						
Change 20110823	84.00			CountOf	=	1			E	E	E	Counts all LSEOs that are Hardware and lists them if there is more than one
Add 20110823	84.20	WHEN		COFCAT	=	"Software"						
Add 20110823	84.22			CountOf	=>	1			RE	RE	RE	Counts all LSEOs that are Software and throw and error if there is none
Add 20110823	84.24	END	84.20									
Add 20110823	84.26	ELSE	83.00									
Add 20110823	84.40	IF		COFCAT	=	"Software"						
Add 20110823	84.42			CountOf	=>	2			E	E	E	Counts all LSEOs that are Software and  throw an error if less than two
Add 20110823	84.44	ELSE	84.40									
Add 20110823	84.60	IF		COFCAT	=	"Service"						
Add 20110823	84.64			CountOf	=>	2			E	E	E	Counts all LSEOs that are Service and and  throw an error if less than two
Add 20110823	84.66	ELSE	84.60									
Add 20110823	84.80											Should not happen
Add 20110823	84.82	END	84.60									
Add 20110823	84.84	END	84.40									
Change 20110823	85.00	END	83.00			

	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void checkModels() throws SQLException, MiddlewareException{
	    // get MODELs from VE2  LSEOBUNDLELSEO-d: WWSEOLSEO-u: MODELWWSEO-u: MODEL
		EntityGroup lseobdllseoGrp = mdlList.getEntityGroup("LSEOBUNDLELSEO");

		// look for duplicate links to lseo too
		Vector hwlseoVct = new Vector(1);
		Vector swlseoVct = new Vector(1);
		Vector svclseoVct = new Vector(1);
		// find MODEL.COFCAT = 100 (Hardware)
		for (int i=0; i<lseobdllseoGrp.getEntityItemCount(); i++){
			EntityItem lseobdllseoItem = lseobdllseoGrp.getEntityItem(i);
			for (int ll=0; ll<lseobdllseoItem.getDownLinkCount(); ll++){
				EntityItem lseoItem = (EntityItem)lseobdllseoItem.getDownLink(ll);
				if (lseoItem.getEntityType().equals("LSEO")){
					Vector wwseoVct = PokUtils.getAllLinkedEntities(lseoItem, "WWSEOLSEO", "WWSEO");
					Vector mdlVct = PokUtils.getAllLinkedEntities(wwseoVct, "MODELWWSEO", "MODEL");
					addDebug("checkModels "+lseobdllseoItem.getKey()+" "+
							lseoItem.getKey()+" wwseoVct: "+wwseoVct.size()+" mdlVct: "+mdlVct.size());
					for (int m=0; m<mdlVct.size(); m++){
						EntityItem mdlItem = (EntityItem)mdlVct.elementAt(m);
						String cofcat = getAttributeFlagEnabledValue(mdlItem , "COFCAT");
						addDebug("checkModels "+mdlItem.getKey()+" COFCAT: "+cofcat);
						if(HARDWARE.equals(cofcat)){
							hwlseoVct.add(lseoItem);
						}else if(SOFTWARE.equals(cofcat)){
							swlseoVct.add(lseoItem);
						}else if(SERVICE.equals(cofcat)){
							svclseoVct.add(lseoItem);
						}
					}

					wwseoVct.clear();
					mdlVct.clear();
				}
			}
		}

		addDebug("checkModels hwlseoVct "+hwlseoVct.size()+" swlseoVct: "+swlseoVct.size()+" svclseoVct: "+svclseoVct.size());
		//83.00	IF		COFCAT	=	"HARDWARE"							
		//84.00			CountOf	=	1			E	E	E	Counts all LSEOs that are Hardware and lists them if there is more than one	
		//	must have only one Hardware {LD: LSEO} {NDN: LSEO}																
		if (hwlseoVct.size()>0){
			if (hwlseoVct.size()>1){
				//LSEO_HWMDL_ERR = must have only one Hardware {0}
				for (int m=0; m<hwlseoVct.size(); m++){
					EntityItem lseo = (EntityItem)hwlseoVct.elementAt(m);
					args[0]=getLD_NDN(lseo);
					createMessage(CHECKLEVEL_E,"LSEO_HWMDL_ERR",args);
				}
			}
			//84.20	WHEN		COFCAT	=	"Software"						
			//84.22			CountOf	=>	1			RE	RE	RE	Counts all LSEOs that are Software and throw and error if there is none
			if (swlseoVct.size()==0){
				//LSEO_SWMDL_ERR = must have one or more Software {0}
				args[0]=m_elist.getEntityGroup("LSEO").getLongDescription();
				createMessage(CHECKLEVEL_RE,"LSEO_SWMDL_ERR",args);
			}
			//84.24	END	84.20
		}else{					
			//84.26	ELSE	83.00									
			//84.40	IF		COFCAT	=	"Software"						
			//84.42			CountOf	=>	2			E	E	E	Counts all LSEOs that are Software and  throw an error if less than two
			//84.44	ELSE	84.40									
			if (swlseoVct.size()>0 && swlseoVct.size()<2){
				//LSEO_SWMDL2_ERR =must have two or more Software {0}
				args[0]=m_elist.getEntityGroup("LSEO").getLongDescription();
				createMessage(CHECKLEVEL_E,"LSEO_SWMDL2_ERR",args);
			}

			//84.60	IF		COFCAT	=	"Service"						
			//84.64			CountOf	=>	2			E	E	E	Counts all LSEOs that are Service and and  throw an error if less than two
			//ELSE	84.60										
			if (svclseoVct.size()>0 && svclseoVct.size()<2){
				//LSEO_SVCMDL_ERR =must have two or more Service {0}
				args[0]=m_elist.getEntityGroup("LSEO").getLongDescription();
				createMessage(CHECKLEVEL_E,"LSEO_SVCMDL_ERR",args);
			}
			//84.80											Should not happen	Illegal Fixed Solution Bundle
			//84.82	END	84.60										
			//84.84	END	84.40										
			//85.00	END	83.00
		} // end not hw lseo
		
		hwlseoVct.clear();
		swlseoVct.clear();
		svclseoVct.clear();
	}
    /****************************
     * 
     * @param rootEntity
     * @param statusFlag
     * @throws Exception
     * 
53	LSEOBUNDLE		Root								LSEOBUNDLE Special Bid	
54	WHEN		SPECBID	=	"Yes" (11458)							
55	WWSEO		LSEOBUNDLE-d: WWSEO-u									
56	WHEN		SPECBID	=	"Yes" (11458)						LSEO Special Bid	
57	LSEO		LSEOBUNDLELSEO-d									
58			LSEOPUBDATEMTRGT	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {NDN: LSEOPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {NDN: A: BUNDLPUBDATEMTRGT}
59			COUNTRYLIST	Contains	LSEOBUNDLE	COUNTRYLIST		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
60			LSEOUNPUBDATEMTRGT	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {NDN: LSEOUNPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {NDN: A:BUNDLUNPUBDATEMTRGT}
61	END	56	
									
62	WWSEO		LSEOBUNDLELSEO-d: WWSEO-u									
63	WHEN		SPECBID	=	"No" (11457)						LSEO GA	
64	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
65	WHEN		AVAILTYPE	=	"Planned Availability"							
66			EFFECTIVEDATE	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes	W	E	E		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: LSEOBUNDLE} {BUNDLPUBDATEMTRGT}
67			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST		W	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
68	END	65	
									
69	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
70	WHEN		AVAILTYPE	=	"Last Order"							
71			EFFECTIVEDATE	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes	W	W	E		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
72			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST		W	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
73	END	70										
74	END	63										
Add	75.00			LSEOBUNDLEIMG-d								IMG	
Add	75.02			UniqueCoverage		IMG		Yes		W	E	Optional	{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
Add	75.04			COUNTRYLIST	"Containsaggregate E"	LSEOBUNDLE	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	"must have a {LD: IMG} for every country in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
Note: identify the country that does not have an Image"
Add	75.06			MIN(PUBFROM)	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT {BUNDLPUBDATEMTRGT
Add	75.08			MAX(PUBTO)	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
Add	75.10	MM		LSEOBUNDLEMM-d								MM	
Add	75.12			UniqueCoverage		MM		Yes		W	E	Optional	{LD: MM} {NDN: MM} have gaps in the date range or they overlap.
75.14			COUNTRYLIST	"Containsaggregate E"	LSEOBUNDLE	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: MM} for every country in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
Note: identify the country that does not have a MM"
Add	75.16			MIN(PUBFROM)	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT {BUNDLPUBDATEMTRGT
Add	75.18			MAX(PUBTO)	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
Add	75.20	FB		LSEOBUNDLEFB-d								FB	
Add	75.22			UniqueCoverage		FB		Yes		W	E	Optional	{LD: FB} {NDN: FB} have gaps in the date range or they overlap.
75.24			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	"must have a {LD: FB} for every country in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
Note: identify the country that does not have a FB"
75.26			MIN(PUBFROM)	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
75.28			MAX(PUBTO)	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBTO as late as or later than the  LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
			
     */
    private void doSpecBidChecks(EntityItem rootEntity, String statusFlag) throws Exception
    {
    	int checklvl = getCheck_W_W_E(statusFlag);

    	ArrayList bdlCtry = new ArrayList();
    	getCountriesAsList(rootEntity, bdlCtry, checklvl);
    	addDebug("doSpecBidChecks "+rootEntity.getKey()+" bdlCtry:"+bdlCtry);
    			
    	// look at each LSEO individually!!
      	EntityGroup lseoGrp = m_elist.getEntityGroup("LSEO");
    	for (int i=0; i<lseoGrp.getEntityItemCount(); i++){
    		EntityItem lseo = lseoGrp.getEntityItem(i);
    		Vector wwseoVct = PokUtils.getAllLinkedEntities(lseo, "WWSEOLSEO", "WWSEO");
    		if (wwseoVct.size()==0){// this is invalid data condition and should not happen
    			addDebug("doNotSpecBidLSEOChecks "+lseo.getKey()+" does not have a WWSEO parent");
    	    	//MISSING_PARENT_ERR2 = {0} does not have a {1} parent. 
    	    	args[0] = getLD_NDN(lseo);
    	    	args[1] = m_elist.getEntityGroup("WWSEO").getLongDescription();
    	    	createMessage(CHECKLEVEL_RE,"MISSING_PARENT_ERR2",args);
    			continue;
    		}
    		EntityItem wwseo = (EntityItem)wwseoVct.elementAt(0); // assume one
    		String specbid = getAttributeFlagEnabledValue(wwseo, "SPECBID");
    		addDebug("doSpecBidChecks "+lseo.getKey()+" "+wwseo.getKey()+" SPECBID: "+specbid);
	
    		//62	WWSEO		LSEOBUNDLELSEO-d: WWSEO-u									
    		//63	WHEN		SPECBID	=	"No" (11457)						LSEO GA	
    		if (SPECBID_NO.equals(specbid)){  // is No
    			addHeading(3,wwseo.getEntityGroup().getLongDescription()+" "+wwseo+" "+
    					lseoGrp.getLongDescription()+" GA Checks:");
    			Vector lseoAvailVct = PokUtils.getAllLinkedEntities(lseo, "LSEOAVAIL", "AVAIL");
    			Vector lseoPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(lseoAvailVct, "AVAILTYPE", PLANNEDAVAIL);
    			Vector lseoLOAvailVct = PokUtils.getEntitiesWithMatchedAttr(lseoAvailVct, "AVAILTYPE", LASTORDERAVAIL);
    	    	  	    	
    	    	ArrayList allLseoPlaAvailCtry = getCountriesAsList(lseoPlaAvailVct, getCheck_W_RE_RE(statusFlag));
    	    	ArrayList allLseoLOAvailCtry = getCountriesAsList(lseoLOAvailVct, getCheck_W_RE_RE(statusFlag));
    	    	
    	    	addDebug("doSpecBidChecks (65,66,67) "+lseo.getKey()+" "+wwseo.getKey()+" NO=SPECBID: "+specbid+
    	    			" lseoAvailVct:"+lseoAvailVct.size()+" lseoPlaAvailVct:"+lseoPlaAvailVct.size()+
    	    			" allLseoPlaAvailCtry "+allLseoPlaAvailCtry);
    	    	
    			addHeading(4,wwseo.getEntityGroup().getLongDescription()+" "+wwseo+" "+
    					lseoGrp.getLongDescription()+" Planned Avail Checks:");
    			
    	    	if (lseoPlaAvailVct.size()>0){
    	    		//64	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
    	    		//65	WHEN		AVAILTYPE	=	"Planned Availability"							
    	    		for (int ii=0; ii<lseoPlaAvailVct.size(); ii++){
    	    			EntityItem lseoplaAvail = (EntityItem)lseoPlaAvailVct.elementAt(ii);
    	    			// this is by country now
    	         		if(hasAnyCountryMatch(lseoplaAvail,bdlCtry)){
    	         			//66			EFFECTIVEDATE	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes	W	E	E		
    	         			//{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: LSEOBUNDLE} {BUNDLPUBDATEMTRGT}	
    	         			checkCanNotBeLater(lseo, lseoplaAvail, "EFFECTIVEDATE", rootEntity, "BUNDLPUBDATEMTRGT", getCheck_W_E_E(statusFlag));
    	         		}else{
    	           	    	ArrayList availCtrylist = new ArrayList();
    	         	    	// fill in list with the avail countries
    	         	    	getCountriesAsList(lseoplaAvail, availCtrylist,CHECKLEVEL_NOOP);
    	        			addDebug("doSpecBidChecks (66) skipping date check "+lseoplaAvail.getKey()+" availCtrylist: "+availCtrylist+
    	        					" did not contain any bdlCtry: "+bdlCtry);
    	        			availCtrylist.clear();
    	         		}
    	    		} 	
    	    		// set of all plannedavail for an lseo must contain all bdlctry
    	    		//67			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST		W	RE	RE		
    	    		if (!allLseoPlaAvailCtry.containsAll(bdlCtry)){
    	     	    	addDebug("doSpecBidChecks (67) allLseoPlaAvailCtry:"+
    	     	    			allLseoPlaAvailCtry+" does not contain all bdlCtry: "+bdlCtry);
    	    			//{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
    	    			//MUST_INCLUDE_CTRY_ERR = {0} {1} must include the Countries in {2} {3}
    	    			//args[0]=getLD_NDN(lseo);
    	    			//args[1]=getLD_NDN((EntityItem)lseoPlaAvailVct.firstElement()); do all
    	    			//args[2]=m_elist.getParentEntityGroup().getLongDescription();
    	    			//args[3]=PokUtils.getAttributeDescription(lseoGrp, "COUNTRYLIST", "COUNTRYLIST");
    	    			//createMessage(getCheck_W_RE_RE(statusFlag),"MUST_INCLUDE_CTRY_ERR",args);
    	    			
    	     	   		for (int i2=0; i2<lseoPlaAvailVct.size(); i2++){
    	        			EntityItem lseoplaAvail = (EntityItem)lseoPlaAvailVct.elementAt(i2); 
    	      
    	        	    	ArrayList availCtrylist = new ArrayList();
    	         	    	// fill in list with the avail countries
    	         	    	getCountriesAsList(lseoplaAvail, availCtrylist,CHECKLEVEL_NOOP);
    	         	    	if (!availCtrylist.containsAll(bdlCtry)){
    	         	    		addDebug("doSpecBidChecks (67) "+lseoplaAvail.getKey()+" availCtrylist: "+availCtrylist+
    	         	    				" did not contain all bdlCtry: "+bdlCtry);
    	         				args[0]=getLD_NDN(lseo);
    	    	    			args[1]=getLD_NDN(lseoplaAvail); 
    	    	    			args[2]=m_elist.getParentEntityGroup().getLongDescription();
    	    	    			args[3]=PokUtils.getAttributeDescription(lseoGrp, "COUNTRYLIST", "COUNTRYLIST");
    	    	    			createMessage(getCheck_W_RE_RE(statusFlag),"MUST_INCLUDE_CTRY_ERR",args);
    	         	    	}
    	        			availCtrylist.clear();
    	    	   		}
    	    		}  	
    	    	}
     
        		//68	END	65	
        		
    			addHeading(4,wwseo.getEntityGroup().getLongDescription()+" "+wwseo+" "+
    					lseoGrp.getLongDescription()+" Last Order Avail Checks:");
    			
    			addDebug("doSpecBidChecks (70,71,72) "+lseo.getKey()+" "+wwseo.getKey()+" SPECBID: "+specbid+
    	    			" lseoAvailVct:"+lseoAvailVct.size()+" lseoLOAvailVct:"+lseoLOAvailVct.size()+
    	    			" allLseoLOAvailCtry "+allLseoLOAvailCtry);
    			
        		if(lseoLOAvailVct.size()>0){
        			//69	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
        			//70	WHEN		AVAILTYPE	=	"Last Order"
        			for (int ii=0; ii<lseoLOAvailVct.size(); ii++){
        				EntityItem lseoLoAvail = (EntityItem)lseoLOAvailVct.elementAt(ii);
            			// this is by country now
    	         		if(hasAnyCountryMatch(lseoLoAvail,bdlCtry)){
    	         			//71			EFFECTIVEDATE	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes	W	W	E		
    	         			//{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
    	         			checkCanNotBeEarlier(lseo, lseoLoAvail, "EFFECTIVEDATE", rootEntity, "BUNDLUNPUBDATEMTRGT", getCheck_W_W_E(statusFlag));
    	         		}else{
    	           	    	ArrayList availCtrylist = new ArrayList();
    	         	    	// fill in list with the avail countries
    	         	    	getCountriesAsList(lseoLoAvail, availCtrylist,CHECKLEVEL_NOOP);
    	        			addDebug("doSpecBidChecks (71) skipping date check "+lseoLoAvail.getKey()+" availCtrylist: "+availCtrylist+
    	        					" did not contain any bdlCtry: "+bdlCtry);
    	        			availCtrylist.clear();
    	         		}
        			}
        			// this is all AVAIL.COUNTRYLIST
        			//72			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST		W	RE	RE		
        			if (!allLseoLOAvailCtry.containsAll(bdlCtry)){
        				addDebug("doSpecBidChecks (72) allLseoLOAvailCtry:"+
        						allLseoLOAvailCtry+" does not contain all bdlCtry: "+bdlCtry);
        				//{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
        				//MUST_INCLUDE_CTRY_ERR = {0} {1} must include the Countries in {2} {3}
        			//	args[0]=getLD_NDN(lseo);
        			//	args[1]=getLD_NDN((EntityItem)lseoLOAvailVct.firstElement()); do all
        			//	args[2]=m_elist.getParentEntityGroup().getLongDescription();
        			//	args[3]=PokUtils.getAttributeDescription(lseoGrp, "COUNTRYLIST", "COUNTRYLIST");
        			//	createMessage(getCheck_W_RE_RE(statusFlag),"MUST_INCLUDE_CTRY_ERR",args);
        				
             	   		for (int i2=0; i2<lseoLOAvailVct.size(); i2++){
    	        			EntityItem lseoLoAvail = (EntityItem)lseoLOAvailVct.elementAt(i2); 
    	      
    	        	    	ArrayList availCtrylist = new ArrayList();
    	         	    	// fill in list with the avail countries
    	         	    	getCountriesAsList(lseoLoAvail, availCtrylist,CHECKLEVEL_NOOP);
    	         	    	if (!availCtrylist.containsAll(bdlCtry)){
    	         	    		addDebug("doSpecBidChecks (72) "+lseoLoAvail.getKey()+" availCtrylist: "+availCtrylist+
    	         	    				" did not contain all bdlCtry: "+bdlCtry);
    	        				args[0]=getLD_NDN(lseo);
    	        				args[1]=getLD_NDN(lseoLoAvail);
    	        				args[2]=m_elist.getParentEntityGroup().getLongDescription();
    	        				args[3]=PokUtils.getAttributeDescription(lseoGrp, "COUNTRYLIST", "COUNTRYLIST");
    	        				createMessage(getCheck_W_RE_RE(statusFlag),"MUST_INCLUDE_CTRY_ERR",args);
    	         	    	}
    	        			availCtrylist.clear();
    	    	   		}
        			}  
        		}
        		//73	END	70										
        		//74	END	63
        		
        		lseoAvailVct.clear();
    			lseoPlaAvailVct.clear();
    			lseoLOAvailVct.clear();
    			allLseoPlaAvailCtry.clear();
    			allLseoLOAvailCtry.clear();
    		}else{
    			addDebug("doSpecBidChecks (58)SPECBID=Yes "+lseo.getKey());
    			addHeading(3,wwseo.getEntityGroup().getLongDescription()+" "+
    					wwseo+" "+lseoGrp.getLongDescription()+" Special Bid Checks:");
    			//56	WHEN		SPECBID	=	"Yes" (11458)						LSEO Special Bid	
    			//57	LSEO		LSEOBUNDLELSEO-d									
    			//58			LSEOPUBDATEMTRGT	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		
    			//{LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {NDN: LSEOPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {NDN: A: BUNDLPUBDATEMTRGT}
    			checkCanNotBeLater(lseo, "LSEOPUBDATEMTRGT", rootEntity, "BUNDLPUBDATEMTRGT", checklvl);

    			//59			COUNTRYLIST	Contains	LSEOBUNDLE	COUNTRYLIST		W	W	E	
    			// this was done in key 45 - no avails at this level
    			//{LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}

				addDebug("doSpecBidChecks (60) ");
    			//60			LSEOUNPUBDATEMTRGT	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT		W	W	E		
    			//{LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {NDN: LSEOUNPUBDATEMTRGT} must not be earlier than the {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {NDN: A:BUNDLUNPUBDATEMTRGT}
    			checkCanNotBeEarlier(lseo, "LSEOUNPUBDATEMTRGT", rootEntity, "BUNDLUNPUBDATEMTRGT", checklvl);
    			//61	END	56	
    		}        	
        	
        	// release memory
    		wwseoVct.clear();
    	}

    	addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Unique Coverage Checks:");
    	
    	checklvl = doCheck_N_W_E(statusFlag);//no more checks if Draft
    	if (CHECKLEVEL_NOOP!=checklvl){
    		//Add	75.00			LSEOBUNDLEIMG-d								IMG	
    		//Add	75.02			UniqueCoverage		IMG		Yes		W	E	Optional	{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
    		//75.04			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	"must have a {LD: IMG} for every country in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
    		//Note: identify the country that does not have an Image"
    		//Add	75.06			MIN(PUBFROM)	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT {BUNDLPUBDATEMTRGT
    		//Add	75.08			MAX(PUBTO)	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
    		checkUniqueCoverage(rootEntity, "LSEOBUNDLEIMG",
    				"IMG", rootEntity,"BUNDLPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT", checklvl, false);

    		//Add	75.10	MM		LSEOBUNDLEMM-d								MM	
    		//Add	75.12			UniqueCoverage		MM		Yes		W	E	Optional	{LD: MM} {NDN: MM} have gaps in the date range or they overlap.
    		//75.14			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: MM} for every country in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
    		//Note: identify the country that does not have a MM"
    		//Add	75.16			MIN(PUBFROM)	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT {BUNDLPUBDATEMTRGT
    		//Add	75.18			MAX(PUBTO)	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
    		checkUniqueCoverage(rootEntity, "LSEOBUNDLEMM",
    				"MM", rootEntity,"BUNDLPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT", checklvl, false);
    		//Add	75.20	FB		LSEOBUNDLEFB-d								FB	
    		//Add	75.22			UniqueCoverage		FB		Yes		W	E	Optional	{LD: FB} {NDN: FB} have gaps in the date range or they overlap.
    		//75.24			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	"must have a {LD: FB} for every country in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
    		//Note: identify the country that does not have a FB"
    		//75.26			MIN(PUBFROM)	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
    		//75.28			MAX(PUBTO)	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBTO as late as or later than the  LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
    		checkUniqueCoverage(rootEntity, "LSEOBUNDLEFB",
    				"FB", rootEntity,"BUNDLPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT", checklvl, false);
    	}

    	bdlCtry.clear();
    }

    /**************
     * 
     * @param rootEntity
     * @param statusFlag
     * @throws Exception
     * 

80	LSEOBUNDLE		Root									
7	WHEN		SPECBID	=	"No" (11457)						LSEOBUNDLE GA	
81			SAPASSORTMODULE	Validate				E	E	E		is not a Special Bid, but it does not have a value for LD(SAPASSORTMODULE)
8	SEOCG		SEOCGBDL-u								SEOCG	
9	IF		"Hardware" (100) or "Software" (101)	IN	LSEOBUNDLE	BUNDLETYPE						
10	THEN		Count of	=>	1			W	W	E	Hardware or Software	must be included in a {LD: SEOCG}
11	ELSE		Count of	=	0			E	E	E	Service	must not be included in a {LD: SEOCG}
12	AVAIL	A	LSEOBUNDLEAVAIL-d								Bundle AVAIL	
13	WHEN		AVAILTYPE	=	"Planned Availability"							
14			Count of	=>	1			RW	RW	RE		must have at least one "Planned Availability"
15			EFFECTIVEDATE	=>	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
16			COUNTRYLIST	in	LSEOBUNDLE	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL}  includes a Country that is not in {LD: COUNTRYLIST}
17	END	13										
18	AVAIL		LSEOBUNDLEAVAIL-d								Bundle AVAIL	
19	WHEN		AVAILTYPE	=	"First Order"							
20			EFFECTIVEDATE	=>	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
21			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL}  includes a Country that is not in {LD: COUNTRYLIST}
22	END	19										
23	AVAIL	B	LSEOBUNDLEAVAIL-d								Bundle AVAIL	
24	WHEN		AVAILTYPE	=	"Last Order"							
25			EFFECTIVEDATE	<=	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT		W	W	E		{LD: AVAIL} {NDN: AVAIL} can not be later than the {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
26			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
27			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL}  includes a Country that is not in {LD: AVAIL} {NDN: A: AVAIL}
28	END	24	
28.20	IMG		LSEOBUNDLEIMG-d								IMG	
28.24	IF		PDHDOMAIN	IN	ABR_PROPERTITIES	XCC_LIST2					XCC Unique Rules	
28.25	AND		"Hardware" (100)	IN	LSEOBUNDLE	BUNDLETYPE
Add	28.28			CountOf	=>	1		W	W	E				Required if XCC	must have at least one Image
Add	28.32	END										Optional for all others	
Add	28.36			UniqueCoverage		IMG		Yes		W	E		{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
Add	28.40			COUNTRYLIST	"Containsaggregate E"	A: AVAIL	COUNTRYLIST			W	E		must have a {LD: IMG} for every country in the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.44			MIN(PUBFROM)	<=	A: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.48			MAX(PUBTO)	=>	B: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: C: AVAIL}
Add	28.52	MM		LSEOBUNDLEMM-d								MM	
Add	28.56			UniqueCoverage		MM		Yes		W	E	Optional	{LD: MM} {NDN: MM} have gaps in the date range or they overlap.
Add	28.60			COUNTRYLIST	"Containsaggregate E"	A: AVAIL	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: MM} for every country in the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.64			MIN(PUBFROM)	<=	A: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: MM} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.68			MAX(PUBTO)	=>	B: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: MM} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: C: AVAIL}
Add	28.72	FB		LSEOBUNDLEFB-d								FB	
Add	28.76			UniqueCoverage		FB		Yes		W	E	Optional	{LD: FB} {NDN: FB} have gaps in the date range or they overlap.
Add	28.80			COUNTRYLIST	"Containsaggregate E"	A: AVAIL	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: FB} for every country in the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.84			MIN(PUBFROM)	<=	A: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: FB} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Add	28.88			MAX(PUBTO)	=>	B: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: FB} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: C: AVAIL}
									
29	WWSEO		LSEOBUNDLELSEO-d: WWSEO-u									
30	WHEN		SPECBID	=	"No" (11457)						LSEO GA	
31	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
32	WHEN		AVAILTYPE	=	"Planned Availability"							
33			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
34			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
35	END	32										
Change 20111213	36.00	AVAIL		LSEOBUNDLELSEO-d: LSEOAVAIL-d		LSEO AVAIL
37	WHEN		AVAILTYPE	=	"Last Order"							
38	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
39	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must have a corresponding {LD: LSEOBUNDLE} {LD: AVAIL} by Country
40			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
41	END	37										
46	END	30	
46.02	WWSEO		LSEOBUNDLELSEO-d: WWSEOLSEO-u	
	47.00	WHEN		SPECBID	=	"Yes" (11458)						LSEO Special Bid	
Add 20110823	47.20								E	E	E	A GA Bundle can not include a Special  Bid LSEO (WWSEO)	A GA Bundle can not include a Special  Bid LSEO {LD: LSEO} {NDN: LSEO}  
Delete 20110823	48.00			LSEOPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {NDN: LSEOPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Delete 20110823	49.00			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Delete 20110823	50.00			LSEOUNPUBDATEMTRGT	=>	B: AVAIL	EFFECTIVEDATE		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {NDN: LSEOUNPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
	51.00	END	47.00										
									
52	END	7		
     */
    private void doNotSpecBidChecks(EntityItem rootEntity, String statusFlag) throws Exception
    {
    	addDebug(rootEntity.getKey()+" is not a SPECBID  BUNDLETYPE["+
    			PokUtils.getAttributeFlagValue(rootEntity, "BUNDLETYPE")+"]");
    	
    	int checklvl = getCheck_W_W_E(statusFlag);
    	
    	//81			SAPASSORTMODULE	Validate				E	E	E		
    	//is not a Special Bid, but it does not have a value for LD(SAPASSORTMODULE)
		EANTextAttribute att = (EANTextAttribute)rootEntity.getAttribute("SAPASSORTMODULE");
		//true if information for the given NLSID is contained in the Text data
		if (att==null || (!((EANTextAttribute)att).containsNLS(1))) {
			args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "SAPASSORTMODULE", "SAPASSORTMODULE");
			createMessage(CHECKLEVEL_E,"NOT_SPECBID_VALUE_ERR",args);
		} // end attr has this language
		
    	int cnt = getCount("SEOCGBDL");
    	//8	SEOCG		SEOCGBDL-u								SEOCG	
    	//9	IF		"Hardware" (100) or "Software" (101)	IN	LSEOBUNDLE	BUNDLETYPE						
    	//10	THEN		Count of	=>	1			W	W	E		must be included in a {LD: SEOCG}
    	//11	ELSE		Count of	=	0			E	E	E		must not be included in a {LD: SEOCG}
		if (!PokUtils.contains(rootEntity, "BUNDLETYPE", TESTSET)){ // not HW or SW
			if(cnt !=0) {
				//MUST_NOT_BE_INCLUDED_ERR = must not be included in a {0}
				args[0] = m_elist.getEntityGroup("SEOCG").getLongDescription();
				createMessage(checklvl,"MUST_NOT_BE_INCLUDED_ERR",args);
			}
		}else{
			if(cnt ==0) {
				//MUST_BE_INCLUDED_ERR = must be included in a {0}
				args[0] = m_elist.getEntityGroup("SEOCG").getLongDescription();
				createMessage(checklvl,"MUST_BE_INCLUDED_ERR",args);
			}
		}
		
		// check LSEOBUNDLEAVAILs
		
		//12	AVAIL	A	LSEOBUNDLEAVAIL-d								Bundle AVAIL	
											
		//	get the bundle AVAILS
		Vector bdlAvailVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOBUNDLEAVAIL", "AVAIL");
    	Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(bdlAvailVct, "AVAILTYPE", LASTORDERAVAIL);
    	Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(bdlAvailVct, "AVAILTYPE", PLANNEDAVAIL);
    	Vector firstOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(bdlAvailVct, "AVAILTYPE", FIRSTORDERAVAIL);
    	addDebug("doNotSpecBidChecks bdlAvailVct: "+bdlAvailVct.size()+" plannedAvailVct: "+plannedAvailVct.size()+
    			" firstOrderAvailVct: "+firstOrderAvailVct.size()+" lastOrderAvailVct: "+lastOrderAvailVct.size());
    	
     	//13	WHEN		AVAILTYPE	=	"Planned Availability"							
		//14			Count of	=>	1			RW	RW	RE		must have at least one "Planned Availability"
		checkPlannedAvailsExist(plannedAvailVct, getCheck_RW_RW_RE(statusFlag));

		ArrayList bdlCtry = new ArrayList();
		getCountriesAsList(rootEntity, bdlCtry, checklvl);
		addDebug("doNotSpecBidChecks (16) bdlCtry: "+bdlCtry);
			
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Planned Avail Checks:");
		for (int i=0; i<plannedAvailVct.size(); i++){
			EntityItem avail = (EntityItem)plannedAvailVct.elementAt(i);
 			
			//15		EFFECTIVEDATE	=>	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		
			//{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}	
			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "BUNDLPUBDATEMTRGT", checklvl);
			
    		//16 COUNTRYLIST	in	LSEOBUNDLE	COUNTRYLIST
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a country that is not in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
			checkAvailCtryInEntity(null,avail, rootEntity,bdlCtry,checklvl); 
		}
		bdlCtry.clear();
//		20121030 Add	16.20	WHEN		"Final" (FINAL)	=	LSEOBUNDLE	DATAQUALITY																																																																																																																																																																																																																																															
//		20121030 Add	16.22	IF		STATUS	=	"Ready for Review" (0040)																																																																																																																																																																																																																																																
//		20121030 Add	16.24	OR		STATUS	=	"Final" (0020)																																																																																																																																																																																																																																																
//		20121030 Add	16.26			CountOf	=>	1					RE		must have at least one "Planned Availability" that is either "Ready for Review" or "Final" in order to be "Final"																																																																																																																																																																																																																																									
//		20121030 Add	16.28	END	16.20																																																																																																																																																																																																																																																			
		checkPlannedAvailsStatus(plannedAvailVct, rootEntity,CHECKLEVEL_RE);
		//17	END	13 
		
		Hashtable plaAvailCtryTbl = getAvailByCountry(plannedAvailVct, checklvl);
		addDebug("doNotSpecBidChecks bdl plaAvailCtry "+plaAvailCtryTbl);
		
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" First Order Avail Checks:");
		//18	AVAIL		LSEOBUNDLEAVAIL-d								Bundle AVAIL	
		//19	WHEN		AVAILTYPE	=	"First Order"									
		for (int i=0; i<firstOrderAvailVct.size(); i++){
			EntityItem avail = (EntityItem)firstOrderAvailVct.elementAt(i);
 			
			//20			EFFECTIVEDATE	=>	LSEOBUNDLE	BUNDLPUBDATEMTRGT		W	W	E		
			//{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}		
			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "BUNDLPUBDATEMTRGT", checklvl);

			//21			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
			checkPlannedAvailForCtryExists(avail, plaAvailCtryTbl.keySet(), checklvl);
		}
		//22	END	19	
		
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Last Order Avail Checks:");
		//23	AVAIL	B	LSEOBUNDLEAVAIL-d								Bundle AVAIL	
		//24	WHEN		AVAILTYPE	=	"Last Order"							
		for (int i=0; i<lastOrderAvailVct.size(); i++){
			EntityItem avail = (EntityItem)lastOrderAvailVct.elementAt(i);
			//25			EFFECTIVEDATE	<=	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT		W	W	E		
			//{LD: AVAIL} {NDN: AVAIL} can not be later than the {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}		
			checkCanNotBeLater(avail, "EFFECTIVEDATE", rootEntity, "BUNDLUNPUBDATEMTRGT", checklvl);
			
			Vector plaVct = new Vector(); // hold onto plannedavail for date checks incase same avail for mult ctrys
			checkCtryMismatch(avail, plaAvailCtryTbl, plaVct, checklvl);
			
			// do the date checks now
			for (int m=0; m<plaVct.size(); m++){
				EntityItem plaAvail = (EntityItem)plaVct.elementAt(m);
				//26			EFFECTIVEDATE	=>	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		
				//{LD: AVAIL} {NDN: AVAIL}  can not be earlier than {LD: AVAIL} {NDN: A: AVAIL}
				checkCanNotBeEarlier(avail, "EFFECTIVEDATE", plaAvail, "EFFECTIVEDATE", checklvl);
			}
			plaVct.clear();
			//	27			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E	
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
			checkPlannedAvailForCtryExists(avail, plaAvailCtryTbl.keySet(), checklvl);
		}
		//28	END	24	
		
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Unique Coverage Checks:");
		//28.20	IMG		LSEOBUNDLEIMG-d								IMG	
		//Change 20111223	28.24	IF		PDHDOMAIN	IN	ABR_PROPERTITIES	XCC_LIST2					XCC Unique Rules	
		if (domainInRuleList(rootEntity, "XCC_LIST2")){ 
			addDebug(rootEntity.getKey()+" and domain in XCCLIST2");
			//28.25	AND		"Hardware" (100)	IN	LSEOBUNDLE	BUNDLETYPE
			if (PokUtils.contains(rootEntity, "BUNDLETYPE", HWTESTSET)){ //MN IN495108
				//28.28			CountOf	=>	1			W	W	E			Required if XCC		must have at least one Image
				cnt = getCount("LSEOBUNDLEIMG");
				if(cnt==0){
					//MINIMUM_ERR =  must have at least one {0}
					args[0] = m_elist.getEntityGroup("IMG").getLongDescription();
					createMessage(checklvl,"MINIMUM_ERR",args);
				}
				//28.32	END										Optional for all others	
			}else{
				addDebug(rootEntity.getKey()+" BUNDLETYPE did not contain Hardware- IMG not checked");
			}
		}

		checklvl = doCheck_N_W_E(statusFlag);//no more checks if Draft
		if (CHECKLEVEL_NOOP!=checklvl){
			//28.36			UniqueCoverage		IMG		Yes		W	E		{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
			//28.40			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST			W	E		must have a {LD: IMG} for every country in the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
			//28.44			MIN(PUBFROM)	<=	A: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
			//28.48			MAX(PUBTO)	=>	B: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: C: AVAIL}
			checkUniqueCoverage(rootEntity, "LSEOBUNDLEIMG","IMG", plannedAvailVct, lastOrderAvailVct, checklvl, false);
			//28.52	MM		LSEOBUNDLEMM-d								MM	
			//28.56			UniqueCoverage		MM		Yes		W	E	Optional	{LD: MM} {NDN: MM} have gaps in the date range or they overlap.
			//28.60			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: MM} for every country in the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
			//28.64			MIN(PUBFROM)	<=	A: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: MM} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
			//28.68			MAX(PUBTO)	=>	B: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: MM} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: C: AVAIL}
			checkUniqueCoverage(rootEntity, "LSEOBUNDLEMM","MM", plannedAvailVct, lastOrderAvailVct, checklvl, false);

			//28.72	FB		LSEOBUNDLEFB-d								FB	
			//28.76			UniqueCoverage		FB		Yes		W	E	Optional	{LD: FB} {NDN: FB} have gaps in the date range or they overlap.
			//28.80			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: FB} for every country in the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
			//28.84			MIN(PUBFROM)	<=	A: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: FB} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
			//28.88			MAX(PUBTO)	=>	B: AVAIL	EFFECTIVEDATE	Yes		W	E		must have a {LD: FB} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: C: AVAIL}
			checkUniqueCoverage(rootEntity, "LSEOBUNDLEFB","FB", plannedAvailVct, lastOrderAvailVct, checklvl, false);
		}
		
		// 29 to 52
		doNotSpecBidLSEOChecks(rootEntity, plannedAvailVct,lastOrderAvailVct, statusFlag);

		bdlAvailVct.clear();
    	lastOrderAvailVct.clear();
    	plannedAvailVct.clear();
    	firstOrderAvailVct.clear();
    }
    /****************
     * Check WWSEO for an LSEOBUNDLE.SPECBID=No
     * @param rootEntity
     * @param bdlPlaAvailVctA
     * @param bdlLOAvailVctB
     * @param statusFlag
     * @throws Exception
29	WWSEO		LSEOBUNDLELSEO-d: WWSEO-u									
30	WHEN		SPECBID	=	"No" (11457)						LSEO GA	
31	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
32	WHEN		AVAILTYPE	=	"Planned Availability"							
33			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
34			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
35	END	32										
Change 20111213	36.00	AVAIL		LSEOBUNDLELSEO-d: LSEOAVAIL-d			LSEO AVAIL
37	WHEN		AVAILTYPE	=	"Last Order"							
38	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
39	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must have a corresponding {LD: LSEOBUNDLE} {LD: AVAIL} by Country
40			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
41	END	37										
46	END	30										
46.02	WWSEO		LSEOBUNDLELSEO-d: WWSEOLSEO-u	
	47.00	WHEN		SPECBID	=	"Yes" (11458)						LSEO Special Bid	
Add 20110823	47.20								E	E	E	A GA Bundle can not include a Special  Bid LSEO (WWSEO)	A GA Bundle can not include a Special  Bid LSEO {LD: LSEO} {NDN: LSEO}  
Delete 20110823	48.00			LSEOPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {NDN: LSEOPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Delete 20110823	49.00			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
Delete 20110823	50.00			LSEOUNPUBDATEMTRGT	=>	B: AVAIL	EFFECTIVEDATE		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {NDN: LSEOUNPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
	51.00	END	47.00										

     */
    private void doNotSpecBidLSEOChecks(EntityItem rootEntity, Vector bdlPlaAvailVctA, 
    		Vector bdlLOAvailVctB, String statusFlag) throws Exception
    {
    	//int checklvl = getCheck_W_W_E(statusFlag);
    	
    	// look at each LSEO individually!!
      	EntityGroup lseoGrp = m_elist.getEntityGroup("LSEO");
    	for (int i=0; i<lseoGrp.getEntityItemCount(); i++){
    		EntityItem lseo = lseoGrp.getEntityItem(i);
    		Vector wwseoVct = PokUtils.getAllLinkedEntities(lseo, "WWSEOLSEO", "WWSEO");
    		if(wwseoVct.size()==0){
    			addDebug("doNotSpecBidLSEOChecks "+lseo.getKey()+" does not have a WWSEO parent");
    			// invalid data - an LSEO must have a WWSEO
    			// this is invalid data condition and should not happen
    	    	args[0] = getLD_NDN(lseo);
    	    	args[1] = m_elist.getEntityGroup("WWSEO").getLongDescription();
    	    	createMessage(CHECKLEVEL_RE,"MISSING_PARENT_ERR2",args);
    			continue;
    		}
    		EntityItem wwseo = (EntityItem)wwseoVct.elementAt(0); // assume one
    		String specbid = getAttributeFlagEnabledValue(wwseo, "SPECBID");
    		addDebug("doNotSpecBidLSEOChecks "+lseo.getKey()+" "+wwseo.getKey()+" SPECBID: "+specbid);
	
    		//29	WWSEO		LSEOBUNDLE-d: WWSEO-u									
    		//30	WHEN		SPECBID	=	"No" (11457)						LSEO GA	
    		if (SPECBID_NO.equals(specbid)){  // is No
    			addHeading(3,wwseo.getEntityGroup().getLongDescription()+" "+
    					wwseo+" "+lseoGrp.getLongDescription()+" GA Checks:");
      			addHeading(4,lseo.getEntityGroup().getLongDescription()+" "+lseo+" Planned Avail Checks:");
    			//31	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
    			//32	WHEN		AVAILTYPE	=	"Planned Availability"							
    			//33			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
    			//34			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
    			checkLseoPlannedAvail(bdlPlaAvailVctA, lseo, statusFlag); 
    			//35	END	32										
    			
    			addHeading(4,lseo.getEntityGroup().getLongDescription()+" "+lseo+" Last Order Avail Checks:");
    			//36.00	AVAIL		LSEOBUNDLELSEO-d: LSEOAVAIL-d	LSEO AVAIL
    			//37	WHEN		AVAILTYPE	=	"Last Order"							
    			//38	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST						
    			//39	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must have a corresponding {LD: LSEOBUNDLE} {LD: AVAIL} by Country
    			//40			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}		
    			matchLseoLastOrderAvail(bdlLOAvailVctB,	bdlPlaAvailVctA, lseo);
    			//41	END	37
    			//46	END	30
    		}else{
    			//47.00	WHEN		SPECBID	=	"Yes" (11458)						LSEO Special Bid	
    			addHeading(3,wwseo.getEntityGroup().getLongDescription()+" "+
    					wwseo+" "+lseoGrp.getLongDescription()+" Special Bid Checks:");
    			
    			//Add 20110823	47.20	E E E	A GA Bundle can not include a Special  Bid LSEO (WWSEO)	A GA Bundle can not include a Special  Bid LSEO {LD: LSEO} {NDN: LSEO} 
    			//GABDL_SPECBID_ERR = A GA Bundle can not include a Special  Bid LSEO {0} 
    			args[0]=getLD_NDN(lseo);
				createMessage(CHECKLEVEL_E,"GABDL_SPECBID_ERR",args);
    			
    			
    			//Delete 20110823	48.00			LSEOPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {NDN: LSEOPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
    			//Delete 20110823	49.00			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
    			//Delete 20110823	50.00			LSEOUNPUBDATEMTRGT	=>	B: AVAIL	EFFECTIVEDATE		W	W	E		{LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {NDN: LSEOUNPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
    												

    			// 47	WHEN		SPECBID	=	"Yes" (11458)						LSEO Special Bid	
    			//ArrayList lseoCtry = new ArrayList();
    			//getCountriesAsList(lseo, lseoCtry, checklvl);
    			
    			/*String pubdate = getAttrValueAndCheckLvl(lseo, "LSEOPUBDATEMTRGT", checklvl);
    			String unpubdate = getAttrValueAndCheckLvl(lseo, "LSEOUNPUBDATEMTRGT", checklvl);	
    			addDebug("doNotSpecBidLSEOChecks (48,49,50) "+lseo.getKey()
    					+" lseoCtry: "+lseoCtry+" pubdate "+pubdate+" unpubdate "+unpubdate);

    			addHeading(4,lseo.getEntityGroup().getLongDescription()+" "+lseo+"  Planned Avail Checks:");
      			
    			for (int p=0; p<bdlPlaAvailVctA.size(); p++){
    				EntityItem bdlPlaAvail = (EntityItem)bdlPlaAvailVctA.elementAt(p);
    				//48			LSEOPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		
    				//{LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {NDN: LSEOPUBDATEMTRGT} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
    				checkDates(lseo, "LSEOPUBDATEMTRGT", bdlPlaAvail, checklvl, DATE_LT_EQ);

    				ArrayList availCtry = new ArrayList();
    				getCountriesAsList(bdlPlaAvail, availCtry, checklvl);
    				//49	LSEO.COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E	
    				//{LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
    				if(!lseoCtry.containsAll(availCtry)){
    					addDebug("doNotSpecBidLSEOChecks lseoCtry: "+lseoCtry+" does not contain all bdlPlaAvail "+
    							bdlPlaAvail.getKey()+" availCtry: "+availCtry);
    					//MUST_INCLUDE_CTRY_ERR = {0} {1} must include the Countries in {2} {3				
    					//{LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
    					args[0]=getLD_NDN(lseo);
    					args[1]=PokUtils.getAttributeDescription(lseo.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
    					args[2]=rootEntity.getEntityGroup().getLongDescription();
    					args[3]=getLD_NDN(bdlPlaAvail);
    					createMessage(checklvl,"MUST_INCLUDE_CTRY_ERR",args);
    				}			
    			}
    			
    			addHeading(4,lseo.getEntityGroup().getLongDescription()+" "+lseo+" Last Order Avail Checks:");

    			for (int p=0; p<bdlLOAvailVctB.size(); p++){
    				EntityItem bdlLOAvail = (EntityItem)bdlLOAvailVctB.elementAt(p);
    				//50		LSEOUNPUBDATEMTRGT	=>	B: AVAIL	EFFECTIVEDATE		W	W	E		
    				//LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {NDN: LSEOUNPUBDATEMTRGT} must not be earlier than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
    				checkDates(lseo, "LSEOUNPUBDATEMTRGT", bdlLOAvail, checklvl, DATE_GR_EQ);
    			}
    			//51.00	END	47.00	
    			
    			lseoCtry.clear();
    			*/
    		}        	
        	
        	// release memory
        	wwseoVct.clear();
    	}
    }
    /***************
     * All lseo plannedavail effdate cannot be later than the lseobdl plannedavail effdate by ctry
     * All lseobdl plannedavail ctrys  must be a subset of all lseo plannedavail ctrys
     * @param bdlPlaAvailVctA
     * @param lseo
     * @param statusFlag
     * @throws MiddlewareException
     * @throws SQLException
     * 
31	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
32	WHEN		AVAILTYPE	=	"Planned Availability"							
33			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E		
{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
34			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		
{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
35	END	32	
     */
    private void checkLseoPlannedAvail(Vector bdlPlaAvailVctA, EntityItem lseo, String statusFlag) 
    throws MiddlewareException, SQLException
    {
    	Vector lseoAvailVct = PokUtils.getAllLinkedEntities(lseo, "LSEOAVAIL", "AVAIL");
    	Vector lseoPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(lseoAvailVct, "AVAILTYPE", PLANNEDAVAIL);
    	ArrayList allLseoPlaAvailCtry = getCountriesAsList(lseoPlaAvailVct, getCheck_W_RE_RE(statusFlag));
    	
    	addDebug("checkLseoPlannedAvail "+lseo.getKey()+" lseoAvailVct: "+
    			lseoAvailVct.size()+" lseoPlaAvailVct: "+lseoPlaAvailVct.size()+
    			" allLseoPlaAvailCtry "+allLseoPlaAvailCtry);
    	
       	// get the set of countries for lseobdl planned avails
    	Hashtable lseoBdlPlaAvailCtryTblA =  getAvailByCountry(bdlPlaAvailVctA, getCheck_W_RE_RE(statusFlag));
    	addDebug("checkLseoPlannedAvail lseoBdlPlaAvailCtryTblA: "+lseoBdlPlaAvailCtryTblA.keySet());
  
       	if (lseoPlaAvailVct.size()>0){
       		//31	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
       		//32	WHEN		AVAILTYPE	=	"Planned Availability"	
       		for (int i=0; i<lseoPlaAvailVct.size(); i++){
       			EntityItem lseoPlaAvail = (EntityItem)lseoPlaAvailVct.elementAt(i); 
       			Vector bdlPlaVct = new Vector(); // hold onto lseobdl plaavail for date checks incase same avail for mult ctrys
       			checkCtryMismatch(lseoPlaAvail, lseoBdlPlaAvailCtryTblA, bdlPlaVct, getCheck_W_RE_RE(statusFlag));
       			// do the date checks now by ctry
       			for (int m=0; m<bdlPlaVct.size(); m++){
       				EntityItem bdlPlaAvail = (EntityItem)bdlPlaVct.elementAt(m);
       				//33			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E	
       				//{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
       				checkDates(lseo, lseoPlaAvail, bdlPlaAvail, getCheck_W_E_E(statusFlag), DATE_LT_EQ);
       			}
       			bdlPlaVct.clear();
       		}
    		//31	AVAIL		LSEOBUNDLELSEO-d:LSEOAVAIL-d								LSEO AVAIL	
    		//32	WHEN		AVAILTYPE	=	"Planned Availability"	
    		//34			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	RE	RE		
    		//{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must include the Countries in {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: A: AVAIL}
    		for (int i=0; i<bdlPlaAvailVctA.size(); i++){
    			// each bdl plannedavail ctry must be a subset of all lseo plannedavail ctrys
    			EntityItem bdlPlaAvail = (EntityItem)bdlPlaAvailVctA.elementAt(i); 
    			ArrayList bdlCtry = new ArrayList();
    			getCountriesAsList(bdlPlaAvail, bdlCtry, getCheck_W_RE_RE(statusFlag));
    			if (!allLseoPlaAvailCtry.containsAll(bdlCtry)){
    				addDebug("checkLseoPlannedAvail (34) lseoPlaAvail allLseoPlaAvailCtry: "+
    						allLseoPlaAvailCtry+" does not contain all bdlPlaAvail "+bdlPlaAvail.getKey()+" bdlCtry "+bdlCtry);

    				// extra ctry on bdl avail
    				//MUST_INCLUDE_CTRY_ERR = {0} {1} must include the Countries in {2} {3}
    			//	args[0]=getLD_NDN(lseo);
    			//	args[1]=getLD_NDN((EntityItem)lseoPlaAvailVct.firstElement()); output all
    			//	args[2]=m_elist.getParentEntityGroup().getLongDescription();
    			//	args[3]=getLD_NDN(bdlPlaAvail);
    			//	createMessage(getCheck_W_RE_RE(statusFlag),"MUST_INCLUDE_CTRY_ERR",args);
    			
    	   	   		for (int i2=0; i2<lseoPlaAvailVct.size(); i2++){
            			EntityItem lseoPlaAvail = (EntityItem)lseoPlaAvailVct.elementAt(i2); 
            	    	ArrayList availCtrylist = new ArrayList();
             	    	// fill in list with the avail countries
             	    	getCountriesAsList(lseoPlaAvail, availCtrylist,CHECKLEVEL_NOOP);
             	    	if (!availCtrylist.containsAll(bdlCtry)){
             	    		addDebug("checkLseoPlannedAvail (34) "+lseoPlaAvail.getKey()+" availCtrylist: "+availCtrylist+
             	    				" did not contain all lseoCtry: "+bdlCtry);
             	    		//MUST_INCLUDE_CTRY_ERR = {0} {1} must include the Countries in {2} {3}
            				args[0]=getLD_NDN(lseo);
            				args[1]=getLD_NDN(lseoPlaAvail);
            				args[2]=m_elist.getParentEntityGroup().getLongDescription();
            				args[3]=getLD_NDN(bdlPlaAvail);
            				createMessage(getCheck_W_RE_RE(statusFlag),"MUST_INCLUDE_CTRY_ERR",args);
             	    	}
            			availCtrylist.clear();
        	   		}
    			}

    			bdlCtry.clear();
    		}  
		//35	END	32		
       	}
       	
     	lseoAvailVct.clear();
    	lseoPlaAvailVct.clear();
    	allLseoPlaAvailCtry.clear();
    	lseoBdlPlaAvailCtryTblA.clear();
    }
    /*********
     * For each lseobdl->plannedAvail.ctry that matches the lseo->LastOrderAvail.ctry
     * there must be a lseobdl->LastOrderAvail.ctry	
     * @param bdlLOAvailVctB
     * @param bdlPlaAvailVctA
     * @param lseo
     * @throws MiddlewareException
     * @throws SQLException
     * 
36.00	AVAIL		LSEOBUNDLELSEO-d: LSEOAVAIL-d								LSEO AVAIL	
37	WHEN		AVAILTYPE	=	"Last Order"							
38	IF		COUNTRYLIST	Match	lseobdlPlannedAvail A: AVAIL	COUNTRYLIST						
39	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		RE	RE	RE		
{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must have a corresponding {LD: LSEOBUNDLE} {LD: AVAIL} by Country
40			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		
{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
41	END	37			
     */
    private void matchLseoLastOrderAvail(Vector bdlLOAvailVctB,
    		Vector bdlPlaAvailVctA, EntityItem lseo) throws MiddlewareException, SQLException
    {
    	int checklvl = CHECKLEVEL_RE;
    	Vector lseoAvailVct = PokUtils.getAllLinkedEntities(lseo, "LSEOAVAIL", "AVAIL");
    	Vector lseoLOAvailVct = PokUtils.getEntitiesWithMatchedAttr(lseoAvailVct, "AVAILTYPE", LASTORDERAVAIL);

    	addDebug("matchLseoLastOrderAvail lseoAvailVct: "+
    			lseoAvailVct.size()+" lseoLOAvailVct: "+lseoLOAvailVct.size());
    	
    	//	IF		LSEO-LastOrderAVAIL.COUNTRYLIST	Match	LSEOBDL-PlannedAVAIL	COUNTRYLIST						
    	//	THEN	LSEOAVAIL.COUNTRYLIST	TheMatch	LSEOBDL-LastOrderAVAIL	COUNTRYLIST		RE	RE	RE		
    	// get the set of countries for lseobdl lastorder avails
    	Hashtable lseoBdlLOAvailCtryTblB =  getAvailByCountry(bdlLOAvailVctB, checklvl);;
    	addDebug("matchLseoLastOrderAvail lseoBdlLOAvailCtryTblB: "+lseoBdlLOAvailCtryTblB);

    	// get the lseo lo avails by country
    	Hashtable lseoLOAvailCtryTbl =  getAvailByCountry(lseoLOAvailVct, checklvl);

    	// For each LSEOBDL->plannedAvail.ctry that matches the LSEO->LastOrderAvail.ctry
    	// there must be a LSEOBDL->LastOrderAvail.ctry
    	for (int i=0; i<bdlPlaAvailVctA.size(); i++){
    		EntityItem plaAvail = (EntityItem)bdlPlaAvailVctA.elementAt(i); 

    		EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(plaAvail, "COUNTRYLIST", checklvl);
    		if (ctrylist != null && ctrylist.toString().length()>0) {
    			// Get the selected Flag codes.
    			MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();

    			Vector lseoloVct = new Vector(); // hold onto lseo lastorderavail in case mult ctrys match
    			for (int im = 0; im < mfArray.length; im++) {						
    				if (mfArray[im].isSelected() &&
    						!lseoBdlLOAvailCtryTblB.containsKey(mfArray[im].getFlagCode())){
    					addDebug("matchLseoLastOrderAvail (38,39) lseobdl-plannedavail:"+plaAvail.getKey()+
    							" No lseobdl lastorderavail for ctry "+mfArray[im].getFlagCode());
    					// get the lseo-lastorderavail for this ctry
    					EntityItem lseoloAvail = (EntityItem)lseoLOAvailCtryTbl.get(mfArray[im].getFlagCode());
    					if (lseoloAvail!=null){
    						addDebug("matchLseoLastOrderAvail (38,39) lseobdl-plannedavail:"+plaAvail.getKey()+
    								" LSEO-lastorderavail "+lseoloAvail.getKey()+" for ctry "+
    								mfArray[im].getFlagCode());
    						if (!lseoloVct.contains(lseoloAvail)){
    							lseoloVct.add(lseoloAvail);
    						}
    					}
    				}
    			}

    			// output msg for all lseo lastorder that didnt have an lseobdl lastorder
    			for (int m=0; m<lseoloVct.size(); m++){
    				EntityItem lseoloAvail = (EntityItem)lseoloVct.elementAt(m);
    		    	//{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} must have a corresponding {LD: LSEOBUNDLE} {LD: AVAIL} by Country
    				//LOAVAIL_MATCH_ERR= {0} {1} is being withdrawn; therefore this {2} must have a corresponding {3} &quot;Last Order&quot; by Country
    				args[0]=getLD_NDN(lseo);
    		    	args[1]=getLD_NDN(lseoloAvail);
    				args[2]=m_elist.getParentEntityGroup().getLongDescription();
    				args[3]=lseoloAvail.getEntityGroup().getLongDescription();
    				createMessage(checklvl,"LOAVAIL_MATCH_ERR",args);
    			}
    			lseoloVct.clear();           				
    		}
    	}
    		
    	//40			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE		
    	//{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
    	for (int i=0; i<lseoLOAvailVct.size(); i++){
			EntityItem lseoloAvail = (EntityItem)lseoLOAvailVct.elementAt(i);
			Vector bdlLoVct = new Vector(); // hold onto lseobdl loavail for date checks incase same avail for mult ctrys
			checkCtryMismatch(lseoloAvail, lseoBdlLOAvailCtryTblB, bdlLoVct, checklvl);
			// do the date checks now by ctry
			for (int m=0; m<bdlLoVct.size(); m++){
				EntityItem bdlloAvail = (EntityItem)bdlLoVct.elementAt(m);
				//40			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	RE	RE	RE	
		    	//{LD: LSEO} {NDN: LSEO} {LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD: LSEOBUNDLE} {LD: AVAIL} {NDN: B: AVAIL}
				checkDates(lseo, lseoloAvail, bdlloAvail, checklvl, DATE_GR_EQ);
			}
			bdlLoVct.clear();
    	}

     	lseoAvailVct.clear();
    	lseoLOAvailVct.clear();
    	lseoBdlLOAvailCtryTblB.clear();
    	lseoLOAvailCtryTbl.clear();
    } 

    /********************************************
     * @param lseo
     * @param lseoAvail
     * @param bdlAvail
     * @param checklvl
     * @param dateRule
     * @throws SQLException
     * @throws MiddlewareException
     */
    private void checkDates(EntityItem lseo, EntityItem lseoAvail, EntityItem bdlAvail,
    		int checklvl, int dateRule) throws SQLException, MiddlewareException
    {
    	String date1 = getAttrValueAndCheckLvl(lseoAvail, "EFFECTIVEDATE", checklvl);
    	String date2 = getAttrValueAndCheckLvl(bdlAvail, "EFFECTIVEDATE", checklvl);
    	addDebug("checkDates lseo:"+
    			lseo.getKey()+" "+lseoAvail.getKey()+" EFFECTIVEDATE:"+date1+" can not be "+(dateRule==DATE_GR_EQ?"earlier":"later")+
    			" than bdlavail: "+bdlAvail.getKey()+" EFFECTIVEDATE:"+date2);
    	boolean isok = checkDates(date1, date2, dateRule);	//date1<=date2	
    	if (!isok){
    		//CANNOT_BE_LATER_ERR = {0} {1} must not be later than the {2} {3}
    		String errcode="CANNOT_BE_LATER_ERR";
    		if (dateRule==DATE_GR_EQ){
    			//CANNOT_BE_EARLIER_ERR2 = {0} {1} must not be earlier than the {2} {3} 
    			errcode="CANNOT_BE_EARLIER_ERR2";
    		}

    		args[0]=getLD_NDN(lseo);
    		args[1]=getLD_NDN(lseoAvail);
    		args[2]=m_elist.getParentEntityGroup().getLongDescription();
    		args[3]=getLD_NDN(bdlAvail);
    		createMessage(checklvl,errcode,args);
    	}
    }

    /******************
     * 
     * @param lseo
     * @param lseoAttrCode
     * @param bdlAvail
     * @param checklvl
     * @param dateRule
     * @throws SQLException
     * @throws MiddlewareException
     * /
    private void checkDates(EntityItem lseo, String lseoAttrCode, EntityItem bdlAvail,
    		int checklvl, int dateRule) throws SQLException, MiddlewareException
    {
    	String date1 = getAttrValueAndCheckLvl(lseo, lseoAttrCode,checklvl);
    	String date2 = getAttrValueAndCheckLvl(bdlAvail, "EFFECTIVEDATE", checklvl);
    	addDebug("checkDates lseo:"+
    			lseo.getKey()+" "+lseoAttrCode+":"+date1+" can not be "+(dateRule==DATE_GR_EQ?"earlier":"later")+
    			" than bdlavail: "+bdlAvail.getKey()+" EFFECTIVEDATE:"+date2);
    	boolean isok = checkDates(date1, date2, dateRule);	//date1<=date2	
    	if (!isok){
    		//CANNOT_BE_LATER_ERR = {0} {1} must not be later than the {2} {3}
    		String errcode="CANNOT_BE_LATER_ERR";
    		if (dateRule==DATE_GR_EQ){
    			//CANNOT_BE_EARLIER_ERR2 = {0} {1} must not be earlier than the {2} {3} 
    			errcode="CANNOT_BE_EARLIER_ERR2";
    		}

    		args[0]=getLD_NDN(lseo);
    		args[1]=getLD_Value(lseo, lseoAttrCode);
    		args[2]=m_elist.getParentEntityGroup().getLongDescription();
    		args[3]=getLD_NDN(bdlAvail);
    		createMessage(checklvl,errcode,args);
    	}
    }*/
    
    /**********************************
     * Must have MODELWWSEO in second VE because end up with SWPRODSTRUCTs and FEATUREs from this MODEL
     * when all you want is SWPRODSTRUCTs from WWSEOPRODSTRUCT
     * @param rootEntity
     * @throws Exception
     */
    private void getModelVE(EntityItem rootEntity) throws Exception
    {
        String VeName = "EXRPT3LSEOBDL2";

        mdlList = m_db.getEntityList(m_elist.getProfile(),
                new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
                new EntityItem[] { new EntityItem(null, m_elist.getProfile(), rootEntity.getEntityType(), rootEntity.getEntityID()) });
        addDebug("getModelVE:: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));
	}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "LSEOBUNDLE ABR.";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.18";
    }

    /***********************************************
     * 5			OSLEVEL	Validate				W	W	E		
    {LD: OSLEVEL} {OSLEVEL} does not match the Software LSEO's OS
     *
     * For LSEOBUNDLEs, verify that its OSLEVEL matches the OS found on a �ValueMetric� SWFEATURE 
     * that is part of the software LSEO. 
     * 
     * Find the WWSEO where LSEOBUNDLELSEO-d: WWSEOLSEO-u: MODELWWSEO-u: MODEL.COFCAT = 101 (Software) 
     *  & MODEL.APPLICATIONTYPE = 33 (OperatingSystem) THEN
     *  IF ValueOf(WWSEO: WWSEOSWPRODSTRUCT-d: SWFEATURE.OSLEVEL) IN ValueOf(LSEOBUNDLE.OSLEVEL) WHERE 
     *  	SWFEATURE.SWFCCAT = 319 (ValueMetric) 
     *  THEN
     *  ELSE
     *  	ErrorMessage LD(OSLEVEL) �Does not match the software LSEOs OS�
     *  END IF
     *  ELSE
     *  EXIT
     *  END IF
     *  
     * @param rootEntity
     * @param checklvl
     * @throws MiddlewareException
     * @throws SQLException 
     */
    private void validateOS(EntityItem rootEntity, int checklvl) throws MiddlewareException, SQLException
    {
		String oslvlAttr = "OSLEVEL";
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(oslvlAttr);
        if (metaAttr==null) {
           addDebug("validateOS ERROR:Attribute "+oslvlAttr+" NOT found in LSEOBUNDLE META data.");
           return;
        }

    	ArrayList bdlOS = new ArrayList();
		//get the oslevel from the bundle
		EANFlagAttribute rootOS = (EANFlagAttribute)rootEntity.getAttribute(oslvlAttr);
		if (rootOS != null) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) rootOS.get();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					bdlOS.add(mfArray[i].getFlagCode());
				}
			}
		}

	    addDebug("validateOS (5) "+rootEntity.getKey()+" oslevel "+bdlOS);

	    // get MODEL from VE2  LSEOBUNDLELSEO-d: WWSEOLSEO-u: MODELWWSEO-u: MODEL
		EntityGroup mdlGrp = mdlList.getEntityGroup("MODEL");
		// find MODEL.COFCAT = 101 (Software) & MODEL.APPLICATIONTYPE = 33 (OperatingSystem)
		for (int i=0; i<mdlGrp.getEntityItemCount(); i++){
			EntityItem mdlItem = mdlGrp.getEntityItem(i);
            String cofcat = getAttributeFlagEnabledValue(mdlItem , "COFCAT");
            String appType = getAttributeFlagEnabledValue(mdlItem , "APPLICATIONTYPE");
            addDebug("validateOS (5) "+mdlItem.getKey()+" COFCAT: "+cofcat+" APPLICATIONTYPE: "+appType);
            if (SOFTWARE.equals(cofcat)&& "33".equals(appType)){
				// find WWSEO for this MODEL
				Vector wwseoVct2 = PokUtils.getAllLinkedEntities(mdlItem, "MODELWWSEO", "WWSEO");
            	// find SWFEATURE for this WWSEO in VE1
            	EntityGroup wwseoGrp = m_elist.getEntityGroup("WWSEO");
            	Vector wwseoVct = new Vector(1);
            	for (int w=0; w<wwseoVct2.size(); w++){
					EntityItem wwseoItem2 = (EntityItem)wwseoVct2.elementAt(w);
					EntityItem wwseoItem1 = wwseoGrp.getEntityItem(wwseoItem2.getKey());
            		wwseoVct.add(wwseoItem1);
				}
				wwseoVct2.clear();
            	Vector swpsVct = PokUtils.getAllLinkedEntities(wwseoVct, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT");
            	addDebug("validateOS "+mdlItem.getKey()+" wwseoVct "+wwseoVct.size()+" swpsVct: "+swpsVct.size());
            	// find SWFEATURE.SWFCCAT = 319 (ValueMetric)
            	for (int w=0; w<swpsVct.size(); w++){
					EntityItem swpsItem = (EntityItem)swpsVct.elementAt(w);
            		addDebug("validateOS "+swpsItem.getKey()+" uplinkcnt: "+swpsItem.getUpLinkCount());
            		for (int u=0; u<swpsItem.getUpLinkCount(); u++){
						EntityItem swfcItem = (EntityItem)swpsItem.getUpLink(u);
						if (swfcItem.getEntityType().equals("SWFEATURE")){
							String swfccat = getAttributeFlagEnabledValue(swfcItem, "SWFCCAT");
							addDebug("validateOS "+swfcItem.getKey()+" SWFCCAT: "+swfccat);
							if ("319".equals(swfccat)){
								ArrayList featOS = new ArrayList();
								//get the oslevel from the feature
								EANFlagAttribute featOSattr = (EANFlagAttribute)swfcItem.getAttribute(oslvlAttr);
								if (featOSattr != null) {
									// Get the selected Flag codes.
									MetaFlag[] mfArray = (MetaFlag[]) featOSattr.get();
									for (int im = 0; im < mfArray.length; im++) {
										// get selection
										if (mfArray[im].isSelected()) {
											featOS.add(mfArray[im].getFlagCode());
										}
									}
								}
								addDebug("validateOS ValueMetric "+swfcItem.getKey()+" oslevel "+featOS);
							    //IF ValueOf(WWSEO: WWSEOSWPRODSTRUCT-d: SWFEATURE.OSLEVEL) IN ValueOf(LSEOBUNDLE.OSLEVEL) 
							    //THEN
							    // ELSE
							    //  	ErrorMessage LD(OSLEVEL) �Does not match the software LSEOs OS�
							    //  END IF
								//if (!(bdlOS.containsAll(featOS) && featOS.containsAll(bdlOS))) { this was the <> check
							     if (!(bdlOS.containsAll(featOS))) {
									//OSLEVEL_ERR = {0} Does not match the software LSEOs OS, {1} {2}
									args[0] = this.getLD_Value(rootEntity, oslvlAttr);
									args[1] = this.getLD_NDN(swfcItem);
									args[2] = this.getLD_Value(swfcItem, oslvlAttr);
									createMessage(checklvl,"OSLEVEL_ERR",args);
								}
								featOS.clear();
							}
						}
					}
				}
				swpsVct.clear();
				wwseoVct.clear();
			}
		}
		bdlOS.clear();
    }

    /*******************************************************
     *	6			BUNDLETYPE	Validate				W	W	E		
    	1) {LD: BUNDLETYPE} does not reflect all of the LSEO's MODEL's {LD: COFCAT}
    	2) {LD: BUNDLETYPE} has a value that does not exist in any LSEO's MODEL's {LD: COFCAT}
     *
     *For LSEOBUNDLEs, verify that its BUNDLETYPE is consistent with the types of children LSEOs.
     *
     *1.	AllValuesOf(LSEOBUNDLELSEO-u: WWSEOLSEO-u: MODELWWSEO u: MODEL.COFCAT) IN LSEOBUNDLE.LSEOBUNDLETYPE
     * 1) {LD: BUNDLETYPE} does not reflect all of the LSEO's MODEL's {LD: COFCAT}
     *2.	AllValuesOf(LSEOBUNDLE.LSEOBUNDLETYPE) IN  LSEOBUNDLELSEO-u: WWSEOLSEO-u: MODELWWSEO-u: MODEL.COFCAT
     * 2) {LD: BUNDLETYPE} has a value that does not exist in any LSEO's MODEL's {LD: COFCAT}

     * @param rootEntity
     * @param checklvl
     * @throws java.sql.SQLException
     * @throws MiddlewareException
     */
    private void validateBUNDLETYPE(EntityItem rootEntity, int checklvl) throws java.sql.SQLException, MiddlewareException
    {
		String attrCode = "BUNDLETYPE";
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(attrCode);
        if (metaAttr==null) {
           addDebug("validateBUNDLETYPE ERROR:Attribute "+attrCode+" NOT found in LSEOBUNDLE META data.");
           return;
        }

        ArrayList mdlTypes = new ArrayList();
		ArrayList bdlTypes = new ArrayList();

		EANFlagAttribute bdlTypeAttr = (EANFlagAttribute)rootEntity.getAttribute(attrCode);
		if (bdlTypeAttr != null) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) bdlTypeAttr.get();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					bdlTypes.add(mfArray[i].getFlagCode());
				}
			}
		}
		
		String prodhierFlag = PokUtils.getAttributeFlagValue(rootEntity, "BHPRODHIERCD");

	    addDebug("validateBUNDLETYPE (6-2) "+rootEntity.getKey()+" bdlTypes "+bdlTypes+" prodhierFlag "+prodhierFlag);
	    
	   	//Delete 20110322 6.02	IF		"Hardware" (100) or "Software" (101)	IN	LSEOBUNDLE	BUNDLETYPE	
	    //if(bdlTypes.contains(HARDWARE) || bdlTypes.contains(SOFTWARE)){
	    //remove it for lenovo Remove - RCQ00337764
//	    if(BHPRODHIERCD_No_ProdHCode.equals(prodhierFlag)){
//	    	//6.04			"00 - No Product Hierarchy Code" (BHPH0000)	<>	LSEOBUNDLE	BHPRODHIERCD		E	E	E		must not have {LD: BHPRODHIERCD} {BHPRODHIERCD}
//	    	//MUST_NOT_HAVE_ERR1= must not have {0}
//	    	args[0] = this.getLD_Value(rootEntity, "BHPRODHIERCD");
//	    	createMessage(CHECKLEVEL_E,"MUST_NOT_HAVE_ERR1",args);
//	    }
	    //}
	   	//Delete 20110322 6.06	END	6.02	

	    // get MODEL from VE2  LSEOBUNDLELSEO-d: WWSEOLSEO-u: MODELWWSEO-u: MODEL
		EntityGroup mdlGrp = mdlList.getEntityGroup("MODEL");
		for (int i=0; i<mdlGrp.getEntityItemCount(); i++){
			EntityItem mdlItem = mdlGrp.getEntityItem(i);

			String modelCOFCAT = getAttributeFlagEnabledValue(mdlItem, "COFCAT");
			if(modelCOFCAT == null)	{
				modelCOFCAT = "";
			}
			addDebug("validateBUNDLETYPE (6-1) "+mdlItem.getKey()+" COFCAT: "+modelCOFCAT);
			if (!bdlTypes.contains(modelCOFCAT)){
				//1.	AllValuesOf(LSEOBUNDLELSEO-u: WWSEOLSEO-u: MODELWWSEO u: MODEL.COFCAT) IN LSEOBUNDLE.LSEOBUNDLETYPE
				//{LD: BUNDLETYPE} does not reflect all of the LSEO's MODEL's {LD: COFCAT}
				//MODEL_TYPE_ERR= {0} does not reflect all of the LSEO's MODEL's {1}
				if (!mdlTypes.contains(modelCOFCAT)){ // if it contains it, then already output the msg for this cofcat
					args[0] = getLD_Value(rootEntity,attrCode);
					args[1] = getLD_Value(mdlItem,"COFCAT");
					createMessage(checklvl,"MODEL_TYPE_ERR",args);
				}
			}
			if (!mdlTypes.contains(modelCOFCAT)){
				mdlTypes.add(modelCOFCAT);
			}
		} // end mdl group

	    addDebug("validateBUNDLETYPE (6-2) all mdlTypes "+mdlTypes);
		if (!mdlTypes.containsAll(bdlTypes)) {
			//2.	AllValuesOf(LSEOBUNDLE.LSEOBUNDLETYPE) IN  LSEOBUNDLELSEO-u: WWSEOLSEO-u: MODELWWSEO-u: MODEL.COFCAT
			//BDLE_TYPE_ERR= {0} has a value that does not exist in any LSEO''s MODEL''s {1}
			//{LD: BUNDLETYPE} has a value that does not exist in any LSEO's MODEL's {LD: COFCAT}
			args[0] = getLD_Value(rootEntity,attrCode);
			args[1] = PokUtils.getAttributeDescription(mdlGrp, "COFCAT", "COFCAT");
			createMessage(checklvl,"BDLE_TYPE_ERR",args);
		}
		mdlTypes.clear();
		bdlTypes.clear();
	}
}
