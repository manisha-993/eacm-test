/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.ACTHWFCGEN30APDG;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
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
/*     */ public class HWFCGEN30APDGABR01
/*     */   extends PokBaseABR
/*     */ {
/*  41 */   public static final String ABR = new String("HWFCGEN30APDGABR01");
/*     */   
/*  43 */   private EntityGroup m_egParent = null;
/*  44 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  52 */     String str1 = null;
/*  53 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  56 */       start_ABRBuild();
/*     */       
/*  58 */       buildReportHeaderII();
/*     */       
/*  60 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  61 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  62 */       println("<br><b>Hardware Feature Conversion Data Generator Request: " + this.m_ei
/*     */           
/*  64 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  67 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  68 */       setReturnCode(0);
/*     */ 
/*     */       
/*  71 */       if (getReturnCode() == 0) {
/*  72 */         log("HWFCGEN30APDGABR01 generating data");
/*  73 */         ACTHWFCGEN30APDG aCTHWFCGEN30APDG = new ACTHWFCGEN30APDG(null, this.m_db, this.m_prof, "ACTHWFCGEN30APDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  79 */         aCTHWFCGEN30APDG.setEntityItem(this.m_ei);
/*  80 */         aCTHWFCGEN30APDG.executeAction(this.m_db, this.m_prof);
/*  81 */         log("HWFCGEN30APDGABR01 finish generating data");
/*     */       } 
/*  83 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  84 */       setReturnCode(-2);
/*  85 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  89 */           .getMessage() + "</font></h3>");
/*     */       
/*  91 */       logError(lockPDHEntityException.getMessage());
/*  92 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  93 */       setReturnCode(-2);
/*  94 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/*  96 */           .getMessage() + "</font></h3>");
/*     */       
/*  98 */       logError(updatePDHEntityException.getMessage());
/*  99 */     } catch (SBRException sBRException) {
/* 100 */       String str = sBRException.toString();
/* 101 */       int i = str.indexOf("(ok)");
/* 102 */       if (i < 0) {
/* 103 */         setReturnCode(-2);
/* 104 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 106 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
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
/*     */       
/* 153 */       buildReportFooter();
/*     */       
/* 155 */       if (!isReadOnly()) {
/* 156 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 162 */     String str = "";
/* 163 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 165 */     while (paramString1.length() > 0 && i >= 0) {
/* 166 */       str = str + paramString1.substring(0, i) + paramString3;
/* 167 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 168 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 170 */     str = str + paramString1;
/* 171 */     return str;
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
/* 182 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 191 */     return "Software Product Base and Initial ABR.";
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
/* 202 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 212 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 222 */     return "HWFCGEN30APDGABR01.java,v 1.4 2008/01/30 19:39:16 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 232 */     return "HWFCGEN30APDGABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\HWFCGEN30APDGABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */