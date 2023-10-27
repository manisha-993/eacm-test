/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.ACTCPMODEL2POFPDG;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.SBRException;
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
/*     */ public class CPMODEL2POFABR01
/*     */   extends PokBaseABR
/*     */ {
/*  53 */   public static final String ABR = new String("CPMODEL2POFABR01");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final String HARDWARE = new String("100");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final String SOFTWARE = new String("101");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final String FEATURECODE = new String("500");
/*     */   
/*  70 */   private EntityGroup m_egParent = null;
/*  71 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  82 */     String str1 = null;
/*  83 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  86 */       start_ABRBuild();
/*     */       
/*  88 */       buildReportHeaderII();
/*     */ 
/*     */       
/*  91 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  92 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  93 */       println("<br><b>Model: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  95 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  96 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */       
/* 100 */       logMessage("**************************Before getParentEntityIds");
/* 101 */       Vector vector = new Vector();
/*     */       
/* 103 */       vector = getParentEntityIds(this.m_ei
/* 104 */           .getEntityType(), this.m_ei
/* 105 */           .getEntityID(), "FEATURE", "PRODSTRUCT");
/*     */ 
/*     */       
/* 108 */       if (vector.size() <= 0) {
/* 109 */         println("<br /><font color=red>Failed. There are no FEATURES/font>");
/* 110 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 114 */       if (getReturnCode() == 0) {
/* 115 */         log("CPMODEL2POFABR01 generating data");
/* 116 */         ACTCPMODEL2POFPDG aCTCPMODEL2POFPDG = new ACTCPMODEL2POFPDG(null, this.m_db, this.m_prof, "ACTCPMODEL2POFPDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 122 */         aCTCPMODEL2POFPDG.setEntityItem(this.m_ei);
/* 123 */         aCTCPMODEL2POFPDG.setABReList(this.m_elist);
/* 124 */         aCTCPMODEL2POFPDG.setExcludeCopy("0");
/*     */         
/* 126 */         aCTCPMODEL2POFPDG.executeAction(this.m_db, this.m_prof);
/* 127 */         StringBuffer stringBuffer = aCTCPMODEL2POFPDG.getActivities();
/* 128 */         println("</br></br/><b>Generated Data:</b>");
/* 129 */         println("<br/>" + stringBuffer.toString());
/*     */         
/* 131 */         log("CPMODEL2POFABR01 finish generating data");
/*     */       } 
/* 133 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 134 */       setReturnCode(-2);
/* 135 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 139 */           .getMessage() + "</font></h3>");
/*     */       
/* 141 */       logError(lockPDHEntityException.getMessage());
/* 142 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 143 */       setReturnCode(-2);
/* 144 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 146 */           .getMessage() + "</font></h3>");
/*     */       
/* 148 */       logError(updatePDHEntityException.getMessage());
/* 149 */     } catch (SBRException sBRException) {
/* 150 */       String str = sBRException.toString();
/* 151 */       int i = str.indexOf("(ok)");
/* 152 */       if (i < 0) {
/* 153 */         setReturnCode(-2);
/* 154 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 156 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 158 */         logError(sBRException.toString());
/*     */       } else {
/* 160 */         str = str.substring(0, i);
/* 161 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 163 */     } catch (Exception exception) {
/*     */       
/* 165 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 166 */       println("" + exception);
/* 167 */       exception.printStackTrace();
/*     */       
/* 169 */       if (getABRReturnCode() != -2) {
/* 170 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 173 */       println("<br /><b>" + 
/*     */           
/* 175 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 178 */               getABRDescription(), 
/* 179 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 182 */       log(
/* 183 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 186 */               getABRDescription(), 
/* 187 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 190 */       str1 = this.m_ei.toString();
/* 191 */       if (str1.length() > 64) {
/* 192 */         str1 = str1.substring(0, 64);
/*     */       }
/* 194 */       setDGTitle(str1);
/* 195 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 198 */       setDGString(getABRReturnCode());
/* 199 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 203 */       buildReportFooter();
/*     */       
/* 205 */       if (!isReadOnly()) {
/* 206 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 212 */     String str = "";
/* 213 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 215 */     while (paramString1.length() > 0 && i >= 0) {
/* 216 */       str = str + paramString1.substring(0, i) + paramString3;
/* 217 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 218 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 220 */     str = str + paramString1;
/* 221 */     return str;
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
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 241 */     return "Hardware Copy OOF To POF ABR.";
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
/* 252 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 262 */     return new String("1.8");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 272 */     return "CPMODEL2POFABR01.java,v 1.8 2008/01/30 19:39:18 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 282 */     return "CPMODEL2POFABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CPMODEL2POFABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */