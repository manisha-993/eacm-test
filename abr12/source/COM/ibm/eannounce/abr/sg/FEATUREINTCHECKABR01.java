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
/*     */ public class FEATUREINTCHECKABR01
/*     */   extends PokBaseABR
/*     */ {
/*  41 */   public static final String ABR = new String("FEATUREINTCHECKABR01");
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
/*     */   public void execute_run() {
/*  53 */     String str1 = null;
/*  54 */     String str2 = null;
/*  55 */     String str3 = null;
/*  56 */     String str4 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  59 */       StringBuffer stringBuffer = new StringBuffer();
/*  60 */       PDGUtility pDGUtility = new PDGUtility();
/*  61 */       start_ABRBuild();
/*     */       
/*  63 */       buildReportHeaderII();
/*     */       
/*  65 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  66 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  67 */       println("<br><b>Feature: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  69 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  70 */       setReturnCode(0);
/*     */       
/*  72 */       str1 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  74 */           .getEntityType(), this.m_ei
/*  75 */           .getEntityID(), "FEATURECODEDUP");
/*     */ 
/*     */       
/*  78 */       str2 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  80 */           .getEntityType(), this.m_ei
/*  81 */           .getEntityID(), "INVENTORYGROUP");
/*     */ 
/*     */       
/*  84 */       if (str1 != null) {
/*     */         
/*  86 */         StringBuffer stringBuffer1 = new StringBuffer();
/*  87 */         stringBuffer1.append("map_FEATURECODE=" + str1 + ";");
/*  88 */         stringBuffer1.append("map_INVENTORYGROUP=" + str2);
/*     */         
/*  90 */         EntityItem[] arrayOfEntityItem = pDGUtility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDFEATURE3", "FEATURE", stringBuffer1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  96 */             .toString());
/*     */         
/*  98 */         if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/*  99 */           setReturnCode(-1);
/* 100 */           stringBuffer.append("Error:There's no FEATURE with feature code " + str1 + " and inventory group " + str2 + str4);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 109 */       if (getReturnCode() == -1 && stringBuffer.toString().length() > 0) {
/* 110 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 111 */         OPICMList oPICMList = new OPICMList();
/* 112 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=" + stringBuffer.toString());
/* 113 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/* 114 */       } else if (getReturnCode() == 0) {
/* 115 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 116 */         OPICMList oPICMList = new OPICMList();
/* 117 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=Passed:OK");
/* 118 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/*     */     
/* 121 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 122 */       setReturnCode(-2);
/* 123 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 127 */           .getMessage() + "</font></h3>");
/*     */       
/* 129 */       logError(lockPDHEntityException.getMessage());
/* 130 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 131 */       setReturnCode(-2);
/* 132 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 134 */           .getMessage() + "</font></h3>");
/*     */       
/* 136 */       logError(updatePDHEntityException.getMessage());
/* 137 */     } catch (SBRException sBRException) {
/* 138 */       String str = sBRException.toString();
/* 139 */       int i = str.indexOf("(ok)");
/* 140 */       if (i < 0) {
/* 141 */         setReturnCode(-2);
/* 142 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 144 */             replace(str, str4, "<br>") + "</font></h3>");
/*     */         
/* 146 */         logError(sBRException.toString());
/*     */       } else {
/* 148 */         str = str.substring(0, i);
/* 149 */         println(replace(str, str4, "<br>"));
/*     */       } 
/* 151 */     } catch (Exception exception) {
/*     */       
/* 153 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 154 */       println("" + exception);
/* 155 */       exception.printStackTrace();
/*     */       
/* 157 */       if (getABRReturnCode() != -2) {
/* 158 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 161 */       println("<br /><b>" + 
/*     */           
/* 163 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 166 */               getABRDescription(), 
/* 167 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 170 */       log(
/* 171 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 174 */               getABRDescription(), 
/* 175 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 178 */       str3 = this.m_ei.toString();
/* 179 */       if (str3.length() > 64) {
/* 180 */         str3 = str3.substring(0, 64);
/*     */       }
/* 182 */       setDGTitle(str3);
/* 183 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 186 */       setDGString(getABRReturnCode());
/* 187 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 191 */       buildReportFooter();
/*     */       
/* 193 */       if (!isReadOnly()) {
/* 194 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 200 */     String str = "";
/* 201 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 203 */     while (paramString1.length() > 0 && i >= 0) {
/* 204 */       str = str + paramString1.substring(0, i) + paramString3;
/* 205 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 206 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 208 */     str = str + paramString1;
/* 209 */     return str;
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
/* 220 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 229 */     return "Feature Integrity Check ABR.";
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
/* 240 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 250 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 260 */     return "FEATUREINTCHECKABR01.java,v 1.4 2006/03/03 19:23:28 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 270 */     return "FEATUREINTCHECKABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\FEATUREINTCHECKABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */