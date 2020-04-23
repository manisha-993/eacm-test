// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import java.util.*;
import org.w3c.dom.*;

/**********************************************************************************
* MODELSAPLXML class
*
* From "SG FS ABR SAPL 20070830.doc"
*/
// MODELSAPLXML.java,v
// Revision 1.3  2008/01/30 19:39:14  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2007/12/12 16:19:00  wendy
// CR1113074218 updates
//
// Revision 1.1  2007/09/13 12:40:28  wendy
// Init for RQ0426071527 - XCC GX
//
//
public class MODELSAPLXML extends SAPLXMLBase
{
    private static final String SAPVE_NAME = "SAPLVEMODEL";  // extract to use for xml
/*
SAPLVEMODEL 0   MODEL   Association MODELPROJA  PROJ
SAPLVEMODEL 0   MODEL   Relator     MODELAVAIL  AVAIL
SAPLVEMODEL 1   AVAIL   Association AVAILGAA    GENERALAREA
SAPLVEMODEL 1   AVAIL   Association AVAILANNA   ANNOUNCEMENT
*/
    private static final Vector SAPLXMLMAP_VCT;

/* SAPL XML
1   <Material>
2   <CableSpecsRequiredFlag>    MODEL.CBLSPECSREQ
2   <CustomerSetupAllowanceDays>    MODEL.CUSTSETUPALLOW
2   <Division>  PROJ.ClassOf(DIV)
2   <EAEligibility> MODEL.EAELIG
2   <GSAProductionStatus>   MODEL.PRODCD
2   <HourlyServiceRate> MODEL.HRLYSVCRATECLSCD
2   <IBMCreditCorp> MODEL.IBMCREDCORP
2   <LicensedInternalCode>  MODEL.LICNSINTERCD
2   <LineOfBusiness>    PROJ.LINEOFBUS
2   <LowEndIndicator>   MODEL.LOWENDFLG
2	<MachineMaintenanceGroupCode>	MODEL.ClassOf(MACHMAINTGRPCD) //CR1113074218
2   <MidrangeSystemOption>  MODEL.MIDRNGESYSOPT
2   <OEMIndicator>  MODEL.OEMINDC
2   <PlantOfManufacture>    MODEL.ClassOf(PLNTOFMFR)
2	<InterplantPlantCode>	GEOMOD.ClassOf(INTERPLNTCD) where GEOMOD.COUNTRYLIST = 1652 (US) //CR1113074218

TIR73RS77
2   <ProductTypeCategory>   MODEL.ClassOf(PRODTYPECATG)
2orig   <ProductGroupingCode>   MODEL.ClassOf(PRODTYPECATG)
end TIR73RS77
2   <ProductID> MODEL.MACHTYPEATR + MODEL.MODELATR
2   <ProductTypeCode>   MODEL.ClassOf(PRODTYPE)
2   <ProfitCenter>  MODEL.ClassOf(PRFTCTR)
2   <SalesManualIndicator>  MODEL.SLEMANLVIEWABL
2orig   <StockCategoryCode> MODEL.ClassOf(STOCKCATCD) TIR747QEJ
2   <StockCategoryCode> MODEL.STOCKCATCD
2   <VendorNumber>  MODEL.VENDID
2   <VolumeDiscount>    MODEL.VOLDISCNT
2   <ESAServiceLevelCategory>   MODEL.ESASVCLEVCATG
2   <AutomaticMaintenanceContract>  MODEL.AUTOMAINTCONTRCT
2   <BulkOrder> MODEL.BULKORD
2   <CountryIdleControl>    MODEL.CNTRYIDLECNTRL
2   <ColorSpecificationIndicator>   MODEL.COLRSPECINDC
2   <ComputerSystemCompoment>   MODEL.COMPUTSYSCOMPOMENT
2   <ComputerSystemStandalone>  MODEL.COMPUTSYSSTNDALONE
2   <CorporateServiceOption>    MODEL.CORPSVCOPT
2   <DPInstallProgram>  MODEL.DPINSTPGM
2   <EducationAllowance-HighSchool> MODEL.EDUCALLOWMHGHSCH
2   <EducationAllowance-SecondarySchool>    MODEL.EDUCALLOWMSECONDRYSCH
2   <EducationAllowance-University> MODEL.EDUCALLOWMUNVRSTY
2   <ESAOnSite> MODEL.ESAONSITE
2   <FrozenZonePeriod-months>   MODEL.FROZENZONEPRIODMMO
2   <InitialPeriodMaintenance>  MODEL.INITPRIODMAINT
2   <LowEndFlag>    MODEL.LOWENDFLG
2   <LPSEffectiveDate>  MODEL.LPSEFFCTVDATE
2   <MaintenanceGroup>  MODEL.MAINTGRP
2   <MaintenanceGroup-RentalPlanD>  MODEL.MAINTGRPMRENTPLAND
2   <MATPass>   MODEL.MATPASS
2   <MaximumMCSforPPRTTest> MODEL.MAXMCSFORPPRTTST
2   <MandatoryMAT>  MODEL.MNATORYMAT
2   <MOSPEligible>  MODEL.MOSPELIG
2   <NormalDeliveryTime-months> MODEL.NORMALDELIVTMEMMO
2   <NormalMaintenanceBilling-months>   MODEL.NORMALMAINTBILLMMO
2   <NormalTypeofMaintenace>    MODEL.NORMALTYPEOFMAINT
2   <OCTFactor-weeks>   MODEL.OCTFACTRMWK
2   <OrdersRejected>    MODEL.ORDREJCTED
2   <PerCallClass>  MODEL.PERCALLCLS
2   <PostOrderReview>   MODEL.POSTORDREVU
2   <PPTPAgreementPeriod>   MODEL.PPTPAGRMTPRIOD
2   <PPTPEligibility>   MODEL.PPTPELIG
2   <PPTPTestPeriod>    MODEL.PPTPTSTPRIOD
2   <PrimarySource-50hz>    MODEL.PRIMSRCM50HZ
2   <PrimarySource-60hz>    MODEL.PRIMSRCM60HZ
2   <ProductionAllotment>   MODEL.PRODALLOTMENT
2   <ProductIdentification> MODEL.PRODID
2   <PurchaseOnly>  MODEL.PURCHONLY
2   <RentalPlanType>    MODEL.RENTPLANTYPE
2   <ReturnedPartsMES>  MODEL.RETURNEDPARTS
2   <RMSEligible>   MODEL.RMSELIG
2   <RPQmachine>    MODEL.RPQMACH
2   <SalesManualViewable>   MODEL.SLEMANLVIEWABL
2   <SSTPEligibility>   MODEL.SSTPELIG
2   <SystemRentalPlanCharge>    MODEL.SYSRENTPLANCHRG
2   <TAPEligibility>    MODEL.TAPELIG
2   <TLPEligibility>    MODEL.TLPELIG
2   <TimeandMaterials>  MODEL.TMENMATRLS
2   <TradeinMachine>    MODEL.TRDINMACH
2   <Voltage>   MODEL.VOLT
2   <DescriptionDataList>
3   <DescriptionData>
4   <MaterialDescription>   MODEL.MKTGNAME
4   <DescriptionLanguage>   NLSID ==> CHQISONLSIDMAP.CHQNLSID : CHQISONLSID.CHQISOLANG
3   </DescriptionData>
2   </DescriptionDataList>
2   <GeographyList> The AVAILs are related to MODEL
3   <Geography> use AVAIL.COUNTRYLIST to GENERALAREA where GENERALAREA.GENAREATYPE=2452 (Country)
4   <RFAGEO>    GENERALAREA.RFAGEO
4   <Country>   GENERALAREA.GENAREANAME
4   <SalesOrg>  GENERALAREA.SLEORG
4   <SalesOffice>   GENERALAREA.SLEOFFC
4   <AASOrderIndicator> AVAIL.ORDERSYSNAME
4   <AASViewableIndicator>  AVAIL.AASVIEWABL
4   <LeaseRentalWithrawalDate>  AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE= AVT220 (Lease Rental Withdrawal)
4   <PackageName>   ANNOUNCEMENT.ANNNUMBER
	<AnnouncementType>	ANNOUNCEMENT.ANNTYPE //CR1113074218
4   <ProductAnnounceDateCountry>    ANNOUNCEMENT.ANNDATE
4   <ProductWithdrawalDate> AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE= 149 (LastOrder)
3   </Geography>
2   </GeographyList>
1   </Material>
*/
/*
1   <wsnt:Notify xmlns:wsnt="http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd" xmlns:ebi="http://ibm.com/esh/ebi">
2   <wsnt:NotificationMessage>
3   <wsnt:Topic Dialect="http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete">
    For Model, Prodstruct, Orderable
        Type/Legacy/Nomenclature/?1/Country/?2/?2/.../EndCountry/
    For AccountingUse
        Type/Manual/Scope/?3/
3   </wsnt:Topic>
3   <wsnt:Message>
4   <ebi:MessageID> EA00000000  <ebi:MessageID> increment as a number
4   <ebi:priority>  "Normal"    <ebi:priority>  constant
4   <PayloadFormat  EACM_Material   </PayloadFormat
4   <NativeCodePage>    0   </NativeCodePage>
4   <body>
    insert EACM PayLoad here
4   </body>
3   </wsnt:Message>
2   </wsnt:NotificationMessage>
1   </wsnt:Notify>

    ?1 = MTM for Model or Prodstruct
    ?1 = PN for OrderablePartnumber
    ?2 = Country Code like US, CA
    ?3 = ACCTGUSEONLYMATRL.PRODTYPE
*/
    static {
        SAPLXMLMAP_VCT = new Vector();  // set of elements
        SAPLElem topElem = new SAPLElem("wsnt:Notify");
        topElem.addXMLAttribute("xmlns:wsnt",XMLNS_WSNT);
        topElem.addXMLAttribute("xmlns:ebi",XMLNS_EBI);
        SAPLXMLMAP_VCT.addElement(topElem);
         // level2
        SAPLElem level2Elem = new SAPLElem("wsnt:NotificationMessage");
        topElem.addChild(level2Elem);
        //level 3
        level2Elem.addChild(new SAPLTopicElem());
        SAPLElem messageElem = new SAPLElem("wsnt:Message");
        level2Elem.addChild(messageElem);
        // level4
        messageElem.addChild(new SAPLMessageIDElem());
        messageElem.addChild(new SAPLFixedElem("ebi:priority","Normal"));
        messageElem.addChild(new SAPLFixedElem("PayloadFormat","EACM_Material"));
        messageElem.addChild(new SAPLFixedElem("NativeCodePage","1208"));

        SAPLElem bodyElem = new SAPLElem("body");
        messageElem.addChild(bodyElem);

        SAPLElem materialElem = new SAPLElem("Material"); // add EACM payload level1
        bodyElem.addChild(materialElem);

        // add EACM payload level2(s)
        materialElem.addChild(new SAPLFixedElem("EACMEntityType","MODEL"));
        materialElem.addChild(new SAPLIdElem("EACMEntityId"));
        materialElem.addChild(new SAPLElem("CableSpecsRequiredFlag","MODEL","CBLSPECSREQ",true));
        materialElem.addChild(new SAPLElem("CustomerSetupAllowanceDays","MODEL","CUSTSETUPALLOW",true));
        materialElem.addChild(new SAPLElem("Division","PROJ","DIV",SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("EAEligibility","MODEL","EAELIG",true));
        materialElem.addChild(new SAPLElem("GSAProductionStatus","MODEL","PRODCD",true));
        materialElem.addChild(new SAPLElem("HourlyServiceRate","MODEL","HRLYSVCRATECLSCD",true));
        materialElem.addChild(new SAPLElem("IBMCreditCorp","MODEL","IBMCREDCORP",true));
        materialElem.addChild(new SAPLElem("LicensedInternalCode","MODEL","LICNSINTERCD",true));
        materialElem.addChild(new SAPLElem("LineOfBusiness","PROJ","LINEOFBUS"));
        materialElem.addChild(new SAPLElem("LowEndIndicator","MODEL","LOWENDFLG",true));

        materialElem.addChild(new SAPLElem("MachineMaintenanceGroupCode","MODEL","MACHMAINTGRPCD",true,SAPLElem.FLAGVAL)); //CR1113074218
        materialElem.addChild(new SAPLElem("MidrangeSystemOption","MODEL","MIDRNGESYSOPT",true));
        materialElem.addChild(new SAPLElem("OEMIndicator","MODEL","OEMINDC",true));
        materialElem.addChild(new SAPLElem("PlantOfManufacture","MODEL","PLNTOFMFR",true,SAPLElem.FLAGVAL));
  		materialElem.addChild(new SAPLGEOFilteredElem("InterplantPlantCode","GEOMOD", //CR1113074218
            "INTERPLNTCD", "COUNTRYLIST","1652",SAPLElem.FLAGVAL));

//TIR73RS77     materialElem.addChild(new SAPLElem("ProductGroupingCode","MODEL","PRODTYPECATG",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("ProductTypeCategory","MODEL","PRODTYPECATG",true,SAPLElem.FLAGVAL)); //TIR73RS77
        materialElem.addChild(new SAPLElem("ProductID","MODEL","MACHTYPEATR|MODELATR",true));
        materialElem.addChild(new SAPLElem("ProductTypeCode","MODEL","PRODTYPE",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("ProfitCenter","MODEL","PRFTCTR",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("SalesManualIndicator","MODEL","SLEMANLVIEWABL",true));
//TIR747QEJ     materialElem.addChild(new SAPLElem("StockCategoryCode","MODEL","STOCKCATCD",true,SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("StockCategoryCode","MODEL","STOCKCATCD",true));
        materialElem.addChild(new SAPLElem("VendorNumber","MODEL","VENDID",true));
        materialElem.addChild(new SAPLElem("VolumeDiscount","MODEL","VOLDISCNT",true));
        materialElem.addChild(new SAPLElem("ESAServiceLevelCategory","MODEL","ESASVCLEVCATG",true));
        materialElem.addChild(new SAPLElem("AutomaticMaintenanceContract","MODEL","AUTOMAINTCONTRCT",true));
        materialElem.addChild(new SAPLElem("BulkOrder","MODEL","BULKORD",true));
        materialElem.addChild(new SAPLElem("CountryIdleControl","MODEL","CNTRYIDLECNTRL",true));
        materialElem.addChild(new SAPLElem("ColorSpecificationIndicator","MODEL","COLRSPECINDC",true));
        materialElem.addChild(new SAPLElem("ComputerSystemCompoment","MODEL","COMPUTSYSCOMPOMENT",true));
        materialElem.addChild(new SAPLElem("ComputerSystemStandalone","MODEL","COMPUTSYSSTNDALONE",true));
        materialElem.addChild(new SAPLElem("CorporateServiceOption","MODEL","CORPSVCOPT",true));
        materialElem.addChild(new SAPLElem("DPInstallProgram","MODEL","DPINSTPGM",true));
        materialElem.addChild(new SAPLElem("EducationAllowance-HighSchool","MODEL","EDUCALLOWMHGHSCH",true));
        materialElem.addChild(new SAPLElem("EducationAllowance-SecondarySchool","MODEL","EDUCALLOWMSECONDRYSCH",true));
        materialElem.addChild(new SAPLElem("EducationAllowance-University","MODEL","EDUCALLOWMUNVRSTY",true));
        materialElem.addChild(new SAPLElem("ESAOnSite","MODEL","ESAONSITE",true));
        materialElem.addChild(new SAPLElem("FrozenZonePeriod-months","MODEL","FROZENZONEPRIODMMO",true));
        materialElem.addChild(new SAPLElem("InitialPeriodMaintenance","MODEL","INITPRIODMAINT",true));
        materialElem.addChild(new SAPLElem("LowEndFlag","MODEL","LOWENDFLG",true));
        materialElem.addChild(new SAPLElem("LPSEffectiveDate","MODEL","LPSEFFCTVDATE",true));
        materialElem.addChild(new SAPLElem("MaintenanceGroup","MODEL","MAINTGRP",true));
        materialElem.addChild(new SAPLElem("MaintenanceGroup-RentalPlanD","MODEL","MAINTGRPMRENTPLAND",true));
        materialElem.addChild(new SAPLElem("MATPass","MODEL","MATPASS",true));
        materialElem.addChild(new SAPLElem("MaximumMCSforPPRTTest","MODEL","MAXMCSFORPPRTTST",true));
        materialElem.addChild(new SAPLElem("MandatoryMAT","MODEL","MNATORYMAT",true));
        materialElem.addChild(new SAPLElem("MOSPEligible","MODEL","MOSPELIG",true));
        materialElem.addChild(new SAPLElem("NormalDeliveryTime-months","MODEL","NORMALDELIVTMEMMO",true));
        materialElem.addChild(new SAPLElem("NormalMaintenanceBilling-months","MODEL","NORMALMAINTBILLMMO",true));
        materialElem.addChild(new SAPLElem("NormalTypeofMaintenace","MODEL","NORMALTYPEOFMAINT",true));
        materialElem.addChild(new SAPLElem("OCTFactor-weeks","MODEL","OCTFACTRMWK",true));
        materialElem.addChild(new SAPLElem("OrdersRejected","MODEL","ORDREJCTED",true));
        materialElem.addChild(new SAPLElem("PerCallClass","MODEL","PERCALLCLS",true));
        materialElem.addChild(new SAPLElem("PostOrderReview","MODEL","POSTORDREVU",true));
        materialElem.addChild(new SAPLElem("PPTPAgreementPeriod","MODEL","PPTPAGRMTPRIOD",true));
        materialElem.addChild(new SAPLElem("PPTPEligibility","MODEL","PPTPELIG",true));
        materialElem.addChild(new SAPLElem("PPTPTestPeriod","MODEL","PPTPTSTPRIOD",true));
        materialElem.addChild(new SAPLElem("PrimarySource-50hz","MODEL","PRIMSRCM50HZ",true));
        materialElem.addChild(new SAPLElem("PrimarySource-60hz","MODEL","PRIMSRCM60HZ",true));
        materialElem.addChild(new SAPLElem("ProductionAllotment","MODEL","PRODALLOTMENT",true));
        materialElem.addChild(new SAPLElem("ProductIdentification","MODEL","PRODID",true));
        materialElem.addChild(new SAPLElem("PurchaseOnly","MODEL","PURCHONLY",true));
        materialElem.addChild(new SAPLElem("RentalPlanType","MODEL","RENTPLANTYPE",true));
        materialElem.addChild(new SAPLElem("ReturnedPartsMES","MODEL","RETURNEDPARTS",true));
        materialElem.addChild(new SAPLElem("RMSEligible","MODEL","RMSELIG",true));
        materialElem.addChild(new SAPLElem("RPQmachine","MODEL","RPQMACH",true));
        materialElem.addChild(new SAPLElem("SalesManualViewable","MODEL","SLEMANLVIEWABL",true));
        materialElem.addChild(new SAPLElem("SSTPEligibility","MODEL","SSTPELIG",true));
        materialElem.addChild(new SAPLElem("SystemRentalPlanCharge","MODEL","SYSRENTPLANCHRG",true));
        materialElem.addChild(new SAPLElem("TAPEligibility","MODEL","TAPELIG",true));
        materialElem.addChild(new SAPLElem("TLPEligibility","MODEL","TLPELIG",true));
        materialElem.addChild(new SAPLElem("TimeandMaterials","MODEL","TMENMATRLS",true));
        materialElem.addChild(new SAPLElem("TradeinMachine","MODEL","TRDINMACH",true));
        materialElem.addChild(new SAPLElem("Voltage","MODEL","VOLT",true));

        SAPLElem elem3 = new SAPLNLSElem("DescriptionData");
        // add level4
        elem3.addChild(new SAPLElem("MaterialDescription","MODEL","MKTGNAME",true));
        elem3.addChild(new SAPLCHQISOElem("DescriptionLanguage"));
        SAPLElem listElem = new SAPLElem("DescriptionDataList");
        // add level3
        listElem.addChild(elem3);
        // add level2(s)
        materialElem.addChild(listElem);

        listElem = new SAPLElem("GeographyList"); // level2
        materialElem.addChild(listElem);

        elem3 = new SAPLGEOElem("Geography","AVAIL","AVAILGAA");        // level3
        listElem.addChild(elem3);
        elem3.addChild(new SAPLItemElem("RFAGEO","GENERALAREA","RFAGEO"));
        elem3.addChild(new SAPLItemElem("Country","GENERALAREA","GENAREANAME"));
        elem3.addChild(new SAPLItemElem("SalesOrg","GENERALAREA","SLEORG"));
        elem3.addChild(new SAPLItemElem("SalesOffice","GENERALAREA","SLEOFFC"));
        elem3.addChild(new SAPLGEOAvailElem("AASOrderIndicator","ORDERSYSNAME"));
        elem3.addChild(new SAPLGEOAvailElem("AASViewableIndicator","AASVIEWABL"));
        elem3.addChild(new SAPLGEOFilteredElem("LeaseRentalWithrawalDate","AVAIL",
            "EFFECTIVEDATE", "AVAILTYPE","AVT220"));
        elem3.addChild(new SAPLGEOAnnElem("PackageName","ANNNUMBER"));
        elem3.addChild(new SAPLGEOAnnElem("AnnouncementType","ANNTYPE")); //CR1113074218
        elem3.addChild(new SAPLGEOAnnElem("ProductAnnounceDateCountry","ANNDATE"));
        elem3.addChild(new SAPLGEOFilteredElem("ProductWithdrawalDate","AVAIL",
            "EFFECTIVEDATE","AVAILTYPE","149"));
    }

    /**********************************
    * get the name(s) of the MQ properties file to use, could be more than one
Wayne Kehrli
ESH = all but ACCGUSEONLYMATRL
OIDH = all but PRODSTRUCT & SWPRODSTRUCT
    */
    protected Vector getMQPropertiesFN() {
        Vector vct = new Vector(1);
        vct.add(OIDHMQSERIES);
        vct.add(ESHMQSERIES);
        return vct;
    }

    /**********************************
    * get the name of the VE to use for the feed
    */
    protected String getSaplVeName() { return SAPVE_NAME;}

    /**********************************
    * get SAPL xml objects
    */
    protected Vector getSAPLXMLMap() { return SAPLXMLMAP_VCT;}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.3";
    }

    static class SAPLTopicElem extends SAPLElem
    {
        /**********************************************************************************
        * Constructor for Topic element
        *
        *3  <wsnt:Topic Dialect="http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete">
        *   For MODEL, PRODSTRUCT, SWPRODSTRUCT, ORDABLEPARTNO
        *       esh:MaterialLegacy/Nomenclature/{MODEL | PRODSTRUCT | SWPRODSTRUCT | ORDABLEPARTNO}/Country/?2/?2/EndCountry
        *3  </wsnt:Topic>
        *
        *?1 = MTM for Model or Prodstruct
        *?2 = Country Code like US, CA
        */
        SAPLTopicElem()
        {
            super("wsnt:Topic",null,null,false);
            addXMLAttribute("Dialect", "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete");
        }

        /**********************************************************************************
        * Create a node for this element and add to the parent and any children this node has
        *
        *@param list EntityList
        *@param document Document needed to create nodes
        *@param parent Element node to add this node too
        *@param debugSb StringBuffer for debug output
        */
        public void addElements(Database dbCurrent, EntityList list, Document document, Element parent,
            StringBuffer debugSb)
        throws
            COM.ibm.eannounce.objects.EANBusinessRuleException,
            java.sql.SQLException,
            COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
            COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
            java.rmi.RemoteException,
            java.io.IOException,
            COM.ibm.opicmpdh.middleware.MiddlewareException,
            COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
        {
            Element elem = (Element) document.createElement(nodeName);
            addXMLAttrs(elem);
            //EntityItem item = list.getParentEntityGroup().getEntityItem(0);

            Vector itemVct = SAPLGEOElem.getAvailEntities(list.getEntityGroup("AVAIL")); // find particular
            String ctrys = getCountryCodes(list, itemVct, "AVAILGAA", debugSb);

            String value = "esh:MaterialLegacy/Nomenclature/MODEL/Country"+ctrys+"/EndCountry";
            elem.appendChild(document.createTextNode(value));
            parent.appendChild(elem);

            // add any children
            for (int c=0; c<childVct.size(); c++){
                SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
                childElem.addElements(dbCurrent,list, document,elem,debugSb);
            }
        }
    }
}
