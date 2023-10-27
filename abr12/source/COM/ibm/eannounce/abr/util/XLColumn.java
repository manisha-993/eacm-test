/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*     */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MetaFlag;
/*     */ import com.ibm.transform.oim.eacm.diff.DiffEntity;
/*     */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*     */ import java.util.Hashtable;
/*     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*     */ import org.apache.poi.hssf.usermodel.HSSFRichTextString;
/*     */ import org.apache.poi.ss.usermodel.RichTextString;
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
/*     */ public class XLColumn
/*     */ {
/*     */   private static final String BLANKS = "                                                                                ";
/*     */   public static final int ATTRVAL = 0;
/*     */   public static final int FLAGVAL = 1;
/*     */   public static final boolean LEFT = true;
/*     */   public static final boolean RIGHT = false;
/*  42 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  43 */   static final String NEWLINE = new String(FOOL_JTEST);
/*     */   
/*     */   private String colName;
/*     */   private String ffColName;
/*  47 */   protected String etype = null;
/*  48 */   protected String attrCode = null;
/*  49 */   private int colWidth = 0;
/*     */   private boolean isLeftJustified = true;
/*  51 */   private int attrSrc = 0;
/*     */   public void setAlwaysShow() {
/*  53 */     this.alwaysShow = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean alwaysShow = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public XLColumn(String paramString1, String paramString2, String paramString3) {
/*  64 */     this(paramString1, paramString2, paramString3, 0);
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
/*     */   public XLColumn(String paramString1, String paramString2, String paramString3, int paramInt) {
/*  77 */     this.colName = paramString1;
/*  78 */     this.etype = paramString2;
/*  79 */     this.attrCode = paramString3;
/*  80 */     this.attrSrc = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColumnLabel() {
/*  88 */     return this.colName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFFColumnLabel() {
/*  96 */     return this.ffColName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFFColumnLabel(String paramString) {
/* 102 */     this.ffColName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumnValue(HSSFCell paramHSSFCell, Hashtable paramHashtable) {
/* 109 */     Object object = paramHashtable.get(this.etype);
/* 110 */     if (object instanceof DiffEntity) {
/* 111 */       DiffEntity diffEntity = (DiffEntity)object;
/* 112 */       if (this.alwaysShow || isChanged(diffEntity)) {
/* 113 */         getValue(paramHSSFCell, diffEntity.getCurrentEntityItem());
/*     */       }
/*     */     } else {
/* 116 */       getValue(paramHSSFCell, (EntityItem)object);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColumnValue(Hashtable paramHashtable) {
/* 125 */     Object object = paramHashtable.get(this.etype);
/* 126 */     String str = "";
/* 127 */     if (object instanceof DiffEntity) {
/* 128 */       DiffEntity diffEntity = (DiffEntity)object;
/* 129 */       if (this.alwaysShow || isChanged(diffEntity)) {
/* 130 */         str = getValue(diffEntity.getCurrentEntityItem());
/*     */       }
/*     */     } else {
/* 133 */       str = getValue((EntityItem)object);
/*     */     } 
/* 135 */     return formatToWidth(str, this.colWidth, this.isLeftJustified);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumnWidth(int paramInt) {
/* 143 */     this.colWidth = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJustified(boolean paramBoolean) {
/* 150 */     this.isLeftJustified = (paramBoolean == true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnWidth() {
/* 157 */     return this.colWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getValue(HSSFCell paramHSSFCell, EntityItem paramEntityItem) {
/* 166 */     if (paramEntityItem == null) {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     paramHSSFCell.setCellType(1);
/* 171 */     paramHSSFCell.setCellValue((RichTextString)new HSSFRichTextString(getValue(paramEntityItem)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getValue(EntityItem paramEntityItem) {
/* 179 */     String str = "";
/* 180 */     if (paramEntityItem != null) {
/* 181 */       EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*     */       
/* 183 */       EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(this.attrCode);
/* 184 */       if (eANMetaAttribute == null) {
/*     */         
/* 186 */         str = "Error: Attribute " + this.attrCode + " not found in " + paramEntityItem.getEntityType() + " META data.";
/*     */       } else {
/* 188 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(this.attrCode);
/* 189 */         if (eANAttribute instanceof COM.ibm.eannounce.objects.EANTextAttribute) {
/* 190 */           str = eANAttribute.toString();
/*     */         }
/* 192 */         else if (this.attrSrc == 1) {
/* 193 */           if (eANMetaAttribute.getAttributeType().equals("U")) {
/* 194 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(this.attrCode);
/* 195 */             if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */               
/* 197 */               MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 198 */               for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */                 
/* 200 */                 if (arrayOfMetaFlag[b].isSelected()) {
/* 201 */                   str = arrayOfMetaFlag[b].getFlagCode();
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/* 206 */           } else if (eANMetaAttribute.getAttributeType().equals("F")) {
/* 207 */             StringBuffer stringBuffer = new StringBuffer();
/*     */             
/* 209 */             EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(this.attrCode);
/* 210 */             if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*     */               
/* 212 */               MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 213 */               for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*     */                 
/* 215 */                 if (arrayOfMetaFlag[b].isSelected()) {
/* 216 */                   if (stringBuffer.length() > 0) {
/* 217 */                     stringBuffer.append(", ");
/*     */                   }
/*     */                   
/* 220 */                   stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/*     */                 } 
/*     */               } 
/*     */             } 
/* 224 */             str = stringBuffer.toString();
/*     */           } 
/*     */         } else {
/* 227 */           str = PokUtils.getAttributeValue(paramEntityItem, this.attrCode, ", ", "", false);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 233 */     return str;
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
/*     */   public boolean isChanged(Hashtable paramHashtable) {
/* 245 */     boolean bool = false;
/*     */     
/* 247 */     DiffEntity diffEntity = (DiffEntity)paramHashtable.get(this.etype);
/* 248 */     if (diffEntity != null) {
/* 249 */       if (diffEntity.isDeleted() || diffEntity.isNew()) {
/* 250 */         bool = true;
/*     */       } else {
/* 252 */         bool = isChanged(diffEntity);
/*     */       } 
/*     */     }
/* 255 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isChanged(DiffEntity paramDiffEntity) {
/* 263 */     boolean bool = false;
/*     */     
/* 265 */     EntityItem entityItem1 = paramDiffEntity.getCurrentEntityItem();
/* 266 */     EntityItem entityItem2 = paramDiffEntity.getPriorEntityItem();
/* 267 */     String str1 = "";
/* 268 */     if (entityItem1 != null) {
/* 269 */       str1 = PokUtils.getAttributeValue(entityItem1, this.attrCode, ", ", "", false);
/*     */     }
/* 271 */     String str2 = "";
/* 272 */     if (entityItem2 != null) {
/* 273 */       str2 = PokUtils.getAttributeValue(entityItem2, this.attrCode, ", ", "", false);
/*     */     }
/* 275 */     bool = !str1.equals(str2) ? true : false;
/*     */     
/* 277 */     return bool;
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
/*     */   public static String formatToWidth(String paramString, int paramInt) {
/* 290 */     return formatToWidth(paramString, paramInt, true);
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
/*     */   public static String formatToWidth(String paramString, int paramInt, boolean paramBoolean) {
/* 304 */     if (paramInt == 0) {
/* 305 */       return paramString;
/*     */     }
/* 307 */     if (paramString == null) {
/* 308 */       paramString = "";
/*     */     }
/* 310 */     String str = paramString;
/* 311 */     int i = paramString.length();
/* 312 */     if (i != paramInt) {
/* 313 */       StringBuffer stringBuffer = new StringBuffer(paramString);
/*     */       
/* 315 */       paramString = paramString.replace('\n', ' ');
/* 316 */       paramString = paramString.replace('\r', ' ');
/* 317 */       if (paramBoolean == true) {
/* 318 */         stringBuffer.append("                                                                                ");
/*     */       } else {
/* 320 */         int j = paramInt - i;
/* 321 */         if (j > 0) {
/* 322 */           stringBuffer.insert(0, "                                                                                ".substring(0, j));
/*     */         }
/*     */       } 
/* 325 */       stringBuffer.setLength(paramInt);
/* 326 */       str = stringBuffer.toString();
/*     */     } 
/* 328 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 336 */     return "Column:" + this.colName + " type:" + this.etype + " attr:" + this.attrCode;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\XLColumn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */