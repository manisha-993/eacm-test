//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2005, 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

import com.ibm.transform.oim.eacm.util.*;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.Attribute;
import COM.ibm.opicmpdh.transactions.*;

/**********************************************************************************
 * PRODSTRUCTABRSTATUS
 *
 *BH FS ABR Data Quality 20120306.doc - OSN updates
 *
 *BH FS ABR Data Quality 20120116.doc - remove 277.00-285.00
 *
 *BH FS ABR Data Quality 20111020e.xls BH Defect 67890 (support for old data)
 *sets changes
 * BH FS ABR Data Quality 20110816c.doc - identicalwarr updates
 *
 * BH FS ABR Data Quality 20110322.doc
 * Delete 5.04 and 5.08 checks and PropagateProdstructWarr changes
 * 
 *BH FS ABR Data Qualtity Checks 20110318.xls date chk chgs
 *
 *From "BH FS ABR Data Quality 20101026.doc"
 *sets chgs
 *From "BH FS ABR Data Quality 20100924.doc"
 *need workflow actions
 *needs LIFECYCLE attribute and transitions
 *missing PRODSTRUCT.ADSABRSTATUS
 *
 * The Product Structure (PRODSTRUCT) is part of a complex data structure and hence a
 * Virtual Entity (VE) may be used to describe the applicable structure for the Data
 * Quality checks and another VE for the SAPL feed.
 *
 * There may be a SAPL XML Feed to OIDH (LEDGER) of this data which is determined
 * after the data quality checks (if any) are complete.
 *
 * The ABR produces a Report as described in section XXI. ABR Reports.
 *
 * The ABR sets its unique attribute (PRODSTRUCTABRSTATUS)
 *
The NDN of PRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
FEATURE.FEATURECODE
A.  Checking
1.  The dates for Product Structure (aka TMF PRODSTRUCT) need to be consistent with the Model (MODEL)
and Feature (FEATURE). This includes planning and availability dates (AVAIL). The TMF cannot be available before
the Model nor the Feature. It may be available after the Model if the order code is not just Initial. It may not
be available after the Feature.
2.  TMF Error (Key 68)
A Model (MODEL) cannot have identical or duplicate Feature Codes (FEATURECODE).

Features within a Machine Type (MACHTYPE) with duplicate Feature Codes (FEATURECODE) must be identical.

SEARCH using action SRDPRODSTRUCTV specifying MACHTYPEATR and FEATURECODE:

a.  UniqueWithinMODEL
If a MODEL has more than one FEATURE with duplicate FEATURECODE, then report an error.
b.  IdenticalWithinMACHTYPE
If a MACHTYPE has more than one FEATURE with duplicate FEATURECODE, then the ENTITYIDs must be identical or else report an error.
3.  ORDERCODE is validated (Key 93)


PRODSTRUCTABRSTATUS_class=COM.ibm.eannounce.abr.sg.PRODSTRUCTABRSTATUS
PRODSTRUCTABRSTATUS_enabled=true
PRODSTRUCTABRSTATUS_idler_class=A
PRODSTRUCTABRSTATUS_keepfile=true
PRODSTRUCTABRSTATUS_report_type=DGTYPE01
PRODSTRUCTABRSTATUS_vename=DQVEPRODSTRUCT
PRODSTRUCTABRSTATUS_CAT1=RPTCLASS.PRODSTRUCTABRSTATUS
PRODSTRUCTABRSTATUS_CAT2=
PRODSTRUCTABRSTATUS_CAT3=RPTSTATUS
PRODSTRUCTABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
 */

//PRODSTRUCTABRSTATUS.java,v
//Revision 1.48  2015/05/14 15:39:38  guobin
//IN6352731 - Planned Avail has to be super set of Last Order Avail
//
//Revision 1.47  2015/02/02 13:49:38  liuweim
//RCQ00337764-RQ - remove BHPH checking from DQ for IBM and Lenovo
//
//Revision 1.13  2010/03/18 12:14:19  wendy
//BH FS ABR Data Quality 20100313.doc updates

//Revision 1.8  2010/01/04 12:48:22  wendy
//cvs failure again

//Revision 1.5  2009/12/07 18:27:35  wendy
//Updated for spec chg BH FS ABR Data Qualtity 20091120.xls

//Revision 1.4  2009/11/04 15:08:07  wendy
//BH Configurable Services - spec chgs

//Revision 1.3  2009/08/17 15:30:10  wendy
//Added headings

//Revision 1.2  2009/08/15 01:41:50  wendy
//SR10, 11, 12, 15, 17 BH updates phase 4

//Revision 1.1  2009/08/06 22:24:31  wendy
//SR10, 11, 12, 15, 17 BH updates phase 3

public class PRODSTRUCTABRSTATUS extends DQABRSTATUS
{
    private static final String PS_SRCHACTION_NAME = "SRDPRODSTRUCTV";
    private static final String ORDERCODE_BOTH = "5955"; //ORDERCODE    5955    B   Both
    private static final String ORDERCODE_MES = "5956"; //  ORDERCODE   5956    M   MES
    private static final String ORDERCODE_INITIAL = "5957"; //  ORDERCODE   5957    I   Initial
    private static final String PRICEDFEATURE_YES = "100"; //PRICEDFEATURE  100     Yes
    private static final String MAINTPRICE_YES = "Yes"; //MAINTPRICE     Yes	Yes	Yes
    private static final String WARRSVCCOVR_WARRANTY = "WSVC02"; // WARRSVCCOVR	WSVC02	Warranty	Warranty
    //private static final String ZEROPRICE_YES = "100"; //ZEROPRICE    100 Yes, 120        No

    //private static final String SEOORDERCODE_MES =  "20";
    private static final Hashtable ATTR_OF_INTEREST_TBL;
    static{
        ATTR_OF_INTEREST_TBL = new Hashtable();
        ATTR_OF_INTEREST_TBL.put("PRODSTRUCTWARR",new String[]{"EFFECTIVEDATE","ENDDATE","COUNTRYLIST","DEFWARR"});
    }

    private EntityList wwseoList = null;
    private EntityList mtmList = null;
    private EntityList mdlList = null;
    private boolean isRPQ = false;
    private boolean isInitialOrderCode = false;
    
    /* (non-Javadoc)
     * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#dereference()
     */
    public void dereference(){
        super.dereference();
        if(wwseoList!=null){
            wwseoList.dereference();
            wwseoList = null;
        }
        if(mtmList!=null){
            mtmList.dereference();
            mtmList = null;
        }
        if(mdlList != null){
            mdlList.dereference();
            mdlList = null;
        }
    }

    /**********************************
     * always check if not final, but need navigation name from model and fc
     */
    protected boolean isVEneeded(String statusFlag) {
        return true;
    }

    /*
     * from set ss
151.00      PRODSTRUCT
151.01      IF      WWSEOPRODSTRUCT-u   WWSEO   STATUS  =   "Final" (0020)
151.02      AND     WWSEOLSEO-d LSEO    STATUS  =   "Final" (0020)
151.03      Perform         LSEO                        PropagateProdstructWarr
151.04      ELSE    151.01
151.05      IF          LSEO    STATUS  =   "Ready for Review" (0040)
151.06      AND         LSEO    LIFECYCLE   =   "Develop" (LF02)  | "Plan" (LF01)
151.07      AND         WWSEO   STATUS  =   "Final" (0020) | "Ready for Review" (0040)
151.08      Perform         LSEO                    PropagateProdstructWarr
151.09      END 151.01

151.17      IF      PRODSTRUCT-d    MODEL   ANNDATE >   "2010-03-01"
Delete 2011-10-20	151.18      OR          MODEL   PDHDOMAIN   IN  XCC_LIST
151.20      IF          PRODSTRUCT  STATUS  =   "Ready for Review" (0040)
151.22      AND         PRODSTRUCT  LIFECYCLE   =   "Develop" (LF02)  | "Plan" (LF01)
151.24      IF      PRODSTRUCT-u    FEATURE FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)
151.26      SET         PRODSTRUCT              ADSABRSTATUS    &ADSFEEDRFR
151.28      ELSE    151.24
151.30      IF      OOFAVAIL-d: AVAILANNA   AVAIL   STATUS  =   "Final" (0020) | "Ready for Review" (0040)
151.31	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
151.32		OR			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
151.34		SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
151.35	R1.0	END	151.31							

151.36      END 151.30
151.38      END 151.24
151.40      END 151.20
152.00      IF          PRODSTRUCT  STATUS  =   "Final" (0020)
152.02      IF      PRODSTRUCT-u    FEATURE FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)
152.04      SET         PRODSTRUCT              ADSABRSTATUS        &ADSFEED
152.06      ELSE    152.02
152.08      IF      OOFAVAIL-d: AVAILANNA   AVAIL   STATUS  =   "Final" (0020)
152.10      AND         ANNOUNCEMENT    STATUS  =   "Final" (0020)
152.12      SET         PRODSTRUCT              ADSABRSTATUS        &ADSFEED
152.14      END 152.08
152.16      END 152.02
152.18      END 152.00
153.00      IF      PRODSTRUCT-u    FEATURE FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)
Add 2011-10-20		153.200	R1.0	AND			PRODSTRUCT	PDHDOMAIN	IN	XCC_LIST
154.00      SET         PRODSTRUCT              WWPRTABRSTATUS      &ABRWAITODS2
155.00      Perform         EXTRACTRPQ                      DeriveEXTRACTRPQ
156.00      END 153.00
157.00      IF      PRODSTRUCT-u    FEATURE FCTYPE  =   Primary FC (100) | "Secondary FC" (110)
158.00      AND     OOFAVAIL-d: AVAILANNA   ANNOUNCEMENT    STATUS  =   "Final" (0020)
159.00      AND         ANNOUNCEMENT    ANNTYPE =   "New" (19)
159.20      AND         AVAIL   STATUS  =   "Final" (0020)
159.22      AND         PRODSTRUCT  STATUS  =   "Final" (0020)
Add 2011-10-20		159.230	R1.0	AND			PRODSTRUCT	PDHDOMAIN	IN	XCC_LIST
159.24      SET         ANNOUNCEMENT                WWPRTABRSTATUS      &ABRWAITODS2
160.00      END 157.00


160.10      ELSE    151.04
160.12      SET         PRODSTRUCT              ADSABRSTATUS    &ADSFEEDRFR &ADSFEED
160.14      END 151.04
161.00      END 151.00  PRODSTRUCT

     */
    /**********************************
     * complete abr processing after status moved to readyForReview; (status was chgreq)
     * C.   Status changed to Ready for Review
151.00      PRODSTRUCT

151.01      IF      WWSEOPRODSTRUCT-u   WWSEO   STATUS  =   "Final" (0020)
151.02      AND     WWSEOLSEO-d LSEO    STATUS  =   "Final" (0020)
151.03      Perform         LSEO                        PropagateProdstructWarr ps=final
151.04      ELSE    151.01
151.05      IF          LSEO    STATUS  =   "Ready for Review" (0040)
151.06      AND         LSEO    LIFECYCLE   =   "Develop" (LF02)  | "Plan" (LF01)
151.07      AND         WWSEO   STATUS  =   "Final" (0020) | "Ready for Review" (0040)
151.08      Perform         LSEO                    PropagateProdstructWarr  ps=rfr
151.09      END 151.01

151.17      IF      PRODSTRUCT-d    MODEL   ANNDATE >   "2010-03-01"
Delete 2011-10-20	151.18      OR          MODEL   PDHDOMAIN   IN  XCC_LIST
151.20      IF          PRODSTRUCT  STATUS  =   "Ready for Review" (0040)
151.22      AND         PRODSTRUCT  LIFECYCLE   =   "Develop" (LF02)  | "Plan" (LF01)
151.24      IF      PRODSTRUCT-u    FEATURE FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)
151.26      SET         PRODSTRUCT              ADSABRSTATUS    &ADSFEEDRFR
151.28      ELSE    151.24
151.30      IF      OOFAVAIL-d: AVAILANNA   AVAIL   STATUS  =   "Final" (0020) | "Ready for Review" (0040)
151.31	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
151.32		OR			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
151.34		SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
151.35	R1.0	END	151.31							

151.36      END 151.30
151.38      END 151.24
151.40      END 151.20
Final processing...
160.10      ELSE    151.17
160.12      SET         PRODSTRUCT              ADSABRSTATUS    &ADSFEEDRFR &ADSFEED
160.14      END 151.17
161.00      END 151.00  PRODSTRUCT
     */
    protected void completeNowR4RProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        EntityGroup eg = m_elist.getEntityGroup("FEATURE");
        EntityItem eiFEATURE = eg.getEntityItem(0);

        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
        EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);

        boolean oldData = this.isOldData(mdlItem, "ANNDATE");
     //   boolean inxcclist = domainInRuleList(mdlItem, "XCC_LIST");
        addDebug("nowRFR: "+mdlItem.getKey()+" olddata "+oldData);

        //151.04        ELSE    151.01
        //151.05        IF          LSEO    STATUS  =   "Ready for Review" (0040)
        //151.06        AND         LSEO    LIFECYCLE   =   "Develop" (LF02)  | "Plan" (LF01)
        //151.07        AND         WWSEO   STATUS  =   "Final" (0020) | "Ready for Review" (0040)
        //151.08        Perform         LSEO                    PropagateProdstructWarr
        propagateProdstructWarr(rootEntity, mdlItem,false);

        //151.09        END 151.01
        //151.17        IF      PRODSTRUCT-d    MODEL   ANNDATE >   "2010-03-01"
        //Delete 2011-10-20	151.18        OR          MODEL   PDHDOMAIN   IN  XCC_LIST
        if (!oldData)// || inxcclist)
        {
//            String lifecycle = getAttributeFlagEnabledValue(rootEntity, "LIFECYCLE");
//            addDebug("nowRFR: "+rootEntity.getKey()+" LIFECYCLE "+lifecycle);
//            if (lifecycle==null || lifecycle.length()==0){
//                lifecycle = LIFECYCLE_Plan;
//            }
//            //151.20        IF          PRODSTRUCT  STATUS  =   "Ready for Review" (0040)
            //20130904 Delete 151.22        AND         PRODSTRUCT  LIFECYCLE   =   "Develop" (LF02)  | "Plan" (LF01)
//            if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//                    LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
                //151.24        IF      PRODSTRUCT-u    FEATURE FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)
                if (isRPQ){
                    addDebug(eiFEATURE.getKey()+" was an RPQ FCTYPE: "+getAttributeFlagEnabledValue(eiFEATURE, "FCTYPE"));
                    //151.26        SET         PRODSTRUCT              ADSABRSTATUS    &ADSFEEDRFR
                    setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
                }else{
                    addDebug(eiFEATURE.getKey()+" was not an RPQ FCTYPE: "+getAttributeFlagEnabledValue(eiFEATURE, "FCTYPE"));
                    //151.28        ELSE    151.24
                    //151.30        IF      OOFAVAIL-d: AVAILANNA   AVAIL   STATUS  =   "Final" (0020) | "Ready for Review" (0040)
                    //151.31	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
                    //151.32		OR			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
                    //151.34		SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
                     doR4R_R10Processing(rootEntity,"OOFAVAIL");
                    //151.35	R1.0	END	151.31	
                    //151.36        END 151.30
                    //151.38        END 151.24
                }
//            }

            //151.40        END 151.20
        }else{
            //160.10        ELSE    151.17
            //160.12        SET         PRODSTRUCT              ADSABRSTATUS    &ADSFEEDRFR &ADSFEED
            setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
            //160.14        END 151.17
        }
        //161.00        END 151.00  PRODSTRUCT
    }

    /* (non-Javadoc)
     * update LIFECYCLE value when STATUS is updated
     * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.eannounce.objects.EntityItem, java.lang.String)
     */
    protected String getLCRFRWFName(){ return "WFLCPRODSTRFR";}
    protected String getLCFinalWFName(){ return "WFLCPRODSTFINAL";}

    /**********************************
     * complete abr processing after status moved to final; (status was chgreq)
     *  STATUS changed to Final
151.00      PRODSTRUCT

151.01      IF      WWSEOPRODSTRUCT-u   WWSEO   STATUS  =   "Final" (0020)
151.02      AND     WWSEOLSEO-d LSEO    STATUS  =   "Final" (0020)
151.03      Perform         LSEO                        PropagateProdstructWarr ps=final
151.04      ELSE    151.01
151.05      IF          LSEO    STATUS  =   "Ready for Review" (0040)
151.06      AND         LSEO    LIFECYCLE   =   "Develop" (LF02)  | "Plan" (LF01)
151.07      AND         WWSEO   STATUS  =   "Final" (0020) | "Ready for Review" (0040)
151.08      Perform         LSEO                    PropagateProdstructWarr  ps=rfr
151.09      END 151.01

151.17      IF      PRODSTRUCT-d    MODEL   ANNDATE >   "2010-03-01"
Delete 2011-10-20	151.18      OR          MODEL   PDHDOMAIN   IN  XCC_LIST
RFR processing...
152.00      IF          PRODSTRUCT  STATUS  =   "Final" (0020)
152.02      IF      PRODSTRUCT-u    FEATURE FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)
152.04      SET         PRODSTRUCT              ADSABRSTATUS        &ADSFEED
152.06      ELSE    152.02
152.08      IF      OOFAVAIL-d: AVAILANNA   AVAIL   STATUS  =   "Final" (0020)
152.09		IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)			
152.10		OR			ANNOUNCEMENT	STATUS	=	"Final" (0020)			
152.12		SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED
152.13		END	152.09								

152.14      END 152.08
152.16      END 152.02
152.18      END 152.00
153.00      IF      PRODSTRUCT-u    FEATURE FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)
Add 2011-10-20		153.200	R1.0	AND			PRODSTRUCT	PDHDOMAIN	IN	XCC_LIST
154.00      SET         PRODSTRUCT              WWPRTABRSTATUS      &ABRWAITODS2
155.00      Perform         EXTRACTRPQ                      DeriveEXTRACTRPQ
156.00      END 153.00
157.00      IF      PRODSTRUCT-u    FEATURE FCTYPE  =   Primary FC (100) | "Secondary FC" (110)
158.00      AND     OOFAVAIL-d: AVAILANNA   ANNOUNCEMENT    STATUS  =   "Final" (0020)
159.00      AND         ANNOUNCEMENT    ANNTYPE =   "New" (19)
159.20      AND         AVAIL   STATUS  =   "Final" (0020)
159.22      AND         PRODSTRUCT  STATUS  =   "Final" (0020)
Add 2011-10-20		159.230	R1.0	AND			PRODSTRUCT	PDHDOMAIN	IN	XCC_LIST
159.24      SET         ANNOUNCEMENT                WWPRTABRSTATUS      &ABRWAITODS2
160.00      END 157.00


160.10      ELSE    151.17
160.12      SET         PRODSTRUCT              ADSABRSTATUS    &ADSFEEDRFR &ADSFEED
160.14      END 151.17
161.00      END 151.00  PRODSTRUCT
     */
    protected void completeNowFinalProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
        EntityGroup eg = m_elist.getEntityGroup("FEATURE");
        EntityItem eiFEATURE = eg.getEntityItem(0);

        EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);

        boolean oldData = this.isOldData(mdlItem, "ANNDATE");
       // boolean inxcclist = domainInRuleList(mdlItem, "XCC_LIST");
        addDebug("nowFinal: "+mdlItem.getKey()+" olddata "+oldData);

        //151.00        PRODSTRUCT
        //151.01        IF      WWSEOPRODSTRUCT-u   WWSEO   STATUS  =   "Final" (0020)
        //151.02        AND     WWSEOLSEO-d LSEO    STATUS  =   "Final" (0020)
        //151.03        Perform         LSEO                        PropagateProdstructWarr
        propagateProdstructWarr(rootEntity, mdlItem,true);

        //151.17        IF      PRODSTRUCT-d    MODEL   ANNDATE >   "2010-03-01"
        //Delete 2011-10-20	151.18        OR          MODEL   PDHDOMAIN   IN  XCC_LIST
        if (!oldData)// || inxcclist)
        {
            //152.00        IF          PRODSTRUCT  STATUS  =   "Final" (0020)
            if (isRPQ){
                addDebug(eiFEATURE.getKey()+" was an RPQ FCTYPE: "+getAttributeFlagEnabledValue(eiFEATURE, "FCTYPE"));
                //152.02        IF      PRODSTRUCT-u    FEATURE FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)
                //152.04        SET         PRODSTRUCT              ADSABRSTATUS        &ADSFEED
                setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
                //152.020	IF		PRODSTRUCT-u	FEATURE	FCTYPE	<>	Primary FC (100) | "Secondary FC" (110)
                //152.040	SET			PRODSTRUCT				RFCABRSTATUS		&OIMSFEED
                setFlagValue(m_elist.getProfile(),"RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
            }else{
//                boolean adsqueued = false;
                //152.06        ELSE    152.02
                addDebug(eiFEATURE.getKey()+" was NOT an RPQ FCTYPE: "+getAttributeFlagEnabledValue(eiFEATURE, "FCTYPE"));

                //157.00        IF      PRODSTRUCT-u    FEATURE FCTYPE  =   Primary FC (100) | "Secondary FC" (110)
                Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "OOFAVAIL", "AVAIL");
    			Vector finalAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "STATUS", STATUS_FINAL);
    			addDebug("nowFinal:  availVct "+availVct.size()+" finalAvailVct "+finalAvailVct.size());
//    			20121210 Delete		152.090		IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)					
//    			20121210 Delete		152.100		OR			ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020)					
//    					152.120		SET			PRODSTRUCT				ADSABRSTATUS		&ADSFEED		
//    			20121210 Delete		152.130		END	152.090	
    			if(finalAvailVct.size() > 0){
    				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
    				//152.080	IF		OOFAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020)
    				//152.120	SET			PRODSTRUCT				RFCABRSTATUS		&OIMSFEED
    				setFlagValue(m_elist.getProfile(),"RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
//                    adsqueued = true;
    			}
                //158.00        AND     OOFAVAIL-d: AVAILANNA   ANNOUNCEMENT    STATUS  =   "Final" (0020)
                //159.20        AND         AVAIL   STATUS  =   "Final" (0020)
                //159.22        AND         PRODSTRUCT  STATUS  =   "Final" (0020)
    			//Add 2011-10-20		159.230	R1.0	AND			PRODSTRUCT	PDHDOMAIN	IN	XCC_LIST
    			for(int x=0; x<finalAvailVct.size(); x++){
    				EntityItem avail = (EntityItem)finalAvailVct.elementAt(x);
    				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
    				if (availAnntypeFlag==null){
    					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
    				}
    				addDebug("nowfinal: final "+avail.getKey()+" availanntype "+availAnntypeFlag);

//    				//152.09	IF		AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
//    				if(!adsqueued && !AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//                        //152.12        SET     PRODSTRUCT      ADSABRSTATUS        &ADSFEED
//                        setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
//                        adsqueued = true;
//                        // 152.13	END	152.09	
//    				}
    				
    				Vector annVct = PokUtils.getAllLinkedEntities(finalAvailVct, "AVAILANNA", "ANNOUNCEMENT");
    				Vector finalAnnVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNSTATUS", STATUS_FINAL);
    				addDebug("nowfinal:  annVct "+annVct.size()+" finalAnnVct "+finalAnnVct.size());
    				for (int i=0; i<finalAnnVct.size(); i++){
    					EntityItem annItem = (EntityItem)finalAnnVct.elementAt(i);
    					String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
    		            //159.00        AND         ANNOUNCEMENT    ANNTYPE =   "New" (19)
    					//Add 2011-10-20		159.230	R1.0	AND			PRODSTRUCT	PDHDOMAIN	IN	XCC_LIST
    					if(ANNTYPE_NEW.equals(anntype) &&
    							domainInRuleList(rootEntity, "XCC_LIST")){
    						addDebug("nowFinal "+annItem.getKey()+" is Final and New and prodstruct.domain in xcc");
    						//159.24        SET         ANNOUNCEMENT                WWPRTABRSTATUS      &ABRWAITODS2
    						setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", getQueuedValueForItem(annItem,"WWPRTABRSTATUS"),annItem);
    					}	
//    					if(!adsqueued){
//    						//152.08        IF      OOFAVAIL-d: AVAILANNA   AVAIL   STATUS  =   "Final" (0020)
//    						//152.10		OR			ANNOUNCEMENT	STATUS	=	"Final" (0020)	
//    						//152.12        SET     PRODSTRUCT      ADSABRSTATUS        &ADSFEED
//    						setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
//    						adsqueued = true;
//    					}
    					// TODO
    					if (isQsmANNTYPE(anntype)) {
    						setFlagValue(m_elist.getProfile(), "QSMCREFABRSTATUS", getQueuedValueForItem(annItem,"QSMCREFABRSTATUS"), annItem);
    						setFlagValue(m_elist.getProfile(), "QSMFULLABRSTATUS", getQueuedValueForItem(annItem,"QSMFULLABRSTATUS"), annItem);
    					}    					
    				}//end annloop
    				annVct.clear();
    				finalAnnVct.clear();
    			} //end final avail loop
    			availVct.clear();
    			finalAvailVct.clear();
                //152.16    END 152.02
                //160.00    END 157
            }
            //  152.18      END 152.00
        }else{
            //160.10        ELSE    151.17
            //160.12        SET         PRODSTRUCT              ADSABRSTATUS    &ADSFEEDRFR &ADSFEED
            setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
            //160.14        END 151.17
            //160.120	SET			PRODSTRUCT				RFCABRSTATUS	&OIMSFEED
            setFlagValue(m_elist.getProfile(),"RFCABRSTATUS", getQueuedValue("RFCABRSTATUS"));
        }
        // Moved 2012-01-11 moved outside of old data check - IN1729992
        if (isRPQ){
            //153.00        IF      PRODSTRUCT-u    FEATURE FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)
            //153.200		AND			PRODSTRUCT	PDHDOMAIN	IN	XCC_LIST
            if(domainInRuleList(rootEntity, "XCC_LIST")){
            	//154.00    SET         PRODSTRUCT              WWPRTABRSTATUS  norfr   &ABRWAITODS2
            	setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", getQueuedValue("WWPRTABRSTATUS"));
            	//155.00    Perform         EXTRACTRPQ          norfr           DeriveEXTRACTRPQ
            	deriveEXTRACTRPQ(eiFEATURE); // support 30b
            	//156.00        END 153.00
            }           
        }
        boolean isQSMRPQ = isQSMRPQ(eiFEATURE);
        if (isQSMRPQ) {
        	 // TODO
            /*
    		 * Trigger the following attributes when FEATURE , PRODSTRUCTs or a MODEL goes to Final.
    		 * FEATURE.QSMRPQCREFABRSTATUS
    		 * FEATURE.QSMRPQFULLABRSTATUS
    		 * So the Attriubtes are on FEATURE and everytime MODEL, FEATURE and/or PRODSTRUCTs goes to final u triger these two ABRs on a feature entity.
    		 * This only applies when FEATURE.FCTYPE='RPQILISTED','RPQPLISTED','RPQRLISTED'
    		 */
    		addDebug("completeNowFinalProcessing - isQSMRPQ " + isQSMRPQ);    			
			setFlagValue(m_elist.getProfile(), "QSMRPQCREFABRSTATUS", getQueuedValueForItem(eiFEATURE, "QSMRPQCREFABRSTATUS"), eiFEATURE);
			setFlagValue(m_elist.getProfile(), "QSMRPQFULLABRSTATUS", getQueuedValueForItem(eiFEATURE, "QSMRPQFULLABRSTATUS"), eiFEATURE);    		
        }
        //160.00    END 157
        //  161.00  END 151 PRODSTRUCT
    }

    /**************
     *
EE. PropagateProdstructWarr

The relator PRODSTRUCTWARR is locked if PRODSTUCT STATUS = {Ready for Review | Final}.
Therefore a change to PRODSTRUCTWARR will require PRODSTRUCT to undergo a change in STATUS
to Change Request and return to Ready for Review and then Final. Therefore the DQ ABR for PRODSTRUCT will be queued.

The SETS spreadsheet will use this function if the PRODSTRUCT passes all of the data quality checks.

A second EXTRACT for a small VE will be performed for the prior time that the DQ ABR Passed. The VE is:

 PRODSTRUCTWARR:D:0

A change of interest may be any one or more of the following:
�   PRODSTRUCTWARR (i.e. add or remove a relator of this type)
�   EFFECTIVEDATE (attribute on PRODSTRUCTWARR)
�   ENDDATE (attribute on PRODSTRUCTWARR)
�   COUNTRYLIST (attribute on PRODSTRUCTWARR)
�   DEFWARR (ATTRIBUTE on PRODSTRUCTWARR)

If there is a change of interest, then Queue grandchildren LSEOs. They are all of the LSEOs in the path
WWSEOPRODSTRUCT-u: WWSEOLSEO-d starting with the root PRODSTRUCT. 
Only LSEOs where LSEO Unpublish Date - Target (LSEOUNPUBDATEMTRGT) > NOW() are considered.
There will be logic that considers the STATUS of the WWSEO and the LSEO.
     * @throws MiddlewareException
     * @throws SQLException
     * @throws MiddlewareRequestException
     */
    private void propagateProdstructWarr(EntityItem psitem, EntityItem mdlitem, boolean isFinal)
    throws MiddlewareRequestException, SQLException, MiddlewareException
    {
        // pull ve for model-wwseo-lseo
        String VeName = "DQVEMDLLSEO";
        wwseoList = m_db.getEntityList(m_elist.getProfile(),
                new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
                new EntityItem[] {new EntityItem(null, m_elist.getProfile(), mdlitem.getEntityType(), mdlitem.getEntityID())});
        addDebug("propagateProdstructWarr: strnow: "+getCurrentDate()+" Extract "+VeName+NEWLINE+PokUtils.outputList(wwseoList));
        EntityGroup wwseoGrp = wwseoList.getEntityGroup("WWSEO");
        Vector validLSEOVct = new Vector();
        if(isFinal){
            //151.00        PRODSTRUCT
            if(wwseoGrp.getEntityItemCount()>0){
                for (int i=0; i<wwseoGrp.getEntityItemCount(); i++){
                    EntityItem wwseo = wwseoGrp.getEntityItem(i);
                    String seoordercode = getAttributeFlagEnabledValue(wwseo, "SEOORDERCODE");
                    addDebug(wwseo.getKey()+" SEOORDERCODE: "+seoordercode);
                    // deleted Only WWSEOs where SEOORDERCODE = �MES�
                    //  151.01      IF      WWSEOPRODSTRUCT-u   WWSEO   STATUS  =   "Final" (0020)
                    //if(SEOORDERCODE_MES.equals(seoordercode) && 
                    if(statusIsFinal(wwseo)){
                        Vector lseoVct = PokUtils.getAllLinkedEntities(wwseo, "WWSEOLSEO", "LSEO");
                        for (int l=0; l<lseoVct.size(); l++){
                            EntityItem lseo = (EntityItem)lseoVct.elementAt(l);
                            String unpubdate = PokUtils.getAttributeValue(lseo, "LSEOUNPUBDATEMTRGT", "", FOREVER_DATE, false);
                            addDebug("propagateProdstructWarr: "+lseo.getKey()+" unpubdate "+unpubdate);
                            //151.02        AND     WWSEOLSEO-d LSEO    STATUS  =   "Final" (0020)
                            //Only LSEOs where LSEO Unpublish Date - Target (LSEOUNPUBDATEMTRGT) > NOW() or Empty are considered.
                            if(statusIsFinal(lseo) && unpubdate.compareTo(getCurrentDate())>0){
                                //151.03        Perform         LSEO                        PropagateProdstructWarr
                                validLSEOVct.add(lseo);
                            }
                        }
                    }
                }
            }
        }else{
            //151.04        ELSE    151.01
            if(wwseoGrp.getEntityItemCount()>0){
                for (int i=0; i<wwseoGrp.getEntityItemCount(); i++){
                    EntityItem wwseo = wwseoGrp.getEntityItem(i);
                    String seoordercode = getAttributeFlagEnabledValue(wwseo, "SEOORDERCODE");
                    addDebug(wwseo.getKey()+" SEOORDERCODE: "+seoordercode);
                    // Only WWSEOs where SEOORDERCODE = �MES�
                    //151.07        AND         WWSEO   STATUS  =   "Final" (0020) | "Ready for Review" (0040)
                   //if(SEOORDERCODE_MES.equals(seoordercode) && 
                    if(statusIsRFRorFinal(wwseo)){
                        Vector lseoVct = PokUtils.getAllLinkedEntities(wwseo, "WWSEOLSEO", "LSEO");
                        for (int l=0; l<lseoVct.size(); l++){
                            EntityItem lseo = (EntityItem)lseoVct.elementAt(l);
                            String unpubdate = PokUtils.getAttributeValue(lseo, "LSEOUNPUBDATEMTRGT", "", FOREVER_DATE, false);
                            addDebug("propagateProdstructWarr: "+lseo.getKey()+" unpubdate "+unpubdate);
                            //Only LSEOs where LSEO Unpublish Date - Target (LSEOUNPUBDATEMTRGT) > NOW() or Empty are considered.
                            //151.05        IF          LSEO    STATUS  =   "Ready for Review" (0040)
                            if(statusIsRFR(lseo) && unpubdate.compareTo(getCurrentDate())>0){
//                                String lifecycle = PokUtils.getAttributeFlagValue(lseo, "LIFECYCLE");
//                                addDebug("propagateProdstructWarr: "+lseo.getKey()+" lifecycle "+lifecycle);
//                                if (lifecycle==null || lifecycle.length()==0){
//                                    lifecycle = LIFECYCLE_Plan;
//                                }
                                //20130904 Delete 151.06       AND             LSEO    LIFECYCLE   =   "Develop" (LF02)  | "Plan" (LF01)
//                                if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//                                        LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
//                                    //151.08        Perform         LSEO                    PropagateProdstructWarr
                                    validLSEOVct.add(lseo);
//                                }
                            }
                        }
                    }
                }
            }
            //151.09        END 151.01
        }
        if(validLSEOVct.size()>0){
            propagateProdstructWarr(psitem, validLSEOVct,isFinal);
            validLSEOVct.clear();
        }
    }
    /**
     *
A second EXTRACT for a small VE will be performed for the prior time that the DQ ABR Passed. The VE is:

 PRODSTRUCTWARR:D:0

A change of interest may be any one or more of the following:
�   PRODSTRUCTWARR (i.e. add or remove a relator of this type)
�   EFFECTIVEDATE (attribute on PRODSTRUCTWARR)
�   ENDDATE (attribute on PRODSTRUCTWARR)
�   COUNTRYLIST (attribute on PRODSTRUCTWARR)
�   DEFWARR (ATTRIBUTE on PRODSTRUCTWARR)

If there is a change of interest, then Queue grandchildren LSEOs.
     * @param psitem
     * @param lseoVct
     * @param queueFinal
     * @throws MiddlewareRequestException
     * @throws MiddlewareException
     * @throws SQLException
     */
    private void propagateProdstructWarr(EntityItem psitem, Vector lseoVct, boolean queueFinal) throws MiddlewareRequestException, MiddlewareException, SQLException
    {
        boolean hadchgs = false;
        String passedDTS = getPrevPassedDTS(psitem, "PRODSTRUCTABRSTATUS");
        if (passedDTS!=null){
            // only changes since moving to rfr or final are considered.. so first time is ignored
            hadchgs = changeOfInterest(psitem, passedDTS, m_elist.getProfile().getValOn(),
                    "DQVEPSWARR", ATTR_OF_INTEREST_TBL);
        }
        if(hadchgs){
            for (int i=0; i<lseoVct.size(); i++){
                EntityItem lseoItem = (EntityItem)lseoVct.elementAt(i);
                if(queueFinal){
                    setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(lseoItem,"ADSABRSTATUS"),lseoItem);
                }else{
                    setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(lseoItem,"ADSABRSTATUS"),lseoItem);
                }
            }
        }
    }

    /**********************************
     * generate string representation of attributes in the list for this entity
     * @param theItem
     * @param attrlist
     * @return
     */
    protected String generateString(EntityItem theItem, String[] attrlist){
        if(theItem.getEntityType().equals("PRODSTRUCTWARR")){
            // check if this is DEFWARR
            String defwarr = PokUtils.getAttributeFlagValue(theItem, "DEFWARR");
            addDebug("generateString: "+theItem.getKey()+" defwarr "+defwarr);
            if(DEFWARR_Yes.equals(defwarr) || defwarr == null){
                StringBuffer sb = new StringBuffer(theItem.getKey());
                for (int a=0; a<attrlist.length; a++){
                    String value = PokUtils.getAttributeValue(theItem, attrlist[a],", ", "", false);
                    if(DEFWARR_Yes.equals(defwarr) && attrlist[a].equals("COUNTRYLIST")){ // dont look at country changes if this is a ww default
                        value="DEFWARR";
                    }
                    if(attrlist[a].equals("DEFWARR")&& defwarr==null){ // dont look at defwarr null to no chgs
                        value=DEFWARR_No_Desc;
                    }
                    sb.append(":"+value);
                }
                return sb.toString();
            }
        }

        return super.generateString(theItem, attrlist);
    }
    
//    JJ.	SetBulkMesIndc
//
//    This is runs for a PRODSTRUCT.
//
//    It runs prior to any checking identified in the Checks spreadsheet.
//
//    It is applicable if
//    1.	BULKMESINDC is empty (aka null)
//    2.	The value of PRODSTRUCT PDHDOMAIN is in PRODSTRUCTABRSTATUS_domainList_MESIND_List
//
//    This is a new List found in the ABR properties file. If there are no values found, then this function does not apply.
//
//    IF ORDERCODE = “MES” (5956) or “Both” (5955)
//    THEN Set BULKMESINDC = “Yes” (MES0001)
//    ELSE do nothing.
//
//    This function does not produce any errors.
    private void setBulkMesIndc(EntityItem item){
    	String domains = getDomainList(item, "MESIND_List");
    	if(domains.trim().length()>0 && !domains.equals("all") && domainInList(item,domains)){
    		String bulkMesIndc = PokUtils.getAttributeFlagValue(item, "BULKMESINDC");
    		if(bulkMesIndc == null){
    			String ordercode = PokUtils.getAttributeFlagValue(item, "ORDERCODE");
    			addDebug(item.getKey()+" ORDERCODE "+ordercode);
    			if(ORDERCODE_BOTH.equals(ordercode) || ORDERCODE_MES.equals(ordercode)){
    				//set BULKMESINDC = “Yes” (MES0001)
    				addDebug(item.getKey()+" set BULKMESINDC = “Yes” (MES0001) ");
    				setFlagValue(m_elist.getProfile(), "BULKMESINDC", "MES0001", item);
    			}
    		}
    	}
    }
    
	/**
	 * PRODSTRUCT may derive BULKMESINDC that must be committed even though ABR fails
	 * @param rek
	 */
	protected void removeAttrBeforeCommit(ReturnEntityKey rek){
		Attribute bulkmesindAttr = null;
		for (int ii=0; ii<rek.m_vctAttributes.size(); ii++){
			Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(ii);
			if(attr.getAttributeCode().equals("BULKMESINDC")){
				bulkmesindAttr = attr;
				break;
			}
		}
		rek.m_vctAttributes.clear();
		// put this one back
		if(bulkmesindAttr!=null){
			addDebug("add BUILKMESINDC back");
			rek.m_vctAttributes.addElement(bulkmesindAttr);
		}
	}

    /**********************************
     *
     *
A.  Checking
from ss
    1.00    PRODSTRUCT      Root
    2.00            ANNDATE
    3.00            GENAVAILDATE    =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: GENAVAILDATE} {GENAVAILDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
    4.00            WITHDRAWDATE    =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: WITHDRAWDATE} {WITHDRAWDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
    5.00            WTHDRWEFFCTVDATE    =>  PRODSTRUCT  WITHDRAWDATE        W   W   E   Not used on GA Features {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE} can not be earlier than {LD: WITHDRAWDATE} {WITHDRAWDATE}
5.02			PRODSTRUCT-d									
Delete 20110322 5.04	IF		"Service" (102)	<>	MODEL	COFCAT						
5.06			"00 - No Product Hierarchy Code" (BHPH0000)	<>	PRODSTRUCT	BHPRODHIERCD		E	E	E		must not have {LD: BHPRODHIERCD} {BHPRODHIERCD}
Delete 20110322 5.08	END	5.04										
 
    6.00    FEATURE     PRODSTRUCT-u                                FEATURE
    7.00            STATUS  =>  PRODSTRUCT  DATAQUALITY     E   E   E   All Features    {LD: STATUS} can not be higher than the {LD: FEATURE} {NDN: FEATURE}
    8.00    WHEN        FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)
    9.00            FIRSTANNDATE    <=  PRODSTRUCT  ANNDATE     W   W   E       {LD: ANNDATE} must not be earlier than the {LD: FEATURE} {NDN:FEATURE}{LD: FIRSTANNDATE} {FIRSTANNDATE}
    10.00           GENAVAILDATE    <=  PRODSTRUCT  GENAVAILDATE        W   W   E       {LD: GENAVAILDATE} must not be earlier than the {LD: FEATURE} {NDN:FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
    11.00           WITHDRAWANNDATE_T   =>  PRODSTRUCT  WITHDRAWDATE        W   W   E       {LD: WITHDRAWDATE} must not be later than {LD: FEATURE}  {NDN:FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
    12.00           WITHDRAWDATEEFF_T   =>  PRODSTRUCT  WTHDRWEFFCTVDATE        W   W   E       {LD: WTHDRWEFFCTVDATE} must not be later than {LD: FEATURE}  {NDN:FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
    12.20   AVAIL       PRODSTRUCT-u:OOFAVAIL-d
    12.30           CountOf =   0           E   E   E       RPQs must not have an {LD: AVAIL} {NDN: AVAIL}
    13.00   END 8.00
    14.00   FEATURE     PRODSTRUCT-u                                FEATURE
    15.00   WHEN        FCTYPE  =   Primary FC (100) | "Secondary FC" (110) GA Logic
    16.00   AVAIL   A   PRODSTRUCT-u:OOFAVAIL-d                             Feature AVAIL
    17.00   WHEN        AVAILTYPE   =   "Planned Availability"
Change 20151201	18.00   IF		PRODSTRUCT-u:OOFAVAIL-d	=	"Planned Availability" or "First Order"
							CountOf =>  1           RE*1    RE*1    RE*1        must have at lease one "Planned Availability"
Change 20160126	17.00   WHEN        AVAILTYPE   =   "Planned Availability"  or "MES Planned Availability"
    19.00           EFFECTIVEDATE   =>  FEATURE FIRSTANNDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    20.00           EFFECTIVEDATE   =>  FEATURE GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {NDN:FEATURE}{LD: GENAVAILDATE} {GENAVAILDATE}
    21.00           EFFECTIVEDATE   =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    22.00           EFFECTIVEDATE   =>  PRODSTRUCT  GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
    23.00           EFFECTIVEDATE   =>  MODEL   ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL}{LD: ANNDATE} {ANNDATE}
    24.00           EFFECTIVEDATE   =>  MODEL   GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
    25.00           COUNTRYLIST in  FEATURE COUNTRYLIST     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
Add 25.80   WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
Add 25.82   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
Add 25.84           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
Add 25.86   END 25.80
Add 25.88   WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    26.00   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
    27.00   IF      ANNTYPE =   "New" (19)
    28.00   THEN        ANNDATE =>  FEATURE FIRSTANNDATE        W   RW  RE      {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    29.00           ANNDATE =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    30.00           ANNDATE =>  MODEL   ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: ANNDATE} {ANNDATE}
    30.20   ELSE    27.00   ANNTYPE <>  "New" (19)
    30.40           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    31.00   END 27.00
Add 31.40   END 25.88
    32.00   END 17.00
    33.00   AVAIL   D   PRODSTRUCT-u:OOFAVAIL-d                             Feature AVAIL
    34.00   WHEN        AVAILTYPE   =   "First Order"
    35.00           EFFECTIVEDATE   =>  FEATURE FIRSTANNDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {ANNDATE}
Delete 20151201	36.00           EFFECTIVEDATE   =>  FEATURE GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
    37.00           EFFECTIVEDATE   =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
Delete 20110318    38.00           EFFECTIVEDATE   =>  PRODSTRUCT  GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
    39.00           EFFECTIVEDATE   =>  MODEL   ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL}{LD: ANNDATE} {ANNDATE}
Delete 20151201	40.00           EFFECTIVEDATE   =>  MODEL   GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
    xx41.00           COUNTRYLIST "IN aggregate G"    A: AVAIL    COUNTRYLIST     W   W   E       {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	41.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
    200.00  ANNOUNCEMENT        D: + AVAILANNA                              Feature Announcement
    201.00  IF      ANNTYPE =   "New" (19)
    202.00  THEN        ANNDATE =>  FEATURE FIRSTANNDATE        W   RW  RE      {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    203.00          ANNDATE =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    204.00          ANNDATE =>  MODEL   ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: ANNDATE} {ANNDATE}
    205.00  ELSE        ANNTYPE <>  "New" (19)
    206.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    207.00  END 201.00
    42.00   END 34.00
    43.00   AVAIL   B   PRODSTRUCT-u:OOFAVAIL-d                             Feature AVAIL
Change 20160126	44.00   WHEN        AVAILTYPE   =   "Last Order" or "MES Last Order"
    46.00           EFFECTIVEDATE   <=  FEATURE WITHDRAWDATEEFF_T       W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE}{NDN:FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
    48.00           EFFECTIVEDATE   <=  PRODSTRUCT  WTHDRWEFFCTVDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
49.00	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    50.00           EFFECTIVEDATE   <=  MODEL   WTHDRWEFFCTVDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL} {NDN:MODEL} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
50.20	END	49.00
    51.00           COUNTRYLIST in  FEATURE COUNTRYLIST     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
    52.00xx           COUNTRYLIST "IN aggregate G"    A: AVAIL    COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	52.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
    54.00   IF          Not Null    FEATURE WITHDRAWDATEEFF_T
    56.00   OR          Not Null    PRODSTRUCT  WTHDRWEFFCTVDATE
    58.00   OR          Not Null    MODEL   WTHDRWEFFCTVDATE
    xx59.00   THEN        COUNTRYLIST Contains    A: AVAIL    COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: A: AVAIL} includes a Country that needs to have a "Last Order" {LD: AVAIL}
Change 20111216	59.00	THEN		COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that needs to have a "Last Order" {LD: AVAIL}
Add 59.20   WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
Add 59.22   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
Add 59.24           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
Add 59.26   END 59.20
Add 59.28   WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    60.00   ANNOUNCEMENT        B: + AVAILANNA                              Feature Announcement
    61.00   IF      ANNTYPE =   End Of Life - Withdrawal from mktg (14)
62.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: FEATURE}{NDN:FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
63.00			ANNDATE	<=	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
63.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE						
64.00			ANNDATE	<=	MODEL	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: MODEL}{NDN:MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
64.10	END	63.80
	64.20   ELSE        ANNTYPE <>  End Of Life - Withdrawal from mktg (14)
    64.40           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    65.00   END 61.00
Add 65.40   END 59.28
    66.00   END 44.00
    250.00  AVAIL   J   PRODSTRUCT-u:OOFAVAIL-d                             PRODSTRUCT AVAIL
    251.00  WHEN        AVAILTYPE   =   "End of Marketing" (200)
    252.00          EFFECTIVEDATE   <=  FEATURE WITHDRAWANNDATE_T       W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
252.20			EFFECTIVEDATE	<=	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}

252.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    253.00          EFFECTIVEDATE   <=  MODEL   WITHDRAWDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
253.20	END	252.80
    xx254.00          COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	254.00			COUNTRYLIST	"in aggregate G"	A:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
    255.00  WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
    256.00  ANNOUNCEMENT        J: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    257.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    258.00  END 255.00
    259.00  WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    260.00  ANNOUNCEMENT    K   J: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    261.00  IF      ANNTYPE =   "End Of Life - Withdrawal from mktg" (14)
    262.00  THEN        ANNDATE <=  FEATURE WITHDRAWANNDATE_T       W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
262.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    263.00          ANNDATE <=  MODEL   WITHDRAWDATE        W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
263.20	END	262.800
    264.00  ELSE        ANNTYPE <>  "End Of Life - Withdrawal from mktg" (14)
    265.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    266.00  END 261.00
    267.00  END 259.00
    268.00  END 251.00
    269.00  AVAIL   G   PRODSTRUCT-u:OOFAVAIL-d                             PRODSTRUCT AVAIL
    270.00  WHEN        AVAILTYPE   =   "End of Service" (151)
    xx271.00          EFFECTIVEDATE   =>  B:AVAIL EFFECTIVEDATE   Yes W   W   E*1     {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: B:AVAIL)
    xx272.00          COUNTRYLIST "IN aggregate G"    B:AVAIL COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
Change 20111216	271.00			EFFECTIVEDATE	=>	B:AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: B:AVAIL)
Change 20111216	272.00			COUNTRYLIST	"in aggregate G"	B:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
    273.00  WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
    274.00  ANNOUNCEMENT        G: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    275.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: G:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    276.00  END 273.00
 Delete 20111213   277.00  WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    278.00  ANNOUNCEMENT    H   G: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    279.00  IF      ANNTYPE =   "End Of Life - Discontinuance of service" (13)
    280.00  THEN        ANNDATE <=  FEATURE WITHDRAWDATEEFF_T       W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
280.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    281.00          ANNDATE <=  MODEL   WTHDRWEFFCTVDATE        W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
281.20	END	280.80
    282.00  ELSE        ANNTYPE <>  "End Of Life - Discontinuance of service" (13)
    283.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: G:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    284.00  END 279.00
    285.00  END 277.00 
 end  Delete 20111213  
    286.00  END 270.00
    67.00   AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
    68.00   WHEN        AVAILTYPE   =   "Planned Availability"
    xx69.00           EFFECTIVEDATE   <=  A: AVAIL    EFFECTIVEDATE   Yes W   RW  RE*1    Feature can not be available before the MODEL is announced by country   {LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
    xx70.00           COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
Change 20111216	69		EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
Change 20111216	70.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
    71.00   END 68.00
    72.00   AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
    73.00   WHEN        AVAILTYPE   =   "First Order"
    xx74.00           EFFECTIVEDATE   <=  A: AVAIL    EFFECTIVEDATE   Yes W   RW  RE*1    Feature can not be available before the MODEL is announced by country   {LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
    xx75.00           COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
Change 20111216	74.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
Change 20111216	75.00			COUNTRYLIST	"Contains
aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
    76.00   END 73.00
    76.20   PRODSTRUCT      Root
    76.40   WHEN        "Initial"   =   PRODSTRUCT  ORDERCODE
    77.00   AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
Change 20160126	78.00   WHEN        AVAILTYPE   =   "Last Order" or "MES Last Order"
xx 79.00           EFFECTIVEDATE   =>  B: AVAIL    EFFECTIVEDATE   Yes W   W   E*1 Withdraw the Feature on or before the MODEL is Withdrawn by country {LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
    xx80.00   IF      COUNTRYLIST Match   A: AVAIL    COUNTRYLIST Yes
 xx81.00   THEN        TheMatch    IN  B: AVAIL    COUNTRYLIST Yes E*1 E*1 E*1     must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
 Change 20111216	79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	W	E*1	Withdraw the Feature on or before the MODEL is Withdrawn by country	{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
Change 20111216	80.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST	Ctry OSN:XCC_LIST					
Change 20111216	81.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST	Ctry OSN:XCC_LIST	E*1	E*1	E*1		must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
   82.00   END 78.00
   82.20   END 76.40
Add 20131016 82.30   END 15.00
    83.00   MODEL       PRODSTRUCT-d                                MODEL
    84.00           STATUS  =>  PRODSTRUCT  DATAQUALITY    E*1   E*1   E*1     can not have a status higher than the {LD: MODEL} {LD: STATUS} {STATUS}
    85.00           ANNDATE <=  PRODSTRUCT  ANNDATE     W   E   E       {LD: ANNDATE} must not be earlier than the {LD: MODEL} {NDN:MODEL}{LD: ANNDATE} {ANNDATE}
    86.00           GENAVAILDATE    <=  PRODSTRUCT  GENAVAILDATE        W   E   E       {LD: GENAVAILDATE} must not be earlier than the {LD: MODEL}{NDN:MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
86.40	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    87.00           WITHDRAWDATE    =>  PRODSTRUCT  WITHDRAWDATE        W   E   E       {LD: WITHDRAWDATE} must not be later than {LD: MODEL} {NDN:MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
    88.00           WTHDRWEFFCTVDATE    =>  PRODSTRUCT  WTHDRWEFFCTVDATE        W   E   E       {LD: WTHDRWEFFCTVDATE} must not be later than {LD: MODEL} {NDN:MODEL} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
88.40	END	86.40
    89.00   TMF Error       Action: SRDPRODSTRUCTV   Search Using    PRODSTRUCT-d: MODEL MACHTYPEATR                 TMF Error Checking
    90.00               AND PRODSTRUCT-u: FEATURE   FEATURECODE
    91.00           UniqueWithinMODEL       FEATURE FEATURECODE     E   E   E       {LD: FEATURECODE} {FEATURECODE} is a duplicate within {LD: MODEL} {MODEL.MODELATR}
    92.00           IdenticalWithinMACHTYPE     FEATURE FEATURECODE     E   E   E       {LD: FEATURECODE} {FEATURECODE} is a duplicate within {LD: MACHTYPE} however, it is not an identical {FEATURE}
    93.00   PRODSTRUCT  C   Root                                PRODSTRUCT
    94.00   WHEN        ORDERCODE   =   "MES" (5956)
    95.00   OR      ORDERCODE   =   "Both" (5955)
    96.00   FEATURE     PRODSTRUCT-u
    97.00   WHEN        PRICEDFEATURE   =   "Yes" (100)     E E E       {LD: PRICEDFEATURE} {PRICEDFEATURE} is invalid for {LD: ORDERCODE} {C: ORDERCODE}
    99.00   END 97.00
    100.00  END 94.00
    xx101.00  END 15.00
140.00  WARR        PRODSTRUCTWARR-d
140.10  WHEN        "Hardware" (100)    =   PRODSTRUCT-d: MODEL COFCAT
140.30  IF      CountOf =>  1                       Optional WARR; however, if one exists, then check it
140.40          IdenticalWARR                   E   E   E   see the doc file for an explanation of the check    {LD: PRODSTRUCT} {NDN: PRODSTRUCT} does not have matching {LD: WARR}
140.50  IF      FCTYPE  =   Primary FC (100) | "Secondary FC" (110) If not RPQ
140.60          WARRCoverage                Yes E   E   E   Not Required    {LD: WARR} {NDN: WARR} have gaps in the date range.
140.70          COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} for every country in the {LD: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
140.80          MIN(EFFECTIVEDATE)  <=  A: AVAIL    EFFECTIVEDATE   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an EFFECTIVEDATE  as early as or earlier than the  {LD: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
140.90          MAX(ENDDATE)    =>  B: AVAIL    EFFECTIVEDATE   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an ENDATE as late as or later than the  {LD: PRODSTRUCT} {LD: AVAIL} {NDN: B: AVAIL}
141.00  ELSE    140.50
141.10          WARRCoverage                Yes E   E   E   Not Required    {LD: WARR} {NDN: WARR} have gaps in the date range.
141.20          COUNTRYLIST "Contains aggregate E"  PRODSTRUCT-u: FEATURE   COUNTRYLIST     E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} for every country in the {LD: PRODSTRUCT} {LD: COUNTRYLIST}
141.30          MIN(EFFECTIVEDATE)  <=  PRODSTRUCT-u: FEATURE   GENAVAILDATE    Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an EFFECTIVEDATE as early as or earlier than the  {LD: FEATURE} {LD: GENAVAILDATE} GENAVAILDATE}
141.40          MAX(ENDDATE)    =>  PRODSTRUCT-u: FEATURE   WITHDRAWDATEEFF_T   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an ENDATE as late as or later than the  {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
141.50  END 140.50
141.60  END 140.30
141.70  END 140.10
     */
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
        addHeading(2,rootEntity.getEntityGroup().getLongDescription()+" Checks:");
        
        //set bulkmesindc prior to all checking
        setBulkMesIndc(rootEntity);
        
        int checklvl = getCheck_W_W_E(statusFlag);
        EntityItem featItem = m_elist.getEntityGroup("FEATURE").getEntityItem(0);// has to exist
        EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0); // has to exist

        addDebug("checking "+rootEntity.getKey()+" internal dates keys 3,4,5,7");
        //3.00          GENAVAILDATE    =>  PRODSTRUCT  ANNDATE     W   W   E
        //{LD: GENAVAILDATE} {GENAVAILDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
        checkCanNotBeEarlier(rootEntity, "GENAVAILDATE", "ANNDATE", checklvl);

        //4.00          WITHDRAWDATE    =>  PRODSTRUCT  ANNDATE     W   W   E
        //{LD: WITHDRAWDATE} {WITHDRAWDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
        checkCanNotBeEarlier(rootEntity, "WITHDRAWDATE", "ANNDATE", checklvl);

        //5.00          WTHDRWEFFCTVDATE    =>  PRODSTRUCT  WITHDRAWDATE        W   W   E   NoT used on GA Features
        //{LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE} can not be earlier than {LD: WITHDRAWDATE} {WITHDRAWDATE}
        checkCanNotBeEarlier(rootEntity, "WTHDRWEFFCTVDATE", "WITHDRAWDATE", checklvl);

		String modelCOFCAT = getAttributeFlagEnabledValue(mdlItem, "COFCAT");
		if(modelCOFCAT == null) {
			modelCOFCAT = "";
		}
		
		//DELETE it since we didn't need it - 2015-01-19
		//boolean isSvcModel = SERVICE.equals(modelCOFCAT);
//		String prodhierFlag = PokUtils.getAttributeFlagValue(rootEntity, "BHPRODHIERCD");
//        
//        addDebug("checking "+featItem.getKey()+" "+mdlItem.getKey()+" modelCOFCAT "+modelCOFCAT+
//        		" root prodhierFlag "+prodhierFlag);
//        
//        //5.02			PRODSTRUCT-d									
//        //Delete 20110322 5.04	IF		"Service" (102)	<>	MODEL	COFCAT						        
//    	//if(!isSvcModel &&  	
//    	if(BHPRODHIERCD_No_ProdHCode.equals(prodhierFlag)){
//            //5.06			"00 - No Product Hierarchy Code" (BHPH0000)	<>	PRODSTRUCT	BHPRODHIERCD		E	E	E		must not have {LD: BHPRODHIERCD} {BHPRODHIERCD}
//			//MUST_NOT_HAVE_ERR1= must not have {0}
//			args[0] = this.getLD_Value(rootEntity, "BHPRODHIERCD");
//			createMessage(CHECKLEVEL_E,"MUST_NOT_HAVE_ERR1",args);
//		}
        //Delete 20110322 5.08	END	5.04										

		//Add 5-23-16	6.10	IF		Yes	=	FEATURE	MAINTPRICE						
		//								Yes	=	PRODSTRUCT	WARRSVCCOVR		W	W	E		If {LD: FEATURE}{NDN: FEATURE} MAINTPRICE=Yes PRODSTRUCT.WARRSVCCOVR needs to be Yes
		// 2018-07-30 remove check 6.10
		//checkFeatureMaintPriceAndTMFWarrSvcCovr(rootEntity, featItem, checklvl);
		
        //7.00          STATUS  =>  PRODSTRUCT  DATAQUALITY     E   E   E   All Features
        //{LD: STATUS} can not be higher than the {LD: FEATURE} {NDN: FEATURE}
        checkStatusVsDQ(featItem, "STATUS", rootEntity,CHECKLEVEL_E);
        isRPQ = isRPQ(featItem);
        
        // get VE2 to go from  MODEL to MODELAVAIL links
        mdlList = getModelVE(mdlItem);

        String ordercode = PokUtils.getAttributeFlagValue(rootEntity, "ORDERCODE");
        addDebug(rootEntity.getKey()+" ORDERCODE "+ordercode);
		isInitialOrderCode = ORDERCODE_INITIAL.equals(ordercode);
        // check FCTYPE
        if (isRPQ){
            //8.00-13.00    8.00    WHEN        FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)                     RPQ logic
            checkRPQFeature(rootEntity, featItem, checklvl);
        }else{
            // 15.00    WHEN        FCTYPE  =   Primary FC (100) | "Secondary FC" (110)     GA Logic
            addDebug(featItem.getKey()+" was NOT an RPQ FCTYPE: "+getAttributeFlagEnabledValue(featItem, "FCTYPE"));
            addHeading(3,featItem.getEntityGroup().getLongDescription()+" and "+
                    mdlItem.getEntityGroup().getLongDescription()+" Checks:");
            
            //16.00 AVAIL   A   PRODSTRUCT-u:OOFAVAIL-d                             Feature AVAIL
            //17.00 WHEN        AVAILTYPE   =   "Planned Availability"  or "MES Planned Availability"
            //34.00 WHEN        AVAILTYPE   =   "First Order"
            //44.00 WHEN        AVAILTYPE   =   "Last Order" or "MES Last Order"
            //251.00	WHEN		AVAILTYPE	=	"End of Marketing" (200)
            //270.00	WHEN		AVAILTYPE	=	"End of Service" (151)
            checkOOFAVAILAvails(rootEntity, featItem, mdlItem, statusFlag);
        }//end 15.00

        addHeading(3,"TMF Error Checks:");

        addDebug("checking "+rootEntity.getKey()+" against "+mdlItem.getKey()+
        " dates keys 84,85,86,87,88");
        //83.00 MODEL       PRODSTRUCT-d                                MODEL
        //84.00         STATUS  =>  PRODSTRUCT  DATAQUALITY     E*1   E*1   E*1 can not have a status higher than the {LD: MODEL} {LD: STATUS} {STATUS}
        checkStatusVsDQ(mdlItem, "STATUS", rootEntity,getCheckLevel(CHECKLEVEL_E,mdlItem,"ANNDATE"));
//        checkStatusVsDQ(mdlItem, "STATUS", rootEntity,CHECKLEVEL_E);//change it to Error even it is old data

        //85.00         ANNDATE <=  PRODSTRUCT  ANNDATE     W   E   E   {LD: ANNDATE} must not be earlier than the {LD: MODEL} {LD: ANNDATE} {ANNDATE}
        checkCanNotBeEarlier(rootEntity, "ANNDATE", mdlItem, "ANNDATE", getCheck_W_E_E(statusFlag));

        //86.00         GENAVAILDATE    <=  PRODSTRUCT  GENAVAILDATE        W   E   E   {LD: GENAVAILDATE} must not be earlier than the {LD: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
        checkCanNotBeEarlier(rootEntity, "GENAVAILDATE", mdlItem, "GENAVAILDATE", getCheck_W_E_E(statusFlag));

        //86.40	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
        if(isInitialOrderCode){
        	//87.00         WITHDRAWDATE    =>  PRODSTRUCT  WITHDRAWDATE        W   E   E   {LD: WITHDRAWDATE} must not be later than {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
        	checkCanNotBeLater(rootEntity, "WITHDRAWDATE", mdlItem, "WITHDRAWDATE", getCheck_W_E_E(statusFlag));
        	//88.00         WTHDRWEFFCTVDATE    =>  PRODSTRUCT  WTHDRWEFFCTVDATE        W   E   E   {LD: WTHDRWEFFCTVDATE} must not be later than {LD: MODEL} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
        	checkCanNotBeLater(rootEntity, "WTHDRWEFFCTVDATE", mdlItem, "WTHDRWEFFCTVDATE", getCheck_W_E_E(statusFlag));
        }else{
        	addDebug("BYPASSING MODEL.WTHDRWEFFCTVDATE and WITHDRAWDATE checks ordercode not initial");
        }
        //88.40	END	86.40
        
       
        //89.00 TMF Error       Action: SRDPRODSTRUCTV   Search Using    PRODSTRUCT-d: MODEL MACHTYPEATR                 TMF Error Checking
        //90.00             AND PRODSTRUCT-u: FEATURE   FEATURECODE
        //91.00         UniqueWithinMODEL       FEATURE FEATURECODE     E   E   E       {LD: FEATURECODE} {FEATURECODE} is a duplicate within {LD: MODEL} {MODEL.MODELATR}
        //92.00         IdenticalWithinMACHTYPE     FEATURE FEATURECODE     E   E   E       {LD: FEATURECODE} {FEATURECODE} is a duplicate within {LD: MACHTYPE} however, it is not an identical {FEATURE}
        checkAllFeatures(featItem, mdlItem);

        /** 
         * Story 1958847: remove this control
         * 
        //93.00 PRODSTRUCT  C   Root                                PRODSTRUCT
        //94.00 WHEN    ORDERCODE   =   "MES" (5956)
        //95.00 OR      ORDERCODE   =   "Both" (5955)
        if (ORDERCODE_BOTH.equals(ordercode) || ORDERCODE_MES.equals(ordercode)){
            //96.00 FEATURE     PRODSTRUCT-u
            //97.00         PRICEDFEATURE   =   "Yes" (100) E E E   {LD: PRICEDFEATURE} {PRICEDFEATURE} is invalid for {LD: ORDERCODE} {C: ORDERCODE}
            String pricedfeature = PokUtils.getAttributeFlagValue(featItem, "PRICEDFEATURE");
            addDebug(featItem.getKey()+" (97) PRICEDFEATURE "+pricedfeature);
            if (!PRICEDFEATURE_YES.equals(pricedfeature)) {
                //INVALID_VALUES_ERR = {0} is invalid for {1}
                args[0]=getLD_Value(featItem, "PRICEDFEATURE");
                args[1]=getLD_Value(rootEntity, "ORDERCODE");
                createMessage(CHECKLEVEL_E,"INVALID_VALUES_ERR",args);
            }
            //99.00 END 97.00
            //100.00    END 94.00
        }
         */

        //140.00    WARR        PRODSTRUCTWARR-d
        //140.10    WHEN        "Hardware" (100)    =   PRODSTRUCT-d: MODEL COFCAT
        if(HARDWARE.equals(modelCOFCAT)){
        	try{
        		checkWARR(rootEntity,featItem,mdlItem,statusFlag);
        	}catch(StopWarrException swe){} // only used to stop warr checks
        }
        //141.70    END 140.10
    }
    
	/**
	 * Add 5-23-16	
	 * 6.10	IF		Yes	=	FEATURE	MAINTPRICE						
					Yes	=	PRODSTRUCT	WARRSVCCOVR		W	W	E		If {LD: FEATURE}{NDN: FEATURE} MAINTPRICE=Yes PRODSTRUCT.WARRSVCCOVR needs to be Yes
	 * @param tmfItem
	 * @param feaItem
	 * @param checkLvl
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void checkFeatureMaintPriceAndTMFWarrSvcCovr(EntityItem tmfItem, EntityItem feaItem, int checkLvl) throws SQLException, MiddlewareException {
		String maintPrice = PokUtils.getAttributeFlagValue(feaItem, "MAINTPRICE");
		String warrSvcCovr = PokUtils.getAttributeFlagValue(tmfItem, "WARRSVCCOVR");
		addDebug("checkFeatureMaintPriceAndTMFWarrSvcCovr " + feaItem.getKey() + " MAINTPRICE " + maintPrice
				+ " " + tmfItem.getKey() + " WARRSVCCOVR " + warrSvcCovr);
		if (MAINTPRICE_YES.equals(maintPrice) && !WARRSVCCOVR_WARRANTY.equals(warrSvcCovr)) {		
			args[0] = this.getLD_NDN(feaItem);
			createMessage(checkLvl, "MAINTPRICE_WARRSVCCOVR_ERR", args);			
		}
	}
	
    /**
140.00  WARR        PRODSTRUCTWARR-d
140.10  WHEN        "Hardware" (100)    =   PRODSTRUCT-d: MODEL COFCAT
140.40          IdenticalWARR                   E   E   E   "see the doc file for an explanation of the check Need to check even if this PRODSTRUCT does not have a WARR"   {LD: PRODSTRUCT} {NDN: PRODSTRUCT} does not have matching {LD: WARR}
140.50  IF      FCTYPE  =   Primary FC (100) | "Secondary FC" (110)                     If not RPQ
140.60          WARRCoverage                Yes E   E   E   Not Required    {LD: WARR} {NDN: WARR} have gaps in the date range.
140.70          COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} for every country in the {LD: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
140.80          MIN(EFFECTIVEDATE)  <=  A: AVAIL    EFFECTIVEDATE   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an EFFECTIVEDATE  as early as or earlier than the  {LD: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
140.90          MAX(ENDDATE)    =>  B: AVAIL    EFFECTIVEDATE   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an ENDATE as late as or later than the  {LD: PRODSTRUCT} {LD: AVAIL} {NDN: B: AVAIL}
141.00	ELSE	140.50				
			PRODSTRUCTVALIDFROM	FirstValue	PRODSTRUCT	ANNDATE
					MAX(MODEL.ANNDATE; FEATURE.ANNDATE)	
					"9999-12-31"	
            PRODSTRUCTVALIDTO   FirstValue  PRODSTRUCT  WTHDRWEFFCTVDATE
                    MIN(MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T}  WITHDRAWDATEEFF_T                   
                    "9999-12-31"
141.10          WARRCoverage                Yes E   E   E   Not Required    {LD: WARR} {NDN: WARR} have gaps in the date range.
141.20          COUNTRYLIST "Contains aggregate E"  PRODSTRUCT-u: FEATURE   COUNTRYLIST     E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} for every country in the {LD: PRODSTRUCT} {LD: COUNTRYLIST}
141.30          MIN(EFFECTIVEDATE)  <=      PRODSTRUCTVALIDFROM Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an EFFECTIVEDATE as early as or earlier than the  general availability of {LD: PRODSTRUCT} {PRODSTRUCTVALIDFROM}
141.40          MAX(ENDDATE)    =>      PRODSTRUCTVALIDTO   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an ENDATE as late as or later than the last order of {LD: PRODSTRUCT} (PRODSTRUCTVALIDTO}
141.50  END 140.50
141.70  END 140.10

     * @param psitem
     * @param featItem
     * @param mdlItem
     * @param statusFlag
     * @throws MiddlewareException
     * @throws SQLException
     * @throws MiddlewareShutdownInProgressException
     * @throws StopWarrException 
     */
    private void checkWARR(EntityItem psitem, EntityItem featItem, EntityItem mdlItem,String statusFlag)
    throws MiddlewareException, SQLException, MiddlewareShutdownInProgressException, StopWarrException
    {
        int cnt = getCount("PRODSTRUCTWARR");

        //140.00    WARR        PRODSTRUCTWARR-d
        //140.10    WHEN        "Hardware" (100)    =   PRODSTRUCT-d: MODEL COFCAT
        addHeading(3,"Identical "+m_elist.getEntityGroup("WARR").getLongDescription()+" Checks:");
        //140.40            IdenticalWARR                   E   E   E   "see the doc file for an explanation of the check Need to check even if this PRODSTRUCT does not have a WARR"   {LD: PRODSTRUCT} {NDN: PRODSTRUCT} does not have matching {LD: WARR}
        identicalWARR(psitem,featItem,mdlItem,CHECKLEVEL_E);

        if(cnt > 0) {
            addHeading(3,m_elist.getEntityGroup("PRODSTRUCTWARR").getLongDescription()+" Coverage Checks:");

            if(!isRPQ){
                //140.50    IF      FCTYPE  =   Primary FC (100) | "Secondary FC" (110)                     If not RPQ
                //140.60            WARRCoverage                Yes E   E   E   Not Required    {LD: WARR} {NDN: WARR} have gaps in the date range.
                //140.70            COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} for every country in the {LD: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
                //140.80            MIN(EFFECTIVEDATE)  <=  A: AVAIL    EFFECTIVEDATE   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an EFFECTIVEDATE  as early as or earlier than the  {LD: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
                //140.90            MAX(ENDDATE)    =>  B: AVAIL    EFFECTIVEDATE   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an ENDATE as late as or later than the  {LD: PRODSTRUCT} {LD: AVAIL} {NDN: B: AVAIL}
                checkWarrCoverage(psitem, psitem, "PRODSTRUCTWARR","OOFAVAIL",CHECKLEVEL_E,CHECKLEVEL_E);
            }else{
                //141.00	ELSE	140.50				
//    			PRODSTRUCTVALIDFROM	FirstValue	PRODSTRUCT	ANNDATE
//				MAX(MODEL.ANNDATE; FEATURE.ANNDATE)	
//				"9999-12-31"	
//              PRODSTRUCTVALIDTO   FirstValue  PRODSTRUCT  WTHDRWEFFCTVDATE
//              MIN(MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T)  "9999-12-31"
                //141.10    WARRCoverage                Yes E   E   E   Not Required    {LD: WARR} {NDN: WARR} have gaps in the date range.
                //141.20    COUNTRYLIST "Contains aggregate E"  PRODSTRUCT-u: FEATURE   COUNTRYLIST     E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} for every country in the {LD: PRODSTRUCT} {LD: COUNTRYLIST}
                //141.30    MIN(EFFECTIVEDATE)  <=  PRODSTRUCTVALIDFROM Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an EFFECTIVEDATE as early as or earlier than the  general availability of {LD: PRODSTRUCT} {PRODSTRUCTVALIDFROM}
                //141.40    MAX(ENDDATE)    =>      PRODSTRUCTVALIDTO   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an ENDATE as late as or later than the last order of {LD: PRODSTRUCT} (PRODSTRUCTVALIDTO}
                checkWarrCoverage(featItem, psitem, mdlItem);
                //141.50    END 140.50
            }
        }
    }

    /*************
     * Check the WARR against feature
     *   - All FEATURE countries must be a subset of xxWARR countries.
     *   - BY country the MIN(xxWARR.EFFECTIVEDATE) must be <= then feature effdate
     *   - BY country the MAX(xxWARR.ENDDATE)   must be => then feature effdate
     *
IF      FCTYPE  = "RPQ-ILISTED" (120) | "RPQ-PLISTED" (130) | "RPQ-RLISTED" (0140)
                PRODSTRUCTVALIDFROM FirstValue  PRODSTRUCT  GENAVAILDATE
                MAX(MODEL.GENAVALDATE; FEATURE.GENAVAILDATE)    "9999-12-31"
                PRODSTRUCTVALIDTO   FirstValue  PRODSTRUCT  WTHDRWEFFCTVDATE
                MIN(MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T)  WITHDRAWDATEEFF_T                   "9999-12-31"
141.10          WARRCoverage                Yes E   E   E   Not Required    {LD: WARR} {NDN: WARR} have gaps in the date range.
141.20          COUNTRYLIST "Contains aggregate E"  PRODSTRUCT-u: FEATURE   COUNTRYLIST     E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} for every country in the {LD: PRODSTRUCT} {LD: COUNTRYLIST}
141.30          MIN(EFFECTIVEDATE)  <=      PRODSTRUCTVALIDFROM Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an EFFECTIVEDATE as early as or earlier than the  general availability of {LD: PRODSTRUCT} {PRODSTRUCTVALIDFROM}
141.40          MAX(ENDDATE)    =>      PRODSTRUCTVALIDTO   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} with an ENDATE as late as or later than the last order of {LD: PRODSTRUCT} (PRODSTRUCTVALIDTO}

     *
CC. WARRCoverage

ID  What    Op  Data to be Checked
1   WARRCoverage        EntityType
2   COUNTRYLIST Contains    Path: AVAIL COUNTRYLIST
3   MIN(EFFECTIVEDATE)  <=  Path: AVAIL EFFECTIVEDATE
4   MAX(ENDDATE)    =>  Path: AVAIL EFFECTIVEDATE

The relator to the WARR has the following attributes (see the column above labeled �What�):
1.  EFFECTIVEDATE
2.  ENDDATE
3.  COUNTRYLIST

If ENDDATE is not specified, then assume a date of "9999-12-31".

By Country in ID 2, if there are multiple, check that they do not have gaps in time by Country.
This may be done by ordering them by Country in increasing values for PUBFROM. Then
MIN(EFFECTIVEDATE) = the first EFFECTIVEDATE
MAX(ENDDATE) = the last ENDDATE

Check that there are no gaps in the date range by checking that Ith ENDDATE => Ith+1 EFFECTIVEDATE + 1.
The error text is found in the spreadsheet for ID 1.

This check is different than UniqueCoverage in the following ways:
�   For a Country, there can be more than one WARR in effect at the same time.
�   The attributes are on the relator to the WARR
�   The attribute DEFWARR with a value of �Yes� implies that it is the World Wide Default Warranty.
If one exists, then this rule passes without any other checking assuming that the date range is consistent
with the availability of the offering.
     *
     * @param featItem
     * @param psItem
     * @param mdlItem
     * @throws SQLException
     * @throws MiddlewareException
     */
    private void checkWarrCoverage(EntityItem featItem, EntityItem psItem, EntityItem mdlItem)
    throws SQLException, MiddlewareException
    {
        //fixme bypass coverage check and msg
    	if(!doWARRChecks()){
            this.addOutput("Bypassing Warranty coverage checks for now.");
            return;
        }

        EntityGroup eGrp = m_elist.getEntityGroup("PRODSTRUCTWARR");
        EntityGroup warrGrp = m_elist.getEntityGroup("WARR");
        addDebug("checkWarrCoverage entered featItem "+featItem.getKey()+" psItem "+psItem.getKey());

        Vector warrRelVct = getDownLinkEntityItems(psItem, "PRODSTRUCTWARR"); // get the warr relators

        ArrayList warrCtryList = new ArrayList(); // get all countries for all warrs, only use valid ones
        Hashtable ucTbl = new Hashtable();
        Vector ucVct = new Vector();

        String fromdate = prodstructValidFrom(psItem, featItem, mdlItem);
        String todate = prodstructValidTo(psItem, featItem, mdlItem);

        ArrayList offeringCtryList = new ArrayList();
        getCountriesAsList(featItem, offeringCtryList,CHECKLEVEL_E);
        addDebug("checkWarrCoverage "+featItem.getKey()+" from "+fromdate+" to "+
                todate+" offeringCtryList "+offeringCtryList);

        //WARR will be grouped by country and sorted by EFFECTIVEDATE
        findWarrByCtry(psItem, "PRODSTRUCTWARR", warrRelVct, offeringCtryList, warrCtryList, ucTbl, ucVct,CHECKLEVEL_E);

        addDebug("checkWarrCoverage all PRODSTRUCTWARR warrCtryList "+warrCtryList+" warrDefRelVct "+
                " ucVct "+ucVct.size());

        if(ucVct.size()==0){
            offeringCtryList.clear();
            return; // no valid WARR to check
        }

        //141.20            COUNTRYLIST "Contains aggregate E"  PRODSTRUCT-u: FEATURE   COUNTRYLIST     E   E   E   Column E - attributes are on PRODSTRUCTWARR must have a {LD: WARR} for every country in the {LD: PRODSTRUCT} {LD: COUNTRYLIST}
        if(!warrCtryList.containsAll(offeringCtryList)){
            //MISSING_CTRY_ERR2 = must have a {0} for every Country in the {1} {2}, missing {3}
            args[0]=warrGrp.getLongDescription();
            args[1]=getLD_NDN(featItem);
            args[2]=PokUtils.getAttributeDescription(featItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
            //Note: identify the country that does not have an Warr"
            ArrayList ctryflagList = new ArrayList();
            getCountriesAsList(featItem, ctryflagList,CHECKLEVEL_E);
            ctryflagList.removeAll(warrCtryList); // remove all matches
            args[3]=getUnmatchedDescriptions(featItem, "COUNTRYLIST",ctryflagList);
            createMessage(CHECKLEVEL_E,"MISSING_CTRY_ERR2",args);
            ctryflagList.clear();
        }

        //check by country
        boolean isok = true;
        Iterator itr = offeringCtryList.iterator();
        while (itr.hasNext() && isok) {
            String ctryflag = (String) itr.next();
            Vector tmpVct = (Vector)ucTbl.get(ctryflag); // get the xxWARR that have this country
            if (tmpVct!=null){
                CoverageData uc = (CoverageData)tmpVct.firstElement(); //first one is the earliest
                String minEffDate =uc.pubfrom;
                addDebug("checkWarrCoverage ctry "+ctryflag+" minEffDate "+minEffDate+" found on "+uc.item.getKey());
                //141.30            MIN(EFFECTIVEDATE)  <=      PRODSTRUCTVALIDFROM Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR
                //must have a {LD: WARR} with an EFFECTIVEDATE as early as or earlier than the general availability of {LD: PRODSTRUCT} {PRODSTRUCTVALIDFROM}
                isok = checkDates(minEffDate, fromdate, DATE_LT_EQ);    //date1<=date2
                if(!isok){
                    //EFF_FROM_ERR= must have a {0} with an {1} as early as or earlier than the general availability of {2} {3}
                    args[0]=warrGrp.getLongDescription();
                    args[1]=PokUtils.getAttributeDescription(eGrp, "EFFECTIVEDATE", "EFFECTIVEDATE");
                    args[2]=psItem.getEntityGroup().getLongDescription();
                    args[3]=fromdate+" for Country "+
                    	getUnmatchedDescriptions(featItem.getEntityGroup(), "COUNTRYLIST", ctryflag);
                    createMessage(CHECKLEVEL_E,"EFF_FROM_ERR",args);
                }
            }else{
                addDebug("checkWarrCoverage: No PRODSTRUCTWARR found for country "+ctryflag);
            }
        }

        /* By Country in ID 2, if there are multiple, check that they do not have gaps in time by Country.
            This may be done by ordering them by Country in increasing values for PUBFROM. Then
            MIN(EFFECTIVEDATE) = the first EFFECTIVEDATE
            MAX(ENDDATE) = the last ENDDATE

            Check that there are no gaps in the date range by checking that Ith ENDDATE => Ith+1 EFFECTIVEDATE + 1.
            The error text is found in the spreadsheet for ID 1.
         */

        HashSet badRangeSet = new HashSet();

        // look for gaps by country
        for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
            String flagCode = (String)e.nextElement();
            Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
            //addDebug("GAPTEST ctry "+flagCode+" ctryUcVct.size "+ctryUcVct.size());
            CoverageData prevUc = null;
            // look at all WARR for this country
            for (int u=0; u<ctryUcVct.size(); u++){
                CoverageData uc = (CoverageData)ctryUcVct.elementAt(u);
                if (u!=0){
                    //addDebug("GAPTEST["+u+"] ctry "+flagCode+" \nprevUc "+prevUc+" \ncurrUc "+uc);
                    if((uc.pubto.compareTo(fromdate)<0) ||
                            uc.pubfrom.compareTo(todate)>0){
                        addDebug("checkWarrCoverage "+uc+" is outside offering range for "+
                                flagCode+" fromdate "+fromdate+
                                " todate "+todate);
                    }else{
                        Date pubToDate = Date.valueOf(prevUc.pubto);
                        long pubto = pubToDate.getTime();
                        long pubtoPlus1 = pubto + 24 * 60 * 60*1000; //pubto+ 1 day
                        Date pubtoPlus1Date = new Date(pubtoPlus1);
                        Date pubFromDate = Date.valueOf(uc.pubfrom);
                        addDebug("GAPTEST["+u+"] ctry "+flagCode+" prev pubto "+prevUc.pubto+" pubtoPlus1Date "+pubtoPlus1Date+" cur pubfrom "+uc.pubfrom);
                        //if (!pubtoPlus1Date.toString().equals(uc.pubfrom)){
                        if (pubtoPlus1Date.compareTo(pubFromDate)<0){
                            if (!badRangeSet.contains(prevUc.item) ||
                                    !badRangeSet.contains(uc.item)) {
                                badRangeSet.add(prevUc.item);
                                badRangeSet.add(uc.item);
                                addDebug("checkWarrCoverage output date range msg for "+prevUc.item.getKey()+
                                        " and "+uc.item.getKey());
                                //141.10            WARRCoverage                Yes E   E   E   Not Required    {LD: WARR} {NDN: WARR} have gaps in the date range.
                                // DATE_RANGE_ERR2={0} and {1} have gaps in the date range.
                                args[0]=getLD_NDN(prevUc.item)+" for "+getLD_NDN(prevUc.warritem);
                                args[1]=getLD_NDN(uc.item)+" for "+getLD_NDN(uc.warritem);
                                createMessage(CHECKLEVEL_E,"DATE_RANGE_ERR2",args);
                            }else{
                                addDebug("checkWarrCoverage already output date range msg for "+prevUc.item.getKey()+
                                        " and "+uc.item.getKey());
                            }
                        }
                    }
                }
                // check for prevUc ending after current uc
                if (prevUc == null || prevUc.pubto.compareTo(uc.pubto)<0){
                    prevUc = uc;
                }
            }
        }
        badRangeSet.clear();
        // end has gap tests

        // sort each countries vectors using PUBTO now
        for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
            String flagCode = (String)e.nextElement();
            Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
            for (int u=0; u<ctryUcVct.size(); u++){
                CoverageData uc = (CoverageData)ctryUcVct.elementAt(u);
                uc.setPubFromSort(false); // sort by pubto now
            }
            Collections.sort(ctryUcVct);
        }

        //check by country
        isok = true;
        itr = offeringCtryList.iterator();
        while (itr.hasNext() && isok) {
            String ctryflag = (String) itr.next();
            Vector tmpVct = (Vector)ucTbl.get(ctryflag);
            if (tmpVct!=null){
                CoverageData uc = (CoverageData)tmpVct.lastElement(); // last one is the latest
                String maxEndDate =uc.pubto;
                addDebug("checkWarrCoverage ctry "+ctryflag+" maxEndDate "+maxEndDate+" found on "+uc.item.getKey());
                //141.40            MAX(ENDDATE)    =>  PRODSTRUCTVALIDTO   Yes E   E   E   Column E - attributes are on PRODSTRUCTWARR
                //must have a {LD: WARR} with an ENDATE as late as or later than the last order of {LD: PRODSTRUCT} (PRODSTRUCTVALIDTO}
                isok = checkDates(maxEndDate, todate, DATE_GR_EQ);  //date1=>date2
                if(!isok){
                    //EFF_TO_ERR= must have a {0} with an {1} as late as or later than the last order of {2} {3}
                    args[0]=warrGrp.getLongDescription();
                    args[1]=PokUtils.getAttributeDescription(eGrp, "ENDDATE", "ENDDATE");
                    args[2]=psItem.getEntityGroup().getLongDescription();
                    args[3]=todate+" for Country "+
                		getUnmatchedDescriptions(featItem.getEntityGroup(), "COUNTRYLIST", ctryflag);
                    createMessage(CHECKLEVEL_E,"EFF_TO_ERR",args);
                }
            }else{
                addDebug("checkWarrCoverage: No PRODSTRUCTWARR found for country "+ctryflag);
            }
        }

        offeringCtryList.clear();
        //258.00 END 255.00

        // release memory
        for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
            String flagCode = (String)e.nextElement();
            Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
            ctryUcVct.clear();
        }
        for (int u=0; u<ucVct.size(); u++){
            CoverageData uc = (CoverageData)ucVct.elementAt(u);
            uc.dereference();
        }
        ucTbl.clear();
        ucVct.clear();
        warrRelVct.clear();
        warrCtryList.clear();
    }

    /**
     * this is only RPQ products 
3.	RPQ Products
	The TPICF is equal to the first one that applies:
	a)	PRODSTRUCT.ANNDATE or if empty
	b)	MAX{MODEL.ANNDATE; FEATURE.FIRSTANNDATE} 
141.00	ELSE	140.50				
			PRODSTRUCTVALIDFROM	FirstValue	PRODSTRUCT	ANNDATE
					MAX(MODEL.ANNDATE; FEATURE.FIRSTANNDATE)	
					"9999-12-31"	
	
     * @param psItem
     * @param featItem
     * @param mdlItem
     * @return
     * @throws MiddlewareException
     * @throws SQLException
     */
    private String prodstructValidFrom(EntityItem psItem, EntityItem featItem, EntityItem mdlItem)
    throws SQLException, MiddlewareException
    {
        String psfromdate = PokUtils.getAttributeValue(psItem, "ANNDATE", "", null, false);
        String mdlfromdate = PokUtils.getAttributeValue(mdlItem, "ANNDATE", "", null, false);
        String fcfromdate = PokUtils.getAttributeValue(featItem, "FIRSTANNDATE", "", null, false);
        addDebug("prodstructValidFrom "+psItem.getKey()+" ANNDATE: "+psfromdate+" "+
                featItem.getKey()+" FIRSTANNDATE: "+fcfromdate+" "+
                mdlItem.getKey()+" ANNDATE: "+mdlfromdate);
        if(psfromdate !=null){
            return psfromdate;
        } 
        if(fcfromdate!=null && mdlfromdate!=null){
            if(fcfromdate.compareTo(mdlfromdate)<0){
                return mdlfromdate;
            }
            return fcfromdate;
        }
        if(mdlfromdate!=null){
            return mdlfromdate;
        }
        if(fcfromdate!=null){
            return fcfromdate;
        }

        //NO_VALUE_MSG = , {0} and {1} do not have a value for {2}
        args[0] = getLD_NDN(featItem);
        args[1] = getLD_NDN(mdlItem);
        args[2] = PokUtils.getAttributeDescription(featItem.getEntityGroup(), "FIRSTANNDATE", "FIRSTANNDATE");
        addWarning("NO_VALUE_MSG",args);

        return FOREVER_DATE;
    }
    /**
     * this is only RPQ products
3.	RPQ Products 
	The TPICT is equal to the first one that applies:
	a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
	b)	MIN{MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) or if empty 
	c)	�9999-12-31�. 
	
     * @param psItem
     * @param featItem
     * @param mdlItem
     * @return
     */
    private String prodstructValidTo(EntityItem psItem, EntityItem featItem, EntityItem mdlItem)
    {
        String pstodate = PokUtils.getAttributeValue(psItem, "WTHDRWEFFCTVDATE", "", null, false);
        String mdltodate = PokUtils.getAttributeValue(mdlItem, "WTHDRWEFFCTVDATE", "", FOREVER_DATE, false);
        String fctodate = PokUtils.getAttributeValue(featItem, "WITHDRAWDATEEFF_T", "", FOREVER_DATE, false);
        addDebug("prodstructValidTo "+psItem.getKey()+" WTHDRWEFFCTVDATE: "+pstodate+" "+
        		mdlItem.getKey()+" WTHDRWEFFCTVDATE: "+mdltodate+" "+
                featItem.getKey()+" WITHDRAWDATEEFF_T: "+fctodate);
        if(pstodate !=null){
            return pstodate;
        }
        if(fctodate.compareTo(mdltodate)>0){
            return mdltodate;
        }
        return fctodate;
    }
    /**
     * The TPICF is equal to the first one that applies:
        		a)	PRODSTRUCT.GENAVAILDATE or if empty
        		b)	MAX{MODEL.ANNDATE; FEATURE.GENAVAILDATE} 
     * @param psItem
     * @param featItem
     * @param mdlItem
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     */
    private String oldGaValidFrom(EntityItem psItem, EntityItem featItem, EntityItem mdlItem)
    throws SQLException, MiddlewareException
    {
        String psfromdate = PokUtils.getAttributeValue(psItem, "GENAVAILDATE", "", null, false);
        String mdlfromdate = PokUtils.getAttributeValue(mdlItem, "ANNDATE", "", null, false);
        String fcfromdate = PokUtils.getAttributeValue(featItem, "GENAVAILDATE", "", null, false);
        addDebug("oldGaValidFrom "+psItem.getKey()+" GENAVAILDATE: "+psfromdate+" "+
                featItem.getKey()+" GENAVAILDATE: "+fcfromdate+" "+
                mdlItem.getKey()+" ANNDATE: "+mdlfromdate);
        if(psfromdate !=null){
            return psfromdate;
        } 
        if(fcfromdate!=null && mdlfromdate!=null){
            if(fcfromdate.compareTo(mdlfromdate)<0){
                return mdlfromdate;
            }
            return fcfromdate;
        }
        if(mdlfromdate!=null){
            return mdlfromdate;
        }
        if(fcfromdate!=null){
            return fcfromdate;
        }

        return FOREVER_DATE;
    }
    /**
     * B.	Checking: IdenticalWARR Overview (Key 122.20)
     *  

			-------MACHTYPE----------
			|						|
			MODEL1					MODEL2
			|	|					|	|
AVAIL1.PLA--|	|					|	|---AVAIL5.PLA	
AVAIL2.LO---|	|					|	|---AVAIL6.LO
				|					|
	AVAIL3.PLA--|					|---AVAIL7.PLA
	AVAIL4.LO---|					|---AVAIL8.LO
				|					|
				-------FEATURE-------
				  | |			| |  
				  | |---WARR3---| |
				  |				  |
				  WARR1			  WARR2

The �identical WARR� requirement is based on some downstream systems that manage EACM Machine Type, Model, 
Feature (TMF or PRODSTRUCT) data as Machine Type, Feature (TF) data. Therefore a WARR for a TMF has to be 
identical for a FEATURE (i.e. FEATURECODE) across all Models of a Machine Type.

In the preceding picture (ppt file), WARR 3 is clearly identical (same entityid) for the FEATURE in MODEL 1 
and MODEL 2. In order to be truly identical, the attributes on the PRODSTRUCTWARR relator must also be considered 
along with the AVAILs for the PRODSTRUCT.

AVAIL 3 and AVAIL 7 specify the Countries (COUNTRYLIST) that the FEATURE is available in along with the 
Effective Date for MODEL 1 and MODEL 2 respectively. AVAIL 4 and AVAIL 8 specify by Country (COUNTRYLIST) 
the end date for MODEL 1 and MODEL 2 respectively. The PRODSTRUCTWARRs for MODEL 1 WARR 3 and MODEL 2 WARR 3 
specify the applicable Countries (COUNTRYLIST), Effective Date, and End Date for the Warranty.

There are many possibilities that are legal even when the COUNTRYLIST and dates are not the same. For example:
The PRODSTRUCTWARR COUNTRYLISTs are not the same.
If the COUNTRYLIST for AVAIL 3 and the MODEL 1 PRODSTRUCTWARR for WARR 3 are the same (e.g. China and Germany) 
and the COUNTRYLIST for AVAIL 7 and the MODEL 2 PRODSTRUCTWARR for WARR 3 are the same (e.g. Germany), then 
this is legal even though the COUNTRYLISTs for WARR 3 are not the same since the TMF for MODEL 2 is not offered 
in China.

The requirement is that during the availability (�Planned Availability� COUNTRYLIST, �Planned Availability� 
EFFECTIVEDATE, �Last Order� EFFECTIVEDATE) of TMFs for a Machine Type where the Feature is identical, 
that the Warranty (WARR) for those TMFs have matching country (PRODSTRUCTWARR COUNTRYLIST), from date 
(EFFECTIVEDATE) and to date (ENDDATE). Matching dates does not mean identical dates since the availability 
period may be different. The matching is checked for the overlap of availability period.

C.	Checking: Identical WARR Details (Key 122.20)

For all MODELs of a MACHTYPE, a FEATURE Code (PRODSTRUCT) must have an identical set of applicable WARRs. 

All PRODSTRUCTs where the child MODEL has an identical MACHTYPEATR and the FEATURE has an identical FEATURECODE 
(i.e. identical entityid) must have an identical set of WARRs by Country (COUNTRYLIST on the relator). 

For a pair of TMFs (PRODSTRUCTS) that have an identical FEATURE, the �Time Period of Interest by Country� (TPIC) 
is the overlap of the availability of the TMFs. 

1.	GA Products
	If the parent FEATURE�s FCTYPE is �Primary FC� (100) | "Secondary FC" (110), then the PRODSTRUCT is a GA Product.
	The Country of interest is taken from the AVAIL COUNTRYLIST where AVAILTYPE = �Planned Availability�
	The TPIC From Date� (TPICF) is 
	�	the MAX (AVAIL EFFECTIVEDATE where AVAILTYPE = �Planned Availability�) by Country for the pair of PRODSTRUCTs
	
	The TPIC �To Date� (TPICT) is 
	�	the MIN(AVAIL EFFECTIVEDATE) where AVAILTYPE = �Last Order� by Country for the pair of PRODSTRUCTs. 
	
	Note: there may be more than one AVAIL of these types and hence the From Date and To Date can vary by Country

2.	GA Products � Old Data
	Old data is defined as GA Products that do not have an AVAIL of AVAILTYPE = �Planned Availability� and were 
	announced prior to 3/1/2010. The announcement date for a PRODSTRUCT is:
	�	PRODSTRUCT ANNDATE if not empty
	�	MODEL ANNDATE if PRODSTRUCT ANNDATE is empty
	
	If the �Old data� criteria are not met, do not proceed with this section. The DQ ABR should fail and not advance 
	Status. The error text is (see Key 18.00 in the attached spreadsheet on the PRODSTRUCT tab; however, the RE*1 does 
	not apply in this case):
	must have at least one "Planned Availability"
	
	If the PRODSTRUCT�s child MODEL has an AVAIL where AVAILTYPE = �Planned Availability�, then 
	The Country of interest is taken from the list produced by the intersection of the PRODSTRUCT�s parent FEATURE 
	COUNTRYLIST and the PRODSTRUCT�s child MODEL�s AVAIL COUNTRYLIST where AVAILTYPE = �Planned Availability�.
	
	The TPICF is equal to the first one that applies:
	a)	PRODSTRUCT.GENAVAILDATE or if empty
	b)	MAX{MODEL via MODELAVAIL-d: AVAIL EFFECTIVEDATE where AVAILTYPE = �Planned Availability�; FEATURE.GENAVAILDATE} 
	or if empty 
	c)	�9999-12-31� 
	
	The TPICT is equal to the first one that applies:
	a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
	b)	MIN(MODEL via MODELAVAIL-d: AVAIL EFFECTIVEDATE where AVAILTYPE = �Last Order� or if it doesn�t exist for the 
	Country of interest then MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) or if empty 
	c)	�9999-12-31�. 
		
	Else
	The Country is taken from the PRODSTRUCT�s parent FEATURE COUNTRYLIST.
	
	The TPICF is equal to the first one that applies:
	a)	PRODSTRUCT.GENAVAILDATE or if empty
	b)	MAX{MODEL.ANNDATE; FEATURE.GENAVAILDATE} 
	
	The TPICT is equal to the first one that applies:
	a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
	b)	MIN{MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) or if empty 
	c)	�9999-12-31�.
	
	ENDIF

3.	RPQ Products

	The Country is taken from the list produced by the intersection of the PRODSTRUCT�s parent FEATURE 
	COUNTRYLIST and the PRODSTRUCT�s child MODEL�s AVAIL COUNTRYLIST where AVAILTYPE = �Planned Availability�.
	
	The TPICF is equal to the first one that applies:
	a)	PRODSTRUCT.ANNDATE or if empty
	b)	MAX{MODEL.ANNDATE; FEATURE.ANNDATE} 
	
	The TPICT is equal to the first one that applies:
	a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
	b)	MIN{MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) or if empty 
	c)	�9999-12-31�. 
	
	Note: If for a Country TPICT <= TPICF, then that Country does not need to be checked

4.	Applicable Warranty

	The applicable Warranty of a TMF (PRODSTRUCT) is specified via the PRODSTRUCTWARR attributes for the 
	Warranty (WARR):
	�	Country � CROUNTRYLIST
	�	From Date � EFFECTIVEDATE
	If empty, assume �1980-01-01�
	�	To Date � ENDDATE
	If empty, assume �9999-12-31� 

5.	Default Warranty (DEFWARR)

	A PRODSTRUCTWARR with attribute code DEFWARR = �Yes� is valid for all countries that do not have a 
	specific WARR. The EFFECTIVEDATE and ENDDATE are still considered by Country. If you have a DEFWARR 
	on one FEATURE (PRODSTRUCT), you must have it on all PRODSTRUCTs for that FEATURE in the same Machine Type.

6.	PRODSTRUCT Pairs

	SEARCH for PRODSTRUCT using Machine Type (MACHTYPEATR) and Feature Code (FEATURECODE) but do not restrict
	based on PDHDOMAIN. 
	
	If more than one PRODSTRUCT is found, then checking must continue.
	
	Consider a pair of PRODSTRUCTs and proceed as follows:
	For each PRODSTRUCT pair, verify that the FEATURE entityids are identical. If not, create an Error Message:
	{LD: PRODSTRUCT} {NDN: PRODSTRUCT 2} has an identical {LD: FEATURECODE} but the EntityIds are different.
	Note: The identified PRODSTRUCT FEATURE does not have a matching EntityId for the PRODSTRUCT�s FEATURE that 
	the ABR is running on. The PRODSTRUCT that the ABR is running on is identified in the report.

7.	Checking

	For each Country, derive TPICF and TPICT.
	
	If TPICF => TPICT, then no further checking of this pair for this country is required since the PRODSTRUCTs 
	are not offered at the same time and hence the WARRs may be different (e.g. WARR 1 and WARR 2)
	
	If TPICF < TPICT, then there is a common period of time that the PRODSTRUCTs are offered and hence the WARRs 
	must be the same. (e.g. WARR 3 � i.e. the entityid is identical). If the WARRs are not identical, create an 
	Error Message:
	
		{LD: PRODSTRUCT} {NDN: PRODSTRUCT 2} does not have an identical {LD: WARR}
	
		Where the identified PRODSTRUCT 2 does not have an identical WARR with the PRODSTRUCT that the ABR is running on. 
		
		The PRODSTRUCT that the ABR is running on is identified in the error report. 
	
		Also, error report includes the following by PRODSTRUCT:
		IF PRODSTUCT-u: FEATURE.FCTYPE = �Primary FC (100) | "Secondary FC" (110)� THEN
		�	{LD: AVAIL} {NDN: AVAIL where AVAILTYPE = �Planned Availability�} 
		�	{LD: AVAIL} {NDN: AVAIL where AVAILTYPE = �Last Order�}
		ELSE
		�	{LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
		�	{LD: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
		�	{LD: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
		�	{LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
		�	{LD: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
		�	{LD: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
		END IF
		
		The error report includes the Warranty:
		�	{LD: WARR} {NDN: WARR}
		�	From PRODSTRUCTWARR
		�	{LD: EFFECTIVEDATE}{EFFECTIVEDATE}
		�	{LD: ENDDATE}{ENDDATE}
		�	{LD: DEFWARR}{DEFWARR}
		�	{LD: COUNTRYLIST}{COUNTRYLIST}

	For MODEL 1 PRODSTRUCT by Country, derive:
	�	From 1 = MAX(TPICF, PRODSTRUCTWARR.EFFECTIVEDATE)
	�	To 1 = MIN(TPICT, PRODSTRUCTWARR.ENDDATE)
	
	For MODEL 2 PRODSTRUCT by Country, derive:
	�	From 2 = MAX(TPICF, PRODSTRUCTWARR.EFFECTIVEDATE)
	�	To 2 = MIN(TPICT, PRODSTRUCTWARR.ENDDATE)
	
	By Country, if From 1 <> From 2, then report an error � see the error in preceding step.
	
	By Country, if To 1 <> To 2, then report an error � see the error in preceding step.

 ======================================
6.	Identical Warr Use Cases

This section is a complete replacement for the original examples.

Ref the picture (ppt) at the start of the section on �Identical Warr Overview�.

			-------MACHTYPE----------
			|						|
			MODEL1					MODEL2
			|	|					|	|
AVAIL1.PLA--|	|					|	|---AVAIL5.PLA	
AVAIL2.LO---|	|					|	|---AVAIL6.LO
				|					|
	AVAIL3.PLA--|					|---AVAIL7.PLA
	AVAIL4.LO---|					|---AVAIL8.LO
				|					|
				-------FEATURE-------
				  | |			| |  
				  | |---WARR3---| |
				  |				  |
				  WARR1			  WARR2
				  
1.	Case 1
PRODSTRUCT AVAILs
AVAIL	COUNTRY	EFFECTIVEDATE
3		China	8/15/2011
4		China	10/20/2012
7		China	9/15/2011
8		China	

PRODSTRUCT WARR
TMF	MODEL	WARR	COUNTRY	EFFECTIVEDATE	ENDDATE		DEFWARR
1	1		3		China	8/15/2011		10/20/2011	No
2	2		3		China	9/15/2011					No

For the two PRODSTRUCTs for this identical FEATURE, the time period that they are both offered 
(9/15/2011 to 10/20/2012), they both have the same (identical entityid) WARR for China. Therefore this is legal.

 
2.	Case 2 
PRODSTRUCT AVAILs
AVAIL	COUNTRY	EFFECTIVEDATE
3		China	8/15/2011
4		China	10/20/2012
7		Germany	9/15/2011
8		Germany	

PRODSTRUCT WARR
TMF	MODEL	WARR	COUNTRY	EFFECTIVEDATE	ENDDATE		DEFWARR
1	1		1		China	8/15/2011		10/20/2011	No
2	2		2		Germany	9/15/2011					No

The identical FEATURE is offered in China for MODEL 1 and in Germany for MODEL 2. Although this is not likely 
to happen, it is legal even though the two PRODSTRUCTs have different WARRs.

3.	Case 3
If in Case 2, the AVAILs and the WARRs were for China, it would not be legal since the WARRs were not identical


4.	Case 4
PRODSTRUCT AVAILs
AVAIL	COUNTRY	EFFECTIVEDATE
3		China	8/15/2011
4		China	10/20/2012
7		China	9/15/2011
8		China	

PRODSTRUCT WARR
TMF	MODEL	WARR	COUNTRY	EFFECTIVEDATE	ENDDATE		DEFWARR
1	1		3		China	8/15/2011		10/20/2012	No
2	2		3		China	9/15/2011		10/20/2012	No
2	2		2				10/21/2012					Yes

For the two PRODSTRUCTs for this identical FEATURE, the time period that they are both offered 
(9/15/2011 to 10/20/2012), they both have the same (identical entityid) WARR for China. So, even though 
TMF 2 has a WARR that TMF does not, since it is valid after TMF is withdrawn, this is legal.

     *
     * @param psitem
     * @param featItem
     * @param mdlItem
     * @param checklvl
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     * @throws StopWarrException 
     */
    private void identicalWARR(EntityItem psitem,EntityItem featItem, EntityItem mdlItem, int checklvl) throws
    SQLException, MiddlewareException, MiddlewareShutdownInProgressException, StopWarrException
    {
        //fixme bypass coverage check and msg
    	if(!doWARRChecks()){ 
            this.addOutput("Bypassing Identical Warranty checks for now.");
            return;
        }
    	
        EntityGroup pswarrGrp = m_elist.getEntityGroup("PRODSTRUCTWARR");
        addDebug("identicalWARR: root pswarrGrp "+pswarrGrp.getEntityItemCount());

        //use parent model, find prodstructs with models with same machtype, and features with same fc
        if(mtmList==null){
        	//SEARCH for PRODSTRUCT using Machine Type (MACHTYPEATR) and Feature Code (FEATURECODE) but do not 
        	//restrict based on PDHDOMAIN. 
            searchForProdstructs(featItem, mdlItem);
            if(mtmList==null){
                return;
            }
            addDebug("identicalWARR search: "+NEWLINE +PokUtils.outputList(mtmList));
        }

        EntityGroup mtmpsgrp = mtmList.getEntityGroup("PRODSTRUCT");// this will have root ps id in it too
        if(mtmList.getEntityGroup("FEATURE").getEntityItemCount()!=1){
            String fcode = PokUtils.getAttributeValue(featItem, "FEATURECODE",", ", "", false);
            // 1.	For each PRODSTRUCT pair, verify that the FEATURE entityids are identical. 
            //Note: The identified PRODSTRUCT FEATURE does not have a matching EntityId for the PRODSTRUCT�s FEATURE that 
            //the ABR is running on. The PRODSTRUCT that the ABR is running on is identified in the report.
            //{LD: PRODSTRUCT} {NDN: PRODSTRUCT 2} has an identical {LD: FEATURECODE} but the EntityIds are different.
            //DUPE_DATA_ERR= {0} has an identical {1} but the EntityIds are different.
            for (int i=0; i<mtmpsgrp.getEntityItemCount(); i++){
                EntityItem otherPsItem = mtmpsgrp.getEntityItem(i);
                if(otherPsItem.getEntityID()==psitem.getEntityID()){
                    continue;
                }

                args[0]=this.getLD_NDN(otherPsItem);
                args[1]=fcode;
                createMessage(CHECKLEVEL_E,"DUPE_DATA_ERR",args);
            }
            return;
        }

        //If more than one PRODSTRUCT is found, then checking must continue.
        if(mtmpsgrp.getEntityItemCount()<=1){
        	return;
        }
        
        // 2 models ac1 and mc1, same fc
        //pull extract to get to all warr and avails
        EntityItem eia[] = new EntityItem[mtmpsgrp.getEntityItemCount()-1];
        int cnt=0;
        for (int i=0; i<mtmpsgrp.getEntityItemCount(); i++){
            EntityItem otherPsItem = mtmpsgrp.getEntityItem(i);
            if(otherPsItem.getEntityID()==psitem.getEntityID()){ //dont pull root info again
                continue;
            }
            eia[cnt++] = otherPsItem;
        }
        String VEname = "DQVEPSIDENTWARR";
        EntityList warrList = m_db.getEntityList(m_elist.getProfile(),
                new ExtractActionItem(null, m_db,m_elist.getProfile(),VEname),  eia);

        // debug display list of groups
        addDebug("identicalWARR extract (otherps): "+VEname+NEWLINE +PokUtils.outputList(warrList));
        
        EntityGroup otherpswarrGrp = warrList.getEntityGroup("PRODSTRUCTWARR");
        addDebug("identicalWARR: otherpswarrGrp "+otherpswarrGrp.getEntityItemCount());
        if(otherpswarrGrp.getEntityItemCount()==0 && pswarrGrp.getEntityItemCount()==0){
            addDebug("identicalWARR: no WARRs found, nothing to check");
            // release memory
            warrList.dereference();
            return;
        }
        EntityList otherMdlAvailList = getModelsVE(warrList.getEntityGroup("MODEL"));  
        // pull model from extract that got to avails and use that one
        // get the model with avail links
    	mdlItem = mdlList.getParentEntityGroup().getEntityItem(0); 

        EntityGroup otherPsGrp = warrList.getParentEntityGroup();

        /*
        For a pair of TMFs (PRODSTRUCTS) that have an identical FEATURE, the �Time Period of Interest by Country� 
        (TPIC) is the overlap of the availability of the TMFs. 
       
        For GA products, the TPIC From Date� (TPICF) is 
        �	the MAX (AVAIL EFFECTIVEDATE where AVAILTYPE = �Planned Availability) 

        For GA products, the TPIC �To Date� (TPICT) is 
        �	the MIN(AVAIL EFFECTIVEDATE) where AVAILTYPE = �Last Order� by Country. 

        Note: there may be more than one AVAIL of these types and hence the From Date and To Date can vary by Country

3.	RPQ Products

	The Country is taken from the list produced by the intersection of the PRODSTRUCT�s parent FEATURE 
	COUNTRYLIST and the PRODSTRUCT�s child MODEL�s AVAIL COUNTRYLIST where AVAILTYPE = �Planned Availability�.
	
	The TPICF is equal to the first one that applies:
	a)	PRODSTRUCT.ANNDATE or if empty
	b)	MAX{MODEL.ANNDATE; FEATURE.ANNDATE} 
	
	The TPICT is equal to the first one that applies:
	a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
	b)	MIN{MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) or if empty 
	c)	�9999-12-31�. 
	
	Note: If for a Country TPICT <= TPICF, then that Country does not need to be checked
         */
        
        //2.	Availability of the PRODSTRUCTs: for each Country, derive TPICF and TPICT.
        // get availability by ctry for this prodstruct
        Hashtable tpicByCtryTbl = getTpicByCtry(psitem, featItem, mdlItem);
        addDebug(D.EBUG_DETAIL,"identicalWARR root psitem "+psitem.getKey()+" tpicByCtryTbl: "+tpicByCtryTbl);

        
        //5.For MODEL 1 PRODSTRUCT by Country, derive:
        //a.	From 1 = MAX(TPICF, PRODSTRUCTWARR.EFFECTIVEDATE)
        //b.	To 1 = MIN(TPICT, PRODSTRUCTWARR.ENDDATE)
        // get the warr ids by ctry for this prodstruct
        Hashtable warrTpicTbl = getWarrTpicByCtry(psitem, tpicByCtryTbl.keySet());
        addDebug(D.EBUG_DETAIL,"identicalWARR root psitem "+psitem.getKey()+" warrTpicTbl: "+warrTpicTbl);
        for (int i=0; i<otherPsGrp.getEntityItemCount(); i++){
            EntityItem otherPsItem = otherPsGrp.getEntityItem(i);
            EntityItem fndFeatItem = this.getUpLinkEntityItem(otherPsItem, "FEATURE"); // this will be the same feature
            EntityItem otherMdlItem = this.getDownLinkEntityItem(otherPsItem, "MODEL");
            addDebug("\nidenticalWARR: checking otherPsItem "+otherPsItem.getKey()+" "+otherMdlItem.getKey());
            // pull model from extract that got to avails and use that one
            // get the model with avail links
            otherMdlItem = otherMdlAvailList.getParentEntityGroup().getEntityItem(otherMdlItem.getKey());        
            
            // get availability by ctry for other prodstruct
            Hashtable otherTpicByCtryTbl = getTpicByCtry(otherPsItem, fndFeatItem,otherMdlItem);

            //6.	For MODEL 2 PRODSTRUCT by Country, derive:
            //a.	From 2 = MAX(TPICF, PRODSTRUCTWARR.EFFECTIVEDATE)
            //b.	To 2 = MIN(TPICT, PRODSTRUCTWARR.ENDDATE)
            Hashtable otherWarrTpicTbl = getWarrTpicByCtry(otherPsItem,otherTpicByCtryTbl.keySet());
            addDebug(D.EBUG_DETAIL,"identicalWARR: otherPsItem "+otherPsItem.getKey()+" "+otherMdlItem.getKey()+
                    " otherTpicByCtryTbl: "+otherTpicByCtryTbl);
            addDebug(D.EBUG_DETAIL,"identicalWARR otherPsItem "+otherPsItem.getKey()+" otherWarrTpicTbl: "+otherWarrTpicTbl);
            // look for overlaps in availability

            // For a pair of PRODSTRUCTS, the Time Period of Interest by Country (TPIC) is the
            //overlap of the offering�s (PRODSTRUCT) availability (AVAIL).
            Iterator tpicitr = tpicByCtryTbl.keySet().iterator();
            boolean hadError = false;
            ctryloop:while (tpicitr.hasNext()) {
                String ctryflag = (String) tpicitr.next();
                TPIC tpic = (TPIC)tpicByCtryTbl.get(ctryflag);
                TPIC othertpic = (TPIC)otherTpicByCtryTbl.get(ctryflag);

                // 3.	If TPICF => TPICT, then no further checking of this pair for this country is required 
                //since the PRODSTRUCTs are not offered at the same time and hence the WARRs may be different 
                //(e.g. WARR 1 and WARR 2)
                TPIC overlaid = tpic.getOverlay(othertpic);// this is the overlap with the TPICF and TPICT for the pair
                addDebug(D.EBUG_DETAIL,"identicalWARR tpic: "+tpic+"\n othertpic "+othertpic+"\n overlaid "+overlaid);
                if (overlaid!=null){ // prodstructs availability have an overlap for this ctry
                	// 4.	If TPICF < TPICT, then there is a common period of time that the PRODSTRUCTs are 
                    //offered and hence the WARRs must be the same. (e.g. WARR 3 � i.e. the entityid is identical). 
                    Vector idvct = (Vector)warrTpicTbl.get(ctryflag);
                    addDebug("identicalWARR ctryflag "+ctryflag+" root "+psitem.getKey()+" idvct "+idvct);
                    if (idvct == null){
                        overlaid.dereference();
                        addDebug("identicalWARR NO WARR found for roots avail ctryflag "+ctryflag+", error flagged in warrcoverage chk");
                        continue ctryloop;
                    }

                    //If the WARRs EFFECTIVEDATE => TPICT or the WARRs ENDATE <= TPICF,
                    //then the WARR is not applicable for the TPIC.
                	// get all WARRs within TPIC
                	Vector myWarrIdVct = new Vector();
                    for (int x=0; x<idvct.size(); x++){
                        WarrTPIC wt = (WarrTPIC)idvct.elementAt(x);
                        if (wt.hasOverlay(overlaid)){
                            //root warr dates by ctry overlap the overlapped avail dates
                        	myWarrIdVct.add(wt);
                        }else{
                            // warrcoverage should flag this error
                            addDebug("identicalWARR ctryflag "+ctryflag+" root warrtpic "+wt+" does not overlap availtpic "+overlaid);
                        }
                    }
                    if(myWarrIdVct.size()==0){
                        // warrcoverage should flag this error
                    	addDebug("identicalWARR ctryflag "+ctryflag+" did not have any overlap in root avail and warr TPIC");
                    	continue ctryloop;
                    }

                    // some root warrs exist for the overlapped avail range
                    
                    // match other warr ids by ctry to root warrids
                    Vector otheridvct = (Vector)otherWarrTpicTbl.get(ctryflag);
                    addDebug("identicalWARR ctryflag "+ctryflag+" "+otherPsItem.getKey()+" b4 overlay otheridvct "+otheridvct);

                    if(otheridvct!=null){ // matching ctry found
                      	// get all WARRs within otherTPIC that overlay avail dates
                    	Vector otherWarrIdVct = new Vector();
                        for (int x=0; x<otheridvct.size(); x++){
                            WarrTPIC wt = (WarrTPIC)otheridvct.elementAt(x);
                            if (wt.hasOverlay(overlaid)){
                                //other warr dates by ctry overlap the overlap avail dates
                            	otherWarrIdVct.add(wt);
                            }else{
                                addDebug("identicalWARR ctryflag "+ctryflag+" other warrtpic "+wt+" does not overlap root availtpic "+overlaid);
                            }
                        }
                         
                        //4.	If TPICF < TPICT, then there is a common period of time that the PRODSTRUCTs are 
                        //offered and hence the WARRs must be the same. (e.g. WARR 3 � i.e. the entityid is identical). 
                        //If the WARRs are not identical, create an Error Message:
                        //see outputWarrError
                        
                        //find the shared warrids, missing warrids and extra warrids
                	   	Vector missingVct = new Vector();
                	   	missingVct.addAll(myWarrIdVct);
                	   	missingVct.removeAll(otherWarrIdVct); // missingVct is the WARR that exist for root but not other
                	   	
                	 	Vector extraVct = new Vector();
                	 	extraVct.addAll(otherWarrIdVct);
                	 	extraVct.removeAll(myWarrIdVct);// extraVct is the WARR that exist for other but not root
                	 	
                	 	Vector sharedVct = new Vector();
                	 	sharedVct.addAll(myWarrIdVct);
                	 	sharedVct.retainAll(otherWarrIdVct); // sharedVct is all WARR in common
                	   	
                	   	addDebug("identicalWARR ctryflag "+ctryflag+" myWarrIdVct "+myWarrIdVct);
                	  	addDebug("identicalWARR  otherWarrIdVct "+otherWarrIdVct);
                	  	addDebug("identicalWARR  missingVct (data linked only to root) "+missingVct);
                		addDebug("identicalWARR  extraVct (data linked to otherps) "+extraVct);
                		addDebug("identicalWARR  sharedVct (this data is from root with id match only) "+sharedVct);
                		
                        //The matching of WARRs is first done using the WARR EntityId. If a WARR EntityId on one PRODSTRUCT does
                        //not have a WARR with the identical EntityId on the other PRODSTRUCT, then this is an error.
                        // that statement is not quite true, must look at overlap first
                		
                     	// here the ids match completely, now check min and max dates
                       	if(sharedVct.size()>0){
                       	 /* A PRODSTRUCTWARR with attribute code DEFWARR = �Yes� is valid for all countries that do not have a specific WARR. 
                            The EFFECTIVEDATE and ENDDATE are still considered by Country. If you have a DEFWARR on one FEATURE (PRODSTRUCT), 
                            you must have it on all PRODSTRUCTs for that FEATURE in the same Machine Type.
                       	  */
                           /* this was calculated in the warrtpic.hasOverlay check
                            5.	For MODEL 1 PRODSTRUCT by Country, derive:
                            	a.	From 1 = MAX(TPICF, PRODSTRUCTWARR.EFFECTIVEDATE)
                            	b.	To 1 = MIN(TPICT, PRODSTRUCTWARR.ENDDATE)

                            6.	For MODEL 2 PRODSTRUCT by Country, derive:
                            	a.	From 2 = MAX(TPICF, PRODSTRUCTWARR.EFFECTIVEDATE)
                            	b.	To 2 = MIN(TPICT, PRODSTRUCTWARR.ENDDATE)
							*/
                       		
                       		boolean defWarrMismatch = false;
                     	   	rootloop:for (int u=0; u<sharedVct.size(); u++){
                       	   		WarrTPIC wt = (WarrTPIC)(sharedVct.elementAt(u));
                       	   		// check otherWarrIdVct
                       	   		for (int uw=0; uw<otherWarrIdVct.size(); uw++){
                       	   			WarrTPIC otherwt = (WarrTPIC)(otherWarrIdVct.elementAt(uw));
                       	   			if(otherwt.equals(wt)){
                       	   				if(otherwt.isDefWarr == wt.isDefWarr){
                       	   					continue rootloop;
                       	   				}
                       	   				defWarrMismatch = true;
                       	   				break;
                       	   			}
                       	   		}
                     	   	}
                   	   		
           	   				//If you have a DEFWARR on one FEATURE (PRODSTRUCT), 
                            //you must have it on all PRODSTRUCTs for that FEATURE in the same Machine Type.
                   	   		if(defWarrMismatch){
                   	   			addDebug("identicalWARR  WARR exist for ctryflag "+ctryflag+" on root "+
                   	   					psitem.getKey()+" and "+otherPsItem.getKey()+" but diff deffwarr");
                   	   			// this is an error
                   	   			//{LD: PRODSTRUCT} {NDN: PRODSTRUCT 2} does not have an identical {LD: WARR}
                   	   			//IDENTICAL_DEFWARR_ERR = {0} does not have an identical default {1} between {2} and {3}
                   	   			args[0]=this.getLD_NDN(otherPsItem); 
                   	   			args[1]=warrList.getEntityGroup("WARR").getLongDescription();
                   	   			args[2]=overlaid.fromDate;
                   	   			args[3]=overlaid.toDate;
                   	   			createMessage(checklvl,"IDENTICAL_DEFWARR_ERR",args);

                   	   			outputWarrError(psitem,mdlItem,tpic,myWarrIdVct);  
                   	   			outputWarrError(otherPsItem,otherMdlItem,othertpic,otherWarrIdVct);   
                   	   			hadError=true;
                   	   		}else{
                   	   			for (int u=0; u<sharedVct.size(); u++){
                   	   				WarrTPIC wt = (WarrTPIC)(sharedVct.elementAt(u));
                   	   				for (int uw=0; uw<otherWarrIdVct.size(); uw++){
                   	   					WarrTPIC otherwt = (WarrTPIC)(otherWarrIdVct.elementAt(uw));
                   	   					if(wt.equals(otherwt)){ //same entityid
                   	   						//7.	By Country, if From 1 <> From 2, then report an error � see the error in step 4
                   	   						//see outputWarrError
                   	   						//8.	By Country, if To 1 <> To 2, then report an error � see the error in step 4.
                   	   						//see outputWarrError
                   	   						if(!wt.maxFromDate.equals(otherwt.maxFromDate) ||
                   	   								!wt.minToDate.equals(otherwt.minToDate)){
                   	   							addDebug("identicalWARR  WARR exist for ctryflag "+ctryflag+" on root "+
                   	   									psitem.getKey()+" and "+otherPsItem.getKey()+" but diff dates");
                   	   							addDebug("identicalWARR  wt "+wt+" otherwt "+otherwt);
                   	   							// this is an error
                   	   							//{LD: PRODSTRUCT} {NDN: PRODSTRUCT 2} does not have an identical {LD: WARR}
                   	   							//IDENTICAL_WARR_ERR = {0} does not have an identical {1} between {2} and {3}
                   	   							args[0]=this.getLD_NDN(otherPsItem); 
                   	   							args[1]=warrList.getEntityGroup("WARR").getLongDescription();
                   	   							args[2]=overlaid.fromDate;
                   	   							args[3]=overlaid.toDate;

                   	   							createMessage(checklvl,"IDENTICAL_WARR_ERR",args);

                   	   							outputWarrError(psitem,mdlItem,tpic,myWarrIdVct);  
                   	   							outputWarrError(otherPsItem,otherMdlItem,othertpic,otherWarrIdVct);   
                   	   							hadError=true;
                   	   						}

                   	   						break;
                   	   					}
                   	   				}
                   	   			}
                   	   		}

                   	   		sharedVct.clear();
                        }
                		
                       	if(!hadError && missingVct.size() >0){
                     		addDebug("identicalWARR  WARR exist for ctryflag "+ctryflag+" on root "+
                       				psitem.getKey()+" but not "+otherPsItem.getKey());
                        	//{LD: PRODSTRUCT} {NDN: PRODSTRUCT 2} does not have an identical {LD: WARR}
                        	//IDENTICAL_WARR_ERR = {0} does not have an identical {1} between {2} and {3}
                        	args[0]=this.getLD_NDN(otherPsItem); 
                        	args[1]=warrList.getEntityGroup("WARR").getLongDescription();
                        	args[2]=overlaid.fromDate;
               	   			args[3]=overlaid.toDate;
               	   			
                        	createMessage(checklvl,"IDENTICAL_WARR_ERR",args);
                        	
                        	outputWarrError(psitem,mdlItem,tpic,myWarrIdVct);  
                        	outputWarrError(otherPsItem,otherMdlItem,othertpic,otherWarrIdVct);   
                        	hadError=true;
                       	}
                 		missingVct.clear();
                       	
                       	if(!hadError && extraVct.size() >0){
                       		addDebug("identicalWARR  WARR exist for ctryflag "+ctryflag+" on "+
                       				otherPsItem.getKey()+" but not root "+psitem.getKey());
                            //The error message will identify the PRODSTRUCT that does not have matching WARRs.
                        	//{LD: PRODSTRUCT} {NDN: PRODSTRUCT 2} does not have an identical {LD: WARR}
                        	//IDENTICAL_WARR_ERR = {0} does not have an identical {1} between {2} and {3}
                        	args[0]=this.getLD_NDN(otherPsItem); 
                        	args[1]=warrList.getEntityGroup("WARR").getLongDescription();
                        	args[2]=overlaid.fromDate;
               	   			args[3]=overlaid.toDate;
                        	createMessage(checklvl,"IDENTICAL_WARR_ERR",args);
                        	
                        	outputWarrError(psitem,mdlItem,tpic,myWarrIdVct);  
                        	outputWarrError(otherPsItem,otherMdlItem,othertpic,otherWarrIdVct);   
                        	hadError=true;
                       	}
                  		extraVct.clear();
                        otherWarrIdVct.clear();
                    }else{
                        // both ps are available in the ctry, but otherps is missing warr
                    	addDebug("identicalWARR NO WARR found for ctryflag "+ctryflag+" on "+otherPsItem.getKey());

                    	//{LD: PRODSTRUCT} {NDN: PRODSTRUCT 2} does not have an identical {LD: WARR}
                    	//IDENTICAL_WARR_ERR = {0} does not have an identical {1} between {2} and {3}
                    	args[0]=this.getLD_NDN(otherPsItem); 
                    	args[1]=warrList.getEntityGroup("WARR").getLongDescription();
                    	args[2]=overlaid.fromDate;
           	   			args[3]=overlaid.toDate;
                    	createMessage(checklvl,"IDENTICAL_WARR_ERR",args);
                    	
                    	outputWarrError(psitem,mdlItem,tpic,myWarrIdVct);  
                    	outputWarrError(otherPsItem,otherMdlItem,othertpic,null);   
                    	hadError=true;
                    }
                    
                    myWarrIdVct.clear();
                    overlaid.dereference();
                }// end overlay found
                
                if(hadError){
                	break ctryloop;
                }
            } // end availctry iterator

            // release memory
            Iterator itr = otherTpicByCtryTbl.keySet().iterator();
            while (itr.hasNext()) {
                TPIC tpic = (TPIC)otherTpicByCtryTbl.get(itr.next());
                tpic.dereference();
            }
            otherTpicByCtryTbl.clear();

            itr = otherWarrTpicTbl.keySet().iterator();
            while (itr.hasNext()) {
                Vector idvct = (Vector)otherWarrTpicTbl.get(itr.next());
                for (int x=0; x<idvct.size(); x++){
                    WarrTPIC wt = (WarrTPIC)idvct.elementAt(x);
                    wt.dereference();
                }
                idvct.clear();
            }
            otherWarrTpicTbl.clear();
        } //end prodstruct loop

        warrList.dereference();
        otherMdlAvailList.dereference();
        
        // release memory
        Iterator itr = warrTpicTbl.keySet().iterator();
        while (itr.hasNext()) {
            Vector idvct = (Vector)warrTpicTbl.get(itr.next());
            for (int x=0; x<idvct.size(); x++){
                WarrTPIC wt = (WarrTPIC)idvct.elementAt(x);
                wt.dereference();
            }
            idvct.clear();
        }
        warrTpicTbl.clear();

        itr = tpicByCtryTbl.keySet().iterator();
        while (itr.hasNext()) {
            TPIC tpic = (TPIC)tpicByCtryTbl.get(itr.next());
            tpic.dereference();
        }
        tpicByCtryTbl.clear();
    }
    
    /**
     * If the WARRs are not identical, create an Error Message:

{LD: PRODSTRUCT} {NDN: PRODSTRUCT 2} does not have an identical {LD: WARR}

Where the identified PRODSTRUCT 2 does not have an identical WARR with the PRODSTRUCT that the 
ABR is running on. The PRODSTRUCT that the ABR is running on is identified in the report. 

Also, report the following by PRODSTRUCT:

IF PRODSTUCT-u: FEATURE.FCTYPE = �Primary FC (100) | "Secondary FC" (110)� THEN
�	{LD: AVAIL} {NDN: AVAIL where AVAILTYPE = �Planned Availability�} 
�	{LD: AVAIL} {NDN: AVAIL where AVAILTYPE = �Last Order�}
ELSE
�	{LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
�	{LD: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
�	{LD: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
�	{LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
�	{LD: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
�	{LD: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
END IF
�	{LD: WARR} {NDN: WARR}
�	From PRODSTRUCTWARR
o	{LD: EFFECTIVEDATE}{EFFECTIVEDATE}
o	{LD: ENDDATE}{ENDDATE}
o	{LD: DEFWARR}{DEFWARR}
o	{LD: COUNTRYLIST}{COUNTRYLIST}
     * @param psItem
     * @param mdlItem
     * @param tpic
     * @param warrTpicVct
     * @throws SQLException
     * @throws MiddlewareException
     * @throws StopWarrException
     */
    private void outputWarrError(EntityItem psItem, EntityItem mdlItem,TPIC tpic, Vector warrTpicVct) throws SQLException, MiddlewareException, StopWarrException
    {	
    	boolean isRoot = psItem.getEntityID()==getEntityID();
    	addHeading(3,  m_elist.getEntityGroup("WARR").getLongDescription()+
    			" check information for: "+(isRoot?"Root ":"")+getLD_NDN(psItem));
    	StringBuffer sb = new StringBuffer("<table width='100%' border='1'><colgroup><col width='20%'><col width='80%'/></colgroup>"+NEWLINE);
	    EntityItem featItem = getUpLinkEntityItem(psItem, "FEATURE"); // this will be the same feature id
	      
    	if(isRPQ){
    		sb.append("<tr><td colspan='2'><b>RPQ product</b></td></tr>"+NEWLINE);
/*
�	{LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
�	{LD: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
�	{LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
�	{LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
�	{LD: MODEL} {LD: ANNDATE} {ANNDATE}
�	{LD: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}    		
*/
   
     		sb.append("<tr><td title='"+psItem.getKey()+"'>"+psItem.getEntityGroup().getLongDescription()+": "+
     				PokUtils.getAttributeDescription(psItem.getEntityGroup(), "ANNDATE", "ANNDATE")+
     				": </td><td>"+PokUtils.getAttributeValue(psItem, "ANNDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
     		sb.append("<tr><td title='"+mdlItem.getKey()+"'>"+mdlItem.getEntityGroup().getLongDescription()+": "+
     				PokUtils.getAttributeDescription(mdlItem.getEntityGroup(), "ANNDATE", "ANNDATE")+
     				": </td><td>"+PokUtils.getAttributeValue(mdlItem, "ANNDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
     		sb.append("<tr><td title='"+featItem.getKey()+"'>"+featItem.getEntityGroup().getLongDescription()+": "+
     				PokUtils.getAttributeDescription(featItem.getEntityGroup(), "FIRSTANNDATE", "FIRSTANNDATE")+
     				": </td><td title='"+featItem.getKey()+"'>"+PokUtils.getAttributeValue(featItem, "FIRSTANNDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
     		sb.append("<tr><td title='"+psItem.getKey()+"'>"+psItem.getEntityGroup().getLongDescription()+": "+
     				PokUtils.getAttributeDescription(psItem.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE")+
     				": </td><td>"+PokUtils.getAttributeValue(psItem, "WTHDRWEFFCTVDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
     		sb.append("<tr><td title='"+mdlItem.getKey()+"'>"+mdlItem.getEntityGroup().getLongDescription()+": "+
     				PokUtils.getAttributeDescription(mdlItem.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE")+
     				": </td><td>"+PokUtils.getAttributeValue(mdlItem, "WTHDRWEFFCTVDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
     		sb.append("<tr><td title='"+featItem.getKey()+"'>"+featItem.getEntityGroup().getLongDescription()+": "+
     				PokUtils.getAttributeDescription(featItem.getEntityGroup(), "WITHDRAWDATEEFF_T", "WITHDRAWDATEEFF_T")+
     				": </td><td>"+PokUtils.getAttributeValue(featItem, "WITHDRAWDATEEFF_T", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
    		sb.append("<tr><td title='"+featItem.getKey()+"'>"+featItem.getEntityGroup().getLongDescription()+": "+
     				PokUtils.getAttributeDescription(featItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST")+
     				": </td><td>"+PokUtils.getAttributeValue(featItem, "COUNTRYLIST", ", ", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);

			Vector mdlAvailVct = PokUtils.getAllLinkedEntities(mdlItem, "MODELAVAIL", "AVAIL");
			Vector mdlPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability

		    //	If the PRODSTRUCT�s child MODEL has an AVAIL where AVAILTYPE = �Planned Availability� then 
            if(mdlPlaAvailVct.size()>0){ 
            	ArrayList fcctryList = new ArrayList();
                getCountriesAsList(featItem, fcctryList,CHECKLEVEL_NOOP); 
                //The Country of interest is taken from the list produced by the intersection of the PRODSTRUCT�s 
        		//parent FEATURE COUNTRYLIST and the PRODSTRUCT�s child MODEL�s AVAIL COUNTRYLIST where 
        		//AVAILTYPE = �Planned Availability�.
            	ArrayList mdlctryList = getCountriesAsList(mdlPlaAvailVct, CHECKLEVEL_NOOP);
            	fcctryList.retainAll(mdlctryList);
            	 
            	if(fcctryList.size()>0){
            		for(int x=0; x<mdlPlaAvailVct.size(); x++){
            			EntityItem mdlavail = (EntityItem)mdlPlaAvailVct.elementAt(x);
            			ArrayList ctryList = new ArrayList();
            			getCountriesAsList(mdlavail, ctryList,CHECKLEVEL_NOOP); 
            			ctryList.retainAll(fcctryList);
            			if(ctryList.size()>0){
            				sb.append("<tr><td colspan='2' title='"+mdlItem.getKey()+
            						":"+mdlavail.getKey()+"'>Model "+getLD_NDN(mdlavail)+"</td></tr>"+NEWLINE);
            				sb.append("<tr><td title='"+mdlavail.getKey()+"'>"+mdlavail.getEntityGroup().getLongDescription()+": "+
            						PokUtils.getAttributeDescription(mdlavail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST")+
            						": </td><td>"+PokUtils.getAttributeValue(mdlavail, "COUNTRYLIST", ", ", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
            			}
            		}
        		}else{
        			sb.append("<tr><td colspan='2'>No intersection in Model Planned Availability CountryList and the Feature</td></tr>"+NEWLINE);
        		}
            }
    		
            mdlPlaAvailVct.clear();
            mdlAvailVct.clear();
    	}else{
    		// is this old GA
    		if(isOldGAProduct(psItem, mdlItem)){	
    			
/*    			The Country of interest is taken from the list produced by the intersection of the PRODSTRUCT�s parent FEATURE COUNTRYLIST and the PRODSTRUCT�s child MODEL�s AVAIL COUNTRYLIST where AVAILTYPE = �Planned Availability�.

    			The TPICF is equal to the first one that applies:
    			a)	PRODSTRUCT.GENAVAILDATE or if empty
    			b)	MAX{MODEL via MODELAVAIL-d: AVAIL EFFECTIVEDATE where AVAILTYPE = �Planned Availability�; 
    			FEATURE.GENAVAILDATE}

    			The TPICT is equal to the first one that applies:
    			a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
    			b)	MIN(MODEL via MODELAVAIL-d: AVAIL EFFECTIVEDATE where AVAILTYPE = �Last Order� or 
    			if it doesn�t exist for the Country of interest then MODEL.WTHDRWEFFCTVDATE; 
    			FEATURE.WITHDRAWDATEEFF_T) or if empty 
    			c)	�9999-12-31�. 
*/
   
    			//2.	GA Products � Old Data
    			Vector mdlAvailVct = PokUtils.getAllLinkedEntities(mdlItem, "MODELAVAIL", "AVAIL");
    			Vector mdlPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability

    		    //	If the PRODSTRUCT�s child MODEL has an AVAIL where AVAILTYPE = �Planned Availability� then 
                if(mdlPlaAvailVct.size()>0){ 
           			Vector mdlLoAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailVct, "AVAILTYPE", LASTORDERAVAIL);//last order

                	sb.append("<tr><td colspan='2'><b>Old GA product with Model Planned Availability</b></td></tr>"+NEWLINE);
                	ArrayList fcctryList = new ArrayList();
                    getCountriesAsList(featItem, fcctryList,CHECKLEVEL_NOOP); 
                    //The Country of interest is taken from the list produced by the intersection of the PRODSTRUCT�s 
            		//parent FEATURE COUNTRYLIST and the PRODSTRUCT�s child MODEL�s AVAIL COUNTRYLIST where 
            		//AVAILTYPE = �Planned Availability�.
                	ArrayList mdlctryList = getCountriesAsList(mdlPlaAvailVct, CHECKLEVEL_NOOP);
                	fcctryList.retainAll(mdlctryList);
                	 
                	 // this is an old GA product with a model that has a planned avail
                	sb.append("<tr><td title='"+psItem.getKey()+"'>"+psItem.getEntityGroup().getLongDescription()+": "+
                			PokUtils.getAttributeDescription(psItem.getEntityGroup(), "GENAVAILDATE", "GENAVAILDATE")+
                			": </td><td>"+PokUtils.getAttributeValue(psItem, "GENAVAILDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
            		sb.append("<tr><td title='"+featItem.getKey()+"'>"+featItem.getEntityGroup().getLongDescription()+": "+
             				PokUtils.getAttributeDescription(featItem.getEntityGroup(), "GENAVAILDATE", "GENAVAILDATE")+
             				": </td><td>"+PokUtils.getAttributeValue(featItem, "GENAVAILDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
                   	sb.append("<tr><td title='"+featItem.getKey()+"'>"+featItem.getEntityGroup().getLongDescription()+": "+
             				PokUtils.getAttributeDescription(featItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST")+
             				": </td><td>"+PokUtils.getAttributeValue(featItem, "COUNTRYLIST", ", ", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);

            		if(fcctryList.size()>0){
            			for(int x=0; x<mdlPlaAvailVct.size(); x++){
            				EntityItem mdlavail = (EntityItem)mdlPlaAvailVct.elementAt(x);
            				ArrayList ctryList = new ArrayList();
            				getCountriesAsList(mdlavail, ctryList,CHECKLEVEL_NOOP); 
            				ctryList.retainAll(fcctryList);
            				if(ctryList.size()>0){
            					sb.append("<tr><td colspan='2' title='"+mdlItem.getKey()+
                						":"+mdlavail.getKey()+"'>Model "+getLD_NDN(mdlavail)+"</td></tr>"+NEWLINE);
            					sb.append("<tr><td title='"+mdlavail.getKey()+"'>"+mdlavail.getEntityGroup().getLongDescription()+": "+
            							PokUtils.getAttributeDescription(mdlavail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST")+
            							": </td><td>"+PokUtils.getAttributeValue(mdlavail, "COUNTRYLIST", ", ", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
            					ctryList.clear();
            				}
            			}
            		}else{
            			sb.append("<tr><td colspan='2'>No intersection in Model Planned Availability CountryList and the Feature</td></tr>"+NEWLINE);
            		}
                  	sb.append("<tr><td title='"+psItem.getKey()+"'>"+psItem.getEntityGroup().getLongDescription()+": "+
                			PokUtils.getAttributeDescription(psItem.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE")+
                			": </td><td>"+PokUtils.getAttributeValue(psItem, "WTHDRWEFFCTVDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
            		sb.append("<tr><td title='"+featItem.getKey()+"'>"+featItem.getEntityGroup().getLongDescription()+": "+
             				PokUtils.getAttributeDescription(featItem.getEntityGroup(), "WITHDRAWDATEEFF_T", "WITHDRAWDATEEFF_T")+
             				": </td><td>"+PokUtils.getAttributeValue(featItem, "WITHDRAWDATEEFF_T", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
                 	sb.append("<tr><td title='"+mdlItem.getKey()+"'>"+mdlItem.getEntityGroup().getLongDescription()+": "+
                			PokUtils.getAttributeDescription(mdlItem.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE")+
                			": </td><td>"+PokUtils.getAttributeValue(mdlItem, "WTHDRWEFFCTVDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
             	
                 	if(fcctryList.size()>0){
            			for(int x=0; x<mdlLoAvailVct.size(); x++){
            				EntityItem mdlavail = (EntityItem)mdlLoAvailVct.elementAt(x);
            				ArrayList ctryList = new ArrayList();
            				getCountriesAsList(mdlavail, ctryList,CHECKLEVEL_NOOP); 
            				ctryList.retainAll(fcctryList);
            				if(ctryList.size()>0){
            					sb.append("<tr><td colspan='2' title='"+mdlItem.getKey()+
                						":"+mdlavail.getKey()+"'>Model "+getLD_NDN(mdlavail)+"</td></tr>"+NEWLINE);
            					sb.append("<tr><td title='"+mdlavail.getKey()+"'>"+mdlavail.getEntityGroup().getLongDescription()+": "+
            							PokUtils.getAttributeDescription(mdlavail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST")+
            							": </td><td>"+PokUtils.getAttributeValue(mdlavail, "COUNTRYLIST", ", ", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
            					ctryList.clear();
            				}
            			}
            		}
                 	mdlPlaAvailVct.clear();
                 	mdlLoAvailVct.clear();
                 	fcctryList.clear();
                }else{// no planned avail
 /*        			Else
        			The Country is taken from the PRODSTRUCT�s parent FEATURE COUNTRYLIST.

        			The TPICF is equal to the first one that applies:
        				a)	PRODSTRUCT.GENAVAILDATE or if empty
        				b)	MAX{MODEL.ANNDATE; FEATURE.GENAVAILDATE} 

        				The TPICT is equal to the first one that applies:
        				a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
        				b)	MIN{MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) or if empty 
*/
                	sb.append("<tr><td colspan='2'><b>Old GA product with no Model Planned Availability</b></td></tr>"+NEWLINE);
                	sb.append("<tr><td title='"+psItem.getKey()+"'>"+psItem.getEntityGroup().getLongDescription()+": "+
                			PokUtils.getAttributeDescription(psItem.getEntityGroup(), "GENAVAILDATE", "GENAVAILDATE")+
                			": </td><td>"+PokUtils.getAttributeValue(psItem, "GENAVAILDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
            		sb.append("<tr><td title='"+featItem.getKey()+"'>"+featItem.getEntityGroup().getLongDescription()+": "+
             				PokUtils.getAttributeDescription(featItem.getEntityGroup(), "GENAVAILDATE", "GENAVAILDATE")+
             				": </td><td>"+PokUtils.getAttributeValue(featItem, "GENAVAILDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
                	sb.append("<tr><td title='"+mdlItem.getKey()+"'>"+mdlItem.getEntityGroup().getLongDescription()+": "+
                			PokUtils.getAttributeDescription(mdlItem.getEntityGroup(), "ANNDATE", "ANNDATE")+
                			": </td><td>"+PokUtils.getAttributeValue(mdlItem, "ANNDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
                	sb.append("<tr><td title='"+psItem.getKey()+"'>"+psItem.getEntityGroup().getLongDescription()+": "+
                			PokUtils.getAttributeDescription(psItem.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE")+
                			": </td><td>"+PokUtils.getAttributeValue(psItem, "WTHDRWEFFCTVDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
            		sb.append("<tr><td title='"+featItem.getKey()+"'>"+featItem.getEntityGroup().getLongDescription()+": "+
             				PokUtils.getAttributeDescription(featItem.getEntityGroup(), "WITHDRAWDATEEFF_T", "WITHDRAWDATEEFF_T")+
             				": </td><td>"+PokUtils.getAttributeValue(featItem, "WITHDRAWDATEEFF_T", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
                 	sb.append("<tr><td title='"+mdlItem.getKey()+"'>"+mdlItem.getEntityGroup().getLongDescription()+": "+
                			PokUtils.getAttributeDescription(mdlItem.getEntityGroup(), "WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE")+
                			": </td><td>"+PokUtils.getAttributeValue(mdlItem, "WTHDRWEFFCTVDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
       
                	sb.append("<tr><td title='"+featItem.getKey()+"'>"+featItem.getEntityGroup().getLongDescription()+": "+
                 				PokUtils.getAttributeDescription(featItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST")+
                 				": </td><td>"+PokUtils.getAttributeValue(featItem, "COUNTRYLIST", ", ", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
                }
                mdlAvailVct.clear();
    		}else{
    			sb.append("<tr><td colspan='2'><b>GA product</b></td></tr>"+NEWLINE);
    			/*
IF PRODSTRUCT-u: FEATURE.FCTYPE = �Primary FC (100) | "Secondary FC" (110)� THEN
�	{LD: AVAIL} {NDN: AVAIL where AVAILTYPE = �Planned Availability�} 
�	{LD: AVAIL} {NDN: AVAIL where AVAILTYPE = �Last Order�}
    			 */
    			String to = "No Last Order "+tpic.fromItem.getEntityGroup().getLongDescription();
    			if(tpic.toItem!=null){
    				to = getLD_NDN(tpic.toItem);
    			}
    			sb.append("<tr><td colspan='2' title='"+tpic.fromItem.getKey()+"'>"+getLD_NDN(tpic.fromItem)+"</td></tr>"+NEWLINE);
            	sb.append("<tr><td title='"+tpic.fromItem.getKey()+"'>"+tpic.fromItem.getEntityGroup().getLongDescription()+": "+
            			PokUtils.getAttributeDescription(tpic.fromItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST")+
            			": </td><td>"+PokUtils.getAttributeValue(tpic.fromItem, "COUNTRYLIST", ", ", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
    			sb.append("<tr><td colspan='2'>"+to+"</td></tr>"+NEWLINE);
    			if(tpic.toItem!=null){
    	           	sb.append("<tr><td title='"+tpic.toItem.getKey()+"'>"+tpic.toItem.getEntityGroup().getLongDescription()+": "+
                			PokUtils.getAttributeDescription(tpic.toItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST")+
                			": </td><td>"+PokUtils.getAttributeValue(tpic.toItem, "COUNTRYLIST", ", ", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);				
    			}
    		}
    	}
/*
�	{LD: WARR} {NDN: WARR}
�	From PRODSTRUCTWARR
o	{LD: EFFECTIVEDATE}{EFFECTIVEDATE}
o	{LD: ENDDATE}{ENDDATE}
o	{LD: DEFWARR}{DEFWARR}
o	{LD: COUNTRYLIST}{COUNTRYLIST}
 */    	
    	if(warrTpicVct!=null && warrTpicVct.size()>0){
    		for (int x=0; x<warrTpicVct.size(); x++){
    			WarrTPIC warrTpic = (WarrTPIC)warrTpicVct.elementAt(x);             
        		sb.append("<tr><td colspan='2' title='"+warrTpic.warrItem.getKey()+"'><b>"+getLD_NDN(warrTpic.warrItem)+"</b></td></tr>"+NEWLINE);
        		sb.append("<tr><td title='"+warrTpic.warrRel.getKey()+"'>"+warrTpic.warrRel.getEntityGroup().getLongDescription()+": "+
         				PokUtils.getAttributeDescription(warrTpic.warrRel.getEntityGroup(), "EFFECTIVEDATE", "EFFECTIVEDATE")+
         				": </td><td>"+PokUtils.getAttributeValue(warrTpic.warrRel, "EFFECTIVEDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
        		sb.append("<tr><td>"+warrTpic.warrRel.getEntityGroup().getLongDescription()+": "+
         				PokUtils.getAttributeDescription(warrTpic.warrRel.getEntityGroup(), "ENDDATE", "ENDDATE")+
         				": </td><td>"+PokUtils.getAttributeValue(warrTpic.warrRel, "ENDDATE", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
           		sb.append("<tr><td>"+warrTpic.warrRel.getEntityGroup().getLongDescription()+": "+
         				PokUtils.getAttributeDescription(warrTpic.warrRel.getEntityGroup(), "DEFWARR", "DEFWARR")+
         				": </td><td>"+PokUtils.getAttributeValue(warrTpic.warrRel, "DEFWARR", "", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
           		sb.append("<tr><td>"+warrTpic.warrRel.getEntityGroup().getLongDescription()+": "+
         				PokUtils.getAttributeDescription(warrTpic.warrRel.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST")+
         				": </td><td>"+PokUtils.getAttributeValue(warrTpic.warrRel, "COUNTRYLIST", ", ", PokUtils.DEFNOTPOPULATED)+"</td></tr>"+NEWLINE);
    		}
    	}else{
    		// no warr
    		sb.append("<tr><td colspan='2'><b>No "+m_elist.getEntityGroup("WARR").getLongDescription()+" found</b></td></tr>"+NEWLINE);
    	}
    	
		sb.append("</table>"+NEWLINE);
		
		addUserAndErrorMsg(sb.toString(), null);
    }

    /**
     * get WARR entities by country for specified prodstruct
     *
        The applicable Warranty of a TMF (PRODSTRUCT) is specified via the PRODSTRUCTWARR attributes for 
        the Warranty (WARR):
        �	Country � COUNTRYLIST
        �	From Date � EFFECTIVEDATE   If empty, assume �1980-01-01�
        �	To Date � ENDDATE   If empty, assume �9999-12-31� 

        A PRODSTRUCTWARR with attribute code DEFWARR = �Yes� is valid for all countries that do not have a specific WARR. 
        The EFFECTIVEDATE and ENDDATE are still considered by Country. If you have a DEFWARR on one FEATURE (PRODSTRUCT), 
        you must have it on all PRODSTRUCTs for that FEATURE in the same Machine Type.
     * @param psitem
     * @param availCtrySet -  set of countries this prodstruct is available in
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     */
    private Hashtable getWarrTpicByCtry(EntityItem psitem, Set availCtrySet) throws SQLException, MiddlewareException
    {
        Hashtable warrtbl = new Hashtable();
        Vector vct = this.getDownLinkEntityItems(psitem, "PRODSTRUCTWARR");
        for (int i=0; i<vct.size(); i++){
            EntityItem warrrel = (EntityItem)vct.elementAt(i);
            String defwarr = PokUtils.getAttributeFlagValue(warrrel, "DEFWARR");
            EntityItem warr = (EntityItem)warrrel.getDownLink(0);
            ArrayList ctryList = new ArrayList();
            getCountriesAsList(warrrel, ctryList, CHECKLEVEL_NOOP);
            addDebug("getWarrTpicByCtry "+warrrel.getKey()+" "+warr.getKey()+" defwarr "+defwarr);
            //A PRODSTRUCTWARR with attribute code DEFWARR = �Yes� is valid for all countries that do not have a
            //specific WARR. The EFFECTIVEDATE and ENDDATE are still considered by Country.
            if(DEFWARR_Yes.equals(defwarr)){
                // add all countries
                EANFlagAttribute att = (EANFlagAttribute)warrrel.getAttribute("COUNTRYLIST");
                MetaFlag[] mfArray = (MetaFlag[]) att.get();
                for (int im = 0; im < mfArray.length; im++) {
                    if (!mfArray[im].isExpired() && !ctryList.contains(mfArray[im].getFlagCode())) {
                    	// limit the set to the countries the product is available in, dont need all others
                    	if(availCtrySet.contains(mfArray[im].getFlagCode())){
                    		ctryList.add(mfArray[im].getFlagCode());
                    	}else{
                    		// bypassing this country
                            addDebug("getWarrTpicByCtry bypassing "+mfArray[im].getFlagCode()+" ps not offered there");
                    	}
                    }
                } //end for
            }

            Iterator itr = ctryList.iterator();
            while (itr.hasNext()) {
                String ctryflag = (String) itr.next();
                Vector idvct = (Vector)warrtbl.get(ctryflag);
                if(idvct==null){
                    idvct = new Vector();
                    warrtbl.put(ctryflag, idvct);
                }
                idvct.add(new WarrTPIC(ctryflag, warr, warrrel));
            }
        }

        return warrtbl;
    }

    /**
     * get TPIC by country..approach varies based on product type
     * find earliest and lastest dates by country
     *
     * @param psItem
     * @param featItem
     * @param mdlItem
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     * @throws StopWarrException 
     */
    private Hashtable getTpicByCtry(EntityItem psItem, EntityItem featItem,EntityItem mdlItem) 
    throws SQLException, MiddlewareException, StopWarrException
    {
        Hashtable availtbl = new Hashtable();
        if(isRPQ){
        	//3.	RPQ Products        	
        	//The TPICF is equal to the first one that applies:
        	//a)	PRODSTRUCT.ANNDATE or if empty
        	//b)	MAX{MODEL.ANNDATE; FEATURE.ANNDATE} 
            String fromdate = prodstructValidFrom(psItem, featItem, mdlItem); 
        	
        	//The TPICT is equal to the first one that applies:
        	//a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
        	//b)	MIN{MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) or if empty 
        	//c)	�9999-12-31�.         
            String todate = prodstructValidTo(psItem, featItem, mdlItem);
            addDebug("getTpicByCtry isRPQ "+psItem.getKey()+" "+featItem.getKey()+" "+mdlItem.getKey()+
                    " fromdate: "+fromdate+" todate "+todate);

        	//The Country is taken from the list produced by the intersection of the PRODSTRUCT�s parent FEATURE 
        	//COUNTRYLIST and the PRODSTRUCT�s child MODEL�s AVAIL COUNTRYLIST where AVAILTYPE = �Planned Availability�.
            ArrayList ctryList = new ArrayList();
            getCountriesAsList(featItem, ctryList,CHECKLEVEL_NOOP); 
            addDebug("getTpicByCtry isRPQ feature ctrylist "+ctryList);

            Vector availVct = PokUtils.getAllLinkedEntities(mdlItem, "MODELAVAIL", "AVAIL");
			Vector plannedavailVector = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability
		    addDebug("getTpicByCtry isRPQ "+mdlItem.getKey()+" availVct "+availVct.size()+
		    		" plannedavailVector "+plannedavailVector.size());
            if(plannedavailVector.size()>0){
            	ArrayList mdlctryList = getCountriesAsList(plannedavailVector, CHECKLEVEL_NOOP);
                addDebug("getTpicByCtry isRPQ model ctrylist "+mdlctryList);
                ctryList.retainAll(mdlctryList);
                addDebug("getTpicByCtry isRPQ intersection ctrylist "+ctryList);
            }
            
            Iterator itr = ctryList.iterator();
            while (itr.hasNext()) {
                String ctryflag = (String) itr.next();
                availtbl.put(ctryflag,new TPIC(ctryflag,fromdate,todate));
            }
            ctryList.clear();
            availVct.clear();
            plannedavailVector.clear();
        }else{
        	//1.	GA Products
        	//	If the parent FEATURE�s FCTYPE is �Primary FC� (100) | "Secondary FC" (110), then the PRODSTRUCT is a GA Product.
        	//	The Country of interest is taken from the AVAIL COUNTRYLIST where AVAILTYPE = �Planned Availability�
        	//	The TPIC From Date� (TPICF) is 
        	//	�	the MAX (AVAIL EFFECTIVEDATE where AVAILTYPE = �Planned Availability�) by Country for the pair of PRODSTRUCTs
        	// The TPIC �To Date� (TPICT) is 
        	//	�	the MIN(AVAIL EFFECTIVEDATE) where AVAILTYPE = �Last Order� by Country for the pair of PRODSTRUCTs. 
        	if(!isOldGAProduct(psItem, mdlItem)){
        		getGATpicByCtry(availtbl,psItem, featItem, mdlItem);
        	}else{
        	//2.	GA Products � Old Data
                Vector mdlAvailVct = PokUtils.getAllLinkedEntities(mdlItem, "MODELAVAIL", "AVAIL");
    			Vector mdlPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability
    			Vector mdlLoAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailVct, "AVAILTYPE", LASTORDERAVAIL);//Lastorder Availability
    		    addDebug("getTpicByCtry isoldGA "+psItem.getKey()+" "+mdlItem.getKey()+" mdlavailVct "+mdlAvailVct.size()+
    		    		" mdlPlaAvailVct "+mdlPlaAvailVct.size()+" mdlLoAvailVct "+mdlLoAvailVct.size());

    		    //	If the PRODSTRUCT�s child MODEL has an AVAIL where AVAILTYPE = �Planned Availability� then 
                if(mdlPlaAvailVct.size()>0){  
                	getOldGATpicByCtry(availtbl,psItem, featItem, mdlItem,mdlPlaAvailVct,mdlLoAvailVct);
                }else{ //		Else
                	//The Country is taken from the PRODSTRUCT�s parent FEATURE COUNTRYLIST.
                    ArrayList ctryList = new ArrayList();
                    getCountriesAsList(featItem, ctryList,CHECKLEVEL_NOOP); 
                    addDebug("getTpicByCtry oldGA feature ctrylist "+ctryList);
        		
                	//The TPICF is equal to the first one that applies:
                	//a)	PRODSTRUCT.GENAVAILDATE or if empty
                	//b)	MAX{MODEL.ANNDATE; FEATURE.GENAVAILDATE} 
                    String fromdate = oldGaValidFrom(psItem, featItem, mdlItem); 

                    //The TPICT is equal to the first one that applies:
            		//a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
            		//b)	MIN{MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) or if empty 
            		//c)	�9999-12-31�.
                    String todate = prodstructValidTo(psItem, featItem, mdlItem);
                    addDebug("getTpicByCtry oldGA "+psItem.getKey()+" "+featItem.getKey()+" "+mdlItem.getKey()+
                            " fromdate: "+fromdate+" todate "+todate);
                    //ENDIF
                    Iterator itr = ctryList.iterator();
                    while (itr.hasNext()) {
                        String ctryflag = (String) itr.next();
                        availtbl.put(ctryflag,new TPIC(ctryflag,fromdate,todate));
                    }
                    ctryList.clear();
        		}
                mdlAvailVct.clear();
                mdlPlaAvailVct.clear();
                mdlLoAvailVct.clear();
        	}
        }

        return availtbl;
    }
    
    /**
     * Old data is defined as GA Products that do not have an AVAIL of AVAILTYPE = �Planned Availability� and 
	were announced prior to 3/1/2010. The announcement date for a PRODSTRUCT is:
	�	PRODSTRUCT ANNDATE if not empty
	�	MODEL ANNDATE 
	
	If the �Old data� criteria are not met, do not proceed with this section. The DQ ABR should fail and not advance 
	Status. The error text is (see Key 18.00 in the attached spreadsheet on the PRODSTRUCT tab; however, the RE*1 does 
	not apply in this case):
        		must have at least one "Planned Availability"
     * @param psItem
     * @param mdlItem
     * @return
     * @throws StopWarrException
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private boolean isOldGAProduct(EntityItem psItem, EntityItem mdlItem) throws StopWarrException, SQLException, MiddlewareException
    {
    	boolean isolddata = false;
        Vector availVct = PokUtils.getAllLinkedEntities(psItem, "OOFAVAIL", "AVAIL");
        Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
        if(plannedAvailVct.size()==0){
        	// check the ps anndate
    		String annDate = PokUtils.getAttributeValue(psItem, "ANNDATE", "", "", false);
			if(annDate.length()==0){
				// check the model
				annDate = PokUtils.getAttributeValue(mdlItem, "ANNDATE", "", "", false);
		        addDebug("isOldGAProduct "+mdlItem.getKey()+" anndate "+annDate);
			}else{
			    addDebug("isOldGAProduct "+psItem.getKey()+" anndate "+annDate);
			}
			if(annDate.length()>0){
				if(OLD_DATA_ANNDATE.compareTo(annDate)>=0){
					isolddata =true;
				}
			}
			if(!isolddata){
				//The error text is (see Key 18.00 in the attached spreadsheet on the PRODSTRUCT tab; however, the RE*1 does 
				//not apply in this case):
	        	//	must have at least one "Planned Availability"
				//MINIMUM_ERR = must have at least one {0}
				//MINIMUM2_ERR = {0} must have at least one {1}
				if (psItem.getEntityID()==getEntityID()){
					args[0] = "Planned Availability. Old data criteria was not met.";
					createMessage(CHECKLEVEL_E,"MINIMUM_ERR",args);	
				}else{
					args[0] = getLD_NDN(psItem);
					args[1] = "Planned Availability. Old data criteria was not met.";
					createMessage(CHECKLEVEL_E,"MINIMUM2_ERR",args);
				}
		        availVct.clear();
		        plannedAvailVct.clear();
				throw new StopWarrException();
			}
        }
        availVct.clear();
        plannedAvailVct.clear();
        return isolddata;
    }
    
	/**
	 * this is an old GA product with a model that has a planned avail
	 * The TPICF is equal to the first one that applies:
	a)	PRODSTRUCT.GENAVAILDATE or if empty
	b)	MAX{MODEL via MODELAVAIL-d: AVAIL EFFECTIVEDATE where AVAILTYPE = �Planned Availability�; 
		FEATURE.GENAVAILDATE} 
	or if empty 
	c)	�9999-12-31� 

	The TPICT is equal to the first one that applies:
	a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
	b)	MIN(MODEL via MODELAVAIL-d: AVAIL EFFECTIVEDATE where AVAILTYPE = �Last Order� or if it doesn�t exist for the 
	Country of interest then MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) or if empty 
	c)	�9999-12-31�. 
	 * @param availtbl
	 * @param psItem
	 * @param featItem
	 * @param model
	 * @param plannedavailVct
	 * @param loavailVct
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void getOldGATpicByCtry(Hashtable availtbl,EntityItem psItem, EntityItem featItem, 
			EntityItem model, Vector mdlPlaAvailVct,Vector mdlLoAvailVct) throws SQLException, MiddlewareException
	{
        ArrayList fcctryList = new ArrayList();
        getCountriesAsList(featItem, fcctryList,CHECKLEVEL_NOOP); 
        addDebug("getOldGATpicByCtry feature ctrylist "+fcctryList);
        //The Country of interest is taken from the list produced by the intersection of the PRODSTRUCT�s 
		//parent FEATURE COUNTRYLIST and the PRODSTRUCT�s child MODEL�s AVAIL COUNTRYLIST where 
		//AVAILTYPE = �Planned Availability�.
    	ArrayList mdlctryList = getCountriesAsList(mdlPlaAvailVct, CHECKLEVEL_NOOP);
       	ArrayList mdlloctryList = getCountriesAsList(mdlLoAvailVct, CHECKLEVEL_NOOP);
        addDebug("getOldGATpicByCtry model plactrylist "+mdlctryList+" mdlloctryList "+mdlloctryList);
        fcctryList.retainAll(mdlctryList);
        addDebug("getOldGATpicByCtry intersection ctrylist "+fcctryList);
        if(fcctryList.size()==0){
        	//no match on country, so skip it
        	return;
        }
        
        String psfromdate = PokUtils.getAttributeValue(psItem, "GENAVAILDATE", "", null, false);
        String pstodate = PokUtils.getAttributeValue(psItem, "WTHDRWEFFCTVDATE", "", null, false);
        addDebug("getOldGATpicByCtry "+psItem.getKey()+" psfromdate "+psfromdate+" pstodate "+pstodate);
        String fcfromdate = PokUtils.getAttributeValue(featItem, "GENAVAILDATE", "", EPOCH_DATE, false);
        String fctodate = PokUtils.getAttributeValue(featItem, "WITHDRAWDATEEFF_T", "", null, false);
        String mdltodate = PokUtils.getAttributeValue(model, "WTHDRWEFFCTVDATE", "", null, false);
        addDebug("getOldGATpicByCtry "+featItem.getKey()+" fcfromdate "+fcfromdate+
        		" fctodate "+fctodate+" mdltodate "+mdltodate);
        	
        //sort avails find earliest date by ctry and latest date by ctry
        AttrComparator attrComp = new AttrComparator("EFFECTIVEDATE");
        Collections.sort(mdlPlaAvailVct, attrComp);
        if(mdlLoAvailVct.size()>0){
        	Collections.sort(mdlLoAvailVct, attrComp);
        }

        for (int i=mdlPlaAvailVct.size()-1; i>=0; i--){ // go in reverse, getting earliest date last
        	ArrayList ctryList = new ArrayList();
        	EntityItem plaAvail = (EntityItem)mdlPlaAvailVct.elementAt(i);
        	getCountriesAsList(plaAvail, ctryList,CHECKLEVEL_NOOP);
        	addDebug("getOldGATpicByCtry mdl pla "+plaAvail.getKey()+" ctrylist "+ctryList);
        	//does this avail match any of the fcctrylist
        	ctryList.retainAll(fcctryList);  // only want matches
        	
        	if(ctryList.size()==0){
        		addDebug("getOldGATpicByCtry no ctry matches found in "+plaAvail.getKey()+
        				" ctrylist and fcctryList "+fcctryList);
        		continue;
        	}
        
        	//The TPICF is equal to the first one that applies:
        	//a)	PRODSTRUCT.GENAVAILDATE or if empty
        	//b)	MAX{MODEL via MODELAVAIL-d: AVAIL EFFECTIVEDATE where AVAILTYPE = �Planned Availability�; 
        	//	FEATURE.GENAVAILDATE} 
        	//or if empty 
        	//c)	�9999-12-31� this cant happen because AVAIL.EFFECTIVEDATE is required
        	String fromdate = psfromdate;
        	if(fromdate==null){
        		String plafromdate = PokUtils.getAttributeValue(plaAvail, "EFFECTIVEDATE", "", EPOCH_DATE, false);
        		addDebug("getOldGATpicByCtry mdl "+plaAvail.getKey()+" fcfromdate "+fcfromdate+" plafromdate "+plafromdate);
        		if(plafromdate.compareTo(fcfromdate)>=0){
        			fromdate=plafromdate;
        		}else{
        			fromdate=fcfromdate;
        		}
        	}
    

        	//The TPICT is equal to the first one that applies:
        	//a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
        	//b)	MIN(MODEL via MODELAVAIL-d: AVAIL EFFECTIVEDATE where AVAILTYPE = �Last Order� or if it 
        	//doesn�t exist for the Country of interest then MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) 
        	//or if empty 
        	//c)	�9999-12-31�. 
        	//if there is no lo avail, do the calculation here
        	String todate = pstodate;
        	if(todate==null){
        		if(mdltodate!=null && fctodate !=null){
        			if(mdltodate.compareTo(fctodate)<0){
        				todate=mdltodate;
        			}else{
        				todate=fctodate;
        			}
        		}else if(mdltodate!=null){
        			todate=mdltodate;
        		}else{
        			if(fctodate !=null){
        				todate = fctodate;
        			}else{
        				todate=FOREVER_DATE;
        			}
        		}
        	}
        	Iterator itr = ctryList.iterator();
        	while (itr.hasNext()) {
        		String ctryflag = (String) itr.next();
        		TPIC tpic = (TPIC)availtbl.get(ctryflag);
        		if(tpic!=null){
        			tpic.fromDate=fromdate;// override with earlier date for this country
        			tpic.fromItem = plaAvail;
        		}else{
        			String ctrytodate = FOREVER_DATE;
        			if(!mdlloctryList.contains(ctryflag)){ // no lo avail so use calculated date
        				ctrytodate=todate;
        			}
        			tpic = new TPIC(ctryflag,fromdate,ctrytodate);
        			tpic.fromItem = plaAvail;
        			availtbl.put(ctryflag,tpic);
        		}
        	}
        	ctryList.clear();
        }
        for (int i=0; i<mdlLoAvailVct.size(); i++){ // go forward, getting latest date last
        	ArrayList ctryList = new ArrayList();
        	EntityItem loAvail = (EntityItem)mdlLoAvailVct.elementAt(i);
        	getCountriesAsList(loAvail, ctryList,CHECKLEVEL_NOOP);
        	
        	addDebug("getOldGATpicByCtry mdl lo "+loAvail.getKey()+" ctrylist "+ctryList);
        	//does this avail match any of the fcctrylist
        	ctryList.retainAll(fcctryList);  // only want matches
        	
        	if(ctryList.size()==0){
        		addDebug("getOldGATpicByCtry no ctry matches found in "+loAvail.getKey()+
        				" ctrylist and fcctryList "+fcctryList);
        		continue;
        	}
        	
        	//The TPICT is equal to the first one that applies:
        	//a)	PRODSTRUCT.WTHDRWEFFCTVDATE or if empty
        	//b)	MIN(MODEL via MODELAVAIL-d: AVAIL EFFECTIVEDATE where AVAILTYPE = �Last Order� or if it 
        	//doesn�t exist for the Country of interest then MODEL.WTHDRWEFFCTVDATE; FEATURE.WITHDRAWDATEEFF_T) 
        	//or if empty 
        	//c)	�9999-12-31�. 
        	String todate = pstodate;
        	if(todate==null){
        		String lotodate = PokUtils.getAttributeValue(loAvail, "EFFECTIVEDATE", "", "", false);
        		addDebug("getOldGATpicByCtry mdlloavail "+loAvail.getKey()+" fctodate "+fctodate+" lotodate "+lotodate);
        		if(fctodate !=null){
        			if(lotodate.compareTo(fctodate)<0){
            			todate=lotodate;
            		}else{
            			todate=fctodate;
            		}
    			}else{
    				todate=lotodate;
    			}
        	}

        	Iterator itr = ctryList.iterator();
        	while (itr.hasNext()) {
        		String ctryflag = (String) itr.next();
        		TPIC tpic = (TPIC)availtbl.get(ctryflag);
        		if(tpic!=null){
        			if(!tpic.toDate.equals(pstodate)){
        				tpic.toDate=todate; // override with later date if found
        			}
        			tpic.toItem = loAvail;
        		}
        	}
        	ctryList.clear();
        }

       //release memory
        fcctryList.clear();
        mdlctryList.clear();
       	mdlloctryList.clear();
	}
    /**
     * The Country of interest is taken from the AVAIL COUNTRYLIST where AVAILTYPE = �Planned Availability�
	The TPIC From Date� (TPICF) is 
	�	the MAX (AVAIL EFFECTIVEDATE where AVAILTYPE = �Planned Availability�) by Country for the pair of PRODSTRUCTs
	
	The TPIC �To Date� (TPICT) is 
	�	the MIN(AVAIL EFFECTIVEDATE) where AVAILTYPE = �Last Order� by Country for the pair of PRODSTRUCTs. 
	
	Note: there may be more than one AVAIL of these types and hence the From Date and To Date can vary by Country

     * @param availtbl
     * @param psItem
     * @param featItem
     * @param mdlItem
     * @throws SQLException
     * @throws MiddlewareException
     */
    private void getGATpicByCtry(Hashtable availtbl,EntityItem psItem, 
    		EntityItem featItem,EntityItem mdlItem) throws SQLException, MiddlewareException
    {
        //look at planned avails
        Vector availVct = PokUtils.getAllLinkedEntities(psItem, "OOFAVAIL", "AVAIL");
        Vector plaAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
        Vector loAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", LASTORDERAVAIL);

        addDebug("getTpicByCtry  "+psItem.getKey()+" availVct: "+
                availVct.size()+" plaAvailVct: "+plaAvailVct.size()+" loAvailVct "+loAvailVct.size());
        //sort avails find earliest date by ctry and latest date by ctry
        AttrComparator attrComp = new AttrComparator("EFFECTIVEDATE");
        Collections.sort(plaAvailVct, attrComp);
        if(loAvailVct.size()>0){
        	Collections.sort(loAvailVct, attrComp);
        }

        for (int i=plaAvailVct.size()-1; i>=0; i--){ // go in reverse, getting earliest date last
            ArrayList ctryList = new ArrayList();
            EntityItem plaAvail = (EntityItem)plaAvailVct.elementAt(i);
            String fromdate = PokUtils.getAttributeValue(plaAvail, "EFFECTIVEDATE", "", "", false);
            getCountriesAsList(plaAvail, ctryList,CHECKLEVEL_NOOP);

            Iterator itr = ctryList.iterator();
            while (itr.hasNext()) {
                String ctryflag = (String) itr.next();
                TPIC tpic = (TPIC)availtbl.get(ctryflag);
                if(tpic!=null){
                    tpic.fromDate=fromdate;// override with earlier date for this country
                    tpic.fromItem = plaAvail;
                }else{
                	tpic = new TPIC(ctryflag,fromdate);
                    tpic.fromItem = plaAvail;
                    availtbl.put(ctryflag,tpic);
                }
            }
            ctryList.clear();
        }
        for (int i=0; i<loAvailVct.size(); i++){ // go forward, getting latest date last
            ArrayList ctryList = new ArrayList();
            EntityItem loAvail = (EntityItem)loAvailVct.elementAt(i);
            String todate = PokUtils.getAttributeValue(loAvail, "EFFECTIVEDATE", "", "", false);
            getCountriesAsList(loAvail, ctryList,CHECKLEVEL_NOOP);

            Iterator itr = ctryList.iterator();
            while (itr.hasNext()) {
                String ctryflag = (String) itr.next();
                TPIC tpic = (TPIC)availtbl.get(ctryflag);
                if(tpic!=null){
                    tpic.toDate=todate; // override with later date if found
                    tpic.toItem = loAvail;
                }
            }
            ctryList.clear();
        }

        // release memory
        availVct.clear();
        plaAvailVct.clear();
        loAvailVct.clear();   	
    }
    /*******************************************
     * check dates on an RPQ feature
     * @param featItem
     * @param checklvl
    	8.00	WHEN		FCTYPE	<>	Primary FC (100) | "Secondary FC" (110)						RPQ logic	
		20120906 Change col N	9.00			FIRSTANNDATE	<=	PRODSTRUCT	ANNDATE		W	W	E		{LD: ANNDATE} {ANNDATE} must not be earlier than the {LD: FEATURE} {NDN:FEATURE}{LD: FIRSTANNDATE} {FIRSTANNDATE}
		20120906 Change col N	10.00			GENAVAILDATE	<=	PRODSTRUCT	GENAVAILDATE		W	W	E		{LD: GENAVAILDATE} {GENAVAILDATE} must not be earlier than the {LD: FEATURE} {NDN:FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
		20120906 Change col N	11.00			WITHDRAWANNDATE_T	=>	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: WITHDRAWDATE} {WITHDRAWDATE} must not be later than {LD: FEATURE}  {NDN:FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
		20120906 Change col N	12.00			WITHDRAWDATEEFF_T	=>	PRODSTRUCT	WTHDRWEFFCTVDATE		W	W	E		{LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE} must not be later than {LD: FEATURE}  {NDN:FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
		20120906 Add	12.10	MODEL		PRODSTRUCT-d									
		20120906 Add	12.12	IF		"Initial"	=	PRODSTRUCT	ORDERCODE					Initial means that it can only be ordered when the MODEL is available	
		20120906 Add	12.14			WITHDRAWDATE	=>	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: WITHDRAWDATE} {WITHDRAWDATE} must not be later than {LD: MODEL} {NDN: MODEL} {LD: WITHDRAWDATE} {N: WITHDRAWDATE}
		20120906 Add	12.16	END	12.12										
		12.20	AVAIL		PRODSTRUCT-u:OOFAVAIL-d									
		12.30			CountOf	=	0			E	E	E		RPQs must not have an {LD: AVAIL} {NDN: AVAIL}
		13.00	END	8.00
     * @throws MiddlewareException
     * @throws SQLException
     */
    private void checkRPQFeature(EntityItem psItem,EntityItem featItem, int checklvl)
    throws SQLException, MiddlewareException
    {
        addHeading(3,featItem.getEntityGroup().getLongDescription()+" RPQ Checks:");

        //8.00  WHEN        FCTYPE  <>  Primary FC (100) | "Secondary FC" (110)             RPQ logic
        addDebug(featItem.getKey()+" was an RPQ FCTYPE: "+getAttributeFlagEnabledValue(featItem, "FCTYPE"));

        //9.00          FIRSTANNDATE    <=  PRODSTRUCT  ANNDATE     W   W   E
        //{LD: ANNDATE} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
        checkCanNotBeEarlier(psItem, "ANNDATE", featItem,"FIRSTANNDATE", checklvl);

        //10.00         GENAVAILDATE    <=  PRODSTRUCT  GENAVAILDATE        W   W   E
        //{LD: GENAVAILDATE} must not be earlier than the {LD: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
        checkCanNotBeEarlier(psItem, "GENAVAILDATE", featItem,"GENAVAILDATE", checklvl);

        //11.00         WITHDRAWANNDATE_T   =>  PRODSTRUCT  WITHDRAWDATE        W   W   E
        //{LD: WITHDRAWDATE} must not be later than {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
        checkCanNotBeLater(psItem, "WITHDRAWDATE", featItem,"WITHDRAWANNDATE_T", checklvl);

        //12.00         WITHDRAWDATEEFF_T   =>  PRODSTRUCT  WTHDRWEFFCTVDATE        W   W   E
        //{LD: WTHDRWEFFCTVDATE} must not be later than {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
        checkCanNotBeLater(psItem, "WTHDRWEFFCTVDATE", featItem,"WITHDRAWDATEEFF_T", checklvl);
        
        String ordercode = PokUtils.getAttributeFlagValue(psItem, "ORDERCODE");
		addDebug("checkRPQFeatures "+psItem.getKey()+" ordercode "+ordercode);
		if(ORDERCODE_INITIAL.equals(ordercode)){			
			addDebug("checkRPQFeatures testing PRODSTRUCT.WITHDRAWDATE cannot be later than MODEL.WITHDRAWDATE");						
//			20120906 Add	12.10	MODEL		PRODSTRUCT-d									
//			20120906 Add	12.12	IF		"Initial"	=	PRODSTRUCT	ORDERCODE					Initial means that it can only be ordered when the MODEL is available	
//			20120906 Add	12.14			WITHDRAWDATE	=>	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: WITHDRAWDATE} {WITHDRAWDATE} must not be later than {LD: MODEL} {NDN: MODEL} {LD: WITHDRAWDATE} {N: WITHDRAWDATE}
//			20120906 Add	12.16	END	12.12
			EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0); // has to exist
			checkCanNotBeLater(psItem, "WITHDRAWDATE", mdlItem,"WITHDRAWDATE", checklvl);
		}

        //12.20 AVAIL       PRODSTRUCT-u:OOFAVAIL-d
        //12.30         CountOf =   0           E   E   E       RPQs must not have an {LD: AVAIL} {NDN: AVAIL}
        int cnt = getCount("OOFAVAIL");
        if(cnt > 0) {
            EntityGroup egrp = m_elist.getEntityGroup("AVAIL");
            for (int i = 0; i<egrp.getEntityItemCount(); i++){
                EntityItem avail = egrp.getEntityItem(i);
                //RPQ_AVAIL_ERR = RPQs must not have an {0}
                args[0]=this.getLD_NDN(avail);
                createMessage(CHECKLEVEL_E,"RPQ_AVAIL_ERR",args);
            }
        }
    }

    /**********************
     * Check OOFAVAIL-avail dates when feature is not an RPQ
     *
     * @param rootEntity
     * @param statusFlag
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *
    15.00   WHEN        FCTYPE  =   Primary FC (100) | "Secondary FC" (110)         GA Logic
    16.00   AVAIL   A   PRODSTRUCT-u:OOFAVAIL-d                             Feature AVAIL
Change 20151201	18.00   IF		PRODSTRUCT-u:OOFAVAIL-d	=	"Planned Availability" or "First Order"
							CountOf =>  1           RE*1    RE*1    RE*1        must have at lease one "Planned Availability"
Change 20160126	17.00   WHEN        AVAILTYPE   =   "Planned Availability"  or "MES Planned Availability"
    19.00           EFFECTIVEDATE   =>  FEATURE FIRSTANNDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    20.00           EFFECTIVEDATE   =>  FEATURE GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {NDN:FEATURE}{LD: GENAVAILDATE} {GENAVAILDATE}
    21.00           EFFECTIVEDATE   =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    22.00           EFFECTIVEDATE   =>  PRODSTRUCT  GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
    23.00           EFFECTIVEDATE   =>  MODEL   ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL}{LD: ANNDATE} {ANNDATE}
    24.00           EFFECTIVEDATE   =>  MODEL   GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
    25.00           COUNTRYLIST in  FEATURE COUNTRYLIST     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
Add 25.80   WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
Add 25.82   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
Add 25.84           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
Add 25.86   END 25.80
Add 25.88   WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    26.00   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
    27.00   IF      ANNTYPE =   "New" (19)
    28.00   THEN        ANNDATE =>  FEATURE FIRSTANNDATE        W   RW  RE      {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    29.00           ANNDATE =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    30.00           ANNDATE =>  MODEL   ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: ANNDATE} {ANNDATE}
    30.20   ELSE    27.00   ANNTYPE <>  "New" (19)
    30.40           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    31.00   END 27.00
Add 31.40   END 25.88
    32.00   END 17.00
    33.00   AVAIL   D   PRODSTRUCT-u:OOFAVAIL-d                             Feature AVAIL
    34.00   WHEN        AVAILTYPE   =   "First Order"
    35.00           EFFECTIVEDATE   =>  FEATURE FIRSTANNDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {ANNDATE}
    36.00           EFFECTIVEDATE   =>  FEATURE GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
    37.00           EFFECTIVEDATE   =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
Delete 20110318    38.00           EFFECTIVEDATE   =>  PRODSTRUCT  GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
    39.00           EFFECTIVEDATE   =>  MODEL   ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL}{LD: ANNDATE} {ANNDATE}
    40.00           EFFECTIVEDATE   =>  MODEL   GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
    xx41.00           COUNTRYLIST "IN aggregate G"    A: AVAIL    COUNTRYLIST     W   W   E       {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	41.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
    200.00  ANNOUNCEMENT        D: + AVAILANNA                              Feature Announcement
    201.00  IF      ANNTYPE =   "New" (19)
    202.00  THEN        ANNDATE =>  FEATURE FIRSTANNDATE        W   RW  RE      {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    203.00          ANNDATE =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    204.00          ANNDATE =>  MODEL   ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: ANNDATE} {ANNDATE}
    205.00  ELSE        ANNTYPE <>  "New" (19)
    206.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    207.00  END 201.00
    42.00   END 34.00
    43.00   AVAIL   B   PRODSTRUCT-u:OOFAVAIL-d                             Feature AVAIL
    44.00   WHEN        AVAILTYPE   =   "Last Order"
    46.00           EFFECTIVEDATE   <=  FEATURE WITHDRAWDATEEFF_T       W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE}{NDN:FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
    48.00           EFFECTIVEDATE   <=  PRODSTRUCT  WTHDRWEFFCTVDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
49.00	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
50.00           EFFECTIVEDATE   <=  MODEL   WTHDRWEFFCTVDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL} {NDN:MODEL} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
50.20	END	49.00
    51.00           COUNTRYLIST in  FEATURE COUNTRYLIST     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
    52.00xx           COUNTRYLIST "IN aggregate G"    A: AVAIL    COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	52.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
    54.00   IF          Not Null    FEATURE WITHDRAWDATEEFF_T
    56.00   OR          Not Null    PRODSTRUCT  WTHDRWEFFCTVDATE
    58.00   OR          Not Null    MODEL   WTHDRWEFFCTVDATE
    xx59.00   THEN        COUNTRYLIST Contains    A: AVAIL    COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: A: AVAIL} includes a Country that needs to have a "Last Order" {LD: AVAIL}
Change 20111216	59.00	THEN		COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that needs to have a "Last Order" {LD: AVAIL}
Add 59.20   WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
Add 59.22   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
Add 59.24           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
Add 59.26   END 59.20
Add 59.28   WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    60.00   ANNOUNCEMENT        B: + AVAILANNA                              Feature Announcement
    61.00   IF      ANNTYPE =   End Of Life - Withdrawal from mktg (14)
62.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: FEATURE}{NDN:FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
63.00			ANNDATE	<=	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
63.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE						
64.00			ANNDATE	<=	MODEL	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: MODEL}{NDN:MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
64.10	END	63.80
64.20   ELSE        ANNTYPE <>  End Of Life - Withdrawal from mktg (14)
    64.40           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    65.00   END 61.00
Add 65.40   END 59.28
    66.00   END 44.00

    250.00  AVAIL   J   PRODSTRUCT-u:OOFAVAIL-d                             PRODSTRUCT AVAIL
    251.00  WHEN        AVAILTYPE   =   "End of Marketing" (200)
    252.00          EFFECTIVEDATE   <=  FEATURE WITHDRAWANNDATE_T       W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
252.20			EFFECTIVEDATE	<=	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}

252.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    253.00          EFFECTIVEDATE   <=  MODEL   WITHDRAWDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
253.20	END	252.80
    254.00xx          COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	254.00			COUNTRYLIST	"in aggregate G"	A:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
    255.00  WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
    256.00  ANNOUNCEMENT        J: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    257.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    258.00  END 255.00
    259.00  WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    260.00  ANNOUNCEMENT    K   J: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    261.00  IF      ANNTYPE =   "End Of Life - Withdrawal from mktg" (14)
    262.00  THEN        ANNDATE <=  FEATURE WITHDRAWANNDATE_T       W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
262.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    263.00          ANNDATE <=  MODEL   WITHDRAWDATE        W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
263.20	END	262.80
    264.00  ELSE        ANNTYPE <>  "End Of Life - Withdrawal from mktg" (14)
    265.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    266.00  END 261.00
    267.00  END 259.00
    268.00  END 251.00

    20  AVAIL   G   PRODSTRUCT-u:OOFAVAIL-d                             PRODSTRUCT AVAIL
    270.00  WHEN        AVAILTYPE   =   "End of Service" (151)
    xx271.00          EFFECTIVEDATE   =>  B:AVAIL EFFECTIVEDATE   Yes W   W   E*1     {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: B:AVAIL)
    xx272.00          COUNTRYLIST "IN aggregate G"    B:AVAIL COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
Change 20111216	271.00			EFFECTIVEDATE	=>	B:AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: B:AVAIL)
Change 20111216	272.00			COUNTRYLIST	"in aggregate G"	B:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
     273.00  WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
    274.00  ANNOUNCEMENT        G: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    275.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: G:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    276.00  END 273.00
  Delete 20111213     277.00  WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    278.00  ANNOUNCEMENT    H   G: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    279.00  IF      ANNTYPE =   "End Of Life - Discontinuance of service" (13)
    280.00  THEN        ANNDATE <=  FEATURE WITHDRAWDATEEFF_T       W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
280.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    281.00          ANNDATE <=  MODEL   WTHDRWEFFCTVDATE        W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
281.20	END	280.80
    282.00  ELSE        ANNTYPE <>  "End Of Life - Discontinuance of service" (13)
    283.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: G:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    284.00  END 279.00
    285.00  END 277.00 
 end  Delete 20111213  
    286.00  END 270.00

     */
    private void checkOOFAVAILAvails(EntityItem psItem, EntityItem featItem, EntityItem mdlItem, String statusFlag)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        int checklvl = getCheck_W_W_E(statusFlag);

        //  get all AVAILS
        EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
        
        //Change 2016-01-26 17.00	WHEN		AVAILTYPE	=	"Planned Availability"  or "MES Planned Availability"
        Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", PLANNEDAVAIL);
        Vector mesPlannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", MESPLANNEDAVAIL);
        
        //Change 2016-01-26 44.00	WHEN		AVAILTYPE	=	"Last Order" or "MES Last Order"
        Vector lastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", LASTORDERAVAIL);
        Vector mesLastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", MESLASTORDERAVAIL);
        
        Vector firstOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", FIRSTORDERAVAIL);
        Vector eomAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", EOMAVAIL);
        Vector eosAvailVct = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", EOSAVAIL);
        ArrayList featCtrylist = new ArrayList();
        // fill in list with the feature's countries
        getCountriesAsList(featItem, featCtrylist,checklvl);
        addDebug("checkOOFAVAILAvails lastOrderAvailVct: "+lastOrderAvailVct.size()+
        		" mesLastOrderAvailVct: "+mesLastOrderAvailVct.size()+
                " plannedAvailVct: "+plannedAvailVct.size()+
                " mesPlannedAvailVct: "+mesPlannedAvailVct.size()+
                " firstOrderAvailVct: "+firstOrderAvailVct.size()+
                " eomAvailVct: "+eomAvailVct.size()+
                " eosAvailVct: "+eosAvailVct.size()+
                " featCtrylist "+featCtrylist);

        // New 2015-12-01		IF		PRODSTRUCT-u:OOFAVAIL-d	=	"Planned Availability" or "First Order"							
        // New 2015-12-01	18.00			CountOf	=>	1			RE*1	RE*1	RE*1		must have at lease one "Planned Availability"
        addHeading(3,availGrp.getLongDescription()+" Planned Avail and First Order Avail count Checks:");
        checkPlannedAvailsOrFirstOrderAvailsExist(plannedAvailVct, firstOrderAvailVct, getCheckLevel(CHECKLEVEL_RE,mdlItem,"ANNDATE"));
        
        
        //Change 2016-01-26 17.00 WHEN        AVAILTYPE   =   "Planned Availability"
        //  to
        //32.00 END 17.00
        //checkOOFAVAILPLAAvails(psItem,  featItem, mdlItem, statusFlag, plannedAvailVct, featCtrylist);
        addHeading(3,availGrp.getLongDescription()+" Planned Avail Checks:");
        checkOOFMesPlaAndPlaAvails(psItem,  featItem, mdlItem, statusFlag, plannedAvailVct, PLANNEDAVAIL, featCtrylist);
        
        //Change 2016-01-26 17.00 WHEN        AVAILTYPE   =   "MES Planned Availability"
        //  to
        //32.00 END 17.00
        addHeading(3,availGrp.getLongDescription()+" MES Planned Avail Checks:");
        checkOOFMesPlaAndPlaAvails(psItem,  featItem, mdlItem, statusFlag, mesPlannedAvailVct, MESPLANNEDAVAIL, featCtrylist);

//        Hashtable plaAvailCtryTbl = getAvailByCountry(plannedAvailVct, checklvl);
//        addDebug("checkOOFAVAILAvails plaAvailCtry "+plaAvailCtryTbl.keySet());

        //34.00 WHEN        AVAILTYPE   =   "First Order"
        // to
        //42.00 END 34.00
        addHeading(3,availGrp.getLongDescription()+" First Order Avail Checks:");
        checkOOFAVAILFOAvails(psItem, featItem, mdlItem, firstOrderAvailVct, plannedAvailVct, mesPlannedAvailVct,
        		checklvl);

        //44.00 WHEN        AVAILTYPE   =   "Last Order"
        // to
        //66.00 END 44.00
        addHeading(3,availGrp.getLongDescription()+" Last Order Avail Checks:");
        checkOOFAVAILLOAvails(psItem, featItem, mdlItem, lastOrderAvailVct, plannedAvailVct, 
        		featCtrylist, LASTORDERAVAIL, statusFlag);
        
        addHeading(3,availGrp.getLongDescription()+" MES Last Order Avail Checks:");
        checkOOFAVAILLOAvails(psItem, featItem, mdlItem, mesLastOrderAvailVct, plannedAvailVct, 
        		featCtrylist, MESLASTORDERAVAIL, statusFlag);
        checkOOFAVAILLOAvails(psItem, featItem, mdlItem, mesLastOrderAvailVct, mesPlannedAvailVct, 
        		featCtrylist, MESLASTORDERAVAIL, statusFlag);
        
        //250.00    AVAIL   J   PRODSTRUCT-u:OOFAVAIL-d                             IPSCSTRUC AVAIL
        //251.00    WHEN        AVAILTYPE   =   "End of Marketing" (200)
        // to
        //268.00    END 251.00
        addHeading(3,availGrp.getLongDescription()+" End of Marketing Avail Checks:");
        checkOOFAVAILEOMAvails(psItem,featItem, mdlItem, eomAvailVct, plannedAvailVct, mesPlannedAvailVct, statusFlag);

//        Hashtable loAvailCtryTbl = getAvailByCountry(lastOrderAvailVct, checklvl);
//        addDebug("checkOOFAVAILAvails loAvailCtryTbl "+loAvailCtryTbl);
        //20    AVAIL   G   PRODSTRUCT-u:OOFAVAIL-d                             IPSCSTRUC AVAIL
        //270.00    WHEN        AVAILTYPE   =   "End of Service" (151)
        // to
        //286.00    END 270.00
        addHeading(3,availGrp.getLongDescription()+" End of Service Avail Checks:");
        checkOOFAVAILEOSAvails(featItem,mdlItem, eosAvailVct, lastOrderAvailVct, mesLastOrderAvailVct, statusFlag);

        //67.00 AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
        // to
        //82.00 END 78.00
        addHeading(3,availGrp.getLongDescription()+" Model Avail Checks:");
        checkModelAvails(psItem,mdlItem, plannedAvailVct, mesPlannedAvailVct, lastOrderAvailVct, mesLastOrderAvailVct, statusFlag);

        lastOrderAvailVct.clear();
        plannedAvailVct.clear();
        eomAvailVct.clear();
        eosAvailVct.clear();
        firstOrderAvailVct.clear();
        featCtrylist.clear();
//        plaAvailCtryTbl.clear();
//        loAvailCtryTbl.clear();
    }

    /*****************************
     * check dates for plannedavails
     * @param psItem
     * @param featItem
     * @param mdlItem
     * @param statusFlag
     * @param plannedAvailVct
     * @param featCtrylist
     * @param checklvl
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *
    17.00   WHEN        AVAILTYPE   =   "Planned Availability"
    18.00           CountOf =>  1           RE*1    RE*1    RE*1        must have at lease one "Planned Availability"
    19.00           EFFECTIVEDATE   =>  FEATURE FIRSTANNDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    20.00           EFFECTIVEDATE   =>  FEATURE GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {NDN:FEATURE}{LD: GENAVAILDATE} {GENAVAILDATE}
    21.00           EFFECTIVEDATE   =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    22.00           EFFECTIVEDATE   =>  PRODSTRUCT  GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
    23.00           EFFECTIVEDATE   =>  MODEL   ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL}{LD: ANNDATE} {ANNDATE}
    24.00           EFFECTIVEDATE   =>  MODEL   GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
    25.00           COUNTRYLIST in  FEATURE COUNTRYLIST     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
Add 25.80   WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
Add 25.82   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
Add 25.84           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
Add 25.86   END 25.80
Add 25.88   WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    26.00   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
    27.00   IF      ANNTYPE =   "New" (19)
    28.00   THEN        ANNDATE =>  FEATURE FIRSTANNDATE        W   RW  RE      {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    29.00           ANNDATE =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    30.00           ANNDATE =>  MODEL   ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: ANNDATE} {ANNDATE}
    30.20   ELSE    27.00   ANNTYPE <>  "New" (19)
    30.40           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    31.00   END 27.00
Add 31.40   END 25.88
    32.00   END 17.00
     */
    private void checkOOFMesPlaAndPlaAvails(EntityItem psItem,  EntityItem featItem, EntityItem mdlItem,
            String statusFlag, Vector mesPlannedOrPlannedAvailVct, String availType, ArrayList featCtrylist)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        int checklvl = getCheck_W_W_E(statusFlag);

        //17.00 WHEN        AVAILTYPE   =   "Planned Availability"
        //Delete 2015-12-01 18.00         Count of    =>  1       RE*1    RE*1    RE*1        must have at least one "Planned Availability"
//        checkPlannedAvailsExist(plannedAvailVct, getCheckLevel(CHECKLEVEL_RE,mdlItem,"ANNDATE"));
//        20121030 Add	25.20	WHEN		"Final" (FINAL)	=	PRODSTRUCT	DATAQUALITY																																																																																																																																																																																																																																																		
//        20121030 Add	25.22	IF		STATUS	=	"Ready for Review" (0040)																																																																																																																																																																																																																																																			
//        20121030 Add	25.24	OR		STATUS	=	"Final" (0020)			
//        20160126 Add 			IF		AVAILTYPE	= 	"Planned Availability"
//        20121030 Add	25.26			CountOf	=>	1					RE*1		must have at least one "Planned Availability" that is either "Ready for Review" or "Final" in order to be "Final"																																																																																																																																																																																																																																												
//        20121030 Add	25.28	END	25.20																																																																																																																																																																																																																																																						
        if(PLANNEDAVAIL.equals(availType)){
        	checkPlannedAvailsStatus(mesPlannedOrPlannedAvailVct, psItem,getCheckLevel(CHECKLEVEL_RE,mdlItem,"ANNDATE"));
        }

        for (int i=0; i<mesPlannedOrPlannedAvailVct.size(); i++){
            EntityItem avail = (EntityItem)mesPlannedOrPlannedAvailVct.elementAt(i);

            //19.00         EFFECTIVEDATE   =>  FEATURE FIRSTANNDATE        W   W   E {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
            checkCanNotBeEarlier(avail, "EFFECTIVEDATE", featItem, "FIRSTANNDATE", checklvl);

            //20.00         EFFECTIVEDATE   =>  FEATURE GENAVAILDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
            checkCanNotBeEarlier(avail, "EFFECTIVEDATE", featItem, "GENAVAILDATE", checklvl);

            //21.00         EFFECTIVEDATE   =>  PRODSTRUCT  ANNDATE     W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
            checkCanNotBeEarlier(avail, "EFFECTIVEDATE", psItem, "ANNDATE", checklvl);

            //22.00         EFFECTIVEDATE   =>  PRODSTRUCT  GENAVAILDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
            checkCanNotBeEarlier(avail, "EFFECTIVEDATE", psItem, "GENAVAILDATE", checklvl);

            //23.00         EFFECTIVEDATE   =>  MODEL   ANNDATE     W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {LD: ANNDATE} {ANNDATE}
            checkCanNotBeEarlier(avail, "EFFECTIVEDATE", mdlItem, "ANNDATE", checklvl);

            //24.00         EFFECTIVEDATE   =>  MODEL   GENAVAILDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
            checkCanNotBeEarlier(avail, "EFFECTIVEDATE", mdlItem, "GENAVAILDATE", checklvl);

            //25.00 COUNTRYLIST in  FEATURE COUNTRYLIST
            checkAvailCtryInEntity(null,avail, featItem,featCtrylist,checklvl);

            //26.00 ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
            //27.00 WHEN        ANNTYPE =   "New" (19)
            Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
            addDebug("checkOOFAVAILPLAAvails "+avail.getKey()+" annVct "+annVct.size());
            //Add   25.80   WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
            //Add   25.82   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
            //Add   25.84           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
            //Add   25.86   END 25.80
            if(checkAvailAnnType(avail, annVct, CHECKLEVEL_E)){
                //Add   25.88   WHEN        AVAILANNTYPE    =   "RFA" (RFA)
                for (int ai=0; ai<annVct.size(); ai++){
                    EntityItem annItem = (EntityItem)annVct.elementAt(ai);
                    String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
                    addDebug("checkOOFAVAILPLAAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
                    if(!ANNTYPE_NEW.equals(anntypeFlag)){
                        //30.2  ELSE        ANNTYPE <>  "New" (19)
                        //30.4          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}

                        //MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
                        args[0] = getLD_NDN(avail);
                        args[1] = getLD_NDN(annItem);
                        createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
                        continue;
                    }
                    //28.00         ANNDATE =>  FEATURE FIRSTANNDATE        W   RW  RE  {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
                    checkCanNotBeEarlier(annItem, "ANNDATE", featItem, "FIRSTANNDATE", checklvl);
                    //29.00         ANNDATE =>  PRODSTRUCT  ANNDATE     W   W   E {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
                    checkCanNotBeEarlier(annItem, "ANNDATE", psItem, "ANNDATE", checklvl);
                    //30.00         ANNDATE =>  MODEL   ANNDATE     W   W   E   {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
                    checkCanNotBeEarlier(annItem, "ANNDATE", mdlItem, "ANNDATE", checklvl);
                }
            }
            annVct.clear();
        }
    }
    /***************************
     * check dates for firstorder avails
     * @param psItem
     * @param featItem
     * @param mdlItem
     * @param plannedAvailVct
     * @param featCtrylist
     * @param checklvl
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *

    34.00   WHEN        AVAILTYPE   =   "First Order"
    35.00           EFFECTIVEDATE   =>  FEATURE FIRSTANNDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {ANNDATE}
    36.00           EFFECTIVEDATE   =>  FEATURE GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
    37.00           EFFECTIVEDATE   =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
Delete 20110318    38.00           EFFECTIVEDATE   =>  PRODSTRUCT  GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
    39.00           EFFECTIVEDATE   =>  MODEL   ANNDATE     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL}{LD: ANNDATE} {ANNDATE}
    40.00           EFFECTIVEDATE   =>  MODEL   GENAVAILDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
    xx41.00           COUNTRYLIST "IN aggregate G"    A: AVAIL    COUNTRYLIST     W   W   E       {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	41.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
    200.00  ANNOUNCEMENT        D: + AVAILANNA                              Feature Announcement
    201.00  IF      ANNTYPE =   "New" (19)
    202.00  THEN        ANNDATE =>  FEATURE FIRSTANNDATE        W   RW  RE      {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE}{NDN:FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    203.00          ANNDATE =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    204.00          ANNDATE =>  MODEL   ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: MODEL} {NDN:MODEL} {LD: ANNDATE} {ANNDATE}
    205.00  ELSE        ANNTYPE <>  "New" (19)
    206.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    207.00  END 201.00
    42.00   END 34.00
     */
    private void checkOOFAVAILFOAvails(EntityItem psItem, EntityItem featItem, EntityItem mdlItem,
            Vector firstOrderAvailVct, Vector plannedAvailVct, Vector mesPlannedAvailVct,
            int checklvl)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
    	if(firstOrderAvailVct.size()>0){
    		for (int i=0; i<firstOrderAvailVct.size(); i++){
    			EntityItem avail = (EntityItem)firstOrderAvailVct.elementAt(i);

    			//35.00         EFFECTIVEDATE   =>  FEATURE FIRSTANNDATE        W   W   E {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", featItem, "FIRSTANNDATE", checklvl);

    			//Delete 2015-12-0136.00         EFFECTIVEDATE   =>  FEATURE GENAVAILDATE        W   W   E {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: FEATURE} {LD: GENAVAILDATE} {GENAVAILDATE}
//    			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", featItem, "GENAVAILDATE", checklvl);

    			//37.00         EFFECTIVEDATE   =>  PRODSTRUCT  ANNDATE     W   W   E {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", psItem, "ANNDATE", checklvl);

    			//Delete 20110318 38.00         EFFECTIVEDATE   =>  PRODSTRUCT  GENAVAILDATE        W   W   E {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE}
    			// checkCanNotBeEarlier(avail, "EFFECTIVEDATE", psItem, "GENAVAILDATE", checklvl);

    			//39.00         EFFECTIVEDATE   =>  MODEL   ANNDATE     W   W   E {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {LD: ANNDATE} {ANNDATE}
    			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", mdlItem, "ANNDATE", checklvl);

    			//Delete 2015-12-01 40.00         EFFECTIVEDATE   =>  MODEL   GENAVAILDATE        W   W   E {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {LD: GENAVAILDATE} {GENAVAILDATE}
//    			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", mdlItem, "GENAVAILDATE", checklvl);

    			//old41.00 COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST
//    			checkPlannedAvailForCtryExists(avail, plannedAvailVct, checklvl);

    			//200.00    ANNOUNCEMENT        D: + AVAILANNA                              Feature Announcement
    			Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
    			addDebug("checkOOFAVAILFOAvails "+avail.getKey()+" annVct "+annVct.size());
    			for (int ai=0; ai<annVct.size(); ai++){
    				EntityItem annItem = (EntityItem)annVct.elementAt(ai);
    				String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
    				addDebug("checkOOFAVAILFOAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
    				if(!ANNTYPE_NEW.equals(anntypeFlag)){
    					//205.00    ELSE        ANNTYPE <>  "New" (19)
    					//206.00        CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}

    					//MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
    					args[0] = getLD_NDN(avail);
    					args[1] = getLD_NDN(annItem);
    					createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
    					continue;
    				}
    				//201.00    IF      ANNTYPE =   "New" (19)
    				//202.00            ANNDATE =>  FEATURE FIRSTANNDATE        W   RW  RE  {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: FEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
    				checkCanNotBeEarlier(annItem, "ANNDATE", featItem, "FIRSTANNDATE", checklvl);
    				//203.00            ANNDATE =>  PRODSTRUCT  ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    				checkCanNotBeEarlier(annItem, "ANNDATE", psItem, "ANNDATE", checklvl);
    				//204.00            ANNDATE =>  MODEL   ANNDATE     W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than the {LD: PRODSTRUCT} {LD: ANNDATE} {ANNDATE}
    				checkCanNotBeEarlier(annItem, "ANNDATE", mdlItem, "ANNDATE", checklvl);
    			} // end ann loop
    			annVct.clear();
    			//207.00    END 201.00
    		}// end firstorder loop
    		
			Hashtable plaAvailOSNTbl = new Hashtable();
			boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl,plannedAvailVct,true,CHECKLEVEL_RE);
			Hashtable mesPlaAvailOSNTbl = new Hashtable();
			boolean mesPlaOsnErrors = getAvailByOSN(mesPlaAvailOSNTbl,mesPlannedAvailVct,true,CHECKLEVEL_RE);
			Hashtable foAvailOSNTbl = new Hashtable();
			boolean foOsnErrors = getAvailByOSN(foAvailOSNTbl,firstOrderAvailVct,true,CHECKLEVEL_RE);
			addDebug("checkOOFAVAILFOAvails foOsnErrors "+
					foOsnErrors+" foAvailOSNTbl.keys "+foAvailOSNTbl.keySet()+" plaOsnErrors "+
					plaOsnErrors+" plaAvailOSNTbl.keys "+plaAvailOSNTbl.keySet()+" mesPlaOsnErrors "+
							mesPlaOsnErrors+" mesPlaAvailOSNTbl.keys "+mesPlaAvailOSNTbl.keySet());
			//Change 20111216	41.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E		
			//{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
			if(plannedAvailVct != null && plannedAvailVct.size() > 0) { // 2015-12-02
				if(!plaOsnErrors && !foOsnErrors){
					// only do this check if no errors were found building the OSN buckets
					checkAvailCtryByOSN(foAvailOSNTbl,plaAvailOSNTbl, "PS_MISSING_PLA_OSNCTRY_ERR", null, true, checklvl);
				}
			}
			if(mesPlannedAvailVct != null && mesPlannedAvailVct.size() > 0) { // 2015-12-02
				if(!mesPlaOsnErrors && !foOsnErrors){
					// only do this check if no errors were found building the OSN buckets
					checkAvailCtryByOSN(foAvailOSNTbl,mesPlaAvailOSNTbl, "MISSING_MES_PLA_OSNCTRY_ERR", null, true, checklvl);
				}
			}
			plaAvailOSNTbl.clear();
			mesPlaAvailOSNTbl.clear();
			foAvailOSNTbl.clear();
    	}
    }

    /***********************************
     * check dates for lastorder avails
     * @param psItem
     * @param featItem
     * @param mdlItem
     * @param lastOrderAvailVct
     * @param featCtry
     * @param plannedAvailCtry
     * @param statusFlag
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *

    43.00   AVAIL   B   PRODSTRUCT-u:OOFAVAIL-d                             Feature AVAIL
    44.00   WHEN        AVAILTYPE   =   "Last Order"
    46.00           EFFECTIVEDATE   <=  FEATURE WITHDRAWDATEEFF_T       W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE}{NDN:FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
    48.00           EFFECTIVEDATE   <=  PRODSTRUCT  WTHDRWEFFCTVDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
49.00	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
50.00           EFFECTIVEDATE   <=  MODEL   WTHDRWEFFCTVDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL} {NDN:MODEL} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
50.20	END	49.00
    51.00           COUNTRYLIST in  FEATURE COUNTRYLIST     W   W   E       {LD: AVAIL} {NDN: AVAIL} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
    xx52.00           COUNTRYLIST "IN aggregate G"    A: AVAIL    COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	52.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
    54.00   IF          Not Null    FEATURE WITHDRAWDATEEFF_T
    56.00   OR          Not Null    PRODSTRUCT  WTHDRWEFFCTVDATE
    58.00   OR          Not Null    MODEL   WTHDRWEFFCTVDATE
    xx59.00   THEN        COUNTRYLIST Contains    A: AVAIL    COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: A: AVAIL} includes a Country that needs to have a "Last Order" {LD: AVAIL}
Change 20111216	59.00	THEN		COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that needs to have a "Last Order" {LD: AVAIL}
Add 59.20   WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
Add 59.22   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
Add 59.24           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
Add 59.26   END 59.20
Add 59.28   WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    60.00   ANNOUNCEMENT        B: + AVAILANNA                              Feature Announcement
    61.00   IF      ANNTYPE =   End Of Life - Withdrawal from mktg (14)
62.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: FEATURE}{NDN:FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
63.00			ANNDATE	<=	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
63.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE						
64.00			ANNDATE	<=	MODEL	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: MODEL}{NDN:MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
64.10	END	63.80
    64.20   ELSE        ANNTYPE <>  End Of Life - Withdrawal from mktg (14)
    64.40           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    65.00   END 61.00
Add 65.40   END 59.28
    66.00   END 44.00
     */
    private void checkOOFAVAILLOAvails(EntityItem psItem, EntityItem featItem, EntityItem mdlItem,
            Vector mesLoOrLoAvailVct, Vector mesPlOrPlAvailVct, ArrayList featCtrylist, String availType,
            String statusFlag)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        int checklvl = getCheck_W_W_E(statusFlag);

        if(mesLoOrLoAvailVct.size()>0){
        	for (int i=0; i<mesLoOrLoAvailVct.size(); i++){
        		EntityItem avail = (EntityItem)mesLoOrLoAvailVct.elementAt(i);

        		//45.00         EFFECTIVEDATE   <=  FEATURE WITHDRAWANNDATE_T       W   W   E {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
        		//delete checkCanNotBeLater(avail, "EFFECTIVEDATE", featItem, "WITHDRAWANNDATE_T", checklvl);

        		//46.00         EFFECTIVEDATE   <=  FEATURE WITHDRAWDATEEFF_T       W   W   E {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
        		checkCanNotBeLater(avail, "EFFECTIVEDATE", featItem, "WITHDRAWDATEEFF_T", checklvl);

        		//47.00         EFFECTIVEDATE   <=  PRODSTRUCT  WITHDRAWDATE        W   W   E {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
        		//delete checkCanNotBeLater(avail, "EFFECTIVEDATE", psItem, "WITHDRAWDATE", checklvl);

        		//48.00         EFFECTIVEDATE   <=  PRODSTRUCT  WTHDRWEFFCTVDATE        W   W   E {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
        		checkCanNotBeLater(avail, "EFFECTIVEDATE", psItem, "WTHDRWEFFCTVDATE", checklvl);

        		//49.00         EFFECTIVEDATE   <=  MODEL   WITHDRAWDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
        		//delete checkCanNotBeLater(avail, "EFFECTIVEDATE", mdlItem, "WITHDRAWDATE", checklvl);

        		//49.00	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
        		if(isInitialOrderCode){
        			//50.00         EFFECTIVEDATE   <=  MODEL   WTHDRWEFFCTVDATE        W   W   E   {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL} {LD: WITHDRWEFFCTVDATE} {WITHDRWEFFCTVDATE}
        			checkCanNotBeLater(avail, "EFFECTIVEDATE", mdlItem, "WTHDRWEFFCTVDATE", checklvl);
        		}else{
        			addDebug("checkOOFAVAILLOAvails BYPASSING AVAIL and MODEL.WTHDRWEFFCTVDATE ordercode not initial");
        		}
        		//50.20	END	49.00

        		//51.00         COUNTRYLIST in  FEATURE COUNTRYLIST     W   W   E
        		checkAvailCtryInEntity(null,avail, featItem,featCtrylist,checklvl);

        		//old52.00         COUNTRYLIST "IN aggregate G"    A: AVAIL    COUNTRYLIST     W   W   E*1
        		//checkPlannedAvailForCtryExists(avail, plaAvailCtryTbl.keySet(), getCheckLevel(checklvl,mdlItem,"ANNDATE"));

        		//60.00 ANNOUNCEMENT        B: + AVAILANNA                              Feature Announcement
        		//61.00 WHEN        ANNTYPE =   End Of Life - Withdrawal from mktg (14)
        		//62.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: FEATURE}{NDN:FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
        		//63.00			ANNDATE	<=	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
        		//63.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE						
        		//64.00			ANNDATE	<=	MODEL	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: MODEL}{NDN:MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
        		//64.10	END	63.80
        		//64.20 ELSE        ANNTYPE <>  End Of Life - Withdrawal from mktg (14)
        		//64.40         CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
        		Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
        		addDebug("checkOOFAVAILLOAvails "+avail.getKey()+" annVct "+annVct.size());

        		//Add   59.20   WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
        		//Add   59.22   ANNOUNCEMENT        A: + AVAILANNA                              Feature Announcement
        		//Add   59.24           CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
        		//Add   59.26   END 59.20
        		if(checkAvailAnnType(avail, annVct, CHECKLEVEL_E)){
        			//Add   59.28   WHEN        AVAILANNTYPE    =   "RFA" (RFA)
        			for (int ai=0; ai<annVct.size(); ai++){
        				EntityItem annItem = (EntityItem)annVct.elementAt(ai);
        				String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
        				addDebug("checkOOFAVAILLOAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
        				if(!ANNTYPE_EOL.equals(anntypeFlag)){
        					//64.20 ELSE        ANNTYPE <>  End Of Life - Withdrawal from mktg (14)
        					//64.40         CountOf =   0           E   E   E       {LD: AVAIL} {NDN: A:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}

        					//MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
        					args[0] = getLD_NDN(avail);
        					args[1] = getLD_NDN(annItem);
        					createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
        					continue;
        				}
        				// removed RW and RE from this check
        				//62.00	THEN		ANNDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: FEATURE}{NDN:FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
        				checkCanNotBeLater(annItem, "ANNDATE", featItem, "WITHDRAWANNDATE_T", checklvl);
        				//63.00			ANNDATE	<=	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
        				checkCanNotBeLater(annItem, "ANNDATE", psItem, "WITHDRAWDATE", checklvl);

        				if(isInitialOrderCode){
        					//63.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE						
        					//64.00			ANNDATE	<=	MODEL	WITHDRAWDATE		W	W	E		{LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be later than the {LD: MODEL}{NDN:MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
        					checkCanNotBeLater(annItem, "ANNDATE", mdlItem, "WITHDRAWDATE", checklvl);
        					//64.10	END	63.80
        				}else{
        					addDebug("checkOOFAVAILLOAvails BYPASSING ANNDATE and MODEL.WITHDRAWDATE ordercode not initial");
        				}
        			}
        		}
        		annVct.clear();
        		//65.00 END 61
        	}
        	//20160126 MES Planned or Planned Avail check
        	Hashtable mesPlOrPlAvailOSNTbl = new Hashtable();
        	boolean mesPlOrPlOsnErrors = getAvailByOSN(mesPlOrPlAvailOSNTbl,mesPlOrPlAvailVct,true,CHECKLEVEL_RE);
        	
        	Hashtable mesLoOrLoAvailOSNTbl = new Hashtable();
        	boolean mesLoOrLoOsnErrors = getAvailByOSN(mesLoOrLoAvailOSNTbl,mesLoOrLoAvailVct,true,CHECKLEVEL_RE);
        	if(LASTORDERAVAIL.equals(availType)){
	        	addDebug("checkOOFAVAILLOAvails LoOsnErrors "+
	        			mesLoOrLoOsnErrors+" LoAvailOSNTbl.keys "+mesLoOrLoAvailOSNTbl.keySet()+" plaOsnErrors "+
	        			mesPlOrPlOsnErrors+" plaAvailOSNTbl.keys "+mesLoOrLoAvailOSNTbl.keySet());
        	}else{
        		addDebug("checkOOFAVAILLOAvails mesLoOsnErrors "+
	        			mesLoOrLoOsnErrors+" mesLoAvailOSNTbl.keys "+mesLoOrLoAvailOSNTbl.keySet()+" mesPlaOsnErrors "+
	        			mesPlOrPlOsnErrors+" plaAvailOSNTbl.keys "+mesPlOrPlAvailOSNTbl.keySet());
        	}
      		//Change 20111216	52.00			COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
        	//{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
        	if(!mesPlOrPlOsnErrors && !mesLoOrLoOsnErrors){
        		// only do this check if no errors were found building the OSN buckets
        		if(LASTORDERAVAIL.equals(availType)){
        			checkAvailCtryByOSN(mesLoOrLoAvailOSNTbl,mesPlOrPlAvailOSNTbl, "PS_MISSING_PLA_OSNCTRY_ERR", null, true, getCheckLevel(checklvl,mdlItem,"ANNDATE"));
        		}else{
        			checkAvailCtryByOSN(mesLoOrLoAvailOSNTbl,mesPlOrPlAvailOSNTbl, "MISSING_MES_PLA_OSNCTRY_ERR", null, true, getCheckLevel(checklvl,mdlItem,"ANNDATE"));
        		}
        	}
    		mesPlOrPlAvailOSNTbl.clear();
        	mesLoOrLoAvailOSNTbl.clear();

        }

        //delete 53.00  IF          Not Null    FEATURE WITHDRAWANNDATE_T
        //54.00 IF          Not Null    FEATURE WITHDRAWDATEEFF_T
        //delete 55.00  OR          Not Null    PRODSTRUCT  WITHDRAWDATE
        //56.00 OR          Not Null    PRODSTRUCT  WTHDRWEFFCTVDATE
        //delete 57.00  OR          Not Null    MODEL   WITHDRAWDATE
        //58.00 OR          Not Null    MODEL   WTHDRWEFFCTVDATE
        //old59.00 THEN        COUNTRYLIST Contains    A: AVAIL    COUNTRYLIST     W   W   E*1 {LD: AVAIL} {NDN: A: AVAIL} includes a Country that needs to have a "Last Order" {LD: AVAIL}
        // old    20111216  59.00	THEN		COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that needs to have a "Last Order" {LD: AVAIL}
        // change 20150514  59.00	THEN		COUNTRYLIST	"in aggregate G"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
        // The change(20150514) is for IN6352731 - Planned Avail has to be super set of Last Order Avail, the check same as key 52.00, so comment this check, not do duplicate check.

//        if (lastOrderAvailVct.size()>0 && hasWDdate(psItem, featItem, mdlItem)){
//        	int tmplvl = getCheckLevel(checklvl,mdlItem,"ANNDATE");
//
//        	Hashtable loAvailOSNTbl = new Hashtable();
//        	boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl,lastOrderAvailVct,true,CHECKLEVEL_RE);
//
//        	Hashtable plaAvailOSNTbl = new Hashtable();
//        	boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl,plannedAvailVct,true,CHECKLEVEL_RE);
//        	addDebug("checkOOFAVAILLOAvails  loOsnErrors "+
//        			loOsnErrors+" plaAvailOSNTbl.keys "+loAvailOSNTbl.keySet()+" plaOsnErrors "+
//        			plaOsnErrors+" plaAvailOSNTbl.keys "+plaAvailOSNTbl.keySet()); 
//        	Set plaAvailKeys = plaAvailOSNTbl.keySet();
//        	Iterator itr = plaAvailKeys.iterator();
//        	while(itr.hasNext()){
//        		String osn = (String)itr.next();
//        		// get avails from each table for this osn
//        		Vector plaAvailOsnVct = (Vector)plaAvailOSNTbl.get(osn);
//        		Vector loAvailOsnVct = (Vector)loAvailOSNTbl.get(osn);
//        		ArrayList lastOrderAvailCtry = null;
//        		if(loAvailOsnVct == null){
//        			addDebug("checkOOFAVAILLOAvails no lo avails to check for osn "+osn);
//        			lastOrderAvailCtry = new ArrayList();
//        		}else{
//        			lastOrderAvailCtry = getCountriesAsList(loAvailOsnVct, tmplvl);
//        			addDebug("checkOOFAVAILLOAvails  lastOrderAvailCtry "+lastOrderAvailCtry);
//        		}
//
//        		// must look thru each to find the plannedavail per osn that doesnt have a lastorder
//        		for (int i=0; i <plaAvailOsnVct.size(); i++){
//        			EntityItem plaAvail = (EntityItem)plaAvailOsnVct.elementAt(i);
//        			ArrayList plaCtrylist = new ArrayList();
//        			// fill in list with the plannedavail's countries
//        			getCountriesAsList(plaAvail, plaCtrylist,tmplvl);
//        			addDebug("checkOOFAVAILLOAvails (59) plannedavail osn "+osn+" "+plaAvail.getKey()+" plaCtrylist "+plaCtrylist);
//
//        			if(!lastOrderAvailCtry.containsAll(plaCtrylist)){
//        				//Change 20111216	59.00	THEN		COUNTRYLIST	Contains	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL} includes a Country that needs to have a "Last Order" {LD: AVAIL}
//        				//MISSING_LOA_ERR ={0} includes a Country that needs to have a &quot;Last Order&quot; {1} {2} Extra countries are: {3}
//        				args[0]=getLD_NDN(plaAvail);
//        				args[1] = plaAvail.getEntityGroup().getLongDescription();
//        				if(osn.equals(DOMAIN_NOT_IN_LIST)){
//							args[2]= "";
//						}else{
//							args[2]= "for "+getLD_Value(plaAvail, "ORDERSYSNAME");
//						}
//        				
//        	            plaCtrylist.removeAll(lastOrderAvailCtry); // remove all matches
//        	            args[3]=getUnmatchedDescriptions(plaAvail, "COUNTRYLIST",plaCtrylist);
//
//        				createMessage(tmplvl,"MISSING_LOA_ERR",args);
//        			}
//        			plaCtrylist.clear();
//        		} // end pla per osn loop
//        		lastOrderAvailCtry.clear();
//        	} // end pla osn iterator
//        	
//        	loAvailOSNTbl.clear();
//         	plaAvailOSNTbl.clear();
//        }
    }
    /*********
     *
     * @param psItem
     * @param featItem
     * @param mdlItem
     * @param eomAvailVct
     * @param plaAvailCtryTbl
     * @param statusFlag
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
    250.00  AVAIL   J   PRODSTRUCT-u:OOFAVAIL-d                             PRODSTRUCT AVAIL
    251.00  WHEN        AVAILTYPE   =   "End of Marketing" (200)
    252.00          EFFECTIVEDATE   <=  FEATURE WITHDRAWANNDATE_T       W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
252.20			EFFECTIVEDATE	<=	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
252.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    253.00          EFFECTIVEDATE   <=  MODEL   WITHDRAWDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
253.20	END	252.80
    xx254.00          COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
Change 20111216	254.00			COUNTRYLIST	"in aggregate G"	A:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
    255.00  WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
    256.00  ANNOUNCEMENT        J: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    257.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    258.00  END 255.00
    259.00  WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    260.00  ANNOUNCEMENT    K   J: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    261.00  IF      ANNTYPE =   "End Of Life - Withdrawal from mktg" (14)
    262.00  THEN        ANNDATE <=  FEATURE WITHDRAWANNDATE_T       W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
262.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    263.00          ANNDATE <=  MODEL   WITHDRAWDATE        W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
263.20	END	262.80
    264.00  ELSE        ANNTYPE <>  "End Of Life - Withdrawal from mktg" (14)
    265.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    266.00  END 261.00
    267.00  END 259.00
    268.00  END 251.00
     */
    private void checkOOFAVAILEOMAvails(EntityItem psItem,EntityItem featItem, EntityItem mdlItem,
            Vector eomAvailVct, Vector plannedAvailVct, Vector mesPlannedAvailVct, String statusFlag)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        int checklvl = getCheck_W_W_E(statusFlag);

        if(eomAvailVct.size()>0){
        	for (int i=0; i<eomAvailVct.size(); i++){
        		EntityItem avail = (EntityItem)eomAvailVct.elementAt(i);
        		//252.00        EFFECTIVEDATE   <=  FEATURE WITHDRAWANNDATE_T       W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
        		checkCanNotBeLater(avail, "EFFECTIVEDATE", featItem, "WITHDRAWANNDATE_T", checklvl);

        		//252.20		EFFECTIVEDATE	<=	PRODSTRUCT	WITHDRAWDATE		W	W	E		{LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: PRODSTRUCT} {LD: WITHDRAWDATE} {WITHDRAWDATE}
        		checkCanNotBeLater(avail, "EFFECTIVEDATE", psItem, "WITHDRAWDATE", checklvl);

        		if(isInitialOrderCode){
        			//252.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
        			//253.00        EFFECTIVEDATE   <=  MODEL   WITHDRAWDATE        W   W   E       {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
        			checkCanNotBeLater(avail, "EFFECTIVEDATE", mdlItem, "WITHDRAWDATE", checklvl);
        			//253.20	END	252.80
        		}else{
        			addDebug("checkOOFAVAILEOMAvails BYPASSING AVAIL and MODEL.WITHDRAWDATE ordercode not initial");
        		}
  
        		//old254.00        COUNTRYLIST "IN aggregate G"    A:AVAIL COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
        		//checkPlannedAvailForCtryExists(avail, plaAvailCtryTbl.keySet(), getCheckLevel(checklvl,mdlItem,"ANNDATE"));

        		String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
        		addDebug("checkOOFAVAILEOMAvails "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
        		Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");

        		if (availAnntypeFlag==null){
        			availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
        		}
        		if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
        			//259.00    WHEN        AVAILANNTYPE    =   "RFA" (RFA)
        			//260.00    ANNOUNCEMENT    K   J: + AVAILANNA-d                                IPSCSTRUC ANNOUNCEMENT
        			addDebug("checkOOFAVAILEOMAvails "+avail.getKey()+" annVct "+annVct.size());
        			for (int ai=0; ai<annVct.size(); ai++){
        				EntityItem annItem = (EntityItem)annVct.elementAt(ai);
        				String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
        				addDebug("checkOOFAVAILEOMAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
        				if(!ANNTYPE_EOL.equals(anntypeFlag)){
        					//264.00    ELSE        ANNTYPE <>  "End Of Life - Withdrawal from mktg" (14)
        					//265.00            CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
        					//MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
        					args[0] = getLD_NDN(avail);
        					args[1] = getLD_NDN(annItem);
        					createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
        					continue;
        				}
        				//261.00    IF      ANNTYPE =   "End Of Life - Withdrawal from mktg" (14)
        				//262.00    THEN        ANNDATE <=  FEATURE WITHDRAWANNDATE_T       W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
        				checkCanNotBeLater(annItem, "ANNDATE", featItem, "WITHDRAWANNDATE_T", checklvl);

        				if(isInitialOrderCode){
        					//262.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
        					//263.00            ANNDATE <=  MODEL   WITHDRAWDATE        W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
        					checkCanNotBeLater(annItem, "ANNDATE", mdlItem, "WITHDRAWDATE", checklvl);
        					//263.20	END	262.80
        				}else{
        					addDebug("checkOOFAVAILEOMAvails BYPASSING ANNDATE and MODEL.WITHDRAWDATE ordercode not initial");
        				}
        			}
        			//266.00    END 261
        		}else{
        			//255.00    WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
        			//256.00    ANNOUNCEMENT        J: + AVAILANNA-d                                IPSCSTRUC ANNOUNCEMENT
        			//257.00            CountOf =   0           E   E   E       {LD: AVAIL} {NDN: J:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
        			checkAvailAnnType(avail, annVct, CHECKLEVEL_E);//output msgs
        			//258.00    END 255
        		}
        		annVct.clear();
        		//267.00    END 259
        		//268.00    END 251
        	}

        	Hashtable plaAvailOSNTbl = new Hashtable();
        	boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl,plannedAvailVct,true,CHECKLEVEL_RE);
        	//20160126 MES Planned Avail check
        	Hashtable mesPlaAvailOSNTbl = new Hashtable();
        	boolean mesPlaOsnErrors = getAvailByOSN(mesPlaAvailOSNTbl,mesPlannedAvailVct,true,CHECKLEVEL_RE);
        	Hashtable eomAvailOSNTbl = new Hashtable();
        	boolean eomOsnErrors = getAvailByOSN(eomAvailOSNTbl,eomAvailVct,true,CHECKLEVEL_RE);
        	addDebug("checkOOFAVAILEOMAvails eomOsnErrors "+
        			eomOsnErrors+" eomAvailOSNTbl.keys "+eomAvailOSNTbl.keySet()+" plaOsnErrors "+
        			plaOsnErrors+" plaAvailOSNTbl.keys "+plaAvailOSNTbl.keySet()+" mesPlaOsnErrors "+
        			mesPlaOsnErrors+" mesPlaAvailOSNTbl.keys "+mesPlaAvailOSNTbl.keySet());
      		//Change 20111216	254.00			COUNTRYLIST	"in aggregate G"	A:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
      		//{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
      		if(!plaOsnErrors && !eomOsnErrors){
        		// only do this check if no erroplaAvailOSNTblrs were found building the OSN buckets
        		checkAvailCtryByOSN(eomAvailOSNTbl,plaAvailOSNTbl, "PS_MISSING_PLA_OSNCTRY_ERR", null, true, getCheckLevel(checklvl,mdlItem,"ANNDATE"));
        	}
      		//20160126 MES Planned Avail check
      		//254.00			COUNTRYLIST	"in aggregate G"	A:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
      		//{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "MES Planned Availability"
      		if(mesPlannedAvailVct != null && mesPlannedAvailVct.size() > 0 && !mesPlaOsnErrors && !eomOsnErrors){
        		checkAvailCtryByOSN(eomAvailOSNTbl,mesPlaAvailOSNTbl, "MISSING_MES_PLA_OSNCTRY_ERR", null, true, getCheckLevel(checklvl,mdlItem,"ANNDATE"));
        	}
      		plaAvailOSNTbl.clear();
        	mesPlaAvailOSNTbl.clear();
        	eomAvailOSNTbl.clear();

        }
    }

    /*************
     *
     * @param featItem
     * @param mdlItem
     * @param eomAvailVct
     * @param plaAvailCtryTbl
     * @param statusFlag
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *
    20  AVAIL   G   PRODSTRUCT-u:OOFAVAIL-d                             PRODSTRUCT AVAIL
    270.00  WHEN        AVAILTYPE   =   "End of Service" (151)
    xx271.00          EFFECTIVEDATE   =>  B:AVAIL EFFECTIVEDATE   Yes W   W   E*1     {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: B:AVAIL)
    xx272.00          COUNTRYLIST "IN aggregate G"    B:AVAIL COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
 Change 20111216	271.00			EFFECTIVEDATE	=>	B:AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: B:AVAIL)
Change 20111216	272.00			COUNTRYLIST	"in aggregate G"	B:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
    273.00  WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
    274.00  ANNOUNCEMENT        G: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    275.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: G:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    276.00  END 273.00
  Delete 20111213     277.00  WHEN        AVAILANNTYPE    =   "RFA" (RFA)
    278.00  ANNOUNCEMENT    H   G: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
    279.00  IF      ANNTYPE =   "End Of Life - Discontinuance of service" (13)
    280.00  THEN        ANNDATE <=  FEATURE WITHDRAWDATEEFF_T       W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
280.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
    281.00          ANNDATE <=  MODEL   WTHDRWEFFCTVDATE        W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
281.20	END	280.80
    282.00  ELSE        ANNTYPE <>  "End Of Life - Discontinuance of service" (13)
    283.00          CountOf =   0           E   E   E       {LD: AVAIL} {NDN: G:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
    284.00  END 279.00
    285.00  END 277.00 
 end  Delete 20111213  
    286.00  END 270.00
     */
    private void checkOOFAVAILEOSAvails(EntityItem featItem, EntityItem mdlItem,
            Vector eosAvailVct, Vector lastOrderAvailVct, Vector mesLastOrderAvailVct, String statusFlag)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        int checklvl = getCheck_W_W_E(statusFlag);

        if(eosAvailVct.size()>0){
        	int tmplvl = getCheckLevel(checklvl,mdlItem,"ANNDATE");
        	for (int i=0; i<eosAvailVct.size(); i++){
        		EntityItem eosavail = (EntityItem)eosAvailVct.elementAt(i);
        	
        		/*Vector loVct = new Vector(); // hold onto loavail for date checks incase same avail for mult ctrys
        		String missingCtry = checkCtryMismatch(eosavail, loAvailCtryTbl, loVct, tmplvl);

        		// do the date checks now
        		for (int m=0; m<loVct.size(); m++){
        			EntityItem loAvail = (EntityItem)loVct.elementAt(m);
        			//old271.00            EFFECTIVEDATE   =>  B:AVAIL EFFECTIVEDATE   Yes W   W   E*1     {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: B:AVAIL)
        			checkCanNotBeEarlier(eosavail, "EFFECTIVEDATE", loAvail, "EFFECTIVEDATE",tmplvl);
        		}
 
        		loVct.clear();
        		//old272.00            COUNTRYLIST "IN aggregate G"    B:AVAIL COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
        		if (missingCtry.length()>0){
        			addDebug("checkOOFAVAILEOSAvails eosavail:"+eosavail.getKey()+
        					" COUNTRYLIST had ctry ["+missingCtry+"] that were not in any loavail");

        			args[0]=getLD_NDN(eosavail);
        			args[1] = PokUtils.getAttributeDescription(eosavail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
        			//MISSING_LO_CTRY_ERR = {0} {1} includes a Country that does not have a &quot;Last Order Availability&quot; Extra countries are: {2}
        			args[2] =missingCtry;
        			createMessage(tmplvl,"MISSING_LO_CTRY_ERR",args);
        		}*/

        		String availAnntypeFlag = PokUtils.getAttributeFlagValue(eosavail, "AVAILANNTYPE");
        		addDebug("checkOOFAVAILEOSAvails "+eosavail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
        		Vector annVct = PokUtils.getAllLinkedEntities(eosavail, "AVAILANNA", "ANNOUNCEMENT");

        		if (availAnntypeFlag==null){
        			availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
        		}


        		if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
        			/*
                Delete 20111213  277-285                

                //277.00    WHEN        AVAILANNTYPE    =   "RFA" (RFA)
                //278.00    ANNOUNCEMENT    H   G: + AVAILANNA-d                                IPSCSTRUC ANNOUNCEMENT
                addDebug("checkOOFAVAILEOSAvails "+eosavail.getKey()+" annVct "+annVct.size());
                for (int ai=0; ai<annVct.size(); ai++){
                    EntityItem annItem = (EntityItem)annVct.elementAt(ai);
                    String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
                    addDebug("checkOOFAVAILEOSAvails "+annItem.getKey()+" anntypeFlag "+anntypeFlag);
                    if(!ANNTYPE_EOLDS.equals(anntypeFlag)){
                        //282.00    ELSE        ANNTYPE <>  "End Of Life - Discontinuance of service" (13)
                        //283.00            CountOf =   0           E   E   E       {LD: AVAIL} {NDN: G:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
                        //MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
                        args[0] = getLD_NDN(eosavail);
                        args[1] = getLD_NDN(annItem);
                        createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
                        continue;
                    }

                    //279.00    IF      ANNTYPE =   "End Of Life - Discontinuance of service" (13)
                    //280.00    THEN        ANNDATE <=  FEATURE WITHDRAWDATEEFF_T       W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
                    checkCanNotBeLater(annItem, "ANNDATE", featItem, "WITHDRAWDATEEFF_T", getCheck_W_RW_RE(statusFlag));

                    if(isInitialOrderCode){
                    	//280.80	WHEN		"Initial"	=	PRODSTRUCT	ORDERCODE
                    	//281.00            ANNDATE <=  MODEL   WTHDRWEFFCTVDATE        W   W   E       {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
                    	checkCanNotBeLater(annItem, "ANNDATE", mdlItem, "WTHDRWEFFCTVDATE", checklvl);
                    	//281.20	END	280.80
                    }else{
                        addDebug("checkOOFAVAILEOSAvails BYPASSING ANNDATE and MODEL.WTHDRWEFFCTVDATE ordercode not initial");
                    }
                }
                //284.00    END 279
        			 */
        		}else{
        			//273.00    WHEN        AVAILANNTYPE    <>  "RFA" (RFA)
        			//274.00    ANNOUNCEMENT        G: + AVAILANNA-d                                PRODSTRUCT ANNOUNCEMENT
        			//275.00            CountOf =   0           E   E   E       {LD: AVAIL} {NDN: G:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
        			checkAvailAnnType(eosavail, annVct, CHECKLEVEL_E);//output msgs
        			//276.00    END 273
        		}
        		annVct.clear();
        		//285.00    END 277


        	}
			Hashtable loAvailOSNTbl = new Hashtable();
			boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl,lastOrderAvailVct,true,CHECKLEVEL_RE);
			addDebug("checkOOFAVAILEOSAvails loOsnErrors "+
					loOsnErrors+" plaAvailOSNTbl.keys "+loAvailOSNTbl.keySet());
			
			//20160126 MES Last Order Avail check
			Hashtable mesLoAvailOSNTbl = new Hashtable();
			boolean mesLoOsnErrors = getAvailByOSN(mesLoAvailOSNTbl,mesLastOrderAvailVct,true,CHECKLEVEL_RE);
			addDebug("checkOOFAVAILEOSAvails mesLoOsnErrors "+
					mesLoOsnErrors+" mesLoAvailOSNTbl.keys "+mesLoAvailOSNTbl.keySet());

			Hashtable eosAvailOSNTbl = new Hashtable();
			boolean eosOsnErrors = getAvailByOSN(eosAvailOSNTbl,eosAvailVct,true,CHECKLEVEL_RE);
			addDebug("checkOOFAVAILEOSAvails  eosOsnErrors "+
					eosOsnErrors+" eosAvailOSNTbl.keys "+eosAvailOSNTbl.keySet());
			// only do this check if no errors were found building the OSN buckets
			if(lastOrderAvailVct != null && lastOrderAvailVct.size() > 0 && !loOsnErrors && !eosOsnErrors){
	       		//Change 20111216	271.00			EFFECTIVEDATE	=>	B:AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: B:AVAIL)
				checkAvailDatesByCtryByOSN(eosAvailOSNTbl,loAvailOSNTbl, null, DATE_GR_EQ,tmplvl,"",false);
				//Change 20111216	272.00			COUNTRYLIST	"in aggregate G"	B:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
				checkAvailCtryByOSN(eosAvailOSNTbl,loAvailOSNTbl, "PS_MISSING_LO_OSNCTRY_ERR", null,true, tmplvl);
			}
			
			//20160126 MES Last Order Avail check
			//271.00			EFFECTIVEDATE	=>	B:AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1		{LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: B:AVAIL)
			//272.00			COUNTRYLIST	"in	aggregate G"	B:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "MES Last Order"
			if(mesLastOrderAvailVct != null && mesLastOrderAvailVct.size() > 0 && !mesLoOsnErrors && !eosOsnErrors){
				checkAvailDatesByCtryByOSN(eosAvailOSNTbl,mesLoAvailOSNTbl, null, DATE_GR_EQ,tmplvl,"",false);
				checkAvailCtryByOSN(eosAvailOSNTbl,mesLoAvailOSNTbl, "MISSING_MES_LO_OSNCTRY_ERR", null,true, tmplvl);
			}
			loAvailOSNTbl.clear();
			mesLoAvailOSNTbl.clear();
			eosAvailOSNTbl.clear();	

        }
    }
    /**
     * @param psItem
     * @param featItem
     * @param mdlItem
     * @return

delete 53   IF          Not Null    FEATURE WITHDRAWANNDATE_T
54.00   IF          Not Null    FEATURE WITHDRAWDATEEFF_T
delete 55   OR          Not Null    PRODSTRUCT  WITHDRAWDATE
56.00   OR          Not Null    PRODSTRUCT  WTHDRWEFFCTVDATE
delete 57   OR          Not Null    MODEL   WITHDRAWDATE
58.00   OR          Not Null    MODEL   WTHDRWEFFCTVDATE
     */
//    private boolean hasWDdate(EntityItem psItem, EntityItem featItem, EntityItem mdlItem)
//    {
//        String featdates[] = {"WITHDRAWDATEEFF_T"};
//        String psmdldates[] = {"WTHDRWEFFCTVDATE"};
//
//        boolean haswddate = checkForValues(featdates, featItem);
//        if (!haswddate){
//            haswddate = checkForValues(psmdldates, psItem);
//        }
//        if (!haswddate){
//            haswddate = checkForValues(psmdldates, mdlItem);
//        }
//
//        return haswddate;
//    }

//    private boolean checkForValues(String attrs[], EntityItem item){
//        boolean hasValue = false;
//        for (int i=0; i<attrs.length; i++){
//            String value = PokUtils.getAttributeValue(item, attrs[i], "", null, false);
//            addDebug("checkForValues "+item.getKey()+" "+attrs[i]+":"+value);
//            if (value!=null){
//                hasValue = true;
//                break;
//            }
//        }
//        return hasValue;
//    }

    /***********
     *
    67.00   AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
    68.00   WHEN        AVAILTYPE   =   "Planned Availability"
    xx69.00           EFFECTIVEDATE   <=  A: AVAIL    EFFECTIVEDATE   Yes W   RW  RE*1    Feature can not be available before the MODEL is announced by country   {LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
    xx70.00           COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
 Change 20111216	69.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
Change 20111216	70.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
   71.00   END 68.00
    72.00   AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
    73.00   WHEN        AVAILTYPE   =   "First Order"
    xx74.00           EFFECTIVEDATE   <=  A: AVAIL    EFFECTIVEDATE   Yes W   RW  RE*1    Feature can not be available before the MODEL is announced by country   {LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
    xx75.00           COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     W   W   E*1     {LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
Change 20111216	74.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
Change 20111216	75.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
    76.00   END 73.00
    76.20   PRODSTRUCT      Root
    76.40   WHEN        "Initial"   =   PRODSTRUCT  ORDERCODE
    77.00   AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
    78.00   WHEN        AVAILTYPE   =   "Last Order"
    xx79.00           EFFECTIVEDATE   =>  B: AVAIL    EFFECTIVEDATE   Yes W   W   E*1 Withdraw the Feature on or before the MODEL is Withdrawn by country {LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
    xx80.00   IF      COUNTRYLIST Match   A: AVAIL    COUNTRYLIST Yes
    xx81.00   THEN        TheMatch    IN  B: AVAIL    COUNTRYLIST Yes E*1 E*1 E*1     must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
 Change 20111216	79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	W	E*1	Withdraw the Feature on or before the MODEL is Withdrawn by country	{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
Change 20111216	80.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST	Ctry OSN:XCC_LIST					
Change 20111216	81.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST	Ctry OSN:XCC_LIST	E*1	E*1	E*1		must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
   82.00   END 78.00
        END 76.40

     * @throws SQLException
     * @throws MiddlewareException
     */
    private void checkModelAvails(EntityItem rootEntity, EntityItem mdlItem, Vector plannedAvailVct, Vector mesPlannedAvailVct,
            Vector lastOrderAvailVct, Vector mesLastOrderAvailVct, String statusFlag) throws MiddlewareException, SQLException
    {

        /*67.00 AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
    68.00   WHEN        AVAILTYPE   =   "Planned Availability"
    69.00           EFFECTIVEDATE   <=  A: AVAIL    EFFECTIVEDATE   Yes W   RW  RE*1    Feature can not be available before the MODEL is announced by country
    {LD: AVAIL} {NDN: A: AVAIL} must not be earlier than the {LD: MODEL} {LD: AVAIL} {NDN: AVAIL}
    70.00           COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     W   W   E*1
    {LD: AVAIL} {NDN: A: AVAIL} must not include a country that is not in the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
Change 20111216	69.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
Change 20111216	70.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
   
    
    71.00   END 68
         */
    	  
        //oldaddHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Model Planned Avail Checks:");
        //old 69-70checkPsModelAvail(mdlList, mdlItem, statusFlag,plannedAvailVct,"OOFAVAIL");
        		
        //72.00 AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
        //73.00 WHEN        AVAILTYPE   =   "First Order"
        //74.00         EFFECTIVEDATE   <=  A: AVAIL    EFFECTIVEDATE   Yes W   RW  RE*1    Feature can not be available before the MODEL is announced by country
        //{LD: AVAIL} {NDN: A: AVAIL} must not be earlier than the {LD: MODEL} {LD: AVAIL} {NDN: AVAIL}
        //75.00         COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     W   W   E*1
        //{LD: AVAIL} {NDN: A: AVAIL} must not include a country that is not in the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
        //76.00 END 73

       //old addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Model First Order Avail Checks:");
        //old 74-75checkPsModelFOAvail(mdlList, mdlItem, statusFlag, plannedAvailVct);

		//if (plannedAvailVct.size()>0){
		checkPsModelPlaFOAvail(mdlItem, statusFlag,plannedAvailVct,mesPlannedAvailVct);
		//}
		
        //76.20 PRODSTRUCT      Root
        //76.40 WHEN        "Initial"   =   PRODSTRUCT  ORDERCODE
        String ordercode = PokUtils.getAttributeFlagValue(rootEntity, "ORDERCODE");
        addDebug("(76.4) ORDERCODE "+ordercode);
        if (ORDERCODE_INITIAL.equals(ordercode)){
            //77.00 AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
            //78.00 WHEN        AVAILTYPE   =   "Last Order"
            //79.00         EFFECTIVEDATE   =>  B: AVAIL    EFFECTIVEDATE   Yes W   W   E*1 Withdraw the Feature on or before the MODEL is Withdrawn by country
            //{LD: AVAIL} {NDN: A: AVAIL} must not be later than the {LD: MODEL} {LD: AVAIL} {NDN: AVAIL}
            //80.00 IF      COUNTRYLIST Match   A: AVAIL    COUNTRYLIST Yes
            //81.00 THEN        TheMatch    IN  B: AVAIL    EFFECTIVEDATE   Yes E*1 E*1 E*1 must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
     
 //       	Change 20111216	79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	W	E*1	Withdraw the Feature on or before the MODEL is Withdrawn by country	{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
 //       	Change 20111216	80.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST	Ctry OSN:XCC_LIST					
 //       	Change 20111216	81.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST	Ctry OSN:XCC_LIST	E*1	E*1	E*1		must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}

            //addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Model Last Order Avail Checks:");
            checkPsModelLastOrderAvail(mdlItem, statusFlag,lastOrderAvailVct,mesLastOrderAvailVct, plannedAvailVct, mesPlannedAvailVct);
            //old checkPsModelLastOrderAvail(mdlList,mdlItem, statusFlag,lastOrderAvailVct,plannedAvailVct);
            //82.00 END 78
        }
        //END   76.40
    }

	/**
	 *
77.00	AVAIL		PRODSTRUCT-d: MODELAVAIL-d								MODEL's AVAIL	
78.00	WHEN		AVAILTYPE	=	"Last Order"							
79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1	Withdraw the Feature on or before the MODEL is Withdrawn by country	{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
80.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST	"Cty OSN:XCC_LIST"					
81.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST	"Cty OSN:XCC_LIST"	E*1	E*1	E*1		must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
82.00	END	78.00										

	 * @param mdlItem
	 * @param statusFlag
	 * @param lastOrderAvailVct
	 * @param plannedAvailVct
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void checkPsModelLastOrderAvail(EntityItem mdlItem, String statusFlag, Vector lastOrderAvailVct, Vector mesLastOrderAvailVct,
			Vector plannedAvailVct, Vector mesPlannedAvailVct) throws MiddlewareException, SQLException
	{
		addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Model Last Order Avail Checks:");
		// get avails from mdl extract
		EntityGroup mdlAvailGrp = mdlList.getEntityGroup("AVAIL");
		Vector mdlLoAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", LASTORDERAVAIL);

		if (lastOrderAvailVct.size()>0 && mdlLoAvailVct.size()>0){

			int	datechecklvl = getCheckLevel(getCheck_W_W_E(statusFlag),mdlItem,"ANNDATE");  
			int	ctrychklvl = getCheckLevel(CHECKLEVEL_E,mdlItem,"ANNDATE");
			
			Hashtable loAvailOSNTbl = new Hashtable();
			boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl,lastOrderAvailVct,true,CHECKLEVEL_RE);

			Hashtable mdlLoAvailOSNTbl = new Hashtable();
			boolean mdlLoOsnErrors = getAvailByOSN(mdlLoAvailOSNTbl,mdlLoAvailVct,true,CHECKLEVEL_RE);
			addDebug("checkPsModelLastOrderAvail  loOsnErrors "+
					loOsnErrors+" loAvailOSNTbl.keys "+loAvailOSNTbl.keySet()+" mdlLoOsnErrors "+
					mdlLoOsnErrors+" mdlLoAvailOSNTbl.keys "+mdlLoAvailOSNTbl.keySet());

			if(!mdlLoOsnErrors && !loOsnErrors){
				// only do this check if no errors were found building the OSN buckets
				//79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1	Withdraw the Feature on or before the MODEL is Withdrawn by country	
				//{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
				checkAvailDatesByCtryByOSN(loAvailOSNTbl,mdlLoAvailOSNTbl, null, DATE_LT_EQ,datechecklvl,getLD_NDN(mdlItem),false);
//				checkAvailDatesByCtryByOSN(mdlLoAvailOSNTbl,loAvailOSNTbl, null, DATE_GR_EQ,datechecklvl,getLD_NDN(mdlItem),false);
			}
			
			//78.00	WHEN		AVAILTYPE	=	"Last Order" or "MES Last Order"
			//80.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST	"Cty OSN:XCC_LIST"					
			//81.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST	"Cty OSN:XCC_LIST"	E*1	E*1	E*1		must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
			//82.00	END	78.00
			
			//PS-LastOrderAvail:Model-LastOrderAvail:PS-PlannedAvail
			checkModelAvailMatchPsAvailCtry(mdlItem, plannedAvailVct, true,loAvailOSNTbl, true, mdlLoAvailOSNTbl, true, ctrychklvl);
			//PS-LastOrderAvail:Model-LastOrderAvail:PS-MesPlannedAvail
			//checkModelAvailMatchPsAvailCtry(mdlItem, mesPlannedAvailVct, true,loAvailOSNTbl, true, mdlLoAvailOSNTbl, true, ctrychklvl);

			
			loAvailOSNTbl.clear();
			mdlLoAvailOSNTbl.clear();
		}else{
			addDebug("checkPsModelLastOrderAvail no PS-LastorderAvailVct to check");
		}
		if (mesLastOrderAvailVct.size()>0 && mdlLoAvailVct.size()>0){

			int	datechecklvl = getCheckLevel(getCheck_W_W_E(statusFlag),mdlItem,"ANNDATE");  
			int	ctrychklvl = getCheckLevel(CHECKLEVEL_E,mdlItem,"ANNDATE");
			
			Hashtable mesLoAvailOSNTbl = new Hashtable();
			boolean mesLoOsnErrors = getAvailByOSN(mesLoAvailOSNTbl,mesLastOrderAvailVct,true,CHECKLEVEL_RE);

			Hashtable mdlLoAvailOSNTbl = new Hashtable();
			boolean mdlLoOsnErrors = getAvailByOSN(mdlLoAvailOSNTbl,mdlLoAvailVct,true,CHECKLEVEL_RE);
			addDebug("checkPsModelLastOrderAvail  mesLoOsnErrors "+
					mesLoOsnErrors+" mesLoAvailOSNTbl.keys "+mesLoAvailOSNTbl.keySet()+" mdlLoOsnErrors "+
					mdlLoOsnErrors+" mdlLoAvailOSNTbl.keys "+mdlLoAvailOSNTbl.keySet());

			if(mesLoAvailOSNTbl.size() > 0 && !mdlLoOsnErrors && !mesLoOsnErrors){
				// only do this check if no errors were found building the OSN buckets
				//79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1	Withdraw the Feature on or before the MODEL is Withdrawn by country	
				//{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
				checkAvailDatesByCtryByOSN(mesLoAvailOSNTbl,mdlLoAvailOSNTbl, null, DATE_LT_EQ,datechecklvl,getLD_NDN(mdlItem),false);
			}
			
			//78.00	WHEN		AVAILTYPE	=	"Last Order" or "MES Last Order"
			//80.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST	"Cty OSN:XCC_LIST"					
			//81.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST	"Cty OSN:XCC_LIST"	E*1	E*1	E*1		must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
			//82.00	END	78.00
			
			//PS-MesLastOrderAvail:Model-LastOrderAvail:PS-PlannedAvail
			if(mesLoAvailOSNTbl.size() > 0)
				checkModelAvailMatchPsAvailCtry(mdlItem, plannedAvailVct, true,mesLoAvailOSNTbl, true, mdlLoAvailOSNTbl, true, ctrychklvl);
			//PS-MesLastOrderAvail:Model-LastOrderAvail:PS-MesPlannedAvail
//			checkModelAvailMatchPsAvailCtry(mdlItem, mesPlannedAvailVct, true,mesLoAvailOSNTbl, true, mdlLoAvailOSNTbl, true, ctrychklvl);
			
			mesLoAvailOSNTbl.clear();
			mdlLoAvailOSNTbl.clear();
		}else{
			addDebug("checkPsModelLastOrderAvail no PS-lastorderAvailVct to check");
		}
//		addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Model MES Last Order Avail Checks:");
//		Vector mdlMesLoAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", MESLASTORDERAVAIL);
//		if (lastOrderAvailVct.size()>0 && mdlMesLoAvailVct.size()>0){
//
//			int	datechecklvl = getCheckLevel(getCheck_W_W_E(statusFlag),mdlItem,"ANNDATE");  
//			int	ctrychklvl = getCheckLevel(CHECKLEVEL_E,mdlItem,"ANNDATE");
//			
//			Hashtable loAvailOSNTbl = new Hashtable();
//			boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl,lastOrderAvailVct,true,CHECKLEVEL_RE);
//
//			Hashtable mdlMesLoAvailOSNTbl = new Hashtable();
//			boolean mdlMesLoOsnErrors = getAvailByOSN(mdlMesLoAvailOSNTbl,mdlMesLoAvailVct,true,CHECKLEVEL_RE);
//			addDebug("checkPsModelLastOrderAvail  loOsnErrors "+
//					loOsnErrors+" loAvailOSNTbl.keys "+loAvailOSNTbl.keySet()+" mdlMesLoOsnErrors "+
//					mdlMesLoOsnErrors+" mdlMesLoAvailOSNTbl.keys "+mdlMesLoAvailOSNTbl.keySet());
//
//			if(!mdlMesLoOsnErrors && !loOsnErrors){
//				// only do this check if no errors were found building the OSN buckets
//				//79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1	Withdraw the Feature on or before the MODEL is Withdrawn by country	
//				//{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
//				checkAvailDatesByCtryByOSN(loAvailOSNTbl,mdlMesLoAvailOSNTbl, null, DATE_LT_EQ,datechecklvl,getLD_NDN(mdlItem),false);
////				checkAvailDatesByCtryByOSN(mdlLoAvailOSNTbl,loAvailOSNTbl, null, DATE_GR_EQ,datechecklvl,getLD_NDN(mdlItem),false);
//			}
//
//			//PS-LastOrderAvail:Model-MesLastOrderAvail:PS-PlannedAvail
//			checkModelAvailMatchPsAvailCtry(mdlItem, plannedAvailVct, true,loAvailOSNTbl, true, mdlMesLoAvailOSNTbl, true, ctrychklvl);
//			//PS-LastOrderAvail:Model-MesLastOrderAvail:PS-MesPlannedAvail
//			checkModelAvailMatchPsAvailCtry(mdlItem, mesPlannedAvailVct, true,loAvailOSNTbl, true, mdlMesLoAvailOSNTbl, true, ctrychklvl);
//			
//			loAvailOSNTbl.clear();
//			mdlMesLoAvailOSNTbl.clear();
//		}else{
//			addDebug("checkPsModelLastOrderAvail no PS-lastorderAvailVct to check");
//		}
//		if (mesLastOrderAvailVct.size()>0 && mdlMesLoAvailVct.size()>0){
//
//			int	datechecklvl = getCheckLevel(getCheck_W_W_E(statusFlag),mdlItem,"ANNDATE");  
//			int	ctrychklvl = getCheckLevel(CHECKLEVEL_E,mdlItem,"ANNDATE");
//			
//			Hashtable mesLoAvailOSNTbl = new Hashtable();
//			boolean mesLoOsnErrors = getAvailByOSN(mesLoAvailOSNTbl,mesLastOrderAvailVct,true,CHECKLEVEL_RE);
//
//			Hashtable mdlMesLoAvailOSNTbl = new Hashtable();
//			boolean mdlMesLoOsnErrors = getAvailByOSN(mdlMesLoAvailOSNTbl,mdlMesLoAvailVct,true,CHECKLEVEL_RE);
//			addDebug("checkPsModelLastOrderAvail  mesLoOsnErrors "+
//					mesLoOsnErrors+" mesLoAvailOSNTbl.keys "+mesLoAvailOSNTbl.keySet()+" mdlMesLoOsnErrors "+
//					mdlMesLoOsnErrors+" mdlMesLoAvailOSNTbl.keys "+mdlMesLoAvailOSNTbl.keySet());
//
//			if(!mdlMesLoOsnErrors && !mesLoOsnErrors){
//				// only do this check if no errors were found building the OSN buckets
//				//79.00			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1	Withdraw the Feature on or before the MODEL is Withdrawn by country	
//				//{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
//				checkAvailDatesByCtryByOSN(mesLoAvailOSNTbl,mdlMesLoAvailOSNTbl, null, DATE_LT_EQ,datechecklvl,getLD_NDN(mdlItem),false);
////				checkAvailDa//tesByCtryByOSN(mdlLoAvailOSNTbl,loAvailOSNTbl, null, DATE_GR_EQ,datechecklvl,getLD_NDN(mdlItem),false);
//			}
//
//			//PS-MesLastOrderAvail:Model-MesLastOrderAvail:PS-PlannedAvail
//			checkModelAvailMatchPsAvailCtry(mdlItem, plannedAvailVct, true,mesLoAvailOSNTbl, true, mdlMesLoAvailOSNTbl, true, ctrychklvl);
//			//PS-MesLastOrderAvail:Model-MesLastOrderAvail:PS-MesPlannedAvail
//			checkModelAvailMatchPsAvailCtry(mdlItem, mesPlannedAvailVct, true,mesLoAvailOSNTbl, true, mdlMesLoAvailOSNTbl, true, ctrychklvl);
//			
//			mesLoAvailOSNTbl.clear();
//			mdlMesLoAvailOSNTbl.clear();
//		}else{
//			addDebug("checkPsModelLastOrderAvail no PS-lastorderAvailVct to check");
//		}
		mdlLoAvailVct.clear();
	}
	private void checkModelAvailMatchPsAvailCtry(EntityItem mdlItem, Vector mesPlannedOrplannedAvailVct, boolean isPsPlannedAvail,
			Hashtable mesLoOrLoAvailOSNTbl, boolean isPsLastOrderAvail, Hashtable mdlmesLoOrLoAvailOSNTbl, boolean isMdlLoAvail, 
			int ctrychklvl) throws MiddlewareException, SQLException
	{		
		if(mesPlannedOrplannedAvailVct.size() < 1)
			return;
		
		Hashtable mesPlaOrPlaAvailOSNTbl = new Hashtable();
		boolean mesPlaOrPlaOsnErrors = getAvailByOSN(mesPlaOrPlaAvailOSNTbl,mesPlannedOrplannedAvailVct,true,CHECKLEVEL_RE);
		
		if(isPsPlannedAvail){
			addDebug("checkPsModelLastOrderAvail  plaOsnErrors "+
					mesPlaOrPlaOsnErrors+" plaAvailOSNTbl.keys "+mesPlaOrPlaAvailOSNTbl.keySet());
		}else{
			addDebug("checkPsModelLastOrderAvail  mesPlaOsnErrors "+
					mesPlaOrPlaOsnErrors+" mesPlaAvailOSNTbl.keys "+mesPlaOrPlaAvailOSNTbl.keySet());
		}
		
		//78.00	WHEN		AVAILTYPE	=	"Last Order" or "MES Last Order"
		//80.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST	"Cty OSN:XCC_LIST"					
		//81.00	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST	"Cty OSN:XCC_LIST"	E*1	E*1	E*1		must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
		//82.00	END	78.00										
	
		// For each PS->plannedAvail.ctry that matches the MODEL->LastOrderAvail.ctry
		// there must be a PS->LastOrderAvail.ctry - must look at each OSN bucket
		Set mesPlaOrPlaAvailKeys = mesPlaOrPlaAvailOSNTbl.keySet();
		Iterator itr = mesPlaOrPlaAvailKeys.iterator();
		while(itr.hasNext()){
			String osn = (String)itr.next();
			// get avails from each table for this osn
			Vector mesPlaOrPlaAvailOsnVct = (Vector)mesPlaOrPlaAvailOSNTbl.get(osn);
			Vector mdlmesLoOrLoAvailOsnVct = (Vector)mdlmesLoOrLoAvailOSNTbl.get(osn);
			if(mdlmesLoOrLoAvailOsnVct == null){
				addDebug("checkPsModelLastOrderAvail no mdllo avails to check for osn "+osn);
				continue;
			}
			Vector mesLoOrLoAvailOsnVct = (Vector)mesLoOrLoAvailOSNTbl.get(osn);
			if(mesLoOrLoAvailOsnVct == null){
				mesLoOrLoAvailOsnVct = new Vector();
			}
			ArrayList mesLoOrLoAvlCtry = getCountriesAsList(mesLoOrLoAvailOsnVct, ctrychklvl);
			if(isPsLastOrderAvail){
				addDebug("checkPsModelLastOrderAvail osn "+osn+" PS-lastOrderAvlCtry "+mesLoOrLoAvlCtry);
			}else {
				addDebug("checkPsModelLastOrderAvail osn "+osn+" PS-mesLastOrderAvlCtry "+mesLoOrLoAvlCtry);
			}

			Hashtable mdlMesLoOrLoAvailCtryTbl = getAvailByCountry(mdlmesLoOrLoAvailOsnVct,ctrychklvl);
			if(isMdlLoAvail){
				addDebug("checkPsModelLastOrderAvail mdlLoAvailCtryTbl: "+mdlMesLoOrLoAvailCtryTbl);
			}else{
				addDebug("checkPsModelLastOrderAvail mdlMesLoAvailCtryTbl: "+mdlMesLoOrLoAvailCtryTbl);
			}

			//look at each planned avail for this osn
			for (int i=0; i<mesPlaOrPlaAvailOsnVct.size(); i++){
				EntityItem avail = (EntityItem)mesPlaOrPlaAvailOsnVct.elementAt(i); 
				EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(avail, "COUNTRYLIST", ctrychklvl);
				if (ctrylist != null && ctrylist.toString().length()>0) {
					// Get the selected Flag codes.
					MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();

					Vector mdlloVct = new Vector(); // hold onto model lastorderavail in case mult ctrys match
					for (int im = 0; im < mfArray.length; im++) {						
						if (mfArray[im].isSelected() &&
								!mesLoOrLoAvlCtry.contains(mfArray[im].getFlagCode())){
							if(isPsPlannedAvail){
								if(isPsLastOrderAvail){
									addDebug("checkPsModelLastOrderAvail PS-plannedavail:"+avail.getKey()+
											" No PS lastorderavail for ctry "+mfArray[im].getFlagCode());
								}else{
									addDebug("checkPsModelLastOrderAvail PS-plannedavail:"+avail.getKey()+
											" No PS meslastorderavail for ctry "+mfArray[im].getFlagCode());
								}
								
							}else{
								if(isPsLastOrderAvail){
									addDebug("checkPsModelLastOrderAvail PS-mesplannedavail:"+avail.getKey()+
											" No PS lastorderavail for ctry "+mfArray[im].getFlagCode());
								}else{
									addDebug("checkPsModelLastOrderAvail PS-mesplannedavail:"+avail.getKey()+
											" No PS meslastorderavail for ctry "+mfArray[im].getFlagCode());
								}
							}
							// get the MODEL-lastorderavail for this ctry
							EntityItem mdlloAvail = (EntityItem)mdlMesLoOrLoAvailCtryTbl.get(mfArray[im].getFlagCode());
							if (mdlloAvail!=null){
								if(isPsPlannedAvail){
									if(isMdlLoAvail){
										addDebug("checkPsModelLastOrderAvail PS-plannedavail:"+avail.getKey()+
												" MODEL-lastorderavail for ctry "+mfArray[im].getFlagCode());
									}else{
										addDebug("checkPsModelLastOrderAvail PS-plannedavail:"+avail.getKey()+
												" MODEL-meslastorderavail for ctry "+mfArray[im].getFlagCode());
									}
								}else{
									if(isMdlLoAvail){
										addDebug("checkPsModelLastOrderAvail PS-mesplannedavail:"+avail.getKey()+
												" MODEL-lastorderavail for ctry "+mfArray[im].getFlagCode());
									}else{
										addDebug("checkPsModelLastOrderAvail PS-mesplannedavail:"+avail.getKey()+
												" MODEL-meslastorderavail for ctry "+mfArray[im].getFlagCode());
									}
								}
								if (!mdlloVct.contains(mdlloAvail)){
									mdlloVct.add(mdlloAvail);
								}
							}
						}
					}
					// output msg for all mdl lastorder that didnt have an ps lastorder
					for (int m=0; m<mdlloVct.size(); m++){
						EntityItem mdlloAvail = (EntityItem)mdlloVct.elementAt(m);
						//PS_LAST_ORDER_ERR = must have a "Last Order" {0} corresponding to {1} {2}
						//must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
						args[0]=mdlloAvail.getEntityGroup().getLongDescription();
						args[1]=getLD_NDN(mdlItem);//.getEntityGroup().getLongDescription();
						args[2]=getLD_NDN(mdlloAvail);
						if(isMdlLoAvail){
							createMessage(ctrychklvl,"PS_LAST_ORDER_ERR",args);
						}else{
							createMessage(ctrychklvl,"PS_MES_LAST_ORDER_ERR",args);
						}
					}
					mdlloVct.clear();           				
				} //ctry list has a value
			}// end pla loop per osn
			mesLoOrLoAvlCtry.clear();
			mdlMesLoOrLoAvailCtryTbl.clear();
		}// end pla osn iterator loop

		mesPlaOrPlaAvailOSNTbl.clear();
	}
    /**
     * 
67.00	AVAIL		PRODSTRUCT-d: MODELAVAIL-d								MODEL's AVAIL	
68.00	WHEN		AVAILTYPE	=	"Planned Availability"							
69.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
70.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
71.00	END	68.00										
72.00	AVAIL		PRODSTRUCT-d: MODELAVAIL-d								MODEL's AVAIL	
73.00	WHEN		AVAILTYPE	=	"First Order"							
74.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
75.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
76.00	END	73.00										

     * @param mdlItem
     * @param statusFlag
     * @param plannedAvailVct
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private void checkPsModelPlaFOAvail(EntityItem mdlItem, String statusFlag,Vector plannedAvailVct, Vector mesPlannedAvailVct) throws MiddlewareException, SQLException{
		// get avails from mdl extract
		EntityGroup mdlAvailGrp = mdlList.getEntityGroup("AVAIL");
		if (mdlAvailGrp ==null){
			throw new MiddlewareException("AVAIL is missing from extract for "+mdlList.getParentActionItem().getActionItemKey());
		}
	 
		int ctrychecklvl = getCheckLevel(getCheck_W_W_E(statusFlag), mdlItem, "ANNDATE");
		int datechecklvl = getCheckLevel(getCheck_W_RW_RE(statusFlag), mdlItem, "ANNDATE");

        addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Model Planned Avail Checks:");
		Vector mdlPlannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", PLANNEDAVAIL);
//		Vector mdlMesPlannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", MESPLANNEDAVAIL);
		Vector mdlFoAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", FIRSTORDERAVAIL);
		addDebug("checkPsModelPlaFOAvail mdlPlannedAvailVct " +mdlPlannedAvailVct.size()+ 
//				" mdlMesPlannedAvailVct " +mdlMesPlannedAvailVct.size()+
				" mdlFoAvailVct "+mdlFoAvailVct.size());
		Hashtable plaAvailOSNTbl = new Hashtable();
		boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl,plannedAvailVct,true,CHECKLEVEL_RE);
		Hashtable mesPlaAvailOSNTbl = new Hashtable();
		boolean mesPlaOsnErrors = getAvailByOSN(mesPlaAvailOSNTbl,mesPlannedAvailVct,true,CHECKLEVEL_RE);
		if(mdlPlannedAvailVct.size()>0) {
			Hashtable mdlplaAvailOSNTbl = new Hashtable();
			boolean mdlplaOsnErrors = getAvailByOSN(mdlplaAvailOSNTbl,mdlPlannedAvailVct,true,CHECKLEVEL_RE);
			
			addDebug("checkPsModelPlaFOAvail  plaOsnErrors "+plaOsnErrors+" plaAvailOSNTbl.keys "+plaAvailOSNTbl.keySet()+
					" mdlplaOsnErrors "+mdlplaOsnErrors+" mdlplaAvailOSNTbl.keys "+mdlplaAvailOSNTbl.keySet());
			
			// only do this check if no errors were found building the OSN buckets
			if(plannedAvailVct != null && plannedAvailVct.size() > 0 && !plaOsnErrors && !mdlplaOsnErrors){
				//Change 20111216	69.00			EFFECTIVEDATE	<=	A:AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	
				//{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
				checkAvailDatesByCtryByOSN(plaAvailOSNTbl,mdlplaAvailOSNTbl, null, DATE_GR_EQ,datechecklvl,getLD_NDN(mdlItem),false);
//				checkAvailDatesByCtryByOSN(mdlplaAvailOSNTbl,plaAvailOSNTbl, null, DATE_LT_EQ,datechecklvl,getLD_NDN(mdlItem),false);

				//Change 20111216	70.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
				//{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
//				checkAvailCtryByOSN(mdlplaAvailOSNTbl,plaAvailOSNTbl, "MODEL_AVAIL_OSNCTRY_ERR", mdlItem, true, ctrychecklvl);
				checkAvailCtryByOSN(plaAvailOSNTbl,mdlplaAvailOSNTbl, "MODEL_AVAIL_OSNCTRY_ERR", mdlItem, true, ctrychecklvl);
			}
			if(mesPlannedAvailVct != null && mesPlannedAvailVct.size() > 0 && !plaOsnErrors && !mdlplaOsnErrors){
				//Change 20111216	69.00			EFFECTIVEDATE	<=	A:AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	
				//{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
				checkAvailDatesByCtryByOSN(mesPlaAvailOSNTbl,mdlplaAvailOSNTbl, null, DATE_GR_EQ,datechecklvl,getLD_NDN(mdlItem),false);
//				checkAvailDatesByCtryByOSN(mdlplaAvailOSNTbl,plaAvailOSNTbl, null, DATE_LT_EQ,datechecklvl,getLD_NDN(mdlItem),false);

				//Change 20111216	70.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
				//{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
//				checkAvailCtryByOSN(mdlplaAvailOSNTbl,plaAvailOSNTbl, "MODEL_AVAIL_OSNCTRY_ERR", mdlItem, true, ctrychecklvl);
				checkAvailCtryByOSN(mesPlaAvailOSNTbl,mdlplaAvailOSNTbl, "MODEL_AVAIL_OSNCTRY_ERR", mdlItem, true, ctrychecklvl);
			}
			
			mdlPlannedAvailVct.clear();
			mdlplaAvailOSNTbl.clear();
		}
		//20160126 Add MODEL MES Planned Avail check
		addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Model MES Planned Avail Checks:");
//		if(mdlMesPlannedAvailVct.size()>0) {
//			Hashtable mdlMesPlaAvailOSNTbl = new Hashtable();
//			boolean mdlMesPlaOsnErrors = getAvailByOSN(mdlMesPlaAvailOSNTbl,mdlMesPlannedAvailVct,true,CHECKLEVEL_RE);
//			
//			addDebug("checkPsModelPlaFOAvail mesPlaOsnErrors "+	mesPlaOsnErrors+" mesPlaAvailOSNTbl.keys "+mesPlaAvailOSNTbl.keySet()+
//					" mdlMesPlaOsnErrors "+	mdlMesPlaOsnErrors+" mdlMesPlaAvailOSNTbl.keys "+mdlMesPlaAvailOSNTbl.keySet());
//			
//			if(mesPlannedAvailVct != null && mesPlannedAvailVct.size() > 0 && !mesPlaOsnErrors && !mdlMesPlaOsnErrors){
//				//69.00			EFFECTIVEDATE	<=	A:AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	
//				//{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
//				checkAvailDatesByCtryByOSN(mesPlaAvailOSNTbl,mdlMesPlaAvailOSNTbl, null, DATE_GR_EQ,datechecklvl,getLD_NDN(mdlItem),false);
//
//				//70.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
//				//{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
//				checkAvailCtryByOSN(mesPlaAvailOSNTbl,mdlMesPlaAvailOSNTbl, "MODEL_AVAIL_OSNCTRY_ERR", mdlItem, true, ctrychecklvl);
//			}
//			
//			mdlMesPlannedAvailVct.clear();
//			mdlMesPlaAvailOSNTbl.clear();	
//		}
       
		addHeading(3,m_elist.getEntityGroup("AVAIL").getLongDescription()+" Model First Order Avail Checks:");
		if(mdlFoAvailVct.size()>0) {
			Hashtable mdlfoAvailOSNTbl = new Hashtable();
			boolean mdlfoOsnErrors = getAvailByOSN(mdlfoAvailOSNTbl,mdlFoAvailVct,true,CHECKLEVEL_RE);
			addDebug("checkPsModelPlaFOAvail  mdlfoOsnErrors "+
					mdlfoOsnErrors+" mdlfoAvailOSNTbl.keys "+mdlfoAvailOSNTbl.keySet());
			// only do this check if no errors were found building the OSN buckets
			if(plannedAvailVct != null && plannedAvailVct.size() > 0 && !plaOsnErrors && !mdlfoOsnErrors){
				//Change 20111216	74.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	
				//{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
//				checkAvailDatesByCtryByOSN(mdlfoAvailOSNTbl,plaAvailOSNTbl, null, DATE_LT_EQ,datechecklvl,getLD_NDN(mdlItem),false);
				checkAvailDatesByCtryByOSN(plaAvailOSNTbl,mdlfoAvailOSNTbl, null, DATE_GR_EQ,datechecklvl,getLD_NDN(mdlItem),false);
				//Change 20111216	75.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
				//{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
				checkAvailCtryByOSN(plaAvailOSNTbl,mdlfoAvailOSNTbl, "MODEL_AVAIL_OSNCTRY_ERR", mdlItem, true, ctrychecklvl);
			}
			if(mesPlannedAvailVct != null && mesPlannedAvailVct.size() > 0 && !mesPlaOsnErrors && !mdlfoOsnErrors){
				//74.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	
				//{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
				checkAvailDatesByCtryByOSN(mesPlaAvailOSNTbl,mdlfoAvailOSNTbl, null, DATE_GR_EQ,datechecklvl,getLD_NDN(mdlItem),false);
				//75.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		
				//{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
				checkAvailCtryByOSN(mesPlaAvailOSNTbl,mdlfoAvailOSNTbl, "MODEL_AVAIL_OSNCTRY_ERR", mdlItem, true, ctrychecklvl);
			}

			mdlfoAvailOSNTbl.clear();	
			mdlFoAvailVct.clear();	
		}
        
		plaAvailOSNTbl.clear();
		mesPlaAvailOSNTbl.clear();
    }
    /*****************
     *
     * @param plannedAvailCtry
     * @param foavailVct
     * @param featItem
     * @param isFeatureRoot
     * @param statusFlag
     * @throws SQLException
     * @throws MiddlewareException
     *
72.00   AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
73.00   WHEN        AVAILTYPE   =   "First Order"
xx74.00           EFFECTIVEDATE   <=  A: AVAIL    EFFECTIVEDATE   Yes W   RW  RE*1    Feature can not be available before the MODEL is announced by country
{LD: AVAIL} {NDN: A: AVAIL} must not be earlier than the {LD: MODEL} {LD: AVAIL} {NDN: AVAIL}
xx75.00           COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     W   W   E*1
{LD: AVAIL} {NDN: A: AVAIL} must not include a country that is not in the {LD: MODEL} {LD: AVAIL} {NDN: AVAIL}
Change 20111216	74.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
Change 20111216	75.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.

76.00   END 73
     * /
    private void checkPsModelFOAvail(EntityList mdlList, EntityItem mdlItem, String statusFlag, Vector plannedAvailVct)
    throws MiddlewareException, SQLException
    {
        // get avails from mdl extract
        EntityGroup mdlAvailGrp = mdlList.getEntityGroup("AVAIL");
        if (mdlAvailGrp ==null){
            throw new MiddlewareException("AVAIL is missing from extract for "+mdlList.getParentActionItem().getActionItemKey());
        }
        if (plannedAvailVct.size()>0){
            //72.00 AVAIL       PRODSTRUCT-d: MODELAVAIL-d                              MODEL's AVAIL
            //73.00 WHEN        AVAILTYPE   =   "First Order"
            Vector mdlFOAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", FIRSTORDERAVAIL);

            Hashtable mdlFOAvailCtryTbl = getAvailByCountry(mdlFOAvailVct, CHECKLEVEL_E);

            addDebug("checkPsModelFOAvail (72-73) mdlFOAvailVct.size "+mdlFOAvailVct.size()+
                    " mdlFOAvailCtryTbl "+mdlFOAvailCtryTbl);

            if(mdlFOAvailVct.size()>0){
                int datechklvl = getCheck_W_RW_RE(statusFlag);
//                Change 20111216	74.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Ctry OSN:XCC_LIST	W	RW	RE*1	Feature can not be available before the MODEL is announced by country	{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
//                Change 20111216	75.00			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.

                //old74.00         EFFECTIVEDATE   <=  A: AVAIL    EFFECTIVEDATE   Yes W   RW  RE*1
                datechklvl = this.getCheckLevel(datechklvl, mdlItem, "ANNDATE");
                int ctrychklvl = getCheck_W_W_E(statusFlag);
                //old75.00         COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     W   W   E*1
                ctrychklvl = this.getCheckLevel(ctrychklvl, mdlItem, "ANNDATE");

                checkPsAndModelAvails(mdlItem, datechklvl, ctrychklvl,
                        mdlFOAvailCtryTbl, plannedAvailVct,"OOFAVAIL", false);
            }

            mdlFOAvailVct.clear();
            mdlFOAvailCtryTbl.clear();
        }else{
            addDebug("checkPsModelFOAvail no PS-plannedAvailVct to check");
        }
    }*/

    /**********************************
     * Must have MODELAVAIL in second VE because extracts wont go from PRODSTRUCT thru MODEL to AVAIL
     * @throws MiddlewareException
     * @throws SQLException
     * @throws MiddlewareRequestException
     */
    private EntityList getModelVE(EntityItem modelEntity)
    throws MiddlewareRequestException, SQLException, MiddlewareException
    {
        String VeName = "DQVEMODELAVAIL";

        EntityList mdlList = m_db.getEntityList(m_elist.getProfile(),
                new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
                new EntityItem[] { new EntityItem(null, m_elist.getProfile(), modelEntity.getEntityType(), modelEntity.getEntityID()) });
        addDebug("getModelVE: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));
        return mdlList;
    }

    /**********************************
     * Must have MODELAVAIL in second VE because extracts wont go from PRODSTRUCT thru MODEL to AVAIL
     * @throws MiddlewareException
     * @throws SQLException
     * @throws MiddlewareRequestException
     */
    private EntityList getModelsVE(EntityGroup mdlGrp)
    throws MiddlewareRequestException, SQLException, MiddlewareException
    {
        String VeName = "DQVEMODELAVAIL";

        EntityItem eia[] = new EntityItem[mdlGrp.getEntityItemCount()];
        for (int i=0; i<mdlGrp.getEntityItemCount(); i++){
        	EntityItem otherMdl = mdlGrp.getEntityItem(i);
        	eia[i] = new EntityItem(null, m_elist.getProfile(), otherMdl.getEntityType(), otherMdl.getEntityID());
        }
        EntityList mdlList = m_db.getEntityList(m_elist.getProfile(),
                new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
                eia);
        addDebug("getModelsVE: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));
        return mdlList;
    }
    /**********************************************************************************
     *
2.  TMF Error (Key 68)
A Model (MODEL) cannot have identical or duplicate Feature Codes (FEATURECODE).

Features within a Machine Type (MACHTYPE) with duplicate Feature Codes (FEATURECODE) must be identical.

SEARCH using action RDPRODSTRUCTV specifying MACHTYEATR and FEATURECODE:

a.  UniqueWithinMODEL
If a MODEL has more than one FEATURE with duplicate FEATURECODE, then report an error.
b.  IdenticalWithinMACHTYPE
If a MACHTYPE has more than one FEATURE with duplicate FEATURECODE, then the ENTITYIDs must be
identical or else report an error.

    89.00   TMF Error       Action: RDPRODSTRUCTV   Search Using    PRODSTRUCT-d: MODEL MACHTYPEATR                 TMF Error Checking
    90.00               AND PRODSTRUCT-u: FEATURE   FEATURECODE
    91.00           UniqueWithinMODEL       FEATURE FEATURECODE     E   E   E       {LD: FEATURECODE} {FEATURECODE} is a duplicate within {LD: MODEL} {MODEL.MODELATR}
    92.00           IdenticalWithinMACHTYPE     FEATURE FEATURECODE     E   E   E       {LD: FEATURECODE} {FEATURECODE} is a duplicate within {LD: MACHTYPE} however, it is not an identical {FEATURE}

     */
    private void checkAllFeatures(EntityItem origfcItem,EntityItem origmdlItem)
    throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    COM.ibm.eannounce.objects.SBRException
    {
        if(mtmList==null){
            searchForProdstructs(origfcItem, origmdlItem);
        }
        addDebug("checkAllFeatures search results "+PokUtils.outputList(mtmList));


        if (mtmList !=null){
            EntityGroup grp = mtmList.getEntityGroup("FEATURE");
            if (grp.getEntityItemCount()>1){
                //92.00         IdenticalWithinMACHTYPE     FEATURE FEATURECODE     E   E   E
                //{LD: FEATURECODE} {FEATURECODE} is a duplicate within {LD: MACHTYPE} however, it is not an identical {FEATURE}
                for (int i=0; i<grp.getEntityItemCount(); i++){
                    EntityItem featItem = grp.getEntityItem(i);
                    addDebug("checkAllFeatures (92) fnd duplicate fc on "+featItem.getKey());
                    //NOT_IDENTICAL_ERR = {0} is a duplicate within {1} however, it is not an identical {2}
                    args[0] = getLD_NDN(featItem)+" "+getLD_Value(featItem, "FEATURECODE");
                    args[1] = PokUtils.getAttributeDescription(origmdlItem.getEntityGroup(), "MACHTYPEATR", "MACHTYPEATR");
                    args[2]=grp.getLongDescription();
                    createMessage(CHECKLEVEL_E,"NOT_IDENTICAL_ERR",args);
                }
            }else{
                // check for dupe prodstruct relators between the same model and feature
                EntityItem featItem = grp.getEntityItem(0);
                addDebug("checkAllFeatures featItem "+featItem.getKey());

                Hashtable dupeTbl = new Hashtable();
                for (int i=0; i<featItem.getDownLinkCount(); i++){
                    EntityItem psitem = (EntityItem)featItem.getDownLink(i);
                    for (int d=0; d<psitem.getDownLinkCount(); d++){
                        EntityItem mdlitem = (EntityItem)psitem.getDownLink(d);
                        addDebug("checkAllFeatures adding psitem "+psitem.getKey()+" to vct for mdlitem "+mdlitem.getKey());
                        Vector psVct = (Vector)dupeTbl.get(mdlitem.getKey());
                        if (psVct==null){
                            psVct = new Vector(1);
                            dupeTbl.put(mdlitem.getKey(),psVct);
                        }
                        psVct.add(psitem);
                    }
                }
                for (Enumeration e = dupeTbl.keys(); e.hasMoreElements();) {
                    String mdlkey = (String)e.nextElement();
                    Vector psVct = (Vector)dupeTbl.get(mdlkey);
                    EntityItem mdlItem = mtmList.getEntityGroup("MODEL").getEntityItem(mdlkey);
                    if (psVct.size()>1){
                        for (int p=0; p<psVct.size(); p++){
                            EntityItem psitem = (EntityItem)psVct.elementAt(p);
                            addDebug("checkAllFeatures (91) mdlItem "+mdlItem.getKey()+" duplicate psitem "+psitem.getKey());
                            if(psitem.getEntityType().equals(getEntityType()) &&
                                    psitem.getEntityID()==getEntityID()){
                                addDebug("checkAllFeatures (91)skipping msg root is duplicate psitem "+psitem.getKey());
                                continue;
                            }
                            //91.00         UniqueWithinMODEL       FEATURE FEATURECODE     E   E   E
                            //{LD: FEATURECODE} {FEATURECODE} is a duplicate within {LD: MODEL} {MODEL.MODELATR}
                            //DUPLICATE_ERR = {0} is a duplicate within {1} {2}
                            String info = "";
                            if (psitem.getEntityID()!=this.getEntityID()){
                                info = this.getLD_NDN(psitem)+" ";
                            }
                            args[0] = info+getLD_Value(featItem, "FEATURECODE");
                            args[1] = mdlItem.getEntityGroup().getLongDescription();
                            args[2] = getLD_Value(mdlItem, "MODELATR");
                            createMessage(CHECKLEVEL_E,"DUPLICATE_ERR",args);
                        }
                        psVct.clear();
                    }
                }
                dupeTbl.clear();
            }
        }
    }

    /**
     * @param featItem
     * @param mdlItem
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareShutdownInProgressException
     *
    89.00   TMF Error       Action: RDPRODSTRUCTV   Search Using    PRODSTRUCT-d: MODEL MACHTYPEATR                 TMF Error Checking
    90.00               AND PRODSTRUCT-u: FEATURE   FEATURECODE
     */
    private void searchForProdstructs(EntityItem featItem, EntityItem mdlItem)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
    {
        Vector attrVct = new Vector(3);
        attrVct.addElement("MODEL:MACHTYPEATR");
        attrVct.addElement("FEATURE:FEATURECODE");

        Vector valVct = new Vector(2);
        valVct.addElement(PokUtils.getAttributeFlagValue(mdlItem, "MACHTYPEATR"));
        valVct.addElement(PokUtils.getAttributeValue(featItem, "FEATURECODE", "", "", false));

        addDebug("searchForProdstructs attrVct "+attrVct+" valVct "+valVct);

        EntityItem eia[]= null;
        try{
            StringBuffer debugSb = new StringBuffer();
            eia= ABRUtil.doSearch(getDatabase(), m_prof,
                    PS_SRCHACTION_NAME, "PRODSTRUCT", true, attrVct, valVct, debugSb);
            if (debugSb.length()>0){
                addDebug(debugSb.toString());
            }
        }catch(SBRException exc){
            // these exceptions are for missing flagcodes or failed business rules, dont pass back
            java.io.StringWriter exBuf = new java.io.StringWriter();
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            addDebug("searchForProdstructs SBRException: "+exBuf.getBuffer().toString());
        }
        if (eia!=null && eia.length > 0){
            mtmList = eia[0].getEntityGroup().getEntityList();
        }

        attrVct.clear();
        valVct.clear();
    }

    /**********************************
     * Q.   DeriveEXTRACTRPQ
     *
     * Note:  this is unchanged (copied) from the current 30b Production ABR derivation.
     *
     * The following information will be inserted (updated if it already exists) into a new entity (EXTRACTRPQ - attribute derivation is shown  below):
     * EXTDTS = Date/Time of insert
     * EXTPRODSTRUCTEID = PRODSTRUCT.Entityid
     * MACHTYPEATR = MODEL.MACHTYPEATR
     * MODELATR = MODEL.MODELATR
     * ORDERCODE = PRODSTRUCT.ORDERCODE
     * FEATURECODE = FEATURE.FEATURECODE
     * COUNTRYLIST = FEATURE.COUNTRYLIST - this was removed in BH FS ABR Data Quality 20110707.doc BUT
     * removal from the pdg template is not enough, COUNTRYLIST is a required attr, so the pdg.checkReqInfoMissing()
     * code picks the first one since no other value was specified!
     * FCTYPE = FEATURE.FCTYPE
     * RPQANNNUMBER = 'RPQ' & RIGHT('00000000000000' & CHAR(feature.entityid),15)
     * EXTMODELEID = MODEL.entityid
     * EXTREATUREEID = FEATURE.entityid
     * ZEROPRICE = FEATURE.ZEROPRICE
     * INVENTORYGROUP = FEATURE.INVENTORYGROUP
     * INSTALL = PRODSTRUCT.INSTALL
     * MKTGNAME = FEATURE. MKTGNAME
     * FIRSTANNDATE = FEATURE. FIRSTANNDATE
     */
    private void deriveEXTRACTRPQ(EntityItem _eiFEATURE)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        try{
            OPICMList infoList = new OPICMList();
            String strFileName = "PDGtemplates/PRODSTRUCTABRSTATUS1.txt"; // BH FS ABR Data Quality 20110707.doc chg in here
            PDGUtility m_utility = new PDGUtility();

            EntityItem _eiPROD = m_elist.getParentEntityGroup().getEntityItem(0);
            EntityGroup eg = m_elist.getEntityGroup("MODEL");
            EntityItem _eiMODEL = eg.getEntityItem(0);

            infoList.put("TIMESTAMP", m_elist.getProfile().getValOn());
            infoList.put("PRODSTRUCT", _eiPROD);
            infoList.put("MODEL", _eiMODEL);
            infoList.put("FEATURE", _eiFEATURE);
            infoList.put("PRODID", _eiPROD.getEntityID() + "");
            infoList.put("FEATUREID", _eiFEATURE.getEntityID() + "");
            infoList.put("MODELID", _eiMODEL.getEntityID() + "");
            String str1 = "00000000000000" + _eiFEATURE.getEntityID();
            int iL = str1.length();
            if (iL > 15) {
                str1 = str1.substring(iL-15);
            }
            infoList.put("ANN", "RPQ" + str1);
            m_prof = m_utility.setProfValOnEffOn(m_db,m_prof);
            TestPDGII pdgObject = new TestPDGII(m_db, m_prof, null, infoList, strFileName );
            StringBuffer sbMissing = pdgObject.getMissingEntities();
            addDebug("deriveEXTRACTRPQ "+_eiFEATURE.getKey()+" "+_eiPROD.getKey()+" "+
                    _eiMODEL.getKey()+" sbmissing: "+sbMissing);
            m_utility.putCreateAction("EXTRACTRPQ", "CREXTRACTRPQ1");
            m_utility.putSearchAction("EXTRACTRPQ", "SRDEXTRACTRPQ1");
            m_utility.generateData(m_db, m_prof, sbMissing, _eiPROD);
        }catch(COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException ex){
            ex.printStackTrace(System.out);
            throw new MiddlewareException(ex.getMessage());
        }catch(COM.ibm.eannounce.objects.SBRException ex){
            ex.printStackTrace(System.out);
            throw new MiddlewareException(ex.toString()); // must use to string or won't get correct msg
        }
    }

    /**
     *  Get ABR description
     *
     *@return    java.lang.String
     */
    public String getDescription() {
        return "PRODSTRUCTABRSTATUS ABR";
    }

    /**
     * getRevision
     *
     * @return
     * @author Owner
     */
    public String getRevision() {
        return "1.18";
    }

    /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
        return "PRODSTRUCTABRSTATUS.java,v 1.8 2010/01/04 12:48:22 wendy Exp";
    }

    /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
        return "PRODSTRUCTABRSTATUS.java";
    }
    /**
     * hold onto a pair of dates and warr entityid, one object per country
     *
     */
    private static class WarrTPIC {
        String ctry = null;
        String effDate=null; // from EFFECTIVEDATE on relator
        String endDate=null;   // from ENDDATE on relator
        String minToDate = null;
        String maxFromDate = null;
        EntityItem warrItem = null;
        EntityItem warrRel = null;
        boolean isDefWarr = false;

        WarrTPIC(String c, EntityItem item, EntityItem warrrel){
            ctry = c;
            warrRel = warrrel;
            effDate = PokUtils.getAttributeValue(warrRel, "EFFECTIVEDATE", "", EPOCH_DATE, false);
            endDate =PokUtils.getAttributeValue(warrRel, "ENDDATE", "", FOREVER_DATE, false);
            String defwarr = PokUtils.getAttributeFlagValue(warrRel, "DEFWARR");
            if(defwarr==null){
            	defwarr = DEFWARR_No;
            }
            isDefWarr = DEFWARR_Yes.equals(defwarr);
            warrItem = item;
        }
        /**
         * a.	From 1 = MAX(TPICF, PRODSTRUCTWARR.EFFECTIVEDATE)
         * b.	To 1 = MIN(TPICT, PRODSTRUCTWARR.ENDDATE)
         * 
         * a.	From 2 = MAX(TPICF, PRODSTRUCTWARR.EFFECTIVEDATE)
         * b.	To 2 = MIN(TPICT, PRODSTRUCTWARR.ENDDATE)
         * @param avail
         * @return
         */
        boolean hasOverlay(TPIC availtpic){
            boolean overlay = false;
            if(availtpic!=null && availtpic.ctry.equals(ctry)){ // ctry must match
                 //For matching WARRs, the EFFECTIVEDATE and the ENDATE are checked within the
                 //TPICF and TPICT range.
                 //If the WARRs EFFECTIVEDATE => TPICT or the WARRs ENDATE <= TPICF,
                 //then the WARR is not applicable for the TPIC.
                if((effDate.compareTo(availtpic.toDate)>=0) || // warr.eff is after avail.to
                    endDate.compareTo(availtpic.fromDate)<=0){ // warr.end is before avail.from
                }else{
                	//MAX(TPICF, PRODSTRUCTWARR.EFFECTIVEDATE)
                	if(effDate.compareTo(availtpic.fromDate)>=0){
                		maxFromDate = effDate;
                	}else{
                		maxFromDate = availtpic.fromDate;
                	}
                	//MIN(TPICT, PRODSTRUCTWARR.ENDDATE)
                 	if(endDate.compareTo(availtpic.toDate)<=0){
                 	     minToDate = endDate;
                	}else{
                	     minToDate = availtpic.toDate;
                	}
               
                    overlay = true;
                }
            }
            return overlay;
        }
        public boolean equals(Object obj) { // used in Vector.contains
            if (obj instanceof WarrTPIC){
                return warrItem.getEntityID()==((WarrTPIC)obj).warrItem.getEntityID();
            }
            return false;
        }
        public String toString() {
        	String minmax="";
        	if(minToDate!=null){
        		minmax=" maxFromDate:"+maxFromDate+" minToDate:"+minToDate;
        	}
            return "\nctry:"+ctry+" effDate:"+effDate+" toDate:"+endDate+minmax+
            	" "+warrItem.getKey()+" thru "+warrRel.getKey()+" defWarr "+isDefWarr;
        }
        void dereference(){
            ctry = null;
            effDate = null;
            endDate = null;
            warrItem = null;
            warrRel = null;
            minToDate = null;
            maxFromDate = null;
        }
    }
    private class StopWarrException extends Exception{
		private static final long serialVersionUID = 1L;
    }
}
