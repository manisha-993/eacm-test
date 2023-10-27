/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class POFQRABR01
/*     */   extends PokBaseABR
/*     */ {
/* 127 */   public static final Hashtable c_hshEntities = new Hashtable<>();
/*     */   static {
/* 129 */     c_hshEntities.put("PUBLICATION", "HI");
/* 130 */     c_hshEntities.put("RULES", "HI");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String ABR = "POFQRABR01";
/*     */ 
/*     */ 
/*     */   
/* 140 */   private EntityGroup m_egParent = null;
/* 141 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 149 */     EntityGroup entityGroup1 = null;
/* 150 */     EntityGroup entityGroup2 = null;
/*     */     
/*     */     try {
/* 153 */       start_ABRBuild();
/*     */       
/* 155 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 156 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 158 */       if (this.m_egParent == null) {
/* 159 */         logMessage("POFQRABR01:" + 
/*     */ 
/*     */             
/* 162 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 164 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 167 */       if (this.m_eiParent == null) {
/* 168 */         logMessage("POFQRABR01:" + 
/*     */ 
/*     */             
/* 171 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 173 */         setReturnCode(-1);
/*     */         
/*     */         return;
/*     */       } 
/* 177 */       logMessage("POFQRABR01:" + 
/*     */ 
/*     */           
/* 180 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 182 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 184 */           .getEntityID());
/* 185 */       buildReportHeader();
/* 186 */       setControlBlock();
/* 187 */       setDGTitle(setDGName(this.m_eiParent, "POFQRABR01"));
/*     */       
/* 189 */       logMessage("POFQRABR01:" + 
/*     */ 
/*     */           
/* 192 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 194 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 196 */           .getEntityID());
/*     */ 
/*     */       
/* 199 */       setReturnCode(0);
/*     */       
/* 201 */       displayHeader(this.m_egParent, this.m_eiParent);
/*     */       
/* 203 */       if (!checkS0001(this.m_eiParent)) {
/* 204 */         setReturnCode(-1);
/*     */       }
/* 206 */       if (!checkS0002(this.m_eiParent, c_hshEntities)) {
/* 207 */         setReturnCode(-1);
/*     */       }
/* 209 */       if (!checkM0007(this.m_egParent, this.m_eiParent)) {
/* 210 */         setReturnCode(-1);
/*     */       }
/* 212 */       if (!checkM0008(this.m_egParent, this.m_eiParent)) {
/* 213 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 216 */       entityGroup1 = this.m_elist.getEntityGroup("POFPOFMGMTGRP"); byte b;
/* 217 */       for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 218 */         EntityItem entityItem = entityGroup1.getEntityItem(b);
/* 219 */         if (!checkM0005(entityItem, "POFMEMBERPOFOMG")) {
/* 220 */           setReturnCode(-1);
/*     */         }
/*     */       } 
/*     */       
/* 224 */       if (!checkM0020(this.m_eiParent)) {
/* 225 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 228 */       if (getReturnCode() == 0) {
/*     */         
/* 230 */         setFlagValue(this.m_eiParent, 
/*     */             
/* 232 */             getStatusAttributeCode(this.m_eiParent), 
/* 233 */             getNextStatusCode(this.m_eiParent));
/*     */         
/* 235 */         entityGroup2 = this.m_elist.getEntityGroup("POFPOFMGMTGRP");
/* 236 */         for (b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 237 */           EntityItem entityItem = entityGroup2.getEntityItem(b);
/* 238 */           setFlagValue(entityItem, 
/*     */               
/* 240 */               getStatusAttributeCode(entityItem), 
/* 241 */               getNextStatusCode(entityItem));
/*     */         } 
/*     */ 
/*     */         
/* 245 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 246 */           setNow();
/* 247 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/*     */       
/* 251 */       println("<br /><b>" + 
/*     */           
/* 253 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 256 */               getABRDescription(), 
/* 257 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 260 */       log(
/* 261 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 264 */               getABRDescription(), 
/* 265 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 267 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 268 */       setReturnCode(-2);
/* 269 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 273 */           .getMessage() + "</font></h3>");
/*     */       
/* 275 */       logError(lockPDHEntityException.getMessage());
/* 276 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 277 */       setReturnCode(-2);
/* 278 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 280 */           .getMessage() + "</font></h3>");
/*     */       
/* 282 */       logError(updatePDHEntityException.getMessage());
/* 283 */     } catch (Exception exception) {
/*     */       
/* 285 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 286 */       println("" + exception);
/*     */ 
/*     */       
/* 289 */       if (getABRReturnCode() != -2) {
/* 290 */         setReturnCode(-3);
/*     */       }
/*     */     } finally {
/*     */       
/* 294 */       if (getReturnCode() == 0) {
/* 295 */         setReturnCode(0);
/*     */         
/* 297 */         setDGString(getABRReturnCode());
/* 298 */         setDGRptName("POFQRABR01");
/* 299 */         setDGRptClass("POFQRABR01");
/* 300 */         printDGSubmitString();
/*     */ 
/*     */         
/* 303 */         buildReportFooter();
/*     */         
/* 305 */         if (!isReadOnly()) {
/* 306 */           clearSoftLock();
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
/* 321 */     return null;
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
/* 332 */     return "<br />This needs to be defined <br />";
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
/* 343 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 352 */     return new String("1.23");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 362 */     return "POFQRABR01.java,v 1.23 2006/03/03 19:23:29 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 372 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\POFQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */