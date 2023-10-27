/*     */ package COM.ibm.eannounce.abr.sg;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.util.GenerateXL;
/*     */ import COM.ibm.eannounce.abr.util.LockPDHEntityException;
/*     */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*     */ import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
/*     */ import COM.ibm.eannounce.objects.EANAttribute;
/*     */ import COM.ibm.eannounce.objects.EntityGroup;
/*     */ import COM.ibm.eannounce.objects.EntityItem;
/*     */ import COM.ibm.eannounce.objects.EntityList;
/*     */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*     */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MODELABRSS
/*     */   extends PokBaseABR
/*     */ {
/* 147 */   public static final String ABR = new String("MODELABRSS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public static final String EFFECTIVEDATE = new String("EFFECTIVEDATE");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public static final String AVAILTYPE = new String("AVAILTYPE");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public static final String GENAREASELECTION = new String("GENAREASELECTION");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public static final String MKTGNAME = new String("MKTGNAME");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   public static final String COMPONENTID = new String("COMPONENTID");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   public static final String DESCRIPTION = new String("DESCRIPTION");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   public static final String OVERVIEWABSTRACT = new String("OVERVIEWABSTRACT");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public static final String DISTRCODE = new String("DISTRCODE");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public static final String SVCCAT = new String("SVCCAT");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public static final String VAE = new String("VAE");
/*     */   
/* 205 */   private EntityGroup m_egParent = null;
/* 206 */   private EntityItem m_eiParent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute_run() {
/* 219 */     EntityGroup entityGroup = null;
/* 220 */     EntityItem entityItem = null;
/*     */     
/* 222 */     ExtractActionItem extractActionItem = null;
/* 223 */     EntityList entityList = null;
/*     */     
/* 225 */     String str = null;
/*     */ 
/*     */     
/*     */     try {
/* 229 */       start_ABRBuild(false);
/* 230 */       setReturnCode(0);
/*     */ 
/*     */ 
/*     */       
/* 234 */       logMessage("My root entitytype and entityid: " + this.m_abri
/*     */           
/* 236 */           .getEntityType() + this.m_abri
/* 237 */           .getEntityID());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 243 */       entityGroup = new EntityGroup(null, this.m_db, this.m_prof, this.m_abri.getEntityType(), "Edit");
/*     */       
/* 245 */       logMessage("stuff needed to build the ei********************" + entityGroup + this.m_prof + this.m_db);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 256 */       entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, this.m_abri.getEntityType(), this.m_abri.getEntityID());
/* 257 */       logMessage("start ei Dump********************");
/* 258 */       logMessage(entityItem.dump(false));
/* 259 */       logMessage("end ei Dump********************");
/*     */       
/* 261 */       if (entityItem != null) {
/* 262 */         EANAttribute[] arrayOfEANAttribute = new EANAttribute[1];
/* 263 */         arrayOfEANAttribute[0] = entityItem.getAttribute("RPTEXCELVE");
/* 264 */         logMessage("atts[0]:" + arrayOfEANAttribute[0]);
/*     */         
/* 266 */         if (arrayOfEANAttribute[0] == null) {
/* 267 */           logMessage("<br>Your RPTEXCELVE attribute is empty");
/* 268 */           setReturnCode(-1);
/*     */         } else {
/* 270 */           str = arrayOfEANAttribute[0].toString();
/*     */           
/* 272 */           EntityItem[] arrayOfEntityItem = { entityItem };
/*     */           
/* 274 */           extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 286 */           entityList = new EntityList(this.m_db, this.m_prof, extractActionItem, arrayOfEntityItem, this.m_abri.getEntityType());
/* 287 */           logMessage("elist VE Dump********************");
/* 288 */           logMessage(entityList.dump(true));
/* 289 */           logMessage("End elist VE Dump********************");
/*     */           
/* 291 */           this.m_egParent = entityList.getParentEntityGroup();
/* 292 */           this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */           
/* 294 */           logMessage(ABR + ":" + 
/*     */ 
/*     */               
/* 297 */               getVersion() + ":Request to Work on Entity:" + this.m_eiParent
/*     */               
/* 299 */               .getEntityType() + ":" + this.m_eiParent
/*     */               
/* 301 */               .getEntityID());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 311 */           GenerateXL generateXL = new GenerateXL(entityList, this.m_eiParent, ABR + "-" + this.m_eiParent.getEntityType() + "-" + this.m_eiParent.getEntityID(), this.m_db, this.m_prof, "SRDXLENTITY", str, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 317 */           setAttachByteForDG(generateXL.getWBBytes());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 324 */           if (this.m_egParent == null) {
/* 325 */             logMessage(ABR + ":" + 
/*     */ 
/*     */                 
/* 328 */                 getVersion() + ":ERROR:1: m_egParent cannot be established.");
/*     */             
/* 330 */             setReturnCode(-1);
/*     */             return;
/*     */           } 
/* 333 */           if (this.m_eiParent == null) {
/* 334 */             logMessage(ABR + ":" + 
/*     */ 
/*     */                 
/* 337 */                 getVersion() + ":ERROR:2: m_eiParent cannot be established.");
/*     */             
/* 339 */             setReturnCode(-1);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/* 345 */       buildReportHeader();
/* 346 */       setControlBlock();
/* 347 */       setDGTitle(setDGName(this.m_eiParent, ABR));
/*     */       
/* 349 */       displayHeader(this.m_egParent, this.m_eiParent);
/*     */       
/* 351 */       log(
/* 352 */           buildLogMessage("IAB2016I: %1# has %2#.", new String[] {
/*     */ 
/*     */               
/* 355 */               getABRDescription(), 
/* 356 */               (getReturnCode() == 0) ? "Passed" : "Failed"
/*     */             }));
/* 358 */     } catch (LockPDHEntityException lockPDHEntityException) {
/* 359 */       setReturnCode(-2);
/* 360 */       logMessage("<h3>IAB1007E: Could not get soft lock.  Rule execution is terminated.<br />" + lockPDHEntityException
/*     */ 
/*     */ 
/*     */           
/* 364 */           .getMessage() + "</font></h3>");
/*     */       
/* 366 */       logError(lockPDHEntityException.getMessage());
/* 367 */     } catch (UpdatePDHEntityException updatePDHEntityException) {
/* 368 */       setReturnCode(-2);
/* 369 */       logMessage("<h3>UpdatePDH error: " + updatePDHEntityException
/* 370 */           .getMessage() + "</font></h3>");
/* 371 */       logError(updatePDHEntityException.getMessage());
/* 372 */     } catch (Exception exception) {
/*     */       
/* 374 */       logMessage("Error in " + this.m_abri
/* 375 */           .getABRCode() + ":" + exception.getMessage());
/* 376 */       logMessage("" + exception);
/*     */ 
/*     */       
/* 379 */       if (getABRReturnCode() != -2) {
/* 380 */         setReturnCode(-3);
/*     */       }
/*     */       
/* 383 */       exception.printStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 390 */       logMessage("Error in " + this.m_abri
/* 391 */           .getABRCode() + ":" + exception.getMessage());
/* 392 */       logMessage("" + exception);
/*     */     }
/*     */     finally {
/*     */       
/* 396 */       if (getReturnCode() == 0) {
/* 397 */         setReturnCode(0);
/*     */         
/* 399 */         setDGString(getABRReturnCode());
/* 400 */         setDGRptName("MODELABRSS");
/* 401 */         setDGRptClass("MODELABRSS");
/* 402 */         printDGSubmitString();
/*     */ 
/*     */         
/* 405 */         buildReportFooter();
/*     */         
/* 407 */         if (!isReadOnly()) {
/* 408 */           clearSoftLock();
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
/*     */   public void printFAILmessage(EntityGroup paramEntityGroup) {
/* 421 */     logMessage("<br><br><b>" + paramEntityGroup
/*     */         
/* 423 */         .getLongDescription() + " is not at final status.</b>");
/*     */     
/* 425 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printWARNINGmessage(EntityGroup paramEntityGroup) {
/* 435 */     logMessage("<br><br><b>" + paramEntityGroup
/*     */         
/* 437 */         .getLongDescription() + " does not exist (Warning - Pass)</b>");
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
/*     */   public void printFAILmessage_2(EntityGroup paramEntityGroup) {
/* 449 */     logMessage("<br><br><b>" + paramEntityGroup
/*     */         
/* 451 */         .getLongDescription() + " needs to be 'Final'status</b>");
/*     */     
/* 453 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printWARNINGmessage_2(EntityGroup paramEntityGroup) {
/* 463 */     logMessage("<br><br><b>" + paramEntityGroup
/*     */         
/* 465 */         .getLongDescription() + " needs to be 'Final'status (Warning - Pass)</b>");
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
/*     */   public void printFAILmessage_3(EntityGroup paramEntityGroup) {
/* 477 */     logMessage("<br><br><b>" + paramEntityGroup
/*     */         
/* 479 */         .getLongDescription() + " needs to be at least 'Ready for Final Review'</b>");
/*     */     
/* 481 */     setReturnCode(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printWARNINGmessage_3(EntityGroup paramEntityGroup) {
/* 491 */     logMessage("<br><br><b>" + paramEntityGroup
/*     */         
/* 493 */         .getLongDescription() + " needs to be at least 'Ready for Final Review' (Warning - Pass)</b>");
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
/*     */   public void checkMODELstatus() {
/* 505 */     this.m_egParent = this.m_elist.getParentEntityGroup();
/* 506 */     this.m_eiParent = this.m_egParent.getEntityItem(0);
/*     */     
/* 508 */     if (this.m_egParent == null) {
/* 509 */       logMessage("********** 1 Model Not found");
/* 510 */       setReturnCode(-1);
/*     */     } else {
/* 512 */       logMessage(this.m_eiParent.dump(false));
/* 513 */       for (byte b = 0; b < this.m_egParent.getEntityItemCount(); b++) {
/*     */         
/* 515 */         EntityItem entityItem = this.m_egParent.getEntityItem(b);
/*     */         
/* 517 */         logMessage("m_egParent.getEntityItem(i) for Model:" + this.m_egParent
/*     */             
/* 519 */             .getEntityItem(b));
/* 520 */         logMessage("ei for Model:" + entityItem.dump(true));
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
/*     */   public void displayHeader(EntityGroup paramEntityGroup, EntityItem paramEntityItem) {
/* 533 */     if (paramEntityGroup != null && paramEntityGroup != null) {
/* 534 */       logMessage("<FONT SIZE=6><b>" + paramEntityGroup
/*     */           
/* 536 */           .getLongDescription() + "</b></FONT><br>");
/*     */ 
/*     */       
/* 539 */       logMessage(displayNavAttributes(paramEntityItem, paramEntityGroup));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setControlBlock() {
/* 547 */     this.m_strNow = getNow();
/* 548 */     this.m_strForever = getForever();
/* 549 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 556 */       .m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
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
/* 568 */     return null;
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
/* 579 */     return "<b>Model Missing Data and Forms Report</b>";
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
/* 590 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRevision() {
/* 599 */     return new String("1.19");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 609 */     return "MODELABRSS.java,v 1.19 2008/01/30 19:39:15 wendy Exp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getABRVersion() {
/* 619 */     return getVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\MODELABRSS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */