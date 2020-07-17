//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2009,2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import java.sql.SQLException;
import java.util.*;

import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
 * AVAILABRSTATUS class
 *
 *BH FS ABR Data Quality 20120116.doc - "End of Service" (151) date checks
 *
 *BH FS ABR Data Quality 20111020e.xls -BH Defect 67890 (support for old data)
 *sets changes
 *from BH FS ABR Data Qualtity Sets 20110330.xls
 *changed VE EXRPT3AVAILDQ 
 *
 *from BH FS ABR Data Qualtity Checks 20110318.xls
 *remove FCTRANSACTION, update VE EXRPT3AVAILDQ
 *
 *from BH FS ABR Data Qualtity Checks 20110301.xls
 *date chgs and remove some checks
 *
 * From BH FS ABR Data Quality 20101112.doc
 * changed VE EXRPT3AVAILDQ remove IPSC*
 * sets changes
 * 
 * 	BH FS ABR Data Quality 20101012.doc
delete 220.0			WITHDRAWDATE	=>	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SVCMOD} {NDN: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
delete 221.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
Add	221.2			CountOf	=	0	
 * 
IX.	AVAIL

A.	Checking

There are four �Availability Type� (AVAILTYPE) that are considered based on current use by CHW:
1.	Planned Availability
2.	First Order
3.	Last Order
4.	End of Service

The Availability is associated with an Announcement and a child of the following entities:
Parent	Description
FCTRANSACTION	Feature Transaction
LSEO	Localized SEO
LSEOBUNDLE	LSEO Bundle
MODEL	Model
SVCMOD	Service Model
MODELCONVERT	Model Conversion
PRODSTRUCT	Product Structure
SWPRODSTRUCT	SW Product Structure

The checking is based on �Availability Type� (AVAILTYPE) and �Availability Announcement Type� (AVAILANNTYPE). 
If AVAILANNTYPE is �RFA� (RFA), then it checks the Announcement that it is in. It checks the offerings that 
reference it. An Availability will only be in at most one Announcement; however, many offerings of different 
(or the same) types may refer to it. All offerings that refer to it must be checked.

 * Virtual Entity
 * Lev	Entity1	RelType	Relator	Entity2	Dir
 * 0	LSEO		Relator	LSEOAVAIL	AVAIL	U
 * 0	LSEOBUNDLE	Relator	LSEOBUNDLEAVAIL	AVAIL	U
 * 0 PRODSTRUCT	Relator OOFAVAIL	AVAIL	U
 * 0 MODEL		Relator MODELAVAIL	AVAIL	U
 * 
AVAILABRSTATUS_class=COM.ibm.eannounce.abr.sg.AVAILABRSTATUS
AVAILABRSTATUS_enabled=true
AVAILABRSTATUS_idler_class=A
AVAILABRSTATUS_keepfile=true
AVAILABRSTATUS_read_only=true
AVAILABRSTATUS_report_type=DGTYPE01
AVAILABRSTATUS_vename=EXRPT3AVAILDQ
 */
//AVAILABRSTATUS.java,v
//Revision 1.22  2011/04/25 12:27:57  wendy
//update cvs revision num
//
//Revision 1.18  2011/03/08 21:29:28  wendy
//BH FS ABR Data Qualtity Checks 20110301.xls - date chgs and remove some checks
//
//Revision 1.13  2010/09/14 15:12:24  wendy
//BH FS ABR Data Quality 20100914.doc - SVCMOD can not link to an EOS AVAIL

//Revision 1.10  2010/01/21 22:20:25  wendy
//updates for BH FS ABR Data Quality 20100120.doc

//Revision 1.9  2010/01/07 12:51:22  wendy
//cvs failure again

//Revision 1.4  2009/11/24 17:06:17  yang
//*** keyword substitution change ***

//Revision 1.3  2009/11/09 19:08:39  wendy
//SR 76 - OTC - Support pre-configured/configured/sw/services offerings

//Revision 1.2  2009/08/17 15:03:53  wendy
//Added headings

//Revision 1.1  2009/08/14 22:26:30  wendy
//SR10, 11, 12, 15, 17 BH updates phase 4

public class AVAILABRSTATUS extends DQABRSTATUS
{
	private static final String[] LSEOABRS = {"ADSABRSTATUS"};
	private static final String[] MODELABRS = {"ADSABRSTATUS"};
	private static final String[] SVCMODABRS = {"ADSABRSTATUS"};
	private static final String[] LSEOBDLABRS = {"ADSABRSTATUS"};
	private static final String[] MAINTABRS = {"ADSABRSTATUS"};
	
	private static final String[] LSEOEPIMS = {"EPIMSABRSTATUS"};
	private static final String[] LSEOBLEPIMS = {"EPIMSABRSTATUS"};
	
	private static final String[] RFCABRS = {"RFCABRSTATUS"};
	/**********************************
	 * ready4review
	 */
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/*
	 * 
from sets ss:
	21.00		AVAIL		Root Entity							
	22.00		WHEN			AVAIL	AVAILTYPE	=	"Planned Availability" (146)			
	23.00		IF		AVAILANNA	ANNOUNCEMENT	ANNTYPE	=	"New" (19)		
Add 2011-10-20		23.200	R1.0	AND			ANNOUNCEMENT	PDHDOMAIN	IN	XCC_LIST		
	24.00		AND			ANNOUNCEMENT	STATUS	=	"Final" (0020)	WWPRTABRSTATUS		&ABRWAITODS2 
	25.00		SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
	26.00		END	23.00								
	27.00		END	22.00	

	28.00		WHEN			AVAIL	AVAILTYPE	=	"Last Order" (149)			
	29.00		IF		AVAILANNA	ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)	
Add 2011-10-20		29.200	R1.0	AND			ANNOUNCEMENT	PDHDOMAIN	IN	XCC_LIST			
	30.00		AND			ANNOUNCEMENT	STATUS	=	"Final" (0020)	QSMRPTABRSTATUS 		&QSMWAITODS 
	30.20   	END	29.00								
	31.00		END	28.00	

	32.00		WHEN			AVAIL	STATUS	=	"Final" (0020)			
	33.00		IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)			
	34.00		IF		AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)		
34.020	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Final" (0020)			
34.040	R1.0	SET			MODELCONVERT				ADSABRSTATUS		&ADSFEED
34.060	R1.0	END	34.020								
		
	35.00		IF		LSEOAVAIL-u	LSEO	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
	36.00		SET			LSEO				ADSABRSTATUS		&ADSFEED
	37.00		END	35.00								
	38.00		IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
	38.20		SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
	39.00		END	38.00

Delete 2011-10-20		39.200		IF			MODEL	ANNDATE	<=	"2010-03-01"			
Delete 2011-10-20		39.210		AND			MODEL	PDHDOMAIN	Not In	XCC_LIST			
Delete 2011-10-20		39.230		IF			MODEL	STATUS	=	"Final" (0020)			
Delete 2011-10-20		39.250		SET			MODEL				ADSABRSTATUS		&ADSFEED
Delete 2011-10-20		39.270		END	39.230								
Delete 2011-10-20		39.290		ELSE	39.200								
		40.000		IF		MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)			
		41.000		SET			MODEL				ADSABRSTATUS		&ADSFEED
		42.000		END	40.000								
Delete 2011-10-20		42.100		END	39.200								
	
42.200	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Final" (0020)			
42.220	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
42.240	R1.0	END	42.200								
42.300	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Final" (0020)			
42.320	R1.0	SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED
42.340	R1.0	END	42.300								
42.400	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)			
42.420	R1.0	AND		SVCMODSVCSEO-u:	SVCMOD	STATUS	=	"Final" (0020)			
42.440	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
42.460	R1.0	END	42.400								
																				
	44.00		IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Final" (0020)			
	45.00		SET			SVCMOD				ADSABRSTATUS		&ADSFEED
	46.00		END	44.00		
	46.01		END	34.00						
	46.02		ELSE	33.00								
	46.04		IF		LSEOAVAIL-u	LSEO	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
	46.06		SET			LSEO				ADSABRSTATUS		&ADSFEED
	46.08		END	46.04								
	46.10		IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
	46.11		SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
	46.12		END	46.10								
Delete 2011-10-20		46.140		IF			MODEL	ANNDATE	<=	"2010-03-01"			
Delete 2011-10-20		46.160		AND			MODEL	PDHDOMAIN	Not In	XCC_LIST			
Delete 2011-10-20		46.170		IF			MODEL	STATUS	=	"Final" (0020)			
Delete 2011-10-20		46.180		SET			MODEL				ADSABRSTATUS		&ADSFEED
Delete 2011-10-20		46.190		END	46.170								
Delete 2011-10-20		46.200		ELSE	46.140								
		46.220		IF		MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)			
		46.240		SET			MODEL				ADSABRSTATUS		&ADSFEED
		46.260		END	46.220								
Delete 2011-10-20		46.280		END	46.140								
								
	46.30		IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Final" (0020)			
	46.32		SET			SVCMOD				ADSABRSTATUS		&ADSFEED
	46.34		END	46.30								
47.000	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Final" (0020)			
47.020	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
47.040	R1.0	END	47.000								
47.060	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Final" (0020)			
47.080	R1.0	SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED
47.100	R1.0	END	47.060								
47.120	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)			
47.140	R1.0	AND		SVCMODSVCSEO-u:	SVCMOD	STATUS	=	"Final" (0020)			
47.160	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
47.180	R1.0	END	47.120								
47.200	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Final" (0020)			
47.220	R1.0	SET			MODELCONVERT				ADSABRSTATUS		&ADSFEED
47.240	R1.0	END	47.180								

	48.00		END	33.00								
	49.00		END	32.00	

	49.10	R1.0	WHEN			AVAIL	STATUS	=	"Ready for Review" (0040)			
	49.11	R1.0	IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)			
	49.12	R1.0	IF		AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)	
49.140	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Ready for Review" (0040)		
49.142	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.144	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR
49.146	R1.0	END	49.140							
49.150	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
49.152	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.154	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
49.156	R1.0	END	49.150							
49.160	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
49.162	R1.0	AND			PRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.164	R1.0	SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
49.166	R1.0	END	49.160							
49.170	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
49.172	R1.0	AND		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)		
49.174	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.176	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR
49.178	R1.0	END	49.170							
			
	49.20	R1.0	IF		LSEOAVAIL-u	LSEO	STATUS	=	"Ready for Review" (0040)			
	49.22	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
	49.24	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
	49.26	R1.0	END	49.20								
	49.30	R1.0	IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)			
	49.32	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
	49.34	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
	49.36	R1.0	END	49.30								
Delete 2011-10-20		49.370	R1.0	IF			MODEL	ANNDATE	<=	"2010-03-01"		
Delete 2011-10-20		49.380	R1.0	AND			MODEL	PDHDOMAIN	Not In	XCC_LIST		
Delete 2011-10-20		49.390	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
Delete 2011-10-20		49.400	R1.0	ELSE	49.370							
		49.410	R1.0	IF		MODELAVAIL-u	MODEL	STATUS	=	"Ready for Review" (0040)		
		49.420	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
		49.440	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
		49.460	R1.0	END	49.410							
Delete 2011-10-20		49.470	R1.0	END	49.370							
														
	49.48	R1.0	IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
	49.49	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
	49.51	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
	49.52	R1.0	END	49.48
	46.53	R1.0	END	49.12								
	49.54	R1.0	ELSE	49.11								
	49.56	R1.0	IF		LSEOAVAIL-u	LSEO	STATUS	=	"Ready for Review" (0040)	
49.561	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
			
	49.58	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
	49.60	R1.0	END	49.56								
	49.62	R1.0	IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)	
49.621	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
			
	49.64	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
	49.66	R1.0	END	49.62								
Delete 2011-10-20		49.680	R1.0	IF			MODEL	ANNDATE	<=	"2010-03-01"		
Delete 2011-10-20		49.700	R1.0	AND			MODEL	PDHDOMAIN	Not In	XCC_LIST		
Delete 2011-10-20		49.720	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
Delete 2011-10-20		49.740	R1.0	ELSE	49.680							
		49.760	R1.0	IF		MODELAVAIL-u	MODEL	STATUS	=	"Ready for Review" (0040)		
		49.761	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
		49.780	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
		49.800	R1.0	END	49.760							
Delete 2011-10-20		49.820	R1.0	END	49.680							
							
	49.84	R1.0	IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Ready for Review" (0040)
49.841	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
				
	49.86	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
	49.88	R1.0	END	49.84								
49.882	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Ready for Review" (0040)		
49.883	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.884	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR
49.885	R1.0	END	49.882							
49.886	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
49.887	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.888	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
49.889	R1.0	END	49.886							
49.890	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
49.891	R1.0	AND			PRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.892	R1.0	SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
49.893	R1.0	END	49.890							
49.894	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
	R1.0	AND		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)		
49.895	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.896	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR
49.897	R1.0	END	49.894							

	49.90	R1.0	END	49.11								
	49.92	R1.0	END	49.10								
	50.00		END	21.00	AVAIL								

	 */    
	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq or draft)
	 * 
	49.10	R1.0	WHEN			AVAIL	STATUS	=	"Ready for Review" (0040)			
	49.11	R1.0	IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)			
	49.12	R1.0	IF		AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
49.140	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Ready for Review" (0040)		
49.142	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.144	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR
49.146	R1.0	END	49.140							
49.150	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
49.152	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.154	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
49.156	R1.0	END	49.150							
49.160	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
49.162	R1.0	AND			PRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.164	R1.0	SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
49.166	R1.0	END	49.160							
49.170	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
49.172	R1.0	AND		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)		
49.174	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.176	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR
49.178	R1.0	END	49.170							
				
	49.20	R1.0	IF		LSEOAVAIL-u	LSEO	STATUS	=	"Ready for Review" (0040)			
	49.22	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
	49.24	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
	49.26	R1.0	END	49.20								
	49.30	R1.0	IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)			
	49.32	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
	49.34	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
	49.36	R1.0	END	49.30								
Delete 2011-10-20		49.370	R1.0	IF			MODEL	ANNDATE	<=	"2010-03-01"		
Delete 2011-10-20		49.380	R1.0	AND			MODEL	PDHDOMAIN	Not In	XCC_LIST		
Delete 2011-10-20		49.390	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
Delete 2011-10-20		49.400	R1.0	ELSE	49.370							
		49.410	R1.0	IF		MODELAVAIL-u	MODEL	STATUS	=	"Ready for Review" (0040)		
		49.420	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
		49.440	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
		49.460	R1.0	END	49.410							
Delete 2011-10-20		49.470	R1.0	END	49.370							
															
	49.48	R1.0	IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
	49.49	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
	49.51	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
	49.52	R1.0	END	49.48	
	46.53	R1.0	END	49.12															
	49.54	R1.0	ELSE	49.11								
	49.56	R1.0	IF		LSEOAVAIL-u	LSEO	STATUS	=	"Ready for Review" (0040)	
49.561	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
			
	49.58	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
	49.60	R1.0	END	49.56								
	49.62	R1.0	IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)	
49.621	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
			
	49.64	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
	49.66	R1.0	END	49.62								
Delete 2011-10-20		49.680	R1.0	IF			MODEL	ANNDATE	<=	"2010-03-01"		
Delete 2011-10-20		49.700	R1.0	AND			MODEL	PDHDOMAIN	Not In	XCC_LIST		
Delete 2011-10-20		49.720	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
Delete 2011-10-20		49.740	R1.0	ELSE	49.680							
		49.760	R1.0	IF		MODELAVAIL-u	MODEL	STATUS	=	"Ready for Review" (0040)		
		49.761	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
		49.780	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
		49.800	R1.0	END	49.760							
Delete 2011-10-20		49.820	R1.0	END	49.680							
							
	49.84	R1.0	IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Ready for Review" (0040)
49.841	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
				
	49.86	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
	49.88	R1.0	END	49.84								
49.882	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Ready for Review" (0040)		
49.883	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.884	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR
49.885	R1.0	END	49.882							
49.886	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
49.887	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.888	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
49.889	R1.0	END	49.886							
49.890	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
49.891	R1.0	AND			PRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.892	R1.0	SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
49.893	R1.0	END	49.890							
49.894	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
	R1.0	AND		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)		
49.895	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
49.896	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR
49.897	R1.0	END	49.894	
	49.90	R1.0	END	49.11								
	49.92	R1.0	END	49.10		
	 */
	protected void completeNowR4RProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		//49.10	R1.0	WHEN			AVAIL	STATUS	=	"Ready for Review" (0040)	
		if(doR10processing()){
			EntityItem avail = m_elist.getParentEntityGroup().getEntityItem(0);
 
			String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
			addDebug("nowRFR "+avail.getKey()+"  availAnntypeFlag "+availAnntypeFlag);

			if (availAnntypeFlag==null){
				availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
			}
//			10-31-2012	AVAL - RFR - do not consider Announcement	"A Soft Delete could be accomplished 
//			my making the statements a comment or by branching around them
//			The purpose is to be able to restore at some point in the future with more logic should it be required"
			//49.11	R1.0	IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)			
//			if(AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//				boolean hadFinalOrRFRAnn = false;
//				Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//				addDebug("nowRFR "+avail.getKey()+"  annVct "+annVct.size());
//				for (int av2=0; av2<annVct.size(); av2++){
//					EntityItem annItem = (EntityItem)annVct.elementAt(av2);
//					addDebug("nowRFR "+annItem.getKey());
//					//49.12	R1.0	IF		AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
//					if(statusIsRFRorFinal(annItem, "ANNSTATUS")){
//						hadFinalOrRFRAnn = true;
//						break;
//					}
//				}// end announcement loop
//				addDebug("nowRFR hadFinalOrRFRAnn "+hadFinalOrRFRAnn);
//				if(hadFinalOrRFRAnn){
//					//49.140	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Ready for Review" (0040)		
//					//49.142	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
//					//49.144	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR
//					//49.146	R1.0	END	49.140	
//					doRFR_ADSXML("MODELCONVERT");
//					
//					//49.150	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
//					//49.152	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
//					//49.154	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
//					//49.156	R1.0	END	49.150	
//					doRFR_ADSXML("SWPRODSTRUCT");
//					
//					//49.160	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
//					//49.162	R1.0	AND			PRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
//					//49.164	R1.0	SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
//					//49.166	R1.0	END	49.160		
//					doRFR_ADSXML("PRODSTRUCT");
//					
//					//49.170	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
//					//49.172	R1.0	AND		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)		
//					//49.174	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
//					//49.176	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR
//					//49.178	R1.0	END	49.170	
//					verifySVCSEORFRAndQueue(avail);
//
//					//49.20	R1.0	IF		LSEOAVAIL-u	LSEO	STATUS	=	"Ready for Review" (0040)			
//					//49.22	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
//					//49.24	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
//					//49.26	R1.0	END	49.20	
//					doRFR_ADSXML("LSEO");
//
//					//49.30	R1.0	IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)			
//					//49.32	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
//					//49.34	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
//					//49.36	R1.0	END	49.30	
//					doRFR_ADSXML("LSEOBUNDLE");
//
//					Vector mdlVct = PokUtils.getAllLinkedEntities(avail, "MODELAVAIL", "MODEL");
//					for (int i = 0; i<mdlVct.size(); i++){
//						EntityItem mdlItem = (EntityItem)mdlVct.elementAt(i);
//						//boolean oldData = this.isOldData(mdlItem, "ANNDATE");
//						//boolean inxcclist = domainInRuleList(mdlItem, "XCC_LIST");
//						addDebug("nowRFR: "+mdlItem.getKey());//+" domain in XCCLIST "+inxcclist+" olddata "+oldData);
//
//						//Delete 2011-10-20	49.37	R1.0	IF			MODEL	ANNDATE	<=	"2010-03-01"			
//						//Delete 2011-10-20	49.38	R1.0	AND			MODEL	PDHDOMAIN	Not In	XCC_LIST		
//						/*if (oldData && !inxcclist){	
//							if(statusIsRFR(mdlItem)){
//								//Delete 2011-10-20	49.39	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR	
//								setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(mdlItem,"ADSABRSTATUS"),mdlItem);
//							}
//						}else{*/
//							//Delete 2011-10-20	49.40	R1.0	ELSE	49.37								
//							//49.41	R1.0	IF		MODELAVAIL-u	MODEL	STATUS	=	"Ready for Review" (0040)			
//							//49.42	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
//							//49.44	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR	
//							doRFR_ADSXML(mdlItem);
//							//49.46	R1.0	END	49.41								
//							//Delete 2011-10-20	49.47	R1.0	END	49.37
//						//}
//					}
//					mdlVct.clear();
//
//					//49.48	R1.0	IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
//					//49.49	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
//					//49.51	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
//					//49.52	R1.0	END	49.48								
//					doRFR_ADSXML(avail, "SVCMODAVAIL", "SVCMOD");
//				}
//				//46.53	R1.0	END	49.12
//			}else { // not RFA 
//				addDebug("nowRFR avail wasnt RFA");
				//49.54		ELSE	49.11	
	
				//49.56		IF		LSEOAVAIL-u	LSEO	STATUS	=	"Ready for Review" (0040)
				//49.561	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
				//49.58		SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
				//49.60		END	49.56	
				doRFR_ADSXML("LSEO");

				//49.62		IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)	
				//49.621	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
				//49.64		SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
				//49.66		END	49.62	
				doRFR_ADSXML("LSEOBUNDLE");

				Vector mdlVct = PokUtils.getAllLinkedEntities(avail, "MODELAVAIL", "MODEL");
				for (int i = 0; i<mdlVct.size(); i++){
					EntityItem mdlItem = (EntityItem)mdlVct.elementAt(i);
					//Delete 2011-10-20	49.68		IF			MODEL	ANNDATE	<=	"2010-03-01"			
					//Delete 2011-10-20	49.70		AND			MODEL	PDHDOMAIN	Not In	XCC_LIST	
					if(statusIsRFR(mdlItem)){
						//boolean oldData = this.isOldData(mdlItem, "ANNDATE");
						//boolean inxcclist = domainInRuleList(mdlItem, "XCC_LIST");
						addDebug("nowRFR: "+mdlItem.getKey());//+" domain in XCCLIST "+inxcclist+" olddata "+oldData);
						// this is a noop
						/*if (oldData && !inxcclist){	
							//Delete 2011-10-20	49.72		SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR	
							setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(mdlItem,"ADSABRSTATUS"),mdlItem);
						}else{*/
							//49.76		IF		MODELAVAIL-u	MODEL	STATUS	=	"Ready for Review" (0040)
							//49.761	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
							//49.78		SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
							doRFR_ADSXML("MODEL");
							//49.80		END	49.76								
							//Delete 2011-10-20	49.82		END	49.68
						//}
					}
				}
				mdlVct.clear();

				//49.84		IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Ready for Review" (0040)
				//49.841	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
				//49.86		SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR		
				//49.88		END	49.84		
				doRFR_ADSXML(avail, "SVCMODAVAIL", "SVCMOD");
				
				//49.882	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Ready for Review" (0040)		
				//49.883	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
				//49.884	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR
				//49.885	R1.0	END	49.882	
				doRFR_ADSXML("MODELCONVERT");
				
				//49.886	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
				//49.887	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
				//49.888	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
				//49.889	R1.0	END	49.886	
				doRFR_ADSXML("SWPRODSTRUCT");
				
				//49.890	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
				//49.891	R1.0	AND			PRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
				//49.892	R1.0	SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
				//49.893	R1.0	END	49.890
				doRFR_ADSXML("PRODSTRUCT");
				
				//49.894	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
				//	R1.0	AND		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)		
				//49.895	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
				//49.896	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR
				//49.897	R1.0	END	49.894		
				
					
				//		IF		MAINTMFAVAIL-u	MAINTPRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
				//		AND			MAINTPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
				//		SET			MAINTPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
				//		END	4.001												
				doRFR_ADSXML("MAINTPRODSTRUCT");
				
				verifySVCSEORFRAndQueue(avail);
				
				//49.90	R1.0	END	49.11
//			}
		} // end do r10 processing
		//49.92	R1.0	END	49.10	
	}
	
	/**
	 * go from avail thru relator to entitytype
	 * @param avail
	 * @param relator
	 * @param entityType
	 * @param attrcodes
	 */
	private void doRFR_ADSXML(EntityItem avail, String relator, String entityType) {
		Vector entityVct = PokUtils.getAllLinkedEntities(avail, relator, entityType);
		for (int entityCount = 0; entityCount < entityVct.size(); entityCount++) {
			EntityItem ei = (EntityItem) entityVct.elementAt(entityCount);
			doRFR_ADSXML(ei);
		}
		entityVct.clear();
	}

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
	 * 
	21.00		AVAIL		Root Entity							
	22.00		WHEN			AVAIL	AVAILTYPE	=	"Planned Availability" (146)			
	23.00		IF		AVAILANNA	ANNOUNCEMENT	ANNTYPE	=	"New" (19)	
Add 2011-10-20		23.200	R1.0	AND			ANNOUNCEMENT	PDHDOMAIN	IN	XCC_LIST
	24.00		AND			ANNOUNCEMENT	STATUS	=	"Final" (0020)	WWPRTABRSTATUS		&ABRWAITODS2 
	25.00		SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
	26.00		END	23.00								
	27.00		END	22.00	

	28.00		WHEN			AVAIL	AVAILTYPE	=	"Last Order" (149)			
	29.00		IF		AVAILANNA	ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)	
Add 2011-10-20		29.200	R1.0	AND			ANNOUNCEMENT	PDHDOMAIN	IN	XCC_LIST
	30.00		AND			ANNOUNCEMENT	STATUS	=	"Final" (0020)	QSMRPTABRSTATUS 		&QSMWAITODS 
	30.20   	END	29.00								
	31.00		END	28.00	

	32.00		WHEN			AVAIL	STATUS	=	"Final" (0020)			
	33.00		IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)			
	34.00		IF		AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)	
34.020	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Final" (0020)			
34.040	R1.0	SET			MODELCONVERT				ADSABRSTATUS		&ADSFEED
34.060	R1.0	END	34.020								
			
	35.00		IF		LSEOAVAIL-u	LSEO	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
	36.00		SET			LSEO				ADSABRSTATUS		&ADSFEED
	37.00		END	35.00								
	38.00		IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
	38.20		SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
	39.00		END	38.00
Delete 2011-10-20		39.200		IF			MODEL	ANNDATE	<=	"2010-03-01"			
Delete 2011-10-20		39.210		AND			MODEL	PDHDOMAIN	Not In	XCC_LIST			
Delete 2011-10-20		39.230		IF			MODEL	STATUS	=	"Final" (0020)			
Delete 2011-10-20		39.250		SET			MODEL				ADSABRSTATUS		&ADSFEED
Delete 2011-10-20		39.270		END	39.230								
Delete 2011-10-20		39.290		ELSE	39.200								
		40.000		IF		MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)			
		41.000		SET			MODEL				ADSABRSTATUS		&ADSFEED
		42.000		END	40.000								
Delete 2011-10-20		42.100		END	39.200								

42.200	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Final" (0020)			
42.220	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
42.240	R1.0	END	42.200								
42.300	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Final" (0020)			
42.320	R1.0	SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED
42.340	R1.0	END	42.300								
42.400	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)			
42.420	R1.0	AND		SVCMODSVCSEO-u:	SVCMOD	STATUS	=	"Final" (0020)			
42.440	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
42.460	R1.0	END	42.400								
																				
	44.00		IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Final" (0020)			
	45.00		SET			SVCMOD				ADSABRSTATUS		&ADSFEED
	46.00		END	44.00
	46.01		END	34.00								
	46.02		ELSE	33.00								
	46.04		IF		LSEOAVAIL-u	LSEO	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
	46.06		SET			LSEO				ADSABRSTATUS		&ADSFEED
	46.08		END	46.04								
	46.10		IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
	46.11		SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
	46.12		END	46.10								
Delete 2011-10-20		46.140		IF			MODEL	ANNDATE	<=	"2010-03-01"			
Delete 2011-10-20		46.160		AND			MODEL	PDHDOMAIN	Not In	XCC_LIST			
Delete 2011-10-20		46.170		IF			MODEL	STATUS	=	"Final" (0020)			
Delete 2011-10-20		46.180		SET			MODEL				ADSABRSTATUS		&ADSFEED
Delete 2011-10-20		46.190		END	46.170								
Delete 2011-10-20		46.200		ELSE	46.140								
		46.220		IF		MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)			
		46.240		SET			MODEL				ADSABRSTATUS		&ADSFEED
		46.260		END	46.220								
Delete 2011-10-20		46.280		END	46.140								
								
	46.30		IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Final" (0020)			
	46.32		SET			SVCMOD				ADSABRSTATUS		&ADSFEED
	46.34		END	46.30								
47.000	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Final" (0020)			
47.020	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
47.040	R1.0	END	47.000								
47.060	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Final" (0020)			
47.080	R1.0	SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED
47.100	R1.0	END	47.060								
47.120	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)			
47.140	R1.0	AND		SVCMODSVCSEO-u:	SVCMOD	STATUS	=	"Final" (0020)			
47.160	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
47.180	R1.0	END	47.120								
47.200	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Final" (0020)			
47.220	R1.0	SET			MODELCONVERT				ADSABRSTATUS		&ADSFEED
47.240	R1.0	END	47.180								

	48.00		END	33.00								
	49.00		END	32.00		
	 */
	protected void completeNowFinalProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		String availType = getAttributeFlagEnabledValue(rootEntity, "AVAILTYPE");
		addDebug(" "+rootEntity.getKey()+" type "+availType);

		//22.00	WHEN			AVAIL	AVAILTYPE	=	"Planned Availability" (146)			
		if (PLANNEDAVAIL.equals(availType)) {
			EntityGroup egrp = m_elist.getEntityGroup("ANNOUNCEMENT");
			for (int i = 0; i < egrp.getEntityItemCount(); i++) {
				EntityItem ei = egrp.getEntityItem(i);
				String annType = getAttributeFlagEnabledValue(ei, "ANNTYPE");
				addDebug(" "+ei.getKey() + " type " + annType);

				//23.00	IF		AVAILANNA	ANNOUNCEMENT	ANNTYPE	=	"New" (19)
				//Add 2011-10-20		23.200	R1.0	AND			ANNOUNCEMENT	PDHDOMAIN	IN	XCC_LIST
				if (statusIsFinal(ei,"ANNSTATUS") &&
						domainInRuleList(ei, "XCC_LIST")) {					
					if (ANNTYPE_NEW.equals(annType)) { //PlannedAvail and New ann
						addDebug(" "+ei.getKey()+" is Final and New");
						//24.00	AND			ANNOUNCEMENT	STATUS	=	"Final" (0020)	WWPRTABRSTATUS		&ABRWAITODS2 
						setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", getQueuedValueForItem(ei,"WWPRTABRSTATUS"), ei);
						//25.00	SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
						setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValueForItem(ei,"QSMRPTABRSTATUS"), ei);
					}
				}
				//26.00	END	23.00																	
			}
		}
		//27.00	END	22.00

		//28.00 WHEN			AVAIL	AVAILTYPE	=	"Last Order" (149)			
		if (LASTORDERAVAIL.equals(availType)) { 
			EntityGroup egrp = m_elist.getEntityGroup("ANNOUNCEMENT");
			for (int i = 0; i < egrp.getEntityItemCount(); i++) {
				EntityItem ei = egrp.getEntityItem(i);
				String annType = getAttributeFlagEnabledValue(ei, "ANNTYPE");
				addDebug(" "+ei.getKey() + "  type " + annType);

				//29.00	IF		AVAILANNA	ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)		
				//Add 2011-10-20	29.200		AND			ANNOUNCEMENT	PDHDOMAIN	IN	XCC_LIST
				//30.00	AND			ANNOUNCEMENT	STATUS	=	"Final" (0020)	QSMRPTABRSTATUS 		&QSMWAITODS 
				if (statusIsFinal(ei,"ANNSTATUS") &&
						domainInRuleList(ei, "XCC_LIST")) {					
					if (ANNTYPE_EOL.equals(annType)) { 
						addDebug(" "+ei.getKey()+" is Final and EOL");
						//QSMRPTABRSTATUS 	NORFR	&QSMWAITODS 
						setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValueForItem(ei,"QSMRPTABRSTATUS"), ei);
					}
				}
			}
		}
		//31.00	END	28.00

		//32.00		WHEN			AVAIL	STATUS	=	"Final" (0020)	
		String availAnntypeFlag = PokUtils.getAttributeFlagValue(rootEntity, "AVAILANNTYPE");
		addDebug("nowFinal "+rootEntity.getKey()+"  availAnntypeFlag "+availAnntypeFlag);

		if (availAnntypeFlag==null){
			availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
		}
		
		// Check if has Hardware or HIPO MODEL or has PRODSTRUCT
		boolean hasHardwareOrHIPOModel = false;
		Vector mdlVct = PokUtils.getAllLinkedEntities(rootEntity, "MODELAVAIL", "MODEL");
		for (int i = 0; i<mdlVct.size(); i++){
			EntityItem mdlItem = (EntityItem)mdlVct.elementAt(i);
			if (isHardwareOrHIPOModel(mdlItem)) {
				hasHardwareOrHIPOModel = true;
				break;
			}
		}
		mdlVct.clear();
		if (!hasHardwareOrHIPOModel) {
			Vector psVct = PokUtils.getAllLinkedEntities(rootEntity, "OOFAVAIL", "PRODSTRUCT");
			addDebug("check if has PRODSTRUCT for  " + rootEntity.getKey() + " size " + psVct.size());
			if (psVct.size() > 0) {
				hasHardwareOrHIPOModel = true;
			}
			psVct.clear();
		}			

		//33.00		IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)			
		if(AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
			boolean hasFinalAnn = false;
			Vector annVct = PokUtils.getAllLinkedEntities(rootEntity, "AVAILANNA", "ANNOUNCEMENT");
			addDebug("nowFinal "+rootEntity.getKey()+"  annVct "+annVct.size());
			for (int av2=0; av2<annVct.size(); av2++){
				EntityItem annItem = (EntityItem)annVct.elementAt(av2);
				addDebug("nowFinal "+annItem.getKey());
				//34.00		IF		AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)	
				if(statusIsFinal(annItem, "ANNSTATUS")){
					// TODO
					String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
					if (hasHardwareOrHIPOModel && isQsmANNTYPE(anntype)) {
						setFlagValue(m_elist.getProfile(), "QSMCREFABRSTATUS", getQueuedValueForItem(annItem,"QSMCREFABRSTATUS"), annItem);
						setFlagValue(m_elist.getProfile(), "QSMFULLABRSTATUS", getQueuedValueForItem(annItem,"QSMFULLABRSTATUS"), annItem);
					}					
					hasFinalAnn = true;
//					break;
				}
			}// end announcement loop
			addDebug("nowFinal hasFinalAnn "+hasFinalAnn);
			if(hasFinalAnn){
//				20121210 Change	Moved to next line for clarity	35.000		IF		LSEOAVAIL-u	LSEO	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS		
//				20121210 Change	Used to queue ADSABRSTATUS	36.000		SET			LSEO				EPIMSABRSTATUS		&ABRWAITODS		
//						37.000		END	35.000										
//				20121210 Change	Moved to next line for clarity	38.000		IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS		
//				20121210 Change	Used to queue ADSABRSTATUS	38.200		SET			LSEOBUNDLE				EPIMSABRSTATUS		&ABRWAITODS					
				verifyFinalAndQueue(rootEntity, "LSEOAVAIL", "LSEO",LSEOEPIMS);	
				verifyFinalAndQueue(rootEntity, "LSEOBUNDLEAVAIL", "LSEOBUNDLE",LSEOBLEPIMS);	
				
			}
			//46.01		END	34.00
		}
//			else {END	33.00
//			addDebug("nowFinal not RFA ");
			
			//46.04		IF		LSEOAVAIL-u	LSEO	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
			//46.06		SET			LSEO				ADSABRSTATUS		&ADSFEED
			//46.08		END	46.04	
			verifyFinalAndQueue(rootEntity, "LSEOAVAIL", "LSEO",LSEOABRS);	
			//46.10		IF		LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
			//46.11		SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
			//46.12		END	46.10	
			verifyFinalAndQueue(rootEntity, "LSEOBUNDLEAVAIL", "LSEOBUNDLE",LSEOBDLABRS);

			//Delete 2011-10-20		46.140		IF			MODEL	ANNDATE	<=	"2010-03-01"			
			//Delete 2011-10-20		46.160		AND			MODEL	PDHDOMAIN	Not In	XCC_LIST			
			//Delete 2011-10-20		46.170		IF			MODEL	STATUS	=	"Final" (0020)			
			//Delete 2011-10-20		46.180		SET			MODEL				ADSABRSTATUS		&ADSFEED
			//Delete 2011-10-20		46.190		END	46.170								
			//Delete 2011-10-20		46.200		ELSE	46.140								
			//			46.220		IF		MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)			
			//			46.240		SET			MODEL				ADSABRSTATUS		&ADSFEED
			//46.240	SetSinceFinal			MODEL	RFCABRSTATUS	=	"Passed" (0030)	RFCABRSTATUS		&OIMSFEED
			//46.242	SetSinceFinal			MODEL	RFCABRSTATUS	<>	"Passed" (0030)	RFCABRSTATUS		&OIMSFEED
			//			46.260		END	46.220								
			//Delete 2011-10-20		46.280		END	46.140								
			verifyModelFinalAndQueue(rootEntity, MODELABRS);

			//46.30		IF		SVCMODAVAIL-u	SVCMOD	STATUS	=	"Final" (0020)			
			//46.32		SET			SVCMOD				ADSABRSTATUS		&ADSFEED
			//46.34		END	46.30	
			verifyFinalAndQueue(rootEntity, "SVCMODAVAIL", "SVCMOD",SVCMODABRS);
			
			//47.000	R1.0	IF		SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Final" (0020)			
			//47.020	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
			//47.040	R1.0	END	47.000		
			verifyFinalAndQueue(rootEntity, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT",MODELABRS);
			
			//47.060	R1.0	IF		OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Final" (0020)			
			//47.080	R1.0	SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED
			//47.080	SET			PRODSTRUCT				RFCABRSTATUS		&OIMSFEED
			//47.100	R1.0	END	47.060		
			verifyFinalAndQueue(rootEntity, "OOFAVAIL", "PRODSTRUCT",MODELABRS);
			verifyFinalAndQueue(rootEntity, "OOFAVAIL", "PRODSTRUCT",RFCABRS);
			//47.120	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)			
			//47.140	R1.0	AND		SVCMODSVCSEO-u:	SVCMOD	STATUS	=	"Final" (0020)			
			//47.160	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
			//47.180	R1.0	END	47.120	
			verifySVCSEOFinalAndQueue(rootEntity);
			
			//47.200	R1.0	IF		MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Final" (0020)			
			//47.220	R1.0	SET			MODELCONVERT				ADSABRSTATUS		&ADSFEED
			//47.240	R1.0	END	47.180	
			verifyFinalAndQueue(rootEntity, "MODELCONVERTAVAIL", "MODELCONVERT",MODELABRS);
			verifyFinalAndQueue(rootEntity, "MODELCONVERTAVAIL", "MODELCONVERT",RFCABRS);
			
			verifyFinalAndQueue(rootEntity, "MAINTMFAVAIL", "MAINTPRODSTRUCT",MAINTABRS);
			//48.00		
//		}
		//49.00		END	32.00	
	}

	/**
	 * go from avail thru relator to entitytype, if it is final then queue abrs 
	 * @param avail
	 * @param relator
	 * @param entityType
	 * @param attrcodes
	 */
	private void verifyFinalAndQueue(EntityItem avail, String relator, String entityType,
			String[] attrcodes) {
		Vector entityVct = PokUtils.getAllLinkedEntities(avail, relator, entityType);
		for (int entityCount = 0; entityCount < entityVct.size(); entityCount++) {
			EntityItem ei = (EntityItem) entityVct.elementAt(entityCount);
			if (statusIsFinal(ei)) {
				for (int i=0; i<attrcodes.length; i++){
					setFlagValue(m_elist.getProfile(),attrcodes[i], getQueuedValueForItem(ei,attrcodes[i]), ei);
				}
			}
		}
		entityVct.clear();
	}
	/**
Delete 2011-10-20		39.200		IF			MODEL	ANNDATE	<=	"2010-03-01"			
Delete 2011-10-20		39.210		AND			MODEL	PDHDOMAIN	Not In	XCC_LIST			
Delete 2011-10-20		39.230		IF			MODEL	STATUS	=	"Final" (0020)			
Delete 2011-10-20		39.250		SET			MODEL				ADSABRSTATUS		&ADSFEED
Delete 2011-10-20		39.270		END	39.230								
Delete 2011-10-20		39.290		ELSE	39.200								
		40.000		IF		MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)			
		41.000		SET			MODEL				ADSABRSTATUS		&ADSFEED
		42.000		END	40.000								
Delete 2011-10-20		42.100		END	39.200								

	---------------------
Delete 2011-10-20		46.140		IF			MODEL	ANNDATE	<=	"2010-03-01"			
Delete 2011-10-20		46.160		AND			MODEL	PDHDOMAIN	Not In	XCC_LIST			
Delete 2011-10-20		46.170		IF			MODEL	STATUS	=	"Final" (0020)			
Delete 2011-10-20		46.180		SET			MODEL				ADSABRSTATUS		&ADSFEED
Delete 2011-10-20		46.190		END	46.170								
Delete 2011-10-20		46.200		ELSE	46.140								
						46.220		IF		MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)					
20130904b Change	Columns E, I, J, K	46.240		SetSinceFinal			MODEL	ADSABRSTATUS	=	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEED		
20130904b Add		46.242		SetSinceFinal			MODEL	ADSABRSTATUS	<>	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEEDFINALFIRST		
46.260		END	46.220						
Delete 2011-10-20		46.280		END	46.140								

	 * @param avail
	 * @param attrcodes
	 * @throws SQLException 
	 * @throws MiddlewareException 
	 * @throws MiddlewareRequestException 
	 */
	private void verifyModelFinalAndQueue(EntityItem avail, String[] attrcodes) throws MiddlewareRequestException, MiddlewareException, SQLException {
		Vector entityVct = PokUtils.getAllLinkedEntities(avail, "MODELAVAIL", "MODEL");
		for (int entityCount = 0; entityCount < entityVct.size(); entityCount++) {
			EntityItem mdlitem = (EntityItem) entityVct.elementAt(entityCount);
			//boolean isold = isOldData(mdlitem, "ANNDATE");
			//boolean inXcclist = domainInRuleList(mdlitem, "XCC_LIST");

			addDebug("verifyModelFinalAndQueue "+mdlitem.getKey());//+ " isold "+isold+" inXcclist "+inXcclist);
			//39.23		IF			MODEL	STATUS	=	"Final" (0020)
			if (statusIsFinal(mdlitem)) {
				//Delete 2011-10-20		39.200		IF			MODEL	ANNDATE	<=	"2010-03-01"			
				//Delete 2011-10-20		39.210		AND			MODEL	PDHDOMAIN	Not In	XCC_LIST		
				/*if(isold && !inXcclist){		
					//Delete 2011-10-20   39.25		SET			MODEL				ADSABRSTATUS		&ADSFEED
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", 
							getQueuedValueForItem(mdlitem,"ADSABRSTATUS"), mdlitem);
					//Delete 2011-10-20   39.27		END	39.23
				}else{
					//Delete 2011-10-20   39.29		ELSE	39.20	
					*/
					//40.00		IF		MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)	
					//41.00		SET			MODEL				ADSABRSTATUS		&ADSFEED
					for (int i=0; i<attrcodes.length; i++){
//						46.220		IF		MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)					
//								20130904b Change	Columns E, I, J, K	46.240		SetSinceFinal			MODEL	ADSABRSTATUS	=	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEED		
//								20130904b Add		46.242		SetSinceFinal			MODEL	ADSABRSTATUS	<>	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEEDFINALFIRST		
//										46.260		END	46.220		
						setSinceFirstFinal(mdlitem,attrcodes[i]);
					}
					// 46.240	SetSinceFinal			MODEL	RFCABRSTATUS	=	"Passed" (0030)	RFCABRSTATUS		&OIMSFEED
					// 46.242	SetSinceFinal			MODEL	RFCABRSTATUS	<>	"Passed" (0030)	RFCABRSTATUS		&OIMSFEED
		 	 	 	// 	MODEL	COFCAT	=	"Hardware(100)	 	 	 	 	 	 
		 	 	 	// 	OR	 	 	 	 	 	 	 	 	 
		 	 	 	// 	MODEL	MACHTYPEATR+MODELATR	IN	5313-HPO and 5372-IS5
					if (isHardwareOrHIPOModel(mdlitem)) {
						setRFCSinceFirstFinal(mdlitem, "RFCABRSTATUS");
					}					
					//42.00		END	40.00	
				//}
			}
			//Delete 2011-10-20		42.100		END	39.200		
		}
		entityVct.clear();
	}

	/**
	 * 	42.400	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)			
		42.420	R1.0	AND		SVCMODSVCSEO-u:	SVCMOD	STATUS	=	"Final" (0020)			
		42.440	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
		42.460	R1.0	END	42.400	
		---------------
		47.120	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)			
		47.140	R1.0	AND		SVCMODSVCSEO-u:	SVCMOD	STATUS	=	"Final" (0020)			
		47.160	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
		47.180	R1.0	END	47.120			
	 * @param avail
	 */
	private void verifySVCSEOFinalAndQueue(EntityItem avail) {
		Vector svcseoVct = PokUtils.getAllLinkedEntities(avail, "SVCSEOAVAIL", "SVCSEO");
		for (int entityCount = 0; entityCount < svcseoVct.size(); entityCount++) {
			EntityItem svcseo = (EntityItem) svcseoVct.elementAt(entityCount);
			//42.400	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)	
			//47.120	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)
			if (statusIsFinal(svcseo)) {
				Vector svcmodVct = PokUtils.getAllLinkedEntities(svcseo, "SVCMODSVCSEO", "SVCMOD");
				addDebug("verifySVCSEOFinalAndQueue "+svcseo.getKey()+ " svcmodVct "+svcmodVct.size());
				for(int i=0; i<svcmodVct.size();i++){
					EntityItem svcmod = (EntityItem)svcmodVct.elementAt(i);
					//42.420	R1.0	AND		SVCMODSVCSEO-u:	SVCMOD	STATUS	=	"Final" (0020)
					//47.140	R1.0	AND		SVCMODSVCSEO-u:	SVCMOD	STATUS	=	"Final" (0020)
					if (statusIsFinal(svcmod)) {
						//42.440	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
						//47.160	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
						setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", 
								getQueuedValueForItem(svcmod,"ADSABRSTATUS"), svcmod);
					}
				}
				svcmodVct.clear();
			}
			//42.460	R1.0	END	42.400	
			//47.180	R1.0	END	47.120
		}
		svcseoVct.clear();
	}
	
	/**
	 * 
		49.170	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
		49.172	R1.0	AND		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)		
		49.174	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
		49.176	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR
		49.178	R1.0	END	49.170
		----------------
		49.894	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
			R1.0	AND		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)		
		49.895	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
		49.896	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR
		49.897	R1.0	END	49.894			
	 * @param avail
	 */
	private void verifySVCSEORFRAndQueue(EntityItem avail) {
		Vector svcseoVct = PokUtils.getAllLinkedEntities(avail, "SVCSEOAVAIL", "SVCSEO");
		for (int entityCount = 0; entityCount < svcseoVct.size(); entityCount++) {
			EntityItem svcseo = (EntityItem) svcseoVct.elementAt(entityCount);
			//49.170	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)
			//49.894	R1.0	IF		SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)
			if (statusIsRFR(svcseo)) {
				Vector svcmodVct = PokUtils.getAllLinkedEntities(svcseo, "SVCMODSVCSEO", "SVCMOD");
				addDebug("verifySVCSEORFRAndQueue "+svcseo.getKey()+ " svcmodVct "+svcmodVct.size());
				for(int i=0; i<svcmodVct.size();i++){
					//49.172	R1.0	AND		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)		
					//49.174	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
					//R1.0	AND		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)		
					//49.895	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
					doRFR_ADSXML((EntityItem)svcmodVct.elementAt(i));
				}
				svcmodVct.clear();
			}
			//49.178	R1.0	END	49.170	
			//49.897	R1.0	END	49.894	
		}
		svcseoVct.clear();
	}
	/**********************************
	 * Note the ABR is only called when
	 * DATAQUALITY transitions from 'Draft to Ready for Review',
	 *   'Change Request to Ready for Review' and from 'Ready for Review to Final'
	 *  
1.0	AVAIL		Root									
2.0	ANNOUNCEMENT		AVAILANNA									
3.0	IF		AVAILANNTYPE	=	"RFA" (RFA)							
4.0			CountOf	=	1			E	E	E	AVAIL must be in an Announcement	{LD: AVAILANNTYPE} {AVAILANNTYPE} must be in only one {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
5.0	ELSE											
6.0			CountOf	=	0			E	E	E	AVAIL must NOT be in an Announcement	must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
7.0	END	3										

8.0	AVAIL	A	Root									
Change column G	9.00	WHEN		AVAILTYPE	=	"Planned Availability" or "MES Planned Availaility"				
doPlannedAvailChecks()					
56.0	END	9										

57.0	AVAIL	B	Root									
58.0	WHEN		AVAILTYPE	=	"First Order"
doFirstOrderAvailChecks()						
105.0	END	58	

106.0	AVAIL	C	Root									
Change column G	107.00	WHEN		AVAILTYPE	=	"Last Order" or "MES Last Order"		
doLastOrderAvailChecks()						
153.0	END	107											

154.0	AVAIL	E	Root									
155.0	WHEN		AVAILTYPE	=	"End of Marketing" (200)
doEOMAvailChecks()
201.0	END	155	

202	AVAIL	D	Root									
203	WHEN		AVAILTYPE	=	"End of Service" (151)						This is just like "Last Order"	
doEOSAvailChecks()
248	END	203
	 */
	protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
	{
		String availType = PokUtils.getAttributeFlagValue(rootEntity, "AVAILTYPE");
		String availAnntypeFlag = PokUtils.getAttributeFlagValue(rootEntity, "AVAILANNTYPE");

		Vector annVct = PokUtils.getAllLinkedEntities(rootEntity, "AVAILANNA", "ANNOUNCEMENT");
		addDebug(rootEntity.getKey()+" availtype "+availType+" availAnntypeFlag "+availAnntypeFlag+" annVct "+annVct.size());
		boolean elevateMsg = false;										

		//3.0	IF		AVAILANNTYPE	=	"RFA" (RFA)	OR AVAILANNTYPE	=	"EPIC" (EPIC) 						
		//4.0			Count of	=	1			E	E	E	AVAIL must be in an Announcement	
		//{LD: AVAILANNTYPE} {AVAILANNTYPE} must be in only one {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
		if (availAnntypeFlag==null){
			availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
		}
		// 1674783: DQ ABR update for an AVAILABRSTATUS
		if (AVAILANNTYPE_RFA.equals(availAnntypeFlag) || AVAILANNTYPE_EPIC.equals(availAnntypeFlag)){
			if (annVct.size()!=1){
				//MUST_BE_IN_ONLY1_ERR = {0} must be in only one {1}
				String errcode = "MUST_BE_IN_ONLY1_ERR";
				if (annVct.size()==0){
					//MUST_BE_IN_ERR = {0} must be in an {1}
					errcode = "MUST_BE_IN_ERR";
				}
			
				args[0] = "";
				args[1] = m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
				createMessage(CHECKLEVEL_E,errcode,args);
				annVct.clear();
				return;
			}
			//IF	ANNSTATUS	=	Final (0020)	Elevate Msg		E	E	E	If the Announcement is "Final", then the AVAIL must meet these rules	
			for (int i=0; i< annVct.size(); i++){
				EntityItem annItem = (EntityItem)annVct.elementAt(i);
				String annStatus = PokUtils.getAttributeFlagValue(annItem, "ANNSTATUS");
				elevateMsg = elevateMsg || STATUS_FINAL.equals(annStatus);
				addDebug(annItem.getKey()+" annstatus "+annStatus+" elevatemsg "+elevateMsg);
			}

			for (int i=0; i< annVct.size(); i++){
				EntityItem annItem = (EntityItem)annVct.elementAt(i);
				// Change column G	9.00	WHEN		AVAILTYPE	=	"Planned Availability" or "MES Planned Availaility"	
				if (PLANNEDAVAIL.equals(availType) || MESPLANNEDAVAIL.equals(availType)){
					doPlannedAvailChecks(availType, rootEntity, annItem, elevateMsg, statusFlag);
				}else if (FIRSTORDERAVAIL.equals(availType)){
					doFirstOrderAvailChecks(rootEntity, annItem, elevateMsg, statusFlag);
				}else if (LASTORDERAVAIL.equals(availType) || MESLASTORDERAVAIL.equals(availType)){ // Change column G	107.00	WHEN		AVAILTYPE	=	"Last Order" or "MES Last Order"
					doLastOrderAvailChecks(availType, rootEntity, annItem, elevateMsg, statusFlag);
				}else if (EOSAVAIL.equals(availType)){
					doEOSAvailChecks(rootEntity, annItem, elevateMsg, statusFlag);
				}else if (EOMAVAIL.equals(availType)){
					doEOMAvailChecks(rootEntity, annItem, elevateMsg, statusFlag);
				}
			}
		}else{	
			//5.0	ELSE											
			//6.0			Count of	=	0			E	E	E	AVAIL must NOT be in an Announcement	
			//must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			if(!checkAvailAnnType(rootEntity, annVct, CHECKLEVEL_E)){
				annVct.clear();
				return;
			}
			// there will not be an announcement if AVAILANNTYPE !=	"RFA"	  
			// Change column G	9.00	WHEN		AVAILTYPE	=	"Planned Availability" or "MES Planned Availaility"	
			if (PLANNEDAVAIL.equals(availType) || MESPLANNEDAVAIL.equals(availType)){
				doPlannedAvailChecks(availType, rootEntity, null, elevateMsg, statusFlag);
			}else if (FIRSTORDERAVAIL.equals(availType)){
				doFirstOrderAvailChecks(rootEntity, null, elevateMsg, statusFlag);
			}else if (LASTORDERAVAIL.equals(availType) || MESLASTORDERAVAIL.equals(availType)){ // Change column G	107.00	WHEN		AVAILTYPE	=	"Last Order" or "MES Last Order"
				doLastOrderAvailChecks(availType, rootEntity, null, elevateMsg, statusFlag);
			}else if (EOSAVAIL.equals(availType)){
				doEOSAvailChecks(rootEntity, null, elevateMsg, statusFlag);
			}else if (EOMAVAIL.equals(availType)){
				doEOMAvailChecks(rootEntity, null, elevateMsg, statusFlag);
			}
		}
		//7.0	END	3.0
	}

	/**************
	 * do checks when Avail is plannedavail
	 * @param rootEntity
	 * @param annItem
	 * @param elevateMsg
	 * @param statusFlag
	 * @throws SQLException
	 * @throws MiddlewareException

35	SWPRODSTRUCT		SWPRODSTRUCTAVAIL-u		No Dates to Check							
del36	IPSCSTRUC		IPSCSTRUCAVAIL-u									
37											nothing to check yet on IPSCSTRUC	
del38	IPSCFEAT		IPSCSTRUCAVAIL-u: IPSCSTRUC-d									
del39			FIRSTANNDATE	<=	A: AVAIL			W	W	E		must not be earlier than {LD: IPSCFEAT} {NDN: IPSCSTRUC} {LD: FIRSTANNDATE} {FIRSTDATE}
40	LSEO		LSEOAVAIL-u									
41			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
42			LSEOPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
43			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
44	LSEOBUNDLE		LSEOBUNDLEAVAIL-u									
45			BUNDLPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
46			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
47			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: STATUS} {STATUS}
48	FCTRANSACTION		FEATURETRNAVAIL-u									
49			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: ANNDATE} {ANNDATE}
50			GENAVAILDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: GENAVAILDATE} {GENAVAILDATE}
51			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: STATUS} {STATUS}
52	MODELCONVERT		MODELCONVERTAVAIL-u									
53			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
54			GENAVAILDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
55			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
56	END	9	

8.0	AVAIL	A	Root									
9.0	WHEN		AVAILTYPE	=	"Planned Availability"							
10.0			EFFECTIVEDATE									
11.0			COUNTRYLIST									
12.0	ANNOUNCEMENT		AVAILANNA									
13.0	WHEN		ANNTYPE	=	"New" (19)							
14.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		must not be earlier than the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
15.0			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
16.0	IF		ANNSTATUS	=	Final (0020)	Elevate Msg		E	E	E	If the Announcement is "Final", then the AVAIL must meet these rules	
17.0	END	13										
18.0	ANNOUNCEMENT		AVAILANNA									
19.0	WHEN		ANNTYPE	<>	"New" (19)							
20.0			CountOf	=	0			E	E	E		must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
21.0	END	19										
22.0	MODEL		MODELAVAIL-u									
23.0			GENAVAILDATE	<=	A: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
24.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: ANNDATE} {ANNDATE}
25.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
26.0	SVCMOD		SVCMODAVAIL-u									
27.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: SVCMOD} {NDN: SVCMOD} {LD: ANNDATE} {ANNDATE}
28.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
29.0	PRODSTRUCT		OOFAVAIL-u									
30.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
30.20	END	29.00
31.0			GENAVAILDATE	<=	A: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
32.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
33.0	FEATURE		OOFAVAIL-u: PRODSTRUCT-u									
34.0			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: FEATURE} {NDN: FEATURE} 
35.0	SWPRODSTRUCT		SWPRODSTRUCTAVAIL-u		No Dates to Check							
35.2			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
del36.0	IPSCSTRUC		IPSCSTRUCAVAIL-u									
del37.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: IPSCSTRUC} {NDN: IPSCSTRUC} {LD: STATUS} {STATUS}
del38.0	IPSCFEAT		IPSCSTRUCAVAIL-u: IPSCSTRUC-d									
del39.0			FIRSTANNDATE	<=	A: AVAIL			W	W	E		must not be earlier than {LD: IPSCFEAT} {NDN: IPSCSTRUC} {LD: FIRSTANNDATE} {FIRSTDATE}
40.0	LSEO		LSEOAVAIL-u									
41.0			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
42.0			LSEOPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
43.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
44.0	LSEOBUNDLE		LSEOBUNDLEAVAIL-u									
45.0			BUNDLPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
46.0			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
47.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: STATUS} {STATUS}
Delete 20110318 48.0	FCTRANSACTION		FEATURETRNAVAIL-u									
Delete 20110318 49.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: ANNDATE} {ANNDATE}
Delete 20110318 50.0			GENAVAILDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: GENAVAILDATE} {GENAVAILDATE}
Delete 20110318 51.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: STATUS} {STATUS}
52.0	MODELCONVERT		MODELCONVERTAVAIL-u									
53.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
54.0			GENAVAILDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
55.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
56.0	END	9										

	 */
	private void doPlannedAvailChecks(String availType, EntityItem rootEntity, EntityItem annItem, boolean elevateMsg, String statusFlag) 
	throws SQLException, MiddlewareException 
	{
		if (annItem!=null){
			String anntype = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
			addDebug("doPlannedAvailChecks "+annItem.getKey()+" anntype "+anntype);

			//18.0	ANNOUNCEMENT		AVAILANNA									
			//19.0	WHEN		ANNTYPE	<>	"New" (19)							
			//20.0			Count of	=	0			E	E	E		must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			if (!ANNTYPE_NEW.equals(anntype)){
				//MUST_NOT_BE_IN_THIS_ERR= {0} must not be in this {1} {2}	  
				args[0]="";
				args[1]= getLD_Value(annItem, "ANNTYPE");
				args[2]=getLD_NDN(annItem);
				createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_THIS_ERR",args);
				return;
			}
			//21.0	END	19	
		}else{
			addDebug("doPlannedAvailChecks no ANNOUNCEMENT");
		}

		int checklvl = getCheck_W_W_E(statusFlag);
		if(elevateMsg){
			checklvl=CHECKLEVEL_E;
		}
		if (PLANNEDAVAIL.equals(availType)) {
			addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Planned Avail Checks:");
		} else if (MESPLANNEDAVAIL.equals(availType)) {
			addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" MES Planned Avail Checks:");
		}
		
		ArrayList availCtry = new ArrayList();
		getCountriesAsList(rootEntity, availCtry, checklvl);

		if (annItem!=null){
			//13.0	WHEN		ANNTYPE	=	"New" (19)							
			//14.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E		must not be earlier than the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			checkDateStatus(rootEntity, annItem, "ANNDATE", false,checklvl,checklvl,DATE_GR_EQ);
			//15.0			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			checkCountryList(annItem, availCtry, checklvl);								
			//17.0	END	13	
		}

		checkNewOfferings(rootEntity, availCtry, checklvl);
		//56.0	END	9	

		availCtry.clear();
	}
	/**
	 * check plannedavail and firstorder avail offerings 
	 * @param rootEntity
	 * @param availCtry
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 	
	 */
	private void checkNewOfferings(EntityItem rootEntity, ArrayList availCtry, int checklvl) 
	throws SQLException, MiddlewareException
	{
		//22.0,71.0	MODEL		MODELAVAIL-u									
		EntityGroup eGrp = m_elist.getEntityGroup("MODEL");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get models thru avail
		Vector vct = PokUtils.getAllLinkedEntities(rootEntity, "MODELAVAIL", "MODEL");
		for (int i=0; i< vct.size(); i++){
			EntityItem mdlItem = (EntityItem)vct.elementAt(i);
			//23.0			GENAVAILDATE	<=	A: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
			//24.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: ANNDATE} {ANNDATE}
			//25.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
			//Delete 20110222	72.0			GENAVAILDATE	<=	B: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
			//73.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: ANNDATE} {ANNDATE}
			//74.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
			//checkDateStatus(rootEntity, mdlItem, new String[]{"ANNDATE","GENAVAILDATE"}, true,
			checkDateStatus(rootEntity, mdlItem, new String[]{"ANNDATE"}, true,
					new int[]{CHECKLEVEL_E,checklvl},CHECKLEVEL_E,DATE_GR_EQ);
		}
		vct.clear();

		//26.0,75.0	SVCMOD		SVCMODAVAIL-u									
		eGrp = m_elist.getEntityGroup("SVCMOD");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get models thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "SVCMODAVAIL", "SVCMOD");
		for (int i=0; i< vct.size(); i++){
			EntityItem svcmodItem = (EntityItem)vct.elementAt(i);
			//27.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: SVCMOD} {NDN: SVCMOD} {LD: ANNDATE} {ANNDATE}
			//28.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
			//76.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: SVCMOD} {NDN: SVCMOD} {LD: ANNDATE} {ANNDATE}
			//77.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
			checkDateStatus(rootEntity, svcmodItem, "ANNDATE", true,checklvl,CHECKLEVEL_E,DATE_GR_EQ);
		}
		vct.clear();

		//29.0,78.0	PRODSTRUCT		OOFAVAIL-u	
		eGrp = m_elist.getEntityGroup("PRODSTRUCT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get ps thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "OOFAVAIL", "PRODSTRUCT");
		for (int i=0; i< vct.size(); i++){
			EntityItem psItem = (EntityItem)vct.elementAt(i);
			//30.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
			//31.0			GENAVAILDATE	<=	A: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
			//32.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
			//79.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
			//Delete 20110222 80.0			GENAVAILDATE	<=	B: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
			//81.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
			//checkDateStatus(rootEntity, psItem, new String[]{"ANNDATE","GENAVAILDATE"}, true,CHECKLEVEL_E,
			checkDateStatus(rootEntity, psItem, new String[]{"ANNDATE"}, true,CHECKLEVEL_E,
					CHECKLEVEL_E,DATE_GR_EQ);
		};
		vct.clear();
		//33.0,82.0	FEATURE		OOFAVAIL-u: PRODSTRUCT-u	
		eGrp = m_elist.getEntityGroup("FEATURE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");	
		vct = getFeatFromAvailRelator(m_elist.getEntityGroup("OOFAVAIL"), "PRODSTRUCT", "FEATURE");
		for (int i=0; i< vct.size(); i++){
			EntityItem featItem = (EntityItem)vct.elementAt(i);
			//34.0			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: FEATURE} {NDN: FEATURE} 
			//83.0			COUNTRYLIST	Contains	B: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: FEATURE} {NDN: FEATURE} 
			checkCountryList(featItem, availCtry, checklvl);
		}
		vct.clear();

		//35.0,84.0	SWPRODSTRUCT		SWPRODSTRUCTAVAIL-u		No Dates to Check							
		eGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get ps thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");
		for (int i=0; i< vct.size(); i++){
			EntityItem swpsItem = (EntityItem)vct.elementAt(i);
			//35.2,84.2			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
			checkStatusVsDQ(swpsItem, "STATUS",rootEntity,CHECKLEVEL_E);
		}
		vct.clear();

		//36.0,85.0	IPSCSTRUC		IPSCSTRUCAVAIL-u									
		/*eGrp = m_elist.getEntityGroup("IPSCSTRUC");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get ps thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "IPSCSTRUCAVAIL", "IPSCSTRUC");
		for (int i=0; i< vct.size(); i++){
			EntityItem psItem = (EntityItem)vct.elementAt(i);
			//37.0,86.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: IPSCSTRUC} {NDN: IPSCSTRUC} {LD: STATUS} {STATUS}
			checkStatusVsDQ(psItem, "STATUS",rootEntity,CHECKLEVEL_E);
		}*/

		//38.0,87.0	IPSCFEAT		IPSCSTRUCAVAIL-u: IPSCSTRUC-d									
		/*eGrp = m_elist.getEntityGroup("IPSCFEAT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		vct = getFeatFromAvailRelator(m_elist.getEntityGroup("IPSCSTRUCAVAIL"), "IPSCSTRUC", "IPSCFEAT");
		for (int i=0; i< vct.size(); i++){
			EntityItem ipscfeatItem = (EntityItem)vct.elementAt(i);
			//39.0			FIRSTANNDATE	<=	A: AVAIL			W	W	E		must not be earlier than {LD: IPSCFEAT} {NDN: IPSCFEAT} {LD: FIRSTANNDATE} {FIRSTDATE}
			//88.0			FIRSTANNDATE	<=	A: AVAIL			W	W	E		must not be earlier than {LD: IPSCFEAT} {NDN: IPSCFEAT} {LD: FIRSTANNDATE} {FIRSTDATE}
			checkDateStatus(rootEntity, ipscfeatItem, "FIRSTANNDATE", false,checklvl,CHECKLEVEL_E,DATE_GR_EQ);
		}
		vct.clear();
		 */

		//40.0,89.0	LSEO		LSEOAVAIL-u			
		eGrp = m_elist.getEntityGroup("LSEO");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem lseoItem = eGrp.getEntityItem(i);
			//41.0			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
			//90.0			COUNTRYLIST	Contains	B: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
			checkCountryList(lseoItem, availCtry, checklvl);
			//42.0			LSEOPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
			//43.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
			//91.0			LSEOPUBDATEMTRGT	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
			//92.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
			checkDateStatus(rootEntity, lseoItem, "LSEOPUBDATEMTRGT", true,checklvl,CHECKLEVEL_E,DATE_GR_EQ);
		}

		//44.0,93.0	LSEOBUNDLE		LSEOBUNDLEAVAIL-u	
		eGrp = m_elist.getEntityGroup("LSEOBUNDLE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem lseobdlItem = eGrp.getEntityItem(i);
			//46.0			COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
			//95.0			COUNTRYLIST	Contains	B: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
			checkCountryList(lseobdlItem, availCtry, checklvl);
			//45.0			BUNDLPUBDATEMTRGT	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
			//47.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: STATUS} {STATUS}
			//94.0			BUNDLPUBDATEMTRGT	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		muat not be earlier than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
			//96.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNCLE} {NDN: LSEOBUNCLE} {LD: STATUS} {STATUS}
			checkDateStatus(rootEntity, lseobdlItem, "BUNDLPUBDATEMTRGT", true,checklvl,CHECKLEVEL_E,DATE_GR_EQ);
		}

		//Delete 20110318 48.0,97.0	FCTRANSACTION		FEATURETRNAVAIL-u		
		/*eGrp = m_elist.getEntityGroup("FCTRANSACTION");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem fctransItem = eGrp.getEntityItem(i);
			//49.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: ANNDATE} {ANNDATE}
			//50.0			GENAVAILDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: GENAVAILDATE} {GENAVAILDATE}
			//51.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: STATUS} {STATUS}
			//98.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: ANNDATE} {ANNDATE}
			//Delete 20110222 99.0			GENAVAILDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: GENAVAILDATE} {GENAVAILDATE}
			//100.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: STATUS} {STATUS}
			//checkDateStatus(rootEntity, fctransItem, new String[]{"ANNDATE","GENAVAILDATE"}, true,checklvl,
			checkDateStatus(rootEntity, fctransItem, new String[]{"ANNDATE"}, true,checklvl,
					CHECKLEVEL_E,DATE_GR_EQ);
		}*/

		//52.0,101.0	MODELCONVERT		MODELCONVERTAVAIL-u	
		eGrp = m_elist.getEntityGroup("MODELCONVERT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem mdlcvtItem = eGrp.getEntityItem(i);
			//53.0			ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
			//54.0			GENAVAILDATE	<=	A: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
			//55.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
			//102.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
			//Delete 20110222 103.0			GENAVAILDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
			//104.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
			//checkDateStatus(rootEntity, mdlcvtItem, new String[]{"ANNDATE","GENAVAILDATE"}, true,checklvl,
			checkDateStatus(rootEntity, mdlcvtItem, new String[]{"ANNDATE"}, true,checklvl,
					CHECKLEVEL_E,DATE_GR_EQ);
		}
		//56.0	END	9
		//105.0	END	58
		
		//	MAINTPRODSTRUCT		MAINTMFAVAIL-u									
		eGrp = m_elist.getEntityGroup("MAINTPRODSTRUCT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get models thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "MAINTMFAVAIL", "MAINTPRODSTRUCT");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem mpsItem = (EntityItem)vct.elementAt(i);
			//35.2,84.2			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
			checkStatusVsDQ(mpsItem, "STATUS",rootEntity,CHECKLEVEL_E);
		}
	}

	/**
	 * find the features from the ps-avail relator
	 * 	EntityType		EntityFrom	EntityTo
		SWPRODSTRUCT	SWFEATURE	MODEL
		PRODSTRUCT		FEATURE		MODEL
		IPSCSTRUC		SVCMOD		IPSCFEAT
	 * @param srcGrp
	 * @param linkType
	 * @param destType
	 * @return
	 */
	private static Vector getFeatFromAvailRelator(EntityGroup srcGrp, String linkType, String destType)
	{
		// find entities thru 'linkType' relators
		Vector destVct = new Vector(1);
		if (srcGrp != null) {
			for(int ie=0; ie<srcGrp.getEntityItemCount();ie++)
			{
				EntityItem entityItem = srcGrp.getEntityItem(ie);
				getFeatFromAvailRelator(entityItem, linkType, destType, destVct);
			}
		}

		return destVct;
	}
	private static void getFeatFromAvailRelator(EntityItem entityItem, String linkType, String destType,
			Vector destVct)
	{
		if (entityItem==null) {
			return; 
		}
		// see if this relator is used as an uplink
		for (int ui=0; ui<entityItem.getUpLinkCount(); ui++)
		{
			EANEntity entityLink = entityItem.getUpLink(ui);
			if (entityLink.getEntityType().equals(linkType))
			{
				// check for destination entity as an uplink
				for (int i=0; i<entityLink.getUpLinkCount(); i++)
				{
					EANEntity entity = entityLink.getUpLink(i);
					if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
						destVct.addElement(entity); }
				}

				// check for destination entity as a downlink
				for (int i=0; i<entityLink.getDownLinkCount(); i++)
				{
					EANEntity entity = entityLink.getDownLink(i);
					if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
						destVct.addElement(entity); }
				}
			}
		}

		// see if this relator is used as a downlink
		for (int ui=0; ui<entityItem.getDownLinkCount(); ui++)
		{
			EANEntity entityLink = entityItem.getDownLink(ui);
			if (entityLink.getEntityType().equals(linkType))
			{
				// check for destination entity as a downlink
				for (int i=0; i<entityLink.getDownLinkCount(); i++)
				{
					EANEntity entity = entityLink.getDownLink(i);
					if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
						destVct.addElement(entity); }
				}
				// check for destination entity as an uplink
				for (int i=0; i<entityLink.getUpLinkCount(); i++)
				{
					EANEntity entity = entityLink.getUpLink(i);
					if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
						destVct.addElement(entity); }
				}
			}
		}
	}

	/*****************
	 * Do checks when Avail is FirstOrder
	 * @param rootEntity
	 * @param annItem
	 * @param elevateMsg
	 * @param statusFlag
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 
57.0	AVAIL	B	Root									
58.0	WHEN		AVAILTYPE	=	"First Order"							
59.0			EFFECTIVEDATE									
60.0			COUNTRYLIST									
61.0	ANNOUNCEMENT		AVAILANNA									
62.0	WHEN		ANNTYPE	=	"New" (19)							
63.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE	Yes	W	W	E		must not be earlier than the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
64.0			COUNTRYLIST	Contains	B: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
65.0	IF		ANNSTATUS	=	Final (0020)	Elevate Msg		E	E	E	If the Announcement is "Final", then the AVAIL must meet these rules	
66.0	END	62										
67.0	ANNOUNCEMENT		AVAILANNA									
68.0	WHEN		ANNTYPE	<>	"New" (19)							
69.0			CountOf	=	0			E	E	E		must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
70.0	END	68										
71.0	MODEL		MODELAVAIL-u									
Delete 20110222 72.0			GENAVAILDATE	<=	B: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
73.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: ANNDATE} {ANNDATE}
74.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
75.0	SVCMOD		SVCMODAVAIL-u									
76.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: SVCMOD} {NDN: SVCMOD} {LD: ANNDATE} {ANNDATE}
77.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
78.0	PRODSTRUCT		OOFAVAIL-u									
79.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
Delete 20110222 80.0			GENAVAILDATE	<=	B: AVAIL	EFFECTIVEDATE		E	E	E		must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
81.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
82.0	FEATURE		OOFAVAIL-u: PRODSTRUCT-u									
83.0			COUNTRYLIST	Contains	B: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: FEATURE} {NDN: FEATURE} 
84.0	SWPRODSTRUCT		SWPRODSTRUCTAVAIL-u		No Dates to Check							
84.2			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
del85.0	IPSCSTRUC		IPSCSTRUCAVAIL-u									
del86.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: IPSCSTRUC} {NDN: IPSCSTRUC} {LD: STATUS} {STATUS}
del87.0	IPSCFEAT		IPSCSTRUCAVAIL-u: IPSCSTRUC-d									
del88.0			FIRSTANNDATE	<=	B: AVAIL			W	W	E		must not be earlier than {LD: IPSCFEAT} {NDN: IPSCFEAT} {LD: FIRSTANNDATE} {FIRSTDATE}
89.0	LSEO		LSEOAVAIL-u									
90.0			COUNTRYLIST	Contains	B: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
91.0			LSEOPUBDATEMTRGT	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
92.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
93.0	LSEOBUNDLE		LSEOBUNDLEAVAIL-u									
94.0			BUNDLPUBDATEMTRGT	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		muat not be earlier than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT} {BUNDLPUBDATEMTRGT}
95.0			COUNTRYLIST	Contains	B: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
96.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNCLE} {NDN: LSEOBUNCLE} {LD: STATUS} {STATUS}
Delete 20110318 97.0	FCTRANSACTION		FEATURETRNAVAIL-u									
Delete 20110318 98.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: ANNDATE} {ANNDATE}
Delete 20110222 99.0			GENAVAILDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: GENAVAILDATE} {GENAVAILDATE}
Delete 20110318 100.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: STATUS} {STATUS}
101.0	MODELCONVERT		MODELCONVERTAVAIL-u									
102.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: ANNDATE} {ANNDATE}
Delete 20110222 103.0			GENAVAILDATE	<=	B: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: GENAVAILDATE} {GENAVAILDATE}
104.0			STATUS	=>	B: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
105.0	END	58												
	 */
	private void doFirstOrderAvailChecks(EntityItem rootEntity, EntityItem annItem, boolean elevateMsg, String statusFlag) 
	throws SQLException, MiddlewareException 
	{
		if (annItem!=null){
			String anntype = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
			addDebug("doFirstOrderAvailChecks "+annItem.getKey()+" anntype "+anntype);
			//68.0	WHEN		ANNTYPE	<>	"New" (19)							
			//69.0			Count of	=	0			E	E	E		must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}	
			if (!ANNTYPE_NEW.equals(anntype)){
				//MUST_NOT_BE_IN_THIS_ERR= {0} must not be in this {1} {2}	
				args[0]="";
				args[1]= getLD_Value(annItem, "ANNTYPE");
				args[2]=getLD_NDN(annItem);
				createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_THIS_ERR",args);
				return;
			}
			//70.0	END	68	
		}else{
			addDebug("doFirstOrderAvailChecks  no ANNOUNCEMENT");
		}

		int checklvl = getCheck_W_W_E(statusFlag);
		if(elevateMsg){
			checklvl=CHECKLEVEL_E;
		}

		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" First Order Avail Checks:");
		ArrayList availCtry = new ArrayList();
		getCountriesAsList(rootEntity, availCtry, checklvl);

		if (annItem!=null){
			//62.0	WHEN		ANNTYPE	=	"New" (19)							
			//63.0			ANNDATE	<=	B: AVAIL	EFFECTIVEDATE	Yes	W	W	E		must not be earlier than the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			checkDateStatus(rootEntity, annItem, "ANNDATE", false,checklvl,checklvl,DATE_GR_EQ);
			//64.0			COUNTRYLIST	Contains	B: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			checkCountryList(annItem, availCtry, checklvl);									
			//66.0	END	62	
		}

		checkNewOfferings(rootEntity, availCtry, checklvl);
		//105.0	END	58	
	}
	/*********************
	 * Do checks when Avail is LastOrder
	 * @param rootEntity
	 * @param annItem
	 * @param elevateMsg
	 * @param statusFlag
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 
106.0	AVAIL	C	Root									
107.0	WHEN		AVAILTYPE	=	"Last Order"							
108.0			EFFECTIVEDATE									
109.0			COUNTRYLIST									
110.0	ANNOUNCEMENT		AVAILANNA									
111.0	WHEN		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)							
112.0			ANNDATE	<=	C: AVAIL	EFFECTIVEDATE	Yes	W	W	E		must not be earlier than the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
113.0			COUNTRYLIST	Contains	C: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
114.0	IF		ANNSTATUS	=	Final (0020)	Elevate Msg		E	E	E	If the Announcement is "Final", then the AVAIL must meet these rules	
115.0	END	111										
116.0	ANNOUNCEMENT		AVAILANNA									
117.0	WHEN		ANNTYPE	<>	"End Of Life - Withdrawal from mktg" (14)							
118.0			CountOf	=	0			E	E	E		must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
119.0	END	117										
120.0	MODEL		MODELAVAIL-u									
121.0			WTHDRWEFFCTVDATE	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: MODEL} {NDN: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
122.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
123.0	SVCMOD		SVCMODAVAIL-u									
124.0			WITHDRAWDATE	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SVCMOD} {NDN: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
125.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
126.0	PRODSTRUCT		OOFAVAIL-u									
127.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
128.0			WTHDRWEFFCTVDATE	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
129.0	FEATURE		OOFAVAIL-u: PRODSTRUCT-u									
130.0			WITHDRAWDATEEFF_T	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: FEATURE} {NDN: PRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
131.0			COUNTRYLIST	Contains	C: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: FEATURE} {NDN: FEATURE} 
132.0	SWPRODSTRUCT		SWPRODSTRUCTAVAIL-u		No Dates to Check							
132.2			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
133.0	SWFEATURE		SWPRODSTRUCTAVAIL-u: SWPRODSTRUCT-u									
134.0			WITHDRAWDATEEFF_T	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SWFEATURE} {NDN:SWPRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
del135.0	IPSCSTRUC		IPSCSTRUCAVAIL-u									
del136.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: IPSCSTRUC} {NDN: IPSCSTRUC} {LD: STATUS} {STATUS}
del137.0	IPSCFEAT		IPSCSTRUCAVAIL-u: IPSCSTRUC-d									
del138.0			WITHDRAWDATEEFF_T	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: IPSCFEAT} {NDN:IPSCFEAT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
139.0	LSEO		LSEOAVAIL-u									
140.0			COUNTRYLIST	Contains	C: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
141.0			LSEOUNPUBDATEMTRGT	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
142.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
143.0	LSEOBUNCLE		LSEOBUNDLEAVAIL-u									
144.0			BUNDLUNPUBDATEMTRGT	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
145.0			COUNTRYLIST	Contains	C: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
146.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNCLE} {NDN: LSEOBUNCLE} {LD: STATUS} {STATUS}
Delete 20110318 147.0	FCTRANSACTION		FEATURETRNAVAIL-u									
Delete 20110318 148.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: STATUS} {STATUS}
Delete 20110318 149.0			WTHDRWEFFCTVDATE	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
150.0	MODELCONVERT		MODELCONVERTAVAIL-u									
151.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
152.0			WTHDRWEFFCTVDATE	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
153.0	END	107										
	 */
	private void doLastOrderAvailChecks(String availType, EntityItem rootEntity, EntityItem annItem, boolean elevateMsg, String statusFlag) 
	throws SQLException, MiddlewareException
	{
		if (annItem!=null){
			String anntype = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
			addDebug("doLastOrderAvailChecks "+annItem.getKey()+" anntype "+anntype);
			//116.0	ANNOUNCEMENT		AVAILANNA									
			//117.0	WHEN		ANNTYPE	<>	"End Of Life - Withdrawal from mktg" (14)							
			//118.0			Count of	=	0			E	E	E		must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			if (!ANNTYPE_EOL.equals(anntype)){
				//MUST_NOT_BE_IN_THIS_ERR= {0} must not be in this {1} {2}	  
				args[0]="";
				args[1]= getLD_Value(annItem, "ANNTYPE");
				args[2]=getLD_NDN(annItem);
				createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_THIS_ERR",args);
				return;
			}
			//119.0	END	117
		}else{
			addDebug("doLastOrderAvailChecks no ANNOUNCEMENT");
		}

		int checklvl = getCheck_W_W_E(statusFlag);
		if(elevateMsg){
			//114.0	IF		ANNSTATUS	=	Final (0020)	Elevate Msg		E	E	E	If the Announcement is "Final", then the AVAIL must meet these rules
			checklvl=CHECKLEVEL_E;
		}
		if (LASTORDERAVAIL.equals(availType)) {
			addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Last Order Avail Checks:");
		} else if (MESLASTORDERAVAIL.equals(availType)) {
			addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" MES Last Order Avail Checks:");
		}		

		ArrayList availCtry = new ArrayList();
		getCountriesAsList(rootEntity, availCtry, checklvl);

		if (annItem!=null){
			//110.0	ANNOUNCEMENT		AVAILANNA									
			//111.0	WHEN		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)							
			//112.0			ANNDATE	<=	C: AVAIL	EFFECTIVEDATE	Yes	W	W	E		must not be earlier than the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			checkDateStatus(rootEntity, annItem, "ANNDATE", false,checklvl,checklvl,DATE_GR_EQ);
			//113.0			COUNTRYLIST	Contains	C: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			checkCountryList(annItem, availCtry, checklvl);									
			//115.0	END	111		
		}

		checkEOLOfferings(rootEntity, checklvl, availCtry);
		//153.0	END	107
	}

	/************
	 * 
202.0	AVAIL	D	Root									
203.0	WHEN		AVAILTYPE	=	"End of Service" (151)						This is just like "Last Order"	
204.0			EFFECTIVEDATE									
205.0			COUNTRYLIST									
206.0	ANNOUNCEMENT		AVAILANNA									
207.0	WHEN		ANNTYPE	=	"End Of Life - Discontinuance of service" (13)						This is just like "End Of Life - Withdrawal from mktg" (14)	
208.0			ANNDATE	<=	D: AVAIL	EFFECTIVEDATE	Yes	W	W	E		must not be earlier than the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
209.0			COUNTRYLIST	Contains	D: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
210.0	IF		ANNSTATUS	=	Final (0020)	Elevate Msg		E	E	E	If the Announcement is "Final", then the AVAIL must meet these rules	
211.0	END	207										
212.0	ANNOUNCEMENT		AVAILANNA									
213.0	WHEN		ANNTYPE	<>	"End Of Life - Discontinuance of service" (13)						This is just like "End Of Life - Withdrawal from mktg" (14)	
214.0			CountOf	=	0			E	E	E		must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
215.0	END	213										
216.0	MODEL		MODELAVAIL-u									
217.0			WTHDRWEFFCTVDATE	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
218.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
219.0	SVCMOD		SVCMODAVAIL-u									
delete 220.0			WITHDRAWDATE	=>	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SVCMOD} {NDN: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
delete 221.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
Add	221.2			CountOf	=	0			E	E	E		must not be in this {LD: AVAILTYPE} {LD: AVAIL} {NDN: AVAIL}
222.0	PRODSTRUCT		OOFAVAIL-u									
223.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
224.0			WTHDRWEFFCTVDATE	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
225.0	FEATURE		OOFAVAIL-u: PRODSTRUCT-u									
226.0			WITHDRAWDATEEFF_T	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: FEATURE} {NDN: PRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
227.0			COUNTRYLIST	Contains	D: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: FEATURE} {NDN: FEATURE} 
228.0	SWFEATURE		SWPRODSTRUCTAVAIL-u: SWPRODSTRUCT-u									
229.0			WITHDRAWDATEEFF_T	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: SWFEATURE} {NDN:SWPRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
229.2	SWPRODSTRUCT		SWPRODSTRUCTAVAIL-u		No Dates to Check							
229.4			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
del230.0	IPSCSTRUC		IPSCSTRUCAVAIL-u								nothing to check yet	
del231.0			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: IPSCSTRUC} {NDN: IPSCSTRUC} {LD: STATUS} {STATUS}
del232.0	IPSCFEAT		IPSCSTRUCAVAIL-u: IPSCSTRUC-d									
del233.0			WITHDRAWDATEEFF_T	=>	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: IPSCFEAT} {NDN:IPSCFEAT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
234.0	LSEO		LSEOAVAIL-u									
235.0			COUNTRYLIST	Contains	D: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
236.0			LSEOUNPUBDATEMTRGT	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
237.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
238.0	LSEOBUNDLE		LSEOBUNDLEAVAIL-u									
239.0			BUNDLUNPUBDATEMTRGT	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
240.0			COUNTRYLIST	Contains	D: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
241.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNCLE} {NDN: LSEOBUNCLE} {LD: STATUS} {STATUS}
Delete 20110318 242.0	FCTRANSACTION		FEATURETRNAVAIL-u									
Delete 20110318 243.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: STATUS} {STATUS}
Delete 20110318 244.0			WTHDRWEFFCTVDATE	=>	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
245.0	MODELCONVERT		MODELCONVERTAVAIL-u									
246.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
247.0			WTHDRWEFFCTVDATE	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
248.0	END	203										

	 * @param rootEntity
	 * @param annItem
	 * @param elevateMsg
	 * @param statusFlag
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void doEOSAvailChecks(EntityItem rootEntity, EntityItem annItem, boolean elevateMsg, String statusFlag) 
	throws SQLException, MiddlewareException
	{
		if (annItem!=null){
			String anntype = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
			addDebug("doEOSAvailChecks "+annItem.getKey()+" anntype "+anntype);
			//212	ANNOUNCEMENT		AVAILANNA									
			//213	WHEN		ANNTYPE	<>	"End Of Life - Discontinuance of service" (13)						This is just like "End Of Life - Withdrawal from mktg" (14)	
			//214			Count of	=	0			E	E	E		must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			if (!ANNTYPE_EOLDS.equals(anntype)){
				//MUST_NOT_BE_IN_THIS_ERR= {0} must not be in this {1} {2}	  
				args[0]="";
				args[1]= getLD_Value(annItem, "ANNTYPE");
				args[2]=getLD_NDN(annItem);
				createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_THIS_ERR",args);
				return;
			}
			//215	END	213
		}else{
			addDebug("doEOSAvailChecks NO ANNOUNCEMENT");
		}

		//210	IF		ANNSTATUS	=	Final (0020)	Elevate Msg		E	E	E	If the Announcement is "Final", then the AVAIL must meet these rules	
		int checklvl = getCheck_W_W_E(statusFlag);
		if(elevateMsg){
			checklvl=CHECKLEVEL_E;
		}
		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" End of Service Avail Checks:");

		ArrayList availCtry = new ArrayList();
		getCountriesAsList(rootEntity, availCtry, checklvl);

		if (annItem!=null){
			//206	ANNOUNCEMENT		AVAILANNA									
			//207	WHEN		ANNTYPE	=	"End Of Life - Discontinuance of service" (13)						This is just like "End Of Life - Withdrawal from mktg" (14)	
			//208			ANNDATE	<=	D: AVAIL	EFFECTIVEDATE	Yes	W	W	E		must not be earlier than the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			//209			COUNTRYLIST	Contains	D: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			checkDateStatus(rootEntity, annItem, "ANNDATE", false,checklvl,checklvl,DATE_GR_EQ);
			checkCountryList(annItem, availCtry, checklvl);									
			//211	END	207		
		}

		checkEOSOfferings(rootEntity, checklvl, availCtry); 
		//248	END	203
	}

	/**
	 * @param rootEntity
	 * @param checklvl
	 * @param availCtry
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkEOSOfferings(EntityItem rootEntity, int checklvl, ArrayList availCtry) 
	throws SQLException, MiddlewareException
	{
		//216.0	MODEL		MODELAVAIL-u									
		EntityGroup eGrp = m_elist.getEntityGroup("MODEL");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get models thru avail
		Vector vct = PokUtils.getAllLinkedEntities(rootEntity, "MODELAVAIL", "MODEL");
		for (int i=0; i< vct.size(); i++){
			EntityItem mdlItem = (EntityItem)vct.elementAt(i);
			//217.0			WTHDRWEFFCTVDATE	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODEL} {NDN: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
			//218.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
			checkDateStatus(rootEntity, mdlItem, "WTHDRWEFFCTVDATE", true,checklvl,CHECKLEVEL_E,DATE_GR_EQ);
		}
		vct.clear();

		//219.0	SVCMOD		SVCMODAVAIL-u									
		eGrp = m_elist.getEntityGroup("SVCMOD");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get models thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "SVCMODAVAIL", "SVCMOD");

		//delete 220.0			WITHDRAWDATE	=>	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SVCMOD} {NDN: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
		//delete 221.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
		//Add	221.2			CountOf	=	0			E	E	E		must not be in this {LD: AVAILTYPE} {LD: AVAIL} {NDN: AVAIL}
		for (int i=0; i< vct.size(); i++){
			EntityItem svcmdlItem = (EntityItem)vct.elementAt(i);
			//MUST_NOT_BE_IN_THIS_ERR= {0} must not be in this {1} {2}	  
			args[0]=getLD_NDN(svcmdlItem);
			args[1]= getLD_Value(rootEntity, "AVAILTYPE");
			args[2]=rootEntity.getEntityGroup().getLongDescription();
			createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_THIS_ERR",args);
		}
		vct.clear();

		//222.0	PRODSTRUCT		OOFAVAIL-u									
		eGrp = m_elist.getEntityGroup("PRODSTRUCT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get ps thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "OOFAVAIL", "PRODSTRUCT");
		for (int i=0; i< vct.size(); i++){
			EntityItem psItem = (EntityItem)vct.elementAt(i);
			//223.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
			//224.0			WTHDRWEFFCTVDATE	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
			checkDateStatus(rootEntity, psItem, "WTHDRWEFFCTVDATE", true,checklvl,CHECKLEVEL_E,DATE_GR_EQ);
		}
		vct.clear();

		//225.0	FEATURE		OOFAVAIL-u: PRODSTRUCT-u									
		eGrp = m_elist.getEntityGroup("FEATURE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		vct = getFeatFromAvailRelator(m_elist.getEntityGroup("OOFAVAIL"), "PRODSTRUCT", "FEATURE");
		for (int i=0; i< vct.size(); i++){
			EntityItem fcItem = (EntityItem)vct.elementAt(i);	
			EntityItem psItem =getDownLinkEntityItem(fcItem, "PRODSTRUCT");
			//226.0			WITHDRAWDATEEFF_T	<=	D: AVAIL	EFFECTIVEDATE		W	W	E	must not be earlier than {LD: FEATURE} {NDN: PRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
			checkCanNotBeEarlier3(psItem, rootEntity, "EFFECTIVEDATE", fcItem, "WITHDRAWDATEEFF_T", checklvl);
			//227.0			COUNTRYLIST	Contains	D: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: FEATURE} {NDN: FEATURE} 
			checkCountryList(fcItem, availCtry, checklvl);
		} 
		vct.clear();

		//229.2	SWPRODSTRUCT		SWPRODSTRUCTAVAIL-u		No Dates to Check							
		eGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get ps thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");
		for (int i=0; i< vct.size(); i++){
			EntityItem swpsItem = (EntityItem)vct.elementAt(i);
			//229.4			STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
			checkStatusVsDQ(swpsItem, "STATUS",rootEntity,CHECKLEVEL_E);
		}

		//228.0	SWFEATURE		SWPRODSTRUCTAVAIL-u: SWPRODSTRUCT-u									
		eGrp = m_elist.getEntityGroup("SWFEATURE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		vct = getFeatFromAvailRelator(m_elist.getEntityGroup("SWPRODSTRUCTAVAIL"), "SWPRODSTRUCT", "SWFEATURE");
		for (int i=0; i< vct.size(); i++){
			EntityItem swfcItem = (EntityItem)vct.elementAt(i);		
			EntityItem psItem =getDownLinkEntityItem(swfcItem, "SWPRODSTRUCT");
			//229.0			WITHDRAWDATEEFF_T	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: SWFEATURE} {NDN:SWPRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
			checkCanNotBeEarlier3(psItem, rootEntity, "EFFECTIVEDATE", swfcItem, "WITHDRAWDATEEFF_T", checklvl);
		} 
		vct.clear();

		//234.0	LSEO		LSEOAVAIL-u									
		eGrp = m_elist.getEntityGroup("LSEO");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem lseoItem = eGrp.getEntityItem(i);
			//235.0			COUNTRYLIST	Contains	D: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
			checkCountryList(lseoItem, availCtry, checklvl);
			//236.0			LSEOUNPUBDATEMTRGT	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
			//237.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
			checkDateStatus(rootEntity, lseoItem, "LSEOUNPUBDATEMTRGT", true,checklvl,CHECKLEVEL_E,DATE_GR_EQ);
		}

		//238.0	LSEOBUNDLE		LSEOBUNDLEAVAIL-u	 								
		eGrp = m_elist.getEntityGroup("LSEOBUNDLE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem lseobdlItem = eGrp.getEntityItem(i);
			//240.0			COUNTRYLIST	Contains	D: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
			checkCountryList(lseobdlItem, availCtry, checklvl);
			//239.0			BUNDLUNPUBDATEMTRGT	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
			//241.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNCLE} {NDN: LSEOBUNCLE} {LD: STATUS} {STATUS}
			checkDateStatus(rootEntity, lseobdlItem, "BUNDLUNPUBDATEMTRGT", true,checklvl,CHECKLEVEL_E, DATE_GR_EQ);
		}

		//245.0	MODELCONVERT		MODELCONVERTAVAIL-u									
		eGrp = m_elist.getEntityGroup("MODELCONVERT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem mdlcvtItem = eGrp.getEntityItem(i);
			//246.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
			//247.0			WTHDRWEFFCTVDATE	<=	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be earlier than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
			checkDateStatus(rootEntity, mdlcvtItem, "WTHDRWEFFCTVDATE", true,
					checklvl,CHECKLEVEL_E, DATE_GR_EQ);
		}
		//248.0	END	203
	}
	
	/**
	 * test 2 attributes, 1 on each entity, use ps entity in msg as path to item2
	 *225.00	FEATURE		OOFAVAIL-u: PRODSTRUCT-u			
	 *226.00			WITHDRAWDATEEFF_T	<=	D: AVAIL	EFFECTIVEDATE
	 * 			must not be earlier than {LD: FEATURE} {NDN: PRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
	 *CANNOT_BE_EARLIER_ERR3 = {0} {1} must not be earlier than the {2} {3} {4}  
	 * @param psitem
	 * @param item1
	 * @param attrCode1
	 * @param item2
	 * @param attrCode2
	 * @param checkLvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkCanNotBeEarlier3(EntityItem psitem, EntityItem item1, String attrCode1, EntityItem item2, String attrCode2,
			int checkLvl) throws SQLException, MiddlewareException
	{
		String date1 = getAttrValueAndCheckLvl(item1, attrCode1, checkLvl);
		String date2 = getAttrValueAndCheckLvl(item2, attrCode2, checkLvl);
		addDebug("checkCanNotBeEarlier3 "+(psitem==null?"":psitem.getKey())+" "+
				item1.getKey()+" "+attrCode1+":"+date1+" "+item2.getKey()+" "+
				attrCode2+":"+date2);
		boolean isok = checkDates(date1, date2, DATE_GR_EQ);	//date1=>date2	
		if(isok){ // look for missing meta
			if(date1.length()>0 && !Character.isDigit(date1.charAt(0))){
				isok=false;
			}
			if(date2.length()>0 && !Character.isDigit(date2.charAt(0))){
				isok=false;
			}
		}
		if (!isok){
			//	CANNOT_BE_EARLIER_ERR3 = {0} {1} must not be earlier than the {2} {3} {4}  
			//{LD:EFFECTIVEDATE}{EFFECTIVEDATE} must not be earlier than {LD: FEATURE} {NDN: PRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
			String psinfo = "";
			if (psitem!=null){
				psinfo = getLD_NDN(psitem)+" ";
			}

			String info=getLD_NDN(item1);
			if (item1.getEntityType().equals(getEntityType()) &&
					item1.getEntityID()==this.getEntityID()){
				// msg is on the root
				info = "";
			}
			args[0]=info;
			args[1]=this.getLD_Value(item1, attrCode1);
	
			if(item2.getEntityType().equals(getEntityType()) && item2.getEntityID()==getEntityID()){
				args[2] =item2.getEntityGroup().getLongDescription();
			}else{
				args[2] =getLD_NDN(item2);
			}
			args[3]=psinfo;
			args[4]=this.getLD_Value(item2, attrCode2);
			createMessage(checkLvl,"CANNOT_BE_EARLIER_ERR3",args);
		}
	}
	/*****************************
	 * 
154.0	AVAIL	E	Root									
155.0	WHEN		AVAILTYPE	=	"End of Marketing" (200)							
156.0			EFFECTIVEDATE									
157.0			COUNTRYLIST									
158.0	ANNOUNCEMENT		AVAILANNA									
159.0	WHEN		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)							
160.0			ANNDATE	<=	E: AVAIL	EFFECTIVEDATE	Yes	W	W	E		must not be earlier than the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
161.0			COUNTRYLIST	Contains	E: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
162.0	IF		ANNSTATUS	=	Final (0020)	Elevate Msg		E	E	E	If the Announcement is "Final", then the AVAIL must meet these rules	
163.0	END	159										
164.0	ANNOUNCEMENT		AVAILANNA									
165.0	WHEN		ANNTYPE	<>	"End Of Life - Withdrawal from mktg" (14)							
166.0			CountOf	=	0			E	E	E		must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
167.0	END	165										
168.0	MODEL		MODELAVAIL-u
169.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: MODEL} {NDN: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}									
170.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
171.0	SVCMOD		SVCMODAVAIL-u									
172.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SVCMOD} {NDN: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
173.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
174.0	PRODSTRUCT		OOFAVAIL-u									
175.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
176.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
177.0	FEATURE		OOFAVAIL-u: PRODSTRUCT-u									
178.0			WITHDRAWANNDATE_T	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: FEATURE} {NDN: PRODSTRUCT} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
179.0			COUNTRYLIST	Contains	E: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: FEATURE} {NDN: FEATURE} 
180.0	SWPRODSTRUCT		SWPRODSTRUCTAVAIL-u		No Dates to Check							
180.2			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
181.0	SWFEATURE		SWPRODSTRUCTAVAIL-u: SWPRODSTRUCT-u									
182.0			WITHDRAWANNDATE_T	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SWFEATURE} {NDN:SWPRODSTRUCT} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}

del183.0	IPSCSTRUC		IPSCSTRUCAVAIL-u									
del184.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: IPSCSTRUC} {NDN: IPSCSTRUC} {LD: STATUS} {STATUS}
del185.0	IPSCFEAT		IPSCSTRUCAVAIL-u: IPSCSTRUC-d									
del186.0			WITHDRAWDATEEFF_T	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: IPSCFEAT} {NDN:IPSCFEAT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
187.0	LSEO		LSEOAVAIL-u									
188.0			COUNTRYLIST	Contains	E: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
Delete 20110222 189.0			LSEOUNPUBDATEMTRGT	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
190.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
191.0	LSEOBUNDLE		LSEOBUNDLEAVAIL-u									
Delete 20110222 192.0			BUNDLUNPUBDATEMTRGT	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
193.0			COUNTRYLIST	Contains	E: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
194.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNCLE} {NDN: LSEOBUNCLE} {LD: STATUS} {STATUS}
Delete 20110318 195.0	FCTRANSACTION		FEATURETRNAVAIL-u									
Delete 20110318 196.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: STATUS} {STATUS}
Delete 20110318 197.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: WITHDRAWDATE} {WITHDRAWDATE}

198.0	MODELCONVERT		MODELCONVERTAVAIL-u									
199.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
200.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: WITHDRAWDATE} {WITHDRAWDATE}

201.0	END	155										

	 * @param rootEntity
	 * @param annItem
	 * @param elevateMsg
	 * @param statusFlag
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void doEOMAvailChecks(EntityItem rootEntity, EntityItem annItem, boolean elevateMsg, String statusFlag) 
	throws SQLException, MiddlewareException
	{
		if (annItem!=null){
			String anntype = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
			addDebug("doEOMAvailChecks "+annItem.getKey()+" anntype "+anntype);

			//164.0	ANNOUNCEMENT		AVAILANNA									
			//165.0	WHEN		ANNTYPE	<>	"End Of Life - Withdrawal from mktg" (14)							
			//166.0			Count of	=	0			E	E	E		must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			if (!ANNTYPE_EOL.equals(anntype)){
				//MUST_NOT_BE_IN_THIS_ERR= {0} must not be in this {1} {2}	  
				args[0]="";
				args[1]= getLD_Value(annItem, "ANNTYPE");
				args[2]=getLD_NDN(annItem);
				createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_THIS_ERR",args);
				return;
			}
			//167.0	END	165		
		}else{
			addDebug("doEOMAvailChecks NO ANNOUNCEMENT");
		}

		//162.0	IF		ANNSTATUS	=	Final (0020)	Elevate Msg		E	E	E	If the Announcement is "Final", then the AVAIL must meet these rules			
		int checklvl = getCheck_W_W_E(statusFlag);
		if(elevateMsg){
			checklvl=CHECKLEVEL_E;
		}
		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" End of Marketing Avail Checks:");

		ArrayList availCtry = new ArrayList();
		getCountriesAsList(rootEntity, availCtry, checklvl);

		if (annItem!=null){
			//158.0	ANNOUNCEMENT		AVAILANNA									
			//159.0	WHEN		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)							
			//160.0			ANNDATE	<=	E: AVAIL	EFFECTIVEDATE	Yes	W	W	E		must not be earlier than the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			checkDateStatus(rootEntity, annItem, "ANNDATE", false,checklvl,checklvl,DATE_GR_EQ);
			//161.0			COUNTRYLIST	Contains	E: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
			checkCountryList(annItem, availCtry, checklvl);									
			//163	END	159			
		}

		//168.0 - 200.0
		checkEOMOfferings(rootEntity, checklvl, availCtry);
		//201.0	END	155
	}
	/**
	 * @param rootEntity
	 * @param checklvl
	 * @param availCtry
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkEOMOfferings(EntityItem rootEntity, int checklvl, ArrayList availCtry) 
	throws SQLException, MiddlewareException
	{
		//168.0	MODEL		MODELAVAIL-u	
		EntityGroup eGrp = m_elist.getEntityGroup("MODEL");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get models thru avail
		Vector vct = PokUtils.getAllLinkedEntities(rootEntity, "MODELAVAIL", "MODEL");
		for (int i=0; i< vct.size(); i++){
			EntityItem mdlItem = (EntityItem)vct.elementAt(i);
			//169.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: MODEL} {NDN: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
			checkDateStatus(rootEntity, mdlItem, "WITHDRAWDATE", true,checklvl,CHECKLEVEL_E,DATE_LT_EQ);
		}
		vct.clear();

		//171.0	SVCMOD		SVCMODAVAIL-u	
		eGrp = m_elist.getEntityGroup("SVCMOD");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get models thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "SVCMODAVAIL", "SVCMOD");
			for (int i=0; i< vct.size(); i++){
				EntityItem svcmdlItem = (EntityItem)vct.elementAt(i);
				//172.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SVCMOD} {NDN: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
				//173.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
				checkDateStatus(rootEntity, svcmdlItem, "WITHDRAWDATE", true,checklvl,CHECKLEVEL_E,DATE_LT_EQ);
			}
		
		vct.clear();

		//174.0	PRODSTRUCT		OOFAVAIL-u	
		eGrp = m_elist.getEntityGroup("PRODSTRUCT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get ps thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "OOFAVAIL", "PRODSTRUCT");
		for (int i=0; i< vct.size(); i++){
			EntityItem psItem = (EntityItem)vct.elementAt(i);
			//175.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
			//176.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
			checkDateStatus(rootEntity, psItem, "WITHDRAWDATE", true,checklvl,CHECKLEVEL_E,DATE_LT_EQ);
		}
		vct.clear();

		//177.0	FEATURE		OOFAVAIL-u: PRODSTRUCT-u	
		eGrp = m_elist.getEntityGroup("FEATURE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		vct = getFeatFromAvailRelator(m_elist.getEntityGroup("OOFAVAIL"), "PRODSTRUCT", "FEATURE");
		for (int i=0; i< vct.size(); i++){
			EntityItem fcItem = (EntityItem)vct.elementAt(i);	
			EntityItem psItem =getDownLinkEntityItem(fcItem, "PRODSTRUCT");
			//178.0			WITHDRAWANNDATE_T	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: FEATURE} {NDN: PRODSTRUCT} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
			checkCanNotBeLater(psItem, rootEntity, "EFFECTIVEDATE", fcItem, "WITHDRAWANNDATE_T", checklvl);
			//179.0			COUNTRYLIST	Contains	E: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: FEATURE} {NDN: FEATURE} 
			checkCountryList(fcItem, availCtry, checklvl);
		} 
		vct.clear();

		//180.0	SWPRODSTRUCT		SWPRODSTRUCTAVAIL-u		No Dates to Check	
		eGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get ps thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");
		for (int i=0; i< vct.size(); i++){
			EntityItem swpsItem = (EntityItem)vct.elementAt(i);
			//180.2		STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
			checkStatusVsDQ(swpsItem, "STATUS",rootEntity,CHECKLEVEL_E);
		}

		//181.0	SWFEATURE		SWPRODSTRUCTAVAIL-u: SWPRODSTRUCT-u	
		eGrp = m_elist.getEntityGroup("SWFEATURE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		vct = getFeatFromAvailRelator(m_elist.getEntityGroup("SWPRODSTRUCTAVAIL"), "SWPRODSTRUCT", "SWFEATURE");
		for (int i=0; i< vct.size(); i++){
			EntityItem swfcItem = (EntityItem)vct.elementAt(i);		
			EntityItem psItem =getDownLinkEntityItem(swfcItem, "SWPRODSTRUCT");
			//182.0			WITHDRAWANNDATE_T	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SWFEATURE} {NDN:SWPRODSTRUCT} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
			checkCanNotBeLater(psItem, rootEntity, "EFFECTIVEDATE", swfcItem, "WITHDRAWANNDATE_T", checklvl);
		} 
		vct.clear();

		//187.0	LSEO		LSEOAVAIL-u	
		eGrp = m_elist.getEntityGroup("LSEO");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem lseoItem = eGrp.getEntityItem(i);
			//188.0			COUNTRYLIST	Contains	E: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
			checkCountryList(lseoItem, availCtry, checklvl);
			//Delete 20110222 189.0			LSEOUNPUBDATEMTRGT	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
			//190.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
			//checkDateStatus(rootEntity, lseoItem, "LSEOUNPUBDATEMTRGT", true,checklvl,CHECKLEVEL_E,DATE_LT_EQ);
			checkStatusVsDQ(lseoItem, "STATUS",rootEntity,CHECKLEVEL_E);
		}

		//191.0	LSEOBUNCLE		LSEOBUNDLEAVAIL-u
		eGrp = m_elist.getEntityGroup("LSEOBUNDLE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem lseobdlItem = eGrp.getEntityItem(i);
			//193.0			COUNTRYLIST	Contains	E: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
			checkCountryList(lseobdlItem, availCtry, checklvl);
			//Delete 20110222 192.0			BUNDLUNPUBDATEMTRGT	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
			//194.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNCLE} {NDN: LSEOBUNCLE} {LD: STATUS} {STATUS}
			//checkDateStatus(rootEntity, lseobdlItem, "BUNDLUNPUBDATEMTRGT", true,checklvl,CHECKLEVEL_E, DATE_LT_EQ);
			checkStatusVsDQ(lseobdlItem, "STATUS",rootEntity,CHECKLEVEL_E);
		}

		//Delete 20110318 195.0	FCTRANSACTION		FEATURETRNAVAIL-u	
		/*eGrp = m_elist.getEntityGroup("FCTRANSACTION");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem fctransItem = eGrp.getEntityItem(i);
			//196.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: STATUS} {STATUS}
			//197.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: FCTRANSACTION} {NDN: FCTRANSACTION} {LD: WITHDRAWDATE} {WITHDRAWDATE}
			checkDateStatus(rootEntity, fctransItem, "WITHDRAWDATE", true, checklvl,CHECKLEVEL_E, DATE_LT_EQ);
		}*/	

		//198.0	MODELCONVERT		MODELCONVERTAVAIL-u		
		eGrp = m_elist.getEntityGroup("MODELCONVERT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem mdlcvtItem = eGrp.getEntityItem(i);
			//199.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
			//200.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
			checkDateStatus(rootEntity, mdlcvtItem, "WITHDRAWDATE", true, checklvl,CHECKLEVEL_E, DATE_LT_EQ);
		}
		//201.0	END	155	
	}
	/**
	 * @param rootEntity
	 * @param checklvl
	 * @param availCtry
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkEOLOfferings(EntityItem rootEntity, int checklvl, ArrayList availCtry) 
	throws SQLException, MiddlewareException
	{
		//120.0	MODEL		MODELAVAIL-u	
		//168.0	MODEL		MODELAVAIL-u									
		EntityGroup eGrp = m_elist.getEntityGroup("MODEL");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get models thru avail
		Vector vct = PokUtils.getAllLinkedEntities(rootEntity, "MODELAVAIL", "MODEL");
		for (int i=0; i< vct.size(); i++){
			EntityItem mdlItem = (EntityItem)vct.elementAt(i);
			//121.0			WTHDRWEFFCTVDATE	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: MODEL} {NDN: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
			//122.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
			//170.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
			checkDateStatus(rootEntity, mdlItem, "WTHDRWEFFCTVDATE", true,checklvl,CHECKLEVEL_E,DATE_LT_EQ);
		}
		vct.clear();

		//123.0	SVCMOD		SVCMODAVAIL-u									
		//171.0	SVCMOD		SVCMODAVAIL-u										
		eGrp = m_elist.getEntityGroup("SVCMOD");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get models thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "SVCMODAVAIL", "SVCMOD");

		for (int i=0; i< vct.size(); i++){
			EntityItem svcmdlItem = (EntityItem)vct.elementAt(i);
			//124.0			WTHDRWEFFCTVDATE	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SVCMOD} {NDN: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
			//125.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
			//172.0			WITHDRAWDATE	=>	E: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SVCMOD} {NDN: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
			//173.0			STATUS	=>	E: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
			//delete 220.0			WITHDRAWDATE	=>	D: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SVCMOD} {NDN: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
			//delete 221.0			STATUS	=>	D: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}
			checkDateStatus(rootEntity, svcmdlItem, "WTHDRWEFFCTVDATE", true,checklvl,CHECKLEVEL_E,DATE_LT_EQ);
		}
		
		vct.clear();

		//126.0	PRODSTRUCT		OOFAVAIL-u									
		//174.0	PRODSTRUCT		OOFAVAIL-u									
		eGrp = m_elist.getEntityGroup("PRODSTRUCT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get ps thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "OOFAVAIL", "PRODSTRUCT");
		for (int i=0; i< vct.size(); i++){
			EntityItem psItem = (EntityItem)vct.elementAt(i);
			//127.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
			//128.0			WTHDRWEFFCTVDATE	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
			checkDateStatus(rootEntity, psItem, "WTHDRWEFFCTVDATE", true,checklvl,CHECKLEVEL_E,DATE_LT_EQ);
		}
		vct.clear();

		//129.0	FEATURE		OOFAVAIL-u: PRODSTRUCT-u									
		//177.0	FEATURE		OOFAVAIL-u: PRODSTRUCT-u	
		eGrp = m_elist.getEntityGroup("FEATURE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		vct = getFeatFromAvailRelator(m_elist.getEntityGroup("OOFAVAIL"), "PRODSTRUCT", "FEATURE");
		for (int i=0; i< vct.size(); i++){
			EntityItem fcItem = (EntityItem)vct.elementAt(i);	
			EntityItem psItem =getDownLinkEntityItem(fcItem, "PRODSTRUCT");
			//130.0			WITHDRAWDATEEFF_T	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: FEATURE} {NDN: PRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
			checkCanNotBeLater(psItem, rootEntity, "EFFECTIVEDATE", fcItem, "WITHDRAWDATEEFF_T", checklvl);
			//131.0			COUNTRYLIST	Contains	C: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: FEATURE} {NDN: FEATURE} 
			checkCountryList(fcItem, availCtry, checklvl);
		} 
		vct.clear();

		//132.0,180.0	SWPRODSTRUCT		SWPRODSTRUCTAVAIL-u		No Dates to Check	
		eGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		// get ps thru avail
		vct = PokUtils.getAllLinkedEntities(rootEntity, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");
		for (int i=0; i< vct.size(); i++){
			EntityItem swpsItem = (EntityItem)vct.elementAt(i);
			//132.2,180.2		STATUS	=>	A: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
			checkStatusVsDQ(swpsItem, "STATUS",rootEntity,CHECKLEVEL_E);
		}

		//133.0	SWFEATURE		SWPRODSTRUCTAVAIL-u: SWPRODSTRUCT-u																							
		//181.0	SWFEATURE		SWPRODSTRUCTAVAIL-u: SWPRODSTRUCT-u	
		eGrp = m_elist.getEntityGroup("SWFEATURE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		vct = getFeatFromAvailRelator(m_elist.getEntityGroup("SWPRODSTRUCTAVAIL"), "SWPRODSTRUCT", "SWFEATURE");
		for (int i=0; i< vct.size(); i++){
			EntityItem swfcItem = (EntityItem)vct.elementAt(i);		
			EntityItem psItem =getDownLinkEntityItem(swfcItem, "SWPRODSTRUCT");
			//134.0			WITHDRAWDATEEFF_T	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: SWFEATURE} {NDN:SWPRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
			checkCanNotBeLater(psItem, rootEntity, "EFFECTIVEDATE", swfcItem, "WITHDRAWDATEEFF_T", checklvl);
		} 
		vct.clear();

		//139.0	LSEO		LSEOAVAIL-u									
		//187.0	LSEO		LSEOAVAIL-u	
		eGrp = m_elist.getEntityGroup("LSEO");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem lseoItem = eGrp.getEntityItem(i);
			//140.0			COUNTRYLIST	Contains	C: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
			//188.0			COUNTRYLIST	Contains	E: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEO} {NDN: LSEO} 
			checkCountryList(lseoItem, availCtry, checklvl);
			//141.0			LSEOUNPUBDATEMTRGT	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
			//142.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEO} {NDN: LSEO} {LD: STATUS} {STATUS}
			checkDateStatus(rootEntity, lseoItem, "LSEOUNPUBDATEMTRGT", true,checklvl,CHECKLEVEL_E,DATE_LT_EQ);
		}

		//143.0	LSEOBUNCLE		LSEOBUNDLEAVAIL-u									
		//191.0	LSEOBUNCLE		LSEOBUNDLEAVAIL-u
		eGrp = m_elist.getEntityGroup("LSEOBUNDLE");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem lseobdlItem = eGrp.getEntityItem(i);
			//145.0			COUNTRYLIST	Contains	C: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
			//193.0			COUNTRYLIST	Contains	E: AVAIL	COUNTRYLIST		W	W	E		can not have a {LD: COUNTRYLIST} that is not in the {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} 
			checkCountryList(lseobdlItem, availCtry, checklvl);
			//144.0			BUNDLUNPUBDATEMTRGT	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: LSEOBUNDLE} {NDN: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
			//146.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: LSEOBUNCLE} {NDN: LSEOBUNCLE} {LD: STATUS} {STATUS}
			checkDateStatus(rootEntity, lseobdlItem, "BUNDLUNPUBDATEMTRGT", true,checklvl,CHECKLEVEL_E, DATE_LT_EQ);
		}
		//150.0	MODELCONVERT		MODELCONVERTAVAIL-u									
		//198.0	MODELCONVERT		MODELCONVERTAVAIL-u		
		eGrp = m_elist.getEntityGroup("MODELCONVERT");
		addHeading(3,eGrp.getLongDescription()+" Checks:");
		for (int i=0; i< eGrp.getEntityItemCount(); i++){
			EntityItem mdlcvtItem = eGrp.getEntityItem(i);
			//151.0			STATUS	=>	C: AVAIL	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: STATUS} {STATUS}
			//152.0			WTHDRWEFFCTVDATE	=>	C: AVAIL	EFFECTIVEDATE		W	W	E		must not be later than {LD: MODELCONVERT} {NDN: MODELCONVERT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
			checkDateStatus(rootEntity, mdlcvtItem, "WTHDRWEFFCTVDATE", true,
					checklvl,CHECKLEVEL_E, DATE_LT_EQ);
		}
		//153.0	END	107		
		//201.0	END	155	
	}
	/********************************
	 * Do date check on single date attribute and status check 
	 * @param rootEntity
	 * @param item
	 * @param dateAttrCode
	 * @param checkStatus
	 * @param dateChklvl
	 * @param statusChklvl
	 * @param dateRule
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkDateStatus(EntityItem rootEntity, EntityItem item, String dateAttrCode, 
			boolean checkStatus, int dateChklvl, int statusChklvl, int dateRule) 
	throws SQLException, MiddlewareException
	{
		checkDateStatus(rootEntity, item,  new String[]{dateAttrCode}, checkStatus, dateChklvl,
				statusChklvl, dateRule);
	}

	/**********************************
	 * Do date checks and status check
	 * @param rootEntity the Avail
	 * @param item		the item to test against
	 * @param dateAttrCodes String[] of date attr codes
	 * @param checkStatus boolean if true verify status
	 * @param dateChklvl int level for date check errors
	 * @param statusChklvl int level for status check error
	 * @param dateRule int date check to make
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkDateStatus(EntityItem rootEntity, EntityItem item, String[] dateAttrCodes, 
			boolean checkStatus, int dateChklvl, int statusChklvl, int dateRule) 
	throws SQLException, MiddlewareException
	{
		for (int i=0; i< dateAttrCodes.length; i++){
			if (dateRule==DATE_GR_EQ){
				checkCanNotBeEarlier(rootEntity, "EFFECTIVEDATE", item, dateAttrCodes[i], dateChklvl);
			}else{
				checkCanNotBeLater(rootEntity, "EFFECTIVEDATE", item, dateAttrCodes[i], dateChklvl);
			}
		}
		if (checkStatus){
			checkStatusVsDQ(item, "STATUS",rootEntity,statusChklvl);
		}
	}
	private void checkDateStatus(EntityItem rootEntity, EntityItem item, String[] dateAttrCodes, 
			boolean checkStatus, int []dateChklvl, int statusChklvl, int dateRule) 
	throws SQLException, MiddlewareException
	{
		for (int i=0; i< dateAttrCodes.length; i++){
			if (dateRule==DATE_GR_EQ){
				checkCanNotBeEarlier(rootEntity, "EFFECTIVEDATE", item, dateAttrCodes[i], dateChklvl[i]);
			}else{
				checkCanNotBeLater(rootEntity, "EFFECTIVEDATE", item, dateAttrCodes[i], dateChklvl[i]);
			}
		}
		if (checkStatus){
			checkStatusVsDQ(item, "STATUS",rootEntity,statusChklvl);
		}
	}
	/**************************
	 * check that the Avails countries are a subset of this item's countrys 
	 * @param item
	 * @param availCtry
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkCountryList(EntityItem item, ArrayList availCtry, int checklvl) 
	throws SQLException, MiddlewareException
	{
		ArrayList itemCtry = new ArrayList();
		getCountriesAsList(item, itemCtry, checklvl);
		if (!itemCtry.containsAll(availCtry)){
			addDebug(item.getKey()+" ctrylist "+itemCtry+" availctry "+availCtry);
			//COUNTRY_MISMATCH_ERR = can not have a {0} that is not in the {1}
			args[0]= PokUtils.getAttributeDescription(item.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
			args[1]=getLD_NDN(item);
			createMessage(checklvl,"COUNTRY_MISMATCH_ERR",args);
		}
		itemCtry.clear();
	}
	/***********************************************
	 *  Get ABR description
	 *
	 *@return java.lang.String
	 */
	public String getDescription()
	{
		String desc =  "AVAIL ABR.";

		return desc;
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getABRVersion()
	{
		return "1.22";
	}
}
