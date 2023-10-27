/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCATAElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLDistinctGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLFEATQTYElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLLSEOAVAILElembh1;
/*     */ import COM.ibm.eannounce.abr.util.XMLLSEOActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLLSEOWARRGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLLSEOWWSEOLangElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMultiFlagElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNLSElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLStatusElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLVMElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLZCONFElem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSLSEOABR
/*     */   extends XMLMQRoot
/*     */ {
/* 156 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SEO_UPDATE"); static {
/* 157 */     XMLMAP.addChild((XMLElem)new XMLVMElem("SEO_UPDATE", "1"));
/*     */     
/* 159 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 160 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 161 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 162 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 163 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 164 */     XMLMAP.addChild(new XMLElem("SEOID", "SEOID"));
/*     */     
/* 166 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 167 */     XMLMAP.addChild(new XMLElem("PRFCNTR", "PRFTCTR", 1));
/* 168 */     XMLMAP.addChild(new XMLElem("PRICEDIND", "PRCINDC"));
/* 169 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/* 170 */     XMLMAP.addChild(new XMLElem("PRDHIERCD", "BHPRODHIERCD"));
/* 171 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/* 172 */     XMLMAP.addChild(new XMLElem("UPCCD", "UPCCD"));
/*     */     
/* 174 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "WWSEO");
/* 175 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/* 176 */     xMLGroupElem1.addChild(new XMLElem("SWROYALBEARING", "SWROYALBEARING"));
/* 177 */     xMLGroupElem1.addChild(new XMLElem("SPECIALBID", "SPECBID"));
/* 178 */     xMLGroupElem1.addChild(new XMLElem("SEOORDERCODE", "SEOORDERCODE"));
/* 179 */     xMLGroupElem1.addChild(new XMLElem("WWENTITYTYPE", "ENTITYTYPE"));
/* 180 */     xMLGroupElem1.addChild(new XMLElem("WWENTITYID", "ENTITYID"));
/* 181 */     xMLGroupElem1.addChild(new XMLElem("WWSEOID", "SEOID"));
/* 182 */     xMLGroupElem1.addChild(new XMLElem("PROJECT", "PROJCDNAM", 1));
/*     */ 
/*     */     
/* 185 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "WEIGHTNDIMN");
/* 186 */     xMLGroupElem1.addChild((XMLElem)xMLGroupElem2);
/* 187 */     xMLGroupElem2.addChild(new XMLElem("WGHTMTRIC", "WGHTMTRIC|WGHTMTRICUNIT"));
/*     */ 
/*     */ 
/*     */     
/* 191 */     XMLDistinctGroupElem xMLDistinctGroupElem1 = new XMLDistinctGroupElem(null, "SGMNTACRNYM", null, true, true);
/* 192 */     xMLGroupElem1.addChild((XMLElem)xMLDistinctGroupElem1);
/* 193 */     xMLDistinctGroupElem1.addChild(new XMLElem("DIVISION", "DIV", 1));
/*     */     
/* 195 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem(null, "MODEL", "U:WWSEOLSEO:U:WWSEO:U:MODELWWSEO:U");
/* 196 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/* 197 */     xMLGroupElem3.addChild(new XMLElem("PARENTENTITYTYPE", "ENTITYTYPE"));
/* 198 */     xMLGroupElem3.addChild(new XMLElem("PARENTENTITYID", "ENTITYID"));
/* 199 */     xMLGroupElem3.addChild(new XMLElem("PARENTMODEL", "MODELATR"));
/* 200 */     xMLGroupElem3.addChild(new XMLElem("PARENTMACHTYPE", "MACHTYPEATR", 1));
/*     */     
/* 202 */     xMLGroupElem3.addChild(new XMLElem("CATEGORY", "COFCAT"));
/*     */     
/* 204 */     xMLGroupElem3.addChild(new XMLElem("SUBCATEGORY", "COFSUBCAT", 2));
/* 205 */     xMLGroupElem3.addChild(new XMLElem("GROUP", "COFGRP"));
/* 206 */     xMLGroupElem3.addChild(new XMLElem("SUBGROUP", "COFSUBGRP"));
/*     */     
/* 208 */     xMLGroupElem1 = new XMLGroupElem(null, "WWSEO");
/* 209 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/* 210 */     xMLGroupElem1.addChild(new XMLElem("WHITEBOXINDC", "WHITEBOXINDC"));
/*     */ 
/*     */ 
/*     */     
/* 214 */     xMLGroupElem1.addChild(new XMLElem("UNSPSC", "UNSPSCCD"));
/* 215 */     xMLGroupElem1.addChild(new XMLElem("UNUOM", "UNSPSCCDUOM"));
/*     */     
/* 217 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 218 */     XMLMAP.addChild(xMLElem1);
/*     */     
/* 220 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 221 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 223 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 224 */     xMLNLSElem.addChild(new XMLElem("MKTGDESC", "LSEOMKTGDESC"));
/*     */     
/* 226 */     xMLGroupElem3 = new XMLGroupElem(null, "WWSEO");
/* 227 */     xMLNLSElem.addChild((XMLElem)xMLGroupElem3);
/* 228 */     xMLGroupElem3.addChild((XMLElem)new XMLLSEOWWSEOLangElem("MKTGNAME", "MKTGNAME"));
/* 229 */     xMLGroupElem3.addChild((XMLElem)new XMLLSEOWWSEOLangElem("INVNAME", "PRCFILENAM"));
/*     */ 
/*     */     
/* 232 */     XMLElem xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 233 */     XMLMAP.addChild(xMLElem2);
/*     */     
/* 235 */     xMLElem2.addChild((XMLElem)new XMLLSEOAVAILElembh1());
/*     */ 
/*     */     
/* 238 */     XMLGroupElem xMLGroupElem8 = new XMLGroupElem("IMAGELIST", "IMG");
/* 239 */     XMLMAP.addChild((XMLElem)xMLGroupElem8);
/*     */     
/* 241 */     XMLElem xMLElem7 = new XMLElem("IMAGEELEMENT");
/* 242 */     xMLGroupElem8.addChild(xMLElem7);
/*     */     
/* 244 */     xMLElem7.addChild((XMLElem)new XMLActivityElem("IMAGEACTION"));
/* 245 */     xMLElem7.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 246 */     xMLElem7.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 247 */     xMLElem7.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 248 */     xMLElem7.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 249 */     xMLElem7.addChild(new XMLElem("PUBTO", "PUBTO"));
/* 250 */     xMLElem7.addChild(new XMLElem("IMAGEDESCRIPTION", "IMGDESC"));
/* 251 */     xMLElem7.addChild(new XMLElem("MARKETINGIMAGEFILENAME", "MKTGIMGFILENAM"));
/*     */ 
/*     */     
/* 254 */     XMLElem xMLElem6 = new XMLElem("COUNTRYLIST");
/* 255 */     xMLElem7.addChild(xMLElem6);
/*     */     
/* 257 */     XMLChgSetElem xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 258 */     xMLElem6.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 260 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 264 */     XMLGroupElem xMLGroupElem7 = new XMLGroupElem("MMLIST", "MM");
/* 265 */     XMLMAP.addChild((XMLElem)xMLGroupElem7);
/*     */     
/* 267 */     XMLElem xMLElem8 = new XMLElem("MMELEMENT");
/* 268 */     xMLGroupElem7.addChild(xMLElem8);
/*     */     
/* 270 */     xMLElem8.addChild((XMLElem)new XMLActivityElem("MMACTION"));
/* 271 */     xMLElem8.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 272 */     xMLElem8.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 273 */     xMLElem8.addChild(new XMLElem("STATUS", "MMSTATUS", 1));
/* 274 */     xMLElem8.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 275 */     xMLElem8.addChild(new XMLElem("PUBTO", "PUBTO"));
/*     */ 
/*     */     
/* 278 */     XMLElem xMLElem5 = new XMLElem("MSGLIST");
/* 279 */     xMLElem8.addChild(xMLElem5);
/*     */     
/* 281 */     xMLNLSElem = new XMLNLSElem("MSGELEMENT");
/* 282 */     xMLElem5.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 284 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 285 */     xMLNLSElem.addChild(new XMLElem("SHRTMKTGMSG", "SHRTMKTGMSG"));
/* 286 */     xMLNLSElem.addChild(new XMLElem("LONGMKTGMSG", "LONGMKTGMSG"));
/*     */     
/* 288 */     xMLElem5 = new XMLElem("AUDIENCELIST");
/* 289 */     xMLElem8.addChild(xMLElem5);
/*     */     
/* 291 */     xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 292 */     xMLElem5.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 294 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTION"));
/*     */     
/* 296 */     xMLElem5 = new XMLElem("COUNTRYLIST");
/* 297 */     xMLElem8.addChild(xMLElem5);
/*     */     
/* 299 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 300 */     xMLElem5.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 302 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 306 */     XMLGroupElem xMLGroupElem6 = new XMLGroupElem("FBLIST", "FB");
/* 307 */     XMLMAP.addChild((XMLElem)xMLGroupElem6);
/*     */     
/* 309 */     XMLElem xMLElem9 = new XMLElem("FBELEMENT");
/* 310 */     xMLGroupElem6.addChild(xMLElem9);
/*     */     
/* 312 */     xMLElem9.addChild((XMLElem)new XMLActivityElem("FBACTION"));
/* 313 */     xMLElem9.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 314 */     xMLElem9.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 315 */     xMLElem9.addChild(new XMLElem("STATUS", "FBSTATUS", 1));
/* 316 */     xMLElem9.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 317 */     xMLElem9.addChild(new XMLElem("PUBTO", "PUBTO"));
/*     */ 
/*     */     
/* 320 */     XMLElem xMLElem4 = new XMLElem("FBSTMTLIST");
/* 321 */     xMLElem9.addChild(xMLElem4);
/*     */     
/* 323 */     xMLNLSElem = new XMLNLSElem("FBSTMTELEMENT");
/* 324 */     xMLElem4.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 326 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 328 */     xMLNLSElem.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 329 */     xMLNLSElem.addChild(new XMLElem("FBSTMT", "FBSTMT"));
/*     */     
/* 331 */     xMLElem4 = new XMLElem("AUDIENCELIST");
/* 332 */     xMLElem9.addChild(xMLElem4);
/*     */     
/* 334 */     xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 335 */     xMLElem4.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 337 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTION"));
/*     */     
/* 339 */     xMLElem4 = new XMLElem("COUNTRYLIST");
/* 340 */     xMLElem9.addChild(xMLElem4);
/*     */     
/* 342 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 343 */     xMLElem4.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 345 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 349 */     xMLElem4 = new XMLElem("AUDIENCELIST");
/* 350 */     XMLMAP.addChild(xMLElem4);
/* 351 */     XMLChgSetElem xMLChgSetElem2 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 352 */     xMLElem4.addChild((XMLElem)xMLChgSetElem2);
/* 353 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "AUDIEN", "AUDIENCEACTION"));
/*     */ 
/*     */     
/* 356 */     XMLMAP.addChild((XMLElem)new XMLZCONFElem());
/*     */ 
/*     */     
/* 359 */     xMLElem4 = new XMLElem("CATALOGOVERRIDELIST");
/* 360 */     XMLMAP.addChild(xMLElem4);
/* 361 */     xMLElem4.addChild((XMLElem)new XMLCATAElem());
/*     */ 
/*     */     
/* 364 */     XMLGroupElem xMLGroupElem5 = new XMLGroupElem("CATATTRIBUTELIST", "CATDATA");
/* 365 */     XMLMAP.addChild((XMLElem)xMLGroupElem5);
/* 366 */     XMLElem xMLElem10 = new XMLElem("CATATTRIBUTEELEMENT");
/* 367 */     xMLGroupElem5.addChild(xMLElem10);
/* 368 */     xMLElem10.addChild((XMLElem)new XMLActivityElem("CATATTRIBUTEACTION"));
/* 369 */     xMLElem10.addChild(new XMLElem("CATATTRIBUTE", "DAATTRIBUTECODE"));
/* 370 */     xMLElem10.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 372 */     xMLElem10.addChild(new XMLElem("CATATTRIUBTEVALUE", "DAATTRIBUTEVALUE"));
/*     */ 
/*     */ 
/*     */     
/* 376 */     XMLDistinctGroupElem xMLDistinctGroupElem2 = new XMLDistinctGroupElem("FEATURELIST", "FEATURE|SWFEATURE", null, true);
/* 377 */     XMLMAP.addChild((XMLElem)xMLDistinctGroupElem2);
/* 378 */     XMLElem xMLElem11 = new XMLElem("FEATUREELEMENT");
/* 379 */     xMLDistinctGroupElem2.addChild(xMLElem11);
/* 380 */     xMLElem11.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 381 */     xMLElem11.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 382 */     xMLElem11.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 383 */     xMLElem11.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/* 384 */     xMLElem11.addChild((XMLElem)new XMLFEATQTYElem("QTY", "CONFQTY"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 451 */     XMLLSEOWARRGroupElem xMLLSEOWARRGroupElem1 = new XMLLSEOWARRGroupElem("WARRLIST", "WARR");
/* 452 */     XMLMAP.addChild((XMLElem)xMLLSEOWARRGroupElem1);
/*     */     
/* 454 */     XMLElem xMLElem12 = new XMLElem("WARRELEMENT");
/* 455 */     xMLLSEOWARRGroupElem1.addChild(xMLElem12);
/*     */     
/* 457 */     xMLElem12.addChild((XMLElem)new XMLLSEOActivityElem("WARRACTION"));
/* 458 */     xMLElem12.addChild(new XMLElem("WARRENTITYTYPE", "ENTITYTYPE"));
/* 459 */     xMLElem12.addChild(new XMLElem("WARRENTITYID", "ENTITYID"));
/* 460 */     xMLElem12.addChild(new XMLElem("WARRID", "WARRID"));
/*     */     
/* 462 */     XMLLSEOWARRGroupElem xMLLSEOWARRGroupElem2 = new XMLLSEOWARRGroupElem(null, "MODELWARR|PRODSTRUCTWARR", "U:MODELWARR|U:PRODSTRUCTWARR", "SPECIAL");
/* 463 */     xMLElem12.addChild((XMLElem)xMLLSEOWARRGroupElem2);
/* 464 */     xMLLSEOWARRGroupElem2.addChild(new XMLElem("PUBFROM", "EFFECTIVEDATE"));
/* 465 */     xMLLSEOWARRGroupElem2.addChild(new XMLElem("PUBTO", "ENDDATE"));
/* 466 */     xMLLSEOWARRGroupElem2.addChild(new XMLElem("DEFWARR", "DEFWARR"));
/* 467 */     XMLElem xMLElem3 = new XMLElem("COUNTRYLIST");
/* 468 */     xMLElem12.addChild(xMLElem3);
/*     */     
/* 470 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 471 */     xMLElem3.addChild((XMLElem)xMLChgSetElem1);
/* 472 */     XMLLSEOWARRGroupElem xMLLSEOWARRGroupElem3 = new XMLLSEOWARRGroupElem(null, "MODELWARR|PRODSTRUCTWARR", "U:MODELWARR|U:PRODSTRUCTWARR");
/* 473 */     xMLChgSetElem1.addChild((XMLElem)xMLLSEOWARRGroupElem3);
/*     */     
/* 475 */     xMLLSEOWARRGroupElem3.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 480 */     XMLGroupElem xMLGroupElem4 = new XMLGroupElem("REPLLSEOLIST", "LSEO", "D:LSEOREPLLSEO:D", true);
/* 481 */     XMLMAP.addChild((XMLElem)xMLGroupElem4);
/*     */     
/* 483 */     XMLElem xMLElem13 = new XMLElem("REPLLSEOELEMENT");
/* 484 */     xMLGroupElem4.addChild(xMLElem13);
/*     */     
/* 486 */     xMLElem13.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*     */     
/* 488 */     xMLElem13.addChild(new XMLElem("SEOENTITYTYPE", "ENTITYTYPE"));
/*     */     
/* 490 */     xMLElem13.addChild(new XMLElem("SEOENTITYID", "ENTITYID"));
/*     */     
/* 492 */     xMLElem13.addChild(new XMLElem("SEOID", "SEOID"));
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
/* 503 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 509 */     return "ADSLSEO";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVeName2() {
/* 514 */     return "ADSLSEO2";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 519 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 525 */     return "SEO_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 534 */     return "$Revision: 1.39 $";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mergeLists(ADSABRSTATUS paramADSABRSTATUS, EntityList paramEntityList1, EntityList paramEntityList2) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
/* 684 */     paramADSABRSTATUS.addDebug("Entered ADSLSEOABR call COM.ibm.eannounce.objects.EntityList.mergeLists");
/*     */     
/* 686 */     EntityList.mergeLists(paramEntityList1, paramEntityList2);
/* 687 */     paramADSABRSTATUS.addDebug("mergeLists:: after merge Extract " + PokUtils.outputList(paramEntityList1));
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSLSEOABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */