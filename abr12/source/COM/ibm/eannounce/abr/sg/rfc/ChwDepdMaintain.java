/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhDepd_dep_ident;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhDepd_depdat;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhDepd_depdescr;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhDepd_depsource;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChwDepdMaintain
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("DEP_IDENT")
/*    */   private List<RdhDepd_dep_ident> dep_ident;
/*    */   @SerializedName("DEPDAT")
/*    */   private List<RdhDepd_depdat> depdat;
/*    */   @SerializedName("DEPDESCR")
/*    */   private List<RdhDepd_depdescr> depdescr;
/*    */   @SerializedName("DEPSOURCE")
/*    */   private List<RdhDepd_depsource> depsource;
/*    */   
/*    */   public ChwDepdMaintain(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 37 */     super(paramString1, "Z_DM_SAP_DEPD_MAINTAIN".toLowerCase(), null);
/* 38 */     this.pims_identity = "H";
/* 39 */     this.rfa_num = paramString1;
/*    */     
/* 41 */     ((RdhDepd_dep_ident)this.dep_ident.get(0)).setDep_extern(paramString2);
/* 42 */     ((RdhDepd_depdat)this.depdat.get(0)).setDep_type(paramString3);
/* 43 */     ((RdhDepd_depdat)this.depdat.get(0)).setStatus("1");
/* 44 */     ((RdhDepd_depdescr)this.depdescr.get(0)).setLanguage("E");
/* 45 */     ((RdhDepd_depdescr)this.depdescr.get(0)).setDescript(paramString4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {
/* 56 */     this.dep_ident = new ArrayList<>();
/* 57 */     RdhDepd_dep_ident rdhDepd_dep_ident = new RdhDepd_dep_ident();
/* 58 */     this.dep_ident.add(rdhDepd_dep_ident);
/*    */     
/* 60 */     this.depdat = new ArrayList<>();
/* 61 */     RdhDepd_depdat rdhDepd_depdat = new RdhDepd_depdat();
/* 62 */     rdhDepd_depdat.setStatus("1");
/* 63 */     this.depdat.add(rdhDepd_depdat);
/*    */     
/* 65 */     this.depdescr = new ArrayList<>();
/* 66 */     RdhDepd_depdescr rdhDepd_depdescr = new RdhDepd_depdescr();
/* 67 */     rdhDepd_depdescr.setLanguage("E");
/* 68 */     this.depdescr.add(rdhDepd_depdescr);
/*    */     
/* 70 */     this.depsource = new ArrayList<>();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 78 */     if (checkFieldsNotEmplyOrNull(this.dep_ident.get(0), "dep_extern"))
/*    */     {
/*    */       
/* 81 */       if (checkFieldsNotEmplyOrNull(this.depdat.get(0), "dep_type"))
/*    */       {
/*    */         
/* 84 */         return checkFieldsNotEmplyOrNull(this.depdescr.get(0), "descript");
/*    */       }
/*    */     }
/* 87 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSourceLineCondition(String paramString) {
/* 93 */     RdhDepd_depsource rdhDepd_depsource = new RdhDepd_depsource();
/* 94 */     rdhDepd_depsource.setLine(paramString);
/* 95 */     this.depsource.add(rdhDepd_depsource);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwDepdMaintain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */