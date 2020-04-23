//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;

import java.util.Vector;

import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
* SLCNTRYABRSTATUS class 
*
*new ve DQVESLCNTRYCOND
*
* From "BH FS ABR Data Qualtity Sets 20110301.xls"
* 
SLCNTRYABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.SLCNTRYABRSTATUS
SLCNTRYABRSTATUS_enabled=true
SLCNTRYABRSTATUS_idler_class=A
SLCNTRYABRSTATUS_keepfile=true
SLCNTRYABRSTATUS_report_type=DGTYPE01
SLCNTRYABRSTATUS_vename=DQVESLCNTRYCOND
*/
//$Log: SLCNTRYABRSTATUS.java,v $
//Revision 1.2  2011/03/15 11:13:25  wendy
//fix comment
//
//Revision 1.1  2011/03/11 14:38:58  wendy
//Initial code
//
//
public class SLCNTRYABRSTATUS extends DQABRSTATUS
{

/*
 * from sets ss								
220.00	R1.0	SLCNTRYCOND									
221.00	R1.0	IF			SLCNTRYCOND	STATUS	=	"Final" (0020)			
222.00	R1.0	IF		SVCLEVSLCNTRYCON-u	SVCLEV	STATUS	=	"Final" (0020)			
223.00	R1.0	SET			SVCLEV				ADSABRSTATUS		&ADSFEED
224.00	R1.0	END	222.00								
225.00	R1.0	ELSE	221.00								
226.00	R1.0	IF			SLCNTRYCOND	STATUS	=	"Ready for Review" (0040)			
227.00	R1.0	AND			SVCLEV	STATUS	=	"Ready for Review" (0040)			
228.00	R1.0	AND			SVCLEV	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
229.00	R1.0	SET			SVCLEV				ADSABRSTATUS	&ADSFEEDRFR	
230.00	R1.0	END	226.00								
231.00	R1.0	END	221.00								
											
 */	
	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq)
	 * C.	Status changed to Ready for Review
	 * 
220.00	R1.0	SLCNTRYCOND																
226.00	R1.0	IF			SLCNTRYCOND	STATUS	=	"Ready for Review" (0040)			
227.00	R1.0	AND			SVCLEV	STATUS	=	"Ready for Review" (0040)			
228.00	R1.0	AND			SVCLEV	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
229.00	R1.0	SET			SVCLEV				ADSABRSTATUS	&ADSFEEDRFR	
230.00	R1.0	END	226.00								
231.00	R1.0	END	221.00	
	 */
	protected void completeNowR4RProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		//setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
		EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);
		Vector svclevVct = PokUtils.getAllLinkedEntities(rootItem, "SVCLEVSLCNTRYCOND", "SVCLEV");
		addDebug("nowRFR: "+rootItem.getKey()+" svclevVct.size "+svclevVct.size());
		for (int i=0; i<svclevVct.size(); i++){
			EntityItem svclev = (EntityItem)svclevVct.elementAt(i);
			//227.00	R1.0	AND			SVCLEV	STATUS	=	"Ready for Review" (0040)			
			//228.00	R1.0	AND			SVCLEV	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
			//229.00	R1.0	SET			SVCLEV				ADSABRSTATUS	&ADSFEEDRFR	
			doRFR_ADSXML(svclev);
		}
		svclevVct.clear();
	}

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
	 *D. STATUS changed to Final
	 *
221.00	R1.0	IF			SLCNTRYCOND	STATUS	=	"Final" (0020)			
222.00	R1.0	IF		SVCLEVSLCNTRYCON-u	SVCLEV	STATUS	=	"Final" (0020)			
223.00	R1.0	SET			SVCLEV				ADSABRSTATUS		&ADSFEED
224.00	R1.0	END	222.00	
	 */
	protected void completeNowFinalProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);
		Vector svclevVct = PokUtils.getAllLinkedEntities(rootItem, "SVCLEVSLCNTRYCOND", "SVCLEV");
		addDebug("nowFinal: "+rootItem.getKey()+" svclevVct.size "+svclevVct.size());
		for (int i=0; i<svclevVct.size(); i++){
			EntityItem svclev = (EntityItem)svclevVct.elementAt(i);
			//222.00	R1.0	IF		SVCLEVSLCNTRYCON-u	SVCLEV	STATUS	=	"Final" (0020)	
			if(statusIsFinal(svclev)){
				//223.00	R1.0	SET			SVCLEV				ADSABRSTATUS		&ADSFEED
				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(svclev, "ADSABRSTATUS"), svclev);
			}
		}
		svclevVct.clear();
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
        String desc = "SVCLEV ABR.";
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
