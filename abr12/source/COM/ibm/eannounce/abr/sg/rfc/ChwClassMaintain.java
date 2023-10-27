/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClas_cla_ch_atr;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClas_cla_descr;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClas_clclasses;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChwClassMaintain
/*     */   extends RdhBase
/*     */ {
/*     */   @SerializedName("CLCLASSES")
/*     */   private List<RdhClas_clclasses> clclasses_list;
/*     */   @Foo
/*     */   private RdhClas_clclasses clclasses;
/*     */   @SerializedName("CLA_DESCR")
/*     */   private List<RdhClas_cla_descr> cla_descr_list;
/*     */   @Foo
/*     */   private RdhClas_cla_descr cla_descr;
/*     */   @SerializedName("CLA_CH_ATR")
/*     */   private List<RdhClas_cla_ch_atr> cla_ch_atr;
/*     */   
/*     */   public ChwClassMaintain(String paramString1, String paramString2, String paramString3) {
/*  46 */     super(paramString1, "Z_DM_SAP_CLASS_MAINTAIN".toLowerCase(), null);
/*  47 */     this.rfa_num = paramString1;
/*  48 */     this.pims_identity = "H";
/*  49 */     this.default_mandt = "10H";
/*     */ 
/*     */     
/*  52 */     this.clclasses.set_class(paramString2);
/*  53 */     this.cla_descr.set_class(paramString2);
/*  54 */     this.cla_descr.setCatchword(paramString3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/*  60 */     super.setDefaultValues();
/*  61 */     this.clclasses = new RdhClas_clclasses();
/*  62 */     this.cla_descr = new RdhClas_cla_descr();
/*  63 */     this.cla_ch_atr = new ArrayList<>();
/*  64 */     this.clclasses_list = new ArrayList<>();
/*  65 */     this.clclasses_list.add(this.clclasses);
/*  66 */     this.cla_descr_list = new ArrayList<>();
/*  67 */     this.cla_descr_list.add(this.cla_descr);
/*     */     
/*  69 */     this.clclasses.setStatus("1");
/*     */     
/*  71 */     this.clclasses.setClass_type("300");
/*     */ 
/*     */     
/*  74 */     this.clclasses.setOrg_area("");
/*     */     
/*  76 */     this.clclasses.setVal_from(DateUtility.getTodayStringWithSimpleFormat());
/*     */     
/*  78 */     this.clclasses.setVal_to("99991231");
/*     */     
/*  80 */     this.clclasses.setCheck_no("X");
/*     */ 
/*     */     
/*  83 */     this.cla_descr.setClass_type("300");
/*     */     
/*  85 */     this.cla_descr.setLanguage("E");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCharacteristic(String paramString) {
/*  96 */     RdhClas_cla_ch_atr rdhClas_cla_ch_atr = new RdhClas_cla_ch_atr();
/*  97 */     rdhClas_cla_ch_atr.set_class(this.clclasses.get_class());
/*  98 */     rdhClas_cla_ch_atr.setClass_type(this.clclasses.getClass_type());
/*  99 */     rdhClas_cla_ch_atr.setCharact(paramString);
/* 100 */     this.cla_ch_atr.add(rdhClas_cla_ch_atr);
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
/* 119 */     if (checkFieldsNotEmplyOrNull(this.clclasses, "cLASS"))
/*     */     {
/* 121 */       if (checkFieldsNotEmplyOrNull(this.cla_descr, "catchword")) {
/*     */         
/* 123 */         for (RdhClas_cla_ch_atr rdhClas_cla_ch_atr : this.cla_ch_atr) {
/*     */           
/* 125 */           if (isNullOrBlank(rdhClas_cla_ch_atr.getCharact())) {
/*     */             
/* 127 */             setRfcrc(8);
/* 128 */             setError_text("One RdhClas_cla_ch_atr.charact attribute is not set to a value(class=" + rdhClas_cla_ch_atr
/* 129 */                 .get_class() + " classtype=" + rdhClas_cla_ch_atr.getClass_type() + ")");
/* 130 */             return false;
/*     */           } 
/*     */         } 
/* 133 */         return true;
/*     */       } 
/*     */     }
/* 136 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwClassMaintain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */