/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import com.ibm.rdh.chw.entity.TypeFeature;
/*     */ import com.ibm.rdh.chw.entity.TypeFeatureUPGGeo;
/*     */ import com.ibm.rdh.chw.entity.TypeModel;
/*     */ import com.ibm.rdh.chw.entity.TypeModelFeature;
/*     */ import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LifecycleDataGenerator
/*     */ {
/*     */   protected String varCond;
/*     */   protected String material;
/*     */   protected String objectType;
/*     */   
/*     */   public LifecycleDataGenerator(TypeModel paramTypeModel) {
/*  19 */     setMaterial(paramTypeModel);
/*  20 */     setVarCond(paramTypeModel);
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
/*     */   public LifecycleDataGenerator(TypeFeature paramTypeFeature) {
/*  33 */     setMaterial(paramTypeFeature);
/*  34 */     setVarCond(paramTypeFeature);
/*     */   }
/*     */ 
/*     */   
/*     */   public LifecycleDataGenerator(TypeModelUPGGeo paramTypeModelUPGGeo) {
/*  39 */     setMaterial(paramTypeModelUPGGeo);
/*  40 */     setVarCond(paramTypeModelUPGGeo);
/*     */   }
/*     */ 
/*     */   
/*     */   public LifecycleDataGenerator(TypeFeatureUPGGeo paramTypeFeatureUPGGeo) {
/*  45 */     setMaterial(paramTypeFeatureUPGGeo);
/*  46 */     setVarCond(paramTypeFeatureUPGGeo);
/*     */   }
/*     */ 
/*     */   
/*     */   public LifecycleDataGenerator(TypeModelFeature paramTypeModelFeature) {
/*  51 */     setMaterial(paramTypeModelFeature);
/*  52 */     setVarCond(paramTypeModelFeature);
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
/*     */   public String getMaterial() {
/* 101 */     return this.material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVarCond() {
/* 110 */     return this.varCond;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaterial(String paramString) {
/* 120 */     this.material = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaterial(TypeModel paramTypeModel) {
/* 125 */     this.material = paramTypeModel.getType() + "NEW";
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
/*     */   public void setMaterial(TypeFeature paramTypeFeature) {
/* 137 */     this.material = paramTypeFeature.getType() + "NEW";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaterial(TypeModelUPGGeo paramTypeModelUPGGeo) {
/* 143 */     if (paramTypeModelUPGGeo.isMTC()) {
/* 144 */       this.material = paramTypeModelUPGGeo.getType() + "MTC";
/*     */     } else {
/* 146 */       this.material = paramTypeModelUPGGeo.getType() + "UPG";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaterial(TypeFeatureUPGGeo paramTypeFeatureUPGGeo) {
/* 153 */     if (paramTypeFeatureUPGGeo.isCrossType()) {
/* 154 */       this.material = paramTypeFeatureUPGGeo.getType() + "MTC";
/*     */     } else {
/* 156 */       this.material = paramTypeFeatureUPGGeo.getType() + "UPG";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaterial(TypeModelFeature paramTypeModelFeature) {
/* 163 */     this.material = paramTypeModelFeature.getType() + "NEW";
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
/*     */   public void setVarCond(String paramString) {
/* 225 */     this.varCond = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVarCond(TypeModel paramTypeModel) {
/* 230 */     this.varCond = "*MODEL: " + paramTypeModel.getModel();
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
/*     */   public void setVarCond(TypeFeature paramTypeFeature) {
/* 242 */     if (paramTypeFeature.isRPQ()) {
/* 243 */       this.varCond = "RPQ: " + paramTypeFeature.getFeature();
/*     */     } else {
/* 245 */       this.varCond = "FEATURE: " + paramTypeFeature.getFeature();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVarCond(TypeModelUPGGeo paramTypeModelUPGGeo) {
/* 252 */     if (paramTypeModelUPGGeo.isMTC()) {
/* 253 */       this
/* 254 */         .varCond = "MTC: " + paramTypeModelUPGGeo.getFromType() + paramTypeModelUPGGeo.getFromModel() + "_" + paramTypeModelUPGGeo.getType() + paramTypeModelUPGGeo.getModel();
/*     */     } else {
/*     */       
/* 257 */       this
/* 258 */         .varCond = "MOD CONV: " + paramTypeModelUPGGeo.getFromModel() + "_" + paramTypeModelUPGGeo.getModel();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVarCond(TypeFeatureUPGGeo paramTypeFeatureUPGGeo) {
/* 266 */     if (paramTypeFeatureUPGGeo.isCrossType()) {
/* 267 */       this
/* 268 */         .varCond = "MTFC: " + paramTypeFeatureUPGGeo.getFromType() + paramTypeFeatureUPGGeo.getFromFeature() + "_" + paramTypeFeatureUPGGeo.getType() + paramTypeFeatureUPGGeo.getFeature();
/*     */     } else {
/*     */       
/* 271 */       this
/* 272 */         .varCond = "FEAT CONV: " + paramTypeFeatureUPGGeo.getFromFeature() + "_" + paramTypeFeatureUPGGeo.getFeature();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVarCond(TypeModelFeature paramTypeModelFeature) {
/* 280 */     if (paramTypeModelFeature.isRPQ()) {
/* 281 */       this.varCond = "MODEL RPQ: " + paramTypeModelFeature.getModel() + "_" + paramTypeModelFeature.getFeature();
/*     */     } else {
/* 283 */       this.varCond = "MODEL FEAT: " + paramTypeModelFeature.getModel() + "_" + paramTypeModelFeature.getFeature();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\LifecycleDataGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */