/*     */ package COM.ibm.eannounce.abr.pcd;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.CATCBPDG;
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
/*     */ public class ECCMCATLGPUBABR08
/*     */   extends PokBaseABR
/*     */ {
/*  34 */   public static final String ABR = new String("ECCMECCMCATLGPUBABR08");
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
/*  62 */       String str4 = this.m_utility.getDate(str1.substring(0, 10), 15);
/*  63 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/*  64 */       this.m_ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/*  66 */       println("<br><b>" + this.m_ei.getKey() + "</b>");
/*  67 */       printNavigateAttributes(this.m_ei, entityGroup, true);
/*     */       
/*  69 */       String str5 = this.m_utility.getAttrValueDesc(this.m_ei, "GENAREANAME");
/*  70 */       String[] arrayOfString = this.m_utility.getFlagCodeForExactDesc(this.m_db, this.m_prof, "OFFCOUNTRY", str5);
/*  71 */       if (arrayOfString == null || arrayOfString.length <= 0) {
/*  72 */         System.out.println(getABRVersion() + " unable to find OFFCOUNTRY for desc: " + str5);
/*  73 */         setCreateDGEntity(false);
/*  74 */         setReturnCode(-1);
/*     */       } else {
/*  76 */         String str = arrayOfString[0];
/*  77 */         if (!isCATLGCNTRYExist(this.m_db, this.m_prof, str)) {
/*  78 */           setCreateDGEntity(false);
/*  79 */           setReturnCode(-1);
/*  80 */           System.out.println(getABRVersion() + " no CATLGCNTRY exists for " + str);
/*     */         } 
/*     */       } 
/*     */       
/*  84 */       if (getReturnCode() == 0 && 
/*  85 */         this.m_ei.getEntityType().equals("CB")) {
/*     */ 
/*     */ 
/*     */         
/*  89 */         String str6 = this.m_utility.getAttrValue(this.m_ei, "TARG_ANN_DATE_CB");
/*  90 */         String str7 = this.m_utility.getAttrValue(this.m_ei, "CBSOLSTATUS");
/*  91 */         int i = this.m_utility.dateCompare(str6, str4);
/*  92 */         if (i == 2 || str7.equals("0040") || str7.equals("0020")) {
/*  93 */           setReturnCode(0);
/*  94 */           log("ECCMCATLGPUBABR08 generating data");
/*  95 */           CATCBPDG cATCBPDG = new CATCBPDG(null, this.m_db, this.m_prof, "CATCBPDG");
/*  96 */           cATCBPDG.setEntityItem(this.m_ei);
/*  97 */           cATCBPDG.executeAction(this.m_db, this.m_prof);
/*  98 */           log("ECCMCATLGPUBABR08 finish generating data");
/*     */         } else {
/* 100 */           setCreateDGEntity(false);
/* 101 */           setReturnCode(-1);
/*     */         }
/*     */       
/*     */       } 
/* 105 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 106 */       setReturnCode(-2);
/* 107 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 111 */           .getMessage() + "</font></h3>");
/*     */       
/* 113 */       logError(lockPDHEntityException.getMessage());
/* 114 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 115 */       setReturnCode(-2);
/* 116 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 118 */           .getMessage() + "</font></h3>");
/*     */       
/* 120 */       logError(updatePDHEntityException.getMessage());
/* 121 */     } catch (SBRException sBRException) {
/* 122 */       String str = sBRException.toString();
/* 123 */       int i = str.indexOf("(ok)");
/* 124 */       if (i < 0) {
/* 125 */         setReturnCode(-2);
/* 126 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 128 */             replace(str, str3, "<br>") + "</font></h3>");
/*     */         
/* 130 */         logError(sBRException.toString());
/*     */       } else {
/* 132 */         str = str.substring(0, i);
/* 133 */         println(replace(str, str3, "<br>"));
/*     */       } 
/* 135 */     } catch (Exception exception) {
/*     */       
/* 137 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 138 */       println("" + exception);
/* 139 */       exception.printStackTrace();
/*     */       
/* 141 */       if (getABRReturnCode() != -2) {
/* 142 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 145 */       println("<br /><b>" + 
/*     */           
/* 147 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 150 */               getABRDescription(), 
/* 151 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 154 */       log(buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 157 */               getABRDescription(), 
/* 158 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 165 */       str2 = getABRDescription() + ":" + this.m_abri.getEntityType() + ":" + this.m_abri.getEntityID();
/* 166 */       if (str2.length() > 64) {
/* 167 */         str2 = str2.substring(0, 64);
/*     */       }
/* 169 */       setDGTitle(str2);
/* 170 */       setDGRptName(ABR);
/*     */ 
/*     */       
/* 173 */       setDGString(getABRReturnCode());
/* 174 */       printDGSubmitString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 180 */       if (!isReadOnly()) {
/* 181 */         clearSoftLock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isCATLGCNTRYExist(Database paramDatabase, Profile paramProfile, String paramString) {
/*     */     try {
/* 188 */       StringBuffer stringBuffer = new StringBuffer();
/* 189 */       stringBuffer.append("map_OFFCOUNTRY=" + paramString);
/*     */       
/* 191 */       String str = new String("SRDCATLGCNTRY01");
/* 192 */       EntityItem[] arrayOfEntityItem = this.m_utility.dynaSearch(paramDatabase, paramProfile, null, str, "CATLGCNTRY", stringBuffer.toString());
/* 193 */       if (arrayOfEntityItem == null || arrayOfEntityItem.length <= 0) {
/* 194 */         return false;
/*     */       }
/* 196 */       return true;
/*     */     }
/* 198 */     catch (Exception exception) {
/* 199 */       exception.printStackTrace();
/*     */       
/* 201 */       return false;
/*     */     } 
/*     */   }
/*     */   private String replace(String paramString1, String paramString2, String paramString3) {
/* 205 */     String str = "";
/* 206 */     int i = paramString1.indexOf(paramString2);
/*     */     
/* 208 */     while (paramString1.length() > 0 && i >= 0) {
/* 209 */       str = str + paramString1.substring(0, i) + paramString3;
/* 210 */       paramString1 = paramString1.substring(i + paramString2.length());
/* 211 */       i = paramString1.indexOf(paramString2);
/*     */     } 
/* 213 */     str = str + paramString1;
/* 214 */     return str;
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
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 234 */     return "Catalog Offering Publication For CB ABR";
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
/* 245 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 255 */     return new String("$Revision: 1.3 $");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 265 */     return "$Id: ECCMCATLGPUBABR08.java,v 1.3 2008/01/30 19:27:19 wendy Exp $";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 275 */     return "ECCMECCMCATLGPUBABR08.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\pcd\ECCMCATLGPUBABR08.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */