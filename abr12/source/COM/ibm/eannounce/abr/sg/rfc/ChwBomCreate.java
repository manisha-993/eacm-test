/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_csap_mbom;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_csap_mbom_Adapter;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_stko_api01;
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBomc_stko_api01_Adapter;
/*    */ import COM.ibm.eannounce.abr.util.DateUtility;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChwBomCreate
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("csap_mbom")
/*    */   private RdhBomc_csap_mbom_Adapter csap_mbom;
/*    */   @SerializedName("jIStko")
/*    */   private RdhBomc_stko_api01_Adapter jIStko;
/*    */   
/*    */   public ChwBomCreate(String paramString1, String paramString2) {
/* 25 */     super(paramString1, "Z_DM_SAP_BOM_CREATE".toLowerCase(), null);
/* 26 */     this.pims_identity = "H";
/* 27 */     this.default_mandt = "10H";
/*    */     
/* 29 */     this.csap_mbom.getMap().setMatnr(paramString1);
/* 30 */     this.csap_mbom.getMap().setWerks(paramString2);
/* 31 */     this.csap_mbom.getMap().setDatuv(DateUtility.getTodayStringWithSimpleFormat());
/*    */   }
/*    */ 
/*    */   
/*    */   public ChwBomCreate(String paramString1, String paramString2, String paramString3) {
/* 36 */     super(paramString2, "Z_DM_SAP_BOM_CREATE".toLowerCase(), null);
/* 37 */     this.pims_identity = "H";
/* 38 */     this.default_mandt = "10H";
/*    */     
/* 40 */     this.csap_mbom.getMap().setMatnr(paramString1);
/* 41 */     this.csap_mbom.getMap().setWerks(paramString3);
/* 42 */     this.csap_mbom.getMap().setDatuv(DateUtility.getTodayStringWithSimpleFormat());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {
/* 48 */     this.csap_mbom = new RdhBomc_csap_mbom_Adapter();
/* 49 */     this.csap_mbom.setStuctName("csap_mbom");
/* 50 */     RdhBomc_csap_mbom rdhBomc_csap_mbom = new RdhBomc_csap_mbom();
/* 51 */     this.csap_mbom.setMap(rdhBomc_csap_mbom);
/* 52 */     rdhBomc_csap_mbom.setStlan("5");
/* 53 */     rdhBomc_csap_mbom.setStlal("1");
/*    */     
/* 55 */     this.jIStko = new RdhBomc_stko_api01_Adapter();
/* 56 */     this.jIStko.setStuctName("jIStko");
/* 57 */     RdhBomc_stko_api01 rdhBomc_stko_api01 = new RdhBomc_stko_api01();
/* 58 */     this.jIStko.setMap(rdhBomc_stko_api01);
/* 59 */     rdhBomc_stko_api01.setBase_quan("1");
/* 60 */     rdhBomc_stko_api01.setBom_status("01");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 66 */     RdhBomc_csap_mbom rdhBomc_csap_mbom = this.csap_mbom.getMap();
/*    */     
/* 68 */     ArrayList<String> arrayList = new ArrayList();
/* 69 */     arrayList.add("matnr");
/* 70 */     arrayList.add("werks");
/* 71 */     return checkFieldsNotEmplyOrNull(rdhBomc_csap_mbom, arrayList, true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isValidRfcrc() {
/* 77 */     return (getRfcrc() == 0 || getRfcrc() == 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwBomCreate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */