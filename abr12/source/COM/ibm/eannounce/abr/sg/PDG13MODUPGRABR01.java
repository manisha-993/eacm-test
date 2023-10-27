/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.ACTPDG13MODUPGR;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PDG13MODUPGRABR01
/*     */   extends PokBaseABR
/*     */ {
/*  51 */   public static final String ABR = new String("PDG13MODUPGRABR01");
/*     */   
/*  53 */   private EntityGroup m_egParent = null;
/*  54 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
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
/*  73 */       println("<br><b>Hardware Model Upgrade Data Generator Request: " + this.m_ei
/*     */           
/*  75 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  78 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  79 */       setReturnCode(0);
/*     */ 
/*     */       
/*  82 */       if (getReturnCode() == 0) {
/*  83 */         log("PDG13MODUPGRABR01 generating data");
/*  84 */         ACTPDG13MODUPGR aCTPDG13MODUPGR = new ACTPDG13MODUPGR(null, this.m_db, this.m_prof, "ACTPDG13MODUPGR");
/*     */         
/*  86 */         aCTPDG13MODUPGR.setEntityItem(this.m_ei);
/*  87 */         aCTPDG13MODUPGR.executeAction(this.m_db, this.m_prof);
/*  88 */         log("PDG13MODUPGRABR01 finish generating data");
/*     */       } 
/*  90 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  91 */       setReturnCode(-2);
/*  92 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  96 */           .getMessage() + "</font></h3>");
/*     */       
/*  98 */       logError(lockPDHEntityException.getMessage());
/*  99 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 100 */       setReturnCode(-2);
/* 101 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 103 */           .getMessage() + "</font></h3>");
/*     */       
/* 105 */       logError(updatePDHEntityException.getMessage());
/* 106 */     } catch (SBRException sBRException) {
/* 107 */       String str = sBRException.toString();
/* 108 */       int i = str.indexOf("(ok)");
/* 109 */       if (i < 0) {
/* 110 */         setReturnCode(-2);
/* 111 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 113 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 115 */         logError(sBRException.toString());
/*     */       } else {
/* 117 */         str = str.substring(0, i);
/* 118 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 120 */     } catch (Exception exception) {
/*     */       
/* 122 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 123 */       println("" + exception);
/* 124 */       exception.printStackTrace();
/*     */       
/* 126 */       if (getABRReturnCode() != -2) {
/* 127 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 130 */       println("<br /><b>" + 
/*     */           
/* 132 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 135 */               getABRDescription(), 
/* 136 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 139 */       log(
/* 140 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 143 */               getABRDescription(), 
/* 144 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 147 */       str1 = this.m_ei.toString();
/* 148 */       if (str1.length() > 64) {
/* 149 */         str1 = str1.substring(0, 64);
/*     */       }
/* 151 */       setDGTitle(str1);
/* 152 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 155 */       setDGString(getABRReturnCode());
/* 156 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 160 */       buildReportFooter();
/*     */       
/* 162 */       if (!isReadOnly()) {
/* 163 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 169 */     String str = "";
/* 170 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 172 */     while (paramString1.length() > 0 && i >= 0) {
/* 173 */       str = str + paramString1.substring(0, i) + paramString3;
/* 174 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 175 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 177 */     str = str + paramString1;
/* 178 */     return str;
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
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 198 */     return "Hardware Upgrade Model ABR";
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
/* 209 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 219 */     return new String("1.7");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 229 */     return "PDG13MODUPGRABR01.java,v 1.7 2008/01/30 19:39:15 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 239 */     return "PDG13MODUPGRABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\PDG13MODUPGRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */