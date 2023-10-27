/*    */ package COM.ibm.eannounce.abr.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
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
/*    */ public class DateUtility
/*    */ {
/* 24 */   private static DateFormat _format = new SimpleDateFormat("yyyy-MM-dd");
/* 25 */   private static DateFormat _format_simple = new SimpleDateFormat("yyyyMMdd");
/* 26 */   private static DateFormat _format_time = new SimpleDateFormat("HHmmss");
/* 27 */   private static DateFormat _format_aeszn = new SimpleDateFormat("yyMMdd");
/*    */ 
/*    */ 
/*    */   
/*    */   public static Date getTodayDate() {
/* 32 */     Calendar calendar = getToday();
/* 33 */     return calendar.getTime();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Calendar getToday() {
/* 38 */     return Calendar.getInstance();
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isBeforeToday(Date paramDate) {
/* 43 */     return paramDate.before(getTodayDate());
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isAfterToday(Date paramDate) {
/* 48 */     return paramDate.after(getTodayDate());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getTodayStringWithSapFormat() {
/* 58 */     return getDateStringWithSapFormat(getTodayDate());
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getDateStringWithSapFormat(Date paramDate) {
/* 63 */     return getDateStringUsing(paramDate, _format);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getDateStringWithSimpleFormat(Date paramDate) {
/* 68 */     return getDateStringUsing(paramDate, _format_simple);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getTodayStringWithSimpleFormat() {
/* 73 */     return getDateStringWithSimpleFormat(getTodayDate());
/*    */   }
/*    */ 
/*    */   
/*    */   private static String getDateStringUsing(Date paramDate, DateFormat paramDateFormat) {
/* 78 */     if (paramDate == null)
/*    */     {
/* 80 */       return null;
/*    */     }
/* 82 */     return paramDateFormat.format(paramDate);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getTimeStringWithTimeFormatForToday() {
/* 87 */     return getDateStringUsing(getTodayDate(), _format_time);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getDateStringWithAesznFormat(Date paramDate) {
/* 92 */     return getDateStringUsing(paramDate, _format_aeszn);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\DateUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */