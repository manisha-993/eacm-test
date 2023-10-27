/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhTssMatCharEntity;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RdhTssMatChar
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("Z_GEO")
/*    */   private String z_geo;
/*    */   @SerializedName("I_MATCHAR")
/*    */   private List<RdhTssMatCharEntity> entities;
/*    */   
/*    */   public RdhTssMatChar(SVCMOD svcmod) {
/* 21 */     super(String.valueOf(svcmod.getMACHTYPE()) + svcmod.getMODEL(), "RDH_YMDM_MATCHAR".toLowerCase(), null);
/*    */     
/* 23 */     List<PRODSTRUCT> prodStructs = svcmod.getPRODSTRUCTLIST();
/* 24 */     if (prodStructs != null && !prodStructs.isEmpty())
/*    */     {
/* 26 */       for (PRODSTRUCT prodStruct : prodStructs) {
/*    */         
/* 28 */         RdhTssMatCharEntity entity = new RdhTssMatCharEntity();
/* 29 */         entity.setMaterial_number(String.valueOf(svcmod.getMACHTYPE()) + svcmod.getMODEL());
/* 30 */         entity.setFeature_id(prodStruct.getFEATURECODE());
/* 31 */         entity.setFeature_name(prodStruct.getMKTGNAME());
/* 32 */         entity.setManopt_flag(prodStruct.getMNATORYOPT());
/* 33 */         entity.setBukrs("");
/* 34 */         entity.setWithdrawdate(prodStruct.getWITHDRAWDATE().replaceAll("-", ""));
/* 35 */         entity.setWthdrweffctvdate(prodStruct.getWTHDRWEFFCTVDATE().replaceAll("-", ""));
/* 36 */         entity.setEffective_date(prodStruct.getEFFECTIVEDATE().replaceAll("-", ""));
/* 37 */         entity.setEnd_date(prodStruct.getENDDATE().replaceAll("-", ""));
/*    */         
/* 39 */         this.entities.add(entity);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultValues() {
/* 51 */     this.entities = new ArrayList<>();
/* 52 */     this.z_geo = "WW";
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 57 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhTssMatChar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */