// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2009,2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg.bh;


import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
* FCTRANSABRSTATUS class
*
*from BH FS ABR Data Qualtity Checks 20110318.xls
*remove avail checks - no VE needed now
*
*From "SG FS ABR Data Quality 20101026.doc"
*sets updates
*
* From "SG FS ABR Data Quality 20010914.doc"
*
*need workflow actions 	- WFLCFCTRNRFR and WFLCFCTRNFINAL
*needs ADSABRSTATUS and LIFECYCLE attrs
*
A.	Checking

Note:  Key 28 through Key 34 are NOT to be implemented at this time. It has not been determined that 
these checks are valid.

Key 22 and 28 find the �TO� TMF (PRODSTRUCT) Availability (AVAIL) of interest by:
1.	Key 22 � SEARCH PRODSTRUCT
Use a SEARCH action to find a PRODSTRUCT using the three attributes shown from FCTRANSACTION.
2.	Key 23 � traverse down the relator OOFAVAIL


Data Quality checks are not required

*
FCTRANABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.FCTRANSABRSTATUS
FCTRANABRSTATUS_enabled=true
FCTRANABRSTATUS_idler_class=A
FCTRANABRSTATUS_keepfile=true
FCTRANABRSTATUS_read_only=true
FCTRANABRSTATUS_report_type=DGTYPE01
FCTRANABRSTATUS_vename=DQVEFCTRANSACTION
FCTRANABRSTATUS_CAT1=RPTCLASS.FCTRANABRSTATUS
FCTRANABRSTATUS_CAT2=
FCTRANABRSTATUS_CAT3=RPTSTATUS
FCTRANABRSTATUS_domains=0010,0020,0030,0040,0050,0150,0160,0170,0190,0200,0210,0220,0230,0240,0250,0260,0270,0280,0290,0300,0310,0320,0330,0340,0350,0360,0370,0520,530,540,SG,550,PDIV49,PDIV71,PPWRS,PRSSS,PSANMOHE,PSANMOLE,PSTGV,PSWIDLU,PSSDS,PSQS

*/
// FCTRANSABRSTATUS.java,v
// Revision 1.8  2010/01/21 14:25:52  wendy
// update cmts
//
// Revision 1.6  2009/12/21 00:54:31  wendy
// Updated to handle old data
//
// Revision 1.5  2009/11/04 15:08:07  wendy
// BH Configurable Services - spec chgs
//
// Revision 1.4  2009/08/18 18:00:15  wendy
// SR10, 11, 12, 15, 17 BH updates
//
// Revision 1.3  2009/08/17 15:35:46  wendy
// Added headings
//
// Revision 1.2  2009/08/15 01:41:50  wendy
// SR10, 11, 12, 15, 17 BH updates phase 4
//
// Revision 1.1  2009/07/30 18:54:36  wendy
// Moved to new pkg for BH SR10, 11, 12, 15, 17
//
public class FCTRANSABRSTATUS extends DQABRSTATUS
{
	//private static final String PS_SRCHACTION_NAME = "LDSRDPRODSTRUCT";//"SRDPRODSTRUCT03";// wont accept model attr->"SRDPRODSTRUCT01";
    private static final String CHECK_ANNDATE = "2012-01-01";
    
    /**********************************
    * not needed now, no AVAIL checks
    */
    protected boolean isVEneeded(String statusFlag) {
        return false;
    }
/*
 * from sets ss
56.20	R1.0	IF			FCTRANSACTION	ANNDATE	>	"2012-01-01"			
56.22	R1.0	IF			FCTRANSACTION	STATUS	=	"Ready for Review" (0040)			
56.24	R1.0	AND			FCTRANSACTION	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
56.26	R1.0	SET			FCTRANSACTION				ADSABRSTATUS	&ADSFEEDRFR	
56.28	R1.0	END	56.22								
56.30	R1.0	IF			FCTRANSACTION	STATUS	=	"Final" (0020)			
56.32	R1.0	SET			FCTRANSACTION				ADSABRSTATUS		&ADSFEED
56.34	R1.0	END	56.30								
56.40	R1.0	ELSE	56.20								
56.60	R1.0	SET			FCTRANSACTION				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
56.80	R1.0	END	56.20
 */
    /**********************************
     * complete abr processing after status moved to readyForReview; (status was chgreq)
     * C.	Status changed to Ready for Review
56.20	R1.0	IF			FCTRANSACTION	ANNDATE	>	"2012-01-01"			
56.22	R1.0	IF			FCTRANSACTION	STATUS	=	"Ready for Review" (0040)			
56.24	R1.0	AND			FCTRANSACTION	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
56.26	R1.0	SET			FCTRANSACTION				ADSABRSTATUS	&ADSFEEDRFR	
56.28	R1.0	END	56.22								
...								
56.40	R1.0	ELSE	56.20								
56.60	R1.0	SET			FCTRANSACTION				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
56.80	R1.0	END	56.20								
57.00		END	55.00	FCTRANSACTION							
     */
    protected void completeNowR4RProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		if(doR10processing()){
			EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);
			//56.20	R1.0	IF			FCTRANSACTION	ANNDATE	>	"2012-01-01"	
			String annDate = PokUtils.getAttributeValue(rootItem, "ANNDATE", "", "", false);
			addDebug("nowRFR: "+rootItem.getKey()+" annDate "+annDate);
			if(annDate.length()>0 && annDate.compareTo(CHECK_ANNDATE)>0){
				addDebug("nowRFR: "+rootItem.getKey()+" is after "+CHECK_ANNDATE);
				//56.22	R1.0	IF			FCTRANSACTION	STATUS	=	"Ready for Review" (0040)			
				//56.24	R1.0	AND			FCTRANSACTION	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)	
				//56.26	R1.0	SET			FCTRANSACTION				ADSABRSTATUS	&ADSFEEDRFR	
				doRFR_ADSXML(rootItem); 
				//56.28	R1.0	END	56.22									
			}else{
				//56.40	R1.0	ELSE	56.20								
				//56.60	R1.0	SET			FCTRANSACTION				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
				//56.80	R1.0	END	56.20	
			}			
			//57.00		END	55.00	FCTRANSACTION							
		}
    }

    /**********************************
    * complete abr processing after status moved to final; (status was r4r)
    *C. STATUS changed to Final
    *
56.20	R1.0	IF			FCTRANSACTION	ANNDATE	>	"2012-01-01"			
...								
56.30	R1.0	IF			FCTRANSACTION	STATUS	=	"Final" (0020)			
56.32	R1.0	SET			FCTRANSACTION				ADSABRSTATUS		&ADSFEED
56.34	R1.0	END	56.30								
56.40	R1.0	ELSE	56.20								
56.60	R1.0	SET			FCTRANSACTION				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
56.80	R1.0	END	56.20								
57.00		END	55.00	FCTRANSACTION							
     */
    protected void completeNowFinalProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	if(doR10processing()){
    		//this looks likea noop.. both set it
    		/*EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);
    		//56.20	R1.0	IF			FCTRANSACTION	ANNDATE	>	"2012-01-01"	
    		String annDate = PokUtils.getAttributeValue(rootItem, "ANNDATE", "", "", false);
    		addDebug("nowFinal: "+rootItem.getKey()+" annDate "+annDate);
    		if(annDate.length()>0 && annDate.compareTo(CHECK_ANNDATE)>0){
    			addDebug("nowFinal: "+rootItem.getKey()+" is after "+CHECK_ANNDATE);
    			//56.30	R1.0	IF			FCTRANSACTION	STATUS	=	"Final" (0020)			
    			//56.32	R1.0	SET			FCTRANSACTION				ADSABRSTATUS		&ADSFEED
    			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
    			//56.34	R1.0	END	56.30	
    		}else{
    			//56.40	R1.0	ELSE	56.20								
    			//56.60	R1.0	SET			FCTRANSACTION				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
    			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
    			//56.80	R1.0	END	56.20	
    		}		*/	
    		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
    		//57.00		END	55.00	FCTRANSACTION			
    		//56.300	IF			FCTRANSACTION	STATUS	=	"Final" (0020)
    		//56.320	SET			FCTRANSACTION				RFCABRSTATUS		&OIMSFEED
    		//56.340	END	56.300
    		//56.400	ELSE	56.200
    		//56.600	SET			FCTRANSACTION				RFCABRSTATUS	&ADSFEEDRFR	&OIMSFEED
    		setFlagValue(m_elist.getProfile(),"RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
    	}
    }

	/* (non-Javadoc)
	 * update LIFECYCLE value when STATUS is updated
	 * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
	 */
	protected String getLCRFRWFName(){ return "WFLCFCTRNRFR";} 
	protected String getLCFinalWFName(){ return "WFLCFCTRNFINAL";}

    /**********************************
    * Note the ABR is only called when
    * DATAQUALITY transitions from 'Draft to Ready for Review',
    *   'Change Request to Ready for Review' and from 'Ready for Review to Final'
    *   
checks from ss
1	FCTRANSACTION		Root									
2			ANNDATE									
3			GENAVAILDATE	=>	ANNDATE			W	W	E	{LD: GENAVAILDATE} {GENAVAILDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
4			WITHDRAWDATE									
5			WTHDRWEFFCTVDATE	=>	WITHDRAWDATE			W	W	E	{LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE} can not be earlier than {LD: WITHDRAWDATE} {WITHDRAWDATE}
Delete 20110318 6	AVAIL	A	FEATURETRNAVAIL-d								AVAIL PA	
Delete 20110318 7	WHEN		AVAILTYPE	=	"Planned Availability"							
Delete 20110318 8			CountOf	=>	1			RE*2	RE*2	RE*2  must have at lease one "Planned Availability"
Delete 20110318 9			EFFECTIVEDATE	=>	FCTRANSACTION	GENAVAILDATE		W	W	E	{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FCTRANSACTION} {LD: GENAVAILDATE} {GENAVAILDATE}
Delete 20110318 10			COUNTRYLIST									
Delete 20110318 11	END	7										
Delete 20110318 12	AVAIL	B	FEATURETRNAVAIL-d								AVAIL FO	
Delete 20110318 13	WHEN		AVAILTYPE	=	"First Order							
Delete 20110318 14			EFFECTIVEDATE	=>	FCTRANSACTION	GENAVAILDATE		W	W	E	{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
Delete 20110318 15			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E	{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Delete 20110318 16	END	13										
Delete 20110318 17	AVAIL	C	FEATURETRNAVAIL-d								AVAIL LO	
Delete 20110318 18	WHEN		AVAILTYPE	=	"Last Order"							
Delete 20110318 19			EFFECTIVEDATE	<=	FCTRANSACTION	WTHDRWEFFCTVDATE		W	W	E*2 {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FCTRANSACTION} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
Delete 20110318 20			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E*2 {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Delete 20110318 21	END	18										
Delete 20110318 22	AVAIL	F	SEARCH PRODSTRUCT	TOMACHTYPE + TOMODEL + TOFEATURECODE							TO TMF AVAIL PA	
Delete 20110318 23	NEXT		OOFAVAIL-d									
Delete 20110318 24	WHEN		AVAILTYPE	=	"Planned Availability"							
Delete 20110318 25			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E*2	{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {NDN: F: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
Delete 20110318 26			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	W	E*2	{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that is not in {LD: PRODSTRUCT} {NDN: F: PRODSTRUCT} {LD: AVAIL} 
Delete 20110318 27	END	24										

    */
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
    	addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");
    	int checklvl = getCheck_W_W_E(statusFlag); 
    	
    	//3			GENAVAILDATE	=>	ANNDATE			W	W	E		
    	//{LD: GENAVAILDATE} {GENAVAILDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
    	checkCanNotBeEarlier(rootEntity, "GENAVAILDATE", "ANNDATE", checklvl);									
    	//5			WTHDRWEFFCTVDATE	=>	WITHDRAWDATE			W	W	E		
    	//{LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE} can not be earlier than {LD: WITHDRAWDATE} {WITHDRAWDATE}
    	checkCanNotBeEarlier(rootEntity, "WTHDRWEFFCTVDATE", "WITHDRAWDATE", checklvl);	
	
    	//get all AVAILS
	/*Delete 20110318 	EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		
    	Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", PLANNEDAVAIL);
		Hashtable plAvailCtryTbl = getAvailByCountry(plannedAvailVct, checklvl);
    	addDebug("doDQChecking plannedAvailVct "+plannedAvailVct.size()+" plAvailCtryTbl: "+plAvailCtryTbl);
		
		checkAvails(rootEntity,plannedAvailVct, plAvailCtryTbl,	statusFlag);
		
		checkTMFAvails(rootEntity,plannedAvailVct, plAvailCtryTbl,checklvl);
				
        plannedAvailVct.clear();
        plAvailCtryTbl.clear();
        */
    }

    /***********************************************
     * @param rootEntity
     * @param plannedAvailVct
     * @param plAvailCtryTbl
     * @param statusFlag
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *
6	AVAIL	A	FEATURETRNAVAIL-d								AVAIL PA	
7	WHEN		AVAILTYPE	=	"Planned Availability"							
8			CountOf	=>	1			RE*2	RE*2	RE*2  must have at lease one "Planned Availability"
9			EFFECTIVEDATE	=>	FCTRANSACTION	GENAVAILDATE		W	W	E		
{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FCTRANSACTION} {LD: GENAVAILDATE} {GENAVAILDATE}
10			COUNTRYLIST									
11	END	7										
12	AVAIL	B	FEATURETRNAVAIL-d								AVAIL FO	
13	WHEN		AVAILTYPE	=	"First Order							
14			EFFECTIVEDATE	=>	FCTRANSACTION	GENAVAILDATE		W	W	E		
{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
15			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E		
{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
16	END	13										
17	AVAIL	C	FEATURETRNAVAIL-d								AVAIL LO	
18	WHEN		AVAILTYPE	=	"Last Order"							
19			EFFECTIVEDATE	<=	FCTRANSACTION	WTHDRWEFFCTVDATE		W	W	E*2 {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FCTRANSACTION} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
20			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E*2 {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
21	END	18	

	 * /
	private void checkAvails(EntityItem rootEntity,Vector plannedAvailVct, Hashtable plAvailCtryTbl,
			String statusFlag) 
		throws java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		int checklvl = getCheck_W_W_E(statusFlag); 
		
    	//get all AVAILS
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		addHeading(3,availGrp.getLongDescription()+" Planned Avail Checks:");
		
		//7	WHEN		AVAILTYPE	=	"Planned Availability"							
		//8			CountOf	=>	1			RE*2	RE*2	RE*2	must have at lease one "Planned Availability"
		checkPlannedAvailsExist(plannedAvailVct, getCheckLevel(CHECKLEVEL_RE,rootEntity,"ANNDATE"));
	
		//9			EFFECTIVEDATE	=>	FCTRANSACTION	GENAVAILDATE		W	W	E		
		//{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FCTRANSACTION} {LD: GENAVAILDATE} {GENAVAILDATE}
		for (int i=0; i<plannedAvailVct.size(); i++){
			EntityItem avail = (EntityItem)plannedAvailVct.elementAt(i);
			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "GENAVAILDATE", checklvl);
		}

		Vector firstOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", FIRSTORDERAVAIL);
		addDebug("checkAvails firstOrderAvailVct "+firstOrderAvailVct.size());

		addHeading(3,availGrp.getLongDescription()+" First Order Avail Checks:");
		//12	AVAIL	B	FEATURETRNAVAIL-d								AVAIL FO	
		//13	WHEN		AVAILTYPE	=	"First Order							
		for (int i=0; i<firstOrderAvailVct.size(); i++){
			//14			EFFECTIVEDATE	=>	FCTRANSACTION	GENAVAILDATE		W	W	E		
			//{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FCTRANSACTION} {LD: GENAVAILDATE} {GENAVAILDATE}
			EntityItem avail = (EntityItem)firstOrderAvailVct.elementAt(i);
			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "GENAVAILDATE", checklvl);

			//15			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E
			checkPlannedAvailForCtryExists(avail, plAvailCtryTbl.keySet(), checklvl);
		}
		
		Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", LASTORDERAVAIL);
		addDebug("checkAvails lastOrderAvailVct "+lastOrderAvailVct.size());
		addHeading(3,availGrp.getLongDescription()+" Last Order Avail Checks:");
		//18	WHEN		AVAILTYPE	=	"Last Order"	
		int oldDataChklvl = getCheckLevel(checklvl,rootEntity,"ANNDATE");
		for (int i=0; i<lastOrderAvailVct.size(); i++){
			//19			EFFECTIVEDATE	<=	FCTRANSACTION	WTHDRWEFFCTVDATE		W	W	E*2		
			//{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FCTRANSACTION} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
			EntityItem avail = (EntityItem)lastOrderAvailVct.elementAt(i);
			checkCanNotBeLater(avail, "EFFECTIVEDATE", rootEntity, "WTHDRWEFFCTVDATE", oldDataChklvl);

			// 20			COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		W	W	E*2	
			checkPlannedAvailForCtryExists(avail, plAvailCtryTbl.keySet(),oldDataChklvl);
		}

		lastOrderAvailVct.clear();
	   	firstOrderAvailVct.clear();
	}

	/**
	 * @param rootEntity
	 * @param plannedAvailVct
	 * @param plAvailCtryTbl
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * 
22	AVAIL	F	SEARCH PRODSTRUCT	TOMACHTYPE + TOMODEL + TOFEATURECODE							TO TMF AVAIL PA	
23	NEXT		OOFAVAIL-d									
24	WHEN		AVAILTYPE	=	"Planned Availability"							
25			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E*2	{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {NDN: F: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
26			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	W	E*2	{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that is not in {LD: PRODSTRUCT} {NDN: F: PRODSTRUCT} {LD: AVAIL} 
27	END	24										
28	AVAIL		SEARCH PRODSTRUCT	TOMACHTYPE + TOMODEL + TOFEATURECODE							TO TMF AVAIL LO	Do NOT implement this yet
29	NEXT		OOFAVAIL-d												Do NOT implement this yet
30	WHEN		AVAILTYPE	=	"Last Order"							Do NOT implement this yet
31	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST					Do NOT implement this yet
32	THEN	TheMatch	IN	C:AVAIL	COUNTRYLIST		W	W	E			Do NOT implement this yet
33	AND		EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	Yes	W	W	E	Do NOT implement this yet
34	END	30																Do NOT implement this yet
	 * /
	private void checkTMFAvails(EntityItem rootEntity,Vector plannedAvailVctA, Hashtable plAvailCtryTblA,
			int checklvl) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		EntityItem psItem = searchForProdstruct(rootEntity); 
		if (psItem != null){
			// pull extract 
			EntityList psList = m_db.getEntityList(m_prof, 
					new ExtractActionItem(null, m_db, m_prof, "DQVEOOFAVAIL"), 
					new EntityItem[]{psItem});
			addDebug("checkTMFAvails VE:DQVEOOFAVAIL\n"+PokUtils.outputList(psList));

			//get all AVAILS
			EntityGroup availGrp = psList.getEntityGroup("AVAIL");
			
			addHeading(3,psItem.getEntityGroup().getLongDescription()+" Planned Avail Checks:");
			Vector psPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", PLANNEDAVAIL);
			
			int oldDataChklvl = getCheckLevel(checklvl,rootEntity,"ANNDATE");
			//24	WHEN		AVAILTYPE	=	"Planned Availability"							
			//25			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	W	E*2	{LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {NDN: F: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
			//26			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		W	W	E*2	{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that is not in {LD: PRODSTRUCT} {NDN: F: PRODSTRUCT} {LD: AVAIL} 
			checkPsPlaAvails(psPlaAvailVct, plannedAvailVctA, plAvailCtryTblA, "OOFAVAIL",
					oldDataChklvl,oldDataChklvl);
			//27	END	24		

			psPlaAvailVct.clear();
			psList.dereference();
		}
	}*/
	/**
	 * @param rootEntity
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * 
	 * SEARCH PRODSTRUCT	TOMACHTYPE + TOMODEL + TOFEATURECODE
	 * /
	private EntityItem searchForProdstruct(EntityItem rootEntity) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{		
		EntityItem psItem = null;
		Vector attrVct = new Vector(3);
		attrVct.addElement("MODEL:MACHTYPEATR");
		attrVct.addElement("MODEL:MODELATR");
		attrVct.addElement("FEATURE:FEATURECODE");
		
		Vector valVct = new Vector(2);
		valVct.addElement(PokUtils.getAttributeValue(rootEntity, "TOMACHTYPE", "", "", false));
		valVct.addElement(PokUtils.getAttributeValue(rootEntity, "TOMODEL", "", "", false));
		valVct.addElement(PokUtils.getAttributeValue(rootEntity, "TOFEATURECODE", "", "", false));

		addDebug("searchForProdstruct attrVct "+attrVct+" valVct "+valVct);

		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			eia= ABRUtil.doSearch(getDatabase(), m_prof, 
					PS_SRCHACTION_NAME, "PRODSTRUCT", true, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug("searchForProdstruct SBRException: "+exBuf.getBuffer().toString());
		}
		if (eia!=null && eia.length > 0){			
			for (int i=0; i<eia.length; i++){
				addDebug("searchForProdstruct found "+eia[i].getKey());
			}
			if (eia.length>1){
				StringBuffer sb = new StringBuffer();
				sb.append("More than one PRODSTRUCT found for "+valVct);
				for (int i=0; i<eia.length; i++){
					sb.append("<br />"+eia[i].getKey()+":"+eia[i]);
				}
				addError(sb.toString(),null);
			}
			psItem = eia[0];
		}
		
		attrVct.clear();
		valVct.clear();
		return psItem;
	}*/

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
        String desc =  "FCTRANSACTION ABR.";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.8";
    }
}
