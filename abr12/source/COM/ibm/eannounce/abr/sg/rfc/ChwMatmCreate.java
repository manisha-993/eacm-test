/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.CountryPlantTax;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.Generalarea;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.HSN;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmm00;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh1;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh2;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh5;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh6;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_bmmh7;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_geo;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_plant;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_sales_org;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhMatm_tax_country;
/*     */ import COM.ibm.eannounce.abr.util.DateUtility;
/*     */ import COM.ibm.eannounce.abr.util.RFCConfig;
/*     */ import COM.ibm.eannounce.abr.util.RfcConfigProperties;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChwMatmCreate
/*     */   extends RdhBase
/*     */ {
/*     */   @SerializedName("TYPE")
/*     */   private String type;
/*     */   @SerializedName("BMM00")
/*     */   private List<RdhMatm_bmm00> bmm00;
/*     */   @SerializedName("BMMH1")
/*     */   private List<RdhMatm_bmmh1> bmmh1;
/*     */   @SerializedName("BMMH2")
/*     */   private List<RdhMatm_bmmh2> bmmh2;
/*     */   @Foo
/*  47 */   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*     */   
/*     */   @SerializedName("BMMH5")
/*     */   private List<RdhMatm_bmmh5> bmmh5;
/*     */   
/*     */   @SerializedName("BMMH6")
/*     */   private List<RdhMatm_bmmh6> bmmh6;
/*     */   
/*     */   @SerializedName("BMMH7")
/*     */   private List<RdhMatm_bmmh7> bmmh7;
/*     */   
/*     */   @SerializedName("GEO")
/*     */   private List<RdhMatm_geo> geos;
/*     */   
/*     */   @SerializedName("IS_MULTI_PLANTS")
/*     */   private String is_multi_plants;
/*     */ 
/*     */   
/*     */   public ChwMatmCreate(MODEL model, String materialType, String materialID, String rfaNum) {
/*  66 */     super(rfaNum, "Z_DM_SAP_MATM_CREATE".toLowerCase(), null);
/*     */     
/*  68 */     this.pims_identity = "H";
/*  69 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setMatnr(materialID);
/*  70 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setMtart(materialType);
/*  71 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setMbrsh("M");
/*     */     
/*  73 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setVtweg("00");
/*     */     
/*  75 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMatkl("000");
/*  76 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMeins("EA");
/*     */ 
/*     */     
/*  79 */     for (int i = 0; i < model.getAVAILABILITYLIST().size(); i++) {
/*  80 */       String ann = ((AVAILABILITY)model.getAVAILABILITYLIST().get(i)).getANNNUMBER();
/*  81 */       if (ann != null && ann.trim().length() > 0) {
/*  82 */         ((RdhMatm_bmmh1)this.bmmh1.get(0)).setZeinr(ann);
/*     */         break;
/*     */       } 
/*     */     } 
/*  86 */     if (((RdhMatm_bmmh1)this.bmmh1.get(0)).getZeinr() == null) {
/*  87 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setZeinr("");
/*  88 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setZeiar("");
/*     */     } else {
/*     */       
/*  91 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setZeiar("RFA");
/*     */     } 
/*  93 */     if (materialID.endsWith("UPG")) {
/*  94 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setStprs("0.00");
/*     */     } else {
/*     */       
/*  97 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setStprs("0.01");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setAeszn(getEarliestAnnDate(model));
/* 110 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setGewei("KG");
/* 111 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setTragr("0001");
/* 112 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSpart("00");
/*     */ 
/*     */     
/* 115 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMaabc("A");
/* 116 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setEkgrp("ZZZ");
/* 117 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setDismm("PD");
/* 118 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setDispo("000");
/* 119 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setPerkz("M");
/* 120 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setDisls("EX");
/* 121 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setBeskz("X");
/* 122 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSbdkz("1");
/* 123 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setFhori("000");
/*     */     
/* 125 */     if ("Hardware".equals(model.getCATEGORY())) {
/*     */       
/* 127 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setLadgr("H001");
/* 128 */       if ("ZMAT".equals(materialType))
/*     */       {
/* 130 */         ((RdhMatm_bmmh1)this.bmmh1.get(0)).setXchpf("X");
/*     */       }
/*     */     } 
/*     */     
/* 134 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMtvfp("ZE");
/* 135 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setKautb("X");
/* 136 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setPrctr(model.getPRFTCTR());
/* 137 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setFxhor("0");
/* 138 */     if ("ZMAT".equals(materialType)) {
/*     */       
/* 140 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setDisgr("Z025");
/* 141 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVprsv("V");
/* 142 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setBklas("7920");
/* 143 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVmvpr("V");
/* 144 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVmbkl("7920");
/* 145 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVjvpr("V");
/* 146 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVjbkl("7920");
/*     */     
/*     */     }
/* 149 */     else if ("ZPRT".equals(materialType)) {
/* 150 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setDisgr("Z010");
/* 151 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVprsv("S");
/* 152 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setBklas("7900");
/* 153 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVmvpr("S");
/* 154 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVmbkl("7900");
/* 155 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVjvpr("S");
/* 156 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVjbkl("7900");
/*     */     } 
/*     */ 
/*     */     
/* 160 */     if (materialID.endsWith("NEW")) {
/* 161 */       if ("LBS".equals(model.getPHANTOMMODINDC()) || "'6661', '6662', '6663', '6664', '6665', '6668', '6669', '9602', '9604'".contains(model.getMACHTYPE()) || (
/* 162 */         "Storage Tier','STORAGE TIER','storage tier','Power Tier'".contains(model.getSUBGROUP()) && model.getSUBGROUP().length() > 0)) {
/* 163 */         ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSernp("NONE");
/* 164 */       } else if ("'2063', '2068', '2059', '2057', '2058'".contains(model.getMACHTYPE())) {
/* 165 */         ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSernp("GG01");
/*     */       } else {
/* 167 */         ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSernp("ZZ06");
/*     */       } 
/* 169 */     } else if (materialID.endsWith("MTC") || materialID.endsWith("UPG")) {
/* 170 */       if ("LBS".equals(model.getPHANTOMMODINDC()) || "Power Tier".equals(model.getSUBGROUP())) {
/* 171 */         ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSernp("NONE");
/*     */       } else {
/*     */         
/* 174 */         ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSernp("ZZ06");
/*     */       }
/*     */     
/* 177 */     } else if ("ZPRT".equals(materialType)) {
/* 178 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSernp("NONE");
/*     */     } 
/* 180 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setPeinh("1");
/* 181 */     if ("Hardware".equals(model.getCATEGORY()) && "ZMAT".equals(materialType)) {
/* 182 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setBwtty("X");
/*     */     }
/*     */     
/* 185 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setVersg("1");
/* 186 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSktof("X");
/* 187 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setAumng("1");
/* 188 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setLfmng("1");
/* 189 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setScmng("1");
/*     */     
/* 191 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMtpos(getMtpos(model));
/* 192 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setProdh(model.getPRODHIERCD());
/*     */     
/* 194 */     if (materialID.endsWith("NEW")) {
/* 195 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setKtgrm("01");
/* 196 */     } else if (materialID.endsWith("UPG") || materialID.endsWith("MTC")) {
/* 197 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setKtgrm("29");
/*     */     
/*     */     }
/* 200 */     else if ("ZPRT".equals(materialType)) {
/* 201 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setKtgrm(model.getACCTASGNGRP());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 206 */     List<TAXCODE> taxcodes = model.getTAXCODELIST();
/* 207 */     if (taxcodes != null && taxcodes.size() > 0) {
/* 208 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMvgr5(((TAXCODE)taxcodes.get(0)).getTAXCODE());
/*     */     }
/*     */ 
/*     */     
/* 212 */     if ("ZMAT".equals(materialType)) {
/*     */       
/* 214 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setKzkfg("X");
/* 215 */       if ("Y".equals(model.getDEFAULTCUSTOMIZEABLE())) {
/* 216 */         ((RdhMatm_bmmh1)this.bmmh1.get(0)).setZconf("E");
/*     */       }
/* 218 */       else if ("N".equals(model.getDEFAULTCUSTOMIZEABLE())) {
/* 219 */         ((RdhMatm_bmmh1)this.bmmh1.get(0)).setZconf("F");
/*     */       } 
/*     */     } 
/*     */     
/* 223 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeib1("X");
/* 224 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeid1("X");
/* 225 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeik1("X");
/* 226 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeiv1("X");
/* 227 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeie1("X");
/* 228 */     List<LANGUAGE> languages = model.getLANGUAGELIST();
/* 229 */     if (languages != null && languages.size() > 0) {
/* 230 */       String nlsid = ((LANGUAGE)languages.get(0)).getNLSID();
/* 231 */       if ("1".equals(nlsid)) {
/* 232 */         ((RdhMatm_bmmh5)this.bmmh5.get(0)).setSpras("E");
/* 233 */       } else if ("2".equals(nlsid)) {
/* 234 */         ((RdhMatm_bmmh5)this.bmmh5.get(0)).setSpras("D");
/*     */       }
/* 236 */       else if ("11".equals(nlsid)) {
/* 237 */         ((RdhMatm_bmmh5)this.bmmh5.get(0)).setSpras("1");
/*     */       } 
/*     */       
/* 240 */       if (materialID.endsWith("NEW")) {
/* 241 */         if ("'Storage Tier', 'Power Tier'".contains(model.getSUBGROUP()) && !"".equals(model.getSUBGROUP())) {
/* 242 */           ((RdhMatm_bmmh5)this.bmmh5.get(0)).setMaktx("Expert Care " + model.getMACHTYPE());
/* 243 */         } else if ("STaaS".equals(model.getSUBGROUP())) {
/* 244 */           ((RdhMatm_bmmh5)this.bmmh5.get(0)).setMaktx("STaaS " + model.getMACHTYPE());
/* 245 */         } else if ("PWaaS".equals(model.getSUBGROUP())) {
/* 246 */           ((RdhMatm_bmmh5)this.bmmh5.get(0)).setMaktx("PWaaS " + model.getMACHTYPE());
/*     */         } else {
/* 248 */           ((RdhMatm_bmmh5)this.bmmh5.get(0)).setMaktx("MACHINE TYPE " + model.getMACHTYPE() + " - Model NEW");
/*     */         } 
/* 250 */         ((RdhMatm_bmmh5)this.bmmh5.get(0)).setTdline("MACHINE TYPE " + model.getMACHTYPE() + " - Model NEW");
/*     */       
/*     */       }
/* 253 */       else if (materialID.endsWith("UPG")) {
/* 254 */         ((RdhMatm_bmmh5)this.bmmh5.get(0)).setMaktx("MACHINE TYPE " + model.getMACHTYPE() + " - Model UPG");
/* 255 */         ((RdhMatm_bmmh5)this.bmmh5.get(0)).setTdline("MACHINE TYPE " + model.getMACHTYPE() + " - Model UPG");
/* 256 */       } else if (materialID.endsWith("MTC")) {
/* 257 */         ((RdhMatm_bmmh5)this.bmmh5.get(0)).setMaktx("MACHINE TYPE " + model.getMACHTYPE() + " - Model MTC");
/* 258 */         ((RdhMatm_bmmh5)this.bmmh5.get(0)).setTdline("MACHINE TYPE " + model.getMACHTYPE() + " - Model MTC");
/*     */       } else {
/* 260 */         ((RdhMatm_bmmh5)this.bmmh5.get(0)).setMaktx(((LANGUAGE)languages.get(0)).getINVNAME());
/* 261 */         ((RdhMatm_bmmh5)this.bmmh5.get(0)).setTdline(((LANGUAGE)languages.get(0)).getMKTGNAME());
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     List<AVAILABILITY> availabilities = model.getAVAILABILITYLIST();
/* 266 */     List<TAXCATEGORY> taxcategories = model.getTAXCATEGORYLIST();
/*     */     
/* 268 */     RdhMatm_geo geo = new RdhMatm_geo();
/* 269 */     geo.setName("WW1");
/* 270 */     geo.setVmsta("Z0");
/* 271 */     if (materialID.endsWith("UPG")) {
/* 272 */       String pubfrom = DateUtility.getTodayStringWithSapFormat();
/* 273 */       geo.setVmstd(pubfrom);
/*     */     } else {
/* 275 */       geo.setVmstd(getEarliestPUBFROM(model));
/*     */     } 
/*     */ 
/*     */     
/* 279 */     Set<SLEORGNPLNTCODE> sset = new HashSet<>();
/* 280 */     Set<TAXCATEGORY> cset = new HashSet<>();
/* 281 */     Set<String> slorgSet = new HashSet<>();
/* 282 */     Set<String> plntSet = new HashSet<>();
/* 283 */     List<RdhMatm_sales_org> sales_orgList = new ArrayList<>();
/* 284 */     List<RdhMatm_plant> plantList = new ArrayList<>();
/* 285 */     Set<String> set = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     List<RdhMatm_tax_country> tax_countries = new ArrayList<>();
/* 334 */     List<CountryPlantTax> taxs = RFCConfig.getTaxs();
/* 335 */     List<Generalarea> generalareas = RFCConfig.getGeneralareas();
/* 336 */     Set<String> plntcdtSet = new HashSet<>();
/* 337 */     Set<String> saleorgSet = new HashSet<>();
/* 338 */     Set<String> countrySet = new HashSet<>();
/*     */     int j;
/* 340 */     for (j = 0; j < taxs.size(); j++) {
/*     */       
/* 342 */       CountryPlantTax tax = taxs.get(j);
/* 343 */       if ("2".equals(tax.getINTERFACE_ID())) {
/*     */         
/* 345 */         String plntcd = tax.getPLNT_CD();
/* 346 */         String salesorg = tax.getSALES_ORG();
/* 347 */         if (!plntcdtSet.contains(plntcd)) {
/* 348 */           plntcdtSet.add(plntcd);
/* 349 */           RdhMatm_plant plant = new RdhMatm_plant();
/* 350 */           plant.setWerks(plntcd);
/* 351 */           plant.setEkgrp("ZZZ");
/*     */           
/* 353 */           if (tax.getTAX_COUNTRY() != null && !tax.getTAX_COUNTRY().equals("")) {
/* 354 */             plant.setSteuc(getSteucCodeNew(model, tax.getTAX_COUNTRY()));
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 365 */           plantList.add(plant);
/*     */         } 
/* 367 */         if (!saleorgSet.contains(salesorg)) {
/* 368 */           saleorgSet.add(salesorg);
/* 369 */           RdhMatm_sales_org sales_org = new RdhMatm_sales_org();
/* 370 */           sales_org.setVkorg(salesorg);
/* 371 */           sales_org.setDwerk(salesorg);
/* 372 */           if (RFCConfig.getDwerk("2", sales_org.getVkorg()) != null) {
/*     */             
/* 374 */             sales_org.setDwerk(RFCConfig.getDwerk("2", sales_org.getVkorg()));
/* 375 */             String countrt = null;
/* 376 */             if ("0147".equals(sales_org.getVkorg())) {
/* 377 */               countrt = "US";
/* 378 */             } else if ("0026".equals(sales_org.getVkorg())) {
/* 379 */               countrt = "Canada";
/*     */             } 
/* 381 */             if (countrt != null) {
/*     */ 
/*     */               
/* 384 */               sales_org.setZtaxclsf(getZtaxclsf(model, countrt));
/* 385 */               if (sales_org != null) {
/* 386 */                 sales_org.setZsabrtax(getZsabrtax(sales_org.getZtaxclsf()));
/*     */               }
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 394 */             if (!set.contains(String.valueOf(sales_org.getVkorg()) + sales_org.getDwerk())) {
/*     */               
/* 396 */               sales_orgList.add(sales_org);
/* 397 */               set.add(String.valueOf(sales_org.getVkorg()) + sales_org.getDwerk());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 404 */     if (materialID.endsWith("UPG") || materialID.endsWith("MTC")) {
/* 405 */       for (j = 0; j < taxs.size(); j++) {
/* 406 */         CountryPlantTax tax = taxs.get(j);
/*     */         
/* 408 */         if ("2".equals(tax.getINTERFACE_ID()) && 
/* 409 */           !countrySet.contains(tax.getTAX_COUNTRY()))
/*     */         {
/* 411 */           countrySet.add(tax.getTAX_COUNTRY());
/* 412 */           RdhMatm_tax_country tax_country = new RdhMatm_tax_country();
/* 413 */           tax_country.setAland(tax.getTAX_COUNTRY());
/* 414 */           tax_country.setTaty1(tax.getTAX_CAT());
/* 415 */           tax_country.setTaxm1(tax.getTAX_CLAS());
/* 416 */           tax_country.setTaxm2(tax.getTAX_CLAS());
/* 417 */           tax_country.setTaxm3(tax.getTAX_CLAS());
/* 418 */           tax_country.setTaxm4(tax.getTAX_CLAS());
/* 419 */           tax_country.setTaxm5(tax.getTAX_CLAS());
/* 420 */           tax_country.setTaxm6(tax.getTAX_CLAS());
/* 421 */           tax_country.setTaxm7(tax.getTAX_CLAS());
/* 422 */           tax_country.setTaxm8(tax.getTAX_CLAS());
/* 423 */           tax_country.setTaxm9(tax.getTAX_CLAS());
/*     */           
/* 425 */           if (tax_country.getAland() != null) {
/* 426 */             tax_countries.add(tax_country);
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 431 */     } else if (materialID.endsWith("NEW") || "ZPRT".equals(materialType)) {
/*     */ 
/*     */       
/* 434 */       for (j = 0; j < taxcategories.size(); j++) {
/* 435 */         TAXCATEGORY taxcategory = taxcategories.get(j);
/* 436 */         List<COUNTRY> countrys = taxcategory.getCOUNTRYLIST();
/* 437 */         if (taxcategory.getTAXCLASSIFICATION() != null && !"".equals(taxcategory.getTAXCLASSIFICATION().trim()))
/*     */         {
/*     */           
/* 440 */           for (int k = 0; k < countrys.size(); k++) {
/* 441 */             COUNTRY country = countrys.get(k);
/* 442 */             boolean notmatch = false;
/* 443 */             if ("1652".equals(country.getCOUNTRY_FC())) {
/* 444 */               List<SLEORGGRP> sleorggrps = taxcategory.getSLEORGGRPLIST();
/* 445 */               notmatch = true;
/* 446 */               for (int m = 0; m < sleorggrps.size(); m++) {
/*     */                 
/* 448 */                 if ("US".equals(((SLEORGGRP)sleorggrps.get(m)).getSLEORGGRP())) {
/* 449 */                   notmatch = false;
/*     */ 
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 457 */             if (!notmatch && !countrySet.contains(country.getCOUNTRY_FC())) {
/* 458 */               countrySet.add(country.getCOUNTRY_FC());
/*     */ 
/*     */               
/* 461 */               RdhMatm_tax_country tax_country = new RdhMatm_tax_country();
/* 462 */               tax_country.setAland(RFCConfig.getAland(country.getCOUNTRY_FC()));
/* 463 */               tax_country.setTaty1(taxcategory.getTAXCATEGORYVALUE());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 476 */               tax_country.setTaxm1("1");
/* 477 */               tax_country.setTaxm2("1");
/* 478 */               tax_country.setTaxm3("1");
/* 479 */               tax_country.setTaxm4("1");
/* 480 */               tax_country.setTaxm5("1");
/* 481 */               tax_country.setTaxm6("1");
/* 482 */               tax_country.setTaxm7("1");
/* 483 */               tax_country.setTaxm8("1");
/* 484 */               tax_country.setTaxm9("1");
/*     */               
/* 486 */               if (tax_country.getAland() != null) {
/* 487 */                 tax_countries.add(tax_country);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 497 */     geo.setPlants(plantList);
/* 498 */     geo.setSales_orgs(sales_orgList);
/* 499 */     geo.setTax_countries(tax_countries);
/* 500 */     this.geos.add(geo);
/*     */ 
/*     */     
/* 503 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setTcode("MM01");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getSteucCode(MODEL model, String country) {
/* 509 */     System.out.println("Getting HSN Steuc Code");
/* 510 */     String steucCode = null;
/* 511 */     String machTypeMatch = model.getMACHTYPE();
/* 512 */     System.out.println("Machine type model passed " + machTypeMatch);
/* 513 */     System.out.println("County code passed " + country);
/*     */     
/* 515 */     List<HSN> hsns = RFCConfig.getHsns();
/*     */     
/* 517 */     for (int i = 0; i < hsns.size(); i++) {
/* 518 */       HSN hsn = hsns.get(i);
/* 519 */       if (hsn.getCountry().equals(country) && hsn.getMachType().equals(machTypeMatch))
/* 520 */         steucCode = hsn.getSteuc(); 
/*     */     } 
/* 522 */     System.out.println("returned HSN Steuc Code " + steucCode);
/* 523 */     return steucCode;
/*     */   }
/*     */   
/*     */   private String getSteucCodeNew(MODEL model, String aland) {
/* 527 */     System.out.println("Getting HSN Steuc Code");
/* 528 */     String steucCode = null;
/* 529 */     String machTypeMatch = model.getMACHTYPE();
/* 530 */     System.out.println("Machine type model passed " + machTypeMatch);
/* 531 */     System.out.println("Aland code passed " + aland);
/*     */     
/* 533 */     List<HSN> hsns = RFCConfig.getHsns();
/*     */     
/* 535 */     for (int i = 0; i < hsns.size(); i++) {
/* 536 */       HSN hsn = hsns.get(i);
/* 537 */       if (hsn.getMachType().equals(machTypeMatch) && hsn.getaLand().equals(aland))
/* 538 */         steucCode = hsn.getSteuc(); 
/*     */     } 
/* 540 */     System.out.println("returned HSN Steuc Code " + steucCode);
/* 541 */     return steucCode;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getZsabrtax(String ztaxclsf) {
/* 546 */     return RfcConfigProperties.getZsabrtaxPropertys(ztaxclsf);
/*     */   }
/*     */   
/*     */   private String getZtaxclsf(MODEL model, String country) {
/* 550 */     List<TAXCATEGORY> taxs = model.getTAXCATEGORYLIST();
/* 551 */     if (taxs != null && taxs.size() > 0) {
/* 552 */       for (int i = 0; i < taxs.size(); i++) {
/* 553 */         List<SLEORGGRP> list = ((TAXCATEGORY)taxs.get(i)).getSLEORGGRPLIST();
/* 554 */         if (list != null && list.size() > 0) {
/* 555 */           for (int j = 0; j < list.size(); j++) {
/* 556 */             if (country.equals(((SLEORGGRP)list.get(j)).getSLEORGGRP())) {
/* 557 */               return ((TAXCATEGORY)taxs.get(i)).getTAXCLASSIFICATION();
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 563 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getMtpos(MODEL model) {
/* 569 */     String result = "";
/* 570 */     String var = (model.getPHANTOMMODINDC() == null) ? "" : model.getPHANTOMMODINDC().toUpperCase();
/* 571 */     String materialID = ((RdhMatm_bmm00)this.bmm00.get(0)).getMatnr();
/*     */     
/* 573 */     if (materialID.endsWith("NEW")) {
/* 574 */       if ("NORM".equals(var)) {
/* 575 */         result = "ZPT1";
/* 576 */       } else if ("REACH".equals(var)) {
/* 577 */         result = "ZPT2";
/* 578 */       } else if (RfcConfigProperties.getMtposMachtype().contains(model.getMACHTYPE())) {
/* 579 */         result = "ZPT4";
/* 580 */       } else if ("LBS".equals(var)) {
/* 581 */         result = "ZPT3";
/*     */       } else {
/* 583 */         result = "Z002";
/*     */       } 
/* 585 */       if ("P1030".equals(model.getPRFTCTR())) {
/* 586 */         result = "ZHCR";
/*     */       }
/* 588 */     } else if (materialID.endsWith("UPG") || materialID.endsWith("MTC")) {
/* 589 */       if ("NORM".equals(var)) {
/* 590 */         result = "ZPT1";
/* 591 */       } else if ("REACH".equals(var)) {
/* 592 */         result = "ZPT2";
/*     */       }
/* 594 */       else if ("LBS".equals(var)) {
/* 595 */         result = "ZPT3";
/*     */       } else {
/*     */         
/* 598 */         result = "Z002";
/*     */       }
/*     */     
/* 601 */     } else if ("ZPRT".equals(((RdhMatm_bmm00)this.bmm00.get(0)).getMtart()) && 
/* 602 */       "Hardware".equals(model.getCATEGORY())) {
/* 603 */       result = "ZSUP";
/*     */     } 
/*     */ 
/*     */     
/* 607 */     return result;
/*     */   }
/*     */   
/*     */   public String getMtart(SVCMOD svcmod) {
/* 611 */     String mtart = "ZSV1";
/* 612 */     if (svcmod == null)
/* 613 */       return mtart; 
/* 614 */     if ("Service".equals(svcmod.getCATEGORY())) {
/* 615 */       if ("Custom".equals(svcmod.getSUBCATEGORY())) {
/* 616 */         mtart = "ZSV1";
/* 617 */       } else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
/* 618 */         mtart = "ZSV2";
/* 619 */       } else if ("Productized Services".equals(svcmod.getSUBCATEGORY()) && 
/* 620 */         "Non-Federated".equals(svcmod.getGROUP())) {
/* 621 */         mtart = "ZSV5";
/*     */       } 
/* 623 */     } else if ("IP".equals(svcmod.getCATEGORY()) && "SC".equals(svcmod.getSUBCATEGORY())) {
/* 624 */       mtart = "ZSV1";
/*     */     } 
/* 626 */     return mtart;
/*     */   }
/*     */   
/*     */   public String getMeins(SVCMOD svcmod) {
/* 630 */     String reslut = "EA";
/* 631 */     if (svcmod == null)
/* 632 */       return reslut; 
/* 633 */     if ("Service".equals(svcmod.getCATEGORY())) {
/* 634 */       if ("Custom".equals(svcmod.getSUBCATEGORY())) {
/* 635 */         reslut = "EA";
/* 636 */       } else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
/* 637 */         reslut = "EA";
/* 638 */       } else if ("Productized Services".equals(svcmod.getSUBCATEGORY()) && 
/* 639 */         "Non-Federated".equals(svcmod.getGROUP())) {
/* 640 */         reslut = "EA";
/*     */       } 
/* 642 */     } else if ("IP".equals(svcmod.getCATEGORY()) && "SC".equals(svcmod.getSUBCATEGORY())) {
/* 643 */       reslut = "EA";
/*     */     } 
/* 645 */     return reslut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getEarliestAnnDate(MODEL model) {
/* 812 */     Date annDate = null;
/* 813 */     String result = "";
/* 814 */     List<AVAILABILITY> list = model.getAVAILABILITYLIST();
/* 815 */     if (list != null && list.size() > 0) {
/* 816 */       for (int i = 0; i < list.size(); i++) {
/*     */         try {
/* 818 */           if (annDate == null) {
/* 819 */             result = ((AVAILABILITY)list.get(i)).getANNDATE();
/*     */             
/* 821 */             annDate = this.sdf.parse(result);
/*     */           } else {
/*     */             
/* 824 */             annDate = this.sdf.parse(((AVAILABILITY)list.get(i)).getANNDATE());
/*     */             
/* 826 */             if (annDate.before(this.sdf.parse(result))) {
/* 827 */               result = ((AVAILABILITY)list.get(i)).getANNDATE();
/*     */             }
/*     */           } 
/* 830 */         } catch (Exception e) {
/* 831 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 838 */     if (result != null)
/* 839 */       result = result.replace("-", ""); 
/* 840 */     if (result != null && result.length() > 6) {
/* 841 */       result = result.substring(result.length() - 6);
/*     */     }
/* 843 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getEarliestPUBFROM(MODEL model) {
/* 853 */     Date PUBFROM = null;
/* 854 */     String result = "";
/* 855 */     List<AVAILABILITY> list = model.getAVAILABILITYLIST();
/* 856 */     if (list != null && list.size() > 0) {
/* 857 */       for (int i = 0; i < list.size(); i++) {
/*     */         try {
/* 859 */           if (PUBFROM == null) {
/* 860 */             result = ((AVAILABILITY)list.get(i)).getPUBFROM();
/* 861 */             PUBFROM = this.sdf.parse(result);
/*     */           } else {
/*     */             
/* 864 */             PUBFROM = this.sdf.parse(((AVAILABILITY)list.get(i)).getPUBFROM());
/* 865 */             if (PUBFROM.before(this.sdf.parse(result))) {
/* 866 */               result = ((AVAILABILITY)list.get(i)).getPUBFROM();
/*     */             }
/*     */           } 
/* 869 */         } catch (Exception e) {
/* 870 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 882 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/* 892 */     this.is_multi_plants = "TRUE";
/* 893 */     RdhMatm_bmm00 bm0 = new RdhMatm_bmm00();
/* 894 */     this.bmm00 = new ArrayList<>();
/* 895 */     this.bmm00.add(bm0);
/* 896 */     RdhMatm_bmmh1 bmh1 = new RdhMatm_bmmh1();
/* 897 */     this.bmmh1 = new ArrayList<>();
/* 898 */     this.bmmh1.add(bmh1);
/* 899 */     RdhMatm_bmmh5 bmh5 = new RdhMatm_bmmh5();
/* 900 */     this.bmmh5 = new ArrayList<>();
/* 901 */     this.bmmh5.add(bmh5);
/*     */     
/* 903 */     this.bmmh6 = new ArrayList<>();
/*     */     
/* 905 */     RdhMatm_bmmh7 bmh7 = new RdhMatm_bmmh7();
/* 906 */     this.bmmh7 = new ArrayList<>();
/* 907 */     this.bmmh7.add(bmh7);
/* 908 */     this.geos = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isReadyToExecute() {
/* 916 */     if (getRfcrc() != 0) {
/* 917 */       return false;
/*     */     }
/* 919 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String transformAddDate(String time) {
/* 929 */     StringBuffer add_date = new StringBuffer(time);
/* 930 */     add_date.insert(6, "-");
/* 931 */     add_date.insert(4, "-");
/* 932 */     return add_date.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwMatmCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */