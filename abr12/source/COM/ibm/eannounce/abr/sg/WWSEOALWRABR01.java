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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WWSEOALWRABR01
/*     */   extends PokBaseABR
/*     */ {
/*  45 */   public static final String ABR = new String("WWSEOALWRABR01");
/*     */   
/*  47 */   private EntityGroup m_egParent = null;
/*  48 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  57 */     String str1 = null;
/*  58 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  61 */       start_ABRBuild();
/*     */       
/*  63 */       buildReportHeaderII();
/*     */       
/*  65 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  66 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  67 */       println("<br><b>WWSEO ALWR: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  69 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  70 */       setReturnCode(0);
/*     */       
/*  72 */       WWSEOALWRPDG wWSEOALWRPDG = new WWSEOALWRPDG(null, this.m_db, this.m_prof, "WWSEOALWRPDG");
/*     */       
/*  74 */       wWSEOALWRPDG.setEntityItem(this.m_ei);
/*  75 */       wWSEOALWRPDG.setABReList(this.m_elist);
/*  76 */       wWSEOALWRPDG.executeAction(this.m_db, this.m_prof);
/*  77 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  78 */       setReturnCode(-2);
/*  79 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  83 */           .getMessage() + "</font></h3>");
/*     */       
/*  85 */       logError(lockPDHEntityException.getMessage());
/*  86 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  87 */       setReturnCode(-2);
/*  88 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/*  90 */           .getMessage() + "</font></h3>");
/*     */       
/*  92 */       logError(updatePDHEntityException.getMessage());
/*  93 */     } catch (SBRException sBRException) {
/*  94 */       String str = sBRException.toString();
/*  95 */       int i = str.indexOf("(ok)");
/*  96 */       if (i < 0) {
/*  97 */         setReturnCode(-2);
/*  98 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 100 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 102 */         logError(sBRException.toString());
/*     */       } else {
/* 104 */         str = str.substring(0, i);
/* 105 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 107 */     } catch (Exception exception) {
/*     */       
/* 109 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 110 */       println("" + exception);
/* 111 */       exception.printStackTrace();
/*     */       
/* 113 */       if (getABRReturnCode() != -2) {
/* 114 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 117 */       println("<br /><b>" + 
/*     */           
/* 119 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 122 */               getABRDescription(), 
/* 123 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 126 */       log(
/* 127 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 130 */               getABRDescription(), 
/* 131 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 134 */       str1 = this.m_ei.toString();
/* 135 */       if (str1.length() > 64) {
/* 136 */         str1 = str1.substring(0, 64);
/*     */       }
/* 138 */       setDGTitle(str1);
/* 139 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 142 */       setDGString(getABRReturnCode());
/* 143 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 147 */       buildReportFooter();
/*     */       
/* 149 */       if (!isReadOnly()) {
/* 150 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 156 */     String str = "";
/* 157 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 159 */     while (paramString1.length() > 0 && i >= 0) {
/* 160 */       str = str + paramString1.substring(0, i) + paramString3;
/* 161 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 162 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 164 */     str = str + paramString1;
/* 165 */     return str;
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
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 185 */     return "WWSEO ALWR With CD ABR.";
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
/* 196 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 206 */     return new String("1.5");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 216 */     return "WWSEOALWRABR01.java,v 1.5 2008/01/30 19:39:14 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 226 */     return "WWSEOALWRABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWSEOALWRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */