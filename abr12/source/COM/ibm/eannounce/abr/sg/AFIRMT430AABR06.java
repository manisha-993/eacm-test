/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWSUBSFEATSUPP30APDG;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
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
/*     */ public class AFIRMT430AABR06
/*     */   extends PokBaseABR
/*     */ {
/*  49 */   public static final String ABR = new String("AFIRMT430AABR06");
/*     */   
/*  51 */   private EntityGroup m_egParent = null;
/*  52 */   private EntityItem m_ei = null;
/*     */   
/*  54 */   private PDGUtility m_utility = new PDGUtility();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  62 */     SWSUBSFEATSUPP30APDG sWSUBSFEATSUPP30APDG = null;
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
/*  73 */       println("<br><b>Software Subscription Data Generator Request: " + this.m_ei
/*     */           
/*  75 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  78 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  79 */       setReturnCode(0);
/*     */ 
/*     */       
/*     */       try {
/*  83 */         if (getReturnCode() == 0) {
/*  84 */           log("AFIRMT430AABR06 generating data");
/*  85 */           sWSUBSFEATSUPP30APDG = new SWSUBSFEATSUPP30APDG(null, this.m_db, this.m_prof, "SWSUBSFEATSUPP30APDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  91 */           sWSUBSFEATSUPP30APDG.setEntityItem(this.m_ei);
/*  92 */           sWSUBSFEATSUPP30APDG.executeAction(this.m_db, this.m_prof);
/*  93 */           log("AFIRMT430AABR06 finish generating data");
/*     */         } 
/*  95 */       } catch (SBRException sBRException) {
/*  96 */         OPICMList oPICMList = new OPICMList();
/*  97 */         StringBuffer stringBuffer = new StringBuffer();
/*  98 */         String str = sBRException.toString();
/*  99 */         int i = str.indexOf("(ok)");
/* 100 */         if (i < 0) {
/* 101 */           setReturnCode(-2);
/* 102 */           println("<h3><font color=red>Generate Data error: " + 
/*     */               
/* 104 */               replace(str, str2, "<br>") + "</font></h3>");
/*     */           
/* 106 */           logError(sBRException.toString());
/*     */         } else {
/* 108 */           str = str.substring(0, i);
/* 109 */           println(replace(str, str2, "<br>"));
/*     */         } 
/* 111 */         stringBuffer.append(sBRException.toString());
/* 112 */         oPICMList.put("AFPDGERRORMSG", "AFPDGERRORMSG= " + stringBuffer);
/* 113 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/*     */     
/* 116 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 117 */       setReturnCode(-2);
/* 118 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 122 */           .getMessage() + "</font></h3>");
/*     */       
/* 124 */       logError(lockPDHEntityException.getMessage());
/* 125 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 126 */       setReturnCode(-2);
/* 127 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 129 */           .getMessage() + "</font></h3>");
/*     */       
/* 131 */       logError(updatePDHEntityException.getMessage());
/* 132 */     } catch (Exception exception) {
/*     */       
/* 134 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 135 */       println("" + exception);
/* 136 */       exception.printStackTrace();
/*     */       
/* 138 */       if (getABRReturnCode() != -2) {
/* 139 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 143 */       println("<br /><b>" + 
/*     */           
/* 145 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 148 */               getABRDescription(), 
/* 149 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 152 */       log(
/* 153 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 156 */               getABRDescription(), 
/* 157 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 160 */       str1 = this.m_ei.toString();
/* 161 */       if (str1.length() > 64) {
/* 162 */         str1 = str1.substring(0, 64);
/*     */       }
/* 164 */       setDGTitle(str1);
/* 165 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 168 */       setDGString(getABRReturnCode());
/* 169 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 173 */       buildReportFooter();
/*     */       
/* 175 */       if (!isReadOnly()) {
/* 176 */         clearSoftLock();
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
/* 190 */     String str = "";
/* 191 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 193 */     while (paramString1.length() > 0 && i >= 0) {
/* 194 */       str = str + paramString1.substring(0, i) + paramString3;
/* 195 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 196 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 198 */     str = str + paramString1;
/* 199 */     return str;
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
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 219 */     return "Software Subscription Feature Support ABR.";
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
/* 230 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 239 */     return new String("1.7");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 248 */     return "AFIRMT430AABR06.java,v 1.7 2006/03/03 19:23:25 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 257 */     return "AFIRMT430AABR06.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT430AABR06.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */