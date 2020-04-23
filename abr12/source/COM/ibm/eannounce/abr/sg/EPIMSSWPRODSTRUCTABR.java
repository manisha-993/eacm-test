// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
* EPIMSSWPRODSTRUCTABR class
*
* From "SG FS ABR ePIMS InAndOut Bound 20071010.doc"
* The Data Quality ABR will only queue this ABR if SWPRODSTUCT.STATUS is Final and ePIMS
* Status (EPIMSSTATUS) is Not Ready.
*
* SWPRODUSTRUCT:MODEL as m
* SWPRODSTRUCT:SWFEATURE as f
* EPSWFEATURE as epswf
*
* If epswf.EPSTATUS exists where m.MACHTYPEATR = epf.EPMACHTYPE and m.MODELATR = epswf.MODEL and
* f.FEATURECODE = epswf.EFEATURECODE then
* 	Update SWPRODSTRUCT.EPIMSSTATUS = epswf.EPSTATUS
* Else
* 	Set SWPRODSTRUCT.EPIMSSTATUS to 'Waiting' (EPWAIT)
* 	Add SWPRODSTRUCT to EPwaitingQueue
* End If
*
* EPwaitingQueue (EPWAITINGQUEUE)
* EPWAITET		Set = 'SWPRODSTRUCT'
* EPWAITEID		Set = SWPRODSTRUCT.entityid
* EPONET		Set = 'SWPRODSTRUCT'
* EPONEID		Set = SWPRODSTRUCT.entityid
* EPMACHTYPE	Set = m.MACHTYPEATR
* EPMODELATR	Set = m.MODELATR
* EPFEATURE		Set = f.FEATURECODE
* EPSALESORG	not set
* EPSTATUS		Set = 'Promoted'
*
*/
// EPIMSSWPRODSTRUCTABR.java,v
// Revision 1.4  2008/05/27 14:28:59  wendy
// Clean up RSA warnings
//
// Revision 1.3  2008/04/08 12:27:19  wendy
// MN 35084789 - support resend of lost notification messages.
// and MN 35178533 - ePIMS lost some geography data
// "SG FS ABR ePIMS Notification 20080407.doc"
//
// Revision 1.2  2008/01/30 19:39:14  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/11/16 19:31:36  nancy
// Init for GX EPIMS ABRs
//
public class EPIMSSWPRODSTRUCTABR extends EPIMSABRBase
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
		//The Data Quality ABR will only queue this ABR if SWPRODSTRUCT.STATUS is Final
		// and ePIMS Status (EPIMSSTATUS) is 'Not Ready'.
		EntityItem rootEntity = epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
        // check value of STATUS attribute
        String epimsFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "EPIMSSTATUS");
        if (epimsFlag == null || epimsFlag.length()==0){
			epimsFlag = NOT_READY;
		}
        String statusFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "STATUS");
        addDebug("execute: STATUS: "+
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

        EntityItem modelItem = epimsAbr.getEntityList().getEntityGroup("MODEL").getEntityItem(0);
        EntityItem featItem = epimsAbr.getEntityList().getEntityGroup("SWFEATURE").getEntityItem(0);

        // search for EPSWFEATURE
        StringBuffer sb = new StringBuffer();
        sb.append("map_EPMACHTYPE=" +  pdgUtility.getAttrValue(modelItem, "MACHTYPEATR")+ ";");
        sb.append("map_EPMODELATR=" +  pdgUtility.getAttrValue(modelItem, "MODELATR")+ ";");
        sb.append("map_EPFEATURECODE=" +  pdgUtility.getAttrValue(featItem, "FEATURECODE"));
        String strSai = "SRDEPSWFEATURE";
        / *
		EPSWFEATURE entity
			EPSWFEATUREABR  A   EPSWFEATURE ABR
			EPFEATURECODE   T   Feature code
			EPMACHTYPE      T   Machine Type
			EPMODELATR  	T   Model
			EPNOTIFYTIME    T   Notification Time
			EPSTATUS        T   ePIMs Status
			PDHDOMAIN
        * /

        EntityItem[] aei = epSearch(sb,strSai, "EPSWFEATURE");
        if (aei==null || aei.length==0){ // no EPSWFEATURE found
            //  Set SWPRODSTRUCT.EPIMSSTATUS to 'Waiting' (EPWAIT)
            setEPIMSSTATUS(WAITING);

            //  Add SWPRODSTRUCT to EPwaitingQueue
            EPWQGenerator epwqGen = new EPWQGenerator(epimsAbr);
            // add the attributes
            // EPWAITET     Set = 'SWPRODSTRUCT'
            epwqGen.addAttribute(EPWQGenerator.EPWAITET, rootEntity.getEntityType());
            // EPWAITEID    Set = SWPRODSTRUCT.entityid
            epwqGen.addAttribute(EPWQGenerator.EPWAITEID, ""+rootEntity.getEntityID());
            // EPONET       Set = 'SWPRODSTRUCT'
            epwqGen.addAttribute(EPWQGenerator.EPONET, rootEntity.getEntityType());
            // EPONEID      Set = SWPRODSTRUCT.entityid
            epwqGen.addAttribute(EPWQGenerator.EPONEID, ""+rootEntity.getEntityID());
            // EPMACHTYPE   Set = SWPRODSTRUCT:MODEL.MACHTYPEATR
            epwqGen.addAttribute(EPWQGenerator.EPMACHTYPE,
                epimsAbr.getAttributeFlagEnabledValue(modelItem,"MACHTYPEATR"));
            // EPMODELATR   Set = SWPRODSTRUCT:MODEL.MODELATR
            epwqGen.addAttribute(EPWQGenerator.EPMODELATR,
                epimsAbr.getAttributeFlagEnabledValue(modelItem,"MODELATR"));
            // EPFEATURECODE    Set = SWPRODSTRUCT:SWFEATURE.FEATURECODE
            epwqGen.addAttribute(EPWQGenerator.EPFEATURECODE,
                PokUtils.getAttributeValue(featItem, "FEATURECODE",", ", "", false));
            // EPSTATUS     Set = 'Promoted'
            epwqGen.addAttribute(EPWQGenerator.EPSTATUS, PROMOTED);

            // create the EPWAITINGQUEUE
            epwqGen.createEntity();
		}else{
			// found one EPSWFEATURE
			//  Update SWPRODSTRUCT.EPIMSSTATUS = epswf.EPSTATUS
			updateEPIMSSTATUS(PokUtils.getAttributeValue(aei[0], "EPSTATUS",", ", "", false));
		}
    }

    /**********************************
    * get the name of the VE to use
    */
    protected String getVeName() { return "EPIMSSWPRODSTRUCTVE";}

	/*********************************
	*/
	protected void handleResend(EntityItem rootEntity, String statusFlag) throws
	java.sql.SQLException, MiddlewareException, javax.xml.parsers.ParserConfigurationException,
	javax.xml.transform.TransformerException, COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, java.io.IOException
	{
        addError("This is a GX ABR and not completed");
	}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.4";
    }
}
