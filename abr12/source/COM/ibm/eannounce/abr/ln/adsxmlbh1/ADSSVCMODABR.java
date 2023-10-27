/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
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
/*     */ public class ADSSVCMODABR
/*     */   extends XMLMQRoot
/*     */ {
/* 235 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SVCMOD_UPDATE"); static {
/* 236 */     XMLMAP.addChild((XMLElem)new XMLVMElem("SVCMOD_UPDATE", "1"));
/*     */     
/* 238 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 239 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 240 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 241 */     XMLMAP.addChild(new XMLElem("MODELENTITYTYPE", "ENTITYTYPE"));
/* 242 */     XMLMAP.addChild(new XMLElem("MODELENTITYID", "ENTITYID"));
/* 243 */     XMLMAP.addChild(new XMLElem("MACHTYPE", "SMACHTYPEATR"));
/* 244 */     XMLMAP.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */     
/* 246 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 247 */     XMLMAP.addChild(new XMLElem("CATEGORY", "SVCMODCATG"));
/* 248 */     XMLMAP.addChild(new XMLElem("SUBCATEGORY", "SVCMODSUBCATG"));
/* 249 */     XMLMAP.addChild(new XMLElem("GROUP", "SVCMODGRP"));
/* 250 */     XMLMAP.addChild(new XMLElem("SUBGROUP", "SVCMODSUBGRP"));
/*     */     
/* 252 */     XMLMAP.addChild(new XMLElem("PROJECT", "PROJCDNAM", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     XMLDistinctGroupElem xMLDistinctGroupElem = new XMLDistinctGroupElem(null, "PROJ", "D:SVCMODPROJA:D", true, true);
/*     */     
/* 263 */     XMLMAP.addChild((XMLElem)xMLDistinctGroupElem);
/* 264 */     xMLDistinctGroupElem.addChild(new XMLElem("DIVISION", "DIV", 1));
/*     */ 
/*     */     
/* 267 */     XMLMAP.addChild(new XMLElem("PRFTCTR", "PRFTCTR", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     XMLMAP.addChild(new XMLElem("PRODHIERCD", "BHPRODHIERCD"));
/*     */     
/* 274 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */ 
/*     */     
/* 277 */     XMLMAP.addChild(new XMLElem("NEC", "NEC"));
/*     */     
/* 279 */     XMLMAP.addChild(new XMLElem("TOS", "TOS"));
/* 280 */     XMLMAP.addChild(new XMLElem("SVCFFTYPE", "SVCFFTYPE"));
/*     */     
/* 282 */     XMLMAP.addChild(new XMLElem("OFERCONFIGTYPE", "OFERCONFIGTYPE"));
/* 283 */     XMLMAP.addChild(new XMLElem("ENDCUSTIDREQ", "ENDCUSTIDREQ"));
/* 284 */     XMLMAP.addChild(new XMLElem("FIXTERMPRIOD", "FIXTERMPRIOD"));
/* 285 */     XMLMAP.addChild(new XMLElem("PRORATEDOTCALLOW", "PRORATEDOTCALLOW"));
/* 286 */     XMLMAP.addChild(new XMLElem("SNGLLNEITEM", "SNGLLNEITEM"));
/* 287 */     XMLMAP.addChild(new XMLElem("SVCCHRGOPT", "SVCCHRGOPT", 2));
/* 288 */     XMLMAP.addChild(new XMLElem("TYPEOFWRK", "TYPEOFWRK"));
/* 289 */     XMLMAP.addChild(new XMLElem("UOMSI", "UOMSI", 2));
/* 290 */     XMLMAP.addChild(new XMLElem("UPGRADEYN", "UPGRADEYN"));
/* 291 */     XMLMAP.addChild(new XMLElem("WWOCCODE", "WWOCCODE"));
/*     */ 
/*     */ 
/*     */     
/* 295 */     XMLMAP.addChild(new XMLElem("UNSPSC", "UNSPSCCD"));
/* 296 */     XMLMAP.addChild(new XMLElem("UNUOM", "UNSPSCCDUOM"));
/*     */     
/* 298 */     XMLMAP.addChild(new XMLElem("PCTOFCMPLTINDC", "PCTOFCMPLTINDC"));
/*     */ 
/*     */     
/* 301 */     XMLMAP.addChild(new XMLElem("SOPRELEVANT", "SOPRELEVANT"));
/* 302 */     XMLMAP.addChild(new XMLElem("SOPTASKTYPE", "SOPTASKTYPE"));
/*     */     
/* 304 */     XMLMAP.addChild(new XMLElem("ALTID", "ALTID"));
/*     */ 
/*     */     
/* 307 */     XMLElem xMLElem8 = new XMLElem("LANGUAGELIST");
/* 308 */     XMLMAP.addChild(xMLElem8);
/*     */     
/* 310 */     XMLNLSElem xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 311 */     xMLElem8.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 313 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 315 */     xMLNLSElem2.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 316 */     xMLNLSElem2.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     xMLElem8 = new XMLElem("AVAILABILITYLIST");
/* 323 */     XMLMAP.addChild(xMLElem8);
/*     */     
/* 325 */     xMLElem8.addChild((XMLElem)new XMLSVCMODAVAILElembh1());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     XMLGroupElem xMLGroupElem8 = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
/* 334 */     XMLMAP.addChild((XMLElem)xMLGroupElem8);
/*     */     
/* 336 */     XMLElem xMLElem9 = new XMLElem("TAXCATEGORYELEMENT");
/* 337 */     xMLGroupElem8.addChild(xMLElem9);
/*     */     
/* 339 */     xMLElem9.addChild((XMLElem)new XMLActivityElem("TAXCATEGORYACTION"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     XMLElem xMLElem7 = new XMLElem("COUNTRYLIST");
/* 350 */     xMLElem9.addChild(xMLElem7);
/*     */     
/* 352 */     XMLChgSetElem xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 353 */     xMLElem7.addChild((XMLElem)xMLChgSetElem1);
/*     */ 
/*     */     
/* 356 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "TAXCNTRY", "COUNTRYACTION", 1));
/*     */     
/* 358 */     xMLElem9.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 367 */     XMLGroupElem xMLGroupElem9 = new XMLGroupElem(null, "SVCMODTAXRELEVANCE", "U:SVCMODTAXRELEVANCE");
/* 368 */     xMLElem9.addChild((XMLElem)xMLGroupElem9);
/* 369 */     xMLGroupElem9.addChild(new XMLElem("TAXCLASSIFICATION", "TAXCLS", 2));
/*     */     
/* 371 */     xMLElem9.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXCATGSLEORGA:D"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 377 */     XMLGroupElem xMLGroupElem7 = new XMLGroupElem("TAXCODELIST", "TAXGRP");
/* 378 */     XMLMAP.addChild((XMLElem)xMLGroupElem7);
/*     */     
/* 380 */     XMLElem xMLElem10 = new XMLElem("TAXCODEELEMENT");
/* 381 */     xMLGroupElem7.addChild(xMLElem10);
/*     */     
/* 383 */     xMLElem10.addChild((XMLElem)new XMLActivityElem("TAXCODEACTION"));
/* 384 */     xMLElem10.addChild(new XMLElem("TAXCODEDESCRIPTION", "DESC"));
/*     */ 
/*     */ 
/*     */     
/* 388 */     XMLElem xMLElem6 = new XMLElem("COUNTRYLIST");
/* 389 */     xMLElem10.addChild(xMLElem6);
/*     */     
/* 391 */     XMLChgSetElem xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 392 */     xMLElem6.addChild((XMLElem)xMLChgSetElem2);
/*     */     
/* 394 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */     
/* 396 */     xMLElem10.addChild(new XMLElem("TAXCODE", "TAXCD"));
/*     */     
/* 398 */     xMLElem10.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXGRPSLEORGA:D"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 403 */     XMLGroupElem xMLGroupElem6 = new XMLGroupElem("CATATTRIBUTELIST", "CATDATA");
/* 404 */     XMLMAP.addChild((XMLElem)xMLGroupElem6);
/*     */     
/* 406 */     XMLNLSElem xMLNLSElem3 = new XMLNLSElem("CATATTRIBUTEELEMENT");
/* 407 */     xMLGroupElem6.addChild((XMLElem)xMLNLSElem3);
/*     */     
/* 409 */     xMLNLSElem3.addChild((XMLElem)new XMLActivityElem("CATATTRIBUTEACTION"));
/*     */     
/* 411 */     xMLNLSElem3.addChild(new XMLElem("CATATTRIBUTE", "DAATTRIBUTECODE"));
/* 412 */     xMLNLSElem3.addChild(new XMLElem("NLSID", "NLSID"));
/* 413 */     xMLNLSElem3.addChild(new XMLElem("CATATTRIUBTEVALUE", "DAATTRIBUTEVALUE"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 418 */     xMLGroupElem6 = new XMLGroupElem("UNBUNDCOMPLIST", "REVUNBUNDCOMP");
/* 419 */     XMLMAP.addChild((XMLElem)xMLGroupElem6);
/*     */     
/* 421 */     XMLElem xMLElem11 = new XMLElem("UNBUNDCOMPELEMENT");
/* 422 */     xMLGroupElem6.addChild(xMLElem11);
/*     */     
/* 424 */     xMLElem11.addChild((XMLElem)new XMLActivityElem("UNBUNDCOMPACTION"));
/*     */     
/* 426 */     xMLElem11.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 427 */     xMLElem11.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 428 */     xMLElem11.addChild(new XMLElem("UNBUNDCOMPID", "UNBUNDCOMPID"));
/*     */ 
/*     */ 
/*     */     
/* 432 */     XMLGroupElem xMLGroupElem10 = new XMLGroupElem(null, "SVCMODUNBUND", "U:SVCMODUNBUND");
/* 433 */     xMLElem11.addChild((XMLElem)xMLGroupElem10);
/* 434 */     xMLGroupElem10.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 435 */     xMLGroupElem10.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/* 436 */     xMLGroupElem10.addChild(new XMLElem("UNBUNDTYPE", "UNBUNDTYPE"));
/*     */ 
/*     */ 
/*     */     
/* 440 */     xMLGroupElem6 = new XMLGroupElem("CHRGCOMPLIST", "CHRGCOMP", "D:SVCMODCHRGCOMP:D");
/* 441 */     XMLMAP.addChild((XMLElem)xMLGroupElem6);
/*     */     
/* 443 */     XMLElem xMLElem12 = new XMLElem("CHRGCOMPELEMENT");
/* 444 */     xMLGroupElem6.addChild(xMLElem12);
/*     */ 
/*     */ 
/*     */     
/* 448 */     xMLElem12.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 449 */     xMLElem12.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 450 */     xMLElem12.addChild(new XMLElem("CHRGCOMPID", "CHRGCOMPID"));
/* 451 */     xMLElem12.addChild(new XMLElem("BHACCTASGNGRP", "BHACCTASGNGRP", 2));
/* 452 */     xMLElem12.addChild(new XMLElem("REFSELCONDTN", "REFSELCONDTN"));
/* 453 */     xMLElem12.addChild(new XMLElem("SVCCHRGOPT", "SVCCHRGOPT", 2));
/*     */     
/* 455 */     xMLElem12.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 456 */     xMLElem12.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/*     */ 
/*     */     
/* 459 */     XMLElem xMLElem5 = new XMLElem("LANGUAGELIST");
/* 460 */     xMLElem12.addChild(xMLElem5);
/*     */ 
/*     */     
/* 463 */     xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 464 */     xMLElem5.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 466 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 467 */     xMLNLSElem2.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 468 */     xMLNLSElem2.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */     
/* 471 */     XMLGroupElem xMLGroupElem5 = new XMLGroupElem("PRICEPOINTLIST", "PRCPT", "D:CHRGCOMPPRCPT:D", true);
/* 472 */     xMLElem12.addChild((XMLElem)xMLGroupElem5);
/*     */     
/* 474 */     XMLElem xMLElem13 = new XMLElem("PRICEPOINTELEMENT");
/* 475 */     xMLGroupElem5.addChild(xMLElem13);
/*     */ 
/*     */ 
/*     */     
/* 479 */     xMLElem13.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 480 */     xMLElem13.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 481 */     xMLElem13.addChild(new XMLElem("PRCPTID", "PRCPTID"));
/* 482 */     xMLElem13.addChild(new XMLElem("PRCMETH", "PRCMETH", 2));
/*     */     
/* 484 */     XMLElem xMLElem4 = new XMLElem("LANGUAGELIST");
/* 485 */     xMLElem13.addChild(xMLElem4);
/*     */ 
/*     */     
/* 488 */     xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 489 */     xMLElem4.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 491 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 492 */     xMLNLSElem2.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 493 */     xMLNLSElem2.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     XMLGroupElem xMLGroupElem4 = new XMLGroupElem("CNTRYEFFLIST", "CNTRYEFF", "D:PRCPTCNTRYEFF:D");
/* 508 */     xMLElem13.addChild((XMLElem)xMLGroupElem4);
/* 509 */     XMLElem xMLElem14 = new XMLElem("CNTRYEFFELEMENT");
/* 510 */     xMLGroupElem4.addChild(xMLElem14);
/* 511 */     xMLElem14.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 512 */     xMLElem14.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/*     */ 
/*     */     
/* 515 */     xMLElem14.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 516 */     xMLElem14.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */ 
/*     */     
/* 519 */     XMLElem xMLElem3 = new XMLElem("COUNTRYLISTLIST");
/* 520 */     xMLElem14.addChild(xMLElem3);
/*     */     
/* 522 */     XMLChgSetElem xMLChgSetElem3 = new XMLChgSetElem("COUNTRYLISTELEMENT");
/* 523 */     xMLElem3.addChild((XMLElem)xMLChgSetElem3);
/*     */     
/* 525 */     xMLChgSetElem3.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 529 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem("REFCVMSPECLIST", "CVMSPEC", "D:PRCPTCVMSPEC:D", true);
/* 530 */     xMLElem13.addChild((XMLElem)xMLGroupElem3);
/* 531 */     XMLElem xMLElem15 = new XMLElem("REFCVMSPECELEMENT");
/* 532 */     xMLGroupElem3.addChild(xMLElem15);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 538 */     XMLGroupElem xMLGroupElem11 = new XMLGroupElem(null, "PRCPTCVMSPEC", "U:PRCPTCVMSPEC", false, 2);
/* 539 */     xMLElem15.addChild((XMLElem)xMLGroupElem11);
/* 540 */     xMLGroupElem11.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 541 */     xMLGroupElem11.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/* 542 */     xMLElem15.addChild(new XMLElem("REFCVMSPECENTITYTYPE", "ENTITYTYPE"));
/* 543 */     xMLElem15.addChild(new XMLElem("REFCVMSPECENTITYID", "ENTITYID"));
/*     */     
/* 545 */     xMLElem15.addChild(new XMLElem("REFCVMSPECTYPE", "SPECTYPE"));
/*     */ 
/*     */     
/* 548 */     xMLElem15.addChild((XMLElem)new XMLSVCMODCVMSPECElem("REFCVMSPECID"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 555 */     xMLGroupElem3 = new XMLGroupElem("CHARVALUEMETRICLIST", "CVM", "D:CHRGCOMPCVM:D");
/* 556 */     xMLElem12.addChild((XMLElem)xMLGroupElem3);
/*     */     
/* 558 */     XMLElem xMLElem16 = new XMLElem("CHARVALUEMETRICELEMENT");
/* 559 */     xMLGroupElem3.addChild(xMLElem16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 569 */     xMLElem16.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 570 */     xMLElem16.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */     
/* 572 */     xMLElem16.addChild(new XMLElem("CVMTYPE", "CVMTYPE"));
/* 573 */     xMLElem16.addChild(new XMLElem("VMID", "VMID"));
/*     */ 
/*     */ 
/*     */     
/* 577 */     xMLElem16.addChild(new XMLElem("CHARACID", "CHARACID"));
/*     */     
/* 579 */     xMLElem16.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 580 */     xMLElem16.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/* 581 */     XMLElem xMLElem2 = new XMLElem("LANGUAGELIST");
/* 582 */     xMLElem16.addChild(xMLElem2);
/*     */ 
/*     */     
/* 585 */     xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 586 */     xMLElem2.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 588 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 589 */     xMLNLSElem2.addChild(new XMLElem("SHRTNAM", "SHRTNAM"));
/*     */     
/* 591 */     xMLElem16.addChild(new XMLElem("CVMDATATYPE", "CVMDATATYPE", 2));
/* 592 */     xMLElem16.addChild(new XMLElem("CVMSELTYPE", "CVMSELTYPE", 2));
/*     */     
/* 594 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem("CVMSPECLIST", "CVMSPEC", "D:CVMCVMSPEC:D");
/* 595 */     xMLElem16.addChild((XMLElem)xMLGroupElem2);
/*     */     
/* 597 */     XMLElem xMLElem17 = new XMLElem("CVMSPECELEMENT");
/*     */     
/* 599 */     xMLGroupElem2.addChild(xMLElem17);
/*     */ 
/*     */ 
/*     */     
/* 603 */     xMLElem17.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 604 */     xMLElem17.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */ 
/*     */ 
/*     */     
/* 608 */     xMLElem17.addChild((XMLElem)new XMLSVCMODCVMSPECElem("CVMSPECID", "CVMTYPE", "CVM", "U:CVMCVMSPEC:U"));
/*     */     
/* 610 */     xMLElem17.addChild(new XMLElem("EFFECTIVEDATE", "EFFECTIVEDATE"));
/* 611 */     xMLElem17.addChild(new XMLElem("ENDDATE", "ENDDATE"));
/*     */ 
/*     */     
/* 614 */     XMLNLSElem xMLNLSElem1 = new XMLNLSElem("LANGUAGELIST");
/* 615 */     xMLElem17.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 617 */     xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 618 */     xMLNLSElem1.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 620 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 621 */     xMLNLSElem2.addChild(new XMLElem("SHRTNAM", "SHRTNAM"));
/*     */ 
/*     */     
/* 624 */     xMLElem17.addChild(new XMLElem("LOWLIMT", "LOWLIMT"));
/*     */     
/* 626 */     xMLElem17.addChild(new XMLElem("HIGHLIMT", "HGHLIMT"));
/* 627 */     xMLElem17.addChild(new XMLElem("SPECTYPE", "SPECTYPE", 2));
/* 628 */     xMLElem17.addChild(new XMLElem("RNGETBLOPT", "RNGETBLOPT", 2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 645 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem("SVCEXECTMELIST", "SVCEXECTME");
/* 646 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 648 */     XMLElem xMLElem18 = new XMLElem("SVCEXECTMEELEMENT");
/* 649 */     xMLGroupElem1.addChild(xMLElem18);
/*     */ 
/*     */ 
/*     */     
/* 653 */     xMLElem18.addChild(new XMLElem("COMPETENCECD", "COMPETENCECD", 2));
/* 654 */     xMLElem18.addChild(new XMLElem("HR", "HR"));
/* 655 */     xMLElem18.addChild(new XMLElem("IMPLEMENTRBN", "IMPLEMENTRBN", 2));
/* 656 */     xMLElem18.addChild(new XMLElem("MACHTYPEATR", "SMACHTYPEATR"));
/* 657 */     xMLElem18.addChild(new XMLElem("MODELATR", "MODELATR"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 662 */     xMLGroupElem1 = new XMLGroupElem("SVCSEOREFLIST", "SVCSEO", "D:SVCMODSVCSEOREF:D", true);
/* 663 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 665 */     XMLElem xMLElem19 = new XMLElem("SVCSEOREFELEMENT");
/* 666 */     xMLGroupElem1.addChild(xMLElem19);
/*     */ 
/*     */ 
/*     */     
/* 670 */     xMLElem19.addChild(new XMLElem("SVCSEOENTITYTYPE", "ENTITYTYPE"));
/* 671 */     xMLElem19.addChild(new XMLElem("SVCSEOENTITYID", "ENTITYID"));
/* 672 */     xMLElem19.addChild(new XMLElem("SEOID", "SEOID"));
/*     */ 
/*     */ 
/*     */     
/* 676 */     xMLGroupElem1 = new XMLGroupElem("SVCMODREFLIST", "SVCMOD", "D:SVCMODSVCMOD:D");
/* 677 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 679 */     XMLElem xMLElem20 = new XMLElem("SVCMODREFELEMENT");
/* 680 */     xMLGroupElem1.addChild(xMLElem20);
/*     */ 
/*     */ 
/*     */     
/* 684 */     xMLElem20.addChild(new XMLElem("SVCMODEENTITYTYPE", "ENTITYTYPE"));
/* 685 */     xMLElem20.addChild(new XMLElem("SVCMODENTITYID", "ENTITYID"));
/* 686 */     xMLElem20.addChild(new XMLElem("MACHTYPEATR", "SMACHTYPEATR"));
/* 687 */     xMLElem20.addChild(new XMLElem("MODELATR", "MODELATR"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 693 */     xMLGroupElem1 = new XMLGroupElem("SVCSEOLIST", "SVCSEO", "D:SVCMODSVCSEO:D", true);
/* 694 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 696 */     XMLElem xMLElem21 = new XMLElem("SVCSEOELEMENT");
/* 697 */     xMLGroupElem1.addChild(xMLElem21);
/*     */ 
/*     */ 
/*     */     
/* 701 */     xMLElem21.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 702 */     xMLElem21.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 703 */     xMLElem21.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 704 */     xMLElem21.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 705 */     xMLElem21.addChild(new XMLElem("SEOID", "SEOID"));
/*     */     
/* 707 */     xMLElem21.addChild(new XMLElem("BHPRDHIERCD", "BHPRODHIERCD"));
/* 708 */     xMLElem21.addChild(new XMLElem("PRFTCTR", "PRFTCTR"));
/* 709 */     xMLElem21.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/*     */ 
/*     */     
/* 712 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 713 */     xMLElem21.addChild(xMLElem1);
/*     */ 
/*     */     
/* 716 */     xMLNLSElem2 = new XMLNLSElem("LANGUAGEELEMENT");
/* 717 */     xMLElem1.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 719 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/* 720 */     xMLNLSElem2.addChild(new XMLElem("INVNAME", "INVNAME"));
/* 721 */     xMLNLSElem2.addChild(new XMLElem("MKTGNAME", "MKTGNAME"));
/*     */ 
/*     */     
/* 724 */     xMLElem1 = new XMLElem("AVAILABILITYLIST");
/* 725 */     xMLElem21.addChild(xMLElem1);
/*     */     
/* 727 */     xMLElem1.addChild((XMLElem)new XMLSVCSEOAVAILElem());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 732 */     xMLElem1 = new XMLElem("PRCPTLIST");
/* 733 */     xMLElem21.addChild(xMLElem1);
/* 734 */     xMLElem1.addChild((XMLElem)new XMLSVCMODPRCPTElem());
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
/*     */   public XMLElem getXMLMap() {
/* 746 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 752 */     return "ADSSVCMOD";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 757 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 763 */     return "SVCMOD_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 772 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSSVCMODABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */