// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
* EPIMSWWSEOABR class
*
* From "SG FS ABR ePIMS InAndOut Bound 20071010.doc"
* The Data Quality ABR will only queue this ABR if WWSEO.STATUS is Final and ePIMS Status is Not Ready.
*
* WWSEOPRODSTRUCT:PRODSTRUCT as p
* WWSEOSWPRODSTRUCT:SWPRODSTRUCT as p
*
* Note: the preceding gives two cases for the following.
*
* If WWSEO.SPECBID = Yes (11458) then
* 	If any p.EPIMSSTATUS <> 'Released' then
* 		Add WWSEO to EPwaitingQueue for the p.EPIMSSTATUS
* 		Set WWSEO.EPIMSSTATUS to 'Waiting' (EPWAIT)
* 	Else
* 		Set WWSEO.EPIMSSTATUS to 'Released'
* 	End if
* Else
* 	If any p.EPIMSSTATUS <> 'Released' or 'Promoted' then
* 		Add WWSEO to EPwaitingQueue for the p.EPIMSSTATUS
* 		Set WWSEO.EPIMSSTATUS to 'Waiting' (EPWAIT)
* 	Else
* 		Set WWSEO.EPIMSSTATUS to 'Promoted'
* 	End if
* End if
*
* EPwaitingQueue (EPWAITINGQUEUE)
* 	EPWAITET	Set = 'WWSEO'
* 	EPWAITEID	Set = entityid
* 	EPONET		Set = p.entitytype
* 	EPONEID		Set = p.entityid
* 	EPMACHTYPE	not set
* 	EPMODELATR	not set
* 	EPFEATURE	not set
* 	EPSALESORG	not set
* 	EPSTATUS	derived
*
*/
// EPIMSWWSEOABR.java,v
// Revision 1.4  2008/05/27 14:28:59  wendy
// Clean up RSA warnings
//
// Revision 1.3  2008/04/08 12:27:19  wendy
// MN 35084789 - support resend of lost notification messages.
// and MN 35178533 - ePIMS lost some geography data
// "SG FS ABR ePIMS Notification 20080407.doc"
//
// Revision 1.2  2008/01/30 19:39:16  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/11/16 19:31:36  nancy
// Init for GX EPIMS ABRs
//
public class EPIMSWWSEOABR extends EPIMSABRBase
{
    /**********************************
    * execute the derived class
    */
    protected void execute() throws Exception
    {
        addOutput("This is a GX ABR and not completed");
    }
    /**********************************
    * execute the derived class
    * /
    protected void executeGX() throws Exception
    {
        // The Data Quality ABR will only queue this ABR if WWSEO.STATUS is Final and
        // ePIMS Status is Not Ready.
        EntityItem rootEntity = epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
        // check value of STATUS attribute
        String epimsFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "EPIMSSTATUS");
        if (epimsFlag == null || epimsFlag.length()==0){
			epimsFlag = NOT_READY;
		}
        String statusFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "STATUS");
        addDebug("execute "+rootEntity.getKey()+" STATUS: "+
            PokUtils.getAttributeValue(rootEntity, "STATUS",", ", "", false)+" ["+statusFlag+"] "+
            "EPIMSSTATUS: "+
            PokUtils.getAttributeValue(rootEntity, "EPIMSSTATUS",", ", null, false)+" ["+epimsFlag+"] ");

        if (!STATUS_FINAL.equals(statusFlag)){
            addError("STATUS was not 'Final'");
            return;
        }
        // check value of EPIMSSTATUS attribute
        if (!NOT_READY.equals(epimsFlag)){
            addDebug("WARNING EPIMSSTATUS was not 'Not Ready'");
        }

        EPWQGenerator epwqGen = new EPWQGenerator(epimsAbr);

		String specbid = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "SPECBID");
		addDebug("execute "+rootEntity.getKey()+" SPECBID: "+specbid);

		// this must pull a new VE, the epimsAbr is just a dummy and gets a null ptr now
		// get VE name
		String VEname = getVeName();
		// create VE3 for lastfinal time
		EntityList list = epimsAbr.getDB().getEntityList(epimsAbr.getProfile(),
			new ExtractActionItem(null, epimsAbr.getDB(),epimsAbr.getProfile(),VEname),
			new EntityItem[] { rootEntity });

		 // debug display list of groups
		addDebug("execute dts: extract: "+VEname+NEWLINE +
			PokUtils.outputList(list));

		String epimsStatus = checkWWSEOLSEOFeatures(list,
			SPECBID_YES.equals(specbid), epwqGen);

		if (!epimsFlag.equals(epimsStatus)){
			setEPIMSSTATUS(epimsStatus);
		}

		list.dereference();
    }

	/*********************************
	*/
	protected void handleResend(EntityItem rootEntity, String statusFlag) throws
	java.sql.SQLException, MiddlewareException, javax.xml.parsers.ParserConfigurationException,
	javax.xml.transform.TransformerException, COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, java.io.IOException
	{
        addError("This is a GX ABR and not completed");
	}

    /**********************************
    * get the name of the VE to use
    */
    protected String getVeName() { return "EPIMSWWSEOVE2";}

    /**********************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.4";
    }
}
