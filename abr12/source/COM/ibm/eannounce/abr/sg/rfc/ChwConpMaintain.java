/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_cpdep_dat;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_cpro_attr;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_object_key;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_rcuco;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChwConpMaintain
/*     */   extends RdhBase
/*     */ {
/*     */   @SerializedName("OBJECT_KEY")
/*     */   private List<RdhConp_object_key> object_key;
/*     */   @SerializedName("CPRO_ATTR")
/*     */   private List<RdhConp_cpro_attr> cpro_attr;
/*     */   @SerializedName("CPDEP_DAT")
/*     */   private List<RdhConp_cpdep_dat> cpdep_dat;
/*     */   @SerializedName("RCUCO")
/*     */   private List<RdhConp_rcuco> rcuco;
/*     */   
/*     */   public ChwConpMaintain(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {
/*  33 */     super(paramString6, "Z_DM_SAP_CONP_MAINTAIN".toLowerCase(), null);
/*  34 */     this.pims_identity = "H";
/*  35 */     this.rfa_num = paramString6;
/*     */     
/*  37 */     ((RdhConp_object_key)this.object_key.get(0)).setKey_feld("MATNR");
/*  38 */     ((RdhConp_object_key)this.object_key.get(0)).setKpara_valu(paramString1);
/*  39 */     if (paramString2 != null) {
/*  40 */       ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setC_profile(paramString2);
/*     */     }
/*     */     
/*  43 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setClasstype("300");
/*  44 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setOrgareas("");
/*  45 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setStatus("1");
/*     */ 
/*     */     
/*  48 */     if (paramString3 != null && paramString4 != null && Integer.parseInt(paramString4) > 0) {
/*     */ 
/*     */       
/*  51 */       ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setBomappl(paramString3);
/*     */       
/*  53 */       ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setBomexpl(paramString4);
/*     */     } 
/*  55 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setTasklexpl("");
/*  56 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setInitscreen("");
/*  57 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setFlassembly("");
/*  58 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setFlresult("");
/*  59 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setFlmdata("");
/*  60 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setFlcasonly("");
/*  61 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setA_laiso("");
/*  62 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setDesign(paramString5);
/*  63 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setPrio("00");
/*  64 */     ((RdhConp_cpro_attr)this.cpro_attr.get(0)).setKz_browser("X");
/*     */     
/*  66 */     ((RdhConp_rcuco)this.rcuco.get(0)).setObtab("MARA");
/*     */     
/*  68 */     this.cpdep_dat = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isReadyToExecute() {
/*  74 */     if (checkFieldsNotEmplyOrNull(this.object_key.get(0), "kpara_valu")) {
/*  75 */       return checkFieldsNotEmplyOrNullInCollection(this.cpdep_dat, "dep_intern");
/*     */     }
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addConfigDependency(String paramString1, String paramString2) {
/*  82 */     RdhConp_cpdep_dat rdhConp_cpdep_dat = new RdhConp_cpdep_dat();
/*  83 */     this.cpdep_dat.add(rdhConp_cpdep_dat);
/*  84 */     rdhConp_cpdep_dat.setC_profile(((RdhConp_cpro_attr)this.cpro_attr.get(0)).getC_profile());
/*  85 */     rdhConp_cpdep_dat.setDep_intern(paramString1);
/*  86 */     rdhConp_cpdep_dat.setFldelete(paramString2);
/*  87 */     rdhConp_cpdep_dat.setStatus("1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/*  96 */     this.object_key = new ArrayList<>();
/*  97 */     RdhConp_object_key rdhConp_object_key = new RdhConp_object_key();
/*  98 */     this.object_key.add(rdhConp_object_key);
/*     */     
/* 100 */     this.cpro_attr = new ArrayList<>();
/* 101 */     RdhConp_cpro_attr rdhConp_cpro_attr = new RdhConp_cpro_attr();
/* 102 */     this.cpro_attr.add(rdhConp_cpro_attr);
/*     */     
/* 104 */     this.cpdep_dat = new ArrayList<>();
/* 105 */     RdhConp_cpdep_dat rdhConp_cpdep_dat = new RdhConp_cpdep_dat();
/* 106 */     this.cpdep_dat.add(rdhConp_cpdep_dat);
/*     */     
/* 108 */     this.rcuco = new ArrayList<>();
/* 109 */     RdhConp_rcuco rdhConp_rcuco = new RdhConp_rcuco();
/* 110 */     this.rcuco.add(rdhConp_rcuco);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwConpMaintain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */