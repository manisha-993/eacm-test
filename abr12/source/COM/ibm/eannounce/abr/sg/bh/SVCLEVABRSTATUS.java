//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;

import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* SVCLEVABRSTATUS class 
*
*need meta
*WFLCSVCLEVRFR and WFLCSVCLEVFINAL
*
*new ve DQVESVCLEV
*
* From "BH FS ABR Data Qualtity Sets 20110301.xls"
* 
SVCLEVABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.SVCLEVABRSTATUS
SVCLEVABRSTATUS_enabled=true
SVCLEVABRSTATUS_idler_class=A
SVCLEVABRSTATUS_keepfile=true
SVCLEVABRSTATUS_report_type=DGTYPE01
SVCLEVABRSTATUS_vename=DQVESVCLEV
*/
//$Log: SVCLEVABRSTATUS.java,v $
//Revision 1.1  2011/03/11 14:38:58  wendy
//Initial code
//
//
public class SVCLEVABRSTATUS extends DQABRSTATUS
{

/*
 * from sets ss
250.00	R1.0	SVCLEV									
251.00	R1.0	IF		SVCLEVSLCNTRYCON-D	Count(SLCNTRYCOND)		>	0			
252.00	R1.0	IF			SVCLEV	STATUS	=	"Final" (0020)			
253.00	R1.0	IF		SVCLEVSLCNTRYCON-D	SLCNTRYCOND	STATUS	=	"Final" (0020)			
254.00	R1.0	SET			SVCLEV				ADSABRSTATUS		&ADSFEED
255.00	R1.0	END	253.00								
256.00	R1.0	ELSE	252.00								
257.00	R1.0	IF			SVCLEV	STATUS	=	"Ready for Review" (0040)			
258.00	R1.0	AND			SLCNTRYCOND	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
258.20	R1.0	AND			SVCLEV	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
259.00	R1.0	SET			SVCLEV				ADSABRSTATUS	&ADSFEEDRFR	
260.00	R1.0	END	257.00								
261.00	R1.0	END	252.00								
262.00	R1.0	ELSE	251.00								
263.00	R1.0	IF			SVCLEV	STATUS	=	"Final" (0020)			
264.00	R1.0	SET			SVCLEV				ADSABRSTATUS		&ADSFEED
265.00	R1.0	ELSE	263.00								
266.00	R1.0	IF			SVCLEV	STATUS	=	"Ready for Review" (0040)			
266.20	R1.0	AND			SVCLEV	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
267.00	R1.0	SET			SVCLEV				ADSABRSTATUS	&ADSFEEDRFR
268.00	R1.0	END	266.00							
269.00	R1.0	END	263.00							
270.00	R1.0	END	251.00							
271.00	R1.0	END	250.00	SVCLEV						

												
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
		EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);
		String lifecycle = PokUtils.getAttributeFlagValue(rootItem, "LIFECYCLE");
		
		if (lifecycle==null || lifecycle.length()==0){ 
			lifecycle = LIFECYCLE_Plan;
		}

		Vector slcntryVct = PokUtils.getAllLinkedEntities(rootItem, "SVCLEVSLCNTRYCOND", "SLCNTRYCOND");
		addDebug("nowRFR: "+rootItem.getKey()+" slcntryVct.size "+slcntryVct.size()+" lifecycle "+lifecycle);
		if(slcntryVct.size()>0){
			//257.00	R1.0	IF			SVCLEV	STATUS	=	"Ready for Review" (0040)	
			// IF LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
			if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
					LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
				//258.20	R1.0	AND			SVCLEV	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
				for (int i=0; i<slcntryVct.size(); i++){
					EntityItem slcntry = (EntityItem)slcntryVct.elementAt(i);
					//258.00	R1.0	AND			SLCNTRYCOND	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
					if(statusIsRFRorFinal(slcntry)){
						//259.00	R1.0	SET			SVCLEV				ADSABRSTATUS	&ADSFEEDRFR	
						setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
						break;		
					}
					//260.00	R1.0	END	257.00		
				}
				slcntryVct.clear();
			}
		}else{
			//266.00	R1.0	IF			SVCLEV	STATUS	=	"Ready for Review" (0040)			
			//266.20	R1.0	AND			SVCLEV	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
			//267.00	R1.0	SET			SVCLEV				ADSABRSTATUS	&ADSFEEDRFR
			doRFR_ADSXML(rootItem);
			//268.00	R1.0	END	266.00	
		}
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
		EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);
		Vector slcntryVct = PokUtils.getAllLinkedEntities(rootItem, "SVCLEVSLCNTRYCOND", "SLCNTRYCOND");
		addDebug("nowFinal: "+rootItem.getKey()+" slcntryVct.size "+slcntryVct.size());
		if(slcntryVct.size()>0){
			//251.00	R1.0	IF		SVCLEVSLCNTRYCON-D	Count(SLCNTRYCOND)		>	0	
			for (int i=0; i<slcntryVct.size(); i++){
				//252.00	R1.0	IF			SVCLEV	STATUS	=	"Final" (0020)		
				EntityItem slcntry = (EntityItem)slcntryVct.elementAt(i);
				//222.00	R1.0	IF		SVCLEVSLCNTRYCON-u	SVCLEV	STATUS	=	"Final" (0020)	
				if(statusIsFinal(slcntry)){
					//253.00	R1.0	IF		SVCLEVSLCNTRYCON-D	SLCNTRYCOND	STATUS	=	"Final" (0020)			
					//254.00	R1.0	SET			SVCLEV				ADSABRSTATUS		&ADSFEED
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
					break;
					//255.00	R1.0	END	253.00			
				}
				//256.00	R1.0	ELSE	252.00	
			}
			slcntryVct.clear();
			//261.00	R1.0	END	252.00	
		}else{
			//262.00	R1.0	ELSE	251.00								
			//263.00	R1.0	IF			SVCLEV	STATUS	=	"Final" (0020)			
			//264.00	R1.0	SET			SVCLEV				ADSABRSTATUS		&ADSFEED
			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
			//265.00	R1.0	ELSE	263.00														
			//269.00	R1.0	END	263.00							
			//270.00	R1.0	END	251.00
		}
	}
	
    /* (non-Javadoc)
     * update LIFECYCLE value when STATUS is updated
     * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
     */ 
    protected String getLCRFRWFName(){ return "WFLCSVCLEVRFR";} 
    protected String getLCFinalWFName(){ return "WFLCSVCLEVFINAL";} 

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
        return "$Revision: 1.1 $";
    }
}
