/*    */ package COM.ibm.eannounce.abr.sg.translation;
/*    */ 
/*    */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*    */ import java.sql.SQLException;
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
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
/*    */ public abstract class AddTranslationProcess
/*    */ {
/*    */   static final String STATUS_FINAL = "0020";
/* 28 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean isValid(EntityHandler paramEntityHandler, Date paramDate) throws Exception;
/*    */ 
/*    */   
/*    */   public void createRelators(RelatorHandler paramRelatorHandler, EntityHandler paramEntityHandler) throws Exception {
/* 36 */     paramRelatorHandler.addChild(paramEntityHandler.getEntityType(), paramEntityHandler
/* 37 */         .getEntityID());
/*    */   }
/*    */   
/*    */   private Date parseDate(Object paramObject) {
/*    */     try {
/* 42 */       return DATE_FORMAT.parse((String)paramObject);
/* 43 */     } catch (ParseException parseException) {
/* 44 */       throw new IllegalArgumentException("Unable to parse date: " + paramObject);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isStatusFinal(Map paramMap, String paramString) throws MiddlewareException, SQLException {
/* 50 */     return "0020".equals(paramMap.get(paramString));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isAfter(Map paramMap, String paramString, Date paramDate) throws MiddlewareException, SQLException {
/* 55 */     String str = (String)paramMap.get(paramString);
/* 56 */     if (str == null)
/* 57 */       throw new IllegalArgumentException(paramString + " cannot be null"); 
/* 58 */     Date date = parseDate(str);
/* 59 */     return paramDate.after(date);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isBefore(Map paramMap, String paramString, Date paramDate) throws MiddlewareException, SQLException {
/* 64 */     String str = (String)paramMap.get(paramString);
/* 65 */     if (str == null)
/* 66 */       return true; 
/* 67 */     Date date = parseDate(str);
/* 68 */     return paramDate.before(date);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\translation\AddTranslationProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */