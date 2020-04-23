// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008 All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* SVCFCABRSTATUS class
*
* from SG FS ABR Data Quality 20080430.doc
*
XXVIII. SVCFEATURE

A.  STATUS = Draft | Change Request

None

B.  STATUS = Ready for Review

None
C.  STATUS changed to Ready for Review

1.  Set ADSABRSTATUS = 0020 (Queued)

D.  STATUS changed to Final

1.  Set ADSABRSTATUS = 0020 (Queued)
*/
//SVCFCABRSTATUS.java,v
//Revision 1.5  2009/07/30 18:41:27  wendy
//Moved BH DQ ABRs to diff pkg
//
//Revision 1.3  2008/05/29 15:53:49  wendy
//Backout queueing ADSABRSTATUS
//
//Revision 1.2  2008/05/27 14:28:58  wendy
//Clean up RSA warnings
//
//Revision 1.1  2008/05/01 12:34:04  wendy
//Init DQ ABR
//
public class SVCFCABRSTATUS extends DQABRSTATUS
{
	/**********************************
	*/
	protected boolean isVEneeded(String statusFlag) {
		return false;
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
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
       	addDebug("No checking required");
	}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "SVCFEATURE ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.5";
    }
}
