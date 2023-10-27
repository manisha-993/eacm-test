/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.ZYTMDMOTHWARRMOD;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.ZYTMDMOTHWARRTMF;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.ZYTMDMOTHWARRUPD;
/*     */ import COM.ibm.eannounce.abr.util.RFCConfig;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ChwYMdmOthWarranty
/*     */   extends RdhBase
/*     */ {
/*     */   @SerializedName("ZYTMDMOTHWARRUPD")
/*     */   private List<ZYTMDMOTHWARRUPD> ZYTMDMOTHWARRUPD_LIST;
/*     */   @SerializedName("ZYTMDMOTHWARRMOD")
/*     */   private List<ZYTMDMOTHWARRMOD> ZYTMDMOTHWARRMOD_LIST;
/*     */   
/*     */   public ChwYMdmOthWarranty(WARR paramWARR) {
/*  28 */     super(paramWARR.getWARRID(), "Z_YMDMOTH_WARRANTY".toLowerCase(), null);
/*  29 */     this.pims_identity = "H";
/*  30 */     this.MATERIAL_TYPE = "WARR";
/*     */ 
/*     */     
/*  33 */     ZYTMDMOTHWARRUPD zYTMDMOTHWARRUPD = new ZYTMDMOTHWARRUPD();
/*  34 */     zYTMDMOTHWARRUPD.setZWARRID(paramWARR.getWARRID());
/*  35 */     String str1 = paramWARR.getWARRPRIOD();
/*  36 */     if ("N/A".equals(str1) || "NA".equals(str1)) {
/*  37 */       zYTMDMOTHWARRUPD.setZWARRPRIOD("0");
/*     */ 
/*     */     
/*     */     }
/*  41 */     else if (CommonUtils.isNumber(str1)) {
/*  42 */       zYTMDMOTHWARRUPD.setZWARRPRIOD(str1);
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/*  47 */     zYTMDMOTHWARRUPD.setZWARRPRIODUM("MONTH");
/*  48 */     zYTMDMOTHWARRUPD.setZWARRTYPE(paramWARR.getWARRTYPE().toUpperCase());
/*  49 */     zYTMDMOTHWARRUPD.setZCATEGORY(paramWARR.getBHWARRCATEGORY());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     String str2 = "";
/*  58 */     if ("International Warranty Service (IWS)".equalsIgnoreCase(paramWARR.getWARRTYPE()) && (paramWARR
/*  59 */       .getCOVRHR() == null || "".equals(paramWARR.getCOVRHR())) && (paramWARR
/*  60 */       .getRESPPROF() == null || "".equals(paramWARR.getRESPPROF()))) {
/*     */       
/*  62 */       str2 = "";
/*     */     }
/*  64 */     else if (paramWARR.getCOVRHR() != null) {
/*  65 */       str2 = paramWARR.getCOVRHR().toUpperCase();
/*     */     } 
/*     */ 
/*     */     
/*  69 */     zYTMDMOTHWARRUPD.setZCOVRPRIOD(str2);
/*     */     
/*  71 */     String str3 = CommonUtils.getFirstSubString(paramWARR.getWARRDATERULEKEY(), 12);
/*  72 */     zYTMDMOTHWARRUPD.setZWARRSDATE(str3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     String str4 = "";
/*  86 */     if ("International Warranty Service (IWS)".equalsIgnoreCase(paramWARR.getWARRTYPE()) && (paramWARR
/*  87 */       .getCOVRHR() == null || "".equals(paramWARR.getCOVRHR())) && (paramWARR
/*  88 */       .getRESPPROF() == null || "".equals(paramWARR.getRESPPROF()))) {
/*     */       
/*  90 */       str4 = "";
/*     */     } else {
/*  92 */       int i = -1;
/*  93 */       if (paramWARR.getRESPPROF() != null) {
/*  94 */         String str = paramWARR.getRESPPROF();
/*  95 */         i = str.indexOf(" ");
/*  96 */         if (i <= 0) {
/*  97 */           str4 = str;
/*     */         } else {
/*  99 */           str4 = CommonUtils.getFirstSubString(str, i);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     zYTMDMOTHWARRUPD.setZRESPTIME(str4);
/*     */     
/* 106 */     zYTMDMOTHWARRUPD.setZNLSID("E");
/*     */     
/* 108 */     if (paramWARR.getLANGUAGELIST() != null)
/*     */     {
/* 110 */       for (WARR.LANGUAGEELEMENT_WARR lANGUAGEELEMENT_WARR : paramWARR.getLANGUAGELIST()) {
/*     */         
/* 112 */         if ("1".equals(lANGUAGEELEMENT_WARR.getNLSID())) {
/*     */ 
/*     */           
/* 115 */           zYTMDMOTHWARRUPD.setZPRODDESC(CommonUtils.getFirstSubString(lANGUAGEELEMENT_WARR.getINVNAME(), 40));
/*     */           
/* 117 */           zYTMDMOTHWARRUPD.setZMKTGNAME(CommonUtils.getFirstSubString(lANGUAGEELEMENT_WARR.getMKTGNAME(), 132));
/*     */           
/* 119 */           zYTMDMOTHWARRUPD.setZWARRDESC(CommonUtils.getFirstSubString(lANGUAGEELEMENT_WARR.getWARRDESC(), 1000));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 125 */     zYTMDMOTHWARRUPD.setZALTWTY1(CommonUtils.getFirstSubString(paramWARR.getOEMESAPRTSLBR(), 10));
/*     */     
/* 127 */     zYTMDMOTHWARRUPD.setZALTWTY2(CommonUtils.getFirstSubString(paramWARR.getOEMESAPRTSONY(), 10));
/*     */     
/* 129 */     zYTMDMOTHWARRUPD.setZTIER_MAIN(CommonUtils.getFirstSubString(paramWARR.getTIERMAIN(), 50));
/*     */     
/* 131 */     zYTMDMOTHWARRUPD.setZPRED_SUP(CommonUtils.getFirstSubString(paramWARR.getPREDSUPP(), 50));
/*     */     
/* 133 */     zYTMDMOTHWARRUPD.setZENH_RESP(CommonUtils.getFirstSubString(paramWARR.getENHCOMRES(), 50));
/*     */     
/* 135 */     zYTMDMOTHWARRUPD.setZREM_CLOAD(CommonUtils.getFirstSubString(paramWARR.getREMCODLOAD(), 50));
/*     */     
/* 137 */     zYTMDMOTHWARRUPD.setZTECH_ADV(CommonUtils.getFirstSubString(paramWARR.getTIERWSU(), 50));
/*     */     
/* 139 */     zYTMDMOTHWARRUPD.setZTECHADVISOR(CommonUtils.getFirstSubString(paramWARR.getTECHADV(), 50));
/*     */     
/* 141 */     zYTMDMOTHWARRUPD.setZSVC1(CommonUtils.getFirstSubString(paramWARR.getSVC1(), 50));
/* 142 */     zYTMDMOTHWARRUPD.setZSVC2(CommonUtils.getFirstSubString(paramWARR.getSVC2(), 50));
/* 143 */     zYTMDMOTHWARRUPD.setZSVC3(CommonUtils.getFirstSubString(paramWARR.getSVC3(), 50));
/* 144 */     zYTMDMOTHWARRUPD.setZSVC4(CommonUtils.getFirstSubString(paramWARR.getSVC4(), 50));
/* 145 */     this.ZYTMDMOTHWARRUPD_LIST.add(zYTMDMOTHWARRUPD);
/*     */   } @SerializedName("ZYTMDMOTHWARRTMF")
/*     */   private List<ZYTMDMOTHWARRTMF> ZYTMDMOTHWARRTMF_LIST; @SerializedName("MATERIAL_TYPE")
/*     */   private String MATERIAL_TYPE;
/*     */   public ChwYMdmOthWarranty(MODEL paramMODEL, Connection paramConnection) throws SQLException {
/* 150 */     super(paramMODEL.getMACHTYPE() + paramMODEL.getMODEL(), "Z_YMDMOTH_WARRANTY".toLowerCase(), null);
/* 151 */     this.pims_identity = "H";
/* 152 */     this.MATERIAL_TYPE = "MODEL";
/* 153 */     ZYTMDMOTHWARRMOD zYTMDMOTHWARRMOD = new ZYTMDMOTHWARRMOD();
/* 154 */     boolean bool = existGENAREASELECTION6400(paramMODEL.getMODELENTITYID(), paramConnection);
/* 155 */     for (WARRELEMENTTYPE wARRELEMENTTYPE : paramMODEL.getWARRLIST()) {
/*     */ 
/*     */       
/* 158 */       if ("Yes".equalsIgnoreCase(wARRELEMENTTYPE.getDEFWARR())) {
/*     */         
/* 160 */         if (!"".equals(wARRELEMENTTYPE.getWARRID()) && !bool) {
/* 161 */           zYTMDMOTHWARRMOD = setZYTMDMOTHWARRMOD(paramMODEL, wARRELEMENTTYPE);
/* 162 */           this.ZYTMDMOTHWARRMOD_LIST.add(zYTMDMOTHWARRMOD); continue;
/* 163 */         }  if (!"".equals(wARRELEMENTTYPE.getWARRID()) && bool) {
/* 164 */           this.ZYTMDMOTHWARRMOD_LIST.addAll(setZYTMDMOTHWARRMODs(paramMODEL, wARRELEMENTTYPE, paramConnection));
/*     */         }
/*     */         continue;
/*     */       } 
/* 168 */       if ("WTY0000".equals(wARRELEMENTTYPE.getWARRID())) {
/* 169 */         if (!bool) {
/* 170 */           zYTMDMOTHWARRMOD = setZYTMDMOTHWARRMOD(paramMODEL, wARRELEMENTTYPE);
/* 171 */           this.ZYTMDMOTHWARRMOD_LIST.add(zYTMDMOTHWARRMOD); continue;
/*     */         } 
/* 173 */         this.ZYTMDMOTHWARRMOD_LIST.addAll(setZYTMDMOTHWARRMODs(paramMODEL, wARRELEMENTTYPE, paramConnection)); continue;
/*     */       } 
/* 175 */       if (!"WTY0000".equals(wARRELEMENTTYPE.getWARRID()))
/*     */       {
/* 177 */         for (COUNTRY cOUNTRY : wARRELEMENTTYPE.getCOUNTRYLIST()) {
/*     */           
/* 179 */           ZYTMDMOTHWARRMOD zYTMDMOTHWARRMOD1 = new ZYTMDMOTHWARRMOD();
/* 180 */           zYTMDMOTHWARRMOD1.setZMACHTYP(paramMODEL.getMACHTYPE());
/* 181 */           zYTMDMOTHWARRMOD1.setZMODEL(paramMODEL.getMODEL());
/*     */           
/* 183 */           for (LANGUAGE lANGUAGE : paramMODEL.getLANGUAGELIST()) {
/*     */             
/* 185 */             if ("1".equals(lANGUAGE.getNLSID()))
/*     */             {
/* 187 */               zYTMDMOTHWARRMOD1.setZINVNAME(CommonUtils.getFirstSubString(lANGUAGE.getMKTGNAME(), 40));
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 196 */           String str1 = cOUNTRY.getCOUNTRY_FC();
/* 197 */           String str2 = RFCConfig.getAland(str1);
/* 198 */           if (str2 == null || "".equals(str2)) {
/*     */             continue;
/*     */           }
/* 201 */           zYTMDMOTHWARRMOD1.setZCOUNTRY(str2);
/*     */           
/* 203 */           zYTMDMOTHWARRMOD1.setZWRTYID(wARRELEMENTTYPE.getWARRID());
/*     */ 
/*     */ 
/*     */           
/* 207 */           String str3 = "";
/* 208 */           String str4 = wARRELEMENTTYPE.getPUBFROM();
/* 209 */           if (str4 == null || "".equals(str4)) {
/* 210 */             str3 = "19800101";
/*     */           } else {
/* 212 */             str3 = str4.replaceAll("-", "");
/*     */           } 
/* 214 */           zYTMDMOTHWARRMOD1.setZPUBFROM(str3);
/* 215 */           zYTMDMOTHWARRMOD1.setZWTYDESC(CommonUtils.getFirstSubString(wARRELEMENTTYPE.getWARRDESC(), 40));
/*     */ 
/*     */ 
/*     */           
/* 219 */           String str5 = "";
/* 220 */           String str6 = wARRELEMENTTYPE.getPUBTO();
/* 221 */           if (str6 == null || "".equals(str6)) {
/* 222 */             str5 = "99991231";
/*     */           } else {
/* 224 */             str5 = str6.replaceAll("-", "");
/*     */           } 
/* 226 */           zYTMDMOTHWARRMOD1.setZPUBTO(str5);
/*     */           
/* 228 */           zYTMDMOTHWARRMOD1.setZWARR_FLAG(CommonUtils.getFirstSubString(wARRELEMENTTYPE.getWARRACTION(), 1));
/*     */           
/* 230 */           String str7 = cOUNTRY.getCOUNTRYACTION();
/* 231 */           zYTMDMOTHWARRMOD1.setZCOUNTRY_FLAG(CommonUtils.getFirstSubString(str7, 1));
/* 232 */           this.ZYTMDMOTHWARRMOD_LIST.add(zYTMDMOTHWARRMOD1);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean existGENAREASELECTION6400(String paramString, Connection paramConnection) {
/* 243 */     String str = "SELECT DISTINCT f.ATTRIBUTEVALUE  FROM OPICM.RELATOR r JOIN OPICM.TEXT t ON r.ENTITY2TYPE =t.ENTITYTYPE AND r.ENTITY2ID =t.ENTITYID AND t.ATTRIBUTECODE ='WARRID'  AND t.ATTRIBUTEVALUE ='WTY0000' AND t.VALTO > CURRENT timestamp AND t.EFFTO > CURRENT timestamp JOIN OPICM.FLAG f ON f.ENTITYTYPE =r.ENTITYTYPE AND f.ENTITYID =r.ENTITYID AND f.ATTRIBUTECODE ='GENAREASELECTION' and f.attributevalue='6400' AND f.VALTO > CURRENT timestamp AND f.EFFTO > CURRENT timestamp WHERE r.ENTITYTYPE ='MODELWARR' AND r.ENTITY1ID =?AND r.VALTO > CURRENT timestamp AND r.EFFTO > CURRENT timestamp WITH ur";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 249 */       PreparedStatement preparedStatement = paramConnection.prepareStatement(str);
/*     */       
/* 251 */       preparedStatement.setString(1, paramString);
/* 252 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 253 */       if (resultSet.next()) {
/* 254 */         return true;
/*     */       }
/* 256 */       return false;
/* 257 */     } catch (SQLException sQLException) {
/* 258 */       throw new RuntimeException(sQLException);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ZYTMDMOTHWARRMOD setZYTMDMOTHWARRMOD(MODEL paramMODEL, WARRELEMENTTYPE paramWARRELEMENTTYPE) {
/* 263 */     ZYTMDMOTHWARRMOD zYTMDMOTHWARRMOD = new ZYTMDMOTHWARRMOD();
/* 264 */     zYTMDMOTHWARRMOD.setZMACHTYP(paramMODEL.getMACHTYPE());
/* 265 */     zYTMDMOTHWARRMOD.setZMODEL(paramMODEL.getMODEL());
/*     */     
/* 267 */     for (LANGUAGE lANGUAGE : paramMODEL.getLANGUAGELIST()) {
/*     */       
/* 269 */       if ("1".equals(lANGUAGE.getNLSID()))
/*     */       {
/* 271 */         zYTMDMOTHWARRMOD.setZINVNAME(CommonUtils.getFirstSubString(lANGUAGE.getMKTGNAME(), 40));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 276 */     zYTMDMOTHWARRMOD.setZCOUNTRY("**");
/*     */     
/* 278 */     zYTMDMOTHWARRMOD.setZWRTYID(paramWARRELEMENTTYPE.getWARRID());
/* 279 */     zYTMDMOTHWARRMOD.setZWTYDESC(CommonUtils.getFirstSubString(paramWARRELEMENTTYPE.getWARRDESC(), 40));
/*     */ 
/*     */ 
/*     */     
/* 283 */     String str1 = "";
/* 284 */     String str2 = paramWARRELEMENTTYPE.getPUBFROM();
/* 285 */     if (str2 == null || "".equals(str2)) {
/* 286 */       str1 = "19800101";
/*     */     } else {
/* 288 */       str1 = str2.replaceAll("-", "");
/*     */     } 
/* 290 */     zYTMDMOTHWARRMOD.setZPUBFROM(str1);
/*     */ 
/*     */ 
/*     */     
/* 294 */     String str3 = "";
/* 295 */     String str4 = paramWARRELEMENTTYPE.getPUBTO();
/* 296 */     if (str4 == null || "".equals(str4)) {
/* 297 */       str3 = "99991231";
/*     */     } else {
/* 299 */       str3 = str4.replaceAll("-", "");
/*     */     } 
/* 301 */     zYTMDMOTHWARRMOD.setZPUBTO(str3);
/*     */     
/* 303 */     zYTMDMOTHWARRMOD.setZWARR_FLAG(CommonUtils.getFirstSubString(paramWARRELEMENTTYPE.getWARRACTION(), 1));
/*     */ 
/*     */     
/* 306 */     if ("Yes".equalsIgnoreCase(paramWARRELEMENTTYPE.getDEFWARR())) {
/* 307 */       zYTMDMOTHWARRMOD.setZCOUNTRY_FLAG("");
/*     */     } else {
/* 309 */       COUNTRY cOUNTRY = paramWARRELEMENTTYPE.getCOUNTRYLIST().get(0);
/* 310 */       String str = cOUNTRY.getCOUNTRYACTION();
/* 311 */       zYTMDMOTHWARRMOD.setZCOUNTRY_FLAG(CommonUtils.getFirstSubString(str, 1));
/*     */     } 
/*     */ 
/*     */     
/* 315 */     return zYTMDMOTHWARRMOD;
/*     */   }
/*     */   
/*     */   protected List<ZYTMDMOTHWARRMOD> setZYTMDMOTHWARRMODs(MODEL paramMODEL, WARRELEMENTTYPE paramWARRELEMENTTYPE, Connection paramConnection) throws SQLException {
/* 319 */     ArrayList<ZYTMDMOTHWARRMOD> arrayList = new ArrayList();
/* 320 */     String str = "select GENAREANAME_FC,GENAREACODE from price.generalarea  where GENAREACODE in (select GENAREACODE from price.generalarea) and NLSID = 1 and ISACTIVE = 1 WITH UR";
/* 321 */     HashSet hashSet = new HashSet();
/* 322 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 323 */     Statement statement = paramConnection.createStatement();
/* 324 */     ResultSet resultSet = statement.executeQuery(str);
/* 325 */     while (resultSet.next()) {
/* 326 */       hashtable.put(resultSet.getString("GENAREANAME_FC").trim(), resultSet.getString("GENAREACODE").trim());
/*     */     }
/* 328 */     statement.close();
/* 329 */     resultSet.close();
/* 330 */     for (COUNTRY cOUNTRY : paramWARRELEMENTTYPE.getCOUNTRYLIST()) {
/* 331 */       ZYTMDMOTHWARRMOD zYTMDMOTHWARRMOD = new ZYTMDMOTHWARRMOD();
/* 332 */       zYTMDMOTHWARRMOD.setZMACHTYP(paramMODEL.getMACHTYPE());
/* 333 */       zYTMDMOTHWARRMOD.setZMODEL(paramMODEL.getMODEL());
/*     */       
/* 335 */       for (LANGUAGE lANGUAGE : paramMODEL.getLANGUAGELIST()) {
/* 336 */         if ("1".equals(lANGUAGE.getNLSID())) {
/* 337 */           zYTMDMOTHWARRMOD.setZINVNAME(CommonUtils.getFirstSubString(lANGUAGE.getMKTGNAME(), 40));
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 342 */       zYTMDMOTHWARRMOD.setZCOUNTRY((String)hashtable.get(cOUNTRY.getCOUNTRY_FC()));
/*     */       
/* 344 */       zYTMDMOTHWARRMOD.setZWRTYID(paramWARRELEMENTTYPE.getWARRID());
/* 345 */       zYTMDMOTHWARRMOD.setZWTYDESC(CommonUtils.getFirstSubString(paramWARRELEMENTTYPE.getWARRDESC(), 40));
/*     */ 
/*     */ 
/*     */       
/* 349 */       String str1 = "";
/* 350 */       String str2 = paramWARRELEMENTTYPE.getPUBFROM();
/* 351 */       if (str2 == null || "".equals(str2)) {
/* 352 */         str1 = "19800101";
/*     */       } else {
/* 354 */         str1 = str2.replaceAll("-", "");
/*     */       } 
/* 356 */       zYTMDMOTHWARRMOD.setZPUBFROM(str1);
/*     */ 
/*     */ 
/*     */       
/* 360 */       String str3 = "";
/* 361 */       String str4 = paramWARRELEMENTTYPE.getPUBTO();
/* 362 */       if (str4 == null || "".equals(str4)) {
/* 363 */         str3 = "99991231";
/*     */       } else {
/* 365 */         str3 = str4.replaceAll("-", "");
/*     */       } 
/* 367 */       zYTMDMOTHWARRMOD.setZPUBTO(str3);
/*     */       
/* 369 */       zYTMDMOTHWARRMOD.setZWARR_FLAG(CommonUtils.getFirstSubString(paramWARRELEMENTTYPE.getWARRACTION(), 1));
/*     */ 
/*     */       
/* 372 */       if ("Yes".equalsIgnoreCase(paramWARRELEMENTTYPE.getDEFWARR())) {
/* 373 */         zYTMDMOTHWARRMOD.setZCOUNTRY_FLAG("");
/*     */       } else {
/* 375 */         COUNTRY cOUNTRY1 = paramWARRELEMENTTYPE.getCOUNTRYLIST().get(0);
/* 376 */         String str5 = cOUNTRY1.getCOUNTRYACTION();
/* 377 */         zYTMDMOTHWARRMOD.setZCOUNTRY_FLAG(CommonUtils.getFirstSubString(str5, 1));
/*     */       } 
/*     */       
/* 380 */       arrayList.add(zYTMDMOTHWARRMOD);
/*     */     } 
/*     */     
/* 383 */     return arrayList;
/*     */   }
/*     */   
/*     */   public ChwYMdmOthWarranty(TMF_UPDATE paramTMF_UPDATE, String paramString) {
/* 387 */     super(paramTMF_UPDATE.getMACHTYPE() + paramTMF_UPDATE.getFEATURECODE(), "Z_YMDMOTH_WARRANTY".toLowerCase(), null);
/* 388 */     this.pims_identity = "H";
/* 389 */     this.MATERIAL_TYPE = "TMF";
/*     */     
/* 391 */     List<WARRELEMENT_TMF> list = paramTMF_UPDATE.getWARRLIST();
/* 392 */     if (list.isEmpty()) {
/* 393 */       ZYTMDMOTHWARRTMF zYTMDMOTHWARRTMF = new ZYTMDMOTHWARRTMF();
/*     */       
/* 395 */       zYTMDMOTHWARRTMF.setZMACHTYP(paramTMF_UPDATE.getMACHTYPE());
/*     */       
/* 397 */       zYTMDMOTHWARRTMF.setZFEATURECODE(paramTMF_UPDATE.getFEATURECODE());
/*     */       
/* 399 */       for (LANGUAGEELEMENT_TMF lANGUAGEELEMENT_TMF : paramTMF_UPDATE.getLANGUAGELIST()) {
/*     */         
/* 401 */         if ("1".equals(lANGUAGEELEMENT_TMF.getNLSID()))
/*     */         {
/* 403 */           zYTMDMOTHWARRTMF.setZINVNAME(CommonUtils.getFirstSubString(lANGUAGEELEMENT_TMF.getMKTGNAME(), 40));
/*     */         }
/*     */       } 
/* 406 */       zYTMDMOTHWARRTMF.setZCOUNTRY("");
/* 407 */       zYTMDMOTHWARRTMF.setZWRTYID("");
/* 408 */       zYTMDMOTHWARRTMF.setZWTYDESC("");
/* 409 */       zYTMDMOTHWARRTMF.setZPUBFROM("00000000");
/* 410 */       zYTMDMOTHWARRTMF.setZPUBTO("00000000");
/* 411 */       zYTMDMOTHWARRTMF.setZWARR_FLAG("");
/* 412 */       zYTMDMOTHWARRTMF.setZCOUNTRY_FLAG("");
/* 413 */       this.ZYTMDMOTHWARRTMF_LIST.add(zYTMDMOTHWARRTMF);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 418 */       for (WARRELEMENT_TMF wARRELEMENT_TMF : list) {
/*     */         
/* 420 */         String str = wARRELEMENT_TMF.getDEFWARR();
/* 421 */         if ("Yes".equalsIgnoreCase(str)) {
/*     */           
/* 423 */           if (!"".equals(wARRELEMENT_TMF.getWARRID())) {
/* 424 */             ZYTMDMOTHWARRTMF zYTMDMOTHWARRTMF = new ZYTMDMOTHWARRTMF();
/*     */             
/* 426 */             zYTMDMOTHWARRTMF.setZMACHTYP(paramTMF_UPDATE.getMACHTYPE());
/*     */             
/* 428 */             zYTMDMOTHWARRTMF.setZFEATURECODE(paramTMF_UPDATE.getFEATURECODE());
/*     */             
/* 430 */             zYTMDMOTHWARRTMF.setZINVNAME(CommonUtils.getFirstSubString(paramString, 40));
/* 431 */             zYTMDMOTHWARRTMF.setZCOUNTRY("**");
/*     */             
/* 433 */             zYTMDMOTHWARRTMF.setZWRTYID(wARRELEMENT_TMF.getWARRID());
/* 434 */             zYTMDMOTHWARRTMF.setZWTYDESC(CommonUtils.getFirstSubString(wARRELEMENT_TMF.getWARRDESC(), 40));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 440 */             String str1 = "";
/* 441 */             String str2 = wARRELEMENT_TMF.getPUBFROM();
/* 442 */             if (str2 == null || "".equals(str2)) {
/* 443 */               str1 = "19800101";
/*     */             } else {
/* 445 */               str1 = str2.replaceAll("-", "");
/*     */             } 
/* 447 */             zYTMDMOTHWARRTMF.setZPUBFROM(str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 453 */             String str3 = "";
/* 454 */             String str4 = wARRELEMENT_TMF.getPUBTO();
/* 455 */             if (str4 == null || "".equals(str4)) {
/* 456 */               str3 = "99991231";
/*     */             } else {
/* 458 */               str3 = str4.replaceAll("-", "");
/*     */             } 
/* 460 */             zYTMDMOTHWARRTMF.setZPUBTO(str3);
/*     */             
/* 462 */             zYTMDMOTHWARRTMF.setZWARR_FLAG(CommonUtils.getFirstSubString(wARRELEMENT_TMF.getWARRACTION(), 1));
/* 463 */             zYTMDMOTHWARRTMF.setZCOUNTRY_FLAG("");
/* 464 */             this.ZYTMDMOTHWARRTMF_LIST.add(zYTMDMOTHWARRTMF);
/*     */           } 
/*     */           continue;
/*     */         } 
/* 468 */         if ("WTY0000".equalsIgnoreCase(wARRELEMENT_TMF.getWARRID())) {
/* 469 */           COUNTRYELEMENT_TMF cOUNTRYELEMENT_TMF = wARRELEMENT_TMF.getCOUNTRYLIST().get(0);
/* 470 */           setZYTMDMOTHWARRTMF(paramTMF_UPDATE, wARRELEMENT_TMF, cOUNTRYELEMENT_TMF, paramString); continue;
/*     */         } 
/* 472 */         for (COUNTRYELEMENT_TMF cOUNTRYELEMENT_TMF : wARRELEMENT_TMF.getCOUNTRYLIST()) {
/* 473 */           setZYTMDMOTHWARRTMF(paramTMF_UPDATE, wARRELEMENT_TMF, cOUNTRYELEMENT_TMF, paramString);
/*     */         }
/*     */       } 
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
/*     */   private void setZYTMDMOTHWARRTMF(TMF_UPDATE paramTMF_UPDATE, WARRELEMENT_TMF paramWARRELEMENT_TMF, COUNTRYELEMENT_TMF paramCOUNTRYELEMENT_TMF, String paramString) {
/* 488 */     ZYTMDMOTHWARRTMF zYTMDMOTHWARRTMF = new ZYTMDMOTHWARRTMF();
/*     */     
/* 490 */     zYTMDMOTHWARRTMF.setZMACHTYP(paramTMF_UPDATE.getMACHTYPE());
/*     */     
/* 492 */     zYTMDMOTHWARRTMF.setZFEATURECODE(paramTMF_UPDATE.getFEATURECODE());
/*     */     
/* 494 */     zYTMDMOTHWARRTMF.setZINVNAME(CommonUtils.getFirstSubString(paramString, 40));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 501 */     String str1 = "";
/* 502 */     if ("WTY0000".equalsIgnoreCase(paramWARRELEMENT_TMF.getWARRID()) || paramCOUNTRYELEMENT_TMF == null) {
/* 503 */       str1 = "**";
/* 504 */       zYTMDMOTHWARRTMF.setZCOUNTRY(str1);
/*     */     } else {
/* 506 */       str1 = paramCOUNTRYELEMENT_TMF.getCOUNTRY_FC();
/* 507 */       String str = RFCConfig.getAland(str1);
/* 508 */       if (str == null || "".equals(str)) {
/*     */         return;
/*     */       }
/* 511 */       zYTMDMOTHWARRTMF.setZCOUNTRY(str);
/*     */     } 
/*     */ 
/*     */     
/* 515 */     zYTMDMOTHWARRTMF.setZWRTYID(paramWARRELEMENT_TMF.getWARRID());
/* 516 */     zYTMDMOTHWARRTMF.setZWTYDESC(CommonUtils.getFirstSubString(paramWARRELEMENT_TMF.getWARRDESC(), 40));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 522 */     String str2 = "";
/* 523 */     String str3 = paramWARRELEMENT_TMF.getPUBFROM();
/* 524 */     if (str3 == null || "".equals(str3)) {
/* 525 */       str2 = "19800101";
/*     */     } else {
/* 527 */       str2 = str3.replaceAll("-", "");
/*     */     } 
/* 529 */     zYTMDMOTHWARRTMF.setZPUBFROM(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 535 */     String str4 = "";
/* 536 */     String str5 = paramWARRELEMENT_TMF.getPUBTO();
/* 537 */     if (str5 == null || "".equals(str5)) {
/* 538 */       str4 = "99991231";
/*     */     } else {
/* 540 */       str4 = str5.replaceAll("-", "");
/*     */     } 
/* 542 */     zYTMDMOTHWARRTMF.setZPUBTO(str4);
/*     */     
/* 544 */     zYTMDMOTHWARRTMF.setZWARR_FLAG(CommonUtils.getFirstSubString(paramWARRELEMENT_TMF.getWARRACTION(), 1));
/* 545 */     zYTMDMOTHWARRTMF.setZCOUNTRY_FLAG(CommonUtils.getFirstSubString(paramCOUNTRYELEMENT_TMF.getCOUNTRYACTION(), 1));
/*     */     
/* 547 */     this.ZYTMDMOTHWARRTMF_LIST.add(zYTMDMOTHWARRTMF);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/* 552 */     this.ZYTMDMOTHWARRUPD_LIST = new ArrayList<>();
/* 553 */     this.ZYTMDMOTHWARRMOD_LIST = new ArrayList<>();
/* 554 */     this.ZYTMDMOTHWARRTMF_LIST = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isReadyToExecute() {
/* 559 */     return true;
/*     */   }
/*     */   
/*     */   public List<ZYTMDMOTHWARRUPD> getZYTMDMOTHWARRUPD_LIST() {
/* 563 */     return this.ZYTMDMOTHWARRUPD_LIST;
/*     */   }
/*     */   
/*     */   public void setZYTMDMOTHWARRUPD_LIST(List<ZYTMDMOTHWARRUPD> paramList) {
/* 567 */     this.ZYTMDMOTHWARRUPD_LIST = paramList;
/*     */   }
/*     */   
/*     */   public List<ZYTMDMOTHWARRMOD> getZYTMDMOTHWARRMOD_LIST() {
/* 571 */     return this.ZYTMDMOTHWARRMOD_LIST;
/*     */   }
/*     */   
/*     */   public void setZYTMDMOTHWARRMOD_LIST(List<ZYTMDMOTHWARRMOD> paramList) {
/* 575 */     this.ZYTMDMOTHWARRMOD_LIST = paramList;
/*     */   }
/*     */   
/*     */   public List<ZYTMDMOTHWARRTMF> getZYTMDMOTHWARRTMF_LIST() {
/* 579 */     return this.ZYTMDMOTHWARRTMF_LIST;
/*     */   }
/*     */   
/*     */   public void setZYTMDMOTHWARRTMF_LIST(List<ZYTMDMOTHWARRTMF> paramList) {
/* 583 */     this.ZYTMDMOTHWARRTMF_LIST = paramList;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwYMdmOthWarranty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */