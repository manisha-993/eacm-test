/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import COM.ibm.opicmpdh.transactions.OPICMABRItem;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
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
/*     */ public class ReportFormatter
/*     */ {
/* 129 */   String[] m_strHeader = null;
/* 130 */   String[][] m_strDetail = (String[][])null;
/* 131 */   String[] m_strPadSpaces = null;
/* 132 */   String m_strSpaces = "                                                                              ";
/* 133 */   String m_strColSeparator = " ";
/*     */   boolean m_bPrintHeader = true;
/*     */   boolean m_bNoDupeLines = true;
/* 136 */   Hashtable m_hNoDupeLines = new Hashtable<>();
/* 137 */   int m_iSeparatorWidth = 0;
/* 138 */   int m_iWidth = 0;
/* 139 */   int[] m_iColWidths = null;
/* 140 */   int[] m_iWrapColumn = null;
/* 141 */   int iREPORTWIDTH = 72;
/* 142 */   int m_iAvgWidth = 0;
/*     */   
/* 144 */   int m_iCurrHdrColumn = 0;
/* 145 */   int m_iCurrDtlColumn = 0;
/* 146 */   int m_iCurrHdrRow = 0;
/* 147 */   int m_iCurrDtlRow = 0;
/* 148 */   int m_iTotDetRows = 0;
/* 149 */   int m_iOffsetLen = 0;
/* 150 */   String m_strOffsetSpace = "";
/* 151 */   StringTokenizer m_st = null;
/* 152 */   OPICMABRItem m_abrItem = null;
/* 153 */   final String BREAK_INDICATOR = "$$BREAKHERE$$";
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean m_bSortOutput = false;
/*     */ 
/*     */ 
/*     */   
/* 161 */   protected int[] m_iSortColumns = null;
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
/*     */   public void setABRItem(OPICMABRItem paramOPICMABRItem) {
/* 177 */     this.m_abrItem = paramOPICMABRItem;
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
/*     */   public void setColumnSeparator(String paramString) {
/* 193 */     this.m_strColSeparator = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReportWidth(int paramInt) {
/* 203 */     this.m_iWidth = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculateColumnWidths() {
/* 213 */     this.m_iAvgWidth = this.iREPORTWIDTH / this.m_iColWidths.length;
/*     */     
/* 215 */     this.m_iWrapColumn = (int[])Array.newInstance(int.class, this.m_iColWidths.length);
/* 216 */     this.m_strPadSpaces = (String[])Array.newInstance(String.class, this.m_iColWidths.length);
/* 217 */     this.m_iSeparatorWidth = this.m_strColSeparator.length();
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
/*     */   
/*     */   private int getColCount() {
/* 240 */     return this.m_iColWidths.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeReport() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHeaderColumn(String paramString) {
/* 257 */     this.m_strHeader[this.m_iCurrHdrColumn] = paramString;
/* 258 */     this.m_iCurrHdrColumn++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDetailColumn(String paramString) {
/* 268 */     if (this.m_strDetail == null) {
/* 269 */       growDetailArray();
/*     */     }
/* 271 */     this.m_strDetail[this.m_iCurrDtlRow][this.m_iCurrDtlColumn] = paramString;
/* 272 */     this.m_iCurrDtlColumn++;
/* 273 */     if (this.m_iCurrDtlColumn > getColCount() - 1) {
/* 274 */       this.m_iCurrDtlColumn = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDetailRow(String paramString) {
/* 285 */     this.m_iCurrDtlRow++;
/* 286 */     this.m_iCurrDtlColumn = 0;
/* 287 */     this.m_iTotDetRows++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void growDetailArray() {
/* 295 */     byte b = 0;
/* 296 */     b = (this.m_iTotDetRows == 0) ? 100 : b;
/* 297 */     String[][] arrayOfString = (String[][])Array.newInstance(String.class, new int[] { b * 2, this.m_iColWidths.length });
/* 298 */     if (this.m_strDetail != null) {
/* 299 */       System.arraycopy(this.m_strDetail, 0, arrayOfString, 0, this.m_strDetail.length);
/*     */     }
/* 301 */     this.m_strDetail = arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColWidth(int[] paramArrayOfint) {
/* 312 */     this.m_iColWidths = paramArrayOfint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeader(String[] paramArrayOfString) {
/* 322 */     this.m_strHeader = paramArrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printHeaders(boolean paramBoolean) {
/* 332 */     this.m_bPrintHeader = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDetail(String[][] paramArrayOfString) {
/* 342 */     this.m_strDetail = paramArrayOfString;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     if (paramArrayOfString != null) {
/* 348 */       this.m_iTotDetRows = this.m_strDetail.length;
/*     */     } else {
/* 350 */       println("setDetail Simple.. someone passed a null parm.. no total rows...");
/* 351 */       this.m_iTotDetRows = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDetail(Vector<String> paramVector) {
/* 362 */     byte b1 = 0;
/* 363 */     byte b2 = 0;
/* 364 */     this.m_iTotDetRows = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 370 */     if (paramVector != null && paramVector.size() > 0 && this.m_iColWidths != null && this.m_iColWidths.length > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 376 */       int i = paramVector.size() / this.m_iColWidths.length;
/* 377 */       if (paramVector.size() % this.m_iColWidths.length != 0) {
/* 378 */         i++;
/*     */       }
/*     */       
/* 381 */       String[][] arrayOfString = (String[][])Array.newInstance(String.class, new int[] { i, this.m_iColWidths.length });
/* 382 */       this.m_strDetail = arrayOfString;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 387 */       for (b1 = 0; b1 < paramVector.size(); b1++) {
/*     */         
/* 389 */         if (this.m_iTotDetRows == this.m_strDetail.length) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 394 */         this.m_strDetail[this.m_iTotDetRows][b2++] = paramVector.elementAt(b1);
/* 395 */         if (b2 == this.m_iColWidths.length) {
/* 396 */           b2 = 0;
/* 397 */           this.m_iTotDetRows++;
/*     */         } 
/*     */       } 
/* 400 */     } else if (paramVector == null) {
/* 401 */       this.m_strDetail = (String[][])null;
/* 402 */       System.out.println("vDetail is null cannot perform a set Detail properly...");
/* 403 */     } else if (paramVector.size() == 0) {
/* 404 */       this.m_strDetail = (String[][])null;
/* 405 */       System.out.println("vDetail has no elements.. cannot perform a set Detail properly...");
/* 406 */     } else if (this.m_iColWidths == null) {
/* 407 */       this.m_strDetail = (String[][])null;
/* 408 */       System.out.println("m_iColWidths seems to be not set.. cannot perform a set Detail properly...");
/* 409 */     } else if (this.m_iColWidths.length == 0) {
/* 410 */       this.m_strDetail = (String[][])null;
/* 411 */       System.out.println("m_iColWidths has no length.. cannot perform a set Detail properly...");
/*     */     } else {
/* 413 */       this.m_strDetail = (String[][])null;
/*     */     } 
/*     */     
/* 416 */     if (this.m_bSortOutput) {
/* 417 */       ColumnSorter columnSorter = new ColumnSorter(this.m_iSortColumns);
/* 418 */       Arrays.sort(this.m_strDetail, columnSorter);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printReport() {
/* 427 */     byte b1 = 0;
/* 428 */     byte b2 = 0;
/* 429 */     this.m_hNoDupeLines = new Hashtable<>();
/* 430 */     if (this.m_strDetail != null) {
/* 431 */       calculateColumnWidths();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 436 */       if (this.m_bPrintHeader) {
/* 437 */         for (b1 = 0; b1 < this.m_strHeader.length; b1++) {
/* 438 */           if (b1 == 0) {
/* 439 */             print(this.m_strOffsetSpace);
/*     */           }
/* 441 */           if (b1 < this.m_strHeader.length - 1) {
/* 442 */             print(this.m_strHeader[b1]);
/* 443 */             if (this.m_strHeader[b1].length() < this.m_iColWidths[b1]) {
/* 444 */               print(this.m_strSpaces.substring(0, this.m_iColWidths[b1] - this.m_strHeader[b1].length()));
/*     */             }
/*     */             
/* 447 */             print(this.m_strColSeparator);
/*     */           } else {
/* 449 */             println(this.m_strHeader[b1]);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 456 */         for (b1 = 0; b1 < this.m_strHeader.length; b1++) {
/* 457 */           for (b2 = 0; b2 < this.m_iColWidths[b1]; b2++) {
/* 458 */             if (b2 == 0) {
/* 459 */               print(this.m_strOffsetSpace);
/*     */             }
/* 461 */             print("-");
/*     */           } 
/* 463 */           print(this.m_strColSeparator);
/*     */         } 
/* 465 */         println("");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 471 */       for (b1 = 0; b1 < this.m_iTotDetRows; b1++) {
/* 472 */         if (!this.m_bNoDupeLines) {
/* 473 */           printRow(this.m_strDetail[b1]);
/* 474 */         } else if (!foundInPrint(this.m_strDetail[b1])) {
/* 475 */           printRow(this.m_strDetail[b1]);
/*     */         } 
/*     */       } 
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
/*     */   private boolean foundInPrint(String[] paramArrayOfString) {
/* 490 */     boolean bool = true;
/* 491 */     String str = "";
/* 492 */     byte b = 0;
/* 493 */     for (b = 0; b < paramArrayOfString.length; b++) {
/* 494 */       if (b == 0) {
/* 495 */         str = paramArrayOfString[b];
/*     */       } else {
/* 497 */         str = str + "|" + paramArrayOfString[b];
/*     */       } 
/*     */     } 
/* 500 */     if (!this.m_hNoDupeLines.containsKey(str)) {
/* 501 */       bool = false;
/* 502 */       this.m_hNoDupeLines.put(str, " ");
/*     */     } 
/* 504 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printRow(String[] paramArrayOfString) {
/* 515 */     boolean bool1 = false;
/* 516 */     boolean bool2 = false;
/*     */     
/* 518 */     byte b = 0;
/* 519 */     int i = 0;
/* 520 */     int j = 0;
/* 521 */     String str1 = " * ";
/* 522 */     String str2 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 527 */     for (b = 0; b < paramArrayOfString.length; b++) {
/* 528 */       if (paramArrayOfString[b] == null) {
/* 529 */         System.out.println("ERROR in ReportFormatter printRow element _strRow[" + b + "] was null");
/*     */       } else {
/*     */         
/* 532 */         paramArrayOfString[b] = paramArrayOfString[b].trim();
/*     */         
/* 534 */         this.m_iWrapColumn[b] = 0;
/*     */         
/* 536 */         if (bool2) {
/* 537 */           paramArrayOfString[b] = this.m_strSpaces.substring(0, this.m_iColWidths[b]);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 542 */         if (paramArrayOfString[b].indexOf("$$BREAKHERE$$") > -1) {
/* 543 */           bool1 = false;
/* 544 */           bool2 = true;
/* 545 */           paramArrayOfString[b] = paramArrayOfString[b].substring(paramArrayOfString[b].indexOf("$$BREAKHERE$$") + "$$BREAKHERE$$".length());
/*     */         }
/* 547 */         else if (paramArrayOfString[b].length() > this.m_iColWidths[b] - this.m_iSeparatorWidth + 1) {
/* 548 */           bool1 = true;
/* 549 */           this.m_iWrapColumn[b] = 1;
/*     */         } 
/*     */       } 
/* 552 */     }  if (bool1) {
/* 553 */       while (bool1) {
/* 554 */         for (b = 0; b < getColCount(); b++) {
/* 555 */           if (b == 0) {
/* 556 */             print(this.m_strOffsetSpace);
/*     */           }
/*     */ 
/*     */           
/* 560 */           if (this.m_iWrapColumn[b] == 1) {
/* 561 */             j = paramArrayOfString[b].indexOf(str1);
/* 562 */             if (j == 0) {
/* 563 */               j = paramArrayOfString[b].indexOf(str1, str1.length() - 1);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 568 */             if (j < this.m_iColWidths[b] && j > 0) {
/*     */               
/* 570 */               str2 = paramArrayOfString[b].substring(0, j);
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 575 */               this.m_st = new StringTokenizer(paramArrayOfString[b]);
/* 576 */               str2 = "";
/* 577 */               while (str2.length() < this.m_iColWidths[b] - this.m_iSeparatorWidth + 1 && this.m_st.hasMoreElements()) {
/* 578 */                 str2 = str2 + ((str2.length() > 0) ? " " : "") + this.m_st.nextToken();
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 584 */               if (str2.length() > this.m_iColWidths[b])
/*     */               {
/* 586 */                 if (str2.lastIndexOf(" ") > 0)
/*     */                 {
/* 588 */                   str2 = str2.substring(0, str2.lastIndexOf(" "));
/*     */                 }
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 594 */             if (str2.length() > this.m_iColWidths[b]) {
/* 595 */               str2 = paramArrayOfString[b].substring(0, this.m_iColWidths[b] - this.m_iSeparatorWidth + 1);
/*     */             }
/*     */ 
/*     */             
/* 599 */             print(str2);
/* 600 */             print(this.m_strSpaces.substring(0, this.m_iColWidths[b] - this.m_iSeparatorWidth + str2.length() - 1) + this.m_strColSeparator);
/*     */             
/* 602 */             if (str2.length() < this.m_iColWidths[b]) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 607 */               paramArrayOfString[b] = paramArrayOfString[b].substring(str2.length() + 1);
/*     */             } else {
/*     */               
/* 610 */               paramArrayOfString[b] = paramArrayOfString[b].substring(str2.length());
/*     */             } 
/*     */             
/* 613 */             if (paramArrayOfString[b].length() < this.m_iColWidths[b]) {
/* 614 */               this.m_iWrapColumn[b] = 0;
/*     */             }
/*     */           } else {
/*     */             
/* 618 */             print(paramArrayOfString[b]);
/* 619 */             print(this.m_strSpaces.substring(0, this.m_iColWidths[b] - paramArrayOfString[b].length() - this.m_iSeparatorWidth + 1) + this.m_strColSeparator);
/* 620 */             paramArrayOfString[b] = this.m_strSpaces.substring(0, this.m_iColWidths[b] - this.m_iSeparatorWidth + 1);
/*     */           } 
/* 622 */           if (b == getColCount() - 1) {
/* 623 */             println("");
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 630 */         i = 0;
/* 631 */         for (b = 0; b < paramArrayOfString.length; b++) {
/* 632 */           i += this.m_iWrapColumn[b];
/*     */         }
/* 634 */         if (i == 0) {
/* 635 */           bool1 = false;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 641 */       for (b = 0; b < getColCount(); b++) {
/* 642 */         if (b < getColCount() - 1) {
/* 643 */           if (b == 0) {
/* 644 */             print(this.m_strOffsetSpace);
/*     */           }
/*     */           
/* 647 */           if (paramArrayOfString[b].length() <= this.m_iColWidths[b]) {
/* 648 */             print(paramArrayOfString[b]);
/* 649 */             print(this.m_strSpaces.substring(0, this.m_iColWidths[b] - paramArrayOfString[b].length() - this.m_iSeparatorWidth + 1) + this.m_strColSeparator);
/*     */           } 
/*     */         } else {
/* 652 */           println(paramArrayOfString[b]);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 656 */       for (b = 0; b < getColCount(); b++) {
/* 657 */         if (b < getColCount() - 1) {
/*     */           
/* 659 */           if (b == 0) {
/* 660 */             print(this.m_strOffsetSpace);
/*     */           }
/*     */           
/* 663 */           if (paramArrayOfString[b].length() <= this.m_iColWidths[b]) {
/*     */             
/* 665 */             if (!bool2) {
/* 666 */               print(paramArrayOfString[b]);
/* 667 */               print(this.m_strSpaces.substring(0, this.m_iColWidths[b] - paramArrayOfString[b].length() - this.m_iSeparatorWidth + 1) + this.m_strColSeparator);
/*     */             } else {
/*     */               
/* 670 */               prettyPrint(paramArrayOfString[b], 69);
/*     */             } 
/* 672 */           } else if (paramArrayOfString[b].length() > this.m_iColWidths[b]) {
/* 673 */             if (bool2) {
/*     */ 
/*     */               
/* 676 */               print(paramArrayOfString[b]);
/*     */             } else {
/* 678 */               println("WE SHOULDNT BE HERE!!");
/*     */             } 
/*     */           } 
/*     */         } else {
/* 682 */           println(paramArrayOfString[b]);
/*     */         } 
/*     */       } 
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
/*     */   public void setOffset(int paramInt) {
/* 696 */     this.m_iOffsetLen = paramInt;
/* 697 */     this.m_strOffsetSpace = "";
/* 698 */     for (byte b = 0; b < paramInt; b++) {
/* 699 */       this.m_strOffsetSpace += " ";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrintDupeLines(boolean paramBoolean) {
/* 710 */     this.m_bNoDupeLines = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 720 */     return new String("$Id: ReportFormatter.java,v 1.24 2008/03/19 19:08:04 wendy Exp $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void print(String paramString) {
/* 730 */     this.m_abrItem.print(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void println(String paramString) {
/* 740 */     this.m_abrItem.println(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ColumnSorter
/*     */     implements Comparator
/*     */   {
/*     */     int[] m_columnIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ColumnSorter(int[] param1ArrayOfint) {
/* 762 */       this.m_columnIndex = param1ArrayOfint;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(Object param1Object1, Object param1Object2) {
/* 774 */       int i = 0;
/* 775 */       byte b = 0;
/* 776 */       String[] arrayOfString1 = (String[])param1Object1;
/* 777 */       String[] arrayOfString2 = (String[])param1Object2;
/*     */       try {
/* 779 */         for (b = 0; b < this.m_columnIndex.length && !i; b++) {
/* 780 */           String str1 = arrayOfString1[this.m_columnIndex[b]];
/* 781 */           String str2 = arrayOfString2[this.m_columnIndex[b]];
/* 782 */           if (str1 == null || str2 == null) {
/* 783 */             System.out.println("ERROR in ReportFormatter compare element in array was null [" + this.m_columnIndex[b] + "] ");
/*     */             break;
/*     */           } 
/* 786 */           i = str1.compareTo(str2);
/*     */         } 
/* 788 */       } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 789 */         System.out.println("Error in ReportFormatter compare parameters: ArrayLength is " + arrayOfString1.length + " column parameter is " + this.m_columnIndex[b] + " " + arrayIndexOutOfBoundsException.getMessage());
/* 790 */         arrayIndexOutOfBoundsException.printStackTrace();
/*     */       } 
/* 792 */       return i;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSortable(boolean paramBoolean) {
/* 803 */     this.m_bSortOutput = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSortColumns(int[] paramArrayOfint) {
/* 813 */     this.m_iSortColumns = paramArrayOfint;
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
/*     */   public void prettyPrint(String paramString, int paramInt) {
/* 831 */     String str1 = null;
/* 832 */     String str2 = null;
/*     */     
/* 834 */     int i = 0;
/*     */ 
/*     */     
/* 837 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "\n", false);
/* 838 */     while (stringTokenizer.hasMoreElements()) {
/* 839 */       str2 = stringTokenizer.nextToken();
/* 840 */       if (str2.length() > paramInt) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 845 */         while (str2.length() > paramInt) {
/* 846 */           i = (str2.length() >= paramInt) ? paramInt : str2.length();
/* 847 */           str1 = str2.substring(0, i);
/* 848 */           if (!str1.substring(i - 1).equals(" "))
/*     */           {
/* 850 */             if (str1.lastIndexOf(" ") > 0)
/*     */             {
/* 852 */               str1 = str1.substring(0, str1.lastIndexOf(" "));
/*     */             }
/*     */           }
/* 855 */           str2 = str2.substring(str1.length());
/* 856 */           println(str1.trim());
/*     */         } 
/* 858 */         if (str2.trim().length() > 0)
/* 859 */           println(str2.trim()); 
/*     */         continue;
/*     */       } 
/* 862 */       println(str2.trim());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\ReportFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */