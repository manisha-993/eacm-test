/*    */ package COM.ibm.eannounce.abr.sg.rfc.entity;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import org.codehaus.jackson.map.annotate.JsonSerialize;
/*    */ 
/*    */ @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class LANGUAGE {
/*    */   @XmlElement(name = "NLSID")
/*    */   private String NLSID;
/*    */   @XmlElement(name = "INVNAME")
/*    */   private String INVNAME;
/*    */   @XmlElement(name = "MKTGNAME")
/*    */   private String MKTGNAME;
/*    */   
/*    */   public String getNLSID() {
/* 19 */     return this.NLSID;
/*    */   }
/*    */   public String getINVNAME() {
/* 22 */     return this.INVNAME;
/*    */   }
/*    */   public String getMKTGNAME() {
/* 25 */     return this.MKTGNAME;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\entity\LANGUAGE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */