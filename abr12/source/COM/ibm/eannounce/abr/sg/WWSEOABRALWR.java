/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.eannounce.objects.WWSEOABRALWRPDG;
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
/*     */ public class WWSEOABRALWR
/*     */   extends PokBaseABR
/*     */ {
/*  54 */   public static final String ABR = new String("WWSEOABRALWR");
/*  55 */   public static final String CDG_EG = new String("CDG");
/*  56 */   public static final String CDENTITY_EG = new String("CDENTITY");
/*  57 */   public static final String ATT_XXPARTNO = new String("XXPARTNO");
/*     */   
/*  59 */   private EntityGroup m_egParent = null;
/*  60 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  69 */     StringBuffer stringBuffer = new StringBuffer();
/*  70 */     PDGUtility pDGUtility = new PDGUtility();
/*  71 */     String str1 = null;
/*  72 */     EntityGroup entityGroup1 = null;
/*  73 */     EntityGroup entityGroup2 = null;
/*  74 */     String str2 = System.getProperty("line.separator");
/*     */     try {
/*  76 */       start_ABRBuild();
/*     */       
/*  78 */       buildReportHeaderII();
/*     */       
/*  80 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  81 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  82 */       println("<br><b>WWSEO: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  84 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  85 */       setReturnCode(0);
/*     */ 
/*     */       
/*  88 */       String str = pDGUtility.getAttrValue(this.m_ei, ATT_XXPARTNO);
/*  89 */       if (str == null || str.length() <= 0) {
/*  90 */         setReturnCode(-1);
/*  91 */         println("<br /><font color=red>Failed. XXPARTNO is blank.</font>");
/*     */       } 
/*     */       
/*  94 */       entityGroup1 = this.m_elist.getEntityGroup(CDG_EG);
/*  95 */       if (entityGroup1 != null) {
/*  96 */         if (entityGroup1.getEntityItemCount() > 1) {
/*  97 */           setReturnCode(-1);
/*  98 */           println("<br /><font color=red>Failed. There are more than one CDGs linked to WWSEO.</font>");
/*  99 */           println("<br/></br/><b>Country Designator Group(s):</b>");
/* 100 */           for (byte b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 101 */             EntityItem entityItem = entityGroup1.getEntityItem(b);
/* 102 */             println("</br/><LI> " + entityItem.toString());
/*     */           } 
/* 104 */         } else if (entityGroup1.getEntityItemCount() <= 0) {
/* 105 */           setReturnCode(-1);
/* 106 */           println("<br /><font color=red>Failed. There are no CDG linked to WWSEO.</font>");
/*     */         } 
/*     */       } else {
/* 109 */         println("EntityGroup CDG is null\n");
/* 110 */         setReturnCode(-1);
/*     */       } 
/*     */       
/* 113 */       entityGroup2 = this.m_elist.getEntityGroup(CDENTITY_EG);
/* 114 */       if (entityGroup2 != null) {
/* 115 */         if (entityGroup2.getEntityItemCount() <= 0) {
/* 116 */           setReturnCode(-1);
/* 117 */           println("<br /><font color=red>Failed. There are no CDENTITYs.</font>");
/*     */         } 
/*     */       } else {
/* 120 */         println("EntityGroup CDENTITY is null\n");
/* 121 */         setReturnCode(-1);
/*     */       } 
/*     */       
/* 124 */       if (getReturnCode() == 0) {
/* 125 */         WWSEOABRALWRPDG wWSEOABRALWRPDG = new WWSEOABRALWRPDG(null, this.m_db, this.m_prof, "WWSEOABRALWRPDG");
/* 126 */         wWSEOABRALWRPDG.setEntityItem(this.m_ei);
/* 127 */         wWSEOABRALWRPDG.setABReList(this.m_elist);
/* 128 */         wWSEOABRALWRPDG.executeAction(this.m_db, this.m_prof);
/* 129 */         stringBuffer = wWSEOABRALWRPDG.getActivities();
/* 130 */         println("</br></br/><b>Generated Data:</b>");
/* 131 */         println("<br/>" + stringBuffer.toString());
/*     */       } 
/* 133 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 134 */       setReturnCode(-2);
/* 135 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException.getMessage() + "</font></h3>");
/* 136 */       logError(lockPDHEntityException.getMessage());
/* 137 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 138 */       setReturnCode(-2);
/* 139 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException.getMessage() + "</font></h3>");
/* 140 */       logError(updatePDHEntityException.getMessage());
/* 141 */     } catch (SBRException sBRException) {
/* 142 */       String str = sBRException.toString();
/* 143 */       int i = str.indexOf("(ok)");
/* 144 */       if (i < 0) {
/* 145 */         setReturnCode(-2);
/* 146 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 148 */             replace(str, str2, "<br>") + "</font></h3>");
/*     */         
/* 150 */         logError(sBRException.toString());
/*     */       } else {
/* 152 */         str = str.substring(0, i);
/* 153 */         println(replace(str, str2, "<br>"));
/*     */       } 
/* 155 */     } catch (Exception exception) {
/*     */       
/* 157 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 158 */       println("" + exception);
/* 159 */       exception.printStackTrace();
/*     */       
/* 161 */       if (getABRReturnCode() != -2) {
/* 162 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 165 */       println("<br /><b>" + buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */               
/* 167 */               getABRDescription(), 
/* 168 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 171 */       log(
/* 172 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 175 */               getABRDescription(), 
/* 176 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 179 */       str1 = this.m_ei.toString();
/* 180 */       if (str1.length() > 64) {
/* 181 */         str1 = str1.substring(0, 64);
/*     */       }
/* 183 */       setDGTitle(str1);
/* 184 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 187 */       setDGString(getABRReturnCode());
/* 188 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 192 */       buildReportFooter();
/*     */       
/* 194 */       if (!isReadOnly()) {
/* 195 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 201 */     String str = "";
/* 202 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 204 */     while (paramString1.length() > 0 && i >= 0) {
/* 205 */       str = str + paramString1.substring(0, i) + paramString3;
/* 206 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 207 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 209 */     str = str + paramString1;
/* 210 */     return str;
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
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 230 */     return "WWSEO ALWR With CD ABR.";
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
/* 241 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 251 */     return new String("1.5");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 261 */     return "WWSEOABRALWR.java,v 1.5 2006/03/08 17:12:17 joan Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 271 */     return "WWSEOABRALWR.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWSEOABRALWR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */