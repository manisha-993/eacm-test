/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWCOPYBILLINGPDG;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AFCOPYBILLINGABR01
/*     */   extends PokBaseABR
/*     */ {
/*  43 */   public static final String ABR = new String("AFCOPYBILLINGABR01");
/*     */   
/*  45 */   private EntityGroup m_egParent = null;
/*  46 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  53 */     Vector vector = null;
/*  54 */     SWCOPYBILLINGPDG sWCOPYBILLINGPDG = null;
/*  55 */     StringBuffer stringBuffer = null;
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
/*  66 */       println("<br><b>Commercial Offering: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  68 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  69 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       vector = getChildrenEntityIds(this.m_ei
/*  75 */           .getEntityType(), this.m_ei
/*  76 */           .getEntityID(), "ORDEROF", "AFCOFOOF");
/*     */ 
/*     */       
/*  79 */       if (vector.size() <= 0) {
/*  80 */         println("<br /><font color=red>Failed. There is no ORDEROF selected</font>");
/*  81 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/*  85 */       if (getReturnCode() == 0) {
/*  86 */         log("AFCOPYBILLINGABR01 generating data");
/*  87 */         sWCOPYBILLINGPDG = new SWCOPYBILLINGPDG(null, this.m_db, this.m_prof, "SWCOPYBILLINGPDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  93 */         sWCOPYBILLINGPDG.setEntityItem(this.m_ei);
/*  94 */         sWCOPYBILLINGPDG.setABReList(this.m_elist);
/*  95 */         sWCOPYBILLINGPDG.executeAction(this.m_db, this.m_prof);
/*  96 */         stringBuffer = sWCOPYBILLINGPDG.getActivities();
/*  97 */         println("</br></br/><b>Generated Data:</b>");
/*  98 */         println("<br/>" + stringBuffer.toString());
/*     */         
/* 100 */         log("AFCOPYBILLINGABR01 finish generating data");
/*     */       } 
/* 102 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 103 */       setReturnCode(-2);
/* 104 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 108 */           .getMessage() + "</font></h3>");
/*     */       
/* 110 */       logError(lockPDHEntityException.getMessage());
/* 111 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 112 */       setReturnCode(-2);
/* 113 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 115 */           .getMessage() + "</font></h3>");
/*     */       
/* 117 */       logError(updatePDHEntityException.getMessage());
/* 118 */     } catch (SBRException sBRException) {
/* 119 */       String str = sBRException.toString();
/* 120 */       int i = str.indexOf("(ok)");
/* 121 */       if (i < 0) {
/* 122 */         setReturnCode(-2);
/* 123 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 125 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 127 */         logError(sBRException.toString());
/*     */       } else {
/* 129 */         str = str.substring(0, i);
/* 130 */         println(replace(str, str2, "<br>"));
/*     */       } 
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
/* 142 */       println("<br /><b>" + 
/*     */           
/* 144 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 147 */               getABRDescription(), 
/* 148 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 151 */       log(
/* 152 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 155 */               getABRDescription(), 
/* 156 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 159 */       str1 = this.m_ei.toString();
/* 160 */       if (str1.length() > 64) {
/* 161 */         str1 = str1.substring(0, 64);
/*     */       }
/* 163 */       setDGTitle(str1);
/* 164 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 167 */       setDGString(getABRReturnCode());
/* 168 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 172 */       buildReportFooter();
/*     */       
/* 174 */       if (!isReadOnly()) {
/* 175 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 181 */     String str = "";
/* 182 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 184 */     while (paramString1.length() > 0 && i >= 0) {
/* 185 */       str = str + paramString1.substring(0, i) + paramString3;
/* 186 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 187 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 189 */     str = str + paramString1;
/* 190 */     return str;
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
/* 201 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 210 */     return "Software Copy Billing ABR.";
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
/* 221 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 231 */     return new String("1.6");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 241 */     return "AFCOPYBILLINGABR01.java,v 1.6 2008/01/30 19:39:15 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 249 */     return "AFCOPYBILLINGABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFCOPYBILLINGABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */