/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import java.util.Collection;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class XLRow
/*     */ {
/*     */   private Object rowitem;
/*     */   private String rowkey;
/*  24 */   private Hashtable rowItemTbl = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XLRow(Object paramObject) {
/*  31 */     this.rowitem = paramObject;
/*  32 */     if (paramObject instanceof EntityItem) {
/*  33 */       this.rowkey = ((EntityItem)paramObject).getKey();
/*     */     }
/*  35 */     if (paramObject instanceof DiffEntity) {
/*  36 */       this.rowkey = ((DiffEntity)paramObject).getKey();
/*     */     }
/*  38 */     addRowItem(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRowItem(Object paramObject) {
/*  45 */     if (paramObject instanceof EntityItem) {
/*  46 */       this.rowItemTbl.put(((EntityItem)paramObject).getEntityType(), paramObject);
/*     */     }
/*  48 */     if (paramObject instanceof DiffEntity) {
/*  49 */       this.rowItemTbl.put(((DiffEntity)paramObject).getEntityType(), paramObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRowType() {
/*  57 */     String str = "";
/*  58 */     if (this.rowitem instanceof EntityItem) {
/*  59 */       str = ((EntityItem)this.rowitem).getEntityType();
/*     */     }
/*  61 */     if (this.rowitem instanceof DiffEntity) {
/*  62 */       str = ((DiffEntity)this.rowitem).getEntityType();
/*     */     }
/*  64 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getRowItem() {
/*  70 */     return this.rowitem;
/*     */   }
/*     */ 
/*     */   
/*     */   public Hashtable getItemTbl() {
/*  75 */     return this.rowItemTbl;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRowKey() {
/*  80 */     return this.rowkey;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dereference() {
/*  85 */     this.rowitem = null;
/*  86 */     this.rowkey = null;
/*  87 */     this.rowItemTbl.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  93 */     StringBuffer stringBuffer = new StringBuffer("RowItem: " + this.rowkey + " others:");
/*  94 */     Collection collection = this.rowItemTbl.values();
/*  95 */     Iterator<EntityItem> iterator = collection.iterator();
/*  96 */     if (this.rowitem instanceof EntityItem) {
/*  97 */       while (iterator.hasNext()) {
/*  98 */         String str = ((EntityItem)iterator.next()).getKey();
/*  99 */         if (!str.equals(this.rowkey)) {
/* 100 */           stringBuffer.append(" " + str);
/*     */         }
/*     */       } 
/*     */     }
/* 104 */     if (this.rowitem instanceof DiffEntity) {
/* 105 */       while (iterator.hasNext()) {
/* 106 */         String str = ((DiffEntity)iterator.next()).getKey();
/* 107 */         if (!str.equals(this.rowkey)) {
/* 108 */           stringBuffer.append(" " + str);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 113 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 120 */     XLRow xLRow = (XLRow)paramObject;
/* 121 */     if (!xLRow.getRowKey().equals(getRowKey())) {
/* 122 */       return false;
/*     */     }
/* 124 */     Collection collection = getItemTbl().values();
/* 125 */     Set set = getItemTbl().keySet();
/* 126 */     if (collection.containsAll(xLRow.getItemTbl().values()) && set
/* 127 */       .containsAll(xLRow.getItemTbl().keySet())) {
/* 128 */       return true;
/*     */     }
/* 130 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XLRow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */