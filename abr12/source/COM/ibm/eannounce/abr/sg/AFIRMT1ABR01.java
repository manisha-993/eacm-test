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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AFIRMT1ABR01
/*     */   extends PokBaseABR
/*     */ {
/*  44 */   public static final String ABR = new String("AFIRMT1ABR01");
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
/*  56 */     SPDGActionItem sPDGActionItem = null;
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
/*  77 */         log("AFIRMT1ABR01 generating data");
/*  78 */         sPDGActionItem = new SPDGActionItem(null, this.m_db, this.m_prof, "SWSPDG1");
/*     */         
/*  80 */         sPDGActionItem.setEntityItem(this.m_ei);
/*  81 */         sPDGActionItem.executeAction(this.m_db, this.m_prof);
/*  82 */         log("AFIRMT1ABR01 finish generating data");
/*     */       } 
/*  84 */     } catch (LockPDHEntityException lockPDHEntityException) {
/*  85 */       setReturnCode(-2);
/*  86 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/*  90 */           .getMessage() + "</font></h3>");
/*     */       
/*  92 */       logError(lockPDHEntityException.getMessage());
/*  93 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/*  94 */       setReturnCode(-2);
/*  95 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/*  97 */           .getMessage() + "</font></h3>");
/*     */       
/*  99 */       logError(updatePDHEntityException.getMessage());
/* 100 */     } catch (SBRException sBRException) {
/* 101 */       String str = sBRException.toString();
/* 102 */       int i = str.indexOf("(ok)");
/* 103 */       if (i < 0) {
/* 104 */         setReturnCode(-2);
/* 105 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 107 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 109 */         logError(sBRException.toString());
/*     */       } else {
/* 111 */         str = str.substring(0, i);
/* 112 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 114 */     } catch (Exception exception) {
/*     */       
/* 116 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 117 */       println("" + exception);
/* 118 */       exception.printStackTrace();
/*     */       
/* 120 */       if (getABRReturnCode() != -2) {
/* 121 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 124 */       println("<br /><b>" + 
/*     */           
/* 126 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 129 */               getABRDescription(), 
/* 130 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 133 */       log(
/* 134 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 137 */               getABRDescription(), 
/* 138 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 141 */       str1 = this.m_ei.toString();
/* 142 */       if (str1.length() > 64) {
/* 143 */         str1 = str1.substring(0, 64);
/*     */       }
/* 145 */       setDGTitle(str1);
/* 146 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 149 */       setDGString(getABRReturnCode());
/* 150 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 154 */       buildReportFooter();
/*     */       
/* 156 */       if (!isReadOnly()) {
/* 157 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 163 */     String str = "";
/* 164 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 166 */     while (paramString1.length() > 0 && i >= 0) {
/* 167 */       str = str + paramString1.substring(0, i) + paramString3;
/* 168 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 169 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 171 */     str = str + paramString1;
/* 172 */     return str;
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
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 192 */     return "Software Product Base and Initial ABR.";
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
/* 203 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 213 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 223 */     return "AFIRMT1ABR01.java,v 1.4 2006/03/03 19:23:25 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 231 */     return "AFIRMT1ABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFIRMT1ABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */