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
/*     */ public class FCINFCINTCHECKABR01
/*     */   extends PokBaseABR
/*     */ {
/*  41 */   public static final String ABR = new String("FCINFCINTCHECKABR01");
/*     */   
/*  43 */   private EntityGroup m_egParent = null;
/*  44 */   private EntityItem m_ei = null;
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
/*  55 */     String str1 = null;
/*  56 */     String str2 = null;
/*  57 */     String str3 = null;
/*  58 */     String str4 = null;
/*  59 */     String str5 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  62 */       StringBuffer stringBuffer = new StringBuffer();
/*  63 */       PDGUtility pDGUtility = new PDGUtility();
/*  64 */       start_ABRBuild();
/*     */       
/*  66 */       buildReportHeaderII();
/*     */       
/*  68 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  69 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  70 */       println("<br><b>Feature in Feature: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  72 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  73 */       setReturnCode(0);
/*     */       
/*  75 */       str1 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  77 */           .getEntityType(), this.m_ei
/*  78 */           .getEntityID(), "FEATURECODE");
/*     */ 
/*     */       
/*  81 */       str2 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  83 */           .getEntityType(), this.m_ei
/*  84 */           .getEntityID(), "FEATURECODE2");
/*     */ 
/*     */       
/*  87 */       str3 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  89 */           .getEntityType(), this.m_ei
/*  90 */           .getEntityID(), "INVENTORYGROUP");
/*     */ 
/*     */       
/*  93 */       if (str1 == null || str2 == null) {
/*  94 */         setReturnCode(-1);
/*  95 */         stringBuffer.append("Error:Feature code and Contained Feature Code can not be empty.");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 100 */       if (getReturnCode() == 0) {
/*     */ 
/*     */         
/* 103 */         if (str1.equals(str2)) {
/* 104 */           setReturnCode(-1);
/* 105 */           stringBuffer.append("Error:Feature Code are the same with Contained Feature Code.\n");
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 110 */         StringBuffer stringBuffer1 = new StringBuffer();
/* 111 */         stringBuffer1.append("map_FEATURECODE=" + str1 + ";");
/* 112 */         stringBuffer1.append("map_INVENTORYGROUP=" + str3);
/*     */         
/* 114 */         EntityItem[] arrayOfEntityItem1 = pDGUtility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDFEATURE3", "FEATURE", stringBuffer1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 120 */             .toString());
/*     */         
/* 122 */         if (arrayOfEntityItem1 == null || arrayOfEntityItem1.length <= 0) {
/* 123 */           setReturnCode(-1);
/* 124 */           stringBuffer.append("Error:There's no FEATURE with feature code " + str1 + " and inventory group " + str3 + str5);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 133 */         stringBuffer1 = new StringBuffer();
/* 134 */         stringBuffer1.append("map_FEATURECODE=" + str2 + ";");
/* 135 */         stringBuffer1.append("map_INVENTORYGROUP=" + str3);
/*     */         
/* 137 */         EntityItem[] arrayOfEntityItem2 = pDGUtility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDFEATURE3", "FEATURE", stringBuffer1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 143 */             .toString());
/*     */         
/* 145 */         if (arrayOfEntityItem2 == null || arrayOfEntityItem2.length <= 0) {
/* 146 */           setReturnCode(-1);
/* 147 */           stringBuffer.append("Error:There's no FEATURE with feature code " + str2 + " and inventory group " + str3 + str5);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 156 */       if (getReturnCode() == -1 && stringBuffer.toString().length() > 0) {
/* 157 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 158 */         OPICMList oPICMList = new OPICMList();
/* 159 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=" + stringBuffer.toString());
/* 160 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/* 161 */       } else if (getReturnCode() == 0) {
/* 162 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 163 */         OPICMList oPICMList = new OPICMList();
/* 164 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=Passed:OK");
/* 165 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/*     */     
/* 168 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 169 */       setReturnCode(-2);
/* 170 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 174 */           .getMessage() + "</font></h3>");
/*     */       
/* 176 */       logError(lockPDHEntityException.getMessage());
/* 177 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 178 */       setReturnCode(-2);
/* 179 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 181 */           .getMessage() + "</font></h3>");
/*     */       
/* 183 */       logError(updatePDHEntityException.getMessage());
/* 184 */     } catch (SBRException sBRException) {
/* 185 */       String str = sBRException.toString();
/* 186 */       int i = str.indexOf("(ok)");
/* 187 */       if (i < 0) {
/* 188 */         setReturnCode(-2);
/* 189 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 191 */             replace(str, str5, "<br>") + "</font></h3>");
/*     */         
/* 193 */         logError(sBRException.toString());
/*     */       } else {
/* 195 */         str = str.substring(0, i);
/* 196 */         println(replace(str, str5, "<br>"));
/*     */       } 
/* 198 */     } catch (Exception exception) {
/*     */       
/* 200 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 201 */       println("" + exception);
/* 202 */       exception.printStackTrace();
/*     */       
/* 204 */       if (getABRReturnCode() != -2) {
/* 205 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 208 */       println("<br /><b>" + 
/*     */           
/* 210 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 213 */               getABRDescription(), 
/* 214 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 217 */       log(
/* 218 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 221 */               getABRDescription(), 
/* 222 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 225 */       str4 = this.m_ei.toString();
/* 226 */       if (str4.length() > 64) {
/* 227 */         str4 = str4.substring(0, 64);
/*     */       }
/* 229 */       setDGTitle(str4);
/* 230 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 233 */       setDGString(getABRReturnCode());
/* 234 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 238 */       buildReportFooter();
/*     */       
/* 240 */       if (!isReadOnly()) {
/* 241 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 247 */     String str = "";
/* 248 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 250 */     while (paramString1.length() > 0 && i >= 0) {
/* 251 */       str = str + paramString1.substring(0, i) + paramString3;
/* 252 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 253 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 255 */     str = str + paramString1;
/* 256 */     return str;
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
/* 267 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 276 */     return "Feature in Feature Integrity Check ABR.";
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
/* 287 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 297 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 307 */     return "FCINFCINTCHECKABR01.java,v 1.4 2006/03/03 19:23:28 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 317 */     return "FCINFCINTCHECKABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\FCINFCINTCHECKABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */