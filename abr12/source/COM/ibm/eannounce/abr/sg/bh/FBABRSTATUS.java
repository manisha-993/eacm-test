//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2010  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* FBABRSTATUS class
*
* BH FS ABR Data Quality 20111017.doc
* When this ABR is invoked and STATUS = DATAQUALITY = “Final”, then perform the actions defined in the SETs spreadsheet.
*
*BH FS ABR Data Quality 20110322.doc
*Delete 20110323	No XML for FB sets 53.80-53.92
* From "BH FS ABR Data Quality 20101201b.doc"
*
*need workflow actions - WFLCFBRFR and WFLCFBFINAL
*need VE DQVEFB
*needs DQ and FBABRSTATUS and LIFECYCLE and ADSABRSTATUS attrs
*
*
FBABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.FBABRSTATUS
FBABRSTATUS_enabled=true
FBABRSTATUS_idler_class=A
FBABRSTATUS_keepfile=true
FBABRSTATUS_report_type=DGTYPE01
FBABRSTATUS_vename=DQVEFB
FBABRSTATUS_CAT1=RPTCLASS.FBABRSTATUS
FBABRSTATUS_CAT2=
FBABRSTATUS_CAT3=RPTSTATUS
*/
//$Log: FBABRSTATUS.java,v $
//Revision 1.3  2011/10/18 15:32:33  wendy
//BH FS ABR Data Quality 20111017.doc updates
//
//Revision 1.2  2011/04/07 19:47:23  wendy
//Remove queue ADS on root
//
//Revision 1.1  2010/12/01 21:51:56  wendy
//BH FS ABR Data Quality 20101201b.doc updates
//
public class FBABRSTATUS extends DQABRSTATUS
{

	/**********************************
	 * nothing needed
	 */
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/**********************************
	 * Note the ABR is only called when
	 * DATAQUALITY transitions from 'Draft to Ready for Review',
	 *	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	 */
	protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
	{
		addDebug("No checking required");
	}

/*
 * 
 from sets ss:
 53.00	R1.0	FB		Root Entity						
53.20	R1.0			LSEOBUNDLEFB-u	LSEOBUNDLE					
53.22	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
53.24	R1.0	AND			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
53.26	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
53.28	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
53.30	R1.0	ELSE	53.22							
53.32	R1.0	IF			FB	STATUS	=	"Final" (0020)		
53.34	R1.0	AND			LSEOBUNDLE	STATUS	=	"Final" (0020)		
53.36	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	
53.38	R1.0	END	53.22							
53.40	R1.0			MODELFB-u	MODEL					
53.42	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
53.44	R1.0	AND			MODEL	STATUS	=	"Ready for Review" (0040)		
53.46	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
53.48	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
53.50	R1.0	ELSE	53.42							
53.52	R1.0	IF			FB	STATUS	=	"Final" (0020)		
53.54	R1.0	AND			MODEL	STATUS	=	"Final" (0020)		
53.56	R1.0	SET			MODEL				ADSABRSTATUS	
53.58	R1.0	END	53.42							
53.60	R1.0			WWSEOFB-u	WWSEO						
53.62	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
53.64	R1.0	AND			WWSEO	STATUS	=	"Ready for Review" (0040)			
53.65	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Ready for Review" (0040)			
53.66	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
53.67	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
53.68	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
53.70	R1.0	ELSE	53.62								
53.72	R1.0	IF			FB	STATUS	=	"Final" (0020)			
53.74	R1.0	AND			WWSEO	STATUS	=	"Final" (0020)			
53.75	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)			
53.76	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
53.77	R1.0	SET			LSEO				ADSABRSTATUS		&ADSFEED
53.78	R1.0	END	53.72								
53.79	R1.0	END	53.62								
						
Delete 20110323
53.80	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040)		
53.82	R1.0	AND			FB	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
53.84	R1.0	SET			FB				ADSABRSTATUS	&ADSFEEDRFR
53.86	R1.0	END	53.80							
53.88	R1.0	IF			FB	STATUS	=	"Final" (0020)		
53.90	R1.0	SET			FB				ADSABRSTATUS	
53.92	R1.0	END	53.88
end Delete 20110323
							
54.00	R1.0	END	53.00	FB	
 */	
	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
	 *C. STATUS changed to Final
	 */
	protected void completeNowFinalProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		if(doR10processing()){
			EntityItem rootItem = m_elist.getParentEntityGroup().getEntityItem(0);
			
			//53.20	R1.0			LSEOBUNDLEFB-u	LSEOBUNDLE					
			//53.22	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
			//53.24	R1.0	AND			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
			//53.26	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
			//53.28	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
			//53.30	R1.0	ELSE	53.22							
			//53.32	R1.0	IF			FB	STATUS	=	"Final" (0020)		
			//53.34	R1.0	AND			LSEOBUNDLE	STATUS	=	"Final" (0020)		
			//53.36	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	
			//53.38	R1.0	END	53.22	
			checkRelatedItems("LSEOBUNDLE", true);
			//53.40	R1.0			MODELFB-u	MODEL					
			//53.42	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
			//53.44	R1.0	AND			MODEL	STATUS	=	"Ready for Review" (0040)		
			//53.46	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
			//53.48	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
			//53.50	R1.0	ELSE	53.42							
			//53.52	R1.0	IF			FB	STATUS	=	"Final" (0020)		
			//53.54	R1.0	AND			MODEL	STATUS	=	"Final" (0020)		
			//53.56	R1.0	SET			MODEL				ADSABRSTATUS	
			//53.58	R1.0	END	53.42	
			checkRelatedItems("MODEL", true);
  
			//53.60	R1.0			WWSEOFB-u	WWSEO						
			//53.62	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
			//53.64	R1.0	AND			WWSEO	STATUS	=	"Ready for Review" (0040)			
			//53.65	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Ready for Review" (0040)			
			//53.66	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
			//53.67	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
			//53.68	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
			//53.70	R1.0	ELSE	53.62								
			//53.72	R1.0	IF			FB	STATUS	=	"Final" (0020)			
			//53.74	R1.0	AND			WWSEO	STATUS	=	"Final" (0020)			
			//53.75	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)			
			//53.76	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
			//53.77	R1.0	SET			LSEO				ADSABRSTATUS		&ADSFEED
			//53.78	R1.0	END	53.72	
			checkWwseoLseo(true, rootItem); 
			//53.79	R1.0	END	53.62								
			
			//Delete 20110323 53.88	R1.0	IF			FB	STATUS	=	"Final" (0020)		
			//53.90	R1.0	SET			FB				ADSABRSTATUS	
			//setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
			//53.92	R1.0	END	53.88							
			//54.00	R1.0	END	53.00	FB						
		}
	}

	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq)
	 * B.	Status changed to Ready for Review	
	 */
	protected void completeNowR4RProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		if(doR10processing()){
			EntityItem rootItem = m_elist.getParentEntityGroup().getEntityItem(0);
			//53.00	R1.0	FB		Root Entity						
			//53.20	R1.0			LSEOBUNDLEFB-u	LSEOBUNDLE					
			//53.22	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
			//53.24	R1.0	AND			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
			//53.26	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
			//53.28	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
			//53.30	R1.0	ELSE	53.22							
			//53.32	R1.0	IF			FB	STATUS	=	"Final" (0020)		
			//53.34	R1.0	AND			LSEOBUNDLE	STATUS	=	"Final" (0020)		
			//53.36	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	
			//53.38	R1.0	END	53.22	
			checkRelatedItems("LSEOBUNDLE", false);
			
			//53.40	R1.0			MODELFB-u	MODEL					
			//53.42	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
			//53.44	R1.0	AND			MODEL	STATUS	=	"Ready for Review" (0040)		
			//53.46	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
			//53.48	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
			//53.50	R1.0	ELSE	53.42							
			//53.52	R1.0	IF			FB	STATUS	=	"Final" (0020)		
			//53.54	R1.0	AND			MODEL	STATUS	=	"Final" (0020)		
			//53.56	R1.0	SET			MODEL				ADSABRSTATUS	
			//53.58	R1.0	END	53.42		
			checkRelatedItems("MODEL", false);
			
			//53.60	R1.0			WWSEOFB-u	WWSEO						
			//53.62	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
			//53.64	R1.0	AND			WWSEO	STATUS	=	"Ready for Review" (0040)			
			//53.65	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Ready for Review" (0040)			
			//53.66	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
			//53.67	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
			//53.68	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
			//53.70	R1.0	ELSE	53.62								
			//53.72	R1.0	IF			FB	STATUS	=	"Final" (0020)			
			//53.74	R1.0	AND			WWSEO	STATUS	=	"Final" (0020)			
			//53.75	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)			
			//53.76	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
			//53.77	R1.0	SET			LSEO				ADSABRSTATUS		&ADSFEED
			//53.78	R1.0	END	53.72	
			checkWwseoLseo(false, rootItem); 
			//53.79	R1.0	END	53.62									
			
			//Delete 20110323 53.80	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040)		
			//53.82	R1.0	AND			FB	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
			//53.84	R1.0	SET			FB				ADSABRSTATUS	&ADSFEEDRFR
			//doRFR_ADSXML(rootItem, getStatusAttrCode()); 
			//53.86	R1.0	END	53.80							
					
			//54.00	R1.0	END	53.00	FB	
		}
	}

    /* (non-Javadoc)
     * When this ABR is invoked and STATUS = DATAQUALITY = “Final”, then perform the actions defined in the SETs spreadsheet.
     * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doAlreadyFinalProcessing(COM.ibm.eannounce.objects.EntityItem)
     */
    protected void doAlreadyFinalProcessing(EntityItem rootEntity) throws Exception {
    	completeNowFinalProcessing();
    }
    
	/* (non-Javadoc)
	 * update LIFECYCLE value when STATUS is updated
	 * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
	 */
	protected String getLCRFRWFName(){ return "WFLCFBRFR";}
	protected String getLCFinalWFName(){ return "WFLCFBFINAL";}

	/**********************************
	* class has a different status attribute
	*/
	protected String getStatusAttrCode() { return "FBSTATUS";}
	
	/***********************************************
	 *  Get ABR description
	 *
	 *@return java.lang.String
	 */
	public String getDescription()
	{
		String desc =  "FB ABR.";
		return desc;
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getABRVersion()
	{
		return "$Revision: 1.3 $";
	}
}
