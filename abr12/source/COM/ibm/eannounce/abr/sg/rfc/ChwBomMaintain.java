/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_csap_mbom;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_csap_mbom_Adapter;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomm_api03;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomm_csdata;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomm_csdep_dat;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomm_stko_api01;
/*     */ import COM.ibm.eannounce.abr.util.DateUtility;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChwBomMaintain
/*     */   extends RdhBase
/*     */ {
/*     */   @SerializedName("csap_mbom")
/*     */   private RdhBomc_csap_mbom_Adapter csap_mbom;
/*     */   @SerializedName("STPO_API03")
/*     */   private List<RdhBomm_api03> api03;
/*     */   @SerializedName("CSDEP_DAT")
/*     */   private List<RdhBomm_csdep_dat> csdep_dat;
/*     */   @SerializedName("STKO_API01")
/*     */   private List<RdhBomm_stko_api01> stko_api01;
/*     */   @SerializedName("CSDATA")
/*     */   private List<RdhBomm_csdata> csdata;
/*     */   
/*     */   public ChwBomMaintain(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/*  35 */     super(paramString1, "Z_DM_SAP_BOM_MAINTAIN".toLowerCase(), null);
/*  36 */     this.pims_identity = "H";
/*  37 */     this.default_mandt = "10H";
/*     */     
/*  39 */     if (getRfcrc() != 0)
/*     */       return; 
/*  41 */     System.out.println("111");
/*  42 */     System.out.println("csap_mbom=" + this.csap_mbom);
/*  43 */     this.csap_mbom.getMap().setMatnr(paramString1);
/*  44 */     this.csap_mbom.getMap().setWerks(paramString2);
/*  45 */     this.csap_mbom.getMap().setDatuv(DateUtility.getTodayStringWithSimpleFormat());
/*     */     
/*  47 */     RdhBomm_api03 rdhBomm_api03 = new RdhBomm_api03();
/*  48 */     rdhBomm_api03.setComponent(paramString3);
/*  49 */     rdhBomm_api03.setItem_no(paramString4);
/*  50 */     rdhBomm_api03.setItem_categ("N");
/*  51 */     rdhBomm_api03.setComp_qty("1");
/*  52 */     rdhBomm_api03.setComp_unit("EA");
/*  53 */     rdhBomm_api03.setRel_sales("X");
/*  54 */     rdhBomm_api03.setIdentifier("A1");
/*  55 */     this.api03.add(rdhBomm_api03);
/*     */     
/*  57 */     RdhBomm_csdep_dat rdhBomm_csdep_dat = new RdhBomm_csdep_dat();
/*  58 */     if (paramString5 != null) {
/*     */       
/*  60 */       rdhBomm_csdep_dat.setDep_intern(paramString5);
/*  61 */       rdhBomm_csdep_dat.setObject_id("2");
/*  62 */       rdhBomm_csdep_dat.setIdentifier("A1");
/*  63 */       rdhBomm_csdep_dat.setStatus("1");
/*  64 */       this.csdep_dat.add(rdhBomm_csdep_dat);
/*     */     } 
/*     */     
/*  67 */     RdhBomm_stko_api01 rdhBomm_stko_api01 = new RdhBomm_stko_api01();
/*  68 */     if (rdhBomm_stko_api01 != null) {
/*  69 */       rdhBomm_stko_api01.setBom_status("01");
/*  70 */       this.stko_api01.add(rdhBomm_stko_api01);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ChwBomMaintain(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {
/*  75 */     super(paramString2, "Z_DM_SAP_BOM_MAINTAIN".toLowerCase(), null);
/*  76 */     this.pims_identity = "H";
/*  77 */     this.default_mandt = "10H";
/*     */     
/*  79 */     if (getRfcrc() != 0)
/*     */       return; 
/*  81 */     System.out.println("111");
/*  82 */     System.out.println("csap_mbom=" + this.csap_mbom);
/*  83 */     this.csap_mbom.getMap().setMatnr(paramString1);
/*  84 */     this.csap_mbom.getMap().setWerks(paramString3);
/*  85 */     this.csap_mbom.getMap().setDatuv(DateUtility.getTodayStringWithSimpleFormat());
/*     */     
/*  87 */     RdhBomm_api03 rdhBomm_api03 = new RdhBomm_api03();
/*  88 */     rdhBomm_api03.setComponent(paramString4);
/*  89 */     rdhBomm_api03.setItem_no(paramString5);
/*  90 */     rdhBomm_api03.setItem_categ("N");
/*  91 */     rdhBomm_api03.setComp_qty("1");
/*  92 */     rdhBomm_api03.setComp_unit("EA");
/*  93 */     rdhBomm_api03.setRel_sales("X");
/*  94 */     rdhBomm_api03.setIdentifier("A1");
/*  95 */     this.api03.add(rdhBomm_api03);
/*     */     
/*  97 */     RdhBomm_csdep_dat rdhBomm_csdep_dat = new RdhBomm_csdep_dat();
/*  98 */     if (paramString6 != null) {
/*     */       
/* 100 */       rdhBomm_csdep_dat.setDep_intern(paramString6);
/* 101 */       rdhBomm_csdep_dat.setObject_id("2");
/* 102 */       rdhBomm_csdep_dat.setIdentifier("A1");
/* 103 */       rdhBomm_csdep_dat.setStatus("1");
/* 104 */       this.csdep_dat.add(rdhBomm_csdep_dat);
/*     */     } 
/*     */     
/* 107 */     RdhBomm_stko_api01 rdhBomm_stko_api01 = new RdhBomm_stko_api01();
/* 108 */     if (rdhBomm_stko_api01 != null) {
/* 109 */       rdhBomm_stko_api01.setBom_status("01");
/* 110 */       this.stko_api01.add(rdhBomm_stko_api01);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/* 122 */     this.csap_mbom = new RdhBomc_csap_mbom_Adapter();
/* 123 */     this.csap_mbom.setStuctName("csap_mbom");
/* 124 */     RdhBomc_csap_mbom rdhBomc_csap_mbom = new RdhBomc_csap_mbom();
/* 125 */     this.csap_mbom.setMap(rdhBomc_csap_mbom);
/* 126 */     rdhBomc_csap_mbom.setStlan("5");
/*     */     
/* 128 */     this.api03 = new ArrayList<>();
/* 129 */     this.csdep_dat = new ArrayList<>();
/* 130 */     this.stko_api01 = new ArrayList<>();
/* 131 */     this.csdata = new ArrayList<>();
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
/*     */   protected boolean isReadyToExecute() {
/* 150 */     if (getRfcrc() != 0)
/* 151 */       return false; 
/* 152 */     RdhBomc_csap_mbom rdhBomc_csap_mbom = this.csap_mbom.getMap();
/* 153 */     ArrayList<String> arrayList = new ArrayList();
/* 154 */     arrayList.add("matnr");
/* 155 */     arrayList.add("werks");
/* 156 */     if (checkFieldsNotEmplyOrNull(rdhBomc_csap_mbom, arrayList, true)) {
/* 157 */       return checkFieldsNotEmplyOrNullInCollection(this.api03, "component");
/*     */     }
/*     */     
/* 160 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwBomMaintain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */