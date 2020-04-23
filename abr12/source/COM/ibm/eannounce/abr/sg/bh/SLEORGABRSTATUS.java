//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* SLEORGABRSTATUS class
*
* From "SG FS ABR Data Quality 20101117.doc"
* 
SLEORGABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.SLEORGABRSTATUS
SLEORGABRSTATUS_enabled=true
SLEORGABRSTATUS_idler_class=A
SLEORGABRSTATUS_keepfile=true
SLEORGABRSTATUS_report_type=DGTYPE01
*/
//$Log: SLEORGABRSTATUS.java,v $
//Revision 1.1  2010/12/16 16:12:35  wendy
//BH FS ABR Data Quality 20101117 updates
//
//
public class SLEORGABRSTATUS extends DQABRSTATUS
{

	/**********************************
	* nothing needed
	*/
	protected boolean isVEneeded(String statusFlag) {
		return false;
	}
	
/*
 * from sets ss
207.00		SLEORGNPLNTCODE									
208.00	R1.0	SET			SLEORGNPLNTCODE				ADSABRSTATUS		&ADSFEED
209.00		END	207.00	SLEORGNPLNTCODE													
 */	
	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq)
	 * C.	Status changed to Ready for Review
	 */
	protected void completeNowR4RProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		//setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
	}

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
	 *D. STATUS changed to Final
	 *
	 */
	protected void completeNowFinalProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		addDebug(" status now final");
		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
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

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "SLEORGNPLNTCODE ABR.";
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
