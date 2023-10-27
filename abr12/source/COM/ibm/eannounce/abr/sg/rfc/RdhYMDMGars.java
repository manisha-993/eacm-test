/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.CountryPlantTax;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhYMDMGars_MAT;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhYMDMGars_PRODUCTS;
/*     */ import COM.ibm.eannounce.abr.util.RFCConfig;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ 
/*     */ public class RdhYMDMGars extends RdhBase {
/*     */   @SerializedName("TBL_GARS_MAT")
/*     */   private List<RdhYMDMGars_MAT> tbl_gars_mat;
/*     */   @Foo
/*  23 */   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); @SerializedName("TBL_PRODUCTS")
/*     */   private List<RdhYMDMGars_PRODUCTS> tbl_products; @Foo
/*  25 */   SimpleDateFormat sdfANNDATE = new SimpleDateFormat("MMddyy");
/*     */   @Foo
/*  27 */   SimpleDateFormat sdfPUBFROMDATE = new SimpleDateFormat("MMddyyyy");
/*     */   @Foo
/*  29 */   String annnumber = null;
/*     */   @Foo
/*  31 */   private String GETCOUNTYNAME = "select GENAREANAME_FC,GENAREACODE from price.generalarea  where GENAREACODE in (select GENAREACODE from price.generalarea) and NLSID = 1 and ISACTIVE = 1 WITH UR";
/*     */   
/*     */   public RdhYMDMGars(MODEL paramMODEL, Connection paramConnection) throws SQLException {
/*  34 */     super(paramMODEL.getMACHTYPE() + "FEA", "RDH_YMDM_GARS".toLowerCase(), null);
/*  35 */     this.pims_identity = "H";
/*  36 */     List list = RFCConfig.getTaxs();
/*  37 */     for (CountryPlantTax countryPlantTax : list) {
/*  38 */       if ("19".equals(countryPlantTax.getINTERFACE_ID())) {
/*  39 */         RdhYMDMGars_MAT rdhYMDMGars_MAT = new RdhYMDMGars_MAT();
/*     */         
/*  41 */         rdhYMDMGars_MAT.setMatnr(paramMODEL.getMACHTYPE() + "FEA");
/*  42 */         rdhYMDMGars_MAT.setBrgew("00");
/*  43 */         rdhYMDMGars_MAT.setPrdha(paramMODEL.getPRODHIERCD());
/*  44 */         rdhYMDMGars_MAT.setNumtp("");
/*  45 */         rdhYMDMGars_MAT.setEan11("");
/*  46 */         rdhYMDMGars_MAT.setZconf("E");
/*  47 */         rdhYMDMGars_MAT.setAeszn(getEarliestAnnDate(paramMODEL));
/*  48 */         if (!this.annnumber.equals("")) {
/*  49 */           rdhYMDMGars_MAT.setZeiar("RFA");
/*     */         } else {
/*  51 */           rdhYMDMGars_MAT.setZeiar("");
/*     */         } 
/*  53 */         rdhYMDMGars_MAT.setZeinr(this.annnumber);
/*  54 */         rdhYMDMGars_MAT.setPrctr(paramMODEL.getPRFTCTR());
/*  55 */         rdhYMDMGars_MAT.setWerks(countryPlantTax.getPLNT_CD());
/*  56 */         rdhYMDMGars_MAT.setKtgrm("06");
/*  57 */         rdhYMDMGars_MAT.setVkorg(countryPlantTax.getSALES_ORG());
/*  58 */         rdhYMDMGars_MAT.setDwerk(countryPlantTax.getDEL_PLNT());
/*  59 */         if (!countryPlantTax.getTAX_CD().trim().contains("NULL")) {
/*  60 */           rdhYMDMGars_MAT.setMvgr5(countryPlantTax.getTAX_CD().trim());
/*     */         } else {
/*  62 */           rdhYMDMGars_MAT.setMvgr5("");
/*     */         } 
/*  64 */         rdhYMDMGars_MAT.setProdh(paramMODEL.getPRODHIERCD());
/*  65 */         rdhYMDMGars_MAT.setVmstd(getEarliestPUBFROM(paramMODEL));
/*  66 */         rdhYMDMGars_MAT.setSptxt("");
/*  67 */         rdhYMDMGars_MAT.setSpras("E");
/*  68 */         rdhYMDMGars_MAT.setMaktx("MACHINE TYPE " + paramMODEL.getMACHTYPE() + "- MODEL FEA");
/*  69 */         rdhYMDMGars_MAT.setAland(countryPlantTax.getTAX_COUNTRY());
/*  70 */         rdhYMDMGars_MAT.setTaxm1(countryPlantTax.getTAX_CLAS());
/*  71 */         rdhYMDMGars_MAT.setTaty1(countryPlantTax.getTAX_CAT());
/*  72 */         rdhYMDMGars_MAT.setMm_mach_type(paramMODEL.getMACHTYPE());
/*  73 */         if (paramMODEL.getUNITCLASS().equals("SIU-CPU") || paramMODEL.getUNITCLASS().equals("SIU-Non CPU")) {
/*  74 */           rdhYMDMGars_MAT.setMm_siu("2");
/*  75 */         } else if (paramMODEL.getUNITCLASS().equals("Non SIU- CPU")) {
/*  76 */           rdhYMDMGars_MAT.setMm_siu("0");
/*     */         } else {
/*  78 */           rdhYMDMGars_MAT.setMm_siu("");
/*     */         } 
/*  80 */         if (paramMODEL.getINSTALL().equals("CIF")) {
/*  81 */           rdhYMDMGars_MAT.setMm_fg_installable("CSU");
/*  82 */         } else if (paramMODEL.getINSTALL().equals("CE")) {
/*  83 */           rdhYMDMGars_MAT.setMm_fg_installable("IBI");
/*     */         } else {
/*  85 */           rdhYMDMGars_MAT.setMm_fg_installable("");
/*     */         } 
/*  87 */         rdhYMDMGars_MAT.setMm_identity("");
/*  88 */         rdhYMDMGars_MAT.setMm_lic(paramMODEL.getLIC());
/*  89 */         rdhYMDMGars_MAT.setMm_bp_cert_specbid(paramMODEL.getBPCERTSPECBID());
/*  90 */         rdhYMDMGars_MAT.setMm_rpqtype("");
/*  91 */         rdhYMDMGars_MAT.setMm_announcement_type("");
/*     */         
/*  93 */         this.tbl_gars_mat.add(rdhYMDMGars_MAT);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  99 */     HashSet<String> hashSet = new HashSet();
/* 100 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 101 */     Statement statement = paramConnection.createStatement();
/* 102 */     ResultSet resultSet = statement.executeQuery(this.GETCOUNTYNAME);
/* 103 */     while (resultSet.next()) {
/* 104 */       hashtable.put(resultSet.getString("GENAREANAME_FC").trim(), resultSet.getString("GENAREACODE").trim());
/*     */     }
/* 106 */     statement.close();
/* 107 */     resultSet.close();
/* 108 */     for (AVAILABILITY aVAILABILITY : paramMODEL.getAVAILABILITYLIST()) {
/* 109 */       RdhYMDMGars_PRODUCTS rdhYMDMGars_PRODUCTS = new RdhYMDMGars_PRODUCTS();
/* 110 */       String str = aVAILABILITY.getCOUNTRY_FC();
/* 111 */       if (hashSet.contains(str)) {
/*     */         continue;
/*     */       }
/* 114 */       hashSet.add(str);
/* 115 */       rdhYMDMGars_PRODUCTS.setPartnum(paramMODEL.getMACHTYPE() + "FEA");
/* 116 */       rdhYMDMGars_PRODUCTS.setLand1((String)hashtable.get(str));
/* 117 */       this.tbl_products.add(rdhYMDMGars_PRODUCTS);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setDefaultValues() {
/* 122 */     this.tbl_gars_mat = new ArrayList<>();
/* 123 */     this.tbl_products = new ArrayList<>();
/*     */   }
/*     */   
/*     */   protected boolean isReadyToExecute() {
/* 127 */     return true;
/*     */   }
/*     */   public String getEarliestAnnDate(MODEL paramMODEL) {
/* 130 */     Date date1 = null;
/* 131 */     Date date2 = null;
/* 132 */     List<AVAILABILITY> list = paramMODEL.getAVAILABILITYLIST();
/* 133 */     if (list != null && list.size() > 0) {
/* 134 */       for (byte b = 0; b < list.size(); b++) {
/*     */         try {
/* 136 */           if (date1 == null) {
/* 137 */             this.annnumber = ((AVAILABILITY)list.get(b)).getANNNUMBER();
/* 138 */             date1 = this.sdf.parse(((AVAILABILITY)list.get(b)).getANNDATE());
/*     */           } else {
/*     */             
/* 141 */             date2 = this.sdf.parse(((AVAILABILITY)list.get(b)).getANNDATE());
/*     */             
/* 143 */             if (date2.before(date1)) {
/* 144 */               date1 = date2;
/* 145 */               this.annnumber = ((AVAILABILITY)list.get(b)).getANNNUMBER();
/*     */             } 
/*     */           } 
/* 148 */         } catch (Exception exception) {
/* 149 */           exception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/* 153 */     if (date1 == null)
/* 154 */       return null; 
/* 155 */     return this.sdfANNDATE.format(date1);
/*     */   }
/*     */   private String getEarliestPUBFROM(MODEL paramMODEL) {
/* 158 */     Date date1 = null;
/* 159 */     Date date2 = null;
/* 160 */     String str = "";
/* 161 */     List<AVAILABILITY> list = paramMODEL.getAVAILABILITYLIST();
/* 162 */     if (list != null && list.size() > 0) {
/* 163 */       for (byte b = 0; b < list.size(); b++) {
/*     */         try {
/* 165 */           if (date1 == null) {
/* 166 */             date1 = this.sdf.parse(((AVAILABILITY)list.get(b)).getPUBFROM());
/*     */           } else {
/*     */             
/* 169 */             date2 = this.sdf.parse(((AVAILABILITY)list.get(b)).getPUBFROM());
/*     */ 
/*     */             
/* 172 */             if (date2.before(date1)) {
/* 173 */               date1 = date2;
/*     */             }
/*     */           } 
/* 176 */         } catch (Exception exception) {
/* 177 */           exception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/* 181 */     if (date1 == null)
/* 182 */       return null; 
/* 183 */     return this.sdfPUBFROMDATE.format(date1);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhYMDMGars.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */