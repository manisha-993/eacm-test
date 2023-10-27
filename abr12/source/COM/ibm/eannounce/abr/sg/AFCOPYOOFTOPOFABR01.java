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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AFCOPYOOFTOPOFABR01
/*     */   extends PokBaseABR
/*     */ {
/*  46 */   public static final String ABR = new String("AFCOPYOOFTOPOFABR01");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static final String HARDWARE = new String("100");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final String SOFTWARE = new String("101");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final String FEATURECODE = new String("500");
/*     */   
/*  63 */   private EntityGroup m_egParent = null;
/*  64 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  70 */     EntityGroup entityGroup = null;
/*  71 */     Vector<Integer> vector = null;
/*  72 */     HWCOPYOFTOPOFPDG hWCOPYOFTOPOFPDG = null;
/*  73 */     StringBuffer stringBuffer = null;
/*  74 */     String str1 = null;
/*  75 */     String str2 = null;
/*  76 */     String str3 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  79 */       start_ABRBuild();
/*     */       
/*  81 */       buildReportHeaderII();
/*     */       
/*  83 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  84 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  85 */       println("<br><b>Order Offering: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  87 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  88 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       str1 = getAttributeFlagEnabledValue(this.m_elist, this.m_ei.getEntityType(), this.m_ei.getEntityID(), "OOFCAT").trim();
/*  98 */       if (!str1.equals(HARDWARE) && 
/*  99 */         !str1.equals(SOFTWARE)) {
/* 100 */         println("</br/><font color=red>Failed. the Order Offering is not in Hardware/Software' Category. </font>");
/* 101 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       str1 = getAttributeFlagEnabledValue(this.m_elist, this.m_ei.getEntityType(), this.m_ei.getEntityID(), "OOFSUBCAT").trim();
/* 112 */       if (!str1.equals(FEATURECODE)) {
/* 113 */         println("</br/><font color=red>Failed. the Order Offering is not in FeatureCode SubCategory. </font>");
/* 114 */         setReturnCode(-1);
/*     */       } 
/*     */ 
/*     */       
/* 118 */       entityGroup = this.m_elist.getEntityGroup("FUP");
/*     */       
/* 120 */       vector = getChildrenEntityIds(this.m_ei
/* 121 */           .getEntityType(), this.m_ei
/* 122 */           .getEntityID(), "FUP", "OOFFUP");
/*     */ 
/*     */       
/* 125 */       if (vector.size() <= 0) {
/* 126 */         println("<br /><font color=red>Failed. There is no FUP</font>");
/* 127 */         setReturnCode(-1);
/* 128 */       } else if (vector.size() > 1) {
/* 129 */         println("<br /><font color=red>Failed. There is not one and only one FUP.</font>");
/* 130 */         setReturnCode(-1);
/* 131 */         println("<br/></br/><b>Function Point(s):</b>");
/* 132 */         for (byte b = 0; b < vector.size(); b++) {
/* 133 */           int i = ((Integer)vector.elementAt(b)).intValue();
/*     */           
/* 135 */           EntityItem entityItem = entityGroup.getEntityItem(entityGroup.getEntityType() + i);
/* 136 */           println("</br/><LI> " + entityItem.toString());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 141 */       if (getReturnCode() == 0) {
/* 142 */         log("AFCOPYOOFTOPOFABR01 generating data");
/* 143 */         hWCOPYOFTOPOFPDG = new HWCOPYOFTOPOFPDG(null, this.m_db, this.m_prof, "HWCOPYOFTOPOFPDG");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 149 */         hWCOPYOFTOPOFPDG.setEntityItem(this.m_ei);
/* 150 */         hWCOPYOFTOPOFPDG.setABReList(this.m_elist);
/* 151 */         hWCOPYOFTOPOFPDG.setExcludeCopy("0");
/* 152 */         hWCOPYOFTOPOFPDG.executeAction(this.m_db, this.m_prof);
/* 153 */         stringBuffer = hWCOPYOFTOPOFPDG.getActivities();
/* 154 */         println("</br></br/><b>Generated Data:</b>");
/* 155 */         println("<br/>" + stringBuffer.toString());
/*     */         
/* 157 */         log("AFCOPYOOFTOPOFABR01 finish generating data");
/*     */       } 
/* 159 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 160 */       setReturnCode(-2);
/* 161 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 165 */           .getMessage() + "</font></h3>");
/*     */       
/* 167 */       logError(lockPDHEntityException.getMessage());
/* 168 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 169 */       setReturnCode(-2);
/* 170 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 172 */           .getMessage() + "</font></h3>");
/*     */       
/* 174 */       logError(updatePDHEntityException.getMessage());
/* 175 */     } catch (SBRException sBRException) {
/* 176 */       String str = sBRException.toString();
/* 177 */       int i = str.indexOf("(ok)");
/* 178 */       if (i < 0) {
/* 179 */         setReturnCode(-2);
/* 180 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 182 */             replace(str, str3, "<br>") + "</font></h3>");
/*     */         
/* 184 */         logError(sBRException.toString());
/*     */       } else {
/* 186 */         str = str.substring(0, i);
/* 187 */         println(replace(str, str3, "<br>"));
/*     */       } 
/* 189 */     } catch (Exception exception) {
/*     */       
/* 191 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 192 */       println("" + exception);
/* 193 */       exception.printStackTrace();
/*     */       
/* 195 */       if (getABRReturnCode() != -2) {
/* 196 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 199 */       println("<br /><b>" + 
/*     */           
/* 201 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 204 */               getABRDescription(), 
/* 205 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 208 */       log(
/* 209 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 212 */               getABRDescription(), 
/* 213 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 216 */       str2 = this.m_ei.toString();
/* 217 */       if (str2.length() > 64) {
/* 218 */         str2 = str2.substring(0, 64);
/*     */       }
/* 220 */       setDGTitle(str2);
/* 221 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 224 */       setDGString(getABRReturnCode());
/* 225 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 229 */       buildReportFooter();
/*     */       
/* 231 */       if (!isReadOnly()) {
/* 232 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 238 */     String str = "";
/* 239 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 241 */     while (paramString1.length() > 0 && i >= 0) {
/* 242 */       str = str + paramString1.substring(0, i) + paramString3;
/* 243 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 244 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 246 */     str = str + paramString1;
/* 247 */     return str;
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
/* 258 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 267 */     return "Hardware Copy OOF To POF ABR.";
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
/* 278 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 288 */     return new String("1.7");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 298 */     return "AFCOPYOOFTOPOFABR01.java,v 1.7 2008/01/30 19:39:15 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 306 */     return "AFCOPYOOFTOPOFABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\AFCOPYOOFTOPOFABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */