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
/*     */ public class AFIRMT130AABR03
/*     */   extends PokBaseABR
/*     */ {
/*  41 */   public static final String ABR = new String("AFIRMT130AABR03");
/*     */   
/*  43 */   private EntityGroup m_egParent = null;
/*  44 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  53 */     SPDGActionItem sPDGActionItem = null;
/*  54 */     String str1 = null;
/*  55 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  58 */       start_ABRBuild();
/*     */       
/*  60 */       buildReportHeaderII();
/*     */       
/*  62 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  63 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  64 */       println("<br><b>Software Product Data Generator Request: " + this.m_ei
/*     */           
/*  66 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  69 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  70 */       setReturnCode(0);
/*     */ 
/*     */       
/*  73 */       if (getReturnCode() == 0) {
/*  74 */         log("AFIRMT130AABR03 generating data");
/*  75 */         sPDGActionItem = new SPDGActionItem(null, this.m_db, this.m_prof, "SWSPDG2");
/*     */         
/*  77 */         sPDGActionItem.setEntityItem(this.m_ei);
/*  78 */         sPDGActionItem.executeAction(this.m_db, this.m_prof);
/*  79 */         log("AFIRMT130AABR03 finish generating data");
/*     */       } 
/*  81 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  82 */       setReturnCode(-2);
/*  83 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  87 */           .getMessage() + "</font></h3>");
/*     */       
/*  89 */       logError(lockPDHEntityException.getMessage());
/*  90 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  91 */       setReturnCode(-2);
/*  92 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/*  94 */           .getMessage() + "</font></h3>");
/*     */       
/*  96 */       logError(updatePDHEntityException.getMessage());
/*  97 */     } catch (SBRException sBRException) {
/*  98 */       String str = sBRException.toString();
/*  99 */       int i = str.indexOf("(ok)");
/* 100 */       if (i < 0) {
/* 101 */         setReturnCode(-2);
/* 102 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 104 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 106 */         logError(sBRException.toString());
/*     */       } else {
/* 108 */         str = str.substring(0, i);
/* 109 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 111 */     } catch (Exception exception) {
/*     */       
/* 113 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 114 */       println("" + exception);
/* 115 */       exception.printStackTrace();
/*     */       
/* 117 */       if (getABRReturnCode() != -2) {
/* 118 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 121 */       println("<br /><b>" + 
/*     */           
/* 123 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 126 */               getABRDescription(), 
/* 127 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 130 */       log(
/* 131 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 134 */               getABRDescription(), 
/* 135 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 138 */       str1 = this.m_ei.toString();
/* 139 */       if (str1.length() > 64) {
/* 140 */         str1 = str1.substring(0, 64);
/*     */       }
/* 142 */       setDGTitle(str1);
/* 143 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 146 */       setDGString(getABRReturnCode());
/* 147 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 151 */       buildReportFooter();
/*     */       
/* 153 */       if (!isReadOnly()) {
/* 154 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 160 */     String str = "";
/* 161 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 163 */     while (paramString1.length() > 0 && i >= 0) {
/* 164 */       str = str + paramString1.substring(0, i) + paramString3;
/* 165 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 166 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 168 */     str = str + paramString1;
/* 169 */     return str;
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
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 189 */     return "Software Optional Feature Base and Initial ABR.";
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
/* 200 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 210 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 220 */     return "AFIRMT130AABR03.java,v 1.4 2006/03/03 19:23:24 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 228 */     return "AFIRMT130AABR03.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT130AABR03.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */