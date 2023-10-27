/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import java.util.List;
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
/*    */ public class StringUtils
/*    */ {
/*    */   public static String replace(String paramString1, String paramString2, String paramString3) {
/* 32 */     if (paramString2.equals("")) {
/* 33 */       throw new IllegalArgumentException("Old pattern must have content.");
/*    */     }
/* 35 */     StringBuffer stringBuffer = new StringBuffer();
/* 36 */     int i = 0;
/* 37 */     int j = 0;
/* 38 */     while ((j = paramString1.indexOf(paramString2, i)) >= 0) {
/* 39 */       stringBuffer.append(paramString1.substring(i, j));
/* 40 */       stringBuffer.append(paramString3);
/* 41 */       i = j + paramString2.length();
/*    */     } 
/* 43 */     stringBuffer.append(paramString1.substring(i));
/* 44 */     return stringBuffer.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String concatenate(List paramList, String paramString) {
/* 55 */     StringBuffer stringBuffer = new StringBuffer();
/* 56 */     boolean bool = true;
/* 57 */     for (byte b = 0; b < paramList.size(); b++) {
/* 58 */       if (bool) {
/* 59 */         bool = false;
/*    */       } else {
/* 61 */         stringBuffer.append(paramString);
/*    */       } 
/* 63 */       stringBuffer.append(paramList.get(b));
/*    */     } 
/* 65 */     return stringBuffer.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\StringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */