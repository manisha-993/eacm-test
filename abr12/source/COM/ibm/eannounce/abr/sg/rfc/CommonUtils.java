/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommonUtils
/*     */ {
/*     */   public static String getFirstSubString(String paramString, int paramInt) {
/*  16 */     if (paramString == null) { paramString = ""; }
/*  17 */     else { paramString = (paramString.length() > paramInt) ? paramString.substring(0, paramInt) : paramString; }
/*  18 */      return paramString;
/*     */   }
/*     */   
/*     */   public static String getLastSubString(String paramString, int paramInt) {
/*  22 */     if (paramString == null) { paramString = ""; }
/*  23 */     else { paramString = (paramString.length() > paramInt) ? paramString.substring(paramString.length() - paramInt, paramString.length()) : paramString; }
/*  24 */      return paramString;
/*     */   }
/*     */   
/*     */   public static String frontCompWithZore(int paramInt1, int paramInt2) {
/*  28 */     return String.format("%0" + paramInt2 + "d", new Object[] { Integer.valueOf(paramInt1) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getNoLetter(String paramString) {
/*  33 */     String str1 = "";
/*  34 */     if (paramString == null) return ""; 
/*  35 */     String str2 = ".*[a-zA-Z]+.*";
/*  36 */     Matcher matcher = Pattern.compile(str2).matcher(paramString);
/*  37 */     if (matcher.matches()) {
/*  38 */       str1 = "";
/*     */     } else {
/*  40 */       str1 = paramString;
/*     */     } 
/*     */     
/*  43 */     return str1;
/*     */   }
/*     */   
/*     */   public static boolean isNoLetter(String paramString) {
/*  47 */     boolean bool = true;
/*  48 */     if (paramString == null) return true; 
/*  49 */     String str = ".*[a-zA-Z]+.*";
/*  50 */     Matcher matcher = Pattern.compile(str).matcher(paramString);
/*  51 */     if (matcher.matches()) {
/*  52 */       bool = false;
/*     */     } else {
/*  54 */       bool = true;
/*     */     } 
/*     */     
/*  57 */     return bool;
/*     */   }
/*     */   
/*     */   public static boolean hasLetter(String paramString) {
/*  61 */     boolean bool = false;
/*  62 */     if (paramString == null) return false; 
/*  63 */     String str = ".*[a-zA-Z]+.*";
/*  64 */     Matcher matcher = Pattern.compile(str).matcher(paramString);
/*  65 */     if (matcher.matches()) {
/*  66 */       bool = true;
/*     */     } else {
/*  68 */       bool = false;
/*     */     } 
/*  70 */     return bool;
/*     */   }
/*     */   
/*     */   public static String getSubstrToChar(String paramString1, String paramString2) {
/*  74 */     if (paramString1 == null) return ""; 
/*  75 */     if (paramString2 == null) return ""; 
/*  76 */     int i = paramString1.indexOf(paramString2);
/*  77 */     if (i == -1) return ""; 
/*  78 */     paramString1 = paramString1.substring(0, i).trim();
/*  79 */     return paramString1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getPreparedSQL(String paramString, Object[] paramArrayOfObject) {
/*  84 */     int i = 0;
/*  85 */     if (null != paramArrayOfObject) i = paramArrayOfObject.length; 
/*  86 */     if (1 > i) return paramString; 
/*  87 */     StringBuffer stringBuffer = new StringBuffer();
/*  88 */     String[] arrayOfString = paramString.split("\\?");
/*  89 */     for (byte b = 0; b < i; b++) {
/*  90 */       stringBuffer.append(arrayOfString[b]).append(" '").append(paramArrayOfObject[b]).append("' ");
/*     */     }
/*  92 */     if (arrayOfString.length > paramArrayOfObject.length) {
/*  93 */       stringBuffer.append(arrayOfString[arrayOfString.length - 1]);
/*     */     }
/*  95 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public static boolean contains(String paramString1, String paramString2) {
/*  99 */     boolean bool = false;
/* 100 */     if (paramString1 == null) return false; 
/* 101 */     if (paramString2 == null) return false; 
/* 102 */     if ("".equals(paramString2.trim())) return false; 
/* 103 */     bool = paramString1.contains(paramString2);
/* 104 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isNumber(String paramString) {
/*     */     try {
/* 110 */       BigDecimal bigDecimal = new BigDecimal(paramString);
/* 111 */       return true;
/* 112 */     } catch (Exception exception) {
/* 113 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 136 */     String[] arrayOfString = new String[2];
/* 137 */     arrayOfString[0] = "11";
/* 138 */     arrayOfString[1] = "22";
/* 139 */     String str1 = "SELECT DISTINCT t2.ATTRIBUTEVALUE AS MODEL, t3.ATTRIBUTEVALUE AS INVNAME FROM OPICM.flag F  INNER JOIN opicm.flag t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='MACHTYPEATR' AND T1.ATTRIBUTEVALUE = ? AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='MODELATR' AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP INNER JOIN opicm.text t3 ON f.ENTITYID =t3.ENTITYID AND f.ENTITYTYPE =t3.ENTITYTYPE AND t3.ATTRIBUTECODE ='INVNAME' AND t3.NLSID =1 AND T3.VALTO > CURRENT  TIMESTAMP AND T3.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t1.ENTITYID AND F1.ENTITYTYPE =t1.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP WHERE f.ENTITYTYPE ='MODEL' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELTIERPABRSTATUS')  AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION=?  WITH ur";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     String str2 = getPreparedSQL(str1, (Object[])arrayOfString);
/* 149 */     System.out.println("querySql=" + str2);
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\CommonUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */