//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* CATNAVABRSTATUS class
*
* From "SG FS ABR Data Quality 20101117.doc"
* 
CATNAVABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.CATNAVABRSTATUS
CATNAVABRSTATUS_enabled=true
CATNAVABRSTATUS_idler_class=A
CATNAVABRSTATUS_keepfile=true
CATNAVABRSTATUS_report_type=DGTYPE01
*/
//$Log: CATNAVABRSTATUS.java,v $
//Revision 1.2  2010/12/09 17:47:43  wendy
//Remove r10 check, control thru props file
//
//Revision 1.1  2010/11/18 17:11:49  wendy
//BH FS ABR Data Quality 20101117 updates
//
public class CATNAVABRSTATUS extends DQABRSTATUS
{

	/**********************************
	* nothing needed
	*/
	protected boolean isVEneeded(String statusFlag) {
		return false;
	}
	
/*
 * from sets ss
210.00		CATNAV									
211.00	R1.0	SET			CATNAV				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
212.00		END	210.00	CATNAV
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
		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
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
        String desc =  "CATNAV ABR.";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "$Revision: 1.2 $";
    }
}
