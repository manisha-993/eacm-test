/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ChwModelConvertMtc
/*     */   extends RfcCallerBase
/*     */ {
/*     */   private MODEL chwMODEL;
/*     */   private MODELCONVERT chwModelConvert;
/*     */   private Connection rdhConnection;
/*     */   private Connection odsConnection;
/*  19 */   private String MODEL_MACHTYPE = "SELECT DISTINCT SUBSTR(t2.ATTRIBUTEVALUE,1,3) AS MODEL, trim(t3.ATTRIBUTEVALUE) AS INVNAME FROM OPICM.flag F  INNER JOIN opicm.flag t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='MACHTYPEATR' AND T1.ATTRIBUTEVALUE = ? AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MODELATR' AND t2.NLSID=1 AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='INVNAME' AND t3.NLSID =1 AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t1.ENTITYID AND F1.ENTITYTYPE =t1.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELTIERPABRSTATUS')  AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=?  WITH ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChwModelConvertMtc(MODEL paramMODEL, MODELCONVERT paramMODELCONVERT, Connection paramConnection1, Connection paramConnection2) {
/*  31 */     this.chwMODEL = paramMODEL;
/*  32 */     this.chwModelConvert = paramMODELCONVERT;
/*  33 */     this.rdhConnection = paramConnection1;
/*  34 */     this.odsConnection = paramConnection2;
/*     */   }
/*     */   
/*     */   public void execute() throws Exception {
/*  38 */     String str1 = "";
/*  39 */     String str2 = this.chwModelConvert.getTOMACHTYPE() + "MTC";
/*  40 */     String str3 = this.chwModelConvert.getFROMMACHTYPE() + this.chwModelConvert.getFROMMODEL() + this.chwModelConvert.getTOMACHTYPE() + this.chwModelConvert.getTOMODEL();
/*     */     
/*  42 */     ChwMatmCreate chwMatmCreate = new ChwMatmCreate(this.chwMODEL, "ZMAT", str2, str3);
/*  43 */     addRfcName(chwMatmCreate);
/*  44 */     chwMatmCreate.execute();
/*  45 */     addRfcResult(chwMatmCreate);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(this.chwMODEL, "ZMAT", str2, str3, this.odsConnection);
/*  51 */     addDebug("Calling Chw001ClfCreate");
/*     */     try {
/*  53 */       chw001ClfCreate.execute();
/*  54 */       addMsg(chw001ClfCreate.getRptSb());
/*  55 */     } catch (Exception exception) {
/*  56 */       addMsg(chw001ClfCreate.getRptSb());
/*  57 */       throw exception;
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
/*  76 */     ChwCharMaintain chwCharMaintain1 = new ChwCharMaintain(str3, "MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MTC", "CHAR", 15, str1, str1, str1, str1, "-", str1, str1, "X", "Machine Type Conversions " + this.chwModelConvert.getTOMACHTYPE());
/*     */     
/*  78 */     addRfcName(chwCharMaintain1);
/*     */ 
/*     */     
/*  81 */     String str4 = this.chwModelConvert.getFROMMACHTYPE().trim() + this.chwModelConvert.getFROMMODEL().trim() + "_" + this.chwModelConvert.getTOMACHTYPE().trim() + this.chwModelConvert.getTOMODEL().trim();
/*  82 */     String str5 = this.chwModelConvert.getFROMMACHTYPE().trim() + this.chwModelConvert.getFROMMODEL().trim() + " to " + this.chwModelConvert.getTOMACHTYPE().trim() + this.chwModelConvert.getTOMODEL().trim();
/*  83 */     chwCharMaintain1.addValue(str4, str5);
/*  84 */     chwCharMaintain1.execute();
/*  85 */     addRfcResult(chwCharMaintain1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(str3, "MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MTC", "MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MTC");
/*     */     
/*  93 */     addRfcName(chwClassMaintain);
/*     */     
/*  95 */     chwClassMaintain.addCharacteristic("MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MTC");
/*  96 */     chwClassMaintain.execute();
/*  97 */     addRfcResult(chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     RdhClassificationMaint rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MTC", "300", "H", str3);
/*     */ 
/*     */ 
/*     */     
/* 107 */     addRfcName(rdhClassificationMaint1);
/* 108 */     rdhClassificationMaint1.execute();
/* 109 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     ChwCharMaintain chwCharMaintain2 = new ChwCharMaintain(str3, "MK_T_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD", "CHAR", 6, str1, str1, str1, str1, "S", str1, str1, "X", this.chwModelConvert.getTOMACHTYPE() + " Model Characteristic");
/*     */     
/* 129 */     addRfcName(chwCharMaintain2);
/*     */ 
/*     */ 
/*     */     
/* 133 */     List<Map<String, String>> list = getFromModelToModel(this.MODEL_MACHTYPE, this.chwModelConvert.getTOMACHTYPE(), this.chwMODEL.getPDHDOMAIN());
/* 134 */     String str6 = "";
/* 135 */     for (Map<String, String> map : list) {
/*     */       
/* 137 */       str4 = (String)map.get("MODEL");
/*     */       
/* 139 */       str6 = CommonUtils.getFirstSubString((String)map.get("INVNAME"), 25) + " " + (String)map.get("MODEL");
/* 140 */       chwCharMaintain2.addValue(str4, str6);
/*     */     } 
/* 142 */     chwCharMaintain2.execute();
/* 143 */     addRfcResult(chwCharMaintain2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     chwClassMaintain = new ChwClassMaintain(str3, "MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD", "MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD");
/*     */     
/* 151 */     addRfcName(chwClassMaintain);
/*     */     
/* 153 */     chwClassMaintain.addCharacteristic("MK_T_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD");
/* 154 */     chwClassMaintain.execute();
/* 155 */     addRfcResult(chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD", "300", "H", str3);
/*     */ 
/*     */ 
/*     */     
/* 164 */     addRfcName(rdhClassificationMaint1);
/* 165 */     rdhClassificationMaint1.execute();
/* 166 */     addRfcResult(rdhClassificationMaint1);
/*     */ 
/*     */ 
/*     */     
/* 170 */     RdhClassificationMaint rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_REFERENCE", "300", "H", str3);
/* 171 */     runRfcCaller(rdhClassificationMaint2);
/*     */ 
/*     */     
/* 174 */     rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_T_VAO_NEW", "300", "H", str3);
/* 175 */     runRfcCaller(rdhClassificationMaint2);
/*     */ 
/*     */     
/* 178 */     rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_D_VAO_NEW", "300", "H", str3);
/* 179 */     runRfcCaller(rdhClassificationMaint2);
/*     */ 
/*     */     
/* 182 */     rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_FC_EXCH", "300", "H", str3);
/* 183 */     runRfcCaller(rdhClassificationMaint2);
/*     */ 
/*     */     
/* 186 */     rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_FC_CONV", "300", "H", str3);
/* 187 */     runRfcCaller(rdhClassificationMaint2);
/*     */ 
/*     */     
/* 190 */     for (Map<String, String> map : list) {
/* 191 */       String str7 = this.chwModelConvert.getTOMACHTYPE() + "UPG";
/*     */       
/* 193 */       String str8 = "SC_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD_" + (String)map.get("MODEL");
/* 194 */       String str9 = "5";
/*     */       
/* 196 */       String str10 = "SC_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD_" + (String)map.get("MODEL");
/*     */       
/* 198 */       ChwDepdMaintain chwDepdMaintain = new ChwDepdMaintain(str3, str8, str9, str10);
/*     */       
/* 200 */       String str11 = "$PARENT.MK_T_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD='" + (String)map.get("MODEL") + "'";
/* 201 */       chwDepdMaintain.addSourceLineCondition(str11);
/* 202 */       runRfcCaller(chwDepdMaintain);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     ChwConpMaintain chwConpMaintain = new ChwConpMaintain(str2, "INITIAL", "SD01", "2", this.chwModelConvert.getTOMACHTYPE() + "MTCUI", str3);
/*     */     
/* 213 */     addRfcName(chwConpMaintain);
/*     */     
/* 215 */     chwConpMaintain.addConfigDependency("E2E", str1);
/*     */     
/* 217 */     chwConpMaintain.addConfigDependency("PR_" + this.chwModelConvert.getTOMACHTYPE() + "_SET_MODEL", str1);
/*     */     
/* 219 */     chwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", str1);
/*     */     
/* 221 */     chwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", str1);
/*     */     
/* 223 */     chwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", str1);
/*     */     
/* 225 */     chwConpMaintain.execute();
/* 226 */     addRfcResult(chwConpMaintain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Map<String, String>> getFromModelToModel(String paramString1, String paramString2, String paramString3) throws SQLException {
/* 233 */     ArrayList<HashMap<Object, Object>> arrayList = new ArrayList();
/* 234 */     String[] arrayOfString = new String[2];
/* 235 */     arrayOfString[0] = paramString2;
/* 236 */     arrayOfString[1] = paramString3;
/* 237 */     String str = CommonUtils.getPreparedSQL(paramString1, (Object[])arrayOfString);
/* 238 */     addDebug("querySql=" + str);
/*     */     
/* 240 */     PreparedStatement preparedStatement = this.rdhConnection.prepareStatement(paramString1);
/* 241 */     preparedStatement.setString(1, paramString2);
/* 242 */     preparedStatement.setString(2, paramString3);
/* 243 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 244 */     while (resultSet.next()) {
/* 245 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 246 */       hashMap.put("MODEL", resultSet.getString("MODEL"));
/* 247 */       hashMap.put("INVNAME", resultSet.getString("INVNAME"));
/* 248 */       arrayList.add(hashMap);
/*     */     } 
/* 250 */     return (List)arrayList;
/*     */   }
/*     */   public static void main(String[] paramArrayOfString) {
/* 253 */     MODEL mODEL = new MODEL();
/* 254 */     mODEL.setMACHTYPE("UTC");
/* 255 */     mODEL.setMODEL("MOD_NAME");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwModelConvertMtc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */