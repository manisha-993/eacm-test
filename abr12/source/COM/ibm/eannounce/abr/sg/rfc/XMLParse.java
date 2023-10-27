/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLParse
/*     */ {
/*     */   private static <T> T getObjFromDoc(Document paramDocument, Class<T> paramClass) throws JAXBException {
/*     */     try {
/*  28 */       JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { paramClass });
/*  29 */       Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/*  30 */       return (T)unmarshaller.unmarshal(paramDocument);
/*  31 */     } catch (JAXBException jAXBException) {
/*     */       
/*  33 */       throw jAXBException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/*  38 */     String str = "";
/*     */     
/*     */     try {
/*  41 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  42 */       documentBuilderFactory.setNamespaceAware(false);
/*  43 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*     */ 
/*     */ 
/*     */       
/*  47 */       SVCMOD sVCMOD = null;
/*  48 */       MODEL mODEL = getObjectFromXml(loadModelXml(null), MODEL.class);
/*  49 */       MODELCONVERT mODELCONVERT = getObjectFromXml(loadModelXml(null), MODELCONVERT.class);
/*  50 */       System.out.println(mODEL);
/*  51 */       MTCYMDMFCMaint mTCYMDMFCMaint = new MTCYMDMFCMaint(mODELCONVERT);
/*     */ 
/*     */       
/*  54 */       mTCYMDMFCMaint.execute();
/*  55 */       System.exit(0);
/*  56 */       if (str != null)
/*     */       {
/*     */         try {
/*  59 */           RdhMatmCreate rdhMatmCreate = new RdhMatmCreate(sVCMOD, null);
/*  60 */           System.out.println(rdhMatmCreate.getEarliestAnnDate(sVCMOD));
/*  61 */           rdhMatmCreate.execute();
/*  62 */           if (rdhMatmCreate.getRfcrc() == 0);
/*     */ 
/*     */ 
/*     */           
/*  66 */           String str1 = sVCMOD.getMACHTYPE() + sVCMOD.getMODEL();
/*  67 */           String str2 = "MG_COMMON";
/*  68 */           String str3 = "001";
/*  69 */           RdhClassificationMaint rdhClassificationMaint = new RdhClassificationMaint(str1, str2, str3, str1);
/*     */ 
/*     */           
/*  72 */           String str4 = "MG_PRODUCTTYPE";
/*  73 */           String str5 = getTableMapingDate(str4, sVCMOD);
/*  74 */           if (str5 != null || !"No characteristic".equals(str5)) {
/*  75 */             rdhClassificationMaint.addCharacteristic(str4, str5);
/*     */           }
/*  77 */           rdhClassificationMaint.execute();
/*  78 */           if (rdhClassificationMaint.getRfcrc() == 0);
/*     */ 
/*     */           
/*  81 */           str2 = "MM_CUSTOM_SERVICES";
/*  82 */           rdhClassificationMaint = new RdhClassificationMaint(str1, str2, str3, str1);
/*  83 */           str4 = "MM_CUSTOM_TYPE";
/*  84 */           str5 = getTableMapingDate(str4, sVCMOD);
/*  85 */           if (str5 != null || !"No characteristic".equals(str5)) {
/*  86 */             rdhClassificationMaint.addCharacteristic(str4, str5);
/*     */           }
/*  88 */           str4 = "MM_CUSTOM_COSTING";
/*  89 */           str5 = getTableMapingDate(str4, sVCMOD);
/*  90 */           if (str5 != null || !"No characteristic".equals(str5)) {
/*  91 */             rdhClassificationMaint.addCharacteristic(str4, str5);
/*     */           }
/*  93 */           str4 = "MM_PROFIT_CENTER";
/*  94 */           str5 = getTableMapingDate(str4, sVCMOD);
/*  95 */           if (str5 != null || !"No characteristic".equals(str5)) {
/*  96 */             rdhClassificationMaint.addCharacteristic(str4, str5);
/*     */           }
/*  98 */           str4 = "MM_TAX_CATEGORY";
/*  99 */           str5 = getTableMapingDate(str4, sVCMOD);
/* 100 */           if (str5 != null || !"No characteristic".equals(str5)) {
/* 101 */             rdhClassificationMaint.addCharacteristic(str4, str5);
/*     */           }
/* 103 */           rdhClassificationMaint.execute();
/*     */           
/* 105 */           if (rdhClassificationMaint.getRfcrc() == 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 117 */           str2 = "MM_FIELDS";
/* 118 */           rdhClassificationMaint = new RdhClassificationMaint(str1, str2, str3, str1);
/* 119 */           if ("Yes".equals(sVCMOD.getSOPRELEVANT())) {
/* 120 */             str4 = "MM_SOP_IND";
/* 121 */             rdhClassificationMaint.addCharacteristic(str4, "X");
/*     */           } 
/* 123 */           str4 = "MM_TASK_TYPE";
/* 124 */           rdhClassificationMaint.addCharacteristic(str4, sVCMOD.getSOPTASKTYPE());
/* 125 */           str4 = "MM_OPPORTUNITY_CODE";
/* 126 */           rdhClassificationMaint.addCharacteristic(str4, sVCMOD.getWWOCCODE());
/* 127 */           rdhClassificationMaint.execute();
/*     */           
/* 129 */           if (rdhClassificationMaint.getRfcrc() == 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 139 */           if (sVCMOD.hasProds()) {
/* 140 */             RdhTssMatChar rdhTssMatChar = new RdhTssMatChar(sVCMOD);
/*     */             
/* 142 */             rdhTssMatChar.execute();
/*     */             
/* 144 */             if (rdhTssMatChar.getRfcrc() == 0);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 150 */           UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_TSS_IERP", sVCMOD.getMACHTYPE() + sVCMOD.getMODEL());
/* 151 */           updateParkStatus.execute();
/*     */ 
/*     */           
/* 154 */           if (updateParkStatus.getRfcrc() == 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 161 */           RdhTssFcProd rdhTssFcProd = new RdhTssFcProd(sVCMOD);
/* 162 */           if (rdhTssFcProd.canRun()) {
/* 163 */             rdhTssFcProd.execute();
/*     */           }
/*     */           
/* 166 */           if (rdhTssFcProd.getRfcrc() == 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 174 */         catch (Exception exception) {
/*     */           
/* 176 */           exception.printStackTrace();
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else
/*     */       {
/*     */         
/*     */         return;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 190 */     catch (Throwable throwable) {
/* 191 */       throwable.printStackTrace();
/*     */     } 
/*     */   }
/*     */   private static String getTableMapingDate(String paramString, SVCMOD paramSVCMOD) {
/* 195 */     String str = null;
/* 196 */     if ("MG_PRODUCTTYPE".equals(paramString)) {
/* 197 */       if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/* 198 */         if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 199 */           if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operations Based".equals(paramSVCMOD.getGROUP())) {
/* 200 */             str = "S3";
/*     */           }
/* 202 */         } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 203 */           if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/* 204 */             str = "S6";
/* 205 */           } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/* 206 */             str = "S7";
/* 207 */           } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/* 208 */             str = "S5";
/* 209 */           } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/* 210 */             str = "S4";
/* 211 */           } else if ("ICA/NEC".equals(paramSVCMOD.getGROUP())) {
/* 212 */             str = "S2";
/*     */           }
/*     */         
/* 215 */         } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && 
/* 216 */           "Non-Federated".equals(paramSVCMOD.getGROUP())) {
/* 217 */           str = "S2";
/*     */         }
/*     */       
/* 220 */       } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && 
/* 221 */         "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 222 */         str = "S8";
/*     */       }
/*     */     
/* 225 */     } else if ("MM_CUSTOM_TYPE".equals(paramString)) {
/*     */       
/* 227 */       if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/* 228 */         if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 229 */           if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operations Based".equals(paramSVCMOD.getGROUP())) {
/* 230 */             str = "OCI";
/*     */           }
/* 232 */         } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 233 */           if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/* 234 */             str = "PC";
/* 235 */           } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/* 236 */             str = "IC";
/* 237 */           } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/* 238 */             str = "TE";
/* 239 */           } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/* 240 */             str = "SA";
/* 241 */           } else if ("ICA/NEC".equals(paramSVCMOD.getGROUP())) {
/* 242 */             str = "No characteristic";
/*     */           }
/*     */         
/* 245 */         } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && 
/* 246 */           "Non-Federated".equals(paramSVCMOD.getGROUP())) {
/* 247 */           str = "SPI";
/*     */         }
/*     */       
/* 250 */       } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && 
/* 251 */         "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 252 */         str = "IPSC";
/*     */       }
/*     */     
/*     */     }
/* 256 */     else if ("MM_CUSTOM_COSTING".equals(paramString)) {
/*     */       
/* 258 */       if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/* 259 */         if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 260 */           if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operations Based".equals(paramSVCMOD.getGROUP())) {
/* 261 */             str = "WBS";
/*     */           }
/* 263 */         } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 264 */           if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/* 265 */             str = "WBS";
/* 266 */           } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/* 267 */             str = "WBS";
/* 268 */           } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/* 269 */             str = "WBS";
/* 270 */           } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/* 271 */             str = "WBS";
/* 272 */           } else if ("ICA/NEC".equals(paramSVCMOD.getGROUP())) {
/* 273 */             str = "No characteristic";
/*     */           }
/*     */         
/* 276 */         } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && 
/* 277 */           "Non-Federated".equals(paramSVCMOD.getGROUP())) {
/* 278 */           str = "WPS";
/*     */         }
/*     */       
/* 281 */       } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && 
/* 282 */         "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 283 */         str = "No characteristic";
/*     */       }
/*     */     
/*     */     }
/* 287 */     else if ("MM_PROFIT_CENTER".equals(paramString)) {
/*     */       
/* 289 */       if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/* 290 */         if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 291 */           if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operations Based".equals(paramSVCMOD.getGROUP())) {
/* 292 */             str = "D";
/*     */           }
/* 294 */         } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 295 */           if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/* 296 */             str = "C";
/* 297 */           } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/* 298 */             str = "C";
/* 299 */           } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/* 300 */             str = "C";
/* 301 */           } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/* 302 */             str = "C";
/* 303 */           } else if ("ICA/NEC".equals(paramSVCMOD.getGROUP())) {
/* 304 */             str = "No characteristic";
/*     */           }
/*     */         
/* 307 */         } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && 
/* 308 */           "Non-Federated".equals(paramSVCMOD.getGROUP())) {
/* 309 */           str = "C";
/*     */         }
/*     */       
/* 312 */       } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && 
/* 313 */         "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 314 */         str = "No characteristic";
/*     */       }
/*     */     
/*     */     }
/* 318 */     else if ("MM_TAX_CATEGORY".equals(paramString)) {
/*     */       
/* 320 */       if ("Service".equals(paramSVCMOD.getCATEGORY())) {
/* 321 */         if ("Custom".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 322 */           if ("Project Based".equals(paramSVCMOD.getGROUP()) || "Operations Based".equals(paramSVCMOD.getGROUP())) {
/* 323 */             str = "D";
/*     */           }
/* 325 */         } else if ("Facility".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 326 */           if ("Penalty".equals(paramSVCMOD.getGROUP())) {
/* 327 */             str = "C";
/* 328 */           } else if ("Incident".equals(paramSVCMOD.getGROUP())) {
/* 329 */             str = "C";
/* 330 */           } else if ("Travel".equals(paramSVCMOD.getGROUP())) {
/* 331 */             str = "C";
/* 332 */           } else if ("Activity".equals(paramSVCMOD.getGROUP())) {
/* 333 */             str = "C";
/* 334 */           } else if ("ICA/NEC".equals(paramSVCMOD.getGROUP())) {
/* 335 */             str = "No characteristic";
/*     */           }
/*     */         
/* 338 */         } else if ("Productized Services".equals(paramSVCMOD.getSUBCATEGORY()) && 
/* 339 */           "Non-Federated".equals(paramSVCMOD.getGROUP())) {
/* 340 */           str = "C";
/*     */         }
/*     */       
/* 343 */       } else if ("IP".equals(paramSVCMOD.getCATEGORY()) && 
/* 344 */         "SC".equals(paramSVCMOD.getSUBCATEGORY())) {
/* 345 */         str = "No characteristic";
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 351 */     return str;
/*     */   }
/*     */   public static SVCMOD getSvcmodFromXml(String paramString) {
/* 354 */     SVCMOD sVCMOD = null;
/*     */     try {
/* 356 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 357 */       documentBuilderFactory.setNamespaceAware(false);
/* 358 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 359 */       Document document = documentBuilder.parse(new ByteArrayInputStream(paramString.getBytes()));
/* 360 */       sVCMOD = getObjFromDoc(document, SVCMOD.class);
/*     */     }
/* 362 */     catch (Throwable throwable) {
/* 363 */       throwable.printStackTrace();
/*     */     } 
/*     */     
/* 366 */     return sVCMOD;
/*     */   }
/*     */   
/*     */   public static <T> T getObjectFromXml(String paramString, Class<T> paramClass) {
/* 370 */     T t = null;
/*     */     try {
/* 372 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 373 */       documentBuilderFactory.setNamespaceAware(false);
/* 374 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 375 */       Document document = documentBuilder.parse(new ByteArrayInputStream(paramString.getBytes("UTF-8")));
/* 376 */       t = getObjFromDoc(document, (Class)paramClass);
/*     */     }
/* 378 */     catch (Throwable throwable) {
/* 379 */       throwable.printStackTrace();
/*     */     } 
/*     */     
/* 382 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String loadXml(String paramString) {
/* 387 */     paramString = "C:\\Users\\JianBoXu\\Desktop\\eacm\\5724D75.xml";
/*     */     
/* 389 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/*     */     try {
/* 392 */       BufferedReader bufferedReader = new BufferedReader(new FileReader(paramString));
/*     */       
/* 394 */       String str = null;
/* 395 */       while ((str = bufferedReader.readLine()) != null) {
/* 396 */         stringBuffer.append(str);
/*     */       }
/*     */     }
/* 399 */     catch (FileNotFoundException fileNotFoundException) {
/*     */       
/* 401 */       fileNotFoundException.printStackTrace();
/* 402 */     } catch (IOException iOException) {
/*     */       
/* 404 */       iOException.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 408 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String loadModelXml(String paramString) {
/* 413 */     paramString = "C:\\Users\\058241672\\Desktop\\backup\\covert.txt";
/*     */ 
/*     */     
/* 416 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/*     */     try {
/* 419 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(paramString)));
/*     */       
/* 421 */       String str = null;
/* 422 */       while ((str = bufferedReader.readLine()) != null) {
/* 423 */         stringBuffer.append(str);
/*     */       }
/*     */     }
/* 426 */     catch (FileNotFoundException fileNotFoundException) {
/*     */       
/* 428 */       fileNotFoundException.printStackTrace();
/* 429 */     } catch (IOException iOException) {
/*     */       
/* 431 */       iOException.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 435 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\XMLParse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */