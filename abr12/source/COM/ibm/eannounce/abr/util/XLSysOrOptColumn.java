/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*    */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ public class XLSysOrOptColumn
/*    */   extends XLColumn
/*    */ {
/*    */   private static final String HARDWARE = "100";
/* 42 */   private static final Set TESTSET = new HashSet(); static {
/* 43 */     TESTSET.add("100");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XLSysOrOptColumn() {
/* 51 */     super("SysOrOpt", "LSEOBUNDLE", "BUNDLETYPE");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isChanged(DiffEntity paramDiffEntity) {
/*    */     boolean bool;
/* 60 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 61 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/*    */     
/* 63 */     if (entityItem1 != null && entityItem2 != null) {
/*    */       
/* 65 */       bool = (PokUtils.contains(entityItem1, this.attrCode, TESTSET) != PokUtils.contains(entityItem2, this.attrCode, TESTSET)) ? true : false;
/*    */     } else {
/*    */       
/* 68 */       bool = true;
/*    */     } 
/*    */     
/* 71 */     return bool;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void getValue(HSSFCell paramHSSFCell, EntityItem paramEntityItem) {
/* 83 */     paramHSSFCell.setCellType(1);
/* 84 */     paramHSSFCell.setCellValue((RichTextString)new HSSFRichTextString(getValue(paramEntityItem)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getValue(EntityItem paramEntityItem) {
/* 93 */     String str = "Both";
/* 94 */     if (paramEntityItem != null && PokUtils.contains(paramEntityItem, this.attrCode, TESTSET)) {
/* 95 */       str = "Initial";
/*    */     }
/* 97 */     return str;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XLSysOrOptColumn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */