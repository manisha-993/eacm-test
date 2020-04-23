//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg.bh;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* CHRGCOMPABRSTATUS class
*
*From "BH FS ABR Data Quality 20101112.doc"
*add OneValidOverTime
*
* From "BH FS ABR Data Quality 20101026.doc"
*
*need new ve DQVECHRGCOMP
CHRGCOMP	ENDDATE,EFFECTIVEDATE				
"CHRGCOMPCVM-d:CVM"	ENDDATE,EFFECTIVEDATE				

*
CHRGCOMPABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.CHRGCOMPABRSTATUS
CHRGCOMPABRSTATUS_enabled=true
CHRGCOMPABRSTATUS_idler_class=A
CHRGCOMPABRSTATUS_keepfile=true
CHRGCOMPABRSTATUS_report_type=DGTYPE01
CHRGCOMPABRSTATUS_vename=DQVECHRGCOMP
CHRGCOMPABRSTATUS_CAT1=RPTCLASS.CHRGCOMPABRSTATUS
CHRGCOMPABRSTATUS_CAT2=
CHRGCOMPABRSTATUS_CAT3=RPTSTATUS
*/
//$Log: CHRGCOMPABRSTATUS.java,v $
//Revision 1.3  2013/11/04 14:27:55  liuweimi
//Change based on document: BH FS ABR Data Quality 20130904b.doc to fix Defect:  BH 185136
//
//Revision 1.2  2010/11/16 17:17:32  wendy
//BH FS ABR Data Quality 20101112 updates
//
//Revision 1.1  2010/10/19 20:03:38  wendy
//Init for spec chg BH FS ABR Data Quality 20101012.xls
//
public class CHRGCOMPABRSTATUS extends DQABRSTATUS
{

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
1.00	CHRGCOMP		Root									
2.00			CHRGCOMPCVM-d		CVM							
3.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: CVM}
4.00			DATAQUALITY	<=	CVM	STATUS		E	E	E		{LD: STATUS} can not be higher than the {LD: CVM} {NDN: CVM} {LD: STATUS} {STATUS}
5.00	CHRGCOMP		Root									
6.00			CHRGCOMPPRCPT-d		PRCPT							
7.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: PRCPT}
8.00			DATAQUALITY	<=	PRCPT	STATUS		E	E	E		{LD: STATUS} can not be higher than the {LD: PRCPT} {NDN: PRCPT} {LD: STATUS} {STATUS}
9.00	CHRGCOMP		Root									
10.00				SetCHRGCOMPID				E	E	E		unable to generate the {LD: CHRGCOMPID}

Add	11.00	CHRGCOMP		Root									
Add	12.00				OneValidOverTime				E	E	E	For the CHRGCOMP, all children CVM are considered	must have at lease one valid {LD: CVM} during the time that the {LD: CHRGCOMP} is valid.
Add	13.00				ParentFrom	CHRGCOMP	EFFECTIVEDATE						
Add	14.00				ParentTo	CHRGCOMP	EFFECTIVE TO						
Add	15.00				ChildFrom	"CHRGCOMPCVM-d:CVM"	EFFECTIVEDATE						
Add	16.00				ChildTo	"CHRGCOMPCVM-d:CVM"	EFFECTIVE TO

	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
    	//2.00			CHRGCOMPCVM-d		CVM	
	   	int cnt = getCount("CHRGCOMPCVM");
		EntityGroup cvmGrp = m_elist.getEntityGroup("CVM");
    
		//3.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: CVM}
		if(cnt==0){
			//MINIMUM_ERR =  must have at least one {0}
			args[0] = cvmGrp.getLongDescription();
			createMessage(CHECKLEVEL_RE,"MINIMUM_ERR",args);
		}
		for (int i=0; i<cvmGrp.getEntityItemCount(); i++){
			EntityItem cvmitem = cvmGrp.getEntityItem(i);
			//4.00			DATAQUALITY	<=	CVM	STATUS		E	E	E		{LD: STATUS} can not be higher than the {LD: CVM} {NDN: CVM} {LD: STATUS} {STATUS}
			checkStatusVsDQ(cvmitem, "STATUS", rootEntity,CHECKLEVEL_E);
		}

		//6.00			CHRGCOMPPRCPT-d		PRCPT	
	   	cnt = getCount("CHRGCOMPPRCPT");
		EntityGroup prcptGrp = m_elist.getEntityGroup("PRCPT");
		//7.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: PRCPT}
		if(cnt==0){
			//MINIMUM_ERR =  must have at least one {0}
			args[0] = prcptGrp.getLongDescription();
			createMessage(CHECKLEVEL_RE,"MINIMUM_ERR",args);
		}
		for (int i=0; i<prcptGrp.getEntityItemCount(); i++){
			EntityItem prcptitem = prcptGrp.getEntityItem(i);
			//8.00			DATAQUALITY	<=	PRCPT	STATUS		E	E	E		{LD: STATUS} can not be higher than the {LD: PRCPT} {NDN: PRCPT} {LD: STATUS} {STATUS}
			checkStatusVsDQ(prcptitem, "STATUS", rootEntity,CHECKLEVEL_E);
		}
		if (this.getReturnCode()==PASS){
			//10.00				SetCHRGCOMPID				E	E	E		unable to generate the {LD: CHRGCOMPID}
			setCHRGCOMPID(rootEntity);
		}
		//Add	11.00	CHRGCOMP		Root									
		//Add	12.00				OneValidOverTime				E	E	E	For the CHRGCOMP, all children CVM are considered	must have at lease one valid {LD: CVM} during the time that the {LD: CHRGCOMP} is valid.
		//Add	13.00				ParentFrom	CHRGCOMP	EFFECTIVEDATE			
		//Add	14.00				ParentTo	CHRGCOMP	ENDDATE						
		//Add	15.00				ChildFrom	"CHRGCOMPCVM-d:CVM"	EFFECTIVEDATE				
		//Add	16.00				ChildTo	"CHRGCOMPCVM-d:CVM"	ENDDATE			
		oneValidOverTime(rootEntity, new String[]{"CHRGCOMPCVM", "CVM"},CHECKLEVEL_E);
    }

    /**********************************
     * complete abr processing after status moved to final; (status was r4r)
     *C. STATUS changed to Final
50.02	R1.0	CHRGCOMP									
50.04	R1.0	IF		SVCMODCHRGCOMP-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
50.06	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
50.08	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
50.10	R1.0	ELSE	50.04								
50.12	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
50.14	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)			
50.16	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
50.18	R1.0	END	50.12								
50.20	R1.0	END	50.04								
50.22		END	50.02	CHRGCOMP														
     */
    protected void completeNowFinalProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	EntityGroup svcmodGrp = m_elist.getEntityGroup("SVCMOD");
    	for (int i=0; i<svcmodGrp.getEntityItemCount(); i++){
    		EntityItem svcmod = svcmodGrp.getEntityItem(i);
//        	String lifecycle = PokUtils.getAttributeFlagValue(svcmod, "LIFECYCLE");
//    	  	addDebug("completeNowFinalProcessing: "+svcmod.getKey()+" lifecycle "+lifecycle);
//    		if (lifecycle==null || lifecycle.length()==0){ 
//        		lifecycle = LIFECYCLE_Plan;
//        	}
    		//50.12	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
        	if (statusIsFinal(svcmod)){
        		//50.16	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
        		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
        	}else{
        		//50.04	R1.0	IF		CHRGCOMPSVCMOD-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
        		//50.06	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
            	if (statusIsRFR(svcmod)){// && (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
            			//LIFECYCLE_Develop.equals(lifecycle))){ // been RFR before
            		// 50.08	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
            		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
            	}
        	}
    	}
    }
    /**********************************
     * complete abr processing after status moved to readyForReview; (status was chgreq)
 	* B.	Status changed to Ready for Review	
50.02	R1.0	CHRGCOMP									
50.04	R1.0	IF		SVCMODCHRGCOMP-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
50.06	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
50.08	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
50.10	R1.0	ELSE	50.04								
50.12	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
50.14	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)			
50.16	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
50.18	R1.0	END	50.12								
50.20	R1.0	END	50.04								
50.22		END	50.02	CHRGCOMP							

     */
    protected void completeNowR4RProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	EntityGroup svcmodGrp = m_elist.getEntityGroup("SVCMOD");
    	for (int i=0; i<svcmodGrp.getEntityItemCount(); i++){
    		EntityItem svcmod = svcmodGrp.getEntityItem(i);
//        	String lifecycle = PokUtils.getAttributeFlagValue(svcmod, "LIFECYCLE");
//    	  	addDebug("completeNowR4RProcessing: "+svcmod.getKey()+" lifecycle "+lifecycle);
//    		if (lifecycle==null || lifecycle.length()==0){ 
//        		lifecycle = LIFECYCLE_Plan;
//        	}
    		//50.04	R1.0	IF		CHRGCOMPSVCMOD-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
    		//50.06	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
        	if (statusIsRFR(svcmod)){
//        		&& (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//        	}
//        			LIFECYCLE_Develop.equals(lifecycle))){ // been RFR before
        		// 50.08	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
        		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
        	}
    	}
    }
	
    /*******************
     *Y.	SetCHRGCOMPID
     *
     * This function only applies to a Chargeable Component (CHRGCOMP). It is invoked only if the Data Quality 
     * Checks pass (i.e. after all of the checks) but prior to the SETS spreadsheet logic. If the generation of 
     * the Chargeable Component Id fails, then the ABR should fail.
     * 
     * This checks Chargeable Component (CHRGCOMP) to ensure that the Chargeable Component Id (CHRGCOMPID) is 
     * filled in and unique.
     * 
     * If Value of (CHRGCOMPID) is Empty (aka Null), then generate CHRGCOMPID as follows:
     * �CC� & Right(�0000� & EntityId,5)
     * 
     * e.g. If the EntityId of the CHRGCOMP  = 23 then
     * CHRGCOMPID = �CC00023�
     * 
     */
    private void setCHRGCOMPID(EntityItem rootItem){
    	String chrgcompid = PokUtils.getAttributeValue(rootItem, "CHRGCOMPID", "", null, false);
	  	addDebug("setCHRGCOMPID: chrgcompid "+chrgcompid);
	  	if (chrgcompid==null){
	  		String id = "0000"+rootItem.getEntityID();
	  		int idlen = id.length();
	  		if(idlen>5){
	  			id = id.substring(idlen-5);
	  		}
	  		id = "CC"+id;
	  		addDebug("setCHRGCOMPID: new chrgcompid "+id);
	  		setTextValue(m_elist.getProfile(), "CHRGCOMPID", id, rootItem);
	  	}
    }
    
    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "CHRGCOMP ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "$Revision: 1.3 $";
    }
}
