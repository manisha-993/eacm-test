/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.GARSYMDMSalesBom_CUKB;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.GARSYMDMSalesBom_MAST;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.GARSYMDMSalesBom_STKO;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.GARSYMDMSalesBom_STPO;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GARSYMDMSalesBom
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("TBL_MAST")
/*    */   private List<GARSYMDMSalesBom_MAST> tbl_mast;
/*    */   @SerializedName("TBL_STKO")
/*    */   private List<GARSYMDMSalesBom_STKO> tbl_stko;
/*    */   @SerializedName("TBL_STPO")
/*    */   private List<GARSYMDMSalesBom_STPO> tbl_stpo;
/*    */   @SerializedName("TBL_CUKB")
/*    */   private List<GARSYMDMSalesBom_CUKB> tbl_cukb;
/*    */   @SerializedName("MACHINE_TYPE")
/*    */   private String machine_type;
/*    */   @SerializedName("MODEL")
/*    */   private String model;
/*    */   
/*    */   public GARSYMDMSalesBom(MODEL paramMODEL, String paramString) {
/* 35 */     super(paramMODEL.getMACHTYPE() + "BOMFEA" + paramString, "RDH_YMDMSALES_BOM".toLowerCase(), null);
/* 36 */     this.pims_identity = "H";
/* 37 */     this.machine_type = paramMODEL.getMACHTYPE();
/* 38 */     this.model = paramMODEL.getMODEL();
/* 39 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMdd");
/*    */     
/* 41 */     GARSYMDMSalesBom_MAST gARSYMDMSalesBom_MAST = new GARSYMDMSalesBom_MAST();
/* 42 */     gARSYMDMSalesBom_MAST.setMatnr(this.machine_type + "FEA");
/* 43 */     gARSYMDMSalesBom_MAST.setWerks(paramString);
/* 44 */     gARSYMDMSalesBom_MAST.setLosvn("0.000");
/* 45 */     gARSYMDMSalesBom_MAST.setLosbs("0.000");
/* 46 */     this.tbl_mast.add(gARSYMDMSalesBom_MAST);
/*    */     
/* 48 */     GARSYMDMSalesBom_STKO gARSYMDMSalesBom_STKO = new GARSYMDMSalesBom_STKO();
/* 49 */     gARSYMDMSalesBom_STKO.setMatnr(this.machine_type + "FEA");
/* 50 */     gARSYMDMSalesBom_STKO.setWerks(paramString);
/* 51 */     gARSYMDMSalesBom_STKO.setDatuv(simpleDateFormat.format(new Date()));
/* 52 */     this.tbl_stko.add(gARSYMDMSalesBom_STKO);
/*    */     
/* 54 */     GARSYMDMSalesBom_STPO gARSYMDMSalesBom_STPO = new GARSYMDMSalesBom_STPO();
/* 55 */     gARSYMDMSalesBom_STPO.setMatnr(this.machine_type + "FEA");
/* 56 */     gARSYMDMSalesBom_STPO.setWerks(paramString);
/* 57 */     gARSYMDMSalesBom_STPO.setIdnrk(this.machine_type + this.model);
/* 58 */     this.tbl_stpo.add(gARSYMDMSalesBom_STPO);
/*    */     
/* 60 */     GARSYMDMSalesBom_CUKB gARSYMDMSalesBom_CUKB = new GARSYMDMSalesBom_CUKB();
/* 61 */     gARSYMDMSalesBom_CUKB.setMatnr(this.machine_type + "FEA");
/* 62 */     gARSYMDMSalesBom_CUKB.setWerks(paramString);
/* 63 */     gARSYMDMSalesBom_CUKB.setIdnrk(this.machine_type + this.model);
/* 64 */     gARSYMDMSalesBom_CUKB.setDep_intern("SC_" + this.machine_type + "_MOD_" + this.model);
/* 65 */     gARSYMDMSalesBom_CUKB.setDep_type("5");
/* 66 */     this.tbl_cukb.add(gARSYMDMSalesBom_CUKB);
/*    */   }
/*    */   protected void setDefaultValues() {
/* 69 */     this.tbl_mast = new ArrayList<>();
/* 70 */     this.tbl_stko = new ArrayList<>();
/* 71 */     this.tbl_stpo = new ArrayList<>();
/* 72 */     this.tbl_cukb = new ArrayList<>();
/*    */   }
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 76 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\GARSYMDMSalesBom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */