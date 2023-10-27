/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class SalesOrgPlants
/*    */ {
/*    */   private String genAreaCode;
/*    */   private String genAreaName;
/*    */   private String geo;
/*    */   private String salesorg;
/*    */   private Vector<String> plants;
/*    */   private Vector<String> returnPlants;
/*    */   
/*    */   public Vector<String> getReturnPlants() {
/* 15 */     return this.returnPlants;
/*    */   }
/*    */   
/*    */   public void setReturnPlants(Vector<String> paramVector) {
/* 19 */     this.returnPlants = paramVector;
/*    */   }
/*    */   
/*    */   public String getGenAreaCode() {
/* 23 */     return this.genAreaCode;
/*    */   }
/*    */   
/*    */   public void setGenAreaCode(String paramString) {
/* 27 */     this.genAreaCode = paramString;
/*    */   }
/*    */   
/*    */   public String getGenAreaName() {
/* 31 */     return this.genAreaName;
/*    */   }
/*    */   
/*    */   public void setGenAreaName(String paramString) {
/* 35 */     this.genAreaName = paramString;
/*    */   }
/*    */   
/*    */   public String getGeo() {
/* 39 */     return this.geo;
/*    */   }
/*    */   
/*    */   public void setGeo(String paramString) {
/* 43 */     this.geo = paramString;
/*    */   }
/*    */   
/*    */   public String getSalesorg() {
/* 47 */     return this.salesorg;
/*    */   }
/*    */   
/*    */   public void setSalesorg(String paramString) {
/* 51 */     this.salesorg = paramString;
/*    */   }
/*    */   
/*    */   public Vector<String> getPlants() {
/* 55 */     return this.plants;
/*    */   }
/*    */   
/*    */   public void setPlants(Vector<String> paramVector) {
/* 59 */     this.plants = paramVector;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\SalesOrgPlants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */