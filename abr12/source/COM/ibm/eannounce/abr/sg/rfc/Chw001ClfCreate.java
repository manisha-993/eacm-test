/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Chw001ClfCreate
/*     */   extends RfcCallerBase
/*     */ {
/*     */   MODEL chwModel;
/*     */   String materialType;
/*     */   String materialID;
/*     */   Connection odsConnection;
/*     */   String rfaNum;
/*     */   
/*     */   public Chw001ClfCreate(MODEL paramMODEL, String paramString1, String paramString2, String paramString3, Connection paramConnection) {
/*  27 */     this.chwModel = paramMODEL;
/*  28 */     this.materialType = paramString1;
/*  29 */     this.materialID = paramString2;
/*  30 */     this.odsConnection = paramConnection;
/*  31 */     this.rfaNum = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute() throws Exception {
/*  39 */     if (this.chwModel == null)
/*     */       return; 
/*  41 */     RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(this.materialID, "MG_COMMON", "001", "H", this.rfaNum);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     addRfcName(rdhClassificationMaint);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     String str1 = "CH";
/*  58 */     if ("ZPRT".equalsIgnoreCase(this.materialType)) {
/*  59 */       if ("Hardware".equalsIgnoreCase(this.chwModel.getCATEGORY())) {
/*  60 */         str1 = "HW";
/*  61 */       } else if ("Service".equalsIgnoreCase(this.chwModel.getCATEGORY())) {
/*  62 */         str1 = "SP";
/*     */       } 
/*     */     }
/*  65 */     rdhClassificationMaint.addCharacteristic("MG_PRODUCTTYPE", str1);
/*  66 */     rdhClassificationMaint.execute();
/*  67 */     addRfcResult(rdhClassificationMaint);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     rdhClassificationMaint = null;
/*  73 */     rdhClassificationMaint = new RdhClassificationMaint(this.materialID, "MM_FIELDS", "001", "H", this.rfaNum);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     addRfcName(rdhClassificationMaint);
/*     */ 
/*     */ 
/*     */     
/*  84 */     str1 = CommonUtils.getFirstSubString(this.materialID, 4);
/*  85 */     rdhClassificationMaint.addCharacteristic("MM_MACH_TYPE", str1);
/*     */     
/*  87 */     if ("ZPRT".equalsIgnoreCase(this.materialType)) {
/*  88 */       str1 = CommonUtils.getLastSubString(this.materialID, 3);
/*  89 */       rdhClassificationMaint.addCharacteristic("MM_MODEL", str1);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     str1 = "";
/*  99 */     if ("SIU-CPU".equalsIgnoreCase(this.chwModel.getUNITCLASS())) {
/* 100 */       str1 = "1";
/* 101 */     } else if ("Non SIU- CPU".equalsIgnoreCase(this.chwModel.getUNITCLASS())) {
/* 102 */       str1 = "0";
/* 103 */     } else if ("SIU-Non CPU".equalsIgnoreCase(this.chwModel.getUNITCLASS())) {
/* 104 */       str1 = "2";
/*     */     } 
/*     */     
/* 107 */     rdhClassificationMaint.addCharacteristic("MM_SIU", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     str1 = "";
/* 119 */     String str2 = this.chwModel.getPRICEDIND().toUpperCase();
/* 120 */     String str3 = this.chwModel.getZEROPRICE().toUpperCase();
/* 121 */     if ("YES".equals(str2) && "YES".equals(str3)) {
/* 122 */       str1 = "Z";
/* 123 */     } else if ("NO".equals(str2) && "NO".equals(str3)) {
/* 124 */       str1 = "Z";
/* 125 */     } else if ("YES".equals(str2) && "NO".equals(str3)) {
/* 126 */       str1 = "G";
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     rdhClassificationMaint.addCharacteristic("MM_PRICERELEVANT", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     str1 = "";
/* 145 */     str2 = this.chwModel.getINSTALL().toUpperCase();
/* 146 */     if ("CIF".equals(str2)) {
/* 147 */       str1 = "C";
/* 148 */     } else if ("CE".equals(str2)) {
/* 149 */       str1 = "I";
/* 150 */     } else if ("N/A".equals(str2)) {
/* 151 */       str1 = "";
/* 152 */     } else if ("DOES NOT APPLY".equals(str2)) {
/* 153 */       str1 = "";
/*     */     } 
/* 155 */     rdhClassificationMaint.addCharacteristic("MM_FG_INSTALLABLE", str1);
/*     */ 
/*     */ 
/*     */     
/* 159 */     str1 = this.chwModel.getUNSPSC();
/* 160 */     rdhClassificationMaint.addCharacteristic("MM_UNSPSC", CommonUtils.getFirstSubString(str1, 8));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     String str4 = this.chwModel.getAMRTZTNLNGTH();
/* 167 */     str1 = CommonUtils.getNoLetter(str4);
/* 168 */     rdhClassificationMaint.addCharacteristic("MM_AMORTLENGTH", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     str4 = this.chwModel.getAMRTZTNSTRT();
/* 174 */     str1 = CommonUtils.getNoLetter(str4);
/* 175 */     rdhClassificationMaint.addCharacteristic("MM_AMORTSTART", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     str1 = "";
/* 182 */     str2 = this.chwModel.getPRODID().toUpperCase();
/* 183 */     if ("O-OTHER/OPTIONS".equalsIgnoreCase(str2)) {
/* 184 */       str1 = "O";
/* 185 */     } else if ("U-SYSTEM UNIT".equalsIgnoreCase(str2)) {
/* 186 */       str1 = "U";
/*     */     } 
/* 188 */     rdhClassificationMaint.addCharacteristic("MM_IDENTITY", str1);
/*     */ 
/*     */     
/* 191 */     str1 = this.chwModel.getSWROYALBEARING();
/* 192 */     rdhClassificationMaint.addCharacteristic("MM_ROYALTY_BEAR_IND", CommonUtils.getFirstSubString(str1, 20));
/*     */ 
/*     */     
/* 195 */     str1 = this.chwModel.getSOMFAMILY();
/* 196 */     rdhClassificationMaint.addCharacteristic("MM_SOM_FAMILY", CommonUtils.getFirstSubString(str1, 2));
/*     */ 
/*     */     
/* 199 */     str1 = this.chwModel.getLIC();
/* 200 */     rdhClassificationMaint.addCharacteristic("MM_LIC", CommonUtils.getFirstSubString(str1, 3));
/*     */ 
/*     */     
/* 203 */     str1 = this.chwModel.getBPCERTSPECBID();
/* 204 */     if ("YES".equalsIgnoreCase(str1)) {
/* 205 */       str1 = "Y";
/* 206 */     } else if ("NO".equalsIgnoreCase(str1)) {
/* 207 */       str1 = "N";
/*     */     } 
/*     */     
/* 210 */     rdhClassificationMaint.addCharacteristic("MM_BP_CERT_SPECBID", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     str1 = "";
/* 219 */     str2 = this.chwModel.getPRPQAPPRVTYPE().toUpperCase();
/* 220 */     if ("RPQ APPROVE I-LISTED".equalsIgnoreCase(str2)) {
/* 221 */       str1 = "RPQ APPROVE";
/* 222 */     } else if ("RPQ OTHER P-LISTED".equalsIgnoreCase(str2)) {
/* 223 */       str1 = "RPQ OTHER";
/*     */     } 
/* 225 */     rdhClassificationMaint.addCharacteristic("MM_RPQTYPE", str1);
/*     */ 
/*     */ 
/*     */     
/* 229 */     str1 = "YES".equalsIgnoreCase(this.chwModel.getSPECBID()) ? "SPB" : "RFA";
/* 230 */     rdhClassificationMaint.addCharacteristic("MM_ANNOUNCEMENT_TYPE", str1);
/*     */ 
/*     */     
/* 233 */     str1 = this.chwModel.getPRODSUPRTCD();
/* 234 */     rdhClassificationMaint.addCharacteristic("MM_PRODUCT_SUPPORT_CODE", CommonUtils.getFirstSubString(str1, 3));
/*     */     
/* 236 */     str1 = this.chwModel.getSYSTEMTYPE();
/* 237 */     rdhClassificationMaint.addCharacteristic("MM_SYSTEM_TYPE", CommonUtils.getFirstSubString(str1, 30));
/*     */     
/* 239 */     if ("ZPRT".equalsIgnoreCase(this.materialType)) {
/* 240 */       str1 = this.chwModel.getPHANTOMMODINDC();
/* 241 */       rdhClassificationMaint.addCharacteristic("MM_PHANTOM_IND", CommonUtils.getFirstSubString(str1, 5));
/*     */     } 
/*     */     
/* 244 */     str1 = "Yes";
/* 245 */     rdhClassificationMaint.addCharacteristic("REMARKETER_REPORTER", str1);
/*     */ 
/*     */     
/* 248 */     if ("ZPRT".equalsIgnoreCase(this.materialType)) {
/* 249 */       if ("Hardware".equalsIgnoreCase(this.chwModel.getCATEGORY())) {
/* 250 */         str1 = "1";
/*     */       } else {
/* 252 */         str1 = "";
/*     */       } 
/*     */     } else {
/* 255 */       str1 = "1";
/*     */     } 
/* 257 */     rdhClassificationMaint.addCharacteristic("MM_PHYSICAL_RETURN", str1);
/*     */     
/* 259 */     str1 = this.chwModel.getWWOCCODE();
/* 260 */     rdhClassificationMaint.addCharacteristic("MM_OPPORTUNITY_CODE", CommonUtils.getFirstSubString(str1, 5));
/*     */     
/* 262 */     if ("ZPRT".equalsIgnoreCase(this.materialType)) {
/* 263 */       str1 = this.chwModel.getACQRCOCD();
/* 264 */       rdhClassificationMaint.addCharacteristic("MM_ACQ_COMPANY", CommonUtils.getFirstSubString(str1, 3));
/*     */     } 
/*     */ 
/*     */     
/* 268 */     if ("Service".equalsIgnoreCase(this.chwModel.getCATEGORY())) {
/* 269 */       str1 = this.materialID;
/* 270 */       rdhClassificationMaint.addCharacteristic("MM_SP_MTM", CommonUtils.getFirstSubString(str1, 7));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     if ("ZPRT".equals(this.materialType)) {
/* 278 */       List<RELEXPCAMT> list = this.chwModel.getRELEXPCAMTLIST();
/* 279 */       if (list != null) {
/* 280 */         for (byte b = 0; b < list.size(); b++) {
/* 281 */           rdhClassificationMaint.addCharacteristic("MM_RELEXPCAMT", ((RELEXPCAMT)list.get(b)).getRELEXPCAMT());
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 286 */     rdhClassificationMaint.execute();
/* 287 */     addRfcResult(rdhClassificationMaint);
/*     */ 
/*     */ 
/*     */     
/* 291 */     if ("Service".equalsIgnoreCase(this.chwModel.getCATEGORY())) {
/*     */       
/* 293 */       RdhClassificationMaint rdhClassificationMaint1 = new RdhClassificationMaint(this.materialID, "MM_SERVICEPAC", "001", "H", this.rfaNum);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 300 */       addRfcName(rdhClassificationMaint1);
/*     */ 
/*     */       
/* 303 */       str1 = this.chwModel.getSDFCD();
/* 304 */       rdhClassificationMaint1.addCharacteristic("MM_SP_SDF", CommonUtils.getFirstSubString(str1, 10));
/*     */       
/* 306 */       str1 = this.chwModel.getSVCLEVCD();
/* 307 */       rdhClassificationMaint1.addCharacteristic("MM_SP_SLC", CommonUtils.getFirstSubString(str1, 10));
/*     */       
/* 309 */       str1 = this.chwModel.getSVCPACMACHBRAND();
/* 310 */       rdhClassificationMaint1.addCharacteristic("MM_HW_SPMACHBRAND", CommonUtils.getFirstSubString(str1, 5));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 317 */       int i = this.chwModel.getSUBCATEGORY().indexOf(" ");
/* 318 */       if (i > 0) {
/* 319 */         str1 = CommonUtils.getFirstSubString(this.chwModel.getSUBCATEGORY(), i);
/*     */       } else {
/* 321 */         str1 = this.chwModel.getSUBCATEGORY();
/*     */       } 
/* 323 */       rdhClassificationMaint1.addCharacteristic("MM_HW_SPTYPE", CommonUtils.getFirstSubString(str1, 10));
/*     */       
/* 325 */       str4 = this.chwModel.getSUBCATEGORY();
/* 326 */       str1 = ATWRTforService(str4);
/* 327 */       rdhClassificationMaint1.addCharacteristic("MM_SP_IDENTIFIER", CommonUtils.getFirstSubString(str1, 5));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 345 */       str2 = this.chwModel.getCOVRPRIOD().toUpperCase();
/* 346 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 347 */       hashMap.put("FIVE YEARS", "60");
/* 348 */       hashMap.put("FIVE YEAR PARTS AND LABOR", "60");
/* 349 */       hashMap.put("FOUR YEARS", "48");
/* 350 */       hashMap.put("ONE YEAR", "12");
/* 351 */       hashMap.put("FOUR YEAR PARTS AND LABOR", "48");
/* 352 */       hashMap.put("THREE YEARS", "36");
/* 353 */       hashMap.put("THREE YEAR PARTS AND LABOR", "36");
/* 354 */       hashMap.put("TWO YEARS", "24");
/* 355 */       hashMap.put("TWO YEAR PARTS AND LABOR (EXTENDS EXISTING 3 YEAR COVERAGE)", "24");
/* 356 */       hashMap.put("SIX YEARS", "72");
/* 357 */       hashMap.put("SEVEN YEARS", "84");
/* 358 */       hashMap.put("THREE MONTHS", "3");
/* 359 */       hashMap.put("3 MONTHS", "3");
/* 360 */       str1 = (String)hashMap.get(str2);
/* 361 */       rdhClassificationMaint1.addCharacteristic("MM_HW_SPTERM", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 366 */       String str5 = this.chwModel.getSVCLEVCD();
/* 367 */       String str6 = "";
/*     */       try {
/* 369 */         str6 = getSVCLEVFromXML(str5);
/* 370 */       } catch (SQLException sQLException) {}
/*     */ 
/*     */       
/* 373 */       SVCLEV sVCLEV = null;
/* 374 */       if (!"".equals(str6)) {
/* 375 */         sVCLEV = CommonEntities.getSVCLEVFromXml(str6);
/*     */       }
/*     */       
/* 378 */       if (sVCLEV != null) {
/*     */ 
/*     */         
/* 381 */         str1 = sVCLEV.getCOVRSHRTDESC();
/* 382 */         rdhClassificationMaint1.addCharacteristic("MM_SP_COVHRS", CommonUtils.getFirstSubString(str1, 5));
/*     */         
/* 384 */         str4 = sVCLEV.getSVCDELIVMETH();
/* 385 */         str1 = getMM_SP_SDMValue(str4);
/* 386 */         rdhClassificationMaint1.addCharacteristic("MM_SP_SDM", CommonUtils.getFirstSubString(str1, 10));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 393 */         String str7 = (sVCLEV.getFIXTME() == null) ? "" : sVCLEV.getFIXTME();
/* 394 */         String str8 = (sVCLEV.getFIXTMEUOM() == null) ? "" : sVCLEV.getFIXTMEUOM();
/* 395 */         String str9 = (sVCLEV.getFIXTMEOBJIVE() == null) ? "" : sVCLEV.getFIXTMEOBJIVE();
/* 396 */         if ("".equals(str7) || "".equals(str8) || "".equals(str9)) {
/* 397 */           str1 = "";
/*     */         } else {
/* 399 */           str1 = str7 + " " + str8 + " " + str9;
/*     */         } 
/* 401 */         rdhClassificationMaint1.addCharacteristic("MM_SPFIXEDTIME", CommonUtils.getFirstSubString(str1, 30));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 408 */         String str10 = (sVCLEV.getONSITERESPTME() == null) ? "" : sVCLEV.getONSITERESPTME();
/* 409 */         String str11 = (sVCLEV.getONSITERESPTMEUOM() == null) ? "" : sVCLEV.getONSITERESPTMEUOM();
/* 410 */         String str12 = (sVCLEV.getONSITERESPTMEOBJIVE() == null) ? "" : sVCLEV.getONSITERESPTMEOBJIVE();
/* 411 */         if ("".equals(str10) || "".equals(str11) || "".equals(str12)) {
/* 412 */           str1 = "";
/*     */         } else {
/* 414 */           str1 = str10 + " " + str11 + " " + CommonUtils.getSubstrToChar(str12, "(");
/*     */         } 
/* 416 */         rdhClassificationMaint1.addCharacteristic("MM_SP_OSRESPTIME", CommonUtils.getFirstSubString(str1, 16));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 423 */         String str13 = (sVCLEV.getCONTTME() == null) ? "" : sVCLEV.getCONTTME();
/* 424 */         String str14 = (sVCLEV.getCONTTMEUOM() == null) ? "" : sVCLEV.getCONTTMEUOM();
/* 425 */         String str15 = (sVCLEV.getCONTTMEOBJIVE() == null) ? "" : sVCLEV.getCONTTMEOBJIVE();
/* 426 */         if ("".equals(str13) || "".equals(str14) || "".equals(str15)) {
/* 427 */           str1 = "";
/*     */         } else {
/* 429 */           str1 = str13 + " " + str14 + " " + str15;
/*     */         } 
/* 431 */         rdhClassificationMaint1.addCharacteristic("MM_SP_CNTACTIME", CommonUtils.getFirstSubString(str1, 30));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 450 */         String str16 = (sVCLEV.getPARTARRVTME() == null) ? "" : sVCLEV.getPARTARRVTME();
/* 451 */         String str17 = (sVCLEV.getPARTARRVTMEUOM() == null) ? "" : sVCLEV.getPARTARRVTMEUOM();
/* 452 */         String str18 = (sVCLEV.getPARTARRVTMEOBJIVE() == null) ? "" : sVCLEV.getPARTARRVTMEOBJIVE();
/* 453 */         if ("".equals(str16) || "".equals(str17) || "".equals(str18)) {
/* 454 */           str1 = "";
/*     */         } else {
/* 456 */           if ("HOURS".equals(str17.toUpperCase())) {
/* 457 */             str1 = "hrs";
/*     */           } else {
/* 459 */             str1 = str17;
/*     */           } 
/* 461 */           if ("T (Target)".equalsIgnoreCase(str18)) {
/* 462 */             str1 = str1 + " Target";
/*     */           } else {
/* 464 */             str1 = str1 + " " + str18;
/*     */           } 
/* 466 */           str1 = str16 + " " + str1;
/*     */         } 
/* 468 */         rdhClassificationMaint1.addCharacteristic("MM_SP_PARIVTIME", CommonUtils.getFirstSubString(str1, 15));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 475 */         String str19 = (sVCLEV.getTRNARNDTME() == null) ? "" : sVCLEV.getTRNARNDTME();
/* 476 */         String str20 = (sVCLEV.getTRNARNDTMEUOM() == null) ? "" : sVCLEV.getTRNARNDTMEUOM();
/* 477 */         String str21 = (sVCLEV.getTRNARNDTMEOBJIVE() == null) ? "" : sVCLEV.getTRNARNDTMEOBJIVE();
/* 478 */         if ("".equals(str19) || "".equals(str20) || "".equals(str21)) {
/* 479 */           str1 = "";
/*     */         } else {
/* 481 */           str1 = str19 + " " + str20 + " " + str21;
/*     */         } 
/* 483 */         rdhClassificationMaint1.addCharacteristic("MM_SP_TARNDTIME", CommonUtils.getFirstSubString(str1, 30));
/*     */       } 
/*     */ 
/*     */       
/* 487 */       rdhClassificationMaint1.execute();
/* 488 */       addRfcResult(rdhClassificationMaint1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getMM_SP_SDMValue(String paramString) {
/* 495 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 496 */     hashMap.put("CCR", "CCR");
/* 497 */     hashMap.put("IOE", "IOE");
/* 498 */     hashMap.put("IOR", "IOR");
/* 499 */     hashMap.put("LOR", "LOR");
/* 500 */     String str = (String)hashMap.get(paramString.toUpperCase());
/* 501 */     if (str == null) str = ""; 
/* 502 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String ATWRTforService(String paramString) {
/* 508 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 509 */     hashMap.put("EURBUNSP", "OTHER");
/* 510 */     hashMap.put("EUREMXSP", "OTHER");
/* 511 */     hashMap.put("EUREXSP", "OTHER");
/* 512 */     hashMap.put("EURMIGSP", "OTHER");
/* 513 */     hashMap.put("EURMVSSP", "OTHER");
/* 514 */     hashMap.put("GENERICHW1", "OTHER");
/* 515 */     hashMap.put("GENERICHW3", "OTHER");
/* 516 */     hashMap.put("GENERICHW5", "OTHER");
/* 517 */     hashMap.put("GENERICHW7", "OTHER");
/* 518 */     hashMap.put("HELPDESK", "OTHER");
/*     */     
/* 520 */     hashMap.put("INSTALL", "OTHER");
/* 521 */     hashMap.put("IPSINSTALL", "OTHER");
/* 522 */     hashMap.put("IPSMA", "OTHER");
/* 523 */     hashMap.put("IPSMAEXT", "OTHER");
/* 524 */     hashMap.put("IPSWAMO", "OTHER");
/* 525 */     hashMap.put("IPSWMOEXT", "OTHER");
/* 526 */     hashMap.put("ITESEDUC", "OTHER");
/* 527 */     hashMap.put("LENINSTL", "OTHER");
/* 528 */     hashMap.put("LENPWUPG", "OTHER");
/* 529 */     hashMap.put("LENTPPOF", "OTHER");
/*     */     
/* 531 */     hashMap.put("LENWAUPG", "OTHER");
/* 532 */     hashMap.put("N/A", "OTHER");
/* 533 */     hashMap.put("SBUNDLE", "OTHER");
/* 534 */     hashMap.put("SBUNDLE1", "OTHER");
/* 535 */     hashMap.put("SBUNDLE2", "OTHER");
/* 536 */     hashMap.put("SBUNDLE3", "OTHER");
/* 537 */     hashMap.put("SBUNDLE4", "OTHER");
/* 538 */     hashMap.put("SBUNDLE5", "OTHER");
/* 539 */     hashMap.put("SBUNDLE6", "OTHER");
/* 540 */     hashMap.put("SERVACCT", "OTHER");
/*     */     
/* 542 */     hashMap.put("STG LAB SERVICES", "OTHER");
/*     */     
/* 544 */     hashMap.put("ENSPEURP", "HW");
/* 545 */     hashMap.put("EURETSSWU", "HW");
/* 546 */     hashMap.put("GENERICHW4", "HW");
/* 547 */     hashMap.put("MAINONLY", "HW");
/* 548 */     hashMap.put("MAINSWSUPP", "HW");
/* 549 */     hashMap.put("MEMEAMAP", "HW");
/* 550 */     hashMap.put("MEMEAWMO", "HW");
/* 551 */     hashMap.put("WMAINOCS", "HW");
/* 552 */     hashMap.put("WMAINTOPT", "HW");
/* 553 */     hashMap.put("GTMSEUR", "HW");
/*     */     
/* 555 */     hashMap.put("PROACTSYS", "HW");
/*     */ 
/*     */     
/* 558 */     hashMap.put("GENERICHW2", "SW");
/* 559 */     hashMap.put("PSSWSUPP", "SW");
/* 560 */     hashMap.put("RTECHSUPEU", "SW");
/* 561 */     hashMap.put("RTECHSUPP", "SW");
/* 562 */     hashMap.put("RTSOS", "SW");
/* 563 */     hashMap.put("RTSSWEU", "SW");
/* 564 */     hashMap.put("RTSXSERIES", "SW");
/* 565 */     hashMap.put("SLEMEASW", "SW");
/* 566 */     hashMap.put("SMOOTHSTRT", "SW");
/* 567 */     hashMap.put("STRTUPSUP", "SW");
/*     */     
/* 569 */     hashMap.put("STRUPSUPEU", "SW");
/* 570 */     hashMap.put("SUPLINEEU", "SW");
/* 571 */     hashMap.put("SUPPORTLN", "SW");
/* 572 */     hashMap.put("SYSEXPERT", "SW");
/* 573 */     hashMap.put("TSEMEASW", "SW");
/* 574 */     hashMap.put("ETSAAEUR", "SW");
/*     */ 
/*     */     
/* 577 */     String str = (String)hashMap.get(paramString.toUpperCase());
/*     */     
/* 579 */     if (str == null) str = "";
/*     */     
/* 581 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getSVCLEVFromXML(String paramString) throws SQLException {
/* 587 */     System.out.println("SVCLEVCD=" + paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 595 */     String str1 = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLCACHEVALIDTO > current timestamp and  XMLENTITYTYPE = 'SVCLEV' and xmlexists('declare default element namespace \"http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/SVCLEV_UPDATE\"; $i/SVCLEV_UPDATE[SVCLEVCD/text() = \"" + paramString + "\"]' passing cache.XMLIDLCACHE.XMLMESSAGE as \"i\") ORDER BY XMLCACHEDTS with ur";
/*     */ 
/*     */ 
/*     */     
/* 599 */     PreparedStatement preparedStatement = this.odsConnection.prepareStatement(str1);
/*     */     
/* 601 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 602 */     String str2 = "";
/* 603 */     if (resultSet.next()) {
/* 604 */       str2 = resultSet.getString("XMLMESSAGE");
/* 605 */       System.out.println("xml=" + str2);
/*     */     } 
/*     */     
/* 608 */     return str2;
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 612 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 613 */     hashMap.put("EURBUNSP", "OTHER");
/* 614 */     hashMap.put("EUREMXSP", "OTHER");
/* 615 */     hashMap.put("EUREXSP", "OTHER");
/* 616 */     hashMap.put("EURMIGSP", "OTHER");
/* 617 */     String str = (String)hashMap.get("123");
/* 618 */     System.out.println("value=" + str);
/* 619 */     str = (String)hashMap.get("EURBUNSP");
/* 620 */     System.out.println("value=" + str);
/* 621 */     str = (String)hashMap.get(null);
/* 622 */     System.out.println("value=" + str);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\Chw001ClfCreate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */