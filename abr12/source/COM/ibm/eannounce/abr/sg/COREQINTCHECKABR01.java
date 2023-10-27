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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class COREQINTCHECKABR01
/*     */   extends PokBaseABR
/*     */ {
/*  50 */   public static final String ABR = new String("COREQINTCHECKABR01");
/*     */   
/*  52 */   private EntityGroup m_egParent = null;
/*  53 */   private EntityItem m_ei = null;
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
/*  77 */       println("<br><b>CoReq: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  79 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  80 */       setReturnCode(0);
/*     */       
/*  82 */       str1 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  84 */           .getEntityType(), this.m_ei
/*  85 */           .getEntityID(), "MACHTYPEATR");
/*     */ 
/*     */       
/*  88 */       str2 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  90 */           .getEntityType(), this.m_ei
/*  91 */           .getEntityID(), "MODELATR");
/*     */ 
/*     */       
/*  94 */       str3 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  96 */           .getEntityType(), this.m_ei
/*  97 */           .getEntityID(), "FEATURECODE");
/*     */ 
/*     */       
/* 100 */       str4 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/* 102 */           .getEntityType(), this.m_ei
/* 103 */           .getEntityID(), "INVENTORYGROUP");
/*     */ 
/*     */       
/* 106 */       if (str1 == null || str2 == null || str3 == null || str4 == null) {
/*     */ 
/*     */ 
/*     */         
/* 110 */         setReturnCode(-1);
/* 111 */         stringBuffer.append("Error:Machine type, model, feature code, and Inventory Group can not be empty.");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 116 */       if (getReturnCode() == 0) {
/*     */         
/* 118 */         StringBuffer stringBuffer1 = new StringBuffer();
/* 119 */         stringBuffer1.append("map_MODEL:MACHTYPEATR=" + str1 + ";");
/* 120 */         stringBuffer1.append("map_MODEL:MODELATR=" + str2 + ";");
/* 121 */         stringBuffer1.append("map_FEATURE:FEATURECODE=" + str3 + ";");
/* 122 */         stringBuffer1.append("map_FEATURE:INVENTORYGROUP=" + str4);
/*     */         
/* 124 */         EntityItem[] arrayOfEntityItem = pDGUtility.dynaSearchII(this.m_db, this.m_prof, this.m_ei, "SRDPRODSTRUCT03", "PRODSTRUCT", stringBuffer1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 130 */             .toString());
/*     */         
/* 132 */         if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/* 133 */           setReturnCode(-1);
/* 134 */           stringBuffer.append("Error:There's no PRODUCTSTRUCT with a MODEL with machine type " + str1 + " and model " + str2 + " and FEATURE with feature code " + str3 + " and inventory group " + str4 + str6);
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
/*     */ 
/*     */       
/* 147 */       if (getReturnCode() == -1 && stringBuffer.toString().length() > 0) {
/* 148 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 149 */         OPICMList oPICMList = new OPICMList();
/* 150 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=" + stringBuffer.toString());
/* 151 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/* 152 */       } else if (getReturnCode() == 0) {
/* 153 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 154 */         OPICMList oPICMList = new OPICMList();
/* 155 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=Passed:OK");
/* 156 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/*     */     
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
/* 182 */             replace(str, str6, "<br>") + "</font></h3>");
/*     */         
/* 184 */         logError(sBRException.toString());
/*     */       } else {
/* 186 */         str = str.substring(0, i);
/* 187 */         println(replace(str, str6, "<br>"));
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
/* 216 */       str5 = this.m_ei.toString();
/* 217 */       if (str5.length() > 64) {
/* 218 */         str5 = str5.substring(0, 64);
/*     */       }
/* 220 */       setDGTitle(str5);
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
/* 267 */     return "CoReq Integrity Check ABR.";
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
/* 298 */     return "COREQINTCHECKABR01.java,v 1.7 2006/03/03 19:23:27 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 308 */     return "COREQINTCHECKABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\COREQINTCHECKABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */