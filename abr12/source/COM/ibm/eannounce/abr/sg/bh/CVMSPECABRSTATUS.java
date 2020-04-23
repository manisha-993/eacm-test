//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg.bh;

import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* CVMSPECABRSTATUS class
*
* From "BH FS ABR Data Quality 20101012.doc"
*
*need new ve - DQVECVMSPEC
*
CVMSPECABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.CVMSPECABRSTATUS
CVMSPECABRSTATUS_enabled=true
CVMSPECABRSTATUS_idler_class=A
CVMSPECABRSTATUS_keepfile=true
CVMSPECABRSTATUS_report_type=DGTYPE01
CVMSPECABRSTATUS_vename=DQVECVMSPEC
CVMSPECABRSTATUS_CAT1=RPTCLASS.CVMSPECABRSTATUS
CVMSPECABRSTATUS_CAT2=
CVMSPECABRSTATUS_CAT3=RPTSTATUS
*/
//$Log: CVMSPECABRSTATUS.java,v $
//Revision 1.2  2013/11/04 14:27:55  liuweimi
//Change based on document: BH FS ABR Data Quality 20130904b.doc to fix Defect:  BH 185136
//
//Revision 1.1  2010/10/19 20:03:38  wendy
//Init for spec chg BH FS ABR Data Quality 20101012.xls
//
public class CVMSPECABRSTATUS extends DQABRSTATUS
{

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
1.00	CVMSPEC		Root									
2.00			CVMCVMSPEC-u		CVM							
3.00			CHARACID	=	CVM	CHARACID		E	E	E	Parent CVM and Child CVMSPEC must have identical values for CHARACID	{LD: CVM} {NDN: CVM} {LD: CHARACID} {CHARACID} is not equal to {LD: CVMSPEC} {NDN: CVMSPEC} {LD: CHARACID} {CHARACID}
4.00			CVMTYPE	=	CVM	CVMTYPE		E	E	E	Parent CVM and Child CVMSPEC must have identical values for CVMTYPE	{LD: CVM} {NDN: CVM} {LD: CVMTYPE} {CVMTYPE} is not equal to {LD: CVMSPEC} {NDN: CVMSPEC} {LD: CVMTYPE} {CVMTYPE}
	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
    	String characid = PokUtils.getAttributeFlagValue(rootEntity, "CHARACID");
       	String cvmtype = PokUtils.getAttributeFlagValue(rootEntity, "CVMTYPE");
  	  	addDebug("doDQChecking: "+rootEntity.getKey()+" characid "+characid+" cvmtype "+cvmtype);
  	  	if (characid==null){
  	  		characid = "";
  	  	}
	  	if (cvmtype==null){
	  		cvmtype = "";
  	  	}
    	//1.00	CVMSPEC		Root		
    	//2.00			CVMCVMSPEC-u		CVM							
    	EntityGroup cvmGrp = m_elist.getEntityGroup("CVM");
		for (int i=0; i<cvmGrp.getEntityItemCount(); i++){
			EntityItem cvmitem = cvmGrp.getEntityItem(i);
			String cvmcharacid = PokUtils.getAttributeFlagValue(cvmitem, "CHARACID");
	       	String cvmcvmtype = PokUtils.getAttributeFlagValue(cvmitem, "CVMTYPE");
	  	  	addDebug("doDQChecking: "+cvmitem.getKey()+" cvmcharacid "+cvmcharacid+" cvmcvmtype "+cvmcvmtype);
	  	  	if (cvmcharacid==null){
	  	  		cvmcharacid = "";
	  	  	}
		  	if (cvmcvmtype==null){
		  		cvmcvmtype = "";
	  	  	}
	    	//3.00			CHARACID	=	CVM	CHARACID		E	E	E	Parent CVM and Child CVMSPEC must have identical values for CHARACID	
	  	  	//{LD: CVM} {NDN: CVM} {LD: CHARACID} {CHARACID} is not equal to {LD: CVMSPEC} {NDN: CVMSPEC} {LD: CHARACID} {CHARACID}
			if(!characid.equals(cvmcharacid)){
		    	//NOT_EQUAL_ERR = {0} {1} is not equal to {2} {3}
				args[0] = this.getLD_NDN(cvmitem);
				args[1] = this.getLD_Value(cvmitem, "CHARACID");
				args[2] = this.getLD_NDN(rootEntity);
				args[3] = this.getLD_Value(rootEntity, "CHARACID");
				createMessage(CHECKLEVEL_E,"NOT_EQUAL_ERR",args);
			}

	    	//4.00			CVMTYPE	=	CVM	CVMTYPE		E	E	E	Parent CVM and Child CVMSPEC must have identical values for CVMTYPE	
			//{LD: CVM} {NDN: CVM} {LD: CVMTYPE} {CVMTYPE} is not equal to {LD: CVMSPEC} {NDN: CVMSPEC} {LD: CVMTYPE} {CVMTYPE}
			if(!cvmtype.equals(cvmcvmtype)){
		    	//NOT_EQUAL_ERR = {0} {1} is not equal to {2} {3}
				args[0] = this.getLD_NDN(cvmitem);
				args[1] = this.getLD_Value(cvmitem, "CVMTYPE");
				args[2] = this.getLD_NDN(rootEntity);
				args[3] = this.getLD_Value(rootEntity, "CVMTYPE");
				createMessage(CHECKLEVEL_E,"NOT_EQUAL_ERR",args);
			}
		}
    }

    /**********************************
     * complete abr processing after status moved to final; (status was r4r)
     *C. STATUS changed to Final
52.50	R1.0	CVMSPEC									
52.52	R1.0	IF		"CVMCVMSPEC-u:CHRGCOMPCVM-u: SVCMODCHRGCOMP-u"	CVM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
52.54	R1.0	AND			CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
52.56	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
52.58	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
52.60	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
52.62	R1.0	ELSE	52.52								
52.64	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
52.66	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)			
52.68	R1.0	AND			CVM	STATUS	=	"Final" (0020)			
52.70	R1.0	AND			CVMSPEC	STATUS	=	"Final" (0020)			
52.72	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
52.74	R1.0	END	52.64								
52.76	R1.0	END	52.52								
				
     */
    protected void completeNowFinalProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	EntityGroup cvmGrp = m_elist.getEntityGroup("CVM");
    	for (int i=0; i<cvmGrp.getEntityItemCount(); i++){
    		EntityItem cvm = cvmGrp.getEntityItem(i);
    		addDebug("completeNowFinalProcessing: "+cvm.getKey());
    		//52.52	R1.0	IF		"CVMSPECCVM-u:CHRGCOMPCVM-u:CHRGCOMPSVCMOD-u"	CVM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
    		if (this.statusIsRFRorFinal(cvm)){
    			Vector chrgcompVct = PokUtils.getAllLinkedEntities(cvm, "CHRGCOMPCVM", "CHRGCOMP");
    			for (int ic=0; ic<chrgcompVct.size(); ic++){
    				EntityItem chrgcomp = (EntityItem)chrgcompVct.elementAt(ic);
    				addDebug("completeNowFinalProcessing: "+chrgcomp.getKey());
    	    		//52.54	R1.0	AND			CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)	
    				if (this.statusIsRFRorFinal(chrgcomp)){
    					Vector svcmodVct = PokUtils.getAllLinkedEntities(chrgcomp, "SVCMODCHRGCOMP", "SVCMOD");
    					for (int s=0; s<svcmodVct.size(); s++)	{
    						EntityItem svcmod = (EntityItem)svcmodVct.elementAt(s);
//    						String lifecycle = PokUtils.getAttributeFlagValue(svcmod, "LIFECYCLE");
//    				    	addDebug("completeNowFinalProcessing: "+svcmod.getKey()+" lifecycle "+lifecycle);
//    				    	if (lifecycle==null || lifecycle.length()==0){ 
//    				    		lifecycle = LIFECYCLE_Plan;
//    				    	}
    				    	//52.64	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
    						if (this.statusIsFinal(svcmod)){
        				    	//52.66	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)			
        				    	//52.68	R1.0	AND			CVM	STATUS	=	"Final" (0020)			
    							if (this.statusIsFinal(cvm) && statusIsFinal(chrgcomp)){
    	    				    	//52.70	R1.0	AND			CVMSPEC	STATUS	=	"Final" (0020)			
    	    				    	//52.72	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
    	    				    	setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
    							}
    						}else{
    					    	//52.56	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
        				    	//
//    							20130904 Delete	Queue if was once Final	52.580		AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)				Was never Final
        				    	if (statusIsRFR(svcmod)){ 
            				    	//52.60	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
        							setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
        						}
    						}
    					}
    					svcmodVct.clear();
    				}
    			}
    			chrgcompVct.clear();
    		}
    	}
    }
    /**********************************
     * complete abr processing after status moved to readyForReview; (status was chgreq)
 	* B.	Status changed to Ready for Review	
52.50	R1.0	CVMSPEC									
52.52	R1.0	IF		"CVMCVMSPEC-u:CHRGCOMPCVM-u:SVCMODCHRGCOMP-u"	CVM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
52.54	R1.0	AND			CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
52.56	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
52.58	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
52.60	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
52.62	R1.0	ELSE	52.52								
52.64	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
52.66	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)			
52.68	R1.0	AND			CVM	STATUS	=	"Final" (0020)			
52.70	R1.0	AND			CVMSPEC	STATUS	=	"Final" (0020)			
52.72	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
52.74	R1.0	END	52.64								
52.76	R1.0	END	52.52								
							

     */
    protected void completeNowR4RProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	EntityGroup cvmGrp = m_elist.getEntityGroup("CVM");
    	for (int i=0; i<cvmGrp.getEntityItemCount(); i++){
    		EntityItem cvm = cvmGrp.getEntityItem(i);
    		addDebug("completeNowR4RProcessing: "+cvm.getKey());
    		//52.52	R1.0	IF		"CVMSPECCVM-u:CHRGCOMPCVM-u:CHRGCOMPSVCMOD-u"	CVM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
    		if (this.statusIsRFRorFinal(cvm)){
    			Vector chrgcompVct = PokUtils.getAllLinkedEntities(cvm, "CHRGCOMPCVM", "CHRGCOMP");
    			for (int ic=0; ic<chrgcompVct.size(); ic++){
    				EntityItem chrgcomp = (EntityItem)chrgcompVct.elementAt(ic);
    				addDebug("completeNowR4RProcessing: "+chrgcomp.getKey());
    	    		//52.54	R1.0	AND			CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
    				if (this.statusIsRFRorFinal(chrgcomp)){
    					Vector svcmodVct = PokUtils.getAllLinkedEntities(chrgcomp, "SVCMODCHRGCOMP", "SVCMOD");
    					for (int s=0; s<svcmodVct.size(); s++)	{
    						EntityItem svcmod = (EntityItem)svcmodVct.elementAt(s);
//    						String lifecycle = PokUtils.getAttributeFlagValue(svcmod, "LIFECYCLE");
//    				    	addDebug("completeNowR4RProcessing: "+svcmod.getKey()+" lifecycle "+lifecycle);
//    				    	if (lifecycle==null || lifecycle.length()==0){ 
//    				    		lifecycle = LIFECYCLE_Plan;
//    				    	}
    				    	//52.56	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
    				    	//52.58	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
//    						20130904 Delete	Queue if was once Final	52.580		AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)				Was never Final	

    				    	if (statusIsRFR(svcmod)){ 
        				    	//52.60	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
    							setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
    						}
    					}
    					svcmodVct.clear();
    				}
    			}
    			chrgcompVct.clear();
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
        String desc =  "CVMSPEC ABR.";

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
