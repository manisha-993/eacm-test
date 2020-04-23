//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2010  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package COM.ibm.eannounce.abr.sg.bh;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
 * IMGABRSTATUS class
 *
 * From "BH FS ABR Data Quality 20100823.doc"
 *
 *need workflow actions - WFLCIMGRFR and WFLCIMGFINAL
 *needs ADSABRSTATUS and LIFECYCLE attrs
 *
 */
//$Log: IMGABRSTATUS.java,v $
//Revision 1.1  2010/09/16 17:49:26  wendy
//BH FS ABR Data Quality 20100914.doc - IMG queue ADSABRSTATUS
//
public class IMGABRSTATUS extends DQABRSTATUS
{

	/**********************************
	 * nothing needed
	 */
	protected boolean isVEneeded(String statusFlag) {
		return false;
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

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
	 *C. STATUS changed to Final
Change	61.00	R1.0	IMG		Root Entity														
Add	61.10	R1.0	IF			IMG	STATUS	=	"Final" (0020)			
Add	61.12	R1.0	SET			IMG				ADSABRSTATUS		&ADSFEED
Add	61.14	R1.0	END	61.10								
	62.00		END	61.00	IMG							

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
	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq)
	 * B.	Status changed to Ready for Review	
Add	61.02	R1.0	IF			IMG	STATUS	=	"Ready for Review" (0040)			
Add	61.04	R1.0	AND			IMG	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
Add	61.06	R1.0	SET			IMG				ADSABRSTATUS	&ADSFEEDRFR	
Add	61.08	R1.0	END	61.02	
	 */
	protected void completeNowR4RProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		if(doR10processing()){
			EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);
			//Add	61.02	R1.0	IF			IMG	STATUS	=	"Ready for Review" (0040)			
			//Add	61.04	R1.0	AND			IMG	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
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

	/* (non-Javadoc)
	 * update LIFECYCLE value when STATUS is updated
	 * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
	 */
	protected String getLCRFRWFName(){ return "WFLCIMGRFR";} 
	protected String getLCFinalWFName(){ return "WFLCIMGFINAL";}

	/***********************************************
	 *  Get ABR description
	 *
	 *@return java.lang.String
	 */
	public String getDescription()
	{
		String desc =  "IMAGE ABR.";

		return desc;
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getABRVersion()
	{
		return "$Revision: 1.1 $";
	}
}
