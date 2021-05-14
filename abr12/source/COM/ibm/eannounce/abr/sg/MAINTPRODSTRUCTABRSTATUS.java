package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.EntityItem;

public class MAINTPRODSTRUCTABRSTATUS extends DQABRSTATUS{
	private Object args[] = new Object[3];

	/**********************************
	* always check if not final, but need navigation name from model and fc
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
	* C.	Status changed to Ready for Review
	1.	Set ADSABRSTATUS = 0020 (Queued)
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
	D.	STATUS changed to Final

	1.	Set ADSABRSTATUS = 0020 (Queued)

	*
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
        setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);
	}

    /**********************************
	A.  STATUS = Draft | Change Request
	1.	CompareAll(MAINTPRODSTRUCT-d: SVCMOD.STATUS) = 0020 (Final) or 0040 (Ready for Review)
	ErrorMessage LD(SVCMOD) 'Status is not Ready for Review or Final'
	2.	CompareAll(MAINTPRODSTRUCT-u: MAINTFEATURE.STATUS) = 0020 (Final) or 0040 (Ready for Review)
	ErrorMessage LD(MAINTFEATURE) 'Status is not Ready for Review or Final'

	B.  STATUS = Ready for Review

	1.  All checks from 'STATUS = Draft | Change Request'
	2.  CompareAll(MAINTPRODSTRUCT-d: SVCMOD.STATUS) = 0020 (Final)
	ErrorMessage LD(SVCMOD) 'Status is not Final'
	3.  CompareAll(MAINTPRODSTRUCT-u: MAINTFEATURE.STATUS) = 0020 (Final)
	ErrorMessage LD(MAINTFEATURE) 'Status is not Final'
	
	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
		if(STATUS_DRAFT.equals(statusFlag) || STATUS_CHGREQ.equals(statusFlag)) // 'Draft or Ready for Review'
		{
	        EntityItem mdlItem = m_elist.getEntityGroup("SVCMOD").getEntityItem(0); // has to exist
	        EntityItem fcItem = m_elist.getEntityGroup("MAINTFEATURE").getEntityItem(0); // has to exist

			//3.	CompareAll(MAINTPRODSTRUCT-d: SVCMOD.STATUS) = 0020 (Final) or 0040 (Ready for Review)
			//ErrorMessage LD(SVCMOD) 'Status is not Ready for Review or Final'
			String status = getAttributeFlagEnabledValue(mdlItem , "STATUS");
			addDebug(mdlItem.getKey()+" check status "+status);
			if (status==null){
				status = STATUS_FINAL;
			}

			if (!STATUS_FINAL.equals(status) && !STATUS_R4REVIEW.equals(status)){
				addDebug(mdlItem.getKey()+" is not Final or R4R");
				//NOT_R4R_FINAL_ERR = {0} {1} is not Ready for Review or Final.
				args[0] = mdlItem.getEntityGroup().getLongDescription();
				args[1] = getNavigationName(mdlItem);
				addError("NOT_R4R_FINAL_ERR",args);
			}
			//4.	CompareAll(MAINTPRODSTRUCT-u: MAINTFEATURE.STATUS) = 0020 (Final) or 0040 (Ready for Review)
			//ErrorMessage LD(MAINTFEATURE 'Status is not Ready for Review or Final'
			status = getAttributeFlagEnabledValue(fcItem , "STATUS");
			addDebug(fcItem.getKey()+" check status "+status);
			if (status==null){
				status = STATUS_FINAL;
			}

			if (!STATUS_FINAL.equals(status) && !STATUS_R4REVIEW.equals(status)){
				addDebug(fcItem.getKey()+" is not Final or R4R");
				//NOT_R4R_FINAL_ERR = {0} {1} is not Ready for Review or Final.
				args[0] = fcItem.getEntityGroup().getLongDescription();
				args[1] = getNavigationName(fcItem);
				addError("NOT_R4R_FINAL_ERR",args);
			}
		}

		if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final'
		{
			//2.CompareAll(SVCPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final)
			//ErrorMessage LD(MODEL) ' is not Final'
			checkStatus("SVCMOD");

			//3.CompareAll(SVCPRODSTRUCT-d: SVCFEATURE.STATUS) = 0020 (Final)
			//ErrorMessage LD(SVCFEATURE) ' is not Final'
			checkStatus("MAINTFEATURE");
			
		}
	}


    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "MAINTPRODSTRUCT ABR";
        return desc;
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
