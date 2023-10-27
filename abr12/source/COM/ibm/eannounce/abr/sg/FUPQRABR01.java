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
/*     */ public class FUPQRABR01
/*     */   extends PokBaseABR
/*     */ {
/* 114 */   public static final Hashtable c_hshEntities = new Hashtable<>();
/*     */   static {
/* 116 */     c_hshEntities.put("TECHCAPABILITY", "HI");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static final String ABR = new String("FUPQRABR01");
/*     */   
/* 126 */   private EntityGroup m_egParent = null;
/* 127 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 135 */     EntityGroup entityGroup1 = null;
/* 136 */     EntityGroup entityGroup2 = null;
/* 137 */     EntityGroup entityGroup3 = null;
/*     */     
/*     */     try {
/* 140 */       start_ABRBuild();
/* 141 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 142 */       this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */       
/* 144 */       if (this.m_egParent == null) {
/* 145 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 148 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 150 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 153 */       if (this.m_eiParent == null) {
/* 154 */         logMessage(ABR + ":" + 
/*     */ 
/*     */             
/* 157 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 159 */         setReturnCode(-1);
/*     */         
/*     */         return;
/*     */       } 
/* 163 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 166 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 168 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 170 */           .getEntityID());
/*     */       
/* 172 */       buildReportHeader();
/* 173 */       setControlBlock();
/* 174 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*     */       
/* 176 */       logMessage(ABR + ":" + 
/*     */ 
/*     */           
/* 179 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 181 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 183 */           .getEntityID());
/*     */ 
/*     */       
/* 186 */       setReturnCode(0);
/*     */       
/* 188 */       displayHeader(this.m_egParent, this.m_eiParent);
/*     */       
/* 190 */       if (!checkS0001(this.m_eiParent)) {
/* 191 */         setReturnCode(-1);
/*     */       }
/* 193 */       if (!checkS0002(this.m_eiParent, c_hshEntities)) {
/* 194 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 197 */       entityGroup1 = this.m_elist.getEntityGroup("ORDEROF"); byte b;
/* 198 */       for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 199 */         EntityItem entityItem = entityGroup1.getEntityItem(b);
/* 200 */         if (!checkM0003(entityItem)) {
/* 201 */           setReturnCode(-1);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 206 */       entityGroup1 = this.m_elist.getEntityGroup("FUPFUPMGMTGRP");
/* 207 */       for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 208 */         EntityItem entityItem = entityGroup1.getEntityItem(b);
/* 209 */         if (!checkM0005(entityItem, "FUPMEMBERFUPOMG")) {
/* 210 */           setReturnCode(-1);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 215 */       entityGroup1 = this.m_elist.getEntityGroup("FUPPOFMGMTGRP");
/* 216 */       for (b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 217 */         EntityItem entityItem = entityGroup1.getEntityItem(b);
/* 218 */         if (!checkM0005(entityItem, "POFMEMBERFUPOMG")) {
/* 219 */           setReturnCode(-1);
/*     */         }
/*     */       } 
/*     */       
/* 223 */       if (!checkM0007(this.m_egParent, this.m_eiParent)) {
/* 224 */         setReturnCode(-1);
/*     */       }
/* 226 */       if (!checkM0008(this.m_egParent, this.m_eiParent)) {
/* 227 */         setReturnCode(-1);
/*     */       }
/* 229 */       if (!checkH0003(this.m_eiParent)) {
/* 230 */         setReturnCode(-1);
/*     */       }
/* 232 */       if (!checkH0006(this.m_eiParent)) {
/* 233 */         setReturnCode(-1);
/*     */       }
/* 235 */       if (!checkV0001(this.m_eiParent)) {
/* 236 */         setReturnCode(-1);
/*     */       }
/*     */       
/* 239 */       if (getReturnCode() == 0) {
/*     */         
/* 241 */         setFlagValue(this.m_eiParent, 
/*     */             
/* 243 */             getStatusAttributeCode(this.m_eiParent), 
/* 244 */             getNextStatusCode(this.m_eiParent));
/* 245 */         entityGroup2 = this.m_elist.getEntityGroup("FUPFUPMGMTGRP");
/* 246 */         for (b = 0; b < entityGroup2.getEntityItemCount(); b++) {
/* 247 */           EntityItem entityItem = entityGroup2.getEntityItem(b);
/* 248 */           setFlagValue(entityItem, 
/*     */               
/* 250 */               getStatusAttributeCode(entityItem), 
/* 251 */               getNextStatusCode(entityItem));
/*     */         } 
/*     */ 
/*     */         
/* 255 */         entityGroup3 = this.m_elist.getEntityGroup("FUPPOFMGMTGRP");
/* 256 */         for (b = 0; b < entityGroup3.getEntityItemCount(); b++) {
/* 257 */           EntityItem entityItem = entityGroup3.getEntityItem(b);
/* 258 */           setFlagValue(entityItem, 
/*     */               
/* 260 */               getStatusAttributeCode(entityItem), 
/* 261 */               getNextStatusCode(entityItem));
/*     */         } 
/*     */         
/* 264 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 265 */           setNow();
/* 266 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/* 269 */       println("<br /><b>" + 
/*     */           
/* 271 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 274 */               getABRDescription(), 
/* 275 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/*     */       
/* 278 */       log(
/* 279 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 282 */               getABRDescription(), 
/* 283 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 285 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 286 */       setReturnCode(-2);
/* 287 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 291 */           .getMessage() + "</font></h3>");
/*     */       
/* 293 */       logError(lockPDHEntityException.getMessage());
/* 294 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 295 */       setReturnCode(-2);
/* 296 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 298 */           .getMessage() + "</font></h3>");
/*     */       
/* 300 */       logError(updatePDHEntityException.getMessage());
/* 301 */     } catch (Exception exception) {
/*     */       
/* 303 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 304 */       println("" + exception);
/*     */ 
/*     */       
/* 307 */       if (getABRReturnCode() != -2) {
/* 308 */         setReturnCode(-3);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 314 */       setDGString(getABRReturnCode());
/* 315 */       setDGRptName("FUPQRABR01");
/* 316 */       setDGRptClass("FUPQRABR01");
/* 317 */       printDGSubmitString();
/*     */ 
/*     */       
/* 320 */       buildReportFooter();
/*     */       
/* 322 */       if (!isReadOnly()) {
/* 323 */         clearSoftLock();
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
/*     */   
/*     */   protected String getABREntityDesc(String paramString, int paramInt) {
/* 338 */     return null;
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
/* 349 */     return "<br /><br />";
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
/* 360 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 370 */     return "FUPQRABR01.java,v 1.17 2006/03/03 19:23:29 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 380 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\FUPQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */