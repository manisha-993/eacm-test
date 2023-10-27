/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLDistinctGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMultiFlagElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNLSElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLSLEORGGRPElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLSVCMODAVAILElembh1;
/*     */ import COM.ibm.eannounce.abr.util.XMLSVCMODCVMSPECElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLSVCMODPRCPTElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLSVCSEOAVAILElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLStatusElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLTAXElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLVMElem;
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
/*     */ public class ADSSVCMODABR
/*     */   extends XMLMQRoot
/*     */ {
/* 263 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SVCMOD_UPDATE"); static {
/* 264 */     XMLMAP.addChild((XMLElem)new XMLVMElem("SVCMOD_UPDATE", "1"));
/*     */     
/* 266 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 267 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 268 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 269 */     XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/* 270 */     XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/* 271 */     XMLMAP.addChild(new XMLElem("MACHTYPE", "SMACHTYPEATR"));
/* 272 */     XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */     
/* 274 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 275 */     XMLMAP.addChild(new XMLElem("CATEGORY", "SVCMODCATG"));
/* 276 */     XMLMAP.addChild(new XMLElem("SUBCATEGORY", "SVCMODSUBCATG"));
/* 277 */     XMLMAP.addChild(new XMLElem("GROUP", "SVCMODGRP"));
/* 278 */     XMLMAP.addChild(new XMLElem("SUBGROUP", "SVCMODSUBGRP"));
/*     */     
/* 280 */     XMLMAP.addChild(new XMLElem("PROJECT", "PROJCDNAM", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     XMLDistinctGroupElem xMLDistinctGroupElem = new XMLDistinctGroupElem(null, "SGMNTACRNYM", "D:SVCMODSGMTACRONYMA:D", true, true);
/* 288 */     XMLMAP.addChild((XMLElem)xMLDistinctGroupElem);
/*     */     
/* 290 */     xMLDistinctGroupElem.addChild(new XMLElem("DIVISION", "DIV", 1));
/*     */ 
/*     */     
/* 293 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     XMLMAP.addChild(new XMLElem("PRODHIERCD", "BHPRODHIERCD"));
/*     */     
/* 300 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */ 
/*     */     
/* 303 */     XMLMAP.addChild(new XMLElem("NEC", "NEC"));
/*     */     
/* 305 */     XMLMAP.addChild(new XMLElem("TOS", "TOS"));
/* 306 */     XMLMAP.addChild(new XMLElem("SVCFFTYPE", "SVCFFTYPE"));
/*     */     
/* 308 */     XMLMAP.addChild(new XMLElem("OFERCONFIGTYPE", "OFERCONFIGTYPE"));
/* 309 */     XMLMAP.addChild(new XMLElem("ENDCUSTIDREQ", "ENDCUSTIDREQ"));
/* 310 */     XMLMAP.addChild(new XMLElem("FIXTERMPRIOD", "FIXTERMPRIOD"));
/* 311 */     XMLMAP.addChild(new XMLElem("PRORATEDOTCALLOW", "PRORATEDOTCALLOW"));
/* 312 */     XMLMAP.addChild(new XMLElem("SNGLLNEITEM", "SNGLLNEITEM"));
/* 313 */     XMLMAP.addChild(new XMLElem("SVCCHRGOPT", "SVCCHRGOPT", 2));
/* 314 */     XMLMAP.addChild(new XMLElem("TYPEOFWRK", "TYPEOFWRK"));
/* 315 */     XMLMAP.addChild(new XMLElem("UOMSI", "UOMSI", 2));
/* 316 */     XMLMAP.addChild(new XMLElem("UPGRADEYN", "UPGRADEYN"));
/* 317 */     XMLMAP.addChild(new XMLElem("WWOCCODE", "WWOCCODE"));
/*     */ 
/*     */ 
/*     */     
/* 321 */     XMLMAP.addChild(new XMLElem("UNSPSC", "UNSPSCCD"));
/* 322 */     XMLMAP.addChild(new XMLElem("UNUOM", "UNSPSCCDUOM"));
/*     */     
/* 324 */     XMLMAP.addChild(new XMLElem("PCTOFCMPLTINDC", "PCTOFCMPLTINDC"));
/*     */ 
/*     */     
/* 327 */     XMLMAP.addChild(new XMLElem("SOPRELEVANT", "SOPRELEVANT"));
/* 328 */     XMLMAP.addChild(new XMLElem("SOPTASKTYPE", "SOPTASKTYPE"));
/*     */     
/* 330 */     XMLMAP.addChild(new XMLElem("ALTID", "ALTID"));
/*     */ 
/*     */     
/* 333 */     XMLElem xMLElem8 = new XMLElem("LANGUAGELIST");
/* 334 */     XMLMAP.addChild(xMLElem8);
/*     */     
/* 336 */     XMLNLSElem xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 337 */     xMLElem8.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 339 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 341 */     xMLNLSElem2.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 342 */     xMLNLSElem2.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 348 */     xMLElem8 = new XMLElem("AVAILABILITYLIST");
/* 349 */     XMLMAP.addChild(xMLElem8);
/*     */     
/* 351 */     xMLElem8.addChild((XMLElem)new XMLSVCMODAVAILElembh1());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 359 */     XMLGroupElem xMLGroupElem9 = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
/* 360 */     XMLMAP.addChild((XMLElem)xMLGroupElem9);
/*     */     
/* 362 */     XMLElem xMLElem9 = new XMLElem("TAXCATEGORYELEMENT");
/* 363 */     xMLGroupElem9.addChild(xMLElem9);
/*     */     
/* 365 */     xMLElem9.addChild((XMLElem)new XMLActivityElem("TAXCATEGORYACTION"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     XMLElem xMLElem7 = new XMLElem("COUNTRYLIST");
/* 376 */     xMLElem9.addChild(xMLElem7);
/*     */     
/* 378 */     XMLChgSetElem xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 379 */     xMLElem7.addChild((XMLElem)xMLChgSetElem1);
/*     */ 
/*     */     
/* 382 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "TAXCNTRY", "COUNTRYACTION", 1));
/*     */     
/* 384 */     xMLElem9.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 393 */     XMLGroupElem xMLGroupElem10 = new XMLGroupElem(null, "SVCMODTAXRELEVANCE", "U:SVCMODTAXRELEVANCE");
/* 394 */     xMLElem9.addChild((XMLElem)xMLGroupElem10);
/* 395 */     xMLGroupElem10.addChild((XMLElem)new XMLTAXElem("TAXCLASSIFICATION", "TAXCLS", 2));
/*     */     
/* 397 */     xMLElem9.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXCATGSLEORGA:D"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 403 */     XMLGroupElem xMLGroupElem8 = new XMLGroupElem("TAXCODELIST", "TAXGRP");
/* 404 */     XMLMAP.addChild((XMLElem)xMLGroupElem8);
/*     */     
/* 406 */     XMLElem xMLElem10 = new XMLElem("TAXCODEELEMENT");
/* 407 */     xMLGroupElem8.addChild(xMLElem10);
/*     */     
/* 409 */     xMLElem10.addChild((XMLElem)new XMLActivityElem("TAXCODEACTION"));
/* 410 */     xMLElem10.addChild(new XMLElem("TAXCODEDESCRIPTION", "DESC"));
/*     */ 
/*     */ 
/*     */     
/* 414 */     XMLElem xMLElem6 = new XMLElem("COUNTRYLIST");
/* 415 */     xMLElem10.addChild(xMLElem6);
/*     */     
/* 417 */     XMLChgSetElem xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 418 */     xMLElem6.addChild((XMLElem)xMLChgSetElem2);
/*     */     
/* 420 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */     
/* 422 */     xMLElem10.addChild(new XMLElem("TAXCODE", "TAXCD"));
/*     */     
/* 424 */     xMLElem10.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXGRPSLEORGA:D"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 429 */     XMLGroupElem xMLGroupElem7 = new XMLGroupElem("CATATTRIBUTELIST", "CATDATA");
/* 430 */     XMLMAP.addChild((XMLElem)xMLGroupElem7);
/*     */     
/* 432 */     XMLNLSElem xMLNLSElem3 = new XMLNLSElem("CATATTRIBUTEELEMENT");
/* 433 */     xMLGroupElem7.addChild((XMLElem)xMLNLSElem3);
/*     */     
/* 435 */     xMLNLSElem3.addChild((XMLElem)new XMLActivityElem("CATATTRIBUTEACTION"));
/*     */     
/* 437 */     xMLNLSElem3.addChild(new XMLElem("CATATTRIBUTE", "DAATTRIBUTECODE"));
/* 438 */     xMLNLSElem3.addChild(new XMLElem("NLSID", "NLSID"));
/* 439 */     xMLNLSElem3.addChild(new XMLElem("CATATTRIUBTEVALUE", "DAATTRIBUTEVALUE"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 444 */     xMLGroupElem7 = new XMLGroupElem("UNBUNDCOMPLIST", "REVUNBUNDCOMP");
/* 445 */     XMLMAP.addChild((XMLElem)xMLGroupElem7);
/*     */     
/* 447 */     XMLElem xMLElem11 = new XMLElem("UNBUNDCOMPELEMENT");
/* 448 */     xMLGroupElem7.addChild(xMLElem11);
/*     */     
/* 450 */     xMLElem11.addChild((XMLElem)new XMLActivityElem("UNBUNDCOMPACTION"));
/*     */     
/* 452 */     xMLElem11.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 453 */     xMLElem11.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 454 */     xMLElem11.addChild(new XMLElem("UNBUNDCOMPID", "UNBUNDCOMPID"));
/*     */ 
/*     */ 
/*     */     
/* 458 */     XMLGroupElem xMLGroupElem11 = new XMLGroupElem(null, "SVCMODUNBUND", "U:SVCMODUNBUND");
/* 459 */     xMLElem11.addChild((XMLElem)xMLGroupElem11);
/* 460 */     xMLGroupElem11.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 461 */     xMLGroupElem11.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/* 462 */     xMLGroupElem11.addChild(new XMLElem("UNBUNDTYPE", "UNBUNDTYPE"));
/*     */ 
/*     */ 
/*     */     
/* 466 */     xMLGroupElem7 = new XMLGroupElem("CHRGCOMPLIST", "CHRGCOMP", "D:SVCMODCHRGCOMP:D");
/* 467 */     XMLMAP.addChild((XMLElem)xMLGroupElem7);
/*     */     
/* 469 */     XMLElem xMLElem12 = new XMLElem("CHRGCOMPELEMENT");
/* 470 */     xMLGroupElem7.addChild(xMLElem12);
/*     */ 
/*     */ 
/*     */     
/* 474 */     xMLElem12.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 475 */     xMLElem12.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 476 */     xMLElem12.addChild(new XMLElem("CHRGCOMPID", "CHRGCOMPID"));
/* 477 */     xMLElem12.addChild(new XMLElem("BHACCTASGNGRP", "BHACCTASGNGRP", 2));
/* 478 */     xMLElem12.addChild(new XMLElem("REFSELCONDTN", "REFSELCONDTN"));
/* 479 */     xMLElem12.addChild(new XMLElem("SVCCHRGOPT", "SVCCHRGOPT", 2));
/*     */     
/* 481 */     xMLElem12.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 482 */     xMLElem12.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/*     */ 
/*     */     
/* 485 */     XMLElem xMLElem5 = new XMLElem("LANGUAGELIST");
/* 486 */     xMLElem12.addChild(xMLElem5);
/*     */ 
/*     */     
/* 489 */     xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 490 */     xMLElem5.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 492 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 493 */     xMLNLSElem2.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 494 */     xMLNLSElem2.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */     
/* 497 */     XMLGroupElem xMLGroupElem6 = new XMLGroupElem("PRICEPOINTLIST", "PRCPT", "D:CHRGCOMPPRCPT:D", true);
/* 498 */     xMLElem12.addChild((XMLElem)xMLGroupElem6);
/*     */     
/* 500 */     XMLElem xMLElem13 = new XMLElem("PRICEPOINTELEMENT");
/* 501 */     xMLGroupElem6.addChild(xMLElem13);
/*     */ 
/*     */ 
/*     */     
/* 505 */     xMLElem13.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 506 */     xMLElem13.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 507 */     xMLElem13.addChild(new XMLElem("PRCPTID", "PRCPTID"));
/* 508 */     xMLElem13.addChild(new XMLElem("PRCMETH", "PRCMETH", 2));
/*     */     
/* 510 */     XMLElem xMLElem4 = new XMLElem("LANGUAGELIST");
/* 511 */     xMLElem13.addChild(xMLElem4);
/*     */ 
/*     */     
/* 514 */     xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 515 */     xMLElem4.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 517 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 518 */     xMLNLSElem2.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 519 */     xMLNLSElem2.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
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
/* 533 */     XMLGroupElem xMLGroupElem5 = new XMLGroupElem("CNTRYEFFLIST", "CNTRYEFF", "D:PRCPTCNTRYEFF:D");
/* 534 */     xMLElem13.addChild((XMLElem)xMLGroupElem5);
/* 535 */     XMLElem xMLElem14 = new XMLElem("CNTRYEFFELEMENT");
/* 536 */     xMLGroupElem5.addChild(xMLElem14);
/* 537 */     xMLElem14.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 538 */     xMLElem14.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/*     */ 
/*     */     
/* 541 */     xMLElem14.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 542 */     xMLElem14.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */ 
/*     */     
/* 545 */     XMLElem xMLElem3 = new XMLElem("COUNTRYLISTLIST");
/* 546 */     xMLElem14.addChild(xMLElem3);
/*     */     
/* 548 */     XMLChgSetElem xMLChgSetElem3 = new XMLChgSetElem("COUNTRYLISTELEMENT");
/* 549 */     xMLElem3.addChild((XMLElem)xMLChgSetElem3);
/*     */     
/* 551 */     xMLChgSetElem3.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 555 */     XMLGroupElem xMLGroupElem4 = new XMLGroupElem("REFCVMSPECLIST", "CVMSPEC", "D:PRCPTCVMSPEC:D", true);
/* 556 */     xMLElem13.addChild((XMLElem)xMLGroupElem4);
/* 557 */     XMLElem xMLElem15 = new XMLElem("REFCVMSPECELEMENT");
/* 558 */     xMLGroupElem4.addChild(xMLElem15);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 564 */     XMLGroupElem xMLGroupElem12 = new XMLGroupElem(null, "PRCPTCVMSPEC", "U:PRCPTCVMSPEC", false, 2);
/* 565 */     xMLElem15.addChild((XMLElem)xMLGroupElem12);
/* 566 */     xMLGroupElem12.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 567 */     xMLGroupElem12.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/* 568 */     xMLElem15.addChild(new XMLElem("REFCVMSPECENTITYTYPE", "ENTITYTYPE"));
/* 569 */     xMLElem15.addChild(new XMLElem("REFCVMSPECENTITYID", "ENTITYID"));
/*     */     
/* 571 */     xMLElem15.addChild(new XMLElem("REFCVMSPECTYPE", "SPECTYPE"));
/*     */ 
/*     */     
/* 574 */     xMLElem15.addChild((XMLElem)new XMLSVCMODCVMSPECElem("REFCVMSPECID"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 581 */     xMLGroupElem4 = new XMLGroupElem("CHARVALUEMETRICLIST", "CVM", "D:CHRGCOMPCVM:D");
/* 582 */     xMLElem12.addChild((XMLElem)xMLGroupElem4);
/*     */     
/* 584 */     XMLElem xMLElem16 = new XMLElem("CHARVALUEMETRICELEMENT");
/* 585 */     xMLGroupElem4.addChild(xMLElem16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 595 */     xMLElem16.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 596 */     xMLElem16.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */     
/* 598 */     xMLElem16.addChild(new XMLElem("CVMTYPE", "CVMTYPE"));
/* 599 */     xMLElem16.addChild(new XMLElem("VMID", "VMID"));
/*     */ 
/*     */ 
/*     */     
/* 603 */     xMLElem16.addChild(new XMLElem("CHARACID", "CHARACID"));
/*     */     
/* 605 */     xMLElem16.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 606 */     xMLElem16.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/* 607 */     XMLElem xMLElem2 = new XMLElem("LANGUAGELIST");
/* 608 */     xMLElem16.addChild(xMLElem2);
/*     */ 
/*     */     
/* 611 */     xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 612 */     xMLElem2.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 614 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 615 */     xMLNLSElem2.addChild(new XMLElem("SHRTNAM", "SHRTNAM"));
/*     */     
/* 617 */     xMLElem16.addChild(new XMLElem("CVMDATATYPE", "CVMDATATYPE", 2));
/* 618 */     xMLElem16.addChild(new XMLElem("CVMSELTYPE", "CVMSELTYPE", 2));
/*     */     
/* 620 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem("CVMSPECLIST", "CVMSPEC", "D:CVMCVMSPEC:D");
/* 621 */     xMLElem16.addChild((XMLElem)xMLGroupElem3);
/*     */     
/* 623 */     XMLElem xMLElem17 = new XMLElem("CVMSPECELEMENT");
/*     */     
/* 625 */     xMLGroupElem3.addChild(xMLElem17);
/*     */ 
/*     */ 
/*     */     
/* 629 */     xMLElem17.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 630 */     xMLElem17.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */ 
/*     */ 
/*     */     
/* 634 */     xMLElem17.addChild((XMLElem)new XMLSVCMODCVMSPECElem("CVMSPECID", "CVMTYPE", "CVM", "U:CVMCVMSPEC:U"));
/*     */     
/* 636 */     xMLElem17.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 637 */     xMLElem17.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/*     */ 
/*     */     
/* 640 */     XMLNLSElem xMLNLSElem1 = new XMLNLSElem("LANGUAGELIST");
/* 641 */     xMLElem17.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 643 */     xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 644 */     xMLNLSElem1.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 646 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 647 */     xMLNLSElem2.addChild(new XMLElem("SHRTNAM", "SHRTNAM"));
/*     */ 
/*     */     
/* 650 */     xMLElem17.addChild(new XMLElem("LOWLIMT", "LOWLIMT"));
/*     */     
/* 652 */     xMLElem17.addChild(new XMLElem("HIGHLIMT", "HGHLIMT"));
/* 653 */     xMLElem17.addChild(new XMLElem("SPECTYPE", "SPECTYPE", 2));
/* 654 */     xMLElem17.addChild(new XMLElem("RNGETBLOPT", "RNGETBLOPT", 2));
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
/* 671 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem("SVCEXECTMELIST", "SVCEXECTME");
/* 672 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/*     */     
/* 674 */     XMLElem xMLElem18 = new XMLElem("SVCEXECTMEELEMENT");
/* 675 */     xMLGroupElem2.addChild(xMLElem18);
/*     */ 
/*     */ 
/*     */     
/* 679 */     xMLElem18.addChild(new XMLElem("COMPETENCECD", "COMPETENCECD", 2));
/* 680 */     xMLElem18.addChild(new XMLElem("HR", "HR"));
/* 681 */     xMLElem18.addChild(new XMLElem("IMPLEMENTRBN", "IMPLEMENTRBN", 2));
/* 682 */     xMLElem18.addChild(new XMLElem("MACHTYPEATR", "SMACHTYPEATR"));
/* 683 */     xMLElem18.addChild(new XMLElem("MODELATR", "MODELATR"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 688 */     xMLGroupElem2 = new XMLGroupElem("SVCSEOREFLIST", "SVCSEO", "D:SVCMODSVCSEOREF:D", true);
/* 689 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/*     */     
/* 691 */     XMLElem xMLElem19 = new XMLElem("SVCSEOREFELEMENT");
/* 692 */     xMLGroupElem2.addChild(xMLElem19);
/*     */ 
/*     */ 
/*     */     
/* 696 */     xMLElem19.addChild(new XMLElem("SVCSEOENTITYTYPE", "ENTITYTYPE"));
/* 697 */     xMLElem19.addChild(new XMLElem("SVCSEOENTITYID", "ENTITYID"));
/* 698 */     xMLElem19.addChild(new XMLElem("SEOID", "SEOID"));
/*     */ 
/*     */ 
/*     */     
/* 702 */     xMLGroupElem2 = new XMLGroupElem("SVCMODREFLIST", "SVCMOD", "D:SVCMODSVCMOD:D");
/* 703 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/*     */     
/* 705 */     XMLElem xMLElem20 = new XMLElem("SVCMODREFELEMENT");
/* 706 */     xMLGroupElem2.addChild(xMLElem20);
/*     */ 
/*     */ 
/*     */     
/* 710 */     xMLElem20.addChild(new XMLElem("SVCMODEENTITYTYPE", "ENTITYTYPE"));
/* 711 */     xMLElem20.addChild(new XMLElem("SVCMODENTITYID", "ENTITYID"));
/* 712 */     xMLElem20.addChild(new XMLElem("MACHTYPEATR", "SMACHTYPEATR"));
/* 713 */     xMLElem20.addChild(new XMLElem("MODELATR", "MODELATR"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 719 */     xMLGroupElem2 = new XMLGroupElem("SVCSEOLIST", "SVCSEO", "D:SVCMODSVCSEO:D", true);
/* 720 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/*     */     
/* 722 */     XMLElem xMLElem21 = new XMLElem("SVCSEOELEMENT");
/* 723 */     xMLGroupElem2.addChild(xMLElem21);
/*     */ 
/*     */ 
/*     */     
/* 727 */     xMLElem21.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 728 */     xMLElem21.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 729 */     xMLElem21.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 730 */     xMLElem21.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 731 */     xMLElem21.addChild(new XMLElem("SEOID", "SEOID"));
/*     */     
/* 733 */     xMLElem21.addChild(new XMLElem("BHPRDHIERCD", "BHPRODHIERCD"));
/* 734 */     xMLElem21.addChild(new XMLElem("PRFTCTR", "PRFTCTR"));
/* 735 */     xMLElem21.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */ 
/*     */     
/* 738 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 739 */     xMLElem21.addChild(xMLElem1);
/*     */ 
/*     */     
/* 742 */     xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 743 */     xMLElem1.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 745 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 746 */     xMLNLSElem2.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 747 */     xMLNLSElem2.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */     
/* 750 */     xMLElem1 = new XMLElem("AVAILABILITYLIST");
/* 751 */     xMLElem21.addChild(xMLElem1);
/*     */     
/* 753 */     xMLElem1.addChild((XMLElem)new XMLSVCSEOAVAILElem());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 758 */     xMLElem1 = new XMLElem("PRCPTLIST");
/* 759 */     xMLElem21.addChild(xMLElem1);
/* 760 */     xMLElem1.addChild((XMLElem)new XMLSVCMODPRCPTElem());
/*     */ 
/*     */ 
/*     */     
/* 764 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem("PRODSTRUCTLIST", "SVCPRODSTRUCT");
/* 765 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 767 */     XMLElem xMLElem22 = new XMLElem("PRODSTRUCTELEMENT");
/* 768 */     xMLGroupElem1.addChild(xMLElem22);
/*     */     
/* 770 */     xMLElem22.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 771 */     xMLElem22.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 772 */     xMLElem22.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 773 */     xMLElem22.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 774 */     xMLElem22.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/* 775 */     xMLElem22.addChild(new XMLElem("MNATORYOPT", "MNATORYOPT", 1));
/* 776 */     xMLElem22.addChild(new XMLElem("OWNERID", "OWNERID"));
/*     */     
/* 778 */     xMLElem22.addChild(new XMLElem("WITHDRAWDATE", "WITHDRAWDATE"));
/* 779 */     xMLElem22.addChild(new XMLElem("WTHDRWEFFCTVDATE", "WTHDRWEFFCTVDATE"));
/*     */     
/* 781 */     XMLGroupElem xMLGroupElem13 = new XMLGroupElem(null, "SVCFEATURE", "U:SVCFEATURE");
/* 782 */     xMLElem22.addChild((XMLElem)xMLGroupElem13);
/* 783 */     xMLGroupElem13.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/* 784 */     xMLGroupElem13.addChild(new XMLElem("FCMKTGSHRTDESC", "FCMKTGSHRTDESC"));
/* 785 */     xMLGroupElem13.addChild(new XMLElem("SVCCAT", "SVCFCCAT"));
/* 786 */     xMLGroupElem13.addChild(new XMLElem("SVCFCSUBCAT", "SVCFCSUBCAT"));
/* 787 */     xMLGroupElem13.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 788 */     xMLGroupElem13.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 797 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 803 */     return "ADSSVCMOD";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 808 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 814 */     return "SVCMOD_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 823 */     return "$Revision: 1.52 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSSVCMODABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */