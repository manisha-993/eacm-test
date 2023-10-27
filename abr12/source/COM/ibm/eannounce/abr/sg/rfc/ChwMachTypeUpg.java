/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class ChwMachTypeUpg
/*     */   extends RfcCallerBase
/*     */ {
/*     */   private MODEL chwModel;
/*     */   private String chwProduct;
/*     */   private Connection rdhConnection;
/*     */   private Connection odsConnection;
/*  19 */   private String MODELCONVERTSQL = " SELECT count(*) FROM OPICM.flag F INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='FROMMODEL' AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t4 ON f.ENTITYID =t4.ENTITYID AND f.ENTITYTYPE =t4.ENTITYTYPE AND t4.ATTRIBUTECODE ='TOMODEL' AND T4.VALTO > CURRENT  TIMESTAMP AND T4.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=?  AND T3.ATTRIBUTEVALUE!=T4.ATTRIBUTEVALUE WITH UR";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private String FCTRANSACTIONSQL = " SELECT count(*) FROM OPICM.flag F INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='FROMMODEL' AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t4 ON f.ENTITYID =t4.ENTITYID AND f.ENTITYTYPE =t4.ENTITYTYPE AND t4.ATTRIBUTECODE ='TOMODEL' AND T4.VALTO > CURRENT  TIMESTAMP AND T4.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=?  AND T3.ATTRIBUTEVALUE!=T4.ATTRIBUTEVALUE WITH UR";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private String FROMMODELTOMODEL = "SELECT DISTINCT trim(FROMMODEL)||'_'||trim(TOMODEL) AS FromModelToModel FROM ( SELECT DISTINCT t3.ATTRIBUTEVALUE AS FROMMODEL, t4.ATTRIBUTEVALUE AS TOMODEL FROM OPICM.flag F INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='FROMMODEL' AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t4 ON f.ENTITYID =t4.ENTITYID AND f.ENTITYTYPE =t4.ENTITYTYPE AND t4.ATTRIBUTECODE ='TOMODEL' AND T4.VALTO > CURRENT  TIMESTAMP AND T4.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')   AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=?  AND T3.ATTRIBUTEVALUE!=T4.ATTRIBUTEVALUE UNION all SELECT DISTINCT t3.ATTRIBUTEVALUE AS FROMMODEL, t4.ATTRIBUTEVALUE AS TOMODEL FROM OPICM.flag F INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='FROMMODEL' AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t4 ON f.ENTITYID =t4.ENTITYID AND f.ENTITYTYPE =t4.ENTITYTYPE AND t4.ATTRIBUTECODE ='TOMODEL' AND T4.VALTO > CURRENT  TIMESTAMP AND T4.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS')   AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=?  AND T3.ATTRIBUTEVALUE!=T4.ATTRIBUTEVALUE ) AS TT WITH UR";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChwMachTypeUpg(MODEL paramMODEL, Connection paramConnection1, Connection paramConnection2) {
/*  71 */     this.chwModel = paramMODEL;
/*  72 */     this.rdhConnection = paramConnection1;
/*  73 */     this.odsConnection = paramConnection2;
/*     */   }
/*     */   public void execute() throws Exception {
/*  76 */     String str1 = "";
/*  77 */     String str2 = this.chwModel.getMACHTYPE() + "UPG";
/*  78 */     String str3 = this.chwModel.getMACHTYPE() + this.chwModel.getMODEL() + "UPG";
/*     */     
/*  80 */     ChwMatmCreate chwMatmCreate = new ChwMatmCreate(this.chwModel, "ZMAT", this.chwModel.getMACHTYPE() + "UPG", str3);
/*  81 */     addRfcName(chwMatmCreate);
/*  82 */     chwMatmCreate.execute();
/*  83 */     addRfcResult(chwMatmCreate);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(this.chwModel, "ZMAT", this.chwModel.getMACHTYPE() + "UPG", str3, this.odsConnection);
/*  89 */     addDebug("Calling Chw001ClfCreate");
/*     */     try {
/*  91 */       chw001ClfCreate.execute();
/*  92 */       addMsg(chw001ClfCreate.getRptSb());
/*  93 */     } catch (Exception exception) {
/*  94 */       addMsg(chw001ClfCreate.getRptSb());
/*  95 */       throw exception;
/*     */     } 
/*     */ 
/*     */     
/*  99 */     RdhClassificationMaint rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_REFERENCE", "300", "H", str3);
/* 100 */     addRfcName(rdhClassificationMaint1);
/* 101 */     rdhClassificationMaint1.execute();
/* 102 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */     
/* 105 */     rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_T_VAO_NEW", "300", "H", str3);
/* 106 */     addRfcName(rdhClassificationMaint1);
/* 107 */     rdhClassificationMaint1.execute();
/* 108 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */     
/* 111 */     rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_D_VAO_NEW", "300", "H", str3);
/* 112 */     addRfcName(rdhClassificationMaint1);
/* 113 */     rdhClassificationMaint1.execute();
/* 114 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */     
/* 117 */     rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_FC_EXCH", "300", "H", str3);
/* 118 */     addRfcName(rdhClassificationMaint1);
/* 119 */     rdhClassificationMaint1.execute();
/* 120 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */     
/* 123 */     rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_FC_CONV", "300", "H", str3);
/* 124 */     addRfcName(rdhClassificationMaint1);
/* 125 */     rdhClassificationMaint1.execute();
/* 126 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     ChwCharMaintain chwCharMaintain = new ChwCharMaintain(str3, "MK_T_" + this.chwModel.getMACHTYPE() + "_MOD", "CHAR", 6, str1, str1, str1, str1, "S", str1, str1, "X", this.chwModel.getMACHTYPE() + " Model Characteristic");
/*     */     
/* 146 */     addRfcName(chwCharMaintain);
/*     */ 
/*     */     
/* 149 */     String str4 = "";
/* 150 */     List<LANGUAGE> list = this.chwModel.getLANGUAGELIST();
/* 151 */     for (LANGUAGE lANGUAGE : list) {
/* 152 */       String str = lANGUAGE.getNLSID();
/* 153 */       if ("1".equals(str)) {
/* 154 */         str4 = lANGUAGE.getINVNAME();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 159 */     String str5 = CommonUtils.getFirstSubString(str4, 25) + " " + this.chwModel.getMODEL();
/* 160 */     chwCharMaintain.addValue(this.chwModel.getMODEL(), str5);
/* 161 */     chwCharMaintain.execute();
/* 162 */     addRfcResult(chwCharMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(str3, "MK_" + this.chwModel.getMACHTYPE() + "_MOD", "MK_" + this.chwModel.getMACHTYPE() + "_MOD");
/*     */     
/* 171 */     addRfcName(chwClassMaintain);
/*     */     
/* 173 */     chwClassMaintain.addCharacteristic("MK_T_" + this.chwModel.getMACHTYPE() + "_MOD");
/* 174 */     chwClassMaintain.execute();
/* 175 */     addRfcResult(chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     RdhClassificationMaint rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_" + this.chwModel.getMACHTYPE() + "_MOD", "300", "H", str3);
/*     */ 
/*     */ 
/*     */     
/* 186 */     addRfcName(rdhClassificationMaint2);
/* 187 */     rdhClassificationMaint2.execute();
/* 188 */     addRfcResult(rdhClassificationMaint2);
/*     */ 
/*     */ 
/*     */     
/* 192 */     if (exist(this.MODELCONVERTSQL, this.chwModel.getMACHTYPE(), this.chwModel.getPDHDOMAIN()) || exist(this.FCTRANSACTIONSQL, this.chwModel.getMACHTYPE(), this.chwModel.getPDHDOMAIN())) {
/* 193 */       String str = this.chwModel.getMACHTYPE();
/* 194 */       List<String> list1 = getFromModelToModel(this.FROMMODELTOMODEL, this.chwModel.getMACHTYPE(), this.chwModel.getPDHDOMAIN());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       ChwCharMaintain chwCharMaintain1 = new ChwCharMaintain(str3, "MK_D_" + this.chwModel.getMACHTYPE() + "_MOD_CONV", "CHAR", 9, str1, str1, str1, str1, "-", str1, str1, "X", "Machine Type Conversions " + this.chwModel.getMACHTYPE());
/*     */       
/* 211 */       addRfcName(chwCharMaintain1);
/*     */ 
/*     */ 
/*     */       
/* 215 */       for (String str6 : list1) {
/*     */         
/* 217 */         String str7 = "From " + this.chwModel.getMACHTYPE() + " Model " + CommonUtils.getFirstSubString(str6, 3) + " to " + CommonUtils.getLastSubString(str6, 3);
/* 218 */         chwCharMaintain1.addValue(str6, str7);
/*     */       } 
/* 220 */       chwCharMaintain1.execute();
/* 221 */       addRfcResult(chwCharMaintain1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 228 */       chwClassMaintain = new ChwClassMaintain(str3, "MK_D_" + this.chwModel.getMACHTYPE() + "_MOD_CONV", "MK_D_" + this.chwModel.getMACHTYPE() + "_MOD_CONV");
/*     */       
/* 230 */       addRfcName(chwClassMaintain);
/*     */       
/* 232 */       chwClassMaintain.addCharacteristic("MK_D_" + this.chwModel.getMACHTYPE() + "_MOD_CONV");
/* 233 */       chwClassMaintain.execute();
/* 234 */       addRfcResult(chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 241 */       rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_D_" + this.chwModel.getMACHTYPE() + "_MOD_CONV", "300", "H", str3);
/*     */ 
/*     */ 
/*     */       
/* 245 */       addRfcName(rdhClassificationMaint2);
/* 246 */       rdhClassificationMaint2.execute();
/* 247 */       addRfcResult(rdhClassificationMaint2);
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
/* 259 */     ChwConpMaintain chwConpMaintain = new ChwConpMaintain(str2, "INITIAL", "SD01", "2", this.chwModel.getMACHTYPE() + "UPGUI", str3);
/*     */     
/* 261 */     addRfcName(chwConpMaintain);
/*     */     
/* 263 */     chwConpMaintain.addConfigDependency("E2E", str1);
/*     */     
/* 265 */     chwConpMaintain.addConfigDependency("PR_" + this.chwModel.getMACHTYPE() + "_SET_MODEL", str1);
/*     */     
/* 267 */     chwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", str1);
/*     */     
/* 269 */     chwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", str1);
/*     */     
/* 271 */     chwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", str1);
/*     */     
/* 273 */     chwConpMaintain.execute();
/* 274 */     addRfcResult(chwConpMaintain);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> getFromModelToModel(String paramString1, String paramString2, String paramString3) throws SQLException {
/* 280 */     ArrayList<String> arrayList = new ArrayList();
/* 281 */     String[] arrayOfString = new String[4];
/* 282 */     arrayOfString[0] = paramString2;
/* 283 */     arrayOfString[1] = paramString3;
/* 284 */     arrayOfString[2] = paramString2;
/* 285 */     arrayOfString[3] = paramString3;
/* 286 */     String str = CommonUtils.getPreparedSQL(paramString1, (Object[])arrayOfString);
/* 287 */     addDebug("querySql=" + str);
/*     */     
/* 289 */     PreparedStatement preparedStatement = this.rdhConnection.prepareStatement(paramString1);
/* 290 */     preparedStatement.setString(1, paramString2);
/* 291 */     preparedStatement.setString(2, paramString3);
/* 292 */     preparedStatement.setString(3, paramString2);
/* 293 */     preparedStatement.setString(4, paramString3);
/* 294 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 295 */     while (resultSet.next()) {
/* 296 */       arrayList.add(resultSet.getString("FromModelToModel"));
/*     */     }
/* 298 */     return arrayList;
/*     */   }
/*     */   
/*     */   public boolean exist(String paramString1, String paramString2, String paramString3) {
/* 302 */     boolean bool = false;
/*     */     try {
/* 304 */       Connection connection = this.rdhConnection;
/* 305 */       String[] arrayOfString = new String[2];
/* 306 */       arrayOfString[0] = paramString2;
/* 307 */       arrayOfString[1] = paramString3;
/* 308 */       String str = CommonUtils.getPreparedSQL(paramString1, (Object[])arrayOfString);
/* 309 */       addDebug("querySql=" + str);
/*     */       
/* 311 */       PreparedStatement preparedStatement = connection.prepareStatement(paramString1);
/* 312 */       preparedStatement.setString(1, paramString2);
/* 313 */       preparedStatement.setString(2, paramString3);
/*     */       
/* 315 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 316 */       while (resultSet.next()) {
/* 317 */         int i = resultSet.getInt(1);
/* 318 */         bool = (i > 0) ? true : false;
/*     */       } 
/* 320 */     } catch (SQLException sQLException) {
/* 321 */       sQLException.printStackTrace();
/*     */     } 
/* 323 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 329 */     MODEL mODEL = new MODEL();
/* 330 */     mODEL.setMACHTYPE("UTC");
/* 331 */     mODEL.setMODEL("MOD_NAME");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwMachTypeUpg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */