/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWSUBSPRODINI30APDG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AFIRMT430AABR02
/*     */   extends PokBaseABR
/*     */ {
/*  49 */   public static final String ABR = new String("AFIRMT430AABR02");
/*     */   
/*  51 */   private EntityGroup m_egParent = null;
/*  52 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  61 */     SWSUBSPRODINI30APDG sWSUBSPRODINI30APDG = null;
/*  62 */     String str1 = null;
/*  63 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  66 */       start_ABRBuild();
/*     */       
/*  68 */       buildReportHeaderII();
/*     */       
/*  70 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  71 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  72 */       println("<br><b>Software Subscription Data Generator Request: " + this.m_ei
/*     */           
/*  74 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  77 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  78 */       setReturnCode(0);
/*     */ 
/*     */       
/*     */       try {
/*  82 */         if (getReturnCode() == 0) {
/*  83 */           log("AFIRMT430AABR02 generating data");
/*  84 */           sWSUBSPRODINI30APDG = new SWSUBSPRODINI30APDG(null, this.m_db, this.m_prof, "SWSUBSPRODINI30APDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  90 */           sWSUBSPRODINI30APDG.setEntityItem(this.m_ei);
/*  91 */           sWSUBSPRODINI30APDG.executeAction(this.m_db, this.m_prof);
/*  92 */           log("AFIRMT430AABR02 finish generating data");
/*     */         } 
/*  94 */       } catch (SBRException sBRException) {
/*  95 */         String str = sBRException.toString();
/*  96 */         int i = str.indexOf("(ok)");
/*  97 */         if (i < 0) {
/*  98 */           setReturnCode(-2);
/*  99 */           println("<h3><font color=red>Generate Data error: " + 
/*     */               
/* 101 */               replace(str, str2, "<br>") + "</font></h3>");
/*     */           
/* 103 */           logError(sBRException.toString());
/*     */         } else {
/* 105 */           str = str.substring(0, i);
/* 106 */           println(replace(str, str2, "<br>"));
/*     */         }
/*     */       
/*     */       } 
/* 110 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 111 */       setReturnCode(-2);
/* 112 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 116 */           .getMessage() + "</font></h3>");
/*     */       
/* 118 */       logError(lockPDHEntityException.getMessage());
/* 119 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 120 */       setReturnCode(-2);
/* 121 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 123 */           .getMessage() + "</font></h3>");
/*     */       
/* 125 */       logError(updatePDHEntityException.getMessage());
/* 126 */     } catch (Exception exception) {
/*     */       
/* 128 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 129 */       println("" + exception);
/* 130 */       exception.printStackTrace();
/*     */       
/* 132 */       if (getABRReturnCode() != -2) {
/* 133 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 136 */       println("<br /><b>" + 
/*     */           
/* 138 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 141 */               getABRDescription(), 
/* 142 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 145 */       log(
/* 146 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 149 */               getABRDescription(), 
/* 150 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 153 */       str1 = this.m_ei.toString();
/* 154 */       if (str1.length() > 64) {
/* 155 */         str1 = str1.substring(0, 64);
/*     */       }
/* 157 */       setDGTitle(str1);
/* 158 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 161 */       setDGString(getABRReturnCode());
/* 162 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 166 */       buildReportFooter();
/*     */       
/* 168 */       if (!isReadOnly()) {
/* 169 */         clearSoftLock();
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
/* 183 */     String str = "";
/* 184 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 186 */     while (paramString1.length() > 0 && i >= 0) {
/* 187 */       str = str + paramString1.substring(0, i) + paramString3;
/* 188 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 189 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 191 */     str = str + paramString1;
/* 192 */     return str;
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
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 212 */     return "Software Subscription Product Initial ABR.";
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
/* 223 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 232 */     return new String("1.7");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 241 */     return "AFIRMT430AABR02.java,v 1.7 2006/03/03 19:23:25 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 250 */     return "AFIRMT430AABR02.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT430AABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */