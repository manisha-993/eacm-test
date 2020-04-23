// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
IMGABRSTATUS_class=COM.ibm.eannounce.abr.sg.IMGABRSTATUS
IMGABRSTATUS_enabled=true
IMGABRSTATUS_idler_class=A
IMGABRSTATUS_keepfile=true
IMGABRSTATUS_read_only=true
IMGABRSTATUS_report_type=DGTYPE01

 *
 * IMGABRSTATUS.java,v
 * Revision 1.15  2007/09/14 17:43:55  wendy
 * Updated for GX
 *
 * Revision 1.14  2007/08/17 16:02:09  wendy
 * RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
 * from 'SG FS xSeries ABRs 20070803.doc'
 *
 * Revision 1.13  2006/03/16 18:24:20  anhtuan
 * Fix null pointer exception.
 *
 * Revision 1.12  2006/03/14 05:01:12  anhtuan
 * Fix Jtest. Jtest does not allow multiple returns from a method.
 *
 * Revision 1.11  2006/03/10 17:08:33  anhtuan
 * Updated Specs 30b FS xSeries ABRs 20060309.doc.
 *
 * Revision 1.10  2006/02/25 21:23:58  anhtuan
 * Remove redundant stuff.
 *
 * Revision 1.9  2006/02/23 03:33:52  anhtuan
 * AHE compliant.
 *
 * Revision 1.8  2006/02/22 04:00:59  anhtuan
 * Use PokUtils.
 *
 * Revision 1.7  2006/01/27 16:29:41  anhtuan
 * Check for null values of DATAQUALITY and/or STATUS.
 *
 * Revision 1.6  2006/01/26 15:02:23  anhtuan
 * AHE copyright.
 *
 * Revision 1.5  2006/01/26 01:58:27  anhtuan
 * Updated specs.
 *
 * Revision 1.3  2005/08/18 00:00:44  anhtuan
 * Fix resource file problem.
 *
 * Revision 1.2  2005/08/04 23:23:27  anhtuan
 * Use resource file.
 *
 * Revision 1.1  2005/07/19 16:20:34  anhtuan
 * Initial version.
 *
 *
 *
 *
 * </pre>
 *
 *@author     Anhtuan Nguyen
 *@created    July 1, 2005
 */
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* IMGABRSTATUS class
*
* From "SG FS ABR Data Quality 20070911.doc"

*A.	STATUS = Draft | Change | Ready for Review
*
*Criteria = None
*
*/
public class IMGABRSTATUS extends DQABRSTATUS
{

	/**********************************
	* nothing needed
	*/
	protected boolean isVEneeded(String statusFlag) {
		return false;
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
        String desc =  "IMAGE ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.15";
    }
}
