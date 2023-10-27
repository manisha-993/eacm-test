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
/*     */ public class ChwModelConvertUpg
/*     */   extends RfcCallerBase
/*     */ {
/*     */   private MODEL chwMODEL;
/*     */   private MODELCONVERT chwModelConvert;
/*     */   private Connection rdhConnection;
/*     */   private Connection odsConnection;
/*  19 */   private String MODEL_MACHTYPE = "SELECT DISTINCT SUBSTR(t2.ATTRIBUTEVALUE,1,3) AS MODEL, trim(t3.ATTRIBUTEVALUE) AS INVNAME FROM OPICM.flag F  INNER JOIN opicm.flag t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='MACHTYPEATR' AND T1.ATTRIBUTEVALUE = ? AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MODELATR' AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='INVNAME' AND t3.NLSID =1 AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t1.ENTITYID AND F1.ENTITYTYPE =t1.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELTIERPABRSTATUS')  AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=?  WITH ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChwModelConvertUpg(MODEL paramMODEL, MODELCONVERT paramMODELCONVERT, Connection paramConnection1, Connection paramConnection2) {
/*  31 */     this.chwMODEL = paramMODEL;
/*  32 */     this.chwModelConvert = paramMODELCONVERT;
/*  33 */     this.rdhConnection = paramConnection1;
/*  34 */     this.odsConnection = paramConnection2;
/*     */   }
/*     */   
/*     */   public void execute() throws Exception {
/*  38 */     String str1 = "";
/*  39 */     String str2 = this.chwModelConvert.getTOMACHTYPE() + "UPG";
/*  40 */     String str3 = this.chwModelConvert.getFROMMACHTYPE() + this.chwModelConvert.getFROMMODEL() + this.chwModelConvert.getTOMACHTYPE() + this.chwModelConvert.getTOMODEL();
/*     */ 
/*     */     
/*  43 */     ChwMatmCreate chwMatmCreate = new ChwMatmCreate(this.chwMODEL, "ZMAT", str2, str3);
/*  44 */     runRfcCaller(chwMatmCreate);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  49 */     Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(this.chwMODEL, "ZMAT", str2, str3, this.odsConnection);
/*  50 */     addDebug("Calling Chw001ClfCreate");
/*     */     try {
/*  52 */       chw001ClfCreate.execute();
/*  53 */       addMsg(chw001ClfCreate.getRptSb());
/*  54 */     } catch (Exception exception) {
/*  55 */       addMsg(chw001ClfCreate.getRptSb());
/*  56 */       throw exception;
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
/*  75 */     ChwCharMaintain chwCharMaintain1 = new ChwCharMaintain(str3, "MK_T_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD", "CHAR", 6, str1, str1, str1, str1, "S", str1, str1, "X", this.chwModelConvert.getTOMACHTYPE() + " Model Characteristic");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     List<Map<String, String>> list = getFromModelToModel(this.MODEL_MACHTYPE, this.chwModelConvert.getTOMACHTYPE(), this.chwMODEL.getPDHDOMAIN());
/*  81 */     String str4 = "";
/*  82 */     String str5 = "";
/*  83 */     for (Map<String, String> map : list) {
/*     */       
/*  85 */       str4 = (String)map.get("MODEL");
/*  86 */       str5 = CommonUtils.getFirstSubString((String)map.get("INVNAME"), 25) + " " + (String)map.get("MODEL");
/*  87 */       chwCharMaintain1.addValue(str4, str5);
/*     */     } 
/*  89 */     runRfcCaller(chwCharMaintain1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     ChwClassMaintain chwClassMaintain = new ChwClassMaintain(str3, "MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD", "MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD");
/*     */ 
/*     */     
/*  99 */     chwClassMaintain.addCharacteristic("MK_T_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD");
/* 100 */     chwClassMaintain.execute();
/* 101 */     addRfcResult(chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     RdhClassificationMaint rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD", "300", "H", str3);
/*     */ 
/*     */ 
/*     */     
/* 110 */     runRfcCaller(rdhClassificationMaint1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     ChwCharMaintain chwCharMaintain2 = new ChwCharMaintain(str3, "MK_D_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD_CONV", "CHAR", 9, str1, str1, str1, str1, "-", str1, str1, "X", this.chwModelConvert.getTOMACHTYPE() + " Features Conversion");
/*     */     
/* 130 */     addRfcName(chwCharMaintain2);
/*     */ 
/*     */     
/* 133 */     str4 = this.chwModelConvert.getFROMMODEL().trim() + "_" + this.chwModelConvert.getTOMODEL().trim();
/*     */     
/* 135 */     String str6 = "From " + this.chwModelConvert.getTOMODELTYPE().trim() + " Model " + this.chwModelConvert.getFROMMODEL().trim() + " to " + this.chwModelConvert.getTOMODEL().trim();
/* 136 */     chwCharMaintain2.addValue(str4, str6);
/* 137 */     runRfcCaller(chwCharMaintain2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     chwClassMaintain = new ChwClassMaintain(str3, "MK_D_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD_CONV", "MK_D_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD_CONV");
/*     */ 
/*     */     
/* 148 */     chwClassMaintain.addCharacteristic("MK_D_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD_CONV");
/* 149 */     runRfcCaller(chwClassMaintain);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     rdhClassificationMaint1 = new RdhClassificationMaint(str2, "MK_D_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD_CONV", "300", "H", str3);
/*     */ 
/*     */ 
/*     */     
/* 158 */     runRfcCaller(rdhClassificationMaint1);
/*     */ 
/*     */ 
/*     */     
/* 162 */     RdhClassificationMaint rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_REFERENCE", "300", "H", str3);
/* 163 */     runRfcCaller(rdhClassificationMaint2);
/*     */ 
/*     */     
/* 166 */     rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_T_VAO_NEW", "300", "H", str3);
/* 167 */     runRfcCaller(rdhClassificationMaint2);
/*     */ 
/*     */     
/* 170 */     rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_D_VAO_NEW", "300", "H", str3);
/* 171 */     runRfcCaller(rdhClassificationMaint2);
/*     */ 
/*     */     
/* 174 */     rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_FC_EXCH", "300", "H", str3);
/* 175 */     runRfcCaller(rdhClassificationMaint2);
/*     */ 
/*     */     
/* 178 */     rdhClassificationMaint2 = new RdhClassificationMaint(str2, "MK_FC_CONV", "300", "H", str3);
/* 179 */     runRfcCaller(rdhClassificationMaint2);
/*     */ 
/*     */     
/* 182 */     for (Map<String, String> map : list) {
/* 183 */       String str7 = this.chwModelConvert.getTOMACHTYPE() + "UPG";
/*     */       
/* 185 */       String str8 = "SC_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD_" + (String)map.get("MODEL");
/* 186 */       String str9 = "5";
/*     */       
/* 188 */       String str10 = "SC_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD_" + (String)map.get("MODEL");
/*     */       
/* 190 */       ChwDepdMaintain chwDepdMaintain = new ChwDepdMaintain(str3, str8, str9, str10);
/*     */       
/* 192 */       String str11 = "$PARENT.MK_T_" + this.chwModelConvert.getTOMACHTYPE() + "_MOD='" + (String)map.get("MODEL") + "'";
/* 193 */       chwDepdMaintain.addSourceLineCondition(str11);
/* 194 */       runRfcCaller(chwDepdMaintain);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     ChwConpMaintain chwConpMaintain = new ChwConpMaintain(str2, "INITIAL", "SD01", "2", this.chwModelConvert.getTOMACHTYPE() + "UPGUI", str3);
/*     */ 
/*     */     
/* 206 */     chwConpMaintain.addConfigDependency("E2E", str1);
/*     */     
/* 208 */     chwConpMaintain.addConfigDependency("PR_" + this.chwModelConvert.getTOMACHTYPE() + "_SET_MODEL", str1);
/*     */     
/* 210 */     chwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", str1);
/*     */     
/* 212 */     chwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", str1);
/*     */     
/* 214 */     chwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", str1);
/* 215 */     runRfcCaller(chwConpMaintain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Map<String, String>> getFromModelToModel(String paramString1, String paramString2, String paramString3) throws SQLException {
/* 222 */     ArrayList<HashMap<Object, Object>> arrayList = new ArrayList();
/* 223 */     String[] arrayOfString = new String[2];
/* 224 */     arrayOfString[0] = paramString2;
/* 225 */     arrayOfString[1] = paramString3;
/* 226 */     String str = CommonUtils.getPreparedSQL(paramString1, (Object[])arrayOfString);
/* 227 */     addDebug("querySql=" + str);
/*     */     
/* 229 */     PreparedStatement preparedStatement = this.rdhConnection.prepareStatement(paramString1);
/* 230 */     preparedStatement.setString(1, paramString2);
/* 231 */     preparedStatement.setString(2, paramString3);
/* 232 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 233 */     while (resultSet.next()) {
/* 234 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 235 */       hashMap.put("MODEL", resultSet.getString("MODEL"));
/* 236 */       hashMap.put("INVNAME", resultSet.getString("INVNAME"));
/* 237 */       arrayList.add(hashMap);
/*     */     } 
/* 239 */     return (List)arrayList;
/*     */   }
/*     */   public static void main(String[] paramArrayOfString) {
/* 242 */     MODEL mODEL = new MODEL();
/* 243 */     mODEL.setMACHTYPE("UTC");
/* 244 */     mODEL.setMODEL("MOD_NAME");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwModelConvertUpg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */