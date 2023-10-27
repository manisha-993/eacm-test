/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*    */ import org.apache.poi.hssf.usermodel.HSSFCell;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XLIdColumn
/*    */   extends XLColumn
/*    */ {
/*    */   public XLIdColumn(String paramString1, String paramString2) {
/* 35 */     super(paramString1, paramString2, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isChanged(DiffEntity paramDiffEntity) {
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void getValue(HSSFCell paramHSSFCell, EntityItem paramEntityItem) {
/* 51 */     if (paramEntityItem != null) {
/* 52 */       paramHSSFCell.setCellValue(paramEntityItem.getEntityID());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getValue(EntityItem paramEntityItem) {
/* 61 */     String str = "";
/* 62 */     if (paramEntityItem != null) {
/* 63 */       str = "" + paramEntityItem.getEntityID();
/*    */     }
/* 65 */     return str;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XLIdColumn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */