/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.SAPLCHQISOElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLFixedElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLGEOAnnElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLGEOAvailElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLGEOElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLGEOFilteredElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLIdElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLItemElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLMessageIDElem;
/*     */ import COM.ibm.eannounce.abr.util.SAPLNLSElem;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MODELSAPLXML
/*     */   extends SAPLXMLBase
/*     */ {
/*     */   private static final String SAPVE_NAME = "SAPLVEMODEL";
/* 172 */   private static final Vector SAPLXMLMAP_VCT = new Vector(); static {
/* 173 */     SAPLElem sAPLElem1 = new SAPLElem("wsnt:Notify");
/* 174 */     sAPLElem1.addXMLAttribute("xmlns:wsnt", "http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd");
/* 175 */     sAPLElem1.addXMLAttribute("xmlns:ebi", "http://ibm.com/esh/ebi");
/* 176 */     SAPLXMLMAP_VCT.addElement(sAPLElem1);
/*     */     
/* 178 */     SAPLElem sAPLElem2 = new SAPLElem("wsnt:NotificationMessage");
/* 179 */     sAPLElem1.addChild(sAPLElem2);
/*     */     
/* 181 */     sAPLElem2.addChild(new SAPLTopicElem());
/* 182 */     SAPLElem sAPLElem3 = new SAPLElem("wsnt:Message");
/* 183 */     sAPLElem2.addChild(sAPLElem3);
/*     */     
/* 185 */     sAPLElem3.addChild((SAPLElem)new SAPLMessageIDElem());
/* 186 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("ebi:priority", "Normal"));
/* 187 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("PayloadFormat", "EACM_Material"));
/* 188 */     sAPLElem3.addChild((SAPLElem)new SAPLFixedElem("NativeCodePage", "1208"));
/*     */     
/* 190 */     SAPLElem sAPLElem4 = new SAPLElem("body");
/* 191 */     sAPLElem3.addChild(sAPLElem4);
/*     */     
/* 193 */     SAPLElem sAPLElem5 = new SAPLElem("Material");
/* 194 */     sAPLElem4.addChild(sAPLElem5);
/*     */ 
/*     */     
/* 197 */     sAPLElem5.addChild((SAPLElem)new SAPLFixedElem("EACMEntityType", "MODEL"));
/* 198 */     sAPLElem5.addChild((SAPLElem)new SAPLIdElem("EACMEntityId"));
/* 199 */     sAPLElem5.addChild(new SAPLElem("CableSpecsRequiredFlag", "MODEL", "CBLSPECSREQ", true));
/* 200 */     sAPLElem5.addChild(new SAPLElem("CustomerSetupAllowanceDays", "MODEL", "CUSTSETUPALLOW", true));
/* 201 */     sAPLElem5.addChild(new SAPLElem("Division", "PROJ", "DIV", 1));
/* 202 */     sAPLElem5.addChild(new SAPLElem("EAEligibility", "MODEL", "EAELIG", true));
/* 203 */     sAPLElem5.addChild(new SAPLElem("GSAProductionStatus", "MODEL", "PRODCD", true));
/* 204 */     sAPLElem5.addChild(new SAPLElem("HourlyServiceRate", "MODEL", "HRLYSVCRATECLSCD", true));
/* 205 */     sAPLElem5.addChild(new SAPLElem("IBMCreditCorp", "MODEL", "IBMCREDCORP", true));
/* 206 */     sAPLElem5.addChild(new SAPLElem("LicensedInternalCode", "MODEL", "LICNSINTERCD", true));
/* 207 */     sAPLElem5.addChild(new SAPLElem("LineOfBusiness", "PROJ", "LINEOFBUS"));
/* 208 */     sAPLElem5.addChild(new SAPLElem("LowEndIndicator", "MODEL", "LOWENDFLG", true));
/*     */     
/* 210 */     sAPLElem5.addChild(new SAPLElem("MachineMaintenanceGroupCode", "MODEL", "MACHMAINTGRPCD", true, 1));
/* 211 */     sAPLElem5.addChild(new SAPLElem("MidrangeSystemOption", "MODEL", "MIDRNGESYSOPT", true));
/* 212 */     sAPLElem5.addChild(new SAPLElem("OEMIndicator", "MODEL", "OEMINDC", true));
/* 213 */     sAPLElem5.addChild(new SAPLElem("PlantOfManufacture", "MODEL", "PLNTOFMFR", true, 1));
/* 214 */     sAPLElem5.addChild((SAPLElem)new SAPLGEOFilteredElem("InterplantPlantCode", "GEOMOD", "INTERPLNTCD", "COUNTRYLIST", "1652", 1));
/*     */ 
/*     */ 
/*     */     
/* 218 */     sAPLElem5.addChild(new SAPLElem("ProductTypeCategory", "MODEL", "PRODTYPECATG", true, 1));
/* 219 */     sAPLElem5.addChild(new SAPLElem("ProductID", "MODEL", "MACHTYPEATR|MODELATR", true));
/* 220 */     sAPLElem5.addChild(new SAPLElem("ProductTypeCode", "MODEL", "PRODTYPE", true, 1));
/* 221 */     sAPLElem5.addChild(new SAPLElem("ProfitCenter", "MODEL", "PRFTCTR", true, 1));
/* 222 */     sAPLElem5.addChild(new SAPLElem("SalesManualIndicator", "MODEL", "SLEMANLVIEWABL", true));
/*     */     
/* 224 */     sAPLElem5.addChild(new SAPLElem("StockCategoryCode", "MODEL", "STOCKCATCD", true));
/* 225 */     sAPLElem5.addChild(new SAPLElem("VendorNumber", "MODEL", "VENDID", true));
/* 226 */     sAPLElem5.addChild(new SAPLElem("VolumeDiscount", "MODEL", "VOLDISCNT", true));
/* 227 */     sAPLElem5.addChild(new SAPLElem("ESAServiceLevelCategory", "MODEL", "ESASVCLEVCATG", true));
/* 228 */     sAPLElem5.addChild(new SAPLElem("AutomaticMaintenanceContract", "MODEL", "AUTOMAINTCONTRCT", true));
/* 229 */     sAPLElem5.addChild(new SAPLElem("BulkOrder", "MODEL", "BULKORD", true));
/* 230 */     sAPLElem5.addChild(new SAPLElem("CountryIdleControl", "MODEL", "CNTRYIDLECNTRL", true));
/* 231 */     sAPLElem5.addChild(new SAPLElem("ColorSpecificationIndicator", "MODEL", "COLRSPECINDC", true));
/* 232 */     sAPLElem5.addChild(new SAPLElem("ComputerSystemCompoment", "MODEL", "COMPUTSYSCOMPOMENT", true));
/* 233 */     sAPLElem5.addChild(new SAPLElem("ComputerSystemStandalone", "MODEL", "COMPUTSYSSTNDALONE", true));
/* 234 */     sAPLElem5.addChild(new SAPLElem("CorporateServiceOption", "MODEL", "CORPSVCOPT", true));
/* 235 */     sAPLElem5.addChild(new SAPLElem("DPInstallProgram", "MODEL", "DPINSTPGM", true));
/* 236 */     sAPLElem5.addChild(new SAPLElem("EducationAllowance-HighSchool", "MODEL", "EDUCALLOWMHGHSCH", true));
/* 237 */     sAPLElem5.addChild(new SAPLElem("EducationAllowance-SecondarySchool", "MODEL", "EDUCALLOWMSECONDRYSCH", true));
/* 238 */     sAPLElem5.addChild(new SAPLElem("EducationAllowance-University", "MODEL", "EDUCALLOWMUNVRSTY", true));
/* 239 */     sAPLElem5.addChild(new SAPLElem("ESAOnSite", "MODEL", "ESAONSITE", true));
/* 240 */     sAPLElem5.addChild(new SAPLElem("FrozenZonePeriod-months", "MODEL", "FROZENZONEPRIODMMO", true));
/* 241 */     sAPLElem5.addChild(new SAPLElem("InitialPeriodMaintenance", "MODEL", "INITPRIODMAINT", true));
/* 242 */     sAPLElem5.addChild(new SAPLElem("LowEndFlag", "MODEL", "LOWENDFLG", true));
/* 243 */     sAPLElem5.addChild(new SAPLElem("LPSEffectiveDate", "MODEL", "LPSEFFCTVDATE", true));
/* 244 */     sAPLElem5.addChild(new SAPLElem("MaintenanceGroup", "MODEL", "MAINTGRP", true));
/* 245 */     sAPLElem5.addChild(new SAPLElem("MaintenanceGroup-RentalPlanD", "MODEL", "MAINTGRPMRENTPLAND", true));
/* 246 */     sAPLElem5.addChild(new SAPLElem("MATPass", "MODEL", "MATPASS", true));
/* 247 */     sAPLElem5.addChild(new SAPLElem("MaximumMCSforPPRTTest", "MODEL", "MAXMCSFORPPRTTST", true));
/* 248 */     sAPLElem5.addChild(new SAPLElem("MandatoryMAT", "MODEL", "MNATORYMAT", true));
/* 249 */     sAPLElem5.addChild(new SAPLElem("MOSPEligible", "MODEL", "MOSPELIG", true));
/* 250 */     sAPLElem5.addChild(new SAPLElem("NormalDeliveryTime-months", "MODEL", "NORMALDELIVTMEMMO", true));
/* 251 */     sAPLElem5.addChild(new SAPLElem("NormalMaintenanceBilling-months", "MODEL", "NORMALMAINTBILLMMO", true));
/* 252 */     sAPLElem5.addChild(new SAPLElem("NormalTypeofMaintenace", "MODEL", "NORMALTYPEOFMAINT", true));
/* 253 */     sAPLElem5.addChild(new SAPLElem("OCTFactor-weeks", "MODEL", "OCTFACTRMWK", true));
/* 254 */     sAPLElem5.addChild(new SAPLElem("OrdersRejected", "MODEL", "ORDREJCTED", true));
/* 255 */     sAPLElem5.addChild(new SAPLElem("PerCallClass", "MODEL", "PERCALLCLS", true));
/* 256 */     sAPLElem5.addChild(new SAPLElem("PostOrderReview", "MODEL", "POSTORDREVU", true));
/* 257 */     sAPLElem5.addChild(new SAPLElem("PPTPAgreementPeriod", "MODEL", "PPTPAGRMTPRIOD", true));
/* 258 */     sAPLElem5.addChild(new SAPLElem("PPTPEligibility", "MODEL", "PPTPELIG", true));
/* 259 */     sAPLElem5.addChild(new SAPLElem("PPTPTestPeriod", "MODEL", "PPTPTSTPRIOD", true));
/* 260 */     sAPLElem5.addChild(new SAPLElem("PrimarySource-50hz", "MODEL", "PRIMSRCM50HZ", true));
/* 261 */     sAPLElem5.addChild(new SAPLElem("PrimarySource-60hz", "MODEL", "PRIMSRCM60HZ", true));
/* 262 */     sAPLElem5.addChild(new SAPLElem("ProductionAllotment", "MODEL", "PRODALLOTMENT", true));
/* 263 */     sAPLElem5.addChild(new SAPLElem("ProductIdentification", "MODEL", "PRODID", true));
/* 264 */     sAPLElem5.addChild(new SAPLElem("PurchaseOnly", "MODEL", "PURCHONLY", true));
/* 265 */     sAPLElem5.addChild(new SAPLElem("RentalPlanType", "MODEL", "RENTPLANTYPE", true));
/* 266 */     sAPLElem5.addChild(new SAPLElem("ReturnedPartsMES", "MODEL", "RETURNEDPARTS", true));
/* 267 */     sAPLElem5.addChild(new SAPLElem("RMSEligible", "MODEL", "RMSELIG", true));
/* 268 */     sAPLElem5.addChild(new SAPLElem("RPQmachine", "MODEL", "RPQMACH", true));
/* 269 */     sAPLElem5.addChild(new SAPLElem("SalesManualViewable", "MODEL", "SLEMANLVIEWABL", true));
/* 270 */     sAPLElem5.addChild(new SAPLElem("SSTPEligibility", "MODEL", "SSTPELIG", true));
/* 271 */     sAPLElem5.addChild(new SAPLElem("SystemRentalPlanCharge", "MODEL", "SYSRENTPLANCHRG", true));
/* 272 */     sAPLElem5.addChild(new SAPLElem("TAPEligibility", "MODEL", "TAPELIG", true));
/* 273 */     sAPLElem5.addChild(new SAPLElem("TLPEligibility", "MODEL", "TLPELIG", true));
/* 274 */     sAPLElem5.addChild(new SAPLElem("TimeandMaterials", "MODEL", "TMENMATRLS", true));
/* 275 */     sAPLElem5.addChild(new SAPLElem("TradeinMachine", "MODEL", "TRDINMACH", true));
/* 276 */     sAPLElem5.addChild(new SAPLElem("Voltage", "MODEL", "VOLT", true));
/*     */     
/* 278 */     SAPLNLSElem sAPLNLSElem = new SAPLNLSElem("DescriptionData");
/*     */     
/* 280 */     sAPLNLSElem.addChild(new SAPLElem("MaterialDescription", "MODEL", "MKTGNAME", true));
/* 281 */     sAPLNLSElem.addChild((SAPLElem)new SAPLCHQISOElem("DescriptionLanguage"));
/* 282 */     SAPLElem sAPLElem6 = new SAPLElem("DescriptionDataList");
/*     */     
/* 284 */     sAPLElem6.addChild((SAPLElem)sAPLNLSElem);
/*     */     
/* 286 */     sAPLElem5.addChild(sAPLElem6);
/*     */     
/* 288 */     sAPLElem6 = new SAPLElem("GeographyList");
/* 289 */     sAPLElem5.addChild(sAPLElem6);
/*     */     
/* 291 */     SAPLGEOElem sAPLGEOElem = new SAPLGEOElem("Geography", "AVAIL", "AVAILGAA");
/* 292 */     sAPLElem6.addChild((SAPLElem)sAPLGEOElem);
/* 293 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("RFAGEO", "GENERALAREA", "RFAGEO"));
/* 294 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("Country", "GENERALAREA", "GENAREANAME"));
/* 295 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("SalesOrg", "GENERALAREA", "SLEORG"));
/* 296 */     sAPLGEOElem.addChild((SAPLElem)new SAPLItemElem("SalesOffice", "GENERALAREA", "SLEOFFC"));
/* 297 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAvailElem("AASOrderIndicator", "ORDERSYSNAME"));
/* 298 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAvailElem("AASViewableIndicator", "AASVIEWABL"));
/* 299 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOFilteredElem("LeaseRentalWithrawalDate", "AVAIL", "EFFECTIVEDATE", "AVAILTYPE", "AVT220"));
/*     */     
/* 301 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAnnElem("PackageName", "ANNNUMBER"));
/* 302 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAnnElem("AnnouncementType", "ANNTYPE"));
/* 303 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOAnnElem("ProductAnnounceDateCountry", "ANNDATE"));
/* 304 */     sAPLGEOElem.addChild((SAPLElem)new SAPLGEOFilteredElem("ProductWithdrawalDate", "AVAIL", "EFFECTIVEDATE", "AVAILTYPE", "149"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector getMQPropertiesFN() {
/* 315 */     Vector<String> vector = new Vector(1);
/* 316 */     vector.add("OIDHMQSERIES");
/* 317 */     vector.add("ESHMQSERIES");
/* 318 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getSaplVeName() {
/* 324 */     return "SAPLVEMODEL";
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector getSAPLXMLMap() {
/* 329 */     return SAPLXMLMAP_VCT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 338 */     return "1.3";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class SAPLTopicElem
/*     */     extends SAPLElem
/*     */   {
/*     */     SAPLTopicElem() {
/* 356 */       super("wsnt:Topic", null, null, false);
/* 357 */       addXMLAttribute("Dialect", "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addElements(Database param1Database, EntityList param1EntityList, Document param1Document, Element param1Element, StringBuffer param1StringBuffer) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 380 */       Element element = param1Document.createElement(this.nodeName);
/* 381 */       addXMLAttrs(element);
/*     */ 
/*     */       
/* 384 */       Vector vector = SAPLGEOElem.getAvailEntities(param1EntityList.getEntityGroup("AVAIL"));
/* 385 */       String str1 = getCountryCodes(param1EntityList, vector, "AVAILGAA", param1StringBuffer);
/*     */       
/* 387 */       String str2 = "esh:MaterialLegacy/Nomenclature/MODEL/Country" + str1 + "/EndCountry";
/* 388 */       element.appendChild(param1Document.createTextNode(str2));
/* 389 */       param1Element.appendChild(element);
/*     */ 
/*     */       
/* 392 */       for (byte b = 0; b < this.childVct.size(); b++) {
/* 393 */         SAPLElem sAPLElem = this.childVct.elementAt(b);
/* 394 */         sAPLElem.addElements(param1Database, param1EntityList, param1Document, element, param1StringBuffer);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\MODELSAPLXML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */