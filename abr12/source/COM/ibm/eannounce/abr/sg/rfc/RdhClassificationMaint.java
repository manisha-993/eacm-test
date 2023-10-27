/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_api_ausp;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_klah;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_kssk;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_mara;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_object_key;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_rcuco;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RdhClassificationMaint
/*     */   extends RdhBase
/*     */ {
/*     */   @SerializedName("CHARVAL_REFRESH")
/*     */   private String charval_refresh;
/*     */   @SerializedName("OBJECT_KEY")
/*     */   private List<RdhClaf_object_key> object_keys;
/*     */   @Foo
/*     */   private RdhClaf_object_key object_key;
/*     */   @SerializedName("KLAH")
/*     */   private List<RdhClaf_klah> klahs;
/*     */   @Foo
/*     */   private RdhClaf_klah klah;
/*     */   @SerializedName("KSSK")
/*     */   private List<RdhClaf_kssk> kssks;
/*     */   @Foo
/*     */   private RdhClaf_kssk kssk;
/*     */   @SerializedName("RCUCO")
/*     */   private List<RdhClaf_rcuco> rcucos;
/*     */   @Foo
/*     */   private RdhClaf_rcuco rcuco;
/*     */   @SerializedName("MARA")
/*     */   private List<RdhClaf_mara> maras;
/*     */   @Foo
/*     */   private RdhClaf_mara mara;
/*     */   @SerializedName("API_AUSP")
/*     */   private List<RdhClaf_api_ausp> api_ausp;
/*     */   
/*     */   public RdhClassificationMaint(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 102 */     super(paramString4, "Z_DM_SAP_CLASSIFICATION_MAINT".toLowerCase(), null);
/* 103 */     this.charval_refresh = "1";
/* 104 */     this.object_key = new RdhClaf_object_key();
/* 105 */     this.object_key.setKey_feld("MATNR");
/* 106 */     this.object_key.setKpara_valu(paramString1);
/* 107 */     this.object_keys = new ArrayList<>();
/* 108 */     this.object_keys.add(this.object_key);
/* 109 */     this.klah = new RdhClaf_klah();
/* 110 */     this.klah.set_class(paramString2);
/* 111 */     this.klahs = new ArrayList<>();
/* 112 */     this.klahs.add(this.klah);
/* 113 */     this.kssk = new RdhClaf_kssk();
/* 114 */     this.kssk.setKlart(paramString3);
/* 115 */     this.kssks = new ArrayList<>();
/* 116 */     this.kssks.add(this.kssk);
/* 117 */     this.api_ausp = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RdhClassificationMaint(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 127 */     super(paramString5, "Z_DM_SAP_CLASSIFICATION_MAINT".toLowerCase(), null);
/* 128 */     this.pims_identity = paramString4;
/* 129 */     this.charval_refresh = "1";
/* 130 */     this.object_key = new RdhClaf_object_key();
/* 131 */     this.object_key.setKey_feld("MATNR");
/* 132 */     this.object_key.setKpara_valu(paramString1);
/* 133 */     this.object_keys = new ArrayList<>();
/* 134 */     this.object_keys.add(this.object_key);
/* 135 */     this.klah = new RdhClaf_klah();
/* 136 */     this.klah.set_class(paramString2);
/* 137 */     this.klahs = new ArrayList<>();
/* 138 */     this.klahs.add(this.klah);
/* 139 */     this.kssk = new RdhClaf_kssk();
/* 140 */     this.kssk.setKlart(paramString3);
/* 141 */     this.kssks = new ArrayList<>();
/* 142 */     this.kssks.add(this.kssk);
/* 143 */     this.api_ausp = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCharacteristic(String paramString1, String paramString2) {
/* 154 */     RdhClaf_api_ausp rdhClaf_api_ausp = new RdhClaf_api_ausp();
/* 155 */     rdhClaf_api_ausp.setCharact(paramString1);
/* 156 */     rdhClaf_api_ausp.setValue(paramString2);
/*     */     
/* 158 */     if (paramString2 != null && paramString2.length() > 0) {
/* 159 */       this.api_ausp.add(rdhClaf_api_ausp);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCharacteristicCount() {
/* 170 */     return this.api_ausp.size();
/*     */   }
/*     */   
/*     */   public String getClassificationName() {
/* 174 */     return this.klah.get_class();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/* 183 */     super.setDefaultValues();
/* 184 */     this.charval_refresh = "1";
/* 185 */     this.rcuco = new RdhClaf_rcuco();
/* 186 */     this.rcuco.setObtab("MARA");
/* 187 */     this.rcucos = new ArrayList<>();
/* 188 */     this.rcucos.add(this.rcuco);
/* 189 */     this.mara = new RdhClaf_mara();
/* 190 */     this.mara.setErsda(DateUtility.getTodayStringWithSimpleFormat());
/* 191 */     this.maras = new ArrayList<>();
/* 192 */     this.maras.add(this.mara);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isReadyToExecute() {
/* 198 */     if (checkFieldsNotEmplyOrNull(this.object_key, "kpara_valu"))
/*     */     {
/*     */       
/* 201 */       if (checkFieldsNotEmplyOrNull(this.klah, "_class"))
/*     */       {
/*     */         
/* 204 */         if (checkFieldsNotEmplyOrNull(this.kssk, "klart"))
/*     */         {
/* 206 */           return checkFieldsNotEmplyOrNullInCollection(this.api_ausp, "value");
/*     */         }
/*     */       }
/*     */     }
/* 210 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhClassificationMaint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */