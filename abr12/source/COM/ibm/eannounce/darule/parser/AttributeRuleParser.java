/*     */ package COM.ibm.eannounce.darule.parser;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.StringUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttributeRuleParser
/*     */ {
/*     */   private static final int STATE_READING = 0;
/*     */   private static final int STATE_OPEN = 1;
/*     */   private static final char DELIMITER = '~';
/*     */   
/*     */   public List parse(String paramString) throws Exception {
/*  55 */     ArrayList<AttributeRule> arrayList = new ArrayList();
/*     */     
/*  57 */     boolean bool = false;
/*  58 */     String str = "";
/*  59 */     for (byte b = 0; b < paramString.length(); b++) {
/*  60 */       char c = paramString.charAt(b);
/*  61 */       if (c == '~') {
/*  62 */         if (!bool) {
/*  63 */           str = "";
/*  64 */           bool = true;
/*  65 */         } else if (bool == true) {
/*  66 */           arrayList
/*  67 */             .add(parseAttributeRule('~' + str + '~', str
/*  68 */                 .trim()));
/*  69 */           bool = false;
/*     */         } 
/*  71 */       } else if (bool == true) {
/*  72 */         str = str + c;
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributeRule parseAttributeRule(String paramString1, String paramString2) throws Exception {
/*  82 */     EntityAttributeRule entityAttributeRule = null;
/*  83 */     AttributeRule attributeRule = null;
/*  84 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString2, ":");
/*  85 */     while (stringTokenizer.hasMoreTokens()) {
/*  86 */       EntityAttributeRule entityAttributeRule1; String str = stringTokenizer.nextToken().trim();
/*  87 */       RelatorAttributeRule relatorAttributeRule = null;
/*  88 */       if (str.indexOf("-d") > 0) {
/*     */         
/*  90 */         String str1 = StringUtils.replace(str, "-d", "");
/*  91 */         relatorAttributeRule = new RelatorAttributeRule(str1, 'd');
/*     */       }
/*  93 */       else if (str.indexOf("-u") > 0) {
/*     */         
/*  95 */         String str1 = StringUtils.replace(str, "-u", "");
/*  96 */         relatorAttributeRule = new RelatorAttributeRule(str1, 'u');
/*     */       }
/*  98 */       else if (str.indexOf(".") > 0) {
/*     */         
/* 100 */         StringTokenizer stringTokenizer1 = new StringTokenizer(str, ".");
/*     */         
/* 102 */         entityAttributeRule1 = new EntityAttributeRule(stringTokenizer1.nextToken().trim(), stringTokenizer1.nextToken().trim());
/*     */       } 
/*     */       
/* 105 */       if (entityAttributeRule1 == null) {
/* 106 */         throw new Exception("Inavlid step: " + str);
/*     */       }
/*     */ 
/*     */       
/* 110 */       entityAttributeRule1.setKey(paramString1);
/*     */       
/* 112 */       if (entityAttributeRule == null) {
/* 113 */         entityAttributeRule = entityAttributeRule1;
/*     */       }
/* 115 */       if (attributeRule != null) {
/* 116 */         attributeRule.setNextRule(entityAttributeRule1);
/*     */       }
/* 118 */       attributeRule = entityAttributeRule1;
/*     */     } 
/*     */     
/* 121 */     return entityAttributeRule;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\parser\AttributeRuleParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */