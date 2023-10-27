/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
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
/*     */ import COM.ibm.eannounce.abr.util.RFCConfig;
/*     */ import COM.ibm.eannounce.abr.util.RfcConfigProperties;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class RdhMatmCreate extends RdhBase {
/*     */   @SerializedName("TYPE")
/*     */   private String type;
/*     */   @SerializedName("BMM00")
/*     */   private List<RdhMatm_bmm00> bmm00;
/*     */   @SerializedName("BMMH1")
/*     */   private List<RdhMatm_bmmh1> bmmh1;
/*     */   @SerializedName("BMMH2")
/*     */   private List<RdhMatm_bmmh2> bmmh2;
/*     */   @SerializedName("BMMH5")
/*     */   private List<RdhMatm_bmmh5> bmmh5;
/*     */   @SerializedName("BMMH6")
/*     */   private List<RdhMatm_bmmh6> bmmh6;
/*     */   @SerializedName("BMMH7")
/*     */   private List<RdhMatm_bmmh7> bmmh7;
/*     */   @SerializedName("GEO")
/*     */   private List<RdhMatm_geo> geos;
/*     */   @SerializedName("IS_MULTI_PLANTS")
/*     */   private String is_multi_plants;
/*     */   @SerializedName("MULCOMPINDC")
/*     */   private String mulcompindc;
/*     */   @Foo
/*  50 */   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*     */   @Foo
/*  52 */   SimpleDateFormat sdfANNDATE = new SimpleDateFormat("ddMMyy");
/*     */   @Foo
/*  54 */   String annnumber = null;
/*     */   
/*     */   @Foo
/*  57 */   static HashMap<String, String> org_groupMap = null;
/*     */   @Foo
/*  59 */   static Set<String> werks_set = null;
/*     */   static {
/*  61 */     String[] arrayOfString1 = "0026, 0066, 0147, 0008,0099,0452,0064,0088,0108,8340,0164,0411,0127,0063,7600".split(",");
/*  62 */     String[] arrayOfString2 = "Canada, Ireland, US, Australia, New Zealand, Brunei Darussalam, Indonesia, Malaysia, Philippines, Singapore, Viet Nam, Thailand, Sri Lanka, India, Japan".split(",");
/*  63 */     String[] arrayOfString3 = "1704,IN01,IN02,IN03,IN04,IN05,IN06,IN07,IN08,IN09,IN10,IN11,IN12,IN13,IN14,IN15,IN16,IN17".split(",");
/*  64 */     werks_set = new HashSet<>(Arrays.asList(arrayOfString3));
/*  65 */     org_groupMap = new HashMap<>();
/*  66 */     for (byte b = 0; b < arrayOfString1.length; b++) {
/*  67 */       org_groupMap.put(arrayOfString1[b].trim(), arrayOfString2[b].trim());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public RdhMatmCreate(SVCMOD paramSVCMOD, String paramString) {
/*  73 */     super(paramSVCMOD.getMACHTYPE() + paramSVCMOD.getMODEL(), "Z_DM_SAP_MATM_CREATE".toLowerCase(), null);
/*     */     
/*  75 */     this.mulcompindc = paramString;
/*  76 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setMatnr(paramSVCMOD.getMACHTYPE() + paramSVCMOD.getMODEL());
/*  77 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setMtart(getMtart(paramSVCMOD));
/*  78 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setMbrsh("M");
/*     */     
/*  80 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setVtweg("00");
/*  81 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeik1("X");
/*  82 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setXeiv1("X");
/*  83 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMatkl("000");
/*  84 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMeins(getMeins(paramSVCMOD));
/*     */     
/*  86 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setZeiar("RFA");
/*  87 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setAeszn(getEarliestAnnDate(paramSVCMOD));
/*  88 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setZeinr(this.annnumber);
/*  89 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setGewei("");
/*  90 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setSpart("00");
/*  91 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setProdh(paramSVCMOD.getPRODHIERCD());
/*  92 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMtpos(getMtpos(paramSVCMOD));
/*  93 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setPrctr((paramSVCMOD.getPRFTCTR() == null) ? "" : paramSVCMOD.getPRFTCTR());
/*  94 */     ((RdhMatm_bmmh1)this.bmmh1.get(0)).setKtgrm((paramSVCMOD.getACCTASGNGRP() == null) ? "" : paramSVCMOD.getACCTASGNGRP());
/*     */     
/*  96 */     if ("Yes".equals(paramSVCMOD.getPCTOFCMPLTINDC())) {
/*  97 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setPrat1("X");
/*     */     }
/*  99 */     List<TAXCODE> list = paramSVCMOD.getTAXCODELIST();
/* 100 */     if (list != null && list.size() > 0) {
/* 101 */       ((RdhMatm_bmmh1)this.bmmh1.get(0)).setMvgr5(((TAXCODE)list.get(0)).getTAXCODE());
/*     */     }
/*     */ 
/*     */     
/* 105 */     List<LANGUAGE> list1 = paramSVCMOD.getLANGUAGELIST();
/* 106 */     if (list1 != null && list1.size() > 0) {
/* 107 */       ((RdhMatm_bmmh5)this.bmmh5.get(0)).setSpras("E");
/* 108 */       ((RdhMatm_bmmh5)this.bmmh5.get(0)).setMaktx(((LANGUAGE)list1.get(0)).getINVNAME());
/* 109 */       ((RdhMatm_bmmh5)this.bmmh5.get(0)).setTdline(((LANGUAGE)list1.get(0)).getMKTGNAME());
/*     */     } else {
/* 111 */       ((RdhMatm_bmmh5)this.bmmh5.get(0)).setSpras("E");
/*     */     } 
/*     */     
/* 114 */     if ("ZSV2".equals(((RdhMatm_bmm00)this.bmm00.get(0)).getMtart())) {
/* 115 */       RdhMatm_bmmh6 rdhMatm_bmmh6 = new RdhMatm_bmmh6();
/* 116 */       rdhMatm_bmmh6.setMeinh("SMI");
/* 117 */       this.bmmh6.add(rdhMatm_bmmh6);
/* 118 */       rdhMatm_bmmh6 = new RdhMatm_bmmh6();
/* 119 */       rdhMatm_bmmh6.setMeinh("HUR");
/* 120 */       this.bmmh6.add(rdhMatm_bmmh6);
/*     */     } 
/* 122 */     ((RdhMatm_bmmh7)this.bmmh7.get(0)).setTdid("0001");
/* 123 */     ((RdhMatm_bmmh7)this.bmmh7.get(0)).setTdspras("E");
/*     */     
/* 125 */     if (paramSVCMOD.getLANGUAGELIST() != null) {
/* 126 */       ((RdhMatm_bmmh7)this.bmmh7.get(0)).setTdline(((LANGUAGE)paramSVCMOD.getLANGUAGELIST().get(0)).getMKTGNAME());
/*     */     }
/* 128 */     List<AVAILABILITY> list2 = paramSVCMOD.getAVAILABILITYLIST();
/* 129 */     List<TAXCATEGORY> list3 = paramSVCMOD.getTAXCATEGORYLIST();
/* 130 */     HashSet<TAXCATEGORY> hashSet = new HashSet<>(list3);
/* 131 */     HashSet<?> hashSet1 = new HashSet();
/* 132 */     String str = null;
/* 133 */     byte b = 1;
/* 134 */     HashSet hashSet2 = new HashSet();
/* 135 */     if (list2 != null && list2.size() > 0) {
/*     */ 
/*     */ 
/*     */       
/* 139 */       HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 140 */       HashMap<Object, Object> hashMap2 = new HashMap<>();
/* 141 */       HashMap<Object, Object> hashMap3 = new HashMap<>();
/*     */ 
/*     */ 
/*     */       
/* 145 */       for (byte b1 = 0; b1 < list2.size(); b1++) {
/* 146 */         Set<SLEORGNPLNTCODE> set = null;
/* 147 */         String str1 = null;
/* 148 */         if (((AVAILABILITY)list2.get(b1)).getPUBTO() != null && !"".equals(((AVAILABILITY)list2.get(b1)).getPUBTO())) {
/* 149 */           str1 = "PUBTO" + ((AVAILABILITY)list2.get(b1)).getPUBTO();
/* 150 */           set = (Set)hashMap1.get(str1);
/* 151 */           if (set == null) {
/* 152 */             set = new HashSet();
/* 153 */             hashMap1.put(str1, set);
/*     */           } 
/*     */         } else {
/* 156 */           str1 = "PUBFROM" + ((AVAILABILITY)list2.get(b1)).getPUBFROM();
/* 157 */           set = (Set)hashMap1.get(str1);
/* 158 */           if (set == null) {
/* 159 */             set = new HashSet();
/* 160 */             hashMap1.put(str1, set);
/*     */           } 
/*     */         } 
/* 163 */         set.addAll(((AVAILABILITY)list2.get(b1)).getSLEORGNPLNTCODELIST());
/*     */         
/* 165 */         Set set1 = (Set)hashMap2.get(((AVAILABILITY)list2.get(b1)).getPUBFROM());
/* 166 */         if (set1 == null) {
/* 167 */           set1 = new HashSet();
/* 168 */           hashMap2.put(((AVAILABILITY)list2.get(b1)).getPUBFROM(), set1);
/*     */         } 
/* 170 */         set1.add(list2.get(b1));
/* 171 */         Set set2 = (Set)hashMap3.get(str1);
/* 172 */         if (set2 == null) {
/* 173 */           set2 = new HashSet();
/*     */         }
/* 175 */         if (list3 != null) {
/* 176 */           for (byte b2 = 0; b2 < list3.size(); b2++) {
/* 177 */             if (list3.get(b2) != null && ((TAXCATEGORY)list3.get(b2)).getCOUNTRYLIST() != null && ((TAXCATEGORY)list3
/* 178 */               .get(b2)).getCOUNTRYLIST().size() > 0 && (
/* 179 */               (AVAILABILITY)list2.get(b1)).getCOUNTRY_FC()
/* 180 */               .equals(((COUNTRY)((TAXCATEGORY)list3.get(b2)).getCOUNTRYLIST().get(0)).getCOUNTRY_FC())) {
/* 181 */               set2.add(list3.get(b2));
/* 182 */               hashSet1.add(list3.get(b2));
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 188 */         if (str == null)
/* 189 */           str = str1; 
/* 190 */         hashMap3.put(str1, set2);
/*     */       } 
/*     */ 
/*     */       
/* 194 */       if (str != null) {
/* 195 */         hashSet.removeAll(hashSet1);
/* 196 */         Set<TAXCATEGORY> set = (Set)hashMap3.get(str);
/* 197 */         if (set != null) {
/* 198 */           set.addAll(hashSet);
/*     */         }
/* 200 */         hashMap3.put(str, set);
/*     */       } 
/*     */       
/* 203 */       Iterator<String> iterator = hashMap1.keySet().iterator();
/* 204 */       while (iterator.hasNext()) {
/* 205 */         String str1 = iterator.next();
/*     */         
/* 207 */         RdhMatm_geo rdhMatm_geo = new RdhMatm_geo();
/*     */         
/* 209 */         rdhMatm_geo.setName("WW" + b++);
/* 210 */         if (str1.startsWith("PUBFROM")) {
/* 211 */           rdhMatm_geo.setVmsta("Z0");
/* 212 */           rdhMatm_geo.setVmstd(str1.replace("PUBFROM", ""));
/*     */         } else {
/* 214 */           rdhMatm_geo.setVmsta("ZJ");
/* 215 */           rdhMatm_geo.setVmstd(str1.replace("PUBTO", ""));
/*     */         } 
/*     */         
/* 218 */         ArrayList<SLEORGNPLNTCODE> arrayList = new ArrayList((Collection)hashMap1.get(str1));
/* 219 */         ArrayList<TAXCATEGORY> arrayList1 = new ArrayList((Collection)hashMap3.get(str1));
/* 220 */         ArrayList<RdhMatm_sales_org> arrayList2 = new ArrayList();
/* 221 */         ArrayList<RdhMatm_plant> arrayList3 = new ArrayList();
/* 222 */         ArrayList<RdhMatm_tax_country> arrayList4 = new ArrayList();
/*     */         
/* 224 */         HashSet<String> hashSet3 = new HashSet();
/* 225 */         HashSet<String> hashSet4 = new HashSet(); byte b2;
/* 226 */         for (b2 = 0; b2 < arrayList.size(); b2++) {
/* 227 */           SLEORGNPLNTCODE sLEORGNPLNTCODE = arrayList.get(b2);
/*     */           
/* 229 */           RdhMatm_plant rdhMatm_plant = new RdhMatm_plant();
/* 230 */           rdhMatm_plant.setWerks(sLEORGNPLNTCODE.getPLNTCD());
/* 231 */           if (rdhMatm_plant.getWerks() != null && rdhMatm_plant.getWerks().startsWith("Y")) {
/* 232 */             rdhMatm_plant.setPrctr(((RdhMatm_bmmh1)this.bmmh1.get(0)).getPrctr().replaceFirst("P", "YY"));
/*     */           } else {
/* 234 */             rdhMatm_plant.setPrctr(((RdhMatm_bmmh1)this.bmmh1.get(0)).getPrctr());
/*     */           } 
/* 236 */           RdhMatm_sales_org rdhMatm_sales_org = new RdhMatm_sales_org();
/* 237 */           rdhMatm_sales_org.setVkorg(((SLEORGNPLNTCODE)arrayList.get(b2)).getSLEORG());
/* 238 */           rdhMatm_sales_org.setDwerk(((SLEORGNPLNTCODE)arrayList.get(b2)).getPLNTCD());
/*     */           
/* 240 */           if (org_groupMap.get(((SLEORGNPLNTCODE)arrayList.get(b2)).getSLEORG()) != null) {
/* 241 */             rdhMatm_sales_org.setZtaxclsf(getZtaxclsf(paramSVCMOD, org_groupMap.get(((SLEORGNPLNTCODE)arrayList.get(b2)).getSLEORG())));
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 250 */           if ("0066".equals(rdhMatm_sales_org.getVkorg())) {
/* 251 */             if ("2046".equals(rdhMatm_plant.getWerks())) {
/* 252 */               if ("P4016".equals(((RdhMatm_bmmh1)this.bmmh1.get(0)).getPrctr())) {
/* 253 */                 rdhMatm_sales_org.setZsabrtax("SWMA");
/* 254 */               } else if ("P4022".equals(((RdhMatm_bmmh1)this.bmmh1.get(0)).getPrctr())) {
/* 255 */                 rdhMatm_sales_org.setZsabrtax("HWMA");
/*     */               } else {
/* 257 */                 rdhMatm_sales_org.setZsabrtax(" ");
/*     */               }
/*     */             
/*     */             }
/*     */           }
/* 262 */           else if ("0026,0147".contains(rdhMatm_sales_org.getVkorg())) {
/*     */             
/* 264 */             if (rdhMatm_sales_org.getZtaxclsf() != null && !rdhMatm_sales_org.getZtaxclsf().equals("")) {
/* 265 */               rdhMatm_sales_org.setZsabrtax(getZsabrtax(rdhMatm_sales_org.getZtaxclsf()));
/*     */             }
/* 267 */           } else if (org_groupMap.get(rdhMatm_sales_org.getVkorg()) != null) {
/* 268 */             if ("P4016".equals(((RdhMatm_bmmh1)this.bmmh1.get(0)).getPrctr())) {
/* 269 */               rdhMatm_sales_org.setZsabrtax("SWMA");
/* 270 */             } else if ("P4022".equals(((RdhMatm_bmmh1)this.bmmh1.get(0)).getPrctr())) {
/* 271 */               rdhMatm_sales_org.setZsabrtax("HWMA");
/* 272 */             } else if ("P4065".equals(((RdhMatm_bmmh1)this.bmmh1.get(0)).getPrctr())) {
/* 273 */               rdhMatm_sales_org.setZsabrtax("SMVS");
/*     */             } else {
/* 275 */               rdhMatm_sales_org.setZsabrtax(" ");
/*     */             } 
/*     */           } 
/*     */           
/* 279 */           if (werks_set.contains(rdhMatm_plant.getWerks())) {
/*     */             
/* 281 */             if ("P4016".equals(((RdhMatm_bmmh1)this.bmmh1.get(0)).getPrctr()) || "P4065".equals(((RdhMatm_bmmh1)this.bmmh1.get(0)).getPrctr())) {
/* 282 */               rdhMatm_plant.setSteuc("998313");
/* 283 */             } else if ("P4022".equals(((RdhMatm_bmmh1)this.bmmh1.get(0)).getPrctr())) {
/* 284 */               rdhMatm_plant.setSteuc("998713");
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 289 */           else if ("2123".equals(rdhMatm_plant.getWerks())) {
/* 290 */             rdhMatm_plant.setSteuc("9815.6000");
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
/*     */ 
/*     */           
/* 303 */           if (RFCConfig.getDwerk("6", rdhMatm_sales_org.getVkorg()) != null && !hashSet3.contains(rdhMatm_sales_org.getVkorg())) {
/*     */             
/* 305 */             rdhMatm_sales_org.setDwerk(RFCConfig.getDwerk("6", rdhMatm_sales_org.getVkorg()));
/* 306 */             arrayList2.add(rdhMatm_sales_org);
/* 307 */             hashSet3.add(rdhMatm_sales_org.getVkorg());
/*     */           } 
/* 309 */           if (!hashSet4.contains(rdhMatm_plant.getWerks())) {
/*     */ 
/*     */             
/* 312 */             arrayList3.add(rdhMatm_plant);
/* 313 */             hashSet4.add(rdhMatm_plant.getWerks());
/*     */           } 
/*     */         } 
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
/* 326 */         if (arrayList1 != null)
/* 327 */           for (b2 = 0; b2 < arrayList1.size(); b2++) {
/* 328 */             List<COUNTRY> list4 = ((TAXCATEGORY)arrayList1.get(b2)).getCOUNTRYLIST();
/* 329 */             if (((TAXCATEGORY)arrayList1.get(b2)).getCOUNTRYLIST().size() != 0) {
/*     */               
/* 331 */               RdhMatm_tax_country rdhMatm_tax_country = new RdhMatm_tax_country();
/* 332 */               rdhMatm_tax_country.setTaty1(((TAXCATEGORY)arrayList1.get(b2)).getTAXCATEGORYVALUE());
/*     */               
/* 334 */               rdhMatm_tax_country.setTaxm1("1");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 340 */               rdhMatm_tax_country.setAland(RFCConfig.getAland(((COUNTRY)((TAXCATEGORY)arrayList1.get(b2)).getCOUNTRYLIST().get(0)).getCOUNTRY_FC()));
/* 341 */               rdhMatm_tax_country.setTaxm2("1");
/* 342 */               rdhMatm_tax_country.setTaxm3("1");
/* 343 */               rdhMatm_tax_country.setTaxm4("1");
/* 344 */               rdhMatm_tax_country.setTaxm5("1");
/* 345 */               rdhMatm_tax_country.setTaxm6("1");
/* 346 */               rdhMatm_tax_country.setTaxm7("1");
/* 347 */               rdhMatm_tax_country.setTaxm8("1");
/* 348 */               rdhMatm_tax_country.setTaxm9("1");
/*     */               
/* 350 */               if (rdhMatm_tax_country.getAland() != null) {
/* 351 */                 arrayList4.add(rdhMatm_tax_country);
/*     */               }
/*     */             } 
/*     */           }  
/* 355 */         rdhMatm_geo.setPlants(arrayList3);
/* 356 */         rdhMatm_geo.setSales_orgs(arrayList2);
/* 357 */         rdhMatm_geo.setTax_countries(arrayList4);
/* 358 */         this.geos.add(rdhMatm_geo);
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
/* 445 */     ((RdhMatm_bmm00)this.bmm00.get(0)).setTcode("MM01");
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
/*     */   private String getZsabrtax(String paramString) {
/* 477 */     return RfcConfigProperties.getZsabrtaxPropertys(paramString);
/*     */   }
/*     */   
/*     */   private String getZtaxclsf(SVCMOD paramSVCMOD, String paramString) {
/* 481 */     List<TAXCATEGORY> list = paramSVCMOD.getTAXCATEGORYLIST();
/* 482 */     if (list != null && list.size() > 0) {
/* 483 */       for (byte b = 0; b < list.size(); b++) {
/* 484 */         List<SLEORGGRP> list1 = ((TAXCATEGORY)list.get(b)).getSLEORGGRPLIST();
/* 485 */         if (list1 != null && list1.size() > 0) {
/* 486 */           for (byte b1 = 0; b1 < list1.size(); b1++) {
/* 487 */             if (paramString.equals(((SLEORGGRP)list1.get(b1)).getSLEORGGRP())) {
/* 488 */               return ((TAXCATEGORY)list.get(b)).getTAXCLASSIFICATION();
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 494 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getMtpos(SVCMOD paramSVCMOD) {
/* 500 */     String str = "";
/* 501 */     if (paramSVCMOD == null)
/* 502 */       return str; 
/* 503 */     if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/* 504 */       if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 505 */         if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operation Based".equals(paramSVCMOD.getGROUP())) {
/* 506 */           str = "ZSV1";
/*     */         }
/*     */       }
/* 509 */       else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/*     */ 
/*     */ 
/*     */         
/* 513 */         if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/* 514 */           str = "ZSV4";
/* 515 */         } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/* 516 */           str = "ZSV5";
/* 517 */         } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/* 518 */           str = "ZSTE";
/* 519 */         } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/* 520 */           str = "ZSA1";
/* 521 */         } else if ("OEM".equals(paramSVCMOD.getGROUP())) {
/* 522 */           str = "ZSOE";
/*     */         } 
/* 524 */       } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 525 */         if ("Non-Federated".equals(paramSVCMOD.getGROUP())) {
/* 526 */           str = "ZSA1";
/* 527 */         } else if ("General".equals(paramSVCMOD.getGROUP())) {
/* 528 */           str = "ZSV1";
/*     */         } 
/*     */       } 
/* 531 */     } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 532 */       str = "ZSV1";
/*     */     } 
/* 534 */     return str;
/*     */   }
/*     */   
/*     */   public String getMtart(SVCMOD paramSVCMOD) {
/* 538 */     String str = "ZSV1";
/* 539 */     if (paramSVCMOD == null)
/* 540 */       return str; 
/* 541 */     if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/* 542 */       if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 543 */         str = "ZSV1";
/* 544 */       } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 545 */         str = "ZSV2";
/* 546 */       } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && "Non-Federated"
/* 547 */         .equals(paramSVCMOD.getGROUP())) {
/* 548 */         str = "ZSV5";
/*     */       } 
/* 550 */     } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 551 */       str = "ZSV1";
/*     */     } 
/* 553 */     return str;
/*     */   }
/*     */   
/*     */   public String getMeins(SVCMOD paramSVCMOD) {
/* 557 */     String str = "EA";
/* 558 */     if (paramSVCMOD == null)
/* 559 */       return str; 
/* 560 */     if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/* 561 */       if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 562 */         str = "EA";
/* 563 */       } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 564 */         str = "EA";
/* 565 */       } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && "Non-Federated"
/* 566 */         .equals(paramSVCMOD.getGROUP())) {
/* 567 */         str = "EA";
/*     */       } 
/* 569 */     } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 570 */       str = "EA";
/*     */     } 
/* 572 */     return str;
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
/*     */   public String getEarliestAnnDate(SVCMOD paramSVCMOD) {
/* 739 */     Date date1 = null;
/* 740 */     Date date2 = null;
/* 741 */     String str = "";
/* 742 */     List<AVAILABILITY> list = paramSVCMOD.getAVAILABILITYLIST();
/* 743 */     if (list != null && list.size() > 0) {
/* 744 */       for (byte b = 0; b < list.size(); b++) {
/*     */         try {
/* 746 */           if (date1 == null) {
/*     */             
/* 748 */             this.annnumber = ((AVAILABILITY)list.get(b)).getANNNUMBER();
/* 749 */             date1 = this.sdf.parse(((AVAILABILITY)list.get(b)).getANNDATE());
/*     */           } else {
/*     */             
/* 752 */             date2 = this.sdf.parse(((AVAILABILITY)list.get(b)).getANNDATE());
/*     */             
/* 754 */             if (date2.after(date1)) {
/* 755 */               date1 = date2;
/*     */               
/* 757 */               this.annnumber = ((AVAILABILITY)list.get(b)).getANNNUMBER();
/*     */             } 
/*     */           } 
/* 760 */         } catch (Exception exception) {
/* 761 */           exception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 771 */     if (date1 == null)
/* 772 */       return null; 
/* 773 */     return this.sdfANNDATE.format(date1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/* 783 */     this.is_multi_plants = "TRUE";
/* 784 */     RdhMatm_bmm00 rdhMatm_bmm00 = new RdhMatm_bmm00();
/* 785 */     this.bmm00 = new ArrayList<>();
/* 786 */     this.bmm00.add(rdhMatm_bmm00);
/* 787 */     RdhMatm_bmmh1 rdhMatm_bmmh1 = new RdhMatm_bmmh1();
/* 788 */     this.bmmh1 = new ArrayList<>();
/* 789 */     this.bmmh1.add(rdhMatm_bmmh1);
/* 790 */     RdhMatm_bmmh5 rdhMatm_bmmh5 = new RdhMatm_bmmh5();
/* 791 */     this.bmmh5 = new ArrayList<>();
/* 792 */     this.bmmh5.add(rdhMatm_bmmh5);
/*     */     
/* 794 */     this.bmmh6 = new ArrayList<>();
/*     */     
/* 796 */     RdhMatm_bmmh7 rdhMatm_bmmh7 = new RdhMatm_bmmh7();
/* 797 */     this.bmmh7 = new ArrayList<>();
/* 798 */     this.bmmh7.add(rdhMatm_bmmh7);
/* 799 */     this.geos = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isReadyToExecute() {
/* 807 */     if (getRfcrc() != 0) {
/* 808 */       return false;
/*     */     }
/* 810 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String transformAddDate(String paramString) {
/* 821 */     StringBuffer stringBuffer = new StringBuffer(paramString);
/* 822 */     stringBuffer.insert(6, "-");
/* 823 */     stringBuffer.insert(4, "-");
/* 824 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhMatmCreate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */