/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
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
/*     */ public class ECCMCATLGPUBABR07
/*     */   extends PokBaseABR
/*     */ {
/*  34 */   public static final String ABR = new String("ECCMCATLGPUBABR07");
/*  35 */   private EntityItem m_ei = null;
/*  36 */   private PDGUtility m_utility = new PDGUtility();
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
/*  49 */     DatePackage datePackage = null;
/*  50 */     String str1 = null;
/*     */     
/*  52 */     EntityGroup entityGroup = null;
/*     */     
/*  54 */     String str2 = null;
/*  55 */     String str3 = System.getProperty("line.separator");
/*     */     try {
/*  57 */       start_ABRBuild(false);
/*  58 */       setReturnCode(0);
/*  59 */       buildReportHeaderII();
/*     */       
/*  61 */       datePackage = this.m_db.getDates();
/*  62 */       str1 = datePackage.getNow();
/*     */       
/*  64 */       String str4 = this.m_utility.getDate(str1.substring(0, 10), 15);
/*  65 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/*  66 */       this.m_ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/*  68 */       println("<br><b>" + this.m_ei.getKey() + "</b>");
/*  69 */       printNavigateAttributes(this.m_ei, entityGroup, true);
/*     */       
/*  71 */       String str5 = this.m_utility.getAttrValueDesc(this.m_ei, "GENAREANAME");
/*  72 */       String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(this.m_db, this.m_prof, "OFFCOUNTRY", str5);
/*  73 */       if (arrayOfString == null || arrayOfString.length <= 0) {
/*  74 */         System.out.println(getABRVersion() + " unable to find OFFCOUNTRY for desc: " + str5);
/*  75 */         setCreateDGEntity(false);
/*  76 */         setReturnCode(-1);
/*     */       } else {
/*  78 */         String str = arrayOfString[0];
/*  79 */         if (!isCATLGCNTRYExist(this.m_db, this.m_prof, str)) {
/*  80 */           setCreateDGEntity(false);
/*  81 */           setReturnCode(-1);
/*  82 */           System.out.println(getABRVersion() + " no CATLGCNTRY exists for " + str);
/*     */         } 
/*     */       } 
/*     */       
/*  86 */       if (getReturnCode() == 0 && 
/*  87 */         this.m_ei.getEntityType().equals("CVAR"))
/*     */       {
/*     */ 
/*     */         
/*  91 */         String str6 = this.m_utility.getAttrValue(this.m_ei, "TARGANNDATE_CVAR");
/*  92 */         String str7 = this.m_utility.getAttrValue(this.m_ei, "STATUS_CVAR");
/*  93 */         int i = this.m_utility.dateCompare(str6, str4);
/*  94 */         if (i == 2 || str7.equals("0040") || str7.equals("0020")) {
/*  95 */           setReturnCode(0);
/*  96 */           log("ECCMCATLGPUBABR07 generating data");
/*  97 */           CATCVARPDG cATCVARPDG = new CATCVARPDG(null, this.m_db, this.m_prof, "CATCVARPDG");
/*  98 */           cATCVARPDG.setEntityItem(this.m_ei);
/*  99 */           cATCVARPDG.executeAction(this.m_db, this.m_prof);
/* 100 */           log("ECCMCATLGPUBABR07 finish generating data");
/*     */         } else {
/*     */           
/* 103 */           setCreateDGEntity(false);
/* 104 */           setReturnCode(-1);
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 109 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 110 */       setReturnCode(-2);
/* 111 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 115 */           .getMessage() + "</font></h3>");
/*     */       
/* 117 */       logError(lockPDHEntityException.getMessage());
/* 118 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 119 */       setReturnCode(-2);
/* 120 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 122 */           .getMessage() + "</font></h3>");
/*     */       
/* 124 */       logError(updatePDHEntityException.getMessage());
/* 125 */     } catch (SBRException sBRException) {
/* 126 */       String str = sBRException.toString();
/* 127 */       int i = str.indexOf("(ok)");
/* 128 */       if (i < 0) {
/* 129 */         setReturnCode(-2);
/* 130 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 132 */             replace(str, str3, "<br>") + "</font></h3>");
/*     */         
/* 134 */         logError(sBRException.toString());
/*     */       } else {
/* 136 */         str = str.substring(0, i);
/* 137 */         println(replace(str, str3, "<br>"));
/*     */       } 
/* 139 */     } catch (Exception exception) {
/*     */       
/* 141 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 142 */       println("" + exception);
/* 143 */       exception.printStackTrace();
/*     */       
/* 145 */       if (getABRReturnCode() != -2) {
/* 146 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 149 */       println("<br /><b>" + 
/*     */           
/* 151 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 154 */               getABRDescription(), 
/* 155 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 158 */       log(buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 161 */               getABRDescription(), 
/* 162 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 169 */       str2 = getABRDescription() + ":" + this.m_abri.getEntityType() + ":" + this.m_abri.getEntityID();
/* 170 */       if (str2.length() > 64) {
/* 171 */         str2 = str2.substring(0, 64);
/*     */       }
/* 173 */       setDGTitle(str2);
/* 174 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 177 */       setDGString(getABRReturnCode());
/* 178 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 184 */       if (!isReadOnly()) {
/* 185 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isCATLGCNTRYExist(Database paramDatabase, Profile paramProfile, String paramString) {
/*     */     try {
/* 192 */       StringBuffer stringBuffer = new StringBuffer();
/* 193 */       stringBuffer.append("map_OFFCOUNTRY=" + paramString);
/*     */       
/* 195 */       String str = new String("SRDCATLGCNTRY01");
/* 196 */       EntityItem[] arrayOfEntityItem = this.m_utility.dynaSearch(paramDatabase, paramProfile, null, str, "CATLGCNTRY", stringBuffer.toString());
/* 197 */       if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/* 198 */         return false;
/*     */       }
/* 200 */       return true;
/*     */     }
/* 202 */     catch (Exception exception) {
/* 203 */       exception.printStackTrace();
/*     */       
/* 205 */       return false;
/*     */     } 
/*     */   }
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 209 */     String str = "";
/* 210 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 212 */     while (paramString1.length() > 0 && i >= 0) {
/* 213 */       str = str + paramString1.substring(0, i) + paramString3;
/* 214 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 215 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 217 */     str = str + paramString1;
/* 218 */     return str;
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
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 238 */     return "Catalog Offering Publication For CVAR ABR";
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
/* 249 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 259 */     return new String("$Revision: 1.3 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 269 */     return "$Id: ECCMCATLGPUBABR07.java,v 1.3 2008/01/30 19:27:19 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 279 */     return "ECCMCATLGPUBABR07.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ECCMCATLGPUBABR07.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */