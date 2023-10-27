/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.Char_descrTable;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.Char_valsTable;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.CharactsTable;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.Chv_descrTable;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChwCharMaintain
/*     */   extends RdhBase
/*     */ {
/*     */   @SerializedName("REFRESH_VALS")
/*     */   private String REFRESH_VALS;
/*     */   @SerializedName("CHARACT")
/*     */   private String charact;
/*     */   @SerializedName("CHARACTS")
/*  21 */   private List<CharactsTable> characts = new ArrayList<>();
/*     */   
/*  23 */   private List<Char_descrTable> CHAR_DESCR = new ArrayList<>();
/*  24 */   private List<Char_valsTable> CHAR_VALS = new ArrayList<>();
/*  25 */   private List<Chv_descrTable> CHV_DESCR = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChwCharMaintain(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12) {
/*  32 */     super(paramString1, "z_dm_sap_char_maintain", null);
/*  33 */     this.pims_identity = "H";
/*  34 */     this.rfa_num = paramString1;
/*  35 */     this.REFRESH_VALS = "";
/*  36 */     this.charact = paramString2;
/*     */     
/*  38 */     CharactsTable charactsTable = new CharactsTable();
/*  39 */     this.characts.add(charactsTable);
/*  40 */     charactsTable.setCHARACT(paramString2);
/*  41 */     charactsTable.setDATATYPE(paramString3);
/*  42 */     charactsTable.setCHARNUMBER(Integer.toString(paramInt));
/*  43 */     charactsTable.setDECPLACES((paramString4 != null && paramString4.length() > 0) ? paramString4 : "");
/*  44 */     charactsTable.setCASESENS((paramString5 != null && paramString5.length() > 0) ? paramString5 : "");
/*  45 */     charactsTable.setNEG_VALS((paramString6 != null && paramString6.length() > 0) ? paramString6 : "");
/*  46 */     charactsTable.setSTATUS("1");
/*  47 */     charactsTable.setGROUP((paramString7 != null && paramString7.length() > 0) ? paramString7 : "");
/*  48 */     charactsTable.setVALASSIGNM((paramString8 != null && paramString8.length() > 0) ? paramString8 : "");
/*  49 */     charactsTable.setNO_ENTRY((paramString9 != null && paramString9.length() > 0) ? paramString9 : "");
/*  50 */     charactsTable.setNO_DISPLAY((paramString10 != null && paramString10.length() > 0) ? paramString10 : "");
/*  51 */     charactsTable.setADDIT_VALS((paramString11 != null && paramString11.length() > 0) ? paramString11 : "");
/*  52 */     if (paramString12 != null && paramString12.length() > 0) {
/*     */       
/*  54 */       Char_descrTable char_descrTable = new Char_descrTable();
/*  55 */       this.CHAR_DESCR.add(char_descrTable);
/*  56 */       char_descrTable.setCHARACT(paramString2);
/*  57 */       char_descrTable.setLANGUAGE("E");
/*  58 */       char_descrTable.setCHDESCR(paramString12);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChwCharMaintain(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  65 */     super(paramString1, "z_dm_sap_char_maintain", null);
/*  66 */     addValue(paramString3, paramString4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addValue(String paramString1, String paramString2) {
/*  71 */     Char_valsTable char_valsTable = new Char_valsTable();
/*  72 */     this.CHAR_VALS.add(char_valsTable);
/*  73 */     char_valsTable.setCHARACT(this.charact);
/*  74 */     char_valsTable.setVALUE(paramString1);
/*  75 */     Chv_descrTable chv_descrTable = new Chv_descrTable();
/*  76 */     this.CHV_DESCR.add(chv_descrTable);
/*  77 */     chv_descrTable.setCHARACT(this.charact);
/*  78 */     chv_descrTable.setLANGUAGE("E");
/*  79 */     chv_descrTable.setVALUE(paramString1);
/*  80 */     chv_descrTable.setVALDESCR(paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isReadyToExecute() {
/*  90 */     ArrayList<String> arrayList = new ArrayList();
/*  91 */     arrayList.add("charact");
/*  92 */     arrayList.add("datatype");
/*  93 */     arrayList.add("charnumber");
/*  94 */     if (checkFieldsNotEmplyOrNullInCollection(this.characts, arrayList)) {
/*  95 */       ArrayList<String> arrayList1 = new ArrayList();
/*  96 */       arrayList1.add("charact");
/*  97 */       arrayList1.add("value");
/*  98 */       return checkFieldsNotEmplyOrNullInCollection(this.CHV_DESCR, arrayList1);
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwCharMaintain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */