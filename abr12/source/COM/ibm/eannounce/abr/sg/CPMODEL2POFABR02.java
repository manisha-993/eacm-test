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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CPMODEL2POFABR02
/*     */   extends PokBaseABR
/*     */ {
/*  56 */   public static final String ABR = new String("CPMODEL2POFABR02");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final String HARDWARE = new String("100");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static final String SOFTWARE = new String("101");
/*     */ 
/*     */   
/*  69 */   private EntityGroup m_egParent = null;
/*  70 */   private EntityItem m_ei = null;
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
/*  81 */     String str1 = null;
/*  82 */     String str2 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  85 */       start_ABRBuild();
/*     */       
/*  87 */       buildReportHeaderII();
/*     */ 
/*     */       
/*  90 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  91 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  92 */       println("<br><b>Model: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  94 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  95 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */       
/*  99 */       logMessage("**************************Before getParentEntityIds");
/* 100 */       Vector vector = new Vector();
/*     */       
/* 102 */       vector = getParentEntityIds(this.m_ei
/* 103 */           .getEntityType(), this.m_ei
/* 104 */           .getEntityID(), "FEATURE", "PRODSTRUCT");
/*     */ 
/*     */       
/* 107 */       if (vector.size() <= 0) {
/* 108 */         println("<br /><font color=red>Failed. There are no FEATURES/font>");
/* 109 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 113 */       if (getReturnCode() == 0) {
/* 114 */         log("CPMODEL2POFABR02 generating data");
/* 115 */         ACTCPMODEL2POFPDG aCTCPMODEL2POFPDG = new ACTCPMODEL2POFPDG(null, this.m_db, this.m_prof, "ACTCPMODEL2POFPDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 121 */         aCTCPMODEL2POFPDG.setEntityItem(this.m_ei);
/* 122 */         aCTCPMODEL2POFPDG.setABReList(this.m_elist);
/* 123 */         aCTCPMODEL2POFPDG.setExcludeCopy("001");
/*     */         
/* 125 */         aCTCPMODEL2POFPDG.executeAction(this.m_db, this.m_prof);
/* 126 */         StringBuffer stringBuffer = aCTCPMODEL2POFPDG.getActivities();
/* 127 */         println("</br></br/><b>Generated Data:</b>");
/* 128 */         println("<br/>" + stringBuffer.toString());
/*     */         
/* 130 */         log("CPMODEL2POFABR02 finish generating data");
/*     */       } 
/* 132 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 133 */       setReturnCode(-2);
/* 134 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 138 */           .getMessage() + "</font></h3>");
/*     */       
/* 140 */       logError(lockPDHEntityException.getMessage());
/* 141 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 142 */       setReturnCode(-2);
/* 143 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 145 */           .getMessage() + "</font></h3>");
/*     */       
/* 147 */       logError(updatePDHEntityException.getMessage());
/* 148 */     } catch (SBRException sBRException) {
/* 149 */       String str = sBRException.toString();
/* 150 */       int i = str.indexOf("(ok)");
/* 151 */       if (i < 0) {
/* 152 */         setReturnCode(-2);
/* 153 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 155 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 157 */         logError(sBRException.toString());
/*     */       } else {
/* 159 */         str = str.substring(0, i);
/* 160 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 162 */     } catch (Exception exception) {
/*     */       
/* 164 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 165 */       println("" + exception);
/* 166 */       exception.printStackTrace();
/*     */       
/* 168 */       if (getABRReturnCode() != -2) {
/* 169 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 172 */       println("<br /><b>" + 
/*     */           
/* 174 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 177 */               getABRDescription(), 
/* 178 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 181 */       log(
/* 182 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 185 */               getABRDescription(), 
/* 186 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 189 */       str1 = this.m_ei.toString();
/* 190 */       if (str1.length() > 64) {
/* 191 */         str1 = str1.substring(0, 64);
/*     */       }
/* 193 */       setDGTitle(str1);
/* 194 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 197 */       setDGString(getABRReturnCode());
/* 198 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 202 */       buildReportFooter();
/*     */       
/* 204 */       if (!isReadOnly()) {
/* 205 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 211 */     String str = "";
/* 212 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 214 */     while (paramString1.length() > 0 && i >= 0) {
/* 215 */       str = str + paramString1.substring(0, i) + paramString3;
/* 216 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 217 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 219 */     str = str + paramString1;
/* 220 */     return str;
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
/* 231 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 240 */     return "Hardware Copy OOF To POF ABR.";
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
/* 251 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 261 */     return new String("1.9");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 271 */     return "CPMODEL2POFABR02.java,v 1.9 2008/01/30 19:39:16 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 281 */     return "CPMODEL2POFABR02.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\CPMODEL2POFABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */