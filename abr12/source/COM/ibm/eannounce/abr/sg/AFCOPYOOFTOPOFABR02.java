/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.HWCOPYOFTOPOFPDG;
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
/*     */ public class AFCOPYOOFTOPOFABR02
/*     */   extends PokBaseABR
/*     */ {
/*  37 */   public static final String ABR = new String("AFCOPYOOFTOPOFABR02");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public static final String HARDWARE = new String("100");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public static final String SOFTWARE = new String("101");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public static final String FEATURECODE = new String("500");
/*     */   
/*  54 */   private EntityGroup m_egParent = null;
/*  55 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  64 */     String str1 = null;
/*  65 */     EntityGroup entityGroup = null;
/*  66 */     HWCOPYOFTOPOFPDG hWCOPYOFTOPOFPDG = null;
/*  67 */     Vector<Integer> vector = null;
/*  68 */     StringBuffer stringBuffer = null;
/*  69 */     String str2 = null;
/*  70 */     String str3 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  73 */       start_ABRBuild();
/*     */       
/*  75 */       buildReportHeaderII();
/*     */       
/*  77 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  78 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  79 */       println("<br><b>Order Offering: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  81 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  82 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  91 */       str1 = getAttributeFlagEnabledValue(this.m_elist, this.m_ei.getEntityType(), this.m_ei.getEntityID(), "OOFCAT").trim();
/*  92 */       if (!str1.equals(HARDWARE) && 
/*  93 */         !str1.equals(SOFTWARE)) {
/*  94 */         println("</br/><font color=red>Failed. the Order Offering is not in Hardware/Software' Category. </font>");
/*  95 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 105 */       str1 = getAttributeFlagEnabledValue(this.m_elist, this.m_ei.getEntityType(), this.m_ei.getEntityID(), "OOFSUBCAT").trim();
/* 106 */       if (!str1.equals(FEATURECODE)) {
/* 107 */         println("</br/><font color=red>Failed. the Order Offering is not in FeatureCode SubCategory. </font>");
/* 108 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 112 */       entityGroup = this.m_elist.getEntityGroup("FUP");
/*     */       
/* 114 */       vector = getChildrenEntityIds(this.m_ei
/* 115 */           .getEntityType(), this.m_ei
/* 116 */           .getEntityID(), "FUP", "OOFFUP");
/*     */ 
/*     */       
/* 119 */       if (vector.size() <= 0) {
/* 120 */         println("<br /><font color=red>Failed. There is no FUP</font>");
/* 121 */         setReturnCode(-1);
/* 122 */       } else if (vector.size() > 1) {
/* 123 */         println("<br /><font color=red>Failed. There is not one and only one FUP.</font>");
/* 124 */         setReturnCode(-1);
/* 125 */         println("<br/></br/><b>Function Point(s):</b>");
/* 126 */         for (byte b = 0; b < vector.size(); b++) {
/* 127 */           int i = ((Integer)vector.elementAt(b)).intValue();
/*     */           
/* 129 */           EntityItem entityItem = entityGroup.getEntityItem(entityGroup.getEntityType() + i);
/* 130 */           println("</br/><LI> " + entityItem.toString());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 135 */       if (getReturnCode() == 0) {
/* 136 */         log("AFCOPYOOFTOPOFABR02 generating data");
/* 137 */         hWCOPYOFTOPOFPDG = new HWCOPYOFTOPOFPDG(null, this.m_db, this.m_prof, "HWCOPYOFTOPOFPDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 143 */         hWCOPYOFTOPOFPDG.setEntityItem(this.m_ei);
/* 144 */         hWCOPYOFTOPOFPDG.setABReList(this.m_elist);
/* 145 */         hWCOPYOFTOPOFPDG.setExcludeCopy("001");
/* 146 */         hWCOPYOFTOPOFPDG.executeAction(this.m_db, this.m_prof);
/* 147 */         stringBuffer = hWCOPYOFTOPOFPDG.getActivities();
/* 148 */         println("</br></br/><b>Generated Data:</b>");
/* 149 */         println("<br/>" + stringBuffer.toString());
/*     */         
/* 151 */         log("AFCOPYOOFTOPOFABR02 finish generating data");
/*     */       } 
/* 153 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 154 */       setReturnCode(-2);
/* 155 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 159 */           .getMessage() + "</font></h3>");
/*     */       
/* 161 */       logError(lockPDHEntityException.getMessage());
/* 162 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 163 */       setReturnCode(-2);
/* 164 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 166 */           .getMessage() + "</font></h3>");
/*     */       
/* 168 */       logError(updatePDHEntityException.getMessage());
/* 169 */     } catch (SBRException sBRException) {
/* 170 */       String str = sBRException.toString();
/* 171 */       int i = str.indexOf("(ok)");
/* 172 */       if (i < 0) {
/* 173 */         setReturnCode(-2);
/* 174 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 176 */             replace(str, str3, "<br>") + "</font></h3>");
/*     */         
/* 178 */         logError(sBRException.toString());
/*     */       } else {
/* 180 */         str = str.substring(0, i);
/* 181 */         println(replace(str, str3, "<br>"));
/*     */       } 
/* 183 */     } catch (Exception exception) {
/*     */       
/* 185 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 186 */       println("" + exception);
/* 187 */       exception.printStackTrace();
/*     */       
/* 189 */       if (getABRReturnCode() != -2) {
/* 190 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 193 */       println("<br /><b>" + 
/*     */           
/* 195 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 198 */               getABRDescription(), 
/* 199 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 202 */       log(
/* 203 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 206 */               getABRDescription(), 
/* 207 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 210 */       str2 = this.m_ei.toString();
/* 211 */       if (str2.length() > 64) {
/* 212 */         str2 = str2.substring(0, 64);
/*     */       }
/* 214 */       setDGTitle(str2);
/* 215 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 218 */       setDGString(getABRReturnCode());
/* 219 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 223 */       buildReportFooter();
/*     */       
/* 225 */       if (!isReadOnly()) {
/* 226 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 232 */     String str = "";
/* 233 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 235 */     while (paramString1.length() > 0 && i >= 0) {
/* 236 */       str = str + paramString1.substring(0, i) + paramString3;
/* 237 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 238 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 240 */     str = str + paramString1;
/* 241 */     return str;
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
/* 252 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 261 */     return "Hardware Copy OOF To POF ABR.";
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
/* 272 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 282 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 292 */     return "AFCOPYOOFTOPOFABR02.java,v 1.4 2006/03/03 19:23:24 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 300 */     return "AFCOPYOOFTOPOFABR02.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFCOPYOOFTOPOFABR02.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */