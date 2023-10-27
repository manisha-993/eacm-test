/*     */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
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
/*     */ public class ADSPRODSTRUCTABR
/*     */   extends XMLMQRoot
/*     */ {
/* 179 */   private static final XMLElem XMLMAP = (XMLElem)new XMLGroupElem("TMF_UPDATE"); static {
/* 180 */     XMLMAP.addChild((XMLElem)new XMLVMElem("TMF_UPDATE", "1"));
/*     */     
/* 182 */     XMLMAP.addChild(new XMLElem("PDHDOMAIN", "PDHDOMAIN"));
/* 183 */     XMLMAP.addChild((XMLElem)new XMLNotificationElem("DTSOFMSG"));
/* 184 */     XMLMAP.addChild((XMLElem)new XMLActivityElem("ACTIVITY"));
/*     */     
/* 186 */     XMLMAP.addChild((XMLElem)new XMLStatusElem("STATUS", "STATUS", 1));
/* 187 */     XMLMAP.addChild(new XMLElem("ENTITYTYPE", "ENTITYTYPE"));
/* 188 */     XMLMAP.addChild(new XMLElem("ENTITYID", "ENTITYID"));
/*     */     
/* 190 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("MODELENTITYTYPE", "ENTITY2TYPE", "MODEL"));
/* 191 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("MODELENTITYID", "ENTITY2ID", "MODEL"));
/*     */     
/* 193 */     XMLGroupElem xMLGroupElem1 = new XMLGroupElem(null, "MODEL", "D:MODEL");
/* 194 */     XMLMAP.addChild((XMLElem)xMLGroupElem1);
/*     */     
/* 196 */     xMLGroupElem1.addChild((XMLElem)new XMLMachtypeElem("MACHTYPE", "MACHTYPEATR"));
/* 197 */     xMLGroupElem1.addChild(new XMLElem("MODEL", "MODELATR"));
/*     */     
/* 199 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("FEATUREENTITYTYPE", "ENTITY1TYPE", "FEATURE"));
/* 200 */     XMLMAP.addChild((XMLElem)new XMLRelatorElem("FEATUREENTITYID", "ENTITY1ID", "FEATURE"));
/*     */     
/* 202 */     XMLGroupElem xMLGroupElem2 = new XMLGroupElem(null, "FEATURE", "U:FEATURE");
/* 203 */     XMLMAP.addChild((XMLElem)xMLGroupElem2);
/* 204 */     xMLGroupElem2.addChild(new XMLElem("FEATURECODE", "FEATURECODE"));
/* 205 */     xMLGroupElem2.addChild(new XMLElem("FCCAT", "HWFCCAT"));
/* 206 */     xMLGroupElem2.addChild(new XMLElem("FCTYPE", "FCTYPE"));
/*     */     
/* 208 */     XMLMAP.addChild(new XMLElem("ORDERCODE", "ORDERCODE", 2));
/* 209 */     XMLMAP.addChild(new XMLElem("SYSTEMMAX", "SYSTEMMAX"));
/* 210 */     XMLMAP.addChild(new XMLElem("SYSTEMMIN", "SYSTEMMIN"));
/* 211 */     XMLMAP.addChild(new XMLElem("CONFIGURATORFLAG", "CONFIGURATORFLAG", 2));
/*     */     
/* 213 */     XMLMAP.addChild(new XMLElem("BULKMESINDC", "BULKMESINDC", 0));
/* 214 */     XMLMAP.addChild(new XMLElem("INSTALL", "INSTALL", 0));
/*     */ 
/*     */ 
/*     */     
/* 218 */     XMLMAP.addChild(new XMLElem("NOCSTSHIP", "NOCSTSHIP", 2));
/*     */ 
/*     */     
/* 221 */     XMLMAP.addChild(new XMLElem("WARRSVCCOVR", "WARRSVCCOVR", 0));
/*     */     
/* 223 */     XMLMAP.addChild(new XMLElem("RETURNEDPARTS", "RETURNEDPARTS", 0));
/*     */ 
/*     */     
/* 226 */     XMLElem xMLElem2 = new XMLElem("OSLIST");
/* 227 */     XMLMAP.addChild(xMLElem2);
/* 228 */     XMLChgSetElem xMLChgSetElem1 = new XMLChgSetElem("OSELEMENT");
/* 229 */     xMLElem2.addChild((XMLElem)xMLChgSetElem1);
/* 230 */     xMLChgSetElem1.addChild((XMLElem)new XMLMultiFlagElem("OS", "OSLEVEL", "OSACTION", 1));
/*     */ 
/*     */     
/* 233 */     XMLElem xMLElem3 = new XMLElem("LANGUAGELIST");
/* 234 */     XMLMAP.addChild(xMLElem3);
/*     */ 
/*     */     
/* 237 */     XMLNLSElem xMLNLSElem = new XMLNLSElem("LANGUAGEELEMENT");
/* 238 */     xMLElem3.addChild((XMLElem)xMLNLSElem);
/*     */     
/* 240 */     xMLNLSElem.addChild(new XMLElem("NLSID", "NLSID"));
/*     */ 
/*     */     
/* 243 */     xMLNLSElem.addChild((XMLElem)new XMLMktgInvElem("MKTGNAME", "MKTGNAME", "MKTGNAME", "U:FEATURE"));
/*     */ 
/*     */ 
/*     */     
/* 247 */     xMLNLSElem.addChild((XMLElem)new XMLMktgInvElem("INVNAME", "INVNAME", "INVNAME", "U:FEATURE"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 252 */     xMLElem2 = new XMLElem("AVAILABILITYLIST");
/* 253 */     XMLMAP.addChild(xMLElem2);
/* 254 */     xMLElem2.addChild((XMLElem)new XMLTMFAVAILElem());
/*     */ 
/*     */     
/* 257 */     xMLElem2 = new XMLElem("AUDIENCELIST");
/* 258 */     XMLMAP.addChild(xMLElem2);
/*     */     
/* 260 */     XMLChgSetElem xMLChgSetElem2 = new XMLChgSetElem("AUDIENCEELEMENT");
/* 261 */     xMLElem2.addChild((XMLElem)xMLChgSetElem2);
/*     */ 
/*     */     
/* 264 */     xMLChgSetElem2.addChild((XMLElem)new XMLMultiFlagElem("AUDIENCE", "AUDIEN", "AUDIENCEACTION", 0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     xMLElem2 = new XMLElem("CATALOGOVERRIDELIST");
/* 280 */     XMLMAP.addChild(xMLElem2);
/* 281 */     xMLElem2.addChild((XMLElem)new XMLCATAElem());
/*     */ 
/*     */     
/* 284 */     XMLGroupElem xMLGroupElem3 = new XMLGroupElem("WARRLIST", "WARR");
/* 285 */     XMLMAP.addChild((XMLElem)xMLGroupElem3);
/*     */     
/* 287 */     XMLElem xMLElem4 = new XMLElem("WARRELEMENT");
/* 288 */     xMLGroupElem3.addChild(xMLElem4);
/*     */     
/* 290 */     xMLElem4.addChild((XMLElem)new XMLActivityElem("WARRACTION"));
/* 291 */     xMLElem4.addChild(new XMLElem("WARRENTITYTYPE", "ENTITYTYPE"));
/* 292 */     xMLElem4.addChild(new XMLElem("WARRENTITYID", "ENTITYID"));
/* 293 */     xMLElem4.addChild(new XMLElem("WARRID", "WARRID"));
/* 294 */     xMLElem4.addChild(new XMLElem("WARRDESC", "INVNAME"));
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
/* 363 */     return "$Revision: 1.18 $";
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


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSPRODSTRUCTABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */