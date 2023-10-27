/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaa_rmclm;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChwAssignCharToClass
/*    */   extends RdhBase
/*    */ {
/*    */   @SerializedName("J_CLASS")
/*    */   private String j_class;
/*    */   @SerializedName("J_KLART")
/*    */   private String j_klart;
/*    */   @SerializedName("MATERIAL")
/*    */   private String material;
/*    */   @SerializedName("RMCLM")
/*    */   private List<RdhClaa_rmclm> rmclms;
/*    */   @Foo
/*    */   private RdhClaa_rmclm rmclm;
/*    */   
/*    */   public ChwAssignCharToClass(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 27 */     super(paramString1, "Z_DM_SAP_ASSIGN_CHAR_TO_CLASS".toLowerCase(), null);
/* 28 */     this.pims_identity = "H";
/* 29 */     this.j_class = paramString2;
/* 30 */     this.j_klart = "300";
/* 31 */     this.material = paramString5;
/* 32 */     this.rmclm = new RdhClaa_rmclm();
/* 33 */     this.rmclm.setAbtei(paramString4);
/* 34 */     this.rmclm.setMerkma(paramString3);
/* 35 */     this.rmclms = new ArrayList<>();
/* 36 */     this.rmclms.add(this.rmclm);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isReadyToExecute() {
/* 42 */     if (checkFieldsNotEmplyOrNull(this, "j_class"))
/*    */     {
/*    */       
/* 45 */       return checkFieldsNotEmplyOrNull(this.rmclm, "merkma");
/*    */     }
/* 47 */     return false;
/*    */   }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isValidRfcrc() {
/* 69 */     return (getRfcrc() == 2 || getRfcrc() == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwAssignCharToClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */