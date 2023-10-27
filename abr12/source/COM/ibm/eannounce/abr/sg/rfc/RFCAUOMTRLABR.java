/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*     */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*     */ import com.ibm.rdh.chw.entity.AUOMaterial;
/*     */ import com.ibm.rdh.chw.entity.CntryTax;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.io.IOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RFCAUOMTRLABR
/*     */   extends RfcAbrAdapter
/*     */ {
/*     */   public RFCAUOMTRLABR(RFCABRSTATUS paramRFCABRSTATUS) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
/*  48 */     super(paramRFCABRSTATUS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processThis() throws Exception {
/*  53 */     this.abr.addDebug("Start processThis()");
/*     */ 
/*     */     
/*  56 */     EntityItem entityItem = getRooEntityItem();
/*     */     
/*  58 */     Vector vector = PokUtils.getAllLinkedEntities(entityItem, "AUOMTRLGENAA", "GENERALAREA");
/*     */     
/*  60 */     List<SalesOrgPlants> list = getAllSalesOrgPlant(vector);
/*  61 */     Set<String> set1 = getAllPlant(list);
/*  62 */     Set<String> set2 = getAllReturnPlant(list);
/*     */     
/*  64 */     String str1 = getAttributeValue(entityItem, "MATERIAL");
/*  65 */     String str2 = str1;
/*  66 */     String str3 = "C";
/*     */     
/*  68 */     AUOMaterial aUOMaterial = new AUOMaterial();
/*  69 */     aUOMaterial.setMaterial(str1);
/*  70 */     aUOMaterial.setAmrtztlnstrt(getAttributeValue(entityItem, "AMRTZTNSTRT"));
/*  71 */     aUOMaterial.setAmrtztlnlngth(getAttributeValue(entityItem, "AMRTZTNLNGTH"));
/*  72 */     aUOMaterial.setDiv(getAttributeFlagValue(entityItem, "DIV"));
/*  73 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*  74 */     aUOMaterial.setEffectiveDate(simpleDateFormat.parse(getAttributeValue(entityItem, "EFFECTIVEDATE")));
/*  75 */     aUOMaterial.setAcctAsgnGrp(getAttributeFlagValue(entityItem, "ACCTASGNGRP"));
/*  76 */     aUOMaterial.setMaterialGroup1("SWC");
/*  77 */     aUOMaterial.setCHWProdHierarchy(getAttributeValue(entityItem, "PRODHIERCD"));
/*  78 */     aUOMaterial.setCountryList(getCountryList(list));
/*  79 */     aUOMaterial.setShortName(getAttributeValue(entityItem, "AUODESC"));
/*  80 */     this.abr.addDebug(aUOMaterial.toString());
/*     */     
/*  82 */     this.rdhRestProxy.r062(aUOMaterial, str3);
/*  83 */     for (String str : set1) {
/*  84 */       this.rdhRestProxy.r005(aUOMaterial, str, str3);
/*  85 */       this.abr.addDebug("Call r005 successfully for plant " + str);
/*     */     } 
/*  87 */     for (SalesOrgPlants salesOrgPlants : list) {
/*  88 */       String str = salesOrgPlants.getSalesorg();
/*  89 */       this.rdhRestProxy.r006(aUOMaterial, str, "Z0", new Date(), str3);
/*  90 */       this.abr.addDebug("Call r006 successfully for salesOrg " + str);
/*     */     } 
/*  92 */     for (String str : set2) {
/*  93 */       this.rdhRestProxy.r057(aUOMaterial, str, str3);
/*  94 */       this.abr.addDebug("Call r057 successfully for returnPlant " + str);
/*     */     } 
/*  96 */     this.rdhRestProxy.r009(aUOMaterial, str3);
/*  97 */     this.rdhRestProxy.r039(aUOMaterial, str3);
/*     */     
/*  99 */     if (needReleaseParkingTable()) {
/* 100 */       this.rdhRestProxy.r144(str2, "R", str3);
/*     */     } else {
/* 102 */       this.rdhRestProxy.r144(str2, "H", str3);
/*     */     } 
/*     */ 
/*     */     
/* 106 */     if (this.entityList != null) {
/* 107 */       this.entityList.dereference();
/* 108 */       this.entityList = null;
/*     */     } 
/* 110 */     this.abr.addDebug("End processThis()");
/*     */   }
/*     */ 
/*     */   
/*     */   private Vector getCountryList(List<SalesOrgPlants> paramList) throws RfcAbrException {
/* 115 */     Vector<CntryTax> vector = new Vector();
/* 116 */     for (SalesOrgPlants salesOrgPlants : paramList) {
/* 117 */       CntryTax cntryTax = new CntryTax();
/* 118 */       cntryTax.setCountry(salesOrgPlants.getGenAreaCode());
/* 119 */       vector.add(cntryTax);
/*     */     } 
/* 121 */     return vector;
/*     */   }
/*     */   
/*     */   protected Set<String> getAllReturnPlant(List<SalesOrgPlants> paramList) {
/* 125 */     HashSet<String> hashSet = new HashSet();
/* 126 */     for (SalesOrgPlants salesOrgPlants : paramList) {
/* 127 */       Vector<String> vector = salesOrgPlants.getReturnPlants();
/* 128 */       for (String str : vector) {
/* 129 */         hashSet.add(str);
/*     */       }
/* 131 */       if (vector.size() == 0) {
/* 132 */         this.abr.addDebug("getAllReturnPlant No return plant found for country code: " + salesOrgPlants.getGenAreaCode());
/*     */       }
/*     */     } 
/* 135 */     this.abr.addDebug("getAllReturnPlant return plants size: " + hashSet.size() + " values: " + hashSet);
/* 136 */     return hashSet;
/*     */   }
/*     */   
/*     */   public String getVeName() {
/* 140 */     return "RFCAUOMTRLABR";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RFCAUOMTRLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */