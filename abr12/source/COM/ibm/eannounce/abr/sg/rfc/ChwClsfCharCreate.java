/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChwClsfCharCreate
/*     */   extends RfcCallerBase
/*     */ {
/*     */   public void CreateGroupChar(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception {
/*  18 */     String str1 = "";
/*  19 */     if ("T".equalsIgnoreCase(paramString2)) {
/*  20 */       str1 = CommonUtils.getFirstSubString(paramString4, 1) + "000 Features Target";
/*  21 */     } else if ("D".equalsIgnoreCase(paramString2)) {
/*  22 */       str1 = CommonUtils.getFirstSubString(paramString4, 1) + "000 Features Delta";
/*     */     } 
/*  24 */     String str2 = "MK_" + paramString2 + "_" + paramString3 + "_FC_" + CommonUtils.getFirstSubString(paramString4, 1) + "000";
/*     */     
/*  26 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(paramString6, str2, "CHAR", 7, "", "", "", "", "-", "", "", "", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  41 */     addRfcName(chwCharMaintain);
/*     */     
/*  43 */     chwCharMaintain.addValue(paramString4, paramString5);
/*  44 */     chwCharMaintain.execute();
/*  45 */     addRfcResult(chwCharMaintain);
/*     */ 
/*     */     
/*  48 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(paramString6, str2, str2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     addRfcName(chwClassMaintain);
/*     */ 
/*     */     
/*  56 */     chwClassMaintain.addCharacteristic(str2);
/*  57 */     chwClassMaintain.execute();
/*  58 */     addRfcResult(chwClassMaintain);
/*     */ 
/*     */     
/*  61 */     RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(paramString1, str2, "300", "H", paramString6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     addRfcName(rdhClassificationMaint);
/*  68 */     System.out.println("100");
/*  69 */     rdhClassificationMaint.execute();
/*  70 */     addRfcResult(rdhClassificationMaint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void CreateQTYChar(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception {
/*  80 */     String str1 = "MK_" + paramString2 + "_" + paramString3 + "_" + paramString4 + "_QTY";
/*  81 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(paramString5, str1, "NUM", 4, "", "", "", "", "S", "", "", "", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     addRfcName(chwCharMaintain);
/*  97 */     chwCharMaintain.execute();
/*  98 */     addRfcResult(chwCharMaintain);
/*     */ 
/*     */ 
/*     */     
/* 102 */     String str2 = "MK_" + paramString2 + "_" + paramString3 + "_FC_" + CommonUtils.getFirstSubString(paramString4, 1) + "000";
/* 103 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(paramString5, str2, str2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     addRfcName(chwClassMaintain);
/*     */ 
/*     */     
/* 111 */     chwClassMaintain.addCharacteristic(str1);
/* 112 */     chwClassMaintain.execute();
/* 113 */     addRfcResult(chwClassMaintain);
/*     */ 
/*     */     
/* 116 */     RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(paramString1, str2, "300", "H", paramString5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     addRfcName(rdhClassificationMaint);
/* 123 */     rdhClassificationMaint.execute();
/* 124 */     addRfcResult(rdhClassificationMaint);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void CreateRPQGroupChar(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception {
/* 130 */     String str1 = "";
/* 131 */     if ("T".equalsIgnoreCase(paramString2)) {
/* 132 */       str1 = "RPQ Features Target";
/* 133 */     } else if ("D".equalsIgnoreCase(paramString2)) {
/* 134 */       str1 = "RPQ Features Delta";
/*     */     } 
/* 136 */     String str2 = "MK_" + paramString2 + "_" + paramString3 + "_FC_RPQ";
/*     */     
/* 138 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(paramString6, str2, "CHAR", 6, "", "", "", "", "-", "", "", "", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     addRfcName(chwCharMaintain);
/*     */     
/* 155 */     chwCharMaintain.addValue(paramString4, paramString5);
/* 156 */     chwCharMaintain.execute();
/* 157 */     addRfcResult(chwCharMaintain);
/*     */ 
/*     */     
/* 160 */     String str3 = "MK_" + paramString2 + "_" + paramString3 + "_RPQ";
/* 161 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(paramString6, str3, str3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     addRfcName(chwClassMaintain);
/*     */ 
/*     */     
/* 169 */     chwClassMaintain.addCharacteristic(str2);
/* 170 */     chwClassMaintain.execute();
/* 171 */     addRfcResult(chwClassMaintain);
/*     */ 
/*     */     
/* 174 */     RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(paramString1, str3, "300", "H", paramString6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     addRfcName(rdhClassificationMaint);
/*     */     
/* 182 */     rdhClassificationMaint.execute();
/* 183 */     addRfcResult(rdhClassificationMaint);
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
/*     */   public void CreateRPQQTYChar(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception {
/* 195 */     String str1 = "MK_" + paramString2 + "_" + paramString3 + "_RPQ_" + paramString4 + "_QTY";
/*     */     
/* 197 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(paramString5, str1, "NUM", 4, "", "", "", "", "S", "", "", "X", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     addRfcName(chwCharMaintain);
/*     */     
/* 214 */     chwCharMaintain.execute();
/* 215 */     addRfcResult(chwCharMaintain);
/*     */ 
/*     */     
/* 218 */     String str2 = "MK_" + paramString2 + "_" + paramString3 + "_RPQ";
/* 219 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(paramString5, str2, str2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     addRfcName(chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     chwClassMaintain.addCharacteristic(str1);
/* 230 */     chwClassMaintain.execute();
/* 231 */     addRfcResult(chwClassMaintain);
/*     */ 
/*     */     
/* 234 */     RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(paramString1, str2, "300", "H", paramString5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     addRfcName(rdhClassificationMaint);
/*     */     
/* 242 */     rdhClassificationMaint.execute();
/* 243 */     addRfcResult(rdhClassificationMaint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void CreateAlphaGroupChar(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception {
/* 250 */     addOutput("CreateAlphaGroupChar obj_id" + paramString1);
/* 251 */     String str1 = getSuffix(paramString1, paramString2, paramString4, "G");
/*     */ 
/*     */ 
/*     */     
/* 255 */     String str2 = "";
/* 256 */     if ("T".equalsIgnoreCase(paramString2)) {
/* 257 */       str2 = str1 + " Alpha Features Target";
/* 258 */     } else if ("D".equalsIgnoreCase(paramString2)) {
/* 259 */       str2 = str1 + " Alpha Features Delta";
/*     */     } 
/*     */     
/* 262 */     String str3 = "MK_" + paramString2 + "_" + CommonUtils.getFirstSubString(paramString1, 4) + "_FC_ALPH_" + str1;
/*     */     
/* 264 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(paramString6, str3, "CHAR", 12, "", "", "", "", "-", "", "", "X", str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     addRfcName(chwCharMaintain);
/*     */     
/* 281 */     chwCharMaintain.addValue(paramString4, paramString5);
/* 282 */     chwCharMaintain.execute();
/* 283 */     addRfcResult(chwCharMaintain);
/*     */     
/* 285 */     String str4 = "MK_" + paramString2 + "_" + CommonUtils.getFirstSubString(paramString1, 4) + "_ALPH_" + str1;
/* 286 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(paramString6, str4, str4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 291 */     addRfcName(chwClassMaintain);
/*     */     
/* 293 */     chwClassMaintain.addCharacteristic(str3);
/* 294 */     chwClassMaintain.execute();
/* 295 */     addRfcResult(chwClassMaintain);
/*     */     
/* 297 */     RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(paramString1, str4, "300", "H", paramString6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     addRfcName(rdhClassificationMaint);
/* 304 */     rdhClassificationMaint.execute();
/* 305 */     addRfcResult(rdhClassificationMaint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void CreateAlphaQTYChar(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception {
/* 313 */     String str1 = "MK_" + paramString2 + "_" + paramString3 + "_" + paramString4 + "_QTY";
/*     */     
/* 315 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(paramString6, str1, "NUM", 4, "", "", "", "", "S", "", "", "", str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     addRfcName(chwCharMaintain);
/* 331 */     chwCharMaintain.execute();
/* 332 */     addRfcResult(chwCharMaintain);
/*     */ 
/*     */ 
/*     */     
/* 336 */     addOutput("CreateAlphaQTYChar obj_id" + paramString1);
/* 337 */     String str2 = getSuffix(paramString1, paramString2, paramString4, "Q");
/*     */ 
/*     */ 
/*     */     
/* 341 */     String str3 = "MK_" + paramString2 + "_" + CommonUtils.getFirstSubString(paramString1, 4) + "_ALPH_" + str2;
/* 342 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(paramString6, str3, str3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     addRfcName(chwClassMaintain);
/*     */     
/* 349 */     chwClassMaintain.execute();
/* 350 */     addRfcResult(chwClassMaintain);
/*     */     
/* 352 */     RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(paramString1, str3, "300", "H", paramString6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     addRfcName(rdhClassificationMaint);
/* 359 */     rdhClassificationMaint.execute();
/* 360 */     addRfcResult(rdhClassificationMaint);
/*     */ 
/*     */     
/* 363 */     ChwAssignCharToClass chwAssignCharToClass = new ChwAssignCharToClass(paramString6, str3, str1, "", paramString5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 370 */     addRfcName(chwAssignCharToClass);
/*     */     
/* 372 */     chwAssignCharToClass.execute();
/* 373 */     addRfcResult(chwAssignCharToClass);
/* 374 */     addOutput("ChwAssignCharToClass.getRfcrc()11=" + chwAssignCharToClass.getRfcrc());
/* 375 */     if (chwAssignCharToClass.getRfcrc() == 2) {
/* 376 */       int i = Integer.parseInt(str2);
/* 377 */       addOutput("isuffix=" + i);
/* 378 */       i++;
/* 379 */       addOutput("isuffix=" + i);
/* 380 */       str2 = CommonUtils.frontCompWithZore(i, 3);
/*     */       
/* 382 */       addOutput("suffix=" + str2);
/* 383 */       String str = "MK_" + paramString2 + "_" + CommonUtils.getFirstSubString(paramString1, 4) + "_ALPH_" + str2;
/* 384 */       ChwClassMaintain chwClassMaintain1 = new ChwClassMaintain(paramString6, str, str);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 389 */       addRfcName(chwClassMaintain1);
/* 390 */       chwClassMaintain1.execute();
/* 391 */       addRfcResult(chwClassMaintain1);
/*     */       
/* 393 */       RdhClassificationMaint rdhClassificationMaint1 = new RdhClassificationMaint(paramString1, str, "300", "H", paramString6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 399 */       addRfcName(rdhClassificationMaint1);
/* 400 */       rdhClassificationMaint1.execute();
/* 401 */       addRfcResult(rdhClassificationMaint1);
/*     */       
/* 403 */       ChwAssignCharToClass chwAssignCharToClass1 = new ChwAssignCharToClass(paramString6, str, str1, "", paramString5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 410 */       addRfcName(chwAssignCharToClass1);
/* 411 */       chwAssignCharToClass1.execute();
/* 412 */       addRfcResult(chwAssignCharToClass1);
/*     */     } 
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
/*     */   private String getSuffix(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 474 */     String str = "001";
/* 475 */     int i = 0;
/* 476 */     ChwBapiClassCharRead chwBapiClassCharRead = new ChwBapiClassCharRead(paramString1, paramString3, paramString2, paramString4);
/* 477 */     addRfcName(chwBapiClassCharRead);
/*     */     try {
/* 479 */       chwBapiClassCharRead.execute();
/* 480 */       addRfcResult(chwBapiClassCharRead);
/* 481 */       addOutput("bapiClassCharRead target_indc = " + paramString2 + "; and char_type=" + paramString4 + "; rc=" + chwBapiClassCharRead.getRfcrc());
/* 482 */       String str1 = chwBapiClassCharRead.getSuffix();
/* 483 */       addOutput("bapiClassCharRead bapi_suffix=" + str1);
/* 484 */       str = CommonUtils.frontCompWithZore(Integer.parseInt(chwBapiClassCharRead.getSuffix()), 3);
/* 485 */       addOutput("bapiClassCharRead suffix=" + str);
/* 486 */     } catch (Exception exception) {
/* 487 */       addRfcResult(chwBapiClassCharRead);
/* 488 */       String str1 = chwBapiClassCharRead.getError_text();
/* 489 */       if (str1.contains("The feature code is not associated") || str1
/* 490 */         .contains("The QTY characteristic is not associated")) {
/* 491 */         addOutput("change the RFC code to 4");
/* 492 */         i = 4;
/* 493 */         addOutput("bapiClassCharRead exception target_indc = " + paramString2 + "; and char_type=" + paramString4 + "; rc=" + i);
/*     */       } else {
/* 495 */         i = chwBapiClassCharRead.getRfcrc();
/* 496 */         addOutput("bapiClassCharRead exception target_indc = " + paramString2 + "; and char_type=" + paramString4 + "; rc=" + i);
/*     */       } 
/*     */     } 
/* 499 */     if (i == 4) {
/*     */       
/* 501 */       String str1 = "MK_" + paramString2 + "_" + CommonUtils.getFirstSubString(paramString1, 4) + "_ALPH_";
/* 502 */       ChwGetMaxClass300Suffix chwGetMaxClass300Suffix = new ChwGetMaxClass300Suffix(paramString1, str1);
/* 503 */       addRfcName(chwGetMaxClass300Suffix);
/*     */       try {
/* 505 */         chwGetMaxClass300Suffix.execute();
/* 506 */         addRfcResult(chwGetMaxClass300Suffix);
/* 507 */         addOutput("ChwGetMaxClass300Suffix target_indc = " + paramString2 + "; and char_type=" + paramString4 + "; rc=" + chwGetMaxClass300Suffix.getRfcrc());
/* 508 */         str = CommonUtils.frontCompWithZore(chwGetMaxClass300Suffix.getMax_suffix(), 3);
/* 509 */         if ("000".equals(str)) {
/* 510 */           str = "001";
/*     */         }
/* 512 */         addOutput("ChwGetMaxClass300Suffix target_indc = " + paramString2 + "; and char_type=" + paramString4 + "; max_suffix =" + str);
/* 513 */       } catch (Exception exception) {
/* 514 */         addRfcResult(chwGetMaxClass300Suffix);
/* 515 */         str = "001";
/* 516 */         addOutput("ChwGetMaxClass300Suffix exception target_indc = " + paramString2 + "; and char_type=" + paramString4 + "; max_suffix =" + str + "; rc=" + chwGetMaxClass300Suffix.getRfcrc());
/*     */       } 
/*     */     } 
/* 519 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwClsfCharCreate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */