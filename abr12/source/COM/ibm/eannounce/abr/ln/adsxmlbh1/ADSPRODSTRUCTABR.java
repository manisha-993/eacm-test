/*     */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.XMLActivityElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLCATAElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLChgSetElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLGroupElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMachtypeElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMktgInvElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLMultiFlagElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNLSElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLNotificationElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLRelatorElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLStatusElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLTMFAVAILElem;
/*     */ import COM.ibm.eannounce.abr.util.XMLVMElem;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ADSPRODSTRUCTABR
/*     */   extends XMLMQRoot
/*     */ {
/* 182 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("TMF_UPDATE"); static {
/* 183 */     XMLMAP.addChild((XMLElem)new XMLVMElem("TMF_UPDATE", "1"));
/*     */     
/* 185 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 186 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 187 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*     */     
/* 189 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 190 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 191 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */     
/* 193 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("MODELENTITYTYPE", "ENTITY2TYPE", "MODEL"));
/* 194 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("MODELENTITYID", "ENTITY2ID", "MODEL"));
/*     */     
/* 196 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "MODEL", "D:MODEL");
/* 197 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 199 */     xMLGroupElem1.addChild((XMLElem)new XMLMachtypeElem("MACHTYPE", "MACHTYPEATR"));
/* 200 */     xMLGroupElem1.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */     
/* 202 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("FEATUREENTITYTYPE", "ENTITY1TYPE", "FEATURE"));
/* 203 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("FEATUREENTITYID", "ENTITY1ID", "FEATURE"));
/*     */     
/* 205 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "FEATURE", "U:FEATURE");
/* 206 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 207 */     xMLGroupElem2.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/* 208 */     xMLGroupElem2.addChild(new XMLElem("FCCAT", "HWFCCAT"));
/* 209 */     xMLGroupElem2.addChild(new XMLElem("FCTYPE", "FCTYPE"));
/*     */     
/* 211 */     XMLMAP.addChild(new XMLElem("ORDERCODE", "ORDERCODE", 2));
/* 212 */     XMLMAP.addChild(new XMLElem("SYSTEMMAX", "SYSTEMMAX"));
/* 213 */     XMLMAP.addChild(new XMLElem("SYSTEMMIN", "SYSTEMMIN"));
/* 214 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG", "CONFIGURATORFLAG", 2));
/*     */     
/* 216 */     XMLMAP.addChild(new XMLElem("BULKMESINDC", "BULKMESINDC", 0));
/* 217 */     XMLMAP.addChild(new XMLElem("INSTALL", "INSTALL", 0));
/*     */ 
/*     */ 
/*     */     
/* 221 */     XMLMAP.addChild(new XMLElem("NOCSTSHIP", "NOCSTSHIP", 2));
/*     */ 
/*     */     
/* 224 */     XMLMAP.addChild(new XMLElem("WARRSVCCOVR", "WARRSVCCOVR", 0));
/*     */ 
/*     */     
/* 227 */     XMLElem xMLElem2 = new XMLElem("OSLIST");
/* 228 */     XMLMAP.addChild(xMLElem2);
/* 229 */     XMLChgSetElem xMLChgSetElem1 = new XMLChgSetElem("OSELEMENT");
/* 230 */     xMLElem2.addChild((XMLElem)xMLChgSetElem1);
/* 231 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("OS", "OSLEVEL", "OSACTION", 1));
/*     */ 
/*     */     
/* 234 */     XMLElem xMLElem3 = new XMLElem("LANGUAGELIST");
/* 235 */     XMLMAP.addChild(xMLElem3);
/*     */ 
/*     */     
/* 238 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 239 */     xMLElem3.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 241 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */     
/* 244 */     xMLNLSElem.addChild((XMLElem)new XMLMktgInvElem("MKTGNAME", "MKTGNAME", "MKTGNAME", "U:FEATURE"));
/*     */ 
/*     */ 
/*     */     
/* 248 */     xMLNLSElem.addChild((XMLElem)new XMLMktgInvElem("INVNAME", "INVNAME", "INVNAME", "U:FEATURE"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 254 */     XMLMAP.addChild(xMLElem2);
/* 255 */     xMLElem2.addChild((XMLElem)new XMLTMFAVAILElem());
/*     */ 
/*     */     
/* 258 */     xMLElem2 = new XMLElem("AUDIENCELIST");
/* 259 */     XMLMAP.addChild(xMLElem2);
/*     */     
/* 261 */     XMLChgSetElem xMLChgSetElem2 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 262 */     xMLElem2.addChild((XMLElem)xMLChgSetElem2);
/*     */ 
/*     */     
/* 265 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "AUDIEN", "AUDIENCEACTION", 0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     xMLElem2 = new XMLElem("CATALOGOVERRIDELIST");
/* 281 */     XMLMAP.addChild(xMLElem2);
/* 282 */     xMLElem2.addChild((XMLElem)new XMLCATAElem());
/*     */ 
/*     */     
/* 285 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem("WARRLIST", "WARR");
/* 286 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/*     */     
/* 288 */     XMLElem xMLElem4 = new XMLElem("WARRELEMENT");
/* 289 */     xMLGroupElem3.addChild(xMLElem4);
/*     */     
/* 291 */     xMLElem4.addChild((XMLElem)new XMLActivityElem("WARRACTION"));
/* 292 */     xMLElem4.addChild(new XMLElem("WARRENTITYTYPE", "ENTITYTYPE"));
/* 293 */     xMLElem4.addChild(new XMLElem("WARRENTITYID", "ENTITYID"));
/* 294 */     xMLElem4.addChild(new XMLElem("WARRID", "WARRID"));
/* 295 */     XMLGroupElem xMLGroupElem4 = new XMLGroupElem(null, "PRODSTRUCTWARR", "U:PRODSTRUCTWARR");
/* 296 */     xMLElem4.addChild((XMLElem)xMLGroupElem4);
/*     */     
/* 298 */     xMLGroupElem4.addChild(new XMLElem("PUBFROM", "EFFECTIVEDATE"));
/* 299 */     xMLGroupElem4.addChild(new XMLElem("PUBTO", "ENDDATE"));
/*     */     
/* 301 */     xMLGroupElem4.addChild(new XMLElem("DEFWARR", "DEFWARR"));
/* 302 */     XMLElem xMLElem1 = new XMLElem("COUNTRYLIST");
/* 303 */     xMLElem4.addChild(xMLElem1);
/*     */     
/* 305 */     xMLChgSetElem1 = new XMLChgSetElem("COUNTRYELEMENT");
/* 306 */     xMLElem1.addChild((XMLElem)xMLChgSetElem1);
/* 307 */     XMLGroupElem xMLGroupElem5 = new XMLGroupElem(null, "PRODSTRUCTWARR", "U:PRODSTRUCTWARR");
/* 308 */     xMLChgSetElem1.addChild((XMLElem)xMLGroupElem5);
/*     */     
/* 310 */     xMLGroupElem5.addChild((XMLElem)new XMLMultiFlagElem("COUNTRY_FC", "COUNTRYLIST", "COUNTRYACTION", 1));
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
/*     */   public XMLElem getXMLMap() {
/* 328 */     return XMLMAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVeName() {
/* 335 */     return "ADSPRODSTRUCT";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVeName2() {
/* 340 */     return "ADSPRODSTRUCT2";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatusAttr() {
/* 346 */     return "STATUS";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMQCID() {
/* 354 */     return "TMF_UPDATE";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 363 */     return "$Revision: 1.1 $";
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
/*     */   protected void mergeLists(ADSABRSTATUS paramADSABRSTATUS, EntityList paramEntityList1, EntityList paramEntityList2) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
/* 375 */     paramADSABRSTATUS.addDebug("Entered ADSPRODSTRUCTABR call COM.ibm.eannounce.objects.EntityList.mergeLists");
/*     */     
/* 377 */     EntityList.mergeLists(paramEntityList1, paramEntityList2);
/* 378 */     paramADSABRSTATUS.addDebug("mergeLists:: after merge Extract " + PokUtils.outputList(paramEntityList1));
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */