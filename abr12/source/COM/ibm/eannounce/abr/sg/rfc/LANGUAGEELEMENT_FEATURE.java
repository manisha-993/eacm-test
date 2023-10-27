/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import org.codehaus.jackson.map.annotate.JsonSerialize;
/*    */ 
/*    */ @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class LANGUAGEELEMENT_FEATURE
/*    */ {
/*    */   @XmlElement(name = "NLSID")
/*    */   private String NLSID;
/*    */   @XmlElement(name = "MKTGDESC")
/*    */   private String MKTGDESC;
/*    */   @XmlElement(name = "MKTGNAME")
/*    */   private String MKTGNAME;
/*    */   @XmlElement(name = "INVNAME")
/*    */   private String INVNAME;
/*    */   @XmlElement(name = "BHINVNAME")
/*    */   private String BHINVNAME;
/*    */   
/*    */   public String getNLSID() {
/* 24 */     return this.NLSID;
/*    */   }
/*    */   public String getMKTGDESC() {
/* 27 */     return this.MKTGDESC;
/*    */   }
/*    */   public String getMKTGNAME() {
/* 30 */     return this.MKTGNAME;
/*    */   }
/*    */   public String getINVNAME() {
/* 33 */     return this.INVNAME;
/*    */   }
/*    */   public String getBHINVNAME() {
/* 36 */     return this.BHINVNAME;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\LANGUAGEELEMENT_FEATURE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */