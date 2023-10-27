/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*    */ import org.apache.poi.hssf.usermodel.HSSFRichTextString;
/*    */ import org.apache.poi.ss.usermodel.RichTextString;
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
/*    */ 
/*    */ public class XLFixedColumn
/*    */   extends XLColumn
/*    */ {
/*    */   public XLFixedColumn(String paramString1, String paramString2) {
/* 38 */     super(paramString1, paramString2, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isChanged(DiffEntity paramDiffEntity) {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void getValue(HSSFCell paramHSSFCell, EntityItem paramEntityItem) {
/* 54 */     paramHSSFCell.setCellType(1);
/* 55 */     paramHSSFCell.setCellValue((RichTextString)new HSSFRichTextString(this.etype));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getValue(EntityItem paramEntityItem) {
/* 64 */     return this.etype;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 72 */     return "Column:" + getColumnLabel() + " fixed:" + this.etype;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XLFixedColumn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */