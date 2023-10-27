/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import org.codehaus.jackson.map.annotate.JsonSerialize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ class CATATTRIBUTEELEMENT_FEATURE
/*     */ {
/*     */   @XmlElement(name = "CATATTRIBUTEACTION")
/*     */   private String CATATTRIBUTEACTION;
/*     */   @XmlElement(name = "CATATTRIBUTE")
/*     */   private String CATATTRIBUTE;
/*     */   @XmlElement(name = "NLSID")
/*     */   private String NLSID;
/*     */   @XmlElement(name = "CATATTRIBUTEVALUE")
/*     */   private String CATATTRIBUTEVALUE;
/*     */   
/*     */   public String getCATATTRIBUTEACTION() {
/* 175 */     return this.CATATTRIBUTEACTION;
/*     */   }
/*     */   public String getCATATTRIBUTE() {
/* 178 */     return this.CATATTRIBUTE;
/*     */   }
/*     */   public String getNLSID() {
/* 181 */     return this.NLSID;
/*     */   }
/*     */   public String getCATATTRIBUTEVALUE() {
/* 184 */     return this.CATATTRIBUTEVALUE;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\CATATTRIBUTEELEMENT_FEATURE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */