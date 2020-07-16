package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.EntityItem;

public class MAINTFCABRSTATUS extends DQABRSTATUS{

	/**********************************
	*/
	protected boolean isVEneeded(String statusFlag) {
		return false;
	}

    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
	* C.	Status changed to Ready for Review
    */
    protected void completeNowR4RProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
//         setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);
    }

    /**********************************
    * complete abr processing after status moved to final; (status was r4r)
    *D. STATUS changed to Final
    *
    *1.	Set ADSABRSTATUS = 0020 (Queued)
    */
    protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		addDebug(" status now final");
		// Set ADSABRSTATUS of all SVCMODs when status=final
//		EntityGroup psGrp = m_elist.getEntityGroup("MAINTPRODSTRUCT");
//		addDebug("completeNowFinalProcessing MAINTPRODSTRUCT count=" + psGrp.getEntityItemCount());
//		for(int p = 0; p < psGrp.getEntityItemCount(); p++) {
//			EntityItem psitem = psGrp.getEntityItem(p);
//			EntityItem mdlItem = getDownLinkEntityItem(psitem, "SVCMOD");
//			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"), psitem);
//		}
    }
    
    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
	*/
	
	protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception {
		
		addDebug("No checking required");
	}
	
	/***********************************************
	    *  Get ABR description
	    *
	    *@return java.lang.String
	    */
	
	public String getDescription() {
		
		return "MAINTFEATURE ABR";
	}
	
	/***********************************************
	 *  Get the version
	    *
	    *@return java.lang.String
	    */
	public String getABRVersion()
	{
	    return "1.0";
	}
}
