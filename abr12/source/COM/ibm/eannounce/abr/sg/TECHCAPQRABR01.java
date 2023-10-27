/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TECHCAPQRABR01
/*     */   extends PokBaseABR
/*     */ {
/*     */   public static final String ABR = "TECHCAPQRABR01";
/*     */   public static final String TECHCAPSTATUS = "TECHCAPSTATUS";
/*     */   
/*     */   public static String getVersion() {
/* 192 */     return "TECHCAPQRABR01.java,v 1.47 2006/03/03 19:23:30 bala Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 202 */     return getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   private EntityGroup m_egParent = null;
/* 213 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 221 */     String str = null;
/*     */     
/*     */     try {
/* 224 */       start_ABRBuild();
/*     */ 
/*     */       
/* 227 */       this.m_egParent = this.m_elist.getParentEntityGroup();
/* 228 */       this
/* 229 */         .m_eiParent = (this.m_egParent == null) ? null : this.m_egParent.getEntityItem(0);
/*     */       
/* 231 */       if (this.m_egParent == null) {
/* 232 */         logMessage("TECHCAPQRABR01:" + 
/*     */ 
/*     */             
/* 235 */             getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */         
/* 237 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 240 */       if (this.m_eiParent == null) {
/* 241 */         logMessage("TECHCAPQRABR01:" + 
/*     */ 
/*     */             
/* 244 */             getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */         
/* 246 */         setReturnCode(-1);
/*     */         return;
/*     */       } 
/* 249 */       logMessage("TECHCAPQRABR01:" + 
/*     */ 
/*     */           
/* 252 */           getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */           
/* 254 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 256 */           .getEntityID());
/* 257 */       buildReportHeader();
/* 258 */       setControlBlock();
/* 259 */       setDGTitle(setDGName(this.m_eiParent, "TECHCAPQRABR01"));
/*     */       
/* 261 */       logMessage("TECHCAPQRABR01:" + 
/*     */ 
/*     */           
/* 264 */           getVersion() + ":Setup Complete:" + this.m_eiParent
/*     */           
/* 266 */           .getEntityType() + ":" + this.m_eiParent
/*     */           
/* 268 */           .getEntityID());
/*     */ 
/*     */       
/* 271 */       setReturnCode(0);
/*     */       
/* 273 */       displayHeader(this.m_egParent, this.m_eiParent);
/*     */       
/* 275 */       if (!checkM0008(this.m_egParent, this.m_eiParent)) {
/* 276 */         setReturnCode(-1);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 283 */       if (getReturnCode() == 0) {
/*     */         
/* 285 */         setFlagValue(this.m_eiParent, 
/*     */             
/* 287 */             getStatusAttributeCode(this.m_eiParent), 
/* 288 */             getNextStatusCode(this.m_eiParent));
/*     */         
/* 290 */         if (isChangeRev(this.m_eiParent) || isChangeFinal(this.m_eiParent)) {
/* 291 */           setNow();
/* 292 */           println(processChangeReport(getT1(this.m_eiParent), getNow()));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       println("<br /><b>" + 
/*     */           
/* 301 */           buildMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 304 */               getABRDescription(), 
/* 305 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }) + "</b>");
/* 307 */       log(
/* 308 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 311 */               getABRDescription(), 
/* 312 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 314 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 315 */       setReturnCode(-2);
/* 316 */       println("<h3><font color=red>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 320 */           .getMessage() + "</font></h3>");
/*     */       
/* 322 */       logError(lockPDHEntityException.getMessage());
/* 323 */       lockPDHEntityException.printStackTrace();
/* 324 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 325 */       setReturnCode(-2);
/* 326 */       println("<h3><font color=red>UpdatePDH error: " + updatePDHEntityException
/*     */           
/* 328 */           .getMessage() + "</font></h3>");
/*     */       
/* 330 */       logError(updatePDHEntityException.getMessage());
/* 331 */       updatePDHEntityException.printStackTrace();
/* 332 */     } catch (Exception exception) {
/* 333 */       println("Error in " + this.m_abri.getABRCode() + ":" + exception.getMessage());
/* 334 */       println("" + exception);
/*     */ 
/*     */       
/* 337 */       if (getABRReturnCode() != -2) {
/* 338 */         setReturnCode(-3);
/*     */       }
/* 340 */       exception.printStackTrace();
/*     */       
/* 342 */       StringWriter stringWriter = new StringWriter();
/* 343 */       exception.printStackTrace(new PrintWriter(stringWriter));
/* 344 */       str = stringWriter.toString();
/* 345 */       println(str);
/*     */       
/* 347 */       logMessage("Error in " + this.m_abri
/* 348 */           .getABRCode() + ":" + exception.getMessage());
/* 349 */       logMessage("" + exception);
/*     */     }
/*     */     finally {
/*     */       
/* 353 */       setDGString(getABRReturnCode());
/* 354 */       setDGRptName("TECHCAPQRABR01");
/* 355 */       setDGRptClass("TECHCAPQRABR01");
/* 356 */       printDGSubmitString();
/*     */ 
/*     */       
/* 359 */       buildReportFooter();
/*     */       
/* 361 */       if (!isReadOnly()) {
/* 362 */         clearSoftLock();
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
/* 376 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 386 */     return "This checks to see if the Techncal Capability Record has a valid Parent/Child classification setting in both directions.  If successfull, it will <br/>transition the status and printa change report if it was currently in a change request state.</br>";
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
/* 397 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 407 */     return new String("1.47");
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\TECHCAPQRABR01.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */