/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.MKTGCOMPATPDG;
/*     */ import COM.ibm.eannounce.objects.PDGUtility;
/*     */ import COM.ibm.eannounce.objects.SBRException;
/*     */ import COM.ibm.opicmpdh.middleware.DatePackage;
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
/*     */ public class MKTGCOMPATABR01
/*     */   extends PokBaseABR
/*     */ {
/*  63 */   public static final String ABR = new String("MKTGCOMPATABR01");
/*  64 */   private PDGUtility m_utility = new PDGUtility();
/*  65 */   private EntityItem m_ei = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String STARTDATE = "1980-01-01-00.00.00.000000";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*  77 */     DatePackage datePackage = null;
/*  78 */     EntityGroup entityGroup = null;
/*     */ 
/*     */     
/*  81 */     String str1 = null;
/*     */     
/*  83 */     String str2 = null;
/*  84 */     String str3 = null;
/*  85 */     String str4 = null;
/*  86 */     String str5 = System.getProperty("line.separator");
/*     */     try {
/*  88 */       start_ABRBuild(false);
/*  89 */       setReturnCode(0);
/*  90 */       buildReportHeaderII();
/*     */       
/*  92 */       datePackage = this.m_db.getDates();
/*  93 */       str1 = datePackage.getNow();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 100 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit", false);
/*     */ 
/*     */       
/* 103 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 109 */         .m_ei = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/*     */       
/* 111 */       println("<br><b>Catalog Country: " + this.m_ei.getKey() + "</b>");
/* 112 */       printNavigateAttributes(this.m_ei, entityGroup, true);
/*     */ 
/*     */ 
/*     */       
/* 116 */       str2 = this.m_utility.getAttrValue(this.m_ei, "CATLGMKTGLASTRUN");
/* 117 */       if (str2 == null || str2
/* 118 */         .length() <= 0) {
/* 119 */         OPICMList oPICMList = new OPICMList();
/* 120 */         oPICMList.put("CATLGMKTGLASTRUN", "CATLGMKTGLASTRUN=1980-01-01-00.00.00.000000");
/*     */ 
/*     */ 
/*     */         
/* 124 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       }
/* 126 */       else if (!this.m_utility.isDateFormat(str2)) {
/* 127 */         println("CATLGMKTGLASTRUN is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000");
/* 128 */         setReturnCode(-1);
/*     */       }
/* 130 */       else if (str2.length() == 10) {
/* 131 */         OPICMList oPICMList = new OPICMList();
/* 132 */         oPICMList.put("CATLGMKTGLASTRUN", "CATLGMKTGLASTRUN=" + str2 + "-00.00.00.000000");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 138 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 143 */       str3 = this.m_utility.getAttrValue(this.m_ei, "OFFCOUNTRY");
/* 144 */       if (str3 == null || str3.length() <= 0) {
/* 145 */         println("OFFCOUNTRY is blank.");
/* 146 */         setReturnCode(-1);
/*     */       } 
/* 148 */       if (getReturnCode() == 0) {
/* 149 */         log("MKTGCOMPATABR01 generating data");
/* 150 */         MKTGCOMPATPDG mKTGCOMPATPDG = new MKTGCOMPATPDG(null, this.m_db, this.m_prof, "MKTGCOMPATPDG");
/*     */         
/* 152 */         mKTGCOMPATPDG.setEntityItem(this.m_ei);
/* 153 */         mKTGCOMPATPDG.executeAction(this.m_db, this.m_prof);
/* 154 */         log("MKTGCOMPATABR01 finish generating data");
/* 155 */         datePackage = this.m_db.getDates();
/* 156 */         str1 = datePackage.getNow();
/*     */         
/* 158 */         OPICMList oPICMList = new OPICMList();
/* 159 */         oPICMList.put("CATLGMKTGLASTRUN", "CATLGMKTGLASTRUN=" + str1);
/*     */         
/* 161 */         this.m_utility.updateAttribute(this.m_db, this.m_prof, this.m_ei, oPICMList);
/*     */       } 
/* 163 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 164 */       setReturnCode(-2);
/* 165 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 169 */           .getMessage() + "</font></h3>");
/*     */       
/* 171 */       logError(lockPDHEntityException.getMessage());
/* 172 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 173 */       setReturnCode(-2);
/* 174 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 176 */           .getMessage() + "</font></h3>");
/*     */       
/* 178 */       logError(updatePDHEntityException.getMessage());
/* 179 */     } catch (SBRException sBRException) {
/* 180 */       String str = sBRException.toString();
/* 181 */       int i = str.indexOf("(ok)");
/* 182 */       if (i < 0) {
/* 183 */         setReturnCode(-2);
/* 184 */         println("<h3><font color=red>Generate Data error: " + 
/*     */             
/* 186 */             replace(str, str5, "<br>") + "</font></h3>");
/*     */         
/* 188 */         logError(sBRException.toString());
/*     */       } else {
/* 190 */         str = str.substring(0, i);
/* 191 */         println(replace(str, str5, "<br>"));
/*     */       } 
/* 193 */     } catch (Exception exception) {
/*     */       
/* 195 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 196 */       println("" + exception);
/* 197 */       exception.printStackTrace();
/*     */       
/* 199 */       if (getABRReturnCode() != -2) {
/* 200 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/* 203 */       println("<br /><b>" + 
/*     */           
/* 205 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 208 */               getABRDescription(), 
/* 209 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 212 */       log(
/* 213 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 216 */               getABRDescription(), 
/* 217 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 225 */       str4 = getABRDescription() + ":" + this.m_abri.getEntityType() + ":" + this.m_abri.getEntityID();
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
/*     */ 
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
/* 276 */     return "Marketing Compatibility ABR";
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
/* 297 */     return new String("1.10");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 307 */     return "MKTGCOMPATABR01.java,v 1.10 2008/01/30 19:39:17 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 317 */     return "MKTGCOMPATABR01.java";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\MKTGCOMPATABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */