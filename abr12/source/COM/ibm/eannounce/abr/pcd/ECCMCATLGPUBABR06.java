/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.CATCCTOPDG;
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
/*     */ public class ECCMCATLGPUBABR06
/*     */   extends PokBaseABR
/*     */ {
/*  33 */   public static final String ABR = new String("ECCMCATLGPUBABR06");
/*  34 */   private EntityItem m_ei = null;
/*  35 */   private PDGUtility m_utility = new PDGUtility();
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
/*  48 */     DatePackage datePackage = null;
/*  49 */     String str1 = null;
/*     */     
/*  51 */     EntityGroup entityGroup = null;
/*     */     
/*  53 */     String str2 = null;
/*  54 */     String str3 = System.getProperty("line.separator");
/*     */     try {
/*  56 */       start_ABRBuild(false);
/*  57 */       setReturnCode(0);
/*  58 */       buildReportHeaderII();
/*     */       
/*  60 */       datePackage = this.m_db.getDates();
/*  61 */       str1 = datePackage.getNow();
/*     */       
/*  63 */       String str4 = this.m_utility.getDate(str1.substring(0, 10), 15);
/*  64 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/*  65 */       this.m_ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/*  67 */       println("<br><b>" + this.m_ei.getKey() + "</b>");
/*  68 */       printNavigateAttributes(this.m_ei, entityGroup, true);
/*     */       
/*  70 */       String str5 = this.m_utility.getAttrValueDesc(this.m_ei, "GENAREANAME");
/*  71 */       String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(this.m_db, this.m_prof, "OFFCOUNTRY", str5);
/*  72 */       if (arrayOfString == null || arrayOfString.length <= 0) {
/*  73 */         System.out.println(getABRVersion() + " unable to find OFFCOUNTRY for desc: " + str5);
/*  74 */         setCreateDGEntity(false);
/*  75 */         setReturnCode(-1);
/*     */       } else {
/*  77 */         String str = arrayOfString[0];
/*  78 */         if (!isCATLGCNTRYExist(this.m_db, this.m_prof, str)) {
/*  79 */           setCreateDGEntity(false);
/*  80 */           setReturnCode(-1);
/*  81 */           System.out.println(getABRVersion() + " no CATLGCNTRY exists for " + str);
/*     */         } 
/*     */       } 
/*     */       
/*  85 */       if (getReturnCode() == 0 && 
/*  86 */         this.m_ei.getEntityType().equals("CCTO")) {
/*     */ 
/*     */ 
/*     */         
/*  90 */         String str6 = this.m_utility.getAttrValue(this.m_ei, "CCOSOLTARGANNDATE");
/*  91 */         String str7 = this.m_utility.getAttrValue(this.m_ei, "CCOSOLSTATUS");
/*  92 */         int i = this.m_utility.dateCompare(str6, str4);
/*  93 */         if (i == 2 || str7.equals("0040") || str7.equals("0020")) {
/*  94 */           setReturnCode(0);
/*  95 */           log("ECCMCATLGPUBABR06 generating data");
/*  96 */           CATCCTOPDG cATCCTOPDG = new CATCCTOPDG(null, this.m_db, this.m_prof, "CATCCTOPDG");
/*  97 */           cATCCTOPDG.setEntityItem(this.m_ei);
/*  98 */           cATCCTOPDG.executeAction(this.m_db, this.m_prof);
/*  99 */           log("ECCMCATLGPUBABR06 finish generating data");
/*     */         } else {
/* 101 */           setCreateDGEntity(false);
/* 102 */           setReturnCode(-1);
/*     */         }
/*     */       
/*     */       } 
/* 106 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 107 */       setReturnCode(-2);
/* 108 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 112 */           .getMessage() + "</font></h3>");
/*     */       
/* 114 */       logError(lockPDHEntityException.getMessage());
/* 115 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 116 */       setReturnCode(-2);
/* 117 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 119 */           .getMessage() + "</font></h3>");
/*     */       
/* 121 */       logError(updatePDHEntityException.getMessage());
/* 122 */     } catch (SBRException sBRException) {
/* 123 */       String str = sBRException.toString();
/* 124 */       int i = str.indexOf("(ok)");
/* 125 */       if (i < 0) {
/* 126 */         setReturnCode(-2);
/* 127 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 129 */             replace(str, str3, "<br>") + "</font></h3>");
/*     */         
/* 131 */         logError(sBRException.toString());
/*     */       } else {
/* 133 */         str = str.substring(0, i);
/* 134 */         println(replace(str, str3, "<br>"));
/*     */       } 
/* 136 */     } catch (Exception exception) {
/*     */       
/* 138 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 139 */       println("" + exception);
/* 140 */       exception.printStackTrace();
/*     */       
/* 142 */       if (getABRReturnCode() != -2) {
/* 143 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 146 */       println("<br /><b>" + 
/*     */           
/* 148 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 151 */               getABRDescription(), 
/* 152 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 155 */       log(buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 158 */               getABRDescription(), 
/* 159 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 166 */       str2 = getABRDescription() + ":" + this.m_abri.getEntityType() + ":" + this.m_abri.getEntityID();
/* 167 */       if (str2.length() > 64) {
/* 168 */         str2 = str2.substring(0, 64);
/*     */       }
/* 170 */       setDGTitle(str2);
/* 171 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 174 */       setDGString(getABRReturnCode());
/* 175 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 181 */       if (!isReadOnly()) {
/* 182 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isCATLGCNTRYExist(Database paramDatabase, Profile paramProfile, String paramString) {
/*     */     try {
/* 189 */       StringBuffer stringBuffer = new StringBuffer();
/* 190 */       stringBuffer.append("map_OFFCOUNTRY=" + paramString);
/*     */       
/* 192 */       String str = new String("SRDCATLGCNTRY01");
/* 193 */       EntityItem[] arrayOfEntityItem = this.m_utility.dynaSearch(paramDatabase, paramProfile, null, str, "CATLGCNTRY", stringBuffer.toString());
/* 194 */       if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/* 195 */         return false;
/*     */       }
/* 197 */       return true;
/*     */     }
/* 199 */     catch (Exception exception) {
/* 200 */       exception.printStackTrace();
/*     */       
/* 202 */       return false;
/*     */     } 
/*     */   }
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 206 */     String str = "";
/* 207 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 209 */     while (paramString1.length() > 0 && i >= 0) {
/* 210 */       str = str + paramString1.substring(0, i) + paramString3;
/* 211 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 212 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 214 */     str = str + paramString1;
/* 215 */     return str;
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
/* 226 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 235 */     return "Catalog Offering Publication For CCTO ABR";
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
/* 246 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 256 */     return new String("$Revision: 1.3 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 266 */     return "$Id: ECCMCATLGPUBABR06.java,v 1.3 2008/01/30 19:27:19 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 276 */     return "ECCMCATLGPUBABR06.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ECCMCATLGPUBABR06.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */