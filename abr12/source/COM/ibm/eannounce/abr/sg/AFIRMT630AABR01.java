/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWSUPPPROD30APDG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AFIRMT630AABR01
/*     */   extends PokBaseABR
/*     */ {
/*  44 */   public static final String ABR = new String("AFIRMT630AABR01");
/*     */   
/*  46 */   private EntityGroup m_egParent = null;
/*  47 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  55 */     SWSUPPPROD30APDG sWSUPPPROD30APDG = null;
/*  56 */     String str1 = null;
/*  57 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  60 */       start_ABRBuild();
/*     */       
/*  62 */       buildReportHeaderII();
/*     */       
/*  64 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  65 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  66 */       println("<br><b>Software Support Data Generator Request: " + this.m_ei
/*     */           
/*  68 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  71 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  72 */       setReturnCode(0);
/*     */ 
/*     */       
/*  75 */       if (getReturnCode() == 0) {
/*  76 */         log("AFIRMT630AABR01 generating data");
/*  77 */         sWSUPPPROD30APDG = new SWSUPPPROD30APDG(null, this.m_db, this.m_prof, "SWSUPPPROD30APDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  83 */         sWSUPPPROD30APDG.setEntityItem(this.m_ei);
/*  84 */         sWSUPPPROD30APDG.executeAction(this.m_db, this.m_prof);
/*  85 */         log("AFIRMT630AABR01 finish generating data");
/*     */       } 
/*  87 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  88 */       setReturnCode(-2);
/*  89 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  93 */           .getMessage() + "</font></h3>");
/*     */       
/*  95 */       logError(lockPDHEntityException.getMessage());
/*  96 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  97 */       setReturnCode(-2);
/*  98 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 100 */           .getMessage() + "</font></h3>");
/*     */       
/* 102 */       logError(updatePDHEntityException.getMessage());
/* 103 */     } catch (SBRException sBRException) {
/* 104 */       String str = sBRException.toString();
/* 105 */       int i = str.indexOf("(ok)");
/* 106 */       if (i < 0) {
/* 107 */         setReturnCode(-2);
/* 108 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 110 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 112 */         logError(sBRException.toString());
/*     */       } else {
/* 114 */         str = str.substring(0, i);
/* 115 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 117 */     } catch (Exception exception) {
/*     */       
/* 119 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 120 */       println("" + exception);
/* 121 */       exception.printStackTrace();
/*     */       
/* 123 */       if (getABRReturnCode() != -2) {
/* 124 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
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
/* 141 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 144 */       str1 = this.m_ei.toString();
/* 145 */       if (str1.length() > 64) {
/* 146 */         str1 = str1.substring(0, 64);
/*     */       }
/* 148 */       setDGTitle(str1);
/* 149 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 152 */       setDGString(getABRReturnCode());
/* 153 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 157 */       buildReportFooter();
/*     */       
/* 159 */       if (!isReadOnly()) {
/* 160 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 166 */     String str = "";
/* 167 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 169 */     while (paramString1.length() > 0 && i >= 0) {
/* 170 */       str = str + paramString1.substring(0, i) + paramString3;
/* 171 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 172 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 174 */     str = str + paramString1;
/* 175 */     return str;
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
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 195 */     return "Software Support Product Base ABR.";
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
/* 206 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 216 */     return new String("1.5");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 226 */     return "AFIRMT630AABR01.java,v 1.5 2006/03/03 19:23:25 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 234 */     return "AFIRMT630AABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT630AABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */