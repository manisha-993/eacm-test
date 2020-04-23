// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import com.ibm.transform.oim.eacm.util.*;
import java.util.*;
import org.w3c.dom.*;

/**********************************************************************************
* PRODSTRUCTSAPLXML
*
* From "SAPL FS xSeries ABRs 20070419.doc"
*
* The Product Structure (PRODSTRUCT) is part of a complex data structure and hence a
* Virtual Entity (VE) may be used to describe the applicable structure for the Data
* Quality checks and another VE for the SAPL feed.
*/

//PRODSTRUCTSAPLXML.java,v
//Revision 1.6  2011/03/22 11:20:24  wendy
//Reassign attr meta to diff group
//
//Revision 1.5  2008/01/30 19:39:16  wendy
//Cleanup RSA warnings
//
//Revision 1.4  2007/12/12 16:19:00  wendy
//CR1113074218 updates
//
//Revision 1.3  2007/12/06 20:59:47  wendy
//TIR79LNMS restored dropped FeatureDescriptionDataList element
//
//Revision 1.2  2007/10/18 11:54:46  wendy
//correct mergelists()
//
//Revision 1.1  2007/09/13 12:40:28  wendy
//Init for RQ0426071527 - XCC GX
//

public class PRODSTRUCTSAPLXML extends SAPLXMLBase
{
  // Class constants

    private static final String SAPVE_NAME = "SAPLVEPRODSTRUCT";  // extract to use for xml
/*
SAPLVEPRODSTRUCT    0   FEATURE Relator PRODSTRUCT  MODEL
SAPLVEPRODSTRUCT    0   PRODSTRUCT  Relator OOFAVAIL    AVAIL
SAPLVEPRODSTRUCT    1   AVAIL   Association AVAILANNA   ANNOUNCEMENT
SAPLVEPRODSTRUCT    1   AVAIL   Association AVAILGAA    GENERALAREA
SAPLVEPRODSTRUCT    1   MODEL   Association MODELPROJA  PROJ
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
2   <ProfitCenter>  MODEL.ClassOf(PRFTCTR)
2   <SalesManualIndicator>  MODEL.SLEMANLVIEWABL
2orig   <StockCategoryCode> MODEL.ClassOf(STOCKCATCD)
2   <StockCategoryCode> MODEL.STOCKCATCD
2   <VendorNumber>  MODEL.VENDID
2   <VolumeDiscount>    MODEL.VOLDISCNT
2   <ESAServiceLevelCategory>   MODEL.ESASVCLEVCATG
the following were deleted on 4/19/07  "SAPL FS xSeries ABRs 20070419.doc"
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
end of deleted on 4/19/07  "SAPL FS xSeries ABRs 20070419.doc"

2   <DescriptionDataList>
3   <DescriptionData>
4   <MaterialDescription>   MODEL.MKTGNAME
4   <DescriptionLanguage>   NLSID ==> CHQISONLSIDMAP.CHQNLSID : CHQISONLSID.CHQISOLANG
3   </DescriptionData>
2   </DescriptionDataList>
2   <GeographyList> The AVAILs from here down are related to PRODSTRUCT (not MODEL)
3   <Geography> use AVAIL.COUNTRYLIST to GENERALAREA where GENERALAREA.GENAREATYPE=2452 (Country)
4   <RFAGEO>    GENERALAREA.RFAGEO
4   <Country>   GENERALAREA.GENAREANAME
4   <SalesOrg>  GENERALAREA.SLEORG
4   <SalesOffice>   GENERALAREA.SLEOFFC
4   <AASOrderIndicator> AVAIL.ORDERSYSNAME
4   <AASViewableIndicator>  AVAIL.AASVIEWABL
4   <LeaseRentalWithrawalDate>  AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE= xxx (Lease Rental Withdrawal)
4   <PackageName>   ANNOUNCEMENT.ANNNUMBER
	<AnnouncementType>	ANNOUNCEMENT.ANNTYPE //CR1113074218
4   <ProductAnnounceDateCountry>    ANNOUNCEMENT.ANNDATE
4   <ProductWithdrawalDate> AVAIL.EFFECTIVEDATE where AVAIL.AVAILTYPE= 149 (LastOrder)
3   </Geography>
2   </GeographyList>
2   <FeatureList>
3   <Feature>
4   <CableSpecsRequiredFlag>    FEATURE.CBLSPECSREQ
4   <EAEligibility> FEATURE.EAELIG
4   <ESAOnsite> FEATURE.ESAONSITE
4   <LowEndIndicator>   FEATURE.LOWENDFLG
4   <MachineMaintenanceGroupCode>   FEATURE.MACHMAINTGRPCD
4   <ProductID> FEATURE.FEATURECODE
4   <MOSPEligible>  FEATURE.MOSPELIG
4   <PlantOfManufacture>   FEATURE.ClassOf(PLNTOFMFR)
4   <VolumeDiscount>    FEATURE.VOLDISCNT
4   <SalesManualIndicator>  FEATURE.SLEMANLVIEWABL
4   <CentralFacilityMaintenance>    FEATURE.CNTRLFACILMAINT
4   <ColorSpecificationIndicator>   FEATURE.COLRSPECINDC
4   <CompatibilityFeature>  FEATURE.COMPATFEAT
4   <DeferredCentralFacilityMaintenance>    FEATURE.DEFERCNTRLFACILMAINT
4   <DPInstallProgram>  FEATURE.DPINSTPGM
4   <EducationAllowance-HighSchool> FEATURE.EDUCALLOWMHGHSCH
4   <EducationAllowance-SecondarySchool>    FEATURE.EDUCALLOWMSECONDRYSCH
4   <EducationAllowance-University> FEATURE.EDUCALLOWMUNVRSTY
4   <FieldInstall-hours>    FEATURE.FLDINSTMHR
4   <HourlyServiceRateClassCode>    FEATURE.HRLYSVCRATECLSCD
4   <OrdersRejected>    FEATURE.ORDREJCTED
4   <ProfitCenter>  FEATURE.PRFTCTR
4   <PurchaseOnly>  FEATURE.PURCHONLY
4   <SingleUse-One-timeCharge>  FEATURE.SNGLUSEMONEMTMECHRG
4   <StandardMaintenance>   FEATURE.STDMAINT
4   <ServiceRepairCenter>   FEATURE.SVCREPARCTR
4   <TimeandMaterials>  FEATURE.TMENMATRLS
4   <Voltage>   FEATURE.VOLT
4   <CustomerSetupAllowanceDays>    PRODSTRUCT.CUSTSETUPALLOW
4   <FeatureDescriptionDataList>
5   <FeatureDescriptionData>
6   <FeatureDescription>    FEATURE.MKTGNAME
6   <FeatureDescriptionLanguage>    NLSID ==> CHQISONLSIDMAP.CHQNLSID : CHQISONLSID.CHQISOLANG
5   </FeatureDescriptionData>
4   </FeatureDescriptionDataList>
3   </Feature
2   </FeatureList>

1   </Material>

i added to ve
insert into opicm.metalinkattr values ('SG','Action/Entity','SAPLVEPRODSTRUCT','PRODSTRUCT','U','0', '1970-01-01-00.00.00.000000','9999-12-31-00.00.00.000000', '1970-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',7777,1)
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
        materialElem.addChild(new SAPLFixedElem("EACMEntityType","PRODSTRUCT"));
        materialElem.addChild(new SAPLIdElem("EACMEntityId"));
        materialElem.addChild(new SAPLElem("CableSpecsRequiredFlag","MODEL","CBLSPECSREQ"));
        materialElem.addChild(new SAPLElem("CustomerSetupAllowanceDays","MODEL","CUSTSETUPALLOW"));
        materialElem.addChild(new SAPLElem("Division","PROJ","DIV",SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("EAEligibility","MODEL","EAELIG"));
        materialElem.addChild(new SAPLElem("GSAProductionStatus","MODEL","PRODCD"));
        materialElem.addChild(new SAPLElem("HourlyServiceRate","MODEL","HRLYSVCRATECLSCD"));
        materialElem.addChild(new SAPLElem("IBMCreditCorp","MODEL","IBMCREDCORP"));
        materialElem.addChild(new SAPLElem("LicensedInternalCode","MODEL","LICNSINTERCD"));
        materialElem.addChild(new SAPLElem("LineOfBusiness","PROJ","LINEOFBUS"));
        materialElem.addChild(new SAPLElem("LowEndIndicator","MODEL","LOWENDFLG"));
        materialElem.addChild(new SAPLElem("MachineMaintenanceGroupCode","MODEL","MACHMAINTGRPCD",SAPLElem.FLAGVAL));//CR1113074218
        materialElem.addChild(new SAPLElem("MidrangeSystemOption","MODEL","MIDRNGESYSOPT"));
        materialElem.addChild(new SAPLElem("OEMIndicator","MODEL","OEMINDC"));
        materialElem.addChild(new SAPLElem("PlantOfManufacture","MODEL","PLNTOFMFR",SAPLElem.FLAGVAL));
  		materialElem.addChild(new SAPLGEOFilteredElem("InterplantPlantCode","GEOMOD", //CR1113074218
            "INTERPLNTCD", "COUNTRYLIST","1652",SAPLElem.FLAGVAL));

//TIR73RS77     materialElem.addChild(new SAPLElem("ProductGroupingCode","MODEL","PRODTYPECATG",SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("ProductTypeCategory","MODEL","PRODTYPECATG",SAPLElem.FLAGVAL)); // TIR73RS77
        materialElem.addChild(new SAPLElem("ProductID","MODEL","MACHTYPEATR|MODELATR"));
        materialElem.addChild(new SAPLElem("ProfitCenter","MODEL","PRFTCTR",SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("SalesManualIndicator","MODEL","SLEMANLVIEWABL"));
//TIR747QEJ     materialElem.addChild(new SAPLElem("StockCategoryCode","MODEL","STOCKCATCD",SAPLElem.FLAGVAL));
        materialElem.addChild(new SAPLElem("StockCategoryCode","MODEL","STOCKCATCD"));
        materialElem.addChild(new SAPLElem("VendorNumber","MODEL","VENDID"));
        materialElem.addChild(new SAPLElem("VolumeDiscount","MODEL","VOLDISCNT"));
        materialElem.addChild(new SAPLElem("ESAServiceLevelCategory","MODEL","ESASVCLEVCATG"));
/*all deleted on 4/19/07
        materialElem.addChild(new SAPLElem("AutomaticMaintenanceContract","MODEL","AUTOMAINTCONTRCT"));
        materialElem.addChild(new SAPLElem("BulkOrder","MODEL","BULKORD"));
        materialElem.addChild(new SAPLElem("CountryIdleControl","MODEL","CNTRYIDLECNTRL"));
        materialElem.addChild(new SAPLElem("ColorSpecificationIndicator","MODEL","COLRSPECINDC"));
        materialElem.addChild(new SAPLElem("ComputerSystemCompoment","MODEL","COMPUTSYSCOMPOMENT"));
        materialElem.addChild(new SAPLElem("ComputerSystemStandalone","MODEL","COMPUTSYSSTNDALONE"));
        materialElem.addChild(new SAPLElem("CorporateServiceOption","MODEL","CORPSVCOPT"));
        materialElem.addChild(new SAPLElem("DPInstallProgram","MODEL","DPINSTPGM"));
        materialElem.addChild(new SAPLElem("EducationAllowance-HighSchool","MODEL","EDUCALLOWMHGHSCH"));
        materialElem.addChild(new SAPLElem("EducationAllowance-SecondarySchool","MODEL","EDUCALLOWMSECONDRYSCH"));
        materialElem.addChild(new SAPLElem("EducationAllowance-University","MODEL","EDUCALLOWMUNVRSTY"));
        materialElem.addChild(new SAPLElem("ESAOnSite","MODEL","ESAONSITE"));
        materialElem.addChild(new SAPLElem("FrozenZonePeriod-months","MODEL","FROZENZONEPRIODMMO"));
        materialElem.addChild(new SAPLElem("InitialPeriodMaintenance","MODEL","INITPRIODMAINT"));
        materialElem.addChild(new SAPLElem("LowEndFlag","MODEL","LOWENDFLG"));
        materialElem.addChild(new SAPLElem("LPSEffectiveDate","MODEL","LPSEFFCTVDATE"));
        materialElem.addChild(new SAPLElem("MaintenanceGroup","MODEL","MAINTGRP"));
        materialElem.addChild(new SAPLElem("MaintenanceGroup-RentalPlanD","MODEL","MAINTGRPMRENTPLAND"));
        materialElem.addChild(new SAPLElem("MATPass","MODEL","MATPASS"));
        materialElem.addChild(new SAPLElem("MaximumMCSforPPRTTest","MODEL","MAXMCSFORPPRTTST"));
        materialElem.addChild(new SAPLElem("MandatoryMAT","MODEL","MNATORYMAT"));
        materialElem.addChild(new SAPLElem("MOSPEligible","MODEL","MOSPELIG"));
        materialElem.addChild(new SAPLElem("NormalDeliveryTime-months","MODEL","NORMALDELIVTMEMMO"));
        materialElem.addChild(new SAPLElem("NormalMaintenanceBilling-months","MODEL","NORMALMAINTBILLMMO"));
        materialElem.addChild(new SAPLElem("NormalTypeofMaintenace","MODEL","NORMALTYPEOFMAINT"));
        materialElem.addChild(new SAPLElem("OCTFactor-weeks","MODEL","OCTFACTRMWK"));
        materialElem.addChild(new SAPLElem("OrdersRejected","MODEL","ORDREJCTED"));
        materialElem.addChild(new SAPLElem("PerCallClass","MODEL","PERCALLCLS"));
        materialElem.addChild(new SAPLElem("PostOrderReview","MODEL","POSTORDREVU"));
        materialElem.addChild(new SAPLElem("PPTPAgreementPeriod","MODEL","PPTPAGRMTPRIOD"));
        materialElem.addChild(new SAPLElem("PPTPEligibility","MODEL","PPTPELIG"));
        materialElem.addChild(new SAPLElem("PPTPTestPeriod","MODEL","PPTPTSTPRIOD"));
        materialElem.addChild(new SAPLElem("PrimarySource-50hz","MODEL","PRIMSRCM50HZ"));
        materialElem.addChild(new SAPLElem("PrimarySource-60hz","MODEL","PRIMSRCM60HZ"));
        materialElem.addChild(new SAPLElem("ProductionAllotment","MODEL","PRODALLOTMENT"));
        materialElem.addChild(new SAPLElem("ProductIdentification","MODEL","PRODID"));
        materialElem.addChild(new SAPLElem("PurchaseOnly","MODEL","PURCHONLY"));
        materialElem.addChild(new SAPLElem("RentalPlanType","MODEL","RENTPLANTYPE"));
        materialElem.addChild(new SAPLElem("ReturnedPartsMES","MODEL","RETURNEDPARTS"));
        materialElem.addChild(new SAPLElem("RMSEligible","MODEL","RMSELIG"));
        materialElem.addChild(new SAPLElem("RPQmachine","MODEL","RPQMACH"));
        materialElem.addChild(new SAPLElem("SalesManualViewable","MODEL","SLEMANLVIEWABL"));
        materialElem.addChild(new SAPLElem("SSTPEligibility","MODEL","SSTPELIG"));
        materialElem.addChild(new SAPLElem("SystemRentalPlanCharge","MODEL","SYSRENTPLANCHRG"));
        materialElem.addChild(new SAPLElem("TAPEligibility","MODEL","TAPELIG"));
        materialElem.addChild(new SAPLElem("TLPEligibility","MODEL","TLPELIG"));
        materialElem.addChild(new SAPLElem("TimeandMaterials","MODEL","TMENMATRLS"));
        materialElem.addChild(new SAPLElem("TradeinMachine","MODEL","TRDINMACH"));
        materialElem.addChild(new SAPLElem("Voltage","MODEL","VOLT"));
*/

        SAPLElem elem3 = new SAPLNLSElem("DescriptionData");
        // add level4
        elem3.addChild(new SAPLElem("MaterialDescription","MODEL","MKTGNAME"));
        elem3.addChild(new SAPLCHQISOElem("DescriptionLanguage"));
        SAPLElem listElem = new SAPLElem("DescriptionDataList");
        // add level3
        listElem.addChild(elem3);
        // add level2(s)
        materialElem.addChild(listElem);

        listElem = new SAPLElem("GeographyList"); // level2 TIR74BL5E
        materialElem.addChild(listElem); //TIR74BL5E

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
        elem3.addChild(new SAPLGEOAnnElem("AnnouncementType","ANNTYPE"));  //CR1113074218
        elem3.addChild(new SAPLGEOAnnElem("ProductAnnounceDateCountry","ANNDATE"));
        elem3.addChild(new SAPLGEOFilteredElem("ProductWithdrawalDate","AVAIL",
            "EFFECTIVEDATE", "AVAILTYPE","149"));

        listElem = new SAPLElem("FeatureList"); // level2
        materialElem.addChild(listElem);
        elem3 = new SAPLElem("Feature");        // level3
        listElem.addChild(elem3);
        elem3.addChild(new SAPLElem("CableSpecsRequiredFlag","FEATURE","CBLSPECSREQ"));
        elem3.addChild(new SAPLElem("EAEligibility","FEATURE","EAELIG"));
        elem3.addChild(new SAPLElem("ESAOnsite","FEATURE","ESAONSITE"));
        elem3.addChild(new SAPLElem("LowEndIndicator","FEATURE","LOWENDFLG"));
        elem3.addChild(new SAPLElem("MachineMaintenanceGroupCode","FEATURE","MACHMAINTGRPCD",SAPLElem.FLAGVAL));//CR1113074218
        elem3.addChild(new SAPLElem("ProductID","FEATURE","FEATURECODE"));
        elem3.addChild(new SAPLElem("MOSPEligible","FEATURE","MOSPELIG"));
        elem3.addChild(new SAPLElem("PlantOfManufacture","FEATURE","PLNTOFMFR",SAPLElem.FLAGVAL));
        elem3.addChild(new SAPLElem("VolumeDiscount","FEATURE","VOLDISCNT"));
        elem3.addChild(new SAPLElem("SalesManualIndicator","FEATURE","SLEMANLVIEWABL"));
        elem3.addChild(new SAPLElem("CentralFacilityMaintenance","FEATURE","CNTRLFACILMAINT"));
        elem3.addChild(new SAPLElem("ColorSpecificationIndicator","FEATURE","COLRSPECINDC"));
        elem3.addChild(new SAPLElem("CompatibilityFeature","FEATURE","COMPATFEAT"));
        elem3.addChild(new SAPLElem("DeferredCentralFacilityMaintenance","FEATURE","DEFERCNTRLFACILMAINT"));
        elem3.addChild(new SAPLElem("DPInstallProgram","FEATURE","DPINSTPGM"));
        elem3.addChild(new SAPLElem("EducationAllowance-HighSchool","FEATURE","EDUCALLOWMHGHSCH"));
        elem3.addChild(new SAPLElem("EducationAllowance-SecondarySchool","FEATURE","EDUCALLOWMSECONDRYSCH"));
        elem3.addChild(new SAPLElem("EducationAllowance-University","FEATURE","EDUCALLOWMUNVRSTY"));
        elem3.addChild(new SAPLElem("FieldInstall-hours","FEATURE","FLDINSTMHR"));
        elem3.addChild(new SAPLElem("HourlyServiceRateClassCode","FEATURE","HRLYSVCRATECLSCD"));
        elem3.addChild(new SAPLElem("OrdersRejected","FEATURE","ORDREJCTED"));
        elem3.addChild(new SAPLElem("ProfitCenter","FEATURE","PRFTCTR",SAPLElem.FLAGVAL));
        elem3.addChild(new SAPLElem("PurchaseOnly","FEATURE","PURCHONLY"));
        elem3.addChild(new SAPLElem("SingleUse-One-timeCharge","FEATURE","SNGLUSEMONEMTMECHRG"));
        elem3.addChild(new SAPLElem("StandardMaintenance","FEATURE","STDMAINT"));
        elem3.addChild(new SAPLElem("ServiceRepairCenter","FEATURE","SVCREPARCTR"));
        elem3.addChild(new SAPLElem("TimeandMaterials","FEATURE","TMENMATRLS"));
        elem3.addChild(new SAPLElem("Voltage","FEATURE","VOLT"));
        elem3.addChild(new SAPLElem("CustomerSetupAllowanceDays","PRODSTRUCT","CUSTSETUPALLOW",true));

        listElem = new SAPLElem("FeatureDescriptionDataList");

		elem3.addChild(listElem); //TIR79LNMS

        SAPLElem elem4 = new SAPLNLSElem("FeatureDescriptionData");
        // add level4
        elem4.addChild(new SAPLElem("FeatureDescription","FEATURE","MKTGNAME"));
        elem4.addChild(new SAPLCHQISOElem("FeatureDescriptionLanguage"));
        // add level3
        listElem.addChild(elem4);
    }

    /**********************************
    * get the name(s) of the MQ properties file to use, could be more than one
    * There may be a SAPL XML Feed to OIDH (LEDGER) of this data which is determined after
    * the data quality checks (if any) are complete.
Wayne Kehrli
ESH = all but ACCGUSEONLYMATRL
OIDH = all but PRODSTRUCT & SWPRODSTRUCT
    */
    protected Vector getMQPropertiesFN() {
        Vector vct = new Vector(1);
//      vct.add(OIDHMQSERIES);
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

    /**********************************
    * PRODSTRUCTSAPLXML needs to pull 2 VEs.  MODELPROJA->PROJ is not returned when PRODSTRUCT is root
    * TIR72PGQ9
Wendy   i think u can pull things tied to a feature when prodstruct is root..
bnair@us.ibm.c...   right, u can
Wendy   but seems like u cant pull things tied to a model when prodstruct is root
bnair@us.ibm.c...   right, because the entry which seeds the trsnavigate always says root is 'UP' when the root entity is a relator
bnair@us.ibm.c...   so thats a limitation with gbl8000
    */
    protected void mergeLists(EntityList theList) throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        Profile profile = theList.getProfile();
        String VeName = "MODELPROJVE";

        EntityItem origmdlItem = theList.getEntityGroup("MODEL").getEntityItem(0); // has to exist

        // entityitem passed in is adjusted in extract, we don't want that so create new one
        EntityList mdlList = saplAbr.getDB().getEntityList(profile,
                new ExtractActionItem(null, saplAbr.getDB(), profile, VeName),
                new EntityItem[] { new EntityItem(null, profile, "MODEL", origmdlItem.getEntityID()) });
        addDebug("mergeLists:: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));

        EntityItem mdlItem = mdlList.getParentEntityGroup().getEntityItem(0);

        // add all modelproja from new mdllist to first pull
        EntityGroup origmdlprjaGrp = theList.getEntityGroup("MODELPROJA");
        EntityGroup mdlprjaGrp = mdlList.getEntityGroup("MODELPROJA");

        EntityItem eiArray[] = mdlprjaGrp.getEntityItemsAsArray();
        for (int i=0;i<eiArray.length; i++)
        {
            EntityItem mdlprojaItem = eiArray[i];
            // remove assoc from new model item
            mdlprojaItem.removeUpLink(mdlItem);
            // add assoc to orig model item
            mdlprojaItem.putUpLink(origmdlItem);
            // put it in the orig list group
            origmdlprjaGrp.putEntityItem(mdlprojaItem);
            // must move metaattr to new group too
            mdlprojaItem.reassign(origmdlprjaGrp); 
            // remove it from new list
            mdlprjaGrp.removeEntityItem(mdlprojaItem);
        }

        // add all proj from new mdllist to first pull
        EntityGroup origprojGrp = theList.getEntityGroup("PROJ");
        EntityGroup projGrp = mdlList.getEntityGroup("PROJ");
        eiArray = projGrp.getEntityItemsAsArray();
        for (int i=0;i<eiArray.length; i++)
        {
            EntityItem projItem = eiArray[i];
            // put it in the orig list group
            origprojGrp.putEntityItem(projItem);
            // must move metaattr to new group too
            projItem.reassign(origprojGrp); 
            // remove it from new list
            projGrp.removeEntityItem(projItem);
            // remove it from new list
            projGrp.removeEntityItem(projItem);
        }

        // add all modelgeomod from new mdllist to first pull
        origmdlprjaGrp = theList.getEntityGroup("MODELGEOMOD");
        mdlprjaGrp = mdlList.getEntityGroup("MODELGEOMOD");

        eiArray = mdlprjaGrp.getEntityItemsAsArray();
        for (int i=0;i<eiArray.length; i++)
        {
            EntityItem mdlprojaItem = eiArray[i];
            // remove assoc from new model item
            mdlprojaItem.removeUpLink(mdlItem);
            // add assoc to orig model item
            mdlprojaItem.putUpLink(origmdlItem);
            // put it in the orig list group
            origmdlprjaGrp.putEntityItem(mdlprojaItem);
            // must move metaattr to new group too
            mdlprojaItem.reassign(origmdlprjaGrp); 
            // remove it from new list
            mdlprjaGrp.removeEntityItem(mdlprojaItem);
        }

        // add all geomod from new mdllist to first pull
        origprojGrp = theList.getEntityGroup("GEOMOD");
        projGrp = mdlList.getEntityGroup("GEOMOD");
        eiArray = projGrp.getEntityItemsAsArray();
        for (int i=0;i<eiArray.length; i++)
        {
            EntityItem projItem = eiArray[i];
            // put it in the orig list group
            origprojGrp.putEntityItem(projItem);
            // must move metaattr to new group too
            projItem.reassign(origprojGrp); 
            // remove it from new list
            projGrp.removeEntityItem(projItem);
            // remove it from new list
            projGrp.removeEntityItem(projItem);
        }

        // release memory
        mdlList.dereference();

        addDebug("mergeLists:: after merge Extract "+NEWLINE+PokUtils.outputList(theList));
    }

    /**
     * getRevision
     *
     * @return
     * @author Owner
     */
    public String getVersion() {
        return "1.6";
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
        public void addElements(Database dbCurrent,EntityList list, Document document, Element parent,
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
            String value = "esh:MaterialLegacy/Nomenclature/PRODSTRUCT/Country"+ctrys+"/EndCountry";
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
