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
/*     */ public class MACHTYPEINTCHECKABR01
/*     */   extends PokBaseABR
/*     */ {
/*  41 */   public static final String ABR = new String("MACHTYPEINTCHECKABR01");
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
/*  55 */     String str3 = System.getProperty("line.separator");
/*     */     
/*     */     try {
/*  58 */       StringBuffer stringBuffer = new StringBuffer();
/*  59 */       PDGUtility pDGUtility = new PDGUtility();
/*  60 */       start_ABRBuild();
/*     */       
/*  62 */       buildReportHeaderII();
/*     */       
/*  64 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/*  65 */       this.m_ei = this.m_egParent.getEntityItem(0);
/*  66 */       println("<br><b>Machine Type: " + this.m_ei.getKey() + "</b>");
/*     */       
/*  68 */       printNavigateAttributes(this.m_ei, this.m_egParent, true);
/*  69 */       setReturnCode(0);
/*     */       
/*  71 */       str1 = getAttributeValue(this.m_elist, this.m_ei
/*     */           
/*  73 */           .getEntityType(), this.m_ei
/*  74 */           .getEntityID(), "MACHTYPEATR");
/*     */ 
/*     */       
/*  77 */       if (str1 == null) {
/*  78 */         setReturnCode(-1);
/*  79 */         stringBuffer.append("Error:Machine type is empty.");
/*     */       } 
/*     */ 
/*     */       
/*  83 */       if (getReturnCode() == 0) {
/*     */         
/*  85 */         StringBuffer stringBuffer1 = new StringBuffer();
/*  86 */         stringBuffer1.append("map_MACHTYPEATR=" + str1);
/*     */         
/*  88 */         EntityItem[] arrayOfEntityItem = pDGUtility.dynaSearch(this.m_db, this.m_prof, this.m_ei, "SRDMACHTYPE1", "MACHTYPE", stringBuffer1
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  94 */             .toString());
/*     */         
/*  96 */         if (arrayOfEntityItem.length > 1) {
/*  97 */           setReturnCode(-1);
/*  98 */           stringBuffer.append("Error:There're " + arrayOfEntityItem.length + " MACHTYPEs with machine type " + str1 + str3);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       if (getReturnCode() == -1 && stringBuffer.toString().length() > 0) {
/* 108 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 109 */         OPICMList oPICMList = new OPICMList();
/* 110 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=" + stringBuffer.toString());
/* 111 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/* 112 */       } else if (getReturnCode() == 0) {
/* 113 */         println("<h3><font color=red>" + stringBuffer.toString() + "</h3>");
/* 114 */         OPICMList oPICMList = new OPICMList();
/* 115 */         oPICMList.put("ABRRESULTS", "ABRRESULTS=Passed:OK");
/* 116 */         pDGUtility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/*     */     
/* 119 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 120 */       setReturnCode(-2);
/* 121 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 125 */           .getMessage() + "</font></h3>");
/*     */       
/* 127 */       logError(lockPDHEntityException.getMessage());
/* 128 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 129 */       setReturnCode(-2);
/* 130 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 132 */           .getMessage() + "</font></h3>");
/*     */       
/* 134 */       logError(updatePDHEntityException.getMessage());
/* 135 */     } catch (SBRException sBRException) {
/* 136 */       String str = sBRException.toString();
/* 137 */       int i = str.indexOf("(ok)");
/* 138 */       if (i < 0) {
/* 139 */         setReturnCode(-2);
/* 140 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 142 */             replace(str, str3, "<br>") + "</font></h3>");
/*     */         
/* 144 */         logError(sBRException.toString());
/*     */       } else {
/* 146 */         str = str.substring(0, i);
/* 147 */         println(replace(str, str3, "<br>"));
/*     */       } 
/* 149 */     } catch (Exception exception) {
/*     */       
/* 151 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 152 */       println("" + exception);
/* 153 */       exception.printStackTrace();
/*     */       
/* 155 */       if (getABRReturnCode() != -2) {
/* 156 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 159 */       println("<br /><b>" + 
/*     */           
/* 161 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 164 */               getABRDescription(), 
/* 165 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 168 */       log(
/* 169 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 172 */               getABRDescription(), 
/* 173 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */       
/* 176 */       str2 = this.m_ei.toString();
/* 177 */       if (str2.length() > 64) {
/* 178 */         str2 = str2.substring(0, 64);
/*     */       }
/* 180 */       setDGTitle(str2);
/* 181 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 184 */       setDGString(getABRReturnCode());
/* 185 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */       
/* 189 */       buildReportFooter();
/*     */       
/* 191 */       if (!isReadOnly()) {
/* 192 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 198 */     String str = "";
/* 199 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 201 */     while (paramString1.length() > 0 && i >= 0) {
/* 202 */       str = str + paramString1.substring(0, i) + paramString3;
/* 203 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 204 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 206 */     str = str + paramString1;
/* 207 */     return str;
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
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 227 */     return "Machine Type Integrity Check ABR.";
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
/* 238 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 248 */     return new String("1.4");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 258 */     return "MACHTYPEINTCHECKABR01.java,v 1.4 2006/03/03 19:23:29 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 268 */     return "MACHTYPEINTCHECKABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\MACHTYPEINTCHECKABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */