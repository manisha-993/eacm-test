/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLBundleTypeElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCATAElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLDistinctGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLLSEOBUNDELAVAILElembh1;
/*     */ import COM.ibm.eannounce.abr.util.XMLLSEOBUNDLEINSTALLElem;
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
/*     */ public class ADSLSEOBUNDLEABR
/*     */   extends XMLMQRoot
/*     */ {
/*  98 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("LSEOBUNDLE_UPDATE"); static {
/*  99 */     XMLMAP.addChild((XMLElem)new XMLVMElem("LSEOBUNDLE_UPDATE", "1"));
/*     */     
/* 101 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 102 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 103 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 104 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 105 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 106 */     XMLMAP.addChild(new XMLElem("SEOID", "SEOID"));
/*     */     
/* 108 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 109 */     XMLMAP.addChild(new XMLElem("PRFCNTR", "PRFTCTR", 1));
/*     */     
/* 111 */     XMLMAP.addChild(new XMLElem("BHPRODHIERCD", "BHPRODHIERCD"));
/* 112 */     XMLMAP.addChild(new XMLElem("BHACCTASGNGRP", "BHACCTASGNGRP", 2));
/* 113 */     XMLMAP.addChild(new XMLElem("UPCCD", "UPCCD"));
/* 114 */     XMLMAP.addChild(new XMLElem("SPECIALBID", "SPECBID"));
/* 115 */     XMLMAP.addChild(new XMLElem("PROJECT", "PROJCDNAM", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     XMLMAP.addChild(new XMLElem("WWOCCODE", "WWOCCODE"));
/* 127 */     XMLMAP.addChild(new XMLElem("SOMFMLY", "SOMFMLY"));
/* 128 */     XMLMAP.addChild(new XMLElem("PRCINDC", "PRCINDC"));
/* 129 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/* 130 */     XMLMAP.addChild(new XMLElem("BPSPECBIDCERTREQ", "BPSPECBIDCERTREQ"));
/* 131 */     XMLMAP.addChild(new XMLElem("SVCPACBNDLTYPE", "SVCPACBNDLTYPE", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     XMLMAP.addChild((XMLElem)new XMLLSEOBUNDLEINSTALLElem());
/*     */ 
/*     */     
/* 154 */     XMLMAP.addChild(new XMLElem("UNSPSC", "UNSPSCCD"));
/* 155 */     XMLMAP.addChild(new XMLElem("UNUOM", "UNSPSCCDUOM"));
/*     */ 
/*     */     
/* 158 */     XMLMAP.addChild((XMLElem)new XMLBundleTypeElem());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     XMLDistinctGroupElem xMLDistinctGroupElem = new XMLDistinctGroupElem(null, "SGMNTACRNYM", "D:LSEOBUNDLESGMTACRONYMA:D", true, true);
/* 166 */     XMLMAP.addChild((XMLElem)xMLDistinctGroupElem);
/* 167 */     xMLDistinctGroupElem.addChild(new XMLElem("DIVISION", "DIV", 1));
/*     */     
/* 169 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 170 */     XMLMAP.addChild(xMLElem1);
/*     */     
/* 172 */     XMLNLSElem xMLNLSElem1 = new XMLNLSElem("LANGUAGEELEMENT");
/* 173 */     xMLElem1.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 175 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/* 176 */     xMLNLSElem1.addChild(new XMLElem("MKTGDESC", "BUNDLMKTGDESC"));
/* 177 */     xMLNLSElem1.addChild(new XMLElem("INVNAME", "PRCFILENAM"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     XMLElem xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 185 */     XMLMAP.addChild(xMLElem2);
/* 186 */     xMLElem2.addChild((XMLElem)new XMLLSEOBUNDELAVAILElembh1());
/*     */ 
/*     */     
/* 189 */     XMLGroupElem xMLGroupElem6 = new XMLGroupElem("IMAGELIST", "IMG");
/* 190 */     XMLMAP.addChild((XMLElem)xMLGroupElem6);
/*     */     
/* 192 */     XMLElem xMLElem8 = new XMLElem("IMAGEELEMENT");
/* 193 */     xMLGroupElem6.addChild(xMLElem8);
/*     */     
/* 195 */     xMLElem8.addChild((XMLElem)new XMLActivityElem("IMAGEACTION"));
/* 196 */     xMLElem8.addChild(new XMLElem("IMAGEENTITYTYPE", "ENTITYTYPE"));
/* 197 */     xMLElem8.addChild(new XMLElem("IMAGEENTITYID", "ENTITYID"));
/*     */ 
/*     */     
/* 200 */     xMLElem8.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 201 */     xMLElem8.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 202 */     xMLElem8.addChild(new XMLElem("PUBTO", "PUBTO"));
/* 203 */     xMLElem8.addChild(new XMLElem("IMAGEDESCRIPTION", "IMGDESC"));
/* 204 */     xMLElem8.addChild(new XMLElem("MARKETINGIMAGEFILENAME", "MKTGIMGFILENAM"));
/*     */ 
/*     */     
/* 207 */     XMLElem xMLElem7 = new XMLElem("COUNTRYLIST");
/* 208 */     xMLElem8.addChild(xMLElem7);
/*     */     
/* 210 */     XMLChgSetElem xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 211 */     xMLElem7.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 213 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 217 */     XMLGroupElem xMLGroupElem5 = new XMLGroupElem("MMLIST", "MM");
/* 218 */     XMLMAP.addChild((XMLElem)xMLGroupElem5);
/*     */     
/* 220 */     XMLElem xMLElem9 = new XMLElem("MMELEMENT");
/* 221 */     xMLGroupElem5.addChild(xMLElem9);
/*     */     
/* 223 */     xMLElem9.addChild((XMLElem)new XMLActivityElem("MMACTION"));
/* 224 */     xMLElem9.addChild(new XMLElem("MMENTITYTYPE", "ENTITYTYPE"));
/* 225 */     xMLElem9.addChild(new XMLElem("MMENTITYID", "ENTITYID"));
/* 226 */     xMLElem9.addChild(new XMLElem("STATUS", "MMSTATUS", 1));
/* 227 */     xMLElem9.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 228 */     xMLElem9.addChild(new XMLElem("PUBTO", "PUBTO"));
/*     */     
/* 230 */     XMLElem xMLElem6 = new XMLElem("MSGLIST");
/* 231 */     xMLElem9.addChild(xMLElem6);
/*     */     
/* 233 */     xMLNLSElem1 = new XMLNLSElem("MSGELEMENT");
/* 234 */     xMLElem6.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 236 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/* 237 */     xMLNLSElem1.addChild(new XMLElem("SHRTMKTGMSG", "SHRTMKTGMSG"));
/* 238 */     xMLNLSElem1.addChild(new XMLElem("LONGMKTGMSG", "LONGMKTGMSG"));
/*     */     
/* 240 */     xMLElem6 = new XMLElem("AUDIENCELIST");
/* 241 */     xMLElem9.addChild(xMLElem6);
/*     */     
/* 243 */     xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 244 */     xMLElem6.addChild((XMLElem)xMLChgSetElem1);
/*     */ 
/*     */     
/* 247 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "ACTIVITY", 0));
/*     */     
/* 249 */     xMLElem6 = new XMLElem("COUNTRYLIST");
/* 250 */     xMLElem9.addChild(xMLElem6);
/*     */     
/* 252 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 253 */     xMLElem6.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 255 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 259 */     XMLGroupElem xMLGroupElem4 = new XMLGroupElem("FBLIST", "FB");
/* 260 */     XMLMAP.addChild((XMLElem)xMLGroupElem4);
/*     */     
/* 262 */     XMLElem xMLElem10 = new XMLElem("FBELEMENT");
/* 263 */     xMLGroupElem4.addChild(xMLElem10);
/*     */     
/* 265 */     xMLElem10.addChild((XMLElem)new XMLActivityElem("FBACTION"));
/* 266 */     xMLElem10.addChild(new XMLElem("FBENTITYTYPE", "ENTITYTYPE"));
/* 267 */     xMLElem10.addChild(new XMLElem("FBENTITYID", "ENTITYID"));
/*     */     
/* 269 */     xMLElem10.addChild(new XMLElem("STATUS", "FBSTATUS", 1));
/* 270 */     xMLElem10.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 271 */     xMLElem10.addChild(new XMLElem("PUBTO", "PUBTO"));
/*     */ 
/*     */     
/* 274 */     XMLElem xMLElem5 = new XMLElem("FBSTMTLIST");
/* 275 */     xMLElem10.addChild(xMLElem5);
/*     */     
/* 277 */     xMLNLSElem1 = new XMLNLSElem("FBSTMTELEMENT");
/* 278 */     xMLElem5.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 280 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/* 281 */     xMLNLSElem1.addChild(new XMLElem("FBSTMT", "FBSTMT"));
/*     */     
/* 283 */     xMLElem5 = new XMLElem("AUDIENCELIST");
/* 284 */     xMLElem10.addChild(xMLElem5);
/*     */     
/* 286 */     xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 287 */     xMLElem5.addChild((XMLElem)xMLChgSetElem1);
/*     */ 
/*     */     
/* 290 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "ACTIVITY", 0));
/*     */     
/* 292 */     xMLElem5 = new XMLElem("COUNTRYLIST");
/* 293 */     xMLElem10.addChild(xMLElem5);
/*     */     
/* 295 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 296 */     xMLElem5.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 298 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 302 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
/* 303 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/*     */     
/* 305 */     XMLElem xMLElem11 = new XMLElem("TAXCATEGORYELEMENT");
/* 306 */     xMLGroupElem3.addChild(xMLElem11);
/*     */     
/* 308 */     xMLElem11.addChild((XMLElem)new XMLActivityElem("TAXCATEGORYACTION"));
/* 309 */     XMLElem xMLElem4 = new XMLElem("COUNTRYLIST");
/* 310 */     xMLElem11.addChild(xMLElem4);
/*     */     
/* 312 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 313 */     xMLElem4.addChild((XMLElem)xMLChgSetElem1);
/*     */ 
/*     */     
/* 316 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "TAXCNTRY", "COUNTRYACTION", 1));
/*     */     
/* 318 */     xMLElem11.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", 1));
/* 319 */     XMLGroupElem xMLGroupElem7 = new XMLGroupElem(null, "SLNTAXRELEVANCE", "U:SLNTAXRELEVANCE");
/* 320 */     xMLElem11.addChild((XMLElem)xMLGroupElem7);
/* 321 */     xMLGroupElem7.addChild(new XMLElem("TAXCLASSIFICATION", "TAXCLS", 2));
/*     */     
/* 323 */     xMLElem11.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXCATGSLEORGA:D"));
/*     */     
/* 325 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem("TAXCODELIST", "TAXGRP");
/* 326 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 327 */     XMLElem xMLElem12 = new XMLElem("TAXCODEELEMENT");
/* 328 */     xMLGroupElem2.addChild(xMLElem12);
/* 329 */     xMLElem12.addChild((XMLElem)new XMLActivityElem("TAXCODEACTION"));
/* 330 */     xMLElem12.addChild(new XMLElem("TAXCODEDESCRIPTION", "DESC"));
/* 331 */     XMLElem xMLElem3 = new XMLElem("COUNTRYLIST");
/* 332 */     xMLElem12.addChild(xMLElem3);
/* 333 */     XMLChgSetElem xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 334 */     xMLElem3.addChild((XMLElem)xMLChgSetElem2);
/* 335 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/* 336 */     xMLElem12.addChild(new XMLElem("TAXCODE", "TAXCD"));
/*     */     
/* 338 */     xMLElem12.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXGRPSLEORGA:D"));
/*     */ 
/*     */     
/* 341 */     xMLElem3 = new XMLElem("AUDIENCELIST");
/* 342 */     XMLMAP.addChild(xMLElem3);
/*     */     
/* 344 */     xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 345 */     xMLElem3.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 347 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "AUDIEN", "AUDIENCEACTION", 0));
/*     */ 
/*     */ 
/*     */     
/* 351 */     XMLMAP.addChild((XMLElem)new XMLZCONFElem());
/*     */ 
/*     */     
/* 354 */     xMLElem3 = new XMLElem("CATALOGOVERRIDELIST");
/* 355 */     XMLMAP.addChild(xMLElem3);
/* 356 */     xMLElem3.addChild((XMLElem)new XMLCATAElem());
/*     */ 
/*     */ 
/*     */     
/* 360 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem("CATATTRIBUTELIST", "CATDATA");
/* 361 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 363 */     XMLNLSElem xMLNLSElem2 = new XMLNLSElem("CATATTRIBUTEELEMENT");
/* 364 */     xMLGroupElem1.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 366 */     xMLNLSElem2.addChild((XMLElem)new XMLActivityElem("CATATTRIBUTEACTION"));
/* 367 */     xMLNLSElem2.addChild(new XMLElem("CATATTRIBUTE", "DAATTRIBUTECODE"));
/* 368 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 370 */     xMLNLSElem2.addChild(new XMLElem("CATATTRIBUTEVALUE", "DAATTRIBUTEVALUE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     xMLGroupElem1 = new XMLGroupElem("COMPONENTLIST", "LSEO");
/* 379 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 381 */     XMLElem xMLElem13 = new XMLElem("COMPONENTELEMENT");
/* 382 */     xMLGroupElem1.addChild(xMLElem13);
/*     */     
/* 384 */     xMLElem13.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 385 */     xMLElem13.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 386 */     xMLElem13.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */ 
/*     */     
/* 389 */     xMLElem13.addChild(new XMLElem("SEOID", "SEOID"));
/* 390 */     XMLGroupElem xMLGroupElem8 = new XMLGroupElem(null, "LSEOBUNDLELSEO", "U:LSEOBUNDLELSEO");
/* 391 */     xMLElem13.addChild((XMLElem)xMLGroupElem8);
/* 392 */     xMLGroupElem8.addChild(new XMLElem("QTY", "LSEOQTY"));
/* 393 */     xMLGroupElem8.addChild(new XMLElem("SEQ", "LSEOSEQ"));
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
/* 404 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 410 */     return "ADSLSEOBUNDLE";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 415 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 421 */     return "LSEOBUNDLE_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 430 */     return "$Revision: 1.25 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSLSEOBUNDLEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */