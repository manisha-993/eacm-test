/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import org.junit.Assert;
/*    */ import org.junit.BeforeClass;
/*    */ import org.junit.Test;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChwModelConvertTest
/*    */ {
/* 20 */   private MODELCONVERT modelConvert = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @BeforeClass
/*    */   public static void setUpBeforeClass() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Test
/*    */   public void testSuccess() throws ClassNotFoundException, SQLException {
/* 33 */     System.out.println("------------- Test ChwModelConvert start -------------");
/* 34 */     String str1 = "C:/EACM_DEV/xml/MODELCONVERT.xml";
/* 35 */     String str2 = CommonEntities.loadXml(str1);
/* 36 */     this.modelConvert = CommonEntities.getModelConvertFromXml(str2);
/*    */     
/* 38 */     ChwModelConvert chwModelConvert = new ChwModelConvert(this.modelConvert);
/*    */     
/*    */     try {
/* 41 */       chwModelConvert.execute();
/* 42 */     } catch (Exception exception) {
/*    */       
/* 44 */       System.err.println(exception.getMessage());
/*    */     } 
/*    */     
/* 47 */     String str3 = chwModelConvert.createLogEntry();
/* 48 */     System.out.println(str3);
/* 49 */     Assert.assertEquals(chwModelConvert.getRfcrc(), 0L);
/* 50 */     System.out.println("------------- Test ChwModelConvert end -------------");
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\ChwModelConvertTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */