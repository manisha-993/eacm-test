/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMSalesBom_CUKB;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMSalesBom_MAST;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMSalesBom_STKO;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMSalesBom_STPO;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChwBulkYMDMSalesBom
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("TBL_MAST")
/*    */   private ArrayList<ChwBulkYMDMSalesBom_MAST> tbl_mast;
/*    */   @SerializedName("TBL_STKO")
/*    */   private ArrayList<ChwBulkYMDMSalesBom_STKO> tbl_stko;
/*    */   @SerializedName("TBL_STPO")
/*    */   private ArrayList<ChwBulkYMDMSalesBom_STPO> tbl_stpo;
/*    */   @SerializedName("TBL_CUKB")
/*    */   private ArrayList<ChwBulkYMDMSalesBom_CUKB> tbl_cukb;
/*    */   
/*    */   public ChwBulkYMDMSalesBom(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 28 */     super(paramString1 + "BOMMEB" + paramString4, "RDH_YMDMSALES_BOM".toLowerCase(), null);
/* 29 */     this.pims_identity = "H";
/*    */     
/* 31 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMdd");
/*    */     
/* 33 */     ChwBulkYMDMSalesBom_MAST chwBulkYMDMSalesBom_MAST = new ChwBulkYMDMSalesBom_MAST();
/* 34 */     chwBulkYMDMSalesBom_MAST.setMatnr(paramString1 + "MEB");
/* 35 */     chwBulkYMDMSalesBom_MAST.setWerks(paramString4);
/* 36 */     chwBulkYMDMSalesBom_MAST.setLosvn("0.000");
/* 37 */     chwBulkYMDMSalesBom_MAST.setLosbs("0.000");
/* 38 */     this.tbl_mast.add(chwBulkYMDMSalesBom_MAST);
/*    */     
/* 40 */     ChwBulkYMDMSalesBom_STKO chwBulkYMDMSalesBom_STKO = new ChwBulkYMDMSalesBom_STKO();
/* 41 */     chwBulkYMDMSalesBom_STKO.setMatnr(paramString1 + "MEB");
/* 42 */     chwBulkYMDMSalesBom_STKO.setWerks(paramString4);
/* 43 */     chwBulkYMDMSalesBom_STKO.setDatuv(simpleDateFormat.format(new Date()));
/* 44 */     this.tbl_stko.add(chwBulkYMDMSalesBom_STKO);
/*    */     
/* 46 */     ChwBulkYMDMSalesBom_STPO chwBulkYMDMSalesBom_STPO1 = new ChwBulkYMDMSalesBom_STPO();
/* 47 */     chwBulkYMDMSalesBom_STPO1.setMatnr(paramString1 + "MEB");
/* 48 */     chwBulkYMDMSalesBom_STPO1.setWerks(paramString4);
/* 49 */     chwBulkYMDMSalesBom_STPO1.setIdnrk(paramString1 + "F" + paramString3);
/* 50 */     this.tbl_stpo.add(chwBulkYMDMSalesBom_STPO1);
/*    */     
/* 52 */     ChwBulkYMDMSalesBom_STPO chwBulkYMDMSalesBom_STPO2 = new ChwBulkYMDMSalesBom_STPO();
/* 53 */     chwBulkYMDMSalesBom_STPO2.setMatnr(paramString1 + "MEB");
/* 54 */     chwBulkYMDMSalesBom_STPO2.setWerks(paramString4);
/* 55 */     chwBulkYMDMSalesBom_STPO2.setIdnrk(paramString1 + paramString2);
/* 56 */     this.tbl_stpo.add(chwBulkYMDMSalesBom_STPO2);
/*    */     
/* 58 */     ChwBulkYMDMSalesBom_CUKB chwBulkYMDMSalesBom_CUKB1 = new ChwBulkYMDMSalesBom_CUKB();
/* 59 */     chwBulkYMDMSalesBom_CUKB1.setMatnr(paramString1 + "MEB");
/* 60 */     chwBulkYMDMSalesBom_CUKB1.setWerks(paramString4);
/* 61 */     chwBulkYMDMSalesBom_CUKB1.setIdnrk(paramString1 + "F" + paramString3);
/* 62 */     chwBulkYMDMSalesBom_CUKB1.setDep_intern("SC_" + paramString1 + "_FC_" + paramString3 + "_BLK");
/* 63 */     chwBulkYMDMSalesBom_CUKB1.setDep_type("5");
/* 64 */     this.tbl_cukb.add(chwBulkYMDMSalesBom_CUKB1);
/*    */     
/* 66 */     ChwBulkYMDMSalesBom_CUKB chwBulkYMDMSalesBom_CUKB2 = new ChwBulkYMDMSalesBom_CUKB();
/* 67 */     chwBulkYMDMSalesBom_CUKB2.setMatnr(paramString1 + "MEB");
/* 68 */     chwBulkYMDMSalesBom_CUKB2.setWerks(paramString4);
/* 69 */     chwBulkYMDMSalesBom_CUKB2.setIdnrk(paramString1 + "F" + paramString3);
/* 70 */     chwBulkYMDMSalesBom_CUKB2.setDep_intern("PR_" + paramString1 + "_FC_" + paramString3 + "_BLK_QTY");
/* 71 */     chwBulkYMDMSalesBom_CUKB2.setDep_type("7");
/* 72 */     this.tbl_cukb.add(chwBulkYMDMSalesBom_CUKB2);
/*    */     
/* 74 */     ChwBulkYMDMSalesBom_CUKB chwBulkYMDMSalesBom_CUKB3 = new ChwBulkYMDMSalesBom_CUKB();
/* 75 */     chwBulkYMDMSalesBom_CUKB3.setMatnr(paramString1 + "MEB");
/* 76 */     chwBulkYMDMSalesBom_CUKB3.setWerks(paramString4);
/* 77 */     chwBulkYMDMSalesBom_CUKB3.setIdnrk(paramString1 + paramString2);
/* 78 */     chwBulkYMDMSalesBom_CUKB3.setDep_intern("SC_" + paramString1 + "_MOD_" + paramString2);
/* 79 */     chwBulkYMDMSalesBom_CUKB3.setDep_type("5");
/* 80 */     this.tbl_cukb.add(chwBulkYMDMSalesBom_CUKB3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 86 */     if (getRfcrc() != 0) {
/* 87 */       return false;
/*    */     }
/* 89 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {
/* 96 */     this.tbl_mast = new ArrayList<>();
/* 97 */     this.tbl_stko = new ArrayList<>();
/* 98 */     this.tbl_stpo = new ArrayList<>();
/* 99 */     this.tbl_cukb = new ArrayList<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwBulkYMDMSalesBom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */