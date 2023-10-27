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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MODCONINTCHECKABR01
/*     */   extends PokBaseABR
/*     */ {
/*  47 */   public static final String ABR = new String("MODCONINTCHECKABR01");
/*     */   
/*  49 */   private EntityGroup m_egParent = null;
/*  50 */   private EntityItem m_ei = null;
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
/*  61 */     String str1 = null;
/*  62 */     String str2 = null;
/*  63 */     String str3 = null;
/*  64 */     String str4 = null;
/*  65 */     String str5 = null;
/*  66 */     String str6 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  69 */       StringBuffer stringBuffer = new StringBuffer();
/*  70 */       PDGUtility pDGUtility = new PDGUtility();
/*  71 */       start_ABRBuild();
/*     */       
/*  73 */       buildReportHeaderII();
/*     */       
/*  75 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  76 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  77 */       println("<br><b>Model Convert: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  79 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  80 */       setReturnCode(0);
/*     */       
/*  82 */       str1 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  84 */           .getEntityType(), this.m_ei
/*  85 */           .getEntityID(), "FROMMACHTYPE");
/*     */ 
/*     */       
/*  88 */       str2 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  90 */           .getEntityType(), this.m_ei
/*  91 */           .getEntityID(), "FROMMODEL");
/*     */ 
/*     */       
/*  94 */       str3 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  96 */           .getEntityType(), this.m_ei
/*  97 */           .getEntityID(), "TOMACHTYPE");
/*     */ 
/*     */       
/* 100 */       str4 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/* 102 */           .getEntityType(), this.m_ei
/* 103 */           .getEntityID(), "TOMODEL");
/*     */ 
/*     */       
/* 106 */       if (str1 == null || str2 == null || str3 == null || str4 == null) {
/*     */ 
/*     */ 
/*     */         
/* 110 */         setReturnCode(-1);
/* 111 */         stringBuffer.append("Error:From machine type model and To Machine type model can not be empty.");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 116 */       if (getReturnCode() == 0) {
/*     */ 
/*     */         
/* 119 */         if (str1.equals(str3) && str2.equals(str4)) {
/* 120 */           setReturnCode(-1);
/* 121 */           stringBuffer.append("Error:From Machine Type and Model are the same with To Machine Type and Model.\n");
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 126 */         StringBuffer stringBuffer1 = new StringBuffer();
/* 127 */         stringBuffer1.append("map_MACHTYPEATR=" + str1 + ";");
/* 128 */         stringBuffer1.append("map_MODELATR=" + str2);
/*     */         
/* 130 */         EntityItem[] arrayOfEntityItem1 = pDGUtility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDMODEL5", "MODEL", stringBuffer1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 136 */             .toString());
/*     */         
/* 138 */         if (arrayOfEntityItem1 == null || arrayOfEntityItem1.length <= 0) {
/* 139 */           setReturnCode(-1);
/* 140 */           stringBuffer.append("Error:There's no MODEL with machine type " + str1 + " and model " + str2 + str6);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 149 */         stringBuffer1 = new StringBuffer();
/* 150 */         stringBuffer1.append("map_MACHTYPEATR=" + str3 + ";");
/* 151 */         stringBuffer1.append("map_MODELATR=" + str4);
/*     */         
/* 153 */         EntityItem[] arrayOfEntityItem2 = pDGUtility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDMODEL5", "MODEL", stringBuffer1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 159 */             .toString());
/*     */         
/* 161 */         if (arrayOfEntityItem2 == null || arrayOfEntityItem2.length <= 0) {
/* 162 */           setReturnCode(-1);
/* 163 */           stringBuffer.append("Error:There's no MODEL with machine type " + str3 + " and model " + str4 + str6);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       if (getReturnCode() == -1 && stringBuffer.toString().length() > 0) {
/* 173 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 174 */         OPICMList oPICMList = new OPICMList();
/* 175 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=" + stringBuffer.toString());
/* 176 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/* 177 */       } else if (getReturnCode() == 0) {
/* 178 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 179 */         OPICMList oPICMList = new OPICMList();
/* 180 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=Passed:OK");
/* 181 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/*     */     
/* 184 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 185 */       setReturnCode(-2);
/* 186 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 190 */           .getMessage() + "</font></h3>");
/*     */       
/* 192 */       logError(lockPDHEntityException.getMessage());
/* 193 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 194 */       setReturnCode(-2);
/* 195 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 197 */           .getMessage() + "</font></h3>");
/*     */       
/* 199 */       logError(updatePDHEntityException.getMessage());
/* 200 */     } catch (SBRException sBRException) {
/* 201 */       String str = sBRException.toString();
/* 202 */       int i = str.indexOf("(ok)");
/* 203 */       if (i < 0) {
/* 204 */         setReturnCode(-2);
/* 205 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 207 */             replace(str, str6, "<br>") + "</font></h3>");
/*     */         
/* 209 */         logError(sBRException.toString());
/*     */       } else {
/* 211 */         str = str.substring(0, i);
/* 212 */         println(replace(str, str6, "<br>"));
/*     */       } 
/* 214 */     } catch (Exception exception) {
/*     */       
/* 216 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 217 */       println("" + exception);
/* 218 */       exception.printStackTrace();
/*     */       
/* 220 */       if (getABRReturnCode() != -2) {
/* 221 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 224 */       println("<br /><b>" + 
/*     */           
/* 226 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 229 */               getABRDescription(), 
/* 230 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 233 */       log(
/* 234 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 237 */               getABRDescription(), 
/* 238 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 241 */       str5 = this.m_ei.toString();
/* 242 */       if (str5.length() > 64) {
/* 243 */         str5 = str5.substring(0, 64);
/*     */       }
/* 245 */       setDGTitle(str5);
/* 246 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 249 */       setDGString(getABRReturnCode());
/* 250 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 254 */       buildReportFooter();
/*     */       
/* 256 */       if (!isReadOnly()) {
/* 257 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 263 */     String str = "";
/* 264 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 266 */     while (paramString1.length() > 0 && i >= 0) {
/* 267 */       str = str + paramString1.substring(0, i) + paramString3;
/* 268 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 269 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 271 */     str = str + paramString1;
/* 272 */     return str;
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
/* 283 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 292 */     return "Model Convert Integrity Check ABR.";
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
/* 303 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 313 */     return new String("1.6");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 323 */     return "MODCONINTCHECKABR01.java,v 1.6 2006/03/03 19:23:29 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 333 */     return "MODCONINTCHECKABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\MODCONINTCHECKABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */