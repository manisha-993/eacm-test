/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmm00;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh1;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh5;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_geo;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_plant;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_sales_org;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_tax_country;
/*     */ import COM.ibm.eannounce.abr.util.RFCConfig;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class RdhSvcMatmCreate
/*     */   extends RdhBase
/*     */ {
/*     */   @SerializedName("BMM00")
/*     */   private List<RdhMatm_bmm00> bmm00;
/*     */   @SerializedName("BMMH1")
/*     */   private List<RdhMatm_bmmh1> bmmh1;
/*     */   @SerializedName("BMMH5")
/*     */   private List<RdhMatm_bmmh5> bmmh5;
/*     */   @SerializedName("GEO")
/*     */   private List<RdhMatm_geo> geos;
/*     */   @SerializedName("IS_MULTI_PLANTS")
/*     */   private String is_multi_plants;
/*     */   @Foo
/*  34 */   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*     */   @Foo
/*  36 */   SimpleDateFormat sdfANNDATE = new SimpleDateFormat("yyMMdd");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RdhSvcMatmCreate(MODEL paramMODEL) {
/*  44 */     super(paramMODEL.getMACHTYPE() + paramMODEL.getMODEL(), "Z_DM_SAP_MATM_CREATE".toLowerCase(), null);
/*     */     
/*  46 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setMatnr(paramMODEL.getMACHTYPE() + paramMODEL.getMODEL());
/*  47 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setMtart("ZPRT");
/*  48 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setMbrsh("M");
/*  49 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setVtweg("00");
/*  50 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMatkl("000");
/*  51 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMeins("EA");
/*  52 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setAeszn(getEarliestAnnDate(paramMODEL));
/*  53 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setGewei("KG");
/*  54 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setTragr("0001");
/*  55 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSpart("00");
/*  56 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setPrdha(paramMODEL.getPRODHIERCD());
/*  57 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMaabc("A");
/*  58 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setEkgrp("ZZZ");
/*  59 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setDismm("PD");
/*  60 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setDispo("000");
/*  61 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setPerkz("M");
/*  62 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setDisls("EX");
/*  63 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setBeskz("X");
/*  64 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSbdkz("1");
/*  65 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setFhori("000");
/*  66 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMtvfp("KP");
/*  67 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setKautb("X");
/*  68 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setPrctr(paramMODEL.getPRFTCTR());
/*  69 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setFxhor("0");
/*  70 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setDisgr("Z010");
/*  71 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSernp("NONE");
/*  72 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVprsv("S");
/*  73 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setStprs("0.01");
/*  74 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setPeinh("1");
/*  75 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setBklas("7900");
/*  76 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVmvpr("S");
/*  77 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVmbkl("7900");
/*  78 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVjvpr("S");
/*  79 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVjbkl("7900");
/*  80 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVersg("1");
/*  81 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSktof("X");
/*  82 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setAumng("1");
/*  83 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setLfmng("1");
/*  84 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setScmng("1");
/*  85 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMtpos(getMtpos(paramMODEL));
/*  86 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setProdh(paramMODEL.getPRODHIERCD());
/*  87 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setKtgrm(paramMODEL.getACCTASGNGRP());
/*  88 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMvgr5((paramMODEL.getTAXCODELIST().size() == 0) ? "" : ((TAXCODE)paramMODEL.getTAXCODELIST().get(0)).getTAXCODE());
/*  89 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeib1("X");
/*  90 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeid1("X");
/*  91 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeik1("X");
/*  92 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeiv1("X");
/*  93 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeie1("X");
/*  94 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setTcode("MM01");
/*     */     
/*  96 */     List<LANGUAGE> list = paramMODEL.getLANGUAGELIST();
/*  97 */     if (list != null && list.size() > 0) {
/*  98 */       ((RdhMatm_bmmh5)this.bmmh5.get(0)).setSpras(getSpras(((LANGUAGE)list.get(0)).getNLSID()));
/*  99 */       ((RdhMatm_bmmh5)this.bmmh5.get(0)).setMaktx(((LANGUAGE)list.get(0)).getINVNAME());
/* 100 */       ((RdhMatm_bmmh5)this.bmmh5.get(0)).setTdline(((LANGUAGE)list.get(0)).getMKTGNAME());
/*     */     } else {
/* 102 */       ((RdhMatm_bmmh5)this.bmmh5.get(0)).setSpras("E");
/*     */     } 
/*     */     
/* 105 */     List<AVAILABILITY> list1 = paramMODEL.getAVAILABILITYLIST();
/* 106 */     ArrayList<Date> arrayList = new ArrayList();
/* 107 */     ArrayList<RdhMatm_sales_org> arrayList1 = new ArrayList();
/* 108 */     ArrayList<RdhMatm_plant> arrayList2 = new ArrayList();
/* 109 */     ArrayList<RdhMatm_tax_country> arrayList3 = new ArrayList();
/* 110 */     ArrayList<String> arrayList4 = new ArrayList();
/* 111 */     ArrayList<String> arrayList5 = new ArrayList();
/* 112 */     ArrayList<String> arrayList6 = new ArrayList();
/*     */     
/* 114 */     if (list1 != null && list1.size() > 0) {
/* 115 */       for (byte b = 0; b < list1.size(); b++) {
/* 116 */         AVAILABILITY aVAILABILITY = list1.get(b);
/* 117 */         Date date = praseDate(aVAILABILITY.getPUBFROM());
/* 118 */         if (date != null) {
/* 119 */           arrayList.add(date);
/*     */         }
/*     */         
/* 122 */         List<SLEORGNPLNTCODE> list3 = aVAILABILITY.getSLEORGNPLNTCODELIST();
/* 123 */         if (list3 != null && list3.size() > 0) {
/* 124 */           for (byte b1 = 0; b1 < list3.size(); b1++) {
/* 125 */             SLEORGNPLNTCODE sLEORGNPLNTCODE = list3.get(b1);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 130 */             String str1 = sLEORGNPLNTCODE.getPLNTCD();
/*     */             
/* 132 */             if (RFCConfig.getPlant("2", sLEORGNPLNTCODE.getPLNTCD()) != null && !arrayList5.contains(str1)) {
/* 133 */               RdhMatm_plant rdhMatm_plant = new RdhMatm_plant();
/* 134 */               rdhMatm_plant.setWerks(str1);
/* 135 */               rdhMatm_plant.setEkgrp("ZZZ");
/* 136 */               arrayList2.add(rdhMatm_plant);
/* 137 */               arrayList5.add(str1);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 143 */             String str2 = RFCConfig.getDwerk("2", sLEORGNPLNTCODE.getSLEORG());
/* 144 */             String str3 = sLEORGNPLNTCODE.getSLEORG();
/* 145 */             if (str2 != null && 
/* 146 */               !arrayList4.contains(str3 + str2)) {
/* 147 */               RdhMatm_sales_org rdhMatm_sales_org = new RdhMatm_sales_org();
/* 148 */               rdhMatm_sales_org.setVkorg(str3);
/*     */               
/* 150 */               rdhMatm_sales_org.setDwerk(str2);
/* 151 */               arrayList1.add(rdhMatm_sales_org);
/* 152 */               arrayList4.add(str3 + str2);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 159 */       ((RdhMatm_geo)this.geos.get(0)).setName("WW1");
/* 160 */       ((RdhMatm_geo)this.geos.get(0)).setVmsta("Z0");
/* 161 */       ((RdhMatm_geo)this.geos.get(0)).setVmstd(this.sdf.format(Collections.<Date>min(arrayList)));
/* 162 */       ((RdhMatm_geo)this.geos.get(0)).setSales_orgs(arrayList1);
/* 163 */       ((RdhMatm_geo)this.geos.get(0)).setPlants(arrayList2);
/*     */     } 
/*     */     
/* 166 */     List<TAXCATEGORY> list2 = paramMODEL.getTAXCATEGORYLIST();
/* 167 */     if (list2 != null && list2.size() > 0) {
/* 168 */       for (byte b = 0; b < list2.size(); b++) {
/* 169 */         TAXCATEGORY tAXCATEGORY = list2.get(b);
/* 170 */         List<COUNTRY> list3 = tAXCATEGORY.getCOUNTRYLIST();
/* 171 */         for (byte b1 = 0; b1 < list3.size(); b1++) {
/*     */ 
/*     */ 
/*     */           
/* 175 */           String str1 = RFCConfig.getAland(((COUNTRY)list3.get(b1)).getCOUNTRY_FC());
/* 176 */           String str2 = tAXCATEGORY.getTAXCATEGORYVALUE();
/* 177 */           if (str1 != null && !arrayList6.contains(str1 + str2)) {
/* 178 */             RdhMatm_tax_country rdhMatm_tax_country = new RdhMatm_tax_country();
/* 179 */             rdhMatm_tax_country.setAland(str1);
/* 180 */             rdhMatm_tax_country.setTaty1(str2);
/* 181 */             rdhMatm_tax_country.setTaxm1("1");
/* 182 */             rdhMatm_tax_country.setTaxm2("1");
/* 183 */             rdhMatm_tax_country.setTaxm3("1");
/* 184 */             rdhMatm_tax_country.setTaxm4("1");
/* 185 */             rdhMatm_tax_country.setTaxm5("1");
/* 186 */             rdhMatm_tax_country.setTaxm6("1");
/* 187 */             rdhMatm_tax_country.setTaxm7("1");
/* 188 */             rdhMatm_tax_country.setTaxm8("1");
/* 189 */             rdhMatm_tax_country.setTaxm9("1");
/* 190 */             arrayList3.add(rdhMatm_tax_country);
/* 191 */             arrayList6.add(str1 + str2);
/*     */           } 
/*     */         } 
/*     */       } 
/* 195 */       ((RdhMatm_geo)this.geos.get(0)).setTax_countries(arrayList3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getSpras(String paramString) {
/* 200 */     String str = null;
/* 201 */     if ("1".equals(paramString)) {
/* 202 */       str = "E";
/* 203 */     } else if ("2".equals(paramString)) {
/* 204 */       str = "D";
/* 205 */     } else if ("11".equals(paramString)) {
/* 206 */       str = "1";
/*     */     } 
/* 208 */     return str;
/*     */   }
/*     */   
/*     */   private String getMtpos(MODEL paramMODEL) {
/* 212 */     String str = null;
/* 213 */     int i = paramMODEL.getSUBCATEGORY().indexOf(" ");
/* 214 */     if (paramMODEL == null)
/* 215 */       return str; 
/* 216 */     if (i > 0) {
/* 217 */       str = MTARTforService(paramMODEL.getSUBCATEGORY().substring(0, i).toUpperCase());
/*     */     } else {
/* 219 */       str = MTARTforService(paramMODEL.getSUBCATEGORY().toUpperCase());
/*     */     } 
/* 221 */     return str;
/*     */   }
/*     */   
/*     */   private String MTARTforService(String paramString) {
/* 225 */     String str = null;
/* 226 */     if ("RTSXSERIES".equals(paramString) || "INSTALL".equals(paramString) || "MAINONLY".equals(paramString) || "MAINONLYA"
/* 227 */       .equals(paramString) || "WMAINOCS".equals(paramString) || "ENSPEURP".equals(paramString) || "GENERICHW2"
/* 228 */       .equals(paramString) || "STRTUPSUP".equals(paramString) || "MEMEAMAP".equals(paramString) || "MEMEAWMO"
/* 229 */       .equals(paramString) || "WMAINOCS".equals(paramString) || "WMAINTOPT".equals(paramString) || "STRTUPSUP"
/* 230 */       .equals(paramString) || "STG".equals(paramString)) {
/* 231 */       str = "LEIS";
/*     */     }
/* 233 */     else if ("SS S & S".equals(paramString) || "ENDUSERSUP".equals(paramString) || "EURBUNSP".equals(paramString) || "EUREMIMP"
/* 234 */       .equals(paramString) || "EURETSSWU".equals(paramString) || "EUREXSP".equals(paramString) || "EURMIGSP"
/* 235 */       .equals(paramString) || "EURMVSCIS".equals(paramString) || "EURMVSSP".equals(paramString) || "GENERICHW1"
/* 236 */       .equals(paramString) || "GENERICHW3".equals(paramString) || "GENERICHW4".equals(paramString) || "GENERICHW5"
/* 237 */       .equals(paramString) || "GENERICHW7".equals(paramString) || "ITESEDUC".equals(paramString) || "PSSWSUPP"
/* 238 */       .equals(paramString) || "RTECHSUPP".equals(paramString) || "SERVACCT".equals(paramString) || "SERVHEALTH"
/* 239 */       .equals(paramString) || "SMOOTHSTRT".equals(paramString) || "MAINSWSUPP".equals(paramString) || "SUPLINEEU"
/* 240 */       .equals(paramString) || "SUPPORTLN".equals(paramString) || "SYSEXPERT".equals(paramString) || "TECHSUPPSV"
/* 241 */       .equals(paramString) || "GTMSEUR".equals(paramString) || "ETSAAEUR".equals(paramString) || "PROACTSYS"
/* 242 */       .equals(paramString) || "GENERICHW6".equals(paramString)) {
/* 243 */       str = "ZPSE";
/*     */     }
/* 245 */     else if ("SBUNDLE1".equals(paramString) || "SBUNDLE2".equals(paramString) || "SBUNDLE3".equals(paramString) || "SBUNDLE4"
/* 246 */       .equals(paramString) || "SBUNDLE5".equals(paramString) || "SBUNDLE6".equals(paramString) || "SBUNDLE"
/* 247 */       .equals(paramString)) {
/* 248 */       str = "ZTSP";
/*     */     }
/* 250 */     else if ("WMAINTOPTA".equals(paramString)) {
/* 251 */       str = "ZCSP";
/*     */     } 
/*     */     
/* 254 */     return str;
/*     */   }
/*     */   
/*     */   private String getEarliestAnnDate(MODEL paramMODEL) {
/* 258 */     Date date1 = null;
/* 259 */     Date date2 = null;
/* 260 */     String str = "";
/* 261 */     List<AVAILABILITY> list = paramMODEL.getAVAILABILITYLIST();
/* 262 */     if (list != null && list.size() > 0) {
/* 263 */       for (byte b = 0; b < list.size(); b++) {
/*     */         try {
/* 265 */           if (date1 == null) {
/*     */ 
/*     */             
/* 268 */             date1 = this.sdf.parse(((AVAILABILITY)list.get(b)).getANNDATE());
/*     */           } else {
/*     */             
/* 271 */             date2 = this.sdf.parse(((AVAILABILITY)list.get(b)).getANNDATE());
/*     */             
/* 273 */             if (date2.after(date1)) {
/* 274 */               date1 = date2;
/*     */             }
/*     */           }
/*     */         
/*     */         }
/* 279 */         catch (Exception exception) {
/* 280 */           exception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     if (date1 == null)
/* 291 */       return null; 
/* 292 */     return this.sdfANNDATE.format(date1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/* 297 */     this.pims_identity = "H";
/* 298 */     this.is_multi_plants = "TRUE";
/* 299 */     RdhMatm_bmm00 rdhMatm_bmm00 = new RdhMatm_bmm00();
/* 300 */     this.bmm00 = new ArrayList<>();
/* 301 */     this.bmm00.add(rdhMatm_bmm00);
/* 302 */     RdhMatm_bmmh1 rdhMatm_bmmh1 = new RdhMatm_bmmh1();
/* 303 */     this.bmmh1 = new ArrayList<>();
/* 304 */     this.bmmh1.add(rdhMatm_bmmh1);
/* 305 */     RdhMatm_bmmh5 rdhMatm_bmmh5 = new RdhMatm_bmmh5();
/* 306 */     this.bmmh5 = new ArrayList<>();
/* 307 */     this.bmmh5.add(rdhMatm_bmmh5);
/*     */     
/* 309 */     this.geos = new ArrayList<>();
/* 310 */     this.geos.add(new RdhMatm_geo());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isReadyToExecute() {
/* 316 */     if (getRfcrc() != 0) {
/* 317 */       return false;
/*     */     }
/* 319 */     return true;
/*     */   }
/*     */   
/*     */   public Date praseDate(String paramString) {
/*     */     try {
/* 324 */       return this.sdf.parse(paramString);
/*     */     }
/* 326 */     catch (ParseException parseException) {
/*     */       
/* 328 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhSvcMatmCreate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */