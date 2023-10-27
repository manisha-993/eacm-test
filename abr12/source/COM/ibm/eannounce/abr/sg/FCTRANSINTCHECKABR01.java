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
/*     */ public class FCTRANSINTCHECKABR01
/*     */   extends PokBaseABR
/*     */ {
/*  47 */   public static final String ABR = new String("FCTRANSINTCHECKABR01");
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
/*  66 */     String str6 = null;
/*  67 */     String str7 = null;
/*  68 */     String str8 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  71 */       StringBuffer stringBuffer = new StringBuffer();
/*  72 */       PDGUtility pDGUtility = new PDGUtility();
/*  73 */       start_ABRBuild();
/*     */       
/*  75 */       buildReportHeaderII();
/*     */       
/*  77 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  78 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  79 */       println("<br><b>Feature Transaction: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  81 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  82 */       setReturnCode(0);
/*     */       
/*  84 */       str1 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  86 */           .getEntityType(), this.m_ei
/*  87 */           .getEntityID(), "FROMMACHTYPE");
/*     */ 
/*     */       
/*  90 */       str2 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  92 */           .getEntityType(), this.m_ei
/*  93 */           .getEntityID(), "FROMMODEL");
/*     */ 
/*     */       
/*  96 */       str3 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  98 */           .getEntityType(), this.m_ei
/*  99 */           .getEntityID(), "FROMFEATURECODE");
/*     */ 
/*     */       
/* 102 */       str4 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/* 104 */           .getEntityType(), this.m_ei
/* 105 */           .getEntityID(), "TOMACHTYPE");
/*     */ 
/*     */       
/* 108 */       str5 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/* 110 */           .getEntityType(), this.m_ei
/* 111 */           .getEntityID(), "TOMODEL");
/*     */ 
/*     */       
/* 114 */       str6 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/* 116 */           .getEntityType(), this.m_ei
/* 117 */           .getEntityID(), "TOFEATURECODE");
/*     */ 
/*     */       
/* 120 */       if (str1 == null || str2 == null || str3 == null || str4 == null || str5 == null || str6 == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 126 */         setReturnCode(-1);
/* 127 */         stringBuffer.append("Error:From machine type, model, feature code and To Machine type, model, feature code can not be empty.");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 132 */       if (getReturnCode() == 0) {
/*     */ 
/*     */         
/* 135 */         if (str3.equals(str6) && str2
/* 136 */           .equals(str5)) {
/* 137 */           setReturnCode(-1);
/* 138 */           stringBuffer.append("Error:From Model and Feature Code are the same with To Model and Feature Code.\n");
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 143 */         StringBuffer stringBuffer1 = new StringBuffer();
/* 144 */         stringBuffer1.append("map_MODEL:MACHTYPEATR=" + str1 + ";");
/* 145 */         stringBuffer1.append("map_MODEL:MODELATR=" + str2 + ";");
/* 146 */         stringBuffer1.append("map_FEATURE:FEATURECODE=" + str3);
/*     */         
/* 148 */         EntityItem[] arrayOfEntityItem1 = pDGUtility.dynaSearchII(this.m_db, this.m_prof, this.m_ei, "SRDPRODSTRUCT03", "PRODSTRUCT", stringBuffer1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 154 */             .toString());
/*     */         
/* 156 */         if (arrayOfEntityItem1 == null || arrayOfEntityItem1.length <= 0) {
/* 157 */           setReturnCode(-1);
/* 158 */           stringBuffer.append("Error:There's no PRODUCTSTRUCT with a MODEL with machine type " + str1 + " and model " + str2 + " and FEATURE with feature code " + str3 + str8);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 169 */         stringBuffer1 = new StringBuffer();
/* 170 */         stringBuffer1.append("map_MODEL:MACHTYPEATR=" + str4 + ";");
/* 171 */         stringBuffer1.append("map_MODEL:MODELATR=" + str5 + ";");
/* 172 */         stringBuffer1.append("map_FEATURE:FEATURECODE=" + str6);
/*     */         
/* 174 */         EntityItem[] arrayOfEntityItem2 = pDGUtility.dynaSearchII(this.m_db, this.m_prof, this.m_ei, "SRDPRODSTRUCT03", "PRODSTRUCT", stringBuffer1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 180 */             .toString());
/*     */         
/* 182 */         if (arrayOfEntityItem2 == null || arrayOfEntityItem2.length <= 0) {
/* 183 */           setReturnCode(-1);
/* 184 */           stringBuffer.append("Error:There's no PRODUCTSTRUCT with a MODEL with machine type " + str4 + " and model " + str5 + " and FEATURE with feature code " + str6 + str8);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 195 */       if (getReturnCode() == -1 && stringBuffer.toString().length() > 0) {
/* 196 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 197 */         OPICMList oPICMList = new OPICMList();
/* 198 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=" + stringBuffer.toString());
/* 199 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/* 200 */       } else if (getReturnCode() == 0) {
/* 201 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 202 */         OPICMList oPICMList = new OPICMList();
/* 203 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=Passed:OK");
/* 204 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/*     */     
/* 207 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 208 */       setReturnCode(-2);
/* 209 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 213 */           .getMessage() + "</font></h3>");
/*     */       
/* 215 */       logError(lockPDHEntityException.getMessage());
/* 216 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 217 */       setReturnCode(-2);
/* 218 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 220 */           .getMessage() + "</font></h3>");
/*     */       
/* 222 */       logError(updatePDHEntityException.getMessage());
/* 223 */     } catch (SBRException sBRException) {
/* 224 */       String str = sBRException.toString();
/* 225 */       int i = str.indexOf("(ok)");
/* 226 */       if (i < 0) {
/* 227 */         setReturnCode(-2);
/* 228 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 230 */             replace(str, str8, "<br>") + "</font></h3>");
/*     */         
/* 232 */         logError(sBRException.toString());
/*     */       } else {
/* 234 */         str = str.substring(0, i);
/* 235 */         println(replace(str, str8, "<br>"));
/*     */       } 
/* 237 */     } catch (Exception exception) {
/*     */       
/* 239 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 240 */       println("" + exception);
/* 241 */       exception.printStackTrace();
/*     */       
/* 243 */       if (getABRReturnCode() != -2) {
/* 244 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 247 */       println("<br /><b>" + 
/*     */           
/* 249 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 252 */               getABRDescription(), 
/* 253 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 256 */       log(
/* 257 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 260 */               getABRDescription(), 
/* 261 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 264 */       str7 = this.m_ei.toString();
/* 265 */       if (str7.length() > 64) {
/* 266 */         str7 = str7.substring(0, 64);
/*     */       }
/* 268 */       setDGTitle(str7);
/* 269 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 272 */       setDGString(getABRReturnCode());
/* 273 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 277 */       buildReportFooter();
/*     */       
/* 279 */       if (!isReadOnly()) {
/* 280 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 286 */     String str = "";
/* 287 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 289 */     while (paramString1.length() > 0 && i >= 0) {
/* 290 */       str = str + paramString1.substring(0, i) + paramString3;
/* 291 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 292 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 294 */     str = str + paramString1;
/* 295 */     return str;
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
/* 306 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 315 */     return "Feature Transaction Integrity Check ABR.";
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
/* 326 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 336 */     return new String("1.6");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 346 */     return "FCTRANSINTCHECKABR01.java,v 1.6 2006/03/03 19:23:28 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 356 */     return "FCTRANSINTCHECKABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\FCTRANSINTCHECKABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */