/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SPDGActionItem;
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
/*     */ public class AFIRMT130AABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String ABR = "AFIRMT130AABR01";
/*  46 */   private EntityGroup m_egParent = null;
/*  47 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  56 */     SPDGActionItem sPDGActionItem = null;
/*  57 */     String str1 = null;
/*  58 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  61 */       start_ABRBuild();
/*     */       
/*  63 */       buildReportHeaderII();
/*     */       
/*  65 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  66 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  67 */       println("<br><b>Software Product Data Generator Request: " + this.m_ei
/*     */           
/*  69 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  72 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*     */ 
/*     */       
/*  75 */       log("AFIRMT130AABR01 generating data");
/*  76 */       sPDGActionItem = new SPDGActionItem(null, this.m_db, this.m_prof, "SWSPDG1");
/*  77 */       sPDGActionItem.setEntityItem(this.m_ei);
/*  78 */       sPDGActionItem.executeAction(this.m_db, this.m_prof);
/*  79 */       setReturnCode(0);
/*  80 */       log("AFIRMT130AABR01 finish generating data");
/*  81 */       sPDGActionItem.dereference();
/*  82 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  83 */       setReturnCode(-2);
/*  84 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  88 */           .getMessage() + "</font></h3>");
/*     */       
/*  90 */       logError(lockPDHEntityException.getMessage());
/*  91 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  92 */       setReturnCode(-2);
/*  93 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/*  95 */           .getMessage() + "</font></h3>");
/*     */       
/*  97 */       logError(updatePDHEntityException.getMessage());
/*  98 */     } catch (SBRException sBRException) {
/*  99 */       String str = sBRException.toString();
/* 100 */       int i = str.indexOf("(ok)");
/* 101 */       if (i < 0) {
/* 102 */         setReturnCode(-2);
/* 103 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 105 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 107 */         logError(sBRException.toString());
/*     */       } else {
/* 109 */         str = str.substring(0, i);
/* 110 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 112 */     } catch (Throwable throwable) {
/*     */       
/* 114 */       println("Error in " + this.m_abri.getABRCode() + ":" + throwable.getMessage());
/* 115 */       println("" + throwable);
/* 116 */       throwable.printStackTrace();
/*     */       
/* 118 */       if (getABRReturnCode() != -2) {
/* 119 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 122 */       println("<br /><b>" + 
/*     */           
/* 124 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 127 */               getABRDescription(), 
/* 128 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 131 */       log(
/* 132 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 135 */               getABRDescription(), 
/* 136 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 139 */       str1 = this.m_ei.toString();
/* 140 */       if (str1.length() > 64) {
/* 141 */         str1 = str1.substring(0, 64);
/*     */       }
/* 143 */       setDGTitle(str1);
/* 144 */       setDGRptName("AFIRMT130AABR01");
/*     */ 
/*     */       
/* 147 */       setDGString(getABRReturnCode());
/* 148 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 152 */       buildReportFooter();
/*     */       
/* 154 */       if (!isReadOnly()) {
/* 155 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 161 */     String str = "";
/* 162 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 164 */     while (paramString1.length() > 0 && i >= 0) {
/* 165 */       str = str + paramString1.substring(0, i) + paramString3;
/* 166 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 167 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 169 */     str = str + paramString1;
/* 170 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 181 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 190 */     return "Software Product Base and Initial ABR.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStyle() {
/* 201 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 211 */     return new String("1.5");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 221 */     return "AFIRMT130AABR01.java,v 1.5 2008/06/16 19:35:22 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 229 */     return "AFIRMT130AABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT130AABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */