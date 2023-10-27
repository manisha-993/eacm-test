/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.CATCBPDG;
/*     */ import COM.ibm.eannounce.objects.CATCCTOPDG;
/*     */ import COM.ibm.eannounce.objects.CATCSOLPDG;
/*     */ import COM.ibm.eannounce.objects.CATCVARPDG;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.Database;
/*     */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*     */ import COM.ibm.opicmpdh.middleware.Profile;
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
/*     */ public class ECCMCATLGPUBABR05
/*     */   extends PokBaseABR
/*     */ {
/*  58 */   public static final String ABR = new String("ECCMCATLGPUBABR05");
/*  59 */   private EntityItem m_ei = null;
/*  60 */   private PDGUtility m_utility = new PDGUtility();
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String STARTDATE = "1980-01-01-00.00.00.000000";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String FOREVER = "9999-12-31-00.00.00.000000";
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  73 */     DatePackage datePackage = null;
/*  74 */     String str1 = null;
/*     */     
/*  76 */     EntityGroup entityGroup = null;
/*     */     
/*  78 */     String str2 = null;
/*  79 */     String str3 = System.getProperty("line.separator");
/*     */     try {
/*  81 */       start_ABRBuild(false);
/*  82 */       setReturnCode(0);
/*  83 */       buildReportHeaderII();
/*     */       
/*  85 */       datePackage = this.m_db.getDates();
/*  86 */       str1 = datePackage.getNow();
/*     */       
/*  88 */       String str4 = this.m_utility.getDate(str1.substring(0, 10), 15);
/*  89 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/*  90 */       this.m_ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/*  92 */       println("<br><b>" + this.m_ei.getKey() + "</b>");
/*  93 */       printNavigateAttributes(this.m_ei, entityGroup, true);
/*     */       
/*  95 */       String str5 = this.m_utility.getAttrValueDesc(this.m_ei, "GENAREANAME");
/*  96 */       String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(this.m_db, this.m_prof, "OFFCOUNTRY", str5);
/*  97 */       if (arrayOfString == null || arrayOfString.length <= 0) {
/*  98 */         System.out.println(getABRVersion() + " unable to find OFFCOUNTRY for desc: " + str5);
/*  99 */         setCreateDGEntity(false);
/* 100 */         setReturnCode(-1);
/*     */       } else {
/* 102 */         String str = arrayOfString[0];
/* 103 */         if (!isCATLGCNTRYExist(this.m_db, this.m_prof, str)) {
/* 104 */           setCreateDGEntity(false);
/* 105 */           setReturnCode(-1);
/* 106 */           System.out.println(getABRVersion() + " no CATLGCNTRY exists for " + str);
/*     */         } 
/*     */       } 
/*     */       
/* 110 */       if (getReturnCode() == 0)
/*     */       {
/* 112 */         if (this.m_ei.getEntityType().equals("CCTO")) {
/*     */ 
/*     */ 
/*     */           
/* 116 */           String str6 = this.m_utility.getAttrValue(this.m_ei, "CCOSOLTARGANNDATE");
/* 117 */           String str7 = this.m_utility.getAttrValue(this.m_ei, "CCOSOLSTATUS");
/* 118 */           int i = this.m_utility.dateCompare(str6, str4);
/* 119 */           if (i == 2 || str7.equals("0040") || str7.equals("0020")) {
/* 120 */             setReturnCode(0);
/* 121 */             log("CATLGPUBABR05 generating data");
/* 122 */             CATCCTOPDG cATCCTOPDG = new CATCCTOPDG(null, this.m_db, this.m_prof, "CATCCTOPDG");
/* 123 */             cATCCTOPDG.setEntityItem(this.m_ei);
/* 124 */             cATCCTOPDG.executeAction(this.m_db, this.m_prof);
/* 125 */             log("CATLGPUBABR05 finish generating data");
/*     */           } else {
/* 127 */             setCreateDGEntity(false);
/* 128 */             setReturnCode(-1);
/*     */           }
/*     */         
/* 131 */         } else if (this.m_ei.getEntityType().equals("CSOL")) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 136 */           String str6 = this.m_utility.getAttrValue(this.m_ei, "TARG_ANN_DATE_CT");
/* 137 */           String str7 = this.m_utility.getAttrValue(this.m_ei, "CSOLSTATUS");
/* 138 */           int i = this.m_utility.dateCompare(str6, str4);
/* 139 */           if (i == 2 || str7.equals("0040") || str7.equals("0020")) {
/* 140 */             setReturnCode(0);
/* 141 */             log("CATLGPUBABR05 generating data");
/* 142 */             CATCSOLPDG cATCSOLPDG = new CATCSOLPDG(null, this.m_db, this.m_prof, "CATCSOLPDG");
/* 143 */             cATCSOLPDG.setEntityItem(this.m_ei);
/* 144 */             cATCSOLPDG.executeAction(this.m_db, this.m_prof);
/* 145 */             log("CATLGPUBABR05 finish generating data");
/*     */           } else {
/*     */             
/* 148 */             setCreateDGEntity(false);
/* 149 */             setReturnCode(-1);
/*     */           }
/*     */         
/* 152 */         } else if (this.m_ei.getEntityType().equals("CVAR")) {
/*     */ 
/*     */ 
/*     */           
/* 156 */           String str6 = this.m_utility.getAttrValue(this.m_ei, "TARGANNDATE_CVAR");
/* 157 */           String str7 = this.m_utility.getAttrValue(this.m_ei, "STATUS_CVAR");
/* 158 */           int i = this.m_utility.dateCompare(str6, str4);
/* 159 */           if (i == 2 || str7.equals("0040") || str7.equals("0020")) {
/* 160 */             setReturnCode(0);
/* 161 */             log("CATLGPUBABR05 generating data");
/* 162 */             CATCVARPDG cATCVARPDG = new CATCVARPDG(null, this.m_db, this.m_prof, "CATCVARPDG");
/* 163 */             cATCVARPDG.setEntityItem(this.m_ei);
/* 164 */             cATCVARPDG.executeAction(this.m_db, this.m_prof);
/* 165 */             log("CATLGPUBABR05 finish generating data");
/*     */           } else {
/*     */             
/* 168 */             setCreateDGEntity(false);
/* 169 */             setReturnCode(-1);
/*     */           }
/*     */         
/* 172 */         } else if (this.m_ei.getEntityType().equals("CB")) {
/*     */ 
/*     */ 
/*     */           
/* 176 */           String str6 = this.m_utility.getAttrValue(this.m_ei, "TARG_ANN_DATE_CB");
/* 177 */           String str7 = this.m_utility.getAttrValue(this.m_ei, "CBSOLSTATUS");
/* 178 */           int i = this.m_utility.dateCompare(str6, str4);
/* 179 */           if (i == 2 || str7.equals("0040") || str7.equals("0020")) {
/* 180 */             setReturnCode(0);
/* 181 */             log("CATLGPUBABR05 generating data");
/* 182 */             CATCBPDG cATCBPDG = new CATCBPDG(null, this.m_db, this.m_prof, "CATCBPDG");
/* 183 */             cATCBPDG.setEntityItem(this.m_ei);
/* 184 */             cATCBPDG.executeAction(this.m_db, this.m_prof);
/* 185 */             log("CATLGPUBABR05 finish generating data");
/*     */           } else {
/* 187 */             setCreateDGEntity(false);
/* 188 */             setReturnCode(-1);
/*     */           } 
/*     */         } 
/*     */       }
/* 192 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 193 */       setReturnCode(-2);
/* 194 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 198 */           .getMessage() + "</font></h3>");
/*     */       
/* 200 */       logError(lockPDHEntityException.getMessage());
/* 201 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 202 */       setReturnCode(-2);
/* 203 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 205 */           .getMessage() + "</font></h3>");
/*     */       
/* 207 */       logError(updatePDHEntityException.getMessage());
/* 208 */     } catch (SBRException sBRException) {
/* 209 */       String str = sBRException.toString();
/* 210 */       int i = str.indexOf("(ok)");
/* 211 */       if (i < 0) {
/* 212 */         setReturnCode(-2);
/* 213 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 215 */             replace(str, str3, "<br>") + "</font></h3>");
/*     */         
/* 217 */         logError(sBRException.toString());
/*     */       } else {
/* 219 */         str = str.substring(0, i);
/* 220 */         println(replace(str, str3, "<br>"));
/*     */       } 
/* 222 */     } catch (Exception exception) {
/*     */       
/* 224 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 225 */       println("" + exception);
/* 226 */       exception.printStackTrace();
/*     */       
/* 228 */       if (getABRReturnCode() != -2) {
/* 229 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 232 */       println("<br /><b>" + 
/*     */           
/* 234 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 237 */               getABRDescription(), 
/* 238 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 241 */       log(buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 244 */               getABRDescription(), 
/* 245 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 252 */       str2 = getABRDescription() + ":" + this.m_abri.getEntityType() + ":" + this.m_abri.getEntityID();
/* 253 */       if (str2.length() > 64) {
/* 254 */         str2 = str2.substring(0, 64);
/*     */       }
/* 256 */       setDGTitle(str2);
/* 257 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 260 */       setDGString(getABRReturnCode());
/* 261 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 267 */       if (!isReadOnly()) {
/* 268 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isCATLGCNTRYExist(Database paramDatabase, Profile paramProfile, String paramString) {
/*     */     try {
/* 275 */       StringBuffer stringBuffer = new StringBuffer();
/* 276 */       stringBuffer.append("map_OFFCOUNTRY=" + paramString);
/*     */       
/* 278 */       String str = new String("SRDCATLGCNTRY01");
/* 279 */       EntityItem[] arrayOfEntityItem = this.m_utility.dynaSearch(paramDatabase, paramProfile, null, str, "CATLGCNTRY", stringBuffer.toString());
/* 280 */       if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/* 281 */         return false;
/*     */       }
/* 283 */       return true;
/*     */     }
/* 285 */     catch (Exception exception) {
/* 286 */       exception.printStackTrace();
/*     */       
/* 288 */       return false;
/*     */     } 
/*     */   }
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 292 */     String str = "";
/* 293 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 295 */     while (paramString1.length() > 0 && i >= 0) {
/* 296 */       str = str + paramString1.substring(0, i) + paramString3;
/* 297 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 298 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 300 */     str = str + paramString1;
/* 301 */     return str;
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
/* 312 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 321 */     return "Catalog Offering Publication For CSOL ABR";
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
/* 332 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 342 */     return new String("$Revision: 1.11 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 352 */     return "$Id: ECCMCATLGPUBABR05.java,v 1.11 2008/01/30 19:27:19 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 362 */     return "ECCMCATLGPUBABR05.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ECCMCATLGPUBABR05.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */