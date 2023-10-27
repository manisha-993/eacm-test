/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSLSEOBUNDLEABR
/*     */   extends XMLMQRoot
/*     */ {
/* 101 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("LSEOBUNDLE_UPDATE"); static {
/* 102 */     XMLMAP.addChild((XMLElem)new XMLVMElem("LSEOBUNDLE_UPDATE", "1"));
/*     */     
/* 104 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 105 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 106 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 107 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 108 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/* 109 */     XMLMAP.addChild(new XMLElem("SEOID", "SEOID"));
/*     */     
/* 111 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 112 */     XMLMAP.addChild(new XMLElem("PRFCNTR", "PRFTCTR", 1));
/*     */     
/* 114 */     XMLMAP.addChild(new XMLElem("BHPRODHIERCD", "BHPRODHIERCD"));
/* 115 */     XMLMAP.addChild(new XMLElem("BHACCTASGNGRP", "BHACCTASGNGRP", 2));
/* 116 */     XMLMAP.addChild(new XMLElem("UPCCD", "UPCCD"));
/* 117 */     XMLMAP.addChild(new XMLElem("SPECIALBID", "SPECBID"));
/* 118 */     XMLMAP.addChild(new XMLElem("PROJECT", "PROJCDNAM", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     XMLMAP.addChild(new XMLElem("WWOCCODE", "WWOCCODE"));
/* 130 */     XMLMAP.addChild(new XMLElem("SOMFMLY", "SOMFMLY"));
/* 131 */     XMLMAP.addChild(new XMLElem("PRCINDC", "PRCINDC"));
/* 132 */     XMLMAP.addChild(new XMLElem("ZEROPRICE", "ZEROPRICE"));
/* 133 */     XMLMAP.addChild(new XMLElem("BPSPECBIDCERTREQ", "BPSPECBIDCERTREQ"));
/* 134 */     XMLMAP.addChild(new XMLElem("SVCPACBNDLTYPE", "SVCPACBNDLTYPE", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     XMLMAP.addChild((XMLElem)new XMLLSEOBUNDLEINSTALLElem());
/*     */ 
/*     */     
/* 157 */     XMLMAP.addChild(new XMLElem("UNSPSC", "UNSPSCCD"));
/* 158 */     XMLMAP.addChild(new XMLElem("UNUOM", "UNSPSCCDUOM"));
/*     */ 
/*     */     
/* 161 */     XMLMAP.addChild((XMLElem)new XMLBundleTypeElem());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     XMLDistinctGroupElem xMLDistinctGroupElem = new XMLDistinctGroupElem(null, "PROJ", "D:LSEOBUNDLEPROJA:D", true, true);
/* 170 */     XMLMAP.addChild((XMLElem)xMLDistinctGroupElem);
/* 171 */     xMLDistinctGroupElem.addChild(new XMLElem("DIVISION", "DIV", 1));
/*     */     
/* 173 */     XMLElem xMLElem1 = new XMLElem("LANGUAGELIST");
/* 174 */     XMLMAP.addChild(xMLElem1);
/*     */     
/* 176 */     XMLNLSElem xMLNLSElem1 = new XMLNLSElem("LANGUAGEELEMENT");
/* 177 */     xMLElem1.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 179 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/* 180 */     xMLNLSElem1.addChild(new XMLElem("MKTGDESC", "BUNDLMKTGDESC"));
/* 181 */     xMLNLSElem1.addChild(new XMLElem("INVNAME", "PRCFILENAM"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     XMLElem xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 189 */     XMLMAP.addChild(xMLElem2);
/* 190 */     xMLElem2.addChild((XMLElem)new XMLLSEOBUNDELAVAILElembh1());
/*     */ 
/*     */     
/* 193 */     XMLGroupElem xMLGroupElem6 = new XMLGroupElem("IMAGELIST", "IMG");
/* 194 */     XMLMAP.addChild((XMLElem)xMLGroupElem6);
/*     */     
/* 196 */     XMLElem xMLElem8 = new XMLElem("IMAGEELEMENT");
/* 197 */     xMLGroupElem6.addChild(xMLElem8);
/*     */     
/* 199 */     xMLElem8.addChild((XMLElem)new XMLActivityElem("IMAGEACTION"));
/* 200 */     xMLElem8.addChild(new XMLElem("IMAGEENTITYTYPE", "ENTITYTYPE"));
/* 201 */     xMLElem8.addChild(new XMLElem("IMAGEENTITYID", "ENTITYID"));
/*     */ 
/*     */     
/* 204 */     xMLElem8.addChild(new XMLElem("STATUS", "STATUS", 1));
/* 205 */     xMLElem8.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 206 */     xMLElem8.addChild(new XMLElem("PUBTO", "PUBTO"));
/* 207 */     xMLElem8.addChild(new XMLElem("IMAGEDESCRIPTION", "IMGDESC"));
/* 208 */     xMLElem8.addChild(new XMLElem("MARKETINGIMAGEFILENAME", "MKTGIMGFILENAM"));
/*     */ 
/*     */     
/* 211 */     XMLElem xMLElem7 = new XMLElem("COUNTRYLIST");
/* 212 */     xMLElem8.addChild(xMLElem7);
/*     */     
/* 214 */     XMLChgSetElem xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 215 */     xMLElem7.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 217 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 221 */     XMLGroupElem xMLGroupElem5 = new XMLGroupElem("MMLIST", "MM");
/* 222 */     XMLMAP.addChild((XMLElem)xMLGroupElem5);
/*     */     
/* 224 */     XMLElem xMLElem9 = new XMLElem("MMELEMENT");
/* 225 */     xMLGroupElem5.addChild(xMLElem9);
/*     */     
/* 227 */     xMLElem9.addChild((XMLElem)new XMLActivityElem("MMACTION"));
/* 228 */     xMLElem9.addChild(new XMLElem("MMENTITYTYPE", "ENTITYTYPE"));
/* 229 */     xMLElem9.addChild(new XMLElem("MMENTITYID", "ENTITYID"));
/* 230 */     xMLElem9.addChild(new XMLElem("STATUS", "MMSTATUS", 1));
/* 231 */     xMLElem9.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 232 */     xMLElem9.addChild(new XMLElem("PUBTO", "PUBTO"));
/*     */     
/* 234 */     XMLElem xMLElem6 = new XMLElem("MSGLIST");
/* 235 */     xMLElem9.addChild(xMLElem6);
/*     */     
/* 237 */     xMLNLSElem1 = new XMLNLSElem("MSGELEMENT");
/* 238 */     xMLElem6.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 240 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/* 241 */     xMLNLSElem1.addChild(new XMLElem("SHRTMKTGMSG", "SHRTMKTGMSG"));
/* 242 */     xMLNLSElem1.addChild(new XMLElem("LONGMKTGMSG", "LONGMKTGMSG"));
/*     */     
/* 244 */     xMLElem6 = new XMLElem("AUDIENCELIST");
/* 245 */     xMLElem9.addChild(xMLElem6);
/*     */     
/* 247 */     xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 248 */     xMLElem6.addChild((XMLElem)xMLChgSetElem1);
/*     */ 
/*     */     
/* 251 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "ACTIVITY", 0));
/*     */     
/* 253 */     xMLElem6 = new XMLElem("COUNTRYLIST");
/* 254 */     xMLElem9.addChild(xMLElem6);
/*     */     
/* 256 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 257 */     xMLElem6.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 259 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 263 */     XMLGroupElem xMLGroupElem4 = new XMLGroupElem("FBLIST", "FB");
/* 264 */     XMLMAP.addChild((XMLElem)xMLGroupElem4);
/*     */     
/* 266 */     XMLElem xMLElem10 = new XMLElem("FBELEMENT");
/* 267 */     xMLGroupElem4.addChild(xMLElem10);
/*     */     
/* 269 */     xMLElem10.addChild((XMLElem)new XMLActivityElem("FBACTION"));
/* 270 */     xMLElem10.addChild(new XMLElem("FBENTITYTYPE", "ENTITYTYPE"));
/* 271 */     xMLElem10.addChild(new XMLElem("FBENTITYID", "ENTITYID"));
/*     */     
/* 273 */     xMLElem10.addChild(new XMLElem("STATUS", "FBSTATUS", 1));
/* 274 */     xMLElem10.addChild(new XMLElem("PUBFROM", "PUBFROM"));
/* 275 */     xMLElem10.addChild(new XMLElem("PUBTO", "PUBTO"));
/*     */ 
/*     */     
/* 278 */     XMLElem xMLElem5 = new XMLElem("FBSTMTLIST");
/* 279 */     xMLElem10.addChild(xMLElem5);
/*     */     
/* 281 */     xMLNLSElem1 = new XMLNLSElem("FBSTMTELEMENT");
/* 282 */     xMLElem5.addChild((XMLElem)xMLNLSElem1);
/*     */     
/* 284 */     xMLNLSElem1.addChild(new XMLElem("NLSID", "NLSID"));
/* 285 */     xMLNLSElem1.addChild(new XMLElem("FBSTMT", "FBSTMT"));
/*     */     
/* 287 */     xMLElem5 = new XMLElem("AUDIENCELIST");
/* 288 */     xMLElem10.addChild(xMLElem5);
/*     */     
/* 290 */     xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 291 */     xMLElem5.addChild((XMLElem)xMLChgSetElem1);
/*     */ 
/*     */     
/* 294 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "CATAUDIENCE", "ACTIVITY", 0));
/*     */     
/* 296 */     xMLElem5 = new XMLElem("COUNTRYLIST");
/* 297 */     xMLElem10.addChild(xMLElem5);
/*     */     
/* 299 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 300 */     xMLElem5.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 302 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/*     */ 
/*     */ 
/*     */     
/* 306 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem("TAXCATEGORYLIST", "TAXCATG");
/* 307 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/*     */     
/* 309 */     XMLElem xMLElem11 = new XMLElem("TAXCATEGORYELEMENT");
/* 310 */     xMLGroupElem3.addChild(xMLElem11);
/*     */     
/* 312 */     xMLElem11.addChild((XMLElem)new XMLActivityElem("TAXCATEGORYACTION"));
/* 313 */     XMLElem xMLElem4 = new XMLElem("COUNTRYLIST");
/* 314 */     xMLElem11.addChild(xMLElem4);
/*     */     
/* 316 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 317 */     xMLElem4.addChild((XMLElem)xMLChgSetElem1);
/*     */ 
/*     */     
/* 320 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "TAXCNTRY", "COUNTRYACTION", 1));
/*     */     
/* 322 */     xMLElem11.addChild(new XMLElem("TAXCATEGORYVALUE", "TAXCATGATR", 1));
/* 323 */     XMLGroupElem xMLGroupElem7 = new XMLGroupElem(null, "SLNTAXRELEVANCE", "U:SLNTAXRELEVANCE");
/* 324 */     xMLElem11.addChild((XMLElem)xMLGroupElem7);
/* 325 */     xMLGroupElem7.addChild(new XMLElem("TAXCLASSIFICATION", "TAXCLS", 2));
/*     */     
/* 327 */     xMLElem11.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXCATGSLEORGA:D"));
/*     */     
/* 329 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem("TAXCODELIST", "TAXGRP");
/* 330 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 331 */     XMLElem xMLElem12 = new XMLElem("TAXCODEELEMENT");
/* 332 */     xMLGroupElem2.addChild(xMLElem12);
/* 333 */     xMLElem12.addChild((XMLElem)new XMLActivityElem("TAXCODEACTION"));
/* 334 */     xMLElem12.addChild(new XMLElem("TAXCODEDESCRIPTION", "DESC"));
/* 335 */     XMLElem xMLElem3 = new XMLElem("COUNTRYLIST");
/* 336 */     xMLElem12.addChild(xMLElem3);
/* 337 */     XMLChgSetElem xMLChgSetElem2 = new XMLChgSetElem("COUNTRYELEMENT");
/* 338 */     xMLElem3.addChild((XMLElem)xMLChgSetElem2);
/* 339 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
/* 340 */     xMLElem12.addChild(new XMLElem("TAXCODE", "TAXCD"));
/*     */     
/* 342 */     xMLElem12.addChild((XMLElem)new XMLSLEORGGRPElem("D:TAXGRPSLEORGA:D"));
/*     */ 
/*     */     
/* 345 */     xMLElem3 = new XMLElem("AUDIENCELIST");
/* 346 */     XMLMAP.addChild(xMLElem3);
/*     */     
/* 348 */     xMLChgSetElem1 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 349 */     xMLElem3.addChild((XMLElem)xMLChgSetElem1);
/*     */     
/* 351 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "AUDIEN", "AUDIENCEACTION", 0));
/*     */ 
/*     */ 
/*     */     
/* 355 */     XMLMAP.addChild((XMLElem)new XMLZCONFElem());
/*     */ 
/*     */     
/* 358 */     xMLElem3 = new XMLElem("CATALOGOVERRIDELIST");
/* 359 */     XMLMAP.addChild(xMLElem3);
/* 360 */     xMLElem3.addChild((XMLElem)new XMLCATAElem());
/*     */ 
/*     */ 
/*     */     
/* 364 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem("CATATTRIBUTELIST", "CATDATA");
/* 365 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 367 */     XMLNLSElem xMLNLSElem2 = new XMLNLSElem("CATATTRIBUTEELEMENT");
/* 368 */     xMLGroupElem1.addChild((XMLElem)xMLNLSElem2);
/*     */     
/* 370 */     xMLNLSElem2.addChild((XMLElem)new XMLActivityElem("CATATTRIBUTEACTION"));
/* 371 */     xMLNLSElem2.addChild(new XMLElem("CATATTRIBUTE", "DAATTRIBUTECODE"));
/* 372 */     xMLNLSElem2.addChild(new XMLElem("NLSID", "NLSID"));
/*     */     
/* 374 */     xMLNLSElem2.addChild(new XMLElem("CATATTRIBUTEVALUE", "DAATTRIBUTEVALUE"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 382 */     xMLGroupElem1 = new XMLGroupElem("COMPONENTLIST", "LSEO");
/* 383 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 385 */     XMLElem xMLElem13 = new XMLElem("COMPONENTELEMENT");
/* 386 */     xMLGroupElem1.addChild(xMLElem13);
/*     */     
/* 388 */     xMLElem13.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/* 389 */     xMLElem13.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 390 */     xMLElem13.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */ 
/*     */     
/* 393 */     xMLElem13.addChild(new XMLElem("SEOID", "SEOID"));
/* 394 */     XMLGroupElem xMLGroupElem8 = new XMLGroupElem(null, "LSEOBUNDLELSEO", "U:LSEOBUNDLELSEO");
/* 395 */     xMLElem13.addChild((XMLElem)xMLGroupElem8);
/* 396 */     xMLGroupElem8.addChild(new XMLElem("QTY", "LSEOQTY"));
/* 397 */     xMLGroupElem8.addChild(new XMLElem("SEQ", "LSEOSEQ"));
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
/* 408 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 414 */     return "ADSLSEOBUNDLE";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 419 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 425 */     return "LSEOBUNDLE_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 434 */     return "$Revision: 1.1 $";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSLSEOBUNDLEABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */