/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLAVAILElembh1;
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCATAElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLDistinctGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMachtypeElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMultiFlagElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNLSElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLSLEORGGRPElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLStatusElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLVMElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLZCONFElem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSMODELABR
/*     */   extends XMLMQRoot
/*     */ {
/* 329 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODEL_UPDATE"); static {
/* 330 */     XMLMAP.addChild((XMLElem)new XMLVMElem("MODEL_UPDATE", "1"));
/*     */     
/* 332 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 333 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 334 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 335 */     XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/* 336 */     XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/* 337 */     XMLMAP.addChild((XMLElem)new XMLMachtypeElem("MACHTYPE", "MACHTYPEATR"));
/* 338 */     XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */     
/* 340 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 341 */     XMLMAP.addChild(new XMLElem("CATEGORY", "COFCAT"));
/*     */     
/* 343 */     XMLMAP.addChild(new XMLElem("SUBCATEGORY", "COFSUBCAT", 2));
/* 344 */     XMLMAP.addChild(new XMLElem("GROUP", "COFGRP"));
/* 345 */     XMLMAP.addChild(new XMLElem("SUBGROUP", "COFSUBGRP"));
/* 346 */     XMLMAP.addChild(new XMLElem("APPLICATIONTYPE", "APPLICATIONTYPE"));
/* 347 */     XMLMAP.addChild(new XMLElem("ORDERCODE", "MODELORDERCODE", 2));
/* 348 */     XMLMAP.addChild(new XMLElem("SARINDC", "SARINDC"));
/* 349 */     XMLMAP.addChild(new XMLElem("PROJECT", "PROJCDNAM", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     XMLDistinctGroupElem xMLDistinctGroupElem1 = new XMLDistinctGroupElem(null, "PROJ", "D:MODELPROJA:D", true, true);
/* 355 */     XMLMAP.addChild((XMLElem)xMLDistinctGroupElem1);
/* 356 */     xMLDistinctGroupElem1.addChild(new XMLElem("DIVISION", "DIV", 1));
/*     */     
/* 358 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     XMLMAP.addChild(new XMLElem("RATECARD", "RATECARDCD", 1));
/* 366 */     XMLMAP.addChild(new XMLElem("UNITCLASS", "SYSIDUNIT"));
/* 367 */     XMLMAP.addChild(new XMLElem("PRICEDIND", "PRCINDC"));
/* 368 */     XMLMAP.addChild(new XMLElem("INSTALL", "INSTALL"));
/* 369 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/* 370 */     XMLMAP.addChild(new XMLElem("UNSPSC", "UNSPSCCD", 1));
/* 371 */     XMLMAP.addChild(new XMLElem("UNSPSCSECONDARY", "UNSPSCCDSECONDRY", 1));
/* 372 */     XMLMAP.addChild(new XMLElem("UNUOM", "UNSPSCCDUOM"));
/*     */ 
/*     */     
/* 375 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "WEIGHTNDIMN", "D:MODELWEIGHTNDIMN:D");
/* 376 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/* 377 */     xMLGroupElem1.addChild(new XMLElem("MEASUREMETRIC", "WGHTMTRIC|WGHTMTRICUNIT"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     XMLMAP.addChild(new XMLElem("WARRSVCCOVR", "WARRSVCCOVR", 2));
/*     */     
/* 386 */     XMLMAP.addChild(new XMLElem("PRODHIERCD", "BHPRODHIERCD"));
/*     */ 
/*     */     
/* 389 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */ 
/*     */     
/* 392 */     XMLMAP.addChild(new XMLElem("AMRTZTNLNGTH", "AMRTZTNLNGTH"));
/*     */ 
/*     */     
/* 395 */     XMLMAP.addChild(new XMLElem("AMRTZTNSTRT", "AMRTZTNSTRT"));
/*     */ 
/*     */     
/* 398 */     XMLMAP.addChild(new XMLElem("PRODID", "PRODID"));
/*     */ 
/*     */     
/* 401 */     XMLMAP.addChild(new XMLElem("SWROYALBEARING", "SWROYALBEARING"));
/*     */ 
/*     */     
/* 404 */     XMLMAP.addChild(new XMLElem("SOMFAMILY", "SOMFMLY", 1));
/*     */ 
/*     */     
/* 407 */     XMLMAP.addChild(new XMLElem("LIC", "LICNSINTERCD"));
/*     */ 
/*     */     
/* 410 */     XMLMAP.addChild(new XMLElem("BPCERTSPECBID", "BPSPECBIDCERTREQ"));
/*     */ 
/*     */     
/* 413 */     XMLMAP.addChild(new XMLElem("WWOCCODE", "WWOCCODE"));
/*     */ 
/*     */ 
/*     */     
/* 417 */     XMLMAP.addChild(new XMLElem("SPECBID", "SPECBID"));
/* 418 */     XMLMAP.addChild(new XMLElem("PRPQAPPRVTYPE", "PRPQAPPRVTYPE"));
/*     */ 
/*     */ 
/*     */     
/* 422 */     XMLMAP.addChild(new XMLElem("DUALPIPE", "DUALPIPE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 429 */     XMLMAP.addChild(new XMLElem("SVCLEVCD", "SVCLEVCD"));
/* 430 */     XMLMAP.addChild(new XMLElem("ENDCUSTIDREQ", "ENDCUSTIDREQ"));
/* 431 */     XMLMAP.addChild(new XMLElem("ENTITLMENTSCOPE", "ENTITLMENTSCOPE", 2));
/* 432 */     XMLMAP.addChild(new XMLElem("PRORATEDOTCALLOW", "PRORATEDOTCALLOW"));
/* 433 */     XMLMAP.addChild(new XMLElem("SVCGOODSENTITL", "SVCGOODSENTITL", 2));
/* 434 */     XMLMAP.addChild(new XMLElem("TYPEOFWRK", "TYPEOFWRK", 2));
/*     */ 
/*     */ 
/*     */     
/* 438 */     XMLMAP.addChild(new XMLElem("SDFCD", "SDFCD"));
/* 439 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "SVC", "D:MODELSVC:D");
/* 440 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 441 */     xMLGroupElem2.addChild(new XMLElem("SVCPACMACHBRAND", "SVCPACMACHBRAND"));
/* 442 */     xMLGroupElem2.addChild(new XMLElem("COVRPRIOD", "COVRPRIOD"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 453 */     XMLMAP.addChild(new XMLElem("MACHRATECATG", "MACHRATECATG"));
/* 454 */     XMLMAP.addChild(new XMLElem("CECSPRODKEY", "CECSPRODKEY"));
/* 455 */     XMLMAP.addChild(new XMLElem("PRODSUPRTCD", "PRODSUPRTCD"));
/* 456 */     XMLMAP.addChild(new XMLElem("MAINTANNBILLELIGINDC", "MAINTANNBILLELIGINDC"));
/* 457 */     XMLMAP.addChild(new XMLElem("NOCHRGMAINTINDC", "NOCHRGMAINTINDC"));
/* 458 */     XMLMAP.addChild(new XMLElem("RETANINDC", "RETANINDC", 2));
/* 459 */     XMLMAP.addChild(new XMLElem("SYSTEMTYPE", "SYSTEMTYPE"));
/*     */     
/* 461 */     XMLMAP.addChild(new XMLElem("PHANTOMMODINDC", "PHANTOMMODINDC", 2));
/*     */ 
/*     */     
/* 464 */     XMLMAP.addChild(new XMLElem("SWMAIND", "SWMAINDC", 2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 473 */     XMLElem xMLElem7 = new XMLElem("LANGUAGELIST");
/* 474 */     XMLMAP.addChild(xMLElem7);
/*     */ 
/*     */     
/* 477 */     XMLNLSElem xMLNLSElem1 = new XMLNLSElem("LANGUAGEELEMENT");
/* 478 */     xMLElem7.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 480 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 485 */     xMLNLSElem1.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */     
/* 488 */     xMLNLSElem1.addChild(new XMLElem("INVNAME", "INVNAME"));
/*     */ 
/*     */     
/* 491 */     xMLElem7 = new XMLElem("AVAILABILITYLIST");
/* 492 */     XMLMAP.addChild(xMLElem7);
/* 493 */     xMLElem7.addChild((XMLElem)new XMLAVAILElembh1());
/*     */ 
/*     */ 
/*     */     
/* 497 */     XMLGroupElem xMLGroupElem9 = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
/* 498 */     XMLMAP.addChild((XMLElem)xMLGroupElem9);
/*     */     
/* 500 */     XMLElem xMLElem8 = new XMLElem("TAXCATEGORYELEMENT");
/* 501 */     xMLGroupElem9.addChild(xMLElem8);
/*     */     
/* 503 */     xMLElem8.addChild((XMLElem)new XMLActivityElem("TAXCATEGORYACTION"));
/*     */     
/* 505 */     XMLElem xMLElem6 = new XMLElem("COUNTRYLIST");
/* 506 */     xMLElem8.addChild(xMLElem6);
/*     */     
/* 508 */     XMLChgSetElem xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 509 */     xMLElem6.addChild((XMLElem)xMLChgSetElem2);
/*     */ 
/*     */     
/* 512 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "TAXCNTRY", "COUNTRYACTION", 1));
/*     */     
/* 514 */     xMLElem8.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", 1));
/* 515 */     XMLGroupElem xMLGroupElem10 = new XMLGroupElem(null, "MODTAXRELEVANCE", "U:MODTAXRELEVANCE");
/* 516 */     xMLElem8.addChild((XMLElem)xMLGroupElem10);
/* 517 */     xMLGroupElem10.addChild(new XMLElem("TAXCLASSIFICATION", "TAXCLS", 2));
/*     */     
/* 519 */     xMLElem8.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXCATGSLEORGA:D"));
/*     */ 
/*     */     
/* 522 */     XMLGroupElem xMLGroupElem8 = new XMLGroupElem("TAXCODELIST", "TAXGRP");
/* 523 */     XMLMAP.addChild((XMLElem)xMLGroupElem8);
/*     */     
/* 525 */     XMLElem xMLElem9 = new XMLElem("TAXCODEELEMENT");
/* 526 */     xMLGroupElem8.addChild(xMLElem9);
/*     */     
/* 528 */     xMLElem9.addChild((XMLElem)new XMLActivityElem("TAXCODEACTION"));
/*     */     
/* 530 */     xMLElem9.addChild(new XMLElem("TAXCODEDESCRIPTION", "DESC"));
/*     */ 
/*     */ 
/*     */     
/* 534 */     xMLElem9.addChild(new XMLElem("TAXCODE", "TAXCD"));
/*     */     
/* 536 */     XMLElem xMLElem5 = new XMLElem("COUNTRYLIST");
/* 537 */     xMLElem9.addChild(xMLElem5);
/*     */     
/* 539 */     XMLChgSetElem xMLChgSetElem3 = new XMLChgSetElem("COUNTRYELEMENT");
/* 540 */     xMLElem5.addChild((XMLElem)xMLChgSetElem3);
/*     */     
/* 542 */     xMLChgSetElem3.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */     
/* 544 */     xMLElem9.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXGRPSLEORGA:D"));
/*     */ 
/*     */     
/* 547 */     xMLElem5 = new XMLElem("AUDIENCELIST");
/* 548 */     XMLMAP.addChild(xMLElem5);
/*     */     
/* 550 */     XMLChgSetElem xMLChgSetElem4 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 551 */     xMLElem5.addChild((XMLElem)xMLChgSetElem4);
/*     */ 
/*     */     
/* 554 */     xMLChgSetElem4.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "AUDIEN", "AUDIENCEACTION", 0));
/*     */ 
/*     */     
/* 557 */     XMLMAP.addChild((XMLElem)new XMLZCONFElem());
/*     */ 
/*     */     
/* 560 */     xMLElem5 = new XMLElem("CATALOGOVERRIDELIST");
/* 561 */     XMLMAP.addChild(xMLElem5);
/* 562 */     xMLElem5.addChild((XMLElem)new XMLCATAElem());
/*     */ 
/*     */     
/* 565 */     xMLElem5 = new XMLElem("OSLIST");
/* 566 */     XMLMAP.addChild(xMLElem5);
/* 567 */     XMLChgSetElem xMLChgSetElem5 = new XMLChgSetElem("OSELEMENT");
/* 568 */     xMLElem5.addChild((XMLElem)xMLChgSetElem5);
/* 569 */     xMLChgSetElem5.addChild((XMLElem)new XMLMultiFlagElem("OSLEVEL", "OSLEVEL", "OSACTION", 1));
/*     */ 
/*     */     
/* 572 */     XMLGroupElem xMLGroupElem7 = new XMLGroupElem("MMLIST", "MM");
/* 573 */     XMLMAP.addChild((XMLElem)xMLGroupElem7);
/*     */     
/* 575 */     XMLElem xMLElem10 = new XMLElem("MMELEMENT");
/* 576 */     xMLGroupElem7.addChild(xMLElem10);
/*     */     
/* 578 */     xMLElem10.addChild((XMLElem)new XMLActivityElem("MMACTION"));
/* 579 */     xMLElem10.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 580 */     xMLElem10.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 581 */     xMLElem10.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 582 */     xMLElem10.addChild(new XMLElem("PUBTO", "PUBTO"));
/* 583 */     xMLElem10.addChild(new XMLElem("STATUS", "MMSTATUS", 1));
/*     */     
/* 585 */     XMLElem xMLElem4 = new XMLElem("LANGUAGELIST");
/* 586 */     xMLElem10.addChild(xMLElem4);
/*     */     
/* 588 */     xMLNLSElem1 = new XMLNLSElem("LANGUAGEELEMENT");
/* 589 */     xMLElem4.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 591 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/* 592 */     xMLNLSElem1.addChild(new XMLElem("LONGMM", "LONGMKTGMSG"));
/*     */     
/* 594 */     xMLElem4 = new XMLElem("COUNTRYLIST");
/* 595 */     xMLElem10.addChild(xMLElem4);
/*     */     
/* 597 */     xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 598 */     xMLElem4.addChild((XMLElem)xMLChgSetElem2);
/*     */     
/* 600 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */     
/* 602 */     xMLElem4 = new XMLElem("AUDIENCELIST");
/* 603 */     xMLElem10.addChild(xMLElem4);
/*     */     
/* 605 */     xMLChgSetElem2 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 606 */     xMLElem4.addChild((XMLElem)xMLChgSetElem2);
/*     */     
/* 608 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTION", 0));
/*     */     
/* 610 */     xMLElem4 = new XMLElem("PAGETYPELIST");
/* 611 */     xMLElem10.addChild(xMLElem4);
/*     */     
/* 613 */     xMLChgSetElem2 = new XMLChgSetElem("PAGETYPEELEMENT");
/* 614 */     xMLElem4.addChild((XMLElem)xMLChgSetElem2);
/*     */     
/* 616 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("PAGETYPE", "CATPAGETYPE", "PAGETYPEACTION", 0));
/*     */ 
/*     */ 
/*     */     
/* 620 */     XMLGroupElem xMLGroupElem6 = new XMLGroupElem("WARRLIST", "WARR");
/* 621 */     XMLMAP.addChild((XMLElem)xMLGroupElem6);
/*     */     
/* 623 */     XMLElem xMLElem11 = new XMLElem("WARRELEMENT");
/* 624 */     xMLGroupElem6.addChild(xMLElem11);
/*     */     
/* 626 */     xMLElem11.addChild((XMLElem)new XMLActivityElem("WARRACTION"));
/* 627 */     xMLElem11.addChild(new XMLElem("WARRENTITYTYPE", "ENTITYTYPE"));
/* 628 */     xMLElem11.addChild(new XMLElem("WARRENTITYID", "ENTITYID"));
/* 629 */     xMLElem11.addChild(new XMLElem("WARRID", "WARRID"));
/* 630 */     XMLGroupElem xMLGroupElem11 = new XMLGroupElem(null, "MODELWARR", "U:MODELWARR");
/* 631 */     xMLElem11.addChild((XMLElem)xMLGroupElem11);
/* 632 */     xMLGroupElem11.addChild(new XMLElem("PUBFROM", "EFFECTIVEDATE"));
/* 633 */     xMLGroupElem11.addChild(new XMLElem("PUBTO", "ENDDATE"));
/*     */     
/* 635 */     xMLGroupElem11.addChild(new XMLElem("DEFWARR", "DEFWARR"));
/* 636 */     XMLElem xMLElem3 = new XMLElem("COUNTRYLIST");
/* 637 */     xMLElem11.addChild(xMLElem3);
/*     */     
/* 639 */     xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 640 */     xMLElem3.addChild((XMLElem)xMLChgSetElem2);
/* 641 */     XMLGroupElem xMLGroupElem12 = new XMLGroupElem(null, "MODELWARR", "U:MODELWARR");
/* 642 */     xMLChgSetElem2.addChild((XMLElem)xMLGroupElem12);
/*     */     
/* 644 */     xMLGroupElem12.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 648 */     XMLGroupElem xMLGroupElem5 = new XMLGroupElem("IMAGELIST", "IMG");
/* 649 */     XMLMAP.addChild((XMLElem)xMLGroupElem5);
/*     */     
/* 651 */     XMLElem xMLElem12 = new XMLElem("IMAGEELEMENT");
/* 652 */     xMLGroupElem5.addChild(xMLElem12);
/*     */     
/* 654 */     xMLElem12.addChild((XMLElem)new XMLActivityElem("IMAGEACTION"));
/* 655 */     xMLElem12.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 656 */     xMLElem12.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 657 */     xMLElem12.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 658 */     xMLElem12.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 659 */     xMLElem12.addChild(new XMLElem("PUBTO", "PUBTO"));
/* 660 */     xMLElem12.addChild(new XMLElem("IMAGEDESCRIPTION", "IMGDESC"));
/* 661 */     xMLElem12.addChild(new XMLElem("MARKETINGIMAGEFILENAME", "MKTGIMGFILENAM"));
/*     */ 
/*     */     
/* 664 */     XMLElem xMLElem2 = new XMLElem("COUNTRYLIST");
/* 665 */     xMLElem12.addChild(xMLElem2);
/*     */     
/* 667 */     xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 668 */     xMLElem2.addChild((XMLElem)xMLChgSetElem2);
/*     */     
/* 670 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 674 */     XMLGroupElem xMLGroupElem4 = new XMLGroupElem("FBLIST", "FB");
/* 675 */     XMLMAP.addChild((XMLElem)xMLGroupElem4);
/*     */     
/* 677 */     XMLElem xMLElem13 = new XMLElem("FBELEMENT");
/* 678 */     xMLGroupElem4.addChild(xMLElem13);
/*     */ 
/*     */     
/* 681 */     xMLElem13.addChild((XMLElem)new XMLActivityElem("FBACTION"));
/* 682 */     xMLElem13.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 683 */     xMLElem13.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 684 */     xMLElem13.addChild(new XMLElem("STATUS", "FBSTATUS", 1));
/* 685 */     xMLElem13.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 686 */     xMLElem13.addChild(new XMLElem("PUBTO", "PUBTO"));
/*     */     
/* 688 */     XMLElem xMLElem14 = new XMLElem("FBSTMTLIST");
/* 689 */     xMLElem13.addChild(xMLElem14);
/*     */     
/* 691 */     XMLNLSElem xMLNLSElem2 = new XMLNLSElem("FBSTMTELEMENT");
/* 692 */     xMLElem14.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 694 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */ 
/*     */     
/* 698 */     xMLNLSElem2.addChild(new XMLElem("FBSTMT", "FBSTMT"));
/*     */     
/* 700 */     XMLElem xMLElem15 = new XMLElem("AUDIENCELIST");
/* 701 */     xMLElem13.addChild(xMLElem15);
/* 702 */     XMLChgSetElem xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 703 */     xMLElem15.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 705 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTION", 0));
/*     */     
/* 707 */     XMLElem xMLElem1 = new XMLElem("COUNTRYLIST");
/* 708 */     xMLElem13.addChild(xMLElem1);
/*     */     
/* 710 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 711 */     xMLElem1.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 713 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 718 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem("CATATTRIBUTELIST", "CATDATA");
/* 719 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/*     */     
/* 721 */     XMLNLSElem xMLNLSElem3 = new XMLNLSElem("CATATTRIBUTEELEMENT");
/* 722 */     xMLGroupElem3.addChild((XMLElem)xMLNLSElem3);
/*     */     
/* 724 */     xMLNLSElem3.addChild((XMLElem)new XMLActivityElem("CATATTRIBUTEACTION"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 730 */     xMLNLSElem3.addChild(new XMLElem("CATATTRIBUTE", "DAATTRIBUTECODE"));
/* 731 */     xMLNLSElem3.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 733 */     xMLNLSElem3.addChild(new XMLElem("CATATTRIUBTEVALUE", "DAATTRIBUTEVALUE"));
/*     */ 
/*     */ 
/*     */     
/* 737 */     xMLGroupElem3 = new XMLGroupElem("UNBUNDCOMPLIST", "REVUNBUNDCOMP");
/* 738 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/*     */     
/* 740 */     XMLElem xMLElem16 = new XMLElem("UNBUNDCOMPELEMENT");
/* 741 */     xMLGroupElem3.addChild(xMLElem16);
/*     */     
/* 743 */     xMLElem16.addChild((XMLElem)new XMLActivityElem("UNBUNDCOMPACTION"));
/* 744 */     xMLElem16.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 745 */     xMLElem16.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 746 */     xMLElem16.addChild(new XMLElem("UNBUNDCOMPID", "UNBUNDCOMPID"));
/*     */     
/* 748 */     XMLDistinctGroupElem xMLDistinctGroupElem2 = new XMLDistinctGroupElem(null, "MODUNBUND", "U:MODUNBUND", true, true);
/* 749 */     xMLElem16.addChild((XMLElem)xMLDistinctGroupElem2);
/* 750 */     xMLDistinctGroupElem2.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 751 */     xMLDistinctGroupElem2.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/* 752 */     xMLDistinctGroupElem2.addChild(new XMLElem("UNBUNDTYPE", "UNBUNDTYPE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 759 */     xMLGroupElem3 = new XMLGroupElem("REPLMODELLIST", "MODEL", "D:MODELREPLMODEL:D", true);
/* 760 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/*     */     
/* 762 */     XMLElem xMLElem17 = new XMLElem("REPLMODELELEMENT");
/* 763 */     xMLGroupElem3.addChild(xMLElem17);
/*     */     
/* 765 */     xMLElem17.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*     */     
/* 767 */     xMLElem17.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/*     */     
/* 769 */     xMLElem17.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/*     */     
/* 771 */     xMLElem17.addChild(new XMLElem("MACHTYPE", "MACHTYPEATR"));
/*     */     
/* 773 */     xMLElem17.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 784 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 790 */     return "ADSMODEL";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 795 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 801 */     return "MODEL_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 810 */     return "1.3";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSMODELABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */