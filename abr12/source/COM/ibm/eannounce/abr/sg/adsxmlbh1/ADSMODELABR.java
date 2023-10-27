/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLAVAILElembh1;
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCATAElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLDistinctGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMODELTAXElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMTDGroupElem;
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
/*     */ public class ADSMODELABR
/*     */   extends XMLMQRoot
/*     */ {
/* 264 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("MODEL_UPDATE"); static {
/* 265 */     XMLMAP.addChild((XMLElem)new XMLVMElem("MODEL_UPDATE", "1"));
/*     */     
/* 267 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 268 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/*     */     
/* 270 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 271 */     XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/* 272 */     XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/* 273 */     XMLMAP.addChild((XMLElem)new XMLMachtypeElem("MACHTYPE", "MACHTYPEATR"));
/* 274 */     XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */     
/* 276 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 277 */     XMLMAP.addChild(new XMLElem("CATEGORY", "COFCAT"));
/*     */ 
/*     */     
/* 280 */     XMLMAP.addChild(new XMLElem("SUBCATEGORY", "COFSUBCAT", 2));
/* 281 */     XMLMAP.addChild(new XMLElem("GROUP", "COFGRP"));
/* 282 */     XMLMAP.addChild(new XMLElem("SUBGROUP", "COFSUBGRP"));
/* 283 */     XMLMAP.addChild(new XMLElem("APPLICATIONTYPE", "APPLICATIONTYPE"));
/* 284 */     XMLMAP.addChild(new XMLElem("ORDERCODE", "MODELORDERCODE", 2));
/* 285 */     XMLMAP.addChild(new XMLElem("SARINDC", "SARINDC"));
/* 286 */     XMLMAP.addChild(new XMLElem("PROJECT", "PROJCDNAM", 1));
/*     */ 
/*     */ 
/*     */     
/* 290 */     XMLDistinctGroupElem xMLDistinctGroupElem1 = new XMLDistinctGroupElem(null, "SGMNTACRNYM", "D:MODELSGMTACRONYMA:D", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     XMLMAP.addChild((XMLElem)xMLDistinctGroupElem1);
/* 296 */     xMLDistinctGroupElem1.addChild(new XMLElem("DIVISION", "DIV", 1));
/*     */     
/* 298 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     XMLMAP.addChild(new XMLElem("RATECARD", "RATECARDCD", 1));
/* 306 */     XMLMAP.addChild(new XMLElem("UNITCLASS", "SYSIDUNIT"));
/* 307 */     XMLMAP.addChild(new XMLElem("PRICEDIND", "PRCINDC"));
/* 308 */     XMLMAP.addChild(new XMLElem("INSTALL", "INSTALL"));
/* 309 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/* 310 */     XMLMAP.addChild(new XMLElem("UNSPSC", "UNSPSCCD", 1));
/* 311 */     XMLMAP.addChild(new XMLElem("UNSPSCSECONDARY", "UNSPSCCDSECONDRY", 1));
/* 312 */     XMLMAP.addChild(new XMLElem("UNUOM", "UNSPSCCDUOM"));
/*     */ 
/*     */     
/* 315 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "WEIGHTNDIMN", "D:MODELWEIGHTNDIMN:D");
/* 316 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/* 317 */     xMLGroupElem1.addChild(new XMLElem("MEASUREMETRIC", "WGHTMTRIC|WGHTMTRICUNIT"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     XMLMAP.addChild(new XMLElem("WARRSVCCOVR", "WARRSVCCOVR", 2));
/*     */     
/* 327 */     XMLMAP.addChild(new XMLElem("PRODHIERCD", "BHPRODHIERCD"));
/*     */ 
/*     */     
/* 330 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */ 
/*     */     
/* 333 */     XMLMAP.addChild(new XMLElem("AMRTZTNLNGTH", "AMRTZTNLNGTH"));
/*     */ 
/*     */     
/* 336 */     XMLMAP.addChild(new XMLElem("AMRTZTNSTRT", "AMRTZTNSTRT"));
/*     */ 
/*     */     
/* 339 */     XMLMAP.addChild(new XMLElem("PRODID", "PRODID"));
/*     */ 
/*     */     
/* 342 */     XMLMAP.addChild(new XMLElem("SWROYALBEARING", "SWROYALBEARING"));
/*     */ 
/*     */     
/* 345 */     XMLMAP.addChild(new XMLElem("SOMFAMILY", "SOMFMLY", 1));
/*     */ 
/*     */     
/* 348 */     XMLMAP.addChild(new XMLElem("LIC", "LICNSINTERCD"));
/*     */ 
/*     */     
/* 351 */     XMLMAP.addChild(new XMLElem("BPCERTSPECBID", "BPSPECBIDCERTREQ"));
/*     */ 
/*     */     
/* 354 */     XMLMAP.addChild(new XMLElem("WWOCCODE", "WWOCCODE"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 359 */     XMLMAP.addChild(new XMLElem("SPECBID", "SPECBID"));
/* 360 */     XMLMAP.addChild(new XMLElem("PRPQAPPRVTYPE", "PRPQAPPRVTYPE"));
/*     */ 
/*     */     
/* 363 */     XMLMAP.addChild(new XMLElem("DUALPIPE", "DUALPIPE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 377 */     XMLMAP.addChild(new XMLElem("SVCLEVCD", "SVCLEVCD"));
/* 378 */     XMLMAP.addChild(new XMLElem("ENDCUSTIDREQ", "ENDCUSTIDREQ"));
/* 379 */     XMLMAP.addChild(new XMLElem("ENTITLMENTSCOPE", "ENTITLMENTSCOPE", 2));
/* 380 */     XMLMAP.addChild(new XMLElem("PRORATEDOTCALLOW", "PRORATEDOTCALLOW"));
/* 381 */     XMLMAP.addChild(new XMLElem("SVCGOODSENTITL", "SVCGOODSENTITL", 2));
/* 382 */     XMLMAP.addChild(new XMLElem("TYPEOFWRK", "TYPEOFWRK", 2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 391 */     XMLMAP.addChild(new XMLElem("SDFCD", "SDFCD"));
/* 392 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "SVC", "D:MODELSVC:D");
/* 393 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 394 */     xMLGroupElem2.addChild(new XMLElem("SVCPACMACHBRAND", "SVCPACMACHBRAND"));
/* 395 */     xMLGroupElem2.addChild(new XMLElem("COVRPRIOD", "COVRPRIOD"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 415 */     XMLMAP.addChild(new XMLElem("MACHRATECATG", "MACHRATECATG"));
/* 416 */     XMLMAP.addChild(new XMLElem("CECSPRODKEY", "CECSPRODKEY"));
/* 417 */     XMLMAP.addChild(new XMLElem("PRODSUPRTCD", "PRODSUPRTCD"));
/* 418 */     XMLMAP.addChild(new XMLElem("MAINTANNBILLELIGINDC", "MAINTANNBILLELIGINDC"));
/* 419 */     XMLMAP.addChild(new XMLElem("NOCHRGMAINTINDC", "NOCHRGMAINTINDC"));
/* 420 */     XMLMAP.addChild(new XMLElem("RETANINDC", "RETANINDC", 2));
/* 421 */     XMLMAP.addChild(new XMLElem("SYSTEMTYPE", "SYSTEMTYPE"));
/*     */ 
/*     */ 
/*     */     
/* 425 */     XMLMAP.addChild(new XMLElem("PHANTOMMODINDC", "PHANTOMMODINDC", 2));
/*     */ 
/*     */ 
/*     */     
/* 429 */     XMLMAP.addChild(new XMLElem("SWMAIND", "SWMAINDC", 2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 435 */     XMLMAP.addChild(new XMLElem("VOLUMEDISCOUNTELIG", "VOLUMEDISCOUNTELIG"));
/* 436 */     XMLMAP.addChild(new XMLElem("IBMCREDIT", "IBMCREDIT"));
/*     */     
/* 438 */     XMLMAP.addChild(new XMLElem("VENDNAME", "VENDNAME"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 447 */     XMLElem xMLElem9 = new XMLElem("LANGUAGELIST");
/* 448 */     XMLMAP.addChild(xMLElem9);
/*     */ 
/*     */     
/* 451 */     XMLNLSElem xMLNLSElem1 = new XMLNLSElem("LANGUAGEELEMENT");
/* 452 */     xMLElem9.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 454 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 459 */     xMLNLSElem1.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */     
/* 462 */     xMLNLSElem1.addChild(new XMLElem("INVNAME", "INVNAME"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 470 */     XMLMTDGroupElem xMLMTDGroupElem = new XMLMTDGroupElem(null, "MACHTYPE", "D:MODELMACHINETYPEA:D");
/* 471 */     xMLNLSElem1.addChild((XMLElem)xMLMTDGroupElem);
/* 472 */     xMLMTDGroupElem.addChild(new XMLElem("MTDESCRIPTION", "INTERNALNAME"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 498 */     xMLElem9 = new XMLElem("AVAILABILITYLIST");
/* 499 */     XMLMAP.addChild(xMLElem9);
/* 500 */     xMLElem9.addChild((XMLElem)new XMLAVAILElembh1());
/*     */ 
/*     */     
/* 503 */     XMLGroupElem xMLGroupElem10 = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
/* 504 */     XMLMAP.addChild((XMLElem)xMLGroupElem10);
/*     */     
/* 506 */     XMLElem xMLElem10 = new XMLElem("TAXCATEGORYELEMENT");
/*     */ 
/*     */     
/* 509 */     xMLGroupElem10.addChild(xMLElem10);
/*     */     
/* 511 */     xMLElem10.addChild((XMLElem)new XMLActivityElem("TAXCATEGORYACTION"));
/*     */     
/* 513 */     XMLElem xMLElem8 = new XMLElem("COUNTRYLIST");
/* 514 */     xMLElem10.addChild(xMLElem8);
/*     */     
/* 516 */     XMLChgSetElem xMLChgSetElem3 = new XMLChgSetElem("COUNTRYELEMENT");
/* 517 */     xMLElem8.addChild((XMLElem)xMLChgSetElem3);
/*     */ 
/*     */     
/* 520 */     xMLChgSetElem3.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "TAXCNTRY", "COUNTRYACTION", 1));
/*     */     
/* 522 */     xMLElem10.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", 1));
/* 523 */     XMLGroupElem xMLGroupElem11 = new XMLGroupElem(null, "MODTAXRELEVANCE", "U:MODTAXRELEVANCE");
/* 524 */     xMLElem10.addChild((XMLElem)xMLGroupElem11);
/* 525 */     xMLGroupElem11.addChild((XMLElem)new XMLMODELTAXElem("TAXCLASSIFICATION", "TAXCLS", 2));
/* 526 */     xMLGroupElem11.addChild((XMLElem)new XMLMODELTAXElem("PRODUCTCODE", "PRODCODE", 1));
/*     */     
/* 528 */     xMLElem10.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXCATGSLEORGA:D"));
/*     */ 
/*     */     
/* 531 */     XMLGroupElem xMLGroupElem9 = new XMLGroupElem("TAXCODELIST", "TAXGRP");
/* 532 */     XMLMAP.addChild((XMLElem)xMLGroupElem9);
/*     */     
/* 534 */     XMLElem xMLElem11 = new XMLElem("TAXCODEELEMENT");
/* 535 */     xMLGroupElem9.addChild(xMLElem11);
/*     */     
/* 537 */     xMLElem11.addChild((XMLElem)new XMLActivityElem("TAXCODEACTION"));
/*     */     
/* 539 */     xMLElem11.addChild(new XMLElem("TAXCODEDESCRIPTION", "DESC"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 544 */     xMLElem11.addChild(new XMLElem("TAXCODE", "TAXCD"));
/*     */     
/* 546 */     XMLElem xMLElem7 = new XMLElem("COUNTRYLIST");
/* 547 */     xMLElem11.addChild(xMLElem7);
/*     */     
/* 549 */     XMLChgSetElem xMLChgSetElem4 = new XMLChgSetElem("COUNTRYELEMENT");
/* 550 */     xMLElem7.addChild((XMLElem)xMLChgSetElem4);
/*     */     
/* 552 */     xMLChgSetElem4.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */     
/* 554 */     xMLElem11.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXGRPSLEORGA:D"));
/*     */ 
/*     */     
/* 557 */     xMLElem7 = new XMLElem("RELEXPCAMTLIST");
/* 558 */     XMLMAP.addChild(xMLElem7);
/*     */     
/* 560 */     XMLChgSetElem xMLChgSetElem5 = new XMLChgSetElem("RELEXPCAMTELEMENT");
/* 561 */     xMLElem7.addChild((XMLElem)xMLChgSetElem5);
/*     */ 
/*     */     
/* 564 */     xMLChgSetElem5.addChild((XMLElem)new XMLMultiFlagElem("RELEXPCAMT", "RELEXPCAMT", "RELEXPCAMTACTION", 0));
/*     */ 
/*     */ 
/*     */     
/* 568 */     xMLElem7 = new XMLElem("AUDIENCELIST");
/* 569 */     XMLMAP.addChild(xMLElem7);
/*     */     
/* 571 */     XMLChgSetElem xMLChgSetElem6 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 572 */     xMLElem7.addChild((XMLElem)xMLChgSetElem6);
/*     */ 
/*     */     
/* 575 */     xMLChgSetElem6.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "AUDIEN", "AUDIENCEACTION", 0));
/*     */ 
/*     */     
/* 578 */     XMLMAP.addChild((XMLElem)new XMLZCONFElem());
/*     */ 
/*     */     
/* 581 */     xMLElem7 = new XMLElem("CATALOGOVERRIDELIST");
/* 582 */     XMLMAP.addChild(xMLElem7);
/* 583 */     xMLElem7.addChild((XMLElem)new XMLCATAElem());
/*     */ 
/*     */     
/* 586 */     xMLElem7 = new XMLElem("OSLIST");
/* 587 */     XMLMAP.addChild(xMLElem7);
/* 588 */     XMLChgSetElem xMLChgSetElem1 = new XMLChgSetElem("OSELEMENT");
/* 589 */     xMLElem7.addChild((XMLElem)xMLChgSetElem1);
/* 590 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("OSLEVEL", "OSLEVEL", "OSACTION", 1));
/*     */ 
/*     */     
/* 593 */     XMLGroupElem xMLGroupElem8 = new XMLGroupElem("MMLIST", "MM");
/* 594 */     XMLMAP.addChild((XMLElem)xMLGroupElem8);
/*     */     
/* 596 */     XMLElem xMLElem12 = new XMLElem("MMELEMENT");
/*     */ 
/*     */     
/* 599 */     xMLGroupElem8.addChild(xMLElem12);
/*     */     
/* 601 */     xMLElem12.addChild((XMLElem)new XMLActivityElem("MMACTION"));
/* 602 */     xMLElem12.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 603 */     xMLElem12.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 604 */     xMLElem12.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 605 */     xMLElem12.addChild(new XMLElem("PUBTO", "PUBTO"));
/* 606 */     xMLElem12.addChild(new XMLElem("STATUS", "MMSTATUS", 1));
/*     */     
/* 608 */     XMLElem xMLElem6 = new XMLElem("LANGUAGELIST");
/* 609 */     xMLElem12.addChild(xMLElem6);
/*     */     
/* 611 */     xMLNLSElem1 = new XMLNLSElem("LANGUAGEELEMENT");
/* 612 */     xMLElem6.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 614 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/* 615 */     xMLNLSElem1.addChild(new XMLElem("LONGMM", "LONGMKTGMSG"));
/*     */     
/* 617 */     xMLElem6 = new XMLElem("COUNTRYLIST");
/* 618 */     xMLElem12.addChild(xMLElem6);
/*     */     
/* 620 */     xMLChgSetElem3 = new XMLChgSetElem("COUNTRYELEMENT");
/* 621 */     xMLElem6.addChild((XMLElem)xMLChgSetElem3);
/*     */     
/* 623 */     xMLChgSetElem3.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */     
/* 625 */     xMLElem6 = new XMLElem("AUDIENCELIST");
/* 626 */     xMLElem12.addChild(xMLElem6);
/*     */     
/* 628 */     xMLChgSetElem3 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 629 */     xMLElem6.addChild((XMLElem)xMLChgSetElem3);
/*     */     
/* 631 */     xMLChgSetElem3.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTION", 0));
/*     */     
/* 633 */     xMLElem6 = new XMLElem("PAGETYPELIST");
/* 634 */     xMLElem12.addChild(xMLElem6);
/*     */     
/* 636 */     xMLChgSetElem3 = new XMLChgSetElem("PAGETYPEELEMENT");
/* 637 */     xMLElem6.addChild((XMLElem)xMLChgSetElem3);
/*     */     
/* 639 */     xMLChgSetElem3.addChild((XMLElem)new XMLMultiFlagElem("PAGETYPE", "CATPAGETYPE", "PAGETYPEACTION", 0));
/*     */ 
/*     */ 
/*     */     
/* 643 */     XMLGroupElem xMLGroupElem7 = new XMLGroupElem("WARRLIST", "WARR");
/* 644 */     XMLMAP.addChild((XMLElem)xMLGroupElem7);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 649 */     XMLElem xMLElem13 = new XMLElem("WARRELEMENT");
/*     */ 
/*     */     
/* 652 */     xMLGroupElem7.addChild(xMLElem13);
/*     */     
/* 654 */     xMLElem13.addChild((XMLElem)new XMLActivityElem("WARRACTION"));
/* 655 */     xMLElem13.addChild(new XMLElem("WARRENTITYTYPE", "ENTITYTYPE"));
/* 656 */     xMLElem13.addChild(new XMLElem("WARRENTITYID", "ENTITYID"));
/* 657 */     xMLElem13.addChild(new XMLElem("WARRID", "WARRID"));
/*     */ 
/*     */ 
/*     */     
/* 661 */     xMLElem13.addChild(new XMLElem("WARRPRIOD", "WARRPRIOD"));
/* 662 */     xMLElem13.addChild(new XMLElem("WARRDESC", "INVNAME"));
/*     */     
/* 664 */     XMLGroupElem xMLGroupElem12 = new XMLGroupElem(null, "MODELWARR", "U:MODELWARR");
/* 665 */     xMLElem13.addChild((XMLElem)xMLGroupElem12);
/* 666 */     xMLGroupElem12.addChild(new XMLElem("PUBFROM", "EFFECTIVEDATE"));
/* 667 */     xMLGroupElem12.addChild(new XMLElem("PUBTO", "ENDDATE"));
/*     */     
/* 669 */     xMLGroupElem12.addChild(new XMLElem("DEFWARR", "DEFWARR"));
/* 670 */     XMLElem xMLElem5 = new XMLElem("COUNTRYLIST");
/* 671 */     xMLElem13.addChild(xMLElem5);
/*     */     
/* 673 */     xMLChgSetElem3 = new XMLChgSetElem("COUNTRYELEMENT");
/* 674 */     xMLElem5.addChild((XMLElem)xMLChgSetElem3);
/* 675 */     XMLGroupElem xMLGroupElem13 = new XMLGroupElem(null, "MODELWARR", "U:MODELWARR");
/* 676 */     xMLChgSetElem3.addChild((XMLElem)xMLGroupElem13);
/*     */ 
/*     */     
/* 679 */     xMLGroupElem13.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 683 */     XMLGroupElem xMLGroupElem6 = new XMLGroupElem("IMAGELIST", "IMG");
/* 684 */     XMLMAP.addChild((XMLElem)xMLGroupElem6);
/*     */     
/* 686 */     XMLElem xMLElem14 = new XMLElem("IMAGEELEMENT");
/*     */ 
/*     */     
/* 689 */     xMLGroupElem6.addChild(xMLElem14);
/*     */     
/* 691 */     xMLElem14.addChild((XMLElem)new XMLActivityElem("IMAGEACTION"));
/* 692 */     xMLElem14.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 693 */     xMLElem14.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 694 */     xMLElem14.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 695 */     xMLElem14.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 696 */     xMLElem14.addChild(new XMLElem("PUBTO", "PUBTO"));
/* 697 */     xMLElem14.addChild(new XMLElem("IMAGEDESCRIPTION", "IMGDESC"));
/* 698 */     xMLElem14.addChild(new XMLElem("MARKETINGIMAGEFILENAME", "MKTGIMGFILENAM"));
/*     */ 
/*     */     
/* 701 */     XMLElem xMLElem4 = new XMLElem("COUNTRYLIST");
/* 702 */     xMLElem14.addChild(xMLElem4);
/*     */     
/* 704 */     xMLChgSetElem3 = new XMLChgSetElem("COUNTRYELEMENT");
/* 705 */     xMLElem4.addChild((XMLElem)xMLChgSetElem3);
/*     */     
/* 707 */     xMLChgSetElem3.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 711 */     XMLGroupElem xMLGroupElem5 = new XMLGroupElem("FBLIST", "FB");
/* 712 */     XMLMAP.addChild((XMLElem)xMLGroupElem5);
/*     */     
/* 714 */     XMLElem xMLElem15 = new XMLElem("FBELEMENT");
/*     */ 
/*     */     
/* 717 */     xMLGroupElem5.addChild(xMLElem15);
/*     */ 
/*     */ 
/*     */     
/* 721 */     xMLElem15.addChild((XMLElem)new XMLActivityElem("FBACTION"));
/* 722 */     xMLElem15.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 723 */     xMLElem15.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 724 */     xMLElem15.addChild(new XMLElem("STATUS", "FBSTATUS", 1));
/* 725 */     xMLElem15.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 726 */     xMLElem15.addChild(new XMLElem("PUBTO", "PUBTO"));
/*     */     
/* 728 */     XMLElem xMLElem16 = new XMLElem("FBSTMTLIST");
/* 729 */     xMLElem15.addChild(xMLElem16);
/*     */     
/* 731 */     XMLNLSElem xMLNLSElem2 = new XMLNLSElem("FBSTMTELEMENT");
/* 732 */     xMLElem16.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 734 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */ 
/*     */     
/* 738 */     xMLNLSElem2.addChild(new XMLElem("FBSTMT", "FBSTMT"));
/*     */     
/* 740 */     XMLElem xMLElem17 = new XMLElem("AUDIENCELIST");
/* 741 */     xMLElem15.addChild(xMLElem17);
/* 742 */     XMLChgSetElem xMLChgSetElem2 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 743 */     xMLElem17.addChild((XMLElem)xMLChgSetElem2);
/*     */     
/* 745 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTION", 0));
/*     */     
/* 747 */     XMLElem xMLElem3 = new XMLElem("COUNTRYLIST");
/* 748 */     xMLElem15.addChild(xMLElem3);
/*     */     
/* 750 */     xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 751 */     xMLElem3.addChild((XMLElem)xMLChgSetElem2);
/*     */     
/* 753 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 757 */     XMLGroupElem xMLGroupElem4 = new XMLGroupElem("CATATTRIBUTELIST", "CATDATA");
/* 758 */     XMLMAP.addChild((XMLElem)xMLGroupElem4);
/*     */     
/* 760 */     XMLNLSElem xMLNLSElem3 = new XMLNLSElem("CATATTRIBUTEELEMENT");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 765 */     xMLGroupElem4.addChild((XMLElem)xMLNLSElem3);
/*     */     
/* 767 */     xMLNLSElem3.addChild((XMLElem)new XMLActivityElem("CATATTRIBUTEACTION"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 773 */     xMLNLSElem3.addChild(new XMLElem("CATATTRIBUTE", "DAATTRIBUTECODE"));
/* 774 */     xMLNLSElem3.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 776 */     xMLNLSElem3.addChild(new XMLElem("CATATTRIUBTEVALUE", "DAATTRIBUTEVALUE"));
/*     */ 
/*     */ 
/*     */     
/* 780 */     xMLGroupElem4 = new XMLGroupElem("UNBUNDCOMPLIST", "REVUNBUNDCOMP");
/* 781 */     XMLMAP.addChild((XMLElem)xMLGroupElem4);
/*     */     
/* 783 */     XMLElem xMLElem18 = new XMLElem("UNBUNDCOMPELEMENT");
/*     */ 
/*     */ 
/*     */     
/* 787 */     xMLGroupElem4.addChild(xMLElem18);
/*     */     
/* 789 */     xMLElem18.addChild((XMLElem)new XMLActivityElem("UNBUNDCOMPACTION"));
/* 790 */     xMLElem18.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 791 */     xMLElem18.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 792 */     xMLElem18.addChild(new XMLElem("UNBUNDCOMPID", "UNBUNDCOMPID"));
/*     */ 
/*     */     
/* 795 */     XMLDistinctGroupElem xMLDistinctGroupElem2 = new XMLDistinctGroupElem(null, "MODUNBUND", "U:MODUNBUND", true, true);
/* 796 */     xMLElem18.addChild((XMLElem)xMLDistinctGroupElem2);
/* 797 */     xMLDistinctGroupElem2.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 798 */     xMLDistinctGroupElem2.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/* 799 */     xMLDistinctGroupElem2.addChild(new XMLElem("UNBUNDTYPE", "UNBUNDTYPE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 806 */     xMLGroupElem4 = new XMLGroupElem("REPLMODELLIST", "MODEL", "D:MODELREPLMODEL:D", true);
/* 807 */     XMLMAP.addChild((XMLElem)xMLGroupElem4);
/*     */     
/* 809 */     XMLElem xMLElem19 = new XMLElem("REPLMODELELEMENT");
/* 810 */     xMLGroupElem4.addChild(xMLElem19);
/*     */ 
/*     */     
/* 813 */     xMLElem19.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 818 */     xMLElem19.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/*     */ 
/*     */     
/* 821 */     xMLElem19.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/*     */ 
/*     */     
/* 824 */     xMLElem19.addChild(new XMLElem("MACHTYPE", "MACHTYPEATR"));
/*     */ 
/*     */     
/* 827 */     xMLElem19.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 865 */     xMLGroupElem4 = new XMLGroupElem("GEOMODLIST", "GEOMOD");
/* 866 */     XMLMAP.addChild((XMLElem)xMLGroupElem4);
/* 867 */     XMLElem xMLElem20 = new XMLElem("GEOMODELEMENT");
/* 868 */     xMLGroupElem4.addChild(xMLElem20);
/* 869 */     xMLElem20.addChild((XMLElem)new XMLActivityElem("GEOMODACTION"));
/* 870 */     xMLElem20.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 871 */     xMLElem20.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 872 */     xMLElem20.addChild(new XMLElem("ANNUALMAINT", "ANNUALMAINT"));
/* 873 */     xMLElem20.addChild(new XMLElem("EDUCALLOWMHGHSCH", "EDUCALLOWMHGHSCH"));
/* 874 */     xMLElem20.addChild(new XMLElem("EDUCALLOWMSECONDRYSCH", "EDUCALLOWMSECONDRYSCH"));
/* 875 */     xMLElem20.addChild(new XMLElem("EDUCALLOWMUNVRSTY", "EDUCALLOWMUNVRSTY"));
/* 876 */     xMLElem20.addChild(new XMLElem("EDUCPURCHELIG", "EDUCPURCHELIG"));
/* 877 */     xMLElem20.addChild(new XMLElem("EMEABRANDCD", "EMEABRANDCD"));
/* 878 */     xMLElem20.addChild(new XMLElem("NOCHRGRENT", "NOCHRGRENT"));
/* 879 */     xMLElem20.addChild(new XMLElem("PERCALLCLS", "PERCALLCLS"));
/* 880 */     xMLElem20.addChild(new XMLElem("PLNTOFMFR", "PLNTOFMFR", 2));
/* 881 */     xMLElem20.addChild(new XMLElem("PURCHONLY", "PURCHONLY"));
/* 882 */     xMLElem20.addChild(new XMLElem("SYSTYPE"));
/* 883 */     xMLElem20.addChild(new XMLElem("INTEGRATEDMODEL", "INTEGRATEDMODEL"));
/* 884 */     xMLElem20.addChild(new XMLElem("GRADUATEDCHARGE", "GRADUATEDCHARGE", 2));
/* 885 */     xMLElem20.addChild(new XMLElem("METHODPROD", "METHODPROD"));
/*     */     
/* 887 */     XMLElem xMLElem2 = new XMLElem("COUNTRYLIST");
/* 888 */     xMLElem20.addChild(xMLElem2);
/*     */     
/* 890 */     xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 891 */     xMLElem2.addChild((XMLElem)xMLChgSetElem2);
/*     */     
/* 893 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 905 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem("COMPATMODELLIST", "MODEL", "U:MDLCGMDL:U:MODELCG:D:MDLCGMDLCGOS:D:MODELCGOS:D:MDLCGOSMDL:D");
/*     */     
/* 907 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/* 908 */     XMLElem xMLElem21 = new XMLElem("COMPATMODELELEMENT");
/* 909 */     xMLGroupElem3.addChild(xMLElem21);
/* 910 */     xMLElem21.addChild((XMLElem)new XMLActivityElem("COMPATMODELACTION"));
/* 911 */     xMLElem21.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 912 */     xMLElem21.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 913 */     xMLElem21.addChild(new XMLElem("MACHTYPE", "MACHTYPEATR"));
/* 914 */     xMLElem21.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */     
/* 916 */     xMLGroupElem3 = new XMLGroupElem("STDMAINTLIST", "STDMAINT");
/* 917 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/* 918 */     XMLElem xMLElem22 = new XMLElem("STDMAINTELEMENT");
/* 919 */     xMLGroupElem3.addChild(xMLElem22);
/* 920 */     xMLElem22.addChild((XMLElem)new XMLActivityElem("STDMAINTACTION"));
/* 921 */     xMLElem22.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 922 */     xMLElem22.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 923 */     xMLElem22.addChild(new XMLElem("MAINTELIG", "MAINTELIG"));
/* 924 */     xMLElem22.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 925 */     xMLElem22.addChild(new XMLElem("COMNAME", "COMNAME"));
/*     */     
/* 927 */     XMLElem xMLElem1 = new XMLElem("COUNTRYLIST");
/* 928 */     xMLElem22.addChild(xMLElem1);
/*     */     
/* 930 */     xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 931 */     xMLElem1.addChild((XMLElem)xMLChgSetElem2);
/*     */     
/* 933 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLElem getXMLMap() {
/* 940 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 947 */     return "ADSMODEL";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 954 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 962 */     return "MODEL_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 971 */     return "1.3";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSMODELABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */