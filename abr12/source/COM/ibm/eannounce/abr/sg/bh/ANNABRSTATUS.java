// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2011 All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
ANNABRSTATUS_class=COM.ibm.eannounce.abr.sg.ANNABRSTATUS
ANNABRSTATUS_enabled=true
ANNABRSTATUS_idler_class=A
ANNABRSTATUS_keepfile=true
ANNABRSTATUS_read_only=true
ANNABRSTATUS_report_type=DGTYPE01
ANNABRSTATUS_vename=EXRPT3ANNDQ
ANNABRSTATUS_CAT1=RPTCLASS.ANNABRSTATUS
ANNABRSTATUS_CAT2=
ANNABRSTATUS_CAT3=RPTSTATUS
ANNABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390

 *
 * 
 */

package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import com.ibm.transform.oim.eacm.util.*;

import java.sql.SQLException;
import java.util.*;

/**********************************************************************************
* ANNABRSTATUS class
* 
*BH FS ABR Data Quality 20111020e.xls - BH Defect 67890 (support for old data)
*sets changes
*
*BH FS ABR Data Quality 20101026.doc
*sets updates
*ANNABRSTATUS_domainList_XCC_LIST=0050,0520
*need updated EXRPT3ANNDQ
*
*
VIII.	ANNOUNCEMENT
A.	Checking

An Announcement includes all of the AVAILs that have the same ANNCODENAME. 

The AVAILs need to be checked as indicated on the ANNOUNCEMENT tab in the attached spreadsheet.

There are three types of announcements with checking based on ANNTYPE. For each Announcement Type, 
the corresponding Availability Type (AVAIL.AVAILTYPE) is listed below it:
1.	"New" (19)
a.	�Planned Availability� (146)
b.	�First Order� (143)
2.	"End Of Life - Withdrawal from mktg" (14)
a.	�Last Order� (149)
3.	"End Of Life - Withdrawal from mktg" (14)
a.	�End of Service� (151)

The other Announcement Types will be �retired� via meta data; however, the ABR will check to make 
sure that these other values will not be used without an update to the ABR.

*
* Virtual Entity
* Lev	Entity1	RelType	Relator	Entity2	Dir
* 0	ANNOUNCEMENT	Assoc	ANNAVAILA	AVAIL	D
* 1	PRODSTRUCT	Relator	OOFAVAIL	AVAIL	U
* 1	LSEO	Relator	LSEOAVAIL	AVAIL	U
* 1	LSEOBUNDLE	Relator	LSEOBUNDLEAVAIL	AVAIL	U
* 1	MODEL	Relator	MODELAVAIL	AVAIL	U
* 2	FEATURE	Relator	PRODSTRUCT	MODEL	U

* 2	LSEO	Relator	LSEOPRODSTRUCT	PRODSTRUCT	D
* 3	FEATURE	Relator	PRODSTRUCT	MODEL	U
* 2	LSEO	Relator	WWSEOLSEO	WWSEO	U
* 3	WWSEO	Relator	WWSEOPRODSTRUCT	PRODSTRUCT	D
* 4	FEATURE	Relator	PRODSTRUCT	MODEL	U
* 2	LSEOBUNDLE	Relator	LSEOBUNDLELSEO	LSEO	D
* 3	LSEO	Relator	LSEOPRODSTRUCT	PRODSTRUCT	D
* 4	FEATURE	Relator	PRODSTRUCT	MODEL	U
* 3	LSEO	Relator	WWSEOLSEO	WWSEO	U
* 4	WWSEO	Relator	WWSEOPRODSTRUCT	PRODSTRUCT	D
* 5	FEATURE	Relator	PRODSTRUCT	MODEL	U
*
*/
//ANNABRSTATUS.java,v
//ANNABRSTATUS.java,v
//Revision 1.12  2010/01/21 22:20:26  wendy
//updates for BH FS ABR Data Quality 20100120.doc
// 
//Revision 1.9  2010/01/07 12:49:59  wendy
//cvs failure again
//
//Revision 1.4  2009/11/30 18:28:16  wendy
//Updated for spec chg BH FS ABR Data Qualtity 20091120.xls
//
//Revision 1.3  2009/11/04 15:07:19  wendy
//SR 76 - OTC - Support pre-configured/configured/sw/services offerings
//
//Revision 1.2  2009/08/17 15:03:53  wendy
//Added headings
//
//Revision 1.1  2009/08/14 22:26:30  wendy
//SR10, 11, 12, 15, 17 BH updates phase 4
//
public class ANNABRSTATUS extends DQABRSTATUS
{
	private static final String[] LSEOABRS = {"EPIMSABRSTATUS","ADSABRSTATUS"};
	private static final String[] MODELABRS = {"ADSABRSTATUS"};
	private static final String[] SVCMODABRS = {"ADSABRSTATUS"};
	private static final String[] LSEOBDLABRS = {"EPIMSABRSTATUS"};
	private static final String[] RFCABRS = {"RFCABRSTATUS"};
	
	/**********************************
	* always need it now
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}
	/**********************************
	* 
	* from sets ss:
	2.00	ANNOUNCEMENT		Root Entity							
3.00		WHEN			ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)			
3.20	AND			ANNOUNCEMENT	PDHDOMAIN	IN	ABR_PROPERTITIES  XCC_LIST			
3.40	SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
4.00		END	3.00								
							
Change 2011-10-20	5.000		WHEN			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	delWWPRTABRSTATUS		del&ABRWAITODS2 
Add 2011-10-20		5.200		AND			ANNOUNCEMENT	PDHDOMAIN	IN	XCC_LIST			
Add 2011-10-20		5.400		SET			ANNOUNCEMENT				WWPRTABRSTATUS		&ABRWAITODS2 
	6.000		SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
	
	19.00	END	5.00								
	7.00	WHEN		ANNAVAILA	AVAIL	STATUS	=	"Final" (0020)			
	8.00	AND			ANNOUNCEMENT	STATUS	=	"Final" (0020)			
Change 2011-10-20e	9.000		IF		ANNAVAILA: LSEOAVAIL-u	LSEO	STATUS	=	"Final" (0020)			
Add 2011-10-20d	9.200		AND			ANNOUNCEMENT	PDHDOMAIN	IN	XCC_LIST			
Add 2011-10-20e	9.400		SET			LSEO				EPIMSABRSTATUS		&ABRWAITODS
	10.000		SET			LSEO				ADSABRSTATUS		&ADSFEED
	11.000		END	9.000								
								
	12.00	IF		ANNAVAILA: MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)	ADSABRSTATUS		&ADSFEED
	13.00	END	12								
	14.00	IF		ANNAVAILA: SVCMODAVAIL-u	SVCMOD	STATUS	=	"Final" (0020)	ADSABRSTATUS		&ADSFEED
	15.00	END	14								
	16.00	IF		ANNAVAILA: LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
	17.00	END	16	
17.20	R1.0	IF		ANNAVAILA: MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Final" (0020)			
17.22	R1.0	SET			MODELCONVERT				ADSABRSTATUS		&ADSFEED
17.24	R1.0	END	17.20								
17.26	R1.0	IF		ANNAVAILA: OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Final" (0020)			
17.28	R1.0	SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED
17.30	R1.0	END	17.26								
17.32	R1.0	IF		ANNAVAILA: SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)			
17.33	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	STATUS	=	"Final" (0020)	
17.34	R1.0	SET			"SVCMODSVCSEO-u:SVCMOD"				ADSABRSTATUS
17.36	R1.0	END	17.32								
17.38	R1.0	IF		ANNAVAILA: SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Final" (0020)			
17.40	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
17.42		END	17.38

	18.00	END	7		
							
R1.0	18.10	WHEN		ANNAVAILA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
R1.0	18.11	AND			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
R1.0	18.12	IF		ANNAVAILA: LSEOAVAIL-u	LSEO	STATUS	=	"Ready for Review" (0040)			
R1.0	18.13	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)			
R1.0 	18.14	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
R1.0	18.15	END	18.12								
R1.0	18.22	IF		ANNAVAILA: MODELAVAIL-u	MODEL	STATUS	=	"Ready for Review" (0040)			
R1.0	18.23	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)			
R1.0 	18.24	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR	
R1.0	18.25	END	18.22								
R1.0	18.32	IF		ANNAVAILA: SVCMODAVAIL-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
R1.0	18.33	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)			
R1.0 	18.34	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
R1.0	18.35	END	18.32								
R1.0	18.42	IF		ANNAVAILA: LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)			
R1.0 	18.43	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)			
R1.0 	18.44	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
R1.0	18.45	END	18.42
18.50	R1.0	IF		ANNAVAILA: MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Ready for Review" (0040)			
18.70	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
18.90	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR	
19.10	R1.0	END	18.50								
19.30	R1.0	IF		ANNAVAILA: OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Ready for Review" (0040)			
19.50	R1.0	AND			PRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
19.70	R1.0	SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	
19.90	R1.0	END	19.30								
20.10	R1.0	IF		ANNAVAILA: SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
20.30	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
20.40	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	STATUS	=	"Ready for Review" (0040)		
20.50	R1.0	SET			"SVCMODSVCSEO-u:SVCMOD"				ADSABRSTATUS	&ADSFEEDRFR
20.55	R1.0	END	20.10							
20.60	R1.0	IF		ANNAVAILA: SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)			
20.65	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
20.70	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
20.75	R1.0	END	20.60								
20.80	R1.0	END	18.10	
20.90	R1.0	END	2.00	Announcement
								
R1.0	18.46	END	18.1								
	20.00	END	2	ANNOUNCEMENT							

	*/
	/**********************************
	* complete abr processing after status moved to readyForReview; (status was chgreq or draft)
	*/
	protected void completeNowR4RProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		String anntype = getAttributeFlagEnabledValue(rootEntity, "ANNTYPE");
		addDebug(rootEntity.getKey()+" status now R4R type "+anntype);
		
		doR10AAOfferings(m_elist.getEntityGroup("AVAIL"));
    }

	/**********************************
	* complete abr processing after status moved to final; (status was r4r)								
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		String anntype = getAttributeFlagEnabledValue(rootEntity, "ANNTYPE");
		addDebug(rootEntity.getKey()+" status now final type "+anntype);
		//2.00	ANNOUNCEMENT		Root Entity				
		//3.00		WHEN			ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)			
		if (ANNTYPE_EOL.equals(anntype)){
			addDebug(rootEntity.getKey()+" is Final and EOL");
			//3.20	AND			ANNOUNCEMENT	PDHDOMAIN	IN	ABR_PROPERTITIES  XCC_LIST			
			//3.40	SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
			if (domainInRuleList(rootEntity, "XCC_LIST")){ 
				setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), rootEntity);
			}else{
				addDebug("nowFinal:  "+rootEntity.getKey()+" was EOL and domain not in XCCLIST");
			}
		}
		//4.00		END	3.00
		
		//Change 2011-10-20	5.000		WHEN		ANNOUNCEMENT	ANNTYPE	=	"New" (19)	 
		//Add 2011-10-20	5.200		AND			ANNOUNCEMENT	PDHDOMAIN	IN	XCC_LIST					
		if (ANNTYPE_NEW.equals(anntype) && 
				domainInRuleList(rootEntity, "XCC_LIST")){
			addDebug(rootEntity.getKey()+" is Final and New and in xcclist");
			//Add 2011-10-20		5.400		SET			ANNOUNCEMENT				WWPRTABRSTATUS		&ABRWAITODS2 
			setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", getQueuedValue("WWPRTABRSTATUS"), rootEntity);			
			//6.000		SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
			setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), rootEntity);
		}
		//19.00	END	5.00
		
		boolean doR10processing = doR10processing();
		boolean hasHardwareOrHIPOModel = false;
		//7.00	WHEN		ANNAVAILA	AVAIL	STATUS	=	"Final" (0020)			
		//8.00	AND			ANNOUNCEMENT	STATUS	=	"Final" (0020)			
		EntityGroup egrp = m_elist.getEntityGroup("AVAIL");
		for (int i = 0; i < egrp.getEntityItemCount(); i++) {
			EntityItem avail = egrp.getEntityItem(i);
			//7.00	WHEN		ANNAVAILA	AVAIL	STATUS	=	"Final" (0020)	
			if (statusIsFinal(avail)) {
				//9.000		IF		ANNAVAILA: LSEOAVAIL-u	LSEO	STATUS	=	"Final" (0020)			
				//Add 2011-10-20d	9.200		AND			ANNOUNCEMENT	PDHDOMAIN	IN	XCC_LIST			
				if(domainInRuleList(rootEntity, "XCC_LIST")){								
					//Add 2011-10-20e	9.400		SET			LSEO				EPIMSABRSTATUS		&ABRWAITODS
					//10.000	SET			LSEO				ADSABRSTATUS		&ADSFEED
					verifyStatusAndSetABRStatus(avail, "LSEOAVAIL", "LSEO",LSEOABRS);
				}
				//11.000		END	9.000		
				//12.00	IF		ANNAVAILA: MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)	ADSABRSTATUS		&ADSFEED
				//12.200	SetSinceFinal			MODEL	RFCABRSTATUS	=	"Passed" (0030)	RFCABRSTATUS		&OIMSFEED
				//12.020	SetSinceFinal			MODEL	RFCABRSTATUS	<>	"Passed" (0030)	RFCABRSTATUS		&OIMSFEED
				verifyStatusAndSetABRStatus(avail, "MODELAVAIL", "MODEL",MODELABRS);
				//13.00	END	12									
				//14.00	IF		ANNAVAILA: SVCMODAVAIL-u	SVCMOD	STATUS	=	"Final" (0020)	ADSABRSTATUS		&ADSFEED
				verifyStatusAndSetABRStatus(avail, "SVCMODAVAIL", "SVCMOD",SVCMODABRS);
				//15.00	END	14	
				//16.00	IF		ANNAVAILA: LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Final" (0020)	EPIMSABRSTATUS		&ABRWAITODS
				verifyStatusAndSetABRStatus(avail, "LSEOBUNDLEAVAIL", "LSEOBUNDLE",LSEOBDLABRS);
				//17.00	END	16
				
				if(doR10processing){
					//17.20	R1.0	IF		ANNAVAILA: MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Final" (0020)			
					//17.22	R1.0	SET			MODELCONVERT				ADSABRSTATUS		&ADSFEED
					verifyStatusAndSetABRStatus(avail, "MODELCONVERTAVAIL", "MODELCONVERT",SVCMODABRS);
					//17.220	SET			MODELCONVERT				RFCABRSTATUS		&OIMSFEED
					verifyStatusAndSetABRStatus(avail, "MODELCONVERTAVAIL", "MODELCONVERT",RFCABRS);
					//17.24	R1.0	END	17.20								
					//17.26	R1.0	IF		ANNAVAILA: OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Final" (0020)			
					//17.28	R1.0	SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED
					verifyStatusAndSetABRStatus(avail, "OOFAVAIL", "PRODSTRUCT",SVCMODABRS);
					//17.280	SET			PRODSTRUCT				RFCABRSTATUS		&OIMSFEED
					verifyStatusAndSetABRStatus(avail, "OOFAVAIL", "PRODSTRUCT",RFCABRS);
					//17.30	R1.0	END	17.26								
					//17.32	R1.0	IF		ANNAVAILA: SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)			
					//17.33	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	STATUS	=	"Final" (0020)	
					//17.34	R1.0	SET			"SVCMODSVCSEO-u:SVCMOD"				ADSABRSTATUS
					verifySVCSEOAndSetSVCMODABRStatus(avail);
					//17.36	R1.0	END	17.32								
					//17.38	R1.0	IF		ANNAVAILA: SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Final" (0020)			
					//17.40	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS		&ADSFEED
					verifyStatusAndSetABRStatus(avail, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT",SVCMODABRS);
					//17.42		END	17.38
				}
			}
			//18.00	END	7
			
			// Check if has Hardware or HIPO MODEL or has PRODSTRUCT
			if (!hasHardwareOrHIPOModel) {
				addDebug("check if has Hardware or HIPO model for  " + avail.getKey());
				Vector mdlVct = PokUtils.getAllLinkedEntities(avail, "MODELAVAIL", "MODEL");
				for (int j = 0; j<mdlVct.size(); j++){
					EntityItem mdlItem = (EntityItem)mdlVct.elementAt(j);
					if (isHardwareOrHIPOModel(mdlItem)) {
						hasHardwareOrHIPOModel = true;
						break;
					}
				}		
				mdlVct.clear();
				Vector psVct = PokUtils.getAllLinkedEntities(avail, "OOFAVAIL", "PRODSTRUCT");
				addDebug("check if has PRODSTRUCT for  " + avail.getKey() + " size " + psVct.size());
				if (psVct.size() > 0) {
					hasHardwareOrHIPOModel = true;
				}
				psVct.clear();
			}			
		}		

		//R1.0	18.10
		doR10AAOfferings(egrp);
		//R1.0	18.46	END	18.1
		
//		if(getAttributeValueHistoryCount(rootEntity, "ANNSTATUS", STATUS_FINAL) == 0) { // check first time final, there will be no final ANNSTATUS history
		// TODO
		if (isQsmANNTYPE(anntype) && hasHardwareOrHIPOModel) {
			addDebug("ANNOUNCETMENT goes to final to trigger QSM");
			setFlagValue(m_elist.getProfile(), "QSMCREFABRSTATUS", getQueuedValueForItem(rootEntity, "QSMCREFABRSTATUS"), rootEntity);
			setFlagValue(m_elist.getProfile(), "QSMFULLABRSTATUS", getQueuedValueForItem(rootEntity, "QSMFULLABRSTATUS"), rootEntity);
		}			
//		}
		
		// END	2.00	ANNOUNCEMENT
	}
	/**
	 * 
	 * do R10 processing for offerings when ANNOUNCEMENT changes status
for ANNOUNCEMENT
R1.0	18.10	WHEN		ANNAVAILA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
R1.0	18.11	AND			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
R1.0	18.12	IF		ANNAVAILA: LSEOAVAIL-u	LSEO	STATUS	=	"Ready for Review" (0040)			
R1.0	18.13	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)			
R1.0 	18.14	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
R1.0	18.15	END	18.12								
R1.0	18.22	IF		ANNAVAILA: MODELAVAIL-u	MODEL	STATUS	=	"Ready for Review" (0040)			
R1.0	18.23	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)			
R1.0 	18.24	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR	
R1.0	18.25	END	18.22								
R1.0	18.32	IF		ANNAVAILA: SVCMODAVAIL-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
R1.0	18.33	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)			
R1.0 	18.34	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
R1.0	18.35	END	18.32								
R1.0	18.42	IF		ANNAVAILA: LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)			
R1.0 	18.43	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)			
R1.0 	18.44	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
R1.0	18.45	END	18.42								

18.50	R1.0	IF		ANNAVAILA: MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Ready for Review" (0040)			
18.70	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
18.90	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR
19.10	R1.0	END	18.50								
19.30	R1.0	IF		ANNAVAILA: OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Ready for Review" (0040)			
19.50	R1.0	AND			PRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
19.70	R1.0	SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	
19.90	R1.0	END	19.30								
20.10	R1.0	IF		ANNAVAILA: SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
20.30	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
20.40	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	STATUS	=	"Ready for Review" (0040)		
20.50	R1.0	SET			"SVCMODSVCSEO-u:SVCMOD"				ADSABRSTATUS	&ADSFEEDRFR
20.55	R1.0	END	20.10							
20.60	R1.0	IF		ANNAVAILA: SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)			
20.65	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
20.70	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	
20.75	R1.0	END	20.60								

	 * @param availGrp
	 * @param rootItem - item that was moved to final or rfr.  note it will not be rfr or final yet
	 */
	private void doR10AAOfferings(EntityGroup availGrp)
	{
		if(!doR10processing()){
			return;
		}
		
		for (int i = 0; i < availGrp.getEntityItemCount(); i++) {
			EntityItem avail = availGrp.getEntityItem(i);
			//R1.0	18.10	WHEN		ANNAVAILA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)	
			//R1.0	18.11	AND			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)				
			if (statusIsRFRorFinal(avail)){
				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
				addDebug("doR10AAOfferings "+avail.getKey()+"  availAnntypeFlag "+availAnntypeFlag);

				//R1.0	18.12	IF		ANNAVAILA: LSEOAVAIL-u	LSEO	STATUS	=	"Ready for Review" (0040)			
				//R1.0	18.13	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)| "Plan" (LF01)			
				//R1.0 	18.14	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
				doRFR_ADSXML(avail, "LSEOAVAIL", "LSEO");
				//R1.0	18.15	END	18.12	

				//R1.0	18.22	IF		ANNAVAILA: MODELAVAIL-u	MODEL	STATUS	=	"Ready for Review" (0040)			
				//R1.0	18.23	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)| "Plan" (LF01)			
				//R1.0 	18.24	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR				
				doRFR_ADSXML(avail, "MODELAVAIL", "MODEL");
				//R1.0	18.25	END	18.22

				//R1.0	18.32	IF		ANNAVAILA: SVCMODAVAIL-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
				//R1.0	18.33	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)| "Plan" (LF01)			
				//R1.0 	18.34	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
				doRFR_ADSXML(avail, "SVCMODAVAIL", "SVCMOD");
				//R1.0	18.35	END	18.32

				//R1.0	18.42	IF		ANNAVAILA: LSEOBUNDLEAVAIL-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)			
				//R1.0 	18.43	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)| "Plan" (LF01)			
				//R1.0 	18.44	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR		
				doRFR_ADSXML(avail, "LSEOBUNDLEAVAIL", "LSEOBUNDLE");
				//R1.0	18.45	END	18.42

				//18.50	R1.0	IF		ANNAVAILA: MODELCONVERTAVAIL-u	MODELCONVERT	STATUS	=	"Ready for Review" (0040)			
				//18.70	R1.0	AND			MODELCONVERT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
				//18.90	R1.0	SET			MODELCONVERT				ADSABRSTATUS	&ADSFEEDRFR	
				doRFR_ADSXML(avail, "MODELCONVERTAVAIL", "MODELCONVERT");
				//19.10	R1.0	END	18.50								

				//19.30	R1.0	IF		ANNAVAILA: OOFAVAIL-u	PRODSTRUCT	STATUS	=	"Ready for Review" (0040)			
				//19.50	R1.0	AND			PRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
				//19.70	R1.0	SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	
				doRFR_ADSXML(avail, "OOFAVAIL", "PRODSTRUCT");
				//19.90	R1.0	END	19.30	

				//20.10	R1.0	IF		ANNAVAILA: SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
				//20.30	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
				//20.40	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	STATUS	=	"Ready for Review" (0040)		
				//20.50	R1.0	SET			"SVCMODSVCSEO-u:SVCMOD"				ADSABRSTATUS	&ADSFEEDRFR
				doRFR_SVCSEO_SVCMODADSXML(avail);
				//20.55	R1.0	END	20.10	

				//20.60	R1.0	IF		ANNAVAILA: SWPRODSTRUCTAVAIL-u	SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)			
				//20.65	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
				//20.70	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	
				doRFR_ADSXML(avail, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");
				//20.75	R1.0	END	20.60	
			}//end avail is rfr or final
		}	// end avail loop	
	}
	/**
	 * Verify the entity STATUS and set the ADSABRSTATUS	
	 */
	private void doRFR_ADSXML(EntityItem avail, String relator, String entityType) {
		Vector entityVct = PokUtils.getAllLinkedEntities(avail, relator, entityType);
		for (int entityCount = 0; entityCount < entityVct.size(); entityCount++) {
			EntityItem ei = (EntityItem) entityVct.elementAt(entityCount);
			doRFR_ADSXML(ei);
		}
		entityVct.clear();
	}
	/**
	 * Verify the entity SVCSEO STATUS and SVCMOD.STATUS set the SVCMOD.ADSABRSTATUS	
	 * 			
20.10	R1.0	IF		ANNAVAILA: SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Ready for Review" (0040)		
20.30	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
20.40	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	STATUS	=	"Ready for Review" (0040)		
20.50	R1.0	SET			"SVCMODSVCSEO-u:SVCMOD"				ADSABRSTATUS	&ADSFEEDRFR
20.55	R1.0	END	20.10							
							
	 */
	private void doRFR_SVCSEO_SVCMODADSXML(EntityItem avail) {
		Vector svcseoVct = PokUtils.getAllLinkedEntities(avail,"SVCSEOAVAIL", "SVCSEO");
		for (int entityCount = 0; entityCount < svcseoVct.size(); entityCount++) {
			EntityItem ei = (EntityItem) svcseoVct.elementAt(entityCount);
			if (statusIsRFR(ei)){		
				//find its SVCMOD
				Vector svcmodVct = PokUtils.getAllLinkedEntities(ei, "SVCMODSVCSEO", "SVCMOD");	
		    	addDebug("doRFR_SVCSEO_SVCMODADSXML "+ei.getKey()+" svcmodVct.size "+svcmodVct.size());
				for (int i2 = 0; i2 < svcmodVct.size(); i2++) {
					EntityItem svcmod = (EntityItem) svcmodVct.elementAt(i2);
					doRFR_ADSXML(svcmod);
				}
				svcmodVct.clear();
			}
		}
		svcseoVct.clear();
	}
	/**
	 * Verify the entity SVCSEO.STATUS=final and set the specified SVCMOD abr attributes	
17.32	R1.0	IF		ANNAVAILA: SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)			
17.33	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	STATUS	=	"Final" (0020)	
17.34	R1.0	SET			"SVCMODSVCSEO-u:SVCMOD"				ADSABRSTATUS
	 * @param avail
	 * @param attrcodes
	 */
	private void verifySVCSEOAndSetSVCMODABRStatus(EntityItem avail) {
		Vector svcseoVct = PokUtils.getAllLinkedEntities(avail, "SVCSEOAVAIL", "SVCSEO");
		for (int entityCount = 0; entityCount < svcseoVct.size(); entityCount++) {
			EntityItem ei = (EntityItem) svcseoVct.elementAt(entityCount);
			//17.32	R1.0	IF		ANNAVAILA: SVCSEOAVAIL-u	SVCSEO	STATUS	=	"Final" (0020)
			if (statusIsFinal(ei)) {
				//find its SVCMOD
				Vector svcmodVct = PokUtils.getAllLinkedEntities(ei, "SVCMODSVCSEO", "SVCMOD");
		    	addDebug("verifySVCSEOAndSetSVCMODABRStatus "+ei.getKey()+" svcmodVct.size "+svcmodVct.size());
				for (int i2 = 0; i2 < svcmodVct.size(); i2++) {
					EntityItem svcmod = (EntityItem) svcmodVct.elementAt(i2);
					//17.33	R1.0	AND			"SVCMODSVCSEO-u:SVCMOD"	STATUS	=	"Final" (0020)
					if (statusIsFinal(svcmod)) { 
						//17.34	R1.0	SET			"SVCMODSVCSEO-u:SVCMOD"				ADSABRSTATUS
						setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(svcmod,"ADSABRSTATUS"), svcmod);
					}
				}
				svcmodVct.clear();
			}
		}
		svcseoVct.clear();
	}

	/**
	 * Verify the entity STATUS=final and set the specified abr attributes	
	 * @param avail
	 * @param relator
	 * @param entityType
	 * @param attrcodes
	 * @throws SQLException 
	 * @throws MiddlewareException 
	 * @throws MiddlewareRequestException 
	 */
	private void verifyStatusAndSetABRStatus(EntityItem avail, String relator, String entityType,
			String[] attrcodes) throws MiddlewareRequestException, MiddlewareException, SQLException {
		Vector entityVct = PokUtils.getAllLinkedEntities(avail, relator, entityType);
		for (int entityCount = 0; entityCount < entityVct.size(); entityCount++) {
			EntityItem ei = (EntityItem) entityVct.elementAt(entityCount);
			if (statusIsFinal(ei)) {
				String entitytype = ei.getEntityType();
				for (int i=0; i<attrcodes.length; i++){					
//12.00					IF		ANNAVAILA: MODELAVAIL-u	MODEL	STATUS	=	"Final" (0020)	ADSABRSTATUS		&ADSFEED
//							SetSinceFinal			MODEL	ADSABRSTATUS	=	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEED
//							SetSinceFinal			MODEL	ADSABRSTATUS	<>	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEEDFINALFIRST
					if("MODEL".equals(entitytype) && "ADSABRSTATUS".equals(attrcodes[i])){
						setSinceFirstFinal(ei, attrcodes[i]);
					}else{
						setFlagValue(m_elist.getProfile(), attrcodes[i], getQueuedValueForItem(ei, attrcodes[i]), ei);
					}
				}
				//12.200	SetSinceFinal			MODEL	RFCABRSTATUS	=	"Passed" (0030)	RFCABRSTATUS		&OIMSFEED
				//12.020	SetSinceFinal			MODEL	RFCABRSTATUS	<>	"Passed" (0030)	RFCABRSTATUS		&OIMSFEED
	 	 	 	// 	MODEL	COFCAT	=	"Hardware(100)	 	 	 	 	 	 
	 	 	 	// 	OR	 	 	 	 	 	 	 	 	 
	 	 	 	// 	MODEL	MACHTYPEATR+MODELATR	IN	5313-HPO and 5372-IS5
				if("MODEL".equals(entitytype) && isHardwareOrHIPOModel(ei)) {
					setRFCSinceFirstFinal(ei, "RFCABRSTATUS");
				}
			}
		}
		entityVct.clear();
	}

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
1	ANNOUNCEMENT		Root								ANNOUNCEMENT NEW	
2	WHEN		ANNTYPE	=	"New" (19)							
3			ANNDATE									
4			COUNTRYLIST									
5			DATAQUALITY	<=	A: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: A: AVAIL} {LD: STATUS} {A: STATUS}
6			DATAQUALITY	<=	B: AVAIL	STATUS		E	E	E		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: B: AVAIL} {LD: STATUS} {B: STATUS}
7	AVAIL	A	ANNAVAILA								AVAIL PA	
8	WHEN		AVAILTYPE	=	"Planned Availability"						Announcement New must have a Planned Availability	
9			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
10			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
11			CountOf	=>	1			RE	RE	RE		must have at least one "Planned Availability"
12			STATUS									
13	END	8										
14	AVAIL	B	ANNAVAILA								AVAIL FO	
15	WHEN		AVAILTYPE	=	"First Order"						Availability First Order is optional and overrides the Announcement ANNDATE	
16			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
17			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
18			STATUS									
19	END	15										
20	AVAIL	E	ANNAVAILA									
21	WHEN		AVAILTYPE	<>	"Planned Availability" | "First Order"						AVAIL <> PA or FO	
22			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT}
23	END	21										
24	END	2										

25	ANNOUNCEMENT		Root								ANNOUNCEMENT EOL	
26	WHEN		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)							
27			ANNDATE									
28			COUNTRYLIST									
29			DATAQUALITY	<=	C: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: C: AVAIL} {LD: STATUS} {C: STATUS}
30			DATAQUALITY	<=	F: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: F: AVAIL} {LD: STATUS} {F: STATUS}
31	AVAIL		ANNAVAILA								AVAIL LO	
32	WHEN	C	AVAILTYPE	=	"Last Order"							
33			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
34			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
35			STATUS									
36	END	32										
37	WHEN	F	AVAILTYPE	=	"End of Marketing" (200)							
38			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
39			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
40			STATUS									
41	END	37										
42	WHEN		AVAILTYPE	<>	"Last Order"| "End of Marketing"						AVAIL <> LO | EOM	
43			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT}
44	END	42										
45	END	26										
																		
46	ANNOUNCEMENT		Root								ANNOUNCEMENT Discontinue Service	
47	WHEN		ANNTYPE	=	"End Of Life - Discontinuance of service" (13)							
48			ANNDATE									
49			COUNTRYLIST									
50			DATAQUALITY	<=	D: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: D: AVAIL} {LD: STATUS} {D: STATUS}
51	AVAIL	D	ANNAVAILA								AVAIL EOS	
52	WHEN		AVAILTYPE	=	"End of Service"							
53			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
54			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
55			STATUS									
56	END	52										
57	WHEN		AVAILTYPE	<>	"End of Service"						AVAIL <> EOS	
58			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT}
59	END	57										
60	END	47											

61	ANNOUNCEMENT		Root									
62	IF		ANNTYPE	<>	"End Of Life - Discontinuance of service" (13)							
63	AND		ANNTYPE	<>	"End Of Life - Withdrawal from mktg" (14)							
64	AND		ANNTYPE	<>	"New" (19)							
65	THEN		CountOf	=	0			E	E	E		this Announcement Type is not supported
66	ANNOUNCEMENT		Root									
67	AVAIL		ANNAVAILA									
68	WHEN		AVAILANNTYPE	<>	"RFA" (RFA)							
69			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
70	END											

	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
		String anntype = getAttributeFlagEnabledValue(rootEntity, "ANNTYPE");
    	EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");						
		//	get the AVAILS
    	Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", LASTORDERAVAIL);
    	Vector mesLastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", MESLASTORDERAVAIL);
    	Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", PLANNEDAVAIL);
    	Vector mesPlannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", MESPLANNEDAVAIL);
    	Vector firstOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", FIRSTORDERAVAIL);
       	Vector eomAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", EOMAVAIL);
       	Vector eosAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", EOSAVAIL);
       	
       	ArrayList annCtry = new ArrayList();
		getAttributeAsList(rootEntity, annCtry, "COUNTRYLIST", getCheck_RW_RW_RE(statusFlag));
		
    	addDebug("doDQChecking "+rootEntity.getKey()+" ANNTYPE "+anntype);
    	// remove any that are not AVAILANNTYPE=RFA so they arent in the other checks
    	// they are truly not valid, they should never have an announcement
    	removeNonRFAAVAIL(lastOrderAvailVct);
    	removeNonRFAAVAIL(mesLastOrderAvailVct);
    	// 1674783: DQ ABR update for an AVAILABRSTATUS.
    	removeNonRFAAndEPICAVAIL(plannedAvailVct);
    	removeNonRFAAndEPICAVAIL(mesPlannedAvailVct);
    	removeNonRFAAVAIL(firstOrderAvailVct);
    	removeNonRFAAVAIL(eomAvailVct);
    	removeNonRFAAVAIL(eosAvailVct);
    	
    	addDebug("doDQChecking  plannedAvailVct: "+plannedAvailVct.size()+
    			" mesPlannedAvailVct: "+mesPlannedAvailVct.size()+
    			" firstOrderAvailVct: "+firstOrderAvailVct.size()+" lastOrderAvailVct: "+lastOrderAvailVct.size()+
    			" mesLastOrderAvailVct: "+mesLastOrderAvailVct.size()+
    			" eomAvailVct: "+eomAvailVct.size()+" eosAvailVct: "+eosAvailVct.size()+
    			" annCtry:"+annCtry);
    	
    	if (ANNTYPE_NEW.equals(anntype)){
    		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" New Checks:");
    		checkNewAnn(rootEntity, plannedAvailVct, mesPlannedAvailVct, firstOrderAvailVct,annCtry,statusFlag);
    	}else if (ANNTYPE_EOL.equals(anntype)){
    		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" End Of Life - Withdrawal from mktg Checks:");
    		checkEOLAnn(rootEntity, eomAvailVct, lastOrderAvailVct, mesLastOrderAvailVct, annCtry,statusFlag);
    	}else if (ANNTYPE_EOLDS.equals(anntype)){
    		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" End Of Life - Discontinuance of service Checks:");
    		checkEOLDSAnn(rootEntity, eosAvailVct, annCtry,statusFlag);
    	}else{
    		//61	ANNOUNCEMENT		Root									
    		//62	IF		ANNTYPE	<>	"End Of Life - Discontinuance of service" (13)							
    		//63	AND		ANNTYPE	<>	"End Of Life - Withdrawal from mktg" (14)							
    		//64	AND		ANNTYPE	<>	"New" (19)							
    		//65	THEN		CountOf	=	0			E	E	E		this Announcement Type is not supported
    		addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" No Checks Done");
    		//ANNTYPE_NOT_SUPPORTED_ERR = thi {0} is not supported
			args[0] = this.getLD_Value(rootEntity, "ANNTYPE");
			createMessage(CHECKLEVEL_E,"ANNTYPE_NOT_SUPPORTED_ERR",args);
    	}
    	
		//66	ANNOUNCEMENT		Root									
		//67	AVAIL		ANNAVAILA									
		//68	WHEN		AVAILANNTYPE	<>	"RFA" (RFA)							
		//69			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
        addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Availability RFA checks:");
    	checkAvailAnnType();
		//70	END	
        
    	annCtry.clear();
    	lastOrderAvailVct.clear();
     	plannedAvailVct.clear();
     	firstOrderAvailVct.clear();
        eomAvailVct.clear();
        eosAvailVct.clear();
    }
    
    /************
     * Do checks when ANNTYPE=New
     * @param rootEntity
     * @param plannedAvailVct
     * @param firstOrderAvailVct
     * @param lastOrderAvailVct
     * @param statusFlag
     * @throws Exception
     * 	
2	WHEN		ANNTYPE	=	"New" (19)							
3			ANNDATE									
4			COUNTRYLIST									
5			DATAQUALITY	<=	A: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: A: AVAIL} {LD: STATUS} {A: STATUS}
6			DATAQUALITY	<=	B: AVAIL	STATUS		E	E	E		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: B: AVAIL} {LD: STATUS} {B: STATUS}
7	AVAIL	A	ANNAVAILA								AVAIL PA	
8	WHEN		AVAILTYPE	=	"Planned Availability"						Announcement New must have a Planned Availability	
9			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL}  must not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
10			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
11			CountOf	=>	1			RE	RE	RE		must have at least one "Planned Availability"
12			STATUS									
13	END	8										
14	AVAIL	B	ANNAVAILA								AVAIL FO	
15	WHEN		AVAILTYPE	=	"First Order"						Availability First Order is optional and overrides the Announcement ANNDATE	
16			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
17			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		W	W	E		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
18			STATUS									
19	END	15										
20	AVAIL	E	ANNAVAILA									
21	WHEN		AVAILTYPE	<>	"Planned Availability" | "First Order"						AVAIL <> PA or FO	
22			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT}
23	END	21										
24	END	2			
     */
    private void checkNewAnn(EntityItem rootEntity, Vector plannedAvailVctA, Vector mesPlannedAvailVctA, Vector firstOrderAvailVctB,
    		ArrayList annCtry,String statusFlag) throws Exception
    {
    	int checklvlWWE = getCheck_W_W_E(statusFlag);
    	int checklvlRWRWRE = getCheck_RW_RW_RE(statusFlag);
    	
		addDebug("checking keys 5, 9, 10");
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Planned Avail Checks:");
		//8	WHEN		AVAILTYPE	=	"Planned Availability"	
		for (int i=0; i<plannedAvailVctA.size(); i++){
			EntityItem plaAvail = (EntityItem)plannedAvailVctA.elementAt(i);
			//5			ANN.DATAQUALITY	<=	A: AVAIL	STATUS		RE	RE	RE		
	    	//{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: A: AVAIL} {LD: STATUS} {A: STATUS}
			checkStatusVsDQ(plaAvail, "STATUS", rootEntity,CHECKLEVEL_RE);
			//9			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		
			//{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
			checkCanNotBeEarlier(plaAvail, "EFFECTIVEDATE", rootEntity, "ANNDATE", checklvlRWRWRE);
			
			//10			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
			checkAvailCtryInEntity(null,plaAvail, rootEntity,annCtry,checklvlRWRWRE);
		}

		addDebug("checking keys 11,20,21");
    	//8	WHEN		AVAILTYPE	=	"Planned Availability"	
		//11			Count of	=>	1			RE	RE	RE		must have at least one "Planned Availability"
		checkPlannedAvailsExist(plannedAvailVctA, CHECKLEVEL_RE); 
		
		//20160126 Add MES Planned Availability Checks
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" MES Planned Avail Checks:");
		//8	WHEN		AVAILTYPE	=	"Planned Availability"	
		for (int i=0; i<mesPlannedAvailVctA.size(); i++){
			EntityItem mesPlaAvail = (EntityItem)mesPlannedAvailVctA.elementAt(i);
			//5			ANN.DATAQUALITY	<=	A: AVAIL	STATUS		RE	RE	RE		
	    	//{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: A: AVAIL} {LD: STATUS} {A: STATUS}
			checkStatusVsDQ(mesPlaAvail, "STATUS", rootEntity,CHECKLEVEL_RE);
			//9			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		
			//{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
			checkCanNotBeEarlier(mesPlaAvail, "EFFECTIVEDATE", rootEntity, "ANNDATE", checklvlRWRWRE);
			
			//10			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
			checkAvailCtryInEntity(null,mesPlaAvail, rootEntity,annCtry,checklvlRWRWRE);
		}
		
		addDebug("checking keys 6,16,17");
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" First Order Avail Checks:");
		//15	WHEN		AVAILTYPE	=	"First Order"
		for (int i=0; i<firstOrderAvailVctB.size(); i++){
			EntityItem foAvail = (EntityItem)firstOrderAvailVctB.elementAt(i);
			//6			DATAQUALITY	<=	B: AVAIL	STATUS		E	E	E		
			//{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: B: AVAIL} {LD: STATUS} {B: STATUS}	
			checkStatusVsDQ(foAvail, "STATUS", rootEntity,CHECKLEVEL_E);
			
			//16	EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		W	W	E		
			//{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
			checkCanNotBeEarlier(foAvail, "EFFECTIVEDATE", rootEntity, "ANNDATE", checklvlWWE);
			
			//17			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		W	W	E		
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
    		checkAvailCtryInEntity(null,foAvail, rootEntity,annCtry,checklvlWWE); 
		}

		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Other Avail Checks:");
		//21	WHEN		AVAILTYPE	<>	"Planned Availability" | "First Order"	
		//22			Count of	=	0			E	E	E		Vector plannedAvailVctA, {LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT}
    	EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");						
		//	look at all AVAILS
		for (int i=0; i<availGrp.getEntityItemCount(); i++){
			EntityItem avail = availGrp.getEntityItem(i);
	    	String availType = PokUtils.getAttributeFlagValue(avail, "AVAILTYPE");
	    	addDebug(avail.getKey()+" availtype "+availType);
	    	if (PLANNEDAVAIL.equals(availType) || MESPLANNEDAVAIL.equals(availType) || FIRSTORDERAVAIL.equals(availType)){
    			continue;
    		}
			//22			Count of	=	0			E	E	E		
			//	{LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT}
			//CANNOT_BE_IN_ERR = {0} can not be in this {1}
			args[0] = getLD_NDN(avail);
			args[1] = rootEntity.getEntityGroup().getLongDescription();
			createMessage(CHECKLEVEL_E,"CANNOT_BE_IN_ERR",args);
		}
		//23	END	21
    }
    /****************
     * do checks when ANNTYPE=EOL
     * @param rootEntity
     * @param plannedAvailVct
     * @param firstOrderAvailVct
     * @param lastOrderAvailVct
     * @param statusFlag
     * @throws Exception
     * 	
25	ANNOUNCEMENT		Root								ANNOUNCEMENT EOL	
26	WHEN		ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)							
27			ANNDATE									
28			COUNTRYLIST									
29			DATAQUALITY	<=	C: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: C: AVAIL} {LD: STATUS} {C: STATUS}
30			DATAQUALITY	<=	F: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: F: AVAIL} {LD: STATUS} {F: STATUS}
31	AVAIL		ANNAVAILA								AVAIL LO	
32	WHEN	C	AVAILTYPE	=	"Last Order"							
33			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
34			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
35			STATUS									
36	END	32										
37	WHEN	F	AVAILTYPE	=	"End of Marketing" (200)							
38			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
39			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
40			STATUS									
41	END	37										
42	WHEN		AVAILTYPE	<>	"Last Order"| "End of Marketing"						AVAIL <> LO | EOM	
43			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT}
44	END	42										
45	END	26			
     */
    private void checkEOLAnn(EntityItem rootEntity, Vector eomAvailVctE,
    		Vector lastOrderAvailVctC, Vector mesLastOrderAvailVctC, ArrayList annCtry, String statusFlag) throws Exception
    {
    	int checklvlRWRWRE = getCheck_RW_RW_RE(statusFlag);
    
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Last Order Avail Checks:");
		//32	WHEN	C	AVAILTYPE	=	"Last Order"
		for (int i=0; i<lastOrderAvailVctC.size(); i++){
			EntityItem loAvail = (EntityItem)lastOrderAvailVctC.elementAt(i);
			//29			DATAQUALITY	<=	C: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: C: AVAIL} {LD: STATUS} {C: STATUS}
			checkStatusVsDQ(loAvail, "STATUS", rootEntity,CHECKLEVEL_RE);
			//33			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		
			//{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
			checkCanNotBeEarlier(loAvail, "EFFECTIVEDATE", rootEntity, "ANNDATE", checklvlRWRWRE);
			
			//34			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
			checkAvailCtryInEntity(null,loAvail, rootEntity,annCtry,checklvlRWRWRE); 
		}
		//36	END	32
		
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" MES Last Order Avail Checks:");
		//32	WHEN	C	AVAILTYPE	=	"Last Order"
		for (int i=0; i<mesLastOrderAvailVctC.size(); i++){
			EntityItem mesLoAvail = (EntityItem)mesLastOrderAvailVctC.elementAt(i);
			//29			DATAQUALITY	<=	C: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: C: AVAIL} {LD: STATUS} {C: STATUS}
			checkStatusVsDQ(mesLoAvail, "STATUS", rootEntity,CHECKLEVEL_RE);
			//33			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		
			//{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
			checkCanNotBeEarlier(mesLoAvail, "EFFECTIVEDATE", rootEntity, "ANNDATE", checklvlRWRWRE);
			
			//34			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
			checkAvailCtryInEntity(null,mesLoAvail, rootEntity,annCtry,checklvlRWRWRE); 
		}
		//36	END	32

		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" End of Marketing Avail Checks:");
		//37	WHEN	F	AVAILTYPE	=	"End of Marketing" (200)	
		for (int i=0; i<eomAvailVctE.size(); i++){
			EntityItem eomAvail = (EntityItem)eomAvailVctE.elementAt(i);
			//30			DATAQUALITY	<=	F: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: F: AVAIL} {LD: STATUS} {F: STATUS}
			checkStatusVsDQ(eomAvail, "STATUS", rootEntity,CHECKLEVEL_RE);
			
			//38			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		
			//{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
			checkCanNotBeEarlier(eomAvail, "EFFECTIVEDATE", rootEntity, "ANNDATE", checklvlRWRWRE);
			//39			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
			checkAvailCtryInEntity(null,eomAvail, rootEntity,annCtry,checklvlRWRWRE); 
		}
		//41	END	37										
		
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Other Avail Checks:");
		addDebug("checking keys 36,39 ");
		//42	WHEN		AVAILTYPE	<>	"Last Order"	| "End of Marketing"			AVAIL <> LO | EOM	
		//43			Count of	=	0			E	E	E  LD: AVAIL} {NDN: AVAIL}  can not in this {LD: ANNOUNCEMENT}
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");						
		//	look at all AVAILS
		for (int i=0; i<availGrp.getEntityItemCount(); i++){
			EntityItem avail = availGrp.getEntityItem(i);
	    	String availType = PokUtils.getAttributeFlagValue(avail, "AVAILTYPE");
	    	addDebug(avail.getKey()+" availtype "+availType);
	    	if (EOMAVAIL.equals(availType) || LASTORDERAVAIL.equals(availType) || MESLASTORDERAVAIL.equals(availType)){
    			continue;
    		}

			//CANNOT_BE_IN_ERR = {0} can not be in this {1}
			args[0] = getLD_NDN(avail);
			args[1] = rootEntity.getEntityGroup().getLongDescription();
			createMessage(CHECKLEVEL_E,"CANNOT_BE_IN_ERR",args);
		}
		//44	END	42
		/* deleted
		//38	WHEN		AVAILTYPE	=	"First Order"							
		//39			Count of	=	0			E	E	E	{LD: AVAIL} {NDN: AVAIL}  can not in this {LD: ANNOUNCEMENT}
		for (int i=0; i<firstOrderAvailVctB.size(); i++){
			EntityItem foAvail = (EntityItem)firstOrderAvailVctB.elementAt(i);
			//CANNOT_BE_IN_ERR = {0} can not be in this {1}
			args[0] = getLD_NDN(foAvail);
			args[1] = rootEntity.getEntityGroup().getLongDescription();
			createMessage(CHECKLEVEL_E,"CANNOT_BE_IN_ERR",args);
		}
		//40	END	38	
		 */ 
    }
     
    /**
     * @param rootEntity
     * @param eosAvailVctD
     * @param annCtry
     * @param statusFlag
     * @throws Exception
     * 
46	ANNOUNCEMENT		Root								ANNOUNCEMENT Discontinue Service	
47	WHEN		ANNTYPE	=	"End Of Life - Discontinuance of service" (13)							
48			ANNDATE									
49			COUNTRYLIST									
50			DATAQUALITY	<=	D: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: D: AVAIL} {LD: STATUS} {D: STATUS}
51	AVAIL	D	ANNAVAILA								AVAIL EOS	
52	WHEN		AVAILTYPE	=	"End of Service"							
53			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
54			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
55			STATUS									
56	END	52										
57	WHEN		AVAILTYPE	<>	"End of Service"						AVAIL <> EOS	
58			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT}
59	END	57										
60	END	47 
     */
    private void checkEOLDSAnn(EntityItem rootEntity, Vector eosAvailVctD,
    		 ArrayList annCtry, String statusFlag) throws Exception
    {
    	int checklvlRWRWRE = getCheck_RW_RW_RE(statusFlag);
    	
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" End of Service Avail Checks:");	
		//52	WHEN		AVAILTYPE	=	"End of Service"										
		for (int i=0; i<eosAvailVctD.size(); i++){
			EntityItem eosAvail = (EntityItem)eosAvailVctD.elementAt(i);
			//50			DATAQUALITY	<=	D: AVAIL	STATUS		RE	RE	RE		{LD: STATUS} can not be higher than the {LD: AVAIL} {NDN: D: AVAIL} {LD: STATUS} {D: STATUS}
			checkStatusVsDQ(eosAvail, "STATUS", rootEntity,CHECKLEVEL_RE);
			
			//53			EFFECTIVEDATE	=>	ANNOUNCEMENT	ANNDATE		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
			checkCanNotBeEarlier(eosAvail, "EFFECTIVEDATE", rootEntity, "ANNDATE", checklvlRWRWRE);
			
			//54			COUNTRYLIST	IN	ANNOUNCEMENT	COUNTRYLIST		RW	RW	RE		{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a Country that is not in the {LD: ANNOUNCEMENT} {LD: COUNTRYLIST}
			checkAvailCtryInEntity(null,eosAvail, rootEntity,annCtry,checklvlRWRWRE); 
		}
		//56	END	52											
		
		addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Other Avail Checks:");
		//57	WHEN		AVAILTYPE	<>	"End of Service"						AVAIL <> EOS	
		//58			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: AVAIL}  can not be in this {LD: ANNOUNCEMENT}
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");						
		//	look at all AVAILS
		for (int i=0; i<availGrp.getEntityItemCount(); i++){
			EntityItem avail = availGrp.getEntityItem(i);
	    	String availType = PokUtils.getAttributeFlagValue(avail, "AVAILTYPE");
	    	addDebug(avail.getKey()+" availtype "+availType);
	    	if (EOSAVAIL.equals(availType) || EODAVAIL.equals(availType)){
    			continue;
    		}

			//CANNOT_BE_IN_ERR = {0} can not be in this {1}
			args[0] = getLD_NDN(avail);
			args[1] = rootEntity.getEntityGroup().getLongDescription();
			createMessage(CHECKLEVEL_E,"CANNOT_BE_IN_ERR",args);
		}
		//59	END	57										
		//60	END	47
    }
	/**********************************
	* class has a different status attribute
	*/
	protected String getStatusAttrCode() { return "ANNSTATUS";}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "ANNOUNCEMENT ABR.";
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
