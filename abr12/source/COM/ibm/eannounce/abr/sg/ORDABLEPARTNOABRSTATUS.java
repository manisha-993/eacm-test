// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;


/**********************************************************************************
* ORDABLEPARTNOABRSTATUS class
*
* From "SG FS ABR Data Quality 20070911.doc"
*
* The Orderable Part Number is a 'stand alone' entity and may have one or more
* Availabilities (AVAIL) as children
*
*A.	STATUS = Draft | Change Request
*
*None
*
*B.	STATUS = Ready for Review
*
*1.	CountOf(ORDABLEPARTNOGEODATE-d: GEODATE => 1
*ErrorMessage 'Must have at least one instance of' LD(GEODATE).
*
*C.	STATUS = Final
*
*Data Quality checks are not required. This occurs when data is sent to SAPL but there is
*a downstream failure which requires the XML to be sent again. The UI allows the setting of
*SAPL to Send which will queue this ABR.
*
*1.	QueueSAPL
*
* The ABR produces a Report as described in section XXI. ABR Reports.
*
* The ABR sets its unique attribute (ORDABLEPARTNOABRSTATUS)
ORDABLEPARTNOABRSTATUS_class=COM.ibm.eannounce.abr.sg.ORDABLEPARTNOABRSTATUS
ORDABLEPARTNOABRSTATUS_enabled=true
ORDABLEPARTNOABRSTATUS_idler_class=A
ORDABLEPARTNOABRSTATUS_keepfile=true
ORDABLEPARTNOABRSTATUS_read_only=true
ORDABLEPARTNOABRSTATUS_report_type=DGTYPE01
ORDABLEPARTNOABRSTATUS_vename=SAPLVEORDPARTN
*/
// ORDABLEPARTNOABRSTATUS.java,v
// Revision 1.9  2007/10/23 17:45:46  wendy
// data model chgs
//
// Revision 1.8  2007/10/15 20:22:35  wendy
// QueueSAPL when PDHDOMAIN is not in list of domains
//
// Revision 1.7  2007/09/14 17:43:55  wendy
// Updated for GX
//
// Revision 1.6  2007/08/17 16:02:10  wendy
// RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
// from 'SG FS xSeries ABRs 20070803.doc'
//
// Revision 1.5  2007/06/18 18:31:45  wendy
// TIR 747QEJ- XML Mapping SS updated
//
// Revision 1.4  2007/06/13 21:18:22  wendy
// TIR73RS77 -XML Mapping updated
//
// Revision 1.3  2007/05/16 17:19:15  wendy
// TIR 7STP5L - Routing of XML correction required, spec chg
//
// Revision 1.2  2007/04/20 14:06:50  wendy
// RQ0417075638 updates
//
// Revision 1.1  2007/04/02 17:41:09  wendy
// Init for SAPL abr
//
public class ORDABLEPARTNOABRSTATUS extends DQABRSTATUS
{

/*
SAPLVEORDPARTN	0	ORDABLEPARTNO	Relator	ORDABLEPARTNOGEODATE	GEODATE
SAPLVEORDPARTN	1	GEODATE	Association	GEODATEGAA	GENERALAREA
*/
	/**********************************
	* complete abr processing when status is already final; (dq was final too)
	* 1.	Set SAPLABRSTATUS = 0020 (Queued)
	*/
	protected void doAlreadyFinalProcessing() {
        setFlagValue(m_elist.getProfile(),"SAPLABRSTATUS", SAPLABR_QUEUED);
	}

	/**********************************
	* complete abr processing after status moved to final; (status was r4r)
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		queueSapl();
	}

	/**********************************
	* complete abr processing after status moved to final; (status was r4r) for
	* those ABRs that have domains that are not in the list of domains
	*/
	protected void completeNowFinalProcessingForOtherDomains() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		queueSapl();
	}

	/**********************************
	* must be readyforreview
	*/
	protected boolean isVEneeded(String statusFlag) {
		return STATUS_R4REVIEW.equals(statusFlag);
	}

    /**********************************
	* From "SG FS ABR Data Quality 20070911.doc"
	*A.	STATUS = Draft | Change Request
	*
	*None
	*
	*B.	STATUS = Ready for Review
	*
	*1.	CountOf(ORDABLEPNGEODATE-d: GEODATE => 1
	*ErrorMessage 'Must have at least one instance of' LD(GEODATE).
	*
	*C.	STATUS = Final
	*
	*Data Quality checks are not required. This occurs when data is sent to SAPL but there is
	*a downstream failure which requires the XML to be sent again. The UI allows the setting of
	*SAPL to Send which will queue this ABR.
	*
	*1.	QueueSAPL
    */
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
		if (STATUS_R4REVIEW.equals(statusFlag)){
			int cnt = getCount("ORDABLEPNGEODATE");
			if(cnt == 0)	{
				EntityGroup eGrp = m_elist.getEntityGroup("GEODATE");
				//MINIMUM_INSTANCE_ERR = must have at least one instance of {0}
				Object args[] = new Object[]{eGrp.getLongDescription()};
				addError("MINIMUM_INSTANCE_ERR",args);
			}
		}else{
        	addDebug("No checking required");
		}
    }

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "ORDABLEPARTNO ABR";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.9";
    }
}
