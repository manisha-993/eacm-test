//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* WARRABRSTATUS class
*
* From "SG FS ABR Data Quality 20101203.doc"
* 
WARRABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.WARRABRSTATUS
WARRABRSTATUS_enabled=true
WARRABRSTATUS_idler_class=A
WARRABRSTATUS_keepfile=true
WARRABRSTATUS_report_type=DGTYPE01
*/
//$Log: WARRABRSTATUS.java,v $
//Revision 1.1  2010/12/03 20:31:51  wendy
//SG FS ABR Data Quality 20101203 init
//
public class WARRABRSTATUS extends DQABRSTATUS
{

	/**********************************
	* nothing needed
	*/
	protected boolean isVEneeded(String statusFlag) {
		return false;
	}
	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq)
	 * C.	Status changed to Ready for Review
210.00		WARR									
211.00	R1.0	SET			WARR				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
212.00		END	210.00	WARR							

	 */
	protected void completeNowR4RProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		if(doR10processing()){
			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
		}
	}

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
	 *D. STATUS changed to Final
	 *
210.00		WARR									
211.00	R1.0	SET			WARR				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED
212.00		END	210.00	WARR							

	 */
	protected void completeNowFinalProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		addDebug(" status now final");
		if(doR10processing()){
			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
		}
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
        String desc =  "WARR ABR.";
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
