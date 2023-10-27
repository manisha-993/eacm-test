/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BASE64Encoder
/*    */ {
/* 11 */   private static char[] codec_table = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String encode(byte[] paramArrayOfbyte) {
/* 19 */     int i = paramArrayOfbyte.length * 8;
/* 20 */     int j = i % 6;
/* 21 */     byte b = 0;
/* 22 */     StringBuffer stringBuffer = new StringBuffer();
/* 23 */     while (b < i) {
/* 24 */       int m, k = b / 8;
/* 25 */       switch (b % 8) {
/*    */         case 0:
/* 27 */           stringBuffer.append(codec_table[(paramArrayOfbyte[k] & 0xFC) >> 2]);
/*    */           break;
/*    */         
/*    */         case 2:
/* 31 */           stringBuffer.append(codec_table[paramArrayOfbyte[k] & 0x3F]);
/*    */           break;
/*    */         case 4:
/* 34 */           if (k == paramArrayOfbyte.length - 1) {
/* 35 */             stringBuffer
/* 36 */               .append(codec_table[(paramArrayOfbyte[k] & 0xF) << 2 & 0x3F]); break;
/*    */           } 
/* 38 */           m = ((paramArrayOfbyte[k] & 0xF) << 2 | (paramArrayOfbyte[k + 1] & 0xC0) >> 6) & 0x3F;
/* 39 */           stringBuffer.append(codec_table[m]);
/*    */           break;
/*    */         
/*    */         case 6:
/* 43 */           if (k == paramArrayOfbyte.length - 1) {
/* 44 */             stringBuffer
/* 45 */               .append(codec_table[(paramArrayOfbyte[k] & 0x3) << 4 & 0x3F]); break;
/*    */           } 
/* 47 */           m = ((paramArrayOfbyte[k] & 0x3) << 4 | (paramArrayOfbyte[k + 1] & 0xF0) >> 4) & 0x3F;
/* 48 */           stringBuffer.append(codec_table[m]);
/*    */           break;
/*    */       } 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 55 */       b += 6;
/*    */     } 
/* 57 */     if (j == 2) {
/* 58 */       stringBuffer.append("==");
/* 59 */     } else if (j == 4) {
/* 60 */       stringBuffer.append("=");
/*    */     } 
/* 62 */     return stringBuffer.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\BASE64Encoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */