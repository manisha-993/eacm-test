/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.EntityItem;
/*    */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*    */ import java.util.Comparator;
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
/*    */ public class AttrComparator
/*    */   implements Comparator
/*    */ {
/* 21 */   private String attrCode = null;
/*    */   
/*    */   public AttrComparator(String paramString) {
/* 24 */     this.attrCode = paramString;
/*    */   }
/*    */   public int compare(Object paramObject1, Object paramObject2) {
/* 27 */     EntityItem entityItem1 = (EntityItem)paramObject1;
/* 28 */     EntityItem entityItem2 = (EntityItem)paramObject2;
/*    */     
/* 30 */     String str1 = PokUtils.getAttributeValue(entityItem1, this.attrCode, "", "", false);
/* 31 */     String str2 = PokUtils.getAttributeValue(entityItem2, this.attrCode, "", "", false);
/* 32 */     return str1.compareTo(str2);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\AttrComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */