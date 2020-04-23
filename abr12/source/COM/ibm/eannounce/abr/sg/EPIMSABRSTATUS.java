// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import java.util.*;

/**********************************************************************************
* EPIMSABRSTATUS class launch epimsAbr class to handle xml generation for specific entitytype
* From "SG FS ABR ePIMS InAndOut Bound 20071010.doc"
* C.	Outbound Notifications to ePIMS
*
* A user, via the UI, will request promotion of offering information by setting Data Quality
* (DATAQUALITY) to Final (0020) which queues the Data Quality ABR. If the data quality criteria
* is met, then the ABR advances Status (STATUS) to Final (0020) and queues applicable ABRs such
* as this one.
*
* If all of the criteria are met, then EACM will place an XML message on a MQ Series Queue
* the same as it does in SG 30b. Although it would be nice to move to the OIM Strategic Architecture
* that will be utilized by WWPRT, the ePIMS schedules do not support this change.
*
* If there is any content waiting (e.g. LSEO in the LSEOBUNDLE) for an ePIMS notification that it
* has been Promoted/Released, then the notification will not be sent at this time. Instead, it will
* be placed on a 'waitingQueue' and wait for the reception of all content being Promoted/Released.
*
*/
// EPIMSABRSTATUS.java,v
// Revision 1.4  2007/12/13 19:55:26  wendy
// Added MODEL abr
//
// Revision 1.3  2007/11/30 22:04:43  wendy
// cleanup
//
// Revision 1.2  2007/11/28 22:58:07  wendy
// merged with WWPRT abr base class
//
// Revision 1.1  2007/11/16 19:32:55  nancy
// Init for GX EPIMs ABRs
//
public class EPIMSABRSTATUS extends EPIMSWWPRTBASE
{
    private static final Hashtable ABR_TBL;
	static{
        ABR_TBL = new Hashtable();
        ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.EPIMSLSEOABR");
        ABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.sg.EPIMSLSEOBUNDLEABR");
        ABR_TBL.put("WWSEO", "COM.ibm.eannounce.abr.sg.EPIMSWWSEOABR");
        ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.EPIMSPRODSTRUCTABR");
        ABR_TBL.put("SWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.EPIMSSWPRODSTRUCTABR");
        ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.EPIMSMODELABR");
    }

	protected String getSimpleABRName()
	{
		// find class to instantiate based on entitytype
		// Load the specified ABR class in preparation for execution
		String clsname = (String) ABR_TBL.get(getEntityType());
		addDebug("creating instance of EPIMSABR  = '" + clsname + "'");
		return clsname;
	}
}
