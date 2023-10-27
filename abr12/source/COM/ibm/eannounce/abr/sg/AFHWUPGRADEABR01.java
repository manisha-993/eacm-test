/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.HWUpgradePIPDG;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AFHWUPGRADEABR01
/*     */   extends PokBaseABR
/*     */ {
/*  63 */   public static final String DEF_NOT_POPULATED_HTML = new String("");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final String ABR = new String("AFHWUPGRADEABR01");
/*     */   
/*  70 */   private EntityGroup m_egParent = null;
/*  71 */   private EntityItem m_ei = null;
/*     */   private boolean m_bPDGComplete = false;
/*  73 */   private int m_iExeCount = 0;
/*  74 */   private HWUpgradePIPDG m_pdg = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  81 */     String str1 = null;
/*  82 */     int i = 0;
/*  83 */     String str2 = null;
/*  84 */     String str3 = System.getProperty("line.separator");
/*     */     try {
/*  86 */       this.m_iExeCount++;
/*     */ 
/*     */       
/*  89 */       if (this.m_iExeCount == 1) {
/*  90 */         start_ABRBuild();
/*     */         
/*  92 */         buildReportHeaderII();
/*     */         
/*  94 */         this.m_egParent = this.m_elist.getParentEntityGroup();
/*  95 */         this.m_ei = this.m_egParent.getEntityItem(0);
/*  96 */         println("<br><b>Hardware Upgrade PDG Request: " + this.m_ei
/*     */             
/*  98 */             .getKey() + "</b>");
/*     */ 
/*     */         
/* 101 */         printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*     */         
/* 103 */         setReturnCode(0);
/*     */       } 
/*     */ 
/*     */       
/* 107 */       if (getReturnCode() == 0) {
/* 108 */         log("AFHWUPGRADEABR01 generating data");
/* 109 */         if (this.m_iExeCount == 1) {
/* 110 */           log("AFHWUPGRADEABR01 first time generating");
/* 111 */           this.m_pdg = new HWUpgradePIPDG(null, this.m_db, this.m_prof, "HWUPPIPDG");
/* 112 */           this.m_pdg.setEntityItem(this.m_ei);
/* 113 */           this.m_pdg.executeAction(this.m_db, this.m_prof);
/*     */         } else {
/* 115 */           log("AFHWUPGRADEABR01 " + this.m_iExeCount + " time generating");
/* 116 */           this.m_pdg.checkMissingDataAgain(this.m_db, this.m_prof, true);
/*     */         } 
/*     */ 
/*     */         
/* 120 */         if (this.m_iExeCount > 1) {
/* 121 */           this.m_pdg.savePDGStatus(this.m_db, this.m_prof);
/*     */         }
/* 123 */         this.m_bPDGComplete = true;
/* 124 */         log("AFHWUPGRADEABR01 finish generating data");
/*     */       } 
/*     */       
/* 127 */       println("<br /><b>" + 
/*     */           
/* 129 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 132 */               getABRDescription(), 
/* 133 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 136 */       log(
/* 137 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 140 */               getABRDescription(), 
/* 141 */               (getReturnCode() == 0) ? "Passed" : "Failed" }));
/* 142 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 143 */       this.m_bPDGComplete = true;
/* 144 */       setReturnCode(-2);
/* 145 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 149 */           .getMessage() + "</font></h3>");
/*     */       
/* 151 */       logError(lockPDHEntityException.getMessage());
/* 152 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 153 */       this.m_bPDGComplete = true;
/* 154 */       setReturnCode(-2);
/* 155 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 157 */           .getMessage() + "</font></h3>");
/*     */       
/* 159 */       logError(updatePDHEntityException.getMessage());
/* 160 */     } catch (SBRException sBRException) {
/* 161 */       this.m_bPDGComplete = true;
/* 162 */       str1 = sBRException.toString();
/* 163 */       i = str1.indexOf("(ok)");
/* 164 */       if (i < 0) {
/* 165 */         setReturnCode(-2);
/* 166 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 168 */             replace(str1, str3, "<br>") + "</font></h3>");
/*     */         
/* 170 */         logError(sBRException.toString());
/*     */       } else {
/* 172 */         str1 = str1.substring(0, i);
/* 173 */         println("<h3> " + replace(str1, str3, "<br>") + "</h3>");
/*     */       } 
/* 175 */     } catch (Exception exception) {
/* 176 */       this.m_bPDGComplete = true;
/*     */       
/* 178 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 179 */       println("" + exception);
/* 180 */       exception.printStackTrace();
/*     */       
/* 182 */       if (getABRReturnCode() != -2) {
/* 183 */         setReturnCode(-3);
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 188 */       if (!this.m_bPDGComplete) {
/* 189 */         setReturnCode(-3);
/* 190 */         println("<h3><font color=red>Generating Data is not complete. </font></h3>");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 197 */       str2 = this.m_ei.toString();
/* 198 */       if (str2.length() > 64) {
/* 199 */         str2 = str2.substring(0, 64);
/*     */       }
/* 201 */       setDGTitle(str2);
/* 202 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 205 */       setDGString(getABRReturnCode());
/* 206 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 210 */       buildReportFooter();
/*     */       
/* 212 */       if (!isReadOnly()) {
/* 213 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 219 */     String str = "";
/* 220 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 222 */     while (paramString1.length() > 0 && i >= 0) {
/* 223 */       str = str + paramString1.substring(0, i) + paramString3;
/* 224 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 225 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 227 */     str = str + paramString1;
/* 228 */     return str;
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
/* 239 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 248 */     return "Hardware Upgrade Proc|? to Proc|? ABR.";
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
/* 259 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 269 */     return new String("1.12");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 279 */     return "AFHWUPGRADEABR01.java,v 1.12 2006/03/03 19:23:24 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 287 */     return "AFHWUPGRADEABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFHWUPGRADEABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */