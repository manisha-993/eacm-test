//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2010  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* MMABRSTATUS class
*
* BH FS ABR Data Quality 20111017.doc
* When this ABR is invoked and STATUS = DATAQUALITY = “Final”, then perform the actions defined in the SETs spreadsheet.
*  
*BH FS ABR Data Quality 20110322.doc
*Delete 20110323	No XML for MM sets 130.62-130.74
*
* From "BH FS ABR Data Quality 20101201b.doc"
*
*need workflow actions - WFLCMMRFR and WFLCMMFINAL
*need ve DQVEMM
*needs DQ and ABRSTATUS and LIFECYCLE and ADSABRSTATUS attrs
*
MMABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.MMABRSTATUS
MMABRSTATUS_enabled=true
MMABRSTATUS_idler_class=A
MMABRSTATUS_keepfile=true
MMABRSTATUS_report_type=DGTYPE01
MMABRSTATUS_vename=DQVEMM
MMABRSTATUS_CAT1=RPTCLASS.MMABRSTATUS
MMABRSTATUS_CAT2=
MMABRSTATUS_CAT3=RPTSTATUS
*
*/
//$Log: MMABRSTATUS.java,v $
//Revision 1.3  2011/10/18 15:32:44  wendy
//BH FS ABR Data Quality 20111017.doc updates
//
//Revision 1.2  2011/04/07 19:47:23  wendy
//Remove queue ADS on root
//
//Revision 1.1  2010/12/01 21:51:24  wendy
//BH FS ABR Data Quality 20101201b.doc updates
//
public class MMABRSTATUS extends DQABRSTATUS
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
 130.00	R1.0	MM		Root Entity						
130.02	R1.0			LSEOBUNDLEMM-u	LSEOBUNDLE					
130.04	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
130.06	R1.0	AND			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
130.08	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
130.10	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
130.12	R1.0	ELSE	130.04							
130.14	R1.0	IF			MM	STATUS	=	"Final" (0020)		
130.16	R1.0	AND			LSEOBUNDLE	STATUS	=	"Final" (0020)		
130.18	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	
130.20	R1.0	END	130.04							
130.22	R1.0			MODELMM-u	MODEL					
130.24	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
130.26	R1.0	AND			MODEL	STATUS	=	"Ready for Review" (0040)		
130.28	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
130.30	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
130.32	R1.0	ELSE	130.24							
130.34	R1.0	IF			MM	STATUS	=	"Final" (0020)		
130.36	R1.0	AND			MODEL	STATUS	=	"Final" (0020)		
130.38	R1.0	SET			MODEL				ADSABRSTATUS	
130.40	R1.0	END	130.24							
130.42	R1.0			WWSEOMM-u	WWSEO						
130.44	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
130.46	R1.0	AND			WWSEO	STATUS	=	"Ready for Review" (0040)			
		AND		WWSEOLSEO-d	LSEO	STATUS	=	"Ready for Review" (0040)			
130.48	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
		AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
130.50	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
130.52	R1.0	ELSE	130.44								
130.54	R1.0	IF			MM	STATUS	=	"Final" (0020)			
130.56	R1.0	AND			WWSEO	STATUS	=	"Final" (0020)			
130.57	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)			
130.58	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
130.59	R1.0	SET			LSEO				ADSABRSTATUS		&ADSFEED
130.60	R1.0	END	130.54								
130.61	R1.0	END	130.44								
						
Delete 20110323
130.62	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040)		
130.64	R1.0	AND			MM	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
130.66	R1.0	SET			MM				ADSABRSTATUS	&ADSFEEDRFR
130.68	R1.0	END	130.62							
130.70	R1.0	IF			MM	STATUS	=	"Final" (0020)		
130.72	R1.0	SET			MM				ADSABRSTATUS	
130.74	R1.0	END	130.70							
131.00	R1.0	END	130.00	MM	
end Delete 20110323

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
			
			//130.00	R1.0	MM		Root Entity						
			//130.02	R1.0			LSEOBUNDLEMM-u	LSEOBUNDLE					
			//130.04	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
			//130.06	R1.0	AND			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
			//130.08	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
			//130.10	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
			//130.12	R1.0	ELSE	130.04							
			//130.14	R1.0	IF			MM	STATUS	=	"Final" (0020)		
			//130.16	R1.0	AND			LSEOBUNDLE	STATUS	=	"Final" (0020)		
			//130.18	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	
			//130.20	R1.0	END	130.04			
			checkRelatedItems("LSEOBUNDLE", true); 
			//130.22	R1.0			MODELMM-u	MODEL					
			//130.24	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
			//130.26	R1.0	AND			MODEL	STATUS	=	"Ready for Review" (0040)		
			//130.28	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
			//130.30	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
			//130.32	R1.0	ELSE	130.24							
			//130.34	R1.0	IF			MM	STATUS	=	"Final" (0020)		
			//130.36	R1.0	AND			MODEL	STATUS	=	"Final" (0020)		
			//130.38	R1.0	SET			MODEL				ADSABRSTATUS	
			//130.40	R1.0	END	130.24		
			checkRelatedItems("MODEL", true);
			
			//130.42	R1.0			WWSEOMM-u	WWSEO						
			//130.44	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
			//130.46	R1.0	AND			WWSEO	STATUS	=	"Ready for Review" (0040)			
			//		AND		WWSEOLSEO-d	LSEO	STATUS	=	"Ready for Review" (0040)			
			//130.48	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
			//		AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
			//130.50	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
			//130.52	R1.0	ELSE	130.44								
			//130.54	R1.0	IF			MM	STATUS	=	"Final" (0020)			
			//130.56	R1.0	AND			WWSEO	STATUS	=	"Final" (0020)			
			//130.57	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)			
			//130.58	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
			//130.59	R1.0	SET			LSEO				ADSABRSTATUS		&ADSFEED
			//130.60	R1.0	END	130.54	
			checkWwseoLseo(true, rootItem); 
			//130.61	R1.0	END	130.44								
	
			//Delete 20110323 130.70	R1.0	IF			MM	STATUS	=	"Final" (0020)		
			//130.72	R1.0	SET			MM				ADSABRSTATUS	
			//setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
			//130.74	R1.0	END	130.70							
			//131.00	R1.0	END	130.00	MM						
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
			
			//130.00	R1.0	MM		Root Entity						
			//130.02	R1.0			LSEOBUNDLEMM-u	LSEOBUNDLE					
			//130.04	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
			//130.06	R1.0	AND			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
			//130.08	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
			//130.10	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
			//130.12	R1.0	ELSE	130.04							
			//130.14	R1.0	IF			MM	STATUS	=	"Final" (0020)		
			//130.16	R1.0	AND			LSEOBUNDLE	STATUS	=	"Final" (0020)		
			//130.18	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	
			//130.20	R1.0	END	130.04		
			checkRelatedItems("LSEOBUNDLE", false);
			//130.22	R1.0			MODELMM-u	MODEL					
			//130.24	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
			//130.26	R1.0	AND			MODEL	STATUS	=	"Ready for Review" (0040)		
			//130.28	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
			//130.30	R1.0	SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR
			//130.32	R1.0	ELSE	130.24							
			//130.34	R1.0	IF			MM	STATUS	=	"Final" (0020)		
			//130.36	R1.0	AND			MODEL	STATUS	=	"Final" (0020)		
			//130.38	R1.0	SET			MODEL				ADSABRSTATUS	
			//130.40	R1.0	END	130.24		
			checkRelatedItems("MODEL", false);

			//130.42	R1.0			WWSEOMM-u	WWSEO						
			//130.44	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
			//130.46	R1.0	AND			WWSEO	STATUS	=	"Ready for Review" (0040)			
			//		AND		WWSEOLSEO-d	LSEO	STATUS	=	"Ready for Review" (0040)			
			//130.48	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
			//		AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
			//130.50	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
			//130.52	R1.0	ELSE	130.44								
			//130.54	R1.0	IF			MM	STATUS	=	"Final" (0020)			
			//130.56	R1.0	AND			WWSEO	STATUS	=	"Final" (0020)			
			//130.57	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)			
			//130.58	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
			//130.59	R1.0	SET			LSEO				ADSABRSTATUS		&ADSFEED
			//130.60	R1.0	END	130.54	
			checkWwseoLseo(false, rootItem); 
			//130.61	R1.0	END	130.44	
			
			//Delete 20110323 130.62	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040)		
			//130.64	R1.0	AND			MM	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
			//130.66	R1.0	SET			MM				ADSABRSTATUS	&ADSFEEDRFR
			//doRFR_ADSXML(rootItem, getStatusAttrCode()); 
			//130.68	R1.0	END	130.62							
			//131.00	R1.0	END	130.00	MM							
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
	protected String getLCRFRWFName(){ return "WFLCMMRFR";} 
	protected String getLCFinalWFName(){ return "WFLCMMFINAL";} 


	/**********************************
	* class has a different status attribute
	*/
	protected String getStatusAttrCode() { return "MMSTATUS";}
	
	/***********************************************
	 *  Get ABR description
	 *
	 *@return java.lang.String
	 */
	public String getDescription()
	{
		String desc =  "MM ABR.";
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
