//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2009,2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import com.ibm.transform.oim.eacm.util.*;

import java.sql.SQLException;
import java.util.*;
/*****************
 *BH FS ABR Data Quality 20111020e.xls BH Defect 67890 (support for old data)
 *sets changes
 * from BH FS ABR Catalog Attr Derivation 20110221b.doc
 * need updated DQVESVCMOD
 * 
 * From "BH FS ABR Data Quality 20101220.doc"
 * DQVESVCMOD VE updates
 * need meta and actions for SVCMODMM and SVCMODFB
 * how to nav to SVCSEO from SVCMOD (not the ref one), cant find it now - trying to test SVCSEOAD.. this must have been there before?
 * 
 * 
 * From "BH FS ABR Data Quality 20100823.doc"
 * 
 * DQVESVCMOD VE updates for 
 * SVCMODCHRGCOMP-d
    SVCMODSVCSEO-d
 * 
 * XXX. SVCMOD

A.  Checking

A SVCMOD always has Availability Dates (AVAIL EFFECTIVEDATE) and may have an Announcement Date (ANNOUNCEMENT ANNDATE) 
which are checked against the planning dates. 

The dates on the IPSC Structure (IPSCSTRUCT) and its referenced Availability (AVAIL) are checked to ensure 
that they are consistent with the Service Model dates (either SVCMOD or AVAIL).

B.  STATUS changed to Ready for Review

1.  Set ADSABRSTATUS = 0020 (Queued)
C.  STATUS changed to Final

1.  Set ADSABRSTATUS = 0020 (Queued)
D.  Status = Final

1.  Set ADSABRSTATUS = 0020 (Queued)

 * 
 *SVCMODABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.SVCMODABRSTATUS
SVCMODABRSTATUS_enabled=true
SVCMODABRSTATUS_idler_class=A
SVCMODABRSTATUS_keepfile=true
SVCMODABRSTATUS_report_type=DGTYPE01
SVCMODABRSTATUS_vename=DQVESVCMOD
SVCMODABRSTATUS_CAT1=RPTCLASS.SVCMODABRSTATUS
SVCMODABRSTATUS_CAT2=
SVCMODABRSTATUS_CAT3=RPTSTATUS
SVCMODABRSTATUS_domains=0010,0020,0030,0040,0050,0150,0160,0170,0190,0200,0210,0220,0230,0240,0250,0260,0270,0280,0290,0300,0310,0320,0330,0340,0350,0360,0370,0520,530,540,SG,550,PDIV49,PDIV71,PPWRS,PRSSS,PSANMOHE,PSANMOLE,PSTGV,PSWIDLU,PSSDS,PSQS,550

 */
//SVCMODABRSTATUS.java,v
//Revision 1.12  2011/03/23 18:08:43  wendy
//Add CATDATA support
//
//Revision 1.11  2011/01/06 13:51:44  wendy
//BH FS ABR Data Quality 20110105 updates
//
//Revision 1.6  2010/01/21 22:13:40  wendy
//update cmts

//Revision 1.2  2010/01/11 19:31:56  wendy
//Add postprocess method to support trigger of workflow action when STATUS is updated

//Revision 1.1  2009/11/11 13:40:06  wendy
//SR 76 - OTC - Support pre-configured/configured/sw/services offerings

public class SVCMODABRSTATUS extends DQABRSTATUS {
    private Vector svcmodAvailVct;
    private Vector svcmodLOAvailVctE;
    private Vector svcmodPlaAvailVctA;
    private Vector svcmodFOAvailVctC;
    private Vector svcmodEOSAvailVctG;
    private Vector svcmodEOMAvailVctM;
    private Hashtable svcmdlPlaAvailCtryTblA = null;
    private Hashtable svcmdlFOAvailCtryTblC = null;
    private Hashtable svcmdlLOAvailCtryTblE = null;
    private Hashtable svcmdlEOSAvailCtryTblG = null;
    private Vector mdlPlaAnnVct = new Vector();

    private String SVCSEOFO = null;
    private String SVCSEOPA = null;
    private String SVCSEOAD = null;
    private String SVCMODFO = null;
    private String SVCMODPA = null;
    private String SVCMODAD = null;

    //OFERCONFIGTYPE    CNFIG   Configurable
    private static final String OFERCONFIGTYPE_Configurable = "CNFIG"; 

    /**********************************
     * must be in list of domains
     */
    protected boolean isVEneeded(String statusFlag) {
        return domainInList();
    }

    /*
     * 
174.00  SVCMOD      Root Entity                         
175.00 R0.5 SET         SVCMOD              ADSABRSTATUS    &ADSFEEDRFR &ADSFEED
176.00 R0.5 END 174 SVCMOD                          

175.02  R1.0    IF          SVCMOD  STATUS  =   "Ready for Review" (0040)           
175.04  R1.0    AND         SVCMOD  LIFECYCLE   =   "Develop" (LF02)  | "Plan" (LF01)           
175.06  R1.0    AND     SVCMODAVAIL-d: AVAILANNA    AVAIL   STATUS  =   "Final" (0020) | "Ready for Review" (0040)          
175.08  R1.0    IF          AVAIL   AVAILANNTYPE    =   "RFA" (RFA)         
175.10  R1.0    IF         ANNOUNCEMENT    STATUS  =   "Final" (0020) | "Ready for Review" (0040)          
175.12  R1.0    SET         SVCMOD              ADSABRSTATUS    &ADSFEEDRFR 
175.14  R1.0    ELSE    175.08                              
175.16  R1.0    SET         SVCMOD              ADSABRSTATUS    &ADSFEEDRFR 
175.17	R1.0	END	175.10
175.18  R1.0    END 175.08                              
175.20  R1.0    END 175.02                              
175.22  R1.0    IF          SVCMOD  STATUS  =   "Final" (0020)          
175.24  R1.0    AND     SVCMODAVAIL-d: AVAILANNA    AVAIL   STATUS  =   "Final" (0020)          
175.26  R1.0    IF          AVAIL   AVAILANNTYPE    =   "RFA" (RFA)         
175.28  R1.0    IF         ANNOUNCEMENT    STATUS  =   "Final" (0020)          
Delete 2011-10-20		175.300	R1.0	IF			ANNOUNCEMENT	ANNTYPE	=	"New" (19)			
Delete 2011-10-20		175.320	R1.0	SET			ANNOUNCEMENT				WWPRTABRSTATUS		&ABRWAITODS2 
Delete 2011-10-20		175.340	R1.0	END	175.300								
                           
175.36  R1.0    SET         SVCMOD              ADSABRSTATUS        &ADSFEED
136.13	R1.0	END	175.28
175.38  R1.0    ELSE    175.26                              
175.40  R1.0    SET         SVCMOD              ADSABRSTATUS        &ADSFEED
175.41  R1.0    END 175.26                              
175.42  R1.0    END 175.22                              
176.00      END 174.00  SVCMOD                          

     */
    /**********************************
     * complete abr processing after status moved to readyForReview; (status was chgreq)
     * B.   Status changed to Ready for Review
175.02  R1.0    IF          SVCMOD  STATUS  =   "Ready for Review" (0040)           
175.04  R1.0    AND         SVCMOD  LIFECYCLE   =   "Develop" (LF02)  | "Plan" (LF01)           
175.06  R1.0    AND     SVCMODAVAIL-d: AVAILANNA    AVAIL   STATUS  =   "Final" (0020) | "Ready for Review" (0040)          
175.08  R1.0    IF          AVAIL   AVAILANNTYPE    =   "RFA" (RFA)         
175.10  R1.0    IF         ANNOUNCEMENT    STATUS  =   "Final" (0020) | "Ready for Review" (0040)          
175.12  R1.0    SET         SVCMOD              ADSABRSTATUS    &ADSFEEDRFR 
175.14  R1.0    ELSE    175.08                              
175.16  R1.0    SET         SVCMOD              ADSABRSTATUS    &ADSFEEDRFR 
175.17	R1.0	END	175.10
175.18  R1.0    END 175.08                              
175.20  R1.0    END 175.02                              
Final processing..                              
176.00      END 174.00  SVCMOD      
     */
    protected void completeNowR4RProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        if(doR10processing()){
            doR4R_RFAProcessing("SVCMODAVAIL");
        }else{ // R0.5
            setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
        }
    }

    /**********************************
     * complete abr processing after status moved to final; (status was r4r)
     *C. STATUS changed to Final
RFR processing..                            
175.22  R1.0    IF          SVCMOD  STATUS  =   "Final" (0020)          
175.24  R1.0    AND     SVCMODAVAIL-d: AVAILANNA    AVAIL   STATUS  =   "Final" (0020)          
175.26  R1.0    IF          AVAIL   AVAILANNTYPE    =   "RFA" (RFA)         
175.28  R1.0    IF         ANNOUNCEMENT    STATUS  =   "Final" (0020)          
Delete 2011-10-20		175.300	R1.0	IF			ANNOUNCEMENT	ANNTYPE	=	"New" (19)			
Delete 2011-10-20		175.320	R1.0	SET			ANNOUNCEMENT				WWPRTABRSTATUS		&ABRWAITODS2 
Delete 2011-10-20		175.340	R1.0	END	175.300								
                            
175.36  R1.0    SET         SVCMOD              ADSABRSTATUS        &ADSFEED
136.13	R1.0	END	175.28
175.38  R1.0    ELSE    175.26                              
175.40  R1.0    SET         SVCMOD              ADSABRSTATUS        &ADSFEED
175.41  R1.0    END 175.26                              
175.42  R1.0    END 175.22                              
176.00      END 174.00  SVCMOD          
     */
    protected void completeNowFinalProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        if(doR10processing()){
            doFinal_RFAProcessing("SVCMODAVAIL");
        }else{ // R0.5
            setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
        }
    }
	/**
	 * used when entities are going to final and must check RFA
175.22	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
175.24	R1.0	AND		SVCMODAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020)			
175.26	R1.0	IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)			
175.28	R1.0	IF			ANNOUNCEMENT	STATUS	=	"Final" (0020)			
Delete 2011-10-20		175.300	R1.0	IF			ANNOUNCEMENT	ANNTYPE	=	"New" (19)			
Delete 2011-10-20		175.320	R1.0	SET			ANNOUNCEMENT				WWPRTABRSTATUS		&ABRWAITODS2 
Delete 2011-10-20		175.340	R1.0	END	175.300									
175.36	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
136.13	R1.0	END	175.28
175.38	R1.0	ELSE	175.26								
175.40	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
175.41	R1.0	END	175.26								
175.42	R1.0	END	175.22	
	 * @param availRel
	 * @throws SQLException 
	 * @throws MiddlewareException 
	 * @throws MiddlewareRequestException 
	 */
	private void doFinal_RFAProcessing(String availRel) throws MiddlewareRequestException, MiddlewareException, SQLException {
		EntityItem mdlItem= m_elist.getParentEntityGroup().getEntityItem(0);
		//Add	175.22		IF			SVCMOD	STATUS	=	"Final" (0020)					
		Vector availVct= PokUtils.getAllLinkedEntities(mdlItem, availRel, "AVAIL");

		availloop:for (int ai=0; ai<availVct.size(); ai++){
			EntityItem avail = (EntityItem)availVct.elementAt(ai);
			String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
			if (availAnntypeFlag==null){
				availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
			}

			addDebug("doFinal_RFAProcessing: "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
			//Add	175.24	AND		SVCMODAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020)		
			if (statusIsFinal(avail)){
				//Add	175.26		IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)		
//				if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//					Vector annVct= PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//					for (int i=0; i<annVct.size(); i++){
//						EntityItem annItem = (EntityItem)annVct.elementAt(i);
//						//String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
//						addDebug("doFinal_RFAProcessing: "+annItem.getKey());//+" type "+anntype);
//						// 175.28	IF			ANNOUNCEMENT	STATUS	=	"Final" (0020)			
//						if (statusIsFinal(annItem,"ANNSTATUS")){
//							/*if(ANNTYPE_NEW.equals(anntype)){
//								addDebug(annItem.getKey()+" is Final and New");
//								//Delete 2011-10-20	175.30	IF			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	
//								//Delete 2011-10-20	175.32		SET			ANNOUNCEMENT				WWPRTABRSTATUS		&ABRWAITODS2
//								setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", getQueuedValueForItem(annItem,"WWPRTABRSTATUS"),annItem);
//							}//Delete 2011-10-20	175.34		END	175.30
//							*/	
//
//							//175.36	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
//							setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(mdlItem,"ADSABRSTATUS"),mdlItem);
//							annVct.clear();
//							break availloop;
//						}	
//					}// end ann loop
//					annVct.clear();
//					//136.13	R1.0	END	175.28
//				}else{ // not RFA
					//175.38		ELSE	175.26								
					//175.40		SET			SVCMOD				ADSABRSTATUS		&ADSFEED
//				20121210 Delete		175.260		IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)					
//				20121210 Delete		175.280		IF			ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020)					
//				20130904b Change	Columns E, I, J, K	175.360		SetSinceFinal			SVCMOD	ADSABRSTATUS	=	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEED		
//						20130904b Add		175.370		SetSinceFinal			SVCMOD	ADSABRSTATUS	<>	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEEDFINALFIRST		
				setSinceFirstFinal(mdlItem, "ADSABRSTATUS");
//				20121210 Delete		136.130		END	175.280										
//				20121210 Delete		175.380		ELSE	175.260										
//				20121210 Delete		175.400		SET			SVCMOD				ADSABRSTATUS		&ADSFEED		
//				20121210 Delete		175.410		END	175.260	
//					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(mdlItem,"ADSABRSTATUS"),mdlItem);
					break availloop;
					//175.41		END	175.26	
//				} // end not RFA
			} // end avail.status=final
		}// end avail loop
		availVct.clear();
		//Add	175.42	END	175.22			
	}
    /**********************************
     * Note the ABR is only called when
     * DATAQUALITY transitions from 'Draft to Ready for Review',
     *  'Change Request to Ready for Review' and from 'Ready for Review to Final'
     * 

A.  Checking

checks from ss:
1.00    SVCMOD      Root                                
2.00            ANNDATE                             
3.00            WITHDRAWDATE    =>  SVCMOD  ANNDATE     E   E   E   {LD: WITHDRAWDATE} {WITHDRAWDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
4.00    AVAIL       SVCMODAVAIL-d                               
5.00    WHEN    A   AVAILTYPE   =   "Planned Availability"                      
6.00            Count of    =>  1           RW  RW  RE  must have at least one "Planned Availability"
7.00            EFFECTIVEDATE   =>  SVCMOD  ANNDATE     W   E   E   {LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: SVCMODEL} {LD: ANNDATE} {ANNDATE}
8.00            COUNTRYLIST                             
9.00    ANNOUNCEMENT    B   A: + AVAILANNA-d                                
10.00   WHEN        "RFA" (RFA) <>  A: AVAIL    AVAILANNTYPE                    
11.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
12.00   END 10.00                                   
13.00   IF      ANNTYPE <>  New (19)                        
14.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
15.00   ELSE        ANNDATE =>  SVCMOD  ANNDATE     W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} can not be earlier than the {LD: SVCMOD} {LD: ANNDATE} {ANNDATE}
16.00   END 13.00                                   
17.00   END 5.00    

18.00   AVAIL   C   SVCMODAVAIL-d                               
19.00   WHEN    C   AVAILTYPE   =   "First Order"                       
20.00           EFFECTIVEDATE   =>  SVCMOD  ANNDATE     W   W   E   {LD: AVAIL} {NDN: AVAIL} has a date earlier than the {LD: SVCMOD} {LD: ANNDATE}
21.00   ANNOUNCEMENT    D   C: + AVAILANNA-d                                
22.00   WHEN        "RFA" (RFA) <>  C: AVAIL    AVAILANNTYPE                    
23.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: C:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
24.00   END 22.00                                   
25.00   IF      ANNTYPE <>  New (19)                        
26.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: C:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
27.00   ELSE        ANNDATE =>  SVCMOD  ANNDATE     W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} can not be earlier than the {LD: SVCMOD} {LD: ANNDATE} {ANNDATE}
28.00   END 25.00                                   
29.00   END 19.00   

30.00   AVAIL   E   SVCMODAVAIL-d                               
31.00   WHEN        AVAILTYPE   =   "Last Order"                        
32.00           EFFECTIVEDATE   <=  SVCMOD  WTHDRWEFFCTVDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: SVCMOD} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
33.00           COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
delete34.00 IF      COUNTRYLIST Match   J: AVAIL    COUNTRYLIST                 
delete35.00 THEN        TheMatch    IN  K: AVAIL    COUNTRYLIST     W   RW  RE  {LD: IPSCFEAT} (NDN: K: IPSCFEAT} must have a "Last Order" for all countries in the {LD: SVCMOD} {LD: AVAIL} {E: AVAIL}
36.00   ANNOUNCEMENT    F   E: + AVAILANNA-d                                
37.00   WHEN        "RFA" (RFA) <>  E: AVAIL    AVAILANNTYPE                    
38.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: E:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
39.00   END 37.00                                   
40.00   IF      ANNTYPE <>  "End Of Life - Withdrawal from mktg" (14)                       
41.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: E:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
42.00   ELSE        ANNDATE <=  SVCMOD  WITHDRAWDATE        W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
43.00   END 40.00                                   
44.00   END 31.00   

45.00   AVAIL   M   SVCMODAVAIL-d                               
46.00   WHEN        AVAILTYPE   =   "End of Marketing" (200)                        
47.00           EFFECTIVEDATE   <=  SVCMOD  WTHDRWEFFCTVDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: SVCMOD} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
48.00           COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
49.00   ANNOUNCEMENT    N   M: + AVAILANNA-d                                
50.00   WHEN        "RFA" (RFA) <>  M: AVAIL    AVAILANNTYPE                    
51.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: M:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
52.00   END 50.00                                   
53.00   IF      ANNTYPE <>  "End Of Life - Withdrawal from mktg" (14)                       
54.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: M:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
55.00   ELSE        ANNDATE <=  SVCMOD  WITHDRAWDATE        W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
56.00   END 53.00                                   
57.00   END 46.00   

For R1.0                                
Remove  58  AVAIL   G   SVCMODAVAIL-d                               
Remove  59  WHEN        AVAILTYPE   =   "End of Service" (151)                      
Remove  60          EFFECTIVEDATE   <=  SVCMOD  WTHDRWEFFCTVDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: SVCMOD} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
Remove  61          COUNTRYLIST IN  E:AVAIL COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
Remove  62  IF      COUNTRYLIST Match   J: AVAIL    COUNTRYLIST                 
Remove  63  THEN        TheMatch    IN  L: AVAIL    COUNTRYLIST     W   RW  RE  {LD: IPSCFEAT} (NDN: L: IPSCFEAT} must have a "End of Service" for all countries in the {LD: SVCMOD} {LD: AVAIL} {E: AVAIL}
Remove  64  WHEN        AVAILANNTYPE    <>  "RFA" (RFA)                     
Remove  65  ANNOUNCEMENT    H   G: + AVAILANNA-d                                
Remove  66  WHEN        "RFA" (RFA) <>  G: AVAIL    AVAILANNTYPE                    
Remove  67          Count of    =   0           E   E   E   {LD: AVAIL} {NDN: G:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
Remove  68  END 66                                  
Remove  69  IF      ANNTYPE <>  "End Of Life - Discontinuance of service" (13)                      
Remove  70          Count of    =   0           E   E   E   {LD: AVAIL} {NDN: G:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
Remove  71  ELSE        ANNDATE <=  SVCMOD  WITHDRAWDATE        W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
Remove  72  END 69                                  
Remove  73  END 59  

delete74.00 AVAIL   J   IPSCSTRUCT-d: IPSCSTRUCAVAIL-d                              
delete75.00 WHEN        AVAILTYPE   =   "Planned Availability"                      
delete76.00         ANNCODENAME                             
delete77.00         COUNTRYLIST "IN aggregate G"    A: AVAIL    COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
delete78.00         EFFECTIVEDATE   =>  A: AVAIL    EFFECTIVEDATE   Yes W   W   E   {LD: IPSCSTRUCT} {NDN: IPSCSTRUCT} {LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD:AVAIL} {NDN: A: AVAIL}
delete79.00 END 75.00                                   
delete80.00 AVAIL       IPSCSTRUCT-d: IPSCSTRUCAVAIL-d                              
delete81.00 WHEN        AVAILTYPE   =   "First Order"                       
delete82.00         ANNCODENAME                             
delete83.00         COUNTRYLIST "IN aggregate G"    J: AVAIL    COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
delete84.00 END 81.00                                   
delete85.00 AVAIL   K   IPSCSTRUCT-d: IPSCSTRUCAVAIL-d                              
delete86.00 WHEN        AVAILTYPE   =   "Last Order"                        
delete87.00         ANNCODENAME                             
delete88.00         COUNTRYLIST IN  J:  AVAIL   COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
delete89.00         EFFECTIVEDATE   <=  E:  AVAIL   EFFECTIVEDATE   Yes W   W   E   {LD: IPSCSTRUCT} {NDN: IPSCSTRUCT} {LD: AVAIL} {NDN: AVAIL} can not be later than the {LD:AVAIL} {NDN: E: AVAIL}
delete90.00 END 86.00                                   
delete91.00 AVAIL   P   IPSCSTRUCT-d: IPSCSTRUCAVAIL-d                              
delete92.00 WHEN        AVAILTYPE   =   "End of Marketing" (200)                        
delete93.00         ANNCODENAME                             
delete94.00         COUNTRYLIST "IN aggregate G"    J:  AVAIL   COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
delete95.00         EFFECTIVEDATE   <=  E:  AVAIL   EFFECTIVEDATE   Yes W   W   E   {LD: IPSCSTRUCT} {NDN: IPSCSTRUCT} {LD: AVAIL} {NDN: AVAIL} can not be later than the {LD:AVAIL} {NDN: E: AVAIL}
delete96.00 END 92.00   
delete97.00 AVAIL   L   IPSCSTRUCT-d: IPSCSTRUCAVAIL-d                              
delete98.00 WHEN        AVAILTYPE   =   "End of Service" (151)                      
delete99.00         ANNCODENAME                             
delete100.00            COUNTRYLIST "IN aggregate G"    J:  AVAIL   COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
delete101.00            EFFECTIVEDATE   <=  E:  AVAIL   EFFECTIVEDATE   Yes W   W   E   {LD: IPSCSTRUCT} {NDN: IPSCSTRUCT} {LD: AVAIL} {NDN: AVAIL} can not be later than the {LD:AVAIL} {NDN: E: AVAIL}
delete102.00    END 98.00   

Replaced    103.20  IF      OFERCONFIGTYPE  =   "Configurable" (CNFIG)                          
Replaced    103.40          SVCMODCHRGCOMP-d        CHRGCOMP                            
Replaced    103.60      Q   CHRGCOMPPRCPT-d     PRCPT                           
Replaced    103.80          DATAQUALITY <=  CHRGCOMP    STATUS      E   E   E   If SVCMOD has a CHRGCOMP, check its STATUS  {LD: STATUS} can not be higher than the {LD: CHRGCOMP} {NDN: CHRGCOMP} {LD: STATUS} {STATUS}
Replaced    104.00          COUNT   =>  0           E   E   E   "Configurable must have 0 - N CHRGCOMP
this used to be 1 - N but they changed it, hence I want to leave it in case they change their mind" {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must have 0 - N LD(CHRGCOMP}
Replaced    104.20      S   SVCMODAVAIL-d       AVAIL                           
Replaced    104.40  WHEN    R   AVAILTYPE   =   "Planned Availability" (146)                        SVCMOD AVAIL "Planned Availability" 
Replaced    104.60          "Q:  +PRCPTCNTRYEFF-dCOUNTRYLIST"   "IN aggregate G"    R: AVAIL    COUNTRYLIST     E   E   E   PRCPT CNTRYEFF COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST {LD: PRCPT} {NDN: Q:PRCPT} {LD: CNTRYEFF} {NDN: CNTRYEFF} {LD: COUNTRYLIST} must not include a country that is not in the {LD: SVCMOD} {LD: AVAIL}
Replaced    104.80          SVCMODPA    SetTo   R: AVAIL    MIN(EFFECTIVEDATE)                      
Replaced    105.00      T   "S: +AVAILANNA-d"       ANNOUNCEMENT                            
Replaced    105.20  IF      T: ANNTYPE  =   New (19)                            
Replaced    105.40          SVCMODAD    SetTo   T: ANNOUNCEMENT MIN(ANNDATE)                        
Replaced    105.60  END 105.20                                      
Replaced    105.80  END 104.40                                      
Replaced    106.00  WHEN        "First Order"   =   S: AVAIL    AVAILTYPE                       
Replaced    106.20          SVCMODFO    SetTo   S:AVAIL MIN(EFFECTIVEDATE)                      
Replaced    106.40  END 106.00                                      
Replaced    106.60      W   "SVCMODSVCSEO-dSVCSEOAVAIL-d"       AVAIL                           
Replaced    106.80  WHEN    X   AVAILTYPE   =   "Planned Availability" (146)                            
Replaced    107.00          SVCSEOPA    SetTo   X: AVAIL    MIN(EFFECTIVEDATE)                      
Replaced    107.20      Y   "W: +AVAILANNA-d"       ANNOUNCEMENT                            
Replaced    107.40  IF      Y: ANNTYPE  =   New (19)                            
Replaced    107.60          SVCSEOAD    SetTo   T: ANNOUNCEMENT MIN(ANNDATE)                        
Replaced    107.80  END 107.40                                      
Replaced    108.00  END 106.80                                      
Replaced    108.20  WHEN        "First Order"   =   W: AVAIL    AVAILTYPE                       
Replaced    108.40          SVCSEOFO    SetTo   W:AVAIL MIN(EFFECTIVEDATE)                      
Replaced    108.60  END 108.20                                      
Replaced    108.80          SVCSEOFIRSTORDER    FirstValue      SVCSEOFO                    Derives SVCSEO First Order  
Replaced    109.00                      SVCSEOAD                        
Replaced    109.20                      SVCSEOPA                        
Replaced    109.40          SVCMODFIRSTORDER    FirstValue      SVCMODFO                    Derves SVCMOD First Order   
Replaced    109.60                      SVCMODAD                        
Replaced    109.80                      SVCMODPA                        
109.90	IF		NotNull(SVCSEOFIRSTORDER)									
109.92	AND		NotNull(SVCMODFIRSTORDER)									
110.00			SVCMODFIRSTORDER	<=	SVCSEOFIRSTORDER			RE	RE	RE	First Order SVCMOD can not be later than SVCSEO First Order	{LD: SVCMOD} First Order date must not be later than the {LD: SVCSEO} {NDN: SVCSEO)
110.20	END	109.92										

113.00  ELSE    104.00  SVCMODSVCSEO-d      SVCSEO                      Owned child SVCSEO  
114.00          COUNT   =   0           E   E   E   If not Configurable, must not have a SVCSEO {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must not have a LD(SVCSEO)
115.00          SVCMODSVCSEOREF-d       SVCSEO                      Shared SVCSEO   
116.00          COUNT   =   0           E   E   E   If not Configurable, must not have a SVCSEO {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must not have a LD(SVCSEO)
117.00          SVCMODCHRGCOMP-d        CHRGCOMP                            
118.00          COUNT   =   0           E   E   E   If not Configurable, must not have a CHRGCOMP   {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must not have a LD(CHRGCOMP)
119.00  END 104.00                                      
120.00  SVCMOD      Root                                    
121.00  IF      OFERCONFIGTYPE  =   "Configurable" (CNFIG)                          
122.00      S   SVCMODAVAIL-d       AVAIL                       AVAIL for Root  
123.00  WHEN        AVAILTYPE   =   "Planned Availability" (146)                        Planned AVAIL for Root  
124.00      T   SVCMODSVCMOD-d      SVCMOD                      Referenced SVCMOD   
125.00      U   SVCMODAVAIL-d       AVAIL                       AVAIL for Referenced SVCMOD 
126.00  WHEN        AVAILTYPE   =   "Planned Availability" (146)                        Planned AVAIL for Referenced SVCMOD 
127.00          S: COUNTRYLIST  Intersection is not null    T: AVAIL    COUNTRYLIST     E   E   E   One or more countries of SVCMOD AVAIL must be in the referenced SVCMODAVAIL. i.e. the intersection of the two country lists may not be null {LD: AVAIL} {NDN: S:AVAIL} {LD: COUNTRYLIST} must have a least one country in common with {LD: SVCMOD} {NDN: T:SVCMOD} {LD: AVAIL} {LD: COUNTRYLIST}
128.00  END 126.00                                      
129.00  END 123.00                                      
130.00  END 121.00                                      
     */
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
        addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");

        int checklvl = getCheck_W_E_E(statusFlag); 

        //1.00  SVCMOD      Root                                
        //2.00          ANNDATE                             
        //3.00      WITHDRAWDATE    =>  SVCMOD  ANNDATE     E   E   E   {LD: WITHDRAWDATE} {WITHDRAWDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
        checkCanNotBeEarlier(rootEntity, "WITHDRAWDATE", "ANNDATE", CHECKLEVEL_E);

        int cnt;
		//3.10	TAXCATG		SVCMODTAXRELEVANCE-d
		cnt = getCount("SVCMODTAXRELEVANCE");
		if(cnt == 0)    {
			//3.12			CountOf	=>	1			W	W	E		must have at least one "Tax Category"
			//MINIMUM_ERR = must have at least one {0}
			args[0] = m_elist.getEntityGroup("TAXCATG").getLongDescription();
			createMessage(getCheck_W_W_E(statusFlag),"MINIMUM_ERR",args);
		}
		
		//3.40	TAXGRP		SVCMODTAXGRP-d
		cnt = getCount("SVCMODTAXGRP");
		if(cnt == 0)    {
			//3.42			CountOf	=>	1			W	W	E		must have at least one "Tax Group"
			//MINIMUM_ERR = must have at least one {0}
			args[0] = m_elist.getEntityGroup("TAXGRP").getLongDescription();
			createMessage(getCheck_W_W_E(statusFlag),"MINIMUM_ERR",args);
		}
        
        getAvails(rootEntity,statusFlag);

        checkAvails(rootEntity, statusFlag,checklvl);

        checkConfig(rootEntity);
    }
    /**
103.00  SVCMOD      Root                                    
103.20  IF      OFERCONFIGTYPE  =   "Configurable" (CNFIG)                          
103.40          SVCMODCHRGCOMP-d        CHRGCOMP                            
103.60      Q   CHRGCOMPPRCPT-d     PRCPT                           
103.80          DATAQUALITY <=  CHRGCOMP    STATUS      E   E   E   If SVCMOD has a CHRGCOMP, check its STATUS  {LD: STATUS} can not be higher than the {LD: CHRGCOMP} {NDN: CHRGCOMP} {LD: STATUS} {STATUS}
104.00          COUNT   =>  0           E   E   E   "Configurable must have 0 - N CHRGCOMP this used to be 1 - N but they changed it, hence I want to leave it in case they change their mind"  {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must have 0 - N LD(CHRGCOMP}
104.20      S   SVCMODAVAIL-d       AVAIL                           
104.40  WHEN    R   AVAILTYPE   =   "Planned Availability" (146)                        SVCMOD AVAIL "Planned Availability" 
104.60          "Q:  +PRCPTCNTRYEFF-d COUNTRYLIST"  "INaggregate G" R: AVAIL    COUNTRYLIST     E   E   E   PRCPT CNTRYEFF COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST {LD: PRCPT} {NDN: Q:PRCPT} {LD: CNTRYEFF} {NDN: CNTRYEFF} {LD: COUNTRYLIST} must not include a country that is not in the {LD: SVCMOD} {LD: AVAIL}
104.80          SVCMODPA    SetTo   R: AVAIL    MIN(EFFECTIVEDATE)                      
105.00      T   "S: +AVAILANNA-d"       ANNOUNCEMENT                            
105.20  IF      T: ANNTYPE  =   New (19)                            
105.40          SVCMODAD    SetTo   T: ANNOUNCEMENT MIN(ANNDATE)                        
105.60  END 105.20                                      
105.80  END 104.40                                      
106.00  WHEN        "First Order"   =   S: AVAIL    AVAILTYPE                       
106.20          SVCMODFO    SetTo   S:AVAIL MIN(EFFECTIVEDATE)                      
106.40  END 106.00                                      
106.60      W   "SVCMODSVCSEO-d SVCSEOAVAIL-d"      AVAIL                           
106.80  WHEN    X   AVAILTYPE   =   "Planned Availability" (146)                            
107.00          SVCSEOPA    SetTo   X: AVAIL    MIN(EFFECTIVEDATE)                      
107.20      Y   "W: +AVAILANNA-d"       ANNOUNCEMENT                        
107.40  IF      Y: ANNTYPE  =   New (19)                            
107.60          SVCSEOAD    SetTo   T: ANNOUNCEMENT MIN(ANNDATE)                        
107.80  END 107.40                                      
108.00  END 106.80                                      
108.20  WHEN        "First Order"   =   W: AVAIL    AVAILTYPE                       
108.40          SVCSEOFO    SetTo   W:AVAIL MIN(EFFECTIVEDATE)                      
108.60  END 108.20                                      
108.80          SVCSEOFIRSTORDER    FirstValue      SVCSEOFO                    Derives SVCSEO First Order  
109.00                      SVCSEOAD                        
109.20                      SVCSEOPA                        
109.40          SVCMODFIRSTORDER    FirstValue      SVCMODFO                    Derves SVCMOD First Order   
109.60                      SVCMODAD                        
109.80                      SVCMODPA                        
109.90	IF		NotNull(SVCSEOFIRSTORDER)									
109.92	AND		NotNull(SVCMODFIRSTORDER)									
110.00			SVCMODFIRSTORDER	<=	SVCSEOFIRSTORDER			RE	RE	RE	First Order SVCMOD can not be later than SVCSEO First Order	{LD: SVCMOD} First Order date must not be later than the {LD: SVCSEO} {NDN: SVCSEO)
110.20	END	109.92										

113.00  ELSE    103.20  SVCMODSVCSEO-d      SVCSEO                      Owned child SVCSEO  

114.00          COUNT   =   0           E   E   E   If not Configurable, must not have a SVCSEO {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must not have a LD(SVCSEO)
115.00          SVCMODSVCSEOREF-d       SVCSEO                      Shared SVCSEO   
116.00          COUNT   =   0           E   E   E   If not Configurable, must not have a SVCSEO {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must not have a LD(SVCSEO)
117.00          SVCMODCHRGCOMP-d        CHRGCOMP                            
118.00          COUNT   =   0           E   E   E   If not Configurable, must not have a CHRGCOMP   {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must not have a LD(CHRGCOMP)
119.00  END 103.20                                      
120.00  SVCMOD      Root                                    
121.00  IF      OFERCONFIGTYPE  =   "Configurable" (CNFIG)                          
122.00      S   SVCMODAVAIL-d       AVAIL                       AVAIL for Root  
123.00  WHEN        AVAILTYPE   =   "Planned Availability" (146)                        Planned AVAIL for Root  
124.00      T   SVCMODSVCMOD-d      SVCMOD                      Referenced SVCMOD   
125.00      U   SVCMODAVAIL-d       AVAIL                       AVAIL for Referenced SVCMOD 
126.00  WHEN        AVAILTYPE   =   "Planned Availability" (146)                        Planned AVAIL for Referenced SVCMOD 
127.00          S: COUNTRYLIST  Intersection is not null    T: AVAIL    COUNTRYLIST     E   E   E   One or more countries of SVCMOD AVAIL must be in the referenced SVCMODAVAIL. i.e. the intersection of the two country lists may not be null {LD: AVAIL} {NDN: S:AVAIL} {LD: COUNTRYLIST} must have a least one country in common with {LD: SVCMOD} {NDN: T:SVCMOD} {LD: AVAIL} {LD: COUNTRYLIST}
128.00  END 126.00                                      
129.00  END 123.00                                      
130.00  END 121.00                                                                                  
     * @param rootEntity
     * @throws MiddlewareException
     * @throws SQLException
     */
    private void checkConfig(EntityItem rootEntity) throws MiddlewareException, SQLException
    {
        String oferconfigtype= PokUtils.getAttributeFlagValue(rootEntity, "OFERCONFIGTYPE");
        addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" "+
                PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "OFERCONFIGTYPE", "OFERCONFIGTYPE")+" checks:");

        int cnt = getCount("SVCMODCHRGCOMP");
        EntityGroup confGrp = m_elist.getEntityGroup("CHRGCOMP");
        addDebug("checkConfig "+rootEntity.getKey()+" oferconfigtype "+oferconfigtype);
        //103.20    IF      OFERCONFIGTYPE  =   "Configurable" (CNFIG)                          
        if(OFERCONFIGTYPE_Configurable.equals(oferconfigtype)){                     
            //103.40            SVCMODCHRGCOMP-d        CHRGCOMP                            
            //103.60        Q   CHRGCOMPPRCPT-d     PRCPT                           
            //104.00            COUNT   =>  0           E   E   E   "Configurable must have 0 - N CHRGCOMP
            //this used to be 1 - N but they changed it, hence I want to leave it in case they change their mind"   {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must have 0 - N LD(CHRGCOMP}
            /*if(cnt==0){
                //MINIMUM2_ERR = {0} must have at least one {1}
                args[0] = getLD_Value(rootEntity, "OFERCONFIGTYPE");
                args[1] = confGrp.getLongDescription();
                createMessage(CHECKLEVEL_E,"MINIMUM2_ERR",args);
            }*/
            for (int i=0; i<confGrp.getEntityItemCount(); i++){
                EntityItem confitem = confGrp.getEntityItem(i);
                //103.80            DATAQUALITY <=  CHRGCOMP    STATUS      E   E   E   If SVCMOD has a CHRGCOMP, check its STATUS  {LD: STATUS} can not be higher than the {LD: CHRGCOMP} {NDN: CHRGCOMP} {LD: STATUS} {STATUS}
                checkStatusVsDQ(confitem, "STATUS", rootEntity,CHECKLEVEL_E);
            }

            addHeading(3,m_elist.getEntityGroup("PRCPT").getLongDescription()+" checks:");

            //103.60        Q   CHRGCOMPPRCPT-d     PRCPT   
            Vector prcptVctQ = PokUtils.getAllLinkedEntities(confGrp, "CHRGCOMPPRCPT", "PRCPT");
            addDebug("checkConfig prcptVctQ "+prcptVctQ.size());

            //104.20        S   SVCMODAVAIL-d       AVAIL                           
            //104.40    WHEN    R   AVAILTYPE   =   "Planned Availability" (146)    SVCMOD AVAIL "Planned Availability" 
            //104.60            "Q:  +  PRCPTCNTRYEFF-d     COUNTRYLIST"    "IN aggregate G"    R: AVAIL    COUNTRYLIST     E   E   E   
            //PRCPT CNTRYEFF COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST   
            //{LD: PRCPT} {NDN: Q:PRCPT} {LD: CNTRYEFF} {NDN: CNTRYEFF} {LD: COUNTRYLIST} must not include a country that is not in the {LD: SVCMOD} {LD: AVAIL}
            //104.80            SVCMODPA    SetTo   R: AVAIL    MIN(EFFECTIVEDATE)                      
            //105.00        T   "S: +   AVAILANNA-d"        ANNOUNCEMENT                            
            //105.20    IF      T: ANNTYPE  =   New (19)                            
            //105.40            SVCMODAD    SetTo   T: ANNOUNCEMENT MIN(ANNDATE)                        
            //105.60    END 105.20  
            //105.80    END 104.40  

            //106.00    WHEN        "First Order"   =   S: AVAIL    AVAILTYPE                       
            //106.20            SVCMODFO    SetTo   S:AVAIL MIN(EFFECTIVEDATE)                      
            //106.40    END 106.00      
            checkSVCMODAvails(rootEntity);

            //106.60        W   "SVCMODSVCSEO-d SVCSEOAVAIL-d"      AVAIL                           
            //106.80    WHEN    X   AVAILTYPE   =   "Planned Availability" (146)                            
            //107.00            SVCSEOPA    SetTo   X: AVAIL    MIN(EFFECTIVEDATE)                      
            //107.20        Y   "W: +AVAILANNA-d"       ANNOUNCEMENT                        
            //107.40    IF      Y: ANNTYPE  =   New (19)                            
            //107.60            SVCSEOAD    SetTo   T: ANNOUNCEMENT MIN(ANNDATE)                        
            //107.80    END 107.40                                      
            //108.00    END 106.80                                      
            //108.20    WHEN        "First Order"   =   W: AVAIL    AVAILTYPE                       
            //108.40            SVCSEOFO    SetTo   W:AVAIL MIN(EFFECTIVEDATE)                      
            //108.60    END 108.20                                      
            checkSVCSEOAvails(rootEntity);

            //108.80            SVCSEOFIRSTORDER    FirstValue      SVCSEOFO                    Derives SVCSEO First Order  
            //109.00                        SVCSEOAD                        
            //109.20                        SVCSEOPA                        
            //109.40            SVCMODFIRSTORDER    FirstValue      SVCMODFO                    Derves SVCMOD First Order   
            //109.60                        SVCMODAD                        
            //109.80                        SVCMODPA                        
            //109.90	IF		NotNull(SVCSEOFIRSTORDER)									
            //109.92	AND		NotNull(SVCMODFIRSTORDER)									
            //110.00			SVCMODFIRSTORDER	<=	SVCSEOFIRSTORDER			RE	RE	RE	First Order SVCMOD can not be later than SVCSEO First Order	{LD: SVCMOD} First Order date must not be later than the {LD: SVCSEO} {NDN: SVCSEO)
            checkFODates(rootEntity);
            //110.20	END	109.92										

            //104.40    WHEN    R   AVAILTYPE   =   "Planned Availability" (146)    same as A:AVAIL SVCMOD AVAIL "Planned Availability" 
            //104.60            "Q:  +  PRCPTCNTRYEFF-d     COUNTRYLIST"    "IN aggregate G"    R: AVAIL    COUNTRYLIST     E   E   E   
            //PRCPT CNTRYEFF COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST   
            //{LD: PRCPT} {NDN: Q:PRCPT} {LD: CNTRYEFF} {NDN: CNTRYEFF} {LD: COUNTRYLIST} must not include a country that is not in the {LD: SVCMOD} {LD: AVAIL}
            for(int i=0; i<prcptVctQ.size(); i++){
                EntityItem prcptitem = (EntityItem)prcptVctQ.elementAt(i);
                String missingCtry = checkCtryMismatch(prcptitem, svcmdlPlaAvailCtryTblA, CHECKLEVEL_E);
                if (missingCtry.length()>0){
                    addDebug(prcptitem.getKey()+" COUNTRYLIST had extra ["+missingCtry+"]");
                    //111.00            Q: COUNTRYLIST  "IN aggregate G"    R: AVAIL    COUNTRYLIST     E   E   E   PRCPT COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST  
                    //{LD: PRCPT} {NDN: Q:PRCPT} {LD: COUNTRYLIST} must not include a country that is not in the {LD: SVCMOD} {LD: AVAIL}   
                    //INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
                    args[0]=getLD_NDN(prcptitem);
                    args[1]=PokUtils.getAttributeDescription(m_elist.getEntityGroup("AVAIL"), "COUNTRYLIST", "COUNTRYLIST");
                    args[2]=m_elist.getEntityGroup("SVCMOD").getLongDescription()+" "+
                    	m_elist.getEntityGroup("AVAIL").getLongDescription();
                    args[3]=PokUtils.getAttributeDescription(m_elist.getEntityGroup("AVAIL"), "COUNTRYLIST", "COUNTRYLIST");
                    args[4]=missingCtry;
                    createMessage(CHECKLEVEL_E,"INCLUDE_ERR2",args);
                }
            }
            //112.00    END 110.00  


            addHeading(3,"Referenced "+rootEntity.getEntityGroup().getLongDescription()+" checks:");
            //120.00    SVCMOD      Root                                    
            //121.00    IF      OFERCONFIGTYPE  =   "Configurable" (CNFIG)                          
            //122.00        S   SVCMODAVAIL-d       AVAIL                       AVAIL for Root  
            //123.00    WHEN        AVAILTYPE   =   "Planned Availability" (146)                        Planned AVAIL for Root  
            //124.00        T   SVCMODSVCMOD-d      SVCMOD                      Referenced SVCMOD   
            //125.00        U   SVCMODAVAIL-d       AVAIL                       AVAIL for Referenced SVCMOD 
            Vector svcmodVctT = PokUtils.getAllLinkedEntities(rootEntity, "SVCMODSVCMOD", "SVCMOD");
            Vector availVctU = PokUtils.getAllLinkedEntities(svcmodVctT, "SVCMODAVAIL", "AVAIL");
            Vector plaAvailVctU = PokUtils.getEntitiesWithMatchedAttr(availVctU, "AVAILTYPE", PLANNEDAVAIL);
            addDebug("svcmodVctT "+svcmodVctT.size()+" availVctU "+availVctU.size()+" plaAvailVctU "+plaAvailVctU.size()); 
            if(svcmdlPlaAvailCtryTblA.size()>0 && // there are root svcmod planned avails with countries
                    plaAvailVctU.size()>0){ // there are referenced svcmod planned avails 
                boolean intersects = false;
                //126.00    WHEN        AVAILTYPE   =   "Planned Availability" (146)                        Planned AVAIL for Referenced SVCMOD 
                availloop:for (int i=0; i<plaAvailVctU.size(); i++){
                    EntityItem plaitem = (EntityItem)plaAvailVctU.elementAt(i);
                    addDebug("referenced svcmod plannedavail "+plaitem.getKey()+" COUNTRYLIST ["+PokUtils.getAttributeFlagValue(plaitem, "COUNTRYLIST")+"]");
                    EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(plaitem, "COUNTRYLIST", CHECKLEVEL_E);
                    if (ctrylist != null && ctrylist.toString().length()>0) {
                        // Get the selected Flag codes.
                        MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
                        for (int im = 0; im < mfArray.length; im++) {                       
                            if (mfArray[im].isSelected()) {
                                if(svcmdlPlaAvailCtryTblA.keySet().contains(mfArray[im].getFlagCode())) {
                                    intersects = true;
                                    break availloop;
                                }
                            }
                        } //end for
                    }
                }
                if (!intersects){
                    for (int i=0; i<svcmodPlaAvailVctA.size(); i++){
                        EntityItem plaitem = (EntityItem)svcmodPlaAvailVctA.elementAt(i);
                        //127.00            S: COUNTRYLIST  Intersection is not null    T: AVAIL    COUNTRYLIST     E   E   E   One or more countries of SVCMOD AVAIL must be in the referenced SVCMODAVAIL. i.e. the intersection of the two country lists may not be null 
                        //{LD: AVAIL} {NDN: S:AVAIL} {LD: COUNTRYLIST} must have a least one country in common with {LD: SVCMOD} {NDN: T:SVCMOD} {LD: AVAIL} {LD: COUNTRYLIST}
                        addDebug(" nointersection root plannedavail "+plaitem.getKey());
                        for (int x=0; x<svcmodVctT.size(); x++){
                            EntityItem svcmoditem = (EntityItem)svcmodVctT.elementAt(x);
                            addDebug("nointersection referenced "+svcmoditem.getKey());
                            //INTERSECT_ERR = {0} {1} must have a least one country in common with {2} {3} {4}
                            args[0]=getLD_NDN(plaitem);
                            args[1]=PokUtils.getAttributeDescription(plaitem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
                            args[2]=getLD_NDN(svcmoditem);
                            args[3] = plaitem.getEntityGroup().getLongDescription();
                            args[4]=PokUtils.getAttributeDescription(plaitem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
                            createMessage(CHECKLEVEL_E,"INTERSECT_ERR",args);
                        }
                    }
                } // end no intersection
                //128.00    END 126.00  
            }// end there are root svcmod planned avails and refd svcmod plaavail

            //129.00    END 123.00                                      
            //130.00    END 121.00  
        }else{
            //117.00            SVCMODCHRGCOMP-d        CHRGCOMP                            
            //118.00            COUNT   =   0           E   E   E   If not Configurable, must not have a CHRGCOMP   {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must not have a LD(CHRGCOMP)
            if(cnt!=0){
                //MUST_NOT_HAVE2_ERR= {0} must not have any {1}
                args[0] = getLD_Value(rootEntity, "OFERCONFIGTYPE");
                args[1] = confGrp.getLongDescription();
                createMessage(CHECKLEVEL_E,"MUST_NOT_HAVE2_ERR",args);
            }
            //113.00    ELSE    103.20  SVCMODSVCSEO-d      SVCSEO                      Owned child SVCSEO  
            //114.00            COUNT   =   0           E   E   E   If not Configurable, must not have a SVCSEO {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must not have a LD(SVCSEO)
            cnt = getCount("SVCMODSVCSEO");
            if(cnt!=0){
                //MUST_NOT_HAVE2_ERR= {0} must not have any {1}
                args[0] = getLD_Value(rootEntity, "OFERCONFIGTYPE");
                args[1] = m_elist.getEntityGroup("SVCSEO").getLongDescription();
                createMessage(CHECKLEVEL_E,"MUST_NOT_HAVE2_ERR",args);
            }
            //115.00            SVCMODSVCSEOREF-d       SVCSEO                      Shared SVCSEO   
            //116.00            COUNT   =   0           E   E   E   If not Configurable, must not have a SVCSEO {LD: OFERCONFIGTYPE} {OFERCONFIGTYPE} must not have a LD(SVCSEO)
            cnt = getCount("SVCMODSVCSEOREF");
            if(cnt!=0){
                //MUST_NOT_HAVE2_ERR= {0} must not have any {1}
                args[0] = getLD_Value(rootEntity, "OFERCONFIGTYPE");
                args[1] = m_elist.getEntityGroup("SVCMODSVCSEOREF").getLongDescription()+" "+m_elist.getEntityGroup("SVCSEO").getLongDescription();
                createMessage(CHECKLEVEL_E,"MUST_NOT_HAVE2_ERR",args);
            }
        }
        //119.00    END 103.20  
    }

    /**
     * 
        108.80          SVCSEOFIRSTORDER    FirstValue      SVCSEOFO                    Derives SVCSEO First Order  
        109.00                      SVCSEOAD                        
        109.20                      SVCSEOPA                        
        109.40          SVCMODFIRSTORDER    FirstValue      SVCMODFO                    Derves SVCMOD First Order   
        109.60                      SVCMODAD                        
        109.80                      SVCMODPA                        
        109.90	IF		NotNull(SVCSEOFIRSTORDER)									
109.92	AND		NotNull(SVCMODFIRSTORDER)									
110.00			SVCMODFIRSTORDER	<=	SVCSEOFIRSTORDER			RE	RE	RE	First Order SVCMOD can not be later than SVCSEO First Order	{LD: SVCMOD} First Order date must not be later than the {LD: SVCSEO} {NDN: SVCSEO)
110.20	END	109.92										
          SVCMODFIRSTORDER    <=  SVCSEOFIRSTORDER            RE  RE  RE  First Order SVCMOD can not be later than SVCSEO First Order {LD: SVCMOD} First Order date must not be later than the {LD: SVCSEO} {NDN: SVCSEO)

     * @param rootEntity
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private void checkFODates(EntityItem rootEntity) throws SQLException, MiddlewareException{
        String seofodate=null;
        String modfodate=null;
        //108.80            SVCSEOFIRSTORDER    FirstValue      SVCSEOFO                    Derives SVCSEO First Order  
        //109.00                        SVCSEOAD                        
        //109.20                        SVCSEOPA                        
        if (SVCSEOFO!=null){
            seofodate = SVCSEOFO;
        }else if (SVCSEOAD!=null){
            seofodate = SVCSEOAD;
        }else if (SVCSEOPA!=null){
            seofodate = SVCSEOPA;
        }

        //109.40            SVCMODFIRSTORDER    FirstValue      SVCMODFO                    Derives SVCMOD First Order   
        //109.60                        SVCMODAD                        
        //109.80                        SVCMODPA                        
        if (SVCMODFO!=null){
            modfodate = SVCMODFO;
        }else if (SVCMODAD!=null){
            modfodate = SVCMODAD;
        }else if (SVCMODPA!=null){
            modfodate = SVCMODPA;
        }
        //109.90	IF		NotNull(SVCSEOFIRSTORDER)									
        //109.92	AND		NotNull(SVCMODFIRSTORDER)									
        //110.00			SVCMODFIRSTORDER	<=	SVCSEOFIRSTORDER			RE	RE	RE	First Order SVCMOD can not be later than SVCSEO First Order	{LD: SVCMOD} First Order date must not be later than the {LD: SVCSEO} {NDN: SVCSEO)
        //FODATE_ERR= {0} First Order date {1} must not be later than the {2} date {3}
        if(modfodate!=null && seofodate!=null){
            if(modfodate.compareTo(seofodate)>0){
                args[0]=rootEntity.getEntityGroup().getLongDescription();
                args[1]=modfodate;
                args[3]=seofodate;
                
                Vector svcseoVct = PokUtils.getAllLinkedEntities(rootEntity, "SVCMODSVCSEO", "SVCSEO");
                for(int iv=0; iv<svcseoVct.size(); iv++){
                    EntityItem svcseo = (EntityItem)svcseoVct.elementAt(iv);
                    args[2]=this.getLD_NDN(svcseo);
                    createMessage(CHECKLEVEL_RE,"FODATE_ERR",args); 
                }
            }
        }
        //110.20	END	109.92										
    }
    /**
106.60      W   "SVCMODSVCSEO-d SVCSEOAVAIL-d"      AVAIL                           
106.80  WHEN    X   AVAILTYPE   =   "Planned Availability" (146)                            
107.00          SVCSEOPA    SetTo   X: AVAIL    MIN(EFFECTIVEDATE)                      
107.20      Y   "W: +AVAILANNA-d"       ANNOUNCEMENT                        
107.40  IF      Y: ANNTYPE  =   New (19)                            
107.60          SVCSEOAD    SetTo   T: ANNOUNCEMENT MIN(ANNDATE)                        
107.80  END 107.40                                      
108.00  END 106.80                                      
108.20  WHEN        "First Order"   =   W: AVAIL    AVAILTYPE                       
108.40          SVCSEOFO    SetTo   W:AVAIL MIN(EFFECTIVEDATE)                      
108.60  END 108.20
     * @param rootEntity
     * @throws SQLException
     * @throws MiddlewareException
     */
    private void checkSVCSEOAvails(EntityItem rootEntity) throws SQLException, MiddlewareException {
        Vector svcseoVct = PokUtils.getAllLinkedEntities(rootEntity, "SVCMODSVCSEO", "SVCSEO");
        addDebug("svcseoVct "+svcseoVct.size());
        for(int iv=0; iv<svcseoVct.size(); iv++){
            EntityItem svcseo = (EntityItem)svcseoVct.elementAt(iv);
            addHeading(3,getLD_NDN(svcseo)+" Planned Availability checks:");
            //106.60        W   "SVCMODSVCSEO-d SVCSEOAVAIL-d"      AVAIL   
            Vector svcseoAvailVctW = PokUtils.getAllLinkedEntities(svcseo, "SVCSEOAVAIL", "AVAIL");
            //106.80    WHEN    X   AVAILTYPE   =   "Planned Availability" (146)    
            Vector svcseoplaAvailVctX = PokUtils.getEntitiesWithMatchedAttr(svcseoAvailVctW, "AVAILTYPE", PLANNEDAVAIL);
            //108.20    WHEN        "First Order"   =   W: AVAIL    AVAILTYPE
            Vector svcseofoAvailVct = PokUtils.getEntitiesWithMatchedAttr(svcseoAvailVctW, "AVAILTYPE", FIRSTORDERAVAIL);

            addDebug("svcseo "+svcseo.getKey()+" svcseoAvailVctW "+svcseoAvailVctW.size()+
                    " svcseoplaAvailVctX "+svcseoplaAvailVctX.size()+" svcseofoAvailVct "+svcseofoAvailVct.size());
            //106.80    WHEN    X   AVAILTYPE   =   "Planned Availability" (146)
            for(int i=0; i<svcseoplaAvailVctX.size(); i++){
                EntityItem availitem = (EntityItem)svcseoplaAvailVctX.elementAt(i);
                String effDate = PokUtils.getAttributeValue(availitem, "EFFECTIVEDATE", "", null, false);
                //107.00            SVCSEOPA    SetTo   X: AVAIL    MIN(EFFECTIVEDATE)                          
                if (SVCSEOPA==null){
                    SVCSEOPA = effDate;
                }else{
                    if (SVCSEOPA.compareTo(effDate)>0){ // find earliest date
                        SVCSEOPA = effDate;
                    }
                }
                //107.20        Y   "W: +   AVAILANNA-d"        ANNOUNCEMENT
                Vector annVct= PokUtils.getAllLinkedEntities(availitem, "AVAILANNA", "ANNOUNCEMENT");
                addDebug(availitem.getKey()+" annVct "+annVct.size());
                for (int i2=0; i2<annVct.size(); i2++){
                    EntityItem annItem = (EntityItem)annVct.elementAt(i2);
                    String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
                    addDebug(annItem.getKey()+" type "+anntype);
                    //107.40    IF      Y: ANNTYPE  =   New (19)
                    if(ANNTYPE_NEW.equals(anntype)){
                        //107.60            SVCSEOAD    SetTo   T: ANNOUNCEMENT MIN(ANNDATE)
                        String annDate = PokUtils.getAttributeValue(annItem, "ANNDATE", "", null, false);
                        if (SVCSEOAD==null){
                            SVCSEOAD = annDate;
                        }else{
                            if (SVCSEOAD.compareTo(annDate)>0){ // find earliest date
                                SVCSEOAD = annDate;
                            }
                        }
                        addDebug("svcseo pla avail ann "+annItem.getKey()+" annDate "+annDate+" SVCSEOAD "+SVCSEOAD);
                    }
                    //107.80    END 107.40
                }// end ann loop
                annVct.clear();
            }
            //108.00    END 106.80

            //108.20    WHEN        "First Order"   =   W: AVAIL    AVAILTYPE                       
            //108.40            SVCSEOFO    SetTo   W:AVAIL MIN(EFFECTIVEDATE)                      
            for(int i=0; i<svcseofoAvailVct.size(); i++){
                EntityItem availitem = (EntityItem)svcseofoAvailVct.elementAt(i);
                String effDate = PokUtils.getAttributeValue(availitem, "EFFECTIVEDATE", "", null, false);
                //108.40            SVCSEOFO    SetTo   W:AVAIL MIN(EFFECTIVEDATE)      
                if (SVCSEOFO==null){
                    SVCSEOFO = effDate;
                }else{
                    if (SVCSEOFO.compareTo(effDate)>0){ // find earliest date
                        SVCSEOFO = effDate;
                    }
                }
                addDebug("svcseo fo avail "+availitem.getKey()+" effDate "+effDate+" SVCSEOFO "+SVCSEOFO);
            }
            //108.60    END 108.20
        }
        //VALUE_FND = {0} found for {1} {2}
        if(SVCSEOFO==null){
            args[0] = "No Date";
        }else{
            args[0] = SVCSEOFO;
        }
        args[1] = m_elist.getEntityGroup("SVCSEO").getLongDescription();
        args[2] = "First Order date";
        
        addResourceMsg("VALUE_FND",args);
        //VALUE_FND = {0} found for {1} {2}
        if(SVCSEOPA==null){
            args[0] = "No Date";
        }else{
            args[0] = SVCSEOPA;
        }
        args[1] = m_elist.getEntityGroup("SVCSEO").getLongDescription();
        args[2] = "Planned Availability date";
        addResourceMsg("VALUE_FND",args);
        
        //VALUE_FND = {0} found for {1} {2}
        if(SVCSEOAD==null){
            args[0] = "No Date";
        }else{
            args[0] = SVCSEOAD;
        }
        args[1] = m_elist.getEntityGroup("SVCSEO").getLongDescription();
        args[2] = "New Announcement date";
        addResourceMsg("VALUE_FND",args);
    }
    /**
     * 
104.20      S   SVCMODAVAIL-d       AVAIL                           
104.40  WHEN    R   AVAILTYPE   =   "Planned Availability" (146)    SVCMOD AVAIL "Planned Availability" 
104.60          "Q:  +  PRCPTCNTRYEFF-d     COUNTRYLIST"    "IN aggregate G"    R: AVAIL    COUNTRYLIST     E   E   E   
PRCPT CNTRYEFF COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST 
{LD: PRCPT} {NDN: Q:PRCPT} {LD: CNTRYEFF} {NDN: CNTRYEFF} {LD: COUNTRYLIST} must not include a country that is not in the {LD: SVCMOD} {LD: AVAIL}
104.80          SVCMODPA    SetTo   R: AVAIL    MIN(EFFECTIVEDATE)                      
105.00      T   "S: +   AVAILANNA-d"        ANNOUNCEMENT                            
105.20  IF      T: ANNTYPE  =   New (19)                            
105.40          SVCMODAD    SetTo   T: ANNOUNCEMENT MIN(ANNDATE)                        
105.60  END 105.20  
105.80  END 104.40  

106.00  WHEN        "First Order"   =   S: AVAIL    AVAILTYPE                       
106.20          SVCMODFO    SetTo   S:AVAIL MIN(EFFECTIVEDATE)                      
106.40  END 106.00
     * @param rootEntity
     * @throws SQLException
     * @throws MiddlewareException
     */
    private void checkSVCMODAvails(EntityItem rootEntity) 
    throws SQLException, MiddlewareException
    {                           
        EntityGroup prcptGrp = m_elist.getEntityGroup("PRCPT");

        //104.20        S   SVCMODAVAIL-d       AVAIL
        Vector availVctS = PokUtils.getAllLinkedEntities(rootEntity, "SVCMODAVAIL", "AVAIL");
        //104.40    WHEN    R   AVAILTYPE   =   "Planned Availability" (146)    SVCMOD AVAIL "Planned Availability"
        Vector plaAvailVctR = PokUtils.getEntitiesWithMatchedAttr(availVctS, "AVAILTYPE", PLANNEDAVAIL);
        //106.00    WHEN        "First Order"   =   S: AVAIL    AVAILTYPE
        Vector foAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVctS, "AVAILTYPE", FIRSTORDERAVAIL);
        
        ArrayList plaavailCtrys = getCountriesAsList(plaAvailVctR ,CHECKLEVEL_E); // get all planned avail ctrys
        
        addDebug("root availVctS "+availVctS.size()+" plaAvailVctR "+plaAvailVctR.size()+
                " foAvailVct "+foAvailVct.size()+" plaavailCtrys "+plaavailCtrys); 
        
        //104.40    WHEN    R   AVAILTYPE   =   "Planned Availability" (146)    SVCMOD AVAIL "Planned Availability"
        for(int i=0; i<plaAvailVctR.size(); i++){
            EntityItem availitem = (EntityItem)plaAvailVctR.elementAt(i);
            String effDate = PokUtils.getAttributeValue(availitem, "EFFECTIVEDATE", "", null, false);
            //104.80            SVCMODPA    SetTo   R: AVAIL    MIN(EFFECTIVEDATE)      
            if (SVCMODPA==null){
                SVCMODPA = effDate;
            }else{
                if (SVCMODPA.compareTo(effDate)>0){ // find earliest date
                    SVCMODPA = effDate;
                }
            }

            addDebug("svcmod pla avail "+availitem.getKey()+" effDate "+effDate+" SVCMODPA "+SVCMODPA);

            //105.00        T   "S: +   AVAILANNA-d"        ANNOUNCEMENT                            
            Vector annVct= PokUtils.getAllLinkedEntities(availitem, "AVAILANNA", "ANNOUNCEMENT");
            addDebug(availitem.getKey()+" annVct "+annVct.size());
            for (int i2=0; i2<annVct.size(); i2++){
                EntityItem annItem = (EntityItem)annVct.elementAt(i2);
                String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
                addDebug(annItem.getKey()+" type "+anntype);
                //105.20    IF      T: ANNTYPE  =   New (19)
                if(ANNTYPE_NEW.equals(anntype)){
                    //105.40            SVCMODAD    SetTo   T: ANNOUNCEMENT MIN(ANNDATE)
                    String annDate = PokUtils.getAttributeValue(annItem, "ANNDATE", "", null, false);
                    if (SVCMODAD==null){
                        SVCMODAD = annDate;
                    }else{
                        if (SVCMODAD.compareTo(annDate)>0){ // find earliest date
                            SVCMODAD = annDate;
                        }
                    }
                    addDebug("svcmod pla avail ann "+annItem.getKey()+" annDate "+annDate+" SVCMODAD "+SVCMODAD);
                }
                //105.60    END 105.20  
            }// end ann loop
            annVct.clear();
        }   
        //38.00 END 32.00
        //VALUE_FND = {0} found for {1} {2}
        if(SVCMODPA==null){
            args[0] = "No Date";
        }else{
            args[0] = SVCMODPA;
        }
        args[1] = rootEntity.getEntityGroup().getLongDescription();
        args[2] = "Planned Availability date";
        addResourceMsg("VALUE_FND",args);

		EntityGroup egCNTRYEFF = m_elist.getEntityGroup("CNTRYEFF");
     	addHeading(3,egCNTRYEFF.getLongDescription()+" and Planned Availability checks:");
     	
        for(int i=0; i<prcptGrp.getEntityItemCount(); i++){
            EntityItem prcptitem = prcptGrp.getEntityItem(i);
            Vector cntryeffVct = PokUtils.getAllLinkedEntities(prcptitem, "PRCPTCNTRYEFF", "CNTRYEFF");
            addDebug(" "+prcptitem.getKey()+" cntryeffVct "+cntryeffVct.size());
            for (int c=0; c<cntryeffVct.size(); c++){
                EntityItem cntryeffitem = (EntityItem)cntryeffVct.elementAt(c);
                String missingCtry = checkCtryMismatch(cntryeffitem, plaavailCtrys, CHECKLEVEL_E);
                if (missingCtry.length()>0){
                    addDebug(cntryeffitem.getKey()+" COUNTRYLIST had extra ["+missingCtry+"]");
//                  104.60  "Q:  +  PRCPTCNTRYEFF-d     COUNTRYLIST"    "IN aggregate G"    R: AVAIL    COUNTRYLIST     E   E   E   
//                  PRCPT CNTRYEFF COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST 
//                  {LD: PRCPT} {NDN: Q:PRCPT} {LD: CNTRYEFF} {NDN: CNTRYEFF} {LD: COUNTRYLIST} must not include a country that is not in the {LD: SVCMOD} {LD: AVAIL}
                    //INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
                    args[0]=this.getLD_NDN(prcptitem);
                    args[1]=this.getLD_NDN(cntryeffitem);

                    args[2]=rootEntity.getEntityGroup().getLongDescription()+" "+
                    m_elist.getEntityGroup("AVAIL").getLongDescription();
                    args[3]=PokUtils.getAttributeDescription(egCNTRYEFF, "COUNTRYLIST", "COUNTRYLIST");
                    args[4]=missingCtry;
                    createMessage(CHECKLEVEL_E,"INCLUDE_ERR2",args);
                }
            }
            cntryeffVct.clear();
        }

        //VALUE_FND = {0} found for {1} {2}
        if(SVCMODAD==null){
            args[0] = "No Date";
        }else{
            args[0] = SVCMODAD;
        }
        args[1] = rootEntity.getEntityGroup().getLongDescription();
        args[2] = "New Announcement date";
        addResourceMsg("VALUE_FND",args);

        //106.00    WHEN        "First Order"   =   S: AVAIL    AVAILTYPE                                                       
        for(int i=0; i<foAvailVct.size(); i++){
            EntityItem availitem = (EntityItem)foAvailVct.elementAt(i);
            String effDate = PokUtils.getAttributeValue(availitem, "EFFECTIVEDATE", "", null, false);
            //106.20            SVCMODFO    SetTo   S:AVAIL MIN(EFFECTIVEDATE)      
            if (SVCMODFO==null){
                SVCMODFO = effDate;
            }else{
                if (SVCMODFO.compareTo(effDate)>0){ // find earliest date
                    SVCMODFO = effDate;
                }
            }
            addDebug("svcmod fo avail "+availitem.getKey()+" effDate "+effDate+" SVCMODFO "+SVCMODFO);
        }
        //106.40    END 106.00  
        //VALUE_FND = {0} found for {1} {2}
        if(SVCMODFO==null){
            args[0] = "No Date";
        }else{
            args[0] = SVCMODFO;
        }
        args[1] = rootEntity.getEntityGroup().getLongDescription();
        args[2] = "First Order date";
        addResourceMsg("VALUE_FND",args);
        
        // release memory
        plaavailCtrys.clear();
    }

    /**
     * get all avails needed for avail checks
     * @param rootEntity
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private void getAvails(EntityItem rootEntity, String statusFlag) throws MiddlewareException, SQLException
    {
        //  get all AVAILS
        EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
        if (availGrp ==null){
            throw new MiddlewareException("AVAIL is missing from extract for "+m_abri.getVEName());
        }

        addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Availability RFA checks:");
        checkAvailAnnType();

        // look at SVCMOD avails
        svcmodAvailVct = PokUtils.getAllLinkedEntities(rootEntity, "SVCMODAVAIL", "AVAIL");

        //4.00  AVAIL   A   SVCMODAVAIL-d       SVCMOD AVAIL    5.00    WHEN    A   AVAILTYPE   =   "Planned Availability"
        svcmodPlaAvailVctA = PokUtils.getEntitiesWithMatchedAttr(svcmodAvailVct, "AVAILTYPE", PLANNEDAVAIL);
        //18.00 AVAIL   C   SVCMODAVAIL-d   SVCMOD AVAIL    19.00   WHEN    C   AVAILTYPE   =   "First Order"   
        svcmodFOAvailVctC = PokUtils.getEntitiesWithMatchedAttr(svcmodAvailVct, "AVAILTYPE", FIRSTORDERAVAIL);
        //30.00 AVAIL   E   SVCMODAVAIL-d   SVCMOD AVAIL    31.00   WHEN        AVAILTYPE   =   "Last Order"
        svcmodLOAvailVctE = PokUtils.getEntitiesWithMatchedAttr(svcmodAvailVct, "AVAILTYPE", LASTORDERAVAIL);
        //45.00 AVAIL   G   SVCMODAVAIL-d   SVCMOD AVAIL    46.00   WHEN        AVAILTYPE   =   "End of Service" (151)  
        svcmodEOSAvailVctG = PokUtils.getEntitiesWithMatchedAttr(svcmodAvailVct, "AVAILTYPE", EOSAVAIL);

        svcmodEOMAvailVctM = PokUtils.getEntitiesWithMatchedAttr(svcmodAvailVct, "AVAILTYPE", EOMAVAIL);
        addDebug("getAvails SVCMODAVAIL svcmodPlaAvailVctA: "+svcmodPlaAvailVctA.size()+
                " svcmodFOAvailVctC: " +svcmodFOAvailVctC.size()+" svcmodLOAvailVctE: "+svcmodLOAvailVctE.size()+
                " svcmodEOSAvailVctG: "+svcmodEOSAvailVctG.size()+
                " svcmodEOMAvailVctM: "+svcmodEOMAvailVctM.size());

        svcmdlPlaAvailCtryTblA = getAvailByCountry(svcmodPlaAvailVctA, getCheck_W_W_E(statusFlag));
        addDebug("getAvails SVCMODAVAIL svcmdlPlaAvailCtryTblA "+svcmdlPlaAvailCtryTblA.keySet());

        svcmdlFOAvailCtryTblC = getAvailByCountry(svcmodFOAvailVctC, getCheck_W_W_E(statusFlag));
        addDebug("getAvails SVCMODAVAIL svcmdlFOAvailCtryTblC: " +svcmdlFOAvailCtryTblC.keySet());

        svcmdlLOAvailCtryTblE = getAvailByCountry(svcmodLOAvailVctE, getCheck_W_W_E(statusFlag));
        addDebug("getAvails SVCMODAVAIL svcmdlLOAvailCtryTblE: " +svcmdlLOAvailCtryTblE.keySet());

        svcmdlEOSAvailCtryTblG = getAvailByCountry(svcmodEOSAvailVctG, getCheck_W_W_E(statusFlag));
        addDebug("getAvails SVCMODAVAIL svcmdlEOSAvailCtryTblG: " +svcmdlEOSAvailCtryTblG.keySet());

    }

    public void dereference(){
        super.dereference();

        SVCSEOFO = null;
        SVCSEOPA = null;
        SVCSEOAD = null;
        SVCMODFO = null;
        SVCMODPA = null;
        SVCMODAD = null;

        mdlPlaAnnVct.clear();
        mdlPlaAnnVct = null;
        if (svcmodAvailVct!=null){
            svcmodAvailVct.clear();
            svcmodAvailVct = null;
        }
        if (svcmodLOAvailVctE!=null){
            svcmodLOAvailVctE.clear();
            svcmodLOAvailVctE = null;
        }
        if (svcmodPlaAvailVctA!=null){
            svcmodPlaAvailVctA.clear();
            svcmodPlaAvailVctA = null;
        }
        if (svcmodFOAvailVctC!=null){
            svcmodFOAvailVctC.clear();
            svcmodFOAvailVctC = null;
        }
        if (svcmodEOSAvailVctG!=null){
            svcmodEOSAvailVctG.clear();
            svcmodEOSAvailVctG = null;
        }
        if (svcmdlPlaAvailCtryTblA!=null){
            svcmdlPlaAvailCtryTblA.clear();
            svcmdlPlaAvailCtryTblA = null;
        }
        if (svcmdlFOAvailCtryTblC!=null){
            svcmdlFOAvailCtryTblC.clear();
            svcmdlFOAvailCtryTblC = null;
        }
        if (svcmdlLOAvailCtryTblE!=null){
            svcmdlLOAvailCtryTblE.clear();
            svcmdlLOAvailCtryTblE = null;
        }
        if (svcmdlEOSAvailCtryTblG!=null){
            svcmdlEOSAvailCtryTblG.clear();
            svcmdlEOSAvailCtryTblG = null;
        }
    }
    /**
     * @param rootEntity
     * @param statusFlag
     * @param checklvl
     * @throws java.sql.SQLException
     * @throws MiddlewareException
     * 
     */
    private void checkAvails(EntityItem rootEntity, String statusFlag, int checklvl) 
    throws java.sql.SQLException, MiddlewareException
    {
        //  get all AVAILS
        EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");

        // check model avails
        addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" "+
                availGrp.getLongDescription()+" Planned Availability Checks:");
        checkSvcmodPlaAvails(rootEntity, statusFlag);

        addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" "+
                availGrp.getLongDescription()+" First Order Checks:");
        checkSvcmodFOAvails(rootEntity, statusFlag); 

        addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" "+
                availGrp.getLongDescription()+" Last Order Checks:");
        checkSvcmodLOAvails(rootEntity, statusFlag); 

        addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" "+
                availGrp.getLongDescription()+" End of Marketing Checks:");
        checkSvcmodEOMAvails(rootEntity, statusFlag); 
    }

    /**
     * @param rootEntity
     * @param statusFlag
     * @param checklvl
     * @throws java.sql.SQLException
     * @throws MiddlewareException
     * 
checks from ss:
5.00    WHEN        AVAILTYPE   =   "Planned Availability"                      
6.00            Count of    =>  1           RW  RW  RE  must have at least one "Planned Availability"
7.00            EFFECTIVEDATE   =>  SVCMOD  ANNDATE     W   E   E   {LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: SVCMODEL} {LD: ANNDATE} {ANNDATE}
8.00            COUNTRYLIST                             
9.00    ANNOUNCEMENT    B   A: + AVAILANNA-d                                
10.00   WHEN        "RFA" (RFA) <>  A: AVAIL    AVAILANNTYPE                    
11.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
12.00   END 10.00                                   
13.00   IF      ANNTYPE <>  New (19)                        
14.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
15.00   ELSE        ANNDATE =>  SVCMOD  ANNDATE     W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} can not be earlier than the {LD: SVCMOD} {LD: ANNDATE} {ANNDATE}
16.00   END 13.00                                   
17.00   END 5.00    
     */
    private void checkSvcmodPlaAvails(EntityItem rootEntity, String statusFlag) 
    throws java.sql.SQLException, MiddlewareException
    {       
        addDebug("checkSvcmodPlaAvails svcmodPlaAvailVctA "+svcmodPlaAvailVctA.size());
        //5.00  WHEN        AVAILTYPE   =   "Planned Availability"                          
        //6.00      Count Of    =>  1           RW  RW  RE  must have at least one "Planned Availability"
        checkPlannedAvailsExist(svcmodPlaAvailVctA, getCheck_RW_RW_RE(statusFlag));
//        20121030 Add	6.20	WHEN		"Final" (FINAL)	=	SVCMOD	DATAQUALITY																																																																																																																																																																																																																																																		
//        20121030 Add	6.22	IF		STATUS	=	"Ready for Review" (0040)																																																																																																																																																																																																																																																			
//        20121030 Add	6.24	OR		STATUS	=	"Final" (0020)																																																																																																																																																																																																																																																			
//        20121030 Add	6.26			CountOf	=>	1					RE		must have at least one "Planned Availability" that is either "Ready for Review" or "Final" in order to be "Final"																																																																																																																																																																																																																																												
//        20121030 Add	6.28	END	6.20																																																																																																																																																																																																																																																						
        checkPlannedAvailsStatus(svcmodPlaAvailVctA, rootEntity,CHECKLEVEL_RE);

        for (int i=0; i<svcmodPlaAvailVctA.size(); i++){
            EntityItem avail = (EntityItem)svcmodPlaAvailVctA.elementAt(i);
            //addDebug("checkAvails svcmodPlaAvail "+avail.getKey());
            //5.00  WHEN        AVAILTYPE   =   "Planned Availability"  
            //7.00          EFFECTIVEDATE   =>  SVCMOD  ANNDATE     W   E   E       
            //{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: SVCMODEL} {LD: ANNDATE} {ANNDATE}
            checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "ANNDATE", getCheck_W_E_E(statusFlag));
            //8.00          COUNTRYLIST             Not Checked since SVCMOD does not have COUNTRYLIST  

            String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
            addDebug("checkSvcmodPlaAvails "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
            if (availAnntypeFlag==null){
                availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
            }
            if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){ // error was already logged
                // check its announcements 
                //9.00  ANNOUNCEMENT    B   A: + AVAILANNA-d                                SVCMOD ANNOUNCEMENT 
                //10.00 WHEN        "RFA" (RFA) <>  A: AVAIL    AVAILANNTYPE    - done in getAvails()                   
                //11.00         Count of    =   0           E   E   E           Only AVAIL.AVAILANNTYPE = "RFA" can be in an ANNOUNCEMENT   
                //{LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
                //12.00 END 10.00                                       
                Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");

                //13.00 IF      ANNTYPE <>  New (19)                            
                //14.00         Count of    =   0           E   E   E       
                //{LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
                for (int ai=0; ai<annVct.size(); ai++){
                    EntityItem annItem = (EntityItem)annVct.elementAt(ai);
                    String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
                    addDebug("checkSvcmodPlaAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
                    if(!ANNTYPE_NEW.equals(anntypeFlag)){
                        //MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
                        args[0] = getLD_NDN(avail);
                        args[1] = getLD_NDN(annItem);
                        createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
                        continue;
                    }
                    //15.00 ELSE        ANNDATE =>  SVCMOD  ANNDATE     W   W   E       
                    //{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} can not be earlier than the {LD: SVCMOD} {LD: ANNDATE} {ANNDATE}
                    //16.00 END 13.00   
                    checkCanNotBeEarlier(annItem, "ANNDATE", rootEntity, "ANNDATE", getCheck_W_W_E(statusFlag));
                    mdlPlaAnnVct.add(annItem); // hang onto this for later checks
                }

                annVct.clear();
            }
        }// end svcmod plannedavail loop
    }

    /**
     * @param rootEntity
     * @param statusFlag
     * @param checklvl
     * @throws java.sql.SQLException
     * @throws MiddlewareException
     * 
checks from ss:
18.00   AVAIL   C   SVCMODAVAIL-d                               
19.00   WHEN        AVAILTYPE   =   "First Order"                       
20.00           EFFECTIVEDATE   =>  SVCMOD  ANNDATE     W   W   E   {LD: AVAIL} {NDN: AVAIL} has a date earlier than the {LD: SVCMOD} {LD: ANNDATE}
21.00   ANNOUNCEMENT    D   C: + AVAILANNA-d                                
22.00   WHEN        "RFA" (RFA) <>  C: AVAIL    AVAILANNTYPE                    
23.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: C:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
24.00   END 22.00                                   
25.00   IF      ANNTYPE <>  New (19)                        
26.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: C:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
27.00   ELSE        ANNDATE =>  SVCMOD  ANNDATE     W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} can not be earlier than the {LD: SVCMOD} {LD: ANNDATE} {ANNDATE}
28.00   END 25.00                                   
29.00   END 19.00   
     */
    private void checkSvcmodFOAvails(EntityItem rootEntity, String statusFlag) 
    throws java.sql.SQLException, MiddlewareException
    {
        addDebug("checkSvcmodFOAvails svcmodFOAvailVctC "+svcmodFOAvailVctC.size());

        for (int i=0; i<svcmodFOAvailVctC.size(); i++){
            EntityItem avail = (EntityItem)svcmodFOAvailVctC.elementAt(i);
            //addDebug("checkAvails svcmodFOAvail "+avail.getKey());
            //19.00 WHEN        AVAILTYPE   =   "First Order"                           
            //20.00 EFFECTIVEDATE   =>  SVCMOD  ANNDATE     W   W   E       
            //{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: SVCMODEL} {LD: ANNDATE} {ANNDATE}
            checkCanNotBeEarlier(avail, "EFFECTIVEDATE", rootEntity, "ANNDATE", getCheck_W_W_E(statusFlag));

            String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
            addDebug("checkSvcmodFOAvails "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
            if (availAnntypeFlag==null){
                availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
            }
            if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){ // error was already logged
                // check its announcements
                //21.00 ANNOUNCEMENT    D   C: + AVAILANNA-d                                SVCMOD ANNOUNCEMENT 
                //22.00 WHEN        "RFA" (RFA) <>  C: AVAIL    AVAILANNTYPE    - done in getAvails()                   
                //23.00         Count of    =   0           E   E   E       {LD: AVAIL} {NDN: C:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
                //24.00 END 22.00                                       
                Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");

                //25.00 IF      ANNTYPE <>  New (19)                            
                //26.00         Count of    =   0           E   E   E       
                //{LD: AVAIL} {NDN: C:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
                for (int ai=0; ai<annVct.size(); ai++){
                    EntityItem annItem = (EntityItem)annVct.elementAt(ai);
                    String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
                    addDebug("checkSvcmodFOAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
                    if(!ANNTYPE_NEW.equals(anntypeFlag)){
                        //MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
                        args[0] = getLD_NDN(avail);
                        args[1] = getLD_NDN(annItem);
                        createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
                        continue;
                    }
                    //27.00 ELSE        ANNDATE =>  SVCMOD  ANNDATE     W   W   E       
                    //{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} can not be earlier than the {LD: SVCMOD} {LD: ANNDATE} {ANNDATE}
                    //28.00 END 25.00                                       
                    //29.00 END 19.00                                   
                    checkCanNotBeEarlier(annItem, "ANNDATE", rootEntity, "ANNDATE", getCheck_W_W_E(statusFlag));
                }

                annVct.clear();
            }
        }// end svcmod foavail loop
    }

    /**
     * @param rootEntity
     * @param statusFlag
     * @param checklvl
     * @throws java.sql.SQLException
     * @throws MiddlewareException
     * 
checks from ss:
30.00   AVAIL   E   SVCMODAVAIL-d                               
31.00   WHEN        AVAILTYPE   =   "Last Order"                        
32.00           EFFECTIVEDATE   <=  SVCMOD  WTHDRWEFFCTVDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: SVCMOD} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
33.00           COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
delete34.00 IF      COUNTRYLIST Match   J: AVAIL    COUNTRYLIST                 
delete35.00 THEN        TheMatch    IN  K: AVAIL    COUNTRYLIST     W   RW  RE  {LD: IPSCFEAT} (NDN: K: IPSCFEAT} must have a "Last Order" for all countries in the {LD: SVCMOD} {LD: AVAIL} {E: AVAIL}
36.00   ANNOUNCEMENT    F   E: + AVAILANNA-d                                
37.00   WHEN        "RFA" (RFA) <>  E: AVAIL    AVAILANNTYPE                    
38.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: E:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
39.00   END 37.00                                   
40.00   IF      ANNTYPE <>  "End Of Life - Withdrawal from mktg" (14)                       
41.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: E:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
42.00   ELSE        ANNDATE <=  SVCMOD  WITHDRAWDATE        W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
43.00   END 40.00                                   
44.00   END 31.00   
     */
    private void checkSvcmodLOAvails(EntityItem rootEntity, String statusFlag) 
    throws java.sql.SQLException, MiddlewareException
    {
        addDebug("checkSvcmodLOAvails svcmodLOAvailVctE "+svcmodLOAvailVctE.size());

        for (int i=0; i<svcmodLOAvailVctE.size(); i++){
            EntityItem avail = (EntityItem)svcmodLOAvailVctE.elementAt(i);
            //addDebug("checkSvcmodLOAvails svcmodLOAvail "+avail.getKey());
            //32.00         EFFECTIVEDATE   <=  SVCMOD  WTHDRWEFFCTVDATE        W   W   E       
            //{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: SVCMOD} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
            checkCanNotBeLater(avail, "EFFECTIVEDATE", rootEntity, "WTHDRWEFFCTVDATE", getCheck_W_W_E(statusFlag));

            //33.00         COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST     W   W   E       
            //{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
            checkPlannedAvailForCtryExists(avail, svcmdlPlaAvailCtryTblA.keySet(), getCheck_W_W_E(statusFlag));

            String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
            addDebug("checkSvcmodLOAvails "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
            if (availAnntypeFlag==null){
                availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
            }
            if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){ // error was already logged
                //36.00 ANNOUNCEMENT    F   E: + AVAILANNA-d                                SVCMOD ANNOUNCEMENT 
                //37.00 WHEN        "RFA" (RFA) <>  E: AVAIL    AVAILANNTYPE - done in getAvails()                      
                //38.00         Count of    =   0           E   E   E       
                //{LD: AVAIL} {NDN: E:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
                //39.00 END 37.00                                       
                Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");

                //40.00 IF      ANNTYPE <>  "End Of Life - Withdrawal from mktg" (14)                           
                //41.00         Count of    =   0           E   E   E       {LD: AVAIL} {NDN: E:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
                for (int ai=0; ai<annVct.size(); ai++){
                    EntityItem annItem = (EntityItem)annVct.elementAt(ai);
                    String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
                    addDebug("checkSvcmodLOAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
                    if(!ANNTYPE_EOL.equals(anntypeFlag)){
                        //MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
                        args[0] = getLD_NDN(avail);
                        args[1] = getLD_NDN(annItem);
                        createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
                        continue;
                    }
                    //42.00 ELSE        ANNDATE <=  SVCMOD  WITHDRAWDATE        W   W   E       
                    //{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} can not be later than the {LD: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
                    //43.00 END 40.00   
                    checkCanNotBeLater(annItem, "ANNDATE", rootEntity, "WITHDRAWDATE", getCheck_W_W_E(statusFlag));
                }

                annVct.clear();
            }
        }// end svcmod lo avail loop

        //34.00 IF      COUNTRYLIST Match   J: AVAIL    COUNTRYLIST                 SVCMOD has Last Order ==> TMF available in that Country has to have a Last Order    
        //35.00 THEN        TheMatch    IN  K: AVAIL    COUNTRYLIST     W   RW  RE      {LD: IPSCFEAT} (NDN: K: IPSCFEAT} must have a "Last Order" for all countries in the {LD: SVCMOD} {LD: AVAIL} {E: AVAIL}
        //delete matchPsModelLastOrderAvail(statusFlag);
    }

    /*************
     * 
45.00   AVAIL   M   SVCMODAVAIL-d                               
46.00   WHEN        AVAILTYPE   =   "End of Marketing" (200)                        
47.00           EFFECTIVEDATE   <=  SVCMOD  WITHDRAWDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: SVCMOD} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
48.00           COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
49.00   ANNOUNCEMENT    N   M: + AVAILANNA-d                                
50.00   WHEN        "RFA" (RFA) <>  M: AVAIL    AVAILANNTYPE                    
51.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: M:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
52.00   END 50.00                                   
53.00   IF      ANNTYPE <>  "End Of Life - Withdrawal from mktg" (14)                       
54.00           Count of    =   0           E   E   E   {LD: AVAIL} {NDN: M:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
55.00   ELSE        ANNDATE <=  SVCMOD  WITHDRAWDATE        W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
56.00   END 53.00                                   
57.00   END 46.00
     */
    private void checkSvcmodEOMAvails(EntityItem rootEntity, String statusFlag) 
    throws java.sql.SQLException, MiddlewareException
    {
        addDebug("checkSvcmodEOMAvails svcmodEOMAvailVctM "+svcmodEOMAvailVctM.size());
        for (int i=0; i<svcmodEOMAvailVctM.size(); i++){
            EntityItem avail = (EntityItem)svcmodEOMAvailVctM.elementAt(i);
            //addDebug("checkSvcmodEOMAvails svcmodLOAvail "+avail.getKey());
            //47.00         EFFECTIVEDATE   <=  SVCMOD  WITHDRAWDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: SVCMOD} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
            checkCanNotBeLater(avail, "EFFECTIVEDATE", rootEntity, "WITHDRAWDATE", getCheck_W_W_E(statusFlag));

            //48.00         COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST     W   W   E   {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
            checkPlannedAvailForCtryExists(avail, svcmdlPlaAvailCtryTblA.keySet(), getCheck_W_W_E(statusFlag));

            String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
            addDebug("checkSvcmodEOMAvails "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
            if (availAnntypeFlag==null){
                availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
            }
            if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){ // error was already logged
                //49.00 ANNOUNCEMENT    N   M: + AVAILANNA-d                                
                //50.00 WHEN        "RFA" (RFA) <>  M: AVAIL    AVAILANNTYPE                    
                //51.00         Count of    =   0           E   E   E   {LD: AVAIL} {NDN: M:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
                //52.00 END 50.00                                   
                Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");

                //53.00 IF      ANNTYPE <>  "End Of Life - Withdrawal from mktg" (14)                       
                //54.00         Count of    =   0           E   E   E   {LD: AVAIL} {NDN: M:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
                for (int ai=0; ai<annVct.size(); ai++){
                    EntityItem annItem = (EntityItem)annVct.elementAt(ai);
                    String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
                    addDebug("checkSvcmodEOMAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
                    if(!ANNTYPE_EOL.equals(anntypeFlag)){
                        //MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
                        args[0] = getLD_NDN(avail);
                        args[1] = getLD_NDN(annItem);
                        createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
                        continue;
                    }
                    //55.00 ELSE        ANNDATE <=  SVCMOD  WITHDRAWDATE        W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: SVCMOD} {LD: WITHDRAWDATE} {WITHDRAWDATE}
                    //56.00 END 53.00                                   
                    //57.00 END 46.00
                    checkCanNotBeLater(annItem, "ANNDATE", rootEntity, "WITHDRAWDATE", getCheck_W_W_E(statusFlag));
                }

                annVct.clear();
            }
        }// end svcmod eom avail loop
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
	protected boolean updateDerivedData(EntityItem rootEntity) throws Exception {
		boolean chgsMade = false;
		//	NOW() <= Withdrawal Effective Date (WTHDRWEFFCTVDATE)
		String wdDate = PokUtils.getAttributeValue(rootEntity, "WTHDRWEFFCTVDATE", "", FOREVER_DATE, false);
		addDebug("updateDerivedData wdDate: "+wdDate+" now: "+getCurrentDate());
		if(getCurrentDate().compareTo(wdDate)<=0){
			chgsMade = execDerivedData(rootEntity);
		}
		return chgsMade;
	}
	
    /* (non-Javadoc)
     * update LIFECYCLE value when STATUS is updated
     * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
     */
    protected String getLCRFRWFName(){ return "WFLCSVCMODRFR";}
    protected String getLCFinalWFName(){ return "WFLCSVCMODFINAL";}

    /***********************************************
     *  Get ABR description
     *
     *@return java.lang.String
     */
    public String getDescription()
    {
        String desc =  "SVCMOD ABR.";

        return desc;
    }

    /***********************************************
     *  Get the version
     *
     *@return java.lang.String
     */
    public String getABRVersion()
    {
        return "1.12";
    }
}
