// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import java.util.*;

/**********************************************************************************
* WWPRTABRSTATUS class launch wwprtAbr class to handle xml generation for specific entitytype
* From "SG FS ABR WWPRT Notification 20071130.doc"
*
* The ABR applies to the Announcements, custom (special bid) LSEOs, custom LSEOBUNDLEs and RPQs.
* This ABR is queued by the Data Quality ABR and notifies WWPRT of items needing pricing action.
*
WWPRTABRSTATUS_class=COM.ibm.eannounce.abr.sg.WWPRTABRSTATUS
WWPRTABRSTATUS_enabled=true
WWPRTABRSTATUS_idler_class=A
WWPRTABRSTATUS_keepfile=true
WWPRTABRSTATUS_report_type=DGTYPE01
WWPRTABRSTATUS_CAT1=RPTCLASS.WWPRTABRSTATUS
WWPRTABRSTATUS_CAT2=WG.PDHDOMAIN
WWPRTABRSTATUS_CAT3=RPTSTATUS
WWPRTABRSTATUS_SUBSCRVE=WWDERDATASNVE
*/
// WWPRTABRSTATUS.java,v
// Revision 1.2  2007/11/30 22:08:40  wendy
// cleanup
//
// Revision 1.1  2007/11/28 22:56:28  wendy
// Init for WWPRT ABRs
//
//
public class WWPRTABRSTATUS extends EPIMSWWPRTBASE
{
    private static final Hashtable ABR_TBL;
	static{
        ABR_TBL = new Hashtable();
        //WWPRTANNABR is no longer used, so remove it
        //ABR_TBL.put("ANNOUNCEMENT", "COM.ibm.eannounce.abr.sg.WWPRTANNABR");
        ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.WWPRTLSEOABR");
        ABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.sg.WWPRTLSEOBUNDLEABR");
        ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.WWPRTPRODSTRUCTABR");
    }

	protected String getSimpleABRName()
	{
		// find class to instantiate based on entitytype
		// Load the specified ABR class in preparation for execution
		String clsname = (String) ABR_TBL.get(getEntityType());
		addDebug("creating instance of WWPRTABR  = '" + clsname + "'");
		return clsname;
	}
}
