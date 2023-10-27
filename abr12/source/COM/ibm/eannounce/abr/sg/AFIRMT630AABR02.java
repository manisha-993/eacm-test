/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWSUPPPRODINI30APDG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AFIRMT630AABR02
/*     */   extends PokBaseABR
/*     */ {
/*  50 */   public static final String ABR = new String("AFIRMT630AABR02");
/*     */   
/*  52 */   private EntityGroup m_egParent = null;
/*  53 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  62 */     SWSUPPPRODINI30APDG sWSUPPPRODINI30APDG = null;
/*  63 */     String str1 = null;
/*  64 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  67 */       start_ABRBuild();
/*     */       
/*  69 */       buildReportHeaderII();
/*     */       
/*  71 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  72 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  73 */       println("<br><b>Software Support Data Generator Request: " + this.m_ei
/*     */           
/*  75 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  78 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  79 */       setReturnCode(0);
/*     */ 
/*     */       
/*  82 */       if (getReturnCode() == 0) {
/*  83 */         log("AFIRMT630AABR02 generating data");
/*  84 */         sWSUPPPRODINI30APDG = new SWSUPPPRODINI30APDG(null, this.m_db, this.m_prof, "SWSUPPPRODINI30APDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  90 */         sWSUPPPRODINI30APDG.setEntityItem(this.m_ei);
/*  91 */         sWSUPPPRODINI30APDG.executeAction(this.m_db, this.m_prof);
/*  92 */         log("AFIRMT630AABR02 finish generating data");
/*     */       } 
/*  94 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  95 */       setReturnCode(-2);
/*  96 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 100 */           .getMessage() + "</font></h3>");
/*     */       
/* 102 */       logError(lockPDHEntityException.getMessage());
/* 103 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 104 */       setReturnCode(-2);
/* 105 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 107 */           .getMessage() + "</font></h3>");
/*     */       
/* 109 */       logError(updatePDHEntityException.getMessage());
/* 110 */     } catch (SBRException sBRException) {
/* 111 */       String str = sBRException.toString();
/* 112 */       int i = str.indexOf("(ok)");
/* 113 */       if (i < 0) {
/* 114 */         setReturnCode(-2);
/* 115 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 117 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 119 */         logError(sBRException.toString());
/*     */       } else {
/* 121 */         str = str.substring(0, i);
/* 122 */         println(replace(str, str2, "<br>"));
/*     */       } 
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
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 173 */     String str = "";
/* 174 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 176 */     while (paramString1.length() > 0 && i >= 0) {
/* 177 */       str = str + paramString1.substring(0, i) + paramString3;
/* 178 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 179 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 181 */     str = str + paramString1;
/* 182 */     return str;
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
/* 193 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 202 */     return "Software Support Product Initial ABR.";
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
/* 213 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 223 */     return new String("1.5");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 233 */     return "AFIRMT630AABR02.java,v 1.5 2006/03/03 19:23:25 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 241 */     return "AFIRMT630AABR02.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT630AABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */