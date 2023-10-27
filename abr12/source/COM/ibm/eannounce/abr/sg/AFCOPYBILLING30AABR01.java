/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWCOPYBILLING30APDG;
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
/*     */ public class AFCOPYBILLING30AABR01
/*     */   extends PokBaseABR
/*     */ {
/*  36 */   public static final String ABR = new String("AFCOPYBILLING30AABR01");
/*     */   
/*  38 */   private EntityGroup m_egParent = null;
/*  39 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  48 */     EntityGroup entityGroup = null;
/*  49 */     SWCOPYBILLING30APDG sWCOPYBILLING30APDG = null;
/*  50 */     StringBuffer stringBuffer = null;
/*  51 */     String str1 = null;
/*  52 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  55 */       start_ABRBuild();
/*     */       
/*  57 */       buildReportHeaderII();
/*     */       
/*  59 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  60 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  61 */       println("<br><b>Model: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  63 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  64 */       setReturnCode(0);
/*     */ 
/*     */       
/*  67 */       entityGroup = this.m_elist.getEntityGroup("SWPRODSTRUCT");
/*  68 */       if (entityGroup.getEntityItemCount() <= 0) {
/*  69 */         println("<br /><font color=red>Failed. There is no SWPRODSTRUCT selected</font>");
/*  70 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/*  74 */       if (getReturnCode() == 0) {
/*  75 */         log("AFCOPYBILLING30AABR01 generating data");
/*  76 */         sWCOPYBILLING30APDG = new SWCOPYBILLING30APDG(null, this.m_db, this.m_prof, "SWCOPYBILLING30APDG");
/*  77 */         sWCOPYBILLING30APDG.setEntityItem(this.m_ei);
/*  78 */         sWCOPYBILLING30APDG.setABReList(this.m_elist);
/*  79 */         sWCOPYBILLING30APDG.executeAction(this.m_db, this.m_prof);
/*  80 */         stringBuffer = sWCOPYBILLING30APDG.getActivities();
/*  81 */         println("</br></br/><b>Generated Data:</b>");
/*  82 */         println("<br/>" + stringBuffer.toString());
/*     */         
/*  84 */         log("AFCOPYBILLING30AABR01 finish generating data");
/*     */       } 
/*  86 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  87 */       setReturnCode(-2);
/*  88 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  92 */           .getMessage() + "</font></h3>");
/*     */       
/*  94 */       logError(lockPDHEntityException.getMessage());
/*  95 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  96 */       setReturnCode(-2);
/*  97 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/*  99 */           .getMessage() + "</font></h3>");
/*     */       
/* 101 */       logError(updatePDHEntityException.getMessage());
/* 102 */     } catch (SBRException sBRException) {
/* 103 */       String str = sBRException.toString();
/* 104 */       int i = str.indexOf("(ok)");
/* 105 */       if (i < 0) {
/* 106 */         setReturnCode(-2);
/* 107 */         println("<h3><font color=red>Generate Data error: " + replace(str, str2, "<br>") + "</font></h3>");
/* 108 */         logError(sBRException.toString());
/*     */       } else {
/* 110 */         str = str.substring(0, i);
/* 111 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 113 */     } catch (Exception exception) {
/*     */       
/* 115 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 116 */       println("" + exception);
/* 117 */       exception.printStackTrace();
/*     */       
/* 119 */       if (getABRReturnCode() != -2) {
/* 120 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 123 */       println("<br /><b>" + 
/*     */           
/* 125 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 128 */               getABRDescription(), 
/* 129 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 132 */       log(
/* 133 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 136 */               getABRDescription(), 
/* 137 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 140 */       str1 = this.m_ei.toString();
/* 141 */       if (str1.length() > 64) {
/* 142 */         str1 = str1.substring(0, 64);
/*     */       }
/* 144 */       setDGTitle(str1);
/* 145 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 148 */       setDGString(getABRReturnCode());
/* 149 */       printDGSubmitString();
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
/* 190 */     return "Software Copy Billing ABR.";
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
/* 211 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 221 */     return "AFCOPYBILLING30AABR01.java,v 1.4 2006/03/03 19:23:24 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 229 */     return "AFCOPYBILLING30AABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFCOPYBILLING30AABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */