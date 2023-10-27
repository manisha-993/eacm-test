/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.transactions.OPICMList;
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
/*     */ public class MODELINTCHECKABR01
/*     */   extends PokBaseABR
/*     */ {
/*  44 */   public static final String ABR = new String("MODELINTCHECKABR01");
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
/*  56 */     String str1 = null;
/*  57 */     String str2 = null;
/*  58 */     String str3 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  61 */       StringBuffer stringBuffer = new StringBuffer();
/*  62 */       PDGUtility pDGUtility = new PDGUtility();
/*  63 */       start_ABRBuild();
/*     */       
/*  65 */       buildReportHeaderII();
/*     */       
/*  67 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  68 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  69 */       println("<br><b>CoReq: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  71 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  72 */       setReturnCode(0);
/*     */       
/*  74 */       str1 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  76 */           .getEntityType(), this.m_ei
/*  77 */           .getEntityID(), "MACHTYPEATR");
/*     */ 
/*     */       
/*  80 */       if (str1 == null) {
/*  81 */         setReturnCode(-1);
/*  82 */         stringBuffer.append("Error:Machine type is empty.");
/*     */       } 
/*     */ 
/*     */       
/*  86 */       if (getReturnCode() == 0) {
/*     */         
/*  88 */         StringBuffer stringBuffer1 = new StringBuffer();
/*  89 */         stringBuffer1.append("map_MACHTYPEATR=" + str1);
/*     */         
/*  91 */         EntityItem[] arrayOfEntityItem = pDGUtility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDMACHTYPE1", "MACHTYPE", stringBuffer1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  97 */             .toString());
/*     */         
/*  99 */         if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/* 100 */           setReturnCode(-1);
/* 101 */           stringBuffer.append("Error:There's no MACHTYPE with machine type " + str1 + str3);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 108 */       if (getReturnCode() == -1 && stringBuffer.toString().length() > 0) {
/* 109 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 110 */         OPICMList oPICMList = new OPICMList();
/* 111 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=" + stringBuffer.toString());
/* 112 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/* 113 */       } else if (getReturnCode() == 0) {
/* 114 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 115 */         OPICMList oPICMList = new OPICMList();
/* 116 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=Passed:OK");
/* 117 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/*     */     
/* 120 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 121 */       setReturnCode(-2);
/* 122 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 126 */           .getMessage() + "</font></h3>");
/*     */       
/* 128 */       logError(lockPDHEntityException.getMessage());
/* 129 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 130 */       setReturnCode(-2);
/* 131 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 133 */           .getMessage() + "</font></h3>");
/*     */       
/* 135 */       logError(updatePDHEntityException.getMessage());
/* 136 */     } catch (SBRException sBRException) {
/* 137 */       String str = sBRException.toString();
/* 138 */       int i = str.indexOf("(ok)");
/* 139 */       if (i < 0) {
/* 140 */         setReturnCode(-2);
/* 141 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 143 */             replace(str, str3, "<br>") + "</font></h3>");
/*     */         
/* 145 */         logError(sBRException.toString());
/*     */       } else {
/* 147 */         str = str.substring(0, i);
/* 148 */         println(replace(str, str3, "<br>"));
/*     */       } 
/* 150 */     } catch (Exception exception) {
/*     */       
/* 152 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 153 */       println("" + exception);
/* 154 */       exception.printStackTrace();
/*     */       
/* 156 */       if (getABRReturnCode() != -2) {
/* 157 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 160 */       println("<br /><b>" + 
/*     */           
/* 162 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 165 */               getABRDescription(), 
/* 166 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 169 */       log(
/* 170 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 173 */               getABRDescription(), 
/* 174 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 177 */       str2 = this.m_ei.toString();
/* 178 */       if (str2.length() > 64) {
/* 179 */         str2 = str2.substring(0, 64);
/*     */       }
/* 181 */       setDGTitle(str2);
/* 182 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 185 */       setDGString(getABRReturnCode());
/* 186 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 190 */       buildReportFooter();
/*     */       
/* 192 */       if (!isReadOnly()) {
/* 193 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 199 */     String str = "";
/* 200 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 202 */     while (paramString1.length() > 0 && i >= 0) {
/* 203 */       str = str + paramString1.substring(0, i) + paramString3;
/* 204 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 205 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 207 */     str = str + paramString1;
/* 208 */     return str;
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
/* 219 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 228 */     return "Model Integrity Check ABR.";
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
/* 239 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 249 */     return new String("1.5");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 259 */     return "MODELINTCHECKABR01.java,v 1.5 2006/03/03 19:23:29 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 269 */     return "MODELINTCHECKABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\MODELINTCHECKABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */