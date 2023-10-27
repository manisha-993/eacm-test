/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMProd_FEATURE;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMProd_MAKT;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMProd_MLAN;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMProd_MODEL;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMProd_MVKE;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.ChwBulkYMDMProd_TMF;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.CountryPlantTax;
/*     */ import COM.ibm.eannounce.abr.util.RFCConfig;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ChwBulkYMDMProd extends RdhBase {
/*     */   @SerializedName("TBL_MODEL")
/*     */   List<ChwBulkYMDMProd_MODEL> tbl_model;
/*     */   @SerializedName("TBL_MODEL_MAKT")
/*     */   List<ChwBulkYMDMProd_MAKT> tbl_model_makt;
/*     */   @SerializedName("TBL_MODEL_MLAN")
/*     */   List<ChwBulkYMDMProd_MLAN> tbl_mlan;
/*     */   @SerializedName("TBL_MODEL_MVKE")
/*     */   List<ChwBulkYMDMProd_MVKE> tbl_mvke;
/*     */   @SerializedName("TBL_TMF")
/*     */   List<ChwBulkYMDMProd_TMF> tbl_tmf;
/*     */   @SerializedName("TBL_FEATURE")
/*     */   List<ChwBulkYMDMProd_FEATURE> tbl_feature;
/*     */   @SerializedName("TBL_FEATURE_MAKT")
/*     */   List<ChwBulkYMDMProd_MAKT> tbl_feature_makt;
/*     */   @Foo
/*     */   MODEL chwProduct;
/*     */   @SerializedName("MATERIAL_TYPE")
/*     */   String materialType;
/*     */   @Foo
/*     */   String tmfEntityID;
/*     */   @Foo
/*     */   Connection odsConnection;
/*     */   @Foo
/*     */   Connection pdhConnection;
/*     */   
/*     */   public ChwBulkYMDMProd(MODEL paramMODEL, String paramString1, String paramString2, Connection paramConnection1, Connection paramConnection2) throws Exception {
/*  49 */     super(paramMODEL.getMACHTYPE() + paramMODEL.getMODEL(), "Z_YMDMBLK_PROD1".toLowerCase(), null);
/*  50 */     this.pims_identity = "H";
/*  51 */     this.chwProduct = paramMODEL;
/*  52 */     this.materialType = paramString1;
/*  53 */     this.tmfEntityID = paramString2;
/*  54 */     this.odsConnection = paramConnection1;
/*  55 */     this.pdhConnection = paramConnection2;
/*     */ 
/*     */     
/*  58 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setModelEntitytype(this.chwProduct.getMODELENTITYTYPE());
/*  59 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setModelEntityID(this.chwProduct.getMODELENTITYID());
/*  60 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setMachType(this.chwProduct.getMACHTYPE());
/*  61 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setModel(this.chwProduct.getMODEL());
/*  62 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setCategory(this.chwProduct.getCATEGORY());
/*  63 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setSubGroup(this.chwProduct.getSUBGROUP());
/*  64 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setPrftctr(this.chwProduct.getPRFTCTR());
/*  65 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setUnitClass(this.chwProduct.getUNITCLASS());
/*  66 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setPricedInd(this.chwProduct.getPRICEDIND());
/*  67 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setInstall(this.chwProduct.getINSTALL());
/*  68 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setZeroPrice(this.chwProduct.getZEROPRICE());
/*  69 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setUnspsc(this.chwProduct.getUNSPSC());
/*  70 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setMeasuremetric(this.chwProduct.getMEASUREMETRIC());
/*  71 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setProdHiercd(this.chwProduct.getPRODHIERCD());
/*  72 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setAcctAsgnGrp(this.chwProduct.getACCTASGNGRP());
/*  73 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setAmrtztnlngth(this.chwProduct.getAMRTZTNLNGTH());
/*  74 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setZmrtztnstrt(this.chwProduct.getAMRTZTNSTRT());
/*  75 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setProdID(this.chwProduct.getPRODID());
/*  76 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setSomFamily(this.chwProduct.getSOMFAMILY());
/*  77 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setLic(this.chwProduct.getLIC());
/*  78 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setBpcertspecbid(this.chwProduct.getBPCERTSPECBID());
/*  79 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setWwocCode(this.chwProduct.getWWOCCODE());
/*  80 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setPrpqIndc("");
/*  81 */     ((ChwBulkYMDMProd_MODEL)this.tbl_model.get(0)).setOrderCode(this.chwProduct.getORDERCODE());
/*     */ 
/*     */     
/*  84 */     ChwBulkYMDMProd_MAKT chwBulkYMDMProd_MAKT = new ChwBulkYMDMProd_MAKT();
/*  85 */     chwBulkYMDMProd_MAKT.setEntitytype(this.chwProduct.getMODELENTITYTYPE());
/*  86 */     chwBulkYMDMProd_MAKT.setEntityID(this.chwProduct.getMODELENTITYID());
/*  87 */     chwBulkYMDMProd_MAKT.setNlsid("E");
/*  88 */     chwBulkYMDMProd_MAKT.setInvName("MACHINE TYPE " + this.chwProduct.getMACHTYPE() + " - MODEL MEB");
/*  89 */     chwBulkYMDMProd_MAKT.setMktgDesc("MACHINE TYPE " + this.chwProduct.getMACHTYPE() + " - MODEL MEB");
/*  90 */     chwBulkYMDMProd_MAKT.setMktgName("MACHINE TYPE " + this.chwProduct.getMACHTYPE() + " - MODEL MEB");
/*  91 */     this.tbl_model_makt.add(chwBulkYMDMProd_MAKT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     ArrayList<String> arrayList1 = new ArrayList();
/* 114 */     ArrayList<String> arrayList2 = new ArrayList();
/* 115 */     List list = RFCConfig.getTaxs();
/* 116 */     for (CountryPlantTax countryPlantTax : list) {
/* 117 */       if ("7".equals(countryPlantTax.getINTERFACE_ID()) && !arrayList1.contains(countryPlantTax.getTAX_COUNTRY())) {
/* 118 */         ChwBulkYMDMProd_MLAN chwBulkYMDMProd_MLAN = new ChwBulkYMDMProd_MLAN();
/* 119 */         chwBulkYMDMProd_MLAN.setModelEntitytype(this.chwProduct.getMODELENTITYTYPE());
/* 120 */         chwBulkYMDMProd_MLAN.setModelEntityid(this.chwProduct.getMODELENTITYID());
/* 121 */         chwBulkYMDMProd_MLAN.setTaxcategoryAction("Update");
/* 122 */         chwBulkYMDMProd_MLAN.setCountryAction("Update");
/* 123 */         chwBulkYMDMProd_MLAN.setCountry_fc(countryPlantTax.getTAX_COUNTRY());
/* 124 */         chwBulkYMDMProd_MLAN.setTaxcategoryValue(countryPlantTax.getTAX_CAT());
/* 125 */         chwBulkYMDMProd_MLAN.setTaxClassification("1");
/* 126 */         arrayList1.add(countryPlantTax.getTAX_COUNTRY());
/* 127 */         this.tbl_mlan.add(chwBulkYMDMProd_MLAN);
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     for (CountryPlantTax countryPlantTax : list) {
/* 132 */       if ("7".equals(countryPlantTax.getINTERFACE_ID()) && !arrayList2.contains(countryPlantTax.getTAX_COUNTRY() + countryPlantTax.getSALES_ORG() + countryPlantTax.getPLNT_CD())) {
/* 133 */         ChwBulkYMDMProd_MVKE chwBulkYMDMProd_MVKE = new ChwBulkYMDMProd_MVKE();
/* 134 */         chwBulkYMDMProd_MVKE.setModelEntitytype(this.chwProduct.getMODELENTITYTYPE());
/* 135 */         chwBulkYMDMProd_MVKE.setModelEntityid(this.chwProduct.getMODELENTITYID());
/* 136 */         chwBulkYMDMProd_MVKE.setCountry_fc(countryPlantTax.getTAX_COUNTRY());
/* 137 */         chwBulkYMDMProd_MVKE.setSleorg(countryPlantTax.getSALES_ORG());
/* 138 */         chwBulkYMDMProd_MVKE.setPlntCd(countryPlantTax.getPLNT_CD());
/* 139 */         chwBulkYMDMProd_MVKE.setPlntDel(countryPlantTax.getDEL_PLNT());
/* 140 */         arrayList2.add(countryPlantTax.getTAX_COUNTRY() + countryPlantTax.getSALES_ORG() + countryPlantTax.getPLNT_CD());
/* 141 */         this.tbl_mvke.add(chwBulkYMDMProd_MVKE);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 146 */     if ("MODEL".equals(paramString1)) {
/*     */       
/* 148 */       List list1 = getTMFid(this.chwProduct.getMODELENTITYID());
/*     */       
/* 150 */       List<String> list2 = getXML(list1);
/* 151 */       for (String str1 : list2) {
/* 152 */         TMF_UPDATE tMF_UPDATE = XMLParse.<TMF_UPDATE>getObjectFromXml(str1, TMF_UPDATE.class);
/*     */         
/* 154 */         ChwBulkYMDMProd_TMF chwBulkYMDMProd_TMF = new ChwBulkYMDMProd_TMF();
/* 155 */         chwBulkYMDMProd_TMF.setEntityType(tMF_UPDATE.getENTITYTYPE());
/* 156 */         chwBulkYMDMProd_TMF.setEntityID(tMF_UPDATE.getENTITYID());
/* 157 */         chwBulkYMDMProd_TMF.setModelEntityType(tMF_UPDATE.getMODELENTITYTYPE());
/* 158 */         chwBulkYMDMProd_TMF.setModelEntityID(tMF_UPDATE.getMODELENTITYID());
/* 159 */         chwBulkYMDMProd_TMF.setFeatureEntityType(tMF_UPDATE.getFEATUREENTITYTYPE());
/* 160 */         chwBulkYMDMProd_TMF.setFeatureEntityID(tMF_UPDATE.getFEATUREENTITYID());
/* 161 */         chwBulkYMDMProd_TMF.setMachType(tMF_UPDATE.getMACHTYPE());
/* 162 */         chwBulkYMDMProd_TMF.setModel(tMF_UPDATE.getMODEL());
/* 163 */         chwBulkYMDMProd_TMF.setFeatureCode(tMF_UPDATE.getFEATURECODE());
/* 164 */         chwBulkYMDMProd_TMF.setPubFromAnnDate(((AVAILABILITYELEMENT_TMF)tMF_UPDATE.getAVAILABILITYLIST().get(0)).getANNDATE());
/* 165 */         chwBulkYMDMProd_TMF.setSystemMax(tMF_UPDATE.getSYSTEMMAX());
/* 166 */         chwBulkYMDMProd_TMF.setFcType(tMF_UPDATE.getFCTYPE());
/* 167 */         chwBulkYMDMProd_TMF.setBlkMesIndc(tMF_UPDATE.getBULKMESINDC());
/* 168 */         this.tbl_tmf.add(chwBulkYMDMProd_TMF);
/*     */         
/* 170 */         ChwBulkYMDMProd_FEATURE chwBulkYMDMProd_FEATURE = new ChwBulkYMDMProd_FEATURE();
/* 171 */         chwBulkYMDMProd_FEATURE.setEntityType("FEATURE");
/* 172 */         chwBulkYMDMProd_FEATURE.setEntityID(tMF_UPDATE.getFEATUREENTITYID());
/* 173 */         chwBulkYMDMProd_FEATURE.setFeatureCode(tMF_UPDATE.getFEATURECODE());
/* 174 */         this.tbl_feature.add(chwBulkYMDMProd_FEATURE);
/*     */         
/* 176 */         Map map = getFeatureAtt(tMF_UPDATE.getFEATUREENTITYID());
/* 177 */         ChwBulkYMDMProd_MAKT chwBulkYMDMProd_MAKT1 = new ChwBulkYMDMProd_MAKT();
/* 178 */         chwBulkYMDMProd_MAKT1.setEntitytype("FEATURE");
/* 179 */         chwBulkYMDMProd_MAKT1.setEntityID(tMF_UPDATE.getFEATUREENTITYID());
/* 180 */         chwBulkYMDMProd_MAKT1.setNlsid("E");
/* 181 */         String str2 = (String)map.get("BHINVNAME");
/* 182 */         str2 = (str2.length() > 30) ? str2.substring(0, 30) : str2;
/* 183 */         chwBulkYMDMProd_MAKT1.setMktgDesc(str2);
/* 184 */         chwBulkYMDMProd_MAKT1.setMktgName((String)map.get("MKTGNAME"));
/* 185 */         chwBulkYMDMProd_MAKT1.setInvName((String)map.get("INVNAME"));
/* 186 */         chwBulkYMDMProd_MAKT1.setBhInvName((String)map.get("BHINVNAME"));
/* 187 */         this.tbl_feature_makt.add(chwBulkYMDMProd_MAKT1);
/*     */       } 
/* 189 */     } else if ("PRODSTRUCT".equals(paramString1)) {
/*     */       
/* 191 */       List<String> list1 = getXML(Arrays.asList(new String[] { paramString2 }));
/* 192 */       TMF_UPDATE tMF_UPDATE = XMLParse.<TMF_UPDATE>getObjectFromXml(list1.get(0), TMF_UPDATE.class);
/*     */       
/* 194 */       ChwBulkYMDMProd_TMF chwBulkYMDMProd_TMF = new ChwBulkYMDMProd_TMF();
/* 195 */       chwBulkYMDMProd_TMF.setEntityType(tMF_UPDATE.getENTITYTYPE());
/* 196 */       chwBulkYMDMProd_TMF.setEntityID(tMF_UPDATE.getENTITYID());
/* 197 */       chwBulkYMDMProd_TMF.setModelEntityType(tMF_UPDATE.getMODELENTITYTYPE());
/* 198 */       chwBulkYMDMProd_TMF.setModelEntityID(tMF_UPDATE.getMODELENTITYID());
/* 199 */       chwBulkYMDMProd_TMF.setFeatureEntityType(tMF_UPDATE.getFEATUREENTITYTYPE());
/* 200 */       chwBulkYMDMProd_TMF.setFeatureEntityID(tMF_UPDATE.getFEATUREENTITYID());
/* 201 */       chwBulkYMDMProd_TMF.setMachType(tMF_UPDATE.getMACHTYPE());
/* 202 */       chwBulkYMDMProd_TMF.setModel(tMF_UPDATE.getMODEL());
/* 203 */       chwBulkYMDMProd_TMF.setFeatureCode(tMF_UPDATE.getFEATURECODE());
/* 204 */       chwBulkYMDMProd_TMF.setPubFromAnnDate(((AVAILABILITYELEMENT_TMF)tMF_UPDATE.getAVAILABILITYLIST().get(0)).getANNDATE());
/* 205 */       chwBulkYMDMProd_TMF.setSystemMax(tMF_UPDATE.getSYSTEMMAX());
/* 206 */       chwBulkYMDMProd_TMF.setFcType(tMF_UPDATE.getFCTYPE());
/* 207 */       chwBulkYMDMProd_TMF.setBlkMesIndc(tMF_UPDATE.getBULKMESINDC());
/* 208 */       this.tbl_tmf.add(chwBulkYMDMProd_TMF);
/*     */       
/* 210 */       ChwBulkYMDMProd_FEATURE chwBulkYMDMProd_FEATURE = new ChwBulkYMDMProd_FEATURE();
/* 211 */       chwBulkYMDMProd_FEATURE.setEntityType("FEATURE");
/* 212 */       chwBulkYMDMProd_FEATURE.setEntityID(tMF_UPDATE.getFEATUREENTITYID());
/* 213 */       chwBulkYMDMProd_FEATURE.setFeatureCode(tMF_UPDATE.getFEATURECODE());
/* 214 */       this.tbl_feature.add(chwBulkYMDMProd_FEATURE);
/*     */       
/* 216 */       Map map = getFeatureAtt(tMF_UPDATE.getFEATUREENTITYID());
/* 217 */       ChwBulkYMDMProd_MAKT chwBulkYMDMProd_MAKT1 = new ChwBulkYMDMProd_MAKT();
/* 218 */       String str = (String)map.get("BHINVNAME");
/* 219 */       str = (str.length() > 30) ? str.substring(0, 30) : str;
/*     */       
/* 221 */       chwBulkYMDMProd_MAKT1.setEntitytype("FEATURE");
/* 222 */       chwBulkYMDMProd_MAKT1.setEntityID(tMF_UPDATE.getFEATUREENTITYID());
/* 223 */       chwBulkYMDMProd_MAKT1.setNlsid("E");
/* 224 */       chwBulkYMDMProd_MAKT1.setMktgDesc(str);
/* 225 */       chwBulkYMDMProd_MAKT1.setMktgName((String)map.get("MKTGNAME"));
/* 226 */       chwBulkYMDMProd_MAKT1.setInvName((String)map.get("INVNAME"));
/* 227 */       chwBulkYMDMProd_MAKT1.setBhInvName((String)map.get("BHINVNAME"));
/* 228 */       this.tbl_feature_makt.add(chwBulkYMDMProd_MAKT1);
/* 229 */       this.rfa_num += tMF_UPDATE.getFEATURECODE() + "MEB";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isReadyToExecute() {
/* 236 */     if (getRfcrc() != 0) {
/* 237 */       return false;
/*     */     }
/* 239 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDefaultValues() {
/* 245 */     this.tbl_model = new ArrayList<>();
/* 246 */     ChwBulkYMDMProd_MODEL chwBulkYMDMProd_MODEL = new ChwBulkYMDMProd_MODEL();
/* 247 */     this.tbl_model.add(chwBulkYMDMProd_MODEL);
/*     */     
/* 249 */     this.tbl_model_makt = new ArrayList<>();
/* 250 */     this.tbl_mlan = new ArrayList<>();
/* 251 */     this.tbl_mvke = new ArrayList<>();
/* 252 */     this.tbl_tmf = new ArrayList<>();
/* 253 */     this.tbl_feature = new ArrayList<>();
/* 254 */     this.tbl_feature_makt = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getTMFid(String paramString) throws SQLException {
/* 260 */     String str = "select distinct r.entityid as TMFID from opicm.relator r\njoin opicm.flag f on f.entitytype=r.entitytype and f.entityid=r.entityid and f.attributecode='BULKMESINDC' and f.attributevalue='MES0001'\nwhere r.entitytype='PRODSTRUCT' and r.entity2type='MODEL' and r.entity2id = ?\nand r.valto>current timestamp and r.effto>current timestamp and f.valto>current timestamp and f.effto>current timestamp with ur";
/*     */ 
/*     */ 
/*     */     
/* 264 */     ArrayList<String> arrayList = new ArrayList();
/* 265 */     PreparedStatement preparedStatement = null;
/* 266 */     preparedStatement = this.pdhConnection.prepareStatement(str);
/* 267 */     preparedStatement.setString(1, paramString);
/* 268 */     ResultSet resultSet = preparedStatement.executeQuery();
/*     */     
/* 270 */     while (resultSet.next()) {
/* 271 */       arrayList.add(resultSet.getInt("TMFID") + "");
/*     */     }
/*     */     
/* 274 */     return arrayList;
/*     */   }
/*     */   
/*     */   public List<String> getXML(List<String> paramList) throws SQLException {
/* 278 */     StringBuilder stringBuilder = new StringBuilder();
/* 279 */     for (byte b1 = 0; b1 < paramList.size(); b1++) {
/* 280 */       if (b1 == 0) { stringBuilder.append("?"); }
/* 281 */       else { stringBuilder.append(",?"); }
/*     */     
/* 283 */     }  String str = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'PRODSTRUCT' and XMLENTITYID in (" + stringBuilder.toString() + ")  and XMLCACHEVALIDTO > current timestamp fetch first 1 rows only with ur";
/* 284 */     ArrayList<String> arrayList = new ArrayList();
/* 285 */     PreparedStatement preparedStatement = null;
/* 286 */     preparedStatement = this.odsConnection.prepareStatement(str);
/* 287 */     for (byte b2 = 0; b2 < paramList.size(); b2++)
/* 288 */       preparedStatement.setString(b2 + 1, paramList.get(b2)); 
/* 289 */     ResultSet resultSet = preparedStatement.executeQuery();
/*     */     
/* 291 */     if (resultSet.next()) {
/* 292 */       arrayList.add(resultSet.getString("XMLMESSAGE"));
/*     */     }
/*     */ 
/*     */     
/* 296 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getFeatureAtt(String paramString) throws SQLException {
/* 301 */     String str = "select t1.attributevalue as MKTGNAME,t2.attributevalue as INVNAME,t3.attributevalue as BHINVNAME from opicm.text t1 left join opicm.text t2 on t2.entityid= t1.entityid and t2.entitytype= t1.entitytype and t2.attributecode='INVNAME' left join opicm.text t3 on t3.entityid= t2.entityid and t3.entitytype= t2.entitytype and t3.attributecode='BHINVNAME' where t1.entityid= ? and t1.entitytype='FEATURE' and t1.attributecode='MKTGNAME' and t1.nlsid=1 and t2.nlsid=1 and t3.nlsid=1 and t1.valto>current timestamp and t1.effto>current timestamp and t2.valto>current timestamp and t2.effto>current timestamp and t3.valto>current timestamp and t3.effto>current timestamp with ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     
/* 309 */     PreparedStatement preparedStatement = null;
/* 310 */     preparedStatement = this.pdhConnection.prepareStatement(str);
/* 311 */     preparedStatement.setString(1, paramString);
/* 312 */     ResultSet resultSet = preparedStatement.executeQuery();
/*     */     
/* 314 */     while (resultSet.next()) {
/* 315 */       hashMap.put("MKTGNAME", resultSet.getString("MKTGNAME"));
/* 316 */       hashMap.put("INVNAME", resultSet.getString("INVNAME"));
/* 317 */       hashMap.put("BHINVNAME", resultSet.getString("BHINVNAME"));
/*     */     } 
/*     */     
/* 320 */     return hashMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwBulkYMDMProd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */