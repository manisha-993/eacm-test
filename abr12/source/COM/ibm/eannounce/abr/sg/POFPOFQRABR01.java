/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
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
/*     */ public class POFPOFQRABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String ABR = "POFPOFQRABR01";
/*     */   public static final String POFPOFMGMTGRP = "POFPOFMGMTGRP";
/*     */   public static final String POFPOFSTATUS = "POFPOFSTATUS";
/* 123 */   private EntityGroup m_egParent = null;
/* 124 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/*     */     try {
/* 134 */       start_ABRBuild();
/*     */       
/* 136 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 137 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 139 */       if (this.m_egParent == null) {
/* 140 */         logMessage("POFPOFQRABR01:" + 
/*     */ 
/*     */             
/* 143 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 145 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 148 */       if (this.m_eiParent == null) {
/* 149 */         logMessage("POFPOFQRABR01:" + 
/*     */ 
/*     */             
/* 152 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 154 */         setReturnCode(-1);
/*     */         
/*     */         return;
/*     */       } 
/* 158 */       logMessage("POFPOFQRABR01:" + 
/*     */ 
/*     */           
/* 161 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 163 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 165 */           .getEntityID());
/* 166 */       buildReportHeader();
/* 167 */       setControlBlock();
/* 168 */       setDGTitle(setDGName(this.m_eiParent, "POFPOFQRABR01"));
/*     */       
/* 170 */       logMessage("POFPOFQRABR01:" + 
/*     */ 
/*     */           
/* 173 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 175 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 177 */           .getEntityID());
/*     */ 
/*     */       
/* 180 */       setReturnCode(0);
/*     */       
/* 182 */       displayHeader(this.m_egParent, this.m_eiParent);
/*     */       
/* 184 */       if (!checkS0002(this.m_eiParent)) {
/* 185 */         setReturnCode(-1);
/*     */       }
/* 187 */       if (!checkM0007(this.m_egParent, this.m_eiParent)) {
/* 188 */         setReturnCode(-1);
/*     */       }
/* 190 */       if (!checkM0008(this.m_egParent, this.m_eiParent)) {
/* 191 */         setReturnCode(-1);
/*     */       }
/* 193 */       if (!checkM0005(this.m_eiParent, "POFMEMBERPOFOMG")) {
/* 194 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 197 */       if (getReturnCode() == 0) {
/*     */         
/* 199 */         setFlagValue(this.m_eiParent, "POFPOFSTATUS", 
/*     */ 
/*     */             
/* 202 */             getNextStatusCode(this.m_eiParent));
/*     */         
/* 204 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 205 */           setNow();
/* 206 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/*     */       
/* 210 */       println("<br /><b>" + 
/*     */           
/* 212 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 215 */               getABRDescription(), 
/* 216 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 219 */       log(
/* 220 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 223 */               getABRDescription(), 
/* 224 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 226 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 227 */       setReturnCode(-2);
/* 228 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 232 */           .getMessage() + "</font></h3>");
/*     */       
/* 234 */       logError(lockPDHEntityException.getMessage());
/* 235 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 236 */       setReturnCode(-2);
/* 237 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 239 */           .getMessage() + "</font></h3>");
/*     */       
/* 241 */       logError(updatePDHEntityException.getMessage());
/* 242 */     } catch (Exception exception) {
/*     */       
/* 244 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 245 */       println("" + exception);
/*     */ 
/*     */       
/* 248 */       if (getABRReturnCode() != -2) {
/* 249 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 253 */       if (getReturnCode() == 0) {
/* 254 */         setReturnCode(0);
/*     */         
/* 256 */         setDGString(getABRReturnCode());
/* 257 */         setDGRptName("POFPOFQRABR01");
/* 258 */         setDGRptClass("POFPOFQRABR01");
/* 259 */         printDGSubmitString();
/*     */ 
/*     */         
/* 262 */         buildReportFooter();
/*     */         
/* 264 */         if (!isReadOnly()) {
/* 265 */           clearSoftLock();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 280 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 291 */     return "<br />This needs to be defined <br />";
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
/* 302 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 311 */     return new String("1.25");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 321 */     return "POFPOFQRABR01.java,v 1.25 2006/03/03 19:23:29 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 331 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\POFPOFQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */