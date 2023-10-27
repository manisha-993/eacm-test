/*     */ package COM.ibm.eannounce.darule.parser;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class AttributeRuleContext
/*     */ {
/*     */   public static final char BOTH = 'b';
/*     */   public static final char DOWN = 'd';
/*     */   public static final char UP = 'u';
/*     */   private EntityItem daRuleItem;
/*     */   private EntityItem currentEntity;
/*  44 */   private List results = new ArrayList();
/*     */   
/*  46 */   private char currentDirection = 'b';
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private StringBuffer log = new StringBuffer();
/*     */   
/*     */   public EntityItem getCurrentEntity() {
/*  54 */     return this.currentEntity;
/*     */   }
/*     */   
/*     */   public void setCurrentEntity(EntityItem paramEntityItem) {
/*  58 */     this.currentEntity = paramEntityItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentDirection(char paramChar) {
/*  65 */     this.currentDirection = paramChar;
/*     */   }
/*     */   
/*     */   public char getCurrentDirection() {
/*  69 */     return this.currentDirection;
/*     */   }
/*     */   
/*     */   public void reset(EntityItem paramEntityItem) {
/*  73 */     this.log = new StringBuffer();
/*  74 */     this.currentDirection = 'b';
/*  75 */     this.results.clear();
/*  76 */     setCurrentEntity(paramEntityItem);
/*     */   }
/*     */   
/*     */   public EntityItem getDaRuleItem() {
/*  80 */     return this.daRuleItem;
/*     */   }
/*     */   
/*     */   public void setDaRuleItem(EntityItem paramEntityItem) {
/*  84 */     this.daRuleItem = paramEntityItem;
/*     */   }
/*     */   
/*     */   public List getResults() {
/*  88 */     return this.results;
/*     */   }
/*     */   
/*     */   public void addResult(String paramString) {
/*  92 */     this.results.add(paramString);
/*     */   }
/*     */   
/*     */   public void dereference() {
/*  96 */     this.daRuleItem = null;
/*  97 */     this.currentEntity = null;
/*  98 */     this.results.clear();
/*     */   }
/*     */   
/*     */   public StringBuffer getLog() {
/* 102 */     return this.log;
/*     */   }
/*     */   
/*     */   public void log(String paramString) {
/* 106 */     if (this.log != null)
/* 107 */       this.log.append(paramString + "\n"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\darule\parser\AttributeRuleContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */