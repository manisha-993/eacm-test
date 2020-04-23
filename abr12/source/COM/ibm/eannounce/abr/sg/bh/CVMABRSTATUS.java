//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg.bh;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* CVMABRSTATUS class
*
*From "BH FS ABR Data Quality 20110801.xls" - IN1076762 - only allow V and 6 chars long for VMID
*From "BH FS ABR Data Quality 20101112.doc"
*add OneValidOverTime
* From "BH FS ABR Data Quality 20101103.doc"
*
*need new ve DQVECVM
*CVM	ENDDATE,EFFECTIVEDATE		
"CVMCVMSPEC-d:CVMSPEC"	ENDDATE,EFFECTIVEDATE							
*
*
CVMABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.CVMABRSTATUS
CVMABRSTATUS_enabled=true
CVMABRSTATUS_idler_class=A
CVMABRSTATUS_keepfile=true
CVMABRSTATUS_report_type=DGTYPE01
CVMABRSTATUS_vename=DQVECVM
CVMABRSTATUS_CAT1=RPTCLASS.CVMABRSTATUS
CVMABRSTATUS_CAT2=
CVMABRSTATUS_CAT3=RPTSTATUS
*/
//$Log: CVMABRSTATUS.java,v $
//Revision 1.4  2013/11/04 14:27:55  liuweimi
//Change based on document: BH FS ABR Data Quality 20130904b.doc to fix Defect:  BH 185136
//
//Revision 1.3  2011/08/02 02:06:48  wendy
//"BH FS ABR Data Quality 20110801.xls" - IN1076762
//
//Revision 1.2  2010/11/16 17:17:32  wendy
//BH FS ABR Data Quality 20101112 updates
//
//Revision 1.1  2010/10/19 20:03:38  wendy
//Init for spec chg BH FS ABR Data Quality 20101012.xls
//
public class CVMABRSTATUS extends DQABRSTATUS
{
//CVMTYPE	C1	Charac
	private static final String CVMTYPE_Characteristic	= "C1"; 
	
    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
1.00	CVM		Root									
2.00			CVMCVMSPEC-d		CVMSPEC							
3.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: CVMSPEC}
4.00	WHEN		CVMTYPE	=	CVMSPEC	CVMTYPE						
5.00			DATAQUALITY	<=	CVMSPEC	STATUS		E	E	E		{LD: STATUS} can not be higher than the {LD: CVMSPEC} {NDN: CVMSPEC} {LD: STATUS} {STATUS}
6.00	END	4.00										
7.00			CVMTYPE	=	CVMSPEC	CVMTYPE		E	E	E	Parent CVM and Child CVMSPEC must have identical values for CVMTYPE	{LD: CVM} {NDN: CVM} {LD: CVMTYPE} {CVMTYPE} is not equal to {LD: CVMSPEC} {NDN: CVMSPEC} {LD: CVMTYPE} {CCVMTYPE}
8.00	IF		CVMTYPE	=	"Characteristic" (CVMC)							
9.00			CHARACID	=	CVMSPEC	CHARACID		E	E	E	Parent CVM and Child CVMSPEC must have identical values for CHARACID	{LD: CVM} {NDN: CVM} {LD: CHARACID} {CHARACID} is not equal to {LD: CVMSPEC} {NDN: CVMSPEC} {LD: CHARACID} {CHARACID}
10.00	ELSE	8.00										
Change 20110801	11.00			 "V"	=	CVM	LEFT(VMID,1)		E	E	E		{LD: CVM} {NDN: CVM} {LD: VMID} {VMID} must start with the upper case character "V"				
Change 20110801	13.00			Integer	Must Be	CVM	RIGHT(VMID,5)		E	E	E	The 5 right most characters must be integer	{LD: CVM} {NDN: CVM} {LD: VMID} {VMID} must be integers other than the first character				
14.00	END	8
Add	15.00	CVM		Root									
Add	16.00				OneValidOverTime				E	E	E	For the CVM, all children CVMSPEC are considered	must have at lease one valid {LD: CVMSPEC} during the time that the {LD: CVM is valid.
Add	17.00				ParentFrom	CVM	EFFECTIVEDATE
Add	18.00				ParentTo	CVM	EFFECTIVE TO
Add	19.00				ChildFrom	"CVMCVMSPEC-d:CVMSPEC"	EFFECTIVEDATE
Add	20.00				ChildTo	"CVMCVMSPEC-d:CVMSPEC"	EFFECTIVE TO

	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
    	String characid = PokUtils.getAttributeFlagValue(rootEntity, "CHARACID");
       	String vmid = PokUtils.getAttributeValue(rootEntity, "VMID", "", "", false);
       	String cvmtype = PokUtils.getAttributeFlagValue(rootEntity, "CVMTYPE");
  	  	addDebug("doDQChecking: "+rootEntity.getKey()+" characid: "+characid+" cvmtype: "+cvmtype+" vmid: "+vmid);
	  	if(cvmtype==null) cvmtype=""; // supposed to have EXIST rule but doesnt
	  	
    	//2.00			CVMCVMSPEC-d		CVMSPEC							
	   	int cnt = getCount("CVMCVMSPEC");
		EntityGroup cvmspecGrp = m_elist.getEntityGroup("CVMSPEC");
		//3.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: CVMSPEC}
		if(cnt==0){
			//MINIMUM_ERR =  must have at least one {0}
			args[0] = cvmspecGrp.getLongDescription();
			createMessage(CHECKLEVEL_RE,"MINIMUM_ERR",args);
		}
    	
		for (int i=0; i<cvmspecGrp.getEntityItemCount(); i++){
			EntityItem cvmspecitem = cvmspecGrp.getEntityItem(i);
	       	String cvmspecCvmtype = PokUtils.getAttributeFlagValue(cvmspecitem, "CVMTYPE");
			String cvmcharacid = PokUtils.getAttributeFlagValue(cvmspecitem, "CHARACID");
	  	  	addDebug("doDQChecking: "+cvmspecitem.getKey()+" cvmspecCvmtype: "+cvmspecCvmtype+" cvmcharacid: "+cvmcharacid);
	  	  	//4.00	WHEN		CVMTYPE	=	CVMSPEC	CVMTYPE						
	  	  	if(cvmtype.equals(cvmspecCvmtype)){ 
		  	  	//5.00			DATAQUALITY	<=	CVMSPEC	STATUS		E	E	E		{LD: STATUS} can not be higher than the {LD: CVMSPEC} {NDN: CVMSPEC} {LD: STATUS} {STATUS}
	  	  		checkStatusVsDQ(cvmspecitem, "STATUS", rootEntity,CHECKLEVEL_E);
	  	  	  	//6.00	END	4.00
	  	  	}else {
	  	  		//7.00			CVMTYPE	=	CVMSPEC	CVMTYPE		E	E	E	Parent CVM and Child CVMSPEC must have identical values for CVMTYPE	
	  	  		//{LD: CVM} {NDN: CVM} {LD: CVMTYPE} {CVMTYPE} is not equal to {LD: CVMSPEC} {NDN: CVMSPEC} {LD: CVMTYPE} {CCVMTYPE}
		    	//NOT_EQUAL_ERR = {0} {1} is not equal to {2} {3}
				args[0] = "";
				args[1] = this.getLD_Value(rootEntity, "CVMTYPE");
				args[2] = this.getLD_NDN(cvmspecitem);
				args[3] = this.getLD_Value(cvmspecitem, "CVMTYPE");
				createMessage(CHECKLEVEL_E,"NOT_EQUAL_ERR",args);
	  	  	}

	  	  	//8.00	IF		CVMTYPE	=	"Characteristic" (CVMC)							
	  	  	if (CVMTYPE_Characteristic.equals(cvmtype)){
		  	  	//9.00			CHARACID	=	CVMSPEC	CHARACID		E	E	E	Parent CVM and Child CVMSPEC must have identical values for CHARACID	
		  	  	//{LD: CVM} {NDN: CVM} {LD: CHARACID} {CHARACID} is not equal to {LD: CVMSPEC} {NDN: CVMSPEC} {LD: CHARACID} {CHARACID}
	  	  		if(!characid.equals(cvmcharacid)){
	  	  			//NOT_EQUAL_ERR = {0} {1} is not equal to {2} {3}
	  	  			args[0] = "";
	  	  			args[1] = this.getLD_Value(rootEntity, "CHARACID");
	  	  			args[2] = this.getLD_NDN(cvmspecitem);
	  	  			args[3] = this.getLD_Value(cvmspecitem, "CHARACID");
	  	  			createMessage(CHECKLEVEL_E,"NOT_EQUAL_ERR",args);
	  	  		}
	  	  	}

		} // end CVMSPEC loop
		//	8.00	IF		CVMTYPE	=	"Characteristic" (CVMC)	
		//10.00	ELSE	8.00										
		if (!CVMTYPE_Characteristic.equals(cvmtype)){
			//11.00			 "V"	=	CVM	LEFT(VMID,1)		E	E	E		{LD: CVM} {NDN: CVM} {LD: VMID} {VMID} must start with the upper case character "V"				
			if(!vmid.startsWith("V")){
				//MUST_STARTWITH_ERR = {0} must start with the upper case character &quot;V&quot;.
				args[0] = this.getLD_Value(rootEntity, "VMID"); 
				createMessage(CHECKLEVEL_E,"MUST_STARTWITH_ERR",args);
			}

			//13.00			Integer	Must Be	CVM	RIGHT(VMID,5)		E	E	E	The 5 right most characters must be integer	{LD: CVM} {NDN: CVM} {LD: VMID} {VMID} must be integers other than the first character				
			if(vmid.length() != 6){
				//LENGTH_ERR = {0} must be {1} characters long
				args[0] = this.getLD_Value(rootEntity, "VMID"); 
				args[1] = "6"; 
				createMessage(CHECKLEVEL_E,"LENGTH_ERR",args);
			}else{
				StringCharacterIterator sci = new StringCharacterIterator(vmid.substring(1));
				char ch = sci.first();
				while(ch != CharacterIterator.DONE){
					if (!Character.isDigit(ch)){
						//INTEGER_ERR = {0} must be integers other than the first character
						args[0] = this.getLD_Value(rootEntity, "VMID"); 
						createMessage(CHECKLEVEL_E,"INTEGER_ERR",args);
						break;
					}

					ch = sci.next();
				}
			}
		}
		//	14.00	END	8	
		//Add	15.00	CVM		Root									
		//Add	16.00				OneValidOverTime				E	E	E	For the CVM, all children CVMSPEC are considered	must have at lease one valid {LD: CVMSPEC} during the time that the {LD: CVM is valid.
		//Add	17.00				ParentFrom	CVM	EFFECTIVEDATE
		//Add	18.00				ParentTo	CVM	ENDDATE
		//Add	19.00				ChildFrom	"CVMCVMSPEC-d:CVMSPEC"	EFFECTIVEDATE
		//Add	20.00				ChildTo	"CVMCVMSPEC-d:CVMSPEC"	ENDDATE
		oneValidOverTime(rootEntity, new String[]{"CVMCVMSPEC", "CVMSPEC"}, CHECKLEVEL_E);
    }

    /**********************************
     * complete abr processing after status moved to final; (status was r4r)
     *C. STATUS changed to Final
52.02	R1.0	CVM									
52.04	R1.0	IF		"CHRGCOMPCVM-u: SVCMODCHRGCOMP-u"	CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
52.06	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
52.08	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
52.10	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
52.12	R1.0	ELSE	52.04								
52.14	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
52.16	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)			
52.18	R1.0	AND			CVM	STATUS	=	"Final" (0020)			
52.20	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
52.22	R1.0	END	52.14								
52.24	R1.0	END	52.04								
						
     */
    protected void completeNowFinalProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	EntityGroup chrgcompGrp = m_elist.getEntityGroup("CHRGCOMP");
    	for (int i=0; i<chrgcompGrp.getEntityItemCount(); i++){
    		EntityItem chrgcomp = chrgcompGrp.getEntityItem(i);
    		addDebug("completeNowFinalProcessing: "+chrgcomp.getKey());
    		//52.04	R1.0	IF		"CHRGCOMPCVM-u:SVCMODCHRGCOMP-u"	CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
    		if (this.statusIsRFRorFinal(chrgcomp)){
    			Vector svcmodVct = PokUtils.getAllLinkedEntities(chrgcomp, "SVCMODCHRGCOMP", "SVCMOD");
    			for (int s=0; s<svcmodVct.size(); s++)	{
    				EntityItem svcmod = (EntityItem)svcmodVct.elementAt(s);
//    	        	String lifecycle = PokUtils.getAttributeFlagValue(svcmod, "LIFECYCLE");
//    	    	  	addDebug("completeNowFinalProcessing: "+svcmod.getKey()+" lifecycle "+lifecycle);
//    	    		if (lifecycle==null || lifecycle.length()==0){ 
//    	        		lifecycle = LIFECYCLE_Plan;
//    	        	}
    	    		//52.14	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)	
    	    		if (statusIsFinal(svcmod)){
    	    			//52.16	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)	
    	    			if(statusIsFinal(chrgcomp)){
    	    				//52.18	R1.0	AND			CVM	STATUS	=	"Final" (0020)			
    	    				//52.20	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
    	    				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
    	    			}
    				}else{
    			 		//52.06	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
        	    			
//    					20130904 Delete	Queue if was once Final	52.080		AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)				Was never Final	

        		    	if (this.statusIsRFR(svcmod)){ // been RFR before
            	    		//52.10	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
        					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
        				}
    				}
    			}
    			svcmodVct.clear();
    		}
    	}
    }
    /**********************************
     * complete abr processing after status moved to readyForReview; (status was chgreq)
 	* B.	Status changed to Ready for Review	
52.02	R1.0	CVM									
52.04	R1.0	IF		"CHRGCOMPCVM-u: SVCMODCHRGCOMP-u"	CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
52.06	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
52.08	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
52.10	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
52.12	R1.0	ELSE	52.04								
52.14	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
52.16	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)			
52.18	R1.0	AND			CVM	STATUS	=	"Final" (0020)			
52.20	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
52.22	R1.0	END	52.14								
52.24	R1.0	END	52.04								
	
     */
    protected void completeNowR4RProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	EntityGroup chrgcompGrp = m_elist.getEntityGroup("CHRGCOMP");
    	for (int i=0; i<chrgcompGrp.getEntityItemCount(); i++){
    		EntityItem chrgcomp = chrgcompGrp.getEntityItem(i);
    		addDebug("completeNowR4RProcessing: "+chrgcomp.getKey());
    		//52.04	R1.0	IF		"CHRGCOMPCVM-u: CHRGCOMPSVCMOD-u"	CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
    		if (this.statusIsRFRorFinal(chrgcomp)){
    			Vector svcmodVct = PokUtils.getAllLinkedEntities(chrgcomp, "SVCMODCHRGCOMP", "SVCMOD");
    			for (int s=0; s<svcmodVct.size(); s++)	{
    				EntityItem svcmod = (EntityItem)svcmodVct.elementAt(s);
//    		    	String lifecycle = PokUtils.getAttributeFlagValue(svcmod, "LIFECYCLE");
//    		    	addDebug("completeNowR4RProcessing: "+svcmod.getKey()+" lifecycle "+lifecycle);
//    		    	if (lifecycle==null || lifecycle.length()==0){ 
//    		    		lifecycle = LIFECYCLE_Plan;
//    		    	}
    	    		//52.06	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
//					20130904 Delete	Queue if was once Final	52.080		AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)				Was never Final
    		    	if (this.statusIsRFR(svcmod)){ 
        	    		//52.10	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
    					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
    				}
    			}
    			svcmodVct.clear();
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
        String desc =  "CVM ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "$Revision: 1.4 $";
    }
}
