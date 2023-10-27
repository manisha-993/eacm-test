/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
/*     */ import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
/*     */ import com.ibm.rdh.chw.entity.CHWAnnouncement;
/*     */ import com.ibm.rdh.chw.entity.CHWGeoAnn;
/*     */ import com.ibm.rdh.chw.entity.LifecycleData;
/*     */ import com.ibm.rdh.chw.entity.TypeFeatureUPGGeo;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ public class RFCFCTRANSACTIONABR
/*     */   extends RfcAbrAdapter
/*     */ {
/*  27 */   String SaleOrg_0147 = "0147";
/*     */ 
/*     */ 
/*     */   
/*     */   public RFCFCTRANSACTIONABR(RFCABRSTATUS paramRFCABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
/*  32 */     super(paramRFCABRSTATUS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processThis() throws RfcAbrException, HWPIMSAbnormalException, Exception {
/*  37 */     this.abr.addDebug("Start processThis()");
/*  38 */     EntityList entityList = null;
/*  39 */     boolean bool = false;
/*  40 */     EntityItem entityItem = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  49 */       entityItem = getRooEntityItem();
/*     */       
/*  51 */       if (!"0020".equals(getAttributeFlagValue(entityItem, "STATUS"))) {
/*  52 */         throw new RfcAbrException("The status is not final, it will not promote this FCTRANSACTION");
/*     */       }
/*  54 */       String str1 = getAttributeValue(entityItem, "FROMMACHTYPE");
/*  55 */       String str2 = getAttributeValue(entityItem, "FROMFEATURECODE");
/*  56 */       String str3 = getAttributeValue(entityItem, "TOMACHTYPE");
/*  57 */       String str4 = getAttributeValue(entityItem, "TOFEATURECODE");
/*     */ 
/*     */       
/*  60 */       TypeFeatureUPGGeo typeFeatureUPGGeo = new TypeFeatureUPGGeo();
/*     */       
/*  62 */       typeFeatureUPGGeo.setType(str3);
/*  63 */       typeFeatureUPGGeo.setFromType(str1);
/*  64 */       typeFeatureUPGGeo.setFeature(str4);
/*  65 */       typeFeatureUPGGeo.setFromFeature(str2);
/*  66 */       typeFeatureUPGGeo.setGeography("LA");
/*     */ 
/*     */       
/*  69 */       CHWAnnouncement cHWAnnouncement = new CHWAnnouncement();
/*     */ 
/*     */ 
/*     */       
/*  73 */       cHWAnnouncement.setAnnDocNo(str1 + "-" + str3);
/*     */ 
/*     */       
/*  76 */       cHWAnnouncement.setAnnouncementType("NEW");
/*     */ 
/*     */       
/*  79 */       String str5 = "C";
/*  80 */       this.abr.addDebug("PimsIdentity: " + str5);
/*     */ 
/*     */       
/*  83 */       bool = "Yes".equals(getAttributeFlagValue(entityItem, "SYSFEEDRESEND"));
/*  84 */       this.abr.addDebug("Resend full: " + bool);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = getAttributeHistory(entityItem, "RFCABRSTATUS");
/*  90 */       boolean bool1 = existBefore(attributeChangeHistoryGroup, "0030");
/*  91 */       this.abr.addDebug("Is passed RFCABRSTATUS exist before: " + bool1);
/*  92 */       String str6 = getAttributeValue(entityItem, "ANNDATE");
/*     */       
/*  94 */       EntityItem entityItem1 = null;
/*  95 */       boolean bool2 = false;
/*  96 */       boolean bool3 = false;
/*  97 */       if (!bool && bool1) {
/*  98 */         bool2 = true;
/*  99 */         String str = getLatestValFromForAttributeValue(attributeChangeHistoryGroup, "0030");
/* 100 */         if (str == null || "".equals(str)) {
/* 101 */           this.abr.addDebug("t1DTS is null");
/*     */         } else {
/* 103 */           String str9 = this.abr.getCurrentTime();
/* 104 */           Profile profile = this.abr.switchRole("BHFEED");
/* 105 */           profile.setValOnEffOn(str, str);
/* 106 */           profile.setEndOfDay(str9);
/* 107 */           profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 108 */           profile.setLoginTime(str9);
/* 109 */           this.abr.addDebug("Get t1 entity list for t1DTS: " + str + " t2DTS: " + str9);
/* 110 */           entityList = getEntityList(profile);
/* 111 */           this.abr.addDebug("EntityList for T1 " + profile.getValOn() + " extract " + getVeName() + " contains the following entities: \n" + 
/* 112 */               PokUtils.outputList(entityList));
/* 113 */           entityItem1 = entityList.getParentEntityGroup().getEntityItem(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 120 */           String str10 = getAttributeValue(entityItem1, "ANNDATE");
/* 121 */           bool3 = istfuGeoChanged(str6, str10);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 126 */       this.rdhRestProxy.r162(typeFeatureUPGGeo, null, "UPG", cHWAnnouncement, null, str5);
/*     */ 
/*     */       
/* 129 */       if (!bool2 || bool3) {
/* 130 */         this.abr.addDebug("---------start  !tfuGeoPromoted || tfuGeoChanged-----------------");
/*     */         
/* 132 */         CHWGeoAnn cHWGeoAnn = new CHWGeoAnn();
/* 133 */         SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
/*     */         
/* 135 */         cHWGeoAnn.setAnnouncementDate(simpleDateFormat1.parse(str6));
/* 136 */         this.abr.addDebug("CHWAnnouncementGEO: " + cHWGeoAnn.toString());
/*     */         
/* 138 */         LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(typeFeatureUPGGeo);
/*     */ 
/*     */         
/* 141 */         updateAnnLifecyle(lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), cHWGeoAnn.getAnnouncementDate(), cHWAnnouncement
/* 142 */             .getAnnDocNo(), cHWAnnouncement.getAnnouncementType(), str5, this.SaleOrg_0147);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 149 */         this.abr.addDebug("---------end !tfuGeoPromoted || tfuGeoChanged-----------------");
/*     */       } 
/*     */       
/* 152 */       this.abr.addDebug("----------------------- Start Promote WDFM Announcement for FCTRANSACTION Withdraw From Market -----------------------");
/*     */       
/* 154 */       boolean bool4 = false;
/* 155 */       boolean bool5 = false;
/*     */       
/* 157 */       String str7 = "";
/* 158 */       String str8 = "";
/*     */       
/* 160 */       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 161 */       str8 = getAttributeValue(entityItem, "WTHDRWEFFCTVDATE");
/* 162 */       this.abr.addDebug("----------- FCTRANSACTION t2 WTHDRWEFFCTVDATE=" + str8);
/* 163 */       if (str8 != null && !"".equals(str8)) {
/* 164 */         Date date = simpleDateFormat.parse(str8);
/*     */ 
/*     */ 
/*     */         
/* 168 */         if (entityItem1 == null) {
/* 169 */           str7 = "";
/*     */         } else {
/* 171 */           str7 = getAttributeValue(entityItem1, "WTHDRWEFFCTVDATE");
/*     */         } 
/* 173 */         this.abr.addDebug("----------- FCTRANSACTION t1 WTHDRWEFFCTVDATE=" + str7);
/*     */         
/* 175 */         if (bool || "".equals(str7)) {
/* 176 */           bool4 = false;
/* 177 */           bool5 = false;
/* 178 */         } else if (str7.equals(str8)) {
/* 179 */           bool4 = true;
/* 180 */           bool5 = false;
/*     */         } else {
/* 182 */           bool5 = true;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 190 */         this.abr.addDebug("isTmwPromoted: " + bool4 + " isTmwChanged: " + bool5);
/* 191 */         LifecycleDataGenerator lifecycleDataGenerator = new LifecycleDataGenerator(typeFeatureUPGGeo);
/* 192 */         String str = cHWAnnouncement.getAnnDocNo();
/* 193 */         if (!bool4 || bool5 == true) {
/* 194 */           LifecycleData lifecycleData = this.rdhRestProxy.r200(lifecycleDataGenerator.getMaterial(), lifecycleDataGenerator.getVarCond(), str, "wdfm", str5, this.SaleOrg_0147);
/*     */           
/* 196 */           this.abr.addDebug("Call r200 successfully for SalesOrg 0147 " + lifecycleData);
/* 197 */           updateWDFMLifecyle(lifecycleData, lifecycleDataGenerator.getVarCond(), lifecycleDataGenerator.getMaterial(), date, str, str5, this.SaleOrg_0147);
/*     */           
/* 199 */           this.abr.addDebug("updateWDFMLifecyle successfully for SalesOrg " + this.SaleOrg_0147);
/*     */         } 
/* 201 */         if (!bool4 || bool5 == true);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 207 */       this.abr.addDebug("----------------------- End Promote WDFM Announcement for FCTRANSACTION Withdraw From Market -----------------------");
/*     */ 
/*     */ 
/*     */       
/* 211 */       if (needReleaseParkingTable()) {
/* 212 */         this.rdhRestProxy.r144(cHWAnnouncement.getAnnDocNo(), "R", str5);
/* 213 */         this.abr.addDebug("Call R144 update parking table successfully");
/*     */       } else {
/* 215 */         this.rdhRestProxy.r144(cHWAnnouncement.getAnnDocNo(), "H", str5);
/*     */       } 
/*     */     } finally {
/*     */       
/* 219 */       if (bool && entityItem != null) {
/* 220 */         setFlagValue("SYSFEEDRESEND", "No", entityItem);
/*     */       }
/*     */       
/* 223 */       if (entityList != null) {
/* 224 */         entityList.dereference();
/* 225 */         entityList = null;
/*     */       } 
/* 227 */       if (this.entityList != null) {
/* 228 */         this.entityList.dereference();
/* 229 */         this.entityList = null;
/*     */       } 
/* 231 */       this.abr.addDebug("End processThis()");
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean istfuGeoChanged(String paramString1, String paramString2) {
/* 236 */     boolean bool = false;
/* 237 */     if (paramString1 == null) {
/* 238 */       if (paramString2 == null) {
/* 239 */         bool = false;
/*     */       } else {
/* 241 */         bool = true;
/*     */       }
/*     */     
/* 244 */     } else if (paramString2 == null) {
/* 245 */       bool = true;
/*     */     }
/* 247 */     else if (paramString2.equals(paramString1)) {
/* 248 */       bool = false;
/*     */     } else {
/* 250 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 254 */     return bool;
/*     */   }
/*     */   
/*     */   public String getVeName() {
/* 258 */     return "RFCFCTRANSACTIONVE";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RFCFCTRANSACTIONABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */