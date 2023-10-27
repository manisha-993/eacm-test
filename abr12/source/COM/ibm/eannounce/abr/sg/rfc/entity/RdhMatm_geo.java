/*     */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*     */ 
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RdhMatm_geo
/*     */ {
/*     */   @SerializedName("NAME")
/*     */   private String name;
/*     */   @SerializedName("VMSTA")
/*     */   private String vmsta;
/*     */   @SerializedName("VMSTD")
/*     */   private String vmstd;
/*     */   @SerializedName("SALES_ORG")
/*     */   private List<RdhMatm_sales_org> sales_orgs;
/*     */   @SerializedName("TAX_COUNTRY")
/*     */   private List<RdhMatm_tax_country> tax_countries;
/*     */   @SerializedName("PLANT")
/*     */   private List<RdhMatm_plant> plants;
/*     */   
/*     */   public List<RdhMatm_tax_country> getTax_countries() {
/*  28 */     return this.tax_countries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTax_countries(List<RdhMatm_tax_country> paramList) {
/*  35 */     this.tax_countries = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  43 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String paramString) {
/*  50 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVmsta() {
/*  57 */     return this.vmsta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVmsta(String paramString) {
/*  64 */     this.vmsta = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVmstd() {
/*  71 */     return this.vmstd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVmstd(String paramString) {
/*  78 */     this.vmstd = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RdhMatm_sales_org> getSales_orgs() {
/*  85 */     return this.sales_orgs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSales_orgs(List<RdhMatm_sales_org> paramList) {
/*  92 */     this.sales_orgs = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RdhMatm_plant> getPlants() {
/*  99 */     return this.plants;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlants(List<RdhMatm_plant> paramList) {
/* 106 */     this.plants = paramList;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\RdhMatm_geo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */