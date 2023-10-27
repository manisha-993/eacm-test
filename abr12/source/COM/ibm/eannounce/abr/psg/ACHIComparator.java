/*    */ package COM.ibm.eannounce.abr.psg;
/*    */ 
/*    */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
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
/*    */ public class ACHIComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object paramObject1, Object paramObject2) {
/* 38 */     AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramObject1;
/* 39 */     AttributeChangeHistoryItem attributeChangeHistoryItem2 = (AttributeChangeHistoryItem)paramObject2;
/* 40 */     int i = attributeChangeHistoryItem1.getChangeDate().compareTo(attributeChangeHistoryItem2.getChangeDate());
/* 41 */     if (i == 0) {
/*    */       
/* 43 */       attributeChangeHistoryItem1.get("ATTVAL", true).toString().compareTo(attributeChangeHistoryItem2
/* 44 */           .get("ATTVAL", true).toString());
/*    */     } else {
/*    */       
/* 47 */       i *= -1;
/*    */     } 
/* 49 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 56 */     return paramObject instanceof ACHIComparator;
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\psg\ACHIComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */