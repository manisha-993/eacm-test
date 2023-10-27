/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.SWPRODUPGRADEPDG;
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
/*     */ public class AFIRMT1ABR02
/*     */   extends PokBaseABR
/*     */ {
/*  44 */   public static final String ABR = new String("AFIRMT1ABR02");
/*     */   
/*  46 */   private EntityGroup m_egParent = null;
/*  47 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  56 */     SWPRODUPGRADEPDG sWPRODUPGRADEPDG = null;
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
/*  67 */       println("<br><b>Software Product Data Generator Request: " + this.m_ei
/*     */           
/*  69 */           .getKey() + "</b>");
/*     */ 
/*     */       
/*  72 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  73 */       setReturnCode(0);
/*     */ 
/*     */       
/*  76 */       if (getReturnCode() == 0) {
/*  77 */         log("AFIRMT1ABR02 generating data");
/*  78 */         sWPRODUPGRADEPDG = new SWPRODUPGRADEPDG(null, this.m_db, this.m_prof, "SWPRODUPGRADEPDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  84 */         sWPRODUPGRADEPDG.setEntityItem(this.m_ei);
/*  85 */         sWPRODUPGRADEPDG.executeAction(this.m_db, this.m_prof);
/*  86 */         log("AFIRMT1ABR02 finish generating data");
/*     */       } 
/*  88 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  89 */       setReturnCode(-2);
/*  90 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  94 */           .getMessage() + "</font></h3>");
/*     */       
/*  96 */       logError(lockPDHEntityException.getMessage());
/*  97 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  98 */       setReturnCode(-2);
/*  99 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 101 */           .getMessage() + "</font></h3>");
/*     */       
/* 103 */       logError(updatePDHEntityException.getMessage());
/* 104 */     } catch (SBRException sBRException) {
/* 105 */       String str = sBRException.toString();
/* 106 */       int i = str.indexOf("(ok)");
/* 107 */       if (i < 0) {
/* 108 */         setReturnCode(-2);
/* 109 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 111 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 113 */         logError(sBRException.toString());
/*     */       } else {
/* 115 */         str = str.substring(0, i);
/* 116 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 118 */     } catch (Exception exception) {
/*     */       
/* 120 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 121 */       println("" + exception);
/* 122 */       exception.printStackTrace();
/*     */       
/* 124 */       if (getABRReturnCode() != -2) {
/* 125 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 128 */       println("<br /><b>" + 
/*     */           
/* 130 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 133 */               getABRDescription(), 
/* 134 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 137 */       log(
/* 138 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 141 */               getABRDescription(), 
/* 142 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 145 */       str1 = this.m_ei.toString();
/* 146 */       if (str1.length() > 64) {
/* 147 */         str1 = str1.substring(0, 64);
/*     */       }
/* 149 */       setDGTitle(str1);
/* 150 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 153 */       setDGString(getABRReturnCode());
/* 154 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 158 */       buildReportFooter();
/*     */       
/* 160 */       if (!isReadOnly()) {
/* 161 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 167 */     String str = "";
/* 168 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 170 */     while (paramString1.length() > 0 && i >= 0) {
/* 171 */       str = str + paramString1.substring(0, i) + paramString3;
/* 172 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 173 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 175 */     str = str + paramString1;
/* 176 */     return str;
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
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 196 */     return "Software Product Upgrade ABR.";
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
/* 207 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 217 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 227 */     return "AFIRMT1ABR02.java,v 1.4 2006/03/03 19:23:25 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 235 */     return "AFIRMT1ABR02.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT1ABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */