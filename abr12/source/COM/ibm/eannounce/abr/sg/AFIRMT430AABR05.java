/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWSUBSFEATINI30APDG;
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
/*     */ public class AFIRMT430AABR05
/*     */   extends PokBaseABR
/*     */ {
/*  49 */   public static final String ABR = new String("AFIRMT430AABR05");
/*     */   
/*  51 */   private EntityGroup m_egParent = null;
/*  52 */   private EntityItem m_ei = null;
/*     */ 
/*     */   
/*  55 */   private PDGUtility m_utility = new PDGUtility();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  61 */     SWSUBSFEATINI30APDG sWSUBSFEATINI30APDG = null;
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
/*  83 */           log("AFIRMT430AABR05 generating data");
/*  84 */           sWSUBSFEATINI30APDG = new SWSUBSFEATINI30APDG(null, this.m_db, this.m_prof, "SWSUBSFEATINI30APDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  90 */           sWSUBSFEATINI30APDG.setEntityItem(this.m_ei);
/*  91 */           sWSUBSFEATINI30APDG.executeAction(this.m_db, this.m_prof);
/*  92 */           log("AFIRMT430AABR05 finish generating data");
/*     */         } 
/*  94 */       } catch (SBRException sBRException) {
/*  95 */         OPICMList oPICMList = new OPICMList();
/*  96 */         StringBuffer stringBuffer = new StringBuffer();
/*  97 */         String str = sBRException.toString();
/*  98 */         int i = str.indexOf("(ok)");
/*  99 */         if (i < 0) {
/* 100 */           setReturnCode(-2);
/* 101 */           println("<h3><font color=red>Generate Data error: " + 
/*     */               
/* 103 */               replace(str, str2, "<br>") + "</font></h3>");
/*     */           
/* 105 */           logError(sBRException.toString());
/*     */         } else {
/* 107 */           str = str.substring(0, i);
/* 108 */           println(replace(str, str2, "<br>"));
/*     */         } 
/* 110 */         stringBuffer.append(sBRException.toString());
/* 111 */         oPICMList.put("AFPDGERRORMSG", "AFPDGERRORMSG= " + stringBuffer);
/* 112 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       } 
/* 114 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 115 */       setReturnCode(-2);
/* 116 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 120 */           .getMessage() + "</font></h3>");
/*     */       
/* 122 */       logError(lockPDHEntityException.getMessage());
/* 123 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 124 */       setReturnCode(-2);
/* 125 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 127 */           .getMessage() + "</font></h3>");
/*     */       
/* 129 */       logError(updatePDHEntityException.getMessage());
/* 130 */     } catch (SBRException sBRException) {
/* 131 */       String str = sBRException.toString();
/* 132 */       int i = str.indexOf("(ok)");
/* 133 */       if (i < 0) {
/* 134 */         setReturnCode(-2);
/* 135 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 137 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 139 */         logError(sBRException.toString());
/*     */       } else {
/* 141 */         str = str.substring(0, i);
/* 142 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 144 */     } catch (Exception exception) {
/*     */       
/* 146 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 147 */       println("" + exception);
/* 148 */       exception.printStackTrace();
/*     */       
/* 150 */       if (getABRReturnCode() != -2) {
/* 151 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 154 */       println("<br /><b>" + 
/*     */           
/* 156 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 159 */               getABRDescription(), 
/* 160 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 163 */       log(
/* 164 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 167 */               getABRDescription(), 
/* 168 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 171 */       str1 = this.m_ei.toString();
/* 172 */       if (str1.length() > 64) {
/* 173 */         str1 = str1.substring(0, 64);
/*     */       }
/* 175 */       setDGTitle(str1);
/* 176 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 179 */       setDGString(getABRReturnCode());
/* 180 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 184 */       buildReportFooter();
/*     */       
/* 186 */       if (!isReadOnly()) {
/* 187 */         clearSoftLock();
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
/* 201 */     String str = "";
/* 202 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 204 */     while (paramString1.length() > 0 && i >= 0) {
/* 205 */       str = str + paramString1.substring(0, i) + paramString3;
/* 206 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 207 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 209 */     str = str + paramString1;
/* 210 */     return str;
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
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 230 */     return "Software Subscription Feature Initial ABR.";
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
/* 241 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 250 */     return new String("1.7");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 259 */     return "AFIRMT430AABR05.java,v 1.7 2006/03/03 19:23:25 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 268 */     return "AFIRMT430AABR05.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT430AABR05.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */