/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWSUBSPROD30APDG;
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
/*     */ public class AFIRMT430AABR01
/*     */   extends PokBaseABR
/*     */ {
/*  49 */   public static final String ABR = new String("AFIRMT430AABR01");
/*     */   
/*  51 */   private EntityGroup m_egParent = null;
/*  52 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  60 */     SWSUBSPROD30APDG sWSUBSPROD30APDG = null;
/*  61 */     String str1 = null;
/*  62 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  65 */       start_ABRBuild();
/*     */       
/*  67 */       buildReportHeaderII();
/*     */       
/*  69 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  70 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  71 */       println("<br><b>Software Subscription Data Generator Request: " + this.m_ei
/*     */           
/*  73 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  76 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  77 */       setReturnCode(0);
/*     */ 
/*     */       
/*     */       try {
/*  81 */         if (getReturnCode() == 0) {
/*  82 */           log("AFIRMT430AABR01 generating data");
/*  83 */           sWSUBSPROD30APDG = new SWSUBSPROD30APDG(null, this.m_db, this.m_prof, "SWSUBSPROD30APDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  89 */           sWSUBSPROD30APDG.setEntityItem(this.m_ei);
/*  90 */           sWSUBSPROD30APDG.executeAction(this.m_db, this.m_prof);
/*  91 */           log("AFIRMT430AABR01 finish generating data");
/*     */         } 
/*  93 */       } catch (SBRException sBRException) {
/*  94 */         String str = sBRException.toString();
/*  95 */         int i = str.indexOf("(ok)");
/*  96 */         if (i < 0) {
/*  97 */           setReturnCode(-2);
/*  98 */           println("<h3><font color=red>Generate Data error: " + 
/*     */               
/* 100 */               replace(str, str2, "<br>") + "</font></h3>");
/*     */           
/* 102 */           logError(sBRException.toString());
/*     */         } else {
/* 104 */           str = str.substring(0, i);
/* 105 */           println(replace(str, str2, "<br>"));
/*     */         } 
/*     */       } 
/* 108 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 109 */       setReturnCode(-2);
/* 110 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 114 */           .getMessage() + "</font></h3>");
/*     */       
/* 116 */       logError(lockPDHEntityException.getMessage());
/* 117 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 118 */       setReturnCode(-2);
/* 119 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 121 */           .getMessage() + "</font></h3>");
/*     */       
/* 123 */       logError(updatePDHEntityException.getMessage());
/* 124 */     } catch (Exception exception) {
/*     */       
/* 126 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 127 */       println("" + exception);
/* 128 */       exception.printStackTrace();
/*     */       
/* 130 */       if (getABRReturnCode() != -2) {
/* 131 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 134 */       println("<br /><b>" + 
/*     */           
/* 136 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 139 */               getABRDescription(), 
/* 140 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 143 */       log(
/* 144 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 147 */               getABRDescription(), 
/* 148 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 151 */       str1 = this.m_ei.toString();
/* 152 */       if (str1.length() > 64) {
/* 153 */         str1 = str1.substring(0, 64);
/*     */       }
/* 155 */       setDGTitle(str1);
/* 156 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 159 */       setDGString(getABRReturnCode());
/* 160 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 164 */       buildReportFooter();
/*     */       
/* 166 */       if (!isReadOnly()) {
/* 167 */         clearSoftLock();
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
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 181 */     String str = "";
/* 182 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 184 */     while (paramString1.length() > 0 && i >= 0) {
/* 185 */       str = str + paramString1.substring(0, i) + paramString3;
/* 186 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 187 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 189 */     str = str + paramString1;
/* 190 */     return str;
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
/* 201 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 210 */     return "Software Subscription Product Base ABR.";
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
/* 221 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 230 */     return new String("1.7");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 239 */     return "AFIRMT430AABR01.java,v 1.7 2006/03/03 19:23:25 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 248 */     return "AFIRMT430AABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT430AABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */