/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWOFUPGRADE30APDG;
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
/*     */ public class AFIRMT130AABR04
/*     */   extends PokBaseABR
/*     */ {
/*  41 */   public static final String ABR = new String("AFIRMT130AABR04");
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
/*  53 */     SWOFUPGRADE30APDG sWOFUPGRADE30APDG = null;
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
/*  74 */         log("AFIRMT130AABR04 generating data");
/*  75 */         sWOFUPGRADE30APDG = new SWOFUPGRADE30APDG(null, this.m_db, this.m_prof, "SWOFUPGRADE30APDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  81 */         sWOFUPGRADE30APDG.setEntityItem(this.m_ei);
/*  82 */         sWOFUPGRADE30APDG.executeAction(this.m_db, this.m_prof);
/*  83 */         log("AFIRMT130AABR04 finish generating data");
/*     */       } 
/*  85 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  86 */       setReturnCode(-2);
/*  87 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  91 */           .getMessage() + "</font></h3>");
/*     */       
/*  93 */       logError(lockPDHEntityException.getMessage());
/*  94 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  95 */       setReturnCode(-2);
/*  96 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/*  98 */           .getMessage() + "</font></h3>");
/*     */       
/* 100 */       logError(updatePDHEntityException.getMessage());
/* 101 */     } catch (SBRException sBRException) {
/* 102 */       String str = sBRException.toString();
/* 103 */       int i = str.indexOf("(ok)");
/* 104 */       if (i < 0) {
/* 105 */         setReturnCode(-2);
/* 106 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 108 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 110 */         logError(sBRException.toString());
/*     */       } else {
/* 112 */         str = str.substring(0, i);
/* 113 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 115 */     } catch (Exception exception) {
/*     */       
/* 117 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 118 */       println("" + exception);
/* 119 */       exception.printStackTrace();
/*     */       
/* 121 */       if (getABRReturnCode() != -2) {
/* 122 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 125 */       println("<br /><b>" + 
/*     */           
/* 127 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 130 */               getABRDescription(), 
/* 131 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 134 */       log(
/* 135 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 138 */               getABRDescription(), 
/* 139 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 142 */       str1 = this.m_ei.toString();
/* 143 */       if (str1.length() > 64) {
/* 144 */         str1 = str1.substring(0, 64);
/*     */       }
/* 146 */       setDGTitle(str1);
/* 147 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 150 */       setDGString(getABRReturnCode());
/* 151 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 155 */       buildReportFooter();
/*     */       
/* 157 */       if (!isReadOnly()) {
/* 158 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 164 */     String str = "";
/* 165 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 167 */     while (paramString1.length() > 0 && i >= 0) {
/* 168 */       str = str + paramString1.substring(0, i) + paramString3;
/* 169 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 170 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 172 */     str = str + paramString1;
/* 173 */     return str;
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
/* 184 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 193 */     return "Software Optional Feature Upgrade ABR.";
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
/* 204 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 214 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 224 */     return "AFIRMT130AABR04.java,v 1.4 2006/03/03 19:23:25 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 232 */     return "AFIRMT130AABR04.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT130AABR04.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */