/*     */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*     */ 
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RdhMatm_sales_org
/*     */ {
/*     */   @SerializedName("VKORG")
/*     */   private String vkorg;
/*     */   @SerializedName("DWERK")
/*     */   private String dwerk;
/*     */   @SerializedName("ZTAXCLSF")
/*     */   private String ztaxclsf;
/*     */   @SerializedName("PROVG")
/*     */   private String provg;
/*     */   @SerializedName("ZZTAXID")
/*     */   private String zztaxid;
/*     */   @SerializedName("ZSABRTAX")
/*     */   private String zsabrtax;
/*     */   
/*     */   public String getVkorg() {
/*  31 */     return this.vkorg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVkorg(String paramString) {
/*  38 */     this.vkorg = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDwerk() {
/*  45 */     return this.dwerk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDwerk(String paramString) {
/*  52 */     this.dwerk = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getZtaxclsf() {
/*  59 */     return this.ztaxclsf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZtaxclsf(String paramString) {
/*  66 */     this.ztaxclsf = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getProvg() {
/*  73 */     return this.provg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProvg(String paramString) {
/*  80 */     this.provg = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getZztaxid() {
/*  87 */     return this.zztaxid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZztaxid(String paramString) {
/*  94 */     this.zztaxid = paramString;
/*     */   }
/*     */   public String getZsabrtax() {
/*  97 */     return this.zsabrtax;
/*     */   }
/*     */   public void setZsabrtax(String paramString) {
/* 100 */     this.zsabrtax = paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhMatm_sales_org.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */