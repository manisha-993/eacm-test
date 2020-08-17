package COM.ibm.eannounce.abr.sg.bh;

import java.util.Iterator;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.EntityItem;

public class SWSPRODSTRUCTABRSTATUS extends DQABRSTATUS{
	private Object args[] = new Object[3];

	/**********************************
	* always check if not final, but need navigation name from model and fc
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
	* C.	Status changed to Ready for Review
	1.	Set ADSABRSTATUS = 0020 (Queued)
    */
    protected void completeNowR4RProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {

    	EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
    	
    	//	IF			MAINTPRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
    	//	AND			MAINTPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
    	//	IF			MAINTMFAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
    	//	SET			MAINTPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
		String lifecycle = PokUtils.getAttributeFlagValue(rootEntity, "LIFECYCLE");
//		addDebug("doR4R_R10Processing: "+theItem.getKey()+" availRel "+availRel+" lifecycle "+lifecycle);
		if (lifecycle==null || lifecycle.length()==0){ 
			lifecycle = LIFECYCLE_Plan;
		}
		if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
				LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
			Vector availVct= PokUtils.getAllLinkedEntities(rootEntity, "SWSTMFAVAIL", "AVAIL");
			availloop:for (int ai=0; ai<availVct.size(); ai++){
				EntityItem avail = (EntityItem)availVct.elementAt(ai);
				if (statusIsRFRorFinal(avail)){				
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(rootEntity,"ADSABRSTATUS"),rootEntity);
					break availloop;
				}
			}// end avail loop
			availVct.clear();
		}
    	
    }

	/**********************************
	* complete abr processing after status moved to final; (status was r4r)
	D.	STATUS changed to Final

	1.	Set ADSABRSTATUS = 0020 (Queued)

	*
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		//IF			MAINTPRODSTRUCT	STATUS	=	"Final" (0020)			
		//IF		MAINTMFAVAIL-d	AVAIL	STATUS	=	"Final" (0020)			
		//SET			MAINTPRODSTRUCT				ADSABRSTATUS		&ADSFEED
			
		Vector availVct= PokUtils.getAllLinkedEntities(rootEntity, "SWSTMFAVAIL", "AVAIL");
		availloop:for (int ai=0; ai<availVct.size(); ai++){
			EntityItem avail = (EntityItem)availVct.elementAt(ai);	
			if (statusIsFinal(avail)){
				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
				break availloop;
			}
		}
		availVct.clear();
		
	}

    /**********************************
	2.00	MAINTFEATURE 		MAINTPRODSTRUCT-u								MAINTFEATURE 	
	3.00			STATUS	=>	MAINTPRODSTRUCT	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: MAINTFEATURE } {NDN: MAINTFEATURE }
			SVCMOD				MAINTPRODSTRUCT-d									
	4.00			STATUS	=>	MAINTPRODSTRUCT	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: SVCMOD} {NDN: SVCMOD} {LD: STATUS} {STATUS}

	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
    	addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");
    	
    	int checklvl = getCheck_W_E_E(statusFlag); 
		EntityItem mdlItem = m_elist.getEntityGroup("SVCMOD").getEntityItem(0); // has to exist
		EntityItem fcItem = m_elist.getEntityGroup("SWSFEATURE").getEntityItem(0); // has to exist
		
		checkStatusVsDQ(mdlItem, "STATUS", rootEntity, CHECKLEVEL_E);
		checkStatusVsDQ(fcItem, "STATUS", rootEntity, CHECKLEVEL_E);
		
		checkAvail(rootEntity);
				
	}

    /**
    5.00	AVAIL	A	MAINTPRODSTRUCT-u:MAINTMFAVAIL-d								Feature AVAIL	
	6.00	WHEN		AVAILTYPE	=	"Planned Availability"							
	7.00			CountOf	=>	1			RE*1	RE*1	RE*1		must have at lease one "Planned Availability"
	8.20	WHEN		"Final" (FINAL)	=	MAINTPRODSTRUCT	DATAQUALITY						
	8.22	IF		STATUS	=	"Ready for Review" (0040)							
	8.24	OR		STATUS	=	"Final" (0020)							
	8.26			CountOf	=>	1					RE*1		must have at least one "Planned Availability" that is either "Ready for Review" or "Final" in order to be "Final"
	8.28	END	8.20										
     */
    private void checkAvail(EntityItem rootEntity) {
    	
    	addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Planned Avail Checks:");

    	Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "SWSTMFAVAIL", "AVAIL");
    	Vector plannedPsAvailVector = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability
		
    	checkPlannedAvailsExist(plannedPsAvailVector, CHECKLEVEL_RE);
    	checkPlannedAvailsType(plannedPsAvailVector, CHECKLEVEL_RE);
    	checkPlannedAvailsStatus(plannedPsAvailVector, rootEntity, CHECKLEVEL_RE);
    	
    	plannedPsAvailVector.clear();
    	availVct.clear();
    	
	}
    
    protected void checkPlannedAvailsType(Vector plannedAvailVct, int checkLevel){
		
			if(plannedAvailVct != null && plannedAvailVct.size() > 0){
				boolean ishasRFRorFINALAvail = false;
				for (Iterator it = plannedAvailVct.iterator(); it.hasNext();) {
					EntityItem avail = (EntityItem) it.next();
					String avail_type = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
					addDebug("check plannedavail "+avail.getKey()+" AVAILANNTYPE: "+ avail_type);
					if(!"NORFA".equalsIgnoreCase(avail_type)){
						ishasRFRorFINALAvail = true;
						break;
					}				
				}
				if(ishasRFRorFINALAvail){
					args[0] = "Planned Availability type";
					args[1] = "NORFA";
					createMessage(checkLevel,"MUST_BE_ERR",args);
				}
			
		}
	}

	/***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "SWSPRODSTRUCT ABR";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.0";
    }
}
