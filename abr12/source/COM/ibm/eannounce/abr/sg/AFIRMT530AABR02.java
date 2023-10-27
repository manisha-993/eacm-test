/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWMAINTPRODINI30APDG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AFIRMT530AABR02
/*     */   extends PokBaseABR
/*     */ {
/*  47 */   public static final String ABR = new String("AFIRMT530AABR02");
/*     */   
/*  49 */   private EntityGroup m_egParent = null;
/*  50 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  59 */     SWMAINTPRODINI30APDG sWMAINTPRODINI30APDG = null;
/*  60 */     String str1 = null;
/*  61 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  64 */       start_ABRBuild();
/*     */       
/*  66 */       buildReportHeaderII();
/*     */       
/*  68 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  69 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  70 */       println("<br><b>Software Maintenance Data Generator Request: " + this.m_ei
/*     */           
/*  72 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  75 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  76 */       setReturnCode(0);
/*     */ 
/*     */       
/*  79 */       if (getReturnCode() == 0) {
/*  80 */         log("AFIRMT530AABR02 generating data");
/*  81 */         sWMAINTPRODINI30APDG = new SWMAINTPRODINI30APDG(null, this.m_db, this.m_prof, "SWMAINTPRODINI30APDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  87 */         sWMAINTPRODINI30APDG.setEntityItem(this.m_ei);
/*  88 */         sWMAINTPRODINI30APDG.executeAction(this.m_db, this.m_prof);
/*  89 */         log("AFIRMT530AABR02 finish generating data");
/*     */       } 
/*  91 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  92 */       setReturnCode(-2);
/*  93 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  97 */           .getMessage() + "</font></h3>");
/*     */       
/*  99 */       logError(lockPDHEntityException.getMessage());
/* 100 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 101 */       setReturnCode(-2);
/* 102 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 104 */           .getMessage() + "</font></h3>");
/*     */       
/* 106 */       logError(updatePDHEntityException.getMessage());
/* 107 */     } catch (SBRException sBRException) {
/* 108 */       String str = sBRException.toString();
/* 109 */       int i = str.indexOf("(ok)");
/* 110 */       if (i < 0) {
/* 111 */         setReturnCode(-2);
/* 112 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 114 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 116 */         logError(sBRException.toString());
/*     */       } else {
/* 118 */         str = str.substring(0, i);
/* 119 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 121 */     } catch (Exception exception) {
/*     */       
/* 123 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 124 */       println("" + exception);
/* 125 */       exception.printStackTrace();
/*     */       
/* 127 */       if (getABRReturnCode() != -2) {
/* 128 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 131 */       println("<br /><b>" + 
/*     */           
/* 133 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 136 */               getABRDescription(), 
/* 137 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 140 */       log(
/* 141 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 144 */               getABRDescription(), 
/* 145 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 148 */       str1 = this.m_ei.toString();
/* 149 */       if (str1.length() > 64) {
/* 150 */         str1 = str1.substring(0, 64);
/*     */       }
/* 152 */       setDGTitle(str1);
/* 153 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 156 */       setDGString(getABRReturnCode());
/* 157 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 161 */       buildReportFooter();
/*     */       
/* 163 */       if (!isReadOnly()) {
/* 164 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 170 */     String str = "";
/* 171 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 173 */     while (paramString1.length() > 0 && i >= 0) {
/* 174 */       str = str + paramString1.substring(0, i) + paramString3;
/* 175 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 176 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 178 */     str = str + paramString1;
/* 179 */     return str;
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
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 199 */     return "Software Maintenance Product Initial ABR.";
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
/* 210 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 220 */     return new String("1.5");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 230 */     return "AFIRMT530AABR02.java,v 1.5 2006/03/03 19:23:25 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 238 */     return "AFIRMT530AABR02.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT530AABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */