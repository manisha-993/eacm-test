/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.WWSEOALWRPDG;
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
/*     */ public class WWSEOALWRABR02
/*     */   extends PokBaseABR
/*     */ {
/*  39 */   public static final String ABR = new String("WWSEOALWRABR02");
/*     */   
/*  41 */   EntityGroup m_egParent = null;
/*  42 */   EntityItem m_ei = null;
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/*  47 */       StringBuffer stringBuffer = new StringBuffer();
/*  48 */       start_ABRBuild();
/*     */       
/*  50 */       buildReportHeaderII();
/*     */       
/*  52 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  53 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  54 */       println("<br><b>WWSEO ALWR: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  56 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  57 */       setReturnCode(0);
/*     */       
/*  59 */       WWSEOALWRPDG wWSEOALWRPDG = new WWSEOALWRPDG(null, this.m_db, this.m_prof, "WWSEOALWRPDG");
/*     */       
/*  61 */       wWSEOALWRPDG.setEntityItem(this.m_ei);
/*  62 */       wWSEOALWRPDG.setABReList(this.m_elist);
/*  63 */       wWSEOALWRPDG.executeAction(this.m_db, this.m_prof);
/*  64 */       stringBuffer = wWSEOALWRPDG.getActivities();
/*  65 */       println("</br></br/><b>Generated Data:</b>");
/*  66 */       println("<br/>" + stringBuffer.toString());
/*  67 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  68 */       setReturnCode(-2);
/*  69 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  73 */           .getMessage() + "</font></h3>");
/*     */       
/*  75 */       logError(lockPDHEntityException.getMessage());
/*  76 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  77 */       setReturnCode(-2);
/*  78 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/*  80 */           .getMessage() + "</font></h3>");
/*     */       
/*  82 */       logError(updatePDHEntityException.getMessage());
/*  83 */     } catch (SBRException sBRException) {
/*  84 */       String str2 = sBRException.toString();
/*  85 */       int i = str2.indexOf("(ok)");
/*  86 */       if (i < 0) {
/*  87 */         setReturnCode(-2);
/*  88 */         println("<h3><font color=red>Generate Data error: " + replace(str2, "\n", "<br>") + "</font></h3>");
/*  89 */         logError(sBRException.toString());
/*     */       } else {
/*  91 */         str2 = str2.substring(0, i);
/*  92 */         println(replace(str2, "\n", "<br>"));
/*     */       } 
/*  94 */     } catch (Exception exception) {
/*     */       
/*  96 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/*  97 */       println("" + exception);
/*  98 */       exception.printStackTrace();
/*     */       
/* 100 */       if (getABRReturnCode() != -2) {
/* 101 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 104 */       println("<br /><b>" + 
/*     */           
/* 106 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 109 */               getABRDescription(), 
/* 110 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 113 */       log(
/* 114 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 117 */               getABRDescription(), 
/* 118 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 121 */       String str = this.m_ei.toString();
/* 122 */       if (str.length() > 64) {
/* 123 */         str = str.substring(0, 64);
/*     */       }
/* 125 */       setDGTitle(str);
/* 126 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 129 */       setDGString(getABRReturnCode());
/* 130 */       printDGSubmitString();
/*     */ 
/*     */       
/* 133 */       buildReportFooter();
/*     */       
/* 135 */       if (!isReadOnly()) {
/* 136 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 142 */     String str = "";
/* 143 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 145 */     while (paramString1.length() > 0 && i >= 0) {
/* 146 */       str = str + paramString1.substring(0, i) + paramString3;
/* 147 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 148 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 150 */     str = str + paramString1;
/* 151 */     return str;
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
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 171 */     return "WWSEO ALWR No CD ABR.";
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
/* 182 */     return "";
/*     */   }
/*     */   
/*     */   public String getRevision() {
/* 186 */     return new String("1.5");
/*     */   }
/*     */   
/*     */   public static String getVersion() {
/* 190 */     return "WWSEOALWRABR02.java,v 1.5 2008/01/30 19:39:17 wendy Exp";
/*     */   }
/*     */   
/*     */   public String getABRVersion() {
/* 194 */     return "WWSEOALWRABR02.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWSEOALWRABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */