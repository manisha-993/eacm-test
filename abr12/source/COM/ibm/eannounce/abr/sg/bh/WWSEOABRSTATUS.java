// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import java.sql.SQLException;
import java.util.*;
/**********************************************************************************
* WWSEOABRSTATUS class
*    
* BH FS ABR Data Quality 20120306.doc - OSN updates
* BH FS ABR Data Quality 20120131.doc - needs updated EXRPT3WWSEO1 CATLGOR updates
* 
* BH FS ABR Data Quality 20120116.doc - delete 6.00-9.00,XCC_LIST2
*    
* BH FS ABR Data Quality 20110517.doc
* support already final or rfr - gen catdata and queue ads abr
* need new VE EXRPT3WWSEO4
* 
* BH FS ABR Data Quality 20110322.doc
* Add checks 82.10-82.30, remove 83.0, remove 133.00-134.00
* 
* from BH FS ABR Catalog Attr Derivation 20110221b.doc
* need updated EXRPT3WWSEO1
* 
* SG FS ABR Data Quality 20101012.doc
* needs updated VE EXRPT3WWSEO1
* 
* SG FS ABR Data Quality 20100615.doc
* needs updated VE EXRPT3WWSEO1 for dropid 100169
* needs lseo.lifecycle
* needs PRODSTRUCTWARR 

WWSEOABRSTATUS_class=COM.ibm.eannounce.abr.sg.WWSEOABRSTATUS
WWSEOABRSTATUS_enabled=true
WWSEOABRSTATUS_idler_class=A
WWSEOABRSTATUS_keepfile=true
WWSEOABRSTATUS_report_type=DGTYPE01
WWSEOABRSTATUS_vename=EXRPT3WWSEO1
WWSEOABRSTATUS_CAT1=RPTCLASS.WWSEOABRSTATUS
WWSEOABRSTATUS_CAT2=
WWSEOABRSTATUS_CAT3=RPTSTATUS
WWSEOABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390


 *
 * WWSEOABRSTATUS.java,v
 * Revision 1.21  2011/06/10 17:40:40  wendy
 * BH FS ABR Data Quality 20110517.doc : support already final or rfr - gen catdata and queue ads abr
 *
 * Revision 1.18  2011/03/23 18:09:35  wendy
 * Add CATDATA support
 *
 * Revision 1.13  2010/07/28 14:50:02  wendy
 * cvs failure again, lost history since 1.10
 *
 * Revision 1.10  2010/03/15 13:20:12  wendy
 * BH FS ABR Data Quality 20100313.doc updates
 *
 * Revision 1.9  2010/01/21 22:20:25  wendy
 * updates for BH FS ABR Data Quality 20100120.doc
 *
 * Revision 1.8  2010/01/20 21:24:31  wendy
 * cvs failure again
 *
 * Revision 1.4  2009/12/03 15:42:27  wendy
 * Updated for spec chg BH FS ABR Data Qualtity 20091120.xls
 *
 * Revision 1.3  2009/08/17 15:03:53  wendy
 * Added headings
 *
 * Revision 1.2  2009/08/15 01:41:50  wendy
 * SR10, 11, 12, 15, 17 BH updates phase 4
 *
 * Revision 1.1  2009/07/30 18:54:36  wendy
 * Moved to new pkg for BH SR10, 11, 12, 15, 17
 * 
 */
public class WWSEOABRSTATUS extends DQABRSTATUS
{
	private EntityList mdlList=null;
	private static final String SEOORDERCODE_Initial =	"10";
	private static final String SEOORDERCODE_MES =	"20";
	
	private EntityList lseoList = null;
	private boolean IMGUniqueCoverageChkDone = false;
	
	public void dereference(){
		super.dereference();
		if (mdlList!=null){
			mdlList.dereference();
			mdlList = null;
		}
		if (lseoList!=null){
			lseoList.dereference();
			lseoList = null;
		}
	}
	/**********************************
	* must be xseries or convergedproduct
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/**
	 * allow derived classes a way to override the VE used when status and dq are the same and checks
	 * are not needed, but some structure may be needed
	 * 
	 * @param statusFlag
	 * @param dataQualityFlag
	 * @return
	 */
	protected String getVEName(String statusFlag, String dataQualityFlag){
		if (statusFlag.equals(STATUS_FINAL)){
			addDebug("Status already final, use diff ve");
			return "EXRPT3WWSEO4";
		}else if (statusFlag.equals(STATUS_R4REVIEW) && dataQualityFlag.equals(DQ_R4REVIEW)){
			addDebug("Status already rfr, use diff ve");
			return "EXRPT3WWSEO4";	
		}
		return 	m_abri.getVEName();
	}
	/**********************************
	 * complete abr processing when status is already final; (dq was final too)
	 * E.	Status is Final
	 * 
	 * When this ABR is invoked and STATUS = DATAQUALITY = �Final�, then checking is not required. 
	 * CATDATA derivation is required. If the generation fails, the DQ ABR will fail. The DQ ABR will 
	 * process data for the selected offering and only utilizes CATADATA rules that have a Life Cycle of Production. 
	 * If the generation of CATDATA is successful, then only the setting of ADSABRSTATUS is processed. 
	 * This includes the necessary conditions and only column N (Final) applies.
	 * 
	 */
    protected void doAlreadyFinalProcessing(EntityItem rootEntity) throws Exception {
       	// update darules and if there are changes queue ADSABRSTATUS
    	if(doDARULEs()){
     		boolean chgsMade = updateDerivedData(rootEntity);
    		addDebug("doAlreadyFinalProcessing: "+rootEntity.getKey()+" chgsMade "+chgsMade);
    		if(chgsMade){
    			//188.00	IF			WWSEO	STATUS	=	"Final" (0020)
    			Vector finalLseoVct = new Vector();
    			EntityGroup eg = m_elist.getEntityGroup("LSEO");
    			for (int i=0; i<eg.getEntityItemCount(); i++){
    				EntityItem item = eg.getEntityItem(i);
    	            //189.00 IF	B	WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)
    				if (statusIsFinal(item)){
    					finalLseoVct.add(item);
    				}
    			}
    						
    			if (finalLseoVct.size()>0){
    				//189.00	IF	B	WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)
    				EntityItem wwseoItem = m_elist.getParentEntityGroup().getEntityItem(0);
    				String	specbid = getAttributeFlagEnabledValue(wwseoItem, "SPECBID");
    				addDebug("doAlreadyFinalProcessing: "+wwseoItem.getKey()+" SPECBID: "+specbid);
    				
    				EntityItem lseoArray[] = new EntityItem[finalLseoVct.size()];
    				finalLseoVct.copyInto(lseoArray);
    				// pull VE to get avails and ann
    				String VeName = "EXRPT3LSEO2";
    		        lseoList = m_db.getEntityList(m_elist.getProfile(),
    		                new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
    		                lseoArray);
    		        addDebug("doAlreadyFinalProcessing: Extract "+VeName+NEWLINE+PokUtils.outputList(lseoList));
    		        EntityGroup lseoGrp = lseoList.getParentEntityGroup(); // these are only Final LSEO
    		        
    		        //190.00	IF			WWSEO	SPECBID	=	"No" (11457)
    		        if (SPECBID_NO.equals(specbid)){
    					//191.00	IF	C	B: LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)
    		        	//191.20	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
    		        	//192.00		OR		C: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)
    		        	//193.00		Perform		SETS_Section	LSEO	Two		
    		  	        doADSLSEOSectionTwo(filterLSEOs(lseoGrp),specbid); 
    		        	//193.20	END	191.20	
    		  	        //194.00	END	191
    		        }else{// specbid=yes
    		    		//195.00	ELSE	190				
    					//196.00	Perform		SETS_Section	LSEO	Two		
    			    	doADSLSEOSectionTwo(lseoGrp.getEntityItemsAsArray(),specbid);
    			        //197.00	END	190	
    		        }
    	       
    				lseoArray = null;
    				finalLseoVct.clear();
    				//198.00	END	189
    			}				
    	        //199.00	END	188	
    		}else{
				//NO_CHGSFOUND = No {0} changes found..
				args[0] = m_elist.getEntityGroup("CATDATA").getLongDescription();
				addResourceMsg("NO_CHGSFOUND",args);
			}
    	}
    }
	/**********************************
	 * complete LSEO DQ ADS abr processing when WWSEO is already final or already rfr
	 * LSEOAVAIL, AVAILANNA must be in extract
	 * @param lseoArray 
	 * @param specbid
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 * 
	 * Note: For �Status is Ready for Review� and �Status is Final�, then any part of the WWSEO section within 
	 * the SETS spreadsheet that might affect setting ADSABRSTATUS would be applied. �Perform SETS_Section LSEO Two� 
	 * applies if the LSEO is not withdrawn (i.e. LSEOUNPUBDATEMTRGT > NOW()). The LSEO section 
	 * �Perform SETS_Section LSEOBUNDLE One� does not apply.
	 * 

	71.00	Section		Two							
	74.00	IF		WWSEOLSEO-u	WWSEO	SPECBID	=	"No" (11457)												
	85.20	IF			LSEO	STATUS	=	"Ready for Review" (0040)			
 	85.22	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)	| "Plan" (LF01)			
	85.24	AND		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
	85.26	AND		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
 	85.28	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
	85.30	END	85.20								
	85.32	IF			LSEO	STATUS	=	"Final" (0020)			
	85.34	AND		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)			
	85.35	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)			
	85.36	OR		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
	85.38	SET			LSEO				ADSABRSTATUS		&ADSFEED
	85.39	END	85.35								
	85.40	END	85.32								
	86.00	ELSE	74.00
	87.10	IF			LSEO	STATUS	=	"Ready for Review" (0040)			
	87.11	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)	| "Plan" (LF01)			
 	87.12	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
	87.13	END	87.10								
	87.20	IF			LSEO	STATUS	=	"Final" (0020)			
	87.23	SET			LSEO				ADSABRSTATUS		&ADSFEED
	87.24	END	87.20								
	88.00	END	74.00								
	102.00	END	71.00	Section Two	
	 */
	private void doADSLSEOSectionTwo(EntityItem[] lseoArray,String specbid) throws 
	MiddlewareRequestException, SQLException, MiddlewareException
	{			
		if (lseoArray==null || lseoArray.length==0){
			addDebug("doADSLSEOSectionTwo: entered with no LSEOs");
			return;
		}

		for (int x=0; x<lseoArray.length; x++){
			EntityItem lseoitem = lseoArray[x];
			//if the LSEO is not withdrawn (i.e. LSEOUNPUBDATEMTRGT > NOW())
			String wdDate = PokUtils.getAttributeValue(lseoitem, "LSEOUNPUBDATEMTRGT", "", FOREVER_DATE, false);
			addDebug("doADSLSEOSectionTwo "+lseoitem.getKey()+" wdDate: "+wdDate+" now: "+
					getCurrentDate()+" SPECBID: "+specbid);
			if(getCurrentDate().compareTo(wdDate)>=0){
				addDebug("doADSLSEOSectionTwo skipping "+lseoitem.getKey()+" it is withdrawn");
				continue;
			}

			//71.00	Section		Two							
			if (statusIsFinal(lseoitem)){
				if (SPECBID_NO.equals(specbid)){  // is No
					// the lseo has already been filtered on avail=final and (availtype=rfa and ann=final) or 
					// availtype!=rfa
					addDebug("doADSLSEOSectionTwo: lseo.status=final specbid=no");
					//85.38		SET			LSEO				ADSABRSTATUS		&ADSFEED
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
				}// end specbid=no
				else{
					addDebug("doADSLSEOSectionTwo: lseo.status=final specbid=yes");
					//87.23	SET			LSEO				ADSABRSTATUS		&ADSFEED
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
					//87.24	END	87.2
				}// end specbid=yes
			}// end lseo = final

			if (statusIsRFR(lseoitem)){ // already filtered on lifecycle
				if (SPECBID_NO.equals(specbid)){  // is No
					// the lseo has already been filtered on avail=rfr|final and (availtype=rfa and ann=rfr|final) or 
					// availtype!=rfa
					addDebug("doADSLSEOSectionTwo: lseo.status=rfr specbid=no");
					//85.28	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR			
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
					//88.00	END	74	
				}else{ // specbid=yes
					//87.10   IF			LSEO	STATUS	=	"Ready for Review" (0040)			
					//87.11	AND			LSEO	LIFECYCLE	=	"Develop" (LF02) | "Plan" (LF01)
					addDebug("doADSLSEOSectionTwo: lseo.status=rfr specbid=yes");
					//87.12	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
					//87.13	END	87.1	
				} //end specbid=yes
				//88.00	END	74
			}// end rfr and r10 loop
		} // end lseoitem array loop

		//102.00	END	109	Section Two		
	}
	/**********************************
	 * complete abr processing when status is already rfr; (dq was rfr too)
	 * D.	Status is Ready for Review
	 * 
	 * When this ABR is invoked and STATUS = DATAQUALITY = �Ready for Review�, then checking is not required. 
	 * CATDATA derivation is required. If the generation fails, the DQ ABR will fail. The DQ ABR will process 
	 * data for the selected offering and only utilizes CATADATA rules that have a Life Cycle of Production. 
	 * If the generation of CATDATA is successful, then only the setting of ADSABRSTATUS is processed. This 
	 * includes the necessary conditions and only column M (Ready for Review) applies.
	 * 
	 */
    protected void doAlreadyRFRProcessing(EntityItem rootEntity) throws Exception {
    	// update darules and if there are changes queue ADSABRSTATUS
    	if(doDARULEs()){
    		boolean chgsMade = updateDerivedData(rootEntity);
    		addDebug("doAlreadyRFRProcessing: "+rootEntity.getKey()+" chgsMade "+chgsMade);
    		if(chgsMade){
    			//	187.20	IF			WWSEO	STATUS	=	"Ready for Review" (0040)
            	Vector lseoSect2Vct = new Vector();
            	EntityItem wwseoItem= m_elist.getParentEntityGroup().getEntityItem(0);
            	String wwseoSPECBID = getAttributeFlagEnabledValue(wwseoItem, "SPECBID");
            	addDebug("doAlreadyRFRProcessing: "+wwseoItem.getKey()+" wwseoSPECBID "+wwseoSPECBID);
            	Vector lseoVct= PokUtils.getAllLinkedEntities(wwseoItem, "WWSEOLSEO", "LSEO");
            	for (int i=0; i<lseoVct.size(); i++){
            		EntityItem lseo = (EntityItem)lseoVct.elementAt(i);
//            		String lifecycle = PokUtils.getAttributeFlagValue(lseo, "LIFECYCLE");
//            		addDebug("doAlreadyRFRProcessing: "+lseo.getKey()+" lifecycle "+lifecycle);
//            		if (lifecycle==null || lifecycle.length()==0){
//            			lifecycle = LIFECYCLE_Develop;
//            		}

            		//187.21	IF			LSEO	STATUS	=	"Ready for Review" (0040)
                	//20130904 Delete  187.22	IF		WWSEOLSEO-d	LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
            		if (statusIsRFR(lseo) 
//            				&& (LIFECYCLE_Develop.equals(lifecycle) || LIFECYCLE_Plan.equals(lifecycle))
            				){ // lseo.status must be rfr if it is dev
            			if(wwseoSPECBID.equals(SPECBID_NO)){
            				addDebug("doAlreadyRFRProcessing: specbid=no ");
            		       	//187.24	IF			WWSEO	SPECBID	=	"No" (11457)
            				Vector availVct= PokUtils.getAllLinkedEntities(lseo, "LSEOAVAIL", "AVAIL");
            				availloop:for (int ai=0; ai<availVct.size(); ai++){
            					EntityItem avail = (EntityItem)availVct.elementAt(ai);
            					
        						String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
        						if (availAnntypeFlag==null){
        							availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
        						}
        						addDebug("doAlreadyRFRProcessing: "+avail.getKey()+" availanntype "+availAnntypeFlag);
           					
                             	//187.26	IF	C	B: LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
            					if (statusIsRFRorFinal(avail)){	            						
            							//187.30	R1.0	Perform		SETS_Section	LSEO	Two	
            							lseoSect2Vct.add(lseo);
        								break availloop;//            						
            					}
            					//187.32	END	187.26
            				}// end avail loop
            				availVct.clear();
            			}else{ // specbid=yes
            				// 187.34	R1.0	ELSE	187.24			
            				// 187.36	R1.0	Perform		SETS_Section	LSEO	Two	
            				addDebug("doAlreadyRFRProcessing: specbid=yes ");
            				lseoSect2Vct.add(lseo);
            			}
            			//187.38	R1.0	END	187.24
            			//187.40	R1.0	END	187.22
            		}//end lseo.status must be rfr if it is dev
            	} // end lseovct loop	
         
            	EntityItem lseoArray[] = null;
            	if (lseoSect2Vct.size()>0){
            		lseoArray = new EntityItem[lseoSect2Vct.size()];
            		lseoSect2Vct.copyInto(lseoArray);
            		doADSLSEOSectionTwo(lseoArray,wwseoSPECBID);
            		lseoArray = null;
            	}

            	lseoSect2Vct.clear();
            	//	Add	187.42	R1.0	END	187.20	
    		}else{
				//NO_CHGSFOUND = No {0} changes found..
				args[0] = m_elist.getEntityGroup("CATDATA").getLongDescription();
				addResourceMsg("NO_CHGSFOUND",args);
			}
    	}
    }
   
	/*
	 * 
from sets ss
184.00	WWSEO		Root Entity							
185.00	SET			WWSEO				COMPATGENABR	&COMPATGENRFR	&COMPATGEN
186.00	Perform			WWSEO				OS	PropagateOStoWWSEO	PropagateOStoWWSEO
187.00	COMMENT		The following from LSEO when Final so that we do not have to queue DQ ABR for LSEO	

Add	187.20	R1.0	IF			WWSEO	STATUS	=	"Ready for Review" (0040)
Add	187.21	R1.0	IF			LSEO	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
187.22	R1.0	IF		WWSEOLSEO-d	LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
Add	187.24	R1.0	IF			WWSEO	SPECBID	=	"No" (11457)
Add	187.26	R1.0	IF	C	B: LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
187.27	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
187.28	R1.0	OR		C: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
187.30	R1.0	Perform		SETS_Section	LSEO	Two		
187.31	R1.0	END	187.27					
	
Add	187.32	R1.0	END	187.26					
Add	187.34	R1.0	ELSE	187.24					
Add	187.36	R1.0	Perform		SETS_Section	LSEO	Two		
Add	187.38	R1.0	END	187.24					
Add	187.40	R1.0	END	187.22					
Add	187.42	R1.0	END	187.20					
					
						
188.00	IF			WWSEO	STATUS	=	"Final" (0020)			
189.00	IF	B	WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)			
190.00	IF			WWSEO	SPECBID	=	"No" (11457)			
191.00	IF	C	B: LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)			
191.20	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
192.00		OR		C: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)
193.00		Perform		SETS_Section	LSEO	Two		
193.20	R1.0	END	191.20					
				
194.00	END	191								
195.00	ELSE	190								
196.00	Perform		SETS_Section	LSEO	Two					
197.00	END	190								
198.00	END	189								
199.00	END	188								
200.00	END	184	WWSEO							
	 */
    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
    *C.  STATUS changed to Ready for Review
185.00	SET			WWSEO				COMPATGENABR	&COMPATGENRFR	&COMPATGEN
186.00	Perform			WWSEO				OS	PropagateOStoWWSEO	PropagateOStoWWSEO
Add	187.20	R1.0	IF			WWSEO	STATUS	=	"Ready for Review" (0040)
Add	187.21	R1.0	IF			LSEO	STATUS	=	"Ready for Review" (0040)
187.22	R1.0	IF		WWSEOLSEO-d	LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
Add	187.24	R1.0	IF			WWSEO	SPECBID	=	"No" (11457)
Add	187.26	R1.0	IF	C	B: LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
187.27	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
187.28	R1.0	OR		C: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
187.30	R1.0	Perform		SETS_Section	LSEO	Two		
187.31	R1.0	END	187.27					
	
Add	187.32	R1.0	END	187.26					
Add	187.34	R1.0	ELSE	187.24					
Add	187.36	R1.0	Perform		SETS_Section	LSEO	Two		
Add	187.38	R1.0	END	187.24					
Add	187.40	R1.0	END	187.22					
Add	187.42	R1.0	END	187.20					

    */
    protected void completeNowR4RProcessing()throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		if (mdlList==null){
			try{
				// get VE2 to go from WWSEO-MODELWWSEO-MODEL and other MODEL links
				getModelVE(m_elist.getParentEntityGroup().getEntityItem(0));
			}catch(Exception e){
				e.printStackTrace();
				addDebug("Exception getting model ve "+e.getMessage());
				throw new MiddlewareException(e.getMessage());
			}
		}
		
		//186.00 Perform			WWSEO				OS	PropagateOStoWWSEO	PropagateOStoWWSEO
//		2013-04-26 Delete based on SETs
//        propagateOStoWWSEO(mdlList.getEntityGroup("MODEL").getEntityItem(0),m_elist.getParentEntityGroup());
        //185.00 SET			WWSEO				COMPATGENABR	&COMPATGENRFR	&COMPATGEN
        setFlagValue(m_elist.getProfile(),"COMPATGENABR",getRFRQueuedValue("COMPATGENABR"));

        if(doR10processing()){
        	//Add	187.20	R1.0	IF			WWSEO	STATUS	=	"Ready for Review" (0040)
        	Vector lseoSect2Vct = new Vector();
        	EntityItem wwseoItem= m_elist.getParentEntityGroup().getEntityItem(0);
        	String wwseoSPECBID = getAttributeFlagEnabledValue(wwseoItem, "SPECBID");
        	addDebug("completeNowR4RProcessing: "+wwseoItem.getKey()+" wwseoSPECBID "+wwseoSPECBID);
        	Vector lseoVct= PokUtils.getAllLinkedEntities(wwseoItem, "WWSEOLSEO", "LSEO");
        	for (int i=0; i<lseoVct.size(); i++){
        		EntityItem lseo = (EntityItem)lseoVct.elementAt(i);
//        		String lifecycle = PokUtils.getAttributeFlagValue(lseo, "LIFECYCLE");
//        		addDebug("completeNowR4RProcessing: "+lseo.getKey()+" lifecycle "+lifecycle);
//        		if (lifecycle==null || lifecycle.length()==0){
//        			lifecycle = LIFECYCLE_Develop;
//        		}

        		//Add	187.21	R1.0	IF			LSEO	STATUS	=	"Ready for Review" (0040)
            	//20130904 Delete 187.22	R1.0	IF		WWSEOLSEO-d	LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
        		if (statusIsRFR(lseo)) {
//        				&& (LIFECYCLE_Develop.equals(lifecycle) || LIFECYCLE_Plan.equals(lifecycle))){ // lseo.status must be rfr if it is dev
        			if(wwseoSPECBID.equals(SPECBID_NO)){
        				addDebug("completeNowR4RProcessing: specbid=no ");
        		       	//Add	187.24	R1.0	IF			WWSEO	SPECBID	=	"No" (11457)
        				Vector availVct= PokUtils.getAllLinkedEntities(lseo, "LSEOAVAIL", "AVAIL");
        				availloop:for (int ai=0; ai<availVct.size(); ai++){
        					EntityItem avail = (EntityItem)availVct.elementAt(ai);
        					
    						String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
    						if (availAnntypeFlag==null){
    							availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
    						}
    						addDebug("completeNowR4RProcessing: "+avail.getKey()+" availanntype "+availAnntypeFlag);
       					
                         	//187.26	R1.0	IF	C	B: LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
        					if (statusIsRFRorFinal(avail)){	
        						//187.27	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
//        						if(!AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
        							//187.30	R1.0	Perform		SETS_Section	LSEO	Two	
        							lseoSect2Vct.add(lseo);
    								break availloop;
//        						}
//
//        						Vector annVct= PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//        						for (int ii=0; ii<annVct.size(); ii++){
//        							EntityItem annItem = (EntityItem)annVct.elementAt(ii);
//        							addDebug("completeNowR4RProcessing: "+annItem.getKey());
//        							//187.28	OR		C: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)	
//        							if(statusIsRFRorFinal(annItem, "ANNSTATUS")){
//        								//187.30	Perform		SETS_Section	LSEO	Two	
//        								lseoSect2Vct.add(lseo);
//        								break availloop;
//        							}
//        						}// end ann loop
//        						annVct.clear();
        						//187.31	R1.0	END	187.27
        					}
        					//187.32	END	187.26
        				}// end avail loop
        				availVct.clear();
        			}else{ // specbid=yes
        				// Add	187.34	R1.0	ELSE	187.24			
        				//Add	187.36	R1.0	Perform		SETS_Section	LSEO	Two	
        				addDebug("completeNowR4RProcessing: specbid=yes ");
        				lseoSect2Vct.add(lseo);
        			}
        			//Add	187.38	R1.0	END	187.24
        			//Add	187.40	R1.0	END	187.22
        		}//end lseo.status must be rfr if it is dev
        	} // end lseovct loop	
     
        	EntityItem lseoArray[] = null;
        	if (lseoSect2Vct.size()>0){
        		lseoArray = new EntityItem[lseoSect2Vct.size()];
        		lseoSect2Vct.copyInto(lseoArray);
        		doLSEOSectionTwo(lseoArray,wwseoSPECBID);
        		lseoArray = null;
        	}

        	lseoSect2Vct.clear();
        	//	Add	187.42	R1.0	END	187.20	
        }// end doR10
    }

	/**
	 * from BH FS ABR Catalog Attr Derivation 20110221.doc
	 * C.	Data Quality
	 * As part of the normal process for offering information, a user first creates data in �Draft�. The user 
	 * then asserts the Data Quality (DATAQUALITY) as being �Ready for Review� which queues the Data Quality ABR. 
	 * The DQ ABR checks to ensure that the data is �Ready for Review� and then advances Status (STATUS) to 
	 * �Ready for Review�.
	 * 
	 * The Data Quality ABR will be enhanced such that if the checks pass, then the DQ ABR will process the 
	 * corresponding DARULE. If DARULE is processed successfully, then the DQ ABR will set 
	 * STATUS = �Ready for Review�. If DARULE is not processed successfully, then the DQ ABR will �Fail� 
	 * and return Data Quality to the prior state (Draft or Change Request).
	 */
	protected boolean updateDerivedData(EntityItem rootEntity)throws Exception {
		boolean chgsMade = false;
		//For all WWSEO where the is at least one child LSEO where
		//Status (STATUS) = �Ready for Review� or �Final�
		EntityGroup lseogrp = m_elist.getEntityGroup("LSEO");
		for (int i=0; i<lseogrp.getEntityItemCount(); i++){
			EntityItem lseo = lseogrp.getEntityItem(i);
			if (this.statusIsRFRorFinal(lseo)){
				if (mdlList==null){
					try{
						// get VE2 to go from WWSEO-MODELWWSEO-MODEL and other MODEL links
						getModelVE(m_elist.getParentEntityGroup().getEntityItem(0));
					}catch(Exception e){
						e.printStackTrace();
						addDebug("Exception getting model ve "+e.getMessage());
						throw new MiddlewareException(e.getMessage());
					}
				}

				EntityItem mdlitem = mdlList.getEntityGroup("MODEL").getEntityItem(0);
				//NOW() <= the parent MODEL�s Withdrawal Effective Date (WTHDRWEFFCTVDATE)
				String wdDate = PokUtils.getAttributeValue(mdlitem, "WTHDRWEFFCTVDATE", "", FOREVER_DATE, false);
				addDebug("updateDerivedData "+mdlitem.getKey()+" wdDate: "+wdDate+" now: "+getCurrentDate());
				if(getCurrentDate().compareTo(wdDate)<=0){
					chgsMade = execDerivedData(rootEntity);
				}
				break;
			}
		}
		return chgsMade;
	}
    /* (non-Javadoc)
     * update LIFECYCLE value when STATUS is updated
     * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
     */
	protected String getLCRFRWFName(){ return "WFLCWWSEORFR";}
	protected String getLCFinalWFName(){ return "WFLCWWSEOFINAL";}
	
	/**********************************
	* complete abr processing after status moved to final; (status was r4r)
	*D.	STATUS changed to Final
188.00	IF			WWSEO	STATUS	=	"Final" (0020)			
189.00	IF	B	WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)			
190.00	IF			WWSEO	SPECBID	=	"No" (11457)			
191.00	IF	C	B: LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)			
191.20	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
192.00		OR		C: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)
193.00		Perform		SETS_Section	LSEO	Two		
193.20	R1.0	END	191.20					
				
194.00	END	191								
195.00	ELSE	190								
196.00	Perform		SETS_Section	LSEO	Two					
197.00	END	190								
198.00	END	189								
199.00	END	188		
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{	
		if (mdlList==null){
			try{
				// get VE2 to go from WWSEO-MODELWWSEO-MODEL and other MODEL links
				getModelVE(m_elist.getParentEntityGroup().getEntityItem(0));
			}catch(Exception e){
				e.printStackTrace();
				addDebug("Exception getting model ve "+e.getMessage());
				throw new MiddlewareException(e.getMessage());
			}
		}

		//186.00 Perform			WWSEO				OS	PropagateOStoWWSEO	PropagateOStoWWSEO
//		2013-04-26 Delete based on SETs
//        propagateOStoWWSEO(mdlList.getEntityGroup("MODEL").getEntityItem(0),m_elist.getParentEntityGroup());
        //185.00 SET			WWSEO				COMPATGENABR	&COMPATGENRFR	&COMPATGEN
		setFlagValue(m_elist.getProfile(),"COMPATGENABR",getQueuedValue("COMPATGENABR"));

		//187.00	COMMENT		The following from LSEO when Final so that we do not have to queue DQ ABR for LSEO		
 						
		//188.00	IF			WWSEO	STATUS	=	"Final" (0020)
		Vector finalLseoVct = new Vector();
		EntityGroup eg = m_elist.getEntityGroup("LSEO");
		for (int i=0; i<eg.getEntityItemCount(); i++){
			EntityItem item = eg.getEntityItem(i);
            //189.00 IF	B	WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)
			if (statusIsFinal(item)){
				finalLseoVct.add(item);
			}
		}
					
		if (finalLseoVct.size()>0){
			//189.00	IF	B	WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)
			EntityItem wwseoItem = m_elist.getParentEntityGroup().getEntityItem(0);
			String	specbid = getAttributeFlagEnabledValue(wwseoItem, "SPECBID");
			addDebug("nowfinal: "+wwseoItem.getKey()+" SPECBID: "+specbid);
			
			EntityItem lseoArray[] = new EntityItem[finalLseoVct.size()];
			finalLseoVct.copyInto(lseoArray);
			// pull VE to get avails and ann
			String VeName = "EXRPT3LSEO2";
	        lseoList = m_db.getEntityList(m_elist.getProfile(),
	                new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
	                lseoArray);
	        addDebug("nowfinal: Extract "+VeName+NEWLINE+PokUtils.outputList(lseoList));
	        EntityGroup lseoGrp = lseoList.getParentEntityGroup();
	        
	        //190.00	IF			WWSEO	SPECBID	=	"No" (11457)
	        if (SPECBID_NO.equals(specbid)){
				//191.00	IF	C	B: LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)
	        	//191.20	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
	        	//192.00		OR		C: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)
	        	//193.00		Perform		SETS_Section	LSEO	Two		
	  	        doLSEOSectionTwo(filterLSEOs(lseoGrp),specbid); 
	        	//193.20	R1.0	END	191.20	
	  	        //194.00	END	191
	        }else{// specbid=yes
	    		//195.00	ELSE	190				
				//196.00	Perform		SETS_Section	LSEO	Two		
		    	doLSEOSectionTwo(lseoGrp.getEntityItemsAsArray(),specbid);
		        //197.00	END	190	
	        }
       
			lseoArray = null;
			finalLseoVct.clear();
			
			//198.00	END	189
		}				
        //199.00	END	188	
	}
	/**
	 * If there is an AVAIL that is Final that is in an ANNOUNCEMENT that is Final, then this is TRUE
	 * 191.00	278 IF	C	B: LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)
191.20		IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
192.00		OR		C: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)
193.00		Perform		SETS_Section	LSEO	Two		
193.20		END	191.20					

	 * @param lseoGrp
	 * @return
	 */
	private EntityItem[] filterLSEOs(EntityGroup lseoGrp)
	{
		Vector lseoVct = new Vector();
		EntityItem lseoArray[] = null;
		//191.00	278 IF	C	B: LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)
		lseoloop: for(int i=0; i<lseoGrp.getEntityItemCount(); i++){
			EntityItem lseoitem = lseoGrp.getEntityItem(i);
			Vector availVct = PokUtils.getAllLinkedEntities(lseoitem, "LSEOAVAIL", "AVAIL");
			Vector finalAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "STATUS", STATUS_FINAL);
			addDebug("filterLSEOs: "+lseoitem.getKey()+" availVct "+availVct.size()+
					" finalAvailVct "+finalAvailVct.size());
			if (finalAvailVct.size()>0){
				//193.00		Perform		SETS_Section	LSEO	Two	
				lseoVct.add(lseoitem);
			}
			availVct.clear();
			finalAvailVct.clear();

		}
		
		if (lseoVct.size()>0){
			lseoArray = new EntityItem[lseoVct.size()];
			lseoVct.copyInto(lseoArray);
		}
		
	   	return lseoArray;
	}
	/**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
checks from ss:
1.00	WWSEO		Root									
2.00	MODEL		MODELWWSEO-u									
3.00			CountOf	=	1			RW	RE	RE	do we still need this - were the actions removed	must have only one parent {LD: MODEL} {NDN: MODEL}
4.00			STATUS	=>	WWSEO	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
5.00			WTHDRWEFFCTVDATE	=>	NOW()			E	E	E		{LD: WWSEO} can not be updated for a {LD: MODEL} that has a {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
Delete 20111212 6.00	AVAIL	AB	MODELAVAIL-d									
7.00	WHEN		AVAILTYPE	=	"Last Order"							
8.00			EFFECTIVEDATE	=>	NOW()			E	E	E		{LD: WWSEO} can not be updated for a {LD: MODEL} that has an {LD: AVAIL} {NDN: AVAIL}
9.00	END	7	end Delete 20111212									
10.00	MODEL		MODELWWSEO-u								MODEL	
11.00	WHEN		COFCAT	=	"Hardware" (100) 						Hardware	
12.00	AND		COFSUBCAT	=	"System" (126)						System	
Change 20111223 13.00	AND		PDHDOMAIN	IN	ABR_PROPERTITIES	XCC_LIST2					XCC	
16.00	DERIVEDDATA		WWSEODERIVEDDATA-d									
17.00			CountOf	=	1			RW	RE	RE		must have exactly one {LD: DERIVEDDATA} {NDN: DERIVEDDATA}
18.00	SEOCG		SEOCGSEO-u									
19.00			CountOf	=	1			RW	RE	RE		must have exactly one {LD: SEOCG} {NDN: SEOCG}
20.00	OnePLANAR	A	WWSEOPRODSTRUCT-d: 		WWSEOPRODSTRUCT	CONFQTY						
21.00		B	A: + PRODSTRUCT-u: 									
22.00		C	B: + FEATUREPLANAR-d		FEATUREPLANAR	QTY						
23.00		D	CountOf		PLANAR							
24.00		H	MODELWWSEO-u									
25.00		I	H: + MODELPLANAR-d		MODELPLANAR	QTY						
26.00		O	CountOf		I: PLANAR							
27.00			(A * C * D) + (I*O)	=	1			RW	RE	RE		must have exactly one {LD: PLANAR} {NDN: PRODSTRUCT}
28.00			D: + O:	=	1			RW	RE	RE		must have exactly one {LD: PLANAR} {NDN: PRODSTRUCT}
29.00	OneOrMoreMECHPKG	J	WWSEOPRODSTRUCT-d: 		WWSEOPRODSTRUCT	CONFQTY						
30.00		K	J: + PRODSTRUCT-u: 									
31.00		L	K: + FEATUREMECHPKG-d		FEATUREMECHPKG	QTY						
32.00		M	CountOf		L: MECHPKG							
33.00		N	MODELWWSEO-u									
34.00		P	N: + MODELMECHPKG-c		MODELMECHPKG	QTY						
35.00		Q	CountOf		P: MECHPKG							
36.00			(J * L * M) +( P * Q)	=>	1			RW	RE	RE		must have at least one {LD: MECHPKG}
37.00	BAY		L: + MECHPKGBAY-d									
38.00		R	UniqueBAYwithin		BAYTYPE & ACCSS & BAYFF			W	E	E		must have unique {LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} see {NDN: PRODSTRUCT}
39.00		S	R: + UniqueBAYmatchBAYSAVAIL	IN	BAYSAVAIL	BAYAVAILTYPE & ACCSS & BAYFF		W	E	E		{LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAYSAVAIL}
40.00	BAY		P: + MECHPKGBAY-d									
41.00		Z	UniqueBAYwithin		BAYTYPE & ACCSS & BAYFF			W	E	E		must have unique {LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} see {LD: MODEL}
42.00			Z: + UniqueBAYmatchBAYSAVAIL	IN	BAYSAVAIL	BAYAVAILTYPE & ACCSS & BAYFF		W	E	E		{LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAYSAVAIL}
43.00	BAYSAVAIL		WWSEOBAYSAVAIL-d									
44.00			BAYAVAILTYPE & ACCSS & BAYFF	IN	BAY	R: UniqueBAYwithin					OR continues	
45.00	OR			IN	BAY	Z: UniqueBAYwithin		W	E	E		{LD: BAYSAVAIL} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAY}
46.00			UniqueBAYSAVAIL		BAYTYPE & ACCSS & BAYFF			W	E	E		{LD: BAYSAVAIL} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must be Unique
46.40	END	43.00										
46.50	WHEN		"Initial" (10)	<>	WWSEO	SEOORDERCODE						
47.00	WEIGHTNDIMN		WWSEOWEIGHTNDIMN-d									
48.00			CountOf	=	1			RE	RE	RE		must have exactly one {LD: WEIGHTNDIMN}
48.10	END	46.50										
49.00	SLOT	T	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d:									
50.00			SLOTTYPE	IN	U: SLOTSAVAIL	SLOTTYPE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
51.00			UniqueSLOTwithin		SLOTTYPE & SLOTSIZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
52.00	SLOT	W	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: 									
53.00			SLOTTYPE	IN	V: SLOTSAVAIL	SLOTTYPE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
54.00			UniqueSLOTwithin		SLOTTYPE & SLOTSZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
55.00	SLOT	X	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: 									
56.00			SLOTTYPE	IN	Y: SLOTSAVAIL	SLOTTYPE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
57.00			UniqueSLOTwithin		SLOTTYPE & SLOTSZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
58.00	SLOTSAVAIL		WWSEOSLOTSAVAIL-d: 									
59.00			UniqueSLOTSAVAIL		ELEMENTTYPE & SLOTTYPE			W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must be Unique
60.00	WHEN	U	ELEMENTTYPE	=	"Memory Card" (0010)							
61.00			SLOTTYPE	IN	T: SLOT	SLOTTYPE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
62.00	END	60										
63.00	WHEN	V	ELEMENTTYPE	=	"Planar" (0020)							
64.00			SLOTTYPE	IN	W: SLOT	SLOTTYPE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
65.00	END	63										
66.00	WHEN	Y	ELEMENTTYPE	=	"Expansion Unit" (0030)							
67.00			SLOTTYPE	IN	X: SLOT	SLOTTYPE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}

68.000	END	66.000										
68.100	WHEN		"No" (11457)	=	WWSEO	SPECBID					if true, then GA	
68.200			WWSEOIMG-d								IMG	
68.220			UniqueCoverage		IMG		Yes		RW	RE	Required	{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
68.240			COUNTRYLIST	"Contains aggregate E"	WWSEOLSEO-d	COUNTRYLIST			RW	RE		must have a {LD: IMG} for every country in the {LD: LSEO} {NDN: LSEO}
68.260			MIN(PUBFROM)	<=	WWSEOLSEO-d	LSEOPUBDATEMTRGT	Yes		RW	RE		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
68.280			MAX(PUBTO)	=>	WWSEOLSEO-d	LSEOUNPUBDATEMTRGT	Yes		RW	RE		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
68.300	MM		WWSEOMM-d								MM	
68.320			UniqueCoverage		MM		Yes		W	E	Optional	{LD: MM} {NDN: MM} have gaps in the date range or they overlap.
68.340			COUNTRYLIST	"Contains aggregate E"	WWSEOLSEO-d	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: MM} for every country in the {LD: LSEO} {NDN: LSEO}
68.360			MIN(PUBFROM)	<=	WWSEOLSEO-d	LSEOPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBFROM as early as or earlier than the  {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
68.380			MAX(PUBTO)	=>	WWSEOLSEO-d	LSEOUNPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBTO as late as or later than the  {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
68.400	FB		WWSEOFB-d								FB	
68.420			UniqueCoverage		FB		Yes		W	E	Optional	{LD: FB} {NDN: FB} have gaps in the date range or they overlap.
68.440			COUNTRYLIST	"Contains aggregate E"	WWSEOLSEO-d	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: FB} for every country in the {LD: LSEO} {NDN: LSEO}
68.460			MIN(PUBFROM)	<=	WWSEOLSEO-d	LSEOPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBFROM as early as or earlier than the  {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
68.480			MAX(PUBTO)	=>	WWSEOLSEO-d	LSEOUNPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBTO as late as or later than the  {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT) {LSEOUNPUBDATEMTRGT}
68.500	END	68.100										
69.000	END	11.000										
									
70.00	WWSEO		Root									
71.00	MODEL		MODELWWSEO-u									
72.00	WHEN		COFCAT	=	"Hardware" (100) 							
73.00	SWPRODSTRUCT		WWSEOSWPRODSTUCT-d									
74.00			CountOf	=	0			E	E	E		a Hardware {LD: WWSEO} can not have a {LD: SWPRODSTRUCT}
77.00	PRODSTRUCT	F	WWSEOPRODSTRUCT-d									
78.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: PRODSTRUCT}
79.00			WITHDRAWDATE	>	NOW()			E	E	E		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE} is withdrawn and can not be referenced.
80.00			STATUS	=>	WWSEO	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
81.00	AVAIL		F: + OOFAVAIL									
82.000	WHEN		AVAILTYPE	=	"Last Order" (149)				
82.100	IF		"Final" (0020)	=	CompareAll(WWSEOLSEO-d: LSEO )	STATUS			
82.200	WHEN		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST			
82.220	IF		F: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
82.222	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
82.224	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
82.226	THEN											
82.228			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
82.230	ELSE	82.226										
82.300			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E	This is only checked when a "Last Order" AVAIL has a country common to AVAIL.COUNTRYLIST and LSEO.COUNTRYLIST	{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
82.320	END	82.226										
82.340	END	82.200										
82.360	END	82.100										
84.000	END	82.000										
										
85.00	FEATURE		F:PRODSTRUCT-u									
86.00			WITHDRAWDATEEFF_T 	>	NOW()			E	E	E		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}  is withdrawn and can not be referenced.
									
		
90.00	END	72										
91.00	WWSEO		Root									
92.00	MODEL		MODELWWSEO-u									
93.00	WHEN		COFCAT	=	"Software" (101)							
94.00	SWPRODSTRUCT	G	WWSEOSWPRODSTRUCT-d									
95.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: SWPRODSTRUCT}
96.00			STATUS	=>	WWSEO	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
97.00	SWFEATURE		G: + SWPRODSTRUCT-u									
98.00			WITHDRAWDATEEFF_T 	>	NOW()			E	E	E		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: WITHDRAWDATEEFF_T } {WITHDRAWDATEEFF_T } is withdrawn and can not be referenced.
99.00	PRODSTRUCT		WWSEOPRODSTRUCT-d									
100.00			CountOf	=	0			E	E	E		a Software {LD: WWSEO} can not have a {LD: PRODSTRUCT}
103.00	AVAIL		G: + SWPRODSTRUCTAVAIL									
104.000	WHEN		AVAILTYPE	=	"Last Order" (149)							
104.020	IF		"Final" (0020)	=	CompareAll(WWSEOLSEO-d: LSEO )	STATUS					For each LSEO that is Final, do the following checks	
104.040	WHEN		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST					if the AVAIL has a country in its COUNTRYLIST that is in the LSEO.COUNTRYLIST, then do the following checks	
104.060	IF		G: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
104.080	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
104.100	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
104.120	THEN											
104.140			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
104.160	ELSE	104.120										
105.000			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
105.020	END	104.120										
105.040	END	104.040										
105.060	END	104.020										
106.000	END	104.000																			
									
107.00	END	93.000										
108.00	WWSEO		Root									
109.00	MODEL		MODELWWSEO-u									
110.00	WHEN		COFCAT	=	"Service" (102)							
111.00	SEOCG		SEOCGSEO-u									
112.00			CountOf	=	0			E	E	E		must not be in a {LD: SEOCG} {NDN: SEOCG}
113.00			SPECBID	=	WWSEO	SPECBID		E	E	E		{LD: SPECBID} {WWSEO.SPECBID} must match {LD: MODEL} {LD: SPECBID} {MODEL.SPECBID}
117.00	PRODSTRUCT		WWSEOPRODSTUCT-d									
118.00			CountOf	=	0			E	E	E		a Service {LD: WWSEO} can not have a {LD: PRODSTRUCT}
119.00	SWPRODSTRUCT		WWSEOSWPRODSTUCT-d									
120.00			CountOf	=	0			E	E	E		a Service {LD: WWSEO} can not have a {LD: SWPRODSTRUCT}
121.00	END	110										

Add	122.00	IF		SEOORDERCODE	=	"MES" (20)						if true, then WWSEO is an Option	
Add	123.00	THEN											
Add	124.00	IMG		WWSEOIMG-d								IMG is optional	
Add	125.00			UniqueCoverage		IMG		Yes		W	E		{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
Add	126.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST			W	E		must have a {LD: IMG} for every country in the {LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST}
Add	127.00			MIN(PUBFROM)	<=	LSEO	LSEOPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
Add	128.00			MAX(PUBTO)	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
					
Delete 20110322	133.00	ELSE	122.00										
Delete 20110322	134.00			CountOf	=	0			E	E	E		must not have any {LD: WARR} {NDN: WARR}
Delete 20110322	136.00	END	122.00										

	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
    	addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");
    	
		// get VE2 to go from WWSEO-MODELWWSEO-MODEL and other MODEL links
		getModelVE(rootEntity);

		EntityGroup mdlGrp = mdlList.getEntityGroup("MODEL");
		//MODEL		MODELWWSEO-u				
		//3.00		Count Of	=	1			RW	RE	RE	must have only one parent {LD: MODEL} {NDN: MODEL}
		EntityGroup relGrp = mdlList.getEntityGroup("MODELWWSEO");
		if (relGrp.getEntityItemCount()!=1){
			//REQUIRES_ONE_PARENT_ERR = must have only one parent {0} 
			if (relGrp.getEntityItemCount()==0){
				args[0] = mdlGrp.getLongDescription();
				createMessage(getCheck_RW_RE_RE(statusFlag),
						"REQUIRES_ONE_PARENT_ERR",args);
			}else{
				for (int i=0; i<relGrp.getEntityItemCount(); i++){
					EntityItem relator = relGrp.getEntityItem(i);
					EntityItem mdl = (EntityItem)relator.getUpLink(0);
					args[0] = getLD_NDN(mdl);
					createMessage(getCheck_RW_RE_RE(statusFlag),
							"REQUIRES_ONE_PARENT_ERR",args);
				}
			}
		}else{
			EntityItem modelItem = mdlGrp.getEntityItem(0);//get this from VE2 MODELWWSEO-u: MODEL

			//4.00		STATUS	=>	WWSEO	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
			checkStatusVsDQ(modelItem, "STATUS", rootEntity,CHECKLEVEL_E);
		    
			String modelCOFCAT = getAttributeFlagEnabledValue(modelItem, "COFCAT");
			addDebug(modelItem.getKey()+" COFCAT: "+modelCOFCAT);
			
			//5.00 2014-04-18 Delete		WTHDRWEFFCTVDATE	=>	NOW()			E	E	E		{LD: WWSEO} can not be updated for a {LD: MODEL} that has a {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
//			checkWithdrawnDate(modelItem, "WTHDRWEFFCTVDATE", CHECKLEVEL_E, false);//commented it by request from Rupal - 2014-04-21
			
			//6.00	AVAIL	AB	MODELAVAIL-d									
			//7.00	WHEN		AVAILTYPE	=	"Last Order"							
			//8.00		EFFECTIVEDATE	=>	NOW()			E	E	E		{LD: WWSEO} can not be updated for a {LD: MODEL} that has an {LD: AVAIL} {NDN: AVAIL}
			//Delete 20111212 checkWDAvails(modelItem, "MODELAVAIL", CHECKLEVEL_E,false);
			//9.00	END	7.00										

			// do checks based on model category
			if(HARDWARE.equals(modelCOFCAT)){
				doHardwareChecks(rootEntity,modelItem, statusFlag);
			}else if(SOFTWARE.equals(modelCOFCAT)){
				doSoftwareChecks(rootEntity,statusFlag);
			}else if(SERVICE.equals(modelCOFCAT)){
				doServiceChecks(rootEntity,modelItem, statusFlag);
			}
			
		 	int checklvl = doCheck_N_W_E(statusFlag);//no img/warr coverage checks if Draft

			String seoordercode = getAttributeFlagEnabledValue(rootEntity, "SEOORDERCODE");		
			Vector psVct = PokUtils.getAllLinkedEntities(rootEntity, "WWSEOPRODSTRUCT", "PRODSTRUCT");
			Vector lseoVct = PokUtils.getAllLinkedEntities(rootEntity, "WWSEOLSEO", "LSEO");	
			addDebug(rootEntity.getKey()+" SEOORDERCODE: "+seoordercode+" wwseo-ps psvct "+psVct.size()+" lseoVct "+lseoVct.size());
			//122.00	IF		SEOORDERCODE	=	"MES" (20)						if true, then WWSEO is an Option
			//123.00	THEN											
			if (SEOORDERCODE_MES.equals(seoordercode)){	//Ad123.00	THEN
				if (CHECKLEVEL_NOOP!=checklvl){
					if(!IMGUniqueCoverageChkDone){ // xcc hw does img check
				    	addHeading(3,m_elist.getEntityGroup("IMG").getLongDescription()+" Unique Coverage Checks:");
						for (int i=0; i<lseoVct.size(); i++){
							EntityItem lseoitem = (EntityItem)lseoVct.elementAt(i);
							//124.00	IMG		WWSEOIMG-d								IMG is optional	
							//125.00			UniqueCoverage		IMG		Yes		W	E		{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
							//126.00			COUNTRYLIST	"Contains aggregate E"	LSEO	COUNTRYLIST			W	E		must have a {LD: IMG} for every country in the {LD: LSEO} {NDN: LSEO} {LD: COUNTRYLIST}
							//127.00			MIN(PUBFROM)	<=	LSEO	LSEOPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEO} {NDN: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
							//128.00			MAX(PUBTO)	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEO} {NDN: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
							checkUniqueCoverage(rootEntity, "WWSEOIMG",
									"IMG", lseoitem,"LSEOPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT", checklvl, false);
						}
						IMGUniqueCoverageChkDone = true;
					}
				}else{
					addDebug("img coverage checks are bypassed because status is "+statusFlag);
				}
			}else{
				//Delete 20110322	133.00	ELSE	122.00		
				/*Vector warrVct = PokUtils.getAllLinkedEntities(psVct, "PRODSTRUCTWARR", "WARR");
				//134.00			CountOf	=	0			E	E	E		must not have any {LD: WARR} {NDN: WARR}
				if(warrVct.size()!=0){
					//MUST_NOT_HAVE_ERR= must not have any {0}
					args[0] = m_elist.getEntityGroup("WARR").getLongDescription();
					createMessage(CHECKLEVEL_E,"MUST_NOT_HAVE_ERR",args);
					warrVct.clear();
				}*/
			} //Delete 20110322	136.00	END	122.00	
			lseoVct.clear();
			psVct.clear();
		}// has one MODEL

		
		if (getReturnCode()!=PASS){
			mdlList.dereference(); // not needed any more
		}
	}

    /**********************************
    * Must have MODELWWSEO in second VE because end up with PRODSTRUCTs and FEATUREs from this MODEL
    * when all you want is PRODSTRUCTs from WWSEOPRODSTRUCT
    */
    private void getModelVE(EntityItem rootEntity) throws Exception
    {
        String VeName = "EXRPT3WWSEO2";

        if("EXRPT3WWSEO4".equals(m_elist.getParentActionItem().getActionItemKey())){
        	// use smaller VE, this is running when already final or rfr
        	VeName = "EXRPT3WWSEO5";
        }
        mdlList = m_db.getEntityList(m_elist.getProfile(),
                new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
                new EntityItem[] { new EntityItem(null, m_elist.getProfile(), rootEntity.getEntityType(), rootEntity.getEntityID()) });
        addDebug("getModelVE:: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));
	}

    /***
     * SR10, 11, 12, 15, 17
     * @param rootEntity
     * @param modelItem
     * @param wwseoStatus
     * @throws MiddlewareException
     * @throws SQLException
70.00	WWSEO		Root									
71.00	MODEL		MODELWWSEO-u									
72.00	WHEN		COFCAT	=	"Hardware" (100) 							
73.00	SWPRODSTRUCT		WWSEOSWPRODSTUCT-d									
74.00			CountOf	=	0			E	E	E		a Hardware {LD: WWSEO} can not have a {LD: SWPRODSTRUCT}
77.00	PRODSTRUCT	F	WWSEOPRODSTRUCT-d									
78.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: PRODSTRUCT}
79.00			WITHDRAWDATE	>	NOW()			E	E	E		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE} is withdrawn and can not be referenced.
80.00			STATUS	=>	WWSEO	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
81.00	AVAIL		F: + OOFAVAIL									
82.000	WHEN		AVAILTYPE	=	"Last Order" (149)				
82.100	IF		"Final" (0020)	=	CompareAll(WWSEOLSEO-d: LSEO )	STATUS			
82.200	WHEN		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST			
82.220	IF		F: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
82.222	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
82.224	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
82.226	THEN											
82.228			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
82.230	ELSE	82.226										
82.300			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E	This is only checked when a "Last Order" AVAIL has a country common to AVAIL.COUNTRYLIST and LSEO.COUNTRYLIST	{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
82.320	END	82.226										
82.340	END	82.200										
82.360	END	82.100										
84.000	END	82.000										
									
85.00	FEATURE		F:PRODSTRUCT-u									
86.00			WITHDRAWDATEEFF_T 	>	NOW()			E	E	E		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}  is withdrawn and can not be referenced.
90.00	END	72	

     */
    private void doHardwareChecks(EntityItem rootEntity, EntityItem modelItem, String wwseoStatus) 
    throws MiddlewareException, SQLException
    {
    	addHeading(3,"Hardware Model Checks:");
    	addDebug("doHardwareChecks: entered");
    	
		String modelCOFSUBCAT = getAttributeFlagEnabledValue(modelItem, "COFSUBCAT");
		addDebug(modelItem.getKey()+" COFSUBCAT: "+modelCOFSUBCAT);	
		// these checks are for all domains
							
		//77.00	PRODSTRUCT	F	WWSEOPRODSTRUCT-d									
		//78.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: PRODSTRUCT}

		int cnt = getCount("WWSEOPRODSTRUCT");
		EntityGroup eGrp = m_elist.getEntityGroup("PRODSTRUCT");
		Vector psVct = PokUtils.getAllLinkedEntities(rootEntity, "WWSEOPRODSTRUCT", "PRODSTRUCT");
		if(cnt == 0)	{
			//MINIMUM_ERR = must have at least one {0}
			args[0] = eGrp.getLongDescription();
			createMessage(CHECKLEVEL_RE,"MINIMUM_ERR",args);
		}else{
			addHeading(3,eGrp.getLongDescription()+" Checks:");
//			20121106 Add	79.202			SYSTEMMAX		WWSEOPRODSTRUCT	CONFQTY		E	E	E		{LD: WWSEOPRODSTRUCT} {LD: CONFQTY} {CONFQTY} can not be greater than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: SYSTEMMAX} {SYSTEMMAX}																																																																																																																																																																																																																																											
			checkSystemMaxAndConfqty(rootEntity,"WWSEOPRODSTRUCT",CHECKLEVEL_E);
	    	//82.10	IF		"Final" (0020)	=	CompareAll(WWSEOLSEO-d: LSEO )	STATUS					
	    	//For each LSEO that is Final, do the following checks	
	    	Vector lseovct = PokUtils.getAllLinkedEntities(rootEntity, "WWSEOLSEO", "LSEO");
	    	Vector finallseoVct = PokUtils.getEntitiesWithMatchedAttr(lseovct, "STATUS", STATUS_FINAL);
	    	addDebug("doHardwareChecks: lseovct "+lseovct.size()+" finallseoVct "+finallseoVct.size());
			for (int i=0; i<psVct.size(); i++) {
				EntityItem psitem = (EntityItem)psVct.elementAt(i);
				//80.00			STATUS	=>	WWSEO	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: STATUS} {STATUS}
				checkStatusVsDQ(psitem, "STATUS", rootEntity,CHECKLEVEL_E);
				//79.00			WITHDRAWDATE	>	NOW()			E	E	E		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE} is withdrawn and can not be referenced.
//				20121030 				RCQ00225128-RQ
				//				checkWithdrawnDate(psitem, "WITHDRAWDATE", CHECKLEVEL_E,true);
				//81.00	AVAIL		F: + OOFAVAIL	
				//82.000	WHEN		AVAILTYPE	=	"Last Order" (149)				
				//82.100	IF		"Final" (0020)	=	CompareAll(WWSEOLSEO-d: LSEO )	STATUS			
				//82.200	WHEN		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST					if the AVAIL has a country in its COUNTRYLIST that is in the LSEO.COUNTRYLIST, then do the following checks	
				//82.220	IF		F: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
				//82.222	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
				//82.224	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
				//82.226	THEN											
				//82.228			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
				//82.230	ELSE	82.226										
				//82.300			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E	This is only checked when a "Last Order" AVAIL has a country common to AVAIL.COUNTRYLIST and LSEO.COUNTRYLIST	{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}							
				checkLseoPSLOAvail(psitem,"PRODSTRUCTCATLGOR", "OOFAVAIL",finallseoVct,CHECKLEVEL_E,CHECKLEVEL_E);
				//82.320	END	82.226										
				//82.340	END	82.200										
				//82.360	END	82.100										
				//84.000	END	82.000									
	
				//85.00	FEATURE		F:PRODSTRUCT-u									
				//86.00			FEATURE.WITHDRAWDATEEFF_T 	>	NOW()			E	E	E		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}  is withdrawn and can not be referenced.
				checkWDFeatures(psitem,"FEATURE", CHECKLEVEL_E);
			}
			//90.00	END	72.00	
			finallseoVct.clear();
			lseovct.clear();
		}
		
		//73.00	SWPRODSTRUCT		WWSEOSWPRODSTUCT-d									
		//74.00			CountOf	=	0			E	E	E		a Hardware {LD: WWSEO} can not have a {LD: SWPRODSTRUCT}
		//PSLINK_ERR = a {0} {1} can not have a {2}
		cnt = getCount("WWSEOSWPRODSTRUCT");
		if(cnt != 0)	{
			eGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
			args[0] = "Hardware";
			args[1] = m_elist.getParentEntityGroup().getLongDescription();
			args[2] = eGrp.getLongDescription();
			createMessage(CHECKLEVEL_E,"PSLINK_ERR",args);
		}
		 
		// these checks are specific to domain
		//Change 20111223 13.00	AND		PDHDOMAIN	IN	ABR_PROPERTITIES	XCC_LIST2					XCC	
		if(SYSTEM.equals(modelCOFSUBCAT) && domainInRuleList(rootEntity, "XCC_LIST2")){
			doHwXCCDomainChecks(rootEntity, wwseoStatus);
        }
    }
 
    /***
     * SR10, 11, 12, 15, 17
     * @param rootEntity
     * @param wwseoStatus
     * @throws MiddlewareException
     * @throws SQLException
10.00	MODEL		MODELWWSEO-u								MODEL	
11.00	WHEN		COFCAT	=	"Hardware" (100) 						Hardware	
12.00	AND		COFSUBCAT	=	"System" (126)						System	
Change 20111223 13.00	AND		PDHDOMAIN	IN	ABR_PROPERTITIES	XCC_LIST2					XCC	
16.00	DERIVEDDATA		WWSEODERIVEDDATA-d									
17.00			CountOf	=	1			RW	RE	RE		must have exactly one {LD: DERIVEDDATA} {NDN: DERIVEDDATA}
18.00	SEOCG		SEOCGSEO-u									
19.00			CountOf	=>	1			RW	RE	RE		must have exactly one {LD: SEOCG} {NDN: SEOCG}
20.00	OnePLANAR	A	WWSEOPRODSTRUCT-d: 		WWSEOPRODSTRUCT	CONFQTY						
21.00		B	A: + PRODSTRUCT-u: 									
22.00		C	B: + FEATUREPLANAR-d		FEATUREPLANAR	QTY						
23.00		D	CountOf		PLANAR							
24.00		H	MODELWWSEO-u									
25.00		I	H: + MODELPLANAR-d		MODELPLANAR	QTY						
26.00		O	CountOf		I: PLANAR							
27.00			(A * C * D) + (I*O)	=	1			RW	RE	RE		must have exactly one {LD: PLANAR} {NDN: PRODSTRUCT}
28.00			D: + O:	=	1			RW	RE	RE		must have exactly one {LD: PLANAR} {NDN: PRODSTRUCT}
29.00	OneOrMoreMECHPKG	J	WWSEOPRODSTRUCT-d: 		WWSEOPRODSTRUCT	CONFQTY						
30.00		K	J: + PRODSTRUCT-u: 									
31.00		L	K: + FEATUREMECHPKG-d		FEATUREMECHPKG	QTY						
32.00		M	CountOf		L: MECHPKG							
33.00		N	MODELWWSEO-u									
34.00		P	N: + MODELMECHPKG-c		MODELMECHPKG	QTY						
35.00		Q	CountOf		P: MECHPKG							
36.00			(J * L * M) +( P * Q)	=>	1			RW	RE	RE		must have at least one {LD: MECHPKG}
37.00	BAY		L: + MECHPKGBAY-d									
38.00		R	UniqueBAYwithin		BAYTYPE & ACCSS & BAYFF			W	E	E		must have unique {LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} see {NDN: PRODSTRUCT}
39.00		S	R: + UniqueBAYmatchBAYSAVAIL	IN	BAYSAVAIL	BAYAVAILTYPE & ACCSS & BAYFF		W	E	E		{LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAYSAVAIL}
40.00	BAY		P: + MECHPKGBAY-d									
41.00		Z	UniqueBAYwithin		BAYTYPE & ACCSS & BAYFF			W	E	E		must have unique {LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} see {LD: MODEL}
42.00			Z: + UniqueBAYmatchBAYSAVAIL	IN	BAYSAVAIL	BAYAVAILTYPE & ACCSS & BAYFF		W	E	E		{LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAYSAVAIL}
43.00	BAYSAVAIL		WWSEOBAYSAVAIL-d									
44.00			BAYAVAILTYPE & ACCSS & BAYFF	IN	BAY	R: UniqueBAYwithin					OR continues	
45.00	OR			IN	BAY	Z: UniqueBAYwithin		W	E	E		{LD: BAYSAVAIL} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAY}
46.00			UniqueBAYSAVAIL		BAYTYPE & ACCSS & BAYFF			W	E	E		{LD: BAYSAVAIL} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must be Unique
46.40	END	43.00										
46.50	WHEN		"Initial" (10)	<>	WWSEO	SEOORDERCODE						
47.00	WEIGHTNDIMN		WWSEOWEIGHTNDIMN-d									
48.00			CountOf	=	1			RE	RE	RE		must have exactly one {LD: WEIGHTNDIMN}
48.10	END	46.50										
49.00	SLOT	T	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d:									
50.00			SLOTTYPE	IN	U: SLOTSAVAIL	SLOTTYPE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
51.00			UniqueSLOTwithin		SLOTTYPE & SLOTSIZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
52.00	SLOT	W	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: 									
53.00			SLOTTYPE	IN	V: SLOTSAVAIL	SLOTTYPE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
54.00			UniqueSLOTwithin		SLOTTYPE & SLOTSZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
55.00	SLOT	X	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: 									
56.00			SLOTTYPE	IN	Y: SLOTSAVAIL	SLOTTYPE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
57.00			UniqueSLOTwithin		SLOTTYPE & SLOTSZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
58.00	SLOTSAVAIL		WWSEOSLOTSAVAIL-d: 									
59.00			UniqueSLOTSAVAIL		ELEMENTTYPE & SLOTTYPE			W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must be Unique
60.00	WHEN	U	ELEMENTTYPE	=	"Memory Card" (0010)							
61.00			SLOTTYPE	IN	T: SLOT	SLOTTYPE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
62.00	END	60										
63.00	WHEN	V	ELEMENTTYPE	=	"Planar" (0020)							
64.00			SLOTTYPE	IN	W: SLOT	SLOTTYPE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
65.00	END	63										
66.00	WHEN	Y	ELEMENTTYPE	=	"Expansion Unit" (0030)							
67.00			SLOTTYPE	IN	X: SLOT	SLOTTYPE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
68.00	END	66	
Add	68.10	WHEN		"No" (11457)	=	WWSEO	SPECBID					if true, then GA	
	68.20			WWSEOIMG-d								IMG	
	68.22			UniqueCoverage		IMG		Yes		RW	RE	Required	{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
Change	68.24			COUNTRYLIST	"Containsaggregate E"	WWSEOLSEO-d	COUNTRYLIST			RW	RE		must have a {LD: IMG} for every country in the {LD: LSEO} {NDN: LSEO}
Change	68.26			MIN(PUBFROM)	<=	WWSEOLSEO-d	LSEOPUBDATEMTRGT	Yes		RW	RE		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
Change	68.28			MAX(PUBTO)	=>	WWSEOLSEO-d	LSEOUNPUBDATEMTRGT	Yes		RW	RE		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
	68.30	MM		WWSEOMM-d								MM	
	68.32			UniqueCoverage		MM		Yes		W	E	Optional	{LD: MM} {NDN: MM} have gaps in the date range or they overlap.
Change	68.34			COUNTRYLIST	"Containsaggregate E"	WWSEOLSEO-d	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: MM} for every country in the {LD: LSEO} {NDN: LSEO}
Change	68.36			MIN(PUBFROM)	<=	WWSEOLSEO-d	LSEOPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBFROM as early as or earlier than the  {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
Change	68.38			MAX(PUBTO)	=>	WWSEOLSEO-d	LSEOUNPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBTO as late as or later than the  {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
	68.40	FB		WWSEOFB-d								FB	
	68.42			UniqueCoverage		FB		Yes		W	E	Optional	{LD: FB} {NDN: FB} have gaps in the date range or they overlap.
Change	68.44			COUNTRYLIST	"Containsaggregate E"	WWSEOLSEO-d	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: FB} for every country in the {LD: LSEO} {NDN: LSEO}
Change	68.46			MIN(PUBFROM)	<=	WWSEOLSEO-d	LSEOPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBFROM as early as or earlier than the  {LD: LSEO} {LD: AVAIL} {NDN: A: AVAIL}
Change	68.48			MAX(PUBTO)	=>	WWSEOLSEO-d	LSEOUNPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBTO as late as or later than the  {LD: LSEO} {LD: AVAIL} {NDN: C: AVAIL}
Add	68.50	END	68.10									
69.00	END	11			
     */
    private void doHwXCCDomainChecks(EntityItem rootEntity, String wwseoStatus) 
    throws MiddlewareException, SQLException
    {
    	addHeading(3,"Hardware Model XCC Checks:");
       	addDebug("doHwXCCDomainChecks: entered");
    	int checklvl = getCheck_RW_RE_RE(wwseoStatus);	
	
		//16.00	DERIVEDDATA		WWSEODERIVEDDATA-d									
    	//17.00			CountOf	=	1			RW	RE	RE		must have exactly one {LD: DERIVEDDATA} {NDN: DERIVEDDATA}
		checkExactlyOne("WWSEODERIVEDDATA", "DERIVEDDATA", checklvl); 
    	
		//18.00	SEOCG		SEOCGSEO-u									
    	//19.00			CountOf	=	1			RW	RE	RE		must have exactly one {LD: SEOCG} {NDN: SEOCG}
//		20121204 Change Col F & N	19.000			CountOf	=>	1			RW	RE	RE		must have at least one {LD: SEOCG} {NDN: SEOCG}
		int cnt = getCount("SEOCGSEO");
		if(cnt ==0) {
			EntityGroup eGrp = m_elist.getEntityGroup("SEOCG");
			//  must be in a {LD MODELCG}
			//MUST_BE_IN_ATLEAST_ONE_ERR = must be in at least one {0}
			args[0] = eGrp.getLongDescription();
			createMessage(checklvl,"MUST_BE_IN_ATLEAST_ONE_ERR",args);
		}
//		checkExactlyOne("SEOCGSEO", "SEOCG", checklvl); 
				
		String seoordercode = getAttributeFlagEnabledValue(rootEntity, "SEOORDERCODE");	
		addDebug(rootEntity.getKey()+" SEOORDERCODE: "+seoordercode);
		//46.50	WHEN		"Initial" (10)	<>	WWSEO	SEOORDERCODE
		if (seoordercode != null && !seoordercode.equals(SEOORDERCODE_Initial)){
			//47.00	WEIGHTNDIMN		WWSEOWEIGHTNDIMN-d									
	    	//48.00			CountOf	=	1			RE	RE	RE		must have exactly one {LD: WEIGHTNDIMN}
			checkExactlyOne("WWSEOWEIGHTNDIMN", "WEIGHTNDIMN", CHECKLEVEL_RE); 			
		}
		//48.10	END	46.50								
			
    	//20.00	OnePLANAR	A	WWSEOPRODSTRUCT-d: 		WWSEOPRODSTRUCT	CONFQTY						
    	//21.00		B	A: + PRODSTRUCT-u: 									
    	//22.00		C	B: + FEATUREPLANAR-d		FEATUREPLANAR	QTY						
    	//23.00		D	CountOf		PLANAR							
    	//24.00		H	MODELWWSEO-u									
    	//25.00		I	H: + MODELPLANAR-d		MODELPLANAR	QTY						
    	//26.00		O	CountOf		I: PLANAR							
    	//27.00			(A * C * D) + (I*O)	=	1			RW	RE	RE		must have exactly one {LD: PLANAR} {NDN: PRODSTRUCT}
    	//28.00			D: + O:	=	1			RW	RE	RE		must have exactly one {LD: PLANAR} {NDN: PRODSTRUCT}
		checkCount("FEATUREPLANAR", "MODELPLANAR", "PLANAR",checklvl);

		//29.00	OneOrMoreMECHPKG	J	WWSEOPRODSTRUCT-d: 		WWSEOPRODSTRUCT	CONFQTY						
    	//30.00		K	J: + PRODSTRUCT-u: 									
    	//31.00		L	K: + FEATUREMECHPKG-d		FEATUREMECHPKG	QTY						
    	//32.00		M	CountOf		L: MECHPKG							
    	//33.00		N	MODELWWSEO-u									
    	//34.00		P	N: + MODELMECHPKG-c		MODELMECHPKG	QTY						
    	//35.00		Q	CountOf		P: MECHPKG							
    	//36.00			(J * L * M) +( P * Q)	=>	1			RW	RE	RE		must have at least one {LD: MECHPKG}
		checkExists("FEATUREMECHPKG", "MODELMECHPKG", "MECHPKG", checklvl);
				
		/*
    	37.00	BAY		L: + MECHPKGBAY-d									
    	38.00		R	UniqueBAYwithin		BAYTYPE & ACCSS & BAYFF			W	E	E		must have unique {LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} see {NDN: PRODSTRUCT}
    	39.00		S	R: + UniqueBAYmatchBAYSAVAIL	IN	BAYSAVAIL	BAYAVAILTYPE & ACCSS & BAYFF		W	E	E		{LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAYSAVAIL}
    	40.00	BAY		P: + MECHPKGBAY-d									
    	41.00		Z	UniqueBAYwithin		BAYTYPE & ACCSS & BAYFF			W	E	E		must have unique {LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} see {LD: MODEL}
    	42.00			Z: + UniqueBAYmatchBAYSAVAIL	IN	BAYSAVAIL	BAYAVAILTYPE & ACCSS & BAYFF		W	E	E		{LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAYSAVAIL}
    	43.00	BAYSAVAIL		WWSEOBAYSAVAIL-d									
    	44.00			BAYAVAILTYPE & ACCSS & BAYFF	IN	BAY	R: UniqueBAYwithin					OR continues	
    	45.00	OR			IN	BAY	Z: UniqueBAYwithin		W	E	E		{LD: BAYSAVAIL} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAY}
    	46.00			UniqueBAYSAVAIL		BAYTYPE & ACCSS & BAYFF			W	E	E		{LD: BAYSAVAIL} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must be Unique
		 */		
		checkBays(getCheck_W_E_E(wwseoStatus));

/*
    	49.00	SLOT	T	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREMEMORYCARD-d: MEMORYCARDSLOT-d:									
    	50.00			SLOTTYPE	IN	U: SLOTSAVAIL	SLOTTYPE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
    	51.00			UniqueSLOTwithin		SLOTTYPE & SLOTSIZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
    	52.00	SLOT	W	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: 									
    	53.00			SLOTTYPE	IN	V: SLOTSAVAIL	SLOTTYPE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
    	54.00			UniqueSLOTwithin		SLOTTYPE & SLOTSZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
    	55.00	SLOT	X	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: 									
    	56.00			SLOTTYPE	IN	Y: SLOTSAVAIL	SLOTTYPE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
    	57.00			UniqueSLOTwithin		SLOTTYPE & SLOTSZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
    	58.00	SLOTSAVAIL		WWSEOSLOTSAVAIL-d: 									
    	59.00			UniqueSLOTSAVAIL		ELEMENTTYPE & SLOTTYPE			W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must be Unique
    	60.00	WHEN	U	ELEMENTTYPE	=	"Memory Card" (0010)							
    	61.00			SLOTTYPE	IN	T: SLOT	SLOTTYPE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
    	62.00	END	60										
    	63.00	WHEN	V	ELEMENTTYPE	=	"Planar" (0020)							
    	64.00			SLOTTYPE	IN	W: SLOT	SLOTTYPE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
    	65.00	END	63										
    	66.00	WHEN	Y	ELEMENTTYPE	=	"Expansion Unit" (0030)							
    	67.00			SLOTTYPE	IN	X: SLOT	SLOTTYPE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
    	68.00	END	66										

 */
		checkSlots(getCheck_W_E_E(wwseoStatus));	

	 	checklvl = doCheck_N_RW_RE(wwseoStatus);//no more checks if Draft

    	if (CHECKLEVEL_NOOP==checklvl){
    		addDebug("doHwXCCDomainChecks: checks are bypassed because status is "+wwseoStatus);
    		return;
    	}
		String wwseoSPECBID = getAttributeFlagEnabledValue(rootEntity, "SPECBID");
		//Add	68.10	WHEN		"No" (11457)	=	WWSEO	SPECBID					if true, then GA
		if(wwseoSPECBID.equals(SPECBID_NO)){
			addDebug("doHwXCCDomainChecks: "+rootEntity.getKey()+" specbid=no");
    		
	    	addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Unique Coverage Checks:");
	    	
			int checklvlNWE = doCheck_N_W_E(wwseoStatus);
			Vector lseoVct = PokUtils.getAllLinkedEntities(rootEntity, "WWSEOLSEO", "LSEO");	
			for (int i=0; i<lseoVct.size(); i++){
				EntityItem lseoitem = (EntityItem)lseoVct.elementAt(i);
				//68.20			WWSEOIMG-d								IMG	
				//20120713b Delete below checking
				//68.22			UniqueCoverage		IMG		Yes		RW	RE	Required	{LD: IMG} {NDN: IMG} have gaps in the date range or they overlap.
				//68.24			COUNTRYLIST	"Contains aggregate E"	WWSEOLSEO-d	COUNTRYLIST			RW	RE		must have a {LD: IMG} for every country in the {LD: LSEO} {NDN: LSEO}
				//68.26			MIN(PUBFROM)	<=	WWSEOLSEO-d	LSEOPUBDATEMTRGT	Yes		RW	RE		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
				//68.28			MAX(PUBTO)	=>	WWSEOLSEO-d	LSEOUNPUBDATEMTRGT	Yes		RW	RE		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
//				checkUniqueCoverage(rootEntity, "WWSEOIMG",
//						"IMG", lseoitem,"LSEOPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT", checklvl, true);
//				IMGUniqueCoverageChkDone = true;
				
				//68.30	MM		WWSEOMM-d								MM	
				//68.32			UniqueCoverage		MM		Yes		W	E	Optional	{LD: MM} {NDN: MM} have gaps in the date range or they overlap.
				//68.34			COUNTRYLIST	"Contains aggregate E"	WWSEOLSEO-d	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: MM} for every country in the {LD: LSEO} {NDN: LSEO}
				//68.36			MIN(PUBFROM)	<=	WWSEOLSEO-d	LSEOPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBFROM as early as or earlier than the  {LD: LSEO} {LD: LSEOPUBDATEMTRGT} {LSEOPUBDATEMTRGT}
				//68.38			MAX(PUBTO)	=>	WWSEOLSEO-d	LSEOUNPUBDATEMTRGT	Yes		W	E		must have a {LD: MM} with a PUBTO as late as or later than the  {LD: LSEO} {LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}
				checkUniqueCoverage(rootEntity, "WWSEOMM",
						"MM", lseoitem,"LSEOPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT", checklvlNWE, false);
				//68.40	FB		WWSEOFB-d								FB	
				//68.42			UniqueCoverage		FB		Yes		W	E	Optional	{LD: FB} {NDN: FB} have gaps in the date range or they overlap.
				//68.44			COUNTRYLIST	"Contains aggregate E"	WWSEOLSEO-d	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: FB} for every country in the {LD: LSEO} {NDN: LSEO}
				//68.46			MIN(PUBFROM)	<=	WWSEOLSEO-d	LSEOPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBFROM as early as or earlier than the  {LD: LSEO} {LD: AVAIL} {NDN: A: AVAIL}
				//68.48			MAX(PUBTO)	=>	WWSEOLSEO-d	LSEOUNPUBDATEMTRGT	Yes		W	E		must have a {LD: FB} with a PUBTO as late as or later than the  {LD: LSEO} {LD: AVAIL} {NDN: C: AVAIL}
				checkUniqueCoverage(rootEntity, "WWSEOFB",
						"FB", lseoitem,"LSEOPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT", checklvlNWE, false);
			}
			lseoVct.clear();
			//68.50	END	68.10
		}
		// 69.00	END	11	
    }

    /**
     * Must have exactly one of these
     * @param relator
     * @param etype
     * @param checklvl
     * @throws MiddlewareException
     * @throws SQLException
     */
    private void checkExactlyOne(String relator, String etype, int checklvl) 
    throws MiddlewareException, SQLException
    {
    	int cnt = getCount(relator);
		if(cnt != 1)	{
			EntityGroup eGrp = m_elist.getEntityGroup(etype);
			//NEED_ONLY_ONE_ERR = must have exactly one {0} 
			if (eGrp.getEntityItemCount()==0){
				args[0] = eGrp.getLongDescription();
				createMessage(checklvl,	"NEED_ONLY_ONE_ERR",args);
			}else{
				for (int i=0; i<eGrp.getEntityItemCount(); i++){
					EntityItem item = eGrp.getEntityItem(i);
					args[0] = getLD_NDN(item);
					createMessage(checklvl,	"NEED_ONLY_ONE_ERR",args);
				}
			}		
		}
    }
    /***
     * SR10, 11, 12, 15, 17
     * @param rootEntity
     * @param wwseoStatus
     * @throws MiddlewareException
     * @throws SQLException
91.00	WWSEO		Root									
92.00	MODEL		MODELWWSEO-u									
93.00	WHEN		COFCAT	=	"Software" (101)							
94.00	SWPRODSTRUCT	G	WWSEOSWPRODSTRUCT-d									
95.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: SWPRODSTRUCT}
96.00			STATUS	=>	WWSEO	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
97.00	SWFEATURE		G: + SWPRODSTRUCT-u									
98.00			WITHDRAWDATEEFF_T 	>	NOW()			E	E	E		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: WITHDRAWDATEEFF_T } {WITHDRAWDATEEFF_T } is withdrawn and can not be referenced.
99.00	PRODSTRUCT		WWSEOPRODSTRUCT-d									
100.00			CountOf	=	0			E	E	E		a Software {LD: WWSEO} can not have a {LD: PRODSTRUCT}
103.00	AVAIL		G: + SWPRODSTRUCTAVAIL									
xx104.00	WHEN		AVAILTYPE	=	"Last Order" (149)							
xx105.00			EFFECTIVEDATE	>	NOW()			W	E	E		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  is withdrawn and can not be referenced.
xx106.00	END	104		
104.000	WHEN		AVAILTYPE	=	"Last Order" (149)							
104.020	IF		"Final" (0020)	=	CompareAll(WWSEOLSEO-d: LSEO )	STATUS					For each LSEO that is Final, do the following checks	
104.040	WHEN		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST					if the AVAIL has a country in its COUNTRYLIST that is in the LSEO.COUNTRYLIST, then do the following checks	
104.060	IF		G: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
104.080	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
104.100	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
104.120	THEN											
104.140			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
104.160	ELSE	104.120										
105.000			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
105.020	END	104.120										
105.040	END	104.040										
105.060	END	104.020										
106.000	END	104.000																		
								
107.00	END	93											

     */
    private void doSoftwareChecks(EntityItem rootEntity, String wwseoStatus) 
    throws MiddlewareException, SQLException
    {
    	addHeading(3,"Software Model Checks:");
    	addDebug("doSoftwareChecks: entered");
    	int checklvl = getCheck_W_E_E(wwseoStatus);
    								
    	//94.00	SWPRODSTRUCT	G	WWSEOSWPRODSTRUCT-d									
    	//95.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: SWPRODSTRUCT}

		int cnt = getCount("WWSEOSWPRODSTRUCT");
		EntityGroup eGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
		if(cnt == 0)	{
			//MINIMUM_ERR = must have at least one {0}
			args[0] = eGrp.getLongDescription();
			createMessage(CHECKLEVEL_RE,"MINIMUM_ERR",args);
		}else{
			addHeading(3,eGrp.getLongDescription()+" Checks:");
	    	//For each LSEO that is Final, do the following checks	
	    	Vector lseovct = PokUtils.getAllLinkedEntities(rootEntity, "WWSEOLSEO", "LSEO");
	    	Vector finallseoVct = PokUtils.getEntitiesWithMatchedAttr(lseovct, "STATUS", STATUS_FINAL);
	    	addDebug("doSoftwareChecks: lseovct "+lseovct.size()+" finallseoVct "+finallseoVct.size());
			for (int i=0; i<eGrp.getEntityItemCount(); i++) {
				EntityItem psitem = eGrp.getEntityItem(i);
				//96.00			STATUS	=>	WWSEO	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: STATUS} {STATUS}
				checkStatusVsDQ(psitem, "STATUS", rootEntity,checklvl);
		    	//103.00	AVAIL		G: + SWPRODSTRUCTAVAIL									
		    	//xx104.00	WHEN		AVAILTYPE	=	"Last Order" (149)							
		    	//xx105.00			EFFECTIVEDATE	>	NOW()			W	E	E		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  is withdrawn and can not be referenced.
				//old checkWDAvails(psitem, "SWPRODSTRUCTAVAIL", checklvl,true);
		    	//xx106.00	END	104.00	
				
				//104.000	WHEN		AVAILTYPE	=	"Last Order" (149)							
				//104.020	IF		"Final" (0020)	=	CompareAll(WWSEOLSEO-d: LSEO )	STATUS					For each LSEO that is Final, do the following checks	
				//104.040	WHEN		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST					if the AVAIL has a country in its COUNTRYLIST that is in the LSEO.COUNTRYLIST, then do the following checks	
				//104.060	IF		G: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
				//104.080	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
				//104.100	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
				//104.120	THEN											
				//104.140			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
				//104.160	ELSE	104.120										
				//105.000			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}							
				checkLseoPSLOAvail(psitem,"SWPRODSTRCATLGOR", "SWPRODSTRUCTAVAIL",finallseoVct,CHECKLEVEL_E,CHECKLEVEL_E);
				//105.020	END	104.120										
				//105.040	END	104.040										
				//105.060	END	104.020										
				//106.000	END	104.000										
				
		    	//97.00	SWFEATURE		G: + SWPRODSTRUCT-u									
		    	//98.00			WITHDRAWDATEEFF_T 	>	NOW()			E	E	E		{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: WITHDRAWDATEEFF_T } {WITHDRAWDATEEFF_T } is withdrawn and can not be referenced.
				checkWDFeatures(psitem, "SWFEATURE", CHECKLEVEL_E);
			}
			finallseoVct.clear();
			lseovct.clear();
		}
		
    	//99.00	PRODSTRUCT		WWSEOPRODSTRUCT-d									
    	//100.00			CountOf	=	0			E	E	E		a Software {LD: WWSEO} can not have a {LD: PRODSTRUCT}
		//PSLINK_ERR = a {0} {1} can not have a {2}
		cnt = getCount("WWSEOPRODSTRUCT");
		if(cnt != 0)	{
			eGrp = m_elist.getEntityGroup("PRODSTRUCT");
			args[0] = "Software";
			args[1] = m_elist.getParentEntityGroup().getLongDescription();
			args[2] = eGrp.getLongDescription();
			createMessage(CHECKLEVEL_E,"PSLINK_ERR",args);
		}
		
    	//101.00	SVCPRODSTRUCT		WWSEOSVCPRODSTUCT-d									
    	//102.00			CountOf	=	0			E	E	E		a Software {LD: WWSEO} can not have a {LD: SVCPRODSTRUCT}
		/*delete cnt = getCount("WWSEOSVCPRODSTRUCT");
		if(cnt != 0)	{
			eGrp = m_elist.getEntityGroup("SVCPRODSTRUCT");
			args[0] = "Software";
			args[1] = m_elist.getParentEntityGroup().getLongDescription();
			args[2] = eGrp.getLongDescription();
			createMessage(CHECKLEVEL_E,"PSLINK_ERR",args);
		}*/
    	//107.00	END	93.00										
    }
    
    /***
     * SR10, 11, 12, 15, 17
     * @param rootEntity
     * @param modelItem
     * @param wwseoStatus
     * @throws MiddlewareException
     * @throws SQLException	
108.00	WWSEO		Root									
109.00	MODEL		MODELWWSEO-u									
110.00	WHEN		COFCAT	=	"Service" (102)							
111.00	SEOCG		SEOCGSEO-u									
112.00			CountOf	=	0			E	E	E		must not be in a {LD: SEOCG} {NDN: SEOCG}
113.00			SPECBID	=	WWSEO	SPECBID		E	E	E		{LD: SPECBID} {WWSEO.SPECBID} must match {LD: MODEL} {LD: SPECBID} {MODEL.SPECBID}
117.00	PRODSTRUCT		WWSEOPRODSTUCT-d									
118.00			CountOf	=	0			E	E	E		a Service {LD: WWSEO} can not have a {LD: PRODSTRUCT}
119.00	SWPRODSTRUCT		WWSEOSWPRODSTUCT-d									
120.00			CountOf	=	0			E	E	E		a Service {LD: WWSEO} can not have a {LD: SWPRODSTRUCT}
121.00	END	110												
								
     */
    private void doServiceChecks(EntityItem rootEntity, EntityItem modelItem, String wwseoStatus) 
    throws MiddlewareException, SQLException
    {
    	addHeading(3,"Service Model Checks:");
    	addDebug("doServiceChecks: entered");
    
		String wwseoSPECBID = getAttributeFlagEnabledValue(rootEntity, "SPECBID");
		String modelSPECBID = getAttributeFlagEnabledValue(modelItem, "SPECBID");
		
		//113.00			SPECBID	=	WWSEO	SPECBID		E	E	E		{LD: SPECBID} {WWSEO.SPECBID} must match {LD: MODEL} {LD: SPECBID} {MODEL.SPECBID}
		addDebug(modelItem.getKey()+" SPECBID: "+modelSPECBID+" "+rootEntity.getKey()+
				" wwseoSPECBID "+wwseoSPECBID);
		if (modelSPECBID != null && !modelSPECBID.equals(wwseoSPECBID)){
			//NO_SPECBID_MATCH = {0} must match {1} {2} 
			args[0] = getLD_Value(rootEntity, "SPECBID");
			args[1] = getLD_NDN(modelItem);
			args[2] = getLD_Value(modelItem, "SPECBID");;
			createMessage(CHECKLEVEL_E,"NO_SPECBID_MATCH",args);
		}

		//111.00	SEOCG		SEOCGSEO-u									
		//112.00			CountOf	=	0			E	E	E		must not be in a {LD: SEOCG} {NDN: SEOCG}
		int cnt = getCount("SEOCGSEO");
		if(cnt != 0)	{
			EntityGroup eGrp = m_elist.getEntityGroup("SEOCG");
			//MUST_NOT_BE_IN_ERR=  must not be in a {0}
			for (int i=0; i<eGrp.getEntityItemCount(); i++){
				EntityItem seoitem = eGrp.getEntityItem(i);
				args[0] = getLD_NDN(seoitem);
				createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR",args);
			}
		}
		
		//117.00	PRODSTRUCT		WWSEOPRODSTUCT-d									
		//118.00			CountOf	=	0			E	E	E		a Service {LD: WWSEO} can not have a {LD: PRODSTRUCT}
		//PSLINK_ERR = a {0} {1} can not have a {2}
		cnt = getCount("WWSEOPRODSTRUCT");
		if(cnt != 0)	{
			EntityGroup eGrp = m_elist.getEntityGroup("PRODSTRUCT");
			args[0] = "Service";
			args[1] = m_elist.getParentEntityGroup().getLongDescription();
			args[2] = eGrp.getLongDescription();
			createMessage(CHECKLEVEL_E,"PSLINK_ERR",args);
		}
		
		//119.00	SWPRODSTRUCT		WWSEOSWPRODSTUCT-d									
		//120.00			CountOf	=	0			E	E	E		a Service {LD: WWSEO} can not have a {LD: SWPRODSTRUCT}
		cnt = getCount("WWSEOSWPRODSTRUCT");
		if(cnt != 0)	{
			EntityGroup eGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
			args[0] = "Service";
			args[1] = m_elist.getParentEntityGroup().getLongDescription();
			args[2] = eGrp.getLongDescription();
			createMessage(CHECKLEVEL_E,"PSLINK_ERR",args);
		}
		//	121.00	END	110	
    }
    
	/****************************************
	* Check for withdrawn date on the (sw)feature
	*
	*/
    private void checkWDFeatures(EntityItem psEntity, String fctype, int checklvl)
	throws java.sql.SQLException, MiddlewareException
	{
    	addDebug("checkWDFeatures checking entity: "+psEntity.getKey());
    	for (int i=0; i<psEntity.getUpLinkCount(); i++){ // look at (sw)feature
    		EntityItem fcitem = (EntityItem)psEntity.getUpLink(i);
    		if (fcitem.getEntityType().equals(fctype)){ // right entity
    			String effDate = PokUtils.getAttributeValue(fcitem, "WITHDRAWDATEEFF_T",", ", "", false);
    			addDebug("checkWDFeatures checking "+fcitem.getKey()+" WITHDRAWDATEEFF_T: "+effDate);
    			if (effDate.length()>0 && effDate.compareTo(getCurrentDate())<=0){
    				//WITHDRAWN_ERR = {0} {1} is withdrawn and can not be referenced.
    				//{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: WITHDRAWDATEEFF_T } {WITHDRAWDATEEFF_T } is withdrawn and can not be referenced.
    				args[0] = getLD_NDN(psEntity)+" "+getLD_NDN(fcitem);
    				args[1] = getLD_Value(fcitem, "WITHDRAWDATEEFF_T");
    				createMessage(checklvl,"WITHDRAWN_ERR",args);
    			}
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
        String desc =  "WWSEO ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
		return "1.21";
    }

    /**********************************************************************************
    * Given a root entity type, the path including the relative entity type is given.
    * This counts the number of Relatives of the EntityType specified via the path and
    * performs the comparison of this count to the # specified. This is a count of paths
    * to entities of the type specified so that even if the same instance of the entity
    * is linked multiple times it is counted multiple times.
	*
	* e.g.  FEATUREMON.QTY * CountOf( FEATUREMON-d: MON)  < = 1
	*
	* Note:  if FEATUREMON.QTY does not exist, then assume a default of 1
	*		A Feature can have a maximum of 1 Monitor.
	*
    */
    private int getCount(EntityList list, String relatorType) throws MiddlewareException
    {
		int count = 0;

		EntityGroup relGrp = list.getEntityGroup(relatorType);
		if (relGrp ==null){
			throw new MiddlewareException(relatorType+" is missing from extract");
		}
		if (relGrp.getEntityItemCount()>0){
			for(int i=0; i<relGrp.getEntityItemCount(); i++){
				int qty = 1;
				EntityItem relItem = relGrp.getEntityItem(i);
				EANAttribute attr = relItem.getAttribute("QTY");
            	if (attr != null){
					qty = Integer.parseInt(attr.get().toString());
				}
				count+=qty;
        		addDebug("getCount "+relItem.getKey()+" qty "+qty);
			}
		}

        addDebug("getCount Total count found for "+relatorType+" = "+count);
		return count;
	}

    /***********************************************
    *1.	OnePLANAR (Key 19)
A "Hardware" WWSEO (Key 10) that is a "System" must have one or more plannar

A Planar (PLANAR) can exist as a child of Feature (FEATURE) and/or as a child of Model (MODEL). There are several relators with an attribute indicating the number or quantity of the child entity that must be used in the computation (Key 26):
WWSEOPRODSTRUCT	CONFQTY
FEATUREPLANAR	QTY
MODELPLANAR	QTY
Note: if the relator exists and the attribute does not exist (aka empty), then assume a value of 1.

The check in Key 27 ensures that the WWSEO must have one or more Features with a child Planar.
    */
    private void checkCount(String fcrelator, String mdlrelator, String entity, int checklvl)throws MiddlewareException
    {
		addDebug("checkCount entered for "+fcrelator+" "+mdlrelator+" "+entity);
		EntityGroup eGrp = m_elist.getEntityGroup(entity);
		int fccnt = getCount(m_elist,fcrelator);
		int mdlcnt = getCount(mdlList,mdlrelator);
//		if(fccnt == 0) {
//			//NEED_ONLY_ONE_ERR = must have exactly one {0}
//			args[0] = eGrp.getLongDescription();
//			createMessage(checklvl,"MINIMUM_ERR",args);
//		}else{
//			int mdlcnt = getCount(mdlList,mdlrelator);
//			if(mdlcnt == 0) {
//				//NEED_ONLY_ONE_ERR = must have exactly one {0}
//				args[0] = eGrp.getLongDescription();
//				createMessage(checklvl,"MINIMUM_ERR",args);
//			}else{
				// look at the combination
				if (fccnt+mdlcnt==0){
					//NEED_ONLY_ONE_ERR = must have exactly one {0}
					args[0] = eGrp.getLongDescription();
					createMessage(checklvl,"MINIMUM_ERR",args);
				}else if (fccnt>0){ // must be one now
					int totalConfQty = 0;
					// check for (WWSEOPRODSTRUCT.CONFQTY) on the WWSEOPRODSTRUCT-PRODSTRUCT-FEATURE-fcrelator
					EntityItem fcrelItem = m_elist.getEntityGroup(fcrelator).getEntityItem(0);
					for (int ui=0; ui<fcrelItem.getUpLinkCount(); ui++)
					{
						EntityItem featItem = (EntityItem)fcrelItem.getUpLink(ui);
						if (featItem.getEntityType().equals("FEATURE"))
						{
							addDebug("checkCount "+fcrelItem.getKey()+" uplink["+ui+"]: "+featItem.getKey());
							for (int i=0; i<featItem.getDownLinkCount(); i++)
							{
								EntityItem psItem = (EntityItem)featItem.getDownLink(i);
								if (psItem.getEntityType().equals("PRODSTRUCT")) {
									addDebug("checkCount "+featItem.getKey()+" dnlink["+i+"]: "+psItem.getKey());
									// find parent WWSEOPS
									for (int uii=0; uii<psItem.getUpLinkCount(); uii++)
									{
										EntityItem wwseopsItem = (EntityItem)psItem.getUpLink(uii);
										if (wwseopsItem.getEntityType().equals("WWSEOPRODSTRUCT"))
										{
											addDebug("checkCount "+psItem.getKey()+" uplink["+uii+"]: "+wwseopsItem.getKey());
											String confQty = PokUtils.getAttributeValue(wwseopsItem, "CONFQTY",", ", "1", false);
											addDebug("checkCount "+wwseopsItem.getKey()+" CONFQTY: "+confQty);
											int iconfQty = Integer.parseInt(confQty);
											totalConfQty+=iconfQty;
										}
									}
								}
							}
						}
					}
					addDebug("checkCount totalConfQty: "+totalConfQty);
					if(totalConfQty == 0) {
						//NEED_ONLY_ONE_ERR = must have exactly one {0}
						args[0] = eGrp.getLongDescription();
						createMessage(checklvl,"MINIMUM_ERR",args);
					}
				}
//			}
//		}
	}
    /***********************************************
    *Check for number of MECHPKG using qty on relators
	*
	*2.	OneOrMoreMECHPKG (Key 28)
A Mechanical Package (MECHPKG) can exist as a child of Feature (FEATURE) and/or as a child of Model (MODEL). There are several relators with an attribute indicating the number or quantity of the child entity that must be used in the computation (Key 35):
WWSEOPRODSTRUCT	CONFQTY
FEATUREMECHPKG	QTY
MODELMECHPKG	QTY 
Note: if the relator exists and the attribute does not exist (aka empty), then assume a value of 1.

    */
    private void checkExists(String fcrelator, String mdlrelator, String entity, int checklvl)throws MiddlewareException
    {
		addDebug("checkExists entered for "+fcrelator+" "+mdlrelator+" "+entity);
		EntityGroup eGrp = m_elist.getEntityGroup(entity);
		int fccnt = getCount(m_elist,fcrelator);
		if(fccnt > 0) {
			addDebug("checkExists fccnt "+fccnt);
		}else{
			int mdlcnt = getCount(mdlList,mdlrelator);
			if(mdlcnt > 0) {
				addDebug("checkExists mdlcnt "+mdlcnt);
			}else{
				//MINIMUM_ERR = must have at least one {0}
				args[0] = eGrp.getLongDescription();
				createMessage(checklvl,"MINIMUM_ERR",args);
			}
		}
	}

    /***********************************************
     * check bays
OneOrMoreMECHPKG	
	J	WWSEOPRODSTRUCT-d: 		WWSEOPRODSTRUCT	CONFQTY						
	K	J: + PRODSTRUCT-u: 									
	L	K: + FEATUREMECHPKG-d		FEATUREMECHPKG	QTY						
						
	N	MODELWWSEO-u									
	P	N: + MODELMECHPKG-c		MODELMECHPKG	QTY						
	
36.00	BAY		L: + MECHPKGBAY-d									
37.00		R	UniqueBAYwithin		BAYTYPE & ACCSS & BAYFF			W	E	E		must have unique {LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} see {NDN: PRODSTRUCT}
38.00		S	R: + UniqueBAYmatchBAYSAVAIL	IN	BAYSAVAIL	BAYAVAILTYPE & ACCSS & BAYFF		W	E	E		{LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAYSAVAIL}
39.00	BAY		P: + MECHPKGBAY-d									
40.00		Z	UniqueBAYwithin		BAYTYPE & ACCSS & BAYFF			W	E	E		must have unique {LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} see {LD: MODEL}
41.00			Z: + UniqueBAYmatchBAYSAVAIL	IN	BAYSAVAIL	BAYAVAILTYPE & ACCSS & BAYFF		W	E	E		{LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAYSAVAIL}
42.00	BAYSAVAIL		WWSEOBAYSAVAIL-d									
43.00			BAYAVAILTYPE & ACCSS & BAYFF	IN	BAY	R: UniqueBAYwithin						
44.00	OR			IN	BAY	Z: UniqueBAYwithin		W	E	E		{LD: BAYSAVAIL} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAY}
45.00			UniqueBAYSAVAIL		BAYTYPE & ACCSS & BAYFF			W	E	E		{LD: BAYSAVAIL} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must be Unique

3.	UniqueBAYwithin (Key 37 & Key 40)
Each Mechanical Package (MECHPKG) must have a unique set of Bays (BAY). For each MECHPKG, consider all 
children of type BAY. The string formed by the concatenation of BAYTYPE, ACCSS, and BAYFF must be unique.

4.	UniqueBAYmatchBAYSAVAIL (Key 38 & Key 41)
Every value found in Key 37 Column F (i.e. BAYTYPE & ACCSS & BAYFF) must have a corresponding Bays Available 
(BAYSAVAIL) with identical BAYAVAILTYPE & ACCSS & BAYFF.

5.	OR (Key 44)
This is a �logical OR� and ties this row to the prior row. In other words, BAYAVAILTYPE & ACCSS & BAYFF must 
be IN either R: UniqueBAYwithin OR Z: UniqueBAYwithin.

6.	UniqueBAYwithin (Key 43)
Each Bays Available (BAYSAVAIL) with BAYAVAILTYPE & ACCSS & BAYFF must have at least one BAY with identical
BAYTYPE & ACCSS & BAYFF.

7.	UniqueBAYSAVAIL (Key 45)
For all Bays Available (BAYSAVAIL), the string BAYTYPE & ACCSS & BAYFF must be Unique.

     *
     */
     private void checkBays(int checklvl)
     {
         Hashtable bayTbl = new Hashtable();
         Hashtable baysAvailTbl = new Hashtable();
         //HashSet baysavailSet = new HashSet();

 		Iterator itr;

 		addDebug("checkBays: entered");
 		
 		//Each Mechanical Package (MECHPKG) must have a unique set of Bays (BAY). For each MECHPKG, consider all 
 		//children of type BAY. The string formed by the concatenation of BAYTYPE, ACCSS, and BAYFF must be unique.
 		// must check each MECHPKG separately, both from FEATURE and from MODEL
 		EntityGroup mechPkgGrp = m_elist.getEntityGroup("MECHPKG"); // from FEATURE
 		for (int i=0; i<mechPkgGrp.getEntityItemCount(); i++){
 			EntityItem mechpkgItem = mechPkgGrp.getEntityItem(i);
 			Vector bayVector = PokUtils.getAllLinkedEntities(mechpkgItem, "MECHPKGBAY", "BAY");
 			addDebug("checkBays: FEATURE "+mechpkgItem.getKey()+" bayVector.size "+bayVector.size());
 			// accumulate all possible BAY configurations from both and use to check BAYSAVAIL
 			Hashtable bayTblSubset = checkBays(bayVector, checklvl);
 			bayTbl.putAll(bayTblSubset);
 			bayTblSubset.clear();
 		}
 		
 		mechPkgGrp = mdlList.getEntityGroup("MECHPKG"); // from MODEL
 		for (int i=0; i<mechPkgGrp.getEntityItemCount(); i++){
 			EntityItem mechpkgItem = mechPkgGrp.getEntityItem(i);
 			Vector bayVector = PokUtils.getAllLinkedEntities(mechpkgItem, "MECHPKGBAY", "BAY");
 			addDebug("checkBays: MODEL "+mechpkgItem.getKey()+" bayVector.size "+bayVector.size());
 			// accumulate all possible BAY configurations from both and use to check BAYSAVAIL
 			Hashtable bayTblSubset = checkBays(bayVector, checklvl);
 			bayTbl.putAll(bayTblSubset);
 			bayTblSubset.clear();
 		}
 		
 		EntityGroup bayGrp = m_elist.getEntityGroup("BAY");
 		EntityGroup baysavailGrp = m_elist.getEntityGroup("BAYSAVAIL");
 	  	addHeading(4,mechPkgGrp.getLongDescription()+" "+bayGrp.getLongDescription()+" and "+
 	  			baysavailGrp.getLongDescription()+" Checks:");
 	  	
 		for(int i = 0; i < baysavailGrp.getEntityItemCount(); i++)
 		{
 			EntityItem baysavailItem = baysavailGrp.getEntityItem(i);
 			String bayAvailType = PokUtils.getAttributeValue(baysavailItem, "BAYAVAILTYPE",", ", "", false);
 			String bayAvailTypeFlag = getAttributeFlagEnabledValue(baysavailItem, "BAYAVAILTYPE");
 			String bayAccss = PokUtils.getAttributeValue(baysavailItem, "ACCSS",", ", "", false);
 			String bayAccssFlag = getAttributeFlagEnabledValue(baysavailItem, "ACCSS");
 			String bayFF = PokUtils.getAttributeValue(baysavailItem, "BAYFF",", ", "", false);
 			String bayFFFlag = getAttributeFlagEnabledValue(baysavailItem, "BAYFF");
 			String bayInfo = bayAvailType + ", " + bayAccss + ", " + bayFF;
 			String bayFlags = bayAvailTypeFlag + "," + bayAccssFlag + "," + bayFFFlag;
 			addDebug("checkBays: "+baysavailItem.getKey()+" bayInfo: "+bayInfo+"["+bayFlags+"]");
 			if (baysAvailTbl.containsKey(bayFlags)){
 				//LD: BAYSAVAIL} {LD: BAYAVAILTYPE} {BAYAVAILTYPE} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must be Unique
 				//MUST_HAVE_UNIQUE_ERR = {0} {1} {2} {3} must be Unique
 				args[0]=baysavailGrp.getLongDescription();
 				args[1]=getLD_Value(baysavailItem, "BAYAVAILTYPE");
 				args[2]=getLD_Value(baysavailItem, "ACCSS");
 				args[3]=getLD_Value(baysavailItem, "BAYFF");
 				createMessage(checklvl,"MUST_HAVE_UNIQUE_ERR",args);
 			}else{
 				baysAvailTbl.put(bayFlags,baysavailItem);
 			}
 		}

 		//MECHPKG with BAY.BAYTYPE+BAY.ACCSS+BAY.BAYFF must have a matching BAYSAVAIL.BAYAVAILTYPE+BAYSAVAIL.ACCSS+BAYSAVAIL.BAYFF linked via WWSEOBAYSAVAIL
 		itr = bayTbl.keySet().iterator();
 		while(itr.hasNext())
 		{
 			String bayInfo = (String) itr.next();
 			//Every value found in Key 37 Column F (i.e. BAYTYPE & ACCSS & BAYFF) must have a corresponding Bays Available 
 			//(BAYSAVAIL) with identical BAYAVAILTYPE & ACCSS & BAYFF.
 			//{LD: BAY} {LD: BAY} {BAY} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAYSAVAIL}
 			if(!baysAvailTbl.containsKey(bayInfo))
 			{
 				EntityItem bayItem = (EntityItem) bayTbl.get(bayInfo);
 				//CORRESPONDING_ERR = {0} {1} {2} {3} must have matching {4}
 				//{LD: BAY} {LD: BAYTYPE} {BAYTYPE} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAYSAVAIL}
 				args[0]=bayGrp.getLongDescription();
 				args[1]=getLD_Value(bayItem, "BAYTYPE");
 				args[2]=getLD_Value(bayItem, "ACCSS");
 				args[3]=getLD_Value(bayItem, "BAYFF");
 				args[4]=baysavailGrp.getLongDescription();
 				createMessage(checklvl,"CORRESPONDING_ERR",args);
 			}
 		}//end of while(itr.hasNext())

 		// make sure baysavail match bays, if no bays then there should not be any baysavail
 		itr = baysAvailTbl.keySet().iterator();
 		while(itr.hasNext())
 		{
 			String bayInfo = (String) itr.next();
 			//Each Bays Available (BAYSAVAIL) with BAYAVAILTYPE & ACCSS & BAYFF must have at least one BAY 
 			//with identical BAYTYPE & ACCSS & BAYFF.
 			if(!bayTbl.containsKey(bayInfo))
 			{
 				EntityItem baysavailItem = (EntityItem) baysAvailTbl.get(bayInfo);
 				// CORRESPONDING_ERR = {0} {1} {2} {3} must have matching {4}
 				//{LD: BAYSAVAIL} {LD: BAYAVAILTYPE} {BAYAVAILTYPE} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must have matching {LD: BAY}
 				args[0]=baysavailGrp.getLongDescription();
 				args[1]=getLD_Value(baysavailItem, "BAYAVAILTYPE");
 				args[2]=getLD_Value(baysavailItem, "ACCSS");
 				args[3]=getLD_Value(baysavailItem, "BAYFF");
 				args[4]=bayGrp.getLongDescription();
 				createMessage(checklvl,"CORRESPONDING_ERR",args);
 			}
 		}//end of while(itr.hasNext())

 		bayTbl.clear();
 		bayTbl = null;
 		baysAvailTbl.clear();
 		baysAvailTbl = null;
     }

     private Hashtable checkBays(Vector bayVector, int checklvl){
    	Hashtable bayTbl = new Hashtable();
  		for(int i = 0; i < bayVector.size(); i++)
 		{
 			EntityItem bayItem = (EntityItem)bayVector.elementAt(i);
 			String bayType = PokUtils.getAttributeValue(bayItem, "BAYTYPE",", ", "", false);
 			String bayTypeFlag = getAttributeFlagEnabledValue(bayItem, "BAYTYPE");
 			String bayAccss = PokUtils.getAttributeValue(bayItem, "ACCSS",", ", "", false);
 			String bayAccssFlag = getAttributeFlagEnabledValue(bayItem, "ACCSS");
 			String bayFF = PokUtils.getAttributeValue(bayItem, "BAYFF",", ", "", false);
 			String bayFFFlag = getAttributeFlagEnabledValue(bayItem, "BAYFF");
 			String bayInfo = bayType + ", " + bayAccss + ", " + bayFF;
 			String bayFlags = bayTypeFlag + "," + bayAccssFlag + "," + bayFFFlag;
 			addDebug("checkBays: "+bayItem.getKey()+" bayInfo: "+bayInfo+"["+bayFlags+"]");
 			if (bayTbl.containsKey(bayFlags)){
 				EntityGroup bayGrp = bayItem.getEntityGroup();
 				//{LD: BAY} {LD: BAYTYPE} {BAYTYPE} {LD: ACCSS} {ACCSS} {LD: BAYFF} {BAYFF} must be Unique
 				//MUST_HAVE_UNIQUE_ERR = {0} {1} {2} {3} must be Unique
 				args[0]=bayGrp.getLongDescription();
 				args[1]=getLD_Value(bayItem, "BAYTYPE");
 				args[2]=getLD_Value(bayItem, "ACCSS");
 				args[3]=getLD_Value(bayItem, "BAYFF");
 				createMessage(checklvl,"MUST_HAVE_UNIQUE_ERR",args);
 			}else{
 				bayTbl.put(bayFlags,bayItem);
 			}
 		}  
  		return bayTbl;
     }
/*
50.00			SLOTTYPE & SLOTSZE	IN	U: SLOTSAVAIL	SLOTTYPE & SLOTSZE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
51.00			UniqueSLOTwithin		SLOTTYPE & SLOTSZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
52.00	SLOT	W	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREPLANAR-d: PLANARSLOT-d: 									
53.00			SLOTTYPE & SLOTSZE	IN	V: SLOTSAVAIL	SLOTTYPE & SLOTSZE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
54.00			UniqueSLOTwithin		SLOTTYPE & SLOTSZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
55.00	SLOT	X	WWSEOPRODSTRUCT-d: PRODSTRUCT-u:  FEATUREEXPDUNIT-d: EXPDUNITSLOT-d: 									
56.00			SLOTTYPE & SLOTSZE	IN	Y: SLOTSAVAIL	SLOTTYPE & SLOTSZE		W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
57.00			UniqueSLOTwithin		SLOTTYPE & SLOTSZE			W	E	E		{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
58.00	SLOTSAVAIL		WWSEOSLOTSAVAIL-d: 									
59.00			UniqueSLOTSAVAIL		ELEMENTTYPE & SLOTTYPE & SLOTSZE			W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must be Unique
60.00	WHEN	U	ELEMENTTYPE	=	"Memory Card" (0010)							
61.00			SLOTTYPE & SLOTSZE	IN	T: SLOT	SLOTTYPE & SLOTSZE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
62.00	END	60.00										
63.00	WHEN	V	ELEMENTTYPE	=	"Planar" (0020)							
64.00			SLOTTYPE & SLOTSZE	IN	W: SLOT	SLOTTYPE & SLOTSZE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
65.00	END	63.00										
66.00	WHEN	Y	ELEMENTTYPE	=	"Expansion Unit" (0030)							
67.00			SLOTTYPE & SLOTSZE	IN	X: SLOT	SLOTTYPE & SLOTSZE		W	E	E		{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
68.00	END	66.00										

8.	UniqueSLOTwithin (Key 50)
Each SLOT must be unique within its parent entity type. This is checked using ELEMENTTYPE & ELEMENTID & SLOTTYPE & SLOTSZE

9.	UniqueSLOTSAVAIL (Key 58)
For all Slots Available (SLOTSAVAIL), the string ELEMENTTYPE & SLOTTYPE & SLOTSZE must be Unique.

 */
    private void checkSlots(int checklvl)
    {
        Hashtable slotsavailMemCardSet  = new Hashtable();
        Hashtable slotsavailPlanarSet  = new Hashtable();
        Hashtable slotsavailExpUnitSet  = new Hashtable();
		/*
		ELEMENTTYPE	0010	Memory Card
		ELEMENTTYPE	0020	Planar
		ELEMENTTYPE	0030	Expansion Unit
		*/
		String memCardTypeFlag = "0010";
		String planarTypeFlag = "0020";
		String expunitTypeFlag = "0030";

		EntityGroup slotsAvailGrp = m_elist.getEntityGroup("SLOTSAVAIL");
	  	addHeading(4,slotsAvailGrp.getLongDescription()+" Checks:");
	  	
        // WWSEO needs to match up the SLOTS with the SLOTSAVAIL by ELEMENTTYPE for  Planar, Memory, and Expansion Unit.
		for(int i = 0; i < slotsAvailGrp.getEntityItemCount(); i++)
		{
			EntityItem slotsavailItem = slotsAvailGrp.getEntityItem(i);
			String slotType = PokUtils.getAttributeValue(slotsavailItem, "SLOTTYPE",", ", "", false);
			String slotTypeFlag = getAttributeFlagEnabledValue(slotsavailItem, "SLOTTYPE");
			String slotSze = PokUtils.getAttributeValue(slotsavailItem, "SLOTSZE",", ", "", false);
			String slotSzeFlag = getAttributeFlagEnabledValue(slotsavailItem, "SLOTSZE");
			String elementType = PokUtils.getAttributeValue(slotsavailItem, "ELEMENTTYPE",", ", "", false);
			String elementTypeFlag = getAttributeFlagEnabledValue(slotsavailItem, "ELEMENTTYPE");
			addDebug("checkSlots: "+slotsavailItem.getKey()+" slotType: "+slotType+
				"["+slotTypeFlag+"] slotSze: "+slotSze+"["+slotSzeFlag+"] elementType: "+elementType);
			if (elementTypeFlag==null || elementTypeFlag.length()==0){
				addDebug("checkSlots: skipping "+slotsavailItem.getKey()+" slotType: "+slotType+" does not have ELEMENTTYPE defined.");
				continue;
			}

			//For all Slots Available (SLOTSAVAIL), the string ELEMENTTYPE & SLOTTYPE & SLOTSZE must be Unique.
			//{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must be Unique
			//MUST_HAVE_UNIQUE_ERR = {0} {1} {2} {3} must be Unique
			if (memCardTypeFlag.equals(elementTypeFlag)){
				if (slotsavailMemCardSet.containsKey(slotTypeFlag+slotSzeFlag)){
	 				args[0]=slotsAvailGrp.getLongDescription();
	 				args[1]=elementType;
	 				args[2]=getLD_Value(slotsavailItem, "SLOTTYPE");
	 				args[3]=getLD_Value(slotsavailItem, "SLOTSZE");
	 				createMessage(checklvl,"MUST_HAVE_UNIQUE_ERR",args);
				}else{
					slotsavailMemCardSet.put(slotTypeFlag+slotSzeFlag,slotsavailItem);
				}
			}else if(planarTypeFlag.equals(elementTypeFlag)){
				if (slotsavailPlanarSet.containsKey(slotTypeFlag+slotSzeFlag)){
	 				args[0]=slotsAvailGrp.getLongDescription();
	 				args[1]=elementType;
	 				args[2]=getLD_Value(slotsavailItem, "SLOTTYPE");
	 				args[3]=getLD_Value(slotsavailItem, "SLOTSZE");
	 				createMessage(checklvl,"MUST_HAVE_UNIQUE_ERR",args);
				}else{
					slotsavailPlanarSet.put(slotTypeFlag+slotSzeFlag,slotsavailItem);
				}
			}else if(expunitTypeFlag.equals(elementTypeFlag)){
				if (slotsavailExpUnitSet.containsKey(slotTypeFlag+slotSzeFlag)){
	 				args[0]=slotsAvailGrp.getLongDescription();
	 				args[1]=elementType;
	 				args[2]=getLD_Value(slotsavailItem, "SLOTTYPE");
	 				args[3]=getLD_Value(slotsavailItem, "SLOTSZE");
	 				createMessage(checklvl,"MUST_HAVE_UNIQUE_ERR",args);
				}else{
					slotsavailExpUnitSet.put(slotTypeFlag+slotSzeFlag,slotsavailItem);
				}
			}
		}

		addDebug("slotsavailMemCardSet "+slotsavailMemCardSet);
		addDebug("slotsavailPlanarSet "+slotsavailPlanarSet);
		addDebug("slotsavailExpUnitSet "+slotsavailExpUnitSet);

		//Each SLOT must be unique within its parent entity type. This is checked using ELEMENTTYPE & ELEMENTID & SLOTTYPE & SLOTSZE
		checkElementSlots(slotsavailMemCardSet,"MEMORYCARD","FEATUREMEMORYCARD","MEMORYCARDSLOT", checklvl);

		//Each SLOT must be unique within its parent entity type. This is checked using ELEMENTTYPE & ELEMENTID & SLOTTYPE & SLOTSZE
		checkElementSlots(slotsavailPlanarSet,"PLANAR","FEATUREPLANAR","PLANARSLOT", checklvl);	

		//Each SLOT must be unique within its parent entity type. This is checked using ELEMENTTYPE & ELEMENTID & SLOTTYPE & SLOTSZE
		checkElementSlots(slotsavailExpUnitSet,"EXPDUNIT","FEATUREEXPDUNIT","EXPDUNITSLOT", checklvl);

        slotsavailMemCardSet.clear();
        slotsavailMemCardSet = null;
        slotsavailPlanarSet.clear();
        slotsavailPlanarSet = null;
        slotsavailExpUnitSet.clear();
        slotsavailExpUnitSet = null;
    }
    /***********************************************
    *check element slots.
    * Each slot must have unique ELEMENTTYPE & ELEMENTID & slottype and slotsize
    * Each slot must have a corresponding slotsavail
    * Each slotsavail must have a corresponding slot
    */
    private void checkElementSlots(Hashtable slotsavailElementTbl,
    	String elemEntity, String elemRelator, String slotRelator, int checklvl)
    {
        Hashtable slotTbl = new Hashtable();
        HashSet uniqueSet  = new HashSet();

		Iterator itr;
		
		addDebug("checkElementSlots: entered "+elemEntity+" "+elemRelator+" "+slotRelator);

		EntityGroup slotGrp = m_elist.getEntityGroup("SLOT");
		EntityGroup parentGrp = m_elist.getEntityGroup("FEATURE");
		EntityGroup elementGrp = m_elist.getEntityGroup(elemEntity);
		EntityGroup slotsAvailGrp = m_elist.getEntityGroup("SLOTSAVAIL");
		Vector elementVector = PokUtils.getAllLinkedEntities(parentGrp, elemRelator, elemEntity);
		
		addHeading(4,parentGrp.getLongDescription()+" "+elementGrp.getLongDescription()+" "+
	  			slotGrp.getLongDescription()+" Checks:");
		
		for(int j = 0; j < elementVector.size(); j++) {
			EntityItem elementItem = (EntityItem)elementVector.get(j);
			int elementEntityId = elementItem.getEntityID();
			Vector slotVector = PokUtils.getAllLinkedEntities(elementItem, slotRelator, "SLOT");
			for(int i = 0; i < slotVector.size(); i++)
			{
				EntityItem slotItem = (EntityItem) slotVector.get(i);
				String slotType = PokUtils.getAttributeValue(slotItem, "SLOTTYPE",", ", "", false);
				String slotSze = PokUtils.getAttributeValue(slotItem, "SLOTSZE",", ", "", false);
				String slotTypeFlag = getAttributeFlagEnabledValue(slotItem, "SLOTTYPE");
				String slotSzeFlag = getAttributeFlagEnabledValue(slotItem, "SLOTSZE");
				addDebug("checkElementSlots: "+elemEntity + elementEntityId +" "+slotItem.getKey()+
					" slotType: "+slotType+"["+slotTypeFlag+"] slotSze: "+slotSze+"["+slotSzeFlag+"]");
				
				if (uniqueSet.contains(elementEntityId + slotTypeFlag+slotSzeFlag)){
					//UniqueSLOTwithin	SLOTTYPE & SLOTSIZE			W	E	E		
					//{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} {LD: SLOTSIZE} {SLOTSIZE} must be Unique
					//MUST_HAVE_UNIQUE_ERR = {0} {1} {2} {3} must be Unique
					args[0]=slotGrp.getLongDescription();
					args[1]=this.getLD_Value(slotItem, "SLOTTYPE");
					args[2]=this.getLD_Value(slotItem, "SLOTSZE");
	 				args[3]="";
					createMessage(checklvl,"MUST_HAVE_UNIQUE_ERR",args);
				}else{
					uniqueSet.add(elementEntityId + slotTypeFlag+slotSzeFlag);
					slotTbl.put(slotTypeFlag+slotSzeFlag,slotItem);
				}
			}
			slotVector.clear();
			slotVector = null;
		}
		uniqueSet.clear();

		//SLOTTYPE	IN	U: SLOTSAVAIL	SLOTTYPE		W	E	E		
		//{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
		itr = slotTbl.keySet().iterator();
		while(itr.hasNext())
		{
			String slotTypeKey = (String) itr.next();
			if(!slotsavailElementTbl.containsKey(slotTypeKey))
			{
				EntityItem slotItem = (EntityItem)slotTbl.get(slotTypeKey);
				// CORRESPONDING_ERR = {0} {1} {2} {3} must have matching {4}
 				//{LD:ELEMENT}{LD: SLOT} {LD: SLOTTYPE} {SLOTTYPE} must have matching {LD: SLOTSAVAIL}
				args[0]=elementGrp.getLongDescription();
 				args[1]=slotGrp.getLongDescription();
 				args[2]=getLD_Value(slotItem, "SLOTTYPE");
 				args[3]=getLD_Value(slotItem, "SLOTSZE");
 				args[4]=slotsAvailGrp.getLongDescription();
 				createMessage(checklvl,"CORRESPONDING_ERR",args);
			}
		}

		// {LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
		itr = slotsavailElementTbl.keySet().iterator();
		while(itr.hasNext())
		{
			String slotInfo = (String) itr.next();
			if(!slotTbl.containsKey(slotInfo))
			{
				EntityItem slotsavailItem = (EntityItem)slotsavailElementTbl.get(slotInfo);
				// CORRESPONDING_ERR = {0} {1} {2} {3} must have matching {4}
 				//{LD: SLOTSAVAIL} {ELEMENTTYPE} {LD: SLOTTYPE} {SLOTTYPE} must have a matching {LD: SLOT} {LD: SLOTTYPE}
				args[0]=slotsAvailGrp.getLongDescription();
 				args[1]=elementGrp.getLongDescription();
 				args[2]= getLD_Value(slotsavailItem, "SLOTTYPE");
 				args[3]= getLD_Value(slotsavailItem, "SLOTSZE");
 				args[4]=slotGrp.getLongDescription()+" "+
 					PokUtils.getAttributeDescription(slotGrp, "SLOTTYPE", "SLOTTYPE");
 				createMessage(checklvl,"CORRESPONDING_ERR",args);
			}
		}//end of while(itr.hasNext())

		slotTbl.clear();
		slotTbl = null;
		elementVector.clear();
		elementVector = null;
    }

}
