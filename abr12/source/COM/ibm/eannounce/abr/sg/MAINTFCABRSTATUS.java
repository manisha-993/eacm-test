package COM.ibm.eannounce.abr.sg;

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
//		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);
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
