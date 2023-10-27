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
/*     */ public class ChwMachTypeMtc
/*     */   extends RfcCallerBase
/*     */ {
/*     */   private MODEL chwModel;
/*     */   private Connection rdhConnection;
/*     */   private Connection odsConnection;
/*  18 */   private String FROMMODELTOMODEL = "SELECT trim(FROMMACHTYPE)||trim(FROMMODEL)||'_'||trim(TOMACHTYPE)||trim(TOMODEL) AS FromModelToModel FROM ( SELECT DISTINCT t1.ATTRIBUTEVALUE AS FROMMACHTYPE, t3.ATTRIBUTEVALUE AS FROMMODEL, t2.ATTRIBUTEVALUE AS TOMACHTYPE, t4.ATTRIBUTEVALUE AS TOMODEL FROM OPICM.flag F INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE =? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='FROMMODEL' AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t4 ON f.ENTITYID =t4.ENTITYID AND f.ENTITYTYPE =t4.ENTITYTYPE AND t4.ATTRIBUTECODE ='TOMODEL' AND T4.VALTO > CURRENT  TIMESTAMP AND T4.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')   AND T1.ATTRIBUTEVALUE !=T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=?  ) AS TT WITH UR";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChwMachTypeMtc(MODEL paramMODEL, Connection paramConnection1, Connection paramConnection2) {
/*  33 */     this.chwModel = paramMODEL;
/*  34 */     this.rdhConnection = paramConnection1;
/*  35 */     this.odsConnection = paramConnection2;
/*     */   }
/*     */   
/*     */   public void execute() throws Exception {
/*  39 */     String str1 = "";
/*  40 */     String str2 = this.chwModel.getMACHTYPE() + "MTC";
/*  41 */     String str3 = this.chwModel.getMACHTYPE() + this.chwModel.getMODEL() + "MTC";
/*     */     
/*  43 */     ChwMatmCreate chwMatmCreate = new ChwMatmCreate(this.chwModel, "ZMAT", this.chwModel.getMACHTYPE() + "MTC", str3);
/*  44 */     addRfcName(chwMatmCreate);
/*  45 */     chwMatmCreate.execute();
/*  46 */     addRfcResult(chwMatmCreate);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  51 */     Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(this.chwModel, "ZMAT", this.chwModel.getMACHTYPE() + "MTC", str3, this.odsConnection);
/*  52 */     addDebug("Calling Chw001ClfCreate");
/*     */     try {
/*  54 */       chw001ClfCreate.execute();
/*  55 */       addMsg(chw001ClfCreate.getRptSb());
/*  56 */     } catch (Exception exception) {
/*  57 */       addMsg(chw001ClfCreate.getRptSb());
/*  58 */       throw exception;
/*     */     } 
/*     */ 
/*     */     
/*  62 */     RdhClassificationMaint rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_REFERENCE", "300", "H", str3);
/*  63 */     addRfcName(rdhClassificationMaint1);
/*  64 */     rdhClassificationMaint1.execute();
/*  65 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */     
/*  68 */     rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_T_VAO_NEW", "300", "H", str3);
/*  69 */     addRfcName(rdhClassificationMaint1);
/*  70 */     rdhClassificationMaint1.execute();
/*  71 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */     
/*  74 */     rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_D_VAO_NEW", "300", "H", str3);
/*  75 */     addRfcName(rdhClassificationMaint1);
/*  76 */     rdhClassificationMaint1.execute();
/*  77 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */     
/*  80 */     rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_FC_EXCH", "300", "H", str3);
/*  81 */     addRfcName(rdhClassificationMaint1);
/*  82 */     rdhClassificationMaint1.execute();
/*  83 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */     
/*  86 */     rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_FC_CONV", "300", "H", str3);
/*  87 */     addRfcName(rdhClassificationMaint1);
/*  88 */     rdhClassificationMaint1.execute();
/*  89 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     ChwCharMaintain chwCharMaintain1 = new ChwCharMaintain(str3, "MK_T_" + this.chwModel.getMACHTYPE() + "_MOD", "CHAR", 6, str1, str1, str1, str1, "S", str1, str1, "X", this.chwModel.getMACHTYPE() + " Model Characteristic");
/*     */     
/* 109 */     addRfcName(chwCharMaintain1);
/*     */     
/* 111 */     String str4 = "";
/* 112 */     List<LANGUAGE> list = this.chwModel.getLANGUAGELIST();
/* 113 */     for (LANGUAGE lANGUAGE : list) {
/* 114 */       String str = lANGUAGE.getNLSID();
/* 115 */       if ("1".equals(str)) {
/* 116 */         str4 = lANGUAGE.getINVNAME();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 121 */     String str5 = CommonUtils.getFirstSubString(str4, 25) + " " + this.chwModel.getMODEL();
/* 122 */     chwCharMaintain1.addValue(this.chwModel.getMODEL(), str5);
/* 123 */     chwCharMaintain1.execute();
/* 124 */     addRfcResult(chwCharMaintain1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(str3, "MK_" + this.chwModel.getMACHTYPE() + "_MOD", "MK_" + this.chwModel.getMACHTYPE() + "_MOD");
/*     */     
/* 132 */     addRfcName(chwClassMaintain);
/*     */     
/* 134 */     chwClassMaintain.addCharacteristic("MK_T_" + this.chwModel.getMACHTYPE() + "_MOD");
/* 135 */     chwClassMaintain.execute();
/* 136 */     addRfcResult(chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     RdhClassificationMaint rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_" + this.chwModel.getMACHTYPE() + "_MOD", "300", "H", str3);
/*     */ 
/*     */ 
/*     */     
/* 146 */     addRfcName(rdhClassificationMaint2);
/* 147 */     rdhClassificationMaint2.execute();
/* 148 */     addRfcResult(rdhClassificationMaint2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     ChwCharMaintain chwCharMaintain2 = new ChwCharMaintain(str3, "MK_" + this.chwModel.getMACHTYPE() + "_MTC", "CHAR", 15, str1, str1, str1, str1, "-", str1, str1, "X", "Machine Type Conversions " + this.chwModel.getMACHTYPE());
/*     */     
/* 167 */     addRfcName(chwCharMaintain2);
/*     */     
/* 169 */     String str6 = this.chwModel.getMACHTYPE();
/*     */     
/* 171 */     List<String> list1 = getFromModelToModel(this.FROMMODELTOMODEL, this.chwModel.getMACHTYPE(), this.chwModel.getPDHDOMAIN());
/* 172 */     for (String str7 : list1) {
/* 173 */       String str8 = str7.replace("_", " to ");
/* 174 */       chwCharMaintain2.addValue(str7, str8);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     chwCharMaintain2.execute();
/* 184 */     addRfcResult(chwCharMaintain2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     chwClassMaintain = new ChwClassMaintain(str3, "MK_" + this.chwModel.getMACHTYPE() + "_MTC", "MK_" + this.chwModel.getMACHTYPE() + "_MTC");
/*     */     
/* 192 */     addRfcName(chwClassMaintain);
/*     */     
/* 194 */     chwClassMaintain.addCharacteristic("MK_" + this.chwModel.getMACHTYPE() + "_MTC");
/* 195 */     chwClassMaintain.execute();
/* 196 */     addRfcResult(chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_" + this.chwModel.getMACHTYPE() + "_MTC", "300", "H", str3);
/*     */ 
/*     */ 
/*     */     
/* 205 */     addRfcName(rdhClassificationMaint2);
/* 206 */     rdhClassificationMaint2.execute();
/* 207 */     addRfcResult(rdhClassificationMaint2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     ChwConpMaintain chwConpMaintain = new ChwConpMaintain(str2, "INITIAL", "SD01", "2", this.chwModel.getMACHTYPE() + "MTCUI", str3);
/*     */     
/* 220 */     addRfcName(chwConpMaintain);
/*     */ 
/*     */     
/* 223 */     chwConpMaintain.addConfigDependency("E2E", str1);
/*     */     
/* 225 */     chwConpMaintain.addConfigDependency("PR_" + this.chwModel.getMACHTYPE() + "_SET_MODEL", str1);
/*     */     
/* 227 */     chwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", str1);
/*     */     
/* 229 */     chwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", str1);
/*     */     
/* 231 */     chwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", str1);
/*     */     
/* 233 */     chwConpMaintain.execute();
/* 234 */     addRfcResult(chwConpMaintain);
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
/*     */   private List<String> getFromModelToModel(String paramString1, String paramString2, String paramString3) throws SQLException {
/* 306 */     ArrayList<String> arrayList = new ArrayList();
/* 307 */     String[] arrayOfString = new String[2];
/* 308 */     arrayOfString[0] = paramString2;
/* 309 */     arrayOfString[1] = paramString3;
/* 310 */     String str = CommonUtils.getPreparedSQL(paramString1, (Object[])arrayOfString);
/* 311 */     addDebug("querySql=" + str);
/*     */     
/* 313 */     PreparedStatement preparedStatement = this.rdhConnection.prepareStatement(paramString1);
/* 314 */     preparedStatement.setString(1, paramString2);
/* 315 */     preparedStatement.setString(2, paramString3);
/* 316 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 317 */     while (resultSet.next()) {
/* 318 */       arrayList.add(resultSet.getString("FromModelToModel"));
/*     */     }
/* 320 */     return arrayList;
/*     */   }
/*     */   public static void main(String[] paramArrayOfString) {
/* 323 */     MODEL mODEL = new MODEL();
/* 324 */     mODEL.setMACHTYPE("UTC");
/* 325 */     mODEL.setMODEL("MOD_NAME");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwMachTypeMtc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */