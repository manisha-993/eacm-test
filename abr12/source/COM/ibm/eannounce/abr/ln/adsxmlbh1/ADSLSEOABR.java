/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCATAElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLDistinctGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLDistinctGroupLnElem;
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
/*     */ public class ADSLSEOABR
/*     */   extends XMLMQRoot
/*     */ {
/* 155 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("SEO_UPDATE"); static {
/* 156 */     XMLMAP.addChild((XMLElem)new XMLVMElem("SEO_UPDATE", "1"));
/*     */     
/* 158 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 159 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 160 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 161 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 162 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 163 */     XMLMAP.addChild(new XMLElem("SEOID", "SEOID"));
/*     */     
/* 165 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 166 */     XMLMAP.addChild(new XMLElem("PRFCNTR", "PRFTCTR", 1));
/* 167 */     XMLMAP.addChild(new XMLElem("PRICEDIND", "PRCINDC"));
/* 168 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/* 169 */     XMLMAP.addChild(new XMLElem("PRDHIERCD", "BHPRODHIERCD"));
/* 170 */     XMLMAP.addChild(new XMLElem("ACCTASGNGRP", "BHACCTASGNGRP", 2));
/* 171 */     XMLMAP.addChild(new XMLElem("UPCCD", "UPCCD"));
/*     */     
/* 173 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "WWSEO");
/* 174 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/* 175 */     xMLGroupElem1.addChild(new XMLElem("SWROYALBEARING", "SWROYALBEARING"));
/* 176 */     xMLGroupElem1.addChild(new XMLElem("SPECIALBID", "SPECBID"));
/* 177 */     xMLGroupElem1.addChild(new XMLElem("SEOORDERCODE", "SEOORDERCODE"));
/* 178 */     xMLGroupElem1.addChild(new XMLElem("WWENTITYTYPE", "ENTITYTYPE"));
/* 179 */     xMLGroupElem1.addChild(new XMLElem("WWENTITYID", "ENTITYID"));
/* 180 */     xMLGroupElem1.addChild(new XMLElem("WWSEOID", "SEOID"));
/* 181 */     xMLGroupElem1.addChild(new XMLElem("PROJECT", "PROJCDNAM", 1));
/*     */ 
/*     */     
/* 184 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "WEIGHTNDIMN");
/* 185 */     xMLGroupElem1.addChild((XMLElem)xMLGroupElem2);
/* 186 */     xMLGroupElem2.addChild(new XMLElem("WGHTMTRIC", "WGHTMTRIC|WGHTMTRICUNIT"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     XMLDistinctGroupLnElem xMLDistinctGroupLnElem = new XMLDistinctGroupLnElem(null, "PROJ", null, true, true);
/* 193 */     xMLGroupElem1.addChild((XMLElem)xMLDistinctGroupLnElem);
/* 194 */     xMLDistinctGroupLnElem.addChild(new XMLElem("DIVISION", "DIV", 1));
/*     */     
/* 196 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem(null, "MODEL", "U:WWSEOLSEO:U:WWSEO:U:MODELWWSEO:U");
/* 197 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/* 198 */     xMLGroupElem3.addChild(new XMLElem("PARENTENTITYTYPE", "ENTITYTYPE"));
/* 199 */     xMLGroupElem3.addChild(new XMLElem("PARENTENTITYID", "ENTITYID"));
/* 200 */     xMLGroupElem3.addChild(new XMLElem("PARENTMODEL", "MODELATR"));
/* 201 */     xMLGroupElem3.addChild(new XMLElem("PARENTMACHTYPE", "MACHTYPEATR", 1));
/*     */     
/* 203 */     xMLGroupElem3.addChild(new XMLElem("CATEGORY", "COFCAT"));
/*     */     
/* 205 */     xMLGroupElem3.addChild(new XMLElem("SUBCATEGORY", "COFSUBCAT", 2));
/* 206 */     xMLGroupElem3.addChild(new XMLElem("GROUP", "COFGRP"));
/* 207 */     xMLGroupElem3.addChild(new XMLElem("SUBGROUP", "COFSUBGRP"));
/*     */     
/* 209 */     xMLGroupElem1 = new XMLGroupElem(null, "WWSEO");
/* 210 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/* 211 */     xMLGroupElem1.addChild(new XMLElem("WHITEBOXINDC", "WHITEBOXINDC"));
/*     */ 
/*     */ 
/*     */     
/* 215 */     xMLGroupElem1.addChild(new XMLElem("UNSPSC", "UNSPSCCD"));
/* 216 */     xMLGroupElem1.addChild(new XMLElem("UNUOM", "UNSPSCCDUOM"));
/*     */     
/* 218 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 219 */     XMLMAP.addChild(xMLElem1);
/*     */     
/* 221 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 222 */     xMLElem1.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 224 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 225 */     xMLNLSElem.addChild(new XMLElem("MKTGDESC", "LSEOMKTGDESC"));
/*     */     
/* 227 */     xMLGroupElem3 = new XMLGroupElem(null, "WWSEO");
/* 228 */     xMLNLSElem.addChild((XMLElem)xMLGroupElem3);
/* 229 */     xMLGroupElem3.addChild((XMLElem)new XMLLSEOWWSEOLangElem("MKTGNAME", "MKTGNAME"));
/* 230 */     xMLGroupElem3.addChild((XMLElem)new XMLLSEOWWSEOLangElem("INVNAME", "PRCFILENAM"));
/*     */ 
/*     */     
/* 233 */     XMLElem xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 234 */     XMLMAP.addChild(xMLElem2);
/*     */     
/* 236 */     xMLElem2.addChild((XMLElem)new XMLLSEOAVAILElembh1());
/*     */ 
/*     */     
/* 239 */     XMLGroupElem xMLGroupElem8 = new XMLGroupElem("IMAGELIST", "IMG");
/* 240 */     XMLMAP.addChild((XMLElem)xMLGroupElem8);
/*     */     
/* 242 */     XMLElem xMLElem7 = new XMLElem("IMAGEELEMENT");
/* 243 */     xMLGroupElem8.addChild(xMLElem7);
/*     */     
/* 245 */     xMLElem7.addChild((XMLElem)new XMLActivityElem("IMAGEACTION"));
/* 246 */     xMLElem7.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 247 */     xMLElem7.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 248 */     xMLElem7.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 249 */     xMLElem7.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 250 */     xMLElem7.addChild(new XMLElem("PUBTO", "PUBTO"));
/* 251 */     xMLElem7.addChild(new XMLElem("IMAGEDESCRIPTION", "IMGDESC"));
/* 252 */     xMLElem7.addChild(new XMLElem("MARKETINGIMAGEFILENAME", "MKTGIMGFILENAM"));
/*     */ 
/*     */     
/* 255 */     XMLElem xMLElem6 = new XMLElem("COUNTRYLIST");
/* 256 */     xMLElem7.addChild(xMLElem6);
/*     */     
/* 258 */     XMLChgSetElem xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 259 */     xMLElem6.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 261 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 265 */     XMLGroupElem xMLGroupElem7 = new XMLGroupElem("MMLIST", "MM");
/* 266 */     XMLMAP.addChild((XMLElem)xMLGroupElem7);
/*     */     
/* 268 */     XMLElem xMLElem8 = new XMLElem("MMELEMENT");
/* 269 */     xMLGroupElem7.addChild(xMLElem8);
/*     */     
/* 271 */     xMLElem8.addChild((XMLElem)new XMLActivityElem("MMACTION"));
/* 272 */     xMLElem8.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 273 */     xMLElem8.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 274 */     xMLElem8.addChild(new XMLElem("STATUS", "MMSTATUS", 1));
/* 275 */     xMLElem8.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 276 */     xMLElem8.addChild(new XMLElem("PUBTO", "PUBTO"));
/*     */ 
/*     */     
/* 279 */     XMLElem xMLElem5 = new XMLElem("MSGLIST");
/* 280 */     xMLElem8.addChild(xMLElem5);
/*     */     
/* 282 */     xMLNLSElem = new XMLNLSElem("MSGELEMENT");
/* 283 */     xMLElem5.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 285 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/* 286 */     xMLNLSElem.addChild(new XMLElem("SHRTMKTGMSG", "SHRTMKTGMSG"));
/* 287 */     xMLNLSElem.addChild(new XMLElem("LONGMKTGMSG", "LONGMKTGMSG"));
/*     */     
/* 289 */     xMLElem5 = new XMLElem("AUDIENCELIST");
/* 290 */     xMLElem8.addChild(xMLElem5);
/*     */     
/* 292 */     xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 293 */     xMLElem5.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 295 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTION"));
/*     */     
/* 297 */     xMLElem5 = new XMLElem("COUNTRYLIST");
/* 298 */     xMLElem8.addChild(xMLElem5);
/*     */     
/* 300 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 301 */     xMLElem5.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 303 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 307 */     XMLGroupElem xMLGroupElem6 = new XMLGroupElem("FBLIST", "FB");
/* 308 */     XMLMAP.addChild((XMLElem)xMLGroupElem6);
/*     */     
/* 310 */     XMLElem xMLElem9 = new XMLElem("FBELEMENT");
/* 311 */     xMLGroupElem6.addChild(xMLElem9);
/*     */     
/* 313 */     xMLElem9.addChild((XMLElem)new XMLActivityElem("FBACTION"));
/* 314 */     xMLElem9.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 315 */     xMLElem9.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 316 */     xMLElem9.addChild(new XMLElem("STATUS", "FBSTATUS", 1));
/* 317 */     xMLElem9.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 318 */     xMLElem9.addChild(new XMLElem("PUBTO", "PUBTO"));
/*     */ 
/*     */     
/* 321 */     XMLElem xMLElem4 = new XMLElem("FBSTMTLIST");
/* 322 */     xMLElem9.addChild(xMLElem4);
/*     */     
/* 324 */     xMLNLSElem = new XMLNLSElem("FBSTMTELEMENT");
/* 325 */     xMLElem4.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 327 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 329 */     xMLNLSElem.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 330 */     xMLNLSElem.addChild(new XMLElem("FBSTMT", "FBSTMT"));
/*     */     
/* 332 */     xMLElem4 = new XMLElem("AUDIENCELIST");
/* 333 */     xMLElem9.addChild(xMLElem4);
/*     */     
/* 335 */     xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 336 */     xMLElem4.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 338 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "AUDIENCEACTION"));
/*     */     
/* 340 */     xMLElem4 = new XMLElem("COUNTRYLIST");
/* 341 */     xMLElem9.addChild(xMLElem4);
/*     */     
/* 343 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 344 */     xMLElem4.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 346 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 350 */     xMLElem4 = new XMLElem("AUDIENCELIST");
/* 351 */     XMLMAP.addChild(xMLElem4);
/* 352 */     XMLChgSetElem xMLChgSetElem2 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 353 */     xMLElem4.addChild((XMLElem)xMLChgSetElem2);
/* 354 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "AUDIEN", "AUDIENCEACTION"));
/*     */ 
/*     */     
/* 357 */     XMLMAP.addChild((XMLElem)new XMLZCONFElem());
/*     */ 
/*     */     
/* 360 */     xMLElem4 = new XMLElem("CATALOGOVERRIDELIST");
/* 361 */     XMLMAP.addChild(xMLElem4);
/* 362 */     xMLElem4.addChild((XMLElem)new XMLCATAElem());
/*     */ 
/*     */     
/* 365 */     XMLGroupElem xMLGroupElem5 = new XMLGroupElem("CATATTRIBUTELIST", "CATDATA");
/* 366 */     XMLMAP.addChild((XMLElem)xMLGroupElem5);
/* 367 */     XMLElem xMLElem10 = new XMLElem("CATATTRIBUTEELEMENT");
/* 368 */     xMLGroupElem5.addChild(xMLElem10);
/* 369 */     xMLElem10.addChild((XMLElem)new XMLActivityElem("CATATTRIBUTEACTION"));
/* 370 */     xMLElem10.addChild(new XMLElem("CATATTRIBUTE", "DAATTRIBUTECODE"));
/* 371 */     xMLElem10.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 373 */     xMLElem10.addChild(new XMLElem("CATATTRIUBTEVALUE", "DAATTRIBUTEVALUE"));
/*     */ 
/*     */ 
/*     */     
/* 377 */     XMLDistinctGroupElem xMLDistinctGroupElem = new XMLDistinctGroupElem("FEATURELIST", "FEATURE|SWFEATURE", null, true);
/* 378 */     XMLMAP.addChild((XMLElem)xMLDistinctGroupElem);
/* 379 */     XMLElem xMLElem11 = new XMLElem("FEATUREELEMENT");
/* 380 */     xMLDistinctGroupElem.addChild(xMLElem11);
/* 381 */     xMLElem11.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 382 */     xMLElem11.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 383 */     xMLElem11.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 384 */     xMLElem11.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/* 385 */     xMLElem11.addChild((XMLElem)new XMLFEATQTYElem("QTY", "CONFQTY"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 452 */     XMLLSEOWARRGroupElem xMLLSEOWARRGroupElem1 = new XMLLSEOWARRGroupElem("WARRLIST", "WARR");
/* 453 */     XMLMAP.addChild((XMLElem)xMLLSEOWARRGroupElem1);
/*     */     
/* 455 */     XMLElem xMLElem12 = new XMLElem("WARRELEMENT");
/* 456 */     xMLLSEOWARRGroupElem1.addChild(xMLElem12);
/*     */     
/* 458 */     xMLElem12.addChild((XMLElem)new XMLLSEOActivityElem("WARRACTION"));
/* 459 */     xMLElem12.addChild(new XMLElem("WARRENTITYTYPE", "ENTITYTYPE"));
/* 460 */     xMLElem12.addChild(new XMLElem("WARRENTITYID", "ENTITYID"));
/* 461 */     xMLElem12.addChild(new XMLElem("WARRID", "WARRID"));
/*     */     
/* 463 */     XMLLSEOWARRGroupElem xMLLSEOWARRGroupElem2 = new XMLLSEOWARRGroupElem(null, "MODELWARR|PRODSTRUCTWARR", "U:MODELWARR|U:PRODSTRUCTWARR", "SPECIAL");
/* 464 */     xMLElem12.addChild((XMLElem)xMLLSEOWARRGroupElem2);
/* 465 */     xMLLSEOWARRGroupElem2.addChild(new XMLElem("PUBFROM", "EFFECTIVEDATE"));
/* 466 */     xMLLSEOWARRGroupElem2.addChild(new XMLElem("PUBTO", "ENDDATE"));
/* 467 */     xMLLSEOWARRGroupElem2.addChild(new XMLElem("DEFWARR", "DEFWARR"));
/* 468 */     XMLElem xMLElem3 = new XMLElem("COUNTRYLIST");
/* 469 */     xMLElem12.addChild(xMLElem3);
/*     */     
/* 471 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 472 */     xMLElem3.addChild((XMLElem)xMLChgSetElem1);
/* 473 */     XMLLSEOWARRGroupElem xMLLSEOWARRGroupElem3 = new XMLLSEOWARRGroupElem(null, "MODELWARR|PRODSTRUCTWARR", "U:MODELWARR|U:PRODSTRUCTWARR");
/* 474 */     xMLChgSetElem1.addChild((XMLElem)xMLLSEOWARRGroupElem3);
/*     */     
/* 476 */     xMLLSEOWARRGroupElem3.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 481 */     XMLGroupElem xMLGroupElem4 = new XMLGroupElem("REPLLSEOLIST", "LSEO", "D:LSEOREPLLSEO:D", true);
/* 482 */     XMLMAP.addChild((XMLElem)xMLGroupElem4);
/*     */     
/* 484 */     XMLElem xMLElem13 = new XMLElem("REPLLSEOELEMENT");
/* 485 */     xMLGroupElem4.addChild(xMLElem13);
/*     */     
/* 487 */     xMLElem13.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*     */     
/* 489 */     xMLElem13.addChild(new XMLElem("SEOENTITYTYPE", "ENTITYTYPE"));
/*     */     
/* 491 */     xMLElem13.addChild(new XMLElem("SEOENTITYID", "ENTITYID"));
/*     */     
/* 493 */     xMLElem13.addChild(new XMLElem("SEOID", "SEOID"));
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
/* 504 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 510 */     return "ADSLSEO";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVeName2() {
/* 515 */     return "ADSLSEO2";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 520 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 526 */     return "SEO_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 535 */     return "$Revision: 1.1 $";
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
/* 685 */     paramADSABRSTATUS.addDebug("Entered ADSLSEOABR call COM.ibm.eannounce.objects.EntityList.mergeLists");
/*     */     
/* 687 */     EntityList.mergeLists(paramEntityList1, paramEntityList2);
/* 688 */     paramADSABRSTATUS.addDebug("mergeLists:: after merge Extract " + PokUtils.outputList(paramEntityList1));
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSLSEOABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */